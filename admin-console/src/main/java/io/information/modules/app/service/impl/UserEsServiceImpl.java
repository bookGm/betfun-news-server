package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guansuo.common.StringUtil;
import io.elasticsearch.dao.EsUserDao;
import io.elasticsearch.entity.EsUserEntity;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import io.information.modules.app.entity.InArticle;
import io.information.modules.app.service.IInArticleService;
import io.information.modules.app.service.UserEsService;
import org.elasticsearch.index.query.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Service
public class UserEsServiceImpl implements UserEsService {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private IInArticleService articleService;


    @Override
    public PageUtils search(SearchRequest request) {
        if (null != request.getKey() && StringUtil.isNotBlank(request.getKey())) {
            String key = request.getKey();
            Integer size = request.getPageSize();
            Integer page = request.getCurrPage();
            //多字段匹配[昵称，简介，企业名称，姓名？]
            SearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(multiMatchQuery(key, "uName", "uIntro", "uNick", "uCompanyName")
                            .operator(Operator.OR) /*.minimumShouldMatch("30%")*/)
                    .withPageable(PageRequest.of(page, size))
                    .build();
            AggregatedPage<EsUserEntity> esUserEntities =
                    elasticsearchTemplate.queryForPage(searchQuery, EsUserEntity.class);
            if (null != esUserEntities && !esUserEntities.getContent().isEmpty()) {
                List<EsUserEntity> list = esUserEntities.getContent();
                long totalCount = esUserEntities.getTotalElements();
                //文章数 浏览数 获赞数
                for (EsUserEntity entity : list) {
                    LambdaQueryWrapper<InArticle> queryWrapper = new LambdaQueryWrapper<>();
                    if (null != entity && null != entity.getuId()) {
                        queryWrapper.eq(InArticle::getuId, entity.getuId());
                        int articleNumber = articleService.count(queryWrapper);
                        entity.setArticleNumber(articleNumber);
                        List<InArticle> articles = articleService.list(queryWrapper);
                        long readNumber = articles.stream().mapToLong(InArticle::getaReadNumber).sum();
                        long likeNumber = articles.stream().mapToLong(InArticle::getaLike).sum();
                        entity.setReadNumber(readNumber);
                        entity.setLikeNUmber(likeNumber);
                    }
                }
                //列表数据 总记录数 每页记录数 当前页数
                return new PageUtils(list, totalCount, size, page);
            }
            return null;
        }
        return null;
    }

    /**
     * 拼接在某属性的 set方法
     */
    private static String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_')
            startIndex = 1;
        return "set" + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }


    //高亮显示ex

//    @Override
//    public PageUtils search(Map<String, Object> map) {
//        Integer size = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
//        Integer page = StringUtil.isBlank(map.get("currPage")) ? 1 : Integer.parseInt(String.valueOf(map.get("currPage")));
//        if (null != map.get("key") && StringUtil.isNotBlank(map.get("key"))) {
//            String key = String.valueOf(map.get("key"));
//            //根据昵称匹配用户
//            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
//            queryBuilder.withQuery(QueryBuilders
//                    .fuzzyQuery("uNick", key).fuzziness(Fuzziness.AUTO))
//                    .withHighlightFields(new HighlightBuilder.Field(key)).build();
//            Page<EsUserEntity> search = esTemplate.queryForPage(queryBuilder.build(), EsUserEntity.class, new SearchResultMapper() {
//                @Override
//                public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
//                    List<EsUserEntity> users = new ArrayList<>();
//                    SearchHits hits = response.getHits();
//                    for (SearchHit searchHit : hits) {
//                        if (hits.getHits().length <= 0) {
//                            return null;
//                        }
//                        EsUserEntity user = new EsUserEntity();
//                        String highLightMessage = searchHit.getHighlightFields().get(key).fragments()[0].toString();
//                        user.setuNick(String.valueOf(searchHit.getFields().get("content")));
//                        // 反射调用set方法将高亮内容设置进去
//                        try {
//                            String setMethodName = parSetName(key);
//                            Class<? extends EsUserEntity> userClass = user.getClass();
//                            Method setMethod = userClass.getMethod(setMethodName, String.class);
//                            setMethod.invoke(user, highLightMessage);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        users.add(user);
//                    }
//                    if (users.size() > 0) {
//                        return (AggregatedPage<T>) new PageImpl<T>((List<T>) users);
//                    }
//                    return null;
//                }
//            });
//            List<EsUserEntity> list = search.getContent();
//            long totalCount = search.getTotalElements();
//            //列表数据 总记录数 每页记录数 当前页数
//            return new PageUtils(list, totalCount, size, page);
//        }
//        return null;
//    }
}
