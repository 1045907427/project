package com.hd.agent.sales.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.OweOrder;
import com.hd.agent.sales.model.OweOrderDetail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;


public interface OweOrderMapper {
	/**
	 * 添加到欠货单明细
	 * @param oweOrderDetail
	 * @return
	 * @author wanghongteng 
	 * @date 9,21,2015
	 */
	public int addOweOrderDetail(OweOrderDetail oweOrderDetail);
	/**
	 * 添加欠货单
	 * @param oweOrder
	 * @return
	 * @author wanghongteng 
	 * @date 9,21,2015
	 */
	public int addOweOrder(OweOrder oweOrder);
	/**
	 * 获取欠货单数量
	 * @param pageMap
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	int getSalesOweOrderCount(PageMap pageMap);
	/**
	 * 获取欠货单列表
	 * @param pageMap
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	List getSalesOweOrderList(PageMap pageMap);
	/**
	 * 获取欠货单
	 * @param id
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public OweOrder getOweOrder(String id); 
	/**
	 * 获取欠货单
	 * @param id
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public List<OweOrderDetail> getOweOrderDetail(String id);
	/**
	 * 修改欠货单状态
	 * @param id
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int auditOweOrder(@Param("id")String id,@Param("userid")String userid,@Param("username")String username,@Param("date")Date date,@Param("businessdate")String businessdate);
	/**
	 * 删除欠货单
	 * @param id
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int deleteOweOrder(String id);
	/**
	 * 删除欠货明细单
	 * @param id
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int deleteOweOrderDetail(String id);
	/**
	 * 通过来源单据编号获取欠单明细
	 * @param id
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int getOweOrderDetailByBillno(String id);
	/**
	 * 关闭欠单
	 * @param id
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int closeOweOrder(@Param("id")String id,@Param("date")Date date);
	/**
	 * 获取所有欠货单
	 * @param pageMap
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	List getAllSalesOweOrderList();

	/**
	 * 通过来源单据编号获取欠单明细
	 * @param id
	 * @return
	 * @author wanghongteng
	 * @date Aug 7,2015
	 */
	public int editOrdernumById(@Param("id")Integer id,@Param("ordernum")BigDecimal ordernum);
}