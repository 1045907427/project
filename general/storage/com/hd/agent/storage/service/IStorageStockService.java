package com.hd.agent.storage.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by luoq on 2018/1/26.
 */
public interface IStorageStockService {
    /**
     * 获取成本异常报表数据
     * @param pageMap
     * @return com.hd.agent.common.util.PageData
     * @throws
     * @author luoqiang
     * @date Jan 26, 2018
     */
    public PageData getAbnormalBillReportData(PageMap pageMap) throws Exception;

    /**
     * 商品成本结算
     * @param map
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Jan 29, 2018
     */
    public Map insertCostReportData(Map map) throws Exception;

    /**
     * 获取存货明细数据
     * @param pageMap
     * @return com.hd.agent.common.util.PageData
     * @throws
     * @author luoqiang
     * @date Jan 30, 2018
     */
    public PageData showCostAccountDetailData(PageMap pageMap) throws Exception;

    /**
     * 获取库存成本调整后的单价
     * @param pageMap
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Mar 22, 2018
     */
    public Map getStorageAfterChangePrice(PageMap pageMap) throws Exception;

    /**
     * 库存成本调整新增出入库单据
     * @param storageid 仓库编码
     * @param goodsid 商品编码
     * @param changedate 业务日期
     * @param changeamount 调整金额
     * @param isAcountCostPrice 是否重新结算
     * @param map 重新结算成本的参数
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Mar 22, 2018
     */
    public Map addChangeBill(String storageid, String goodsid, String changedate, BigDecimal changeamount,String isAcountCostPrice,Map map) throws Exception;
}
