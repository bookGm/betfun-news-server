package io.elasticsearch.service.impl;

import io.elasticsearch.dao.EsUserDao;
import io.elasticsearch.entity.EsUserEntity;
import io.elasticsearch.service.EsUserService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Service
public class EsUserServiceImpl implements EsUserService {
    @Autowired
    private EsUserDao userDao;
    @Autowired
    private ElasticsearchTemplate esTemplate;


    @Override
    public void saveUser(EsUserEntity userEntity) {
        userDao.save(userEntity);
    }

    @Override
    public void removeUser(Long[] uIds) {
        for (Long uId : uIds) {
            userDao.deleteById(uId);
        }
    }

    @Override
    public void updatedUser(EsUserEntity userEntity) {
        userDao.deleteById(userEntity.getuId());
        userDao.save(userEntity);
    }

    @Override
    public List<EsUserEntity> search(String key) {
        //根据昵称匹配用户
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders
                .fuzzyQuery("uNick", key).fuzziness(Fuzziness.AUTO))
                .withHighlightFields(new HighlightBuilder.Field(key)).build();
        Page<EsUserEntity> search = esTemplate.queryForPage(queryBuilder.build(), EsUserEntity.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
                List<EsUserEntity> users = new ArrayList<>();
                SearchHits hits = response.getHits();
                for (SearchHit searchHit : hits) {
                    if (hits.getHits().length <= 0) {
                        return null;
                    }
                    EsUserEntity user = new EsUserEntity();
                    String highLightMessage = searchHit.getHighlightFields().get(key).fragments()[0].toString();
                    user.setuNick(String.valueOf(searchHit.getFields().get("content")));
                    // 反射调用set方法将高亮内容设置进去
                    try {
                        String setMethodName = parSetName(key);
                        Class<? extends EsUserEntity> userClass = user.getClass();
                        Method setMethod = userClass.getMethod(setMethodName, String.class);
                        setMethod.invoke(user, highLightMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    users.add(user);
                }
                if (users.size() > 0) {
                    return (AggregatedPage<T>) new PageImpl<T>((List<T>) users);

                }
                return null;
            }
        });
        return search.getContent();
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

}
