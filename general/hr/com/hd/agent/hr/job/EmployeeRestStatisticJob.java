/**
 * @(#)EmployeeRestStatisticJob.java
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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成请假单报表
 * 
 * @author limin
 * @date Dec 29, 2015
 */
public class EmployeeRestStatisticJob extends BaseJob {

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobDetail jobDetail = jobExecutionContext.getJobDetail();
//		Map map =  (Map) jobDetail.getJobDataMap().get("dataMap");
        String processid = null;

		try {

            String today = CommonUtils.getTodayDataStr();
            String yesterday = CommonUtils.getYestodayDateStr();

            ISysParamService paramService = (ISysParamService) SpringContextUtils.getBean("sysParamService");
            SysParam restParam = paramService.getSysParam("EMPLOYEERESTPROCESSNAME");

            ProcessMapper processMapper = (ProcessMapper) SpringContextUtils.getBean("processMapper");
            List<Process> processes = processMapper.selectProcessByDefinitionkey(restParam.getPvalue());

            StatisticMapper statisticMapper = (StatisticMapper) SpringContextUtils.getBean("statisticMapper");
            statisticMapper.deleteRestStatisticByDate(today);

            List<Map> datas = new ArrayList<Map>();
            for(Process process : processes) {

                processid = process.getId();
                if(process.getJson() == null) {

                    continue;
                }

                String jsonStr = new String(process.getJson(), "UTF-8");

                if("[]".equals(jsonStr) || "{}".equals(jsonStr) || StringUtils.isEmpty(jsonStr)) {

                    continue;
                }

//                // 上一次统计
//                Map yesterdayMap = statisticMapper.selectRestStatisticByProcessid(process.getId(), yesterday);
//                // 这一次统计
//                Map todayMap = statisticMapper.selectRestStatisticByProcessid(process.getId(), today);
//
//                // 上一次统计存在且status为结束 && 这一次统计不存在
//                if(yesterdayMap != null && "9".equals(yesterdayMap.get("status"))) {
//
//                    if(todayMap != null && "9".equals(todayMap.get("status"))) {
//                        continue;
//                    }
//                    if(todayMap != null) {
//
//                        statisticMapper.deleteRestStatisticById((String)todayMap.get("id"));
//                    }
//
//                    yesterdayMap.remove("id");
//                    statisticMapper.insertRestStatistic(yesterdayMap);
//                    continue;
//                }
//
//                // 今天的统计结果存在了，并且状态为结束
//                if(todayMap != null && "9".equals(todayMap.get("status"))) {
//                    continue;
//                }
//
//                // 今天的统计结果存在了，并且状态不为结束
//                // 删除并重新统计
//                if(todayMap != null && !"9".equals(todayMap.get("status"))) {
//                    statisticMapper.deleteRestStatisticById(String.valueOf(todayMap.get("id")));
//                }

//                List<Map> jsons = JSONUtils.jsonStrToList(jsonStr, new HashMap<String, String>());
                JSONArray jsonArray = JSONArray.fromObject(jsonStr);

                Map data = new HashMap();
                data.put("processid", process.getId());
                data.put("businessdate", CommonUtils.getTodayDataStr());
                data.put("status", process.getStatus());
                for(int i = 0; i < jsonArray.size(); i++) {

                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String agentitemid = jsonObject.getString("agentitemid");
                    String title = jsonObject.getString("title");
                    String v = "";

                    Object valueObject = jsonObject.get("vals");
                    if(valueObject instanceof JSONObject) {

                        Object textObject = ((JSONObject)valueObject).get("text");
                        if(textObject != null) {
                            v = ((JSONObject)valueObject).getString("text");
                        }
                    } else if(valueObject instanceof JSONArray) {

                        JSONArray tempArr = (JSONArray) valueObject;
                        for(int j = 0; j < tempArr.size(); j++) {

                            JSONObject jObject = (JSONObject) tempArr.get(j);
                            v = v + "," + jObject.getString("text");
                        }
                        // 去除，
                        if(StringUtils.isNotEmpty(v)) {
                            v = v.substring(1);
                        }
                    } else if(valueObject instanceof String) {
                        v = (String) valueObject;
                    }


                    if(findStr(title, "申请") && (findStr(title, "人") || findStr(title, "用户"))) {
                        data.put("applyuser", v);
                        continue;
                    }

                    if(findStr(title, "申请") && (findStr(title, "时间") || findStr(title, "日期"))) {
                        data.put("applydate", v);
                        continue;
                    }

                    if(findStr(title, "部门") && (findStr(title, "所在") || findStr(title, "所属") || findStr(title, "归属") || findStr(title, "申请"))) {
                        data.put("dept", v);
                        continue;
                    }

                    if(findStr(title, "代理") && (findStr(title, "岗位") || findStr(title, "职位") || findStr(title, "职务"))) {
                        data.put("delegateuser", v);
                        continue;
                    }

                    if(findStr(title, "岗位") || findStr(title, "职位") || findStr(title, "职务")) {
                        data.put("workjob", v);
                        continue;
                    }

                    if(findStr(title, "代理") && (findStr(title, "电话"))) {
                        data.put("delegatephone", v);
                        continue;
                    }

                    if(findStr(title, "请假") && (findStr(title, "类型"))) {
                        data.put("resttype", v);
                        continue;
                    }

                    if(findStr(title, "开始") && (findStr(title, "时间") || findStr(title, "日期"))) {
                        data.put("begindate", v);
                        continue;
                    }

                    if(findStr(title, "结束") && (findStr(title, "时间") || findStr(title, "日期"))) {
                        data.put("enddate", v);
                        continue;
                    }

                    if((findStr(title, "天数") || findStr(title, "时长"))) {
                        data.put("day", v);
                        continue;
                    }

                    if(findStr(title, "请假") && (findStr(title, "理由") || findStr(title, "事由") || findStr(title, "原因"))) {
                        data.put("reason", v);
                        continue;
                    }


                }
                datas.add(data);
                if(datas.size() >= 100) {

                    statisticMapper.insertRestStatisticList(datas);
                    datas = new ArrayList<Map>();
                }
            }
            if(datas.size() > 0) {
                statisticMapper.insertRestStatisticList(datas);
            }
			flag = true;
		} catch (Exception e) {
			logger.error("定时器执行异常 请假单报表生成失败！OA: " + processid, e);
		}

		super.executeInternal(jobExecutionContext);
	}

    /**
     *
     * @param src
     * @param g
     * @return
     * @author limin
     * @date Jul 21, 2016
     */
    private boolean findStr(String src, String g) {

        if(StringUtils.isEmpty(src)) {
            return false;
        }

        return src.indexOf(g) >= 0;
    }
}

