/**
 * @(#)RejectBillExtServiceImpl.java
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

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.sales.model.Receipt;
import com.hd.agent.sales.model.ReceiptDetail;
import com.hd.agent.sales.model.RejectBill;
import com.hd.agent.sales.model.RejectBillDetail;
import com.hd.agent.sales.service.ext.IRejectBillExtService;
import com.hd.agent.sales.service.impl.BaseSalesServiceImpl;
import com.hd.agent.storage.service.IStorageForSalesService;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zhengziyong
 */
public class RejectBillExtServiceImpl extends BaseSalesServiceImpl implements IRejectBillExtService {

    private IStorageForSalesService storageForSalesService;

    public IStorageForSalesService getStorageForSalesService() {
        return storageForSalesService;
    }

    public void setStorageForSalesService(
            IStorageForSalesService storageForSalesService) {
        this.storageForSalesService = storageForSalesService;
    }

    /**
     * 判断是否需要生成退货通知单
     *
     * @param receipt
     * @return true需要自动生成退货通知单，false不需要生成退货通知单
     * @throws Exception
     * @author zhengziyong
     * @date Jun 6, 2013
     */
    @Override
    public boolean isAutoAddRejectBill(Receipt receipt) throws Exception {
        List<ReceiptDetail> detailList = receipt.getReceiptDetailList();
        for (ReceiptDetail detail : detailList) {
            if (detail.getUnitnum().compareTo(detail.getReceiptnum()) == 0) {
                continue;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 回单审核自动生成退货通知单
     *
     * @param receipt
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date May 28, 2013
     */
    @Override
    public String addRejectBillAuto(Receipt receipt) throws Exception {
        String result = null;
        SysUser sysUser = getSysUser();
        RejectBill rejectBill = new RejectBill();
        //退货类型1直退2售后退货
        rejectBill.setBilltype("1");
        rejectBill.setReceiptid(receipt.getId());
        rejectBill.setAdddeptid(sysUser.getDepartmentid());
        rejectBill.setAdddeptname(sysUser.getDepartmentname());
        rejectBill.setAdduserid(sysUser.getUserid());
        rejectBill.setAddusername(sysUser.getName());
        rejectBill.setBillno(receipt.getId());
        rejectBill.setBusinessdate(getCurrentDate());
        rejectBill.setCustomerid(receipt.getCustomerid());
        rejectBill.setRemark("回单直退" + receipt.getRemark());
        rejectBill.setPaytype(receipt.getPaytype());
        rejectBill.setHandlerid(receipt.getHandlerid());
        rejectBill.setSalesdept(receipt.getSalesdept());
        rejectBill.setSalesuser(receipt.getSalesuser());
        rejectBill.setSettletype(receipt.getSettletype());
        rejectBill.setStorageid(receipt.getStorageid());
        rejectBill.setSource("9");
        rejectBill.setStatus("2");
        rejectBill.setField01(receipt.getField01());
        rejectBill.setField02(receipt.getField02());
        rejectBill.setField03(receipt.getField03());
        rejectBill.setField04(receipt.getField04());
        rejectBill.setField05(receipt.getField05());
        rejectBill.setField06(receipt.getField06());
        rejectBill.setField07(receipt.getField07());
        rejectBill.setField08(receipt.getField08());
        rejectBill.setDuefromdate(receipt.getDuefromdate());
        if (isAutoCreate("t_sales_rejectbill")) {
            // 获取自动编号
            String id = getAutoCreateSysNumbderForeign(rejectBill, "t_sales_rejectbill");
            rejectBill.setId(id);
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
            Random random = new Random();
            String rand = random.nextInt(99999999) + "";
            String id = dateFormat.format(new Date()) + rand;
            rejectBill.setId(id);
        }
        rejectBill.setBillDetailList(receiptDetailToBillDetail(receipt.getReceiptDetailList(), rejectBill.getId()));
        if (addRejectBill(rejectBill)) {
            result = rejectBill.getId();
            //审核销售退货通知单
            auditRejectBillByReceiptaudit(rejectBill.getId());
            //验收销售退货通知单（关联回单审核）
            storageForSalesService.updateSaleRejectEnterCheckByReceipt(receipt, receipt.getReceiptDetailList());
        }
        return result;
    }

    /**
     * 回单明细转为退货通知单明细
     *
     * @param receiptDetailList 订单明细
     * @param billId            发货单编号
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date May 28, 2013
     */
    private List<RejectBillDetail> receiptDetailToBillDetail(List<ReceiptDetail> receiptDetailList, String billId) throws Exception {
        List<RejectBillDetail> billDetailList = new ArrayList<RejectBillDetail>();
        for (ReceiptDetail receiptDetail : receiptDetailList) {
            if (receiptDetail.getUnitnum().compareTo(receiptDetail.getReceiptnum()) == 1) {
                RejectBillDetail rejectBillDetail = new RejectBillDetail();
                rejectBillDetail.setBillno(receiptDetail.getBillid());
                rejectBillDetail.setBilldetailno(receiptDetail.getId());
                BigDecimal unitnum = receiptDetail.getUnitnum().subtract(receiptDetail.getReceiptnum());
                rejectBillDetail.setDeliverytype(receiptDetail.getDeliverytype());
                rejectBillDetail.setGoodsid(receiptDetail.getGoodsid());
                rejectBillDetail.setUnitid(receiptDetail.getUnitid());
                rejectBillDetail.setUnitname(receiptDetail.getUnitname());
                rejectBillDetail.setUnitnum(unitnum);
                Map map = countGoodsInfoNumber(receiptDetail.getGoodsid(), receiptDetail.getAuxunitid(), unitnum);
                if (map.containsKey("auxInteger")) {
                    rejectBillDetail.setAuxnum(new BigDecimal(map.get("auxInteger").toString()));
                }
                if (map.containsKey("auxremainder")) {
                    rejectBillDetail.setAuxremainder(new BigDecimal(map.get("auxremainder").toString()));
                }
                if (map.containsKey("auxnumdetail")) {
                    rejectBillDetail.setAuxnumdetail(map.get("auxnumdetail").toString());
                }
                rejectBillDetail.setAuxunitid(receiptDetail.getAuxunitid());
                rejectBillDetail.setAuxunitname(receiptDetail.getAuxunitname());
                rejectBillDetail.setDeliverydate(receiptDetail.getDeliverydate());
                rejectBillDetail.setExpirationdate(receiptDetail.getExpirationdate());
                rejectBillDetail.setRemark(receiptDetail.getRemark());
                BigDecimal taxamount=unitnum.multiply(receiptDetail.getTaxprice());
                rejectBillDetail.setTaxamount(taxamount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                rejectBillDetail.setTaxprice(receiptDetail.getTaxprice());
                rejectBillDetail.setNotaxamount(getNotaxAmountByTaxAmount(taxamount, receiptDetail.getTaxtype()));
                rejectBillDetail.setNotaxprice(receiptDetail.getNotaxprice());
                rejectBillDetail.setCostprice(receiptDetail.getCostprice());
                rejectBillDetail.setTaxtype(receiptDetail.getTaxtype());
                rejectBillDetail.setTaxtypename(receiptDetail.getTaxtypename());
                rejectBillDetail.setTax(rejectBillDetail.getTaxamount().subtract(rejectBillDetail.getNotaxamount()));
                rejectBillDetail.setBillid(billId);
                rejectBillDetail.setBillno(receiptDetail.getBillid());
                rejectBillDetail.setBilldetailno(receiptDetail.getId());
                rejectBillDetail.setField01(receiptDetail.getField01());
                rejectBillDetail.setField02(receiptDetail.getField02());
                rejectBillDetail.setField03(receiptDetail.getField03());
                rejectBillDetail.setField04(receiptDetail.getField04());
                rejectBillDetail.setField05(receiptDetail.getField05());
                rejectBillDetail.setField06(receiptDetail.getField06());
                rejectBillDetail.setField07(receiptDetail.getField07());
                rejectBillDetail.setField08(receiptDetail.getField08());
                
                rejectBillDetail.setSummarybatchid(receiptDetail.getSummarybatchid());
                rejectBillDetail.setBatchno(receiptDetail.getBatchno());
                rejectBillDetail.setStoragelocationid(receiptDetail.getStoragelocationid());
                rejectBillDetail.setProduceddate(receiptDetail.getProduceddate());
                rejectBillDetail.setDeadline(receiptDetail.getDeadline());
                rejectBillDetail.setDuefromdate(receiptDetail.getDuefromdate());
                billDetailList.add(rejectBillDetail);
            }
        }
        return billDetailList;
    }

    /**
     * 反审回单时删除退货通知单
     *
     * @param id
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Jun 6, 2013
     */
    @Override
    public boolean deleteRejectBillOppauditReceipt(String id) throws Exception {
        Map map = new HashMap();
        map.put("id", id);
        RejectBill receiptBill = getSalesRejectBillMapper().getRejectBillByReceipt(map);
        if (receiptBill != null) {
            if ("1".equals(receiptBill.getStatus()) || "2".equals(receiptBill.getStatus())) {
                return deleteRejectBill(receiptBill.getId());
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * 回单改数量审核后，直接审核销售退货通知单并且关联回单
     *
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2015年2月6日
     */
    public boolean auditRejectBillByReceiptaudit(String id) throws Exception {
        Map result = new HashMap();
        SysUser sysUser = getSysUser();
        Map map = new HashMap();
        map.put("id", id);
        RejectBill rejectBill = getSalesRejectBillMapper().getRejectBill(map);
        Date businessdate = CommonUtils.stringToDate(rejectBill.getBusinessdate());
        List<RejectBillDetail> detaiList = getSalesRejectBillMapper().getRejectBillDetailListByBill(id);
        if (null == sysUser) {
            sysUser = getSysUserById(rejectBill.getAdduserid());
        }
        String brandid = null;
        boolean brandMu = false;
        for(RejectBillDetail rejectBillDetail : detaiList){
            if(null==brandid){
                brandid = rejectBillDetail.getBrandid();
            }else if(!brandid.equals(rejectBillDetail.getBrandid())){
                brandMu = true;
            }
            //应收日期
            String duefromdate = getReceiptDateBySettlement(businessdate,rejectBill.getCustomerid(),rejectBillDetail.getBrandid());
            rejectBillDetail.setDuefromdate(duefromdate);
        }
        //判断单据里面是否只有单个品牌 单个品牌则单据应收日期取该品牌的应收日期
        if(brandMu){
            brandid = null;
        }
        //获取该单据的应收日期
        rejectBill.setDuefromdate(getReceiptDateBySettlement(businessdate, rejectBill.getCustomerid(),brandid));
        String billId = storageForSalesService.addSaleRejectEnterByRejectBill(rejectBill, detaiList);//生成下游单据
        getSalesRejectBillMapper().updateRejectBillRefer("1", id); //更改参照状态

        RejectBill rejectBill2 = new RejectBill();
        rejectBill2.setBusinessdate(getCurrentDate());
        rejectBill2.setAudituserid(sysUser.getUserid());
        rejectBill2.setAuditusername(sysUser.getName());
        rejectBill2.setAudittime(new Date());
        rejectBill2.setStatus("3");
        rejectBill2.setCheckdate(getCurrentDate());
        rejectBill2.setId(id);
        //获取该单据的应收日期
        rejectBill2.setDuefromdate(rejectBill.getDuefromdate());
        rejectBill2.setVersion(rejectBill.getVersion());
        boolean flag = getSalesRejectBillMapper().updateRejectBillStatus(rejectBill2) > 0;
        if(!flag){
            throw new Exception("回单改数量审核后，销售退货通知单审核失败 单据编号："+id);
        }
        result.put("flag", flag);
        result.put("billId", billId);
        return false;
    }

    @Override
    public boolean oppauditAndDeleteRejectBillByReceipt(String id)
            throws Exception {
        boolean flag = false;
        List<RejectBill> list = getSalesRejectBillMapper().getRejectBillByReceiptid(id);
        for (RejectBill rejectBill : list) {
            if (!"4".equals(rejectBill.getStatus())) { //只有状态为3（审核状态）才可进行反审
                throw new Exception("回单反审，销售发货通知单关闭不能反审");
            }
            //反审 并且删除 销售退货入库单
            boolean bl = storageForSalesService.deleteSaleRejectEnterByRejectidAndReceiptid(rejectBill.getId(), id);
            if (!bl) {
                throw new Exception("回单反审，销售发货通知单反审失败");
            }
            int j = getSalesRejectBillMapper().deleteRejectBillDetailByBill(rejectBill.getId());
            int i = getSalesRejectBillMapper().deleteRejectBill(rejectBill.getId());
            flag = i > 0;
        }
        return flag;
    }
}

