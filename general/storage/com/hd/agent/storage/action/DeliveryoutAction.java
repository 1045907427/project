/**
 * @(#)DeliveryoutAction.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年8月13日 huangzhiqian 创建版本
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
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONObject;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.sales.model.OrderDetail;
import com.hd.agent.sales.service.IOrderService;
import com.hd.agent.storage.model.StorageDeliveryOut;
import com.hd.agent.storage.model.StorageDeliveryOutDetail;
import com.hd.agent.storage.model.StorageSummary;
import com.hd.agent.storage.model.StorageSummaryBatch;
import com.hd.agent.storage.service.DeliveryOutService;
import com.hd.agent.storage.service.impl.DeliveryOutServiceImpl;

/**
 * 供应商代配送出库单
 * 
 * @author huangzhiqian
 */
public class DeliveryoutAction extends BaseFilesAction {
	private StorageDeliveryOut storageDeliveryOut;
	
	public void setStorageDeliveryOut(StorageDeliveryOut storageDeliveryOut) {
		this.storageDeliveryOut = storageDeliveryOut;
	}

	public StorageDeliveryOut getStorageDeliveryOut() {
		return storageDeliveryOut;
	}

	private DeliveryOutService deliveryOutService;
	private IOrderService salesOrderService;
	
	public IOrderService getSalesOrderService() {
		return salesOrderService;
	}

	public void setSalesOrderService(IOrderService salesOrderService) {
		this.salesOrderService = salesOrderService;
	}

	public DeliveryOutService getDeliveryOutService() {
		return deliveryOutService;
	}

	public void setDeliveryOutService(DeliveryOutService deliveryOutService) {
		this.deliveryOutService = deliveryOutService;
	}

	/**
	 * 查看所有发货配送入库单页面
	 * 
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月4日
	 */
	public String showDeliveryOutListPage() {
		String billtype = request.getParameter("outtype");
		request.setAttribute("billtype", billtype);
		return SUCCESS;
	}

	/**
	 * 发货配送入库Page
	 * 
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月13日
	 */
	public String showDeliveryOutPage() {
	    Boolean flag= true;
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
		StorageDeliveryOut storageDeliveryOut=deliveryOutService.getStorageDeliveryOutById(id);
		if(null==storageDeliveryOut){
			flag=false;
		}
		request.setAttribute("flag", flag);
		return SUCCESS;
	}

	/**
	 * 发货配送入库AddPage
	 * 
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月13日
	 */
	public String deliveryOutAddPage() {
		// 单据类型
		String billtype = request.getParameter("billtype");
		request.setAttribute("billtype", billtype);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("today", dateFormat.format(calendar.getTime()));
		return SUCCESS;
	}

	/**
	 * 跳转详情Dialog Add
	 * 
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年8月5日
	 */
	public String deliveryOutDetailDiaLogAdd() throws Exception {
		String businessdate=request.getParameter("businessdate");
		request.setAttribute("businessdate", businessdate);
		String customerid=request.getParameter("customerid");
		request.setAttribute("customerid", customerid);
		String billtype=request.getParameter("billtype");
		request.setAttribute("billtype", billtype);
		String supplierid = request.getParameter("supplierid");
		request.setAttribute("supplierid", supplierid);
		// 判断商品是否允许重复1允许0不允许
		String isrepeat = getSysParamValue("IsDeliverOutGoodsRepeat");
		if (null == isrepeat || "".equals(isrepeat)) {
			isrepeat = "0";
		}
		request.setAttribute("isrepeat", isrepeat);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return SUCCESS;
	}
	/**
	 * 新增保存
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年8月14日
	 */
	@UserOperateLog(key="StorageDeliveryOut",type=2)
	public String deliveryOutSave() throws Exception {
		SysUser user = getSysUser();
		String detail = request.getParameter("detail");
		String foot = request.getParameter("foot");
		storageDeliveryOut.setAdduserid(user.getUserid());
		storageDeliveryOut.setAddusername(user.getName());
		storageDeliveryOut.setAdddeptid(user.getDepartmentid());
		storageDeliveryOut.setAdddeptname(user.getDepartmentname());
		storageDeliveryOut.setAddtime(new Date());
		BindFootData(storageDeliveryOut, foot);
		List list = JSONUtils.jsonStrToList(detail.trim(),new StorageDeliveryOutDetail());
		BindDetailData(list, detail);
		Map<String,Object> map = deliveryOutService.insertDatas(storageDeliveryOut, list);
		boolean flag = (Boolean)map.get("flag");
		String saveAndAudit = request.getParameter("saveAndAudit");
		if(flag&&"audit".equals(saveAndAudit.trim())){
			StorageDeliveryOut updateOut=new StorageDeliveryOut();
			updateOut.setAudittime(new Date());
			updateOut.setAudituserid(user.getUserid());
			updateOut.setAuditusername(user.getName());
			updateOut.setStatus("3");
			updateOut.setId(storageDeliveryOut.getId());
			boolean auditflag = deliveryOutService.audit(storageDeliveryOut.getId(),updateOut);
			map.put("auditflag", auditflag);
			addLog("供应商代配送出库 保存审核 编号："+storageDeliveryOut.getId(), auditflag);
		}else{
			addLog("供应商代配送出库新增保存 编号："+storageDeliveryOut.getId(), flag);
			
		}
		map.put("backid", storageDeliveryOut.getId());
		addJSONObject(map);
		request.setAttribute("billtype", storageDeliveryOut.getBilltype());
		return SUCCESS;
	}

	/**
	 * datagrid列表
	 * 
	 * @return
	 * @author huangzhiqian
	 * @throws Exception
	 * @date 2015年8月14日
	 */
	public String deliveryOutListPage() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		pageMap.setOrderSql(" addtime desc ");
		PageData pageData = deliveryOutService.getStorageDeliveryOutList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}

	/**
	 * 批量删除
	 * @throws Exception 
	 */
	@UserOperateLog(key="StorageDeliveryOut",type=3)
	public String batchdeleteOut() throws Exception {
		Map map = new HashMap();
		boolean flag = false;
		String oldIdStr = request.getParameter("ids");
		String[] idArr = oldIdStr.split(",");
		int num_suc = 0;
		int num_fail = 0;
		for (int i = 0; i < idArr.length; i++) {
			if (deliveryOutService.deleteOutAndDetailById(idArr[i])) {
				addLog("供应商代配送出库单批量删除 编号："+idArr[i], true);
				num_suc++;
			} else {
				addLog("供应商代配送出库单批量删除 编号："+idArr[i], false);
				num_fail++;
			}
		}
		if (num_suc == idArr.length) {
			flag = true;
		}
		map.put("flag", flag);
		addJSONObject(map);
		return SUCCESS;

	}
	/**
	 * 单个删除
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年8月19日
	 */
	@UserOperateLog(key="StorageDeliveryOut",type=3)
	public String deleteOut() throws Exception {
		Map map = new HashMap();
		String id = request.getParameter("id");
		boolean flag=false;
		
		if(StringUtils.isNotEmpty(id)){
			flag=deliveryOutService.deleteOutAndDetailById(id);
		}
		addLog("供应商代配送出库单删除 编号："+id, flag);
		map.put("flag", flag);
		addJSONObject(map);
		return SUCCESS;

	}
	
	/**
	 * 查看编辑页面
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年8月19日
	 */
	public String deliveryOutEditPage() throws Exception{
		String resultPage = request.getParameter("type");
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		String billtype=request.getParameter("billtype");
		List rslist = deliveryOutService.getEnterAndDetailById(id);
		if (rslist != null && rslist.size() > 0) {
			StorageDeliveryOut out = (StorageDeliveryOut) rslist.get(0);
			request.setAttribute("storageDeliveryOut", out);
			//如果尝试 修改 已审核的数据,重定向到查看页面
			String status=out.getStatus();
			if ("3".equals(status)||"4".equals(status)||"5".equals(status)) {
				if("edit".equals(resultPage)){
					String url="storage/deliveryout/showDeliveryOutPage.do?billtype="+billtype+"&type=view&id="+id;
					HttpServletResponse response = ServletActionContext.getResponse();
					response.sendRedirect(url); 
					return "";
				}
			}
//			if("1".equals(billtype)){
				if(out.getSupplierid()!=null&&!"".equals(out.getSupplierid())){
					BuySupplier supplier = getBaseBuySupplierById(out.getSupplierid());
					request.setAttribute("supplierName", supplier.getName());
				}
//			}else 
			if("2".equals(billtype)){
					String customerName="";
					if("2".equals(out.getSourcetype())){
						//有来源类型时,获取上游单据客户名称
						customerName =deliveryOutService.getUpCustomerName(out.getSourceid());
						
					}else{
						DeliveryOutServiceImpl serviceImpl=(DeliveryOutServiceImpl)deliveryOutService;
						Customer customer=serviceImpl.getCustomerByID(out.getCustomerid());
						if(customer!=null&&customer.getName()!=null){
							customerName=customer.getName()+"";
						}	
					}
					request.setAttribute("customerName",customerName);
				
			}
			request.setAttribute("billtype", billtype);
	    }
		if (rslist != null && rslist.size() == 2) {
			List<StorageDeliveryOutDetail> mList = (List<StorageDeliveryOutDetail>) rslist.get(1);
			String jsonStr = JSONUtils.listToJsonStr(mList);
			request.setAttribute("StorageDeliveryOutDetailList", jsonStr);
		}

		return resultPage;
		
		
		
	}
	
	/**
	 * 审核
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年8月19日
	 */
	@UserOperateLog(key="StorageDeliveryOut",type=3)
	public String audit() throws Exception{
		Map map = new HashMap();
		boolean flag=false;
		String id=request.getParameter("id");
		if(StringUtils.isNotEmpty(id)){
			StorageDeliveryOut out=deliveryOutService.getStorageDeliveryOutById(id);
			if("1".equals(out.getIscheck())||"3".equals(out.getStatus())){
				map.put("flag", flag);
				map.put("msg", "单据已审核");
				addJSONObject(map);
				return SUCCESS;
			}
			SysUser sysUser=getSysUser();
			StorageDeliveryOut saveout=new StorageDeliveryOut();
			saveout.setId(id);
			saveout.setAudittime(new Date());
			saveout.setAudituserid(sysUser.getUserid());
			saveout.setAuditusername(sysUser.getName());
			saveout.setBusinessdate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			saveout.setStatus("3");
			flag=deliveryOutService.audit(id,saveout);
			addLog("供应商代配送出库单审核 编号："+id, flag);
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
	@UserOperateLog(key="StorageDeliveryOut",type=3)
	public String audits() throws Exception{
		String msg = "", fmsg = "", smsg = "";
		int fnum = 0, snum = 0;
		String ids = request.getParameter("ids");
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			StorageDeliveryOut out=deliveryOutService.getStorageDeliveryOutById(id);
			if("1".equals(out.getIscheck())||"3".equals(out.getStatus())){
				fnum++;
				if(StringUtils.isEmpty(fmsg)){
					fmsg = id;
				}else{
					fmsg=fmsg+","+id;
				}
				continue;
			}
			SysUser sysUser=getSysUser();
			StorageDeliveryOut saveOut=new StorageDeliveryOut();
			saveOut.setId(id);
			saveOut.setAudittime(new Date());
			saveOut.setAudituserid(sysUser.getUserid());
			saveOut.setAuditusername(sysUser.getName());
			saveOut.setBusinessdate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			saveOut.setStatus("3");
			boolean flag=deliveryOutService.audit(id,saveOut);
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
		addLog("供应商代配送出库单审核：" +msg, true);
		Map returnMap = new HashMap();
		returnMap.put("msg", msg);
		returnMap.put("flag", flag);
		addJSONObject(returnMap);
		return "success";
	}
	/**
	 * 修改单据后保存
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年8月19日
	 */
	@UserOperateLog(key="StorageDeliveryOut",type=3)
	public String deliveryOutEditSave() throws Exception{
		Map map = new HashMap();
		if (StringUtils.isEmpty(storageDeliveryOut.getId())) {
			map.put("flag", false);
			map.put("msg", "未能找到要修改的信息");
			addJSONObject(map);
			return SUCCESS;
		}
		String detail = request.getParameter("detail");
		List<StorageDeliveryOutDetail> bodList = null;
		if (null != detail && !"".equals(detail.trim())) {
			bodList = JSONUtils.jsonStrToList(detail.trim(),new StorageDeliveryOutDetail());
		}
		StorageDeliveryOut oldOut = deliveryOutService.getStorageDeliveryOutById(storageDeliveryOut.getId());
		if (null == oldOut) {
			map.put("flag", false);
			map.put("msg", "未能找到要修改的信息");
			addJSONObject(map);
			return SUCCESS;
		}
		if (!"1".equals(oldOut.getStatus())&& !"2".equals(oldOut.getStatus())) {
			map.put("flag", false);
			map.put("msg", "抱歉，当前单据不可修改");
			addJSONObject(map);
			return SUCCESS;
		}
		storageDeliveryOut.setStatus(oldOut.getStatus());

		if ("2".equals(storageDeliveryOut.getStatus())) {
			if (null == bodList || bodList.size() == 0) {
				map.put("flag", false);
				map.put("backid", "");
				map.put("msg", "保存状态下，请填写需要采购的商品");
				addJSONObject(map);
				return SUCCESS;
			}
		}
		SysUser sysUser = getSysUser();
		boolean flag = false;
		storageDeliveryOut.setModifyuserid(sysUser.getUserid());
		storageDeliveryOut.setModifyusername(sysUser.getName());
		storageDeliveryOut.setModifytime(new Date());
		// 对foot的处理
		String foot = request.getParameter("foot");
		BindFootData(storageDeliveryOut, foot);
		// 对detail,不能直接赋值的处理
		BindDetailData(bodList, detail);
		map= deliveryOutService.updataEnterAndDetail(storageDeliveryOut, bodList);
		flag = (Boolean) map.get("flag");
		
		String saveAndAudit = request.getParameter("saveAndAudit");
		if(flag&&"audit".equals(saveAndAudit.trim())){
			/**编辑  保存并审核开始**/
			//保存并且审核
			StorageDeliveryOut newOut=new StorageDeliveryOut();
			newOut.setAudittime(new Date());
			newOut.setAudituserid(sysUser.getUserid());
			newOut.setAuditusername(sysUser.getName());
			newOut.setStatus("3");
			newOut.setBusinessdate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));	
			newOut.setId(storageDeliveryOut.getId());
			boolean auditflag = false;
			deliveryOutService.audit(storageDeliveryOut.getId(), newOut);
			addLog("其他出库单保存审核 编号："+storageDeliveryOut.getId(), auditflag);
			/**编辑  保存并审核结束**/
		}else{
			addLog("其他出库单修改保存 编号："+storageDeliveryOut.getId(), flag);
		}
		map.put("flag", flag);
		map.put("backid", storageDeliveryOut.getId());		
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 跳转修改Dialog
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年8月19日
	 */
	public String dileveryOutDetailEdit() throws Exception{
		String billtype=request.getParameter("billtype");
		request.setAttribute("billtype", billtype);
		String isrepeat = getSysParamValue("IsDeliverOutGoodsRepeat");
		if (null == isrepeat || "".equals(isrepeat)) {
			isrepeat = "0";
		}
		request.setAttribute("isrepeat", isrepeat);
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		return SUCCESS;

	}
	
	
	public String getStorageSummaryByStorageidAndGoodsid() throws Exception{
		String goodsid=request.getParameter("goodsid").trim();
		String storageid=request.getParameter("storageid").trim();
		StorageSummary storageSummary=getStorageSummaryByStorageidAndGoodsid(storageid,goodsid);
		Map map=new HashMap();
		map.put("storageSummary", storageSummary);
		addJSONObject(map);
		return SUCCESS;
	}
	
	
	
	
	public String getDeliveryOutGoodsInfo() throws Exception{
		String id=request.getParameter("goodsid");
		String customerId = request.getParameter("customerid").trim();
		//0取基准销售价,1取合同价
		String priceType = getSysParamValue("DELIVERYORDERPRICE");
		GoodsInfo info=getGoodsInfoByID(id);
		Map map=BeanUtils.describe(info);
		if("1".equals(priceType)&&!"".equals(customerId)){
			//系统参数取价
			OrderDetail  detail = salesOrderService.getFixGoodsDetail(id, customerId);
			map.put("basesaleprice", detail.getTaxprice());
		}
		addJSONObject(map);
		return SUCCESS;
		
	}
	
	
	public String getStorageDeliveryOutDetail() throws Exception{
		String id=request.getParameter("id");
		StorageDeliveryOutDetail detail=deliveryOutService.getDeliveryOutDetailById(id);
		addJSONObject(BeanUtils.describe(detail));
		return SUCCESS;
	}
	
	
	
	/**
	 * 反审
	 * @return
	 * @author huangzhiqian
	 * @throws Exception 
	 * @date 2015年9月15日
	 */
	@UserOperateLog(key="StorageDeliveryOut",type=0)
	public String oppauditDeliveryOut() throws Exception{
		String id = request.getParameter("id");
		Map resultMap = deliveryOutService.oppauditDeliveryOut(id);
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
		addLog("代配送出库单反审 编号："+id+"。 "+msg, flag);
		return "success";
		
	}
	
	@UserOperateLog(key="StorageDeliveyOut",type=0)
	public String batchcheck() throws Exception{
			String idarrs=request.getParameter("idarrs");
			Map resultMap=new HashMap();
			if(null==idarrs || "".equals(idarrs.trim())){
				resultMap.put("flag", false);
				resultMap.put("msg", "未能找到要验收的编号");
				addJSONObject(resultMap);
				return SUCCESS;			
			}
			resultMap= deliveryOutService.batchCheck(idarrs);		
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
			addLog("批量验收代配送客户出库单："+logMsg, flag);
			return SUCCESS;
	}	
	
	
	/**
	 * 验收
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年9月15日
	 */
	@UserOperateLog(key="StorageDeliveyOut",type=0)
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
			StorageDeliveryOut updateOut=new StorageDeliveryOut();
			updateOut.setIscheck("1"); 
			updateOut.setCheckuserid(sysUser.getUserid());
			updateOut.setCheckname(sysUser.getName());
			updateOut.setChecktime(new Date());
			updateOut.setStatus("4");
//			updateOut.setBusinessdate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			updateOut.setId(id);
			boolean flag=deliveryOutService.updateDeliveryOut(updateOut);
			resultMap.put("flag", flag);
			addJSONObject(resultMap);
			addLog("验收代配送客户出库单："+id, flag);
			return "success";
	}
	
	/**
	 * 获取批次库存信息
	 * @return
	 * @author huangzhiqian
	 * @throws Exception 
	 * @date 2015年12月17日
	 */
	public String getStorageSummaryBatchByStorageidAndGoodsidAndBatchNo() throws Exception{
		Map resultMap=new HashMap();
		String goodsId =request.getParameter("goodsid");
		String storageid =request.getParameter("storageid");
		String batchno =request.getParameter("batchno");
		
		StorageSummaryBatch batch = deliveryOutService.getStorageSummaryBatchByStorageidAndGoodsidAndBatchNo(goodsId,storageid,batchno);
		if(batch!=null){
			resultMap.put("data", batch);
			addJSONObject(resultMap);
			
		}
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
	private void BindFootData(StorageDeliveryOut entry, String foot) {
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
		// 获取goodsInfo,detail中没有的数据绑定上去
		JSONArray detailarray = new JSONArray(detail);
		for(int i=0;i<list.size();i++){
			JSONObject tempObj = (JSONObject) detailarray.get(i);
			JSONObject goodsinfoObject = (JSONObject) tempObj.get("goodsInfo");
//			String goodssort = goodsinfoObject.getString("goodstype") + "";
			String brandid = goodsinfoObject.getString("brand") + "";
			String auxunitid = goodsinfoObject.getString("auxunitid") + "";
			StorageDeliveryOutDetail mdetail =(StorageDeliveryOutDetail) list.get(i);
//			mdetail.setGoodssort(goodssort);
			mdetail.setBrandid(brandid);
			if(!"".equals(auxunitid)){
				mdetail.setAuxunitid(auxunitid);
			}
			mdetail.setSeq(i);
		}
		
	}
	/**
	 * 通过id获取代配送出库单类型
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 12 29, 2015
	 */
	public String getDeliveryOutType() throws Exception {
		String id = request.getParameter("id");
		StorageDeliveryOut storageDeliveryOut=deliveryOutService.getStorageDeliveryOutById(id);
		Map returnMap = new HashMap();
		returnMap.put("type", storageDeliveryOut.getBilltype());
		addJSONObject(returnMap);
		return "success";
	}
}
