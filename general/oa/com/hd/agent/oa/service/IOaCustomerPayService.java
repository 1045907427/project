/**
 * @(#)IOaCustomerPayService.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-11-17 limin 创建版本
 */
package com.hd.agent.oa.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.model.OaCustomerPay;
import com.hd.agent.oa.model.OaCustomerPayDetail;

/**
 * 客户费用支付申请单Service
 * 
 * @author limin
 */
public interface IOaCustomerPayService {

	/**
	 * 
	 * @param id
	 * @return
	 * @author limin 
	 * @date 2014-11-18
	 */
	public OaCustomerPay selectCustomerPayInfo(String id);

    /**
     *
     * @param map
     * @return
     * @author limin
     * @date 2014-11-18
     */
    public PageData selectOaCustomerPayDetailList(PageMap map);

    /**
     *
     * @param billid
     * @return
     * @author limin
     * @date 2014-11-18
     */
    public List selectOaCustomerPayDetailList(String billid);

	/**
	 * 添加客户费用支付申请单
	 * @param customerpay
	 * @param detailList
	 * @return
	 * @author limin 
	 * @date 2014-11-18
	 */
	public int addOaCustomerPay(OaCustomerPay customerpay, List<OaCustomerPayDetail> detailList);

	/**
	 * 添加客户费用支付申请单
	 * @param customerpay
	 * @param detailList
	 * @return
	 * @author limin 
	 * @date 2014-11-18
	 */
	public int editOaCustomerPay(OaCustomerPay customerpay, List<OaCustomerPayDetail> detailList);
	
	/**
	 * 获取品牌对应的销售金额
	 * @param map
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-20
	 */
	public String getCustomerBrandSalesAmount(Map map) throws Exception;
}

