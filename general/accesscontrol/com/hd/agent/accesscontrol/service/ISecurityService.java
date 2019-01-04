/**
 * @(#)ISecurityService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-12 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.service;

import com.hd.agent.accesscontrol.model.*;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * security权限操作接口
 * 权限初始化与用户认证相关
 * @author chenwei
 */
public interface ISecurityService {
	/**
	 * 获取全部权限与资源的对应关系
	 * @return
	 * @throws Exception
	 */
	public Map<String, Collection<ConfigAttribute>> getResourceMap() throws Exception;
	/**
	 * 通过用户名获取用户拥有的权限
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public Collection<GrantedAuthority> getUserAuthority(String username) throws Exception;
	/**
	 * 根据菜单获取页面操作列表
	 * @param operateid 菜单编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-14
	 */
	public List showOperListByMenu(String operateid) throws Exception;
	/**
	 * 显示菜单列表
	 * @param state 菜单状态
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-15
	 */
	public List showMenuList(String state) throws Exception;

    /**
     * 根据用户编号获取用户拥有的菜单树
     * @param state             菜单状态
     * @param userid            用户编号
     * @param buttontype        是否显示按钮
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2015-03-01
     */
    public List showMenuTreeByUserid(String state,String userid,String buttontype) throws Exception;

    /**
     * 根据角色编号 获取菜单树
     * @param state
     * @param roleids
     * @return
     * @throws Exception
     */
    public List showMenuTreeByRoleids(String state,String roleids) throws Exception;
	/**
	 * 根据用户的权限获取菜单数
	 * @param authorityList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-5
	 */
	public List showUserMenuTree(List authorityList,String easyuiThemeName) throws Exception;

	/**
	 * 根据用户的权限 获取全部菜单和按钮
	 * @param authorityList
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2013-1-5
	 */
	public List showUserOperate(List authorityList,String easyuiThemeName) throws Exception;
	/**
	 * 获取菜单与功能列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-19
	 */
	public List showOperateTree(String state) throws Exception;
	/**
	 * 获取选择的菜单与按钮列表
	 * @param authorityid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-20
	 */
	public List showOperateChecked(String authorityid) throws Exception;
	/**
	 * 显示停用菜单列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-14
	 */
	public List showMenuCloseList() throws Exception;
	/**
	 * 显示停用菜单列表,map中为查询条件
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2017-11-29
	 */
	public List showMenuCloseListByMap(Map conditionMap) throws Exception;
	/**
	 * 添加功能操作数据包括菜单与页面操作
	 * @param operate
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-14
	 */
	public boolean addOperate(Operate operate) throws Exception;
	/**
	 * 删除功能操作数据包括菜单与页面操作
	 * @param operateid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-14
	 */
	public boolean deleteOperate(String operateid) throws Exception;
	/**
	 * 关闭功能操作数据包括菜单与页面操作
	 * @param operateid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-15
	 */
	public boolean setOperateClose(String operateid) throws Exception;
	/**
	 * 开启功能操作包括菜单与页面操作
	 * @param operateid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-15
	 */
	public boolean setOperateOpen(String operateid) throws Exception;
	/**
	 * 根据编号（字符串组合以英文逗号分割），启用功能操作（菜单与页面操作）
	 * @param operateidarrs
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2012-12-15
	 */
	public boolean setOperateOpenByIds(String operateidarrs) throws Exception;
	/**
	 * 根据编号（字符串组合以英文逗号分割），禁用功能操作（菜单与页面操作）
	 * @param operateidarrs
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2012-12-15
	 */
	public boolean setOperateCloseByIds(String operateidarrs) throws Exception;
	/**
	 * 获取菜单操作详细信息
	 * @param operateid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-15
	 */
	public Operate showOperateInfo(String operateid) throws Exception;
	/**
	 * 修改功能操作（菜单和页面操作）
	 * @param operate	
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-17
	 */
	public boolean editOperate(Operate operate) throws Exception;
	/**
	 * 获取权限列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-17
	 */
	public List showAuthorityList(String state) throws Exception;
	/**
	 * 获取角色名称数量
	 * @param name
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-17
	 */
	public int getAuthorityNameCount(String name) throws Exception;
	/**
	 * 获取权限编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-30
	 */
	public String getAuthorityid() throws Exception;
	/**
	 * 添加权限
	 * @param authority	权限基本信息
	 * @param operates	功能权限
	 * @param datarules 数据权限
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-19
	 */
	public boolean addAuthority(Authority authority,String operates,String datarules) throws Exception;
	/**
	 * 停用权限
	 * @param authorityid
	 * @return
	 * @author chenwei 
	 * @date 2012-12-19
	 */
	public boolean closeAuthority(String authorityid) throws Exception;
	/**
	 * 启用权限
	 * @param authorityid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-19
	 */
	public boolean openAuthority(String authorityid) throws Exception;
	/**
	 * 删除权限
	 * @param authorityid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-19
	 */
	public boolean deleteAuthority(String authorityid) throws Exception;
	/**
	 * 获取权限基本信息
	 * @param authorityid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-19
	 */
	public Authority showAuthorityInfo(String authorityid) throws Exception;
	/**
	 * 修改权限
	 * @param authority
	 * @param operates
	 * @param datarules
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-20
	 */
	public boolean editAuthority(Authority authority,String operates,String datarules) throws Exception;
	/**
	 * 添加字段权限
	 * @param accessColumn
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-29
	 */
	public boolean addAccessColumn(AccessColumn accessColumn,String authorityid) throws Exception;
	/**
	 * 获取权限拥有的字段权限列表
	 * @param authorityid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-30
	 */
	public List showAuthorityColumnList(String authorityid) throws Exception;
	/**
	 * 删除字段权限
	 * @param authorityid
	 * @param columnid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-30
	 */
	public boolean deleteAccessColumn(String authorityid,String columnid) throws Exception;
	/**
	 * 根据权限编号与表名获取字段权限列表
	 * @param authorityList
	 * @param tablename
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-30
	 */
	public List showAccessColumnsByAuthoritys(List authorityList,String tablename) throws Exception;
	/**
	 * 根据角色编号获取角色信息。
	 * 多个编号用,分割
	 * @param ids
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 22, 2013
	 */
	public List getAuthorityListByIds(String ids) throws Exception;
	/**
	 * 根据角色名称 获取角色信息
	 * @param name
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 30, 2013
	 */
	public Authority getAuthorityListByName(String name) throws Exception;
	/**
	 * 根据URL地址获取菜单与按钮详细信息
	 * @param url
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 28, 2013
	 */
	public Operate getOperateByURL(String url) throws Exception;
	/**
	 * 获取字段权限sql字符串
	 * @param tablename
	 * @param alias
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 30, 2013
	 */
	public String getAccessColumnSqlStr(String tablename,String alias) throws Exception;
	/**
	 * 获取字段权限sql字符串（多表）
	 * @param tableMap
	 * @param columnMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 30, 2013
	 */
	public String getAccessColumnSqlStrByTables(Map tableMap,Map columnMap) throws Exception;
	/**
	 * 根据角色编号，获取能访问的按钮列表
	 * @param authorityList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 3, 2013
	 */
	public List getOperateButtonsByAuthority(List authorityList) throws Exception;
	/**
	 * 获取公司名称
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月20日
	 */
	public String getCompanyName() throws Exception;
	/**
	 * 设置公司名称
	 * @param name
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月20日
	 */
	public boolean updateCompanyName(String name) throws Exception;
	/**
	 * 获取当前用户登录人数
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月21日
	 */
	public UserLoginNum getUserLoginNum() throws Exception;
	/**
	 * 判断当前用户是否已经登录
	 * @param username
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月21日
	 */
	public boolean isLogin(String username) throws Exception;

    /**
     * 保存菜单帮助内容
     * @param opreateHelp
     * @return
     * @throws Exception
     */
	public boolean saveOperateHelp(OperateHelp opreateHelp) throws Exception;

    /**
     * 获取菜单帮助内容
     * @param operateid
     * @return
     * @throws Exception
     */
	public OperateHelp getOperateHelp(String operateid) throws Exception;
    /**
     * 获取菜单帮助内容
     * @param md5url
     * @return
     * @throws Exception
     */
    public OperateHelp getOperateHelpByMD5(String md5url) throws Exception;
}

