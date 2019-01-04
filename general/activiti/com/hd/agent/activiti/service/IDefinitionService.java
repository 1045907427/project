/**
 * @(#)DefinitionService.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 24, 2013 zhengziyong 创建版本
 */
package com.hd.agent.activiti.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.hd.agent.activiti.model.*;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import org.activiti.engine.impl.pvm.process.ActivityImpl;

/**
 * 流程定义Service
 * 
 * @author zhengziyong
 */
public interface IDefinitionService {

	/**
	 * 添加流程分类
	 * @param type
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 24, 2013
	 */
	public boolean addDefinitionType(DefinitionType type) throws Exception;
	
	/**
	 * 获取流程分类列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 24, 2013
	 */
	public List getDefinitionTypeList() throws Exception;
	
	/**
	 * 获取流程分类详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 24, 2013
	 */
	public DefinitionType getDefinitionType(String id) throws Exception;
//
//	/**
//	 * 获取流程分类详细信息
//	 * @param key
//	 * @return
//	 * @throws Exception
//	 * @author zhengziyong
//	 * @date Sep 26, 2013
//	 */
//	public DefinitionType getDefinitionTypeByKey(String key) throws Exception;
//
//	/**
//	 * 是否存在该Key
//	 * @param key
//	 * @return
//	 * @throws Exception
//	 * @author zhengziyong
//	 * @date Sep 26, 2013
//	 */
//	public boolean existsDefinitionTypeKey(String key) throws Exception;
	
	/**
	 * 修改流程分类
	 * @param type
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 24, 2013
	 */
	public boolean updateDefinitionType(DefinitionType type) throws Exception;
	
	/**
	 * 删除流程分类
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 24, 2013
	 */
	public boolean deleteDefinitionType(String id) throws Exception;
	
	/**
	 * 创建新模型，流程设计前先创建模型
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 25, 2013
	 */
	public String createNewModel() throws Exception;
	
	/**
	 * 添加流程定义
	 * @param definition
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 25, 2013
	 */
	public boolean addDefinition(Definition definition) throws Exception;
	
	/**
	 * 更新流程定义
	 * @param definition
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 26, 2013
	 */
	public boolean updateDefinitionByKey(Definition definition) throws Exception;
	
	/**
	 * 获取流程定义
	 * @param definitionkey
     * @param definitionid
     * @param isdeploy
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 25, 2013
	 */
	public Definition getDefinitionByKey(String definitionkey, String definitionid, String isdeploy) throws Exception;
	
	/**
	 * 获取流程定义列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 25, 2013
	 */
	public PageData getDefinitionData(PageMap pageMap) throws Exception;
	
	/**
	 * 布署流程
	 * @param definitionkey 流程定义唯一标识
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 25, 2013
	 */
	public boolean deploy(String definitionkey) throws Exception;
	
	/**
	 * 获取流程图流
	 * @param definitionkey
     * @param definitionid
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 25, 2013
	 */
	public InputStream getDefinitionDiagram(String definitionkey, String definitionid) throws Exception;
	
	/**
	 * 从activiti定义中获取历史布署的流程定义
	 * @param definitionkey
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 26, 2013
	 */
	public List getDefinitionHistoryList(String definitionkey) throws Exception;
	
	/**
	 * 启用新版本为当前版本
	 * @param prodefid activiti中的布署的流程定义编号（如test:1:1110）
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 26, 2013
	 */
	public boolean enableDefinition(String prodefid) throws Exception;
	
	/**
	 * 获取流程所有用户任务节点信息
	 * @param definitionkey
     * @param definitionid
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 26, 2013
	 */
	public List<Map<String, Object>> getUserTaskList(String definitionkey, String definitionid) throws Exception;
	
	/**
	 * 获取流程所有会签任务节点信息
	 * @param definitionkey
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 28, 2013
	 */
	public List<Map<String, Object>> getSignTaskList(String definitionkey) throws Exception;
	
	/**
	 * 添加流程节点信息
	 * @param task
	 * @param rule true时表明设定规则
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 27, 2013
	 */
	public boolean addDefinitionTask(DefinitionTask task, boolean rule) throws Exception;
	
	/**
	 * 添加流程表单和流程节点表单以及提醒设置
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 28, 2013
	 */
	public boolean addDefinitionTaskForm(Definition definition, List<DefinitionTask> tasks) throws Exception;
	
	/**
	 * 获取流程节点信息
	 * @param definitionkey 流程定义标识
     * @param definitionid
	 * @param taskkey 节点标识
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 27, 2013
	 */
	public DefinitionTask getDefinitionTaskByKey(String definitionkey, String definitionid, String taskkey) throws Exception;
	
	/**
	 * 添加流程会签节点规则信息
	 * @param sign
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 28, 2013
	 */
	public boolean addDefinitionSign(DefinitionSign sign) throws Exception; 
	
	/**
	 * 添加多个流程会签节点规则信息
	 * @param signList 
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 28, 2013
	 */
	public boolean addDefinitionSign(List<DefinitionSign> signList) throws Exception;
	
	/**
	 * 获取流程会签节点信息
	 * @param definitionid
	 * @param taskkey
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 6, 2013
	 */
	public DefinitionSign getDefinitionSignByKey(String definitionid, String taskkey) throws Exception;
	
	/**
	 * 获取流程定义与分类的树状结构数据
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 30, 2013
	 */
	public List<Map> getDefinitionTree(String type) throws Exception;
	
	/**
	 * 设置“能否设置人员”
	 * @param definitionTask
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-7-23
	 */
	public int setCanassign(DefinitionTask definitionTask) throws Exception;
	
	/**
	 * 获取流程定义的SVG代码
     * @param definitionid
	 * @param modelId
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-8-30
	 */
	public byte[] getDefinitionSvg(String definitionid, String modelId) throws Exception;
	
	/**
	 * 添加工作流操作规则
	 * @param list
	 * @return
	 */
	public boolean addAuthRule(List<AuthRule> list);
//
//	/**
//	 * 根据type获取指定流程的对应权限配置规则
//	 * @param definitionkey
//	 * @param type
//	 * @return
//	 */
//	public List<String> getRuleByType(String definitionkey, String type);
//
//	/**
//	 * 根据type获取指定流程的对应权限配置详情
//	 * @param definitionkey
//	 * @param type
//	 * @return
//	 */
//	public List<Map> getRuleDetailByType(String definitionkey, String type);
	
	/**
	 * 根据type获取指定流程的对应权限配置详情说明
	 * @param definitionkey
	 * @param taskkey
	 * @param type
	 * @return
	 */
	public Map getRuleDetailDescription(String definitionkey, String taskkey, String type) throws Exception;
	
	/**
	 * 
	 * @param definitionkey
     * @param definitionid
	 * @param taskkey
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-10-27
	 */
	public DefinitionTask getDefinitionTask(String definitionkey, String definitionid, String taskkey) throws Exception;
	
	/**
	 * 获取流程用户节点设定
	 * @return
	 * @author limin 
	 * @date 2014-12-4
	 */
	public List<AuthRule> selectAuthRule(Map map) throws Exception;
	
	/**
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-2
	 */
	public List<EventHandler> selectEventHandlerByTask(Map map) throws Exception;
	
	/**
	 * 清空流程节点的人员设定规则（新建工作、审批权限）
	 * @param definitionkey
	 * @param taskkey
	 * @param event
	 * @param list
	 * @return
	 * @author limin 
	 * @date 2015-1-5
	 */
	public int saveEventHandlerForTask(String definitionkey, String taskkey, String event, List<EventHandler> list) throws Exception;

	/**
	 * 
	 * @param definitionkey
	 * @param taskkey
	 * @param type
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-12
	 */
	public int clearAuthRule(String definitionkey, String taskkey, String type) throws Exception;

    /**
     * check当前流程标识是否已经存在
     * @param unkey
     * @param modelid
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-03-12
     */
    public int isDefinitionExist(String unkey, String modelid) throws Exception;

    /**
     *
     * @param definitionkey
     * @param definitionid
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-04-02
     */
    public KeywordRule selectKeywordRuleByDefinitionkey(String definitionkey, String definitionid) throws Exception;

//    /**
//     *
//     * @param definitionkey
//     * @return
//     * @throws Exception
//     * @author limin
//     * @date 2015-04-02
//     */
//    public int deleteKeywordRuleByDefinitionkey(String definitionkey) throws Exception;

    /**
     *
     * @param keywordRule
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-04-02
     */
    public int insertKeywordRuleByDefinitionkey(KeywordRule keywordRule) throws Exception;

    /**
     *
     * @param modelid
     * @param unkey
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-04-16
     */
    public int deleteDefinition(String modelid, String unkey) throws Exception;

    /**
     *
     * @param definitionkey
     * @param taskkey
     * @param mappingtype
     * @param list
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 23, 2015
     */
    public int saveAuthMapping(String definitionkey, String taskkey, String mappingtype, List<Map<String, String>> list) throws Exception;

    /**
     *
     * @param definitionkey
     * @param definitionid
     * @param taskkey
     * @param mappingtype
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 23, 2015
     */
    public List<AuthMapping> selectAuthMapping(String definitionkey, String definitionid, String taskkey, String mappingtype) throws Exception;

    /**
     *
     * @return
     * @throws Exception
     * @author limin
     * @date May 19, 2015
     */
    public List<Map> generateSvg() throws Exception;

	/**
	 * 获取所有的Handler配置
	 * @param url
	 * @param handlertype
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Apr 19, 2016
     */
	public List<Map> selectHanderItems(String url, String handlertype) throws Exception;

    /**
     * 获取流程的首个节点taskkey
     * @param definitionkey
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 28, 2016
     */
    public String getFirstTaskkeyOfDefinition(String definitionkey) throws Exception;

    /**
     * 获取所有已部署的流程
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 28, 2016
     */
    public List getAllDefinitionVersions() throws Exception;

	/**
	 * 获取指定activiti节点的下一个节点（可能为多个）
	 *
	 * @param startActivity
	 * @param srcList
	 * @param taskkeys
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Jun 8, 2017
	 */
	public List<ActivityImpl> getActivitiNodeList(ActivityImpl startActivity, List<ActivityImpl> srcList, List<String> taskkeys) throws Exception;

	/**
	 * 判断节点是否为会签节点
	 * @param definitionid
	 * @param taskkey
	 * @throws Exception
	 * @author limin
	 * @date Jun 9, 2017
	 */
	public boolean isSignTask(String definitionid, String taskkey) throws Exception;

	/**
	 * 获取流程节点类型
	 *
	 * @param definitionid
	 * @param taskkey
	 * @throws Exception
	 * @author limin
	 * @date Jun 9, 2017
	 */
	public Class getDefinitionTaskType(String definitionid, String taskkey) throws Exception;
}

