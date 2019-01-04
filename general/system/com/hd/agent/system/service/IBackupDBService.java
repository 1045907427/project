package com.hd.agent.system.service;

import com.hd.agent.system.model.BackupDB;

import java.util.List;
import java.util.Map;

/**
 * Created by wanghongteng on 2017/9/28.
 */
public interface IBackupDBService {

    /**
     * 获取数据库备份配置数据
     * @param
     * @return java.lang.String
     * @throws
     * @author wanghongteng
     * @date 2017-9-28
     */
    public BackupDB getBackupDB() throws Exception;


    /**
     * 获取数据库备份配置数据
     * @param
     * @return java.lang.String
     * @throws
     * @author wanghongteng
     * @date 2017-9-28
     */
    public List<Map> getBackupDBFileList() throws Exception;
    /**
     * 保存数据库备份参数配置页面
     * @param
     * @return java.lang.String
     * @throws
     * @author wanghongteng
     * @date 2017-9-28
     */
    public Map saveBackup(BackupDB backupDB) throws Exception;

    /**
     * 手动备份数据库
     * @param
     * @return java.lang.String
     * @throws
     * @author wanghongteng
     * @date 2017-9-28
     */
    public Map doBackup() throws Exception;

    /**
     * 压缩备份文件
     * @param
     * @return java.lang.String
     * @throws
     * @author wanghongteng
     * @date 2017-9-28
     */
    public Map doBackupFileToGzip(String filename) throws Exception;

    /**
     * 删除过期文件
     * @param
     * @return java.lang.String
     * @throws
     * @author wanghongteng
     * @date 2017-9-28
     */
    public Map deleteExpireFile() throws Exception;

    /**
     * 定时器自动备份
     * @param
     * @return java.lang.String
     * @throws
     * @author wanghongteng
     * @date 2017-9-28
     */
    public void autoDoBackDB() throws Exception;
}
