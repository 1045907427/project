/**
 * @(#)BigDecimalConverter.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Apr 16, 2013 zhengziyong 创建版本
 */
package com.hd.agent.common.util;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

/**
 * 
 * BigDecimal转换器
 * @author zhengziyong
 */
public class BigDecimalConverter extends StrutsTypeConverter {

	@Override
	public Object convertFromString(Map arg0, String[] arg1, Class arg2) {
		BigDecimal bd = null;  
        if(BigDecimal.class ==arg2){  
            String bdStr = arg1[0];  
            if(bdStr!=null&&!"".equals(bdStr)){  
                bd = new BigDecimal(bdStr);  
            }else{  
                
            }  
            return bd;  
        }         
        return BigDecimal.ZERO;  
	}

	@Override
	public String convertToString(Map arg0, Object arg1) {
		 if(arg1 instanceof BigDecimal){  
	            BigDecimal b = new BigDecimal(arg1.toString()).setScale(2,BigDecimal.ROUND_HALF_DOWN);  
	            return b.toString();  
	        }         
	        return arg1.toString();  
	}

}

