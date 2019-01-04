/**
 * @(#)OaGoodsExecutionEndListener.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-7-29 limin 创建版本
 */
package com.hd.agent.oa.listener;

import java.util.ArrayList;
import java.util.List;

import com.hd.agent.activiti.dao.ProcessMapper;
import com.hd.agent.activiti.model.Process;
import com.hd.agent.activiti.service.impl.BusinessEndListenerImpl;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.GoodsInfo_MteringUnitInfo;
import com.hd.agent.basefiles.model.GoodsInfo_PriceInfo;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.oa.dao.OaGoodsDetailMapper;
import com.hd.agent.oa.dao.OaGoodsMapper;
import com.hd.agent.oa.model.OaGoods;
import com.hd.agent.oa.model.OaGoodsDetail;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.service.ISysCodeService;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * @author limin
 */
public class OaGoodsExecutionEndListener extends BusinessEndListenerImpl {

	private OaGoodsMapper oaGoodsMapper;
	
	private BaseServiceImpl baseService;
	
	private ProcessMapper processMapper;
	
	private OaGoodsDetailMapper oaGoodsDetailMapper;
	
	private ISysCodeService baseSysCodeService;

	public OaGoodsMapper getOaGoodsMapper() {
		return oaGoodsMapper;
	}

	public void setOaGoodsMapper(OaGoodsMapper oaGoodsMapper) {
		this.oaGoodsMapper = oaGoodsMapper;
	}

	public BaseServiceImpl getBaseService() {
		return baseService;
	}

	public void setBaseService(BaseServiceImpl baseService) {
		this.baseService = baseService;
	}

	public ProcessMapper getProcessMapper() {
		return processMapper;
	}

	public void setProcessMapper(ProcessMapper processMapper) {
		this.processMapper = processMapper;
	}

	public OaGoodsDetailMapper getOaGoodsDetailMapper() {
		return oaGoodsDetailMapper;
	}

	public void setOaGoodsDetailMapper(OaGoodsDetailMapper oaGoodsDetailMapper) {
		this.oaGoodsDetailMapper = oaGoodsDetailMapper;
	}

	public ISysCodeService getBaseSysCodeService() {
		return baseSysCodeService;
	}

	public void setBaseSysCodeService(ISysCodeService baseSysCodeService) {
		this.baseSysCodeService = baseSysCodeService;
	}

	@Override
	public void end(Process process) throws Exception {
		
		OaGoods oag = oaGoodsMapper.selectOaGoods(process.getBusinessid());
		String billid = oag.getId();
		List<OaGoodsDetail> list =oaGoodsDetailMapper.selectGoodsDetailListByBillid(billid);
		
		for(OaGoodsDetail detail : list) {
			
			GoodsInfo goodsInfo = new GoodsInfo();
			List<GoodsInfo_PriceInfo> priceInfoList = new ArrayList<GoodsInfo_PriceInfo>();
			GoodsInfo_MteringUnitInfo goodsMUInfo = new GoodsInfo_MteringUnitInfo();

			goodsInfo.setId(detail.getGoodsid());
			goodsInfo.setName(detail.getGoodsname());
			goodsInfo.setState("1");
			goodsInfo.setMainunit(detail.getUnitid());
			goodsInfo.setBrand(detail.getBrandid());
			goodsInfo.setBarcode(detail.getBarcode());
			goodsInfo.setBoxbarcode(detail.getBoxbarcode());
			goodsInfo.setDefaultsort(detail.getGoodssort());
			// goodsInfo.setGrossweight(detail.getGrossweight());
			// goodsInfo.setSinglevolume(detail.getSinglevolume());
			goodsInfo.setStorageid(detail.getStorageid());
			goodsInfo.setHighestbuyprice(detail.getBuytaxprice());
			goodsInfo.setDefaulttaxtype(detail.getTaxtype());
			goodsInfo.setDefaultsupplier(oag.getSupplierid());
			goodsInfo.setTotalvolume(detail.getTotalvolume());
			goodsInfo.setTotalweight(detail.getTotalweight());
			goodsInfo.setGwidth(detail.getGwidth());
			goodsInfo.setGlength(detail.getGlength());
			goodsInfo.setGhight(detail.getGheight());
			goodsInfo.setBasesaleprice(detail.getBasesaleprice());
			goodsInfo.setRemark(detail.getRemark());
			goodsInfo.setModel(detail.getModel());

            goodsInfo.setBoxnum(detail.getBoxnum());

			// 1948 核算成本价没有导入 
			goodsInfo.setCostaccountprice(detail.getCostaccountprice());

            // 5646 6.7（百润）&通用版：新品登录加产地
            goodsInfo.setProductfield(detail.getProductfield());

            // 8101 1114&通用版：新品登录把产地和保质期都加上去
			if(detail.getShelflife() != null && StringUtils.isNotEmpty(detail.getShelflifeunit())) {
				goodsInfo.setIsshelflife("1");
				goodsInfo.setShelflife(detail.getShelflife());
				goodsInfo.setShelflifeunit(detail.getShelflifeunit());
			}

            String price1code = null;
            String price2code = null;
            String price3code = null;
            String price4code = null;
            String price5code = null;
            String price6code = null;
            String price7code = null;
            String price8code = null;
            String price1name = null;
            String price2name = null;
            String price3name = null;
            String price4name = null;
            String price5name = null;
            String price6name = null;
            String price7name = null;
            String price8name = null;

			List<SysCode> priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
			int count = 0 ;
			
			if(priceList.size() > count) {
				
				SysCode code = priceList.get(count);
				price1code = code.getCode();
				price1name = code.getCodename();

                GoodsInfo_PriceInfo price1 = new GoodsInfo_PriceInfo();
                price1.setGoodsid(detail.getGoodsid());
                price1.setCode(price1code);
                price1.setName(price1name);
                price1.setTaxprice(detail.getPrice1());
                priceInfoList.add(price1);

				count++;
			}
			if(priceList.size() > count) {
				
				SysCode code = priceList.get(count);
				price2code = code.getCode();
				price2name = code.getCodename();

                GoodsInfo_PriceInfo price2 = new GoodsInfo_PriceInfo();
                price2.setGoodsid(detail.getGoodsid());
                price2.setCode(price2code);
                price2.setName(price2name);
                price2.setTaxprice(detail.getPrice2());
                priceInfoList.add(price2);

				count++;
			}
			if(priceList.size() > count) {
				
				SysCode code = priceList.get(count);
				price3code = code.getCode();
				price3name = code.getCodename();

                GoodsInfo_PriceInfo price3 = new GoodsInfo_PriceInfo();
                price3.setGoodsid(detail.getGoodsid());
                price3.setCode(price3code);
                price3.setName(price3name);
                price3.setTaxprice(detail.getPrice3());
                priceInfoList.add(price3);

				count++;
			}
			if(priceList.size() > count) {
				
				SysCode code = priceList.get(count);
				price4code = code.getCode();
				price4name = code.getCodename();

                GoodsInfo_PriceInfo price4 = new GoodsInfo_PriceInfo();
                price4.setGoodsid(detail.getGoodsid());
                price4.setCode(price4code);
                price4.setName(price4name);
                price4.setTaxprice(detail.getPrice4());
                priceInfoList.add(price4);

				count++;
			}
            if(priceList.size() > count) {

                SysCode code = priceList.get(count);
                price5code = code.getCode();
                price5name = code.getCodename();

                GoodsInfo_PriceInfo price5 = new GoodsInfo_PriceInfo();
                price5.setGoodsid(detail.getGoodsid());
                price5.setCode(price5code);
                price5.setName(price5name);
                price5.setTaxprice(detail.getPrice5());
                priceInfoList.add(price5);

                count++;
            }
            if(priceList.size() > count) {

                SysCode code = priceList.get(count);
                price6code = code.getCode();
                price6name = code.getCodename();

                GoodsInfo_PriceInfo price6 = new GoodsInfo_PriceInfo();
                price6.setGoodsid(detail.getGoodsid());
                price6.setCode(price6code);
                price6.setName(price6name);
                price6.setTaxprice(detail.getPrice6());
                priceInfoList.add(price6);

                count++;
            }
            if(priceList.size() > count) {

                SysCode code = priceList.get(count);
                price7code = code.getCode();
                price7name = code.getCodename();

                GoodsInfo_PriceInfo price7 = new GoodsInfo_PriceInfo();
                price7.setGoodsid(detail.getGoodsid());
                price7.setCode(price7code);
                price7.setName(price7name);
                price7.setTaxprice(detail.getPrice7());
                priceInfoList.add(price7);

                count++;
            }
            if(priceList.size() > count) {

                SysCode code = priceList.get(count);
                price8code = code.getCode();
                price8name = code.getCodename();

                GoodsInfo_PriceInfo price8 = new GoodsInfo_PriceInfo();
                price8.setGoodsid(detail.getGoodsid());
                price8.setCode(price8code);
                price8.setName(price8name);
                price8.setTaxprice(detail.getPrice8());
                priceInfoList.add(price8);

                count++;
            }

			// 辅单位
			goodsMUInfo.setGoodsid(detail.getGoodsid());
			goodsMUInfo.setMeteringunitid(detail.getAuxunitid());
			goodsMUInfo.setRate(detail.getBoxnum());
			goodsMUInfo.setIsdefault("1");
			
			// 登录到商品档案表 
			baseService.addBaseGoodsInfo(goodsInfo, priceInfoList, goodsMUInfo);
		}
	}

	@Override
	public void delete(Process process) throws Exception {
		
		// 当该Process已经被删除时，删除相关的业务数据
		if(processMapper.getProcess(process.getId()) != null) {
			
			String businessId = process.getBusinessid();
			
			oaGoodsMapper.deleteOaGoods(businessId);
			oaGoodsDetailMapper.deleteOaGoodsDetailByBillid(businessId);
		}
	}

}

