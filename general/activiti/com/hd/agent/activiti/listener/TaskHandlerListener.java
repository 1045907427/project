/**
 * @(#)TaskHandler.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015-1-5 limin 创建版本
 */
package com.hd.agent.activiti.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.activiti.assignee.SignServiceImpl;
import com.hd.agent.activiti.dao.ProcessMapper;
import com.hd.agent.activiti.model.Comment;
import com.hd.agent.activiti.model.Definition;
import com.hd.agent.activiti.model.EventHandler;
import com.hd.agent.activiti.model.Process;
import com.hd.agent.activiti.service.IDefinitionService;
import com.hd.agent.activiti.service.IWorkService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.activiti.dao.EventHandlerMapper;
import com.hd.agent.common.util.SpringContextUtils;
import org.apache.poi.ss.formula.functions.Even;

/**
 * Event Task Handler
 *
 * @author limin
 */
public class TaskHandlerListener implements TaskListener {

    /**
     * 默认CREATE_HANDLER
     */
    private static String DEFAULT_CREATE_HANDLER = "assignee";

    /**
     * 默认COMPLETE_HANDLER
     */
    private static String DEFAULT_COMPLETE_HANDLER = "informer";

    private EventHandlerMapper handlerMapper;

    private ProcessMapper processMapper;

    private IWorkService workService;

    private IDefinitionService definitionService;

    private SignServiceImpl signService;

    public EventHandlerMapper getHandlerMapper() {
        return handlerMapper;
    }

    public void setHandlerMapper(EventHandlerMapper handlerMapper) {
        this.handlerMapper = handlerMapper;
    }

    public ProcessMapper getProcessMapper() {
        return processMapper;
    }

    public void setProcessMapper(ProcessMapper processMapper) {
        this.processMapper = processMapper;
    }

    public IWorkService getWorkService() {
        return workService;
    }

    public void setWorkService(IWorkService workService) {
        this.workService = workService;
    }

    public IDefinitionService getDefinitionService() {
        return definitionService;
    }

    public void setDefinitionService(IDefinitionService definitionService) {
        this.definitionService = definitionService;
    }

    public SignServiceImpl getSignService() {
        return signService;
    }

    public void setSignService(SignServiceImpl signService) {
        this.signService = signService;
    }

    @Override
    public void notify(DelegateTask task) throws Exception {

        String definitionkey = null;
        String definitionid = null;
        String taskkey = task.getTaskDefinitionKey();

        String event = task.getEventName();
        String instanceid = task.getProcessInstanceId();

        Process process = processMapper.getProcessByInstance(instanceid);
        if(process == null) {
            definitionkey = task.getProcessDefinitionId();
            definitionid = task.getProcessDefinitionId();
        } else {
            definitionkey = process.getDefinitionid();
            definitionid = process.getDefinitionid();
        }

        boolean sign = definitionService.isSignTask(definitionid, taskkey);

        // 不同意时，不执行handler
        if(EVENTNAME_COMPLETE.equals(event) && !sign) {

            Object object = task.getVariable(taskkey + "_audit");

            if(object == null) {
                return ;
            }

            boolean audit = (Boolean) object;
            if(!audit) {
                return ;
            }
        }

        if(StringUtils.isNotEmpty(definitionkey)) {

            definitionkey = definitionkey.substring(0, definitionkey.indexOf(":"));
        }

        Definition definition = definitionService.getDefinitionByKey(definitionkey, definitionid, null);

        Map param = new HashMap();
        param.put("definitionkey", definitionkey);
        param.put("definitionid", definitionid);
        param.put("taskkey", taskkey);
        param.put("event", event);
        List<EventHandler> list = handlerMapper.selectEventHandlerByTask(param);

        // 执行默认handler： assignee
        if(EVENTNAME_CREATE.equals(event)) {

//            workService.deleteProcessTaskByInstanceid(instanceid);
            executeHandler(task, DEFAULT_CREATE_HANDLER);
        }

        // 如果run_listener为0，表明不需要执行listener
        String run = (String) task.getVariable("run_listener");
        task.removeVariable("run_listener");
        if(!"0".equals(run)) {

            // 执行配置的handler
            for (EventHandler handler : list) {

                if(EVENTNAME_CREATE.equals(handler.getEvent())) {
                    continue;
                }

                if(!sign) {
                    executeHandler(task, handler.getHandler());
                } else if(signService.ok((ActivityExecution) task.getExecution())) {
                    executeHandler(task, handler.getHandler());
                }
            }

            // 执行create handler
            // run create handler
            if(process != null && EVENTNAME_CREATE.equals(event)) {

                List<Comment> comments2 = workService.getCommentListByInstanceid(instanceid);
                List<Comment> comments = new ArrayList<Comment>();
                // 相邻两个comment如果taskkey相同，则当成一个处理
                for(Comment comment : comments2) {

                    if(comments2.indexOf(comment) == 0) {
                        comments.add(comment);
                    }

                    if(comment.getTaskkey().equals(comments.get(comments.size() - 1).getTaskkey())) {
                        comments.remove(comments.size() - 1);
                    }

                    boolean signComment = definitionService.isSignTask(process.getDefinitionid(), comment.getTaskkey());
                    if(signComment) {
                        Boolean signAgree = (Boolean) task.getExecution().getVariable(comment.getTaskkey() + "_sign");
                        comment.setAgree("0");
                        if(signAgree != null) {
                            comment.setAgree(signAgree ? "1" : "0");
                        }
                    }

                    comments.add(comment);
                }

                String startTaskkey = process.getTaskkey();
                String endTaskkey = taskkey;
                boolean start = false;
                boolean end = false;
                List<String> executedTaskkeys = new ArrayList<String>();

                // 未接受(收回)
                if(StringUtils.isNotEmpty(process.getTaskid())
                        && !comments.get(comments.size() - 1).getTaskid().equals(process.getTaskid())) {

                    end = true;
                    executedTaskkeys.add(taskkey);

                // 正常转交
                } else if(task.getExecution().hasVariable(process.getTaskkey() + "_audit")
                        && (Boolean) task.getExecution().getVariable(process.getTaskkey() + "_audit")) {

                // 驳回
                } else if(task.getExecution().hasVariable(process.getTaskkey() + "_audit")
                        && !(Boolean) task.getExecution().getVariable(process.getTaskkey() + "_audit")) {

                    for(int i = comments.size() - 1; i >= 0; i--) {

                        Comment comment = comments.get(i);

                        if(start && !executedTaskkeys.contains(comment.getTaskkey())) {
                            executedTaskkeys.add(comment.getTaskkey());
                        }

                        if(startTaskkey.equals(comment.getTaskkey())) {
                            executedTaskkeys = new ArrayList<String>();
                            start = true;
                        }
                        if(endTaskkey.equals(comment.getTaskkey())) {
                            end = true;
                        }

                        if(end) {
                            break;
                        }
                    }
                }

                if(!end) {
                    executedTaskkeys = new ArrayList<String>();
                }

                if(executedTaskkeys.size() > 0) {

                    List<EventHandler> handlers = handlerMapper.selectExecutedHandlers(definitionid, executedTaskkeys);

                    List<String> beanids = new ArrayList<String>();
                    for(EventHandler handler : handlers) {
                        beanids.add(handler.getHandler());
                    }

                    if(beanids.size() > 0) {

                        List<Map> oppositeHandlers = handlerMapper.selectOppositeHandlerByCompleteHandler(beanids);
                        // oppositeHandlers: {handler: 'listener1', clazz: 'com.xx.xxx.xxListener', description: 'XXX'}
                        for(Map map : oppositeHandlers) {

                            String handler = (String) map.get("handler");

                            TaskListener listener = (TaskListener) SpringContextUtils.getBean(handler);
                            if(listener != null) {

                                listener.notify(task);
                            }
                        }
                    }
                }
            }

        }

        // 执行默认handler： complete
        if(EVENTNAME_COMPLETE.equals(event) && signService.complete((ActivityExecution) task.getExecution())) {

            executeHandler(task, DEFAULT_COMPLETE_HANDLER);

            // 执行DataCollector
            List<Map> collectors = handlerMapper.selectHanderItemsByUrl(definition.getBusinessurl().split("\\?")[0], "4"); // 4: data collector
            for(Map m : collectors) {

                String handler = (String) m.get("handler");
                executeHandler(task, handler);
            }
        }
    }

    /**
     * 执行handler
     * @param task
     * @param handler
     * @throws Exception
     * @author limin
     * @date Apr 20, 2016
     */
    private void executeHandler(DelegateTask task, String handler) throws Exception {
        TaskListener listener = (TaskListener) SpringContextUtils.getBean(handler);
        if(listener != null) {

            listener.notify(task);
        }
    }
}

