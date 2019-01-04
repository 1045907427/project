package com.hd.agent.system.dao;

import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface SyncMapper {
    /**
     * 获取数据表的行信息
     *
     * @param tableSchema
     * @return
     */
    List<String> selectTableNames(@Param("tableSchema") String tableSchema, @Param("tablePrefix") String tablePrefix);

    /**
     * 查询集合
     * @param sql 待查询的sql语句
     * @return
     */
    List<LinkedHashMap<String, Object>> selectList(String sql);

    /**
     * 删除操作
     * @param sql 待执行的sql语句
     * @return
     */
    int deleteSql(String sql);

    /**
     * 查询记录总数
     * @param sql 待查询的sql语句
     * @return
     */
    int selectCount(String sql);

    /**
     * 插入单条记录
     * @param sql
     * @return
     */
    int insert(String sql);

    int insertList(@Param("tableName") String tableName, @Param("columnSql") String columnSql, @Param("itemSql") String itemSql, @Param("list") List<LinkedHashMap<String, Object>> dataList);

    /**
     * 获取数据表的行信息
     *
     * @param tableSchema
     * @param tableName
     * @return
     */
    List<Map> selectTableColumn(@Param("tableSchema") String tableSchema, @Param("tableName") String tableName);

    List<LinkedHashMap<String, Object>> selectBusinessdateList(@Param("tableName") String tableName, @Param("businessdate1") String businessdate1, @Param("businessdate2") String businessdate2);

    List<LinkedHashMap<String, Object>> selectBusinessdateDetailList(@Param("tableName") String tableName, @Param("detailTableName") String detailTableName, @Param("parentKey") String parentKey, @Param("businessdate1") String businessdate1, @Param("businessdate2") String businessdate2);

    /**
     * 根据业务日期获取相关的主表编号,再清理子表的关联数据
     * @param tableName
     * @param detailTableName
     * @param businessdate1
     * @param businessdate2
     * @return
     */
    int deleteBusinessdateDetailList(@Param("tableName") String tableName, @Param("detailTableName") String detailTableName, @Param("parentKey") String parentKey, @Param("businessdate1") String businessdate1, @Param("businessdate2") String businessdate2);
    //int insertBaseBomDetail(@Param("list") List<Map> list);
}