/**
 * @(#)UserAuthorityMapper.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-3-14 panxiaoxiao 创建版本
 */
package com.hd.agent.accesscontrol.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.accesscontrol.model.Authority;
import com.hd.agent.accesscontrol.model.UserAuthority;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public interface UserAuthorityMapper {

	/**
	 * 新增用户权限
	 * @param userAuthority
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-14
	 */
	public int addUserAuthority(UserAuthority userAuthority);
	
	/**
	 * 修改用户权限
	 * @param userAuthority
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-14
	 */
	public int editUserAuthority(UserAuthority userAuthority);
	
	/**
	 * 显示用户权限信息
	 * @param userauthid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-18
	 */
	public List showUserAuthorityInfo(@Param("userid")String userid);
	
	/**
	 * 删除系统用户
	 * @param userid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-18
	 */
	public int deleteUserAuthority(@Param("userid")String userid);
	
	/**
	 * 根据用户编号userid获取用户权限列表
	 * @param userid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-8
	 */
	public List<UserAuthority> getUserAuthoritiesListByUserid(@Param("userid")String userid);
	
	/**
	 * 根据权限id数组获取权限列表
	 * @param authorityidArr
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-8
	 */
	public List<Authority> getAuthorityByAuthorityidArr(@Param("authorityidArr")String[] authorityidArr);
	
	/**
	 * 判断是否存在用户权限
	 * @param userid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 6, 2013
	 */
	public int isExistUserAuthority(String userid);
}

