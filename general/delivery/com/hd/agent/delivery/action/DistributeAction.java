/**
 * @(#)DistributeAction.java
 *
 * @author wanghongteng
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 配送单操作
 * -------------------------------------------------------------------------
 * 2015年8月4日 wanghongteng 创建版本
 */
package com.hd.agent.delivery.action;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.common.util.*;
import com.hd.agent.sales.model.ModelOrder;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.GoodsInfo_MteringUnitInfo;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.service.IExcelService;
import com.hd.agent.delivery.model.DeliveryAogorder;
import com.hd.agent.delivery.model.DeliveryAogorderDetail;
import com.hd.agent.delivery.model.DeliveryAogreturn;
import com.hd.agent.delivery.model.DeliveryAogreturnDetail;
import com.hd.agent.delivery.model.DeliveryOrder;
import com.hd.agent.delivery.model.DeliveryOrderDetail;
import com.hd.agent.delivery.model.DeliveryRejectbill;
import com.hd.agent.delivery.model.DeliveryRejectbillDetail;
import com.hd.agent.delivery.model.ExportDeliveryAogorder;
import com.hd.agent.delivery.model.ExportDeliveryAogreturn;
import com.hd.agent.delivery.model.ExportDeliveryOrder;
import com.hd.agent.delivery.model.ExportDeliveryRejectbill;
import com.hd.agent.delivery.service.IDistributeService;
import com.hd.agent.storage.model.StorageSummary;
import com.hd.agent.storage.model.StorageSummaryBatch;
import com.hd.agent.storage.service.IDistributeRejectService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * 
 * @author wanghongteng
 */
public class DistributeAction extends BaseFilesAction {
	private IDistributeRejectService distributeRejectService;
	private IDistributeService distributeService;
	private DeliveryAogorder deliveryAogorder;
	private DeliveryAogreturn deliveryAogreturn;
	private DeliveryOrder deliveryOrder;
	private DeliveryRejectbill deliveryRejectbill;
	protected IExcelService excelService;

	public IExcelService getExcelService() {
		return excelService;
	}

	public void setExcelService(IExcelService excelService) {
		this.excelService = excelService;
	}

	public IDistributeService getDistributeService() {
		return distributeService;
	}

	public void setDistributeService(IDistributeService distributeService) {
		this.distributeService = distributeService;
	}

	public DeliveryAogorder getDeliveryAogorder() {
		return deliveryAogorder;
	}

	public void setDeliveryAogorder(DeliveryAogorder deliveryAogorder) {
		this.deliveryAogorder = deliveryAogorder;
	}

	public DeliveryAogreturn getDeliveryAogreturn() {
		return deliveryAogreturn;
	}

	public void setDeliveryAogreturn(DeliveryAogreturn deliveryAogreturn) {
		this.deliveryAogreturn = deliveryAogreturn;
	}

	public DeliveryOrder getDeliveryOrder() {
		return deliveryOrder;
	}

	public void setDeliveryOrder(DeliveryOrder deliveryOrder) {
		this.deliveryOrder = deliveryOrder;
	}

	public DeliveryRejectbill getDeliveryRejectbill() {
		return deliveryRejectbill;
	}

	public void setDeliveryRejectbill(DeliveryRejectbill deliveryRejectbill) {
		this.deliveryRejectbill = deliveryRejectbill;
	}

	/**
	 * 显示代配送采购单页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 4, 2015
	 */
	public String showDeliveryAogorderListPage() throws Exception {
		return "success";
	}

	/**
	 * 获取代配送采购单列表
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 4, 2015
	 */
	
	public String showDeliveryAogorderList() throws Exception {
		// 获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		// map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = distributeService.showDeliveryAogorderList(pageMap);
		addJSONObject(pageData);
		return "success";
	}

	/**
	 * 显示点击新增按钮所显示的页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 5, 2015
	 */
	public String showDeliveryAogorderAddPage() throws Exception {
		request.setAttribute("type", "add");
		return "success";
	}

	/**
	 * 显示代配送采购单新增页面的按钮栏
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 5, 2015
	 */
	public String deliveryAogorderAddPage() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_delivery_aogorder"));
		request.setAttribute("userName", getSysUser().getName());
		return "success";
	}

	/**
	 * 显示到货明细的添加页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	public String showDeliveryAogorderDetailAddPage() throws Exception {
		String supplierid = request.getParameter("supplierid");
		request.setAttribute("supplierid", supplierid);
		String storageid = request.getParameter("storageid");
		request.setAttribute("storageid", storageid);
		// 字段权限
		Map fieldMap = getAccessColumn("t_base_goods_info");
		request.setAttribute("fieldMap", fieldMap);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}

	/**
	 * 显示到货明细的修改页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	public String showDeliveryAogorderDetailEditPage() throws Exception {
		Map fieldMap = getAccessColumn("t_base_goods_info");
		request.setAttribute("fieldMap", fieldMap);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}

	/**
	 * 保存代配送采购单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryAogorder", type = 2)
	public String addDeliveryAogorderSave() throws Exception {
		deliveryAogorder.setStatus("2");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new DeliveryAogorderDetail());
		Map map = distributeService.addDeliveryAogorder(deliveryAogorder, detailList);
		String id = null != map.get("id") ? (String) map.get("id") : "";
		String saveaudit = request.getParameter("saveaudit");
		boolean flag = (Boolean) map.get("flag");
		String msg = "";
		if (flag && "saveaudit".equals(saveaudit)) {
			Map returnmap = distributeService.auditDeliveryAogorder(deliveryAogorder.getId());
			if ((Boolean) returnmap.get("flag")) {
				Map aogorderList = distributeService.getDeliveryAogorderInfo(id);
				DeliveryAogorder deliveryAogorder=(DeliveryAogorder) aogorderList.get("deliveryAogorder");
				List list=(List) aogorderList.get("detailList");
				boolean index=distributeService.addDistributeRejectByAogorder(deliveryAogorder, list);
				if(!index){
					distributeService.oppauditDeliveryAogorder(id);
				}
			}
			map.put("auditflag", returnmap.get("flag"));
			msg = (String) returnmap.get("msg");
			map.put("msg", msg);
			addLog("代配送采购单保存审核 编号：" + id, returnmap);
		} else {
			addLog("代配送采购单新增编号"+id, flag);
		}
		addJSONObject(map);
		return "success";
	}

	/**
	 * 显示代配送采购单修改页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */

	public String showDeliveryAogorderEditPage() throws Exception {
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.setAttribute("type", "edit");
		return "success";
	}

	/**
	 * 获取需要修改的代配送采购单的信息
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	public String deliveryAogorderEditPage() throws Exception {
		String id = request.getParameter("id");
		Map map = distributeService.getDeliveryAogorderInfo(id);
		DeliveryAogorder deliveryAogorder = (DeliveryAogorder) map.get("deliveryAogorder");
		List<DeliveryAogorderDetail> list = (List) map.get("detailList");
		// for(DeliveryAogorderDetail deliveryAogorderDetail : list){
		//   StorageSummary storageSummary = null;
		// storageSummary =
		// getStorageSummarySumByGoodsid(deliveryAogorderDetail.getGoodsid());
		//   deliveryAogorderDetail.setUsablenum(storageSummary.getUsablenum());
		// }
		String jsonStr = JSONUtils.listToJsonStr(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("deliveryAogorder", deliveryAogorder);
		request.setAttribute("detailList", jsonStr);
		request.setAttribute("listSize", list.size());
		if ("1".equals(deliveryAogorder.getStatus()) || "2".equals(deliveryAogorder.getStatus()) || "6".equals(deliveryAogorder.getStatus())) {
			// 加锁
			lockData("t_delivery_aogorder", deliveryAogorder.getId());
			return "success";
		} else {
			return "viewSuccess";
		}
	}

	/**
	 * 删除代配送采购单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryAogorder", type = 4)
	public String deleteDeliveryAogorder() throws Exception {
		String id = request.getParameter("id");
		// 加锁
		boolean lock = isLock("t_delivery_aogorder", id);
		if (lock) { // 被锁定不能进行修改
			addJSONObject("lock", true);
			addLog("代配送采购单 编码：" + id + "互斥，操作", false);
			return "success";
		}
		boolean flag = distributeService.deleteDeliveryAogorder(id);
		addJSONObject("flag", flag);
		addLog("代配送采购单删除 编号：" + id, flag);
		return "success";
	}

	/**
	 * 保存修改后的代配送采购单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryAogorderDetail", type = 3)
	public String editDeliveryAogorderSave() throws Exception {
		// 加锁
		boolean lock = isLock("t_delivery_aogorder", deliveryAogorder.getId());
		if (lock) { // 被锁定不能进行修改
			addJSONObject("lock", true);
			addLog("代配送采购单 编码：" + deliveryAogorder.getId() + "互斥，操作", false);
			return "success";
		}
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new DeliveryAogorderDetail());
		Map map = distributeService.editDeliveryAogorder(deliveryAogorder, detailList);
		String id = null != map.get("id") ? (String) map.get("id") : "";
		String saveaudit = request.getParameter("saveaudit");
		boolean flag = (Boolean) map.get("flag");
		String msg = "";
		if (flag && "saveaudit".equals(saveaudit)) {
			Map returnmap = distributeService.auditDeliveryAogorder(deliveryAogorder.getId());
			if ((Boolean) returnmap.get("flag")) {
				Map aogorderList = distributeService.getDeliveryAogorderInfo(id);
				DeliveryAogorder deliveryAogorder=(DeliveryAogorder) aogorderList.get("deliveryAogorder");
				List list=(List) aogorderList.get("detailList");
				boolean index=distributeService.addDistributeRejectByAogorder(deliveryAogorder, list);
				if(!index){
					distributeService.oppauditDeliveryAogorder(id);
				}
			}
			map.put("auditflag", returnmap.get("flag"));
			msg = (String) returnmap.get("msg");
			map.put("msg", msg);
			addLog("代配送采购单保存审核 编号：" + id, returnmap);
		} else {
			addLog("代配送采购单修改保存编号:"+id, flag);
		}
		addJSONObject(map);
		// 解锁数据
		isLockEdit("t_delivery_aogorder", deliveryAogorder.getId()); // 判断锁定并解锁
		return "success";
	}

	/**
	 * 审核代配送采购单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryAogorder", type = 3)
	public String auditDeliveryAogorder() throws Exception {
		String id = request.getParameter("id");
		// 加锁
		boolean lock = isLock("t_delivery_aogorder", id);
		if (lock) { // 被锁定不能进行修改
			addJSONObject("lock", true);
			addLog("代配送采购单 编码：" + id + "互斥，操作", false);
			return "success";
		}
		Map map = distributeService.auditDeliveryAogorder(id);
		if ((Boolean) map.get("flag")) {
			Map aogorderList = distributeService.getDeliveryAogorderInfo(id);
			DeliveryAogorder deliveryAogorder=(DeliveryAogorder) aogorderList.get("deliveryAogorder");
			List list=(List) aogorderList.get("detailList");
			
			boolean index=distributeService.addDistributeRejectByAogorder(deliveryAogorder, list);
			if(!index){
				distributeService.oppauditDeliveryAogorder(id);
			}
		}
		addJSONObject(map);
		addLog("代配送采购单审核 编号：" + id, map);
		return "success";
	}

	/**
	 * 批量审核代配送采购单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryAogorder", type = 3)
	public String auditsDeliveryAogorder() throws Exception {
		String msg = "", fmsg = "", smsg = "";
		int fnum = 0, snum = 0;
		String ids = request.getParameter("ids");
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			if (isLock("t_delivery_aogorder", id)) {
				if (StringUtils.isEmpty(fmsg)) {
					fmsg = id;
				} else {
					fmsg += "," + id;
				}
				fnum++;
			} else {
				Map map = distributeService.auditDeliveryAogorder(id);
				Map aogorderList = distributeService.getDeliveryAogorderInfo(id);
				DeliveryAogorder deliveryAogorder=(DeliveryAogorder) aogorderList.get("deliveryAogorder");
				List list=(List) aogorderList.get("detailList");
				
				boolean index=distributeService.addDistributeRejectByAogorder(deliveryAogorder, list);
				if(!index){
					distributeService.oppauditDeliveryAogorder(id);
				}
				else{
				if (StringUtils.isEmpty(smsg) && (Boolean) map.get("flag")) {
					smsg = id;
				} else {
					smsg += "," + id;
				}
				snum++;
				addLog("代配送采购单审核编号：" + id, map);
				}
			}
		}
		if (fnum != 0) {

			msg = "编号：" + fmsg + "被上锁，审核失败。共" + fnum + "条数据<br>";

		}
		if (snum != 0) {
			if (StringUtils.isEmpty(msg))
				msg = "编号：" + smsg + "审核成功。共" + snum + "条数据<br>";
			else
				msg += "编号：" + smsg + "审核成功。共" + snum + "条数据<br>";
		}

		Map returnMap = new HashMap();
		returnMap.put("msg", msg);
		addJSONObject(returnMap);
		return "success";
	}

	/**
	 * 反审代配送采购单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryAogorder", type = 3)
	public String oppauditDeliveryAogorder() throws Exception {
		String id = request.getParameter("id");
		// 加锁
		boolean lock = isLock("t_delivery_aogorder", id);
		if (lock) { // 被锁定不能进行修改
			addJSONObject("lock", true);
			addLog("代配送采购单 编码：" + id + "互斥，操作", false);
			return "success";
		}		
		Map map = distributeService.oppauditDeliveryAogorder(id);
		addJSONObject(map);
		addLog("代配送采购单反核 编号：" + id, map);
		return "success";
	}

	// 代配送采购退单部分
	/**
	 * 显示代配送采购退单页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 4, 2015
	 */
	public String showDeliveryAogreturnListPage() throws Exception {
		return "success";
	}

	/**
	 * 显示点击新增按钮所显示的页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 5, 2015
	 */
	public String showDeliveryAogreturnAddPage() throws Exception {
		request.setAttribute("type", "add");
		return "success";
	}

	/**
	 * 获取代配送采购退单列表
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 4, 2015
	 */
	
	public String showDeliveryAogreturnList() throws Exception {
		// 获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		// map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = distributeService.showDeliveryAogreturnList(pageMap);
		addJSONObject(pageData);
		return "success";
	}

	/**
	 * 显示退货明细的添加页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	public String showDeliveryAogreturnDetailAddPage() throws Exception {
		String supplierid = request.getParameter("supplierid");
		request.setAttribute("supplierid", supplierid);
		// 字段权限
		Map fieldMap = getAccessColumn("t_base_goods_info");
		request.setAttribute("fieldMap", fieldMap);
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}

	/**
	 * 显示代配送采购退单新增页面的按钮栏
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 5, 2015
	 */
	public String deliveryAogreturnAddPage() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_delivery_aogorder"));
		request.setAttribute("userName", getSysUser().getName());
		return "success";
	}

	/**
	 * 保存代配送采购退单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryAogreturn", type = 2)
	public String addDeliveryAogreturnSave() throws Exception {
		deliveryAogreturn.setStatus("2");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new DeliveryAogreturnDetail());
		Map map = distributeService.addDeliveryAogreturn(deliveryAogreturn, detailList);
		String id = null != map.get("id") ? (String) map.get("id") : "";
		String saveaudit = request.getParameter("saveaudit");
		boolean flag = (Boolean) map.get("flag");
		String msg = "";
		if (flag && "saveaudit".equals(saveaudit)) {
			Map returnmap = distributeService.auditDeliveryAogreturn(deliveryAogreturn.getId());
			
			if ((Boolean) returnmap.get("flag")) {
                Map aogreturnList = distributeService.getDeliveryAogreturnInfo(id);
				
				DeliveryAogreturn deliveryAogreturn=(DeliveryAogreturn) aogreturnList.get("deliveryAogreturn");
				List list=(List) aogreturnList.get("detailList");
				
				Map m=distributeService.addDistributeOutByAogreturn(deliveryAogreturn, list);
				if((Boolean) m.get("flag")){
					map.put("msg", (String)m.get("msg"));
					map.put("auditflag",m.get("flag"));
				}
				else{
					map.put("auditflag", m.get("flag"));
					map.put("msg",(String)m.get("msg"));
					distributeService.oppauditDeliveryAogreturn(id);
				}
			}
			else{
				map.put("msg",(String)returnmap.get("msg"));
				map.put("auditflag", returnmap.get("flag"));
			}
			
			
			
			addLog("代配送采购退单保存审核 编号：" + id, returnmap);
		} else {
			addLog("代配送采购退单新增保存编号：", flag);
		}
		addJSONObject(map);
		return "success";
	}

	/**
	 * 显示退货明细的修改页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	public String showDeliveryAogreturnDetailEditPage() throws Exception {
		Map fieldMap = getAccessColumn("t_base_goods_info");
		request.setAttribute("fieldMap", fieldMap);
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}

	/**
	 * 显示代配送采购退单修改页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	public String showDeliveryAogreturnEditPage() throws Exception {
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.setAttribute("type", "edit");
		return "success";
	}

	/**
	 * 获取需要修改的代配送采购退单的信息
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	public String deliveryAogreturnEditPage() throws Exception {
		String id = request.getParameter("id");
		Map map = distributeService.getDeliveryAogreturnInfo(id);
		DeliveryAogreturn deliveryAogreturn = (DeliveryAogreturn) map.get("deliveryAogreturn");
		List<DeliveryAogreturnDetail> list = (List) map.get("detailList");
		for (DeliveryAogreturnDetail deliveryAogreturnDetail : list) {
			StorageSummaryBatch storageSummary = null;
			//批次管理商品
			if(StringUtils.isNotEmpty(deliveryAogreturnDetail.getBatchno())){
				storageSummary = distributeService.getStorageSummaryByStorageidAndGoodsidAndBatchId(deliveryAogreturn.getStorageid(), deliveryAogreturnDetail.getGoodsid(), deliveryAogreturnDetail.getBatchno());
			}
			if (storageSummary == null) {
				storageSummary = distributeService.getStorageSummaryByStoreIdAndGoodsIdService(deliveryAogreturn.getStorageid(), deliveryAogreturnDetail.getGoodsid());
			}
			if(null!=storageSummary){
				deliveryAogreturnDetail.setUsablenum(storageSummary.getUsablenum());
			}
		}
		String jsonStr = JSONUtils.listToJsonStr(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("deliveryAogreturn", deliveryAogreturn);
		request.setAttribute("detailList", jsonStr);
		request.setAttribute("listSize", list.size());
		if ("1".equals(deliveryAogreturn.getStatus()) || "2".equals(deliveryAogreturn.getStatus()) || "6".equals(deliveryAogreturn.getStatus())) {
			lockData("t_delivery_aogreturn", deliveryAogreturn.getId());
			return "success";
		} else {
			return "viewSuccess";
		}
	}

	/**
	 * 反审代配送采购退单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryAogreturn", type = 3)
	public String oppauditDeliveryAogreturn() throws Exception {
		String id = request.getParameter("id");
		// 加锁
		boolean lock = isLock("t_delivery_aogreturn", id);
		if (lock) { // 被锁定不能进行修改
			addJSONObject("lock", true);
			addLog("代配送采购退单 编码：" + id + "互斥，操作", false);
			return "success";
		}
		Map map = distributeService.oppauditDeliveryAogreturn(id);
		addJSONObject(map);
		addLog("代配送采购退单反核 编号：" + id, map);
		return "success";
	}

	/**
	 * 批量审核代配送采购退单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryAogreturn", type = 3)
	public String auditsDeliveryAogreturn() throws Exception {
		String msg = "", fmsg = "", smsg = "",famsg="";
		int fnum = 0, snum = 0,fanum=0;
		String ids = request.getParameter("ids");
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			if (isLock("t_delivery_aogreturn", id)) {
				if (StringUtils.isEmpty(fmsg)) {
					fmsg = id;
				} else {
					fmsg += "," + id;
				}
				fnum++;
			} else {
				Map map = distributeService.auditDeliveryAogreturn(id);
				if ((Boolean) map.get("flag")) {
					Map aogreturnList = distributeService.getDeliveryAogreturnInfo(id);
					DeliveryAogreturn deliveryAogreturn = (DeliveryAogreturn) aogreturnList.get("deliveryAogreturn");
					List list = (List) aogreturnList.get("detailList");
					Map m = distributeService.addDistributeOutByAogreturn(deliveryAogreturn, list);

					if (StringUtils.isEmpty(smsg) &&(Boolean) m.get("flag")) {
						smsg = id;
						snum++;
						addLog("代配送采购退单审核 编号：" + id, map);
					} 
					else if((Boolean) m.get("flag")){
						smsg += "," + id;
						snum++;
						addLog("代配送采购退单审核 编号：" + id, map);
					}
					else{
						distributeService.oppauditDeliveryAogreturn(id);
						if (StringUtils.isEmpty(famsg)) {
							famsg = id;
							fanum++;
						} 
						else{
							famsg += "," + id;
							fanum++;
						}
					}
					
				}
			}
		}
		if (fnum != 0) {

			msg = "编号：" + fmsg + "被上锁，审核失败。共" + fnum + "条数据<br>";

		}
		if (snum != 0) {
			if (StringUtils.isEmpty(msg))
				msg = "编号：" + smsg + "审核成功。共" + snum + "条数据<br>";
			else
				msg += "编号：" + smsg + "审核成功。共" + snum + "条数据<br>";
		}
		if (fanum != 0) {
			if (StringUtils.isEmpty(famsg))
				msg = "编号：" + famsg + "库存量出错，审核失败。共" + fanum + "条数据<br>";
			else
				msg += "编号：" + famsg + "库存量出错，审核失败。共" + fanum + "条数据<br>";
		}
		Map returnMap = new HashMap();
		returnMap.put("msg", msg);
		addJSONObject(returnMap);
		return "success";
	}

	/**
	 * 审核代配送采购退单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryAogreturn", type = 3)
	public String auditDeliveryAogreturn() throws Exception {
		String id = request.getParameter("id");
		// 加锁
		boolean lock = isLock("t_delivery_aogreturn", id);
		if (lock) { // 被锁定不能进行修改
			addJSONObject("lock", true);
			addLog("代配送采购退单 编码：" + id + "互斥，操作", false);
			return "success";
		}
		Map map = distributeService.auditDeliveryAogreturn(id);
		Map returnMap = new HashMap();
		if ((Boolean) map.get("flag")) {
			Map aogreturnList = distributeService.getDeliveryAogreturnInfo(id);
			DeliveryAogreturn deliveryAogreturn=(DeliveryAogreturn) aogreturnList.get("deliveryAogreturn");
			List list=(List) aogreturnList.get("detailList");
			Map m=distributeService.addDistributeOutByAogreturn(deliveryAogreturn, list);
			if((Boolean) m.get("flag")){
				returnMap.put("flag", (Boolean) m.get("flag"));
			}
			else{
				returnMap.put("flag", (Boolean) m.get("flag"));
				returnMap.put("msg",(String)m.get("msg"));
				distributeService.oppauditDeliveryAogreturn(id);
			}
		}
		else{
			returnMap.put("flag", (Boolean) map.get("flag"));
		}
		addJSONObject(returnMap);
		addLog("代配送采购退单审核 编号：" + id, returnMap);
		return "success";
	}

	/**
	 * 保存修改后的代配送采购单明细
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryAogreturnDetail", type = 3)
	public String editDeliveryAogreturnSave() throws Exception {
		boolean lock = isLock("t_delivery_aogreturn", deliveryAogreturn.getId()); // 判断锁定并解锁
		if (lock) { // 被锁定不能进行修改
			addJSONObject("lock", true);
			addLog("代配送采购退单 编码：" + deliveryAogreturn.getId() + "互斥，操作", false);
			return "success";
		}
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new DeliveryAogreturnDetail());
		Map map = distributeService.editDeliveryAogreturn(deliveryAogreturn, detailList);
		String id = null != map.get("id") ? (String) map.get("id") : "";
		String saveaudit = request.getParameter("saveaudit");
		boolean flag = (Boolean) map.get("flag");
		String msg = "";
		if (flag && "saveaudit".equals(saveaudit)) {
			Map returnmap = distributeService.auditDeliveryAogreturn(deliveryAogreturn.getId());
			if ((Boolean) returnmap.get("flag")) {
                Map aogreturnList = distributeService.getDeliveryAogreturnInfo(id);
				
				DeliveryAogreturn deliveryAogreturn=(DeliveryAogreturn) aogreturnList.get("deliveryAogreturn");
				List list=(List) aogreturnList.get("detailList");
				
				Map m=distributeService.addDistributeOutByAogreturn(deliveryAogreturn, list);
				if((Boolean) m.get("flag")){
					map.put("msg", (String)m.get("msg"));
					map.put("auditflag",m.get("flag"));
				}
				else{
					map.put("auditflag", m.get("flag"));
					map.put("msg",(String)m.get("msg"));
					distributeService.oppauditDeliveryAogreturn(id);
				}
			}
			else{
				map.put("msg",(String)returnmap.get("msg"));
				map.put("auditflag", returnmap.get("flag"));
			}
			addLog("代配送采购退单保存审核 编号：" + id, returnmap);
		} else {
			addLog("代配送采购退单修改保存编号:"+id, flag);
		}
		addJSONObject(map);
		// 解锁数据
		isLockEdit("t_delivery_aogreturn", deliveryAogreturn.getId()); // 判断锁定并解锁
		return "success";
	}

	/**
	 * 删除代配送采购退单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryAogreturn", type = 4)
	public String deleteDeliveryAogreturn() throws Exception {
		String id = request.getParameter("id");
		// 加锁
		boolean lock = isLock("t_delivery_aogreturn", id);
		if (lock) { // 被锁定不能进行修改
			addJSONObject("lock", true);
			addLog("代配送采购退单 编码：" + id + "互斥，操作", false);
			return "success";
		}
		boolean flag = distributeService.deleteDeliveryAogreturn(id);
		addJSONObject("flag", flag);
		addLog("代配送采购单删除 编号：" + id, flag);
		return "success";
	}

	// 代配送销售订单
	/**
	 * 获取代配送销售订单列表页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 4, 2015
	 */
	public String showDeliveryOrderListPage() throws Exception {
		return "success";
	}

	/**
	 * 获取代配送销售订单列表
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 4, 2015
	 */
	public String showDeliveryOrderList() throws Exception {
		// 获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		// map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = distributeService.showDeliveryOrderList(pageMap);
		addJSONObject(pageData);
		return "success";
	}

	/**
	 * 显示点击新增按钮所显示的页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 5, 2015
	 */
	public String showDeliveryOrderAddPage() throws Exception {
		request.setAttribute("type", "add");
		return "success";
	}

	/**
	 * 显示点击修改按钮所显示的页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 5, 2015
	 */
	public String showDeliveryOrderEditPage() throws Exception {
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.setAttribute("type", "edit");
		return "success";
	}

	/**
	 * 显示代配送销售订单新增页面的按钮栏
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 5, 2015
	 */
	public String deliveryOrderAddPage() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_delivery_order"));
		request.setAttribute("userName", getSysUser().getName());
		return "success";
	}

	/**
	 * 获取需要修改的代配送销售订单的信息
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	public String deliveryOrderEditPage() throws Exception {
		String id = request.getParameter("id");
		Map map = distributeService.getDeliveryOrderInfo(id);
		DeliveryOrder deliveryOrder = (DeliveryOrder) map.get("deliveryOrder");
		List<DeliveryOrderDetail> list = (List) map.get("detailList");
		for (DeliveryOrderDetail deliveryOrderDetail : list) {
			StorageSummaryBatch batch = null;
			//批次管理
			if(StringUtils.isNotEmpty(deliveryOrderDetail.getBatchno())){
				batch = distributeService.getStorageSummaryByStorageidAndGoodsidAndBatchId(deliveryOrder.getStorageid(),deliveryOrderDetail.getGoodsid(),deliveryOrderDetail.getBatchno());
			}
			if(batch == null) {
				batch = distributeService.getStorageSummaryByStoreIdAndGoodsIdService(deliveryOrder.getStorageid(),deliveryOrderDetail.getGoodsid());
			}
			if(null != batch){
				deliveryOrderDetail.setUsablenum(batch.getUsablenum());
			}
		}
		String jsonStr = JSONUtils.listToJsonStr(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("deliveryOrder", deliveryOrder);
		request.setAttribute("detailList", jsonStr);
		request.setAttribute("listSize", list.size());
		if ("1".equals(deliveryOrder.getStatus()) || "2".equals(deliveryOrder.getStatus()) || "6".equals(deliveryOrder.getStatus())) {
			// 加锁
			lockData("t_delivery_order", deliveryOrder.getId());
			return "success";
		} else {
			return "viewSuccess";
		}
	}

	/**
	 * 保存代配送销售订单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryOrder", type = 2)
	public String addDeliveryOrderSave() throws Exception {
		deliveryOrder.setStatus("2");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new DeliveryOrderDetail());
		Map map = distributeService.addDeliveryOrder(deliveryOrder, detailList);
		String id = null != map.get("id") ? (String) map.get("id") : "";
		String saveaudit = request.getParameter("saveaudit");
		boolean flag = (Boolean) map.get("flag");
		String msg = "";
		if (flag && "saveaudit".equals(saveaudit)) {
			Map returnmap = distributeService.auditDeliveryOrder(deliveryOrder.getId());
			if ((Boolean) returnmap.get("flag")) {
                Map orderList = distributeService.getDeliveryOrderInfo(id);
				
                DeliveryOrder deliveryOrder=(DeliveryOrder) orderList.get("deliveryOrder");
				List list=(List) orderList.get("detailList");
				
				Map m=distributeService.addDistributeOutByOrder(deliveryOrder, list);
				if((Boolean) m.get("flag")){
					map.put("msg", (String)m.get("msg"));
					map.put("auditflag",m.get("flag"));
				}
				else{
					map.put("auditflag", m.get("flag"));
					map.put("msg",(String)m.get("msg"));
					distributeService.oppauditDeliveryOrder(id);
				}
			}
			else{
				map.put("msg",(String)returnmap.get("msg"));
				map.put("auditflag", returnmap.get("flag"));
			}
			addLog("代配送销售订单保存审核 编号：" + id, returnmap);
		} else {
			addLog("代配送销售订单新增保存 编号："+id, flag);
		}
		addJSONObject(map);
		return "success";
	}

	/**
	 * 保存修改后的代配送销售订单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryOrderDetail", type = 3)
	public String editDeliveryOrderSave() throws Exception {
		boolean lock = isLock("t_delivery_order", deliveryOrder.getId()); // 判断锁定并解锁
		if (lock) { // 被锁定不能进行修改
			addJSONObject("lock", true);
			addLog("代配送销售订单 编码：" + deliveryOrder.getId() + "互斥，操作", false);
			return "success";
		}
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new DeliveryOrderDetail());
		Map map = distributeService.editDeliveryOrder(deliveryOrder, detailList);
		String id = null != map.get("id") ? (String) map.get("id") : "";
		String saveaudit = request.getParameter("saveaudit");
		boolean flag = (Boolean) map.get("flag");
		String msg = "";
		if (flag && "saveaudit".equals(saveaudit)) {
			Map returnmap = distributeService.auditDeliveryOrder(deliveryOrder.getId());
			if ((Boolean) returnmap.get("flag")) {
                Map orderList = distributeService.getDeliveryOrderInfo(id);
				
                DeliveryOrder deliveryOrder=(DeliveryOrder) orderList.get("deliveryOrder");
				List list=(List) orderList.get("detailList");
				
				Map m=distributeService.addDistributeOutByOrder(deliveryOrder, list);
				if((Boolean) m.get("flag")){
					map.put("msg", (String)m.get("msg"));
					map.put("auditflag",m.get("flag"));
				}
				else{
					map.put("auditflag", m.get("flag"));
					map.put("msg",(String)m.get("msg"));
					distributeService.oppauditDeliveryOrder(id);
				}
			}
			else{
				map.put("msg",(String)returnmap.get("msg"));
				map.put("auditflag", returnmap.get("flag"));
			}
			addLog("代配送销售订单保存审核 编号：" + id, returnmap);
		} else {
			addLog("代配送销售订单修改保存 编号："+id, flag);
		}
		addJSONObject(map);
		// 解锁数据
		isLockEdit("t_delivery_order", deliveryOrder.getId()); // 判断锁定并解锁
		return "success";
	}

	/**
	 * 删除代配送销售订单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryOrder", type = 4)
	public String deleteDeliveryOrder() throws Exception {
		String id = request.getParameter("id");
		// 加锁
		boolean lock = isLock("t_delivery_order", id);
		if (lock) { // 被锁定不能进行修改
			addJSONObject("lock", true);
			addLog("代配送销售订单 编码：" + id + "互斥，操作", false);
			return "success";
		}
		boolean flag = distributeService.deleteDeliveryOrder(id);
		addJSONObject("flag", flag);
		addLog("代配送销售订单删除 编号：" + id, flag);
		return "success";
	}

	/**
	 * 反审代配送销售订单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryOrder", type = 3)
	public String oppauditDeliveryOrder() throws Exception {
		String id = request.getParameter("id");
		// 加锁
				boolean lock = isLock("t_delivery_order", id);
				if (lock) { // 被锁定不能进行修改
					addJSONObject("lock", true);
					addLog("代配送销售订单 编码：" + id + "互斥，操作", false);
					return "success";
				}	
		Map map = distributeService.oppauditDeliveryOrder(id);
		addJSONObject(map);
		addLog("代配送销售订单反审 编号：" + id, map);
		return "success";
	}

	/**
	 * 批量审核代配送销售订单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryOrder", type = 3)
	public String auditsDeliveryOrder() throws Exception {
		String msg = "", fmsg = "", smsg = "", famsg = "";
		int fnum = 0, snum = 0, fanum = 0;
		String ids = request.getParameter("ids");
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			if (isLock("t_delivery_order", id)) {
				if (StringUtils.isEmpty(fmsg)) {
					fmsg = id;
				} else {
					fmsg += "," + id;
				}
				fnum++;
			} else {
				Map map = distributeService.auditDeliveryOrder(id);
				if ((Boolean) map.get("flag")) {
					Map orderList = distributeService.getDeliveryOrderInfo(id);

					DeliveryOrder deliveryOrder = (DeliveryOrder) orderList.get("deliveryOrder");
					List list = (List) orderList.get("detailList");
					Map m = distributeService.addDistributeOutByOrder(deliveryOrder, list);
					if (StringUtils.isEmpty(smsg) && (Boolean) m.get("flag")) {
						smsg = id;
						snum++;
						addLog("代配送销售订单审核 编号：" + id, map);
					} else if ((Boolean) m.get("flag")) {
						smsg += "," + id;
						snum++;
						addLog("代配送销售订单审核 编号：" + id, map);
					} else {
						distributeService.oppauditDeliveryOrder(id);
						if (StringUtils.isEmpty(famsg)) {
							famsg = id;
							fanum++;
						} else {
							famsg += "," + id;
							fanum++;
						}
					}
				}
			}
		}

		if (fnum != 0) {

			msg = "编号：" + fmsg + "被上锁，审核失败。共" + fnum + "条数据<br>";

		}
		if (snum != 0) {
			if (StringUtils.isEmpty(msg))
				msg = "编号：" + smsg + "审核成功。共" + snum + "条数据<br>";
			else
				msg += "编号：" + smsg + "审核成功。共" + snum + "条数据<br>";
		}
		if (fanum != 0) {
			if (StringUtils.isEmpty(famsg))
				msg = "编号：" + famsg + "库存量出错，审核失败。共" + fanum + "条数据<br>";
			else
				msg += "编号：" + famsg + "库存量出错，审核失败。共" + fanum + "条数据<br>";
		}
		Map returnMap = new HashMap();
		returnMap.put("msg", msg);
		addJSONObject(returnMap);
		return "success";
	}

	/**
	 * 显示到货明细的添加页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	public String showDeliveryOrderDetailAddPage() throws Exception {
		String storageid = request.getParameter("storageid");
		request.setAttribute("storageid", storageid);
		
		String customerid = request.getParameter("customerid");
		request.setAttribute("customerid", customerid);
		
		// 字段权限
		Map fieldMap = getAccessColumn("t_base_goods_info");
		request.setAttribute("fieldMap", fieldMap);
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}

	/**
	 * 显示到货明细的修改页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	public String showDeliveryOrderDetailEditPage() throws Exception {
		Map fieldMap = getAccessColumn("t_base_goods_info");
		request.setAttribute("fieldMap", fieldMap);
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}

	/**
	 * 审核代配送销售订单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryOrder", type = 3)
	public String auditDeliveryOrder() throws Exception {
		String id = request.getParameter("id");
		// 加锁
		boolean lock = isLock("t_delivery_order", id);
		if (lock) { // 被锁定不能进行修改
			addJSONObject("lock", true);
			addLog("代配送采购退单 编码：" + id + "互斥，操作", false);
			return "success";
		}
		Map map = distributeService.auditDeliveryOrder(id);
		Map returnMap = new HashMap();
		if ((Boolean) map.get("flag")) {
			Map orderList = distributeService.getDeliveryOrderInfo(id);
			DeliveryOrder deliveryOrder=(DeliveryOrder) orderList.get("deliveryOrder");
			List list=(List) orderList.get("detailList");
			Map m=distributeService.addDistributeOutByOrder(deliveryOrder, list);
			if((Boolean) m.get("flag")){
				returnMap.put("flag", (Boolean) m.get("flag"));
			}
			else{
				returnMap.put("flag", (Boolean) m.get("flag"));
				returnMap.put("msg",(String)m.get("msg"));
				distributeService.oppauditDeliveryOrder(id);
			}
		}
		else{
			returnMap.put("flag", (Boolean) map.get("flag"));
		}
		addJSONObject(returnMap);
		addLog("代配送销售订单审核 编号：" + id, returnMap);
		return "success";
	}

	// 代配送销售退单
	/**
	 * 获取代配送销售退单列表页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 4, 2015
	 */
	public String showDeliveryRejectbillListPage() throws Exception {
		return "success";
	}

	/**
	 * 获取代配送销售退单列表
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 4, 2015
	 */
	public String showDeliveryRejectbillList() throws Exception {
		// 获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		// map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = distributeService.showDeliveryRejectbillList(pageMap);
		addJSONObject(pageData);
		return "success";
	}

	/**
	 * 显示点击新增按钮所显示的页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 5, 2015
	 */
	public String showDeliveryRejectbillAddPage() throws Exception {
		request.setAttribute("type", "add");
		return "success";
	}

	/**
	 * 显示点击修改按钮所显示的页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 5, 2015
	 */
	public String showDeliveryRejectbillEditPage() throws Exception {
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.setAttribute("type", "edit");
		return "success";
	}

	/**
	 * 保存代配送销售退单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryRejectbill", type = 2)
	public String addDeliveryRejectbillSave() throws Exception {
		deliveryRejectbill.setStatus("2");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new DeliveryRejectbillDetail());
		Map map = distributeService.addDeliveryRejectbill(deliveryRejectbill, detailList);
		String id = null != map.get("id") ? (String) map.get("id") : "";
		String saveaudit = request.getParameter("saveaudit");
		boolean flag = (Boolean) map.get("flag");
		String msg = "";
		if (flag && "saveaudit".equals(saveaudit)) {
			Map returnmap = distributeService.auditDeliveryRejectbill(deliveryRejectbill.getId());
			if ((Boolean) returnmap.get("flag")) {
				Map rejectbillList = distributeService.getDeliveryRejectbillInfo(id);
				DeliveryRejectbill deliveryRejectbill=(DeliveryRejectbill) rejectbillList.get("deliveryRejectbill");
				List list=(List) rejectbillList.get("detailList");
				boolean index=distributeService.addDistributeRejectByRejectbill(deliveryRejectbill, list);
			    if(!index){
			    	distributeService.oppauditDeliveryRejectbill(id);
			    }
			}
			map.put("auditflag", returnmap.get("flag"));
			
			map.put("msg", msg);
			addLog("代配送销售退单保存审核 编号：" + id, returnmap);
		} else {
			addLog("代配送销售退单新增保存 编号："+ id, flag);
		}
		addJSONObject(map);
		return "success";
	}

	/**
	 * 保存修改后的代配送销售订单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryRejectbill", type = 3)
	public String editDeliveryRejectbillSave() throws Exception {
		boolean lock = isLock("t_delivery_rejectbill", deliveryRejectbill.getId()); // 判断锁定并解锁
		if (lock) { // 被锁定不能进行修改
			addJSONObject("lock", true);
			addLog("代配送销售退单 编码：" + deliveryRejectbill.getId() + "互斥，操作", false);
			return "success";
		}
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new DeliveryRejectbillDetail());
		Map map = distributeService.editDeliveryRejectbill(deliveryRejectbill, detailList);
		String id = null != map.get("id") ? (String) map.get("id") : "";
		String saveaudit = request.getParameter("saveaudit");
		boolean flag = (Boolean) map.get("flag");
		String msg = "";
		if (flag && "saveaudit".equals(saveaudit)) {
			Map returnmap = distributeService.auditDeliveryRejectbill(deliveryRejectbill.getId());
			if ((Boolean) returnmap.get("flag")) {
				Map rejectbillList = distributeService.getDeliveryRejectbillInfo(id);
				DeliveryRejectbill deliveryRejectbill=(DeliveryRejectbill) rejectbillList.get("deliveryRejectbill");
				List list=(List) rejectbillList.get("detailList");
				boolean index=distributeService.addDistributeRejectByRejectbill(deliveryRejectbill, list);
			    if(!index){
			    	distributeService.oppauditDeliveryRejectbill(id);
			    }
			}
			map.put("auditflag", returnmap.get("flag"));
			msg = (String) returnmap.get("msg");
			map.put("msg", msg);
			addLog("代配送销售退单保存审核 编号：" + id, returnmap);
		} else {
			addLog("代配送销售退单修改保存 编号："+id, flag);
		}
		addJSONObject(map);
		// 解锁数据
		isLockEdit("t_delivery_rejectbill", deliveryRejectbill.getId()); // 判断锁定并解锁
		return "success";
	}

	/**
	 * 显示代配送销售退单新增页面的按钮栏
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 5, 2015
	 */
	public String deliveryRejectbillAddPage() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_delivery_rejectbill"));
		request.setAttribute("userName", getSysUser().getName());
		return "success";
	}

	/**
	 * 获取需要修改的代配送销售退单的信息
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	public String deliveryRejectbillEditPage() throws Exception {
		String id = request.getParameter("id");
		Map map = distributeService.getDeliveryRejectbillInfo(id);
		DeliveryRejectbill deliveryRejectbill = (DeliveryRejectbill) map.get("deliveryRejectbill");
		List<DeliveryRejectbillDetail> list = (List) map.get("detailList");
		// for(DeliveryAogorderDetail deliveryAogorderDetail : list){
		// / StorageSummary storageSummary = null;
		// storageSummary =
		// getStorageSummarySumByGoodsid(deliveryAogorderDetail.getGoodsid());
		// / deliveryAogorderDetail.setUsablenum(storageSummary.getUsablenum());
		// }
		String jsonStr = JSONUtils.listToJsonStr(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("deliveryRejectbill", deliveryRejectbill);
		request.setAttribute("detailList", jsonStr);
		request.setAttribute("listSize", list.size());
		if ("1".equals(deliveryRejectbill.getStatus()) || "2".equals(deliveryRejectbill.getStatus()) || "6".equals(deliveryRejectbill.getStatus())) {
			// 加锁
			lockData("t_delivery_rejectbill", deliveryRejectbill.getId());
			return "success";
		} else {
			return "viewSuccess";
		}
	}

	/**
	 * 删除代配送销售退单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryRejectbill", type = 4)
	public String deleteDeliveryRejectbill() throws Exception {
		String id = request.getParameter("id");
		// 加锁
		boolean lock = isLock("t_delivery_rejectbill", id);
		if (lock) { // 被锁定不能进行修改
			addJSONObject("lock", true);
			addLog("代配送销售退单 编码：" + id + "互斥，操作", false);
			return "success";
		}
		boolean flag = distributeService.deleteDeliveryRejectbill(id);
		addJSONObject("flag", flag);
		addLog("代配送销售退单删除 编号：" + id, flag);
		return "success";
	}

	/**
	 * 审核代配送销售退单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryRejectbill", type = 3)
	public String auditDeliveryRejectbill() throws Exception {
		String id = request.getParameter("id");
		// 加锁
		boolean lock = isLock("t_delivery_rejectbill", id);
		if (lock) { // 被锁定不能进行修改
			addJSONObject("lock", true);
			addLog("代配送销售退单 编码：" + id + "互斥，操作", false);
			return "success";
		}
		Map map = distributeService.auditDeliveryRejectbill(id);
		if ((Boolean) map.get("flag")) {
			Map rejectbillList = distributeService.getDeliveryRejectbillInfo(id);
			DeliveryRejectbill deliveryRejectbill=(DeliveryRejectbill) rejectbillList.get("deliveryRejectbill");
			List list=(List) rejectbillList.get("detailList");
			boolean index=distributeService.addDistributeRejectByRejectbill(deliveryRejectbill, list);
		    if(!index){
		    	distributeService.oppauditDeliveryRejectbill(id);
		    }
		}
		addJSONObject(map);
		addLog("代配送销售退单审核 编号：" + id, map);
		return "success";
	}

	/**
	 * 反审代配送销售退单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryRejectbill", type = 3)
	public String oppauditDeliveryRejectbill() throws Exception {
		String id = request.getParameter("id");
		// 加锁
				boolean lock = isLock("t_delivery_rejectbill", id);
				if (lock) { // 被锁定不能进行修改
					addJSONObject("lock", true);
					addLog("代配送销售退单 编码：" + id + "互斥，操作", false);
					return "success";
				}	
		Map map = distributeService.oppauditDeliveryRejectbill(id);
		addJSONObject(map);
		addLog("代配送销售退单反核 编号：" + id, map);
		return "success";
	}

	/**
	 * 批量审核代配送销售退单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryRejectbill", type = 3)
	public String auditsDeliveryRejectbill() throws Exception {
		String msg = "", fmsg = "", smsg = "";
		int fnum = 0, snum = 0;
		String ids = request.getParameter("ids");
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			if (isLock("t_delivery_Rejectbill", id)) {
				if (StringUtils.isEmpty(fmsg)) {
					fmsg = id;
				} else {
					fmsg += "," + id;
				}
				fnum++;
			} else {
				Map map = distributeService.auditDeliveryRejectbill(id);
				Map rejectbillList = distributeService.getDeliveryRejectbillInfo(id);
				DeliveryRejectbill deliveryRejectbill=(DeliveryRejectbill) rejectbillList.get("deliveryRejectbill");
				List list=(List) rejectbillList.get("detailList");
				boolean index=distributeService.addDistributeRejectByRejectbill(deliveryRejectbill, list);
			    if(!index){
			    	distributeService.oppauditDeliveryRejectbill(id);
			    }
			    else{
				if (StringUtils.isEmpty(smsg) && (Boolean) map.get("flag")) {
					smsg = id;
				} else {
					smsg += "," + id;
				}
				snum++;
				addLog("代配送销售退单审核 编号：" + id, map);
			    }
			}
		}
		if (fnum != 0) {
			msg = "编号：" + fmsg + "被上锁，审核失败。共" + fnum + "条数据<br>";
		}
		if (snum != 0) {
			if (StringUtils.isEmpty(msg))
				msg = "编号：" + smsg + "审核成功。共" + snum + "条数据<br>";
			else
				msg += "编号：" + smsg + "审核成功。共" + snum + "条数据<br>";
		}
		Map returnMap = new HashMap();
		returnMap.put("msg", msg);
		addJSONObject(returnMap);
		return "success";
	}

	/**
	 * 显示到货明细的添加页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	public String showDeliveryRejectbillDetailAddPage() throws Exception {
		String supplierid = request.getParameter("supplierid");
		request.setAttribute("supplierid", supplierid);
		
		String customerid = request.getParameter("customerid");
		request.setAttribute("customerid", customerid);
		
		// 字段权限
		Map fieldMap = getAccessColumn("t_base_goods_info");
		request.setAttribute("fieldMap", fieldMap);
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}

	/**
	 * 显示到货明细的修改页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	public String showDeliveryRejectbillDetailEditPage() throws Exception {
		Map fieldMap = getAccessColumn("t_base_goods_info");
		request.setAttribute("fieldMap", fieldMap);
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}

	/**
	 * 获取辅单位名称
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	public String getAuxunitname() throws Exception {
		String goodsId = request.getParameter("goodsId");
		String storageId = request.getParameter("storageid");
		GoodsInfo_MteringUnitInfo goodsInfo = getDefaultGoodsAuxMeterUnitInfo(goodsId);

		StorageSummary storageSummary = null;
		if (StringUtils.isNotEmpty(storageId)) {
			storageSummary = getStorageSummaryByStorageidAndGoodsid(storageId, goodsId);
		} else {
			storageSummary = getStorageSummarySumByGoodsid(goodsId);
		}

		Map returnMap = new HashMap();
		returnMap.put("usablenum", storageSummary.getUsablenum());
		returnMap.put("auxunitname", goodsInfo.getMeteringunitName());
		addJSONObject(returnMap);
		return "success";
	}

	/**
	 * 计算总金额和获取可用量
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	public String getAmount() throws Exception {
		String price = request.getParameter("price");
		String unitnum = request.getParameter("unitnum");
		String goodsId = request.getParameter("goodsid");
		String boxnum = request.getParameter("boxnum");
		Double iPrice = Double.parseDouble(price) * Double.parseDouble(unitnum);
		BigDecimal iBoxnum = (new BigDecimal(unitnum)).divide(new BigDecimal(boxnum),3,BigDecimal.ROUND_HALF_UP);
		String taxamount = iPrice + "";
		String totalbox = iBoxnum + "";
		Map returnMap = new HashMap();
		returnMap.put("taxamount", taxamount);
		returnMap.put("totalbox", totalbox);
		addJSONObject(returnMap);
		return "success";
	}

	/**
	 * 通过辅数量计算总数量
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	public String computNumByAux() throws Exception {
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
		String auxstr = request.getParameter("aux");
		String unitstr = request.getParameter("unit");
		String boxnumstr = request.getParameter("boxnum");
		BigDecimal aux = BigDecimal.ZERO;
		if(StringUtils.isNotEmpty(auxstr)){
			aux = new BigDecimal(auxstr);
		}
		BigDecimal unit = BigDecimal.ZERO;
		if(StringUtils.isNotEmpty(unitstr)){
			unit = new BigDecimal(unitstr);
		}
		BigDecimal boxnum = new BigDecimal("9999");
		if(StringUtils.isNotEmpty(boxnumstr)){
			boxnum = new BigDecimal(boxnumstr);
		}
		if(unit.compareTo(boxnum) >= 0){
			BigDecimal i = unit.divide(boxnum,0,BigDecimal.ROUND_DOWN);
			aux = aux.add(i);
			unit = unit.subtract(i.multiply(boxnum)).setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
		}
		BigDecimal unitnum = aux.multiply(boxnum).add(unit).setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
		Map returnMap = new HashMap();
		returnMap.put("unitnum", unitnum);
		returnMap.put("aux", aux);
		returnMap.put("unit", unit);
		addJSONObject(returnMap);
		return "success";
	}

	/**
	 * 通过总数量计算辅数量
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	public String computNum() throws Exception {
		String unitnumstr = request.getParameter("unitnum");
		String boxnumstr = request.getParameter("boxnum");
		BigDecimal unitnum = BigDecimal.ZERO;
		if(StringUtils.isNotEmpty(unitnumstr)){
			unitnum = new BigDecimal(unitnumstr);
		}
		BigDecimal boxnum = new BigDecimal("9999");
		if(StringUtils.isNotEmpty(boxnumstr)){
			boxnum = new BigDecimal(boxnumstr);
		}
		BigDecimal aux = unitnum.divideAndRemainder(boxnum)[0];
		BigDecimal unit = unitnum.divideAndRemainder(boxnum)[1];
		Map returnMap = new HashMap();
		returnMap.put("aux", aux);
		returnMap.put("unit", unit);
		addJSONObject(returnMap);
		return "success";
	}
	
	/**
	 * 代配送采购单导出
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key="DeliveryAogorder",type=2,value="代配送采购单导出")
	public void exportAogorder()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		String state = request.getParameter("state");
		if(StringUtils.isNotEmpty(state)){
			map.put("state", state);
		}
		String title = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}
		else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		pageMap.setCondition(map);
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("businessdate", "业务日期");
		firstMap.put("supplierid", "供应商编号");
		firstMap.put("suppliername", "供应商名称");
		firstMap.put("storageid", "仓库编号");
		firstMap.put("storagename", "仓库名称");
		firstMap.put("goodsid", "商品编号");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("unitname", "单位");
		firstMap.put("auxunitname", "辅单位");
		firstMap.put("unitnum", "数量");
		firstMap.put("auxnum", "箱数");
		firstMap.put("overnum", "个数");
		firstMap.put("auxnumdetail", "辅数量");
		firstMap.put("price", "单价");
		firstMap.put("taxamount", "金额");
		firstMap.put("remark", "备注");
		result.add(firstMap);
		
		List list = distributeService.getAogorderList(pageMap);
		if(list.size() != 0){
			for(Object obj : list){
				ExportDeliveryAogorder exportDeliveryAogorder=(ExportDeliveryAogorder)obj;
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2 = PropertyUtils.describe(exportDeliveryAogorder);
				for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
					if(map2.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
						for(Map.Entry<String, Object> entry : map2.entrySet()){
							if(fentry.getKey().equals(entry.getKey())){
								objectCastToRetMap(retMap,entry);
							}
						}
					}
					else{
						retMap.put(fentry.getKey(), "");
					}
				}
				result.add(retMap);
			}
		}
		ExcelUtils.exportExcel(result, title);
	}
	
	
	/**
	 * 代配送采购退单导出
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key="DeliveryAogreturn",type=2,value="代配送采购退单导出")
	public void exportAogreturn()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		String state = request.getParameter("state");
		if(StringUtils.isNotEmpty(state)){
			map.put("state", state);
		}
		String title = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}
		else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		pageMap.setCondition(map);
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("businessdate", "业务日期");
		firstMap.put("supplierid", "供应商编号");
		firstMap.put("suppliername", "供应商名称");
		firstMap.put("storageid", "仓库编号");
		firstMap.put("storagename", "仓库名称");
		firstMap.put("goodsid", "商品编号");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("unitname", "单位");
		firstMap.put("auxunitname", "辅单位");
		firstMap.put("unitnum", "数量");
		firstMap.put("auxnum", "箱数");
		firstMap.put("overnum", "个数");
		firstMap.put("auxnumdetail", "辅数量");
		firstMap.put("price", "单价");
		firstMap.put("taxamount", "金额");
		firstMap.put("remark", "备注");
		result.add(firstMap);
		
		List list = distributeService.getAogreturnList(pageMap);
		if(list.size() != 0){
			for(Object obj : list){
				ExportDeliveryAogreturn exportDeliveryAogreturn=(ExportDeliveryAogreturn)obj;
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2 = PropertyUtils.describe(exportDeliveryAogreturn);
				for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
					if(map2.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
						for(Map.Entry<String, Object> entry : map2.entrySet()){
							if(fentry.getKey().equals(entry.getKey())){
								objectCastToRetMap(retMap,entry);
							}
						}
					}
					else{
						retMap.put(fentry.getKey(), "");
					}
				}
				result.add(retMap);
			}
		}
		ExcelUtils.exportExcel(result, title);
	}
	/**
	 * 代配送销售订单导出
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key="DeliveryOrder",type=2,value="代配送销售订单导出")
	public void exportOrder()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		String state = request.getParameter("state");
		if(StringUtils.isNotEmpty(state)){
			map.put("state", state);
		}
		String title = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}
		else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		map.put("isorderlist","0");
		pageMap.setCondition(map);
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("businessdate", "业务日期");
		firstMap.put("supplierid", "供应商编号");
		firstMap.put("customerid", "客户编号");
		firstMap.put("customername", "客户名称");
		firstMap.put("storageid", "仓库编号");
		firstMap.put("storagename", "仓库名称");
		firstMap.put("goodsid", "商品编号");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("unitname", "单位");
		firstMap.put("auxunitname", "辅单位");
		firstMap.put("unitnum", "数量");
		firstMap.put("auxnum", "箱数");
		firstMap.put("overnum", "个数");
		firstMap.put("auxnumdetail", "辅数量");
		firstMap.put("price", "单价");
		firstMap.put("taxamount", "金额");
		firstMap.put("sourceid", "客户单号");
		firstMap.put("remark", "备注");
		result.add(firstMap);
		
		List list = distributeService.getOrderList(pageMap);
		if(list.size() != 0){
			for(Object obj : list){
				ExportDeliveryOrder exportDeliveryOrder=(ExportDeliveryOrder)obj;
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2 = PropertyUtils.describe(exportDeliveryOrder);
				for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
					if(map2.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
						for(Map.Entry<String, Object> entry : map2.entrySet()){
							if(fentry.getKey().equals(entry.getKey())){
								objectCastToRetMap(retMap,entry);
							}
						}
					}
					else{
						retMap.put(fentry.getKey(), "");
					}
				}
				result.add(retMap);
			}
		}
		ExcelUtils.exportExcel(result, title);
	}
	/**
	 * 代配送销售订单按单据导出
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key="DeliveryOrder",type=2,value="代配送销售订单按单据导出")
	public void exportOrderList()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		String state = request.getParameter("state");
		if(StringUtils.isNotEmpty(state)){
			map.put("state", state);
		}
		String title = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}
		else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		map.put("isorderlist","1");
		pageMap.setCondition(map);

		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("id", "单据编号");
		firstMap.put("businessdate", "业务日期");
		firstMap.put("supplierid", "供应商编号");
		firstMap.put("suppliername", "供应商名称");
		firstMap.put("customerid", "客户编号");
		firstMap.put("customername", "客户名称");
		firstMap.put("storageid", "仓库编号");
		firstMap.put("storagename", "仓库名称");
		firstMap.put("totalvolume", "总体积(M*3)");
		firstMap.put("totalweight", "总重量");
		firstMap.put("totalbox", "总箱数");
		firstMap.put("totalamount", "总金额");
		firstMap.put("addusername", "制单人");
		firstMap.put("adddeptname", "制单人部门");
		firstMap.put("addtime", "制单时间");
		firstMap.put("sourceid", "客户单号");
		firstMap.put("mainremark", "备注");
		result.add(firstMap);

		List list = distributeService.getOrderList(pageMap);
		if(list.size() != 0){
			for(Object obj : list){
				ExportDeliveryOrder exportDeliveryOrder=(ExportDeliveryOrder)obj;
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2 = PropertyUtils.describe(exportDeliveryOrder);
				for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
					if(map2.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
						for(Map.Entry<String, Object> entry : map2.entrySet()){
							if(fentry.getKey().equals(entry.getKey())){
								objectCastToRetMap(retMap,entry);
							}
						}
					}
					else{
						retMap.put(fentry.getKey(), "");
					}
				}
				result.add(retMap);
			}
		}
		ExcelUtils.exportExcel(result, title);
	}
	/**
	 * 代配送销售退单导出
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key="DeliveryRejectbill",type=2,value="代配送销售退单导出")
	public void exportRejectbill()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		String state = request.getParameter("state");
		if(StringUtils.isNotEmpty(state)){
			map.put("state", state);
		}
		String title = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}
		else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		map.put("isorderlist","0");
		pageMap.setCondition(map);
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("businessdate", "业务日期");
		firstMap.put("supplierid", "供应商编号");
		firstMap.put("customerid", "客户编号");
		firstMap.put("customername", "客户名称");
		firstMap.put("storageid", "仓库编号");
		firstMap.put("storagename", "仓库名称");
		firstMap.put("goodsid", "商品编号");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("unitname", "单位");
		firstMap.put("auxunitname", "辅单位");
		firstMap.put("unitnum", "数量");
		firstMap.put("auxnum", "箱数");
		firstMap.put("overnum", "个数");
		firstMap.put("auxnumdetail", "辅数量");
		firstMap.put("price", "单价");
		firstMap.put("taxamount", "金额");
		firstMap.put("sourceid", "客户单号");
		firstMap.put("remark", "备注");
		result.add(firstMap);
		
		List list = distributeService.getRejectbillList(pageMap);
		if(list.size() != 0){
			for(Object obj : list){
				ExportDeliveryRejectbill exportDeliveryRejectbill=(ExportDeliveryRejectbill)obj;
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2 = PropertyUtils.describe(exportDeliveryRejectbill);
				for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
					if(map2.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
						for(Map.Entry<String, Object> entry : map2.entrySet()){
							if(fentry.getKey().equals(entry.getKey())){
								objectCastToRetMap(retMap,entry);
							}
						}
					}
					else{
						retMap.put(fentry.getKey(), "");
					}
				}
				result.add(retMap);
			}
		}
		ExcelUtils.exportExcel(result, title);
	}
	/**
	 * 代配送销售退单按单据导出
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key="DeliveryRejectbill",type=2,value="代配送销售退单按单据导出")
	public void exportRejectbillList()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		String state = request.getParameter("state");
		if(StringUtils.isNotEmpty(state)){
			map.put("state", state);
		}
		String title = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}
		else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		map.put("isorderlist","1");
		pageMap.setCondition(map);

		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("id", "单据编号");
		firstMap.put("businessdate", "业务日期");
		firstMap.put("supplierid", "供应商编号");
		firstMap.put("suppliername", "供应商名称");
		firstMap.put("customerid", "客户编号");
		firstMap.put("customername", "客户名称");
		firstMap.put("storageid", "仓库编号");
		firstMap.put("storagename", "仓库名称");
		firstMap.put("totalvolume", "总体积(M*3)");
		firstMap.put("totalweight", "总重量");
		firstMap.put("totalbox", "总箱数");
		firstMap.put("totalamount", "总金额");
		firstMap.put("addusername", "制单人");
		firstMap.put("adddeptname", "制单人部门");
		firstMap.put("addtime", "制单时间");
		firstMap.put("sourceid", "客户单号");
		firstMap.put("mainremark", "备注");
		result.add(firstMap);

		List list = distributeService.getRejectbillList(pageMap);
		if(list.size() != 0){
			for(Object obj : list){
				ExportDeliveryRejectbill exportDeliveryRejectbill=(ExportDeliveryRejectbill)obj;
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2 = PropertyUtils.describe(exportDeliveryRejectbill);
				for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
					if(map2.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
						for(Map.Entry<String, Object> entry : map2.entrySet()){
							if(fentry.getKey().equals(entry.getKey())){
								objectCastToRetMap(retMap,entry);
							}
						}
					}
					else{
						retMap.put(fentry.getKey(), "");
					}
				}
				result.add(retMap);
			}
		}
		ExcelUtils.exportExcel(result, title);
	}
	/**
	 * 导入代配送采购单
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryAogorder", type = 2)
	public String importAogorder()throws Exception{
		Map<String, Object> retMap = new HashMap<String, Object>();
		//获取第一行数据为字段的描述列表
		List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile);
		List<String> paramList2 = new ArrayList<String>();
		for(String str : paramList){
			if("业务日期".equals(str)){
				paramList2.add("businessdate");
			}else if("供应商编号".equals(str)){
				paramList2.add("supplierid");
			}else if("供应商名称".equals(str)){
				paramList2.add("suppliername");
			}else if("仓库编号".equals(str)){
				paramList2.add("storageid");
			}else if("仓库名称".equals(str)){
				paramList2.add("storagename");
			}else if("商品编号".equals(str)){
				paramList2.add("goodsid");
			}else if("商品名称".equals(str)){
				paramList2.add("goodsname");
			}else if("单位".equals(str)){
				paramList2.add("unitname");
			}else if("辅单位".equals(str)){
				paramList2.add("auxunitname");
			}else if("数量".equals(str)){
				paramList2.add("unitnum");
			}else if("箱数".equals(str)){
				paramList2.add("auxnum");
			}else if("个数".equals(str)){
				paramList2.add("overnum");
			}else if("辅数量".equals(str)){
				paramList2.add("auxnumdetail");
			}else if("单价".equals(str)){
				paramList2.add("price");
			}else if("金额".equals(str)){
				paramList2.add("taxamount");
			}else if("备注".equals(str)){
				paramList2.add("remark");
			}
			else{
				paramList2.add("null");
			}
		}
	 	List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
	 	List result = new ArrayList();
	 	if(null != list && list.size() != 0){
			Map map2 =  distributeService.importAogorder(list);
			List<String> idlist=(List<String>)map2.get("idlist");
			String logmsg="";
			for(String id : idlist){
				if(logmsg==""){
					logmsg=id;
				}
				else{
					logmsg=logmsg+","+id;
				}
				
			}
			addLog("代配送采购单导入 编号："+logmsg , map2);
			retMap.putAll(map2);
		}else{
			retMap.put("excelempty", true);
		}
	 	addJSONObject(retMap);
		return SUCCESS;
	}
	/**
	 * 导入代配送采购退单
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryAogreturn", type = 2)
	public String importAogreturn()throws Exception{
		Map<String, Object> retMap = new HashMap<String, Object>();
		//获取第一行数据为字段的描述列表
		List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile);
		List<String> paramList2 = new ArrayList<String>();
		for(String str : paramList){
			if("业务日期".equals(str)){
				paramList2.add("businessdate");
			}else if("供应商编号".equals(str)){
				paramList2.add("supplierid");
			}else if("供应商名称".equals(str)){
				paramList2.add("suppliername");
			}else if("仓库编号".equals(str)){
				paramList2.add("storageid");
			}else if("仓库名称".equals(str)){
				paramList2.add("storagename");
			}else if("商品编号".equals(str)){
				paramList2.add("goodsid");
			}else if("商品名称".equals(str)){
				paramList2.add("goodsname");
			}else if("单位".equals(str)){
				paramList2.add("unitname");
			}else if("辅单位".equals(str)){
				paramList2.add("auxunitname");
			}else if("数量".equals(str)){
				paramList2.add("unitnum");
			}else if("箱数".equals(str)){
				paramList2.add("auxnum");
			}else if("个数".equals(str)){
				paramList2.add("overnum");
			}else if("辅数量".equals(str)){
				paramList2.add("auxnumdetail");
			}else if("单价".equals(str)){
				paramList2.add("price");
			}else if("金额".equals(str)){
				paramList2.add("taxamount");
			}else if("备注".equals(str)){
				paramList2.add("remark");
			}
			else{
				paramList2.add("null");
			}
		}
	 	List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
	 	List result = new ArrayList();
	 	if(null != list && list.size() != 0){
			Map map2 =  distributeService.importAogreturn(list);
			List<String> idlist=(List<String>)map2.get("idlist");
			String logmsg="";
			for(String id : idlist){
				if(logmsg==""){
					logmsg=id;
				}
				else{
					logmsg=logmsg+","+id;
				}
				
			}
			addLog("代配送采购退单导入 编号："+logmsg , map2);
			retMap.putAll(map2);
		}else{
			retMap.put("excelempty", true);
		}
	 	addJSONObject(retMap);
		return SUCCESS;
	}

    public String showModelOrderParamPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 代配送销售订单 模板导入
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016.7.19
     */
    public String importDeliveryOrderModel() throws Exception {

        String supplierid = request.getParameter("supplierid");
        String busid = request.getParameter("busid");
        String gtype = request.getParameter("gtype");
        String pricetype = request.getParameter("pricetype");
        String fileparam = request.getParameter("fileparam");
        String[] info = fileparam.split(";");

        //读取导入文件
        InputStream is = new FileInputStream(importFile);
        Workbook hssfWorkbook = null;
        String type = ExcelUtils.getFileByFile(importFile);
        if("xls".equals(type)){
            hssfWorkbook = new HSSFWorkbook(is);
        }else if("xlsx".equals(type)){
            hssfWorkbook = new XSSFWorkbook(importFile.getPath());
        }else{
            hssfWorkbook = new XSSFWorkbook(importFile.getPath());
        }

        Map<Integer,String> goodsMap = new HashMap<Integer, String>();
        Map<Integer,String> priceMap = new HashMap<Integer, String>();
        Map<Integer,String> numMap = new HashMap<Integer, String>();
        Map<Integer,String> boxnumMap = new HashMap<Integer, String>();
        int beginRow = 0; //开始行
        int endRow = 0;//结束行
        int goodsCol = 0;
        int numCol = 0 ;
        int priceCol = 0;
        int boxnumCol = 0;
        String msg = "";
        int customerCol = -1;
        int customerRow = 0;
        String sourceid = "";
        for(int i = 0 ; i< info.length ; ++ i) {
            String value = info[i].split("=")[1];//获取参数值
            if (info[i].contains("开始单元格")) {
                String beginRowStr = value.replaceAll("[a-zA-Z]+","");
                beginRow = Integer.parseInt(beginRowStr) - 1;
            }else if(info[i].contains("商品条形码")){
                goodsCol = ExcelUtils.chagneCellColtoNumber(value);

            }else if(info[i].contains("商品数量")){
                numCol = ExcelUtils.chagneCellColtoNumber(value);

            }else if(info[i].contains("商品单价") && "1".equals(pricetype)){
                priceCol = ExcelUtils.chagneCellColtoNumber(value);

            }else if(info[i].contains("商品助记符")){
                goodsCol = ExcelUtils.chagneCellColtoNumber(value);

            }else if(info[i].contains("商品店内码")){
                goodsCol = ExcelUtils.chagneCellColtoNumber(value);
            }else if(info[i].contains("商品编码")){
                goodsCol = ExcelUtils.chagneCellColtoNumber(value);
            }else if(info[i].contains("客户单号位置")){
                String Column = value.replaceAll("\\d+", "");
                customerCol = ExcelUtils.chagneCellColtoNumber(Column);
                String customerRowStr = value.replaceAll("[a-zA-Z]+","");
                customerRow = Integer.parseInt(customerRowStr);
            }
        }
        //分别读取对应列值
        for(int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++){
            goodsMap = ExcelUtils.importExcelByGoodsGolumn(importFile,numSheet,beginRow,0,goodsCol);
            numMap =  ExcelUtils.importExcelByColumn(importFile,numSheet,beginRow,0,numCol);
            priceMap =  ExcelUtils.importExcelByColumn(importFile,numSheet,beginRow,endRow,priceCol);
            if(boxnumCol > -1){
                boxnumMap =  ExcelUtils.importExcelByColumn(importFile,numSheet,beginRow,endRow,boxnumCol);
            }
            //组装导入数据
            int orderNum = goodsMap.size();
            int endRowLine = beginRow + goodsMap.size();

            List<ModelOrder> wareList = new ArrayList<ModelOrder>();
            if(goodsMap.size() == orderNum && orderNum >= 0){
//                List<String> rowInfo = ExcelUtils.importFirstRowByIndex(importFile,customerRow-1, 0);
//                if(rowInfo.size() > 0){
//                    sourceid = (String) rowInfo.get(customerCol);
//                }
				if(customerCol>=0){
					sourceid=ExcelUtils.getSheetRowCellValue(importFile,numSheet,customerRow,customerCol);
				}
                for(int i = beginRow; i< endRowLine ; ++ i){
                    ModelOrder modelOrder = new ModelOrder();
					String unitnum=(String)numMap.get(i);
					if(StringUtils.isNotEmpty(unitnum)&&unitnum.indexOf("(")!=-1){
						unitnum=unitnum.substring(0,unitnum.indexOf("("));
					}
                    modelOrder.setUnitnum(unitnum);
                    modelOrder.setBarcode((String)goodsMap.get(i));
                    modelOrder.setBusid(busid);
                    if(priceMap.size() > 0 && "1".equals(pricetype)){
                        modelOrder.setTaxprice((String)priceMap.get(i));
                    }
                    if(boxnumMap.size() > 0){
                        modelOrder.setBoxnum((String) boxnumMap.get(i));
                    }
                    wareList.add(modelOrder);
                }

                String emptySheet = "";
                int count = 0;
                //代配送销售订单 数据
                DeliveryOrder deliveryOrder = new DeliveryOrder();
                deliveryOrder.setStatus("2");
                deliveryOrder.setCustomerid(busid);
                deliveryOrder.setSourceid(sourceid);
                Customer customer = getCustomerById(busid);
                if(null != customer){
                    deliveryOrder.setCustomername(customer.getName());
                }
                deliveryOrder.setSupplierid(supplierid);
                BuySupplier buySupplier = getBaseBuySupplierById(supplierid);
                deliveryOrder.setStorageid(buySupplier.getStorageid());

                if(wareList.size()> 0) {
                    Map detailMap = distributeService.changeModelForDetail(wareList,gtype);
                    List<DeliveryOrderDetail> detailList = (List<DeliveryOrderDetail>) detailMap.get("detailList");
                    if(detailList.size() > 0){
                        //插入数据
                        Map insertMap = distributeService.addDeliveryOrder(deliveryOrder, detailList);
                        String id = null != insertMap.get("id") ? (String) insertMap.get("id") : "";
                        boolean flag = (Boolean) insertMap.get("flag");
                        if(flag){
                            if(StringUtils.isEmpty(msg)){
                                msg = "导入成功!";
                            }
                            String unimportGoods = (String) detailMap.get("unimportGoods");
                            String disableGoods = (String) detailMap.get("disableGoods");
                            if(StringUtils.isNotEmpty(unimportGoods)){
                                msg +="第"+(numSheet+1)+"张工作表没有对应商品："+unimportGoods+",";
                            }
                            if(StringUtils.isNotEmpty(disableGoods)){
                                msg +="第"+(numSheet+1)+"张工作表禁用商品："+disableGoods+";";
                            }
                        }else{
                            msg += "导入失败";
                        }

                    }else{
                        emptySheet += (numSheet + 1) + "";
                    }
                }else{
                    emptySheet += (numSheet + 1) + "";
                }
                if(emptySheet !="" && count != hssfWorkbook.getNumberOfSheets()){
                    msg += "第"+(numSheet+1)+"张工作表导入不成功;";
                }else if(count == hssfWorkbook.getNumberOfSheets()){
                    msg = "导入失败!";
                }
            } else {
                msg = "导入失败!";
            }
        }
        request.setAttribute("msg",msg);
        return SUCCESS;
    }

	/**
	 * 导入代配送销售订单
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryOrder", type = 2)
	public String importOrder()throws Exception{
		Map<String, Object> retMap = new HashMap<String, Object>();
		//获取第一行数据为字段的描述列表
		List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile);
		List<String> paramList2 = new ArrayList<String>();
		for(String str : paramList){
			if("业务日期".equals(str)){
				paramList2.add("businessdate");
			}else if("供应商编号".equals(str)){
				paramList2.add("supplierid");
			}else if("客户编号".equals(str)){
				paramList2.add("customerid");
			}else if("客户名称".equals(str)){
				paramList2.add("customername");
			}else if("仓库编号".equals(str)){
				paramList2.add("storageid");
			}else if("仓库名称".equals(str)){
				paramList2.add("storagename");
			}else if("商品编号".equals(str)){
				paramList2.add("goodsid");
			}else if("商品名称".equals(str)){
				paramList2.add("goodsname");
			}else if("单位".equals(str)){
				paramList2.add("unitname");
			}else if("辅单位".equals(str)){
				paramList2.add("auxunitname");
			}else if("数量".equals(str)){
				paramList2.add("unitnum");
			}else if("箱数".equals(str)){
				paramList2.add("auxnum");
			}else if("个数".equals(str)){
				paramList2.add("overnum");
			}else if("辅数量".equals(str)){
				paramList2.add("auxnumdetail");
			}else if("单价".equals(str)){
				paramList2.add("price");
			}else if("金额".equals(str)){
				paramList2.add("taxamount");
			}else if("备注".equals(str)){
				paramList2.add("remark");
			}
			else{
				paramList2.add("null");
			}
		}
	 	List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
	 	List result = new ArrayList();
	 	if(null != list && list.size() != 0){
			Map map2 =  distributeService.importOrder(list);
			List<String> idlist=(List<String>)map2.get("idlist");
			String logmsg="";
			for(String id : idlist){
				if(logmsg==""){
					logmsg=id;
				}
				else{
					logmsg=logmsg+","+id;
				}
				
			}
			addLog("代配送销售订单导入 编号："+logmsg , map2);
			retMap.putAll(map2);
		}else{
			retMap.put("excelempty", true);
		}
	 	addJSONObject(retMap);
		return SUCCESS;
	}
	/**
	 * 导入代配送销售退单
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "DeliveryimportRejectbill", type = 2)
	public String importRejectbill()throws Exception{
		Map<String, Object> retMap = new HashMap<String, Object>();
		//获取第一行数据为字段的描述列表
		List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile);
		List<String> paramList2 = new ArrayList<String>();
		for(String str : paramList){
			if("业务日期".equals(str)){
				paramList2.add("businessdate");
			}else if("供应商编号".equals(str)){
				paramList2.add("supplierid");
			}else if("客户编号".equals(str)){
				paramList2.add("customerid");
			}else if("客户名称".equals(str)){
				paramList2.add("customername");
			}else if("仓库编号".equals(str)){
				paramList2.add("storageid");
			}else if("仓库名称".equals(str)){
				paramList2.add("storagename");
			}else if("商品编号".equals(str)){
				paramList2.add("goodsid");
			}else if("商品名称".equals(str)){
				paramList2.add("goodsname");
			}else if("单位".equals(str)){
				paramList2.add("unitname");
			}else if("辅单位".equals(str)){
				paramList2.add("auxunitname");
			}else if("数量".equals(str)){
				paramList2.add("unitnum");
			}else if("箱数".equals(str)){
				paramList2.add("auxnum");
			}else if("个数".equals(str)){
				paramList2.add("overnum");
			}else if("辅数量".equals(str)){
				paramList2.add("auxnumdetail");
			}else if("单价".equals(str)){
				paramList2.add("price");
			}else if("金额".equals(str)){
				paramList2.add("taxamount");
			}else if("备注".equals(str)){
				paramList2.add("remark");
			}
			else{
				paramList2.add("null");
			}
		}
	 	List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
	 	List result = new ArrayList();
	 	if(null != list && list.size() != 0){
			Map map2 =  distributeService.importRejectbill(list);
			List<String> idlist=(List<String>)map2.get("idlist");
			String logmsg="";
			for(String id : idlist){
				if(logmsg==""){
					logmsg=id;
				}
				else{
					logmsg=logmsg+","+id;
				}
				
			}
			addLog("代配送销售退单导入 编号："+logmsg , map2);
			retMap.putAll(map2);
		}else{
			retMap.put("excelempty", true);
		}
	 	addJSONObject(retMap);
		return SUCCESS;
	}
}
