/**
 * @(#)WorkCanlendarServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-28 chenwei 创建版本
 */
package com.hd.agent.basefiles.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.dao.WorkCanlendarMapper;
import com.hd.agent.basefiles.model.WorkCanlendar;
import com.hd.agent.basefiles.model.WorkCanlendarDetail;
import com.hd.agent.basefiles.service.IWorkCanlendarService;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 工作日历service实现类
 * @author chenwei
 */
public class WorkCanlendarServiceImpl extends BaseServiceImpl implements IWorkCanlendarService {
	/**
	 * 工作日历dao
	 */
	private WorkCanlendarMapper workCanlendarMapper;

	public WorkCanlendarMapper getWorkCanlendarMapper() {
		return workCanlendarMapper;
	}

	public void setWorkCanlendarMapper(WorkCanlendarMapper workCanlendarMapper) {
		this.workCanlendarMapper = workCanlendarMapper;
	}

	@Override
	public boolean addWorkCalendar(WorkCanlendar workCanlendar, String restDays,String year)
			throws Exception {
		int i = workCanlendarMapper.addWorkCalendar(workCanlendar);
		int yearNum = Integer.parseInt(year);
		int days = 365;
		if ((yearNum % 4 == 0 && yearNum % 100 != 0) || yearNum % 400 == 0) {
			days = 366;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.set(yearNum, 0, 1);
		List list = new ArrayList();
		String[] restDayList = restDays.split(",");
		for(int j=0;j<days;j++){
			String date = "";
			if(j==0){
				date = CommonUtils.dataToStr(calendar.getTime(), "yyyy-MM-dd");
			}else{
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				date = CommonUtils.dataToStr(calendar.getTime(), "yyyy-MM-dd");
			}
			WorkCanlendarDetail workCanlendarDetail = new WorkCanlendarDetail();
			workCanlendarDetail.setCanlendarid(workCanlendar.getId());
			workCanlendarDetail.setDate(date);
			boolean flag = false;
			for(String rest : restDayList){
				if(date.equals(rest)){
					flag = true;
					break;
				}
			}
			if(flag){
				workCanlendarDetail.setIswork("0");
			}else{
				workCanlendarDetail.setIswork("1");
			}
			workCanlendarDetail.setYear(year);
			list.add(workCanlendarDetail);
		}
		int z = workCanlendarMapper.addWorkCalendarDetail(list);
		return i>0&&z==days;
	}

	@Override
	public PageData getWorkCalendarList(PageMap pageMap) throws Exception {
		//数据权限
		String sql = getDataAccessRule("t_base_workcanlendar",null);
		pageMap.setDataSql(sql);
		PageData pageData = new PageData(workCanlendarMapper
				.getWorkCalendarCount(pageMap), workCanlendarMapper
				.getWorkCalendarList(pageMap), pageMap);
		return pageData;
	}

	@Override
	public Map showWorkCanlendarInfo(String id) throws Exception {
		WorkCanlendar workCanlendar = workCanlendarMapper.showWorkCanlendarInfo(id);
		List list = workCanlendarMapper.getWorkCanlendarRestdayList(id);
		Map map = new HashMap();
		map.put("workCanlendar", workCanlendar);
		map.put("list", list);
		return map;
	}

	@Override
	public boolean deleteWorkCalendar(String id) throws Exception {
		int i = workCanlendarMapper.deleteWorkCalendar(id);
		workCanlendarMapper.deleteWorkCalendarDetail(id);
		return i>0;
	}

	@Override
	public boolean editWorkCalendar(WorkCanlendar workCanlendar, String id,
			String restDays, String year) throws Exception {
		//修改工作日历基本信息
		Map map = new HashMap();
		map.put("record", workCanlendar);
		map.put("id", id);
		int i = workCanlendarMapper.editWorkCalendar(map);
		
		//根据年度删除工作日历明细
		workCanlendarMapper.deleteWorkCalendarDetailByYear(id, year);
		//当工作日历编码修改后，修改对应的明细数据
		if(!id.equals(workCanlendar.getId())){
			workCanlendarMapper.updateWorkCalendarDetail(id, workCanlendar.getId());
		}
		//添加新的该年度工作日历明细
		List list = new ArrayList();
		String[] restDayList = restDays.split(",");
		int yearNum = Integer.parseInt(year);
		Calendar calendar = Calendar.getInstance();
		calendar.set(yearNum, 0, 1);
		int days = 365;
		if ((yearNum % 4 == 0 && yearNum % 100 != 0) || yearNum % 400 == 0) {
			days = 366;
		}
		for(int j=0;j<days;j++){
			String date = "";
			if(j==0){
				date = CommonUtils.dataToStr(calendar.getTime(), "yyyy-MM-dd");
			}else{
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				date = CommonUtils.dataToStr(calendar.getTime(), "yyyy-MM-dd");
			}
			WorkCanlendarDetail workCanlendarDetail = new WorkCanlendarDetail();
			workCanlendarDetail.setCanlendarid(workCanlendar.getId());
			workCanlendarDetail.setDate(date);
			boolean flag = false;
			for(String rest : restDayList){
				if(date.equals(rest)){
					flag = true;
					break;
				}
			}
			if(flag){
				workCanlendarDetail.setIswork("0");
			}else{
				workCanlendarDetail.setIswork("1");
			}
			workCanlendarDetail.setYear(year);
			list.add(workCanlendarDetail);
		}
		int z = workCanlendarMapper.addWorkCalendarDetail(list);
		
		return i>0&&z==days;
	}

	@Override
	public boolean openWorkCalendar(String id, String userid) throws Exception {
		int i = workCanlendarMapper.openWorkCalendar(id, userid);
		return i>0;
	}

	@Override
	public boolean closeWorkCalendar(String id, String userid) throws Exception {
		int i = workCanlendarMapper.closeWorkCalendar(id, userid);
		return i>0;
	}

	@Override
	public boolean checkWorkCalendarId(String id) throws Exception {
		int i = workCanlendarMapper.checkWorkCalendarId(id);
		return i==0;
	}
	
	
}

