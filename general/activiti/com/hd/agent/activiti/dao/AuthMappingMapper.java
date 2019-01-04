package com.hd.agent.activiti.dao;

import com.hd.agent.activiti.model.AuthMapping;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AuthMappingMapper {

    /**
     *
     * @param definitionkey
     * @param definitionid
     * @param taskkey
     * @param mappingtype
     * @return
     * @author limin
     * @date Apr 23, 2015
     */
    public List<AuthMapping> selectMappingByTaskkey(@Param("definitionkey")String definitionkey, @Param("definitionid")String definitionid, @Param("taskkey")String taskkey, @Param("mappingtype")String mappingtype);

    /**
     *
     * @param definitionkey
     * @param definitionid
     * @param taskkey
     * @param mappingtype
     * @return
     * @author limin
     * @date Apr 23, 2015
     */
    public int deleteMappingByTaskkey(@Param("definitionkey")String definitionkey, @Param("definitionid")String definitionid, @Param("taskkey")String taskkey, @Param("mappingtype")String mappingtype);

    /**
     *
     * @param mapping
     * @return
     * @author limin
     * @date Apr 23, 2015
     */
    public int insertMapping(AuthMapping mapping);

    /**
     *
     * @param map
     * @return
     * @author limin
     * @date Apr 28, 2015
     */
    public AuthMapping selectAuthMapping(Map map);

    /**
     * 将旧版本的authmap设定clone到新版本
     * @param param
     * @return
     * @author limin
     * @date May 8, 2015
     */
    public int doCloneOldVerson2NewVersion(Map param);

}