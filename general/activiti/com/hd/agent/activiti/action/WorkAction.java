/**
 * @(#)WorkAction.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 30, 2013 zhengziyong 创建版本
 */
package com.hd.agent.activiti.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.hd.agent.activiti.model.*;
import com.hd.agent.activiti.model.Process;
import com.hd.agent.common.util.JSONUtils;
import org.activiti.engine.impl.task.TaskDefinition;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import org.json.JSONArray;

/**
 *
 *
 * @author zhengziyong
 */
public class WorkAction extends BaseAction {

    private Process process;

    private Comment comment;

    private Delegate delegate;

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Delegate getDelegate() {
        return delegate;
    }

    public void setDelegate(Delegate delegate) {
        this.delegate = delegate;
    }

    private static Map<String, String> macros = new HashMap<String, String>();
    static {

        macros.put("sys_datetime", "yyyy-MM-dd HH:mm");
        macros.put("sys_date", "yyyy-MM-dd");
        macros.put("sys_date_cn", "yyyy年MM月dd日");
        macros.put("sys_date_cn_short1", "yyyy年MM月");
        macros.put("sys_date_cn_short4", "yyyy");
        macros.put("sys_date_cn_short3", "yyyy年");
        macros.put("sys_date_cn_short2", "MM月dd日");
        macros.put("sys_time", "HH:mm");
        macros.put("sys_week", "EEE");
    }

    /**
     * 新建工作页面
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Sep 30, 2013
     */
    public String newWorkPage() throws Exception{

        return SUCCESS;
    }

    /**
     * 常用工作页面
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Sep 30, 2013
     */
    public String newWorkPage2() throws Exception{
        request.setAttribute("list", workService.getCommonWorkList());
        return SUCCESS;
    }

    /**
     * 新建工作引导页面
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Sep 30, 2013
     */
    public String newWorkGuidePage() throws Exception{
        String definitionkey = request.getParameter("definitionkey");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Definition definition = definitionService.getDefinitionByKey(definitionkey, null, "1");
        SysUser user = getSysUser();
        String title = user.getDepartmentname()+ "-"+ user.getName()+ "-"+ definition.getName()+ "-"+ dateFormat.format(new Date());
        request.setAttribute("title", title);
        request.setAttribute("definition", definition);
        return SUCCESS;
    }

    /**
     * 新工作添加页面
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Sep 30, 2013
     */
    public String newWorkAddPage() throws Exception{
        String definitionkey = request.getParameter("definitionkey");
        String title = request.getParameter("title");
        Definition definition = definitionService.getDefinitionByKey(definitionkey, null, "1");
        String formData = "该流程没配置启动表单！";
        String businessUrl = "";
        String formType = "";
        if(definition != null){
            formType = definition.getFormtype();
            if("business".equals(formType)) {
                businessUrl = definition.getBusinessurl();
            } else {
                Form form = formService.getFormByKey(definition.getFormkey());
                if(form != null){
                    formData = CommonUtils.bytes2String(form.getDetail());
                }
            }
        }

        // TODO
        if("addOaAccess".equals(definitionkey)) {

            process = new Process();
            process.setDefinitionkey(definitionkey);
            process.setDefinitionname(definition.getName());
            process.setTitle(title);
            process.setBusinessid(null);
            workService.addNewWork(process);

            workService.startNewWork(process.getId(), getSysUser().getUserid());
            request.setAttribute("title", title);
            request.setAttribute("definitionkey", definitionkey);
            request.setAttribute("definitionName", definition.getName());
            request.setAttribute("formData", formData);
            request.setAttribute("businessUrl", businessUrl);
            request.setAttribute("formType", formType);
            request.setAttribute("process", process);

            //Definition definition1 = definitionService.getDefinitionByKey(key);
            request.setAttribute("handle", "1");
            request.setAttribute("definition", definition);

            return "success2";
        }

        request.setAttribute("title", title);
        request.setAttribute("definitionkey", definitionkey);
        request.setAttribute("definitionName", definition.getName());
        request.setAttribute("formData", formData);
        request.setAttribute("businessUrl", businessUrl);
        request.setAttribute("formType", formType);
        request.setAttribute("process", process);
        return SUCCESS;
    }

    /**
     * 添加新工作
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Sep 30, 2013
     */
    public String addNewWork() throws Exception{
        boolean flag = false;
        String type = request.getParameter("type");
        Definition definition = definitionService.getDefinitionByKey(process.getDefinitionkey(), null, "1");
        if(definition != null){
            process.setDefinitionname(definition.getName());
        }
        flag = workService.addNewWork(process);
        Map map = new HashMap();
        map.put("flag", flag);
        map.put("id", process.getId());
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 新工作修改页面（未提交工作流的工作）
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 5, 2013
     */
    public String newWorkUpdatePage() throws Exception{

        String id = request.getParameter("id");
        Process process = workService.getProcess(id, "1");

        if(process == null) {

            return SUCCESS;
        }

        String title = process.getTitle();
        if(StringUtils.isNotEmpty(title) && title.startsWith(process.getId() + "-")) {
            title = title.substring(title.indexOf(process.getId() + "-") + (process.getId() + "-").length());
            process.setTitle(title);
        }

        String definitionkey = process.getDefinitionkey();
        Definition definition = definitionService.getDefinitionByKey(definitionkey, null, "1");

        if(process.getApplydate() != null) {
            return SUCCESS;
        }

        request.setAttribute("process", process);
        request.setAttribute("definition", definition);
        return SUCCESS;
    }

    /**
     * 修改新工作
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 5, 2013
     */
    public String updateNewWork() throws Exception{

        boolean flag = false;
        String type = request.getParameter("type");
        String json = CommonUtils.nullToEmpty(request.getParameter("json"));
        String html = CommonUtils.nullToEmpty(request.getParameter("html"));

        if(StringUtils.isNotEmpty(json) && !"[]".equals(json)) {
            process.setJson(json.getBytes(DEFAULT_ENCODING));
        }
        if(StringUtils.isNotEmpty(html)) {
            process.setHtml(html.getBytes(DEFAULT_ENCODING));
        }

        flag = workService.updateNewWorkAndTitle(process);
        Map map = new HashMap();
        map.put("flag", flag);
        map.put("id", process.getId());
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 启动工作流页面
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 8, 2013
     */
    public String startNewWorkPage() throws Exception{
        String id = request.getParameter("id");
        Process process = workService.getProcess(id, "1");
        Definition definition = definitionService.getDefinitionByKey(process.getDefinitionkey(), null, "1");
        Map map = workService.getNextDefinitionTaskInfo(definition.getDefinitionid(), null);

        List<Map> list = workService.selectAttachList(id, null);

        request.setAttribute("process", process);
        request.setAttribute("nexttasklist", map.get("nexttasklist"));
        request.setAttribute("attachlist", list);

        return SUCCESS;
    }

    /**
     * 启动工作流
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 8, 2013
     */
    public String startNewWork() throws Exception{

        String id = request.getParameter("id");
        String type = request.getParameter("type");
        String nexttaskkey = request.getParameter("nexttaskkey");
        String nexttasktype = request.getParameter("nexttasktype");
        String nextAssignee = request.getParameter("nextAssignee");
        boolean flag = false;

        if(!"endEvent".equals(nexttasktype)) {
            flag = workService.startNewWork(id, nextAssignee, nexttaskkey);
        }

		/*
		if(flag && !"endEvent".equals(nexttasktype)) {
			
			Process process = workService.getProcess(id, type);
			workService.updateCompleteTaskWithoutLog(process.getTaskid(), nextAssignee, nexttaskkey);
		}
		*/

        addJSONObject("flag", flag);
        return SUCCESS;
    }

    /**
     * 我的工作-未接收
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-10-27
     */
    public String myWorkPage6() throws Exception{

        SysUser user = getSysUser();

        request.setAttribute("user", user);
        return SUCCESS;
    }

    /**
     * 我的工作-办理中
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-10-27
     */
    public String myWorkPage7() throws Exception{

        SysUser user = getSysUser();

        request.setAttribute("user", user);
        return SUCCESS;
    }

    /**
     * 我的工作-已办结
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-10-27
     */
    public String myWorkPage8() throws Exception{

        return SUCCESS;
    }

    /**
     * 我的工作-已删除
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-10-27
     */
    public String myWorkPage9() throws Exception{

        return SUCCESS;
    }

    /**
     * 我的工作-全部工作
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-10-28
     */
    public String myWorkPage11() throws Exception{

        return SUCCESS;
    }

    /**
     * 我的工作-已挂起
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-10-28
     */
    public String myWorkPage12() throws Exception{

        return SUCCESS;
    }

    /**
     * 获取工作流程数据
     *  type:1我的请求<br/>2我的办结<br/>3待办事宜<br/>4已办事宜<br/>5我的草稿<br/>6未接收<br/>7办理中<br/>8已办结<br/>9已删除<br/>10首页待办工作<br/>0工作管理
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 5, 2013
     */
    public String getWorkList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        SysUser user = getSysUser();
        map.put("userid", user.getUserid());

        String include = CommonUtils.nullToEmpty((String) map.get("include"));
        map.put("include", CommonUtils.emptyToNull(include));
        if(StringUtils.isEmpty(include)) {

            map.put("keys", null);

        } else {

            String[] arr = include.split(",");
            List<String> keys = new ArrayList<String>();
            for(String str : arr) {

                keys.add(str);
            }
            map.put("keys", keys);
        }

        pageMap.setCondition(map);
        addJSONObject(workService.getProcessData(pageMap));
        return SUCCESS;
    }

    /**
     * 删除工作记录（未提交工作流记录）
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 5, 2013
     */
    public String deleteWork() throws Exception{
        String id = request.getParameter("id");
        addJSONObject("flag", workService.deleteWork(id, "1"));
        return SUCCESS;
    }

    /**
     * 驳回上一节点
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 31, 2013
     */
//    public String backPrevWork() throws Exception{
//        String id = request.getParameter("id");
//        String preTaskKey = workService.getPreTaskKey(id);
//
//        // 前一节点不存在时，驳回至申请者
//        if(StringUtils.isEmpty(preTaskKey)) {
//
//            Process process = workService.getProcess(id, "1");
//
//            if(process == null) {
//
//                Map map = new HashMap();
//                map.put("flag", false);
//                addJSONObject(map);
//                return SUCCESS;
//            }
//
//            Map map = new HashMap();
//            boolean flag = workService.deleteAndInsertProcess(process.getInstanceid(), null);
//            map.put("flag", flag);
//            addJSONObject(map);
//            return SUCCESS;
//        }
//        boolean flag = workService.updateBackPrevWork(id);
//        Map map = new HashMap();
//        map.put("flag", flag);
//        addJSONObject(map);
//        return SUCCESS;
//    }

    public String takeBackWork() throws Exception{

        String id = request.getParameter("id");
        Process process = workService.getProcess(id, "1");
        boolean flag = false;
        SysUser user = getSysUser();
        String message = "";
        Map map = new HashMap();
        String taskkey = null;

        List<Comment> list = workService.getRealCommentList(id, null, false, false);
        int count = list.size();

        if(StringUtils.isEmpty(list.get(count - 1).getId())) {

            list.remove(count - 1);
        }
        count = list.size();

        if(count < 3 && list.get(count - 1).getTaskid().equals(process.getTaskid())) {

            flag = false;

        } else {

            Comment comment1 = list.get(count - 1);
            Comment comment2 = list.get(count - 2);
            if(process.getTaskid().equals(comment1.getTaskid())) {

                if(comment2.getAssignee().equals(user.getUserid())) {

                    taskkey = comment2.getTaskkey();
                    flag = workService.takeBackWork(id, taskkey);
                } else {

                    message = "可能由于该工作已经被转交！";
                }
            } else {

                if(comment1.getAssignee().equals(user.getUserid())) {

                    taskkey = comment1.getTaskkey();
                    flag = workService.takeBackWork(id, taskkey);
                } else {

                    message = "可能由于该工作已经被转交！";
                }
            }

        }

        map.put("flag", flag);
        map.put("message", message);

        addJSONObject(map);
        //addJSONObject("flag", flag);
        return SUCCESS;
    }

    /**
     * 撤销工作流(提交工作流的记录从工作流中撤出)
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 28, 2013
     */
    public String backOutWork() throws Exception{
        boolean flag = false;
        String id = request.getParameter("id");
        flag = workService.updateUndoWork(id);
        addJSONObject("flag", flag);
        return SUCCESS;
    }

    /**
     * 工作处理页面，根据formtype加载不同的页面
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 6, 2013
     */
    public String workHandlePage() throws Exception{

        String definitionkey = request.getParameter("definitionkey");
        String title = request.getParameter("title");
        String instanceid = request.getParameter("instanceid");
        String taskid = request.getParameter("taskid");
        String taskkey = request.getParameter("taskkey");

//        SysUser user = getSysUser();
//        if(StringUtils.isEmpty(taskid)) {
//            List<ProcessTask> processTaskList = workService.getProcessTaskListByTaskkey(instanceid, taskkey);
//            for(ProcessTask processTask : processTaskList) {
//                if(processTask.getUserid().equals(user.getUserid())) {
//                    taskid = processTask.getTaskid();
//                    break;
//                }
//            }
//        }

        Map map = workService.doHandleWork(definitionkey, instanceid, taskid, title);

        // 将map → request
        Iterator<String> mapkeys = map.keySet().iterator();
        while(mapkeys.hasNext()) {

            String mapkey = mapkeys.next();
            request.setAttribute(mapkey, map.get(mapkey));
        }

        Process process = (Process) map.get("process");
        request.setAttribute("first", true);        // first 是否为第一节点 true：是；false：否
        if(process != null) {
            List comments = workService.getRealCommentList(process.getId(), process.getInstanceid(), true, true);
            if(comments.size() > 0) {
                request.setAttribute("first", false);
            }
        }

        return SUCCESS;
    }

    /**
     * 在线表单类型工作处理页面（formtype为formkey）
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 6, 2013
     */
    public String workFormKeyPage() throws Exception{
        String taskid = request.getParameter("taskid");
        String instanceid = request.getParameter("instanceid");
        String processid = request.getParameter("processid");
        Process process = workService.getProcess(instanceid, "2");
        if(process == null) {

            process = workService.getProcess(processid, "1");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Definition definition = definitionService.getDefinitionByKey(process.getDefinitionkey(), process.getDefinitionid(), null);
        Form form = formService.getFormByKey(definition.getFormkey());
        String title = process.getTitle().substring(process.getId().length() + 1);
        request.setAttribute("process", process);
        request.setAttribute("taskid", taskid);
        request.setAttribute("name", getSysUser().getName());
        request.setAttribute("date", dateFormat.format(new Date()));
        request.setAttribute("title", title);
        if(form != null){
            request.setAttribute("formData", form.getDetail());
        }
        return SUCCESS;
    }

    /**
     * 工作查看页面，根据formtype加载不同的页面
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 8, 2013
     */
    public String workViewPage() throws Exception{
        String taskid = request.getParameter("taskid");
        String instanceid = request.getParameter("instanceid");
        String from = request.getParameter("from");
        String processid = request.getParameter("processid");

        Process process = null;
        if(StringUtils.isNotEmpty(instanceid)) {

            process = workService.getProcess(instanceid, "2");

        } else {

            process = workService.getProcess(processid, "1");
        }

        if(process != null) {

            String processId = process.getId();
            String title = process.getTitle();
            String definitionkey = process.getDefinitionkey();
            Definition definition = definitionService.getDefinitionByKey(definitionkey, process.getDefinitionid(), null);
            String businessId = process.getBusinessid();
            String businessUrl = definition.getBusinessurl();
            request.setAttribute("definition", definition);
            request.setAttribute("businessUrl", businessUrl);
            request.setAttribute("businessId", businessId);
            request.setAttribute("processId", processId);
            request.setAttribute("title", title);
            request.setAttribute("process", process);
        }
        request.setAttribute("taskid", taskid);
        request.setAttribute("instanceid", instanceid);
        request.setAttribute("user", getSysUser());
        request.setAttribute("from", from);
        return SUCCESS;
    }

    /**
     * 在线表单类型的工作查看页面（formtype为formkey）
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 8, 2013
     */
    public String workFormKeyViewPage() throws Exception{
        String taskid = request.getParameter("taskid");
        String instanceid = request.getParameter("instanceid");
        String id = request.getParameter("id");
        String processid = request.getParameter("processid");
        Process process = null;
        if(StringUtils.isNotEmpty(instanceid)) {

            process = workService.getProcess(instanceid, "2");

        } else if(StringUtils.isNotEmpty(id)) {

            process = workService.getProcess(id, "1");

        } else {

            process = workService.getProcess(processid, "1");
        }

        if(process == null){
            return SUCCESS;
        }
        SysUser user = getSysUser();
        Definition definition = definitionService.getDefinitionByKey(process.getDefinitionkey(), process.getDefinitionid(), null);
        Form form = formService.getFormByKey(definition.getFormkey());
        request.setAttribute("process", process);
        request.setAttribute("user", user);
        request.setAttribute("taskid", taskid);
        request.setAttribute("formData", form.getDetail());
        return SUCCESS;
    }

    /**
     *
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-10-28
     */
    public String workPrintPage() throws Exception{

        String id = request.getParameter("id");
        String instanceid = request.getParameter("instanceid");
        Process process = workService.getProcess(instanceid, "2");
        if (process == null) {
            process = workService.getProcess(id, "1");
        }

        {

            String definitionkey = process.getDefinitionkey();
            Definition definition = definitionService.getDefinitionByKey(definitionkey, process.getDefinitionid(), null);
            String formkey = definition.getFormkey();
            Form form = formService.getFormByKey(formkey);

            List<FormItemRule> rules = formService.selectFormRuleList(process.getDefinitionkey(), process.getDefinitionid(), process.getTaskkey());

            if(rules.size() == 0) {

                formService.initFormRule(definitionkey, process.getTaskkey());
                rules = formService.selectFormRuleList(process.getDefinitionkey(), process.getDefinitionid(), process.getTaskkey());
            }

            request.setAttribute("rules", JSONUtils.listToJsonStr(rules));
            request.setAttribute("definition", definition);
            request.setAttribute("form", form);
            request.setAttribute("process", process);
            request.setAttribute("json", process == null ? "" : CommonUtils.bytes2String(process.getJson()));
            request.setAttribute("detail", form == null ? null : CommonUtils.bytes2String(form.getDetail()));
            request.setAttribute("html", process == null ? null : CommonUtils.bytes2String(process.getHtml()));

        }
        return SUCCESS;
    }

    /**
     * 首页显示待办工作页面
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 11, 2013
     */
    public String workToDoPage() throws Exception{
        Map map = new HashMap();
        map.put("userid", getSysUser().getUserid());
        map.put("type", "10");
        pageMap.setCondition(map);
        pageMap.setRows(1000);
        PageData pageData = workService.getProcessData(pageMap);
        List list = pageData.getList();
        request.setAttribute("list", list);
        request.setAttribute("count", list.size());
        return SUCCESS;
    }



    /**
     * 业务（URL)表单启动流程页面
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 10, 2013
     */
    public String startWorkPage() throws Exception{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String businessId = request.getParameter("id");
        String definitionkey = request.getParameter("definitionkey");
        SysUser user = getSysUser();
        Definition definition = definitionService.getDefinitionByKey(definitionkey, null, "1");
        String title = user.getDepartmentname()+ "-"+ user.getName()+ "-"+ definition.getName()+ "-"+ dateFormat.format(new Date());
        request.setAttribute("title", title);
        request.setAttribute("businessId", businessId);
        request.setAttribute("definition", definition);
        return SUCCESS;
    }

    /**
     * 业务（URL)表单启动流程
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 10, 2013
     */
	/*
	 * 该方法废弃，查看最新的startBusinessWork方法
	public String startBusinessWork() throws Exception{
		String type = request.getParameter("acceptType");
		String applyUser = request.getParameter("applyUser");
		if("1".equals(type)){
			applyUser = "";
		}
		String listener = request.getParameter("listener");
		BusinessListener businessListener = null;
		if(StringUtils.isNotEmpty(listener)){
			businessListener = (BusinessListener)SpringContextUtils.getBean(listener);
		}
		Map<String, Object> variables = new HashMap<String, Object>();
		String param = request.getParameter("param");
		if(StringUtils.isNotEmpty(param)){
			JSONObject jsonObject = JSONObject.fromObject(param);
			if(jsonObject != null){
				Set<String> set = jsonObject.keySet();
				for(String s: set){
					variables.put(s, jsonObject.get(s));
				}
			}
		}
		if(null!=applyUser && !"".equals(applyUser.trim()) && variables.size()>0){
			String assigneeKey=(String)variables.get("startAssigneeKey");
			if(null!=assigneeKey && !"".equals(assigneeKey.trim())){
				variables.put(assigneeKey.trim(), applyUser.trim());
			}
		}
		addJSONObject("flag", workService.startBusiness(businessListener, process, variables));
		return SUCCESS;
	}
	*/

    public String backOutBusinessWork() throws Exception{
        String businessId = request.getParameter("id");
        String listener = request.getParameter("listener");
        if(StringUtils.isEmpty(listener)){
            addJSONObject("flag", false);
            return SUCCESS;
        }
        boolean flag = workService.updateUndoBusinessWork(businessId, listener);
        addJSONObject("flag", flag);
        return SUCCESS;
    }

    /**
     * 审批意见添加页面
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 5, 2013
     */
    public String commentAddPage() throws Exception{

        String taskid = request.getParameter("taskid");
        String businessid = request.getParameter("businessid");
        String processid = request.getParameter("processid");

        Map ret = workService.doComment(processid, businessid, taskid);
        Process process = (Process) ret.get("process");
        Comment comment = (Comment) ret.get("comment");

        Definition definition = definitionService.getDefinitionByKey(process.getDefinitionkey(), process.getDefinitionid(), null);
        Map map = workService.getNextDefinitionTaskInfo(definition.getDefinitionid(), process.getTaskkey());

        request.setAttribute("process", process);
        request.setAttribute("tasklist", map.get("tasklist"));

        request.setAttribute("signature", getSysUser().getName());
        request.setAttribute("time", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        request.setAttribute("comment", comment);
        return SUCCESS;
    }

    /**
     * 审批意见添加页面（精简版）
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 5, 2013
     */
    public String commentAddPage2() throws Exception{
        String taskid = request.getParameter("taskid");
        Comment comment = workService.getCommentByTask(taskid);
        if(comment == null){ //如果不存在该task的comment，则添加。
            comment = new Comment();
            comment.setTaskid(taskid);
            workService.addComment(comment);
        }
        request.setAttribute("comment", comment);
        return SUCCESS;
    }

    /**
     * 更新审批信息
     *  type：0暂存信息1保存并流转到下一步2精简审批
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 6, 2013
     */
    public String updateCommentInfo() throws Exception{

        String type = request.getParameter("type");
        String instanceid = request.getParameter("instanceid");                 // instanceid
        String nexttaskkey = request.getParameter("nexttaskkey");               // 下一节点taskkey
        String nexttasktype = request.getParameter("nexttasktype");             // 下一节点类型
        String sign = request.getParameter("sign");                             // 1: 会签
        String nextAssignee = request.getParameter("nextAssignee");             // 下一步候选人
        String signnexttask = request.getParameter("signnexttask");             // 会签下一节点
        String signNextAssignee = request.getParameter("signNextAssignee");     // 会签下一节点处理人

        if("endEvent".equals(nexttasktype)) {
            nexttaskkey = null;
        }

        Process process = workService.getProcess(instanceid, "2");

        if(process == null
                || "2".equals(process.getStatus())
                || "3".equals(process.getStatus())
                || "9".equals(process.getStatus())
                || "1".equals(process.getIsend())) {

            addJSONObject("flag", false);
            return SUCCESS;
        }

        if(process == null || (!process.getTaskid().equals(comment.getTaskid()) && !"1".equals(sign) )) {

            addJSONObject("flag", false);
            return SUCCESS;
        }

        boolean flag = false;

        if("1".equals(sign)) {
            flag = workService.updateSignComment(comment);
        } else {
            flag = workService.updateComment(comment, type, nextAssignee, nexttaskkey, signnexttask, signNextAssignee);
        }
        addJSONObject("flag", flag);
        return SUCCESS;
    }

    /**
     * 审批信息列表页面
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 7, 2013
     */
    public String commentListPage() throws Exception{

        String id = request.getParameter("id");
        String type = request.getParameter("type"); //1为instanceid,2为businessKey

        String instanceid = request.getParameter("instanceid");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Comment> list = workService.getCommentList(id, type);

        List result = new ArrayList();
        List<String> signTaskList = new ArrayList<String>();

        Process process = workService.getProcess(id, "1");

        for(Comment comment : list){
            if("process_begin".equals(comment.getTaskkey())){
                comment.setAssignee("<font color='red'>流程开始</font>");
            } else if("process_end".equals(comment.getTaskkey())){
                comment.setAssignee("<font color='red'>流程结束</font>");
            } else{

                if(definitionService.isSignTask(process.getDefinitionid(), comment.getTaskkey())) {
                    signTaskList.remove(comment.getTaskkey());
                    signTaskList.add(comment.getTaskkey());
                }

                if(StringUtils.isEmpty(comment.getEndtime())){
                    String timeStr = "";
                    long time = new Date().getTime() - dateFormat.parse(comment.getBegintime()).getTime();
                    time = time / 1000;
                    if(time/(60*60*24) != 0){
                        timeStr += time/(60*60*24) + "天";
                    }
                    time = time - ((time/(60*60*24))*60*60*24);
                    if(time/(60*60) != 0){
                        timeStr += time/(60*60) + "时";
                    }
                    time = time - ((time/(60*60))*60*60);
                    if(time/60 != 0){
                        timeStr += time/60 + "分";
                    }
                    time = time - ((time/60)*60);
                    timeStr += time + "秒";
                    comment.setAssignee("<font color='green'>办理中 已用时：" + timeStr + "</font>");
                }
                else {
                    String timeStr = "";
                    long time = dateFormat.parse(comment.getEndtime()).getTime() - dateFormat.parse(comment.getBegintime()).getTime();
                    time = time / 1000;
                    if(time/(60*60*24) != 0){
                        timeStr += time/(60*60*24) + "天";
                    }
                    time = time - ((time/(60*60*24))*60*60*24);
                    if(time/(60*60) != 0){
                        timeStr += time/(60*60) + "时";
                    }
                    time = time - ((time/(60*60))*60*60);
                    if(time/60 != 0){
                        timeStr += time/60 + "分";
                    }
                    time = time - ((time/60)*60);
                    timeStr += time + "秒";
                    comment.setAssignee("<font color='green'>已办理 用时：" + timeStr + "</font>");
                }
            }
            result.add(comment);
        }

        if("3".equals(type)) {
            process = workService.getProcess(id, "1");
        } else {
            process = workService.getProcess(id, "3");
            if(StringUtils.isNotEmpty(instanceid)) {
                process = workService.getProcess(instanceid, "2");
            }
        }

        request.setAttribute("list", result);
        request.setAttribute("process", process);
        request.setAttribute("signTaskList", new JSONArray(signTaskList).toString());
        if(StringUtils.isNotEmpty(process.getTaskid())) {

            request.setAttribute("assignee", baseService.getSysUserById(process.getAssignee()));
        } else {

            request.setAttribute("assignee", baseService.getSysUserById(process.getApplyuserid()));
        }
        return SUCCESS;
    }

    /**
     * 流程审批流程图显示页面
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 10, 2013
     */
    public String commentImgPage() throws Exception{
        String type = request.getParameter("type"); //type为1时id为instanceid，type为2时id为businessId
        String id = request.getParameter("id");
        Process process = null;
        if("1".equals(type)){
            process = workService.getProcess(id, "2");
        }
        else if("2".equals(type)){
            process = workService.getProcess(id, "3");
        } else {
            process = workService.getProcess(id, "1");
        }
        request.setAttribute("definitionkey", process.getDefinitionkey());
        request.setAttribute("definitionid", process.getDefinitionid());
        request.setAttribute("instanceid", process.getInstanceid());
        return SUCCESS;
    }

    /**
     * 获取审批流程图片相关信息
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 10, 2013
     */
    public String getCommentImgInfo() throws Exception{

        String instanceId = request.getParameter("instanceid");
        String processid = request.getParameter("processid");
        if(StringUtils.isEmpty(instanceId)) {
            Process process = workService.getProcess(processid, "1");
            if(process == null) {
                addJSONArray(new ArrayList());
                return SUCCESS;
            }
            instanceId = process.getInstanceid();
        }
        addJSONArray(workService.getCommentImgInfo(instanceId));
        return SUCCESS;
    }

    /**
     * 委托页面
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 8, 2013
     */
    public String delegatePage() throws Exception{

        return SUCCESS;
    }

    /**
     * 委托规则添加页面
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 8, 2013
     */
    public String delegateAddPage() throws Exception{
        request.setAttribute("user", getSysUser());
        return SUCCESS;
    }

    /**
     * 添加委托规则
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 8, 2013
     */
    @UserOperateLog(key="work", type=2, value="")
    public String addDelegate() throws Exception{
        delegate.setUserid(getSysUser().getUserid());
        if(StringUtils.isEmpty(delegate.getBegindate()) && StringUtils.isEmpty(delegate.getEnddate())){
            delegate.setRemain("1");
        }
        if("1".equals(delegate.getRemain())){
            delegate.setBegindate("");
            delegate.setEnddate("");
        }
        if(StringUtils.isEmpty(delegate.getRemain())){
            delegate.setRemain("0");
        }
        boolean flag = workService.addDelegate(delegate);
        addJSONObject("flag", flag);
        addLog("添加工作委托，编号："+ delegate.getId(), flag);
        return SUCCESS;
    }

    /**
     * 委托规则列表
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 9, 2013
     */
    public String getDelegateList() throws Exception{
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Map map = CommonUtils.changeMap(request.getParameterMap());
        SysUser user = getSysUser();
        map.put("userid", user.getUserid());
        pageMap.setCondition(map);
        PageData pageData = workService.getDelegateData(pageMap);
        List result = new ArrayList();
        List list = pageData.getList();
        for(int i = 0; i<list.size();i++){
            try{
                Delegate delegate = (Delegate)list.get(i);
                if("1".equals(delegate.getRemain())){
                    delegate.setBegindate("一直有效");
                    delegate.setStatus("启用");
                }
                else{
                    if(StringUtils.isEmpty(delegate.getBegindate()) && StringUtils.isNotEmpty(delegate.getEnddate())){
                        if(format.parse(delegate.getEnddate()).getTime()>new Date().getTime()){
                            delegate.setStatus("启用");
                        }
                        else{
                            delegate.setStatus("停用");
                        }
                        delegate.setBegindate("截止于"+delegate.getEnddate());
                    }
                    else if(StringUtils.isEmpty(delegate.getEnddate()) && StringUtils.isNotEmpty(delegate.getBegindate())){
                        if(format.parse(delegate.getBegindate()).getTime()<new Date().getTime()){
                            delegate.setStatus("启用");
                        }
                        else{
                            delegate.setStatus("停用");
                        }
                        delegate.setBegindate("开始于"+delegate.getBegindate());
                    }
                    else if(StringUtils.isEmpty(delegate.getEnddate()) && StringUtils.isEmpty(delegate.getBegindate())){
                        delegate.setStatus("停用");
                    }
                    else{
                        if((format.parse(delegate.getBegindate()).getTime()<=new Date().getTime()) && ((format.parse(delegate.getEnddate()).getTime()+ 86400000)>=new Date().getTime())){
                            delegate.setStatus("启用");
                        }
                        else{
                            delegate.setStatus("停用");
                        }
                        delegate.setBegindate(delegate.getBegindate() + " 至 " + delegate.getEnddate());
                    }
                }
                result.add(delegate);
            }catch (Exception e) {}
        }
        pageData.setList(result);
        addJSONObject(pageData);
        return SUCCESS;
    }

    /**
     * 委托规则修改页面
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 9, 2013
     */
    public String delegateEditPage() throws Exception{
        String id = request.getParameter("id");
        Delegate delegate = workService.getDelegate(id);
        if(delegate != null){
            Definition definition = definitionService.getDefinitionByKey(delegate.getDefinitionkey(), null, "1");
            request.setAttribute("definition", definition);
        }
        request.setAttribute("delegate", delegate);
        request.setAttribute("user", getSysUser());
        return SUCCESS;
    }

    /**
     * 修改委托规则
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 9, 2013
     */
    @UserOperateLog(key="work", type=3, value="")
    public String updateDelegate() throws Exception{
        if(StringUtils.isEmpty(delegate.getBegindate()) && StringUtils.isEmpty(delegate.getEnddate())){
            delegate.setRemain("1");
        }
        if("1".equals(delegate.getRemain())){
            delegate.setBegindate("");
            delegate.setEnddate("");
        }
        if(StringUtils.isEmpty(delegate.getRemain())){
            delegate.setRemain("0");
        }
        boolean flag = workService.updateDelegate(delegate);
        addJSONObject("flag", flag);
        addLog("修改工作委托信息，编号："+ delegate.getId(), flag);
        return SUCCESS;
    }

    /**
     * 删除委托规则
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 9, 2013
     */
    public String deleteDelegate() throws Exception{
        String id = request.getParameter("id");
        addJSONObject("flag", workService.deleteDelegate(id));
        return SUCCESS;
    }

    /**
     * 委托日志查询
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 9, 2013
     */
    public String delegateLogPage() throws Exception{

        return SUCCESS;
    }

    /**
     * 委托日志查询列表
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 9, 2013
     */
    public String getDelegateLogList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        map.put("userid", getSysUser().getUserid());
        pageMap.setCondition(map);
        addJSONObject(workService.getDelegateLogData(pageMap));
        return SUCCESS;
    }


    /**
     * 工作查询页面
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 8, 2013
     */
    public String workQueryPage() throws Exception{

        return SUCCESS;
    }

    /**
     * 工作查询列表
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 9, 2013
     */
    public String getWorkQueryList() throws Exception{

        Map<String, String> condition = CommonUtils.changeMap(request.getParameterMap());

        String keywords = CommonUtils.nullToEmpty(condition.get("keywords"));
        List<String> list = new ArrayList<String>();
        String arr[] = keywords.split(" ");
        for(int i = arr.length - 1; i >= 0; i--) {

            if(StringUtils.isNotEmpty(arr[i])) {

                list.add(arr[i]);
            }
        }
        // condition.put("keywords", list.t);
        Map condition2 = new HashMap(condition);
        condition2.put("keywords", list.size() == 0 ? null : list);

        condition2.put("userid", getSysUser().getUserid());
        pageMap.setCondition(condition2);
        addJSONObject(workService.getWorkQueryData(pageMap));
        return SUCCESS;
    }

    /**
     * 日志查询列表
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 9, 2013
     */
    public String logQueryPage() throws Exception{

        return SUCCESS;
    }

    /**
     * 日志查询列表
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 10, 2013
     */
    public String getLogQueryList() throws Exception{
        Map<String, String> condition = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(condition);
        addJSONObject(workService.getLogData(pageMap));
        return SUCCESS;
    }

    public String isHandled() throws Exception {

        String id = request.getParameter("id");
        String taskid = request.getParameter("taskid");

        Process process = workService.getProcess(id, "1");

        Map map = new HashMap();
        if(process == null || StringUtils.isEmpty(taskid) || !taskid.equals(process.getTaskid())) {

            map.put("flag", false);
            addJSONObject(map);
            return SUCCESS;
        }
        map.put("flag", true);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 驳回页面
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-8-23
     */
    public String workBackPage() throws Exception {

        String definitionkey = request.getParameter("definitionkey");
        String taskkey = request.getParameter("taskkey");
        String id = request.getParameter("id");

        TaskDefinition taskDefinition = null;
        Process process = workService.getProcess(id, "1");
        String taskid = process.getTaskid();

        Comment c = workService.getCommentByTask(taskid);
        if(c == null){ //如果不存在该task的comment，则添加。
            c = new Comment();
            c.setTaskid(taskid);
            workService.addComment(c);
        }

        List<Comment> cList = workService.getRealCommentList(process.getId(), process.getInstanceid(), true, true);
        List<Map> preCommentList = new ArrayList<Map>();
        for(Comment comment : cList) {
            Map temp = new HashMap();
            temp.put("taskid", comment.getTaskid());
            temp.put("taskkey", comment.getTaskkey());
            temp.put("taskname", comment.getTaskname());
            temp.put("sign", definitionService.isSignTask(process.getDefinitionid(), comment.getTaskkey()));
            preCommentList.add(temp);
        }

        request.setAttribute("list", preCommentList);
        request.setAttribute("task", taskDefinition);
        request.setAttribute("process", process);
        request.setAttribute("comment", c);
        return SUCCESS;
    }

    /**
     * 驳回流程至某一节点
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Oct 31, 2013
     */
    public String backWork() throws Exception{
        String id = request.getParameter("id");
        String taskkey = request.getParameter("taskkey");

        // 驳回到之前任一节点（非申请节点）

        if(StringUtils.isNotEmpty(taskkey)) {

            Process process = workService.getProcess(id, "1");

            if(process == null
                    || "2".equals(process.getStatus())
                    || "3".equals(process.getStatus())
                    || "9".equals(process.getStatus())
                    || "1".equals(process.getIsend())) {

                Map map = new HashMap();
                map.put("flag", false);
                addJSONObject(map);
            }

            // 验证工作是否有效
            if(!comment.getTaskid().equals(process.getTaskid())) {

                Map map = new HashMap();
                map.put("flag", false);
                map.put("msg", " 该工作已经被处理。");
                addJSONObject(map);
            }

            boolean ret = workService.backWork(id, taskkey, comment);
            Map map = new HashMap();
            map.put("flag", ret);
            addJSONObject(map);
            return SUCCESS;
        }

        // 驳回到重新申请节点
        // TODO
        // 以下代码在业务上不可能跑到。
//        Process process = workService.getProcess(id, "1");
//
//        Map map = new HashMap();
//        boolean flag = workService.deleteAndInsertProcess(process.getInstanceid(), comment);
//        map.put("flag", flag);
//        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 获取下一节点审批人员信息
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-12-3
     */
    public String getNextTaskDefinition() throws Exception {

        String taskkey = request.getParameter("taskkey");
        String id = request.getParameter("id");
        String type = request.getParameter("type");

        Process process = workService.getProcess(id, type);
        Definition definition = definitionService.getDefinitionByKey(process.getDefinitionkey(), process.getDefinitionid(), null);

        Map map = workService.getNextTaskDefinition(definition.getDefinitionid(), id, taskkey, process.getApplyuserid());

        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 删除附件
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-10-7
     */
    public String deleteAttach() throws Exception {

        String attachid = request.getParameter("attachid");

        boolean flag = workService.deleteAttach(attachid);

        Map map = new HashMap();
        map.put("flag", flag);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 根据processid, instanceid, taskkey获取流程节点审批信息
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-10-7
     */
    public String listComments() throws Exception {

        String businesskey = request.getParameter("businesskey");
        String processid = request.getParameter("processid");
        String instanceid = request.getParameter("instanceid");
        String taskkey = CommonUtils.nullToEmpty(request.getParameter("taskkey"));
        String agree = request.getParameter("agree");
        String definitionkey = request.getParameter("definitionkey");
        Process process = null;
        List comments = new ArrayList();
        String now = "";

        boolean agreeflag = true;
        if(StringUtils.isEmpty(taskkey)) {
            agreeflag = false;
        }

        if(StringUtils.isNotEmpty(businesskey)) {

            process = workService.getProcess(businesskey, "3");
            if(process != null){

                processid = process.getId();
                instanceid = process.getInstanceid();
                definitionkey = process.getDefinitionkey();
                now = process.getTaskkey();
            }
        }

        process = workService.getProcess(processid, "1");
        if(process != null && StringUtils.isNotEmpty(process.getInstanceid())) {

            instanceid = process.getInstanceid();
            definitionkey = process.getDefinitionkey();
            now = process.getTaskkey();
        }

        process = workService.getProcess(instanceid, "2");
        if(process != null && StringUtils.isNotEmpty(process.getId())) {

            processid = process.getId();
            definitionkey = process.getDefinitionkey();
            now = process.getTaskkey();
        }

        Map map = new HashMap();
        map.put("processid", processid);
        map.put("instanceid", instanceid);
        String[] taskkeys = taskkey.split(",");
        map.put("taskkeys", taskkeys);
        if(StringUtils.isEmpty(taskkey)) {
            map.put("taskkeys", null);
        }
        map.put("agree", agree);

        List<Comment> list = new ArrayList<Comment>();
        if(agreeflag) {

            list = workService.getRealCommentList(process.getId(), process.getInstanceid(), agreeflag, true);

            if(StringUtils.isEmpty(taskkey)) {
                String[] temp = new String[list.size()];
                for(int i = 0; i < temp.length; i++) {
                    String str = list.get(i).getTaskkey();
                    temp[i] = str;
                }
                taskkeys = temp;
            }

            if(taskkeys.length > 0) {

                List userTaskList = definitionService.getUserTaskList(definitionkey, process.getDefinitionid());
                for(int i = 0; i < taskkeys.length; i++) {
                    Map task = new HashMap();
                    String tkey = taskkeys[i];

                    task.put("taskkey", tkey);
                    for(int j = 0; j < userTaskList.size(); j++) {

                        if(tkey.equals(((Map)userTaskList.get(j)).get("key"))) {
                            task.put("name", ((Map)userTaskList.get(j)).get("name"));
                            break;
                        }
                    }

                    List<Map> signCommentList = new ArrayList<Map>();
                    // 是否会签
                    boolean sign = historyService.createHistoricTaskInstanceQuery().processInstanceId(instanceid).taskDefinitionKey(tkey).list().size() > 1;
                    for(Comment comment : list) {

                        String assignee = comment.getAssignee();	// 审批人
                        SysUser user = getSysUserById(assignee);
                        if(user == null) {
                            user = new SysUser();
                        }

                        if(tkey.equals(comment.getTaskkey())) {

                            if("process_begin".equals(tkey)) {

                                task.put("start", "1");
                                task.put("time", comment.getBegintime());
                                task.put("name", "开始");
                                task.put("user", user.getName());
                                task.put("timefrom", comment.getBegintime());
                                continue;

                            } else if("process_end".equals(tkey)) {

                                task.put("end", "1");
                                task.put("time", comment.getBegintime());
                                task.put("name", "结束");
                                // task.put("user", user.getName());
                                continue;

                            } else {

                                // task.put("user", comment.getSignature());
                                task.put("user", user.getName());
                                task.put("time", CommonUtils.dateStringChange(comment.getEndtime(), "yyyy年MM月dd日"));
                                if(StringUtils.isEmpty(comment.getEndtime())) {
                                    task.put("time", "");
                                }
                                task.put("timefrom", CommonUtils.nullToEmpty(comment.getBegintime()));
                                task.put("timeto", CommonUtils.nullToEmpty(comment.getEndtime()));
                                task.put("comment", CommonUtils.nullToEmpty(comment.getComment()));
                                task.put("taskid", comment.getTaskid());
                                task.put("agree", comment.getAgree());

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                                Date timefrom = sdf.parse(comment.getBegintime());
                                Date timeto = new Date();
                                if(StringUtils.isNotEmpty(comment.getEndtime())) {
                                    timeto = sdf.parse(comment.getEndtime());
                                }

                                long span =  (timeto.getTime() - timefrom.getTime()) / 1000;
                                long second = span % 60;
                                long minute = span % (60 * 60) / 60;
                                long hour = span % (3600 * 24) / (60 * 60);
                                long day = span / (3600 * 24);
                                StringBuilder sb = new StringBuilder();
                                if(day > 0) {
                                    sb.append(day + "天");
                                }
                                if(hour > 0) {
                                    sb.append(hour + "时");
                                }
                                if(minute > 0) {
                                    sb.append(minute + "分");
                                }
                                if(second > 0) {
                                    sb.append(second + "秒");
                                }
                                task.put("timespan", sb.toString().length() > 0 ? sb.toString() : "0秒");


                                if(sign) {

                                    signCommentList.add(task);
                                    continue;
                                }

                            }
                        }

                    }

                    if(sign) {
                        comments.add(signCommentList);
                    } else {
                        comments.add(task);
                    }
                }
            }

        } else {

            list = workService.getComments(map);

            List<Map> signCommentList = new ArrayList<Map>();

            for(Comment comment : list) {

                Map task = new HashMap();

                List userTaskList = definitionService.getUserTaskList(definitionkey, process.getDefinitionid());

                task.put("taskkey", comment.getTaskkey());
                for(int j = 0; j < userTaskList.size(); j++) {

                    if(comment.getTaskkey().equals(((Map)userTaskList.get(j)).get("key"))) {
                        task.put("name", ((Map)userTaskList.get(j)).get("name"));
                        break;
                    }
                }

                // 是否会签
//                boolean sign = historyService.createHistoricTaskInstanceQuery().processInstanceId(instanceid).taskDefinitionKey(comment.getTaskkey()).list().size() > 1;
                boolean sign = definitionService.isSignTask(process.getDefinitionid(), comment.getTaskkey());
                //String assignee = comment.getAssignee();	// 审批人
                SysUser user = getSysUserById(comment.getAssignee());
                if(user == null) {
                    user = new SysUser();
                }

                if ("process_begin".equals(comment.getTaskkey())) {

                    task.put("start", "1");
                    task.put("time", comment.getBegintime());
                    task.put("name", "开始");
                    task.put("user", user.getName());
                    task.put("timefrom", comment.getBegintime());
                    comments.add(task);
                    continue;

                } else if ("process_end".equals(comment.getTaskkey())) {

                    task.put("end", "1");
                    task.put("time", comment.getBegintime());
                    task.put("name", "结束");
                    // task.put("user", user.getName());
                    comments.add(task);
                    continue;

                } else {

                    // task.put("user", comment.getSignature());
                    task.put("user", user.getName());
                    task.put("time", CommonUtils.dateStringChange(comment.getEndtime(), "yyyy年MM月dd日"));
                    if (StringUtils.isEmpty(comment.getEndtime())) {
                        task.put("time", "");
                    }
                    task.put("timefrom", CommonUtils.nullToEmpty(comment.getBegintime()));
                    task.put("timeto", CommonUtils.nullToEmpty(comment.getEndtime()));
                    task.put("comment", CommonUtils.nullToEmpty(comment.getComment()));
                    task.put("taskid", comment.getTaskid());
                    task.put("agree", comment.getAgree());

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    Date timefrom = sdf.parse(comment.getBegintime());
                    Date timeto = new Date();
                    if (StringUtils.isNotEmpty(comment.getEndtime())) {
                        timeto = sdf.parse(comment.getEndtime());
                    }

                    long span = (timeto.getTime() - timefrom.getTime()) / 1000;
                    long second = span % 60;
                    long minute = span % (60 * 60) / 60;
                    long hour = span % (3600 * 24) / (60 * 60);
                    long day = span / (3600 * 24);
                    StringBuilder sb = new StringBuilder();
                    if (day > 0) {
                        sb.append(day + "天");
                    }
                    if (hour > 0) {
                        sb.append(hour + "时");
                    }
                    if (minute > 0) {
                        sb.append(minute + "分");
                    }
                    if (second > 0) {
                        sb.append(second + "秒");
                    }
                    task.put("timespan", sb.toString().length() > 0 ? sb.toString() : "0秒");
                    if(sign) {
                        signCommentList.add(task);
                    }
                }
                if(!sign) {
                    if(signCommentList.size() > 0) {
                        comments.add(signCommentList);
                        signCommentList = new ArrayList<Map>();
                    }
                    comments.add(task);
                }

            }
            if(signCommentList.size() > 0) {
                comments.add(signCommentList);
            }
        }

        SysUser assignee = new SysUser();
        if(process != null && StringUtils.isNotEmpty(process.getAssignee())) {

            assignee = getSysUserById(process.getAssignee());
        }

        Map result = new HashMap();
        process = (Process)CommonUtils.deepCopy(process);
        process.setHtml(null);
        process.setJson(null);
        process.setPhonehtml(null);
        result.put("process", process);
        result.put("comments", comments);
        result.put("assignee", assignee);
        addJSONObject(result);
        return SUCCESS;
    }

    /**
     * 获取流程附件List
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-10-9
     */
    public String listAttach() throws Exception {

        String processid = CommonUtils.nullToEmpty(request.getParameter("processid"));

        Map ret = workService.listAttach(processid);
        Map map = new HashMap();

        if(ret == null) {

            addJSONArray(new ArrayList());
            return SUCCESS;
        }

        addJSONObject(ret);
        return SUCCESS;

//        Map ret = new HashMap();
//        int count = 0;
//
//        if(StringUtils.isEmpty(processid)) {
//            addJSONArray(new ArrayList());
//            return SUCCESS;
//        }
//
//        Process process = workService.getProcess(processid, "1");
//        if(process != null && StringUtils.isNotEmpty(process.getId())) {
//            processid = process.getId();
//        }
//
//        List<Map> cfileList = new ArrayList<Map>();
//
//        {
//            Map p = new HashMap();
//            List<Map> fileList = workService.selectAttachList(processid, null);
//            if(fileList.size() >0) {
//
//                String userid = (String) fileList.get(0).get("adduserid");
//                SysUser user = getSysUserById(userid);
//
//                Map comment = new HashMap();
//                comment.put("taskname", "申请");
//
//                Map res = new HashMap();
//                res.put("comment", comment);
//                res.put("files", fileList);
//                res.put("addusername", user.getName());
//                res.put("delete", true);
//                if(getSysUser().getUserid().equals(user.getUserid())) {
//                    res.put("delete", true);
//                }
//
//                cfileList.add(res);
//            }
//        }
//
//        List<Comment> commentList = workService.getCommentListByProcessid(processid);
//        for(Comment comment : commentList) {
//
//            List<Map> fileList = workService.selectAttachList(processid, comment.getId());
//
//            if(fileList.size() > 0) {
//
//                String userid = (String) fileList.get(0).get("adduserid");
//                SysUser user = getSysUserById(userid);
//
//                Map res = new HashMap();
//                res.put("comment", comment);
//                res.put("taskkey", comment.getTaskkey());
//                res.put("files", fileList);
//                res.put("addusername", user.getName());
//                res.put("delete", false);
//                if(getSysUser().getUserid().equals(user.getUserid())) {
//                    res.put("delete", true);
//                }
//                count ++;
//                cfileList.add(res);
//            } else if(fileList.size() == 0 && process.getTaskid().equals(process.getTaskid())) {
//
//                SysUser user = getSysUser();
//
//                Map res = new HashMap();
//                res.put("comment", comment);
//                res.put("taskkey", comment.getTaskkey());
//                res.put("files", fileList);
//                res.put("addusername", user.getName());
//                res.put("delete", true);
//
//                cfileList.add(res);
//            }
//        }
//
//        request.setAttribute("filelist", cfileList);
//
//        ret.put("filelist", cfileList);
//        ret.put("count", count);
//        addJSONObject(ret);
//
//        return SUCCESS;
    }

    /**
     *
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-10-11
     */
    public String addAttach() throws Exception {

        String attachid = request.getParameter("attachid");
        String processid = request.getParameter("processid");
        String commentid = request.getParameter("commentid");

        List<Comment> list = workService.getCommentListByProcessid(processid);
        Comment comment = list.get(list.size() - 1);

        boolean flag = workService.addAttach(processid, comment.getId(), attachid);

        Process process = workService.getProcess(processid, "1");

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Map map = new HashMap();
        map.put("flag", flag);
        map.put("process", process);
        map.put("adduser", getSysUser());
        map.put("addtime", sdf.format(date));
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     *
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-10-27
     */
    public String canBackWork() throws Exception {

        String id = request.getParameter("id");

        Process process = workService.getProcess(id, "1");

        String definitionkey = process.getDefinitionkey();
        String taskkey = process.getTaskkey();

        Map map = new HashMap();
        map.put("flag", workService.getRealCommentList(process.getId(), process.getInstanceid(), true, true).size() > 0);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     *
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-10-27
     */
    public String updateProcessBussinessId() throws Exception {

        String id = request.getParameter("id");
        String businessid = request.getParameter("businessid");
        process = workService.getProcess(id, "1");
        process.setBusinessid(businessid);

        int ret = workService.updateProcess(process);

        Map map = new HashMap();
        map.put("flag", ret > 0);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 删除流程
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-10-27
     */
    public String deleteProcess() throws Exception {

        String id = request.getParameter("id");
        String type = request.getParameter("type");
        int ret = 0;
        String category = "";
        Process process = null;

        // 1为彻底删除
        if("1".equals(type)) {

            process = workService.getProcess(id, "1");
            ret = workService.deleteWork(id);
            category = "销毁";

        } else {

            process = workService.getProcess(id, "1");
            process.setStatus("3");
            ret = workService.updateProcess(process);
            category = "删除";
        }

        Map map = new HashMap();
        map.put("flag", ret > 0);
        addJSONObject(map);

        WorkLog log = new WorkLog();
        log.setInstanceid(process.getInstanceid());
        log.setDefinitionkey(process.getDefinitionkey());
        log.setType("3");
        log.setContent(getSysUser().getName() + " " + category + "了流程");
        log.setTitle(process.getTitle());
        log.setProcessid(process.getId());
        log.setApplyuserid(process.getApplyuserid());
        log.setApplyusername(process.getApplyusername());
        log.setTaskname(category);
        log.setDefinitionkey(process.getDefinitionkey());
        workService.addLog(log);

        return SUCCESS;
    }

    /**
     * 恢复已删除的流程
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-10-27
     */
    public String recoverProcess() throws Exception {

        String id = request.getParameter("id");

        Process process = workService.getProcess(id, "1");
        process.setStatus("1");
        int ret = workService.updateProcess(process);

        Map map = new HashMap();
        map.put("flag", ret > 0);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 工作统计页面
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-10-28
     */
    public String workStatisticsPage() throws Exception {

        String definitionkey = request.getParameter("definitionkey");
        List<String> keys = new ArrayList<String>();
        List<Map> list = new ArrayList<Map>();

        if(StringUtils.isNotEmpty(definitionkey)) {

            keys.add(definitionkey);

        } else {

            List<Map> definitionList = definitionService.getDefinitionTree(null);
            for(int i = 0; i < definitionList.size(); i++){

                Map definition = definitionList.get(i);
                if(definition.get("isparent") != null && definition.get("isparent").equals("1")) {

                    definitionList.remove(definition);
                    i--;
                } else {
                    keys.add((String) definition.get("unkey"));
                }
            }
        }

        if(keys.size() == 0) {
            keys = workService.selectAllDefinitionKey();
        }

        for(String str : keys) {

            if(StringUtils.isEmpty(str)) {
                continue;
            }

            String[] types = {
                    "6", 	// 未接收
                    "7",	// 办理中
                    "8",	// 已办结
                    "9",	// 已删除
                    "12"	// 已挂起
            };

            int sum = 0;
            Map stat = new HashMap();
            stat.put("key", str);
            for(String type : types) {

                Map map = new HashMap();
                map.put("type", type);
                map.put("definitionkey", str);
                map.put("userid", getSysUser().getUserid());
                pageMap.setCondition(map);

                int count = workService.getProcessCount(pageMap);
                sum = sum + count;
                stat.put("type" + type, count);
            }
            stat.put("sum", sum);
            if(sum > 0) {

                Definition definition = definitionService.getDefinitionByKey(str, null, "1");
                stat.put("name", definition.getName());

                list.add(stat);
            }
        }

        request.setAttribute("list", list);
        return SUCCESS;
    }

    /**
     * 工作管理页面
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-11-10
     */
    public String workManagePage() throws Exception {

        return SUCCESS;
    }

    /**
     * 挂起工作
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-11-11
     */
    public String suspendProcess() throws Exception {

        String id = request.getParameter("id");
        Process process = workService.getProcess(id, "1");

        if(process == null
                || "9".equals(process.getStatus())
                || "1".equals(process.getIsend())) {

            Map map = new HashMap();
            map.put("flag", false);
            addJSONObject(map);
        }

        boolean ret = workService.suspendWork(id);

        WorkLog log = new WorkLog();
        log.setInstanceid(process.getInstanceid());
        log.setType("3");
        log.setContent(getSysUser().getName() + " 挂起了流程");
        log.setTitle(process.getTitle());
        log.setProcessid(process.getId());
        log.setApplyuserid(process.getApplyuserid());
        log.setApplyusername(process.getApplyusername());
        log.setTaskname("挂起");
        workService.addLog(log);

        Map map = new HashMap();
        map.put("flag", ret);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 激活已经被挂起的工作
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-11-11
     */
    public String activateProcess() throws Exception {

        String id = request.getParameter("id");
        Process process = workService.getProcess(id, "1");

        if(process == null
                || "9".equals(process.getStatus())
                || "1".equals(process.getIsend())) {

            Map map = new HashMap();
            map.put("flag", false);
            addJSONObject(map);
        }

        boolean ret = workService.activateWork(id);

        WorkLog log = new WorkLog();
        log.setInstanceid(process.getInstanceid());
        log.setType("3");
        log.setContent(getSysUser().getName() + " 激活了流程");
        log.setTitle(process.getTitle());
        log.setProcessid(process.getId());
        log.setApplyuserid(process.getApplyuserid());
        log.setApplyusername(process.getApplyusername());
        log.setTaskname("激活");
        workService.addLog(log);

        Map map = new HashMap();
        map.put("flag", ret);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 查询工作Process
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-11-11
     */
    public String selectProcess() throws Exception {

        String id = request.getParameter("id");
        String type = request.getParameter("type");

        Process process = workService.selectProcess(id, type);

//        addJSONObject("process", process);
        Map map = new HashMap();
        map.put("process", process);
        if (process != null) {
            map.put("definition", definitionService.getDefinitionByKey(process.getDefinitionkey(), process.getDefinitionid(), null));
        }
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     *
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-11-12
     */
    public String workPiePage() throws Exception {

        return SUCCESS;
    }

    /**
     *
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-11-14
     */
    public String quitProcess() throws Exception {

        String id = request.getParameter("id");
        String userid = getSysUser().getUserid();

        Process process = workService.getProcess(id, "1");

        Map map = new HashMap();
        map.put("id", id);
        map.put("userid", userid);

        int ret = workService.quitUnsavedWork(map);

        if(ret > 0) {

            workService.deleteProcessInstance(process.getInstanceid(), getSysUser().getName());

            WorkLog log = new WorkLog();
            log.setInstanceid(process.getInstanceid());
            log.setDefinitionkey(process.getDefinitionkey());
            log.setType("3");
            log.setContent(getSysUser().getName() + " 舍弃了工作");
            log.setTitle(process.getTitle());
            log.setProcessid(process.getId());
            log.setApplyuserid(process.getApplyuserid());
            log.setApplyusername(process.getApplyusername());
            log.setTaskname("舍弃");
            workService.addLog(log);
        }

        addJSONObject("flag", ret > 0);
        return SUCCESS;
    }

    /**
     * 提交工作至工作流
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-12-31
     */
    public String startBusinessWork() throws Exception {

        String businessid = request.getParameter("businessid");			// 业务ID
        String title = request.getParameter("title");					// 工作标题
        String definitionkey = request.getParameter("definitionkey");	// 流程定义key
        String taskkey = request.getParameter("taskkey");				// 需要提交到的用户节点key
        String assignee = request.getParameter("assignee");				// taskkey节点对应的审批用户

        Map map = new HashMap();

        if(StringUtils.isEmpty(businessid)) {

            map.put("message", "业务编号为空！");
            map.put("flag", false);
            return SUCCESS;
        }

        if(StringUtils.isEmpty(definitionkey)) {

            map.put("message", "流程标识key为空！");
            map.put("flag", false);
            return SUCCESS;
        }

        if(StringUtils.isNotEmpty(taskkey) && StringUtils.isEmpty(assignee)) {

            map.put("message", "未指定办理人！");
            map.put("flag", false);
            return SUCCESS;
        }

        SysUser user = getSysUser();
        Definition definition = definitionService.getDefinitionByKey(definitionkey, null, "1");

        Process process = new Process();
        process.setTitle(title);							// 标题
        process.setDefinitionkey(definitionkey);			// 流程定义key
        process.setDefinitionname(definition.getName());	// 流程名
        process.setBusinessid(businessid);					// 业务ID
        process.setApplyuserid(user.getUserid());			//
        process.setApplyusername(user.getUsername());

        workService.addNewWork(process);
        workService.startNewWork(process.getId(), getSysUser().getUserid());
        process = workService.getProcess(process.getId(), "1");

        if(StringUtils.isNotEmpty(taskkey)) {

            // 添加节点审批信息
            // begindate
            Comment beginComment = new Comment();
            beginComment.setTaskid(process.getTaskid());
            workService.addComment(beginComment);
            // enddate
            Comment endComment = new Comment();
            endComment.setId(beginComment.getId());
            endComment.setTaskid(process.getTaskid());
            endComment.setAssignee(user.getUserid());
            endComment.setAgree("1");
            endComment.setComment(null);

            workService.updateComment(endComment, "1", assignee, taskkey, null, null);
        }

        addJSONObject("flag", true);
        return SUCCESS;
    }

    /**
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 10, 2015
     */
    public String workFormContentPage() throws Exception {

        String id = request.getParameter("id");

        Process process = workService.getProcess(id, "1");

        String definitionkey = process.getDefinitionkey();
        Definition definition = definitionService.getDefinitionByKey(definitionkey, process.getDefinitionid(), null);
        String formkey = definition.getFormkey();
        Form form = formService.getFormByKey(formkey);

        List<FormItemRule> rules = formService.selectFormRuleList(process.getDefinitionkey(), process.getDefinitionid(), process.getTaskkey());

        if(rules.size() == 0) {

//            formService.initFormRule(definitionkey, process.getTaskkey());
//            rules = formService.selectFormRuleList(process.getDefinitionkey(), process.getDefinitionid(), process.getTaskkey());

            List<FormItem> items = formService.selectFormItemList(formkey);

            for(FormItem item : items) {

                FormItemRule rule = new FormItemRule();
                rule.setUnkey(item.getUnkey());
                rule.setItemid(item.getItemid());
                rule.setItemname(item.getItemname());
                rule.setDefinitionkey(definitionkey);
                rule.setDefinitionid(process.getDefinitionid());
                rule.setTaskkey(process.getTaskkey());
                rule.setWritable(1);
                rule.setVisible(1);
                rule.setRequired("0");

                rules.add(rule);
            }
        }

        List<FormItem> items = formService.selectFormItemList(formkey);

        Map macro = new HashMap();
        for(String key :  macros.keySet()) {

            String format = (String) macros.get(key);
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = new Date();

            String str = sdf.format(date);
            macro.put(key, str);
        }

        SysUser user = getSysUser();
        macro.put("sys_userid", user.getUserid());
        macro.put("sys_username", user.getName());
        macro.put("sys_deptid", user.getDepartmentid());
        macro.put("sys_deptname", user.getDepartmentname());

        // signature
        macro.put("sys_signature_name", user.getName());
        macro.put("sys_signature_date", CommonUtils.getTodayDataStr());

        Map editor = new HashMap();
        editor.put("macro", macro);

        // wdate 设定
        Map wdate = new HashMap();
        String today = CommonUtils.getTodayDataStr();
        String month01 = CommonUtils.getCurrentYearStr() + "-" + CommonUtils.getCurrentMonthStr() + "-01";
        String month31 = CommonUtils.getCurrentMonthLastDay();
        String year01 = CommonUtils.getCurrentYearStr() + "-01-01";
        String year365 = CommonUtils.getCurrentYearStr() + "-12-31";
        wdate.put("today", today);
        wdate.put("month01", month01);
        wdate.put("month31", month31);
        wdate.put("year01", year01);
        wdate.put("year365", year365);
        editor.put("wdate", wdate);

        request.setAttribute("editor", JSONUtils.mapToJsonStr(editor));
        request.setAttribute("macro", JSONUtils.mapToJsonStr(macro));
        request.setAttribute("wdate", JSONUtils.mapToJsonStr(wdate));
        request.setAttribute("rules", JSONUtils.listToJsonStr(rules));
        request.setAttribute("items", JSONUtils.listToJsonStr(items));
        request.setAttribute("definition", definition);
        request.setAttribute("form", form);
        request.setAttribute("process", process);
        request.setAttribute("json", CommonUtils.bytes2String(process.getJson()));
        request.setAttribute("detail", CommonUtils.bytes2String(form.getDetail()));
        request.setAttribute("html", CommonUtils.bytes2String(process.getHtml()));
        return SUCCESS;
    }

    /**
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 3, 2015
     */
    public String traceListPage() throws Exception {

        String id = request.getParameter("id");

        List<DataTrace> traces = workService.selectDataTraceList(id);

        List<Map> list = new ArrayList<Map>();
        for(int i = 0; i < traces.size(); i++) {

            DataTrace trace = traces.get(i);
            Map map = JSONUtils.jsonStrToMap(JSONUtils.objectToJsonStr(trace));
            map.put("trace", CommonUtils.bytes2String(trace.getTrace()));
            map.put("adddate", trace.getAdddate());

            list.add(map);
        }

        Process process = workService.getProcess(id, "1");
        Definition definition = definitionService.getDefinitionByKey(process.getDefinitionkey(), process.getDefinitionid(), null);

        request.setAttribute("traces", list);
        request.setAttribute("definition", definition);
        return SUCCESS;
    }

    /**
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 10, 2015
     */
    public String captureKeyword() throws Exception {

        String id = request.getParameter("id");
        String keyword1 = request.getParameter("keyword1");
        String keyword2 = request.getParameter("keyword2");
        String keyword3 = request.getParameter("keyword3");
        String keyword4 = request.getParameter("keyword4");
        String keyword5 = request.getParameter("keyword5");

        Process process = workService.getProcess(id, "1");
        process.setKeyword1(keyword1);
        process.setKeyword2(keyword2);
        process.setKeyword3(keyword3);
        process.setKeyword4(keyword4);
        process.setKeyword5(keyword5);

        int ret = workService.updateKeywords(process);

        addJSONObject("flag", ret > 0);
        return SUCCESS;
    }

    /**
     * 作废工作
     * @return
     * @throws Exception
     * @author limin
     * @date May 6, 2015
     */
    public String rollbackProcess() throws Exception {

        String id = request.getParameter("id");
        int ret = workService.rollbackProcess(workService.getProcess(id, "1"));

        Map map = new HashMap();
        map.put("flag", ret > 0);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 更换处理人页面
     * @return
     * @throws Exception
     * @author limin
     * @date Nov 16, 2015
     */
    public String changeAssigneePage() throws Exception {

        String id = request.getParameter("id");
        String taskid = CommonUtils.nullToEmpty(request.getParameter("taskid"));

        Process process = workService.getProcess(id, "1");

        // 该工作失效
        if(process == null || !taskid.equals(process.getTaskid())) {

            request.setAttribute("valid", false);
            return SUCCESS;
        }

        // 所有审批信息
        List<Comment> comments = workService.getCommentListByProcessid(process.getId());

        if(comments.size() > 0) {

            Comment current = comments.get(comments.size() - 1);    // 当前审批信息
            request.setAttribute("comment", current);
        }

        request.setAttribute("valid", true);
        request.setAttribute("process", process);
        return SUCCESS;
    }

    /**
     * 查看流程审批信息页面
     * @return
     * @throws Exception
     * @author limin
     * @date Jan 4, 2016
     */
    public String commentViewPage() throws Exception {

        return SUCCESS;
    }

    /**
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 18, 2016
     */
    public String handlerLogListPage() throws Exception {

        PageData data = workService.selectHandlerLogList(pageMap);
        List<Map> items = workService.selectListenerClazzes();

        request.setAttribute("list", data.getList());
        request.setAttribute("items", items);
        return SUCCESS;
    }

    /**
     * 显示Handler 日志详情页面
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 19, 2016
     */
    public String handlerLogInfoPage() throws Exception {

        String id = request.getParameter("id");

        HandlerLog log = workService.selectHandlerLogById(id);

        List params = new ArrayList();

        Object p1 = (byteToObject(log.getParam1()));
        Object p2 = (byteToObject(log.getParam2()));
        Object p3 = (byteToObject(log.getParam3()));
        Object p4 = (byteToObject(log.getParam4()));
        Object p5 = (byteToObject(log.getParam5()));

        if(p1 != null) {
            String jsonStr = objectToJsonStr(p1);
            params.add(jsonStr);
            request.setAttribute("param1", jsonStr);
        }
        if(p2 != null) {
            String jsonStr = objectToJsonStr(p2);
            params.add(jsonStr);
            request.setAttribute("param2", jsonStr);
        }
        if(p3 != null) {
            String jsonStr = objectToJsonStr(p3);
            params.add(jsonStr);
            request.setAttribute("param3", jsonStr);
        }
        if(p4 != null) {
            String jsonStr = objectToJsonStr(p4);
            params.add(jsonStr);
            request.setAttribute("param4", jsonStr);
        }
        if(p5 != null) {
            String jsonStr = objectToJsonStr(p5);
            params.add(jsonStr);
            request.setAttribute("param5", jsonStr);
        }

        Object r = (byteToObject(log.getReturnobj()));

        request.setAttribute("params", params);

        if(r instanceof Map) {

            request.setAttribute("returnmap", JSONUtils.mapToJsonStr((Map) r));

        } else {

            request.setAttribute("returnvalue", r.toString());
        }

        return SUCCESS;
    }

    /**
     * byte[] → 对象
     * @param bytes
     * @return
     * @author limin
     * @date Apr 19, 2016
     */
    private Object byteToObject(byte[] bytes) throws IOException {

        if(bytes == null) {
            return null;
        }

        Object obj = null;
        ByteArrayInputStream bi = null;
        ObjectInputStream oi = null;

        try {

            bi = new ByteArrayInputStream(bytes);
            oi = new ObjectInputStream(bi);

            obj = oi.readObject();
        } catch (Exception e) {

            System.out.println("translation" + e.getMessage());
            e.printStackTrace();
            return null;

        } finally {

            if(oi != null) {
                oi.close();
            }

            if(bi != null) {
                bi.close();
            }
        }
        return obj;
    }

    /**
     * 对象反射
     * @param obj
     * @return
     * @author limin
     * @date Apr 19, 2016
     */
    private String objectToJsonStr(Object obj) throws Exception {

        if(obj instanceof List) {

            return JSONUtils.listToJsonStr((List) obj);
        } else if(obj instanceof String) {
            return obj.toString();
        }

        return JSONUtils.objectToJsonStr(obj);
    }

    /**
     * 查询handler 日志
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 19, 2016
     */
    public String getHandlerLogList() throws Exception {

        Map condition = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(condition);

        PageData data = workService.selectHandlerLogList(pageMap);
        addJSONObject(data);
        return SUCCESS;
    }

    /**
     * 流程节点会签用户设置（含设置项）
     * @return
     * @throws Exception
     * @author limin
     * @date Jun 16, 2017
     */
    public String getNextTasksBySignTask() throws Exception {

        String instanceid = request.getParameter("instanceid");
        String taskkey = request.getParameter("taskkey");

        List list = workService.getNextTasksBySignTask(instanceid, taskkey);

        addJSONArray(list);
        return SUCCESS;
    }
}

