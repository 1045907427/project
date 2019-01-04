/**
 * @(#)StringToDateJsonValueProcessor.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 16, 2013 zhengziyong 创建版本
 */
package com.hd.agent.common.util;

import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * 
 * 
 * @author zhengziyong
 */
public class StringToDateJsonValueProcessor implements JsonValueProcessor {

	@Override
	public Object processArrayValue(Object arg0, JsonConfig arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
		if(arg1 instanceof Date){
			if("".equals(arg0)){
				arg0 = null;
			}
		}
		return arg0; 
	}

}

