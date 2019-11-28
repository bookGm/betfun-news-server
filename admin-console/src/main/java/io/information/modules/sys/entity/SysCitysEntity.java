

package io.information.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 省市县（区）
 *
 * @author LX
 */
@Data
@TableName("sys_citys")
public class SysCitysEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    private Long pid;
    private Integer level;
    private String name;
    private String province;
    private String city;
    private String county;
    private String fullName;
    private Integer status;
    private String pinyin;
    private String keywords;

}
