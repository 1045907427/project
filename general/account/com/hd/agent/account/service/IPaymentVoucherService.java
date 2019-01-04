package com.hd.agent.account.service;

import com.hd.agent.account.model.PaymentVoucher;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

import java.util.List;
import java.util.Map;

public interface IPaymentVoucherService {
	
	/**
	 * 添加交款单及其明细
	 * @param paymentVoucher
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年3月25日
	 */
	public boolean addPaymentVoucherAddDetail(PaymentVoucher paymentVoucher) throws Exception;
	/**
	 * 交款单分页列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public PageData showPaymentVoucherPageList(PageMap pageMap) throws Exception;
	
	/**
	 * 新增交款单
	 * @param paymentVoucher
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public boolean insertPaymentVoucher(PaymentVoucher paymentVoucher) throws Exception;
	/**
	 * 更新交款单
	 * @param paymentVoucher
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public boolean updatePaymentVoucher(PaymentVoucher paymentVoucher) throws Exception;
	
	/**
	 * 根据ID编号获取交款单
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public PaymentVoucher showPaymentVoucher(String id) throws Exception;
	/**
	 * 根据ID编号删除交款单及其明细
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public boolean deletePaymentVoucherAndDetail(String id) throws Exception;
	/**
	 * 审核交款单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年3月25日
	 */
	public Map auditPaymentVoucher(String id) throws Exception;
	/**
	 * 反审核交款单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年3月26日
	 */
	public Map oppauditPaymentVoucher(String id) throws Exception;
	/**
	 *  根据数据ID编号及数据权限获取交款单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年3月25日
	 */
	public PaymentVoucher showPaymentVoucherByDataAuth(String id) throws Exception;
	/**
	 * 更新交款单及其明细
	 * @param paymentVoucher
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年3月25日
	 */
	public boolean updatePaymentVoucherAddDetail(PaymentVoucher paymentVoucher) throws Exception;
	/**
	 * 根据ID编号查看交款单及明细
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年3月26日
	 */
	public PaymentVoucher showPaymentVoucherAndDetail(String id) throws Exception;
	/**
	 * 根据交款单更新打印次数
	 * @param paymentVoucher
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年3月26日
	 */
	public boolean updateOrderPrinttimes(PaymentVoucher paymentVoucher) throws Exception;
	/**
	 * 根据交款单列表更新打印次数
	 * @param list
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年3月26日
	 */
	public void updateOrderPrinttimes(List<PaymentVoucher> list) throws Exception;
    /**
     * 根据交款单列表更新交款单状态
     * @param list
     * @throws Exception
     * @author zhanghonghui
     * @date 2015年3月26日
     */
    public boolean updateOrderByStatus(List<PaymentVoucher> list) throws Exception;
	/**
	 * 根据map中参数获取交款单列表<br/>
	 * map中参数:<br/>
	 * dataSql：数据权限<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * status: 表示状态3<br/>
	 * statusarr: 表示状态，类似 1,2,3<br/>
	 * notprint ： 1 表示未打印的<br/>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年3月26日
	 */
	public List showPaymentVoucherListBy(Map map) throws Exception;

    /**
     * 判断手机上传的单据是否存在 存在则返回单据编号
     * @param billid
     * @return
     * @throws Exception
     */
    public String hasPhoneBill(String billid) throws Exception;

    /**
     * 交款单分页列表
     * @param pageMap
     * @return
     * @author zhanghonghui
     * @date 2015年3月24日
     */
    public List showPaymentVoucherListForPhone(PageMap pageMap) throws Exception;

    /**
     * 获取所有审核通过的交款单
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016-8-2
     */
    public List<PaymentVoucher> getPaymentVoucherListByAudit() throws Exception;

}
