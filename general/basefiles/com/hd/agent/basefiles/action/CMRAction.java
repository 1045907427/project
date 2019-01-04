/**
 * @(#)CMRAction.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 17, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.FilesLevel;
import com.hd.agent.basefiles.model.MarketActivitySort;
import com.hd.agent.basefiles.model.SaleChance_Sort;
import com.hd.agent.basefiles.model.SaleMode;
import com.hd.agent.basefiles.model.SaleMode_Detail;
import com.hd.agent.basefiles.model.TaskSort;
import com.hd.agent.basefiles.service.ICMRService;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.model.Tree;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.system.model.SysCode;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class CMRAction extends FilesLevelAction{

	private ICMRService  cmrService;
	private SaleMode saleMode;
	private SaleChance_Sort saleChance_Sort;
	private TaskSort taskSort;
	private MarketActivitySort marketActivitySort;
	public ICMRService getCmrService() {
		return cmrService;
	}
	public void setCmrService(ICMRService cmrService) {
		this.cmrService = cmrService;
	}
	public SaleMode getSaleMode() {
		return saleMode;
	}
	public void setSaleMode(SaleMode saleMode) {
		this.saleMode = saleMode;
	}
	public SaleChance_Sort getSaleChance_Sort() {
		return saleChance_Sort;
	}
	public void setSaleChance_Sort(SaleChance_Sort saleChance_Sort) {
		this.saleChance_Sort = saleChance_Sort;
	}
	
	/*----------------------------------------销售方式-------------------------------------*/
	
	public MarketActivitySort getMarketActivitySort() {
		return marketActivitySort;
	}
	public void setMarketActivitySort(MarketActivitySort marketActivitySort) {
		this.marketActivitySort = marketActivitySort;
	}
	public TaskSort getTaskSort() {
		return taskSort;
	}
	public void setTaskSort(TaskSort taskSort) {
		this.taskSort = taskSort;
	}
	/**
	 * 显示销售方式页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 7, 2013
	 */
	public String showSaleModePage()throws Exception{
		return SUCCESS;
	}
	
	public String showSaleModeListPage()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = cmrService.showSaleModeListPage(pageMap);
		List<SaleMode> list = pageData.getList();
		for(SaleMode saleMode : list){
			if(StringUtils.isNotEmpty(saleMode.getState())){//状态
				SysCode sysCode = super.getBaseSysCodeService().showSysCodeInfo(saleMode.getState(), "state");
				if(sysCode != null){
					saleMode.setStateName(sysCode.getCodename());
				}
			}
		}
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 显示销售方式详情页面
	 * 判断数据是否允许查看
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 7, 2013
	 */
	public String showSaleModeViewPage()throws Exception{
		String id = request.getParameter("id");
		SaleMode saleMode = cmrService.showSaleModeInfo(id);
		if(saleMode != null){
			//获取t_base_crm_salemode表的字段查看权限
			Map colMap = getAccessColumn("t_base_crm_salemode");
			request.setAttribute("showMap", colMap);
			request.setAttribute("saleMode", saleMode);
		}
		return SUCCESS;
	}
	
	/**
	 * 显示销售方式新增页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 7, 2013
	 */
	public String showSalemodeAddPage()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 显示销售方式修改页面
	 * 判断数据是否允许修改，判断是否被引用
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 7, 2013
	 */
	public String showSalemodeEditPage()throws Exception{
		String id = request.getParameter("id");
		SaleMode saleMode = cmrService.showSaleModeInfo(id);
		if(saleMode != null){
			//获取t_base_goods_info表的字段编辑权限
			Map colMap = getEditAccessColumn("t_base_crm_salemode");
			request.setAttribute("showMap", colMap);
			request.setAttribute("saleMode", saleMode);
		}
		return SUCCESS;
	}
	
	/**
	 * 显示销售方式复制页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 9, 2013
	 */
	public String showSaleModeCopyPage()throws Exception{
		String id = request.getParameter("id");
		SaleMode saleMode = cmrService.showSaleModeInfo(id);
		if(saleMode != null){
			request.setAttribute("saleMode", saleMode);
		}
		return SUCCESS;
	}
	/**
	 * 根据销售方式编号获取销售详细列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 7, 2013
	 */
	public String showSaleModeDetailList()throws Exception{
		String salemodeid = request.getParameter("salemodeid");//销售方式编号
		List list = cmrService.showSaleModeDetailList(salemodeid);
		addJSONArray(list);
		return  SUCCESS;
	}
	
	/**
	 * 检验销售方式编号唯一性
	 * true 已存在 false 不存在
	 * @return
	 * @throws Excepton
	 * @author panxiaoxiao 
	 * @date May 7, 2013
	 */
	public String isRepeatSaleModeId()throws Exception{
		String id = request.getParameter("id");
		boolean flag = cmrService.isRepeatSaleModeId(id);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 检验销售方式名称唯一性
	 * true 已存在 false 不存在
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 7, 2013
	 */
	public String isRepeatSaleModeName()throws Exception{
		boolean flag = cmrService.isRepeatSaleModeName(request.getParameter("name"));
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 新增销售方式
	 * @return
	 * @author panxiaoxiao 
	 * @date May 8, 2013
	 */
	@UserOperateLog(key="SaleMode",type=2,value="")
	public String addSaleMode()throws Exception{
		//检验销售方式编号是否重复
		if(StringUtils.isNotEmpty(saleMode.getId())){
			if(!cmrService.isRepeatSaleModeId(saleMode.getId())){
				String type = request.getParameter("type");//save or hold
				String insertedSMD = request.getParameter("insertedSMD");
				List<SaleMode_Detail> insertDetailList = new ArrayList<SaleMode_Detail>();
				if(insertedSMD != null){
					//把json字符串转换成对象
					JSONArray json = JSONArray.fromObject(insertedSMD);
					insertDetailList = JSONArray.toList(json, SaleMode_Detail.class);
				}
				if("save".equals(type)){
					saleMode.setState("2");
				}
				else{
					saleMode.setState("3");
				}
				SysUser sysUser = getSysUser();
				saleMode.setAdddeptid(sysUser.getDepartmentid());
				saleMode.setAdddeptname(sysUser.getDepartmentname());
				saleMode.setAdduserid(sysUser.getUserid());
				saleMode.setAddusername(sysUser.getName());
				boolean flag = cmrService.addSaleMode(saleMode, insertDetailList);
				addLog("新增销售方式 编号:"+saleMode.getId(),flag);
				addJSONObject("flag", flag);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 修改销售方式
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 8, 2013
	 */
	@UserOperateLog(key="SaleMode",type=3,value="")
	public String editSaleMode()throws Exception{
		String type = request.getParameter("type");//save or hold
		String insertedSMD = request.getParameter("insertedSMD");
		String updatedSMD = request.getParameter("updatedSMD");
		String deletedSMD = request.getParameter("deletedSMD");
		List<SaleMode_Detail> insertDetailList = new ArrayList<SaleMode_Detail>();
		List<SaleMode_Detail> updateDetailList = new ArrayList<SaleMode_Detail>();
		List<SaleMode_Detail> delDetailList = new ArrayList<SaleMode_Detail>();
		if(insertedSMD != null){
			//把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(insertedSMD);
			insertDetailList = JSONArray.toList(json, SaleMode_Detail.class);
		}
		if(updatedSMD != null){
			JSONArray json = JSONArray.fromObject(updatedSMD);
			updateDetailList = JSONArray.toList(json, SaleMode_Detail.class);
		}
		if(deletedSMD != null){
			JSONArray json = JSONArray.fromObject(deletedSMD);
			delDetailList = JSONArray.toList(json, SaleMode_Detail.class);
		}
		if(!"1".equals(saleMode.getState()) && !"0".equals(saleMode.getState())){
			if("save".equals(type)){
				saleMode.setState("2");
			}
		}
		SysUser sysUser = getSysUser();
		saleMode.setModifyuserid(sysUser.getUserid());
		saleMode.setModifyusername(sysUser.getName());
		boolean flag = cmrService.editSaleMode(saleMode, insertDetailList, updateDetailList, delDetailList);
		addLog("修改销售方式 编号:"+saleMode.getId(),flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 删除销售方式
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 8, 2013
	 */
	@UserOperateLog(key="SaleMode",type=4,value="")
	public String deleteSaleMode()throws Exception{
		String id = request.getParameter("id");
		int userNum = 0;
		Map retMap = new HashMap();
		//判断是否被引用，是否允许删除 true可以操作，false不可以操作
		if(!canTableDataDelete("t_base_crm_salemode",id)){//true可以操作，false不可以操作
			userNum = 1;
		}
		else{
			boolean flag = cmrService.deleteSaleMode(id);
			addLog("删除销售方式 编号:"+id,flag);
			retMap.put("flag", flag);
		}
		retMap.put("userNum", userNum);
		addJSONObject(retMap);
		return SUCCESS;
	}
	
	/**
	 * 启用销售方式
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 8, 2013
	 */
	@UserOperateLog(key="SaleMode",type=0,value="")
	public String enableSaleMode()throws Exception{
		String id = request.getParameter("id");
		Map map = new HashMap();
		SysUser sysUser = getSysUser();
		map.put("openuserid", sysUser.getUserid());
		map.put("openusername", sysUser.getName());
		map.put("id", id);
		boolean flag = cmrService.enableSaleMode(map);
		addLog("启用销售方式 编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 禁用销售方式
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 8, 2013
	 */
	@UserOperateLog(key="SaleMode",type=0,value="")
	public String disableSaleMode()throws Exception{
		String id = request.getParameter("id");
		int userNum = 0;
		Map retMap = new HashMap();
		//判断是否被引用,true可以操作，false不可以操作
		if(!canTableDataDelete("t_base_crm_salemode",id)){
			userNum = 1;
		}
		else{
			Map map = new HashMap();
			SysUser sysUser = getSysUser();
			map.put("closeuserid", sysUser.getUserid());
			map.put("closeusername", sysUser.getName());
			map.put("id", id);
			boolean flag = cmrService.disableSaleMode(map);
			retMap.put("flag", flag);
			addLog("禁用销售方式 编号:"+id,flag);
		}
		retMap.put("userNum", userNum);
		addJSONObject(retMap);
		return SUCCESS;
	}
	
	/**
	 * 检验销售阶段编码唯一性
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 9, 2013
	 */
	public String isRepeatSatgeCode()throws Exception{
		String code = request.getParameter("code");
		String salemodeid = request.getParameter("salemodeid");
		Map map = new HashMap();
		map.put("code", code);
		map.put("salemodeid", salemodeid);
		boolean flag = cmrService.isRepeatStageCode(map);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 检验销售阶段名称唯一性
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 9, 2013
	 */
	public String isRepeatSatgeName()throws Exception{
		String name = request.getParameter("name");
		String salemodeid = request.getParameter("salemodeid");
		Map map = new HashMap();
		map.put("name", name);
		map.put("salemodeid", salemodeid);
		boolean flag = cmrService.isRepeatStageName(map);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	/*----------------------------------------销售机会来源分类---------------------------------------------------------*/
	/**
	 * 显示销售机会来源
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 11, 2013
	 */
	public String showSaleChance_SortPage()throws Exception{
		List list = getBaseFilesLevelService().showFilesLevelList("t_base_crm_salechance_sort");
		int len = 0;
		if(null != list && list.size() != 0){
			len = list.size();
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
	 * 显示销售机会来源新增页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 11, 2013
	 */
	public String showSaleChance_SortAddPage()throws Exception{
		String id = request.getParameter("id");//当为空时添加的销售机会来源为一级目录，否则该编号为添加销售机会来源的父级
		SaleChance_Sort saleChance_Sort = cmrService.getSaleChanceInfo(id);
		int len = 0;
		if(saleChance_Sort != null && StringUtils.isNotEmpty(saleChance_Sort.getId())){
			len = saleChance_Sort.getId().length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_crm_salechance_sort", len);
		request.setAttribute("saleChance_Sort", saleChance_Sort);
		request.setAttribute("len", nextLen);
		return SUCCESS;
	}
	
	
	/**
	 * 显示销售机会来源修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 11, 2013
	 */
	public String showSaleChance_SortEditPage()throws Exception{
		String id = request.getParameter("id");
		String pname = "";
		SaleChance_Sort saleChance_Sort = cmrService.getSaleChanceInfo(id);
		if(null != saleChance_Sort && StringUtils.isNotEmpty(saleChance_Sort.getPid())){
			SaleChance_Sort saleChance = cmrService.getSaleChanceInfo(saleChance_Sort.getPid());
			if(null != saleChance){
				pname = saleChance.getName();
			}
		}
		int len = 0;
		if(saleChance_Sort != null && StringUtils.isNotEmpty(saleChance_Sort.getPid())){
			len = saleChance_Sort.getPid().length();
		}
		request.setAttribute("pname", pname);
		int nextLen = getBaseTreeFilesNext("t_base_crm_salechance_sort", len);
		request.setAttribute("editFlag", canTableDataDelete("t_base_crm_salechance_sort", id));
		request.setAttribute("len", nextLen);
		request.setAttribute("saleChance_Sort", saleChance_Sort);
		return SUCCESS;
	}
	
	/**
	 * 显示销售机会来源复制页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 11, 2013
	 */
	public String showSaleChance_SortCopyPage()throws Exception{
		String id = request.getParameter("id");
		String pname = "";
		SaleChance_Sort saleChance_Sort = cmrService.getSaleChanceInfo(id);
		if(null != saleChance_Sort && StringUtils.isNotEmpty(saleChance_Sort.getPid())){
			SaleChance_Sort saleChance = cmrService.getSaleChanceInfo(saleChance_Sort.getPid());
			if(null != saleChance){
				pname = saleChance.getName();
			}
		}
		int len = 0;
		if(saleChance_Sort != null && StringUtils.isNotEmpty(saleChance_Sort.getPid())){
			len = saleChance_Sort.getPid().length();
		}
		request.setAttribute("pname", pname);
		int nextLen = getBaseTreeFilesNext("t_base_crm_salechance_sort", len);
		request.setAttribute("len", nextLen);
		request.setAttribute("saleChance_Sort", saleChance_Sort);
		return SUCCESS;
	}
	
	/**
	 * 显示销售机会来源详情页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 11, 2013
	 */
	public String showSaleChance_SortViewPage()throws Exception{
		String id = request.getParameter("id");
		SaleChance_Sort saleChance_Sort = cmrService.getSaleChanceInfo(id);
		request.setAttribute("saleChance_Sort", saleChance_Sort);
		return SUCCESS;
	}
	
	/**
	 * 显示销售机会来源树型数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 13, 2013
	 */
	public String getSaleChanceSortTree()throws Exception{
		List result = new ArrayList();
		List<SaleChance_Sort> list = cmrService.getSaleChanceSortList(pageMap);
		Map<String,String> first = new HashMap<String,String>();
		Tree pTree = new Tree();
		pTree.setId("");
		pTree.setText("销售机会来源分类");
		pTree.setOpen("true");
		result.add(pTree);
		if(list.size() != 0){
			for(SaleChance_Sort saleChance_Sort:list){
				Tree cTree = new Tree();
				cTree.setId(saleChance_Sort.getId());
				cTree.setParentid(saleChance_Sort.getPid());
				cTree.setText(saleChance_Sort.getThisname());
				cTree.setState(saleChance_Sort.getState());
				result.add(cTree);
			}
		}
		addJSONArray(result);
		return SUCCESS;
	}
	
	/**
	 * 新增销售机会来源
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 13, 2013
	 */
	public String addSaleChance()throws Exception{
		String type = request.getParameter("type");
		if("hold".equals(type)){
			saleChance_Sort.setState("3");
		}
		else{
			saleChance_Sort.setState("2");
		}
		SysUser sysUser = getSysUser();
		saleChance_Sort.setAdddeptid(sysUser.getDepartmentid());
		saleChance_Sort.setAdddeptname(sysUser.getDepartmentname());
		saleChance_Sort.setAdduserid(sysUser.getUserid());
		saleChance_Sort.setAddusername(sysUser.getName());
		boolean flag = cmrService.addSaleChance(saleChance_Sort);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("flag", flag);
		map.put("backid", saleChance_Sort.getId());
		Tree node = new Tree();
		node.setId(saleChance_Sort.getId());
		node.setParentid(saleChance_Sort.getPid());
		node.setState(saleChance_Sort.getState());
		node.setText(saleChance_Sort.getThisname());
		map.put("node", node);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 编码是否重复，是否被使用
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 13, 2013
	 */
	public String saleChanceNOUsed()throws Exception{
		String id = request.getParameter("id");
		SaleChance_Sort saleChance_Sort = cmrService.getSaleChanceInfo(id);
		boolean flag = false;
		if(saleChance_Sort != null){
			flag = true;
		}
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 名称是否被使用,true存在 false 不存在
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 14, 2013
	 */
	public String saleChanceNameNOUsed()throws Exception{
		String name = request.getParameter("name");
		boolean flag = cmrService.saleChanceNameNOUsed(name);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 修改销售机会来源
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 14, 2013
	 */
	public String editSaleChance()throws Exception{
		String type = request.getParameter("type");
		if(!"1".equals(saleChance_Sort.getState()) && !"0".equals(saleChance_Sort.getState())){
			if("save".equals(type)){
				saleChance_Sort.setState("2");
			}
		}
		SysUser sysUser = getSysUser();
		saleChance_Sort.setModifyuserid(sysUser.getUserid());
		saleChance_Sort.setModifyusername(sysUser.getName());
		Map map = cmrService.editSaleChance(saleChance_Sort);
		addJSONObject(map);
		return SUCCESS;
		
	}
	
	/**
	 * 删除销售机会来源，删除时判断是否被引用，引用则无法删除
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 14, 2013
	 */
	public String deleteSaleChance()throws Exception{
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_base_crm_salechance_sort", id);
		if(!delFlag){
			addJSONObject("delFlag", delFlag);
			return SUCCESS;
		}
		boolean flag = cmrService.deleteSaleChance(id);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 启用销售机会来源
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 14, 2013
	 */
	public String enableSaleChance_Sort()throws Exception{
		String id = request.getParameter("id");
		SaleChance_Sort saleChance = new SaleChance_Sort();
		SysUser sysUser = getSysUser();
		saleChance.setOpenuserid(sysUser.getUserid());
		saleChance.setOpenusername(sysUser.getName());
		saleChance.setId(id);
		boolean flag = cmrService.enableSaleChance(saleChance);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 禁用销售机会来源,如果该节点是父节点，则禁用下面所有为启用状态的子节点
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 14, 2013
	 */
	public String disableSaleChance_Sort()throws Exception{
		String id = request.getParameter("id");
		SysUser sysUser = getSysUser();
		Map map = new HashMap();
		map.put("id", id);
		pageMap.setCondition(map);
		List<SaleChance_Sort> list = cmrService.getSalesAreaParentAllChildren(pageMap);//查询该节点及其所有子节点信息
		int successNum = 0,failureNum = 0,notAllowNum = 0;
		List<String> ids = new ArrayList<String>();
		for(SaleChance_Sort saleChance_Sort : list){
			if(!"1".equals(saleChance_Sort.getState())){
				notAllowNum++;
			}
			else{
				SaleChance_Sort saleChance = new SaleChance_Sort();
				saleChance.setCloseuserid(sysUser.getUserid());
				saleChance.setCloseusername(sysUser.getName());
				saleChance.setId(saleChance_Sort.getId());
				boolean flag = cmrService.disableSaleChance(saleChance);
				if(flag){
					successNum++;
					ids.add(saleChance_Sort.getId());//返回所有禁用的记录编号，供前台更新树节点信息
				}
				else{
					failureNum++;
				}
			}
		}
		Map retMap = new HashMap();
		retMap.put("successNum", successNum);
		retMap.put("failureNum", failureNum);
		retMap.put("notAllowNum", notAllowNum);
		retMap.put("ids", ids);
		addJSONObject(retMap);
		return SUCCESS;
	}
	
	/*-----------------------------------任务分类------------------------------------------*/
	
	/**
	 * 任务分类显示页面
	 */
	public String showTaskSortPage()throws Exception{
		//获取表的档案级次列表
		List list = getBaseFilesLevelService().showFilesLevelList("t_base_crm_task_sort");
		int len = 0;
		if(null != list && list.size() != 0){
			len = list.size();
			StringBuilder sb = new StringBuilder();
			if(list.size() != 0){
				for(int i=0;i<list.size();i++){
					FilesLevel level = (FilesLevel)list.get(i);
					sb.append(level.getLen() + ",");
				}
				if(sb.length() > 0){
					request.setAttribute("lenStr", sb.deleteCharAt(sb.length()-1).toString());
				}
			}
		}
		request.setAttribute("len", len);
		return SUCCESS;
	}
	
	/**
	 * 获取任务分类树状结构数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	public String getTaskSortTree()throws Exception{
		List result = new ArrayList();
		List<TaskSort> list = cmrService.getTaskSortList(pageMap);
		Map<String,String> first = new HashMap<String,String>();
		Tree pTree = new Tree();
		pTree.setId("");
		pTree.setText("任务分类");
		pTree.setOpen("true");
		result.add(pTree);
		if(list.size() != 0){
			for(TaskSort taskSort:list){
				Tree cTree = new Tree();
				cTree.setId(taskSort.getId());
				cTree.setParentid(taskSort.getPid());
				cTree.setText(taskSort.getThisname());
				cTree.setState(taskSort.getState());
				result.add(cTree);
			}
		}
		addJSONArray(result);
		return SUCCESS;
	}
	
	/**
	 * 显示任务分类新增页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	public String showTaskSortAddPage()throws Exception{
		String id = request.getParameter("id");//当为空时添加的任务分类为一级目录，否则该编号为添加区域的父级
		TaskSort taskSort = cmrService.getTaskSortView(id);
		int len = 0;
		if(taskSort != null && StringUtils.isNotEmpty(taskSort.getId())){
			len = taskSort.getId().length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_crm_task_sort", len);//本级编码长度
		request.setAttribute("taskSort", taskSort);
		request.setAttribute("len", nextLen);
		return SUCCESS;
	}
	
	/**
	 * 新增任务分类
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	@UserOperateLog(key="TaskSort",type=2,value="")
	public String addTaskSort()throws Exception{
		String type = request.getParameter("type");
		if("save".equals(type)){
			taskSort.setState("2");
		}
		else{
			taskSort.setState("3");
		}
		SysUser sysUser = getSysUser();
		taskSort.setAdddeptid(sysUser.getDepartmentid());
		taskSort.setAdddeptname(sysUser.getDepartmentname());
		taskSort.setAdduserid(sysUser.getUserid());
		taskSort.setAddusername(sysUser.getName());
		boolean flag = cmrService.addTaskSort(taskSort);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", flag);
		map.put("backid", taskSort.getId());
		Tree node = new Tree();
		node.setId(taskSort.getId());
		node.setParentid(taskSort.getPid());
		node.setText(taskSort.getThisname());
		node.setState(taskSort.getState());
		map.put("node", node);
		addLog("新增任务分类 编号:"+taskSort.getId(),flag);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 显示任务分类修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	public String showTaskSortEditPage()throws Exception{
		String id = request.getParameter("id");
		String pname = "";
		TaskSort taskSort = cmrService.getTaskSortView(id);
		if(null != taskSort && StringUtils.isNotEmpty(taskSort.getPid())){
			TaskSort task = cmrService.getTaskSortView(taskSort.getPid());
			if(task != null){
				pname = task.getName();
			}
		}
		int len = 0;
		if(taskSort != null && StringUtils.isNotEmpty(taskSort.getPid())){
			len = taskSort.getPid().length();
		}
		request.setAttribute("pname", pname);
		int nextLen = getBaseTreeFilesNext("t_base_crm_task_sort", len);
		request.setAttribute("len", nextLen);
		request.setAttribute("editFlag", canTableDataDelete("t_base_crm_task_sort", id));
		request.setAttribute("taskSort", taskSort);
		return SUCCESS;
	}
	
	/**
	 * 修改任务分类
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	@UserOperateLog(key="TaskSort",type=3,value="")
	public String editTaskSort()throws Exception{
		String type = request.getParameter("type");
		if(!"0".equals(taskSort.getState()) && !"1".equals(taskSort.getState())){
			if("save".equals(type)){
				taskSort.setState("2");
			}
			else{
				taskSort.setState("3");
			}
		}
		SysUser sysUser = getSysUser();
		taskSort.setModifyuserid(sysUser.getUserid());
		taskSort.setModifyusername(sysUser.getName());
		Map map = cmrService.editTaskSort(taskSort);
		addLog("修改任务分类 编号:"+taskSort.getId(),map.get("flag").equals(true));
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 显示任务分类复制页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	public String showTaskSortCopyPage()throws Exception{
		String id = request.getParameter("id");
		String pname = "";
		TaskSort taskSort = cmrService.getTaskSortView(id);
		if(null != taskSort && StringUtils.isNotEmpty(taskSort.getPid())){
			TaskSort task = cmrService.getTaskSortView(taskSort.getPid());
			if(task != null){
				pname = task.getName();
			}
		}
		int len = 0;
		if(taskSort != null && StringUtils.isNotEmpty(taskSort.getPid())){
			len = taskSort.getPid().length();
		}
		request.setAttribute("pname", pname);
		int nextLen = getBaseTreeFilesNext("t_base_crm_task_sort", len);
		request.setAttribute("len", nextLen);
		request.setAttribute("taskSort", taskSort);
		return SUCCESS;
	}
	
	/**
	 * 显示任务分类详情页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	public String showTaskSortViewPage()throws Exception{
		String id = request.getParameter("id");
		TaskSort taskSort = cmrService.getTaskSortView(id);
		request.setAttribute("taskSort", taskSort);
		return SUCCESS;
	}
	
	/**
	 * 删除任务，删除时判断该信息是否被引用，引用则无法删除
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	@UserOperateLog(key="TaskSort",type=4,value="")
	public String deleteTaskSort()throws Exception{
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_base_crm_task_sort", id);
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag = cmrService.deleteTaskSort(id);
		addLog("删除任务分类 编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 启用任务
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	@UserOperateLog(key="TaskSort",type=0,value="")
	public String enableTaskSort()throws Exception{
		String id = request.getParameter("id");
		TaskSort task = new TaskSort();
		SysUser sysUser = getSysUser();
		task.setOpenuserid(sysUser.getUserid());
		task.setOpenusername(sysUser.getName());
		task.setId(id);
		boolean flag = cmrService.enableTaskSort(task);
		addLog("启用任务分类 编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 禁用任务，如果该节点为父节点，则禁用下面所有为启用状态的子节点
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	@UserOperateLog(key="TaskSort",type=0,value="禁用任务")
	public String disableTaskSort()throws Exception{
		String id = request.getParameter("id");
		SysUser sysUser = getSysUser();
		Map condition = new HashMap();
		condition.put("id", id);
		pageMap.setCondition(condition);
		List<TaskSort> list = cmrService.getTaskSortParentAllChildren(pageMap);//获取该节点及所有子节点
		int successNum = 0;
		int failureNum = 0;
		int notAllowNum = 0;
		List<String> ids = new ArrayList<String>();
		for(TaskSort taskSort:list){
			if(!"1".equals(taskSort.getState())){
				notAllowNum++;
			}
			else{
				TaskSort task = new TaskSort();
				task.setCloseuserid(sysUser.getUserid());
				task.setCloseusername(sysUser.getName());
				task.setId(taskSort.getId());
				boolean flag = cmrService.disableTaskSort(task);
				if(flag){
					successNum++;
					ids.add(taskSort.getId()); //返回所有禁用的记录编号，供前台更新树节点信息
				}
				else{
					failureNum++;
				}
			}
		}
		Map map = new HashMap();
		map.put("successNum", successNum);
		map.put("failureNum", failureNum);
		map.put("notAllowNum", notAllowNum);
		map.put("ids", ids);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 任务编码是否被使用，true 被使用，false 未被使用
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	public String isUsedTaskSortID()throws Exception{
		String id = request.getParameter("id");
		TaskSort taskSort = cmrService.getTaskSortView(id);
		boolean flag = false;
		if(taskSort != null){
			flag = true;
		}
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 任务名称是否被使用，true 被使用，false 未被使用
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 17, 2013
	 */
	public String isUsedTaskSortName()throws Exception{
		String name = request.getParameter("name");
		boolean flag = cmrService.isUsedTaskSortName(name);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/*-----------------------------------------------------------------------*/
	
	/**
	 * 显示市场活动分类页面
	 */
	public String marketActivitySortPage()throws  Exception{
		List list = getBaseFilesLevelService().showFilesLevelList("t_base_crm_marketactivity_sort");
		int len = 0;
		if(null != list && list.size() != 0){
			len = list.size();
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
	 * 获取市场活动分类树状结构数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 20, 2013
	 */
	public String getmarketActivityTree()throws Exception{
		List retList = new ArrayList();
		Tree first = new Tree();
		first.setId("");
		first.setText("市场活动分类");
		first.setOpen("true");
		retList.add(first);
		List<MarketActivitySort> markList = cmrService.getmarketActivitySortList(pageMap);
		for(MarketActivitySort mark:markList){
			Tree cTree = new Tree();
			cTree.setId(mark.getId());
			cTree.setText(mark.getThisname());
			cTree.setParentid(mark.getPid());
			cTree.setState(mark.getState());
			retList.add(cTree);
		}
		addJSONArray(retList);
		return SUCCESS;
	}
	/**
	 * 显示市场活动分类新增页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 20, 2013
	 */
	public String marketActivitySortAddPage()throws Exception{
		String id = request.getParameter("id"); //当为空时添加的市场活动分类为一级目录，否则该编号为添加市场活动的父级
		MarketActivitySort marketActivitySort = cmrService.getmarketActivitySortDetail(id);
		int len = 0;
		if(marketActivitySort != null && StringUtils.isNotEmpty(marketActivitySort.getId())){
			len = marketActivitySort.getId().length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_crm_marketactivity_sort", len);
		request.setAttribute("marketActivitySort", marketActivitySort);
		request.setAttribute("len", nextLen);
		return SUCCESS;
	}
	
	/**
	 *新增市场活动分类
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 20, 2013
	 */
	@UserOperateLog(key="MarketActivitySort",type=2,value="")
	public String addmarketActivitySort()throws Exception{
		String type = request.getParameter("type");
		if("save".equals(type)){
			marketActivitySort.setState("2");
		}
		else{
			marketActivitySort.setState("3");
		}
		SysUser sysUser = getSysUser();
		marketActivitySort.setAdddeptid(sysUser.getDepartmentid());
		marketActivitySort.setAdddeptname(sysUser.getDepartmentname());
		marketActivitySort.setAdduserid(sysUser.getUserid());
		marketActivitySort.setAddusername(sysUser.getName());
		boolean flag = cmrService.addmarketActivitySort(marketActivitySort);
		addLog("新增市场活动分类 编号:"+marketActivitySort.getId(),flag);
		Map map = new HashMap();
		map.put("flag", flag);
		Tree node = new Tree();
		node.setId(marketActivitySort.getId());
		node.setParentid(marketActivitySort.getPid());
		node.setParentid(marketActivitySort.getPid());
		node.setText(marketActivitySort.getThisname());
		node.setState(marketActivitySort.getState());
		map.put("node", node);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 显示市场活动分类修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 20, 2013
	 */
	public String marketActivitySortEditPage()throws Exception{
		String id = request.getParameter("id");
		int len = 0;
		String pname = "";
		MarketActivitySort marketActivitySort = cmrService.getmarketActivitySortDetail(id);
		if(marketActivitySort != null && StringUtils.isNotEmpty(marketActivitySort.getPid())){
			len = marketActivitySort.getPid().length();
			MarketActivitySort mark = cmrService.getmarketActivitySortDetail(marketActivitySort.getPid());
			if(null != mark){
				pname = mark.getName();
			}
		}
		request.setAttribute("pname", pname);
		int nextLen = getBaseTreeFilesNext("t_base_crm_marketactivity_sort", len);
		request.setAttribute("len", nextLen);
		request.setAttribute("editFlag", canTableDataDelete("t_base_crm_marketactivity_sort", id));
		request.setAttribute("marketActivitySort", marketActivitySort);
		return SUCCESS;
	}
	
	/**
	 * 修改市场活动分类
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 20, 2013
	 */
	@UserOperateLog(key="MarketActivitySort",type=3,value="")
	public String editMarketActivitySort()throws Exception{
		String type = request.getParameter("type");
		if(!"0".equals(marketActivitySort.getState()) && !"1".equals(marketActivitySort.getState())){
			if("save".equals(type)){
				marketActivitySort.setState("2");
			}
		}
		SysUser sysUser = getSysUser();
		marketActivitySort.setModifyuserid(sysUser.getUserid());
		marketActivitySort.setModifyusername(sysUser.getName());
		Map map = cmrService.editmarketActivitySort(marketActivitySort);
		addLog("修改市场活动分类 编号:"+marketActivitySort.getId(),map.get("flag").equals(true));
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 显示市场活动分类复制页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 20, 2013
	 */
	public String marketActivitySortCopyPage()throws Exception{
		String id = request.getParameter("id");
		String pname = "";
		int len = 0;
		MarketActivitySort marketActivitySort = cmrService.getmarketActivitySortDetail(id);
		if(null != marketActivitySort && StringUtils.isNotEmpty(marketActivitySort.getPid())){
			len = marketActivitySort.getPid().length();
			MarketActivitySort market = cmrService.getmarketActivitySortDetail(marketActivitySort.getPid());
			if(null != market){
				pname = market.getName();
			}
		}
		int nextLen = getBaseTreeFilesNext("t_base_crm_marketactivity_sort", len);
		request.setAttribute("len", nextLen);
		request.setAttribute("pname", pname);
		request.setAttribute("marketActivitySort", marketActivitySort);
		return SUCCESS;
	}
	
	/**
	 * 显示市场活动分类详情页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 20, 2013
	 */
	public String marketActivitySortViewPage()throws Exception{
		String id = request.getParameter("id");
		MarketActivitySort marketActivitySort = cmrService.getmarketActivitySortDetail(id);
		request.setAttribute("marketActivitySort", marketActivitySort);
		return SUCCESS;
	}
	
	/**
	 * 删除市场活动分类，删除时判断该信息是否被引用，引用则无法删除
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 20, 2013
	 */
	@UserOperateLog(key="MarketActivitySort",type=4,value="")
	public String deleteMarketActivitySort()throws Exception{
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_base_crm_marketactivity_sort", id);
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag = cmrService.deletemarketActivitySort(id);
		addLog("删除市场活动分类 编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 启用市场活动分类
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 20, 2013
	 */
	@UserOperateLog(key="MarketActivitySort",type=0,value="")
	public String enableMarketActivitySort()throws Exception{
		String id = request.getParameter("id");
		MarketActivitySort market = new MarketActivitySort();
		SysUser sysUser = getSysUser();
		market.setOpenuserid(sysUser.getUserid());
		market.setOpenusername(sysUser.getName());
		market.setId(id);
		boolean flag = cmrService.enablemarketActivitySort(market);
		addLog("启用市场活动分类 编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 禁用市场活动分类，如果该节点是父节点，则禁用下面所有启用活动的子节点
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 20, 2013
	 */
	@UserOperateLog(key="MarketActivitySort",type=0,value="禁用市场活动分类")
	public String disableMarketActivitySort()throws Exception{
		String id = request.getParameter("id");
		SysUser sysUser = getSysUser();
		Map condition = new HashMap();
		condition.put("id", id);
		pageMap.setCondition(condition);
		List<MarketActivitySort> list = cmrService.getmarketActivitySortParentAllChildren(pageMap);//查询该节点及所有子节点信息
		int successNum = 0;
		int failureNum = 0;
		int notAllowNum = 0;
		List<String> ids = new ArrayList<String>();
		for(MarketActivitySort marketActivitySort:list){
			if(!"1".equals(marketActivitySort.getState())){
				notAllowNum++;
			}
			else{
				MarketActivitySort market = new MarketActivitySort();
				market.setCloseuserid(sysUser.getUserid());
				market.setCloseusername(sysUser.getName());
				market.setId(marketActivitySort.getId());
				boolean flag = cmrService.disablemarketActivitySort(market);
				if(flag){
					successNum++;
					ids.add(marketActivitySort.getId());
				}
				else{
					failureNum++;
				}
			}
		}
		Map map = new HashMap();
		map.put("successNum", successNum);
		map.put("failureNum", failureNum);
		map.put("notAllowNum", notAllowNum);
		map.put("ids", ids);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 验证市场活动分类编码是否被使用，true 被使用，false 未被使用
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 20, 2013
	 */
	public String isUsedMarketActivitySortID()throws Exception{
		String id = request.getParameter("id");
		MarketActivitySort marketActivitySort = cmrService.getmarketActivitySortDetail(id);
		boolean flag = false;
		if(marketActivitySort != null){
			flag = true;
		}
		addJSONObject("flag", flag);
		return SUCCESS;
		
	}
	
	/**
	 * 验证市场活动分类名称是否被使用，true 被使用，false 未被使用
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 20, 2013
	 */
	public String isUsedMarketActivitySortName()throws Exception{
		String name = request.getParameter("name");
		boolean flag = cmrService.isUsedmarketActivitySortName(name);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
}

