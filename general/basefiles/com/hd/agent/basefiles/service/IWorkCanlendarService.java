/**
 * @(#)IWorkCanlendarService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-28 chenwei 创建版本
 */
package com.hd.agent.basefiles.service;

import java.util.Map;

import com.hd.agent.basefiles.model.WorkCanlendar;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 工作日历service
 * @author chenwei
 */
public interface IWorkCanlendarService {
	/**
	 * 添加工作日历
	 * @param workCanlendar
	 * @param restDays
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-30
	 */
	public boolean addWorkCalendar(WorkCanlendar workCanlendar,String restDays,String year) throws Exception;
	/**
	 * 获取工作日历列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	public PageData getWorkCalendarList(PageMap pageMap) throws Exception;
	/**
	 * 获取工作日历详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	public Map showWorkCanlendarInfo(String id) throws Exception;
	/**
	 * 删除工作日历
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	public boolean deleteWorkCalendar(String id) throws Exception;
	/**
	 * 修改工作日历
	 * @param workCanlendar
	 * @param id
	 * @param restDays
	 * @param year
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	public boolean editWorkCalendar(WorkCanlendar workCanlendar,String id,String restDays,String year) throws Exception;
	/**
	 * 启用工作日历
	 * @param id
	 * @param userid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	public boolean openWorkCalendar(String id,String userid) throws Exception;
	/**
	 * 禁用工作日历
	 * @param id
	 * @param userid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	public boolean closeWorkCalendar(String id,String userid) throws Exception;
	/**
	 * 验证工作日历编码是否重复
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	public boolean checkWorkCalendarId(String id) throws Exception;
}

