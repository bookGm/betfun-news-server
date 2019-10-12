package io.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.io.Serializable;

/**
 * 测试Es地址数据
 *
 */
@Data
@Document(indexName = "pojo",type = "person",shards = 1,replicas = 0,refreshInterval = "-1")
public class LocationEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Field
    private String id;
    @Field
    private String firstName;
    @Field
    private String lastName;
    @Field
    private Integer age = 0;
    @Field
    private String about;

    @GeoPointField
    private GeoPoint geo;

}
