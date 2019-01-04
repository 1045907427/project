/**
 * @(#)CrmVisitReportMapper.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-8-25 limin 创建版本
 */
package com.hd.agent.crm.dao;

import com.hd.agent.common.util.PageMap;

import java.util.List;
import java.util.Map;

/**
 * 客户拜访报表Mapper
 *
 * Created by limin on 2016/8/25.
 */
public interface CrmVisitReportMapper {

    /**
     * 查询业务员巡店汇总数据List
     *
     * @param pageMap
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 25, 2016
     */
    List<Map> getVisitReportList(PageMap pageMap) throws Exception;

    /**
     * 查询业务员巡店汇总数据Count
     *
     * @param pageMap
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 25, 2016
     */
    int getVisitReportCount(PageMap pageMap) throws Exception;

    /**
     * 查询业务员巡店汇总数据合计
     *
     * @param pageMap
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 26, 2016
     */
    Map getVisitReportSumData(PageMap pageMap) throws Exception;

    /**
     * 查询业务员拜访客户汇总数据List
     *
     * @param pageMap
     * @return
     * @throws Exception
     * @author xuxin
     * @date May 10, 2017
     */
    List<Map> getPersonReportList(PageMap pageMap) throws Exception;

    /**
     * 查询业务员拜访客户汇总数据Count
     *
     * @param pageMap
     * @return
     * @throws Exception
     * @author xuxin
     * @date May 10, 2017
     */
    int getPersonReportCount(PageMap pageMap) throws Exception;
}
