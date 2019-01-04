/**
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-2-28 chenwei 创建版本
 */
package com.hd.agent.basefiles.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.basefiles.model.WorkCanlendar;
import com.hd.agent.common.util.PageMap;

/**
 * 工作日历dao
 * @author chenwei
 */
public interface WorkCanlendarMapper {
	/**
	 * 添加工作日历详细信息
	 * @param workCanlendar
	 * @return
	 * @author chenwei 
	 * @date 2013-1-30
	 */
	public int addWorkCalendar(WorkCanlendar workCanlendar);
	/**
	 * 添加工作日历详细情况
	 * @param list
	 * @return
	 * @author chenwei 
	 * @date 2013-1-30
	 */
	public int addWorkCalendarDetail(List list);
	/**
	 * 获取工作日历列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	public List getWorkCalendarList(PageMap pageMap);
	/**
	 * 获取工作日历数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	public int getWorkCalendarCount(PageMap pageMap);
	/**
	 * 获取工作日历信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	public WorkCanlendar showWorkCanlendarInfo(@Param("id")String id);
	/**
	 * 获取工作日历休息日列表
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	public List getWorkCanlendarRestdayList(@Param("id")String id);
	/**
	 * 删除工作日历
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	public int deleteWorkCalendar(@Param("id")String id);
	/**
	 * 删除工作日历明细
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	public int deleteWorkCalendarDetail(@Param("id")String id);
	/**
	 * 修改工作日历信息
	 * @param map
	 * @return
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	public int editWorkCalendar(Map map);
	/**
	 * 根据年度删除工作日历明细
	 * @param id
	 * @param year
	 * @return
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	public int deleteWorkCalendarDetailByYear(@Param("id")String id,@Param("year")String year);
	/**
	 * 修改工作日历明细信息
	 * @param id
	 * @param newid
	 * @return
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	public int updateWorkCalendarDetail(@Param("id")String id,@Param("newid")String newid);
	/**
	 * 启用工作日历
	 * @param id
	 * @param userid
	 * @return
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	public int openWorkCalendar(@Param("id")String id,@Param("userid")String userid);
	/**
	 * 禁用工作日历
	 * @param id
	 * @param userid
	 * @return
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	public int closeWorkCalendar(@Param("id")String id,@Param("userid")String userid);
	/**
	 * 延迟工作日历编码是否重复
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	public int checkWorkCalendarId(@Param("id")String id);
}