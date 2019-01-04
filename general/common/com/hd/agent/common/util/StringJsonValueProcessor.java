/**
 * @(#)StringJsonValueProcessor.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 25, 2013 chenwei 创建版本
 */
package com.hd.agent.common.util;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * 
 * string对象转json字符串时，处理特殊字符，JSONArray.fromObject使用时注册进JsonConfig
 * @author chenwei
 */
public class StringJsonValueProcessor implements JsonValueProcessor {

	@Override
	public Object processArrayValue(Object arg0, JsonConfig arg1) {
		return null;
	}

	@Override
	public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
		if (arg1 instanceof String) {  
			if(arg1 != null){
	            return arg1.toString().replaceAll("'", "‘");
			}
        }  
		return arg1;
	}

}

