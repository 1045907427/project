/**
 * @(#)BaseFilesServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 10, 2013 chenwei 创建版本
 */
package com.hd.agent.basefiles.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.CustomerCapitalMapper;
import com.hd.agent.account.model.CustomerCapital;
import com.hd.agent.basefiles.dao.*;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.IBaseFilesService;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.OfficeUtils;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.dao.DispatchBillMapper;
import com.hd.agent.sales.dao.ReceiptMapper;
import com.hd.agent.storage.model.StorageSummary;
import com.hd.agent.storage.service.IStorageSummaryService;
import com.hd.agent.system.model.SysParam;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 *
 * 基础档案service
 * 提供方法供业务类调用
 * @author chenwei
 */
public class BaseFilesServiceImpl extends BaseServiceImpl implements IBaseFilesService{

	/**
	 * 商品档案dao
	 */
	private GoodsMapper baseFilesGoodsMapper;
	/**
	 * 仓库档案dao
	 */
	private StorageMapper baseFilesStorageMapper;

	/**
	 * 客户档案dao
	 */
	private CustomerMapper baseFilesCustomerMapper;
    /**
     * 客户分销规则dao
     */
    private DistributionRuleMapper baseFilesDistributionRuleMapper;
	/**
	 * 税种dao
	 */
	private FinanceMapper baseFilesFinanceMapper;

	/**
	 * 部门档案dao
	 */
	private DepartMentMapper baseFilesDepartmentMapper;

	/**
	 * 人员档案dao
	 */
	private PersonnelMapper baseFilesPersonnelMapper;

	/**
	 * 联系人档案dao
	 */
	private ContacterMapper baseFilesContacterMapper;
	/**
	 * 供应商档案dao
	 */
	private BuySupplierMapper baseFilesBuySupplierMapper;

	/**
	 * 客户关系dao
	 */
	private CMRMapper baseFilesCrmMapper;
	/**
	 * 销售区域dao
	 */
	private SalesAreaMapper baseFilesSalesAreaMapper;
	/**
	 * 科目档案
	 */
	private SubjectMapper baseSubjectMapper;


	private IStorageSummaryService baseStorageSummaryService;

	private ReceiptMapper salesReceiptMapper;

	private CustomerCapitalMapper customerCapitalMapper;

	private DispatchBillMapper dispatchBillMapper;

	public CustomerCapitalMapper getCustomerCapitalMapper() {
		return customerCapitalMapper;
	}

	public void setCustomerCapitalMapper(CustomerCapitalMapper customerCapitalMapper) {
		this.customerCapitalMapper = customerCapitalMapper;
	}

	public SubjectMapper getBaseSubjectMapper() {
		return baseSubjectMapper;
	}

	public void setBaseSubjectMapper(SubjectMapper baseSubjectMapper) {
		this.baseSubjectMapper = baseSubjectMapper;
	}

	public StorageMapper getBaseFilesStorageMapper() {
		return baseFilesStorageMapper;
	}

	public void setBaseFilesStorageMapper(StorageMapper baseFilesStorageMapper) {
		this.baseFilesStorageMapper = baseFilesStorageMapper;
	}

	public CustomerMapper getBaseFilesCustomerMapper() {
		return baseFilesCustomerMapper;
	}

	public void setBaseFilesCustomerMapper(CustomerMapper baseFilesCustomerMapper) {
		this.baseFilesCustomerMapper = baseFilesCustomerMapper;
	}

	public DepartMentMapper getBaseFilesDepartmentMapper() {
		return baseFilesDepartmentMapper;
	}

	public void setBaseFilesDepartmentMapper(
			DepartMentMapper baseFilesDepartmentMapper) {
		this.baseFilesDepartmentMapper = baseFilesDepartmentMapper;
	}

	public PersonnelMapper getBaseFilesPersonnelMapper() {
		return baseFilesPersonnelMapper;
	}

	public void setBaseFilesPersonnelMapper(PersonnelMapper baseFilesPersonnelMapper) {
		this.baseFilesPersonnelMapper = baseFilesPersonnelMapper;
	}

	public ContacterMapper getBaseFilesContacterMapper() {
		return baseFilesContacterMapper;
	}

	public void setBaseFilesContacterMapper(ContacterMapper baseFilesContacterMapper) {
		this.baseFilesContacterMapper = baseFilesContacterMapper;
	}

	public GoodsMapper getBaseFilesGoodsMapper() {
		return baseFilesGoodsMapper;
	}

	public void setBaseFilesGoodsMapper(GoodsMapper baseFilesGoodsMapper) {
		this.baseFilesGoodsMapper = baseFilesGoodsMapper;
	}

	public ReceiptMapper getSalesReceiptMapper() {
		return salesReceiptMapper;
	}

	public void setSalesReceiptMapper(ReceiptMapper salesReceiptMapper) {
		this.salesReceiptMapper = salesReceiptMapper;
	}

	public FinanceMapper getBaseFilesFinanceMapper() {
		return baseFilesFinanceMapper;
	}

	public void setBaseFilesFinanceMapper(FinanceMapper baseFilesFinanceMapper) {
		this.baseFilesFinanceMapper = baseFilesFinanceMapper;
	}

	public BuySupplierMapper getBaseFilesBuySupplierMapper() {
		return baseFilesBuySupplierMapper;
	}

	public void setBaseFilesBuySupplierMapper(
			BuySupplierMapper baseFilesBuySupplierMapper) {
		this.baseFilesBuySupplierMapper = baseFilesBuySupplierMapper;
	}

	public CMRMapper getBaseFilesCrmMapper() {
		return baseFilesCrmMapper;
	}

	public void setBaseFilesCrmMapper(CMRMapper baseFilesCrmMapper) {
		this.baseFilesCrmMapper = baseFilesCrmMapper;
	}

	public SalesAreaMapper getBaseFilesSalesAreaMapper() {
		return baseFilesSalesAreaMapper;
	}

	public void setBaseFilesSalesAreaMapper(SalesAreaMapper baseFilesSalesAreaMapper) {
		this.baseFilesSalesAreaMapper = baseFilesSalesAreaMapper;
	}

	public IStorageSummaryService getBaseStorageSummaryService() {
		return baseStorageSummaryService;
	}

	public void setBaseStorageSummaryService(IStorageSummaryService baseStorageSummaryService) {
		this.baseStorageSummaryService = baseStorageSummaryService;
	}

    public DistributionRuleMapper getBaseFilesDistributionRuleMapper() {
        return baseFilesDistributionRuleMapper;
    }

    public void setBaseFilesDistributionRuleMapper(DistributionRuleMapper baseFilesDistributionRuleMapper) {
        this.baseFilesDistributionRuleMapper = baseFilesDistributionRuleMapper;
    }

	public DispatchBillMapper getDispatchBillMapper() {
		return dispatchBillMapper;
	}

	public void setDispatchBillMapper(DispatchBillMapper dispatchBillMapper) {
		this.dispatchBillMapper = dispatchBillMapper;
	}
	private JsTaxTypeCodeMapper baseJsTaxTypeCodeMapper;

	public JsTaxTypeCodeMapper getBaseJsTaxTypeCodeMapper() {
		return baseJsTaxTypeCodeMapper;
	}

	public void setBaseJsTaxTypeCodeMapper(JsTaxTypeCodeMapper baseJsTaxTypeCodeMapper) {
		this.baseJsTaxTypeCodeMapper = baseJsTaxTypeCodeMapper;
	}

	/**
	 * 获取包含商品状态在内的商品基本信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public GoodsInfo getGoodsStatesInfoByID(String id) throws Exception{
		GoodsInfo goodsInfo = baseFilesGoodsMapper.getBaseGoodsInfo(id);
		return goodsInfo ;
	}
	/**
	 * 根据商品编码获取商品详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date May 10, 2013
	 */
	public GoodsInfo getGoodsInfoByID(String id) throws Exception{
		GoodsInfo goodsInfo = baseFilesGoodsMapper.getBaseGoodsInfo(id);
		if(null!=goodsInfo){
			Brand brand = baseFilesGoodsMapper.getBrandInfo(goodsInfo.getBrand());
			if(brand != null){
				goodsInfo.setBrandName(brand.getName());
			}
			StorageInfo storageInfo = baseFilesStorageMapper.showBaseStorageInfo(goodsInfo.getStorageid());
			if(storageInfo != null){
				goodsInfo.setStorageName(storageInfo.getName());
			}
			StorageLocation storageLocation = getStorageLocation(goodsInfo.getStoragelocation());
			if(null!=storageLocation){
				goodsInfo.setStoragelocationName(storageLocation.getName());
			}
			MeteringUnit unitInfo = baseFilesGoodsMapper.showMeteringUnitInfo(goodsInfo.getMainunit());
			if(unitInfo != null){
				goodsInfo.setMainunitName(unitInfo.getName());
			}
			//获取主辅单位换算比率（箱装量）
			GoodsInfo_MteringUnitInfo goodsInfo_MteringUnitInfo = getDefaultGoodsAuxMeterUnitInfo(id);
			if(null!=goodsInfo_MteringUnitInfo){
				goodsInfo.setBoxnum(goodsInfo_MteringUnitInfo.getRate());
				goodsInfo.setAuxunitid(goodsInfo_MteringUnitInfo.getMeteringunitid());
				goodsInfo.setAuxunitname(goodsInfo_MteringUnitInfo.getMeteringunitName());
			}
			if(null != goodsInfo.getHighestbuyprice() && null != goodsInfo.getBoxnum()){
				BigDecimal buyboxprice = goodsInfo.getHighestbuyprice().multiply(goodsInfo.getBoxnum());
				goodsInfo.setBuyboxprice(buyboxprice);
			}
			TaxType taxType = getTaxType(goodsInfo.getDefaulttaxtype());
			if(null!=taxType){
				goodsInfo.setDefaulttaxtypeName(taxType.getName());
			}
		}
		return goodsInfo;
	}
	/**
	 * 通过客户编号和客户店内码 获取商品档案信息
	 * 未找到合同价 则取总店的合同价
	 * @param customerid
	 * @param customergoodsid
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Dec 26, 2013
	 */
	public GoodsInfo getGoodsInfoByCustomerGoodsid(String customerid,String customergoodsid) throws Exception{
		CustomerPrice customerPrice = getBaseFilesCustomerMapper().getCustomerPriceByCustomerAndShopid(customerid, customergoodsid);
		if(null==customerPrice){
			Customer customer = getCustomerByID(customerid);
			if(null!=customer && null!=customer.getPid() && !"".equals(customer.getPid())){
				customerPrice = getBaseFilesCustomerMapper().getCustomerPriceByCustomerAndShopid(customer.getPid(), customergoodsid);
			}
		}
		if(null!=customerPrice){
			GoodsInfo goodsInfo = getAllGoodsInfoByID(customerPrice.getGoodsid());
			return goodsInfo;
		}
		return null;
	}
	/**
	 * 根据品牌编码获取品牌详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Oct 17, 2013
	 */
	public Brand getGoodsBrandByID(String id) throws Exception{
		Brand brand = baseFilesGoodsMapper.getBrandInfo(id);
		return brand;
	}

	/**
	 * 根据品牌名称获取品牌信息
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Brand getGoodsBrandByName(String name)throws Exception{
		Brand brand = baseFilesGoodsMapper.getBrandInfoByName(name);
		return brand;
	}
	/**
	 * 获取商品的成本价
	 * 商品存在核算成本价时，取核算成本价
	 * 否则取最新库存价（平均成本）
	 * 否则取最高采购价
	 * @param goodsInfo
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Sep 16, 2013
	 */
	public BigDecimal getGoodsCostprice(String storageid,GoodsInfo goodsInfo) throws Exception{
        //未指定仓库时，去商品的成本价
        //指定仓库时，去该仓库的成本价 该仓库不存在该商品时，取商品的成本价
        if(null!=goodsInfo){
            //仓库是否独立核算 0否1是 默认否
            String isStorageAccount = getSysParamValue("IsStorageAccount");
            if(StringUtils.isEmpty(isStorageAccount)){
                isStorageAccount = "0";
            }
            if(StringUtils.isNotEmpty(storageid) && "1".equals(isStorageAccount)){
                StorageInfo storageInfo = getStorageInfoByID(storageid);
                //判断仓库是否独立核算 独立核算时 成本价取该仓库的成本价
                if("1".equals(storageInfo.getIsaloneaccount())){
                    StorageSummary storageSummary = getBaseStorageSummaryService().getStorageSummaryByStorageAndGoods(storageid,goodsInfo.getId());
                    if(null!=storageSummary){
                        return storageSummary.getCostprice();
                    }else{
                        return goodsInfo.getNewstorageprice();
                    }
                }else{
                    return goodsInfo.getNewstorageprice();
                }
            }else{
                if(null!=goodsInfo.getNewstorageprice()){
                    return goodsInfo.getNewstorageprice();
                }else{
                    return BigDecimal.ZERO;
                }
            }
        }else{
            return BigDecimal.ZERO;
        }

	}
    /**
     * 获取商品的成本价
     * 商品存在核算成本价时，取核算成本价
     * 		否则取最新库存价（平均成本）
     * 		否则取最高采购价
     * @param storageid
     * @param goodsid
     * @return
     * @throws Exception
     * @author chenwei
     * @date Sep 16, 2013
     */
    public BigDecimal getGoodsCostprice(String storageid,String goodsid) throws Exception{
        GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
        if(null!=goodsInfo){
            //仓库是否独立核算 0否1是 默认否
            String isStorageAccount = getSysParamValue("IsStorageAccount");
            if(StringUtils.isEmpty(isStorageAccount)){
                isStorageAccount = "0";
            }
            if(StringUtils.isNotEmpty(storageid) && "1".equals(isStorageAccount)){
                StorageInfo storageInfo = getStorageInfoByID(storageid);
                //判断仓库是否独立核算 独立核算时 成本价取该仓库的成本价
                if("1".equals(storageInfo.getIsaloneaccount())){
                    StorageSummary storageSummary = getBaseStorageSummaryService().getStorageSummaryByStorageAndGoods(storageid,goodsInfo.getId());
                    if(null!=storageSummary){
                        return storageSummary.getCostprice();
                    }else{
                        return goodsInfo.getNewstorageprice();
                    }
                }else{
                    return goodsInfo.getNewstorageprice();
                }
            }else{
                if(null!=goodsInfo.getNewstorageprice()){
                    return goodsInfo.getNewstorageprice();
                }else{
                    return BigDecimal.ZERO;
                }
            }
        }else{
            return BigDecimal.ZERO;
        }
    }
	/**
	 * 获取商品档案全部信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date May 21, 2013
	 */
	public GoodsInfo getAllGoodsInfoByID(String id) throws Exception{
		GoodsInfo goodsInfo = baseFilesGoodsMapper.getGoodsInfo(id);
		if(null!=goodsInfo){
			Brand brand = baseFilesGoodsMapper.getBrandInfo(goodsInfo.getBrand());
			if(brand != null){
				goodsInfo.setBrandName(brand.getName());
			}
			StorageInfo storageInfo = baseFilesStorageMapper.showBaseStorageInfo(goodsInfo.getStorageid());
			if(storageInfo != null){
				goodsInfo.setStorageName(storageInfo.getName());
			}
			//主单位
			MeteringUnit unitInfo = baseFilesGoodsMapper.showMeteringUnitInfo(goodsInfo.getMainunit());
			if(unitInfo != null){
				goodsInfo.setMainunitName(unitInfo.getName());
			}
			//辅单位
			MeteringUnit auxUnitInfo = getGoodsAuxUnitInfoByGoodsid(id);
			if(null != auxUnitInfo){
				goodsInfo.setAuxunitid(auxUnitInfo.getId());
				goodsInfo.setAuxunitname(auxUnitInfo.getName());
			}
			//获取主辅单位换算比率（箱装量）
			GoodsInfo_MteringUnitInfo goodsInfo_MteringUnitInfo = getDefaultGoodsAuxMeterUnitInfo(id);
			if(null!=goodsInfo_MteringUnitInfo){
				goodsInfo.setBoxnum(goodsInfo_MteringUnitInfo.getRate());
//				MeteringUnit auxunitInfo = baseFilesGoodsMapper.showMeteringUnitInfo(goodsInfo_MteringUnitInfo.getMeteringunitid());
//				if(auxunitInfo != null){
//					goodsInfo.setAuxunitname(auxunitInfo.getName());
//					goodsInfo.setAuxunitid(goodsInfo_MteringUnitInfo.getMeteringunitid());
//				}
			}
			TaxType taxType = getTaxType(goodsInfo.getDefaulttaxtype());
			if(null!=taxType){
				goodsInfo.setDefaulttaxtypeName(taxType.getName());
				goodsInfo.setTaxrate(taxType.getRate().divide(new BigDecimal(100),6,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1)));
			}
			if(StringUtils.isNotEmpty(goodsInfo.getJstaxsortid())){
				JsTaxTypeCode jsTaxTypeCode = baseJsTaxTypeCodeMapper.getJsTaxTypeCodeById(goodsInfo.getJstaxsortid());
				if(null!=jsTaxTypeCode){
					goodsInfo.setJstaxsortname(jsTaxTypeCode.getGoodsname());
				}
			}
		}
		return goodsInfo;
	}

	/**
	 * 获取商品档案全部信息(无缓存)
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2017-01-13
	 */
	public GoodsInfo getAllGoodsInfoByIDNoCache(String id) throws Exception{
		GoodsInfo goodsInfo = baseFilesGoodsMapper.getBaseGoodsInfoNoCache(id);
		if(null!=goodsInfo){
			Brand brand = baseFilesGoodsMapper.getBrandInfo(goodsInfo.getBrand());
			if(brand != null){
				goodsInfo.setBrandName(brand.getName());
			}
			StorageInfo storageInfo = baseFilesStorageMapper.showBaseStorageInfo(goodsInfo.getStorageid());
			if(storageInfo != null){
				goodsInfo.setStorageName(storageInfo.getName());
			}
			//主单位
			MeteringUnit unitInfo = baseFilesGoodsMapper.showMeteringUnitInfo(goodsInfo.getMainunit());
			if(unitInfo != null){
				goodsInfo.setMainunitName(unitInfo.getName());
			}
			//辅单位
			MeteringUnit auxUnitInfo = getGoodsAuxUnitInfoByGoodsid(id);
			if(null != auxUnitInfo){
				goodsInfo.setAuxunitid(auxUnitInfo.getId());
				goodsInfo.setAuxunitname(auxUnitInfo.getName());
			}
			//获取主辅单位换算比率（箱装量）
			GoodsInfo_MteringUnitInfo goodsInfo_MteringUnitInfo = getDefaultGoodsAuxMeterUnitInfo(id);
			if(null!=goodsInfo_MteringUnitInfo){
				goodsInfo.setBoxnum(goodsInfo_MteringUnitInfo.getRate());
//				MeteringUnit auxunitInfo = baseFilesGoodsMapper.showMeteringUnitInfo(goodsInfo_MteringUnitInfo.getMeteringunitid());
//				if(auxunitInfo != null){
//					goodsInfo.setAuxunitname(auxunitInfo.getName());
//					goodsInfo.setAuxunitid(goodsInfo_MteringUnitInfo.getMeteringunitid());
//				}
			}
			TaxType taxType = getTaxType(goodsInfo.getDefaulttaxtype());
			if(null!=taxType){
				goodsInfo.setDefaulttaxtypeName(taxType.getName());
				goodsInfo.setTaxrate(taxType.getRate().divide(new BigDecimal(100),6,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1)));
			}
		}
		return goodsInfo;
	}
	/**
	 * 根据商品助记符获取商品档案
	 * @param spell
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Dec 30, 2013
	 */
	public GoodsInfo getGoodsInfoBySpell(String spell) throws Exception{
		GoodsInfo goodsInfo = getBaseGoodsMapper().getGoodsInfoBySpellLimitOne(spell);
		return goodsInfo;
	}

	/**
	 * 根据条形码获取商品档案
	 * @param barcode
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Mar 10, 2014
	 */
	public GoodsInfo getGoodsInfoByBarcode(String barcode)throws Exception{
		GoodsInfo goodsInfo = getBaseGoodsMapper().getGoodsInfoByBarcodeLimitOne(barcode);
		return goodsInfo;
	}

	/**
	 * 根据商品编号获取商品的平均采购价
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jul 17, 2013
	 */
	public BigDecimal getGoodsAvgBuyprice(String id) throws Exception{
		BigDecimal avgprice = new BigDecimal(0);
		GoodsInfo goodsInfo = baseFilesGoodsMapper.getGoodsInfo(id);
		if(null!=goodsInfo){
			if(null==goodsInfo.getNewstorageprice() ||  goodsInfo.getNewstorageprice().compareTo(avgprice)==0){
				avgprice = goodsInfo.getHighestbuyprice();
			}else{
				avgprice = goodsInfo.getNewstorageprice();
			}
		}
		return avgprice;
	}
	/**
	 * 根据客户和商品编码获取该客户的合同价或者价格套价格
	 * @param goodsid
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Dec 26, 2013
	 */
	public BigDecimal getGoodsPriceByCustomer(String goodsid,String customerid) throws Exception{
		BigDecimal price = BigDecimal.ZERO;
		if(null==goodsid || "".equals(goodsid.trim())){
			return price;
		}
		goodsid=goodsid.trim();
		if(null==customerid || "".equals(customerid.trim())){
			GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid); //商品信息
			if(null!=goodsInfo){
				BigDecimal baseTaxPrice = goodsInfo.getBasesaleprice(); //取基准销售价
				if( null  == baseTaxPrice){
					baseTaxPrice = new BigDecimal(0);
				}
				price = baseTaxPrice;
			}
		}else{
			customerid=customerid.trim();
			CustomerPrice customerPrice = getBaseFilesCustomerMapper().getCustomerPriceByCustomerAndGoods(customerid, goodsid);
			if(null==customerPrice){
				Customer customer = getCustomerByID(customerid);
				if(null != customer && null!= customer.getPid() && !"".equals(customer.getPid())){
					customerPrice = getBaseFilesCustomerMapper().getCustomerPriceByCustomerAndGoods(customer.getPid(), goodsid);
				}
			}
			if( null != customerPrice){ //取合同价
				BigDecimal customerTaxPrice = customerPrice.getPrice();
				if( null == customerTaxPrice){
					customerTaxPrice = new BigDecimal(0);
				}
				price = customerTaxPrice;
			}
			else{
				GoodsInfo_PriceInfo priceInfo = getPriceInfo(goodsid, customerid); //客户的价格套信息
				if( null != priceInfo ){ //如果客户设置了价格套信息，则取价格套信息中的价格
					price = priceInfo.getTaxprice();
				}
				else{
					GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid); //商品信息
					if(null!=goodsInfo){
						BigDecimal baseTaxPrice = goodsInfo.getBasesaleprice(); //取基准销售价
						if( null  == baseTaxPrice){
							baseTaxPrice = new BigDecimal(0);
						}
						price = baseTaxPrice;
					}
				}
			}
		}
		return price;
	}

	/**
	 * 根据客户和商品编码获取该客户价格套价格
	 * @param goodsid
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Dec 26, 2013
	 */
	public BigDecimal getGoodsPriceSetByCustomer(String goodsid,String customerid) throws Exception{
		BigDecimal price = BigDecimal.ZERO;
		if(null==goodsid || "".equals(goodsid.trim())){
			return price;
		}
		goodsid=goodsid.trim();
		if(null==customerid || "".equals(customerid.trim())){
			return price;
		}
		GoodsInfo_PriceInfo priceInfo = getPriceInfo(goodsid, customerid); //客户的价格套信息
		if( null != priceInfo ){ //如果客户设置了价格套信息，则取价格套信息中的价格
			price = priceInfo.getTaxprice();
		}
		return price;
	}
	/**
	 * 更新商品档案成本价
	 * @param goodsInfo
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Sep 16, 2013
	 */
	public boolean updateGoodsCostprice(GoodsInfo goodsInfo,String remark,String billid) throws Exception{
		boolean flag = false;
		if(null!=goodsInfo){
			GoodsInfo oldGoodsInfo = getAllGoodsInfoByID(goodsInfo.getId());
			if(null!=oldGoodsInfo.getNewstorageprice() && null!=goodsInfo.getNewstorageprice() && oldGoodsInfo.getNewstorageprice().compareTo(goodsInfo.getNewstorageprice())!=0){
				//添加商品成本价发生变化的记录
				baseFilesGoodsMapper.addGoodsCostpriceChangeLog(goodsInfo.getId(), goodsInfo.getNewstorageprice(),remark,billid);
			}
			int  i =baseFilesGoodsMapper.updateGoodsInfoWriteBack(goodsInfo);
			flag = i>0;
		}else{
		}
		return flag;
	}
	/**
	 * 获取商品辅助计量单位列表
	 * @param goodsId 商品编号
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 13, 2013
	 */
	public List<GoodsInfo_MteringUnitInfo> getUnitInfoList(String goodsId) throws Exception{
		List<GoodsInfo_MteringUnitInfo> list = baseFilesGoodsMapper.getMUListByGoodsId(goodsId);
		for(GoodsInfo_MteringUnitInfo info: list){
			MeteringUnit unit = baseFilesGoodsMapper.showMeteringUnitInfo(info.getMeteringunitid());
			if(unit != null){
				info.setMeteringunitName(unit.getName());
			}
		}
		return list;
	}

	/**
	 * 获取商品的默认辅单位
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Sep 28, 2013
	 */
	public MeteringUnit getGoodsDefaulAuxunit(String goodsid) throws Exception{
		GoodsInfo_MteringUnitInfo goodsInfo_MteringUnitInfo = baseFilesGoodsMapper.getMUInfoByGoodsIdAndIsdefault(goodsid);
		if(null!=goodsInfo_MteringUnitInfo){
			MeteringUnit unit = baseFilesGoodsMapper.showMeteringUnitInfo(goodsInfo_MteringUnitInfo.getMeteringunitid());
			return unit;
		}else{
			return null;
		}
	}
	/**
	 * 获取客户对应商品价格套的信息
	 * @param goodsId
	 * @param customerId
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 13, 2013
	 */
	public GoodsInfo_PriceInfo getPriceInfo(String goodsId, String customerId) throws Exception{
		String priceCode = null;
		if(StringUtils.isEmpty(priceCode)){
			priceCode = baseFilesCustomerMapper.getCustomerPriceInfo(customerId);
		}
		GoodsInfo_PriceInfo priceInfo = baseFilesGoodsMapper.getPriceInfoByGoodsAndCode(goodsId, priceCode);
		return priceInfo;
	}

	/**
	 * 根据仓库编码获取仓库信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date May 10, 2013
	 */
	public StorageInfo getStorageInfoByID(String id) throws Exception{
		StorageInfo storageInfo = baseFilesStorageMapper.showBaseStorageInfo(id);
		return storageInfo;
	}
	/**
	 * 根据仓库名称获取仓库档案信息
	 * @param name
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2014年7月21日
	 */
	public StorageInfo getStorageInfoByName(String name) throws Exception{
		StorageInfo storageInfo = baseFilesStorageMapper.getStorageInfoByName(name);
		return storageInfo;
	}
	/**
	 * 根据车销人员用户编号获取对应车销仓库
	 * @param userid		用户编号
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Sep 2, 2013
	 */
	public StorageInfo getStorageInfoByCarsaleuser(String userid) throws Exception{
		SysUser sysUser = getBaseSysUserMapper().showSysUserInfo(userid);
		if(null!=sysUser && StringUtils.isNotEmpty(sysUser.getPersonnelid())){
			StorageInfo storageInfo = baseFilesStorageMapper.getStorageInfoByCarsaleuser(sysUser.getPersonnelid());
			return storageInfo;
		}else{
			return null;
		}
	}

	/**
	 * 获取仓库档案列表
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 5, 2013
	 */
	@Override
	public List getStorageInfoAllList() throws Exception{
		PageMap pageMap = new PageMap();
		String dataSql = getDataAccessRule("t_base_storage_info", "t");
		pageMap.setDataSql(dataSql);
		List list = baseFilesStorageMapper.getStorageInfoAllList(pageMap);
		return list;
	}

	/**
	 * 获取启用的仓库档案列表
	 * @param
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Mar 05, 2018
	 */
	@Override
	public List getStorageInfoOpenList() throws Exception {
		PageMap pageMap = new PageMap();
		String dataSql = getDataAccessRule("t_base_storage_info", "t");
		pageMap.setDataSql(dataSql);
		List list = baseFilesStorageMapper.getStorageInfoOpenList(pageMap);
		return list;
	}

	/**
	 * 根据库位编码获取库位信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date May 17, 2013
	 */
	public StorageLocation getStorageLocationByID(String id) throws Exception{
		StorageLocation storageLocation = baseFilesStorageMapper.showStorageLocationInfo(id);
		return storageLocation;
	}
	/**
	 * 通过税种编号获取税种信息 没有则获取默认税种
	 * @param id 税种编号
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 16, 2013
	 */
	public TaxType getTaxType(String id) throws Exception{
		TaxType taxType = baseFilesFinanceMapper.getTaxTypeInfo(id);
		if(null!=taxType){
			return taxType;
		}else{
			String value = getSysParamValue("DEFAULTAXTYPE");
			TaxType defaultaxType = baseFilesFinanceMapper.getTaxTypeInfo(value);
			return defaultaxType;
		}
	}

	/**
	 * 通过库位编号获取库位信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 31, 2013
	 */
	public StorageLocation getStorageLocation(String id) throws Exception{
		StorageLocation storageLocation = baseFilesStorageMapper.showStorageLocationInfo(id);
		return storageLocation;
	}

	/**
	 * 根据商品编码,主单位数量,辅单位编码
	 * 计算得到金额,辅单位数量,辅单位数量描述信息等
	 * @param goodsid		商品编码
	 * @param auxunitid		辅单位编码
	 * @param price			商品单价
	 * @param unitNum		商品主单位数量
	 */
	public Map countGoodsInfoNumber(String goodsid, String auxunitid,BigDecimal price,BigDecimal unitNum) throws Exception {
		//保留几位小数 默认为2位
		int scale = 2;
		GoodsInfo goodsInfo = baseFilesGoodsMapper.getGoodsInfo(goodsid);
		//辅单位数量描述信息
		String auxnumDetail = "";
		//辅单位数量
		BigDecimal auxnum = new BigDecimal(0);
		BigDecimal amount = new BigDecimal(0);
		Map map = new HashMap();
		if(null!=goodsInfo){
			//获取主计量单位名称
			goodsInfo.setMainunitName(baseFilesGoodsMapper.showMeteringUnitInfo(goodsInfo.getMainunit()).getName());
			//获取商品的辅助计量单位列表
			List<GoodsInfo_MteringUnitInfo> list = baseFilesGoodsMapper.getMUListByGoodsId(goodsid);
			GoodsInfo_MteringUnitInfo goodsInfo_MteringUnitInfo = null;
			//获取对应的辅单位换算信息
			for(GoodsInfo_MteringUnitInfo mteringUnitInfo : list){
				if(StringUtils.isNotEmpty(auxunitid) && auxunitid.equals(mteringUnitInfo.getMeteringunitid())){
					goodsInfo_MteringUnitInfo = mteringUnitInfo;
					break;
				}else{
					if("1".equals(mteringUnitInfo.getIsdefault())){
						goodsInfo_MteringUnitInfo = mteringUnitInfo;
						break;
					}
				}
			}
			if(null!=goodsInfo_MteringUnitInfo && null!=goodsInfo_MteringUnitInfo.getRate()){
				MeteringUnit meteringUnit=baseFilesGoodsMapper.showMeteringUnitInfo(goodsInfo_MteringUnitInfo.getMeteringunitid());
				if(null!=meteringUnit && StringUtils.isNotEmpty(meteringUnit.getName())){
					goodsInfo_MteringUnitInfo.setMeteringunitName(meteringUnit.getName());
				}
				//换算方式 辅比主
//				if("1".equals(goodsInfo_MteringUnitInfo.getMode())){
				//获取数量的绝对值
				BigDecimal absUnitnum = unitNum.abs();
				if(goodsInfo_MteringUnitInfo.getRate().compareTo(new BigDecimal(0))==0){
					goodsInfo_MteringUnitInfo.setRate(new BigDecimal(1));
				}
				//辅单位整数数量 = （主单位数量/换算比率）取整
				BigDecimal auxnumInteger = absUnitnum.divideAndRemainder(goodsInfo_MteringUnitInfo.getRate())[0];
				//主单位余数数量= （主单位数量/换算比率-辅单位整数数量）*换算比率
				BigDecimal remainder = absUnitnum.divideAndRemainder(goodsInfo_MteringUnitInfo.getRate())[1];
				//辅比主换算比率（箱装量）
				map.put("auxrate", goodsInfo_MteringUnitInfo.getRate());
				//数量为负数时
				if(unitNum.compareTo(new BigDecimal(0))==-1){
					map.put("auxremainder", CommonUtils.strDigitNumDeal("-"+remainder.toString()));
					map.put("auxInteger", "-"+auxnumInteger.setScale(0,BigDecimal.ROUND_HALF_UP).toString());
					//辅单位数量描述信息
					auxnumDetail = "-"+auxnumInteger.setScale(0,BigDecimal.ROUND_HALF_UP).toString() + goodsInfo_MteringUnitInfo.getMeteringunitName();
				}else{
					map.put("auxremainder", CommonUtils.strDigitNumDeal(remainder.toString()));
					map.put("auxInteger", auxnumInteger.setScale(0,BigDecimal.ROUND_HALF_UP).toString());
					//辅单位数量描述信息
					auxnumDetail = auxnumInteger.setScale(0,BigDecimal.ROUND_HALF_UP).toString() + goodsInfo_MteringUnitInfo.getMeteringunitName();
				}
				if(remainder.compareTo(BigDecimal.ZERO)!=0){
					auxnumDetail += remainder.toString() +goodsInfo.getMainunitName();
				}
				if(goodsInfo_MteringUnitInfo.getRate().doubleValue()>0){ //辅单位数量 = 主单位数量/换算比率
					auxnum = unitNum.divide(goodsInfo_MteringUnitInfo.getRate(),3,BigDecimal.ROUND_HALF_UP);
				}
//				}else if("2".equals(goodsInfo_MteringUnitInfo.getMode())){	//换算方式 主比辅
//					//辅单位数量 = 主单位数量*换算比率
//					auxnum = unitNum.multiply(goodsInfo_MteringUnitInfo.getRate()).setScale(scale,BigDecimal.ROUND_HALF_UP);
//					//辅单位数量描述信息
//					auxnumDetail = auxnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP).toString()+goodsInfo_MteringUnitInfo.getMeteringunitName();
//				}
				//金额 = 主单位数量*单价
				amount = unitNum.multiply(price).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
				//返回值


				map.clear();
				map.put("auxunitid", goodsInfo_MteringUnitInfo.getMeteringunitid());
				//辅单位数量
				map.put("auxnum", auxnum);
				//辅单位数量描述
				map.put("auxnumdetail", CommonUtils.strDigitNumDeal(auxnumDetail));
				//金额
				map.put("unitamount", amount);
			}else{
				//金额 = 主单位数量*单价
				amount = unitNum.multiply(price).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
				//返回值
				map.clear();
				if(null!=goodsInfo_MteringUnitInfo){
					map.put("auxunitid", goodsInfo_MteringUnitInfo.getMeteringunitid());
				}else{
					map.put("auxunitid", "");
				}
				//辅单位数量
				map.put("auxnum", 0);
				//辅单位数量描述
				map.put("auxnumdetail", "");
				//金额
				map.put("unitamount", amount);
			}
			map.put("unitname", goodsInfo.getMainunitName());
			map.put("auxunitname", goodsInfo_MteringUnitInfo.getMeteringunitName()); //辅单位名称
		}
		return map;
	}

	/**
	 * 通过商品编码、主单位数量、辅单位编码计算得到辅单位数量及多余的主单位数量,辅单位数量描述信息
	 *
	 * @param goodsId		商品编码
	 * @param auxUnitId		辅单位编码
	 * @param unitNum		商品主单位数量
	 */
	@Override
	public Map countGoodsInfoNumber(String goodsId, String auxUnitId, BigDecimal unitNum) throws Exception {
		int scale = 2; //默认保留2位小数位
		String auxnumDetail = ""; //辅单位数量描述信息
		BigDecimal auxnum = new BigDecimal(0); //辅单位数量
		if(null==unitNum){
			unitNum = BigDecimal.ZERO;
		}
		Map map = new HashMap();
		if(null==goodsId || "".equals(goodsId.trim())){
			return map;
		}

		GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsId);
		if(null != goodsInfo){
			if(null != goodsInfo.getGrossweight()){
				map.put("totalboxweight", unitNum.multiply(goodsInfo.getGrossweight()).setScale(6, BigDecimal.ROUND_HALF_UP));
			}else{
				map.put("totalboxweight",BigDecimal.ZERO);
			}
			if(null != goodsInfo.getSinglevolume()){
				map.put("totalboxvolume", unitNum.multiply(goodsInfo.getSinglevolume()).setScale(6, BigDecimal.ROUND_HALF_UP));
			}else{
				map.put("totalboxvolume",BigDecimal.ZERO);
			}
//			MeteringUnit maiMeteringUnit = baseFilesGoodsMapper.showMeteringUnitInfo(goodsInfo.getMainunit());
//			if(null!=maiMeteringUnit){
//				goodsInfo.setMainunitName(maiMeteringUnit.getName()); //获取主计量单位名称
//			}
//			List<GoodsInfo_MteringUnitInfo> list = baseFilesGoodsMapper.getMUListByGoodsId(goodsId); //获取商品的辅助计量单位列表
//			GoodsInfo_MteringUnitInfo goodsInfo_MteringUnitInfo = null;
//			for(GoodsInfo_MteringUnitInfo mteringUnitInfo : list){ //获取对应的辅单位换算信息
//				if(StringUtils.isNotEmpty(auxUnitId) && auxUnitId.equals(mteringUnitInfo.getMeteringunitid())){
//					goodsInfo_MteringUnitInfo = mteringUnitInfo;
//					break;
//				}else{
//					if("1".equals(mteringUnitInfo.getIsdefault())){
//						goodsInfo_MteringUnitInfo = mteringUnitInfo;
//						break;
//					}
//				}
//			}
			if(null!=goodsInfo.getBoxnum()){
//				MeteringUnit meteringUnit=baseFilesGoodsMapper.showMeteringUnitInfo(goodsInfo_MteringUnitInfo.getMeteringunitid());
//				if(null!=meteringUnit && StringUtils.isNotEmpty(meteringUnit.getName())){
//					goodsInfo_MteringUnitInfo.setMeteringunitName(meteringUnit.getName());
//				}
//				if("1".equals(goodsInfo_MteringUnitInfo.getMode())){ //换算方式为辅比主
				//获取数量的绝对值
				BigDecimal absUnitnum = unitNum.abs();
				if(goodsInfo.getBoxnum().compareTo(new BigDecimal(0))==0){
                    goodsInfo.setBoxnum(new BigDecimal(1));
				}
				//辅单位整数数量 = （主单位数量/换算比率）取整
				BigDecimal auxnumInteger = absUnitnum.divideAndRemainder(goodsInfo.getBoxnum())[0];
				//主单位余数数量= （主单位数量/换算比率-辅单位整数数量）*换算比率
				BigDecimal remainder = absUnitnum.divideAndRemainder(goodsInfo.getBoxnum())[1];

				//辅比主换算比率（箱装量）
				map.put("auxrate", goodsInfo.getBoxnum());
				//数量为负数时
				if(unitNum.compareTo(new BigDecimal(0))==-1){
					map.put("auxremainder", CommonUtils.strDigitNumDeal("-"+remainder.toString()));
					map.put("auxInteger", "-"+auxnumInteger.setScale(0,BigDecimal.ROUND_HALF_UP).toString());
					//辅单位数量描述信息
					auxnumDetail = "-"+auxnumInteger.setScale(0,BigDecimal.ROUND_HALF_UP).toString() + goodsInfo.getAuxunitname();
				}else{
					map.put("auxremainder", CommonUtils.strDigitNumDeal(remainder.toString()));
					map.put("auxInteger", auxnumInteger.setScale(0,BigDecimal.ROUND_HALF_UP).toString());
					//辅单位数量描述信息
					auxnumDetail = auxnumInteger.setScale(0,BigDecimal.ROUND_HALF_UP).toString() + goodsInfo.getAuxunitname();
				}
				if(remainder.compareTo(BigDecimal.ZERO)!=0){
					auxnumDetail += remainder.toString() +goodsInfo.getMainunitName();
				}
				if(goodsInfo.getBoxnum().doubleValue()>0){ //辅单位数量 = 主单位数量/换算比率
					auxnum = unitNum.divide(goodsInfo.getBoxnum(),3,BigDecimal.ROUND_HALF_UP);
				}
//				}else if("2".equals(goodsInfo_MteringUnitInfo.getMode())){	//换算方式 主比辅
//					auxnum = unitNum.multiply(goodsInfo_MteringUnitInfo.getRate()).setScale(scale,BigDecimal.ROUND_HALF_UP); //辅单位数量 = 主单位数量*换算比率
//					auxnumDetail = auxnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP).toString()+goodsInfo_MteringUnitInfo.getMeteringunitName(); //辅单位数量描述信息
//				}
				//map.put("taxtype", goodsInfo.getDefaulttaxtype());
				if(null!=goodsInfo.getAuxunitid()){
					map.put("auxunitid", goodsInfo.getAuxunitid());
				}else{
					map.put("auxunitid", "");
				}
				map.put("auxnum", auxnum); //辅单位数量
				map.put("auxnumdetail", CommonUtils.strDigitNumDeal(auxnumDetail)); //辅单位数量描述
				map.put("auxunitname", goodsInfo.getAuxunitname()); //辅单位名称
			}else{
				map.put("auxremainder", "0");
				map.put("auxInteger", "0");
			}
			map.put("unitname", goodsInfo.getMainunitName());
		}
		return map;
	}
	/**
	 * 根据主单位数量和商品编码，获取商品各辅单位数量描述.
	 * 返回默认辅单位数量auxnumdefault 默认辅单位数量描述auxnumdetaildefault 默认辅单位名称auxunitnamedefault
	 * 其他辅单位数量auxnum(1,2...) 辅单位数量描述auxnumdetail(1,2..) 辅单位名称auxunitname(1,2..).
	 * 如果alias不为空 map中的key值将以alias为前缀
	 * @param unitNum		主单位数量
	 * @param goodsid		商品编码
	 * @param alias			跟返回的map加上key值
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date May 16, 2013
	 */
	public Map getAuxnumsByGoods(BigDecimal unitNum,String goodsid,String alias) throws Exception{
		//默认保留2位小数位
		int scale = 2;
		GoodsInfo goodsInfo = baseFilesGoodsMapper.getGoodsInfo(goodsid);
		if(null==alias){
			alias = "";
		}
		Map map = null;
		if(null!=goodsInfo){
			map = new HashMap();
			MeteringUnit maiMeteringUnit = baseFilesGoodsMapper.showMeteringUnitInfo(goodsInfo.getMainunit());
			if(null!=maiMeteringUnit){
				goodsInfo.setMainunitName(maiMeteringUnit.getName()); //获取主计量单位名称
			}
			List<GoodsInfo_MteringUnitInfo> list = baseFilesGoodsMapper.getMUListByGoodsId(goodsid); //获取商品的辅助计量单位列表
			int i=1;
			for(GoodsInfo_MteringUnitInfo goodsInfo_MteringUnitInfo : list){ //获取对应的辅单位换算信息
				MeteringUnit meteringUnit = baseFilesGoodsMapper.showMeteringUnitInfo(goodsInfo_MteringUnitInfo.getMeteringunitid());
				if(null!=meteringUnit){
					//获取辅单位名称
					goodsInfo_MteringUnitInfo.setMeteringunitName(meteringUnit.getName());
				}else{
					goodsInfo_MteringUnitInfo.setMeteringunitName("");
				}
				//辅单位数量描述
				String auxnumDetail = "";
				BigDecimal auxnum = new BigDecimal(0);

				if(null!=goodsInfo_MteringUnitInfo.getRate()){
					if("1".equals(goodsInfo_MteringUnitInfo.getMode())){ //换算方式为辅比主
						//获取数量的绝对值
						BigDecimal absUnitnum = unitNum.abs();
						if(goodsInfo_MteringUnitInfo.getRate().compareTo(new BigDecimal(0))==0){
							goodsInfo_MteringUnitInfo.setRate(new BigDecimal(1));
						}
						//主单位余数数量= 主单位数量%换算比率
						BigDecimal remainder = absUnitnum.remainder(goodsInfo_MteringUnitInfo.getRate());
						//辅单位整数数量（主单位换算辅单位后剩余的主单位数量） = (主单位数量-主单位余数数量)/换算比率（箱装量）
						BigDecimal auxnumInteger = absUnitnum.subtract(remainder).divide(goodsInfo_MteringUnitInfo.getRate());
						//辅比主换算比率（箱装量）
						map.put("auxrate", goodsInfo_MteringUnitInfo.getRate());
						//数量为负数时
						if(unitNum.compareTo(new BigDecimal(0))==-1){
							map.put("auxremainder", "-"+remainder.setScale(0,BigDecimal.ROUND_HALF_UP).toString());
							map.put("auxInteger", "-"+auxnumInteger.setScale(0,BigDecimal.ROUND_HALF_UP).toString());
							//辅单位数量描述信息
							auxnumDetail = "-"+auxnumInteger.setScale(0,BigDecimal.ROUND_HALF_UP).toString() + goodsInfo_MteringUnitInfo.getMeteringunitName();
						}else{
							map.put("auxremainder", remainder.setScale(0,BigDecimal.ROUND_HALF_UP).toString());
							map.put("auxInteger", auxnumInteger.setScale(0,BigDecimal.ROUND_HALF_UP).toString());
							//辅单位数量描述信息
							auxnumDetail = auxnumInteger.setScale(0,BigDecimal.ROUND_HALF_UP).toString() + goodsInfo_MteringUnitInfo.getMeteringunitName();
						}
						if(remainder.compareTo(BigDecimal.ZERO)!=0){
							auxnumDetail += remainder.setScale(0,BigDecimal.ROUND_HALF_UP).toString() +goodsInfo.getMainunitName();
						}
						if(goodsInfo_MteringUnitInfo.getRate().doubleValue()>0){ //辅单位数量 = 主单位数量/换算比率
							auxnum = unitNum.divide(goodsInfo_MteringUnitInfo.getRate(),scale,BigDecimal.ROUND_HALF_UP);
						}
					}else if("2".equals(goodsInfo_MteringUnitInfo.getMode())){	//换算方式 主比辅
						auxnum = unitNum.multiply(goodsInfo_MteringUnitInfo.getRate()).setScale(scale,BigDecimal.ROUND_HALF_UP); //辅单位数量 = 主单位数量*换算比率
						auxnumDetail = auxnum.setScale(scale,BigDecimal.ROUND_HALF_UP).toString()+goodsInfo_MteringUnitInfo.getMeteringunitName(); //辅单位数量描述信息
					}
					//判断是否默认辅计量单位
					if("1".equals(goodsInfo_MteringUnitInfo.getIsdefault())){
						map.put(alias+"auxnumdefault", auxnum); //辅单位数量
						map.put(alias+"auxnumdetaildefault", auxnumDetail); //辅单位数量描述
						map.put(alias+"auxunitnamedefault", goodsInfo_MteringUnitInfo.getMeteringunitName()); //辅单位名称
					}else{
						map.put(alias+"auxnum"+i, auxnum); //辅单位数量
						map.put(alias+"auxnumdetail"+i, auxnumDetail); //辅单位数量描述
						map.put(alias+"auxunitname"+i, goodsInfo_MteringUnitInfo.getMeteringunitName()); //辅单位名称
						i ++;
					}
				}
			}
		}
		return map;
	}


	/**
	 * 获取计量单位
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-5-15
	 */
	public MeteringUnit getMeteringUnitById(String id) throws Exception{
		return baseFilesGoodsMapper.showMeteringUnitInfo(id);
	}
	/**
	 * 获取商品默认辅助单位信息
	 * @param goodsId
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-5-15
	 */
	@Override
	public GoodsInfo_MteringUnitInfo getDefaultGoodsAuxMeterUnitInfo(String goodsId) throws Exception{
		List<GoodsInfo_MteringUnitInfo> list = baseFilesGoodsMapper.getMUListByGoodsId(goodsId); //获取商品的辅助计量单位列表
		GoodsInfo_MteringUnitInfo goodsInfo_MteringUnitInfo = null;
		for(GoodsInfo_MteringUnitInfo mteringUnitInfo : list){ //获取对应的辅单位换算信息
			if("1".equals(mteringUnitInfo.getIsdefault())){
				goodsInfo_MteringUnitInfo = mteringUnitInfo;
				MeteringUnit meteringUnit=baseFilesGoodsMapper.showMeteringUnitInfo(goodsInfo_MteringUnitInfo.getMeteringunitid());
				if(null!=meteringUnit && StringUtils.isNotEmpty(meteringUnit.getName())){
					goodsInfo_MteringUnitInfo.setMeteringunitName(meteringUnit.getName());
				}
				break;
			}
		}
		return goodsInfo_MteringUnitInfo;
	}
	/**
	 * 获取商品辅助单位信息
	 * @param goodsId
	 * @param auxUnitId
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-5-15
	 */
	public GoodsInfo_MteringUnitInfo getGoodsAuxMeterUnitInfo(String goodsId,String auxUnitId) throws Exception{

		List<GoodsInfo_MteringUnitInfo> list = baseFilesGoodsMapper.getMUListByGoodsId(goodsId); //获取商品的辅助计量单位列表
		GoodsInfo_MteringUnitInfo goodsInfo_MteringUnitInfo = null;
		for(GoodsInfo_MteringUnitInfo mteringUnitInfo : list){ //获取对应的辅单位换算信息
			if(mteringUnitInfo.getMeteringunitid().equals(auxUnitId)){
				goodsInfo_MteringUnitInfo = mteringUnitInfo;
				MeteringUnit meteringUnit=baseFilesGoodsMapper.showMeteringUnitInfo(goodsInfo_MteringUnitInfo.getMeteringunitid());
				if(null!=meteringUnit && StringUtils.isNotEmpty(meteringUnit.getName())){
					goodsInfo_MteringUnitInfo.setMeteringunitName(meteringUnit.getName());
				}
				break;
			}
		}
		return goodsInfo_MteringUnitInfo;
	}
	/**
	 * 根据商品编码 获取商品的默认辅单位信息
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jun 14, 2013
	 */
	public MeteringUnit getGoodsAuxUnitInfoByGoodsid(String goodsid) throws Exception{
		List<GoodsInfo_MteringUnitInfo> list = baseFilesGoodsMapper.getMUListByGoodsId(goodsid); //获取商品的辅助计量单位列表
		MeteringUnit meteringUnit = null;
		for(GoodsInfo_MteringUnitInfo mteringUnitInfo : list){ //获取对应的辅单位换算信息
			if("1".equals(mteringUnitInfo.getIsdefault())){
				meteringUnit=baseFilesGoodsMapper.showMeteringUnitInfo(mteringUnitInfo.getMeteringunitid());
				break;
			}
		}
		return meteringUnit;
	}
	/**
	 * 更新商品档案信息。回写商品档案
	 * @param goodsInfo
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date May 28, 2013
	 */
	public boolean updateGoodsInfo(GoodsInfo goodsInfo) throws Exception{
		if(null!=goodsInfo){
			goodsInfo.setOldId(goodsInfo.getId());
			int i = baseFilesGoodsMapper.editGoodsInfo(goodsInfo);
			return i>0;
		}else{
			return false;
		}
	}
	/**
	 * 获取供应商档案信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-6-7
	 */
	@Override
	public BuySupplier getSupplierInfoById(String id) throws Exception{
		return baseFilesBuySupplierMapper.getBuySupplier(id);
	}
	/**
	 * 根据编号获取部门档案信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jun 14, 2013
	 */
	public DepartMent getDepartMentById(String id) throws Exception{
		return baseFilesDepartmentMapper.getDepartmentInfo(id);
	}
	/**
	 * 根据编号获取人员信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jun 14, 2013
	 */
	public Personnel getPersonnelById(String id) throws Exception{
		return baseFilesPersonnelMapper.getPersonnelInfo(id);
	}
	/**
	 * 通过姓名获取人员
	 * @param name
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Sep 28, 2013
	 */
	public Personnel getPersonnelByName(String name) throws Exception{
		List list = baseFilesPersonnelMapper.getPersonnelByName(name);
		if(null!=list && list.size()==1){
			return (Personnel) list.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 根据部门编号 获取该部门下 与当前用户 相同名称的人员
	 * @param deptid
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2014年11月28日
	 */
	public Personnel getSamePersonnelByDeptid(String deptid,String personnelid) throws Exception{
		Personnel personnel = getPersonnelById(personnelid);
		if(null!=personnel){
			Personnel personnel1 = baseFilesPersonnelMapper.getPersonnelByDeptidAndName(deptid, personnel.getName());
			return personnel1;
		}else{
			return null;
		}
	}
	/**
	 * 根据编号获取联系人信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jun 14, 2013
	 */
	public Contacter getContacterById(String id) throws Exception{
		Map map = new HashMap();
		map.put("id", id);
		return baseFilesContacterMapper.getContacterDetail(map);
	}
	/**
	 * 根据客户编号获取联系人列表
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 12, 2013
	 */
	public List getContacterByCustomerid(String customerid) throws Exception{
		List list = baseFilesContacterMapper.getContacterListByCustomer("1", customerid);
		return list;
	}
	/**
	 * 根据供应商编号获取联系人列表
	 * @param supplierid
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 12, 2013
	 */
	public List getContacterBySupplierid(String supplierid) throws Exception{
		List list = baseFilesContacterMapper.getContacterListByCustomer("2", supplierid);
		return list;

	}
	/**
	 * 根据编号获取客户分类
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Dec 23, 2013
	 */
	public CustomerSort getCustomerSortByID(String id) throws Exception{
		Map map = new HashMap();
		map.put("id", id);
		return getBaseCustomerSortMapper().getCustomerSortDetail(map);
	}

	/**
	 * 根据客户编号获取客户信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jun 18, 2013
	 */
	public Customer getCustomerByID(String id) throws Exception{
		Map map = new HashMap();
		map.put("id", id);
		return baseFilesCustomerMapper.getCustomerDetail(map);
	}


	/**
	 * 根据客户编号获取客户信息(无缓存)
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2047-01-13
	 */
	public Customer getCustomerDetailNoCache(String id) throws Exception{
		return baseFilesCustomerMapper.getCustomerDetailNoCache(id);
	}

	/**
	 * 根据客户名称获取客户信息
	 * @param name
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2015-10-07
	 */
	public Customer getCustomerByName(String name)throws Exception{
		Customer customer = null;
		List<Customer> list = baseFilesCustomerMapper.getCustomerByName(name);
		if(list.size() != 0){
			customer = list.get(0);
		}
		return customer;
	}
	/**
	 * 根据总店编号获取门店列表
	 * @param pid
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2014年11月17日
	 */
	public List getCustomerListByPid(String pid) throws Exception{
		List list = baseFilesCustomerMapper.getCustomerListByPid(pid);
		return list;
	}
	/**
	 * 获取客户档案总店，如果没有上级则返回自身
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Sep 23, 2013
	 */
	public Customer getHeadCustomer(String id) throws Exception {
		Map map = new HashMap();
		map.put("id", id);
		Customer customer = baseFilesCustomerMapper.getCustomerDetail(map);
		if(customer != null){
			if(StringUtils.isNotEmpty(customer.getPid())){
				map.put("id", customer.getPid());
				Customer customer2 = baseFilesCustomerMapper.getCustomerDetail(map);
				return customer2;
			}
			else{
				return customer;
			}
		}
		return null;
	}

	/**
	 * 根据编号获取销售区域信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Sep 17, 2013
	 */
	public SalesArea getSalesareaByID(String id) throws Exception{
		Map map = new HashMap();
		map.put("id", id);
		return baseFilesSalesAreaMapper.getSalesAreaDetail(map);
	}
	/**
	 * 根据支付方式编号获取支付方式信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 12, 2013
	 */
	public Payment getPaymentByID(String id) throws Exception{
		Map map = new HashMap();
		map.put("id", id);
		return baseFilesFinanceMapper.getPaymentDetail(map);
	}
	/**
	 * 根据结算方式编号获取结算方式信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 12, 2013
	 */
	public Settlement getSettlementByID(String id) throws Exception{
		Map map = new HashMap();
		map.put("id", id);
		return baseFilesFinanceMapper.getSettlemetDetail(map);
	}
	/**
	 * 根据含税金额和税种 获取无税金额(税额保留6位小数)
	 * @param taxamount
	 * @param taxtype
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jun 11, 2013
	 */
	public BigDecimal getNotaxAmountByTaxAmount(BigDecimal taxamount,String taxtype) throws Exception{
		TaxType taxTypeInfo = getTaxType(taxtype);
		if(null==taxamount){
			taxamount = new BigDecimal(0);
		}
		if(null!=taxTypeInfo){
			//税率  保存6位小数
			BigDecimal taxrate = taxTypeInfo.getRate().divide(new BigDecimal(100),6, BigDecimal.ROUND_HALF_UP);
			//无税金额 = 含税金额/（1+税率） 保存6位小数
			BigDecimal notaxamount = taxamount.divide(taxrate.add(new BigDecimal(1)),decimalLen,BigDecimal.ROUND_HALF_UP);
			return notaxamount;
		}else{
			return new BigDecimal(0);
		}
	}
	/**
	 * 根据含税金额和税种 获取无税金额
	 * @param taxamount		金额
	 * @param taxtype		税种
	 * @param scale			保留位数
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Feb 14, 2014
	 */
	public BigDecimal getNotaxAmountByTaxAmount(BigDecimal taxamount,String taxtype,int scale) throws Exception{
		TaxType taxTypeInfo = getTaxType(taxtype);
		if(null==taxamount){
			taxamount = new BigDecimal(0);
		}
		if(null!=taxTypeInfo){
			//税率  保存6位小数
			BigDecimal taxrate = taxTypeInfo.getRate().divide(new BigDecimal(100),6, BigDecimal.ROUND_HALF_UP);
			//无税金额 = 含税金额/（1+税率） 保存6位小数
			BigDecimal notaxamount = taxamount.divide(taxrate.add(new BigDecimal(1)),scale,BigDecimal.ROUND_HALF_UP);
			return notaxamount;
		}else{
			return new BigDecimal(0);
		}
	}
	/**
	 * 获取仓库商品的安全库存
	 * @param goodsid
	 * @param storageid
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jun 19, 2013
	 */
	public BigDecimal getStorageInventoryByGoodsidAndStorageid(String goodsid,String storageid) throws Exception{
		GoodsInfo_StorageInfo goodsInfo_StorageInfo = baseFilesGoodsMapper.getGoodsInfoStorageInfo(goodsid, storageid);
		BigDecimal safenum = null;
		if(null==goodsInfo_StorageInfo){
			safenum = new BigDecimal(0);
		}else{
			if(null!=goodsInfo_StorageInfo.getSafeinventory()){
				safenum = goodsInfo_StorageInfo.getSafeinventory();
			}else{
				safenum = new BigDecimal(0);
			}
		}
		return safenum;
	}

//	@Override
//	public Personnel getPersonnelByGCB(String brandid, String customerid)
//			throws Exception {
//		Map map = new HashMap();
//		map.put("brandid", brandid);
//		map.put("customerid", customerid);
//		if(null!=brandid && null!=customerid){
//			return baseFilesPersonnelMapper.getPersonnelByGCB(map);
//		}else{
//			return null;
//		}
//	}
	/**
	 * 根据客户编号和品牌 获取品牌业务员信息
	 * @param brandid
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Nov 11, 2013
	 */
	public String getBrandUseridByCustomeridAndBrand(String brandid, String customerid) throws Exception{
		String personnelid = baseFilesPersonnelMapper.getBrandUserIdByCustomerAndBrand(customerid, brandid);
		if(null==personnelid){
			personnelid = "";
		}
		return personnelid;
	}
	/**
	 * 根据客户编号和品牌 获取厂家业务员信息
	 * @param brandid
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2014年11月6日
	 */
	public String getSupplieruserByCustomeridAndBrand(String brandid, String customerid) throws Exception{
		String personnelid = baseFilesPersonnelMapper.getSupplierUserIdByCustomerAndBrand(customerid, brandid);
		if(null==personnelid){
			personnelid = "";
		}
		return personnelid;
	}
	/**
	 * 获取销售方式所有列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Aug 2, 2013
	 */
	public List getSaleModeListData()throws Exception{
		return baseFilesCrmMapper.getSaleModeListData();
	}

	/**
	 * 获取销售机会来源分了所有列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Aug 2, 2013
	 */
	public List getSaleChanceSortListData()throws Exception{
		return baseFilesCrmMapper.getSaleChanceSortListData();
	}

	/**
	 * 获取任务分类所有列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Aug 2, 2013
	 */
	public List getTaskSortListData()throws Exception{
		return baseFilesCrmMapper.getTaskSortListData();
	}

	/**
	 * 获取市场活动分类所有列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Aug 2, 2013
	 */
	public List getmarketActivitySortListData()throws Exception{
		return baseFilesCrmMapper.getmarketActivitySortListData();
	}

	/**
	 * 获取税种档案所有列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Aug 2, 2013
	 */
	public List getTaxTypeListData()throws Exception{
		return baseFilesFinanceMapper.getTaxTypeListData();
	}

	/**
	 * 获取结算方式所有列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Aug 2, 2013
	 */
	public List getSettlementListData()throws Exception{
		return baseFilesFinanceMapper.getSettlementListData();
	}

	/**
	 * 获取支付方式所有列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Aug 2, 2013
	 */
	public List getPaymentListData()throws Exception{
		return baseFilesFinanceMapper.getPaymentListData();
	}

	/**
	 * 获取费用分类所有列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Aug 2, 2013
	 */
	public List getExpensesSortListData()throws Exception{
		return baseFilesFinanceMapper.getExpensesSortListData();
	}

	/**
	 * 根据辅单位，商品编码获取主单位
	 * @param unitNum
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Aug 2, 2013
	 */
	public Map retMainUnitByUnitAndGoodid(BigDecimal unitNum,String goodsid)throws Exception{
		GoodsInfo goodsInfo = baseFilesGoodsMapper.getGoodsInfo(goodsid);
		BigDecimal mainUnitNum = new BigDecimal(0);//主单位
		BigDecimal auxnumInteger = new BigDecimal(0);//剩余辅单位
		String auxunitname = "";
		String unitname = "";
		if(null!=goodsInfo){
			MeteringUnit meteringUnit = getMeteringUnitById(goodsInfo.getMainunit());
			if(null!=meteringUnit){
				unitname = meteringUnit.getName();
			}
		}
		if(null != goodsInfo){
			//根据商品编码获取辅助计量单位列表
			List<GoodsInfo_MteringUnitInfo> muList = baseFilesGoodsMapper.getMUListByGoodsId(goodsid);
			if(muList.size() != 0){
				for(GoodsInfo_MteringUnitInfo muInfo : muList){
					//判断是否为默认辅计量单位,是,则判断换算类型
					if("1".equals(muInfo.getIsdefault())){
						auxunitname = muInfo.getMeteringunitName();
						if("1".equals(muInfo.getType())){//固定
							//判断换算方式
//							if("1".equals(muInfo.getMode())){//辅比主
							if(null != muInfo.getRate()){
								//获取数量的绝对值
								BigDecimal absUnitnum = unitNum.abs();
								if(muInfo.getRate().compareTo(new BigDecimal(0))==0){
									muInfo.setRate(new BigDecimal(1));
								}
								mainUnitNum = unitNum.multiply(muInfo.getRate());
							}
//							}
//							else if("2".equals(muInfo.getMode())){//主比辅
//								if(null != muInfo.getRate()){
//									//获取数量的绝对值
//									BigDecimal absUnitnum = unitNum.abs();
//									if(muInfo.getRate().compareTo(new BigDecimal(0))==0){
//										muInfo.setRate(new BigDecimal(1));
//									}
//									//主单位整数
//									mainUnitNum = absUnitnum.remainder(muInfo.getRate()); //主整数数量= 辅单位数量%换算比率
//									//辅单位余数 = 辅单位数量 - 主单位整数*换算比率
//									auxnumInteger = absUnitnum.subtract(mainUnitNum.multiply(muInfo.getRate()));
//								}
//							}
						}
						else{//浮动
							break;
						}
					}
				}
			}
		}
		Map map = new HashMap();
		map.put("mainUnitNum", mainUnitNum);
		map.put("auxnumInteger", auxnumInteger);
		map.put("unitname", unitname);
		map.put("auxunitname", auxunitname);
		return map;
	}

	@Override
	public List getStorageInOutAllList(String type) throws Exception {
		PageMap pageMap = new PageMap();
		Map map = new HashMap();
		map.put("type", type);
		pageMap.setCondition(map);
		String dataSql = getDataAccessRule("t_base_storage_inout", null);
		pageMap.setDataSql(dataSql);
		List list  = baseFilesStorageMapper.getStorageInoutAllList(pageMap);
		return list;
	}
	/**
	 * 获取银行档案信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 23, 2013
	 */
	public Bank getBankInfoByID(String id) throws Exception{
		Bank bank = baseFilesFinanceMapper.getBankDetail(id);
		return bank;
	}
	/**
	 * 获取费用科目档案信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2014年11月12日
	 */
	public ExpensesSort getExpensesSortByID(String id) throws Exception{
		Map map = new HashMap();
		map.put("id", id);
		ExpensesSort expensesSort = baseFilesFinanceMapper.getExpensesSortDetail(map);
		return expensesSort;
	}
	/**
	 * 根据客户编号获取该客户的销售内勤用户信息
	 * 如果不存在销售内勤用户 则返回当前用户信息
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 28, 2013
	 */
	public SysUser getSalesIndoorSysUserByCustomerid(String customerid) throws Exception{
		Customer customer = getCustomerByID(customerid);
		SysUser sysUser = null;
		if(null!=customer){
			String salesIndoor = customer.getIndoorstaff();
			if(null!=salesIndoor && !"".equals(salesIndoor)){
				sysUser = getBaseSysUserMapper().checkSysUserByPerId(salesIndoor);
				if(null==sysUser){
					sysUser = new SysUser();
					sysUser.setPersonnelid(salesIndoor);
				}
			}
		}
		if(null==sysUser){
			sysUser = getSysUser();
		}
		return sysUser;
	}

	/**
	 * 根据客户编号获取该客户的销售内勤用户信息（手机订单）
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date Aug 28, 2013
	 */
	public SysUser getSalesIndoorSysUserByCustomeridForPhone(String customerid) throws Exception{
		Customer customer = getCustomerByID(customerid);
		SysUser sysUser = null;
		if(null!=customer){
			String salesIndoor = customer.getIndoorstaff();
			if(null!=salesIndoor && !"".equals(salesIndoor)){
				sysUser = getBaseSysUserMapper().checkSysUserByPerId(salesIndoor);
			}
		}
		return sysUser;
	}
	/**
	 * 根据客户编号 品牌编号计算应收日期
	 * 当客户有品牌结算方式的时候 按品牌结算方式计算
	 * 品牌编号为null或者为空的时候 取客户档案中的结算方式
	 * 如果结算方式不存在 则通过系统参数获取默认结算方式
	 * @param date
	 * @param customerid 		客户编号
	 * @param brandid         品牌编号
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Sep 18, 2013
	 */
	public String getReceiptDateBySettlement(Date date,String customerid,String brandid) throws Exception{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(null==date){
			date = new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 0);
		Customer customer = getCustomerByID(customerid);
		if(null!=customer){
			String settletype = null;
			CustomerBrandSettletype customerBrandSettletype = null;
			if(StringUtils.isNotEmpty(brandid)){
				customerBrandSettletype = getCustomerBrandSettletype(customerid,brandid);
				if(null!=customerBrandSettletype){
					settletype = customerBrandSettletype.getSettletype();
				}
			}
			if(StringUtils.isEmpty(settletype)){
				settletype = customer.getSettletype();
			}
			Map map = new HashMap();
			map.put("id", settletype);
			Settlement settlement = baseFilesFinanceMapper.getSettlemetDetail(map);
			int overgracedate = 0;
			if(null!=customer.getOvergracedate()){
				overgracedate = customer.getOvergracedate();
			}
			if(null!=settlement){
				//月结
				//当前日期在结算日之前 本月开始算应收日期
				//当前日期在结算日之后 下个月开始算应收日期
				if("1".equals(settlement.getType())){
					//客户结算日
					int settleday = 0;
					if(StringUtils.isNotEmpty(brandid) && null!=customerBrandSettletype){
						//结算日为空的时候 结算日默认为30号
						if(null!=customerBrandSettletype.getSettleday() && !"".equals(customerBrandSettletype.getSettleday())){
							if(StringUtils.isNumeric(customerBrandSettletype.getSettleday())){
								settleday = Integer.parseInt(customerBrandSettletype.getSettleday());
							}else{
								settleday = 30;
							}
						}
					}else{
						//结算日为空的时候 结算日默认为30号
						if(null!=customer.getSettleday() && !"".equals(customer.getSettleday())){
							if(StringUtils.isNumeric(customer.getSettleday())){
								settleday = Integer.parseInt(customer.getSettleday());
							}else{
								settleday = 30;
							}
						}
					}
					int nowday = CommonUtils.getDataMonthDay(date);
					if(nowday>=settleday){
						//下个月
						calendar.add(Calendar.MONTH,1);
					}
					//把日期设置为当月第一天
					calendar.set(Calendar.DATE, 0);
					calendar.add(Calendar.DAY_OF_MONTH, settleday+settlement.getDays().intValue()+overgracedate);

				}else if("2".equals(settlement.getType())){						//日结
					if(null!=customer && "1".equals(customer.getOvercontrol())){
						calendar.add(Calendar.DAY_OF_MONTH, settlement.getDays().intValue()+overgracedate);
					}else{
						calendar.add(Calendar.DAY_OF_MONTH, settlement.getDays().intValue());
					}
				}
			}else{
				//根据系统参数获取默认结算方式
				settletype = getSysParamValue("SETTLETYPE");
				map.put("id", settletype);
				Settlement defaulSettlement = baseFilesFinanceMapper.getSettlemetDetail(map);
				if(null!=defaulSettlement){
					//月结
					if("1".equals(defaulSettlement.getType())){
						//客户结算日
						int settleday = 0;
						//结算日为空的时候 结算日默认为30号
						if(null!=customer.getSettleday() && !"".equals(customer.getSettleday())){
							if(StringUtils.isNumeric(customer.getSettleday())){
								settleday = Integer.parseInt(customer.getSettleday());
							}else{
								settleday = 30;
							}
						}
						int nowday = CommonUtils.getDataMonthDay(date);
						if(nowday>=settleday){
							//下个月
							calendar.add(Calendar.MONTH,1);
						}
						//把日期设置为当月第一天
						calendar.set(Calendar.DATE, 0);
						calendar.add(Calendar.DAY_OF_MONTH, settleday+settlement.getDays().intValue()+overgracedate);
					}else if("2".equals(defaulSettlement.getType())){				//日结
						if(null!=customer && "1".equals(customer.getOvercontrol())){
							calendar.add(Calendar.DAY_OF_MONTH, defaulSettlement.getDays().intValue()+overgracedate);
						}else{
							calendar.add(Calendar.DAY_OF_MONTH, defaulSettlement.getDays().intValue());
						}
					}

				}
			}
		}
		return dateFormat.format(calendar.getTime());
	}

	@Override
	public String getAccessColunmnList(String tablename, String alias)
			throws Exception {
		String cols = getAccessColumnList(tablename,alias);
		return cols;
	}

	/**
	 * 根据客户编码和商品编码判断是否买过该商品
	 * @param customerid
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Apr 2, 2014
	 */
	public boolean checkCustomerGoods(String customerid,String goodsid)throws Exception{
		boolean flag = baseFilesCustomerMapper.checkCustomerGoods(customerid,goodsid) > 0;
		return flag;
	}

	/**
	 * 获取客户合同价，如果未查询到该客户合同价则查询上级是否有合同价
	 *
	 * @param customerId
	 * @param goodsId
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date Dec 23, 2013
	 */
	public CustomerPrice getCustomerPrice(String customerId, String goodsId) throws Exception {
		CustomerPrice customerPrice = null;
		customerPrice = getBaseFilesCustomerMapper().getCustomerPriceByCustomerAndGoods(customerId, goodsId);
		if (customerPrice != null) {
			return customerPrice;
		}
		Customer customer = getCustomerByID(customerId);
		if (customer != null && StringUtils.isNotEmpty(customer.getPid())) {
			return getCustomerPrice(customer.getPid(), goodsId);
		}
		return null;
	}

	@Override
	public BigDecimal getDefaultSalesPrice(String customerid,String goodsid)throws Exception{
		BigDecimal defaultprice = BigDecimal.ZERO;
		GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid); //商品信息
		if (goodsInfo != null) {
			CustomerPrice customerPrice = getCustomerPrice(customerid, goodsid);
			if (customerPrice != null) { //取合同价
				defaultprice = customerPrice.getPrice();
			} else {
				GoodsInfo_PriceInfo priceInfo = getPriceInfo(customerid, goodsid); //客户的价格套信息
				if (priceInfo != null) { //如果客户设置了价格套信息，则取价格套信息中的价格
					defaultprice = priceInfo.getTaxprice();
				} else {//取基准销售价
					defaultprice = goodsInfo.getBasesaleprice();
				}
			}
		}
		return defaultprice;
	}

	@Override
	public synchronized void doGlobalUpdateBillsBranduser() throws Exception {
		//获取是否执行客户历史数据系统参数
		String doEditCustomerTask = "1";
		SysParam sysParam = getBaseSysParamMapper().getSysParam("doEditCustomerTask");
		if(null != sysParam){
			doEditCustomerTask = sysParam.getPvalue();
		}
		if("1".equals(doEditCustomerTask)){
			Map map = new HashMap();

			String today = CommonUtils.getTodayDataStr();
			SysParam chdayssysParam = getBaseSysParamMapper().getSysParam("changeDaysBrandusersDatas");
			if(null != chdayssysParam){
				Integer days = Integer.parseInt(chdayssysParam.getPvalue().toString());
				String yeday = CommonUtils.getBeforeDateInDays(new Date(),days+1);
				map.put("startdate",yeday);
			}else{
				map.put("startdate",today);
			}

			int updatenum = 45000;
			//分批次执行品牌业务员数据
			Map numMap = baseFilesCustomerMapper.getBillDetailsNum();
			Integer demandbillnum = ((BigDecimal)numMap.get("demandbillnum")).intValue();
			Integer dispatchbillnum = ((BigDecimal)numMap.get("dispatchbillnum")).intValue();
			Integer ordercarnum = ((BigDecimal)numMap.get("ordercarnum")).intValue();
			Integer ordernum = ((BigDecimal)numMap.get("ordernum")).intValue();
			Integer receiptnum = ((BigDecimal)numMap.get("receiptnum")).intValue();
			Integer rejectbillnum = ((BigDecimal)numMap.get("rejectbillnum")).intValue();
			Integer saleoutnum = ((BigDecimal)numMap.get("saleoutnum")).intValue();
			Integer salerejectenternum = ((BigDecimal)numMap.get("salerejectenternum")).intValue();
			Integer salesinvoicenum = ((BigDecimal)numMap.get("salesinvoicenum")).intValue();
			Integer salesinvoicebillnum = ((BigDecimal)numMap.get("salesinvoicebillnum")).intValue();

			//品牌业务员
			map.put("employetype", "3");
			List<Map> branddelList = baseFilesPersonnelMapper.getNoPersonBrandAndCustomerListByEM(map);
			if(null != branddelList && branddelList.size() != 0){
				if(demandbillnum != 0){
					int i = 0;
					while (i <= demandbillnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > demandbillnum){
							endnum = demandbillnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editOrderDemandBCDel(map);
					}
				}
				if(dispatchbillnum != 0){
					int i = 0;
					while (i <= dispatchbillnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > dispatchbillnum){
							endnum = dispatchbillnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editOrderDispatchbillBCDel(map);
					}
				}
				if(ordercarnum != 0){
					int i = 0;
					while (i <= ordercarnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > ordercarnum){
							endnum = ordercarnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editOrderCarBCDel(map);
					}
				}
				if(ordernum != 0){
					int i = 0;
					while (i <= ordernum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > ordernum){
							endnum = ordernum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editOrderBCDel(map);
					}

				}
				if(receiptnum != 0){
					int i = 0;
					while (i <= receiptnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > receiptnum){
							endnum = receiptnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editOrderReceiptBCDel(map);
					}
				}
				if(rejectbillnum != 0){
					int i = 0;
					while (i <= rejectbillnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > rejectbillnum){
							endnum = rejectbillnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editOrderRejectbillBCDel(map);
					}
				}
				if(saleoutnum != 0){
					int i = 0;
					while (i <= saleoutnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > saleoutnum){
							endnum = saleoutnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editOrderSaleoutBCDel(map);
					}
				}
				if(salerejectenternum != 0){
					int i = 0;
					while (i <= salerejectenternum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > salerejectenternum){
							endnum = salerejectenternum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editOrderSalerejectEnterBCDel(map);
					}
				}
				if(salesinvoicenum != 0){
					int i = 0;
					while (i <= salesinvoicenum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > salesinvoicenum){
							endnum = salesinvoicenum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editOrderInvoiceBCDel(map);
					}
				}
				if(salesinvoicebillnum != 0){
					int i = 0;
					while (i <= salesinvoicebillnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > salesinvoicebillnum){
							endnum = salesinvoicebillnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editOrderInvoiceBillBCDel(map);
					}
				}
				baseFilesCustomerMapper.editOrderPushBalanceBCDel(map);

				baseFilesPersonnelMapper.deleteNoPersonBrandAndCustomerByMap(map);
			}

			List<Map> branduserList = baseFilesPersonnelMapper.getBrandAndCustomerListMap(map);
			if(null != branduserList && branduserList.size() != 0){
				if(demandbillnum != 0){
					int i = 0;
					while (i <= demandbillnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > demandbillnum){
							endnum = demandbillnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editGlobalOrderDemandBC(map);
					}
				}
				if(dispatchbillnum != 0){
					int i = 0;
					while (i <= dispatchbillnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > dispatchbillnum){
							endnum = dispatchbillnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editGlobalOrderDispatchbillBC(map);
					}
				}
				if(ordercarnum != 0){
					int i = 0;
					while (i <= ordercarnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > ordercarnum){
							endnum = ordercarnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editGlobalOrderCarBC(map);
					}
				}
				if(ordernum != 0){
					int i = 0;
					while (i <= ordernum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > ordernum){
							endnum = ordernum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editGlobalOrderBC(map);
					}

				}
				if(receiptnum != 0){
					int i = 0;
					while (i <= receiptnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > receiptnum){
							endnum = receiptnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editGlobalOrderReceiptBC(map);
					}
				}
				if(rejectbillnum != 0){
					int i = 0;
					while (i <= rejectbillnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > rejectbillnum){
							endnum = rejectbillnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editGlobalOrderRejectbillBC(map);
					}
				}
				if(saleoutnum != 0){
					int i = 0;
					while (i <= saleoutnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > saleoutnum){
							endnum = saleoutnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editGlobalOrderSaleoutBC(map);
					}
				}
				if(salerejectenternum != 0){
					int i = 0;
					while (i <= salerejectenternum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > salerejectenternum){
							endnum = salerejectenternum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editGlobalOrderSalerejectEnterBC(map);
					}
				}
				if(salesinvoicenum != 0){
					int i = 0;
					while (i <= salesinvoicenum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > salesinvoicenum){
							endnum = salesinvoicenum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editGlobalOrderInvoiceBC(map);
					}
				}
				if(salesinvoicebillnum != 0){
					int i = 0;
					while (i <= salesinvoicebillnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > salesinvoicebillnum){
							endnum = salesinvoicebillnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editGlobalOrderInvoiceBillBC(map);
					}
				}
				baseFilesCustomerMapper.editGlobalOrderPushBalanceBC(map);
			}

			//厂家业务员
			map.put("employetype", "7");
			List<Map> supplierdelList = baseFilesPersonnelMapper.getNoPersonBrandAndCustomerListByEM(map);
			if(null != supplierdelList && supplierdelList.size() != 0){
				if(demandbillnum != 0){
					int i = 0;
					while (i <= demandbillnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > demandbillnum){
							endnum = demandbillnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editSupplieruserChangeDemandDel(map);
					}
				}
				if(dispatchbillnum != 0){
					int i = 0;
					while (i <= dispatchbillnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > dispatchbillnum){
							endnum = dispatchbillnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editSupplieruserChangeDispatchbillDel(map);
					}
				}
				if(ordercarnum != 0){
					int i = 0;
					while (i <= ordercarnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > ordercarnum){
							endnum = ordercarnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editSupplieruserChangeOrderCarDel(map);
					}
				}
				if(ordernum != 0){
					int i = 0;
					while (i <= ordernum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > ordernum){
							endnum = ordernum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editSupplieruserChangeOrderDel(map);
					}

				}
				if(receiptnum != 0){
					int i = 0;
					while (i <= receiptnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > receiptnum){
							endnum = receiptnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editSupplieruserChangeReceiptDel(map);
					}
				}
				if(rejectbillnum != 0){
					int i = 0;
					while (i <= rejectbillnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > rejectbillnum){
							endnum = rejectbillnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editSupplieruserChangeRejectbillDel(map);
					}
				}
				if(saleoutnum != 0){
					int i = 0;
					while (i <= saleoutnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > saleoutnum){
							endnum = saleoutnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editSupplieruserChangeSaleoutDel(map);
					}
				}
				if(salerejectenternum != 0){
					int i = 0;
					while (i <= salerejectenternum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > salerejectenternum){
							endnum = salerejectenternum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editSupplieruserChangeSaleRejectEnterDel(map);
					}
				}
				if(salesinvoicenum != 0){
					int i = 0;
					while (i <= salesinvoicenum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > salesinvoicenum){
							endnum = salesinvoicenum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editSupplieruserChangeInvoiceDel(map);
					}
				}
				if(salesinvoicebillnum != 0){
					int i = 0;
					while (i <= salesinvoicebillnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > salesinvoicebillnum){
							endnum = salesinvoicebillnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editSupplieruserChangeInvoiceBillDel(map);
					}
				}
				baseFilesCustomerMapper.editSupplieruserChangePushBalanceDel(map);

				baseFilesPersonnelMapper.deleteNoPersonBrandAndCustomerByMap(map);
			}

			List<Map> supplieruserList = baseFilesPersonnelMapper.getSupplieruserBrandAndCustomerListMap(map);
			if(null != supplieruserList && supplieruserList.size() != 0){
				if(demandbillnum != 0){
					int i = 0;
					while (i <= demandbillnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > demandbillnum){
							endnum = demandbillnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editGlobalSupplieruserChangeDemandBC(map);
					}
				}
				if(dispatchbillnum != 0){
					int i = 0;
					while (i <= dispatchbillnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > dispatchbillnum){
							endnum = dispatchbillnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editGlobalSupplieruserChangeDispatchbillBC(map);
					}
				}
				if(ordercarnum != 0){
					int i = 0;
					while (i <= ordercarnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > ordercarnum){
							endnum = ordercarnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editGlobalSupplieruserChangeOrderCarBC(map);
					}
				}
				if(ordernum != 0){
					int i = 0;
					while (i <= ordernum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > ordernum){
							endnum = ordernum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editGlobalSupplieruserChangeOrderBC(map);
					}

				}
				if(receiptnum != 0){
					int i = 0;
					while (i <= receiptnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > receiptnum){
							endnum = receiptnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editGlobalSupplieruserChangeReceiptBC(map);
					}
				}
				if(rejectbillnum != 0){
					int i = 0;
					while (i <= rejectbillnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > rejectbillnum){
							endnum = rejectbillnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editGlobalSupplieruserChangeRejectbillBC(map);
					}
				}
				if(saleoutnum != 0){
					int i = 0;
					while (i <= saleoutnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > saleoutnum){
							endnum = saleoutnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editGlobalSupplieruserChangeSaleoutBC(map);
					}
				}
				if(salerejectenternum != 0){
					int i = 0;
					while (i <= salerejectenternum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > salerejectenternum){
							endnum = salerejectenternum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editGlobalSupplieruserChangeSaleRejectEnterBC(map);
					}
				}
				if(salesinvoicenum != 0){
					int i = 0;
					while (i <= salesinvoicenum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > salesinvoicenum){
							endnum = salesinvoicenum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editGlobalSupplieruserChangeInvoiceBC(map);
					}
				}
				if(salesinvoicebillnum != 0){
					int i = 0;
					while (i <= salesinvoicebillnum){
						map.put("startnum",i);
						i = i + updatenum;
						int endnum = i;
						if(i > salesinvoicebillnum){
							endnum = salesinvoicebillnum;
						}
						map.put("endnum",endnum);
						baseFilesCustomerMapper.editGlobalSupplieruserChangeInvoiceBillBC(map);
					}
				}
				baseFilesCustomerMapper.editGlobalSupplieruserChangePushBalanceBC(map);
			}
		}
	}

	/**
	 * 初始化缓存客户档案与商品档案
	 *
	 * @throws Exception
	 */
	@Override
	public void initCustomerGoodsCache() throws Exception {
		List<String> list = baseFilesCustomerMapper.getAllCustomerList();
		for(String customerid : list){
			Customer customer = getCustomerByID(customerid);
		}
		List<String> gLost = baseFilesGoodsMapper.getAllGoodsidList();
		for(String goodsid :gLost){
			getAllGoodsInfoByID(goodsid);
		}
	}

	/**
	 * 根据含税单价,税种编码，主单位数量获取应有的金额（未税单价，含税金额，未税金额，税额等）
	 * @param taxprice
	 * @param taxtypeid
	 * @param unitnum
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Aug 2, 2014
	 */
	public Map getAmountDetailByTaxWithUnitnum(BigDecimal taxprice,String taxtypeid,BigDecimal unitnum)throws Exception{
		if(null == taxprice){
			taxprice = BigDecimal.ZERO;
		}
		if(null == unitnum){
			unitnum = BigDecimal.ZERO;
		}
		Map map = new HashMap();
		TaxType taxType2 = getTaxType(taxtypeid);
		BigDecimal rate = new BigDecimal("1");
		if(null != taxType2){
			rate = taxType2.getRate().divide(new BigDecimal("100"), 6, BigDecimal.ROUND_HALF_UP).add(new BigDecimal("1"));
		}
		//未税单价 = 含税单价/税率
		BigDecimal notaxprice = BigDecimal.ZERO;
		if(taxprice.compareTo(BigDecimal.ZERO) != 0){
			notaxprice = taxprice.divide(rate, 6, BigDecimal.ROUND_HALF_UP);
		}
		//含税金额 = 含税单价*数量
		BigDecimal taxamount = taxprice.multiply(unitnum).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
		//未税金额 = 未税单价*数量
		BigDecimal notaxamount = notaxprice.multiply(unitnum).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
		//税额 = 含税金额-未税金额
		BigDecimal tax = taxamount.subtract(notaxamount);

		map.put("notaxprice", notaxprice);
		map.put("taxamount", taxamount);
		map.put("notaxamount", notaxamount);
		map.put("tax", tax);
		return map;
	}
	/**
	 * 根据商品分类编号 获取商品分类
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2015年4月3日
	 */
	public WaresClass getWaresClassByID(String id) throws Exception{
		WaresClass waresClass = baseFilesGoodsMapper.getWaresClassInfo(id);
		return waresClass;
	}

	/**
	 * 修改商品价格
	 * @param goodsInfo
	 * @param priceList
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2015年6月3日
	 */
	public boolean modifyGoodsPrices(GoodsInfo goodsInfo,List<GoodsInfo_PriceInfo> priceList)throws Exception{
		goodsInfo.setOldId(goodsInfo.getId());
//        goodsInfo.setNewbuyprice(goodsInfo.getHighestbuyprice());
		boolean flag = baseFilesGoodsMapper.editGoodsInfo(goodsInfo) > 0;
		if(flag){
			Map listMap = new HashMap();
			//价格套
			BigDecimal taxrate = BigDecimal.ONE;
			TaxType taxType2 = getTaxType(goodsInfo.getDefaulttaxtype());
			if(null != taxType2){
				taxrate = BigDecimal.ONE.add(taxType2.getRate().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
			}
			if(null != priceList && priceList.size() != 0){
				List<GoodsInfo_PriceInfo> addPriceList=new ArrayList<GoodsInfo_PriceInfo>();
				for(GoodsInfo_PriceInfo priceInfo : priceList){
					priceInfo.setGoodsid(goodsInfo.getId());
					GoodsInfo_PriceInfo price = baseFilesGoodsMapper.getPriceDataByGoodsidAndCode(goodsInfo.getOldId(), priceInfo.getCode());
					if(null != price){
						if(null != priceInfo.getTaxprice() && priceInfo.getTaxprice().compareTo(new BigDecimal(0)) != 0){
							price.setTaxprice(priceInfo.getTaxprice());
							BigDecimal notaxprice = price.getTaxprice().divide(taxrate,6,BigDecimal.ROUND_HALF_UP);
							price.setPrice(notaxprice);
							// 4066 通用版：商品调价申请单中修改价格套价格后，工作流完结后也要同时修改价格套的箱价
							price.setBoxprice(priceInfo.getTaxprice() == null ? BigDecimal.ZERO : priceInfo.getTaxprice().multiply(goodsInfo.getBoxnum()));
							baseFilesGoodsMapper.editPriceInfo(price);
						}
						else{
							baseFilesGoodsMapper.deletePriceDataByGoodsidAndCode(goodsInfo.getOldId(), priceInfo.getCode());
						}
					}
					else{
						if(null != priceInfo.getTaxprice() && priceInfo.getTaxprice().compareTo(new BigDecimal(0)) != 0){
							BigDecimal notaxprice = priceInfo.getTaxprice().divide(taxrate,6,BigDecimal.ROUND_HALF_UP);
							priceInfo.setPrice(notaxprice);
							if(null != taxType2){
								priceInfo.setTaxtype(taxType2.getId());
							}
							// 4066 通用版：商品调价申请单中修改价格套价格后，工作流完结后也要同时修改价格套的箱价
							priceInfo.setBoxprice(priceInfo.getTaxprice() == null ? BigDecimal.ZERO : priceInfo.getTaxprice().multiply(goodsInfo.getBoxnum()));
							addPriceList.add(priceInfo);
						}
					}
				}
				if(addPriceList.size() != 0){
					listMap.put("priceInfoMap", addPriceList);
					baseFilesGoodsMapper.addPriceInfos(listMap);
				}
			}
		}
		return flag;
	}

	@Override
	public synchronized void editBuySupplierChangeJob(Map map)throws Exception{
		BuySupplier buysupplier = (BuySupplier)map.get("supplier");

		baseFilesBuySupplierMapper.editPurchasePlannedOrder(buysupplier);
		baseFilesBuySupplierMapper.editPurchaseBuyOrder(buysupplier);
		baseFilesBuySupplierMapper.editPurchaseArrivalOrder(buysupplier);
		baseFilesBuySupplierMapper.editPurchaseReturnOrder(buysupplier);
		baseFilesBuySupplierMapper.editPurchaseInvoice(buysupplier);
		baseFilesBuySupplierMapper.editStoragePurchaseEnter(buysupplier);
		baseFilesBuySupplierMapper.editStoragePurchaserejectOut(buysupplier);

		baseFilesBuySupplierMapper.editJsFundInput(buysupplier);
	}

	@Override
	public synchronized void editCustomerChangeBranduserJob(Map map) throws Exception {
		List<Map<String, String>> list = (List<Map<String, String>>)map.get("list");
		if(list.size() != 0){
			for(Map<String, String> map2 : list){
				//baseFilesCustomerMapper.editBranduserChangeBill(map2);
				baseFilesCustomerMapper.editOrderDispatchbill(map2);
				baseFilesCustomerMapper.editOrderCar(map2);
				baseFilesCustomerMapper.editOrder(map2);
				baseFilesCustomerMapper.editOrderReceipt(map2);
				baseFilesCustomerMapper.editOrderRejectbill(map2);
				baseFilesCustomerMapper.editOrderSaleout(map2);
				baseFilesCustomerMapper.editOrderSalerejectEnter(map2);
				baseFilesCustomerMapper.editOrderInvoice(map2);
				baseFilesCustomerMapper.editOrderInvoiceBill(map2);
				baseFilesCustomerMapper.editOrderPushBalance(map2);
			}
		}
	}

	@Override
	public synchronized void editCustomerChangeJob(Map map) throws Exception {
		Customer customer = (Customer)map.get("customer");

		//baseFilesCustomerMapper.editCustomerChangeBill(customer);
		baseFilesCustomerMapper.editCustomerSalesDemand(customer);
		baseFilesCustomerMapper.editCustomerSalesOrder(customer);
		baseFilesCustomerMapper.editCustomerSalesOrderCar(customer);
		baseFilesCustomerMapper.editCustomerSalesDispatchbill(customer);
		baseFilesCustomerMapper.editCustomerSalesReceipt(customer);
		baseFilesCustomerMapper.editCustomerSalesRejectbill(customer);
		baseFilesCustomerMapper.editCustomerSalesSaleout(customer);
		baseFilesCustomerMapper.editCustomerSalesInvoice(customer);
		baseFilesCustomerMapper.editCustomerSalesInvoiceDetail(customer);
		baseFilesCustomerMapper.editCustomerSalesInvoiceBill(customer);
		baseFilesCustomerMapper.editCustomerSalesInvoiceBillDetail(customer);
		baseFilesCustomerMapper.editCustomerSalesSalerejectEnter(customer);
		baseFilesCustomerMapper.editCustomerSalesPushBalance(customer);
        baseFilesCustomerMapper.editCustomerSalesBeginAmount(customer);

	}

	@Override
	public synchronized void editCustomerChangePricesortJob(Map map) throws Exception {
		String customerid = (String)map.get("customerid");
		String pricesort = (String)map.get("pricesort");

		List<CustomerPrice> list = baseFilesCustomerMapper.getCustomerPriceListByCustomer(customerid);
		if(list.size() > 0){
			for(CustomerPrice customerPrice : list){
				GoodsInfo_PriceInfo gPriceInfo = baseFilesGoodsMapper.getPriceInfoByGoodsAndCode(customerPrice.getGoodsid(), pricesort);
				if(null != gPriceInfo){
					customerPrice.setTaxprice(gPriceInfo.getTaxprice());
					TaxType taxType = baseFilesFinanceMapper.getTaxTypeInfo(gPriceInfo.getTaxtype());
					if(null != taxType){
						BigDecimal taxrate = taxType.getRate().divide(new BigDecimal("100"), 6, BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1));
						customerPrice.setTaxrate(taxrate);
					}else{
						customerPrice.setTaxrate(new BigDecimal("1"));
					}
				}
			}

			baseFilesCustomerMapper.deleteCustomerPriceByCustomer(customerid);
			baseFilesCustomerMapper.addCustomerPriceMore(list);
		}
	}

	@Override
	public synchronized void editCustomerChangeSupplieruserJob(Map map) throws Exception {
		List<Map<String, String>> list = (List<Map<String, String>>)map.get("list");
		if(list.size() != 0){
			for(Map<String, String> map2 : list){
				//baseFilesCustomerMapper.editSupplieruserChangeBill(map2);
				baseFilesCustomerMapper.editSupplieruserChangeDispatchbill(map2);
				baseFilesCustomerMapper.editSupplieruserChangeOrderCar(map2);
				baseFilesCustomerMapper.editSupplieruserChangeOrder(map2);
				baseFilesCustomerMapper.editSupplieruserChangeReceipt(map2);
				baseFilesCustomerMapper.editSupplieruserChangeRejectbill(map2);
				baseFilesCustomerMapper.editSupplieruserChangeSaleout(map2);
				baseFilesCustomerMapper.editSupplieruserChangeSaleRejectEnter(map2);
				baseFilesCustomerMapper.editSupplieruserChangeInvoice(map2);
				baseFilesCustomerMapper.editSupplieruserChangeInvoiceBill(map2);
				baseFilesCustomerMapper.editSupplieruserChangePushBalance(map2);
			}
		}
	}

	@Override
	public synchronized void editGoodsChangeJob() throws Exception {
		Map map = new HashMap();
		String today = CommonUtils.getTodayDataStr();
//		String today = CommonUtils.getYestodayDateStr();
		SysParam chdayssysParam = getBaseSysParamMapper().getSysParam("changeDaysGoodsData");
		if(null != chdayssysParam){
			Integer days = Integer.parseInt(chdayssysParam.getPvalue().toString());
			String yeday = CommonUtils.getBeforeDateInDays(new Date(),days+1);
			map.put("startdate",yeday);
		}else{
			map.put("startdate",today);
		}
		map.put("enddate",today);

		//修改商品档案的所属部门,且更新商品档案的修改时间，以便更新商品档案修改过所涉及的销售单据数据
		SysUser sysUser = getSysUser();
		if(null != sysUser){
			map.put("modifyuserid",sysUser.getUserid());
			map.put("modifyusername",sysUser.getName());
		}else{
			map.put("modifyuserid","");
			map.put("modifyusername","");
		}

		//品牌档案品牌部门修改后变更商品档案中对应的品牌部门,其中商品档案中的供应商已根据品牌档案修改操作时，立即修改了
		baseFilesGoodsMapper.editBrandDeptChangeGoods(map);

		//修改品牌档案后对应的冲差单数据更新
		//冲差单表中不含商品编码，只能根据品牌档案中的品牌部门、供应商更新冲差单
		baseFilesGoodsMapper.editBrandChangePushBalance(map);
		//发票有来源于冲差单的，只能根据品牌档案中的品牌部门、供应商更新来源单据
		baseFilesGoodsMapper.editSalesInvoiceCauseOfSupplier(map);
		//开票有来源于冲差单的，只能根据品牌档案中的品牌部门、供应商更新来源单据
		baseFilesGoodsMapper.editSalesInvoiceBillCauseOfSupplier(map);

		//商品档案修改后对应商品的对应合同价的箱价数据更新
		baseFilesGoodsMapper.editGoodsInfoChangeCustomerPriceCtcboxprice(map);

		int updatenum = 45000;
		//分批次执行品牌业务员数据
		Map numMap = baseFilesCustomerMapper.getBillDetailsNum();
		Integer demandbillnum = ((BigDecimal)numMap.get("demandbillnum")).intValue();
		Integer dispatchbillnum = ((BigDecimal)numMap.get("dispatchbillnum")).intValue();
		Integer ordercarnum = ((BigDecimal)numMap.get("ordercarnum")).intValue();
		Integer ordernum = ((BigDecimal)numMap.get("ordernum")).intValue();
		Integer receiptnum = ((BigDecimal)numMap.get("receiptnum")).intValue();
		Integer rejectbillnum = ((BigDecimal)numMap.get("rejectbillnum")).intValue();
		Integer saleoutnum = ((BigDecimal)numMap.get("saleoutnum")).intValue();
		Integer salerejectenternum = ((BigDecimal)numMap.get("salerejectenternum")).intValue();
		Integer salesinvoicenum = ((BigDecimal)numMap.get("salesinvoicenum")).intValue();
		Integer salesinvoicebillnum = ((BigDecimal)numMap.get("salesinvoicebillnum")).intValue();
		Integer arrivalordernum = ((BigDecimal)numMap.get("arrivalordernum")).intValue();
		Integer purchaseenternum = ((BigDecimal)numMap.get("purchaseenternum")).intValue();
		Integer purchaserejectoutnum = ((BigDecimal)numMap.get("purchaserejectoutnum")).intValue();
		Integer storagedaynum = ((BigDecimal)numMap.get("storagedaynum")).intValue();
		Integer storagemonthnum = ((BigDecimal)numMap.get("storagemonthnum")).intValue();
		Integer storagerealdaynum = ((BigDecimal)numMap.get("storagerealdaynum")).intValue();
		Integer storagerealmonthnum = ((BigDecimal)numMap.get("storagerealmonthnum")).intValue();

		//销售单据
		if(demandbillnum != 0){
			int i = 0;
			while (i <= demandbillnum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > demandbillnum){
					endnum = demandbillnum;
				}
				map.put("endnum",endnum);
				//销售单据品牌编码、品牌部门、供应商、商品分类更新
				baseFilesGoodsMapper.editGoodsInfoChangeDemandDetail(map);
				//单据品牌业务员更新
				baseFilesCustomerMapper.editOrderDemandBC(map);
				//单据厂家业务员更新
				baseFilesCustomerMapper.editSupplieruserChangeDemandBC(map);
			}
		}
		if(dispatchbillnum != 0){
			int i = 0;
			while (i <= dispatchbillnum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > dispatchbillnum){
					endnum = dispatchbillnum;
				}
				map.put("endnum",endnum);
				//销售单据品牌编码、品牌部门、供应商、商品分类更新
				baseFilesGoodsMapper.editGoodsInfoChangeDispatchbillDetail(map);
				//单据品牌业务员更新
				baseFilesCustomerMapper.editOrderDispatchbillBC(map);
				//单据厂家业务员更新
				baseFilesCustomerMapper.editSupplieruserChangeDispatchbillBC(map);
			}
		}
		if(ordercarnum != 0){
			int i = 0;
			while (i <= ordercarnum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > ordercarnum){
					endnum = ordercarnum;
				}
				map.put("endnum",endnum);
				//销售单据品牌编码、品牌部门、供应商、商品分类更新
				baseFilesGoodsMapper.editGoodsInfoChangeOrderCarDetail(map);
				//单据品牌业务员更新
				baseFilesCustomerMapper.editOrderCarBC(map);
				//单据厂家业务员更新
				baseFilesCustomerMapper.editSupplieruserChangeOrderCarBC(map);
			}
		}
		if(ordernum != 0){
			int i = 0;
			while (i <= ordernum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > ordernum){
					endnum = ordernum;
				}
				map.put("endnum",endnum);
				//销售单据品牌编码、品牌部门、供应商、商品分类更新
				baseFilesGoodsMapper.editGoodsInfoChangeOrderDetail(map);
				//单据品牌业务员更新
				baseFilesCustomerMapper.editOrderBC(map);
				//单据厂家业务员更新
				baseFilesCustomerMapper.editSupplieruserChangeOrderBC(map);
			}
		}
		if(receiptnum != 0){
			int i = 0;
			while (i <= receiptnum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > receiptnum){
					endnum = receiptnum;
				}
				map.put("endnum",endnum);
				//销售单据品牌编码、品牌部门、供应商、商品分类更新
				baseFilesGoodsMapper.editGoodsInfoChangeReceiptDetail(map);
				//单据品牌业务员更新
				baseFilesCustomerMapper.editOrderReceiptBC(map);
				//单据厂家业务员更新
				baseFilesCustomerMapper.editSupplieruserChangeReceiptBC(map);
			}
		}
		if(rejectbillnum != 0){
			int i = 0;
			while (i <= rejectbillnum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > rejectbillnum){
					endnum = rejectbillnum;
				}
				map.put("endnum",endnum);
				//销售单据品牌编码、品牌部门、供应商、商品分类更新
				baseFilesGoodsMapper.editGoodsInfoChangeRejectbillDetail(map);
				//单据品牌业务员更新
				baseFilesCustomerMapper.editOrderRejectbillBC(map);
				//单据厂家业务员更新
				baseFilesCustomerMapper.editSupplieruserChangeRejectbillBC(map);
			}
		}
		if(saleoutnum != 0){
			int i = 0;
			while (i <= saleoutnum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > saleoutnum){
					endnum = saleoutnum;
				}
				map.put("endnum",endnum);
				//销售单据品牌编码、品牌部门、供应商、商品分类更新
				baseFilesGoodsMapper.editGoodsInfoChangeSaleoutDetail(map);
				//单据品牌业务员更新
				baseFilesCustomerMapper.editOrderSaleoutBC(map);
				//单据厂家业务员更新
				baseFilesCustomerMapper.editSupplieruserChangeSaleoutBC(map);
			}
		}
		if(salerejectenternum != 0){
			int i = 0;
			while (i <= salerejectenternum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > salerejectenternum){
					endnum = salerejectenternum;
				}
				map.put("endnum",endnum);
				//销售单据品牌编码、品牌部门、供应商、商品分类更新
				baseFilesGoodsMapper.editGoodsInfoChangeSalerejectEnterDetail(map);
				//单据品牌业务员更新
				baseFilesCustomerMapper.editOrderSalerejectEnterBC(map);
				//单据厂家业务员更新
				baseFilesCustomerMapper.editSupplieruserChangeSaleRejectEnterBC(map);
			}
		}
		if(salesinvoicenum != 0){
			int i = 0;
			while (i <= salesinvoicenum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > salesinvoicenum){
					endnum = salesinvoicenum;
				}
				map.put("endnum",endnum);
				//销售单据品牌编码、品牌部门、供应商、商品分类更新
				baseFilesGoodsMapper.editGoodsInfoChangeInvoiceDetail(map);
				//单据品牌业务员更新
				baseFilesCustomerMapper.editOrderInvoiceBC(map);
				//单据厂家业务员更新
				baseFilesCustomerMapper.editSupplieruserChangeInvoiceBC(map);
			}
		}
		if(salesinvoicebillnum != 0){
			int i = 0;
			while (i <= salesinvoicebillnum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > salesinvoicebillnum){
					endnum = salesinvoicebillnum;
				}
				map.put("endnum",endnum);
				//销售单据品牌编码、品牌部门、供应商、商品分类更新
				baseFilesGoodsMapper.editGoodsInfoChangeInvoiceBillDetail(map);
				//单据品牌业务员更新
				baseFilesCustomerMapper.editOrderInvoiceBillBC(map);
				//单据厂家业务员更新
				baseFilesCustomerMapper.editSupplieruserChangeInvoiceBillBC(map);
			}
		}
		//进销存品牌编码、品牌部门、供应商、商品分类更新
		if(storagedaynum != 0){
			int i = 0;
			while (i <= storagedaynum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > storagedaynum){
					endnum = storagedaynum;
				}
				map.put("endnum",endnum);
				//每日进销存品牌编码、品牌部门、供应商更新
				baseFilesGoodsMapper.editStorageDayCauseOfGoodsChange(map);
			}
		}
		if(storagemonthnum != 0){
			int i = 0;
			while (i <= storagemonthnum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > storagemonthnum){
					endnum = storagemonthnum;
				}
				map.put("endnum",endnum);
				//每月进销存品牌编码、品牌部门、供应商更新
				baseFilesGoodsMapper.editStorageMonthCauseOfGoodsChange(map);
			}
		}
		if(storagerealdaynum != 0){
			int i = 0;
			while (i <= storagerealdaynum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > storagerealdaynum){
					endnum = storagerealdaynum;
				}
				map.put("endnum",endnum);
				//实际每日品牌编码、品牌部门、供应商更新
				baseFilesGoodsMapper.editStorageRealDayCauseOfGoodsChange(map);
			}
		}
		if(storagerealmonthnum != 0){
			int i = 0;
			while (i <= storagerealmonthnum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > storagerealmonthnum){
					endnum = storagerealmonthnum;
				}
				map.put("endnum",endnum);
				//实际每月进销存品牌编码、品牌部门、供应商更新
				baseFilesGoodsMapper.editStorageRealMonthCauseOfGoodsChange(map);
			}
		}

		//单据品牌业务员更新
		baseFilesCustomerMapper.editOrderPushBalanceBC(map);
		//单据厂家业务员更新
		baseFilesCustomerMapper.editSupplieruserChangePushBalanceBC(map);

		//采购单据
		if(arrivalordernum != 0){
			int i = 0;
			while (i <= arrivalordernum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > arrivalordernum){
					endnum = arrivalordernum;
				}
				map.put("endnum",endnum);
				//采购单据品牌编码更新
				baseFilesGoodsMapper.editGoodsInfoPurchaseArrivalOrder(map);
			}
		}
		if(purchaseenternum != 0){
			int i = 0;
			while (i <= purchaseenternum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > purchaseenternum){
					endnum = purchaseenternum;
				}
				map.put("endnum",endnum);
				//采购单据品牌编码更新
				baseFilesGoodsMapper.editGoodsInfoPurchaseEnter(map);
			}
		}
		if(purchaserejectoutnum != 0){
			int i = 0;
			while (i <= purchaserejectoutnum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > purchaserejectoutnum){
					endnum = purchaserejectoutnum;
				}
				map.put("endnum",endnum);
				//采购单据品牌编码更新
				baseFilesGoodsMapper.editGoodsInfoPurchaserejectOut(map);
			}
		}
		//获取天数范围内修改过的商品数据列表
		List<GoodsInfo> editGoodsList = baseFilesGoodsMapper.getChangeDaysUpdateGoodsList(map);
		if(null != editGoodsList && editGoodsList.size() != 0){
			for(GoodsInfo goodsInfo : editGoodsList){
				MeteringUnit meteringUnit = getMeteringUnitById(goodsInfo.getMainunit());
				if(null != meteringUnit){
					goodsInfo.setMainunitName(meteringUnit.getName());
				}
				MeteringUnit auxMeteringUnit = getMeteringUnitById(goodsInfo.getAuxunitid());
				if(null != auxMeteringUnit){
					goodsInfo.setAuxunitname(auxMeteringUnit.getName());
				}
				baseFilesGoodsMapper.editStorageDataCauseOfGoodsChange(goodsInfo);
			}
		}
	}

	@Override
	public void editAttachOneConvertHtmlJob(Map map) throws Exception {
		String src = (String)map.get("src");
		String dis = (String)map.get("dis");
		String ext = (String)map.get("ext");
		//这里开始   微软OFFICE转html
		File htmlfile = new File(dis);
		File parent = htmlfile.getParentFile();
		if(parent!=null&&!parent.exists()){
			parent.mkdirs();
		}
//        if(".doc".equals(ext) || ".docx".equals(ext)){
//            JacobUtils.wordToHtml(src, dis);
//        }else if(".xls".equals(ext) || ".xlsx".equals(ext)){
//            JacobUtils.excelToHtml(src,dis);
//        }

		//使用openoffcie 转
		int iCovertResult=OfficeUtils.office2Other(src, dis);
		if(iCovertResult==0){
			throw new Exception("文件转换失败");
		}
	}

	/**
	 * 通过客户编号和商品编码 获取客户商品合同价<br/>
	 * emptyThenUsePID 当参数值为true时,如果没有查询到当前客户商品合同价，则该客户的上级客户和商品编码再次查询客户商品合同价
	 * @param customerid
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2016-01-05
	 */
	public CustomerPrice getCustomerPriceByCustomerAndGoodsid(String customerid,String goodsid) throws Exception{
		CustomerPrice customerPrice = getBaseFilesCustomerMapper().getCustomerPriceByCustomerAndGoodsid(customerid, goodsid);
		return customerPrice;
	}
	/**
	 * 根据科目编号获取科目档案信息
	 * @param subjectId
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2016年2月21日
	 */
	public Subject getSubjectInfoById(String subjectId) throws Exception{
		Subject subject=getBaseSubjectMapper().getSubjectById(subjectId);
		return subject;
	}
	/**
	 * 金额、单价、数量变动后，相互计算
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2016年1月21日
	 */
	public Map computeUnitumPriceAmountChange(String type,String taxpriceStr,String unitnumStr,String amountStr) throws Exception{

		if(null==taxpriceStr || "".equals(taxpriceStr.trim())){
			taxpriceStr="0";
		}
		if(null==unitnumStr || "".equals(unitnumStr.trim())){
			unitnumStr="0";
		}
		if(null==amountStr || "".equals(amountStr.trim())){
			amountStr="0";
		}

		BigDecimal taxprice=new BigDecimal(taxpriceStr.trim());
		BigDecimal unitnum=new BigDecimal(unitnumStr.trim());
		BigDecimal amount=new BigDecimal(amountStr.trim());
		//金额变动
		if("1".equals(type)){
			if(amount.compareTo(BigDecimal.ZERO)==0){
				taxprice=BigDecimal.ZERO;
			}else{
				amount=amount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
				//如果数量为零
				if(unitnum.compareTo(BigDecimal.ZERO)==0){
					//单价也为零
					taxprice=BigDecimal.ZERO;
				}else{
					unitnum=unitnum.setScale(decimalLen,BigDecimal.ROUND_HALF_UP);

					taxprice=amount.divide(unitnum, 6, BigDecimal.ROUND_HALF_UP);
				}

			}

		}else if("2".equals(type)){
			//数量变动
			if(unitnum.compareTo(BigDecimal.ZERO)==0){
				taxprice=BigDecimal.ZERO;
			}else{
				unitnum=unitnum.setScale(decimalLen,BigDecimal.ROUND_HALF_UP);

				if(amount.compareTo(BigDecimal.ZERO)==0){
					if(taxprice.compareTo(BigDecimal.ZERO)>0){
						amount=unitnum.multiply(taxprice).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
					}
				}else{
					amount=amount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP);

					taxprice=amount.divide(unitnum, 6, BigDecimal.ROUND_HALF_UP);
				}

			}
		}else if("3".equals(type)){
			//单价变动
			if(taxprice.compareTo(BigDecimal.ZERO)==0){
				if(amount.compareTo(BigDecimal.ZERO)>0 && unitnum.compareTo(BigDecimal.ZERO)>0){
					taxprice=amount.divide(unitnum, 6, BigDecimal.ROUND_HALF_UP);
				}else{
					taxprice=BigDecimal.ZERO;
				}
			}else{
				taxprice=taxprice.setScale(6,BigDecimal.ROUND_HALF_UP);

				if(unitnum.compareTo(BigDecimal.ZERO)==0){
					amount=BigDecimal.ONE;
				}else{
					unitnum=unitnum.setScale(decimalLen,BigDecimal.ROUND_HALF_UP);

					amount=unitnum.multiply(taxprice).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
				}

			}
		}
		Map resultMap=new HashMap();
		resultMap.put("taxprice", taxprice);
		resultMap.put("unitnum", unitnum);
		resultMap.put("amount", amount);
		return resultMap;
	}

	/**
	 * 根据商品编码获取电商商品明细相关信息
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-04-28
	 */
	public Map getEbGoodsDetailInfo(String goodsid)throws Exception{
		Map retmap = new HashMap();
		String pricecode = "";
		SysParam sysParam = getBaseSysParamMapper().getSysParam("ebgoodsprice");
		if(null != sysParam){
			pricecode = sysParam.getPvalue();
		}
		GoodsInfo goodsInfo = getBaseGoodsMapper().getGoodsInfo(goodsid);
		if(null != goodsInfo){
			retmap.put("goodsid",goodsid);
			retmap.put("goodsname",goodsInfo.getName());
			retmap.put("barcode",goodsInfo.getBarcode());
			retmap.put("unitid",goodsInfo.getMainunit());
			MeteringUnit mainunit = getBaseGoodsMapper().showMeteringUnitInfo(goodsInfo.getMainunit());
			if(null != mainunit){
				retmap.put("unitname",mainunit.getName());
			}

			GoodsInfo_MteringUnitInfo goodsInfo_mteringUnitInfo = getBaseGoodsMapper().getMUInfoByGoodsIdAndIsdefault(goodsid);
			if(null != goodsInfo_mteringUnitInfo){
				retmap.put("auxunitid",goodsInfo_mteringUnitInfo.getMeteringunitid());
				MeteringUnit auxunit = getBaseGoodsMapper().showMeteringUnitInfo(goodsInfo_mteringUnitInfo.getMeteringunitid());
				if(null != auxunit){
					retmap.put("auxunitname",auxunit.getName());
				}
			}

			if(StringUtils.isNotEmpty(pricecode)){
				GoodsInfo_PriceInfo goodsprice = getBaseGoodsMapper().getPriceInfoByGoodsAndCode(goodsid,pricecode);
				if(null != goodsprice){
					retmap.put("price",goodsprice.getTaxprice());
				}
			}
		}
		return retmap;
	}

	/**
	 * 根据商品编码、数量获取辅单位描述、金额等数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-04-28
	 */
	public Map getEbgoodsRowUnitnumInfo(String goodsid, String unitnum,String pricestr)throws Exception{
		Map map = getEbGoodsDetailInfo(goodsid);
		if(!map.isEmpty()){
			String auxunitid = (String)map.get("auxunitid");
			BigDecimal price = BigDecimal.ZERO;
			if(StringUtils.isNotEmpty(pricestr)){
				price = new BigDecimal(pricestr);
			}else{
				price = null != map.get("price") ? (BigDecimal)map.get("price") : BigDecimal.ZERO;
			}
			BigDecimal amount = price.multiply(new BigDecimal(unitnum));
			map.put("amount",amount);
			Map map1 = countGoodsInfoNumber(goodsid,auxunitid,new BigDecimal(unitnum));
			if(!map1.isEmpty()){
				String auxnumdetail = null != map1.get("auxnumdetail") ? (String)map1.get("auxnumdetail") : "";
				map.put("auxnumdetail",auxnumdetail);
			}
			map.put("unitnum",new BigDecimal(unitnum));
		}
		return map;
	}

    /**
     * 获取
     * @param storageid
     * @return
     * @throws Exception
     */
    public String getStorageIsAccount(String storageid) throws Exception{
        //仓库是否独立核算 0否1是 默认否
        String isStorageAccount = getSysParamValue("IsStorageAccount");
        if("1".equals(isStorageAccount)){
            StorageInfo storageInfo = getStorageInfoByID(storageid);
            if(null!=storageInfo && "1".equals(storageInfo.getIsaloneaccount())){
                return "1";
            }
        }
        return "0";
    }
	/**
	 * 客户应收款、余额<br/>
	 * 返回结果<br/>
	 * receivableAmount:客户应收款<br/>
	 * leftAmount : 余额<br/>
	 * @param customerid
	 * @return
	 * @author zhanghonghui
	 * @date 2015年11月12日
	 */
	public Map showCustomerReceivableInfoData(String customerid) throws Exception {
		if(null==customerid || "".equals(customerid.trim())){
			return null;
		}

		Map resultMap=new HashMap();

		//应收金额
		BigDecimal receivableAmount = salesReceiptMapper.getReceivableAmountByCustomerid(customerid);
		if (null == receivableAmount) {
			receivableAmount = BigDecimal.ZERO;
		}

		resultMap.put("receivableAmount", receivableAmount);
		//客户资金情况（余额）
		CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(customerid);

		BigDecimal leftAmount=null;
		if(null!=customerCapital){
			leftAmount=customerCapital.getAmount();
		}
		if(null==leftAmount){
			leftAmount=BigDecimal.ZERO;
		}
		resultMap.put("leftAmount", leftAmount);

		return resultMap;
	}

	@Override
	public synchronized void doBuySupplierDeptChangeJob() throws Exception {
		baseFilesBuySupplierMapper.editMatcostsInputSupplierDeptCaseBuydeptid();
		//调整付款单所属部门
        baseFilesBuySupplierMapper.editPayorderBuydeptCaseBuydeptid();
        // 调整客户费用deptid
        baseFilesBuySupplierMapper.editCustomerPayableBuydeptCaseBuydeptid();
	}

	@Override
	public synchronized void doAutoUpdateGoodsNewsaledate() throws Exception {
		List<Map> list = dispatchBillMapper.getDispatchBillGoodsidBusinessdate();
		for(Map map : list){
			String goodsid = null != map.get("goodsid") ? (String)map.get("goodsid") : "";
			String businessdate = null != map.get("businessdate") ? (String)map.get("businessdate") : "";
			if(StringUtils.isNotEmpty(goodsid) && StringUtils.isNotEmpty(businessdate)){
				baseFilesGoodsMapper.updateGoodsinfoNewsaledate(goodsid,businessdate);
			}
		}
	}

	@Override
	public synchronized void doAutoUpdateCustomerNewsalesdate() throws Exception {
		List<Map> list = dispatchBillMapper.getDispatchBillCustomeridBusinessdate();
		for(Map map : list){
			String customerid = null != map.get("customerid") ? (String)map.get("customerid") : "";
			String businessdate = null != map.get("businessdate") ? (String)map.get("businessdate") : "";
			if(StringUtils.isNotEmpty(customerid) && StringUtils.isNotEmpty(businessdate)){
				baseFilesCustomerMapper.updateCustomerNewsaledate(customerid, businessdate);
			}
		}
	}

	@Override
	public synchronized void doAutoUpdateBillByBaseFiles() throws Exception {
		int updatenum = 45000;
		//分批次执行品牌业务员数据
		Map numMap = baseFilesCustomerMapper.getBillDetailsNum();
		Integer demandbillnum = ((BigDecimal)numMap.get("demandbillnum")).intValue();
		Integer dispatchbillnum = ((BigDecimal)numMap.get("dispatchbillnum")).intValue();
		Integer ordercarnum = ((BigDecimal)numMap.get("ordercarnum")).intValue();
		Integer ordernum = ((BigDecimal)numMap.get("ordernum")).intValue();
		Integer receiptnum = ((BigDecimal)numMap.get("receiptnum")).intValue();
		Integer rejectbillnum = ((BigDecimal)numMap.get("rejectbillnum")).intValue();
		Integer saleoutnum = ((BigDecimal)numMap.get("saleoutnum")).intValue();
		Integer salerejectenternum = ((BigDecimal)numMap.get("salerejectenternum")).intValue();
		Integer salesinvoicenum = ((BigDecimal)numMap.get("salesinvoicenum")).intValue();
		Integer salesinvoicebillnum = ((BigDecimal)numMap.get("salesinvoicebillnum")).intValue();

		//客户档案修改更新单据的销售区域、销售部门、客户业务员、内勤，其中销售核销、销售开票、冲差不需更新内勤
		baseFilesGoodsMapper.updateBillByCustomerChange();
		//品牌修改，变更单据明细中的品牌部门、进销存中的供应商  品牌业务员修改，变更单据明细中的品牌业务员 厂家业务员修改,变更单据明细中的厂家业务员
		Map map = new HashMap();
		if(demandbillnum != 0){
			int i = 0;
			while (i <= demandbillnum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > demandbillnum){
					endnum = demandbillnum;
				}
				map.put("endnum",endnum);
				baseFilesGoodsMapper.updateDemandDetailByBrandOrBranduserOrSupplieruserChange(map);
			}
		}
		if(dispatchbillnum != 0){
			int i = 0;
			while (i <= dispatchbillnum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > dispatchbillnum){
					endnum = dispatchbillnum;
				}
				map.put("endnum",endnum);
				baseFilesGoodsMapper.updateDispatchbillDetailByBrandOrBranduserOrSupplieruserChange(map);
			}
		}
		if(ordercarnum != 0){
			int i = 0;
			while (i <= ordercarnum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > ordercarnum){
					endnum = ordercarnum;
				}
				map.put("endnum",endnum);
				baseFilesGoodsMapper.updateOrderCarDetailByBrandOrBranduserOrSupplieruserChange(map);
			}
		}
		if(ordernum != 0){
			int i = 0;
			while (i <= ordernum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > ordernum){
					endnum = ordernum;
				}
				map.put("endnum",endnum);
				baseFilesGoodsMapper.updateOrderDetailByBrandOrBranduserOrSupplieruserChange(map);
			}
		}
		if(receiptnum != 0){
			int i = 0;
			while (i <= receiptnum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > receiptnum){
					endnum = receiptnum;
				}
				map.put("endnum",endnum);
				baseFilesGoodsMapper.updateReceiptDetailByBrandOrBranduserOrSupplieruserChange(map);
			}
		}
		if(rejectbillnum != 0){
			int i = 0;
			while (i <= rejectbillnum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > rejectbillnum){
					endnum = rejectbillnum;
				}
				map.put("endnum",endnum);
				baseFilesGoodsMapper.updateRejectbillDetailByBrandOrBranduserOrSupplieruserChange(map);
			}
		}
		if(saleoutnum != 0){
			int i = 0;
			while (i <= saleoutnum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > saleoutnum){
					endnum = saleoutnum;
				}
				map.put("endnum",endnum);
				baseFilesGoodsMapper.updateSaleoutDetailByBrandOrBranduserOrSupplieruserChange(map);
			}
		}
		if(salerejectenternum != 0){
			int i = 0;
			while (i <= salerejectenternum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > salerejectenternum){
					endnum = salerejectenternum;
				}
				map.put("endnum",endnum);
				baseFilesGoodsMapper.updateSalerejectEnterDetailByBrandOrBranduserOrSupplieruserChange(map);
			}
		}
		if(salesinvoicenum != 0){
			int i = 0;
			while (i <= salesinvoicenum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > salesinvoicenum){
					endnum = salesinvoicenum;
				}
				map.put("endnum",endnum);
				baseFilesGoodsMapper.updateInvoiceDetailByBrandOrBranduserOrSupplieruserChange(map);
			}
		}
		if(salesinvoicebillnum != 0){
			int i = 0;
			while (i <= salesinvoicebillnum){
				map.put("startnum",i);
				i = i + updatenum;
				int endnum = i;
				if(i > salesinvoicebillnum){
					endnum = salesinvoicebillnum;
				}
				map.put("endnum",endnum);
				baseFilesGoodsMapper.updateInvoiceBillDetailByBrandOrBranduserOrSupplieruserChange(map);
			}
		}
		baseFilesGoodsMapper.updateCustomerPushByBrandOrBranduserOrSupplieruserChange();

		//更新品牌业务员对应品牌对应客户表标记、厂家业务员对应品牌对应客户表标记
		baseFilesGoodsMapper.updatePersnBrandOrPersonSupplier();
	}

	/**
	 * 根据供应商编号 品牌编号计算应收日期
	 * 当供应商有品牌结算方式的时候 按品牌结算方式计算
	 * 品牌编号为null或者为空的时候 取供应商档案中的结算方式
	 * 如果结算方式不存在 则通过系统参数获取默认结算方式
	 * @param date
	 * @param supplierid
	 * @param brandid
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Oct 26, 2017
	 */
	public String getSupplierDateBySettlement(Date date,String supplierid,String brandid) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(null==date){
			date = new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 0);
		BuySupplier buySupplier = getSupplierInfoById(supplierid);
		if(null!=buySupplier){
			String settletype = null;
			BuySupplierBrandSettletype buySupplierBrandSettletype = null;
			if(StringUtils.isNotEmpty(brandid)){
				buySupplierBrandSettletype = getSupplierBrandSettletype(supplierid, brandid);
				if(null!=buySupplierBrandSettletype){
					settletype = buySupplierBrandSettletype.getSettletype();
				}
			}
			if(StringUtils.isEmpty(settletype)){
				settletype = buySupplier.getSettletype();
			}
			Map map = new HashMap();
			map.put("id", settletype);
			Settlement settlement = baseFilesFinanceMapper.getSettlemetDetail(map);
//			int overgracedate = 0;
//			if(null!=buySupplier.getOvergracedate()){
//				overgracedate = buySupplier.getOvergracedate();
//			}
			if(null!=settlement){
				//月结
				//当前日期在结算日之前 本月开始算应收日期
				//当前日期在结算日之后 下个月开始算应收日期
				if("1".equals(settlement.getType())){
					//客户结算日
					int settleday = 0;
					if(StringUtils.isNotEmpty(brandid) && null!=buySupplierBrandSettletype){
						//结算日为空的时候 结算日默认为30号
						if(null!=buySupplierBrandSettletype.getSettleday() && !"".equals(buySupplierBrandSettletype.getSettleday())){
							if(StringUtils.isNumeric(buySupplierBrandSettletype.getSettleday())){
								settleday = Integer.parseInt(buySupplierBrandSettletype.getSettleday());
							}else{
								settleday = 30;
							}
						}
					}else{
						//结算日为空的时候 结算日默认为30号
						if(null!=buySupplier.getSettleday() && !"".equals(buySupplier.getSettleday())){
							if(StringUtils.isNumeric(buySupplier.getSettleday())){
								settleday = Integer.parseInt(buySupplier.getSettleday());
							}else{
								settleday = 30;
							}
						}
					}
					int nowday = CommonUtils.getDataMonthDay(date);
					if(nowday>=settleday){
						//下个月
						calendar.add(Calendar.MONTH,1);
					}
					//把日期设置为当月第一天
					calendar.set(Calendar.DATE, 0);
					calendar.add(Calendar.DAY_OF_MONTH, settleday+settlement.getDays().intValue());

				}else if("2".equals(settlement.getType())){						//日结
					if(null!=buySupplier){
						calendar.add(Calendar.DAY_OF_MONTH, settlement.getDays().intValue());
					}else{
						calendar.add(Calendar.DAY_OF_MONTH, settlement.getDays().intValue());
					}
				}
			}else{
				//根据系统参数获取默认结算方式
				settletype = getSysParamValue("SupplierSettletype");
				map.put("id", settletype);
				Settlement defaulSettlement = baseFilesFinanceMapper.getSettlemetDetail(map);
				if(null!=defaulSettlement){
					//月结
					if("1".equals(defaulSettlement.getType())){
						//客户结算日
						int settleday = 0;
						//结算日为空的时候 结算日默认为30号
						if(null!=buySupplier.getSettleday() && !"".equals(buySupplier.getSettleday())){
							if(StringUtils.isNumeric(buySupplier.getSettleday())){
								settleday = Integer.parseInt(buySupplier.getSettleday());
							}else{
								settleday = 30;
							}
						}
						int nowday = CommonUtils.getDataMonthDay(date);
						if(nowday>=settleday){
							//下个月
							calendar.add(Calendar.MONTH,1);
						}
						//把日期设置为当月第一天
						calendar.set(Calendar.DATE, 0);
						calendar.add(Calendar.DAY_OF_MONTH, settleday+settlement.getDays().intValue());
					}else if("2".equals(defaulSettlement.getType())){				//日结
						if(null!=buySupplier){
							calendar.add(Calendar.DAY_OF_MONTH, defaulSettlement.getDays().intValue());
						}else{
							calendar.add(Calendar.DAY_OF_MONTH, defaulSettlement.getDays().intValue());
						}
					}

				}
			}
		}
		return dateFormat.format(calendar.getTime());
	}

}

