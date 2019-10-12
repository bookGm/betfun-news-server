package io.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.io.Serializable;

/**
 * 测试Es地址数据
 *
 */
@Data
@Document(indexName = "pojo",type = "person",shards = 5,replicas = 1,refreshInterval = "-1")
public class LocationEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Field(type = FieldType.Keyword)
    private String id;
    @Field(type = FieldType.Keyword)
    private String firstName;
    @Field(type = FieldType.Keyword)
    private String lastName;
    @Field(type = FieldType.Integer)
    private Integer age = 0;
    @Field(type = FieldType.Keyword)
    private String about;

    @GeoPointField
    private GeoPoint geo;

}
