/**
 * @(#)OrderCarServiceImpl.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 2, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.*;
import com.hd.agent.sales.service.IOrderCarService;
import com.hd.agent.sales.service.IOrderService;
import com.hd.agent.sales.service.IReceiptService;
import com.hd.agent.sales.service.ext.IDispatchBillExtService;
import com.hd.agent.storage.model.Saleout;
import com.hd.agent.storage.model.SaleoutDetail;
import com.hd.agent.storage.model.StorageSummary;
import com.hd.agent.storage.service.IStorageForSalesService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 车销
 *
 * @author zhengziyong
 */
public class OrderCarServiceImpl extends BaseSalesServiceImpl implements IOrderCarService {

    private IDispatchBillExtService dispatchBillExtService;

    private IStorageForSalesService storageForSalesService;

    private IReceiptService receiptService;

    private IOrderService orderService;

    public IDispatchBillExtService getDispatchBillExtService() {
        return dispatchBillExtService;
    }

    public void setDispatchBillExtService(
            IDispatchBillExtService dispatchBillExtService) {
        this.dispatchBillExtService = dispatchBillExtService;
    }

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

    public IOrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(IOrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public Map addOrderCar(OrderCar order) throws Exception {
        Map retMap = new HashMap();
        if (isAutoCreate("t_sales_order_car")) {
            // 获取自动编号
            String id = getAutoCreateSysNumbderForeign(order, "t_sales_order_car");
            order.setId(id);
        }else{
            order.setId("CXXD-"+CommonUtils.getDataNumberSeconds());
        }
        //判断是否存在keyid，不存在则直接取单据编号，且，判断该单据是否上传过
        if(StringUtils.isEmpty(order.getKeyid())){
            order.setKeyid(order.getId());
        }
        boolean isHaveFlag = isHaveOrderCarByKeyid(order.getKeyid());
        if(isHaveFlag){
            retMap.put("flag", false);
            retMap.put("msg", "根据手机Keyid判断，已存在该单据！");
            return retMap;
        }
        Customer customer = getCustomerByID(order.getCustomerid());
        if(customer != null){
            order.setHandlerid(customer.getContact());
            order.setSalesdept(customer.getSalesdeptid());
            order.setSalesuser(customer.getSalesuserid());
            order.setSettletype(customer.getSettletype());
            order.setCustomersort(customer.getCustomersort());
            order.setSalesarea(customer.getSalesarea());
            order.setPcustomerid(customer.getPid());
        }
        SysUser thisSysuser = getSysUser();
        //要货通知单客户业务员获取方式 1取客户默认业务员2取制单人为客户业务员
        String salesUserDemandType = getSysParamValue("SalesUserDemandType");
        if("2".equals(salesUserDemandType)){
            Personnel personnel = getPersonnelById(thisSysuser.getPersonnelid());
            if(null!=personnel){
                order.setSalesdept(personnel.getBelongdeptid());
                order.setSalesuser(personnel.getId());
            }
        }
        if(StringUtils.isEmpty(order.getCaruser())){
            SysUser sysUser = getSysUser();
            if(sysUser != null){
                if(StringUtils.isEmpty(order.getStorageid())){
                    StorageInfo storageInfo = getStorageInfoByCarsaleuser(sysUser.getUserid());
                    if(storageInfo != null){
                        order.setStorageid(storageInfo.getId());
                    }
                }

                order.setAdddeptid(sysUser.getDepartmentid());
                order.setAdddeptname(sysUser.getDepartmentname());
                order.setAdduserid(sysUser.getUserid());
                order.setAddusername(sysUser.getName());
            }
        }else{
            SysUser sysUser = getBaseSysUserMapper().checkSysUserByPerId(order.getCaruser());
            if(sysUser != null){
                order.setAdddeptid(sysUser.getDepartmentid());
                order.setAdddeptname(sysUser.getDepartmentname());
                order.setAdduserid(sysUser.getUserid());
                order.setAddusername(sysUser.getName());
            }
        }
        //根据客户编号获取该客户的销售内勤用户信息
        SysUser indooruser = getSalesIndoorSysUserByCustomerid(order.getCustomerid());
        if (indooruser != null) {
            order.setIndooruserid(indooruser.getPersonnelid());
        }

        String sysparm = getSysParamValue("OrderCarAuditAuto");
        //判断车销仓库中商品数量是否足够
        boolean flag = false;
        boolean auditflag = false;
        //车销订单自动审核 需要判断仓库中数量是否足够
        if ("1".equals(sysparm)) {
            List<OrderCarDetail> orderDetailList = order.getOrderDetailList();
            if (orderDetailList.size() > 0) {
                for (OrderCarDetail orderDetail : orderDetailList) {
                    if (orderDetail != null) {
                        orderDetail.setOrderid(order.getId());
                        GoodsInfo goodsInfo = getAllGoodsInfoByID(orderDetail.getGoodsid());
                        if (null != goodsInfo) {
                            orderDetail.setGoodssort(goodsInfo.getDefaultsort());
                            orderDetail.setBrandid(goodsInfo.getBrand());
                            orderDetail.setCostprice(getGoodsCostprice(order.getStorageid(),goodsInfo));
                            orderDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), order.getCustomerid()));
                            //厂家业务员
                            orderDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), order.getCustomerid()));
                            //获取品牌部门
                            Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                            if (null != brand) {
                                orderDetail.setBranddept(brand.getDeptid());
                            }
                            orderDetail.setSupplierid(goodsInfo.getDefaultsupplier());
                        }
                        orderDetail.setInitprice(orderDetail.getTaxprice());
                        getSalesOrderCarMapper().addOrderCarDetail(orderDetail);
                    }
                }

                flag = getSalesOrderCarMapper().addOrderCar(order) > 0;
                if (flag) {
                    retMap.put("id",order.getId());
                    if ("1".equals(sysparm)) {
                        SysUser addSysUser = getSysUserById(order.getAdduserid());
                        Map returnmap = auditOrderCar(order.getId(), addSysUser);
                        auditflag = (Boolean) returnmap.get("flag");
                    }
                }
            }
        } else {
            List<OrderCarDetail> orderDetailList = order.getOrderDetailList();
            if (orderDetailList.size() > 0) {
                for (OrderCarDetail orderDetail : orderDetailList) {
                    if (orderDetail != null) {
                        orderDetail.setOrderid(order.getId());
                        GoodsInfo goodsInfo = getAllGoodsInfoByID(orderDetail.getGoodsid());
                        if (null != goodsInfo) {
                            orderDetail.setGoodssort(goodsInfo.getDefaultsort());
                            orderDetail.setBrandid(goodsInfo.getBrand());
                            orderDetail.setCostprice(getGoodsCostprice(order.getStorageid(),goodsInfo));
                            orderDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), order.getCustomerid()));
                            //厂家业务员
                            orderDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), order.getCustomerid()));
                            //获取品牌部门
                            Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                            if (null != brand) {
                                orderDetail.setBranddept(brand.getDeptid());
                            }
                            orderDetail.setSupplierid(goodsInfo.getDefaultsupplier());
                        }
                        orderDetail.setInitprice(orderDetail.getTaxprice());
                        getSalesOrderCarMapper().addOrderCarDetail(orderDetail);
                    }
                }

                flag = getSalesOrderCarMapper().addOrderCar(order) > 0;
                if(flag){
                    retMap.put("id",order.getId());
                }
            }
        }
        retMap.put("flag",flag);
        retMap.put("auditflag",auditflag);
        return retMap;
    }

    @Override
    public boolean updateOrderCar(OrderCar order) throws Exception {
		Map map = new HashMap();
		map.put("id", order.getId());
		OrderCar oldOrder = getSalesOrderCarMapper().getOrderCar(map);
		if(null==oldOrder || "3".equals(oldOrder.getStatus()) || "4".equals(oldOrder.getStatus())){
			return false;
		}
        Customer customer = getCustomerByID(order.getCustomerid());
        if (null != customer) {
            order.setSalesarea(customer.getSalesarea());
            order.setPcustomerid(customer.getPid());
            order.setCustomersort(customer.getCustomersort());
        }
        SysUser carsysUser = getBaseSysUserMapper().checkSysUserByPerId(order.getCaruser());
        if(carsysUser != null){
            order.setAdddeptid(carsysUser.getDepartmentid());
            order.setAdddeptname(carsysUser.getDepartmentname());
            order.setAdduserid(carsysUser.getUserid());
            order.setAddusername(carsysUser.getName());
        }
        SysUser user = getSysUser();
        order.setModifyuserid(user.getUserid());
        order.setModifyusername(user.getName());
        SysUser sysUser = getSalesIndoorSysUserByCustomerid(order.getCustomerid());
        if (sysUser != null) {
            order.setIndooruserid(sysUser.getPersonnelid());
        }
        List<OrderCarDetail> orderDetailList = order.getOrderDetailList();
        //删除明细
        getSalesOrderCarMapper().deleteOrderCarDetailByOrderId(order.getId());
        if (orderDetailList.size() > 0) {
            for (OrderCarDetail orderDetail : orderDetailList) {
                if (orderDetail != null) {
                    orderDetail.setOrderid(order.getId());
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(orderDetail.getGoodsid());
                    if (null != goodsInfo) {
                        orderDetail.setGoodssort(goodsInfo.getDefaultsort());
                        orderDetail.setBrandid(goodsInfo.getBrand());
                        orderDetail.setCostprice(getGoodsCostprice(order.getStorageid(),goodsInfo));
                        //获取品牌部门
                        Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                        if (null != brand) {
                            orderDetail.setBranddept(brand.getDeptid());
                        }
                        orderDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), order.getCustomerid()));
                        //厂家业务员
                        orderDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), order.getCustomerid()));
                        orderDetail.setSupplierid(goodsInfo.getDefaultsupplier());
                    }
                    getSalesOrderCarMapper().addOrderCarDetail(orderDetail);
                }
            }
        }
        return getSalesOrderCarMapper().updateOrderCar(order) > 0;
    }

    @Override
    public OrderCar getOrderCar(String id) throws Exception {
        Map map = getAccessColumnMap("t_sales_order_car", null);
        map.put("id", id);
        OrderCar order = getSalesOrderCarMapper().getOrderCar(map);
        Customer customer = getCustomerByID(order.getCustomerid());
        if (customer != null) {
            order.setCustomername(customer.getName());
        }
        StorageInfo storageInfo = getStorageInfoByID(order.getStorageid());
        if (storageInfo != null) {
            order.setStoragename(storageInfo.getName());
        }
        //车销人员
        SysUser sysUser = getBaseSysUserMapper().getSysUserByUseridWithoutCache(order.getAdduserid());
        if(null != sysUser){
            order.setCaruser(sysUser.getPersonnelid());
        }
        List<OrderCarDetail> orderDetaiList = getSalesOrderCarMapper().getOrderCarDetailByOrder(id);
        for (OrderCarDetail detail : orderDetaiList) {
            GoodsInfo goodsInfo = getGoodsInfoByID(detail.getGoodsid());
            detail.setBuyprice(goodsInfo.getHighestbuyprice());
            detail.setGoodsInfo(goodsInfo);
            TaxType taxType = getTaxType(detail.getTaxtype());
            if (taxType != null) {
                detail.setTaxtypename(taxType.getName());
            }
            //获取系统价
            OrderDetail orderDetail = orderService.getGoodsDetail(detail.getGoodsid(), order.getCustomerid(), order.getBusinessdate(), detail.getUnitnum(), null);
            if (null != orderDetail) {
                detail.setSysprice(orderDetail.getTaxprice());
            }
            if ("2".equals(order.getStatus())) {
//				boolean flag = storageForSalesService.isGoodsEnoughByDispatchBillDetail(billDetail);
                StorageSummary storageSummary = null;
                if (null != order.getStorageid() && !"".equals(order.getStorageid())) {
                    storageSummary = storageForSalesService.getStorageSummarySumByGoodsidAndStorageid(detail.getGoodsid(), order.getStorageid());
                } else {
                    storageSummary = storageForSalesService.getStorageSummarySumByGoodsid(detail.getGoodsid());
                }
                if (null != storageSummary) {
                    detail.setUsablenum(storageSummary.getUsablenum());
                } else {
                    detail.setUsablenum(BigDecimal.ZERO);
                }
            }
        }
        order.setOrderDetailList(orderDetaiList);
        return order;
    }

    @Override
    public PageData getOrderCarData(PageMap pageMap) throws Exception {
        String sql = getDataAccessRule("t_sales_order_car", null); //数据权限
        pageMap.setDataSql(sql);
        List<OrderCar> orderList = getSalesOrderCarMapper().getOrderCarList(pageMap);
        for (OrderCar order : orderList) {
            StorageInfo storageInfo = getStorageInfoByID(order.getStorageid());
            if (null != storageInfo) {
                order.setStorageid(storageInfo.getName());
            }
            Personnel indoorPerson = getPersonnelById(order.getIndooruserid());
            if (null != indoorPerson) {
                order.setIndoorusername(indoorPerson.getName());
            }
            DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(order.getSalesdept());
            if (departMent != null) {
                order.setSalesdept(departMent.getName());
            }
            Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(order.getSalesuser());
            if (personnel != null) {
                order.setSalesuser(personnel.getName());
            }
            Map map = new HashMap();
            map.put("id", order.getCustomerid());
            Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(map);
            if (customer != null) {
                order.setCustomerid(customer.getId());
                order.setCustomername(customer.getName());
            }
            map.put("id", order.getHandlerid());
            Contacter contacter = getBaseFilesContacterMapper().getContacterDetail(map);
            if (contacter != null) {
                order.setHandlerid(contacter.getName());
            }
            Map total = getSalesOrderCarMapper().getOrderCarDetailTotal(order.getId());
            if (total != null) {
                if (total.containsKey("taxamount")) {
                    order.setField01(total.get("taxamount").toString());
                }
                if (total.containsKey("notaxamount")) {
                    order.setField02(total.get("notaxamount").toString());
                }
                if (total.containsKey("tax")) {
                    order.setField03(total.get("tax").toString());
                }
            }
        }
        PageData pageData = new PageData(getSalesOrderCarMapper().getOrderCarCount(pageMap), orderList, pageMap);

        List<OrderCar> footer = new ArrayList<OrderCar>();
        OrderCar footerCar = new OrderCar();
        footerCar.setCustomername("选中合计");
        footerCar.setField01("0");
        footerCar.setField02("0");
        footerCar.setField03("0");
        footer.add(footerCar);
        pageData.setFooter(footer);
        return pageData;
    }

    @Override
    public Map auditOrderCar(String id, SysUser sysUser) throws Exception {
        Map result = new HashMap();
        if (null == sysUser) {
            sysUser = getSysUser();
        }
        Map map = new HashMap();
        map.put("id", id);
        OrderCar order = getSalesOrderCarMapper().getOrderCar(map);
        if (null != order && (null == order.getStorageid() || "".equals(order.getStorageid()))) {
            result.put("msg", "请指定仓库！");
            result.put("flag", false);
        } else if ("2".equals(order.getStatus())) { //只有状态为2（保存状态）才可进行审核
            order.setAudituserid(sysUser.getUserid());
            order.setAuditusername(sysUser.getName());
            order.setAudittime(new Date());
            OrderCar order2 = new OrderCar();
            order2.setId(id);
            order2.setStatus("4");
            order2.setAudituserid(sysUser.getUserid());
            order2.setAuditusername(sysUser.getName());
            order2.setAudittime(new Date());
            order2.setBusinessdate(CommonUtils.getTodayDataStr());
            boolean flag = false;
            List<OrderCarDetail> orderDetaiList = getSalesOrderCarMapper().getOrderCarDetailByOrder(id);
            order.setOrderDetailList(orderDetaiList);
            //按商品合计 用来判断仓库该商品可用数量是否足够
            List<OrderCarDetail> orderDetailSumGoodsList = getSalesOrderCarMapper().getOrderCarDetailSumListByGoodsid(id);
            //判断车销仓库中商品数量是否足够
            Map returnmap = storageForSalesService.isGoodsEnoughByOrdercar(order, orderDetailSumGoodsList);
            boolean sendflag = (Boolean) returnmap.get("flag");
            if (sendflag) {
                //自动生成订单 发货通知单
                Map addMap = dispatchBillExtService.addNextBillByOrdercar(order);
                flag = (Boolean) addMap.get("flag");
                if (flag) {
                    flag = getSalesOrderCarMapper().updateOrderCarStatus(order2) > 0;
                }
                result.put("msg", addMap.get("msg"));
                result.put("flag", flag);
            } else {
                String msg = (String) returnmap.get("msg");
                result.put("flag", false);
                result.put("msg", msg);
            }
        }
        return result;
    }

    @Override
    public Map oppauditOrderCar(String id) throws Exception {
        Map result = new HashMap();
        SysUser sysUser = getSysUser();
        Map map = new HashMap();
        map.put("id", id);
        OrderCar order = getSalesOrderCarMapper().getOrderCar(map);
        boolean flag = false;
        String msg = "";
        //只有当天的直营销售单才能反审
        if ("4".equals(order.getStatus()) && order.getBusinessdate().equals(CommonUtils.getTodayDataStr())) {
            OrderCar orderstatus = new OrderCar();
            orderstatus.setId(order.getId());
            orderstatus.setStatus("2");
            int i = getSalesOrderCarMapper().updateOrderCar(orderstatus);
            if (i > 0) {
                Order order1 = getSalesOrderMapper().getOrderBySourceid(order.getId(), "2");
                if (null != order1) {
                    //反审并删除发货回单
                    Receipt receipt = getSalesReceiptMapper().getReceiptBySaleorderid(order1.getId());
                    if (null != receipt) {
                        receiptService.auditReceipt("2", receipt.getId());
                        deleteReceipt(receipt.getId());
                    }
                    //根据订单编号 删除销售发货单
                    storageForSalesService.deleteSaleOutByOrderid(order1.getId());
                    //根据订单编号 删除发货通知单
                    getSalesDispatchBillMapper().deleteDispatchbillByOrderid(order1.getId());
                    getSalesDispatchBillMapper().deleteDispatchbillDetailByOrderid(order1.getId());
                    //删除销售订单
                    getSalesOrderMapper().deleteOrder(order1.getId());
                    getSalesOrderDetailMapper().deleteOrderDetailByOrderId(order1.getId());
                    flag = true;
                }
            }
        } else {
            msg = "直营销售单反审失败，只有当天的单据才能反审。";
        }
        result.put("flag", flag);
        result.put("msg", msg);
        return result;
    }

    @Override
    public Map auditMultiOrderCar(String id) throws Exception {
        int failure = 0;
        int success = 0;
        int noaudit = 0;
        boolean sFlag = false;
        String successid = "";
        String failureid = "";
        SysUser sysUser = getSysUser();
        String msg = "";
        Map map = new HashMap();
        map.put("id", id);
        OrderCar order = getSalesOrderCarMapper().getOrderCar(map);
        if ("2".equals(order.getStatus())) { //只有状态为2（保存状态）才可进行审核
            if (StringUtils.isNotEmpty(order.getStorageid())) {
                OrderCar order2 = new OrderCar();
                order2.setId(id);
                order2.setStatus("4");
                order2.setAudituserid(sysUser.getUserid());
                order2.setAuditusername(sysUser.getName());
                order2.setAudittime(new Date());
                order2.setBusinessdate(CommonUtils.getTodayDataStr());
                boolean flag = false;
                List<OrderCarDetail> orderDetaiList = getSalesOrderCarMapper().getOrderCarDetailByOrder(id);
                order.setOrderDetailList(orderDetaiList);
                //按商品合计 用来判断仓库该商品可用数量是否足够
                List<OrderCarDetail> orderDetailSumGoodsList = getSalesOrderCarMapper().getOrderCarDetailSumListByGoodsid(id);
                //判断车销仓库中商品数量是否足够
                Map returnmap = storageForSalesService.isGoodsEnoughByOrdercar(order, orderDetailSumGoodsList);
                boolean sendflag = (Boolean) returnmap.get("flag");
                if (sendflag) {
                    //自动生成发货通知单
                    Map addMap = dispatchBillExtService.addNextBillByOrdercar(order);
                    flag = (Boolean) addMap.get("flag");
                    String addmsg = (String) addMap.get("msg");
                    if (null != addmsg) {
                        msg += addmsg;
                    }
                    if (flag) {
                        flag = getSalesOrderCarMapper().updateOrderCarStatus(order2) > 0;
                        successid += id + ",";
                        success++;
                    } else {
                        failureid += id + ",";
                        failure++;
                    }
                } else {
                    String msgstr = (String) returnmap.get("msg");
                    if (null != msgstr) {
                        msg += msgstr + ".";
                    }
                    failureid += id + ",";
                    failure++;
                }
            } else {
                msg += "单据：" + id + "未指定仓库。";
                failureid += id + ",";
                failure++;
            }

        } else {
            failureid += id + ",";
            noaudit++;
        }

        Map retmap = new HashMap();
        retmap.put("failure", failure);
        retmap.put("success", success);
        retmap.put("noaudit", noaudit);
        retmap.put("successid", successid);
        retmap.put("failureid", failureid);
        retmap.put("msg", msg);
        return retmap;
    }

    @Override
    public List getOrderCarPrintList(PageMap pageMap) throws Exception {
        String sql = getDataAccessRule("t_storage_saleout", "s"); //数据权限
        pageMap.setDataSql(sql);
        Map condition = pageMap.getCondition();
        String groupby = (String) condition.get("groupby");
        if (!"customer".equals(groupby) && !"storage".equals(groupby)) {
            groupby = "customer";
        }
        String showOrderDetail = (String) condition.get("showOrderDetail");
        condition.put("isPageFlag", "true");
        List<Saleout> list = getSalesOrderCarMapper().getOrderCarPrintListData(pageMap);

        Map detailMap = (Map) CommonUtils.deepCopy(condition);
        //参数用于控制，是否按仓库汇总打印
        if (detailMap.containsKey("groupby")) {
            detailMap.remove("groupby");
        }
        ///选中打印
        if (detailMap.containsKey("saleoutidarr")) {
            detailMap.remove("saleoutidarr");
        }
        String saleoutidarr = (String) condition.get("saleoutidarr");
        if (null == saleoutidarr || "".equals(saleoutidarr.trim())) {
            saleoutidarr = "";
        }
        List<SaleoutDetail> detailList = null;
        for (Saleout item : list) {
            if (StringUtils.isNotEmpty(item.getCustomerid())) {
                Customer customerInfo = getCustomerByID(item.getCustomerid());
                if (null != customerInfo) {
                    item.setCustomername(customerInfo.getName());
                    item.setCustomerInfo(customerInfo);
                }
            }
            if (StringUtils.isNotEmpty(item.getSalesdept())) {
                DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(item.getSalesdept());
                if (null != departMent) {
                    item.setSalesdeptname(departMent.getName());
                }
            }

            if (StringUtils.isNotEmpty(item.getSalesuser())) {
                Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(item.getSalesuser());
                if (null != personnel) {
                    item.setSalesusername(personnel.getName());
                }
            }
            if (StringUtils.isNotEmpty(item.getStorageid())) {
                StorageInfo storageInfo = getStorageInfoByID(item.getStorageid());
                if (null != storageInfo) {
                    item.setStoragename(storageInfo.getName());
                }//选择打印，就无需要模糊查询
                if ("storage".equals(groupby)) {
                    if (!"".equals(saleoutidarr)) {
                        detailMap.put("saleoutidarr", saleoutidarr);
                    }

                    detailMap.put("storageid", item.getStorageid());
                    detailMap.put("businessdate", item.getBusinessdate());
                }
            }
            if (!"storage".equals(groupby)) {
                detailMap.put("saleoutid", item.getId());
            }
            if ("true".equals(showOrderDetail)) {
                detailList = getSaleoutDetailForOrderCarPrint(detailMap);
                item.setSaleoutDetailList(detailList);
            }
        }
        return list;
    }

    @Override
    public PageData showOrderCarPrintListData(PageMap pageMap) throws Exception {
        String sql = getDataAccessRule("t_storage_saleout", "s"); //数据权限
        pageMap.setDataSql(sql);
        Map condition = pageMap.getCondition();
        String groupby = (String) condition.get("groupby");
        if (!"customer".equals(groupby) && !"storage".equals(groupby)) {
            groupby = "customer";
        }
        List<Saleout> list = getSalesOrderCarMapper().getOrderCarPrintListData(pageMap);


        for (Saleout item : list) {
            if (StringUtils.isNotEmpty(item.getCustomerid())) {
                Customer customerInfo = getCustomerByID(item.getCustomerid());
                if (null != customerInfo) {
                    item.setCustomername(customerInfo.getName());
                    item.setCustomerInfo(customerInfo);
                }
            }
            if (StringUtils.isNotEmpty(item.getSalesdept())) {
                DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(item.getSalesdept());
                if (null != departMent) {
                    item.setSalesdeptname(departMent.getName());
                }
            }

            if (StringUtils.isNotEmpty(item.getSalesuser())) {
                Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(item.getSalesuser());
                if (null != personnel) {
                    item.setSalesusername(personnel.getName());
                }
            }
            if (StringUtils.isNotEmpty(item.getStorageid())) {
                StorageInfo storageInfo = getStorageInfoByID(item.getStorageid());
                if (null != storageInfo) {
                    item.setStoragename(storageInfo.getName());
                }
            }
            if (StringUtils.isNotEmpty(item.getIndooruserid())) {
                Personnel indoorPerson = getPersonnelById(item.getIndooruserid());
                if (null != indoorPerson) {
                    item.setIndoorusername(indoorPerson.getName());
                }
            }
        }
        int icount = getSalesOrderCarMapper().getOrderCarPrintListDataCount(pageMap);
        PageData pageData = new PageData(icount, list, pageMap);
        return pageData;
    }

    private List getSaleoutDetailForOrderCarPrint(Map map) throws Exception {
        List<SaleoutDetail> list = getSalesOrderCarMapper().getOrderCarPrintDetailListData(map);
        for (SaleoutDetail saleoutDetail : list) {
            StorageInfo storageInfo = getStorageInfoByID(saleoutDetail.getStorageid());
            if (null != storageInfo) {
                saleoutDetail.setStoragename(storageInfo.getName());
            }
            StorageLocation storageLocation = getStorageLocation(saleoutDetail.getStoragelocationid());
            if (null != storageLocation) {
                saleoutDetail.setStoragelocationname(storageLocation.getName());
            }
            TaxType taxType = getTaxType(saleoutDetail.getTaxtype());
            if (null != taxType) {
                saleoutDetail.setTaxtypename(taxType.getName());
            }
            GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(getGoodsInfoByID(saleoutDetail.getGoodsid()));
            //折扣显示处理
            if ("1".equals(saleoutDetail.getIsdiscount())) {
                goodsInfo.setName("(折扣)" + goodsInfo.getName());
                goodsInfo.setBarcode("");
                goodsInfo.setBoxnum(null);
                saleoutDetail.setUnitnum(null);
                saleoutDetail.setAuxnumdetail("");
                saleoutDetail.setTaxprice(null);
                saleoutDetail.setUnitname("");
                if ("1".equals(saleoutDetail.getIsbranddiscount())) {
                    saleoutDetail.setGoodsid("");
                    goodsInfo.setName("(折扣)" + goodsInfo.getBrandName());
                    saleoutDetail.setIsdiscount("2");
                }
            }
            saleoutDetail.setGoodsInfo(goodsInfo);
        }
        return list;
    }

    @Override
    public Map deleteMultiOrderCar(String ids) throws Exception {
        Map returnMap = new HashMap();
        boolean flag = false;
        int failure = 0;
        int success = 0;
        if (StringUtils.isNotEmpty(ids)) {
            String[] idArr = ids.split(",");
            for (String id : idArr) {
                Map map = new HashMap();
                map.put("id", id);
                OrderCar order = getSalesOrderCarMapper().getOrderCar(map);
                if (null != order && "2".equals(order.getStatus())) { //只有状态为2（保存状态）才可进行删除
                    OrderCar order2 = new OrderCar();
                    order2.setId(order.getId());
                    //删除标志
                    order2.setStatus("9");
                    boolean delflag = getSalesOrderCarMapper().updateOrderCarStatus(order2) > 0;
                    if (delflag) {
                        success++;
                    } else {
                        failure++;
                    }
                } else {
                    failure++;
                }
            }
            flag = true;
        }
        returnMap.put("flag", flag);
        returnMap.put("failure", failure);
        returnMap.put("success", success);
        return returnMap;
    }

    @Override
    public Map updateOrderCarToDemand(String id) throws Exception {
        Map map = new HashMap();
        map.put("id", id);
        boolean flag = false;
        String msg = "";
        OrderCar order = getSalesOrderCarMapper().getOrderCar(map);
        if (null != order && "2".equals(order.getStatus())) {
            List<OrderCarDetail> orderDetaiList = getSalesOrderCarMapper().getOrderCarDetailByOrder(id);
            order.setOrderDetailList(orderDetaiList);
            boolean addflag = addOrderCarToDemand(order);
            if (addflag) {
                int i = getSalesOrderCarMapper().deleteOrderCar(id);
                getSalesOrderCarMapper().deleteOrderCarDetailByOrderId(id);
                flag = i > 0;
                msg = "生成要货申请单:" + order.getId();
            }
        }
        Map returnMap = new HashMap();
        returnMap.put("flag", flag);
        returnMap.put("msg", msg);
        return returnMap;
    }

    /**
     * 直营销售单转换成要货单
     *
     * @return
     * @throws Exception
     * @author chenwei
     * @date Apr 9, 2014
     */
    public boolean addOrderCarToDemand(OrderCar order) throws Exception {
        Demand demand = new Demand();
        demand.setId(order.getId());
        demand.setStatus("0");
        demand.setBusinessdate(order.getBusinessdate());
        demand.setCustomerid(order.getCustomerid());
        demand.setCustomersort(order.getCustomersort());
        demand.setHandlerid(order.getHandlerid());
        demand.setSalesarea(order.getSalesarea());
        demand.setSalesdept(order.getSalesdept());
        demand.setSalesuser(order.getSalesuser());
        demand.setAdddeptid(order.getAdddeptid());
        demand.setAdddeptname(order.getAdddeptname());
        demand.setAdduserid(order.getAdduserid());
        demand.setAddusername(order.getAddusername());
        demand.setRemark(order.getRemark());
        SysUser sysUser = getSalesIndoorSysUserByCustomeridForPhone(demand.getCustomerid());
        if (sysUser != null) {
            demand.setIndooruserid(sysUser.getPersonnelid());
        }
        List detailList = new ArrayList();
        List<OrderCarDetail> orderCarDetailList = order.getOrderDetailList();
        for (OrderCarDetail orderCarDetail : orderCarDetailList) {
            DemandDetail detail = new DemandDetail();
            detail.setOrderid(demand.getId());
            detail.setGoodsid(orderCarDetail.getGoodsid());
            GoodsInfo goodsInfo = getAllGoodsInfoByID(orderCarDetail.getGoodsid());
            if (null != goodsInfo) {
                detail.setGoodssort(goodsInfo.getDefaultsort());
                detail.setBrandid(orderCarDetail.getBrandid());
                //品牌部门
                Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                if (null != brand) {
                    detail.setBranddept(brand.getDeptid());
                }
                detail.setSupplierid(goodsInfo.getDefaultsupplier());
                //根据客户编号和品牌编号 获取品牌业务员
                detail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), demand.getCustomerid()));
                //厂家业务员
                detail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), order.getCustomerid()));
                detail.setUnitid(goodsInfo.getMainunit());
                detail.setUnitname(goodsInfo.getMainunitName());
                detail.setUnitnum(orderCarDetail.getUnitnum());
                detail.setAuxunitid(orderCarDetail.getAuxunitid());
                detail.setAuxunitname(orderCarDetail.getAuxunitname());
                detail.setAuxnumdetail(orderCarDetail.getAuxnumdetail());
                detail.setAuxnum(orderCarDetail.getAuxnum());
                detail.setOvernum(orderCarDetail.getOvernum());
                detail.setTotalbox(orderCarDetail.getTotalbox());

                detail.setTaxprice(orderCarDetail.getTaxprice());
                detail.setTaxtype(goodsInfo.getDefaulttaxtype());
                detail.setTaxamount(orderCarDetail.getTaxamount());
                detail.setNotaxamount(orderCarDetail.getNotaxamount());
                detail.setTax(orderCarDetail.getTax());
                detail.setNotaxprice(orderCarDetail.getNotaxprice());
                detail.setCostprice(getGoodsCostprice(order.getStorageid(),goodsInfo));
                detail.setOldprice(orderCarDetail.getTaxprice());
                detail.setRemark(orderCarDetail.getRemark());
                getSalesDemandMapper().addDemandDetail(detail);
            }
        }
        return getSalesDemandMapper().addDemand(demand) > 0;
    }

    @Override
    public boolean checkOrderCarRepeat(String id) throws Exception {
        String sysparm = getSysParamValue("checkOrderRepeatDays");
        if (StringUtils.isEmpty(sysparm) || !StringUtils.isNumeric(sysparm)) {
            sysparm = "3";
        }
        Map querymap = new HashMap();
        querymap.put("id", id);
        OrderCar orderCar = getSalesOrderCarMapper().getOrderCar(querymap);
        if (null != orderCar) {
            Map map = getSalesOrderCarMapper().checkOrderCarRepeat(id, orderCar.getCustomerid(), sysparm);
            if (null == map) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public boolean isHaveOrderCarByKeyid(String keyid) throws Exception {
        OrderCar orderCar = getSalesOrderCarMapper().getOrderCarByKeyid(keyid);
        if (null != orderCar) {
            return true;
        }
        return false;
    }

    /**
     * 根据keyid 获取零售单单据编号
     *
     * @param keyid
     * @return
     * @throws Exception
     */
    @Override
    public String getOrderCarBillidByKeyid(String keyid) throws Exception {
        OrderCar orderCar = getSalesOrderCarMapper().getOrderCarByKeyid(keyid);
        if (null != orderCar) {
            return orderCar.getId();
        }
        return null;
    }

}

