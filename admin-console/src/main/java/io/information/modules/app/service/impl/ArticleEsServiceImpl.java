package io.information.modules.app.service.impl;

import com.guansuo.common.DateUtils;
import com.guansuo.common.StringUtil;
import com.rabbitmq.client.Channel;
import io.elasticsearch.dao.EsArticleDao;
import io.elasticsearch.entity.EsArticleEntity;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import io.information.modules.app.service.ArticleEsService;
import io.information.modules.app.service.IInArticleService;
import io.mq.utils.Constants;
import io.mq.utils.RabbitMQUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Service
public class ArticleEsServiceImpl implements ArticleEsService {
    @Autowired
    private EsArticleDao articleDao;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    IInArticleService articleService;


    private static final Logger LOG = LoggerFactory.getLogger(ArticleEsServiceImpl.class);

    @RabbitListener(queues = Constants.queue)
    public void receive(String payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        LOG.info("消费内容为：{}", payload);
        RabbitMQUtils.askMessage(channel, tag, LOG);
    }

    @Override
    public PageUtils searchInfo(SearchRequest request) {
        if (null != request.getKey() && StringUtil.isNotBlank(request.getKey())) {
            String key = request.getKey();
            Integer pageSize = request.getPageSize();
            Integer currPage = request.getCurrPage();

            NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
            //设置索引...
            //设置查询条件
            searchQuery.withQuery(multiMatchQuery(key, "aKeyword", "aTitle", "aContent", "aBrief")
                    .operator(Operator.OR) /*.minimumShouldMatch("30%")*/)
                    .withPageable(PageRequest.of(currPage, pageSize));

            //设置高亮
            withHighlight(searchQuery);
            //自定义查询结果封装

            AggregatedPage<EsArticleEntity> esArticleEntities = elasticsearchTemplate.queryForPage(searchQuery.build(), EsArticleEntity.class,
                    new SearchResultMapper() {

                        @Override
                        public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz,
                                                                Pageable pageable) {
                            // TODO Auto-generated method stub
                            List<EsArticleEntity> chunk = new ArrayList<>();
                            SearchHits hits = response.getHits();

                            for (SearchHit hit : hits) {
                                if (hits.getHits().length <= 0) {
                                    return null;
                                }
                                Map<String, Object> smap = hit.getSourceAsMap();
                                Map<String, HighlightField> hmap = hit.getHighlightFields();
                                EsArticleEntity esEntity = createEsEntity(smap, hmap);

                                //高亮内容
                                setHighLight(hit, "aKeyword", esEntity);
                                setHighLight(hit, "aTitle", esEntity);
                                setHighLight(hit, "aContent", esEntity);
                                setHighLight(hit, "aBrief", esEntity);

                                chunk.add(esEntity);
                            }
                            return new AggregatedPageImpl<>((List<T>) chunk, pageable, response.getHits().getTotalHits());
                        }
                    });

            List<EsArticleEntity> entityList = esArticleEntities.getContent();
            // 简单时间
            for (EsArticleEntity entity : entityList) {
                entity.setaSimpleTime(DateUtils.getSimpleTime(entity.getaCreateTime()));
            }
            long total = esArticleEntities.getTotalElements();
            return new PageUtils(entityList, total, pageSize, currPage);
        }
        return null;
    }


    /**
     * 添加高亮条件
     */
    private void withHighlight(NativeSearchQueryBuilder searchQuery) {
        HighlightBuilder.Field hfield = new HighlightBuilder.Field("aKeyword")
                .preTags("<b style='color:#349dff'>")
                .postTags("</b>")
                .fragmentSize(100);
        HighlightBuilder.Field hfield2 = new HighlightBuilder.Field("aTitle")
                .preTags("<b style='color:#349dff'>")
                .postTags("</b>")
                .fragmentSize(100);
        HighlightBuilder.Field hfield3 = new HighlightBuilder.Field("aContent")
                .preTags("<b style='color:#349dff'>")
                .postTags("</b>")
                .fragmentSize(100);
        HighlightBuilder.Field hfield4 = new HighlightBuilder.Field("aBrief")
                .preTags("<b style='color:#349dff'>")
                .postTags("</b>")
                .fragmentSize(100);
        searchQuery.withHighlightFields(hfield, hfield2, hfield3, hfield4);
    }

    /**
     * 根据搜索结果创建ES对象
     */
    private EsArticleEntity createEsEntity(Map<String, Object> smap, Map<String, HighlightField> hmap) {
        EsArticleEntity ed = new EsArticleEntity();
        if (smap.get("aId") != null)
            ed.setaId(Long.parseLong(String.valueOf(smap.get("aId").toString())));
        if (smap.get("uId") != null)
            ed.setuId(Long.parseLong(String.valueOf(smap.get("uId").toString())));
        if (smap.get("uName") != null)
            ed.setuName(String.valueOf(smap.get("uName").toString()));
        if (smap.get("aCover") != null)
            ed.setaCover(String.valueOf(smap.get("aCover").toString()));
        if (smap.get("aType") != null)
            ed.setaType(Integer.parseInt(String.valueOf(smap.get("aType").toString())));
        if (smap.get("aSource") != null)
            ed.setaSource(String.valueOf(smap.get("aSource").toString()));
        if (smap.get("aLink") != null)
            ed.setaLink(String.valueOf(smap.get("aLink").toString()));
        if (smap.get("aLike") != null)
            ed.setaLike(Long.parseLong(String.valueOf(smap.get("aLike").toString())));
        if (smap.get("aCollect") != null)
            ed.setaCollect(Integer.parseInt(String.valueOf(smap.get("aCollect").toString())));
        if (smap.get("aCritic") != null)
            ed.setaCritic(Long.parseLong(String.valueOf(smap.get("aCritic").toString())));
        if (smap.get("aReadNumber") != null)
            ed.setaReadNumber(Long.parseLong(String.valueOf(smap.get("aReadNumber").toString())));
        if (smap.get("aCreateTime") != null) {
            String timeString = String.valueOf(smap.get("aCreateTime").toString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = sdf.parse(timeString);
                ed.setaCreateTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return ed;
    }


    /**
     * 反射调用属性的set方法设置高亮的内容   注意BUG
     */
    public void setHighLight(SearchHit searchHit, String field, Object object) {
        Map<String, HighlightField> highlightFieldMap = searchHit.getHighlightFields();
        HighlightField highlightField = highlightFieldMap.get(field);
        if (highlightField != null) {
            String highLightMessage = highlightField.fragments()[0].toString();
//            String capitalize = StringUtils.capitalize(field);
//            String methodName = "set" + capitalize;//setUName
            String methodName = "set" + field;//setuName
            Class<?> clazz = object.getClass();
            try {
                Method setMethod = clazz.getMethod(methodName, String.class);
                setMethod.invoke(object, highLightMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



//    @Override
//    public PageUtils searchTest(SearchRequest request) {
//        if (null != request.getKey() && StringUtil.isNotBlank(request.getKey())) {
//            String key = request.getKey();
//            Integer size = request.getPageSize();
//            Integer page = request.getCurrPage();
//            //多字段匹配[标题，摘要，内容]
//            SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                    .withQuery(multiMatchQuery(key, "aKeyword", "aTitle", "aContent", "aBrief")
//                                    .operator(Operator.OR)
//                            /*.minimumShouldMatch("30%")*/)
//                    .withPageable(PageRequest.of(page, size))
//                    .build();
//            AggregatedPage<EsArticleEntity> esFlashEntities =
//                    elasticsearchTemplate.queryForPage(searchQuery, EsArticleEntity.class);
//            if (null != esFlashEntities && !esFlashEntities.getContent().isEmpty()) {
//                List<EsArticleEntity> list = esFlashEntities.getContent();
//                long totalCount = esFlashEntities.getTotalElements();
//                //列表数据 总记录数 每页记录数 当前页数
//                return new PageUtils(list, totalCount, size, page);
//            }
//            return null;
//        }
//        return null;
//    }

}
