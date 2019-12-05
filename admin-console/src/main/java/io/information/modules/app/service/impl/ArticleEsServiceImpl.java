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
            //设置高亮
            withHighlight(searchQuery);
            //设置查询条件
            searchQuery.withQuery(multiMatchQuery(key, "aKeyword", "aTitle", "aContent", "aBrief")
                    .operator(Operator.OR) /*.minimumShouldMatch("30%")*/)
                    .withPageable(PageRequest.of(currPage, pageSize));

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
                            return new AggregatedPageImpl<>((List<T>) chunk);
                        }
                    });

            List<EsArticleEntity> entityList = esArticleEntities.getContent();
            // 简单时间
            for (EsArticleEntity entity : entityList) {
                entity.setASimpleTime(DateUtils.getSimpleTime(entity.getACreateTime()));
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
                .preTags("<em style='color:#349dff'>")
                .postTags("</em>")
                .fragmentSize(100);
        HighlightBuilder.Field hfield2 = new HighlightBuilder.Field("aTitle")
                .preTags("<em style='color:#349dff'>")
                .postTags("</em>")
                .fragmentSize(100);
        HighlightBuilder.Field hfield3 = new HighlightBuilder.Field("aContent")
                .preTags("<em style='color:#349dff'>")
                .postTags("</em>")
                .fragmentSize(100);
        HighlightBuilder.Field hfield4 = new HighlightBuilder.Field("aBrief")
                .preTags("<em style='color:#349dff'>")
                .postTags("</em>")
                .fragmentSize(100);
        searchQuery.withHighlightFields(hfield, hfield2, hfield3, hfield4);
    }

    /**
     * 根据搜索结果创建ES对象
     */
    private EsArticleEntity createEsEntity(Map<String, Object> smap, Map<String, HighlightField> hmap) {
        EsArticleEntity ed = new EsArticleEntity();
        if (smap.get("aId") != null)
            ed.setAId(Long.parseLong(String.valueOf(smap.get("aId").toString())));
        if (smap.get("uId") != null)
            ed.setUId(Long.parseLong(String.valueOf(smap.get("uId").toString())));
        if (smap.get("uName") != null)
            ed.setUName(String.valueOf(smap.get("uName").toString()));
        if (smap.get("aCover") != null)
            ed.setACover(String.valueOf(smap.get("aCover").toString()));
        if (smap.get("aType") != null)
            ed.setAType(Integer.parseInt(String.valueOf(smap.get("aType").toString())));
        if (smap.get("aSource") != null)
            ed.setASource(String.valueOf(smap.get("aSource").toString()));
        if (smap.get("aLink") != null)
            ed.setALink(String.valueOf(smap.get("aLink").toString()));
        if (smap.get("aLike") != null)
            ed.setALike(Long.parseLong(String.valueOf(smap.get("aLike").toString())));
        if (smap.get("aCollect") != null)
            ed.setACollect(Integer.parseInt(String.valueOf(smap.get("aCollect").toString())));
        if (smap.get("aCritic") != null)
            ed.setACritic(Long.parseLong(String.valueOf(smap.get("aCritic").toString())));
        if (smap.get("aReadNumber") != null)
            ed.setAReadNumber(Long.parseLong(String.valueOf(smap.get("aReadNumber").toString())));
        if (smap.get("aCreateTime") != null) {
            String timeString = String.valueOf(smap.get("aCreateTime").toString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = sdf.parse(timeString);
                ed.setACreateTime(date);
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
            String capitalize = StringUtils.capitalize(field);
            String methodName = "set" + capitalize;//setUName
            Class<?> clazz = object.getClass();
            try {
                Method setMethod = clazz.getMethod(methodName, String.class);
                setMethod.invoke(object, highLightMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
