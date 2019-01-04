package com.hd.agent.phone.service;

import com.hd.agent.common.util.PageMap;

import java.util.List;
import java.util.Map;

/**
 * Created by luoq on 2016/11/10.
 */
public interface IPhoneReportLineService {
    /**
     * 查询单据数据
     * @param pageMap
     * @return
     * @throws Exception
     */
    public List getSalesDemandBillReportData(PageMap pageMap) throws Exception;

    /**
     * 查询单据信息
     * @param map
     * @return
     * @throws Exception
     */
    public Map getDemandBillOrderTrack(Map map) throws Exception;
}
