/**
 * @(#)CacheBeanPostProcessor.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jan 23, 2014 chenwei 创建版本
 */
package com.hd.agent.common.cache;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.service.impl.BaseServiceImpl;

/**
 * 
 * 
 * @author chenwei
 */
public class CacheBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessAfterInitialization(Object obj, String arg1)
			throws BeansException {
		try {  
            if(obj instanceof BaseServiceImpl) {    
                ((BaseServiceImpl)obj).getAmountDecimalsLength();
            }else if(obj instanceof BaseAction){
            	 ((BaseAction)obj).getAmountDecimalsLength();
            }
        } catch (Exception e) {  
             e.printStackTrace();  
        }   
        return obj;    
	}

	@Override
	public Object postProcessBeforeInitialization(Object arg0, String arg1)
			throws BeansException {
		return arg0;
	}

}

