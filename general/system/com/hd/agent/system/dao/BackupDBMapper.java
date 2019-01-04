package com.hd.agent.system.dao;

import com.hd.agent.system.model.BackupDB;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface BackupDBMapper {

    /**
     * 根据主键查询记录
     */
    BackupDB getBackupDBByConnectname(String connectname);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int addBackupDB(BackupDB backupDB);

    /**
     * 根据主键删除记录
     */
    int deleteBackupDBByConnectname(String connectname);












//
//
//
//
//    /**
//     * 根据条件查询记录总数
//     */
//    int countByExample(BackupDB example);
//
//    /**
//     * 根据条件删除记录
//     */
//    int deleteByExample(BackupDB example);
//
//
//
//
//    /**
//     * 保存属性不为空的记录
//     */
//    int insertSelective(BackupDB record);
//
//    /**
//     * 根据条件查询记录集
//     */
//    List<BackupDB> selectByExample(BackupDB example);
//
//    /**
//     * 根据主键查询记录
//     */
//    BackupDB selectByPrimaryKey(Integer id);
//
//    /**
//     * 根据条件更新属性不为空的记录
//     */
//    int updateByExampleSelective(@Param("record") BackupDB record, @Param("condition") Map<String, Object> condition);
//
//    /**
//     * 根据条件更新记录
//     */
//    int updateByExample(@Param("record") BackupDB record, @Param("condition") Map<String, Object> condition);
//
//    /**
//     * 根据主键更新属性不为空的记录
//     */
//    int updateByPrimaryKeySelective(BackupDB record);
//
//    /**
//     * 根据主键更新记录
//     */
//    int updateByPrimaryKey(BackupDB record);
}