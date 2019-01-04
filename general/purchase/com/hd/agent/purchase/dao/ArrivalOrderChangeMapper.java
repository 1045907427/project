package com.hd.agent.purchase.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.purchase.model.ArrivalOrderChange;
import com.hd.agent.purchase.model.ArrivalOrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by luoq on 2018/1/8.
 */
public interface ArrivalOrderChangeMapper {
    /**
     * 添加采购进货单分摊记录
     * @param arrivalOrderChange
     * @return int
     * @throws
     * @author luoqiang
     * @date Jan 08, 2018
     */
    public int insertArrivalOrderChange(ArrivalOrderChange arrivalOrderChange);


    /**
     * 获取采购分摊记录
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Jan 08, 2018
     */
    public List getPurchaseShareLogList(PageMap pageMap);

    /**
     * 获取采购分摊记录
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Jan 08, 2018
     */
    public int getPurchaseShareLogCount(PageMap pageMap);

    /**
     * 清楚费用分摊记录
     * @param id
     * @return int
     * @throws
     * @author luoqiang
     * @date Jan 08, 2018
     */
    public int clearArrivalOrderChangeLog(@Param("id")String id);

    /**
     * 获取采购运费分摊记录条数
     * @param id
     * @return int
     * @throws
     * @author luoqiang
     * @date Jan 08, 2018
     */
    public int getArrivalOrderNum(@Param("id")String id);

    /**
     * 获取采购运费分摊数据
     * @param arrivalOrderDetail
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Jan 08, 2018
     */
    public Map getArrivalPurchaseChangeData(ArrivalOrderDetail arrivalOrderDetail);
}
