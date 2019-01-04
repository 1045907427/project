/**
 * @(#)ICrmVisitReportService.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-8-25 limin 创建版本
 */
package com.hd.agent.crm.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 客户拜访报表Service
 *
 * Created by limin on 2016/8/25.
 */
public interface ICrmVisitReportService {

    /**
     * 查询业务员巡店汇总报表数据
     *
     * @param pageMap
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 25, 2016
     */
    public PageData getVisitReportData(PageMap pageMap) throws Exception;

    /**
     * 查询业务员拜访客户汇总报表数据
     *
     * @param pageMap
     * @return
     * @throws Exception
     * @author xuxin
     */
    public PageData getPersonReportData(PageMap pageMap) throws Exception;
}
