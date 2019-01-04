package com.hd.agent.hr.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

public interface IStatisticService {

    /**
     * 查询请假数据
     * @param map
     * @return
     * @throws Exception
     * @author limin
     * @date Jun 30, 2015
     */
    public PageData selectRestList(PageMap map) throws Exception;
    /**
     * 查询加班数据
     * @param map
     * @return
     * @throws Exception
     * @author limin
     * @date Jul 1, 2015
     */
    public PageData selectOvertimeList(PageMap map) throws Exception;
}
