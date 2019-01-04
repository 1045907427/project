/**
 * @(#)MapDistanceJob.java
 * @author xuxin
 * <p>
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2017-6-9 xuxin 创建版本
 */
package com.hd.agent.hr.job;

import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.hr.service.IMapService;
import com.hd.agent.phone.service.IPhoneService;
import com.hd.agent.system.job.BaseJob;
import com.hd.agent.system.model.SysParam;
import com.hd.agent.system.service.ISysParamService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * 车辆地图轨迹数据获取
 *
 * @author xuxin
 * @date 2017-6-9
 */
public class MapDistanceJob extends BaseJob {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //JobDetail jobDetail = jobExecutionContext.getJobDetail();
        try {
            IMapService mapService = (IMapService) SpringContextUtils.getBean("mapService");
            ISysParamService sysParamService = (ISysParamService) SpringContextUtils.getBean("sysParamService");
            //昨天日期
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());
            calendar.add(calendar.DATE, -1);
            String day = CommonUtils.dataToStr(calendar.getTime(), "yyyy-MM-dd");
            SysParam sysParam = sysParamService.getSysParam("isOpenBaiduYY");
            String isOpenBaiduYY = "0";
            if (null != sysParam) {
                isOpenBaiduYY = sysParam.getPvalue();
            }
            //启用鹰眼时 使用鹰眼服务计算行程
            //否则按坐标定位计算
            if ("1".equals(isOpenBaiduYY)) {
                Map result = mapService.addRootByDateAndUserId(day, day, null, true);
                addSysLogInfo(result.get("msg").toString());
            } else {
                IPhoneService phoneService = (IPhoneService) SpringContextUtils.getBean("phoneService");
                Map result = phoneService.addRootByDateAndUserId(day, day, null);
                addSysLogInfo(result.get("msg").toString());
            }
            flag = true;
        } catch (Exception ex) {
            logger.error("车辆地图轨迹数据获取异常.", ex);
        }
        super.executeInternal(jobExecutionContext);
    }
}

