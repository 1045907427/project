/**
 * @(#)OweOrder.java
 *
 * @author wanghongteng
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月21日 wanghongteng 创建版本
 */
package com.hd.agent.sales.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import com.hd.agent.sales.model.OweOrderDetail;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.sales.model.OweOrder;

/**
 * 
 * 
 * @author wanghongteng
 */
/**
 * @author Administrator
 *
 */
public class OweOrderAction extends BaseSalesAction {

	/**
	 * 显示销售欠单页面
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 9,24,2015
	 */
	public String oweOrderListPage() throws Exception{
		String ids=request.getParameter("id");
		request.setAttribute("ids", ids);
    	return  "success";
    }
	/**
	 * 获取销售欠单列表
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 9,24,2015
	 */
	public String getOweOrderList() throws Exception{
		// 获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		// map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = salesOweOrderService.getOweOrderList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 销售欠单操作页面
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 9,24,2015
	 */
	public String oweOrderPage() throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		request.setAttribute("id", id);
		request.setAttribute("type", type);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return SUCCESS;
	}

	/**
	 * 销售订单修改
	 *
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Dec 11, 2013
	 */
	@UserOperateLog(key = "OweOrder", type = 3)
	public String updateOweOrder() throws Exception {
		Map map = new HashMap();
		String id = request.getParameter("id");
		String saveaudit = request.getParameter("saveaudit");
		OweOrder oweOrder = salesOweOrderService.getOweOrder(id);
		SysUser sysUser = getSysUser();
		oweOrder.setModifyuserid(sysUser.getUserid());
		oweOrder.setModifyusername(sysUser.getName());
		String oweOrderDetailJson = request.getParameter("goodsjson");
		List<OweOrderDetail> oweOrderDetails = JSONUtils.jsonStrToList(oweOrderDetailJson, new OweOrderDetail());
		oweOrder.setDetailList(oweOrderDetails);
		Map returnmap = salesOweOrderService.updateOweOrder(oweOrder);
		if((Boolean)returnmap.get("flag") && "saveaudit".equals(saveaudit)){
			Map auditresultMap=salesOweOrderService.OweOrderAddOrder(id);
			addLog("欠货单编号：" + id+"生成销售订单", (Boolean)auditresultMap.get("flag"));
			auditresultMap.put("savetype","saveaudit");
			addJSONObject(auditresultMap);
		}else{
			addLog("修改欠货单 编号：" + id, (Boolean)returnmap.get("flag"));
			returnmap.put("savetype","save");
			addJSONObject(returnmap);
		}
		return SUCCESS;
	}

	/**
	 * 销售欠单查看页面
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 9,24,2015
	 */
	public String oweOrderViewPage() throws Exception{
		String  id = request.getParameter("id");
		OweOrder oweOrder = salesOweOrderService.getOweOrder(id);
		if(oweOrder == null){
			return SUCCESS;
		}
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr = JSONUtils.listToJsonStr(oweOrder.getDetailList());
		if(oweOrder.getStatus().equals("0")){
		    request.setAttribute("type", "edit");
		}
		else if(oweOrder.getStatus().equals("1")){
			 request.setAttribute("type", "view");
		}
		else{
			 request.setAttribute("type", "close");
		}
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
		request.setAttribute("oweOrder", oweOrder);
		request.setAttribute("goodsJson", jsonStr);
		request.setAttribute("statusList", statusList);
		return SUCCESS;
	}
	/**
	 * 检查销售订单是否超账期和信用额度问题
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 9,24,2015
	 */
	public String checkAddOrder() throws Exception{
		String customerIds = request.getParameter("customerIds");
		//判断单据是否超账期
		String ids = request.getParameter("ids");
		boolean flag = false;
		String msg = "";
		if (null != customerIds && !"".equals(customerIds)) {
			String[] customerIdrr = customerIds.split(",");
			for (String customerId : customerIdrr) {
				if (isReceivablePassDateByCustomerid(customerId)) {
					flag = false;
					if (!"".equals(msg)) {
						msg += "</br>该客户存在超账期问题";
					} else {
						msg = "该客户存在超账期问题";
					}
				} else if (!isReceivableInCredit(customerId)) {
					flag = false;
					if (!"".equals(msg)) {
						msg += "</br>该客户存在超账期问题";
					} else {
						msg = "该客户存在超信用额度问题";
					}
				} else {
					flag = true;
				}
			}
		}
		Map map = new HashMap();
		map.put("msg", msg);
		map.put("flag", flag);
		addJSONObject(map);
		return SUCCESS;
	}
	/**
	 * 生成销售订单
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 9,24,2015
	 */
	@UserOperateLog(key = "OweOrder", type = 3)
	public String OweOrderAddOrder() throws Exception{

		return "success";
	}
	/**
	 * 批量生成销售订单
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 9,24,2015
	 */
	@UserOperateLog(key = "OweOrder", type = 3)
	public String OweOrderAddOrders() throws Exception{
		String ids = request.getParameter("ids");
		String[] idsarr = ids.split(",");
		int snum=0,fnum=0;
		String msg="",fmsg="",smsg="";
		for (String id : idsarr){
			Map map=salesOweOrderService.OweOrderAddOrder(id);
			addLog("欠货单编号：" + id+"生成销售订单", (Boolean)map.get("flag"));
			fmsg += (String)map.get("msg")+"。<br>";
			fnum++;
		}
		msg =  fmsg + "共" +fnum + "条数据<br>";
		Map map = new HashMap();
		map.put("msg", msg);
		addJSONObject(map);
		return "success";
	}
	/**
	 * 删除销售欠单
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 9,24,2015
	 */
	@UserOperateLog(key = "OweOrder", type = 4)
	public String deleteOweOrder() throws Exception{
		String ids = request.getParameter("ids");
		String[] idsarr = ids.split(",");
		int snum=0,fnum=0;
		String msg="",fmsg="",smsg="";
		for (String id : idsarr){
			Map map=salesOweOrderService.deleteOweOrder(id);
			addLog("删除欠货单 编号：" + id, (Boolean)map.get("flag"));
			if(!(Boolean)map.get("flag")){
				if (StringUtils.isEmpty(fmsg)) {
					fmsg = id;
				} else {
					fmsg += "," + id;
				}
				fnum++;
			}
		}
		if (fnum>0){
			msg = "编号：" + fmsg + "删除失败。共" + fnum + "条数据<br>";
			Map map = new HashMap();
			map.put("flag",false);
			map.put("msg", msg);
			addJSONObject(map);
			return "success";
		}
		else{
			Map map = new HashMap();
			map.put("flag",true);
			addJSONObject(map);
			return "success";
		}
		
	}
	/**
	 * 发货通知单生成销售欠单
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 9,24,2015
	 */
	public String addOweOrderByDispatchBill() throws Exception{
		String id = request.getParameter("id");
		Map map=salesOweOrderService.addOweOrderByDispatchBill(id);
		addJSONObject(map);
		return "success";
		
	}
	/**
	 * 销售订单生成销售欠单
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 9,24,2015
	 */
	public String addOweOrderByOrder() throws Exception{
		String id = request.getParameter("id");
		Map map=salesOweOrderService.addOweOrderByOrder(id);
		addJSONObject(map);
		return "success";
		
	}
	/**
	 * 关闭销售欠单
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 9,24,2015
	 */
	@UserOperateLog(key = "OweOrder", type = 3)
	public String closeOweOrder() throws Exception{
		String ids = request.getParameter("ids");
		String[] idsarr = ids.split(",");
		int snum=0,fnum=0;
		String msg="",fmsg="",smsg="";
		for (String id : idsarr){
			Map map=salesOweOrderService.closeOweOrder(id);
			addLog("关闭欠货单 编号：" + id, (Boolean)map.get("flag"));
			if(!(Boolean)map.get("flag")){
				if (StringUtils.isEmpty(fmsg)) {
					fmsg = id;
				} else {
					fmsg += "," + id;
				}
				fnum++;
			}
		}
		if (fnum>0){
			msg = "编号：" + fmsg + "关闭失败。共" + fnum + "条数据<br>";
			Map map = new HashMap();
			map.put("flag",false);
			map.put("msg", msg);
			addJSONObject(map);
			return "success";
		}
		else{
			Map map = new HashMap();
			map.put("flag",true);
			addJSONObject(map);
			return "success";
		}
	}
	/**
	 * 检查订单是否存在
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 9,24,2015
	 */
	public String checkOweOrderId() throws Exception{
		String id = request.getParameter("id");
		Map map=salesOweOrderService.checkOweOrderId(id);
		addJSONObject(map);
		return "success";
		
	}
}

