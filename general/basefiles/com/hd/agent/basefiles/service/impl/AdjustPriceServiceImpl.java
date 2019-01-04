/**
 * @(#)AdjustPriceServiceImpl.java
 *
 * @author wanghongteng
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年10月9日 wanghongteng 创建版本
 */
package com.hd.agent.basefiles.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.dao.AdjustPriceDetailMapper;
import com.hd.agent.basefiles.dao.AdjustPriceMapper;
import com.hd.agent.basefiles.dao.GoodsMapper;
import com.hd.agent.basefiles.model.AdjustPrice;
import com.hd.agent.basefiles.model.AdjustPriceDetail;
import com.hd.agent.basefiles.model.AdjustPriceExport;
import com.hd.agent.basefiles.model.Brand;
import com.hd.agent.basefiles.model.CustomerPrice;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.GoodsInfo_MteringUnitInfo;
import com.hd.agent.basefiles.model.GoodsInfo_PriceInfo;
import com.hd.agent.basefiles.model.TaxType;
import com.hd.agent.basefiles.service.IAdjustPriceService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author wanghongteng
 */
public class AdjustPriceServiceImpl extends FilesLevelServiceImpl implements IAdjustPriceService{
    
	
	/**
	 * 调价单dao
	 */
	private AdjustPriceMapper baseFilesAdjustPriceMapper;
	/**
	 * 调价单明细dao
	 */
	private AdjustPriceDetailMapper baseFilesAdjustPriceDetailMapper;
	private GoodsMapper goodsMapper;
	private BaseFilesServiceImpl baseFilesServiceImpl;

	public BaseFilesServiceImpl getBaseFilesServiceImpl() {
		return baseFilesServiceImpl;
	}

	public void setBaseFilesServiceImpl(BaseFilesServiceImpl baseFilesServiceImpl) {
		this.baseFilesServiceImpl = baseFilesServiceImpl;
	}

	public GoodsMapper getGoodsMapper() {
		return goodsMapper;
	}

	public void setGoodsMapper(GoodsMapper goodsMapper) {
		this.goodsMapper = goodsMapper;
	}
	public AdjustPriceMapper getBaseFilesAdjustPriceMapper() {
		return baseFilesAdjustPriceMapper;
	}

	public void setBaseFilesAdjustPriceMapper(AdjustPriceMapper baseFilesAdjustPriceMapper) {
		this.baseFilesAdjustPriceMapper = baseFilesAdjustPriceMapper;
	}

	public AdjustPriceDetailMapper getBaseFilesAdjustPriceDetailMapper() {
		return baseFilesAdjustPriceDetailMapper;
	}

	public void setBaseFilesAdjustPriceDetailMapper(AdjustPriceDetailMapper baseFilesAdjustPriceDetailMapper) {
		this.baseFilesAdjustPriceDetailMapper = baseFilesAdjustPriceDetailMapper;
	}
	
	

	/**
	 * 获取调价单列表
	 * 
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	@Override
	public PageData showAdjustPriceList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_delivery_aogorder", "t");
		pageMap.setDataSql(dataSql);
		int count = baseFilesAdjustPriceMapper.getAdjustPriceListCount(pageMap);
		List<AdjustPrice> list = baseFilesAdjustPriceMapper.getAdjustPriceList(pageMap);
		for (AdjustPrice item : list) {
		}
		PageData pageData = new PageData(count, list, pageMap);
		return pageData;
	}

	/**
	 * 根据商品编码，客户编码获取含税单价
	 * 
	 * @param goodsid
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	@Override
	public CustomerPrice getPriceDataByCustomerid(String goodsid, String customerid) throws Exception {
		CustomerPrice customerPrice = getBaseCustomerMapper().getCustomerPriceByCustomerAndGoods(customerid, goodsid);
		return customerPrice;
	}

	/**
	 * 根据客户编码获取合同商品列表 
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	@Override
	public List<CustomerPrice> getCustomerPriceListByTypeCode(String customerid) throws Exception {
		List<CustomerPrice> customerPrice = getBaseCustomerMapper().getCustomerPriceListByCustomer(customerid);
		return customerPrice;
	}
	/**
	 * 根据价格套编码获取价格套商品列表
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	public List<GoodsInfo_PriceInfo> getPriceListByTypeCode(String code) throws Exception{
		List<GoodsInfo_PriceInfo> GoodsInfo_PriceInfolist = goodsMapper.getPriceListByTypeCode(code);
		return GoodsInfo_PriceInfolist;
	}
	// 通过品牌自动生成
	/**
	 * 获取品牌编码
	 * 
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	@Override
	public List getBrandList() throws Exception {
		List list = goodsMapper.getBrandList();
		return list;
	}

	/**
	 * 通过涨幅和商品品牌获取调价商品
	 * 
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	@Override
	public List getAdjustPriceDetailByBrand(String rate, String brands, String type, String busid) throws Exception {
		List list = new ArrayList();
		String[] brandArr = brands.split(",");
		for (String brandid : brandArr) {
			List goodslist = goodsMapper.getGoodsListByBrand(brandid);
			for (Object obj : goodslist) {
				GoodsInfo goodsInfo = (GoodsInfo) obj;
				if ("1".equals(goodsInfo.getState())) {
					AdjustPriceDetail adjustPriceDetail = new AdjustPriceDetail();
					adjustPriceDetail.setGoodsid(goodsInfo.getId());
					adjustPriceDetail.setGoodsname(goodsInfo.getName());
					adjustPriceDetail.setBarcode(goodsInfo.getBarcode());
					adjustPriceDetail.setRate(new BigDecimal(rate));
					if (StringUtils.isNotEmpty(goodsInfo.getBrand()))
						adjustPriceDetail.setGoodsbrandName(goodsMapper.getBrandInfo(goodsInfo.getBrand()).getName());
					if (type.equals("1"))
						adjustPriceDetail.setOldprice(goodsInfo.getHighestbuyprice());
					else if (type.equals("2"))
						adjustPriceDetail.setOldprice(goodsInfo.getBasesaleprice());
					else if (type.equals("3")) {
						GoodsInfo_PriceInfo goodsInfo_PriceInfo = goodsMapper.getPriceDataByGoodsidAndCode(goodsInfo.getId(), busid);
						if (null != goodsInfo_PriceInfo)
							adjustPriceDetail.setOldprice(goodsInfo_PriceInfo.getTaxprice());
					}
					if (null != adjustPriceDetail.getOldprice()) {
						double doubleRate = Double.parseDouble(rate) / 100;
						adjustPriceDetail.setNowprice((adjustPriceDetail.getOldprice().multiply(new BigDecimal(doubleRate))).add(adjustPriceDetail.getOldprice()));
						GoodsInfo_MteringUnitInfo goodsInfo_MteringUnitInfo=goodsMapper.getMUInfoByGoodsIdAndIsdefault(goodsInfo.getId());
						if(null!=goodsInfo_MteringUnitInfo)
						    adjustPriceDetail.setBoxnum(goodsInfo_MteringUnitInfo.getRate());
						adjustPriceDetail.setNowboxprice(adjustPriceDetail.getNowprice().multiply(adjustPriceDetail.getBoxnum()));
						adjustPriceDetail.setOldboxprice(adjustPriceDetail.getOldprice().multiply(adjustPriceDetail.getBoxnum()));
						list.add(adjustPriceDetail);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 通过客户编码获取品牌编码
	 * 
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	@Override
	public List getBrandListByCustomerid(String customerid) throws Exception {
		List<CustomerPrice> customerPricelist = getBaseCustomerMapper().getCustomerPriceListByCustomer(customerid);
		List<Brand> list = new ArrayList();
		List<String> brandidlist = new ArrayList();
		for (CustomerPrice customerPrice : customerPricelist) {
			GoodsInfo goodsInfo = goodsMapper.getGoodsInfo(customerPrice.getGoodsid());
			if ("1".equals(goodsInfo.getState())) {
				int index = 0;
				for (String brandid : brandidlist) {
					if (goodsInfo.getBrand().equals(brandid)) {
						break;
					}
					index++;
				}
				if (index == brandidlist.size()) {
					brandidlist.add(goodsInfo.getBrand());
				}
			}
		}
		for (String brandid : brandidlist) {
			Brand brand = goodsMapper.getBrandInfo(brandid);
			list.add(brand);
		}
		return list;
	}

	/**
	 * 通过客户，涨幅和品牌获取调价商品
	 * 
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	@Override
	public List getAdjustCustomerPriceDetailByBrand(String rate, String brands, String customerid) throws Exception {
		List list = new ArrayList();
		String[] brandArr = brands.split(",");
		List<CustomerPrice> customerPricelist = getBaseCustomerMapper().getCustomerPriceListByCustomer(customerid);
		for (CustomerPrice customerPrice : customerPricelist) {
			AdjustPriceDetail adjustPriceDetail = new AdjustPriceDetail();
			GoodsInfo goodsInfo = goodsMapper.getGoodsInfo(customerPrice.getGoodsid());
			for (String brandid : brandArr) {
				if (goodsInfo.getState().equals("1") && goodsInfo.getBrand().equals(brandid)) {
					adjustPriceDetail.setGoodsid(customerPrice.getGoodsid());
					adjustPriceDetail.setGoodsname(goodsInfo.getName());
					adjustPriceDetail.setBarcode(goodsInfo.getBarcode());
					if(StringUtils.isNotEmpty(goodsInfo.getBrand()))
					    adjustPriceDetail.setGoodsbrandName(goodsMapper.getBrandInfo(goodsInfo.getBrand()).getName());
					adjustPriceDetail.setOldprice(customerPrice.getPrice());
					adjustPriceDetail.setRate(new BigDecimal(rate));
					double doubleRate = Double.parseDouble(rate)/100;
					adjustPriceDetail.setNowprice((adjustPriceDetail.getOldprice().multiply(new BigDecimal(doubleRate))).add(adjustPriceDetail.getOldprice()));
					GoodsInfo_MteringUnitInfo goodsInfo_MteringUnitInfo=goodsMapper.getMUInfoByGoodsIdAndIsdefault(goodsInfo.getId());
					if(null!=goodsInfo_MteringUnitInfo)
					    adjustPriceDetail.setBoxnum(goodsInfo_MteringUnitInfo.getRate());
					adjustPriceDetail.setNowboxprice(adjustPriceDetail.getNowprice().multiply(adjustPriceDetail.getBoxnum()));
					adjustPriceDetail.setOldboxprice(adjustPriceDetail.getOldprice().multiply(adjustPriceDetail.getBoxnum()));
					list.add(adjustPriceDetail);
				}
			}
		}
		return list;
	}

	// 通过分类自动生成
	/**
	 * 获取分类编码
	 * 
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	@Override
	public List getDefaultSortList() throws Exception {
		List list = goodsMapper.getBrandList();
		return list;
	}

	/**
	 * 通过涨幅和商品分类获取调价商品
	 * 
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	@Override
	public List getAdjustPriceDetailByDefaultSort(String rate, String goodssorts, String type, String busid) throws Exception {
		List list = new ArrayList();
		String[] goodssortArr = goodssorts.split(",");
		if (type.equals("4")) {
			List<CustomerPrice> customerPricelist = getBaseCustomerMapper().getCustomerPriceListByCustomer(busid);
			for (CustomerPrice customerPrice : customerPricelist) {
				AdjustPriceDetail adjustPriceDetail = new AdjustPriceDetail();
				GoodsInfo goodsInfo = goodsMapper.getGoodsInfo(customerPrice.getGoodsid());
				for (String goodssort : goodssortArr) {
					if (goodsInfo.getState().equals("1") && goodsInfo.getDefaultsort().equals(goodssort)) {
						adjustPriceDetail.setGoodsid(customerPrice.getGoodsid());
						adjustPriceDetail.setGoodsname(goodsInfo.getName());
						adjustPriceDetail.setBarcode(goodsInfo.getBarcode());
						if(StringUtils.isNotEmpty(goodsInfo.getBrand()))
						    adjustPriceDetail.setGoodsbrandName(goodsMapper.getBrandInfo(goodsInfo.getBrand()).getName());
						adjustPriceDetail.setOldprice(customerPrice.getPrice());
						double doubleRate = Double.parseDouble(rate)/100;
						adjustPriceDetail.setRate(new BigDecimal(rate));
						adjustPriceDetail.setNowprice((adjustPriceDetail.getOldprice().multiply(new BigDecimal(doubleRate))).add(adjustPriceDetail.getOldprice()));
						GoodsInfo_MteringUnitInfo goodsInfo_MteringUnitInfo=goodsMapper.getMUInfoByGoodsIdAndIsdefault(goodsInfo.getId());
						if(null!=goodsInfo_MteringUnitInfo)
						    adjustPriceDetail.setBoxnum(goodsInfo_MteringUnitInfo.getRate());
						if(null!=adjustPriceDetail.getBoxnum()){
						    adjustPriceDetail.setNowboxprice(adjustPriceDetail.getNowprice().multiply(adjustPriceDetail.getBoxnum()));
						    adjustPriceDetail.setOldboxprice(adjustPriceDetail.getOldprice().multiply(adjustPriceDetail.getBoxnum()));
						}
						list.add(adjustPriceDetail);
					}
				}
			}
		} else {
			for (String goodssort : goodssortArr) {
				List goodslist = goodsMapper.getGoodsListByDefaultSort(goodssort);
				for (Object obj : goodslist) {
					GoodsInfo goodsInfo = (GoodsInfo) obj;
					if ("1".equals(goodsInfo.getState())) {
						AdjustPriceDetail adjustPriceDetail = new AdjustPriceDetail();
						adjustPriceDetail.setGoodsid(goodsInfo.getId());
						adjustPriceDetail.setGoodsname(goodsInfo.getName());
						adjustPriceDetail.setBarcode(goodsInfo.getBarcode());
						if (StringUtils.isNotEmpty(goodsInfo.getBrand()))
							adjustPriceDetail.setGoodsbrandName(goodsMapper.getBrandInfo(goodsInfo.getBrand()).getName());
						adjustPriceDetail.setRate(new BigDecimal(rate));
						if (type.equals("1"))
							adjustPriceDetail.setOldprice(goodsInfo.getHighestbuyprice());
						else if (type.equals("2"))
							adjustPriceDetail.setOldprice(goodsInfo.getBasesaleprice());
						else if (type.equals("3")) {
							GoodsInfo_PriceInfo goodsInfo_PriceInfo = goodsMapper.getPriceDataByGoodsidAndCode(goodsInfo.getId(), busid);
							if (null != goodsInfo_PriceInfo)
								adjustPriceDetail.setOldprice(goodsInfo_PriceInfo.getTaxprice());
						}
						if (null != adjustPriceDetail.getOldprice()) {
							double doubleRate = Double.parseDouble(rate) / 100;
							adjustPriceDetail.setNowprice((adjustPriceDetail.getOldprice().multiply(new BigDecimal(doubleRate))).add(adjustPriceDetail.getOldprice()));
							GoodsInfo_MteringUnitInfo goodsInfo_MteringUnitInfo=goodsMapper.getMUInfoByGoodsIdAndIsdefault(goodsInfo.getId());
							if(null!=goodsInfo_MteringUnitInfo)
							    adjustPriceDetail.setBoxnum(goodsInfo_MteringUnitInfo.getRate());
							if(null!=adjustPriceDetail.getBoxnum()){
							   adjustPriceDetail.setNowboxprice(adjustPriceDetail.getNowprice().multiply(adjustPriceDetail.getBoxnum()));
							    adjustPriceDetail.setOldboxprice(adjustPriceDetail.getOldprice().multiply(adjustPriceDetail.getBoxnum()));
							}
							list.add(adjustPriceDetail);
						}
					}
				}
			}
		}
		return list;
	}

	/**
	 * 保存新增的调价单
	 * 
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	@Override
	public Map addAdjustPrice(AdjustPrice adjustPrice, List<AdjustPriceDetail> detailList) throws Exception {
		String msg = "";
		boolean flag = false;
		boolean canAudit = true;
		if (canAudit) {
			if (isAutoCreate("t_base_adjust_price")) {
				// 获取自动编号
				String id = getAutoCreateSysNumbderForeign(adjustPrice, "t_base_adjust_price");
				adjustPrice.setId(id);
			} else {
				adjustPrice.setId("TJD-" + CommonUtils.getDataNumberSendsWithRand());
			}
		}
		adjustPrice.setAddtime(new Date());
		adjustPrice.setBusinessdate(CommonUtils.getTodayDataStr());
		SysUser sysUser = getSysUser();
		adjustPrice.setAdddeptid(sysUser.getDepartmentid());
		adjustPrice.setAdddeptname(sysUser.getDepartmentname());
		adjustPrice.setAdduserid(sysUser.getUserid());
		adjustPrice.setAddusername(sysUser.getName());
		for(AdjustPriceDetail adjustPriceDetail:detailList){
			adjustPriceDetail.setBillid(adjustPrice.getId());
			baseFilesAdjustPriceDetailMapper.addAdjustPriceDetail(adjustPriceDetail);
		}
		flag=baseFilesAdjustPriceMapper.addAdjustPrice(adjustPrice)>0;
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("id", adjustPrice.getId());
		return map;
	}
	/**
	 * 审核的调价单
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	@Override
	public Map auditAdjustPrice(String id) throws Exception {
		boolean flag;
		String msg="";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		String businessdate = dateFormat.format(calendar.getTime());
		SysUser sysUser = getSysUser();
		AdjustPrice adjustPrice = baseFilesAdjustPriceMapper.getAdjustPriceByID(id);
		if ("2".equals(adjustPrice.getStatus())) {
			int i = baseFilesAdjustPriceMapper.auditAdjustPrice(id, sysUser.getUserid(), sysUser.getName(), new Date(), businessdate);
			flag = i > 0;
			if(flag){
				flag=false;
				List<AdjustPriceDetail> detailList=baseFilesAdjustPriceDetailMapper.getAdjustPriceDetailList(adjustPrice.getId());
				if("1".equals(adjustPrice.getType())){
					for(AdjustPriceDetail adjustPriceDetail:detailList){
						GoodsInfo goodsInfo = goodsMapper.getGoodsInfo(adjustPriceDetail.getGoodsid());
						goodsInfo.setHighestbuyprice(adjustPriceDetail.getNowprice());
						goodsInfo.setNewbuyprice(adjustPriceDetail.getNowprice());
						goodsInfo.setOldId(goodsInfo.getId());
						flag=goodsMapper.editGoodsInfo(goodsInfo)>0;
					}
				}
                if("2".equals(adjustPrice.getType())){
                    for(AdjustPriceDetail adjustPriceDetail:detailList){
                    	GoodsInfo goodsInfo = goodsMapper.getGoodsInfo(adjustPriceDetail.getGoodsid());
						goodsInfo.setBasesaleprice(adjustPriceDetail.getNowprice());
						goodsInfo.setOldId(goodsInfo.getId());
						flag=goodsMapper.editGoodsInfo(goodsInfo)>0;
					}
				}
                if("3".equals(adjustPrice.getType())){
                    for(AdjustPriceDetail adjustPriceDetail:detailList){
                    	GoodsInfo_PriceInfo goodsInfo_PriceInfo=goodsMapper.getPriceInfoByGoodsAndCode(adjustPriceDetail.getGoodsid(), adjustPrice.getBusid());
                    	if(null!=goodsInfo_PriceInfo){
                    	    goodsInfo_PriceInfo.setTaxprice(adjustPriceDetail.getNowprice());
                    	    TaxType taxType=getBaseFinanceMapper().getTaxTypeInfo(goodsInfo_PriceInfo.getTaxtype());
                    	    double doublePrice=goodsInfo_PriceInfo.getTaxprice().doubleValue()/(1+taxType.getRate().doubleValue()/100);
                    	    goodsInfo_PriceInfo.setPrice(new BigDecimal(doublePrice));
                    	    GoodsInfo_MteringUnitInfo goodsInfo_MteringUnitInfo=goodsMapper.getMUInfoByGoodsIdAndIsdefault(adjustPriceDetail.getGoodsid());
                    	    if(null!=goodsInfo_MteringUnitInfo)
                    	        goodsInfo_PriceInfo.setBoxprice(goodsInfo_MteringUnitInfo.getRate().multiply(goodsInfo_PriceInfo.getTaxprice()));
                    	    flag=goodsMapper.editPriceInfo(goodsInfo_PriceInfo)>0;
                    	}
					}
				}
                if("4".equals(adjustPrice.getType())){
                    for(AdjustPriceDetail adjustPriceDetail:detailList){
                    	CustomerPrice customerPrice = getBaseCustomerMapper().getCustomerPriceByCustomerAndGoods(adjustPrice.getBusid(), adjustPriceDetail.getGoodsid());
                    	customerPrice.setPrice(adjustPriceDetail.getNowprice());
                    	GoodsInfo goodsInfo= baseFilesServiceImpl.getAllGoodsInfoByID(adjustPriceDetail.getGoodsid());
                    	TaxType taxType=getBaseFinanceMapper().getTaxTypeInfo(goodsInfo.getDefaulttaxtype());
                 	    double doubleNoprice=customerPrice.getPrice().doubleValue()/(1+taxType.getRate().doubleValue()/100);
                    	customerPrice.setNoprice(new BigDecimal(doubleNoprice));
                 	    GoodsInfo_MteringUnitInfo goodsInfo_MteringUnitInfo=goodsMapper.getMUInfoByGoodsIdAndIsdefault(adjustPriceDetail.getGoodsid());
                    	if(null!=goodsInfo_MteringUnitInfo)
                 	        customerPrice.setCtcboxprice(goodsInfo_MteringUnitInfo.getRate().multiply(customerPrice.getPrice()));
                    	flag=getBaseCustomerMapper().updateCustomerPrice(customerPrice)>0;
					}
				}
				if(!flag){
					msg="，修改价格出错";
					oppauditAdjustPrice(id);
				}
			}
		} else {
			flag = false;
			msg="，订单不是保存状态，不能审核";
		}
		Map map = new HashMap();
		map.put("msg", msg);
		map.put("flag", flag);
		return map;
	}
	/**
	 * 保存修改的调价单
	 * 
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	@Override
	public Map editAdjustPrice(AdjustPrice adjustPrice, List<AdjustPriceDetail> detailList) throws Exception {
		String msg = "";
		boolean flag = false;
		AdjustPrice checkAdjustPrice = baseFilesAdjustPriceMapper.getAdjustPriceByID(adjustPrice.getId());
		if ("2".equals(checkAdjustPrice.getStatus())) {
			baseFilesAdjustPriceDetailMapper.deleteAdjustPriceDetail(adjustPrice.getId());
			for (AdjustPriceDetail adjustPriceDetail : detailList) {
				adjustPriceDetail.setBillid(adjustPrice.getId());
				baseFilesAdjustPriceDetailMapper.addAdjustPriceDetail(adjustPriceDetail);
			}
			SysUser sysUser = getSysUser();
			adjustPrice.setModifyuserid(sysUser.getUserid());
			adjustPrice.setModifyusername(sysUser.getName());
			adjustPrice.setModifytime(new Date());
			int i = baseFilesAdjustPriceMapper.editAdjustPrice(adjustPrice);
			flag = i > 0;
		} 
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("id", adjustPrice.getId());
		return map;
	}

	/**
	 * 删除调价单
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	@Override
	public boolean deleteAdjustPrice(String id) throws Exception {
		AdjustPrice adjustPrice = baseFilesAdjustPriceMapper.getAdjustPriceByID(id);
		if ("2".equals(adjustPrice.getStatus())&&adjustPrice!=null) {
			int i = baseFilesAdjustPriceMapper.deleteAdjustPrice(id);
			if (i > 0) {
				baseFilesAdjustPriceDetailMapper.deleteAdjustPriceDetail(adjustPrice.getId());
				return true;
			}
			else{
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 反审调价单
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	@Override
	public Map oppauditAdjustPrice(String id) throws Exception {
		boolean flag;
		SysUser sysUser = getSysUser();
		AdjustPrice adjustPrice = baseFilesAdjustPriceMapper.getAdjustPriceByID(id);
		if ("3".equals(adjustPrice.getStatus())) {
			int i = baseFilesAdjustPriceMapper.oppauditAdjustPrice(id, sysUser.getUserid(), sysUser.getName(), new Date());
			flag = i > 0;
		} else {
			flag = false;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		return map;
	}

	/**
	 * 通过id获取调价单
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	@Override
	public Map getAdjustPriceInfo(String id) throws Exception {
		AdjustPrice adjustPrice = baseFilesAdjustPriceMapper.getAdjustPriceByID(id);
		List<AdjustPriceDetail> list = baseFilesAdjustPriceDetailMapper.getAdjustPriceDetailList(id);
		for (AdjustPriceDetail adjustPriceDetail : list) {
			GoodsInfo goodsInfo = goodsMapper.getGoodsInfo(adjustPriceDetail.getGoodsid());
			if(null != goodsInfo){
				adjustPriceDetail.setGoodsname(goodsInfo.getName());
				adjustPriceDetail.setBarcode(goodsInfo.getBarcode());
				GoodsInfo_MteringUnitInfo goodsInfo_MteringUnitInfo=goodsMapper.getMUInfoByGoodsIdAndIsdefault(goodsInfo.getId());
				if(null!=goodsInfo_MteringUnitInfo)
					adjustPriceDetail.setBoxnum(goodsInfo_MteringUnitInfo.getRate());
				adjustPriceDetail.setNowboxprice(adjustPriceDetail.getNowprice().multiply(adjustPriceDetail.getBoxnum()));
				adjustPriceDetail.setOldboxprice(adjustPriceDetail.getOldprice().multiply(adjustPriceDetail.getBoxnum()));
				if(StringUtils.isNotEmpty(goodsInfo.getBrand()))
					adjustPriceDetail.setGoodsbrandName(goodsMapper.getBrandInfo(goodsInfo.getBrand()).getName());
			}
		}
		Map map = new HashMap();
		map.put("adjustPrice", adjustPrice);
		map.put("detailList", list);
		return map;
	}

	/**
	 * 获取导出列表
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	@Override
	public List<AdjustPriceExport> getAdjustPriceExportList(PageMap pageMap) throws Exception {
		String dataSql = getAccessColumnList("t_base_adjust_price", "a");
		pageMap.setDataSql(dataSql);
		pageMap.setQueryAlias("a");
		List<AdjustPriceExport> list = baseFilesAdjustPriceMapper.getAdjustPriceExportList(pageMap);
		if(list.size() != 0){
			for(AdjustPriceExport adjustPriceExport : list){
				GoodsInfo goodsInfo=goodsMapper.getGoodsInfo(adjustPriceExport.getGoodsid());
				if(null != goodsInfo){
					adjustPriceExport.setGoodsname(goodsInfo.getName());

					GoodsInfo_MteringUnitInfo goodsInfo_MteringUnitInfo=goodsMapper.getMUInfoByGoodsIdAndIsdefault(goodsInfo.getId());
					if(null!=goodsInfo_MteringUnitInfo)
						adjustPriceExport.setBoxnum(goodsInfo_MteringUnitInfo.getRate());
					adjustPriceExport.setNowboxprice(adjustPriceExport.getNowprice().multiply(adjustPriceExport.getBoxnum()));
					adjustPriceExport.setOldboxprice(adjustPriceExport.getOldprice().multiply(adjustPriceExport.getBoxnum()));
				}
				if("1".equals(adjustPriceExport.getType())){
					adjustPriceExport.setTypename("商品采购价");
				}
				else if("2".equals(adjustPriceExport.getType())){
					adjustPriceExport.setTypename("商品基准销售价");
				}
				else if("3".equals(adjustPriceExport.getType())){
					adjustPriceExport.setTypename("价格套");
				}
				else if("4".equals(adjustPriceExport.getType())){
					adjustPriceExport.setTypename("合同价");
				}
			}
		}
		return list;
	}
	/**
	 * 导入商品调价单
	 * @param list
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 24, 2015
	 */
	public Map importAdjustPrice(List<Map<String, Object>> list) throws Exception {
		//根据供应商编码分割单据
		boolean emptyType = false,emptyGoods = false;
		String nullgoodsids = "",nullspells = "",nullbarcode = "",failorders = "";
		String errormsg = "",drerrormsg = "";
		int emptyTypeNum = 0,emptyGoodsNum = 0;
		int success=0,ordernum=0; 
		List<AdjustPrice> adjustPriceList=new ArrayList<AdjustPrice>();
		List<AdjustPriceDetail> detaillist = new ArrayList<AdjustPriceDetail>();
		List<String> idlist=new ArrayList<String>();
		for(Map<String, Object> map : list){
			
			String indexID="";
			String typename = (null != map.get("typename")) ? (String)map.get("typename") : "";
			String type="";
	    	String busid = (null != map.get("busid")) ? (String)map.get("busid") : "";
		    String businessdate = (null != map.get("businessdate")) ? (String)map.get("businessdate") : "";
		    if(businessdate=="")//获取业务日期
		    	businessdate=CommonUtils.getTodayDataStr();
		    if("商品采购价".equals(typename)){
				type="1";
			}else if("商品基准销售价".equals(typename)){
				type="2";
			}else if("价格套".equals(typename)){
				type="3";
			}else if("合同价".equals(typename)){
				type="4";
			}
			if(adjustPriceList.isEmpty()){
				AdjustPrice adjustPrice=new AdjustPrice();
				adjustPrice.setType(type);
				adjustPrice.setBusid(busid);
				adjustPrice.setBusinessdate(businessdate);
				if (isAutoCreate("t_base_adjust_price")) {
					// 获取自动编号
					String id = getAutoCreateSysNumbderForeign(adjustPrice, "t_base_adjust_price");
					adjustPrice.setId(id);
				} else {
					adjustPrice.setId("TJD-" + CommonUtils.getDataNumberSendsWithRand());
				}
				indexID=adjustPrice.getId();
				adjustPriceList.add(adjustPrice);
			}
			else{
				boolean haveid=false;
				for(AdjustPrice index : adjustPriceList){
					if(type.equals(index.getType())&&busid.equals(index.getBusid())&&businessdate.equals(index.getBusinessdate())){
						indexID=index.getId();
						haveid=true;
						break;
					}
				}
				if(!haveid)
				{
					AdjustPrice adjustPrice=new AdjustPrice();
					adjustPrice.setType(type);
					adjustPrice.setBusid(busid);
					adjustPrice.setBusinessdate(businessdate);
					if (isAutoCreate("t_base_adjust_price")) {
						// 获取自动编号
						String id = getAutoCreateSysNumbderForeign(adjustPrice, "t_base_adjust_price");
						adjustPrice.setId(id);
					} else {
						adjustPrice.setId("TJD-" + CommonUtils.getDataNumberSendsWithRand());
					}
					indexID=adjustPrice.getId();
					adjustPriceList.add(adjustPrice);
				}
			}
			String goodsid = (null != map.get("goodsid")) ? (String)map.get("goodsid") : "";
			String spell = (null != map.get("spell")) ? (String)map.get("spell") : "";
			String barcode = (null != map.get("barcode")) ? (String)map.get("barcode") : "";
			
			if(type!=""&&goodsid!=""){
				//根据已知商品编码or助记符or条形码获取商品档案信息
				GoodsInfo goodsInfoQ = null;
				if(StringUtils.isNotEmpty(goodsid)){
					goodsInfoQ = baseFilesServiceImpl.getAllGoodsInfoByID(goodsid);
				}else if(StringUtils.isNotEmpty(spell)){
					goodsInfoQ =baseFilesServiceImpl.getGoodsInfoBySpell(spell);
				}else if(StringUtils.isNotEmpty(barcode)){
					goodsInfoQ =baseFilesServiceImpl.getGoodsInfoByBarcode(barcode);
				}
				GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(goodsInfoQ);
				if(null != goodsInfo){
					BigDecimal nowprice = (null != map.get("nowprice")) ? new BigDecimal((String) map.get("nowprice")) : BigDecimal.ZERO;// 现价
					BigDecimal nowboxprice = (null != map.get("nowboxprice")) ? new BigDecimal((String) map.get("nowboxprice")) : BigDecimal.ZERO;// 现箱价
					BigDecimal rate = (null != map.get("rate")) ? new BigDecimal((String) map.get("rate")) : BigDecimal.ZERO;// 涨幅
					BigDecimal boxnum=new BigDecimal("0");
					GoodsInfo_MteringUnitInfo goodsInfo_MteringUnitInfo=goodsMapper.getMUInfoByGoodsIdAndIsdefault(goodsInfo.getId());
					if(null!=goodsInfo_MteringUnitInfo)
						boxnum=goodsInfo_MteringUnitInfo.getRate();
					try {
						if (nowprice.compareTo(BigDecimal.ZERO)!=0) {
							double doubleNowprice = nowprice.doubleValue();
							AdjustPriceDetail adjustPriceDetail = new AdjustPriceDetail();
							adjustPriceDetail.setGoodsid(goodsInfo.getId());
							adjustPriceDetail.setBillid(indexID);
							if (type.equals("1"))
								adjustPriceDetail.setOldprice(goodsInfo.getHighestbuyprice());
							else if(type.equals("2"))
								adjustPriceDetail.setOldprice(goodsInfo.getBasesaleprice());
							else if(type.equals("3")&&busid!=""){
								GoodsInfo_PriceInfo goodsInfo_PriceInfo=goodsMapper.getPriceDataByGoodsidAndCode(goodsInfo.getId(), busid);
								if(null!=goodsInfo_PriceInfo){
									adjustPriceDetail.setOldprice(goodsInfo_PriceInfo.getTaxprice());
								}else{
									adjustPriceDetail.setOldprice(new BigDecimal("0"));
								}
							}
							else if (type.equals("4")&&busid!=""){
								CustomerPrice customerPrice = getBaseCustomerMapper().getCustomerPriceByCustomerAndGoods(busid,goodsInfo.getId());
								if(null!=customerPrice){
									adjustPriceDetail.setOldprice(customerPrice.getPrice());
								}
							}
							adjustPriceDetail.setNowprice(nowprice);
							if(null!=adjustPriceDetail.getOldprice()){
							    double doubleOldPrice=adjustPriceDetail.getOldprice().doubleValue();
							    if(0==doubleOldPrice){
									adjustPriceDetail.setRate(new BigDecimal("0"));
								}else{
									double doubleRate=(doubleNowprice-doubleOldPrice)/doubleOldPrice*100;
									adjustPriceDetail.setRate(BigDecimal.valueOf(doubleRate));
								}
							    detaillist.add(adjustPriceDetail);
							}
						}
						else if(nowboxprice.compareTo(BigDecimal.ZERO)!=0) {
							double doubleboxnum=boxnum.doubleValue();
							double doubleNowprice = nowboxprice.doubleValue()/doubleboxnum;
							AdjustPriceDetail adjustPriceDetail = new AdjustPriceDetail();
							adjustPriceDetail.setGoodsid(goodsInfo.getId());
							adjustPriceDetail.setBillid(indexID);
							if (type.equals("1"))
								adjustPriceDetail.setOldprice(goodsInfo.getHighestbuyprice());
							else if(type.equals("2"))
								adjustPriceDetail.setOldprice(goodsInfo.getBasesaleprice());
							else if(type.equals("3")&&busid!=""){
								GoodsInfo_PriceInfo goodsInfo_PriceInfo=goodsMapper.getPriceDataByGoodsidAndCode(goodsInfo.getId(), busid);
								if(null!=goodsInfo_PriceInfo){
									adjustPriceDetail.setOldprice(goodsInfo_PriceInfo.getTaxprice());
								}else{
									adjustPriceDetail.setOldprice(new BigDecimal("0"));
								}
							}
							else if (type.equals("4")&&busid!=""){
								CustomerPrice customerPrice = getBaseCustomerMapper().getCustomerPriceByCustomerAndGoods(busid,goodsInfo.getId());
								if(null!=customerPrice){
									adjustPriceDetail.setOldprice(customerPrice.getPrice());
								}
							}
							adjustPriceDetail.setNowprice(BigDecimal.valueOf(doubleNowprice));
							if(null!=adjustPriceDetail.getOldprice()){
                                double doubleOldPrice=adjustPriceDetail.getOldprice().doubleValue();
                                if(0==doubleOldPrice){
                                    adjustPriceDetail.setRate(new BigDecimal("0"));
                                }else{
                                    double doubleRate=(doubleNowprice-doubleOldPrice)/doubleOldPrice*100;
                                    adjustPriceDetail.setRate(BigDecimal.valueOf(doubleRate));
                                }
                                detaillist.add(adjustPriceDetail);
							}
						}
						else if (rate.compareTo(BigDecimal.ZERO)!=0) {
							double doubleRate = rate.doubleValue()/100;
							AdjustPriceDetail adjustPriceDetail = new AdjustPriceDetail();
							adjustPriceDetail.setGoodsid(goodsInfo.getId());
							adjustPriceDetail.setBillid(indexID);
							if (type.equals("1"))
								adjustPriceDetail.setOldprice(goodsInfo.getHighestbuyprice());
							else if (type.equals("2"))
								adjustPriceDetail.setOldprice(goodsInfo.getBasesaleprice());
							else if (type.equals("3")){
								GoodsInfo_PriceInfo goodsInfo_PriceInfo=goodsMapper.getPriceDataByGoodsidAndCode(goodsInfo.getId(), busid);
								if(null!=goodsInfo_PriceInfo){
									adjustPriceDetail.setOldprice(goodsInfo_PriceInfo.getTaxprice());
								}else{
									adjustPriceDetail.setOldprice(new BigDecimal("0"));
								}
							}
							else if (type.equals("4")){
								CustomerPrice customerPrice = getBaseCustomerMapper().getCustomerPriceByCustomerAndGoods(busid,goodsInfo.getId());
								if(null!=customerPrice){
									adjustPriceDetail.setOldprice(customerPrice.getPrice());
								}
							}
							adjustPriceDetail.setRate(rate);
							if(null!=adjustPriceDetail.getOldprice()){
						    	double doubleOldPrice=adjustPriceDetail.getOldprice().doubleValue();
							    double doubleNowPrice=doubleOldPrice*doubleRate+doubleOldPrice;
							    adjustPriceDetail.setNowprice(BigDecimal.valueOf(doubleNowPrice));
							    detaillist.add(adjustPriceDetail);
							}
						}
					} catch (Exception e) {
						String msg = "";
						if(StringUtils.isNotEmpty(goodsid)){
							msg = "商品编码为："+goodsid;
						}else if(StringUtils.isNotEmpty(spell)){
							msg = "助记符为："+spell;
						}else if(StringUtils.isNotEmpty(barcode)){
							msg = "条形码为："+barcode;
						}
					}
				}else{
					if(StringUtils.isNotEmpty(goodsid)){
						if(StringUtils.isNotEmpty(nullgoodsids)){
							nullgoodsids += "," + goodsid;
						}else{
							nullgoodsids = goodsid;
						}
					}else if(StringUtils.isNotEmpty(spell)){
						if(StringUtils.isNotEmpty(nullspells)){
							nullspells += "," + spell;
						}else{
							nullspells = spell;
						}
					}else if(StringUtils.isNotEmpty(barcode)){
						if(StringUtils.isNotEmpty(nullbarcode)){
							nullbarcode += "," + barcode;
						}else{
							nullbarcode = barcode;
						}
					}
				}
			}else{
				emptyGoods = true;
				emptyGoodsNum++;
				emptyType = true;
				emptyTypeNum++;
			}
		}
		//执行导入操作，即新增采购计划单
		boolean flag=false;
		if (!adjustPriceList.isEmpty()) {
			for (AdjustPrice adjustPrice : adjustPriceList) {
				adjustPrice.setStatus("2");
				SysUser sysUser = getSysUser();
				adjustPrice.setAdddeptid(sysUser.getDepartmentid());
				adjustPrice.setAdddeptname(sysUser.getDepartmentname());
				adjustPrice.setAdduserid(sysUser.getUserid());
				adjustPrice.setAddusername(sysUser.getName());
				adjustPrice.setRemark("excel导入");
				adjustPrice.setAddtime(new Date());
				int i=0;
				for(AdjustPriceDetail adjustPriceDetail : detaillist){
					if(adjustPriceDetail.getBillid().equals(adjustPrice.getId())){
				        i=baseFilesAdjustPriceMapper.addAdjustPrice(adjustPrice);
					    break;
					}
				}
				if(i>0){
					flag=true;
					ordernum++;
					idlist.add(adjustPrice.getId());
				}
			}
		}
		if(!detaillist.isEmpty()){
			for (AdjustPriceDetail adjustPriceDetail:detaillist) {
				int i =baseFilesAdjustPriceDetailMapper.addAdjustPriceDetail(adjustPriceDetail);
				if(i>0){
					success++;
					
				}
			}
		}	
		
		Map map = new HashMap();
		if(flag){
			map.put("success", success);
			map.put("ordernum", ordernum);
		}
		else{
			map.put("error", true);
		}
		map.put("idlist", idlist);
		map.put("flag", flag);
		return map;
	}
}

