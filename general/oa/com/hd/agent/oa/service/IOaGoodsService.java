/**
 * @(#)IOaGoodsService.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-7-9 limin 创建版本
 */
package com.hd.agent.oa.service;

import java.util.List;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.model.OaGoods;

/**
 * 
 * 
 * @author limin
 */
public interface IOaGoodsService {

	/**
	 * 添加新品申请单
	 * @param goods
	 * @param list
	 * @return
	 * @author limin 
	 * @date 2014-7-15
	 */
	public int addGoods(OaGoods goods, List list);
	
	/**
	 * 
	 * @param id
	 * @return
	 * @author limin 
	 * @date 2014-7-16
	 */
	public OaGoods selectOaGoods(String id);
	
	/**
	 * 查询对应的新品登录单明细数据
	 * @param pageMap
	 * @return
	 * @author limin 
	 * @date 2014-7-16
	 */
	public PageData selectGoodsDetailList(PageMap pageMap);
	
	/**
	 * 修改新品申请单
	 * @param goods
	 * @param list
	 * @return
	 * @author limin 
	 * @date 2014-7-25
	 */
	public int editGoods(OaGoods goods, List list);
	
	/**
	 * 查询该商品编号是否已存在
	 * @param goodsid
     * @param billid
	 * @return
	 * @author limin 
	 * @date 2014-7-30
	 */
	public int selectExistedGoodsid(String goodsid, String billid);
	
	/**
	 * 查询该商品名称是否已存在
	 * @param goodsname
	 * @return
	 * @author limin 
	 * @date 2014-7-31
	 */
	public int selectExistedGoodsname(String goodsname);
	
	/**
	 * 查询该条形码是否已存在
	 * @param barcode
	 * @return
	 * @author limin 
	 * @date 2014-7-31
	 */
	public int selectExistedBarcode(String barcode);
	
	/**
	 * 查询该箱装条形码是否已存在
	 * @param boxbarcode
	 * @return
	 * @author limin 
	 * @date 2014-7-31
	 */
	public int selectExistedBoxbarcode(String boxbarcode);
}

