package com.hd.agent.system.job;

import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.service.IBackupDBService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by wanghongteng on 2017/10/19.
 */
public class deleteExpireBackupDBFileJob extends BaseJob {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        IBackupDBService backupDBService = (IBackupDBService) SpringContextUtils.getBean("backupDBService");
        try {
            backupDBService.deleteExpireFile();
            flag = true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("定时器执行异常 自动删除过期的数据库备份文件 状态", e);
        }
        super.executeInternal(jobExecutionContext);
    }


}