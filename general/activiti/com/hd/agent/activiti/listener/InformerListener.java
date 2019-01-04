/**
 * @(#)InformerListener.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015-5-18 limin 创建版本
 */
package com.hd.agent.activiti.listener;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.activiti.model.DefinitionTask;
import com.hd.agent.activiti.model.Process;
import com.hd.agent.activiti.service.IDefinitionService;
import com.hd.agent.activiti.service.IWorkService;
import com.hd.agent.common.service.impl.BaseServiceImpl;

/**
 * Event Task Handler
 *
 * @author limin
 */
public class InformerListener extends BaseServiceImpl implements TaskListener {

    /**
     *
     */
    protected IDefinitionService definitionService;

    /**
     *
     */
    protected IWorkService workService;

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
    public void notify(DelegateTask task) throws Exception {

        String definitionkey = task.getProcessDefinitionId();
        String definitionid = task.getProcessDefinitionId();
        String taskkey = task.getTaskDefinitionKey();
        String taskname = task.getName();
        String event = task.getEventName();
        String instanceid = task.getExecution().getProcessInstanceId();

        // 不同意时，不执行handler
        if("complete".equals(event)) {

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

        DefinitionTask dt = definitionService.getDefinitionTaskByKey(definitionkey, task.getProcessDefinitionId(), taskkey);

        if(dt != null && StringUtils.isNotEmpty(dt.getEndremindapplier())) {

            Process process = workService.getProcess(instanceid, "2");

            Map map = new HashMap();
            map.put("mtiptype", dt.getEndremindapplier());
            map.put("receivers", process.getApplyuserid());
            String businessUrl = "act/workViewPage.do?processid="+ process.getId();
            map.put("remindurl", businessUrl);
//            map.put("title", "工作流提醒");
            map.put("title", "您发起的工作已被审批");
            map.put("msgtype", "4");
            map.put("content", "您发起的工作(OA编号：" + process.getId() + ")在节点[" + taskname + "]已经审批完成。点击链接可以查看详情。");
            map.put("tabtitle", "工作查看");
            addMessageReminder(map);
        }

    }
}
