package com.hd.agent.purchase.service.ext;

import java.util.Map;

/**
 * Created by limin on 2016/10/28.
 */
public interface IPurchaseForSalesService {

    /**
     * 获取最新批次价
     *
     * @param goodsid
     * @param batchno
     * @return
     * @author limin
     * @date Oct 28, 2016
     */
    public Map selectLastestBuyPriceByGoodsAndBatchno(String goodsid, String batchno);

}
