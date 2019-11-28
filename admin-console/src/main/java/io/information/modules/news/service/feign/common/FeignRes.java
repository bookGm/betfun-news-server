

package io.information.modules.news.service.feign.common;

import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 */
public class FeignRes extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public FeignRes() {
        put("code", 0);
        put("msg", "success");
        put("count", "success");
        put("data", "success");
    }

    public static FeignRes error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static FeignRes error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static FeignRes error(int code, String msg) {
        FeignRes r = new FeignRes();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static FeignRes ok(String msg) {
        FeignRes r = new FeignRes();
        r.put("msg", msg);
        return r;
    }

    public static FeignRes ok(Map<String, Object> map) {
        FeignRes r = new FeignRes();
        r.putAll(map);
        return r;
    }

    public static FeignRes ok() {
        return new FeignRes();
    }

    public FeignRes put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
