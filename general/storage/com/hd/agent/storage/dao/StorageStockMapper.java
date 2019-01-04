package com.hd.agent.storage.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.StorageStockSum;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by luoq on 2018/1/26.
 */
public interface StorageStockMapper {
    /**
     * 获取成本异常数据
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Jan 26, 2018
     */
    public List getAbnormalBillReportList(PageMap pageMap);

    /**
     * 获取成本异常数据
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Jan 26, 2018
     */
    public int getAbnormalBillReportCount(PageMap pageMap);

    /**
     * 添加结算合计数据
     * @param list
     * @return int
     * @throws
     * @author luoqiang
     * @date Jan 29, 2018
     */
    public int insertCostAccountSum(StorageStockSum storageStockSum);

    /**
     * 获取存货明细数据
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Jan 29, 2018
     */
    public List getAbnormalBillDetailList(PageMap pageMap);

    /**
     * 添加存货明细数据
     * @param pageMap
     * @return int
     * @throws
     * @author luoqiang
     * @date Jan 29, 2018
     */
    public int insertStorageStockDetailList(PageMap pageMap);

    /**
     * 更新存货明细数据结算成本价
     * @param accountid
     * @return int
     * @throws
     * @author luoqiang
     * @date Jan 29, 2018
     */
    public int updateStorageAccountDetailCostPrice(@Param("accountid") String accountid);

    /**
     * 更新来源单据成本价
     * @param accountid
     * @return int
     * @throws
     * @author luoqiang
     * @date Jan 29, 2018
     */
    public int updateSourceBillCostPrice(@Param("accountid") String accountid);

    /**
     * 更新库存成本价
     * @param accountid
     * @return int
     * @throws
     * @author luoqiang
     * @date Jan 29, 2018
     */
    public int updateStorageSummaryCostprice(@Param("accountid") String accountid);

    /**
     * 添加商品成本变更记录
     * @param accountid
     * @return int
     * @throws
     * @author luoqiang
     * @date Jan 29, 2018
     */
    public int insertGoodsCostPriceChange(@Param("accountid") String accountid);

    /**
     * 删除临时新增的存货明细数据
     * @param accountid
     * @return int
     * @throws
     * @author luoqiang
     * @date Jan 29, 2018
     */
    public int deleteCostAccountDetailByAccountid(@Param("accountid") String accountid);

    /**
     * 删除存货明细合计数据
     * @param accountid
     * @return int
     * @throws
     * @author luoqiang
     * @date Jan 22, 2018
     */
    public int deleteCostAccountSumByAccountid(String accountid);

    /**
     * 获取成本结算报表明细数据
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Jan 19, 2018
     */
    public List getCostAccountDetailList(PageMap pageMap);

    /**
     * 获取生成的存货明细数据
     * @param pageMap
     * @return int
     * @throws
     * @author luoqiang
     * @date Jan 24, 2018
     */
    public int getCostAccountDetailCount(PageMap pageMap);

    /**
     * 获取存货明细合计数据
     * @param pageMap
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Jan 25, 2018
     */
    public Map getCostAccountDetailSum(PageMap pageMap);

    /**
     * 判断业务日期内的单据是否审核通过，有未审核通过的就不允许结算
     * @param map
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Jan 19, 2018
     */
    public Map checkBillForAccount(Map map);
}
