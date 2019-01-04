/**
 * @(#)IOwnOrderService.java
 *
 * @author wanghongteng
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月21日 wanghongteng 创建版本
 */
package com.hd.agent.sales.service;

import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.OweOrder;

/**
 * 
 * 
 * @author wanghongteng
 */
/**
 * @author Administrator
 *
 */
public interface IOweOrderService {
	 
    
    PageData getOweOrderList(PageMap pageMap) throws Exception;
    
    public OweOrder getOweOrder(String id) throws Exception;
    
    public Map OweOrderAddOrder(String id) throws Exception;
    public Map updateOweOrder(OweOrder oweOrder) throws Exception;
    public Map deleteOweOrder(String id) throws Exception;
    public Map closeOweOrder(String id) throws Exception;
    public Map addOweOrderByDispatchBill(String id) throws Exception;
    public Map addOweOrderByOrder(String id) throws Exception;
    public void sendMessage() throws Exception;
    public Map checkOweOrderId(String id) throws Exception;
}

