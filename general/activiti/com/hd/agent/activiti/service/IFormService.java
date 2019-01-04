/**
 * @(#)IFormService.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 29, 2013 zhengziyong 创建版本
 */
package com.hd.agent.activiti.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.activiti.model.Form;
import com.hd.agent.activiti.model.FormItem;
import com.hd.agent.activiti.model.FormItemRule;
import com.hd.agent.activiti.model.FormType;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author zhengziyong
 */
public interface IFormService {

	/**
	 * 添加表单分类
	 * @param type
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 29, 2013
	 */
	public boolean addFormType(FormType type) throws Exception;
	
	/**
	 * 修改表单分类
	 * @param type
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 29, 2013
	 */
	public boolean updateFormType(FormType type) throws Exception;
	
	/**
	 * 获取表单分类列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 29, 2013
	 */
	public List getFormTypeList() throws Exception;
	
	/**
	 * 获取表单分类信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 29, 2013
	 */
	public FormType getFormType(String id) throws Exception;
//
//	/**
//	 * 通过key获取表单分类信息
//	 * @param key
//	 * @return
//	 * @throws Exception
//	 * @author zhengziyong
//	 * @date Sep 29, 2013
//	 */
//	public FormType getFormTypeByKey(String key) throws Exception;
	
	/**
	 * 删除表单分类
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 29, 2013
	 */
	public boolean deleteFormType(String id) throws Exception;
	
	/**
	 * 获取表单列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 29, 2013
	 */
	public PageData getFormData(PageMap pageMap) throws Exception;
	
	/**
	 * 通过key获取表单信息
	 * @param key
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 29, 2013
	 */
	public Form getFormByKey(String key) throws Exception;
	
	/**
	 * 添加表单信息
	 * @param form
     * @param list
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 29, 2013
	 */
	public boolean addForm(Form form, List<FormItem> list) throws Exception;
	
	/**
	 * 删除表单信息
	 * @param id
     * @param unkey
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 30, 2013
	 */
	public boolean deleteForm(String id, String unkey) throws Exception;

    /**
     * 初始化节点的Form规则
     * @param definitionkey
     * @param taskkey
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 9, 2015
     */
    public boolean initFormRule(String definitionkey, String taskkey) throws Exception;

    /**
     * 查询流程的Form规则
     * @param definitionkey
     * @param definitionid
     * @param taskkey
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 9, 2015
     */
    public List<FormItemRule> selectFormRuleList(String definitionkey, String definitionid, String taskkey) throws Exception;

    /**
     * 设定项目规则
     * @param map
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 10, 2015
     */
    public boolean setFormItemRule(Map map) throws Exception;

    /**
     * 获取表单项目list
     * @param formkey
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 11, 2015
     */
    public List<FormItem> selectFormItemList(String formkey) throws Exception;

    /**
     * check 在线表单是否被流程引用
     * @param formkey
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 30, 2015
     */
    public int isFormReferencedByDefinition(String formkey) throws Exception;

    /**
     * 更新手机表单html代码
     * @param form
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 26, 2015
     */
    public int savePhoneHtml(Form form) throws Exception;
}

