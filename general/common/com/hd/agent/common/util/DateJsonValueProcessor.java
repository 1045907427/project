/**
 * @(#)DateJsonValueProcessor.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 15, 2013 zhengziyong 创建版本
 */
package com.hd.agent.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * 
 * date转换成日期字符串，JSONArray.fromObject使用时注册进JsonConfig
 * @author zhengziyong
 */
public class DateJsonValueProcessor implements JsonValueProcessor {

	@Override
	public Object processArrayValue(Object arg0, JsonConfig arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
		if (arg1 instanceof Date) {  
			if(arg1 != null){
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            return dateFormat.format(arg1);
			}
        }  
        return arg1;  
	}

}

