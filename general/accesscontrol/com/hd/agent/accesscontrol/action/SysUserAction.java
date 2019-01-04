/**
 * @(#)SysUserAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-26 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.action;

import com.hd.agent.accesscontrol.base.AgentSessionContext;
import com.hd.agent.accesscontrol.model.Authority;
import com.hd.agent.accesscontrol.model.SysLoginRule;
import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.accesscontrol.model.UserAuthority;
import com.hd.agent.accesscontrol.service.ISecurityService;
import com.hd.agent.accesscontrol.service.ISysUserService;
import com.hd.agent.basefiles.action.FilesLevelAction;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.service.IDepartMentService;
import com.hd.agent.basefiles.service.IPersonnelService;
import com.hd.agent.basefiles.service.IWorkJobService;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 系统用户Action
 * @author chenwei
 */
public class SysUserAction extends FilesLevelAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7178810254727403100L;
	/**
	 * 系统相关用户信息获取
	 */
	private SessionRegistry sessionRegistry;
	
	/**
	 * 系统用户类型
	 */
	private SysUser user;
	/**
	 * 用户权限类型
	 */
	private UserAuthority userAuthority;
	/**
	 * 用户登录规则
	 */
	private SysLoginRule sysLoginRule;
	
	public UserAuthority getUserAuthority() {
		return userAuthority;
	}

	public void setUserAuthority(UserAuthority userAuthority) {
		this.userAuthority = userAuthority;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}
	
	public SysLoginRule getSysLoginRule() {
		return sysLoginRule;
	}

	public void setSysLoginRule(SysLoginRule sysLoginRule) {
		this.sysLoginRule = sysLoginRule;
	}

	/**
	 * 系统用户service
	 */
	private ISysUserService sysUserService;
	/**
	 * 部门service
	 */
	private IDepartMentService departMentService;
	/**
	 * 人员service
	 */
	private IPersonnelService personnelService;

	/**
	 * 权限service
	 */
	private ISecurityService securityService;
	
	/**
	 * 工作岗位
	 */
	private IWorkJobService workJobService;
	
	public IPersonnelService getPersonnelService() {
		return personnelService;
	}

	public void setPersonnelService(IPersonnelService personnelService) {
		this.personnelService = personnelService;
	}

	public ISysUserService getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(ISysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
	
	public SessionRegistry getSessionRegistry() {
		return sessionRegistry;
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}
	
	public IDepartMentService getDepartMentService() {
		return departMentService;
	}

	public void setDepartMentService(IDepartMentService departMentService) {
		this.departMentService = departMentService;
	}

	public ISecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(ISecurityService securityService) {
		this.securityService = securityService;
	}

	public IWorkJobService getWorkJobService() {
		return workJobService;
	}

	public void setWorkJobService(IWorkJobService workJobService) {
		this.workJobService = workJobService;
	}

	/**
	 * 显示用户列表数组json
	 * @return
	 * @author chenwei 
	 * @date 2012-12-26
	 */
	public String showSysUserJson() throws Exception{
		List list = sysUserService.getSysUserList();
		addJSONArray(list);
		return "success";
	}
	/**
	 * 显示用户列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-31
	 */
	public String showSysUserPage() throws Exception{
		return "success";
	}
	/**
	 * 显示用户列表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-31
	 */
	public String showSysUserList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = sysUserService.showSysUserList(pageMap);
		if(null!=pageData && null!=pageData.getList()){
			List<SysUser> list = pageData.getList();
			for(SysUser sysUser : list){
				boolean isphoneLogin = securityService.isLogin("P_"+sysUser.getUsername());
				boolean isSysLogin = securityService.isLogin(sysUser.getUsername());
				if(isphoneLogin){
					sysUser.setIsPhoneLogin("1");
				}else{
					sysUser.setIsPhoneLogin("0");
				}
				if(isSysLogin){
					sysUser.setIsSysLogin("1");
				}else{
					sysUser.setIsSysLogin("0");
				}
			}
		}
		//分页数据转成json格式传回页面
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 显示用户登录控制页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-22
	 */
	public String showSysUserLoginContorl() throws Exception{
		
		return "success";
	}

	/**
	 * 显示用户选择页面<br/>
	 * 传入的参数如下：<br/>
	 * valueKeyId:存储ID的input或textarea
	 * nameKeyId:存储名称的input或textarea
	 * checkedData：修改时传入的值
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-22
	 */
	public String showUserChooserPage() throws Exception{
		Map map = new HashMap();
		map.put("valueKeyId", "");
		map.put("nameKeyId", "");
		map.put("checkedData","");
		Map querymap = CommonUtils.changeMap(request.getParameterMap());
		map.putAll(querymap);
		//往页面传值
		request.setAttribute("requestMap", map);
		return "success";
	}
	/**
	 * 获取在线用户列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 9, 2013
	 */
	public String getOnlineUserTreeList() throws Exception{
		//获取在线人员用户编号
		List<Object> list = sessionRegistry.getAllPrincipals();
		List<SysUser> userList = new ArrayList<SysUser>();
		for (Object object : list) {
			UserDetails userDetails = (UserDetails) object;
			String username = userDetails.getUsername();
			String username1 = username;
			if(username.startsWith("P_")){
				String[] strArr = username.split("P_");
				username1 = strArr[1];
				SysUser sysUser = sysUserService.getUser(username1);
				if(null!=sysUser){
					SysUser sysObject = (SysUser) CommonUtils.deepCopy(sysUser);
					sysObject.setName(sysUser.getName()+"(手机)");
					userList.add(sysObject);
				}
			}else{
				SysUser sysUser = sysUserService.getUser(username1);
				if(null!=sysUser){
					SysUser sysObject = (SysUser) CommonUtils.deepCopy(sysUser);
					userList.add(sysObject);
				}
			}
		}
//		//获取在线的用户信息列表
//		List<SysUser> userList = null;
//		if(usernameList.size()>0){
//			userList = sysUserService.getUserListByUsernames(usernameList);
//		}else{
//			userList = new ArrayList<SysUser>();
//		}
		
		//获取启用的部门列表
		List<DepartMent> deptList = departMentService.showDepartmentOpenList();
		List treeList = new ArrayList();
		//最顶级父节点
		Map pmap = new HashMap();
		pmap.put("id", "d");
		pmap.put("pId",null);
		String companyname = getSysParamValue("COMPANYNAME");
		pmap.put("name", companyname);
		pmap.put("open", true);
		treeList.add(pmap);
		for(DepartMent departMent : deptList){
			Map map = new HashMap();
			map.put("id", "d"+departMent.getId());
			map.put("pId", "d"+departMent.getPid());
			map.put("name", departMent.getName());
			map.put("open", true);
			map.put("isParent", "true");
			treeList.add(map);
		}
		for (SysUser sysUser : userList) {
			Map map = new HashMap();
			map.put("id", sysUser.getUserid());
			map.put("pId", "d"+sysUser.getDepartmentid());
			map.put("name", sysUser.getName());
			map.put("open", false);
			if("0".equals(sysUser.getSex())){
				map.put("icon", "image/womanOn.gif");
			}else{
				map.put("icon", "image/manOn.gif");
			}
			treeList.add(map);
		}
		addJSONArray(treeList);
		return "success";
	}
	/**
	 * 获取全部在线人员列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 11, 2013
	 */
	public String getAllUserTreeList() throws Exception{
		//获取在线人员用户编号
		List<Object> list = sessionRegistry.getAllPrincipals();
		Map userMap = new HashMap();
		for (Object object : list) {
			UserDetails userDetails = (UserDetails) object;
			String username = userDetails.getUsername();
			userMap.put(username, true);
		}

		//获取启用的部门列表
		List<DepartMent> deptList = departMentService.showDepartmentOpenList();
		List treeList = new ArrayList();
		for(DepartMent departMent : deptList){
			if(departMent.getId().equals(departMent.getThisid())){
				List<SysUser> userList = sysUserService.getSysUserListByDeptid(departMent.getId());
				int loginCount = 0;
				for (SysUser sysUser : userList) {
					Map map = new HashMap();
					map.put("id", sysUser.getUserid());
					map.put("pId", "d"+departMent.getId());
					map.put("name", sysUser.getName());
					map.put("open", false);
					if("0".equals(sysUser.getSex())){
						if(userMap.containsKey(sysUser.getUsername())){
							loginCount ++;
							map.put("icon", "image/womanOn.png");
							treeList.add(map);
						}
                        if (userMap.containsKey("P_" + sysUser.getUsername())) {
                            Map pmap = (Map) CommonUtils.deepCopy(map);
                            pmap.put("name", sysUser.getName() + "(手机)");
                            loginCount++;
                            pmap.put("icon", "image/womanOn.png");
                            treeList.add(pmap);
                        }
					}else{
						if(userMap.containsKey(sysUser.getUsername())){
							loginCount ++;
							map.put("icon", "image/manOn.png");
							treeList.add(map);
						}
                        if (userMap.containsKey("P_" + sysUser.getUsername())) {
                            Map pmap = (Map) CommonUtils.deepCopy(map);
                            pmap.put("name", sysUser.getName() + "(手机)");
                            loginCount++;
                            pmap.put("icon", "image/manOn.png");
                            treeList.add(pmap);
                        }
					}
				}
				for (SysUser sysUser : userList) {
					Map map = new HashMap();
					map.put("id", sysUser.getUserid());
					map.put("pId", "d"+departMent.getId());
					map.put("name", sysUser.getName());
					map.put("open", false);
					if("0".equals(sysUser.getSex())){
						if(!userMap.containsKey(sysUser.getUsername())){
							map.put("icon", "image/womanOff.png");
							treeList.add(map);
						}
					}else{
						if(!userMap.containsKey(sysUser.getUsername())){
							map.put("icon", "image/manOff.png");
							treeList.add(map);
						}
					}
				}
				Map dmap = new HashMap();
				dmap.put("id", "d"+departMent.getId());
				dmap.put("pId", "d"+departMent.getPid());
				dmap.put("name", departMent.getName()+"("+loginCount+"/"+userList.size()+")");
				dmap.put("isParent", "true");
				treeList.add(dmap);
			}
		}
		List<SysUser> userList = sysUserService.getSysUserListByDeptid("");
		int loginCount = 0;
		for (SysUser sysUser : userList) {
			Map map = new HashMap();
			map.put("id", sysUser.getUserid());
			map.put("pId", "d99");
			map.put("name", sysUser.getName());
			map.put("open", false);
			if("0".equals(sysUser.getSex())){
				if(userMap.containsKey(sysUser.getUsername())){
					loginCount ++;
					map.put("icon", "image/womanOn.png");
				}else{
					map.put("icon", "image/womanOff.png");
				}
                treeList.add(map);
                if (userMap.containsKey("P_" + sysUser.getUsername())) {
                    Map pmap = (Map) CommonUtils.deepCopy(map);
                    pmap.put("name", sysUser.getName() + "(手机)");
                    loginCount++;
                    pmap.put("icon", "image/womanOn.png");
                    treeList.add(pmap);
                }
			}else{
				if(userMap.containsKey(sysUser.getUsername())){
					loginCount ++;
					map.put("icon", "image/manOn.png");
				}else{
					map.put("icon", "image/manOff.png");
				}
                treeList.add(map);
                if (userMap.containsKey("P_" + sysUser.getUsername())) {
                    Map pmap = (Map) CommonUtils.deepCopy(map);
                    pmap.put("name", sysUser.getName() + "(手机)");
                    loginCount++;
                    pmap.put("icon", "image/manOn.png");
                    treeList.add(pmap);
                }
			}
		}
		Map dmap = new HashMap();
		dmap.put("id", "d99");
		dmap.put("pId", "d");
		dmap.put("name", "未指定部门("+loginCount+"/"+userList.size()+")");
		dmap.put("isParent", "true");
		treeList.add(dmap);
		addJSONArray(treeList);
		return "success";
	}
	
	/**
	 * 分配系统用户
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-12
	 */
	@UserOperateLog(key="SysUser",type=2)
	public String allotSystemUser()throws Exception{
		int failNum = 0,sucNum = 0,unSucNum = 0,existNum = 0;
		String persIDsStr = request.getParameter("persIDsStr");
		Map paramMap = new HashMap();
		if(StringUtils.isNotEmpty(persIDsStr)){
			String[] idArr=persIDsStr.split(",");
			for(int i=0;i<idArr.length;i++){
				Personnel personnel = personnelService.showPersonnelInfo(idArr[i]);
				if(null != personnel){
					if(!"1".equals(personnel.getState())){
						failNum++;
					}
					else{
						//验证用户名唯一性，即是否已存在该用户
						boolean isExist = sysUserService.userNameCheck(personnel.getName());
						if(isExist){
							SysUser sysUserInfo = getSysUser();
							paramMap.put("id", idArr[i]);//人员编号
							paramMap.put("adduserid", sysUserInfo.getUserid());
							paramMap.put("password", CommonUtils.MD5("123456"));
							paramMap.put("belongdeptid", personnel.getBelongdeptid());
							boolean flag = sysUserService.addAllotSystemUser(paramMap);
							if(flag){
								sucNum++;
							}
							else{
								unSucNum++;
							}
						}
						else{
							existNum++;
						}
					}
				}
			}
		}
		Map map1 = new HashMap();
		map1.put("failNum", failNum);
		map1.put("sucNum", sucNum);
		map1.put("unSucNum", unSucNum);
		map1.put("existNum", existNum);
		addJSONObject(map1);
		addLog("分配系统用户 编号："+persIDsStr, true);
		return "success";
	}
	
	/**
	 * 显示系统用户新增页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-13
	 */
	public String showSysUserAddPage()throws Exception{
        //系统用户表中已关联的人员数据
        Map map = sysUserService.associatedPersonnelid();
        request.setAttribute("associate", JSONUtils.mapToJsonStr(map));
		return "success";
	}
	
	/**
	 * 系统用户新增
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-14
	 */
	@UserOperateLog(key="sysUser",type=2,value="")
	public String addSysUser()throws Exception{
		//添加人用户编号
		SysUser sysUserInfo = getSysUser();
		user.setAdduserid(sysUserInfo.getUserid());
		user.setPassword(CommonUtils.MD5("123456"));
		userAuthority.setAdduserid(sysUserInfo.getUserid());
		boolean flag = sysUserService.addSysUser(user,userAuthority);
		addJSONObject("flag", flag);
		//添加日志内容
		addLog("新增系统用户 用户名:"+user.getUsername(),flag);
		return "success";
	}
	
	/**
	 * 用户名唯一性检验
	 * @return
	 * @author panxiaoxiao
	 * @date 2013-3-15
	 */
	public String usernameCheck()throws Exception{
		String username = request.getParameter("username");
		boolean flag = sysUserService.userNameCheck(username);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 显示系统用户修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-16
	 */
	public String showSysUserEditPage()throws Exception{
		String lockFlag = "0",userAuthorityStr = "";//加锁，不可以操作
		String userid = request.getParameter("userid");
		SysUser user = sysUserService.showSysUserInfo(userid);
		if(null != user){
			//获取t_sys_user表的字段编辑权限
			Map colMap = getBaseFilesEditWithAuthority("t_sys_user",user);
			//显示修改页面时，给数据加锁
			boolean flag = lockData("t_sys_user", userid);
			if(flag){
				lockFlag = "1";//解锁，可以操作
				request.setAttribute("colMap", colMap);
			}
			else{
				request.setAttribute("colMap", null);
			}
			List<UserAuthority> userAuthorityList = sysUserService.showUserAuthorityInfo(user.getUserid());
			for(int i=0;i<userAuthorityList.size();i++){
                if(!userAuthorityList.get(i).getAuthorityid().equals("")){
                    userAuthorityStr = userAuthorityStr + userAuthorityList.get(i).getAuthorityid() + ",";
                }
			}
            //系统用户表中已关联的人员数据
            Map map = sysUserService.associatedPersonnelid();
            request.setAttribute("associate", JSONUtils.mapToJsonStr(map));
			request.setAttribute("user", user);
			request.setAttribute("lockFlag", lockFlag);
			request.setAttribute("userAuthorityStr", userAuthorityStr);
		}
		return "success";
	}
	
	/**
	 * 修改系统用户信息
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-18
	 */
	@UserOperateLog(key="sysUser",type=3,value="")
	public String editSysUser()throws Exception{
		SysUser sysUserInfo = getSysUser();
		user.setModifyuserid(sysUserInfo.getUserid());
		//user.setPassword(CommonUtils.MD5("123456"));
		userAuthority.setAdduserid(sysUserInfo.getUserid());
		boolean flag = sysUserService.editSysUser(user,userAuthority);
		addJSONObject("flag", flag);
		//添加日志内容
		addLog("修改系统用户 编号:"+user.getUserid(),flag);
		return "success";
	}
	
	/**
	 * 启用系统用户
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-18
	 */
	@UserOperateLog(key="sysUser",type=0,value="")
	public String enableSysUser()throws Exception{
		String ids = request.getParameter("ids");
		String openstr = "",unsucstr = "",msg = "",sucstr = "";
		int opennum = 0,sucnum = 0,unsucnum = 0;
		String[] idArr = ids.split(",");
		for(String userid : idArr){
			SysUser sysUser = sysUserService.showSysUserInfo(userid);
			if("1".equals(sysUser.getState())){
				opennum++;
				if(StringUtils.isEmpty(openstr)){
					openstr = sysUser.getUsername();
				}else{
					openstr += "," + sysUser.getUsername();
				}
			}else{
				boolean flag = sysUserService.enableSysUser(userid);
				if(flag){
					sucnum++;
					if(StringUtils.isEmpty(sucstr)){
						sucstr = sysUser.getUsername();
					}else{
						sucstr += "," + sysUser.getUsername();
					}
				}else{
					unsucnum++;
					if(StringUtils.isEmpty(unsucstr)){
						unsucstr = sysUser.getUsername();
					}else{
						unsucstr += "," + sysUser.getUsername();
					}
				}
			}
		}
		if(StringUtils.isNotEmpty(openstr)){
			if(StringUtils.isEmpty(msg)){
				msg = "用户:" + openstr + "已启用,共" + opennum + "条数据;<br>";
			}else{
				msg += "用户:" + openstr + "已启用,共" + opennum + "条数据;<br>";
			}
		}
		if(StringUtils.isNotEmpty(unsucstr)){
			if(StringUtils.isEmpty(msg)){
				msg = "用户:" + unsucstr + "启用失败,共" + unsucnum + "条数据;<br>";
			}else{
				msg += "用户:" + unsucstr + "启用失败,共" + unsucnum + "条数据;<br>";
			}
		}
		if(StringUtils.isNotEmpty(sucstr)){
			if(StringUtils.isEmpty(msg)){
				msg = "用户:" + sucstr + "启用成功,共" + sucnum + "条数据;<br>";
			}else{
				msg += "用户:" + sucstr + "启用成功,共" + sucnum + "条数据;<br>";
			}
		}
		if(sucnum > 0){
			//添加日志内容
			addLog("启用系统用户 编号:"+sucstr,true);
		}
		addJSONObject("msg", msg);
		return "success";
	}
	
	/**
	 * 禁用系统用户
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-18
	 */
	@UserOperateLog(key="sysUser",type=0,value="")
	public String disableSysUser()throws Exception{
		String ids = request.getParameter("ids");
		String closestr = "",unsucstr = "",msg = "",sucstr = "";
		int closenum = 0,sucnum = 0,unsucnum = 0;
		String[] idArr = ids.split(",");
		for(String userid : idArr){
			SysUser sysUser = sysUserService.showSysUserInfo(userid);
			if("0".equals(sysUser.getState())){
				closenum++;
				if(StringUtils.isEmpty(closestr)){
					closestr = sysUser.getUsername();
				}else{
					closestr += "," + sysUser.getUsername();
				}
			}else{
				boolean flag = sysUserService.disableSysUser(userid);
				if(flag){
					sucnum++;
					if(StringUtils.isEmpty(sucstr)){
						sucstr = sysUser.getUsername();
					}else{
						sucstr += "," + sysUser.getUsername();
					}
				}else{
					unsucnum++;
					if(StringUtils.isEmpty(unsucstr)){
						unsucstr = sysUser.getUsername();
					}else{
						unsucstr += "," + sysUser.getUsername();
					}
				}
			}
		}
		if(StringUtils.isNotEmpty(closestr)){
			if(StringUtils.isEmpty(msg)){
				msg = "用户:" + closestr + "已禁用,共" + closenum + "条数据;<br>";
			}else{
				msg += "用户:" + closestr + "已禁用,共" + closenum + "条数据;<br>";
			}
		}
		if(StringUtils.isNotEmpty(unsucstr)){
			if(StringUtils.isEmpty(msg)){
				msg = "用户:" + unsucstr + "禁用失败,共" + unsucnum + "条数据;<br>";
			}else{
				msg += "用户:" + unsucstr + "禁用失败,共" + unsucnum + "条数据;<br>";
			}
		}
		if(StringUtils.isNotEmpty(sucstr)){
			if(StringUtils.isEmpty(msg)){
				msg = "用户:" + sucstr + "禁用成功,共" + sucnum + "条数据;<br>";
			}else{
				msg += "用户:" + sucstr + "禁用成功,共" + sucnum + "条数据;<br>";
			}
		}
		if(sucnum > 0){
			//添加日志内容
			addLog("禁用系统用户 编号:"+sucstr,true);
		}
		addJSONObject("msg", msg);
		return "success";
	}
	
	/**
	 * 删除系统用户
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-18
	 */
	@UserOperateLog(key="sysUser",type=4,value="")
	public String deleteSysUser()throws Exception{
		String ids = request.getParameter("ids");
		String openstr = "",unsucstr = "",msg = "",sucstr = "",lockstr = "";
		int opennum = 0,sucnum = 0,unsucnum = 0,locknum = 0;
		String[] idArr = ids.split(",");
		for(String userid : idArr){
			SysUser sysUser = sysUserService.showSysUserInfo(userid);
			if("1".equals(sysUser.getState())){
				opennum++;
				if(StringUtils.isEmpty(openstr)){
					openstr = sysUser.getUsername();
				}else{
					openstr += "," + sysUser.getUsername();
				}
			}else{
				//删除系统用户时，判断是否加锁(被引用),返回true已被加锁，不可以删除，false,未被加锁，可以删除
				boolean lockFlag = isLock("t_sys_user", userid);
				if(!lockFlag){
					boolean flag = sysUserService.deleteSysUser(userid);
					if(flag){
						sucnum++;
						if(StringUtils.isEmpty(sucstr)){
							sucstr = sysUser.getUsername();
						}else{
							sucstr += "," + sysUser.getUsername();
						}
					}else{
						unsucnum++;
						if(StringUtils.isEmpty(unsucstr)){
							unsucstr = sysUser.getUsername();
						}else{
							unsucstr += "," + sysUser.getUsername();
						}
					}
				}
				else{
					locknum++;
					if(StringUtils.isEmpty(lockstr)){
						lockstr = sysUser.getUsername();
					}else{
						lockstr += "," + sysUser.getUsername();
					}
				}
			}
		}
		if(StringUtils.isNotEmpty(openstr)){
			if(StringUtils.isEmpty(msg)){
				msg = "用户:" + openstr + "为启用状态,不允许删除,共" + opennum + "条数据;<br>";
			}else{
				msg += "用户:" + openstr + "为启用状态,不允许删除,共" + opennum + "条数据;<br>";
			}
		}
		if(StringUtils.isNotEmpty(lockstr)){
			if(StringUtils.isEmpty(msg)){
				msg = "用户:" + lockstr + "被引用(加锁),无法删除,共" + locknum + "条数据;<br>";
			}else{
				msg += "用户:" + lockstr + "被引用(加锁),无法删除,共" + locknum + "条数据;<br>";
			}
		}
		if(StringUtils.isNotEmpty(unsucstr)){
			if(StringUtils.isEmpty(msg)){
				msg = "用户:" + unsucstr + "删除失败,共" + unsucnum + "条数据;<br>";
			}else{
				msg += "用户:" + unsucstr + "删除失败,共" + unsucnum + "条数据;<br>";
			}
		}
		if(StringUtils.isNotEmpty(sucstr)){
			if(StringUtils.isEmpty(msg)){
				msg = "用户:" + sucstr + "删除成功,共" + sucnum + "条数据;<br>";
			}else{
				msg += "用户:" + sucstr + "删除成功,共" + sucnum + "条数据;<br>";
			}
		}
		if(sucnum > 0){
			//添加日志内容
			addLog("删除系统用户 编号:"+sucstr,true);
		}
		addJSONObject("msg", msg);
		return "success";
	}
	
	/**
	 * 获取系统用户信息详情
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-22
	 */
	public String showSysUserInfo()throws Exception{
		String userid = request.getParameter("userid");
		SysUser user = sysUserService.showSysUserInfo(userid);
		Map userMap = new HashMap();
		userMap.put("user", user);
		addJSONObject(userMap);
		return "success";
	}
	
	/**
	 * 显示复制系统用户信息页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-22
	 */
	public String showSysUserCopyPage()throws Exception{
		String userAuthorityStr = "";
		String userid = request.getParameter("userid");
		SysUser user = sysUserService.showSysUserInfo(userid);
		List<UserAuthority> userAuthorityList = sysUserService.showUserAuthorityInfo(user.getUserid());
		for(int i=0;i<userAuthorityList.size();i++){
			userAuthorityStr = userAuthorityStr + userAuthorityList.get(i).getAuthorityid() + ",";
		}
		request.setAttribute("user", user);
		request.setAttribute("userAuthorityStr", userAuthorityStr);
		return "success";
	}
	
	/**
	 * 复制新增系统用户
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-22
	 */
	public String sysUserCopyAdd()throws Exception{
		//添加人用户编号
		SysUser sysUserInfo = getSysUser();
		user.setAdduserid(sysUserInfo.getUserid());
		user.setPassword(CommonUtils.MD5("123456"));
		userAuthority.setAdduserid(sysUserInfo.getUserid());
		boolean flag = sysUserService.addSysUser(user,userAuthority);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 重置系统用户密码
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-22
	 */
	@UserOperateLog(key="sysUser",type=0,value="")
	public String resetSysUserPwd()throws Exception{
		String ids = request.getParameter("ids");
		String pwd = CommonUtils.MD5("123456");
		String[] idarr = ids.split(",");
		boolean flag = false;
		for(String userid : idarr){
			Map map = new HashMap();
			map.put("userid", userid);
			map.put("password", pwd);
			flag = sysUserService.editResetSysUserPwd(map);
		}
		//添加日志内容
		addLog("系统用户 编号:"+ids+"密码重置",flag);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 显示系统用户信息详情页面
	 * @return
	 * @author panxiaoxiao
	 * @date 2013-3-25
	 */
	public String showSysUserInfoPage()throws Exception{
		String userAuthorityStr="";
		String userid = request.getParameter("userid");
		SysUser user = sysUserService.showSysUserInfo(userid);
		if(user != null){
			List<UserAuthority> userAuthorityList = sysUserService.showUserAuthorityInfo(user.getUserid());
			for(int i=0;i<userAuthorityList.size();i++){
				userAuthorityStr = userAuthorityStr + userAuthorityList.get(i).getAuthorityid() + ",";
			}
			request.setAttribute("userAuthorityStr", userAuthorityStr);
			request.setAttribute("user", user);
		}
		//String macAddress = getMACAddress();
		//System.out.print(System.getProperty("os.name"));
		return "success";
	}
	
	/**
	 * 显示密码修改页面 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-27
	 */
	public String showModifyPwdPage()throws Exception{
		String userid = request.getParameter("userid");
		SysUser user = sysUserService.showSysUserInfo(userid);
		request.setAttribute("user", user);
		return "success";
	}
	
	
	/**
	 * 修改密码
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-27
	 */
	@UserOperateLog(key="SysUser",type=3)
	public String modifySysUserPwd()throws Exception{
		SysUser userInfo = sysUserService.showSysUserInfo(user.getUserid());
		boolean flag = false;
		String Mes = "";
		Map retMap = new HashMap();
		if(null != userInfo){
			if(CommonUtils.MD5(user.getPassword()).equals(userInfo.getPassword())){//输入的密码与数据库密码比较，是否相同，相同则可以修改
				Map paramMap = new HashMap();
				paramMap.put("userid", user.getUserid());
				paramMap.put("newPwd", CommonUtils.MD5(user.getNewpassword()));
				flag = sysUserService.modifySysUserPwd(paramMap);
			}
			else{
				Mes="原密码错误,";
			}
		}
		else{
			Mes="数据异常,";
		}
		retMap.put("Mes", Mes);
		retMap.put("flag", flag);
		addJSONObject(retMap);
		addLog("系统用户密码修改 编号："+user.getUserid(), flag);
		return "success";
	}
	
	/**
	 * 获取权限列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-30
	 */
	public String showAuthorityListInSysUser() throws Exception{
		List list = securityService.showAuthorityList("1");
		addJSONArray(list);
		return "success";
	}
	
	/**
	 * 显示以选择的用户（权限）列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-8
	 */
	public String showAuthorityListTrue()throws Exception{
		String userid = request.getParameter("userid");
		//根据用户编号userid获取权限编号authorityid列表
		List<Authority> authoritiesList = sysUserService.getAuthoritiesListByUserid(userid);
		addJSONArray(authoritiesList);
		return "success";
	}
	/**
	 * 获取用户登录控制规则列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月23日
	 */
	public String showSysLoginRuleList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = sysUserService.showSysLoginRuleList(pageMap);
		//分页数据转成json格式传回页面
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 显示用户登录规则控制添加页面 
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月23日
	 */
	public String showSysLoginRuleAddPage() throws Exception{
		return "success";
	}
	/**
	 * 添加用户登录规则
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月24日
	 */
	@UserOperateLog(key="SysUser",type=2)
	public String addSysLoginRule() throws Exception{
		String userid = request.getParameter("userid");
		Map map = CommonUtils.changeMap(request.getParameterMap());
		boolean flag = sysUserService.addSysLoginRule(map);
		addJSONObject("flag", flag);
		addLog("添加用户登录规则 编号："+userid, flag);
		return "success";
	}
	/**
	 * 删除登录规则
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月25日
	 */
	@UserOperateLog(key="SysUser",type=4)
	public String deleteSysLoginRule() throws Exception{
		String ids = request.getParameter("ids");
		boolean flag = sysUserService.deleteSysLoginRule(ids);
		addJSONObject("flag", flag);
		addLog("删除用户登录规则 编号："+ids, flag);
		return "success";
	}
	/**
	 * 登录规则修改
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月25日
	 */
	public String showSysLoginRuleEditPage() throws Exception{
		String userid = request.getParameter("userid");
		SysLoginRule sysLoginRule = sysUserService.getSysLoginRuleInfo(userid);
		request.setAttribute("sysLoginRule", sysLoginRule);
		if(null!=sysLoginRule && StringUtils.isNotEmpty(sysLoginRule.getIp())){
			if(sysLoginRule.getIp().indexOf("-")>0){
				String[] ipArr = sysLoginRule.getIp().split("-");
				request.setAttribute("ip1", ipArr[0]);
				request.setAttribute("ip2", ipArr[1]);
			}else{
				request.setAttribute("ip", sysLoginRule.getIp());
			}
		}
		return "success";
	}
	/**
	 * 修改保存登录规则
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月26日
	 */
	@UserOperateLog(key="SysUser",type=3)
	public String updateSysLoginRule() throws Exception{
		String ip1 = request.getParameter("ip1");
		String ip2 = request.getParameter("ip2");
		String ip = request.getParameter("ip");
		boolean flag = false;
		if(null!=sysLoginRule){
			if("1".equals(sysLoginRule.getLogintype()) && StringUtils.isNotEmpty(ip1)&&StringUtils.isNotEmpty(ip2)){
				sysLoginRule.setIp(ip1+"-"+ip2);
			}else if("3".equals(sysLoginRule.getLogintype())){
				sysLoginRule.setIp(ip);
			}
			flag = sysUserService.updateSysLoginRule(sysLoginRule);
		}
		addJSONObject("flag", flag);
		addLog("修改用户登录规则 编号："+sysLoginRule.getUserid(), flag);
		return "success";
	}
	/**
	 * 踢出用户
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月21日
	 */
	@UserOperateLog(key="SysUser",type=0)
	public String kickOutSysUser() throws Exception{
		String ids = request.getParameter("ids");
		String[] idarr = ids.split(",");
		boolean flag = false;
		String msg = "";
		for(String userid : idarr){
			SysUser sysUser = getSysUserById(userid);
			if(null!=sysUser){
				boolean kickflag = kikcOutUser(sysUser.getUsername());
				if(kickflag){
					msg += sysUser.getName()+",";
					flag = true;
				}
			}
		}
		if(StringUtils.isNotEmpty(msg)){
			msg +="被踢出";
		}
		//添加日志内容
		addLog("系统用户"+msg,flag);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 根据用户名踢出用户
	 * @param username
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月21日
	 */
	public boolean kikcOutUser(String username) throws Exception{
		boolean flag = false;
		//获取在线人员用户编号
		List<Object> list = sessionRegistry.getAllPrincipals();
		for (Object object : list) {
			UserDetails userDetails = (UserDetails) object;
			String name = userDetails.getUsername();
			if(name.equals(username) || name.equals("P_"+username)){
				List<SessionInformation> sessionInformationList = sessionRegistry.getAllSessions(object, false); 
				if (sessionInformationList!=null) {   
	                for (int j=0; j<sessionInformationList.size(); j++) {  
	                    sessionInformationList.get(j).expireNow();  
	                    sessionRegistry.removeSessionInformation(sessionInformationList.get(j).getSessionId());
	                    //清除session
	                    HttpSession session = AgentSessionContext.getSession(sessionInformationList.get(j).getSessionId());
	                    if(null!=session){
	                    	session.invalidate();
	                    }
	                }  
	                flag = true;
	            } 
				break;
			}
		}
		return flag;
	}

    /**
     * 强制上传坐标
     * @return
     * @throws Exception
     */
    public String uploadlocation() throws Exception{
        String ids = request.getParameter("ids");
        String[] idarr = ids.split(",");
        boolean flag = false;
        String msg = "";
        for(String userid : idarr){
            SysUser sysUser = getSysUserById(userid);
            if(null!=sysUser){
                boolean addflag = sendPhoneMsg(sysUser.getUserid(),"2","上传定位","上传定位","");
                if(addflag){
                    msg += sysUser.getName()+",";
                    flag = true;
                }
            }
        }
        if(StringUtils.isNotEmpty(msg)){
            msg +="被踢出";
        }
        //添加日志内容
        addLog("系统用户"+msg,flag);
        addJSONObject("flag", flag);
        return "success";
    }
	/**
	 * 修改会计日期
	 * @author lin_xx
	 * @date 2017/5/27
	 */
	public String modifyAccountDate() throws Exception {
		String date = request.getParameter("cdate");
		String IsOpenBusDate = getSysParamValue("IsOpenBusDate");
		if(!(StringUtils.isNotEmpty(IsOpenBusDate)  && "1".equals(IsOpenBusDate))){
			date = CommonUtils.getTodayDateStr();
		}
		//在session中设置登陆日期和账套
		request.getSession().setAttribute(Current_Date, date);
//		if(null!=date && !date.equals(CommonUtils.getTodayDateStr())){
//			//业务日期取当期操作日期
//			request.getSession().setAttribute(Current_Date,date);
//		}
		addJSONObject("flag",true);
		return SUCCESS;
	}

	//显示操作日期页面
	public String showAccountDatePage() throws Exception{
		String currentDate = getCurrentDate();
		request.setAttribute("date",currentDate);
		String IsOpenBusDate=getSysParamValue("IsOpenBusDate");
		request.setAttribute("IsOpenBusDate",IsOpenBusDate);
		return SUCCESS;
	}
}

