/**
 * @(#)IUserService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-13 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.accesscontrol.model.Authority;
import com.hd.agent.accesscontrol.model.SysLoginRule;
import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.accesscontrol.model.UserAuthority;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 用户相关处理
 * @author chenwei
 */
public interface ISysUserService {
	/**
	 * 通过用户名获取用户详细信息
	 * @param username
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-13
	 */
	public SysUser getUser(String username) throws Exception;
	/**
	 * 获取用户列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-26
	 */
	public List getSysUserList() throws Exception;
	/**
	 * 根据部门编号获取用户列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年3月30日
	 */
	public List getSysUserListByDeptid(String deptid) throws Exception;
	/**
	 * 获取系统用户列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-31
	 */
	public PageData showSysUserList(PageMap pageMap) throws Exception;
	
	/**
	 * 分配系统用户
	 * @param idArrStr 分配人员信息id数组集合  addUserid 添加人员id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-8
	 */
	public boolean addAllotSystemUser(Map paramMap)throws Exception;
	/**
	 * 根据用户名列表获取用户对象列表
	 * @param userList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 9, 2013
	 */
	public List getUserListByUsernames(List userList) throws Exception;
	
	/**
	 * 系统用户新增
	 * @param sysUser
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-14
	 */
	public boolean addSysUser(SysUser user,UserAuthority userAuthority)throws Exception;
	
	/**
	 * 用户名的唯一性检验
	 * @param username
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-15
	 */
	public boolean userNameCheck(String username)throws Exception;
	
	/**
	 * 显示系统用户信息
	 * @param userid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-16
	 */
	public SysUser showSysUserInfo(String userid)throws Exception;
	
	/**
	 * 显示系统用户信息,并查询出状态、岗位、性别等信息
	 * @param userid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-16
	 */
	public SysUser showSysUserMoreInfo(String userid)throws Exception;
	
	/**
	 * 显示用户权限信息
	 * @param userauthid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-18
	 */
	public List showUserAuthorityInfo(String userid)throws Exception;
	
	/**
	 * 修改系统用户信息
	 * @param sysUser
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-18
	 */
	public boolean editSysUser(SysUser sysUser,UserAuthority userAuthority)throws Exception;
	
	/**
	 * 启用系统用户
	 * @param userid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-18
	 */
	public boolean enableSysUser(String userid)throws Exception;
	
	/**
	 * 禁用系统用户
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-18
	 */
	public boolean disableSysUser(String userid)throws Exception;
	
	/**
	 * 删除系统用户
	 * @param userid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-18
	 */
	public boolean deleteSysUser(String userid)throws Exception;
	
	/**
	 * 重置密码
	 * @param userid
	 * @return
	 * @throws Excepiton
	 * @author panxiaoxiao 
	 * @date 2013-3-22
	 */
	public boolean editResetSysUserPwd(Map map)throws Exception;
	
	/**
	 * 密码修改
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-27
	 */
	public boolean modifySysUserPwd(Map map)throws Exception;
	
	/**
	 * 根据用户权限id（Authorityid）数组字符串，显示已选择的用户（权限）列表
	 * @param userid 根据用户编号userid获取用户权限列表
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-8
	 */
	public List<Authority> getAuthoritiesListByUserid(String userid)throws Exception;
	/**
	 * 用户登录后更新用户登录信息
	 * @param userid
	 * @param ip
	 * @param mac
	 * @param sid
	 * @return
	 * @author chenwei 
	 * @date 2014年7月10日
	 */
	public boolean updateSysLogin(String userid,String ip,String mac,String sid,String cid) throws Exception;
	/**
	 * 获取系统用户登录规则控制列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月23日
	 */
	public PageData showSysLoginRuleList(PageMap pageMap) throws Exception;
	/**
	 * 添加用户登录规则
	 * @param map
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月24日
	 */
	public boolean addSysLoginRule(Map map) throws Exception;
	/**
	 * 删除登录规则
	 * @param ids
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月25日
	 */
	public boolean deleteSysLoginRule(String ids) throws Exception;
	/**
	 * 获取用户的登录规则详情
	 * @param userid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月25日
	 */
	public SysLoginRule getSysLoginRuleInfo(String userid) throws Exception;
	/**
	 * 修改登录规则
	 * @param sysLoginRule
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月26日
	 */
	public boolean updateSysLoginRule(SysLoginRule sysLoginRule) throws Exception;

    public Map associatedPersonnelid() throws Exception;


	/**
	 * 根据角色名称获取用户列表
	 * @param rolename
	 * @return
	 * @author limin
	 * @date Mar 30, 2016
	 */
	public List getSysUserListByRole(String rolename) throws Exception;
}

