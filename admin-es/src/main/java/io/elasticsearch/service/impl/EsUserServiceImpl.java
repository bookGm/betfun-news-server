package io.elasticsearch.service.impl;

import io.elasticsearch.dao.EsUserDao;
import io.elasticsearch.entity.EsUserEntity;
import io.elasticsearch.service.EsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EsUserServiceImpl implements EsUserService {
    @Autowired
    private EsUserDao userDao;


    @Override
    public void saveUser(EsUserEntity userEntity) {
        userDao.save(userEntity);
    }

    @Override
    public void removeUser(String[] uIds) {
        for (String uId : uIds) {
            userDao.deleteById(Long.parseLong(uId));
        }
    }

    @Override
    public void updatedUser(EsUserEntity userEntity) {
        userDao.deleteById(userEntity.getuId());
        userDao.save(userEntity);
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
