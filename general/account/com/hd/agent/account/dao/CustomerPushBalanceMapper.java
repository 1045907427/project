package com.hd.agent.account.dao;

import com.hd.agent.account.model.CustomerPushBalance;
import com.hd.agent.common.util.PageMap;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 客户应收款冲差
 * @author chenwei
 */
public interface CustomerPushBalanceMapper {
	/**
	 * 获取客户应收冲差列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 18, 2013
	 */
	public List showCustomerPushBanlanceList(PageMap pageMap);
	/**
	 * 获取客户应收款冲差数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 18, 2013
	 */
	public int showCustomerPushBanlanceCount(PageMap pageMap);
	/**
	 * 获取客户应收款冲差合计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jan 24, 2014
	 */
	public CustomerPushBalance showCustomerPushBanlanceSum(PageMap pageMap);
	/**
	 * 客户应收款冲差添加
	 * @param customerPushBalance
	 * @return
	 * @author chenwei 
	 * @date Jul 18, 2013
	 */
	public int addCustomerPushBanlance(CustomerPushBalance customerPushBalance);
	/**
	 * 获取客户应收款冲差数据
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jul 19, 2013
	 */
	public CustomerPushBalance showCustomerPushBanlanceInfo(@Param("id")String id);
	/**
	 * 客户应收款冲差修改
	 * @param customerPushBalance
	 * @return
	 * @author chenwei 
	 * @date Jul 19, 2013
	 */
	public int editCustomerPushBanlance(CustomerPushBalance customerPushBalance);
	/**
	 * 客户应收款冲差删除
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jul 19, 2013
	 */
	public int deleteCustomerPushBanlance(@Param("id")String id);
	/**
	 * 根据销售发票编号删除客户应收款冲差单
	 * @param invoiceid
	 * @return
	 * @author chenwei 
	 * @date Jul 19, 2013
	 */
	public int deleteCustomerPushBanlanceByInvoiceid(@Param("invoiceid")String invoiceid);
	/**
	 * 客户应收款冲差审核
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date Jul 19, 2013
	 */
	public int auditCustomerPushBanlance(@Param("id")String id,@Param("userid")String userid,@Param("username")String username);
	/**
	 * 客户应收款冲差反审
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date Jul 19, 2013
	 */
	public int oppauditCustomerPushBanlance(@Param("id")String id,@Param("userid")String userid,@Param("username")String username);
	/**
	 * 通过销售发票编号 审核冲差单
	 * @param invoiceid
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date Nov 5, 2013
	 */
	public int auditCustomerPushBanlanceByinvoiceid(@Param("invoiceid")String invoiceid,@Param("userid")String userid,@Param("username")String username);
	/**
	 * 通过销售发票编号 反审冲差单
	 * @param invoiceid
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date Nov 5, 2013
	 */
	public int oppauditCustomerPushBanlanceByinvoiceid(@Param("invoiceid")String invoiceid,@Param("userid")String userid,@Param("username")String username);
	/**
	 * 通过销售发票编号 品牌编号 删除冲差单
	 * @param invoiceid
	 * @param brandid
	 * @return
	 * @author chenwei 
	 * @date Nov 5, 2013
	 */
	public int deleteCustomerPushBanlanceByinvoiceidAndBrandid(@Param("invoiceid")String invoiceid,@Param("brandid")String brandid);
	
	/**
	 * 删除返利相关的冲差单
	 * @param invoiceid
	 * @return
	 * @author chenwei 
	 * @date Nov 23, 2013
	 */
	public int deleteCustomerPushBanlanceByInvoiceIsrebate(@Param("invoiceid")String invoiceid);

	/**
	 * 通过销售发票编号 品牌编号 删除冲差单
	 * @param sourceid
	 * @param brandid
	 * @return
	 * @author chenwei
	 * @date Nov 5, 2013
	 */
	public int deleteCustomerPushBanlanceBySouceid(@Param("sourceid")String sourceid,@Param("brandid")String brandid);

	/**
	 * 删除返利相关的冲差单
	 * @param sourceid
	 * @return
	 * @author chenwei
	 * @date Nov 23, 2013
	 */
	public int deleteCustomerPushBanlanceBySouceidIsrebate(@Param("sourceid")String sourceid);
	/**
	 * 获取发票相关返利的冲差单数量
	 * @param invoiceid
	 * @return
	 * @author chenwei 
	 * @date Nov 23, 2013
	 */
	public int getCustomerPushBanlanceByInvoiceIsrebate(@Param("invoiceid")String invoiceid);

	/**
	 * 根据回单编号关闭核销客户应收款冲差单
	 * @param receiptid
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei
	 * @date Jan 4, 2014
	 */
	public int closeCustomerPushBanlanceByReceiptid(@Param("receiptid")String receiptid,@Param("userid")String userid,@Param("username")String username);

	/**
	 * 反核销 根据回单编号管理客户应收款冲差单状态审核通过
	 * @param receiptid
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 4, 2014
	 */
	public int openCustomerPushBanlanceByReceiptid(@Param("receiptid")String receiptid);

	/**
	 * 根据销售发票编号管理客户应收款冲差单
	 * @param invoiceid
	 * @return
	 * @author chenwei
	 * @date Jul 19, 2013
	 */
	public int closeCustomerPushBanlanceBySourceid(@Param("invoiceid")String invoiceid,@Param("userid")String userid,@Param("username")String username,@Param("writeoffdate") String writeoffdate);

	/**
	 * 反核销 根据销售发票编号管理客户应收款冲差单状态审核通过
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 4, 2014
	 */
	public int openCustomerPushBanlanceByInvoiceid(@Param("invoiceid")String invoiceid);
	/**
	 * 根据编号关闭客户应收款冲差单
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jul 19, 2013
	 */
	public int closeCustomerPushBanlance(@Param("id")String id,@Param("userid")String userid,@Param("username")String username);
	
	/**
	 * 反核销 根据编号管理客户应收款冲差单状态审核通过
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 4, 2014
	 */
	public int openCustomerPushBanlance(@Param("id")String id);
	/**
	 * 核销关闭客户应收款冲差单
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date Jan 17, 2014
	 */
	public int writeoffCustomerPushBanlance(@Param("id")String id,@Param("writeoffamount")BigDecimal writeoffamount,@Param("oldWriteoffamount")BigDecimal oldWriteoffamount,@Param("oldTailamount")BigDecimal oldTailamount,@Param("userid")String userid,@Param("username")String username,@Param("writeoffdate") String writeoffdate);
	/**
	 * 根据客户编号获取已经审核通过的客户应收款冲差单
	 * @param customerid
	 * @return
	 * @author chenwei 
	 * @date Jul 19, 2013
	 */
	public List getCustomerPushBanlanceListByCustomerid(@Param("customerid")String customerid);
	/**
	 * 获取客户应收款冲差
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-10-5
	 */
	public List showCustomerPushBanlanceListBy(Map map);
	/**
	 * 更新打印次数
	 * @param customerPushBalance
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-30
	 */
	public int updateOrderPrinttimes(CustomerPushBalance customerPushBalance);
	/**
	 * 更新冲差单是否关联收款单状态1是0否
	 * @param id
	 * @param isrelate
	 * @return
	 * @author chenwei 
	 * @date Dec 2, 2013
	 */
	public int updateCustomerPushIsrelate(@Param("id")String id,@Param("isrelate")String isrelate);
	/**
	 * 更新冲差单是否关联发票状态1是0否
	 * @param id
	 * @param isrefer
	 * @return
	 * @author chenwei 
	 * @date Jan 25, 2014
	 */
	public int updateCustomerPushIsrefer(@Param("id")String id,@Param("isrefer")String isrefer,@Param("invoicedate") String invoicedate);

	/**
	 * 清空冲差单发票开票状态
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Feb 21, 2014
	 */
	public int updateCustomerPushIsreferClear(@Param("id")String id);
	/**
	 * 更新冲差单是否关联发票状态1是0否
	 * @param invoiceid
	 * @return
	 * @author chenwei 
	 * @date Jan 25, 2014
	 */
	public int updateCustomerPushBanlanceIsreferByInvoiceid(@Param("invoiceid")String invoiceid);
	/**
	 * 根据冲差单编号 更新是否关联发票状态
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Mar 1, 2014
	 */
	public int updateCustomerPushBanlanceIsreferByID(@Param("id")String id);
	
	/**
	 * 统计客户应收冲差
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-1-25
	 */
	public int getCustomerPushBanlanceCountBy(Map map) throws Exception;	
	/**
	 * 更新正常冲差单是否关联发票状态1是0否
	 * @param invoiceid
	 * @param brandid
	 * @return
	 * @author chenwei 
	 * @date Jan 26, 2014
	 */  
	public int updateCustomerPushBanlanceIsReferByinvoiceidAndBrandid(@Param("invoiceid")String invoiceid,@Param("brandid")String brandid);
	
	/**
	 * 通过OA编号删除客户应收款冲差单
	 * @param oaid			OA编号
	 * @return
	 * @author chenwei 
	 * @date 2014年10月24日
	 */
	public int deleteCustomerPushBanlanceByOA(@Param("oaid")String oaid);
	
	/**
	 * 回写冲差单是否生成交接单
	 * @param id
	 * @param ishand
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 31, 2015
	 */
	public int updateCustomerPushBalanceIsHand(@Param("id")String id,@Param("ishand")String ishand);

    /**
     * 通过OA编号查询客户应收款冲差单
     * @param oaid
     * @return
     * @author limin
     * @date 2015年4月8日
     */
    public List selectCustomerPushBanlanceByOaid(@Param("oaid")String oaid);

    /**
     * 根据编号冲差单中的发票号
     * @param id
     * @return
     */
    public int clearCustomerPushBanlanceInvoiceidByID(@Param("id")String id);
	
	/**
	 * 更新冲差单是否关联发票状态1是0否(是否开票)
	 * @param id
	 * @param isinvoicebill
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 14, 2015
	 */
	public int updateCustomerPushIsreferBill(@Param("id")String id,@Param("isinvoicebill")String isinvoicebill);
	
	/**
	 * 根据冲差单编号 更新是否实际开票 （申请开票）
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 16, 2015
	 */
	public int updateCustomerPushBanlanceIsinvoicebillByID(@Param("id")String id);
	
	/**
	 * 更新正常冲差单是否实际开票1是0否(申请开票)
	 * @param invoiceid
	 * @param brandid
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 15, 2015
	 */
	public int updateCustomerPushBanlanceIsinvoicebillByinvoiceidAndBrandid(@Param("invoiceid")String invoiceid,@Param("brandid")String brandid);

	/**
	 * 更新开票时间
	 * @param map
	 * @return
	 */
	public int updateCustomerPushBanlanceInvoicedate(Map map);
    /**
     * 根据条件获取客户冲差单的报表数据
     * @return
     * @author lin_xx
     * @data Aug 2,2016
     */
    public List getPushBalanceReportData(PageMap pageMap);
	 /**
	  * 获取客户及业务员冲差报表  数据
	  * @author lin_xx
	  * @date 2016/12/24
	  */
	public List<Map> getCustomerAndUserPushData(PageMap pageMap);
	/**
	 * 获取客户及业务员冲差报表合计
	 * @author lin_xx
	 * @date 2016/12/24
	 */
	public int getCustomerAndUserPushDataCount(PageMap pageMap);
	 /**
	  * 获取客户及业务员冲差报表 页脚
	  * @author lin_xx
	  * @date 2016/12/26
	  */
	 public Map getCustomerAndUserPushDataSum(PageMap pageMap);
    /**
     * 根据条件获取客户冲差单的报表数据 数量
     * @return
     * @author lin_xx
     * @data Aug 2,2016
     */
    public int getPushBalanceReportDataCount(PageMap pageMap);
    /**
     * 根据条件获取客户冲差单的报表数据 合计
     * @return
     * @author lin_xx
     * @data Aug 2,2016
     */
    public CustomerPushBalance getPushBalanceReportSum(PageMap pageMap);


	/**
	 * 根据发货回单编号回写回单冲差单是否开票标记
	 * @param receiptid
	 * @param isinvoicebill
	 * @return
	 * @author panxiaoxiao
	 * @data 2016-12-16
	 */
	public int updateReceiptCustomerPushBalanceIsinvoicebill(@Param("receiptid")String receiptid,@Param("isinvoicebill")String isinvoicebill);

	/**
	 * 根据发货回单编号回写回单冲差单开票日期
	 * @param receiptid
	 * @param isinvoicebill
	 * @return
	 * @author panxiaoxiao
	 * @data 2016-12-16
	 */
	public int updateReceiptCustomerPushBalanceInvoicebilldate(@Param("receiptid")String receiptid, @Param("isinvoicebill")String isinvoicebill,@Param("invoicebilldate") String invoicebilldate);
}
