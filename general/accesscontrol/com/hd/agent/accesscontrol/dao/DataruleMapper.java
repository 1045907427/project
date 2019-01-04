/**
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-20 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.accesscontrol.model.Datarule;

/**
 * 数据权限资源规则数据库操作
 * @author chenwei
 */
public interface DataruleMapper {
	/**
	 * 添加数据权限规则
	 * @param datarule
	 * @return
	 * @author chenwei 
	 * @date 2012-12-21
	 */
	public int addDatarule(Datarule datarule);
	/**
	 * 获取数据权限资源规则列表
	 * @return
	 * @author chenwei 
	 * @date 2012-12-21
	 */
	public List showDataruleList();
	/**
	 * 删除数据权限资源规则
	 * @param dataid
	 * @return
	 * @author chenwei 
	 * @date 2012-12-22
	 */
	public int deleteDatarule(@Param("dataid")String dataid);
	/**
	 * 根据表名获取数据权限控制规则
	 * @param tablename
	 * @return
	 * @author chenwei 
	 * @date 2012-12-24
	 */
	public Datarule getDatarule(@Param("tablename")String tablename);
	/**
	 * 根据表名和用户编号获取数据权限控制信息
	 * @param tablename
	 * @param userid
	 * @return
	 * @author chenwei 
	 * @date 2014年12月18日
	 */
	public Datarule getDataruleByUserid(@Param("tablename")String tablename,@Param("userid")String userid);
	/**
	 * 根据数据权限 核查是否存在
	 * @param datarule
	 * @return
	 * @author chenwei 
	 * @date 2014年12月18日
	 */
	public Datarule checkDatarule(Datarule datarule);
	/**
	 * 根据编号获取数据权限详细信息
	 * @param dataid
	 * @return
	 * @author chenwei 
	 * @date 2012-12-26
	 */
	public Datarule showDataruleInfo(@Param("dataid")String dataid);
	/**
	 * 修改数据权限规则
	 * @param datarule
	 * @return
	 * @author chenwei 
	 * @date 2013-1-7
	 */
	public int editDatarule(Datarule datarule);
	/**
	 * 验证该表是否配置了数据权限规则
	 * @param tablename
	 * @return
	 * @author chenwei 
	 * @date 2013-1-9
	 */
	public int checkDataruleTable(@Param("tablename")String tablename);
	/**
	 * 根据用户编号获取用户的数据权限列表
	 * @param userid
	 * @return
	 * @author chenwei 
	 * @date 2014年12月18日
	 */
	public List getDataruleListByUserid(@Param("userid")String userid);
	/**
	 * 获取全部数据权限启用列表
	 * @return
	 * @author chenwei 
	 * @date 2014年12月18日
	 */
	public List getDataruleOpneList(@Param("type")String type);
}