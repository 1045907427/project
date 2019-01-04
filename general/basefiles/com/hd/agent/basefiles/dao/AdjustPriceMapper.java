package com.hd.agent.basefiles.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.basefiles.model.AdjustPrice;
import com.hd.agent.common.util.PageMap;

public interface AdjustPriceMapper {
	/**
	 * 获取调价单数量
	 * @param pageMap
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	int getAdjustPriceListCount(PageMap pageMap);
	/**
	 * 获取到调价单列表
	 * @param pageMap
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	List getAdjustPriceList(PageMap pageMap);
	/**
	 * 添加商品调价单
	 * @param adjustPrice
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int addAdjustPrice(AdjustPrice adjustPrice);
	/**
	 * 通过ID获取调价单
	 * @param id
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public AdjustPrice getAdjustPriceByID(@Param("id")String id);
	/**
	 * 审核商品调价单
	 * @param id userid username date
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int auditAdjustPrice(@Param("id")String id,@Param("userid")String userid,@Param("username")String username,@Param("date")Date date,@Param("businessdate")String businessdate);
	/**
	 * 修改商品调价单
	 * @param adjustPrice
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int editAdjustPrice(AdjustPrice adjustPrice);
	/**
	 * 删除商品调价单
	 * @param id
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int deleteAdjustPrice(@Param("id")String id);
	/**
	 * 反审商品调价单
	 * @param id userid username date
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int oppauditAdjustPrice(@Param("id")String id,@Param("userid")String userid,@Param("username")String username,@Param("date")Date date);
	/**
	 * 获取调价单导出列表
	 * @param pageMap
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public List getAdjustPriceExportList(PageMap pageMap);
}