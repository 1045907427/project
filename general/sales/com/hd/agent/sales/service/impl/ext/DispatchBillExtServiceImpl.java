/**
 * @(#)DispatchBillExtServiceImpl.java
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
import com.hd.agent.basefiles.model.CustomerGoods;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.TaxType;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.sales.model.*;
import com.hd.agent.sales.service.IReceiptService;
import com.hd.agent.sales.service.ext.IDispatchBillExtService;
import com.hd.agent.sales.service.ext.IOrderExtService;
import com.hd.agent.sales.service.impl.BaseSalesServiceImpl;
import com.hd.agent.storage.service.IStorageForSalesService;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zhengziyong
 */
public class DispatchBillExtServiceImpl extends BaseSalesServiceImpl implements IDispatchBillExtService {

    /**
     * 调用库存接口service
     */
    private IStorageForSalesService storageForSalesService;
    /**
     * 销售发货回单service
     */
    private IReceiptService receiptService;
    /**
     * 销售订单接口service
     */
    private IOrderExtService salesOrderExtService;

    public IStorageForSalesService getStorageForSalesService() {
        return storageForSalesService;
    }

    public void setStorageForSalesService(
            IStorageForSalesService storageForSalesService) {
        this.storageForSalesService = storageForSalesService;
    }

    public IReceiptService getReceiptService() {
        return receiptService;
    }

    public void setReceiptService(IReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    public IOrderExtService getSalesOrderExtService() {
        return salesOrderExtService;
    }

    public void setSalesOrderExtService(IOrderExtService salesOrderExtService) {
        this.salesOrderExtService = salesOrderExtService;
    }

    /**
     * 订单审核自动生成发货通知单
     *
     * @param order
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date May 28, 2013
     */
    public String addDispatchBillAuto(Order order) throws Exception {
        String result = null;
        DispatchBill dispatchBill = new DispatchBill();
        dispatchBill.setAdddeptid(order.getAdddeptid());
        dispatchBill.setAdddeptname(order.getAdddeptname());
        dispatchBill.setAdduserid(order.getAdduserid());
        dispatchBill.setAddusername(order.getAddusername());
        dispatchBill.setBillno(order.getId());
        dispatchBill.setBusinessdate(order.getBusinessdate());
        dispatchBill.setCustomerid(order.getCustomerid());
        dispatchBill.setPcustomerid(order.getPcustomerid());
        dispatchBill.setCustomersort(order.getCustomersort());
        dispatchBill.setStorageid(order.getStorageid());
        dispatchBill.setRemark(order.getRemark());
        dispatchBill.setPaytype(order.getPaytype());
        dispatchBill.setHandlerid(order.getHandlerid());
        dispatchBill.setSalesarea(order.getSalesarea());
        dispatchBill.setSalesdept(order.getSalesdept());
        dispatchBill.setSalesuser(order.getSalesuser());
        dispatchBill.setSettletype(order.getSettletype());
        dispatchBill.setSource("1");
        dispatchBill.setStatus("2");
        dispatchBill.setField01(order.getField01());
        dispatchBill.setField02(order.getField02());
        dispatchBill.setField03(order.getField03());
        dispatchBill.setField04(order.getField04());
        dispatchBill.setField05(order.getField05());
        dispatchBill.setField06(order.getField06());
        dispatchBill.setField07(order.getField07());
        dispatchBill.setField08(order.getField08());
        if (isAutoCreate("t_sales_dispatchbill")) {
            // 获取自动编号
            String id = getAutoCreateSysNumbderForeign(dispatchBill, "t_sales_dispatchbill");
            dispatchBill.setId(id);
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
            Random random = new Random();
            String rand = random.nextInt(99999999) + "";
            String id = dateFormat.format(new Date()) + rand;
            dispatchBill.setId(id);
        }
        dispatchBill.setBillDetailList(orderDetailToBillDetail(order.getOrderDetailList(), dispatchBill.getId()));
        if (addDispatchBill(dispatchBill)) {
            result = dispatchBill.getId();
        }
        return result;
    }

    /**
     * 订单明细转为发货通知单明细
     *
     * @param orderDetailList 订单明细
     * @param billId         发货单编号
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date May 28, 2013
     */
    private List<DispatchBillDetail> orderDetailToBillDetail(List<OrderDetail> orderDetailList, String billId) throws Exception {
        List<DispatchBillDetail> billDetailList = new ArrayList<DispatchBillDetail>();
        int seq = 1;
        for (OrderDetail orderDetail : orderDetailList) {
            DispatchBillDetail dispatchBillDetail = new DispatchBillDetail();
            dispatchBillDetail.setGoodsid(orderDetail.getGoodsid());
            dispatchBillDetail.setGroupid(orderDetail.getGroupid());
            dispatchBillDetail.setGoodssort(orderDetail.getGoodssort());
            dispatchBillDetail.setBrandid(orderDetail.getBrandid());
            dispatchBillDetail.setBranduser(orderDetail.getBranduser());
            dispatchBillDetail.setSupplieruser(orderDetail.getSupplieruser());
            dispatchBillDetail.setSupplierid(orderDetail.getSupplierid());
            dispatchBillDetail.setBranddept(orderDetail.getBranddept());
            dispatchBillDetail.setIsdiscount(orderDetail.getIsdiscount());
            dispatchBillDetail.setIsbranddiscount(orderDetail.getIsbranddiscount());
            dispatchBillDetail.setRepartitiontype(orderDetail.getRepartitiontype());
            dispatchBillDetail.setUnitid(orderDetail.getUnitid());
            dispatchBillDetail.setUnitname(orderDetail.getUnitname());
            dispatchBillDetail.setUnitnum(orderDetail.getUnitnum());
            dispatchBillDetail.setAuxnum(orderDetail.getAuxnum());
            dispatchBillDetail.setOvernum(orderDetail.getOvernum());
            dispatchBillDetail.setTotalbox(orderDetail.getTotalbox());
            dispatchBillDetail.setAuxnumdetail(orderDetail.getAuxnumdetail());
            dispatchBillDetail.setAuxunitid(orderDetail.getAuxunitid());
            dispatchBillDetail.setAuxunitname(orderDetail.getAuxunitname());
            dispatchBillDetail.setDeliverytype(orderDetail.getDeliverytype());
            dispatchBillDetail.setDeliverydate(orderDetail.getDeliverydate());
            dispatchBillDetail.setExpirationdate(orderDetail.getExpirationdate());
            dispatchBillDetail.setRemark(orderDetail.getRemark());
            dispatchBillDetail.setTax(orderDetail.getTax());
            dispatchBillDetail.setTaxamount(orderDetail.getTaxamount());
            dispatchBillDetail.setTaxprice(orderDetail.getTaxprice());
            dispatchBillDetail.setNotaxamount(orderDetail.getNotaxamount());
            dispatchBillDetail.setNotaxprice(orderDetail.getNotaxprice());
            dispatchBillDetail.setCostprice(orderDetail.getCostprice());
            dispatchBillDetail.setTaxtype(orderDetail.getTaxtype());
            dispatchBillDetail.setTaxtypename(orderDetail.getTaxtypename());
            dispatchBillDetail.setBillid(billId);
            dispatchBillDetail.setBillno(orderDetail.getOrderid());
            dispatchBillDetail.setBilldetailno(orderDetail.getId());
            dispatchBillDetail.setField01(orderDetail.getField01());
            dispatchBillDetail.setField02(orderDetail.getField02());
            dispatchBillDetail.setField03(orderDetail.getField03());
            dispatchBillDetail.setField04(orderDetail.getField04());
            dispatchBillDetail.setField05(orderDetail.getField05());
            dispatchBillDetail.setField06(orderDetail.getField06());
            dispatchBillDetail.setField07(orderDetail.getField07());
            dispatchBillDetail.setField08(orderDetail.getField08());
            dispatchBillDetail.setSummarybatchid(orderDetail.getSummarybatchid());
            dispatchBillDetail.setStoragelocationid(orderDetail.getStoragelocationid());
            dispatchBillDetail.setBatchno(orderDetail.getBatchno());
            dispatchBillDetail.setProduceddate(orderDetail.getProduceddate());
            dispatchBillDetail.setDeadline(orderDetail.getDeadline());
            dispatchBillDetail.setSeq(seq);
            dispatchBillDetail.setStorageid(orderDetail.getStorageid());
            if (dispatchBillDetail.getUnitnum().compareTo(BigDecimal.ZERO) == 1 || "1".equals(dispatchBillDetail.getIsdiscount())) {
                billDetailList.add(dispatchBillDetail);
            }
            seq++;
        }
        return billDetailList;
    }

    /**
     * 直营销售订单（车销单）明细转为发货通知单明细
     *
     * @param ordercarDetailList
     * @param billId
     * @return
     * @throws Exception
     * @author chenwei
     * @date Sep 3, 2013
     */
    public List<DispatchBillDetail> ordercarDetailToBillDetail(List<OrderCarDetail> ordercarDetailList, String billId) throws Exception {
        List<DispatchBillDetail> billDetailList = new ArrayList<DispatchBillDetail>();
        for (OrderCarDetail ordercarDetail : ordercarDetailList) {
            DispatchBillDetail dispatchBillDetail = new DispatchBillDetail();
            dispatchBillDetail.setGoodsid(ordercarDetail.getGoodsid());
            dispatchBillDetail.setGoodssort(ordercarDetail.getGoodssort());
            dispatchBillDetail.setBrandid(ordercarDetail.getBrandid());
            dispatchBillDetail.setBranduser(ordercarDetail.getBranduser());
            dispatchBillDetail.setSupplieruser(ordercarDetail.getSupplieruser());
            dispatchBillDetail.setBranddept(ordercarDetail.getBranddept());
            dispatchBillDetail.setUnitid(ordercarDetail.getUnitid());
            dispatchBillDetail.setUnitname(ordercarDetail.getUnitname());
            dispatchBillDetail.setUnitnum(ordercarDetail.getUnitnum());
            dispatchBillDetail.setAuxnum(ordercarDetail.getAuxnum());
            dispatchBillDetail.setOvernum(ordercarDetail.getOvernum());
            dispatchBillDetail.setAuxnumdetail(ordercarDetail.getAuxnumdetail());
            dispatchBillDetail.setAuxunitid(ordercarDetail.getAuxunitid());
            dispatchBillDetail.setAuxunitname(ordercarDetail.getAuxunitname());
            dispatchBillDetail.setBatchno(ordercarDetail.getBatchno());
            dispatchBillDetail.setDeliverydate(ordercarDetail.getDeliverydate());
            dispatchBillDetail.setExpirationdate(ordercarDetail.getExpirationdate());
            dispatchBillDetail.setRemark(ordercarDetail.getRemark());
            dispatchBillDetail.setTax(ordercarDetail.getTax());
            dispatchBillDetail.setTaxamount(ordercarDetail.getTaxamount());
            dispatchBillDetail.setTaxprice(ordercarDetail.getTaxprice());
            dispatchBillDetail.setNotaxamount(ordercarDetail.getNotaxamount());
            dispatchBillDetail.setNotaxprice(ordercarDetail.getNotaxprice());
            dispatchBillDetail.setTaxtype(ordercarDetail.getTaxtype());
            dispatchBillDetail.setTaxtypename(ordercarDetail.getTaxtypename());
            dispatchBillDetail.setBillid(billId);
            dispatchBillDetail.setBillno(ordercarDetail.getOrderid());
            dispatchBillDetail.setBilldetailno(ordercarDetail.getId());
            dispatchBillDetail.setField01(ordercarDetail.getField01());
            dispatchBillDetail.setField02(ordercarDetail.getField02());
            dispatchBillDetail.setField03(ordercarDetail.getField03());
            dispatchBillDetail.setField04(ordercarDetail.getField04());
            dispatchBillDetail.setField05(ordercarDetail.getField05());
            dispatchBillDetail.setField06(ordercarDetail.getField06());
            dispatchBillDetail.setField07(ordercarDetail.getField07());
            dispatchBillDetail.setField08(ordercarDetail.getField08());

            dispatchBillDetail.setCostprice(ordercarDetail.getCostprice());
            billDetailList.add(dispatchBillDetail);
        }
        return billDetailList;
    }

    /**
     * 订单反审时检查订单的下游发货通知单是否审核，未审核则删除，审核则不可反审
     *
     * @param id 订单编号
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date May 28, 2013
     */
    public boolean deleteDispatchBillOppauditOrder(String id) throws Exception {
        DispatchBill dispatchBill = getDispatchBillByOrder(id);
        //根据系统参数 判断是否启用了销售发货通知单
        //未启用销售发货通知单时 销售订单反审需要先反审销售发货通知单 再删除发货通知单
        String sysparam = getSysParamValue("IsDispatchProcessUse");
        if ("0".equals(sysparam) && null != dispatchBill) {
            if ("4".equals(dispatchBill.getStatus())) { //只有状态未关闭的单据
                return false;
            }
            boolean bl = storageForSalesService.deleteSaleOutBySourceid(dispatchBill.getId());
            if (bl) {
                List<DispatchBillDetail> billDetailList = dispatchBill.getBillDetailList();
                String orderId = dispatchBill.getBillno(); //取参照的订单的编号
                salesOrderExtService.updateClearOrderDetailBack(billDetailList);
                salesOrderExtService.updateOrderOpen(orderId);
                DispatchBill bill = new DispatchBill();
                bill.setStatus("2");
                bill.setId(dispatchBill.getId());
                bill.setPhprinttimes(0);
                bill.setPrinttimes(0);
                boolean flag = getSalesDispatchBillMapper().updateDispatchBillStatus(bill) > 0;
                if (flag) {
                    dispatchBill.setStatus("2");
                }
            }
        }
        if (dispatchBill != null) {
            if ("1".equals(dispatchBill.getStatus()) || "2".equals(dispatchBill.getStatus())) {
                deleteDispatchBill(dispatchBill.getId());
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public Map addNextBillByOrdercar(OrderCar orderCar)
            throws Exception {
        boolean flag = false;
        Map map = new HashMap();
        if (null != orderCar && null != orderCar.getStorageid()) {
            flag = addOrderBillByOrdercar(orderCar);
            map.put("flag", flag);
        } else {
            map.put("flag", false);
            map.put("msg", "直营销售单没有指定仓库。");
        }
        return map;
    }

    /**
     * 车销订单生成订单
     *
     * @param orderCar
     * @return
     * @throws Exception
     * @author chenwei
     * @date Mar 18, 2014
     */
    public boolean addOrderBillByOrdercar(OrderCar orderCar) throws Exception {
        Order order = new Order();
        order.setAudittime(new Date());
        order.setAudituserid(orderCar.getAudituserid());
        order.setAuditusername(orderCar.getAuditusername());
        order.setAdddeptid(orderCar.getAdddeptid());
        order.setAdddeptname(orderCar.getAdddeptname());
        order.setAdduserid(orderCar.getAdduserid());
        order.setAddusername(orderCar.getAddusername());
        order.setIndooruserid(orderCar.getIndooruserid());
        order.setBusinessdate(CommonUtils.getTodayDataStr());
        order.setCustomerid(orderCar.getCustomerid());
        order.setCustomersort(orderCar.getCustomersort());
        order.setRemark(orderCar.getRemark());
        order.setPaytype(orderCar.getPaytype());
        order.setHandlerid(orderCar.getHandlerid());
        order.setSalesarea(orderCar.getSalesarea());
        order.setSalesdept(orderCar.getSalesdept());
        order.setSalesuser(orderCar.getSalesuser());
        order.setSettletype(orderCar.getSettletype());
        order.setSourceid(orderCar.getId());
        order.setSourcetype("2");
        order.setStorageid(orderCar.getStorageid());
        order.setStatus("4");
        order.setField01(orderCar.getField01());
        order.setField02(orderCar.getField02());
        order.setField03(orderCar.getField03());
        order.setField04(orderCar.getField04());
        order.setField05(orderCar.getField05());
        order.setField06(orderCar.getField06());
        order.setField07(orderCar.getField07());
        order.setField08(orderCar.getField08());
        order.setRemark("此单为直营销售单");
        if (isAutoCreate("t_sales_order_1")) {
            // 获取自动编号
            String id = getAutoCreateSysNumbderForeign(order, "t_sales_order_1");
            order.setId(id);
        } else {
            order.setId(orderCar.getId());
        }
        order.setOrderDetailList(ordercarDetailToOrderDetail(orderCar.getOrderDetailList(), order.getId(), order.getCustomerid()));
        boolean flag = addOrderForPhone(order);
        if (flag) {
            //添加已交易记录
            addCustomerGoods(order,order.getId());
            flag = addDispatchBillByOrdercar(order);
        }
        return flag;
    }

    private List<OrderDetail> ordercarDetailToOrderDetail(List<OrderCarDetail> list, String orderId, String customerId) throws Exception {
        List<OrderDetail> detailList = new ArrayList<OrderDetail>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());
        for (OrderCarDetail orderCarDetail : list) {
            OrderDetail detail = new OrderDetail();
            detail.setOrderid(orderId);
            detail.setGoodsid(orderCarDetail.getGoodsid());
            GoodsInfo goodsInfo = getGoodsInfoByID(detail.getGoodsid());
            detail.setGoodsInfo(goodsInfo);
            detail.setUnitnum(orderCarDetail.getUnitnum());
            detail.setUnitid(orderCarDetail.getUnitid());
            detail.setUnitname(orderCarDetail.getUnitname());
            detail.setAuxunitid(orderCarDetail.getAuxunitid());
            detail.setAuxunitname(orderCarDetail.getAuxunitname());
            detail.setAuxnum(orderCarDetail.getAuxnum());
            detail.setOvernum(orderCarDetail.getOvernum());
            detail.setTotalbox(orderCarDetail.getTotalbox());
            detail.setAuxnumdetail(orderCarDetail.getAuxnumdetail());
            detail.setTaxtype(orderCarDetail.getTaxtype());
            detail.setDemandprice(orderCarDetail.getTaxprice());
            detail.setDemandamount(orderCarDetail.getTaxamount());

            //车销设置已发货数量
            detail.setSendnumaux(orderCarDetail.getTotalbox());
            detail.setSendnummain(orderCarDetail.getUnitnum());
            detail.setSendamounttax(orderCarDetail.getTaxamount());
            detail.setSendamountnotax(orderCarDetail.getNotaxamount());

            if (orderCarDetail.getTaxprice().compareTo(new BigDecimal(0)) == 0) {
                detail.setRemark("赠送");
            } else {
                detail.setTaxprice(orderCarDetail.getTaxprice());
                detail.setOldprice(orderCarDetail.getTaxprice());
                BigDecimal taxamount=detail.getUnitnum().multiply(detail.getTaxprice());
                detail.setTaxamount(taxamount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                BigDecimal noTaxAmount = getNotaxAmountByTaxAmount(taxamount, detail.getTaxtype());
                detail.setNotaxamount(noTaxAmount);
                BigDecimal tax = detail.getTaxamount().subtract(detail.getNotaxamount());
                detail.setTax(tax);
                TaxType taxType = getTaxType(detail.getTaxtype());
                BigDecimal noTaxPrice = detail.getTaxprice().divide(taxType.getRate().divide(new BigDecimal(100)).add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP);
                detail.setNotaxprice(noTaxPrice);
                String remark = orderCarDetail.getRemark();
                if (null == remark) {
                    remark = "";
                }
                detail.setRemark(remark);
            }
            detailList.add(detail);
        }
        return detailList;
    }
    /**
     * 添加已买商品，添加客户满赠已赠送信息
     *
     * @param order
     * @param id
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 24, 2013
     */
    public void addCustomerGoods(Order order, String id) throws Exception {
        if(null!=order){
            List<OrderDetail> list = order.getOrderDetailList();
            if (list.size() != 0) {
                for (OrderDetail orderDetail : list) {
                    CustomerGoods customerGoods = getBaseCustomerMapper().getCustomerGoodsByCustomerAndGoodsid(order.getCustomerid(),
                            orderDetail.getGoodsid());
                    //已存在该已买商品，判断价格是否相同
                    if (null != customerGoods) {
                        BigDecimal taxprice = (null != orderDetail.getTaxprice()) ? orderDetail.getTaxprice() : (new BigDecimal(0));
                        BigDecimal price = (null != customerGoods.getPrice()) ? customerGoods.getPrice() : (new BigDecimal(0));
                        //更新该已买商品
                        if (taxprice.compareTo(price) != 0) {
                            customerGoods.setPrice(taxprice);
                            getBaseCustomerMapper().updateCustomerGoods(customerGoods);
                        }
                    } else {
                        //商品不存在，新增已买商品
                        CustomerGoods customerGoods2 = new CustomerGoods();
                        customerGoods2.setCustomerid(order.getCustomerid());
                        customerGoods2.setGoodsid(orderDetail.getGoodsid());
                        customerGoods2.setPrice(orderDetail.getTaxprice());
                        getBaseCustomerMapper().addCustomerGoods(customerGoods2);
                    }
                }
            }

        }
    }
    /**
     * 直营销售单生成发货通知单
     *
     * @param order
     * @return
     * @throws Exception
     * @author chenwei
     * @date Sep 3, 2013
     */
    public boolean addDispatchBillByOrdercar(Order order) throws Exception {
        DispatchBill dispatchBill = new DispatchBill();
        dispatchBill.setAudittime(new Date());
        dispatchBill.setAudituserid(order.getAudituserid());
        dispatchBill.setAuditusername(order.getAuditusername());
        dispatchBill.setAdddeptid(order.getAdddeptid());
        dispatchBill.setAdddeptname(order.getAdddeptname());
        dispatchBill.setAdduserid(order.getAdduserid());
        dispatchBill.setAddusername(order.getAddusername());
        dispatchBill.setIndooruserid(order.getIndooruserid());
        dispatchBill.setBillno(order.getId());
        dispatchBill.setBusinessdate(order.getBusinessdate());
        dispatchBill.setCustomerid(order.getCustomerid());
        dispatchBill.setCustomersort(order.getCustomersort());
        dispatchBill.setPcustomerid(order.getPcustomerid());
        dispatchBill.setRemark(order.getRemark());
        dispatchBill.setPaytype(order.getPaytype());
        dispatchBill.setHandlerid(order.getHandlerid());
        dispatchBill.setSalesarea(order.getSalesarea());
        dispatchBill.setSalesdept(order.getSalesdept());
        dispatchBill.setSalesuser(order.getSalesuser());
        dispatchBill.setSettletype(order.getSettletype());
        dispatchBill.setSource("2");
        dispatchBill.setStorageid(order.getStorageid());
        dispatchBill.setStatus("4");
        dispatchBill.setField01(order.getField01());
        dispatchBill.setField02(order.getField02());
        dispatchBill.setField03(order.getField03());
        dispatchBill.setField04(order.getField04());
        dispatchBill.setField05(order.getField05());
        dispatchBill.setField06(order.getField06());
        dispatchBill.setField07(order.getField07());
        dispatchBill.setField08(order.getField08());

        if (isAutoCreate("t_sales_dispatchbill")) {
            // 获取自动编号
            String id = getAutoCreateSysNumbderForeign(dispatchBill, "t_sales_dispatchbill");
            dispatchBill.setId(id);
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
            Random random = new Random();
            String rand = random.nextInt(99999999) + "";
            String id = dateFormat.format(new Date()) + rand;
            dispatchBill.setId(id);
        }
        //直营销售订单明细转换成销售发货通知单明细
        List detailList = orderDetailToBillDetail(order.getOrderDetailList(), dispatchBill.getId());
        dispatchBill.setBillDetailList(detailList);
        boolean flag = addDispatchBill(dispatchBill);
        if (flag) {
            //获取销售发货通知单明细列表
            List dataList = getDispatchBillDetailList(dispatchBill.getId());
            //生成发货单并审核
            Map returnmap = storageForSalesService.addSaleOutByOrdercar(dispatchBill, dataList);
            flag = (Boolean) returnmap.get("flag");
            if (flag) {
                String receiptid = (String) returnmap.get("receiptid");
                Map receiptmap = receiptService.auditReceipt("1", receiptid);
                flag = (Boolean) receiptmap.get("flag");
            }
        }
        return flag;
    }

    @Override
    public boolean isGoodsEnoughByOrdercar(OrderCar orderCar,
                                           List<OrderCarDetail> detailList) throws Exception {
        Map returnmap = storageForSalesService.isGoodsEnoughByOrdercar(orderCar, orderCar.getOrderDetailList());
        boolean sendflag = (Boolean) returnmap.get("flag");
        return sendflag;
    }

    @Override
    public Map addAndAuditDispatchBill(Order order) throws Exception {
        SysUser sysUser = getSysUser();
        Map result = new HashMap();
        String dispatchbillid = addDispatchBillAuto(order);
        DispatchBill dispatchBill = getSalesDispatchBillMapper().getDispatchBill(dispatchbillid);
        List detailList = getSalesDispatchBillMapper().getDispatchBillDetailListByBill(dispatchbillid);
        if (!"2".equals(dispatchBill.getStatus())) { //只有状态为2（保存状态）才可进行审核
            result.put("flag", false);
            return result;
        }
        if (null != detailList && detailList.size() > 0) {
            Map juMap = new HashMap();
            if (null == dispatchBill.getStorageid() || "".equals(dispatchBill.getStorageid())) {
                juMap = storageForSalesService.isGoodsEnoughByDispatchBillDetail(detailList);
            } else {
                juMap = storageForSalesService.isGoodsEnoughByDispatchBillDetailInStorage(dispatchBill.getStorageid(), detailList);
            }
            if (!juMap.containsKey("flag") || !(Boolean) juMap.get("flag")) {
                result.put("flag", false);
                result.put("msg", juMap.get("msg"));
                return result;
            }
            Map map = storageForSalesService.addSaleOutByDispatchbill(dispatchBill, detailList); //自动生成出库单
            if (map.containsKey("flag") && map.get("flag").toString() == "true") {
                getSalesDispatchBillMapper().updateDispatchBillRefer("1", dispatchbillid); //更新发货通知单的参照状态
            }
            if ("1".equals(dispatchBill.getSource())) { //参照订单则需要回写
                String orderId = dispatchBill.getBillno(); //取参照的订单的编号
                salesOrderExtService.updateOrderDetailBack(detailList,orderId); //回写上游单据数据
//				salesOrderExtService.updateOrderClose(orderId); //关闭上游单据
            }
            DispatchBill bill = new DispatchBill();
            bill.setAudituserid(sysUser.getUserid());
            bill.setAuditusername(sysUser.getName());
            bill.setAudittime(new Date());
            bill.setStatus("3");
            bill.setId(dispatchbillid);
            //审核之后 设置当天日期为业务日期
            bill.setBusinessdate(order.getBusinessdate());
            boolean flag = getSalesDispatchBillMapper().updateDispatchBillStatus(bill) > 0;
            result.put("flag", flag);
            result.put("billid", map.get("saleoutid"));
            result.put("msg", "生成销售发货单:" + map.get("saleoutid"));
        }else{
            result.put("flag", false);
            result.put("msg", "单据明细为空，自动关闭");
        }
        return result;
    }
}

