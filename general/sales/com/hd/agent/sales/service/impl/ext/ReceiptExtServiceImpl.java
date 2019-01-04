/**
 * @(#)ReceiptExtServiceImpl.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 7, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.service.impl.ext;

import com.hd.agent.sales.model.Receipt;
import com.hd.agent.sales.model.ReceiptDetail;
import com.hd.agent.sales.model.RejectBillDetail;
import com.hd.agent.sales.service.ext.IReceiptExtService;
import com.hd.agent.sales.service.impl.BaseSalesServiceImpl;

import java.math.BigDecimal;
import java.util.List;

/**
 * 销售发货回单对外接口
 *
 * @author zhengziyong
 */
public class ReceiptExtServiceImpl extends BaseSalesServiceImpl implements IReceiptExtService {

    /**
     * 退货通知单审核回写发货回单
     *
     * @param billDetailList
     * @throws Exception
     * @author zhengziyong
     * @date Jun 7, 2013
     */
    @Override
    public void updateReceiptDetailBack(List<RejectBillDetail> billDetailList) throws Exception {
        if (billDetailList != null) {
            for (RejectBillDetail billDetail : billDetailList) {
                ReceiptDetail detail = getSalesReceiptMapper().getReceiptDetail(billDetail.getBilldetailno());
                ReceiptDetail receiptDetail = new ReceiptDetail();
                receiptDetail.setRejectnummain(billDetail.getUnitnum());
                receiptDetail.setRejectnumaux(billDetail.getAuxnum());
                receiptDetail.setRejectamounttax(billDetail.getTaxamount());
                receiptDetail.setRejectamountnotax(billDetail.getNotaxamount());
                receiptDetail.setNorejectnummain(detail.getUnitnum().subtract(billDetail.getUnitnum()));
                receiptDetail.setNorejectnumaux(detail.getAuxnum().subtract(billDetail.getAuxnum()));
                receiptDetail.setNorejectamounttax(detail.getTaxamount().subtract(billDetail.getTaxamount()));
                receiptDetail.setNorejectamountnotax(detail.getNotaxamount().subtract(billDetail.getNotaxamount()));
                receiptDetail.setId(billDetail.getBilldetailno());
                receiptDetail.setBillid(billDetail.getBillno());
                getSalesReceiptMapper().updateReceiptDetailBack(receiptDetail);
            }
        }
    }

    /**
     * 退货通知单反审清除回写的发货回单数据
     *
     * @param billDetailList
     * @throws Exception
     * @author zhengziyong
     * @date Jun 7, 2013
     */
    @Override
    public void updateClearReceiptDetailBack(List<RejectBillDetail> billDetailList) throws Exception {
        if (billDetailList != null) {
            for (RejectBillDetail billDetail : billDetailList) {
                ReceiptDetail receiptDetail = new ReceiptDetail();
                receiptDetail.setRejectnummain(new BigDecimal(0));
                receiptDetail.setRejectnumaux(new BigDecimal(0));
                receiptDetail.setRejectamounttax(new BigDecimal(0));
                receiptDetail.setRejectamountnotax(new BigDecimal(0));
                receiptDetail.setNorejectnummain(new BigDecimal(0));
                receiptDetail.setNorejectnumaux(new BigDecimal(0));
                receiptDetail.setNorejectamounttax(new BigDecimal(0));
                receiptDetail.setNorejectamountnotax(new BigDecimal(0));
                receiptDetail.setId(billDetail.getBilldetailno());
                receiptDetail.setBillid(billDetail.getBillno());
                getSalesReceiptMapper().updateReceiptDetailBack(receiptDetail);
            }
        }
    }

    /**
     * 关闭销售发货回单
     *
     * @throws Exception
     * @author zhengziyong
     * @date May 21, 2013
     */
    public boolean updateReceiptClose(String id) throws Exception {
        return super.updateReceiptClose(id);
    }

    /**
     * 退货通知单反审后发货回单状态需改回审核状态
     *
     * @param id
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date May 22, 2013
     */
    public boolean updateReceiptOpen(String id) throws Exception {
        Receipt receipt = new Receipt();
        receipt.setId(id);
        receipt.setStatus("3");
        return getSalesReceiptMapper().updateReceiptStatus(receipt) > 0;
    }

    @Override
    public boolean updateReceiptInvoice(String isinvoice, String canceldate, String id) throws Exception {
        return getSalesReceiptMapper().updateReceiptInvoice(isinvoice, canceldate, id) > 0;
    }

}

