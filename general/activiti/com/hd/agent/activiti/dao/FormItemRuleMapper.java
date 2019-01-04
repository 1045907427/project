package com.hd.agent.activiti.dao;

import java.util.List;
import java.util.Map;

import com.hd.agent.activiti.model.FormItemRule;
import org.apache.ibatis.annotations.Param;

public interface FormItemRuleMapper {

    /**
     * 初始化表单项目的权限
     * @param rule
     * @return
     * @author limin
     * @date Feb 9, 2015
     */
    public int insertItemRuleForInit(FormItemRule rule);

    /**
     * 更新表单项目规则
     * @param map
     * @return
     * @author limin
     * @date Feb 9, 2015
     */
    public int updateItemRule(Map map);

    /**
     * 查询表单项目规则
     * @param map
     * @return
     * @author limin
     * @date Feb 9, 2015
     */
    public FormItemRule selectItemRule(Map map);

    /**
     * 查询表单项目规则List
     * @param map
     * @return
     * @author limin
     * @date Feb 9, 2015
     */
    public List<FormItemRule> selectItemRuleList(Map map);

    /**
     * 更新表单项目规则
     * @param map
     * @return
     * @author limin
     * @date Feb 10, 2015
     */
    public int setFormItemRule(Map map);

    /**
     * 根据formkey删除form项目规则
     * @param unkey
     * @return
     * @author limin
     * @date Feb 14, 2015
     */
    public int deleteFormItemRuleByUnkey(String unkey);

    /**
     * 删除不存在的项目规则
     * @param unkey
     * @return
     * @author limin
     * @date 2015年2月15日
     */
    public int deleteNoneExistItemRule(@Param("unkey")String unkey);

    /**
     * 将旧版本的item操作权限设定clone到新版本
     * @param param
     * @return
     * @author limin
     * @date May 15, 2015
     */
    public int doCloneOldVerson2NewVersion(Map param);
}