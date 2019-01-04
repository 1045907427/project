/**
 * @(#)OaAccessServiceImpl.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-9-25 limin 创建版本
 */
package com.hd.agent.oa.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.CustomerPrice;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.GoodsInfo_PriceInfo;
import com.hd.agent.basefiles.model.TaxType;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.dao.OaAccessAuditinfoMapper;
import com.hd.agent.oa.dao.OaAccessBrandDiscountMapper;
import com.hd.agent.oa.dao.OaAccessGoodsAmountMapper;
import com.hd.agent.oa.dao.OaAccessGoodsPriceMapper;
import com.hd.agent.oa.dao.OaAccessInvoiceBrandMapper;
import com.hd.agent.oa.dao.OaAccessInvoiceMapper;
import com.hd.agent.oa.dao.OaAccessMapper;
import com.hd.agent.oa.dao.OaAccessResultMapper;
import com.hd.agent.oa.model.OaAccess;
import com.hd.agent.oa.model.OaAccessBrandDiscount;
import com.hd.agent.oa.model.OaAccessGoodsAmount;
import com.hd.agent.oa.model.OaAccessGoodsPrice;
import com.hd.agent.oa.model.OaAccessInvoice;
import com.hd.agent.oa.service.IOaAccessService;
import com.hd.agent.sales.model.OrderDetail;
import com.hd.agent.sales.service.IOrderService;

/**
 * 费用通路单Service实现类
 * 
 * @author limin
 */
public class OaAccessServiceImpl extends BaseFilesServiceImpl implements IOaAccessService {

	private OaAccessAuditinfoMapper oaAccessAuditinfoMapper ;

	private OaAccessBrandDiscountMapper oaAccessBrandDiscountMapper ;

	private OaAccessGoodsAmountMapper oaAccessGoodsAmountMapper ;

	private OaAccessGoodsPriceMapper oaAccessGoodsPriceMapper ;

	private OaAccessInvoiceBrandMapper oaAccessInvoiceBrandMapper ;

	private OaAccessInvoiceMapper oaAccessInvoiceMapper ;

	private OaAccessMapper oaAccessMapper ;

	private OaAccessResultMapper oaAccessResultMapper ;
	
	private IOrderService baseFilesServiceImpl;

	public OaAccessAuditinfoMapper getOaAccessAuditinfoMapper() {
		return oaAccessAuditinfoMapper;
	}

	public void setOaAccessAuditinfoMapper(
			OaAccessAuditinfoMapper oaAccessAuditinfoMapper) {
		this.oaAccessAuditinfoMapper = oaAccessAuditinfoMapper;
	}

	public OaAccessBrandDiscountMapper getOaAccessBrandDiscountMapper() {
		return oaAccessBrandDiscountMapper;
	}

	public void setOaAccessBrandDiscountMapper(
			OaAccessBrandDiscountMapper oaAccessBrandDiscountMapper) {
		this.oaAccessBrandDiscountMapper = oaAccessBrandDiscountMapper;
	}

	public OaAccessGoodsAmountMapper getOaAccessGoodsAmountMapper() {
		return oaAccessGoodsAmountMapper;
	}

	public void setOaAccessGoodsAmountMapper(
			OaAccessGoodsAmountMapper oaAccessGoodsAmountMapper) {
		this.oaAccessGoodsAmountMapper = oaAccessGoodsAmountMapper;
	}

	public OaAccessGoodsPriceMapper getOaAccessGoodsPriceMapper() {
		return oaAccessGoodsPriceMapper;
	}

	public void setOaAccessGoodsPriceMapper(
			OaAccessGoodsPriceMapper oaAccessGoodsPriceMapper) {
		this.oaAccessGoodsPriceMapper = oaAccessGoodsPriceMapper;
	}

	public OaAccessInvoiceBrandMapper getOaAccessInvoiceBrandMapper() {
		return oaAccessInvoiceBrandMapper;
	}

	public void setOaAccessInvoiceBrandMapper(
			OaAccessInvoiceBrandMapper oaAccessInvoiceBrandMapper) {
		this.oaAccessInvoiceBrandMapper = oaAccessInvoiceBrandMapper;
	}

	public OaAccessInvoiceMapper getOaAccessInvoiceMapper() {
		return oaAccessInvoiceMapper;
	}

	public void setOaAccessInvoiceMapper(OaAccessInvoiceMapper oaAccessInvoiceMapper) {
		this.oaAccessInvoiceMapper = oaAccessInvoiceMapper;
	}

	public OaAccessMapper getOaAccessMapper() {
		return oaAccessMapper;
	}

	public void setOaAccessMapper(OaAccessMapper oaAccessMapper) {
		this.oaAccessMapper = oaAccessMapper;
	}

	public OaAccessResultMapper getOaAccessResultMapper() {
		return oaAccessResultMapper;
	}

	public void setOaAccessResultMapper(OaAccessResultMapper oaAccessResultMapper) {
		this.oaAccessResultMapper = oaAccessResultMapper;
	}

	public IOrderService getBaseFilesServiceImpl() {
		return baseFilesServiceImpl;
	}

	public void setBaseFilesServiceImpl(IOrderService baseFilesServiceImpl) {
		this.baseFilesServiceImpl = baseFilesServiceImpl;
	}

	@Override
	public OrderDetail getGoodsPrice(String customerId, String goodsId) throws Exception {
		
		GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsId); //商品信息
		OrderDetail orderDetail = new OrderDetail();
		
		if(goodsInfo != null){
			BigDecimal rate = new BigDecimal(1);
			TaxType taxType = getTaxType(goodsInfo.getDefaulttaxtype()); //获取默认税种
			if(taxType != null){
				orderDetail.setTaxtype(taxType.getId()); //税种档案中的编码
				orderDetail.setTaxtypename(taxType.getName()); //税种名称
				rate = taxType.getRate().divide(new BigDecimal(100));
			}
			
			CustomerPrice customerPrice = getCustomerPrice(customerId, goodsId);
			if(customerPrice != null){ //取合同价
				BigDecimal customerTaxPrice = customerPrice.getPrice();
				if(customerTaxPrice == null){
					customerTaxPrice = new BigDecimal(0);
				}
				orderDetail.setFixprice(customerTaxPrice);
				orderDetail.setTaxprice(customerTaxPrice);
				orderDetail.setNotaxprice(customerTaxPrice.divide(rate.add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP));
				orderDetail.setRemark("");
			}
			else{
				GoodsInfo_PriceInfo priceInfo = getPriceInfo(goodsId, customerId); //客户的价格套信息
				if(priceInfo != null){ //如果客户设置了价格套信息，则取价格套信息中的价格
					orderDetail.setFixprice(priceInfo.getTaxprice());
					orderDetail.setTaxprice(priceInfo.getTaxprice()); //从价格套中取含税价格
					orderDetail.setNotaxprice(priceInfo.getTaxprice().divide(rate.add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP)); //从价格套中取无税价格
					orderDetail.setRemark("");
				}
				else{
					BigDecimal baseTaxPrice = goodsInfo.getBasesaleprice(); //取基准销售价
					if(baseTaxPrice == null){
						baseTaxPrice = new BigDecimal(0);
					}
					orderDetail.setFixprice(baseTaxPrice);
					orderDetail.setTaxprice(baseTaxPrice);
					orderDetail.setNotaxprice(baseTaxPrice.divide(rate.add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP));
					orderDetail.setRemark("");
				}
			}

			/////////////////////////
			// TODO
			/////////////////////////
			/*
			List<GoodsInfo_MteringUnitInfo> list = getBaseFilesGoodsMapper().getMUListByGoodsId(goodsId);
			GoodsInfo_MteringUnitInfo auxUnitInfo = null; //默认辅计量
			for(GoodsInfo_MteringUnitInfo info : list){ //查找默认辅计量单位
				if("1".equals(info.getIsdefault())){
					auxUnitInfo = info;
					break;
				}
			}
			if(auxUnitInfo == null && list.size() > 0){ //如果没有设置默认辅单位，则设第一个辅单位为默认
				auxUnitInfo = list.get(0);
			}
			if(auxUnitInfo != null){ //如果商品没有设置辅计量单位，则不设置辅计量单位信息
				orderDetail.setAuxunitid(auxUnitInfo.getMeteringunitid()); //辅计量单位编码
				Map map = countGoodsInfoNumber(goodsId, auxUnitInfo.getMeteringunitid(), new BigDecimal(num));
				if(map.containsKey("auxunitname")){
					orderDetail.setAuxunitname(map.get("auxunitname").toString()); //辅计量单位名称
				}
				if(map.containsKey("auxnumdetail")){
					orderDetail.setAuxnumdetail(map.get("auxnumdetail").toString()); //辅单位数量描述(辅单位数量+辅单位+主单位余数+主单位)
				}
				if(map.containsKey("auxremainder")){
					orderDetail.setOvernum(new BigDecimal(map.get("auxremainder").toString())); //辅单位数量
				}
				if(map.containsKey("auxInteger")){
					orderDetail.setAuxnum(new BigDecimal(map.get("auxInteger").toString())); //主单位余数
				}
			}
			orderDetail.setGoodsInfo(goodsInfo);
			*/
			/////////////////////////////////
			
		}
		return orderDetail;
	}

	@Override
	public boolean addOaAccess(OaAccess access,
			List<OaAccessGoodsPrice> priceList,
			List<OaAccessGoodsAmount> amountList,
			List<OaAccessBrandDiscount> discountList,
			OaAccessInvoice invoice) {

		int ret = oaAccessMapper.insertOaAccess(access);
//		invoice.setBillid(access.getId());
//		if(StringUtils.isEmpty(invoice.getPayamount())) {
//			invoice.setPayamount(null);
//		}
//		oaAccessInvoiceMapper.insertOaAccessInvoice(invoice);

		for(OaAccessGoodsPrice price : priceList) {
			
			if(StringUtils.isEmpty(price.getGoodsname())) {
				
				continue;
			}
			
			price.setBillid(access.getId());
			if(StringUtils.isNotEmpty(price.getRate()) && price.getRate().contains("%")) {
				price.setRate(price.getRate().substring(0, price.getRate().length() - 1));
			}
			if(StringUtils.isEmpty(price.getRate())) {
				
				price.setRate(null);
			}
			if(StringUtils.isEmpty(price.getSenddetail() )) {
				
				price.setSenddetail(null);
			}
			if(StringUtils.isEmpty(price.getSenddetail() )) {
				
				price.setSenddetail(null);
			}
			oaAccessGoodsPriceMapper.insertOaAccessGoodsPrice(price);
		}
		
		for(OaAccessGoodsAmount amount : amountList) {
			
			if(StringUtils.isEmpty(amount.getGoodsname())) {
				
				continue;
			}
			
			amount.setBillid(access.getId());
			oaAccessGoodsAmountMapper.insertOaAccessGoodsAmount(amount);
		}

//		if(!"2".equals(access.getPaytype())) {
//
//			for(OaAccessBrandDiscount discount : discountList) {
//
//				discount.setBillid(access.getId());
//				discount.setCustomerid(access.getCustomerid());
//				discount.setOaid(access.getOaid());
//				if(StringUtils.isEmpty(discount.getBrandid()) || StringUtils.isEmpty(discount.getBrandname())) {
//
//					continue;
//				}
//				oaAccessBrandDiscountMapper.insertOaAccessBrandDiscount(discount);
//			}
//		}
	
		return ret > 0;
	}

	@Override
	public OaAccess selectOaAccessInfo(String id) {

		return oaAccessMapper.selectOaAccessInfo(id);
	}

	@Override
	public PageData selectOaAccessGoodsPriceList(PageMap map) {
		
		List list = oaAccessGoodsPriceMapper.selectOaAccessGoodsPriceList(map);
		int cnt = oaAccessGoodsPriceMapper.selectOaAccessGoodsPriceListCnt(map);

		return new PageData(cnt, list, map);
	}

	@Override
	public PageData selectOaAccessGoodsAmountList(PageMap map) throws Exception {
		
		List list = oaAccessGoodsAmountMapper.selectOaAccessGoodsAmountList(map);
		
		OaAccess access = oaAccessMapper.selectOaAccessInfo((String) map.getCondition().get("billid"));
		
		for(int i = 0; i < list.size(); i++){
			
			OaAccessGoodsAmount amount = (OaAccessGoodsAmount) list.get(i);
			
			if(StringUtils.isEmpty(amount.getGoodsid())) {
				
				continue;
			}
			
			Customer customer = getCustomerByID(access.getCustomerid());
			
			Map param = new HashMap();
			if(!"1".equals(customer.getIslast())){
				param.put("pcustomerid", access.getCustomerid());
			} else {
				param.put("customerid", access.getCustomerid());
			}
			param.put("begindate", access.getConbegindate());
			param.put("enddate", access.getConenddate());
			param.put("goodsid", amount.getGoodsid());
			
			int sales = oaAccessMapper.selectGoodsSalesOut(param);
			int reject = oaAccessMapper.selectGoodsRejectEnter(param);
			sales = (sales - reject) < 0 ? 0 : (sales - reject);
			
			amount.setErpnum(new BigDecimal(sales));
            amount.setDownamount((amount.getDifprice() == null ? BigDecimal.ZERO : amount.getDifprice()).multiply(amount.getErpnum() == null ? BigDecimal.ZERO : amount.getErpnum()));
		}
		
		int cnt = oaAccessGoodsAmountMapper.selectOaAccessGoodsAmountListCnt(map);

		return new PageData(cnt, list, map);
	}

	@Override
	public PageData selectOaAccessBrandDiscountList(PageMap map) {
		
		List list = oaAccessBrandDiscountMapper.selectOaAccessBrandDiscountList(map);
		int cnt = oaAccessBrandDiscountMapper.selectOaAccessBrandDiscountListCnt(map);

		return new PageData(cnt, list, map);
	}

	@Override
	public boolean editOaAccess(OaAccess access,
			List<OaAccessGoodsPrice> priceList,
			List<OaAccessGoodsAmount> amountList,
			List<OaAccessBrandDiscount> discountList,
			OaAccessInvoice invoice) throws Exception {

		int ret = oaAccessMapper.updateOaAccess(access);
//		invoice.setBillid(access.getId());
//		if(StringUtils.isEmpty(invoice.getPayamount())) {
//			invoice.setPayamount(null);
//		}
//		oaAccessInvoiceMapper.updateOaAccessInvoice(invoice);

		oaAccessGoodsPriceMapper.deleteOaAccessGoodsPrice(access.getId());
		for(OaAccessGoodsPrice price : priceList) {
			
			if(StringUtils.isEmpty(price.getGoodsname())) {
				
				continue;
			}
			
			price.setBillid(access.getId());
			if(StringUtils.isNotEmpty(price.getRate()) && price.getRate().contains("%")) {
				price.setRate(price.getRate().substring(0, price.getRate().length() - 1));
			}
			if(StringUtils.isEmpty(price.getRate())) {
				
				price.setRate(null);
			}
			if(StringUtils.isEmpty(price.getSenddetail() )) {
				
				price.setSenddetail(null);
			}
			if(StringUtils.isEmpty(price.getSenddetail() )) {
				
				price.setSenddetail(null);
			}

			oaAccessGoodsPriceMapper.insertOaAccessGoodsPrice(price);
		}
		
		oaAccessGoodsAmountMapper.deleteOaAccessGoodsAmount(access.getId());
		for(OaAccessGoodsAmount amount : amountList) {
			
			if(StringUtils.isEmpty(amount.getGoodsname())) {
				
				continue;
			}

			amount.setBillid(access.getId());
			oaAccessGoodsAmountMapper.insertOaAccessGoodsAmount(amount);
		}

//		oaAccessBrandDiscountMapper.deleteOaAccessBrandDiscount(access.getId());
//
//		if(!"2".equals(access.getPaytype())) {
//
//			for(OaAccessBrandDiscount discount : discountList) {
//
//				discount.setBillid(access.getId());
//				discount.setCustomerid(access.getCustomerid());
//				discount.setOaid(access.getOaid());
//				if(StringUtils.isEmpty(discount.getBrandid()) || StringUtils.isEmpty(discount.getBrandname())) {
//
//					continue;
//				}
//				oaAccessBrandDiscountMapper.insertOaAccessBrandDiscount(discount);
//			}
//		}
	
		return ret > 0;
	}

	@Override
	public OaAccessInvoice selectOaAccessInvoice(String billid) {

		return oaAccessInvoiceMapper.selectOaAccessInvoice(billid);
	}

	@Override
	public List selectOaAccessBrandDiscountList(String billid) {

		return oaAccessBrandDiscountMapper.selectOaAccessBrandDiscountList2(billid);
	}

	@Override
	public List selectOaAccessGoodsPriceList(String billid) {

		return oaAccessGoodsPriceMapper.selectOaAccessGoodsPriceList2(billid);
	}

    @Override
    public Map getErpNum(String goodsid, String customerid, String startdate, String enddate) throws Exception {

        if(StringUtils.isEmpty(goodsid)
                || StringUtils.isEmpty(customerid)
                || StringUtils.isEmpty(startdate)
                || StringUtils.isEmpty(enddate)) {

//            return new HashMap();
            return null;
        }

        Customer customer = getCustomerByID(customerid);

        if(customer == null) {

//            return new HashMap();
            return null;
        }

        Map param = new HashMap();
        if(!"1".equals(customer.getIslast())){
            param.put("pcustomerid", customerid);
        } else {
            param.put("customerid", customerid);
        }
        param.put("begindate", startdate);
        param.put("enddate", enddate);
        param.put("goodsid", goodsid);

        int sales = oaAccessMapper.selectGoodsSalesOut(param);
        int reject = oaAccessMapper.selectGoodsRejectEnter(param);
        sales = (sales - reject) < 0 ? 0 : (sales - reject);

        Map result = new HashMap();
        result.put("sales", sales);

        return result;
    }
}

