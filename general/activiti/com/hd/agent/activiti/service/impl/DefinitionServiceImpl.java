/**
 * @(#)DefinitionServiceImpl.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 24, 2013 zhengziyong 创建版本
 */
package com.hd.agent.activiti.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;

import com.hd.agent.activiti.dao.*;
import com.hd.agent.activiti.model.*;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.ExclusiveGatewayActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.NoneStartEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.activiti.service.IDefinitionService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 流程定义Service实现类
 * 
 * @author zhengziyong
 */
public class DefinitionServiceImpl extends BaseServiceImpl implements IDefinitionService {

	private DefinitionTypeMapper definitionTypeMapper;

	private DefinitionMapper definitionMapper;

	private DefinitionTaskMapper definitionTaskMapper;

	private DefinitionSignMapper definitionSignMapper;
	
	private AuthRuleMapper ruleMapper;
	
	private EventHandlerMapper handlerMapper;

    private KeywordRuleMapper keywordRuleMapper;

    private AuthMappingMapper mappingMapper;

    private FormItemRuleMapper itemRuleMapper;

    private SourceMapper sourceMapper;

	public DefinitionTypeMapper getDefinitionTypeMapper() {
		return definitionTypeMapper;
	}

	public void setDefinitionTypeMapper(DefinitionTypeMapper definitionTypeMapper) {
		this.definitionTypeMapper = definitionTypeMapper;
	}

	public DefinitionMapper getDefinitionMapper() {
		return definitionMapper;
	}

	public void setDefinitionMapper(DefinitionMapper definitionMapper) {
		this.definitionMapper = definitionMapper;
	}

	public DefinitionTaskMapper getDefinitionTaskMapper() {
		return definitionTaskMapper;
	}

	public void setDefinitionTaskMapper(DefinitionTaskMapper definitionTaskMapper) {
		this.definitionTaskMapper = definitionTaskMapper;
	}

	public DefinitionSignMapper getDefinitionSignMapper() {
		return definitionSignMapper;
	}

	public void setDefinitionSignMapper(DefinitionSignMapper definitionSignMapper) {
		this.definitionSignMapper = definitionSignMapper;
	}

	public AuthRuleMapper getRuleMapper() {
		return ruleMapper;
	}

	public void setRuleMapper(AuthRuleMapper ruleMapper) {
		this.ruleMapper = ruleMapper;
	}

	public EventHandlerMapper getHandlerMapper() {
		return handlerMapper;
	}

	public void setHandlerMapper(EventHandlerMapper handlerMapper) {
		this.handlerMapper = handlerMapper;
	}

    public KeywordRuleMapper getKeywordRuleMapper() {
        return keywordRuleMapper;
    }

    public void setKeywordRuleMapper(KeywordRuleMapper keywordRuleMapper) {
        this.keywordRuleMapper = keywordRuleMapper;
    }

    public AuthMappingMapper getMappingMapper() {
        return mappingMapper;
    }

    public void setMappingMapper(AuthMappingMapper mappingMapper) {
        this.mappingMapper = mappingMapper;
    }

    public FormItemRuleMapper getItemRuleMapper() {
        return itemRuleMapper;
    }

    public void setItemRuleMapper(FormItemRuleMapper itemRuleMapper) {
        this.itemRuleMapper = itemRuleMapper;
    }

    public SourceMapper getSourceMapper() {
        return sourceMapper;
    }

    public void setSourceMapper(SourceMapper sourceMapper) {
        this.sourceMapper = sourceMapper;
    }

    @Override
	public boolean addDefinitionType(DefinitionType type) throws Exception {
		return definitionTypeMapper.addDefinitionType(type)>0;
	}

	@Override
	public List getDefinitionTypeList() throws Exception {
		return definitionTypeMapper.getDefinitionTypeList();
	}

	@Override
	public DefinitionType getDefinitionType(String id) throws Exception {
		return definitionTypeMapper.getDefinitionType(id);
	}

	@Override
	public boolean updateDefinitionType(DefinitionType type) throws Exception {
		return definitionTypeMapper.updateDefinitionType(type)>0;
	}

	@Override
	public boolean deleteDefinitionType(String id) throws Exception {
		return definitionTypeMapper.deleteDefinitionType(id)>0;
	}
	
	@Override
	public String createNewModel() throws Exception {
		Model model = repositoryService.newModel();
		model.setName("");
		model.setVersion(1);
		model.setMetaInfo("{\"name\":\"\",\"description\":\"\"}");
		repositoryService.saveModel(model);
		String editor = "{\"id\":\"canvas\",\"resourceId\":\"canvas\",\"stencilset\":{\"namespace\":\"http://b3mn.org/stencilset/bpmn2.0#\"}}";
		repositoryService.addModelEditorSource(model.getId(), editor.getBytes("utf-8"));
		return model.getId();
	}

	@Override
	public boolean addDefinition(Definition definition) throws Exception {
		SysUser sysUser = getSysUser();
		definition.setAdddeptid(sysUser.getDepartmentid());
		definition.setAdddeptname(sysUser.getDepartmentname());
		definition.setAdduserid(sysUser.getUserid());
		definition.setAddusername(sysUser.getName());
		return definitionMapper.addDefinition(definition)>0;
	}

	@Override
	public boolean updateDefinitionByKey(Definition definition) throws Exception {
		return definitionMapper.updateDefinitionByKey(definition)>0;
	}

	@Override
	public Definition getDefinitionByKey(String definitionkey, String definitionid, String isdeploy) throws Exception {
		Definition definition = definitionMapper.getDefinitionByKey(definitionkey, definitionid, isdeploy);
		if(definition != null){
			DefinitionType type = definitionTypeMapper.getDefinitionTypeByKey(definition.getType());
			definition.setDefinitionType(type);
		}
		return definition;
	}

	@Override
	public PageData getDefinitionData(PageMap pageMap) throws Exception {
		List<Definition> list = definitionMapper.getDefinitionList(pageMap);
        List list2 = new ArrayList<Map>();
		for(Definition definition: list){

            // BUG 3436
			// if(definition != null){
				DefinitionType type = definitionTypeMapper.getDefinitionTypeByKey(definition.getType());
				definition.setDefinitionType(type);
			// }

//            Map map = JSONUtils.jsonStrToMap(JSONUtils.objectToJsonStr(definition));
            Definition latest = definitionMapper.getDefinitionByKey(definition.getUnkey(), null, "1");
            if(latest != null) {

//                map.put("version", latest.getVersion());
//                map.put("formtype", latest.getFormtype());
                definition.setVersion(latest.getVersion());
                definition.setFormtype(latest.getFormtype());
            }
//            map.put("type", type != null ? type.getName() : "");
            definition.setType(type != null ? type.getName() : "");

//            list2.add(map);
            definition.setName(StringEscapeUtils.escapeHtml(CommonUtils.nullToEmpty(definition.getName())));
            list2.add(definition);
		}
		return new PageData(definitionMapper.getDefinitionCount(pageMap), list2, pageMap);
	}

	@Override
	public boolean deploy(String definitionkey) throws Exception {

        Definition definition = definitionMapper.getDefinitionByKey(definitionkey, null, null);

        // 如果最新版本已经部署，则不进行部署操作
        if("1".equals(definition.getIsdeploy())) {

            return true;
        }

        DeploymentBuilder builder = repositoryService.createDeployment().name(definitionkey);
        builder.addInputStream(definitionkey + ".bpmn20.xml", new ByteArrayInputStream(definition.getBytes()));
        Deployment deployment = builder.deploy();
        if(deployment != null && StringUtils.isNotEmpty(deployment.getId())){

            Definition old = definitionMapper.getDefinitionByKey(definitionkey, null, "1");
            String oldDefinitionid = old != null ? old.getDefinitionid() : null;

            definition.setDeploymentid(deployment.getId());
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();

            // insert t_act_source表
            Source source = new Source();
            source.setDefinitionid(processDefinition.getId());
            source.setType("svg");
            source.setBytes(repositoryService.getModelEditorSourceExtra(definition.getModelid()));

            sourceMapper.insertSource(source);

            // 将某个流程unkey所对应的所有流程定义isdeploy置为0：未部署
            definitionMapper.setDefinitionAllEndeployed(definition.getUnkey());

            definition.setDefinitionid(processDefinition.getId());
            definition.setVersion(String.valueOf(processDefinition.getVersion()));
            definition.setIsdeploy("1");
            definition.setIsmodify("0");
            // 将旧版本definition设定迁移到新版本
            if(old != null) {

                definition.setFormtype(old.getFormtype());
                definition.setFormkey(old.getFormkey());
                definition.setBusinessurl(old.getBusinessurl());
                definition.setEndlistener(old.getEndlistener());
                definition.setEndremindtype(old.getEndremindtype());
                definition.setType(old.getType());
            }
            boolean flag = definitionMapper.updateDefinitionById(definition) > 0;

            if(old != null) {

                String newDefinitionid = processDefinition.getId();

                if(StringUtils.isNotEmpty(definition.getDefinitionid()) && StringUtils.isNotEmpty(newDefinitionid) && StringUtils.isNotEmpty(oldDefinitionid)) {

                    SysUser user = getSysUser();

                    Map param = new HashMap();
                    param.put("olddefinitionid", oldDefinitionid);
                    param.put("newdefinitionid", newDefinitionid);
                    param.put("adduserid", user.getUserid());
                    param.put("addusername", user.getUsername());
                    param.put("adddeptid", user.getDepartmentid());
                    param.put("adddeptname", user.getDepartmentname());

                    //int ret1 = definitionMapper.doCloneOldVerson2NewVersion(param);
                    definitionTaskMapper.doCloneOldVerson2NewVersion(param);
                    ruleMapper.doCloneOldVerson2NewVersion(param);
                    mappingMapper.doCloneOldVerson2NewVersion(param);
                    itemRuleMapper.doCloneOldVerson2NewVersion(param);
                    handlerMapper.doCloneOldVerson2NewVersion(param);
                    keywordRuleMapper.doCloneOldVerson2NewVersion(param);
                    definitionSignMapper.doCloneOldVerson2NewVersion(param);

					fillDefinitionSignTask(definition);
					fillDefinitionTask(definition);
                }

                return flag;
            }

			fillDefinitionSignTask(definition);
			fillDefinitionTask(definition);
            return true;

        }
        return false;

    }

	@Override
	public InputStream getDefinitionDiagram(String definitionkey, String definitionid) throws Exception {

		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(/*definition.getDefinitionid()*/definitionid).singleResult();
		String resourceName = processDefinition.getDiagramResourceName();
		InputStream is = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
		return is; 
	}

	@Override
	public List getDefinitionHistoryList(String definitionkey) throws Exception {
		List<ProcessDefinition> definitionList = repositoryService.createProcessDefinitionQuery().processDefinitionKey(definitionkey).orderByDeploymentId().desc().list();
		return definitionList;
	}

	@Override
	public boolean enableDefinition(String prodefid) throws Exception {

        definitionMapper.setDefinitionAllEndeployed(prodefid.split(":")[0]);

        Definition definition = definitionMapper.getDefinitionByKey(prodefid.split(":")[0], prodefid, null);
        definition.setIsdeploy("1");
        definition.setIsmodify("0");

        int ret2 =definitionMapper.enableDefinitionVersion(prodefid);
        return ret2 > 0;
	}

	@Override
	public List<Map<String, Object>> getUserTaskList(String definitionkey, String definitionid) throws Exception {

        ProcessDefinitionEntity entity = null;
        Definition definition = getDefinitionByKey(definitionkey, definitionid, StringUtils.isEmpty(definitionid) ? "1" : "0");
        if(StringUtils.isEmpty(definitionid)) {
            entity = (ProcessDefinitionEntity)((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(definition.getDefinitionid());
        } else {
            entity = (ProcessDefinitionEntity)((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(definitionid);
        }

		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();

        SysUser user = getSysUser();
        ActivityImpl startActivity = null;

		List<ActivityImpl> activityList = entity.getActivities();
		for(ActivityImpl activityImpl: activityList){

            ActivityBehavior activityBehavior = activityImpl.getActivityBehavior();
            if(activityBehavior instanceof NoneStartEventActivityBehavior) {
                startActivity = activityImpl;
                break;
            }
		}

		List<ActivityImpl> taskList = getActivitiNodeList(startActivity, new ArrayList(), new ArrayList());
		Collections.reverse(taskList);	// 返回的tasklist为倒序

        for(ActivityImpl a : taskList) {

            ActivityBehavior activityBehavior = a.getActivityBehavior();
            if(activityBehavior instanceof UserTaskActivityBehavior
					|| activityBehavior instanceof ParallelMultiInstanceBehavior){
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("key", a.getId());
                map.put("name", a.getProperty("name"));
                DefinitionTask definitionTask = definitionTaskMapper.getDefinitionTaskByKey(definitionkey, entity.getId(), a.getId());

                if(definitionTask == null) {

                    definitionTask = new DefinitionTask();
                    definitionTask.setDefinitionkey(definitionkey);
                    definitionTask.setDefinitionid(entity.getId());
                    definitionTask.setTaskkey(a.getId());
                    definitionTask.setTaskname((String) a.getProperty("name"));

                    definitionTask.setAdduserid(user.getUserid());
                    definitionTask.setAddusername(user.getName());
                    definitionTask.setAdddeptid(user.getDepartmentid());
                    definitionTask.setAdddeptname(user.getDepartmentname());

                    definitionTaskMapper.addDefinitionTask(definitionTask);
                    definitionTask = definitionTaskMapper.getDefinitionTaskByKey(definitionkey, entity.getId(), a.getId());
                }

                if(definitionTask != null){
                    map.put("formkey", definitionTask.getFormkey());
                    map.put("businessUrl", definitionTask.getBusinessurl());
                    map.put("remindtype", definitionTask.getRemindtype());
                    map.put("endremindapplier", definitionTask.getEndremindapplier());
                }
				if(activityBehavior instanceof UserTaskActivityBehavior){
					map.put("nodeType", "UserTaskActivityBehavior");
				} else if(activityBehavior instanceof ParallelMultiInstanceBehavior){
					map.put("nodeType", "ParallelMultiInstanceBehavior");
				}
                result.add(map);
            }
        }

        return result;
	}
	
	@Override
	public List<Map<String, Object>> getSignTaskList(String definitionkey) throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Definition definition = getDefinitionByKey(definitionkey, null, "1");
		ProcessDefinitionEntity entity = (ProcessDefinitionEntity)((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(definition.getDefinitionid());
		List<ActivityImpl> activityList = entity.getActivities();
		for(ActivityImpl activityImpl: activityList){
			ActivityBehavior activityBehavior = activityImpl.getActivityBehavior();
			if(activityBehavior instanceof ParallelMultiInstanceBehavior){
				Map<String, Object> map = new HashMap<String, Object>();
				DefinitionSign sign = definitionSignMapper.getDefinitionSignByDefinitionid(definition.getDefinitionid(), activityImpl.getId());
				map.put("taskkey", activityImpl.getId());
				map.put("taskname", activityImpl.getProperty("name"));
				
				if(sign != null) {
				
					map.put("counttype", sign.getCounttype());
					map.put("decisiontype", sign.getDecisiontype());
					map.put("votetype", sign.getVotetype());
					map.put("votenum", sign.getVotenum());
					map.put("user", sign.getUser());
					map.put("username", sign.getName());
				}
				result.add(map);
			}
		}
		return result;
	}

	@Override
	public boolean addDefinitionTask(DefinitionTask task, boolean flag) throws Exception {
		
		if(!flag) {

			// 审批节点用户设定（元）
			int count = definitionTaskMapper.getCountByKey(task.getDefinitionkey(), task.getDefinitionid(), task.getTaskkey());
			SysUser sysUser = getSysUser();
			if(count > 0){
				task.setModifyuserid(sysUser.getUserid());
				task.setModifyusername(sysUser.getName());
				return definitionTaskMapper.updateDefinitionTaskByKey(task)>0;
			}
			else{
				task.setAdduserid(sysUser.getUserid());
				task.setAddusername(sysUser.getName());
				task.setAdddeptid(sysUser.getDepartmentid());
				task.setAdddeptname(sysUser.getDepartmentname());
				return definitionTaskMapper.addDefinitionTask(task)>0;
			}
		}

		SysUser user = getSysUser();
		
		Map map = new HashMap();
		map.put("definitionkey", task.getDefinitionkey());
        map.put("definitionid", task.getDefinitionid());
		map.put("type", "aud");
		map.put("taskkey", task.getTaskkey());
		
		ruleMapper.deleteAuthRuleForUserTask(map);
		
		Map<String, String> m = new HashMap<String, String>();
		m.put("assignee", "01");
		m.put("departmentRole", "02");
		m.put("department", "03");
		m.put("oneRole", "04");
		m.put("oneDepartment", "05");
		m.put("oneDepartmentOneRole", "06");
		m.put("onePost", "07");
		
		int rule = Integer.parseInt(StringUtils.isEmpty(m.get(task.getRule())) ? "01" : m.get(task.getRule()));
		
		List<AuthRule> list = new ArrayList<AuthRule>();
		switch (rule) {
		// 01: 指定人员
		case 1 : {
		
			String userids = CommonUtils.nullToEmpty(task.getUser());
			if(!"".equals(userids)) {
				
				String[] idArray = userids.split(",");
				for(String userid : idArray) {
					
					if(StringUtils.isEmpty(userid)) {

						continue;
					}
					
					AuthRule auth = new AuthRule();
					auth.setDefinitionkey(task.getDefinitionkey());
                    auth.setDefinitionid(task.getDefinitionid());
					auth.setType("aud");
					auth.setTaskkey(task.getTaskkey());
					auth.setRule("01");
					auth.setUserid(userid);
					auth.setAdduserid(user.getUserid());
					
					list.add(auth);
				}
			}
			
			break;
		}
		// 本部门指定角色
		case 2 : {
			
			String roleids = CommonUtils.nullToEmpty(task.getRole());
			if(!"".equals(roleids)) {
				
				String[] idArray = roleids.split(",");
				for(String roleid : idArray) {
					
					if(StringUtils.isEmpty(roleid)) {

						continue;
					}

					AuthRule auth = new AuthRule();
					auth.setDefinitionkey(task.getDefinitionkey());
                    auth.setDefinitionid(task.getDefinitionid());
					auth.setType("aud");
					auth.setTaskkey(task.getTaskkey());
					auth.setRule("02");
					auth.setRoleid(roleid);
					auth.setAdduserid(user.getUserid());
					
					list.add(auth);
				}
			}
			
			break;
		}
		// 03: 本部门
		case 3 : {
			
			AuthRule auth = new AuthRule();
			auth.setDefinitionkey(task.getDefinitionkey());
            auth.setDefinitionid(task.getDefinitionid());
			auth.setType("aud");
			auth.setTaskkey(task.getTaskkey());
			auth.setRule("03");
			auth.setAdduserid(user.getUserid());
			
			list.add(auth);
			
			break;
		}
		// 04：指定角色
		case 4 : {
			
			String roleids = CommonUtils.nullToEmpty(task.getRole());
			if(!"".equals(roleids)) {
				
				String[] idArray = roleids.split(",");
				for(String roleid : idArray) {
					
					if(StringUtils.isEmpty(roleid)) {

						continue;
					}

					AuthRule auth = new AuthRule();
					auth.setDefinitionkey(task.getDefinitionkey());
                    auth.setDefinitionid(task.getDefinitionid());
					auth.setType("aud");
					auth.setTaskkey(task.getTaskkey());
					auth.setRule("04");
					auth.setRoleid(roleid);
					auth.setAdduserid(user.getUserid());
					
					list.add(auth);
				}
			}

			break;
		}
		// 05: 指定部门
		case 5 : {
			
			String deptids = CommonUtils.nullToEmpty(task.getDept());
			if(!"".equals(deptids)) {
				
				String[] idArray = deptids.split(",");
				for(String deptid : idArray) {
					
					if(StringUtils.isEmpty(deptid)) {

						continue;
					}

					AuthRule auth = new AuthRule();
					auth.setDefinitionkey(task.getDefinitionkey());
                    auth.setDefinitionid(task.getDefinitionid());
					auth.setType("aud");
					auth.setTaskkey(task.getTaskkey());
					auth.setRule("05");
					auth.setDeptid(deptid);
					auth.setAdduserid(user.getUserid());
					
					list.add(auth);
				}
			}

			break;
		}
		// 06: 指定部门与角色
		case 6 : {
			
			String deptids = CommonUtils.nullToEmpty(task.getDept());
			String roleids = CommonUtils.nullToEmpty(task.getRole());
			
			if(!"".equals(deptids) && !"".equals(roleids)) {
				
				String[] idArray1 = deptids.split(",");
				String[] idArray2 = roleids.split(",");
				
				for(String deptid : idArray1) {
					
					for(String roleid : idArray2) {

						AuthRule auth = new AuthRule();
						auth.setDefinitionkey(task.getDefinitionkey());
                        auth.setDefinitionid(task.getDefinitionid());
						auth.setType("aud");
						auth.setTaskkey(task.getTaskkey());
						auth.setRule("06");
						auth.setDeptid(deptid);
						auth.setRoleid(roleid);
						auth.setAdduserid(user.getUserid());
						
						list.add(auth);
					}
				}
			}

			break;
		}
		// 07: 指定岗位
		case 7 : {
			
			String postids = CommonUtils.nullToEmpty(task.getPost());
			
			if(!"".equals(postids)) {
				
				String[] idArray = postids.split(",");
				for(String postid : idArray) {

					AuthRule auth = new AuthRule();
					auth.setDefinitionkey(task.getDefinitionkey());
                    auth.setDefinitionid(task.getDefinitionid());
					auth.setType("aud");
					auth.setTaskkey(task.getTaskkey());
					auth.setRule("07");
					auth.setPostid(postid);
					auth.setAdduserid(user.getUserid());
					
					list.add(auth);
				}
			}
			
			break;
		}
		default:
			break;
		}
		
		for(AuthRule auth : list) {
			
			ruleMapper.addAuthRule(auth);
		}
		
		return true;
	}

	@Override
	public boolean addDefinitionTaskForm(Definition definition, List<DefinitionTask> tasks) throws Exception {

		for(DefinitionTask task: tasks){
			if("formkey".equals(definition.getFormtype())){
				task.setBusinessurl("");
			}
			else if("business".equals(definition.getFormtype())){
				task.setFormkey("");
			}
			addDefinitionTask(task, false);
		}
		if("formkey".equals(definition.getFormtype())){
			definition.setBusinessurl("");
			definition.setEndlistener("");
		}
		else if("business".equals(definition.getFormtype())){
			definition.setFormkey("");
		}
		return updateDefinitionByKey(definition);
	}
	
	@Override
	public DefinitionTask getDefinitionTaskByKey(String definitionkey, String definitionid, String taskkey) throws Exception {

        return definitionTaskMapper.getDefinitionTaskByKey(definitionkey, definitionid, taskkey);
	}

	@Override
	public boolean addDefinitionSign(DefinitionSign sign) throws Exception {
		int count = definitionSignMapper.getDefinitionSignCountByDefinitionid(sign.getDefinitionid(), sign.getTaskkey());
		SysUser user = getSysUser();
		if(count>0){
			sign.setModifyuserid(user.getUserid());
			sign.setModifyusername(user.getName());
			return definitionSignMapper.updateDefinitionSignByKey(sign)>0;
		}
		else{
			sign.setAdddeptid(user.getDepartmentid());
			sign.setAdddeptname(user.getDepartmentname());
			sign.setAdduserid(user.getUserid());
			sign.setAddusername(user.getName());
			return definitionSignMapper.addDefinitionSign(sign)>0;
		}
	}
	
	@Override
	public boolean addDefinitionSign(List<DefinitionSign> signList) throws Exception {
		for(DefinitionSign sign : signList){
			addDefinitionSign(sign);
		}
		return true;
	}
	
	@Override
	public DefinitionSign getDefinitionSignByKey(String definitionid, String taskkey) throws Exception {
//		Definition definition = getDefinitionByKey(definitionkey, null, "1");
		return definitionSignMapper.getDefinitionSignByDefinitionid(definitionid, taskkey);
	}

	@Override
	public List<Map> getDefinitionTree(String type) throws Exception {
		
		SysUser user = getSysUser();		// 当前用户
		List list = getSysUserRoleList();	// 角色List
		
		// 参数设定
		Map map = new HashMap();
		// 
		// String dataSql = getDataAccessRule("t_act_auth_rule", "r");
		// map.put("dataSql", dataSql);
		map.put("type", type);
		map.put("deptid", user.getDepartmentid());
		map.put("postid", user.getWorkjobid());
		map.put("userid", user.getUserid());
		map.put("roleid", list.toString().substring(1, list.toString().length() - 1).replaceAll(" ", ""));

        // 获取每个流程的首个taskkey
        List<Definition> definitions = definitionMapper.getAllDefinitionVersions();
        List<Map> definitionMaps = new ArrayList<Map>();
        for(Definition d : definitions) {

            Map temp = new HashMap();
            temp.put("definitionkey", d.getUnkey());
            temp.put("taskkey", getFirstTaskkeyOfDefinition(d.getUnkey()));
            definitionMaps.add(temp);
        }

        if(definitionMaps.size() == 0) {
            definitionMaps = null;
        }
        map.put("definitionMaps", definitionMaps);
  
		List<Map> tree = definitionMapper.getDefinitionTree(map);
		List<Map> tree2 = new ArrayList<Map>();
		
		for(int i = 0; i < tree.size(); i++) {
			
			Map m1 = tree.get(i);
			if("0".equals(m1.get("isparent"))) {
				
				tree2.add(m1);
				continue;
			}
			
			for(int j = 0; j < tree.size(); j++) {
				
				Map m2 = tree.get(j);
				if(m1.get("id").equals(m2.get("pid"))) {
					
					tree2.add(m1);
					break;
				}
			}
		}
		
		return tree2;
	}

	@Override
	public int setCanassign(DefinitionTask definitionTask) throws Exception {

		SysUser user = getSysUser();
		
		// 是否指定人员设定（新）
		Map map = new HashMap();
		map.put("definitionkey", definitionTask.getDefinitionkey());
		map.put("type", "aud");
		map.put("taskkey", definitionTask.getTaskkey());
		
		// 清除设定
		ruleMapper.deleteAuthRuleForCanassign(map);
		
		// 添加设定
		AuthRule auth = new AuthRule();
		auth.setDefinitionkey(definitionTask.getDefinitionkey());
		auth.setType("aud");
		auth.setTaskkey(definitionTask.getTaskkey());
		auth.setRule("");
		auth.setCanassign(definitionTask.getCanassign());
		auth.setAdduserid(user.getUserid());
		
		int ret = ruleMapper.addAuthRule(auth);
		
		// 是否指定人员设定（元）
		definitionTaskMapper.updateDefinitionTaskByKey(definitionTask);
		
		return ret;
	}

	@Override
	public byte[] getDefinitionSvg(String definitionid, String modelId) throws Exception {

        Source source = sourceMapper.selectSource(definitionid, "svg");

        if(source != null && source.getBytes() != null) {

            return source.getBytes();
        }

		byte[] svg = repositoryService.getModelEditorSourceExtra(modelId);
		return svg;
	}

	@Override
	public boolean addAuthRule(List<AuthRule> list) {

		if(list.size() > 0) {
            ruleMapper.deleteAuthRuleForDefinitionTask(list.get(0).getDefinitionkey(), list.get(0).getDefinitionid(), list.get(0).getTaskkey(), list.get(0).getType());
		}
		
		int sum = 0;
		for(AuthRule rule : list) {
			
			int ret = ruleMapper.addAuthRule(rule);
			sum = sum + ret;
		}
		return sum == list.size();
	}

	@Override
	public Map getRuleDetailDescription(String definitionkey, String taskkey, String type) throws Exception {

        Definition definition = getDefinitionByKey(definitionkey, null, "1");

		Map param = new HashMap();
		param.put("definitionkey", definitionkey);
        param.put("definitionid", definition.getDefinitionid());
		param.put("taskkey", CommonUtils.emptyToNull(taskkey));
		param.put("type", type);
		List<AuthRule> arList = ruleMapper.selectAuthRule(param);

        // 如果根据最新的规则取不到
        if(arList.size() == 0) {

            param.remove("definitionid");
            arList = ruleMapper.selectAuthRule(param);
        }
		
		Map rule01 = new HashMap();
		//rule01.put("rule", "01");
		rule01.put("userid", new ArrayList<String>());
		rule01.put("deptid", new ArrayList<String>());
		rule01.put("roleid", new ArrayList<String>());
		rule01.put("postid", new ArrayList<String>());

		Map rule02 = new HashMap();
		//rule02.put("rule", "02");
		rule02.put("userid", new ArrayList<String>());
		rule02.put("deptid", new ArrayList<String>());
		rule02.put("roleid", new ArrayList<String>());
		rule02.put("postid", new ArrayList<String>());

		Map rule03 = new HashMap();
		//rule03.put("rule", "03");
		rule03.put("userid", new ArrayList<String>());
		rule03.put("deptid", new ArrayList<String>());
		rule03.put("roleid", new ArrayList<String>());
		rule03.put("postid", new ArrayList<String>());

		Map rule04 = new HashMap();
		//rule04.put("rule", "04");
		rule04.put("userid", new ArrayList<String>());
		rule04.put("deptid", new ArrayList<String>());
		rule04.put("roleid", new ArrayList<String>());
		rule04.put("postid", new ArrayList<String>());

		Map rule05 = new HashMap();
		//rule05.put("rule", "05");
		rule05.put("userid", new ArrayList<String>());
		rule05.put("deptid", new ArrayList<String>());
		rule05.put("roleid", new ArrayList<String>());
		rule05.put("postid", new ArrayList<String>());

		Map rule06 = new HashMap();
		//rule06.put("rule", "06");
		rule06.put("userid", new ArrayList<String>());
		rule06.put("deptid", new ArrayList<String>());
		rule06.put("roleid", new ArrayList<String>());
		rule06.put("postid", new ArrayList<String>());

		Map rule07 = new HashMap();
		//rule07.put("rule", "07");
		rule07.put("userid", new ArrayList<String>());
		rule07.put("deptid", new ArrayList<String>());
		rule07.put("roleid", new ArrayList<String>());
		rule07.put("postid", new ArrayList<String>());

		Map result = new HashMap();
		result.put("r01", rule01);
		result.put("r02", rule02);
		result.put("r03", rule03);
		result.put("r04", rule04);
		result.put("r05", rule05);
		result.put("r06", rule06);
		result.put("r07", rule07);

        // 解析取得的规则List
        for(AuthRule ar : arList) {

            if(StringUtils.isEmpty(ar.getRule())) {

                continue;
            }

            ((Map) result.get("r" + ar.getRule())).put("rule", ar.getRule());

            List<String> userids = ((List<String>)(((Map) result.get("r" + ar.getRule())).get("userid")));
            List<String> deptids = ((List<String>)(((Map) result.get("r" + ar.getRule())).get("deptid")));
            List<String> roleids = ((List<String>)(((Map) result.get("r" + ar.getRule())).get("roleid")));
            List<String> postids = ((List<String>)(((Map) result.get("r" + ar.getRule())).get("postid")));

            boolean userid_flag = true;
            boolean deptid_flag = true;
            boolean roleid_flag = true;
            boolean postid_flag = true;
            for(String str : userids) {

                if(str.equals(ar.getUserid())) {

                    userid_flag = false;
                    break;
                }
            }
            for(String str : deptids) {

                if(str.equals(ar.getDeptid())) {

                    deptid_flag = false;
                    break;
                }
            }
            for(String str : roleids) {

                if(str.equals(ar.getRoleid())) {

                    roleid_flag = false;
                    break;
                }
            }
            for(String str : postids) {

                if(str.equals(ar.getPostid())) {

                    postid_flag = false;
                    break;
                }
            }

            if(userid_flag) {
                userids.add(ar.getUserid());
            }
            if(deptid_flag) {
                deptids.add(ar.getDeptid());
            }
            if(roleid_flag) {
                roleids.add(ar.getRoleid());
            }
            if(postid_flag) {
                postids.add(ar.getPostid());
            }
        }
		
		rule01.put("userid", ((List<String>)rule01.get("userid")).toString().substring(1, ((List<String>)rule01.get("userid")).toString().length() - 1).replaceAll(" ", ""));
		rule02.put("userid", ((List<String>)rule02.get("userid")).toString().substring(1, ((List<String>)rule02.get("userid")).toString().length() - 1).replaceAll(" ", ""));
		rule03.put("userid", ((List<String>)rule03.get("userid")).toString().substring(1, ((List<String>)rule03.get("userid")).toString().length() - 1).replaceAll(" ", ""));
		rule04.put("userid", ((List<String>)rule04.get("userid")).toString().substring(1, ((List<String>)rule04.get("userid")).toString().length() - 1).replaceAll(" ", ""));
		rule05.put("userid", ((List<String>)rule05.get("userid")).toString().substring(1, ((List<String>)rule05.get("userid")).toString().length() - 1).replaceAll(" ", ""));
		rule06.put("userid", ((List<String>)rule06.get("userid")).toString().substring(1, ((List<String>)rule06.get("userid")).toString().length() - 1).replaceAll(" ", ""));
		rule07.put("userid", ((List<String>)rule07.get("userid")).toString().substring(1, ((List<String>)rule07.get("userid")).toString().length() - 1).replaceAll(" ", ""));
		
		rule01.put("deptid", ((List<String>)rule01.get("deptid")).toString().substring(1, ((List<String>)rule01.get("deptid")).toString().length() - 1).replaceAll(" ", ""));
		rule02.put("deptid", ((List<String>)rule02.get("deptid")).toString().substring(1, ((List<String>)rule02.get("deptid")).toString().length() - 1).replaceAll(" ", ""));
		rule03.put("deptid", ((List<String>)rule03.get("deptid")).toString().substring(1, ((List<String>)rule03.get("deptid")).toString().length() - 1).replaceAll(" ", ""));
		rule04.put("deptid", ((List<String>)rule04.get("deptid")).toString().substring(1, ((List<String>)rule04.get("deptid")).toString().length() - 1).replaceAll(" ", ""));
		rule05.put("deptid", ((List<String>)rule05.get("deptid")).toString().substring(1, ((List<String>)rule05.get("deptid")).toString().length() - 1).replaceAll(" ", ""));
		rule06.put("deptid", ((List<String>)rule06.get("deptid")).toString().substring(1, ((List<String>)rule06.get("deptid")).toString().length() - 1).replaceAll(" ", ""));
		rule07.put("deptid", ((List<String>)rule07.get("deptid")).toString().substring(1, ((List<String>)rule07.get("deptid")).toString().length() - 1).replaceAll(" ", ""));
		
		rule01.put("roleid", ((List<String>)rule01.get("roleid")).toString().substring(1, ((List<String>)rule01.get("roleid")).toString().length() - 1).replaceAll(" ", ""));
		rule02.put("roleid", ((List<String>)rule02.get("roleid")).toString().substring(1, ((List<String>)rule02.get("roleid")).toString().length() - 1).replaceAll(" ", ""));
		rule03.put("roleid", ((List<String>)rule03.get("roleid")).toString().substring(1, ((List<String>)rule03.get("roleid")).toString().length() - 1).replaceAll(" ", ""));
		rule04.put("roleid", ((List<String>)rule04.get("roleid")).toString().substring(1, ((List<String>)rule04.get("roleid")).toString().length() - 1).replaceAll(" ", ""));
		rule05.put("roleid", ((List<String>)rule05.get("roleid")).toString().substring(1, ((List<String>)rule05.get("roleid")).toString().length() - 1).replaceAll(" ", ""));
		rule06.put("roleid", ((List<String>)rule06.get("roleid")).toString().substring(1, ((List<String>)rule06.get("roleid")).toString().length() - 1).replaceAll(" ", ""));
		rule07.put("roleid", ((List<String>)rule07.get("roleid")).toString().substring(1, ((List<String>)rule07.get("roleid")).toString().length() - 1).replaceAll(" ", ""));
		
		rule01.put("postid", ((List<String>)rule01.get("postid")).toString().substring(1, ((List<String>)rule01.get("postid")).toString().length() - 1).replaceAll(" ", ""));
		rule02.put("postid", ((List<String>)rule02.get("postid")).toString().substring(1, ((List<String>)rule02.get("postid")).toString().length() - 1).replaceAll(" ", ""));
		rule03.put("postid", ((List<String>)rule03.get("postid")).toString().substring(1, ((List<String>)rule03.get("postid")).toString().length() - 1).replaceAll(" ", ""));
		rule04.put("postid", ((List<String>)rule04.get("postid")).toString().substring(1, ((List<String>)rule04.get("postid")).toString().length() - 1).replaceAll(" ", ""));
		rule05.put("postid", ((List<String>)rule05.get("postid")).toString().substring(1, ((List<String>)rule05.get("postid")).toString().length() - 1).replaceAll(" ", ""));
		rule06.put("postid", ((List<String>)rule06.get("postid")).toString().substring(1, ((List<String>)rule06.get("postid")).toString().length() - 1).replaceAll(" ", ""));
		rule07.put("postid", ((List<String>)rule07.get("postid")).toString().substring(1, ((List<String>)rule07.get("postid")).toString().length() - 1).replaceAll(" ", ""));

        List mappings = mappingMapper.selectMappingByTaskkey(definitionkey, definition.getDefinitionid(), taskkey, null);
        result.put("mappings", mappings.size());

		return result;
	}

	@Override
	public DefinitionTask getDefinitionTask(String definitionkey, String definitionid, String taskkey)
			throws Exception {

		return definitionTaskMapper.getDefinitionTaskByKey(definitionkey, definitionid, taskkey);
	}

	@Override
	public List<AuthRule> selectAuthRule(Map map) throws Exception {

		return ruleMapper.selectAuthRule(map);
	}

	@Override
	public List<EventHandler> selectEventHandlerByTask(Map map)
			throws Exception {

		return handlerMapper.selectEventHandlerByTask(map);
	}

	@Override
	public int saveEventHandlerForTask(String definitionkey, String taskkey, String event, List<EventHandler> list) throws Exception {

        Definition definition = definitionMapper.getDefinitionByKey(definitionkey, null, "1");

		handlerMapper.deleteEventHandlerByTask(definitionkey, definition.getDefinitionid(), taskkey, event);
		
		if(list.size() == 0) {
			
			return 0;
		}
		
		for(EventHandler handler : list) {

            handler.setDefinitionid(definition.getDefinitionid());
			handlerMapper.insertEventHandler(handler);
		}
		
		return 0;
	}

	@Override
	public int clearAuthRule(String definitionkey, String taskkey, String type)
			throws Exception {

        Definition definition = getDefinitionByKey(definitionkey, null, "1");

        int ret1 = mappingMapper.deleteMappingByTaskkey(definitionkey, definition.getDefinitionid(), taskkey, "1");
        int ret2 = mappingMapper.deleteMappingByTaskkey(definitionkey, definition.getDefinitionid(), taskkey, "2");
		int ret3 = ruleMapper.clearAuthRule(definitionkey, taskkey, type);

        return ret1 + ret2 + ret3;
	}

    @Override
    public int isDefinitionExist(String unkey, String modelid) throws Exception {

        return definitionMapper.isDefinitionExist(unkey, modelid);
    }

    @Override
    public KeywordRule selectKeywordRuleByDefinitionkey(String definitionkey, String definitionid) throws Exception {
        return keywordRuleMapper.selectKeywordRuleByDefinitionkey(definitionkey, definitionid);
    }

    @Override
    public int insertKeywordRuleByDefinitionkey(KeywordRule keywordRule) throws Exception {
        keywordRuleMapper.deleteKeywordRuleByDefinitionkey(keywordRule.getDefinitionkey(), keywordRule.getDefinitionid());
        return keywordRuleMapper.insertKeywordRule(keywordRule);
    }

    @Override
    public int deleteDefinition(String modelid, String unkey) throws Exception {

        repositoryService.deleteDeployment(modelid, true);

        if(repositoryService.getModel(modelid) != null) {
            repositoryService.deleteModel(modelid);
        }

        int ret = definitionMapper.deleteDefinitionByUnkey(unkey);

        return ret;
    }

    @Override
    public int saveAuthMapping(String definitionkey, String taskkey, String mappingtype, List<Map<String, String>> list) throws Exception {

        List<AuthMapping> mappings = new ArrayList<AuthMapping>();

        Definition definition = getDefinitionByKey(definitionkey, null, "1");

        mappingMapper.deleteMappingByTaskkey(definitionkey, definition.getDefinitionid(), taskkey, mappingtype);

        for(Map<String, String> mapping : list) {

            mappings.addAll(resolveAuthMapping(mapping));

        }

        SysUser user = null;
        if(mappings.size() > 0) {
            user = getSysUser();
        }

        int count = 1;
        for(AuthMapping mapping : mappings) {

            mapping.setDefinitionkey(definitionkey);
            mapping.setDefinitionid(definition.getDefinitionid());
            mapping.setTaskkey(taskkey);
            mapping.setMappingtype(mappingtype);
            mapping.setAdduserid(user.getUserid());
            mappingMapper.insertMapping(mapping);
            count ++;
        }

        return count;
    }

    /**
     *
     * @param mapping
     * @return
     */
    private List<AuthMapping> resolveAuthMapping(Map<String, String> mapping) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, ParseException {

        List<Map<String, Object>> frommappings = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> tomappings = new ArrayList<Map<String, Object>>();
        List<AuthMapping> list = new ArrayList<AuthMapping>();

        // 解析来源规则
        {
            String[] userids = mapping.get("fromuserids").split(",");
            String[] deptids = mapping.get("fromdeptids").split(",");
            String[] roleids = mapping.get("fromroleids").split(",");
            String[] postids = mapping.get("frompostids").split(",");

            for(String userid : userids) {

                for(String deptid : deptids) {

                    for(String roleid : roleids) {

                        for(String postid : postids) {

                            Map<String, Object> temp = new HashMap<String, Object>();
                            temp.put("category", mapping.get("category"));
                            temp.put("fromrule", mapping.get("fromrule"));
                            temp.put("fromuserid", userid);
                            temp.put("fromdeptid", deptid);
                            temp.put("fromroleid", roleid);
                            temp.put("frompostid", postid);

                            frommappings.add(temp);
                        }
                    }
                }
            }
        }

        // 解析to规则
        {
            String[] userids = mapping.get("touserids").split(",");
            String[] deptids = mapping.get("todeptids").split(",");
            String[] roleids = mapping.get("toroleids").split(",");
            String[] postids = mapping.get("topostids").split(",");

            for(String userid : userids) {

                for(String deptid : deptids) {

                    for(String roleid : roleids) {

                        for(String postid : postids) {

                            Map<String, Object> temp = new HashMap<String, Object>();
                            temp.put("torule", mapping.get("torule"));
                            temp.put("touserid", userid);
                            temp.put("todeptid", deptid);
                            temp.put("toroleid", roleid);
                            temp.put("topostid", postid);

                            tomappings.add(temp);
                        }
                    }
                }
            }
        }

        // from 关联 to
        {

            for(Map<String, Object> from : frommappings) {

                for(Map<String, Object> to : tomappings) {

                    from.putAll(to);
                    AuthMapping temp = CommonUtils.mapToObject(new HashMap<String, Object>(from), AuthMapping.class);
                    list.add(temp);
                }
            }
        }
        return list;
    }

    @Override
    public List<AuthMapping> selectAuthMapping(String definitionkey, String definitionid, String taskkey, String mappingtype) throws Exception {

        if(StringUtils.isEmpty(definitionid)) {

            Definition definition = getDefinitionByKey(definitionkey, null, "1");
            definitionid = definition.getDefinitionid();
        }

        return mappingMapper.selectMappingByTaskkey(definitionkey, definitionid, taskkey, mappingtype);
    }

    @Override
    public List<Map> generateSvg() throws Exception {

        List<Definition> definitions = definitionMapper.getAllDefinitionVersions();
        List<Map> results = new ArrayList<Map>();

        for(Definition definition : definitions) {

            Map result = new HashMap();

            Source source = sourceMapper.selectSource(definition.getDefinitionid(), "svg");
            if(source == null) {

                byte[] bytes = repositoryService.getModelEditorSourceExtra(definition.getModelid());
                source = new Source();
                source.setDefinitionid(definition.getDefinitionid());
                source.setType("svg");
                source.setBytes(bytes);

                int ret = 9;

                try {
                    ret = sourceMapper.insertSource(source);
                } catch(Exception e) {

                }

                result.put("definition", definition);
                result.put("result", String.valueOf(ret));

            } else {

                result.put("definition", definition);
                result.put("result", "0");
            }

            results.add(result);
        }

        return results;
    }

	@Override
	public List<Map> selectHanderItems(String url, String handlertype) throws Exception {
		return handlerMapper.selectHanderItemsByUrl(url, handlertype);
	}

    /**
     *
     * @param activity
     * @return
     * @author limin
     * @date Apr 26, 2016
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
            if(!list.contains(ai)) {
				list.add(ai);
			}
        }

        map.put("next", list);
        map.put("size", String.valueOf(list.size()));
        return map;
    }

    @Override
    public String getFirstTaskkeyOfDefinition(String definitionkey) throws Exception {

        Definition definition = getDefinitionByKey(definitionkey, null, "1");
        ProcessDefinitionEntity entity = (ProcessDefinitionEntity)((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(definition.getDefinitionid());

        ActivityImpl startActivity = null;

        List<ActivityImpl> activityList = entity.getActivities();
        for(ActivityImpl activityImpl: activityList){

            ActivityBehavior activityBehavior = activityImpl.getActivityBehavior();
            if(activityBehavior instanceof NoneStartEventActivityBehavior) {
                startActivity = activityImpl;
                break;
            }
        }

        List<ActivityImpl> taskList = getActivitiNodeList(startActivity, new ArrayList(), new ArrayList());
		Collections.reverse(taskList);

        return taskList.get(0).getId();
    }

    @Override
    public List getAllDefinitionVersions() throws Exception {
        return definitionMapper.getAllDefinitionVersions();
    }

	/**
	 * 新增默认的会签规则
	 *
	 * @param definition
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Jun 7, 2017
	 */
    private int fillDefinitionSignTask(Definition definition) throws Exception {

    	int count = 0;
		List<Map<String, Object>> definitionSignList = getSignTaskList(definition.getUnkey());
		for(Map<String, Object> definitionSignMap : definitionSignList) {

			if(getDefinitionSignByKey(definition.getDefinitionid(), (String) definitionSignMap.get("taskkey")) == null) {

				DefinitionSign definitionSign = new DefinitionSign();
				definitionSign.setDefinitionkey(definition.getUnkey());
				definitionSign.setDefinitionid(definition.getDefinitionid());
				definitionSign.setTaskkey((String) definitionSignMap.get("taskkey"));
				definitionSign.setTaskname((String) definitionSignMap.get("taskname"));
				definitionSign.setCounttype("1");
				definitionSign.setDecisiontype("1");
				definitionSign.setVotetype("1");
				definitionSign.setVotenum(1);
				boolean flag = addDefinitionSign(definitionSign);
				if (flag) {
					count ++;
				}
			}
		}

		return count;
	}

	/**
	 * 新增默认的会签规则
	 *
	 * @param definition
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Jun 7, 2017
	 */
	private int fillDefinitionTask(Definition definition) throws Exception {

		int count = 0;
		SysUser user = getSysUser();
		List<Map<String, Object>> definitionTaskList = getUserTaskList(definition.getUnkey(), definition.getDefinitionid());
//		for(Map<String, Object> definitionTaskMap : definitionTaskList) {
//
//			DefinitionTask definitionTask = getDefinitionTask(definition.getUnkey(), definition.getDefinitionid(), (String) definitionTaskMap.get("taskkey"));
//
//			if(definitionTask == null) {
//
//				definitionTask = new DefinitionTask();
//				definitionTask.setDefinitionkey(definition.getUnkey());
//				definitionTask.setDefinitionid(definition.getDefinitionid());
//				definitionTask.setTaskkey((String) definitionTaskMap.get("taskkey"));
//				definitionTask.setTaskname((String) definitionTaskMap.get("taskname"));
//				definitionTask.setAdduserid(user.getUserid());
//				definitionTask.setAddusername(user.getName());
//				definitionTask.setAdddeptid(user.getDepartmentid());
//				definitionTask.setAdddeptname(user.getDepartmentname());
//				boolean flag = this.addDefinitionTask(definitionTask, false);
//				if (flag) {
//					count ++;
//				}
//			}
//		}

		return count;
	}

	@Override
	public List<ActivityImpl> getActivitiNodeList(ActivityImpl startActivity, List<ActivityImpl> srcList, List<String> taskkeys) throws Exception {

		for(PvmTransition transition : startActivity.getOutgoingTransitions()) {
			PvmActivity destination = transition.getDestination();
			if(!srcList.contains(destination)) {
				if(!taskkeys.contains(transition.getDestination().getId())) {
					taskkeys.add(transition.getDestination().getId());
					getActivitiNodeList((ActivityImpl) transition.getDestination(), srcList, taskkeys);
				}
				if(srcList.contains(destination)) {
					srcList.remove(destination);
				}
				if((((ActivityImpl) destination).getActivityBehavior() instanceof UserTaskActivityBehavior
						|| ((ActivityImpl) destination).getActivityBehavior() instanceof ParallelMultiInstanceBehavior)) {
					srcList.add((ActivityImpl)destination);
				}
			}
		}

		return srcList;
	}

	@Override
	public boolean isSignTask(String definitionid, String taskkey) throws Exception {
		ProcessDefinitionEntity entity = (ProcessDefinitionEntity)((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(definitionid);
		for(ActivityImpl activity : entity.getActivities()) {
			if(activity.getId().equals(taskkey)) {
				return activity.getActivityBehavior() instanceof ParallelMultiInstanceBehavior;
			}
		}
		return false;
	}

	@Override
	public Class getDefinitionTaskType(String definitionid, String taskkey) throws Exception {
		return ((ActivityImpl) (((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(definitionid).findActivity(taskkey))).getActivityBehavior().getClass();
	}
}

