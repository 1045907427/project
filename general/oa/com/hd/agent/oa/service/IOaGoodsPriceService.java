package com.hd.agent.oa.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.model.OaGoodsPrice;
import com.hd.agent.oa.model.OaGoodsPriceDetail;

import java.util.List;

/**
 * Created by limin on 2015/5/27.
 */
public interface IOaGoodsPriceService {
    /**
     * 新增商品调价单
     * @return
     * @author lin_xx
     * @date 2015-6-1
     */
    public int insertOaGoodsPrice(OaGoodsPrice price, List list) throws Exception;
    /**
     * 查看商品调价单
     * @return
     * @author lin_xx
     * @date 2015-6-2
     */
    public OaGoodsPrice selectOaGoodsPrice(String id) throws Exception;
    /**
     * 查看商品调价单明细
     * @return
     * @author lin_xx
     * @date 2015-6-2
     */
    public PageData selectGoodsDetailList(PageMap pageMap) throws Exception;

    /**
     * 修改商品调价单
     * @return
     * @author lin_xx
     * @date 2015-6-3
     */
    public int editOaGoodsPrice(OaGoodsPrice price, List list)throws Exception;

    /**
     * 根据编号返回修改的商品信息
     * @param billid
     * @return
     */
    public List<OaGoodsPriceDetail> selectOaGoodsPriceDetailListByBillid(String billid);
}
