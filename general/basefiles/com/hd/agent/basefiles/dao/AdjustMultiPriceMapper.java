package com.hd.agent.basefiles.dao;

import com.hd.agent.basefiles.model.AdjustMultiPrice;
import com.hd.agent.basefiles.model.AdjustMultiPriceDetail;
import com.hd.agent.common.util.PageMap;

import java.util.List;


public interface AdjustMultiPriceMapper {

    /**
    * 查询商品多价调整单数据
    * @author lin_xx
    * @date 2017/3/20
    */
    List<AdjustMultiPrice> getAdjustMultiPriceListData(PageMap pageMap);
    /**
     * 查询商品多价调整单数据 条数
     * @author lin_xx
     * @date 2017/3/20
     */
    int getAdjustMultiPriceListDataCount(PageMap pageMap);

    /**
     * 根据主键删除商品多价调整单
     */
    int deleteAdjustMultiPriceById(String id);

    /**
     * 保存商品多价调整单
     */
    int addAdjustMultiPrice(AdjustMultiPrice record);

    /**
     * 根据主键查询商品多价调整单
     */
    AdjustMultiPrice getAdjustMultiPriceById(String id);

    /**
     * 根据主键更新商品多价调整单
     */
    int updateAdjustMultiPrice(AdjustMultiPrice record);
    /**
     * 根据主键删除商品调价明细
     */
    int deleteMultiPriceDetailByBillid(String billid);

    /**
     * 保存商品调价明细
     */
    int addMultiPriceDetail(AdjustMultiPriceDetail record);

    /**
     * 根据主键查询商品调价明细
     */
    List<AdjustMultiPriceDetail> getMultiPriceDetailByBillid(String billid);

    /**
     * 根据主键更新商品调价明细
     */
    int updateMultiPriceDetail(AdjustMultiPriceDetail record);

}