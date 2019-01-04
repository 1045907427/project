package com.hd.agent.activiti.dao;

import java.util.List;
import java.util.Map;

import com.hd.agent.activiti.model.Definition;
import com.hd.agent.common.util.PageMap;
import org.apache.ibatis.annotations.Param;

public interface DefinitionMapper {
//
//	public int getDefinitionCountByKey(String key);
	
	public int addDefinition(Definition definition);
	
	public int updateDefinitionByKey(Definition definition);
	
	public List getDefinitionList(PageMap pageMap);
	
	public int getDefinitionCount(PageMap pageMap);

    /**
     *
     * @param unkey
     * @param definitionid
     * @param isdeploy
     * @return
     */
	public Definition getDefinitionByKey(@Param("unkey")String unkey, @Param("definitionid")String definitionid, @Param("isdeploy")String isdeploy);
	
	public List<Map> getDefinitionTree(Map map);

    /**
     * check当前流程标识是否已经存在
     * @param unkey
     * @param modelid
     * @return
     * @author limin
     * @date 2015-03-12
     */
    public int isDefinitionExist(@Param("unkey")String unkey, @Param("modelid")String modelid);

    /**
     * 删除流程定义
     * @param unkey
     * @return
     */
    public int deleteDefinitionByUnkey(@Param("unkey")String unkey);

    /**
     * 判断在线表单是否被流程引用
     * @param formkey
     * @return
     * @author limin
     * @date Apr 30, 2015
     */
    public int isFormReferencedByDefinition(@Param("formkey")String formkey);
//
//    /**
//     * 获取已部署的最新版本
//     * @param unkey
//     * @param definitionid
//     * @return
//     * @author limin
//     * @date May 7, 2015
//     */
//    public Definition selectLatestDeployVersion(@Param("unkey")String unkey, @Param("definitionid")String definitionid);

    /**
     *
     * @param definition
     * @return
     * @author limin
     * @date May 7, 2015
     */
    public int updateDefinitionById(Definition definition);

    /**
     *
     * @param definitionid
     * @return
     * @author limin
     * @date Feb 8, 2018
     */
    public int enableDefinitionVersion(@Param("definitionid") String definitionid);

    /**
     * 将definitionkey对应的流程定义isdeploy=1
     * @param definitionkey
     * @return
     * @author limin
     * @date May 14, 2015
     */
    public int setDefinitionAllEndeployed(String definitionkey);

    /**
     * 获取所有流程版本
     * @return
     * @author limin
     * @date May 19, 2015
     */
    public List getAllDefinitionVersions();
}