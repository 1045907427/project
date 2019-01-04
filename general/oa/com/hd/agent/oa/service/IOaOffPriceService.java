package com.hd.agent.oa.service;

import java.util.List;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.model.OaOffPrice;
import com.hd.agent.oa.model.OaOffPriceDetail;

/**
 * Created by limin on 2015/3/9.
 */
public interface IOaOffPriceService {

    /**
     * 查询批量特价调整单明细List
     * @param map
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 9, 2015
     */
    public PageData selectOaOffPriceDetailList(PageMap map) throws Exception;

    /**
     * 新增批量特价调整单
     * @param price
     * @param list
     * @return
     * @author limin
     * @date Mar 10, 2015
     */
    public int addOaOffPrice(OaOffPrice price, List<OaOffPriceDetail> list) throws Exception;

    /**
     * 修改批量特价调整单
     * @param price
     * @param list
     * @return
     * @author limin
     * @date Mar 10, 2015
     */
    public int editOaOffPrice(OaOffPrice price, List<OaOffPriceDetail> list) throws Exception;

    /**
     * 查询批量特价申请单
     * @param id
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 10, 2015
     */
    public OaOffPrice selectOaOffPrice(String id) throws Exception;

    /**
     * 查询批量特价调整单明细List
     * @param billid
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 10, 2015
     */
    public List<OaOffPriceDetail> selectOaOffPriceDetailListByBillid(String billid) throws Exception;

    /**
     * 查询商品
     *
     * @param pageMap
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 30, 2015
     */
    public PageData getGoodsList(PageMap pageMap) throws Exception;
}
