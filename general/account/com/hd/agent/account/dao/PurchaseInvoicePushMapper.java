package com.hd.agent.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.account.model.PurchaseInvoicePush;
import com.hd.agent.common.util.PageMap;

/**
 * 采购发票冲差dao
 * @author chenwei
 */
public interface PurchaseInvoicePushMapper {
    /**
     * 根据采购发票编号获取采购冲差信息
     * @param invoiceid
     * @return
     * @author chenwei
     * @date Oct 17, 2013
     */
    public PurchaseInvoicePush getPurchaseInvoicePushInfo(@Param("invoiceid")String invoiceid);

    /**
     * 根据采购发票冲差单号获取采购冲差信息
     * @param id
     * @return
     * @author lin_xx
     * @date May 5, 2016
     */
    public  PurchaseInvoicePush getPurchaseInvoicePushInfoByType(String id);
    /**
     * 根据采购发票编号获取采购冲差信息
     * @param invoiceid
     * @return
     * @author lin_xx
     * @date May 5, 2016
     */
    public List<PurchaseInvoicePush> getPurchaseInvoicePushInfoList(@Param("invoiceid")String invoiceid);
    /**
     * 根据编号获取采购冲差信息
     * @param id
     * @return
     * @author panxiaoxiao
     * @date Nov 13, 2013
     */
    public PurchaseInvoicePush showPurchaseInvoicePushInfo(@Param("id")String id);
    /**
     * 添加采购发票冲差
     * @param purchaseInvoicePush
     * @return
     * @author chenwei
     * @date Oct 17, 2013
     */
    public int addPurchaseInvoicePursh(PurchaseInvoicePush purchaseInvoicePush);
    /**
     * 修改采购发票冲差
     * @param purchaseInvoicePush
     * @return
     * @author chenwei
     * @date Oct 17, 2013
     */
    public int updatePurchaseInvoicePush(PurchaseInvoicePush purchaseInvoicePush);
    /**
     * 根据冲差单ID 修改采购发票冲差
     * @param purchaseInvoicePush
     * @return
     * @author chenwei
     * @date Oct 17, 2013
     */
    public int updatePurchaseInvoicePushById(PurchaseInvoicePush purchaseInvoicePush);
    /**
     * 删除采购发票冲差
     * @param invoiceid
     * @return
     * @author chenwei
     * @date Oct 17, 2013
     */
    public int deletePurchaseInvoicePush(@Param("invoiceid")String invoiceid);
    /**
     * 删除采购发票冲差
     * @param id
     * @return
     * @author chenwei
     * @date Oct 17, 2013
     */
    public int deletePurchaseInvoicePushByType(String id);
    /**
     * 获取采购发票冲差数据列表
     * @param pageMap
     * @return
     * @author chenwei
     * @date Oct 17, 2013
     */
    public List showPurchaseInvoicePushList(PageMap pageMap);
    /**
     * 获取采购发票冲差数据数量
     * @param pageMap
     * @return
     * @author chenwei
     * @date Oct 17, 2013
     */
    public int showPurchaseInvoicePushCount(PageMap pageMap);
    /**
     * 获取采购发票冲差数据合计
     * @return
     * @author panxiaoxiao
     * @date 2015-05-07
     */
    public PurchaseInvoicePush getPurchaseInvoicePushSum(PageMap pageMap);

    /**
     * 根据采购发票号 更新采购发票冲差单状态
     * @param invoiceid
     * @param status
     * @return
     * @author chenwei
     * @date Oct 17, 2013
     */
    public int updatePurchaseInvoicePushStatus(@Param("invoiceid")String invoiceid,@Param("status")String status);
    /**
     * 根据采购发票号 更新采购发票冲差单 核销状态
     * @param invoiceid
     * @return
     * @author chenwei
     * @date Oct 17, 2013
     */
    public int updatePurchaseInvoiceIswriteoff(@Param("invoiceid")String invoiceid);

    /**
     * 根据采购发票编码更新冲差单状态（反核销）
     * @param invoiceid
     * @return
     * @author panxiaoxiao
     * @date 2015-03-24
     */
    public int updatePurchaseInvoiceUnIswriteoff(@Param("invoiceid")String invoiceid);
    /**
     * 根据编号关闭供应商应收款冲差单
     * @param id
     * @return
     * @author panxiaoxiao
     * @date Nov 13, 2013
     */
    public int closePurchaseInvoicePush(@Param("id")String id);

    /**
     * 根据发票编码、品牌编号获取发票冲差信息
     * @param invoiceid
     * @param brand
     * @return
     */
    public PurchaseInvoicePush getPurchaseInvoicePushInfoByGoodsid(@Param("invoiceid")String invoiceid, @Param("brand")String brand);
}