/**
 * @(#)WorkJobAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Apr 11, 2013 chenwei 创建版本
 */
package com.hd.agent.basefiles.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.WorkJob;
import com.hd.agent.basefiles.service.IWorkJobService;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;

/**
 * 
 * 工作岗位action
 * @author chenwei
 */
public class WorkJobAction extends BaseAction {
	
	private IWorkJobService workJobService;
	
	private WorkJob workJob;
	
	public IWorkJobService getWorkJobService() {
		return workJobService;
	}

	public void setWorkJobService(IWorkJobService workJobService) {
		this.workJobService = workJobService;
	}
	
	public WorkJob getWorkJob() {
		return workJob;
	}

	public void setWorkJob(WorkJob workJob) {
		this.workJob = workJob;
	}

	/**
	 * 显示工作岗位页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 11, 2013
	 */
	public String showWorkJobPage() throws Exception{
		return "success";
	}
	/**
	 * 显示工作岗位添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 11, 2013
	 */
	public String showWorkJobAddPage() throws Exception{
		return "success";
	}
	/**
	 * 添加工作岗位暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="WorkJob",type=2)
	public String addWorkJobHold() throws Exception{
		SysUser sysUser = getSysUser();
		workJob.setState("2");
		workJob.setAdduserid(sysUser.getUserid());
		workJob.setAddusername(sysUser.getName());
		workJob.setAdddeptid(sysUser.getDepartmentid());
		workJob.setAdddeptname(sysUser.getDepartmentname());
		boolean flag = workJobService.addWorkJob(workJob);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", workJob.getId());
		addJSONObject(map);
		addLog("工作岗位暂存 编码:"+workJob.getId(), flag);
		return "success";
	}
	/**
	 * 添加工作岗位保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="WorkJob",type=2)
	public String addWorkJobSave() throws Exception{
		SysUser sysUser = getSysUser();
		workJob.setState("2");
		workJob.setAdduserid(sysUser.getUserid());
		workJob.setAddusername(sysUser.getName());
		workJob.setAdddeptid(sysUser.getDepartmentid());
		workJob.setAdddeptname(sysUser.getDepartmentname());
		boolean flag = workJobService.addWorkJob(workJob);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", workJob.getId());
		addJSONObject(map);
		addLog("工作岗位保存 编码:"+workJob.getId(), flag);
		return "success";
	}
	/**
	 * 获取工作岗位列表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 12, 2013
	 */
	public String getWorkJobList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = workJobService.getWorkJobList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 查看工作岗位详细信息
	 */
	public String showWorkJobInfo() throws Exception{
		String id = request.getParameter("id");
		WorkJob workJob = workJobService.showWorkJobInfo(id);
		request.setAttribute("workJob", workJob);
		return "success";
	}
	/**
	 * 显示工作岗位修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 12, 2013
	 */
	public String showWorkJobEditPage() throws Exception{
		String id = request.getParameter("id");
		WorkJob workJob = workJobService.showWorkJobInfo(id);
		//判断数据是否被引用 true未被引用 false被引用
		boolean isQuoteDel = canTableDataDelete("t_base_workjob", id);
		request.setAttribute("workJob", workJob);
		request.setAttribute("isQuoteDel", isQuoteDel);
		return "success";
	}
	/**
	 * 修改暂存工作岗位
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 12, 2013
	 */
	@UserOperateLog(key="WorkJob",type=3)
	public String editWorkJobHold() throws Exception{
		SysUser sysUser = getSysUser();
		workJob.setModifyuserid(sysUser.getUserid());
		workJob.setModifyusername(sysUser.getName());
		//盘点加锁是否可以操作
		boolean lockFlag = isLockEdit("t_base_workjob", workJob.getOldid());
		boolean flag = false;
		if(lockFlag){
			flag = workJobService.editWorkJob(workJob);
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("lockFlag", lockFlag);
		map.put("id", workJob.getId());
		addJSONObject(map);
		addLog("工作岗位修改暂存 编码:"+workJob.getId(), flag);
		return "success";
	}
	/**
	 * 修改保存工作岗位
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 12, 2013
	 */
	@UserOperateLog(key="WorkJob",type=3)
	public String editWorkJobSave() throws Exception{
		SysUser sysUser = getSysUser();
		if("3".equals(workJob.getState())){
			workJob.setState("2");
		}
		workJob.setModifyuserid(sysUser.getUserid());
		workJob.setModifyusername(sysUser.getName());
		//盘点加锁是否可以操作
		boolean lockFlag = isLockEdit("t_base_workjob", workJob.getOldid());
		boolean flag = false;
		if(lockFlag){
			flag = workJobService.editWorkJob(workJob);
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("lockFlag", lockFlag);
		map.put("id", workJob.getId());
		addJSONObject(map);
		addLog("工作岗位修改保存 编码:"+workJob.getId(), flag);
		return "success";
	}
	/**
	 * 删除工作岗位
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 12, 2013
	 */
	@UserOperateLog(key="WorkJob",type=4)
	public String deleteWorkJob() throws Exception{
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_base_workjob", id);
		boolean flag = false;
		if(delFlag){
			flag = workJobService.deleteWorkJob(id);
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("delFlag",delFlag );
		addJSONObject(map);
		addLog("工作岗位删除 编码:"+id, flag);
		return "success";
	}
	/**
	 * 工作岗位复制
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 13, 2013
	 */
	public String showWorkJobCopyPage() throws Exception{
		String id = request.getParameter("id");
		WorkJob workJob = workJobService.showWorkJobInfo(id);
		request.setAttribute("workJob", workJob);
		return "success";
	}
	/**
	 * 启用工作岗位
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 13, 2013
	 */
	@UserOperateLog(key="WorkJob",type=3)
	public String openWorkJob() throws Exception{
		String id = request.getParameter("id");
		boolean flag = workJobService.openWorkJob(id);
		addJSONObject("flag", flag);
		addLog("工作岗位启用 编码:"+id, flag);
		return "success";
	}
	/**
	 * 禁用工作岗位
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 13, 2013
	 */
	@UserOperateLog(key="WorkJob",type=3)
	public String closeWorkJob() throws Exception{
		String id = request.getParameter("id");
		boolean flag = workJobService.closeWorkJob(id);
		addJSONObject("flag", flag);
		addLog("工作岗位禁用 编码:"+id, flag);
		return "success";
	}
	/**
	 * 验证工作岗位编号是否重复
	 * true 正常 false重复
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 13, 2013
	 */
	public String checkWorkJobID() throws Exception{
		String id = request.getParameter("id");
		boolean flag = workJobService.checkWorkJobID(id);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 验证工作岗位名称是否重复
	 * true 正常 false重复
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 13, 2013
	 */
	public String checkWorkJobName() throws Exception{
		String name = request.getParameter("name");
		boolean flag = workJobService.checkWorkJobName(name);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 根据工作岗位获取该工作岗位的角色列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 27, 2013
	 */
	public String getRoleListByWorkjob() throws Exception{
		String id = request.getParameter("id");
		List list = workJobService.getRoleListByWorkjob(id);
		addJSONArray(list);
		return "success";
	}
}

