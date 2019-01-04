/**
 * @(#)FinancePaymentsServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 31, 2013 chenwei 创建版本
 */
package com.hd.agent.report.service.impl;

import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.report.dao.FinancePaymentsMapper;
import com.hd.agent.report.model.*;
import com.hd.agent.report.service.IFinancePaymentsService;
import org.activiti.engine.impl.Page;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 应付款service实现类
 * @author chenwei
 */
public class FinancePaymentsServiceImpl extends BaseFilesServiceImpl implements
		IFinancePaymentsService {
	
	private FinancePaymentsMapper financePaymentsMapper;

    public FinancePaymentsMapper getFinancePaymentsMapper() {
		return financePaymentsMapper;
	}

	public void setFinancePaymentsMapper(FinancePaymentsMapper financePaymentsMapper) {
		this.financePaymentsMapper = financePaymentsMapper;
	}

	@Override
	public PageData showSupplierPaymentsData(PageMap pageMap) throws Exception {
		if(!pageMap.getCondition().containsKey("businessdate1")){
			pageMap.getCondition().put("businessdate1", "1900-01-01");
		}
		String dataSql = getDataAccessRule("t_report_finance_payments", "z");
		pageMap.setDataSql(dataSql);
		List<SupplierPayments> list = financePaymentsMapper.showSupplierPaymentsData(pageMap);
		for(SupplierPayments supplierPayments : list){
			BuySupplier buySupplier = getSupplierInfoById(supplierPayments.getSupplierid());
			if(null!=buySupplier){
				supplierPayments.setSuppliername(buySupplier.getName());
			}
			DepartMent departMent = getDepartmentByDeptid(supplierPayments.getBuydeptid());
			if(null!=departMent){
				supplierPayments.setBuydeptname(departMent.getName());
			}
		}
		PageData pageData = new PageData(financePaymentsMapper.showSupplierPaymentsDataCount(pageMap),list,pageMap);
		List footer = new ArrayList();
		SupplierPayments supplierPaymentsSum = financePaymentsMapper.showSupplierPaymentsSumData(pageMap);
		if(null!=supplierPaymentsSum){
			supplierPaymentsSum.setSuppliername("合计");
			footer.add(supplierPaymentsSum);
		}
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public PageData getSupplierInvoiceWriteoffData(PageMap pageMap)
			throws Exception {
		List<SupplierPayments> list = financePaymentsMapper.getSupplierInvoiceWriteoffData(pageMap);
		for(SupplierPayments supplierPayments : list){
			BuySupplier buySupplier = getSupplierInfoById(supplierPayments.getSupplierid());
			if(null!=buySupplier){
				supplierPayments.setSuppliername(buySupplier.getName());
			}
			//采购总额 = 进货金额 - 退货金额
			if(null != supplierPayments.getBuyamount() && null != supplierPayments.getReturnamount()){
				supplierPayments.setRealpayamount(supplierPayments.getBuyamount().subtract(supplierPayments.getReturnamount()));
			}
			//未核销金额 = 开票金额 - 已核销金额
			if(null != supplierPayments.getInvoiceamount() && null != supplierPayments.getPayamount()){
				supplierPayments.setUnpayamount(supplierPayments.getInvoiceamount().subtract(supplierPayments.getPayamount()));
			}
			// 若采购总额 - 已核销金额 <= 当前余额 当前应付金额 = 0;反之，当前应付金额 = 采购总额 - 已核销金额 - 当前余额；
			if(null != supplierPayments.getRealpayamount() && null != supplierPayments.getPayamount()){
				BigDecimal sub = supplierPayments.getRealpayamount().subtract(supplierPayments.getPayamount());
				if(null != supplierPayments.getPrepayamount()){
					if(sub.compareTo(supplierPayments.getPrepayamount()) == 1){
						supplierPayments.setPayableamount(sub.subtract(supplierPayments.getPrepayamount()));
					}else{
						supplierPayments.setPayableamount(BigDecimal.ZERO);
					}
				}
			}
			DepartMent departMent = getDepartmentByDeptid(supplierPayments.getBuydeptid());
			if(null!=departMent){
				supplierPayments.setBuydeptname(departMent.getName());
			}
		}
		PageData pageData = new PageData(financePaymentsMapper.getSupplierInvoiceWriteoffDataCount(pageMap),list,pageMap);
		SupplierPayments supplierPaymentsSum = financePaymentsMapper.getSupplierInvoiceWriteoffSumData(pageMap);
		if(null!=supplierPaymentsSum){
			supplierPaymentsSum.setSuppliername("合计");
			//采购总额 = 进货金额 - 退货金额
			if(null != supplierPaymentsSum.getBuyamount() && null != supplierPaymentsSum.getReturnamount()){
			supplierPaymentsSum.setRealpayamount(supplierPaymentsSum.getBuyamount().subtract(supplierPaymentsSum.getReturnamount()));
			}
			//未核销金额金额 = 开票金额 - 已核销金额
			if(null != supplierPaymentsSum.getInvoiceamount() && null != supplierPaymentsSum.getPayamount()){
				supplierPaymentsSum.setUnpayamount(supplierPaymentsSum.getInvoiceamount().subtract(supplierPaymentsSum.getPayamount()));
			}
			// 若采购总额 - 已核销金额 <= 当前余额 当前应付金额 = 0;反之，当前应付金额 = 采购总额 - 已核销金额 - 当前余额；
			if(null != supplierPaymentsSum.getRealpayamount() && null != supplierPaymentsSum.getPayamount()){
				BigDecimal sub = supplierPaymentsSum.getRealpayamount().subtract(supplierPaymentsSum.getPayamount());
				if(null != supplierPaymentsSum.getPrepayamount()){
					if(sub.compareTo(supplierPaymentsSum.getPrepayamount()) == 1){
						supplierPaymentsSum.setPayableamount(sub.subtract(supplierPaymentsSum.getPrepayamount()));
					}else{
						supplierPaymentsSum.setPayableamount(BigDecimal.ZERO);
					}
				}
			}
			List footer = new ArrayList();
			footer.add(supplierPaymentsSum);
			pageData.setFooter(footer);
		}
		return pageData;
	}

	@Override
	public PageData showSupplierPaysFlowListData(PageMap pageMap)
			throws Exception {
		String dataSql = getDataAccessRule("t_report_finance_payments", "z");
		pageMap.setDataSql(dataSql);
		List<SupplierPayFlow> list = financePaymentsMapper.showSupplierPaysFlowListData(pageMap);
        String auxunitnumdetail="";
        for(SupplierPayFlow supplierPayFlow : list){
            if("9".equals(supplierPayFlow.getBilltype())){
                supplierPayFlow.setBusinessdate(supplierPayFlow.getBusinessdate()+" 小计");
                supplierPayFlow.setGoodsid("");
                supplierPayFlow.setId("");
                supplierPayFlow.setUnitid("");
                supplierPayFlow.setUnitname("");
                supplierPayFlow.setIswriteoff("");
                supplierPayFlow.setIsinvoice("");
                supplierPayFlow.setBrandid("");
                supplierPayFlow.setSupplierid("");
                supplierPayFlow.setPrice(null);
                if(null==supplierPayFlow.getAuxnum()){
                    supplierPayFlow.setAuxnum(BigDecimal.ZERO);
                }
                auxunitnumdetail = supplierPayFlow.getAuxnum().setScale(0, BigDecimal.ROUND_HALF_UP).toString();
                auxunitnumdetail += ",";
                if(null!=supplierPayFlow.getAuxremainder()){
                    auxunitnumdetail += supplierPayFlow.getAuxremainder().toString();
                }else{
                    auxunitnumdetail+="0";
                }
                supplierPayFlow.setAuxunitnumdetail(CommonUtils.strDigitNumDeal(auxunitnumdetail));
            }else{
                GoodsInfo goodsInfo = getAllGoodsInfoByID(supplierPayFlow.getGoodsid());
                if(null!=goodsInfo){
                    supplierPayFlow.setBarcode(goodsInfo.getBarcode());
                    supplierPayFlow.setGoodsname(goodsInfo.getName());
                }
                if(StringUtils.isNotEmpty(supplierPayFlow.getBrandid())){
                    Brand brand = getGoodsBrandByID(supplierPayFlow.getBrandid());
                    if(null!=brand){
                        supplierPayFlow.setBrandname(brand.getName());
                    }
                }
                if(StringUtils.isNotEmpty(supplierPayFlow.getSupplierid())){
                    BuySupplier buySupplier = getSupplierInfoById(supplierPayFlow.getSupplierid());
                    if(null!=buySupplier){
                        supplierPayFlow.setSuppliername(buySupplier.getName());
                    }
                }

                if(null==supplierPayFlow.getAuxnum()){
                    supplierPayFlow.setAuxnum(BigDecimal.ZERO);
                }
                if("2".equals(supplierPayFlow.getBilltype())){
                    if(BigDecimal.ZERO.compareTo(supplierPayFlow.getAuxnum())==0){
                        auxunitnumdetail= "-0";
                    }else{
                        auxunitnumdetail = supplierPayFlow.getAuxnum().setScale(0, BigDecimal.ROUND_HALF_UP).toString();
                    }
                }else{
                    auxunitnumdetail = supplierPayFlow.getAuxnum().setScale(0, BigDecimal.ROUND_HALF_UP).toString();
                }
                auxunitnumdetail += supplierPayFlow.getAuxunitname();
                if(null!=supplierPayFlow.getAuxremainder() && BigDecimal.ZERO.compareTo(supplierPayFlow.getAuxremainder())!=0){
                    if("2".equals(supplierPayFlow.getBilltype())){
                        auxunitnumdetail += supplierPayFlow.getAuxremainder().negate().toString();
                    }else{
                        auxunitnumdetail += supplierPayFlow.getAuxremainder().toString();
                    }
                    auxunitnumdetail += supplierPayFlow.getUnitname();
                }
                supplierPayFlow.setAuxunitnumdetail(CommonUtils.strDigitNumDeal(auxunitnumdetail));

                //单据类型
                if("1".equals(supplierPayFlow.getBilltype())){
                    supplierPayFlow.setBilltypename("采购入库单");
                }
                else if("2".equals(supplierPayFlow.getBilltype())){
                    supplierPayFlow.setBilltypename("采购退货出库单");
                }
                if(null != supplierPayFlow.getIsinvoice()){
                    if("0".equals(supplierPayFlow.getIsinvoice())){
                        supplierPayFlow.setIsinvoicename("未开票");
                    }
                    else{
                        supplierPayFlow.setIsinvoicename("已开票");
                    }
                }
                if(null != supplierPayFlow.getIswriteoff()){
                    if("0".equals(supplierPayFlow.getIswriteoff())){
                        supplierPayFlow.setIswriteoffname("未核销");
                    }
                    else{
                        supplierPayFlow.setIswriteoffname("已开票");
                    }
                }
                else{
                    supplierPayFlow.setIsinvoicename("");
                }
            }
        }
        PageData pageData = new PageData(financePaymentsMapper.showSupplierPaysFlowListCount(pageMap),list,pageMap);
        List<SupplierPayFlow> footerList = new ArrayList<SupplierPayFlow>();
        SupplierPayFlow supplierPayFlowSum = financePaymentsMapper.getSupplierPaysFlowSumData(pageMap);
        if(null!=supplierPayFlowSum){
            supplierPayFlowSum.setBusinessdate("合计");
            if(null==supplierPayFlowSum.getAuxnum()){
                supplierPayFlowSum.setAuxnum(BigDecimal.ZERO);
            }
            auxunitnumdetail = supplierPayFlowSum.getAuxnum().setScale(0, BigDecimal.ROUND_HALF_UP).toString();
            if(null!=supplierPayFlowSum.getAuxremainder() && BigDecimal.ZERO.compareTo(supplierPayFlowSum.getAuxremainder())!=0){
                auxunitnumdetail =auxunitnumdetail + ","+ supplierPayFlowSum.getAuxremainder().toString();
            }
            supplierPayFlowSum.setAuxunitnumdetail(CommonUtils.strDigitNumDeal(auxunitnumdetail));
            footerList.add(supplierPayFlowSum);
        }
        pageData.setFooter(footerList);
		return pageData;
	}

	@Override
	public PageData showSupplierPayBankListData(PageMap pageMap)
			throws Exception {
		List<Map<String,Object>> list = financePaymentsMapper.showSupplierPayData(pageMap);
		List<Bank> bankList = getBaseFilesFinanceMapper().getBankList();
		for(Map<String,Object> map : list){
			String supplierid = (String) map.get("supplierid");
			BuySupplier buySupplier = getSupplierInfoById(supplierid);
			if(null!=buySupplier){
				map.put("suppliername", buySupplier.getName());
			}
			pageMap.getCondition().put("thissupplierid", supplierid);
			pageMap.getCondition().put("bank", null);
			//获取该供应商现金金额
			BigDecimal cashamount = financePaymentsMapper.getSupplierPayBankSumBySupplieridAndBank(pageMap);
			map.put("cashamount", cashamount);
			//获取该金额互各银行金额
			for(Bank bank : bankList){
				pageMap.getCondition().put("bank", bank.getId());
				BigDecimal bankamount = financePaymentsMapper.getSupplierPayBankSumBySupplieridAndBank(pageMap);
				map.put("bank"+bank.getId(), bankamount);
			}
		}
		PageData pageData = new PageData(financePaymentsMapper.showSupplierPayCount(pageMap),list,pageMap);
		Map<String,Object> footmap = new HashMap<String,Object>();
		pageMap.getCondition().put("bank", null);
		//合计数据
		BigDecimal totalamount = financePaymentsMapper.getSupplierPaySum(pageMap);
		footmap.put("amount", totalamount);
		footmap.put("suppliername", "合计");
		BigDecimal totalcashamount = financePaymentsMapper.getSupplierPayBankSumByBank(pageMap);
		footmap.put("cashamount", totalcashamount);
		//获取该金额互各银行金额
		for(Bank bank : bankList){
			pageMap.getCondition().put("bank", bank.getId());
			BigDecimal bankamount = financePaymentsMapper.getSupplierPayBankSumByBank(pageMap);
			footmap.put("bank"+bank.getId(), bankamount);
		}
		List<Map<String,Object>> footerList = new ArrayList<Map<String,Object>>();
		footerList.add(footmap);
		pageData.setFooter(footerList);
		return pageData;
	}
	/**
	 * 供应商应付动态-单据对账明细 数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-12-16
	 */
	public PageData showSupplierPaymentsBillDetailData(PageMap pageMap) throws Exception{
		String dataSql = getDataAccessRule("t_report_finance_payments", "t");
		pageMap.setDataSql(dataSql);
		if(pageMap.getCondition().containsKey("showgroupbycol")){
			pageMap.getCondition().remove("showgroupbycol");
		}
		String showgroupbycol="";
		if(!pageMap.getCondition().containsKey("groupcols")){
			pageMap.getCondition().put("groupcols", "id");
		}else{
			String groupcols = (String) pageMap.getCondition().get("groupcols");
			if(groupcols!=null && !"".equals(groupcols.trim())){
				if("supplierid".equals(groupcols.trim().toLowerCase()) 
						|| "businessdate".equals(groupcols.trim().toLowerCase())
						|| "buydeptid".equals(groupcols.trim().toLowerCase())){
					showgroupbycol=groupcols.trim().toLowerCase();
					pageMap.getCondition().put("showgroupbycol", showgroupbycol);
					pageMap.getCondition().put("showgrouplastbycol", showgroupbycol+",id");
				}
				groupcols= "id";
			}else{
				groupcols= "id";
			}
			pageMap.getCondition().put("groupcols", groupcols);
		}
		
		if(StringUtils.isNotEmpty(showgroupbycol)){
			String orderSql="";
			String sort="";
			String orderby="";
			if(pageMap.getCondition().containsKey("sort") && pageMap.getCondition().containsKey("order")){
				sort=pageMap.getCondition().get("sort").toString();
				orderby=pageMap.getCondition().get("order").toString();
				pageMap.getCondition().remove("sort");
				pageMap.getCondition().remove("order");
			}
			if(showgroupbycol.equals(sort)){
				orderSql=sort+" "+orderby+" ,isgroupbycol asc";
			}else{
				if(StringUtils.isNotEmpty(sort)){
					pageMap.getCondition().put("groupcolsort",sort);
					pageMap.getCondition().put("groupcolorder",orderby);
					orderSql=showgroupbycol+" asc ,isgroupbycol asc";
				}else{
					orderSql=showgroupbycol+" asc ,isgroupbycol asc";
				}
			}
			pageMap.setOrderSql(orderSql);
		}else if(!pageMap.getCondition().containsKey("sort") || !pageMap.getCondition().containsKey("order")){
			pageMap.getCondition().put("sort", "businessdate");
			pageMap.getCondition().put("order", "desc");
		}

		if(pageMap.getCondition().containsKey("supplierid")){
			String str = (String) pageMap.getCondition().get("supplierid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") >0){
				pageMap.getCondition().put("issupplierarr", "1");
			}
		}
		List<SupplierPaymentsBillDetail> list=financePaymentsMapper.getSupplierPaymentsBillDetailData(pageMap);
		BuySupplier buySupplier = null;
		for(SupplierPaymentsBillDetail item : list){
			if(StringUtils.isNotEmpty(item.getSupplierid())){
				buySupplier= getSupplierInfoById(item.getSupplierid());
				if(null!=buySupplier){
					item.setSuppliername(buySupplier.getName());
				}
			}
			if(StringUtils.isNotEmpty(item.getBuydeptid())){
				DepartMent departMent = getDepartmentByDeptid(item.getBuydeptid());
				if(null != departMent ){
					item.setBuydeptname(departMent.getName());
				}
			}
			if(StringUtils.isNotEmpty(showgroupbycol)){
				if("supplierid".equals(item.getId())){
					item.setAdduserid("");
					item.setAddusername("");
					item.setBilltype("");
					item.setBilltypename("");
					item.setBusinessdate("");
					item.setBuydeptid("");
					item.setBuydeptname("");
					item.setBuyorderid("");
					item.setId("小计");
					item.setBusinessdate("");			
				}else if("buydeptid".equals(item.getId())){
					item.setAdduserid("");
					item.setAddusername("");
					item.setBilltype("");
					item.setBilltypename("");
					item.setSupplierid("");
					item.setSuppliername("");
					item.setBusinessdate("");
					item.setBuyorderid("");
					item.setId("小计");
				}else if("businessdate".equals(item.getId())){
					item.setAdduserid("");
					item.setAddusername("");
					item.setBilltype("");
					item.setBilltypename("");
					item.setSupplierid("");
					item.setSuppliername("");
					item.setBuydeptid("");
					item.setBuydeptname("");
					item.setBuyorderid("");
					item.setId("小计");
				} 
			}
			if("1".equals(item.getBilltype())){
				item.setBilltypename("采购进货单");
			}else if("2".equals(item.getBilltype())){
				item.setBilltypename("采购退货通知单");					
			}else if("3".equals(item.getBilltype())){
				item.setBilltypename("供应商应付款期初");
			}
		}
		int total=financePaymentsMapper.getSupplierPaymentsBillDetailCount(pageMap);
		PageData pageData=new PageData(total, list, pageMap);
		//取合计数据
		pageMap.getCondition().put("groupcols", "all");
		pageMap.getCondition().remove("showgroupbycol");
		List<SupplierPaymentsBillDetail> footer=null;
		if(list.size()>0){
			footer=financePaymentsMapper.getSupplierPaymentsBillDetailData(pageMap);
		}else{
			footer=new ArrayList<SupplierPaymentsBillDetail>();
		}

		for(SupplierPaymentsBillDetail item : footer){
			if(null!=item){
				item.setAdduserid("");
				item.setAddusername("");
				item.setBilltype("");
				item.setBilltypename("合计");
				item.setBusinessdate("");
				item.setId("");
				item.setSupplierid("");
				item.setSuppliername("");
				item.setBuyorderid("");
			}
		}
		pageData.setFooter(footer);
		return pageData;
	}
	
	@Override
	public PageData showSupplierPaymentsInvoiceReportData(PageMap pageMap) throws Exception{
		String dataSql = getDataAccessRule("t_report_finance_payments", null);
		pageMap.setDataSql(dataSql);
		Map condition=pageMap.getCondition();
		String exportDataFlag="";
		String groupcols = "";
		if(null!=condition){
			groupcols=(String)condition.get("groupcols");
			if(null==groupcols || "".equals(groupcols.trim())){
				condition.remove("groupcols");
			}
			exportDataFlag=(String) condition.get("exportdataflag");
		}
		List<SupplierPaymentsInvoiceReport> list = financePaymentsMapper.getSupplierPaymentsInvoiceReportData(pageMap);
		if(list.size() != 0){
			for(SupplierPaymentsInvoiceReport item : list){				
				if("2".equals(item.getColumnflag())){
					item.setId("小计");
					if("supplierid".equals(groupcols.toLowerCase())){
						item.setBusinessdate(null);
						item.setBuydeptid(null);
					}else if("businessdate".equals(groupcols.toLowerCase())){
						item.setSupplierid(null);
						item.setBuydeptid(null);
					}else if("buydeptid".equals(groupcols.toLowerCase())){
						item.setBusinessdate(null);
						item.setSupplierid(null);
					}
				}
				if(StringUtils.isNotEmpty(item.getSupplierid())){
					BuySupplier buySupplier = getSupplierInfoById(item.getSupplierid());
					if(null != buySupplier ){
						item.setSuppliername(buySupplier.getName());
					}
				}
				if(StringUtils.isNotEmpty(item.getBuydeptid())){
					DepartMent departMent = getDepartmentByDeptid(item.getBuydeptid());
					if(null != departMent ){
						item.setBuydeptname(departMent.getName());
					}
				}
			}
		}
		PageData pageData = new PageData(financePaymentsMapper.getSupplierPaymentsInvoiceReportCount(pageMap),list,pageMap);
		
		//合计
		SupplierPaymentsInvoiceReport supplierPaymentsInvoiceReport = financePaymentsMapper.getSupplierPaymentsInvoiceReportSum(pageMap);
		if(null!=supplierPaymentsInvoiceReport){
			List footer = new ArrayList();
			if(!"true".equals(exportDataFlag)){
				//SupplierPaymentsInvoiceReport selectReport = new SupplierPaymentsInvoiceReport();
				//selectReport.setId("选中金额");
				//footer.add(selectReport);
			}
			
			supplierPaymentsInvoiceReport.setId("合计");
			footer.add(supplierPaymentsInvoiceReport);
			pageData.setFooter(footer);
		}
		return pageData;
	}

    @Override
    public PageData getSupplierPayBillData(PageMap pageMap) throws Exception {
		if(!pageMap.getCondition().containsKey("businessdate1")){
			pageMap.getCondition().put("businessdate1", "1900-01-01");
		}
        String dataSql = getDataAccessRule("t_report_finance_payments", "z");
        pageMap.setDataSql(dataSql);
		pageMap.getCondition().put("isgroups","z.supplierid");
        List<SupplierPayBill> list = financePaymentsMapper.getSupplierPayBillData(pageMap);
        for(SupplierPayBill supplierPayBill : list){
            BuySupplier buySupplier = getSupplierInfoById(supplierPayBill.getSupplierid());
            if(null != buySupplier){
                supplierPayBill.setSuppliername(buySupplier.getName());
            }
            DepartMent departMent = getDepartMentById(supplierPayBill.getBuydeptid());
            if(null != departMent){
                supplierPayBill.setBuydeptname(departMent.getName());
            }
        }
        PageData pageData = new PageData(financePaymentsMapper.getSupplierPayBillCount(pageMap),list,pageMap);
        List<SupplierPayBill> footer = new ArrayList<SupplierPayBill>();
		pageMap.getCondition().put("isgroups","all");
        SupplierPayBill supplierPayBillSum = financePaymentsMapper.getSupplierPayBillSum(pageMap);
        if(null != supplierPayBillSum){
			supplierPayBillSum.setSupplierid("");
            supplierPayBillSum.setSuppliername("合计");
            footer.add(supplierPayBillSum);
        }
        pageData.setFooter(footer);
        return pageData;
    }

    @Override
    public List getSupplierPayBillDetailData(PageMap pageMap) throws Exception {
        String dataSql = getDataAccessRule("t_report_finance_payments", "z");
        pageMap.setDataSql(dataSql);
        List<SupplierPayBill> list = financePaymentsMapper.getSupplierPayBillDetailData(pageMap);
		BigDecimal balanceamount = null;
		BigDecimal initbuyamount = null;
        for(SupplierPayBill supplierPayBill : list){
			if(null == initbuyamount){
				pageMap.getCondition().put("businessdate1",supplierPayBill.getBusinessdate());
				BigDecimal initbuyamount1 = financePaymentsMapper.getSupplierPayBillInitbuyamount(pageMap);
				if(null != initbuyamount1){
					initbuyamount = initbuyamount1;
				}else{
					initbuyamount = BigDecimal.ZERO;
				}
			}
			supplierPayBill.setInitbuyamount(initbuyamount);
			balanceamount = supplierPayBill.getPayableamount().subtract(supplierPayBill.getPayamount()).add(supplierPayBill.getPushbalanceamount()).add(initbuyamount);
			supplierPayBill.setBalanceamount(balanceamount);
			initbuyamount = balanceamount;

            if("1".equals(supplierPayBill.getBilltype())){
				supplierPayBill.setBilltypename("采购进货单");
            }else if("2".equals(supplierPayBill.getBilltype())){
                supplierPayBill.setBilltypename("采购退货出库单");
            }else if("3".equals(supplierPayBill.getBilltype())){
                supplierPayBill.setBilltypename("付款单");
            }else if("4".equals(supplierPayBill.getBilltype())){
                supplierPayBill.setBilltypename("冲差单");
            }else if("5".equals(supplierPayBill.getBilltype())){
				supplierPayBill.setBilltypename("供应商应付款期初");
			}
        }
        return list;
    }

	@Override
	public PageData getReceiptAmountReportData(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_report_salesflow_base", "z");
		pageMap.setDataSql(dataSql);

		if(!pageMap.getCondition().containsKey("businessdate1")){
			pageMap.getCondition().put("businessdate1", "1900-01-01");
		}

		List<Map> list = financePaymentsMapper.getReceiptAmountReportList(pageMap);
		for(Map map : list){
			String customerid = (String)map.get("customerid");
			String salesareaid = (String)map.get("salesarea");
			Customer customer = getCustomerByID(customerid);
			if (null != customer){
				map.put("customername",customer.getName());
			}
			SalesArea salesArea = getSalesareaByID(salesareaid);
			if(null != salesArea){
				map.put("salesareaname",salesArea.getThisname());
			}
		}
		PageData pageData = new PageData(financePaymentsMapper.getReceiptAmountReportCount(pageMap),list,pageMap);
		List<Map> footer = new ArrayList<Map>();
		Map map2 = financePaymentsMapper.getReceiptAmountReportSum(pageMap);
		if(null != map2){
			map2.put("customerid","");
			map2.put("customername","合计");
			footer.add(map2);
		}
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public PageData getReceiptAmountDetailData(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_report_salesflow_base", "z");
		pageMap.setDataSql(dataSql);
		List<Map> list = financePaymentsMapper.getReceiptAmountDetailList(pageMap);
		BigDecimal balanceamount = null;
		BigDecimal initunwithdrawnamount = null;
		for(Map map : list){
			String billtype = (String)map.get("billtype");
			BigDecimal salesamount = null != map.get("salesamount") ? (BigDecimal)map.get("salesamount") : BigDecimal.ZERO;
			BigDecimal withdrawnamount = null != map.get("withdrawnamount") ? (BigDecimal)map.get("withdrawnamount") : BigDecimal.ZERO;
			String businessdate = (String)map.get("businessdate");

			if(null == initunwithdrawnamount){
				if(!pageMap.getCondition().containsKey("businessdate1")){
					pageMap.getCondition().put("businessdate1", "1900-01-01");
				}else{
					pageMap.getCondition().put("businessdate1",businessdate);
				}
				BigDecimal initunwithdrawnamount1 = financePaymentsMapper.getReceiptAmountInitunwithdrawnamount(pageMap);
				if(null != initunwithdrawnamount1){
					initunwithdrawnamount = initunwithdrawnamount1;
				}else{
					initunwithdrawnamount = BigDecimal.ZERO;
				}
			}
			map.put("initunwithdrawnamount",initunwithdrawnamount);
			balanceamount = salesamount.subtract(withdrawnamount).add(initunwithdrawnamount);
			map.put("balanceamount",balanceamount);
			initunwithdrawnamount = balanceamount;


			if("1".equals(billtype)){
				map.put("billtypename","发货单");
			}else if("2".equals(billtype)){
				map.put("billtypename","退货入库单");
			}else if("3".equals(billtype)){
				map.put("billtypename","冲差单");
			}else if("4".equals(billtype)){
				map.put("billtypename","客户应收款期初");
			}
		}

		int rows = pageMap.getRows();
		int endnum = 0;
		if(!pageMap.getCondition().containsKey("isflag") && pageMap.getCondition().get("isflag") != "true"){
			endnum = pageMap.getStartNum() + rows;
			if(endnum > list.size()){
				endnum = list.size();
			}
		}else{
			endnum = list.size();
		}
		PageData pageData = new PageData(list.size(),list.subList(pageMap.getStartNum(),endnum),pageMap);
		return pageData;
	}

	@Override
	public PageData getSupplierPaymentDetailList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_report_finance_payments", "z");
		pageMap.setDataSql(dataSql);
		List<SupplierPayments> list = financePaymentsMapper.getSupplierPaymentDetailList(pageMap);
		BigDecimal unpayamount = null;
		BigDecimal initunpayamount = null;
		for(SupplierPayments supplierPayments : list){
			if(null == initunpayamount){
				pageMap.getCondition().put("businessdate1",supplierPayments.getBusinessdate());
				BigDecimal initunpayamount1 = financePaymentsMapper.getSupplierPaymentInitunpayamount(pageMap);
				if(null != initunpayamount1){
					initunpayamount = initunpayamount1;
				}else{
					initunpayamount = BigDecimal.ZERO;
				}
			}
			supplierPayments.setInitunpayamount(initunpayamount);
			unpayamount = supplierPayments.getBuyamount().subtract(supplierPayments.getReturnamount()).add(supplierPayments.getPushbalanceamount()).subtract(supplierPayments.getPayamount()).add(initunpayamount);
			supplierPayments.setUnpayamount(unpayamount);
			initunpayamount = unpayamount;

			if("1".equals(supplierPayments.getBilltype())){
				supplierPayments.setBilltypename("采购进货单");
			}else if("2".equals(supplierPayments.getBilltype())){
				supplierPayments.setBilltypename("采购退货出库单");
			}else if("3".equals(supplierPayments.getBilltype())){
				supplierPayments.setBilltypename("采购发票");
			}else if("4".equals(supplierPayments.getBilltype())){
				supplierPayments.setBilltypename("采购发票冲差单");
			}else if("5".equals(supplierPayments.getBilltype())){
				supplierPayments.setBilltypename("供应商应付款期初");
			}
		}

		int rows = pageMap.getRows();
		int endnum = 0;
		if(!pageMap.getCondition().containsKey("isflag") && pageMap.getCondition().get("isflag") != "true"){
			endnum = pageMap.getStartNum() + rows;
			if(endnum > list.size()){
				endnum = list.size();
			}
		}else{
			endnum = list.size();
		}
		PageData pageData = new PageData(list.size(),list.subList(pageMap.getStartNum(),endnum),pageMap);
		return pageData;
	}
}

