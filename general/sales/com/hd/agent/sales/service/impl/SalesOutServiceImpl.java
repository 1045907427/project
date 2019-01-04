/**
 * @(#)SalesOutServiceImpl.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 6, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.BeginAmountMapper;
import com.hd.agent.account.dao.CustomerPushBalanceMapper;
import com.hd.agent.account.model.CustomerPushBalance;
import com.hd.agent.account.model.SalesInvoice;
import com.hd.agent.account.model.SalesInvoiceBill;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.sales.model.*;
import com.hd.agent.sales.service.IReceiptService;
import com.hd.agent.sales.service.ISalesOutService;
import com.hd.agent.storage.model.SaleRejectEnterDetail;
import com.hd.agent.storage.model.Saleout;
import com.hd.agent.storage.model.SaleoutDetail;
import com.hd.agent.storage.service.IStorageSaleService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 
 * 提供给仓库的接口
 * @author zhengziyong
 */
public class SalesOutServiceImpl extends BaseSalesServiceImpl implements ISalesOutService {

    private IStorageSaleService storageSaleService;

    private CustomerPushBalanceMapper customerPushBalanceMapper;
    /**
     * 客户应收款期初
     */
    private BeginAmountMapper beginAmountMapper;

    public IStorageSaleService getStorageSaleService() {
        return storageSaleService;
    }

    public void setStorageSaleService(IStorageSaleService storageSaleService) {
        this.storageSaleService = storageSaleService;
    }

    @Override
    public boolean updateDispatchBillRefer(String isrefer, String id) throws Exception {
        return getSalesDispatchBillMapper().updateDispatchBillRefer(isrefer, id) > 0;
    }

    public CustomerPushBalanceMapper getCustomerPushBalanceMapper() {
        return customerPushBalanceMapper;
    }

    public void setCustomerPushBalanceMapper(
            CustomerPushBalanceMapper customerPushBalanceMapper) {
        this.customerPushBalanceMapper = customerPushBalanceMapper;
    }

    public BeginAmountMapper getBeginAmountMapper() {
        return beginAmountMapper;
    }

    public void setBeginAmountMapper(BeginAmountMapper beginAmountMapper) {
        this.beginAmountMapper = beginAmountMapper;
    }

    @Override
    public boolean updateDispatchBillClose(String id, String orderid) throws Exception {
        //系统是否启用销售发货通知单 如果未启动 发货单审核通过后需要关闭销售订单
        String sysparam = getSysParamValue("IsDispatchProcessUse");
        if ("0".equals(sysparam)) {
            Order order = new Order();
            order.setId(orderid);
            order.setClosetime(new Date());
            order.setStatus("4");
            getSalesOrderMapper().updateOrderStatus(order);
        }
        DispatchBill bill = new DispatchBill();
        bill.setId(id);
        bill.setClosetime(new Date());
        bill.setStatus("4");
        return getSalesDispatchBillMapper().updateDispatchBillStatus(bill) > 0;
    }

    @Override
    public boolean updateDispatchBillOpen(String id, String orderid) throws Exception {
        //系统是否启用销售发货通知单 如果未启动 发货单反核通过后需要打开销售订单
        String sysparam = getSysParamValue("IsDispatchProcessUse");
        if ("0".equals(sysparam)) {
            Order order = new Order();
            order.setId(orderid);
            order.setStatus("3");
            getSalesOrderMapper().updateOrderStatus(order);
        }
        DispatchBill bill = new DispatchBill();
        bill.setId(id);
        bill.setStatus("3");
        return getSalesDispatchBillMapper().updateDispatchBillStatus(bill) > 0;
    }

    @Override
    public void updateDispatchBillDetailBack(List<SaleoutDetail> detailList) throws Exception {
        if (detailList != null) {
            for (SaleoutDetail outDetail : detailList) {
                if ((outDetail.getUnitnum().compareTo(new BigDecimal(0)) == 1) || "1".equals(outDetail.getIsdiscount())) { //回写数量大于0或者折扣明细 才进行数据更新
                    DispatchBillDetail detail = getSalesDispatchBillMapper().getDispatchBillDetail(outDetail.getDispatchbilldetailid());
                    if (null != detail) {
                        DispatchBillDetail billDetail = new DispatchBillDetail();
                        if (detail.getOutnummain() == null) detail.setOutnummain(new BigDecimal(0));
                        if (detail.getOutnumaux() == null) detail.setOutnumaux(new BigDecimal(0));
                        if (detail.getTaxamount() == null) detail.setTaxamount(new BigDecimal(0));
                        if (detail.getNotaxamount() == null) detail.setNotaxamount(new BigDecimal(0));
                        if (detail.getNooutnummain() == null) detail.setNooutnummain(new BigDecimal(0));
                        if (detail.getNooutnumaux() == null) detail.setNooutnumaux(new BigDecimal(0));
                        if (detail.getNooutamounttax() == null) detail.setNooutamounttax(new BigDecimal(0));
                        if (detail.getNooutamountnotax() == null) detail.setNooutamountnotax(new BigDecimal(0));
                        billDetail.setOutnummain(outDetail.getUnitnum().add(detail.getOutnummain()));
                        //					billDetail.setOutnumaux(outDetail.getAuxnum().add(detail.getOutnumaux()));
                        billDetail.setOutamounttax(outDetail.getTaxamount().add(detail.getOutamounttax()));
                        billDetail.setOutamountnotax(outDetail.getNotaxamount().add(detail.getOutamountnotax()));
                        if (detail.getNooutnummain().compareTo(detail.getUnitnum()) == 0) { //如果初始未出库的数量为0，则未出库为数量减去回写的数量
                            billDetail.setNooutnummain(detail.getUnitnum().subtract(outDetail.getUnitnum()));
                            //						billDetail.setNooutnumaux(detail.getAuxnum().subtract(outDetail.getAuxnum()));
                            billDetail.setNooutamounttax(detail.getTaxamount().subtract(outDetail.getTaxamount()));
                            billDetail.setNooutamountnotax(detail.getNotaxamount().subtract(outDetail.getNotaxamount()));
						}
						else{ //如果初始未出库的数量不为0，则未出库为初始未出为数量减去回写的数量
							billDetail.setNooutnummain(detail.getNooutnummain().subtract(outDetail.getUnitnum()));
	//						billDetail.setNooutnumaux(detail.getNooutnumaux().subtract(outDetail.getAuxnum()));
							billDetail.setNooutamounttax(detail.getNooutamounttax().subtract(outDetail.getTaxamount()));
							billDetail.setNooutamountnotax(detail.getNooutamountnotax().subtract(outDetail.getNotaxamount()));
						}
						billDetail.setId(outDetail.getDispatchbilldetailid());
						billDetail.setBillid(outDetail.getDispatchbillid());
						getSalesDispatchBillMapper().updateDispatchBillDetailBack(billDetail);
					}
				}
			}
		}
	}

    @Override
    public void updateClearDispatchBillDetailBack(List<SaleoutDetail> detailList) throws Exception {
        if (detailList != null) {
            for (SaleoutDetail outDetail : detailList) {
                if ((outDetail.getUnitnum().compareTo(new BigDecimal(0)) == 1) || "1".equals(outDetail.getIsdiscount())) { //回写数量大于0才进行数据更新
                    DispatchBillDetail detail = getSalesDispatchBillMapper().getDispatchBillDetail(outDetail.getDispatchbilldetailid());
                    DispatchBillDetail billDetail = new DispatchBillDetail();
                    billDetail.setOutnummain(detail.getOutnummain().subtract(outDetail.getUnitnum()));
//					billDetail.setOutnumaux(detail.getOutnumaux().subtract(outDetail.getAuxnum()));
                    billDetail.setOutamounttax(detail.getOutamounttax().subtract(outDetail.getTaxamount()));
                    billDetail.setOutamountnotax(detail.getOutamountnotax().subtract(outDetail.getNotaxamount()));
                    billDetail.setNooutnummain(detail.getNooutnummain().add(outDetail.getUnitnum()));
//					billDetail.setNooutnumaux(detail.getNooutnumaux().add(outDetail.getAuxnum()));
                    billDetail.setNooutamounttax(detail.getNooutamounttax().add(outDetail.getTaxamount()));
                    billDetail.setNooutamountnotax(detail.getNooutamountnotax().add(outDetail.getNotaxamount()));
                    billDetail.setId(outDetail.getDispatchbilldetailid());
                    billDetail.setBillid(outDetail.getDispatchbillid());
                    getSalesDispatchBillMapper().updateDispatchBillDetailBack(billDetail);
                }
            }
        }
    }

    @Override
    public DispatchBill getDispatchBill(String id) throws Exception {
        List<DispatchBillDetail> billDetailList = getSalesDispatchBillMapper().getDispatchBillDetailListByBill(id);
        DispatchBill dispatchBill = getSalesDispatchBillMapper().getDispatchBill(id);
        for (DispatchBillDetail billDetail : billDetailList) {
            GoodsInfo goodsInfo = getGoodsInfoByID(billDetail.getGoodsid());
            billDetail.setGoodsInfo(goodsInfo);
            TaxType taxType = getTaxType(billDetail.getTaxtype());
            if (taxType != null) {
                billDetail.setTaxtypename(taxType.getName());
            }
        }
        dispatchBill.setBillDetailList(billDetailList);
        return dispatchBill;
    }

    @Override
    public DispatchBillDetail getDispatchBillDetail(String id) throws Exception {
        DispatchBillDetail dispatchBillDetail = getSalesDispatchBillMapper().getDispatchBillDetail(id);
        GoodsInfo goodsInfo = getGoodsInfoByID(dispatchBillDetail.getGoodsid());
        dispatchBillDetail.setGoodsInfo(goodsInfo);
        return dispatchBillDetail;
    }

    @Override
    public String addReceiptAuto(Saleout saleout, List<SaleoutDetail> detailList) throws Exception {
        String result = null;
        Receipt receipt = new Receipt();
        receipt.setIndooruserid(saleout.getIndooruserid());
        receipt.setAdddeptid(saleout.getAdddeptid());
        receipt.setAdddeptname(saleout.getAdddeptname());
        receipt.setAdduserid(saleout.getAdduserid());
        receipt.setAddusername(saleout.getAddusername());
        receipt.setBillno(saleout.getId());
        receipt.setBusinessdate(saleout.getBusinessdate());
        receipt.setCustomerid(saleout.getCustomerid());
        receipt.setPcustomerid(saleout.getPcustomerid());
        receipt.setCustomersort(saleout.getCustomersort());
        receipt.setRemark(saleout.getRemark());
        receipt.setPaytype(saleout.getPaytype());
        receipt.setHandlerid(saleout.getHandlerid());
        receipt.setSalesarea(saleout.getSalesarea());
        receipt.setSalesdept(saleout.getSalesdept());
        receipt.setSalesuser(saleout.getSalesuser());
        receipt.setSettletype(saleout.getSettletype());
        receipt.setStorageid(saleout.getStorageid());
        receipt.setSaleorderid(saleout.getSaleorderid());
        receipt.setSource("1");
        receipt.setStatus("2");
        receipt.setField01(saleout.getField01());
        receipt.setField02(saleout.getField02());
        receipt.setField03(saleout.getField03());
        receipt.setField04(saleout.getField04());
        receipt.setField05(saleout.getField05());
        receipt.setField06(saleout.getField06());
        receipt.setField07(saleout.getField07());
        receipt.setField08(saleout.getField08());
//		//根据审核时间 获取应收日期
        Date saleoutDate = saleout.getAudittime();
        receipt.setDuefromdate(getReceiptDateBySettlement(saleoutDate, saleout.getCustomerid(),null));
        receipt.setReceiptDetailList(saleoutDetailToReceiptDetail(detailList, receipt.getId()));
        if ((addReceipt(receipt))) {
            result = receipt.getId();

        }
        return result;
    }

    /**
     * 通过销售发货回单编号 审核销售发货回单
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public boolean auditReceipt(String id) throws Exception {
        boolean flag = false;
        if( StringUtils.isNotEmpty(id)){
            IReceiptService iReceiptService = (IReceiptService) SpringContextUtils.getBean("salesReceiptService");
            Map map = iReceiptService.auditReceipt("1",id);
            if(null!=map){
                flag = (Boolean)map.get("flag");
            }
        }
        return flag;
    }

    private List<ReceiptDetail> saleoutDetailToReceiptDetail(List<SaleoutDetail> saleoutDetailList, String billId) throws Exception {
        List<ReceiptDetail> billDetailList = new ArrayList<ReceiptDetail>();
        for (SaleoutDetail saleoutDetail : saleoutDetailList) {
            ReceiptDetail receiptDetail = new ReceiptDetail();
            receiptDetail.setGoodsid(saleoutDetail.getGoodsid());
            receiptDetail.setGroupid(saleoutDetail.getGroupid());
            receiptDetail.setBrandid(saleoutDetail.getBrandid());
            receiptDetail.setGoodssort(saleoutDetail.getGoodssort());
            receiptDetail.setBranduser(saleoutDetail.getBranduser());
            //厂家业务员
            receiptDetail.setSupplieruser(saleoutDetail.getSupplieruser());
            receiptDetail.setBranddept(saleoutDetail.getBranddept());
            receiptDetail.setSupplierid(saleoutDetail.getSupplierid());
            receiptDetail.setIsdiscount(saleoutDetail.getIsdiscount());
            receiptDetail.setIsbranddiscount(saleoutDetail.getIsbranddiscount());
            receiptDetail.setUnitid(saleoutDetail.getUnitid());
            receiptDetail.setUnitname(saleoutDetail.getUnitname());
            receiptDetail.setUnitnum(saleoutDetail.getUnitnum());
            receiptDetail.setReceiptnum(saleoutDetail.getUnitnum());
            receiptDetail.setAuxnum(saleoutDetail.getAuxnum());
            receiptDetail.setAuxremainder(saleoutDetail.getAuxremainder());
            receiptDetail.setAuxnumdetail(saleoutDetail.getAuxnumdetail());
            receiptDetail.setTotalbox(saleoutDetail.getTotalbox());
            receiptDetail.setReceiptbox(saleoutDetail.getTotalbox());
            receiptDetail.setAuxunitid(saleoutDetail.getAuxunitid());
            receiptDetail.setAuxunitname(saleoutDetail.getAuxunitname());
            receiptDetail.setDeliverytype(saleoutDetail.getDeliverytype());
            //receiptDetail.setDeliverydate(saleoutDetail.getDeliverydate());
            //receiptDetail.setExpirationdate(saleoutDetail.get.getExpirationdate());
            receiptDetail.setRemark(saleoutDetail.getRemark());
            receiptDetail.setTax(saleoutDetail.getTax());
            receiptDetail.setTaxamount(saleoutDetail.getTaxamount());
            receiptDetail.setReceipttaxamount(saleoutDetail.getTaxamount());
            receiptDetail.setTaxprice(saleoutDetail.getTaxprice());
            receiptDetail.setNotaxamount(saleoutDetail.getNotaxamount());
            receiptDetail.setReceiptnotaxamount(saleoutDetail.getNotaxamount());
            receiptDetail.setNotaxprice(saleoutDetail.getNotaxprice());
            //应收未税单价 初始时应收未税单价=未税单价
            receiptDetail.setReceiptnotaxprice(saleoutDetail.getNotaxprice());
            //成本价
            receiptDetail.setCostprice(saleoutDetail.getCostprice());
            //折扣金额
            receiptDetail.setDiscountamount(saleoutDetail.getDiscountamount());
            //初始化单价
            receiptDetail.setInittaxprice(saleoutDetail.getTaxprice());
            receiptDetail.setTaxtype(saleoutDetail.getTaxtype());
            receiptDetail.setTaxtypename(saleoutDetail.getTaxtypename());
            receiptDetail.setBillid(billId);
            receiptDetail.setBillno(saleoutDetail.getSaleoutid());
            receiptDetail.setBilldetailno(saleoutDetail.getId().toString());
            receiptDetail.setField01(saleoutDetail.getField01());
            receiptDetail.setField02(saleoutDetail.getField02());
            receiptDetail.setField03(saleoutDetail.getField03());
            receiptDetail.setField04(saleoutDetail.getField04());
            receiptDetail.setField05(saleoutDetail.getField05());
            receiptDetail.setField06(saleoutDetail.getField06());
            receiptDetail.setField07(saleoutDetail.getField07());
            receiptDetail.setField08(saleoutDetail.getField08());
            receiptDetail.setSeq(saleoutDetail.getSeq());
            receiptDetail.setSummarybatchid(saleoutDetail.getSummarybatchid());
            receiptDetail.setStoragelocationid(saleoutDetail.getStoragelocationid());
            receiptDetail.setBatchno(saleoutDetail.getBatchno());
            receiptDetail.setProduceddate(saleoutDetail.getProduceddate());
            receiptDetail.setDeadline(saleoutDetail.getDeadline());
            receiptDetail.setDuefromdate(saleoutDetail.getDuefromdate());
            billDetailList.add(receiptDetail);
        }
        return billDetailList;
    }

    @Override
    public boolean deleteReceipt(String id, String orderid) throws Exception {
        Receipt receipt = getSalesReceiptMapper().getReceiptBySaleorderid(orderid);
        if (receipt != null) {
            int count = getSalesReceiptMapper().getReceiptDetailRejectCount(receipt.getId());
            if (count == 0 && ("1".equals(receipt.getStatus()) || "2".equals(receipt.getStatus()))) {
                //删除明细
                boolean flag = getSalesReceiptMapper().deleteReceiptDetailByBillno(id) > 0;
                List list = getSalesReceiptMapper().getReceiptDetailListByReceipt(receipt.getId());
                if (null == list || list.size() == 0) {
                    getSalesReceiptMapper().deleteReceipt(receipt.getId());
                }else{
                    //重新获取来源单据编码
                    List<String> list1 = getSalesReceiptMapper().getReceiptBillnosListByReceiptid(receipt.getId());
                    if(null != list1 && list1.size() != 0){
                        String billnos = "";
                        for(String billno : list1){
                            if(StringUtils.isEmpty(billnos)){
                                billnos = billno;
                            }else{
                                billnos += "," + billno;
                            }
                        }
                        receipt.setBillno(billnos);
                    }
                    //根据来源单据发货单判断配送状态0未配送1配送中2已配送
                    String isdelivery = getIsDeliveryByReceiptSourceids(receipt);
                    receipt.setIsdelivery(isdelivery);
                    getSalesReceiptMapper().updateReceipt(receipt);
                }
                return flag;
			}
			else{
				return false;
			}
		}
		else{
			return true;
		}
	}

    @Override
    public boolean updateReceiptRefer(String isrefer, String id) throws Exception {
        return getSalesReceiptMapper().updateReceiptRefer(isrefer, id) > 0;
    }

    @Override
    public void updateReceiptDetailBack(List<SaleRejectEnterDetail> detailList) throws Exception {
        if (detailList != null) {
            for (SaleRejectEnterDetail saleDetail : detailList) {
                ReceiptDetail detail = getSalesReceiptMapper().getReceiptDetail(saleDetail.getBilldetailid());
                ReceiptDetail receiptDetail = new ReceiptDetail();
                BigDecimal num = detail.getUnitnum().subtract(detail.getReceiptnum()); //退货入库的数量
                receiptDetail.setRejectnummain(saleDetail.getUnitnum());
                receiptDetail.setRejectnumaux(saleDetail.getAuxnum());
                receiptDetail.setRejectamounttax(saleDetail.getTaxamount());
                receiptDetail.setRejectamountnotax(saleDetail.getNotaxamount());
                receiptDetail.setNorejectnummain(num.subtract(saleDetail.getUnitnum()));
                Map map = countGoodsInfoNumber(detail.getGoodsid(), detail.getAuxunitid(), num);
                BigDecimal auxnum = new BigDecimal(0);
                if (map.containsKey("auxnum") && StringUtils.isNotEmpty(map.get("auxnum").toString())) {
                    auxnum = new BigDecimal(map.get("auxnum").toString());
                }
                receiptDetail.setNorejectnumaux(auxnum.subtract(saleDetail.getAuxnum()));
                BigDecimal taxAmount = detail.getTaxprice().multiply(num).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);
                receiptDetail.setNorejectamounttax(taxAmount.subtract(saleDetail.getTaxamount()));
                BigDecimal noTaxAmount = getNotaxAmountByTaxAmount(taxAmount, detail.getTaxtype());
                receiptDetail.setNorejectamountnotax(noTaxAmount.subtract(saleDetail.getNotaxamount()));
                receiptDetail.setId(saleDetail.getBilldetailid());
                receiptDetail.setBillid(saleDetail.getBillid());
                getSalesReceiptMapper().updateReceiptDetailBack(receiptDetail);
            }
        }
    }

    @Override
    public void updateClearReceiptDetailBack(List<SaleRejectEnterDetail> detailList) throws Exception {
        if (detailList != null) {
            for (SaleRejectEnterDetail saleDetail : detailList) {
                ReceiptDetail receiptDetail = new ReceiptDetail();
                receiptDetail.setRejectnummain(new BigDecimal(0));
                receiptDetail.setRejectnumaux(new BigDecimal(0));
                receiptDetail.setRejectamounttax(new BigDecimal(0));
                receiptDetail.setRejectamountnotax(new BigDecimal(0));
                receiptDetail.setNorejectnummain(new BigDecimal(0));
                receiptDetail.setNorejectnumaux(new BigDecimal(0));
                receiptDetail.setNorejectamounttax(new BigDecimal(0));
                receiptDetail.setNorejectamountnotax(new BigDecimal(0));
                receiptDetail.setId(saleDetail.getBilldetailid());
                receiptDetail.setBillid(saleDetail.getBillid());
                getSalesReceiptMapper().updateReceiptDetailBack(receiptDetail);
            }
        }
    }

    @Override
    public boolean updateReceiptClose(String id) throws Exception {
        return super.updateReceiptClose(id);
    }

    @Override
    public boolean updateReceiptOpen(String id) throws Exception {
        Receipt receipt = new Receipt();
        receipt.setId(id);
        receipt.setStatus("3");
        return getSalesReceiptMapper().updateReceiptStatus(receipt) > 0;
    }

    @Override
    public Receipt getReceipt(String id) throws Exception {
        Map map = new HashMap();
        map.put("id", id);
        Receipt receipt = getSalesReceiptMapper().getReceipt(map);
        if (null != receipt) {
            DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(receipt.getSalesdept());
            if (departMent != null) {
                receipt.setSalesdeptname(departMent.getName());
            }
            Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(receipt.getSalesuser());
            if (personnel != null) {
                receipt.setSalesusername(personnel.getName());
            }
            Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(map);
            if (customer != null) {
                receipt.setCustomername(customer.getName());
            }
            Contacter contacter = getBaseFilesContacterMapper().getContacterDetail(map);
            if (contacter != null) {
                receipt.setHandlername(contacter.getName());
            }
            Map total = getSalesReceiptMapper().getReceiptDetailTotal(id);
            if (total != null) {
                if (total.containsKey("taxamount")) {
                    receipt.setField01(total.get("taxamount").toString());
                }
                if (total.containsKey("notaxamount")) {
                    receipt.setField02(total.get("notaxamount").toString());
                }
                if (total.containsKey("tax")) {
                    receipt.setField03(total.get("tax").toString());
                }
            }
            List<ReceiptDetail> receiptDetailList = getSalesReceiptMapper().getReceiptDetailListByReceipt(id);
            for (ReceiptDetail receiptDetail : receiptDetailList) {
                GoodsInfo goodsInfo = getGoodsInfoByID(receiptDetail.getGoodsid());
                receiptDetail.setGoodsInfo(goodsInfo);
                TaxType taxType = getTaxType(receiptDetail.getTaxtype());
                if (taxType != null) {
                    receiptDetail.setTaxtypename(taxType.getName());
                }
                StorageLocation storageLocation = getStorageLocation(receiptDetail.getStoragelocationid());
                if (storageLocation != null) {
                    receiptDetail.setStoragelocationname(storageLocation.getName());
                }
            }
            receipt.setReceiptDetailList(receiptDetailList);
        }
        return receipt;
    }

    @Override
    public Receipt getReceiptInfo(String id) throws Exception {
        Map map = getAccessColumnMap("t_sales_receipt", null);
        map.put("id", id);
        Receipt receipt = getSalesReceiptMapper().getReceipt(map);
        if (null != receipt) {
            DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(receipt.getSalesdept());
            if (departMent != null) {
                receipt.setSalesdeptname(departMent.getName());
            }
            Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(receipt.getSalesuser());
            if (personnel != null) {
                receipt.setSalesusername(personnel.getName());
            }
            Customer customer = getCustomerByID(receipt.getCustomerid());
            if (customer != null) {
                receipt.setCustomername(customer.getName());
            }
            Map map1 = new HashMap();
            map1.put("id",receipt.getHandlerid());
            Contacter contacter = getBaseFilesContacterMapper().getContacterDetail(map1);
            if (contacter != null) {
                receipt.setHandlername(contacter.getName());
            }
            Map total = getSalesReceiptMapper().getReceiptDetailTotal(id);
            if (total != null) {
                if (total.containsKey("taxamount")) {
                    receipt.setField01(total.get("taxamount").toString());
                }
                if (total.containsKey("notaxamount")) {
                    receipt.setField02(total.get("notaxamount").toString());
                }
                if (total.containsKey("tax")) {
                    receipt.setField03(total.get("tax").toString());
                }
            }
        }
        return receipt;
    }

    @Override
    public ReceiptDetail getReceiptDetail(String id) throws Exception {
        ReceiptDetail receiptDetail = getSalesReceiptMapper().getReceiptDetail(id);
        GoodsInfo goodsInfo = getGoodsInfoByID(receiptDetail.getGoodsid());
        receiptDetail.setGoodsInfo(goodsInfo);
        return receiptDetail;
    }

    @Override
    public ReceiptDetail getReceiptDetailInfo(String id, String billid) throws Exception {
        ReceiptDetail receiptDetail = getSalesReceiptMapper().getReceiptDetailByBillidAndId(id, billid);
        return receiptDetail;
    }

    @Override
    public boolean updateRejectBillRefer(String isrefer, String id) throws Exception {
        return getSalesRejectBillMapper().updateRejectBillRefer(isrefer, id) > 0;
    }

    @Override
    public boolean updateRejectBillClose(String id) throws Exception {
        RejectBill rejectBill = new RejectBill();
        rejectBill.setId(id);
        rejectBill.setClosetime(new Date());
        rejectBill.setStatus("4");
        return getSalesRejectBillMapper().updateRejectBillStatusWithNoVersion(rejectBill) > 0;
    }

    /**
     * 更新退货通知单应收日期
     *
     * @param id
     * @param duefromdate
     * @return
     * @throws Exception
     */
    @Override
    public boolean updateRejectBillDuefromdate(String id, String duefromdate) throws Exception {
        RejectBill rejectBill = new RejectBill();
        rejectBill.setId(id);
        rejectBill.setDuefromdate(duefromdate);
        return getSalesRejectBillMapper().updateRejectBillStatusWithNoVersion(rejectBill) > 0;
    }

    @Override
    public boolean updateRejectBillOpen(String id) throws Exception {
        RejectBill rejectBill = new RejectBill();
        rejectBill.setId(id);
        rejectBill.setStatus("3");
        return getSalesRejectBillMapper().updateRejectBillStatusWithNoVersion(rejectBill) > 0;
    }

    @Override
    public void updateRejectBillDetailBack(List<SaleRejectEnterDetail> detailList) throws Exception {
        if (detailList != null) {
            for (SaleRejectEnterDetail saleDetail : detailList) {
                RejectBillDetail detail = getSalesRejectBillMapper().getRejectBillDetail(saleDetail.getRejectdetailid());
                if (null != detail) {
                    RejectBillDetail billDetail = new RejectBillDetail();
                    billDetail.setTaxprice(saleDetail.getTaxprice());
                    billDetail.setNotaxprice(saleDetail.getNotaxprice());
                    BigDecimal taxamount=detail.getUnitnum().multiply(saleDetail.getTaxprice());
                    billDetail.setTaxamount(taxamount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                    BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, detail.getTaxtype());
                    billDetail.setNotaxamount(notaxamount);
                    //销售退货出库单成本价 回写销售退货通知单
                    billDetail.setCostprice(saleDetail.getCostprice());

                    billDetail.setUnitnum(saleDetail.getUnitnum());
                    billDetail.setAuxnum(saleDetail.getAuxnum());
                    billDetail.setAuxremainder(saleDetail.getAuxremainder());
                    billDetail.setTotalbox(saleDetail.getTotalbox());

                    billDetail.setInnummain(saleDetail.getUnitnum());
                    billDetail.setInnumaux(saleDetail.getAuxnum());
                    billDetail.setInamounttax(saleDetail.getTaxamount());
                    billDetail.setInamountnotax(saleDetail.getNotaxamount());
                    billDetail.setNoinnummain(detail.getUnitnum().subtract(saleDetail.getUnitnum()));
                    billDetail.setNoinnumaux(detail.getAuxnum().subtract(saleDetail.getAuxnum()));
                    billDetail.setNoinamounttax(detail.getTaxamount().subtract(saleDetail.getTaxamount()));
                    billDetail.setNoinamountnotax(detail.getNotaxamount().subtract(saleDetail.getNotaxamount()));
                    billDetail.setId(saleDetail.getBilldetailid());
                    billDetail.setBillid(saleDetail.getBillid());
                    getSalesRejectBillMapper().updateRejectBillDetailBack(billDetail);
                }
            }
        }
    }

    @Override
    public void updateClearRejectBillDetailBack(List<SaleRejectEnterDetail> detailList) throws Exception {
        if (detailList != null) {
            for (SaleRejectEnterDetail saleDetail : detailList) {
                RejectBillDetail billDetail = new RejectBillDetail();
                billDetail.setInnummain(new BigDecimal(0));
                billDetail.setInnumaux(new BigDecimal(0));
                billDetail.setInamounttax(new BigDecimal(0));
                billDetail.setInamountnotax(new BigDecimal(0));
                billDetail.setNoinnummain(new BigDecimal(0));
                billDetail.setNoinnumaux(new BigDecimal(0));
                billDetail.setNoinamounttax(new BigDecimal(0));
                billDetail.setNoinamountnotax(new BigDecimal(0));
                billDetail.setId(saleDetail.getBilldetailid());
                billDetail.setBillid(saleDetail.getBillid());
                getSalesRejectBillMapper().updateRejectBillDetailBack(billDetail);
            }
        }
    }

    @Override
    public RejectBill getRejectBill(String id) throws Exception {
        Map map = new HashMap();
        map.put("id", id);
        RejectBill rejectBill = getSalesRejectBillMapper().getRejectBill(map);
        if (null != rejectBill) {
            DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(rejectBill.getSalesdept());
            if (departMent != null) {
                rejectBill.setSalesdeptname(departMent.getName());
            }
            Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(rejectBill.getSalesuser());
            if (personnel != null) {
                rejectBill.setSalesusername(personnel.getName());
            }
            Customer customer = getCustomerByID(rejectBill.getCustomerid());
            if (customer != null) {
                rejectBill.setCustomername(customer.getName());
            }
            Contacter contacter = getContacterById(rejectBill.getHandlerid());
            if (contacter != null) {
                rejectBill.setHandlername(contacter.getName());
            }
            Map total = getSalesRejectBillMapper().getRejectBillDetailTotal(id);
            if (total != null) {
                if (total.containsKey("taxamount")) {
                    rejectBill.setField01(total.get("taxamount").toString());
                }
                if (total.containsKey("notaxamount")) {
                    rejectBill.setField02(total.get("notaxamount").toString());
                }
                if (total.containsKey("tax")) {
                    rejectBill.setField03(total.get("tax").toString());
                }
            }
            List<RejectBillDetail> billDetailList = getSalesRejectBillMapper().getRejectBillDetailListByBill(id);
            for (RejectBillDetail billDetail : billDetailList) {
                GoodsInfo goodsInfo = getGoodsInfoByID(billDetail.getGoodsid());
                billDetail.setGoodsInfo(goodsInfo);
                TaxType taxType = getTaxType(billDetail.getTaxtype());
                if (taxType != null) {
                    billDetail.setTaxtypename(taxType.getName());
                }
            }
            rejectBill.setBillDetailList(billDetailList);
        }
        return rejectBill;
    }

    @Override
    public RejectBill getRejectBillInfo(String id) throws Exception {
        Map map = getAccessColumnMap("t_sales_rejectbill", null);
        map.put("id", id);
        RejectBill rejectBill = getSalesRejectBillMapper().getRejectBill(map);
        if (null != rejectBill) {
            DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(rejectBill.getSalesdept());
            if (departMent != null) {
                rejectBill.setSalesdeptname(departMent.getName());
            }
            Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(rejectBill.getSalesuser());
            if (personnel != null) {
                rejectBill.setSalesusername(personnel.getName());
            }
            Customer customer = getCustomerByID(rejectBill.getCustomerid());
            if (customer != null) {
                rejectBill.setCustomername(customer.getName());
            }
            Contacter contacter = getContacterById(rejectBill.getHandlerid());
            if (contacter != null) {
                rejectBill.setHandlername(contacter.getName());
            }
            Map total = getSalesRejectBillMapper().getRejectBillDetailTotal(id);
            if (total != null) {
                if (total.containsKey("taxamount")) {
                    BigDecimal taxamount = (BigDecimal)total.get("taxamount");
                    rejectBill.setField01(taxamount.negate().toString());
                }
                if (total.containsKey("notaxamount")) {
                    BigDecimal notaxamount = (BigDecimal)total.get("notaxamount");
                    rejectBill.setField02(notaxamount.negate().toString());
                }
                if (total.containsKey("tax")) {
                    BigDecimal tax = (BigDecimal)total.get("tax");
                    rejectBill.setField03(tax.negate().toString());
                }
            }
        }
        return rejectBill;
    }

    @Override
    public RejectBillDetail getRejectBillDetail(String id) throws Exception {
        RejectBillDetail rejectBillDetail = getSalesRejectBillMapper().getRejectBillDetail(id);
        GoodsInfo goodsInfo = getGoodsInfoByID(rejectBillDetail.getGoodsid());
        rejectBillDetail.setGoodsInfo(goodsInfo);
        return rejectBillDetail;
    }

    @Override
    public RejectBillDetail getRejectBillDetailInfo(String id, String billid) throws Exception {
        RejectBillDetail rejectBillDetail = getSalesRejectBillMapper().getRejectBillDetailByBillidAndId(id, billid);
        return rejectBillDetail;
    }

    @Override
    public boolean updateReceiptInvoice(String isinvoice, String canceldate, String id) throws Exception {
        return getSalesReceiptMapper().updateReceiptInvoice(isinvoice, canceldate, id) > 0;
    }
	@Override
	public boolean updateReceiptInvoicebill(String isinvoicebill,
			String canceldate, String id) throws Exception {
		return getSalesReceiptMapper().updateReceiptInvoicebill(isinvoicebill, canceldate, id) > 0;
	}

    @Override
    public boolean updateRejectBillInvoice(String isinvoice, String canceldate, String id) throws Exception {
        return getSalesRejectBillMapper().updateRejectBillInvoice(isinvoice, canceldate, id) > 0;
    }

    @Override
	public boolean updateRejectBillInvoicebill(String isinvoicebill,
			String canceldate, String id) throws Exception {
		return getSalesRejectBillMapper().updateRejectBillInvoicebill(isinvoicebill, canceldate, id)>0;
	}

	@Override
	public boolean updateReceiptAndRejectBillInvoice(List<String> billList,SalesInvoice salesInvoice,String isinvoice)
			throws Exception {
        String invoicedate = getCurrentDate();
        //回写销售核销来源单据为发货回单的相关单据明细的是否开票标记
        //回写发货回单明细是否开票标记
		int i = getSalesReceiptMapper().updateReceiptDetailIsinvoice(salesInvoice.getId(), isinvoice);
        //回写发货单明细、退货入库单（直退）明细、退货通知单（直退）是否开票标记
		storageSaleService.updateSaleOutDetailIsinvoiceBySalesInvoiceid(salesInvoice.getId(), isinvoice,invoicedate);

        //更新销售核销来源单据为销售退货通知单相关单据明细的是否开票标记
        //回写退货通知单明细是否开票标记（售后退货）
		getSalesRejectBillMapper().updateRejectDetailIsinvoice(salesInvoice.getId(), isinvoice);
        //回写销售退货入库单明细是否开票标记（售后退货）
		storageSaleService.updateRejectEnterDetailIsinvoiceBySalesInvoiceid(salesInvoice.getId(), isinvoice,invoicedate);
		
		for(String billid : billList){
			Map map = new HashMap();//getAccessColumnMap("t_sales_receipt", null);
			map.put("id", billid);
			Receipt receipt = getSalesReceiptMapper().getReceipt(map);
			if(null!=receipt){
                //更新发货回单是否开票标记
                getSalesReceiptMapper().updateReceiptIsvoiceByBillid(billid);
                //更新退货通知单（直退）是否开票标记
                getSalesRejectBillMapper().updateErectRejectBillIsvoiceByBillid(billid);
				//更新发货单、退货入库单（直退）申请金额
				storageSaleService.updateSaleOutInvoiceAmount(billid);
                //更新回单开票日期
                getSalesReceiptMapper().updateSalesReceiptInvoicedate(receipt.getId(),isinvoice,invoicedate);
			}else{
				Map rejectmap = new HashMap();//getAccessColumnMap("t_sales_rejectbill", null);
				rejectmap.put("id", billid);
				RejectBill rejectBill = getSalesRejectBillMapper().getRejectBill(rejectmap);
				if(null!=rejectBill){
                    getSalesRejectBillMapper().updateRejectBillIsvoiceByBillid(billid);
					//更新发货单申请金额
					storageSaleService.updateRejectEnterInvoiceAmount(billid);
                    //更新退货通知单抽单日期
                    getSalesRejectBillMapper().upageRejectBillInvoicedate(rejectBill.getId(),isinvoice,invoicedate);
				}else{
					//判断该单据是否冲差单
					CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(billid);
					if(null!=customerPushBalance){
                        customerPushBalanceMapper.updateCustomerPushIsrefer(billid, isinvoice,invoicedate);
					}else{
						//更新应收款期初开票标志
						if("1".equals(isinvoice)){
							beginAmountMapper.updateBeginAmountInvoice(billid, invoicedate, isinvoice);
						}else{
							beginAmountMapper.updateBeginAmountInvoice(billid, "", isinvoice);
						}
						
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean updateReceiptAndRejectBillInvoiceBill(
			List<String> detailList, SalesInvoiceBill salesInvoiceBill,
			String isinvoicebill) throws Exception {
        String invoicebilldate = getCurrentDate();
        //更新发货回单明细列表是否实际开票状态
		getSalesReceiptMapper().updateReceiptDetailIsinvoicebill(salesInvoiceBill.getId(), isinvoicebill);
        //更新发货出库单、退货入库单（直退）明细、退货通知单（直退）明细列表是否实际开票状态
        storageSaleService.updateSaleOutDetailIsinvoicebillBySalesInvoicebillid(salesInvoiceBill.getId(), isinvoicebill,invoicebilldate);
		//更新退货通知单明细列表是否实际开票（售后退货）
        getSalesRejectBillMapper().updateRejectDetailIsinvoicebill(salesInvoiceBill.getId(), isinvoicebill);
        //更新退货入库单明细列表是否实际开票（售后退货）
        storageSaleService.updateRejectEnterDetailIsinvoicebillBySalesInvoicebillid(salesInvoiceBill.getId(), isinvoicebill,invoicebilldate);

		for(String billid : detailList){
			Map map = new HashMap();//getAccessColumnMap("t_sales_receipt", null);
			map.put("id", billid);
			Receipt receipt = getSalesReceiptMapper().getReceipt(map);
			if(null!=receipt){
                //更新回单开票状态
                getSalesReceiptMapper().updateReceiptIsvoicebillByBillid(billid);
                //更新退货通知单（直退）是否实际开票
                getSalesRejectBillMapper().updateErectRejectBillIsvoicebillByBillid(billid);
				 //根据发货回单回写回单冲差单的开票状态
                customerPushBalanceMapper.updateReceiptCustomerPushBalanceIsinvoicebill(billid,isinvoicebill);
                //更新发货单、退货通知单（直退）申请金额
                storageSaleService.updateSaleOutInvoicebillAmount(billid);
            }else{
				Map rejectmap = new HashMap();//getAccessColumnMap("t_sales_rejectbill", null);
				rejectmap.put("id", billid);
				RejectBill rejectBill = getSalesRejectBillMapper().getRejectBill(rejectmap);
				if(null!=rejectBill){
                    getSalesRejectBillMapper().updateRejectBillIsvoicebillByBillid(billid);
                    //更新销售退货入库单开票金额
                    storageSaleService.updateRejectEnterInvoicebillAmount(billid);
                }else{
					//判断该单据是否冲差单
					CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(billid);
					if(null!=customerPushBalance){
                        customerPushBalanceMapper.updateCustomerPushIsreferBill(billid, isinvoicebill);
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean updateReceiptAndRejectBillInvoiceByDelete(List<String> billList, SalesInvoice salesInvoice)
			throws Exception {
		for(String billid : billList){
			Map map = new HashMap();//getAccessColumnMap("t_sales_receipt", null);
			map.put("id", billid);
			Receipt receipt = getSalesReceiptMapper().getReceipt(map);
			if(null!=receipt){
				//更新发货单申请金额
				storageSaleService.updateSaleOutInvoiceAmount(billid);
			}else{
				Map rejectmap = new HashMap();//getAccessColumnMap("t_sales_rejectbill", null);
				rejectmap.put("id", billid);
				RejectBill rejectBill = getSalesRejectBillMapper().getRejectBill(rejectmap);
				if(null!=rejectBill){
					//更新退货入库单申请金额
					storageSaleService.updateRejectEnterInvoiceAmount(billid);
				}else{
					CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(billid);
					if(null!=customerPushBalance){
						customerPushBalanceMapper.updateCustomerPushBanlanceIsreferByID(billid);
					}else{
						beginAmountMapper.updateBeginAmountInvoice(billid, "", "0");
					}
					
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean updateReceiptAndRejectBillInvoicebillByDelete(
			List<String> detailList, SalesInvoiceBill salesInvoiceBill)
			throws Exception {
		for(String billid : detailList){
			Map map = new HashMap();//getAccessColumnMap("t_sales_receipt", null);
			map.put("id", billid);
			Receipt receipt = getSalesReceiptMapper().getReceipt(map);
			if(null!=receipt){
				//更新发货单开票金额
				storageSaleService.updateSaleOutInvoicebillAmount(billid);
			}else{
				Map rejectmap = new HashMap();//getAccessColumnMap("t_sales_rejectbill", null);
				rejectmap.put("id", billid);
				RejectBill rejectBill = getSalesRejectBillMapper().getRejectBill(rejectmap);
				if(null!=rejectBill){
					//更新退货入库单开票金额
					storageSaleService.updateRejectEnterInvoicebillAmount(billid);
				}else{
					CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(billid);
					if(null!=customerPushBalance){
						customerPushBalanceMapper.updateCustomerPushBanlanceIsinvoicebillByID(billid);
					}
				}
			}
		}
		return true;
	}

    @Override
    public boolean updateReceiptAndRejectBillInvoiceWriteoff(List<String> billList, SalesInvoice salesInvoice,String writeoffdate)
            throws Exception {
        SysUser sysUser = getSysUser();
        //回写销售核销来源单据为发货回单的相关单据明细的是否核销标记
        getSalesReceiptMapper().updateReceiptDetailIsinvoiceWriteoff(salesInvoice.getId(),writeoffdate);
        //更新发货单、退货入库单（直退）、退货通知单（直退） 明细数据的核销状态
        storageSaleService.updateSaleOutDetailIswriteoffByReceipt(salesInvoice.getId(),writeoffdate);

        //更新退货通知单明细是否核销（售后退货）
        getSalesRejectBillMapper().updateRejectDetailWriteoff(salesInvoice.getId(),writeoffdate);
        //更新退货入库单明细数据的核销状态（售后退货）
        storageSaleService.updateRejectEnterDetailIswriteoffByReject(salesInvoice.getId(),writeoffdate);

        //关闭核销 发票相关的冲差单
        customerPushBalanceMapper.closeCustomerPushBanlanceBySourceid(salesInvoice.getId(), sysUser.getUserid(), sysUser.getName(),writeoffdate);
        for (String billid : billList) {
            Map map = getAccessColumnMap("t_sales_receipt", null);
            map.put("id", billid);
            Receipt receipt = getSalesReceiptMapper().getReceipt(map);
            if (null != receipt) {
                List<ReceiptDetail> receiptList = getSalesReceiptMapper().getReceiptDetailListByReceipt(billid);
                int writeoffnum = 0;
                for (ReceiptDetail receiptDetail : receiptList) {
                    if ("1".equals(receiptDetail.getIsinvoice())) {
                        writeoffnum++;
                    }
                }
                if (writeoffnum == receiptList.size()) {
                    //核销完 关闭回单
                    updateReceiptClose(billid);
                    updateReceiptInvoice("2", writeoffdate, billid);
                    //更新退货入库单（直退）、退货通知单（直退）的核销状态
                    storageSaleService.updateRejectEnterWriteoffByReceipt(billid);
                } else {
                    updateReceiptInvoice("5", null, billid);
                }
                //更新发货单核销金额
                storageSaleService.updateSaleOutWriteOffAmount(billid);
                //关闭核销 回单生成的冲差
                customerPushBalanceMapper.closeCustomerPushBanlanceByReceiptid(receipt.getId(), sysUser.getUserid(), sysUser.getName());
            } else {
                Map rejectmap = new HashMap();
                rejectmap.put("id", billid);
                RejectBill rejectBill = getSalesRejectBillMapper().getRejectBill(rejectmap);
                if (null != rejectBill) {
                    int writeoffnum = 0;
                    List<RejectBillDetail> rejectList = getSalesRejectBillMapper().getRejectBillDetailListByBill(billid);
                    for (RejectBillDetail rejectBillDetail : rejectList) {
                        if ("1".equals(rejectBillDetail.getIsinvoice())) {
                            writeoffnum++;
                        }
                    }
                    if (writeoffnum == rejectList.size()) {
                        //核销完 关闭销售退货通知单
                        updateRejectBillClose(billid);
                        updateRejectBillInvoice("2",writeoffdate, billid);
                    } else {
                        updateRejectBillInvoice("5", null, billid);
                    }
                    storageSaleService.updateRejectEnterWriteoffAmount(billid);
                } else {
                    //客户应收款期初核销
                    beginAmountMapper.updateBeginAmountWriteoff(billid, writeoffdate, "1", sysUser.getUserid(), sysUser.getName());
                }
            }
        }
        return true;
    }

	@Override
	public boolean updateReceiptAndRejectBillInvoiceBackWriteoff(
			List<String> billList, SalesInvoice salesInvoice) throws Exception {
        //更新销售发货回单明细的核销状态
		getSalesReceiptMapper().updateReceiptDetailBackInvoiceWriteoff(salesInvoice.getId());
		//更新发货单、退货入库单（直退）、退货通知单（直退） 明细数据的核销状态（反核销）
		storageSaleService.updateSaleOutDetailBackWriteoffByReceipt(salesInvoice.getId());

        //更新销售发货通知单明细的核销状态（售后退货）
        getSalesRejectBillMapper().updateRejectDetailBackWriteoff(salesInvoice.getId());
		//更新退货入库单 明细数据的核销状态（售后退货）
		storageSaleService.updateRejectEnterDetailBackWriteoffByReject(salesInvoice.getId());
		//发票相关的冲差单恢复审核通过状态，计算核销金额
		customerPushBalanceMapper.openCustomerPushBanlanceByInvoiceid(salesInvoice.getId());
//		if(null!=salesInvoice.getInvoiceno() && !"".equals(salesInvoice.getInvoiceno())){
//			customerPushBalanceMapper.openCustomerPushBanlanceByInvoiceid(salesInvoice.getInvoiceno());
//		}
		for(String billid : billList){
			Map map = getAccessColumnMap("t_sales_receipt", null);
			map.put("id", billid);
			Receipt receipt = getSalesReceiptMapper().getReceipt(map);
			if(null!=receipt){
				List<ReceiptDetail> receiptList = getSalesReceiptMapper().getReceiptDetailListByReceipt(billid);
				int writeoffnum = 0;
				for(ReceiptDetail receiptDetail : receiptList){
					if("1".equals(receiptDetail.getIsinvoice())){
						writeoffnum++;
					}
				}
				//明细开票数量与明细总数量一致，则回单开票状态为开票，否则为开票中
				if(writeoffnum==receiptList.size()){
					backReceiptClose(billid);
					updateReceiptInvoice("1", "", billid);
				}else{
					updateReceiptInvoice("4", "", billid);
				}
                //退货入库（直退）、发货通知单（直退）为未核销状态
                storageSaleService.updateRejectEnterBackWriteoffByReceipt(billid);
				//发货单、退货入库单（直退）核销金额清零
				storageSaleService.clearSaleOutWriteOffAmount(billid);
				//回写回单生成的冲差单状态审核通过
				customerPushBalanceMapper.openCustomerPushBanlanceByReceiptid(receipt.getId());
			}else{
				Map rejectmap = new HashMap();
				rejectmap.put("id", billid);
				RejectBill rejectBill = getSalesRejectBillMapper().getRejectBill(rejectmap);
				if(null!=rejectBill){
					int writeoffnum = 0;
					List<RejectBillDetail> rejectList = getSalesRejectBillMapper().getRejectBillDetailListByBill(billid);
					for(RejectBillDetail rejectBillDetail : rejectList){
						if("1".equals(rejectBillDetail.getIsinvoice())){
							writeoffnum++;
						}
					}
					//明细开票数量与明细总数量一致，则回单开票状态为开票，否则为开票中
					if(writeoffnum==rejectList.size()){
						backRejectBillClose(billid);
						updateRejectBillInvoice("1", "", billid);
					}else{
						updateRejectBillInvoice("4", null, billid);
					}
					storageSaleService.clearRejectEnterWriteoffAmount(billid);
				}else{
					//客户应收款期初反核销
					beginAmountMapper.updateBeginAmountWriteoff(billid, "", "0", "", "");
				}
			}
		}
		return true;
	}

    @Override
    public RejectBill getRejectBillByDetailid(String detailid) throws Exception {
        RejectBill rejectBill = getSalesRejectBillMapper().getRejectBillByDetailid(detailid);
        return rejectBill;
    }

    @Override
    public List getReceiptDetailBrandDiscountList(String billid, String brandid)
            throws Exception {
        List list = getSalesReceiptMapper().getReceiptDetailBrandDiscountList(billid, brandid);
        return list;
    }

    @Override
    public List getDispatchBillDetailBrandDiscountList(String id, String brandid)
            throws Exception {
        List list = getSalesDispatchBillMapper().getDispatchBillDetailBrandDiscountList(id, brandid);
        return list;
    }

    @Override
    public List getReceiptUnWriteoffListByCustomerid(String id) throws Exception {
        List<Receipt> list = getSalesReceiptMapper().getReceiptUnWriteoffListByCustomerid(id);
        for (Receipt receipt : list) {
            Personnel indoorPerson = getPersonnelById(receipt.getIndooruserid());
            if (null != indoorPerson) {
                receipt.setIndoorusername(indoorPerson.getName());
            }
            DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(receipt.getSalesdept());
            if (departMent != null) {
                receipt.setSalesdept(departMent.getName());
            }
            Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(receipt.getSalesuser());
            if (personnel != null) {
                receipt.setSalesuser(personnel.getName());
            }
            Map map = new HashMap();
            map.put("id", receipt.getCustomerid());
            Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(map);
            if (customer != null) {
                receipt.setCustomername(customer.getName());
            }
            //获取总店客户
            Customer headCustomer = getHeadCustomer(receipt.getCustomerid());
            if (null != headCustomer) {
                receipt.setHeadcustomerid(headCustomer.getId());
                receipt.setHeadcustomername(headCustomer.getName());
            }
            map.put("id", receipt.getHandlerid());
            Contacter contacter = getBaseFilesContacterMapper().getContacterDetail(map);
            if (contacter != null) {
                receipt.setHandlerid(contacter.getName());
            }
            Map total = getSalesReceiptMapper().getReceiptDetailTotal(receipt.getId());
            if (total != null) {
                if (total.containsKey("taxamount")) {
                    receipt.setTotaltaxamount((BigDecimal) total.get("taxamount"));
                }
                if (total.containsKey("notaxamount")) {
                    receipt.setTotalnotaxamount((BigDecimal) total.get("notaxamount"));
                }
                if (total.containsKey("tax")) {
                    receipt.setTotaltax((BigDecimal) total.get("tax"));
                }
                if (total.containsKey("receipttaxamount")) {
                    receipt.setTotalreceipttaxamount((BigDecimal) total.get("receipttaxamount"));
                }
            }
        }
        return list;
    }

    @Override
    public boolean isRejectBillDetailRelate(String id) throws Exception {
        int i = getSalesRejectBillMapper().isRejectBillDetailRelate(id);
        return i == 0;
    }

    @Override
    public List getRejectBillByReceiptid(String receiptid)
            throws Exception {
        List list = getSalesRejectBillMapper().getRejectBillByReceiptid(receiptid);
        return list;
    }

    @Override
    public boolean updateRejectBillDetailBack(RejectBillDetail rejectBillDetail)
            throws Exception {
        int i = getSalesRejectBillMapper().updateRejectBillDetailBack(rejectBillDetail);
        return i > 0;
    }

    @Override
    public List getReceiptNoWriteoffListByCustomerid(String customerid)
            throws Exception {
        List list = getSalesReceiptMapper().getReceiptNoWriteoffListByCustomerid(customerid);
        return list;
    }

    @Override
    public boolean checkIsDoneApplyWriteOffCaseReceipt(String saleoutid) throws Exception {
        List<String> list = getSalesReceiptMapper().getReceiptidListBySaleoutid(saleoutid);
        boolean flag = true;
        for(String receiptid : list){
            Map map = new HashMap();
            map.put("id",receiptid);
            Receipt receipt = getSalesReceiptMapper().getReceipt(map);
            if(null == receipt || (!"3".equals(receipt.getStatus()) && !"4".equals(receipt.getStatus()))){
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public boolean updateReceiptInvoicedate(Map map) throws Exception {
        return getSalesReceiptMapper().updateReceiptInvoicedate(map) > 0;
    }

    @Override
    public boolean updateRejectBillInvoicedate(Map map) throws Exception {
        return getSalesRejectBillMapper().updateSaleRejectBillInvoicedate(map) > 0;
    }

}

