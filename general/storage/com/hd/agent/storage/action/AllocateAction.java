/**
 * @(#)AllocateAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 24, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.action;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.StorageInfo;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.*;
import com.hd.agent.storage.model.*;
import com.hd.agent.storage.service.IAllocateService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 调拨单相关action
 * @author chenwei
 */
public class AllocateAction extends BaseFilesAction {
	
	private AllocateNotice allocateNotice;
	
	private AllocateOut allocateOut;
	
	private AllocateEnter allocateEnter;
	/**
	 * 调拨单相关service
	 */
	private IAllocateService allocateService;

	public IAllocateService getAllocateService() {
		return allocateService;
	}

	public void setAllocateService(IAllocateService allocateService) {
		this.allocateService = allocateService;
	}
	
	public AllocateNotice getAllocateNotice() {
		return allocateNotice;
	}

	public void setAllocateNotice(AllocateNotice allocateNotice) {
		this.allocateNotice = allocateNotice;
	}
	
	public AllocateOut getAllocateOut() {
		return allocateOut;
	}

	public void setAllocateOut(AllocateOut allocateOut) {
		this.allocateOut = allocateOut;
	}

	public AllocateEnter getAllocateEnter() {
		return allocateEnter;
	}

	public void setAllocateEnter(AllocateEnter allocateEnter) {
		this.allocateEnter = allocateEnter;
	}

	/**
	 * 显示调拨通知单新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 25, 2013
	 */
	public String showAllocateNoticeAddPage() throws Exception{
		request.setAttribute("type", "add");
		String isAllocateShowBilltype=getSysParamValue("isAllocateShowBilltype");
		request.setAttribute("isAllocateShowBilltype",isAllocateShowBilltype);
		return "success";
	}
	/**
	 * 显示调拨通知单详细新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 25, 2013
	 */
	public String allocateNoticeAddPage() throws Exception{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_storage_allocate_notice"));
		request.setAttribute("userName", getSysUser().getName());
		String isAllocateShowBilltype=getSysParamValue("isAllocateShowBilltype");
		request.setAttribute("isAllocateShowBilltype",isAllocateShowBilltype);
		return "success";
	}
	/**
	 * 显示调拨通知单明细添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 25, 2013
	 */
	public String showAllocateNoticeDetailAddPage() throws Exception{
		String outstorageid = request.getParameter("outstorageid");
		request.setAttribute("outstorageid", outstorageid);
		//字段权限
		Map fieldMap = getAccessColumn("t_storage_summary");
		request.setAttribute("fieldMap", fieldMap);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}
	/**
	 * 显示调拨通知单明细添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jun 25, 2013
	 */
	public String showAllocateNoticeDetailAddByBrandAndSortPage() throws Exception{
		String outstorageid = request.getParameter("outstorageid");
		request.setAttribute("outstorageid", outstorageid);
		//字段权限
		Map fieldMap = getAccessColumn("t_storage_summary");
		request.setAttribute("fieldMap", fieldMap);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
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
		String storageid = request.getParameter("storageid");
		Map map = CommonUtils.changeMap(request.getParameterMap());
		String brands=(String)map.get("brand");
		String defaultsorts=(String)map.get("defaultsort");
		String goodsname=(String)map.get("goodsname");
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
		PageData pageData = allocateService.getGoodsByBrandAndSort(pageMap,storageid);

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
		List<AllocateNoticeDetail> orderDetailList = JSONUtils.jsonStrToList(orderDetailJson, new AllocateNoticeDetail());
		List list=allocateService.AddOrderDetailByBrandAndSort(orderDetailList);
		addJSONArray(list);
		return "success";

	}

	/**
	 * 显示调拨通知单明细修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 25, 2013
	 */
	public String showAllocateNoticeDetailEditPage() throws Exception{
		//字段权限
		Map fieldMap = getAccessColumn("t_storage_summary");
		request.setAttribute("fieldMap", fieldMap);
		
		String goodsid = request.getParameter("goodsid");
		GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
        String storageid = request.getParameter("storageid");
        StorageInfo storageInfo = getStorageInfo(storageid);
		if(null!=goodsInfo && "1".equals(goodsInfo.getIsbatch())){
			request.setAttribute("isbatch", "1");
		}else{
			request.setAttribute("isbatch", "0");
		}
		request.setAttribute("goodsid", goodsid);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}
	/**
	 * 调拨通知单 数量 金额计算
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 25, 2013
	 */
	public String computeAllocateNoticeDetailNum() throws Exception{
		String goodsid = request.getParameter("goodsid");
		String auxunitid = request.getParameter("auxunitid");
		String unitnumStr = request.getParameter("unitnum");
		String taxpriceStr = request.getParameter("taxprice");
		String taxtypeid = request.getParameter("taxtype");
		
		
		BigDecimal unitnum = null;
		if(null==unitnumStr || "".equals(unitnumStr)){
			unitnum = new BigDecimal(0);
		}else{
			unitnum = new BigDecimal(unitnumStr);
		}
		BigDecimal taxprice = null;
		if(null==taxpriceStr || "".equals(taxpriceStr)){
			taxprice = new BigDecimal(0);
		}else{
			taxprice = new BigDecimal(taxpriceStr);
		}
		Map taxinfoMap = getTaxInfosByTaxpriceAndTaxtype(taxprice, taxtypeid,unitnum);
		
		Map returnMap = new HashMap();
		//无税单价
		BigDecimal notaxprice = (BigDecimal) taxinfoMap.get("notaxprice");
		//税种名称
		String taxtypename = (String) taxinfoMap.get("taxtypename");
		//含税金额
		BigDecimal taxamount = (BigDecimal) taxinfoMap.get("taxamount");
		//无税金额
		BigDecimal notaxamount = (BigDecimal) taxinfoMap.get("notaxamount");
		//税额
		BigDecimal tax = taxamount.subtract(notaxamount);
		
		Map auxnumMap = countGoodsInfoNumber(goodsid, auxunitid, unitnum);
		BigDecimal auxnum = (BigDecimal) auxnumMap.get("auxnum");
		String auxnumdetail = (String) auxnumMap.get("auxnumdetail");
		String auxunitname = (String) auxnumMap.get("auxunitname");
		
		returnMap.put("taxprice", taxprice);
		returnMap.put("notaxprice", notaxprice);
		returnMap.put("taxtypename", taxtypename);
		returnMap.put("taxamount", taxamount);
		returnMap.put("notaxamount", notaxamount);
		returnMap.put("tax", tax);
		returnMap.put("auxnum", auxnum);
		returnMap.put("auxnumdetail", auxnumdetail);
		returnMap.put("auxunitname", auxunitname);
		addJSONObject(returnMap);
		return "success";
	}
	/**
	 * 调拨通知单暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 25, 2013
	 */
	@UserOperateLog(key="AllocateNotice",type=2)
	public String addAllocateNoticeHold() throws Exception{
		allocateNotice.setStatus("1");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new AllocateNoticeDetail());
		Map map = allocateService.addallocateNotice(allocateNotice, detailList);
		addJSONObject(map);
		addLog("调拨通知单新增暂存 编号："+allocateNotice.getId(), map);
		return "success";
	}
	/**
	 * 调拨通知单保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 25, 2013
	 */
	@UserOperateLog(key="AllocateNotice",type=2)
	public String addAllocateNoticeSave() throws Exception{
		allocateNotice.setStatus("2");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new AllocateNoticeDetail());
		Map map = allocateService.addallocateNotice(allocateNotice, detailList);
		boolean flag = (Boolean)map.get("flag");
		String saveaudit = request.getParameter("saveaudit");
		if(flag && "saveaudit".equals(saveaudit)){
			Map returnmap = allocateService.auditAllocateNotice(allocateNotice.getId());
			map.put("auditflag", returnmap.get("flag"));
			map.put("msg", returnmap.get("msg"));
			map.put("downid", returnmap.get("downid"));
			addLog("调拨通知单保存审核 编号："+allocateNotice.getId(), returnmap);
		}else{
			addLog("调拨通知单新增保存 编号："+allocateNotice.getId(), map);
		}
		addJSONObject(map);
		return "success";
	}
	/**
	 * 调拨通知单列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 25, 2013
	 */
	public String showAllocateNoticeListPage() throws Exception{
		
		return "success";
	}
	/**
	 * 获取调拨通知单列表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 25, 2013
	 */
	public String showAllocateNoticeList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = allocateService.showAllocateNoticeList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 显示调拨通知单修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public String showAllocateNoticeEditPage() throws Exception{
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.setAttribute("type", "edit");
		String isAllocateShowBilltype=getSysParamValue("isAllocateShowBilltype");
		request.setAttribute("isAllocateShowBilltype",isAllocateShowBilltype);
		return "success";
	}
	/**
	 * 显示调拨通知单修改详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public String allocateNoticeEditPage() throws Exception{
		String id = request.getParameter("id");
		Map map = allocateService.getAllocateNoticeInfo(id);
		AllocateNotice allocateNotice = (AllocateNotice) map.get("allocateNotice");
		List list =  (List) map.get("detailList");
		String jsonStr = JSONUtils.listToJsonStr(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("allocateNotice", allocateNotice);
		request.setAttribute("detailList", jsonStr);
		request.setAttribute("listSize", list.size());
		String printlimit=getPrintLimitInfo();		
		request.setAttribute("printlimit", printlimit);
		String isAllocateShowBilltype=getSysParamValue("isAllocateShowBilltype");
		request.setAttribute("isAllocateShowBilltype",isAllocateShowBilltype);
		StorageInfo enterstorage=getStorageInfo(allocateNotice.getEnterstorageid());
		request.setAttribute("enterisaloneaccount",enterstorage.getIsaloneaccount());
		StorageInfo outstorage=getStorageInfo(allocateNotice.getOutstorageid());
		request.setAttribute("outisaloneaccount",outstorage.getIsaloneaccount());
		
		if("1".equals(allocateNotice.getStatus()) || "2".equals(allocateNotice.getStatus()) || "6".equals(allocateNotice.getStatus())){
			return "success";
		}else{
			return "viewSuccess";
		}
	}
	/**
	 * 调拨通知单修改保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	@UserOperateLog(key="AllocateNotice",type=3)
	public String editAllocateNoticeSave() throws Exception{
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new AllocateNoticeDetail());
		boolean flag = allocateService.editAllocateNotice(allocateNotice, detailList);
		Map map = new HashMap();
		String saveaudit = request.getParameter("saveaudit");
		if(flag && "saveaudit".equals(saveaudit)){
			Map returnmap = allocateService.auditAllocateNotice(allocateNotice.getId());
			map.put("auditflag", returnmap.get("flag"));
			map.put("msg", returnmap.get("msg"));
			map.put("downid", returnmap.get("downid"));
			addLog("调拨通知单保存审核 编号："+allocateNotice.getId(), returnmap);
		}else{
			addLog("调拨通知单修改保存 编号："+allocateNotice.getId(), flag);
		}
		map.put("flag", flag);
		map.put("id", allocateNotice.getId());
		addJSONObject(map);
		return "success";
	}
	/**
	 * 调拨通知单修改暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	@UserOperateLog(key="AllocateNotice",type=3)
	public String editAllocateNoticeHold() throws Exception{
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new AllocateNoticeDetail());
		boolean flag = allocateService.editAllocateNotice(allocateNotice, detailList);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", allocateNotice.getId());
		addLog("调拨通知单修改暂存 编号："+allocateNotice.getId(), flag);
		addJSONObject(map);
		return "success";
	}
	/**
	 * 显示调拨通知单查看页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public String showAllocateNoticeViewPage() throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		if("handle".equals(type)){
			request.setAttribute("type", type);
		}else{
			request.setAttribute("type", "view");
		}
		request.setAttribute("id", id);
		return "success";
	}
	/**
	 * 显示调拨通知单查看详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public String allocateNoticeViewPage() throws Exception{
		String id = request.getParameter("id");
		Map map = allocateService.getAllocateNoticeInfo(id);
		AllocateNotice allocateNotice = (AllocateNotice) map.get("allocateNotice");
		List list =  (List) map.get("detailList");
		String jsonStr = JSONUtils.listToJsonStr(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("allocateNotice", allocateNotice);
		request.setAttribute("detailList", jsonStr);
		
		String printlimit=getPrintLimitInfo();		
		request.setAttribute("printlimit", printlimit);
		String isAllocateShowBilltype=getSysParamValue("isAllocateShowBilltype");
		request.setAttribute("isAllocateShowBilltype",isAllocateShowBilltype);
		
		return "success";
	}
	/**
	 * 删除调拨通知单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	@UserOperateLog(key="AllocateNotice",type=4)
	public String deleteAllocateNotice() throws Exception{
		String id = request.getParameter("id");
		boolean flag = allocateService.deleteAllocateNotice(id);
		addJSONObject("flag", flag);
		addLog("调拨通知单修改删除 编号："+id, flag);
		return "success";
	}
	/**
	 * 调拨通知单审核
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	@UserOperateLog(key="AllocateNotice",type=3)
	public String auditAllocateNotice() throws Exception{
		String id = request.getParameter("id");
		Map map = allocateService.auditAllocateNotice(id);
		addJSONObject(map);
		addLog("调拨通知单审核 编号："+id, map);
		return "success";
	}
	/**
	 * 调拨通知单反审
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	@UserOperateLog(key="AllocateNotice",type=3)
	public String oppauditAllocateNotice() throws Exception{
		String id = request.getParameter("id");
		Map map = allocateService.oppauditAllocateNotice(id);
		addJSONObject(map);
		addLog("调拨通知单反审 编号："+id, map);
		return "success";
	}
	/**
	 * 调拨通知单明细列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public String showAllocateNoticeDetailList() throws Exception{
		String id = request.getParameter("id");
		Map map = allocateService.getAllocateNoticeInfo(id);
		List list = (List) map.get("detailList");
		addJSONArray(list);
		return "success";
	}
	/**
	 * 根据单据编号和明细编号获取调拨通知单明细详情
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 29, 2013
	 */
	public String getAllocateNoticeDetailInfo() throws Exception{
		String id = request.getParameter("id");
		String detailid = request.getParameter("detailid");
		AllocateNoticeDetail allocateNoticeDetail = allocateService.getAllocateNoticeDetailInfo(id, detailid);
		addJSONObject("allocateNoticeDetail", allocateNoticeDetail);
		return "success";
	}
	/**
	 * 调拨通知单导入
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月21日
	 */
	@UserOperateLog(key="AllocateNotice",type=2)
	public String allocateNoticeImport() throws Exception{
		try {
			List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
			List<String> paramList2 = new ArrayList<String>();
			for(String str : paramList){
				if("调出仓库".equals(str)){
					paramList2.add("outstorageid");
				}else if("调入仓库".equals(str)){
					paramList2.add("enterstorageid");
				}else if("调拨类型".equals(str)){
					paramList2.add("billtype");
				}else if("调拨价".equals(str)){
					paramList2.add("taxprice");
				}else if("商品编码".equals(str)){
					paramList2.add("goodsid");
				}else if("条形码".equals(str)){
					paramList2.add("barcode");
				}else if("箱数".equals(str)){
					paramList2.add("auxnum");
				}else if("个数".equals(str)){
					paramList2.add("auxremainder");
				}else if("批次号".equals(str)){
					paramList2.add("batchno");
				}else if("生产日期".equals(str)){
					paramList2.add("produceddate");
				}
				else if("备注".equals(str)){
					paramList2.add("remark");
				}else{
					paramList2.add("null");
				}
			}
			Map map = new HashMap();
			List list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
			if(list.size() != 0){
				map = allocateService.addAllocateNoticeByImport(list);
			}else{
				map.put("excelempty", true);
			}
			addJSONObject(map);
			String id = "";
			if(null!=map){
				id = (String) map.get("id");
			}
			addLog("导入调拨通知单 编号:"+id, map);
		} catch (Exception e) {
			Map map = new HashMap();
			map.put("dataerror", true);
			addJSONObject(map);
		}
		return "success";
	}
	/**
	 * 导出调拨通知单明细
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月28日
	 */
	public void exportAllocateNoticeList() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
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
        
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("id", "编号");
        firstMap.put("businessdate", "业务日期");
		String isAllocateShowBilltype=getSysParamValue("isAllocateShowBilltype");
		if("1".equals(isAllocateShowBilltype)){
			firstMap.put("billtypename", "调拨类型");
		}
        firstMap.put("outstoragename", "调出仓库");
        firstMap.put("enterstoragename", "调入仓库");
        firstMap.put("goodsid", "商品编码");
        firstMap.put("goodsname", "商品名称");
        firstMap.put("barcode", "条形码");
        firstMap.put("boxnum", "箱装量");
        firstMap.put("unitname", "单位");
        firstMap.put("auxnum", "箱数");
        firstMap.put("auxremainder", "个数");
        firstMap.put("taxprice", "含税单价");
        firstMap.put("notaxprice", "未税单价");
        firstMap.put("taxamount", "含税金额");
        firstMap.put("notaxamount", "未税金额");
		if("1".equals(isAllocateShowBilltype)){
			firstMap.put("costprice", "成本价");
		}
        firstMap.put("batchno", "批次号");
        firstMap.put("produceddate", "生产日期");
        firstMap.put("printtimes", "打印次数");
        firstMap.put("remark", "备注");
        result.add(firstMap);

        List<Map<String, Object>> allocateOutList = allocateService.getAllocateNoticeByExport(pageMap);
        for(Map<String, Object> map2 : allocateOutList){
            Map<String, Object> retMap = new LinkedHashMap<String, Object>();
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
        ExcelUtils.exportExcel(result, title);
	}
	/*------------调拨出库单------------------*/
	/**
	 * 显示调拨出库单新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public String showAllocateOutAddPage() throws Exception{
		request.setAttribute("view", "add");
		String isAllocateShowBilltype=getSysParamValue("isAllocateShowBilltype");
		request.setAttribute("isAllocateShowBilltype",isAllocateShowBilltype);
		return "success";
	}
	/**
	 * 显示调拨出库单新增详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public String allocateOutAddPage() throws Exception{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_storage_allocate_out"));
		request.setAttribute("userName", getSysUser().getName());
		String isAllocateShowBilltype=getSysParamValue("isAllocateShowBilltype");
		request.setAttribute("isAllocateShowBilltype",isAllocateShowBilltype);
		return "success";
	}
	/**
	 * 显示调拨出库单列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public String showAllocateOutListPage() throws Exception{
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		String deliveryAddAllocate = getSysParamValue("DeliveryAddAllocate");
		pageMap.getCondition().put("deliveryAddAllocate", deliveryAddAllocate);
		return "success";
	}
	/**
	 * 获取调拨出库单列表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public String showAllocateOutList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = allocateService.showAllocateOutList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 显示调拨出库单明细添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public String showAllocateOutDetailAddPage() throws Exception{
		String outstorageid = request.getParameter("outstorageid");
		request.setAttribute("outstorageid", outstorageid);
		//字段权限
		Map fieldMap = getAccessColumn("t_storage_summary");
		request.setAttribute("fieldMap", fieldMap);
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}
	/**
	 * 显示调拨出库单明细修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public String showAllocateOutDetailEditPage() throws Exception{
		//字段权限
		Map fieldMap = getAccessColumn("t_storage_summary");
		request.setAttribute("fieldMap", fieldMap);
		
		String goodsid = request.getParameter("goodsid");
		GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
		String storageid = request.getParameter("storageid");
		String isbatch = "0";
		if(null!=goodsInfo){
			if("1".equals(goodsInfo.getIsbatch())){
				isbatch = "1";
			}
		}
		request.setAttribute("isbatch", isbatch);
		request.setAttribute("goodsid", goodsid);
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}
	/**
	 * 调拨出库单添加保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	@UserOperateLog(key="AllocateOut",type=2)
	public String addAllocateOutSave() throws Exception{
		allocateOut.setStatus("2");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new AllocateOutDetail());
		Map map = allocateService.addAllocateOut(allocateOut, detailList);
        String id = null != map.get("id") ? (String)map.get("id") : "";
		String saveaudit = request.getParameter("saveaudit");
		boolean flag = (Boolean)map.get("flag");
		String msg = "";
		if(flag && "saveaudit".equals(saveaudit)){
			Map returnmap = allocateService.auditAllocateOut(allocateOut.getId());
			map.put("auditflag", returnmap.get("flag"));
			msg = (String) returnmap.get("msg");
			map.put("msg", msg);
			addLog("调拨单保存审核 编号："+id, returnmap);
		}else{
			addLog("调拨单"+id+"新增保存", flag);
		}
		addJSONObject(map);
		return "success";
	}
	/**
	 * 调拨出库单添加保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	@UserOperateLog(key="AllocateOut",type=2)
	public String addAllocateOutHold() throws Exception{
		allocateOut.setStatus("1");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new AllocateOutDetail());
		Map map = allocateService.addAllocateOut(allocateOut, detailList);
		addJSONObject(map);
		addLog("调拨单新增暂存 编号："+allocateOut.getId(), map);
		return "success";
	}
	/**
	 * 调拨出库单查看页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public String showAllocateOutViewPage() throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		if("handle".equals(type)){
			request.setAttribute("type", type);
		}else{
			request.setAttribute("type", "view");
		}
		request.setAttribute("id", id);
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		return "success";
	}
	/**
	 * 显示调拨出库单查看详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public String allocateOutViewPage() throws Exception{
		String id = request.getParameter("id");
		Map map = allocateService.getAllocateOutInfo(id);
		AllocateOut allocateOut = (AllocateOut) map.get("allocateOut");
		List list =  (List) map.get("detailList");
		String jsonStr = JSONUtils.listToJsonStr(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("allocateOut", allocateOut);
		request.setAttribute("detailList", jsonStr);
		String isAllocateShowBilltype=getSysParamValue("isAllocateShowBilltype");
		request.setAttribute("isAllocateShowBilltype",isAllocateShowBilltype);
		return "success";
	}
	/**
	 * 显示调拨出库单修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public String showAllocateOutEditPage() throws Exception{
		Boolean flag=true;
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.setAttribute("type", "edit");
		AllocateOut allocateOut= allocateService.getAllocateOutPureInfo(id);
		if(null==allocateOut){
			flag=false;
		}
		request.setAttribute("flag", flag);
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		return "success";
	}
	/**
	 * 显示调拨出库单修改详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public String allocateOutEditPage() throws Exception{
		String id = request.getParameter("id");
		Map map = allocateService.getAllocateOutInfo(id);
		AllocateOut allocateOut = (AllocateOut) map.get("allocateOut");
		List list =  (List) map.get("detailList");
		String jsonStr = JSONUtils.listToJsonStr(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("allocateOut", allocateOut);
		request.setAttribute("detailList", jsonStr);
		request.setAttribute("listSize", list.size());
		String isAllocateShowBilltype=getSysParamValue("isAllocateShowBilltype");
		request.setAttribute("isAllocateShowBilltype",isAllocateShowBilltype);
		StorageInfo enterstorage=getStorageInfo(allocateOut.getEnterstorageid());
		request.setAttribute("enterisaloneaccount",enterstorage.getIsaloneaccount());
		StorageInfo outstorage=getStorageInfo(allocateOut.getOutstorageid());
		request.setAttribute("outisaloneaccount",outstorage.getIsaloneaccount());
		if("1".equals(allocateOut.getStatus()) || "2".equals(allocateOut.getStatus()) || "6".equals(allocateOut.getStatus())){
			return "success";
		}else{
			return "viewSuccess";
		}
	}
	/**
	 * 调拨出库单修改保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	@UserOperateLog(key="AllocateOut",type=3)
	public String editAllocateOutSave() throws Exception{
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new AllocateOutDetail());
		boolean flag = false;
		Map editMap = allocateService.editAllocateOut(allocateOut, detailList);
		Map map = new HashMap();
		String msg = "";
		if(null!=editMap){
			flag = (Boolean) editMap.get("flag");
			msg = (String) editMap.get("msg");
		}
		String saveaudit = request.getParameter("saveaudit");
		if(flag && "saveaudit".equals(saveaudit)){
			Map returnmap = allocateService.auditAllocateOut(allocateOut.getId());
			map.put("auditflag", returnmap.get("flag"));
			msg = (String) returnmap.get("msg");
			addLog("调拨单保存审核 编号："+allocateOut.getId(), returnmap);
		}else{
			addLog("调拨单修改保存 编号："+allocateOut.getId(), flag);
		}
		map.put("flag", flag);
		map.put("id", allocateOut.getId());
		map.put("msg", msg);
		addJSONObject(map);
		return "success";
	}
	/**
	 * 调拨通知单修改暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	@UserOperateLog(key="AllocateOut",type=3)
	public String editAllocateOutHold() throws Exception{
		allocateOut.setStatus("1");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new AllocateOutDetail());
		boolean flag = false;
		Map editMap = allocateService.editAllocateOut(allocateOut, detailList);
		Map map = new HashMap();
		String msg = "";
		if(null!=editMap){
			flag = (Boolean) editMap.get("flag");
			msg = (String) editMap.get("msg");
		}
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("id", allocateOut.getId());
		addJSONObject(map);
		addLog("调拨单修改暂存 编号："+allocateOut.getId(), flag);
		return "success";
	}
	/**
	 * 调拨通知单删除
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	@UserOperateLog(key="AllocateOut",type=4)
	public String deleteAllocateOut() throws Exception{
		String id = request.getParameter("id");
		boolean flag = allocateService.deleteAllocateOut(id);
		addJSONObject("flag", flag);
		addLog("调拨单删除 编号："+id, flag);
		return "success";
	}
	/**
	 * 显示上游单据参照查询页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public String showAllocateOutRelationUpperPage() throws Exception{
		
		return "success";
	}
	/**
	 * 显示上游单据列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public String showAllocateOutSourceListPage() throws Exception{
		return "success";
	}
	/**
	 * 根据上游单据生成调拨出库单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	@UserOperateLog(key="AllocateOut",type=2)
	public String addAllocateOutByRefer() throws Exception{
		String sourceid = request.getParameter("id");
		Map map = allocateService.addAllocateOutByRefer(sourceid);
		addJSONObject(map);
		String id = "";
		if(null!=map){
			id = (String) map.get("id");
		}
		addLog("调拨单参照上游单据生成 编号："+id, map);
		return "success";
	}
	/**
	 * 调拨出库单审核
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	@UserOperateLog(key="AllocateOut",type=3)
	public String auditAllocateOut() throws Exception{
		String id = request.getParameter("id");
		Map map = allocateService.auditAllocateOut(id);
		addJSONObject(map);
		addLog("调拨单审核 编号："+id, map);
		return "success";
	}
	/**
	 * 调拨出库单反审
	 * 已废弃
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	@UserOperateLog(key="AllocateOut",type=3)
	public String oppauditAllocateOut() throws Exception{
		String id = request.getParameter("id");
		boolean flag = allocateService.oppauditAllocateOut(id);
		addJSONObject("flag", flag);
		addLog("调拨单反审 编号："+id, flag);
		return "success";
	}
	/**
	 * 获取调拨出库单明细列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public String showAllocateOutDetailList() throws Exception{
		String id = request.getParameter("id");
		Map map = allocateService.getAllocateOutInfo(id);
		List list =  (List) map.get("detailList");
		addJSONArray(list);
		return "success";
	}
	/**
	 * 显示调拨出库单明细查询页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 29, 2013
	 */
	public String showAllocateOutDetailListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 调拨单明细查询
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 29, 2013
	 */
	public String showAllocateOutDetailListQuery() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = allocateService.showAllocateOutDetailListQuery(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	
	/**
	 * 导出-调拨单明细列表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportAllocateOutData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
		pageMap.setCondition(map);
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
		PageData pageData = allocateService.showAllocateOutDetailListQuery(pageMap);
		ExcelUtils.exportExcel(exportAllocateOutDetailListDataFilter(pageData.getList()), title);
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(调拨单明细列表)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportAllocateOutDetailListDataFilter(List<Map<String,Object>> list) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("id", "编号");
		firstMap.put("businessdate", "业务日期");
		firstMap.put("outstoragename", "调出仓库");
		firstMap.put("enterstoragename", "调入仓库");
		firstMap.put("goodsid", "商品编码");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("brandname", "品牌名称");
		firstMap.put("model", "规格参数");
		firstMap.put("barcode", "条形码");
		firstMap.put("unitname", "单位");
		firstMap.put("unitnum", "数量");
		firstMap.put("auxunitnumdetail", "辅数量");
		firstMap.put("taxprice", "含税单价");
		firstMap.put("taxamount", "含税金额");
		firstMap.put("notaxamount", "无税金额");
		firstMap.put("remark", "备注");
		result.add(firstMap);
		
		if(list.size() != 0){
			for(Map<String,Object> map : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
					if(map.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
						for(Map.Entry<String, Object> entry : map.entrySet()){
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
		return result;
	}
    /**
     * 调拨（出库）单导入
     * @throws Exception
     * @author lin_xx
     * @date june 30, 2015
     */
    @UserOperateLog(key="AllocateOut",type=2)
    public String allocateOutImport() throws Exception{
        try {
            List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
            List<String> paramList2 = new ArrayList<String>();
            for(String str : paramList){
				if("调拨类型".equals(str)){
					paramList2.add("billtype");
				} else if("调出仓库".equals(str)){
                    paramList2.add("outstorageid");
                }else if("调入仓库".equals(str)){
                    paramList2.add("enterstorageid");
                }else if("调拨价".equals(str)){
					paramList2.add("taxprice");
				}else if("商品编码".equals(str)){
                    paramList2.add("goodsid");
                }else if("条形码".equals(str)){
                    paramList2.add("barcode");
                }else if("箱数".equals(str)){
                    paramList2.add("auxnum");
                }else if("个数".equals(str)){
                    paramList2.add("auxremainder");
                }else if("批次号".equals(str)){
                    paramList2.add("batchno");
                }else if("生产日期".equals(str)){
                    paramList2.add("produceddate");
                }
                else if("备注".equals(str)){
                    paramList2.add("remark");
                }else{
                    paramList2.add("null");
                }
            }
            Map map = new HashMap();
            List list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
            if(list.size() != 0){
                map = allocateService.addAllocateOutByImport(list);
            }else{
                map.put("excelempty", true);
            }
            addJSONObject(map);
            String id = "";
            if(null!=map){
                id = (String) map.get("id");
            }
            addLog("导入调拨单 编号:"+id, map);
        } catch (Exception e) {
            Map map = new HashMap();
            map.put("dataerror", true);
            addJSONObject(map);
			throw new Exception(e);
        }
        return "success";
    }

    /**
     * 调拨（出库）单导出
     * @throws Exception
     * @author lin_xx
     * @date June 11, 2015
     */
    public void exportAllocateOutList() throws Exception{

        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        String title = "";
        if(map.containsKey("excelTitle")){
            title = map.get("excelTitle").toString();
        }
        else{
            title = "list";
        }
        if(org.apache.commons.lang3.StringUtils.isEmpty(title)){
            title = "list";
        }

        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("id", "编号");
        firstMap.put("businessdate", "业务日期");
		String isAllocateShowBilltype=getSysParamValue("isAllocateShowBilltype");
		if("1".equals(isAllocateShowBilltype)){
			firstMap.put("billtypename", "调拨类型");
		}
        firstMap.put("outstoragename", "调出仓库");
        firstMap.put("enterstoragename", "调入仓库");
        firstMap.put("goodsid", "商品编码");
        firstMap.put("goodsname", "商品名称");
        firstMap.put("barcode", "条形码");
        firstMap.put("boxnum", "箱装量");
        firstMap.put("unitname", "单位");
        firstMap.put("auxnum", "箱数");
        firstMap.put("auxremainder", "个数");
        firstMap.put("taxprice", "含税单价");
        firstMap.put("notaxprice", "未税单价");
        firstMap.put("taxamount", "含税金额");
        firstMap.put("notaxamount", "未税金额");
		if("1".equals(isAllocateShowBilltype)){
			firstMap.put("costprice", "成本价");
		}
        firstMap.put("batchno", "批次号");
        firstMap.put("produceddate", "生产日期");
//        firstMap.put("deadline", "有效截止日期");
        firstMap.put("printtimes", "打印次数");
        firstMap.put("remark", "备注");
        result.add(firstMap);

        List<Map<String, Object>> allocateOutList = allocateService.getAllocateOutByExport(pageMap);
        for(Map<String, Object> map2 : allocateOutList){
            Map<String, Object> retMap = new LinkedHashMap<String, Object>();
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
        ExcelUtils.exportExcel(result, title);


    }
	
	/*------------------------调拨入库单---------------------------------*/
	/**
	 * 显示调拨入库单列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	public String showAllocateEnterListPage() throws Exception{
		return "success";
	}
	/**
	 * 获取调拨入库单列表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	public String showAllocateEnterList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = allocateService.showAllocateEnterList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 显示调拨入库单查看页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public String showAllocateEnterViewPage() throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		if("handle".equals(type)){
			request.setAttribute("type", type);
		}else{
			request.setAttribute("type", "view");
		}
		request.setAttribute("id", id);
		return "success";
	}
	/**
	 * 显示调拨入库单查看详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public String allocateEnterViewPage() throws Exception{
		String id = request.getParameter("id");
		Map map = allocateService.getAllocateEnterInfo(id);
		AllocateEnter allocateEnter = (AllocateEnter) map.get("allocateEnter");
		List list =  (List) map.get("detailList");
		String jsonStr = JSONUtils.listToJsonStr(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("allocateEnter", allocateEnter);
		request.setAttribute("detailList", jsonStr);
		request.setAttribute("listSize", list.size());
		return "success";
	}
	/**
	 * 显示调拨入库单修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public String showAllocateEnterEditPage() throws Exception{
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.setAttribute("type", "edit");
		return "success";
	}
	/**
	 * 调拨入库单修改详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public String allocateEnterEditPage() throws Exception{
		String id = request.getParameter("id");
		Map map = allocateService.getAllocateEnterInfo(id);
		AllocateEnter allocateEnter = (AllocateEnter) map.get("allocateEnter");
		List list =  (List) map.get("detailList");
		String jsonStr = JSONUtils.listToJsonStr(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("allocateEnter", allocateEnter);
		request.setAttribute("detailList", jsonStr);
		request.setAttribute("listSize", list.size());
		if("1".equals(allocateEnter.getStatus()) || "2".equals(allocateEnter.getStatus()) || "6".equals(allocateEnter.getStatus())){
			return "success";
		}else{
			return "viewSuccess";
		}
	}
	/**
	 * 显示调拨入库单明细修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public String showAllocateEnterDetailEditPage() throws Exception{
		return "success";
	}
	/**
	 * 获取调拨出库单明细详细信息
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public String getAllocateOutDetailInfo() throws Exception{
		String id = request.getParameter("id");
		String detailid = request.getParameter("detailid");
		AllocateOutDetail allocateOutDetail = allocateService.getAllocateOutDetailInfo(id, detailid);
		GoodsInfo goodsInfo = getBaseGoodsService().showGoodsInfo(allocateOutDetail.getGoodsid());
		Map map = new HashMap();
		map.put("allocateOutDetail", allocateOutDetail);
		//判断商品是否库位管理
		if(null!=goodsInfo){
			if("1".equals(goodsInfo.getIsbatch())){
				map.put("isstoragelocation", false);
			}else if("1".equals(goodsInfo.getIsstoragelocation())){
				map.put("isstoragelocation", true);
			}else{
				map.put("isstoragelocation", false);
			}
		}
		addJSONObject(map);
		return "success";
	}
	/**
	 * 调拨入库单修改保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public String editAllocateEnterSave() throws Exception{
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new AllocateEnterDetail());
		boolean flag = allocateService.editAllocateEnter(allocateEnter, detailList);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", allocateEnter.getId());
		addJSONObject(map);
		return "success";
	}
	/**
	 * 调拨入库单修改暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public String editAllocateEnterHold() throws Exception{
		allocateEnter.setStatus("1");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new AllocateEnterDetail());
		boolean flag = allocateService.editAllocateEnter(allocateEnter, detailList);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", allocateEnter.getId());
		addJSONObject(map);
		return "success";
	}
	/**
	 * 调拨入库单删除
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public String deleteAllocateEnter() throws Exception{
		String id = request.getParameter("id");
		boolean flag = allocateService.deleteAllocateEnter(id);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 调拨入库单审核
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public String auditAllocateEnter() throws Exception{
		String id = request.getParameter("id");
		Map map = allocateService.auditAllocateEnter(id);
		addJSONObject(map);
		return "success";
	}
	/**
	 * 显示上游单据查询页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public String showAllocateEnterRelationUpperPage() throws Exception{
		return "success";
	}
	/**
	 * 显示上游单据列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public String showAllocateEnterSourceListPage() throws Exception{
		return "success";
	}
	/**
	 * 根据来源单据编号生成调拨入库单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public String addAllocateEnterByRefer() throws Exception{
		String sourceid = request.getParameter("id");
		Map map = allocateService.addAllocateEnterByRefer(sourceid);
		addJSONObject(map);
		return "success";
	}
	/**
	 * 调拨出库单提交工作流
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 25, 2013
	 */
	public String submitAllocateOutPageProcess() throws Exception{
		String id = request.getParameter("id");
		Map map = allocateService.submitAllocateOutPageProcess(id);
		addJSONObject(map);
		return "success";
	}
	/**
	 * 调拨入库单提交工作流
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 25, 2013
	 */
	public String submitAllocateEnterPageProcess() throws Exception{
		String id = request.getParameter("id");
		Map map = allocateService.submitAllocateEnterPageProcess(id);
		addJSONObject(map);
		return "success";
	}
	/**
	 * 调拨通知单提交工作流
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 25, 2013
	 */
	public String submitAllocateNoticePageProcess() throws Exception{
		String id = request.getParameter("id");
		boolean flag = allocateService.submitAllocateNoticePageProcess(id);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 调拨单审核出库
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Nov 08, 2017
	 */
	@UserOperateLog(key="AllocateOut",type=3)
	public String auditAllocateStorageOut() throws Exception {
		String id = request.getParameter("id");
		Map map = allocateService.auditAllocateStorageOut(id);
		addJSONObject(map);
		addLog("调拨单审核出库 编号："+id, map);
		return "success";
	}

	/**
	 * 调拨单审核入库
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Nov 08, 2017
	 */
	@UserOperateLog(key="AllocateOut",type=3)
	public String auditAllocateStorageEnter() throws Exception {
		String id = request.getParameter("id");
		Map map = allocateService.auditAllocatStorageEnter(id);
		addJSONObject(map);
		addLog("调拨单审核入库 编号："+id, map);
		return "success";
	}

	/**
	 * 调拨单反审出库
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Nov 08, 2017
	 */
	@UserOperateLog(key="AllocateOut",type=3)
	public String oppauditAllocateStorageOut() throws Exception {
		String id = request.getParameter("id");
		Map map = allocateService.oppauditAllocateStorageOut(id);
		addJSONObject(map);
		addLog("调拨单反审出库 编号："+id, map);
		return "success";
	}

	/**
	 * 获取商品的库存成本价
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Nov 14, 2017
	 */
	public String getStorageSummaryCostprice() throws Exception{
		String goodsid=request.getParameter("goodsid");
		String storageid=request.getParameter("storageid");
		BigDecimal costprice=allocateService.getStorageSummaryCostprice(goodsid, storageid);
		addJSONObject("costprice",costprice);
		return SUCCESS;
	}

	/**
	 * 调拨差额报表
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Dec 01, 2017
	 */
	public String showAllocateDiffAmountPage() {
		return SUCCESS;
	}

	/**
	 * 获取调拨差额报表数据
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Dec 01, 2017
	 */
	public String showAllocateDiffAmountData() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData=allocateService.getAllocateDiffAmountData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}

	/**
	 * 导出调拨差额报表
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Dec 01, 2017
	 */
	public void exportAllocateDiffAmountData() throws Exception {
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
		PageData pageData = allocateService.getAllocateDiffAmountData(pageMap);
		List list = pageData.getList();
		if(null != pageData.getFooter()){
			List footer = pageData.getFooter();
			list.addAll(footer);
		}
		ExcelUtils.exportAnalysExcel(map,list);
	}

	/**
	 * 显示调拨待发量商品列表页面
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Jan 26, 2018
	 */
	public String showGoodsAllocateOutWaitListPage() throws Exception {
		String goodsid = request.getParameter("goodsid");
		String storageid = request.getParameter("storageid");
		if(StringUtils.isEmpty(storageid)){
			storageid = "";
		}
		String summarybatchid = request.getParameter("summarybatchid");
		if(StringUtils.isEmpty(summarybatchid)){
			summarybatchid = "";
		}
		request.setAttribute("storageid",storageid);
		request.setAttribute("goodsid", goodsid);
		request.setAttribute("summarybatchid", summarybatchid);
		return "success";
	}

	/**
	 * 显示调拨待发量商品列表数据
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Jan 26, 2018
	 */
	public String showAllocateOutGoodsWaitListData() throws Exception{
		String goodsid = request.getParameter("goodsid");
		String storageid = request.getParameter("storageid");
		if(StringUtils.isEmpty(storageid) || "null".equals(storageid)){
			storageid = null;
		}
		String summarybatchid = request.getParameter("summarybatchid");
		if(StringUtils.isEmpty(summarybatchid)){
			summarybatchid = null;
		}
		List list = allocateService.showAllocateOutGoodsWaitListData(goodsid,storageid,summarybatchid);
		addJSONArray(list);
		return "success";
	}

	
}

