

package io.information.modules.news.service.feign.common;

import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 */
public class FeignResJinSe {
	private static final long serialVersionUID = 1L;

	private Integer bottom_id;
	private JSONArray list;
	private Integer top_id;

	public Integer getBottom_id() {
		return bottom_id;
	}

	public void setBottom_id(Integer bottom_id) {
		this.bottom_id = bottom_id;
	}

	public JSONArray getList() {
		return list;
	}

	public void setList(JSONArray list) {
		this.list = list;
	}

	public Integer getTop_id() {
		return top_id;
	}

	public void setTop_id(Integer top_id) {
		this.top_id = top_id;
	}
}
