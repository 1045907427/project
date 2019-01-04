/**
 * @(#)OffPriceService.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月1日 huangzhiqian 创建版本
 */
package com.hd.agent.client.service;

import com.hd.agent.client.model.ClientOffprice;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author huangzhiqian
 */
public interface IOffPriceService {

	/**
	 * datagrid查询
	 * @param pageMap
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月1日
	 */
	public PageData getOffPriceList(PageMap pageMap)throws Exception;
	
	/**
	 * excel导入
	 * @param infoList
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月1日
	 */
	public Map addOffPriceForExcel(List<ClientOffprice> infoList) throws Exception;
	/**
	 * 客户端特价商品查询
	 * @param deptid
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月2日
	 */
	public List<ClientOffprice> getOffPriceForClient(String deptid, String goodsid) throws Exception;
	/**
	 * 更新特价商品信息(带校验)
	 * @param clientOffprice
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月4日
	 */
	public boolean updateSaleOffGoods(ClientOffprice clientOffprice)throws Exception;
	
	/**
	 * 新增
	 * @param clientOffprice
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2016年2月3日
	 */
	public boolean addOffPriceGoods(ClientOffprice clientOffprice)throws Exception;

	/**
	 * 根据商品编号,部门编号查询
	 * @param deptid
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2016年2月3日
	 */
	public List<ClientOffprice> selectOffPriceGoodsByGoodsIdAndDeptId(String deptid, String goodsid)throws Exception;

	/**
	 * 导入门店特价
	 * @param list
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Apr 5, 2016
	 */
	public Map importClientOffPrice(List<Map> list) throws Exception;

	/**
	 * 根据编码获取特价情报
	 * @param id
	 * @return
	 * @throws Exception
     */
	public ClientOffprice selectClientOffPriceById(String id) throws Exception;
}

