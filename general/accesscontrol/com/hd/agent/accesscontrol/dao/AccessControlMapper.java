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

import com.hd.agent.accesscontrol.model.OperateHelp;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.annotations.Param;

import com.hd.agent.accesscontrol.model.AccessColumn;
import com.hd.agent.accesscontrol.model.Authority;
import com.hd.agent.accesscontrol.model.Operate;

/**
 * 权限控制操作dao
 * @author chenwei
 */
public interface AccessControlMapper {
	
	/**
	 * 获取所有权限
	 * @return
	 * @author chenwei 
	 * @date 2012-12-13
	 */
	public List<String> getAllAuthorityList();
	/**
	 * 获取权限对应的操作资源列表
	 * @param authorityid
	 * @return
	 * @author chenwei 
	 * @date 2012-12-13
	 */
	public List<String> getOperateListFromAuthority(@Param("authorityid")String authorityid);
	/**
	 * 获取权限有效的url
	 * @return
	 * @author chenwei 
	 * @date 2013-1-23
	 */
	public List<String> getAllOperateList();
	
	/**
	 * 根据菜单获取菜单下的页面操作列表
	 * @param 
	 * @return
	 * @author chenwei 
	 * @date 2012-12-14
	 */
	public List showOperListByMenu(@Param("pid")String pid);
	/**
	 * 根据状态获取菜单列表
	 * @return
	 * @author chenwei 
	 * @date 2012-12-15
	 */
	public List showMenuList(@Param("state")String state);

    /**
     * 根据用户编号 获取用户拥有的菜单列表
     * @param state
     * @param userid
     * @param buttontype
     * @return
     * @author chenwei
     * @date 2015-03-01
     */
    public List showMenuTreeByUserid(@Param("state")String state,@Param("userid")String userid,@Param("buttontype")String buttontype);
    /**
     * 根据角色编号 获取角色拥有的菜单列表
     * @param state
     * @param roleids
     * @return
     * @author chenwei
     * @date 2015-03-01
     */
    public List showMenuTreeByRoleids(@Param("state")String state,@Param("roleids")String roleids);
	/**
	 * 根据用户的权限(角色)获取用户拥有的菜单列表
	 * @param authorityList
	 * @return
	 * @author chenwei 
	 * @date 2013-1-5
	 */
	public List showUserMenuTree(@Param("authorityList")List authorityList);
	/**
	 * 根据用户的权限(角色)获取用户拥有的菜单列表
	 * @param authorityList
	 * @return
	 * @author chenwei
	 * @date 2013-1-5
	 */
	public List showUserOperate(@Param("authorityList")List authorityList);
	/**
	 * 根据状态获取菜单操作列表
	 * @param state
	 * @return
	 * @author chenwei 
	 * @date 2012-12-19
	 */
	public List showOperateTree(@Param("state")String state);
	/**
	 * 获取权限拥有的菜单功能按钮列表
	 * @param authorityid
	 * @return
	 * @author chenwei 
	 * @date 2012-12-20
	 */
	public List showOperateChecked(@Param("authorityid")String authorityid);
	/**
	 * 根据菜单编号获取页面操作列表
	 * @return
	 * @author chenwei 
	 * @date 2012-12-14
	 */
	public List showMenuCloseList();
	/**
	 * 根据map中相关条件获取页面操作列表
	 * @return
	 * @author zhanghonghui
	 * @date 2017-11-29
	 */
	public List showMenuCloseListByMap(Map map);
	/**
	 * 添加功能操作（菜单与页面操作）
	 * @param operate
	 * @return
	 * @author chenwei 
	 * @date 2012-12-14
	 */
	public int addOperate(Operate operate);
	/**
	 * 删除功能操作（菜单与页面操作）
	 * @param operateid
	 * @return
	 * @author chenwei 
	 * @date 2012-12-14
	 */
	public int deleteOperate(@Param("operateid")String operateid);
	/**
	 * 删除菜单时，同时删除菜单下面的页面操作
	 * @param operateid
	 * @return
	 * @author chenwei 
	 * @date 2012-12-15
	 */
	public int deleteOperAfterMenu(@Param("operateid")String operateid);
	/**
	 * 获取功能操作子节点列表
	 * @param operateid
	 * @return
	 * @author chenwei 
	 * @date Sep 11, 2013
	 */
	public List getOperateListByoperateid(@Param("operateid")String operateid);
	/**
	 * 关闭功能操作（菜单与页面操作）
	 * @param operateid
	 * @return
	 * @author chenwei 
	 * @date 2012-12-15
	 */
	public int closeOperate(@Param("operateid")String operateid);
	/**
	 * 根据父节点关闭子节点
	 * @param pid
	 * @return
	 * @author chenwei 
	 * @date Sep 11, 2013
	 */
	public int closeOperateBypid(@Param("pid")String pid);
	/**
	 * 开启功能操作（菜单与页面操作）
	 * @param operateid
	 * @return
	 * @author chenwei 
	 * @date 2012-12-15
	 */
	public int openOperate(@Param("operateid")String operateid);
	/**
	 * 根据编号（字符串组合以英文逗号分割），开启功能操作（菜单与页面操作）
	 * @param operateidarrs
	 * @return
	 * @author chenwei
	 * @date 2012-12-15
	 */
	public int openOperateByIds(@Param("operateidarrs")String operateidarrs);
	/**
	 * 根据编号（字符串组合以英文逗号分割），禁用功能操作（菜单与页面操作）
	 * @param operateidarrs
	 * @return
	 * @author chenwei
	 * @date 2012-12-15
	 */
	public int closeOperateByIds(@Param("operateidarrs")String operateidarrs);
	/**
	 * 获取菜单操作详细信息
	 * @param operateid
	 * @return
	 * @author chenwei 
	 * @date 2012-12-15
	 */
	public Operate showOperateInfo(@Param("operateid")String operateid);
	/**
	 * 修改功能操作（菜单与页面操作）
	 * @param operate
	 * @return
	 * @author chenwei 
	 * @date 2012-12-17
	 */
	public int editOperate(Operate operate);
	/**
	 * 获取权限列表
	 * @return
	 * @author chenwei 
	 * @date 2012-12-17
	 */
	public List showAuthorityList(@Param("state")String state,@Param("dataSql")String dataSql);
	/**
	 * 获取该别名的权限（角色）数量
	 * @param name
	 * @return
	 * @author chenwei 
	 * @date 2013-1-17
	 */
	public int getAuthorityNameCount(@Param("name")String name);
	/**
	 * 获取权限编号
	 * @return
	 * @author chenwei 
	 * @date 2012-12-30
	 */
	public String getAuthorityid();
	/**
	 * 添加权限基本信息
	 * @param authority
	 * @return
	 * @author chenwei 
	 * @date 2012-12-19
	 */
	public int addAuthority(Authority authority);
	/**
	 * 添加权限功能操作关联信息
	 * @param authorityid
	 * @param operateid
	 * @param adduserid
	 * @return
	 * @author chenwei 
	 * @date 2012-12-19
	 */
	public int addAuthOper(@Param("authorityid")String authorityid,@Param("operateid")String operateid,@Param("adduserid")String adduserid);
	/**
	 * 启用或停用权限
	 * @param authorityid
	 * @param state 0停用1启用
	 * @return
	 * @author chenwei 
	 * @date 2012-12-19
	 */
	public int setAuthorityState(@Param("authorityid")String authorityid,@Param("state")String state);
	/**
	 * 删除权限
	 * @param authorityid
	 * @return
	 * @author chenwei 
	 * @date 2012-12-19
	 */
	public int deleteAuthority(@Param("authorityid")String authorityid);
	/**
	 * 删除权限与功能操作关联信息
	 * @param authorityid
	 * @return
	 * @author chenwei 
	 * @date 2012-12-19
	 */
	public int deleteAuthOper(@Param("authorityid")String authorityid);
	/**
	 * 获取权限基本信息
	 * @param authorityid
	 * @return
	 * @author chenwei 
	 * @date 2012-12-19
	 */
	public Authority showAuthorityInfo(@Param("authorityid")String authorityid);
	/**
	 * 根据角色名称获取角色
	 * @param name
	 * @return
	 * @author chenwei 
	 * @date Dec 30, 2013
	 */
	public Authority getAuthorityListByName(@Param("name")String name);
	/**
	 * 修改权限基本信息
	 * @param authority
	 * @return
	 * @author chenwei 
	 * @date 2012-12-20
	 */
	public int editAuthority(Authority authority);
	/**
	 * 添加字段权限
	 * @param accessColumn
	 * @return
	 * @author chenwei 
	 * @date 2012-12-30
	 */
	public int addAccessColumn(AccessColumn accessColumn);
	/**
	 * 添加权限与字段权限控制关联关系
	 * @param authorityid
	 * @param columnid
	 * @return
	 * @author chenwei 
	 * @date 2012-12-30
	 */
	public int addAuthorityCol(@Param("authorityid")String authorityid,@Param("columnid")String columnid,@Param("adduserid")String userid);
	/**
	 * 获取权限拥有的字段权限列表
	 * @param authorityid
	 * @return
	 * @author chenwei 
	 * @date 2012-12-30
	 */
	public List showAuthorityColumnList(@Param("authorityid")String authorityid);
	/**
	 * 删除权限与字段权限的关联关系
	 * @param authorityid
	 * @param columnid
	 * @return
	 * @author chenwei 
	 * @date 2012-12-30
	 */
	public int deleteAuthorityColumn(@Param("authorityid")String authorityid,@Param("columnid")String columnid);
	/**
	 * 删除字段权限
	 * @param columnid
	 * @return
	 * @author chenwei 
	 * @date 2012-12-30
	 */
	public int deleteAccessColumn(@Param("columnid")String columnid);
	/**
	 * 根据权限编号与表名获取字段权限列表
	 * @param map
	 * @return
	 * @author chenwei 
	 * @date 2012-12-30
	 */
	public List showAccessColumnsByAuthoritys(Map map);
	/**
	 * 根据角色编号数组获取角色详细信息列表
	 * @param ids
	 * @return
	 * @author chenwei 
	 * @date Mar 22, 2013
	 */
	public List getAuthorityListByIds(@Param("ids")String[] ids);
	/**
	 * 根据url地址获取菜单或者按钮详细信息
	 * @param url
	 * @return
	 * @author chenwei 
	 * @date Mar 28, 2013
	 */
	public List getOperateByURL(@Param("url")String url);
	/**
	 * 根据角色列表获取可以访问的按钮列表
	 * @param list
	 * @return
	 * @author chenwei 
	 * @date Apr 3, 2013
	 */
	public List getOperateButtonsByAuthority(@Param("list")List list);

    /**
     * 删除菜单帮助内容
     * @param md5url
     * @return
     */
	public int deleteOperateHelpByMD5(@Param("md5url") String md5url);
    /**
     * 保存菜单帮助内容
     * @param opreateHelp
     * @return
     */
	public int saveOperateHelp(OperateHelp opreateHelp);

    /**
     * 获取菜单帮助内容
     * @param operateid
     * @return
     */
    public OperateHelp getOperateHelp(@Param("operateid") String operateid);
    /**
     * 获取菜单帮助内容
     * @param md5url
     * @return
     */
    public OperateHelp getOperateHelpByMD5(@Param("md5url") String md5url);

}
