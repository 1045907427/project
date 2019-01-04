/**
 * @(#)CheckListAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 18, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Brand;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.GoodsInfo_MteringUnitInfo;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.service.IExcelService;
import com.hd.agent.common.util.*;
import com.hd.agent.storage.model.CheckList;
import com.hd.agent.storage.model.CheckListDetail;
import com.hd.agent.storage.service.ICheckListService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 盘点单action
 * @author chenwei
 */
public class CheckListAction extends BaseFilesAction {
	/**
	 * 盘点单
	 */
	private CheckList checkList;
	/**
	 * 盘点单service
	 */
	private ICheckListService checkListService;
	/**
	 * excel service
	 */
	private IExcelService excelService;

	public CheckList getCheckList() {
		return checkList;
	}

	public void setCheckList(CheckList checkList) {
		this.checkList = checkList;
	}

	public ICheckListService getCheckListService() {
		return checkListService;
	}

	public void setCheckListService(ICheckListService checkListService) {
		this.checkListService = checkListService;
	}
	
	public IExcelService getExcelService() {
		return excelService;
	}

	public void setExcelService(IExcelService excelService) {
		this.excelService = excelService;
	}


	/**
	 * 显示盘点单新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 18, 2013
	 */
	public String showCheckListAddPage() throws Exception{
		request.setAttribute("type", "add");
		return "success";
	}
	/**
	 * 显示盘点单新增详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 18, 2013
	 */
	public String checkListAddPage() throws Exception{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_storage_checklist"));
		request.setAttribute("userName", getSysUser().getName());
		//仓库列表
		List storageList = getStorageInfoAllList();
		request.setAttribute("storageList", storageList);
		String jsonStr = JSONUtils.listToJsonStr(storageList);
		request.setAttribute("storageListJson", jsonStr);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		
		List list = getPersListByOperationType("2");
		request.setAttribute("userList", list);
		return "success";
	}
	/**
	 * 显示盘点单明细添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 18, 2013
	 */
	public String showCheckListDetailAddPage() throws Exception{
		String storageid = request.getParameter("storageid");
		if(null!=storageid){
			request.setAttribute("storageid", storageid);
		}else{
			request.setAttribute("storageid", "");
		}
		//字段权限
		Map colMap = getEditAccessColumn("t_storage_checklist_detail");
		request.setAttribute("colMap", colMap);
		
		String IsCheckListUseBatch = getSysParamValue("IsCheckListUseBatch");
		if(StringUtils.isEmpty(IsCheckListUseBatch)){
			IsCheckListUseBatch = "0";
		}
		request.setAttribute("IsCheckListUseBatch", IsCheckListUseBatch);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}
	/**
	 * 计算盘点单明细中的数量换算
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 21, 2013
	 */
	public String computeCheckListNum() throws Exception{
		String booknumStr = request.getParameter("booknum");
		String realnumStr = request.getParameter("realnum");
		String goodsid = request.getParameter("goodsid");
		String auxunitid = request.getParameter("auxunitid");
        String taxpriceStr = request.getParameter("taxprice");

        BigDecimal taxprice = null;
        if(null==taxpriceStr || "".equals(taxpriceStr)){
            GoodsInfo goodsInfo = getBaseGoodsService().showGoodsInfo(goodsid);
            taxprice = goodsInfo.getNewbuyprice();
        }else{
            taxprice = new BigDecimal(taxpriceStr);
        }

		//账面数量
		BigDecimal booknum = new BigDecimal(booknumStr);
		//实际数量
		BigDecimal realnum = null;
		if(null!=realnumStr && !"".equals(realnumStr)){
			realnum = new BigDecimal(realnumStr);
		}else{
			realnum = BigDecimal.ZERO;
		}
		//盈亏数量=实际数量-账面数量
		BigDecimal profitlossnum = realnum.subtract(booknum);
		//获取金额 辅单位数量 辅单位数量描述
		Map bookmap = countGoodsInfoNumber(goodsid, auxunitid, booknum);
		String auxunitname = (String) bookmap.get("auxunitname");
		String unitname = (String) bookmap.get("unitname");
		//获取金额 辅单位数量 辅单位数量描述
		Map realmap = countGoodsInfoNumber(goodsid, auxunitid, realnum);
		String auxremainder = (String) realmap.get("auxremainder");
		String auxInteger = (String) realmap.get("auxInteger");
		//获取金额 辅单位数量 辅单位数量描述
		Map profitlossmap = countGoodsInfoNumber(goodsid, auxunitid, profitlossnum);
		//辅比主换算率（箱装量）
		BigDecimal auxrate = (BigDecimal) bookmap.get("auxrate");
		
		Map map = new HashMap();
		map.put("auxbooknum", bookmap.get("auxnum"));
		map.put("auxbooknumdetail", bookmap.get("auxnumdetail"));
		map.put("auxrealnum", realmap.get("auxnum"));
		map.put("auxrealnumdetail", realmap.get("auxnumdetail"));
		map.put("auxunitname", auxunitname);
		map.put("unitname", unitname);
		map.put("auxremainder", auxremainder);
		map.put("auxInteger", auxInteger);
		map.put("auxprofitlossnum", profitlossmap.get("auxnum"));
		map.put("auxprofitlossnumdetail", profitlossmap.get("auxnumdetail"));
		map.put("profitlossnum", profitlossnum.toString());
        map.put("profitlossamount", profitlossnum.multiply(taxprice));


		if(null!=auxrate){
			map.put("auxrate", auxrate);
		}
		addJSONObject(map);
		return "success";
	}
	/**
	 * 通过辅单位数量 计算商品含税 未税金额 主单位数
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 8, 2013
	 */
	public String computeCheckListNumByAux() throws Exception{
		String goodsid = request.getParameter("goodsid");
		String auxIntergerStr = request.getParameter("auxInterger");
		String auxremainderStr = request.getParameter("auxremainder");
		String taxpriceStr = request.getParameter("taxprice");
		String taxtypeid = request.getParameter("taxtype");
		String booknumStr = request.getParameter("booknum");
		String auxunitid = request.getParameter("auxunitid");
		
		BigDecimal auxInterger = null;
		if(null==auxIntergerStr || "".equals(auxIntergerStr)){
			auxInterger = new BigDecimal(0);
		}else{
			auxInterger = new BigDecimal(auxIntergerStr);
		}
		BigDecimal auxremainder = null;
		if(null==auxremainderStr || "".equals(auxremainderStr)){
			auxremainder = new BigDecimal(0);
		}else{
			auxremainder = new BigDecimal(auxremainderStr);
		}
		BigDecimal taxprice = null;
		if(null==taxpriceStr || "".equals(taxpriceStr)){
			GoodsInfo goodsInfo = getBaseGoodsService().showGoodsInfo(goodsid);
			taxprice = goodsInfo.getNewbuyprice();
		}else{
			taxprice = new BigDecimal(taxpriceStr);
		}
		
		
		Map mainMap = retMainUnitByUnitAndGoodid(auxInterger, goodsid);
		BigDecimal mainnum = (BigDecimal) mainMap.get("mainUnitNum");
		mainnum = mainnum.add(auxremainder);
		
		//账面数量
		BigDecimal booknum = new BigDecimal(booknumStr);
		//实际数量
		BigDecimal realnum = mainnum;
		//盈亏数量=账面数量-实际数量
		BigDecimal profitlossnum = realnum.subtract(booknum);
		//获取金额 辅单位数量 辅单位数量描述
		Map profitlossmap = countGoodsInfoNumber(goodsid, auxunitid, profitlossnum);
		//辅比主换算率（箱装量）
		BigDecimal auxrate = (BigDecimal) profitlossmap.get("auxrate");
		Map taxinfoMap = getTaxInfosByTaxpriceAndTaxtype(taxprice, taxtypeid,mainnum);
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

        GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
        String auxnumdetail = "";
        //辅数量大于等于箱装量
        if(auxremainder.compareTo(goodsInfo.getBoxnum()) >= 0){
            BigDecimal num = auxremainder.divideAndRemainder(goodsInfo.getBoxnum())[0];
            auxInterger = auxInterger.add(num);
            auxremainder = auxremainder.divideAndRemainder(goodsInfo.getBoxnum())[1];
        }

        GoodsInfo_MteringUnitInfo meteringUnit = getDefaultGoodsAuxMeterUnitInfo(goodsid);
        if(null!=meteringUnit){
            auxnumdetail += auxInterger.abs().intValue()+meteringUnit.getMeteringunitName();
        }else{
            auxnumdetail += auxInterger.abs().intValue()+"箱";
        }
        if(auxremainder.compareTo(BigDecimal.ZERO) != 0){
            auxnumdetail += auxremainder.abs()+goodsInfo.getMainunitName();
        }
		
		Map returnMap = new HashMap();
		returnMap.put("taxprice", taxprice);
		returnMap.put("notaxprice", notaxprice);
		returnMap.put("taxtypename", taxtypename);
		returnMap.put("taxamount", taxamount);
		returnMap.put("notaxamount", notaxamount);
		returnMap.put("tax", tax);
		returnMap.put("mainnum", mainnum);
        returnMap.put("auxremainder",auxremainder);
        returnMap.put("auxInterger",auxInterger);
        if(mainnum.compareTo(BigDecimal.ZERO)==-1){
            auxnumdetail ="-"+auxnumdetail;
        }
        returnMap.put("auxnumdetail", CommonUtils.strDigitNumDeal(auxnumdetail));
		
		returnMap.put("auxprofitlossnum", profitlossmap.get("auxnum"));
		returnMap.put("auxprofitlossnumdetail", profitlossmap.get("auxnumdetail"));
		returnMap.put("profitlossnum", profitlossnum.toString());
        returnMap.put("profitlossamount", profitlossnum.multiply(taxprice));

		if(null!=auxrate){
			returnMap.put("auxrate", auxrate);
		}
		addJSONObject(returnMap);
		return "success";
	}
	/**
	 * 显示盘点单明细修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 21, 2013
	 */
	public String showCheckListDetailEditPage() throws Exception{
		//字段权限
		Map colMap = getEditAccessColumn("t_storage_checklist_detail");
		request.setAttribute("colMap", colMap);
		List list = getPersListByOperationType("2");
		SysUser sysUser = getSysUser();
		request.setAttribute("userList", list);
		request.setAttribute("thisUserid", sysUser.getPersonnelid());
		
		String IsCheckListUseBatch = getSysParamValue("IsCheckListUseBatch");
		if(StringUtils.isEmpty(IsCheckListUseBatch)){
			IsCheckListUseBatch = "0";
		}
		request.setAttribute("IsCheckListUseBatch", IsCheckListUseBatch);
		
		String goodsid = request.getParameter("goodsid");
		String checklistid = request.getParameter("checklistid");
		String id = request.getParameter("id");

		GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
		if(null!=goodsInfo){
			request.setAttribute("isbatch", goodsInfo.getIsbatch());
		}else{
			request.setAttribute("isbatch", "0");
		}
		request.setAttribute("goodsid", goodsid);
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		CheckListDetail checkListDetail=checkListService.getCheckListDetailById(checklistid,id);
		request.setAttribute("checkListDetail", checkListDetail);
		return "success";
	}
	/**
	 * 根据仓库编码获取仓库下面的商品信息列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 22, 2013
	 */
	public String getCheckListDetailList() throws Exception{
		String storageid = request.getParameter("storageid");
		String brands = request.getParameter("brands");
		String goodssorts = request.getParameter("goodssorts");
		List list = checkListService.getCheckListDetail(storageid,brands,goodssorts);
		addJSONArray(list);
		return "success";
	}

	/**
	 * 检查该商品是否存在该单据明细中
	 * true 存在 false 不存在
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-02-15
	 */
	public String doCheckListDetailIsHadGoods()throws Exception{
		String goodsid = request.getParameter("goodsid");
		String checklistid = request.getParameter("checklistid");
		boolean flag = checkListService.doCheckListDetailIsHadGoods(goodsid,checklistid);
		addJSONObject("flag",flag);
		return SUCCESS;
	}

	/**
	 * 添加盘点单明细列表数据根据查询到的数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-02-04
	 */
	@UserOperateLog(key="CheckList",type=2)
	public String addCheckListDetail()throws Exception{
		String storageid = request.getParameter("storageid");
		String brands = request.getParameter("brands");
		String goodssorts = request.getParameter("goodssorts");
		Map map = checkListService.addCheckListDetail(checkList,storageid,brands,goodssorts);
		addLog("新增盘点单明细 编号："+(checkList==null?"":checkList.getId()), map);
		addJSONObject(map);
		return SUCCESS;
	}

	/**
	 * 添加盘点单单个明细数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-02-16
	 */
	@UserOperateLog(key="CheckList",type=2)
	public String addSaveCheckListDetail()throws Exception{
		List<CheckListDetail> detailList = new ArrayList<CheckListDetail>();
		String detailListJson = request.getParameter("detailList");
		if (StringUtils.isNotEmpty(detailListJson)){
			detailList = JSONUtils.jsonStrToList(detailListJson, new CheckListDetail());
		}
		Map map = checkListService.addSaveCheckListDetail(checkList,detailList);
		addLog("新增盘点单明细 编号："+(checkList==null?"":checkList.getId()), map);
		addJSONObject(map);
		return SUCCESS;
	}

	/**
	 * 修改盘点单单个明细数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-02-17
	 */
    @UserOperateLog(key="CheckList",type=3)
	public String editSaveCheckListDetail()throws Exception{
		List<CheckListDetail> detailList = new ArrayList<CheckListDetail>();
		String detailListJson = request.getParameter("detailList");
		if (StringUtils.isNotEmpty(detailListJson)){
			detailList = JSONUtils.jsonStrToList(detailListJson, new CheckListDetail());
		}
		Map map = checkListService.editSaveCheckListDetail(checkList,detailList);
        if(null!=detailList && detailList.size()>0){
            CheckListDetail checkListDetail = detailList.get(0);
            addLog("修改盘点单明细 编号："+(checkList==null?"":checkList.getId())+",商品编号："+checkListDetail.getGoodsid()+",数量："+checkListDetail.getRealnum().toString(), map);
        }else{
			CheckListDetail checkListDetail = detailList.get(0);
			addLog("修改盘点单明细 编号："+(checkList==null?"":checkList.getId()), map);
		}

		addJSONObject(map);
		return SUCCESS;
	}

	/**
	 * 删除盘点单明细列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-02-17
	 */
	@UserOperateLog(key="CheckList",type=4)
	public String deleteCheckListDetails()throws Exception{
		String delDetailListJson = request.getParameter("delDetailList");
		List<CheckListDetail> delDetailList = new ArrayList<CheckListDetail>();
		if (StringUtils.isNotEmpty(delDetailListJson)){
			delDetailList = JSONUtils.jsonStrToList(delDetailListJson, new CheckListDetail());
		}
		Map map = checkListService.deleteCheckListDetails(checkList,delDetailList);
		addJSONObject(map);
		addLog("删除盘点单明细 编号："+(checkList==null?"":checkList.getId()), map);
		return SUCCESS;
	}
	/**
	 * 根据仓库编码和商品类别获取仓库下面的商品信息列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年8月28日
	 */
	public String getCheckListDetailByGoodssorts() throws Exception{
		String storageid = request.getParameter("storageid");
		String goodssorts = request.getParameter("goodssorts");
		List list = checkListService.getCheckListDetailByGoodssorts(storageid,goodssorts);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 盘点单添加保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 22, 2013
	 */
	@UserOperateLog(key="CheckList",type=2)
	public String addCheckListSave() throws Exception{
		if(null!=checkList){
			if (isAutoCreate("t_storage_checklist")) {
				// 获取自动编号
				String id = getAutoCreateSysNumbderForeign(checkList, "t_storage_checklist");
				checkList.setId(id);
			}
			checkList.setStatus("2");
//			String checkListDetailJson = request.getParameter("checkListDetailJson");
//			List detailList = JSONUtils.jsonStrToList(checkListDetailJson, new CheckListDetail());
			boolean flag = checkListService.editJustCheckList(checkList);
			Map map = new HashMap();
			map.put("flag", flag);
			map.put("id", checkList.getId());
			map.put("status", checkList.getStatus());
			addJSONObject(map);
			addLog("盘点单新增保存 编号："+checkList.getId(), flag);
		}
		return "success";
	}
	/**
	 * 盘点单添加暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 22, 2013
	 */
	@UserOperateLog(key="CheckList",type=2)
	public String addCheckListHold() throws Exception{
		if (isAutoCreate("t_storage_checklist")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(checkList, "t_storage_checklist");
			checkList.setId(id);
		}
		checkList.setStatus("1");
		String checkListDetailJson = request.getParameter("checkListDetailJson");
		List detailList = JSONUtils.jsonStrToList(checkListDetailJson, new CheckListDetail());
		boolean flag = checkListService.addCheckList(checkList, detailList);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", checkList.getId());
		map.put("status", checkList.getStatus());
		addJSONObject(map);
		addLog("盘点单新增暂存 编号："+checkList.getId(), flag);
		return "success";
	}
	/**
	 * 显示盘点单详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 23, 2013
	 */
	public String showCheckListViewPage() throws Exception{
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
	 * 显示盘点单详细信息
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 22, 2013
	 */
	public String checkListViewPage() throws Exception{
		String id = request.getParameter("id");
		Map map = checkListService.getCheckListInfo(id);
		CheckList checkList = (CheckList) map.get("checkList");
		List list = (List) map.get("checkListDetail");
		String jsonStr = JSONUtils.listToJsonStrFilterWithOutGoodsInfo(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("checkList", checkList);
		request.setAttribute("checkListDetailList", jsonStr);
		request.setAttribute("statusList", statusList);
		//仓库列表
		List storageList = getStorageInfoAllList();
		request.setAttribute("storageList", storageList);
		
		List userList = getPersListByOperationType("2");
		request.setAttribute("userList", userList);
		return "success";
	}
	/**
	 * 显示盘点单列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 22, 2013
	 */
	public String showCheckListPage() throws Exception{
		request.setAttribute("firstday", CommonUtils.getMonthDateStr());
		request.setAttribute("today", CommonUtils.getTodayDataStr());
		//仓库列表
		List storageList = getStorageInfoAllList();
		request.setAttribute("storageList", storageList);
		return "success";
	}
	/**
	 * 获取盘点单分页数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 22, 2013
	 */
	public String showCheckListData() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = checkListService.showCheckListPageData(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 显示盘点单修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 23, 2013
	 */
	public String showCheckListEditPage() throws Exception{
		String id = request.getParameter("id");
		request.setAttribute("type", "edit");
		request.setAttribute("id", id);
		return "success";
	}

	/**
	 * 根据盘点单编号获取其明细列表分页数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2015-02-03
	 */
	public String getCheckListDetailData()throws Exception{
		String id = request.getParameter("id");
		Map map = CommonUtils.changeMap(request.getParameterMap());
		map.put("checklistid",id);
		pageMap.setCondition(map);
		PageData pageData = checkListService.getCheckListDetailData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	/**
	 * 显示盘点单详详情修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 23, 2013
	 */
	public String checkListEditPage() throws Exception{
		String id = request.getParameter("id");
		Map map = checkListService.getCheckListInfo(id);
		CheckList checkList = (CheckList) map.get("checkList");
//		List list = (List) map.get("checkListDetail");
//		String jsonStr = JSONUtils.listToJsonStrFilterWithOutGoodsInfo(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("checkList", checkList);
//		request.setAttribute("checkListDetailList", jsonStr);
		request.setAttribute("statusList", statusList);
		//仓库列表
		List storageList = getStorageInfoAllList();
		request.setAttribute("storageList", storageList);
		String storageListJson = JSONUtils.listToJsonStr(storageList);
		request.setAttribute("storageListJson", storageListJson);
		List userList = getPersListByOperationType("2");
		request.setAttribute("userList", userList);
		if(null!=checkList){
			if("1".equals(checkList.getStatus()) || "2".equals(checkList.getStatus()) || "6".equals(checkList.getStatus())){
				return "success";
			}else{
				return "viewSuccess";
			}
		}else{
			return "addSuccess";
		}
		
	}
	/**
	 * 盘点单修改暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 23, 2013
	 */
	@UserOperateLog(key="CheckList",type=3)
	public String editCheckListHold() throws Exception{
		checkList.setStatus("1");
		String checkListDetailJson = request.getParameter("checkListDetailJson");
		List detailList = JSONUtils.jsonStrToList(checkListDetailJson, new CheckListDetail());
		boolean flag = checkListService.editCheckList(checkList, detailList);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", checkList.getId());
		map.put("status", checkList.getStatus());
		addJSONObject(map);
		addLog("盘点单修改暂存 编号："+checkList.getId(), flag);
		return "success";
	}
	/**
	 * 盘点单修改保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 23, 2013
	 */
	@UserOperateLog(key="CheckList",type=3)
	public String editCheckListSave() throws Exception{
		String checksave = request.getParameter("checksave");
//		String checkListDetailJson = request.getParameter("checkListDetailJson");
//		List detailList = JSONUtils.jsonStrToList(checkListDetailJson, new CheckListDetail());
		boolean flag = checkListService.editJustCheckList(checkList);
		Map map = new HashMap();
		boolean auditflag = false;
		if(flag && "checksave".equals(checksave)){
			auditflag = checkListService.updateCheckListFinish(checkList.getId());
		}
		map.put("flag", flag);
		map.put("id", checkList.getId());
		map.put("status", checkList.getStatus());
		map.put("auditflag", auditflag);
		addJSONObject(map);
		if(auditflag){
			addLog("盘点单修改保存盘点完成 编号："+checkList.getId(), auditflag);
		}else{
			addLog("盘点单修改保存 编号："+checkList.getId(), flag);
		}
		return "success";
	}
	/**
	 * 盘点单删除
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 23, 2013
	 */
	@UserOperateLog(key="CheckList",type=4)
	public String deleteCheckList() throws Exception{
		String ids = request.getParameter("id");
		if(null!=ids){
			Map map = new HashMap();
			int delSuccess = 0;
			int delFail = 0;
			String[] idArr = ids.split(",");
			for(String id :idArr){
				boolean flag1 = checkListService.deleteCheckList(id);
				if(flag1){
					delSuccess ++;
				}else{
					delFail ++;
				}
			}
            map.put("delSuccess", delSuccess);
            map.put("delFail", delFail);
            if(delSuccess == 0 &&delFail >0){
                map.put("flag", false);
                addJSONObject(map);
                addLog("盘点单删除 编号："+ids, false);

            }else{
                map.put("flag", true);
                addJSONObject(map);
                addLog("盘点单删除 编号："+ids, true);

            }

		}
		return "success";
	}
	/**
	 * 显示盘点单复制页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 24, 2013
	 */
	public String checkListCopyPage() throws Exception{
		String id = request.getParameter("id");
		Map map = checkListService.getCheckListInfo(id);
		CheckList checkList = (CheckList) map.get("checkList");
		List list = (List) map.get("checkListDetail");
		String jsonStr = JSONUtils.listToJsonStrFilterWithOutGoodsInfo(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("checkList", checkList);
		request.setAttribute("checkListDetailList", jsonStr);
		request.setAttribute("statusList", statusList);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_storage_checklist"));
		request.setAttribute("userName", getSysUser().getName());
		return "success";
	}
	/**
	 * 根据仓库编码判断是否有未审核的盘点单存在
	 * true:有未审核的盘点单 false无盘点单存在
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 24, 2013
	 */
	public String isHaveCheckListByStorageid() throws Exception{
		String storageid = request.getParameter("storageid");
		Map map = checkListService.isHaveCheckListByStorageid(storageid);
		addJSONObject(map);
		return "success";
	}
	/**
	 * 审核盘点单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 24, 2013
	 */
	@UserOperateLog(key="CheckList",type=3)
	public String auditCheckList() throws Exception{
		String id = request.getParameter("id");
		Map map = checkListService.auditCheckList(id);
		addJSONObject(map);
		addLog("盘点单审核 编号："+id, map);
		return "success";
	}
	/**
	 * 盘点单反审
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 28, 2013
	 */
	@UserOperateLog(key="CheckList",type=3)
	public String oppauditCheckList() throws Exception{
		String id = request.getParameter("id");
		Map map = checkListService.oppauditCheckList(id);
		addJSONObject(map);
		addLog("盘点单反审 编号："+id, map);
		return "success";
	}
	/**
	 * 获取盘点单明细列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 24, 2013
	 */
	public String showCheckListDetailData() throws Exception{
		String id = request.getParameter("id");
		List list = checkListService.showCheckListDetailData(id);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 盘点单提交工作流
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 25, 2013
	 */
	public String submitCheckListPageProcess() throws Exception{
		String id = request.getParameter("id");
		boolean flag = checkListService.submitCheckListPageProcess(id);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 显示仓库下的品牌列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 8, 2013
	 */
	public String showStorageBrandListPage() throws Exception{
		String storageid = request.getParameter("storageid");
		List<String> list = getBaseStorageSummaryService().getStorageBrandList(storageid);
		List dataList = new ArrayList();
		for(String brandid : list){
			Brand brand = getBaseGoodsService().getBrandInfo(brandid);
			if(null!=brand){
				dataList.add(brand);
			}
		}
		String jsonStr = JSONUtils.listToJsonStr(dataList);
		request.setAttribute("detailList", jsonStr);
		return "success";
	}
	/**
	 * 显示商品分类页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年8月28日
	 */
	public String showStorageGoodsSortListPage() throws Exception{
		return "success";
	}
	/**
	 * 盘点单检查 验证数量是否一致
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 9, 2013
	 */
	@UserOperateLog(key="CheckList",type=0)
	public String getCheckListNumIsTure() throws Exception{
		String id = request.getParameter("id");
		Map map = checkListService.getCheckListNumIsTure(id);
		addJSONObject(map);
		addLog("盘点单检查 编号："+id, true);
		return "success";
	}
	/**
	 * 盘点单盘点完成
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 9, 2013
	 */
	@UserOperateLog(key="CheckList",type=0)
	public String checkListFinish() throws Exception{
		String id = request.getParameter("id");
		boolean flag = checkListService.updateCheckListFinish(id);
		addJSONObject("flag", flag);
		addLog("盘点单盘点完成 编号："+id, flag);
		return "success";
	}
	/**
	 * 结束盘点
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 26, 2013
	 */
	@UserOperateLog(key="CheckList",type=0)
	public String closeCheckListByCheck() throws Exception{
		String id = request.getParameter("id");
		Map map = checkListService.closeCheckListByCheck(id);
		addJSONObject(map);
		addLog("盘点单盘点结束 编号："+id, map);
		return "success";
	}
	/**
	 * 根据盘点结果 重新生成盘点单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 9, 2013
	 */
	@UserOperateLog(key="CheckList",type=2)
	public String addCheckListByCheck() throws Exception{
		String id = request.getParameter("id");
		Map map = checkListService.addCheckListByCheck(id);
		addJSONObject(map);
		String newid = "";
		if(null!=map){
			newid = (String) map.get("newid");
		}
		addLog("根据盘点结果重新生成盘点单 原盘点单号:"+id+",新盘点单号："+newid, map);
		return "success";
	}
	/**
	 * 导出盘点单
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 28, 2013
	 */
	public void exportCheckListData() throws Exception{
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
		List list = checkListService.exportCheckListData(pageMap);
		ExcelUtils.exportExcel(exportDataFilter(list), title);
	}
	/**
	 * 导入盘点单数据 进行盘点
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 28, 2013
	 */
	@UserOperateLog(key="CheckList",type=3)
	public String importCheckListData() throws Exception{
		String id = request.getParameter("id");
		List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
		List<String> paramList2 = new ArrayList<String>();
		for(String str : paramList){
			if("盘点单号".equals(str)){
				paramList2.add("id");
			}else if("业务日期".equals(str)){
				paramList2.add("businessdate");
			}else if("盘点仓库".equals(str)){
				paramList2.add("storagename");
			}else if("第几次盘点".equals(str)){
				paramList2.add("checkno");
			}else if("明细号".equals(str)){
				paramList2.add("detailid");
			}
			else if("商品编码".equals(str)){
				paramList2.add("goodsid");
			}
			else if("商品名称".equals(str)){
				paramList2.add("name");
			}
			else if("品牌名称".equals(str)){
				paramList2.add("brandname");
			}else if("规格参数".equals(str)){
				paramList2.add("model");
			}else if("条形码".equals(str)){
				paramList2.add("barcode");
			}else if("箱装量".equals(str)){
				paramList2.add("boxnum");
			}else if("单位".equals(str)){
				paramList2.add("unitname");
			}else if("账面总数".equals(str)){
				paramList2.add("booknum");
			}else if("账面箱数".equals(str)){
				paramList2.add("auxbooknum");
			}else if("账面个数".equals(str)){
				paramList2.add("auxbooknumremainder");
			}
			else if("实际总数".equals(str)){
				paramList2.add("realnum");
			}
			else if("实际箱数".equals(str)){
				paramList2.add("auxrealnum");
			}
			else if("实际个数".equals(str)){
				paramList2.add("auxrealnumremainder");
			}
			else if("盈亏总数".equals(str)){
				paramList2.add("profitlossnum");
			}
			else if("盈亏箱数".equals(str)){
				paramList2.add("auxprofitlossnum");
			}
			else if("盈亏个数".equals(str)){
				paramList2.add("auxprofitlossnumremainder");
			}else if("批次号".equals(str)){
                paramList2.add("batchno");
            }else if("生产日期".equals(str)){
                paramList2.add("produceddate");
            }
			else if("盘点人".equals(str)){
				paramList2.add("checkusername");
			}else{
				paramList2.add("null");
			}
		}
		Map map = new HashMap();
		List list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
		if(list.size() != 0){
			map = checkListService.updateCheckListDataByImport(list, id);
		}else{
			map.put("excelempty", true);
		}
		addJSONObject(map);
		addLog("盘点单导入盘点 编号:"+id, map);
		return "success";
	}
	
	/**
	 * 根据文件类型导入盘点单数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 13, 2014
	 */
	@UserOperateLog(key="CheckList",type=3)
	public String importCheckListDataOfTheStyle()throws Exception{
		String id = request.getParameter("id");
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		//判断文件格式filestype: 1.excel文件、2.txt文件
		String filestype = null;
		if(map.containsKey("filestype") && null != map.get("filestype")){
			filestype = (String)map.get("filestype");
		}
		if("1".equals(filestype)){
			importCheckListData();
		}else if("2".equals(filestype)){
			List<String> paramList = new ArrayList<String>();
			paramList.add("goodsid");//商品编码
			paramList.add("name");//商品名称
			paramList.add("realnum");//实际总数
			//获取要导入的数据
			List list = checkListService.getImoportTxtData(excelFile,paramList,id);
			Map map2 = new HashMap();
			if(list.size() != 0){
				map2 = checkListService.updateCheckListDataByImport(list, id);
			}else{
				map2.put("excelempty", true);
			}
			addJSONObject(map2);
			addLog("盘点单导入盘点 编号:"+id, map2);
		}
		return SUCCESS;
	}
	
	/**
	 * 数据转换 list转成符合excel导出的数据格式
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 28, 2013
	 */
	private List<Map<String, Object>> exportDataFilter(List<Map> list) throws Exception{
		//字段权限
		Map colMap = getEditAccessColumn("t_storage_checklist_detail");
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("id", "盘点单号");
		firstMap.put("businessdate", "业务日期");
		firstMap.put("storagename", "盘点仓库");
		firstMap.put("checkno", "第几次盘点");
		firstMap.put("detailid", "明细号");
		firstMap.put("goodsid", "商品编码");
		firstMap.put("name", "商品名称");
		firstMap.put("brandname", "品牌名称");
		firstMap.put("model", "规格参数");
		firstMap.put("barcode", "条形码");
		firstMap.put("boxnum", "箱装量");
		firstMap.put("unitname", "单位");
		if(null!=colMap && colMap.containsKey("price")){
			firstMap.put("price", "单价");
		}
        firstMap.put("batchno", "批次号");
        firstMap.put("produceddate", "生产日期");
		if(null!=colMap && colMap.containsKey("booknum")){
			firstMap.put("booknum", "账面总数");
			firstMap.put("auxbooknum", "账面箱数");
			firstMap.put("auxbooknumremainder", "账面个数");
			if(null!=colMap && colMap.containsKey("price")){
				firstMap.put("bookamount", "账面金额");
			}
		}
		firstMap.put("realnum", "实际总数");
		firstMap.put("auxrealnum", "实际箱数");
		firstMap.put("auxrealnumremainder", "实际个数");
		if(null!=colMap && colMap.containsKey("price")){
			firstMap.put("realamount", "实际金额");
		}
		if(null!=colMap && colMap.containsKey("profitlossnum")){
			firstMap.put("profitlossnum", "盈亏总数");
			firstMap.put("auxprofitlossnum", "盈亏箱数");
			firstMap.put("auxprofitlossnumremainder", "盈亏个数");
			if(null!=colMap && colMap.containsKey("price")){
				firstMap.put("profitlossamount", "盈亏金额");
			}
		}
		firstMap.put("checkusername", "盘点人");
		result.add(firstMap);
		
		if(list.size() != 0){
			for(Map<String, Object> map : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
					if(map.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
						for(Map.Entry<String, Object> entry : map.entrySet()){
							if(fentry.getKey().equals(entry.getKey())){
								Object object = entry.getValue();
								if(null!=object){
									if (object instanceof String) {
										retMap.put(entry.getKey(), object);
									}else if(object instanceof BigDecimal){
										BigDecimal bignum = (BigDecimal) object;
										retMap.put(entry.getKey(), bignum);
									}else if(object instanceof Timestamp){
										Timestamp timestamp = (Timestamp) object;
										retMap.put(entry.getKey(), timestamp);
									}else if(object instanceof Date){
										Date date = (Timestamp) object;
										retMap.put(entry.getKey(), date);
									}
									else if(object instanceof Integer){
										Integer intnum = (Integer) object;
										retMap.put(entry.getKey(), intnum);
									}
								}else{
									retMap.put(entry.getKey(), "");
								}
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
	 * 根据文件类型导出盘点单数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 13, 2014
	 */
	public void exportCheckListDataOfTheStyle()throws Exception{
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
		List list = checkListService.exportCheckListData(pageMap);
		//判断文件格式filestype: 1.excel文件、2.txt文件
		String filestype = null;
		if(map.containsKey("filestype") && null != map.get("filestype")){
			filestype = (String)map.get("filestype");
		}else{
			filestype = "1";
		}
		if("1".equals(filestype)){
			ExcelUtils.exportExcel(exportDataFilter(list), title);
		}else if("2".equals(filestype)){
			ExcelUtils.exportTXT(list, title);
		}
	}

	/**
	 * 申请动态盘点页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-08-25
	 */
	public String showDynamicCheckListPage()throws Exception{
		return SUCCESS;
	}

	/**
	 * 动态盘点单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-08-25
	 */
    @UserOperateLog(key="CheckList",type=3)
	public String addDynamicCheckList()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
        Map retmap = checkListService.addDynamicCheckList(map);
        String msg = "";
        if(null!=retmap){
            msg = (String) retmap.get("msg");
        }
        addLog("动态盘成点单生成 "+msg, retmap);
        addJSONObject(retmap);
		return SUCCESS;
	}
}

