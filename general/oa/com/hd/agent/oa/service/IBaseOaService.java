/**
 * @(#)IBaseOaService.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-1-28 limin 创建版本
 */
package com.hd.agent.oa.service;

import com.hd.agent.sales.model.OrderDetail;

/**
 * OA通用Service
 *
 * @author limin
 */
public interface IBaseOaService {

    /**
     * 获取商品原价
     * @param customerId
     * @param goodsId
     * @return
     * @author limin
     * @date Jan 28, 2016
     */
    public OrderDetail getGoodsPrice(String customerId, String goodsId) throws Exception;
}
