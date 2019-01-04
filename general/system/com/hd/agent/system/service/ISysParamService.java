/**
 * @(#)ISysParamService.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-19 panxiaoxiao 创建版本
 */
package com.hd.agent.system.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.SysParam;

/**
 * 
 * 系统参数相关业务逻辑功能接口
 * @author panxiaoxiao
 */
public interface ISysParamService {
	/**
	 * 显示系统参数列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-19
	 */
	public List showSysParamList() throws Exception;
	
	/**
	 * 显示系统参数分页数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-19
	 */
	public PageData showSysParamPageList(PageMap pageMap) throws Exception;
	
	/**
	 * 显示系统参数信息
	 * @param paramid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-19
	 */
	public SysParam showSysParamInfo(String paramid) throws Exception;
	
	/**
	 * 添加系统参数
	 * @param sysParam
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-19
	 */ 
	public boolean addSysParam(SysParam sysParam) throws Exception;
	
	/**
	 * 系统参数更改
	 * @param sysParam
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-20
	 */
	public boolean editSysParam(SysParam sysParam) throws Exception;
	
	/**
	 * 停用系统参数
	 * @param paramid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-20
	 */
	public boolean disableSysParam(String paramid) throws Exception;
	
	/**
	 * 启用系统参数
	 * @param paramid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-20
	 */
	public boolean enableSysParam(String paramid) throws Exception;
	
	/**
	 * 查询系统参数名称
	 * @param pname
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-21
	 */
	public boolean searchPname(String pname) throws Exception;
	/**
	 * 根据参数名获取系统参数
	 * @param name
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 24, 2013
	 */
	public SysParam getSysParam(String name) throws Exception;
	
	/**
	 * 根据所属模块获取系统参数
	 * @param id
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2016年1月6日
	 */
	public List<SysParam> showSysParamListByModualId(String id)throws Exception;
	
	/**
	 * 根据所属模块更新系统参数
	 * @param map
	 * @return
	 * @author huangzhiqian
	 * @date 2016年1月6日
	 */
	public Map updateSysCodeByModual(Map map)throws Exception;
	/**
	 * 打印工具类型JSON
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author zhanghonghui
	 * @date Sep 07, 2017
	 */
	public String getPrintToolTypeJsonCache() throws Exception;
}

