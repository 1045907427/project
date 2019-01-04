/**
 * @(#)Definition.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 24, 2013 zhengziyong 创建版本
 */
package com.hd.agent.activiti.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.activiti.model.*;
import com.hd.agent.common.annotation.UserOperateLog;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.SpringContextUtils;

/**
 * 
 * 
 * @author zhengziyong
 */
public class DefinitionAction extends BaseAction {
	
	private DefinitionType definitionType;
	private DefinitionTask definitionTask;

    private KeywordRule keywordrule;
	
	public DefinitionType getDefinitionType() {
		return definitionType;
	}

	public void setDefinitionType(DefinitionType definitionType) {
		this.definitionType = definitionType;
	}

	public DefinitionTask getDefinitionTask() {
		return definitionTask;
	}

	public void setDefinitionTask(DefinitionTask definitionTask) {
		this.definitionTask = definitionTask;
	}

    public KeywordRule getKeywordrule() {
        return keywordrule;
    }

    public void setKeywordrule(KeywordRule keywordrule) {
        this.keywordrule = keywordrule;
    }

    /**
	 * 流程定义页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 24, 2013
	 */
	public String definitionPage() throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 获取流程分类树状数据
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 24, 2013
	 */
	public String getDefinitionTypeTree() throws Exception{
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		List<DefinitionType> list = definitionService.getDefinitionTypeList();
		Map<String, String> first = new HashMap<String, String>();
		first.put("id", "");
		first.put("name", "流程分类");
		first.put("key", "");
		first.put("open", "true");
		result.add(first);
		for(DefinitionType type: list){
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", type.getId());
			map.put("pid", type.getPid() == null?"":type.getPid());
			map.put("name", type.getName());
			map.put("key", type.getUnkey());
			result.add(map);
		}
		addJSONArray(result);
		return SUCCESS;
	}
	
	/**
	 * 获取流程分类列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 26, 2013
	 */
	public String getDefinitionTypeList() throws Exception{
		List<DefinitionType> list = definitionService.getDefinitionTypeList();
		addJSONArray(list);
		return SUCCESS;
	}
	
	/**
	 * 流程分类增加页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 24, 2013
	 */
	public String definitionTypeAddPage() throws Exception{
		String pid = request.getParameter("pid");
		DefinitionType type = definitionService.getDefinitionType(pid);
		if(type != null){
			request.setAttribute("name", type.getName());
		}
		else{
			request.setAttribute("name", "流程分类");
		}
		request.setAttribute("pid", pid);
		return SUCCESS;
	}
	
	/**
	 * 增加流程分类
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 24, 2013
	 */
	public String definitionTypeAdd() throws Exception{
		SysUser sysUser = getSysUser();
		definitionType.setAdddeptid(sysUser.getDepartmentid());
		definitionType.setAdddeptname(sysUser.getDepartmentname());
		definitionType.setAdduserid(sysUser.getUserid());
		definitionType.setAddusername(sysUser.getName());
		Map result = new HashMap();
		if(definitionService.addDefinitionType(definitionType)){
			result.put("flag", true);
		}
		else{
			result.put("flag", false);
		}
		result.put("type", "add");
		Map node = new HashMap();
		node.put("id", definitionType.getId());
		node.put("pid", definitionType.getPid());
		node.put("name", definitionType.getName());
		node.put("key", definitionType.getUnkey());
		result.put("node", node);
		addJSONObject(result);
		return SUCCESS;
	}
	
	/**
	 * 流程分类修改页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 24, 2013
	 */
	public String definitionTypeEditPage() throws Exception{
		String id = request.getParameter("id");
		DefinitionType type = definitionService.getDefinitionType(id);
		if(type != null){
			DefinitionType type2 = definitionService.getDefinitionType(type.getPid());
			if(type2 != null){
				request.setAttribute("name", type2.getName());
			}
			else{
				request.setAttribute("name", "流程分类");
			}
		}
		request.setAttribute("type", type);
		return SUCCESS;
	}
	
	/**
	 * 修改流程分类
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 24, 2013
	 */
	public String definitionTypeEdit() throws Exception{
		Map result = new HashMap();
		if(definitionService.updateDefinitionType(definitionType)){
			result.put("flag", true);
		}
		else{
			result.put("flag", false);
		}
		result.put("id", definitionType.getId());
		result.put("name", definitionType.getName());
		result.put("type", "edit");
		addJSONObject(result);
		return SUCCESS;
	}
	
	/**
	 * 删除流程分类
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 24, 2013
	 */
	public String deleteDefinitionType() throws Exception{
		String id = request.getParameter("id");
		if(definitionService.deleteDefinitionType(id)){
			addJSONObject("flag", true);
		}
		else{
			addJSONObject("flag", false);
		}
		return SUCCESS;
	}
	
	/**
	 * 流程定义列表与设置页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 28, 2013
	 */
	public String definitionPage2() throws Exception{
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		return SUCCESS;
	}
	
	/**
	 * 获取流程定义列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 28, 2013
	 */
	public String getDefinitionList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		if(!map.containsKey("page")) {
			map.put("nopage", true);
		}
		pageMap.setCondition(map);
		PageData pageData = definitionService.getDefinitionData(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 创建新模型
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 25, 2013
	 */
	public String createNewModel() throws Exception{
		addJSONObject("modelId", definitionService.createNewModel());
		return SUCCESS;
	}
	
	/**
	 * 模型设计页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 25, 2013
	 */
	public String modelDesignPage() throws Exception{
		String id = request.getParameter("modelId");
		request.setAttribute("id", id);
		return SUCCESS;
	}
	
	/**
	 * 流程布署
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 25, 2013
	 */
	public String deployDefinition() throws Exception{
		String definitionkey = request.getParameter("definitionkey");
		addJSONObject("flag", definitionService.deploy(definitionkey));
		return SUCCESS;
	}
	
	/**
	 * 流程定义管理设置页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 25, 2013
	 */
	public String definitionSettingPage() throws Exception{
		String definitionkey = request.getParameter("definitionkey");
		Definition definition = definitionService.getDefinitionByKey(definitionkey, null, "1");
		List list = definitionService.getDefinitionTypeList();
		request.setAttribute("typeList", list);
		request.setAttribute("definition", definition);
		return SUCCESS;
	}
	
	public String setDefinitionType() throws Exception{
		String type = request.getParameter("type");
		String definitionkey = request.getParameter("definitionkey");
		Definition definition = new Definition();
		definition.setType(type);
		definition.setUnkey(definitionkey);
		addJSONObject("flag", definitionService.updateDefinitionByKey(definition));
		return SUCCESS;
	}
	
	/**
	 * 流程节点用户设置(含列表)
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 26, 2013
	 */
	public String definitionUserSettingPage() throws Exception{
		
		// 节点
		String definitionkey = request.getParameter("definitionkey");
		List list = definitionService.getUserTaskList(definitionkey, null);
		request.setAttribute("key", definitionkey);
		request.setAttribute("list", list);
		return SUCCESS;
	}
	
	/**
	 * 流程节点用户设置（含设置项）
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 27, 2013
	 */
	public String definitionUserSettingPage2() throws Exception{

		String definitionkey = request.getParameter("definitionkey");
		String taskkey = request.getParameter("taskkey");
		
		Map param = new HashMap();
		param.put("definitionkey", definitionkey);
		param.put("taskkey", taskkey);
		param.put("type", "aud");
		
		List<AuthRule> ruleList = definitionService.selectAuthRule(param);
		
		List<String> useridList = new ArrayList<String>();
		List<String> deptidList = new ArrayList<String>();
		List<String> roleidList = new ArrayList<String>();
		List<String> postidList = new ArrayList<String>();
		String rule = "";
		for(AuthRule authRule : ruleList) {
			
			rule = authRule.getRule();
			String userid = (String) authRule.getUserid();
			String deptid = (String) authRule.getDeptid();
			String roleid = (String) authRule.getRoleid();
			String postid = (String) authRule.getPostid();
			
			if(StringUtils.isNotEmpty(userid)) {
				useridList.add(userid);
			}
			
			if(StringUtils.isNotEmpty(deptid)) {
				deptidList.add(deptid);
			}
			
			if(StringUtils.isNotEmpty(roleid)) {
				roleidList.add(roleid);
			}
			
			if(StringUtils.isNotEmpty(postid)) {
				postidList.add(postid);
			}
		}
		
		request.setAttribute("rule", rule);
		request.setAttribute("userid", useridList.toString().substring(1, useridList.toString().length() - 1).replaceAll(" ", ""));
		request.setAttribute("deptid", deptidList.toString().substring(1, deptidList.toString().length() - 1).replaceAll(" ", ""));
		request.setAttribute("roleid", roleidList.toString().substring(1, roleidList.toString().length() - 1).replaceAll(" ", ""));
		request.setAttribute("postid", postidList.toString().substring(1, postidList.toString().length() - 1).replaceAll(" ", ""));

		DefinitionTask definitionTask = definitionService.getDefinitionTaskByKey(definitionkey, null, taskkey);
		request.setAttribute("task", definitionTask);
		return SUCCESS;
	}
	
	/**
	 * 流程节点用户设置（含设置项）
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 27, 2013
	 */
	public String definitionUserSettingPage3() throws Exception{

		return SUCCESS;
	}
	
	/**
	 * 获取流程的权限设定
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-12-10
	 */
	public String getMultiAuthruleSetting() throws Exception {

		String definitionkey = request.getParameter("definitionkey");
		String taskkey = CommonUtils.emptyToNull(request.getParameter("taskkey"));
		String type = request.getParameter("type");
		
		//----------------------------------------------------------------------
		// 添加对多种规则设定的支持↓↓↓↓↓↓↓↓↓↓
		//----------------------------------------------------------------------
        Definition definition = definitionService.getDefinitionByKey(definitionkey, null, "1");

		Map param = new HashMap();
		param.put("definitionkey", definitionkey);
        param.put("definitionid", definition.getDefinitionid());
		param.put("taskkey", taskkey);
		param.put("type", type);
		List<AuthRule> arList = definitionService.selectAuthRule(param);
		
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

//			((Map) result.get("r" + ar.getRule())).put("rule", ar.getRule());
//			((List<String>)(((Map) result.get("r" + ar.getRule())).get("userid"))).add(ar.getUserid());
//			((List<String>)(((Map) result.get("r" + ar.getRule())).get("deptid"))).add(ar.getDeptid());
//			((List<String>)(((Map) result.get("r" + ar.getRule())).get("roleid"))).add(ar.getRoleid());
//			((List<String>)(((Map) result.get("r" + ar.getRule())).get("postid"))).add(ar.getPostid());
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

		addJSONObject(result);
		return SUCCESS;
		//----------------------------------------------------------------------
		// 添加对多种规则设定的支持↑↑↑↑↑↑↑↑↑↑
		//----------------------------------------------------------------------
	}
	
	/**
	 * 流程节点表单设置
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 27, 2013
	 */
	public String definitionFormSettingPage() throws Exception{
		String definitionkey = request.getParameter("definitionkey");
		Definition definition = definitionService.getDefinitionByKey(definitionkey, null, "1");
		List list = definitionService.getUserTaskList(definitionkey, null);
		request.setAttribute("key", definitionkey);
		request.setAttribute("list", list);
		request.setAttribute("definition", definition);
		return SUCCESS;
	}
	
	/**
	 * 流程定义历史版本页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 26, 2013
	 */
	public String definitionHistoryListPage() throws Exception{
		String definitionkey = request.getParameter("definitionkey");
		request.setAttribute("definitionkey", definitionkey);
		return SUCCESS;
	}
	
	/**
	 * 获取流程定义历史版本
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 26, 2013
	 */
	public String getDefinitionHistoryList() throws Exception{
		String definitionkey = request.getParameter("definitionkey");
		Definition now = definitionService.getDefinitionByKey(definitionkey, null, "1");
		List<ProcessDefinition> definitionList = definitionService.getDefinitionHistoryList(definitionkey);
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for(ProcessDefinition processDefinition: definitionList){

            String definitionid = processDefinition.getId();

            Definition definition = definitionService.getDefinitionByKey(definitionkey, definitionid, null);

            if(definition == null) {

                continue;
            }

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", processDefinition.getId());
			map.put("key", processDefinition.getKey());
			map.put("name", processDefinition.getName());
			map.put("version", processDefinition.getVersion());
			map.put("deploymentId", processDefinition.getDeploymentId());
			map.put("description", processDefinition.getDescription());
			if(processDefinition.getId().equals(now.getDefinitionid())){
				map.put("current", true);
			}
			else{
				map.put("current", false);
			}
			result.add(map);
		}
		addJSONArray(result);
		return SUCCESS;
	}
	
	/**
	 * 启用新版本为当前版本
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 26, 2013
	 */
	public String enableDefinition() throws Exception{
		String prodefid = request.getParameter("prodefid"); //activiti中的布署的流程定义编号（如test:1:1110）
		addJSONObject("flag", definitionService.enableDefinition(prodefid));
		return SUCCESS;
	}
	
	/**
	 * 获取流程图文件名
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 25, 2013
	 */
	public String getDefinitionDiagram() throws Exception{
        String definitionkey = request.getParameter("definitionkey");
        String definitionid = request.getParameter("definitionid");
		Definition definition = definitionService.getDefinitionByKey(definitionkey, definitionid, StringUtils.isEmpty(definitionid) ? "1" : null);
		String fileName = definition.getDefinitionid().replace(":", "_")+ ".png";
		String path = request.getRealPath(File.separator + "activiti" + File.separator + "diagram" + File.separator);
		File file = new File(path, fileName);
		if(!file.exists()){
			InputStream is = definitionService.getDefinitionDiagram(definitionkey, StringUtils.isNotEmpty(definitionid) ? definitionid : definition.getDefinitionid());
            FileOutputStream outStream = null;
            try {
                outStream = new FileOutputStream(file);
                byte[] b = new byte[1024];
                int len = -1;
                while ((len = is.read(b)) != -1) {
                    outStream.write(b, 0, len);
                }
            } finally {

                if(is != null) {
                    is.close();
                }
                if(outStream != null) {
                    outStream.close();
                }
            }
        }
		
		String modelId = definition.getModelid();
		byte[] svg = definitionService.getDefinitionSvg(definitionid, modelId);
		
		Map map = new HashMap();
		map.put("path", fileName);
		map.put("svg", CommonUtils.bytes2String(svg));
		
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 添加流程节点用户
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 28, 2013
	 */
	public String addDefinitionUser() throws Exception{
		
		DefinitionTask definitionTask = definitionService.getDefinitionTask(this.definitionTask.getDefinitionkey(), null, this.definitionTask.getTaskkey());
		
		definitionTask.setTaskname(this.definitionTask.getTaskname());
		definitionTask.setRule(this.definitionTask.getRule());
		definitionTask.setUser(this.definitionTask.getUser());
		definitionTask.setDept(this.definitionTask.getDept());
		definitionTask.setRole(this.definitionTask.getRole());
		definitionTask.setPost(this.definitionTask.getPost());
		definitionTask.setRulename(this.definitionTask.getRulename());
		definitionTask.setRuledetail(this.definitionTask.getRuledetail());
		
		addJSONObject("flag", definitionService.addDefinitionTask(definitionTask, true));
		return SUCCESS;
	}
	
	/**
	 * 添加流程节点表单
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 28, 2013
	 */
	public String addDefinitionForm() throws Exception{
		List<DefinitionTask> taskList = new ArrayList<DefinitionTask>();
		Map map = request.getParameterMap();
		String[] definitionkeys = (String[])map.get("definitionkey");
		String[] formTypes = (String[])map.get("formtype");
		String[] gBusinessUrls = (String[])map.get("gBusinessUrl");
		String[] endListeners = (String[])map.get("endListener");
		String[] gFormKeys = (String[])map.get("gFormkey");
		String[] gRemindTypes = (String[])map.get("remindtype");
        String[] gEndRemindTypes = (String[])map.get("endremindtype");
        String[] gEndRemindApplier = (String[])map.get("endremindapplier");

        if("formkey".equals(formTypes[0])) {

            String formkey = gFormKeys[0];
            Form form = formService.getFormByKey(formkey);
            if(form == null) {

                Map result = new HashMap();
                result.put("flag", false);
                result.put("message", "选择的表单不存在，无法保存！");
                addJSONObject(result);
                return SUCCESS;
            }
        }

        Definition d = definitionService.getDefinitionByKey(definitionkeys[0], null, "1");

		Definition definition = new Definition();
		//definitionService.getDefinitionByKey(keys[0]);
		//if(keys != null && keys.length > 0){
			definition.setUnkey(definitionkeys[0]);
        definition.setDefinitionid(d.getDefinitionid());    //
		//}
		//if(gBusinessUrls != null && gBusinessUrls.length > 0){
			definition.setBusinessurl(gBusinessUrls[0]);
		//}
		//if(endListeners != null && endListeners.length > 0){
			definition.setEndlistener(endListeners[0]);
		//}
		//if(gRemindTypes != null && gRemindTypes.length > 0){
			definition.setRemindtype(arrayToString(gRemindTypes, ","));
		//}
		//if(formTypes != null && formTypes.length > 0){
			definition.setFormtype(formTypes[0]);
		//}
		//if(gFormKeys != null && gFormKeys.length > 0){
			definition.setFormkey(gFormKeys[0]);
		//}
		//----------------------------------------------------------------------
		// 流程结束通知设定↓↓↓↓↓↓↓↓↓↓↑↑↑↑↑↑↑↑↑↑
		//----------------------------------------------------------------------
		definition.setEndremindtype(arrayToString(gEndRemindTypes, ","));
		//----------------------------------------------------------------------
		// 流程结束通知设定↑↑↑↑↑↑↑↑↑↑
		//----------------------------------------------------------------------
		String[] taskkeys = (String[])map.get("taskkey");
		String[] taskNames = (String[])map.get("taskName");
		String[] businessUrls = (String[])map.get("businessUrl"); 
		String[] formKeys = (String[])map.get("formkey");
        String[] definitionids = (String[])map.get("definitionid");
		if(taskkeys != null && taskkeys.length > 0){
			for(int i = 0; i< taskkeys.length; i++){
				String taskkey = taskkeys[i];
				//DefinitionTask task = new DefinitionTask();
				DefinitionTask task = definitionService.getDefinitionTaskByKey(definitionkeys[0], null, taskkey);
				if(task == null) {
					task = new DefinitionTask();
				}
				task.setTaskkey(taskkey);
				task.setTaskname(taskNames[i]);
				String[] remindTypes  = (String[])map.get("remindtype_"+ taskkey);
				//if(remindTypes != null){
					task.setRemindtype(arrayToString(remindTypes, ","));
				//}
                String endremindapplier  = CommonUtils.nullToEmpty(request.getParameter("endremindapplier_"+ taskkey));
                task.setEndremindapplier(endremindapplier);
				task.setBusinessurl(businessUrls[i]);
				task.setFormkey(formKeys[i]);
                task.setDefinitionkey(definition.getUnkey());
                task.setDefinitionid(definitionids[i]);
				taskList.add(task);
			}
		}
		boolean flag = definitionService.addDefinitionTaskForm(definition, taskList);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 会签设置页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 28, 2013
	 */
	public String signTaskSettingPage() throws Exception{
		String definitionkey = request.getParameter("definitionkey");
		List list = definitionService.getSignTaskList(definitionkey);
		request.setAttribute("definitionkey", definitionkey);
		request.setAttribute("list", list);
		return SUCCESS;
	}
	
	/**
	 * 添加会签规则
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 28, 2013
	 */
	public String addSignRule() throws Exception{
		List<DefinitionSign> signList = new ArrayList<DefinitionSign>();
		Map map = request.getParameterMap();
		String[] definitionkey = (String[]) map.get("definitionkey");
		String definitionid = definitionService.getDefinitionByKey(definitionkey[0], null, "1").getDefinitionid();
		String[] taskkeys = (String[])map.get("taskkey");
		String[] taskNames = (String[])map.get("taskName"); 
		String[] countTypes = (String[])map.get("counttype");
		String[] decisionTypes = (String[])map.get("decisiontype");
		String[] voteTypes = (String[])map.get("votetype");
		String[] voteNums = (String[])map.get("votenum");
		if(taskkeys != null && taskkeys.length > 0){
			for(int i = 0; i< taskkeys.length; i++){
				DefinitionSign sign = new DefinitionSign();
				String taskkey = taskkeys[i];
				sign.setDefinitionkey(definitionkey[0]);
				sign.setDefinitionid(definitionid);
				sign.setTaskkey(taskkey);
				String[] users = (String[])map.get("user_"+ taskkey);
				if(users != null && users.length > 0){
					sign.setUser(users[0]);
				}
				String[] names = (String[])map.get("name_"+ taskkey);
				if(names != null && names.length > 0){
					sign.setName(names[0]);
				}
				sign.setTaskname(taskNames[i]);
				sign.setCounttype(countTypes[0]);
				sign.setDecisiontype(decisionTypes[i]);
				sign.setVotetype(voteTypes[i]);
				sign.setVotenum(Integer.valueOf(voteNums[i]));
				signList.add(sign);
			}
		}
		addJSONObject("flag", definitionService.addDefinitionSign(signList));
		return SUCCESS;
	}
	
	/**
	 * 获取流程定义与分类的树状结构数据
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 30, 2013
	 */
	public String getDefinitionTree() throws Exception{
		addJSONArray(definitionService.getDefinitionTree("aud"));
		return SUCCESS;
	}
	
	/**
	 * 数组转为字符串
	 * @param arrs
     * @param split
	 * @return
	 * @author zhengziyong 
	 * @date Sep 28, 2013
	 */
	private String arrayToString(String[] arrs, String split){
		String result = "";
		if(arrs == null) {
			return result;
		}
		for(String arr: arrs){
			result += arr + split;
		}
		if(result.endsWith(split)){
			result = result.substring(0, (result.length() - 1));
		}
		return result;
	}
	
	/**
	 * 设置“能否设置人员”
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-7-23
	 */
	public String setCanassign() throws Exception {
		
		String taskkey = request.getParameter("taskkey");
		String definitionkey = request.getParameter("definitionkey");
		String canassign = request.getParameter("canassign");
		
		DefinitionTask definitionTask = new DefinitionTask();
		definitionTask.setDefinitionkey(definitionkey);
		definitionTask.setTaskkey(taskkey);
		definitionTask.setCanassign(canassign);
		
		int ret = definitionService.setCanassign(definitionTask);
		
		addJSONObject("flag", ret > 0);
		
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addAuthRule() throws Exception {
		
		String definitionkey = request.getParameter("definitionkey");
		String type = request.getParameter("type");
		String taskkey = CommonUtils.emptyToNull(request.getParameter("taskkey"));
		String rules = request.getParameter("rule");

		Definition definition = definitionService.getDefinitionByKey(definitionkey, null, "1");

//		// 会签
//		if("sign".equals(type)) {
//			DefinitionSign definitionSign = definitionService.getDefinitionSignByKey(definitionkey, taskkey);
//			if (definitionSign == null) {
//				definitionSign = new DefinitionSign();
//				definitionSign.setDefinitionkey(definitionkey);
//				definitionSign.setDefinitionid(definition.getDefinitionid());
//				definitionSign.setTaskkey(taskkey);
//				definitionSign.setTaskname(definitionService.getDefinitionTask(definitionkey, definition.getDefinitionid(), taskkey).getTaskname());
//				definitionSign.setCounttype("1");
//				definitionSign.setDecisiontype("1");
//				definitionSign.setVotetype("1");
//				definitionSign.setVotenum(1);
//				definitionService.addDefinitionSign(definitionSign);
//			}
//		}

		Map map = new HashMap();
		if(StringUtils.isEmpty(rules)) {
			
			map.put("flag", false);
			addJSONObject(map);
			return SUCCESS;
		}

		List<AuthRule> list = new ArrayList<AuthRule>();
		SysUser user = getSysUser();
		
		String[] ruleList = rules.split(",");
		
		for(String rule : ruleList) {
		
			String userid = CommonUtils.nullToEmpty(request.getParameter("userid" + rule));
			String deptid = CommonUtils.nullToEmpty(request.getParameter("deptid" + rule));
			String roleid = CommonUtils.nullToEmpty(request.getParameter("roleid" + rule));
			String postid = CommonUtils.nullToEmpty(request.getParameter("postid" + rule));
			
			switch (Integer.parseInt(rule)) {
			// 指定人员
			case 1: {
				
				String[] userids = userid.split(",");
				for(String str : userids) {
					
					if(StringUtils.isEmpty(str)) {
						continue;
					}
					
					AuthRule auth = new AuthRule();
					auth.setDefinitionkey(definitionkey);
                    auth.setDefinitionid(definition.getDefinitionid());
					auth.setType(type);
					auth.setTaskkey(taskkey);
					auth.setRule(rule);
					auth.setUserid(str);
					auth.setAdduserid(user.getUserid());
					
					list.add(auth);
				}
				
				break;
			}
			case 2:{
				
				String[] roleids = roleid.split(",");
				for(String str : roleids) {
					
					if(StringUtils.isEmpty(str)) {
						continue;
					}
					
					AuthRule auth = new AuthRule();
					auth.setDefinitionkey(definitionkey);
                    auth.setDefinitionid(definition.getDefinitionid());
					auth.setTaskkey(taskkey);
					auth.setType(type);
					auth.setRule(rule);
					auth.setRoleid(str);
					auth.setDeptid("");
					auth.setAdduserid(user.getUserid());
					
					list.add(auth);
				}

				break;
			}
			case 3:{
				
				//String[] deptids = deptids.split(",");
				String[] deptids = {""};
				for(String str : deptids) {
					
					//if(StringUtils.isEmpty(str)) {
					//	continue;
					//}
					
					AuthRule auth = new AuthRule();
					auth.setDefinitionkey(definitionkey);
                    auth.setDefinitionid(definition.getDefinitionid());
					auth.setTaskkey(taskkey);
					auth.setType(type);
					auth.setRule(rule);
					auth.setDeptid(str);
					auth.setAdduserid(user.getUserid());
					
					list.add(auth);
				}

				break;
			}
			/*
			*/
			// 指定角色
			case 4: {
				
				String[] roleids = roleid.split(",");
				for(String str : roleids) {
					
					if(StringUtils.isEmpty(str)) {
						continue;
					}
					
					AuthRule auth = new AuthRule();
					auth.setDefinitionkey(definitionkey);
                    auth.setDefinitionid(definition.getDefinitionid());
					auth.setTaskkey(taskkey);
					auth.setType(type);
					auth.setRule(rule);
					auth.setRoleid(str);
					auth.setAdduserid(user.getUserid());
					
					list.add(auth);
				}
	
				break;
			}
			// 指定部门
			case 5: {
				
				String [] deptids = deptid.split(",");
				for(String str : deptids) {
					
					if(StringUtils.isEmpty(str)) {
						continue;
					}
					
					AuthRule auth = new AuthRule();
					auth.setDefinitionkey(definitionkey);
                    auth.setDefinitionid(definition.getDefinitionid());
					auth.setTaskkey(taskkey);
					auth.setType(type);
					auth.setRule(rule);
					auth.setDeptid(str);
					auth.setAdduserid(user.getUserid());
					
					list.add(auth);
				}
				break;
			}
			// 指定部门与角色
			case 6: {
				
				String [] deptids = deptid.split(",");
				String[] roleids = roleid.split(",");
				for(String str : deptids) {
					
					if(StringUtils.isEmpty(str)) {
						continue;
					}
					
					for(String str2 : roleids) {
						
						if(StringUtils.isEmpty(str2)) {
							continue;
						}
						
						AuthRule auth = new AuthRule();
						auth.setDefinitionkey(definitionkey);
                        auth.setDefinitionid(definition.getDefinitionid());
						auth.setTaskkey(taskkey);
						auth.setType(type);
						auth.setRule(rule);
						auth.setDeptid(str);
						auth.setRoleid(str2);
						auth.setAdduserid(user.getUserid());
						
						list.add(auth);
					}
				}
				break;
			}
			// 指定岗位
			case 7: {
				
				String [] postids = postid.split(",");
				for(String str : postids) {
					
					if(StringUtils.isEmpty(str)) {
						continue;
					}
					
					AuthRule auth = new AuthRule();
					auth.setDefinitionkey(definitionkey);
                    auth.setDefinitionid(definition.getDefinitionid());
					auth.setTaskkey(taskkey);
					auth.setType(type);
					auth.setRule(rule);
					auth.setPostid(str);
					auth.setAdduserid(user.getUserid());
					
					list.add(auth);
				}
				break;
			}
			default:
				break;
			}
		
		}
		
		boolean ret = definitionService.addAuthRule(list);
		
		map.put("flag", ret);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 获取流程对应的权限配置
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-12-3
	 */
	public String getRuleDetailDescription() throws Exception {
		
		String definitionkey = request.getParameter("definitionkey");	// 流程definitionkey
		String taskkey = request.getParameter("taskkey");				// 节点taskkey
		String type = request.getParameter("type");						// 类型 add、aud、del...
		
		Map map = definitionService.getRuleDetailDescription(definitionkey, taskkey, type);
		
		map.put("definitionkey", definitionkey);
		map.put("taskkey", taskkey);
		map.put("type", type);
		
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 获取流程节点对应的“能否指定人员”设置
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-12-4
	 */
	public String getCanassignSetting() throws Exception {
		
		String definitionkey = request.getParameter("definitionkey");	// 流程definitionkey
		String taskkey = request.getParameter("taskkey");				// 节点taskkey
		String type = request.getParameter("type");						// 类型 add、aud、del...
		
		Map param = new HashMap();
		param.put("definitionkey", definitionkey);
		param.put("taskkey", taskkey);
		param.put("type", type);
		param.put("canassign", "1");									// canassign 随便设定
		
		List<AuthRule> ruleList = definitionService.selectAuthRule(param);
		
		Map map = new HashMap();
		map.put("definitionkey", definitionkey);
		map.put("taskkey", taskkey);
		map.put("type", type);
		// 没有规则时，表明未进行过设定，此时“能否指定人员”应该为否
		if(ruleList == null || ruleList.size() == 0) {
			
			map.put("canassign", "0");

		// 
		} else {
			
			AuthRule rule = ruleList.get(0);
			if(StringUtils.isEmpty(rule.getCanassign())) {

				map.put("canassign", "0");
			} else {
				
				map.put("canassign", rule.getCanassign());
			}
		}

		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 流程用户节点Handler查看页面
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-12-31
	 */
	public String definitionHandlerSettingPage() throws Exception {
		
		String definitionkey = request.getParameter("definitionkey");
		List list = definitionService.getUserTaskList(definitionkey, null);
		
		request.setAttribute("list", list);
		return SUCCESS;
	}
	
	/**
	 * 流程用户节点Handler查看页面
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-12-31
	 */
	public String definitionHandlerSettingPage2() throws Exception {
		
		String definitionkey = request.getParameter("definitionkey");
        String definitionid = request.getParameter("definitionid");
		String taskkey = request.getParameter("taskkey");
		String event = request.getParameter("event");

		Definition definition = definitionService.getDefinitionByKey(definitionkey, null, "1");
        if(StringUtils.isEmpty(definitionid)) {
            definitionid = definition.getDefinitionid();
        }

		Map param = new HashMap();
        param.put("definitionkey", definitionkey);
        param.put("definitionid", definitionid);
		param.put("taskkey", taskkey);
		param.put("event", event);
		
		List list = definitionService.selectEventHandlerByTask(param);
		
		if(StringUtils.isNotEmpty(definition.getBusinessurl())) {

			List<Map> items = definitionService.selectHanderItems(definition.getBusinessurl().split("\\?")[0], "1");	// 1: create handler
			request.setAttribute("items", items);
		}

		request.setAttribute("list", list);
		return SUCCESS;
	}
	
	/**
	 * 获取节点handler配置
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-2
	 */
	public String getEventHandlerSetting() throws Exception {
		
		String definitionkey = request.getParameter("definitionkey");
		String taskkey = request.getParameter("taskkey");
		String event = request.getParameter("event");

        Definition definition = definitionService.getDefinitionByKey(definitionkey, null, "1");

		Map param = new HashMap();
		param.put("definitionkey", definitionkey);
        param.put("definitionid", definition.getDefinitionid());
		param.put("taskkey", taskkey);
		param.put("event", event);
		
		List list = definitionService.selectEventHandlerByTask(param);
		
		Map map = new HashMap();
		map.put("definitionkey", definitionkey);
		map.put("taskkey", taskkey);
		map.put("event", event);
		map.put("list", list);
		
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 验证bean id是否有效
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-3
	 */
	public String checkBean() throws Exception {
		
		String bean = request.getParameter("bean");
		boolean flag = true;
		
		try {
			Object o = SpringContextUtils.getBean(bean);
			if(o != null/* && o.getClass().getMethod(arg0, arg1)*/) {
				
			} else {
				
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
		}
		
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 保存bean设置
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-5
	 */
	public String saveBean() throws Exception {
	
		String definitionkey = request.getParameter("definitionkey");
		String taskkey = request.getParameter("taskkey");
		String event = request.getParameter("event");
		String handlers = CommonUtils.nullToEmpty(request.getParameter("handlers"));
		
		if(StringUtils.isEmpty(definitionkey) || StringUtils.isEmpty(taskkey)) {
			
			addJSONObject("flag", false);
		}
		
		List<EventHandler> list = JSONUtils.jsonStrToList(handlers, new EventHandler());;
		for(EventHandler handler : list) {
			
			//handler.setDefinitionkey(definitionkey);
			//handler.setTaskkey(taskkey);
			//handler.setEvent(event);
			//handler.setHandler(str);
			handler.setAdduserid(getSysUser().getUserid());
		}
		
		definitionService.saveEventHandlerForTask(definitionkey, taskkey, event, list);
		
		addJSONObject("flag", true);
		return SUCCESS;
	}
	
	/**
	 * 清空流程节点的人员设定规则（新建工作、审批权限）
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-12
	 */
	public String clearAuthRule() throws Exception {
		
		String definitionkey = request.getParameter("definitionkey");
		String taskkey = CommonUtils.emptyToNull(request.getParameter("taskkey"));
		String type = request.getParameter("type");

		int ret = definitionService.clearAuthRule(definitionkey, taskkey, type);
		
		addJSONObject("flag", true);
		return SUCCESS;
	}

    /**
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 9, 2015
     */
    public String definitionOtherSettingPage() throws Exception {

        String definitionkey = request.getParameter("definitionkey");

        Definition definition = definitionService.getDefinitionByKey(definitionkey, null, "1");
        KeywordRule keywordRule = definitionService.selectKeywordRuleByDefinitionkey(definitionkey, definition.getDefinitionid());

        if(StringUtils.isNotEmpty(definition.getFormkey())) {

            Form form = formService.getFormByKey(definition.getFormkey());
            if(form != null) {
                List<FormItem> items = formService.selectFormItemList(definition.getFormkey());
                request.setAttribute("form", form);
                request.setAttribute("items", items);
            }

        }

        request.setAttribute("definition", definition);
        request.setAttribute("keywordrule", keywordRule);
        return SUCCESS;
    }

    /**
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 10, 2015
     */
    public String saveKeywordRule() throws Exception {

        keywordrule.setAdduserid(getSysUser().getUserid());

        Definition definition = definitionService.getDefinitionByKey(keywordrule.getDefinitionkey(), null, "1");
        keywordrule.setDefinitionid(definition.getDefinitionid());
        int ret = definitionService.insertKeywordRuleByDefinitionkey(keywordrule);

        addJSONObject("flag", ret > 0);
        return SUCCESS;
    }

    /**
     * 删除流程
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 16, 2015
     */
    @UserOperateLog(key="Definition", type=3)
    public String deleteDefinition() throws Exception {

        String modelid = request.getParameter("modelid");
        String unkey = request.getParameter("unkey");

        Definition definition = definitionService.getDefinitionByKey(unkey, null, "1");

        int ret = definitionService.deleteDefinition(modelid, unkey);

        addLog("删除流程定义 标识：" + unkey + " 名称：" + definition.getName(), ret > 0);
        addJSONObject("flag", ret > 0);
        return SUCCESS;
    }

    /**
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 23, 2015
     */
    public String saveAuthMapping() throws Exception {

        String definitionkey = request.getParameter("definitionkey");
        String taskkey = request.getParameter("taskkey");
        String mappingtype = request.getParameter("mappingtype");
        String mapping = request.getParameter("mapping");

        List list = JSONUtils.jsonStrToList(mapping, new HashMap<String, String>());

        int ret = definitionService.saveAuthMapping(definitionkey, taskkey, mappingtype, list);

        addJSONObject("flag", ret > 0);
        return SUCCESS;
    }

    /**
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 23, 2015
     */
    public String selectAuthMapping() throws Exception {

        String definitionkey = request.getParameter("definitionkey");
        String definitionid = request.getParameter("definitionid");
        String taskkey = request.getParameter("taskkey");
        String mappingtype = request.getParameter("mappingtype");
        String mapping = request.getParameter("mapping");

        List list = definitionService.selectAuthMapping(definitionkey, definitionid, taskkey, mappingtype);

        addJSONObject("list", list);
        return SUCCESS;
    }

    /**
     * 生成流程对应的SVG代码
     * @return
     * @throws Exception
     * @author limin
     * @date May 19, 2015
     */
    public String generateSvg() throws Exception {

        List list = definitionService.generateSvg();

        request.setAttribute("result", list);
        return SUCCESS;
    }

	/**
	 * 流程节点会签用户设置（含设置项）
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Jun 7, 2017
	 */
	public String signTaskUserSettingPage() throws Exception{

		return SUCCESS;
	}
}

