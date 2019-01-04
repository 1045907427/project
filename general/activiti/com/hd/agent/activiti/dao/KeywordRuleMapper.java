package com.hd.agent.activiti.dao;

import com.hd.agent.activiti.model.KeywordRule;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface KeywordRuleMapper {

    /**
     *
     * @param definitionkey
     * @param definitionid
     * @return
     * @author limin
     * @date 2015年4月2日
     */
    public KeywordRule selectKeywordRuleByDefinitionkey(@Param("definitionkey")String definitionkey, @Param("definitionid")String definitionid);

    /**
     *
     * @param definitionkey
     * @param definitionid
     * @return
     * @author limin
     * @date 2015年4月2日
     */
    public int deleteKeywordRuleByDefinitionkey(@Param("definitionkey")String definitionkey, @Param("definitionid")String definitionid);

    /**
     *
     * @param rule
     * @return
     * @author limin
     * @date 2015年4月2日
     */
    public int insertKeywordRule(KeywordRule rule);

    /**
     * 将旧版本的keyword设定clone到新版本
     * @param param
     * @return
     * @author limin
     * @date May 21, 2015
     */
    public int doCloneOldVerson2NewVersion(Map param);
}