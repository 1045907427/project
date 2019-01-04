package com.hd.agent.storage.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.Saleout;
import com.hd.agent.storage.model.SaleoutDetail;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 销售出库单dao
 * @author chenwei
 */
public interface SaleoutMapper {
	/**
	 * 添加销售出库明细
	 * @param saleoutDetail
	 * @return
	 * @author chenwei 
	 * @date Jun 4, 2013
	 */
	public int addSaleOutDetail(SaleoutDetail saleoutDetail);
	/**
	 * 根据销售出库明细列表添加数据
	 * @param list
	 * @return
	 * @author chenwei 
	 * @date Dec 5, 2013
	 */
	public int addSaleOutDetailList(@Param("list")List list);
	/**
	 * 添加销售出库单
	 * @param saleout
	 * @return
	 * @author chenwei 
	 * @date Jun 4, 2013
	 */
	public int addSaleOut(Saleout saleout);
	/**
	 * 获取销售出库单列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jun 5, 2013
	 */
	public List showSaleOutList(PageMap pageMap);
	/**
	 * 获取销售出库单数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jun 5, 2013
	 */
	public int showSaleOutCount(PageMap pageMap);
	
	/**
	 * 获取发货单列表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 12, 2014
	 */
	public List getSaleOutListForBigSaleOut(PageMap pageMap);
	
	/**
	 * 获取发货单列表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 12, 2014
	 */
	public int getSaleOutCountForBigSaleOut(PageMap pageMap);
	
	/**
	 * 获取销售出库单基本信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 5, 2013
	 */
	public Saleout getSaleOutInfo(@Param("id")String id);

	/**
	 * 获取生成未税凭证数据
	 * @throws
	 * @author lin_xx
	 * @date 2018-01-30
	 */
	public List<Map> getSaleOutSumData(List<String> ids);


    /**
     * 获取销售出库单基本信息（数据权限）
     * @param id
     * @param dataSql
     * @return
     * @author chenwei
     * @date Jun 5, 2013
     */
    public Saleout getSaleOutInfoWithDataSql(@Param("id")String id,@Param("dataSql") String dataSql);

	/**
	 * 根据仓库编号和来源单据编号获取发货单信息
	 * @param sourceid
	 * @param storageid
	 * @return
	 * @author chenwei 
	 * @date Jul 11, 2013
	 */
	public Saleout getSaleOutInfoByStorageidAndSourceid(@Param("sourceid")String sourceid,@Param("storageid")String storageid);
	/**
	 * 通过来源单据编号获取销售出库单基本信息
	 * @param sourceid
	 * @return
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	public List getSaleOutInfoBySourceid(@Param("sourceid")String sourceid);
	/**
	 * 通过销售订单编号获取销售订单列表
	 * @param saleorderid
	 * @return
	 * @author chenwei 
	 * @date Nov 11, 2013
	 */
	public List getSaleOutInfoBySaleorderid(@Param("saleorderid")String saleorderid);
	/**
	 * 获取销售出库单明细列表
	 * @param saleoutid
	 * @return
	 * @author chenwei 
	 * @date Jun 5, 2013
	 */
	public List getSaleOutDetailList(@Param("saleoutid")String saleoutid);
	/**
	 * 获取销售出库单商品合计明细列表
	 * @param saleoutid
	 * @return
	 * @author chenwei 
	 * @date 2015年1月28日
	 */
	public List getSaleOutDetailGoodsSumList(@Param("saleoutid")String saleoutid);
	/**
	 * 获取销售出库单明细列表 （合计品牌折扣）
	 * @param saleoutid
	 * @return
	 * @author chenwei 
	 * @date Dec 17, 2013
	 */
	public List getSaleOutDetailListSumDiscount(@Param("saleoutid")String saleoutid);
	/**
	 * 获取销售出库单明细排序列表（合计）<br/>
	 * map中字段:<br/>
	 * saleoutid 出库单编号<br/>
	 * showShopid ：当值为true，关联出店内码，目前正大特别要求<br/> 
	 * order 排序<br/>
	 * @param map
	 * @return
	 * @author chenwei 
	 * @date Sep 24, 2013
	 */
	public List getSaleOutDetailListByOrder(Map map);
	/**
	 * 根据销售订单号获取销售出库单明细列表
	 * @param saleorderid
	 * @return
	 * @author chenwei 
	 * @date Jun 5, 2013
	 */
	public List getSaleOutDetailListBySaleorder(@Param("saleorderid")String saleorderid);
	
	/**
	 * 获取先手出库单明细列表 根据销售发货通知单编号和明显编号分组合计
	 * @param saleoutid
	 * @return
	 * @author chenwei 
	 * @date Jun 8, 2013
	 */
	public List getSaleOutDetailSumList(@Param("saleoutid")String saleoutid);
	/**
	 * 删除销售出库单明细
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	public int deleteSaleOutDetail(@Param("id")String id,@Param("saleoutid")String saleoutid);
	/**
	 * 根据销售出库单编号 删除明细
	 * @param saleoutid
	 * @return
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	public int deleteSaleOutDetailBySaleoutid(@Param("saleoutid")String saleoutid);
	/**
	 * 根据销售出库单编号 删除数量为0的明细
	 * @param saleoutid
	 * @return
	 * @author chenwei 
	 * @date Jan 6, 2014
	 */
	public int deleteSaleOutDetailZeroBySaleoutid(@Param("saleoutid")String saleoutid);
	/**
	 * 删除销售出库单
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	public int deleteSaleOut(@Param("id")String id);
	/**
	 * 修改销售出库单信息
	 * @param saleout
	 * @return
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	public int editSaleOut(Saleout saleout);
	/**
	 * 根据编号获取销售出库单明细的详细信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 7, 2013
	 */
	public SaleoutDetail getSaleoutDetailById(@Param("id")String id);
	
	/**
	 * 根据销售发货通知单明细编号获取销售出库单中的数量
	 * @param dispatchbillid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 7, 2013
	 */
	public BigDecimal getSaleOutNumByDispatchBillDetailID(@Param("dispatchbillid")String dispatchbillid,@Param("dispatchbilldetailid")String dispatchbilldetailid) throws Exception;
	/**
	 * 审核关闭发货单
	 * @param id
	 * @param isrefer
	 * @param userid
	 * @param username
	 * @param duefromdate
	 * @return
	 * @author chenwei 
	 * @date 2014年5月12日
	 */
	public int auditSaleOutClose(@Param("id")String id,@Param("isrefer")String isrefer,@Param("userid")String userid,@Param("username")String username,@Param("duefromdate")String duefromdate,@Param("businessdate") String businessdate);
	/**
	 * 审核销售出库单
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date Jun 8, 2013
	 */
	public int auditSaleOut(@Param("id")String id,@Param("isrefer")String isrefer,@Param("userid")String userid,@Param("username")String username,@Param("duefromdate")String duefromdate,@Param("businessdate") String businessdate);
	/**
	 * 反审销售出库单
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date Jun 8, 2013
	 */
	public int oppauditSaleOut(@Param("id")String id,@Param("userid")String userid,@Param("username")String username);
	/**
	 * 更新销售出库单参照状态
	 * @param saleorderid
	 * @param isrefer
	 * @return
	 * @author chenwei 
	 * @date Jun 8, 2013
	 */
	public int updateSaleoutRefer(@Param("saleorderid")String saleorderid,@Param("isrefer")String isrefer);
	/**
	 * 根据发货单编号 更新参照状态
	 * @param id
	 * @param isrefer
	 * @return
	 * @author chenwei 
	 * @date Nov 9, 2013
	 */
	public int updateSaleoutReferByid(@Param("id")String id,@Param("isrefer")String isrefer);
	/**
	 * 关闭销售出库单
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 8, 2013
	 */
	public int updateSaleOutClose(@Param("id")String id);
	/**
	 * 回单核销后，根据回单编号 修改销售出库单核销状态
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Sep 10, 2013
	 */
	public int updateSaleOutWriteByReceipt(@Param("id")String id);
	/**
	 * 打开销售出库单 状态变为审核状态
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 8, 2013
	 */
	public int updateSaleOutOpen(@Param("id")String id);

    /**
     * 修改发货单明细的数量与金额
     * @param saleoutDetail
     * @return
     */
    public int updateSaleoutDetailNumAndAmount(SaleoutDetail saleoutDetail);

	/**
	 * 回写更新销售出库单明细信息
	 * @param saleoutDetail
	 * @return
	 * @author chenwei 
	 * @date Jun 8, 2013
	 */
	public int updateSaleoutDetailBack(SaleoutDetail saleoutDetail);
	/**
	 * 清除销售出库单明细回写
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 8, 2013
	 */
	public int updateClearSaleoutDetailBack(@Param("id")String id);
	/**
	 * 更新销售出库单 是否核销状态
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Oct 11, 2013
	 */
	public int updateSaleOutDetailIswriteoff(@Param("id")String id,@Param("writeoffdate") String writeoffdate);
	
	/**
	 * 更新销售出库单 是否核销状态(申请开票)
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 25, 2015
	 */
	public int updateSaleOutDetailIswriteoffInvoiceBill(@Param("id")String id);
	
	/**
	 * 反核销，更新销售出库单 不为核销状态
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 4, 2014
	 */
	public int updateSaleOutDetailBackWriteoff(@Param("id")String id);
	
	/**
	 * 反核销，更新销售出库单 不为核销状态(申请开票)
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 25, 2015
	 */
	public int updateSaleOutDetailBackWriteoffInvoiceBill(@Param("id")String id);
	/**
	 * 更新销售出库单 是否开票状态
	 * @param id
	 * @param isinvoice
	 * @return
	 * @author chenwei 
	 * @date Oct 11, 2013
	 */
	public int updateSaleOutDetailIsinvoice(@Param("id")String id,@Param("isinvoice")String isinvoice,@Param("invoicedate") String invoicedate);
	
	/**
	 * 更新销售出库单 是否实际开票1是0否(申请开票)
	 * @param id
	 * @param isinvoicebill
	 * @return
	 * @author panxiaoxiao
	 * @date Feb 14, 2015
	 */
	public int updateSaleOutDetailIsinvoicebill(@Param("id")String id,@Param("isinvoicebill")String isinvoicebill,@Param("invoicebilldate") String invoicebilldate);
	/**
	 * 更新打印次数
	 * @param saleout
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-4
	 */
	public int updateSaleoutPrinttimes(Saleout saleout);
	/**
	 * 更新打印次数，数据自动加
	 * map中参数：<br/>
	 * idarrs : 字符串，例如：1,2,3<br/>
	 * saleorderid : 销售订单<br/>
	 * sourceid : 来源编号<br/>
	 * phprinttimes : 配货 更新字段<br/>
	 * printtimes : 发货 更新字段<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-1-8
	 */
	public int updateSaleoutPrinttimesBy(Map map);

	/**
	 * 获取销售出库单列表
	 * map中参数：<br/>
	 * dataSql : 数据权限<br/>
	 * showdetail :  1显示明细列表<br/>
	 * idarrs : 字符串组，类似1,2,3<br/>
	 * phprintstatus : 配货打印<br/>
	 * ckprintstatus : 出库打印<br/>
	 * status :  状态<br/> 
	 * statusarr : 字符串组，类似1,2,3<br/>
	 * saleidarrs : 销售订单字符串组<br/>
	 * saleorderid : 销售订单<br/>
	 * sourceidarrs : 来源编号 字符串组<br/>
	 * sourceid : 来源编号 
	 * @param map
	 * @return
	 * @author chenwei 
	 * @date Jun 5, 2013
	 */
	public List showSaleOutListBy(Map map);
	/**
	 * 根据销售订单编号 获取未审核的发货单数量
	 * @param saleorderid
	 * @return
	 * @author chenwei 
	 * @date Nov 9, 2013
	 */
	public int getUnauditSaleOutCountBySaleorderid(@Param("saleorderid")String saleorderid);
	/**
	 * 根据销售订单编号 获取发货单列表
	 * @param saleorderid
	 * @return
	 * @author chenwei 
	 * @date Nov 9, 2013
	 */
	public List getSaleOutListBySaleorderid(@Param("saleorderid")String saleorderid,@Param("status")String status);
	/**
	 * 获取发货单的发货与发货出库总金额
	 * @param saleoutid
	 * @return
	 * @author chenwei 
	 * @date Nov 19, 2013
	 */
	public SaleoutDetail getSaleOutSumAmountBySaleoutid(@Param("saleoutid")String saleoutid);
	/**
	 * 获取发货单的开票总金额
	 * @param saleoutid
	 * @return
	 * @author chenwei 
	 * @date Nov 19, 2013
	 */
	public SaleoutDetail getSaleOutSumInvoiceAmountBySaleoutid(@Param("saleoutid")String saleoutid);
	
	/**
	 * 获取发货单的开票总金额(申请开票)
	 * @param saleoutid
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 14, 2015
	 */
	public SaleoutDetail getSaleOutSumInvoicebillAmountBySaleoutid(@Param("saleoutid")String saleoutid);
	/**
	 * 获取发货单的核销金额
	 * @param saleoutid
	 * @return
	 * @author chenwei 
	 * @date Nov 19, 2013
	 */
	public SaleoutDetail getSaleOutSumWriteoffAmountBySaleoutid(@Param("saleoutid")String saleoutid);
	
	/**
	 * 获取发货单的未核销金额
	 * @param saleoutid
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 6, 2014
	 */
	public SaleoutDetail getSaleOutSumUNWriteoffAmountBySaleoutid(@Param("saleoutid")String saleoutid);
	/**
	 * 根据回单编号 获取发货单列表
	 * @param receiptid
	 * @return
	 * @author chenwei 
	 * @date Nov 19, 2013
	 */
	public List getSaleOutListByReceiptid(@Param("receiptid")String receiptid);
	/**
	 * 根据回单编号 获取销售发票编号列表
	 * @param receiptid
	 * @return
	 * @author chenwei 
	 * @date Nov 19, 2013
	 */
	public List getSaleInvoiceListByReceiptid(@Param("receiptid")String receiptid);
	
	/**
	 * 根据回单编号 获取销售发票编号列表(申请开票)
	 * @param receiptid
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 14, 2015
	 */
	public List getSaleInvoiceBillListByReceiptid(@Param("receiptid")String receiptid);
	
	//===========================================================================================
	// 打印块开始
	//===========================================================================================
	/**
	 * 获取销售出库单打印明细列表<br/>
	 * map中参数：<br/>
	 * status : 状态<br/>
	 * statusarr : 状态字符串数组<br/>
	 * dispatchbillid : 销售发货通知单<br/>
	 * saleorderid : 销售订单<br/>
	 * notphprint : 配货是否未打印，1未打印<br/>
	 * notprint : 发货是否未打印，1未打印<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date Jun 5, 2013
	 */
	public List getSaleOutDetailPrintListBy(Map map);
	/**
	 * 根据发货通知单或销售编号获取发货单打印统计<br/>
	 * map中参数：<br/>
	 * printtype :显示类型,1配货，其他发货 统计<br/>
	 * saleorderid : 销售订单<br/>
	 * sourceid : 上游单据<br/>
	 * 返回参数：<br/>
	 * unprint : 未打印条数<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-1-4
	 */
	public Map getSaleOutPrintCountInfoBy(Map map);
	/**
	 * 根据发货通知单编号获取发货通知单打印统计<br/>
	 * map中参数：<br/>
	 * sourceid : 销售订单<br/>
	 * id : 通知单编号<br/>
	 * 返回参数：<br/>
	 * phprinttimes : 配货打印条数<br/>
	 * printtimes : 发货打印条数<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-1-4
	 */
	public Map getDispatchBillPrintCountInfoBy(Map map);
	/**
	 * 同步销售发货通知单打印次数
	 * map中参数：<br/>
	 * printtype :显示类型,1配货次数为1,2发货次数为2,3配货发货次数为1,4配货次数加1,5发货次数加1,6配货发货次数加1<br/>
	 * sourceid : 销售订单<br/>
	 * id : 通知单编号<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-1-5
	 */
	public int updateSyncDispatchBillPrinttimes(Map map);
	/**
	 * 根据销售单编号获取销售单打印统计<br/>
	 * map中参数：<br/>
	 * id : 销售订单<br/>
	 * 返回参数：<br/>
	 * phprinttimes : 配货打印条数<br/>
	 * printtimes : 发货打印条数<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-1-4
	 */
	public Map getSalesOrderPrintCountInfoBy(Map map);
	/**
	 * 同步销售单打印次数
	 * map中参数：<br/>
	 * printtype :显示类型,1配货次数为1,2发货次数为2,3配货发货次数为1,4配货次数加1,5发货次数加1,6配货发货次数加1<br/>
	 * id :  销售单编号<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-1-5
	 */
	public int updateSyncSalesOrderPrinttimes(Map map);	
	//===========================================================================================
	// 打印块结束
	//===========================================================================================
	/**
	 * 根据商品编码 获取发货单中该商品未发货的信息
	 * @param goodsid
	 * @return
	 * @author chenwei 
	 * @date Jan 16, 2014
	 */
	public List showGoodsWaitSaleListData(@Param("goodsid")String goodsid,@Param("storageid")String storageid,@Param("summarybatchid")String summarybatchid);
	/**
	 * 根据客户编号获取未核销的发货单列表
	 * @param customerid
	 * @return
	 * @author chenwei 
	 * @date Mar 22, 2014
	 */
	public List getSaleOutNoWriteoffListByCustomerid(@Param("customerid")String customerid);
	
	/**
	 * 更新发货单应收日期
	 * @param id
	 * @param duefromdate
	 * @return
	 * @author chenwei 
	 * @date Mar 22, 2014
	 */
	public int updateSaleOutDuefromdate(@Param("id")String id,@Param("duefromdate")String duefromdate);
	/**
	 * 根据订单编号更新发货单应收日期
	 * @param orderid
     * @param brandid
	 * @param duefromdate
	 * @return
	 * @author chenwei 
	 * @date Mar 22, 2014
	 */
	public int updateSaleOutDuefromdateByOrderid(@Param("orderid")String orderid,@Param("brandid")String brandid,@Param("duefromdate")String duefromdate);
	/**
	 * 更新回单的应收日期
	 * @param orderid
	 * @param duefromdate
	 * @return
	 * @author chenwei 
	 * @date Mar 22, 2014
	 */
	public int updateReceiptDuefromdateByOrderid(@Param("orderid")String orderid,@Param("duefromdate")String duefromdate);
	/**
	 * 根据订单编号删除发货单
	 * @param orderid
	 * @return
	 * @author chenwei 
	 * @date Apr 17, 2014
	 */
	public int deleteSaleOutByOrderid(@Param("orderid")String orderid);
	
	/**
	 * 根据订单编号删除发货单明细
	 * @param orderid
	 * @return
	 * @author chenwei 
	 * @date Apr 17, 2014
	 */
	public int deleteSaleOutDetailByOrderid(@Param("orderid")String orderid);
	/**
	 * 获取未核对的发货单列表
	 * @param storageid
	 * @param date
     * @param status
	 * @return
	 * @author chenwei 
	 * @date 2014年6月30日
	 */
	public List getSaleoutUnCheckList(@Param("storageid")String storageid,@Param("date")String date,@Param("status")String status);

    /**
     * 给扫描枪提供获取发货单列表
     * @param map
     * @return
     */
    public List getSaleoutForScanList(Map map);
	/**
	 * 根据发货单编号 获取不包括折扣的发货单明细列表
	 * @param billid
	 * @return
	 * @author chenwei 
	 * @date 2014年6月30日
	 */
	public List getSaleoutDetailWithoutDiscount(@Param("billid")String billid);
	/**
	 * 根据发货单编号 更新发货单核对确认状态
	 * @param id
	 * @param checkuseid
	 * @param checkusername
	 * @return
	 * @author chenwei 
	 * @date 2014年6月30日
	 */
	public int updateSaleoutCheckFlag(@Param("id")String id,@Param("checkuserid")String checkuseid,@Param("checkusername")String checkusername);
    /**
     * 根据发货单编号 清除发货单核对确认状态
     * @param id
     * @return
     * @author chenwei
     * @date 2014年6月30日
     */
    public int updateSaleoutCheckClearFlag(@Param("id")String id);
	
	/**
	 * 修改是否大单发货
	 * @param isbigsaleout
     * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 15, 2014
	 */
	public int updateIsBigSaleOut(@Param("isbigsaleout")String isbigsaleout,@Param("id")String id);
	
	/**
	 * 根据客户编号和商品编码 获取历史交易信息(一年内的数据)
	 * @param customerid
	 * @param goodsid
	 * @return
	 * @author chenwei 
	 * @date 2014年10月6日
	 */
	public List getCustomerHisGoodsSalesList(@Param("customerid")String customerid,@Param("goodsid")String goodsid);
	
	/**
	 * 清除发货单中的发票号
	 * @param receiptid
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 24, 2014
	 */
	public int clearSaleoutInvoiceidByReceiptid(@Param("receiptid")String receiptid);

    /**
     * 获取预开票所需的审核通过的发货单数据
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015/03/11
     */
    public List<Map> getSaleoutListForAdvanceBill(PageMap pageMap);

    /**
     * 获取预开票所需的审核通过的发货单数据
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015/03/11
     */
    public int getSaleoutListForAdvanceBillCount(PageMap pageMap);

    /**
     * 获取预开票所需的审核通过的发货单数据
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015/03/11
     */
    public Map getSaleoutListForAdvanceBillSum(PageMap pageMap);
    /**
     * 根据单据编号和明细编号，获取发货单明细详情
     * @param id
     * @param saleoutid
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015/03/11
     */
    public SaleoutDetail getSaleoutDetailByBillidAndId(@Param("id")String id, @Param("saleoutid")String saleoutid);
    /**
     * 根据发货单编号和品牌编号 获取发货单中品牌折扣的明细列表
     * @param saleoutid
     * @param brandid
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-12
     */
    public List getSaleoutDetailBrandDiscountList(@Param("saleoutid")String saleoutid, @Param("brandid")String brandid);
    /**
     * 根据销售发票编号 更新发货单 修改是否实际开票1是0否
     * @param id
     * @param isinvoicebill
     * @return
     * @author panxiaoxiao
     * @date 2015-03-12
     */
    public int updateSaleoutDetailIsinvoicebillAdvance(@Param("id")String id,@Param("isinvoicebill")String isinvoicebill,@Param("invoicebilldate") String invoicebilldate);
    /**
     * 更新发货单申请开票发票状态 (申请开票)
     * @param isinvoicebill 0未开票1已开票3可以生成发票4开票中
     * @param id
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-12
     */
    public int updateSaleoutInvoicebill(@Param("isinvoicebill")String isinvoicebill,@Param("id")String id);

    /**
     * 获取发货单明细合计
     * @param id
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-12
     */
    public Map getSaleoutDetailTotal(String id);

    /**
     * 根据发货单编码集获取最小配送单状态
     * @param map
     * @return
     * @author panxiaoxiao
     * @date 2015-04-22
     */
    public String getIsDeliveryByReceiptSourceids(Map map);

    /**
     * 根据客户编码、商品编码显示历史价格表数据页面
     * @return
     * @throws Exception
     * @author pan_xx
     * @date 2015年6月16日
     */
    public List<Map> getRejectBillHistoryGoodsPriceList(Map map);
    /**
     * 根据MAP中参数查询 发货单明细 统计数<br/>
     * map中参数：<br/>
     * saleoutid : 发货单编号<br/>
     * brandarrs: 根据品牌编号字符串，以,分隔<br/>
     * groupby: 是否分组查询,当对brandid 进分<br/>
     * @param map
     * @return
     * @author zhanghonghui 
     * @date 2015年8月4日
     */
    public int getSaleOutDetailCountBy(Map map);
    /**
     * 根据Map中参数查询 发货单明细 关联 发货单 统计发货单明细数<br/>
     * map 中参数：<br/>
     * saleoutid : 发货单编号<br/>
     * brandarrs: 根据品牌编号字符串，以,分隔<br/>
     * saleorderid: 销售单编号<br/>
     * dispatchbillid : 销售通知单编号<br/>
     * groupby: 是否分组查询,当对brandid 进分<br/>
     * @param map
     * @return
     * @author zhanghonghui 
     * @date 2015年8月6日
     */
    public int getSaleOutRightJoinDetailCountBy(Map map);
    /**
     * 根据编号获取进货单，打印次数，上游单据、仓库编号
     * @param id
     * @return
     * @author zhanghonghui 
     * @date 2015年8月5日
     */
    public Saleout getSaloutPrinttimesById(@Param("id")String id);
    
    /**
     * 根据MAP中参数查询 发货单明细  品牌列表<br/>
     * map中参数：<br/>
     * saleoutid : 发货单编号<br/>
     * brandarrs: 根据品牌编号字符串，以,分隔<br/>
     * groupby : group by 的字段,必填,必须detail里有<br/>
     * @param map
     * @return
     * @author zhanghonghui 
     * @date 2015年8月4日
     */
    public List getSaleOutDetailListGroupBy(Map map);
    /**
     * 根据Map中参数查询 发货单明细 关联 发货单 统计发货单 品牌列表<br/>
     * map 中参数：<br/>
     * saleoutid : 发货单编号<br/>
     * brandarrs: 根据品牌编号字符串，以,分隔<br/>
     * saleorderid: 销售单编号<br/>
     * dispatchbillid : 销售通知单编号<br/>
     * groupby : group by 的字段,必填,,必须detail里有<br/>
     * @param map
     * @return
     * @author zhanghonghui 
     * @date 2015年8月6日
     */
    public List getSaleOutRightJoinDetailListGroupBy(Map map);

    /**
     * 多少天之内或多少月之内的最低销售价
     * @param customerid
     * @param goodsid
     * @param lowestdate
     * @return
     */
    public BigDecimal getSaleOutLowestPrice(@Param("customerid")String customerid, @Param("goodsid")String goodsid, @Param("lowestdate")String lowestdate);

    /**
     * 最近一次销售价（交易价）
     * @param customerid
     * @param goodsid
     * @return
     */
    public BigDecimal getSaleOutLastPrice(@Param("customerid")String customerid, @Param("goodsid")String goodsid);

    /**
     * 根据发货单编号 获取发货单商品品牌列表
     * @param id
     * @return
     */
    public List getSaleOutBrandListById(@Param("id")String id);

    /**
     * 根据发货单编号和品牌编号 更新应收日期
     * @param id
     * @param brandid
     * @param duefromdate
     * @return
     */
    public int updateSaleOutDetailDuefromdate(@Param("id")String id,@Param("brandid")String brandid,@Param("duefromdate")String duefromdate);

    /**
     * 更新回单明细的应收日期
     * @param orderid
     * @param duefromdate
     * @return
     * @author chenwei
     * @date Mar 22, 2014
     */
    public int updateReceiptDetailDuefromdateByOrderid(@Param("orderid")String orderid,@Param("brandid")String brandid,@Param("duefromdate")String duefromdate);

    /**
     * 根据订单编号 品牌编号 更新应收日期
     * @param orderid
     * @param brandid
     * @param duefromdate
     * @return
     */
    public int  updateSaleOutDetailDuefromdateByOrderid(@Param("orderid")String orderid,@Param("brandid")String brandid,@Param("duefromdate")String duefromdate);

    /**
     * 更新发货单的成本价
     * @param id
     * @param isStorageAccount
     * @return
     */
    public int updateSaleOutDetailCostprice(@Param("id")String id,@Param("isStorageAccount")String isStorageAccount);
    
    /**
	 * 修改发货单发货人
	 * @param id,storager
	 * @return
	 * @author wanghongteng 
	 * @date 2016-4-6
	 */
	public int editDispatchUser(@Param("id")String id,@Param("storager")String storager);

	/**
	 * 根据回单编号修改回单开票状态
	 * @param billid
	 * @return
	 * @author pan_xx
	 * @date 2016-05-26
	 */
	public int updateSaleoutIsvoicebillByBillid(@Param("billid")String billid);

	/**
	 * 根据发货通知单编号判断发货单是否已生成大单发货，true已生成，false未生成
	 * @param dispatchbillid
	 * @return
	 * @author panxiaoxiao
	 * @date 2017-01-18
	 */
	public int doCheckSaleoutIsbigsaleout(@Param("dispatchbillid")String dispatchbillid);

	/**
	 * 根据销售订单编号判断发货单是否已生成大单发货，true已生成，false未生成
	 * @param orderid
	 * @return
	 * @author panxiaoxiao
	 * @date 2017-01-19
	 */
	public 	int doCheckSaleoutIsbigsaleoutByOrderid(@Param("orderid")String orderid);
	/**
	 * 根据销售订单获取发货单的发货与发货出库总金额
	 * @param saleorderid
	 * @return
	 * @author chenwei
	 * @date Nov 19, 2013
	 */
	public SaleoutDetail getSaleOutSumAmountBySaleorderid(@Param("saleorderid")String saleorderid);

	/**
	 * 获取销售出库单明细列表 （合计品牌折扣）
	 * @param map
	 * @return java.util.List<com.hd.agent.storage.model.SaleoutDetail>
	 * @throws
	 * @author zhanghonghui
	 * @date May 10, 2017
	 */
	public List<SaleoutDetail> getSaleOutDetailListSumDiscountByMap(Map map);

	/**
	 * 获取导出发货单明细数据
	 * @param pageMap
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Mar 02, 2018
	 */
	public List getSaleoutDetailExportData(PageMap pageMap);
}