package io.elasticsearch.utils;

import io.elasticsearch.dao.EsArticleDao;
import io.elasticsearch.dao.LocationDao;
import io.elasticsearch.entity.EsArticleEntity;
import io.elasticsearch.entity.LocationEntity;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = LocationRTest.class)
public class LocationRTest {
    @Autowired
    LocationDao employeeDao;
    @Autowired
    EsArticleDao articleDao;

//    @Test
    //导入数据
    public void testAdd() {
        List<EsArticleEntity> employees = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            EsArticleEntity location = new EsArticleEntity();
            location.setUId(0L);
            location.setABrief("摘要" + i);
            location.setAId((long) i);
            location.setAContent("内容" + i);
            location.setACreateTime(new Date());
            location.setATitle("标题" + i);
            employees.add(location);
        }
        articleDao.saveAll(employees);
    }


    /**
     * 地理搜索方法
     *
     * @param lat      latitude 纬度cl
     * @param lon      longitude 经度
     * @param distance 距离
     * @param pageable 分页
     */
    public Page<LocationEntity> findPage(double lat, double lon, String distance, Pageable pageable) {
        //实现了SearchQuery接口，用于组装QueryBuilder和SortBuilder以及Pageable等
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //分页
        builder.withPageable(pageable);
        //比尔查询件：间接实现了QueryBuilder接口
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //以点为中心，搜索指定范围
        GeoDistanceQueryBuilder distanceQueryBuilder = new GeoDistanceQueryBuilder("geo");
        distanceQueryBuilder.point(lat, lon);
        //定义查询的距离单位：千米
        distanceQueryBuilder.distance(distance, DistanceUnit.KILOMETERS);
        boolQueryBuilder.filter(distanceQueryBuilder);
        builder.withQuery(boolQueryBuilder);

        //按相隔距离排序：千米
        GeoDistanceSortBuilder sortBuilder = new GeoDistanceSortBuilder("geo", lat, lon);
        sortBuilder.unit(DistanceUnit.KILOMETERS);
        sortBuilder.order(SortOrder.ASC);
        builder.withSort(sortBuilder);

        return employeeDao.search(builder.build());
    }

    @Test
    public void search() {
        Page<LocationEntity> page = findPage(11.71, 13.25, "50000000", new PageRequest(0, 5));
        for (LocationEntity location : page) {
            double distance = GeoDistance.ARC.calculate(
                    11.71,
                    13.25,
                    location.getGeo().getLat(),
                    location.getGeo().getLon(),
                    DistanceUnit.KILOMETERS);
            System.out.println("距离：" + location + "目标," + distance + "千米");
        }
    }
}
