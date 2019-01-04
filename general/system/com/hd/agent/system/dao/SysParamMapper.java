/**
 * @(#)SysParamMapper.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-19 panxiaoxiao 创建版本
 */
package com.hd.agent.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.SysParam;

/**
 * 系统参数表相关操作
 * 
 * @author panxiaoxiao
 */
public interface SysParamMapper {
	
	/**
	 * 获取系统参数列表
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-19
	 */
	public List getSysParamList();
	
	/**
	 * 根据条件，获取系统参数列表
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-19
	 */
	public List getSysParamPageList(PageMap pageMap);
	
	/**
	 * 根据条件，获取系统参数数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-19
	 */
	public int getSysParamCount(PageMap pageMap);
	
	/**
	 * 获取参数信息
	 * @param paramid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-19
	 */
	public SysParam getSysParamInfo(@Param("paramid")String paramid);
	
	/**
	 * 添加系统参数
	 * @param sysParam
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-19
	 */
	public int insertSysParam(SysParam sysParam);
	
	/**
	 * 修改系统参数
	 * @param sysParam
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-19
	 */
	public int updateSysParam(SysParam sysParam);
	
	/**
	 * 停用系统参数
	 * @param paramid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-19
	 */
	public int disableSysParam(@Param("paramid")String paramid);
	
	/**
	 * 启用系统参数
	 * @param paramid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-20
	 */
	public int enableSysParam(@Param("paramid")String paramid);

	/**
	 * 搜索系统参数名称
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-20
	 */
	public String searchPname(@Param("pname")String pname);
	/**
	 * 根据参数名称pname获取参数详细信息
	 * @param name
	 * @return
	 * @author chenwei 
	 * @date Apr 24, 2013
	 */
	public SysParam getSysParam(@Param("name")String name);
	
	/**
	 * 所属模块获取系统参数
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2016年1月6日
	 */
	public List<SysParam> showSysParamListByModualId(String id);
	/**
	 * 根据系统参数的名称更新系统参数值
	 * @param key
	 * @param string
	 * @author huangzhiqian
	 * @date 2016年1月6日
	 */
	public void updateSysParamBypName(@Param("name")String key, @Param("val")String value);

}

