
package com.john.manage.json;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonStringOnly {
	
	
	@Test
	//无需对象快速转化为json字符串
	public void toJSONString() throws Exception{
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("website", 1);
		jsonParam.put("type", "衣服1");
		System.out.println("简单对象"+JSONObject.toJSON(jsonParam));
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("website", 2);
		map.put("type", "衣服2");
		JSONObject jsonParam1 = new JSONObject(map);
		
		JSONArray array = new JSONArray();
		array.add(jsonParam);
		array.add(jsonParam1);
		System.out.println("数组对象"+JSONArray.toJSON(array));
		
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("website", 3);
		map2.put("array", array);
		System.out.println("复杂对象"+JSONObject.toJSON(map2));
	}
	
	
	
	@Test
	//json字符串	快速获取信息
	public void fromJSONString() throws Exception{
		
		String json1 = "{\"website\":1,\"type\":\"衣服1\"}";
		JSONObject jsonParam1 = (JSONObject)JSONObject.parse(json1);
		System.out.println("简单JSON属性:"+jsonParam1.getString("type"));
		
		String json2 = "[{\"website\":1,\"type\":\"衣服2\"},{\"website\":2,\"type\":\"衣服\"}]";
		JSONArray array = JSONObject.parseArray(json2);
		System.out.println("数组JSON属性:"+(array.getJSONObject(0)).getString("type"));
		
		
		String json3 = "{\"array\":[{\"website\":1,\"type\":\"衣服3\"},{\"website\":2,\"type\":\"衣服\"}],\"website\":3}";
		JSONObject jsonParam3 = (JSONObject)JSONObject.parse(json3);
		JSONArray jsonArray = jsonParam3.getJSONArray("array");
		System.out.println("复杂JSON属性:"+(jsonArray.getJSONObject(0)).getString("type"));
		
	}
	
	
}
