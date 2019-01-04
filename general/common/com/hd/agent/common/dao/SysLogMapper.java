package com.hd.agent.common.dao;

import com.hd.agent.common.model.SysLog;
import com.hd.agent.common.util.PageMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysLogMapper {
	/**
	 * 添加系统用户操作日志
	 * @param sysLog
	 * @return
	 * @author chenwei 
	 * @date 2012-12-22
	 */
	public int addSysLog(SysLog sysLog);
	
	/**
	 * 根据条件查询系统用户日志
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-24
	 */
	public List searchSysLog(PageMap pageMap);
	
	public int getSysLogCount(PageMap pageMap);
	
	/**
	 * 根据id,获取系统日志详情
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-24
	 */
	public SysLog getSysLogInfo(@Param("id")String id);

    /**
     * 根据id,获取系统日志详情数据
     * @param id
     * @return
     * @author panxiaoxiao
     * @date 2012-12-24
     */
    public SysLog getSysLogInfoData(@Param("id")String id);
	
	/**
	 * 根据id,删除日志
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-24
	 */
	public int deleteSysLog(@Param("id")String id);
	/**
	 * 清除一个月之前的系统日志
	 * @return
	 * @author chenwei 
	 * @date Mar 27, 2013
	 */
	public int clearSysLog();

    /**
     * 添加修改记录数据
     * @param id            编号
     * @param olddata       修改前数据
     * @param newdata       修改后数据
     * @return
     */
    public int insertSysLogData(@Param("id")String id,@Param("olddata")String olddata,@Param("newdata")String newdata,@Param(("changedata"))String changedata);
}