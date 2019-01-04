/**
 * @(#)ISysLogService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-22 chenwei 创建版本
 */
package com.hd.agent.common.service;

import com.hd.agent.common.model.SysLog;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 系统用户日志操作
 * @author chenwei
 */
public interface ISysLogService {
	/**
	 * 添加系统用户操作日志
	 * @param sysLog
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-22
	 */
	public boolean addSysLog(SysLog sysLog) throws Exception;
	
	/**
	 * 查询系统用户操作日志
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-24
	 */
	public PageData showSearchSysLog(PageMap pageMap) throws Exception;
	
	/**
	 * 显示系统日志详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-24
	 */
	public SysLog showSysLogInfo(String id) throws Exception;

    /**
     * 显示系统日志数据
     * @param id
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2015-11-10
     */
    public SysLog showSysLogInfoData(String id) throws Exception;
	
	/**
	 * 删除系统日志
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-24
	 */
	public boolean deleteSysLog(String id) throws Exception;
	/**
	 * 清楚一个月之前的系统日志
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 27, 2013
	 */
	public boolean clearSysLog() throws Exception;

    /**
     * 添加系统修改数据记录
     * @param id                编号
     * @param olddata           修改前数据
     * @param newdata           修改后数据
     * @param changedata        变更数据
     * @return
     * @throws Exception
     */
    public boolean addSysLogData(String id,String olddata,String newdata,String changedata) throws Exception;
}

