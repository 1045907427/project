/**
 * @(#)DemandAction.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Oct 24, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.sales.model.Demand;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhengziyong
 */
public class DemandAction extends BaseSalesAction {

	public String demandPage() throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		request.setAttribute("id", id);
		request.setAttribute("type", type);
		return SUCCESS;
	}
	
	public String demandViewPage() throws Exception{
		String  id = request.getParameter("id");
		Demand demand = salesDemandService.getDemand(id);
		if(demand == null){
			return SUCCESS;
		}
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr = JSONUtils.listToJsonStr(demand.getDetailList());
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
		request.setAttribute("demand", demand);
		request.setAttribute("goodsJson", jsonStr);
		request.setAttribute("statusList", statusList);
		return SUCCESS;
	}
	
	/**
	 * 要货申请列表页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 24, 2013
	 */
	public String demandListPage() throws Exception{
		request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
		return SUCCESS;
	}
	
	public String getDemandList() throws Exception{
		SysUser sysUser = getSysUser();
		Map map = CommonUtils.changeMap(request.getParameterMap());
		map.put("userid", sysUser.getUserid());
		pageMap.setCondition(map);
		addJSONObject(salesDemandService.getDemanData(pageMap));
		return SUCCESS;
	}

	/**
	 * 判断要货申请单审核或生成订单时，要货单据是否重复，判断当天是否允许放单，是否存在超信用额度问题，是否超账期
	 * @return
	 * @throws Exception
	 */
	public String canAuditDemand() throws Exception{
		String customerId = request.getParameter("customerId");
		//判断要货单据在最近几天内是否重复
		String ids = request.getParameter("ids");
		boolean flag = false;
		String msg = "";
		if(null!=ids && !"".equals(ids)){
			String[] idarr = ids.split(",");
			for(String id : idarr){
				boolean isrepeat = getSalesDemandService().checkDemandRepeat(id);
				if(!isrepeat){
					if("".equals(msg)){
						msg +="单据:"+id;
					}else{
						msg +=","+id;
					}
				}


			}
			if(!"".equals(msg)){
				String sysparm = getSysParamValue("checkOrderRepeatDays");
				if(StringUtils.isEmpty(sysparm) || !StringUtils.isNumeric(sysparm)){
					sysparm = "3";
				}
				msg +="在最近"+sysparm+"天内存在重复情况。";
			}
			for(String id : idarr){

				Demand demand = getSalesDemandService().getDemand(id);
				if(demand == null || "1".equals(demand.getStatus())) {
					if("".equals(msg)){
						msg +="单据:"+id;
					}else{
						msg +=","+id;
					}
				}
			}
			if(!"".equals(msg)){
				String sysparm = getSysParamValue("checkOrderRepeatDays");
				if(StringUtils.isEmpty(sysparm) || !StringUtils.isNumeric(sysparm)){
					sysparm = "3";
				}
				msg +="不存在或者已审核。";
			}
		}

		Map rmap = getSalesDemandService().isReceivablePassDateByIds(ids);
		boolean pFlag = false;
		if(null!=rmap){
			pFlag = (Boolean)rmap.get("flag");
		}
		if(pFlag){
			flag = false;
			if(!"".equals(msg)){
				msg += "</br>";
			}
			if(rmap.containsKey("msg")){
				String pmsg = (String) rmap.get("msg");
				if(StringUtils.isNotEmpty(pmsg)){
					msg += pmsg;
				}
			}
		}
		else if(!isReceivableInCredit(customerId)){
			flag = false;
			if(!"".equals(msg)){
				msg += "</br>该客户存在超信用额度问题";
			}else{
				msg = "该客户存在超信用额度问题";
			}
		}
		else {
			flag = true;
		}
		Map map = new HashMap();
		map.put("msg", msg);
		map.put("flag", flag);
		addJSONObject(map);
		return SUCCESS;
	}
	
	@UserOperateLog(key="Demand",type=3)
	public String auditDemand() throws Exception{
		String id = request.getParameter("id");

		String[] srcIds = id.split(",");
		List<String> lockIds = new ArrayList<String>();
		List<String> nolockIds = new ArrayList<String>();

		for(String targetId : srcIds) {
			boolean lock = lockData("t_sales_demand", targetId);
			if(lock) {
				nolockIds.add(targetId);
			} else {
				lockIds.add(targetId);
			}
		}

		if(lockIds.size() > 0) {
			addJSONObject("msg", "单据[" + StringUtils.join(nolockIds.toArray()) + "]已被锁定，请刷新后再试！");
			return SUCCESS;
		}

		String billid = getSalesDemandService().auditDemand(StringUtils.join(nolockIds.toArray(), ","));

		addJSONObject("result", billid);
		boolean flag = false;
		if(StringUtils.isNotEmpty(billid) && !"canot".equals(billid)){
			flag = true;
		}
		addLog("销售要货申请单审核 编号："+id, flag);
		return SUCCESS;
	}
	
	@UserOperateLog(key="Demand",type=4)
	public String deleteDemand() throws Exception{
		String ids = request.getParameter("id");
		if(ids.endsWith(",")){
			ids.substring(0, ids.length()-1);
		}
		String[] idarr = ids.split(",");
		String success = "";
		String fail = "";
		boolean flag = false;
		for(String id : idarr){
			boolean delfalg = salesDemandService.deleteDemand(id);
			if(delfalg){
				flag = true;
				if("".equals(success)){
					success = "成功编号：";
				}
				success += id+",";
			}else{
				if("".equals(fail)){
					fail = "失败编号:";
				}
				fail += id+",";
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", success+" "+fail);
		addJSONObject(map);
		addLog("销售要货申请单删除 "+success+" "+fail, flag);
		return SUCCESS;
	}
	/**
	 * 要货申请单转直营销售单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 9, 2014
	 */
	@UserOperateLog(key="Demand",type=3)
	public String updateDemandToOrderCar() throws Exception{
		String id = request.getParameter("id");
		Map map = salesDemandService.updateDemandToOrderCar(id);
		addJSONObject(map);
		addLog("要货单转直营销售单 编号："+id, map);
		return SUCCESS;
	}
	/**
	 * 要货申请图片列表页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date Oct 24, 2013
	 */
	public String demandListImagePage() throws Exception{
//		request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
		return SUCCESS;
	}
	/**
	 * 获取当日业务员展示页面图片列表的数据
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Dec 26, 2016
	 */
	public String getDemandImageData(){
		List list=salesDemandService.getDemandImageData();
		Map map=new HashMap();
		map.put("list",list);
		addJSONObject(map);
		return SUCCESS;
	}
	/**
	 * 获取当日业务员展示页面表格的数据
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Dec 26, 2016
	 */
	public String getDemandImageList() throws Exception{
		List list=salesDemandService.getDemandImageList();
		String taxamount=salesDemandService.getDemandTotalToday();
		Map map=new HashMap();
		map.put("list",list);
		map.put("taxamount",taxamount);
		addJSONObject(map);
		return SUCCESS;
	}
	public String showPersonnelDemandDataPage(){
		String personnelid=request.getParameter("id");
		request.setAttribute("personnelid",personnelid);
		return SUCCESS;
	}
	public String getPersonnelDemandData() throws Exception{
		String personnelid=request.getParameter("id");
		Map map=new HashMap();
		map.put("personnelid", personnelid);
		pageMap.setCondition(map);
		PageData pageData=salesDemandService.getPersonnelDemandData(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	public String showDemandPersonnelDataDetailPage() throws Exception{
		String  id = request.getParameter("id");
		Demand demand = salesDemandService.getDemand(id);
		if(demand == null){
			return SUCCESS;
		}
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr = JSONUtils.listToJsonStr(demand.getDetailList());
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
		request.setAttribute("demand", demand);
		request.setAttribute("goodsJson", jsonStr);
		request.setAttribute("statusList", statusList);
		return SUCCESS;
	}

	/**
	 * 要货单关联订货单生成订单
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Jan 19, 2018
	 */
	@UserOperateLog(key = "Demand", type = 3)
	public String addOrderByDemandAndOrderGoods() throws Exception {
		String id = request.getParameter("id");
		//关联的订货单号
		String ordergoodsid=request.getParameter("ordergoodsid");
		String billid = getSalesDemandService().addOrderByDemandAndOrderGoods(id,ordergoodsid);
		addJSONObject("result", billid);
		boolean flag = false;

		if(StringUtils.isNotEmpty(billid) && !"canot".equals(billid)){
			flag = true;
		}

		addLog("销售要货单"+id+"关联订货单"+ordergoodsid, flag);
		return SUCCESS;
	}
}

