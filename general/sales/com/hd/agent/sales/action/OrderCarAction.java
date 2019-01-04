/**
 * @(#)QuotationAction.java
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
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.sales.model.OrderCar;
import com.hd.agent.sales.model.OrderCarDetail;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 销售订单
 * @author zhengziyong
 */
public class OrderCarAction extends BaseSalesAction {
	
	private OrderCar ordercar;
	
	public OrderCar getOrdercar() {
		return ordercar;
	}
	public void setOrdercar(OrderCar ordercar) {
		this.ordercar = ordercar;
	}

	/**
	 * 显示订单新增页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Aug 17, 2013
	 */
	public String showOrderCarAddPage() throws Exception{
		return "success";
	}
	
	/**
	 * 销售订单页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 3, 2013
	 */
	public String orderCarPage() throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		request.setAttribute("id", id);
		request.setAttribute("type", type);
		return SUCCESS;
	}

	/**
	 * 显示零售订单新增页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-06-30
	 */
	public String orderCarAddPage()throws Exception{
		request.setAttribute("date",CommonUtils.getTodayDataStr());
		return SUCCESS;
	}

	/**
	 * 新增直营销售单（零售订单）
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-06-30
	 */
	@UserOperateLog(key="OrderCar",type=2)
	public String addOrderCar()throws Exception{
		String orderDetailJson = request.getParameter("goodsjson");
		List<OrderCarDetail> orderDetailList = JSONUtils.jsonStrToList(orderDetailJson, new OrderCarDetail());
		ordercar.setOrderDetailList(orderDetailList);
		ordercar.setStatus("2");
		Map map = salesOrderCarService.addOrderCar(ordercar);
		addJSONObject(map);
		addLog("零售订单新增 编号："+(String)map.get("id"), map.get("flag").equals(true));
		return SUCCESS;
	}

	public String orderCarEditPage() throws Exception{
		String  id = request.getParameter("id");
		OrderCar order = salesOrderCarService.getOrderCar(id);
		if(order == null){
			return "viewSuccess";
		}else{
			List statusList = getBaseSysCodeService().showSysCodeListByType("status");
			String jsonStr = JSONUtils.listToJsonStr(order.getOrderDetailList());
			request.setAttribute("settletype", getSettlementListData());
			request.setAttribute("paytype", getPaymentListData());
			request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
			request.setAttribute("order", order);
			request.setAttribute("goodsJson", jsonStr);
			request.setAttribute("statusList", statusList);
			request.setAttribute("isLocked", lockData("t_sales_order_car", order.getId()));
			if("2".equals(order.getStatus())){
				return "editSuccess";
			}else{
				return "viewSuccess";
			}
			
		}
	}
	
	/**
	 * 修改直营销售单
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 29, 2013
	 */
	@UserOperateLog(key="OrderCar",type=3)
	public String updateOrderCar() throws Exception{
		String orderDetailJson = request.getParameter("goodsjson");
		List<OrderCarDetail> orderDetailList = JSONUtils.jsonStrToList(orderDetailJson, new OrderCarDetail());
		ordercar.setOrderDetailList(orderDetailList);
		boolean flag = salesOrderCarService.updateOrderCar(ordercar);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("backid", ordercar.getId());
		addJSONObject(map);
		addLog("直营销售单修改 编号："+ordercar.getId(), flag);
		return SUCCESS;
	}
	
	/**
	 * 销售订单查看页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 10, 2013
	 */
	public String orderCarViewPage() throws Exception{
		String  id = request.getParameter("id");
		OrderCar order = salesOrderCarService.getOrderCar(id);
		if(order == null){
			return SUCCESS;
		}
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr = JSONUtils.listToJsonStr(order.getOrderDetailList());
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
		request.setAttribute("order", order);
		request.setAttribute("goodsJson", jsonStr);
		request.setAttribute("statusList", statusList);
		request.setAttribute("isLocked", lockData("t_sales_order_car", order.getId()));
		return SUCCESS;
	}
	
	/**
	 * 销售订单列表页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 10, 2013
	 */
	public String orderCarListPage() throws Exception{
		request.setAttribute("storageList", getStorageInfoAllList());
		return SUCCESS;
	}
	
	/**
	 * 获取订单列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 20, 2013
	 */
	public String getOrderCarList() throws Exception{
		SysUser sysUser = getSysUser();
		Map map = CommonUtils.changeMap(request.getParameterMap());
		map.put("userid", sysUser.getUserid());
		pageMap.setCondition(map);
		PageData pageData = salesOrderCarService.getOrderCarData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 审核只用销售单订单
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 16, 2013
	 */
	@UserOperateLog(key="OrderCar",type=3)
	public String auditOrderCar() throws Exception{
		String id = request.getParameter("id");
		Map result = salesOrderCarService.auditOrderCar(id,null);
		addJSONObject(result);
		addLog("直营销售单审核 编号："+id, result);
		return SUCCESS;
	}
	/**
	 * 反审直营销售单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2014
	 */
	@UserOperateLog(key="OrderCar",type=3)
	public String oppauditOrderCar() throws Exception{
		String id = request.getParameter("id");
		Map result = salesOrderCarService.oppauditOrderCar(id);
		addJSONObject(result);
		addLog("直营销售单反审 编号："+id, result);
		return "success";
	}
	/**
	 * 批量审核
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Aug 5, 2013
	 */
	@UserOperateLog(key="OrderCar",type=3)
	public String auditMultiOrderCar() throws Exception{
		int failure = 0;
		int success = 0;
		int noaudit = 0;
		String successid = "";
		String failureid = "";
		String msg = "";
		String ids = request.getParameter("ids");
		boolean sFlag = false;
		if (StringUtils.isNotEmpty(ids)) {
			if (ids.endsWith(",")) {
				ids = ids.substring(0, ids.length() - 1);
			}
			String[] idArr = ids.split(",");
			for (String id : idArr) {
				Map map = salesOrderCarService.auditMultiOrderCar(id);
				failure += (Integer)map.get("failure");
				success += (Integer)map.get("success");
				noaudit += (Integer)map.get("noaudit");
				if(StringUtils.isEmpty(successid)){
					successid = (String)map.get("successid");
				}else{
					successid = (String)map.get("successid") + successid;
				}
				if(StringUtils.isEmpty(failureid)){
					failureid = (String)map.get("failureid");
				}else{
					failureid = (String)map.get("failureid") + failureid;
				}
				if(StringUtils.isEmpty(msg)){
					msg = (String)map.get("msg");
				}else{
					msg = (String)map.get("msg") + msg;
				}
			}
			sFlag =true;
		}
		Map retmap = new HashMap();
		retmap.put("failure",failure);
		retmap.put("success",success);
		retmap.put("noaudit",noaudit);
		retmap.put("successid",successid);
		retmap.put("failureid",failureid);
		retmap.put("msg",msg);
		retmap.put("flag",sFlag);
		addJSONObject(retmap);
		addLog("直营销售单批量审核 成功编号："+successid+" .失败编号："+failureid, retmap);
		return SUCCESS;
	}
	/**
	 * 车销打印页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-4-9
	 */
	public String orderCarPrintListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		request.setAttribute("today", today);
		String printlimit=getPrintLimitInfo();

		request.setAttribute("printlimit", printlimit);
		return SUCCESS;
	}
	/**
	 * 车销打印数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-4-9
	 */
	public String showOrderCarPrintListData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		map.put("statusarr", "3,4");
		pageMap.setCondition(map);
		PageData pageData=salesOrderCarService.showOrderCarPrintListData(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	
	/**
	 * 显示车销订单明细添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 7, 2014
	 */
	public String orderCarDetailAddPage() throws Exception{
		String customerId = request.getParameter("cid");
		Map colMap = getEditAccessColumn("t_sales_order_car_detail");
		request.setAttribute("colMap", colMap);
		request.setAttribute("customerId", customerId);
		request.setAttribute("date", CommonUtils.getTodayDataStr());
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}
	/**
	 * 显示车销订单明细修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 8, 2014
	 */
	public String orderCarDetailEditPage() throws Exception{
		Map colMap = getEditAccessColumn("t_sales_order_car_detail");
		request.setAttribute("colMap", colMap);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}
	/**
	 * 删除车销订单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 9, 2014
	 */
	@UserOperateLog(key="OrderCar",type=4)
	public String deleteOrderCar() throws Exception{
		String ids = request.getParameter("ids");
		Map map = salesOrderCarService.deleteMultiOrderCar(ids);
		addJSONObject(map);
		addLog("直营销售单批量作废 编号："+ids, map);
		return "success";
	}
	/**
	 * 车销订单转换成要货申请单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 9, 2014
	 */
	@UserOperateLog(key="OrderCar",type=3)
	public String updateOrderCarToDemand() throws Exception{
		String id = request.getParameter("id");
		Map map = salesOrderCarService.updateOrderCarToDemand(id);
		addJSONObject(map);
		addLog("直营销售转要货单 编号："+id, map);
		return "success";
	}
	/**
	 * 获取车销订单是否重复的信息
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 25, 2014
	 */
	public String canAuditOrderCar() throws Exception{
		String ids = request.getParameter("ids");
		//判断要货单据在最近几天内是否重复
		boolean flag = true;
		String msg = "";
		if(null!=ids && !"".equals(ids)){
			String[] idarr = ids.split(",");
			for(String id : idarr){
				boolean isrepeat = salesOrderCarService.checkOrderCarRepeat(id);
				if(!isrepeat){
					if("".equals(msg)){
						msg +="单据:"+id;
					}else{
						msg +=","+id;
					}
					flag = false;
				}
			}
			if(!"".equals(msg)){
				String sysparm = getSysParamValue("checkOrderRepeatDays");
				if(StringUtils.isEmpty(sysparm) || !StringUtils.isNumeric(sysparm)){
					sysparm = "3";
				}
				msg +="在最近"+sysparm+"天内存在重复情况.";
			}
		}
		Map map = new HashMap();
		map.put("msg", msg);
		map.put("flag", flag);
		addJSONObject(map);
		return "success";
	}
}

