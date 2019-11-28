package io.information.common.utils;

import com.guansuo.common.StringUtil;
import io.information.modules.news.entity.DicEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Filename: DicHelper.java  <br>
 * <p>
 * Description:   <br>
 *
 * @author: LX <br>
 * @version: 1.0 <br>
 */
@Component
public class DicHelper {
    private Map<Long, DicEntity> dicsContainer = new HashMap<Long, DicEntity>();
    private Map<String, DicEntity> dicsVContainer = new HashMap<String, DicEntity>();
    private List<DicEntity> dicsList = new ArrayList<DicEntity>();

    /**
     * 设置词典容器中的数据
     *
     * @param did
     * @param dic
     * @author: LX
     */
    public synchronized void setDicName(Long did, DicEntity dic) {
        dicsContainer.put(did, dic);
    }

    /**
     * 获取词典名称
     *
     * @param did
     * @return
     * @author: LX
     */
    public String getDicName(Long did) {
        if (dicsContainer.containsKey(did)) {
            return dicsContainer.get(did).getdName();
        }
        return null;
    }

    /**
     * 获取词典id
     *
     * @param value
     * @return
     * @author: LX
     */
    public Long getDicId(String value) {
        if (dicsVContainer.containsKey(value)) {
            return dicsVContainer.get(value).getdId();
        }
        return null;
    }

    /**
     * 获取词典值
     *
     * @param dicName
     * @return
     * @author: LX
     */
    public Long getDicValue(Long pid, String dicName) {
        List<DicEntity> dicsByPcode = dicsList.stream().filter(n -> n.getpId() == pid).collect(Collectors.toList());
        Long value = -1L;
        for (DicEntity d : dicsByPcode) {
            String name = d.getdName();
            if (dicName.equals(name)) {
                return value = d.getdId();
            }
//			else if(dicName.contains(name)||name.contains(dicName)){
//				return value= d.getCode();
//			}
        }
        return value;
    }

    /**
     * 初始化
     *
     * @param dicsDb
     * @author: LX
     */
    public void init(List<DicEntity> dicsDb) {
        for (DicEntity dic : dicsDb) {
            dicsContainer.put(dic.getdId(), dic);
            dicsVContainer.put(dic.getdValue(), dic);
        }
        dicsList = dicsDb;
    }

    /**
     * 获取词典名称
     *
     * @param list
     * @return
     * @author: LX
     */
    public String getDicName(String[] list) {
        if (list != null && list.length > 0) {
            String s = "";
            for (String temp : list) {
                if (dicsContainer.containsKey(temp)) {
                    s += "," + dicsContainer.get(temp).getdName();
                }
            }
            if (!StringUtil.isEmpty(s)) {
                return s.substring(1);
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

}
