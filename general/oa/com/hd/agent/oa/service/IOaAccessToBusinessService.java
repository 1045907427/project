/**
 * @(#)IOAToBusinessService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年10月22日 chenwei 创建版本
 */
package com.hd.agent.oa.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.oa.model.OaAccess;
import com.hd.agent.oa.model.OaAccessBrandDiscount;
import com.hd.agent.oa.model.OaAccessGoodsPrice;

/**
 * 
 * OA费用通路单接口
 * @author chenwei
 */
public interface IOaAccessToBusinessService {
	/**
	 * 通路单普通接口（生成客户应收款冲差单）（支付方式为折扣）
	 * @param oaAccess				OA表单单据信息
	 * @param brandDiscountList		品牌折让列表
	 * @return						返回参数flag true表示成功false 表示失败 
	 * 								msg提示信息、
	 * 								ids生成的相关单据单据编号
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月22日
	 */
	public Map addBusinessBillByOaAccess(OaAccess oaAccess,List<OaAccessBrandDiscount> brandDiscountList) throws Exception;
	/**
	 * 通路单普通接口(生成代垫)
	 * @param oaAccess				OA表单单据信息
	 * @param brandDiscountList		品牌折让列表
	 * @return						返回参数flag true表示成功false 表示失败 
	 * 								msg提示信息、
	 * 								ids生成的相关单据单据编号
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月5日
	 */
	public Map addMatcostsByOaAccess(OaAccess oaAccess, List<OaAccessBrandDiscount> brandDiscountList) throws Exception;
	/**
	 * 通路单降价特价接口（生成特价调整单）
	 * @param oaAccess			OA表单单据信息
	 * @param goodsPricelist	商品价格变更信息
	 * @return					返回参数flag true表示成功false 表示失败 
	 * 							msg提示信息
	 * 							ids生成的相关单据单据编号
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月22日
	 */
	public Map addOffPriceByOaAccess(OaAccess oaAccess, List<OaAccessGoodsPrice> goodsPricelist) throws Exception;
	/**
	 * 删除相关单据(应收款冲差单)
	 * @param oaAccess			OA表单单据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月24日
	 */
	public boolean deleteBusinessBillByOaAccess(OaAccess oaAccess) throws Exception;
	/**
	 * 删除相关单据（代垫）
	 * @param oaAccess			OA表单单据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月5日
	 */
	public boolean deleteMatcostsByOaAccess(OaAccess oaAccess) throws Exception;
	/**
	 * 删除特价调整单
	 * @param oaAccess
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月5日
	 */
	public boolean deleteOffpriceByOaAccess(OaAccess oaAccess) throws Exception;
	/**
	 * 生成客户应付费用
	 * @param oaAccess
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月12日
	 */
	public boolean addCustomerCostPayableByOaAccess(OaAccess oaAccess) throws Exception;
	/**
	 * 删除客户应付费用
	 * @param oaAccess
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月12日
	 */
	public boolean rollbackCustomerCostPayableByOaAccess(OaAccess oaAccess) throws Exception;
	/**
	 * 支付节点后 关闭客户应付费用
	 * @param oaAccess
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月12日
	 */
	public boolean updateCustomerCostPayableColse(OaAccess oaAccess) throws Exception;
	/**
	 * 回滚该节点后 启用客户应付费用
	 * @param oaAccess
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月12日
	 */
	public boolean updateCustomerCostPayableOpen(OaAccess oaAccess) throws Exception;
	
	/**
	 * 查询通路单对应的查询客户应付费用
	 * @param oaid
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-17
	 */
	public List selectCustomerCostPayableByOaid(String oaid) throws Exception;
	
	/**
	 * 查询通路单对应的代垫数据
	 * @param oaid
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-17
	 */
	public List selectMatcostsByOaid(String oaid) throws Exception;
	
	/**
	 * 查询通路单对应的特价调整单
	 * @param oaid
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-17
	 */
	public List selectOffPrice(String oaid) throws Exception;

	/**
	 * 生成客户应付费用(未批准)
	 * @param access
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Sep 29, 2015
	 */
	public boolean addAuthedCustomerFeeByOaAccess(OaAccess access) throws Exception;

	/**
	 * 生成客户应付费用(未批准)
	 * @param access
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Sep 29, 2015
	 */
	public boolean deleteAuthedCustomerFeeByOaAccess(OaAccess access) throws Exception;

	/**
	 * 生成客户应付费用(未批准)
	 * @param access
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Sep 29, 2015
	 */
	public boolean updateAuthedCustomerFeeByOaAccess(OaAccess access) throws Exception;
}

