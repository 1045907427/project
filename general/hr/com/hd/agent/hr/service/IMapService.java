package com.hd.agent.hr.service;

import java.util.Map;

/**
 * Created by xuxin on 2017/6/12 0012.
 */
public interface IMapService {
    /**
     * 获取轨迹信息
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param userid  用户编号
     * @param elseLocation 如果鹰眼轨迹未获取,是否采用定位法重新计算
     * @return
     * @throws Exception
     */
    Map addRootByDateAndUserId(String startDate, String endDate, String userid, boolean elseLocation) throws Exception;
}
