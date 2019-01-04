/**
 * @(#)IReceiptExtService.java
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

import com.hd.agent.sales.model.RejectBillDetail;

import java.util.List;

/**
 * 
 * 销售发货回单对外接口
 * 
 * @author zhengziyong
 */
public interface IReceiptExtService {

	/**
	 * 退货通知单审核回写发货回单
	 * @param billDetailList
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 7, 2013
	 */
	public void updateReceiptDetailBack(List<RejectBillDetail> billDetailList) throws Exception;
	
	/**
	 * 退货通知单反审清除回写的发货回单数据
	 * @param billDetailList
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 7, 2013
	 */
	public void updateClearReceiptDetailBack(List<RejectBillDetail> billDetailList) throws Exception;
	
	/**
	 * 关闭销售发货回单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 7, 2013
	 */
	public boolean updateReceiptClose(String id) throws Exception;
	
	/**
	 * 退货通知单反审后发货回单状态需改回审核状态
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 7, 2013
	 */
	public boolean updateReceiptOpen(String id) throws Exception;
	
	/**
	 * 更新回单发票状态
	 * @param isinvoice
	 * @param canceldate
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jul 1, 2013
	 */
	public boolean updateReceiptInvoice(String isinvoice, String canceldate, String id) throws Exception;
	
}

