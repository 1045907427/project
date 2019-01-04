/**
 * @(#)IWorkService.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 30, 2013 zhengziyong 创建版本
 */
package com.hd.agent.activiti.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.activiti.model.*;
import com.hd.agent.activiti.model.Process;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author zhengziyong
 */
public interface IWorkService {

	/**
	 * 获取常用工作
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 11, 2013
	 */
	public List getCommonWorkList() throws Exception;
	
	/**
	 * 添加新工作
	 * @param process
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 30, 2013
	 */
	public boolean addNewWork(Process process) throws Exception;
//
//	/**
//	 * 添加新工作并提交工作流
//	 * @param process
//	 * @return
//	 * @throws Exception
//	 * @author zhengziyong
//	 * @date Sep 30, 2013
//	 */
//	public boolean addStartNewWork(Process process) throws Exception;
	
	/**
	 * 更新工作
	 * @param process
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 5, 2013
	 */
	public boolean updateNewWork(Process process) throws Exception;
//
//	/**
//	 * 更新工作并提交工作流
//	 * @param process
//	 * @return
//	 * @throws Exception
//	 * @author zhengziyong
//	 * @date Oct 5, 2013
//	 */
//	public boolean updateStartNewWork(Process process) throws Exception;
	
	/**
	 * 启动工作流程
	 * @param id t_act_process表中编号
	 * @param applyUserId 指定的接受人员
	 * @param nexttaskkey 下一节点taskkey
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 8, 2013
	 */
	public boolean startNewWork(String id, String applyUserId, String... nexttaskkey) throws Exception;
	
	/**
	 * 更新流程实例信息（以instanceid条件）
	 * @param process
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 6, 2013
	 */
	public boolean updateProcessByInstance(Process process) throws Exception;
	
	/**
	 * 获取流程实例列表
	 * @param pageMap (type:1我的请求2我的办结3待办事宜4已办事宜)
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 5, 2013
	 */
	public PageData getProcessData(PageMap pageMap) throws Exception;
	
	/**
	 * 获取流程实例信息
	 * @param id
	 * @param type 1、id为记录编号，2、id为流程实例编号，3、id为业务编号，默认为1
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 5, 2013
	 */
	public Process getProcess(String id, String type) throws Exception;
	
	/**
	 * 删除工作记录
	 * @param id 
	 * @param type 1、id为记录编号，2、id为流程实例编号，默认为1
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 5, 2013
	 */
	public boolean deleteWork(String id, String type) throws Exception;
//
//	/**
//	 * 驳回上一节点
//	 * @param id
//	 * @return
//	 * @throws Exception
//	 * @author zhengziyong
//	 * @date Oct 31, 2013
//	 */
//	public boolean updateBackPrevWork(String id) throws Exception;
//
//	/**
//	 * 收回流程
//	 * @param id
//	 * @return
//	 * @throws Exception
//	 * @author zhengziyong
//	 * @date Oct 31, 2013
//	 */
//	public boolean updateTakeBackWork(String id) throws Exception;
	
	/**
	 * 撤销流程
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 28, 2013
	 */
	public boolean updateUndoWork(String id) throws Exception;
	
	/**
	 * 业务表单流程撤销工作流程
	 * @param businessId
	 * @param listener
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 28, 2013
	 */
	public boolean updateUndoBusinessWork(String businessId, String listener) throws Exception;
	
	/**
	 * 添加审批意见
	 * @param comment
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 5, 2013
	 */
	public boolean addComment(Comment comment) throws Exception;

	/**
	 * 更新审批信息
	 * @param comment
	 * @param type 			0暂存信息1保存并流转到下一步2精简审批
	 * @param nextAssignee 	指定的下一步接收人
	 * @param taskkey 		下一步节点标识
	 * @return
	 * @author zhengziyong
	 * @date Oct 6, 2013
	 */
	public boolean updateComment(Comment comment,
								 String type,
								 String nextAssignee,
								 String taskkey,
								 String signnexttask,
								 String signNextAssignee) throws Exception;

	/**
	 * 更新审批信息 (for 会签)
	 *
	 * @param comment
	 * @return
	 * @author limin
	 * @date Jun 15, 2017
	 */
	public boolean updateSignComment(Comment comment) throws Exception;

	/**
	 * 获取节点任务编号获取审批意见
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 5, 2013
	 */
	public Comment getCommentByTask(String id) throws Exception;
	
	/**
	 * 获取流程审批信息列表
	 * @param id 流程实例编号
	 * @param type 1为instanceid,2为businessKey
	 * @return 
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 7, 2013
	 */
	public List getCommentList(String id, String type) throws Exception;
	
	/**
	 * 获取审批流程图相关数据
	 * @param instanceid 流程实例编号
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 10, 2013
	 */
	public List getCommentImgInfo(String instanceid) throws Exception;
	
	/**
	 * 工作委托规则详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 9, 2013
	 */
	public Delegate getDelegate(String id) throws Exception;
	
	/**
	 * 添加工作委托信息
	 * @param delegate
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 8, 2013
	 */
	public boolean addDelegate(Delegate delegate) throws Exception;

    /**
     * 根据OA编号工作委托信息
     * @param oaid
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 23, 2016
     */
    public int deleteDelegateByOaid(String oaid) throws Exception;

    /**
     * 根据OA编号工作委托信息
     * @param oaid
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 23, 2016
     */
    public Delegate selectDelegateByOaid(String oaid) throws Exception;

    /**
	 * 更新工作委托规则
	 * @param delegate
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 9, 2013
	 */
	public boolean updateDelegate(Delegate delegate) throws Exception;
	
	/**
	 * 删除工作委托规则
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 9, 2013
	 */
	public boolean deleteDelegate(String id) throws Exception;
	
	/**
	 * 获取工作委托信息列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 9, 2013
	 */
	public PageData getDelegateData(PageMap pageMap) throws Exception;
	
	/**
	 * 获取用户的委托列表
	 * @param userId 人员编号
	 * @param definitionkey 流程标识
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 9, 2013
	 */
	public List getDelegateList(String userId, String definitionkey) throws Exception;
	
	/**
	 * 添加委托日志
	 * @param log
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 9, 2013
	 */
	public boolean addDelegateLog(DelegateLog log) throws Exception;
	
	/**
	 * 查询委托日志数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 9, 2013
	 */
	public PageData getDelegateLogData(PageMap pageMap) throws Exception;
	
	/**
	 * 工作查询
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 8, 2013
	 */
	public PageData getWorkQueryData(PageMap pageMap) throws Exception;
	
	/**
	 * 添加流程日志
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 9, 2013
	 */
	public boolean addLog(WorkLog log) throws Exception;
	
	/**
	 * 获取日志数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 9, 2013
	 */
	public PageData getLogData(PageMap pageMap) throws Exception;
//
//	/**
//	 * 启动业务表单流程
//	 * @param businessListener
//	 * @param process
//	 * @param variables
//	 * @return
//	 * @throws Exception
//	 * @author zhengziyong
//	 * @date Oct 10, 2013
//	 */
//	public boolean startBusiness(BusinessListener businessListener, Process process, Map<String, Object> variables) throws Exception;
	
	/**
	 * 添加查阅者
	 * @param taskid
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 23, 2013
	 */
	public boolean addViewer(String taskid) throws Exception;
//
//	/**
//	 * 根据processid获取节点的上一节点的task
//	 * @param id
//	 * @return
//	 * @throws Exception
//	 * @author limin
//	 * @date 2014-7-11
//	 */
//	public String getPreTaskKey(String id) throws Exception;
	
	/**
	 * 
	 * @param process
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-7-18
	 */
	public boolean updateNewWorkAndTitle(Process process) throws Exception;
	
	/**
	 * 获取下一节点信息(包含审批人员等节点设置信息)
	 * @param definitionkey 版本定义ID   xxxxxxxx:99:9999
	 * @param taskkey
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-7-23
	 */
	public Map getNextDefinitionTaskInfo(String definitionkey, String taskkey) throws Exception;
//
//	/**
//	 * 移除工作流实例
//	 * @param process
//	 * @return
//	 * @throws Exception
//	 * @author limin
//	 * @date 2014-7-25
//	 */
//	public int clearProcessInstanceInfo(Process process) throws Exception;

	/**
	 * 驳回流程
	 * @param id
	 * @param taskkey
	 * @param comment
	 * @return
	 * @author limin 
	 * @date 2014-8-25
	 */
	public boolean backWork(String id, String taskkey, Comment comment) throws Exception;
	
	/**
	 * 
	 * @param definitionid
     * @param processId
	 * @param taskkey
	 * @param applyUserId
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-10-6
	 */
	public Map getNextTaskDefinition(String definitionid, String processId, String taskkey, String applyUserId) throws Exception;
//
//	/**
//	 * 结束当前节点并强制跳转到某一结点
//	 * @param taskid
//	 * @param assignee
//	 * @param taskkey
//	 * @return
//	 * @throws Exception
//	 * @author limin
//	 * @date 2014-10-6
//	 */
//	public boolean updateCompleteTaskWithoutLog(String taskid, String assignee, String... taskkey) throws Exception;
	
	/**
	 * 删除附件
	 * @param attachid
	 * @return
	 * @author limin 
	 * @date 2014-10-7
	 */
	public boolean deleteAttach(String attachid) throws Exception;
	
	/**
	 * 根据processid获取审批信息List
	 * @param processid
	 * @return
	 * @author limin 
	 * @date 2014-10-9
	 */
	public List<Comment> getCommentListByProcessid(String processid);
	
	/**
	 * 流程添加附件
	 * @param processid
	 * @param commentid
	 * @param attachid
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-10-11
	 */
	public boolean addAttach(String processid, String commentid, String attachid) throws Exception;
	
	/**
	 * 查询工作流附件
	 * @param processid
	 * @param commentid
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-10-11
	 */
	public List<Map> selectAttachList(String processid, String commentid) throws Exception;
	
	/**
	 * 
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2014-10-13
	 */
	public List<Comment> getComments(Map map);
	
	/**
	 * 
	 * @param process
	 * @return
	 */
	public int updateProcess(Process process);
	
	/**
	 * 获取工作件数
	 * @param pageMap
	 * @throws Exception
	 * @author limin 
	 * @date 2014-10-28
	 */
	public int getProcessCount(PageMap pageMap) throws Exception;
	
	/**
	 * 根据instanceid获取审批信息List
	 * @param instanceid
	 * @return
	 * @author limin 
	 * @date 2014-10-30
	 */
	public List<Comment> getCommentListByInstanceid(String instanceid);
	
	/**
	 * 
	 * @param processid
	 * @param instanceid
	 * @param agree
	 * @param end
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-10-31
	 */
	public List<Comment> getRealCommentList(String processid, String instanceid, boolean agree, boolean end) throws Exception;
	
	/**
	 * 收回工作
	 * @param processid
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-8
	 */
	public boolean takeBackWork(String processid, String taskkey) throws Exception;
	
	/**
	 * 删除工作
	 * @param id 工作编号/OA号
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-10
	 */
	public int deleteWork(String id) throws Exception;
	
	/**
	 * 激活工作
	 * @param id 工作编号/OA号
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-11
	 */
	public boolean activateWork(String id) throws Exception;
	
	/**
	 * 挂起工作
	 * @param id 工作编号/OA号
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-11
	 */
	public boolean suspendWork(String id) throws Exception;
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-12
	 */
	public List<String> selectAllDefinitionKey() throws Exception;
	
	/**
	 * 舍弃未保存过的工作
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-14
	 */
	public int quitUnsavedWork(Map map) throws Exception;
	
	/**
	 * 
	 * @param instanceid
	 * @param reason
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-14
	 */
	public void deleteProcessInstance(String instanceid, String reason) throws Exception;
//
//	/**
//	 * 启动流程
//	 * @param title 工作标题
//	 * @param definitionkey 流程标识
//	 * @param veriables
//	 * @return
//	 * @author limin
//	 * @date 2014-12-31
//	 */
//	public boolean startBusinessWork(String title, String definitionkey, Map<String, Object> veriables) throws Exception;

    /**
     * 根据工作编号
     * @param processid
     * @return
     * @author limin
     * @date Mar 3, 2015
     */
    public List<DataTrace> selectDataTraceList(String processid) throws Exception;

    /**
     * 更新工作关键字
     * @param process
     * @return
     * @author limin
     * @date 2015-04-10
     */
    public int updateKeywords(Process process) throws Exception;

    /**
     * 作废工作
     * @param process
     * @return
     * @author limin
     * @date 2015-05-06
     */
    public int rollbackProcess(Process process) throws Exception;

    /**
     * 处理工作
     * @param definitionkey
     * @param instanceid
     * @param taskid
     * @param title
     * @return
     * @throws Exception
     * @author limin
     * @date Nov 17, 2015
     */
    public Map doHandleWork(String definitionkey, String instanceid, String taskid, String title) throws Exception;

    /**
     *
     * @param processid
     * @param businessid
     * @param taskid
     * @return
     * @throws Exception
     * @author limin
     * @date Nov 18, 2015
     */
    public Map doComment(String processid, String businessid, String taskid) throws Exception;

    /**
     * 处理工作(手机)
     * @param process
     * @return
     * @throws Exception
     * @author limin
     * @date Nov 18, 2015
     */
    public Map doHandleWork2(Process process) throws Exception;

	/**
	 * 插入handle执行log
	 * @param log
	 * @return
	 * @author limin
	 * @date Apr 13, 2016
	 */
	public int addHandlerLog(HandlerLog log);
//
//	/**
//	 * 获取流程审批信息List，每个节点只保留最新的审批信息
//	 * @param instanceid
//	 * @return
//	 * @author limin
//	 * @date Apr 18, 2016
//	 */
//	public List<Comment> getCommentsList(String instanceid);

	/**
	 * 查询Handler 日志数据
	 * @param map
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Apr 19, 2016
     */
	public PageData selectHandlerLogList(PageMap map) throws Exception;

	/**
	 * 查询Handler 日志信息
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Apr 19, 2016
	 */
	public HandlerLog selectHandlerLogById(String id) throws Exception;

	/**
	 * 新增datagrace
	 *
	 * @param trace
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Apr 19, 2016
	 */
	public int addDataTrace(DataTrace trace) throws Exception;

	/**
	 * 获取所有的handler list
	 * @return
	 * @author limin
	 * @date Apr 20, 2016
	 */
	public List<Map> selectListenerClazzes();

    /**
     * 获取工作的附件List
     *
     * @return
     * @author limin
     * @date May 28, 2016
     */
    public Map listAttach(String processid) throws Exception;

	/**
	 * 查询工作（有附带信息）
	 * @param id
	 * @param type
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date May 24, 2017
	 */
	public Process selectProcess(String id, String type) throws Exception;

	/**
	 * 新增工作任务
	 *
	 * @param processTask
	 * @return
	 * @author limin
	 * @date Jun 14, 2017
	 */
	public int addProcessTask(ProcessTask processTask);

	/**
	 * 根据taskkey删除ProcessTask
	 *
	 * @param instanceid
	 * @param taskkey
	 * @return
	 * @author limin
	 * @date Jun 14, 2017
	 */
	public int deleteProcessTaskByTaskkey(String instanceid, String taskkey);

	/**
	 * 根据taskkey删除ProcessTask
	 *
	 * @param instanceid
	 * @return
	 * @author limin
	 * @date Jun 14, 2017
	 */
	public int deleteProcessTaskByInstanceid(String instanceid);

	/**
	 * 根据taskkey获取ProcessTask
	 *
	 * @param instanceid
	 * @param taskkey
	 * @return
	 * @author limin
	 * @date Jun 16, 2017
	 */
	public List<ProcessTask> getProcessTaskListByTaskkey(String instanceid, String taskkey);

	/**
	 * 获取会签节点下一节点
	 *
	 * @param instanceid
	 * @param taskkey
	 * @throws Exception
	 * @author limin
	 * @date Jun 16, 2017
	 */
	public List<Map> getNextTasksBySignTask(String instanceid, String taskkey) throws Exception;

	/**
	 * 查询工作会签用户
	 *
	 * @param instanceid
	 * @param taskid
	 * @param taskkey
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Jun 21, 2017
	 */
	public int isSignUser(String instanceid, String taskid, String taskkey) throws Exception;

	/**
	 * 获取工作list
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Jun 21, 2017
	 */
	public List<Process> getProcessList(Map param) throws Exception;
}

