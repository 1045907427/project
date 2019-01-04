/**
 * @(#)IPurchaseInvoiceService.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 11, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.account.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.account.model.PurchaseInvoice;
import com.hd.agent.account.model.PurchaseInvoiceDetail;
import com.hd.agent.account.model.PurchaseInvoicePush;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.purchase.model.ArrivalOrderDetail;
import com.hd.agent.purchase.model.ReturnOrderDetail;

/**
 *
 *
 * @author panxiaoxiao
 */
public interface IPurchaseInvoiceService {

    /**
     * 获取采购发票列表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 11, 2013
     */
    public PageData showPurchaseInvoiceList(PageMap pageMap)throws Exception;

    /**
     * 根据采购进货单生成采购发票
     * @param ids
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 13, 2013
     */
    public Map addPurchaseInvoiceByArrivalOrder(String ids,List<ArrivalOrderDetail> arrivalDetailList)throws Exception;

    /**
     * 根据采购退货通知单生成采购发票
     * @param ids
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 13, 2013
     */
    public Map addPurchaseInvoiceByReturnOrder(String ids,List<ReturnOrderDetail> returnOrderDetailList)throws Exception;

    /**
     * 获取采购发票信息
     * @param id
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 13, 2013
     */
    public Map getPurchaseInvoiceInfo(String id)throws Exception;
    /**
     * 获取采购发票详细信息
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date Oct 17, 2013
     */
    public PurchaseInvoice showPurchaseInvoiceInfo(String id)throws Exception;
    /**
     * 删除采购发票
     * @param id
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 15, 2013
     */
    public boolean deletePurchaseInvoice(String id)throws Exception;

    /**
     * 修改采购发票
     * @param purchaseInvoice
     * @param detailList
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 15, 2013
     */
    public Map editPurchaseInVoice(PurchaseInvoice purchaseInvoice,List<PurchaseInvoiceDetail> detailList)throws Exception;

    /**
     * 审核采购发票
     * @param id
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 15, 2013
     */
    public boolean auditPurchaseInvoice(String id)throws Exception;

    /**
     * 反审采购发票
     * @param id
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 15, 2013
     */
    public boolean oppauditPurchaseInvoice(String id)throws Exception;

    /**
     * 根据供应商编码获取该供应商下的采购发票
     * @param supplierid
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 17, 2013
     */
    public List showPurchaseInvoiceListBySupplier(String supplierid)throws Exception;

    /**
     * 根据发票编号获取采购发票列表
     * @param invoiceid
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 17, 2013
     */
    public List getPurchaseInvoiceListByInvoiceids(String invoiceid)throws Exception;

    /**
     * 采购发票提交工作流
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jul 26, 2013
     */
    public boolean submitPurchaseInvoicePageProcess(String id) throws Exception;

    /**
     * 获取采购发票上游单据列表
     * @param sourceid
     * @param sourcetype
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Aug 31, 2013
     */
    public List showPurchaseInvoiceSourceListReferData(String sourceid,String sourcetype)throws Exception;

    /**
     * 获取采购发票详情信息
     * @param billid
     * @param goodsid
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 10, 2013
     */
    public PurchaseInvoiceDetail getPurchaseInvoiceDetailInfo(String sourceid,String billid,String goodsid)throws Exception;
    /**
     * 获取采购发票详情信息
     * @param sourceid
     * @param billid
     * @param sourcedetailid
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 10, 2013
     */
    public PurchaseInvoiceDetail getPurchaseInvoiceDetailInfoBySource(String sourceid,String billid,String sourcedetailid)throws Exception;
    /**
     * 根据采购发票编号获取采购发票冲差信息
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date Oct 17, 2013
     */
    public PurchaseInvoicePush getPurchaseInvoicePushInfo(String id) throws Exception;

    /**
     * 根据采购单编号 获取采购冲差单数量
     * @param invoiceid
     * @return
     * @throws Exception
     */
    public int countPurchasePush(String invoiceid) throws Exception ;
    /**
     * 添加或者修改采购发票应付款冲差
     * @param purchaseInvoicePush
     * @return
     * @throws Exception
     * @author chenwei
     * @date Oct 17, 2013
     */
    public boolean addOrUpdatePurchaseInvoicePush(PurchaseInvoicePush purchaseInvoicePush) throws Exception;

    /**
     * 更新冲差单
     * @param purchaseInvoicePush
     * @return
     * @throws Exception
     */
    public boolean updatePurchaseInvoicePush(PurchaseInvoicePush purchaseInvoicePush) throws Exception;
    /**
     * 获取采购发票冲差单数据列表
     * @param pageMap
     * @return
     * @throws Exception
     * @author chenwei
     * @date Oct 17, 2013
     */
    public PageData showPurchaseInvoicePushList(PageMap pageMap) throws Exception;
    /**
     * 根据采购发票号删除采购发票冲差单
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date Oct 17, 2013
     */
    public boolean deletePurchaseInvoicePush(String id) throws Exception;

    /**
     * 根据采购发票号和发票类型及对应品牌 删除采购发票冲差单
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date Oct 17, 2013
     */
    public boolean deletePurchaseInvoicePushDetail(String id, String pushtype,String brand) throws Exception;

    public PageData getPurchaseOrderListData(PageMap pageMap)throws Exception;

    /**
     * 生成采购发票
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Nov 11, 2013
     */
    public Map addPurchaseInvoice(String ids)throws Exception;
    /**
     * 根据来源编号获取采购发票
     * @param sourceid
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-12-17
     */
    public List getPurchaseInvoiceListBySourceid(String sourceid) throws Exception;

    /**
     * 获取可生成采购发票所有来源单据列表数据
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Nov 19, 2014
     */
    public PageData getPurchaseInvoiceSourceOfBillList(PageMap pageMap)throws Exception;

    /**
     * 根据供应商编码显示采购发票列表
     * @param supplierid
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Nov 21, 2014
     */
    public List getPurchaseInvoiceListBySupplierid(String supplierid)throws Exception;
    /**
     * 根据Map中参数 获取采购发票列表数据
     * @param map
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2015年9月9日
     */
    public List showPurchaseInvoiceListBy(Map map) throws Exception;
    /**
     * 更新打印次数
     * @param purchaseInvoice
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2015年9月9日
     */
    public boolean updateOrderPrinttimes(PurchaseInvoice purchaseInvoice) throws Exception;
    /**
     * 更新凭证生成次数
     * @throws
     * @author lin_xx
     * @date 2017-11-30
     */
    public boolean updatePurchaseInvoiceVouchertimes(PurchaseInvoice purchaseInvoice) throws Exception;
    /**
     * 更新打印次数
     * @param list
     * @throws Exception
     * @author zhanghonghui
     * @date 2015年9月9日
     */
    public void updateOrderPrinttimes(List<PurchaseInvoice> list) throws Exception;
    /**
     * 只获取采购发票原数据
     * @param id
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2015年9月9日
     */
    public PurchaseInvoice showPurchaseInvoicePureInfo(String id) throws Exception;

    /**
     * 获取采购发票明细列表根据商品编码
     * @param billid
     * @param goodsid
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-12-02
     */
    public List getPurchaseInvoiceDetailListByGoodsid(String billid, String goodsid)throws Exception;

    /**
     * 根据采购发票编码获取发票明细
     * @return
     * @throws Exception
     */
    public List<PurchaseInvoiceDetail> getPurchaseInvoiceDetailList(String billid) throws Exception;

    /**
     * 获取供应商发票总计
     * @param idarr
     * @return
     * @throws Exception
     */
    public List<Map> getSupplierPushSumData(List<String> idarr) throws Exception;

    /**
     * 删除采购发票应付款期初
     * @param id
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-10-10
     */
    public boolean deleteInvoiceBeginDueDetail(String id)throws Exception;

    /**
     * 获取供应商发票总计,生成凭证时，按单据取数据
     * @param idarr
     * @return
     * @throws Exception
     */
    public List<Map> getSupplierPushSumDataForThird(List<String> idarr) throws Exception;

    /**
     * 获取采购发票冲差按品牌的冲差金额
     * @param id
     * @return java.util.List<java.util.Map>
     * @throws
     * @author luoqiang
     * @date Jan 12, 2018
     */
    public List<Map> getPurchaseInvoicePushSumData(String id);

}

