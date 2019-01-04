package com.hd.agent.hr.service.impl;

import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.hr.dao.StatisticMapper;
import com.hd.agent.hr.service.IStatisticService;

public class StatisticServiceImpl extends BaseServiceImpl implements IStatisticService {

    private StatisticMapper statisticMapper;

    public StatisticMapper getStatisticMapper() {
        return statisticMapper;
    }

    public void setStatisticMapper(StatisticMapper statisticMapper) {
        this.statisticMapper = statisticMapper;
    }

    @Override
    public PageData selectRestList(PageMap map) throws Exception {

        return new PageData(statisticMapper.selectRestListCnt(map), statisticMapper.selectRestList(map), map); 
    }

    @Override
    public PageData selectOvertimeList(PageMap map) throws Exception {
        return new PageData(statisticMapper.selectOvertimeListCnt(map), statisticMapper.selectOvertimeList(map), map);
    }
}
