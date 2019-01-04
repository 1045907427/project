/**
 * @(#)SalesInvoiceServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 1, 2013 chenwei 创建版本
 */
package com.hd.agent.account.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.*;
import com.hd.agent.account.model.*;
import com.hd.agent.account.service.ISalesInvoiceService;
import com.hd.agent.account.service.ISalesStatementService;
import com.hd.agent.basefiles.dao.FinanceMapper;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.ISalesExtService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.dao.ReceiptMapper;
import com.hd.agent.sales.model.Receipt;
import com.hd.agent.sales.model.ReceiptDetail;
import com.hd.agent.sales.model.RejectBill;
import com.hd.agent.sales.model.RejectBillDetail;
import com.hd.agent.sales.service.ISalesOutService;
import com.hd.agent.storage.model.SaleRejectEnter;
import com.hd.agent.storage.service.IStorageSaleService;
import com.hd.agent.system.model.SysParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * 
 * 销售发票接口实现类
 * @author chenwei
 */
public class SalesInvoiceServiceImpl extends BaseAccountServiceImpl implements ISalesInvoiceService{
	
	private SalesInvoiceMapper salesInvoiceMapper;

    private SalesInvoiceBillMapper salesInvoiceBillMapper;

    private CustomerPushBalanceMapper customerPushBalanceMapper;
	
	private ISalesStatementService salesStatementService;

	private BeginAmountMapper beginAmountMapper;
    /**
	 * 对账单dao
	 */
	private SalesStatementMapper salesStatementMapper;
	/**
	 * 客户余额dao
	 */
	private CustomerCapitalMapper customerCapitalMapper;

    private ReceiptMapper receiptMapper;
	
	private ISalesOutService salesOutService;
	
	private IStorageSaleService storageSaleService;
	
	private ISalesExtService salesExtService;

	private FinanceMapper financeMapper;

    public ReceiptMapper getReceiptMapper() {
        return receiptMapper;
    }

    public void setReceiptMapper(ReceiptMapper receiptMapper) {
        this.receiptMapper = receiptMapper;
    }

    public SalesInvoiceBillMapper getSalesInvoiceBillMapper() {
        return salesInvoiceBillMapper;
    }

    public void setSalesInvoiceBillMapper(SalesInvoiceBillMapper salesInvoiceBillMapper) {
        this.salesInvoiceBillMapper = salesInvoiceBillMapper;
    }

    public IStorageSaleService getStorageSaleService() {
		return storageSaleService;
	}

	public void setStorageSaleService(IStorageSaleService storageSaleService) {
		this.storageSaleService = storageSaleService;
	}

	public SalesInvoiceMapper getSalesInvoiceMapper() {
		return salesInvoiceMapper;
	}

	public void setSalesInvoiceMapper(SalesInvoiceMapper salesInvoiceMapper) {
		this.salesInvoiceMapper = salesInvoiceMapper;
	}
	
	public ISalesOutService getSalesOutService() {
		return salesOutService;
	}

	public void setSalesOutService(ISalesOutService salesOutService) {
		this.salesOutService = salesOutService;
	}
	
	public ISalesExtService getSalesExtService() {
		return salesExtService;
	}

	public void setSalesExtService(ISalesExtService salesExtService) {
		this.salesExtService = salesExtService;
	}
	
	public CustomerPushBalanceMapper getCustomerPushBalanceMapper() {
		return customerPushBalanceMapper;
	}

	public void setCustomerPushBalanceMapper(
			CustomerPushBalanceMapper customerPushBalanceMapper) {
		this.customerPushBalanceMapper = customerPushBalanceMapper;
	}
	
	public SalesStatementMapper getSalesStatementMapper() {
		return salesStatementMapper;
	}

	public void setSalesStatementMapper(SalesStatementMapper salesStatementMapper) {
		this.salesStatementMapper = salesStatementMapper;
	}
	
	public CustomerCapitalMapper getCustomerCapitalMapper() {
		return customerCapitalMapper;
	}

	public void setCustomerCapitalMapper(CustomerCapitalMapper customerCapitalMapper) {
		this.customerCapitalMapper = customerCapitalMapper;
	}

	public BeginAmountMapper getBeginAmountMapper() {
		return beginAmountMapper;
	}

	public void setBeginAmountMapper(BeginAmountMapper beginAmountMapper) {
		this.beginAmountMapper = beginAmountMapper;
	}

	public FinanceMapper getFinanceMapper() {
		return financeMapper;
	}

	public void setFinanceMapper(FinanceMapper financeMapper) {
		this.financeMapper = financeMapper;
	}

	@Override
	public Map addSalesInvoiceByReceipt(String ids) throws Exception {
		boolean flag = false;
		String salesInvoiceid = "";
		String msg = "";
		if(null==ids || "".equals(ids)){
		}else{
			String[] idArr = ids.split(",");
			String customerid = null;
			String saledept = null;
			String saleuser = null;
			String settletype = null;
			String paytype =null;
			boolean addFlag = true;
			
			List<Receipt> receiptList = new ArrayList<Receipt>();
			//验证多个回单是否能组成一张销售发票
			for(String id : idArr){
				Receipt receipt = salesOutService.getReceipt(id);
				//获取总店客户档案信息 没有总店信息则获取自身
				Customer headCutomer = salesExtService.getHeadCustomer(receipt.getCustomerid());
				//判断是否同一个客户
				if(null==customerid || customerid.equals(headCutomer.getId())){
					if(null==customerid){
						customerid = headCutomer.getId();
					}
				}else{
					addFlag = false;
					msg = "不是同一客户(总店)下的销售发货回单，不能生成销售发票";
					break;
				}
				receiptList.add(receipt);
			}
			//生成销售发票
			if(addFlag && receiptList.size()>0){
				SalesInvoice salesInvoice = new SalesInvoice();
				if (isAutoCreate("t_account_sales_invoice")) {
					// 获取自动编号
					String id = getAutoCreateSysNumbderForeign(salesInvoice, "t_account_sales_invoice");
					salesInvoice.setId(id);
				}else{
					salesInvoice.setId("XSFP-"+CommonUtils.getDataNumberSendsWithRand());
				}
				Receipt receipt = receiptList.get(0);
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				salesInvoice.setBusinessdate(dateFormat.format(calendar.getTime()));
				salesInvoice.setStatus("2");
				salesInvoice.setSourcetype("1");
				//是否折扣 0否
				salesInvoice.setIsdiscount("0");
				//发票类型 1增值税
				salesInvoice.setInvoicetype("1");
				//是否核销 0否
				salesInvoice.setIswriteoff("0");
				Customer headCutomer = salesExtService.getHeadCustomer(receipt.getCustomerid());
				salesInvoice.setCustomerid(headCutomer.getId());
				//开票客户
				salesInvoice.setInvoicecustomerid(headCutomer.getId());
				//开票客户名称
				salesInvoice.setInvoicecustomername(headCutomer.getName());
				
				salesInvoice.setHandlerid(headCutomer.getContact());
				salesInvoice.setSalesdept(receipt.getSalesdept());
				salesInvoice.setSalesuser(receipt.getSalesuser());
				salesInvoice.setSettletype(receipt.getSettletype());
				salesInvoice.setPaytype(receipt.getPaytype());
				
				List<SalesInvoiceDetail> salesInvoiceDetailList = new ArrayList<SalesInvoiceDetail>();
				String sourceid = "";
				for(Receipt receiptObject : receiptList){
					if("".equals(sourceid)){
						sourceid = receiptObject.getId();
					}else{
						sourceid += ","+receiptObject.getId();
					}
					List<ReceiptDetail> receiptDetailList = receiptObject.getReceiptDetailList();
					for(ReceiptDetail receiptDetail : receiptDetailList){
						SalesInvoiceDetail salesInvoiceDetail = new SalesInvoiceDetail();
						salesInvoiceDetail.setBillid(salesInvoice.getId());
						salesInvoiceDetail.setSourceid(receiptObject.getId());
						salesInvoiceDetail.setSourcedetailid(receiptDetail.getId());
						salesInvoiceDetail.setCustomerid(receiptObject.getCustomerid());
						salesInvoiceDetail.setGoodsid(receiptDetail.getGoodsid());
						//商品品牌 和品牌业务员信息
						GoodsInfo goodsInfo = getAllGoodsInfoByID(receiptDetail.getGoodsid());
						if(null!=goodsInfo){
							salesInvoiceDetail.setGoodssort(goodsInfo.getDefaultsort());
							salesInvoiceDetail.setBrandid(goodsInfo.getBrand());
							salesInvoiceDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoice.getCustomerid()));
							//厂家业务员
							salesInvoiceDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoice.getCustomerid()));
							salesInvoiceDetail.setSupplierid(goodsInfo.getDefaultsupplier());
						}
						
						//应收日期
						salesInvoiceDetail.setReceivedate(receiptDetail.getDuefromdate());
						
						salesInvoiceDetail.setUnitid(receiptDetail.getUnitid());
						salesInvoiceDetail.setUnitname(receiptDetail.getUnitname());
						salesInvoiceDetail.setUnitnum(receiptDetail.getReceiptnum());
						salesInvoiceDetail.setAuxunitid(receiptDetail.getAuxunitid());
						salesInvoiceDetail.setAuxunitname(receiptDetail.getAuxunitname());
						Map auxMap = countGoodsInfoNumber(receiptDetail.getGoodsid(), receiptDetail.getAuxunitid(), receiptDetail.getReceiptnum());
						salesInvoiceDetail.setAuxnum((BigDecimal) auxMap.get("auxnum"));
						salesInvoiceDetail.setAuxnumdetail( (String) auxMap.get("auxnumdetail"));
						
						salesInvoiceDetail.setTaxprice(receiptDetail.getTaxprice());
						salesInvoiceDetail.setTaxamount(receiptDetail.getReceipttaxamount());
						salesInvoiceDetail.setNotaxprice(receiptDetail.getNotaxprice());
						salesInvoiceDetail.setNotaxamount(receiptDetail.getReceiptnotaxamount());
						salesInvoiceDetail.setTaxtype(receiptDetail.getTaxtype());
						salesInvoiceDetail.setTax(receiptDetail.getReceipttaxamount().subtract(receiptDetail.getReceiptnotaxamount()));
						salesInvoiceDetail.setRemark(receiptDetail.getRemark());
						
						int i = salesInvoiceMapper.addSalesInvoiceDetail(salesInvoiceDetail);
						if(i>0){
							salesInvoiceDetailList.add(salesInvoiceDetail);
						}else{
							throw new RuntimeException("销售发票明细添加失败");
						}
					}
					salesOutService.updateReceiptInvoice("1", null, receiptObject.getId());
					salesOutService.updateReceiptInvoicebill("1", null, receiptObject.getId());
				}
				SysUser sysUser = getSysUser();
				salesInvoice.setAdddeptid(sysUser.getDepartmentid());
				salesInvoice.setAdddeptname(sysUser.getDepartmentname());
				salesInvoice.setAdduserid(sysUser.getUserid());
				salesInvoice.setAddusername(sysUser.getName());
				//含税总金额
				BigDecimal alltaxamount = new BigDecimal(0);
				//未税总金额
				BigDecimal allnotaxamount = new BigDecimal(0);
				//应收总金额
				BigDecimal invoiceamount =  new BigDecimal(0);
				for(SalesInvoiceDetail salesInvoiceDetail : salesInvoiceDetailList){
					alltaxamount = alltaxamount.add(salesInvoiceDetail.getTaxamount());
					allnotaxamount = allnotaxamount.add(salesInvoiceDetail.getNotaxamount());
					invoiceamount = invoiceamount.add(salesInvoiceDetail.getTaxamount());
				}
				salesInvoice.setTaxamount(alltaxamount);
				salesInvoice.setNotaxamount(allnotaxamount);
				salesInvoice.setInvoiceamount(invoiceamount);
				salesInvoice.setSourceid(sourceid);
				int i = salesInvoiceMapper.addSalesInvoice(salesInvoice);
				flag = i>0;
				if(flag){
					salesInvoiceid = salesInvoice.getId();
				}
			}
		}
		
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", salesInvoiceid);
		map.put("msg", msg);
		return map;
	}

	@Override
	public Map addSalesInvoiceByRejectbill(String ids) throws Exception {
		boolean flag = false;
		String salesInvoiceid = "";
		String msg = "";
		if(null==ids || "".equals(ids)){
		}else{
			String[] idArr = ids.split(",");
			String customerid = null;
			String saledept = null;
			String saleuser = null;
			String settletype = null;
			String paytype =null;
			boolean addFlag = true;
			
			List<RejectBill> rejectBillList = new ArrayList<RejectBill>();
			//验证多个回单是否能组成一张销售发票
			for(String id : idArr){
				RejectBill rejectBill = salesOutService.getRejectBill(id);
				//获取总店客户档案信息 没有总店信息则获取自身
				Customer headCutomer = salesExtService.getHeadCustomer(rejectBill.getCustomerid());
				//判断是否同一个客户
				if(null==customerid || customerid.equals(headCutomer.getId())){
					if(null==customerid){
						customerid = headCutomer.getId();
					}
				}else{
					addFlag = false;
					msg = "不是同一客户(总店)下的销售退货通知单，不能生成销售发票";
					break;
				}
//				//判断是否同一个销售部门
//				if(null==saledept || saledept.equals(rejectBill.getSalesdept())){
//					if(null==saledept){
//						saledept = rejectBill.getSalesdept();
//					}
//				}else{
//					addFlag = false;
//					msg = "不是同一销售部门下的销售发货回单，不能生成销售发票";
//					break;
//				}
//				//判断是否同一个客户业务员
//				if(null==saleuser || saleuser.equals(rejectBill.getSalesuser())){
//					if(null==saledept){
//						saleuser = rejectBill.getSalesuser();
//					}
//				}else{
//					addFlag = false;
//					msg = "不是同一客户业务员的销售发货回单，不能生成销售发票";
//					break;
//				}
				rejectBillList.add(rejectBill);
			}
			//判断是否可以生成销售发票
			if(addFlag && rejectBillList.size()>0){
				SalesInvoice salesInvoice = new SalesInvoice();
				if (isAutoCreate("t_account_sales_invoice")) {
					// 获取自动编号
					String id = getAutoCreateSysNumbderForeign(salesInvoice, "t_account_sales_invoice");
					salesInvoice.setId(id);
				}else{
					salesInvoice.setId("XSFP-"+CommonUtils.getDataNumberSendsWithRand());
				}
				RejectBill reject = rejectBillList.get(0);
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				salesInvoice.setBusinessdate(dateFormat.format(calendar.getTime()));
				salesInvoice.setStatus("2");
				salesInvoice.setSourcetype("2");
				//是否折扣 0否
				salesInvoice.setIsdiscount("0");
				//发票类型 1增值税
				salesInvoice.setInvoicetype("1");
				//是否核销 0否
				salesInvoice.setIswriteoff("0");
				
				Customer headCutomer = salesExtService.getHeadCustomer(reject.getCustomerid());
				salesInvoice.setCustomerid(headCutomer.getId());
				salesInvoice.setHandlerid(headCutomer.getContact());
				salesInvoice.setInvoicecustomerid(reject.getCustomerid());
				//开票客户
				salesInvoice.setInvoicecustomerid(headCutomer.getId());
				//开票客户名称
				salesInvoice.setInvoicecustomername(headCutomer.getName());
				
				salesInvoice.setSalesdept(reject.getSalesdept());
				salesInvoice.setSalesuser(reject.getSalesuser());
				salesInvoice.setSettletype(reject.getSettletype());
				salesInvoice.setPaytype(reject.getPaytype());
				
				List<SalesInvoiceDetail> salesInvoiceDetailList = new ArrayList<SalesInvoiceDetail>();
				String sourceid = "";
				for(RejectBill rejectBill : rejectBillList){
					if("".equals(sourceid)){
						sourceid = rejectBill.getId();
					}else{
						sourceid += ","+rejectBill.getId();
					}
					List<RejectBillDetail> rejectBillDetailList = rejectBill.getBillDetailList();
					for(RejectBillDetail rejectBillDetail : rejectBillDetailList){
						SalesInvoiceDetail salesInvoiceDetail = new SalesInvoiceDetail();
						salesInvoiceDetail.setBillid(salesInvoice.getId());
						salesInvoiceDetail.setSourceid(rejectBill.getId());
						salesInvoiceDetail.setSourcedetailid(rejectBillDetail.getId());
						salesInvoiceDetail.setCustomerid(rejectBill.getCustomerid());
						salesInvoiceDetail.setGoodsid(rejectBillDetail.getGoodsid());
						//商品品牌 和品牌业务员信息
						GoodsInfo goodsInfo = getAllGoodsInfoByID(rejectBillDetail.getGoodsid());
						if(null!=goodsInfo){
							salesInvoiceDetail.setGoodssort(goodsInfo.getDefaultsort());
							salesInvoiceDetail.setBrandid(goodsInfo.getBrand());
							salesInvoiceDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoice.getCustomerid()));
							//厂家业务员
							salesInvoiceDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoice.getCustomerid()));
							salesInvoiceDetail.setSupplierid(goodsInfo.getDefaultsupplier());
						}
						salesInvoiceDetail.setUnitid(rejectBillDetail.getUnitid());
						salesInvoiceDetail.setUnitname(rejectBillDetail.getUnitname());
						salesInvoiceDetail.setUnitnum(rejectBillDetail.getInnummain());
						salesInvoiceDetail.setAuxunitid(rejectBillDetail.getAuxunitid());
						salesInvoiceDetail.setAuxunitname(rejectBillDetail.getAuxunitname());
						Map auxMap = countGoodsInfoNumber(rejectBillDetail.getGoodsid(), rejectBillDetail.getAuxunitid(), rejectBillDetail.getInnummain());
						salesInvoiceDetail.setAuxnum((BigDecimal) auxMap.get("auxnum"));
						salesInvoiceDetail.setAuxnumdetail( (String) auxMap.get("auxnumdetail"));
						
						//退货通知单生成的发票 金额为负
						salesInvoiceDetail.setTaxprice(rejectBillDetail.getTaxprice());
						salesInvoiceDetail.setTaxamount(rejectBillDetail.getInamounttax().negate());
						salesInvoiceDetail.setNotaxprice(rejectBillDetail.getNotaxprice());
						salesInvoiceDetail.setNotaxamount(rejectBillDetail.getInamountnotax().negate());
						salesInvoiceDetail.setTaxtype(rejectBillDetail.getTaxtype());
						salesInvoiceDetail.setTax(rejectBillDetail.getInamounttax().subtract(rejectBillDetail.getInamountnotax()).negate());
						salesInvoiceDetail.setRemark(rejectBillDetail.getRemark());
						
						int i = salesInvoiceMapper.addSalesInvoiceDetail(salesInvoiceDetail);
						if(i>0){
							salesInvoiceDetailList.add(salesInvoiceDetail);
						}else{
							throw new RuntimeException("销售发票明细添加失败");
						}
					}
					salesOutService.updateRejectBillInvoice("1", null, rejectBill.getId());
				}
				SysUser sysUser = getSysUser();
				salesInvoice.setAdddeptid(sysUser.getDepartmentid());
				salesInvoice.setAdddeptname(sysUser.getDepartmentname());
				salesInvoice.setAdduserid(sysUser.getUserid());
				salesInvoice.setAddusername(sysUser.getName());
				//含税总金额
				BigDecimal alltaxamount = new BigDecimal(0);
				//未税总金额
				BigDecimal allnotaxamount = new BigDecimal(0);
				//应收总金额
				BigDecimal invoiceamount =  new BigDecimal(0);
				for(SalesInvoiceDetail salesInvoiceDetail : salesInvoiceDetailList){
					alltaxamount = alltaxamount.add(salesInvoiceDetail.getTaxamount());
					allnotaxamount = allnotaxamount.add(salesInvoiceDetail.getNotaxamount());
					invoiceamount = invoiceamount.add(salesInvoiceDetail.getTaxamount());
				}
				salesInvoice.setTaxamount(alltaxamount);
				salesInvoice.setNotaxamount(allnotaxamount);
				salesInvoice.setInvoiceamount(invoiceamount);
				salesInvoice.setSourceid(sourceid);
				int i = salesInvoiceMapper.addSalesInvoice(salesInvoice);
				flag = i>0;
				if(flag){
					salesInvoiceid = salesInvoice.getId();
				}
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", salesInvoiceid);
		return map;
	}
	
	@Override
	public Map addSalesInvoiceByReceiptAndRejectbill(String ids,String customerid,String iswriteoff)
			throws Exception {
		boolean flag = false;
		String salesInvoiceid = "";
		String msg = "";
		if(null==ids || "".equals(ids)){
		}else{
			JSONArray idArr = JSONArray.fromObject(ids);
			String saledept = null;
			String saleuser = null;
			String settletype = null;
			String paytype =null;
			boolean addFlag = true;
			String sourceids = null;
			//用来存放来源单据编号
			Map sourceidMap = new HashMap();
			//门店客户
			Map childcustomerMap =  new HashMap();
			//单据编号集合
			Map billMap = new HashMap();
			//验证多个回单是否能组成一张销售发票
			for(int i=0;i<idArr.size();i++){
				JSONObject detailObject = (JSONObject) idArr.get(i);
				//单据类型 1回单2销售退货通知单
				String billtype = detailObject.getString("billtype");
				//单据编号
				String id = detailObject.getString("billid");
				//明细编号
				String detailid = detailObject.getString("detailid");
				//是否折扣
				String isdiscount = "0";
				if(detailObject.containsKey("isdiscount")){
					isdiscount = detailObject.getString("isdiscount");
				}
				if(!sourceidMap.containsKey(id)){
					//来源单据编号
					if(sourceids==null){
						sourceids = id;
					}else{
						sourceids += ","+id;
					}
					sourceidMap.put(id, billtype);
				}
				
				if("1".equals(billtype)){
					if(!billMap.containsKey(id)){
						Receipt receipt = salesOutService.getReceipt(id);
						childcustomerMap.put(receipt.getCustomerid(), receipt.getCustomerid());
						//获取总店客户档案信息 没有总店信息则获取自身
						Customer headCutomer = salesExtService.getHeadCustomer(receipt.getCustomerid());
						if(null==customerid){
							customerid = headCutomer.getId();
						}
						//判断是否同一个客户
						if(customerid.equals(receipt.getCustomerid()) || customerid.equals(headCutomer.getId())){
						}else{
							addFlag = false;
							msg = "不是同一客户(总店)下的销售发货回单，不能生成销售发票";
							break;
						}
						billMap.put(id, receipt);
					}
				}else if("2".equals(billtype)){
					if(!billMap.containsKey(id)){
						RejectBill rejectBill = salesOutService.getRejectBill(id);
						childcustomerMap.put(rejectBill.getCustomerid(), rejectBill.getCustomerid());
						//获取总店客户档案信息 没有总店信息则获取自身
						Customer headCutomer = salesExtService.getHeadCustomer(rejectBill.getCustomerid());
						if(null==customerid){
							customerid = headCutomer.getId();
						}
						//判断是否同一个客户
						if(customerid.equals(rejectBill.getCustomerid()) || customerid.equals(headCutomer.getId())){
							if(null==customerid){
								customerid = headCutomer.getId();
							}
						}else{
							addFlag = false;
							msg = "不是同一客户(总店)下的销售退货通知单，不能生成销售发票";
							break;
						}
						billMap.put(id, rejectBill);
					}
				}else if("3".equals(billtype)){
					if(!billMap.containsKey(id)){
						CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(id);
						childcustomerMap.put(customerPushBalance.getCustomerid(), customerPushBalance.getCustomerid());
						//获取总店客户档案信息 没有总店信息则获取自身
						Customer headCutomer = salesExtService.getHeadCustomer(customerPushBalance.getCustomerid());
						if(null==customerid){
							customerid = headCutomer.getId();
						}
						//判断是否同一个客户
						if(customerid.equals(customerPushBalance.getCustomerid()) || customerid.equals(headCutomer.getId())){
							if(null==customerid){
								customerid = headCutomer.getId();
							}
						}else{
							addFlag = false;
							msg = "不是同一客户(总店)下的冲差单，不能生成销售发票";
							break;
						}
						billMap.put(id, customerPushBalance);
					}
				}else if("4".equals(billtype)){
					if(!billMap.containsKey(id)){
						BeginAmount beginAmount = beginAmountMapper.getBeginAmountByID(id);
						childcustomerMap.put(beginAmount.getCustomerid(), beginAmount.getCustomerid());
						//获取总店客户档案信息 没有总店信息则获取自身
						Customer headCutomer = salesExtService.getHeadCustomer(beginAmount.getCustomerid());
						if(null==customerid){
							customerid = headCutomer.getId();
						}
						//判断是否同一个客户
						if(customerid.equals(beginAmount.getCustomerid()) || customerid.equals(headCutomer.getId())){
							if(null==customerid){
								customerid = headCutomer.getId();
							}
						}else{
							addFlag = false;
							msg = "不是同一客户(总店)下的应收款期初，不能生成销售发票";
							break;
						}
						billMap.put(id, beginAmount);
					}
				}
			}
			//判断是否可以生成销售发票
			if(addFlag){
				SalesInvoice salesInvoice = new SalesInvoice();
				if (isAutoCreate("t_account_sales_invoice")) {
					// 获取自动编号
					String id = getAutoCreateSysNumbderForeign(salesInvoice, "t_account_sales_invoice");
					salesInvoice.setId(id);
				}else{
					salesInvoice.setId("XSFP-"+CommonUtils.getDataNumberSendsWithRand());
				}
				salesInvoice.setBusinessdate(getAuditBusinessdate(salesInvoice.getBusinessdate()));
				//判断是否申请核销1是0否
				if("0".equals(iswriteoff)){
					salesInvoice.setStatus("2");
				}else if("1".equals(iswriteoff)){
					salesInvoice.setStatus("3");
				}
				salesInvoice.setSourcetype("2");
				//是否折扣 0否
				salesInvoice.setIsdiscount("0");
				//发票类型 1增值税
				//salesInvoice.setInvoicetype("1");
				//是否核销 0否
				salesInvoice.setIswriteoff("0");
				
				Customer customer = getCustomerByID(customerid);
				salesInvoice.setCustomerid(customerid);
				if(null!=customer){
					salesInvoice.setHandlerid(customer.getContact());
					salesInvoice.setInvoicecustomerid(customerid);
					//开票类型： 若为申请核销，则开票类型为核销状态，否则取客户档案核销方式
					if("0".equals(iswriteoff)){
                        //使用于普通版
                        salesInvoice.setApplytype("2");
						//salesInvoice.setApplytype(customer.getCanceltype());
					}else if("1".equals(iswriteoff)){
						salesInvoice.setApplytype("2");
					}
					salesInvoice.setSalesdept(customer.getSalesdeptid());
					salesInvoice.setSalesuser(customer.getSalesuserid());
					salesInvoice.setSettletype(customer.getSettletype());
					salesInvoice.setPaytype(customer.getPaytype());
					salesInvoice.setIndooruserid(customer.getIndoorstaff());
					//总店
					if("0".equals(customer.getIslast())){
						salesInvoice.setPcustomerid(customer.getId());
					}else{
						salesInvoice.setPcustomerid(customer.getPid());
					}
				}
				String customerids = null;
				Set set = childcustomerMap.entrySet();
				Iterator it = set.iterator();
				while (it.hasNext()) {
					Map.Entry<String, String> entry = (Entry<String, String>) it.next();
					if(null==customerids){
						customerids = entry.getKey();
					}else{
						if(customerids.indexOf(entry.getKey()) == -1){
							customerids += ","+entry.getKey();
						}
					}
				}
				salesInvoice.setChlidcustomerid(customerids);
				//开票客户
				salesInvoice.setInvoicecustomerid(customer.getId());
				//获取该客户的上次开票客户名称
				String invoicecustomername = salesInvoiceMapper.getSalesInvoiceNameByCustomerid(customer.getId());
				if(null!=invoicecustomername){
					//开票客户名称
					salesInvoice.setInvoicecustomername(invoicecustomername);
				}else{
					//开票客户名称
					salesInvoice.setInvoicecustomername(customer.getName());
				}
				
				List<SalesInvoiceDetail> salesInvoiceDetailList = new ArrayList<SalesInvoiceDetail>();
				
				Map billObjectMap = new HashMap();
				for(int i=0;i<idArr.size();i++){
					JSONObject detailObject = (JSONObject) idArr.get(i);
					//单据类型 1回单2销售退货通知单
					String billtype = detailObject.getString("billtype");
					//单据编号
					String id = detailObject.getString("billid");
					//明细编号
					String detailid = detailObject.getString("detailid");
					SalesInvoiceDetail salesInvoiceDetail = null;
					
					if("1".equals(billtype)){
						Receipt receipt = null;
						if(billObjectMap.containsKey(id)){
							receipt = (Receipt) billObjectMap.get(id);
						}else{
							receipt = salesOutService.getReceipt(id);
							billObjectMap.put(id, receipt);
						}
						if(null!=receipt){
							ReceiptDetail receiptDetail = salesOutService.getReceiptDetailInfo(detailid,id);
							if(null!=receiptDetail){
								if("1".equals(receiptDetail.getIsdiscount()) && "1".equals(receiptDetail.getIsbranddiscount())){
									List<ReceiptDetail> list = salesOutService.getReceiptDetailBrandDiscountList(receiptDetail.getBillid(), receiptDetail.getBrandid());
									for(ReceiptDetail receiptDetail2 : list){
										salesInvoiceDetail = new SalesInvoiceDetail();
										salesInvoiceDetail.setBillid(salesInvoice.getId());
										salesInvoiceDetail.setIsdiscount(receiptDetail2.getIsdiscount());
										salesInvoiceDetail.setIsbranddiscount(receiptDetail2.getIsbranddiscount());
										//来源销售发货回单
										salesInvoiceDetail.setSourcetype("1");
										salesInvoiceDetail.setSourceid(id);
										salesInvoiceDetail.setSourcedetailid(receiptDetail2.getId());
										salesInvoiceDetail.setCustomerid(receipt.getCustomerid()!=null?receipt.getCustomerid():"");
										salesInvoiceDetail.setPcustomerid(receipt.getPcustomerid()!=null?receipt.getPcustomerid():"");
										salesInvoiceDetail.setCustomersort(receipt.getCustomersort()!=null?receipt.getCustomersort():"");
										salesInvoiceDetail.setSalesarea(receipt.getSalesarea()!=null?receipt.getSalesarea():"");
										salesInvoiceDetail.setSalesdept(receipt.getSalesdept()!=null?receipt.getSalesdept():"");
										salesInvoiceDetail.setSalesuser(receipt.getSalesuser()!=null?receipt.getSalesuser():"");
										salesInvoiceDetail.setGoodsid(receiptDetail2.getGoodsid()!=null?receiptDetail2.getGoodsid():"");
										salesInvoiceDetail.setBranddept(receiptDetail2.getBranddept()!=null?receiptDetail2.getBranddept():"");
										salesInvoiceDetail.setCostprice(receiptDetail2.getCostprice());
										//商品品牌 和品牌业务员信息
										GoodsInfo goodsInfo = getAllGoodsInfoByID(receiptDetail2.getGoodsid());
										if(null!=goodsInfo){
											salesInvoiceDetail.setGoodssort(goodsInfo.getDefaultsort());
											salesInvoiceDetail.setBrandid(goodsInfo.getBrand());
											salesInvoiceDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceDetail.getCustomerid()));
											//厂家业务员
											salesInvoiceDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoice.getCustomerid()));
											//商品默认供应商
											salesInvoiceDetail.setSupplierid(goodsInfo.getDefaultsupplier());
										}
										salesInvoiceDetail.setUnitid(receiptDetail2.getUnitid());
										salesInvoiceDetail.setUnitname(receiptDetail2.getUnitname());
										salesInvoiceDetail.setUnitnum(receiptDetail2.getReceiptnum());
										salesInvoiceDetail.setAuxunitid(receiptDetail2.getAuxunitid());
										salesInvoiceDetail.setAuxunitname(receiptDetail2.getAuxunitname());
										Map auxMap = countGoodsInfoNumber(receiptDetail2.getGoodsid(), receiptDetail2.getAuxunitid(), receiptDetail2.getReceiptnum());
										salesInvoiceDetail.setAuxnum((BigDecimal) auxMap.get("auxnum"));
										salesInvoiceDetail.setAuxnumdetail( (String) auxMap.get("auxnumdetail"));
										
										//退货通知单生成的发票 金额为负
										salesInvoiceDetail.setTaxprice(receiptDetail2.getTaxprice());
										BigDecimal taxamount=receiptDetail2.getReceipttaxamount();
										salesInvoiceDetail.setTaxamount(taxamount);
										salesInvoiceDetail.setTaxtype(receiptDetail2.getTaxtype());
										//重新计算税额税额
										BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, salesInvoiceDetail.getTaxtype());
										salesInvoiceDetail.setNotaxamount(notaxamount);
										//未税单价 = 未税金额/数量
										//未税单价 = 未税金额/数量
										if(null!=notaxamount && notaxamount.compareTo(BigDecimal.ZERO)!=0 
												&& null!=salesInvoiceDetail.getUnitnum() && salesInvoiceDetail.getUnitnum().compareTo(BigDecimal.ZERO)!=0){
											salesInvoiceDetail.setNotaxprice(notaxamount.divide(salesInvoiceDetail.getUnitnum(), 6,BigDecimal.ROUND_HALF_UP));
										}else{
											salesInvoiceDetail.setNotaxprice(receiptDetail2.getNotaxprice());
										}
										salesInvoiceDetail.setTax(salesInvoiceDetail.getTaxamount().subtract(salesInvoiceDetail.getNotaxamount()));
										salesInvoiceDetail.setRemark(receiptDetail2.getRemark());
										salesInvoiceDetail.setSeq(receiptDetail2.getSeq());
										salesInvoiceDetailList.add(salesInvoiceDetail);
									}
								}else{
									salesInvoiceDetail = new SalesInvoiceDetail();
									salesInvoiceDetail.setBillid(salesInvoice.getId());
									//来源销售发货回单
									salesInvoiceDetail.setSourcetype("1");
									salesInvoiceDetail.setSourceid(id);
									salesInvoiceDetail.setSourcedetailid(detailid);
									salesInvoiceDetail.setIsdiscount(receiptDetail.getIsdiscount());
									salesInvoiceDetail.setCustomerid(receipt.getCustomerid()!=null?receipt.getCustomerid():"");
									salesInvoiceDetail.setPcustomerid(receipt.getPcustomerid()!=null?receipt.getPcustomerid():"");
									salesInvoiceDetail.setCustomersort(receipt.getCustomersort()!=null?receipt.getCustomersort():"");
									salesInvoiceDetail.setSalesarea(receipt.getSalesarea()!=null?receipt.getSalesarea():"");
									salesInvoiceDetail.setSalesdept(receipt.getSalesdept()!=null?receipt.getSalesdept():"");
									salesInvoiceDetail.setSalesuser(receipt.getSalesuser()!=null?receipt.getSalesuser():"");
									salesInvoiceDetail.setGoodsid(receiptDetail.getGoodsid()!=null?receiptDetail.getGoodsid():"");
									salesInvoiceDetail.setBranddept(receiptDetail.getBranddept()!=null?receiptDetail.getBranddept():"");
									salesInvoiceDetail.setCostprice(receiptDetail.getCostprice());
									//商品品牌 和品牌业务员信息
									GoodsInfo goodsInfo = getAllGoodsInfoByID(receiptDetail.getGoodsid());
									if(null!=goodsInfo){
										salesInvoiceDetail.setGoodssort(goodsInfo.getDefaultsort());
										salesInvoiceDetail.setBrandid(goodsInfo.getBrand());
										salesInvoiceDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceDetail.getCustomerid()));
										//厂家业务员
										salesInvoiceDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoice.getCustomerid()));
										//商品默认供应商
										salesInvoiceDetail.setSupplierid(goodsInfo.getDefaultsupplier());
									}
									salesInvoiceDetail.setUnitid(receiptDetail.getUnitid());
									salesInvoiceDetail.setUnitname(receiptDetail.getUnitname());
									salesInvoiceDetail.setUnitnum(receiptDetail.getReceiptnum());
									salesInvoiceDetail.setAuxunitid(receiptDetail.getAuxunitid());
									salesInvoiceDetail.setAuxunitname(receiptDetail.getAuxunitname());
									Map auxMap = countGoodsInfoNumber(receiptDetail.getGoodsid(), receiptDetail.getAuxunitid(), receiptDetail.getReceiptnum());
									salesInvoiceDetail.setAuxnum((BigDecimal) auxMap.get("auxnum"));
									salesInvoiceDetail.setAuxnumdetail( (String) auxMap.get("auxnumdetail"));
									
									//退货通知单生成的发票 金额为负
									salesInvoiceDetail.setTaxprice(receiptDetail.getTaxprice());
									BigDecimal taxamount=receiptDetail.getReceipttaxamount();
									salesInvoiceDetail.setTaxamount(taxamount);
									salesInvoiceDetail.setTaxtype(receiptDetail.getTaxtype());
									//重新计算税额税额
									BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, salesInvoiceDetail.getTaxtype());
									salesInvoiceDetail.setNotaxamount(notaxamount);
									//未税单价 = 未税金额/数量
									if(null!=notaxamount && notaxamount.compareTo(BigDecimal.ZERO)!=0 
											&& null!=salesInvoiceDetail.getUnitnum() && salesInvoiceDetail.getUnitnum().compareTo(BigDecimal.ZERO)!=0){
										salesInvoiceDetail.setNotaxprice(notaxamount.divide(salesInvoiceDetail.getUnitnum(), 6,BigDecimal.ROUND_HALF_UP));
									}else{
										salesInvoiceDetail.setNotaxprice(receiptDetail.getNotaxprice());
									}
									salesInvoiceDetail.setTax(salesInvoiceDetail.getTaxamount().subtract(salesInvoiceDetail.getNotaxamount()));
									salesInvoiceDetail.setRemark(receiptDetail.getRemark());
									salesInvoiceDetail.setSeq(receiptDetail.getSeq());
									salesInvoiceDetailList.add(salesInvoiceDetail);
								}
							}
						}
					}else if("2".equals(billtype)){
						RejectBill rejectBill = null;
						if(billObjectMap.containsKey(id)){
							rejectBill = (RejectBill) billObjectMap.get(id);
						}else{
							rejectBill = salesOutService.getRejectBill(id);
							billObjectMap.put(id, rejectBill);
						}
						if(null!=rejectBill){
							RejectBillDetail rejectBillDetail = salesOutService.getRejectBillDetailInfo(detailid, id);
							if(null!=rejectBillDetail){
								salesInvoiceDetail = new SalesInvoiceDetail();
								salesInvoiceDetail.setBillid(salesInvoice.getId());
								//来源销售退货通知单
								salesInvoiceDetail.setSourcetype("2");
								salesInvoiceDetail.setSourceid(rejectBill.getId());
								salesInvoiceDetail.setSourcedetailid(rejectBillDetail.getId());
								salesInvoiceDetail.setCustomerid(rejectBill.getCustomerid()!=null?rejectBill.getCustomerid():"");
								salesInvoiceDetail.setPcustomerid(rejectBill.getPcustomerid()!=null?rejectBill.getPcustomerid():"");
								salesInvoiceDetail.setCustomersort(rejectBill.getCustomersort()!=null?rejectBill.getCustomersort():"");
								salesInvoiceDetail.setSalesarea(rejectBill.getSalesarea()!=null?rejectBill.getSalesarea():"");
								salesInvoiceDetail.setSalesdept(rejectBill.getSalesdept()!=null?rejectBill.getSalesdept():"");
								salesInvoiceDetail.setSalesuser(rejectBill.getSalesuser()!=null?rejectBill.getSalesuser():"");
								salesInvoiceDetail.setGoodsid(rejectBillDetail.getGoodsid()!=null?rejectBillDetail.getGoodsid():"");
								salesInvoiceDetail.setBranddept(rejectBillDetail.getBranddept()!=null?rejectBillDetail.getBranddept():"");
								salesInvoiceDetail.setCostprice(rejectBillDetail.getCostprice());
								//商品品牌 和品牌业务员信息
								GoodsInfo goodsInfo = getAllGoodsInfoByID(rejectBillDetail.getGoodsid());
								if(null!=goodsInfo){
									salesInvoiceDetail.setGoodssort(goodsInfo.getDefaultsort());
									salesInvoiceDetail.setBrandid(goodsInfo.getBrand());
									salesInvoiceDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceDetail.getCustomerid()));
									//厂家业务员
									salesInvoiceDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoice.getCustomerid()));
									//商品默认供应商
									salesInvoiceDetail.setSupplierid(goodsInfo.getDefaultsupplier());
								}
								salesInvoiceDetail.setUnitid(rejectBillDetail.getUnitid());
								salesInvoiceDetail.setUnitname(rejectBillDetail.getUnitname());
								salesInvoiceDetail.setUnitnum(rejectBillDetail.getUnitnum().negate());
								salesInvoiceDetail.setAuxunitid(rejectBillDetail.getAuxunitid());
								salesInvoiceDetail.setAuxunitname(rejectBillDetail.getAuxunitname());
								Map auxMap = countGoodsInfoNumber(rejectBillDetail.getGoodsid(), rejectBillDetail.getAuxunitid(), rejectBillDetail.getUnitnum().negate());
								salesInvoiceDetail.setAuxnum((BigDecimal) auxMap.get("auxnum"));
								salesInvoiceDetail.setAuxnumdetail((String) auxMap.get("auxnumdetail"));
								
								//退货通知单生成的发票 金额为负
								salesInvoiceDetail.setTaxprice(rejectBillDetail.getTaxprice());
								BigDecimal taxamount=rejectBillDetail.getTaxamount().negate();
								salesInvoiceDetail.setTaxamount(taxamount);
								salesInvoiceDetail.setTaxtype(rejectBillDetail.getTaxtype());
								//重新计算税额税额
								BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, salesInvoiceDetail.getTaxtype());
								salesInvoiceDetail.setNotaxamount(notaxamount);
								//未税单价 = 未税金额/数量
								if(null!=notaxamount && notaxamount.compareTo(BigDecimal.ZERO)!=0 
										&& null!=salesInvoiceDetail.getUnitnum() && salesInvoiceDetail.getUnitnum().compareTo(BigDecimal.ZERO)!=0){
									salesInvoiceDetail.setNotaxprice(notaxamount.divide(salesInvoiceDetail.getUnitnum(), 6,BigDecimal.ROUND_HALF_UP));
								}else{
									salesInvoiceDetail.setNotaxprice(rejectBillDetail.getNotaxprice());
								}
								salesInvoiceDetail.setTax(salesInvoiceDetail.getTaxamount().subtract(salesInvoiceDetail.getNotaxamount()));
								salesInvoiceDetail.setRemark(rejectBillDetail.getRemark());
								salesInvoiceDetail.setSeq(rejectBillDetail.getSeq());
								salesInvoiceDetailList.add(salesInvoiceDetail);
							}
						}
					}else if("3".equals(billtype)){
						CustomerPushBalance customerPushBalance = null;
						if(billObjectMap.containsKey(id)){
							customerPushBalance = (CustomerPushBalance) billObjectMap.get(id);
						}else{
							customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(id);
							billObjectMap.put(id, customerPushBalance);
						}
						if(null!=customerPushBalance){
							salesInvoiceDetail = new SalesInvoiceDetail();
							salesInvoiceDetail.setBillid(salesInvoice.getId());
							//来源销售退货通知单
							salesInvoiceDetail.setSourcetype("3");
							salesInvoiceDetail.setSourceid(customerPushBalance.getId());
							salesInvoiceDetail.setSourcedetailid(customerPushBalance.getId());
							salesInvoiceDetail.setCustomerid(customerPushBalance.getCustomerid());
							salesInvoiceDetail.setPcustomerid(customerPushBalance.getPcustomerid()!=null?customerPushBalance.getPcustomerid():"");
							salesInvoiceDetail.setCustomersort(customerPushBalance.getCustomersort()!=null?customerPushBalance.getCustomersort():"");
							salesInvoiceDetail.setSalesarea(customerPushBalance.getSalesarea()!=null?customerPushBalance.getSalesarea():"");
							salesInvoiceDetail.setSalesdept(customerPushBalance.getSalesdept()!=null?customerPushBalance.getSalesdept():"");
							salesInvoiceDetail.setSalesuser(customerPushBalance.getSalesuser()!=null?customerPushBalance.getSalesuser():"");
							salesInvoiceDetail.setGoodsid(customerPushBalance.getBrandid()!=null?customerPushBalance.getBrandid():"");
							salesInvoiceDetail.setBranddept(customerPushBalance.getBranddept()!=null?customerPushBalance.getBranddept():"");
							
							salesInvoiceDetail.setBrandid(customerPushBalance.getBrandid());
							salesInvoiceDetail.setBranduser(getBrandUseridByCustomeridAndBrand(customerPushBalance.getBrandid(), salesInvoiceDetail.getCustomerid()));
							//厂家业务员
							salesInvoiceDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(customerPushBalance.getBrandid(), salesInvoiceDetail.getCustomerid()));
							Brand brand = getGoodsBrandByID(customerPushBalance.getBrandid());
							if(null!=brand){
								//商品默认供应商
								salesInvoiceDetail.setSupplierid(brand.getSupplierid());
							}
							salesInvoiceDetail.setUnitid("");
							salesInvoiceDetail.setUnitname("");
							salesInvoiceDetail.setUnitnum(BigDecimal.ZERO);
							salesInvoiceDetail.setAuxunitid("");
							salesInvoiceDetail.setAuxunitname("");
							salesInvoiceDetail.setAuxnum(BigDecimal.ZERO);
							salesInvoiceDetail.setAuxnumdetail("");
							
							//退货通知单生成的发票 金额为负
							salesInvoiceDetail.setTaxprice(BigDecimal.ZERO);
							BigDecimal taxamount=customerPushBalance.getAmount();
							salesInvoiceDetail.setTaxamount(taxamount);
							salesInvoiceDetail.setNotaxprice(BigDecimal.ZERO);
							salesInvoiceDetail.setTaxtype(customerPushBalance.getDefaulttaxtype());
							//重新计算税额税额
							BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, salesInvoiceDetail.getTaxtype());
							salesInvoiceDetail.setNotaxamount(notaxamount);
							salesInvoiceDetail.setTax(salesInvoiceDetail.getTaxamount().subtract(salesInvoiceDetail.getNotaxamount()));
							salesInvoiceDetail.setRemark(customerPushBalance.getRemark());
							salesInvoiceDetail.setSeq(999);
							salesInvoiceDetailList.add(salesInvoiceDetail);
						}
					}else if("4".equals(billtype)){
						BeginAmount beginAmount = null;
						if(billObjectMap.containsKey(id)){
							beginAmount = (BeginAmount) billObjectMap.get(id);
						}else{
							beginAmount = beginAmountMapper.getBeginAmountByID(id);
							billObjectMap.put(id, beginAmount);
						}
						if(null!=beginAmount){
							salesInvoiceDetail = new SalesInvoiceDetail();
							salesInvoiceDetail.setBillid(salesInvoice.getId());
							//来源销售退货通知单
							salesInvoiceDetail.setSourcetype("4");
							salesInvoiceDetail.setSourceid(beginAmount.getId());
							salesInvoiceDetail.setSourcedetailid(beginAmount.getId());
							salesInvoiceDetail.setCustomerid(beginAmount.getCustomerid());
							salesInvoiceDetail.setPcustomerid(beginAmount.getPcustomerid()!=null?beginAmount.getPcustomerid():"");
							salesInvoiceDetail.setCustomersort(beginAmount.getCustomersort()!=null?beginAmount.getCustomersort():"");
							salesInvoiceDetail.setSalesarea(beginAmount.getSalesarea()!=null?beginAmount.getSalesarea():"");
							salesInvoiceDetail.setSalesdept(beginAmount.getSalesdept()!=null?beginAmount.getSalesdept():"");
							salesInvoiceDetail.setSalesuser(beginAmount.getSalesuser()!=null?beginAmount.getSalesuser():"");
							salesInvoiceDetail.setGoodsid("QC");
							salesInvoiceDetail.setBranddept("");
							
							salesInvoiceDetail.setBrandid("QC");
							salesInvoiceDetail.setBranduser("QC");
							salesInvoiceDetail.setSupplieruser("QC");
							//商品默认供应商
							salesInvoiceDetail.setSupplierid("QC");
							salesInvoiceDetail.setUnitid("");
							salesInvoiceDetail.setUnitname("");
							salesInvoiceDetail.setUnitnum(BigDecimal.ZERO);
							salesInvoiceDetail.setAuxunitid("");
							salesInvoiceDetail.setAuxunitname("");
							salesInvoiceDetail.setAuxnum(BigDecimal.ZERO);
							salesInvoiceDetail.setAuxnumdetail("");
							
							//退货通知单生成的发票 金额为负
							salesInvoiceDetail.setTaxprice(BigDecimal.ZERO);
							BigDecimal taxamount=beginAmount.getAmount();
							salesInvoiceDetail.setTaxamount(taxamount);
							salesInvoiceDetail.setNotaxprice(BigDecimal.ZERO);
							//取系统默认税种
							String taxtype = "";
							SysParam taxtypeParam = getBaseSysParamMapper().getSysParam("DEFAULTAXTYPE");
							if(null != taxtypeParam){
								taxtype = taxtypeParam.getPvalue();
							}
							salesInvoiceDetail.setTaxtype(taxtype);
							//重新计算税额税额
							BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, salesInvoiceDetail.getTaxtype());
							salesInvoiceDetail.setNotaxamount(notaxamount);
							salesInvoiceDetail.setTax(salesInvoiceDetail.getTaxamount().subtract(salesInvoiceDetail.getNotaxamount()));
							salesInvoiceDetail.setRemark(beginAmount.getRemark());
							salesInvoiceDetail.setSeq(999);
							salesInvoiceDetailList.add(salesInvoiceDetail);
						}
					}
				}
				if(salesInvoiceDetailList.size()<500){
					salesInvoiceMapper.addSalesInvoiceDetailList(salesInvoiceDetailList);
				}else{
					int num = salesInvoiceDetailList.size()/500 +1;
					for(int i=1;i<=num;i++){
						if(i<num){
							List addList = new ArrayList(salesInvoiceDetailList.subList((i-1)*500, i*500));
							if(null!=addList && addList.size()>0){
								salesInvoiceMapper.addSalesInvoiceDetailList(addList);
							}
						}else{
							List addList = new ArrayList(salesInvoiceDetailList.subList((i-1)*500, salesInvoiceDetailList.size()));
							if(null!=addList && addList.size()>0){
								salesInvoiceMapper.addSalesInvoiceDetailList(addList);
							}
						}
					}
				}
				SysUser sysUser = getSysUser();
				salesInvoice.setAdddeptid(sysUser.getDepartmentid());
				salesInvoice.setAdddeptname(sysUser.getDepartmentname());
				salesInvoice.setAdduserid(sysUser.getUserid());
				salesInvoice.setAddusername(sysUser.getName());
				//含税总金额
				BigDecimal alltaxamount = new BigDecimal(0);
				//未税总金额
				BigDecimal allnotaxamount = new BigDecimal(0);
				//应收总金额
				BigDecimal invoiceamount =  new BigDecimal(0);
				for(SalesInvoiceDetail salesInvoiceDetail : salesInvoiceDetailList){
					alltaxamount = alltaxamount.add(salesInvoiceDetail.getTaxamount());
					allnotaxamount = allnotaxamount.add(salesInvoiceDetail.getNotaxamount());
					invoiceamount = invoiceamount.add(salesInvoiceDetail.getTaxamount());
				}
				salesInvoice.setTaxamount(alltaxamount);
				salesInvoice.setNotaxamount(allnotaxamount);
				salesInvoice.setInvoiceamount(invoiceamount);
				List<String> sourceidList = salesInvoiceMapper.getSalesInvoiceSouceidList(salesInvoice.getId());
				String billsourceid = "";
				for(String sourceid : sourceidList){
					if("".equals(billsourceid)){
						billsourceid = sourceid;
					}else{
						billsourceid += ","+sourceid;
					}
				}
				salesInvoice.setSourceid(billsourceid);
				int i = salesInvoiceMapper.addSalesInvoice(salesInvoice);
				flag = i>0;
				if(flag){
					salesInvoiceid = salesInvoice.getId();
					salesOutService.updateReceiptAndRejectBillInvoice(sourceidList,salesInvoice,"1");
				}else{
					throw new Exception("销售核销新增出错");
				}
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", salesInvoiceid);
		map.put("msg", msg);
		return map;
	}
	
	@Override
	public Map addSalesInvoiceByReceiptAndRejectbillForPhone(String uid, String ids, String customerid) throws Exception {
		boolean flag = false;
		String salesInvoiceid = "";
		String msg = "";
		if(null==ids || "".equals(ids)){
		}else{
			JSONArray idArr = JSONArray.fromObject(ids);
			String saledept = null;
			String saleuser = null;
			String settletype = null;
			String paytype =null;
			boolean addFlag = true;
			String sourceids = null;
			//用来存放来源单据编号
			Map sourceidMap = new HashMap();
			//门店客户
			Map childcustomerMap =  new HashMap();
			//单据编号集合
			Map billMap = new HashMap();
			//验证多个回单是否能组成一张销售发票
			for(int i=0;i<idArr.size();i++){
				JSONObject detailObject = (JSONObject) idArr.get(i);
				//单据类型 1回单2销售退货通知单
				String billtype = detailObject.getString("billtype");
				//单据编号
				String id = detailObject.getString("billid");
				//明细编号
				String detailid = detailObject.getString("detailid");
				//是否折扣
				String isdiscount = "0";
				if(detailObject.containsKey("isdiscount")){
					isdiscount = detailObject.getString("isdiscount");
				}
				if(!sourceidMap.containsKey(id)){
					//来源单据编号
					if(sourceids==null){
						sourceids = id;
					}else{
						sourceids += ","+id;
					}
					sourceidMap.put(id, billtype);
				}
				
				if("1".equals(billtype)){
					if(!billMap.containsKey(id)){
						Receipt receipt = salesOutService.getReceipt(id);
						childcustomerMap.put(receipt.getCustomerid(), receipt.getCustomerid());
						//获取总店客户档案信息 没有总店信息则获取自身
						Customer headCutomer = salesExtService.getHeadCustomer(receipt.getCustomerid());
						if(null==customerid){
							customerid = headCutomer.getId();
						}
						//判断是否同一个客户
						if(customerid.equals(receipt.getCustomerid()) || customerid.equals(headCutomer.getId())){
						}else{
							addFlag = false;
							msg = "不是同一客户(总店)下的销售发货回单，不能生成销售发票";
							break;
						}
						billMap.put(id, receipt);
					}
				}else if("2".equals(billtype)){
					if(!billMap.containsKey(id)){
						RejectBill rejectBill = salesOutService.getRejectBill(id);
						childcustomerMap.put(rejectBill.getCustomerid(), rejectBill.getCustomerid());
						//获取总店客户档案信息 没有总店信息则获取自身
						Customer headCutomer = salesExtService.getHeadCustomer(rejectBill.getCustomerid());
						if(null==customerid){
							customerid = headCutomer.getId();
						}
						//判断是否同一个客户
						if(customerid.equals(rejectBill.getCustomerid()) || customerid.equals(headCutomer.getId())){
							if(null==customerid){
								customerid = headCutomer.getId();
							}
						}else{
							addFlag = false;
							msg = "不是同一客户(总店)下的销售退货通知单，不能生成销售发票";
							break;
						}
						billMap.put(id, rejectBill);
					}
				}else if("3".equals(billtype)){
					if(!billMap.containsKey(id)){
						CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(id);
						childcustomerMap.put(customerPushBalance.getCustomerid(), customerPushBalance.getCustomerid());
						//获取总店客户档案信息 没有总店信息则获取自身
						Customer headCutomer = salesExtService.getHeadCustomer(customerPushBalance.getCustomerid());
						if(null==customerid){
							customerid = headCutomer.getId();
						}
						//判断是否同一个客户
						if(customerid.equals(customerPushBalance.getCustomerid()) || customerid.equals(headCutomer.getId())){
							if(null==customerid){
								customerid = headCutomer.getId();
							}
						}else{
							addFlag = false;
							msg = "不是同一客户(总店)下的冲差单，不能生成销售发票";
							break;
						}
						billMap.put(id, customerPushBalance);
					}
				}else if("4".equals(billtype)){
                    if(!billMap.containsKey(id)){
                        BeginAmount beginAmount = beginAmountMapper.getBeginAmountByID(id);
                        childcustomerMap.put(beginAmount.getCustomerid(), beginAmount.getCustomerid());
                        //获取总店客户档案信息 没有总店信息则获取自身
                        Customer headCutomer = salesExtService.getHeadCustomer(beginAmount.getCustomerid());
                        if(null==customerid){
                            customerid = headCutomer.getId();
                        }
                        //判断是否同一个客户
                        if(customerid.equals(beginAmount.getCustomerid()) || customerid.equals(headCutomer.getId())){
                            if(null==customerid){
                                customerid = headCutomer.getId();
                            }
                        }else{
                            addFlag = false;
                            msg = "不是同一客户(总店)下的应收款期初，不能生成销售发票";
                            break;
                        }
                        billMap.put(id, beginAmount);
                    }
                }
			}
			//判断是否可以生成销售发票
			if(addFlag){
				SalesInvoice salesInvoice = new SalesInvoice();
				if (isAutoCreate("t_account_sales_invoice")) {
					// 获取自动编号
					String id = getAutoCreateSysNumbderForeign(salesInvoice, "t_account_sales_invoice");
					salesInvoice.setId(id);
				}else{
					salesInvoice.setId("XSFP-"+CommonUtils.getDataNumberSendsWithRand());
				}
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				salesInvoice.setBusinessdate(dateFormat.format(calendar.getTime()));
				salesInvoice.setStatus("2");
				salesInvoice.setSourcetype("2");
				//是否折扣 0否
				salesInvoice.setIsdiscount("0");
				//发票类型 1增值税
				//salesInvoice.setInvoicetype("1");
				//是否核销 0否
				salesInvoice.setIswriteoff("0");
				
				Customer customer = getCustomerByID(customerid);
				salesInvoice.setCustomerid(customerid);
				if(null!=customer){
					salesInvoice.setHandlerid(customer.getContact());
					salesInvoice.setInvoicecustomerid(customerid);
					//开票类型： 取客户档案核销方式
					salesInvoice.setApplytype(customer.getCanceltype());
					salesInvoice.setSalesdept(customer.getSalesdeptid());
					salesInvoice.setSalesuser(customer.getSalesuserid());
					salesInvoice.setSettletype(customer.getSettletype());
					salesInvoice.setPaytype(customer.getPaytype());
					salesInvoice.setIndooruserid(customer.getIndoorstaff());
					//总店
					if("0".equals(customer.getIslast())){
						salesInvoice.setPcustomerid(customer.getId());
					}else{
						salesInvoice.setPcustomerid(customer.getPid());
					}
				}
				String customerids = null;
				Set set = childcustomerMap.entrySet();
				Iterator it = set.iterator();
				while (it.hasNext()) {
					Map.Entry<String, String> entry = (Entry<String, String>) it.next();
					if(null==customerids){
						customerids = entry.getKey();
					}else{
						if(customerids.indexOf(entry.getKey()) == -1){
							customerids += ","+entry.getKey();
						}
					}
				}
				salesInvoice.setChlidcustomerid(customerids);
				//开票客户
				salesInvoice.setInvoicecustomerid(customer.getId());
				//获取该客户的上次开票客户名称
				String invoicecustomername = salesInvoiceMapper.getSalesInvoiceNameByCustomerid(customer.getId());
				if(null!=invoicecustomername){
					//开票客户名称
					salesInvoice.setInvoicecustomername(invoicecustomername);
				}else{
					//开票客户名称
					salesInvoice.setInvoicecustomername(customer.getName());
				}
				
				List<SalesInvoiceDetail> salesInvoiceDetailList = new ArrayList<SalesInvoiceDetail>();
				
				Map billObjectMap = new HashMap();
				for(int i=0;i<idArr.size();i++){
					JSONObject detailObject = (JSONObject) idArr.get(i);
					//单据类型 1回单2销售退货通知单
					String billtype = detailObject.getString("billtype");
					//单据编号
					String id = detailObject.getString("billid");
					//明细编号
					String detailid = detailObject.getString("detailid");
					SalesInvoiceDetail salesInvoiceDetail = null;
					
					if("1".equals(billtype)){
						Receipt receipt = null;
						if(billObjectMap.containsKey(id)){
							receipt = (Receipt) billObjectMap.get(id);
						}else{
							receipt = salesOutService.getReceipt(id);
							billObjectMap.put(id, receipt);
						}
						if(null!=receipt){
							ReceiptDetail receiptDetail = salesOutService.getReceiptDetailInfo(detailid,id);
							if(null!=receiptDetail){
								if("1".equals(receiptDetail.getIsdiscount()) && "1".equals(receiptDetail.getIsbranddiscount())){
									List<ReceiptDetail> list = salesOutService.getReceiptDetailBrandDiscountList(receiptDetail.getBillid(), receiptDetail.getBrandid());
									for(ReceiptDetail receiptDetail2 : list){
										salesInvoiceDetail = new SalesInvoiceDetail();
										salesInvoiceDetail.setBillid(salesInvoice.getId());
										salesInvoiceDetail.setIsdiscount(receiptDetail2.getIsdiscount());
										salesInvoiceDetail.setIsbranddiscount(receiptDetail2.getIsbranddiscount());
										//来源销售发货回单
										salesInvoiceDetail.setSourcetype("1");
										salesInvoiceDetail.setSourceid(id);
										salesInvoiceDetail.setSourcedetailid(receiptDetail2.getId());
										salesInvoiceDetail.setCustomerid(receipt.getCustomerid()!=null?receipt.getCustomerid():"");
										salesInvoiceDetail.setPcustomerid(receipt.getPcustomerid()!=null?receipt.getPcustomerid():"");
										salesInvoiceDetail.setCustomersort(receipt.getCustomersort()!=null?receipt.getCustomersort():"");
										salesInvoiceDetail.setSalesarea(receipt.getSalesarea()!=null?receipt.getSalesarea():"");
										salesInvoiceDetail.setSalesdept(receipt.getSalesdept()!=null?receipt.getSalesdept():"");
										salesInvoiceDetail.setSalesuser(receipt.getSalesuser()!=null?receipt.getSalesuser():"");
										salesInvoiceDetail.setGoodsid(receiptDetail2.getGoodsid()!=null?receiptDetail2.getGoodsid():"");
										salesInvoiceDetail.setBranddept(receiptDetail2.getBranddept()!=null?receiptDetail2.getBranddept():"");
										salesInvoiceDetail.setCostprice(receiptDetail2.getCostprice());
										//商品品牌 和品牌业务员信息
										GoodsInfo goodsInfo = getAllGoodsInfoByID(receiptDetail2.getGoodsid());
										if(null!=goodsInfo){
											salesInvoiceDetail.setGoodssort(goodsInfo.getDefaultsort());
											salesInvoiceDetail.setBrandid(goodsInfo.getBrand());
											salesInvoiceDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceDetail.getCustomerid()));
											//厂家业务员
											salesInvoiceDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceDetail.getCustomerid()));
											salesInvoiceDetail.setSupplierid(goodsInfo.getDefaultsupplier());
										}
										salesInvoiceDetail.setUnitid(receiptDetail2.getUnitid());
										salesInvoiceDetail.setUnitname(receiptDetail2.getUnitname());
										salesInvoiceDetail.setUnitnum(receiptDetail2.getReceiptnum());
										salesInvoiceDetail.setAuxunitid(receiptDetail2.getAuxunitid());
										salesInvoiceDetail.setAuxunitname(receiptDetail2.getAuxunitname());
										Map auxMap = countGoodsInfoNumber(receiptDetail2.getGoodsid(), receiptDetail2.getAuxunitid(), receiptDetail2.getReceiptnum());
										salesInvoiceDetail.setAuxnum((BigDecimal) auxMap.get("auxnum"));
										salesInvoiceDetail.setAuxnumdetail( (String) auxMap.get("auxnumdetail"));
										
										//退货通知单生成的发票 金额为负
										salesInvoiceDetail.setTaxprice(receiptDetail2.getTaxprice());
										BigDecimal taxamount=receiptDetail2.getReceipttaxamount();
										salesInvoiceDetail.setTaxamount(taxamount);
										salesInvoiceDetail.setTaxtype(receiptDetail2.getTaxtype());
										//重新计算税额税额
										BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, salesInvoiceDetail.getTaxtype());
										salesInvoiceDetail.setNotaxamount(notaxamount);
										//未税单价 = 未税金额/数量
										//未税单价 = 未税金额/数量
										if(null!=notaxamount && notaxamount.compareTo(BigDecimal.ZERO)!=0 
												&& null!=salesInvoiceDetail.getUnitnum() && salesInvoiceDetail.getUnitnum().compareTo(BigDecimal.ZERO)!=0){
											salesInvoiceDetail.setNotaxprice(notaxamount.divide(salesInvoiceDetail.getUnitnum(), 6,BigDecimal.ROUND_HALF_UP));
										}else{
											salesInvoiceDetail.setNotaxprice(receiptDetail2.getNotaxprice());
										}
										salesInvoiceDetail.setTax(salesInvoiceDetail.getTaxamount().subtract(salesInvoiceDetail.getNotaxamount()));
										salesInvoiceDetail.setRemark(receiptDetail2.getRemark());
										salesInvoiceDetail.setSeq(receiptDetail2.getSeq());
										salesInvoiceDetailList.add(salesInvoiceDetail);
									}
								}else{
									salesInvoiceDetail = new SalesInvoiceDetail();
									salesInvoiceDetail.setBillid(salesInvoice.getId());
									//来源销售发货回单
									salesInvoiceDetail.setSourcetype("1");
									salesInvoiceDetail.setSourceid(id);
									salesInvoiceDetail.setSourcedetailid(detailid);
									salesInvoiceDetail.setIsdiscount(receiptDetail.getIsdiscount());
									salesInvoiceDetail.setCustomerid(receipt.getCustomerid()!=null?receipt.getCustomerid():"");
									salesInvoiceDetail.setPcustomerid(receipt.getPcustomerid()!=null?receipt.getPcustomerid():"");
									salesInvoiceDetail.setCustomersort(receipt.getCustomersort()!=null?receipt.getCustomersort():"");
									salesInvoiceDetail.setSalesarea(receipt.getSalesarea()!=null?receipt.getSalesarea():"");
									salesInvoiceDetail.setSalesdept(receipt.getSalesdept()!=null?receipt.getSalesdept():"");
									salesInvoiceDetail.setSalesuser(receipt.getSalesuser()!=null?receipt.getSalesuser():"");
									salesInvoiceDetail.setGoodsid(receiptDetail.getGoodsid()!=null?receiptDetail.getGoodsid():"");
									salesInvoiceDetail.setBranddept(receiptDetail.getBranddept()!=null?receiptDetail.getBranddept():"");
									salesInvoiceDetail.setCostprice(receiptDetail.getCostprice());
									//商品品牌 和品牌业务员信息
									GoodsInfo goodsInfo = getAllGoodsInfoByID(receiptDetail.getGoodsid());
									if(null!=goodsInfo){
										salesInvoiceDetail.setGoodssort(goodsInfo.getDefaultsort());
										salesInvoiceDetail.setBrandid(goodsInfo.getBrand());
										salesInvoiceDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceDetail.getCustomerid()));
										//厂家业务员
										salesInvoiceDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceDetail.getCustomerid()));
										salesInvoiceDetail.setSupplierid(goodsInfo.getDefaultsupplier());
									}
									salesInvoiceDetail.setUnitid(receiptDetail.getUnitid());
									salesInvoiceDetail.setUnitname(receiptDetail.getUnitname());
									salesInvoiceDetail.setUnitnum(receiptDetail.getReceiptnum());
									salesInvoiceDetail.setAuxunitid(receiptDetail.getAuxunitid());
									salesInvoiceDetail.setAuxunitname(receiptDetail.getAuxunitname());
									Map auxMap = countGoodsInfoNumber(receiptDetail.getGoodsid(), receiptDetail.getAuxunitid(), receiptDetail.getReceiptnum());
									salesInvoiceDetail.setAuxnum((BigDecimal) auxMap.get("auxnum"));
									salesInvoiceDetail.setAuxnumdetail( (String) auxMap.get("auxnumdetail"));
									
									//退货通知单生成的发票 金额为负
									salesInvoiceDetail.setTaxprice(receiptDetail.getTaxprice());
									BigDecimal taxamount=receiptDetail.getReceipttaxamount();
									salesInvoiceDetail.setTaxamount(taxamount);
									salesInvoiceDetail.setTaxtype(receiptDetail.getTaxtype());
									//重新计算税额税额
									BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, salesInvoiceDetail.getTaxtype());
									salesInvoiceDetail.setNotaxamount(notaxamount);
									//未税单价 = 未税金额/数量
									if(null!=notaxamount && notaxamount.compareTo(BigDecimal.ZERO)!=0 
											&& null!=salesInvoiceDetail.getUnitnum() && salesInvoiceDetail.getUnitnum().compareTo(BigDecimal.ZERO)!=0){
										salesInvoiceDetail.setNotaxprice(notaxamount.divide(salesInvoiceDetail.getUnitnum(), 6,BigDecimal.ROUND_HALF_UP));
									}else{
										salesInvoiceDetail.setNotaxprice(receiptDetail.getNotaxprice());
									}
									salesInvoiceDetail.setTax(salesInvoiceDetail.getTaxamount().subtract(salesInvoiceDetail.getNotaxamount()));
									salesInvoiceDetail.setRemark(receiptDetail.getRemark());
									salesInvoiceDetail.setSeq(receiptDetail.getSeq());
									salesInvoiceDetailList.add(salesInvoiceDetail);
								}
							}
						}
					}else if("2".equals(billtype)){
						RejectBill rejectBill = null;
						if(billObjectMap.containsKey(id)){
							rejectBill = (RejectBill) billObjectMap.get(id);
						}else{
							rejectBill = salesOutService.getRejectBill(id);
							billObjectMap.put(id, rejectBill);
						}
						if(null!=rejectBill){
							RejectBillDetail rejectBillDetail = salesOutService.getRejectBillDetailInfo(detailid, id);
							if(null!=rejectBillDetail){
								salesInvoiceDetail = new SalesInvoiceDetail();
								salesInvoiceDetail.setBillid(salesInvoice.getId());
								//来源销售退货通知单
								salesInvoiceDetail.setSourcetype("2");
								salesInvoiceDetail.setSourceid(rejectBill.getId());
								salesInvoiceDetail.setSourcedetailid(rejectBillDetail.getId());
								salesInvoiceDetail.setCustomerid(rejectBill.getCustomerid()!=null?rejectBill.getCustomerid():"");
								salesInvoiceDetail.setPcustomerid(rejectBill.getPcustomerid()!=null?rejectBill.getPcustomerid():"");
								salesInvoiceDetail.setCustomersort(rejectBill.getCustomersort()!=null?rejectBill.getCustomersort():"");
								salesInvoiceDetail.setSalesarea(rejectBill.getSalesarea()!=null?rejectBill.getSalesarea():"");
								salesInvoiceDetail.setSalesdept(rejectBill.getSalesdept()!=null?rejectBill.getSalesdept():"");
								salesInvoiceDetail.setSalesuser(rejectBill.getSalesuser()!=null?rejectBill.getSalesuser():"");
								salesInvoiceDetail.setGoodsid(rejectBillDetail.getGoodsid()!=null?rejectBillDetail.getGoodsid():"");
								salesInvoiceDetail.setBranddept(rejectBillDetail.getBranddept()!=null?rejectBillDetail.getBranddept():"");
								salesInvoiceDetail.setCostprice(rejectBillDetail.getCostprice());
								//商品品牌 和品牌业务员信息
								GoodsInfo goodsInfo = getAllGoodsInfoByID(rejectBillDetail.getGoodsid());
								if(null!=goodsInfo){
									salesInvoiceDetail.setGoodssort(goodsInfo.getDefaultsort());
									salesInvoiceDetail.setBrandid(goodsInfo.getBrand());
									salesInvoiceDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceDetail.getCustomerid()));
									//厂家业务员
									salesInvoiceDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceDetail.getCustomerid()));
									salesInvoiceDetail.setSupplierid(goodsInfo.getDefaultsupplier());
								}
								salesInvoiceDetail.setUnitid(rejectBillDetail.getUnitid());
								salesInvoiceDetail.setUnitname(rejectBillDetail.getUnitname());
								salesInvoiceDetail.setUnitnum(rejectBillDetail.getUnitnum().negate());
								salesInvoiceDetail.setAuxunitid(rejectBillDetail.getAuxunitid());
								salesInvoiceDetail.setAuxunitname(rejectBillDetail.getAuxunitname());
								Map auxMap = countGoodsInfoNumber(rejectBillDetail.getGoodsid(), rejectBillDetail.getAuxunitid(), rejectBillDetail.getUnitnum().negate());
								salesInvoiceDetail.setAuxnum((BigDecimal) auxMap.get("auxnum"));
								salesInvoiceDetail.setAuxnumdetail((String) auxMap.get("auxnumdetail"));
								
								//退货通知单生成的发票 金额为负
								salesInvoiceDetail.setTaxprice(rejectBillDetail.getTaxprice());
								BigDecimal taxamount=rejectBillDetail.getTaxamount().negate();
								salesInvoiceDetail.setTaxamount(taxamount);
								salesInvoiceDetail.setTaxtype(rejectBillDetail.getTaxtype());
								//重新计算税额税额
								BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, salesInvoiceDetail.getTaxtype());
								salesInvoiceDetail.setNotaxamount(notaxamount);
								//未税单价 = 未税金额/数量
								if(null!=notaxamount && notaxamount.compareTo(BigDecimal.ZERO)!=0 
										&& null!=salesInvoiceDetail.getUnitnum() && salesInvoiceDetail.getUnitnum().compareTo(BigDecimal.ZERO)!=0){
									salesInvoiceDetail.setNotaxprice(notaxamount.divide(salesInvoiceDetail.getUnitnum(), 6,BigDecimal.ROUND_HALF_UP));
								}else{
									salesInvoiceDetail.setNotaxprice(rejectBillDetail.getNotaxprice());
								}
								salesInvoiceDetail.setTax(salesInvoiceDetail.getTaxamount().subtract(salesInvoiceDetail.getNotaxamount()));
								salesInvoiceDetail.setRemark(rejectBillDetail.getRemark());
								salesInvoiceDetail.setSeq(rejectBillDetail.getSeq());
								salesInvoiceDetailList.add(salesInvoiceDetail);
							}
						}
					}else if("3".equals(billtype)){
						CustomerPushBalance customerPushBalance = null;
						if(billObjectMap.containsKey(id)){
							customerPushBalance = (CustomerPushBalance) billObjectMap.get(id);
						}else{
							customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(id);
							billObjectMap.put(id, customerPushBalance);
						}
						if(null!=customerPushBalance){
							salesInvoiceDetail = new SalesInvoiceDetail();
							salesInvoiceDetail.setBillid(salesInvoice.getId());
							//来源销售退货通知单
							salesInvoiceDetail.setSourcetype("3");
							salesInvoiceDetail.setSourceid(customerPushBalance.getId());
							salesInvoiceDetail.setSourcedetailid(customerPushBalance.getId());
							salesInvoiceDetail.setCustomerid(customerPushBalance.getCustomerid());
							salesInvoiceDetail.setPcustomerid(customerPushBalance.getPcustomerid()!=null?customerPushBalance.getPcustomerid():"");
							salesInvoiceDetail.setCustomersort(customerPushBalance.getCustomersort()!=null?customerPushBalance.getCustomersort():"");
							salesInvoiceDetail.setSalesarea(customerPushBalance.getSalesarea()!=null?customerPushBalance.getSalesarea():"");
							salesInvoiceDetail.setSalesdept(customerPushBalance.getSalesdept()!=null?customerPushBalance.getSalesdept():"");
							salesInvoiceDetail.setSalesuser(customerPushBalance.getSalesuser()!=null?customerPushBalance.getSalesuser():"");
							salesInvoiceDetail.setGoodsid(customerPushBalance.getBrandid()!=null?customerPushBalance.getBrandid():"");
							salesInvoiceDetail.setBranddept(customerPushBalance.getBranddept()!=null?customerPushBalance.getBranddept():"");
							
							salesInvoiceDetail.setBrandid(customerPushBalance.getBrandid());
							salesInvoiceDetail.setBranduser(getBrandUseridByCustomeridAndBrand(customerPushBalance.getBrandid(), salesInvoiceDetail.getCustomerid()));
							//厂家业务员
							salesInvoiceDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(customerPushBalance.getBrandid(), salesInvoiceDetail.getCustomerid()));
							//设置供应商
							Brand brand = getGoodsBrandByID(customerPushBalance.getBrandid());
							if(null!=brand){
								salesInvoiceDetail.setSupplierid(brand.getSupplierid());
                                salesInvoiceDetail.setTaxtype(brand.getDefaulttaxtype());
							}
							if(StringUtils.isEmpty(salesInvoiceDetail.getTaxtype())){
	                            salesInvoiceDetail.setTaxtype(customerPushBalance.getDefaulttaxtype());
							}
							salesInvoiceDetail.setUnitid("");
							salesInvoiceDetail.setUnitname("");
							salesInvoiceDetail.setUnitnum(BigDecimal.ZERO);
							salesInvoiceDetail.setAuxunitid("");
							salesInvoiceDetail.setAuxunitname("");
							salesInvoiceDetail.setAuxnum(BigDecimal.ZERO);
							salesInvoiceDetail.setAuxnumdetail("");
							
							//退货通知单生成的发票 金额为负
							salesInvoiceDetail.setTaxprice(BigDecimal.ZERO);
							BigDecimal taxamount=customerPushBalance.getAmount();
							salesInvoiceDetail.setTaxamount(taxamount);
							salesInvoiceDetail.setNotaxprice(BigDecimal.ZERO);

							//重新计算税额税额
							BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, salesInvoiceDetail.getTaxtype());
							salesInvoiceDetail.setNotaxamount(notaxamount);
							salesInvoiceDetail.setTax(salesInvoiceDetail.getTaxamount().subtract(salesInvoiceDetail.getNotaxamount()));
							salesInvoiceDetail.setRemark(customerPushBalance.getRemark());
							salesInvoiceDetail.setSeq(999);
							salesInvoiceDetailList.add(salesInvoiceDetail);
						}
					}
				}
				if(salesInvoiceDetailList.size()<500){
					salesInvoiceMapper.addSalesInvoiceDetailList(salesInvoiceDetailList);
				}else{
					int num = salesInvoiceDetailList.size()/500 +1;
					for(int i=1;i<=num;i++){
						if(i<num){
							List addList = new ArrayList(salesInvoiceDetailList.subList((i-1)*500, i*500));
							if(null!=addList && addList.size()>0){
								salesInvoiceMapper.addSalesInvoiceDetailList(addList);
							}
						}else{
							List addList = new ArrayList(salesInvoiceDetailList.subList((i-1)*500, salesInvoiceDetailList.size()));
							if(null!=addList && addList.size()>0){
								salesInvoiceMapper.addSalesInvoiceDetailList(addList);
							}
						}
					}
				}
				SysUser sysUser = getSysUserById(uid);
				salesInvoice.setAdddeptid(sysUser.getDepartmentid());
				salesInvoice.setAdddeptname(sysUser.getDepartmentname());
				salesInvoice.setAdduserid(sysUser.getUserid());
				salesInvoice.setAddusername(sysUser.getName());
				//含税总金额
				BigDecimal alltaxamount = new BigDecimal(0);
				//未税总金额
				BigDecimal allnotaxamount = new BigDecimal(0);
				//应收总金额
				BigDecimal invoiceamount =  new BigDecimal(0);
				for(SalesInvoiceDetail salesInvoiceDetail : salesInvoiceDetailList){
					alltaxamount = alltaxamount.add(salesInvoiceDetail.getTaxamount());
					allnotaxamount = allnotaxamount.add(salesInvoiceDetail.getNotaxamount());
					invoiceamount = invoiceamount.add(salesInvoiceDetail.getTaxamount());
				}
				salesInvoice.setTaxamount(alltaxamount);
				salesInvoice.setNotaxamount(allnotaxamount);
				salesInvoice.setInvoiceamount(invoiceamount);
				List<String> sourceidList = salesInvoiceMapper.getSalesInvoiceSouceidList(salesInvoice.getId());
				String billsourceid = "";
				for(String sourceid : sourceidList){
					if("".equals(billsourceid)){
						billsourceid = sourceid;
					}else{
						billsourceid += ","+sourceid;
					}
				}
				salesInvoice.setSourceid(billsourceid);
				int i = salesInvoiceMapper.addSalesInvoice(salesInvoice);
				flag = i>0;
				if(flag){
					salesInvoiceid = salesInvoice.getId();
					salesOutService.updateReceiptAndRejectBillInvoice(sourceidList,salesInvoice,"1");
				}
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", salesInvoiceid);
		map.put("msg", msg);
		return map;
	}

	@Override
	public Map addToSalesInvoiceByReceiptAndRejectbill(String ids,
			String customerid, String billid) throws Exception {
		boolean flag = false;
		String salesInvoiceid = "";
		String msg = "";
		if(null==ids || "".equals(ids)){
		}else{
			JSONArray idArr = JSONArray.fromObject(ids);
			boolean addFlag = true;
			String sourceids = null;
			//用来存放来源单据编号
			Map sourceidMap = new HashMap();
			//门店客户
			Map childcustomerMap =  new HashMap();
			Map billMap = new HashMap();
			SalesInvoice salesInvoice = salesInvoiceMapper.getSalesInvoiceInfo(billid);
			if(null!=salesInvoice && "2".equals(salesInvoice.getStatus()) && salesInvoice.getCustomerid().equals(customerid)){
				//验证多个回单是否能组成一张销售发票
				for(int i=0;i<idArr.size();i++){
					JSONObject detailObject = (JSONObject) idArr.get(i);
					//单据类型 1回单2销售退货通知单
					String billtype = detailObject.getString("billtype");
					//单据编号
					String id = detailObject.getString("billid");
					//明细编号
					String detailid = detailObject.getString("detailid");
					//是否折扣
					String isdiscount = "0";
					if(detailObject.containsKey("isdiscount")){
						isdiscount = detailObject.getString("isdiscount");
					}
					if(!sourceidMap.containsKey(id)){
						//来源单据编号
						if(sourceids==null){
							sourceids = id;
						}else{
							sourceids += ","+id;
						}
						sourceidMap.put(id, billtype);
					}
					
					if("1".equals(billtype)){
						if(!billMap.containsKey(id)){
							Receipt receipt = salesOutService.getReceipt(id);
							childcustomerMap.put(receipt.getCustomerid(), receipt.getCustomerid());
							//获取总店客户档案信息 没有总店信息则获取自身
							Customer headCutomer = salesExtService.getHeadCustomer(receipt.getCustomerid());
							if(null==customerid){
								customerid = headCutomer.getId();
							}
							//判断是否同一个客户
							if(customerid.equals(receipt.getCustomerid()) || customerid.equals(headCutomer.getId())){
							}else{
								addFlag = false;
								msg = "不是同一客户(总店)下的销售发货回单，不能生成销售发票";
								break;
							}
							billMap.put(id, receipt);
						}
					}else if("2".equals(billtype)){
						if(!billMap.containsKey(id)){
							RejectBill rejectBill = salesOutService.getRejectBill(id);
							childcustomerMap.put(rejectBill.getCustomerid(), rejectBill.getCustomerid());
							//获取总店客户档案信息 没有总店信息则获取自身
							Customer headCutomer = salesExtService.getHeadCustomer(rejectBill.getCustomerid());
							if(null==customerid){
								customerid = headCutomer.getId();
							}
							//判断是否同一个客户
							if(customerid.equals(rejectBill.getCustomerid()) || customerid.equals(headCutomer.getId())){
								if(null==customerid){
									customerid = headCutomer.getId();
								}
							}else{
								addFlag = false;
								msg = "不是同一客户(总店)下的销售退货通知单，不能生成销售发票";
								break;
							}
							billMap.put(id, rejectBill);
						}
					}else if("3".equals(billtype)){
						if(!billMap.containsKey(id)){
							CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(id);
							childcustomerMap.put(customerPushBalance.getCustomerid(), customerPushBalance.getCustomerid());
							//获取总店客户档案信息 没有总店信息则获取自身
							Customer headCutomer = salesExtService.getHeadCustomer(customerPushBalance.getCustomerid());
							if(null==customerid){
								customerid = headCutomer.getId();
							}
							//判断是否同一个客户
							if(customerid.equals(customerPushBalance.getCustomerid()) || customerid.equals(headCutomer.getId())){
								if(null==customerid){
									customerid = headCutomer.getId();
								}
							}else{
								addFlag = false;
								msg = "不是同一客户(总店)下的冲差单，不能生成销售发票";
								break;
							}
							billMap.put(id, customerPushBalance);
						}
					}else if("4".equals(billtype)){
						if(!billMap.containsKey(id)){
							BeginAmount beginAmount = beginAmountMapper.getBeginAmountByID(id);
							childcustomerMap.put(beginAmount.getCustomerid(), beginAmount.getCustomerid());
							//获取总店客户档案信息 没有总店信息则获取自身
							Customer headCutomer = salesExtService.getHeadCustomer(beginAmount.getCustomerid());
							if(null==customerid){
								customerid = headCutomer.getId();
							}
							//判断是否同一个客户
							if(customerid.equals(beginAmount.getCustomerid()) || customerid.equals(headCutomer.getId())){
								if(null==customerid){
									customerid = headCutomer.getId();
								}
							}else{
								addFlag = false;
								msg = "不是同一客户(总店)下的应收款期初，不能生成销售发票";
								break;
							}
							billMap.put(id, beginAmount);
						}
					}
				}
			}else{
				addFlag = false;
			}
			//判断是否可以生成销售发票
			if(addFlag){
				
				String customerids = salesInvoice.getChlidcustomerid();
				Set set = childcustomerMap.entrySet();
				Iterator it = set.iterator();
				while (it.hasNext()) {
					Map.Entry<String, String> entry = (Entry<String, String>) it.next();
					if(null==customerids || "".equals(customerids)){
						customerids = entry.getKey();
					}else{
						if(customerids.indexOf(entry.getKey())==-1){
							customerids += ","+entry.getKey();
						}
					}
				}
				salesInvoice.setChlidcustomerid(customerids);
				List<SalesInvoiceDetail> salesInvoiceDetailList = new ArrayList<SalesInvoiceDetail>();
				
				for(int i=0;i<idArr.size();i++){
					JSONObject detailObject = (JSONObject) idArr.get(i);
					//单据类型 1回单2销售退货通知单
					String billtype = detailObject.getString("billtype");
					//单据编号
					String id = detailObject.getString("billid");
					//明细编号
					String detailid = detailObject.getString("detailid");
					SalesInvoiceDetail salesInvoiceDetail = null;
					
					if("1".equals(billtype)){
						Receipt receipt = salesOutService.getReceipt(id);
						if(null!=receipt){
							ReceiptDetail receiptDetail = salesOutService.getReceiptDetailInfo(detailid,id);
							if(null!=receiptDetail){
								if("1".equals(receiptDetail.getIsdiscount()) && "1".equals(receiptDetail.getIsbranddiscount())){
									List<ReceiptDetail> list = salesOutService.getReceiptDetailBrandDiscountList(receiptDetail.getBillid(), receiptDetail.getBrandid());
									for(ReceiptDetail receiptDetail2 : list){
										salesInvoiceDetail = new SalesInvoiceDetail();
										salesInvoiceDetail.setBillid(salesInvoice.getId());
										salesInvoiceDetail.setIsdiscount(receiptDetail2.getIsdiscount());
										salesInvoiceDetail.setIsbranddiscount(receiptDetail2.getIsbranddiscount());
										//来源销售发货回单
										salesInvoiceDetail.setSourcetype("1");
										salesInvoiceDetail.setSourceid(id);
										salesInvoiceDetail.setSourcedetailid(receiptDetail2.getId());
										salesInvoiceDetail.setCustomerid(receipt.getCustomerid()!=null?receipt.getCustomerid():"");
										salesInvoiceDetail.setPcustomerid(receipt.getPcustomerid()!=null?receipt.getPcustomerid():"");
										salesInvoiceDetail.setCustomersort(receipt.getCustomersort()!=null?receipt.getCustomersort():"");
										salesInvoiceDetail.setSalesarea(receipt.getSalesarea()!=null?receipt.getSalesarea():"");
										salesInvoiceDetail.setSalesdept(receipt.getSalesdept()!=null?receipt.getSalesdept():"");
										salesInvoiceDetail.setSalesuser(receipt.getSalesuser()!=null?receipt.getSalesuser():"");
										salesInvoiceDetail.setGoodsid(receiptDetail2.getGoodsid()!=null?receiptDetail2.getGoodsid():"");
										salesInvoiceDetail.setBranddept(receiptDetail2.getBranddept()!=null?receiptDetail2.getBranddept():"");
										salesInvoiceDetail.setCostprice(receiptDetail2.getCostprice());
										//商品品牌 和品牌业务员信息
										GoodsInfo goodsInfo = getAllGoodsInfoByID(receiptDetail2.getGoodsid());
										if(null!=goodsInfo){
											salesInvoiceDetail.setGoodssort(goodsInfo.getDefaultsort());
											salesInvoiceDetail.setBrandid(goodsInfo.getBrand());
											salesInvoiceDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceDetail.getCustomerid()));
											//厂家业务员
											salesInvoiceDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceDetail.getCustomerid()));
											salesInvoiceDetail.setSupplierid(goodsInfo.getDefaultsupplier());
										}
										salesInvoiceDetail.setUnitid(receiptDetail2.getUnitid());
										salesInvoiceDetail.setUnitname(receiptDetail2.getUnitname());
										salesInvoiceDetail.setUnitnum(receiptDetail2.getReceiptnum());
										salesInvoiceDetail.setAuxunitid(receiptDetail2.getAuxunitid());
										salesInvoiceDetail.setAuxunitname(receiptDetail2.getAuxunitname());
										Map auxMap = countGoodsInfoNumber(receiptDetail2.getGoodsid(), receiptDetail2.getAuxunitid(), receiptDetail2.getReceiptnum());
										salesInvoiceDetail.setAuxnum((BigDecimal) auxMap.get("auxnum"));
										salesInvoiceDetail.setAuxnumdetail( (String) auxMap.get("auxnumdetail"));
										
										//退货通知单生成的发票 金额为负
										salesInvoiceDetail.setTaxprice(receiptDetail2.getTaxprice());
										BigDecimal taxamount=receiptDetail2.getReceipttaxamount();
										salesInvoiceDetail.setTaxamount(taxamount);
										salesInvoiceDetail.setTaxtype(receiptDetail2.getTaxtype());
										//重新计算税额税额
										BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, salesInvoiceDetail.getTaxtype());
										salesInvoiceDetail.setNotaxamount(notaxamount);
										//未税单价 = 未税金额/数量
										if(null!=notaxamount && notaxamount.compareTo(BigDecimal.ZERO)!=0 
												&& null!=salesInvoiceDetail.getUnitnum() && salesInvoiceDetail.getUnitnum().compareTo(BigDecimal.ZERO)!=0){
											salesInvoiceDetail.setNotaxprice(notaxamount.divide(salesInvoiceDetail.getUnitnum(), 6,BigDecimal.ROUND_HALF_UP));
										}else{
											salesInvoiceDetail.setNotaxprice(receiptDetail2.getNotaxprice());
										}
										salesInvoiceDetail.setTax(receiptDetail2.getReceipttaxamount().subtract(receiptDetail2.getReceiptnotaxamount()));
										salesInvoiceDetail.setRemark(receiptDetail2.getRemark());
										salesInvoiceDetail.setSeq(receiptDetail2.getSeq());
										salesInvoiceDetailList.add(salesInvoiceDetail);
									}
								}else{
									salesInvoiceDetail = new SalesInvoiceDetail();
									salesInvoiceDetail.setBillid(salesInvoice.getId());
									//来源销售发货回单
									salesInvoiceDetail.setSourcetype("1");
									salesInvoiceDetail.setSourceid(id);
									salesInvoiceDetail.setSourcedetailid(detailid);
									salesInvoiceDetail.setIsdiscount(receiptDetail.getIsdiscount());
									salesInvoiceDetail.setCustomerid(receipt.getCustomerid()!=null?receipt.getCustomerid():"");
									salesInvoiceDetail.setPcustomerid(receipt.getPcustomerid()!=null?receipt.getPcustomerid():"");
									salesInvoiceDetail.setCustomersort(receipt.getCustomersort()!=null?receipt.getCustomersort():"");
									salesInvoiceDetail.setSalesarea(receipt.getSalesarea()!=null?receipt.getSalesarea():"");
									salesInvoiceDetail.setSalesdept(receipt.getSalesdept()!=null?receipt.getSalesdept():"");
									salesInvoiceDetail.setSalesuser(receipt.getSalesuser()!=null?receipt.getSalesuser():"");
									salesInvoiceDetail.setGoodsid(receiptDetail.getGoodsid()!=null?receiptDetail.getGoodsid():"");
									salesInvoiceDetail.setBranddept(receiptDetail.getBranddept()!=null?receiptDetail.getBranddept():"");
									salesInvoiceDetail.setCostprice(receiptDetail.getCostprice());
									//商品品牌 和品牌业务员信息
									GoodsInfo goodsInfo = getAllGoodsInfoByID(receiptDetail.getGoodsid());
									if(null!=goodsInfo){
										salesInvoiceDetail.setGoodssort(goodsInfo.getDefaultsort());
										salesInvoiceDetail.setBrandid(goodsInfo.getBrand());
										salesInvoiceDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceDetail.getCustomerid()));
										//厂家业务员
										salesInvoiceDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceDetail.getCustomerid()));
										salesInvoiceDetail.setSupplierid(goodsInfo.getDefaultsupplier());
									}
									salesInvoiceDetail.setUnitid(receiptDetail.getUnitid());
									salesInvoiceDetail.setUnitname(receiptDetail.getUnitname());
									salesInvoiceDetail.setUnitnum(receiptDetail.getReceiptnum());
									salesInvoiceDetail.setAuxunitid(receiptDetail.getAuxunitid());
									salesInvoiceDetail.setAuxunitname(receiptDetail.getAuxunitname());
									Map auxMap = countGoodsInfoNumber(receiptDetail.getGoodsid(), receiptDetail.getAuxunitid(), receiptDetail.getReceiptnum());
									salesInvoiceDetail.setAuxnum((BigDecimal) auxMap.get("auxnum"));
									salesInvoiceDetail.setAuxnumdetail( (String) auxMap.get("auxnumdetail"));
									
									//退货通知单生成的发票 金额为负
									salesInvoiceDetail.setTaxprice(receiptDetail.getTaxprice());
									BigDecimal taxamount=receiptDetail.getReceipttaxamount();
									salesInvoiceDetail.setTaxamount(taxamount);
									salesInvoiceDetail.setTaxtype(receiptDetail.getTaxtype());
									//重新计算税额税额
									BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, salesInvoiceDetail.getTaxtype());
									salesInvoiceDetail.setNotaxamount(notaxamount);
									//未税单价 = 未税金额/数量
									if(null!=notaxamount && notaxamount.compareTo(BigDecimal.ZERO)!=0 
											&& null!=salesInvoiceDetail.getUnitnum() && salesInvoiceDetail.getUnitnum().compareTo(BigDecimal.ZERO)!=0){
										salesInvoiceDetail.setNotaxprice(notaxamount.divide(salesInvoiceDetail.getUnitnum(), 6,BigDecimal.ROUND_HALF_UP));
									}else{
										salesInvoiceDetail.setNotaxprice(receiptDetail.getNotaxprice());
									}
									salesInvoiceDetail.setTax(receiptDetail.getReceipttaxamount().subtract(receiptDetail.getReceiptnotaxamount()));
									salesInvoiceDetail.setRemark(receiptDetail.getRemark());
									salesInvoiceDetail.setSeq(receiptDetail.getSeq());
									salesInvoiceDetailList.add(salesInvoiceDetail);
								}
							}
						}
					}else if("2".equals(billtype)){
						RejectBill rejectBill = salesOutService.getRejectBill(id);
						if(null!=rejectBill){
							RejectBillDetail rejectBillDetail = salesOutService.getRejectBillDetailInfo(detailid,id);
							if(null!=rejectBillDetail){
								salesInvoiceDetail = new SalesInvoiceDetail();
								salesInvoiceDetail.setBillid(salesInvoice.getId());
								//来源销售退货通知单
								salesInvoiceDetail.setSourcetype("2");
								salesInvoiceDetail.setSourceid(rejectBill.getId());
								salesInvoiceDetail.setSourcedetailid(rejectBillDetail.getId());
								salesInvoiceDetail.setCustomerid(rejectBill.getCustomerid()!=null?rejectBill.getCustomerid():"");
								salesInvoiceDetail.setPcustomerid(rejectBill.getPcustomerid()!=null?rejectBill.getPcustomerid():"");
								salesInvoiceDetail.setCustomersort(rejectBill.getCustomersort()!=null?rejectBill.getCustomersort():"");
								salesInvoiceDetail.setSalesarea(rejectBill.getSalesarea()!=null?rejectBill.getSalesarea():"");
								salesInvoiceDetail.setSalesdept(rejectBill.getSalesdept()!=null?rejectBill.getSalesdept():"");
								salesInvoiceDetail.setSalesuser(rejectBill.getSalesuser()!=null?rejectBill.getSalesuser():"");
								salesInvoiceDetail.setGoodsid(rejectBillDetail.getGoodsid()!=null?rejectBillDetail.getGoodsid():"");
								salesInvoiceDetail.setBranddept(rejectBillDetail.getBranddept()!=null?rejectBillDetail.getBranddept():"");
								salesInvoiceDetail.setCostprice(rejectBillDetail.getCostprice());
								//商品品牌 和品牌业务员信息
								GoodsInfo goodsInfo = getAllGoodsInfoByID(rejectBillDetail.getGoodsid());
								if(null!=goodsInfo){
									salesInvoiceDetail.setGoodssort(goodsInfo.getDefaultsort());
									salesInvoiceDetail.setBrandid(goodsInfo.getBrand());
									salesInvoiceDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceDetail.getCustomerid()));
									//厂家业务员
									salesInvoiceDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceDetail.getCustomerid()));
									salesInvoiceDetail.setSupplierid(goodsInfo.getDefaultsupplier());
								}
								salesInvoiceDetail.setUnitid(rejectBillDetail.getUnitid());
								salesInvoiceDetail.setUnitname(rejectBillDetail.getUnitname());
								salesInvoiceDetail.setUnitnum(rejectBillDetail.getInnummain().negate());
								salesInvoiceDetail.setAuxunitid(rejectBillDetail.getAuxunitid());
								salesInvoiceDetail.setAuxunitname(rejectBillDetail.getAuxunitname());
								Map auxMap = countGoodsInfoNumber(rejectBillDetail.getGoodsid(), rejectBillDetail.getAuxunitid(), rejectBillDetail.getInnummain().negate());
								salesInvoiceDetail.setAuxnum((BigDecimal) auxMap.get("auxnum"));
								salesInvoiceDetail.setAuxnumdetail((String) auxMap.get("auxnumdetail"));
								
								//退货通知单生成的发票 金额为负
								salesInvoiceDetail.setTaxprice(rejectBillDetail.getTaxprice());
								BigDecimal taxamount=rejectBillDetail.getTaxamount().negate();
								salesInvoiceDetail.setTaxamount(taxamount);
								salesInvoiceDetail.setTaxtype(rejectBillDetail.getTaxtype());
								//重新计算税额税额
								BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, salesInvoiceDetail.getTaxtype());
								salesInvoiceDetail.setNotaxamount(notaxamount);
								//未税单价 = 未税金额/数量
								if(null!=notaxamount && notaxamount.compareTo(BigDecimal.ZERO)!=0 
										&& null!=salesInvoiceDetail.getUnitnum() && salesInvoiceDetail.getUnitnum().compareTo(BigDecimal.ZERO)!=0){
									salesInvoiceDetail.setNotaxprice(notaxamount.divide(salesInvoiceDetail.getUnitnum(), 6,BigDecimal.ROUND_HALF_UP));
								}else{
									salesInvoiceDetail.setNotaxprice(rejectBillDetail.getNotaxprice());
								}
								salesInvoiceDetail.setTax(rejectBillDetail.getTaxamount().subtract(rejectBillDetail.getNotaxamount()).negate());
								salesInvoiceDetail.setRemark(rejectBillDetail.getRemark());
								salesInvoiceDetail.setSeq(rejectBillDetail.getSeq());
								salesInvoiceDetailList.add(salesInvoiceDetail);
							}
						}
					}else if("3".equals(billtype)){
						CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(id);
						if(null!=customerPushBalance){
							salesInvoiceDetail = new SalesInvoiceDetail();
							salesInvoiceDetail.setBillid(salesInvoice.getId());
							//来源销售退货通知单
							salesInvoiceDetail.setSourcetype("3");
							salesInvoiceDetail.setSourceid(customerPushBalance.getId());
							salesInvoiceDetail.setSourcedetailid(customerPushBalance.getId());
							salesInvoiceDetail.setCustomerid(customerPushBalance.getCustomerid());
							salesInvoiceDetail.setPcustomerid(customerPushBalance.getPcustomerid()!=null?customerPushBalance.getPcustomerid():"");
							salesInvoiceDetail.setCustomersort(customerPushBalance.getCustomersort()!=null?customerPushBalance.getCustomersort():"");
							salesInvoiceDetail.setSalesarea(customerPushBalance.getSalesarea()!=null?customerPushBalance.getSalesarea():"");
							salesInvoiceDetail.setSalesdept(customerPushBalance.getSalesdept()!=null?customerPushBalance.getSalesdept():"");
							salesInvoiceDetail.setSalesuser(customerPushBalance.getSalesuser()!=null?customerPushBalance.getSalesuser():"");
							salesInvoiceDetail.setGoodsid(customerPushBalance.getBrandid()!=null?customerPushBalance.getBrandid():"");
							salesInvoiceDetail.setBranddept(customerPushBalance.getBranddept()!=null?customerPushBalance.getBranddept():"");
							
							salesInvoiceDetail.setBrandid(customerPushBalance.getBrandid());
							salesInvoiceDetail.setBranduser(getBrandUseridByCustomeridAndBrand(customerPushBalance.getBrandid(), salesInvoiceDetail.getCustomerid()));
							//厂家业务员
							salesInvoiceDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(customerPushBalance.getBrandid(), salesInvoiceDetail.getCustomerid()));
							Brand brand = getGoodsBrandByID(customerPushBalance.getBrandid());
							if(null!=brand){
								salesInvoiceDetail.setSupplierid(brand.getSupplierid());
							}
							salesInvoiceDetail.setUnitid("");
							salesInvoiceDetail.setUnitname("");
							salesInvoiceDetail.setUnitnum(BigDecimal.ZERO);
							salesInvoiceDetail.setAuxunitid("");
							salesInvoiceDetail.setAuxunitname("");
							salesInvoiceDetail.setAuxnum(BigDecimal.ZERO);
							salesInvoiceDetail.setAuxnumdetail("");
							
							//退货通知单生成的发票 金额为负
							salesInvoiceDetail.setTaxprice(BigDecimal.ZERO);
							salesInvoiceDetail.setTaxamount(customerPushBalance.getAmount());
							salesInvoiceDetail.setNotaxprice(BigDecimal.ZERO);
							salesInvoiceDetail.setNotaxamount(customerPushBalance.getNotaxamount());
							salesInvoiceDetail.setTaxtype(customerPushBalance.getDefaulttaxtype());
							salesInvoiceDetail.setTax(customerPushBalance.getTax());
							salesInvoiceDetail.setRemark(customerPushBalance.getRemark());
							salesInvoiceDetail.setSeq(999);
							salesInvoiceDetailList.add(salesInvoiceDetail);
						}
					}else if("4".equals(billtype)){
						BeginAmount beginAmount = beginAmountMapper.getBeginAmountByID(id);
						if(null!=beginAmount){
							salesInvoiceDetail = new SalesInvoiceDetail();
							salesInvoiceDetail.setBillid(salesInvoice.getId());
							//来源销售退货通知单
							salesInvoiceDetail.setSourcetype("4");
							salesInvoiceDetail.setSourceid(beginAmount.getId());
							salesInvoiceDetail.setSourcedetailid(beginAmount.getId());
							salesInvoiceDetail.setCustomerid(beginAmount.getCustomerid());
							salesInvoiceDetail.setPcustomerid(beginAmount.getPcustomerid()!=null?beginAmount.getPcustomerid():"");
							salesInvoiceDetail.setCustomersort(beginAmount.getCustomersort()!=null?beginAmount.getCustomersort():"");
							salesInvoiceDetail.setSalesarea(beginAmount.getSalesarea()!=null?beginAmount.getSalesarea():"");
							salesInvoiceDetail.setSalesdept(beginAmount.getSalesdept()!=null?beginAmount.getSalesdept():"");
							salesInvoiceDetail.setSalesuser(beginAmount.getSalesuser()!=null?beginAmount.getSalesuser():"");
							salesInvoiceDetail.setGoodsid("QC");
							salesInvoiceDetail.setBranddept("");
							
							salesInvoiceDetail.setBrandid("QC");
							salesInvoiceDetail.setBranduser("QC");
							salesInvoiceDetail.setSupplieruser("QC");
							//商品默认供应商
							salesInvoiceDetail.setSupplierid("QC");
							salesInvoiceDetail.setUnitid("");
							salesInvoiceDetail.setUnitname("");
							salesInvoiceDetail.setUnitnum(BigDecimal.ZERO);
							salesInvoiceDetail.setAuxunitid("");
							salesInvoiceDetail.setAuxunitname("");
							salesInvoiceDetail.setAuxnum(BigDecimal.ZERO);
							salesInvoiceDetail.setAuxnumdetail("");
							
							//退货通知单生成的发票 金额为负
							salesInvoiceDetail.setTaxprice(BigDecimal.ZERO);
							BigDecimal taxamount=beginAmount.getAmount();
							salesInvoiceDetail.setTaxamount(taxamount);
							salesInvoiceDetail.setNotaxprice(BigDecimal.ZERO);

							//取系统默认税种
							String taxtype = "";
							SysParam taxtypeParam = getBaseSysParamMapper().getSysParam("DEFAULTAXTYPE");
							if(null != taxtypeParam){
								taxtype = taxtypeParam.getPvalue();
							}
							salesInvoiceDetail.setTaxtype(taxtype);
							
							//重新计算税额税额
							BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, salesInvoiceDetail.getTaxtype());
							salesInvoiceDetail.setNotaxamount(notaxamount);
							salesInvoiceDetail.setTax(salesInvoiceDetail.getTaxamount().subtract(salesInvoiceDetail.getNotaxamount()));
							salesInvoiceDetail.setRemark(beginAmount.getRemark());
							salesInvoiceDetail.setSeq(999);
							salesInvoiceDetailList.add(salesInvoiceDetail);
						}
					}
				}
				
				if(salesInvoiceDetailList.size()<500){
					salesInvoiceMapper.addSalesInvoiceDetailList(salesInvoiceDetailList);
				}else{
					int num = salesInvoiceDetailList.size()/500 +1;
					for(int i=1;i<=num;i++){
						if(i<num){
							List addList = new ArrayList(salesInvoiceDetailList.subList((i-1)*500, i*500));
							if(null!=addList && addList.size()>0){
								salesInvoiceMapper.addSalesInvoiceDetailList(addList);
							}
						}else{
							List addList = new ArrayList(salesInvoiceDetailList.subList((i-1)*500, salesInvoiceDetailList.size()));
							if(null!=addList && addList.size()>0){
								salesInvoiceMapper.addSalesInvoiceDetailList(addList);
							}
						}
					}
				}
				
				SysUser sysUser = getSysUser();
				salesInvoice.setAdddeptid(sysUser.getDepartmentid());
				salesInvoice.setAdddeptname(sysUser.getDepartmentname());
				salesInvoice.setAdduserid(sysUser.getUserid());
				salesInvoice.setAddusername(sysUser.getName());
				//含税总金额
				BigDecimal alltaxamount = new BigDecimal(0);
				//未税总金额
				BigDecimal allnotaxamount = new BigDecimal(0);
				//应收总金额
				BigDecimal invoiceamount =  new BigDecimal(0);
				List<SalesInvoiceDetail> newSalesInvoiceList = salesInvoiceMapper.getSalesInvoiceDetailList(salesInvoice.getId());
				for(SalesInvoiceDetail salesInvoiceDetail : newSalesInvoiceList){
					alltaxamount = alltaxamount.add(salesInvoiceDetail.getTaxamount());
					allnotaxamount = allnotaxamount.add(salesInvoiceDetail.getNotaxamount());
					invoiceamount = invoiceamount.add(salesInvoiceDetail.getTaxamount());
				}
				salesInvoice.setTaxamount(alltaxamount);
				salesInvoice.setNotaxamount(allnotaxamount);
				salesInvoice.setInvoiceamount(invoiceamount);
				salesInvoice.setSourceid(salesInvoice.getSourceid()+","+sourceids);
				int i = salesInvoiceMapper.editSalesInvoice(salesInvoice);
				flag = i>0;
				if(flag){
//					List sourceidList = salesInvoiceMapper.getSalesInvoiceSouceidList(salesInvoice.getId());
					List sourceidList = new ArrayList();
					if(null!=sourceids){
						String[] sourceidArr = sourceids.split(",");
						for(String sourceid : sourceidArr){
							sourceidList.add(sourceid);
						}
						salesOutService.updateReceiptAndRejectBillInvoice(sourceidList,salesInvoice,"1");
					}
				}
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", salesInvoiceid);
		map.put("msg", msg);
		return map;
	}
	@Override
	public Map getSalesInvoiceInfo(String id) throws Exception {
		Map map = new HashMap();
		SalesInvoice salesInvoice = salesInvoiceMapper.getSalesInvoiceInfo(id);
		if(null!=salesInvoice){
			CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(salesInvoice.getCustomerid());
			if(null!=customerCapital){
				salesInvoice.setCustomeramount(customerCapital.getAmount());
			}else{
				salesInvoice.setCustomeramount(BigDecimal.ZERO);
			}
			Customer customer = getCustomerByID(salesInvoice.getCustomerid());
			if(null!=customer){
				salesInvoice.setCustomername(customer.getName());
			}
			Contacter contacter = getContacterById(salesInvoice.getHandlerid());
			if(null!=contacter){
				salesInvoice.setHandlername(contacter.getName());
			}
			Personnel personnel = getPersonnelById(salesInvoice.getSalesuser());
			if(null!=personnel){
				salesInvoice.setSalesusername(personnel.getName());
			}
			List<SalesInvoiceDetail> detailList = salesInvoiceMapper.getSalesInvoiceDetailListSumBranddiscount(id);
			for(SalesInvoiceDetail salesInvoiceDetail : detailList){
				if("3".equals(salesInvoiceDetail.getSourcetype())){
					GoodsInfo goodsInfo = new GoodsInfo();
					Brand brand = getGoodsBrandByID(salesInvoiceDetail.getBrandid());
					if(null!=brand){
						goodsInfo.setName("(折扣)"+brand.getName());
						salesInvoiceDetail.setGoodsInfo(goodsInfo);
						salesInvoiceDetail.setUnitnum(new BigDecimal(1));
						salesInvoiceDetail.setTaxprice(salesInvoiceDetail.getTaxamount());
						salesInvoiceDetail.setNotaxprice(salesInvoiceDetail.getNotaxamount());
						TaxType taxType = getTaxType(salesInvoiceDetail.getTaxtype());
						if(null!=taxType){
							salesInvoiceDetail.setTaxtypename(taxType.getName());
						}
					}
				}else if("4".equals(salesInvoiceDetail.getSourcetype())){
					GoodsInfo goodsInfo = new GoodsInfo();
					goodsInfo.setName("应收款期初");
					salesInvoiceDetail.setGoodsInfo(goodsInfo);
					salesInvoiceDetail.setUnitnum(new BigDecimal(1));
					salesInvoiceDetail.setTaxprice(salesInvoiceDetail.getTaxamount());
					salesInvoiceDetail.setNotaxprice(salesInvoiceDetail.getNotaxamount());
					TaxType taxType = getTaxType(salesInvoiceDetail.getTaxtype());
					if(null!=taxType){
						salesInvoiceDetail.setTaxtypename(taxType.getName());
					}
				}else{
					TaxType taxType = getTaxType(salesInvoiceDetail.getTaxtype());
					if(null!=taxType){
						salesInvoiceDetail.setTaxtypename(taxType.getName());
					}
					Map auxmap = countGoodsInfoNumber(salesInvoiceDetail.getGoodsid(), salesInvoiceDetail.getAuxunitid(), salesInvoiceDetail.getUnitnum());
					String auxnumdetail = (String) auxmap.get("auxnumdetail");
					salesInvoiceDetail.setAuxnumdetail(auxnumdetail);
					
					//折扣显示处理
					GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(getAllGoodsInfoByID(salesInvoiceDetail.getGoodsid()));
					if("1".equals(salesInvoiceDetail.getIsdiscount())){
						goodsInfo.setBarcode(null);
						goodsInfo.setBoxnum(null);
						goodsInfo.setName("(折扣)"+goodsInfo.getName());
						//salesInvoiceDetail.setUnitnum(null);
						salesInvoiceDetail.setUnitnum(new BigDecimal(1));
						salesInvoiceDetail.setAuxnumdetail(null);
						salesInvoiceDetail.setTaxprice(null);
						if("1".equals(salesInvoiceDetail.getIsbranddiscount())){
							salesInvoiceDetail.setGoodsid("");
							goodsInfo.setName("(折扣)"+goodsInfo.getBrandName());
//							salesInvoiceDetail.setIsdiscount("2");
						}
					}
					salesInvoiceDetail.setGoodsInfo(goodsInfo);
				}
				//返利折扣
				if("2".equals(salesInvoiceDetail.getIsdiscount())){
					GoodsInfo goodsInfo = new GoodsInfo();
					goodsInfo.setName("折扣");
					salesInvoiceDetail.setGoodsInfo(goodsInfo);
					salesInvoiceDetail.setUnitnum(new BigDecimal(1));
				}
                if(null != salesInvoiceDetail.getGoodsInfo()){
                    BigDecimal boxnum = null != salesInvoiceDetail.getGoodsInfo().getBoxnum() ? salesInvoiceDetail.getGoodsInfo().getBoxnum() : BigDecimal.ZERO;
                    BigDecimal taxprice = null != salesInvoiceDetail.getTaxprice() ? salesInvoiceDetail.getTaxprice() : BigDecimal.ZERO;
                    BigDecimal boxprice = boxnum.multiply(taxprice);
                    salesInvoiceDetail.setBoxprice(boxprice);
                }

			}
			List<String> customerList = salesInvoiceMapper.getSalesInvoiceCustomerList(id);
			boolean isHeader = true;
			String customerids = "";
			List customerArr = new ArrayList();
			for(String cusotmerid : customerList){
				if(cusotmerid.equals(salesInvoice.getCustomerid())){
					isHeader = false;
				}
				Customer newcustomer = getCustomerByID(cusotmerid);
				customerArr.add(newcustomer);
			}
			if(isHeader){
				Customer newcustomer = (Customer) CommonUtils.deepCopy(getCustomerByID(salesInvoice.getCustomerid())) ;
				String invoicecustomername = salesInvoiceMapper.getSalesInvoiceNameByCustomerid(salesInvoice.getCustomerid());
				if(StringUtils.isNotEmpty(invoicecustomername)){
					newcustomer.setName(invoicecustomername);
				}
				customerArr.add(newcustomer);
			}
			map.put("salesInvoice", salesInvoice);
			map.put("detailList", detailList);
			map.put("customerArr", customerArr);
		}
		return map;
	}

	@Override
	public PageData showSalesInvoiceList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_account_sales_invoice", "t");
		pageMap.setDataSql(dataSql);
		String dataSqlPush = getDataAccessRule("t_account_customer_push_balance", "t");
		pageMap.getCondition().put("dataSqlPush", dataSqlPush);
		List<SalesInvoice> list = salesInvoiceMapper.showSalesInvoiceList(pageMap);
        Map condition = pageMap.getCondition();
        //对存在多个来源单据编号的进行查询
        if(condition.containsKey("stockid") && list.size() == 0){
            //从回单中查询销售订单编号
            Receipt receipt = receiptMapper.getReceiptBySaleorderid((String) condition.get("stockid"));
            if(null != receipt){
                condition.remove("stockid");
                condition.put("billid",receipt.getId());
                pageMap.setCondition(condition);
                list = salesInvoiceMapper.showSalesInvoiceList(pageMap);
            }else{
                //从退货入库单中查退货通知单编号
                SaleRejectEnter saleRejectEnter = getBaseSaleRejectEnterMapper().getSaleRejectEnterByID((String) condition.get("stockid"));
                if(null != saleRejectEnter){
                    condition.remove("stockid");
                    condition.put("billid",saleRejectEnter.getSourceid());
                    pageMap.setCondition(condition);
                    list = salesInvoiceMapper.showSalesInvoiceList(pageMap);
                }
            }
        }

		BigDecimal totalCstAmount = new BigDecimal(0);//总账户余额
		for(SalesInvoice salesInvoice : list){
			CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(salesInvoice.getCustomerid());
			if(null!=customerCapital){
				salesInvoice.setCustomeramount(customerCapital.getAmount());
				totalCstAmount = totalCstAmount.add(salesInvoice.getCustomeramount());
			}else{
				salesInvoice.setCustomeramount(BigDecimal.ZERO);
			}
			Customer customer = getCustomerByID(salesInvoice.getCustomerid());
			if(null!=customer){
				salesInvoice.setCustomername(customer.getName());
			}
			if(null!=salesInvoice.getChlidcustomerid()){
				String[] childcustomer = salesInvoice.getChlidcustomerid().split(",");
				String childcustomername = null;
				for(String customerid : childcustomer){
					Customer chlidcustomer = getCustomerByID(customerid);
					if(null!=chlidcustomer){
						if(null==childcustomername){
							childcustomername = chlidcustomer.getName();
						}else{
							childcustomername += ","+chlidcustomer.getName();
						}
					}
				}
				salesInvoice.setChlidcustomername(childcustomername);
			}
			Contacter contacter = getContacterById(salesInvoice.getHandlerid());
			if(null!=contacter){
				salesInvoice.setHandlername(contacter.getName());
			}
			DepartMent departMent = getDepartmentByDeptid(salesInvoice.getSalesdept());
			if(null!=departMent){
				salesInvoice.setSalesdeptname(departMent.getName());
			}
			Personnel personnel = getPersonnelById(salesInvoice.getSalesuser());
			if(null!=personnel){
				salesInvoice.setSalesusername(personnel.getName());
			}
		}
		PageData pageData = new PageData(salesInvoiceMapper.showSalesInvoiceCount(pageMap),list,pageMap);
		
		//合计
		SalesInvoice salesInvoice = salesInvoiceMapper.getSalesInvoiceDataSum(pageMap);
		if(null!=salesInvoice){
			List footer = new ArrayList();
//			SalesInvoice salesInvoice2 = new SalesInvoice();
//			salesInvoice2.setId("选中金额");
//			salesInvoice2.setCustomeramount(BigDecimal.ZERO);
//			footer.add(salesInvoice2);
			
			salesInvoice.setId("合计");
			footer.add(salesInvoice);
			pageData.setFooter(footer);
			
		}
		return pageData;
	}
	
	@Override
	public PageData getTailamountReportPageData(PageMap pageMap) throws Exception {
		List<SalesInvoice> list = salesInvoiceMapper.getTailamountReportData(pageMap);
		for(SalesInvoice salesInvoice : list){
			Customer customer = getCustomerByID(salesInvoice.getCustomerid());
			if(null!=customer){
				salesInvoice.setCustomername(customer.getName());
				if(StringUtils.isNotEmpty(customer.getSalesarea())){
					SalesArea salesArea = getSalesareaByID(customer.getSalesarea());
					if(null != salesArea){
						salesInvoice.setSalesareaname(salesArea.getThisname());
					}
				}
			}
			if(null!=salesInvoice.getChlidcustomerid()){
				String[] childcustomer = salesInvoice.getChlidcustomerid().split(",");
				String childcustomername = null;
				for(String customerid : childcustomer){
					Customer chlidcustomer = getCustomerByID(customerid);
					if(null!=chlidcustomer){
						if(null==childcustomername){
							childcustomername = chlidcustomer.getName();
						}else{
							childcustomername += ","+chlidcustomer.getName();
						}
					}
				}
				salesInvoice.setChlidcustomername(childcustomername);
			}
			if(StringUtils.isNotEmpty(salesInvoice.getHandlerid())){
				Contacter contacter = getContacterById(salesInvoice.getHandlerid());
				if(null!=contacter){
					salesInvoice.setHandlername(contacter.getName());
				}
			}
			if(StringUtils.isNotEmpty(salesInvoice.getSalesdept())){
				DepartMent departMent = getDepartmentByDeptid(salesInvoice.getSalesdept());
				if(null!=departMent){
					salesInvoice.setSalesdeptname(departMent.getName());
				}
			}
			if(StringUtils.isNotEmpty(salesInvoice.getSalesuser())){
				Personnel personnel = getPersonnelById(salesInvoice.getSalesuser());
				if(null!=personnel){
					salesInvoice.setSalesusername(personnel.getName());
				}
			}
		}
		PageData pageData = new PageData(salesInvoiceMapper.getTailamountReportCount(pageMap),list,pageMap);
		
		//合计
		List<SalesInvoice> footer = salesInvoiceMapper.getTailamountReportSumData(pageMap);
		if(null != footer && footer.size() != 0){
			for(SalesInvoice salesInvoice : footer){
				if(null != salesInvoice){
					salesInvoice.setCustomername("合计");
				}
			}
			pageData.setFooter(footer);
		}
		return pageData;
	}

	@Override
	public boolean deletesalesInvoice(String id) throws Exception {
		SalesInvoice salesInvoice = salesInvoiceMapper.getSalesInvoiceInfo(id);
		if(null==salesInvoice){
			return false;
		}
		boolean flag = false;
		if("1".equals(salesInvoice.getStatus()) || "2".equals(salesInvoice.getStatus()) || "5".equals(salesInvoice.getStatus())){
			List<String> sourceidList = salesInvoiceMapper.getSalesInvoiceSouceidList(salesInvoice.getId());
			//先更新回单以及上游单据开票状态
			salesOutService.updateReceiptAndRejectBillInvoice(sourceidList,salesInvoice,"0");
			//删除销售发票明细
			int i = salesInvoiceMapper.deleteSalesInvoice(id);
			salesInvoiceMapper.deleteSalesInvoiceDetail(id);
			customerPushBalanceMapper.deleteCustomerPushBanlanceByInvoiceid(id);
//			salesOutService.updateReceiptAndRejectBillInvoiceByDelete(sourceidList, salesInvoice);
			flag = i>0;
			if(flag){
//				if(StringUtils.isNotEmpty(salesInvoice.getSourceid())){
//					 String[] sourceidArr = salesInvoice.getSourceid().split(",");
//					 for(String billid : sourceidArr){
//						 Receipt receipt = salesOutService.getReceiptInfo(billid);
//						 if(null != receipt){
//							 //清除发票号
//							 storageSaleService.clearSaleoutInvoiceidByReceiptid(receipt.getId());
//							 storageSaleService.clearSaleRejectEnterInvoiceidByReceiptid(receipt.getId());
//						 }else{
//							 RejectBill rejectBill = salesOutService.getRejectBillInfo(billid);
//							 if(null != rejectBill){
//								 storageSaleService.clearSaleRejectEnterInvoiceidByRejectbillid(rejectBill.getId());
//							 }
//						 }
//					 }
//				 }
				//销售核销删除清除销售开票单据关联的销售核销编号
				if(StringUtils.isNotEmpty(salesInvoice.getSalesinvoicebillid())){
					Map map = new HashMap();
					map.put("salesinvoiceid","");
					map.put("invoicebillid",salesInvoice.getSalesinvoicebillid());
					salesInvoiceBillMapper.updateSalesInvoiceBillBack(map);
				}
			}
		}
		return flag;
	}

	@Override
	public Map editSalesInvoice(SalesInvoice salesInvoice,String delgoodsids) throws Exception {
		
		boolean flag = false;
		String msg = "";
		SalesInvoice oldSalesInvoice = salesInvoiceMapper.getSalesInvoiceInfo(salesInvoice.getId());
		if(null != oldSalesInvoice && ("2".equals(oldSalesInvoice.getStatus()) || "1".equals(oldSalesInvoice.getStatus()) || "5".equals(oldSalesInvoice.getStatus()))){
						
			String[] goodsidArr = delgoodsids.split(",");
			//明细删除前 更新上游单据开票状态
			if(!"".equals(delgoodsids) && null!=goodsidArr && goodsidArr.length>0){
				List sourceidList = salesInvoiceMapper.getSalesInvoiceSouceidList(salesInvoice.getId());
				salesOutService.updateReceiptAndRejectBillInvoice(sourceidList,salesInvoice,"0");
			}
			for(String goodsid : goodsidArr){
				
				if(!"FL".equals(goodsid)){
					//更新正常冲差单的 与发票关联状态
					customerPushBalanceMapper.updateCustomerPushBanlanceIsReferByinvoiceidAndBrandid(salesInvoice.getId(), goodsid);
					//删除相关冲差单
					customerPushBalanceMapper.deleteCustomerPushBanlanceByinvoiceidAndBrandid(salesInvoice.getId(), goodsid);
				}else{
					customerPushBalanceMapper.deleteCustomerPushBanlanceByInvoiceIsrebate(salesInvoice.getId());
				}
				salesInvoiceMapper.deleteSalesInvoiceDetailByGoodsid(salesInvoice.getId(), goodsid);
			}
			List<SalesInvoiceDetail> detailList = salesInvoiceMapper.getSalesInvoiceDetailList(salesInvoice.getId());
			BigDecimal invoiceAmount = new BigDecimal(0);
			BigDecimal taxamount = BigDecimal.ZERO;
			BigDecimal notaxamount = BigDecimal.ZERO;
			
			for(SalesInvoiceDetail salesInvoiceDetail : detailList){
				salesInvoiceDetail.setBillid(salesInvoice.getId());
				//商品品牌 和品牌业务员信息
				GoodsInfo goodsInfo = getAllGoodsInfoByID(salesInvoiceDetail.getGoodsid());
				if(null!=goodsInfo){
					salesInvoiceDetail.setGoodssort(goodsInfo.getDefaultsort());
					salesInvoiceDetail.setBrandid(goodsInfo.getBrand());
					salesInvoiceDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceDetail.getCustomerid()));
					//厂家业务员
					salesInvoiceDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceDetail.getCustomerid()));
					//默认供应商
					salesInvoiceDetail.setSupplierid(goodsInfo.getDefaultsupplier());
				}
				taxamount = taxamount.add(salesInvoiceDetail.getTaxamount());
				notaxamount = notaxamount.add(salesInvoiceDetail.getNotaxamount());
				invoiceAmount = invoiceAmount.add(salesInvoiceDetail.getTaxamount());
			}
			List<String> sourceidList = salesInvoiceMapper.getSalesInvoiceSouceidList(salesInvoice.getId());
			String billsourceid = "";
			for(String sourceid : sourceidList){
				if("".equals(billsourceid)){
					billsourceid = sourceid;
				}else{
					billsourceid += ","+sourceid;
				}
			}
            salesInvoice.setStatus("2");
			salesInvoice.setSourceid(billsourceid);
			salesInvoice.setTaxamount(taxamount);
			salesInvoice.setNotaxamount(notaxamount);
			salesInvoice.setInvoiceamount(invoiceAmount);
			SysUser sysUser = getSysUser();
			salesInvoice.setModifyuserid(sysUser.getUserid());
			salesInvoice.setModifyusername(sysUser.getName());
			if("5".equals(salesInvoice.getStatus())){
				salesInvoice.setStatus("2");
			}
			int i = salesInvoiceMapper.editSalesInvoice(salesInvoice);
			flag = i>0;
			if(flag && ((!"".equals(delgoodsids) && null!=goodsidArr && goodsidArr.length>0) || null!=salesInvoice.getInvoiceno() && !"".equals(salesInvoice.getInvoiceno()))){
				salesOutService.updateReceiptAndRejectBillInvoice(sourceidList,salesInvoice,"1");
			}
		}else{
			msg = "销售发票已审核或者核销。不能进行保存。";	
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("id", salesInvoice.getId());
		return map;
	}

	@Override
	public boolean auditSalesInvoice(String id) throws Exception {
		SalesInvoice salesInvoice = salesInvoiceMapper.getSalesInvoiceInfo(id);
		boolean flag = false;
		if("2".equals(salesInvoice.getStatus()) || "6".equals(salesInvoice.getStatus())){
			//审核业务日期
			String auditBusinessdate = getAuditBusinessdate(salesInvoice.getBusinessdate());
			SysUser sysUser = getSysUser();
			int i = salesInvoiceMapper.auditSalesInvoice(id, sysUser.getUserid(), sysUser.getName(),auditBusinessdate);
			flag = i>0;
			//如果销售发票是折扣 根据折扣生成应收款冲差单
			if(flag){
				List<SalesInvoiceDetail> list = salesInvoiceMapper.getSalesInvoiceDetailList(id);
				for(SalesInvoiceDetail salesInvoiceDetail : list){
					//明细来源冲差时，审核通过后 同时审核通过冲差单
					if("3".equals(salesInvoiceDetail.getSourcetype())){
						customerPushBalanceMapper.auditCustomerPushBanlanceByinvoiceid(salesInvoice.getId(), sysUser.getUserid(), sysUser.getName());
						break;
					}
				}
			}
		}
		return flag;
	}

	@Override
	public boolean oppauditSalesInvoice(String id) throws Exception {
		SalesInvoice salesInvoice = salesInvoiceMapper.getSalesInvoiceInfo(id);
		if(null != salesInvoice){
			salesInvoice.setBilltype("1");
		}else{
			salesInvoice.setBilltype("2");
		}
		salesStatementService.deleteRelateCollectionOrder(id, salesInvoice.getBilltype());
		boolean flag = false;
		if("3".equals(salesInvoice.getStatus())){
			SysUser sysUser = getSysUser();
			int i = salesInvoiceMapper.oppauditSalesInvoice(id, sysUser.getUserid(), sysUser.getName());
			flag = i>0;
			if(flag){
				List<SalesInvoiceDetail> list = salesInvoiceMapper.getSalesInvoiceDetailList(id);
				for(SalesInvoiceDetail salesInvoiceDetail : list){
					//明细来源冲差时，反审通过后 同时反审通过冲差单
					if("3".equals(salesInvoiceDetail.getSourcetype())){
						customerPushBalanceMapper.oppauditCustomerPushBanlanceByinvoiceid(salesInvoice.getId(), sysUser.getUserid(), sysUser.getName());
						break;
					}
				}
			}
		}
		return flag;
	}

	@Override
	public List showSalesInvoiceListByCustomerid(String customerid)
			throws Exception {
		List<SalesInvoice> list = salesInvoiceMapper.showSalesInvoiceListByCustomerid(customerid);
		for(SalesInvoice salesInvoice : list){
			salesInvoice.setBilltype("1");
			Customer customer = getCustomerByID(salesInvoice.getCustomerid());
			if(null!=customer){
				salesInvoice.setCustomername(customer.getName());
			}
			Contacter contacter = getContacterById(salesInvoice.getHandlerid());
			if(null!=contacter){
				salesInvoice.setHandlername(contacter.getName());
			}
			DepartMent departMent = getDepartmentByDeptid(salesInvoice.getSalesdept());
			if(null!=departMent){
				salesInvoice.setSalesdeptname(departMent.getName());
			}
			Personnel personnel = getPersonnelById(salesInvoice.getSalesuser());
			if(null!=personnel){
				salesInvoice.setSalesusername(personnel.getName());
			}
		}
//		List<CustomerPushBalance> pushBalanceList = customerPushBalanceMapper.getCustomerPushBanlanceListByCustomerid(customerid);
//		for(CustomerPushBalance customerPushBalance : pushBalanceList){
//			SalesInvoice salesInvoice = new SalesInvoice();
//			salesInvoice.setBilltype("2");
//			salesInvoice.setId(customerPushBalance.getId());
//			salesInvoice.setCustomerid(customerPushBalance.getCustomerid());
//			salesInvoice.setBusinessdate(customerPushBalance.getBusinessdate());
//			Customer customer = getCustomerByID(salesInvoice.getCustomerid());
//			if(null!=customer){
//				salesInvoice.setCustomername(customer.getName());
//			}
//			salesInvoice.setTaxamount(customerPushBalance.getAmount());
//			salesInvoice.setInvoiceamount(customerPushBalance.getAmount());
//			list.add(salesInvoice);
//		}
		return list;
	}

	@Override
	public List getSalesInvoiceListByInvoiceids(String invoiceid)
			throws Exception {
		List list = new ArrayList();
		if(null!=invoiceid && !"".equals(invoiceid)){
			String[] invoiceidArr = invoiceid.split(",");
			for(String id : invoiceidArr){
				SalesInvoice salesInvoice = salesInvoiceMapper.getSalesInvoiceInfo(id);
				if(null==salesInvoice){
					CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(id);
					salesInvoice = new SalesInvoice();
					salesInvoice.setBilltype("2");
					salesInvoice.setId(customerPushBalance.getId());
					salesInvoice.setCustomerid(customerPushBalance.getCustomerid());
					salesInvoice.setBusinessdate(customerPushBalance.getBusinessdate());
					salesInvoice.setTaxamount(customerPushBalance.getAmount());
					if("1".equals(customerPushBalance.getIsrelate())){
						BigDecimal invoiceamount = salesStatementMapper.getRelateCollectionOrderInvoiceAmount(customerPushBalance.getId());
						salesInvoice.setInvoiceamount(invoiceamount);
					}else{
						salesInvoice.setInvoiceamount(customerPushBalance.getAmount());
					}
					salesInvoice.setIsrelate(customerPushBalance.getIsrelate());
				}else{
					if("1".equals(salesInvoice.getIsrelate())){
						BigDecimal invoiceamount = salesStatementMapper.getRelateCollectionOrderInvoiceAmount(salesInvoice.getId());
						salesInvoice.setInvoiceamount(invoiceamount);
					}
					salesInvoice.setBilltype("1");
				}
				list.add(salesInvoice);
			}
		}
		return list;
	}
  
	@Override
	public boolean submitSalesInvoicePageProcess(String id) throws Exception {
		return false;
	}

	@Override
	public List showSalesInvoiceSourceListReferData(String salesinvoiceid,
			String sourcetype) throws Exception {
		String sourceid = null;
		SalesInvoice salesInvoice = salesInvoiceMapper.getSalesInvoiceInfo(salesinvoiceid);
		if(null!=salesInvoice){
			sourceid = salesInvoice.getSourceid();
		}
		if(null!=sourceid){
			String[] ids = sourceid.split(",");
			List list = new ArrayList();
			for(String id : ids){
				//开票金额
				BigDecimal invoiceAmount = salesInvoiceMapper.getSalesInvoiceDetailAmount(salesinvoiceid, id);
				if(null == invoiceAmount){
					invoiceAmount = BigDecimal.ZERO;
				}
				Receipt receipt =  salesOutService.getReceiptInfo(id);
				if(null==receipt){
					RejectBill rejectBill =  salesOutService.getRejectBillInfo(id);
					if(null!=rejectBill){
						rejectBill.setField04(invoiceAmount.toString());
						list.add(rejectBill);
					}else{
						CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(id);
						if(null!=customerPushBalance){
							Receipt cpreceipt = new Receipt();
							cpreceipt.setId(id);
							cpreceipt.setCustomerid(customerPushBalance.getCustomerid());
							Customer customer = getCustomerByID(customerPushBalance.getCustomerid());
							if(null!=customer){
								cpreceipt.setCustomername(customer.getName());
							}
							cpreceipt.setIsinvoice(customerPushBalance.getIsrefer());
							if("1".equals(customerPushBalance.getIswriteoff())){
								cpreceipt.setIsinvoice("2");
							}
							cpreceipt.setIsinvoicebill(customerPushBalance.getIsinvoicebill());
							cpreceipt.setSalesuser(customerPushBalance.getSalesuser());
							Personnel personnel = getPersonnelById(customerPushBalance.getSalesuser());
							if(null!=personnel){
								cpreceipt.setSalesusername(personnel.getName());
							}
							cpreceipt.setField01(customerPushBalance.getAmount().toString());
							cpreceipt.setField04(invoiceAmount.toString());
							cpreceipt.setBusinessdate(customerPushBalance.getBusinessdate());
							cpreceipt.setAddusername(customerPushBalance.getAddusername());
							cpreceipt.setStatus(customerPushBalance.getStatus());
							list.add(cpreceipt);
						}else{
							BeginAmount beginAmount = beginAmountMapper.getBeginAmountByID(id);
							if(null!=beginAmount){
								Receipt cpreceipt = new Receipt();
								cpreceipt.setId(id);
								cpreceipt.setCustomerid(beginAmount.getCustomerid());
								Customer customer = getCustomerByID(beginAmount.getCustomerid());
								if(null!=customer){
									cpreceipt.setCustomername(customer.getName());
								}
								if("1".equals(beginAmount.getIsinvoice())){
									cpreceipt.setIsinvoice("1");
								}
								if("1".equals(beginAmount.getIswriteoff())){
									cpreceipt.setIsinvoice("2");
								}
								cpreceipt.setSalesuser(beginAmount.getSalesuser());
								Personnel personnel = getPersonnelById(beginAmount.getSalesuser());
								if(null!=personnel){
									cpreceipt.setSalesusername(personnel.getName());
								}
								cpreceipt.setField01(beginAmount.getAmount().toString());
								cpreceipt.setField04(invoiceAmount.toString());
								cpreceipt.setBusinessdate(beginAmount.getBusinessdate());
								cpreceipt.setAddusername(beginAmount.getAddusername());
								cpreceipt.setStatus(beginAmount.getStatus());
								list.add(cpreceipt);
							}
						}
					}
					
				}else{
					receipt.setField04(invoiceAmount.toString());
					list.add(receipt);
				}
			}
			return list;
		}
		return null;
	}
	
	@Override
	public List showSalesInvoicePrintListBy(Map map) throws Exception{
		PageMap pageMap=new PageMap();
		pageMap.setCondition(map);
		String dataSql = getDataAccessRule("t_account_sales_invoice", null);
		pageMap.setDataSql(dataSql);

		boolean showdetail=false;
		if(map.containsKey("showdetail") && null!=map.get("showdetail") && "1".equals(map.get("showdetail").toString())){
			showdetail=true;
		}
		
		String isShowListDataForPrint=(String)map.get("isShowListDataForPrint");

		String neggoodstodiscount=(String) map.get("neggoodstodiscount");
		String zkgoodstodiscount=(String) map.get("zkgoodstodiscount");
		String isShowDiscountSum=(String) map.get("isShowDiscountSum");
		
		List<SalesInvoice> list = salesInvoiceMapper.showSalesInvoiceList(pageMap);
		for(SalesInvoice salesInvoice : list){
			Customer customer = getCustomerByID(salesInvoice.getCustomerid());
			if(null!=customer){
				salesInvoice.setCustomername(customer.getName());
			}
			Contacter contacter = getContacterById(salesInvoice.getHandlerid());
			if(null!=contacter){
				salesInvoice.setHandlername(contacter.getName());
			}
			DepartMent departMent = getDepartmentByDeptid(salesInvoice.getSalesdept());
			if(null!=departMent){
				salesInvoice.setSalesdeptname(departMent.getName());
			}
			Personnel personnel = getPersonnelById(salesInvoice.getSalesuser());
			if(null!=personnel){
				salesInvoice.setSalesusername(personnel.getName());
			}
			
			if(showdetail){
				Map queryMap=new HashMap();
				queryMap.put("billid", salesInvoice.getId());
				queryMap.put("orderby", "print");
				List<SalesInvoiceDetail> detailList = salesInvoiceMapper.getSalesInvoiceDetailListSumBranddiscountForPrint(queryMap);
				List<SalesInvoiceDetail> tmpList=new ArrayList<SalesInvoiceDetail>();
				SalesInvoiceDetail discountSum=new SalesInvoiceDetail();
				SalesInvoiceDetail salesInvoiceDetailSum=new SalesInvoiceDetail();
				TaxType aTaxType=null;
				for(SalesInvoiceDetail salesInvoiceDetail : detailList){
					//打印时，金额为零
					if(null!=isShowListDataForPrint && 
							(null == salesInvoiceDetail.getNotaxamount() || BigDecimal.ZERO.compareTo(salesInvoiceDetail.getNotaxamount())==0  )){
						continue;
					}
					TaxType taxType = getTaxType(salesInvoiceDetail.getTaxtype());
					salesInvoiceDetail.setTaxtypename(taxType.getName());					
					salesInvoiceDetail.setTaxTypeInfo(taxType);
					if(null==aTaxType){
						aTaxType=taxType;
					}
					
					if(!"3".equals(salesInvoiceDetail.getSourcetype())){
						//处理不是冲差单的单据
						Map auxmap = countGoodsInfoNumber(salesInvoiceDetail.getGoodsid(), salesInvoiceDetail.getAuxunitid(), salesInvoiceDetail.getUnitnum());
						String auxnumdetail = (String) auxmap.get("auxnumdetail");
						salesInvoiceDetail.setAuxnumdetail(auxnumdetail);
						
						//折扣显示处理
						GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(getAllGoodsInfoByID(salesInvoiceDetail.getGoodsid()));
						if("1".equals(salesInvoiceDetail.getIsdiscount())){
							goodsInfo.setBarcode(null);
							goodsInfo.setBoxnum(null);
							if("1".equals(zkgoodstodiscount)){
								//冲差、折扣是否转成折扣
								goodsInfo.setName("折扣");
							}else{
								goodsInfo.setName("(折扣)"+goodsInfo.getName());								
							}
							if("1".equals(salesInvoiceDetail.getIsbranddiscount())){
								salesInvoiceDetail.setGoodsid("");
								if("1".equals(zkgoodstodiscount)){		
									//冲差、折扣是否转成折扣
									goodsInfo.setName("折扣");
								}else{							
									goodsInfo.setName("(折扣)"+goodsInfo.getBrandName());
								}
							}	
							salesInvoiceDetail.setUnitname(null);
							salesInvoiceDetail.setUnitnum(null);
							salesInvoiceDetail.setTaxprice(null);
							salesInvoiceDetail.setNotaxprice(null);
							salesInvoiceDetail.setAuxnumdetail(null);
							salesInvoiceDetail.setAuxunitname(null);
						}
						//负商品转为折扣
						if("1".equals(neggoodstodiscount) 
								&& "0".equals(salesInvoiceDetail.getIsbranddiscount())
								&& "0".equals(salesInvoiceDetail.getIsdiscount())
								&& BigDecimal.ZERO.compareTo(salesInvoiceDetail.getNotaxamount())>0 ){
							goodsInfo.setBarcode(null);
							goodsInfo.setBoxnum(null);

							//冲差、折扣是否转成折扣
							goodsInfo.setName("折扣");
							
							salesInvoiceDetail.setUnitname(null);
							salesInvoiceDetail.setUnitnum(null);
							salesInvoiceDetail.setTaxprice(null);
							salesInvoiceDetail.setNotaxprice(null);
							salesInvoiceDetail.setAuxnumdetail(null);
							salesInvoiceDetail.setAuxunitname(null);
							//负商品转为折扣
							salesInvoiceDetail.setIsdiscount("1");
						}
						salesInvoiceDetail.setGoodsInfo(goodsInfo);
					}else{
						//冲差单处理
						GoodsInfo goodsInfo = new GoodsInfo();
						Brand brand = getGoodsBrandByID(salesInvoiceDetail.getBrandid());
						if(null!=brand){
							if("1".equals(zkgoodstodiscount)){
								//冲差、折扣是否转成折扣
								goodsInfo.setName("折扣");
							}else{
								goodsInfo.setName("(折扣)"+brand.getName());								
							}
							salesInvoiceDetail.setGoodsInfo(goodsInfo);
						}
						
						salesInvoiceDetail.setUnitname(null);
						salesInvoiceDetail.setUnitnum(null);
						salesInvoiceDetail.setTaxprice(null);
						salesInvoiceDetail.setNotaxprice(null);
						salesInvoiceDetail.setAuxnumdetail(null);
						salesInvoiceDetail.setAuxunitname(null);
						//设置为品牌折扣
						salesInvoiceDetail.setIsbranddiscount("1");					
						
					}
					//返利折扣
					if("2".equals(salesInvoiceDetail.getIsdiscount())){
						GoodsInfo goodsInfo = new GoodsInfo();
						goodsInfo.setName("折扣");
						salesInvoiceDetail.setGoodsInfo(goodsInfo);
						
						salesInvoiceDetail.setUnitname(null);
						salesInvoiceDetail.setUnitnum(null);
						salesInvoiceDetail.setTaxprice(null);
						salesInvoiceDetail.setNotaxprice(null);
						salesInvoiceDetail.setAuxnumdetail(null);
						salesInvoiceDetail.setAuxunitname(null);
					}
					

					salesInvoiceDetail.setIsreportblank(0);
					
					if("1".equals(salesInvoiceDetail.getIsbranddiscount()) || !"0".equals(salesInvoiceDetail.getIsdiscount())){
						if(null==discountSum.getTaxamount()){
							discountSum.setTaxamount(salesInvoiceDetail.getTaxamount());
						}else if(null!=salesInvoiceDetail.getTaxamount()){
							discountSum.setTaxamount(discountSum.getTaxamount().add(salesInvoiceDetail.getTaxamount()));
						}
						//无税金额 = 含税金额/（1+税率） 保存6位小数
						if(null != taxType.getRate()){
						    discountSum.setNotaxamount(discountSum.getTaxamount().divide(taxType.getRate().divide(new BigDecimal(100),6,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1)),decimalLen,BigDecimal.ROUND_HALF_UP));
						    discountSum.setTax(discountSum.getTaxamount().subtract(discountSum.getNotaxamount()));
						}else{
							discountSum.setNotaxamount(BigDecimal.ZERO);
							discountSum.setTax(BigDecimal.ZERO);
						}
					}else{						
						
						if(null==salesInvoiceDetailSum.getTaxamount()){
							salesInvoiceDetailSum.setTaxamount(salesInvoiceDetail.getTaxamount());
						}else if(null!=salesInvoiceDetail.getTaxamount()){
							salesInvoiceDetailSum.setTaxamount(salesInvoiceDetailSum.getTaxamount().add(salesInvoiceDetail.getTaxamount()));
						}
						

						//无税金额 = 含税金额/（1+税率） 保存6位小数
						if(null != taxType.getRate()){
							salesInvoiceDetailSum.setNotaxamount(salesInvoiceDetailSum.getTaxamount().divide(taxType.getRate().divide(new BigDecimal(100),6,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1)),decimalLen,BigDecimal.ROUND_HALF_UP));
							salesInvoiceDetailSum.setTax(salesInvoiceDetailSum.getTaxamount().subtract(salesInvoiceDetailSum.getNotaxamount()));
						}else{
							salesInvoiceDetailSum.setNotaxamount(BigDecimal.ZERO);
							salesInvoiceDetailSum.setTax(BigDecimal.ZERO);
						}
					}
					if("0".equals(salesInvoiceDetail.getIsbranddiscount()) && "0".equals(salesInvoiceDetail.getIsdiscount())){
						tmpList.add(salesInvoiceDetail);
					}else {
						if(!"1".equals(isShowDiscountSum)){
							tmpList.add(salesInvoiceDetail);
						}
					}
				}
                //折扣合并
                if("1".equals(isShowDiscountSum) 
                		&& null!=discountSum.getNotaxamount() 
                		&& BigDecimal.ZERO.compareTo(discountSum.getNotaxamount())!=0){
                	SalesInvoiceDetail detailDiscount=(SalesInvoiceDetail)CommonUtils.deepCopy(discountSum);
                	
                	GoodsInfo goodsInfo=new GoodsInfo();
					goodsInfo.setName("折扣");
					detailDiscount.setGoodsInfo(goodsInfo);
					detailDiscount.setIsdiscount("1");	

					detailDiscount.setTaxtypename(aTaxType.getName());
					detailDiscount.setTaxTypeInfo(aTaxType);
					
					tmpList.add(detailDiscount);
                	
                }
                //原价合计
				if(null!=salesInvoiceDetailSum.getNotaxamount()){
					GoodsInfo goodsInfo=new GoodsInfo();
					goodsInfo.setName("原价合计");
					salesInvoiceDetailSum.setGoodsInfo(goodsInfo);

					salesInvoiceDetailSum.setTaxtypename(aTaxType.getName());
					salesInvoiceDetailSum.setTaxTypeInfo(aTaxType);
					
					salesInvoiceDetailSum.setIsdiscount("-90099");
					
					tmpList.add(salesInvoiceDetailSum);
					
				}
                //折扣额合计
				if(null!=discountSum.getNotaxamount()){
					GoodsInfo goodsInfo=new GoodsInfo();
					goodsInfo.setName("折扣额合计");
					discountSum.setGoodsInfo(goodsInfo);
					discountSum.setIsdiscount("1");

					discountSum.setTaxtypename(aTaxType.getName());
					discountSum.setTaxTypeInfo(aTaxType);
					
					discountSum.setIsdiscount("-90099");
					
					tmpList.add(discountSum);
				}
				

				salesInvoice.setSalesInvoiceDetailList(tmpList);
			}
		}
		return list;
	}
	
	

	@Override
	public List getSalesInvoiceSourceDetailList(String id) throws Exception {
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal totalUnitnum = BigDecimal.ZERO;
		//获取该发票的明细列表
		List<SalesInvoiceDetail> detaiList = salesInvoiceMapper.getSalesInvoiceDetailList(id);
		if(detaiList.size() != 0){
			for(SalesInvoiceDetail salesInvoiceDetail : detaiList){
				GoodsInfo goodsInfo = getGoodsInfoByID(salesInvoiceDetail.getGoodsid());
				salesInvoiceDetail.setGoodsInfo(goodsInfo);
				//税种
				TaxType taxType = getTaxType(salesInvoiceDetail.getTaxtype());
				if(null != taxType){
					salesInvoiceDetail.setTaxtypename(taxType.getName());
				}
				BigDecimal taxamount = (null != salesInvoiceDetail.getTaxamount()) ? salesInvoiceDetail.getTaxamount() : BigDecimal.ZERO;
				totalAmount = totalAmount.add(taxamount);
				BigDecimal unitnum = (null != salesInvoiceDetail.getUnitnum()) ? salesInvoiceDetail.getUnitnum() : BigDecimal.ZERO;
				totalUnitnum = totalUnitnum.add(unitnum);
			}
			SalesInvoiceDetail salesInvoiceDetailSum = new SalesInvoiceDetail();
			salesInvoiceDetailSum.setSourceid("合计");
			salesInvoiceDetailSum.setTaxamount(totalAmount);
			salesInvoiceDetailSum.setUnitnum(totalUnitnum);
			detaiList.add(salesInvoiceDetailSum);
		}
		return detaiList;
	}

	@Override
	public boolean updateOrderPrinttimes(SalesInvoice salesInvoice) throws Exception{
		return salesInvoiceMapper.updateOrderPrinttimes(salesInvoice)>0;
	}
	@Override
	public void updateOrderPrinttimes(List<SalesInvoice> list) throws Exception{
		if(null!=list){
			for(SalesInvoice item : list){
				salesInvoiceMapper.updateOrderPrinttimes(item);
			}
		}		
	}

	@Override
	public boolean updateSalesInvoiceApplyWriteOff(String id) throws Exception {
		SalesInvoice salesInvoice = salesInvoiceMapper.getSalesInvoiceInfo(id);
		boolean flag = false;
		if(null!=salesInvoice && "3".equals(salesInvoice.getStatus()) && "1".equals(salesInvoice.getApplytype())){
			int i = salesInvoiceMapper.updateSalesInvoiceApplyWriteOff(id);
			flag = i>0;
		}
		return flag;
	}

	@Override
	public Map addCustomerPushBanlanceBySalesInvoice(String invoiceid,
			String pushtype, CustomerPushBalance customerPushBalance)
			throws Exception {
		SysUser sysUser = getSysUser();
		boolean flag = false;
		Map returnMap = new HashMap();
		SalesInvoice salesInvoice = salesInvoiceMapper.getSalesInvoiceInfo(invoiceid);
        if(null==salesInvoice || "3".equals(salesInvoice.getStatus()) || "4".equals(salesInvoice.getStatus())){
            returnMap.put("flag", false);
            returnMap.put("pushid", "");
            return returnMap;
        }
		String pushid = "";
		//冲差总金额
		BigDecimal totalpushamount = customerPushBalance.getAmount();
		//1总店冲差 2门店冲差
		if("1".equals(pushtype)){
			List<String> customerList = salesInvoiceMapper.getSalesInvoiceCustomerList(invoiceid);
			//成本总金额
			BigDecimal totalCost = BigDecimal.ZERO;
			Map costMap = new HashMap();
			for(String customerid : customerList){
				BigDecimal costamount = salesInvoiceMapper.getSalesInvoiceCostByCustomerid(invoiceid, customerid,customerPushBalance.getBrandid());
				//存在销售额 成本金额大于0
				if(null!=costamount && costamount.compareTo(BigDecimal.ZERO)!=0){
					totalCost = totalCost.add(costamount);
					costMap.put(customerid, costamount);
				}
			}
			if(totalCost.compareTo(BigDecimal.ZERO)==1){
				BigDecimal totaluseamount = BigDecimal.ZERO;
				for(int i=0;i<customerList.size();i++){
					String customerid = customerList.get(i);
					BigDecimal costamount = (BigDecimal) costMap.get(customerid);
					if(null==costamount){
						costamount = BigDecimal.ZERO;
					}
					//门店客户冲差金额 = （销售成本金额/总销售成本金额）*总冲差金额
					BigDecimal pushamount = costamount.divide(totalCost,6,BigDecimal.ROUND_HALF_UP).multiply(totalpushamount).setScale(2, BigDecimal.ROUND_HALF_UP);
					//最后一个门店 冲差金额 = 总冲差金额 - 已分配冲差金额
					if(i==(customerList.size()-1)){
						pushamount = totalpushamount.subtract(totaluseamount);
					}
					if(null!=pushamount && pushamount.compareTo(BigDecimal.ZERO)!=0){
						CustomerPushBalance customerPushBalance2 = new CustomerPushBalance();
						if (isAutoCreate("t_account_customer_push_balance")) {
							// 获取自动编号
							String id = getAutoCreateSysNumbderForeign(customerPushBalance2, "t_account_customer_push_balance");
							customerPushBalance2.setId(id);
						}else{
							customerPushBalance2.setId("CC-"+CommonUtils.getDataNumberSendsWithRand());
						} 
						customerPushBalance2.setCustomerid(customerid);
						//取上级客户
						Customer headcustomer = salesExtService.getHeadCustomer(customerid);
						if(null!=headcustomer){
							customerPushBalance2.setPcustomerid(headcustomer.getId());
						}
						Customer customer = getCustomerByID(customerid);
						if(null!=customer){
							customerPushBalance2.setSalesarea(customer.getSalesarea());
							customerPushBalance2.setSalesdept(customer.getSalesdeptid());
							customerPushBalance2.setSalesuser(customer.getSalesuserid());
							customerPushBalance2.setPcustomerid(customer.getPid());
							customerPushBalance2.setCustomersort(customer.getCustomersort());
							customerPushBalance2.setIndooruserid(customer.getIndoorstaff());
						}
						customerPushBalance2.setIsinvoice("1");
						customerPushBalance2.setIsrefer("1");
						customerPushBalance2.setInvoiceid(invoiceid);
						customerPushBalance2.setPushtype(customerPushBalance.getPushtype());
						customerPushBalance2.setBrandid(customerPushBalance.getBrandid());
						customerPushBalance2.setBranduser(getBrandUseridByCustomeridAndBrand(customerPushBalance.getBrandid(), customerid));
						//厂家业务员
						customerPushBalance2.setSupplieruser(getSupplieruserByCustomeridAndBrand(customerPushBalance.getBrandid(), customerid));
						//获取品牌部门、税种，取品牌档案中的税种，若品牌档案中没有税种，则取系统默认税种
						Brand brand = getGoodsBrandByID(customerPushBalance.getBrandid());
						if(null!=brand){
							customerPushBalance2.setBranddept(brand.getDeptid());
                            customerPushBalance2.setSupplierid(brand.getSupplierid());
                            customerPushBalance2.setDefaulttaxtype(brand.getDefaulttaxtype());
						}
						if(StringUtils.isEmpty(customerPushBalance2.getDefaulttaxtype())){
							String taxtype = "";
							SysParam taxtypeParam = getBaseSysParamMapper().getSysParam("DEFAULTAXTYPE");
							if(null != taxtypeParam){
								taxtype = taxtypeParam.getPvalue();
							}
							customerPushBalance2.setDefaulttaxtype(taxtype);
						}
						customerPushBalance2.setBusinessdate(customerPushBalance.getBusinessdate());
						customerPushBalance2.setSubject(customerPushBalance.getSubject());
						
						totaluseamount = totaluseamount.add(pushamount);
						customerPushBalance2.setAmount(pushamount);

                        //计算冲差未税金额、冲差税额
                        BigDecimal notaxamount = BigDecimal.ZERO;
                        if(StringUtils.isNotEmpty(customerPushBalance2.getDefaulttaxtype())){
                            notaxamount = getNotaxAmountByTaxAmount(pushamount,customerPushBalance2.getDefaulttaxtype());
                        }else{
                            notaxamount = getNotaxAmountByTaxAmount(pushamount,null);
                        }
                        customerPushBalance2.setNotaxamount(notaxamount);
                        customerPushBalance2.setTax(customerPushBalance2.getAmount().subtract(customerPushBalance2.getNotaxamount()));

						customerPushBalance2.setAdduserid(sysUser.getUserid());
						customerPushBalance2.setAddusername(sysUser.getName());
						customerPushBalance2.setAdddeptid(sysUser.getDepartmentid());
						customerPushBalance2.setAdddeptname(sysUser.getDepartmentname());
						customerPushBalance2.setStatus("2");
//						customerPushBalance2.setInitsendamount(pushamount);
						customerPushBalance2.setSendamount(pushamount);
						customerPushBalance2.setCheckdate(CommonUtils.getTodayDataStr());
						customerPushBalance2.setCheckamount(pushamount);
						customerPushBalance2.setInvoicedate(CommonUtils.getTodayDataStr());
						customerPushBalance2.setInvoiceamount(pushamount);
						customerPushBalance2.setRemark(customerPushBalance.getRemark());
						int j= customerPushBalanceMapper.addCustomerPushBanlance(customerPushBalance2);
						if(j>0){
							if(pushid!=""){
								pushid += ","+customerPushBalance2.getId();
							}else{
								pushid = customerPushBalance2.getId();
							}
						}
						SalesInvoiceDetail salesInvoiceDetail = new SalesInvoiceDetail();
						salesInvoiceDetail.setIsdiscount("1");
						salesInvoiceDetail.setIsbranddiscount("1");
						salesInvoiceDetail.setBillid(invoiceid);
						salesInvoiceDetail.setSourceid(customerPushBalance2.getId());
						salesInvoiceDetail.setSourcedetailid(customerPushBalance2.getId());
						salesInvoiceDetail.setBrandid(customerPushBalance2.getBrandid());
						salesInvoiceDetail.setBranddept(customerPushBalance2.getBranddept());
						salesInvoiceDetail.setGoodsid(customerPushBalance2.getBrandid());
						salesInvoiceDetail.setCustomerid(customerPushBalance2.getCustomerid());
						//获取上级客户
						if(null!=customer){
							salesInvoiceDetail.setPcustomerid(customer.getPid());
							salesInvoiceDetail.setSalesarea(customer.getSalesarea());
							salesInvoiceDetail.setSalesdept(customer.getSalesdeptid());
							salesInvoiceDetail.setSalesuser(customer.getSalesuserid());
							salesInvoiceDetail.setCustomersort(customer.getCustomersort());
						}
						salesInvoiceDetail.setSourcetype("3");
						salesInvoiceDetail.setBranduser(getBrandUseridByCustomeridAndBrand(customerPushBalance2.getBrandid(), customerPushBalance2.getCustomerid()));
						//厂家业务员
						salesInvoiceDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(customerPushBalance2.getBrandid(), customerPushBalance2.getCustomerid()));
						if(null!=brand){
							salesInvoiceDetail.setSupplierid(brand.getSupplierid());
						}
						salesInvoiceDetail.setTaxamount(customerPushBalance2.getAmount());
						salesInvoiceDetail.setNotaxamount(notaxamount);
                        BigDecimal tax = salesInvoiceDetail.getTaxamount().subtract(salesInvoiceDetail.getNotaxamount());
                        salesInvoiceDetail.setTax(tax);
                        salesInvoiceDetail.setTaxtype(customerPushBalance2.getDefaulttaxtype());
						salesInvoiceDetail.setSeq(99999);
						salesInvoiceDetail.setRemark(customerPushBalance.getRemark());
						int z = salesInvoiceMapper.addSalesInvoiceDetail(salesInvoiceDetail);
						flag = z>0;
					}
				}
				
			}
		}else{
			if (isAutoCreate("t_account_customer_push_balance")) {
				// 获取自动编号
				String id = getAutoCreateSysNumbderForeign(customerPushBalance, "t_account_customer_push_balance");
				customerPushBalance.setId(id);
			}else{
				customerPushBalance.setId("CC-"+CommonUtils.getDataNumberSendsWithRand());
			} 
			//取上级客户
			Customer headcustomer = salesExtService.getHeadCustomer(customerPushBalance.getCustomerid());
			if(null!=headcustomer){
				customerPushBalance.setPcustomerid(headcustomer.getId());
			}
			Customer customer = getCustomerByID(customerPushBalance.getCustomerid());
			if(null!=customer){
				customerPushBalance.setSalesarea(customer.getSalesarea());
				customerPushBalance.setSalesdept(customer.getSalesdeptid());
				customerPushBalance.setSalesuser(customer.getSalesuserid());
				customerPushBalance.setPcustomerid(customer.getPid());
				customerPushBalance.setCustomersort(customer.getCustomersort());
				customerPushBalance.setIndooruserid(customer.getIndoorstaff());
			}
			//是否关联销售发票 1是0否
			customerPushBalance.setIsinvoice("1");
			customerPushBalance.setIsrefer("1");
			customerPushBalance.setInvoiceid(invoiceid);
			customerPushBalance.setAdduserid(sysUser.getUserid());
			customerPushBalance.setAddusername(sysUser.getName());
			customerPushBalance.setAdddeptid(sysUser.getDepartmentid());
			customerPushBalance.setAdddeptname(sysUser.getDepartmentname());
			customerPushBalance.setBranduser(getBrandUseridByCustomeridAndBrand(customerPushBalance.getBrandid(), customerPushBalance.getCustomerid()));
			//厂家业务员
			customerPushBalance.setSupplieruser(getSupplieruserByCustomeridAndBrand(customerPushBalance.getBrandid(), customerPushBalance.getCustomerid()));
			//获取品牌部门、税种，取品牌档案中的税种，若品牌档案中没有税种，则取系统默认税种
			Brand brand = getGoodsBrandByID(customerPushBalance.getBrandid());
			if(null!=brand){
				customerPushBalance.setBranddept(brand.getDeptid());
				customerPushBalance.setSupplierid(brand.getSupplierid());
                customerPushBalance.setDefaulttaxtype(brand.getDefaulttaxtype());
			}
			if(StringUtils.isEmpty(customerPushBalance.getDefaulttaxtype())){
				String taxtype = "";
				SysParam taxtypeParam = getBaseSysParamMapper().getSysParam("DEFAULTAXTYPE");
				if(null != taxtypeParam){
					taxtype = taxtypeParam.getPvalue();
				}
				customerPushBalance.setDefaulttaxtype(taxtype);
			}

            //计算冲差未税金额、冲差税额
            BigDecimal notaxamount = BigDecimal.ZERO;
            if(StringUtils.isNotEmpty(customerPushBalance.getDefaulttaxtype())){
                notaxamount = getNotaxAmountByTaxAmount(customerPushBalance.getAmount(),customerPushBalance.getDefaulttaxtype());
            }else{
                notaxamount = getNotaxAmountByTaxAmount(customerPushBalance.getAmount(),null);
            }
            customerPushBalance.setNotaxamount(notaxamount);
            customerPushBalance.setTax(customerPushBalance.getAmount().subtract(notaxamount));

			customerPushBalance.setStatus("2");
//			customerPushBalance.setInitsendamount(customerPushBalance.getAmount());
			customerPushBalance.setSendamount(customerPushBalance.getAmount());
			customerPushBalance.setCheckdate(CommonUtils.getTodayDataStr());
			customerPushBalance.setCheckamount(customerPushBalance.getAmount());
			customerPushBalance.setInvoicedate(CommonUtils.getTodayDataStr());
			customerPushBalance.setInvoiceamount(customerPushBalance.getAmount());
			
			int j = customerPushBalanceMapper.addCustomerPushBanlance(customerPushBalance);
			if(j>0){
				pushid = customerPushBalance.getId();
				SalesInvoiceDetail salesInvoiceDetail = new SalesInvoiceDetail();
				salesInvoiceDetail.setIsdiscount("1");
				salesInvoiceDetail.setIsbranddiscount("1");
				salesInvoiceDetail.setSourceid(customerPushBalance.getId());
				salesInvoiceDetail.setSourcedetailid(customerPushBalance.getId());
				salesInvoiceDetail.setBillid(invoiceid);
				salesInvoiceDetail.setBrandid(customerPushBalance.getBrandid());
				salesInvoiceDetail.setBranddept(customerPushBalance.getBranddept());
				salesInvoiceDetail.setGoodsid(customerPushBalance.getBrandid());
				salesInvoiceDetail.setCustomerid(customerPushBalance.getCustomerid());
				//获取上级客户
				if(null!=customer){
					salesInvoiceDetail.setPcustomerid(customer.getPid());
					salesInvoiceDetail.setSalesarea(customer.getSalesarea());
					salesInvoiceDetail.setSalesdept(customer.getSalesdeptid());
					salesInvoiceDetail.setSalesuser(customer.getSalesuserid());
					salesInvoiceDetail.setCustomersort(customer.getCustomersort());
				}
				salesInvoiceDetail.setSourcetype("3");
				salesInvoiceDetail.setBranduser(getBrandUseridByCustomeridAndBrand(salesInvoiceDetail.getBrandid(), salesInvoiceDetail.getCustomerid()));
				//厂家业务员
				customerPushBalance.setSupplieruser(getSupplieruserByCustomeridAndBrand(salesInvoiceDetail.getBrandid(), salesInvoiceDetail.getCustomerid()));
				if(null!=brand){
					salesInvoiceDetail.setSupplierid(brand.getSupplierid());
				}
				salesInvoiceDetail.setTaxamount(customerPushBalance.getAmount());
				salesInvoiceDetail.setNotaxamount(notaxamount);
                BigDecimal tax = salesInvoiceDetail.getTaxamount().subtract(salesInvoiceDetail.getNotaxamount());
                salesInvoiceDetail.setTax(tax);
				salesInvoiceDetail.setTaxtype(customerPushBalance.getDefaulttaxtype());
				salesInvoiceDetail.setSeq(99999);
				salesInvoiceDetail.setRemark(customerPushBalance.getRemark());
				int i = salesInvoiceMapper.addSalesInvoiceDetail(salesInvoiceDetail);
				flag = i>0;
			}
		}
		if(flag){
			BigDecimal taxamount=salesInvoice.getTaxamount().add(totalpushamount);
			salesInvoice.setTaxamount(taxamount);
			salesInvoice.setInvoiceamount(salesInvoice.getInvoiceamount().add(totalpushamount));
			BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, null);
			salesInvoice.setNotaxamount(notaxamount);
			List<String> sourceidList = salesInvoiceMapper.getSalesInvoiceSouceidList(salesInvoice.getId());
			String billsourceid = "";
			for(String sourceid : sourceidList){
				if("".equals(billsourceid)){
					billsourceid = sourceid;
				}else{
					billsourceid += ","+sourceid;
				}
			}
			salesInvoice.setSourceid(billsourceid);
			salesInvoiceMapper.editSalesInvoice(salesInvoice);
		}
		returnMap.put("flag", flag);
		returnMap.put("pushid", pushid);
		return returnMap;
	}

	@Override
	public List showSalesInvoiceListPageByIds(String ids) throws Exception {
		List<SalesInvoice> list = salesInvoiceMapper.showSalesInvoiceListPageByIds(ids);
		for(SalesInvoice salesInvoice : list){
			Customer customer = getCustomerByID(salesInvoice.getCustomerid());
			if(null!=customer){
				salesInvoice.setCustomername(customer.getName());
			}
			if(null!=salesInvoice.getChlidcustomerid()){
				String[] childcustomer = salesInvoice.getChlidcustomerid().split(",");
				String childcustomername = null;
				for(String customerid : childcustomer){
					Customer chlidcustomer = getCustomerByID(customerid);
					if(null!=chlidcustomer){
						if(null==childcustomername){
							childcustomername = chlidcustomer.getName();
						}else{
							childcustomername += ","+chlidcustomer.getName();
						}
					}
				}
				salesInvoice.setChlidcustomername(childcustomername);
			}
			Contacter contacter = getContacterById(salesInvoice.getHandlerid());
			if(null!=contacter){
				salesInvoice.setHandlername(contacter.getName());
			}
			DepartMent departMent = getDepartmentByDeptid(salesInvoice.getSalesdept());
			if(null!=departMent){
				salesInvoice.setSalesdeptname(departMent.getName());
			}
			Personnel personnel = getPersonnelById(salesInvoice.getSalesuser());
			if(null!=personnel){
				salesInvoice.setSalesusername(personnel.getName());
			}
		}
		return list;
	}

	@Override
	public boolean addSalesInvoiceRebate(String id, BigDecimal rebateAmount,String remark,String subject)
			throws Exception {
		boolean flag = false;
		SalesInvoice salesInvoice = salesInvoiceMapper.getSalesInvoiceInfo(id);
        if(null==salesInvoice || "3".equals(salesInvoice.getStatus()) || "4".equals(salesInvoice.getStatus())){
            return false;
        }
		int countRebatePush = customerPushBalanceMapper.getCustomerPushBanlanceByInvoiceIsrebate(id);
		if(countRebatePush==0 && null!=salesInvoice){
			//返利总金额 = - 发票总金额*返利率/100
//			BigDecimal rebateAmount = salesInvoice.getTaxamount().multiply(rebate).divide(new BigDecimal(100),decimalLen,BigDecimal.ROUND_HALF_UP);
			rebateAmount = rebateAmount.negate();
			List<String> customerList = salesInvoiceMapper.getSalesInvoiceSendCustomerList(id);
			//总发货金额
			BigDecimal totalSendamount = salesInvoiceMapper.getSalesInvoiceSendAmount(id);
			//客户返利金额集合
			Map rebateMap = new HashMap();
			//客户发货金额集合
			Map customerMap = new HashMap();
			BigDecimal totaluseRebateAmount = BigDecimal.ZERO;
			//分配发票下各客户 按发货金额比例 分配返利金额
			for(int i=0;i<customerList.size();i++){
				String customerid = customerList.get(i);
				BigDecimal taxamount = salesInvoiceMapper.getSalesInvoiceAmountByCustomerid(id, customerid);
				BigDecimal customerRebateAmount = BigDecimal.ZERO;
				if(taxamount.compareTo(BigDecimal.ZERO)==1){
					//客户返利金额 = （客户总发货金额/发票总发货金额）*返利总额
					customerRebateAmount = taxamount.divide(totalSendamount,decimalLen,BigDecimal.ROUND_HALF_UP).multiply(rebateAmount).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
				}
				//最后一个客户 返利金额 = 总返利金额 - 已分配返利金额
				if(i==(customerList.size()-1)){
					customerRebateAmount = rebateAmount.subtract(totaluseRebateAmount);
				}
				customerMap.put(customerid, taxamount);
				rebateMap.put(customerid, customerRebateAmount);
				totaluseRebateAmount = totaluseRebateAmount.add(customerRebateAmount);
			}
			//根据客户的返利金额 根据客户下不同品牌的发货金额比例 生成该客户的冲差单
			Set set = rebateMap.entrySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> entry = (Entry<String, String>) it.next();
				String customerid = entry.getKey();
				//客户返利金额
				BigDecimal customerRebate = (BigDecimal) rebateMap.get(customerid);
				BigDecimal sendAmount = (BigDecimal) customerMap.get(customerid);
				BigDecimal useRebate = BigDecimal.ZERO;
				List<SalesInvoiceDetail> customerBrandList = salesInvoiceMapper.getSalesInvoiceBrandAmountByCustomerid(id, customerid);
				for(int i=0;i<customerBrandList.size();i++){
					SalesInvoiceDetail salesInvoiceDetail = customerBrandList.get(i);
					BigDecimal brandSumAmount = salesInvoiceDetail.getTaxamount();
					BigDecimal pushAmount = BigDecimal.ZERO;
					if(brandSumAmount.compareTo(BigDecimal.ZERO)==1){
						//客户返利金额 = （客户总发货金额/发票总发货金额）*返利总额
						pushAmount = brandSumAmount.divide(sendAmount,6,BigDecimal.ROUND_HALF_UP).multiply(customerRebate).setScale(2,BigDecimal.ROUND_HALF_UP);
					}
					//最后一个品牌 返利金额 = 总返利金额 - 已分配返利金额
					if(i==(customerBrandList.size()-1)){
						pushAmount = customerRebate.subtract(useRebate);
					}
					if(pushAmount.compareTo(BigDecimal.ZERO)!=0){
						CustomerPushBalance customerPushBalance = addCustomerPushBanlanceByInvoiceAndBrand(customerid, salesInvoiceDetail.getBrandid(), pushAmount, id,remark,subject);
						if(null!=customerPushBalance){
							useRebate = useRebate.add(pushAmount);
							//生成发票折扣明细数据 
							SalesInvoiceDetail salesInvoiceDetailrebet = new SalesInvoiceDetail();
							salesInvoiceDetailrebet.setSourceid(customerPushBalance.getId());
							salesInvoiceDetailrebet.setSourcedetailid(customerPushBalance.getId());
							salesInvoiceDetailrebet.setIsdiscount("2");
							salesInvoiceDetailrebet.setIsbranddiscount("1");
							salesInvoiceDetailrebet.setBillid(id);
							salesInvoiceDetailrebet.setBrandid(customerPushBalance.getBrandid());
							salesInvoiceDetailrebet.setBranddept(customerPushBalance.getBranddept());
							salesInvoiceDetailrebet.setBranduser(customerPushBalance.getBranduser());
							salesInvoiceDetailrebet.setSupplierid(customerPushBalance.getSupplierid());
							//厂家业务员
							salesInvoiceDetailrebet.setSupplieruser(customerPushBalance.getSupplieruser());
							//返利
							salesInvoiceDetailrebet.setGoodsid("FL");
							salesInvoiceDetailrebet.setCustomerid(salesInvoiceDetail.getCustomerid());
							Customer customer = getCustomerByID(salesInvoiceDetail.getCustomerid());
							if(null!=customer){
								salesInvoiceDetailrebet.setPcustomerid(customer.getPid());
								salesInvoiceDetailrebet.setSalesarea(customer.getSalesarea());
								salesInvoiceDetailrebet.setSalesdept(customer.getSalesdeptid());
								salesInvoiceDetailrebet.setSalesuser(customer.getSalesuserid());
								salesInvoiceDetailrebet.setCustomersort(customer.getCustomersort());
							}
							salesInvoiceDetailrebet.setSourcetype("3");
							salesInvoiceDetailrebet.setTaxamount(pushAmount);
                            salesInvoiceDetailrebet.setNotaxamount(customerPushBalance.getNotaxamount());
                            salesInvoiceDetailrebet.setTax(customerPushBalance.getTax());
                            salesInvoiceDetailrebet.setTaxtype(customerPushBalance.getDefaulttaxtype());
							salesInvoiceDetailrebet.setSeq(99999);
							salesInvoiceDetailrebet.setRemark(remark);
							int j = salesInvoiceMapper.addSalesInvoiceDetail(salesInvoiceDetailrebet);
							if(j>0){
								flag = true;
							}
						}
					}
					
				}
			}
			if(flag){
				salesInvoice.setTaxamount(salesInvoice.getTaxamount().add(rebateAmount));
				BigDecimal taxamount=salesInvoice.getInvoiceamount().add(rebateAmount);
				salesInvoice.setInvoiceamount(taxamount);
				BigDecimal totalnotaxamount = getNotaxAmountByTaxAmount(taxamount, null);
				salesInvoice.setNotaxamount(totalnotaxamount);
				salesInvoiceMapper.editSalesInvoice(salesInvoice);
			}
		}
		return flag;
	}
	/**
	 * 
	 * @param customerid		客户编号
	 * @param brandid				品牌编号
	 * @param amount			冲差金额
	 * @param invoiceid			销售发票编号
	 * @return
	 * @author chenwei 
	 * @date Nov 23, 2013
	 */
	public CustomerPushBalance addCustomerPushBanlanceByInvoiceAndBrand(String customerid,String brandid,BigDecimal amount,String invoiceid,String remark,String subject) throws Exception{
		SysUser sysUser = getSysUser();
		CustomerPushBalance customerPushBalance = new CustomerPushBalance();
		if (isAutoCreate("t_account_customer_push_balance")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(customerPushBalance, "t_account_customer_push_balance");
			customerPushBalance.setId(id);
		}else{
			customerPushBalance.setId("CC-"+CommonUtils.getDataNumberSendsWithRand());
		} 
		customerPushBalance.setCustomerid(customerid);
		Customer customer = getCustomerByID(customerid);
		if(null!=customer){
			customerPushBalance.setPcustomerid(customer.getPid());
			customerPushBalance.setSalesarea(customer.getSalesarea());
			customerPushBalance.setSalesdept(customer.getSalesdeptid());
			customerPushBalance.setSalesuser(customer.getSalesuserid());
			customerPushBalance.setCustomersort(customer.getCustomersort());
			customerPushBalance.setIndooruserid(customer.getIndoorstaff());
		}
		customerPushBalance.setIsinvoice("1");
		customerPushBalance.setIsrebate("1");
		customerPushBalance.setIsrefer("1");
		customerPushBalance.setInvoiceid(invoiceid);
		customerPushBalance.setPushtype("1");
		customerPushBalance.setBrandid(brandid);
        if(StringUtils.isNotEmpty(brandid)){
            Brand brand = getGoodsBrandByID(brandid);
            if(null != brand){
                customerPushBalance.setDefaulttaxtype(brand.getDefaulttaxtype());
            }
        }
		customerPushBalance.setBranduser(getBrandUseridByCustomeridAndBrand(customerPushBalance.getBrandid(), customerid));
		//厂家业务员
		customerPushBalance.setSupplieruser(getSupplieruserByCustomeridAndBrand(customerPushBalance.getBrandid(), customerid));
		//获取品牌部门
		Brand brand = getGoodsBrandByID(customerPushBalance.getBrandid());
		if(null!=brand){
			customerPushBalance.setBranddept(brand.getDeptid());
			customerPushBalance.setSupplierid(brand.getSupplierid());
		}
		customerPushBalance.setBusinessdate(CommonUtils.getTodayDataStr());
		customerPushBalance.setAmount(amount);
        //计算冲差未税金额、税额
        BigDecimal notaxamount = BigDecimal.ZERO;
        if(StringUtils.isNotEmpty(customerPushBalance.getDefaulttaxtype())){
            notaxamount = getNotaxAmountByTaxAmount(amount,customerPushBalance.getDefaulttaxtype());
        }else{
            notaxamount = getNotaxAmountByTaxAmount(customerPushBalance.getAmount(),null);
        }
        customerPushBalance.setNotaxamount(notaxamount);
        customerPushBalance.setTax(customerPushBalance.getAmount().subtract(customerPushBalance.getNotaxamount()));

		customerPushBalance.setAdduserid(sysUser.getUserid());
		customerPushBalance.setAddusername(sysUser.getName());
		customerPushBalance.setAdddeptid(sysUser.getDepartmentid());
		customerPushBalance.setAdddeptname(sysUser.getDepartmentname());
		customerPushBalance.setStatus("2");
//		customerPushBalance.setInitsendamount(amount);
		customerPushBalance.setSendamount(amount);
		customerPushBalance.setCheckdate(CommonUtils.getTodayDataStr());
		customerPushBalance.setCheckamount(amount);
		customerPushBalance.setInvoicedate(CommonUtils.getTodayDataStr());
		customerPushBalance.setInvoiceamount(amount);
		if(null==remark){
			remark = "";
		}
		customerPushBalance.setRemark(remark);
		customerPushBalance.setSubject(subject);
		int i = customerPushBalanceMapper.addCustomerPushBanlance(customerPushBalance);
		if(i>0){
			return customerPushBalance;
		}else{
			return null;
		}
	}

	@Override
	public List getReceiptUnWriteOffData(String customerid) throws Exception {
		List list = salesOutService.getReceiptUnWriteoffListByCustomerid(customerid);
		return list;
	}
	@Override
	public SalesInvoice getPureSalesInvoicePureInfo(String id) throws Exception {
		return salesInvoiceMapper.getSalesInvoiceInfo(id);
	}

	@Override
	public boolean updateSalesInvoiceCancel(String id) throws Exception {
		SalesInvoice salesInvoice = salesInvoiceMapper.getSalesInvoiceInfo(id);
		boolean flag = false;
		if(null!=salesInvoice && "2".equals(salesInvoice.getStatus())){
			SysUser sysUser = getSysUser();
			int i = salesInvoiceMapper.updateSalesInvoiceCancel(id, sysUser.getUserid(), sysUser.getName());
			flag = i>0;
		}
		return flag;
	}

	@Override
	public List showSalesInvoiceListPageByCustomer(String customerid)
			throws Exception {
		List<SalesInvoice> list = salesInvoiceMapper.showSalesInvoiceListPageByCustomer(customerid);
		for(SalesInvoice salesInvoice : list){
			CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(salesInvoice.getCustomerid());
			if(null!=customerCapital){
				salesInvoice.setCustomeramount(customerCapital.getAmount());
			}else{
				salesInvoice.setCustomeramount(BigDecimal.ZERO);
			}
			Customer customer = getCustomerByID(salesInvoice.getCustomerid());
			if(null!=customer){
				salesInvoice.setCustomername(customer.getName());
			}
			if(null!=salesInvoice.getChlidcustomerid()){
				String[] childcustomer = salesInvoice.getChlidcustomerid().split(",");
				String childcustomername = null;
				for(String id : childcustomer){
					Customer chlidcustomer = getCustomerByID(id);
					if(null!=chlidcustomer){
						if(null==childcustomername){
							childcustomername = chlidcustomer.getName();
						}else{
							childcustomername += ","+chlidcustomer.getName();
						}
					}
				}
				salesInvoice.setChlidcustomername(childcustomername);
			}
			Contacter contacter = getContacterById(salesInvoice.getHandlerid());
			if(null!=contacter){
				salesInvoice.setHandlername(contacter.getName());
			}
			DepartMent departMent = getDepartmentByDeptid(salesInvoice.getSalesdept());
			if(null!=departMent){
				salesInvoice.setSalesdeptname(departMent.getName());
			}
			Personnel personnel = getPersonnelById(salesInvoice.getSalesuser());
			if(null!=personnel){
				salesInvoice.setSalesusername(personnel.getName());
			}
			String taxtype=salesInvoiceMapper.getSalesInvoiceDetailOneTaxtype(salesInvoice.getId());
			salesInvoice.setTaxtype(taxtype);
			if(StringUtils.isNotEmpty(salesInvoice.getTaxtype())){
				TaxType taxTypeInfo=getTaxType(salesInvoice.getTaxtype());
				if(null!=taxTypeInfo){
					salesInvoice.setTaxtypename(taxTypeInfo.getName());
				}
			}
		}
		return list;
	}
	@Override
	public int getSalesInvoiceHasBlance(String invoiceid) throws Exception{
		int icount=0;
		if(null==invoiceid || "".equals(invoiceid.trim())){
			return icount;
		}
		Map map=new HashMap();
		map.put("invoiceid", invoiceid.trim());
		map.put("statusarr", "3,4");
		icount=customerPushBalanceMapper.getCustomerPushBanlanceCountBy(map);
		return icount;
	}

	public ISalesStatementService getSalesStatementService() {
		return salesStatementService;
	}

	public void setSalesStatementService(
			ISalesStatementService salesStatementService) {
		this.salesStatementService = salesStatementService;
	}

	@Override
	public Map salesInvoiceMutiDelete(String ids) throws Exception {
		String[] idArr = ids.split(",");
		String sucids = "",unsucids = "";
		for(String id : idArr){
			boolean flag = deletesalesInvoice(id);
			if(flag){
				if(sucids == ""){
					sucids = id;
				}else{
					sucids += "," + id;
				}
			}else{
				if(unsucids == ""){
					unsucids = id;
				}else{
					unsucids += "," + id;
				}
			}
		}
		Map map = new HashMap();
		map.put("sucids", sucids);
		map.put("unsucids", unsucids);
		return map;
	}

	@Override
	public List getCustomerReceivablePastdueList(PageMap pageMap)
			throws Exception {
		List list = salesInvoiceMapper.getCustomerReceivablePastdueList(pageMap);
		return list;
	}

	@Override
	public PageData showSalesInvoiceDetailData(PageMap pageMap)
			throws Exception {
		String id = (String) pageMap.getCondition().get("id");
		List<Map> list = salesInvoiceMapper.showSalesInvoiceDetailData(pageMap);
		for(Map map : list){
			String customerid = (String) map.get("customerid");
			Customer customer = getCustomerByID(customerid);
			if(null!=customer){
				map.put("customername", customer.getName());
			}
			String goodsid = (String) map.get("goodsid");
			GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
			if(null!=goodsInfo){
				map.put("goodsInfo", goodsInfo);
				map.put("goodsname", goodsInfo.getName());
			}
			String sourcetype = (String) map.get("sourcetype");
			if("1".equals(sourcetype)){
				map.put("sourcetypename", "销售回单");
			}else if("2".equals(sourcetype)){
				map.put("sourcetypename", "销售退货通知单");
			}else if("3".equals(sourcetype)){
				map.put("sourcetypename", "应收款冲差单");
			}
		}
		PageData pageData = new PageData(salesInvoiceMapper.showSalesInvoiceDetailDataCount(pageMap), list, pageMap);
		Map map = salesInvoiceMapper.getSalesInvoiceDetailSumData(id);
		if(null!=map){
			List footer = new ArrayList();
			map.put("businessdate", "合计");
			footer.add(map);
			pageData.setFooter(footer);
		}
		return pageData;
	}

	@Override
	public List getSalesInvoiceListByPhone(Map map) throws Exception {
		String dataSql = getDataAccessRule("t_account_sales_invoice", "t");
		map.put("datasql", dataSql);
		List<Map> list = salesInvoiceMapper.getSalesInvoiceListByPhone(map);
		for(Map dataMap : list){
			String status = (String) dataMap.get("status");
			if("2".equals(status)){
				dataMap.put("status", "保存");
			}else if("3".equals(status)){
				dataMap.put("status", "审核通过");
			}else if("4".equals(status)){
				dataMap.put("status", "关闭");
			}else if("5".equals(status)){
				dataMap.put("status", "回退");
			}
			String applytype = (String) dataMap.get("applytype");
			if("1".equals(applytype)){
				dataMap.put("applytype", "开票");
			}else if("2".equals(applytype)){
				dataMap.put("applytype", "核销");
			}else if("3".equals(applytype)){
				dataMap.put("applytype", "开票核销");
			}
		}
		return list;
	}

	@Override
	public Map deleteSalesInvoiceSource(Map map) throws Exception {
		String invoiceid = (String)map.get("invoiceid");
		String sourceid = (String)map.get("sourceid");
		String delgoodsids = "";
		List<SalesInvoiceDetail> detailList = salesInvoiceMapper.getSalesInvoiceDetailListByBillidAndSourceid(invoiceid, sourceid);
		for(SalesInvoiceDetail salesInvoiceDetail : detailList){
			if(StringUtils.isEmpty(delgoodsids)){
				delgoodsids = salesInvoiceDetail.getGoodsid();
			}else{
				delgoodsids += "," + salesInvoiceDetail.getGoodsid();
			}
		}
		Map map2 = new HashMap();
		SalesInvoice salesInvoice = salesInvoiceMapper.getSalesInvoiceInfo(invoiceid);
		if("2".equals(salesInvoice.getStatus())){
			if(StringUtils.isNotEmpty(delgoodsids)){
				 map2 = editSalesInvoice(salesInvoice,delgoodsids,sourceid);
				 //发票来源单据删除成功，判断来源单据是否为销售退货通知单或发货回单，若是，则根据其单据编码获取上游单据清除发票号
				 if(map2.get("flag").equals(true)){
					 if(StringUtils.isNotEmpty(sourceid)){
						 String[] sourceidArr = sourceid.split(",");
						 for(String billid : sourceidArr){
							 Receipt receipt = salesOutService.getReceiptInfo(billid);
							 if(null != receipt){
								 //清除发票号
								 storageSaleService.clearSaleoutInvoiceidByReceiptid(receipt.getId());
								 storageSaleService.clearSaleRejectEnterInvoiceidByReceiptid(receipt.getId());
							 }else{
								 RejectBill rejectBill = salesOutService.getRejectBillInfo(billid);
								 if(null != rejectBill){
									 storageSaleService.clearSaleRejectEnterInvoiceidByRejectbillid(rejectBill.getId());
								 }
							 }
						 }
					 }
				 }
			}else{
				map2.put("flag", false);
				map2.put("msg", "不存在明细,无法从发票中删除!");
			}
		}else{
			map2.put("flag", false);
			map2.put("msg", "只有保存状态下才能执行此操作!");
		}
		map2.put("delgoodsids", delgoodsids);
		return map2;
	}

	@Override
	public Map checkSalesInvoiceCanMuApplyInvoice(String ids) throws Exception {
		String msg = "",unids = "",doids = "",unallids = "",difttaxtypeids = "",beginids = "";
		Map map = new HashMap();
		if(StringUtils.isNotEmpty(ids)){
			String[] idArr = ids.split(",");
			for(String id : idArr){
				//判断来源单据是否存在客户应收款期初，若存在，则不允许申请开票
				int hasbegincount = salesInvoiceMapper.getSalesInvoiceDetailListHasBeginAmount(id);
				if(hasbegincount == 0){
					//判断明细中是否不同税种，count > 1 不同税种， =1 相同税种
					int count = salesInvoiceMapper.getSalesInvoiceDetailTaxtypeCount(id);
					if(count == 1){
						//是否完全销售开票 false 完全 true 未完全
						boolean flag2 = checkSalesInvoiceBillCanMuApplyInvoiceAll(id);
						if(flag2){
							//是否已存在销售开票的单据
							boolean flag = checkSalesInvoiceBillCanMuApplyInvoice(id);
							if(flag){
								if(StringUtils.isNotEmpty(unids)){
									unids += "," + id;
								}else {
									unids = id;
								}
							}
							if(StringUtils.isNotEmpty(doids)){
								doids += "," + id;
							}else {
								doids = id;
							}
						}else{
							if(StringUtils.isNotEmpty(unallids)){
								unallids += "," + id;
							}else {
								unallids = id;
							}
						}
					}else if(count > 1){
						if(StringUtils.isNotEmpty(difttaxtypeids)){
							difttaxtypeids += "," + id;
						}else {
							difttaxtypeids = id;
						}
					}
				}else {
					if(StringUtils.isNotEmpty(beginids)){
						beginids += "," + id;
					}else {
						beginids = id;
					}
				}
			}
			if(StringUtils.isNotEmpty(beginids)){
				msg = "销售发票编号：" +beginids+ "来源单据存在客户应收款期初，不允许申请开票！";
			}
			if(StringUtils.isNotEmpty(difttaxtypeids)){
				if(StringUtils.isEmpty(msg)){
					msg = "销售发票编号：" +difttaxtypeids+ "存在不同税种！";
				}else{
					msg += "<br>" + "销售发票编号：" +difttaxtypeids+ "存在不同税种！";
				}
			}
			if(StringUtils.isNotEmpty(unallids)){
				if(StringUtils.isNotEmpty(msg)){
					msg += "<br>" + "销售发票编号：" +unallids+ "来源单据已完全销售开票！";
				}else{
					msg = "销售发票编号：" +unallids+ "来源单据已完全销售开票！";
				}
			}
			if(StringUtils.isNotEmpty(unids)){
				if(StringUtils.isNotEmpty(msg)){
					msg += "<br>" + unids+ "存在已申请开票过的单据！";
				}else{
					msg = unids+ "存在已申请开票过的单据！";
				}
			}
			map.put("doids",doids);
			map.put("msg",msg);
		}
		return map;
	}

	/**
	 * 删除发票明细 回写
	 * @param salesInvoice
	 * @param delgoodsids
	 * @param sourceid2
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 22, 2014
	 */
	private Map editSalesInvoice(SalesInvoice salesInvoice,String delgoodsids,String sourceid2)throws Exception{
		boolean flag = false;
		String msg = "";
		SalesInvoice oldSalesInvoice = salesInvoiceMapper.getSalesInvoiceInfo(salesInvoice.getId());
		if(null != oldSalesInvoice && "2".equals(oldSalesInvoice.getStatus()) || "1".equals(oldSalesInvoice.getStatus()) || "5".equals(oldSalesInvoice.getStatus())){
			String[] goodsidArr = delgoodsids.split(",");
			//明细删除前 更新上游单据开票状态
			if(!"".equals(delgoodsids) && null!=goodsidArr && goodsidArr.length>0){
				List sourceidList = salesInvoiceMapper.getSalesInvoiceSouceidList(salesInvoice.getId());
				salesOutService.updateReceiptAndRejectBillInvoice(sourceidList,salesInvoice,"0");
			}
			for(String goodsid : goodsidArr){
				
				if(!"FL".equals(goodsid)){
					//更新正常冲差单的 与发票关联状态
					customerPushBalanceMapper.updateCustomerPushBanlanceIsReferByinvoiceidAndBrandid(salesInvoice.getId(), goodsid);
					//删除相关冲差单
					customerPushBalanceMapper.deleteCustomerPushBanlanceBySouceid(sourceid2, goodsid);
				}else{
					customerPushBalanceMapper.deleteCustomerPushBanlanceBySouceidIsrebate(sourceid2);
				}
				salesInvoiceMapper.deleteSalesInvoiceDetailByGoodsidBoth(salesInvoice.getId(), goodsid,sourceid2);
			}
			List<SalesInvoiceDetail> detailList = salesInvoiceMapper.getSalesInvoiceDetailList(salesInvoice.getId());
			BigDecimal invoiceAmount = new BigDecimal(0);
			BigDecimal taxamount = BigDecimal.ZERO;
			BigDecimal notaxamount = BigDecimal.ZERO;
			
			for(SalesInvoiceDetail salesInvoiceDetail : detailList){
				salesInvoiceDetail.setBillid(salesInvoice.getId());
				//商品品牌 和品牌业务员信息
				GoodsInfo goodsInfo = getAllGoodsInfoByID(salesInvoiceDetail.getGoodsid());
				if(null!=goodsInfo){
					salesInvoiceDetail.setGoodssort(goodsInfo.getDefaultsort());
					salesInvoiceDetail.setBrandid(goodsInfo.getBrand());
					salesInvoiceDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceDetail.getCustomerid()));
					//厂家业务员
					salesInvoiceDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceDetail.getCustomerid()));
					//默认供应商
					salesInvoiceDetail.setSupplierid(goodsInfo.getDefaultsupplier());
				}
				taxamount = taxamount.add(salesInvoiceDetail.getTaxamount());
				notaxamount = notaxamount.add(salesInvoiceDetail.getNotaxamount());
				invoiceAmount = invoiceAmount.add(salesInvoiceDetail.getTaxamount());
			}
			List<String> sourceidList = salesInvoiceMapper.getSalesInvoiceSouceidList(salesInvoice.getId());
			String billsourceid = "";
			for(String sourceid : sourceidList){
				if("".equals(billsourceid)){
					billsourceid = sourceid;
				}else{
					billsourceid += ","+sourceid;
				}
			}
			salesInvoice.setSourceid(billsourceid);
			salesInvoice.setTaxamount(taxamount);
			salesInvoice.setNotaxamount(notaxamount);
			salesInvoice.setInvoiceamount(invoiceAmount);
			SysUser sysUser = getSysUser();
			salesInvoice.setModifyuserid(sysUser.getUserid());
			salesInvoice.setModifyusername(sysUser.getName());
			if("5".equals(salesInvoice.getStatus())){
				salesInvoice.setStatus("2");
			}
			int i = salesInvoiceMapper.editSalesInvoice(salesInvoice);
			flag = i>0;
			if(flag && ((!"".equals(delgoodsids) && null!=goodsidArr && goodsidArr.length>0) || null!=salesInvoice.getInvoiceno() && !"".equals(salesInvoice.getInvoiceno()))){
				salesOutService.updateReceiptAndRejectBillInvoice(sourceidList,salesInvoice,"1");
			}
		}else{
			msg = "销售发票已审核或者核销。不能进行保存。";	
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("id", salesInvoice.getId());
		return map;
	}

    @Override
    public boolean checkSalesInvoiceBillCanMuApplyInvoiceAll(String id)throws Exception {
        return salesInvoiceMapper.checkSalesInvoiceBillCanMuApplyInvoiceAll(id) > 0;
    }

    @Override
    public boolean checkSalesInvoiceBillCanMuApplyInvoice(String id)throws Exception {
        return salesInvoiceMapper.checkSalesInvoiceBillCanMuApplyInvoice(id) > 0;
    }

    @Override
    public Map doSalesInvoiceMuApplyInvoice(String id)throws Exception {
        Map retmap = new HashMap();
        SalesInvoice salesInvoice = salesInvoiceMapper.getSalesInvoiceInfo(id);
        if(null != salesInvoice){
            String customerid = salesInvoice.getCustomerid();
            String ids = "";
            List<SalesInvoiceDetail> list = salesInvoiceMapper.getUnInvoiceSalesInvoiceList(id);
            List<Map> list2 = new ArrayList<Map>();
            for(SalesInvoiceDetail salesInvoiceDetail : list){
                Map map = new HashMap();
                map.put("billtype",salesInvoiceDetail.getSourcetype());
                map.put("billid",salesInvoiceDetail.getSourceid());
                map.put("detailid",salesInvoiceDetail.getSourcedetailid());
                map.put("isdiscount",salesInvoiceDetail.getIsdiscount());
				map.put("isbranddiscount",salesInvoiceDetail.getIsbranddiscount());
                list2.add(map);
            }
            if(list2.size() != 0){
                JSONArray json = JSONArray.fromObject(list2);
                ids = json.toString();
            }
            Map map2 = addSalesInvoiceByApplyInvoice(ids, customerid, "0");
			//写入销售核销、销售开票关系
			if(null != map2 && !map2.isEmpty()){
				if(map2.get("flag").equals(true)){
					String salesinvoicebillid = null != map2.get("id") ? (String)map2.get("id") : "";
					map2.put("salesinvoicebillid",salesinvoicebillid);
					map2.put("invoiceid",id);
					salesInvoiceMapper.updateSalesInvoiceBack(map2);

					map2.put("invoicebillid",salesinvoicebillid);
					map2.put("salesinvoiceid",id);
					salesInvoiceBillMapper.updateSalesInvoiceBillBack(map2);
				}
			}
            return map2;
        }
        return null;
    }

    @Override
    public Map getCancelSalesInvoiceFlag(String invoiceid) throws Exception {
        Map map = new HashMap();
        boolean flag = false;
        SalesInvoice salesInvoice = salesInvoiceMapper.getSalesInvoiceInfo(invoiceid);
        if(null != salesInvoice){
            if("3".equals(salesInvoice.getStatus())){//单据状态
                if("1".equals(salesInvoice.getIsrelate())){//是否关联
                    flag = true;
                }else{
                    map.put("msg","单据未关联收款单，不允许核销!");
                }
            }else{
                map.put("msg","单据不为审核通过状态，不允许核销!");
            }
        }
        map.put("flag",flag);
        return map;
    }

    @Override
    public Map getUnrelateSalesInvoiceFlag(String invoiceid) throws Exception {
        Map map = new HashMap();
        boolean flag = false;
        SalesInvoice salesInvoice = salesInvoiceMapper.getSalesInvoiceInfo(invoiceid);
        if(null != salesInvoice){
            if("0".equals(salesInvoice.getIswriteoff())){//核销状态
                if("1".equals(salesInvoice.getIsrelate())){//是否关联
                    flag = true;
                }else{
                    map.put("msg","单据已取消关联收款单!");
                }
            }else{
                map.put("msg","单据已核销，不允许取消关联收款单!");
            }
        }
        map.put("flag",flag);
        return map;
    }

    @Override
    public Map getRelateSalesInvoiceFlag(String invoiceid) throws Exception {
        Map map = new HashMap();
        boolean flag = false;
        SalesInvoice salesInvoice = salesInvoiceMapper.getSalesInvoiceInfo(invoiceid);
        if(null != salesInvoice){
            if("3".equals(salesInvoice.getStatus())){//单据状态
                if("0".equals(salesInvoice.getIswriteoff())){//核销状态
                    if("0".equals(salesInvoice.getIsrelate())){//是否关联
                        flag = true;
                    }else{
                        map.put("msg","单据已关联收款单!");
                    }
                }else{
                    map.put("msg","单据已核销，不允许关联收款单!");
                }
            }else{
                map.put("msg","单据不为审核通过状态，不允许关联收款单!");
            }
        }
        map.put("flag",flag);
        return map;
    }

    @Override
    public Map getOppauditFlag(String invoiceid) throws Exception {
        Map map = new HashMap();
        boolean flag = false;
        SalesInvoice salesInvoice = salesInvoiceMapper.getSalesInvoiceInfo(invoiceid);
        if(null != salesInvoice){
            if("3".equals(salesInvoice.getStatus())){//单据状态
                if("0".equals(salesInvoice.getIsrelate())){//是否关联
                    flag = true;
                }else{
                    map.put("msg","单据已关联收款单，不允许反审!");
                }
            }else{
                map.put("msg","单据不为审核通过状态，不允许反审!");
            }
        }
        map.put("flag",flag);
        return map;
    }

	@Override
	public PageData getSalesInvoiceDetailList(PageMap pageMap) throws Exception {
		List<SalesInvoiceDetail> detailList = salesInvoiceMapper.getSalesInvoiceDetailListSumBranddiscountByPageMap(pageMap);
		for(SalesInvoiceDetail salesInvoiceDetail : detailList){
			if("3".equals(salesInvoiceDetail.getSourcetype())){
				GoodsInfo goodsInfo = new GoodsInfo();
				Brand brand = getGoodsBrandByID(salesInvoiceDetail.getBrandid());
				if(null!=brand){
					goodsInfo.setName("(折扣)"+brand.getName());
					salesInvoiceDetail.setGoodsname("(折扣)"+brand.getName());
					salesInvoiceDetail.setIsrebate("1");
					salesInvoiceDetail.setGoodsInfo(goodsInfo);
					salesInvoiceDetail.setUnitnum(new BigDecimal(1));
					salesInvoiceDetail.setTaxprice(salesInvoiceDetail.getTaxamount());
					salesInvoiceDetail.setNotaxprice(salesInvoiceDetail.getNotaxamount());
					if(StringUtils.isNotEmpty(salesInvoiceDetail.getTaxtype())){
						TaxType taxType = getTaxType(salesInvoiceDetail.getTaxtype());
						if(null!=taxType){
							salesInvoiceDetail.setTaxtypename(taxType.getName());
						}
					}
				}
			}else if("4".equals(salesInvoiceDetail.getSourcetype())){
				GoodsInfo goodsInfo = new GoodsInfo();
				goodsInfo.setName("应收款期初");
				salesInvoiceDetail.setGoodsname("应收款期初");
				salesInvoiceDetail.setGoodsInfo(goodsInfo);
				salesInvoiceDetail.setUnitnum(new BigDecimal(1));
				salesInvoiceDetail.setTaxprice(salesInvoiceDetail.getTaxamount());
				salesInvoiceDetail.setNotaxprice(salesInvoiceDetail.getNotaxamount());
				if(StringUtils.isNotEmpty(salesInvoiceDetail.getTaxtype())){
					TaxType taxType = getTaxType(salesInvoiceDetail.getTaxtype());
					if(null!=taxType){
						salesInvoiceDetail.setTaxtypename(taxType.getName());
					}
				}
			}else{
				if(StringUtils.isNotEmpty(salesInvoiceDetail.getTaxtype())){
					TaxType taxType = getTaxType(salesInvoiceDetail.getTaxtype());
					if(null!=taxType){
						salesInvoiceDetail.setTaxtypename(taxType.getName());
					}
				}
				Map auxmap = countGoodsInfoNumber(salesInvoiceDetail.getGoodsid(), salesInvoiceDetail.getAuxunitid(), salesInvoiceDetail.getUnitnum());
				String auxnumdetail = (String) auxmap.get("auxnumdetail");
				salesInvoiceDetail.setAuxnumdetail(auxnumdetail);

				//折扣显示处理
				GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(getAllGoodsInfoByID(salesInvoiceDetail.getGoodsid()));
				if(null != goodsInfo){
					salesInvoiceDetail.setGoodsname(goodsInfo.getName());
					salesInvoiceDetail.setBarcode(goodsInfo.getBarcode());
					salesInvoiceDetail.setModel(goodsInfo.getModel());
					salesInvoiceDetail.setBrandname(goodsInfo.getBrandName());
					salesInvoiceDetail.setBoxnum(goodsInfo.getBoxnum());
					if("1".equals(salesInvoiceDetail.getIsdiscount())){
						goodsInfo.setBarcode(null);
						goodsInfo.setBoxnum(null);
						goodsInfo.setName("(折扣)"+goodsInfo.getName());
						salesInvoiceDetail.setBarcode(null);
						salesInvoiceDetail.setBoxnum(null);
						salesInvoiceDetail.setGoodsname("(折扣)" + goodsInfo.getName());
						salesInvoiceDetail.setIsrebate("1");
						//salesInvoiceDetail.setUnitnum(null);
						salesInvoiceDetail.setUnitnum(new BigDecimal(1));
						salesInvoiceDetail.setAuxnumdetail(null);
						salesInvoiceDetail.setTaxprice(null);
						if("1".equals(salesInvoiceDetail.getIsbranddiscount())){
							salesInvoiceDetail.setGoodsid("");
							goodsInfo.setName("(折扣)"+goodsInfo.getBrandName());
							salesInvoiceDetail.setGoodsname("(折扣)" + goodsInfo.getBrandName());
							salesInvoiceDetail.setIsrebate("1");
//							salesInvoiceDetail.setIsdiscount("2");
						}
					}
					salesInvoiceDetail.setGoodsInfo(goodsInfo);
				}
			}
			//返利折扣
			if("2".equals(salesInvoiceDetail.getIsdiscount())){
				GoodsInfo goodsInfo = new GoodsInfo();
				goodsInfo.setName("折扣");
				salesInvoiceDetail.setGoodsname("折扣");
				salesInvoiceDetail.setIsrebate("1");
				salesInvoiceDetail.setGoodsInfo(goodsInfo);
				salesInvoiceDetail.setUnitnum(new BigDecimal(1));
			}
			if(null != salesInvoiceDetail.getGoodsInfo()){
				BigDecimal boxnum = null != salesInvoiceDetail.getGoodsInfo().getBoxnum() ? salesInvoiceDetail.getGoodsInfo().getBoxnum() : BigDecimal.ZERO;
				BigDecimal taxprice = null != salesInvoiceDetail.getTaxprice() ? salesInvoiceDetail.getTaxprice() : BigDecimal.ZERO;
				BigDecimal boxprice = boxnum.multiply(taxprice);
				salesInvoiceDetail.setBoxprice(boxprice);
			}
		}
		int count = salesInvoiceMapper.getSalesInvoiceDetailCountSumBranddiscountByPageMap(pageMap);
		List<SalesInvoiceDetail> foot = salesInvoiceMapper.getSalesInvoiceDetailTotalSumBranddiscountByPageMap(pageMap);
		if(null != foot && foot.size() != 0){
			for(SalesInvoiceDetail salesInvoiceDetail : foot){
				salesInvoiceDetail.setGoodsid("合计");
			}
		}else{
			foot = new ArrayList();
		}
		PageData pageData = new PageData(count,detailList,pageMap);
		pageData.setFooter(foot);
		return pageData;
	}

	/**
    * 根据申请开票生成销售开票
    * @param ids,customerid,iswriteoff
    * @return
    * @author panxiaoxiao
    * @date 2015-03-03
    * @throws Exception
    */
    private Map addSalesInvoiceByApplyInvoice(String ids,String customerid,String iswriteoff)throws Exception{
        boolean flag = false;
        String salesInvoiceid = "";
        String msg = "";
        if(null==ids || "".equals(ids)){
        }else{
            JSONArray idArr = JSONArray.fromObject(ids);
            boolean addFlag = true;
            String sourceids = null;
            //用来存放来源单据编号
            Map sourceidMap = new HashMap();
            //门店客户
            Map childcustomerMap =  new HashMap();
            //单据编号集合
            Map billMap = new HashMap();
            //验证多个回单是否能组成一张销售发票
            for(int i=0;i<idArr.size();i++){
                JSONObject detailObject = (JSONObject) idArr.get(i);
                //单据类型 1回单2销售退货通知单
                String billtype = detailObject.getString("billtype");
                //单据编号
                String id = detailObject.getString("billid");
                //明细编号
                String detailid = detailObject.getString("detailid");
                //是否折扣
                String isdiscount = "0";
                if(detailObject.containsKey("isdiscount")){
                    isdiscount = detailObject.getString("isdiscount");
                }
                if(!sourceidMap.containsKey(id)){
                    //来源单据编号
                    if(sourceids==null){
                        sourceids = id;
                    }else{
                        sourceids += ","+id;
                    }
                    sourceidMap.put(id, billtype);
                }

                if("1".equals(billtype)){
                    if(!billMap.containsKey(id)){
                        Receipt receipt = salesOutService.getReceipt(id);
                        childcustomerMap.put(receipt.getCustomerid(), receipt.getCustomerid());
                        //获取总店客户档案信息 没有总店信息则获取自身
                        Customer headCutomer = salesExtService.getHeadCustomer(receipt.getCustomerid());
                        if(null==customerid){
                            customerid = headCutomer.getId();
                        }
                        //判断是否同一个客户
                        if(customerid.equals(receipt.getCustomerid()) || customerid.equals(headCutomer.getId())){
                        }else{
                            addFlag = false;
                            msg = "不是同一客户(总店)下的销售发货回单，不能生成销售发票";
                            break;
                        }
                        billMap.put(id, receipt);
                    }
                }else if("2".equals(billtype)){
                    if(!billMap.containsKey(id)){
                        RejectBill rejectBill = salesOutService.getRejectBill(id);
                        childcustomerMap.put(rejectBill.getCustomerid(), rejectBill.getCustomerid());
                        //获取总店客户档案信息 没有总店信息则获取自身
                        Customer headCutomer = salesExtService.getHeadCustomer(rejectBill.getCustomerid());
                        if(null==customerid){
                            customerid = headCutomer.getId();
                        }
                        //判断是否同一个客户
                        if(customerid.equals(rejectBill.getCustomerid()) || customerid.equals(headCutomer.getId())){
                            if(null==customerid){
                                customerid = headCutomer.getId();
                            }
                        }else{
                            addFlag = false;
                            msg = "不是同一客户(总店)下的销售退货通知单，不能生成销售发票";
                            break;
                        }
                        billMap.put(id, rejectBill);
                    }
                }else if("3".equals(billtype)){
                    if(!billMap.containsKey(id)){
                        CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(id);
                        childcustomerMap.put(customerPushBalance.getCustomerid(), customerPushBalance.getCustomerid());
                        //获取总店客户档案信息 没有总店信息则获取自身
                        Customer headCutomer = salesExtService.getHeadCustomer(customerPushBalance.getCustomerid());
                        if(null==customerid){
                            customerid = headCutomer.getId();
                        }
                        //判断是否同一个客户
                        if(customerid.equals(customerPushBalance.getCustomerid()) || customerid.equals(headCutomer.getId())){
                            if(null==customerid){
                                customerid = headCutomer.getId();
                            }
                        }else{
                            addFlag = false;
                            msg = "不是同一客户(总店)下的冲差单，不能生成销售发票";
                            break;
                        }
                        billMap.put(id, customerPushBalance);
                    }
                }
            }
			List<TaxType> taxList=financeMapper.getTaxTypeListData();

			Map invoiceBillMap=new HashMap();
			for(TaxType taxType:taxList){
				Map taxMap=new HashMap();
				invoiceBillMap.put(taxType.getId(),taxMap);
			}
            //判断是否可以生成销售发票
            if(addFlag){
                List<SalesInvoiceBillDetail> salesInvoiceBillDetailList = new ArrayList<SalesInvoiceBillDetail>();

                Map billObjectMap = new HashMap();
                for(int i=0;i<idArr.size();i++){
                    JSONObject detailObject = (JSONObject) idArr.get(i);
                    //单据类型 1回单2销售退货通知单
                    String billtype = detailObject.getString("billtype");
                    //单据编号
                    String id = detailObject.getString("billid");
                    //明细编号
                    String detailid = detailObject.getString("detailid");
					//是否折扣
					String isdiscount = "0";
					if(detailObject.containsKey("isdiscount") && StringUtils.isNotEmpty(detailObject.getString("isdiscount"))){
						isdiscount = detailObject.getString("isdiscount");
					}
					//是否品牌折扣
					String isbranddiscount = "0";
					if(detailObject.containsKey("isbranddiscount") && StringUtils.isNotEmpty(detailObject.getString("isbranddiscount"))){
						isbranddiscount = detailObject.getString("isbranddiscount");
					}
                    SalesInvoiceBillDetail salesInvoiceBillDetail = null;

                    if("1".equals(billtype)){
                        Receipt receipt = null;
                        if(billObjectMap.containsKey(id)){
                            receipt = (Receipt) billObjectMap.get(id);
                        }else{
                            receipt = salesOutService.getReceipt(id);
                            billObjectMap.put(id, receipt);
                        }
                        if(null!=receipt){
                            ReceiptDetail receiptDetail = salesOutService.getReceiptDetailInfo(detailid,id);
                            if(null!=receiptDetail){
                                if("1".equals(receiptDetail.getIsdiscount()) && "1".equals(receiptDetail.getIsbranddiscount())){
                                    List<ReceiptDetail> list = salesOutService.getReceiptDetailBrandDiscountList(receiptDetail.getBillid(), receiptDetail.getBrandid());
                                    for(ReceiptDetail receiptDetail2 : list){
                                        salesInvoiceBillDetail = new SalesInvoiceBillDetail();
                                        salesInvoiceBillDetail.setIsdiscount(receiptDetail2.getIsdiscount());
                                        salesInvoiceBillDetail.setIsbranddiscount(receiptDetail2.getIsbranddiscount());
                                        //来源销售发货回单
                                        salesInvoiceBillDetail.setSourcetype("1");
                                        salesInvoiceBillDetail.setSourceid(id);
                                        salesInvoiceBillDetail.setSourcedetailid(receiptDetail2.getId());
                                        salesInvoiceBillDetail.setBillno(receiptDetail2.getBillno());
                                        salesInvoiceBillDetail.setBilldetailid(receiptDetail2.getBilldetailno());
                                        salesInvoiceBillDetail.setCustomerid(receipt.getCustomerid()!=null?receipt.getCustomerid():"");
                                        salesInvoiceBillDetail.setPcustomerid(receipt.getPcustomerid()!=null?receipt.getPcustomerid():"");
                                        salesInvoiceBillDetail.setCustomersort(receipt.getCustomersort()!=null?receipt.getCustomersort():"");
                                        salesInvoiceBillDetail.setSalesarea(receipt.getSalesarea()!=null?receipt.getSalesarea():"");
                                        salesInvoiceBillDetail.setSalesdept(receipt.getSalesdept()!=null?receipt.getSalesdept():"");
                                        salesInvoiceBillDetail.setSalesuser(receipt.getSalesuser()!=null?receipt.getSalesuser():"");
                                        salesInvoiceBillDetail.setGoodsid(receiptDetail2.getGoodsid()!=null?receiptDetail2.getGoodsid():"");
                                        salesInvoiceBillDetail.setBranddept(receiptDetail2.getBranddept()!=null?receiptDetail2.getBranddept():"");
                                        salesInvoiceBillDetail.setCostprice(receiptDetail2.getCostprice());
                                        //商品品牌 和品牌业务员信息
                                        GoodsInfo goodsInfo = getAllGoodsInfoByID(receiptDetail2.getGoodsid());
                                        if(null!=goodsInfo){
                                            salesInvoiceBillDetail.setGoodssort(goodsInfo.getDefaultsort());
                                            salesInvoiceBillDetail.setBrandid(goodsInfo.getBrand());
                                            salesInvoiceBillDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceBillDetail.getCustomerid()));
                                            //厂家业务员
                                            salesInvoiceBillDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), customerid));
                                            //商品默认供应商
                                            salesInvoiceBillDetail.setSupplierid(goodsInfo.getDefaultsupplier());
                                        }
                                        salesInvoiceBillDetail.setUnitid(receiptDetail2.getUnitid());
                                        salesInvoiceBillDetail.setUnitname(receiptDetail2.getUnitname());
                                        salesInvoiceBillDetail.setUnitnum(receiptDetail2.getReceiptnum());
                                        salesInvoiceBillDetail.setAuxunitid(receiptDetail2.getAuxunitid());
                                        salesInvoiceBillDetail.setAuxunitname(receiptDetail2.getAuxunitname());
                                        Map auxMap = countGoodsInfoNumber(receiptDetail2.getGoodsid(), receiptDetail2.getAuxunitid(), receiptDetail2.getReceiptnum());
                                        salesInvoiceBillDetail.setAuxnum((BigDecimal) auxMap.get("auxnum"));
                                        salesInvoiceBillDetail.setAuxnumdetail( (String) auxMap.get("auxnumdetail"));

                                        //退货通知单生成的发票 金额为负
                                        salesInvoiceBillDetail.setTaxprice(receiptDetail2.getTaxprice());
										BigDecimal taxamount=receiptDetail2.getReceipttaxamount();
                                        salesInvoiceBillDetail.setTaxamount(taxamount);
                                        salesInvoiceBillDetail.setTaxtype(receiptDetail2.getTaxtype());
                                        //重新计算税额税额
                                        BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, salesInvoiceBillDetail.getTaxtype());
                                        salesInvoiceBillDetail.setNotaxamount(notaxamount);
                                        //未税单价 = 未税金额/数量
                                        if(null!=notaxamount && notaxamount.compareTo(BigDecimal.ZERO)!=0
                                                && null!=salesInvoiceBillDetail.getUnitnum() && salesInvoiceBillDetail.getUnitnum().compareTo(BigDecimal.ZERO)!=0){
                                            salesInvoiceBillDetail.setNotaxprice(notaxamount.divide(salesInvoiceBillDetail.getUnitnum(), 6,BigDecimal.ROUND_HALF_UP));
                                        }else{
                                            salesInvoiceBillDetail.setNotaxprice(receiptDetail2.getNotaxprice());
                                        }
                                        salesInvoiceBillDetail.setTax(salesInvoiceBillDetail.getTaxamount().subtract(salesInvoiceBillDetail.getNotaxamount()));
                                        salesInvoiceBillDetail.setRemark(receiptDetail2.getRemark());
                                        salesInvoiceBillDetail.setSeq(receiptDetail2.getSeq());
										//针对不同税种生成多条单据，用来保存单据的金额信息
										if(invoiceBillMap.containsKey(receiptDetail2.getTaxtype())){
											Map tmap= (Map) invoiceBillMap.get(receiptDetail2.getTaxtype());
											tmap=getSalesInvoiceBillMap(tmap,receiptDetail2.getReceipttaxamount(),notaxamount,iswriteoff,customerid,receipt.getCustomerid());
											invoiceBillMap.put(receiptDetail2.getTaxtype(),tmap);
											SalesInvoiceBill bill=(SalesInvoiceBill)tmap.get("bill");
											salesInvoiceBillDetail.setBillid(bill.getId());
										}
                                        salesInvoiceBillDetailList.add(salesInvoiceBillDetail);
                                    }
                                }else{
                                    salesInvoiceBillDetail = new SalesInvoiceBillDetail();
//                                    salesInvoiceBillDetail.setBillid(salesInvoiceBill.getId());
                                    //来源销售发货回单
                                    salesInvoiceBillDetail.setSourcetype("1");
                                    salesInvoiceBillDetail.setSourceid(id);
                                    salesInvoiceBillDetail.setSourcedetailid(detailid);
                                    salesInvoiceBillDetail.setBillno(receiptDetail.getBillno());
                                    salesInvoiceBillDetail.setBilldetailid(receiptDetail.getBilldetailno());
                                    salesInvoiceBillDetail.setIsdiscount(receiptDetail.getIsdiscount());
                                    salesInvoiceBillDetail.setCustomerid(receipt.getCustomerid()!=null?receipt.getCustomerid():"");
                                    salesInvoiceBillDetail.setPcustomerid(receipt.getPcustomerid()!=null?receipt.getPcustomerid():"");
                                    salesInvoiceBillDetail.setCustomersort(receipt.getCustomersort()!=null?receipt.getCustomersort():"");
                                    salesInvoiceBillDetail.setSalesarea(receipt.getSalesarea()!=null?receipt.getSalesarea():"");
                                    salesInvoiceBillDetail.setSalesdept(receipt.getSalesdept()!=null?receipt.getSalesdept():"");
                                    salesInvoiceBillDetail.setSalesuser(receipt.getSalesuser()!=null?receipt.getSalesuser():"");
                                    salesInvoiceBillDetail.setGoodsid(receiptDetail.getGoodsid()!=null?receiptDetail.getGoodsid():"");
                                    salesInvoiceBillDetail.setBranddept(receiptDetail.getBranddept()!=null?receiptDetail.getBranddept():"");
                                    salesInvoiceBillDetail.setCostprice(receiptDetail.getCostprice());
                                    //商品品牌 和品牌业务员信息
                                    GoodsInfo goodsInfo = getAllGoodsInfoByID(receiptDetail.getGoodsid());
                                    if(null!=goodsInfo){
                                        salesInvoiceBillDetail.setGoodssort(goodsInfo.getDefaultsort());
                                        salesInvoiceBillDetail.setBrandid(goodsInfo.getBrand());
                                        salesInvoiceBillDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceBillDetail.getCustomerid()));
                                        //厂家业务员
                                        salesInvoiceBillDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), customerid));
                                        //商品默认供应商
                                        salesInvoiceBillDetail.setSupplierid(goodsInfo.getDefaultsupplier());
                                    }
                                    salesInvoiceBillDetail.setUnitid(receiptDetail.getUnitid());
                                    salesInvoiceBillDetail.setUnitname(receiptDetail.getUnitname());
                                    salesInvoiceBillDetail.setUnitnum(receiptDetail.getReceiptnum());
                                    salesInvoiceBillDetail.setAuxunitid(receiptDetail.getAuxunitid());
                                    salesInvoiceBillDetail.setAuxunitname(receiptDetail.getAuxunitname());
                                    Map auxMap = countGoodsInfoNumber(receiptDetail.getGoodsid(), receiptDetail.getAuxunitid(), receiptDetail.getReceiptnum());
                                    salesInvoiceBillDetail.setAuxnum((BigDecimal) auxMap.get("auxnum"));
                                    salesInvoiceBillDetail.setAuxnumdetail( (String) auxMap.get("auxnumdetail"));

                                    //退货通知单生成的发票 金额为负
                                    salesInvoiceBillDetail.setTaxprice(receiptDetail.getTaxprice());
									BigDecimal taxamount=receiptDetail.getReceipttaxamount();
                                    salesInvoiceBillDetail.setTaxamount(taxamount);
                                    salesInvoiceBillDetail.setTaxtype(receiptDetail.getTaxtype());
                                    //重新计算税额税额
                                    BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, salesInvoiceBillDetail.getTaxtype());
                                    salesInvoiceBillDetail.setNotaxamount(notaxamount);
                                    //未税单价 = 未税金额/数量
                                    if(null!=notaxamount && notaxamount.compareTo(BigDecimal.ZERO)!=0
                                            && null!=salesInvoiceBillDetail.getUnitnum() && salesInvoiceBillDetail.getUnitnum().compareTo(BigDecimal.ZERO)!=0){
                                        salesInvoiceBillDetail.setNotaxprice(notaxamount.divide(salesInvoiceBillDetail.getUnitnum(), 6,BigDecimal.ROUND_HALF_UP));
                                    }else{
                                        salesInvoiceBillDetail.setNotaxprice(receiptDetail.getNotaxprice());
                                    }
                                    salesInvoiceBillDetail.setTax(salesInvoiceBillDetail.getTaxamount().subtract(salesInvoiceBillDetail.getNotaxamount()));
                                    salesInvoiceBillDetail.setRemark(receiptDetail.getRemark());
                                    salesInvoiceBillDetail.setSeq(receiptDetail.getSeq());
									//针对不同税种生成多条单据，用来保存单据的金额信息
									if(invoiceBillMap.containsKey(receiptDetail.getTaxtype())){
										Map tmap= (Map) invoiceBillMap.get(receiptDetail.getTaxtype());
										tmap=getSalesInvoiceBillMap(tmap,receiptDetail.getReceipttaxamount(),notaxamount,iswriteoff,customerid,receipt.getCustomerid());
										invoiceBillMap.put(receiptDetail.getTaxtype(),tmap);
										SalesInvoiceBill bill=(SalesInvoiceBill)tmap.get("bill");
										salesInvoiceBillDetail.setBillid(bill.getId());
									}
                                    salesInvoiceBillDetailList.add(salesInvoiceBillDetail);
                                }
                            }
                        }
                    }else if("2".equals(billtype)){
                        RejectBill rejectBill = null;
                        if(billObjectMap.containsKey(id)){
                            rejectBill = (RejectBill) billObjectMap.get(id);
                        }else{
                            rejectBill = salesOutService.getRejectBill(id);
                            billObjectMap.put(id, rejectBill);
                        }
                        if(null!=rejectBill){
                            RejectBillDetail rejectBillDetail = salesOutService.getRejectBillDetailInfo(detailid, id);
                            if(null!=rejectBillDetail){
                                salesInvoiceBillDetail = new SalesInvoiceBillDetail();
                                //来源销售退货通知单
                                salesInvoiceBillDetail.setSourcetype("2");
                                salesInvoiceBillDetail.setSourceid(rejectBill.getId());
                                salesInvoiceBillDetail.setSourcedetailid(rejectBillDetail.getId());
                                salesInvoiceBillDetail.setBillno(rejectBillDetail.getBillid());
                                salesInvoiceBillDetail.setBilldetailid(rejectBillDetail.getId());
                                salesInvoiceBillDetail.setCustomerid(rejectBill.getCustomerid()!=null?rejectBill.getCustomerid():"");
                                salesInvoiceBillDetail.setPcustomerid(rejectBill.getPcustomerid()!=null?rejectBill.getPcustomerid():"");
                                salesInvoiceBillDetail.setCustomersort(rejectBill.getCustomersort()!=null?rejectBill.getCustomersort():"");
                                salesInvoiceBillDetail.setSalesarea(rejectBill.getSalesarea()!=null?rejectBill.getSalesarea():"");
                                salesInvoiceBillDetail.setSalesdept(rejectBill.getSalesdept()!=null?rejectBill.getSalesdept():"");
                                salesInvoiceBillDetail.setSalesuser(rejectBill.getSalesuser()!=null?rejectBill.getSalesuser():"");
                                salesInvoiceBillDetail.setGoodsid(rejectBillDetail.getGoodsid()!=null?rejectBillDetail.getGoodsid():"");
                                salesInvoiceBillDetail.setBranddept(rejectBillDetail.getBranddept()!=null?rejectBillDetail.getBranddept():"");
                                salesInvoiceBillDetail.setCostprice(rejectBillDetail.getCostprice());
                                //商品品牌 和品牌业务员信息
                                GoodsInfo goodsInfo = getAllGoodsInfoByID(rejectBillDetail.getGoodsid());
                                if(null!=goodsInfo){
                                    salesInvoiceBillDetail.setGoodssort(goodsInfo.getDefaultsort());
                                    salesInvoiceBillDetail.setBrandid(goodsInfo.getBrand());
                                    salesInvoiceBillDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceBillDetail.getCustomerid()));
                                    //厂家业务员
                                    salesInvoiceBillDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), customerid));
                                    //商品默认供应商
                                    salesInvoiceBillDetail.setSupplierid(goodsInfo.getDefaultsupplier());
                                }
                                salesInvoiceBillDetail.setUnitid(rejectBillDetail.getUnitid());
                                salesInvoiceBillDetail.setUnitname(rejectBillDetail.getUnitname());
                                salesInvoiceBillDetail.setUnitnum(rejectBillDetail.getUnitnum().negate());
                                salesInvoiceBillDetail.setAuxunitid(rejectBillDetail.getAuxunitid());
                                salesInvoiceBillDetail.setAuxunitname(rejectBillDetail.getAuxunitname());
                                Map auxMap = countGoodsInfoNumber(rejectBillDetail.getGoodsid(), rejectBillDetail.getAuxunitid(), rejectBillDetail.getUnitnum().negate());
                                salesInvoiceBillDetail.setAuxnum((BigDecimal) auxMap.get("auxnum"));
                                salesInvoiceBillDetail.setAuxnumdetail((String) auxMap.get("auxnumdetail"));

                                //退货通知单生成的发票 金额为负
                                salesInvoiceBillDetail.setTaxprice(rejectBillDetail.getTaxprice());
								BigDecimal taxamount=rejectBillDetail.getTaxamount().negate();
                                salesInvoiceBillDetail.setTaxamount(taxamount);
                                salesInvoiceBillDetail.setTaxtype(rejectBillDetail.getTaxtype());
                                //重新计算税额税额
                                BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, salesInvoiceBillDetail.getTaxtype());
                                salesInvoiceBillDetail.setNotaxamount(notaxamount);
                                //未税单价 = 未税金额/数量
                                if(null!=notaxamount && notaxamount.compareTo(BigDecimal.ZERO)!=0
                                        && null!=salesInvoiceBillDetail.getUnitnum() && salesInvoiceBillDetail.getUnitnum().compareTo(BigDecimal.ZERO)!=0){
                                    salesInvoiceBillDetail.setNotaxprice(notaxamount.divide(salesInvoiceBillDetail.getUnitnum(), 6,BigDecimal.ROUND_HALF_UP));
                                }else{
                                    salesInvoiceBillDetail.setNotaxprice(rejectBillDetail.getNotaxprice());
                                }
                                salesInvoiceBillDetail.setTax(salesInvoiceBillDetail.getTaxamount().subtract(salesInvoiceBillDetail.getNotaxamount()));
                                salesInvoiceBillDetail.setRemark(rejectBillDetail.getRemark());
                                salesInvoiceBillDetail.setSeq(rejectBillDetail.getSeq());
								//针对不同税种生成多条单据，用来保存单据的金额信息
								if(invoiceBillMap.containsKey(rejectBillDetail.getTaxtype())){
									Map tmap= (Map) invoiceBillMap.get(rejectBillDetail.getTaxtype());
									tmap=getSalesInvoiceBillMap(tmap,rejectBillDetail.getTaxamount().negate(),notaxamount,iswriteoff,customerid,rejectBill.getCustomerid());
									invoiceBillMap.put(rejectBillDetail.getTaxtype(),tmap);
									SalesInvoiceBill bill=(SalesInvoiceBill)tmap.get("bill");
									salesInvoiceBillDetail.setBillid(bill.getId());
								}
                                salesInvoiceBillDetailList.add(salesInvoiceBillDetail);
                            }
                        }
                    }else if("3".equals(billtype)){
                        CustomerPushBalance customerPushBalance = null;
                        if(billObjectMap.containsKey(id)){
                            customerPushBalance = (CustomerPushBalance) billObjectMap.get(id);
                        }else{
                            customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(id);
                            billObjectMap.put(id, customerPushBalance);
                        }
                        if(null!=customerPushBalance){
                            salesInvoiceBillDetail = new SalesInvoiceBillDetail();
							salesInvoiceBillDetail.setIsdiscount(isdiscount);
							salesInvoiceBillDetail.setIsbranddiscount(isbranddiscount);
                            //来源销售退货通知单
                            salesInvoiceBillDetail.setSourcetype("3");
                            salesInvoiceBillDetail.setSourceid(customerPushBalance.getId());
                            salesInvoiceBillDetail.setSourcedetailid(customerPushBalance.getId());
                            salesInvoiceBillDetail.setCustomerid(customerPushBalance.getCustomerid());
                            salesInvoiceBillDetail.setPcustomerid(customerPushBalance.getPcustomerid()!=null?customerPushBalance.getPcustomerid():"");
                            salesInvoiceBillDetail.setCustomersort(customerPushBalance.getCustomersort()!=null?customerPushBalance.getCustomersort():"");
                            salesInvoiceBillDetail.setSalesarea(customerPushBalance.getSalesarea()!=null?customerPushBalance.getSalesarea():"");
                            salesInvoiceBillDetail.setSalesdept(customerPushBalance.getSalesdept()!=null?customerPushBalance.getSalesdept():"");
                            salesInvoiceBillDetail.setSalesuser(customerPushBalance.getSalesuser()!=null?customerPushBalance.getSalesuser():"");
                            salesInvoiceBillDetail.setGoodsid(customerPushBalance.getBrandid()!=null?customerPushBalance.getBrandid():"");
                            salesInvoiceBillDetail.setBranddept(customerPushBalance.getBranddept()!=null?customerPushBalance.getBranddept():"");

                            salesInvoiceBillDetail.setBrandid(customerPushBalance.getBrandid());
                            salesInvoiceBillDetail.setBranduser(getBrandUseridByCustomeridAndBrand(customerPushBalance.getBrandid(), salesInvoiceBillDetail.getCustomerid()));
                            //厂家业务员
                            salesInvoiceBillDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(customerPushBalance.getBrandid(), salesInvoiceBillDetail.getCustomerid()));
                            Brand brand = getGoodsBrandByID(customerPushBalance.getBrandid());
                            if(null!=brand){
                                //商品默认供应商
                                salesInvoiceBillDetail.setSupplierid(brand.getSupplierid());
                            }
                            salesInvoiceBillDetail.setUnitid("");
                            salesInvoiceBillDetail.setUnitname("");
                            salesInvoiceBillDetail.setUnitnum(BigDecimal.ZERO);
                            salesInvoiceBillDetail.setAuxunitid("");
                            salesInvoiceBillDetail.setAuxunitname("");
                            salesInvoiceBillDetail.setAuxnum(BigDecimal.ZERO);
                            salesInvoiceBillDetail.setAuxnumdetail("");

                            //退货通知单生成的发票 金额为负
                            salesInvoiceBillDetail.setTaxprice(BigDecimal.ZERO);
							BigDecimal taxamount=customerPushBalance.getAmount();
                            salesInvoiceBillDetail.setTaxamount(taxamount);
                            salesInvoiceBillDetail.setNotaxprice(BigDecimal.ZERO);
                            salesInvoiceBillDetail.setTaxtype(customerPushBalance.getDefaulttaxtype());
                            //重新计算税额税额
                            BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, salesInvoiceBillDetail.getTaxtype());
                            salesInvoiceBillDetail.setNotaxamount(notaxamount);
                            salesInvoiceBillDetail.setTax(salesInvoiceBillDetail.getTaxamount().subtract(salesInvoiceBillDetail.getNotaxamount()));
                            salesInvoiceBillDetail.setRemark(customerPushBalance.getRemark());
                            salesInvoiceBillDetail.setSeq(999);
							//针对不同税种生成多条单据，用来保存单据的金额信息
							if(invoiceBillMap.containsKey(customerPushBalance.getDefaulttaxtype())){
								Map tmap= (Map) invoiceBillMap.get(customerPushBalance.getDefaulttaxtype());
								tmap=getSalesInvoiceBillMap(tmap,customerPushBalance.getAmount(),notaxamount,iswriteoff,customerid,customerPushBalance.getCustomerid());
								invoiceBillMap.put(customerPushBalance.getDefaulttaxtype(),tmap);
								SalesInvoiceBill bill=(SalesInvoiceBill)tmap.get("bill");
								salesInvoiceBillDetail.setBillid(bill.getId());
							}
                            salesInvoiceBillDetailList.add(salesInvoiceBillDetail);
                        }
                    }
                }
                if(salesInvoiceBillDetailList.size()<500){
                    salesInvoiceBillMapper.addSalesInvoiceBillDetailList(salesInvoiceBillDetailList);
                }else{
                    int num = salesInvoiceBillDetailList.size()/500 +1;
                    for(int i=1;i<=num;i++){
                        if(i<num){
                            List addList = new ArrayList(salesInvoiceBillDetailList.subList((i-1)*500, i*500));
                            if(null!=addList && addList.size()>0){
                                salesInvoiceBillMapper.addSalesInvoiceBillDetailList(addList);
                            }
                        }else{
                            List addList = new ArrayList(salesInvoiceBillDetailList.subList((i-1)*500, salesInvoiceBillDetailList.size()));
                            if(null!=addList && addList.size()>0){
                                salesInvoiceBillMapper.addSalesInvoiceBillDetailList(addList);
                            }
                        }
                    }
                }

				for(TaxType taxType:taxList){
					Map map=(Map)invoiceBillMap.get(taxType.getId());
					BigDecimal taxamount=(BigDecimal)map.get("taxamount");
					BigDecimal notaxamount=(BigDecimal)map.get("notaxamount");
					String customerids=(String)map.get("customerids");
					if(map.containsKey("taxamount")){
						SalesInvoiceBill salesInvoiceBill = (SalesInvoiceBill) map.get("bill");
						SysUser sysUser = getSysUser();
						salesInvoiceBill.setAdddeptid(sysUser.getDepartmentid());
						salesInvoiceBill.setAdddeptname(sysUser.getDepartmentname());
						salesInvoiceBill.setAdduserid(sysUser.getUserid());
						salesInvoiceBill.setAddusername(sysUser.getName());
						salesInvoiceBill.setChlidcustomerid(customerids);
						salesInvoiceBill.setTaxamount(taxamount);
						salesInvoiceBill.setNotaxamount(notaxamount);
						List<String> sourceidList = salesInvoiceBillMapper.getSalesInvoiceBillSouceidList(salesInvoiceBill.getId());
						String billsourceid = "";
						for (String sourceid : sourceidList) {
							if ("".equals(billsourceid)) {
								billsourceid = sourceid;
							} else {
								billsourceid += "," + sourceid;
							}
						}
						salesInvoiceBill.setSourceid(billsourceid);
						int i = salesInvoiceBillMapper.addSalesInvoiceBill(salesInvoiceBill);
						flag = i>0;
						if (flag) {
							salesInvoiceid = salesInvoiceBill.getId();
							salesOutService.updateReceiptAndRejectBillInvoiceBill(sourceidList, salesInvoiceBill, "1");
						} else {
							throw new Exception("申请开票出错");
						}
					}

				}
            }
        }
        Map map = new HashMap();
        map.put("flag", flag);
        map.put("id", salesInvoiceid);
        map.put("msg", msg);
        return map;
    }
    public List getSalesInvoiceDetailTaxtypeCountList(String invoiceids) throws Exception{
    	return salesInvoiceMapper.getSalesInvoiceDetailTaxtypeCountList(invoiceids);
    }
	/**
	 * TODO HERE
	 * @param map
	 * @param amount 含税金额
	 * @param noamount 未税金额
	 * @param iswriteoff
	 * @param customerid 开票客户
	 * @param cid 单据明细的客户
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date May 15, 2017
	 */
	private Map getSalesInvoiceBillMap(Map map,BigDecimal amount,BigDecimal noamount,String iswriteoff,String customerid,String cid) throws Exception {
		BigDecimal taxamount;
		if(!map.containsKey("taxamount")){
			taxamount= amount;
		}else{
			taxamount= (BigDecimal) map.get("taxamount");
			taxamount=taxamount.add(amount);
		}
		map.put("taxamount",taxamount);
		BigDecimal notaxamount;
		if(!map.containsKey("notaxamount")){
			notaxamount= noamount;
		}else{
			notaxamount= (BigDecimal) map.get("notaxamount");
			notaxamount=notaxamount.add(noamount);
		}
		map.put("notaxamount",notaxamount);

		String customerids="";
		if(!map.containsKey("customerids")){
			customerids= cid;
		}else{
			customerids= (String) map.get("customerids");
			if(customerids.indexOf(cid)==-1){
				customerids=customerids+","+cid;
			}
		}
		map.put("customerids",customerids);

		SalesInvoiceBill bill=(SalesInvoiceBill)map.get("bill");
		if(bill==null){
			SalesInvoiceBill salesInvoiceBill = new SalesInvoiceBill();
			if (isAutoCreate("t_account_sales_invoicebill")) {
				// 获取自动编号
				String id = getAutoCreateSysNumbderForeign(salesInvoiceBill, "t_account_sales_invoicebill");
				salesInvoiceBill.setId(id);
			}else{
				salesInvoiceBill.setId("XSKP-"+CommonUtils.getDataNumberSendsWithRand());
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			salesInvoiceBill.setBusinessdate(dateFormat.format(calendar.getTime()));
			//单据类型1正常开票2预开票
			salesInvoiceBill.setBilltype("1");
			//判断是否申请核销1是0否
			if("0".equals(iswriteoff)){
				salesInvoiceBill.setStatus("2");
			}else if("1".equals(iswriteoff)){
				salesInvoiceBill.setStatus("3");
			}
			//发票类型 1增值税
			//salesInvoice.setInvoicetype("1");

			Customer customer = getCustomerByID(customerid);
			salesInvoiceBill.setCustomerid(customerid);
			if(null!=customer){
				//salesInvoiceBill.setHandlerid(customer.getContact());
				salesInvoiceBill.setInvoicecustomerid(customerid);
				salesInvoiceBill.setSalesdept(customer.getSalesdeptid());
				salesInvoiceBill.setSalesuser(customer.getSalesuserid());
				salesInvoiceBill.setIndooruserid(customer.getIndoorstaff());
				//总店
				if("0".equals(customer.getIslast())){
					salesInvoiceBill.setPcustomerid(customer.getId());
				}else{
					salesInvoiceBill.setPcustomerid(customer.getPid());
				}
			}
			//开票客户
			salesInvoiceBill.setInvoicecustomerid(customer.getId());
			//获取该客户的上次开票客户名称
			String invoicecustomername = salesInvoiceBillMapper.getSalesInvoiceBillNameByCustomerid(customer.getId());
			if(null!=invoicecustomername){
				//开票客户名称
				salesInvoiceBill.setInvoicecustomername(invoicecustomername);
			}else{
				//开票客户名称
				salesInvoiceBill.setInvoicecustomername(customer.getName());
			}
			map.put("bill",salesInvoiceBill);
		}
		return map;
	}

}

