/**
 * @(#)PurchaseInvoiceMapper.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 11, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.account.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.account.model.PurchaseInvoice;
import com.hd.agent.account.model.PurchaseInvoiceDetail;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 采购发票dao
 * @author panxiaoxiao
 */
public interface PurchaseInvoiceMapper {

	/**
	 * 新增采购发票
	 * @param purchaseInvoice
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 11, 2013
	 */
	public int addPurchaseInvoice(PurchaseInvoice purchaseInvoice);
	
	/**
	 * 新增采购发票细则
	 * @param purchaseInvoiceDetail
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 11, 2013
	 */
	public int addPurchaseInvoiceDetail(PurchaseInvoiceDetail purchaseInvoiceDetail);
	
	/**
	 * 根据编码获取采购发票详情
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 11, 2013
	 */
	public PurchaseInvoice getPurchaseInvoiceInfo(String id);
	
	/**
	 * 根据单据编码获取采购发票细则列表
	 * @param billid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 11, 2013
	 */
	public List getPurchaseInvoiceDetailList(String billid);

    /**
     * 根据商品编码分类合计采购发票明细列表
     * @param billid
     * @return
     */
    public List getPurchaseInvoiceDetailListGroupGoodsid(@Param("billid")String billid);

    /**
     * 获取采购发票来源单据编号列表
     * @param billid
     * @return
     * @author panxiaoxiao
     * @date 2015-03-24
     */
    public List getPurchaseInvoiceSouceidList(@Param("billid")String billid);
	/**
	 * 根据单据编码和来源编号获取采购发票细则列表
	 * @param billid
	 * @param sourceid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-7-29
	 */
	public List getPurchaseInvoiceDetailListWithSourceid(@Param("billid")String billid,@Param("sourceid")String sourceid);
	
	/**
	 * 获取采购发票列表分页
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 11, 2013
	 */
	public List<PurchaseInvoice> getPurchaseInvoiceListPage(PageMap pageMap);
	
	/**
	 * 获取采购发票列表分页数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 11, 2013
	 */
	public int getPurchaseInvoiceListCount(PageMap pageMap);
	
	/**
	 * 获取采购发票合计
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 23, 2014
	 */
	public PurchaseInvoice getPurchaseInvoiceDataSum(PageMap pageMap);
	
	/**
	 * 删除状态为暂存或保存的采购发票
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 11, 2013
	 */
	public int deletePurchaseInvoice(String id);

	/**
	 * 根据来源单据编号删除采购发票明细
	 * @param billid
	 * @param sourceid
	 * @return
	 * @author panxiaoxiao
	 * @date 2016-10-10
	 */
	public int deletePurchaseInvoiceBySourceid(@Param("billid")String billid,@Param("sourceid")String sourceid);
	
	/**
	 * 根据单据编码删除所有采购发票细则
	 * @param billid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 11, 2013
	 */
	public int deletePurchaseInvoiceDetail(String billid);
	
	/**
	 * 修改采购发票
	 * @param purchaseInvoice
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 11, 2013
	 */
	public int editPurchaseInvoice(PurchaseInvoice purchaseInvoice);
	
	/**
	 * 采购发票审核
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 11, 2013
	 */
	public int auditPurchaseInvoice(Map map);
	
	/**
	 * 采购发票反审
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 11, 2013
	 */
	public int oppauditPurchaseInvoice(Map map);
	
	/**
	 * 采购发票核销
	 * @param id
	 * @param writeoffamount
	 * @param tailamount
	 * @param writeoffdate
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 17, 2013
	 */
	public int writeOffPurchaseInvoice(@Param("id")String id,@Param("writeoffamount")BigDecimal writeoffamount,@Param("tailamount")BigDecimal tailamount,@Param("writeoffdate")String writeoffdate);

	/**
	 *
	 * @throws
	 * @author lin_xx
	 * @date 2017-11-30
	 */
	public int updatePurchaseInvoiceVouchertimes(PurchaseInvoice purchaseInvoice);
    /**
     * 采购发票反核销
     * @param id
     * @return
     * @author panxiaoxiao
     * @date 2015-03-24
     */
    public int uncancelPurchaseInvoice(@Param("id")String id);
	/**
	 * 根据供应商编码获取未核销的采购发票列表
	 * @param supplierid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 17, 2013
	 */
	public List getPurchaseInvoiceListBySupplierid(String supplierid);
	
	/**
	 * 根据供应商编码获取为核销的采购发票列表数据
	 * @param supplierid
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 21, 2014
	 */
	public List getPurchaseInvoiceListDataBySupplierid(String supplierid);
	
	/**
	 * 获取采购发票详情信息
	 * @param billid
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 10, 2013
	 */
	public PurchaseInvoiceDetail getPurchaseInvoiceDetailInfo(@Param("sourceid")String sourceid,@Param("billid")String billid,@Param("goodsid")String goodsid);
	/**
	 * 获取采购发票详情信息,根据来源单据编号，来源单据明细及发票号
	 * @param sourceid
	 * @param billid
	 * @param sourcedetailid
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 10, 2013
	 */
	public PurchaseInvoiceDetail getPurchaseInvoiceDetailInfoBySource(@Param("sourceid")String sourceid,@Param("billid")String billid,@Param("sourcedetailid")String sourcedetailid);
	/**
	 * 根据来源单据编号和商品编码获取发票该商品数据列表
	 * @param sourceid
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 17, 2013
	 */
	public List<PurchaseInvoiceDetail> getPurchaseInvoiceDetailListBySourceidAndGoodsid(@Param("sourceid")String sourceid,@Param("goodsid")String goodsid);

	public List<PurchaseInvoiceDetail> getPurchaseInvoiceDetailListBySourceidAndGoodsidAndStatus(@Param("sourceid")String sourceid,@Param("goodsid")String goodsid);

	public List<PurchaseInvoice> getPurchaseInvoiceListBySourceidAndStatus(@Param("sourceid")String sourceid,@Param("status")String status);
	
	/**
	 * 根据来源单据获取状态为保存或审核的发票
	 * @param sourceid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 21, 2014
	 */
	public List getPurchaseInvoiceListBySourceidWithStatus(@Param("sourceid")String sourceid);
	
	/**
	 * 根据来源单据编号获取总核销金额
	 * @param sourceid
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	public BigDecimal getTotalWriteoffamountBySourceid(@Param("sourceid")String sourceid);
	
	/**
	 * 获取关闭状态,根据来源编号，并按来源单据及期明细编号分组合计
	 * @param sourceid
	 * @param sourceid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 21, 2014
	 */
	public List getPurchaseInvoiceSumBySourceidGroup(@Param("sourceid")String sourceid);
	
	public List getPurchaseOrderList(PageMap pageMap)throws Exception;
	
	public int getPurchaseOrderCount(PageMap pageMap)throws Exception;
	
	public int addPurchaseInvoiceDetailList(List<PurchaseInvoiceDetail> list)throws Exception;
	
	public List getPurchaseInvoiceDetailListBySourceid(@Param("sourceid")String sourceid)throws Exception;
	/**
	 * 根据来源单据获取发票
	 * @param sourceid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-12-17
	 */
	public List getPurchaseInvoiceListBySourceid(@Param("sourceid")String sourceid)throws Exception;
	/**
	 * 根据来源编号并按来源编号明细合计<br/>
	 * map中参数：<br/>
	 * sourceid ： 来源单据编号，必须<br/>
	 * status : 状态<br/>
	 * statusarr : 状态字符串<br/>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-3-7
	 */
	public List getPurchaseInvoiceDetailSumListBySourceidGroup(Map map)throws Exception;

	/**
	 * 根据采购发票、商品编号查找采购发票明细列表数据
	 * @param billid
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-10-28
	 */
	public List getPurchaseInvoiceDetailListByBillAndGoodsid(@Param("billid")String billid,@Param("goodsid")String goodsid)throws Exception;
	/**
	 * 根据发票编号查询发票明细里相同来源单据号及其明细列表
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-9-26
	 */
	public List getPurchaseInvoiceDetailSourcetypeList(@Param("billid")String id) throws Exception;
	
	/**
	 * 获取可生成采购发票所有来源单据列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 19, 2014
	 */
	public List getPurchaseInvoiceSourceOfBillList(PageMap pageMap)throws Exception;
	
	/**
	 * 获取可生成采购发票所有来源单据列表数量
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 19, 2014
	 */
	public int getPurchaseInvoiceSourceOfBillCount(PageMap pageMap)throws Exception;
	
	/**
	 * 获取可生成采购发票所有来源单据列表合计
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 19, 2014
	 */
	public Map getPurchaseInvoiceSourceOfBillSumData(PageMap pageMap)throws Exception;
	/**
	 * 获取采购发票中品牌列表
	 * @param invoiceid
	 * @return
	 * @throws Exception
	 */
	public List getPurchaseInvoiceBrandList(@Param("invoiceid")String invoiceid ) throws Exception;

    /**
     * 根据采购发票细则编号删除对应的采购发票细则
     * @param id
     * @return
     * @author lin_xx
     * @date June 30, 2015
     */
    public int deletePurchaseInvoiceDetailById(Integer id);

    /**
     * 根据Map中参数获取采购发票列表<br/>
	 * map中参数:<br/>
	 * dataSql：数据权限<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * status: 表示状态3<br/>
	 * statusarr: 表示状态，类似 1,2,3<br/>
     * @param map
     * @return
     * @author zhanghonghui 
     * @date 2015年9月9日
     */
	public List<PurchaseInvoice> getPurchaseInvoiceListByMap(Map map);
	
	/**
	 * 更新打印次数
	 * @param purchaseInvoice
	 * @return
     * @author zhanghonghui 
     * @date 2015年9月9日
	 */
	public int updateOrderPrinttimes(PurchaseInvoice purchaseInvoice);

    /**
     * 根据商品编码获取采购发票明细列表
     * @param billid
     * @param goodsid
     * @return
     * @throws Exception
     */
    public List<PurchaseInvoiceDetail> getPurchaseInvoiceDetailListByGoodsid(@Param("billid")String billid, @Param("goodsid")String goodsid)throws Exception;

    /**
     * 根据商品编码 来源单据号 获取采购发票明细
     */
    public PurchaseInvoiceDetail getPurchaseInvoiceGoodsByGoodsidSourceid(@Param("billid")String billid,
                                                                          @Param("sourceid")String sourceid,@Param("goodsid")String goodsid)throws Exception;

    /**
     * 根据采购发票编码获取明细金额合计
     * @param invoiceid
     * @return
     */
    public PurchaseInvoiceDetail getPurchaseInvoiceDetailSum(@Param("invoiceid")String invoiceid);

    /**
     *获取供应商发票总计
     */
    public  List<Map> getSupplierRejectSumData(List<String>  idarr);

	/**
	 *获取供应商发票总计
	 */
	public  List<Map> getSupplierRejectSumDataForThird(List<String>  idarr);

	/**
	 * 获取采购发票冲差按品牌汇总数据
	 * @param id
	 * @return java.util.List<java.util.Map>
	 * @throws
	 * @author luoqiang
	 * @date Jan 12, 2018
	 */
	public List<Map> getPurchaseInvoicePushSumData(@Param("id") String id);
}

