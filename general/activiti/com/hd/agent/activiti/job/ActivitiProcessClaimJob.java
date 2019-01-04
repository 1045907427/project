/**
 * @(#)ActivitiProcessClaimJob.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2017-6-21 limin 创建版本
 */
package com.hd.agent.activiti.job;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.activiti.service.IWorkService;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.job.BaseJob;
import org.activiti.engine.task.Task;
import org.activiti.engine.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limin on 2017/6/21.
 */
public class ActivitiProcessClaimJob extends BaseJob {

    private TaskService taskService;

    private IWorkService workService;

    private static final int OFFSET = 500;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {

//            taskService = (TaskService)SpringContextUtils.getBean("taskService");
//            workService = (IWorkService)SpringContextUtils.getBean("workService");
//
//            int start = 0;
//            Map param = new HashMap();
//            param.put("status", "1");
//            param.put("offset", OFFSET);
//
//            List<Process> processList = null;
//
//            do {
//                param.put("start", start);
//                processList = workService.getProcessList(param);
//                for(Process process : processList) {
//
//                    if(StringUtils.isNotEmpty(process.getAssignee()) && StringUtils.isNotEmpty(process.getTaskid())) {
//                        List<Task> taskList = taskService.createTaskQuery().taskId(process.getTaskid()).list();
//                        if(taskList.size() > 0 && workService.getCommentByTask(process.getTaskid()) != null) {
//                            taskList.get(0).setAssignee(process.getAssignee());
//                            taskService.claim(process.getTaskid(), process.getAssignee());
//                        }
//                    }
//
//                }
//                start = start + OFFSET;
//
//            } while (processList != null && processList.size() > 0);

            flag = true;
        } catch (Exception e) {
            logger.error("定时器执行异常 Activiti工作认领失败", e);
        }
        super.executeInternal(jobExecutionContext);
    }
}
