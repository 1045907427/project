/**
 * @(#)TaskCreateListener.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Oct 6, 2013 zhengziyong 创建版本
 */
package com.hd.agent.activiti.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.activiti.model.DefinitionTask;
import com.hd.agent.activiti.model.Process;
import com.hd.agent.activiti.service.IDefinitionService;
import com.hd.agent.activiti.service.IWorkService;
import com.hd.agent.common.service.impl.BaseServiceImpl;

/**
 * 
 * 
 * @author zhengziyong
 */
public class TaskCreateListener extends BaseServiceImpl implements TaskListener {

	private final static String RULE_ASSIGNEE = "assignee";  //指定人员
	private final static String RULE_DEPARTMENTROLE = "departmentRole";  //本部门指定角色
	private final static String RULE_DEPARTMENT = "department";  //本部门所有人
	private final static String RULE_ONEROLE = "oneRole";  //指定角色
	private final static String RULE_ONEDEPARTMENT = "oneDepartment";  //指定部门
	private final static String RULE_ONEDEPARTMENTONEROLE = "oneDepartmentOneRole";  //指定部门与角色
	private final static String RULE_ONEPOST = "onePost";  //指定岗位
	
	private IDefinitionService definitionService;
	private IWorkService workService;
	
	public IDefinitionService getDefinitionService() {
		return definitionService;
	}

	public void setDefinitionService(IDefinitionService definitionService) {
		this.definitionService = definitionService;
	}

	public IWorkService getWorkService() {
		return workService;
	}

	public void setWorkService(IWorkService workService) {
		this.workService = workService;
	}

	@Override
	public void notify(DelegateTask task) {
		Process process = new Process();
		try {
			SysUser sysUser = getSysUser();
			String definitionid = task.getProcessDefinitionId();
			DelegateExecution execution = task.getExecution();
			String applyUserId = execution.getVariable("applyUserId").toString();
			String taskkey = task.getTaskDefinitionKey();
			String definitionkey = "";
			if(definitionid.contains(":")){
				definitionkey = definitionid.split(":")[0];
			}
			String businessKey = execution.getProcessBusinessKey();
			if(businessKey.contains(":")){
				businessKey = businessKey.split(":")[1];
			}
			DefinitionTask definitionTask = definitionService.getDefinitionTaskByKey(definitionkey, task.getProcessDefinitionId(), taskkey);
			process.setTaskid(task.getId());
			process.setTaskkey(task.getTaskDefinitionKey());
			process.setTaskname(task.getName());
			process.setUpdatedate(new Date());
			process.setId(businessKey);
			process.setSignuser("");
			if(definitionTask == null){
				process.setAssignee(sysUser.getUserid());
				process.setCondidate("");
				task.setAssignee(sysUser.getUserid());
			}
			else{
				String rule = definitionTask.getRule();
				if(StringUtils.isEmpty(rule)){
					process.setAssignee("");
					process.setCondidate(applyUserId);
					task.setAssignee(applyUserId);
				}
				else if(RULE_ASSIGNEE.equals(rule)){ //指定人员
					String user = definitionTask.getUser();
					String[] userArr = user.split(",");
					if(userArr.length < 2){
						process.setAssignee(user);
						process.setCondidate("");
						task.setAssignee(user);
					}
					else{
						process.setAssignee("");
						process.setCondidate(user);
						List<String> userList = new ArrayList<String>();
						for(String a: userArr){
							userList.add(a);
						}
						task.addCandidateUsers(userList);
					}
				}
				else if(RULE_DEPARTMENTROLE.equals(rule)){ //本部门指定角色
					String deptId = sysUser.getDepartmentid();
					String role = definitionTask.getRole();
					List<SysUser> sysUserList = getSysUserListByRoleidAndDeptid(role, deptId);
					List<String> userList = new ArrayList<String>();
					String userStr = "";
					for(SysUser user: sysUserList){
						userList.add(user.getUserid());
						userStr += user.getUserid()+ ",";
					}
					if(userStr.endsWith(",")){
						userStr = userStr.substring(0, userStr.length() - 1);
					}
					process.setAssignee("");
					process.setCondidate(userStr);
					task.addCandidateGroups(userList);
				}
				else if(RULE_DEPARTMENT.equals(rule)){ //本部门所有人
					String deptId = sysUser.getDepartmentid();
					List<SysUser> sysUserList = getSysUserListByDept(deptId);
					List<String> userList = new ArrayList<String>();
					String userStr = "";
					for(SysUser user: sysUserList){
						userList.add(user.getUserid());
						userStr += user.getUserid()+ ",";
					}
					if(userStr.endsWith(",")){
						userStr = userStr.substring(0, userStr.length() - 1);
					}
					process.setAssignee("");
					process.setCondidate(userStr);
					task.addCandidateGroups(userList);
				}
				else if(RULE_ONEROLE.equals(rule)){ //指定角色
					String role = definitionTask.getRole();
					List<SysUser> sysUserList = getSysUserListByRoleid(role);
					List<String> userList = new ArrayList<String>();
					String userStr = "";
					for(SysUser user: sysUserList){
						userList.add(user.getUserid());
						userStr += user.getUserid()+ ",";
					}
					if(userStr.endsWith(",")){
						userStr = userStr.substring(0, userStr.length() - 1);
					}
					process.setAssignee("");
					process.setCondidate(userStr);
					task.addCandidateGroups(userList);
				}
				else if(RULE_ONEDEPARTMENT.equals(rule)){ //指定部门
					String deptId = definitionTask.getDept();
					List<SysUser> sysUserList = getSysUserListByDept(deptId);
					List<String> userList = new ArrayList<String>();
					String userStr = "";
					for(SysUser user: sysUserList){
						userList.add(user.getUserid());
						userStr += user.getUserid()+ ",";
					}
					if(userStr.endsWith(",")){
						userStr = userStr.substring(0, userStr.length() - 1);
					}
					process.setAssignee("");
					process.setCondidate(userStr);
					task.addCandidateGroups(userList);
				}
				else if(RULE_ONEDEPARTMENTONEROLE.equals(rule)){ //指定部门与角色
					String role = definitionTask.getRole();
					String deptId = definitionTask.getDept();
					List<SysUser> sysUserList = getSysUserListByRoleidAndDeptid(role, deptId);
					List<String> userList = new ArrayList<String>();
					String userStr = "";
					for(SysUser user: sysUserList){
						userList.add(user.getUserid());
						userStr += user.getUserid()+ ",";
					}
					if(userStr.endsWith(",")){
						userStr = userStr.substring(0, userStr.length() - 1);
					}
					process.setAssignee("");
					process.setCondidate(userStr);
					task.addCandidateGroups(userList);
				}
				else if(RULE_ONEPOST.equals(rule)){
					String post = definitionTask.getPost();
					List<SysUser> sysUserList = getSysUserListByWorkjobid(post);
					List<String> userList = new ArrayList<String>();
					String userStr = "";
					for(SysUser user: sysUserList){
						userList.add(user.getUserid());
						userStr += user.getUserid()+ ",";
					}
					if(userStr.endsWith(",")){
						userStr = userStr.substring(0, userStr.length() - 1);
					}
					process.setAssignee("");
					process.setCondidate(userStr);
					task.addCandidateGroups(userList);
				}
			}
			workService.updateNewWork(process);
		} catch (Exception e) {
			try {
				throw new Exception("节点创建时失败");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}

