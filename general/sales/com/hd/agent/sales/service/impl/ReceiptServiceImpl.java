/**
 * @(#)ReceiptServiceImpl.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 30, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.model.*;
import com.hd.agent.account.service.*;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.*;
import com.hd.agent.sales.service.IReceiptService;
import com.hd.agent.sales.service.ext.IRejectBillExtService;
import com.hd.agent.storage.model.Saleout;
import com.hd.agent.storage.service.IStorageForSalesService;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 回单service方法
 *
 * @author zhengziyong
 */
public class ReceiptServiceImpl extends BaseSalesServiceImpl implements IReceiptService {

    //出库单对外接口
    private IStorageForSalesService storageForSalesService;

    private ISalesInvoiceService salesInvoiceService;

    private ISalesStatementService salesStatementService;

    private ICollectionOrderService collectionOrderService;
    //退货通知单对外接口
    private IRejectBillExtService salesRejectBillExtService;
    /**
     * 客户冲差service
     */
    private ICustomerPushBanlanceService customerPushBanlanceService;
    /**
     * 客户应收款期初
     */
    private IBeginAmountService beginAmountService;

    public ICollectionOrderService getCollectionOrderService() {
        return collectionOrderService;
    }

    public void setCollectionOrderService(ICollectionOrderService collectionOrderService) {
        this.collectionOrderService = collectionOrderService;
    }

    public ISalesStatementService getSalesStatementService() {
        return salesStatementService;
    }

    public void setSalesStatementService(ISalesStatementService salesStatementService) {
        this.salesStatementService = salesStatementService;
    }

    public ISalesInvoiceService getSalesInvoiceService() {
        return salesInvoiceService;
    }

    public void setSalesInvoiceService(ISalesInvoiceService salesInvoiceService) {
        this.salesInvoiceService = salesInvoiceService;
    }

    public IStorageForSalesService getStorageForSalesService() {
        return storageForSalesService;
    }

    public void setStorageForSalesService(
            IStorageForSalesService storageForSalesService) {
        this.storageForSalesService = storageForSalesService;
    }

    public IRejectBillExtService getSalesRejectBillExtService() {
        return salesRejectBillExtService;
    }

    public void setSalesRejectBillExtService(
            IRejectBillExtService salesRejectBillExtService) {
        this.salesRejectBillExtService = salesRejectBillExtService;
    }

    public ICustomerPushBanlanceService getCustomerPushBanlanceService() {
        return customerPushBanlanceService;
    }

    public void setCustomerPushBanlanceService(
            ICustomerPushBanlanceService customerPushBanlanceService) {
        this.customerPushBanlanceService = customerPushBanlanceService;
    }

    public IBeginAmountService getBeginAmountService() {
        return beginAmountService;
    }

    public void setBeginAmountService(IBeginAmountService beginAmountService) {
        this.beginAmountService = beginAmountService;
    }

    @Override
    public boolean addReceipt(Receipt receipt) throws Exception {
        return super.addReceipt(receipt);
    }

    @Override
    public boolean updateReceipt(Receipt receipt) throws Exception {
        Map map = new HashMap();
        map.put("id", receipt.getId());
        Receipt oldreceipt = getSalesReceiptMapper().getReceipt(map);
		if(null==oldreceipt || "3".equals(oldreceipt.getStatus()) || "4".equals(oldreceipt.getStatus())){
            return false;
        }
        //修改客户单号
        if(StringUtils.isNotEmpty(receipt.getSaleorderid())){
            getSalesOrderMapper().updateOrderSourceid(receipt.getSourceid(),receipt.getSaleorderid()) ;
        }
        //获取销售区域
        if (null == receipt.getSalesarea() || "".equals(receipt.getSalesarea())) {
            Customer customer = getCustomerByID(receipt.getCustomerid());
            if (null != customer) {
                receipt.setSalesarea(customer.getSalesarea());
                receipt.setPcustomerid(customer.getPid());
                receipt.setCustomersort(customer.getCustomersort());
            }
        }
//		getSalesReceiptMapper().deleteReceiptDetailWithoutDiscountByReceiptId(receipt.getId());
        List<ReceiptDetail> receiptDetailList = receipt.getReceiptDetailList();
        if (receiptDetailList.size() > 0) {
            List detailAddlist = new ArrayList();
            for (ReceiptDetail receiptDetail : receiptDetailList) {
                if (receiptDetail != null) {
                    receiptDetail.setBillid(receipt.getId());
                    //判断成本价是否存在
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(receiptDetail.getGoodsid());
                    if (null != goodsInfo) {
                        //计算辅单位数量
                        Map auxmap = countGoodsInfoNumber(receiptDetail.getGoodsid(), receiptDetail.getAuxunitid(), receiptDetail.getUnitnum());
                        if (auxmap.containsKey("auxnum")) {
                            receiptDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
                        }
                        //计算辅单位数量
                        Map receiptauxmap = countGoodsInfoNumber(receiptDetail.getGoodsid(), receiptDetail.getAuxunitid(), receiptDetail.getReceiptnum());
                        if (receiptauxmap.containsKey("auxnum")) {
                            receiptDetail.setReceiptbox((BigDecimal) receiptauxmap.get("auxnum"));
                        }
                        receiptDetail.setGoodssort(goodsInfo.getDefaultsort());
                        receiptDetail.setBrandid(goodsInfo.getBrand());
                        receiptDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), receipt.getCustomerid()));
                        //厂家业务员
                        receiptDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), receipt.getCustomerid()));
                        //获取品牌部门
                        Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                        if (null != brand) {
                            receiptDetail.setBranddept(brand.getDeptid());
                        }
                        receiptDetail.setSupplierid(goodsInfo.getDefaultsupplier());

                        if (receiptDetail.getReceiptnum().compareTo(receiptDetail.getUnitnum()) == 1) {
                            receiptDetail.setReceiptnum(receiptDetail.getUnitnum());
                        }
                    }
                    if ("0".equals(receiptDetail.getIsdiscount()) && null != receiptDetail.getReceiptnum()) {
                        BigDecimal receiptTaxAmount = receiptDetail.getReceiptnum().multiply(receiptDetail.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);
                        BigDecimal receiptNoTaxAmount = getNotaxAmountByTaxAmount(receiptTaxAmount, receiptDetail.getTaxtype());
                        receiptDetail.setReceipttaxamount(receiptTaxAmount);
                        receiptDetail.setReceiptnotaxamount(receiptNoTaxAmount);

                        getSalesReceiptMapper().updateReceiptDetail(receiptDetail);
//						detailAddlist.add(receiptDetail);
                    }
                }
            }
//			getSalesReceiptMapper().addReceiptDetailList(detailAddlist);
        }
        SysUser sysUser = getSalesIndoorSysUserByCustomerid(receipt.getCustomerid());
        if (sysUser != null) {
            receipt.setIndooruserid(sysUser.getPersonnelid());
        }
        return getSalesReceiptMapper().updateReceipt(receipt) > 0;
    }

    @Override
    public boolean deleteReceipt(String id) throws Exception {
        Map map = new HashMap();
        map.put("id", id);
        Receipt receipt = getSalesReceiptMapper().getReceipt(map);
        storageForSalesService.updateSaleoutRefer("0", receipt.getSaleorderid()); //更新上游单据参照状态，0为未参照
        return super.deleteReceipt(id);
    }

    @Override
    public Map auditReceipt(String type, String id) throws Exception {
        Map result = new HashMap();
        SysUser sysUser = getSysUser();
        Map map = new HashMap();
        map.put("id", id);
        Receipt receipt = getSalesReceiptMapper().getReceipt(map);

        String billId = "";
        if(null == receipt){
            result.put("flag", false);
            return result;
        }
        if ("1".equals(type)) { //审核
            if (!"2".equals(receipt.getStatus())) { //只有状态为2（保存状态）才可进行审核
                result.put("flag", false);
                return result;
            }
            //判断回单是否可以审核 发货单是否全部审核完毕
            boolean canaudit = storageForSalesService.receiptCanAuditBySaleorderid(receipt.getSaleorderid());
            if (!canaudit) {
                result.put("flag", false);
                result.put("msg", "相同订单下的发货单未全部发货，回单不能审核！");
                return result;
            }

            if (null == sysUser) {
                sysUser = getSysUserById(receipt.getAdduserid());
            }
            Receipt receipt2 = new Receipt();
            receipt2.setAudituserid(sysUser.getUserid());
            receipt2.setAuditusername(sysUser.getName());
            receipt2.setAudittime(new Date());
            receipt2.setStatus("3");
            receipt2.setId(id);
            receipt2.setSaleorderid(receipt.getSaleorderid());
            receipt2.setBusinessdate(getAuditBusinessdate(receipt.getBusinessdate()));
            Date businessdate = CommonUtils.stringToDate(receipt2.getBusinessdate());
            //来源发货单
            //应收日期计算方式 1按发货日期计算 2按回单审核日期计算
            String receivabletype = getSysParamValue("RECEIVABLETYPE");
            if ("2".equals(receivabletype)) {
                //判断客户是否有品牌结算方式
                boolean isHasBrandSettle = isCustomerHasBrandSettle(receipt.getCustomerid());
                if (isHasBrandSettle) {
                    List<String> brandList = getSalesReceiptMapper().getReceiptBrandListById(id);
                    if(null!=brandList && brandList.size()>0){
                        if(brandList.size()==1){
                            String brandid = brandList.get(0);
                            String duefromdate = getReceiptDateBySettlement(businessdate,receipt.getCustomerid(),brandid);
                            getSalesReceiptMapper().updateReceiptDetailDuefromdateByBrandidAndBillid(receipt.getId(), brandid, duefromdate);
                            getBaseSaleoutMapper().updateSaleOutDetailDuefromdateByOrderid(receipt.getSaleorderid(), brandid, duefromdate);
                            //更新发货单应收日期
                            getBaseSaleoutMapper().updateSaleOutDuefromdateByOrderid(receipt.getSaleorderid(),brandid, duefromdate);
                            receipt2.setDuefromdate(duefromdate);
                            receipt.setDuefromdate(duefromdate);
                        }else{
                            for (String brandid : brandList) {
                                String duefromdate = getReceiptDateBySettlement(businessdate,receipt.getCustomerid(),brandid);
                                getSalesReceiptMapper().updateReceiptDetailDuefromdateByBrandidAndBillid(receipt.getId(), brandid, duefromdate);
                                getBaseSaleoutMapper().updateSaleOutDetailDuefromdateByOrderid(receipt.getSaleorderid(), brandid, duefromdate);
                                //更新发货单应收日期
                                getBaseSaleoutMapper().updateSaleOutDuefromdateByOrderid(receipt.getSaleorderid(),brandid, duefromdate);
                            }
                            String billDuefromdate = getReceiptDateBySettlement(businessdate,receipt.getCustomerid(),null);
                            receipt2.setDuefromdate(billDuefromdate);
                            receipt.setDuefromdate(billDuefromdate);
                            //更新发货单应收日期
                            List<Saleout> saleoutList = getBaseSaleoutMapper().getSaleOutInfoBySaleorderid(receipt.getSaleorderid());
                            for(Saleout saleout : saleoutList){
                                //获取发货单中的品牌列表
                                List<String> sbrandList = getBaseSaleoutMapper().getSaleOutBrandListById(saleout.getId());
                                if (null != sbrandList && sbrandList.size() > 0) {
                                    if (sbrandList.size() == 1) {
                                        String brandid = sbrandList.get(0);
                                        String duefromdate = getReceiptDateBySettlement(businessdate, saleout.getCustomerid(), brandid);
                                        getBaseSaleoutMapper().updateSaleOutDuefromdate(saleout.getId(), duefromdate);
                                    } else {
                                        String duefromdate = getReceiptDateBySettlement(businessdate, saleout.getCustomerid(), null);
                                        getBaseSaleoutMapper().updateSaleOutDuefromdate(saleout.getId(), duefromdate);
                                    }
                                } else {
                                    String duefromdate = getReceiptDateBySettlement(businessdate, saleout.getCustomerid(), null);
                                    getBaseSaleoutMapper().updateSaleOutDuefromdate(saleout.getId(), duefromdate);
                                }
                            }

                        }
                    }else{
                        String billDuefromdate = getReceiptDateBySettlement(businessdate,receipt.getCustomerid(),null);
                        getSalesReceiptMapper().updateReceiptDetailDuefromdateByBrandidAndBillid(receipt.getSaleorderid(), null, billDuefromdate);
                        getBaseSaleoutMapper().updateSaleOutDetailDuefromdateByOrderid(receipt.getSaleorderid(), null, billDuefromdate);
                        getBaseSaleoutMapper().updateSaleOutDuefromdateByOrderid(receipt.getSaleorderid(),null, billDuefromdate);
                        receipt2.setDuefromdate(billDuefromdate);
                        receipt.setDuefromdate(billDuefromdate);
                    }
                }else{
                    String billDuefromdate = getReceiptDateBySettlement(businessdate,receipt.getCustomerid(),null);
                    getSalesReceiptMapper().updateReceiptDetailDuefromdateByBrandidAndBillid(receipt.getId(), null, billDuefromdate);
                    getBaseSaleoutMapper().updateSaleOutDetailDuefromdateByOrderid(receipt.getSaleorderid(), null, billDuefromdate);
                    getBaseSaleoutMapper().updateSaleOutDuefromdateByOrderid(receipt.getSaleorderid(),null, billDuefromdate);
                    receipt2.setDuefromdate(billDuefromdate);
                    receipt.setDuefromdate(billDuefromdate);
                }
            }

            List receiptDetailList = getSalesReceiptMapper().getReceiptDetailListByReceipt(id);
            receipt.setReceiptDetailList(receiptDetailList);
            //生成冲差单
            //根据价格调整后的金额 差异 生成冲差单
            customerPushBanlanceService.addCustomerPushBanlanceByReceipt(receipt, receiptDetailList);
            //回单的成本价参照发货单成本价
            getSalesReceiptMapper().updateReceiptDetailCostprice(id);
            storageForSalesService.updateSaleOutBack(receiptDetailList);//回写出库单
            if (null != receipt.getBillno()) {
                String[] saleoutids = receipt.getBillno().split(",");
                for (String saleoutid : saleoutids) {
                    storageForSalesService.updateSaleOutClose(saleoutid);//关闭出库单
                }
            }
            //如果数量不对则需生成退货通知单（现改为直接生成退货入库单）
            //判断是否有直退 有直退则验收下游销售退货入库单
            if (salesRejectBillExtService.isAutoAddRejectBill(receipt)) {
                String receiptAndRejectType = getSysParamValue("ReceiptAndRejectType");
                if ("2".equals(receiptAndRejectType)) {
                    //自动生成退货通知单并且审核关联
                    billId = salesRejectBillExtService.addRejectBillAuto(receipt);
                } else {
                    storageForSalesService.updateSaleRejectEnterCheckByReceipt(receipt, receiptDetailList);
                }
            }
            getSalesReceiptMapper().updateReceiptInvoice("3", null, id);//设置为可以生成发票状态
            if(!"1".equals(receipt.getIsinvoicebill()) && !"4".equals(receipt.getIsinvoicebill())){
                getSalesReceiptMapper().updateReceiptInvoicebill("3", null, id);//设置为可以生成开票状态
            }
            receipt2.setIsrefer("1");
            boolean flag = getSalesReceiptMapper().updateReceiptStatus(receipt2) > 0;
            result.put("flag", flag);
            result.put("billId", billId);
		}
		else if("2".equals(type)){ //反审
            if (!"3".equals(receipt.getStatus())) {
                result.put("flag", false);
                return result;
            } else if ("3".equals(receipt.getStatus()) && ((!"3".equals(receipt.getIsinvoice()) && !"0".equals(receipt.getIsinvoice())) || (!"3".equals(receipt.getIsinvoicebill()) && !"0".equals(receipt.getIsinvoicebill())))) {
                result.put("invoiceflag", false);
                return result;
            }
            List receiptDetailList = getSalesReceiptMapper().getReceiptDetailListByReceipt(id);
            receipt.setReceiptDetailList(receiptDetailList);
            //删除相关冲差单
            customerPushBanlanceService.deleteCustomerPushBanlanceByReceiptid(receipt.getId());
            if (salesRejectBillExtService.isAutoAddRejectBill(receipt)) {
                String receiptAndRejectType = getSysParamValue("ReceiptAndRejectType");
                if ("2".equals(receiptAndRejectType)) {
                    //自动生成退货通知单并且审核关联
                    salesRejectBillExtService.oppauditAndDeleteRejectBillByReceipt(receipt.getId());
                } else {
                    boolean checkflag = storageForSalesService.updateSaleRejectEnterCheckByReceiptOpen(receipt);
                }
            }
            if ("1".equals(receipt.getSource())) { //参照出库单则需要回写
                storageForSalesService.updateClearSaleOutBack(receiptDetailList);//回写出库单
                if (null != receipt.getBillno()) {
                    String[] saleoutids = receipt.getBillno().split(",");
                    for (String saleoutid : saleoutids) {
                        storageForSalesService.updateSaleOutOpen(saleoutid);//重置出库单
                    }
                }

            }
            Receipt receipt2 = new Receipt();
            receipt2.setStatus("2");
            receipt2.setId(id);
            receipt2.setIsinvoice("0");
			receipt2.setIsinvoicebill("0");
            boolean flag = getSalesReceiptMapper().updateReceiptStatus(receipt2) > 0;
            result.put("flag", flag);
		}
		return result;
	}

    @Override
    public Map auditMultiReceipt(String ids) throws Exception {
        int failure = 0;
        int success = 0;
        boolean sFlag = true;
        String msg = "";
        if (StringUtils.isNotEmpty(ids)) {
            if (ids.endsWith(",")) {
                ids = ids.substring(0, ids.length() - 1);
            }
            String[] idArr = ids.split(",");
            for (String id : idArr) {
                Map map = auditReceipt("1", id);
                boolean flag = (Boolean) map.get("flag");
                if (flag) {
                    success++;
				}
				else{
					failure++;
					if(map.containsKey("msg")){
						msg += "<br/>"+map.get("msg");
					}
				}
			}
		}
		Map map = new HashMap();
		map.put("flag", sFlag);
		map.put("failure", failure);
		map.put("success", success);
		map.put("msg", msg);
		return map;
	}

    @Override
    public Receipt getReceipt(String id) throws Exception {
        Map map = new HashMap();
        map.put("id", id);
        Receipt receipt = getSalesReceiptMapper().getReceipt(map);
        if (null != receipt) {
            Customer customer = getCustomerByID(receipt.getCustomerid());
            if (null != customer) {
                receipt.setCustomername(customer.getName());
            }
            Contacter contacter = getContacterById(receipt.getHandlerid());
            if (null != contacter) {
                receipt.setHandlername(contacter.getName());
            }
            DepartMent departMent = getDepartmentByDeptid(receipt.getSalesdept());
            if (null != departMent) {
                receipt.setSalesdeptname(departMent.getName());
            }
            Personnel personnel = getPersonnelById(receipt.getSalesuser());
            if (null != personnel) {
                receipt.setSalesusername(personnel.getName());
            }
            Payment payment = getPaymentByID(receipt.getPaytype());
            if (null != payment) {
                receipt.setPaytypename(payment.getName());
            }
            Settlement settlement = getSettlementByID(receipt.getSettletype());
            if (null != settlement) {
                receipt.setSettletypename(settlement.getName());
            }
            StorageInfo storageInfo = getStorageInfoByID(receipt.getStorageid());
            if (null != storageInfo) {
                receipt.setStoragename(storageInfo.getName());
            }
            List<ReceiptDetail> receiptDetailList = getSalesReceiptMapper().getReceiptDetailListSumDiscount(id);
            for (ReceiptDetail receiptDetail : receiptDetailList) {
                TaxType taxType = getTaxType(receiptDetail.getTaxtype());
                if (taxType != null) {
                    receiptDetail.setTaxtypename(taxType.getName());
                }
                StorageLocation storageLocation = getStorageLocation(receiptDetail.getStoragelocationid());
                if (storageLocation != null) {
                    receiptDetail.setStoragelocationname(storageLocation.getName());
                }
                //折扣显示处理
                GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(getAllGoodsInfoByID(receiptDetail.getGoodsid()));
                if ("1".equals(receiptDetail.getIsdiscount())) {
                    goodsInfo.setBarcode(null);
                    goodsInfo.setBoxnum(null);
                    goodsInfo.setName("(折扣)" + goodsInfo.getName());
                    receiptDetail.setUnitnum(null);
                    receiptDetail.setAuxnumdetail(null);
                    receiptDetail.setTaxprice(null);
                    if ("1".equals(receiptDetail.getIsbranddiscount())) {
                        receiptDetail.setGoodsid("");
                        goodsInfo.setName("(折扣)" + goodsInfo.getBrandName());
                        receiptDetail.setIsdiscount("2");
                    }
                }
                receiptDetail.setGoodsInfo(goodsInfo);
                if (null != goodsInfo && null != goodsInfo.getBoxnum()) {
                    receiptDetail.setBuyprice(goodsInfo.getNewbuyprice());
                    receiptDetail.setBoxprice(goodsInfo.getBoxnum().multiply(receiptDetail.getTaxprice() == null ? BigDecimal.ZERO : receiptDetail.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                }
            }
            receipt.setReceiptDetailList(receiptDetailList);
        }
        return receipt;
    }

    @Override
    public PageData getReceiptData(PageMap pageMap) throws Exception {
        String dataSql = getDataAccessRule("t_sales_receipt", "t");
        pageMap.setDataSql(dataSql);
        pageMap.setQueryAlias("z");
        pageMap.getCondition().put("today",CommonUtils.getTodayDataStr());
        List<Receipt> list  = getSalesReceiptMapper().getReceiptList(pageMap);
        for (Receipt receipt : list) {
            //加客户单号
            Personnel indoorPerson = getPersonnelById(receipt.getIndooruserid());
            if (null != indoorPerson) {
                receipt.setIndoorusername(indoorPerson.getName());
            }
            DepartMent departMent = getDepartmentByDeptid(receipt.getSalesdept());
            if (departMent != null) {
                receipt.setSalesdeptname(departMent.getName());
            }
            Personnel personnel = getPersonnelById(receipt.getSalesuser());
            if (personnel != null) {
                receipt.setSalesusername(personnel.getName());
            }
            SalesArea salesArea = getSalesareaByID(receipt.getSalesarea());
            if(null != salesArea){
                receipt.setSalesareaname(salesArea.getThisname());
            }
            Customer customer = getCustomerByID(receipt.getCustomerid());
            if (customer != null) {
                receipt.setCustomername(customer.getName());
                if ("1".equals(customer.getIslongterm())) {
                    receipt.setAccounttypename("账期");
                } else if ("1".equals(customer.getIscash())) {
                    receipt.setAccounttypename("现款");
                }
                //获取总店客户
                Customer headCustomer = getCustomerByID(customer.getPid());
                if (null != headCustomer) {
                    receipt.setHeadcustomerid(headCustomer.getId());
                    receipt.setHeadcustomername(headCustomer.getName());

                }
            }
            Contacter contacter = getContacterById(receipt.getHandlerid());
            if (contacter != null) {
                receipt.setHandlerid(contacter.getName());
            }
//            Map total = getSalesReceiptMapper().getReceiptDetailTotal(receipt.getId());
//            if (total != null) {
//                if (total.containsKey("taxamount")) {
//                    receipt.setTotaltaxamount((BigDecimal) total.get("taxamount"));
//                }
//                if (total.containsKey("notaxamount")) {
//                    receipt.setTotalnotaxamount((BigDecimal) total.get("notaxamount"));
//                }
//                if (total.containsKey("tax")) {
//                    receipt.setTotaltax((BigDecimal) total.get("tax"));
//                }
//                if (total.containsKey("receipttaxamount")) {
//                    receipt.setTotalreceipttaxamount((BigDecimal) total.get("receipttaxamount"));
//                }
//            }
        }
        PageData pageData = new PageData(getSalesReceiptMapper().getReceiptCount(pageMap), list, pageMap);
        return pageData;
    }

    @Override
    public List getDetailListByReceipt(String id) throws Exception {
        List<ReceiptDetail> receiptDetailList = getSalesReceiptMapper().getReceiptDetailListByReceipt(id);
        for (ReceiptDetail receiptDetail : receiptDetailList) {
            GoodsInfo goodsInfo = getGoodsInfoByID(receiptDetail.getGoodsid());
            receiptDetail.setGoodsInfo(goodsInfo);
        }
        return receiptDetailList;
    }

    @Override
    public boolean submitReceiptProcess(String title, String userId, String processDefinitionKey, String businessKey, Map<String, Object> variables) throws Exception {
        return false;
    }

    @Override
    public ReceiptDetail getReceiptDetail(String billid, String detailid)
            throws Exception {
        ReceiptDetail receiptDetail = getSalesReceiptMapper().getReceiptDetail(detailid);
        return receiptDetail;
    }

    @Override
    public boolean receiptCancelCheck(String id, List<ReceiptDetail> detailList) throws Exception {
        Receipt receipt = getReceipt(id);
        if (null == receipt) {
            return false;
        }
        //判断是否有关联直退销售退货通知单的数据
        //有数据 则回滚关联操作
        //更新明细是否关联信息
        getSalesRejectBillMapper().updateRejectBillDetailIsrefer(id);
        //清空退货通知单关联回单的信息
        getSalesRejectBillMapper().updateRejectBillClearReceipt(id);
        //清空退货入库关联回单的信息
        getSalesRejectBillMapper().updateSaleRejectEnterClearReceipt(id);
        int i = getSalesReceiptMapper().deleteReceiptDetailByReceiptId(id);
        for (ReceiptDetail receiptDetail : detailList) {
            receiptDetail.setBillid(id);
            //判断成本价是否存在
            GoodsInfo goodsInfo = getAllGoodsInfoByID(receiptDetail.getGoodsid());
            if (null != goodsInfo) {
                if (null == receiptDetail.getCostprice()) {
                    receiptDetail.setCostprice(getGoodsCostprice(null,goodsInfo));
                } else if (receiptDetail.getCostprice().compareTo(BigDecimal.ZERO) == 0) {
                    receiptDetail.setCostprice(getGoodsCostprice(null,goodsInfo));
                }
                if (null == receiptDetail.getBrandid() || "".equals(receiptDetail.getBrandid())) {
                    receiptDetail.setGoodssort(goodsInfo.getDefaultsort());
                    receiptDetail.setBrandid(goodsInfo.getBrand());
                    receiptDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), receipt.getCustomerid()));
                    //厂家业务员
                    receiptDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), receipt.getCustomerid()));
                    //获取品牌部门
                    Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                    if (null != brand) {
                        receiptDetail.setBranddept(brand.getDeptid());
                    }
                    receiptDetail.setSupplierid(goodsInfo.getDefaultsupplier());
                }
            }
            getSalesReceiptMapper().addReceiptDetail(receiptDetail);
        }
        return true;
    }

    @Override
    public PageData getReceiptAndRejectBillList(PageMap pageMap)
            throws Exception {
        String dataSql = getDataAccessRule("t_report_sales_invoicebill","t");
        if(StringUtils.isNotEmpty(dataSql)){
            dataSql = dataSql.replaceAll("t.payeeid","c.payeeid");
        }
        pageMap.setDataSql(dataSql);

        SysUser sysUser = getSysUser();
        //判断是否品牌业务员
        String brandUserRoleName = getSysParamValue("BrandUserRoleName");
        boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
        if (isBrandUser) {
            pageMap.getCondition().put("isBrandUser", true);
            pageMap.getCondition().put("personnelid", sysUser.getPersonnelid());
        }
        List<Map> list = getSalesReceiptMapper().getReceiptAndRejectBillList(pageMap);
        for (Map map : list) {
            String id = (String) map.get("id");
            String billtype = (String) map.get("billtype");
            if ("1".equals(billtype)) {
                map.put("billtypename", "销售发货回单");
            } else if ("2".equals(billtype)) {
                map.put("billtypename", "销售退货通知单");
            } else if ("3".equals(billtype)) {
                map.put("billtypename", "冲差单");
            } else if ("4".equals(billtype)) {
                map.put("billtypename", "应收款期初");
            }
            String salesdept = (String) map.get("salesdept");
            DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(salesdept);
            if (departMent != null) {
                map.put("salesdept", departMent.getName());
            }
            String salesuser = (String) map.get("salesuser");
            Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(salesuser);
            if (personnel != null) {
                map.put("salesuser", personnel.getName());
            }
            String customerid = (String) map.get("customerid");

            Customer customer = getCustomerByID(customerid);
            if (customer != null) {
                map.put("customername", customer.getName());
                map.put("customerInfo", customer);

                Customer headCustomer = getCustomerByID(customer.getPid());
                if (null != headCustomer) {
                    map.put("headcustomername", headCustomer.getName());
                    map.put("headcustomerid", customer.getPid());
                } else {
                    map.put("headcustomerid", customer.getId());
                }
            }
            String handlerid = (String) map.get("handlerid");
            Contacter contacter = getContacterById(handlerid);
            if (contacter != null) {
                map.put("handlerid", contacter.getName());
            }
        }
        PageData pageData = new PageData(getSalesReceiptMapper().getReceiptAndRejectBillCount(pageMap), list, pageMap);
        Map dataSum = getSalesReceiptMapper().getReceiptAndRejectBillSumData(pageMap);
		if(null!= dataSum){
			List footer = new ArrayList();
			dataSum.put("id", "合计");
			footer.add(dataSum);
			pageData.setFooter(footer);
		}
		return pageData;
	}
	
	@Override
	public PageData getReceiptAndRejectBillListForInvoiceBill(PageMap pageMap)
			throws Exception {
        String dataSql = getDataAccessRule("t_report_sales_invoicebill","t");
        if(StringUtils.isNotEmpty(dataSql)){
            dataSql = dataSql.replaceAll("t.payeeid","c.payeeid");
        }
        pageMap.setDataSql(dataSql);

		SysUser sysUser = getSysUser();
		//判断是否品牌业务员
		String brandUserRoleName = getSysParamValue("BrandUserRoleName");
		boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
		if(isBrandUser){
			pageMap.getCondition().put("isBrandUser", true);
			pageMap.getCondition().put("personnelid", sysUser.getPersonnelid());
		}
		List<Map> list = getSalesReceiptMapper().getReceiptAndRejectBillListForInvoiceBill(pageMap);
		for(Map map : list){
			String id = (String) map.get("id");
			String billtype = (String) map.get("billtype");
			if("1".equals(billtype)){
				map.put("billtypename", "销售发货回单");
			}else if("2".equals(billtype)){
				map.put("billtypename", "销售退货通知单");
			}else if("3".equals(billtype)){
				map.put("billtypename", "冲差单");
			}
			String salesdept = (String) map.get("salesdept");
			DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(salesdept);
			if(departMent != null){
				map.put("salesdept", departMent.getName());
			}
			String salesuser = (String) map.get("salesuser");
			Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(salesuser);
			if(personnel != null){
				map.put("salesuser", personnel.getName());
			}
			String customerid = (String) map.get("customerid");
			
			Customer customer = getCustomerByID(customerid);
			if(customer != null){
				map.put("customername", customer.getName());
				map.put("customerInfo", customer);
				
				Customer headCustomer = getCustomerByID(customer.getPid());
				if(null!=headCustomer){
					map.put("headcustomername", headCustomer.getName());
					map.put("headcustomerid", customer.getPid());
				}else{
					map.put("headcustomerid", customer.getId());
				}
			}
		}
		PageData pageData = new PageData(getSalesReceiptMapper().getReceiptAndRejectBillCountForInvoiceBill(pageMap),list,pageMap);
		Map dataSum = getSalesReceiptMapper().getReceiptAndRejectBillSumDataForInvoiceBill(pageMap);
		if(null!= dataSum){
			List footer = new ArrayList();
			dataSum.put("id", "合计");
			footer.add(dataSum);
			pageData.setFooter(footer);
		}
		return pageData;
	}

    @Override
    public List getReceiptAndRejectBillDetailList(PageMap pageMap)
            throws Exception {
        String datasql1 = getDataAccessRule("t_sales_receipt", "t");
        String datasql2 = getDataAccessRule("t_sales_rejectbill", "t");
        pageMap.getCondition().put("datasql1", datasql1);
        pageMap.getCondition().put("datasql2", datasql2);
        List<Map> list = getSalesReceiptMapper().getReceiptAndRejectBillDetailList(pageMap);
        for (Map map : list) {
            String customerid = (String) map.get("customerid");
            Customer customer = getCustomerByID(customerid);
            if (null != customer) {
                map.put("customername", customer.getName());
            }
            String goodsid = (String) map.get("goodsid");
            GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
            if (null != goodsid) {
                map.put("goodsname", goodsInfo.getName());
                map.put("boxnum", goodsInfo.getBoxnum());
            }
            String brandid = (String) map.get("brandid");
            Brand brand = getGoodsBrandByID(brandid);
            if (null != brand) {
                map.put("brandname", brand.getName());
            }
            String isdiscount = (String) map.get("isdiscount");
            if ("1".equals(isdiscount)) {
                map.put("goodsname", "(折扣)" + goodsInfo.getName());
                map.put("boxnum", null);
            } else if ("2".equals(isdiscount)) {
                map.put("goodsname", "(折扣)" + brand.getName());
                map.put("boxnum", null);
                map.put("goodsid", null);
            }
        }
        return list;
    }

    @Override
    public String getReceiptDate(Date date, String customerid) throws Exception {
        String receivabledate = getReceiptDateBySettlement(date, customerid,null);
        return receivabledate;
    }

    @Override
    public boolean isReceivableAmountPassDateByCustomerid(String customerid)
            throws Exception {
        return isReceivablePassDateByCustomerid(customerid);
    }

    @Override
    public boolean isReceivableInCreditByCustomerid(String customerid)
            throws Exception {
        return isReceivableInCredit(customerid);
    }

    @Override
    public Map getRejectListByReceiptid(String id) throws Exception {
        Receipt receipt = getReceipt(id);
        Map map = null;
        if (null != receipt) {
            map = new HashMap();
            String customerid = receipt.getCustomerid();
            List<RejectBill> list = getSalesRejectBillMapper().getDirectRejectBillListByCustomerid(customerid);
            for (RejectBill rejectBill : list) {
                DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(rejectBill.getSalesdept());
                if (departMent != null) {
                    rejectBill.setSalesdept(departMent.getName());
                }
                Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(rejectBill.getSalesuser());
                if (personnel != null) {
                    rejectBill.setSalesuser(personnel.getName());
                }
                Customer customer = getCustomerByID(rejectBill.getCustomerid());
                if (customer != null) {
                    rejectBill.setCustomername(customer.getName());
                }
                rejectBill.setCustomerInfo(customer);
                //获取总店客户
                Customer headCustomer = getHeadCustomer(rejectBill.getCustomerid());
                if (null != headCustomer) {
                    rejectBill.setHeadcustomerid(headCustomer.getId());
                    rejectBill.setHeadcustomername(headCustomer.getName());
                }
                map.put("id", rejectBill.getHandlerid());
                Contacter contacter = getBaseFilesContacterMapper().getContacterDetail(map);
                if (contacter != null) {
                    rejectBill.setHandlerid(contacter.getName());
                }
                Map total = getSalesRejectBillMapper().getRejectBillDetailTotal(rejectBill.getId());
                if (total != null) {
                    if (total.containsKey("taxamount")) {
                        rejectBill.setTotaltaxamount((BigDecimal) total.get("taxamount"));
                    }
                    if (total.containsKey("notaxamount")) {
                        rejectBill.setTotalnotaxamount((BigDecimal) total.get("notaxamount"));
                    }
                    if (total.containsKey("tax")) {
                        rejectBill.setTotaltax((BigDecimal) total.get("tax"));
                    }
                }
            }
            map.put("list", list);

            Customer customer = getCustomerByID(customerid);
            map.put("customer", customer);

            List receiptDetailList = getSalesReceiptMapper().getReceiptDetailListByReceipt(id);
            map.put("detailList", receiptDetailList);
        }
        return map;
    }

    @Override
    public Map receiptRelationRejectBill(String receiptid, String rejectbillids)
            throws Exception {
        String[] rejectbillidArr = rejectbillids.split(",");
        boolean flag = false;
        String msg = "";
        //是否可以关联
        boolean relateflag = true;
        for (String rejectbillid : rejectbillidArr) {
            Receipt receipt = getReceipt(receiptid);
            //判断回单与销售退货通知单 是否符合关联条件
            //销售退货通知单处于保存状态 退货类型为直退
            //回单所属客户与销售退货通知单所属客户一致
            if (null != receipt && "2".equals(receipt.getStatus())) {
                //销售发货回单明细列表
                List<ReceiptDetail> receiptList = getSalesReceiptMapper().getReceiptDetailListByReceipt(receiptid);
                boolean goflag = true;
                //判断回单是否关联过
                for (ReceiptDetail receiptDetail : receiptList) {
                    if (receiptDetail.getUnitnum().compareTo(receiptDetail.getReceiptnum()) != 0) {
                        goflag = false;
                        break;
                    }
                }
                if (goflag == false) {
                    Map map = new HashMap();
                    map.put("flag", false);
                    map.put("msg", "回单已关联销售退货通知单，请先取消关联！");
                    return map;
                }
                //销售退货通知单明细列表
                List<RejectBillDetail> rejectBillDetailList = getSalesRejectBillMapper().getRejectBillDetailListByBill(rejectbillid);
                Map map = new HashMap();
                map.put("id", rejectbillid);
                RejectBill rejectBill = getSalesRejectBillMapper().getRejectBill(map);
                if (null != rejectBill && "3".equals(rejectBill.getStatus()) && receipt.getCustomerid().equals(receipt.getCustomerid())) {
                    for (RejectBillDetail rejectBillDetail : rejectBillDetailList) {
                        GoodsInfo rejectGoodsInfo = getAllGoodsInfoByID(rejectBillDetail.getGoodsid());
                        boolean ishave = false;
                        //未关联的直退数量
                        BigDecimal rejectnum = rejectBillDetail.getUnitnum();
                        for (ReceiptDetail receiptDetail : receiptList) {
                            GoodsInfo receiptGoodsInfo = getAllGoodsInfoByID(receiptDetail.getGoodsid());
                            //判断销售退货通知单 且回单明细不是折扣
                            if ((rejectBillDetail.getGoodsid().equals(receiptDetail.getGoodsid()) || rejectGoodsInfo.getBarcode().equals(receiptGoodsInfo.getBarcode()))
                                    && !"1".equals(receiptDetail.getIsdiscount()) && receiptDetail.getReceiptnum().compareTo(BigDecimal.ZERO) == 1) {
                                //直退数量
                                BigDecimal rejectdirtnum = BigDecimal.ZERO;
                                if (rejectnum.compareTo(receiptDetail.getReceiptnum()) == 1) {
                                    //客户接收数量 = 回单数量 - 退货通知单数量
                                    receiptDetail.setReceiptnum(BigDecimal.ZERO);
                                    receiptDetail.setReceipttaxamount(BigDecimal.ZERO);
                                    rejectdirtnum = receiptDetail.getUnitnum();
                                } else {
                                    //客户接收数量 = 回单数量 - 退货通知单数量
                                    receiptDetail.setReceiptnum(receiptDetail.getReceiptnum().subtract(rejectnum));
                                    receiptDetail.setReceipttaxamount(receiptDetail.getTaxprice().multiply(receiptDetail.getReceiptnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                                    rejectdirtnum = rejectnum;
                                }
                                ishave = true;
                                rejectnum = rejectnum.subtract(rejectdirtnum);
                                if (rejectnum.compareTo(BigDecimal.ZERO) == 0) {
                                    break;
                                }
                            }
                        }
                        if (ishave == false) {
                            GoodsInfo goodsInfo = getAllGoodsInfoByID(rejectBillDetail.getGoodsid());
                            if (null != goodsInfo) {
                                msg += "销售发货回单中，不存在商品：" + rejectBillDetail.getGoodsid() + "," + goodsInfo.getName() + "。";
                                relateflag = false;
                                break;
                            }
                        }

                        //退货数量不为0时 不可关联
                        if (rejectnum.compareTo(BigDecimal.ZERO) != 0) {
                            relateflag = false;
                            break;
                        }
                    }
                }
            } else {
                relateflag = false;
                break;
            }
            if (relateflag == false) {
                break;
            }
        }
        if (relateflag) {
            for (String rejectbillid : rejectbillidArr) {
                Receipt receipt = getReceipt(receiptid);
                //销售发货回单明细列表
                List<ReceiptDetail> receiptList = getSalesReceiptMapper().getReceiptDetailListByReceipt(receiptid);
                //销售退货通知单明细列表
                List<RejectBillDetail> rejectBillDetailList = getSalesRejectBillMapper().getRejectBillDetailListByBill(rejectbillid);
                Map map = new HashMap();
                map.put("id", rejectbillid);
                RejectBill rejectBill = getSalesRejectBillMapper().getRejectBill(map);
                //判断回单与销售退货通知单 是否符合关联条件
                //销售退货通知单处于保存状态 退货类型为直退
                //回单所属客户与销售退货通知单所属客户一致
                if (null != receipt && "2".equals(receipt.getStatus())) {
                    for (RejectBillDetail rejectBillDetail : rejectBillDetailList) {
                        GoodsInfo rejectGoodsInfo = getAllGoodsInfoByID(rejectBillDetail.getGoodsid());
                        boolean ishave = false;
                        //未关联的直退数量
                        BigDecimal rejectnum = rejectBillDetail.getUnitnum();
                        ReceiptDetail relatReceiptDetail = null;
                        boolean barcodeFlag = false;
                        //通过相同商品编码关联 商品编码相同的商品如果数量不一致 再去关联相同条形码
                        for (ReceiptDetail receiptDetail : receiptList) {
                            GoodsInfo receiptGoodsInfo = getAllGoodsInfoByID(receiptDetail.getGoodsid());
                            //判断销售退货通知单 且回单明细不是折扣
                            if (rejectBillDetail.getGoodsid().equals(receiptDetail.getGoodsid())
                                    && !"1".equals(receiptDetail.getIsdiscount()) && receiptDetail.getReceiptnum().compareTo(BigDecimal.ZERO) == 1) {
                                //直退数量
                                BigDecimal rejectdirtnum = BigDecimal.ZERO;
                                if (rejectnum.compareTo(receiptDetail.getReceiptnum()) == 1) {
                                    //客户接收数量 = 回单数量 - 退货通知单数量
                                    receiptDetail.setReceiptnum(BigDecimal.ZERO);
                                    receiptDetail.setReceipttaxamount(BigDecimal.ZERO);
                                    rejectdirtnum = receiptDetail.getUnitnum();
                                } else {
                                    //客户接收数量 = 回单数量 - 退货通知单数量
                                    receiptDetail.setReceiptnum(receiptDetail.getReceiptnum().subtract(rejectnum));
                                    receiptDetail.setReceipttaxamount(receiptDetail.getTaxprice().multiply(receiptDetail.getReceiptnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                                    rejectdirtnum = rejectnum;
                                }
                                GoodsInfo goodsInfo = getAllGoodsInfoByID(rejectBillDetail.getGoodsid());
                                if (null != goodsInfo) {
                                    BigDecimal noReceipttaxamount = getNotaxAmountByTaxAmount(receiptDetail.getReceipttaxamount(), goodsInfo.getDefaulttaxtype());
                                    receiptDetail.setReceiptnotaxamount(noReceipttaxamount);
                                }

                                //关联 直退销售退货通知单明细编号
                                receiptDetail.setRejectbillid(rejectBillDetail.getBillid());
                                receiptDetail.setRejectdetailid(rejectBillDetail.getId());
                                String remark = "";
                                if (null != receiptDetail.getRemark() && !"".equals(receiptDetail.getRemark())) {
                                    remark += receiptDetail.getRemark() + ",";
                                }
                                receiptDetail.setRemark(remark + "直退：" + rejectdirtnum.setScale(0, BigDecimal.ROUND_HALF_UP));
                                //更新客户接收数量 接收含税金额 未税金额
                                getSalesReceiptMapper().updateReceiptDetailBack(receiptDetail);
                                //更新销售入库单明细中来源回单编号
                                //									getSalesRejectBillMapper().updateDirectRejectBillDetailIsrefer(rejectBillDetail.getId(), "1");
                                //更新来源的来源单据编号来源单据明细编号
                                storageForSalesService.updateSaleRejectEnterDetailIDs(rejectBillDetail.getBillid(), rejectBillDetail.getId(), receiptDetail.getBillid(), receiptDetail.getId());
                                ishave = true;
                                rejectnum = rejectnum.subtract(rejectdirtnum);
                                relatReceiptDetail = receiptDetail;
                                if (rejectnum.compareTo(BigDecimal.ZERO) == 0) {
                                    break;
                                }
                            }
                        }
                        //未关联数量大于0时 寻找相同条形码的商品关联
                        if (rejectnum.compareTo(BigDecimal.ZERO) == 1) {
                            barcodeFlag = true;
                        }
                        if (barcodeFlag) {
                            for (ReceiptDetail receiptDetail : receiptList) {
                                GoodsInfo receiptGoodsInfo = getAllGoodsInfoByID(receiptDetail.getGoodsid());
                                //判断销售退货通知单 且回单明细不是折扣
                                if (rejectGoodsInfo.getBarcode().equals(receiptGoodsInfo.getBarcode())
                                        && !"1".equals(receiptDetail.getIsdiscount()) && receiptDetail.getReceiptnum().compareTo(BigDecimal.ZERO) == 1) {
                                    //直退数量
                                    BigDecimal rejectdirtnum = BigDecimal.ZERO;
                                    if (rejectnum.compareTo(receiptDetail.getReceiptnum()) == 1) {
                                        //客户接收数量 = 回单数量 - 退货通知单数量
                                        receiptDetail.setReceiptnum(BigDecimal.ZERO);
                                        receiptDetail.setReceipttaxamount(BigDecimal.ZERO);
                                        rejectdirtnum = receiptDetail.getUnitnum();
                                    } else {
                                        //客户接收数量 = 回单数量 - 退货通知单数量
                                        receiptDetail.setReceiptnum(receiptDetail.getReceiptnum().subtract(rejectnum));
                                        receiptDetail.setReceipttaxamount(receiptDetail.getTaxprice().multiply(receiptDetail.getReceiptnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                                        rejectdirtnum = rejectnum;
                                    }
                                    GoodsInfo goodsInfo = getAllGoodsInfoByID(rejectBillDetail.getGoodsid());
                                    if (null != goodsInfo) {
                                        BigDecimal noReceipttaxamount = getNotaxAmountByTaxAmount(receiptDetail.getReceipttaxamount(), goodsInfo.getDefaulttaxtype());
                                        receiptDetail.setReceiptnotaxamount(noReceipttaxamount);
                                    }

                                    //关联 直退销售退货通知单明细编号
                                    receiptDetail.setRejectbillid(rejectBillDetail.getBillid());
                                    receiptDetail.setRejectdetailid(rejectBillDetail.getId());
                                    String remark = "";
                                    if (null != receiptDetail.getRemark() && !"".equals(receiptDetail.getRemark())) {
                                        remark += receiptDetail.getRemark() + ",";
                                    }
                                    receiptDetail.setRemark(remark + "直退：" + rejectdirtnum.setScale(0, BigDecimal.ROUND_HALF_UP));
                                    //更新客户接收数量 接收含税金额 未税金额
                                    getSalesReceiptMapper().updateReceiptDetailBack(receiptDetail);
                                    //更新销售入库单明细中来源回单编号
                                    //									getSalesRejectBillMapper().updateDirectRejectBillDetailIsrefer(rejectBillDetail.getId(), "1");
                                    //更新来源的来源单据编号来源单据明细编号
                                    storageForSalesService.updateSaleRejectEnterDetailIDs(rejectBillDetail.getBillid(), rejectBillDetail.getId(), receiptDetail.getBillid(), receiptDetail.getId());
                                    ishave = true;
                                    rejectnum = rejectnum.subtract(rejectdirtnum);
                                    relatReceiptDetail = receiptDetail;
                                    if (rejectnum.compareTo(BigDecimal.ZERO) == 0) {
                                        break;
                                    }
                                }
                            }
                        }
                        //更新销售退货通知单 直退关联数量
                        RejectBillDetail detail = new RejectBillDetail();
                        detail.setBillid(rejectBillDetail.getBillid());
                        detail.setId(rejectBillDetail.getId());
                        detail.setUnitnum(rejectBillDetail.getUnitnum());
                        detail.setTaxprice(relatReceiptDetail.getTaxprice());
                        BigDecimal taxamount=detail.getTaxprice().multiply(detail.getUnitnum());
                        detail.setTaxamount(taxamount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                        detail.setNotaxprice(relatReceiptDetail.getNotaxprice());
                        BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, detail.getTaxtype());
                        detail.setNotaxamount(notaxamount);
                        detail.setTax(detail.getTaxamount().subtract(detail.getNotaxamount()));
                        detail.setIsrefer("1");
                        //更新销售退货通知单明细
                        getSalesRejectBillMapper().updateRejectBillDetailBack(detail);
                        //更新销售退货入库单明细
                        storageForSalesService.updateSaleRejectEnterDetailByRejectDetail(detail);
                    }
                    //更新销售退货通知单关联回单编号
                    RejectBill updaterejectBill = new RejectBill();
                    updaterejectBill.setReceiptid(receiptid);
                    updaterejectBill.setId(rejectbillid);
                    int i = getSalesRejectBillMapper().updateRejectBill(updaterejectBill);
                    flag = i > 0;
                    //通过销售退货通知单编号 更新下游单据单据金额
                    storageForSalesService.updateSaleRejectEnterAmount(rejectbillid);
                } else {
                    msg = "销售发货通知单明细中的数量大于回单明细中的数量。不能关联。";
                }
            }
        }


        Map map = new HashMap();
        map.put("flag", flag);
        map.put("msg", msg);
        return map;
    }

    /**
     * 根据回单中关联退货单编号获取退货单列表
     * @param id
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-6-8
     */
    public List<RejectBill> getReceiptRejectBillList(String id) throws Exception{
        Receipt receipt = getReceipt(id);
        List<Map> list = getSalesReceiptMapper().getrejectBillIdListByreject(id);
        List<RejectBill> rejectBilllist = new ArrayList<RejectBill>();
        for(Map map : list){
            if(null != map){
                String rejectid = (String)map.get("rejectbillid");
                if(StringUtils.isNotEmpty(rejectid)){
                    RejectBill rejectBill = getSalesRejectBillMapper().getRejectBillById(rejectid);
                    if(null!=rejectBill){
                        rejectBilllist.add(rejectBill);
                    }
                }
            }
        }
        return rejectBilllist;
    }

    @Override
    public Map updateReceiptRelationRejectBillAgain(String receiptid,String rejectbillids) throws Exception {
        String[] rejectbillidArr = rejectbillids.split(",");
        boolean flag = false;
        String msg = "";
        //是否可以关联
        boolean relateflag = true;
        Map querymap = new HashMap();
        querymap.put("id", receiptid);
        Receipt receipt = getSalesReceiptMapper().getReceipt(querymap);
        //判断回单与销售退货通知单 是否符合关联条件
        //销售退货通知单处于保存状态 退货类型为直退
        //回单所属客户与销售退货通知单所属客户一致
        if (null != receipt && "2".equals(receipt.getStatus()) && StringUtils.isNotEmpty(rejectbillids)) {
            //销售发货回单明细列表
            List<ReceiptDetail> rList = getSalesReceiptMapper().getReceiptDetailListByReceiptForRel(receiptid);
            List<ReceiptDetail> copyReceiptList = (List<ReceiptDetail>) CommonUtils.deepCopy(rList);
            //销售退货通知单明细列表
            List<RejectBillDetail> rejectBillDetailList = getSalesRejectBillMapper().getRejectBillDetailListByBillidsForRel(rejectbillidArr,receipt.getCustomerid());
            if (null!=rejectBillDetailList) {
                for (RejectBillDetail rejectBillDetail : rejectBillDetailList) {
                    GoodsInfo rejectGoodsInfo = getAllGoodsInfoByID(rejectBillDetail.getGoodsid());
                    boolean ishave = false;
                    boolean isPriceEqu = true;
                    //未关联的直退数量
                    BigDecimal rejectnum = rejectBillDetail.getUnitnum();
                    //判断退货关联回单中，回单符合条数
                    //当符合条数为多条的时候，需要判断退货的价格需要与回单中明细的价格一直
                    int count = 0;
                    for(ReceiptDetail receiptDetail : copyReceiptList){
                        GoodsInfo receiptGoodsInfo = getAllGoodsInfoByID(receiptDetail.getGoodsid());
                        if(!rejectBillDetail.getGoodsid().equals(receiptDetail.getGoodsid()) && !rejectGoodsInfo.getBarcode().equals(receiptGoodsInfo.getBarcode())){
                            continue;
                        }
                        if (!rejectBillDetail.getDeliverytype().equals(receiptDetail.getDeliverytype())) {
                            continue;
                        }
                        count ++;
                    }
                    for (ReceiptDetail receiptDetail : copyReceiptList) {
                        GoodsInfo receiptGoodsInfo = getAllGoodsInfoByID(receiptDetail.getGoodsid());
                        if(!rejectBillDetail.getGoodsid().equals(receiptDetail.getGoodsid()) && !rejectGoodsInfo.getBarcode().equals(receiptGoodsInfo.getBarcode())){
                            continue;
                        }
                        if (!rejectBillDetail.getDeliverytype().equals(receiptDetail.getDeliverytype())) {
                            continue;
                        }
                        isPriceEqu =true;
                        ishave = true;
                        if(count>1 && rejectBillDetail.getTaxprice().compareTo(receiptDetail.getTaxprice())!=0){
                            isPriceEqu = false;
                            continue;
                        }
                        //判断销售退货通知单 且回单明细不是折扣
                        if ( !"1".equals(receiptDetail.getIsdiscount()) && receiptDetail.getReceiptnum().compareTo(BigDecimal.ZERO) == 1) {
                            //直退数量
                            BigDecimal rejectdirtnum = BigDecimal.ZERO;
                            if (rejectnum.compareTo(receiptDetail.getReceiptnum()) == 1) {
                                receiptDetail.setReceipttaxamount(BigDecimal.ZERO);
                                rejectdirtnum = receiptDetail.getReceiptnum();
                                //客户接收数量 = 回单数量 - 退货通知单数量
                                receiptDetail.setReceiptnum(BigDecimal.ZERO);
                            } else {
                                //客户接收数量 = 回单数量 - 退货通知单数量
                                receiptDetail.setReceiptnum(receiptDetail.getReceiptnum().subtract(rejectnum));
                                receiptDetail.setReceipttaxamount(receiptDetail.getTaxprice().multiply(receiptDetail.getReceiptnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                                rejectdirtnum = rejectnum;
                            }
                            rejectnum = rejectnum.subtract(rejectdirtnum);
                            if (rejectnum.compareTo(BigDecimal.ZERO) == 0) {
                                break;
                            }
                        }else{
                            ishave = false;
                            isPriceEqu = false;
                        }
                    }
                    if (ishave == false) {
                        GoodsInfo goodsInfo = getAllGoodsInfoByID(rejectBillDetail.getGoodsid());
                        if (null != goodsInfo) {
                            msg += "销售发货回单中，不存在商品或者赠品与正常商品未区分。</br>商品：" + rejectBillDetail.getGoodsid() + "," + goodsInfo.getName() + "。";
                            relateflag = false;
                            break;
                        }
                    }
                    if (isPriceEqu == false) {
                        GoodsInfo goodsInfo = getAllGoodsInfoByID(rejectBillDetail.getGoodsid());
                        if (null != goodsInfo && null!=rejectBillDetail.getTaxprice()) {
                            msg += "关联时，回单中的商品价格不一致。</br>商品：" + rejectBillDetail.getGoodsid() + "," + goodsInfo.getName() + "。";
                            relateflag = false;
                            break;
                        }
                    }
                    //退货数量不为0时 不可关联
                    if (rejectnum.compareTo(BigDecimal.ZERO) != 0) {
                        GoodsInfo goodsInfo = getAllGoodsInfoByID(rejectBillDetail.getGoodsid());
                        if (null != goodsInfo) {
                            if(count>1){
                                msg += "退货中的商品数量比回单中的数量多。因为退货商品在回单中有多条符合的明细，只匹配价格相同的商品。</br>退货商品：" + rejectBillDetail.getGoodsid()+"。退货数量："+CommonUtils.strDigitNumDeal(rejectBillDetail.getAuxnumdetail());
                            }else{
                                msg += "退货中的商品数量比回单中的数量多。</br>商品：" + rejectBillDetail.getGoodsid() + "," + goodsInfo.getName() + "。退货数量："+CommonUtils.strDigitNumDeal(rejectBillDetail.getAuxnumdetail());
                            }

                        }
                        relateflag = false;
                        break;
                    }
                }
            }
        } else {
            relateflag = false;
        }

        if (relateflag) {
            //销售发货回单明细列表
            List<ReceiptDetail> receiptList = getSalesReceiptMapper().getReceiptDetailListByReceiptForRel(receiptid);
            //销售退货通知单明细列表
            List<RejectBillDetail> rejectBillDetailList = getSalesRejectBillMapper().getRejectBillDetailListByBillidsForRel(rejectbillidArr, receipt.getCustomerid());
            //判断回单与销售退货通知单 是否符合关联条件
            //销售退货通知单处于保存状态 退货类型为直退
            //回单所属客户与销售退货通知单所属客户一致
            if (null != receipt && "2".equals(receipt.getStatus())) {
                for (RejectBillDetail rejectBillDetail : rejectBillDetailList) {
                    GoodsInfo rejectGoodsInfo = getAllGoodsInfoByID(rejectBillDetail.getGoodsid());
                    //未关联的直退数量
                    BigDecimal rejectnum = rejectBillDetail.getUnitnum();
                    ReceiptDetail relatReceiptDetail = null;
                    boolean barcodeFlag = false;
                    int count = 0;
                    for(ReceiptDetail receiptDetail : receiptList){
                        GoodsInfo receiptGoodsInfo = getAllGoodsInfoByID(receiptDetail.getGoodsid());
                        if(!rejectBillDetail.getGoodsid().equals(receiptDetail.getGoodsid()) && !rejectGoodsInfo.getBarcode().equals(receiptGoodsInfo.getBarcode())){
                            continue;
                        }
                        if (!rejectBillDetail.getDeliverytype().equals(receiptDetail.getDeliverytype())) {
                            continue;
                        }
                        count ++;
                    }
                    //通过相同商品编码关联 商品编码相同的商品如果数量不一致 再去关联相同条形码
                    for (ReceiptDetail receiptDetail : receiptList) {
                        GoodsInfo receiptGoodsInfo = getAllGoodsInfoByID(receiptDetail.getGoodsid());
                        if(!rejectBillDetail.getGoodsid().equals(receiptDetail.getGoodsid())){
                            continue;
                        }
                        if (!rejectBillDetail.getDeliverytype().equals(receiptDetail.getDeliverytype())) {
                            continue;
                        }
                        //退货关联回单时，需要判断价格是否一致
                        if(count>1 && rejectBillDetail.getTaxprice().compareTo(receiptDetail.getTaxprice())!=0){
                            continue;
                        }
                        //判断销售退货通知单 且回单明细不是折扣
                        if ( !"1".equals(receiptDetail.getIsdiscount()) && receiptDetail.getReceiptnum().compareTo(BigDecimal.ZERO) == 1) {
                            //直退数量
                            BigDecimal rejectdirtnum = BigDecimal.ZERO;
                            if (rejectnum.compareTo(receiptDetail.getReceiptnum()) == 1) {
                                //客户接收数量 = 回单数量 - 退货通知单数量
                                receiptDetail.setReceipttaxamount(BigDecimal.ZERO);
                                rejectdirtnum = receiptDetail.getReceiptnum();
                                receiptDetail.setReceiptnum(BigDecimal.ZERO);
                            } else {
                                BigDecimal lastReceiptnum = receiptDetail.getReceiptnum().subtract(rejectnum);
                                //客户接收数量 = 回单数量 - 退货通知单数量
                                receiptDetail.setReceiptnum(lastReceiptnum);
                                receiptDetail.setReceipttaxamount(receiptDetail.getTaxprice().multiply(receiptDetail.getReceiptnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                                rejectdirtnum = rejectnum;
                            }
                            GoodsInfo goodsInfo = getAllGoodsInfoByID(rejectBillDetail.getGoodsid());
                            if (null != goodsInfo) {
                                BigDecimal noReceipttaxamount = getNotaxAmountByTaxAmount(receiptDetail.getReceipttaxamount(), goodsInfo.getDefaulttaxtype());
                                receiptDetail.setReceiptnotaxamount(noReceipttaxamount);
                            }

                            //计算客户接收箱数
                            Map auxmap = countGoodsInfoNumber(receiptDetail.getGoodsid(), receiptDetail.getAuxunitid(), receiptDetail.getReceiptnum());
                            if (auxmap.containsKey("auxnum")) {
                                receiptDetail.setReceiptbox((BigDecimal) auxmap.get("auxnum"));
                            }
                            //关联 直退销售退货通知单明细编号
                            String addrejectid = "";
                            if (StringUtils.isEmpty(receiptDetail.getRejectbillid())) {
                                addrejectid = rejectBillDetail.getBillid();
                            } else {
                                addrejectid = receiptDetail.getRejectbillid() + "," + rejectBillDetail.getBillid();
                            }
                            String addrejectdetailid = "";
                            if (StringUtils.isEmpty(receiptDetail.getRejectdetailid())) {
                                addrejectdetailid = rejectBillDetail.getId();
                            } else {
                                addrejectdetailid = receiptDetail.getRejectdetailid() + "," + rejectBillDetail.getId();
                            }
                            receiptDetail.setRejectbillid(addrejectid);
                            receiptDetail.setRejectdetailid(addrejectdetailid);
//								receiptDetail.setRejectbillid(rejectBillDetail.getBillid());
//								receiptDetail.setRejectdetailid(rejectBillDetail.getId());
                            String remark = "";
                            if (null != receiptDetail.getRemark() && !"".equals(receiptDetail.getRemark())) {
                                remark += receiptDetail.getRemark() + ",";
                            }
                            receiptDetail.setRemark(CommonUtils.strDigitNumDeal(remark + "直退：" + rejectdirtnum));
                            //更新客户接收数量 接收含税金额 未税金额
                            getSalesReceiptMapper().updateReceiptDetailBack(receiptDetail);
                            //更新销售入库单明细中来源回单编号
                            //									getSalesRejectBillMapper().updateDirectRejectBillDetailIsrefer(rejectBillDetail.getId(), "1");
                            //更新来源的来源单据编号来源单据明细编号
                            storageForSalesService.updateSaleRejectEnterDetailIDs(rejectBillDetail.getBillid(), rejectBillDetail.getId(), receiptDetail.getBillid(), receiptDetail.getId());
                            rejectnum = rejectnum.subtract(rejectdirtnum);
                            relatReceiptDetail = receiptDetail;
                            if (rejectnum.compareTo(BigDecimal.ZERO) == 0) {
                                break;
                            }
                        }
                    }
                    //未关联数量大于0时 寻找相同条形码的商品关联
                    if (rejectnum.compareTo(BigDecimal.ZERO) == 1) {
                        barcodeFlag = true;
                    }
                    if (barcodeFlag) {
                        for (ReceiptDetail receiptDetail : receiptList) {
                            GoodsInfo receiptGoodsInfo = getAllGoodsInfoByID(receiptDetail.getGoodsid());
                            if(!rejectGoodsInfo.getBarcode().equals(receiptGoodsInfo.getBarcode())){
                                continue;
                            }
                            if (!rejectBillDetail.getDeliverytype().equals(receiptDetail.getDeliverytype())) {
                                continue;
                            }
                            //退货关联回单时，需要判断价格是否一致
                            if(count>1 && rejectBillDetail.getTaxprice().compareTo(receiptDetail.getTaxprice())!=0){
                                continue;
                            }
                            //判断销售退货通知单 且回单明细不是折扣
                            if (!"1".equals(receiptDetail.getIsdiscount()) && receiptDetail.getReceiptnum().compareTo(BigDecimal.ZERO) == 1) {
                                //直退数量
                                BigDecimal rejectdirtnum = BigDecimal.ZERO;
                                if (rejectnum.compareTo(receiptDetail.getReceiptnum()) == 1) {
                                    //客户接收数量 = 回单数量 - 退货通知单数量
                                    receiptDetail.setReceipttaxamount(BigDecimal.ZERO);
                                    rejectdirtnum = receiptDetail.getReceiptnum();
                                    receiptDetail.setReceiptnum(BigDecimal.ZERO);
                                } else {
                                    //客户接收数量 = 回单数量 - 退货通知单数量
                                    BigDecimal lastReceiptnum = receiptDetail.getReceiptnum().subtract(rejectnum);
                                    receiptDetail.setReceiptnum(lastReceiptnum);
                                    receiptDetail.setReceipttaxamount(receiptDetail.getTaxprice().multiply(receiptDetail.getReceiptnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                                    rejectdirtnum = rejectnum;
                                }
                                GoodsInfo goodsInfo = getAllGoodsInfoByID(rejectBillDetail.getGoodsid());
                                if (null != goodsInfo) {
                                    BigDecimal noReceipttaxamount = getNotaxAmountByTaxAmount(receiptDetail.getReceipttaxamount(), goodsInfo.getDefaulttaxtype());
                                    receiptDetail.setReceiptnotaxamount(noReceipttaxamount);
                                }
                                //计算客户接收箱数
                                Map auxmap = countGoodsInfoNumber(receiptDetail.getGoodsid(), receiptDetail.getAuxunitid(), receiptDetail.getReceiptnum());
                                if (auxmap.containsKey("auxnum")) {
                                    receiptDetail.setReceiptbox((BigDecimal) auxmap.get("auxnum"));
                                }
                                //关联 直退销售退货通知单明细编号
                                String addrejectid = "";
                                if (StringUtils.isEmpty(receiptDetail.getRejectbillid())) {
                                    addrejectid = rejectBillDetail.getBillid();
                                } else {
                                    addrejectid = receiptDetail.getRejectbillid() + "," + rejectBillDetail.getBillid();
                                }
                                String addrejectdetailid = "";
                                if (StringUtils.isEmpty(receiptDetail.getRejectdetailid())) {
                                    addrejectdetailid = rejectBillDetail.getId();
                                } else {
                                    addrejectdetailid = receiptDetail.getRejectdetailid() + "," + rejectBillDetail.getId();
                                }
                                receiptDetail.setRejectbillid(addrejectid);
                                receiptDetail.setRejectdetailid(addrejectdetailid);
                                String remark = "";
                                if (null != receiptDetail.getRemark() && !"".equals(receiptDetail.getRemark())) {
                                    remark += receiptDetail.getRemark() + ",";
                                }
                                receiptDetail.setRemark(CommonUtils.strDigitNumDeal(remark + "直退：" + rejectdirtnum));
                                //更新客户接收数量 接收含税金额 未税金额
                                getSalesReceiptMapper().updateReceiptDetailBack(receiptDetail);
                                //更新销售入库单明细中来源回单编号
                                //									getSalesRejectBillMapper().updateDirectRejectBillDetailIsrefer(rejectBillDetail.getId(), "1");
                                //更新来源的来源单据编号来源单据明细编号
                                storageForSalesService.updateSaleRejectEnterDetailIDs(rejectBillDetail.getBillid(), rejectBillDetail.getId(), receiptDetail.getBillid(), receiptDetail.getId());
                                rejectnum = rejectnum.subtract(rejectdirtnum);
                                relatReceiptDetail = receiptDetail;
                                if (rejectnum.compareTo(BigDecimal.ZERO) == 0) {
                                    break;
                                }
                            }
                        }
                    }
                    //更新销售退货通知单 直退关联数量
                    RejectBillDetail detail = new RejectBillDetail();
                    detail.setBillid(rejectBillDetail.getBillid());
                    detail.setId(rejectBillDetail.getId());
                    detail.setUnitnum(rejectBillDetail.getUnitnum());
                    detail.setTaxprice(relatReceiptDetail.getTaxprice());
                    BigDecimal taxamount=detail.getTaxprice().multiply(detail.getUnitnum());
                    detail.setTaxamount(taxamount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                    detail.setNotaxprice(relatReceiptDetail.getNotaxprice());
                    BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, detail.getTaxtype());
                    detail.setNotaxamount(notaxamount);
                    detail.setTax(detail.getTaxamount().subtract(detail.getNotaxamount()));
                    detail.setIsrefer("1");
                    //更新销售退货通知单明细
                    getSalesRejectBillMapper().updateRejectBillDetailBack(detail);
                    //更新销售退货入库单明细
                    storageForSalesService.updateSaleRejectEnterDetailByRejectDetail(detail);
                }
                for(String rejectbillid : rejectbillidArr){
                    Map map = new HashMap();
                    map.put("id",rejectbillid);
                    RejectBill rejectBill = getSalesRejectBillMapper().getRejectBill(map);
                    if(null!=rejectBill && "3".equals(rejectBill.getStatus()) && rejectBill.getCustomerid().equals(receipt.getCustomerid())){
                        //更新销售退货通知单关联回单编号
                        RejectBill updaterejectBill = new RejectBill();
                        updaterejectBill.setReceiptid(receiptid);
                        updaterejectBill.setId(rejectbillid);
                        int i = getSalesRejectBillMapper().updateRejectBill(updaterejectBill);
                        flag = i > 0;
                        //通过销售退货通知单编号 更新下游单据单据金额
                        storageForSalesService.updateSaleRejectEnterAmount(rejectbillid);
                    }
                }
            } else {
                if (StringUtils.isEmpty(msg)) {
                    msg = "销售发货通知单明细中的数量大于回单明细中的数量。不能关联。";
                } else {
                    msg += "销售发货通知单明细中的数量大于回单明细中的数量。不能关联。";
                }
            }
        }

        Map map = new HashMap();
        map.put("flag", flag);
        map.put("msg", msg);
        return map;
    }

    @Override
    public Map rejectNumBigerThanReceiptNum(String receiptid,String rejectbillids) throws Exception {
        String[] rejectbillidArr = rejectbillids.split(",");
        boolean flag = true, isdistinct = false;
        String msg = "", biggoodsids = "", receiptgoods = "", receiptbarcodes = "";
        List<RejectBillDetail> rejectBillDetailList = getSalesRejectBillMapper().getRejectBillDetailListByBills(rejectbillids);

        Receipt receipt = getReceipt(receiptid);
        //销售发货回单明细列表
        List<ReceiptDetail> receiptList = getSalesReceiptMapper().getReceiptDetailListByReceipt(receiptid);
        for (ReceiptDetail receiptDetail2 : receiptList) {
            receiptgoods += receiptDetail2.getGoodsid() + ",";
            GoodsInfo goodsInfo = getGoodsInfoByID(receiptDetail2.getGoodsid());
            if (null != goodsInfo) {
                receiptbarcodes += goodsInfo.getBarcode() + ",";
            }
        }
        List<ReceiptDetail> receiptList2 = getSalesReceiptMapper().getReceiptDetailListByReceiptGroupGoods(receiptid);
        for (RejectBillDetail rejectBillDetail : rejectBillDetailList) {
            GoodsInfo rejectGoodsInfo = getAllGoodsInfoByID(rejectBillDetail.getGoodsid());
            //退货通知单商品编码包含在回单商品编码集中
            if (receiptgoods.indexOf(rejectBillDetail.getGoodsid()) == -1) {
                //通知单商品编码不包含在回单商品编码集中，判断其条形码是否包含在回单条形码中
                if (null != rejectGoodsInfo.getBarcode() && receiptbarcodes.indexOf(rejectGoodsInfo.getBarcode()) == -1) {
                    isdistinct = true;
                } else {
                    //条形码相同，判断品牌编码是否相同，若不相同，停止以下操作
                    for (ReceiptDetail receiptDetail2 : receiptList) {
                        GoodsInfo goodsInfo = getGoodsInfoByID(receiptDetail2.getGoodsid());
                        if (rejectGoodsInfo.getBarcode().equals(goodsInfo.getBarcode()) && !rejectGoodsInfo.getBrand().equals(goodsInfo.getBrand())) {
                            isdistinct = true;
                        }
                    }
                }
            }
            //通过相同商品编码关联 商品编码相同的商品如果数量不一致 再去关联相同条形码
            for (ReceiptDetail receiptDetail : receiptList2) {
                GoodsInfo receiptGoodsInfo = getAllGoodsInfoByID(receiptDetail.getGoodsid());
                //判断销售退货通知单 且回单明细不是折扣
                if (rejectBillDetail.getGoodsid().equals(receiptDetail.getGoodsid())
                        && !"1".equals(receiptDetail.getIsdiscount())) {
                    //退货通知单某商品的数量大于回单中该商品的数量要进行提示，并停止以下操作
                    if (rejectBillDetail.getUnitnum().compareTo(receiptDetail.getReceiptnum()) == 1) {
                        if (StringUtils.isEmpty(biggoodsids)) {
                            biggoodsids = receiptDetail.getGoodsid();
                        } else {
                            biggoodsids += "," + receiptDetail.getGoodsid();
                        }
                        rejectBillDetail.setUnitnum(rejectBillDetail.getUnitnum().subtract(receiptDetail.getReceiptnum()));
                        continue;
                    } else {
                        rejectBillDetail.setUnitnum(BigDecimal.ZERO);
                    }
                }
            }
        }
        for (RejectBillDetail rejectBillDetail : rejectBillDetailList) {
            GoodsInfo rejectGoodsInfo = getAllGoodsInfoByID(rejectBillDetail.getGoodsid());
            //通过相同商品编码关联 商品编码相同的商品如果数量不一致 再去关联相同条形码
            for (ReceiptDetail receiptDetail : receiptList2) {
                GoodsInfo receiptGoodsInfo = getAllGoodsInfoByID(receiptDetail.getGoodsid());
                //判断销售退货通知单 且回单明细不是折扣
                if (rejectGoodsInfo.getBarcode().equals(receiptGoodsInfo.getBarcode())
                        && !"1".equals(receiptDetail.getIsdiscount())) {
                    //退货通知单某商品的数量大于回单中该商品的数量要进行提示，并停止以下操作
                    if (rejectBillDetail.getUnitnum().compareTo(receiptDetail.getReceiptnum()) == 1) {
                        if (StringUtils.isEmpty(biggoodsids)) {
                            biggoodsids = rejectGoodsInfo.getBarcode();
                        } else {
                            biggoodsids += "," + rejectGoodsInfo.getBarcode();
                        }
                        rejectBillDetail.setUnitnum(rejectBillDetail.getUnitnum().subtract(receiptDetail.getReceiptnum()));
                        continue;
                    } else {
                        rejectBillDetail.setUnitnum(BigDecimal.ZERO);
                    }
                }
            }
            if(rejectBillDetail.getUnitnum().compareTo(BigDecimal.ZERO)!=0){
                flag = false;
            }
        }
        if (isdistinct) {
            msg = "销售退货通知单明细与回单中的商品明细不一致。不能关联!";
        }
        if (StringUtils.isNotEmpty(biggoodsids)) {
            if (StringUtils.isEmpty(msg)) {
                msg = "商品或者条码：" + biggoodsids + "退货通知单的数量大于发货回单的数量。<br/>";
            } else {
                msg += "<br/>" + "商品：" + biggoodsids + "退货通知单的数量大于发货回单的数量。<br/>";
            }
        }

        Map map = new HashMap();
        map.put("msg", msg);
        map.put("flag",flag);
        return map;
    }

    @Override
    public CustomerPushBalance getCustomerPushBalanceByID(String id)
            throws Exception {
        CustomerPushBalance customerPushBalance = customerPushBanlanceService.showCustomerPushBanlanceInfo(id);
        if(null!=customerPushBalance){
        	TaxType taxType = getTaxType(customerPushBalance.getDefaulttaxtype());
            if (taxType != null) {
            	customerPushBalance.setDefaulttaxtypename(taxType.getName());
            }
        }
        return customerPushBalance;
    }

    @Override
    public BeginAmount getBeginAmountByID(String id) throws Exception {
        BeginAmount beginAmount = beginAmountService.getBeginAmountByID(id);
        return beginAmount;
    }
    @Override
    public Receipt getTotalReceiptAmount(String ids) throws Exception {
        if(StringUtils.isNotEmpty(ids)){
            String[] idArr = ids.split(",");
            return super.getSalesReceiptMapper().getTotalReceiptAmount(idArr);
        }
        return null;
    }

    @Override
    public Map doWriteoffSalesReceipt(Map map) throws Exception {
        String receiptids = (String)map.get("receiptids");
        String customerid = (String)map.get("customerid");
        String amountStr = (String)map.get("amount");//已关联金额
        String tailamountStr = (String)map.get("tailamount");//尾差金额
        String receiptamountStr = (String)map.get("receiptamount");//回单金额
        String writeoffamountStr = (String)map.get("writeoffamount");//核销金额
        String detailListStr = (String)map.get("detailList");//收款单列表
        boolean flag = false;
        String msg = "",salesInvoiceid = "";
        if(StringUtils.isNotEmpty(receiptids)){
            //根据回单编号生成销售核销
            List<Map> list = new ArrayList<Map>();
            String[] idArr = receiptids.split(",");
            for(String id : idArr){
                Receipt receipt = getReceipt(id);
                if(null != receipt && "3".equals(receipt.getStatus())){
                    List<ReceiptDetail> detailList = receipt.getReceiptDetailList();
                    for(ReceiptDetail receiptDetail : detailList){
                        Map map2 = new HashMap();
                        map2.put("billtype","1");
                        map2.put("billid",receiptDetail.getBillid());
                        map2.put("detailid",receiptDetail.getId());
                        map2.put("isdiscount",receiptDetail.getIsdiscount());
                        list.add(map2);
                    }
                }
            }
            String ids = "";
            if(list.size() != 0){
                JSONArray json = JSONArray.fromObject(list);
                ids = json.toString();
            }
            //销售核销
            Map map2 = salesInvoiceService.addSalesInvoiceByReceiptAndRejectbill(ids,customerid,"1");
            if(map2.get("flag").equals(true)){
                salesInvoiceid = (String)map2.get("id");
                msg = (String)map2.get("msg");

                if(StringUtils.isNotEmpty(salesInvoiceid)){
                    BigDecimal amount = BigDecimal.ZERO;
                    if(null!=amountStr && !"".equals(amountStr)){
                        amount = new BigDecimal(amountStr);
                    }
                    //核销生成的销售核销step1关联收款单step2核销
                    //step1关联收款单
                    List collectionOrderList = JSONUtils.jsonStrToList(detailListStr, new CollectionOrderRelate());
                    boolean flag2 = salesStatementService.addRelateCollectionOrder(salesInvoiceid, "1", amount, collectionOrderList);

                    //step2核销
                    if(flag2){
                        List<SalesStatement> list2 = new ArrayList<SalesStatement>();
                        SalesStatement salesStatement = new SalesStatement();
                        salesStatement.setBillid(salesInvoiceid);
                        BigDecimal writeoffamount = BigDecimal.ZERO;
                        if(null!=writeoffamountStr && !"".equals(writeoffamountStr)){
                            writeoffamount = new BigDecimal(writeoffamountStr);
                        }
                        salesStatement.setWriteoffamount(writeoffamount);
                        BigDecimal tailamount = BigDecimal.ZERO;
                        if(null!=tailamountStr && !"".equals(tailamountStr)){
                            tailamount = new BigDecimal(tailamountStr);
                        }
                        salesStatement.setTailamount(tailamount);
                        list2.add(salesStatement);
                        Map map3 = salesStatementService.auditWriteoffCollectionOrder(customerid, list2);
                        flag = map3.get("flag").equals(true);
                    }
                }
            }else{
                msg = (String)map2.get("msg");
            }
        }
        Map retmap = new HashMap();
        retmap.put("flag",flag);
        retmap.put("salesInvoiceid",salesInvoiceid);
        retmap.put("msg",msg);
        return retmap;
    }

    @Override
    public Map doDirectWriteoffSalesReceipt(Map map) throws Exception {
        String receiptids = (String)map.get("ids");
        String customerid = (String)map.get("customerid");
        BigDecimal receiptamount = BigDecimal.ZERO;
        Receipt receiptSum = getTotalReceiptAmount(receiptids);
        if(null != receiptSum){
            receiptamount = receiptSum.getTotaltaxamount();
        }
        boolean flag = false;
        String msg = "",salesInvoiceid = "";
        if(StringUtils.isNotEmpty(receiptids)){
            //根据回单编号生成销售核销
            List<Map> list = new ArrayList<Map>();
            String[] idArr = receiptids.split(",");
            String doids = "";
            for(String id : idArr){
                Receipt receipt = getReceipt(id);
                if(null != receipt && "3".equals(receipt.getStatus())){
                    if(StringUtils.isEmpty(doids)){
                        doids = receipt.getId();
                    }else{
                        doids += "," + receipt.getId();
                    }
                    List<ReceiptDetail> detailList = receipt.getReceiptDetailList();
                    for(ReceiptDetail receiptDetail : detailList){
                        Map map2 = new HashMap();
                        map2.put("billtype","1");
                        map2.put("billid",receiptDetail.getBillid());
                        map2.put("detailid",receiptDetail.getId());
                        map2.put("isdiscount",receiptDetail.getIsdiscount());
                        list.add(map2);
                    }
                }
            }
            String ids = "";
            if(list.size() != 0){
                JSONArray json = JSONArray.fromObject(list);
                ids = json.toString();
            }
            //销售核销
            Map map2 = salesInvoiceService.addSalesInvoiceByReceiptAndRejectbill(ids,customerid,"1");
            if(map2.get("flag").equals(true)){
                salesInvoiceid = (String)map2.get("id");
                msg = (String)map2.get("msg");

                if(StringUtils.isNotEmpty(salesInvoiceid)){
                    //根据客户编码新增保存并审核收款单
                    CollectionOrder collectionOrder = new CollectionOrder();
                    collectionOrder.setBusinessdate(CommonUtils.getTodayDataStr());
                    collectionOrder.setStatus("2");
                    collectionOrder.setCollectiontype("1");
                    collectionOrder.setCustomerid(customerid);
                    String bankid = getSysParamValue("DefaultBankID");
                    if(StringUtils.isNotEmpty(bankid)){
                        collectionOrder.setBank(bankid);
                    }
                    collectionOrder.setAmount(receiptamount);
                    Map map1 = collectionOrderService.addCollectionOrderSaveAudit(collectionOrder);
                    if(null != map1.get("auditflag") && map1.get("auditflag").equals(true)){
                        String collectionOrderid = null != map1.get("collectionOrderId") ? (String)map1.get("collectionOrderId") : "";
                        if(StringUtils.isNotEmpty(collectionOrderid)){
                            List<CollectionOrderRelate> relateList = new ArrayList<CollectionOrderRelate>();
                            List<CollectionOrder> list1 = collectionOrderService.getCollectionOrderListByIds(collectionOrderid);
                            for(CollectionOrder collectionOrder1 : list1){
                                CollectionOrderRelate collectionOrderRelate = null;
                                collectionOrderRelate = new CollectionOrderRelate();
                                collectionOrderRelate.setId(collectionOrder1.getId());
                                collectionOrderRelate.setBusinessdate(collectionOrder1.getBusinessdate());
                                collectionOrderRelate.setAmount(collectionOrder1.getAmount());
                                collectionOrderRelate.setWriteoffamount(collectionOrder1.getAmount());
                                collectionOrderRelate.setRemainderamount(BigDecimal.ZERO);
                                collectionOrderRelate.setRelateamount(collectionOrder1.getAmount());
                                relateList.add(collectionOrderRelate);
                            }
                            if(relateList.size() != 0){
                                //核销生成的销售核销step1关联收款单step2核销
                                //step1关联收款单
                                boolean flag2 = salesStatementService.addRelateCollectionOrder(salesInvoiceid, "1", receiptamount, relateList);

                                //step2核销
                                if(flag2){
                                    List<SalesStatement> list2 = new ArrayList<SalesStatement>();
                                    SalesStatement salesStatement = new SalesStatement();
                                    salesStatement.setBillid(salesInvoiceid);
                                    salesStatement.setWriteoffamount(receiptamount);
                                    salesStatement.setTailamount(BigDecimal.ZERO);
                                    list2.add(salesStatement);
                                    Map map3 = salesStatementService.auditWriteoffCollectionOrder(customerid, list2);
                                    flag = map3.get("flag").equals(true);
                                }
                            }
                        }
                    }
                }
            }else{
                msg = (String)map2.get("msg");
            }
        }
        Map retmap = new HashMap();
        retmap.put("flag",flag);
        retmap.put("salesInvoiceid",salesInvoiceid);
        retmap.put("msg",msg);
        return retmap;
    }

    /**
     * 提供申请核销 获取回单明细列表
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public List<ReceiptDetail> getReceiptDetailListByApplyWriteoff(String id) throws Exception {
        List<ReceiptDetail> receiptDetailList = getSalesReceiptMapper().getReceiptDetailListSumDiscount(id);
        for (ReceiptDetail receiptDetail : receiptDetailList) {
            TaxType taxType = getTaxType(receiptDetail.getTaxtype());
            if (taxType != null) {
                receiptDetail.setTaxtypename(taxType.getName());
            }
            StorageLocation storageLocation = getStorageLocation(receiptDetail.getStoragelocationid());
            if (storageLocation != null) {
                receiptDetail.setStoragelocationname(storageLocation.getName());
            }
            //折扣显示处理
            GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(getAllGoodsInfoByID(receiptDetail.getGoodsid()));
            if ("1".equals(receiptDetail.getIsdiscount())) {
                goodsInfo.setBarcode(null);
                goodsInfo.setBoxnum(null);
                goodsInfo.setName("(折扣)" + goodsInfo.getName());
                receiptDetail.setUnitnum(null);
                receiptDetail.setAuxnumdetail(null);
                receiptDetail.setTaxprice(null);
                if ("1".equals(receiptDetail.getIsbranddiscount())) {
                    receiptDetail.setGoodsid("");
                    goodsInfo.setName("(折扣)" + goodsInfo.getBrandName());
                    receiptDetail.setIsdiscount("2");
                }
            }
            receiptDetail.setGoodsInfo(goodsInfo);
            if (null != goodsInfo && null != goodsInfo.getBoxnum()) {
                receiptDetail.setBoxprice(goodsInfo.getBoxnum().multiply(receiptDetail.getTaxprice() == null ? BigDecimal.ZERO : receiptDetail.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
            }
        }
        return receiptDetailList;
    }

    @Override
    public List<Map<String, Object>> getReceiptByExport(PageMap pageMap) throws Exception {

        List<Map<String, Object>> rejectBill = getSalesReceiptMapper().getReceiptListExport(pageMap);
        String id = "";
        //对其中既有商品折扣 又有品牌折扣的 商品折扣信息 进行筛选
        for(int i = 0 ;i<rejectBill.size(); i++){
            Map<String, Object> map = rejectBill.get(i);
            id = (String) map.get("id");
            String isbranddiscount = (String) map.get("isbranddiscount");
            String isdiscount = (String) map.get("isdiscount");

            if(isdiscount.equals("1") && isbranddiscount.equals("1")){
                rejectBill.remove(map);
                --i;
            }
        }

        Receipt receipt = getReceipt(id);
        List<ReceiptDetail> detail = receipt.getReceiptDetailList();
        for(ReceiptDetail d : detail){
            if(d.getIsbranddiscount().equals("1")){
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("goodsname", "(折扣)"+getGoodsBrandByID(d.getBrandid()).getName());
                map.put("receipttaxamount", d.getReceipttaxamount());
                map.put("taxamount",d.getTaxamount());
                map.put("remark","品牌折扣");
                map.put("id",receipt.getId());
                map.put("businessdate",receipt.getBusinessdate());
                map.put("customerid",receipt.getCustomerid());
                map.put("status",receipt.getStatus());
                map.put("salesuser",receipt.getSalesuser());
                map.put("saleorderid",receipt.getSaleorderid());
                rejectBill.add(map);
            }
        }

        for(Map<String, Object> map :rejectBill){
            //客户
            String customerid = (String)map.get("customerid");
            Customer customerInfo = getBaseCustomerMapper().getCustomerInfo(customerid);
            if(null != customerInfo) {
                map.put("customername", customerInfo.getName());
            }

            //销售部门
            String saledeptid =  (String) map.get("salesdept");
            DepartMent departMentInfo = getDepartMentById(saledeptid);
            if( null != departMentInfo){
                map.put("salesdeptname",departMentInfo.getName());
            }

            //客户业务员
            String salesuserid = (String) map.get("salesuser");
            Personnel salesuserInfo = getPersonnelById(salesuserid);
            if( null != salesuserInfo){
                map.put("salesusername",salesuserInfo.getName());
            }


            String isbranddiscount = (String) map.get("isbranddiscount");
            String isdiscount = (String) map.get("isdiscount");
            //商品
            String goodsid = (String) map.get("goodsid");
            GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
            //判断是否是折扣
            if(null != goodsInfo &&  isdiscount.equals("1") && !isbranddiscount.equals("1") ){
                map.put("goodsname", "(折扣)"+goodsInfo.getName());
                map.put("remark","商品折扣");
                map.put("barcode",goodsInfo.getBarcode());
                map.put("boxnum",goodsInfo.getBoxnum());
                BigDecimal taxprice = (BigDecimal)map.get("taxprice");
                map.put("boxprice",goodsInfo.getBoxnum().multiply(taxprice));
            }else  if(null != goodsInfo) {
                map.put("goodsname", goodsInfo.getName());
                map.put("barcode",goodsInfo.getBarcode());
                map.put("boxnum",goodsInfo.getBoxnum());
                BigDecimal taxprice = (BigDecimal)map.get("taxprice");
                map.put("boxprice",goodsInfo.getBoxnum().multiply(taxprice));
            }


            String state = (String) map.get("status");
            if(null != state){
                if(state.equals("2")){
                    map.put("state","保存");
                }else if(state.equals("3")){
                    map.put("state","审核通过");
                }else {
                    map.put("state","关闭");
                }
            }

        }

        return rejectBill;
    }
    @Override
    public Map<String, List<Map>> getReceiptListForHDCXByMap(Map map) throws Exception{
        LinkedHashMap<String, List<Map>> printData=new LinkedHashMap<String, List<Map>>();
    	List<Map> dataList=getSalesReceiptMapper().getReceiptListForHDCXByMap(map);
    	List<Map> printList=null;
		Map queryMap=new HashMap();
    	if(null!=dataList && dataList.size()>0){
    		Iterator<Map> it = dataList.iterator();
    		 while (it.hasNext()){
    	        Map itemData=it.next();
    	        if(null!=itemData){
    	        	String tmpstr=(String)itemData.get("customerid");
    	        	if(null!=tmpstr && !"".equals(tmpstr.trim())){
    					Customer customer = getCustomerByID(tmpstr.trim());
    					if(null!=customer){
        					itemData.put("customername",customer.getName()); 
        					
        					if(StringUtils.isNotEmpty(customer.getContact())){
        						queryMap.clear();
        						queryMap.put("id", customer.getContact());
        						Contacter contacter = getBaseFilesContacterMapper().getContacterDetail(queryMap);
        						if(contacter != null){
        							itemData.put("customercontact", contacter.getName());
        							itemData.put("customercontacttel", customer.getMobile());
        						}
        					}   						
    					}
                	}
    	        	tmpstr=(String)itemData.get("brandid");
    	        	if(null!=tmpstr && !"".equals(tmpstr.trim())){
	    				Brand brand = getBaseFilesGoodsMapper().getBrandInfo(tmpstr.trim());
	    				if(null!=brand){
	    					itemData.put("brandname", brand.getName());
	    				}
    	        	}
    	        	GoodsInfo goodsInfo=null;
    	        	tmpstr=(String)itemData.get("goodsid");
    	        	if(null!=tmpstr && !"".equals(tmpstr.trim())){
    	        		goodsInfo= getAllGoodsInfoByID(tmpstr.trim());
    	        	}
                    if(null==goodsInfo){
                        goodsInfo=new GoodsInfo();
                    }
    	        	itemData.put("goodsInfo", goodsInfo);
    	        	
    	        	String billid=(String)itemData.get("billid");
    	        	if(null!=billid  && !"".equals(billid.trim())){
                        if(printData.containsKey(billid.trim())){
                            printList=(List<Map>)printData.get(billid.trim());
                            if(null==printList){
                                printList=new ArrayList<Map>();
                            }
                        }else{
                            printList=new ArrayList<Map>();
                        }
                        printList.add(itemData);
                        printData.put(billid.trim(), printList);
            			
            		}else{
                        continue;
            		}
    	        	BigDecimal taxprice=(BigDecimal)itemData.get("taxprice");
    	        	BigDecimal inittaxprice=(BigDecimal)itemData.get("inittaxprice");
    	        	BigDecimal unitnum=(BigDecimal)itemData.get("unitnum");
    	        	BigDecimal ceamount=BigDecimal.ZERO;
    	        	if(null!=taxprice 
    	        			&& null!=inittaxprice 
    	        			&& null!=unitnum){
    	        		if(inittaxprice.compareTo(taxprice)!=0){
    	        			ceamount=taxprice.subtract(inittaxprice).multiply(unitnum).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);
    	        		}
    	        	}
    	        	itemData.put("ceamount", ceamount);
    	        }
    	     }
    	}
    	return printData;
    }

    @Override
    public BigDecimal getReceivableAmountByCustomerid(String customerid) throws Exception {
        return getSalesReceiptMapper().getReceivableAmountByCustomerid(customerid);
    }

    /**
     * 获取品牌折扣后的各商品明细数据
     * @param map
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-08-12
     */
    private List getDiscountReceiptDetailList(Map map)throws Exception{
        List<ReceiptDetail> noDiscountDetailList = new ArrayList<ReceiptDetail>();
        if(!map.isEmpty()){
            String discounttype = (String)map.get("discounttype");
            String receiptid = (String)map.get("receiptid");
            String brandid = (String)map.get("brandid");
            BigDecimal totalDiscountAmount = null != map.get("taxamount") ? new BigDecimal((String)map.get("taxamount")) : BigDecimal.ZERO;
            String discount = (String)map.get("discount");
            String repartitiontype = (String)map.get("repartitiontype");

            //获取该品牌折扣且isdiscount为0的所有商品明细
            noDiscountDetailList = getSalesReceiptMapper().getReceiptDetailNoDiscountList(brandid,receiptid);
            BigDecimal totalamount = BigDecimal.ZERO;
            BigDecimal totalunitnum = BigDecimal.ZERO;
            BigDecimal totalboxSum = BigDecimal.ZERO;
            for(ReceiptDetail receiptDetail : noDiscountDetailList){
                totalamount = totalamount.add(receiptDetail.getTaxamount());
                totalunitnum = totalunitnum.add(receiptDetail.getUnitnum());
                totalboxSum = totalboxSum.add(receiptDetail.getTotalbox());
            }
            for(ReceiptDetail receiptDetail : noDiscountDetailList){
                String remark = receiptDetail.getRemark();
                int brandindex = receiptDetail.getRemark().indexOf("品牌折扣");
                int receiptindex = receiptDetail.getRemark().indexOf("回单折扣");
                if(brandindex != -1){
                    if(brandindex > 0){
                        remark = remark.substring(0,brandindex-1);
                    }else if(brandindex == 0){
                        remark = remark.substring(0,brandindex);
                    }

                }else if(receiptindex != -1){
                    if(receiptindex > 0){
                        remark = remark.substring(0,receiptindex-1);
                    }else if(receiptindex == 0){
                        remark = remark.substring(0,receiptindex);
                    }
                }
                if("1".equals(discounttype)){
                    if(StringUtils.isNotEmpty(remark)){
                        remark = remark+"，品牌折扣："+discount+"折";
                    }else{
                        remark = "品牌折扣："+discount+"折";
                    }
                }else if("2".equals(discounttype)){
                    if(StringUtils.isNotEmpty(remark)){
                        remark = remark+"，回单折扣："+discount+"折";
                    }else{
                        remark = "回单折扣："+discount+"折";
                    }
                }
                receiptDetail.setRemark(CommonUtils.strDigitNumDeal(remark));

                //计算平摊到商品中的 各折扣金额
                BigDecimal discountamount = BigDecimal.ZERO;
                if("0".equals(repartitiontype)){//金额
                    discountamount = receiptDetail.getTaxamount().divide(totalamount, 6, BigDecimal.ROUND_HALF_UP).multiply(totalDiscountAmount);
                }else if("1".equals(repartitiontype)){//数量
                    discountamount = receiptDetail.getUnitnum().divide(totalunitnum, 6, BigDecimal.ROUND_HALF_UP).multiply(totalDiscountAmount);
                }else if("2".equals(repartitiontype)){//箱数
                    discountamount = receiptDetail.getTotalbox().divide(totalboxSum, 6, BigDecimal.ROUND_HALF_UP).multiply(totalDiscountAmount);
                }
                receiptDetail.setDiscountamount(discountamount);

                receiptDetail.setReceipttaxamount(receiptDetail.getTaxamount().add(discountamount));
                BigDecimal taxprice = receiptDetail.getReceipttaxamount().divide(receiptDetail.getUnitnum(), 6, BigDecimal.ROUND_HALF_UP);
                receiptDetail.setTaxprice(taxprice);

                TaxType taxType = getTaxType(receiptDetail.getTaxtype());
                if (taxType != null) {
                    receiptDetail.setTaxtypename(taxType.getName());
                }
                StorageLocation storageLocation = getStorageLocation(receiptDetail.getStoragelocationid());
                if (storageLocation != null) {
                    receiptDetail.setStoragelocationname(storageLocation.getName());
                }
                GoodsInfo goodsInfo = getGoodsInfoByID(receiptDetail.getGoodsid());
                if (null != goodsInfo){
                    receiptDetail.setGoodsInfo(goodsInfo);
                    receiptDetail.setBuyprice(goodsInfo.getNewbuyprice());
                    receiptDetail.setBoxprice(goodsInfo.getBoxnum().multiply(receiptDetail.getTaxprice() == null ? BigDecimal.ZERO : receiptDetail.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                }
            }
        }
        return noDiscountDetailList;
    }

    @Override
    public List addSaveReceiptDetailBrandDiscount(Map map) throws Exception {
        map.put("discounttype","1");//品牌折扣
        List<ReceiptDetail> noDiscountDetailList = getDiscountReceiptDetailList(map);
        return noDiscountDetailList;
    }

    @Override
    public List addSaveBillDetailReceiptDiscount(Map map) throws Exception {
        List<ReceiptDetail> noDiscountDetailList = new ArrayList<ReceiptDetail>();
        if(!map.isEmpty()){
            map.put("discounttype","2");//回单折扣
            String receiptid = (String)map.get("receiptid");
            BigDecimal totalamount = null != map.get("totalamount") ? new BigDecimal((String)map.get("totalamount")) : BigDecimal.ZERO;
            BigDecimal totalDiscountAmount = null != map.get("taxamount") ? new BigDecimal((String)map.get("taxamount")) : BigDecimal.ZERO;

            BigDecimal countAmount = BigDecimal.ZERO;
            List<ReceiptDetail> brandList = getSalesReceiptMapper().getNoDiscountReceiptDetailListGroupBrand(receiptid);
            if(brandList.size() != 0){
                //未折扣商品明细合计
                for(ReceiptDetail brandSumReceiptDetail : brandList){
                    countAmount = countAmount.add(brandSumReceiptDetail.getTaxamount());
                }

                //根据各品牌金额合计比例摊分折扣金额
                Map<String,String> branddiscountMap = new HashMap<String,String>();
                for(ReceiptDetail brandSumReceiptDetail : brandList){
                    BigDecimal branddiscount = brandSumReceiptDetail.getTaxamount().divide(countAmount,6,BigDecimal.ROUND_HALF_UP).multiply(totalDiscountAmount);

                    branddiscountMap.put(brandSumReceiptDetail.getBrandid(),branddiscount.toString());
                }

                //根据各品牌摊分的金额重计算商品明细单据
                for (String key : branddiscountMap.keySet()) {
                    List<ReceiptDetail> list2 = null;
                    list2 = new ArrayList<ReceiptDetail>();
                    map.put("brandid",key);
                    map.put("taxamount",(String)branddiscountMap.get(key));
                    list2 = getDiscountReceiptDetailList(map);
                    noDiscountDetailList.addAll(list2);
                }
            }
        }
        return noDiscountDetailList;
    }

	@Override
    public Receipt getReceiptBySaleorderid(String saleorderid) {

        return getSalesReceiptMapper().getReceiptBySaleorderid(saleorderid);
    }
    @Override
    public List<ReceiptDetail> getReceiptDetailListByMap(Map map) throws Exception{
        List<ReceiptDetail> receiptDetailList = getSalesReceiptMapper().getReceiptDetailListByMap(map);
        for (ReceiptDetail receiptDetail : receiptDetailList) {
            TaxType taxType = getTaxType(receiptDetail.getTaxtype());
            if (taxType != null) {
                receiptDetail.setTaxtypename(taxType.getName());
            }
            StorageLocation storageLocation = getStorageLocation(receiptDetail.getStoragelocationid());
            if (storageLocation != null) {
                receiptDetail.setStoragelocationname(storageLocation.getName());
            }
            //折扣显示处理
            GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(getAllGoodsInfoByID(receiptDetail.getGoodsid()));
            if ("1".equals(receiptDetail.getIsdiscount())) {
                goodsInfo.setBarcode(null);
                goodsInfo.setBoxnum(null);
                goodsInfo.setName("(折扣)" + goodsInfo.getName());
                receiptDetail.setUnitnum(null);
                receiptDetail.setAuxnumdetail(null);
                receiptDetail.setTaxprice(null);
                if ("1".equals(receiptDetail.getIsbranddiscount())) {
                    receiptDetail.setGoodsid("");
                    goodsInfo.setName("(折扣)" + goodsInfo.getBrandName());
                    receiptDetail.setIsdiscount("2");
                }
            }
            receiptDetail.setGoodsInfo(goodsInfo);
            if (null != goodsInfo && null != goodsInfo.getBoxnum()) {
                receiptDetail.setBuyprice(goodsInfo.getNewbuyprice());
                receiptDetail.setBoxprice(goodsInfo.getBoxnum().multiply(receiptDetail.getTaxprice() == null ? BigDecimal.ZERO : receiptDetail.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
            }
        }
        return receiptDetailList;
    }

    @Override
    public Receipt getReceiptInfoById(String id) throws Exception {
        Map map = new HashMap();
        map.put("id", id);
        Receipt receipt = getSalesReceiptMapper().getReceipt(map);
        if (null != receipt) {
            Customer customer = getCustomerByID(receipt.getCustomerid());
            if (null != customer) {
                receipt.setCustomername(customer.getName());
            }
            Contacter contacter = getContacterById(receipt.getHandlerid());
            if (null != contacter) {
                receipt.setHandlername(contacter.getName());
            }
            DepartMent departMent = getDepartmentByDeptid(receipt.getSalesdept());
            if (null != departMent) {
                receipt.setSalesdeptname(departMent.getName());
            }
            Personnel personnel = getPersonnelById(receipt.getSalesuser());
            if (null != personnel) {
                receipt.setSalesusername(personnel.getName());
            }
            Payment payment = getPaymentByID(receipt.getPaytype());
            if (null != payment) {
                receipt.setPaytypename(payment.getName());
            }
            Settlement settlement = getSettlementByID(receipt.getSettletype());
            if (null != settlement) {
                receipt.setSettletypename(settlement.getName());
            }
            StorageInfo storageInfo = getStorageInfoByID(receipt.getStorageid());
            if (null != storageInfo) {
                receipt.setStoragename(storageInfo.getName());
            }

        }
        return receipt;
    }
    @Override
    public List<ReceiptDetail> getReceiptDetailListSumDiscountByMap(Map map) throws Exception{
        List<ReceiptDetail> receiptDetailList = getSalesReceiptMapper().getReceiptDetailListSumDiscountByMap(map);
        for (ReceiptDetail receiptDetail : receiptDetailList) {
            TaxType taxType = getTaxType(receiptDetail.getTaxtype());
            if (taxType != null) {
                receiptDetail.setTaxtypename(taxType.getName());
            }
            StorageLocation storageLocation = getStorageLocation(receiptDetail.getStoragelocationid());
            if (storageLocation != null) {
                receiptDetail.setStoragelocationname(storageLocation.getName());
            }
            //折扣显示处理
            GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(getAllGoodsInfoByID(receiptDetail.getGoodsid()));
            if ("1".equals(receiptDetail.getIsdiscount())) {
                goodsInfo.setBarcode(null);
                goodsInfo.setBoxnum(null);
                goodsInfo.setName("(折扣)" + goodsInfo.getName());
                receiptDetail.setUnitnum(null);
                receiptDetail.setAuxnumdetail(null);
                receiptDetail.setTaxprice(null);
                if ("1".equals(receiptDetail.getIsbranddiscount())) {
                    receiptDetail.setGoodsid("");
                    goodsInfo.setName("(折扣)" + goodsInfo.getBrandName());
                    receiptDetail.setIsdiscount("2");
                }
            }
            receiptDetail.setGoodsInfo(goodsInfo);
            if (null != goodsInfo && null != goodsInfo.getBoxnum()) {
                receiptDetail.setBuyprice(goodsInfo.getNewbuyprice());
                receiptDetail.setBoxprice(goodsInfo.getBoxnum().multiply(receiptDetail.getTaxprice() == null ? BigDecimal.ZERO : receiptDetail.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
            }
        }
        return receiptDetailList;
    }
    @Override
    public List<Receipt> getReceiptListBy(Map map) throws Exception{

        String showPCustomerName=(String)map.get("showPCustomerName");
        List<Receipt> list = getSalesReceiptMapper().getReceiptListBy(map);
        Customer pCustomer =null;
        String showDetailListType=(String) map.get("showDetailListType");
        for (Receipt item : list) {
            if(StringUtils.isNotEmpty(item.getIndooruserid())) {
                Personnel indoorPerson = getPersonnelById(item.getIndooruserid());
                if (null != indoorPerson) {
                    item.setIndoorusername(indoorPerson.getName());
                }
            }
            if(StringUtils.isNotEmpty(item.getSalesdept())) {
                DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(item.getSalesdept());
                if (departMent != null) {
                    //20151229 为了打印转换为Saleout
                    item.setSalesdeptname(departMent.getName());
                }
            }
            if(StringUtils.isNotEmpty(item.getSalesuser())) {
                Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(item.getSalesuser());
                if (personnel != null) {
                    //20151229 为了打印转换为Saleout
                    item.setSalesusername(personnel.getName());
                }
            }
            Map queryMap = new HashMap();
            if(StringUtils.isNotEmpty(item.getCustomerid())) {
                queryMap.put("id", item.getCustomerid());
                Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(queryMap);
                if (null != customer) {
                    //原先把客户名称放在item的customerid中，现在改为放在cutomername中
                    item.setCustomername(customer.getName());
                    item.setCustomerInfo(customer);

                    if ("true".equals(showPCustomerName)) {
                        if (StringUtils.isNotEmpty(customer.getPid())) {
                            if (null != pCustomer && customer.getPid().equals(pCustomer.getId())) {
                                customer.setPname(pCustomer.getName());
                            } else {
                                queryMap.put("id", customer.getPid());
                                pCustomer = getBaseFilesCustomerMapper().getCustomerDetail(queryMap);
                                if (null != pCustomer) {
                                    customer.setPname(pCustomer.getName());
                                }
                            }
                        }
                    }
                    if(StringUtils.isNotEmpty(customer.getSettletype())) {
                        Settlement settlement = getSettlementByID(customer.getSettletype());
                        if (null != settlement) {
                            item.setSettletypename(settlement.getName());
                        }
                    }
                }
            }

            if(StringUtils.isNotEmpty(item.getHandlerid())) {
                queryMap.clear();
                queryMap.put("id", item.getHandlerid());
                Contacter contacter = getBaseFilesContacterMapper().getContacterDetail(queryMap);
                if (contacter != null) {
                    //20151229 为了打印转换为Saleout
                    item.setHandlername(contacter.getName());
                }
            }

            List<OrderDetail> detailList=null;
            if("print".equals(showDetailListType)){

                List<ReceiptDetail> receiptDetailList = getSalesReceiptMapper().getReceiptDetailListSumDiscount(item.getId());
                for (ReceiptDetail receiptDetail : receiptDetailList) {
                    if(StringUtils.isNotEmpty(item.getCustomerid())){
                        Customer customer = getCustomerByID(item.getCustomerid());
                        if(null!=customer){
                            //获取客户店内码
                            CustomerPrice customerPrice=getCustomerPriceByCustomerAndGoodsid(item.getCustomerid(), receiptDetail.getGoodsid());
                            if(null!=customerPrice && StringUtils.isNotEmpty(customerPrice.getShopid())){
                                receiptDetail.setShopid(customerPrice.getShopid());
                            }else if(StringUtils.isNotEmpty(customer.getPid())){
                                customerPrice=getCustomerPriceByCustomerAndGoodsid(customer.getPid(), receiptDetail.getGoodsid());
                                if(null!=customerPrice){
                                    receiptDetail.setShopid(customerPrice.getShopid());
                                }
                            }
                        }
                    }
                    StorageLocation storageLocation = getStorageLocation(receiptDetail.getStoragelocationid());
                    if(null!=storageLocation){
                        receiptDetail.setStoragelocationname(storageLocation.getName());
                    }
                    TaxType taxType = getTaxType(receiptDetail.getTaxtype());
                    if(null!=taxType){
                        receiptDetail.setTaxtypename(taxType.getName());
                    }
                    //折扣显示处理
                    GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(getAllGoodsInfoByID(receiptDetail.getGoodsid()));
                    if ("1".equals(receiptDetail.getIsdiscount())) {
                        goodsInfo.setBarcode(null);
                        goodsInfo.setBoxnum(null);
                        goodsInfo.setName("(折扣)" + goodsInfo.getName());
                        receiptDetail.setUnitnum(null);
                        receiptDetail.setAuxnumdetail(null);
                        receiptDetail.setTaxprice(null);
                        if ("1".equals(receiptDetail.getIsbranddiscount())) {
                            receiptDetail.setGoodsid("");
                            goodsInfo.setName("(折扣)" + goodsInfo.getBrandName());
                            receiptDetail.setIsdiscount("2");
                        }
                    }
                    receiptDetail.setGoodsInfo(goodsInfo);
                    if (null != goodsInfo && null != goodsInfo.getBoxnum()) {
                        receiptDetail.setBuyprice(goodsInfo.getNewbuyprice());
                        receiptDetail.setBoxprice(goodsInfo.getBoxnum().multiply(receiptDetail.getTaxprice() == null ? BigDecimal.ZERO : receiptDetail.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                    }
                }
            }
        }
        return list;
    }
    /**
     * 更新打印次数
     * @param receipt
     * @author zhanghonghui
     * @date 2017-11-06
     */
    @Override
    public boolean updateOrderPrinttimes(Receipt receipt) throws Exception{
        return getSalesReceiptMapper().updateOrderPrinttimes(receipt)>0;
    }
}

