/**
 * @(#)JSONUtils.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 9, 2013 chenwei 创建版本
 */
package com.hd.agent.common.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.math.BigDecimal;
import java.util.*;

/**
 * 
 * JSON相关操作工具类
 * @author chenwei
 */
public class JSONUtils {
	/**
	 * json字符串转List对象
	 * @param jsonStr
	 * @param object
	 * @return
	 * @author chenwei 
	 * @date May 9, 2013
	 */
	public static List jsonStrToList(String jsonStr,Object object){
		List list = null;
		if(null!=jsonStr){
			JSONArray array = JSONArray.fromObject(jsonStr);
			JSONArray result = new JSONArray();
			for(int i=0; i<array.size(); i++){
				JSONObject jsonObject = (JSONObject)array.get(i);
				if(!jsonObject.isEmpty()){
                    //移除明细里面的老数据记录
                    jsonObject.remove("oldFromData");
					result.add(jsonObject);
				}
			}
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new StringToDateJsonValueProcessor());
			list = JSONArray.toList(result, object, jsonConfig);
		}
		return list;
	}

	/**
	 * json字符串转对象
	 * @param jsonStr
	 * @param object
	 * @return
	 */
	public static Object jsonStrToObject(String jsonStr,Object object){
		Object rObject = null;
		if(null!=jsonStr){
			JSONObject jsonObject = JSONObject.fromObject(jsonStr);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new StringToDateJsonValueProcessor());
			rObject = JSONObject.toBean(jsonObject, object, jsonConfig);
		}
		return rObject;
	}
	public static Map jsonStrToMap(String jsonStr) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		Map map = mapper.readValue(jsonStr, Map.class);
		return map;
	}
	/**
	 * 对象列表list转换成json字符串
	 * @param list
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 15, 2013
	 */
	public static String listToJsonStr(List list) throws Exception{
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
		jsonConfig.registerJsonValueProcessor(BigDecimal.class, new BigDecimalJsonValueProcessor());
		jsonConfig.registerJsonValueProcessor(String.class, new StringJsonValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		String result = jsonArray.toString();
		result = result.replaceAll("\\\\", "\\\\\\\\");
		return result;
	}
	
	/**
	 * 对象列表list转换成json字符串
	 * @param list
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 15, 2013
	 */
	public static String listToJsonStrFilter(List list) throws Exception{
		JsonConfig jsonConfig = new JsonConfig();
		//对象转json字符串 控制某些字段不转换
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);     
		jsonConfig.setExcludes(new String[] { "field01", "field02" , "field03", "field04", "field05", "field06", 
				"field07", "field08", "field09", "field10", "field11", "field12",
				"adduserid","addusername","adddeptid","adddeptname","addtime","modifyuserid","modifyusername","modifytime",
				"openuserid","openusername","opentime","closeuserid","closeusername","closetime",
				"physicsremark","newtotalbuynum","newtotalbuyamount","costaccountprice","planmargin",
				"newbuyprice","newsaleprice","newstorageprice","newbuydate","newsaledate","everybillaveragesales","newinstroragedate",
				"newoutstoragedate","newinventory","newcheckdate","gshape","glength","gmlength","gwidth","gmwidth","ghight","gmhight",
				"gdiameter","gmdiameter","grossweight","netweight","totalweight","totalvolume","singlevolume","kgweight","image","priceList",
				"isshelflife","shelflife","shelflifeunit","highestbuyprice","lowestsaleprice","basesaleprice","highestinventory","lowestinventory",
				"safeinventory","normalprice","checktype","checkdate","checkunit","defaultbuyer","defaultsaler","defaultsupplier","defaulttaxtype",
				"productfield","sortkey","isinoutstorage","storageid","storagelocation","isbatch","isstoragelocation",
				"abclevel","bstype","bstypeName","checktypeName","checkunitName","defaultbuyerName","defaultsalerName","defaulttaxtypeName",
				"oldId","oldbrand","olddefaultsupplier","shelflifeunitName","storageName","storagelocationName","gshapeName","goodstypeName",
				"delMUIds","delPriceIds","delSLIds","delStorageIds","delWCIds","edittype"}); 
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
		jsonConfig.registerJsonValueProcessor(BigDecimal.class, new BigDecimalJsonValueProcessor());
		jsonConfig.registerJsonValueProcessor(String.class, new StringJsonValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		String result = jsonArray.toString();
		result = result.replaceAll("\\\\", "\\\\\\\\");
		return result;
	} 
	/**
	 * 对象列表list转换成json字符串
	 * @param list
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 15, 2013
	 */
	public static String listToJsonStrFilterWithOutGoodsInfo(List list) throws Exception{
		JsonConfig jsonConfig = new JsonConfig();
		//对象转json字符串 控制某些字段不转换
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);     
		jsonConfig.setExcludes(new String[] { "field01", "field02" , "field03", "field04", "field05", "field06", 
				"field07", "field08", "field09", "field10", "field11", "field12",
				"adduserid","addusername","adddeptid","adddeptname","addtime","modifyuserid","modifyusername","modifytime",
				"openuserid","openusername","opentime","closeuserid","closeusername","closetime",
				"goodsInfo"}); 
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
		jsonConfig.registerJsonValueProcessor(BigDecimal.class, new BigDecimalJsonValueProcessor());
		jsonConfig.registerJsonValueProcessor(String.class, new StringJsonValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		String result = jsonArray.toString();
		result = result.replaceAll("\\\\", "\\\\\\\\");
		return result;
	} 
	/**
	 * 对象转换成json字符串
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-1-6
	 */
	public static String mapToJsonStr(Map map) throws Exception{
		if(null!=map){
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new DateTimeJsonValueProcessor());
			jsonConfig.registerJsonValueProcessor(BigDecimal.class, new BigDecimalJsonValueProcessor());
			jsonConfig.registerJsonValueProcessor(String.class, new StringJsonValueProcessor());
			JSONObject jsonObject = JSONObject.fromObject(map,jsonConfig);
			return jsonObject.toString();
		}else{
			return null;
		}
		
	}
    /**
     * 对象转换成json字符串  清除null值
     * @param map
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-1-6
     */
    public static String mapToJsonStrClearNull(Map map) throws Exception{
        if(null!=map){
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
            jsonConfig.registerJsonValueProcessor(BigDecimal.class, new BigDecimalJsonValueProcessor());
            jsonConfig.registerJsonValueProcessor(String.class, new StringJsonValueProcessor());
            //过滤null值
            PropertyFilter pf = new PropertyFilter() {
                @Override
                // 返回true则跳过此属性，返回false则正常转换
                public boolean apply(Object source, String name, Object value) {
                    if (value == null || String.valueOf(value).equals("")) {
                        return true;
                    }
                    return false;
                }
            };
            // 将过滤器放入json-config中
            jsonConfig.setJsonPropertyFilter(pf);
            JSONObject jsonObject = JSONObject.fromObject(map,jsonConfig);
            return jsonObject.toString();
        }else{
            return null;
        }
    }
    /**
     * object对象转换成json字符串
     * @param object
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-1-6
     */
    public static String objectToJsonStr(Object object) throws Exception{
        if(null!=object){
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
            jsonConfig.registerJsonValueProcessor(BigDecimal.class, new BigDecimalJsonValueProcessor());
            jsonConfig.registerJsonValueProcessor(String.class, new StringJsonValueProcessor());
            JSONObject jsonObject = JSONObject.fromObject(object,jsonConfig);
            return jsonObject.toString();
        }else{
            return null;
        }

    }

    /**
     * 返回两个map中value不相等的键值
     * @param map1
     * @param map2
     * @param index 参数位置
     * @return
     * @throws Exception
     */
    public static String compareMap(Map map1,Map map2,int index) throws Exception {
        String key = "";
        if(map1.size() > 0 && map2.size() > 0) {
            Iterator<Map.Entry<String, String>> iter1 = map1.entrySet().iterator();
            while (iter1.hasNext()) {
                Map.Entry<String, String> entry1 = (Map.Entry<String, String>) iter1.next();

                if (entry1.getValue() != null && map2.get(entry1.getKey()) != null) {
                    Object m1value = entry1.getValue();
                    Object m2value = map2.get(entry1.getKey());

                    if (!m1value.equals(m2value)){//若两个map中相同key对应的value不相等
                        key += index+"#"+entry1.getKey() + ",";
                       // key += entry1.getKey() + ",";
                    }
                }
            }
        }
        return key;
    }
}

