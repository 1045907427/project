/**
 * @(#)BaseServiceImpl.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 21, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.CustomerCapitalMapper;
import com.hd.agent.account.dao.SalesFreeOrderMapper;
import com.hd.agent.account.model.CustomerCapital;
import com.hd.agent.basefiles.model.Brand;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.Settlement;
import com.hd.agent.basefiles.service.ISalesExtService;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.sales.dao.*;
import com.hd.agent.sales.model.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zhengziyong
 */
public class BaseSalesServiceImpl extends BaseFilesServiceImpl {

    private OffpriceMapper salesOffpriceMapper;
    private DispatchBillMapper salesDispatchBillMapper;
    private OrderMapper salesOrderMapper;
    private OrderDetailMapper salesOrderDetailMapper;
    private ReceiptMapper salesReceiptMapper;
    private RejectBillMapper salesRejectBillMapper;
    private OrderCarMapper salesOrderCarMapper;
    private DemandMapper salesDemandMapper;
    private PromotionPackageMapper salesPromotionMapper;
    private OweOrderMapper salesOweOrderMapper;
    private ImportSetMapper importSetMapper;
    private OrderGoodsMapper orderGoodsMapper;
  
    /**
     * 销售放单dao
     */
    private SalesFreeOrderMapper salesFreeOrderMapper;

    private CustomerCapitalMapper customerCapitalMapper;

    public ImportSetMapper getImportSetMapper() {
        return importSetMapper;
    }

    public void setImportSetMapper(ImportSetMapper importSetMapper) {
        this.importSetMapper = importSetMapper;
    }

    public OweOrderMapper getSalesOweOrderMapper() {
		return salesOweOrderMapper;
	}

	public void setSalesOweOrderMapper(OweOrderMapper salesOweOrderMapper) {
		this.salesOweOrderMapper = salesOweOrderMapper;
	}

	public PromotionPackageMapper getSalesPromotionMapper() {
        return salesPromotionMapper;
    }

    public void setSalesPromotionMapper(PromotionPackageMapper salesPromotionMapper) {
        this.salesPromotionMapper = salesPromotionMapper;
    }

    public OffpriceMapper getSalesOffpriceMapper() {
        return salesOffpriceMapper;
    }

    public void setSalesOffpriceMapper(OffpriceMapper salesOffpriceMapper) {
        this.salesOffpriceMapper = salesOffpriceMapper;
    }

    public DispatchBillMapper getSalesDispatchBillMapper() {
        return salesDispatchBillMapper;
    }

    public void setSalesDispatchBillMapper(
            DispatchBillMapper salesDispatchBillMapper) {
        this.salesDispatchBillMapper = salesDispatchBillMapper;
    }

    public OrderMapper getSalesOrderMapper() {
        return salesOrderMapper;
    }

    public void setSalesOrderMapper(OrderMapper salesOrderMapper) {
        this.salesOrderMapper = salesOrderMapper;
    }

    public OrderDetailMapper getSalesOrderDetailMapper() {
        return salesOrderDetailMapper;
    }

    public void setSalesOrderDetailMapper(OrderDetailMapper salesOrderDetailMapper) {
        this.salesOrderDetailMapper = salesOrderDetailMapper;
    }

    public ReceiptMapper getSalesReceiptMapper() {
        return salesReceiptMapper;
    }

    public void setSalesReceiptMapper(ReceiptMapper salesReceiptMapper) {
        this.salesReceiptMapper = salesReceiptMapper;
    }

    public RejectBillMapper getSalesRejectBillMapper() {
        return salesRejectBillMapper;
    }

    public void setSalesRejectBillMapper(RejectBillMapper salesRejectBillMapper) {
        this.salesRejectBillMapper = salesRejectBillMapper;
    }

    public OrderCarMapper getSalesOrderCarMapper() {
        return salesOrderCarMapper;
    }

    public void setSalesOrderCarMapper(OrderCarMapper salesOrderCarMapper) {
        this.salesOrderCarMapper = salesOrderCarMapper;
    }

    public DemandMapper getSalesDemandMapper() {
        return salesDemandMapper;
    }

    public void setSalesDemandMapper(DemandMapper salesDemandMapper) {
        this.salesDemandMapper = salesDemandMapper;
    }

    public SalesFreeOrderMapper getSalesFreeOrderMapper() {
        return salesFreeOrderMapper;
    }

    public void setSalesFreeOrderMapper(SalesFreeOrderMapper salesFreeOrderMapper) {
        this.salesFreeOrderMapper = salesFreeOrderMapper;
    }

    public CustomerCapitalMapper getCustomerCapitalMapper() {
        return customerCapitalMapper;
    }

    public void setCustomerCapitalMapper(CustomerCapitalMapper customerCapitalMapper) {
        this.customerCapitalMapper = customerCapitalMapper;
    }

    public OrderGoodsMapper getOrderGoodsMapper() {
        return orderGoodsMapper;
    }

    public void setOrderGoodsMapper(OrderGoodsMapper orderGoodsMapper) {
        this.orderGoodsMapper = orderGoodsMapper;
    }

    /**
     * 计算单价税额
     *
     * @param type  1为含税单价，2为无税单价
     * @param price 含税单价或无税单价
     * @param rate  税率
     * @return 单价税额
     * @throws Exception
     * @author zhengziyong
     * @date May 28, 2013
     */
    protected BigDecimal computeTax(int type, BigDecimal price, BigDecimal rate) throws Exception {
        BigDecimal result = new BigDecimal(0);
        if (type == 1) {
            BigDecimal noTaxPrice = price.divide(rate.add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP);
            result = price.subtract(noTaxPrice);
        } else if (type == 2) {
            BigDecimal taxPrice = price.multiply(rate.add(new BigDecimal(1)));
            result = taxPrice.subtract(price);
        }
        return result;
    }

    /**
     * 添加销售发货通知单
     *
     * @param bill
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date May 30, 2013
     */
    protected boolean addDispatchBill(DispatchBill bill) throws Exception {
        if (null == bill.getAdduserid() || "".equals(bill.getAdduserid())) {
            SysUser sysUser = getSysUser();
            bill.setAdddeptid(sysUser.getDepartmentid());
            bill.setAdddeptname(sysUser.getDepartmentname());
            bill.setAdduserid(sysUser.getUserid());
            bill.setAddusername(sysUser.getName());
            if (null == bill.getSalesarea() || "".equals(bill.getSalesarea())) {
                Customer customer = getCustomerByID(bill.getCustomerid());
                if (null != customer) {
                    bill.setSalesarea(customer.getSalesarea());
                    bill.setPcustomerid(customer.getPid());
                    bill.setCustomersort(customer.getCustomersort());
                }
            }
        }
        //根据客户编号获取销售内勤用户信息
        SysUser sysUser = getSalesIndoorSysUserByCustomerid(bill.getCustomerid());
        bill.setIndooruserid(sysUser.getPersonnelid());

        List<DispatchBillDetail> billDetaiList = bill.getBillDetailList();
        if (billDetaiList != null) { //添加明细
            List<DispatchBillDetail> brandDiscountList = new ArrayList();
            int seq = 0;
            List detailiDataList = new ArrayList();
            for (DispatchBillDetail billDetail : billDetaiList) {
                billDetail.setBillid(bill.getId());
                GoodsInfo goodsInfo = getAllGoodsInfoByID(billDetail.getGoodsid());
                if (null != goodsInfo) {
                    if (null == billDetail.getCostprice() || billDetail.getCostprice().compareTo(BigDecimal.ZERO) != 0) {
                        billDetail.setCostprice(getGoodsCostprice(bill.getStorageid(),goodsInfo));
                    }
                    //计算辅单位数量
                    Map map = countGoodsInfoNumber(billDetail.getGoodsid(), billDetail.getAuxunitid(), billDetail.getUnitnum());
                    if (map.containsKey("auxnum")) {
                        billDetail.setTotalbox((BigDecimal) map.get("auxnum"));
                    }

                    billDetail.setGoodssort(goodsInfo.getDefaultsort());
                    billDetail.setBrandid(goodsInfo.getBrand());
                    //获取商品供应商
                    billDetail.setSupplierid(goodsInfo.getDefaultsupplier());
                    //是否有品牌 和品牌业务员
                    if (null == billDetail.getBrandid() || "".equals(billDetail.getBrandid())) {
                        billDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), bill.getCustomerid()));
                        //厂家业务员
                        billDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), bill.getCustomerid()));
                        //获取品牌部门
                        Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                        if (null != brand) {
                            billDetail.setBranddept(brand.getDeptid());
                            billDetail.setSupplierid(brand.getSupplierid());
                        }
                    }
                }
                seq++;
                billDetail.setSeq(seq);
                //品牌折扣不直接插入 折算到各商品上后再插入
                if (!"2".equals(billDetail.getIsdiscount())) {
                    detailiDataList.add(billDetail);
                    //getSalesDispatchBillMapper().addDispatchBillDetail(billDetail);
                } else {
                    brandDiscountList.add(billDetail);
                }
            }
            if(detailiDataList.size()>0){
                //明细列表添加
                getSalesDispatchBillMapper().addDispatchBillDetailList(detailiDataList);
                //品牌折扣 平摊到各商品中
                for (DispatchBillDetail billDetail : brandDiscountList) {
                    List<DispatchBillDetail> brandGoodsList = getSalesDispatchBillMapper().getDispatchBillDetailListByBillAndBrandid(bill.getId(), billDetail.getBrandid());
                    BigDecimal totalamount = BigDecimal.ZERO;
                    BigDecimal totalunitnum = BigDecimal.ZERO;
                    BigDecimal totalboxSum = BigDecimal.ZERO;
                    for (DispatchBillDetail billGoodsDetail : brandGoodsList) {
                        totalamount = totalamount.add(billGoodsDetail.getTaxamount());
                        totalunitnum = totalunitnum.add(billGoodsDetail.getUnitnum());
                        totalboxSum = totalboxSum.add(billGoodsDetail.getTotalbox());
                    }
                    if (null != billDetail.getTaxamount() && billDetail.getTaxamount().compareTo(BigDecimal.ZERO) != 0) {
                        BigDecimal useamount = BigDecimal.ZERO;
                        for (int i = 0; i < brandGoodsList.size(); i++) {
                            DispatchBillDetail billGoodsDetail = brandGoodsList.get(i);
                            DispatchBillDetail detail = new DispatchBillDetail();
                            detail.setGoodsid(billGoodsDetail.getGoodsid());
                            detail.setBrandid(billGoodsDetail.getBrandid());
                            GoodsInfo goodsInfo = getAllGoodsInfoByID(billGoodsDetail.getGoodsid());
                            if (null != goodsInfo) {
                                detail.setGoodssort(goodsInfo.getDefaultsort());
                            }
                            detail.setBranddept(billGoodsDetail.getBranddept());
                            detail.setBranduser(billGoodsDetail.getBranduser());
                            Brand brand = getGoodsBrandByID(billGoodsDetail.getBrandid());
                            if (null != brand) {
                                //厂家业务员
                                detail.setSupplieruser(getSupplieruserByCustomeridAndBrand(billGoodsDetail.getBrandid(), bill.getCustomerid()));
                                detail.setSupplierid(brand.getSupplierid());
                            }
                            detail.setBillid(billGoodsDetail.getBillid());
                            detail.setIsdiscount("1");
                            detail.setIsbranddiscount("1");
                            seq++;
                            detail.setSeq(seq);
                            detail.setRemark(billDetail.getRemark());
                            detail.setRepartitiontype(billDetail.getRepartitiontype());
                            //计算平摊到商品中的 各折扣金额
                            BigDecimal discountamount = BigDecimal.ZERO;
                            if("0".equals(billDetail.getRepartitiontype())){//金额
                                discountamount = billGoodsDetail.getTaxamount().divide(totalamount, 6, BigDecimal.ROUND_HALF_UP).multiply(billDetail.getTaxamount());
                            }else if("1".equals(billDetail.getRepartitiontype())){//数量
                                discountamount = billGoodsDetail.getUnitnum().divide(totalunitnum, 6, BigDecimal.ROUND_HALF_UP).multiply(billDetail.getTaxamount());
                            }else if("2".equals(billDetail.getRepartitiontype())){//箱数
                                discountamount = billGoodsDetail.getTotalbox().divide(totalboxSum, 6, BigDecimal.ROUND_HALF_UP).multiply(billDetail.getTaxamount());
                            }
                            if (i == brandGoodsList.size() - 1) {
                                discountamount = billDetail.getTaxamount().subtract(useamount);
                            }
                            //已分配金额
                            useamount = useamount.add(discountamount);
                            BigDecimal notaxdiscountamount = BigDecimal.ZERO;
                            if(StringUtils.isNotEmpty(billDetail.getBrandid())){
                                Brand brand1 = getGoodsBrandByID(billDetail.getBrandid());
                                if(null != brand1){
                                    notaxdiscountamount = getNotaxAmountByTaxAmount(discountamount, brand1.getDefaulttaxtype());
                                }else{
                                    notaxdiscountamount = getNotaxAmountByTaxAmount(discountamount, null);
                                }
                                detail.setTaxamount(discountamount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
                                detail.setNotaxamount(notaxdiscountamount);
                                detail.setTax(detail.getTaxamount().subtract(detail.getNotaxamount()));
                            }

                            detailiDataList.add(billDetail);
                            getSalesDispatchBillMapper().addDispatchBillDetail(detail);
                        }
                    }
                }
            }

        }
        getSalesOrderMapper().updateOrderRefer("1", bill.getBillno()); //更新上游单据的参照状态，1为已经参照下次不能再参照
        return getSalesDispatchBillMapper().addDispatchBill(bill) > 0;
    }

    /**
     * 根据销售发货通知单编号 获取明细列表
     *
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date Sep 3, 2013
     */
    protected List getDispatchBillDetailList(String id) throws Exception {
        List<DispatchBillDetail> billDetaiList = getSalesDispatchBillMapper().getDispatchBillDetailListByBill(id);
        return billDetaiList;
    }

    /**
     * 通过订单编号获取发货通知单信息，前提是发货通知单参照了该订单
     *
     * @param id
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date May 30, 2013
     */
    protected DispatchBill getDispatchBillByOrder(String id) throws Exception {
        return getSalesDispatchBillMapper().getDispatchBillByOrder(id);
    }

    /**
     * 删除销售发货通知单信息
     *
     * @param id
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date May 30, 2013
     */
    protected boolean deleteDispatchBill(String id) throws Exception {
        DispatchBill bill = getSalesDispatchBillMapper().getDispatchBill(id);
        if(null==bill || "3".equals(bill.getStatus()) || "4".equals(bill.getStatus())){
            return false;
        }
        getSalesDispatchBillMapper().deleteDispatchBillDetailByBill(id); //删除明细
        getSalesOrderMapper().updateOrderRefer("0", bill.getBillno()); //更新上游单据参照状态，0为未参照
        return getSalesDispatchBillMapper().deleteDispatchBill(id) > 0;
    }

    /**
     * 添加发货回单
     *
     * @param receipt
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Jun 6, 2013
     */
    public boolean addReceipt(Receipt receipt) throws Exception {
        Receipt oldReceipt = getSalesReceiptMapper().getReceiptBySaleorderid(receipt.getSaleorderid());
        boolean flag = false;
        if (null == oldReceipt) {
            if (isAutoCreate("t_sales_receipt")) {
                String id = getAutoCreateSysNumbderForeign(receipt, "t_sales_receipt"); // 获取自动编号
                receipt.setId(id);
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
                Random random = new Random();
                String rand = random.nextInt(99999999) + "";
                String id = dateFormat.format(new Date()) + rand;
                receipt.setId(id);
            }
            //根据客户编号获取默认销售内勤用户信息
            SysUser sysUser = getSalesIndoorSysUserByCustomerid(receipt.getCustomerid());
            receipt.setIndooruserid(sysUser.getPersonnelid());
            //获取销售区域
            if (null == receipt.getSalesarea() || "".equals(receipt.getSalesarea())) {
                Customer customer = getCustomerByID(receipt.getCustomerid());
                if (null != customer) {
                    receipt.setSalesarea(customer.getSalesarea());
                    receipt.setPcustomerid(customer.getPid());
                    receipt.setCustomersort(customer.getCustomersort());
                }
            }
        } else {
            oldReceipt.setBillno(oldReceipt.getBillno() + "," + receipt.getBillno());
            receipt.setId(oldReceipt.getId());
            oldReceipt.setBusinessdate(getAuditBusinessdate(receipt.getBusinessdate()));
        }
        List<ReceiptDetail> receiptDetailList = receipt.getReceiptDetailList();
        if (receiptDetailList.size() > 0) {
            List detailAddList = new ArrayList();
            for (ReceiptDetail receiptDetail : receiptDetailList) {
                if (receiptDetail != null) {
                    receiptDetail.setBillid(receipt.getId());
                    //判断成本价是否存在
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(receiptDetail.getGoodsid());
                    if (null != goodsInfo) {
                        if (null == receiptDetail.getCostprice()) {
                            receiptDetail.setCostprice(getGoodsCostprice(null,goodsInfo));
                        } else if (receiptDetail.getCostprice().compareTo(BigDecimal.ZERO) == 0) {
                            receiptDetail.setCostprice(getGoodsCostprice(null,goodsInfo));
                        }
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
                        }
                        receiptDetail.setSupplierid(goodsInfo.getDefaultsupplier());
                    }
                    detailAddList.add(receiptDetail);
                    //getSalesReceiptMapper().addReceiptDetail(receiptDetail);
                }
            }
            if (detailAddList.size() > 0) {
                getSalesReceiptMapper().addReceiptDetailList(detailAddList);
            }
        }
        if (null == oldReceipt) {
            //根据来源单据发货单判断配送状态0未配送1配送中2已配送
            String isdelivery = getIsDeliveryByReceiptSourceids(receipt);
            receipt.setIsdelivery(isdelivery);
            int i = getSalesReceiptMapper().addReceipt(receipt);
            flag = i > 0;
        } else {
            //根据来源单据发货单判断配送状态0未配送1配送中2已配送
            String isdelivery = getIsDeliveryByReceiptSourceids(oldReceipt);
            oldReceipt.setIsdelivery(isdelivery);
            int i = getSalesReceiptMapper().updateReceipt(oldReceipt);
            flag = i > 0;
        }
        if(flag){
            List<ReceiptDetail> receiptList = getSalesReceiptMapper().getReceiptDetailListByReceipt(receipt.getId());
            int invoicenum = 0;
            for(ReceiptDetail receiptDetail : receiptList){
                if("1".equals(receiptDetail.getIsinvoicebill())){
                    invoicenum++;
                }
            }
            //更新回单发货状态
            if(invoicenum==receiptList.size()){
                getSalesReceiptMapper().updateReceiptInvoicebill("1", null, receipt.getId());
            }else if(invoicenum>0 && invoicenum<receiptList.size()){
                getSalesReceiptMapper().updateReceiptInvoicebill("4", null, receipt.getId());
            }else if(invoicenum==0){
                getSalesReceiptMapper().updateReceiptInvoicebill("3", null, receipt.getId());
            }
        }
        return flag;
    }

    /**
     * 删除发货回单
     *
     * @param id
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Jun 6, 2013
     */
    public boolean deleteReceipt(String id) throws Exception {
        Map map = new HashMap();
        map.put("id", id);
        Receipt oldReceipt = getSalesReceiptMapper().getReceipt(map);
        if(null==oldReceipt || "3".equals(oldReceipt.getStatus()) || "4".equals(oldReceipt.getStatus())){
            return false;
        }
        getSalesReceiptMapper().deleteReceiptDetailByReceiptId(id);
        return getSalesReceiptMapper().deleteReceipt(id) > 0;
    }

    /**
     * 关闭销售发货回单
     *
     * @throws Exception
     * @author zhengziyong
     * @date May 21, 2013
     */
    protected boolean updateReceiptClose(String id) throws Exception {
        Receipt receipt = new Receipt();
        receipt.setId(id);
        receipt.setClosetime(new Date());
        receipt.setStatus("4");
        return salesReceiptMapper.updateReceiptStatus(receipt) > 0;
    }

    /**
     * 反核销 回写销售发货回单状态为审核通过
     *
     * @return
     * @author panxiaoxiao
     * @date Aug 4, 2014
     */
    protected boolean backReceiptClose(String id) throws Exception {
        Receipt receipt = new Receipt();
        receipt.setId(id);
        receipt.setClosetime(null);
        receipt.setStatus("3");
        return salesReceiptMapper.updateReceiptStatus(receipt) > 0;
    }

    /**
     * 反核销 回写销售退货通知单状态为审核通过
     *
     * @param id
     * @return
     * @author panxiaoxiao
     * @date Aug 4, 2014
     */
    protected boolean backRejectBillClose(String id) {
        RejectBill rejectBill = new RejectBill();
        rejectBill.setId(id);
        rejectBill.setClosetime(null);
        rejectBill.setStatus("3");
        return getSalesRejectBillMapper().updateRejectBillStatusWithNoVersion(rejectBill) > 0;
    }

    /**
     * 添加退货通知单
     *
     * @param bill
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Jun 6, 2013
     */
    protected boolean addRejectBill(RejectBill bill) throws Exception {
        //是否残次 残次取残次仓库
        if ("1".equals(bill.getIsincomplete())) {
            String storageid = getSysParamValue("INCOMPLETESTORAGE");
            bill.setStorageid(storageid);
        }
        SysUser sysUser = getSysUser();
        if (sysUser != null) {
            bill.setAdddeptid(sysUser.getDepartmentid());
            bill.setAdddeptname(sysUser.getDepartmentname());
            bill.setAdduserid(sysUser.getUserid());
            bill.setAddusername(sysUser.getName());
        }
        //根据客户编号获取默认销售内勤用户信息
        SysUser indoorsysUser = getSalesIndoorSysUserByCustomerid(bill.getCustomerid());
        if (indoorsysUser != null) {
            bill.setIndooruserid(indoorsysUser.getPersonnelid());
        }
        Customer customer = getCustomerByID(bill.getCustomerid());
        if (null != customer) {
            bill.setSalesarea(customer.getSalesarea());
            bill.setPcustomerid(customer.getPid());
            bill.setCustomersort(customer.getCustomersort());
        }
        List<RejectBillDetail> billDetailList = bill.getBillDetailList();
        if (billDetailList != null) {
            for (RejectBillDetail billDetail : billDetailList) {
                if (billDetail != null && StringUtils.isNotEmpty(billDetail.getGoodsid())) {
                    //计算辅单位数量
                    Map auxmap = countGoodsInfoNumber(billDetail.getGoodsid(), billDetail.getAuxunitid(), billDetail.getUnitnum());
                    if (auxmap.containsKey("auxnum")) {
                        billDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
                    }
                    billDetail.setBillid(bill.getId());
                    if (null == billDetail.getCostprice() || billDetail.getCostprice().compareTo(BigDecimal.ZERO) == 0) {
                        GoodsInfo goodsInfo = getAllGoodsInfoByID(billDetail.getGoodsid());
                        if (null != goodsInfo) {
                            //成本价
                            billDetail.setCostprice(getGoodsCostprice(bill.getStorageid(),goodsInfo));
                            billDetail.setGoodssort(goodsInfo.getDefaultsort());
                            billDetail.setBrandid(goodsInfo.getBrand());
                            billDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), bill.getCustomerid()));
                            //厂家业务员
                            billDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), bill.getCustomerid()));
                            //品牌部门
                            Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                            if (null != brand) {
                                billDetail.setBranddept(brand.getDeptid());
                            }
                            billDetail.setSupplierid(goodsInfo.getDefaultsupplier());
                            //当单据中仓库编号不为空时，判断明细的仓库编号是否一致，不一致时设为空
                            if(StringUtils.isNotEmpty(bill.getStorageid()) && StringUtils.isNotEmpty(billDetail.getSummarybatchid())){
                                if(!bill.getStorageid().equals(billDetail.getStorageid())){
                                    billDetail.setSummarybatchid("");
                                    billDetail.setStorageid("");
                                }
                            }
                        }
                    }
                    getSalesRejectBillMapper().addRejectBillDetail(billDetail);
                }
            }
        }
        getSalesReceiptMapper().updateReceiptRefer("1", bill.getBillno());//更新上游单据的参照状态，1为已经参照下次不能再参照
        bill.setKeyid(bill.getId());
        return getSalesRejectBillMapper().addRejectBill(bill) > 0;
    }

    /**
     * 删除退货通知单
     *
     * @param id
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Jun 7, 2013
     */
    protected boolean deleteRejectBill(String id) throws Exception {
        Map map = new HashMap();
        map.put("id", id);
        RejectBill rejectBill = getSalesRejectBillMapper().getRejectBill(map);
        if (null != rejectBill && ("1".equals(rejectBill.getStatus()) || "2".equals(rejectBill.getStatus()))) {
            getSalesRejectBillMapper().deleteRejectBillDetailByBill(id);//删除明细
            getSalesReceiptMapper().updateReceiptRefer("0", rejectBill.getBillno());//更新上游单据参照状态，0为未参照
            return getSalesRejectBillMapper().deleteRejectBill(id) > 0;
        } else {
            return false;
        }
    }

    /**
     * 回写客户档案
     *
     * @return
     * @author zhengziyong
     * @date Jul 8, 2013
     */
    public boolean updateCustomerBack(List<OrderDetail> orderDetailList) throws Exception {
        String orderId = "";
        if (orderDetailList != null && orderDetailList.size() > 0) {
            orderId = orderDetailList.get(0).getOrderid();
        }
        ISalesExtService salesExtService = (ISalesExtService) SpringContextUtils.getBean("salesExtService");
        Customer backInfo = salesExtService.getCustomerBack(orderId);
        Customer customer = new Customer();
        if (backInfo != null) {
            BigDecimal allSaleSum = new BigDecimal(0);
            for (OrderDetail orderDetail : orderDetailList) {
                allSaleSum.add(orderDetail.getSendamounttax());
            }
            customer.setAllsalessum(backInfo.getAllsalessum().add(allSaleSum));
            return salesExtService.updateCustomerBack(customer);
        }
        return false;
    }

    /**
     * 判断客户是否存在超账期应收款
     *
     * @throws Exception
     * @return true存在 false不存在
     * @author chenwei
     * @date Oct 25, 2013
     */
    public boolean isReceivablePassDateByCustomerid(String customerid) throws Exception {
        String date = CommonUtils.getTodayDataStr();
        Customer customer = getCustomerByID(customerid);
        //判断该客户当天是否允许放单
        int freeorderCount = salesFreeOrderMapper.getSalesFreeOrderCountByCustomeridAndDate(customerid, date);
        if (freeorderCount > 0) {
            return false;
        } else {
            //判断客户是否超账期控制
            if (null != customer && "1".equals(customer.getOvercontrol())) {
                BigDecimal passDateAmount = salesReceiptMapper.getReceivablePassDateAmountByCustomerid(customerid, date,null);
                if (null != passDateAmount && passDateAmount.compareTo(BigDecimal.ZERO) == 1) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }
    public Map isReceivablePassDateByCustomeridAndDemand (String customerid,String demandid)throws Exception{
        Customer customer = getCustomerByID(customerid);
        //超账期判断 1按客户整体来判断 2按该订单中的品牌判断
        String orderPassdateTypeControl = getSysParamValue("OrderPassdateTypeControl");
        if(StringUtils.isEmpty(orderPassdateTypeControl)){
            orderPassdateTypeControl = "1";
        }
        boolean passDateflag = true;
        String msg = "";
        String date = CommonUtils.getTodayDataStr();
        //判断客户是否现结
        Settlement settlement = getSettlementByID(customer.getSettletype());
        boolean settleFlag = false ;
        if (null != settlement && "3".equals(settlement.getType())) {
            settleFlag = true ;
        }
        List<String> bList = null;
        //判断客户是否超账期控制 现结客户不受该参数控制
        if (null != customer && "1".equals(customer.getOvercontrol()) && !settleFlag) {
            if("2".equals(orderPassdateTypeControl) && StringUtils.isNotEmpty(demandid)){
                bList = salesDemandMapper.getBrandListInDemand(demandid);
                if(bList.size()==0){
                    bList = null;
                }
            }
            BigDecimal passDateAmount = salesReceiptMapper.getReceivablePassDateAmountByCustomerid(customer.getId(), date,bList);
            if (null != passDateAmount && passDateAmount.compareTo(BigDecimal.ZERO) == 1) {
                passDateflag = false;
            } else {
                passDateflag = true;
            }
        } else {
            passDateflag = true;
        }
        if (!passDateflag) {
            msg = "客户："+customer.getId()+","+customer.getName()+",应收款超账期；";
            if("2".equals(orderPassdateTypeControl) && StringUtils.isNotEmpty(demandid)){
                List<Map> bPassList = salesReceiptMapper.getReceivablePassDateBrandListByCustomerid(customer.getId(), date,bList);
                if(null!=bPassList && bPassList.size()>0){
                    msg += "品牌：";
                    for(Map map : bPassList){
                        String brandid = (String) map.get("brandid");
                        BigDecimal amount = (BigDecimal) map.get("amount");
                        Brand brand = getGoodsBrandByID(brandid);
                        if(null!=brand){
                            msg += brand.getName()+",超账:"+amount.setScale(2,BigDecimal.ROUND_HALF_UP).toString()+",";
                        }
                    }
                }
            }
        }
        Map map = new HashedMap();
        map.put("flag",!passDateflag);
        map.put("msg",msg);
        return map;
    }
    /**
     * 根据客户编号 判断客户应收款是否在信用额度 信用期限内
     * 信用期限为空时 信用额度一直有效
     * 应收费 大于信用额度时 返回false
     * 应收款 小于等于信用额度时 返回true
     *
     * @param customerid
     * @return
     * @throws Exception
     * @author chenwei
     * @date Oct 25, 2013
     */
    public boolean isReceivableInCredit(String customerid) throws Exception {
        Customer customer = getCustomerByID(customerid);
        boolean flag = false;
        //判断该客户当天是否允许放单
        int freeorderCount = salesFreeOrderMapper.getSalesFreeOrderCountByCustomeridAndDate(customerid, CommonUtils.getTodayDataStr());
        if (freeorderCount > 0) {
            return true;
        }
        //应收金额
        BigDecimal receivableAmount = salesReceiptMapper.getReceivableAmountByCustomerid(customer.getId());
        if (null == receivableAmount) {
            receivableAmount = BigDecimal.ZERO;
        }
        //订单未出库金额
        BigDecimal orderUnOutAmount = salesOrderMapper.getCustomerOrderAmount(customer.getId());
        if (null != orderUnOutAmount) {
            receivableAmount = receivableAmount.add(orderUnOutAmount);
        }
        //客户超长期控制 客户历史应收款总额大于0时
        if (null != customer && null != receivableAmount && receivableAmount.compareTo(BigDecimal.ZERO) == 1) {
            BigDecimal credit = customer.getCredit();
            //客户未定义信用额度时， 返回true
            if (null == credit || credit.compareTo(BigDecimal.ZERO) == 0) {
                flag = true;
            } else if (credit.compareTo(receivableAmount) != -1) {        //信用额度大于等于应收款总额时 返回true
                flag = true;
            }
        } else {
            flag = true;
        }
        return flag;
    }

    /**
     * 判断订单是否可以审核
     *
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年10月9日
     */
    public Map isOrderCanAudit(String id) throws Exception {
        Map returnMap = new HashMap();
        boolean flag = false;
        String msg = "";
        Map querymap = new HashMap();
        querymap.put("id", id);
        Order order = getSalesOrderMapper().getOrderDetail(querymap);
        Customer customer = getCustomerByID(order.getCustomerid());
        if (null != order && null != customer) {
            String date = CommonUtils.getTodayDataStr();
            //判断该客户当天是否允许放单
            int freeorderCount = salesFreeOrderMapper.getSalesFreeOrderCountByCustomeridAndDate(customer.getId(), date);
            if (freeorderCount > 0) {
                msg = "客户：" + customer.getId() + "," + customer.getName() + "放单。";
                flag = true;
            } else {
                //判断客户是否现结
                Settlement settlement = getSettlementByID(customer.getSettletype());
                boolean settleFlag = false ;
                if (null != settlement && "3".equals(settlement.getType())) {
                    settleFlag = true ;
                }
                //信用额度判断
                boolean creditflag = true;
                //超账期判断
                boolean passDateflag = true;
                //0金额判断
                boolean zeroFlag = true;
                //现金客户 是否有余额下单（结算方式为日结0天的客户）
                boolean cashFlag = true;
                //应收金额
                BigDecimal receivableAmount = BigDecimal.ZERO;
                //订单未出库金额
                BigDecimal orderUnOutAmount = BigDecimal.ZERO;
                //订单金额
                BigDecimal thisOrderAmount = salesOrderMapper.getOrderAmount(id);
                if(null == thisOrderAmount){
                    thisOrderAmount = BigDecimal.ZERO;
                }
                //已用额度
                BigDecimal useCreditAmount = BigDecimal.ZERO;
                BigDecimal pcreditamount = BigDecimal.ZERO;
                BigDecimal creditamount = BigDecimal.ZERO;
                BigDecimal comCreditamount = BigDecimal.ZERO;
                //客户余额
                BigDecimal leftAmount = BigDecimal.ZERO;
                //负尾差金额
                String negateTailAmountLimit = getSysParamValue("negateTailAmountLimit");
                //总店客户
                Customer pcustomer = null;
                //总店结算方式
                Settlement psettlement = null;
                //总店是否现结
                boolean psettleFlag = false ;
                if(StringUtils.isNotEmpty(customer.getPid())){
                    pcustomer = getCustomerByID(customer.getPid());
                    if(null!=pcustomer){
                        psettlement = getSettlementByID(pcustomer.getSettletype());
                        if (null != psettlement && "3".equals(psettlement.getType())) {
                            psettleFlag = true ;
                        }
                    }
                    if(null!=pcustomer && null!=pcustomer.getCredit() && pcustomer.getCredit().compareTo(BigDecimal.ZERO)==1){
                        pcreditamount = pcustomer.getCredit();
                        comCreditamount = pcustomer.getCredit();
                    }
                }
                if(pcreditamount.compareTo(BigDecimal.ZERO)==0){
                    if (null != customer.getCredit()) {
                        creditamount = customer.getCredit().setScale(2, BigDecimal.ROUND_HALF_UP);
                        comCreditamount = customer.getCredit();
                    }
                }
                 //现结客户 是否控制下单
                String isCashOrderAudit = getSysParamValue("isCashOrderAudit");
                //有总店的客户 信用额度判断取总店的数据 总店未设置信用额度或者信用额度为0的时候 取门店的额度
                //有总店的客户且总店信用额度不为0的 现结控制下单不控制
                //总店结算方式为现结时，门店取总店的余额判断
                if(StringUtils.isNotEmpty(customer.getPid()) && (pcreditamount.compareTo(BigDecimal.ZERO)==1 || psettleFlag)){
                    //应收金额
                    receivableAmount = salesReceiptMapper.getReceivableAmountByPCustomerid(customer.getPid());
                    if (null == receivableAmount) {
                        receivableAmount = BigDecimal.ZERO;
                    }
                    receivableAmount = receivableAmount.add(thisOrderAmount);
                    //判断销售订单审核时， 是否自动生成发货单
                    //1 先生成销售发货通知单 0 自动审核销售发货通知单并且生成发货单
                    //自动生成发货单时，统计订单未出库金额  取销售订单状态为3的即可
                    //生成销售发货通知单  统计订单状态为3的和销售发货通知单为3的状态的金额
                    String sysparam = getSysParamValue("IsDispatchProcessUse");
                    if("0".equals(sysparam)){
                        //订单未出库金额
                        orderUnOutAmount = salesOrderMapper.getCustomerOrderAmountByPcustomerid(customer.getPid());
                    }else{
                        orderUnOutAmount = salesOrderMapper.getCustomerOrderAndDispatchAmountByPcustomerid(customer.getPid());
                    }
                    if (null != orderUnOutAmount) {
                        receivableAmount = receivableAmount.add(orderUnOutAmount);
                    }
                    CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(customer.getId());
                    if(null!=customerCapital){
                        leftAmount = customerCapital.getAmount();
                    }
                    //判断客户是否有总店， 有总店的情况下 客户余额需要加上总店的余额
                    if(null!=customer && StringUtils.isNotEmpty(customer.getPid())){
                        CustomerCapital pcustomerCapital = customerCapitalMapper.getCustomerCapital(customer.getPid());
                        if(null!=pcustomerCapital){
                            leftAmount = leftAmount.add(pcustomerCapital.getAmount());
                        }
                    }
                }else{
                    //应收金额
                    receivableAmount = salesReceiptMapper.getReceivableAmountByCustomerid(customer.getId());
                    if (null == receivableAmount) {
                        receivableAmount = BigDecimal.ZERO;
                    }
                    receivableAmount = receivableAmount.add(thisOrderAmount);
                    //判断销售订单审核时， 是否自动生成发货单
                    //1 先生成销售发货通知单 0 自动审核销售发货通知单并且生成发货单
                    //自动生成发货单时，统计订单未出库金额  取销售订单状态为3的即可
                    //生成销售发货通知单  统计订单状态为3的和销售发货通知单为3的状态的金额
                    String sysparam = getSysParamValue("IsDispatchProcessUse");
                    if("0".equals(sysparam)){
                        //订单未出库金额
                        orderUnOutAmount = salesOrderMapper.getCustomerOrderAmount(customer.getId());
                    }else{
                        orderUnOutAmount = salesOrderMapper.getCustomerOrderAndDispatchAmount(customer.getId());
                    }
                    if (null != orderUnOutAmount) {
                        receivableAmount = receivableAmount.add(orderUnOutAmount);
                    }
                    CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(customer.getId());
                    if(null!=customerCapital){
                        leftAmount = customerCapital.getAmount();
                    }
                }

                if ("1".equals(isCashOrderAudit)) {
                    //结算方式为现结的客户
                    if (settleFlag) {
                        //客户欠款要小于等于客户账户余额才行
                        // 应收金额+允许尾差金额
                        if (StringUtils.isNotEmpty(negateTailAmountLimit)) {
                            receivableAmount = receivableAmount.add(new BigDecimal(negateTailAmountLimit));
                        }
                        if (receivableAmount.compareTo(leftAmount) != 1) {
                            cashFlag = true;
                        } else {
                            cashFlag = false;
                        }
                    }
                }
                //已用额度
                useCreditAmount = receivableAmount;
                //客户信用额度控制 客户应收款总额大于0时 现结客户不受该参数控制
                if (null != receivableAmount && receivableAmount.compareTo(BigDecimal.ZERO) == 1  && !settleFlag) {
                    //客户未定义信用额度时， 返回true
                    if (null == comCreditamount || comCreditamount.compareTo(BigDecimal.ZERO) == 0) {
                        creditflag = true;
                    } else if (comCreditamount.compareTo(receivableAmount) != -1) {        //信用额度大于等于应收款总额时 返回true
                        creditflag = true;
                    } else {
//                        //判断应收金额+允许尾差金额，信用额度是否大于等于应收款总额，若是，返回true,否则返回false
//                        if (StringUtils.isNotEmpty(negateTailAmountLimit)) {
//                            receivableAmount = receivableAmount.add(new BigDecimal(negateTailAmountLimit));
//                        }
//                        if(comCreditamount.compareTo(receivableAmount) != -1){
//                            creditflag = true;
//                        }else{
//                            creditflag = false;
//                        }
                        creditflag = false;
                    }
                }
                //超账期判断 1按客户整体来判断 2按该订单中的品牌判断
                String orderPassdateTypeControl = getSysParamValue("OrderPassdateTypeControl");
                if(StringUtils.isEmpty(orderPassdateTypeControl)){
                    orderPassdateTypeControl = "1";
                }
                List<String> bList = null;
                //判断客户是否超账期控制 现结客户不受该参数控制
                if (null != customer && "1".equals(customer.getOvercontrol()) && !settleFlag) {
                    if("2".equals(orderPassdateTypeControl)){
                        bList = salesOrderMapper.getBrandListInOrder(id);
                        if(bList.size()==0){
                            bList = null;
                        }
                    }
                    BigDecimal passDateAmount = salesReceiptMapper.getReceivablePassDateAmountByCustomerid(customer.getId(), date,bList);
                    if (null != passDateAmount && passDateAmount.compareTo(BigDecimal.ZERO) == 1) {
                        passDateflag = false;
                    } else {
                        passDateflag = true;
                    }
                } else {
                    passDateflag = true;
                }
                //0金额判断
                String isZeroOrderAudit = getSysParamValue("IsZeroOrderAudit");
                if ("1".equals(isZeroOrderAudit)) {
                    int zeroCount = getSalesOrderDetailMapper().getOrderDetailZeroCount(id);
                    if (zeroCount > 0) {
                        zeroFlag = false;
                    }
                }
                //组装信息说明
                msg = "订单" + id + ":" + "客户：" + customer.getId() + "," + customer.getName() + ",";
                if (!creditflag) {
                    if(StringUtils.isNotEmpty(customer.getPid()) && pcreditamount.compareTo(BigDecimal.ZERO)==1){
                        pcreditamount = pcreditamount.setScale(2,BigDecimal.ROUND_HALF_UP);
                        msg += "总店应收款超过信用额度!信用额度:" + pcreditamount + "；已用额度：" + useCreditAmount.setScale(2, BigDecimal.ROUND_HALF_UP)+"；";
                    }else{
                        if (null != customer.getCredit()) {
                            creditamount = customer.getCredit().setScale(2, BigDecimal.ROUND_HALF_UP);
                        }
                        msg += "应收款超过信用额度!信用额度:" + creditamount + "；已用额度：" + useCreditAmount.setScale(2, BigDecimal.ROUND_HALF_UP)+"；";
                    }
                }
                if (!passDateflag) {
                    msg += "应收款超账期；";
                    if("2".equals(orderPassdateTypeControl)){
                        List<Map> bPassList = salesReceiptMapper.getReceivablePassDateBrandListByCustomerid(customer.getId(), date,bList);
                        if(null!=bPassList && bPassList.size()>0){
                            msg += "品牌：";
                            for(Map map : bPassList){
                                String brandid = (String) map.get("brandid");
                                BigDecimal amount = (BigDecimal) map.get("amount");
                                Brand brand = getGoodsBrandByID(brandid);
                                if(null!=brand){
                                    msg += brand.getName()+",超账:"+amount.setScale(2,BigDecimal.ROUND_HALF_UP).toString()+",";
                                }
                            }
                        }
                    }
                }
                if (!zeroFlag) {
                    msg += "有0金额商品；";
                }
                if (!cashFlag) {
                    if(leftAmount.compareTo(BigDecimal.ZERO)==1){
                        if(psettleFlag){
                             msg += "客户结算方式为现结，总店账户余额:"+leftAmount.setScale(2, BigDecimal.ROUND_HALF_UP)+"；已用余额："+receivableAmount.setScale(2, BigDecimal.ROUND_HALF_UP)
                                +"，余额不足下单；";
                        }else{
                             msg += "客户结算方式为现结，账户余额:"+leftAmount.setScale(2, BigDecimal.ROUND_HALF_UP)+"；已用余额："+receivableAmount.setScale(2, BigDecimal.ROUND_HALF_UP)
                                +"，余额不足下单；";
                        }

                    }else {
                        msg += "客户结算方式为现结，账户余额:0；余额不足下单；";
                    }

                }
                //判断是否可以审核
                if (!creditflag || !passDateflag || !zeroFlag || !cashFlag) {
                    flag = false;
                } else {
                    flag = true;
                }
            }
        } else {
            flag = false;
            msg += "客户或者订单不存在；";
            flag = false;
        }
        returnMap.put("flag", flag);
        returnMap.put("msg", msg);
        return returnMap;
    }

    public boolean addOrderForPhone(Order order) throws Exception {
        Map map = new HashMap();
        map.put("id", order.getId());
        Order order2 = getSalesOrderMapper().getOrderDetail(map);
        if (order2 != null && StringUtils.isNotEmpty(order2.getId())) {
            return true;
        }
        List<OrderDetail> orderDetailList = order.getOrderDetailList();
        if (orderDetailList.size() > 0) {
            List detailList = new ArrayList();
            for (OrderDetail orderDetail : orderDetailList) {
                if (orderDetail != null) {
                    orderDetail.setOrderid(order.getId());
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(orderDetail.getGoodsid());
                    if (null != goodsInfo) {
                        orderDetail.setGoodssort(goodsInfo.getDefaultsort());
                        orderDetail.setBrandid(goodsInfo.getBrand());
                        orderDetail.setCostprice(getGoodsCostprice(order.getStorageid(),goodsInfo));
                        orderDetail.setFixnum(orderDetail.getUnitnum());
                        //获取品牌部门
                        Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                        if (null != brand) {
                            orderDetail.setBranddept(brand.getDeptid());
                        }
                        //根据客户编号和品牌编号 获取品牌业务员
                        orderDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), order.getCustomerid()));
                        //厂家业务员
                        orderDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), order.getCustomerid()));
                        orderDetail.setSupplierid(goodsInfo.getDefaultsupplier());
                    }
                    detailList.add(orderDetail);
                    //addOrderDetail(orderDetail);
                }
            }
            //添加明细
            getSalesOrderDetailMapper().addOrderDetailList(detailList);
        }
        //根据客户编号获取该客户的销售内勤用户信息
        SysUser sysUser = getSalesIndoorSysUserByCustomerid(order.getCustomerid());
        if (sysUser != null) {
            //销售内勤
            order.setIndooruserid(sysUser.getPersonnelid());
        }
        //获取销售区域上级客户
        Customer customer = getCustomerByID(order.getCustomerid());
        if (null != customer) {
            order.setSalesarea(customer.getSalesarea());
            order.setPcustomerid(customer.getPid());
            order.setCustomersort(customer.getCustomersort());
        }
        return getSalesOrderMapper().addOrder(order) > 0;
    }
    
    /**
     * 客户应收款、余额<br/>
     * 返回结果<br/>
     * receivableAmount:客户应收款<br/>
     * leftAmount : 余额<br/>
     * @param customerid
     * @return
     * @author zhanghonghui
     * @date 2015年11月12日
     */
    public Map showCustomerReceivableInfoData(String customerid) throws Exception {
        if(null==customerid || "".equals(customerid.trim())){
            return null;
        }

        Map resultMap=new HashMap();

        //应收金额
        BigDecimal receivableAmount = salesReceiptMapper.getReceivableAmountByCustomerid(customerid);
        if (null == receivableAmount) {
            receivableAmount = BigDecimal.ZERO;
        }

        resultMap.put("receivableAmount", receivableAmount);
        //客户资金情况（余额）
        CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(customerid);

        BigDecimal leftAmount=null;
        if(null!=customerCapital){
            leftAmount=customerCapital.getAmount();
        }
        if(null==leftAmount){
            leftAmount=BigDecimal.ZERO;
        }
        Customer customer = getCustomerByID(customerid);
        //判断客户是否有总店， 有总店的情况下 客户余额需要加上总店的余额
        if(null!=customer && StringUtils.isNotEmpty(customer.getPid())){
            CustomerCapital pcustomerCapital = customerCapitalMapper.getCustomerCapital(customer.getPid());
            if(null!=pcustomerCapital){
                leftAmount = leftAmount.add(pcustomerCapital.getAmount());
            }
        }
        resultMap.put("leftAmount", leftAmount);

        return resultMap;
    }

    /**
     * 判断订单是否可以审核
     *
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年10月9日
     */
    public Map isOrderGoodsCanAudit(String id) throws Exception {
        Map returnMap = new HashMap();
        boolean flag = false;
        String msg = "";
        Map querymap = new HashMap();
        querymap.put("id", id);
        OrderGoods order = getOrderGoodsMapper().getOrderGoods(id);
        Customer customer = getCustomerByID(order.getCustomerid());
        if (null != order && null != customer) {
            String date = CommonUtils.getTodayDataStr();
            //判断该客户当天是否允许放单
            int freeorderCount = salesFreeOrderMapper.getSalesFreeOrderCountByCustomeridAndDate(customer.getId(), date);
            if (freeorderCount > 0) {
                msg = "客户：" + customer.getId() + "," + customer.getName() + "放单。";
                flag = true;
            } else {
                //判断客户是否现结
                Settlement settlement = getSettlementByID(customer.getSettletype());
                boolean settleFlag = false ;
                if (null != settlement && "3".equals(settlement.getType())) {
                    settleFlag = true ;
                }
                //信用额度判断
                boolean creditflag = true;
                //超账期判断
                boolean passDateflag = true;
                //0金额判断
                boolean zeroFlag = true;
                //现金客户 是否有余额下单（结算方式为日结0天的客户）
                boolean cashFlag = true;
                //应收金额
                BigDecimal receivableAmount = BigDecimal.ZERO;
                //订单未出库金额
                BigDecimal orderUnOutAmount = BigDecimal.ZERO;
                //订单金额
                BigDecimal thisOrderAmount = salesOrderMapper.getOrderAmount(id);
                if(null == thisOrderAmount){
                    thisOrderAmount = BigDecimal.ZERO;
                }
                //已用额度
                BigDecimal useCreditAmount = BigDecimal.ZERO;
                BigDecimal pcreditamount = BigDecimal.ZERO;
                BigDecimal creditamount = BigDecimal.ZERO;
                BigDecimal comCreditamount = BigDecimal.ZERO;
                //负尾差金额
                String negateTailAmountLimit = getSysParamValue("negateTailAmountLimit");
                if(StringUtils.isNotEmpty(customer.getPid())){
                    Customer pcustomer = getCustomerByID(customer.getPid());
                    if(null!=pcustomer && null!=pcustomer.getCredit() && pcustomer.getCredit().compareTo(BigDecimal.ZERO)==1){
                        pcreditamount = pcustomer.getCredit();
                        comCreditamount = pcustomer.getCredit();
                    }
                }
                if(pcreditamount.compareTo(BigDecimal.ZERO)==0){
                    if (null != customer.getCredit()) {
                        creditamount = customer.getCredit().setScale(2, BigDecimal.ROUND_HALF_UP);
                        comCreditamount = customer.getCredit();
                    }
                }
                //有总店的客户 信用额度判断取总店的数据 总店未设置信用额度或者信用额度为0的时候 取门店的额度
                //有总店的客户且总店信用额度不为0的 现结控制下单不控制
                if(StringUtils.isNotEmpty(customer.getPid()) && pcreditamount.compareTo(BigDecimal.ZERO)==1){
                    //应收金额
                    receivableAmount = salesReceiptMapper.getReceivableAmountByPCustomerid(customer.getPid());
                    if (null == receivableAmount) {
                        receivableAmount = BigDecimal.ZERO;
                    }
                    receivableAmount = receivableAmount.add(thisOrderAmount);
                    //判断销售订单审核时， 是否自动生成发货单
                    //1 先生成销售发货通知单 0 自动审核销售发货通知单并且生成发货单
                    //自动生成发货单时，统计订单未出库金额  取销售订单状态为3的即可
                    //生成销售发货通知单  统计订单状态为3的和销售发货通知单为3的状态的金额
                    String sysparam = getSysParamValue("IsDispatchProcessUse");
                    if("0".equals(sysparam)){
                        //订单未出库金额
                        orderUnOutAmount = salesOrderMapper.getCustomerOrderAmountByPcustomerid(customer.getPid());
                    }else{
                        orderUnOutAmount = salesOrderMapper.getCustomerOrderAndDispatchAmountByPcustomerid(customer.getPid());
                    }
                    if (null != orderUnOutAmount) {
                        receivableAmount = receivableAmount.add(orderUnOutAmount);
                    }
                }else{
                    //应收金额
                    receivableAmount = salesReceiptMapper.getReceivableAmountByCustomerid(customer.getId());
                    if (null == receivableAmount) {
                        receivableAmount = BigDecimal.ZERO;
                    }
                    receivableAmount = receivableAmount.add(thisOrderAmount);
                    //判断销售订单审核时， 是否自动生成发货单
                    //1 先生成销售发货通知单 0 自动审核销售发货通知单并且生成发货单
                    //自动生成发货单时，统计订单未出库金额  取销售订单状态为3的即可
                    //生成销售发货通知单  统计订单状态为3的和销售发货通知单为3的状态的金额
                    String sysparam = getSysParamValue("IsDispatchProcessUse");
                    if("0".equals(sysparam)){
                        //订单未出库金额
                        orderUnOutAmount = salesOrderMapper.getCustomerOrderAmount(customer.getId());
                    }else{
                        orderUnOutAmount = salesOrderMapper.getCustomerOrderAndDispatchAmount(customer.getId());
                    }
                    if (null != orderUnOutAmount) {
                        receivableAmount = receivableAmount.add(orderUnOutAmount);
                    }
                    //现结客户 是否控制下单
                    String isCashOrderAudit = getSysParamValue("isCashOrderAudit");
                    if ("1".equals(isCashOrderAudit)) {
                        //结算方式为现结的客户
                        if (settleFlag) {
                            CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(customer.getId());
                            //客户欠款要小于等于客户账户余额才行
                            // 应收金额+允许尾差金额
                            if (StringUtils.isNotEmpty(negateTailAmountLimit)) {
                                receivableAmount = receivableAmount.add(new BigDecimal(negateTailAmountLimit));
                            }
                            if (null != customerCapital && receivableAmount.compareTo(customerCapital.getAmount()) != 1) {
                                cashFlag = true;
                            } else {
                                cashFlag = false;
                            }
                        }
                    }
                }

                //已用额度
                useCreditAmount = receivableAmount;
                //客户信用额度控制 客户应收款总额大于0时 现结客户不受该参数控制
                if (null != receivableAmount && receivableAmount.compareTo(BigDecimal.ZERO) == 1  && !settleFlag) {
                    //客户未定义信用额度时， 返回true
                    if (null == comCreditamount || comCreditamount.compareTo(BigDecimal.ZERO) == 0) {
                        creditflag = true;
                    } else if (comCreditamount.compareTo(receivableAmount) != -1) {        //信用额度大于等于应收款总额时 返回true
                        creditflag = true;
                    } else {
//                        //判断应收金额+允许尾差金额，信用额度是否大于等于应收款总额，若是，返回true,否则返回false
//                        if (StringUtils.isNotEmpty(negateTailAmountLimit)) {
//                            receivableAmount = receivableAmount.add(new BigDecimal(negateTailAmountLimit));
//                        }
//                        if(comCreditamount.compareTo(receivableAmount) != -1){
//                            creditflag = true;
//                        }else{
//                            creditflag = false;
//                        }
                        creditflag = false;
                    }
                }
                //超账期判断 1按客户整体来判断 2按该订单中的品牌判断
                String orderPassdateTypeControl = getSysParamValue("OrderPassdateTypeControl");
                if(StringUtils.isEmpty(orderPassdateTypeControl)){
                    orderPassdateTypeControl = "1";
                }
                List<String> bList = null;
                //判断客户是否超账期控制 现结客户不受该参数控制
                if (null != customer && "1".equals(customer.getOvercontrol()) && !settleFlag) {
                    if("2".equals(orderPassdateTypeControl)){
                        bList = salesOrderMapper.getBrandListInOrder(id);
                        if(bList.size()==0){
                            bList = null;
                        }
                    }
                    BigDecimal passDateAmount = salesReceiptMapper.getReceivablePassDateAmountByCustomerid(customer.getId(), date,bList);
                    if (null != passDateAmount && passDateAmount.compareTo(BigDecimal.ZERO) == 1) {
                        passDateflag = false;
                    } else {
                        passDateflag = true;
                    }
                } else {
                    passDateflag = true;
                }
                //0金额判断
                String isZeroOrderAudit = getSysParamValue("IsZeroOrderAudit");
                if ("1".equals(isZeroOrderAudit)) {
                    int zeroCount = getSalesOrderDetailMapper().getOrderDetailZeroCount(id);
                    if (zeroCount > 0) {
                        zeroFlag = false;
                    }
                }
                //组装信息说明
                msg = "订单" + id + ":" + "客户：" + customer.getId() + "," + customer.getName() + ",";
                if (!creditflag) {
                    if(StringUtils.isNotEmpty(customer.getPid()) && pcreditamount.compareTo(BigDecimal.ZERO)==1){
                        pcreditamount = pcreditamount.setScale(2,BigDecimal.ROUND_HALF_UP);
                        msg += "总店应收款超过信用额度!信用额度:" + pcreditamount + "；已用额度：" + useCreditAmount.setScale(2, BigDecimal.ROUND_HALF_UP)+"；";
                    }else{
                        if (null != customer.getCredit()) {
                            creditamount = customer.getCredit().setScale(2, BigDecimal.ROUND_HALF_UP);
                        }
                        msg += "应收款超过信用额度!信用额度:" + creditamount + "；已用额度：" + useCreditAmount.setScale(2, BigDecimal.ROUND_HALF_UP)+"；";
                    }
                }
                if (!passDateflag) {
                    msg += "应收款超账期；";
                    if("2".equals(orderPassdateTypeControl)){
                        List<Map> bPassList = salesReceiptMapper.getReceivablePassDateBrandListByCustomerid(customer.getId(), date,bList);
                        if(null!=bPassList && bPassList.size()>0){
                            msg += "品牌：";
                            for(Map map : bPassList){
                                String brandid = (String) map.get("brandid");
                                BigDecimal amount = (BigDecimal) map.get("amount");
                                Brand brand = getGoodsBrandByID(brandid);
                                if(null!=brand){
                                    msg += brand.getName()+",超账:"+amount.setScale(2,BigDecimal.ROUND_HALF_UP).toString()+",";
                                }
                            }
                        }
                    }
                }
                if (!zeroFlag) {
                    msg += "有0金额商品；";
                }
                if (!cashFlag) {
                    CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(customer.getId());
                    if(null != customerCapital){
                        msg += "客户结算方式为现结，账户余额:"+customerCapital.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)+"；已用余额："+receivableAmount.setScale(2, BigDecimal.ROUND_HALF_UP)
                                +"，余额不足下单；";
                    }else {
                        msg += "客户结算方式为现结，账户余额:0；余额不足下单；";
                    }

                }
                //判断是否可以审核
                if (!creditflag || !passDateflag || !zeroFlag || !cashFlag) {
                    flag = false;
                } else {
                    flag = true;
                }
            }
        } else {
            flag = false;
            msg += "客户或者订单不存在；";
            flag = false;
        }
        returnMap.put("flag", flag);
        returnMap.put("msg", msg);
        return returnMap;
    }
}

