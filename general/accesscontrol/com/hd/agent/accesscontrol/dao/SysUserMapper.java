/**
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-12 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.accesscontrol.model.Authority;
import com.hd.agent.accesscontrol.model.SysLoginRule;
import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.util.PageMap;

/**
 * 用户dao
 * @author chenwei
 */
public interface SysUserMapper {
	/**
	 * 获取用户拥有的权限列表
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date 2012-12-13
	 */
	public List<String> getUserAuthorityList(@Param("username")String username);
	
	/**
	 * 通过用户编号获取用户拥有的权限列表
	 * @param userid
	 * @return
	 * @author zhengziyong 
	 * @date Dec 30, 2013
	 */
	public List<String> getUserAuthorityListById(@Param("userid")String userid);
	/**
	 * 通过用户编号获取用户拥有的角色列表
	 * @param userid
	 * @return
	 * @author chenwei 
	 * @date 2014年11月13日
	 */
	public List<Authority> getAuthorityListByUserid(@Param("userid")String userid);
	/**
	 * 根据用户的工作岗位 获取权限列表
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date Apr 13, 2013
	 */
	public List<String> getUserAuthorityListByWorkjob(@Param("username")String username);
	/**
	 * 根据用户名获取用户信息
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date 2012-12-13
	 */
	public SysUser getUser(@Param("username")String username);
	
	/**
	 * 根据用户编号获取用户信息
	 * @param userid
	 * @return
	 * @author zhengziyong 
	 * @date 2013-3-7
	 */
	public SysUser getUserById(String userid);
	
	/**
	 * 获取启用状态下的用户列表
	 * @return
	 * @author chenwei 
	 * @date 2012-12-26
	 */
	public List getSysUserList();
	/**
	 * 根据部门编号获取用户列表
	 * @param deptid
	 * @return
	 * @author chenwei 
	 * @date 2015年3月30日
	 */
	public List getSysUserListByDeptid(@Param("deptid")String deptid);
	/**
	 * 获取未指定部门的用户列表
	 * @return
	 * @author chenwei 
	 * @date 2015年3月30日
	 */
	public List getSysUserListByNoDeptid();
	
	/**
	 * 显示系统用户列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2012-12-31
	 */
	public List showSysUserList(PageMap pageMap);
	/**
	 * 显示系统用户数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2012-12-31
	 */
	public int showSysUserCount(PageMap pageMap);
	
	/**
	 * 分配系统用户
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-11
	 */
	public int addAllotSystemUser(Map map1);
	
	/**
	 * 根据用户名列表获取用户列表
	 * @param list
	 * @return
	 * @author chenwei 
	 * @date Mar 9, 2013
	 */
	public List getUserListByUsernames(@Param("userList")List list);
	/**
	 * 根据角色名称获取用户列表
	 * @param roleid
	 * @return
	 * @author chenwei 
	 * @date Mar 11, 2013
	 */
	public List getSysUserListByRole(@Param("rolename")String rolename);
	/**
	 * 根据角色编号获取用户列表
	 * @param roleid
	 * @return
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public List getSysUserListByRoleid(@Param("roleid")String roleid);
	/**
	 * 根据部门编号获取用户列表
	 * @param deptid
	 * @return
	 * @author chenwei 
	 * @date Mar 11, 2013
	 */
	public List getSysUserListByDept(@Param("deptid")String deptid);
	/**
	 * 根据角色别名和用户编号获取两个的交集
	 * @param rolealias
	 * @param deptid
	 * @return
	 * @author chenwei 
	 * @date Mar 11, 2013
	 */
	public List getSysUserListByRoleAndDept(@Param("rolename")String rolename,@Param("deptid")String deptid);
	/**
	 * 根据角色编号和部门编号获取用户列表
	 * @param rolename
	 * @param deptid
	 * @return
	 * @author chenwei 
	 * @date Oct 6, 2013
	 */
	public List getSysUserListByRoleidAndDeptid(@Param("roleid")String roleid,@Param("deptid")String deptid);
	/**
	 * 根据岗位名称获取用户列表
	 * @param workjobname
	 * @return
	 * @author chenwei 
	 * @date May 6, 2013
	 */
	public List getSysUserListByWorkjob(@Param("workjobname")String workjobname);
	/**
	 * 根据岗位编号获取用户列表
	 * @param workjobid
	 * @return
	 * @author chenwei 
	 * @date May 6, 2013
	 */
	public List getSysUserListByWorkjobid(@Param("workjobid")String workjobid);
	/**
	 * 系统用户新增
	 * @param sysUser
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-14
	 */
	public int addSysUser(SysUser user);
	
	/**
	 * 用户名的唯一性检验
	 * @param username
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-15
	 */
	public int usernameCheck(@Param("username")String username);
	
	/**
	 * 显示系统用户信息
	 * @param userid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-16
	 */
	public SysUser showSysUserInfo(@Param("userid")String userid);
	
	/**
	 * 修改系统用户信息
	 * @param sysUser
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-18
	 */
	public int editSysUser(SysUser sysUser);
	
	/**
	 * 启用系统用户
	 * @param userid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-18
	 */
	public int enableSysUser(@Param("userid")String userid);
	
	/**
	 * 禁用系统用户
	 * @param userid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-18
	 */
	public int disableSysUser(@Param("userid")String userid);
	
	/**
	 * 删除系统用户
	 * @param userid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-18
	 */
	public int deleteSysUser(@Param("userid")String userid);
	
	/**
	 * 根据用户名获取系统用户信息
	 * @param username
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-20
	 */
	public SysUser getUserByUsername(@Param("username")String username);
	
	/**
	 * 重置密码
	 * @param userid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-22
	 */
	public int editResetSysUserPwd(Map map);
	
	/**
	 * 密码修改
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-27
	 */
	public int modifySysUserPwd(Map map);
	
	/**
	 * 根据人员编号批量禁用系统用户
	 * @param perIdArr
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-29
	 */
	public int disableSysUserByPerId(@Param("perIdArr")String[] sarrayList);
	
	/**
	 * 根据人员编号判断是否该人员是否分配系统用户
	 * @param personnelid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-29
	 */
	public SysUser checkSysUserByPerId(@Param("personnelid")String personnelid);
	/**
	 * 获取系统用户信息
	 * @param userid
	 * @return
	 * @author chenwei 
	 * @date 2014年7月26日
	 */
	public SysUser getSysUserByUseridWithoutCache(@Param("userid")String userid);
	/**
	 * 根据关联人员编码获取用户列表数据
	 * @param personnelid
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 28, 2013
	 */
	public List getSysUserListByPersonnelid(@Param("personnelid")String personnelid);
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
	public int updateSysLogin(@Param("userid")String userid,@Param("ip")String ip,@Param("mac")String mac,@Param("sid")String sid,@Param("cid")String cid);
	/**
	 * 获取用户登录规则控制列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年7月23日
	 */
	public List showSysLoginRuleList(PageMap pageMap);
	/**
	 * 获取用户登录规则控制列表数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年7月23日
	 */
	public int showSysLoginRuleListCount(PageMap pageMap);
	/**
	 * 添加用户登录规则
	 * @param sysLoginRule
	 * @return
	 * @author chenwei 
	 * @date 2014年7月24日
	 */
	public int addSysLoginRule(SysLoginRule sysLoginRule);
	/**
	 * 删除用户登录规则
	 * @param userid
	 * @return
	 * @author chenwei 
	 * @date 2014年7月24日
	 */
	public int deleteSysLoginRule(@Param("userid")String userid);
	/**
	 * 获取用户登录规则详情
	 * @param userid
	 * @return
	 * @author chenwei 
	 * @date 2014年7月25日
	 */
	public SysLoginRule getSysLoginRuleInfo(@Param("userid")String userid);
	/**
	 * 登录规则修改
	 * @param sysLoginRule
	 * @return
	 * @author chenwei 
	 * @date 2014年7月26日
	 */
	public int updateSysLoginRule(SysLoginRule sysLoginRule);

	/**
	 * 根据角色和用户编号获取符合的用户
	 * @param roleid
	 * @param userid
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Nov 21, 2017
	 */
	public List getSysUserListByRoleidAndUserid(@Param("roleid")String roleid,@Param("userid")String userid);
}