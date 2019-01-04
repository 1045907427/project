/**
 * @(#)EmployeeOvertimeStatisticJob.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 29, 2015 limin 创建版本
 */
package com.hd.agent.hr.job;

import com.hd.agent.activiti.dao.ProcessMapper;
import com.hd.agent.activiti.model.Process;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.hr.dao.StatisticMapper;
import com.hd.agent.system.job.BaseJob;
import com.hd.agent.system.model.SysParam;
import com.hd.agent.system.service.ISysParamService;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成加班申请单报表
 * 
 * @author limin
 * @date Dec 29, 2015
 */
public class EmployeeOvertimeStatisticJob extends BaseJob {

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		JobDetail jobDetail = jobExecutionContext.getJobDetail();
		Map map =  (Map) jobDetail.getJobDataMap().get("dataMap");
		// CustomerMapper customerMapper = (CustomerMapper)SpringContextUtils.getBean("customerMapper");

		try {

            ISysParamService paramService = (ISysParamService) SpringContextUtils.getBean("sysParamService");
            SysParam restParam = paramService.getSysParam("EMPLOYEEOVERTIMEPROCESSNAME");

            ProcessMapper processMapper = (ProcessMapper) SpringContextUtils.getBean("processMapper");
            List<Process> list = processMapper.selectProcessByDefinitionkey(restParam.getPvalue());

            StatisticMapper statisticMapper = (StatisticMapper) SpringContextUtils.getBean("statisticMapper");
            statisticMapper.deleteOvertimeStatisticByDate(CommonUtils.getTodayDataStr());

            for(Process process : list) {

                if(process.getJson() == null) {

                    continue;
                }

                String jsonStr = new String(process.getJson(), "UTF-8");

                if("[]".equals(jsonStr) || "{}".equals(jsonStr) || "".equals(jsonStr)) {
                    continue;
                }

                List<Map> jsons = JSONUtils.jsonStrToList(jsonStr, new HashMap<String, String>());

                Map json = new HashMap();
                Map data = new HashMap();
                data.put("businessdate", CommonUtils.getTodayDataStr());
                data.put("status", process.getStatus());
                int cnt = 1;
                for(Map temp : jsons) {

                    String agentitemid = (String) temp.get("agentitemid");
                    String vals = (String) temp.get("vals");

                    json.put(agentitemid, vals);

                    data.put("val" + (cnt < 10 ? "0" + cnt : cnt), CommonUtils.nullToEmpty(vals).trim());
                    cnt++;
                }

                data.put("val" + (cnt < 10 ? "0" + cnt : cnt), process.getId());
                cnt++;
                data.put("val" + (cnt < 10 ? "0" + cnt : cnt), process.getStatus());
                cnt++;

                statisticMapper.insertOvertimeStatistic(data);
            }

			flag = true;
		} catch (Exception e) {
			logger.error("定时器执行异常 请假单报表生成失败！", e);
		}

		super.executeInternal(jobExecutionContext);
	}
}

