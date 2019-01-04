/**
 * @(#)IOaAccessService.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-9-25 limin 创建版本
 */
package com.hd.agent.oa.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.model.OaAccess;
import com.hd.agent.oa.model.OaAccessBrandDiscount;
import com.hd.agent.oa.model.OaAccessGoodsAmount;
import com.hd.agent.oa.model.OaAccessGoodsPrice;
import com.hd.agent.oa.model.OaAccessInvoice;
import com.hd.agent.sales.model.OrderDetail;

/**
 * 费用通路单Service
 * 
 * @author limin
 */
public interface IOaAccessService {

	/**
	 * 获取商品原价
	 * @param customerId
	 * @param goodsId
	 * @return
	 * @author limin 
	 * @date 2014-9-26
	 */
	public OrderDetail getGoodsPrice(String customerId, String goodsId) throws Exception;
	
	/**
	 * 添加申请单
	 * @param access 通路单申请单
	 * @param priceList 申请单价格变更信息List
	 * @param amountList 申请单差价信息List
	 * @param discountList 折扣信息List
	 * @param invoice 支付信息
	 * @return
	 * @author limin 
	 * @date 2014-9-29
	 */
	public boolean addOaAccess(OaAccess access,
                               List<OaAccessGoodsPrice> priceList,
                               List<OaAccessGoodsAmount> amountList,
                               List<OaAccessBrandDiscount> discountList,
                               OaAccessInvoice invoice);
	
	/**
	 * 修改申请单
	 * @param access 通路单申请单
	 * @param priceList 申请单价格变更信息List
	 * @param amountList 申请单差价信息List
	 * @param discountList 折扣信息List
	 * @param invoice 支付信息
	 * @return
	 * @author limin 
	 * @date 2014-10-5
	 */
	public boolean editOaAccess(OaAccess access,
                                List<OaAccessGoodsPrice> priceList,
                                List<OaAccessGoodsAmount> amountList,
                                List<OaAccessBrandDiscount> discountList,
                                OaAccessInvoice invoice) throws Exception;
	
	/**
	 * 查询申请单
	 * @param id
	 * @return
	 * @author limin 
	 * @date 2014-9-30
	 */
	public OaAccess selectOaAccessInfo(String id);
	
	/**
	 * 根据单号查询申请单价格变更信息List
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2014-9-30
	 */
	public PageData selectOaAccessGoodsPriceList(PageMap map);
	
	/**
	 * 根据单号查询申请单差价金额信息List
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2014-9-30
	 */
	public PageData selectOaAccessGoodsAmountList(PageMap map) throws Exception;
	
	/**
	 * 根据单号查询申请单品牌折扣信息List
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2014-9-30
	 */
	public PageData selectOaAccessBrandDiscountList(PageMap map);
	
	/**
	 * 
	 * @param billid
	 * @return
	 * @author limin 
	 * @date 2014-10-7
	 */
	public OaAccessInvoice selectOaAccessInvoice(String billid);
	
	/**
	 * 
	 * @param billid
	 * @return
	 */
	public List selectOaAccessBrandDiscountList(String billid);
	
	/**
	 * 
	 * @param billid
	 * @return
	 */
	public List selectOaAccessGoodsPriceList(String billid);

    /**
     * 获取ERP数量
     * @param goodsid
     * @param customerid
     * @param startdate
     * @param enddate
     * @return
     * @author limin
     * @date Aug 11, 2015
     */
    public Map getErpNum(String goodsid, String customerid, String startdate, String enddate) throws Exception;
}

