/**
 * @(#)MapAction.java
 * @author xuxin
 * <p>
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2017-6-8 xuxin 创建版本
 */
package com.hd.agent.hr.action;

import org.apache.commons.lang3.StringUtils;

/**
 * 地图管理Action
 *
 * @author xuxin
 */
public class MapAction extends BaseAction {

    private static final long serialVersionUID = -1108102060731270421L;

    /**
     * 轨迹管理界面
     *
     * @return
     */
    public String indexPage() throws Exception {
        String ak = getSysParamValue("baiduWebAK");
        String service_id = getSysParamValue("baiduYYServerid");
        String radiusThreshold = getSysParamValue("baiduYYRadiusThreshold");
        if (StringUtils.isEmpty(ak) || StringUtils.isEmpty(service_id) || StringUtils.isEmpty(radiusThreshold)) {
            request.setAttribute("errormsg", "地图配置项缺失,请配置map_ak 和 map_service_id 和 radiusThreshold");
        }
        request.setAttribute("ak", ak);
        request.setAttribute("service_id", service_id);
        request.setAttribute("radiusThreshold", radiusThreshold);
        String phoneGPSTime = getSysParamValue("PhoneGPSTime");
        String startTime = "0:00:00";
        String endTime = "23:59:59";
        if (StringUtils.isNotEmpty(phoneGPSTime)) {
            String[] temp = phoneGPSTime.split("~");
            if (temp.length == 2) {
                startTime = temp[0] + ":00";
                endTime = temp[1] + ":00";
            }
        }
        request.setAttribute("startTime", startTime);
        request.setAttribute("endTime", endTime);
        return SUCCESS;
    }
}

