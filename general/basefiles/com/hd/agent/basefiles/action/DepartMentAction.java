/**
 * @(#)DepartMentAction.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-21 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.StorageInfo;
import com.hd.agent.basefiles.model.WorkCanlendar;
import com.hd.agent.basefiles.service.IDepartMentService;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.model.Tree;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.model.SysParam;
import com.hd.agent.system.service.ISysParamService;
/**
 * 部门档案 对映struts-system配置
 * depart主页面显示 showDepartMainPage
 * 
 * @author panxiaoxiao
 */
public class DepartMentAction extends FilesLevelAction{
	/**
	 * 部门档案定义service
	 */
	private IDepartMentService departMentService;
	private ISysParamService sysParamService;
	private DepartMent departMent;
	
	public ISysParamService getSysParamService() {
		return sysParamService;
	}
	public void setSysParamService(ISysParamService sysParamService) {
		this.sysParamService = sysParamService;
	}
	public DepartMent getDepartMent() {
		return departMent;
	}
	public void setDepartMent(DepartMent departMent) {
		this.departMent = departMent;
	}
	public IDepartMentService getDepartMentService() {
		return departMentService;
	}
	public void setDepartMentService(IDepartMentService departMentService) {
		this.departMentService = departMentService;
	}
	
	/**
	 * 显示部门档案主页
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-21
	 */
	public String showDepartMainPage() throws Exception{
		return "success";
	}
	
	/**
	 * 获取树形部门json数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-23
	 */
	public String showDepartMentTree() throws Exception{
        List treeList=new ArrayList();
        List<DepartMent> list=departMentService.showDepartmentNameList();
        Tree pTree = new Tree();
		SysParam sysParam = sysParamService.getSysParam("COMPANYNAME");
		if(null != sysParam){
            pTree.setText(sysParam.getPvalue());
		}
		else{
            pTree.setText("系统参数配置错误,请联系管理员!");
		}
        pTree.setId("");
        pTree.setOpen("true");
		treeList.add(pTree);
		if(list.size() != 0){
			for(DepartMent departMent : list){
                Tree cTree = new Tree();
                cTree.setId(departMent.getId());
                cTree.setParentid(departMent.getPid());
                cTree.setText(departMent.getName());
                cTree.setState(departMent.getState());
				treeList.add(cTree);
			}
		}
		addJSONArray(treeList);
		return "success";
	}

	/**
	 * 获取树形部门json数据,判断是否选中
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author zhanghonghui
	 * @date Sep 02, 2017
	 */
	public String showDepartMentCheckedTree() throws Exception{
		List treeList=new ArrayList();
		List<DepartMent> list=departMentService.showDepartmentNameList();
		Tree pTree = new Tree();
		SysParam sysParam = sysParamService.getSysParam("COMPANYNAME");
		if(null != sysParam){
			pTree.setText(sysParam.getPvalue());
		}
		else{
			pTree.setText("系统参数配置错误,请联系管理员!");
		}
		String view = request.getParameter("view");
		String checkall = request.getParameter("checkall");
		String checkidarrs=request.getParameter("checkidarrs");
		Map checkedMap = new HashMap();
		if(null!=checkidarrs && !"".equals(checkidarrs.trim())){
			String[]checkidArr=checkidarrs.split(",");
			for(String id:checkidArr){
				checkedMap.put(id,id);
			}
		}
		pTree.setId("");
		pTree.setOpen("true");
		if("view".equals(view)){
			pTree.setChkDisabled(true);
		}
		treeList.add(pTree);
		boolean ispchecked=false;
		if(list.size() != 0){
			for(DepartMent departMent : list){
				Tree deptTree = new Tree();
				deptTree.setId(departMent.getId());
				deptTree.setParentid(departMent.getPid());
				deptTree.setText(departMent.getName());
				deptTree.setState(departMent.getState());

				if("true".equals(checkall) || checkedMap.containsKey(departMent.getId())){
					deptTree.setChecked("true");
					deptTree.setOpen("true");
					ispchecked=true;
				}
				if("view".equals(view)){
					deptTree.setChkDisabled(true);
				}
				treeList.add(deptTree);
			}
		}
		if(ispchecked){
			pTree.setChecked("true");
		}
		addJSONArray(treeList);
		return "success";
	}
	
	/**
	 * 根据部门编号id获取部门信息
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-23
	 */
	public String showDepartMentByMenu()throws Exception{
		String id=request.getParameter("id");
		//获取t_demo_user表的字段编辑权限
		Map colMap = getAccessColumn("t_base_department");
		DepartMent departMent=departMentService.showDepartMentInfo(id);
		if(null != departMent){
			request.setAttribute("showMap", colMap);
			request.setAttribute("departMent", departMent);
			DepartMent departMentInfo=departMentService.showDepartMentInfo(departMent.getPid());
			if(null != departMentInfo){
				request.setAttribute("sName", departMentInfo.getName());
				request.setAttribute("sId", departMentInfo.getId());
			}
			else{
				SysParam sysParam = sysParamService.getSysParam("COMPANYNAME");
				if(null != sysParam){
					request.setAttribute("sName", sysParam.getPvalue());
				}
				request.setAttribute("sId", "");
			}
		}
        List statelist = getBaseSysCodeService().showSysCodeListByType("state");
        if(statelist.size() != 0){
            request.setAttribute("statelist", statelist);
        }
		return "success";
	}
	
	/**
	 * 显示新增部门信息页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-23
	 */
	public String showDepartMentAddPage() throws Exception{
		int pLength=0;
		String sName="COMPANYNAME",sId="";
		String state=request.getParameter("state");
		String id=request.getParameter("id");
		DepartMent departMent=departMentService.showDepartMentInfo(id);
//		String pId=request.getParameter("pId");
		if(null != departMent){
			if(!"3".equals(departMent.getState())){
				pLength=id.length();
				sName=departMent.getName();
				sId=id;
			}
		}
		else{
			id=null;
			pLength=0;
		}
		//获取本级编码长度
		int length=getBaseTreeFilesNext("t_base_department",pLength);
		request.setAttribute("length", length);
		request.setAttribute("state", state);
		request.setAttribute("pId", id);
		request.setAttribute("name",sName);
		request.setAttribute("sId",sId);
		return "success";
	}
	
	/**
	 * 显示修改部门信息页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-23
	 */
	public String showDepartMentEditPage() throws Exception{
		//获取页面传过来id的值
		String id = request.getParameter("id");
		DepartMent departMent=departMentService.showDepartMentInfo(id);
		if(null != departMent){
			//获取t_base_department表的字段编辑权限,字段是否被引用修改权限
			Map colMap =  getBaseFilesEditWithAuthority("t_base_department", departMent);
			//显示修改页面时，给数据加锁
			boolean flag = lockData("t_base_department", id);//true：可以操作,false:不可以操作
			if(flag){
				//往页面传值
				request.setAttribute("editMap", colMap);
				request.setAttribute("lockFlag", "1");//解锁
			}
			else{
				request.setAttribute("editMap", null);
				request.setAttribute("lockFlag", "0");//加锁
			}
			String sName="",sId="";
			int length=0;
			if(null != departMent){
				String thisId=departMent.getThisid();
				length=thisId.length();
				if(StringUtils.isNotEmpty(departMent.getPid())){
					DepartMent departMentInfo=departMentService.showDepartMentInfo(departMent.getPid());
					if(null != departMentInfo){
						sName=departMentInfo.getName();
						sId=departMentInfo.getId();
					}
				}
			}
			request.setAttribute("oldId", id);
			request.setAttribute("departMent", departMent);
			request.setAttribute("sName", sName);
			request.setAttribute("sId", sId);
			request.setAttribute("length", length);
			request.setAttribute("editFlag", canTableDataDelete("t_base_department", id));
		}
        List statelist = getBaseSysCodeService().showSysCodeListByType("state");
        if(statelist.size() != 0){
            request.setAttribute("statelist", statelist);
        }
		return "success";
	}
	
	/**
	 * 部门新增实现功能
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-23
	 */
	@UserOperateLog(key="Department",type=2,value="")
	public String addDepartMent()throws Exception{
		boolean flag=false;
		String style=request.getParameter("style");
		SysUser sysUser = getSysUser();
		if("4".equals(style)){//新增 
			departMent.setAdduserid(sysUser.getUserid());
			departMent.setAdddeptid(sysUser.getDepartmentid());
			flag=departMentService.addDepartMent(departMent);
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("flag", flag);
            Tree node = new Tree();
            node.setId(departMent.getId());
            node.setParentid(departMent.getPid());
            node.setState(departMent.getState());
            node.setText(departMent.getName());
            map.put("node", node);
            addJSONObject(map);
            addLog("新增部门 编号:"+departMent.getId(),flag);
		}
		else{//修改
			departMent.setModifyuserid(sysUser.getUserid());
			departMent.setModifydeptid(sysUser.getDepartmentid());
			Map map = departMentService.editDepartMent(departMent);
            addJSONObject(map);
            addLog("修改部门 编号:"+departMent.getId(),map.get("flag").equals(true));
		}
		return "success";
	}
	
	
//	/**
//	 * 暂存
//	 * @return
//	 * @throws Exception
//	 * @author panxiaoxiao
//	 * @date 2013-1-26
//	 */
//	public String holdDepartMent()throws Exception{
//		boolean flag=false;
//		String style=request.getParameter("style");
//		if("4".equals(style)){//新增
//			flag=departMentService.addDepartMent(departMent);
//		}
//		else{//修改
//			flag=departMentService.editDepartMent(departMent);
//		}
//		addJSONObject("flag", flag);
//		return "success";
//	}
	
	/**
	 * 部门修改实现功能
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-23
	 */
	@UserOperateLog(key="Department",type=3,value="")
	public String editDepartMent()throws Exception{
		SysUser sysUser = getSysUser();
		departMent.setModifyuserid(sysUser.getUserid());
		departMent.setModifydeptid(sysUser.getDepartmentid());
		Map map = departMentService.editDepartMent(departMent);
		addJSONObject(map);
		addLog("修改部门 编号:"+departMent.getId(),map.get("flag").equals(true));
		return "success";
	}
	
	/**
	 * 部门类修改实现功能
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-26
	 */
	@UserOperateLog(key="Department",type=3,value="")
	public String editDepartMentAll()throws Exception{
		SysUser sysUser = getSysUser();
		departMent.setModifyuserid(sysUser.getUserid());
		departMent.setModifydeptid(sysUser.getDepartmentid());
		Map map = departMentService.editDepartMent(departMent);
		addJSONObject(map);
		//添加日志内容
		addLog("修改父级部门 编号:"+departMent.getId()+"以下的整个部门",map.get("flag").equals(true));
		return "success";
	}
	/**
	 * 获取部门列表(树型)部门列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-24
	 */
	public String getDepartMentList()throws Exception{
		Map map=new HashMap();
		map.put("id", "");
		map.put("name", "");
		List list=departMentService.showDepartmentNameList();
		list.add(map);
		addJSONArray(list);
		return "success";
	}
	
	/**
	 * 获取部门列表，分页
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-28
	 */
	public String showDepartMentListPage() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		//给通用查询条件sql语句设置别名
		//setQueryRulesWithAlias("d");
		pageMap.setCondition(map);
		PageData pageData=departMentService.showDepartmentList(pageMap);
		List<DepartMent> deptList = pageData.getList();
		for(DepartMent departMent : deptList){
			if(StringUtils.isNotEmpty(departMent.getState())){ //状态
				SysCode sysCode = super.getBaseSysCodeService().showSysCodeInfo(departMent.getState(), "state");
				if(sysCode != null){
					departMent.setStateName(sysCode.getCodename());
				}
			}
			if(StringUtils.isNotEmpty(departMent.getManageruserid())){ //部门主管
				SysUser sysUser = super.getBaseSysUserService().showSysUserInfo(departMent.getManageruserid());
				if(sysUser != null){
					departMent.setManagerName(sysUser.getName());
				}
			}
			if(StringUtils.isNotEmpty(departMent.getWorkcalendar())){ //工作日历
				Map workCanlendarMap = super.getBaseWorkCanlendarService().showWorkCanlendarInfo(departMent.getWorkcalendar());
				if(workCanlendarMap != null){
					if(workCanlendarMap.containsKey("workCanlendar")){
						WorkCanlendar workCanlendar = (WorkCanlendar) workCanlendarMap.get("workCanlendar");
						if(workCanlendar != null){
							departMent.setWorkcalendar(workCanlendar.getName());
						}
					}
				}
			}
			if(StringUtils.isNotEmpty(departMent.getPid())){ //上级部门
				DepartMent dept = departMentService.showDepartMentInfo(departMent.getPid());
				if(dept != null){
					departMent.setpDeptName(dept.getName());
				}
			}
			if(StringUtils.isNotEmpty(departMent.getAdduserid())){ //建档人
				SysUser sysUser = super.getBaseSysUserService().showSysUserInfo(departMent.getAdduserid());
				if(sysUser != null){
					departMent.setAdduserid(sysUser.getName());
				}
			}
			if(StringUtils.isNotEmpty(departMent.getAdddeptid())){ //建档部门
				DepartMent dept = departMentService.showDepartMentInfo(departMent.getAdddeptid());
				if(dept != null){
					departMent.setAdddeptid(dept.getName());
				}
			}
			if(StringUtils.isNotEmpty(departMent.getModifydeptid())){ //最后修改部门
				DepartMent dept = departMentService.showDepartMentInfo(departMent.getModifydeptid());
				if(dept != null){
					departMent.setModifydeptid(dept.getName());
				}
			}
			if(StringUtils.isNotEmpty(departMent.getModifyuserid())){ //最后修改人
				SysUser sysUser = super.getBaseSysUserService().showSysUserInfo(departMent.getModifyuserid());
				if(sysUser != null){
					departMent.setModifyuserid(sysUser.getName());
				}
			}
			if(StringUtils.isNotEmpty(departMent.getOpenuserid())){ //启用人
				SysUser sysUser = super.getBaseSysUserService().showSysUserInfo(departMent.getOpenuserid());
				if(sysUser != null){
					departMent.setOpenuserid(sysUser.getName());
				}
			}
			if(StringUtils.isNotEmpty(departMent.getCloseuserid())){ //禁用人
				SysUser sysUser = super.getBaseSysUserService().showSysUserInfo(departMent.getCloseuserid());
				if(sysUser != null){
					departMent.setCloseuserid(sysUser.getName());
				}
			}
			if(StringUtils.isNotEmpty(departMent.getDepttype())){ //部门业务属性
				String depttypeName = "";
				String[] depttypeArr = departMent.getDepttype().toString().split(",");
				for(int i = 0;i<depttypeArr.length;i++){
					SysCode sysCode = super.getBaseSysCodeService().showSysCodeInfo(depttypeArr[i], "depttype");
					if(sysCode != null){
						if(StringUtils.isNotEmpty(depttypeName)){
							depttypeName += "," + sysCode.getCodename();
						}
						else{
							depttypeName = sysCode.getCodename();
						}
					}
				}
				departMent.setDepttypeName(depttypeName);
			}
			if(StringUtils.isNotEmpty(departMent.getStorageid())){//关联仓库
				StorageInfo storageInfo = getBaseStorageService().showStorageInfo(departMent.getStorageid());
				if(null != storageInfo){
					departMent.setStoragename(storageInfo.getName());
				}
			}
		}
		addJSONObject(pageData);
		return "success";
	}
	
	/**
	 * 获取下一级次长度
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-25
	 */
	public String getNextLenght()throws Exception{
 		int pLen=0;
		String pId=request.getParameter("pId");
		if(StringUtils.isNotEmpty(pId)){
			pLen=pId.length();
		}
		int nextLen=getBaseTreeFilesNext("t_base_department",pLen);
		addJSONObject("nextLen", nextLen);
		return "success";
	}
	
	/**
	 * 是否存在该部门id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-25
	 */
	public String isExistDepartmentId()throws Exception{
		String id="";
		String thisid=request.getParameter("thisid");
		String pId=request.getParameter("pid");
		if(!"".equals(pId)){
			id=pId+thisid;
		}
		else{
			id=thisid;
		}
		String str=departMentService.isExistDepartmentId(id);
		addJSONObject("str", str);
		return "success";
	}
	
	/**
	 * 根据部门编码id删除部门,删除前检验是否被引用(加锁)
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-25
	 */
	@UserOperateLog(key="Department",type=4,value="")
	public String deleteDepartment()throws Exception{
		String id=request.getParameter("id");
		int sucNum = 0,failNum = 0,lockNum = 0,unAllowDel = 0;
		Map retMap = new HashMap();
		//删除部门时，判断是否加锁(被引用),返回true已被加锁，不可以删除，false,未被加锁，可以删除
		if(!isLock("t_base_department", id)){
			boolean isAllowDel = super.canTableDataDelete("t_base_department", id);//true 允许删除，false 不允许删除
			if(isAllowDel){
				boolean flag = departMentService.deleteDepartMent(id);
				addLog("删除部门 编号:"+id+"部门类",flag);
				if(flag){
					sucNum++;
				}
				else{
					failNum++;
				}
			}
			else{
				unAllowDel++;
			}
		}
		else{
			lockNum++;
		}
		retMap.put("sucNum", sucNum);
		retMap.put("failNum", failNum);
		retMap.put("lockNum", lockNum);
		retMap.put("unAllowDel", unAllowDel);
		addJSONObject(retMap);
		return "success";
	}
	
	/**
	 * 删除整个部门类，删除前检验是否被引用(加锁)
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-25
	 */
	@UserOperateLog(key="Department",type=4,value="删除部门")
	public String deleteDepartMentAll()throws Exception{
		String pid=request.getParameter("id");
		int sucNum = 0,failNum = 0,lockNum = 0,unAllowDel = 0;
		Map retMap = new HashMap();
		List<DepartMent> deptList = departMentService.getPidAllDeptList(pid);
		if(deptList.size() != 0){
			for(DepartMent departMent:deptList){
				//删除部门时，判断是否网络互斥,返回true已被加锁，不可以删除，false,未被加锁，可以删除
				if(!isLock("t_base_department", departMent.getId())){
					boolean isAllowDel = super.canTableDataDelete("t_base_department", departMent.getId());//true 允许删除，false 不允许删除
					if(isAllowDel){
						boolean flag = departMentService.deleteDepartMent(departMent.getId());
						if(flag){
							sucNum++;
						}
						else{
							failNum++;
						}
					}
					else{
						unAllowDel++;
					}
				}
				else{
					lockNum++;
				}
			}
		}
		retMap.put("sucNum", sucNum);
		retMap.put("failNum", failNum);
		retMap.put("lockNum", lockNum);
		retMap.put("unAllowDel", unAllowDel);
		addJSONObject(retMap);
		return "success";
	}
	
	/**
	 * 禁用整个部门类
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-25
	 */
	@UserOperateLog(key="Department",type=0,value="禁用父级部门")
	public String disableDepartMentAll()throws Exception{
		String id=request.getParameter("id");
		int lockNum = 0,sucNum = 0,failNum = 0;
		List<DepartMent> deptList = departMentService.getPidAllDeptList(id);
		if(deptList.size() != 0){
			for(DepartMent departMent:deptList){
				//判断数据是否被加锁,True已被加锁。Fasle未加锁。（网络互斥）
				if(!isLock("t_base_department", departMent.getId())){
                    boolean flag = departMentService.disableDepartMent(departMent.getId());
                    if(flag){
                        sucNum++;
                    }
                    else{
                        failNum++;
                    }
				}
				else{
					lockNum++;
				}
			}
		}
		Map retMap = new HashMap();
		retMap.put("lockNum", lockNum);
		retMap.put("sucNum", sucNum);
		retMap.put("failNum", failNum);
		addJSONObject(retMap);
		return "success";
	}
	
	/**
	 * 启用整个部门类
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-25
	 */
	@UserOperateLog(key="Department",type=0,value="")
	public String enableDepartMentAll()throws Exception{
		String id = request.getParameter("id");
		int sucNum = 0, failNum = 0, invNum = 0;
		List<DepartMent> deptList = departMentService.getPidAllDeptList(id);
		if(deptList.size() != 0){
			for(DepartMent departMent : deptList){
				if(!"2".equals(departMent.getState()) && !"0".equals(departMent.getState())){//当状态不为保存或禁用
					invNum++;
				}
				else{
					boolean flag = departMentService.enableDepartMent(departMent.getId());
					if(flag){
						sucNum++;
					}
					else{
						failNum++;
					}
				}
			}
		}
		Map retMap = new HashMap();
		retMap.put("invNum", invNum);
		retMap.put("sucNum", sucNum);
		retMap.put("failNum", failNum);
		addJSONObject(retMap);
		return "success";
	}

	/**
	 * 禁用部门
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-25
	 */
	@UserOperateLog(key="Department",type=0,value="")
	public String disableDepartMent()throws Exception{
		String id=request.getParameter("id");
		int lockNum = 0,sucNum = 0,failNum = 0;
		//判断数据是否被加锁,True已被加锁。Fasle未加锁。
		if(!isLock("t_base_department", id)){
			boolean flag=departMentService.disableDepartMent(id);
			addLog("禁用部门 编号:"+id,flag);
			if(flag){
				sucNum++;
			}
			else{
				failNum++;
			}
		}
		else{
			lockNum++;
		}
		Map retMap = new HashMap();
		retMap.put("lockNum", lockNum);
		retMap.put("sucNum", sucNum);
		retMap.put("failNum", failNum);
		addJSONObject(retMap);
		return "success";
	}

	/**
	 * 启用部门
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-25
	 */
	@UserOperateLog(key="Department",type=0,value="")
	public String enableDepartMent()throws Exception{
		String id=request.getParameter("id");
		String pId=request.getParameter("pId");
		boolean retFlag = false;
		Map retMap = new HashMap();
		if(StringUtils.isNotEmpty(pId)){
			DepartMent departMentInfo = departMentService.showDepartMentInfo(pId);
			if(null != departMentInfo){
				if("0".equals(departMentInfo.getState())){//父级部门状态为禁用时，不可以启用子级部门
					retMap.put("pState", "0");
					addJSONObject(retMap);
					return "success";
				}
			}
		}
		boolean flag = departMentService.enableDepartMent(id);
		addLog("启用部门 编号:"+id,flag);
		retMap.put("pState", "1");
		retMap.put("flag", flag);
		addJSONObject(retMap);
		return "success";
	}
	
	/**
	 * 复制部门信息
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-26
	 */
	public String copyDepartMent()throws Exception{
		String id=request.getParameter("id");
		SysParam sysParam = sysParamService.getSysParam("COMPANYNAME");
		String sName="",sId="";
		if(null != sysParam){
			sName = sysParam.getPvalue();
		}
		String state = request.getParameter("state");
		int pLength=0;
		DepartMent departMent=departMentService.showDepartMentInfo(id);
		if(null != departMent){
			if(!"1".equals(departMent.getState())){//该部门状态不为启用时,可复制
				pLength=departMent.getThisid().length();
				if(!"".equals(departMent.getPid())){
					DepartMent departMentInfo=departMentService.showDepartMentInfo(departMent.getPid());
					if(null != departMentInfo){
						sName=departMentInfo.getName();
						sId=departMentInfo.getId();
					}
				}
			}
		}
		int length=getBaseTreeFilesNext("t_base_department",pLength);
		request.setAttribute("state", state);
		request.setAttribute("oldId", id);
		request.setAttribute("departMent", departMent);
		request.setAttribute("sName", sName);
		request.setAttribute("sId", sId);
		request.setAttribute("lenght", length);
		return "success";
	}
	
	/**
	 * 显示导入窗口页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-20
	 */
	public String importExcelPage() throws Exception{
		return "success";
	}
	
	/**
	 * 判断父级及其下级所有的部门是否存在不允许启用或禁用的状态
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-22
	 */
	public String getPidAllDeptList()throws Exception{
		String pId = request.getParameter("pId");
		String gPid = request.getParameter("gPid");
		String type = request.getParameter("type");
		boolean flag = departMentService.getPidAllDeptListIsHold(pId,gPid,type);
		addJSONObject("flag", flag);
		return "success";
	}

	/**
	 * 根据编码获取父级及其所有子级
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 2, 2013
	 */
	public String getPidAllDeptList2()throws Exception{
		String id=request.getParameter("id");
		List<DepartMent> deptList = departMentService.getPidAllDeptList(id);
		addJSONArray(deptList);
		return SUCCESS;
	}
	
	/**
	 * 检查部门名称的唯一性
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-5
	 */
	public String checkSoleName()throws Exception{
		String name = request.getParameter("name");
		boolean flag = departMentService.checkSoleName(name);
		addJSONObject("flag", flag);
		return "success";
	}
}

