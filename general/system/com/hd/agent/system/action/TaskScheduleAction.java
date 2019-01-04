/**
 * @(#)TaskScheduleAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 14, 2013 chenwei 创建版本
 */
package com.hd.agent.system.action;

import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.system.model.TaskList;
import com.hd.agent.system.model.TaskSchedule;
import com.hd.agent.system.service.ITaskScheduleService;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 任务调度
 * @author chenwei
 */
public class TaskScheduleAction extends BaseAction {
	/**
	 * 任务清单
	 */
	private TaskList taskList;
	/**
	 * 任务计划安排
	 */
	private TaskSchedule taskSchedule;
	/**
	 * 任务调度service
	 */
	private ITaskScheduleService taskScheduleService;
	
	public TaskList getTaskList() {
		return taskList;
	}

	public void setTaskList(TaskList taskList) {
		this.taskList = taskList;
	}

	public ITaskScheduleService  getTaskScheduleService() {
		return taskScheduleService;
	}

	public void setTaskScheduleService(ITaskScheduleService taskScheduleService) {
		this.taskScheduleService = taskScheduleService;
	}
	
	public TaskSchedule getTaskSchedule() {
		return taskSchedule;
	}

	public void setTaskSchedule(TaskSchedule taskSchedule) {
		this.taskSchedule = taskSchedule;
	}

	/**
	 * 显示任务清单列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 14, 2013
	 */
	public String showTaskListPage() throws Exception{
		return "success";
	}
	/**
	 * 显示任务添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public String showTaskListAddPage() throws Exception{
		return "success";
	}
	/**
	 * 添加任务清单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	@UserOperateLog(key="Task",type=2)
	public String addTaskList() throws Exception{
		//保存状态
		taskList.setState("2");
		boolean flag = taskScheduleService.addTaskList(taskList);
		addJSONObject("flag", flag);
		
		addLog("添加任务清单 编号:"+taskList.getId(), flag);
		return "success";
	}
	/**
	 * 显示任务清单列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public String showTaskList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = taskScheduleService.showTaskListData(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 显示任务清单修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public String showTaskListEditPage() throws Exception{
		String id = request.getParameter("id");
		TaskList taskList = taskScheduleService.showTaskListInfo(id);
		request.setAttribute("taskList", taskList);
		return "success";
	}
	/**
	 * 修改任务清单信息
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	@UserOperateLog(key="Task",type=3)
	public String editTaskList() throws Exception{
		if(null!=taskList && !"1".equals(taskList.getState())){
			taskList.setState("2");
		}
		boolean flag = taskScheduleService.editTaskList(taskList);
		addJSONObject("flag", flag);
		
		addLog("修改任务清单 编号:"+taskList.getId(), flag);
		return "success";
	}
	/**
	 * 删除任务清单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	@UserOperateLog(key="Task",type=4)
	public String deleteTaskList() throws Exception{
		String id = request.getParameter("id");
		boolean flag = taskScheduleService.deleteTaskList(id);
		addJSONObject("flag", flag);
		
		addLog("删除任务清单 编号:"+id, flag);
		return "success";
	}
	/**
	 * 启用任务清单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	@UserOperateLog(key="Task",type=3)
	public String openTaskList() throws Exception{
		String id = request.getParameter("id");
		boolean flag = taskScheduleService.openTaskList(id);
		addJSONObject("flag", flag);
		
		addLog("启用任务清单 编号:"+id, flag);
		return "success";
	}
	/**
	 * 禁用任务清单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	@UserOperateLog(key="Task",type=3)
	public String closeTaskList() throws Exception{
		String id = request.getParameter("id");
		boolean flag = taskScheduleService.closeTaskList(id);
		addJSONObject("flag", flag);
		
		addLog("禁用任务清单 编号:"+id, flag);
		return "success";
	}
	/**
	 * 获取任务清单列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public String getTaskListData() throws Exception{
		//type 1取业务任务2系统任务0全部
		String type = request.getParameter("type");
		if("0".equals(type)){
			type = null;
		}
		List list = taskScheduleService.getTaskListData(type);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 显示任务调度计划页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public String showTaskSchedulePage() throws Exception{
		return "success";
	}
	/**
	 * 显示任务计划安排添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public String showTaskScheduleAddPage() throws Exception{
		return "success";
	}
	/**
	 * 添加任务计划安排
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	@UserOperateLog(key="Task",type=2)
	public String addTaskSchedule() throws Exception{
		taskSchedule.setTaskid(CommonUtils.getDataNumber());
		//默认为停止状态
		taskSchedule.setState("3");
		boolean flag = taskScheduleService.addTaskSchedule(taskSchedule);
		addJSONObject("flag", flag);
		
		addLog("添加任务计划 编号:"+taskSchedule.getTaskid(), flag);
		return "success";
	}
	/**
	 * 显示任务计划安排列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public String showTaskScheduleList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		if(!map.containsKey("state")){
			map.put("state", "1");
		}else{
			String state = (String) map.get("state");
			if("9".equals(state)){
				map.remove("state");
			}
		}
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = taskScheduleService.showTaskScheduleList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 启动任务计划
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 16, 2013
	 */
	@UserOperateLog(key="Task",type=3)
	public String startTaskSchedule() throws Exception{
        String ids = request.getParameter("ids");
        String[] idArr = ids.split(",");
        boolean flag = false;
        String msg = "";
        String successMsg = "";
        String failMsg = "";
        for(String task : idArr){
            String[] taskArr = task.split("@");
            String taskid = taskArr[0];
            String team = taskArr[1];
            boolean dFlag = taskScheduleService.startTaskSchedule(taskid,team);
            if(dFlag){
                flag = true;
                successMsg += taskid+",";
            }else{
                failMsg += taskid+",";
            }
        }
        if(StringUtils.isNotEmpty(successMsg)){
            msg += "成功编号："+successMsg;
        }
        if(StringUtils.isNotEmpty(failMsg)){
            msg += "失败编号："+failMsg ;
        }
        Map map = new HashMap();
        map.put("flag",flag);
        map.put("msg",msg);
        addJSONObject(map);
        addLog("启用任务计划 :" + msg, flag);
        return "success";
	}
	/**
	 * 暂停任务计划
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 18, 2013
	 */
	@UserOperateLog(key="Task",type=3)
	public String pauseTaskSchedule() throws Exception{
        String ids = request.getParameter("ids");
        String[] idArr = ids.split(",");
        boolean flag = false;
        String msg = "";
        String successMsg = "";
        String failMsg = "";
        for(String task : idArr){
            String[] taskArr = task.split("@");
            String taskid = taskArr[0];
            String team = taskArr[1];
            boolean dFlag = taskScheduleService.pauseTaskSchedule(taskid, team);
            if(dFlag){
                flag = true;
                successMsg += taskid+",";
            }else{
                failMsg += taskid+",";
            }
        }
        if(StringUtils.isNotEmpty(successMsg)){
            msg += "成功编号："+successMsg;
        }
        if(StringUtils.isNotEmpty(failMsg)){
            msg += "失败编号："+failMsg ;
        }
        Map map = new HashMap();
        map.put("flag",flag);
        map.put("msg",msg);
        addJSONObject(map);
        addLog("暂停任务计划 :" + msg, flag);
        return "success";
	}
	/**
	 * 停止任务计划
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 18, 2013
	 */
	@UserOperateLog(key="Task",type=3)
	public String cancelTaskSchedule() throws Exception{
        String ids = request.getParameter("ids");
        String[] idArr = ids.split(",");
        boolean flag = false;
        String msg = "";
        String successMsg = "";
        String failMsg = "";
        for(String task : idArr){
            String[] taskArr = task.split("@");
            String taskid = taskArr[0];
            String team = taskArr[1];
            boolean dFlag = taskScheduleService.cancelTaskSchedule(taskid, team);
            if(dFlag){
                flag = true;
                successMsg += taskid+",";
            }else{
                failMsg += taskid+",";
            }
        }
        if(StringUtils.isNotEmpty(successMsg)){
            msg += "成功编号："+successMsg;
        }
        if(StringUtils.isNotEmpty(failMsg)){
            msg += "失败编号："+failMsg ;
        }
        Map map = new HashMap();
        map.put("flag",flag);
        map.put("msg",msg);
        addJSONObject(map);
        addLog("停止任务计划 :" + msg, flag);
        return "success";
	}
	/**
	 * 删除任务计划
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 26, 2013
	 */
	@UserOperateLog(key="Task",type=4)
	public String deleteTaskSchedule() throws Exception{
        String ids = request.getParameter("ids");
        String[] idArr = ids.split(",");
        boolean flag = false;
        String msg = "";
        String successMsg = "";
        String failMsg = "";
        for(String task : idArr){
            String[] taskArr = task.split("@");
            String taskid = taskArr[0];
            String team = taskArr[1];
            boolean dFlag = taskScheduleService.deleteTaskSchedule(taskid,team);
            if(dFlag){
                flag = true;
                successMsg += taskid+",";
            }else{
                failMsg += taskid+",";
            }
        }
        if(StringUtils.isNotEmpty(successMsg)){
            msg += "成功编号："+successMsg;
        }
        if(StringUtils.isNotEmpty(failMsg)){
            msg += "失败编号："+failMsg ;
        }
        Map map = new HashMap();
        map.put("flag",flag);
        map.put("msg",msg);
		addJSONObject(map);
		addLog("删除任务计划 :" + msg, flag);
		return "success";
	}
	/**
	 * 显示任务计划修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 18, 2013
	 */
	public String showTaskScheduleEditPage() throws Exception{
		String taskid = request.getParameter("taskid");
		String team = request.getParameter("team");
		TaskSchedule taskSchedule = taskScheduleService.showTaskSchedule(taskid, team);
		request.setAttribute("taskSchedule", taskSchedule);
		return "success";
	}
	/**
	 * 修改任务计划信息
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 18, 2013
	 */
	@UserOperateLog(key="Task",type=3)
	public String editTaskSchedule() throws Exception{
		boolean flag = taskScheduleService.editTaskSchedule(taskSchedule);
		addJSONObject("flag", flag);
		
		addLog("修改任务计划 编号:"+taskSchedule.getTaskid(), flag);
		return "success";
	}
	/**
	 * 显示任务执行日志列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 19, 2013
	 */
	public String showTaskScheduleLogsPage() throws Exception{
		String taskid = request.getParameter("taskid");
		String team = request.getParameter("team");
		request.setAttribute("taskid", taskid);
		request.setAttribute("team", team);
		return "success";
	}
	/**
	 * 获取任务执行日志列表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 19, 2013
	 */
	public String showTaskLogsList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = taskScheduleService.showTaskLogsList(pageMap);
		addJSONObject(pageData);
		return "success";
	}

    /**
     * 立即执行任务计划
     * @return
     * @throws Exception
     */
    @UserOperateLog(key="Task",type=3)
    public String runTaskSchedule() throws Exception{
        String taskid = request.getParameter("taskid");
        String team = request.getParameter("team");
        boolean flag = taskScheduleService.runTaskSchedule(taskid, team);
        addJSONObject("flag", flag);

        addLog("立即任务计划 编号:"+taskid, flag);
        return "success";
    }
}

