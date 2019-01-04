package com.hd.agent.journalsheet.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.journalsheet.model.CustomerCostPayable;

/**
 * 客户应付款费用
 * @author hdit001
 */
public interface CustomerCostPayableMapper {
	/**
	 * 添加客户应付费用
	 * @param customerCostPayable
	 * @return
	 * @author chenwei 
	 * @date 2014年11月12日
	 */
	public int addCustomerCostPayable(CustomerCostPayable customerCostPayable);
	/**
	 * 删除客户应付费用
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2014年11月12日
	 */
	public int deleteCustomerCostPayableByOaid(@Param("oaid")String oaid);

	/**
	 * 删除客户费用
	 * @param oaid
	 * @param billtype
	 * @return
	 * @author limin
	 * @date Sep 29, 2015
	 */
	public int deleteCustomerCostPayableByOaidBilltype(@Param("oaid")String oaid, @Param("billtype")String billtype);

	/**
	 * 更新客户应付费用是否付款状态
	 * @param oaid
	 * @param paydate
	 * @param payuserid
	 * @param ispay
	 * @return
	 * @author chenwei 
	 * @date 2014年11月12日
	 */
	public int updateCustomerCostPayableIsPay(@Param("oaid")String oaid,@Param("paydate")String paydate,@Param("payuserid")String payuserid,@Param("paydeptid")String paydeptid,@Param("ispay")String ispay);
	/**
	 * 获取客户应付费用合计列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年11月12日
	 */
	public List showCustomerCostPayableListData(PageMap pageMap);
	/**
	 * 获取客户应付费用合计数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年11月12日
	 */
	public int showCustomerCostPayableListCount(PageMap pageMap);
	/**
	 * 获取客户应付费用合计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年11月12日
	 */
	public Map showCustomerCostPayableSumData(PageMap pageMap);
	/**
	 * 获取客户应付费用明细数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年11月12日
	 */
	public List showCustomerCostPayableDetailList(PageMap pageMap);
	/**
	 * 获取客户应付费用明细数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年11月12日
	 */
	public int showCustomerCostPayableDetailCount(PageMap pageMap);
	/**
	 * 获取客户应付费用明细合计金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年11月12日
	 */
	public CustomerCostPayable showCustomerCostPayableDetailSum(PageMap pageMap);

	/**
	 * 获取客户应付费用明细数据
	 * @param id
	 * @param customerid
	 * @param businessdate
	 * @return
	 * @author limin
	 * @date Aug 3, 2016
	 */
	public Map showCustomerCostPayableBeginEnd(@Param("id") String id, @Param("customerid") String customerid, @Param("businessdate") String businessdate,@Param("isPcustomer") String isPcustomer);

	/**
	 * 根据OA编号查询客户费用
	 * @param oaid
	 * @return
	 * @author limin 
	 * @date 2015-1-17
	 */
	public List selectCustomerCostPayableByOaid(@Param("oaid")String oaid);
	/**
	 * 获取客户应付费用期初列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2015年1月20日
	 */
	public List showCustomerCostPayableInitList(PageMap pageMap);
	/**
	 * 获取客户应付费用期初数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2015年1月20日
	 */
	public int showCustomerCostPayableInitCount(PageMap pageMap);
	/**
	 * 获取客户应付费用期初合计
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2015年1月20日
	 */
	public Map showCustomerCostPayableInitSum(PageMap pageMap);
	/**
	 * 根据编号获取客户应付费用详情
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2015年1月20日
	 */
	public CustomerCostPayable getCustomerCostPayableByID(@Param("id")String id);
	/**
	 * 修改客户应付费用信息
	 * @param customerCostPayable
	 * @return
	 * @author chenwei 
	 * @date 2015年1月20日
	 */
	public int editCustomerCostPayableInit(CustomerCostPayable customerCostPayable);
	/**
	 * 删除客户应付费用信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2015年1月20日
	 */
	public int deleteCustomerCostPayableByID(String id);

	/**
	 * 根据客户应付费用编号，更新客户应付费用信息
	 * @param customerCostPayable
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年5月15日
	 */
	public int updateCustomerCostPayable(CustomerCostPayable customerCostPayable);


	/**
	 * 根据客户应付费用来源类型，来源单据编号，更新客户应付费用信息
	 * @param customerCostPayable
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年5月15日
	 */
	public int updateCustomerCostPayableBySource(CustomerCostPayable customerCostPayable);
	/**
	 * 根据来源类型，来源编号，获取客户应付条数
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年5月15日
	 */
	public int getCustomerCostPayableCountBySource(Map map);
	/**
	 * 根据MAP中参数获取客户应付费用详情<br/>
	 * MAP中参数：<br/>
	 * billno: 来源单据编号<br/>
	 * oaid: OA编号<br/>
	 * sourcefrom : 来源 ,0手工录入，1代垫， 11：费用冲差支付单 12:通路单 13：客户费用申请单 14：客户费用批量支付申请单<br/>
	 * hcflag : 红冲标志：0普通单据，1红冲,2被红冲的单据<br/>
	 * sourcefromnot : 不与该来源相关，格式为以,分隔的字符串
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2015年1月20日
	 */
	public CustomerCostPayable getCustomerCostPayableByMap(Map map);
	
	/**
	 * 根据MAP中参数删除客户应付费用详情<br/>
	 * MAP中参数：<br/>
	 * billno: 来源单据编号<br/>
	 * sourcefrom : 来源 ,0手工录入，1代垫， 11：费用冲差支付单 12:通路单 13：客户费用申请单 14：客户费用批量支付申请单<br/>
	 * hcflag : 红冲标志：0普通单据，1红冲,2被红冲的单据<br/>
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2015年1月20日
	 */
	public int deleteCustomerCostPayableByMap(Map map);

    /**
     * 根据OA编号删除客户应付费用
     * @param oaid
     * @param sourceform
     * @return
     * @author limin
     * @date Jun 3, 2015
     */
    public int deleteCustomerCostPayableByOa(@Param("oaid")String oaid, @Param("sourceform")String sourceform);

	/**
	 * 更新客户费用金额
	 * @param payable
	 * @return
	 * @author limin
	 * @date Sep 29, 2015
	 */
	public int updateCustomerFee(CustomerCostPayable payable);

	/**
	 * 查询客户费用
	 * @param oaid
	 * @param billtype
	 * @return
	 * @author limin
	 * @date Sep 29 ,2015
	 */
	public CustomerCostPayable selectCustomerFee(@Param("oaid")String oaid, @Param("billtype")String billtype);

	/**
	 * 获取客户费用要生成凭证的数据
	 * @param idlist
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Dec 20, 2017
	 */
	public List getCustomerCostPayableSumData(List idlist);
}
