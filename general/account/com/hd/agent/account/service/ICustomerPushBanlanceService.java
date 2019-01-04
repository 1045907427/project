/**
 * @(#)ICustomerPushBanlanceService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 18, 2013 chenwei 创建版本
 */
package com.hd.agent.account.service;

import com.hd.agent.account.model.CustomerPushBalance;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.Receipt;
import com.hd.agent.sales.model.ReceiptDetail;

import java.util.List;
import java.util.Map;

/**
 * 
 * 客户应收款冲差service
 * @author chenwei
 */
public interface ICustomerPushBanlanceService {
	/**
	 * 获取客户应收款冲差列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 18, 2013
	 */
	public PageData showCustomerPushBanlanceList(PageMap pageMap) throws Exception;
	/**
	 * 客户应收款冲差添加
	 * @param customerPushBalance
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 18, 2013
	 */
	public boolean addCustomerPushBanlance(CustomerPushBalance customerPushBalance) throws Exception;
	/**
	 * 获取客户应收款冲差信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 19, 2013
	 */
	public CustomerPushBalance showCustomerPushBanlanceInfo(String id) throws Exception;
	/**
	 * 客户应收款冲差修改
	 * @param customerPushBalance
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 19, 2013
	 */
	public boolean editCustomerPushBanlance(CustomerPushBalance customerPushBalance) throws Exception;
	
	/**
	 * 客户应收款冲差备注修改
	 * @param customerPushBalance
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 27, 2014
	 */
	public boolean editCustomerPushBanlanceRemark(CustomerPushBalance customerPushBalance)throws Exception;
	/**
	 * 客户应收款冲差删除
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 19, 2013
	 */
	public boolean deleteCustomerPushBanlance(String id) throws Exception;
	/**
	 * 客户应收冲差审核
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 19, 2013
	 */
	public boolean auditCustomerPushBanlance(String id) throws Exception;
	/**
	 * 客户应收款冲差反审
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 19, 2013
	 */
	public boolean oppauditCustomerPushBanlance(String id) throws Exception;
	
	/**
	 * 获取客户应收款冲差列表
	 * @param map
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 18, 2013
	 */
	public List showCustomerPushBanlanceListBy(Map map) throws Exception;
	
	/**
	 * 更新打印次数
	 * @param customerPushBalance
	 * @author zhanghonghui 
	 * @date 2013-9-10
	 */
	public boolean updateOrderPrinttimes(CustomerPushBalance customerPushBalance) throws Exception;
	/**
	 * 更新打印次数
	 * @param list
	 * @author zhanghonghui 
	 * @date 2013-9-10
	 */
	public void updateOrderPrinttimes(List<CustomerPushBalance> list) throws Exception;
	/**
	 * 根据回单生成冲差单。
	 * 如果回单验收时，修改过价格，则根据价格调整后 产生的金额差异
	 * 生成冲差单
	 * @param receipt
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 15, 2013
	 */
	public boolean addCustomerPushBanlanceByReceipt(Receipt receipt ,List<ReceiptDetail> list) throws Exception;
	/**
	 * 根据回单编号 删除相关冲差单
	 * @param receiptid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 15, 2013
	 */
	public boolean deleteCustomerPushBanlanceByReceiptid(String receiptid) throws Exception;
	/**
	 * 获取冲打印数据，根据相同客户且相同业务日期会合并一起打印
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-1-25
	 */
	public Map<String, List<CustomerPushBalance>> showCustomerPushBanlancePrintListBy(Map map) throws Exception;
	/**
	 * 添加并且审核客户应收款冲差单（提供OA通路单接口）
	 * @param customerPushBalance
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月22日
	 */
	public String addAndAuditCustomerPushBanlanceByOA(CustomerPushBalance customerPushBalance) throws Exception;
	/**
	 * 根据OA编号删除客户应收款冲差单
	 * @param oaid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月24日
	 */
	public boolean deleteCustomerPushBanlanceByOA(String oaid) throws Exception;
    /**
     *
     * @param oaid
     * @return
     * @throws Exception
     * @author limin
     * @date 2015年4月8日
     */
    public List selectCustomerPushBanlanceByOaid(String oaid) throws Exception;

	/**
	 * 统计客户应收冲差<br/>
	 * map中参数：<br/>
	 * idarrs : 冲差单编号，多个编号使用 , 分隔<br/>
	 * statusarr :状态，多个状态以 ,分隔<br/>
	 * isinvoice : 来源类型<br/>
	 * invoiceid : 来源单据编号<br/>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2014-1-25
	 */
	public int getCustomerPushBanlanceCountBy(Map map) throws Exception;

    /**
     * 根据默认税种计算未税金额、税额
     * @param map
     * @return
     * @throws Exception
     * @author panxiaoixao
     * @date 2015-11-20
     */
    public Map getPushBanlanceNoTaxAmount(Map map)throws Exception;
    /**
     * 根据条件获取客户冲差报表
     * @param map
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016-8-2
     */
    public PageData getPushBalanceReportData(PageMap pageMap) throws Exception;

	 /**
	  * 据条件获取客户及业务员冲差报表  数据
	  * @author lin_xx
	  * @date 2016/12/24
	  */
	 public PageData getCustomerAndUserPushData(PageMap pageMap) throws Exception ;
	/**
	 * 批量添加客户应收款冲差
	 * @param customerPushBalance
	 * @param list
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date May 19, 2017
	 */
	public Map addMoreCustomerPushBanlace(CustomerPushBalance customerPushBalance,List<CustomerPushBalance> list) throws Exception;
	/**
	 * 批量审核客户应收款冲差
	 * @param ids
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date May 19, 2017
	 */
	public Map auditMoreCustomerPushBanlace(String ids) throws Exception;

}


