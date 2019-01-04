/**
 * @(#)OrderAction.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 3, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.*;
import com.hd.agent.sales.model.*;
import com.hd.agent.sales.service.impl.ext.OrderExtServiceImpl;
import com.hd.agent.storage.model.StorageSummary;
import com.hd.agent.system.model.SysParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 销售订单
 * 
 * @author zhengziyong
 */
public class OrderAction extends BaseSalesAction {

	private Order saleorder;

	public Order getSaleorder() {
		return saleorder;
	}

	public void setSaleorder(Order saleorder) {
		this.saleorder = saleorder;
	}

	/**
	 * 显示订单新增页面
	 * 
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 17, 2013
	 */
    public String showOrderAddPage() throws Exception {
        return "success";
	}

	/**
	 * 销售订单页面
	 * 
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 3, 2013
	 */
	public String orderPage() throws Exception {
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		request.setAttribute("id", id);
		request.setAttribute("type", type);
		request.setAttribute("today", CommonUtils.getTodayDataStr());
		// 判断销售管理中商品是否允许重复1允许0不允许
		String isrepeat = getSysParamValue("IsSalesGoodsRepeat");
		request.setAttribute("isrepeat", isrepeat);
        //鼠标悬停处是否显示商品明细的历史销售价
        String priceFloat = getSysParamValue("orderPageShowHistrooyPriceFloat");
        request.setAttribute("priceFloat", priceFloat);

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
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		request.setAttribute("CustomerNewBuyRemind", getSysParamValue("CustomerNewBuyRemind"));
		return SUCCESS;
	}

	/**
	 * 销售订单商品详细信息添加页面
	 *
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 14, 2013
	 */
	public String orderDetailAddPage() throws Exception {
		String customerId = request.getParameter("cid");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DATE, 2);
		Map colMap = getEditAccessColumn("t_sales_order_detail");
		request.setAttribute("colMap", colMap);
		request.setAttribute("customerId", customerId);
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
        request.setAttribute("presentByZero",getSysParamValue("presentByZero"));
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		request.setAttribute("CustomerNewBuyRemind", getSysParamValue("CustomerNewBuyRemind"));
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
	public String orderDetailEditPage() throws Exception {
		String customerId = request.getParameter("cid");
		Map colMap = getEditAccessColumn("t_sales_order_detail");
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
		return SUCCESS;
	}

	/**
	 * 显示商品折扣添加页面
	 * 
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2014年6月21日
	 */
	public String orderDiscountAddPage() throws Exception {
		return "success";
	}

	/**
	 * 显示商品折扣修改页面
	 * 
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2014年6月21日
	 */
	public String orderDiscountEditPage() throws Exception {
		return "success";
	}

	/**
	 * 显示品牌折扣添加页面
	 * 
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2014年6月21日
	 */
	public String orderBrandDiscountAddPage() throws Exception {
		SysParam discountTypeSysParam = getBaseSysParamService().getSysParam("repartitionType");
		if(null != discountTypeSysParam){
			request.setAttribute("repartitionType",discountTypeSysParam.getPvalue());
		}else{
			request.setAttribute("repartitionType","0");
		}
		return "success";
	}

	/**
	 * 显示品牌页面修改页面
	 * 
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2014年6月21日
	 */
	public String orderBrandDiscountEditPage() throws Exception {
		return "success";
	}

	/**
	 * 销售订单新增页面
	 * 
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 3, 2013
	 */
	public String orderAddPage() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_sales_order"));
		request.setAttribute("userName", getSysUser().getName());
		request.setAttribute("salestypeList", getBaseSysCodeService().showSysCodeListByType("salestype"));//销售类型
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

	/**
	 * 销售订单复制页面
	 * 
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 17, 2013
	 */
	public String orderCopyPage() throws Exception {
		String id = request.getParameter("id");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Order order = salesOrderService.getOrderByCopy(id);
		String jsonStr = JSONUtils.listToJsonStr(order.getOrderDetailList());
		order.setRemark("");
		request.setAttribute("saleorder", order);
		request.setAttribute("goodsJson", jsonStr);
		request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_sales_order"));
		request.setAttribute("userName", getSysUser().getName());
		// 系统参数 控制销售订单 仓库是否必填
		String isOrderStorageSelect = getSysParamValue("IsOrderStorageSelect");
		if (StringUtils.isEmpty(isOrderStorageSelect)) {
			isOrderStorageSelect = "0";
		}
		request.setAttribute("isOrderStorageSelect", isOrderStorageSelect);

        //当前客户应收款及余额情况
        Map map = salesOrderService.showCustomerReceivableInfoData(order.getCustomerid());
        BigDecimal receivableAmount  = (BigDecimal) map.get("receivableAmount");//客户应收款情况
        BigDecimal leftAmount = (BigDecimal) map.get("leftAmount");//客户余额
        request.setAttribute("receivableAmount", receivableAmount);
        request.setAttribute("leftAmount", leftAmount);

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

	/**
	 * 添加销售订单
	 * 
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 10, 2013
	 */
	@UserOperateLog(key = "SaleOrder", type = 2)
	public String addOrder() throws Exception {
		String addType = request.getParameter("addType");
		if ("temp".equals(addType)) { // 暂存
			saleorder.setStatus("1");
		} else if ("real".equals(addType)) { // 保存
			saleorder.setStatus("2");
		}

		String orderDetailJson = request.getParameter("goodsjson");
		List<OrderDetail> orderDetailList = JSONUtils.jsonStrToList(orderDetailJson, new OrderDetail());
		saleorder.setOrderDetailList(orderDetailList);
		// 判断是否审核
		String saveaudit = request.getParameter("saveaudit");
		Map returnmap = salesOrderService.addOrder(saleorder, saveaudit);
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
				addLog("销售订单新增保存审核 编号：" + saleorder.getId(), flag);
			} else {
				addLog("销售订单新增 编号：" + saleorder.getId(), flag);
			}

		} else {

			addLog("销售订单新增 编号：" + saleorder.getId(), flag);
		}
		map.put("flag", flag);
		map.put("backid", saleorder.getId());
		map.put("type", "add");
		addJSONObject(map);

		return SUCCESS;
	}

	/**
	 * 销售订单修改页面
	 * 
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 10, 2013
	 */
	public String orderEditPage() throws Exception {
		String id = request.getParameter("id");
		Order order = salesOrderService.getOrder(id);
		//显示修改页面时，给数据加锁
		//lockData("t_sales_order", id);
		// 系统参数 控制销售订单 仓库是否必填
		String isOrderStorageSelect = getSysParamValue("IsOrderStorageSelect");
		if (StringUtils.isEmpty(isOrderStorageSelect)) {
			isOrderStorageSelect = "0";
		}
		request.setAttribute("isOrderStorageSelect", isOrderStorageSelect);

		if (null != order) {
			List statusList = getBaseSysCodeService().showSysCodeListByType("status");
			List<OrderDetail> detailList = order.getOrderDetailList();
			String jsonStr = JSONUtils.listToJsonStr(detailList);
			if (null != order.getLackList()) {
				String laskGoodsjsonStr = JSONUtils.listToJsonStr(order.getLackList());
				request.setAttribute("laskJson", laskGoodsjsonStr);
			}
			request.setAttribute("saleorder", order);
			request.setAttribute("goodsJson", jsonStr);
			request.setAttribute("statusList", statusList);

            //当前客户应收款及余额情况
            Map map = salesOrderService.showCustomerReceivableInfoData(order.getCustomerid());
            BigDecimal receivableAmount  = (BigDecimal) map.get("receivableAmount");//客户应收款情况
            BigDecimal leftAmount = (BigDecimal) map.get("leftAmount");//客户余额
            request.setAttribute("receivableAmount", receivableAmount);
            request.setAttribute("leftAmount", leftAmount);

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

			request.setAttribute("salestypeList", getBaseSysCodeService().showSysCodeListByType("salestype"));//销售类型

			if ("1".equals(order.getStatus()) || "2".equals(order.getStatus()) || "6".equals(order.getStatus())) {
				// 加锁
				lockData("t_sales_order", order.getId());
				return "editSuccess";
			} else {
				return "viewSuccess";
			}
		} else {
			return "addSuccess";
		}
	}

	/**
	 * 销售订单修改
	 * 
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Dec 11, 2013
	 */
	@UserOperateLog(key = "SaleOrder", type = 3)
	public String updateOrder() throws Exception {
		boolean lock = isLock("t_sales_order", saleorder.getId()); // 判断锁定并解锁
		if (lock) { // 被锁定不能进行修改
			addJSONObject("lock", true);
			addLog("销售订单 编码：" + saleorder.getId() + "互斥，操作", false);
			return SUCCESS;
		}
		String addType = request.getParameter("addType");
		if ("1".equals(saleorder.getStatus())) {
			if ("real".equals(addType)) {
				saleorder.setStatus("2");
			}
		}
		Map map = new HashMap();

		SysUser sysUser = getSysUser();
		saleorder.setModifyuserid(sysUser.getUserid());
		saleorder.setModifyusername(sysUser.getName());
		String orderDetailJson = request.getParameter("goodsjson");
		List<OrderDetail> orderDetailList = JSONUtils.jsonStrToList(orderDetailJson, new OrderDetail());
		saleorder.setOrderDetailList(orderDetailList);
		// 配置库存后 缺货商品
		String lackGoodsjson = request.getParameter("lackGoodsjson");
		if (StringUtils.isNotEmpty(lackGoodsjson)) {
			List<OrderDetail> lackGoodsList = JSONUtils.jsonStrToList(lackGoodsjson, new OrderDetail());
			saleorder.setLackList(lackGoodsList);
		}
		// 判断是否审核
		String saveaudit = request.getParameter("saveaudit");
		Map returnmap = salesOrderService.updateOrder(saleorder, saveaudit);
		boolean flag = (Boolean) returnmap.get("flag");
		String msg = "";
		if ("saveaudit".equals(saveaudit) && flag) {
			boolean auditflag = (Boolean) returnmap.get("auditflag");
			msg = (String) returnmap.get("msg");
			String billId = (String) returnmap.get("billId");
			map.put("auditflag", auditflag);
			map.put("msg", msg);
			map.put("billId", billId);
			addLog("销售订单保存审核 编号：" + saleorder.getId() + "," + msg, auditflag);
		} else {
            if(returnmap.containsKey("msg")){
                msg = (String) returnmap.get("msg");
                addLog("销售订单修改 编号：" + saleorder.getId() + "," + msg , flag);
            }else{
                addLog("销售订单修改 编号：" + saleorder.getId() , flag);
            }
		}
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("backid", saleorder.getId());
		addJSONObject(map);
		// 解锁数据
		isLockEdit("t_sales_order", saleorder.getId()); // 判断锁定并解锁
		return SUCCESS;
	}

    /**
     * 重新设置订单业务日期
     * @return
     * @throws Exception
     */
    @UserOperateLog(key="SaleOrder",type=3)
    public String updateOrderDate() throws Exception {

        String id = request.getParameter("id");
        String date = request.getParameter("date");

        boolean lock = isLockEdit("t_sales_order", id); // 判断锁定并解锁
        if (!lock) { // 被锁定不能进行修改
            addJSONObject("lock", true);
            return SUCCESS;
        }

        Order order = salesOrderService.getOrder(id);
        if(order == null){
            return SUCCESS;
        }
        order.setBusinessdate(date);

        boolean flag = salesOrderService.updateOrderDate(order);
        addJSONObject("flag", flag);
        addLog("销售订单业务日期调整 编号："+id, flag);

        return  SUCCESS;
    }

     /**
      * 获取客户商品最近三次审核通过单子里的价格
      * @author lin_xx
      * @date 2017/3/15
      */
     public String getAuditPrice() throws Exception {
         String customerid = request.getParameter("customerid");
         String goodsid = request.getParameter("goodsid");
         List list = salesOrderService.getCustomerGoodsLastThreePrice(customerid,goodsid);
         addJSONArray(list);
         return SUCCESS;
     }

	/**
	 * 销售订单查看页面
	 * 
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 10, 2013
	 */
	public String orderViewPage() throws Exception {
		String id = request.getParameter("id");
		Order order = salesOrderService.getOrder(id);
		if (order == null) {
			return SUCCESS;
		}
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr = JSONUtils.listToJsonStr(order.getOrderDetailList());
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
		request.setAttribute("saleorder", order);
		request.setAttribute("goodsJson", jsonStr);
		request.setAttribute("statusList", statusList);
		request.setAttribute("isLocked", lockData("t_sales_order", order.getId()));
		request.setAttribute("salestypeList", getBaseSysCodeService().showSysCodeListByType("salestype"));//销售类型

		String printlimit = getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);

        //当前客户应收款及余额情况
        Map map = salesOrderService.showCustomerReceivableInfoData(order.getCustomerid());
        BigDecimal receivableAmount  = (BigDecimal) map.get("receivableAmount");//客户应收款情况
        BigDecimal leftAmount = (BigDecimal) map.get("leftAmount");//客户余额
        request.setAttribute("receivableAmount", receivableAmount);
        request.setAttribute("leftAmount", leftAmount);

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
	 * 销售订单列表页面
	 * 
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 10, 2013
	 */
	public String orderListPage() throws Exception {
		String printlimit = getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
		request.setAttribute("today", dateFormat.format(new Date()));

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
	 * 获取订单列表
	 * 
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 20, 2013
	 */
	public String getOrderList() throws Exception {
		SysUser sysUser = getSysUser();
		Map map = CommonUtils.changeMap(request.getParameterMap());
		map.put("userid", sysUser.getUserid());

        pageMap.setCondition(map);
        PageData pageData = salesOrderService.getOrderData(pageMap);
        addJSONObject(pageData);

		return SUCCESS;
	}

	/**
	 * 通过订单编码获取商品明细列表
	 * 
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 20, 2013
	 */
	public String getDetailListByOrder() throws Exception {
		String id = request.getParameter("id");
		List list = salesOrderService.getDetailListOrder(id);
		addJSONArray(list);
		return SUCCESS;
	}

	/**
	 * 获取商品详细信息，包括价格套信息、税种信息、计量单位信息
	 * 
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 13, 2013
	 */
	public String getGoodsDetail() throws Exception {
		String goodsId = request.getParameter("id"); // 商品编码
		String customerId = request.getParameter("cid"); // 客户编码
		String storageid = request.getParameter("storageid");// 仓库编码
		String date = request.getParameter("date");
		String type = request.getParameter("type");
		String num = request.getParameter("num");
		BigDecimal unitNum = BigDecimal.ZERO;
		if (StringUtils.isNotEmpty(num)) {
			unitNum = new BigDecimal(num);
		}
		StorageSummary storageSummary = null;
		if (StringUtils.isNotEmpty(storageid)) {
			storageSummary = getStorageSummaryByStorageidAndGoodsid(storageid, goodsId);
		} else {
			storageSummary = getStorageSummarySumByGoodsid(goodsId);
		}
		Map map = new HashMap();
		map.put("detail", salesOrderService.getGoodsDetail(goodsId, customerId, date, unitNum, type));
		if (storageSummary != null) {
			map.put("total", storageSummary.getUsablenum());
			map.put("unitname", storageSummary.getUnitname());
		} else {
			map.put("total", 0);
			map.put("unitname", "");
			map.put("storageid", "");
		}
		addJSONObject(map);
		return SUCCESS;
	}

	/**
	 * 审核或反审订单
	 * 
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 16, 2013
	 */
	@UserOperateLog(key = "SaleOrder", type = 3)
	public String auditOrder() throws Exception {
		String type = request.getParameter("type"); // 1为审核2为反审
		String id = request.getParameter("id");
		String model = request.getParameter("model");
		Map result = salesOrderService.auditOrder(type, id, model);
		addJSONObject(result);
		if ("1".equals(type)) {
			addLog("销售订单审核 编号：" + id, result);
		} else {
			addLog("销售订单反审 编号：" + id, result);
		}
		return SUCCESS;
	}

	/**
	 * 批量超级审核
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date May 8, 2014
	 */
	@UserOperateLog(key = "SaleOrder", type = 3)
	public String supplierAuditOrderMuti() throws Exception {
		String type = request.getParameter("type"); // 1为审核2为反审
		String ids = request.getParameter("ids");
		String model = request.getParameter("model");
		String billids = "", remsg = "", sucids = "", unsucids = "";
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			Map result = salesOrderService.auditOrder(type, id, model);
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
			addLog("销售订单批量超级审核 编号：" + sucids, true);
		} else {
			addLog("销售订单批量超级审核 编号：" + unsucids, false);
		}
		return SUCCESS;
	}

	/**
	 * 批量审核
	 * 
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date Aug 5, 2013
	 */
	@UserOperateLog(key = "SaleOrder", type = 3)
	public String auditMultiOrder() throws Exception {
		String ids = request.getParameter("ids");
		Map map = salesOrderService.auditMultiOrder(ids);
		addJSONObject(map);
		String msg = "";
		if (map.containsKey("msg")) {
			msg = "失败信息:" + (String) map.get("msg");
		}
		addLog("销售订单批量审核 编号：" + ids + ";" + msg, map);
		return SUCCESS;
	}

	/**
	 * 删除订单信息
	 * 
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 17, 2013
	 */
	@UserOperateLog(key = "SaleOrder", type = 4)
	public String deleteOrder() throws Exception {
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_sales_order", id); // 判断是否被引用，被引用则无法删除。
		if (!delFlag) {
			addJSONObject("delFlag", true);
			return SUCCESS;
		}

		Order order = salesOrderService.getOrder(id);
		if(order == null || "3".equals(order.getStatus()) || "4".equals(order.getStatus())) {
			Map result = new HashMap();
			result.put("flag", false);
			result.put("msg", "订单已审核或者已删除！");
			addJSONObject(result);
			return SUCCESS;
		}

		boolean flag = salesOrderService.deleteOrder(id);
		addJSONObject("flag", flag);
		addLog("销售订单删除 编号：" + id, flag);
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
	public String doInvalidOrder() throws Exception {
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		Map map = salesOrderService.doInvalidOrder(id, type);
		addJSONObject(map);
		if ("1".equals(type)) {
			addLog("作废订单：" + id, (Boolean) map.get("flag"));
		} else if ("2".equals(type)) {
			addLog("取消作废订单：" + id, (Boolean) map.get("flag"));
		}
		return SUCCESS;
	}

	/**
	 * 批量作废订单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-11-18
	 */
	@UserOperateLog(key = "SaleOrder", type = 3)
	public String doBatchInvalidOrder()throws Exception{
		String ids = request.getParameter("ids");
		Map map = salesOrderService.doBatchInvalidOrder(ids);
		addJSONObject(map);
		String msg = map.get("msg") != null ? (String)map.get("msg") : "";
		if(StringUtils.isNotEmpty(msg)){
			addLog(msg, true);
		}else{
			addLog("作废订单：" + ids, false);
		}
		return SUCCESS;
	}

	/**
	 * 提交工作流
	 * 
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date Jun 29, 2013
	 */
	@UserOperateLog(key = "SaleOrder", type = 3)
	public String submitOrderProcess() throws Exception {
		String id = request.getParameter("id");
		Order order = salesOrderService.getOrder(id);
		if (!order.getStatus().equals("2")) {
			addJSONObject("flag", false);
			return SUCCESS;
		}
		SysUser user = getSysUser();
		Map<String, Object> variables = new HashMap<String, Object>();
		String title = "销售订单 " + order.getId() + " (" + order.getBusinessdate() + ")";
		boolean flag = salesOrderService.submitOrderProcess(title, user.getUserid(), "salesOrder", id, variables);
		addJSONObject("flag", flag);
		addLog("销售订单提交工作流 编号：" + id, flag);
		return SUCCESS;
	}

	/**
	 * 根据客户编号 商品编号 商品数量 获取该客户下 商品的含税单价 含税金额 未税单价 未税金额等
	 * 
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Sep 25, 2013
	 */
	public String countSalesGoodsByCustomer() throws Exception {
		String customerid = request.getParameter("customerid");
		String goodsid = request.getParameter("goodsid");
		String num = request.getParameter("num");
		String date = request.getParameter("date");
		String type = request.getParameter("type");
		if (null == type || "".equals(type)) {
			type = null;
		}
		BigDecimal unitnum = BigDecimal.ZERO;
		if (null != num && !"".equals(num)) {
			unitnum = new BigDecimal(num);
		}

		OrderDetail orderDetail = salesOrderService.getGoodsDetail(goodsid, customerid, date, unitnum, type);
        Map map = new HashMap();
		if (null != orderDetail) {
			BigDecimal taxprice = orderDetail.getTaxprice();
			BigDecimal notaxprice = orderDetail.getNotaxprice();
			// 含税金额
			BigDecimal taxamount = unitnum.multiply(taxprice).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);
			BigDecimal notaxamount = unitnum.multiply(notaxprice).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);
			BigDecimal tax = taxamount.subtract(notaxamount).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);

			map.put("taxprice", taxprice);
			map.put("notaxprice", notaxprice);
			map.put("taxamount", taxamount);
			map.put("notaxamount", notaxamount);
			map.put("tax", tax);
		}
        Customer customer = getCustomerById(customerid);
        map.put("salesdept",customer.getSalesdeptid());
        Map customerMap = salesOrderService.showCustomerReceivableInfoData(customerid);
        map.putAll(customerMap);
        addJSONObject(map);
		return "success";
	}

	/**
	 * 批量刷新客户商品价格
     *
	 * @return
	 * @throws Exception
     * @author limin
     * @date Feb 6, 2018
	 */
	public String multiRefreshCustomerGoodsPrice() throws Exception {

        String customerid = request.getParameter("customerid");
        String date = request.getParameter("date");
		String detailsStr = request.getParameter("detailsStr");

		List<Map> detailMapList = JSONUtils.jsonStrToList(detailsStr, new HashMap());
		for(Map detailMap : detailMapList) {

            String goodsid = (String) detailMap.get("goodsid");
			String num = (String) detailMap.get("num");
            String isdiscount = (String) detailMap.get("isdiscount");
            if(StringUtils.isEmpty(isdiscount) || "0".equals(isdiscount)) {

                OrderDetail orderDetail = salesOrderService.getGoodsDetail(goodsid, customerid, date, new BigDecimal(num), null);
                if (null != orderDetail) {
                    BigDecimal taxprice = orderDetail.getTaxprice();
                     BigDecimal notaxprice = orderDetail.getNotaxprice();
                    // 含税金额
                    BigDecimal taxamount = new BigDecimal(num).multiply(taxprice).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);
                    BigDecimal notaxamount = new BigDecimal(num).multiply(notaxprice).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);
                    BigDecimal tax = taxamount.subtract(notaxamount).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);

                    detailMap.put("taxprice", taxprice);
                    detailMap.put("notaxprice", notaxprice);
                    detailMap.put("taxamount", taxamount);
                    detailMap.put("notaxamount", notaxamount);
					detailMap.put("tax", tax);
					detailMap.put("isnew", orderDetail.getIsnew());
                }
            }
		}

    	Map result = new HashMap();
		result.put("details", detailMapList);
		result.put("receivableInfo",salesOrderService.showCustomerReceivableInfoData(customerid));
		addJSONObject(result);
		return SUCCESS;
	}

    /**
     * 根据客户编号获取客户资金余额及其应收款
     * @return
     * @throws Exception
     */
    public String getCustomerFinanceInfo() throws Exception {
        String customerid = request.getParameter("customerid");
        Map map = salesOrderService.showCustomerReceivableInfoData(customerid);
        addJSONObject(map);
        return SUCCESS;
    }

	/**
	 * 判断销售订单是否重复问题
	 * 
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Apr 26, 2014
	 */
	public String canAuditOrder() throws Exception {
		String ids = request.getParameter("ids");
		// 判断要货单据在最近几天内是否重复
		boolean flag = true;
		String msg = "";
		if (null != ids && !"".equals(ids)) {
			String[] idarr = ids.split(",");
			for (String id : idarr) {
				Map returnMap = salesOrderService.checkOrderAudit(id);
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

	/**
	 * 配置库存
	 * 
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2014年6月23日
	 */
	@UserOperateLog(key = "SalesOrder", type = 3, value = "")
	public String orderDeployInfo() throws Exception {
		String id = request.getParameter("id");
		Map map = salesOrderService.orderDeployInfo(id);
		addJSONObject(map);
		addLog("销售订单配置库存 编号：" + id, true);
		return "success";
	}

	/**
	 * 销售订单配置库存页面
	 * 
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2014年6月23日
	 */
	public String orderDeployInfoPage() throws Exception {
		String id = request.getParameter("id");
		// 1追加 2替换
		String barcodeflag = request.getParameter("barcodeflag");
        String deploy = request.getParameter("deploy");
		Order order = salesOrderService.getOrderDeployInfo(id, barcodeflag,deploy);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr = JSONUtils.listToJsonStr(order.getOrderDetailList());
		String laskJsonStr = JSONUtils.listToJsonStr(order.getLackList());
		request.setAttribute("saleorder", order);
		request.setAttribute("goodsJson", jsonStr);
		request.setAttribute("laskJson", laskJsonStr);
		request.setAttribute("statusList", statusList);
		request.setAttribute("isLocked", lockData("t_sales_order", order.getId()));

		// 系统参数 控制销售订单 仓库是否必填
		String isOrderStorageSelect = getSysParamValue("IsOrderStorageSelect");
		if (StringUtils.isEmpty(isOrderStorageSelect)) {
			isOrderStorageSelect = "0";
		}
		request.setAttribute("isOrderStorageSelect", isOrderStorageSelect);
		return "success";
	}

	/**
	 * 导入销售订单
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Sep 25, 2013
	 */
	@UserOperateLog(key = "SalesOrder", type = 2, value = "")
	public String importSalesOrderExcel() throws Exception {
		Map<String, String> map3 = CommonUtils.changeMap(request.getParameterMap());
		String clazz = request.getParameter("clazz");
		String meth = request.getParameter("method");
		String module = request.getParameter("module");
		String pojo = request.getParameter("pojo");
		Object object2 = SpringContextUtils.getBean(clazz);
		Class entity = Class.forName("com.hd.agent." + module + ".model." + pojo);
		Method[] methods = object2.getClass().getMethods();
		Method method = null;
		for (Method m : methods) {
			if (m.getName().equals(meth)) {
				method = m;
			}
		}
		// 判断文件格式filestype: 1.excel文件、2.txt文件
		String filestype = null;
		List<String> paramList = null;
		List<String> paramList2 = new ArrayList<String>();
		if (map3.containsKey("filestype") && null != map3.get("filestype")) {
			filestype = (String) map3.get("filestype");
		}
		List<Map<String, Object>> list = null;
		int successNum = 0, failureNum = 0, ordernum = 0;
		String msg = "", backorderids = "", goodsidmsg = "", customergoodsidmsg = "", customerids = "", spellmsg = "", barcodemsg = "", disablegoodsidsmsg = "";
		boolean flag = true;
		// .excel格式获取第一行字段
		if ("1".equals(filestype)) {
			paramList = ExcelUtils.importExcelFirstRow(excelFile); // 获取第一行数据为字段的描述列表
			if (null != paramList) {
				for (String str : paramList) {
					if ("客户编码".equals(str)) {
						paramList2.add("customerid");
					} else if ("客户名称".equals(str)) {
						paramList2.add("customername");
					} else if ("业务日期".equals(str)) {
						paramList2.add("businessdate");
					} else if ("销售部门编码".equals(str)) {
						paramList2.add("salesdept");
					} else if ("销售部门名称".equals(str)) {
						paramList2.add("salesdeptname");
					} else if ("客户业务员".equals(str)) {
						paramList2.add("salesusername");
					} else if ("商品编码".equals(str)) {
						paramList2.add("goodsid");
					} else if ("商品名称".equals(str)) {
						paramList2.add("goodsname");
					} else if ("商品助记符".equals(str)) {
						paramList2.add("spell");
					} else if ("商品条形码".equals(str)) {
						paramList2.add("barcode");
					} else if ("店内码".equals(str)) {
						paramList2.add("customergoodsid");
					} else if ("数量".equals(str)) {
						paramList2.add("unitnum");
					} else if ("辅数量".equals(str)) {
						paramList2.add("auxnumdetail");
					} else if ("单价".equals(str)) {
						paramList2.add("taxprice");
					} else if ("金额".equals(str)) {
						paramList2.add("taxamount");
					} else if ("备注".equals(str)) {
						paramList2.add("remark");
					} else if ("客户单号".equals(str)) {
						paramList2.add("sourceid");
					} else {
						paramList2.add("null");
					}
				}
			}
			list = ExcelUtils.importExcel(excelFile, paramList2); // 获取导入数据
			Map resultMap2 = new HashMap();
			if (null != list && list.size() != 0) {
				List result = new ArrayList();
				for (Map<String, Object> map : list) {
					Object object = entity.newInstance();
					Field[] fields = entity.getDeclaredFields();
                    // 判断map中是否含有taxprice，如果不含有taxprice，则把importSalesOrder中的taxprice设置为null
                    BigDecimal taxprice = null;
					if(map.containsKey("taxprice")) {
                        taxprice = new BigDecimal((String) map.get("taxprice"));
                    }
					// 获取的导入数据格式转换
					DRCastTo(map, fields);
					PropertyUtils.copyProperties(object, map);
                    ImportSalesOrder importSalesOrder = (ImportSalesOrder) object;
                    importSalesOrder.setTaxprice(taxprice);

					result.add(importSalesOrder);
				}

				if (result.size() != 0) {
					resultMap2 = excelService.insertSalesOrder(object2, result, method);
					if (resultMap2.containsKey("customerid") && null != resultMap2.get("customerid")) {
						msg = "客户编号：" + (String) resultMap2.get("customerid") + "不存在,导入失败";
					}
					if (resultMap2.containsKey("goodsidmsg") && null != resultMap2.get("goodsidmsg")) {
						goodsidmsg += (String) resultMap2.get("goodsidmsg");
					}
					if (resultMap2.containsKey("customergoodsidmsg") && null != resultMap2.get("customergoodsidmsg")) {
						customergoodsidmsg += (String) resultMap2.get("customergoodsidmsg");
					}
					if (resultMap2.containsKey("spellmsg") && null != resultMap2.get("spellmsg")) {
						spellmsg += (String) resultMap2.get("spellmsg");
					}
					if (resultMap2.containsKey("barcodemsg") && null != resultMap2.get("barcodemsg")) {
						barcodemsg += (String) resultMap2.get("barcodemsg");
					}
					if (resultMap2.containsKey("disablegoodsidsmsg") && null != resultMap2.get("disablegoodsidsmsg")) {
						disablegoodsidsmsg += (String) resultMap2.get("disablegoodsidsmsg");
					}
					if (StringUtils.isEmpty(msg)) {
						if (StringUtils.isNotEmpty(customergoodsidmsg)) {
							msg = "店内码:" + customergoodsidmsg + "不存在,导入失败";
						} else if (StringUtils.isNotEmpty(goodsidmsg)) {
							msg = "商品编码:" + goodsidmsg + "不存在,导入失败";
						} else if (StringUtils.isNotEmpty(spellmsg)) {
							msg = "商品助记符:" + spellmsg + "不存在,导入失败";
						} else if (StringUtils.isNotEmpty(barcodemsg)) {
							msg = "商品条形码:" + barcodemsg + "不存在,导入失败";
						} else if (StringUtils.isNotEmpty(disablegoodsidsmsg)) {
							msg = "商品:" + disablegoodsidsmsg + "不为启用状态,不予导入";
						}
					}
//					else {
//						msg = "客户编号不存在,导入失败";
//					}
					resultMap2.put("msg", msg);
					if (resultMap2.containsKey("backorderids") && null != resultMap2.get("backorderids")) {
						backorderids = (String) resultMap2.get("backorderids");
						if (backorderids.indexOf(",") != -1) {
							ordernum = backorderids.split(",").length;
						} else {
							ordernum = 1;
						}
						resultMap2.put("ordernum", ordernum);
						addLog("销售订单导入 编号:" + resultMap2.get("backorderids").toString(), resultMap2.get("flag").equals(true));
					}
				}
			} else {
				resultMap2.put("excelempty", true);
			}
			addJSONObject(resultMap2);
		} else if ("2".equals(filestype)) {
			Map resultMap3 = new HashMap();
			paramList = OrderExtServiceImpl.importTxtFirstRow(excelFile); // 获取第一行数据为字段的描述列表
			// 获取三和总店编码
			String pid = getSysParamValue("SHORDERIMPORT");
			Map<String, List<Map<String, Object>>> map2 = OrderExtServiceImpl.importTXT(excelFile, paramList, pid); // 获取导入数据
			Iterator it = map2.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				Object key = entry.getKey();
				List<Map<String, Object>> list2 = (List<Map<String, Object>>) entry.getValue();
				for (Map<String, Object> map : list2) {
					// 根据总店编码+店号获取客户信息
					Customer customer = getBaseSalesService().getCustomerInfoByShopno(pid, (String) map.get("shopno"));
					if (null != customer) {
						map.put("customerid", customer.getId());
						map.put("customername", customer.getName());
					}
				}
				List result = new ArrayList();
				if (null != list2 && list2.size() != 0) {
					for (Map<String, Object> map : list2) {
						Object object = entity.newInstance();
						Field[] fields = entity.getDeclaredFields();
						// 获取的导入数据格式转换
						DRCastTo(map, fields);
						// BeanUtils.populate(object, map);
						PropertyUtils.copyProperties(object, map);
						result.add(object);
					}
					if (result.size() != 0) {
						Map resultMap2 = excelService.insertSalesOrder(object2, result, method);
						if (resultMap2.containsKey("success") && null != resultMap2.get("success")) {
							successNum += (Integer) resultMap2.get("success");
						}
						if (resultMap2.containsKey("failure") && null != resultMap2.get("failure")) {
							failureNum += (Integer) resultMap2.get("failure");
						}
						if (resultMap2.containsKey("customerid") && null != resultMap2.get("customerid")) {
							if (StringUtils.isEmpty(customerids)) {
								customerids = (String) resultMap2.get("customerid");
							} else {
								customerids += "," + (String) resultMap2.get("customerid");
							}
							msg = "客户编号：" + customerids + "不存在,导入失败";
						}
						if (resultMap2.containsKey("flag") && null != resultMap2.get("flag")) {
							flag = resultMap2.get("flag").equals(true) && flag;
						}
						if (resultMap2.containsKey("goodsidmsg") && null != resultMap2.get("goodsidmsg")) {
							goodsidmsg += (String) resultMap2.get("goodsidmsg");
						}
						if (resultMap2.containsKey("customergoodsidmsg") && null != resultMap2.get("customergoodsidmsg")) {
							customergoodsidmsg += (String) resultMap2.get("customergoodsidmsg");
						}
						if (resultMap2.containsKey("spellmsg") && null != resultMap2.get("spellmsg")) {
							spellmsg += (String) resultMap2.get("spellmsg");
						}
						if (resultMap2.containsKey("backorderids") && null != resultMap2.get("backorderids")) {
							if (StringUtils.isEmpty(backorderids)) {
								backorderids = (String) resultMap2.get("backorderids");
							} else {
								backorderids += "," + (String) resultMap2.get("backorderids");
							}
						}
					}
				}
			}
			// resultMap3.put("flag", flag);
			resultMap3.put("success", successNum);
			resultMap3.put("failure", failureNum);
			resultMap3.put("repeatNum", 0);
			resultMap3.put("closeNum", 0);
			if (StringUtils.isEmpty(msg)) {
				if (StringUtils.isNotEmpty(customergoodsidmsg)) {
					msg = "店内码:" + customergoodsidmsg + "不存在,导入失败";
				} else if (StringUtils.isNotEmpty(goodsidmsg)) {
					msg = "商品编码:" + goodsidmsg + "不存在,导入失败";
				} else if (StringUtils.isNotEmpty(spellmsg)) {
					msg = "商品助记符:" + spellmsg + "不存在,导入失败";
				}
			} else {
				msg = "客户编号不存在,导入失败";
			}
			resultMap3.put("msg", msg);
			if (StringUtils.isNotEmpty("backorderids")) {
				addLog("销售订单导入 编号:" + backorderids, flag);
			}
			if (StringUtils.isNotEmpty(backorderids)) {
				if (backorderids.indexOf(",") != -1) {
					ordernum = backorderids.split(",").length;
				} else {
					ordernum = 1;
				}
			}
			resultMap3.put("ordernum", ordernum);
			addJSONObject(resultMap3);
		}
		return SUCCESS;
	}

	/**
	 * 销售订单导出
	 * 
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Sep 24, 2013
	 */
	@UserOperateLog(key = "order", type = 0, value = "销售订单导出")
	public void exportBillExcel() throws Exception {
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
		firstMap.put("sourceid", "来源单号/客户单号");
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
        firstMap.put("addusername","制单人");
		firstMap.put("remark", "单据备注");
		firstMap.put("remark1", "备注");
		result.add(firstMap);

		List<ExportSalesOrder> list = excelService.getBillList(pageMap);
		if (list.size() != 0) {
			for (ExportSalesOrder exportSalesOrder : list) {
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2 = PropertyUtils.describe(exportSalesOrder);
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

	/**
	 * 根据缺货商品报表 补单
	 * 
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2014年12月25日
	 */
	@UserOperateLog(key = "SaleOrder", type = 2)
	public String addOrderByGoodsout() throws Exception {
		String ids = request.getParameter("ids");
		Map map = salesOrderService.addOrderByGoodsout(ids);
		String msg = "";
		String billid = "";
		if (null != map) {
			msg = (String) map.get("msg");
			billid = (String) map.get("billid");
		}
		addJSONObject(map);
		addLog("缺货商品补单 " + msg + "    生成订单：" + billid);
		return "success";
	}

	/**
	 * 显示促销商品添加页面
	 * 
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2015年3月12日
	 */
	public String orderGoodsPromotionDetailAddPage() throws Exception {
		String groupid = request.getParameter("groupid");
		String storageid = request.getParameter("storageid");
        String orderid = request.getParameter("orderid");
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		PromotionPackageGroup promotionPackageGroup = salesOrderService.getOrderGoodsPromotionDetailInfoByOrderid(orderid,groupid);
		if (null != promotionPackageGroup) {
			request.setAttribute("promotionPackageGroup", promotionPackageGroup);
			// 买赠
			if ("1".equals(promotionPackageGroup.getPtype())) {
				List<PromotionPackageGroupDetail> list = promotionPackageGroup.getGroupDetails();
				PromotionPackageGroupDetail promotionPackageGroupDetail = null;
				List giveList = new ArrayList();
				for (PromotionPackageGroupDetail detail : list) {
					// 获取商品可用量
					StorageSummary storageSummary = null;
					if (StringUtils.isNotEmpty(storageid)) {
						storageSummary = salesOrderService.getStorageSummarySumByGoodsidAndStorageid(detail.getGoodsid(), storageid);
					} else {
						storageSummary = salesOrderService.getStorageSummarySumByGoodsid(detail.getGoodsid());
					}
					if (null != storageSummary) {
						detail.setUsablenum(storageSummary.getUsablenum());
					} else {
						detail.setUsablenum(BigDecimal.ZERO);
					}
					if ("1".equals(detail.getGtype())) {
						promotionPackageGroupDetail = detail;
					} else {
						giveList.add(detail);
					}
				}
				request.setAttribute("promotionPackageGroupDetail", promotionPackageGroupDetail);
				if (null != giveList && giveList.size() > 0) {
					String listJson = JSONUtils.listToJsonStr(giveList);
					request.setAttribute("listJson", listJson);
				}
				if (null != promotionPackageGroupDetail && null != promotionPackageGroupDetail.getGoodsInfo()) {
					String goodsInfoJson = JSONUtils.objectToJsonStr(promotionPackageGroupDetail.getGoodsInfo());
					request.setAttribute("goodsInfoJson", goodsInfoJson);
				}
				return "giveSuccess";
			} else if ("2".equals(promotionPackageGroup.getPtype())) {
				List<PromotionPackageGroupDetail> list = promotionPackageGroup.getGroupDetails();
				String listJson = JSONUtils.listToJsonStr(list);
				request.setAttribute("listJson", listJson);
				return "bundSuccess";
			}
		}
		return "success";
	}

	/**
	 * 根据数量与单价 获取买赠的辅数量与金额
	 * 
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2015年3月12日
	 */
	public String getAuxUnitNumAndAmountByGive() throws Exception {
        String orderid = request.getParameter("orderid");
		String goodsId = request.getParameter("id");
		String price = request.getParameter("price");
		String unitNum = request.getParameter("unitnum");
		String taxtype = request.getParameter("taxtype");
		String groupid = request.getParameter("groupid");

		if (StringUtils.isEmpty(unitNum)) {
			unitNum = "0";
		}
		if (StringUtils.isEmpty(price)) {
			price = "0";
		}
		BigDecimal bUnitNum = new BigDecimal(unitNum);
		BigDecimal bTaxPrice = new BigDecimal(price);

		Map result = new HashMap();
		GoodsInfo_MteringUnitInfo mteringUnitInfo = getDefaultGoodsAuxMeterUnitInfo(goodsId);
		String auxUnitId = "";
		if (mteringUnitInfo != null) {
			auxUnitId = mteringUnitInfo.getMeteringunitid();
		}
		Map returnMap = countGoodsInfoNumber(goodsId, auxUnitId, bUnitNum);
		if (null != returnMap) {
			String auxnum = (String) returnMap.get("auxInteger");
			String overnum = (String) returnMap.get("auxremainder");
			String auxnumdetail = (String) returnMap.get("auxnumdetail");

			result.put("auxnum", auxnum);
			result.put("overnum", overnum);
			result.put("auxnumdetail", auxnumdetail);
		}
		result.put("taxprice", bTaxPrice);
		result.put("unitnum", bUnitNum);
		GoodsInfo goodsInfo = getGoodsInfoByID(goodsId);
		if (null != goodsInfo) {
			result.put("boxprice", bTaxPrice.multiply(goodsInfo.getBoxnum()));
		}
		if (null == bTaxPrice) {
			bTaxPrice = BigDecimal.ZERO;
		}
		if (null == bUnitNum) {
			bUnitNum = BigDecimal.ZERO;
		}
		BigDecimal bTaxAmount = new BigDecimal(0); // 含税金额
		BigDecimal bNoTaxAmount = new BigDecimal(0); // 无税金额
		// 获取小数位
		int decimalLen = getAmountDecimalsLength();
		bTaxAmount = bTaxPrice.multiply(bUnitNum).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);

		bNoTaxAmount = getNotaxAmountByTaxAmount(bTaxAmount, taxtype);
		result.put("taxamount", bTaxAmount);
		result.put("notaxamount", bNoTaxAmount);
		if (bNoTaxAmount.compareTo(BigDecimal.ZERO) != 0) {
			result.put("notaxprice", bNoTaxAmount.divide(bUnitNum, 6, BigDecimal.ROUND_HALF_UP));
		}
		result.put("tax", bTaxAmount.subtract(bNoTaxAmount));

		List giveList = getGiveNumListByGroupidAndUnitnum(orderid,groupid, goodsId, bUnitNum);
		if (null != giveList) {
			result.put("giveList", giveList);
		}
		addJSONObject(result);
		return "success";
	}

	/**
	 * 根据辅数量和单价 获取总数量与金额
	 * 
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2015年3月12日
	 */
	public String getUnitNumAndAmountByGive() throws Exception {
        String orderid = request.getParameter("orderid");
		String goodsId = request.getParameter("id");
		String auxnum = request.getParameter("auxnum"); // 辅计量单位数量
		String overnum = request.getParameter("overnum"); // 主单位余数
		String price = request.getParameter("price");
		String taxtype = request.getParameter("taxtype");
		String groupid = request.getParameter("groupid");

		if (StringUtils.isEmpty(auxnum)) {
			auxnum = "0";
		}
		if (StringUtils.isEmpty(overnum)) {
			overnum = "0";
		}
		if (StringUtils.isEmpty(price)) {
			price = "0";
		}

		BigDecimal bAuxNum = new BigDecimal(auxnum);
		BigDecimal bOverNum = new BigDecimal(overnum);
		BigDecimal taxprice = new BigDecimal(price);
		BigDecimal bUnitNum = new BigDecimal(0);
		Map retMap = retMainUnitByUnitAndGoodid(bAuxNum, goodsId);
		BigDecimal mainUnitNum = new BigDecimal(0);
		if (retMap.containsKey("mainUnitNum")) {
			mainUnitNum = new BigDecimal(retMap.get("mainUnitNum").toString());
		}
		bUnitNum = bOverNum.add(mainUnitNum);
		Map result = new HashMap();
		GoodsInfo_MteringUnitInfo mteringUnitInfo = getDefaultGoodsAuxMeterUnitInfo(goodsId);
		String auxUnitId = "";
		if (mteringUnitInfo != null) {
			auxUnitId = mteringUnitInfo.getMeteringunitid();
		}

		Map returnMap = countGoodsInfoNumber(goodsId, auxUnitId, bUnitNum);
		if (null != returnMap) {
			String auxnumdetail = (String) returnMap.get("auxnumdetail");

			result.put("auxnum", auxnum);
			result.put("overnum", overnum);
			result.put("auxnumdetail", auxnumdetail);
		}
		result.put("taxprice", taxprice);
		result.put("unitnum", bUnitNum);
		GoodsInfo goodsInfo = getGoodsInfoByID(goodsId);
		if (null != goodsInfo) {
			result.put("boxprice", taxprice.multiply(goodsInfo.getBoxnum()));
		}
		if (null == bUnitNum) {
			bUnitNum = BigDecimal.ZERO;
		}
		BigDecimal bTaxAmount = new BigDecimal(0); // 含税金额
		BigDecimal bNoTaxAmount = new BigDecimal(0); // 无税金额
		// 获取小数位
		int decimalLen = getAmountDecimalsLength();
		bTaxAmount = taxprice.multiply(bUnitNum).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);

		bNoTaxAmount = getNotaxAmountByTaxAmount(bTaxAmount, taxtype);
		result.put("taxamount", bTaxAmount);
		result.put("notaxamount", bNoTaxAmount);
		if (bNoTaxAmount.compareTo(BigDecimal.ZERO) != 0) {
			result.put("notaxprice", bNoTaxAmount.divide(bUnitNum, 6, BigDecimal.ROUND_HALF_UP));
		}

		result.put("tax", bTaxAmount.subtract(bNoTaxAmount));
		// 赠送数量
		List giveList = getGiveNumListByGroupidAndUnitnum(orderid,groupid, goodsId, bUnitNum);
		if (null != giveList) {
			result.put("giveList", giveList);
		}
		addJSONObject(result);
		return "success";
	}

	/**
	 * 根据分组编号，商品编号和商品数量 获取赠品数量列表
	 * @param  orderid
	 * @param groupid
	 * @param goodsid
	 * @param unitnum
	 * @return
	 * @throws Exception
	 */
	public List getGiveNumListByGroupidAndUnitnum(String orderid,String groupid, String goodsid, BigDecimal unitnum) throws Exception {
		if (null != unitnum && unitnum.compareTo(BigDecimal.ZERO) == 1) {
			int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;

			PromotionPackageGroup promotionPackageGroup = salesOrderService.getOrderGoodsPromotionDetailInfoByOrderid(orderid,groupid);
			if ("1".equals(promotionPackageGroup.getPtype())) {
				List<PromotionPackageGroupDetail> list = promotionPackageGroup.getGroupDetails();
				PromotionPackageGroupDetail promotionPackageGroupDetail = null;
				List<PromotionPackageGroupDetail> giveList = new ArrayList();
				for (PromotionPackageGroupDetail detail : list) {
					if ("1".equals(detail.getGtype())) {
						promotionPackageGroupDetail = detail;
					} else {
						giveList.add(detail);
					}
				}
				BigDecimal givenum = BigDecimal.ZERO;
				if (goodsid.equals(promotionPackageGroupDetail.getGoodsid())) {
					givenum = unitnum.divide(promotionPackageGroupDetail.getUnitnum(), 0, BigDecimal.ROUND_DOWN);
				}
                //判断赠送的份数是否足够 不够只能赠送剩余的数量
                if(promotionPackageGroup.getLimitnum().compareTo(BigDecimal.ZERO)==1 && promotionPackageGroup.getRemainnum().compareTo(givenum)==-1){
                    givenum = promotionPackageGroup.getRemainnum();
                }
				List giveNumList = new ArrayList();
				for (PromotionPackageGroupDetail detail : giveList) {
					BigDecimal num = detail.getUnitnum().multiply(givenum);
					Map giveMap = new HashMap();
					giveMap.put("id", detail.getId());
					giveMap.put("goodsid", detail.getGoodsid());
					giveMap.put("givenum", num.setScale(decimalScale,BigDecimal.ROUND_HALF_UP));
					if (num.compareTo(BigDecimal.ZERO) == 1) {
						Map auxMap = countGoodsInfoNumber(detail.getGoodsid(), detail.getAuxunitid(), num);
						if (null != auxMap) {
							String auxnum = (String) auxMap.get("auxInteger");
							String overnum = (String) auxMap.get("auxremainder");
							BigDecimal totalbox = (BigDecimal) auxMap.get("auxnum");
							giveMap.put("givetotalbox", totalbox);
							giveMap.put("giveauxnum", auxnum);
							giveMap.put("giveovernum", overnum);

							String auxnumdetail = (String) auxMap.get("auxnumdetail");
							giveMap.put("givenumdetail", auxnumdetail);

							giveMap.put("givecount", givenum);
						}
					} else {
						giveMap.put("givenumdetail", "");
					}
					giveNumList.add(giveMap);
				}
				return giveNumList;
			}
		}
		return null;
	}

	/**
	 * 获取捆绑的商品数量金额等内容
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getGoodsDetailListByBund() throws Exception {
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
		String customerid = request.getParameter("customerid");
		String groupid = request.getParameter("groupid");
		String num = request.getParameter("bundnum");
		BigDecimal bundnum = BigDecimal.ZERO;
		if (StringUtils.isNotEmpty(num)) {
			bundnum = new BigDecimal(num);
		}
		PromotionPackageGroup promotionPackageGroup = salesOrderService.getOrderGoodsPromotionDetailInfo(groupid);
		if (null != promotionPackageGroup && "2".equals(promotionPackageGroup.getPtype())) {
			BigDecimal totalTaxAmount = BigDecimal.ZERO;
			BigDecimal totalNoTaxAmoint = BigDecimal.ZERO;
			List<PromotionPackageGroupDetail> bundList = promotionPackageGroup.getGroupDetails();
			List bundDetaiList = new ArrayList();
			for (PromotionPackageGroupDetail detail : bundList) {
				BigDecimal unitnum = detail.getUnitnum().multiply(bundnum);
				Map bundMap = new HashMap();
				bundMap.put("id", detail.getId());
				bundMap.put("goodsid", detail.getGoodsid());
				bundMap.put("unitnum", unitnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP));
				GoodsInfo goodsInfo = getGoodsInfoByID(detail.getGoodsid());
				if (null != goodsInfo && unitnum.compareTo(BigDecimal.ZERO) == 1) {
					Map auxMap = countGoodsInfoNumber(detail.getGoodsid(), detail.getAuxunitid(), unitnum);
					if (null != auxMap) {
						String auxnum = (String) auxMap.get("auxInteger");
						String overnum = (String) auxMap.get("auxremainder");
						BigDecimal totalbox = (BigDecimal) auxMap.get("auxnum");
						bundMap.put("totalbox", totalbox);
						bundMap.put("auxnum", auxnum);
						bundMap.put("overnum", overnum);
						String auxnumdetail = (String) auxMap.get("auxnumdetail");
						bundMap.put("auxnumdetail", auxnumdetail);
					}
					BigDecimal bTaxAmount = new BigDecimal(0); // 含税金额
					BigDecimal bNoTaxAmount = new BigDecimal(0); // 无税金额
					// 获取小数位
					int decimalLen = getAmountDecimalsLength();
					bTaxAmount = detail.getPrice().multiply(unitnum).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);

					bNoTaxAmount = getNotaxAmountByTaxAmount(bTaxAmount, goodsInfo.getDefaulttaxtype());

					bundMap.put("taxprice", detail.getPrice());
					bundMap.put("boxprice", detail.getPrice().multiply(goodsInfo.getBoxnum()).setScale(6, BigDecimal.ROUND_HALF_UP));
					bundMap.put("taxamount", bTaxAmount);
					bundMap.put("notaxamount", bNoTaxAmount);
					if (bNoTaxAmount.compareTo(BigDecimal.ZERO) != 0) {
						bundMap.put("notaxprice", bNoTaxAmount.divide(unitnum, 6, BigDecimal.ROUND_HALF_UP));
					}

					bundMap.put("tax", bTaxAmount.subtract(bNoTaxAmount));

					OrderDetail orderDetail = salesOrderService.getFixGoodsDetail(detail.getGoodsid(), customerid);
					if (null != orderDetail) {
						bundMap.put("oldprice", orderDetail.getTaxprice());
					}
					bundDetaiList.add(bundMap);

					totalTaxAmount = totalTaxAmount.add(bTaxAmount);
					totalNoTaxAmoint = totalNoTaxAmoint.add(bNoTaxAmount);
				}
			}
			Map returnMap = new HashMap();
			returnMap.put("totaltaxamount", totalTaxAmount);
			returnMap.put("totalnotaxamount", totalNoTaxAmoint);
			returnMap.put("bundList", bundDetaiList);
			addJSONObject(returnMap);
		}
		return "success";
	}

	/**
	 * 获取买赠捆绑的促销信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getSalesOrderPromotionInfo() throws Exception {
		String groupid = request.getParameter("groupid");
		String goodslist = request.getParameter("goodslist");
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
		PromotionPackageGroup promotionPackageGroup = salesOrderService.getOrderGoodsPromotionDetailInfo(groupid);
		Map map = new HashMap();
		if (null != promotionPackageGroup) {
			if ("2".equals(promotionPackageGroup.getPtype()) && StringUtils.isNotEmpty(goodslist)) {
				JSONArray jsonArray = JSONArray.fromObject(goodslist);
				BigDecimal[] goodsArr = new BigDecimal[jsonArray.size()];
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					BigDecimal unitnum = new BigDecimal(jsonObject.getString("unitnum"));
					goodsArr[i] = unitnum;
				}
				// 排序商品的数量 从小到大
				for (int i = 0; i < goodsArr.length; i++) {
					for (int j = i; j < goodsArr.length; j++) {
						if (goodsArr[i].compareTo(goodsArr[j]) > 0) {
							BigDecimal temp = goodsArr[i];
							goodsArr[i] = goodsArr[j];
							goodsArr[j] = temp;
						}
					}
				}
				List<PromotionPackageGroupDetail> list = promotionPackageGroup.getGroupDetails();
				if (goodsArr.length == list.size()) {
					BigDecimal[] bundArr = new BigDecimal[list.size()];
					for (int i = 0; i < list.size(); i++) {
						PromotionPackageGroupDetail promotionPackageGroupDetail = list.get(i);
						bundArr[i] = promotionPackageGroupDetail.getUnitnum();
					}
					for (int i = 0; i < bundArr.length; i++) {
						for (int j = i; j < bundArr.length; j++) {
							if (bundArr[i].compareTo(bundArr[j]) > 0) {
								BigDecimal temp = bundArr[i];
								bundArr[i] = bundArr[j];
								bundArr[j] = temp;
							}
						}
					}
					// 计算 捆绑购买数量
					BigDecimal bundnum = BigDecimal.ZERO;
					for (int i = 0; i < goodsArr.length; i++) {
						BigDecimal s = goodsArr[i];
						BigDecimal a = bundArr[i];
						BigDecimal bundnumTmp = goodsArr[i].divide(bundArr[i], decimalScale, BigDecimal.ROUND_HALF_UP);
						if (bundnum.compareTo(BigDecimal.ZERO) == 0 || bundnum.compareTo(bundnumTmp) == 0) {
							bundnum = bundnumTmp;
						} else {
							bundnum = BigDecimal.ZERO;
						}
					}
					map.put("bundnum", bundnum);
				}

			}
			map.put("flag", true);
			map.put("ptype", promotionPackageGroup.getPtype());
			map.put("groupid", promotionPackageGroup.getGroupid());
		} else {
			map.put("flag", false);
		}
		addJSONObject(map);
		return "success";
	}

	/**
	 * 显示促销商品修改页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String orderGoodsPromotionDetailEditPage() throws Exception {
        String orderid = request.getParameter("orderid");
		String groupid = request.getParameter("groupid");
		String storageid = request.getParameter("storageid");
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		PromotionPackageGroup promotionPackageGroup = salesOrderService.getOrderGoodsPromotionDetailInfoByOrderid(orderid,groupid);
		if (null != promotionPackageGroup) {
			request.setAttribute("promotionPackageGroup", promotionPackageGroup);
			// 买赠
			if ("1".equals(promotionPackageGroup.getPtype())) {
				List<PromotionPackageGroupDetail> list = promotionPackageGroup.getGroupDetails();
				PromotionPackageGroupDetail promotionPackageGroupDetail = null;
				List giveList = new ArrayList();
				for (PromotionPackageGroupDetail detail : list) {
					// 获取商品可用量
					StorageSummary storageSummary = null;
					if (StringUtils.isNotEmpty(storageid)) {
						storageSummary = salesOrderService.getStorageSummarySumByGoodsidAndStorageid(detail.getGoodsid(), storageid);
					} else {
						storageSummary = salesOrderService.getStorageSummarySumByGoodsid(detail.getGoodsid());
					}
					if (null != storageSummary) {
						detail.setUsablenum(storageSummary.getUsablenum());
					} else {
						detail.setUsablenum(BigDecimal.ZERO);
					}
					if ("1".equals(detail.getGtype())) {
						promotionPackageGroupDetail = detail;
					} else {
						giveList.add(detail);
					}
				}
				request.setAttribute("promotionPackageGroupDetail", promotionPackageGroupDetail);
				if (null != giveList && giveList.size() > 0) {
					String listJson = JSONUtils.listToJsonStr(giveList);
					request.setAttribute("listJson", listJson);
				}
				if (null != promotionPackageGroupDetail && null != promotionPackageGroupDetail.getGoodsInfo()) {
					String goodsInfoJson = JSONUtils.objectToJsonStr(promotionPackageGroupDetail.getGoodsInfo());
					request.setAttribute("goodsInfoJson", goodsInfoJson);
				}
				return "giveSuccess";
			} else if ("2".equals(promotionPackageGroup.getPtype())) {
				List<PromotionPackageGroupDetail> list = promotionPackageGroup.getGroupDetails();
				String listJson = JSONUtils.listToJsonStr(list);
				request.setAttribute("listJson", listJson);
				return "bundSuccess";
			}
		}
		return "success";
	}

	/**
	 * 显示销售订单版本记录页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String showOrderVersionListPage() throws Exception {
		String id = request.getParameter("id");
		List list = salesOrderService.showOrderVersionList(id);
		String listJson = JSONUtils.listToJsonStr(list);
		request.setAttribute("listJson", listJson);
		return "success";
	}

	/**
	 * 显示销售订单版本详情信息
	 * @return
	 * @throws Exception
	 */
	public String orderVersionViewPage() throws Exception {
		String id = request.getParameter("id");
		String version = request.getParameter("version");
		Order order = salesOrderService.getOrderVersion(id, version);
		if (null != order) {
			List statusList = getBaseSysCodeService().showSysCodeListByType("status");
			String jsonStr = JSONUtils.listToJsonStr(order.getOrderDetailList());
			if (null != order.getLackList()) {
				String laskGoodsjsonStr = JSONUtils.listToJsonStr(order.getLackList());
				request.setAttribute("laskJson", laskGoodsjsonStr);
			}
			request.setAttribute("saleorder", order);
			request.setAttribute("goodsJson", jsonStr);
			request.setAttribute("statusList", statusList);
		}
		return "success";
	}

	/**
	 * 获取客户商品满赠赠送列表页面
	 * 
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2015年9月16日
	 */
	public String showFullFreeListPage() throws Exception {
		String orderid = request.getParameter("orderid");
		String customerid = request.getParameter("customerid");
		String goodsid = request.getParameter("goodsid");
		String numStr = request.getParameter("num");
		BigDecimal num = BigDecimal.ZERO;
		if (StringUtils.isNotEmpty(numStr)) {
			num = new BigDecimal(numStr);
		}
		if (StringUtils.isEmpty(orderid)) {
			orderid = null;
		}
		List dataList = salesOrderService.getFullFreeListPage(orderid, customerid, goodsid, num);
		String jsonStr = JSONUtils.listToJsonStr(dataList);
		request.setAttribute("listJson", jsonStr);
		return "success";
	}

	/**
	 * 订单模板页面显示
	 * 
	 * @return
	 * @throws Exception
	 * @author lin_xx
	 * @date 2015年9月23日
	 */
	public String salesModelPage() throws Exception {
		// Map map = CommonUtils.changeMap(request.getParameterMap());
		// map.put("state",'1');
		// pageMap.setCondition(map);
		// PageData pageData= getImportService().showImportModelData(pageMap);
		// List<ImportSet> list = pageData.getList();
		// List<String> urlList = new ArrayList<String>();
		// for(ImportSet importSet : list){
		// String url =
		// importSet.getUrl()+"?busid="+importSet.getBusid()+"&ctype="+importSet.getCtype();
		// importSet.setUrl(url);
		// }
		// request.setAttribute("list",list );
		// request.setAttribute("size",list.size() );
		return SUCCESS;
	}

	/**
	 * 显示批量添加销售订单明细界面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 2015-12-2
	 */
	public String orderDetailAddByBrandAndSortPage() throws Exception {
		String customerid = request.getParameter("customerid");
		request.setAttribute("customerid", customerid);
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		return SUCCESS;
		
	}
	/**
	 * 获取批量添加的商品
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 2015-12-2
	 */
	public String getOrderDetailByBrandAndSort() throws Exception {
		String customerid = request.getParameter("customerid");
		String date = request.getParameter("date");
		String storageid = request.getParameter("storageid");
		String goodsname = request.getParameter("goodsname");
		Map map = CommonUtils.changeMap(request.getParameterMap());
	    String brands=(String)map.get("brand");
	    String defaultsorts=(String)map.get("defaultsort");
	    String[] brandArr  =null;   
    	String[] defaultsortArr=null;
    	  if(null!=brands){
    	   brandArr = brands.split(",");
    	  }
    	  if(null!=defaultsorts){
    	   defaultsortArr = defaultsorts.split(",");
    	  }
		Map conditionMap = new HashMap();
		if(StringUtils.isNotEmpty(goodsname)){
			conditionMap.put("goodsname",goodsname);
		}
		conditionMap.put("state", "1");
		conditionMap.put("brandArr", brandArr);
		conditionMap.put("defaultsortArr", defaultsortArr);
		if(map.containsKey("id")){
			conditionMap.put("id",map.get("id"));
		}
		pageMap.setCondition(conditionMap);
		PageData pageData = salesOrderService.getGoodsByBrandAndSort(pageMap,customerid,storageid,date,brands,defaultsorts);
		
		addJSONObject(pageData);
		return SUCCESS;
		
	}
	/**
	 * 添加批量商品明细
	* 
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 2015-12-2
	 */
	public String AddOrderDetailByBrandAndSort() throws Exception {
		String orderDetailJson = request.getParameter("jsonDetail");
		List<OrderDetail> orderDetailList = JSONUtils.jsonStrToList(orderDetailJson, new OrderDetail());
		List list=salesOrderService.AddOrderDetailByBrandAndSort(orderDetailList);
		addJSONArray(list);
		return "success";
		
	}
	
	/**
	 * 检测是否有特价
	 *  
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 2015-12-2
	 */
	public String checkOffprice() throws Exception {
		String goodsid = request.getParameter("goodsid");
		String customerid = request.getParameter("customerid");
		String boxnum = request.getParameter("boxnum");
		String auxnum = request.getParameter("auxnum"); //辅计量单位数量
		String overnum = request.getParameter("overnum"); //主单位余数
		String date = request.getParameter("date");
		BigDecimal num = (new BigDecimal(boxnum)).multiply(new BigDecimal(auxnum)).add(new BigDecimal(overnum));
		
		Map result =salesOrderService.checkOffprice(goodsid,customerid,date,num);
		
		addJSONObject(result);
		return "success";
		
	}

    /**
     * 第一次新增保存成功后提醒框
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016-1-12
     */
    public String messageRedictPage() throws Exception {
        request.setAttribute("id",request.getParameter("id"));
        return SUCCESS;
    }

    /**
     * 订单折扣页
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016-1-12
     */
    public String orderDetailDiscountAddPage() throws Exception{
		SysParam discountTypeSysParam = getBaseSysParamService().getSysParam("repartitionType");
		if(null != discountTypeSysParam){
			request.setAttribute("repartitionType",discountTypeSysParam.getPvalue());
		}else{
			request.setAttribute("repartitionType","0");
		}
        return SUCCESS;
    }

    public String getPromotionInfoByGroupid() throws Exception{

        String groupid = request.getParameter("groupid");
        PromotionPackageGroup group = getSalesPromotionService().getBundle(groupid);
        if(null != group ){
            List list = group.getGroupDetails();
            jsonResult = new HashMap();
            jsonResult.put("rows", list);
        }
        return SUCCESS;
    }

    public String modifyGoodsContractPrice() throws Exception {

        String id = request.getParameter("id");
        String goodsid = request.getParameter("goodsid");
        String price = request.getParameter("price");

        Map map = salesOrderService.modifyGoodsContractPrice(id,goodsid,price);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 修改订单明细商品价格 → 最新采购价
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Oct 27, 2016
     */
    public String setOrderNewbuyprice() throws Exception {

        String goodsStr = request.getParameter("goodsStr");

        List<OrderDetail> detailList = JSONUtils.jsonStrToList(goodsStr, new OrderDetail());

        for(OrderDetail detail : detailList) {

            GoodsInfo goods = getGoodsInfoByID(detail.getGoodsid());
            if(goods == null) {
                continue;
            }
            BigDecimal newBuyPrice = goods.getNewbuyprice() == null ? BigDecimal.ZERO : goods.getNewbuyprice();
            BigDecimal boxnum = goods.getBoxnum() == null ? BigDecimal.ZERO : goods.getBoxnum();
            detail.setTaxprice(newBuyPrice);
            detail.setBoxprice(newBuyPrice.multiply(boxnum));

            detail.setTaxamount(newBuyPrice.multiply(detail.getUnitnum()));

            String taxtype = goods.getDefaulttaxtype();
            TaxType taxType = getTaxType(taxtype);
            if(taxType != null) {

                BigDecimal noTaxPrice = computeTax(1, newBuyPrice, taxType.getRate());
                BigDecimal noTaxAmount = computeTax(1, detail.getTaxamount(), taxType.getRate());
                detail.setNotaxprice(noTaxPrice);
                detail.setNotaxamount(noTaxAmount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
                detail.setTax(detail.getTaxamount().subtract(detail.getNotaxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
            }

        }

        addJSONArray(detailList);
        return SUCCESS;
    }

	/**
	 * 修改订单明细商品价格 → 采购价（最高采购价）
	 *
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2017-01-11
	 */
	public String setOrderHighestbuyprice()throws Exception{
		String goodsStr = request.getParameter("goodsStr");

		List<OrderDetail> detailList = JSONUtils.jsonStrToList(goodsStr, new OrderDetail());

		for(OrderDetail detail : detailList) {

			GoodsInfo goods = getGoodsInfoByID(detail.getGoodsid());
			if(goods == null) {
				continue;
			}
			BigDecimal newBuyPrice = goods.getHighestbuyprice() == null ? BigDecimal.ZERO : goods.getHighestbuyprice();
			BigDecimal boxnum = goods.getBoxnum() == null ? BigDecimal.ZERO : goods.getBoxnum();
			detail.setTaxprice(newBuyPrice);
			detail.setBoxprice(newBuyPrice.multiply(boxnum));

			detail.setTaxamount(newBuyPrice.multiply(detail.getUnitnum()));

			String taxtype = goods.getDefaulttaxtype();
			TaxType taxType = getTaxType(taxtype);
			if(taxType != null) {

				BigDecimal noTaxPrice = computeTax(1, newBuyPrice, taxType.getRate());
				BigDecimal noTaxAmount = computeTax(1, detail.getTaxamount(), taxType.getRate());
				detail.setNotaxprice(noTaxPrice);
				detail.setNotaxamount(noTaxAmount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
				detail.setTax(detail.getTaxamount().subtract(detail.getNotaxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
			}

		}

		addJSONArray(detailList);
		return SUCCESS;
	}

    /**
     * 修改订单明细商品价格 → 最新批次价
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Oct 27, 2016
     */
    public String setOrderBatchprice() throws Exception {

        String goodsStr = request.getParameter("goodsStr");

        List<OrderDetail> detailList = JSONUtils.jsonStrToList(goodsStr, new OrderDetail());

        for(OrderDetail detail : detailList) {

            GoodsInfo goods = getGoodsInfoByID(detail.getGoodsid());
            if(goods == null) {
                continue;
            }

            BigDecimal lastestBuyPrice = null;
            // 批次号为空，取最新采购价
            if(StringUtils.isEmpty(detail.getBatchno())) {

                lastestBuyPrice = goods.getNewbuyprice();

            // 批次号不为空，取该批次最新进价
            } else {

                Map map = purchaseForSalesService.selectLastestBuyPriceByGoodsAndBatchno(goods.getId(), CommonUtils.emptyToNull(detail.getBatchno()));

                if(map == null) {
                } else {
                    lastestBuyPrice = (BigDecimal) map.get("taxprice");
                }

                if(lastestBuyPrice == null) {
                    lastestBuyPrice = goods.getNewbuyprice();
                }
            }

            if(lastestBuyPrice == null) {
                continue;
            }

            BigDecimal boxnum = goods.getBoxnum() == null ? BigDecimal.ZERO : goods.getBoxnum();
            detail.setTaxprice(lastestBuyPrice);
            detail.setBoxprice(lastestBuyPrice.multiply(boxnum));

            detail.setTaxamount(lastestBuyPrice.multiply(detail.getUnitnum()));

            String taxtype = goods.getDefaulttaxtype();
            TaxType taxType = getTaxType(taxtype);
            if(taxType != null) {

                BigDecimal noTaxPrice = computeTax(1, lastestBuyPrice, taxType.getRate());
                BigDecimal noTaxAmount = computeTax(1, detail.getTaxamount(), taxType.getRate());
                detail.setNotaxprice(noTaxPrice);
                detail.setNotaxamount(noTaxAmount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
				detail.setTax(detail.getTaxamount().subtract(detail.getNotaxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
            }

        }

        addJSONArray(detailList);
        return SUCCESS;
    }
	/**
	 * 全局导出订单列表
	 * @param
	 * @return void
	 * @throws
	 * @author luoqiang
	 * @date Jun 26, 2017
	 */
	public void exportOrderListData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		Map queryMap = new HashMap();
		queryMap.put("isflag", "true");
		String query = (String) map.get("param");
		JSONObject object = JSONObject.fromObject(query);
		for (Object k : object.keySet()) {
			Object v = object.get(k);
			if(StringUtils.isNotEmpty((String) v)){
				queryMap.put(k.toString(), (String) v);
			}
		}
		pageMap.setCondition(queryMap);
		PageData pageData = salesOrderService.getOrderData(pageMap);
		List list = pageData.getList();
		if(null != pageData.getFooter()){
			List footer = pageData.getFooter();
			list.addAll(footer);
		}
		ExcelUtils.exportAnalysExcel(map, list);
	}
}
