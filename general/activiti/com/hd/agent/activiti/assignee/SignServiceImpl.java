/**
 * @(#)SignService.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 8, 2017 limin 创建版本
 */
package com.hd.agent.activiti.assignee;

import com.hd.agent.activiti.model.Comment;
import com.hd.agent.activiti.model.DefinitionSign;
import com.hd.agent.activiti.model.ProcessTask;
import com.hd.agent.activiti.service.IDefinitionService;
import com.hd.agent.activiti.service.IWorkService;
import com.hd.agent.activiti.service.impl.BaseServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 会签
 *
 * Created by limin on 2017/6/8.
 */
public class SignServiceImpl extends BaseServiceImpl {

    private static Logger log = Logger.getLogger(SignServiceImpl.class.getName());

    private IWorkService workService;

    private IDefinitionService definitionService;

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

    private void endOperate(ActivityExecution execution, String instanceid, String taskkey, boolean agree) throws Exception {
        execution.setVariable(taskkey + "_sign", agree);
        if (log.isLoggable(Level.FINE)) {
            log.fine(String.format("instanceid: %s, taskkey: %s complete, sign: %s ", instanceid, taskkey, true));
        }
        execution.setVariable("run_listener", agree ? "1" : "0");
    }

    /**
     * 节点是否完成
     *
     * @param execution
     * @return
     * @throws Exception
     * @author limin
     * @date Jun 16, 2017
     */
    public boolean complete(ActivityExecution execution) throws Exception {

        String instanceid = execution.getProcessInstanceId();
        String definitionid = execution.getProcessDefinitionId();
        String taskkey = execution.getCurrentActivityId();

//        if(execution.hasVariable(taskkey + "_sign") && (Boolean) execution.getVariable(taskkey + "_sign")) {
//            return (Boolean) execution.getVariable(taskkey + "_sign");
//        }

        boolean sign = definitionService.isSignTask(definitionid, taskkey);

        int ok = 0;
        int ng = 0;

        execution.removeVariable("run_listener");
        if(sign) {
            DefinitionSign definitionSign = definitionService.getDefinitionSignByKey(definitionid, taskkey);

            if (definitionSign == null) {
                throw new Exception("会签规则未设置！");
            }

            int votenum = definitionSign.getVotenum();
            String votetype = definitionSign.getVotetype();
            String counttype = definitionSign.getCounttype();
            String decisiontype = definitionSign.getDecisiontype();

            int total = (Integer) execution.getVariable("nrOfInstances");
//            int complete = (Integer) execution.getVariable("nrOfCompletedInstances");
//            int active = (Integer) execution.getVariable("nrOfActiveInstances");

            List<ProcessTask> processTaskList = workService.getProcessTaskListByTaskkey(instanceid, taskkey);
            for(ProcessTask processTask : processTaskList) {
                String taskid = processTask.getTaskid();
                Comment comment = workService.getCommentByTask(taskid);
                if (comment == null) {
                    continue;
                }

                if("1".equals(comment.getAgree()) && StringUtils.isNotEmpty(comment.getEndtime())) {
                    ok++;
                } else if("0".equals(comment.getAgree()) && StringUtils.isNotEmpty(comment.getEndtime())) {
                    ng++;
                }
            }

            // 全部都同意
            if(ok >= total) {
//                execution.setVariable(taskkey + "_sign", true);
//                if (log.isLoggable(Level.FINE)) {
//                    log.fine(String.format("instanceid: %s, taskkey: %s complete, sign: %s ", instanceid, taskkey, true));
//                }
                endOperate(execution, instanceid, taskkey, true);
                return true;
            }

            // 全部都反对
            if(ng >= total) {
//                execution.setVariable(taskkey + "_sign", false);
//                if (log.isLoggable(Level.FINE)) {
//                    log.fine(String.format("instanceid: %s, taskkey: %s complete, sign: %s ", instanceid, taskkey, false));
//                }
                endOperate(execution, instanceid, taskkey, false);
                return true;
            }

            // 达条件统计
            if("1".equals(counttype)) {

                // 通过
                if("1".equals(decisiontype)) {

                    // 绝对票数
                    if("1".equals(votetype)) {

                        if(ok >= votenum) {
//                            execution.setVariable(taskkey + "_sign", true);
//                            if (log.isLoggable(Level.FINE)) {
//                                log.fine(String.format("instanceid: %s, taskkey: %s complete, sign: %s ", instanceid, taskkey, true));
//                            }
                            endOperate(execution, instanceid, taskkey, true);
                            return true;
                        }

                        if(ok + ng >= total) {
                            if(ng > total - votenum) {
//                                execution.setVariable(taskkey + "_sign", false);
//                                if (log.isLoggable(Level.FINE)) {
//                                    log.fine(String.format("instanceid: %s, taskkey: %s complete, sign: %s ", instanceid, taskkey, false));
//                                }
                                endOperate(execution, instanceid, taskkey, false);
                                return true;
                            } else {
//                                execution.setVariable(taskkey + "_sign", true);
//                                if (log.isLoggable(Level.FINE)) {
//                                    log.fine(String.format("instanceid: %s, taskkey: %s complete, sign: %s ", instanceid, taskkey, true));
//                                }
                                endOperate(execution, instanceid, taskkey, true);
                                return true;
                            }
                        }

                    // 百分比
                    } else if("0".equals(votetype)) {

                        int percent = ok * 100 / total;
                        if(percent >= votenum) {
//                            execution.setVariable(taskkey + "_sign", true);
//                            if (log.isLoggable(Level.FINE)) {
//                                log.fine(String.format("instanceid: %s, taskkey: %s complete, sign: %s ", instanceid, taskkey, true));
//                            }
                            endOperate(execution, instanceid, taskkey, true);
                            return true;
                        } else if(ok + ng >= total) {
//                            execution.setVariable(taskkey + "_sign", false);
//                            if (log.isLoggable(Level.FINE)) {
//                                log.fine(String.format("instanceid: %s, taskkey: %s complete, sign: %s ", instanceid, taskkey, false));
//                            }
                            endOperate(execution, instanceid, taskkey, false);
                            return true;
                        }
                    }

                // 拒绝
                } else if("0".equals(decisiontype)) {

                    // 绝对票数
                    if("1".equals(votetype)) {

                        // 全部都反对
                        if(ng >= votenum) {
//                            execution.setVariable(taskkey + "_sign", false);
//                            if (log.isLoggable(Level.FINE)) {
//                                log.fine(String.format("instanceid: %s, taskkey: %s complete, sign: %s ", instanceid, taskkey, false));
//                            }
                            endOperate(execution, instanceid, taskkey, false);
                            return true;
                        }

                        if(ok + ng >= total) {
                            if(ok > total - votenum) {
//                                execution.setVariable(taskkey + "_sign", true);
//                                if (log.isLoggable(Level.FINE)) {
//                                    log.fine(String.format("instanceid: %s, taskkey: %s complete, sign: %s ", instanceid, taskkey, true));
//                                }
                                endOperate(execution, instanceid, taskkey, true);
                                return true;
                            } else {
//                                execution.setVariable(taskkey + "_sign", false);
//                                if (log.isLoggable(Level.FINE)) {
//                                    log.fine(String.format("instanceid: %s, taskkey: %s complete, sign: %s ", instanceid, taskkey, false));
//                                }
                                endOperate(execution, instanceid, taskkey, false);
                                return true;
                            }
                        }

                    // 百分比
                    } else if("0".equals(votetype)) {

                        int percent = ng * 100 / total;
                        // 反对百分比超过反对线
                        if(percent >= votenum) {
//                            execution.setVariable(taskkey + "_sign", false);
//                            if (log.isLoggable(Level.FINE)) {
//                                log.fine(String.format("instanceid: %s, taskkey: %s complete, sign: %s ", instanceid, taskkey, false));
//                            }
                            endOperate(execution, instanceid, taskkey, false);
                            return true;
                        } else if(ok + ng >= total) {
//                            execution.setVariable(taskkey + "_sign", true);
//                            if (log.isLoggable(Level.FINE)) {
//                                log.fine(String.format("instanceid: %s, taskkey: %s complete, sign: %s ", instanceid, taskkey, true));
//                            }
                            endOperate(execution, instanceid, taskkey, true);
                            return true;
                        }

                    }
                }

            // 全签收统计
            } else if("0".equals(counttype)) {

                if(ok + ng >= total) {
                    // 通过
                    if("1".equals(decisiontype)) {

                        // 绝对票数
                        if("1".equals(votetype)) {

                            if(ok >= votenum) {
//                                execution.setVariable(taskkey + "_sign", true);
//                                if (log.isLoggable(Level.FINE)) {
//                                    log.fine(String.format("instanceid: %s, taskkey: %s complete, sign: %s ", instanceid, taskkey, true));
//                                }
                                endOperate(execution, instanceid, taskkey, true);
                                return true;
                            }

                        // 百分比
                        } else if("0".equals(votetype)) {

                            int percent = ok * 100 / total;
                            if(percent >= votenum) {
//                                execution.setVariable(taskkey + "_sign", true);
//                                if (log.isLoggable(Level.FINE)) {
//                                    log.fine(String.format("instanceid: %s, taskkey: %s complete, sign: %s ", instanceid, taskkey, true));
//                                }
                                endOperate(execution, instanceid, taskkey, true);
                                return true;
                            }
                        }

                    // 拒绝
                    } else if("0".equals(counttype)) {

                        // 绝对票数
                        if("1".equals(votetype)) {

                            if(ng >= votenum) {
//                                execution.setVariable(taskkey + "_sign", false);
//                                if (log.isLoggable(Level.FINE)) {
//                                    log.fine(String.format("instanceid: %s, taskkey: %s complete, sign: %s ", instanceid, taskkey, false));
//                                }
                                endOperate(execution, instanceid, taskkey, false);
                                return true;
                            }

                            // 百分比
                        } else if("0".equals(votetype)) {

                            int percent = ng * 100 / total;
                            if(percent >= votenum) {
//                                execution.setVariable(taskkey + "_sign", false);
//                                if (log.isLoggable(Level.FINE)) {
//                                    log.fine(String.format("instanceid: %s, taskkey: %s complete, sign: %s ", instanceid, taskkey, false));
//                                }
                                endOperate(execution, instanceid, taskkey, false);
                                return true;
                            }

                        }
                    }
                }
            }
        } else {
            return true;
        }
        return false;
    }

    /**
     * 会签是否通过
     *
     * @param execution
     * @return
     * @throws Exception
     * @author limin
     * @date Jun 16, 2017
     */
    public boolean ok(ActivityExecution execution) throws Exception {

        String instanceid = execution.getProcessInstanceId();
        String definitionid = execution.getProcessDefinitionId();
//        String taskkey = execution.getCurrentActivityId();
        String taskkey = execution.getActivity().getIncomingTransitions().get(0).getSource().getId();
        if(((ActivityImpl)execution.getActivity()).getActivityBehavior() instanceof ParallelMultiInstanceBehavior) {
            taskkey = ((ExecutionEntity) execution).getActivityId();
        }

        boolean sign = definitionService.isSignTask(definitionid, taskkey);

        int ok = 0;
        int ng = 0;

        if(sign) {

            DefinitionSign definitionSign = definitionService.getDefinitionSignByKey(definitionid, taskkey);

            if (definitionSign == null) {
                throw new Exception("会签规则未设置！");
            }

            int votenum = definitionSign.getVotenum();
            String votetype = definitionSign.getVotetype();
            String counttype = definitionSign.getCounttype();
            String decisiontype = definitionSign.getDecisiontype();
            
            int total = ((List) execution.getVariable("assigneeList")).size();

            List<ProcessTask> processTaskList = workService.getProcessTaskListByTaskkey(instanceid, taskkey);
            for(ProcessTask processTask : processTaskList) {
                String taskid = processTask.getTaskid();
                Comment comment = workService.getCommentByTask(taskid);
                if (comment == null) {
                    continue;
                }

                if("1".equals(comment.getAgree()) && StringUtils.isNotEmpty(comment.getEndtime())) {
                    ok++;
                } else if("0".equals(comment.getAgree()) && StringUtils.isNotEmpty(comment.getEndtime())) {
                    ng++;
                }
            }

            // 全部都同意
            if(ok >= total) {
                return true;
            }

            // 全部都反对
            if(ng >= total) {
                return false;
            }

            // 达条件统计
            if("1".equals(counttype)) {

                // 通过
                if("1".equals(decisiontype)) {

                    // 绝对票数
                    if("1".equals(votetype)) {

                        if(ok >= votenum) {
                            return true;
                        }

                        if(ok + ng >= total) {
                            if(ng > total - votenum) {
                                return false;
                            } else {
                                return true;
                            }
                        }

                    // 百分比
                    } else if("0".equals(votetype)) {

                        int percent = ok * 100 / total;
                        if(percent >= votenum) {
                            return true;
                        } else {
                            return false;
                        }

                    }

                // 拒绝
                } else if("0".equals(decisiontype)) {

                    // 绝对票数
                    if("1".equals(votetype)) {

                        // 全部都反对
                        if(ng >= votenum) {
                            return false;
                        }

                        if(ok + ng >= total) {
                            if(ok > total - votenum) {
                                return true;
                            } else {
                                return false;
                            }
                        }

                    // 百分比
                    } else if("0".equals(votetype)) {

                        int percent = ng * 100 / total;
                        // 反对百分比超过反对线
                        if(percent >= votenum) {
                            return false;
                        } else {
                            return true;
                        }

                    }
                }

            // 全签收统计
            } else if("0".equals(counttype)) {

                // 通过
                if("1".equals(decisiontype)) {

                    // 同意票数超过绝对票数
                    if("1".equals(votetype)) {
                        if(ok >= votenum) {
                            return true;
                        } else {
                            return false;
                        }
                    }

                    // 同意票数超过比例线
                    if("0".equals(votetype)) {
                        if(ok * 100 / total >= votenum) {
                            return true;
                        } else {
                            return false;
                        }
                    }

                // 反对
                } else if("0".equals(decisiontype)) {

                    // 反对票数超过绝对票数
                    if("1".equals(votetype)) {
                        if(ng >= votenum) {
                            return false;
                        } else {
                            return true;
                        }
                    }

                    // 反对票数超过比例线
                    if("0".equals(votetype)) {
                        if(ng * 100 / total >= votenum) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                }

                return true;
            }
        }

        return false;
    }

    /**
     * 会签是否ng
     *
     * @param execution
     * @return
     * @throws Exception
     * @author limin
     * @date Jun 16, 2017
     */
    public boolean ng(ActivityExecution execution) throws Exception {

        return !ok(execution);
    }
}
