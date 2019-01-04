/**
 * @(#)WorkCalendarAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-28 chenwei 创建版本
 */
package com.hd.agent.basefiles.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.WorkCanlendar;
import com.hd.agent.basefiles.model.WorkCanlendarDetail;
import com.hd.agent.basefiles.service.IWorkCanlendarService;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;

/**
 * 
 * 工作日历相关action
 * @author chenwei
 */
public class WorkCalendarAction extends BaseAction {
	
	private WorkCanlendar workCanlendar;
	
	public WorkCanlendar getWorkCanlendar() {
		return workCanlendar;
	}

	public void setWorkCanlendar(WorkCanlendar workCanlendar) {
		this.workCanlendar = workCanlendar;
	}
	/**
	 * 工作日历service
	 */
	private IWorkCanlendarService workCanlendarService;
	
	public IWorkCanlendarService getWorkCanlendarService() {
		return workCanlendarService;
	}

	public void setWorkCanlendarService(IWorkCanlendarService workCanlendarService) {
		this.workCanlendarService = workCanlendarService;
	}


	/**
	 * 显示工作日历配置页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-28
	 */
	public String showWorkCalendarPage() throws Exception{
		
		return "success";
	}
	/**
	 * 显示工作日历添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-28
	 */
	public String showWorkCanlendarAddPage() throws Exception{
		String year = request.getParameter("year");
		String restDays = request.getParameter("restDays");
		if(null==year||"".equals(year)){
			Calendar c = Calendar.getInstance();
			int year1 = c.get(Calendar.YEAR);
			year = ""+year1;
		}
		if(null!=restDays&&!"".equals(restDays)&&",".equals(restDays.substring(0, 1))){
			restDays = restDays.substring(1, restDays.length());
		}
		request.setAttribute("year", year);
		request.setAttribute("workCanlendar", workCanlendar);
		request.setAttribute("restDays", restDays);
		return "success";
	}
	/**
	 * 显示工作日历快速设置页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-29
	 */
	public String showWorkCanlendarQuickSetPage() throws Exception{
		String year1 = request.getParameter("year");
		int year = Integer.parseInt(year1);
		boolean flag = false;
		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
			flag = true;
		} else {
			flag = false;
		}
		request.setAttribute("year", year);
		request.setAttribute("isLeapYear", flag);
		return "success";
	}
	/**
	 * 工作日历快速设置。获取星期weeks,开始时间beginDate结束时间endDate.得到需要设置的日期
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-30
	 */
	public String workCanlendarQuickSet() throws Exception{
		String weeks = request.getParameter("weeks");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		
		String[] week = weeks.split(",");
		
		//开始时间
		Calendar beginCal = Calendar.getInstance();
		//结束时间
		Calendar endCal = Calendar.getInstance();
		beginCal.setTime(CommonUtils.stringToDate(beginDate));
		endCal.setTime(CommonUtils.stringToDate(endDate));
		Map map = new HashMap();
		if(week.length>0){
			for(String weekday :week){
				Calendar calendar = (Calendar) beginCal.clone();
				calendar.add(Calendar.DAY_OF_WEEK, 2);
				calendar.add(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_WEEK));
				calendar.set(Calendar.DAY_OF_WEEK,Integer.parseInt(weekday)); 
				
				Calendar d = (Calendar) calendar.clone();
				d.add(Calendar.DAY_OF_YEAR, -7);
				
				//向后取
				while ((calendar.before(endCal) && calendar.after(beginCal))||(calendar.compareTo(endCal)==0)||calendar.compareTo(beginCal)==0) {
					String month = String.valueOf(calendar.get(Calendar.MONTH)+1);
					if(map.containsKey("month"+month)){
						List list = (List) map.get("month"+month);
						list.add(CommonUtils.dataToStr(calendar.getTime(), "yyyy-MM-dd"));
					}else{
						List list = new ArrayList();
						list.add(CommonUtils.dataToStr(calendar.getTime(), "yyyy-MM-dd"));
						map.put("month"+month, list);
					}
					calendar.add(Calendar.DAY_OF_YEAR, 7);
				}
				//向前取
				while ((d.before(endCal) && d.after(beginCal))||(d.compareTo(endCal)==0)||d.compareTo(beginCal)==0) {
					String month = String.valueOf(d.get(Calendar.MONTH)+1);
					if(map.containsKey("month"+month)){
						List list = (List) map.get("month"+month);
						list.add(CommonUtils.dataToStr(d.getTime(), "yyyy-MM-dd"));
					}else{
						List list = new ArrayList();
						list.add(CommonUtils.dataToStr(d.getTime(), "yyyy-MM-dd"));
						map.put("month"+month, list);
					}
					d.add(Calendar.DAY_OF_YEAR, -7);
				}
			}
		}
		addJSONObject(map);
		return "success";
	}
	/**
	 * 保存工作日历
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-30
	 */
	@UserOperateLog(key="WorkCalendar",type=2)
	public String addWorkCalendar() throws Exception{
		SysUser sysUser = getSysUser();
		workCanlendar.setAdduserid(sysUser.getUserid());
		workCanlendar.setAdddeptid(sysUser.getDepartmentid());
		if(!"1".equals(workCanlendar.getState())){
			//保存
			workCanlendar.setState("2");
		}
		
		String year = request.getParameter("year");
		String restDays = request.getParameter("restDays");
		boolean flag = workCanlendarService.addWorkCalendar(workCanlendar, restDays, year);
		addJSONObject("flag", flag);
		
		addLog("保存工作日历 编号:"+workCanlendar.getId(), flag);
		return "success";
	}
	/**
	 * 暂存工作日历
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	@UserOperateLog(key="WorkCalendar",type=2)
	public String addWorkCalendarHold() throws Exception{
		SysUser sysUser = getSysUser();
		workCanlendar.setAdduserid(sysUser.getUserid());
		workCanlendar.setAdddeptid(sysUser.getDepartmentid());
		//保存
		workCanlendar.setState("3");
		String year = request.getParameter("year");
		String restDays = request.getParameter("restDays");
		boolean flag = workCanlendarService.addWorkCalendar(workCanlendar, restDays, year);
		addJSONObject("flag", flag);
		
		addLog("暂存工作日历 编号:"+workCanlendar.getId(), flag);
		return "success";
		
	}
	/**
	 * 获取工作日历列表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	public String getWorkCalendarList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = workCanlendarService.getWorkCalendarList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 显示工作日历详细信息
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	public String showWorkCanlendarInfo() throws Exception{
		String id = request.getParameter("id");
		String year1 = request.getParameter("year");
		String restDays = request.getParameter("restDays");
		Map map = workCanlendarService.showWorkCanlendarInfo(id);
		WorkCanlendar workCanlendar = (WorkCanlendar) map.get("workCanlendar");
		List<WorkCanlendarDetail> list = (List<WorkCanlendarDetail>) map.get("list");
		
		int year = 0;
		if(null!=year1&&!"".equals(year1)){
			year = Integer.parseInt(year1);
			if(null==restDays||"".equals(restDays)){
				for(WorkCanlendarDetail workCanlendarDetail:list){
					if("".equals(restDays)){
						restDays = workCanlendarDetail.getDate();
					}else{
						restDays += "," +workCanlendarDetail.getDate();
					}
				}
			}
		}else{
			if(null==restDays||"".equals(restDays)){
				for(WorkCanlendarDetail workCanlendarDetail:list){
					if("".equals(restDays)){
						restDays = workCanlendarDetail.getDate();
					}else{
						restDays += "," +workCanlendarDetail.getDate();
					}
					if(Integer.parseInt(workCanlendarDetail.getYear())>year){
						year = Integer.parseInt(workCanlendarDetail.getYear());
					}
				}
			}
		}
		request.setAttribute("year", year);
		request.setAttribute("workCanlendar", workCanlendar);
		request.setAttribute("restDays", restDays);
		return "success";
	}
	/**
	 * 删除工作日历
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	@UserOperateLog(key="WorkCalendar",type=4)
	public String deleteWorkCalendar() throws Exception{
		String id = request.getParameter("id");
		boolean flag = workCanlendarService.deleteWorkCalendar(id);
		addJSONObject("flag", flag);
		
		addLog("删除工作日历 编号:"+id, flag);
		return "success";
	}
	/**
	 * 显示工作日历修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	public String showWorkCanlendarEditPage() throws Exception{
		String id = request.getParameter("id");
		String year1 = request.getParameter("year");
		String restDays = request.getParameter("restDays");
		Map map = workCanlendarService.showWorkCanlendarInfo(id);
		WorkCanlendar workCanlendar = (WorkCanlendar) map.get("workCanlendar");
		List<WorkCanlendarDetail> list = (List<WorkCanlendarDetail>) map.get("list");
		
		int year = 0;
		if(null!=year1&&!"".equals(year1)){
			year = Integer.parseInt(year1);
			if(null==restDays||"".equals(restDays)){
				for(WorkCanlendarDetail workCanlendarDetail:list){
					if("".equals(restDays)){
						restDays = workCanlendarDetail.getDate();
					}else{
						restDays += "," +workCanlendarDetail.getDate();
					}
				}
			}
		}else{
			if(null==restDays||"".equals(restDays)){
				for(WorkCanlendarDetail workCanlendarDetail:list){
					if("".equals(restDays)){
						restDays = workCanlendarDetail.getDate();
					}else{
						restDays += "," +workCanlendarDetail.getDate();
					}
					if(Integer.parseInt(workCanlendarDetail.getYear())>year){
						year = Integer.parseInt(workCanlendarDetail.getYear());
					}
				}
			}
		}
		request.setAttribute("year", year);
		request.setAttribute("workCanlendar", workCanlendar);
		request.setAttribute("restDays", restDays);
		return "success";
	}
	/**
	 * 修改工作日历
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	@UserOperateLog(key="WorkCalendar",type=3)
	public String editWorkCalendar() throws Exception{
		String type = request.getParameter("type");
		
		SysUser sysUser = getSysUser();
		workCanlendar.setModifyuserid(sysUser.getUserid());
		if(!"1".equals(workCanlendar.getState())){
			if(null == type){
				workCanlendar.setState("2");
			}else if("open".equals(type)){
				workCanlendar.setState("1");
			}
		}
		
		String id = request.getParameter("id");
		String year = request.getParameter("year");
		String restDays = request.getParameter("restDays");
		
		boolean flag = workCanlendarService.editWorkCalendar(workCanlendar, id, restDays, year);
		addJSONObject("flag", flag);
		
		addLog("修改工作日历 编号:"+workCanlendar.getId(), flag);
		return "success";
	}
	/**
	 * 暂存修改工作日历
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	@UserOperateLog(key="WorkCalendar",type=3)
	public String editWorkCalendarHold() throws Exception{
		SysUser sysUser = getSysUser();
		workCanlendar.setModifyuserid(sysUser.getUserid());
		workCanlendar.setState("3");
		String id = request.getParameter("id");
		String year = request.getParameter("year");
		String restDays = request.getParameter("restDays");
		
		boolean flag = workCanlendarService.editWorkCalendar(workCanlendar, id, restDays, year);
		addJSONObject("flag", flag);
		
		addLog("修改工作日历 编号:"+workCanlendar.getId(), flag);
		return "success";
	}
	/**
	 * 启用工作日历
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	@UserOperateLog(key="WorkCalendar",type=3)
	public String openWorkCalendar() throws Exception{
		String id = request.getParameter("id");
		SysUser sysUser = getSysUser();
		boolean flag = workCanlendarService.openWorkCalendar(id, sysUser.getUserid());
		addJSONObject("flag", flag);
		
		addLog("启用工作日历 编号:"+id, flag);
		return "success";
	}
	/**
	 * 禁用工作日历
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	@UserOperateLog(key="WorkCalendar",type=3)
	public String closeWorkCalendar() throws Exception{
		String id = request.getParameter("id");
		SysUser sysUser = getSysUser();
		boolean flag = workCanlendarService.closeWorkCalendar(id, sysUser.getUserid());
		addJSONObject("flag", flag);
		
		addLog("禁用工作日历 编号:"+id, flag);
		return "success";
	}
	/**
	 * 工作日历复制
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	public String showWorkCanlendarCopyPage() throws Exception{
		String id = request.getParameter("id");
		String restDays = "";
		Map map = workCanlendarService.showWorkCanlendarInfo(id);
		WorkCanlendar workCanlendar = (WorkCanlendar) map.get("workCanlendar");
		List<WorkCanlendarDetail> list = (List<WorkCanlendarDetail>) map.get("list");
		
		int year = 0;
		for(WorkCanlendarDetail workCanlendarDetail:list){
			if("".equals(restDays)){
				restDays = workCanlendarDetail.getDate();
			}else{
				restDays += "," +workCanlendarDetail.getDate();
			}
			if(Integer.parseInt(workCanlendarDetail.getYear())>year){
				year = Integer.parseInt(workCanlendarDetail.getYear());
			}
		}
		request.setAttribute("year", year);
		request.setAttribute("workCanlendar", workCanlendar);
		request.setAttribute("restDays", restDays);
		return "success";
	}
	/**
	 * 验证工作日历编码是否重复.
	 * true正常false重复
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-31
	 */
	public String checkWorkCalendarId() throws Exception{
		String id = request.getParameter("id");
		boolean flag = workCanlendarService.checkWorkCalendarId(id);
		addJSONObject("flag", flag);
		return "success";
	}
}

