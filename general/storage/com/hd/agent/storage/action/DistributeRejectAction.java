/**
 * @(#)DistributeRejectAction.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年8月4日 huangzhiqian 创建版本
 */
package com.hd.agent.storage.action;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONObject;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.GoodsInfo_MteringUnitInfo;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.delivery.service.IDistributeService;
import com.hd.agent.storage.model.StorageDeliveryEnter;
import com.hd.agent.storage.model.StorageDeliveryEnterDetail;
import com.hd.agent.storage.model.StorageDeliveryOut;
import com.hd.agent.storage.service.IDistributeRejectService;
import com.hd.agent.storage.service.impl.DistributeRejectServiceImpl;

/**
 * 供应商代配送入库单
 * 
 * @author huangzhiqian
 */
public class DistributeRejectAction extends BaseFilesAction {
	private IDistributeRejectService distributeRejectService;
	private IDistributeService distributeService;
	public void setDistributeService(IDistributeService distributeService) {
		this.distributeService = distributeService;
	}
	public IDistributeRejectService getDistributeRejectService() {
		return distributeRejectService;
	}

	public void setDistributeRejectService(
			IDistributeRejectService distributeRejectService) {
		this.distributeRejectService = distributeRejectService;
	}

	private StorageDeliveryEnter storageDeliveryEnter;

	public StorageDeliveryEnter getStorageDeliveryEnter() {
		return storageDeliveryEnter;
	}

	public void setStorageDeliveryEnter(
			StorageDeliveryEnter storageDeliveryEnter) {
		this.storageDeliveryEnter = storageDeliveryEnter;
	}

	/**
	 * 查看所有发货配送入库单页面
	 * 
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月4日
	 */
	public String showDistributeRejectListPage() {
		String billtype = request.getParameter("entertype");
		request.setAttribute("billtype", billtype);
		return SUCCESS;
	}

	/**
	 * 获取发货配送入库单列表
	 * 
	 * @return
	 * @author huangzhiqian
	 * @throws Exception
	 * @date 2015年8月4日
	 */
	public String distributeRejectList() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		pageMap.setOrderSql(" addtime desc ");
		PageData pageData = distributeRejectService
				.getStorageDeliveryEnterList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}

	/**
	 * 配送入库页面
	 * 
	 * @return
	 * @author huangzhiqian
	 * @throws Exception
	 * @date 2015年8月4日
	 */
	public String showDistributeRejectPage() throws Exception {
        Boolean flag=true;
		// 单据类型
		String billtype = request.getParameter("billtype");
		request.setAttribute("billtype", billtype);
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		if (type == null || "".equals(type.trim())) {
			type = "add";
		}
		request.setAttribute("id", id);
		request.setAttribute("type", type);
		request.setAttribute("billtype", billtype);
		StorageDeliveryEnter  storageDeliveryEnter=distributeRejectService.getDistributeRejectEnterById(id);
		if(null==storageDeliveryEnter){
			flag=false;
		}
		request.setAttribute("flag", flag);
		return SUCCESS;
	}

	/**
	 * 配送入库主页面
	 * 
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年8月5日
	 */
	public String distributeRejectAddPage() throws Exception {
		// 单据类型
		String billtype = request.getParameter("billtype");
		request.setAttribute("billtype", billtype);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("today", dateFormat.format(calendar.getTime()));

		return SUCCESS;
	}

	/**
	 * 添加 配送入库单
	 * 
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年8月5日
	 */
	@UserOperateLog(key="StorageDeliveryEnter",type=2)
	public String distributeRejectInfoAdd() throws Exception {
		Map map = new HashMap();
		SysUser user = getSysUser();
		String saveAndAudit = request.getParameter("saveAndAudit");
		if("audit".equals(saveAndAudit.trim())){
			//保存并且审核
			storageDeliveryEnter.setAudittime(new Date());
			storageDeliveryEnter.setAudituserid(user.getUserid());
			storageDeliveryEnter.setAuditusername(user.getName());
			storageDeliveryEnter.setStatus("3");//提前改变状态,后面audit直接更新入库
		}
		String detail = request.getParameter("detail");
		String foot = request.getParameter("foot");
		storageDeliveryEnter.setAdduserid(user.getUserid());
		storageDeliveryEnter.setAddusername(user.getName());
		storageDeliveryEnter.setAdddeptid(user.getDepartmentid());
		storageDeliveryEnter.setAdddeptname(user.getDepartmentname());
		storageDeliveryEnter.setAddtime(new Date());
		BindFootData(storageDeliveryEnter, foot);
		List list = JSONUtils.jsonStrToList(detail.trim(),
				new StorageDeliveryEnterDetail());
		BindDetailData(list, detail);
		boolean flag = distributeRejectService.insertDatas(storageDeliveryEnter, list);
		if(flag&&"audit".equals(saveAndAudit.trim())){
			//保存并且审核
			boolean auditflag=distributeRejectService.audit(storageDeliveryEnter, storageDeliveryEnter,false);
			addLog("代配送入库单保存审核 编号："+storageDeliveryEnter.getId(), auditflag);
		}else{
			addLog("代配送入库单保存 编号："+storageDeliveryEnter.getId(), flag);
		}
		map.put("flag", flag);
		map.put("backid", storageDeliveryEnter.getId());
		addJSONObject(map);
		request.setAttribute("billtype", storageDeliveryEnter.getBilltype());
		return SUCCESS;
	}

	/**
	 * 详情Dialog
	 * 
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年8月5日
	 */
	public String distributeRejectDetailAdd() throws Exception {
		
		String billtype = request.getParameter("billtype");
		request.setAttribute("billtype", billtype);
		String supplierid = request.getParameter("supplierid");
		request.setAttribute("supplierid", supplierid);
		String customerid = request.getParameter("customerid");
		request.setAttribute("customerid", customerid);
		// 判断商品是否允许重复1允许0不允许
		String isrepeat = getSysParamValue("IsDeliverEnterGoodsRepeat");
		if (null == isrepeat || "".equals(isrepeat)) {
			isrepeat = "0";
		}
		request.setAttribute("isrepeat", isrepeat);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return SUCCESS;
	}

	public String distributeRejectDetailEdit() throws Exception {
		String billtype = request.getParameter("billtype");
		request.setAttribute("billtype", billtype);
		String isrepeat = getSysParamValue("IsDeliverEnterGoodsRepeat");
		if (null == isrepeat || "".equals(isrepeat)) {
			isrepeat = "0";
		}
		request.setAttribute("isrepeat", isrepeat);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return SUCCESS;
	}

	/**
	 * 获取指定GOODSID的辅单位信息
	 * 
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月6日
	 */
	public String getGoodsmeteringunit() throws Exception {
		String goodsId = request.getParameter("goodsId").trim();
		GoodsInfo_MteringUnitInfo mteringUnitInfo = getDefaultGoodsAuxMeterUnitInfo(goodsId);
		Map map=new HashMap();
		map.put("meteringunitName", mteringUnitInfo.getMeteringunitName());
		map.put("unitid", mteringUnitInfo.getMeteringunitid());
		addJSONObject(map);
		return SUCCESS;
	}

	/**
	 * 获取指定GOODSID的体积,重量相关信息
	 * 
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年8月7日
	 */
	public String getGoodsdetail() throws Exception {
		String goodsId = request.getParameter("goodsId").trim();
		Map map = new HashedMap();
		if (!"".equals(goodsId)) {
			map = distributeRejectService.getGoodsInfo(goodsId);
		}
		addJSONObject(map);
		return SUCCESS;
	}

	/**
	 * 跳转到详情查看/修改
	 * 
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年8月7日
	 */
	public String distributeRejectEdit() throws Exception {
		String resultPage = request.getParameter("type").trim();
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		String billtype=request.getParameter("billtype");
		List rslist = distributeRejectService.getDistributeRejectInfoByID(id);
		if (rslist != null && rslist.size() > 0) {
			StorageDeliveryEnter storageDeliveryEnter = (StorageDeliveryEnter) rslist.get(0);
			request.setAttribute("storageDeliveryEnter", storageDeliveryEnter);
			//如果尝试 修改 已审核的数据,重定向到查看页面
			String status=storageDeliveryEnter.getStatus();
			if ("3".equals(status)||"4".equals(status)||"5".equals(status)) {
				if("edit".equals(resultPage)){
					String url="storage/distrtibution/showDistributeRejectPage.do?billtype="+billtype+"&type=view&id="+id;
					HttpServletResponse response = ServletActionContext.getResponse();
					response.sendRedirect(url); 
					return "";
				}
			}
//			if("1".equals(billtype)){
			if(storageDeliveryEnter.getSupplierid()!=null&&!"".equals(storageDeliveryEnter.getSupplierid())){
				BuySupplier supplier = getBaseBuySupplierById(storageDeliveryEnter.getSupplierid());
				request.setAttribute("supplierName", supplier.getName());
			}	
//			}else 
			if("2".equals(billtype)){
				
				String customerName="";
				if("2".equals(storageDeliveryEnter.getSourcetype())){
					//有来源类型时,获取上游单据客户名称
					customerName =distributeRejectService.getUpCustomerName(storageDeliveryEnter.getSourceid());
					
				}else{
					DistributeRejectServiceImpl serviceImpl=(DistributeRejectServiceImpl)distributeRejectService;
					Customer customer=serviceImpl.getCustomerByID(storageDeliveryEnter.getCustomerid());
					if(customer!=null&&customer.getName()!=null){
						customerName=customer.getName();
					}	
				}
				request.setAttribute("customerName",customerName);
				
			}
			request.setAttribute("billtype", billtype);
	    }
		if (rslist != null && rslist.size() == 2) {
			List<StorageDeliveryEnterDetail> mList = (List<StorageDeliveryEnterDetail>) rslist.get(1);
			String jsonStr = JSONUtils.listToJsonStr(mList);
			request.setAttribute("StorageDeliveryEnterDetailList", jsonStr);
		}
		//

		return resultPage;
	}

	/**
	 * 编辑的保存页面
	 * 
	 * @return
	 * @author huangzhiqian
	 * @throws Exception
	 * @date 2015年8月10日
	 */
	@UserOperateLog(key="StorageDeliveryEnter",type=3)
	public String distributeRejectInfoEditSave() throws Exception {
		
		Map map = new HashMap();
		if (StringUtils.isEmpty(storageDeliveryEnter.getId())) {
			map.put("flag", false);
			map.put("msg", "未能找到要修改的信息");
			addJSONObject(map);
			return SUCCESS;
		}
		String detail = request.getParameter("detail");
		List<StorageDeliveryEnterDetail> bodList = null;
		if (null != detail && !"".equals(detail.trim())) {
			bodList = JSONUtils.jsonStrToList(detail.trim(),
					new StorageDeliveryEnterDetail());
		}
		// 获取goodsInfo 封装detail
		JSONArray detailarray = new JSONArray(detail);
		JSONObject tempObj = (JSONObject) detailarray.get(0);
		JSONObject goodsinfoObject = (JSONObject) tempObj.get("goodsInfo");
//		String goodssort = goodsinfoObject.getString("goodstype") + "";
		String brandid = goodsinfoObject.getString("brand") + "";
		String auxunitid = goodsinfoObject.getString("auxunitid") + "";
		for (Object object : bodList) {
			StorageDeliveryEnterDetail storageDeliveryEnterDetail = (StorageDeliveryEnterDetail) object;
//			storageDeliveryEnterDetail.setGoodssort(goodssort);
			storageDeliveryEnterDetail.setBrandid(brandid);
			storageDeliveryEnterDetail.setAuxunitid(auxunitid);
		}
		StorageDeliveryEnter oldEnter = distributeRejectService
				.getDistributeRejectEnterById(storageDeliveryEnter.getId());
		if (null == oldEnter) {
			map.put("flag", false);
			map.put("msg", "未能找到要修改的信息");
			addJSONObject(map);
			return SUCCESS;
		}
		if (!"1".equals(oldEnter.getStatus())
				&& !"2".equals(oldEnter.getStatus())) {
			map.put("flag", false);
			map.put("msg", "抱歉，当前单据不可修改");
			addJSONObject(map);
			return SUCCESS;
		}
		storageDeliveryEnter.setStatus(oldEnter.getStatus());

		if ("2".equals(storageDeliveryEnter.getStatus())) {
			if (null == bodList || bodList.size() == 0) {
				map.put("flag", false);
				map.put("backid", "");
				map.put("msg", "保存状态下，请填写需要采购的商品");
				addJSONObject(map);
				return SUCCESS;
			}
		}
		SysUser sysUser = getSysUser();
		
		/**需要审核时,对部分数据修改**/
		String saveAndAudit = request.getParameter("saveAndAudit");
		if("audit".equals(saveAndAudit.trim())){
			//保存并且审核
			storageDeliveryEnter.setAudittime(new Date());
			storageDeliveryEnter.setAudituserid(sysUser.getUserid());
			storageDeliveryEnter.setAuditusername(sysUser.getName());
			storageDeliveryEnter.setBusinessdate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			storageDeliveryEnter.setStatus("3");
		}
		
		boolean flag = false;
		storageDeliveryEnter.setModifyuserid(sysUser.getUserid());
		storageDeliveryEnter.setModifyusername(sysUser.getName());
		storageDeliveryEnter.setModifytime(new Date());
		// 对foot的处理
		String foot = request.getParameter("foot");
		BindFootData(storageDeliveryEnter, foot);
		// 对detail,不能直接赋值的处理
		BindDetailData(bodList, detail);
		flag = distributeRejectService.updataEnterAndDetail(storageDeliveryEnter, bodList);
		map.put("flag", flag);
		map.put("backid", storageDeliveryEnter.getId());
		//审核时有来源,关闭来源订单
		if(flag&&"audit".equals(saveAndAudit.trim())){
			flag=distributeRejectService.audit(storageDeliveryEnter, oldEnter,true);
			addLog("供应商代配送入库单保存并审核 编号："+storageDeliveryEnter.getId(), true);
		}else{
			addLog("供应商代配送入库单保存 编号："+storageDeliveryEnter.getId(), true);
		}
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * @return
	 * @author huangzhiqian
	 * @throws Exception 
	 * @date 2015年8月12日
	 */
	@UserOperateLog(key="StorageDeliveryEnter",type=4)
	public String deleteEnter() throws Exception{
		Map map = new HashMap();
		String id = request.getParameter("id");
		boolean flag=false;
		
		if(StringUtils.isNotEmpty(id)){
			flag=distributeRejectService.deleteEnterAndDetailById(id);
			addLog("供应商代配送入库单删除 编号："+id, flag);
		}
		map.put("flag", flag);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 批量删除
	 * @return
	 * @author huangzhiqian
	 * @throws Exception 
	 * @date 2015年8月12日
	 */
	@UserOperateLog(key="StorageDeliveryEnter",type=4)
	public String batchdeleteEnter() throws Exception{
		Map map = new HashMap();
		boolean flag=false;
		String oldIdStr = request.getParameter("ids");
		String[] idArr = oldIdStr.split(",");
		int num_suc=0; int num_fail=0;
		for (int i = 0; i < idArr.length; i++) {
			if(distributeRejectService.deleteEnterAndDetailById(idArr[i])){
				num_suc++;
				addLog("供应商代配送入库单删除 编号："+idArr[i], true);
			}else{
				num_fail++;
				addLog("供应商代配送入库单删除 编号："+idArr[i], false);
			}
		}
		if(num_suc==idArr.length){
			flag=true;
		}
		map.put("flag", flag);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 审核
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年8月26日
	 */
	@UserOperateLog(key="StorageDeliveryEnter",type=3)
	public String audit() throws Exception{
		Map map = new HashMap();
		map.put("msg", "");
		boolean flag=false;
		String id=request.getParameter("id");
		if(StringUtils.isNotEmpty(id)){
			StorageDeliveryEnter enter=distributeRejectService.getDistributeRejectEnterById(id);
			if("1".equals(enter.getIscheck())||"3".equals(enter.getStatus())){
				map.put("flag", flag);
				map.put("msg", "单据已审核");
				addJSONObject(map);
				return SUCCESS;
			}
			SysUser sysUser=getSysUser();
			StorageDeliveryEnter saveEnter=new StorageDeliveryEnter();
			saveEnter.setId(id);
			saveEnter.setAudittime(new Date());
			saveEnter.setAudituserid(sysUser.getUserid());
			saveEnter.setAuditusername(sysUser.getName());
			saveEnter.setBusinessdate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			saveEnter.setStatus("3");
			flag=distributeRejectService.audit(saveEnter,enter,true);
			addLog("供应商代配送入库单审核 编号："+id, flag);
		}else{
			map.put("msg", " 请保存后审核");
		}
		map.put("flag", flag);
		addJSONObject(map);
		return SUCCESS;
	}
	/**
	 * 审核
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年8月26日
	 */
	@UserOperateLog(key="StorageDeliveryEnter",type=3)
	public String audits() throws Exception{
		String msg = "", fmsg = "", smsg = "";
		int fnum = 0, snum = 0;
		String ids = request.getParameter("ids");
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			StorageDeliveryEnter enter=distributeRejectService.getDistributeRejectEnterById(id);
			if("1".equals(enter.getIscheck())||"3".equals(enter.getStatus())){
				fnum++;
				if(StringUtils.isEmpty(fmsg)){
					fmsg = id;
				}else{
					fmsg=fmsg+","+id;
				}
				continue;
			}
			SysUser sysUser=getSysUser();
			StorageDeliveryEnter saveEnter=new StorageDeliveryEnter();
			saveEnter.setId(id);
			saveEnter.setAudittime(new Date());
			saveEnter.setAudituserid(sysUser.getUserid());
			saveEnter.setAuditusername(sysUser.getName());
			saveEnter.setBusinessdate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			saveEnter.setStatus("3");
			boolean flag=distributeRejectService.audit(saveEnter,enter,true);
			if(flag){
				snum++;
				if(StringUtils.isEmpty(smsg)){
					smsg = id;
				}else{
					smsg=smsg+","+id;
				}
			}else{
				fnum++;
				if(StringUtils.isEmpty(fmsg)){
					fmsg = id;
				}else{
					fmsg=fmsg+","+id;
				}
			}
		}
		boolean flag = false;
		if (fnum != 0) {
			msg = "编号：" + fmsg + "审核失败。共" + fnum + "条数据<br>";

		}
		if (snum != 0) {
			flag=true;
			if (StringUtils.isEmpty(msg)){
				msg = "编号：" + smsg + "审核成功。共" + snum + "条数据<br>";
			}
			else{
				msg += "编号：" + smsg + "审核成功。共" + snum + "条数据<br>";
			}

		}
		addLog("代配送采购单审核：" +msg, true);
		Map returnMap = new HashMap();
		returnMap.put("msg", msg);
		returnMap.put("flag", flag);
		addJSONObject(returnMap);
		return "success";
	}
	
	@UserOperateLog(key="StorageDeliveryEnter",type=0)
	public String opaudit() throws Exception{
		String id = request.getParameter("id");
		Map resultMap = distributeRejectService.oppauditDeliveryEnter(id);
		Boolean flag=false;
		String msg="";
		if(null==resultMap){
			resultMap=new HashMap();
			resultMap.put("flag", false);
			flag=false;
		}else{
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
				resultMap.put("flag", false);
			}
			if(resultMap.containsKey("msg")){
				msg=(String)resultMap.get("msg");
			}
		}
		addJSONObject(resultMap);
		addLog("代配送入库单反审 编号："+id+"。 "+msg, flag);
		return "success";
		
	}	
	
	
	@UserOperateLog(key="StorageDeliveyEnter",type=0)
	public String batchcheck() throws Exception{
			String idarrs=request.getParameter("idarrs");
			Map resultMap=new HashMap();
			if(null==idarrs || "".equals(idarrs.trim())){
				resultMap.put("flag", false);
				resultMap.put("msg", "未能找到要验收的编号");
				addJSONObject(resultMap);
				return SUCCESS;			
			}
			resultMap= distributeRejectService.batchCheck(idarrs);		
			Boolean flag=false;
			String logMsg="";
			if(null==resultMap){
				resultMap=new HashMap();
				resultMap.put("flag", false);
				flag=false;
			}else{
				flag=(Boolean)resultMap.get("flag");
				if(null==flag){
					flag=false;
					resultMap.put("flag", false);
				}
				Integer isucess=(Integer)resultMap.get("isucess");
				Integer ifail=(Integer)resultMap.get("ifail");
				String tmpStr=(String)resultMap.get("successid");
				if(null!=isucess && isucess>0){
					logMsg="成功："+tmpStr;
				}
	
				tmpStr=(String)resultMap.get("failid");
				if(null!=ifail && ifail>0){
					logMsg=(logMsg.length()>0?logMsg+",":"")+"失败："+tmpStr;
				}
			}
			resultMap.put("msg", logMsg);
			addJSONObject(resultMap);
			addLog("批量验收代配送客户入库单："+logMsg, flag);
			return SUCCESS;
	}	

	/**
	 * 验收
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年9月15日
	 */
	@UserOperateLog(key="StorageDeliveyEnter",type=0)
	public String check()throws Exception{
			SysUser sysUser=getSysUser();
			String id=request.getParameter("id");
			Map resultMap=new HashMap();
			if(null==id || "".equals(id.trim())){
				resultMap.put("flag", false);
				resultMap.put("msg", "未能找到要验收的编号");
				addJSONObject(resultMap);
				return SUCCESS;			
			}
			StorageDeliveryEnter updateEnter=new StorageDeliveryEnter();
			updateEnter.setIscheck("1"); //检查
			updateEnter.setCheckuserid(sysUser.getUserid());
			updateEnter.setCheckname(sysUser.getName());
			updateEnter.setChecktime(new Date());
			updateEnter.setStatus("4");
//			updateEnter.setBusinessdate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			updateEnter.setId(id);
			boolean flag=distributeRejectService.updateStorageDeliveryEnter(updateEnter);
			resultMap.put("flag", flag);
			addJSONObject(resultMap);
			addLog("验收代配送客户入库单："+id, flag);
			return "success";
	}
	
	
	
	
	
	
	
	
	

	/**
	 * 对主表体积,重量,箱数,总金额数据绑定
	 * 
	 * @param entry
	 * @param foot
	 * @author huangzhiqian
	 * @date 2015年8月11日
	 */
	private void BindFootData(StorageDeliveryEnter entry, String foot) {
		if ("".equals(foot.trim()))
			return;
		JSONArray footarray = new JSONArray(foot);
		JSONObject obj = (JSONObject) footarray.get(0);
		entry.setTotalamount(new BigDecimal(obj.get("taxamount") + ""));
		entry.setTotalvolume(new BigDecimal(obj.get("volumn") + ""));
		entry.setTotalbox(new BigDecimal(obj.get("totalbox") + ""));
		entry.setTotalweight(new BigDecimal(obj.get("weight") + ""));
	}

	/**
	 * 把前台未传入的信息 根据goodsInfo获得
	 * 
	 * @param list
	 * @param detail
	 * @author huangzhiqian
	 * @date 2015年8月11日
	 */
	private void BindDetailData(List list, String detail) {
		// 获取goodsInfo
		JSONArray detailarray = new JSONArray(detail);
		for(int i=0;i<list.size();i++){
			JSONObject tempObj = (JSONObject) detailarray.get(i);
			JSONObject goodsinfoObject = (JSONObject) tempObj.get("goodsInfo");
//			String goodssort = goodsinfoObject.getString("goodstype") + "";
			String brandid = goodsinfoObject.getString("brand") + "";
			String auxunitid = goodsinfoObject.getString("auxunitid") + "";
			StorageDeliveryEnterDetail mdetail =(StorageDeliveryEnterDetail) list.get(i);
//			mdetail.setGoodssort(goodssort);
			mdetail.setBrandid(brandid);
			if(!"".equals(auxunitid)){
				mdetail.setAuxunitid(auxunitid);
			}	
			mdetail.setSeq(i);
		}

	}
    
	/**
	 * 通过id获取代配送入库单类型
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 12 29, 2015
	 */
	public String getDistributeRejectType() throws Exception {
		String id = request.getParameter("id");
		StorageDeliveryEnter storageDeliveryEnter=distributeRejectService.getDistributeRejectEnterById(id);
		Map returnMap = new HashMap();
		returnMap.put("type", storageDeliveryEnter.getBilltype());
		addJSONObject(returnMap);
		return "success";
	}
}
