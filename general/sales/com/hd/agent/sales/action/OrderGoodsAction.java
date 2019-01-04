package com.hd.agent.sales.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.*;
import com.hd.agent.sales.model.*;
import com.hd.agent.sales.service.IDemandService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 销售订货单
 * Created by luoq on 2017/10/16.
 */

public class OrderGoodsAction extends BaseSalesAction{
    private OrderGoods orderGoods;

    public OrderGoods getOrderGoods() {
        return orderGoods;
    }

    public void setOrderGoods(OrderGoods orderGoods) {
        this.orderGoods = orderGoods;
    }

    private IDemandService salesDemandService;

    @Override
    public IDemandService getSalesDemandService() {
        return salesDemandService;
    }

    @Override
    public void setSalesDemandService(IDemandService salesDemandService) {
        this.salesDemandService = salesDemandService;
    }

    /**
     * 销售订货单列表页面
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Oct 16, 2017
     */
    public String orderGoodsListPage() {
        return SUCCESS;
    }

    public String getOrderGoodsList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = orderGoodsService.getOrderGoodsList(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

    /**
     * 销售订货单页面
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Oct 16, 2017
     */
    public String orderGoodsPage() throws Exception {
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        request.setAttribute("id", id);
        request.setAttribute("type", type);
        request.setAttribute("today", CommonUtils.getTodayDataStr());
        // 判断销售管理中商品是否允许重复1允许0不允许
        String isrepeat = getSysParamValue("IsSalesGoodsRepeat");
        request.setAttribute("isrepeat", isrepeat);

        // 发货打印是否需要发货单审核通过才能打印
        String fHPrintAfterSaleOutAudit = getSysParamValue("saleOrderFHPrintAfterSaleOutAudit");
        if (null == fHPrintAfterSaleOutAudit || "".equals(fHPrintAfterSaleOutAudit.trim())) {
            fHPrintAfterSaleOutAudit = "0";
        }
        request.setAttribute("fHPrintAfterSaleOutAudit", fHPrintAfterSaleOutAudit.trim());

        // 发货单打印是否显示打印选项
        String showSaleoutPrintOptions = getSysParamValue("showSaleoutPrintOptions");
        if (null == showSaleoutPrintOptions || "".equals(showSaleoutPrintOptions.trim())) {
            showSaleoutPrintOptions = "0";
        }
        request.setAttribute("showSaleoutPrintOptions", showSaleoutPrintOptions.trim());
        request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
        return SUCCESS;
    }

    /**
     * 销售订货单新增页面
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Oct 16, 2017
     */
    public String orderGoodsAddPage() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        request.setAttribute("settletype", getSettlementListData());
        request.setAttribute("paytype", getPaymentListData());
        request.setAttribute("salesDept",getDeptListByOpertype("4"));
        request.setAttribute("date", dateFormat.format(calendar.getTime()));
        request.setAttribute("autoCreate", isAutoCreate("t_sales_goodsorder"));
        request.setAttribute("userName", getSysUser().getName());
        // 系统参数 控制销售订单 默认排序方式
        String orderDetailSortGoods = getSysParamValue("OrderDetailSortGoods");
        if (StringUtils.isEmpty(orderDetailSortGoods)) {
            orderDetailSortGoods = "0";
        }
        request.setAttribute("orderDetailSortGoods", orderDetailSortGoods);
        // 系统参数 控制销售订单 仓库是否必填
        String isOrderStorageSelect = getSysParamValue("IsOrderStorageSelect");
        if (StringUtils.isEmpty(isOrderStorageSelect)) {
            isOrderStorageSelect = "0";
        }
        request.setAttribute("isOrderStorageSelect", isOrderStorageSelect);

        String OpenDeptStorage = getSysParamValue("OpenDeptStorage");
        if ("1".equals(OpenDeptStorage)) {
            SysUser sysUser = getSysUser();
            DepartMent departMent = getBaseDepartMentService().showDepartMentInfo(sysUser.getDepartmentid());
            if (null != departMent) {
                request.setAttribute("defaultStorageid", departMent.getStorageid());
            } else {
                request.setAttribute("defaultStorageid", "");
            }
        } else {
            request.setAttribute("defaultStorageid", "");
        }

        // 发货打印是否需要发货单审核通过才能打印
        String fHPrintAfterSaleOutAudit = getSysParamValue("saleOrderFHPrintAfterSaleOutAudit");
        if (null == fHPrintAfterSaleOutAudit || "".equals(fHPrintAfterSaleOutAudit.trim())) {
            fHPrintAfterSaleOutAudit = "0";
        }
        request.setAttribute("fHPrintAfterSaleOutAudit", fHPrintAfterSaleOutAudit.trim());

        // 发货单打印是否显示打印选项
        String showSaleoutPrintOptions = getSysParamValue("showSaleoutPrintOptions");
        if (null == showSaleoutPrintOptions || "".equals(showSaleoutPrintOptions.trim())) {
            showSaleoutPrintOptions = "0";
        }
        request.setAttribute("showSaleoutPrintOptions", showSaleoutPrintOptions.trim());

        //读取保存成功后跳转界面的cookie
        Cookie[] cookies = request.getCookies();
        String addCheck = "";//是否继续添加订单
        for (int i = 0; i < cookies.length; i++) {
            Cookie c = cookies[i];
            if (c.getName().equalsIgnoreCase("addCheck")) {
                addCheck = c.getValue();
            }else{
                continue;
            }
        }
        if(addCheck != ""){
            request.setAttribute("isadd",addCheck);
        }
        return SUCCESS;
    }

    public String showOrderGoodsDetailAddPage() throws Exception {
        String customerId = request.getParameter("cid");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.DATE, 2);
        Map colMap = getEditAccessColumn("t_sales_goodsorder_detail");
        request.setAttribute("colMap", colMap);
        request.setAttribute("customerId", customerId);
        request.setAttribute("date", dateFormat.format(calendar.getTime()));
        request.setAttribute("presentByZero",getSysParamValue("presentByZero"));
        request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
        String isOrderGoodsUseTax=getSysParamValue("isOrderGoodsUseTax");
        if("0".equals(isOrderGoodsUseTax)){
            return "notaxsuccess";
        }
        return SUCCESS;
    }

    /**
     * 添加销售订货单
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Oct 16, 2017
     */
    @UserOperateLog(key = "OrderGoods", type = 2)
    public String addOrderGoods() throws Exception{
        String addType = request.getParameter("addType");
        orderGoods.setStatus("2");

        String orderDetailJson = request.getParameter("goodsjson");
        List<OrderGoodsDetail> orderDetailList = JSONUtils.jsonStrToList(orderDetailJson, new OrderGoodsDetail());
        orderGoods.setOrderDetailList(orderDetailList);
        // 判断是否审核
        String saveaudit = request.getParameter("saveaudit");
        Map returnmap = orderGoodsService.addOrderGoods(orderGoods, saveaudit);
        Map map = new HashMap();
        boolean flag = (Boolean) returnmap.get("flag");
        if ("saveaudit".equals(saveaudit) && flag) {
            boolean auditflag = (Boolean) returnmap.get("auditflag");
            String msg = (String) returnmap.get("msg");
            String billId = (String) returnmap.get("billId");
            map.put("auditflag", auditflag);
            map.put("msg", msg);
            map.put("billId", billId);
            if (auditflag) {
                addLog("销售订货单新增保存审核 编号：" + orderGoods.getId(), flag);
            } else {
                addLog("销售订货单新增 编号：" + orderGoods.getId(), flag);
            }

        } else {

            addLog("销售订货单新增 编号：" + orderGoods.getId(), flag);
        }
        map.put("flag", flag);
        map.put("backid", orderGoods.getId());
        map.put("type", "add");
        addJSONObject(map);

        return SUCCESS;
    }

    /**
     * 销售订货单修改页面
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Oct 16, 2017
     */
    public String orderGoodsEditPage() throws Exception {
        String id = request.getParameter("id");
        OrderGoods orderGoods = orderGoodsService.getOrderGoods(id);
        //显示修改页面时，给数据加锁
       // boolean flag = lockData("t_sales_goodsorder", id);
        // 系统参数 控制销售订单 仓库是否必填
        String isOrderStorageSelect = getSysParamValue("IsOrderStorageSelect");
        if (StringUtils.isEmpty(isOrderStorageSelect)) {
            isOrderStorageSelect = "0";
        }
        request.setAttribute("isOrderStorageSelect", isOrderStorageSelect);

        if (null != orderGoods) {
            List statusList = getBaseSysCodeService().showSysCodeListByType("status");
            List<OrderGoodsDetail> detailList = orderGoods.getOrderDetailList();
            String jsonStr = JSONUtils.listToJsonStr(detailList);
//            if (null != orderGoods.getLackList()) {
//                String laskGoodsjsonStr = JSONUtils.listToJsonStr(orderGoods.getLackList());
//                request.setAttribute("laskJson", laskGoodsjsonStr);
//            }
            request.setAttribute("orderGoods", orderGoods);
            request.setAttribute("goodsJson", jsonStr);
            request.setAttribute("statusList", statusList);


            String printlimit = getPrintLimitInfo();
            request.setAttribute("printlimit", printlimit);

            // 发货打印是否需要发货单审核通过才能打印
            String fHPrintAfterSaleOutAudit = getSysParamValue("saleOrderFHPrintAfterSaleOutAudit");
            if (null == fHPrintAfterSaleOutAudit || "".equals(fHPrintAfterSaleOutAudit.trim())) {
                fHPrintAfterSaleOutAudit = "0";
            }
            request.setAttribute("fHPrintAfterSaleOutAudit", fHPrintAfterSaleOutAudit.trim());

            // 发货单打印是否显示打印选项
            String showSaleoutPrintOptions = getSysParamValue("showSaleoutPrintOptions");
            if (null == showSaleoutPrintOptions || "".equals(showSaleoutPrintOptions.trim())) {
                showSaleoutPrintOptions = "0";
            }
            request.setAttribute("showSaleoutPrintOptions", showSaleoutPrintOptions.trim());

            if ("1".equals(orderGoods.getStatus()) || "2".equals(orderGoods.getStatus()) || "6".equals(orderGoods.getStatus())) {
                // 加锁
                lockData("t_sales_goodsorder", orderGoods.getId());
                return "editSuccess";
            } else {
                return "viewSuccess";
            }
        } else {
            return "addSuccess";
        }
    }

    /**
     * 销售订货单查看页面
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Oct 16, 2017
     */
    public String orderGoodsViewPage() throws Exception{
        String id = request.getParameter("id");
        OrderGoods orderGoods = orderGoodsService.getOrderGoods(id);
        if (orderGoods == null) {
            return SUCCESS;
        }
        List statusList = getBaseSysCodeService().showSysCodeListByType("status");
        String jsonStr = JSONUtils.listToJsonStr(orderGoods.getOrderDetailList());
        request.setAttribute("settletype", getSettlementListData());
        request.setAttribute("paytype", getPaymentListData());
        request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
        request.setAttribute("orderGoods", orderGoods);
        request.setAttribute("goodsJson", jsonStr);
        request.setAttribute("statusList", statusList);
        request.setAttribute("isLocked", lockData("t_sales_goodsorder", orderGoods.getId()));

        String printlimit = getPrintLimitInfo();
        request.setAttribute("printlimit", printlimit);


        // 发货打印是否需要发货单审核通过才能打印
        String fHPrintAfterSaleOutAudit = getSysParamValue("saleOrderFHPrintAfterSaleOutAudit");
        if (null == fHPrintAfterSaleOutAudit || "".equals(fHPrintAfterSaleOutAudit.trim())) {
            fHPrintAfterSaleOutAudit = "0";
        }
        request.setAttribute("fHPrintAfterSaleOutAudit", fHPrintAfterSaleOutAudit.trim());

        // 发货单打印是否显示打印选项
        String showSaleoutPrintOptions = getSysParamValue("showSaleoutPrintOptions");
        if (null == showSaleoutPrintOptions || "".equals(showSaleoutPrintOptions.trim())) {
            showSaleoutPrintOptions = "0";
        }
        request.setAttribute("showSaleoutPrintOptions", showSaleoutPrintOptions.trim());
        return SUCCESS;
    }

    /**
     * 修改销售订货单
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Oct 17, 2017
     */
    @UserOperateLog(key = "OrderGoods", type = 3)
    public String updateOrderGoods() throws Exception{
        boolean lock = isLock("t_sales_goodsorder", orderGoods.getId()); // 判断锁定并解锁
        if (lock) { // 被锁定不能进行修改
            addJSONObject("lock", true);
            addLog("销售订货单 编码：" + orderGoods.getId() + "互斥，操作", false);
            return SUCCESS;
        }
        String addType = request.getParameter("addType");
        if ("1".equals(orderGoods.getStatus())) {
            if ("real".equals(addType)) {
                orderGoods.setStatus("2");
            }
        }
        Map map = new HashMap();

        SysUser sysUser = getSysUser();
        orderGoods.setModifyuserid(sysUser.getUserid());
        orderGoods.setModifyusername(sysUser.getName());
        String orderDetailJson = request.getParameter("goodsjson");
        List<OrderGoodsDetail> orderDetailList = JSONUtils.jsonStrToList(orderDetailJson, new OrderGoodsDetail());
        orderGoods.setOrderDetailList(orderDetailList);
        // 配置库存后 缺货商品
        String lackGoodsjson = request.getParameter("lackGoodsjson");
        if (StringUtils.isNotEmpty(lackGoodsjson)) {
            List<OrderGoodsDetail> lackGoodsList = JSONUtils.jsonStrToList(lackGoodsjson, new OrderGoodsDetail());
            orderGoods.setLackList(lackGoodsList);
        }
        // 判断是否审核
        String saveaudit = request.getParameter("saveaudit");
        Map returnmap = orderGoodsService.updateOrderGoods(orderGoods, saveaudit);
        boolean flag = (Boolean) returnmap.get("flag");
        String msg = "";
        if ("saveaudit".equals(saveaudit) && flag) {
            boolean auditflag = (Boolean) returnmap.get("auditflag");
            msg = (String) returnmap.get("msg");
            String billId = (String) returnmap.get("billId");
            map.put("auditflag", auditflag);
            map.put("msg", msg);
            map.put("billId", billId);
            addLog("销售订货单保存审核 编号：" + orderGoods.getId() + "," + msg, auditflag);
        } else {
            if(returnmap.containsKey("msg")){
                msg = (String) returnmap.get("msg");
                addLog("销售订货单修改 编号：" + orderGoods.getId() + "," + msg , flag);
            }else{
                addLog("销售订货单修改 编号：" + orderGoods.getId() , flag);
            }
        }
        map.put("flag", flag);
        map.put("msg", msg);
        map.put("backid", orderGoods.getId());
        addJSONObject(map);
        // 解锁数据
        isLockEdit("t_sales_goodsorder", orderGoods.getId()); // 判断锁定并解锁
        return SUCCESS;
    }

    /**
     * 判断订货单能否审核
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Oct 17, 2017
     */
    public String canAuditOrderGoods() throws Exception{
        String ids = request.getParameter("ids");
        // 判断要货单据在最近几天内是否重复
        boolean flag = true;
        String msg = "";
        if (null != ids && !"".equals(ids)) {
            String[] idarr = ids.split(",");
            for (String id : idarr) {
                Map returnMap = orderGoodsService.checkOrderGoodsAudit(id);
                boolean auditflag = (Boolean) returnMap.get("flag");
                if (!auditflag) {
                    String returnMsg = (String) returnMap.get("msg");
                    msg += returnMsg + "</br>";
                    flag = false;
                }
            }
        }
        Map map = new HashMap();
        map.put("msg", msg);
        map.put("flag", flag);
        addJSONObject(map);
        return "success";
    }

    public String orderGoodsDiscountEditPage(){
        return SUCCESS;
    }

    public String orderGoodsBrandDiscountEditPage(){
        return SUCCESS;
    }

    /**
     * 销售订单商品详细信息修改页面
     *
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date May 14, 2013
     */
    public String orderGoodsDetailEditPage() throws Exception {
        String customerId = request.getParameter("cid");
        Map colMap = getEditAccessColumn("t_sales_goodsorder_detail");
        request.setAttribute("colMap", colMap);
        request.setAttribute("customerId", customerId);

        String goodsid = request.getParameter("goodsid");
        GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
        if (null != goodsInfo) {
            request.setAttribute("isbatch", goodsInfo.getIsbatch());
        } else {
            request.setAttribute("isbatch", "0");
        }
        request.setAttribute("presentByZero",getSysParamValue("presentByZero"));
        request.setAttribute("goodsid", goodsid);
        request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
        String isOrderGoodsUseTax=getSysParamValue("isOrderGoodsUseTax");
        if("0".equals(isOrderGoodsUseTax)){
            return "notaxsuccess";
        }
        return SUCCESS;
    }

    /**
     * 审核订货单
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Oct 17, 2017
     */
    public String auditOrderGoods() throws Exception{
        String type = request.getParameter("type"); // 1为审核2为反审
        String id = request.getParameter("id");
        String model = request.getParameter("model");
        Map result = orderGoodsService.auditOrderGoods(type, id, model);
        addJSONObject(result);
        if ("1".equals(type)) {
            addLog("销售订货单审核 编号：" + id, result);
        } else {
            addLog("销售订货单反审 编号：" + id, result);
        }
        return SUCCESS;
    }

    /**
     * 删除销售订货单
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Oct 17, 2017
     */
    @UserOperateLog(key = "OrderGoods", type = 4)
    public String deleteOrderGoods() throws Exception{
        String id = request.getParameter("id");
        boolean delFlag = canTableDataDelete("t_sales_order_detail", id); // 判断是否被引用，被引用则无法删除。
        if (!delFlag) {
            addJSONObject("delFlag", true);
            return SUCCESS;
        }
        boolean flag = orderGoodsService.deleteOrderGoods(id);
        addJSONObject("flag", flag);
        addLog("销售订单删除 编号：" + id, flag);
        return SUCCESS;
    }

    /**
     * 销售订货单批量审核
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Oct 17, 2017
     */
    public String auditMultiOrderGoods() throws Exception {
        String ids = request.getParameter("ids");
        Map map = orderGoodsService.auditMultiOrderGoods(ids);
        addJSONObject(map);
        String msg = "";
        if (map.containsKey("msg")) {
            msg = "失败信息:" + (String) map.get("msg");
        }
        addLog("销售订货单批量审核 编号：" + ids + ";" + msg, map);
        return SUCCESS;
    }

    public String supplierAuditOrderGoodsMuti() throws Exception{
        String type = request.getParameter("type"); // 1为审核2为反审
        String ids = request.getParameter("ids");
        String model = request.getParameter("model");
        String billids = "", remsg = "", sucids = "", unsucids = "";
        String[] idArr = ids.split(",");
        for (String id : idArr) {
            Map result = orderGoodsService.auditOrderGoods(type, id, model);
            if (result.get("flag").equals(true)) {
                if (StringUtils.isEmpty(sucids)) {
                    sucids = id;
                } else {
                    sucids += "," + id;
                }
                if (result.containsKey("billid")) {
                    if (StringUtils.isEmpty(billids)) {
                        billids = (String) result.get("billid");
                    } else {
                        billids += "," + (String) result.get("billid");
                    }
                }
            } else {
                if (StringUtils.isEmpty(unsucids)) {
                    unsucids = id;
                } else {
                    unsucids += "," + id;
                }
                if (StringUtils.isEmpty(remsg)) {
                    remsg = (String) result.get("msg");
                } else {
                    remsg += (String) result.get("msg");
                }
            }
        }
        Map map = new HashMap();
        map.put("billids", billids);
        if (!"".equals(billids)) {
            remsg = "生成单据:" + billids + ";</br>" + remsg;
        }
        map.put("msg", remsg);
        map.put("sucids", sucids);
        map.put("unsucids", unsucids);
        addJSONObject(map);
        if (StringUtils.isNotEmpty(sucids)) {
            addLog("销售订货单批量超级审核 编号：" + sucids, true);
        } else {
            addLog("销售订货单批量超级审核 编号：" + unsucids, false);
        }
        return SUCCESS;
    }

    /**
     * 销售订货单生成销售订单页面
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Oct 17, 2017
     */
    public String showGoodsToOrderPage() throws Exception{
        String id = request.getParameter("id");
        OrderGoods orderGoods = orderGoodsService.getOrderGoods(id);
        List<OrderGoodsDetail> orderGoodsDetailList=orderGoodsService.showGoodsToOrderData(id);
//        List<OrderGoodsDetail> orderGoodsDetailList=orderGoods.getOrderDetailList();
//        for(OrderGoodsDetail orderGoodsDetail:orderGoodsDetailList){
//            orderGoodsDetail.setUnitnum(orderGoodsDetail.getNotorderunitnum());
//        }
        String jsonStr = JSONUtils.listToJsonStr(orderGoodsDetailList);
        request.setAttribute("goodsJson", jsonStr);
        return SUCCESS;
    }

    /**
     * 从销售订货单生成销售订单
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Oct 17, 2017
     */
    @UserOperateLog(key = "OrderGoods", type = 3)
    public String addOrderByOrderGoodsBill() throws Exception{
        String id = request.getParameter("id");
        String orderDetailJson = request.getParameter("goodsJson");
        List<OrderDetail> orderDetailList = JSONUtils.jsonStrToList(orderDetailJson, new OrderDetail());
        Map map=orderGoodsService.addOrderByOrderGoodsBill(id,orderDetailList);
        addJSONObject(map);
        addLog("根据订货单"+id+"生成销售订单", map);
        return SUCCESS;
    }


    /**
     * 销售订单页面里根据订货单生成销售回单页面
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Oct 18, 2017
     */
    public String showOrderAddByOrderGoodsPage() {
        return SUCCESS;
    }

    /**
     * 获取可以生成订单的订货单数据
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Oct 18, 2017
     */
    public String getOrderGoodsListForAddOrder() throws Exception{
        Map map=CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData=orderGoodsService.getOrderGoodsListForAddOrder(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

    /**
     * 销售订货单生成销售订单页面
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Oct 17, 2017
     */
    public String showOrderGoodsToOrderDataPage() throws Exception{
        String id = request.getParameter("id");
        OrderGoods orderGoods = orderGoodsService.getOrderGoods(id);
        List<OrderGoodsDetail> orderGoodsDetailList=orderGoodsService.showGoodsToOrderData(id);
//        for(OrderGoodsDetail orderGoodsDetail:orderGoodsDetailList){
//            orderGoodsDetail.setUnitnum(orderGoodsDetail.getNotorderunitnum());
//        }
        String jsonStr = JSONUtils.listToJsonStr(orderGoodsDetailList);
        // 加锁
        lockData("t_sales_goodsorder", orderGoods.getId());
        request.setAttribute("goodsJson", jsonStr);
        request.setAttribute("orderGoods",orderGoods);
        return SUCCESS;
    }


    /**
     * 要货单关联订货单页面
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Oct 18, 2017
     */
    public String showDemandAddOrderGoodsPage() throws Exception{
        String orderid= request.getParameter("orderid");
        Demand demand = salesDemandService.getDemand(orderid);
        request.setAttribute("demand",demand);
        return SUCCESS;
    }

    /**
     * 获取符合要货单关联条件的订货单数据
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Oct 19, 2017
     */
    public String getDemandAddOrderGoodsData() throws Exception{
        String id=request.getParameter("id");
        Demand demand= salesDemandService.getDemand(id);
        Map map=CommonUtils.changeMap(request.getParameterMap());
        map.put("id",id);
        map.put("customerid",demand.getCustomerid());
        pageMap.setCondition(map);
        PageData pageData=orderGoodsService.getDemandAddOrderGoodsData(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }
    /**
     * 作废或取消作废订单
     *
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Dec 26, 2013
     */
    @UserOperateLog(key = "SaleOrder", type = 3)
    public String doInvalidOrderGoods() throws Exception {
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        Map map = orderGoodsService.doInvalidOrderGoods(id, type);
        addJSONObject(map);
        if ("1".equals(type)) {
            addLog("作废订货单：" + id, (Boolean) map.get("flag"));
        } else if ("2".equals(type)) {
            addLog("取消作废订货单：" + id, (Boolean) map.get("flag"));
        }
        return SUCCESS;
    }

    /**
     * 判断订货单有没有关联过销售订单或者要货单
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Mar 14, 2018
     */
    public String checkInvalidOrderGoods() {
        String id=request.getParameter("id");
        Map map = orderGoodsService.checkInvalidOrderGoods(id);
        addJSONObject(map);
        return SUCCESS;
    }



    /**
     * 导出销售订货单
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Jan 05, 2018
     */
    public void exportOrderGoodsData() throws Exception{
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        String state = request.getParameter("state");
        if (StringUtils.isNotEmpty(state)) {
            map.put("state", state);
        }
        String title = "";
        if (map.containsKey("excelTitle")) {
            title = map.get("excelTitle").toString();
        } else {
            title = "list";
        }
        if (StringUtils.isEmpty(title)) {
            title = "list";
        }
        if (map.containsKey("ordersql") && null != map.get("ordersql")) {
            pageMap.setOrderSql((String) map.get("ordersql"));
        }
        pageMap.setCondition(map);

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("id", "编号");
        firstMap.put("businessdate", "业务日期");
        firstMap.put("customerid", "客户编码");
        firstMap.put("customername", "客户名称");
        firstMap.put("salesusername","客户业务员");
        firstMap.put("salesdept", "销售部门编码");
        firstMap.put("salesdeptname", "销售部门名称");
        firstMap.put("statusname", "状态");
//        firstMap.put("field01", "金额");
//        firstMap.put("notordertaxamount", "未生成金额");
//        firstMap.put("indoorusername", "销售内勤");
        firstMap.put("ladingbill", "提货券");
        firstMap.put("goodsid", "商品编码");
        firstMap.put("customergoodsid", "店内码");
        firstMap.put("goodsname", "商品名称");
        firstMap.put("spell", "商品助记符");
        firstMap.put("barcode", "商品条形码");
        firstMap.put("boxnum", "箱装量");
        firstMap.put("model","规格型号");
        firstMap.put("volume","体积(立方米)");
        firstMap.put("grossweight","重量（千克）");
        firstMap.put("unitnum", "数量");
        firstMap.put("auxnumdetail", "辅数量");
        firstMap.put("taxprice", "单价");
        firstMap.put("taxamount", "金额");
        firstMap.put("orderunitnum", "已生成订单数");
        firstMap.put("notorderunitnum", "未生成订单数");

        firstMap.put("addusername","制单人");
        firstMap.put("remark1", "备注");
        result.add(firstMap);

        List<ExportOrderGoods> list=orderGoodsService.getOrderGoodsExportData(pageMap);
        if (list.size() != 0) {
            for (ExportOrderGoods exportOrderGoods : list) {
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2 = PropertyUtils.describe(exportOrderGoods);
                for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
                    if (map2.containsKey(fentry.getKey())) { // 如果记录中包含该Key，则取该Key的Value
                        for (Map.Entry<String, Object> entry : map2.entrySet()) {
                            if (fentry.getKey().equals(entry.getKey())) {
                                objectCastToRetMap(retMap, entry);
                            }
                        }
                    } else {
                        retMap.put(fentry.getKey(), "");
                    }
                }
                result.add(retMap);
            }
        }
        ExcelUtils.exportExcel(result, title);
    }
}
