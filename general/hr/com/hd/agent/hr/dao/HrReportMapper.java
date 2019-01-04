/**
 * @(#)HrReportMapper.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015-9-1 limin 创建版本
 */
package com.hd.agent.hr.dao;

import com.hd.agent.common.util.PageMap;

import java.util.List;
import java.util.Map;

/**
 * Created by limin on 2016/9/1.
 */
public interface HrReportMapper {

    /**
     * 查询签到报表数据
     *
     * @param pageMap
     * @return
     * @author limin
     * @date Set 1, 2016
     */
    public List<Map> getSignReportDataList(PageMap pageMap);

    /**
     * 查询签到报表count
     *
     * @param pageMap
     * @return
     * @author limin
     * @date Set 1, 2016
     */
    public int getSignReportDataCount(PageMap pageMap);
}
