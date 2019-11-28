package io.information.common.utils;

import io.information.modules.sys.entity.SysCitysEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Filename: CityHelper.java  <br>
 * <p>
 * Description:   <br>
 *
 * @version: 1.0 <br>
 */
@Component
public class CityHelper {
    private Map<Long, SysCitysEntity> citys = new HashMap<Long, SysCitysEntity>();
    private List<SysCitysEntity> cityList = new ArrayList<SysCitysEntity>();

    /**
     * 设置城市数据
     *
     * @param cityId
     * @param city
     */
    public synchronized void setCity(Long cityId, SysCitysEntity city) {
        citys.put(cityId, city);
    }

    /**
     * 获取城市名称
     *
     * @param cityId
     * @return
     */
    public String getCityName(Long cityId) {
        if (citys.containsKey(cityId)) {
            return citys.get(cityId).getName();
        }
        return null;
    }

    /**
     * 获取城市id
     *
     * @param cityName
     * @return
     */
    public Long getCityId(String cityName) {
        Long id = 0L;
        for (SysCitysEntity s : cityList) {
            String name = s.getName();
            if (cityName.equals(name)) {
                return id = s.getId();
            } else if (cityName.contains(name) || name.contains(cityName)) {
                return id = s.getId();
            }
        }
        return id;
    }

    /**
     * 初始化
     *
     * @param cs
     */
    public void init(List<SysCitysEntity> cs) {
        for (SysCitysEntity c : cs) {
            citys.put(c.getId(), c);
        }
        cityList = cs;
    }
}
