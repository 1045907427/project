/**
 * @(#)BigDecimalJsonValueProcessor.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 25, 2013 chenwei 创建版本
 */
package com.hd.agent.common.util;

import java.math.BigDecimal;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * 
 * 数字转字符串，JSONArray.fromObject使用时注册进JsonConfig
 * @author chenwei
 */
public class BigDecimalJsonValueProcessor implements JsonValueProcessor {

	@Override
	public Object processArrayValue(Object arg0, JsonConfig arg1) {
		return null;
	}

	@Override
	public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
		if (arg1 instanceof BigDecimal) {  
			if(arg1 != null){
	            return ((BigDecimal)arg1).toString();
			}
        }  
		return arg1;
	}

}

