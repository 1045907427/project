/**
 * @(#)DeliveryReportServiceImpl.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月16日 huangzhiqian 创建版本
 */
package com.hd.agent.report.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.model.*;
import com.hd.agent.report.model.DeliveryAllReport;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.report.action.DeliveryReportAction;
import com.hd.agent.report.dao.DeliveryReportMapper;
import com.hd.agent.report.model.DeliveryCustomerOutReport;
import com.hd.agent.report.service.IDeliveryReportService;

/**
 * 代配送报表
 * 
 * @author huangzhiqian
 */
public class DeliveryReportServiceImpl extends BaseFilesServiceImpl implements IDeliveryReportService {
	private DeliveryReportMapper deliveryReportMapper;

	public DeliveryReportMapper getDeliveryReportMapper() {
		return deliveryReportMapper;
	}

	public void setDeliveryReportMapper(DeliveryReportMapper deliveryReportMapper) {
		this.deliveryReportMapper = deliveryReportMapper;
	}

	@Override
	public PageData getDeliveryCustomerData(PageMap pageMap,int type) throws Exception {
		
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		if(StringUtils.isNotEmpty(groupcols)){
			if(groupcols.indexOf("customerid")!=-1){
				groupcols=groupcols.replace("customerid", "t.customerid");
			}
			if(groupcols.indexOf("goodsid")!=-1){
				groupcols=groupcols.replace("goodsid", "t1.goodsid");
			}
			if(groupcols.indexOf("brandid")!=-1){
				groupcols=groupcols.replace("brandid", "t1.brandid");
			}
			if(groupcols.indexOf("goodssort")!=-1){
				groupcols=groupcols.replace("goodssort", "t1.goodssort");
			}
			if(groupcols.indexOf("supplierid")!=-1){
				groupcols=groupcols.replace("supplierid", "t.supplierid");
			}
			pageMap.getCondition().remove("groupcols");
			pageMap.getCondition().put("groupcols", groupcols);
		}
		
		String queryTable ="";
		if(type==DeliveryReportAction.REPORT_CUSTOMER_OUT){
			queryTable= " t_storage_delivery_out t , t_storage_delivery_out_detail t1 ";
		}else if(type==DeliveryReportAction.REPORT_CUSTOMER_ENTER){
			queryTable= " t_storage_delivery_enter t , t_storage_delivery_enter_detail t1 ";
		}
		pageMap.getCondition().put("queryTable",queryTable);
		
		
		List<DeliveryCustomerOutReport> rsList=deliveryReportMapper.getDeliveryCustomerData(pageMap);
		for(DeliveryCustomerOutReport outrp:rsList){
			if(StringUtils.isNotEmpty(groupcols)){
				
				if(groupcols.indexOf("customerid")!=-1){
					String cusId=outrp.getCustomerid();
					if(StringUtils.isNotEmpty(cusId)){
						Customer cus=getCustomerByID(cusId);
						if(cus!=null) outrp.setCustomername(cus.getName());
					}
				}
				if(groupcols.indexOf("goodsid")!=-1){
					String godId=outrp.getGoodsid();
					if(StringUtils.isNotEmpty(godId)){
						GoodsInfo godIf=getGoodsInfoByID(godId);
						if(godIf!=null) outrp.setGoodsname(godIf.getName());
					}
				}
				
				if(groupcols.indexOf("brandid")!=-1){
					String bId=outrp.getBrandid();
					if(StringUtils.isNotEmpty(bId)){
						Brand bnd=getGoodsBrandByID(bId);
						if(bnd!=null) outrp.setBrandname(bnd.getName());
					}
				}
				if(groupcols.indexOf("goodssort")!=-1){
					String soid=outrp.getGoodssort();
					if(StringUtils.isNotEmpty(soid)){
						WaresClass waresClass = getWaresClassByID(soid);
						if(waresClass!=null) outrp.setGoodssortname(waresClass.getName());
					}
				}
				
				if(groupcols.indexOf("supplierid")!=-1){
					String sId=outrp.getSupplierid();
					if(StringUtils.isNotEmpty(sId)){
						BuySupplier bsl=getSupplierInfoById(sId);
						if(bsl!=null) outrp.setSuppliername(bsl.getName());
					}
				}
			}else{
				String cusId=outrp.getCustomerid();
				if(StringUtils.isNotEmpty(cusId)){
					Customer cus=getCustomerByID(cusId);
					if(cus!=null) outrp.setCustomername(cus.getName());
				}
			}
		}
		
		
		pageMap.getCondition().put("isLimit", "false");
		List<DeliveryCustomerOutReport> rsListWhitOutLimit=deliveryReportMapper.getDeliveryCustomerData(pageMap);
		BigDecimal saleprice=new BigDecimal(0);
		BigDecimal cost=new BigDecimal(0);
		BigDecimal totalbox=new BigDecimal(0);
		BigDecimal volume=new BigDecimal(0);
		BigDecimal weight=new BigDecimal(0);
		for(DeliveryCustomerOutReport outrp:rsListWhitOutLimit){
			if(outrp.getSaleprice()!=null){
				saleprice=saleprice.add(outrp.getSaleprice());
			}
			if(outrp.getCost()!=null){
				cost=cost.add(outrp.getCost());
			}
			if(outrp.getTotalbox()!=null){
				totalbox=totalbox.add(outrp.getTotalbox());
			}
			if(outrp.getVolume()!=null){
				volume=volume.add(outrp.getVolume());
			}
			if(outrp.getWeight()!=null){
				weight=weight.add(outrp.getWeight());
			}
			
		}
		
		
		int count=rsListWhitOutLimit.size();
		
		
		ArrayList<DeliveryCustomerOutReport> list=new ArrayList<DeliveryCustomerOutReport>();
		DeliveryCustomerOutReport foot=new DeliveryCustomerOutReport();

		
		foot.setSaleprice(saleprice);
		foot.setCost(cost);
		foot.setTotalbox(totalbox);
		foot.setVolume(volume);
		foot.setWeight(weight);
		//合计的位置
		if(StringUtils.isNotEmpty(groupcols)){
			if(groupcols.indexOf("customerid")!=-1){
				foot.setCustomername("合计");
			}else{
				if(groupcols.indexOf("goodsid")!=-1){
					foot.setGoodsname("合计");
				}else{
					if(groupcols.indexOf("brandid")!=-1){
						foot.setBrandname("合计");
					}else{
						if(groupcols.indexOf("goodssort")!=-1){
							foot.setGoodssortname("合计");
						}else{
							if(groupcols.indexOf("supplierid")!=-1){
								foot.setSuppliername("合计");
							}
						}
					}
				}
			}
		}else{
			foot.setCustomername("合计");
		}
		
		
		
		
		
		
		
		
		list.add(foot);
		
		
		PageData pg=new PageData(count, rsList, pageMap);
		pg.setFooter(list);
		
		return pg;
	}

	@Override
	public List<DeliveryCustomerOutReport> getDeliveryCustomerDataExcel(PageMap pageMap,int type) throws Exception {
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		if(StringUtils.isNotEmpty(groupcols)){
			if(groupcols.indexOf("customerid")!=-1){
				groupcols=groupcols.replace("customerid", "t.customerid");
			}
			if(groupcols.indexOf("goodsid")!=-1){
				groupcols=groupcols.replace("goodsid", "t1.goodsid");
			}
			if(groupcols.indexOf("brandid")!=-1){
				groupcols=groupcols.replace("brandid", "t1.brandid");
			}
			if(groupcols.indexOf("goodssort")!=-1){
				groupcols=groupcols.replace("goodssort", "t1.goodssort");
			}
			if(groupcols.indexOf("supplierid")!=-1){
				groupcols=groupcols.replace("supplierid", "t.supplierid");
			}
			pageMap.getCondition().remove("groupcols");
			pageMap.getCondition().put("groupcols", groupcols);
		}
		String queryTable ="";
		if(type==DeliveryReportAction.REPORT_CUSTOMER_OUT){
			queryTable= " t_storage_delivery_out t , t_storage_delivery_out_detail t1 ";
		}else if(type==DeliveryReportAction.REPORT_CUSTOMER_ENTER){
			queryTable= " t_storage_delivery_enter t , t_storage_delivery_enter_detail t1 ";
		}
		pageMap.getCondition().put("queryTable",queryTable);
		pageMap.getCondition().put("isLimit", "false");
		
		List<DeliveryCustomerOutReport> rsList = deliveryReportMapper.getDeliveryCustomerData(pageMap);
		
		for(DeliveryCustomerOutReport outrp:rsList){
			if(StringUtils.isNotEmpty(groupcols)){
				
				if(groupcols.indexOf("customerid")!=-1){
					String cusId=outrp.getCustomerid();
					if(StringUtils.isNotEmpty(cusId)){
						Customer cus=getCustomerByID(cusId);
						if(cus!=null) outrp.setCustomername(cus.getName());
					}
				}
				if(groupcols.indexOf("goodsid")!=-1){
					String godId=outrp.getGoodsid();
					if(StringUtils.isNotEmpty(godId)){
						GoodsInfo godIf=getGoodsInfoByID(godId);
						if(godIf!=null) outrp.setGoodsname(godIf.getName());
					}
				}
				
				if(groupcols.indexOf("brandid")!=-1){
					String bId=outrp.getBrandid();
					if(StringUtils.isNotEmpty(bId)){
						Brand bnd=getGoodsBrandByID(bId);
						if(bnd!=null) outrp.setBrandname(bnd.getName());
					}
				}
				if(groupcols.indexOf("goodssort")!=-1){
					String soid=outrp.getGoodssort();
					if(StringUtils.isNotEmpty(soid)){
						WaresClass waresClass = getWaresClassByID(soid);
						if(waresClass!=null) outrp.setGoodssortname(waresClass.getName());
					}
				}
				
				if(groupcols.indexOf("supplierid")!=-1){
					String sId=outrp.getSupplierid();
					if(StringUtils.isNotEmpty(sId)){
						BuySupplier bsl=getSupplierInfoById(sId);
						if(bsl!=null) outrp.setSuppliername(bsl.getName());
					}
				}
			}else{
				String cusId=outrp.getCustomerid();
				if(StringUtils.isNotEmpty(cusId)){
					Customer cus=getCustomerByID(cusId);
					if(cus!=null) outrp.setCustomername(cus.getName());
				}
			}
		}
		return rsList;
	}
	@Override
	public PageData showAllDeliveryData(PageMap pageMap) throws Exception {
		String datasql = getDataAccessRule("t_report_storage_bak", "t");
		pageMap.setDataSql(datasql);
		//小计列
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		if(!pageMap.getCondition().containsKey("groupcols")){
			groupcols = "goodsid";
		}
		pageMap.getCondition().put("groupcols", groupcols);
		List<DeliveryAllReport> list = deliveryReportMapper.showAllDeliveryData(pageMap);
		for(DeliveryAllReport storageBuySaleReport : list){
			GoodsInfo goodsInfo = getAllGoodsInfoByID(storageBuySaleReport.getGoodsid());
			if(null!=goodsInfo ){
				storageBuySaleReport.setBarcode(goodsInfo.getBarcode());
				storageBuySaleReport.setGoodsname(goodsInfo.getName());
				storageBuySaleReport.setUnitname(goodsInfo.getMainunitName());
				Map auxsafeMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getSafenum());
				storageBuySaleReport.setAuxsafenumdetail((String) auxsafeMap.get("auxnumdetail"));
			}else{
				Brand brand = getGoodsBrandByID(storageBuySaleReport.getGoodsid());
				if(null!=brand){
					storageBuySaleReport.setGoodsname("品牌折扣："+brand.getName());
				}else{
					storageBuySaleReport.setGoodsname("其他折扣");
				}
			}
			//品牌
			if(StringUtils.isNotEmpty(storageBuySaleReport.getBrandid())){
				Brand brand = getGoodsBrandByID(storageBuySaleReport.getBrandid());
				if(null != brand){
					storageBuySaleReport.setBrandname(brand.getName());
				}else{
					storageBuySaleReport.setBrandname("其他未定义");
				}
			}else{
				storageBuySaleReport.setBrandname("其他未指定");
			}
			//仓库
			if(StringUtils.isNotEmpty(storageBuySaleReport.getStorageid())) {
				StorageInfo storageInfo = getStorageInfoByID(storageBuySaleReport.getStorageid());
				if(null != storageInfo) {
					storageBuySaleReport.setStoragename(storageInfo.getName());
				}
			}
			//供应商
			BuySupplier supplier = getSupplierInfoById(storageBuySaleReport.getSupplierid());
			if(null != supplier){
				storageBuySaleReport.setSuppliername(supplier.getName());
			}else{
				storageBuySaleReport.setSuppliername("其他未定义");
			}
			//默认税种
			TaxType taxType = getTaxType(goodsInfo.getDefaulttaxtype());

			//箱装量
			BigDecimal boxnum=goodsInfo.getBoxnum()==null?BigDecimal.ZERO:goodsInfo.getBoxnum();
			BigDecimal existingnum=storageBuySaleReport.getExistingnum()==null?BigDecimal.ZERO:storageBuySaleReport.getExistingnum();;//期初数量
			BigDecimal existingbox=storageBuySaleReport.getExistingbox()==null?BigDecimal.ZERO:storageBuySaleReport.getExistingbox();;//期初箱数

			BigDecimal saleouttotalnum=storageBuySaleReport.getSaleouttotalnum()==null?BigDecimal.ZERO:storageBuySaleReport.getSaleouttotalnum();
			BigDecimal saleintotalnum=storageBuySaleReport.getSaleintotalnum()==null?BigDecimal.ZERO:storageBuySaleReport.getSaleintotalnum();
			BigDecimal intotalnum=storageBuySaleReport.getIntotalnum()==null?BigDecimal.ZERO:storageBuySaleReport.getIntotalnum();
			BigDecimal outtotalnum=storageBuySaleReport.getOuttotalnum()==null?BigDecimal.ZERO:storageBuySaleReport.getOuttotalnum();
			BigDecimal inprice=storageBuySaleReport.getSaleinaddcostprice()==null?BigDecimal.ZERO:storageBuySaleReport.getSaleinaddcostprice();//成本价
			BigDecimal outprice=storageBuySaleReport.getSaleoutaddcostprice()==null?BigDecimal.ZERO:storageBuySaleReport.getSaleoutaddcostprice();//成本价
			BigDecimal endnum=existingnum.add(intotalnum).add(outtotalnum).subtract(saleintotalnum).subtract(saleouttotalnum);//期末数量
			BigDecimal endbox=storageBuySaleReport.getEndtotalbox()==null?BigDecimal.ZERO:storageBuySaleReport.getEndtotalbox();//期末箱数
			storageBuySaleReport.setEndnum(endnum);
			storageBuySaleReport.setEndtotalbox(endbox);
			Map auxendnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), endnum);
			storageBuySaleReport.setAuxendnumdetail((String) auxendnumMap.get("auxnumdetail"));//期末数量
			Map auxInitnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getExistingnum());
			storageBuySaleReport.setAuxinitnumdetail((String) auxInitnumMap.get("auxnumdetail"));//期初数量
			Map buyinnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), intotalnum);
			storageBuySaleReport.setAuxbuyinnumdetail((String) buyinnumMap.get("auxnumdetail"));//采购入库箱数
			Map buyoutnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), outtotalnum);
			storageBuySaleReport.setAuxbuyoutnumdetail((String) buyoutnumMap.get("auxnumdetail"));//采购出库箱数
			Map saleoutnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), saleouttotalnum);
			storageBuySaleReport.setAuxsaleoutnumdetail((String) saleoutnumMap.get("auxnumdetail"));//销售出库箱数
			Map saleinnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), saleintotalnum);
			storageBuySaleReport.setAuxsaleinnumdetail((String) saleinnumMap.get("auxnumdetail"));//退货箱数

			storageBuySaleReport.setExistingbox(existingbox);

		}
		PageData pageData = new PageData(deliveryReportMapper.showAllDeliveryCount(pageMap), list, pageMap);
		List<DeliveryAllReport> footer = deliveryReportMapper.showAllDeliverySum(pageMap);

		for (DeliveryAllReport storageBuySaleReport : footer){
			if(null != storageBuySaleReport){
				storageBuySaleReport.setIntotalnum(storageBuySaleReport.getIntotalnum().setScale(0, BigDecimal.ROUND_HALF_UP));
				storageBuySaleReport.setOuttotalnum(storageBuySaleReport.getOuttotalnum().setScale(0, BigDecimal.ROUND_HALF_UP));
				storageBuySaleReport.setSaleintotalnum(storageBuySaleReport.getSaleintotalnum().setScale(0, BigDecimal.ROUND_HALF_UP));
				storageBuySaleReport.setSaleouttotalnum(storageBuySaleReport.getSaleouttotalnum().setScale(0, BigDecimal.ROUND_HALF_UP));
				BigDecimal endnum=storageBuySaleReport.getExistingnum().add(storageBuySaleReport.getIntotalnum()).add(storageBuySaleReport.getOuttotalnum()).
						subtract(storageBuySaleReport.getSaleintotalnum()).subtract(storageBuySaleReport.getSaleouttotalnum());//期末数量
				BigDecimal endamount=storageBuySaleReport.getTaxamount().add(storageBuySaleReport.getIntaxamount()).add(storageBuySaleReport.getOuttaxamount()).
						subtract(storageBuySaleReport.getSaleintaxamount()).subtract(storageBuySaleReport.getSaleouttaxamount());//期末金额
				BigDecimal endnotaxamount=storageBuySaleReport.getNotaxamount().add(storageBuySaleReport.getInnotaxamount()).
						add(storageBuySaleReport.getOutnotaxamount()).subtract(storageBuySaleReport.getSaleinnotaxamount()).subtract(storageBuySaleReport.getSaleoutnotaxamount());
				storageBuySaleReport.setEndnum(endnum);
				storageBuySaleReport.setEndamount(endamount);
				storageBuySaleReport.setEndnotaxamount(endnotaxamount);

				if(groupcols.indexOf("goodsid") != -1){
					storageBuySaleReport.setGoodsid("");
					storageBuySaleReport.setGoodsname("合计");
				}else if(groupcols.indexOf("storageid") != -1){
					storageBuySaleReport.setStorageid("");
					storageBuySaleReport.setStoragename("合计");
				}else if(groupcols.indexOf("brandid") != -1){
					storageBuySaleReport.setBrandid("");
					storageBuySaleReport.setBrandname("合计");
				}else if(groupcols.indexOf("supplierid") != -1){
					storageBuySaleReport.setSupplierid("");
					storageBuySaleReport.setSuppliername("合计");
				}
				//storageBuySaleReport.setInaddcostprice(null);
				pageData.setFooter(footer);
			}
		}
		return pageData;
	}

	
	
}

