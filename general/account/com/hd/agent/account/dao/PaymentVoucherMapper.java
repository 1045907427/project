package com.hd.agent.account.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.account.model.PaymentVoucher;
import com.hd.agent.account.model.PaymentVoucherDetail;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * @author zhanghanghui
 */
public interface PaymentVoucherMapper {
	/**
	 * 交款单分页列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public List getPaymentVoucherPageList(PageMap pageMap);
	/**
	 * 交款单分页条数统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public int getPaymentVoucherPageCount(PageMap pageMap);
	/**
	 * 交款单分页合计金额统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public PaymentVoucher getPaymentVoucherPageSum(PageMap pageMap);
	/**
	 * 新增交款单
	 * @param paymentVoucher
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public int insertPaymentVoucher(PaymentVoucher paymentVoucher);
	/**
	 * 更新交款单
	 * @param paymentVoucher
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public int updatePaymentVoucher(PaymentVoucher paymentVoucher);
	/**
	 * 根据ID编号获取交款单
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public PaymentVoucher getPaymentVoucher(@Param("id")String id);
	/**
	 * map中的参数<br/>
	  * 条件参数：<br/>
	 * id : 编号
	 * authDataSql ： 数据权限参数<br/>
	 * cols: 字段权限<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月25日
	 */
	public PaymentVoucher getPaymentVoucherBy(Map map);
	/**
	 * 根据ID编号删除交款单
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public int deletePaymentVoucher(@Param("id")String id);
	/**
	 * 根据交款单编号，交款单明细
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public List getPaymentVoucherDetailList(@Param("orderid")String orderid);
	/**
	 * 根据ID编号，删除交款单明细
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public int deletePaymentVoucherDetail(@Param("id")String id);
	/**
	 * 根据交款单编号，删除交款单明细
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public int deletePaymentVoucherDetailByOrderid(@Param("orderid")String orderid);
	/**
	 * 新增交款单明细
	 * @param paymentVoucherDetail
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public int insertPaymentVoucherDetail(PaymentVoucherDetail paymentVoucherDetail);
	/**
	 * 更新交款单明细
	 * @param paymentVoucherDetail
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public int updatePaymentVoucherDetail(PaymentVoucherDetail paymentVoucherDetail);
	
	/**
	 * 更新交款单状态信息（审核、关闭、中止）<br/>
	 * 审核时更新字段<br/>
	 * status : 3审核、2反审为保存<br/>
	 * audituserid : 审核用户编号<br/>
	 * auditusername ： 审核用户姓名<br/>
	 * audittime : 审核时间<br/>
	 * 中止时更新字段<br/>
	 * status : 5 <br/>
	 * stopuserid : 中止用户编号<br/>
	 * stopusername : 中止用户姓名<br/>
	 * stoptime : 中止时间<br/>
	 * 关闭时更新字段<br/>
	 * status : 4 <br/>
	 * closetime : 关闭时间<br/>
	 * @param plannedOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-10
	 */
	public int updatePaymentVoucherStatus(PaymentVoucher paymentVoucher);
	
	/**
	 * 交款单明细查询统计<br/>
	 * map中参数<br/>
	 * orderid :　交款单编号<br/>
	 * authDataSql  ：数据权限<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-20
	 */
	public int getPaymentVoucherDetailCountBy(Map map);
	/**
	 * 更新交款单金额<br/>
	 * map中参数<br/>
	 * id : 编号 <br/>
	 * totalamount : 总金额<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月26日
	 */
	public int updatePaymentVoucherAmount(Map map);
	
	/**
	 * 更新打印次数
	 * @param id
	 * @param printtimes
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-30
	 */
	public int updateOrderPrinttimes(PaymentVoucher arrivalOrder);
    /**
     * 更新打印次数
     * @return
     * @author lin_xx
     * @date 2016-8-30
     */
    public int updateOrderByStatus(@Param("idsArr")String[] idsArr);
	/**
	 * 获取交款单列表<br/>
	 * map中参数:<br/>
	 * dataSql：数据权限<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * status: 表示状态3<br/>
	 * statusarr: 表示状态，类似 1,2,3<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-10-16
	 */
	public List showPaymentVoucherListBy(Map map);
	/**
	 * 交款单明细查询统计<br/>
	 * map中参数<br/>
	 * id :　交款单编号<br/>
	 * notAudit :　未审核<br/>
	 * authDataSql  ：数据权限<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-20
	 */
	public int deletePaymentVoucherBy(Map map);

    /**
     * 判断手机上传的交款单是否存在
     * @param billid
     * @return
     */
    public int hasPhoneBill(@Param("billid")String billid);

    /**
     * 根据手机上传的交款单编号 获取单据编号
     * @param billid
     * @return
     */
    public String getBillIDByPhoneBillid(@Param("billid")String billid);

    /**
     * 交款单分页列表
     * @param pageMap
     * @return
     * @author zhanghonghui
     * @date 2015年3月24日
     */
    public List getPaymentVoucherListForPhone(PageMap pageMap);
    /**
     * 交款单审核通过状态数据
     * @return
     * @author lin_xx
     * @date 2016年8月2日
     */
    public List<PaymentVoucher> getPaymentVoucherListByAudit();

}
