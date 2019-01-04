package com.hd.agent.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.model.OaAccessGoodsAmount;

public interface OaAccessGoodsAmountMapper {
	
	/**
	 * 费用通路单申请单数量信息登录
	 * @param amount
	 * @return
	 * @author limin 
	 * @date 2014-9-29
	 */
	public int insertOaAccessGoodsAmount(OaAccessGoodsAmount amount);
	
	/**
	 * 费用通路单申请单数量信息删除
	 * @param billid
	 * @return
	 * @author limin 
	 * @date 2014-9-30
	 */
	public int deleteOaAccessGoodsAmount(@Param("billid") String billid);
	
	/**
	 * 费用通路单申请单数量信息List
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2014-9-30
	 */
	public List selectOaAccessGoodsAmountList(PageMap map);
	
	/**
	 * 费用通路单申请单数量信息List数量
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2014-9-30
	 */
	public int selectOaAccessGoodsAmountListCnt(PageMap map);

    /**
     *
     * @param billid
     * @return
     * @author limin
     * @date Jun 22, 2015
     */
    public List selectOaAccessGoodsAmountList2(@Param("billid") String billid);
}