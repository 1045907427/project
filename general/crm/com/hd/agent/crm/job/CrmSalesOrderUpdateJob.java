package com.hd.agent.crm.job;

import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.crm.service.ICustomerSummaryService;
import com.hd.agent.system.job.BaseJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lin_xx on 2016/11/28.
 */
public class CrmSalesOrderUpdateJob extends BaseJob {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        ICustomerSummaryService customerSummaryService = (ICustomerSummaryService) SpringContextUtils.getBean("customerSummaryService");
        try {
            List<Map> customerList = customerSummaryService.getAllSummaryCustomer();
            for(Map map : customerList){
                Map condition = new HashMap();
                condition.put("begindate",map.get("businessdate"));
                condition.put("enddate", CommonUtils.getTodayDataStr());
                condition.put("customerid", map.get("customerid"));

                PageMap pageMap = new PageMap();
                pageMap.setCondition(condition);
                Map updateMap = customerSummaryService.crmSalesSync(pageMap);
                flag = (Boolean) updateMap.get("flag");
            }
        } catch (Exception e) {
            logger.error("定时器执行异常 系统日志清理任务", e);
        }
        super.executeInternal(jobExecutionContext);
    }

}

