package com.hd.agent.activiti.dao;

import java.util.List;

import com.hd.agent.activiti.model.FormType;

public interface FormTypeMapper {
    
	public int addFormType(FormType type);
	
	public int updateFormType(FormType type);
	
	public List getFormTypeList();
	
	public FormType getFormTypeByKey(String key);
	
	public FormType getFormType(String id);
	
	public int deleteFormType(String id);
	
}