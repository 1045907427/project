package com.hd.agent.basefiles.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.basefiles.model.AdjustPriceDetail;

public interface AdjustPriceDetailMapper {
	/**
	 * 添加商品调价单明细
	 * @param adjustPriceDetail
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int addAdjustPriceDetail(AdjustPriceDetail adjustPriceDetail);
	/**
	 * 删除调价单商品明细
	 * @param id
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int deleteAdjustPriceDetail(@Param("billid")String billid);
	/**
	 * 通过ID获取商品调价单明细
	 * @param billid
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public List<AdjustPriceDetail> getAdjustPriceDetailList(@Param("billid")String billid);
}