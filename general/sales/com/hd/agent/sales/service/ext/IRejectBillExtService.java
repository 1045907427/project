/**
 * @(#)IRejectBillService.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 7, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.service.ext;

import com.hd.agent.sales.model.Receipt;

/**
 * 
 * 销售退货通知单对外接口
 * 
 * @author zhengziyong
 */
public interface IRejectBillExtService {

	/**
	 * 判断是否需要生成退货通知单
	 * @param receipt
	 * @return true需要生成退货通知单，false不需要生成退货通知单
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 7, 2013
	 */
	public boolean isAutoAddRejectBill(Receipt receipt) throws Exception;
	
	/**
	 * 回单审核自动生成退货通知单
	 * @param receipt
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 7, 2013
	 */
	public String addRejectBillAuto(Receipt receipt) throws Exception;
	
	/**
	 * 反审回单时删除退货通知单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 7, 2013
	 */
	public boolean deleteRejectBillOppauditReceipt(String id) throws Exception;
	/**
	 * 反审删除销售退货通知单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年2月6日
	 */
	public boolean oppauditAndDeleteRejectBillByReceipt(String id) throws Exception;
	
}

