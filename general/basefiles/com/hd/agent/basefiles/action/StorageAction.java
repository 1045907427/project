/**
 * @(#)StorageAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Apr 16, 2013 chenwei 创建版本
 */
package com.hd.agent.basefiles.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.StorageInfo;
import com.hd.agent.basefiles.model.StorageInout;
import com.hd.agent.basefiles.model.StorageLocation;
import com.hd.agent.basefiles.model.StorageType;
import com.hd.agent.basefiles.service.IStorageService;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.system.model.SysCode;

/**
 * 仓库档案action
 * 
 * @author chenwei
 */
public class StorageAction extends BaseAction {
	/**
	 * 仓库档案service
	 */
	private IStorageService storageService;
	/**
	 * 出入库类型档案
	 */
	private StorageInout storageInout;
	/**
	 * 仓库类型
	 */
	private StorageType storageType;
	/**
	 * 库位档案
	 */
	private StorageLocation storageLocation;
	/**
	 * 仓库档案
	 */
	private StorageInfo storageInfo;
	
	public IStorageService getStorageService() {
		return storageService;
	}

	public void setStorageService(IStorageService storageService) {
		this.storageService = storageService;
	}
	
	public StorageInout getStorageInout() {
		return storageInout;
	}

	public void setStorageInout(StorageInout storageInout) {
		this.storageInout = storageInout;
	}

	public StorageType getStorageType() {
		return storageType;
	}

	public void setStorageType(StorageType storageType) {
		this.storageType = storageType;
	}

	public StorageLocation getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(StorageLocation storageLocation) {
		this.storageLocation = storageLocation;
	}
	
	public StorageInfo getStorageInfo() {
		return storageInfo;
	}

	public void setStorageInfo(StorageInfo storageInfo) {
		this.storageInfo = storageInfo;
	}

	/**
	 * 显示出入库档案类型页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 16, 2013
	 */
	public String showStorageInoutPage() throws Exception{
		return "success";
	}
	/**
	 * 显示出入库档案类型添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 16, 2013
	 */
	public String showStorageInoutAddPage() throws Exception{
		return "success";
	}
	/**
	 * 出入库类型档案暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 16, 2013
	 */
	@UserOperateLog(key="StorageInout",type=2)
	public String addStorageInoutHold() throws Exception{
		storageInout.setState("3");
		boolean flag = storageService.addStorageInout(storageInout);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", storageInout.getId());
		addJSONObject(map);
		addLog("出入库类型暂存 编码："+storageInout.getId(), flag);
		return "success";
	}
	/**
	 * 出入库类型档案保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 16, 2013
	 */
	@UserOperateLog(key="StorageInout",type=2)
	public String addStorageInoutSave() throws Exception{
		storageInout.setState("2");
		boolean flag = storageService.addStorageInout(storageInout);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", storageInout.getId());
		addJSONObject(map);
		addLog("出入库类型保存 编码："+storageInout.getId(), flag);
		return "success";
	}
	/**
	 * 验证出入库类型编码是否重复
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public String checkStorageInoutID() throws Exception{
		String id = request.getParameter("id");
		boolean flag = storageService.checkStorageInoutID(id);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 验证出入库类型名称是否重复
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public String checkStorageInoutName() throws Exception{
		String name = request.getParameter("name");
		boolean flag =  storageService.checkStorageInoutName(name);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 显示出入库类型详细页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public String showStorageInoutInfo() throws Exception{
		String id = request.getParameter("id");
		StorageInout storageInout = storageService.showStorageInoutInfo(id);
		request.setAttribute("storageInout", storageInout);
		return "success";
	}
	/**
	 * 显示出入库类型添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public String showStorageInoutCopyPage() throws Exception{
		String id = request.getParameter("id");
		StorageInout storageInout = storageService.showStorageInoutInfo(id);
		request.setAttribute("storageInout", storageInout);
		return "success";
	}
	/**
	 * 获取出入库类型列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public String showStorageInoutList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = storageService.showStorageInoutList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 显示出入库类型修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public String showStorageInoutEditPage() throws Exception{
		String id = request.getParameter("id");
		StorageInout storageInout = storageService.showStorageInoutInfo(id);
		request.setAttribute("storageInout", storageInout);
		request.setAttribute("editFlag", canTableDataDelete("t_base_storage_inout", id));
		return "success";
	}
	/**
	 * 出入库档案修改暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	@UserOperateLog(key="StorageInout",type=3)
	public String editStorageInoutHold() throws Exception{
		SysUser sysUser = getSysUser();
		storageInout.setState("3");
		storageInout.setModifyuserid(sysUser.getUserid());
		storageInout.setModifyusername(sysUser.getName());
		//判断加锁是否可以操作
		boolean lockFlag = isLockEdit("t_base_storage_inout", storageInout.getOldid());
		boolean flag = false;
		if(lockFlag){
			flag = storageService.editStorageInout(storageInout);
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("lockFlag", lockFlag);
		map.put("id", storageInout.getId());
		addJSONObject(map);
		addLog("出入库类型修改暂存 编码："+storageInout.getId(), flag);
		return "success";
	}
	/**
	 * 出入库档案修改保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	@UserOperateLog(key="StorageInout",type=3)
	public String editStorageInoutSave() throws Exception{
		SysUser sysUser = getSysUser();
		if(!"1".equals(storageInout.getState())){
			storageInout.setState("2");
		}
		storageInout.setModifyuserid(sysUser.getUserid());
		storageInout.setModifyusername(sysUser.getName());
		//判断加锁是否可以操作
		boolean lockFlag = isLockEdit("t_base_storage_inout", storageInout.getOldid());
		boolean flag = false;
		if(lockFlag){
			flag = storageService.editStorageInout(storageInout);
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("lockFlag", lockFlag);
		map.put("id", storageInout.getId());
		addJSONObject(map);
		addLog("出入库类型修改保存 编码："+storageInout.getId(), flag);
		return "success";
	}
	/**
	 * 删除出入库类型
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	@UserOperateLog(key="StorageInout",type=4)
	public String deleteStorageInout() throws Exception{
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_base_storage_inout", id);
		boolean flag = false;
		if(delFlag){
			flag = storageService.deleteStorageInout(id);
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("delFlag",delFlag );
		addJSONObject(map);
		addLog("出入库类型删除 编码:"+id, flag);
		return "success";
	}
	/**
	 * 启用出入库类型
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	@UserOperateLog(key="StorageInout",type=3)
	public String openStorageInout() throws Exception{
		String id = request.getParameter("id");
		boolean flag = storageService.openStorageInout(id);
		addJSONObject("flag", flag);
		addLog("出入库类型启用 编码:"+id, flag);
		return "success";
	}
	/**
	 * 禁用出入库类型
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	@UserOperateLog(key="StorageInout",type=3)
	public String closeStorageInout() throws Exception{
		String id = request.getParameter("id");
		boolean flag = storageService.closeStorageInout(id);
		addJSONObject("flag", flag);
		addLog("出入库类型禁用 编码:"+id, flag);
		return "success";
	}
	/**
	 * 显示仓库类型页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public String showStorageTypePage() throws Exception{
		return "success";
	}
	/**
	 * 显示仓库类型添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public String showStorageTypeAddPage() throws Exception{
		return "success";
	}
	/**
	 * 仓库类型添加暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	@UserOperateLog(key="StorageType",type=2)
	public String addStorageTypeHold() throws Exception{
		storageType.setState("3");
		boolean flag = storageService.addStorageType(storageType);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", storageType.getId());
		addJSONObject(map);
		addLog("仓库类型暂存 编码："+storageType.getId(), flag);
		return "success";
	}
	/**
	 * 仓类型添加保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	@UserOperateLog(key="StorageType",type=2)
	public String addStorageTypeSave() throws Exception{
		storageType.setState("2");
		boolean flag = storageService.addStorageType(storageType);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", storageType.getId());
		addJSONObject(map);
		addLog("仓库类型保存 编码："+storageType.getId(), flag);
		return "success";
	}
	/**
	 * 验证仓库类型编码是否重复
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public String checkStorageTypeID() throws Exception{
		String id = request.getParameter("id");
		boolean flag = storageService.checkStorageTypeID(id);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 验证仓库类型名称是否重复
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public String checkStorageTypeName() throws Exception{
		String name = request.getParameter("name");
		boolean flag = storageService.checkStorageTypeID(name);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 显示仓库类型详细页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public String showStorageTypeInfo() throws Exception{
		String id  = request.getParameter("id");
		StorageType storageType = storageService.showStorageTypeInfo(id);
		request.setAttribute("storageType", storageType);
		return "success";
	}
	/**
	 * 获取仓库类型列表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public String showStorageTypeList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = storageService.showStorageTypeList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 显示仓库类型修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public String showStorageTypeEditPage() throws Exception{
		String id  = request.getParameter("id");
		StorageType storageType = storageService.showStorageTypeInfo(id);
		request.setAttribute("storageType", storageType);
		request.setAttribute("editFlag", canTableDataDelete("t_base_storage_type", id));
		return "success";
	}
	/**
	 * 仓库类型修改暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	@UserOperateLog(key="StorageType",type=3)
	public String editStorageTypeHold() throws Exception{
		SysUser sysUser = getSysUser();
		storageType.setState("3");
		storageType.setModifyuserid(sysUser.getUserid());
		storageType.setModifyusername(sysUser.getName());
		//判断加锁是否可以操作
		boolean lockFlag = isLockEdit("t_base_storage_type", storageType.getOldid());
		boolean flag = false;
		if(lockFlag){
			flag = storageService.editStorageType(storageType);
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("lockFlag", lockFlag);
		map.put("id", storageType.getId());
		
		addJSONObject(map);
		addLog("仓库类型修改暂存 编码："+storageType.getId(), flag);
		return "success";
	}
	/**
	 * 仓库类型修改保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	@UserOperateLog(key="StorageType",type=3)
	public String editStorageTypeSave() throws Exception{
		SysUser sysUser = getSysUser();
		if(!"1".equals(storageType.getState())){
			storageType.setState("2");
		}
		storageType.setModifyuserid(sysUser.getUserid());
		storageType.setModifyusername(sysUser.getName());
		//判断加锁是否可以操作
		boolean lockFlag = isLockEdit("t_base_storage_type", storageType.getOldid());
		boolean flag = false;
		if(lockFlag){
			flag = storageService.editStorageType(storageType);
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("lockFlag", lockFlag);
		map.put("id", storageType.getId());
		addJSONObject(map);
		addLog("仓库类型修改保存 编码："+storageType.getId(), flag);
		return "success";
	}
	/**
	 * 显示仓库类型复制新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public String showStorageTypeCopyPage() throws Exception{
		String id  = request.getParameter("id");
		StorageType storageType = storageService.showStorageTypeInfo(id);
		request.setAttribute("storageType", storageType);
		return "success";
	}
	/**
	 * 删除仓库类型
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	@UserOperateLog(key="StorageType",type=4)
	public String deleteStorageType() throws Exception{
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_base_storage_type", id);
		boolean flag = false;
		if(delFlag){
			flag = storageService.deleteStorageType(id);
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("delFlag",delFlag );
		addJSONObject(map);
		addLog("仓库类型删除 编码:"+id, flag);
		return "success";
	}
	/**
	 * 仓库类型启用
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	@UserOperateLog(key="StorageType",type=3)
	public String openStorageType() throws Exception{
		String id = request.getParameter("id");
		boolean flag = storageService.openStorageType(id);
		addJSONObject("flag", flag);
		addLog("仓库类型启用 编码:"+id, flag);
		return "success";
	}
	/**
	 * 仓库类型禁用
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	@UserOperateLog(key="StorageType",type=3)
	public String closeStorageType() throws Exception{
		String id = request.getParameter("id");
		boolean flag = storageService.closeStorageType(id);
		addJSONObject("flag", flag);
		addLog("仓库类型禁用 编码:"+id, flag);
		return "success";
	}
	
	/*----------------------库位档案---------------------------*/
	/**
	 * 显示库位档案管理页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	public String showStorageLocationPage() throws Exception{
		return "success";
	}
	/**
	 * 显示库位档案新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	public String showStorageLocationAddPage() throws Exception{
		String id = request.getParameter("id");
		StorageLocation storageLocation = storageService.showStorageLocationInfo(id);
		if(null!=id && !"".equals(id)){
			//根据选中的库位档案获取下级本级编码长度
			int nextLen = getBaseTreeFilesNext("t_base_storage_location",id.length());
			request.setAttribute("nextLen", nextLen);
		}else{
			//根据选中的库位档案获取下级本级编码长度
			int nextLen = getBaseTreeFilesNext("t_base_storage_location",0);
			request.setAttribute("nextLen", nextLen);
		}
		request.setAttribute("storageLocation", storageLocation);
		return "success";
	}
	/**
	 * 获取库位档案树状数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	public String showStorageLocationTree() throws Exception{
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		List<StorageLocation> list = storageService.getStorageLocationList();
		Map<String, String> first = new HashMap<String, String>();
		first.put("id", "");
		first.put("name", "库位");
		first.put("open", "true");
		result.add(first);
		for(StorageLocation storageLocation : list){
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", storageLocation.getId());
			if(null==storageLocation.getPid()){
				map.put("pid", "");
			}else{
				map.put("pid", storageLocation.getPid());
			}
			map.put("name", storageLocation.getThisname());
			map.put("state", storageLocation.getState());
			result.add(map);
		}
		addJSONArray(result);
		return "success";
	}
	/**
	 * 库位档案新增暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	@UserOperateLog(key="StorageLocation",type=2)
	public String addStorageLocationHold() throws Exception{
		storageLocation.setState("3");
		boolean flag = storageService.addStorageLocation(storageLocation);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", storageLocation.getId());
		
		Map<String, String> node = new HashMap<String, String>();
		node.put("id", storageLocation.getId());
		node.put("pid", storageLocation.getPid());
		node.put("name", storageLocation.getThisname());
		node.put("state", storageLocation.getState());
		map.put("node", node);
		addJSONObject(map);
		addLog("库位档案新增暂存 编码："+storageLocation.getId(), flag);
		return "success";
	}
	/**
	 * 库位档案新增保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	@UserOperateLog(key="StorageLocation",type=2)
	public String addStorageLocationSave() throws Exception{
		storageLocation.setState("2");
		boolean flag = storageService.addStorageLocation(storageLocation);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", storageLocation.getId());
		
		Map<String, String> node = new HashMap<String, String>();
		node.put("id", storageLocation.getId());
		node.put("pid", storageLocation.getPid());
		node.put("name", storageLocation.getThisname());
		node.put("state", storageLocation.getState());
		map.put("node", node);
		addJSONObject(map);
		addLog("库位档案新增保存 编码："+storageLocation.getId(), flag);
		return "success";
	}
	/**
	 * 验证库位档案ID是否重复
	 * true未重复 false重复
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	public String checkStorageLocationID() throws Exception{
		String id = request.getParameter("id");
		boolean flag = storageService.checkStorageLocationID(id);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 验证库位档案名称是否重复
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	public String checkStorageLocationName() throws Exception{
		String name = request.getParameter("name");
		boolean flag = storageService.checkStorageLocationName(name);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 显示库位档案详细信息
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 20, 2013
	 */
	public String showStorageLocationInfoPage() throws Exception{
		String id = request.getParameter("id");
		StorageLocation storageLocation = storageService.showStorageLocationInfo(id);
		List stateList = getBaseSysCodeService().showSysCodeListByType("state");
		request.setAttribute("stateList", stateList);
		request.setAttribute("storageLocation", storageLocation);
		return "success";
	}
	/**
	 * 显示库位档案修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 20, 2013
	 */
	public String showStorageLocationEditPage() throws Exception{
		String id = request.getParameter("id");
		StorageLocation storageLocation = storageService.showStorageLocationInfo(id);
		//状态编码
		List stateList = getBaseSysCodeService().showSysCodeListByType("state");
		if(null!=id && !"".equals(id)){
			//根据选中的库位档案获取下级本级编码长度
			int nextLen = getBaseTreeFilesNext("t_base_storage_location",storageLocation.getPid().length());
			request.setAttribute("nextLen", nextLen);
		}else{
			//根据选中的库位档案获取下级本级编码长度
			int nextLen = getBaseTreeFilesNext("t_base_storage_location",0);
			request.setAttribute("nextLen", nextLen);
		}
		request.setAttribute("stateList", stateList);
		request.setAttribute("storageLocation", storageLocation);
		request.setAttribute("editFlag", canTableDataDelete("t_base_storage_location", id));
		return "success";
	}
	/**
	 * 库位档案修改暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 20, 2013
	 */
	@UserOperateLog(key="StorageLocation",type=3)
	public String editStorageLocationHold() throws Exception{
		storageLocation.setState("3");
		
		//判断加锁是否可以操作
		boolean lockFlag = isLockEdit("t_base_storage_location", storageLocation.getOldid());
		Map map = new HashMap();
		if(lockFlag){
			map = storageService.editStorageLocation(storageLocation);
			addLog("库位档案修改暂存 编码："+storageLocation.getId(), map.get("flag").equals(true));
		}
		map.put("lockFlag", lockFlag);
		map.put("id", storageLocation.getId());
		addJSONObject(map);
		return "success";
	}
	
	/**
	 * 库位档案修改暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 20, 2013
	 */
	@UserOperateLog(key="StorageLocation",type=3)
	public String editStorageLocationSave() throws Exception{
		if(!"1".equals(storageLocation.getState())){
			storageLocation.setState("2");
		}
		
		//判断加锁是否可以操作
		boolean lockFlag = isLockEdit("t_base_storage_location", storageLocation.getOldid());
		Map map = new HashMap();
		if(lockFlag){
			map = storageService.editStorageLocation(storageLocation);
			addLog("库位档案修改保存 编码："+storageLocation.getId(), map.get("flag").equals(true));
		}
		map.put("lockFlag", lockFlag);
		map.put("id", storageLocation.getId());
		addJSONObject(map);
		return "success";
	}
	/**
	 * 库位档案启用
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 20, 2013
	 */
	@UserOperateLog(key="StorageLocation",type=3)
	public String openStorageLocation() throws Exception{
		String id = request.getParameter("id");
		boolean flag = storageService.openStorageLocation(id);
		addJSONObject("flag", flag);
		addLog("库位档案启用 编码："+id, flag);
		return "success";
	}
	/**
	 * 库位档案禁用
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 20, 2013
	 */
	@UserOperateLog(key="StorageLocation",type=3)
	public String closeStorageLocation() throws Exception{
		String id = request.getParameter("id");
		boolean flag = storageService.closeStorageLocation(id);
		addJSONObject("flag", flag);
		addLog("库位档案禁用 编码："+id, flag);
		return "success";
	}
	/**
	 * 库位档案删除
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 20, 2013
	 */
	@UserOperateLog(key="StorageLocation",type=4)
	public String deleteStorageLocation() throws Exception{
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_base_storage_location", id);
		boolean flag = false;
		if(delFlag){
			flag = storageService.deleteStorageLocation(id);
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("delFlag",delFlag );
		addJSONObject(map);
		addLog("库位类型删除 编码:"+id, flag);
		return "success";
	}
	/**
	 * 显示库位档案复制新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 20, 2013
	 */
	public String showStorageLocationCopyPage() throws Exception{
		String id = request.getParameter("id");
		StorageLocation storageLocation = storageService.showStorageLocationInfo(id);
		if(null!=id && !"".equals(id)){
			//根据选中的库位档案获取下级本级编码长度
			int nextLen = getBaseTreeFilesNext("t_base_storage_location",id.length());
			request.setAttribute("nextLen", nextLen);
		}else{
			//根据选中的库位档案获取下级本级编码长度
			int nextLen = getBaseTreeFilesNext("t_base_storage_location",0);
			request.setAttribute("nextLen", nextLen);
		}
		request.setAttribute("storageLocation", storageLocation);
		return "success";
	}
	
	/**
	 * 判断本级名称是否重复，true 不重复，false 重复
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 26, 2014
	 */
	public String isRepeatStorageLocationThisname()throws Exception{
		String thisname = request.getParameter("thisname");
		boolean flag = storageService.isRepeatStorageLocationThisname(thisname);
		addJSONObject("flag", flag);
		return "success";
	}
	/*--------------------仓库档案----------------------*/
	/**
	 * 显示仓库档案页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 20, 2013
	 */
	public String showStorageInfoPage() throws Exception{
		return "success";
	}
	/**
	 * 显示仓库档案新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 22, 2013
	 */
	public String showStorageInfoAddPage() throws Exception{
		return "success";
	}
	/**
	 * 验证仓库档案编码是否重复
	 * true 未重复 false重复
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 22, 2013
	 */
	public String checkStorageInfoID() throws Exception{
		String id = request.getParameter("id");
		boolean flag = storageService.checkStorageInfoID(id);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 验证仓库档案名称是否重复
	 * true 未重复 false重复
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 22, 2013
	 */
	public String checkStorageInfoName() throws Exception{
		String name = request.getParameter("name");
		boolean flag = storageService.checkStorageInfoName(name);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 仓库档案新增暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 22, 2013
	 */
	@UserOperateLog(key="StorageInfo",type=2)
	public String addStorageInfoHold() throws Exception{
		storageInfo.setState("3");
		boolean flag = storageService.addStorageInfo(storageInfo);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", storageInfo.getId());
		addJSONObject(map);
		addLog("仓库档案新增暂存 编码："+storageInfo.getId(), flag);
		return "success";
	}
	/**
	 * 仓库档案新增保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 22, 2013
	 */
	@UserOperateLog(key="StorageInfo",type=2)
	public String addStorageInfoSave() throws Exception{
		storageInfo.setState("2");
		boolean flag = storageService.addStorageInfo(storageInfo);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", storageInfo.getId());
		addJSONObject(map);
		addLog("仓库档案新增保存 编码："+storageInfo.getId(), flag);
		return "success";
	}
	/**
	 * 显示仓库档案详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 22, 2013
	 */
	public String showStorageInfoViewPage() throws Exception{
		String id = request.getParameter("id");
		StorageInfo storageInfo = storageService.showStorageInfo(id);
		request.setAttribute("storageInfo", storageInfo);
		return "success";
	}
	/**
	 * 显示仓库档案修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 22, 2013
	 */
	public String showStorageInfoEditPage() throws Exception{
		String id = request.getParameter("id");
		StorageInfo storageInfo = storageService.showStorageInfo(id);
		request.setAttribute("storageInfo", storageInfo);
		request.setAttribute("editFlag", canTableDataDelete("t_base_storage_info", id));
		return "success";
	}
	/**
	 * 显示仓库档案复制新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 22, 2013
	 */
	public String showStorageInfoCopyPage() throws Exception{
		String id = request.getParameter("id");
		StorageInfo storageInfo = storageService.showStorageInfo(id);
		request.setAttribute("storageInfo", storageInfo);
		return "success";
	}
	/**
	 * 获取仓库档案列表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 22, 2013
	 */
	public String showStorageInfoDataList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = storageService.showStorageInfoList(pageMap);
		List menoeylist = super.getBaseSysCodeService().showSysCodeListByType("menoyManange");
		List priceList = super.getBaseSysCodeService().showSysCodeListByType("priceManage");
		List<StorageInfo> list = pageData.getList();
		for(StorageInfo storageInfo : list){
			//金额管理方式
			for(int i=0;i<menoeylist.size();i++){
				SysCode sysCode = (SysCode) menoeylist.get(i);
				 if(storageInfo.getMoenytype().equals(sysCode.getCode())){
					 storageInfo.setMoenytypename(sysCode.getCodename());
				 }
			}
			//金额管理方式
			for(int i=0;i<priceList.size();i++){
				SysCode sysCode = (SysCode) priceList.get(i);
				 if(storageInfo.getPricetype().equals(sysCode.getCode())){
					 storageInfo.setPricetypename(sysCode.getCodename());
				 }
			}
		}
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 仓库档案修改暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 22, 2013
	 */
	@UserOperateLog(key="StorageInfo",type=3)
	public String editStorageInfoHold() throws Exception{
		SysUser sysUser = getSysUser();
		storageInfo.setModifyuserid(sysUser.getUserid());
		storageInfo.setModifyusername(sysUser.getName());
		storageInfo.setState("3");
		//判断加锁是否可以操作
		boolean lockFlag = isLockEdit("t_base_storage_info", storageInfo.getOldid());
		boolean flag = false;
		if(lockFlag){
			flag = storageService.editStorageInfo(storageInfo);
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("lockFlag", lockFlag);
		map.put("id", storageInfo.getId());
		addJSONObject(map);
		addLog("仓库档案修改暂存 编码："+storageInfo.getId(), flag);
		return "success";
	}
	/**
	 * 仓库档案修改保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 22, 2013
	 */
	@UserOperateLog(key="StorageInfo",type=3)
	public String editStorageInfoSave() throws Exception{
		SysUser sysUser = getSysUser();
		storageInfo.setModifyuserid(sysUser.getUserid());
		storageInfo.setModifyusername(sysUser.getName());
		if(!"1".equals(storageInfo.getState())){
			storageInfo.setState("2");
		}
		//判断加锁是否可以操作
		boolean lockFlag = isLockEdit("t_base_storage_info", storageInfo.getOldid());
		boolean flag = false;
		if(lockFlag){
			flag = storageService.editStorageInfo(storageInfo);
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("lockFlag", lockFlag);
		map.put("id", storageInfo.getId());
		addJSONObject(map);
		addLog("仓库档案修改保存 编码："+storageInfo.getId(), flag);
		return "success";
	}
	/**
	 * 删除仓库档案信息
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 23, 2013
	 */
	@UserOperateLog(key="StorageInfo",type=4)
	public String deleteStorageInfo() throws Exception{
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_base_storage_info", id);
		boolean flag = false;
		if(delFlag){
			flag = storageService.deleteStorageInfo(id);
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("delFlag",delFlag );
		addJSONObject(map);
		addLog("仓库档案删除 编码:"+id, flag);
		return "success";
	}
	/**
	 * 仓库档案启用
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 23, 2013
	 */
	@UserOperateLog(key="StorageInfo",type=3)
	public String openStorageInfo() throws Exception{
		String id = request.getParameter("id");
		boolean flag = storageService.openStorageInfo(id);
		addJSONObject("flag", flag);
		addLog("仓库档案启用 编码:"+id, flag);
		return "success";
	}
	/**
	 * 仓库档案禁用
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 23, 2013
	 */
	@UserOperateLog(key="StorageInfo",type=3)
	public String closeStorageInfo() throws Exception{
		String id = request.getParameter("id");
		boolean flag = storageService.closeStorageInfo(id);
		addJSONObject("flag", flag);
		addLog("仓库档案禁用 编码:"+id, flag);
		return "success";
	}
}
 
