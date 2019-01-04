/**
 * @(#)OaGoodsDetailMapper.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-7-9 limin 创建版本
 */
package com.hd.agent.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.model.OaGoodsDetail;

/**
 * 
 * 
 * @author limin
 */
public interface OaGoodsDetailMapper {

    /**
     *
     * @param detail
     * @return
     */
	public int insertOaGoodsDetail(OaGoodsDetail detail);
	
	/**
	 * 查询
	 * @param pageMap
	 * @return
	 * @author limin 
	 * @date 2014-7-25
	 */
	public List selectGoodsDetailList(PageMap pageMap);
	
	/**
	 * 查询新品登录单商品明细数目
	 * @param pageMap
	 * @return
	 * @author limin 
	 * @date 2014-7-25
	 */
	public int selectGoodsDetailListCount(PageMap pageMap);
	
	/**
	 * 根据单据编号删除新品登录单商品明细
	 * @param billid
	 * @return
	 * @author limin 
	 * @date 2014-7-25
	 */
	public int deleteOaGoodsDetailByBillid(@Param("billid") String billid);
	
	/**
	 * 根据单据编号查询商品明细List
	 * @param billid
	 * @return
	 * @author limin 
	 * @date 2014-7-30
	 */
	public List selectGoodsDetailListByBillid(@Param("billid") String billid);
	
	/**
	 * 查询该商品编号是否已存在
	 * @param goodsid
     * @param billid
	 * @return
	 * @author limin 
	 * @date 2014-7-30
	 */
	public int selectExistedGoodsid(@Param("goodsid") String goodsid, @Param("billid") String billid);
	
	/**
	 * 查询该商品名称是否已存在
	 * @param goodsname
	 * @return
	 * @author limin 
	 * @date 2014-7-31
	 */
	public int selectExistedGoodsname(@Param("goodsname") String goodsname);
	
	/**
	 * 查询该条形码是否已存在
	 * @param barcode
	 * @return
	 * @author limin 
	 * @date 2014-7-31
	 */
	public int selectExistedBarcode(@Param("barcode") String barcode);
	
	/**
	 * 查询该箱装条形码是否已存在
	 * @param boxbarcode
	 * @return
	 * @author limin 
	 * @date 2014-7-31
	 */
	public int selectExistedBoxbarcode(@Param("boxbarcode") String boxbarcode);
}

