package com.hd.agent.account.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.model.PaymentVoucher;
import com.hd.agent.account.model.PaymentVoucherDetail;
import com.hd.agent.account.service.IPaymentVoucherService;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class PaymentVoucherAction extends BaseFilesAction  {
	/**
	 * 交款单业务类
	 */
	private IPaymentVoucherService paymentVoucherService;

	public IPaymentVoucherService getPaymentVoucherService() {
		return paymentVoucherService;
	}

	public void setPaymentVoucherService(
			IPaymentVoucherService paymentVoucherService) {
		this.paymentVoucherService = paymentVoucherService;
	}
	
	private PaymentVoucher paymentVoucher;

	public PaymentVoucher getPaymentVoucher() {
		return paymentVoucher;
	}

	public void setPaymentVoucher(PaymentVoucher paymentVoucher) {
		this.paymentVoucher = paymentVoucher;
	}
	/**
	 * 交款单列表页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public String paymentVoucherListPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 交款单列表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public String showPaymentVoucherPageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		if(null!=map){
			if(map.containsKey("isNoPageflag")){
				map.remove("isNoPageflag");
			}
		}
		String sort = (String)map.get("sort");
		String order = (String) map.get("order");
		if(StringUtils.isEmpty(sort) || StringUtils.isEmpty(order)){
			if(map.containsKey("sort")){
				map.remove("sort");
			}
			if(map.containsKey("order")){
				map.remove("order");
			}
			pageMap.setOrderSql(" businessdate desc , id desc");
		}
		pageMap.setCondition(map);
		PageData pageData=paymentVoucherService.showPaymentVoucherPageList(pageMap);
		
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	/**
	 * 交款单页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public String paymentVoucherPage() throws Exception{
		String tmp=request.getParameter("id");
		request.setAttribute("id", tmp);
		tmp=request.getParameter("type");
		request.setAttribute("type", tmp);
		return SUCCESS;
	}
	/**
	 * 添加交款单页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public String paymentVoucherAddPage() throws Exception{
		SysUser sysUser=getSysUser();
		Personnel personnel=sysUserConnectePersonnelInfo(sysUser.getUserid());
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("today", dateFormat.format(calendar.getTime()));
        if(personnel != null){
            request.setAttribute("collectuserid", personnel.getId());
        }

		return SUCCESS;
	}

	/**
	 * 添加交款单
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	@UserOperateLog(key="Account-PaymentVoucher",type=2)
	public String addPaymentVoucher() throws Exception{
		Map map=new HashMap();
		boolean flag=false;
		String addType = request.getParameter("addType");
		String paymentVoucherDetails=request.getParameter("paymentVoucherDetails");
		List<PaymentVoucherDetail> detailList=null;
		if(null!=paymentVoucherDetails && !"".equals(paymentVoucherDetails.trim())){
			detailList=JSONUtils.jsonStrToList(paymentVoucherDetails.trim(),new PaymentVoucherDetail());
			paymentVoucher.setPaymentVoucherDetailList(detailList);
		}
		
		if("temp".equals(addType)){
			paymentVoucher.setStatus("1");	//暂存
		}else if("real".equals(addType)){
			paymentVoucher.setStatus("2");
		}else{
			paymentVoucher.setStatus("1");//暂存
		}		
		if("2".equals(paymentVoucher.getStatus())){
			if(null==detailList || detailList.size()==0){
				map.put("flag", false);
				map.put("backid", "");
				map.put("msg", "保存状态下，请填写交款单明细信息");
				addJSONObject(map);
				return SUCCESS;
			}
		}

		SysUser sysUser=getSysUser();
		Personnel person=null;
        if(StringUtils.isNotEmpty(paymentVoucher.getCollectuserid())){
        	person = getPersonnelInfoById(paymentVoucher.getCollectuserid());
        }
		if(null!=person){
			paymentVoucher.setCollectuserid(person.getId());
			paymentVoucher.setCollectusername(person.getName());
		}else{
			map.put("flag", false);
			map.put("backid", "");
			map.put("msg", "抱歉，未能找到相关收款人信息，请联系管理员处理");
			addJSONObject(map);
			return SUCCESS;
		}
		
		paymentVoucher.setAdduserid(sysUser.getUserid());
		paymentVoucher.setAddusername(sysUser.getName());
		paymentVoucher.setAdddeptid(sysUser.getDepartmentid());
		paymentVoucher.setAdddeptname(sysUser.getDepartmentname());
		paymentVoucher.setAddtime(new Date());
		flag=paymentVoucherService.addPaymentVoucherAddDetail(paymentVoucher);
		//判断是否审核
		String saveaudit = request.getParameter("saveaudit");
		if("saveaudit".equals(saveaudit) && flag){
			Map auditMap = paymentVoucherService.auditPaymentVoucher(paymentVoucher.getId());
			//addJSONObject(result);

			Boolean auditflag= (Boolean) auditMap.get("flag");
			if(null==auditflag){
				auditflag=false;
			}
			map.put("auditflag", auditflag);
			String msg = (String) auditMap.get("msg");
			map.put("msg", msg);

			Boolean nextBillFlag=(Boolean) auditMap.get("nextBillFlag");
			if(null==nextBillFlag){
				nextBillFlag=false;
			}
			map.put("nextBillFlag", nextBillFlag);

			if(auditflag){
				logStr="保存并审核交款单成功，生成收款单成功，单据编号："+paymentVoucher.getId();
			}else{
				logStr="交款单保存成功,但审核失败，单据编号："+paymentVoucher.getId();
			}

		}else if(flag){
			logStr="添加交款单成功，单据编号："+paymentVoucher.getId();
		}else{
			logStr="添加交款单失败，单据编号："+paymentVoucher.getId();
		}
		map.put("flag", flag);
		map.put("backid", paymentVoucher.getId());
		map.put("opertype", "add");
		addJSONObject(map);
		return SUCCESS;
	}
	/**
	 * 编辑交款单页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2015年3月24日
	 */
	public String paymentVoucherEditPage() throws Exception{
		String id=request.getParameter("id");
		PaymentVoucher paymentVoucher=paymentVoucherService.showPaymentVoucherAndDetail(id);
		if(null==paymentVoucher){
			paymentVoucher=new PaymentVoucher();
		}
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr="[]";
		if(null!=paymentVoucher.getPaymentVoucherDetailList()){
			jsonStr = JSONUtils.listToJsonStr(paymentVoucher.getPaymentVoucherDetailList());
		}

		request.setAttribute("statusList", statusList);
		request.setAttribute("paymentVoucher", paymentVoucher);
		request.setAttribute("paymentVoucherDetailList", jsonStr);

		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		if(!"1".equals(paymentVoucher.getStatus()) && !"2".equals(paymentVoucher.getStatus())){
			return "viewSuccess";
		}
		return SUCCESS;
	}
	/**
	 * 编辑交款单
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2015年3月24日
	 */
	@UserOperateLog(key="Account-PaymentVoucher",type=3)
	public String editPaymentVoucher() throws Exception{
		Map map = new HashMap();
		if(StringUtils.isEmpty(paymentVoucher.getId())){
			map.put("flag", false);
			map.put("msg", "未能找到要修改的信息");
			addJSONObject(map);
			return SUCCESS;
		}

		String paymentVoucherDetails=request.getParameter("paymentVoucherDetails");
		List<PaymentVoucherDetail> aodList=null;
		if(null!=paymentVoucherDetails && !"".equals(paymentVoucherDetails.trim())){
			aodList=JSONUtils.jsonStrToList(paymentVoucherDetails.trim(),new PaymentVoucherDetail());
			paymentVoucher.setPaymentVoucherDetailList(aodList);
		}

		if("2".equals(paymentVoucher.getStatus())){
			if(null==aodList || aodList.size()==0){
				map.put("flag", false);
				map.put("backid", "");
				map.put("msg", "保存状态下，请填写交款单明细信息");
				addJSONObject(map);
				return SUCCESS;
			}
		}
		PaymentVoucher oldpaymentVoucher=paymentVoucherService.showPaymentVoucherByDataAuth(paymentVoucher.getId());
		if(null==oldpaymentVoucher){
			map.put("flag", false);
			map.put("msg", "未能找到要修改的信息");
			addJSONObject(map);
			return SUCCESS;
		}

		String addType = request.getParameter("addType");
		if(!"1".equals(oldpaymentVoucher.getStatus()) && !"2".equals(oldpaymentVoucher.getStatus())){
			map.put("flag", false);
			map.put("msg", "抱歉，当前单据不可修改");
			addJSONObject(map);
			return SUCCESS;
		}
		if("1".equals(oldpaymentVoucher.getStatus())){
			if("real".equals(addType)){
				paymentVoucher.setStatus("2");
			}
		}else{
			paymentVoucher.setStatus(oldpaymentVoucher.getStatus());
		}
        Personnel personnel=getPersonnelInfoById(paymentVoucher.getCollectuserid());
        if(null!=personnel){
            paymentVoucher.setCollectusername(personnel.getName());
        }else{
            map.put("flag", false);
            map.put("backid", "");
            map.put("msg", "抱歉，未能找到相关收款人信息，请联系管理员处理");
            addJSONObject(map);
            return SUCCESS;
        }
		boolean flag=false;
		SysUser sysUser = getSysUser();
		paymentVoucher.setModifyuserid(sysUser.getUserid());
		paymentVoucher.setModifyusername(sysUser.getName());
		paymentVoucher.setModifytime(new Date());
		flag= paymentVoucherService.updatePaymentVoucherAddDetail(paymentVoucher);
		map.clear();

		//判断是否审核
		String saveaudit = request.getParameter("saveaudit");
		if("saveaudit".equals(saveaudit) && flag){
			Map auditMap = paymentVoucherService.auditPaymentVoucher(paymentVoucher.getId());
			//addJSONObject(result);
			Boolean auditflag= (Boolean) auditMap.get("flag");
			if(null==auditflag){
				auditflag=false;
			}
			map.put("auditflag", auditflag);
			String msg = (String) auditMap.get("msg");
			map.put("msg", msg);

			Boolean nextBillFlag=(Boolean) auditMap.get("nextBillFlag");
			if(null==nextBillFlag){
				nextBillFlag=false;
			}
			map.put("nextBillFlag", nextBillFlag);

			if(auditflag){
				logStr="保存并审核交款单成功，生成收款单成功，单据编号："+paymentVoucher.getId();
			}else{
				logStr="交款单保存成功,但审核失败，单据编号："+paymentVoucher.getId();
			}
		}else if(flag){
			logStr="修改交款单成功，单据编号："+paymentVoucher.getId();
		}else{
			logStr="修改交款单失败，单据编号："+paymentVoucher.getId();
		}
		map.put("backid", paymentVoucher.getId());
		map.put("opertype", "edit");
		map.put("flag", flag);
		addJSONObject(map);
		return SUCCESS;
	}

	/**
	 * 查看交款单页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2015年3月24日
	 */
	public String paymentVoucherViewPage() throws Exception{
		String id=request.getParameter("id");
		PaymentVoucher paymentVoucher=paymentVoucherService.showPaymentVoucherAndDetail(id);
		if(null==paymentVoucher){
			paymentVoucher=new PaymentVoucher();
		}
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr="[]";
		if(null!=paymentVoucher.getPaymentVoucherDetailList()){
			jsonStr = JSONUtils.listToJsonStr(paymentVoucher.getPaymentVoucherDetailList());
		}

		request.setAttribute("statusList", statusList);
		request.setAttribute("paymentVoucher", paymentVoucher);
		request.setAttribute("paymentVoucherDetailList", jsonStr);

		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		return SUCCESS;
	}


	/**
	 * 查看交款单明细添加页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2015年3月24日
	 */
	public String paymentVoucherDetailAddPage() throws Exception{

		Map colMap = getEditAccessColumn("t_account_paymentvoucher_detail");
		request.setAttribute("colMap", colMap);
		return SUCCESS;
	}

	/**
	 * 查看交款单明细编辑页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2015年3月24日
	 */
	public String paymentVoucherDetailEditPage() throws Exception{
		Map colMap = getEditAccessColumn("t_account_paymentvoucher_detail");
		request.setAttribute("colMap", colMap);
		return SUCCESS;
	}

	/**
	 * 查看交款单明细查看页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2015年3月24日
	 */
	public String paymentVoucherDetailViewPage() throws Exception{
		return SUCCESS;
	}

	/**
	 * 删除交款单
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2015年3月24日
	 */
	@UserOperateLog(key="Account-PaymentVoucher",type=4)
	public String deletePaymentVoucher() throws Exception{
		String id=request.getParameter("id");

		boolean delFlag = canTableDataDelete("t_account_paymentvoucher", id); //判断是否被引用，被引用则无法删除。
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag=paymentVoucherService.deletePaymentVoucherAndDetail(id);
		addJSONObject("flag", flag);
		if(flag){
			logStr="删除交款单成功，单据编号："+id;
		}else{
			logStr="删除交款单失败，单据编号："+id;
		}
		return SUCCESS;
	}

	/**
	 * 审核交款单
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2015年3月24日
	 */
	@UserOperateLog(key="Account-PaymentVoucher",type=0)
	public String auditPaymentVoucher() throws Exception{
		String id = request.getParameter("id");
		if(null==id || "".equals(id)){
			addJSONObject("flag", false);
			return SUCCESS;
		}
		Map map=paymentVoucherService.auditPaymentVoucher(id);
		addJSONObject(map);
		boolean flag = (Boolean) map.get("flag");
		if(flag){
			logStr="审核交款单成功，单据编号："+id;
		}else{
			logStr="审核交款单失败，单据编号："+id;
		}
		return SUCCESS;
	}

	/**
	 * 反审交款单
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2015年3月24日
	 */
	@UserOperateLog(key="Account-PaymentVoucher",type=0)
	public String oppauditPaymentVoucher() throws Exception{
		String id = request.getParameter("id");
		if(null==id || "".equals(id)){
			addJSONObject("flag", false);
			return SUCCESS;
		}
		Map map=paymentVoucherService.oppauditPaymentVoucher(id);
		addJSONObject(map);
		boolean flag = (Boolean) map.get("flag");
		if(flag){
			logStr="反审交款单成功，单据编号："+id;
		}else{
			logStr="反审交款单失败，单据编号："+id;
		}
		return SUCCESS;
	}
	
}
