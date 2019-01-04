/**
 * @(#)OaGoodsServiceImpl.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-7-9 limin 创建版本
 */
package com.hd.agent.oa.service.impl;

import java.util.List;

import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.activiti.dao.ProcessMapper;
import com.hd.agent.basefiles.dao.FinanceMapper;
import com.hd.agent.basefiles.dao.GoodsMapper;
import com.hd.agent.basefiles.dao.StorageMapper;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.dao.OaGoodsDetailMapper;
import com.hd.agent.oa.dao.OaGoodsMapper;
import com.hd.agent.oa.model.OaGoods;
import com.hd.agent.oa.model.OaGoodsDetail;
import com.hd.agent.oa.service.IOaGoodsService;

/**
 * 
 * 
 * @author limin
 */
public class OaGoodsServiceImpl extends BaseFilesServiceImpl implements IOaGoodsService {

	private OaGoodsMapper oaGoodsMapper;
	
	private OaGoodsDetailMapper oaGoodsDetailMapper;
	
	private ProcessMapper processMapper;
	
	private GoodsMapper goodsMapper;
	
	private StorageMapper storageMapper;
	
	private FinanceMapper financeMapper;

	public OaGoodsMapper getOaGoodsMapper() {
		return oaGoodsMapper;
	}

	public void setOaGoodsMapper(OaGoodsMapper oaGoodsMapper) {
		this.oaGoodsMapper = oaGoodsMapper;
	}

	public OaGoodsDetailMapper getOaGoodsDetailMapper() {
		return oaGoodsDetailMapper;
	}

	public void setOaGoodsDetailMapper(OaGoodsDetailMapper oaGoodsDetailMapper) {
		this.oaGoodsDetailMapper = oaGoodsDetailMapper;
	}

	public ProcessMapper getProcessMapper() {
		return processMapper;
	}

	public void setProcessMapper(ProcessMapper processMapper) {
		this.processMapper = processMapper;
	}

	public GoodsMapper getGoodsMapper() {
		return goodsMapper;
	}

	public void setGoodsMapper(GoodsMapper goodsMapper) {
		this.goodsMapper = goodsMapper;
	}

	public StorageMapper getStorageMapper() {
		return storageMapper;
	}

	public void setStorageMapper(StorageMapper storageMapper) {
		this.storageMapper = storageMapper;
	}

	public FinanceMapper getFinanceMapper() {
		return financeMapper;
	}

	public void setFinanceMapper(FinanceMapper financeMapper) {
		this.financeMapper = financeMapper;
	}

	@Override
	public int addGoods(OaGoods goods, List list) {

		int ret = oaGoodsMapper.insertOaGoods(goods);
		
		for(Object o : list) {
			
			OaGoodsDetail detail = (OaGoodsDetail) o;
			if(StringUtils.isEmpty(detail.getGoodsid())) {
				continue;
			}
			detail.setBillid(goods.getId());
			
			oaGoodsDetailMapper.insertOaGoodsDetail(detail);
		}
		return ret;
	}

	@Override
	public OaGoods selectOaGoods(String id) {

		OaGoods goods = oaGoodsMapper.selectOaGoods(id);
		return goods;
	}

	@Override
	public PageData selectGoodsDetailList(PageMap pageMap) {

		List list = oaGoodsDetailMapper.selectGoodsDetailList(pageMap);
		
		/*
		for(Object o : list) {
			
			OaGoodsDetail detail = (OaGoodsDetail) o;
			
			// 品牌名
			String brandid = detail.getBrandid();
			if(StringUtils.isNotEmpty(brandid)) {
				
				Brand brand = goodsMapper.getBrandInfo(brandid);
				if(brand != null) {
					
					detail.setBrandname(brand.getName());
				}
			}
			
			// 商品分类名称
			String goodsSort = detail.getGoodssort();
			if(StringUtils.isNotEmpty(goodsSort)) {
				
				WaresClass waresClass = goodsMapper.getWaresClassInfo(goodsSort);
				if(waresClass != null) {
					
					detail.setGoodssortname(waresClass.getThisname());
				}
			}
			
			// 主单位
			String unitid = detail.getUnitid();
			if(StringUtils.isNotEmpty(unitid)) {
				
				MeteringUnit unit = goodsMapper.showMeteringUnitInfo(unitid);
				if(unit != null) {
					
					detail.setUnitname(unit.getName());
				}
			}
			
			// 辅单位
			String auxunitid = detail.getAuxunitid();
			if(StringUtils.isNotEmpty(auxunitid)){
				
				MeteringUnit auxunit = goodsMapper.showMeteringUnitInfo(auxunitid);
				if(auxunit != null) {
					
					detail.setAuxunitname(auxunit.getName());
				}
			}
			
			// 仓库
			String storageid = detail.getStorageid();
			if(StringUtils.isNotEmpty(storageid)) {
				
				StorageInfo storage = storageMapper.showStorageInfo(storageid);
				if(storage != null) {
					
					detail.setStoragename(storage.getName());
				}
			}
			
			// 税种
			String taxtype = detail.getTaxtype();
			if(StringUtils.isNotEmpty(taxtype)) {
				
				TaxType tax = financeMapper.getTaxTypeInfo(taxtype);
				if(tax != null) {
					
					detail.setTaxname(tax.getName());
				}
			}
		}
		*/
		return new PageData(oaGoodsDetailMapper.selectGoodsDetailListCount(pageMap), list, pageMap);
	}

	@Override
	public int editGoods(OaGoods goods, List list) {

		int ret = oaGoodsMapper.updateOaGoods(goods);
		
		oaGoodsDetailMapper.deleteOaGoodsDetailByBillid(goods.getId());
		
		for(Object o : list) {
			
			OaGoodsDetail detail = (OaGoodsDetail) o;
			if(StringUtils.isEmpty(detail.getGoodsid())) {
				continue;
			}
			detail.setBillid(goods.getId());
			
			oaGoodsDetailMapper.insertOaGoodsDetail(detail);
		}
		return ret;
	}

	@Override
	public int selectExistedGoodsid(String goodsid, String billid) {

		return oaGoodsDetailMapper.selectExistedGoodsid(goodsid, billid);
	}
	
	@Override
	public int selectExistedGoodsname(String goodsname) {
		
		return oaGoodsDetailMapper.selectExistedGoodsname(goodsname);
	}

	@Override
	public int selectExistedBarcode(String barcode) {

		return oaGoodsDetailMapper.selectExistedBarcode(barcode);
	}

	@Override
	public int selectExistedBoxbarcode(String boxbarcode) {

		return oaGoodsDetailMapper.selectExistedBoxbarcode(boxbarcode);
	}
}

