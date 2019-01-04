/**
 * @(#)IHrReportService.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-9-1 limin 创建版本
 */
package com.hd.agent.hr.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.hr.model.Signin;

import java.util.Map;

/**
 * 报表Service
 * 
 * @author limin
 */
public interface IHrReportService {

    /**
     * 查询签到报表数据
     *
     * @param pageMap
     * @return
     * @author limin
     * @date Set 1, 2016
     */
    public PageData getSignReportData(PageMap pageMap) throws Exception;

}

