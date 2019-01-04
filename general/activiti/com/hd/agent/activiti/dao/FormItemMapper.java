package com.hd.agent.activiti.dao;

import java.util.List;

import com.hd.agent.activiti.model.FormItem;

public interface FormItemMapper {

    /**
     * 根据form unkey查询项目一览
     * @param unkey
     * @return
     * @author limin
     * @date Feb 9, 2015
     */
    public List<FormItem> selectFromItemByUnkey(String unkey);

    /**
     * 根据form unkey删除项目
     * @param unkey
     * @return
     * @author limin
     * @date Feb 9, 2015
     */
    public int deleteFormItemByUnkey(String unkey);

    /**
     * 插入表单项目
     * @param item
     * @return
     */
    public int insertFormItem(FormItem item);
}