/**
 * @(#)WorkServiceImpl.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 30, 2013 zhengziyong 创建版本
 */
package com.hd.agent.activiti.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.hd.agent.accesscontrol.model.Authority;
import com.hd.agent.accesscontrol.service.ISysUserService;
import com.hd.agent.activiti.dao.*;
import com.hd.agent.activiti.model.*;
import com.hd.agent.activiti.model.Process;
import com.hd.agent.common.util.CommonUtils;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.*;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.activiti.service.BusinessEndListener;
import com.hd.agent.activiti.service.IDefinitionService;
import com.hd.agent.activiti.service.IFormService;
import com.hd.agent.activiti.service.IWorkService;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.common.dao.AttachFileMapper;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.common.util.SpringContextUtils;

/**
 * 
 * 
 * @author zhengziyong
 */
public class WorkServiceImpl extends BaseServiceImpl implements IWorkService {

	/**
	 * logger
	 */
	private Logger logger = Logger.getLogger(this.getClass());

	private IDefinitionService definitionService;

	private IFormService actFormService;

	private ProcessMapper processMapper;

	private CommentMapper commentMapper;

	private DelegateMapper delegateMapper;

	private DelegateLogMapper delegateLogMapper;

	private WorkLogMapper workLogMapper;
	
	private AttachFileMapper attachFileMapper;
	
	private AttachMapper attachMapper;
	
	private AuthRuleMapper ruleMapper;

    private DataTraceMapper traceMapper;

    private ISysUserService sysUserService;

    private AuthMappingMapper mappingMapper;
	
	private EventHandlerMapper handlerMapper;
	
	public IDefinitionService getDefinitionService() {
		return definitionService;
	}

	public void setDefinitionService(IDefinitionService definitionService) {
		this.definitionService = definitionService;
	}

	public IFormService getActFormService() {
		return actFormService;
	}

	public void setActFormService(IFormService actFormService) {
		this.actFormService = actFormService;
	}

	public ProcessMapper getProcessMapper() {
		return processMapper;
	}

	public void setProcessMapper(ProcessMapper processMapper) {
		this.processMapper = processMapper;
	}

	public CommentMapper getCommentMapper() {
		return commentMapper;
	}

	public void setCommentMapper(CommentMapper commentMapper) {
		this.commentMapper = commentMapper;
	}

	public DelegateMapper getDelegateMapper() {
		return delegateMapper;
	}
	
	public void setDelegateMapper(DelegateMapper delegateMapper) {
		this.delegateMapper = delegateMapper;
	}

	public DelegateLogMapper getDelegateLogMapper() {
		return delegateLogMapper;
	}

	public void setDelegateLogMapper(DelegateLogMapper delegateLogMapper) {
		this.delegateLogMapper = delegateLogMapper;
	}

	public WorkLogMapper getWorkLogMapper() {
		return workLogMapper;
	}

	public void setWorkLogMapper(WorkLogMapper workLogMapper) {
		this.workLogMapper = workLogMapper;
	}

	public AttachFileMapper getAttachFileMapper() {
		return attachFileMapper;
	}

	public void setAttachFileMapper(AttachFileMapper attachFileMapper) {
		this.attachFileMapper = attachFileMapper;
	}

	public AttachMapper getAttachMapper() {
		return attachMapper;
	}

	public void setAttachMapper(AttachMapper attachMapper) {
		this.attachMapper = attachMapper;
	}

	public AuthRuleMapper getRuleMapper() {
		return ruleMapper;
	}

	public void setRuleMapper(AuthRuleMapper ruleMapper) {
		this.ruleMapper = ruleMapper;
	}

    public DataTraceMapper getTraceMapper() {
        return traceMapper;
    }

    public void setTraceMapper(DataTraceMapper traceMapper) {
        this.traceMapper = traceMapper;
    }

    public ISysUserService getSysUserService() {
        return sysUserService;
    }

    public void setSysUserService(ISysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    public AuthMappingMapper getMappingMapper() {
        return mappingMapper;
    }

    public void setMappingMapper(AuthMappingMapper mappingMapper) {
        this.mappingMapper = mappingMapper;
    }

	public EventHandlerMapper getHandlerMapper() {
		return handlerMapper;
	}

	public void setHandlerMapper(EventHandlerMapper handlerMapper) {
		this.handlerMapper = handlerMapper;
	}

    @Override
	public List getCommonWorkList() throws Exception {
		SysUser user = getSysUser();
		List list = getSysUserRoleList();
		String roleid = list.toString().substring(1, list.toString().length() - 1);
		roleid = roleid.replaceAll(" ", "");
		Map map = new HashMap();
		//String dataSql = getDataAccessRule("t_act_auth_rule", "r");
		//map.put("dataSql", dataSql);
		map.put("type", "aud");
		map.put("deptid", user.getDepartmentid());
		map.put("postid", user.getWorkjobid());
		map.put("userid", user.getUserid());
		map.put("roleid", roleid);

        // 获取每个流程的首个taskkey
        List<Definition> definitions = definitionService.getAllDefinitionVersions();
        List<Map> definitionMaps = new ArrayList<Map>();
        for(Definition d : definitions) {

            Map temp = new HashMap();
            temp.put("definitionkey", d.getUnkey());
            temp.put("taskkey", definitionService.getFirstTaskkeyOfDefinition(d.getUnkey()));
            definitionMaps.add(temp);
        }

        if(definitionMaps.size() == 0) {
            definitionMaps = null;
        }
        map.put("definitionMaps", definitionMaps);

		return processMapper.getCommonProcessList(map);
	}

	@Override
	public boolean addNewWork(Process process) throws Exception {
		SysUser user = getSysUser();
		process.setApplyuserid(user.getUserid());
		process.setApplyusername(user.getName());
		if(StringUtils.isNotEmpty(process.getId())){
			return updateNewWork(process);
		}
		else{
			int ret = processMapper.addProcess(process);
			
			if(ret > 0) {
				ret = processMapper.updateProcessTitle(process);
			}
			return ret > 0;
		}
	}
//
//	@Override
//	public boolean addStartNewWork(Process process) throws Exception {
//		SysUser user = getSysUser();
//		process.setApplyuserid(user.getUserid());
//		process.setApplyusername(user.getName());
//		process.setApplydate(new Date());
//		processMapper.addProcess(process);
//		StartProcess(process, null);
//		if(StringUtils.isEmpty(process.getInstanceid())) return false;
//		return processMapper.updateProcess(process)>0;
//	}

	@Override
	public boolean updateNewWork(Process process) throws Exception {
		return processMapper.updateProcess(process)>0;
	}
//
//	@Override
//	public boolean updateStartNewWork(Process process) throws Exception {
//
//		process.setApplydate(new Date());
//		StartProcess(process, null);
//		if(StringUtils.isEmpty(process.getInstanceid())) return false;
//		return processMapper.updateProcess(process)>0;
//	}

	@Override
	public boolean startNewWork(String id, String applyUserId, String... nexttaskkey) throws Exception {
		Process process = new Process();
		Process model = getProcess(id, "1");
		Map<String, Object> properties = new HashMap<String, Object>();
		
		if(StringUtils.isNotEmpty(applyUserId)){
			properties.put("firstAssignee", applyUserId);
		}
		
		process.setDefinitionkey(model.getDefinitionkey());
		process.setId(id);
		process.setApplydate(new Date());
		StartProcess(process, properties, nexttaskkey);
		if(StringUtils.isEmpty(process.getInstanceid())) return false;
		return processMapper.updateProcess(process)>0;
	}

	@Override
	public boolean updateProcessByInstance(Process process) throws Exception {
		return processMapper.updateProcessByInstance(process)>0;
	}
	
	@Override
	public PageData getProcessData(PageMap pageMap) throws Exception {

		List<Process> list = processMapper.getProcessList(pageMap);
		SysUser user = getSysUser();
		
		// 根据SQL判断某个流程是否可收回可能会不完全正确
		if("8".equals(pageMap.getCondition().get("type"))) {
			
			for(Process process : list) {

				if("1".equals(process.getCantakeback())) {
					
					List<Comment> clist = getRealCommentList(process.getId(), process.getInstanceid(), false, true);
					int count = clist.size();
					
					if(count == 0) {
						continue;
					}
					
					Comment comment = clist.get(count - 1);
					
					if(StringUtils.isNotEmpty(comment.getTaskkey())
							&& comment.getTaskkey().equals(process.getTaskkey())
							&& StringUtils.isNotEmpty(comment.getTaskid())
							&& !comment.getTaskid().equals(process.getTaskid())) {
						
						process.setCantakeback("0");
					}
				}
				
			}
		}

		if("6".equals(pageMap.getCondition().get("type"))
				|| "7".equals(pageMap.getCondition().get("type"))) {

			for(Process process : list) {
				if(StringUtils.isNotEmpty(process.getTaskkey())) {
					process.setSign(definitionService.isSignTask(process.getDefinitionid(), process.getTaskkey()));
				}
			}
		}
		
		// 工作监控
        if("0".equals(pageMap.getCondition().get("type"))) {

            for(Process process : list) {

                process.setCandelete("1");// 能否强制删除判断
                String definitionkey = process.getDefinitionkey();
                Definition definition = definitionService.getDefinitionByKey(definitionkey, process.getDefinitionid(), null);

//                String listener = definition.getEndlistener();
//                if(StringUtils.isNotEmpty(listener)) {
//
//                    BusinessEndListener endListener = (BusinessEndListener) SpringContextUtils.getBean(listener);
//
//                    if(!endListener.check(process)) {
//
//                        process.setCandelete("0");
//                    }
//                }
				if (definition != null) {
					String businessurl = definition.getBusinessurl();
					String baseurl = "";
					if(StringUtils.isNotEmpty(businessurl)) {
						baseurl = businessurl;
						if(businessurl.indexOf('?') >= 0) {
							baseurl = businessurl.substring(0, businessurl.indexOf('?'));
						}
					}

					if(StringUtils.isNotEmpty(baseurl)) {
						List<Map> handlerList = handlerMapper.selectHanderItemsByUrl(baseurl, "5");
						for(Map handlerMap : handlerList) {
							String handlerStr = (String) handlerMap.get("handler");
							if(StringUtils.isNotEmpty(handlerStr)) {
								BusinessEndListener listener = (BusinessEndListener)SpringContextUtils.getBean(handlerStr);
								if(listener != null){
									if(!listener.check(process)) {
										process.setCandelete("0");
										break;
									}
								}
							}
						}
					}
				}
            }
        }

		// 首页待办
		if("10".equals(pageMap.getCondition().get("type"))) {

			for(Process process : list) {

				// 判断上一步是否同意
				List<Comment> comments = getCommentListByProcessid(process.getId());
				String agree = "1";
				if(StringUtils.isNotEmpty(process.getTaskid()) && process.getTaskid().equals(comments.get(comments.size() - 1).getTaskid())) {

					Comment pre = comments.get(comments.size() - 2);
					if(StringUtils.isNotEmpty(pre.getTaskid())) {

						agree = pre.getAgree();
					}

				} else if(StringUtils.isNotEmpty(process.getTaskid()) && !process.getTaskid().equals(comments.get(comments.size() - 1).getTaskid())) {

					Comment pre = comments.get(comments.size() - 1);
					if(StringUtils.isNotEmpty(pre.getTaskid())) {

						agree = pre.getAgree();
					}
				}

				if(StringUtils.isNotEmpty(process.getTaskkey())) {
					process.setSign(definitionService.isSignTask(process.getDefinitionid(), process.getTaskkey()));
				}

				process.setPreagree(agree);

				List<Task> taskList = taskService.createTaskQuery().processInstanceId(process.getInstanceid()).taskAssignee(user.getUserid()).list();
				if(taskList.size() > 0) {
//					process = (Process) CommonUtils.deepCopy(process);
					process.setTaskid(taskList.get(0).getId());

				}
			}
		}
		
		return new PageData(processMapper.getProcessCount(pageMap), list, pageMap);
	}

	@Override
	public Process getProcess(String id, String type) throws Exception {
		if(StringUtils.isEmpty(type)){
			type = "1";
		}
		if("1".equals(type)){

			if(StringUtils.isNotEmpty(id) && id.startsWith("0")) {
				return null;

			}

			Process process = processMapper.getProcess(id);
			return process;
		}
		else if("2".equals(type)){
			Process process = processMapper.getProcessByInstance(id);
			return process;
		}
		else if("3".equals(type)){
			Process process = processMapper.getProcessByBusinessId(id);
			return process;
		}
		return null;
	}

	@Override
	public boolean deleteWork(String id, String type) throws Exception {
		if(StringUtils.isEmpty(type)){
			type = "1";
		}
		if("1".equals(type)){
			Process process = processMapper.getProcess(id);
			
			if(process != null && process.getApplydate() != null){
				return false;
			}
			commentMapper.deleteCommentByProcessid(id);
			boolean flag =  processMapper.deleteProcess(id)>0;

			Definition definition = definitionService.getDefinitionByKey(process.getDefinitionkey(), null, "1");
//			if(endListener != null && !"".equals(endListener)) {
//				BusinessEndListener listener = (BusinessEndListener) SpringContextUtils.getBean(endListener);
//
//				listener.delete(process);
//			}
			String businessurl = definition.getBusinessurl();
			String baseurl = "";
			if(StringUtils.isNotEmpty(businessurl)) {
				baseurl = businessurl;
				if(businessurl.indexOf('?') >= 0) {
					baseurl = businessurl.substring(0, businessurl.indexOf('?'));
				}
			}

			if(StringUtils.isNotEmpty(baseurl)) {
				List<Map> handlerList = handlerMapper.selectHanderItemsByUrl(baseurl, "5");
				for(Map handlerMap : handlerList) {
					String handlerStr = (String) handlerMap.get("handler");
					if(StringUtils.isNotEmpty(handlerStr)) {
						BusinessEndListener listener = (BusinessEndListener)SpringContextUtils.getBean(handlerStr);
						if(listener != null){
							listener.delete(process);
							logger.info(">>>>>>>> Target:" + handlerMap.get("clazz") + ".delete(): " + handlerMap.get("handlerdescription"));
						}
					}
				}
			}
			
			return flag;
		}
		else if("2".equals(type)){
			
		}
		return false;
	}
//
//	@Override
//	public boolean updateBackPrevWork(String id) throws Exception {
//		Process process = getProcess(id, "1");
//		SysUser user = getSysUser();
//		if(process == null || StringUtils.isEmpty(process.getTaskid())){
//			return false;
//		}
//		taskService.backTask(process.getTaskid());
//		WorkLog log = new WorkLog();
//		log.setInstanceid(process.getInstanceid());
//		log.setType("1");
//		log.setContent(user.getName()+ " 驳回了流程");
//		log.setTaskname("驳回流程");
//		return addLog(log);
//	}
//
//	@Override
//	public boolean updateTakeBackWork(String id) throws Exception {
//		SysUser user = getSysUser();
//		Process process = getProcess(id, "1");
//		if(process == null || StringUtils.isEmpty(process.getTaskid())){
//			return false;
//		}
//		Comment comment = getCommentMapper().getCommentByUser(process.getInstanceid(), user.getUserid());
//		if(comment == null){
//			return false;
//		}
//		if(process.getTaskkey().equals(comment.getTaskkey())){
//			return false;
//		}
//		taskService.complete(process.getTaskid(), null, comment.getTaskkey());
//		// 收回时，同时删除上一次用户审批情报Comment
//		int ret = getCommentMapper().deleteComment(comment.getId());
//
//		WorkLog log = new WorkLog();
//		log.setInstanceid(process.getInstanceid());
//		log.setType("1");
//		log.setContent(user.getName()+ " 收回了流程");
//		log.setTaskname("收回流程");
//		addLog(log);
//		return ret > 0;
//	}

	@Override
	public boolean updateUndoWork(String id) throws Exception {
		Process process = getProcess(id, "1");
		if("1".equals(process.getIsend())){
			return false;
		}
		SysUser user = getSysUser();
		processMapper.UndoProcessFromActiviti(process.getTaskid(), process.getInstanceid());
		WorkLog log = new WorkLog();
		log.setInstanceid(process.getInstanceid());
		log.setType("1");
		log.setContent(user.getName()+ " 撤销了流程");
		log.setTaskname("撤销流程");
		addLog(log);
		return processMapper.UndoProcess(id, process.getInstanceid())>0;
	}
	
	@Override
	public boolean updateUndoBusinessWork(String businessId, String listener) throws Exception {
		Process process = processMapper.getProcessByBusinessId(businessId);
		// 流程实例被删除、流程实例已结束、流程实例发起人非当前登录用户时撤销失败
		if (process == null
				|| "1".equals(process.getIsend())
				|| !getSysUser().getUserid().equals(process.getApplyuserid())) {
			return false;
		}
		/*
		BusinessUndoListener listener2 = (BusinessUndoListener)SpringContextUtils.getBean(listener);
		if(listener2 == null){
			return false;
		}
		*/
		SysUser user = getSysUser();
		// runtimeService.deleteProcessInstance(process.getInstanceid(), "");
		processMapper.UndoProcessFromActiviti(process.getTaskid(), process.getInstanceid());
		WorkLog log = new WorkLog();
		log.setInstanceid(process.getInstanceid());
		log.setType("1");
		log.setContent(user.getName()+ " 撤销了流程");
		log.setTaskname("撤销流程");
		addLog(log);
		//listener2.undo(process);
		boolean ret1 = processMapper.UndoProcess2(/*process.getId(), */process.getInstanceid())>0;
		boolean ret2 = processMapper.cancelProcess(process.getId()) > 0;
		
		return ret2;
	}

	@Override
	public boolean addComment(Comment comment) throws Exception {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SysUser user = getSysUser();
		
		Task task = taskService.createTaskQuery().taskId(comment.getTaskid()).singleResult();
		if(task == null){
			comment.setDevice(user.getLoginType());
			return commentMapper.addComment(comment)>0;
		}

		Process process = getProcess(task.getProcessInstanceId(), "2");
		comment.setProcessid(process.getId());

		comment.setTaskkey(task.getTaskDefinitionKey());
		comment.setTaskname(task.getName());
		comment.setBegintime(dateFormat.format(new Date()));
		comment.setInstanceid(task.getProcessInstanceId());
		comment.setAssignee(user.getUserid());
		comment.setDevice(user.getLoginType());
		addSignWork(task); //如果工作未签收，则先签收工作。
		return commentMapper.addComment(comment)>0;
	}

	@Override
	public boolean updateComment(Comment comment,
								 String type,
								 String nextAssignee,
								 String taskkey,
								 String signnexttask,
								 String signNextAssignee) throws Exception {

		Comment model = commentMapper.getComment(comment.getId());
		Process process = getProcess(model.getProcessid(), "1");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SysUser user = getSysUser();

		List<Task> taskEntityList = taskService.createTaskQuery().processInstanceId(process.getInstanceid()).taskAssignee(user.getUserid()).list();
		if(process != null
				&& !user.getUserid().equals(process.getAssignee())
				&& taskEntityList.size() == 0) {

			return false;
		}

		Task currentTask = taskEntityList.get(0);
		if(!currentTask.getId().equals(model.getTaskid())) {
			return false;
		}

		Map<String, Object> variables = new HashMap<String, Object>();

        // 如果转交的taskkey 与当前工作的taskkey 相同，表明是变更处理人
        if(process != null && process.getTaskkey().equals(taskkey)) {

            // 设置变量run_listener，该变量为0时，表明不需要执行节点listener，否则需要执行listienr
            variables.put("run_listener", "0");
        }

        // 节点处理人
        variables.put(taskkey + "_assignee", nextAssignee);
        if(StringUtils.isNotEmpty(signnexttask)) {
			variables.put(signnexttask + "_assignee", signNextAssignee);
		}

		if(StringUtils.isEmpty(taskkey)) {
			variables.put("gotoend", true);
		}

		variables.put(model.getTaskkey() + "_audit", true);
		variables.put(model.getTaskkey(), true);
		variables.put("destination", taskkey);
		variables.put("assigneeList", Arrays.asList(nextAssignee.split(",")));

		processMapper.updateProcessTaskEndtime(process.getInstanceid(), currentTask.getId());

		comment.setEndtime(dateFormat.format(new Date()));
		comment.setAssignee(user.getUserid());
		if(!updateCompleteTask(comment.getTaskid(), variables, taskkey)) {
			return false;
		}
		comment.setDevice(user.getLoginType());
		return commentMapper.updateComment(comment)>0;
	}

	@Override
	public boolean updateSignComment(Comment comment) throws Exception {

		Comment model = commentMapper.getComment(comment.getId());
		Process process = getProcess(model.getProcessid(), "1");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SysUser user = getSysUser();

		List<Task> taskEntityList = taskService.createTaskQuery().processInstanceId(process.getInstanceid()).taskAssignee(user.getUserid()).list();
		if(process != null
				&& !user.getUserid().equals(process.getAssignee())
				&& taskEntityList.size() == 0) {

			return false;
		}

		Task currentTask = taskEntityList.get(0);
		if(!currentTask.getId().equals(model.getTaskid())) {
			return false;
		}

		comment.setEndtime(dateFormat.format(new Date()));
		comment.setAssignee(user.getUserid());
		comment.setDevice(user.getLoginType());

		if(commentMapper.updateComment(comment) > 0) {
			if(!updateCompleteTask(comment.getTaskid(), new HashMap<String, Object>())) {
				throw new Exception("工作流异常！");
			}
			return true;
		}
		return false;
	}

	@Override
	public Comment getCommentByTask(String id) throws Exception {
		return commentMapper.getCommentByTask(id);
	}
	
	@Override
	public List getCommentList(String id, String type) throws Exception {
		
		// 查看流程
		// if("2".equals(type)){
		if("3".equals(type)){

			// processid
			Process process = getProcess(id, "1");
			id = process.getInstanceid();
			
			List list1 = commentMapper.getCommentListByInstance(id);
			
			String processid = process.getId();
			List list2 = commentMapper.getCommentListByProcessid(processid);
			
			list1.addAll(list2);
			Map map = new HashMap();
			for(int i = 0; i < list1.size(); i++) {
				
				Comment comment = (Comment) list1.get(i);
				map.put(comment.getId(), comment);
			}
			
			List<Comment> commentList = new ArrayList<Comment>();
			Collection<Comment> c = map.values();
			
			Iterator<Comment> i = c.iterator();
			while(i.hasNext()) {
				
				Comment comment = i.next();
				commentList.add(comment);
			}
			sortCommentList(commentList);
			return commentList;
			
		}
		
		// 查看流程图
		return commentMapper.getCommentListByInstance(id);
	}
	
	@Override
	public List getCommentImgInfo(String instanceid) throws Exception {
		List<Map<String, Object>> infoList = new ArrayList<Map<String,Object>>();
		// List<Comment> commentList = getCommentList(instanceid, "1");
		Process process = getProcess(instanceid, "2");
//		List<Comment> commentList = getRealCommentList(null, instanceid, true, false);
		List<Comment> commentList = new ArrayList<Comment>();
		{
			String processid = process.getId();
			boolean agree = true;
			boolean end = false;

			if(StringUtils.isEmpty(processid)) {

				if(process != null && StringUtils.isNotEmpty(process.getId())) {

					processid = process.getId();
				}
			}

			if(StringUtils.isEmpty(instanceid)) {

				if(process != null && StringUtils.isNotEmpty(process.getInstanceid())) {

					instanceid = process.getInstanceid();
				}
			}

			List<Comment> list = getCommentListByProcessid(processid);
			List<Comment> list2 = getCommentListByInstanceid(instanceid);

			if(list2.size() == list.size()) {

				if(!process.getTaskkey().equals(list.get(list.size() - 1).getTaskkey())) {

					Comment comment = new Comment();
					comment.setTaskkey(process.getTaskkey());
					comment.setTaskname(process.getTaskname());
					comment.setTaskid(process.getTaskid());
					comment.setAssignee(process.getAssignee());
					list.add(comment);
				}

				for(int i = 0; i < list.size(); i++) {

					Comment c1 = list.get(i);
					List<Comment> list3 = new ArrayList<Comment>(list);

					for(int j = list3.size() - 1; j > i; j--) {

						Comment c2 = list3.get(j);

						if(c1.getTaskkey().equals(c2.getTaskkey()) && !definitionService.isSignTask(process.getDefinitionid(), c1.getTaskkey())) {

							for(int k = i; k < j; k++) {

								list.remove(i);
							}
							i--;
							break;

						}
					}
				}

				if(agree) {

					for(int i = list.size() - 1; i >= 0; i--) {

						Comment comment = list.get(i);

						if(!"1".equals(comment.getAgree())) {

							list.remove(i);
						}
					}
				}

				if(end) {

					for(int i = list.size() - 1; i >= 0; i--) {

						Comment comment = list.get(i);

						if(StringUtils.isEmpty(comment.getEndtime())) {

							list.remove(i);
						}
					}
				}

				commentList = list;
			}
		}

        if(!commentList.get(commentList.size()- 1).getTaskid().equals(process.getTaskid()) && commentList.get(commentList.size()- 1).getTaskkey().equals(process.getTaskkey())) {

            commentList.remove(commentList.size() - 1);
        }
		
		if(!commentList.get(commentList.size()- 1).getTaskid().equals(process.getTaskid())) {
			
			Comment comment = new Comment();
			comment.setTaskid(process.getTaskid());
			comment.setTaskkey(process.getTaskkey());
			comment.setTaskname(process.getTaskname());
			comment.setAssignee(process.getAssignee());
			commentList.add(comment);
		}
		
		List<Viewer> viewerList = commentMapper.getViewerByInstance(instanceid);
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceid).singleResult();

		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(instanceid).singleResult();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(historicProcessInstance.getProcessDefinitionId()).singleResult();
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(processDefinition.getId());
		List<ActivityImpl> activityList = processDefinitionEntity.getActivities();
		for(ActivityImpl activityImpl : activityList){
			Map<String, Object> info = new HashMap<String, Object>();
			info.put("current", false);
			info.put("x", activityImpl.getX());
			info.put("y", activityImpl.getY());
			info.put("width", activityImpl.getWidth());
			info.put("height", activityImpl.getHeight());
			info.put("taskkey", activityImpl.getId());
			info.put("taskName", activityImpl.getProperty("name"));
			
			if(activityImpl.getActivityBehavior() instanceof NoneStartEventActivityBehavior) {
				info.put("shape", "NoneStartEventActivityBehavior");
			} else if(activityImpl.getActivityBehavior() instanceof NoneEndEventActivityBehavior) { 
				info.put("shape", "NoneEndEventActivityBehavior");
			} else if(activityImpl.getActivityBehavior() instanceof UserTaskActivityBehavior) {
				info.put("shape", "UserTaskActivityBehavior");
			} else if(activityImpl.getActivityBehavior() instanceof ExclusiveGatewayActivityBehavior) {
				info.put("shape", "ExclusiveGatewayActivityBehavior");
			} else if(activityImpl.getActivityBehavior() instanceof ParallelMultiInstanceBehavior) {
				info.put("shape", "ParallelMultiInstanceBehavior");
			}
			
			List<Map<String, Object>> detailList = new ArrayList<Map<String,Object>>();
			for(Comment comment: commentList){

				// 开始节点
				if("process_begin".equals(comment.getTaskkey()) && activityImpl.getActivityBehavior() instanceof NoneStartEventActivityBehavior){ //开始信息

					SysUser aUser = getSysUserById(comment.getAssignee());
					if(aUser != null){
						info.put("handlename", aUser.getName());
						info.put("state", "流程开始");
					}
					info.put("starttime", comment.getBegintime());
					info.put("event", "start");

				// 结束节点
				} else if("process_end".equals(comment.getTaskkey()) && activityImpl.getActivityBehavior() instanceof NoneEndEventActivityBehavior){

					info.put("state", "流程结束");
					info.put("endtime", comment.getEndtime());
					info.put("event", "end");

				// 用户任务
				} else if(comment.getTaskkey().equals(activityImpl.getId()) && activityImpl.getActivityBehavior() instanceof UserTaskActivityBehavior){

					Map<String, Object> map = new HashMap<String, Object>();
					for(Viewer viewer: viewerList){
						if(viewer.getTaskid().equals(comment.getTaskid())){
							map.put("viewer", viewer.getViewername());
							break;
						}
					}
					SysUser aUser = getSysUserById(comment.getAssignee());
					if(aUser != null){
						map.put("handlename", aUser.getName());
						map.put("starttime", comment.getBegintime());
						if(StringUtils.isNotEmpty(comment.getEndtime())){
							map.put("state", "已办理");
							map.put("agree", ("1".equals(comment.getAgree())? "同意" : "不同意"));
							map.put("endtime", comment.getEndtime());
						} else if(StringUtils.isEmpty(comment.getBegintime())) {
							map.put("state", "未接收");
							map.remove("starttime");
						} else{
							map.put("state", "办理中");
						}
						
					}
					detailList.add(map);

				// 会签节点
				} else if(comment.getTaskkey().equals(activityImpl.getId()) && activityImpl.getActivityBehavior() instanceof ParallelMultiInstanceBehavior){

					Map<String, Object> map = new HashMap<String, Object>();
					for(Viewer viewer: viewerList){
						if(viewer.getTaskid().equals(comment.getTaskid())){
							map.put("viewer", viewer.getViewername());
							break;
						}
					}
					SysUser aUser = getSysUserById(comment.getAssignee());
					if(aUser != null){
						map.put("handlename", aUser.getName());
						map.put("starttime", comment.getBegintime());
						if(StringUtils.isNotEmpty(comment.getEndtime())){
							map.put("state", "已办理");
							map.put("agree", "1".equals(comment.getAgree())? "同意" : "不同意");
							map.put("endtime", comment.getEndtime());
						} else if(StringUtils.isEmpty(comment.getBegintime())) {
							map.put("state", "未接收");
							map.remove("starttime");
						} else{
							map.put("state", "办理中");
						}

					}
					detailList.add(map);
				}
			}
			if(activityImpl.getId().equals(process.getTaskkey())) {
				info.put("current", true);
			}
			if(detailList.size() > 0){
				info.put("task", detailList);
			}
			infoList.add(info);
		}
		return infoList;
	}
	

	@Override
	public Delegate getDelegate(String id) throws Exception {
		return delegateMapper.getDelegate(id);
	}
	
	@Override
	public boolean addDelegate(Delegate delegate) throws Exception {
		return delegateMapper.addDelegate(delegate)>0;
	}

    @Override
    public int deleteDelegateByOaid(String oaid) throws Exception {
        return delegateMapper.deleteDelegateByOaid(oaid);
    }

    @Override
    public Delegate selectDelegateByOaid(String oaid) throws Exception {
        return delegateMapper.selectDelegateByOaid(oaid);
    }

    @Override
	public boolean updateDelegate(Delegate delegate) throws Exception {
		return delegateMapper.updateDelegate(delegate)>0;
	}

	@Override
	public boolean deleteDelegate(String id) throws Exception {
		return delegateMapper.deleteDelegate(id)>0;
	}

	@Override
	public PageData getDelegateData(PageMap pageMap) throws Exception {
		return new PageData(delegateMapper.getDelegateCount(pageMap), delegateMapper.getDelegateList(pageMap), pageMap);
	}
	
	@Override
	public List getDelegateList(String userId, String definitionkey) throws Exception {
		return delegateMapper.getDelegateListByUserAndKey(userId, definitionkey);
	}
	
	@Override
	public boolean addDelegateLog(DelegateLog log) throws Exception {
		return delegateLogMapper.addDelegateLog(log)>0;
	}

	@Override
	public PageData getDelegateLogData(PageMap pageMap) throws Exception {
		return new PageData(delegateLogMapper.getDelegateLogCount(pageMap), delegateLogMapper.getDelegateLogList(pageMap), pageMap);
	}

	@Override
	public PageData getWorkQueryData(PageMap pageMap) throws Exception {
		
		// 数据权限控制
		String dataSql = getDataAccessRule("t_act_process", "a");
		pageMap.setDataSql(dataSql);

		List<Process> processList = processMapper.getWorkList(pageMap);
		for(Process process: processList) {
			String assignee = process.getAssignee();
			SysUser user = getSysUserById(assignee);
			if (user != null) {
				process.setAssigneename(user.getName());
			}
		}
		
		return new PageData(processMapper.getWorkCount(pageMap), processList, pageMap);
	}

	@Override
	public boolean addLog(WorkLog log) throws Exception {
		SysUser user = getSysUser();
		Process process = processMapper.getProcessByInstance(log.getInstanceid());
		if(process == null){
			process = processMapper.getProcess(log.getProcessid());
		}
		if(process != null){
			log.setTitle(process.getTitle());
			log.setApplyuserid(process.getApplyuserid());
			log.setApplyusername(process.getApplyusername());
			log.setDefinitionkey(process.getDefinitionkey());
			log.setProcessid(process.getId());
		}
		log.setAssigneeid(user.getUserid());
		log.setAssigneename(user.getName());
		if(StringUtils.isEmpty(log.getIp())){
			log.setIp(ServletActionContext.getRequest().getRemoteAddr());
		}
		if(StringUtils.isEmpty(log.getContent())){
			if("1".equals(log.getType())){
				log.setContent("转交下一步，办理人：" + log.getAssigneename() + "[ " + log.getTaskkey() + " -> " + CommonUtils.nullToEmpty(log.getNexttaskkey()) + " ]");
			}
			if("2".equals(log.getType())){
				log.setContent("[" + log.getAssigneename() + "]进行了修改");
			}
			if("3".equals(log.getType())){
				log.setContent("流程结束");
			}
		}
		log.setDevice(user.getLoginType());
		return workLogMapper.addLog(log)>0;
	}

	@Override
	public PageData getLogData(PageMap pageMap) throws Exception {
		return new PageData(workLogMapper.getLogCount(pageMap), workLogMapper.getLogList(pageMap), pageMap);
	}
//
//	@Override
//	public boolean startBusiness(BusinessListener businessListener, Process process, Map<String, Object> variables) throws Exception{
//		SysUser user = getSysUser();
//		process.setApplydate(new Date());
//		process.setApplyuserid(user.getUserid());
//		process.setApplyusername(user.getName());
//		processMapper.addProcess(process);
//		StartProcess(process, variables);
//		if(StringUtils.isEmpty(process.getInstanceid())) return false;
//		if(businessListener != null){
//			businessListener.before(process);
//		}
//		return processMapper.updateProcess(process)>0;
//	}
	
	@Override
	public boolean addViewer(String taskid) throws Exception {
		Viewer viewer = commentMapper.getViewerByTask(taskid);
		SysUser user = getSysUser();
		if(viewer == null){
			viewer = new Viewer();
			Task task = taskService.createTaskQuery().taskId(taskid).singleResult();
			viewer.setInstanceid(task.getProcessInstanceId());
			viewer.setTaskid(taskid);
			viewer.setTaskkey(task.getTaskDefinitionKey());
			viewer.setTaskname(task.getName());
			viewer.setViewerid(user.getUserid());
			viewer.setViewername(user.getName());
			return commentMapper.addViewer(viewer)>0;
		}
		else{
			if(!viewer.getViewerid().contains(user.getUserid())){
				viewer.setViewerid(viewer.getViewerid()+ ";"+ user.getUserid());
				viewer.setViewername(viewer.getViewername()+ ";"+ user.getName());
				return commentMapper.updateViewer(viewer)>0;
			}
		}
		return true;
	}

	/**
	 * 启动流程
	 * @param process
	 * @param properties
	 * @throws Exception
	 */
	protected void StartProcess(Process process, Map<String, Object> properties, String... nexttaskkey) throws Exception{
		SysUser user = getSysUser();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		identityService.setAuthenticatedUserId(user.getUserid());
		Definition definition = definitionService.getDefinitionByKey(process.getDefinitionkey(), null, "1");
		if(definition != null){
			ExecutionEntity execution = (ExecutionEntity) runtimeService.startProcessInstanceById(definition.getDefinitionid(), "process:"+ process.getId(), properties);
			
			List<TaskEntity> tasklist = execution.getTasks();
			
			if(tasklist.size() > 0 && nexttaskkey != null && nexttaskkey.length > 0 && StringUtils.isNotEmpty(nexttaskkey[0])) {
				taskService.complete(tasklist.get(0).getId(), properties, nexttaskkey[0]);
			}
			
			process.setInstanceid(execution.getProcessInstanceId());
			Comment comment = new Comment();
			comment.setProcessid(process.getId());
			comment.setBegintime(dateFormat.format(new Date()));
			comment.setAssignee(user.getUserid());
			comment.setTaskid("");
			comment.setTaskname("申请");
			comment.setTaskkey("process_begin");
			comment.setInstanceid(execution.getProcessInstanceId());
			comment.setProcessid(process.getId());
			comment.setDevice(user.getLoginType());
			commentMapper.addComment(comment);//添加流程的开始信息
			WorkLog log = new WorkLog();
			log.setInstanceid(execution.getProcessInstanceId());
			log.setProcessid(process.getId());
			log.setTaskname("申请");
			log.setType("1");
			log.setContent("申请-启动流程");
			addLog(log);
		}
	}

	/**
	 * 候选人签收工作
	 * @param taskid
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 6, 2013
	 */
	protected boolean addSignWork(String taskid) throws Exception {
		SysUser user = getSysUser();
		Task task = taskService.createTaskQuery().taskId(taskid).singleResult();
		if(StringUtils.isEmpty(task.getAssignee())){
			taskService.claim(taskid, user.getUserid());
			Process process = new Process();
			process.setInstanceid(task.getProcessInstanceId());
			process.setAssignee(user.getUserid());
			process.setCondidate("");
			return processMapper.updateProcessByInstance(process)>0;
		}
		return true;
	}
	
	/**
	 * 候选人签收工作
	 * @param task
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 6, 2013
	 */
	protected boolean addSignWork(Task task) throws Exception {
		SysUser user = getSysUser();
		if(StringUtils.isEmpty(task.getAssignee())){
			taskService.claim(task.getId(), user.getUserid());
//			Process process = new Process();
//			process.setInstanceid(task.getProcessInstanceId());
//			process.setAssignee(user.getUserid());
//			process.setCondidate("");
//			return processMapper.updateProcessByInstance(process)>0;
			return true;
		}
		return true;
	}

	/**
	 * 完成工作
	 * @param taskid
	 * @param variables
	 * @param taskkey
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 6, 2013
	 */
	protected boolean updateCompleteTask(String taskid, Map<String, Object> variables, String... taskkey) throws Exception{
		Task task = taskService.createTaskQuery().taskId(taskid).singleResult();
		if(taskkey.length == 0 || StringUtils.isEmpty(taskkey[0])) {
			taskService.complete(taskid, variables);
		} else {
			taskService.complete(taskid, variables, taskkey[0]);
		}
		WorkLog log = new WorkLog();
		log.setInstanceid(task.getProcessInstanceId());
		log.setType("1");
		log.setTaskname(task.getName());
        log.setTaskkey(task.getTaskDefinitionKey());
        log.setNexttaskkey((taskkey == null || taskkey.length == 0) ? "" : taskkey[0]);
		addLog(log);
		return true;
	}
//
//	@Override
//	public String getPreTaskKey(String id) throws Exception {
//
//		Process process = getProcess(id, "1");
//		ProcessMapper processMapper = (ProcessMapper)SpringContextUtils.getBean("processMapper");
//		return processMapper.getPrevTaskKey(process.getTaskid());
//	}

	@Override
	public boolean updateNewWorkAndTitle(Process process) throws Exception {

		int ret = processMapper.updateProcess(process);
		
		if(ret > 0) {
			ret = processMapper.updateProcessTitle(process);

            SysUser user = getSysUser();
            DataTrace trace = new DataTrace();
            trace.setProcessid(Integer.parseInt(process.getId()));
            trace.setTrace(process.getJson());
            trace.setAdduserid(user.getUserid());
            trace.setAddusername(user.getName());
            trace.setAdddeptid(user.getDepartmentid());
            trace.setAdddeptname(user.getDepartmentname());
			trace.setDefinitionid(process.getDefinitionid());
			trace.setTaskid(process.getTaskid());
			trace.setTaskkey(process.getTaskkey());
			trace.setTaskname(process.getTaskname());

            ret = traceMapper.insertDataTrace(trace);
		}
		return ret > 0;
	}

	@Override
	public Map getNextDefinitionTaskInfo(String definitionkey, String taskkey) throws Exception {

		Map map = new HashMap();
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(definitionkey);// addOaCustomer:77:20647
		
		//获得当前任务的所有节点
		List<ActivityImpl> activitiList = def.getActivities();
		
		// 获取开始节点节点ID
		List<UserTaskActivityBehavior> list = new ArrayList<UserTaskActivityBehavior>();
		ActivityImpl startActivity = null;
		for(ActivityImpl activity : activitiList) {

			if(activity.getId().equals(taskkey)) {
				startActivity = activity;
			}
		}
		List<ActivityImpl> taskList = getActivitiNextUserNode(startActivity);
		List<Map> taskMapList = new ArrayList<Map>();
		for(ActivityImpl activity : taskList) {
			Map activityMap = new HashMap();
			activityMap.put("taskkey", activity.getId());
			activityMap.put("taskname", activity.getProperty("name"));
			activityMap.put("type", activity.getProperty("type"));
			if(activity.getActivityBehavior() instanceof ParallelMultiInstanceBehavior) {
				activityMap.put("sign", true);
			}
			taskMapList.add(activityMap);
		}

		map.put("tasklist", taskMapList);
		
		return map;
	}

	private List<ActivityImpl> getActivitiNextUserNode(ActivityImpl srcActivity) throws Exception {
		List<ActivityImpl> taskList = new ArrayList<ActivityImpl>();
		for(PvmTransition transition : srcActivity.getOutgoingTransitions()) {

			ActivityImpl destination = (ActivityImpl) transition.getDestination();
			if(destination.getActivityBehavior() instanceof UserTaskActivityBehavior
					|| destination.getActivityBehavior() instanceof ParallelMultiInstanceBehavior
					|| destination.getActivityBehavior() instanceof NoneEndEventActivityBehavior) {
				taskList.add(destination);
			} else if(destination.getActivityBehavior() instanceof ExclusiveGatewayActivityBehavior) {
				taskList.addAll(getActivitiNextUserNode(destination));
			}
		}
		return taskList;
	}
//
//	@Override
//	public int clearProcessInstanceInfo(Process process) throws Exception {
//
//		int ret = processMapper.clearProcessInstanceInfo(process);
//		return ret;
//	}

	/**
	 * 
	 * @param activity
	 * @return
	 * @author limin 
	 * @date 2014-10-5
	 */
	private Map<String, Object> nextNode(ActivityImpl activity) {
		
		Map map = new HashMap();
		map.put("id", activity.getId());
		map.put("object", activity);
		map.put("next", null);
		
		List<PvmTransition> transitions = activity.getOutgoingTransitions();
		List list = new ArrayList();
		for(PvmTransition transition : transitions) {
			
			PvmActivity pa = transition.getDestination();
			ActivityImpl ai = (ActivityImpl) pa;
			list.add(ai);
		}
		
		map.put("next", list);
		map.put("size", String.valueOf(list.size()));
		return map;
	}

	@Override
	public boolean backWork(String id, String taskkey, Comment comment) throws Exception {
		
		Process process = getProcess(id, "1");
		
		if(process == null) {
			
			return false;
		}

		SysUser user = getSysUser();
		if(user == null) {
			user = new SysUser();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		comment.setEndtime(sdf.format(new Date()));
		comment.setAssignee(user.getUserid());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put(process.getTaskkey() + "_back", taskkey);
		map.put(process.getTaskkey() + "_audit", false);
		map.put(process.getTaskkey(), false);
		boolean ret1 = updateCompleteTask(comment.getTaskid(), map, taskkey);
		
		if(ret1) {

			String executionId = runtimeService.createExecutionQuery().processInstanceId(process.getInstanceid()).singleResult().getId();
			
			String assignee = (String) runtimeService.getVariable(executionId, taskkey + "_assignee");
			
			if(StringUtils.isEmpty(assignee)) {
				assignee = (String) runtimeService.getVariable(executionId, "firstAssignee");
			}
			
			SysUser toUser = getSysUserById(assignee);
			if(toUser == null) {
				toUser = new SysUser();
			}
			
			WorkLog log = new WorkLog();
			log.setInstanceid(process.getInstanceid());
			log.setType("1");
			log.setContent(user.getName()+ " 驳回了流程至 " + toUser.getName());
			log.setTitle(process.getTitle());
			log.setProcessid(process.getId());
			log.setApplyuserid(process.getApplyuserid());
			log.setApplyusername(process.getApplyusername());
			log.setTaskname("驳回流程");
			addLog(log);
		}

		comment.setDevice(user.getLoginType());
		int ret2 = commentMapper.updateComment(comment);
		return ret1 && (ret2 > 0);
	}
	
	/**
	 * 根据某一部门编号，获取该部门上层到最顶层的所有部门编号
	 * @param id
	 * @return
	 * @author limin 
	 * @throws Exception 
	 * @date 2014-8-26
	 */
	public Map<String, String> getTopDepts(String id) throws Exception {
		
		DepartMent d = getDepartmentByDeptid(id);
		Map<String, String> map = new HashMap<String, String>();
		map.put(id, id);
		
		if(d == null) {
			
			return map;
		}

		if(!StringUtils.isEmpty(d.getPid())) {
			
			map.putAll(getTopDepts(d.getPid()));
		} else {
			
			map.put(d.getThisid(), d.getThisid());
		}
		
		return map;
	}
	
	/**
	 * 
	 * @param list
	 * @author limin 
	 * @date 2014-9-19
	 */
	private static void sortCommentList(List<Comment> list) {
		
		Collections.sort(list, new Comparator<Comment>() {
			public int compare(Comment c1, Comment c2) {
				
				int id1 = Integer.parseInt(c1.getId());
				int id2 = Integer.parseInt(c2.getId());
				
				if(id1 > id2) {
					return 1;
				} else if(id1 == id2) {
					return 0;
				}
				return -1;
			}
		});

	}

	@Override
	public Map getNextTaskDefinition(String definitionid, String processId, String taskkey, String applyUserId) throws Exception {

        Process process = getProcess(processId, "1");
		String definitionkey = definitionid.substring(0, definitionid.indexOf(":"));

		ProcessDefinitionEntity entity = (ProcessDefinitionEntity)((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(definitionid);
		// 是否会签
		boolean sign = false;
		for(ActivityImpl activity : entity.getActivities()) {
			if(activity.getId().equals(taskkey)) {
				sign = activity.getActivityBehavior() instanceof ParallelMultiInstanceBehavior;
				break;
			}
		}

        //**********************************************************************
        //↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        //**********************************************************************
        SysUser applyUser = getSysUserById(applyUserId);
        AuthMapping mapping = null;
        if(!sign){
            Map param = new HashMap();
			param.put("definitionkey", definitionkey);
			param.put("definitionid", definitionid);
            param.put("taskkey", taskkey);
            param.put("userid", applyUserId);
            param.put("deptid", applyUser.getDepartmentid());

            List<Authority> authorities = sysUserService.getAuthoritiesListByUserid(applyUserId);

            param.put("roleid", authorities.size() == 0 ? null : authorities);
            param.put("postid", applyUser.getWorkjobid());
            mapping = mappingMapper.selectAuthMapping(param);
        }
        //**********************************************************************
        //↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
        //**********************************************************************

        List<String> destUserIdList = new ArrayList<String>();
        String destUserId = "";    // 下一步审批用户
        // 根据规则，取出对应的user
        List<SysUser> userList = new ArrayList<SysUser>();
        Map map = new HashMap();    // 结果
        if(mapping == null) {

            Map param = new HashMap();
            param.put("definitionkey", definitionkey);
            param.put("definitionid", StringUtils.isEmpty(process.getDefinitionid()) ? null : process.getDefinitionid());
            param.put("type", sign ? "sign" :"aud");
            param.put("taskkey", taskkey);

            List<AuthRule> ruleList = ruleMapper.selectAuthRuleForUserTask(param);

            if(ruleList.size() == 0) {
                param.put("definitionid", definitionid);
                ruleList = ruleMapper.selectAuthRuleForUserTask(param);
            }

            // 根据规则，取出对应的user
            for (AuthRule auth : ruleList) {

                if (StringUtils.isEmpty(auth.getRule())) {

                    continue;
                }

                switch (Integer.parseInt(auth.getRule())) {
                    // 01: 指定用户
                    case 1: {

                        SysUser su = getSysUserById(auth.getUserid());
                        if (su != null) {

                            userList.add(su);
                        }
                        break;
                    }
                    // 02： 本部门指定角色
                    case 2: {

                        List<SysUser> deptRoleUserList = getSysUserListByRoleAndDept(/*auth.getRoleid()*/auth.getRolename(), applyUser.getDepartmentid());
                        userList.addAll(deptRoleUserList);
                        break;
                    }
                    // 03: 本部门
                    case 3: {

                        List<SysUser> deptidUserList = getSysUserListByDept(applyUser.getDepartmentid());
                        userList.addAll(deptidUserList);
                        break;
                    }
                    // 04: 指定角色
                    case 4: {

                        List<SysUser> roleidUserList = getSysUserListByRoleid(auth.getRoleid());
                        userList.addAll(roleidUserList);
                        break;
                    }
                    // 05: 指定部门
                    case 5: {

                        List<SysUser> deptidUserList = getSysUserListByDept(auth.getDeptid());
                        userList.addAll(deptidUserList);
                        break;
                    }
                    // 06: 指定部门与角色
                    case 6: {

                        List<SysUser> deptRoleUserList = getSysUserListByRoleAndDept(/*auth.getRoleid()*/auth.getRolename(), auth.getDeptid());
                        userList.addAll(deptRoleUserList);
                        break;
                    }
                    // 07: 指定岗位
                    case 7: {

                        List<SysUser> postidUserList = getSysUserListByWorkjobid(auth.getPostid());
                        userList.addAll(postidUserList);
                        break;
                    }
                    default:
                        break;
                }

            }
        } else {

            switch (Integer.parseInt(mapping.getTorule())) {
                // 01: 指定用户
                case 1: {

                    SysUser su = getSysUserById(mapping.getTouserid());
                    if (su != null) {

                        userList.add(su);
                    }
                    break;
				}
                // 04: 指定角色
                case 4: {

                    List<SysUser> roleidUserList = getSysUserListByRoleid(mapping.getToroleid());
                    userList.addAll(roleidUserList);
                    break;
                }
                // 05: 指定部门
                case 5: {

                    List<SysUser> deptidUserList = getSysUserListByDept(mapping.getTodeptid());
                    userList.addAll(deptidUserList);
                    break;
                }
                // 06: 指定部门与角色
                case 6: {

                    List<SysUser> deptRoleUserList = getSysUserListByRoleAndDept(/*mapping.getToroleid()*/mapping.getTorolename(), mapping.getTodeptid());
                    userList.addAll(deptRoleUserList);
                    break;
                }
                // 07: 指定岗位
                case 7: {

                    List<SysUser> postidUserList = getSysUserListByWorkjobid(mapping.getTopostid());
                    userList.addAll(postidUserList);
                    break;
                }
                default:
                    break;
            }
        }
		
		// destUserIdList（List<String>）设定
		for(SysUser su : userList) {

			if(!destUserIdList.contains(su.getUserid())) {
				destUserIdList.add(su.getUserid());
			}
		}

		int count = 0;
		destUserId = destUserIdList.toString().substring(1, destUserIdList.toString().length() - 1).replaceAll(" ", "");
		String[] userArray = destUserId.split(",");
		String destUserName = "";
		List<String> deptnames = new ArrayList<String>();
		for(String userId : userArray) {
			
			if(StringUtils.isEmpty(userId)) {
				continue;
			}
			SysUser user = getSysUserById(userId);
			if(user != null) {
				destUserName = destUserName + user.getName() + ", ";
				deptnames.add(user.getDepartmentid());
				count++;
			}
		}
		
		if(StringUtils.isNotEmpty(destUserName)) {
			destUserName = destUserName.substring(0, destUserName.length() - 2);
		}
		if(StringUtils.isEmpty(destUserName)) {
			
			destUserName = "无";
		}
		
		Map<String, String> deptmap = new HashMap<String, String>();
		
		// List<String> deptnames重新设定
		deptnames = new ArrayList<String>();
		for(SysUser su : userList) {
			
			deptnames.add(su.getDepartmentid());
		}
		
		for(String str : deptnames) {
			
			deptmap.putAll(getTopDepts(str));
		}
		
		Set<String> deptset = deptmap.keySet();
		String deptnameStr = "";
		for(String str : deptset) {
			
			deptnameStr = deptnameStr + "d" + str + ",";
		}
		
		map.put("userid", destUserId);
		map.put("username", destUserName);
		map.put("count", count);
		map.put("ids", destUserIdList);
		map.put("widgetstr", deptnameStr + destUserId);
		
		return map;
	}
//
//	@Override
//	public boolean updateCompleteTaskWithoutLog(String taskid, String assignee, String... taskkey) throws Exception {
//
//		Map<String, Object> variables = new HashMap<String, Object>();
//		variables.put(taskkey + "_assignee", assignee);
//		variables.put("firstAssignee", assignee);
//
//		if(taskkey.length == 0) {
//			taskService.complete(taskid, variables);
//		} else {
//			taskService.complete(taskid, variables, taskkey[0]);
//		}
//		return true;
//	}

	@Override
	public boolean deleteAttach(String attachid) throws Exception {

		int ret = attachMapper.deleteAttach(attachid);
		
		return ret > 0;
	}

	@Override
	public List<Comment> getCommentListByProcessid(String processid) {
		
		return commentMapper.getCommentListByProcessid(processid);
	}

	@Override
	public boolean addAttach(String processid, String commentid, String attachid) throws Exception {

		Map map = new HashMap();
		map.put("processid", processid);
		map.put("commentid", commentid);
		map.put("attachid", attachid);
		map.put("adduserid", getSysUser().getUserid());
		int ret = attachMapper.addAttach(map);
		return ret > 0;
	}

	@Override
	public List<Map> selectAttachList(String processid, String commentid) throws Exception {

		return attachMapper.selectAttachList(processid, commentid, getSysUser().getUserid());
	}

	@Override
	public List<Comment> getComments(Map map) {
		
		return commentMapper.getComments(map);
	}

	@Override
	public int updateProcess(Process process) {
		
		return processMapper.updateProcess(process);
	}

	@Override
	public int getProcessCount(PageMap pageMap) throws Exception {

		return processMapper.getProcessCount(pageMap);
	}

	@Override
	public List<Comment> getCommentListByInstanceid(String instanceid) {

		return commentMapper.getCommentListByInstance(instanceid);
	}

	@Override
	public List<Comment> getRealCommentList(String processid,
			String instanceid, boolean agree, boolean end) throws Exception {
		
		if(StringUtils.isEmpty(processid)) {
			
			Process process = getProcess(instanceid, "2");
			if(process != null && StringUtils.isNotEmpty(process.getId())) {
				
				processid = process.getId();
			}
		}
		
		if(StringUtils.isEmpty(instanceid)) {
			
			Process process = getProcess(processid, "1");
			if(process != null && StringUtils.isNotEmpty(process.getInstanceid())) {
				
				instanceid = process.getInstanceid();
			}
		}
		
		List<Comment> list = getCommentListByProcessid(processid);
		List<Comment> list2 = getCommentListByInstanceid(instanceid);
		
		Process process = getProcess(processid, "1");
		
		if(list2.size() == list.size()) {
			
			if(!process.getTaskkey().equals(list.get(list.size() - 1).getTaskkey())) {
				
				Comment comment = new Comment();
				comment.setTaskkey(process.getTaskkey());
				comment.setTaskname(process.getTaskname());
				comment.setTaskid(process.getTaskid());
				comment.setAssignee(process.getAssignee());
				list.add(comment);
			}

			for(int i = 0; i < list.size(); i++) {

				Comment c1 = list.get(i);
				List<Comment> list3 = new ArrayList<Comment>(list);

				for(int j = list3.size() - 1; j > i; j--) {
					
					Comment c2 = list3.get(j);
					
					if(c1.getTaskkey().equals(c2.getTaskkey())/* && !definitionService.isSignTask(process.getDefinitionid(), c1.getTaskkey())*/) {
						
						for(int k = i; k < j; k++) {
							
							list.remove(i);
						}
						i--;
						break;
						
					}
				}
			}
			
			if(agree) {

				for(int i = list.size() - 1; i >= 0; i--) {
					
					Comment comment = list.get(i);
					
					if(!"1".equals(comment.getAgree())) {
						
						list.remove(i);
					}
				}
			}
			
			if(end) {

				for(int i = list.size() - 1; i >= 0; i--) {
					
					Comment comment = list.get(i);
					
					if(StringUtils.isEmpty(comment.getEndtime())) {
						
						list.remove(i);
					}
				}
			}
			
			return list;
		}
		
		return new ArrayList<Comment>();
	}

	@Override
	public boolean takeBackWork(String processid, String taskkey) throws Exception {

		Process process = getProcess(processid, "1");
		SysUser user = getSysUser();
		
		List<Comment> list = getCommentListByProcessid(processid);
		int count = list.size();
		
		Comment comment = list.get(count - 1);
		if(process.getTaskid().equals(comment.getTaskid())) {
			
			String cid = comment.getId();
			commentMapper.deleteComment(cid);
		}
		
		Map variables = new HashMap();
		variables.put("inform_user", false);
		
		taskService.complete(process.getTaskid(), variables, taskkey);
		
		WorkLog log = new WorkLog();
		log.setInstanceid(process.getInstanceid());
		log.setType("1");
		log.setContent(user.getName()+ " 收回了流程");
		log.setTaskname("收回流程");
		addLog(log);

		return true;
	}

	@Override
	public int deleteWork(String id) throws Exception {

		Process process = getProcess(id, "1");
		
		if(process != null && StringUtils.isNotEmpty(process.getInstanceid())) {
		
			SysUser user = getSysUser();
			
			runtimeService.setVariable(process.getInstanceid(), "delete_instance", true);
			runtimeService.deleteProcessInstance(process.getInstanceid(), user.getName());
		}
		
		if(process != null && StringUtils.isNotEmpty(process.getDefinitionkey())) {

			Definition definition = definitionService.getDefinitionByKey(process.getDefinitionkey(), process.getDefinitionid(), null);

//			if(StringUtils.isNotEmpty(definition.getEndlistener())) {
//
//                BusinessEndListener listener = (BusinessEndListener) SpringContextUtils.getBean(definition.getEndlistener());
//
//                if (listener != null) {
//
//                    listener.delete(process);
//                }
//            }
			String businessurl = definition.getBusinessurl();
			String baseurl = "";
			if(StringUtils.isNotEmpty(businessurl)) {
				baseurl = businessurl;
				if(businessurl.indexOf('?') >= 0) {
					baseurl = businessurl.substring(0, businessurl.indexOf('?'));
				}
			}

			if(StringUtils.isNotEmpty(baseurl)) {
				List<Map> handlerList = handlerMapper.selectHanderItemsByUrl(baseurl, "5");
				for(Map handlerMap : handlerList) {
					String handlerStr = (String) handlerMap.get("handler");
					if(StringUtils.isNotEmpty(handlerStr)) {
						BusinessEndListener listener = (BusinessEndListener)SpringContextUtils.getBean(handlerStr);
						if(listener != null){
							listener.delete(process);
							logger.info(">>>>>>>> Target:" + handlerMap.get("clazz") + ".delete(): " + handlerMap.get("handlerdescription"));
						}
					}
				}
			}
		}

        commentMapper.deleteCommentByProcessid(id);
        traceMapper.deleteDataTrace(id);
		return processMapper.deleteProcess(id);
	}

	@Override
	public boolean activateWork(String id) throws Exception {

		boolean ret = false;
		Process process = getProcess(id, "1");
		
		if(process != null && StringUtils.isNotEmpty(process.getInstanceid())) {
		
			runtimeService.activateProcessInstanceById(process.getInstanceid());
		}
		
		if(process != null) {
			
			process.setStatus("1");
			ret = updateProcessByInstance(process);
		}
		
		return ret;
	}

	@Override
	public boolean suspendWork(String id) throws Exception {

		boolean ret = false;
		Process process = getProcess(id, "1");
		
		if(process != null && StringUtils.isNotEmpty(process.getInstanceid())) {
		
			runtimeService.suspendProcessInstanceById(process.getInstanceid());
		}
		
		if(process != null) {
			
			process.setStatus("2");
			ret = updateProcessByInstance(process);
		}

		return ret;
	}

	@Override
	public List<String> selectAllDefinitionKey() throws Exception {

		return processMapper.selectAllDefinitionKey();
	}

	@Override
	public int quitUnsavedWork(Map map) throws Exception {

		String id = (String) map.get("id");
//		Process process = processMapper.getProcess(id);
//		if(process != null) {
//
//			String definitionkey = process.getDefinitionkey();
//			Definition definition = definitionService.getDefinitionByKey(definitionkey, process.getDefinitionid(), null);
//
//			if(StringUtils.isNotEmpty(definition.getEndlistener())) {
//
//				BusinessEndListener listener = (BusinessEndListener) SpringContextUtils.getBean(definition.getEndlistener());
//
//				if (listener != null) {
//
//					listener.delete(process);
//				}
//			}
//		}

        int ret = processMapper.quitUnsavedWork(map);
        if(ret > 0) {
            commentMapper.deleteCommentByProcessid(id);
            traceMapper.deleteDataTrace(id);
        }
        return ret;
	}

	@Override
	public void deleteProcessInstance(String instanceid, String reason)
			throws Exception {
		
		runtimeService.setVariable(instanceid, "delete_instance", true);
		runtimeService.deleteProcessInstance(instanceid, reason);
	}
//
//	@Override
//	public boolean startBusinessWork(String title, String definitionkey,
//			Map<String, Object> veriables) throws Exception {
//
//		SysUser user = getSysUser();
//
//		Process process = new Process();
//
//		process.setApplyuserid(user.getUserid());
//		process.setApplyusername(user.getUsername());
//
//		return false;
//	}

    @Override
    public List<DataTrace> selectDataTraceList(String processid) throws Exception {
        return traceMapper.selectDataTraceList(processid);
    }

    @Override
    public int updateKeywords(Process process) {
        return processMapper.updateKeywords(process);
    }

    @Override
    public int rollbackProcess(Process process) throws Exception {

        String definitionkey = process.getDefinitionkey();
        Definition definition = definitionService.getDefinitionByKey(definitionkey, process.getDefinitionid(), null);

//        if(StringUtils.isNotEmpty(definition.getEndlistener())) {
//
//            BusinessEndListener listener = (BusinessEndListener) SpringContextUtils.getBean(definition.getEndlistener());
//
//            if(listener != null) {
//
//                listener.rollback(process);
//            }
//
//        }
		String businessurl = definition.getBusinessurl();
		String baseurl = "";
		if(StringUtils.isNotEmpty(businessurl)) {
			baseurl = businessurl;
			if(businessurl.indexOf('?') >= 0) {
				baseurl = businessurl.substring(0, businessurl.indexOf('?'));
			}
		}

		if(StringUtils.isNotEmpty(baseurl)) {
			List<Map> handlerList = handlerMapper.selectHanderItemsByUrl(baseurl, "5");
			for(Map handlerMap : handlerList) {
				String handlerStr = (String) handlerMap.get("handler");
				if(StringUtils.isNotEmpty(handlerStr)) {
					BusinessEndListener listener = (BusinessEndListener)SpringContextUtils.getBean(handlerStr);
					if(listener != null){
						listener.rollback(process);
						logger.info(">>>>>>>> Target:" + handlerMap.get("clazz") + ".rollback(): " + handlerMap.get("handlerdescription"));
					}
				}
			}
		}

        process.setStatus("0");
        int ret = updateProcess(process);

        WorkLog log = new WorkLog();
        log.setInstanceid(process.getInstanceid());
        log.setProcessid(process.getId());
        log.setTaskname("作废");
        log.setType("5");
        log.setContent("作废-工作作废");
        addLog(log);

        return ret;
    }

    @Override
    public Map doHandleWork(String definitionkey, String instanceid, String taskid, String title) throws Exception {

        Process process = null;
        Map map = new HashMap();
        SysUser sysUser = getSysUser();

        if(StringUtils.isEmpty(definitionkey)) {

            process = getProcess(instanceid, "2");

            if(process == null) {

                map.put("exist", false);
                return map;
            }

            title = process.getTitle();
            definitionkey = process.getDefinitionkey();
        }

        Definition definition = definitionService.getDefinitionByKey(definitionkey, null, "1");

        String formData = "该流程没配置启动表单！";
        String businessUrl = "";
        String formType = "";
        if(definition != null){
            formType = definition.getFormtype();
            if("business".equals(formType)) {
                businessUrl = definition.getBusinessurl();
            } else {
                Form form = actFormService.getFormByKey(definition.getFormkey());
                if(form != null){
                    formData = CommonUtils.bytes2String(form.getDetail());
                }
            }
        }

        if(StringUtils.isEmpty(instanceid)) {

            process = new Process();
            process.setDefinitionkey(definitionkey);
            process.setDefinitionname(definition.getName());
            process.setDefinitionid(definition.getDefinitionid());
            process.setTitle(title);
            process.setBusinessid(null);
            process.setJson(null);

            if(definition != null && StringUtils.isNotEmpty(definition.getFormkey())) {

                Form form = actFormService.getFormByKey(definition.getFormkey());
                process.setPhonehtml(form.getPhonehtml());
                process.setHtml(form.getDetail());
            }

            addNewWork(process);
            startNewWork(process.getId(), sysUser.getUserid());
            process = getProcess(process.getId(), "1");
        }

		List<Task> taskEntityList = taskService.createTaskQuery().processInstanceId(process.getInstanceid()).taskAssignee(sysUser.getUserid()).list();
		if(process != null && taskEntityList.size() == 0) {

			map.put("valid", false);
			return map;
		}

		Task currentTask = taskEntityList.get(0);

        if(StringUtils.isNotEmpty(taskid) && StringUtils.isNotEmpty(process.getTaskid()) && process != null && !taskid.equals(process.getTaskid())) {

            map.put("valid", false);
            return map;
        }

		if(StringUtils.isNotEmpty(taskid) && StringUtils.isEmpty(process.getTaskid()) && !taskid.equals(currentTask.getId())) {

			map.put("valid", false);
			return map;
		}

        // Add comment to receive work
//		Comment comment = getCommentByTask(process.getTaskid());
		Comment comment = commentMapper.getCommentByUserTask(sysUser.getUserid(), currentTask.getId());
        if(comment == null) {

			comment = new Comment();
            comment.setTaskid(currentTask.getId());
			comment.setTaskname(currentTask.getName());
            addComment(comment);

            int k = processMapper.updateProcessTaskBegintime(process.getInstanceid(), currentTask.getId());

			taskService.claim(currentTask.getId(), getSysUser().getUserid());
        }

        DefinitionTask definitionTask = definitionService.getDefinitionTask(process.getDefinitionkey(), process.getDefinitionid(), process.getTaskkey());
        if(definitionTask != null && StringUtils.isNotEmpty(definitionTask.getBusinessurl())) {
            businessUrl = definitionTask.getBusinessurl();
        }

        map.put("valid", true);
        map.put("exist", true);
        map.put("title", title);
        map.put("definitionkey", definitionkey);
        map.put("definitionName", definition.getName());
        map.put("formData", formData);
        map.put("businessUrl", businessUrl);
        map.put("formType", formType);
        map.put("process", process);

        map.put("handle", "1");
        map.put("definition", definition);
        map.put("keywordrule", definitionService.selectKeywordRuleByDefinitionkey(definition.getUnkey(), definition.getDefinitionid()));
		map.put("sign", definitionService.isSignTask(process.getDefinitionid(), currentTask.getTaskDefinitionKey()));

        return map;
    }

    @Override
    public Map doComment(String processid, String businessid, String taskid) throws Exception {

        Process process = getProcess(processid, "1");

        if(process.getBusinessid() == null || !process.getBusinessid().equals(businessid)) {

            process.setBusinessid(businessid);
            updateProcess(process);
        }

        if(StringUtils.isEmpty(taskid)) {

            taskid = process.getTaskid();
        }

        SysUser sysUser = getSysUser();
		List<Task> taskEntityList = taskService.createTaskQuery().processInstanceId(process.getInstanceid()).taskAssignee(sysUser.getUserid()).list();

		Comment comment = getCommentByTask(taskEntityList.get(0).getId());
        //如果不存在该task的comment，则添加。
        if(comment == null){

            comment = new Comment();
            comment.setTaskid(taskid);
            addComment(comment);
            comment = getCommentByTask(taskid);
        }

        Map map = new HashMap();
        map.put("process", process);
        map.put("comment", comment);
        return map;
    }

    @Override
    public Map doHandleWork2(Process process) throws Exception {

		SysUser user = getSysUser();
		// 新增工作
		if(StringUtils.isEmpty(process.getId())) {

			addNewWork(process);
			startNewWork(process.getId(), user.getUserid());
		}

        process = getProcess(process.getId(), "1");


		List<Task> taskList = taskService.createTaskQuery().processInstanceId(process.getInstanceid()).taskAssignee(user.getUserid()).list();
		if(taskList.size() != 1) {
			throw new Exception("接收工作出错！");
		}
		String taskid = taskList.get(0).getId();

		// 接收工作
        Comment comment = getCommentByTask(taskid);
        if(comment == null) {

            comment = new Comment();
            comment.setTaskid(taskid);
            addComment(comment);

			processMapper.updateProcessTaskBegintime(process.getInstanceid(), comment.getTaskid());
			taskService.claim(taskid, user.getUserid());
        }

        Map map = new HashMap();
        map.put("process", process);
        return map;
    }

	@Override
	public int addHandlerLog(HandlerLog log) {
		return handlerMapper.insertHandlerLog(log);
	}
//
//	@Override
//	public List<Comment> getCommentsList(String instanceid) {
//
//		List<Comment> comments = commentMapper.getCommentListByInstance(instanceid);
//
//		if(comments.size() == 0) {
//			return new ArrayList<Comment>();
//		}
//
//		List<Comment> list = new ArrayList<Comment>();
//		Map temp = new HashMap();
//		for(int i = comments.size() - 1; i >= 0; i--) {
//
//			Comment comment = comments.get(i);
//			if(temp.containsKey(comment.getTaskkey())) {
//				continue;
//			}
//
//			temp.put(comment.getTaskkey(), comment.getTaskkey());
//			list.add(comment);
//		}
//
//		if(list.size() > 0) {
//			Collections.sort(list, new Comparator<Comment>() {
//				@Override
//				public int compare(Comment c1, Comment c2) {
//
//					if("process_begin".equals(c1.getTaskkey())) {
//						return -1;
//					}
//					if("process_begin".equals(c2.getTaskkey())) {
//						return 1;
//					}
//
//					if("process_end".equals(c1.getTaskkey())) {
//						return 1;
//					}
//					if("process_end".equals(c2.getTaskkey())) {
//						return -1;
//					}
//
//					return Integer.parseInt(c1.getTaskid()) - Integer.parseInt(c2.getTaskid());
//				}
//			});
//		}
//
//		return list;
//	}

	@Override
	public PageData selectHandlerLogList(PageMap map) throws Exception {
		return new PageData(handlerMapper.selectHandlerLogListCount(map), handlerMapper.selectHandlerLogList(map), map);
	}

	@Override
	public HandlerLog selectHandlerLogById(String id) throws Exception {
		return handlerMapper.selectHandlerLogById(id);
	}

	@Override
	public int addDataTrace(DataTrace trace) throws Exception {
		return traceMapper.insertDataTrace(trace);
	}

	@Override
	public List<Map> selectListenerClazzes() {
		return handlerMapper.selectListenerClazzes();
	}

    @Override
    public Map listAttach(String processid) throws Exception {

        Map ret = new HashMap();
        int count = 0;

        if(StringUtils.isEmpty(processid)) {

            return null;
        }

        Process process = getProcess(processid, "1");
        if(process != null && StringUtils.isNotEmpty(process.getId())) {
            processid = process.getId();
        }

        List<Map> cfileList = new ArrayList<Map>();

        {
            Map p = new HashMap();
            List<Map> fileList = selectAttachList(processid, null);
            if(fileList.size() >0) {

                String userid = (String) fileList.get(0).get("adduserid");
                SysUser user = getSysUserById(userid);

                Map comment = new HashMap();
                comment.put("taskname", "申请");

                Map res = new HashMap();
                res.put("comment", comment);
                res.put("files", fileList);
                res.put("addusername", user.getName());
                res.put("delete", true);
                if(getSysUser().getUserid().equals(user.getUserid())) {
                    res.put("delete", true);
                }

                cfileList.add(res);
            }
        }

        List<Comment> commentList = getCommentListByProcessid(processid);
        for(Comment comment : commentList) {

            List<Map> fileList = selectAttachList(processid, comment.getId());

            if(fileList.size() > 0) {

                String userid = (String) fileList.get(0).get("adduserid");
                SysUser user = getSysUserById(userid);

                Map res = new HashMap();
                res.put("comment", comment);
                res.put("taskkey", comment.getTaskkey());
                res.put("files", fileList);
                res.put("addusername", user.getName());
                res.put("delete", false);
                if(getSysUser().getUserid().equals(user.getUserid())) {
                    res.put("delete", true);
                }
                count ++;
                cfileList.add(res);
            } else if(fileList.size() == 0 && process.getTaskid().equals(process.getTaskid())) {

                SysUser user = getSysUser();

                Map res = new HashMap();
                res.put("comment", comment);
                res.put("taskkey", comment.getTaskkey());
                res.put("files", fileList);
                res.put("addusername", user.getName());
                res.put("delete", true);

                cfileList.add(res);
            }
        }

        ret.put("filelist", cfileList);
        ret.put("count", count);

        return ret;
    }

	@Override
	public Process selectProcess(String id, String type) throws Exception {

		if(StringUtils.isEmpty(type)){

			type = "1";
		}

		Process process = getProcess(id, type);

		if(process == null) {

			return process;
		}

		// 能否强制删除判断
		process.setCandelete("1");
		String definitionkey = process.getDefinitionkey();
		Definition definition = definitionService.getDefinitionByKey(definitionkey, process.getDefinitionid(), null);

		String businessurl = definition.getBusinessurl();
		String baseurl = "";
		if(StringUtils.isNotEmpty(businessurl)) {
			baseurl = businessurl;
			if(businessurl.indexOf('?') >= 0) {
				baseurl = businessurl.substring(0, businessurl.indexOf('?'));
			}
		}

		if(StringUtils.isNotEmpty(baseurl)) {
			List<Map> handlerList = handlerMapper.selectHanderItemsByUrl(baseurl, "5");
			for(Map handlerMap : handlerList) {
				String handlerStr = (String) handlerMap.get("handler");
				if(StringUtils.isNotEmpty(handlerStr)) {
					BusinessEndListener listener = (BusinessEndListener)SpringContextUtils.getBean(handlerStr);
					if(listener != null){
						if(!listener.check(process)) {
							process.setCandelete("0");
							break;
						}
					}
				}
			}
		}

		return process;
	}

	@Override
	public int addProcessTask(ProcessTask processTask) {
		return processMapper.insertProcessTask(processTask);
	}

	@Override
	public int deleteProcessTaskByTaskkey(String instanceid, String taskkey) {
		return processMapper.deleteProcessTaskByTaskkey(instanceid, taskkey);
	}

	@Override
	public int deleteProcessTaskByInstanceid(String instanceid) {
		int ret = processMapper.deleteProcessTaskByInstanceid(instanceid);
		return ret;
	}

	@Override
	public List<ProcessTask> getProcessTaskListByTaskkey(String instanceid, String taskkey) {
		return processMapper.selectProcessTaskListByTaskkey(instanceid, taskkey);
	}

	@Override
	public List<Map> getNextTasksBySignTask(String instanceid, String taskkey) throws Exception {

		Process process = getProcess(instanceid, "2");

		ActivityImpl src = null;
		ProcessDefinitionEntity entity = (ProcessDefinitionEntity)((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(process.getDefinitionid());
		for(ActivityImpl activity : entity.getActivities()) {
			if(activity.getId().equals(taskkey)) {
				src = activity;
			}
		}

//		List<Comment> commentList = getRealCommentList(process.getId(), process.getInstanceid(), false, true);

		if(src.getOutgoingTransitions().size() != 1) {
			throw new Exception("会签节点配置错误！");
		}

		ActivityImpl gateway = (ActivityImpl) src.getOutgoingTransitions().get(0).getDestination();
		if(!(gateway.getActivityBehavior() instanceof ExclusiveGatewayActivityBehavior)) {
			throw new Exception("会签节点配置错误！");
		}

		List<Map> nextNodeList = new ArrayList<Map>();
		for(PvmTransition transition : gateway.getOutgoingTransitions()) {

			String conditionText = (String) transition.getProperty("conditionText");
			if(StringUtils.isNotEmpty(conditionText) && "${signService.ok(execution)}".equals(conditionText.replaceAll("[\\s;]", ""))) {

				if(definitionService.isSignTask(process.getDefinitionid(), transition.getDestination().getId())) {
					throw new Exception("会签节点之后不能为会签节点！");
				}

				Map nodeMap = new HashMap();
				nodeMap.put("taskkey", transition.getDestination().getId());
				nodeMap.put("taskname", transition.getDestination().getProperty("name"));
				if(((ActivityImpl)transition.getDestination()).getActivityBehavior() instanceof NoneEndEventActivityBehavior) {
					nodeMap.put("type", "endEvent");
				} else if(((ActivityImpl)transition.getDestination()).getActivityBehavior() instanceof UserTaskActivityBehavior) {
					nodeMap.put("type", "userTask");
					nodeMap.put("users", getNextTaskDefinition(process.getDefinitionid(), process.getId(), transition.getDestination().getId(), process.getApplyuserid()));
				}

				nextNodeList.add(nodeMap);
			}

//			if(pre) {
//				continue;
//			}


		}

		return nextNodeList;
	}

	@Override
	public int isSignUser(String instanceid, String taskid, String taskkey) throws Exception {
		return processMapper.selectIsSignUser(instanceid, taskid, taskkey);
	}

	@Override
	public List<Process> getProcessList(Map param) throws Exception {
		return processMapper.selectProcessListByMap(param);
	}
}
