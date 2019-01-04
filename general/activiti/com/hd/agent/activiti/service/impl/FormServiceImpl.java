/**
 * @(#)FormServiceImpl.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 29, 2013 zhengziyong 创建版本
 */
package com.hd.agent.activiti.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.activiti.dao.*;
import com.hd.agent.activiti.model.*;
import com.hd.agent.activiti.service.IFormService;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 *
 *
 * @author zhengziyong
 */
public class FormServiceImpl extends BaseServiceImpl implements IFormService {

    private FormTypeMapper formTypeMapper;

    private FormMapper formMapper;

    private FormItemMapper itemMapper;

    private DefinitionMapper definitionMapper;

    private FormItemRuleMapper itemRuleMapper;

    public FormTypeMapper getFormTypeMapper() {
        return formTypeMapper;
    }

    public void setFormTypeMapper(FormTypeMapper formTypeMapper) {
        this.formTypeMapper = formTypeMapper;
    }

    public FormMapper getFormMapper() {
        return formMapper;
    }

    public void setFormMapper(FormMapper formMapper) {
        this.formMapper = formMapper;
    }

    public FormItemMapper getItemMapper() {
        return itemMapper;
    }

    public void setItemMapper(FormItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    public DefinitionMapper getDefinitionMapper() {
        return definitionMapper;
    }

    public void setDefinitionMapper(DefinitionMapper definitionMapper) {
        this.definitionMapper = definitionMapper;
    }

    public FormItemRuleMapper getItemRuleMapper() {
        return itemRuleMapper;
    }

    public void setItemRuleMapper(FormItemRuleMapper itemRuleMapper) {
        this.itemRuleMapper = itemRuleMapper;
    }

    @Override
    public boolean addFormType(FormType type) throws Exception {
        return formTypeMapper.addFormType(type)>0;
    }

    @Override
    public List getFormTypeList() throws Exception {
        return formTypeMapper.getFormTypeList();
    }

    @Override
    public boolean updateFormType(FormType type) throws Exception {
        return formTypeMapper.updateFormType(type)>0;
    }

    @Override
    public FormType getFormType(String id) throws Exception {
        return formTypeMapper.getFormType(id);
    }
//
//    @Override
//    public FormType getFormTypeByKey(String key) throws Exception {
//        return formTypeMapper.getFormTypeByKey(key);
//    }

    @Override
    public boolean deleteFormType(String id) throws Exception {
        return formTypeMapper.deleteFormType(id)>0;
    }

    @Override
    public PageData getFormData(PageMap pageMap) throws Exception {
        List<Form> list = formMapper.getFormList(pageMap);
        for(Form form: list){
            FormType formType = formTypeMapper.getFormTypeByKey(form.getType());
            form.setFormType(formType);
        }
        return new PageData(formMapper.getFormCount(pageMap), list, pageMap);
    }

    @Override
    public Form getFormByKey(String key) throws Exception {
        return formMapper.getFormByKey(key);
    }

    @Override
    public boolean addForm(Form form, List<FormItem> list) throws Exception {
        SysUser user = getSysUser();
        int count = formMapper.getCountByKey(form.getUnkey());
        int ret = 0;
        if(count > 0){
            form.setModifyuserid(user.getUserid());
            form.setModifyusername(user.getName());

            ret = formMapper.updateFormByKey(form);

            if(ret > 0) {

                itemMapper.deleteFormItemByUnkey(form.getUnkey());
                for(FormItem item : list) {

                    item.setAdduserid(user.getUserid());
                    item.setAddusername(user.getName());
                    item.setAdddeptid(user.getUserid());
                    item.setAdddeptname(user.getName());

                    itemMapper.insertFormItem(item);
                }
            }
        }
        else{
            form.setAdduserid(user.getUserid());
            form.setAddusername(user.getName());
            form.setAdddeptid(user.getUserid());
            form.setAdddeptname(user.getName());
            ret = formMapper.addForm(form);

            if(ret > 0) {

                for(FormItem item : list) {

                    item.setAdduserid(user.getUserid());
                    item.setAddusername(user.getName());
                    item.setAdddeptid(user.getUserid());
                    item.setAdddeptname(user.getName());

                    itemMapper.insertFormItem(item);
                }
            }
        }

        // 2555 修改表单中控件名称后，流程定义管理中表单管理中项目会多出来
        itemRuleMapper.deleteNoneExistItemRule(form.getUnkey());
        return ret > 0;
    }

    @Override
    public boolean deleteForm(String id, String unkey) throws Exception {

        itemMapper.deleteFormItemByUnkey(unkey);
        itemRuleMapper.deleteFormItemRuleByUnkey(unkey);

        return formMapper.deleteForm(id) > 0;
    }

    @Override
    public boolean initFormRule(String definitionkey, String taskkey) throws Exception {

        Definition definition = definitionMapper.getDefinitionByKey(definitionkey, null, "1");
        String formkey = definition.getFormkey();

        List<FormItem> itemList = itemMapper.selectFromItemByUnkey(formkey);
        for(FormItem item : itemList) {

            Map param = new HashMap();
            param.put("definitionkey", definitionkey);
            param.put("definitionid", definition.getDefinitionid());
            param.put("taskkey", taskkey);
            param.put("unkey", item.getUnkey());
            param.put("itemid", item.getItemid());
            param.put("itemname", item.getItemname());

            FormItemRule rule = itemRuleMapper.selectItemRule(param);
            if(rule == null) {

                rule = new FormItemRule();
                rule.setUnkey(item.getUnkey());
                rule.setItemid(item.getItemid());
                rule.setItemname(item.getItemname());
                rule.setDefinitionkey(definitionkey);
                rule.setDefinitionid(definition.getDefinitionid());
                rule.setTaskkey(taskkey);

                itemRuleMapper.insertItemRuleForInit(rule);
            }
        }

        return true;
    }

    @Override
    public List<FormItemRule> selectFormRuleList(String definitionkey, String definitionid, String taskkey) throws Exception {

        Definition definition = definitionMapper.getDefinitionByKey(definitionkey, definitionid, null);
        String formkey = definition.getFormkey();

        Map param = new HashMap();
        param.put("definitionkey", definitionkey);
        param.put("definitionid", definition.getDefinitionid());
        param.put("taskkey", taskkey);
        param.put("unkey", definition.getFormkey());

        return itemRuleMapper.selectItemRuleList(param);
    }

    @Override
    public boolean setFormItemRule(Map map) throws Exception {

        String definitionkey = (String) map.get("definitionkey");
        String taskkey = (String) map.get("taskkey");
        String itemid = (String) map.get("itemid");
        String col = (String) map.get("col");
        String val = (String) map.get("val");

        Definition definition = definitionMapper.getDefinitionByKey(definitionkey, null, "1");
        String unkey = definition.getFormkey();

        Map param = new HashMap();
        param.put("definitionkey", definitionkey);
        param.put("definitionid", definition.getDefinitionid());
        param.put("taskkey", taskkey);
        param.put("unkey", unkey);
        param.put("itemid", itemid);
        param.put("col", col);
        param.put("val", val);

        return itemRuleMapper.setFormItemRule(param) > 0;
    }

    @Override
    public List<FormItem> selectFormItemList(String formkey) throws Exception {

        return itemMapper.selectFromItemByUnkey(formkey);
    }

    @Override
    public int isFormReferencedByDefinition(String formkey) throws Exception {
        return definitionMapper.isFormReferencedByDefinition(formkey);
    }

    @Override
    public int savePhoneHtml(Form form) throws Exception {
        form.setModifyuserid(getSysUser().getUserid());
        return formMapper.updateFormByKey(form);
    }
}

