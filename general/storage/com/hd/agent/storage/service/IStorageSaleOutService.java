/**
 * @(#)IStorageSaleOutService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 31, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service;

import com.hd.agent.account.model.SalesInvoiceBill;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.DispatchBill;
import com.hd.agent.sales.model.DispatchBillDetail;
import com.hd.agent.storage.model.Saleout;
import com.hd.agent.storage.model.SaleoutDetail;

import java.util.List;
import java.util.Map;

/**
 * 
 * 销售出入库service
 * @author chenwei
 */
public interface IStorageSaleOutService {
	/**
	 * 通过销售发货通知单与明细，生成销售出库单
	 * Map:flag:true成功 false失败（可用量不足，不能自动生成）
	 *     msg:返回信息（失败提醒信息）
	 *     saleoutid :销售出库单编号
	 * @param dispatchBill
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 31, 2013
	 */
	public Map addSaleOutByDispatchbill(DispatchBill dispatchBill,List<DispatchBillDetail> detailList) throws Exception;
	/**
	 * 通过来源单据编号 删除销售出库单信息
	 * @param sourceid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	public boolean deleteSaleOutBySourceid(String sourceid) throws Exception;
	/**
	 * 获取销售出库单列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 5, 2013
	 */
	public PageData showSaleOutList(PageMap pageMap) throws Exception;
	/**
	 * 获取销售出库单详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 5, 2013
	 */
	public Map getSaleOutInfo(String id) throws Exception;

    /**
     * 获取销售发货单详细信息（不包含明细列表）
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jun 5, 2013
     */
    public Saleout getBaseSaleOutInfo(String id)throws Exception;

    /**
     * 获取销售发货单详细信息 数据权限内
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jun 5, 2013
     */
    public Saleout getSaleOutInfoWithDataSql(String id)throws Exception;
	/**
	 * 获取销售出库单详细信息（不包括折扣明细，折扣数据合计到打折商品中）
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 10, 2013
	 */
	public Map getSaleOutInfoWithoutDiscount(String id) throws Exception;
	/**
	 * 更新发货单参照状态
	 * @param id
	 * @param refer
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 9, 2013
	 */
	public boolean updateSaleOutRefer(String id,String refer) throws Exception;
	/**
	 * 添加销售出库单信息
	 * @param saleout
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	public Map addSaleOut(Saleout saleout,List<SaleoutDetail> detailList) throws Exception;
	/**
	 * 删除销售出库单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	public boolean deleteSaleOut(String id) throws Exception;
	/**
	 * 修改销售出库单
	 * @param saleout
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	public Map editSaleOut(Saleout saleout,List<SaleoutDetail> detailList) throws Exception;
	/**
	 * 审核销售出库单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	public Map auditSaleOut(String id) throws Exception;
	/**
	 * 反审销售出库单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 8, 2013
	 */
	public Map oppauditSaleOut(String id) throws Exception;

    /**
     * 清除发货单核对人信息
     * @param id
     * @return
     * @throws Exception
     */
    public Map deleteSaleoutCheckuser(String id) throws Exception;
	/**
	 * 根据来源单据编号(销售发货通知单编号)获取销售发货通知单明细列表
	 * @param sourceid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 8, 2013
	 */
	public List showDispatchBillDetailListBySourceid(String sourceid) throws Exception;
	/**
	 * 根据出库单编号和销售发货通知单编号获取发货通知单明细
	 * @param saleoutid
	 * @param dispatchBillid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 7, 2013
	 */
	public List showDispatchBillDetailList(String saleoutid,String dispatchBillid) throws Exception;
	/**
	 * 根据销售发货通知单编号和明细编号 生成销售出库单
	 * @param dispatchbillid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 7, 2013
	 */
	public Map addSaleOutByRefer(String dispatchbillid) throws Exception;
	/**
	 * 根据销售出库单编号获取明细列表
	 * @param id				编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 10, 2013
	 */
	public List getSaleOutDetailListByID(String id) throws Exception;
	
	/**
	 * 发货单工作流提交
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 24, 2013
	 */
	public Map submitSaleOutProcess(String id) throws Exception;	
	/**
	 * 根据销售订单号获取明细列表
	 * @param saleorderid				编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 10, 2013
	 */
	public List getSaleOutDetailListBySaleorder(String saleorderid) throws Exception;
	/**
	 * 获取销售出库单列表<br/>
	 * map中参数：<br/>
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
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-10-10
	 */
	public List showSaleOutListBy(Map map) throws Exception;
	/**
	 * 获取销售出库单列表,不检查数据权限<br/>
	 * map中参数：<br/>
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
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-10-10
	 */
	public List showSaleOutPureListBy(Map map) throws Exception;
	/**
	 * 获取发货单明细列表<br/>
	 * map 中参数：<br/>
	 * saleoutid : 发货单编号,必填写<br/>
	 * order 排序<br/>
	 * detailorderbygoodsid : 排序,如果有order则此参数无效<br/>
	 * printSplitType ： 打印时，是否需要拆单（选择不同查询sql），0不需要，2按品牌<br/>
	 * printSplitByBrand : 品牌编号字符串，当printSplitType=2<br/>
	 * exceptSplitByBrand : 1 时，除了printSplitByBrand外的其他信息，当printSplitType=2<br/>
	 * brandid:品牌码<br/>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年8月5日
	 */
	public List getSaleOutDetailListBy(Map map) throws Exception;
	/**
	 * 根据销售订单编号 获取销售出库单列表
	 * @param saleorderid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 11, 2013
	 */
	public List showSaleOutListBySaleorderid(String saleorderid) throws Exception;
	/**
	 * 根据商品编码获取商品待发信息 发货单
	 * @param goodsid
     * @param storageid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 16, 2014
	 */
	public List showGoodsWaitSaleListData(String goodsid,String storageid,String summarybatchid) throws Exception;
	/**
	 * 根据客户编号更新未核销的发货单应收日期
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 22, 2014
	 */
	public boolean updateCustomerSaleOutNoWriteoffDuefromdateByCustomerid(String customerid) throws Exception;
	/**
	 * 根据仓库编号和业务日期 获取未发货核对的发货单列表
	 * @param storageid
	 * @param date
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年6月30日
	 */
	public List getSaleoutUnCheckList(String storageid,String date,String status) throws Exception;

    /**
     * 为扫描枪提供发货单列表
     * @param status
     * @param map
     * @return
     * @throws Exception
     */
    public List getSaleoutForScanList(String status,Map map) throws Exception;
	/**
	 * 根据发货单编号 获取不包括折扣的发货单明细
	 * @param billid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年6月30日
	 */
	public List getSaleoutDetailWithoutDiscount(String billid) throws Exception;
	/**
	 * 根据发货单编号 更新发货单核对确认状态
	 * @param billid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年6月30日
	 */
	public boolean updateSaleoutCheckFlag(String billid) throws Exception;

    /**
     * 扫描枪 上传发货单并且审核
     * @param json
     * @return
     * @throws Exception
     */
    public Map updateSaleoutAndAudit(String json) throws Exception;
	/**
	 * 
	 * @param customerid
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月5日
	 */
	public List getCustomerHisGoodsSalesList(String customerid,String goodsid) throws Exception;

    /**
     * 获取预开票所需的审核通过的发货单数据列表
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015/03/11
     */
    public PageData getSaleoutListForAdvanceBill(PageMap pageMap)throws Exception;

    /**
     * 根据单据编号和明细编号，获取发货单明细详情
     * @param id
     * @param saleoutid
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015/03/11
     */
    public SaleoutDetail getSaleoutDetailInfo(String id, String saleoutid)throws Exception;
    /**
     * 根据发货单编号和品牌编号 获取发货单中品牌折扣的明细列表
     * @param saleoutid
     * @param brandid
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-12
     */
    public List<SaleoutDetail> getSaleoutDetailBrandDiscountList(String saleoutid, String brandid)throws Exception;
    /**
     * 根据单据编号和单据类型,以及发票明细数据 更新单据的发票生成状态(预开票)
     * @param sourceidList
     * @param salesInvoiceBill     (申请开票)
     * @param isinvoicebill
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Feb 14, 2015
     */
    public boolean updateSaleoutAdvanceBill(List<String> sourceidList, SalesInvoiceBill salesInvoiceBill, String isinvoicebill)throws Exception;
    /**
     * 更新发货单申请开票发票状态 (申请开票)
     * @param isinvoicebill 0未开票1已开票3可以生成发票4开票中
     * @param id
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-12
     */
    public boolean updateSaleoutInvoicebill(String isinvoicebill,String id)throws Exception;
    
    /**
     * 根据MAP中参数查询 统计数<br/>
     * map中参数：<br/>
     * saleoutid : 发货单编号<br/>
     * brandarrs: 根据品牌编号字符串，以,分隔<br/>
     * groupby: 是否分组查询,当对brandid 进分<br/>
     * @param map
     * @return
     * @author zhanghonghui 
     * @date 2015年8月4日
     */
    public int getSaleOutDetailCountBy(Map map) throws Exception;
    
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
    public int getSaleOutRightJoinDetailCountBy(Map map) throws Exception;
    /**
     * 根据编号获取进货单，打印次数，上游单据、仓库编号
     * @param id
     * @return
     * @throws Exception
     * @author zhanghonghui 
     * @date 2015年8月5日
     */
    public Saleout getSaloutPrinttimesById(String id) throws Exception;
    

	
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
    public List getSaleOutDetailListGroupBy(Map map) throws Exception;
    /**
     * 根据Map中参数查询 发货单明细 关联 发货单 统计发货单 品牌列表<br/>
     * 表t_storage_saleout 为t,表t_storage_saleout_detail 为t1<br/>
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
    public List getSaleOutRightJoinDetailListGroupBy(Map map) throws Exception;
    /**
     * 根据品牌拆分明细列表
     * @param detailList
     * @return
     * @throws Exception
     * @author zhanghonghui 
     * @date 2015年11月20日
     */
    public Map getSaleOutDetailListDataSplitByBrand(List<SaleoutDetail> detailList) throws Exception;
    /**
     * 根据整散单拆分明细列表<br/>
     * 返回结果Map<br/>
     * zxDetailList:整箱明细列表<br/>
     * sxDetailList:散箱明细列表<br/>
     * zkouDetailList:拆扣明细列表<br/>
     * @param detailList
     * @return
     * @throws Exception
     * @author zhanghonghui 
     * @date 2015年11月20日
     */
    public Map getSaleOutDetailListDataSplitByZSD(List<SaleoutDetail> detailList) throws Exception;
    /**
     * 修改发货人
     * @param id
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016-4-6
     */
    public boolean editDispatchUser(String id,String storager)throws Exception;
	/**
	 * 根据销售订单获取发货单的发货与发货出库总金额
	 * @param saleorderid
	 * @return com.hd.agent.storage.model.SaleoutDetail
	 * @throws
	 * @author zhang_honghui
	 * @date Jan 19, 2017
	 */
	public SaleoutDetail getSaleOutSumAmountBySaleorderid(String saleorderid) throws Exception;

	/**
	 * 根据编号获取发货单信息，不含明细列表
	 * @param id
	 * @return
	 * @throws
	 * @author zhanghonghui
	 * @date May 10, 2017
	 */
	public Saleout getSaleOutInfoById(String id) throws Exception;
	/**
	 * 获取销售出库单明细列表 （合计品牌折扣）<br>
	 * billid:单据编号<br/>
	 * billidarrs:单据编号组<br/>
	 * taxtype:税种<br/>	 *
	 * @param map
	 * @return java.util.List<com.hd.agent.storage.model.SaleoutDetail>
	 * @throws
	 * @author zhanghonghui
	 * @date May 10, 2017
	 */
	public List<SaleoutDetail> getSaleOutDetailListSumDiscountByMap(Map map) throws Exception;
	/**
	 * 获取生成未税凭证数据
	 * @throws
	 * @author lin_xx
	 * @date 2018-01-30
	 */
	 public List<Map> getSaleOutSumData(List<String> ids) throws Exception;

	/**
	 * 获取发货单导出明细数据
	 * @param pageMap
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Mar 02, 2018
	 */
	 public List getSaleoutDetailExportData(PageMap pageMap) throws Exception;

}

