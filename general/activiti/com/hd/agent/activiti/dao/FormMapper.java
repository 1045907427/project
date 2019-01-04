package com.hd.agent.activiti.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.activiti.model.Form;
import com.hd.agent.common.util.PageMap;

public interface FormMapper {
	
	public Form getFormByKey(String key);
	
	public int getCountByKey(String key);
	
	public List getFormList(PageMap pageMap);
	
	public int getFormCount(PageMap pageMap);
	
	public int deleteForm(String id);
	
	public int addForm(Form form);
	
	public int updateForm(Form form);
	
	public int updateFormByKey(Form form);
	
	//以下方法为表单调用方法
	/**
	 * 获取人员列表，id为部门编号
	 */
	public List getPersonnelList(@Param("id")String id);
}