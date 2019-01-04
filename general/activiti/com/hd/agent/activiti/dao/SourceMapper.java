package com.hd.agent.activiti.dao;

import com.hd.agent.activiti.model.Source;
import org.apache.ibatis.annotations.Param;

public interface SourceMapper {

    /**
     *
     * @param source
     * @return
     * @author limin
     * @date May19, 2015
     */
    public int insertSource(Source source);

    /**
     *
     * @param definitionid
     * @param type
     * @return
     * @author limin
     * @date May19, 2015
     */
    public Source selectSource(@Param("definitionid")String definitionid, @Param("type")String type);
}