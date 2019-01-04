/**
 * @(#)PurchaseQueryServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 30, 2013 chenwei 创建版本
 */
package com.hd.agent.purchase.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.TaxType;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.purchase.dao.PurchaseQueryMapper;
import com.hd.agent.purchase.model.PurchaseJournalAccount;
import com.hd.agent.purchase.service.IPurchaseQueryService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 
 * 采购业务查询service实现类
 * @author chenwei
 */
public class PurchaseQueryServiceImpl extends BasePurchaseServiceImpl implements IPurchaseQueryService{

	private PurchaseQueryMapper purchaseQueryMapper;
	
	public PurchaseQueryMapper getPurchaseQueryMapper() {
		return purchaseQueryMapper;
	}

	public void setPurchaseQueryMapper(PurchaseQueryMapper purchaseQueryMapper) {
		this.purchaseQueryMapper = purchaseQueryMapper;
	}

	@Override
	public PageData showArrivalReturnDetailList(PageMap pageMap)
			throws Exception {
		String dataSql = getDataAccessRule("t_report_purchase_base", "y");
		pageMap.setDataSql(dataSql);
		Map condition=pageMap.getCondition();
		List<Map<String,Object>> list = purchaseQueryMapper.showArrivalReturnDetailList(pageMap);
		java.text.DecimalFormat df=null;
		for(Map<String,Object> map : list){
			String id = (String) map.get("id");
			String businessdate = (String) map.get("businessdate");
			//小计项
			if(id.equals(businessdate)){
				map.put("businessdate", businessdate+" 小计");
				map.put("id", "");
				map.put("taxprice", "");
				map.put("unitname", "");
				map.put("auxunitname", "");
				map.put("billtype", "");
				map.put("isinvoicename", "");
				map.put("supplierid", "");
				map.put("unitnum", "");
				
				BigDecimal auxnum = (BigDecimal) map.get("auxnum");
				BigDecimal auxremainder = (BigDecimal) map.get("auxremainder");
				String auxnumdetail = "";
				df=new java.text.DecimalFormat("0");
				if(null!=auxnum){
					auxnumdetail=df.format(auxnum);
					auxnumdetail=auxnumdetail.trim();
					if(StringUtils.isEmpty(auxnumdetail)){
						auxnumdetail="0";
					}
				}else {
					auxnumdetail="0";
				}
				if(null!=auxremainder){
					auxnumdetail=auxnumdetail+","+auxremainder;
				}else{
					auxnumdetail=auxnumdetail+",0";
				}
				map.put("auxnumdetail", CommonUtils.strDigitNumDeal(auxnumdetail));
				
			}else{
				String goodsid = (String) map.get("goodsid");
				GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
				if(null!=goodsInfo){
					map.put("goodsname", goodsInfo.getName());
					map.put("brandname", goodsInfo.getBrandName());
					map.put("model", goodsInfo.getModel());
					map.put("barcode", goodsInfo.getBarcode());
					
					TaxType taxType = getTaxType(goodsInfo.getDefaulttaxtype());
					if(null!=taxType){
						map.put("taxtypename", taxType.getName());
					}
				}
				String supplierid = (String) map.get("supplierid");
				BuySupplier buySupplier = getSupplierInfoById(supplierid);
				if(null!=buySupplier){
					map.put("suppliername", buySupplier.getName());
				}
				String billtype = (String) map.get("billtype");
				if("0".equals(billtype)){
					map.put("billtypename", "进货单");
				}else if("1".equals(billtype)){
					map.put("billtypename", "退货单");
				}
				BigDecimal auxnum = (BigDecimal) map.get("auxnum");
				BigDecimal auxremainder = (BigDecimal) map.get("auxremainder");
				String auxunitname = (String) map.get("auxunitname");
				String unitname = (String) map.get("unitname");
				String auxnumdetail = "";
				if(null==auxnum){
					auxnum=BigDecimal.ZERO;
				}
				if("1".equals(billtype)){
					if(BigDecimal.ZERO.compareTo(auxnum)==0){
						auxnumdetail = "-0";
					}else{
						auxnumdetail = auxnum.setScale(0, BigDecimal.ROUND_HALF_UP).toString();
					}
				}else{
					auxnumdetail = auxnum.setScale(0, BigDecimal.ROUND_HALF_UP).toString();
				}
				if(StringUtils.isNotEmpty(auxunitname)){
					auxnumdetail += auxunitname;
				}
				if(StringUtils.isNotEmpty(auxunitname) && !auxunitname.equals(unitname)){
					if(null!=auxremainder){
						if(StringUtils.isNotEmpty(auxunitname)){
							if(BigDecimal.ZERO.compareTo(auxremainder)!=0){
								if("1".equals(billtype)){
									auxnumdetail += auxremainder.negate().toString();
								}else{
									auxnumdetail += auxremainder.toString();
								}
								if(StringUtils.isNotEmpty(unitname)){
									auxnumdetail += unitname;
								}
							}
						}else{
							auxnumdetail = auxnumdetail+","+ auxremainder.toString();
						}
					}
				}
				map.put("auxnumdetail", CommonUtils.strDigitNumDeal(auxnumdetail));
				
				String iswriteoff = (String) map.get("iswriteoff");
				if("1".equals(iswriteoff)){
					map.put("iswriteoffname", "已核销");
				}else{
					map.put("iswriteoffname", "未核销");
				}
				
			}
			BigDecimal taxamount = (BigDecimal) map.get("taxamount");
			BigDecimal notaxamount = (BigDecimal) map.get("notaxamount");
			BigDecimal tax = taxamount.subtract(notaxamount);
			map.put("tax", tax);
		}
		PageData pageData = new PageData(purchaseQueryMapper.showArrivalReturnDetailCount(pageMap),list,pageMap);
		condition.put("noShowSubtotal", "true");
		List<Map<String,Object>> footer=new ArrayList<Map<String,Object>>();
		Map<String, Object> footData=purchaseQueryMapper.showArrivalReturnDetailTotalSum(pageMap);
		if(null!=footData){

			BigDecimal taxamount = (BigDecimal) footData.get("taxamount");
			BigDecimal notaxamount = (BigDecimal) footData.get("notaxamount");
			BigDecimal tax =BigDecimal.ZERO;
			if(null!=taxamount && null!=notaxamount){
				 tax=taxamount.subtract(notaxamount);
			}
			
			BigDecimal auxnum = (BigDecimal) footData.get("auxnum");
			BigDecimal auxremainder =(BigDecimal) footData.get("auxremainder");
			String auxnumdetail = "";
			df=new java.text.DecimalFormat("0");
			if(null!=auxnum){
				auxnumdetail=df.format(auxnum);
				auxnumdetail=auxnumdetail.trim();
				if(StringUtils.isEmpty(auxnumdetail)){
					auxnumdetail="0";
				}
			}else {
				auxnumdetail="0";
			}
			if(null!=auxremainder){
				auxnumdetail=auxnumdetail+","+auxremainder.toString();
			}else{
				auxnumdetail=auxnumdetail+",0";
			}
			
			footData.put("id", "合计金额");
			footData.put("tax", tax);
			footData.put("auxnumdetail", CommonUtils.strDigitNumDeal(auxnumdetail));
			footData.put("unitnum", "");
			footer.add(footData);
		}
		pageData.setFooter(footer);
		return pageData;
	}
	
	/**
	 * 采购进货退货流水账
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-10-7
	 */
	public List showPurchaseJournalAccountList(PageMap pageMap) throws Exception{
		Map condition=pageMap.getCondition();
		if(null!=condition ){
			//流水账为已经核销
			if(condition.containsKey("invoice0")){
				condition.remove("invoice0");
				pageMap.setCondition(condition);
			}
		}

		SysUser sysUser=getSysUser();
		String dataSql1 = getDataAccessRule("t_purchase_arrivalorder", "t");
		String dataSql2 = getDataAccessRule("t_purchase_returnorder", "t");
		pageMap.getCondition().put("dataSql1", dataSql1);
		pageMap.getCondition().put("dataSql2", dataSql2);
		List<PurchaseJournalAccount> list = purchaseQueryMapper.showPurchaseJournalAccountList(pageMap);
		for(PurchaseJournalAccount item : list){
			String id = item.getId();
			String businessdate = item.getBusinessdate();
			item.setAdduserid(sysUser.getUserid());
			item.setAddusername(sysUser.getName());
			//小计项
			if(id.equals(businessdate)){
				item.setAdduserid("");
				item.setAddusername("");
				item.setBusinessdate("");
				item.setCanceldate("");
				item.setGoodsname( businessdate+" 小计");
				item.setId("");
				item.setTaxprice(null);
				item.setUnitname("");
				item.setAuxunitname("");
				//item.setBilltype("");
				item.setIswriteoff("");
				item.setSupplierid("");
				BigDecimal auxnum = item.getAuxnum();
				BigDecimal auxremainder = item.getAuxremainder();
				String auxnumdetail = "";
				if(null!=auxnum){
					auxnumdetail = auxnum.setScale(0,BigDecimal.ROUND_HALF_UP).toString();
				}else{
					auxnumdetail = "0";
				}
				if(null!=auxremainder){
					auxnumdetail += ","+auxremainder.setScale(0,BigDecimal.ROUND_HALF_UP).toString();
				}else{
					auxnumdetail += ",0";
				}
				item.setAuxnumdetail(auxnumdetail);
			}else{
				String goodsid = item.getGoodsid();
				GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
				if(null!=goodsInfo){
					item.setGoodsname(goodsInfo.getName());
					item.setGoodsInfo(goodsInfo);
					
					TaxType taxType = getTaxType(goodsInfo.getDefaulttaxtype());
					if(null!=taxType){
						item.setTaxtypename(taxType.getName());
						item.setTaxtype(taxType.getType());
					}
				}
				if(StringUtils.isNotEmpty(item.getSupplierid())){
					BuySupplier buySupplier = getSupplierInfoById(item.getSupplierid());
					if(null!=buySupplier){
						item.setSuppliername(buySupplier.getName());
					}
				}
				String billtype = item.getBilltype();
				if("0".equals(billtype)){
					item.setBilltypename("进货单");
				}else if("1".equals(billtype)){
					item.setBilltypename( "退货单");
				}
				String auxnumdetail = "";
				if(null==item.getAuxnum()){
					item.setAuxnum(BigDecimal.ZERO);
				}
				if("2".equals(item.getBilltype())){
					if(BigDecimal.ZERO.compareTo(item.getAuxnum())==0){
						auxnumdetail= "-0";
					}else{
						auxnumdetail = item.getAuxnum().setScale(0, BigDecimal.ROUND_HALF_UP).toString();						
					}
				}else{
					auxnumdetail = item.getAuxnum().setScale(0, BigDecimal.ROUND_HALF_UP).toString();
				}
				auxnumdetail += item.getAuxunitname();
				if(null!=item.getAuxremainder() && BigDecimal.ZERO.compareTo(item.getAuxremainder())!=0){
					if("2".equals(item.getBilltype())){
						auxnumdetail += item.getAuxremainder().setScale(0, BigDecimal.ROUND_HALF_UP).negate().toString();	
					}else{
						auxnumdetail += item.getAuxremainder().setScale(0, BigDecimal.ROUND_HALF_UP).toString();	
					}
					auxnumdetail += item.getUnitname();
				}
				item.setAuxnumdetail( auxnumdetail);
				
				String isinvoice = item.getIswriteoff();
				if("2".equals(isinvoice)){
					item.setWriteoffname( "已核销");
				}else{
					item.setWriteoffname( "未核销");
				}
				
			}
			BigDecimal taxamount = item.getTaxamount();
			BigDecimal notaxamount = item.getNotaxamount();
			item.setTax( taxamount.subtract(notaxamount));
		}
		if(null!=list && list.size()>0){
			condition.put("noShowSubtotal", "true");
			PurchaseJournalAccount foot=new PurchaseJournalAccount();
			Map<String, Object> footData=purchaseQueryMapper.showArrivalReturnDetailTotalSum(pageMap);
			if(null!=footData){
	
				BigDecimal taxamount = (BigDecimal) footData.get("taxamount");
				BigDecimal notaxamount = (BigDecimal) footData.get("notaxamount");
				BigDecimal tax =BigDecimal.ZERO;
				if(null!=taxamount && null!=notaxamount){
					 tax=taxamount.subtract(notaxamount);
				}
				
				BigDecimal auxnum = (BigDecimal) footData.get("auxnum");
				BigDecimal auxremainder =(BigDecimal) footData.get("auxremainder");
				String auxnumdetail = "";
				java.text.DecimalFormat df = new java.text.DecimalFormat("0");
				if(null!=auxnum){
					auxnumdetail=df.format(auxnum);
					auxnumdetail=auxnumdetail.trim();
					if(StringUtils.isEmpty(auxnumdetail)){
						auxnumdetail="0";
					}
				}else {
					auxnumdetail="0";
				}
				if(null!=auxremainder){
					auxnumdetail=auxnumdetail+","+df.format(auxremainder);
				}else{
					auxnumdetail=auxnumdetail+",0";
				}
				foot.setBusinessdate("合计金额");
				foot.setId("合计金额");
				foot.setTax(tax);
				foot.setGoodsname("");
				foot.setTaxamount(taxamount);
				foot.setNotaxamount(notaxamount);
				foot.setAuxnumdetail(auxnumdetail);
				foot.setBilltype("3");
				list.add(foot);
			}
		}
		return list;
	}
}

