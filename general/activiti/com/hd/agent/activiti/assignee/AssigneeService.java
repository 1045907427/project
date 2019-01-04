/**
 * @(#)AssigneeService.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Oct 6, 2013 zhengziyong 创建版本
 */
package com.hd.agent.activiti.assignee;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.activiti.model.*;
import com.hd.agent.activiti.model.Process;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.activiti.dao.AuthRuleMapper;
import com.hd.agent.activiti.service.IDefinitionService;
import com.hd.agent.activiti.service.IWorkService;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.SpringContextUtils;

/**
 *
 *
 * @author zhengziyong
 */
public class AssigneeService extends BaseServiceImpl implements TaskListener {

    private final static String RULE_ASSIGNEE = "assignee";  //指定人员

    private final static String RULE_DEPARTMENTROLE = "departmentRole";  //本部门指定角色

    private final static String RULE_DEPARTMENT = "department";  //本部门所有人

    private final static String RULE_ONEROLE = "oneRole";  //指定角色

    private final static String RULE_ONEDEPARTMENT = "oneDepartment";  //指定部门

    private final static String RULE_ONEDEPARTMENTONEROLE = "oneDepartmentOneRole";  //指定部门与角色

    private final static String RULE_ONEPOST = "onePost";  //指定岗位

    private IDefinitionService definitionService;

    private IWorkService workService;

    private AuthRuleMapper ruleMapper;

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

    public AuthRuleMapper getRuleMapper() {
        return ruleMapper;
    }

    public void setRuleMapper(AuthRuleMapper ruleMapper) {
        this.ruleMapper = ruleMapper;
    }

    /**
     * 第一个用户任务人员分配统一入口
     * @param task
     * @throws Exception
     * @author zhengziyong
     * @date Oct 8, 2013
     */
    public void first(DelegateTask task) throws Exception{
        DelegateExecution execution = task.getExecution();
        Object obj = execution.getVariable("firstAssignee");
        assignee(task, obj);
    }

    /**
     * 除第一个用户任务外其他用户任务分配人员统一入口
     * @param task
     * @throws Exception
     * @author zhengziyong
     * @date Oct 7, 2013
     */
    public void assignee(DelegateTask task) throws Exception{
        String taskkey = task.getTaskDefinitionKey();
        Object obj = task.getVariable(taskkey+ "_assignee");

        String processid = task.getExecution().getProcessBusinessKey();
        Process process = workService.getProcess(processid, "1");
        if (process == null) {
            processid = processid.split(":")[1];
            process = workService.getProcess(processid, "1");
        }
        boolean sign = definitionService.isSignTask(process.getDefinitionid(), process.getTaskkey());
        if(sign) {
            sign(task, process);
        } else {
            assignee(task, obj);
        }
    }

    /**
     * 设置会签人员
     * @param task
     * @param process
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date 2013-2-28
     */
    public void sign(DelegateTask task, Process process) throws Exception{

        ProcessTask processTask = new ProcessTask();
        processTask.setProcessid(Integer.parseInt(process.getId()));
        processTask.setInstanceid(task.getProcessInstanceId());
        processTask.setTaskkey(task.getTaskDefinitionKey());
        processTask.setTaskid(task.getId());
        processTask.setUserid((String) task.getVariable("assignee"));
        workService.addProcessTask(processTask);

        task.setAssignee((String) task.getVariable("assignee"));

        Process existProcess = new Process();
        existProcess.setTaskid("");
        existProcess.setTaskkey(task.getTaskDefinitionKey());
        existProcess.setTaskname(task.getName());
        existProcess.setUpdatedate(new Date());
        existProcess.setId(process.getId());
        existProcess.setSignuser("");
        existProcess.setAssignee("");
        existProcess.setCondidate("");
        workService.updateNewWork(existProcess);
    }

    /**
     * 人员分配
     * @param task
     * @param obj
     * @throws Exception
     * @author zhengziyong
     * @date Oct 9, 2013
     */
    private void assignee(DelegateTask task, Object obj) throws Exception{
        Process process = new Process();
//		SysUser sysUser = getSysUser();
        String pUser = "";
        String definitionid = task.getProcessDefinitionId();
        DelegateExecution execution = task.getExecution();
        String applyUserId = execution.getVariable("applyUserId").toString();
        SysUser sysUser = getSysUserById(applyUserId);
        String taskkey = task.getTaskDefinitionKey();
        String definitionkey = "";
        if(definitionid.contains(":")){
            definitionkey = definitionid.split(":")[0];
        }
        String processid = execution.getProcessBusinessKey();
        if(processid.contains(":")){
            processid = processid.split(":")[1];
        }
        DefinitionTask definitionTask = definitionService.getDefinitionTaskByKey(definitionkey, task.getProcessDefinitionId(), taskkey);
//        Definition definition = definitionService.getDefinitionByKey(definitionkey, null, "1");
//        if("1".equals(definition.getRemindtype())) {
//            definitionTask.setRemindtype("1");
//        }
        process.setTaskid(task.getId());
        process.setTaskkey(task.getTaskDefinitionKey());
        process.setTaskname(task.getName());
        process.setUpdatedate(new Date());
        process.setId(processid);
        process.setSignuser("");

        //流程中已经指定的办理人
        String taskAssignee = task.getAssignee();
        if(null!=taskAssignee && !"".equals(taskAssignee)){
            process.setAssignee(taskAssignee);
            process.setCondidate("");
            task.setAssignee(taskAssignee);
            //String definitionid = task.getProcessDefinitionId();
            //task.getTaskDefinitionKey();
            if(CommonUtils.nullToEmpty(definitionTask.getRemindtype()).contains("1")) {
                addRemind(pUser, task, definitionkey);
            }
        }else if(obj != null && !"".equals(obj.toString())){
            pUser = obj.toString();

            Process newProcess = workService.getProcess(processid, "1");

            process.setAssignee(pUser);
            if(StringUtils.isNotEmpty(newProcess.getBusinessid()) || (newProcess.getJson() != null && StringUtils.isNotEmpty(newProcess.getJson().toString()))) {

                String delegateUserId = getAssigneeDelegate(pUser, definitionkey);
                if(StringUtils.isNotEmpty(delegateUserId)){
                    addLog(task, pUser, delegateUserId, processid); //添加委托日志
                    pUser = delegateUserId;
                }
                if(definitionTask != null && CommonUtils.nullToEmpty(definitionTask.getRemindtype()).contains("1")) {
                    addRemind(pUser, task, definitionkey);
                }
                process.setAssignee(pUser);
            }

            process.setCondidate("");
            task.setAssignee(pUser);
        }
        else{
            if(definitionTask == null){
                process.setAssignee(sysUser.getUserid());
                process.setCondidate("");
                task.setAssignee(sysUser.getUserid());
            }
            else{
                String rule = definitionTask.getRule();

                List<String> ruleList = ruleMapper.getRuleByUserTaskType(definitionTask.getDefinitionkey(), definitionTask.getTaskkey(), "aud");

                if(StringUtils.isEmpty(rule)){
                    process.setAssignee(applyUserId);
                    process.setCondidate("");
                    task.setAssignee(applyUserId);
                    if(CommonUtils.nullToEmpty(definitionTask.getRemindtype()).contains("1")) {
                        addRemind(applyUserId, task, definitionkey);
                    }
                }
                else if(RULE_ASSIGNEE.equals(rule)){ //指定人员
                    String user = definitionTask.getUser();
                    String[] userArr = user.split(",");
                    if(userArr.length < 2){
                        String delegateUserId = getAssigneeDelegate(user, definitionkey);
                        if(StringUtils.isNotEmpty(delegateUserId)){
                            addLog(task, user, delegateUserId, processid); //添加委托日志
                            user = delegateUserId;
                        }
                        if(CommonUtils.nullToEmpty(definitionTask.getRemindtype()).contains("1")) {
                            addRemind(user, task, definitionkey);
                        }
                        process.setAssignee(user);
                        process.setCondidate("");
                        task.setAssignee(user);
                    }
                    else{
                        process.setAssignee("");
                        List<String> userList = new ArrayList<String>();
                        for(String a: userArr){
                            String delegateUserId = getAssigneeDelegate(a, definitionkey);
                            if(StringUtils.isNotEmpty(delegateUserId)){
                                addLog(task, a, delegateUserId, processid); //添加委托日志
                                user.replace(a, delegateUserId);
                                a = delegateUserId;
                            }
                            if(CommonUtils.nullToEmpty(definitionTask.getRemindtype()).contains("1")) {
                                addRemind(a, task, definitionkey);
                            }
                            userList.add(a);
                        }
                        process.setCondidate(user);
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
                        String userId = user.getUserid();
                        String delegateUserId = getAssigneeDelegate(userId, definitionkey);
                        if(StringUtils.isNotEmpty(delegateUserId)){
                            addLog(task, userId, delegateUserId, processid); //添加委托日志
                            userId = delegateUserId;
                        }
                        if(CommonUtils.nullToEmpty(definitionTask.getRemindtype()).contains("1")) {
                            addRemind(userId, task, definitionkey);
                        }
                        userList.add(userId);
                        userStr += userId+ ",";
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
                        String userId = user.getUserid();
                        String delegateUserId = getAssigneeDelegate(userId, definitionkey);
                        if(StringUtils.isNotEmpty(delegateUserId)){
                            addLog(task, userId, delegateUserId, processid); //添加委托日志
                            userId = delegateUserId;
                        }
                        if(CommonUtils.nullToEmpty(definitionTask.getRemindtype()).contains("1")) {
                            addRemind(userId, task, definitionkey);
                        }
                        userList.add(userId);
                        userStr += userId+ ",";
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
                        String userId = user.getUserid();
                        String delegateUserId = getAssigneeDelegate(userId, definitionkey);
                        if(StringUtils.isNotEmpty(delegateUserId)){
                            addLog(task, userId, delegateUserId, processid); //添加委托日志
                            userId = delegateUserId;
                        }
                        if(CommonUtils.nullToEmpty(definitionTask.getRemindtype()).contains("1")) {
                            addRemind(userId, task, definitionkey);
                        }
                        userList.add(userId);
                        userStr += userId+ ",";
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
                        String userId = user.getUserid();
                        String delegateUserId = getAssigneeDelegate(userId, definitionkey);
                        if(StringUtils.isNotEmpty(delegateUserId)){
                            addLog(task, userId, delegateUserId, processid); //添加委托日志
                            userId = delegateUserId;
                        }
                        if(CommonUtils.nullToEmpty(definitionTask.getRemindtype()).contains("1")) {
                            addRemind(userId, task, definitionkey);
                        }
                        userList.add(userId);
                        userStr += userId+ ",";
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
                        String userId = user.getUserid();
                        String delegateUserId = getAssigneeDelegate(userId, definitionkey);
                        if(StringUtils.isNotEmpty(delegateUserId)){
                            addLog(task, userId, delegateUserId, processid); //添加委托日志
                            userId = delegateUserId;
                        }
                        if(CommonUtils.nullToEmpty(definitionTask.getRemindtype()).contains("1")) {
                            addRemind(userId, task, definitionkey);
                        }
                        userList.add(userId);
                        userStr += userId+ ",";
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
                        String userId = user.getUserid();
                        String delegateUserId = getAssigneeDelegate(userId, definitionkey);
                        if(StringUtils.isNotEmpty(delegateUserId)){
                            addLog(task, userId, delegateUserId, processid); //添加委托日志
                            userId = delegateUserId;
                        }
                        if(CommonUtils.nullToEmpty(definitionTask.getRemindtype()).contains("1")) {
                            addRemind(userId, task, definitionkey);
                        }
                        userList.add(userId);
                        userStr += userId+ ",";
                    }
                    if(userStr.endsWith(",")){
                        userStr = userStr.substring(0, userStr.length() - 1);
                    }
                    process.setAssignee("");
                    process.setCondidate(userStr);
                    task.addCandidateGroups(userList);
                }
            }
        }
        workService.updateNewWork(process);
    }

    /**
     * 获取用户的委托规则
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date 2013-2-7
     */
    private List<Delegate> getDelegates(String userId, String definitionkey) throws Exception{
//        IWorkService workService = (IWorkService)SpringContextUtils.getBean("workService");
        List<Delegate> list = workService.getDelegateList(userId, definitionkey);
        return list;
    }

    /**
     * 执行委托规则，通过判断用户的委托规则如果有符合条件的，则返回被委托人员编号
     * @param definitionkey 流程描述标识
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date 2013-2-7
     */
    private String getAssigneeDelegate(String userId, String definitionkey) throws Exception{

        List<Delegate> delegates = getDelegates(userId, definitionkey);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 倒序遍历，取最新添加的规则
        for(int i = delegates.size() - 1; i >=0 ;i--){

            Delegate delegate = delegates.get(i);
            // 一直有效
            if("1".equals(delegate.getRemain())){
                return delegate.getDelegateuserid();
            }

            if(StringUtils.isEmpty(delegate.getBegindate())){
//                delegate.setBegindate(dateFormat.format(new Date()));
                delegate.setBegindate("1900-01-01 00:00:00");
            }
            if(StringUtils.isEmpty(delegate.getEnddate())){
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(new Date());
//                calendar.add(calendar.DATE, 1);
//                delegate.setEnddate(dateFormat.format(calendar.getTime()));
                delegate.setEnddate("9999-12-31 23:59:59");
            }

            String now = dateFormat.format(new Date());
            if(now.compareTo(delegate.getBegindate()) >= 0 && now.compareTo(delegate.getEnddate()) <= 0) {

                return delegate.getDelegateuserid();
            }

//            if(StringUtils.isEmpty(delegate.getBegindate()) && StringUtils.isNotEmpty(delegate.getEnddate())){
//                if(dateFormat.parse(delegate.getEnddate()).getTime()>new Date().getTime()){
//                    return delegate.getDelegateuserid();
//                }
//                else{
//                    return null;
//                }
//            }
//            else if(StringUtils.isEmpty(delegate.getEnddate()) && StringUtils.isNotEmpty(delegate.getBegindate())){
//                if(dateFormat.parse(delegate.getBegindate()).getTime()<new Date().getTime()){
//                    return delegate.getDelegateuserid();
//                }
//                else{
//                    return null;
//                }
//            }
//            else if(StringUtils.isEmpty(delegate.getEnddate()) && StringUtils.isEmpty(delegate.getBegindate())){
//                return null;
//            }
//            else{
//                if((dateFormat.parse(delegate.getBegindate()).getTime()<new Date().getTime()) && ((dateFormat.parse(delegate.getEnddate()).getTime() + 86400000)>new Date().getTime())){
//                    return delegate.getDelegateuserid();
//                }
//                else{
//                    return null;
//                }
//            }
        }
        return null;
    }

    /**
     * 日志记录-流程流转日志
     * @param task
     * @param userId
     * @param delegateUserId
     * @throws Exception
     * @author zhengziyong
     * @date 2013-3-5
     */
    private void addLog(DelegateTask task, String userId, String delegateUserId, String businessKey) throws Exception{
        IWorkService workService = (IWorkService)SpringContextUtils.getBean("workService");
        Process process = workService.getProcess(businessKey, "1");
        DelegateLog delegate = new DelegateLog();
        delegate.setTitle(process.getTitle());
        delegate.setDelegateuserid(delegateUserId);
        delegate.setUserid(userId);
        delegate.setTaskid(task.getId());
        delegate.setTaskname(task.getName());
//        delegate.setInstanceid(task.getProcessInstanceId());
        delegate.setProcessid(task.getProcessInstanceId());
        delegate.setDefinitionkey(process.getDefinitionkey());
        delegate.setDefinitionname(process.getDefinitionname());
        workService.addDelegateLog(delegate);
    }

    /**
     * 发送提醒信息
     * @param userId
     * @param task
     * @param definitionkey
     * @throws Exception
     * @author zhengziyong
     * @date 2013-3-20
     */
    private void addRemind(String userId, DelegateTask task, String definitionkey) throws Exception{

        {
            String instanceid = task.getProcessInstanceId();
            Definition definition = definitionService.getDefinitionByKey(definitionkey, null, "1");
            if (StringUtils.isNotEmpty(instanceid)) {

                Process process = workService.getProcess(instanceid, "2");
                if (process == null || (StringUtils.isNotEmpty(definition.getBusinessurl()) && StringUtils.isEmpty(process.getBusinessid()))) {

                    return;
                }
                if (process == null || (StringUtils.isNotEmpty(definition.getFormkey()) && StringUtils.isEmpty(CommonUtils.bytes2String(process.getJson())))) {

                    return;
                }
            }
        }

        DefinitionTask definitionTask = definitionService.getDefinitionTaskByKey(definitionkey, task.getProcessDefinitionId(), task.getTaskDefinitionKey());
        String remindType = "";
        if(definitionTask == null || StringUtils.isEmpty(definitionTask.getRemindtype())){
//            Definition definition = definitionService.getDefinitionByKey(definitionkey);
//            if(definition != null){
//                remindType = definition.getRemindtype();
//            }
        }
        else{
            remindType = definitionTask.getRemindtype();
        }
        Map map = new HashMap();
        map.put("mtiptype", remindType);
        map.put("receivers", userId);
        String businessUrl = "act/workHandlePage.do?taskid=" + task.getId() +"&taskkey=" + task.getTaskDefinitionKey()+ "&instanceid="+ task.getProcessInstanceId();
        map.put("remindurl", businessUrl);
//        map.put("title", "工作流提醒");
        map.put("title", "您有新的工作");
        map.put("msgtype", "4");
        map.put("content", "您有一条工作任务可以办理，如果看到该消息而没有工作可办理，该工作可能已被其他可办理人员办理。");
        map.put("tabtitle", "处理工作");
        addMessageReminder(map);
    }

    @Override
    public void notify(DelegateTask task) throws Exception {

        String taskkey = task.getTaskDefinitionKey();
        Object obj = task.getVariable(taskkey+ "_assignee");

        if(obj == null) {
            obj = task.getExecution().getVariable("firstAssignee");
        }

        String processid = task.getExecution().getProcessBusinessKey().split(":")[1];
        Process process = workService.getProcess(processid, "1");
        boolean sign = definitionService.isSignTask(task.getProcessDefinitionId(), taskkey);
        if(sign) {
            sign(task, process);
        } else {
            assignee(task, obj);
            workService.deleteProcessTaskByInstanceid(process.getInstanceid());
        }
    }
}

