/**
 * @(#)IDataAccessService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-20 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.service;

import java.util.List;

import com.hd.agent.accesscontrol.model.Datarule;

/**
 * 
 * 数据权限相关业务逻辑处理
 * @author chenwei
 */
public interface IDataAccessService {
	/**
	 * 添加数据权限控制规则
	 * @param datarule
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-21
	 */
	public boolean addDatarule(Datarule datarule) throws Exception;
	/**
	 * 获取数据权限资源规则列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-21
	 */
	public List showDataruleList() throws Exception;
	/**
	 * 删除数据权限资源规则
	 * @param dataid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-22
	 */
	public boolean deleteDatarule(String dataid) throws Exception;
	/**
	 * 显示数据权限详细信息
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-26
	 */
	public Datarule showDataruleInfo(String dataid) throws Exception;
	/**
	 * 修改数据权限规则
	 * @param datarule
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-7
	 */
	public boolean editDatarule(Datarule datarule) throws Exception;
	/**
	 * 验证资源是否已经配置了数据权限规则
	 * @param tablename
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-9
	 */
	public boolean checkDataruleTable(String tablename) throws Exception;
	/**
	 * 根据用户编号获取该用户的数据权限
	 * @param userid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月18日
	 */
	public List getDataruleListByUserid(String userid) throws Exception;
	/**
	 * 获取全部数据权限启用列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月18日
	 */
	public List getDataruleOpneList(String type) throws Exception;

    /**
     * 根据用户编号和数据权限表名 获取数据权限规则
     * 当未指定数据权限规则时 获取全局默认的数据权限规则
     * @param userid
     * @param tablename
     * @return
     * @throws Exception
     */
    public Datarule getDataRuleInfoByTablenameAndUserid(String userid,String tablename) throws Exception;
}

