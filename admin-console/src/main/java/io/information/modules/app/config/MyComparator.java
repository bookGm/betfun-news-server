package io.information.modules.app.config;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;

public class MyComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        // TODO Auto-generated method stub
        int result = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Map.Entry<Object, Object> map1 = (Map.Entry<Object, Object>) o1, map2 = (Map.Entry<Object, Object>) o2;
            Date map1_time = format.parse(String.valueOf(map1.getValue()));
            Date map2_time = format.parse(String.valueOf(map2.getValue()));
            if (map1_time.after(map2_time)) {
                result = -1;
            } else if (map1_time.before(map2_time)) {
                result = 1;
            } else if (map1_time.equals(map2_time)) {
                result = 0;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }
}