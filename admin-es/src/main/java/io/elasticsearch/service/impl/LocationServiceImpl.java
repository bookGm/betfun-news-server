package io.elasticsearch.service.impl;

import io.elasticsearch.dao.LocationDao;
import io.elasticsearch.entity.LocationEntity;
import io.elasticsearch.service.LocationService;
import io.elasticsearch.utils.PageUtils;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("locationService")
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationDao locationDao;
    @Autowired
    private ElasticsearchTemplate esTemplate;


    /**
     * 新增索引
     *
     * @param entity
     * @return
     */
    @Override
    public boolean save(LocationEntity entity) {
        LocationEntity save = locationDao.save(entity);
        return save != null;
    }

    /**
     * 删除索引
     *
     * @param entity
     */
    @Override
    public void delete(LocationEntity entity) {
        locationDao.delete(entity);
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Override
    public LocationEntity findById(String id) {
        Optional<LocationEntity> entity = locationDao.findById(id);
        return entity.isPresent() ? entity.get() : null;
    }

    /**
     * 查新全部
     *
     * @return
     */
    @Override
    public Iterable<LocationEntity> findAll() {
        return locationDao.findAll();
    }

    /**
     * 分页查询
     *
     * @param params
     * @param currPage
     * @param pageSize
     * @return
     */
    @Override
    public PageUtils findPage(String params, int currPage, int pageSize) {
        Pageable pageable = new PageRequest(currPage, pageSize);
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("firstName", params);

        Page<LocationEntity> page = locationDao.search(queryBuilder, pageable);

        int totalCount = (int) page.getTotalElements();
        List<LocationEntity> entities = page.getContent();

        return new PageUtils(entities, totalCount, pageSize, currPage);
    }
}
