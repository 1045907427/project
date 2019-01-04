package com.hd.agent.sales.dao;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.ImportSet;
import org.apache.ibatis.annotations.Param;

public interface ImportSetMapper {

    /**
     * 根据主键删除记录
     */
    public int deleteImportModelById(Integer id);
    /**
     * 保存属性不为空的记录
     */
    public int insertImportSet(ImportSet record);

    /**
     * 根据条件查询记录集
     */
    public List<ImportSet> showImportModelData(PageMap pageMap);
    /**
    * 根据条件查询记录集个数
    */
    public int showImportModelDataCount(PageMap pageMap);

    /**
     * 根据主键查询记录
     */
    public ImportSet showImportModelById(Integer id);

    /**
     * 根据条件更新记录
     */
    public int updateByExample(@Param("record") ImportSet record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    public int updateImportSet(ImportSet record);

    /**
     * 根据主键更新记录
     */
    public int updateByPrimaryKey(ImportSet record);
}