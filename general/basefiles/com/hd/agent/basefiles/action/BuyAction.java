/**
 * @(#)BuyAreaAction.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-4-17 zhanghonghui 创建版本
 */
package com.hd.agent.basefiles.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.model.AttachFile;
import com.hd.agent.common.service.IAttachFileService;
import com.hd.agent.common.util.*;
import com.hd.agent.journalsheet.service.IJournalSheetService;
import net.sf.json.JSONArray;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.service.IBuyService;
import com.hd.agent.basefiles.service.IContacterService;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.service.IExcelService;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.model.TableColumn;
import com.hd.agent.system.service.ITaskScheduleService;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class BuyAction  extends BaseAction {

	private IBuyService buyService;
	private IExcelService excelService;
	private BuyArea buyArea;

	private BuySupplierSort buySupplierSort;
	
	private BuySupplier buySupplier;
	private BuySupplierDetailsort buySupplierDetailsort;
	private BuySupplierBrandSettletype buySupplierBrandSettletype;
	
	private IContacterService contacterService;
	private ITaskScheduleService taskScheduleService;

    private IAttachFileService attachFileService;

    public IAttachFileService getAttachFileService() {
        return attachFileService;
    }

    public void setAttachFileService(IAttachFileService attachFileService) {
        this.attachFileService = attachFileService;
    }
	
	public IExcelService getExcelService() {
		return excelService;
	}

	public void setExcelService(IExcelService excelService) {
		this.excelService = excelService;
	}

	public ITaskScheduleService getTaskScheduleService() {
		return taskScheduleService;
	}

	public void setTaskScheduleService(ITaskScheduleService taskScheduleService) {
		this.taskScheduleService = taskScheduleService;
	}

	public IBuyService getBuyService() {
		return buyService;
	}

	public void setBuyService(IBuyService buyService) {
		this.buyService = buyService;
	}

	public BuyArea getBuyArea() {
		return buyArea;
	}

	public void setBuyArea(BuyArea buyArea) {
		this.buyArea = buyArea;
	}
	


	public BuySupplierSort getBuySupplierSort() {
		return buySupplierSort;
	}

	public void setBuySupplierSort(BuySupplierSort buySupplierSort) {
		this.buySupplierSort = buySupplierSort;
	}
	
	public BuySupplier getBuySupplier() {
		return buySupplier;
	}

	public void setBuySupplier(BuySupplier buySupplier) {
		this.buySupplier = buySupplier;
	}

	public BuySupplierDetailsort getBuySupplierDetailsort() {
		return buySupplierDetailsort;
	}

	public void setBuySupplierDetailsort(BuySupplierDetailsort buySupplierDetailsort) {
		this.buySupplierDetailsort = buySupplierDetailsort;
	}

	public IContacterService getContacterService() {
		return contacterService;
	}

	public void setContacterService(IContacterService contacterService) {
		this.contacterService = contacterService;
	}

	public BuySupplierBrandSettletype getBuySupplierBrandSettletype() {
		return buySupplierBrandSettletype;
	}

	public void setBuySupplierBrandSettletype(BuySupplierBrandSettletype buySupplierBrandSettletype) {
		this.buySupplierBrandSettletype = buySupplierBrandSettletype;
	}

	/**
	 * 采购区域显示页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 10, 2013
	 */
	public String buyArea() throws Exception {
		List list = getBaseFilesLevelService().showFilesLevelList("t_base_buy_area");
		int len=0;
		if(null != list && list.size() != 0){
			len=list.size();
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<list.size();i++){
				FilesLevel level = (FilesLevel)list.get(i);
				sb.append(level.getLen() + ",");
			}
			if(sb.length() > 0){
				request.setAttribute("lenStr", sb.deleteCharAt(sb.length()-1).toString());
			}
		}
		request.setAttribute("len", len);
		return SUCCESS;
	}
	
	/**
	 * 获取采购区域树状结构数据
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 10, 2013
	 */
	public String getBuyAreaTree() throws Exception{
		String type = request.getParameter("type"); //当type为1时只取启用数据
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		List<BuyArea> list = buyService.getBuyAreaList();
		Map<String, String> first = new HashMap<String, String>();
		first.put("id", "");
		first.put("name", "采购区域");
		first.put("open", "true");
		result.add(first);
		Map<String, String> map=null;
		for(BuyArea area : list){
			if("1".equals(type)){
				if("1".equals(area.getState())){
					map = new HashMap<String, String>();
					map.put("id", area.getId());
					map.put("pid", area.getPid());
					map.put("name", area.getThisname());
					map.put("state", area.getState());
					result.add(map);
				}
			}else{
				map = new HashMap<String, String>();
				map.put("id", area.getId());
				map.put("pid", area.getPid());
				map.put("name", area.getThisname());
				map.put("state", area.getState());
				result.add(map);
			}
		}
		addJSONArray(result);
		return SUCCESS;
	}
	
	/**
	 * 采购区域添加页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public String buyAreaAddPage() throws Exception{
		String id = request.getParameter("id"); //当为空时添加的采购区域为一级目录，否则该编号为添加区域的父级
		BuyArea buyArea = buyService.getBuyAreaDetail(id);
		int len = 0;
		if(buyArea != null && StringUtils.isNotEmpty(buyArea.getId())){
			len = buyArea.getId().length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_buy_area", len);
		request.setAttribute("len", nextLen);
		request.setAttribute("buyArea", buyArea);
		return SUCCESS;
	}
	
	/**
	 * 添加采购区域
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */

	@UserOperateLog(key="BuyArea",type=2,value="")
	public String addBuyArea() throws Exception{
		String addType = request.getParameter("addType");
		if("temp".equals(addType)){
			buyArea.setState("3");
		}
		if("real".equals(addType)){
			buyArea.setState("2");
		}
		SysUser sysUser = getSysUser();
		buyArea.setAdduserid(sysUser.getUserid());
		buyArea.setAdddeptid(sysUser.getDepartmentid());
		boolean flag = buyService.addBuyArea(buyArea);
		addLog("新增采购区域 编号:"+buyArea.getId(),flag);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", flag);
		map.put("backid", buyArea.getId());
		map.put("type", "add");
		Map<String, String> node = new HashMap<String, String>();
		node.put("id", buyArea.getId());
		node.put("pid", buyArea.getPid());
		node.put("name", buyArea.getThisname());
		node.put("state", buyArea.getState());
		map.put("node", node);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 修改采购区域信息页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public String buyAreaEditPage() throws Exception{
		String id = request.getParameter("id");
		BuyArea buyArea = buyService.getBuyAreaDetail(id);
		List stateList = getBaseSysCodeService().showSysCodeListByType("state");
		if(buyArea != null && StringUtils.isNotEmpty(buyArea.getPid())){
			BuyArea area = buyService.getBuyAreaDetail(buyArea.getPid());
			request.setAttribute("parentName", area.getName());
		}
		int len = 0;
		if(buyArea != null && StringUtils.isNotEmpty(buyArea.getId())){
			len = buyArea.getPid().length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_buy_area", len);
		request.setAttribute("len", nextLen);
		request.setAttribute("stateList", stateList);
		request.setAttribute("buyArea", buyArea);
		request.setAttribute("editFlag", canTableDataDelete("t_base_buy_area", id));
		return SUCCESS;
	}
	
	/**
	 * 修改采购区域信息
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="BuyArea",type=3,value="")
	public String updateBuyArea() throws Exception{
		String addType = request.getParameter("addType");
		if("0".equals(buyArea.getState()) || "1".equals(buyArea.getState())){
			
		}
		else{
			if("real".equals(addType)){
				buyArea.setState("2");
			}
		}
		SysUser sysUser = getSysUser();
		buyArea.setModifyuserid(sysUser.getUserid());
		Map map = buyService.updateBuyArea(buyArea);
		addLog("修改采购区域 编号:"+buyArea.getId(),map.get("flag").equals(true));
		map.put("backid", buyArea.getId());
		map.put("oldid", buyArea.getOldid());
		map.put("type", "edit");
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 采购区域复制页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 12, 2013
	 */
	public String buyAreaCopyPage() throws Exception{
		String id = request.getParameter("id");
		BuyArea buyArea = buyService.getBuyAreaDetail(id);
		if(buyArea!=null && StringUtils.isNotEmpty(buyArea.getPid())){
			BuyArea area = buyService.getBuyAreaDetail(buyArea.getPid());
			request.setAttribute("parentName", area.getName());
		}
		int len=0;
		int nextLen = getBaseTreeFilesNext("t_base_buy_area", len);
		request.setAttribute("len", nextLen);
		request.setAttribute("buyArea", buyArea);
		return SUCCESS;
	}
	
	/**
	 * 采购区域查看页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public String buyAreaViewPage() throws Exception{
		String id = request.getParameter("id");
		BuyArea buyArea = buyService.getBuyAreaDetail(id);
		List stateList = getBaseSysCodeService().showSysCodeListByType("state");
		request.setAttribute("stateList", stateList);
		request.setAttribute("buyArea", buyArea);
		return SUCCESS;
	}
	
	/**
	 * 删除采购区域信息
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="BuyArea",type=4,value="")
	public String deleteBuyArea() throws Exception{
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_base_buy_area", id); 
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag = buyService.deleteBuyArea(id);
		addLog("删除采购区域 编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 启用采购区域
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="BuyArea",type=0,value="")
	public String openBuyArea() throws Exception{
		String id = request.getParameter("id");
		SysUser sysUser = getSysUser();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("openuserid", sysUser.getUserid());
		map.put("openusername", sysUser.getName());
		map.put("id", id);
		map.put("state","1");
		boolean flag = buyService.updateBuyAreaBy(map);
		addLog("启用采购区域 编号:" + id, flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 禁用采购区域
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="BuyArea",type=0,value="")
	public String closeBuyArea() throws Exception{		
		String id = request.getParameter("id");
		Map map=buyService.closeBuyArea(id);
		addLog("禁用采购区域 编号:"+id,map.get("flag").equals(true));
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 编号是否存在
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-20
	 */
	public String isBuyAreaIdExist() throws Exception{
		String id=request.getParameter("id");
		boolean flag=false;
		if(null!=id && !"".equals(id.trim())){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("id", id.trim());
			flag=buyService.getBuyAreaCountBy(map)>0;			
		}
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 判断本级名称是否重复，true 不重复，false 重复
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-13
	 */
	public String isRepeatBuyAreaThisname()throws Exception{
		String thisname = request.getParameter("thisname");
		boolean flag = buyService.isRepeatBuyAreaThisname(thisname);
		addJSONObject("flag", flag);
		return "success";
	}
	//--------------------------------------------------------------//
	//	供应商分类	//
	//--------------------------------------------------------------//
	
	public String showBuySupplierDetailSortList() throws Exception{
		String supplierid=request.getParameter("supplierid");
		List<BuySupplierDetailsort> list=new ArrayList<BuySupplierDetailsort>();
		if(null != supplierid && !"".equals(supplierid.trim())){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("supplierid", supplierid.trim());
			list=buyService.showBuySupplierDetailSortListBy(map);
		}
		addJSONArray(list);
		return SUCCESS;
	}
	
	/**
	 * 供应商分类显示页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 10, 2013
	 */
	public String buySupplierSort() throws Exception {
		List list = getBaseFilesLevelService().showFilesLevelList("t_base_buy_supplier_sort");
		int len=0;
		if(list!=null && list.size()>0){
			len=list.size();
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<list.size();i++){
				FilesLevel level = (FilesLevel)list.get(i);
				sb.append(level.getLen() + ",");
			}
			if(sb.length() > 0){
				request.setAttribute("lenStr", sb.deleteCharAt(sb.length()-1).toString());
			}
		}
		request.setAttribute("len", len);		
		return SUCCESS;
	}
	
	/**
	 * 获取供应商分类树状结构数据
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 10, 2013
	 */
	public String getBuySupplierSortTree() throws Exception{
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		List<BuySupplierSort> list = buyService.getBuySupplierSortList();
		Map<String, String> first = new HashMap<String, String>();
		first.put("id", "");
		first.put("name", "供应商分类");
		first.put("open", "true");
		result.add(first);
		for(BuySupplierSort area : list){
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", area.getId());
			map.put("pid", area.getPid());
			map.put("name", area.getThisname());
			map.put("state", area.getState());
			result.add(map);
		}
		addJSONArray(result);
		return SUCCESS;
	}
	
	/**
	 * 供应商分类添加页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public String buySupplierSortAddPage() throws Exception{
		String id = request.getParameter("id"); //当为空时添加的供应商分类为一级目录，否则该编号为添加区域的父级
		BuySupplierSort buySupplierSort = buyService.getBuySupplierSortDetail(id);
		int len = 0;
		if(buySupplierSort != null && StringUtils.isNotEmpty(buySupplierSort.getId())){
			len = buySupplierSort.getId().length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_buy_supplier_sort", len);
		request.setAttribute("len", nextLen);
		request.setAttribute("buySupplierSort", buySupplierSort);
		return SUCCESS;
	}
	
	/**
	 * 添加供应商分类
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="BuySupplierSort",type=2,value="")
	public String addBuySupplierSort() throws Exception{
		String addType = request.getParameter("addType");
		if("temp".equals(addType)){
			buySupplierSort.setState("3");
		}
		if("real".equals(addType)){
			buySupplierSort.setState("2");
		}
		SysUser sysUser = getSysUser();
		buySupplierSort.setAdduserid(sysUser.getUserid());
		buySupplierSort.setAdddeptid(sysUser.getDepartmentid());
		boolean flag = buyService.addBuySupplierSort(buySupplierSort);
		addLog("新增供应商分类 编号:"+buySupplierSort.getId(),flag);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", flag);
		map.put("backid", buySupplierSort.getId());
		map.put("type", "add");
		Map<String, String> node = new HashMap<String, String>();
		node.put("id", buySupplierSort.getId());
		node.put("pid", buySupplierSort.getPid());
		node.put("name", buySupplierSort.getThisname());
		node.put("state", buySupplierSort.getState());
		map.put("node", node);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 修改供应商分类信息页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public String buySupplierSortEditPage() throws Exception{
		String id = request.getParameter("id");
		BuySupplierSort buySupplierSort = buyService.getBuySupplierSortDetail(id);
		List stateList = getBaseSysCodeService().showSysCodeListByType("state");
		if(buySupplierSort != null && StringUtils.isNotEmpty(buySupplierSort.getPid())){
			BuySupplierSort area = buyService.getBuySupplierSortDetail(buySupplierSort.getPid());
			request.setAttribute("parentName", area.getName());
		}
		int len = 0;
		if(buySupplierSort != null && StringUtils.isNotEmpty(buySupplierSort.getPid())){
			len = buySupplierSort.getPid().length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_buy_supplier_sort", len);
		request.setAttribute("len", nextLen);
		request.setAttribute("stateList", stateList);
		request.setAttribute("buySupplierSort", buySupplierSort);
		request.setAttribute("editFlag", canTableDataDelete("t_base_buy_supplier_sort", id));
		return SUCCESS;
	}
	
	/**
	 * 修改供应商分类信息
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="BuySupplierSort",type=3,value="")
	public String updateBuySupplierSort() throws Exception{
		String addType = request.getParameter("addType");
		if("0".equals(buySupplierSort.getState()) || "1".equals(buySupplierSort.getState())){
			
		}
		else{
			if("real".equals(addType)){
				buySupplierSort.setState("2");
			}
		}
		SysUser sysUser = getSysUser();
		buySupplierSort.setModifyuserid(sysUser.getUserid());
		Map map = buyService.updateBuySupplierSort(buySupplierSort);
		addLog("修改供应商分类 编号:"+buySupplierSort.getId(),map.get("flag").equals(true));
		map.put("backid", buySupplierSort.getId());
		map.put("oldid", buySupplierSort.getOldid());
		map.put("type", "edit");
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 供应商分类复制页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 12, 2013
	 */
	public String buySupplierSortCopyPage() throws Exception{
		String id = request.getParameter("id");
		BuySupplierSort buySupplierSort = buyService.getBuySupplierSortDetail(id);
		if(buySupplierSort!=null && StringUtils.isNotEmpty(buySupplierSort.getPid())){
			BuySupplierSort area = buyService.getBuySupplierSortDetail(buySupplierSort.getPid());
			request.setAttribute("parentName", area.getName());
		}
		int len=0;
		int nextLen = getBaseTreeFilesNext("t_base_buy_supplier_sort", len);
		request.setAttribute("len", nextLen);
		request.setAttribute("buySupplierSort", buySupplierSort);
		return SUCCESS;
	}
	
	/**
	 * 供应商分类查看页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public String buySupplierSortViewPage() throws Exception{
		String id = request.getParameter("id");
		BuySupplierSort buySupplierSort = buyService.getBuySupplierSortDetail(id);
		List stateList = getBaseSysCodeService().showSysCodeListByType("state");
		request.setAttribute("stateList", stateList);
		request.setAttribute("buySupplierSort", buySupplierSort);
		return SUCCESS;
	}
	
	/**
	 * 删除供应商分类信息
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="BuySupplierSort",type=4,value="")
	public String deleteBuySupplierSort() throws Exception{
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_base_buy_supplier_sort", id); 
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag = buyService.deleteBuySupplierSort(id);
		addLog("删除供应商分类 编号:" + id, flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 启用供应商分类
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="BuySupplierSort",type=0,value="")
	public String openBuySupplierSort() throws Exception{
		String id = request.getParameter("id");
		SysUser sysUser = getSysUser();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("openuserid", sysUser.getUserid());
		map.put("openusername", sysUser.getName());
		map.put("id", id);
		map.put("state","1");
		boolean flag = buyService.updateBuySupplierSortBy(map);
		addLog("启用供应商分类 编号:" + id, flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 禁用供应商分类
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="BuySupplierSort",type=0,value="")
	public String closeBuySupplierSort() throws Exception{		
		String id = request.getParameter("id");
		Map map=buyService.closeBuySuppplierSort(id);
		addLog("禁用供应商分类 编号:"+id,map.get("flag").equals(true));
		addJSONObject(map);
		return SUCCESS;
	}
	/**
	 * 编号是否存在
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-20
	 */
	public String isBuySupplierSortIdExist() throws Exception{
		String id=request.getParameter("id");
		boolean flag=false;
		if(null!=id && !"".equals(id.trim())){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("id", id.trim());
			flag=buyService.getBuySupplierSortCountBy(map)>0;			
		}
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 判断本级名称是否重复，true 不重复，false 重复
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-13
	 */
	public String isRepeatSupplierSortThisname()throws Exception{
		String thisname = request.getParameter("thisname");
		boolean flag = buyService.isRepeatSuppplierSortThisname(thisname);
		addJSONObject("flag", flag);
		return "success";
	}
	//--------------------------------------------------------------//
	//	供应商档案	//
	//--------------------------------------------------------------//
	
	/**
	 * 供应商档案列表页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-20
	 */
	public String buySupplierListPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 供应商档案
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-20
	 */
	public String showBuySupplierPageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData=buyService.showBuySupplierPageList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	/**
	 * 供应商档案
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-20
	 */
	public String buySupplierPage() throws Exception{
		String tmp=request.getParameter("id");
		request.setAttribute("id", tmp);
		tmp=request.getParameter("sortid");
		request.setAttribute("sortid", tmp);
		tmp=request.getParameter("areaid");
		request.setAttribute("areaid", tmp);
		tmp=request.getParameter("type");
		request.setAttribute("type", tmp);
		return SUCCESS;
	}
	/**
	 * 供应商档案添加页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-22
	 */
	public String buySupplierAddPage() throws Exception{
		Map<String,Object> fieldMap=supplierDIYFieldName();
		List dayList = new ArrayList();
		for(int i=1;i<=31;i++){
			dayList.add(String.valueOf(i));
		}
		List natureList = getBaseSysCodeService().showSysCodeListByType("firm_nature");
		List recoverymodeList = getBaseSysCodeService().showSysCodeListByType("reimbursetype");
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		request.setAttribute("canceltypeList", canceltypeList); 
		request.setAttribute("recoverymodeList", recoverymodeList);
		request.setAttribute("natureList", natureList); //企业性质
		request.setAttribute("fieldmap", fieldMap);
		request.setAttribute("dayList", dayList); 
		return SUCCESS;
	}
	/**
	 * 添加供应商档案
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-22
	 */
	@UserOperateLog(key="BuySupplier",type=2,value="")
	public String addBuySupplier() throws Exception{
		String addType = request.getParameter("addType");
		if("temp".equals(addType)){
			buySupplier.setState("3");
		}
		if("real".equals(addType)){
			buySupplier.setState("2");
		}
		SysUser sysUser = getSysUser();
		buySupplier.setAdduserid(sysUser.getUserid());
		buySupplier.setAdddeptid(sysUser.getDepartmentid());
		buySupplier.setAdddeptname(sysUser.getDepartmentname());
		buySupplier.setAddusername(sysUser.getName());
		buySupplier.setBuySupplierDetailsort(buySupplierDetailsort);
		boolean flag=buyService.addBuySupplier(buySupplier);
		addLog("新增供应商 编号:"+buySupplier.getId(),flag);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", flag);
		map.put("backid", buySupplier.getId());
		addJSONObject(map);
		return SUCCESS;
	}

	
	public void queryNamesCommon(BuySupplier buySupplier) throws Exception{
		if(buySupplier!=null ){
			if(StringUtils.isNotEmpty(buySupplier.getContact())){
				Contacter contact=contacterService.getContacterDetail(buySupplier.getContact());
				if(contact!=null){
					buySupplier.setContactname(contact.getName());
				}
			}
			if(StringUtils.isNotEmpty(buySupplier.getSuppliersort())){
				BuySupplierSort buySupplierSort=buyService.getBuySupplierSortDetail(buySupplier.getSuppliersort());
				if(buySupplierSort!=null){
					buySupplier.setSuppliersortname(buySupplierSort.getName());
				}
			}
		}
	}
	/**
	 * 供应商档案修改页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-22
	 */
	public String buySupplierEditPage() throws Exception{
		String id = request.getParameter("id");
		BuySupplier buySupplier=buyService.showBuySupplier(id);
		//获取t_base_sales_customer表的字段编辑权限
		Map mapCols =  getBaseFilesEditWithAuthority("t_base_buy_supplier", buySupplier);
		//显示修改页面时，给数据加锁,true:可以修改，false:不可以修改 
		boolean flag = lockData("t_base_buy_supplier", id);
		if(flag){
			queryNamesCommon(buySupplier);
			Map<String,Object> fieldMap=supplierDIYFieldName();
			List dayList = new ArrayList();
			for(int i=1;i<=31;i++){
				dayList.add(String.valueOf(i));
			}
			List natureList = getBaseSysCodeService().showSysCodeListByType("firm_nature");
			List recoverymodeList = getBaseSysCodeService().showSysCodeListByType("reimbursetype");
			List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
			request.setAttribute("canceltypeList", canceltypeList); 
			request.setAttribute("fieldmap", fieldMap);
			request.setAttribute("buySupplier", buySupplier);
			request.setAttribute("recoverymodeList", recoverymodeList);
			request.setAttribute("natureList", natureList); //企业性质
			request.setAttribute("dayList", dayList); 
			request.setAttribute("editFlag", canTableDataDelete("t_base_buy_supplier", id));
			return SUCCESS;
		}else{
			request.setAttribute("lock", true);
			request.setAttribute("editMap", null);
			request.setAttribute("id", id);
			return SUCCESS;
		}
	}
	/**
	 * 供应商档案修改
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-22
	 */
	@UserOperateLog(key="BuySupplier",type=3,value="")
	public String editBuySupplier() throws Exception{
		String addType = request.getParameter("addType");
		if("0".equals(buySupplier.getState()) || "1".equals(buySupplier.getState())){
			
		}
		else{
			if("real".equals(addType)){
				buySupplier.setState("2");
			}
		}
		BuySupplier oldBuySupplier = buyService.showBuySupplier(buySupplier.getOldid());
		SysUser sysUser = getSysUser();
		buySupplier.setModifyuserid(sysUser.getUserid());
		buySupplier.setModifyusername(sysUser.getName());
		buySupplier.setBuySupplierDetailsort(buySupplierDetailsort);
		boolean flag = buyService.updateBuySupplier(buySupplier);
		if(flag){
			//判断默认采购部门，采购员是否改变，改变则变动采购管理数据
			if(null != buySupplier && null != oldBuySupplier && buySupplier.getOldid().equals(buySupplier.getId())){
				if((null != buySupplier.getBuyuserid() && !oldBuySupplier.getBuyuserid().equals(buySupplier.getBuyuserid())) ||
					(null != buySupplier.getBuydeptid() && !oldBuySupplier.getBuydeptid().equals(buySupplier.getBuydeptid()))
				){
					addEditBuySupplierTaskSchedule(buySupplier);
				}
			}
		}
		addLog("修改供应商 编号:"+buySupplier.getId(),flag);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", flag);
		map.put("backid", buySupplier.getId());
		map.put("oldid", buySupplier.getOldid());
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 供应商档案查看页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-22
	 */
	public String buySupplierViewPage() throws Exception{
		String id = request.getParameter("id");
		BuySupplier buySupplier=buyService.showBuySupplier(id);
		queryNamesCommon(buySupplier);
		Map<String,Object> fieldMap=supplierDIYFieldName();
		List natureList = getBaseSysCodeService().showSysCodeListByType("firm_nature");
		List recoverymodeList = getBaseSysCodeService().showSysCodeListByType("reimbursetype");
		List dayList = new ArrayList();
		for(int i=1;i<=31;i++){
			dayList.add(String.valueOf(i));
		}
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		List<Contacter> contacterList = contacterService.getContacterListByCustomer("2", id);
		List stateList = getBaseSysCodeService().showSysCodeListByType("state");
		for(Contacter contacter : contacterList){ //联系人状态
			for(int i=0; i< stateList.size(); i++){
				SysCode sysCode = (SysCode)stateList.get(i);
				if(contacter.getState().equals(sysCode.getCode())){
					contacter.setState(sysCode.getCodename());
				}
			}
		}
		request.setAttribute("contacterList", contacterList); //联系人列表
		request.setAttribute("canceltypeList", canceltypeList); 
		request.setAttribute("fieldmap", fieldMap);
		request.setAttribute("buySupplier", buySupplier);
		request.setAttribute("recoverymodeList", recoverymodeList);
		request.setAttribute("natureList", natureList); //企业性质
		request.setAttribute("dayList", dayList); 
		return SUCCESS;
	}
	
	/**
	 * 供应商档案复制页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-22
	 */
	public String buySupplierCopyPage() throws Exception{
		String id = request.getParameter("id");
		BuySupplier buySupplier=buyService.showBuySupplier(id);
		queryNamesCommon(buySupplier);
		Map<String,Object> fieldMap=supplierDIYFieldName();
		List natureList = getBaseSysCodeService().showSysCodeListByType("firm_nature");
		List recoverymodeList = getBaseSysCodeService().showSysCodeListByType("reimbursetype");
		List dayList = new ArrayList();
		for(int i=1;i<=31;i++){
			dayList.add(String.valueOf(i));
		}
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		request.setAttribute("canceltypeList", canceltypeList); 
		request.setAttribute("fieldmap", fieldMap);
		request.setAttribute("buySupplier", buySupplier);
		request.setAttribute("recoverymodeList", recoverymodeList);
		request.setAttribute("natureList", natureList); //企业性质
		request.setAttribute("dayList", dayList); 
		return SUCCESS;
	}
	/**
	 * 启用供应商档案
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-26
	 */
	@UserOperateLog(key="BuySupplier",type=0,value="")
	public String openBuySupplier() throws Exception{
		String id=request.getParameter("id");
		boolean flag=false;
		Map<String,Object> msgMap=new HashMap<String,Object>();
		if(null==id || "".equals(id.trim())){
			msgMap.put("flag", flag);
			msgMap.put("msg", "未能找到供应商信息");
			addJSONObject(msgMap);
			return "success";			
		}
		id=id.trim();
		SysUser sysUser = getSysUser();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("oldid",id);	//条件参数
		map.put("state", "1");
		map.put("openuserid", sysUser.getUserid());
		map.put("openusername", sysUser.getName());
		map.put("opentime", new Date());
		map.put("statearrs", "0,2");	//禁用或保存状态
		flag=buyService.updateBuySupplierBy(map);
		addLog("启用供应商 编号:" + id, flag);
		map.put("flag", flag);
		map.put("backid", id);
		addJSONObject(map);
		return SUCCESS;
	}
	/**
	 * 批量启用供应商档案
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-26
	 */
	@UserOperateLog(key="BuySupplier",type=0,value="")
	public String openMultiBuySupplier() throws Exception{
		boolean flag=false;
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		String retids = null;
		Map<String,Object> msgMap=new HashMap<String,Object>();
		String ids=request.getParameter("ids");
		if(null==ids || "".equals(ids.trim())){
			msgMap.put("flag", flag);
			msgMap.put("msg", "未能找到要启用的供应商信息");
			addJSONObject(msgMap);
			return "success";
		}
		String[] idarr=ids.trim().split(",");
		if(idarr.length==0){
			msgMap.put("flag", flag);
			msgMap.put("msg", "未能找到要启用的供应商信息");
			addJSONObject(msgMap);
			return "success";			
		}
		Map<String,Object> map=new HashMap<String,Object>();

		SysUser sysUser = getSysUser();
		map.put("state", "1");
		map.put("openuserid", sysUser.getUserid());
		map.put("openusername", sysUser.getName());
		map.put("opentime", new Date());
		map.put("statearrs", "0,2"); //禁用或保存状态下
		for(String item : idarr){
			if(null != item && !"".equals(item.trim())){
				map.put("oldid", item.trim());
				flag=buyService.updateBuySupplierBy(map) || flag;
				if(flag){
					iSuccess=iSuccess+1;
					if(StringUtils.isNotEmpty(retids)){
						retids += "," + item;
					}
					else{
						retids = item;
					}
				}else{
					iFailure=iFailure+1;
				}
			}else{
				iNohandle=iNohandle+1;
			}
		}
		map.clear();
		
		map.put("flag", flag);
		map.put("isuccess", iSuccess);
		map.put("ifailure", iFailure);
		map.put("inohandle", iNohandle);
		if(iSuccess > 0){
			addLog("启用供应商 编号:"+retids,true);
		}
		addJSONObject(map);
		return SUCCESS;
	}
	/**
	 * 禁用供应商档案
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-26
	 */
	@UserOperateLog(key="BuySupplier",type=0,value="")
	public String closeBuySupplier() throws Exception{
		String id=request.getParameter("id");
		boolean flag=false;
		Map<String,Object> msgMap=new HashMap<String,Object>();
		if(null==id || "".equals(id.trim())){
			msgMap.put("flag", flag);
			msgMap.put("msg", "未能找到要禁用的供应商信息");
			addJSONObject(msgMap);
			return "success";			
		}
		id=id.trim();
		SysUser sysUser = getSysUser();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("oldid",id);
		map.put("closeuserid", sysUser.getUserid());
		map.put("closeusername", sysUser.getName());
		map.put("closetime", new Date());
		map.put("state", "0");
		flag=buyService.updateBuySupplierBy(map);
		addLog("禁用供应商 编号:"+id,flag);
		map.clear();
		map.put("flag", flag);
		map.put("backid", id);
		addJSONObject(map);
		return SUCCESS;
	}
	/**
	 * 批量禁用供应商档案
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-26
	 */
	@UserOperateLog(key="BuySupplier",type=0,value="")
	public String closeMultiBuySupplier() throws Exception{
		boolean flag=false;
		int iSuccess=0,iFailure=0,iNohandle=0;
		String retids = null;
		Map<String,Object> msgMap=new HashMap<String,Object>();
		String ids=request.getParameter("ids");
		if("".equals(ids)){
			msgMap.put("flag", flag);
			msgMap.put("msg", "未能找到要启用的供应商信息");
			addJSONObject(msgMap);
			return "success";
		}
		String[] idarr=ids.split(",");
		if(idarr.length==0){
			msgMap.put("flag", flag);
			msgMap.put("msg", "未能找到要启用的供应商信息");
			addJSONObject(msgMap);
			return "success";			
		}
		Map<String,Object> map=new HashMap<String,Object>();

		SysUser sysUser = getSysUser();
		map.put("state", "0");
		map.put("openuserid", sysUser.getUserid());
		map.put("openusername", sysUser.getName());
		map.put("opentime", new Date());
		map.put("statearrs","1"); //禁用或保存状态下
		for(String item : idarr){
			if(null != item && !"".equals(item.trim())){
				map.put("oldid", item.trim());
				flag=buyService.updateBuySupplierBy(map) || flag;
				if(flag){
					iSuccess=iSuccess+1;
					if(StringUtils.isNotEmpty(retids)){
						retids += "," + item;
					}
					else{
						retids = item;
					}
				}else{
					iFailure=iFailure+1;
				}
			}else{
				iNohandle=iNohandle+1;
			}
		}

		map.clear();
		map.put("flag", flag);
		map.put("isuccess", iSuccess);
		map.put("ifailure", iFailure);
		map.put("inohandle", iNohandle);
		if(iSuccess > 0){
			addLog("禁用供应商 编号:"+retids,true);
		}
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 删除供应商档案信息，删除时判断该信息是否被引用，引用则无法删除。
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="BuySupplier",type=4,value="")
	public String deleteBuySupplier() throws Exception{
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_base_buy_supplier", id); 
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag = buyService.deleteBuySupplier(id);
		addLog("删除供应商 编号:" + id, flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 批量删除供应商档案信息，删除时判断该信息是否被引用，引用则无法删除。
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 20, 2013
	 */
	@UserOperateLog(key="BuySupplier",type=4,value="")
	public String deleteMultiBuySupplier() throws Exception{
		String ids = request.getParameter("id");
		if(ids.endsWith(",")){
			ids = ids.substring(0, ids.length() - 1);
		}
		String[] idArr = ids.split(",");
		int successNum = 0; //删除成功数
		int failureNum = 0; //删除失败数
		int notAllowNum = 0; //不允许删除数
		String retids = null;
		boolean flag=false;
		for(int i = 0; i<idArr.length; i++){
			String id = idArr[i];
			if(canTableDataDelete("t_base_buy_supplier", id)){ //如果未被引就可以删除
				flag=buyService.deleteBuySupplier(id) || flag;
				if(flag){
					successNum++;
					if(StringUtils.isNotEmpty(retids)){
						retids += "," + idArr[i];
					}else{
						retids = idArr[i];
					}
				}
				else{
					failureNum++;
				}
			}
			else{
				notAllowNum++;
			}
		}
		if(successNum > 0){
			addLog("删除供应商 编号:"+retids,true);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", flag);
		map.put("isuccess", successNum);
		map.put("ifailure", failureNum);
		map.put("inohandle", notAllowNum);
		addJSONObject(map);
		return SUCCESS;
	}
	/**
	 * 编号是否存在
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-20
	 */
	public String isBuySupplierIdExist() throws Exception{
		String id=request.getParameter("id");
		boolean flag=false;
		if(null!=id && !"".equals(id.trim())){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("id", id.trim());
			flag=buyService.getBuySupplierCountBy(map)>0;			
		}
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 名称是否存在
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 7, 2013
	 */
	public String isBuySupplierNameExist()throws Exception{
		String name = request.getParameter("name");
		boolean flag = false;
		if(StringUtils.isNotEmpty(name)){
			Map map = new HashMap();
			map.put("name", name);
			flag=buyService.getBuySupplierCountBy(map)>0;	
		}
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 获取自定义字段名称
	 * @return map
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 16, 2013
	 */
	public Map<String, Object> supplierDIYFieldName() throws Exception{
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("tablename", "t_base_buy_supplier");
		List<TableColumn> list = getBaseDataDictionaryService().getTableColumnListBy(queryMap);
		Map<String,Object> map = new HashMap<String,Object>();
		if(list!=null && list.size()>0){
			for(TableColumn column : list){
				if(column.getColumnname().indexOf("field")>-1){
					map.put(column.getColumnname(), column.getColchnname());
				}
			}
		}
		return map;
	}
	/**
	 * 显示供应商选择页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 3, 2013
	 */
	public String showSuppplierSelectPage() throws Exception{
		String id = request.getParameter("id");
		String divid = request.getParameter("divid");
		String paramRule = request.getParameter("paramRule");
		request.setAttribute("paramRule", paramRule);
		request.setAttribute("divid", divid);
		request.setAttribute("id", id);
		return "success";
	}
	/**
	 * 根据id值获取供应商列表
	 * id查询供应商编码 供应商助记码
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 3, 2013
	 */
	public String getSupplierSelectListData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = buyService.getSupplierSelectListData(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	
	/*--------------------------供应商档案快捷操作----------------------------------------*/
	
	/**
	 * 显示供应商快捷列表页面
	 */
	public String showBuySupplierShortcutPage()throws Exception{
		Map colMap = getRequiredColumn("t_base_buy_supplier");
		request.setAttribute("colMap", colMap);
		return SUCCESS;
	}
	
	/**
	 * 显示供应商简化版页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 13, 2013
	 */
	public String showBuySupplierShortcutMainPage()throws Exception{
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		request.setAttribute("type", type);
		request.setAttribute("id", id);
		return SUCCESS;
	}
	
	/**
	 * 显示供应商快捷新增页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 7, 2013
	 */
	public String showBuySupplierShortcutAddPage()throws Exception{
		List recoverymodeList = getBaseSysCodeService().showSysCodeListByType("reimbursetype");
		List stateList = getBaseSysCodeService().showSysCodeListByType("state");
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		List dayList = new ArrayList();
		for(int i=1;i<=31;i++){
			dayList.add(String.valueOf(i));
		}
		request.setAttribute("dayList", dayList);
		request.setAttribute("canceltypeList", canceltypeList); 
		request.setAttribute("stateList", stateList);
		request.setAttribute("recoverymodeList", recoverymodeList);
		Map colMap = getRequiredColumn("t_base_buy_supplier");
		request.setAttribute("colMap", colMap);
		return SUCCESS;
	}
	
	/**
	 * 显示供应商快捷修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 7, 2013
	 */
	public String showBuySupplierShortcutEditPage()throws Exception{
		String id = request.getParameter("id");
		BuySupplier buySupplier=buyService.showBuySupplier(id);
		//获取t_base_sales_customer表的字段编辑权限
		Map mapCols =  getBaseFilesEditWithAuthority("t_base_buy_supplier", buySupplier);
		//显示修改页面时，给数据加锁,true:可以修改，false:不可以修改 
		boolean flag = lockData("t_base_buy_supplier", id);
		if(flag){
			List recoverymodeList = getBaseSysCodeService().showSysCodeListByType("reimbursetype");
			List stateList = getBaseSysCodeService().showSysCodeListByType("state");
			List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
			List dayList = new ArrayList();
			for(int i=1;i<=31;i++){
				dayList.add(String.valueOf(i));
			}
			//结算方式
			if(StringUtils.isNotEmpty(buySupplier.getSettletype())){
				Settlement settlement = getBaseFinanceService().getSettlemetDetail(buySupplier.getSettletype());
				if(null != settlement){
					request.setAttribute("monthorday", settlement.getType());
				}
			}
			request.setAttribute("dayList", dayList);
			request.setAttribute("canceltypeList", canceltypeList); 
			request.setAttribute("stateList", stateList);
			request.setAttribute("recoverymodeList", recoverymodeList);
			request.setAttribute("buySupplier", buySupplier);
			request.setAttribute("editFlag", canTableDataDelete("t_base_buy_supplier", id));
			Map colMap = getRequiredColumn("t_base_buy_supplier");
			request.setAttribute("colMap", colMap);
			return SUCCESS;
		}else{
			request.setAttribute("lock", true);
			request.setAttribute("editMap", null);
			request.setAttribute("id", id);
			return SUCCESS;
		}
	}
	
	/**
	 * 显示供应商快捷复制页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 7, 2013
	 */
	public String showBuySupplierShortcutCopyPage()throws Exception{
		String id = request.getParameter("id");
		BuySupplier buySupplier=buyService.showBuySupplier(id);
		List recoverymodeList = getBaseSysCodeService().showSysCodeListByType("reimbursetype");
		List stateList = getBaseSysCodeService().showSysCodeListByType("state");
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		request.setAttribute("canceltypeList", canceltypeList); 
		request.setAttribute("stateList", stateList);
		request.setAttribute("recoverymodeList", recoverymodeList);
		request.setAttribute("buySupplier", buySupplier);
		Map colMap = getRequiredColumn("t_base_buy_supplier");
		request.setAttribute("colMap", colMap);
		return SUCCESS;
	}
	
	/**
	 * 显示供应商快捷查看页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 7, 2013
	 */
	public String showBuySupplierShortcutViewPage()throws Exception{
		String id = request.getParameter("id");
		BuySupplier buySupplier=buyService.showBuySupplier(id);
		List recoverymodeList = getBaseSysCodeService().showSysCodeListByType("reimbursetype");
		List stateList = getBaseSysCodeService().showSysCodeListByType("state");
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		List dayList = new ArrayList();
		for(int i=1;i<=31;i++){
			dayList.add(String.valueOf(i));
		}
		request.setAttribute("dayList", dayList);
		request.setAttribute("canceltypeList", canceltypeList); 
		request.setAttribute("stateList", stateList);
		request.setAttribute("recoverymodeList", recoverymodeList);
		request.setAttribute("buySupplier", buySupplier);
		Map colMap = getRequiredColumn("t_base_buy_supplier");
		request.setAttribute("colMap", colMap);
		return SUCCESS;
	}
	
	/**
	 * 快捷新增供应商
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 7, 2013
	 */
	@UserOperateLog(key="BuySupplier",type=2,value="")
	public String addBuySupplierShortcut()throws Exception{
		SysUser sysUser = getSysUser();
		buySupplier.setAdddeptid(sysUser.getDepartmentid());
		buySupplier.setAdddeptname(sysUser.getDepartmentname());
		buySupplier.setAdduserid(sysUser.getUserid());
		buySupplier.setAddusername(sysUser.getName());
		buySupplier.setState("2");
		boolean flag = buyService.addBuySupplier(buySupplier);
		addLog("新增供应商 编号:"+buySupplier.getId(),flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 快捷修改供应商
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 7, 2013
	 */
	@UserOperateLog(key="BuySupplier",type=3,value="")
	public String editBuySupplierShortcut()throws Exception{
		SysUser sysUser = getSysUser();
		buySupplier.setModifyuserid(sysUser.getUserid());
		buySupplier.setModifyusername(sysUser.getName());
		if(!"0".equals(buySupplier.getState()) && !"1".equals(buySupplier.getState())){
			buySupplier.setState("2");
		}
		BuySupplier oldBuySupplier = buyService.showBuySupplier(buySupplier.getOldid());
		boolean flag = buyService.updateBuySupplier(buySupplier);
		if(flag){
			//判断默认采购部门，采购员是否改变，改变则变动采购管理数据
			if(null != buySupplier && null != oldBuySupplier && buySupplier.getOldid().equals(buySupplier.getId())){
				if((null != buySupplier.getBuyuserid() && !oldBuySupplier.getBuyuserid().equals(buySupplier.getBuyuserid())) ||
					(null != buySupplier.getBuydeptid() && !oldBuySupplier.getBuydeptid().equals(buySupplier.getBuydeptid()))
				){
					addEditBuySupplierTaskSchedule(buySupplier);

				}
			}
			//修改应收日期
			if( (null != buySupplier.getSettleday() && !buySupplier.getSettleday().equals(oldBuySupplier.getSettleday())) ||
					(null != buySupplier.getSettletype() && !buySupplier.getSettletype().equals(oldBuySupplier.getSettletype()))
					){
				IJournalSheetService journalSheetService = (IJournalSheetService) SpringContextUtils.getBean("journalSheetService");
				journalSheetService.updateMatcostsNoWriteoffDuefromdateBySupplier(buySupplier.getId());
			}
		}
		addLog("修改供应商 编号:"+buySupplier.getId(),flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 添加供应商修改定时器
	 * @param buySupplier
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 13, 2014
	 */
	public void addEditBuySupplierTaskSchedule(BuySupplier buySupplier)throws Exception{
		String taskid = CommonUtils.getDataNumberSendsWithRand();
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(Calendar.MINUTE, 1);
		nowTime.add(Calendar.SECOND, 1);
		Date afterDate = (Date) nowTime.getTime();
		
		//把执行时间转成quartz表达式 适合单次执行
		String con = CommonUtils.getQuartzCronExpression(afterDate);
		
		Map dataMap = new HashMap();
		dataMap.put("supplier", buySupplier);
		taskScheduleService.addTaskScheduleAndStart(taskid, "供应商档案修改后更新相关单据", "com.hd.agent.basefiles.job.BuySupplierChangeJob", "supplier", con, "1", dataMap);
	}
	
	/**
	 * 导入供应商
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 7, 2015
	 */
	@UserOperateLog(key="BuySupplier",type=2,value="供应商档案导入")
	public String importSupplierSimplifyListData()throws Exception{
		Map<String,Object> retMap = new HashMap<String,Object>();
		try {
			String clazz = "buyService",meth = "addShortcutSupplierExcel",module = "basefiles",pojo = "BuySupplier";
			Object object2 = SpringContextUtils.getBean(clazz);
			Class entity = Class.forName("com.hd.agent." + module + ".model." +pojo);
			Method[] methods = object2.getClass().getMethods();
			Method method = null;
			for(Method m : methods){
				if(m.getName().equals(meth)){
					method = m;
				}
			}
			
			List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
			List<String> paramList2 = new ArrayList<String>();
			for(String str : paramList){
				if("供应商编码".equals(str)){
					paramList2.add("id");
				}
				else if("供应商名称".equals(str)){
					paramList2.add("name");
				}
				else if("助记符".equals(str)){
					paramList2.add("spell");
				}
				else if("简称".equals(str)){
					paramList2.add("shortname");
				}
				else if("税号".equals(str)){
					paramList2.add("taxno");
				}
				else if("开户银行".equals(str)){
					paramList2.add("bank");
				}
				else if("开户账号".equals(str)){
					paramList2.add("cardno");
				}
				else if("电话".equals(str)){
					paramList2.add("telphone");
				}
				else if("传真".equals(str)){
					paramList2.add("faxno");
				}
				else if("详细地址".equals(str)){
					paramList2.add("address");
				}
				else if("所属部门".equals(str)){
					paramList2.add("filialename");
				}
				else if("采购部门".equals(str)){
					paramList2.add("buydeptname");
				}
				else if("采购员".equals(str)){
					paramList2.add("buyusername");
				}
				else if("所属区域".equals(str)){
					paramList2.add("buyareaname");
				}
				else if("所属分类".equals(str)){
					paramList2.add("suppliersortname");
				}
				else if("默认仓库".equals(str)){
					paramList2.add("storagename");
				}
				else if("订单追加".equals(str)){
					paramList2.add("orderappendname");
				}
				else if("状态".equals(str)){
					paramList2.add("statename");
				}
				else if("备注".equals(str)){
					paramList2.add("remark");
				}
				else if("业务联系人".equals(str)){
					paramList2.add("contactname");			
				}
				else if("业务联系人电话".equals(str)){
					paramList2.add("contactmobile");
				}
				else if("业务联系人邮箱".equals(str)){
					paramList2.add("contactemail");
				}
				else if("月销售指标".equals(str)){
					paramList2.add("salesmonth");
				}
				else if("资金占用额度".equals(str)){
					paramList2.add("ownlimit");
				}
				else if("年度目标".equals(str)){
					paramList2.add("annualobjectives");
				}
				else if("年度返利%".equals(str)){
					paramList2.add("annualrebate");
				}
				else if("半年度返利%".equals(str)){
					paramList2.add("semiannualrebate");
				}
				else if("季度返利%".equals(str)){
					paramList2.add("quarterlyrebate");
				}
				else if("月度返利%".equals(str)){
					paramList2.add("monthlyrebate");
				}
				else if("破损补贴".equals(str)){
					paramList2.add("breakagesubsidies");
				}
				else if("其他费用补贴".equals(str)){
					paramList2.add("othersubsidies");
				}
				else if("供价折扣率%".equals(str)){
					paramList2.add("pricediscount");
				}
				else if("法人代表".equals(str)){
					paramList2.add("person");
				}
				else if("法人代表电话".equals(str)){
					paramList2.add("personmobile");
				}
				else if("注册资金".equals(str)){
					paramList2.add("fund");
				}else if("结算方式".equals(str)){
					paramList2.add("settletypename");
				}
				else if("每月结算日".equals(str)){
					paramList2.add("settleday");
				}else{
					paramList2.add("null");
				}
			}
			
			if(paramList.size() == paramList2.size()){
				List result = new ArrayList();
				List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
                List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
                if(list.size() != 0){
					Map detialMap = new HashMap();
					for(Map<String, Object> map4 : list){
						Object object = entity.newInstance();
						Field[] fields = entity.getDeclaredFields();
                        try{
                            //获取的导入数据格式转换
                            DRCastTo(map4,fields);
                            //BeanUtils.populate(object, map4);
                            PropertyUtils.copyProperties(object, map4);
                            result.add(object);
                        }catch (Exception e){
                            errorList.add(map4);
                        }
					}
					if(result.size() != 0){
						retMap = excelService.insertSalesOrder(object2, result, method);
					}
                    if(errorList.size() > 0){
                        String fileid=createErrorFile(list);
                        retMap.put("msg","导入失败"+errorList.size()+"条");
                        retMap.put("errorid",fileid);
                   }
				}else{
					retMap.put("excelempty", true);
				}
			}
			else{
				retMap.put("versionerror", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("error", true);
		}
		addJSONObject(retMap);
		return SUCCESS;
	}

    public String createErrorFile(List<Map<String, Object>> errorList) throws  Exception{

        //模板文件路径
        String tempFilePath = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/Supplier.xls");

        List<String> dataListCell = new ArrayList<String>();

        dataListCell.add("id");
        dataListCell.add("name");
        dataListCell.add("spell");
        dataListCell.add("shortname");
        dataListCell.add("taxno");
        dataListCell.add("bank");
        dataListCell.add("cardno");
        dataListCell.add("telphone");
        dataListCell.add("faxno");
        dataListCell.add("address");
        dataListCell.add("filialename");
        dataListCell.add("buydeptname");
        dataListCell.add("buyusername");
        dataListCell.add("buyareaname");
        dataListCell.add("suppliersortname");
        dataListCell.add("suppliersortname");
        dataListCell.add("orderappendname");
        dataListCell.add("statename");
        dataListCell.add("remark");
        dataListCell.add("contactname");
        dataListCell.add("contactmobile");
        dataListCell.add("contactemail");
        dataListCell.add("salesmonth");
        dataListCell.add("ownlimit");
        dataListCell.add("annualobjectives");
        dataListCell.add("annualrebate");
        dataListCell.add("semiannualrebate");
        dataListCell.add("quarterlyrebate");
        dataListCell.add("monthlyrebate");
        dataListCell.add("breakagesubsidies");
        dataListCell.add("othersubsidies");
        dataListCell.add("pricediscount");
        dataListCell.add("person");
        dataListCell.add("personmobile");
        dataListCell.add("fund");


		ExcelFileUtils handle = new ExcelFileUtils();
        handle.writeListData(tempFilePath, dataListCell, errorList, 0);

        SysUser sysUser=getSysUser();
		String dateSubPath=CommonUtils.getYearMonthDayDirPath();
		String phyDirPath=CommonUtils.getUploadFilePhysicalDir("errorimportfile", dateSubPath);

        String fileName = CommonUtils.getDateTimeUUID();
        fileName= fileName+ ".xls";

        File errorFile = new File(phyDirPath, fileName);

        if(!errorFile.exists()){
            errorFile.createNewFile();
        }
        OutputStream os = new FileOutputStream(errorFile);

        //写到输出流并关闭资源
        handle.writeAndClose(tempFilePath, os);

        os.flush();
        os.close();

        handle.readClose(tempFilePath);


        String fullPath = "upload/errorimportfile/"+ dateSubPath + "/" + fileName;
		fullPath=CommonUtils.filterFilePathSaparator(fullPath);
        AttachFile attachFile = new AttachFile();
        attachFile.setExt(".xls");
        attachFile.setFilename(fileName);
        attachFile.setFullpath(fullPath);
        attachFile.setOldfilename(fileName);
        //将临时文件信息插入数据库
        attachFileService.addAttachFile(attachFile);

        String id = "";

        if(null!=attachFile){
            id=attachFile.getId();
        }
        return id;
    }

	/**
	 * 导出供应商精简版列表数据
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 7, 2015
	 */
	public void exportSupplierSimplifyListData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
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
		firstMap.put("id", "供应商编码");
		firstMap.put("name", "供应商名称");
		firstMap.put("spell", "助记符");
		firstMap.put("shortname", "简称");
		firstMap.put("taxno", "税号");
		firstMap.put("bank", "开户银行");
		firstMap.put("cardno", "开户账号");
		firstMap.put("telphone", "电话");
		firstMap.put("faxno", "传真");
		firstMap.put("person", "法人代表");
		firstMap.put("personmobile", "法人代表电话");
		firstMap.put("fund", "注册资金");
		firstMap.put("address", "详细地址");
		firstMap.put("buydeptname", "采购部门");
		firstMap.put("buyusername", "采购员");
		firstMap.put("buyareaname", "所属区域");
		firstMap.put("suppliersortname", "所属分类");
		firstMap.put("storagename", "默认仓库");
		firstMap.put("orderappendname", "订单追加");
		firstMap.put("statename", "状态");
		firstMap.put("remark", "备注");
		firstMap.put("contactname", "业务联系人");
		firstMap.put("contactmobile", "业务联系人电话");
		firstMap.put("contactemail", "业务联系人邮箱");
		firstMap.put("salesmonth", "月销售指标");
		firstMap.put("ownlimit", "资金占用额度");
		firstMap.put("annualobjectives", "年度目标");
		firstMap.put("annualrebate", "年度返利%");
		firstMap.put("semiannualrebate", "半年度返利%");
		firstMap.put("quarterlyrebate", "季度返利%");
		firstMap.put("monthlyrebate", "月度返利%");
		firstMap.put("breakagesubsidies", "破损补贴");
		firstMap.put("othersubsidies", "其他费用补贴");
		firstMap.put("pricediscount", "供价折扣率%");
		result.add(firstMap);
		
		List<BuySupplier> list = excelService.getSupplierList(pageMap);
		for(BuySupplier buySupplier : list){
			Map<String, Object> retMap = new LinkedHashMap<String, Object>();
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2 = PropertyUtils.describe(buySupplier);
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

	/**
	 * 供应商品牌结算方式
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Oct 25, 2017
	 */
	public String showSupplierBrandSettletypeListPage()throws Exception {
		List dayList = new ArrayList();
		for(int i=1;i<=31;i++){
			Map map = null;
			map = new HashMap();
			map.put("value",String.valueOf(i));
			map.put("text",String.valueOf(i));
			dayList.add(map);
		}
		String daylistjsonstr = JSONUtils.listToJsonStr(dayList);
		request.setAttribute("daylistjsonstr",daylistjsonstr);
		return SUCCESS;
	}

	/**
	 *获取供应商列表
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Oct 25, 2017
	 */
	public String getSupplierListForPact() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = buyService.getSupplierListForPact(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}

	/**
	 * 供应商品牌结算方式新增
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Oct 25, 2017
	 */
	public String showSupplierBrandSettletypeAddPage() throws Exception {
		return "success";
	}

	/**
	 * 新增供应商品牌结算方式
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Oct 25, 2017
	 */
	@UserOperateLog(key="BuySupplierBrandSettletype",type=2,value="")
	public String addSupplierBrandSettletype()throws Exception {
		boolean flag  = buyService.addSupplierBrandSettletype(buySupplierBrandSettletype);
		addJSONObject("flag", flag);
		addLog("添加供应商品牌结算方式 " + buySupplierBrandSettletype.getSupplierid(), flag);
		return SUCCESS;
	}

	/**
	 * 获取供应商的品牌结算方式
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Oct 25, 2017
	 */
	public String showSupplierBrandSettletypeList() {
		String supplierid = request.getParameter("supplierid");
		List list = buyService.getSupplierBrandSettletypeList(supplierid);
		addJSONArray(list);

		return SUCCESS;
	}

	/**
	 * 删除品牌结算方式
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-30
	 */
	@UserOperateLog(key="BuySupplierBrandSettletype",type=4,value="")
	public String deleteSupplierBrandSettletypes()throws Exception{
		String idstr = request.getParameter("idstr");
		String supplierid = request.getParameter("supplierid");
		Map map = buyService.deleteSupplierBrandSettletypes(idstr);
//		Map map = buyService.deleteCustomerBrandSettletypes(idstr);
		Integer sucnum = (Integer)map.get("sucnum");
		String sucbrands = (String)map.get("sucbrands");
		Integer unsucnum = (Integer)map.get("unsucnum");
		String unsucbrands = (String)map.get("unsucbrands");
		String msg = "";
		if(sucnum > 0){
			msg = "成功"+sucnum+"条记录;<br>删除品牌结算方式 供应商编号:"+supplierid+"品牌编码:"+sucbrands+"成功";
			addLog("删除品牌结算方式 客户编号:"+supplierid+"品牌编码:"+sucbrands,true);
		}
		if(unsucnum > 0){
			msg += "失败"+unsucnum+"条记录;<br>删除品牌结算方式 供应商编号:"+supplierid+"品牌编码:"+unsucbrands+"失败";
			addLog("删除品牌结算方式 供应商编号:"+supplierid+"品牌编码:"+unsucbrands,false);
		}
		addJSONObject("msg", msg);
		return SUCCESS;
	}

	/**
	 * 修改客户品牌结算方式
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-30
	 */
	@UserOperateLog(key="BuySupplierBrandSettletype",type=3,value="")
	public String editSupplierBrandSettletype()throws Exception{
		String rowsjsonstr = request.getParameter("rowsjsonstr");
		String sucbrands = "",unsucbrands = "",supplierid = "";
		int sucnum = 0,unsucnum = 0;
		if(StringUtils.isNotEmpty(rowsjsonstr)){
			JSONArray array = JSONArray.fromObject(rowsjsonstr);
			List<BuySupplierBrandSettletype> list = array.toList(array, BuySupplierBrandSettletype.class);
			supplierid = list.get(0).getSupplierid();
			for(BuySupplierBrandSettletype buySupplierBrandSettletype : list){
				boolean flag = buyService.editSupplierBrandSettletype(buySupplierBrandSettletype);
				if(flag){
					sucnum++;
					if(StringUtils.isNotEmpty(sucbrands)){
						sucbrands += "," + buySupplierBrandSettletype.getBrandid();
					}else{
						sucbrands = buySupplierBrandSettletype.getBrandid();
					}
				}else{
					unsucnum++;
					if(StringUtils.isNotEmpty(unsucbrands)){
						unsucbrands += "," + buySupplierBrandSettletype.getBrandid();
					}else{
						unsucbrands = buySupplierBrandSettletype.getBrandid();
					}
				}
			}
			if(StringUtils.isNotEmpty(sucbrands)){
				addLog("修改供应商品牌结算方式 供应商编号:"+supplierid+"品牌编码:"+sucbrands,true);
			}else{
				addLog("修改供应商品牌结算方式 供应商编号:"+supplierid+"品牌编码:"+unsucbrands,false);
			}
		}

		String msg = "修改品牌结算方式 供应商编号："+supplierid+"<br>成功"+sucnum+"条记录;<br>失败"+unsucnum+"条记录;";
		addJSONObject("msg", msg);
		return SUCCESS;
	}

//	/**
//	 * 导入供应商品牌结算方式
//	 * @param
//	 * @return java.lang.String
//	 * @throws
//	 * @author luoqiang
//	 * @date Nov 06, 2017
//	 */
//	@UserOperateLog(key="BuySupplierBrandSettletype",type=2,value="供应商品牌结算方式导入")
//	public String importSupplierBrandSettletype()throws Exception {
//		Map map = new HashMap();
//		List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
//		if(null != paramList){
//			List<String> paramList2 = new ArrayList<String>();
//			for(String str : paramList){
//				if("供应商编码".equals(str)){
//					paramList2.add("supplierid");
//				}
//				else if("供应商名称".equals(str)){
//					paramList2.add("suppliername");
//				}
//				else if("品牌编码".equals(str)){
//					paramList2.add("brandid");
//				}
//				else if("品牌名称".equals(str)){
//					paramList2.add("brandname");
//				}
//				else if("结算方式".equals(str)){
//					paramList2.add("settletypename");
//				}
//				else if("每月结算日".equals(str)){
//					paramList2.add("settleday");
//				}else{
//					paramList2.add("null");
//				}
//			}
//			String ids = "";
//			boolean flag = false;
//			List list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
//			if(list.size() != 0){
//				map = buyService.importSupplierBrandSettletype(list);
//				if(map.containsKey("sucstr")){
//					ids = (String) map.get("sucstr");
//				}
//				if(StringUtils.isNotEmpty(ids)){
//					flag = true;
//				}
//				addLog("供应商品牌结算方式导入:"+ids, flag);
//			}else{
//				map.put("excelempty", true);
//			}
//		}else{
//			map.put("msg", "文件损坏!");
//		}
//
//		addJSONObject(map);
//		return SUCCESS;
//	}

	/**
	 * 导入供应商品牌结算方式
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Nov 06, 2017
	 */
	@UserOperateLog(key="BuySupplierBrandSettletype",type=2,value="供应商品牌结算方式导入")
	public String importSupplierBrandSettletype()throws Exception {
		Map<String,Object> retMap = new HashMap<String,Object>();
		try{
			//获取第一行数据为字段的描述列表
			List<String> firstLine = ExcelUtils.importExcelFirstRow(excelFile);
			List<String> titleList = new ArrayList<String>();

			Map titleMap = new HashMap();
			titleMap.put("供应商编码", "supplierid");
			titleMap.put("品牌编码", "brandid");
			titleMap.put("结算方式", "settletypename");
			titleMap.put("每月结算日", "settleday");


			for(String str : firstLine){

				titleList.add((String) titleMap.get(str));
			}

			//获取导入数据
			List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, titleList);
			if(list.size() != 0){

				List<Map<String, Object>> errorList =buyService.importSupplierBrandSettletype(list);
				if(errorList.size() > 0){

					Map errorTitleMap = new LinkedHashMap();
					errorTitleMap.put("supplierid", "供应商编码");
					errorTitleMap.put("brandid", "品牌编码");
					errorTitleMap.put("settletypename", "结算方式");
					errorTitleMap.put("settleday", "每月结算日");
					errorTitleMap.put("lineno", "行号");
					errorTitleMap.put("errors", "错误信息");

					String fileid = createErrorFile(errorTitleMap, errorList, request);
					retMap.put("msg", "导入失败" + errorList.size() + "条");
					retMap.put("errorid",fileid);
				} else {
					retMap.put("flag", true);
				}
			}else{
				retMap.put("excelempty", true);
			}

		}catch (Exception e) {
			e.printStackTrace();
			retMap.put("exception", true);
		}
		addJSONObject(retMap);
		return SUCCESS;
	}
}