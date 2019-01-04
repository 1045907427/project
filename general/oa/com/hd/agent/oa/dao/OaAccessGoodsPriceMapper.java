package com.hd.agent.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.model.OaAccessGoodsPrice;

public interface OaAccessGoodsPriceMapper {
	
	/**
	 * 费用通路单申请单价格变更信息登录
	 * @param price
	 * @return
	 * @author limin 
	 * @date 2014-9-29
	 */
	public int insertOaAccessGoodsPrice(OaAccessGoodsPrice price);
	
	/**
	 * 查询商品价格变更信息List
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2014-9-30
	 */
	public List selectOaAccessGoodsPriceList(PageMap map);
	
	/**
	 * 查询商品价格变更信息List数量
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2014-9-30
	 */
	public int selectOaAccessGoodsPriceListCnt(PageMap map);
	
	/**
	 * 费用通路单申请单价格变更信息删除
	 * @param billid
	 * @return
	 * @author limin 
	 * @date 2014-9-30
	 */
	public int deleteOaAccessGoodsPrice(@Param("billid") String billid);

    /**
     *
     * @param billid
     * @return
     */
    public List selectOaAccessGoodsPriceList2(@Param("billid") String billid);
}