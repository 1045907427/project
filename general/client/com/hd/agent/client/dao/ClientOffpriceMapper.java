package com.hd.agent.client.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.client.model.ClientOffprice;
import com.hd.agent.common.util.PageMap;


public interface ClientOffpriceMapper {
	/**
	 * datagrid数据查询
	 * @param pageMap
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月1日
	 */
	public List<ClientOffprice> getOffPriceList(PageMap pageMap);
	
	/**
	 * datagrid数量查询
	 * @param pageMap
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月1日
	 */
	public int getOffPriceCount(PageMap pageMap);

	/**
	 * 添加特价商品
	 * @param info
	 * @author huangzhiqian
	 * @date 2015年12月1日
	 */
	public int insertOffPriceGoods(ClientOffprice info);
	/**
	 * 查询特价商品
	 * @param deptid 部门编号
	 * @param goodsid 商品编号
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月1日
	 */
	public List<ClientOffprice> findOffPriceGoodsByGoodsIdAndDeptId(@Param("deptid")String deptid, @Param("goodsid")String goodsid);
	/**
	 * 更新特价商品信息
	 * @param info
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月1日
	 */
	public int updateOffPriceGoodsInfo(ClientOffprice info);
	
	/**
	 * 根据门店编号查询特价商品
	 * @param deptid
	 * @param goodsid
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月2日
	 */
	public List<ClientOffprice> selectOffPriceGoodsByDeptId(@Param("deptid")String deptid, @Param("goodsid")String goodsid);
	/**
	 * 日志数据查询
	 * @param pageMap
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月7日
	 */
	public List<ClientOffprice> getOffPriceLogList(PageMap pageMap);
	/**
	 * 日志数量
	 * @param pageMap
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月7日
	 */
	public int getOffPriceLogCount(PageMap pageMap);

	/**
	 * 根据编码查询特价
	 * @param id
	 * @return
	 * @author limin
	 * @date Apr 5, 2016
	 */
	public ClientOffprice selectClientOffPriceById(@Param("id") String id);

}