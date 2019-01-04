package com.hd.agent.activiti.dao;

import com.hd.agent.activiti.model.DataTrace;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataTraceMapper {

    /**
     * 查询工作相对应的数据变更记录
     * @param processid
     * @return
     * @author limin
     * @date Mar 3, 2015
     */
    public List<DataTrace> selectDataTraceList(@Param("processid")String processid);

    /**
     * 删除工作对应的数据变更记录
     * @param processid
     * @return
     * @author limin
     * @date Mar 3, 2015
     */
    public int deleteDataTrace(@Param("processid")String processid);

    /**
     * 查询工作相对应的数据变更记录
     * @param id
     * @param processid
     * @return
     * @author limin
     * @date Mar 3, 2015
     */
    public DataTrace selectDataTraceByProcessid(@Param("id")String id, @Param("processid")String processid);

    /**
     * 插入工作相对应的数据变更记录
     * @param trace
     * @return
     * @author limin
     * @date Mar 3, 2015
     */
    public int insertDataTrace(DataTrace trace);
}