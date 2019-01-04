package com.hd.agent.system.job;

import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.dao.SysParamMapper;
import com.hd.agent.system.model.SysParam;
import com.hd.agent.system.service.IAccountingService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by PXX on 2015/4/2.
 */
public class CloseAccountingJob extends BaseJob {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        String isAutoCloseAccounting = "";
        SysParamMapper sysParamMapper = (SysParamMapper) SpringContextUtils.getBean("sysParamMapper");
        SysParam sysParam = sysParamMapper.getSysParam("isAutoCloseAccounting");
        if(null!=sysParam){
            isAutoCloseAccounting = sysParam.getPvalue();
        }

        if("1".equals(isAutoCloseAccounting)){
            try {
                IAccountingService accountingService = (IAccountingService)SpringContextUtils.getBean("accountingService");
                accountingService.closeAccountingTask();
                flag = true;
            }catch (Exception e){
                logger.error("定时器执行异常 会计区间关账失败", e);
            }
            super.executeInternal(jobExecutionContext);
        }
    }
}
