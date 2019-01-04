package com.hd.agent.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.model.OaAccessBrandDiscount;

public interface OaAccessBrandDiscountMapper {

	/**
	 * 费用通路单申请单品牌折扣信息登录
	 * @param discount
	 * @return
	 * @author limin 
	 * @date 2014-9-29
	 */
	public int insertOaAccessBrandDiscount(OaAccessBrandDiscount discount);
	
	/**
	 * 费用通路单申请单品牌折扣信息删除
	 * @param billid
	 * @return
	 * @author limin 
	 * @date 2014-9-30
	 */
	public int deleteOaAccessBrandDiscount(@Param("billid") String billid);
	
	/**
	 * 查询费用通路单申请单品牌折扣信息List
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2014-9-30
	 */
	public List selectOaAccessBrandDiscountList(PageMap map);
	
	/**
	 * 查询费用通路单申请单品牌折扣信息List数量
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2014-9-30
	 */
	public int selectOaAccessBrandDiscountListCnt(PageMap map);
	
	/**
	 * 查询费用通路单申请单品牌折扣信息List
	 * @param billid
	 * @return
	 */
	public List selectOaAccessBrandDiscountList2(@Param("billid") String billid);
}