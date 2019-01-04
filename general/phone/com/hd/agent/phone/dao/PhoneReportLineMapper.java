package com.hd.agent.phone.dao;

import com.hd.agent.common.util.PageMap;

import java.util.List;
import java.util.Map;

/**
 * Created by luoq on 2016/11/10.
 */
public interface PhoneReportLineMapper {
    /**
     * 查询单据数据
     * @param pageMap
     * @return
     */
    public List getSalesDemandBillReportData(PageMap pageMap);

    /**
     * 查询订单信息
     * @param orderid
     * @return
     */
    public Map getOrderTrackByOrderid(String orderid);
}
