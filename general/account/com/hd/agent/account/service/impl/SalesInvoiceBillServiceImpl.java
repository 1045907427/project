/**
 * @(#)SalesInvoiceBillServiceImpl.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Feb 14, 2015 panxiaoxiao 创建版本
 */
package com.hd.agent.account.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import com.hd.agent.account.model.*;
import com.hd.agent.basefiles.dao.FinanceMapper;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.sales.dao.ReceiptMapper;
import com.hd.agent.storage.model.SaleRejectEnter;
import com.hd.agent.system.model.SysParam;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.CustomerCapitalMapper;
import com.hd.agent.account.dao.CustomerPushBalanceMapper;
import com.hd.agent.account.dao.SalesInvoiceBillMapper;
import com.hd.agent.account.dao.SalesInvoiceMapper;
import com.hd.agent.account.dao.SalesStatementMapper;
import com.hd.agent.account.service.ISalesInvoiceBillService;
import com.hd.agent.account.service.ISalesStatementService;
import com.hd.agent.basefiles.service.ISalesExtService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.Receipt;
import com.hd.agent.sales.model.ReceiptDetail;
import com.hd.agent.sales.model.RejectBill;
import com.hd.agent.sales.model.RejectBillDetail;
import com.hd.agent.sales.service.ISalesOutService;
import com.hd.agent.storage.model.Saleout;
import com.hd.agent.storage.model.SaleoutDetail;
import com.hd.agent.storage.service.IStorageSaleOutService;
import com.hd.agent.storage.service.IStorageSaleService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * 申请开票借口实现类
 * @author panxiaoxiao
 */
public class SalesInvoiceBillServiceImpl extends BaseAccountServiceImpl implements ISalesInvoiceBillService{

	private SalesInvoiceBillMapper salesInvoiceBillMapper;

    private SalesInvoiceMapper salesInvoiceMapper;
	
	private CustomerCapitalMapper customerCapitalMapper;

	private ISalesOutService salesOutService;
	
	private IStorageSaleService storageSaleService;
	
	private ISalesExtService salesExtService;

    private IStorageSaleOutService storageSaleOutService;
	
	private CustomerPushBalanceMapper customerPushBalanceMapper;

    private ReceiptMapper receiptMapper;
	
	private ISalesStatementService salesStatementService;
	
	private SalesStatementMapper salesStatementMapper;

	private FinanceMapper financeMapper;

    public ReceiptMapper getReceiptMapper() {
        return receiptMapper;
    }

    public void setReceiptMapper(ReceiptMapper receiptMapper) {
        this.receiptMapper = receiptMapper;
    }

    public IStorageSaleOutService getStorageSaleOutService() {
        return storageSaleOutService;
    }

    public void setStorageSaleOutService(IStorageSaleOutService storageSaleOutService) {
        this.storageSaleOutService = storageSaleOutService;
    }

    public SalesInvoiceMapper getSalesInvoiceMapper() {
        return salesInvoiceMapper;
    }

    public void setSalesInvoiceMapper(SalesInvoiceMapper salesInvoiceMapper) {
        this.salesInvoiceMapper = salesInvoiceMapper;
    }

    public SalesStatementMapper getSalesStatementMapper() {
		return salesStatementMapper;
	}

	public void setSalesStatementMapper(SalesStatementMapper salesStatementMapper) {
		this.salesStatementMapper = salesStatementMapper;
	}

    public ISalesStatementService getSalesStatementService() {
		return salesStatementService;
	}

	public void setSalesStatementService(
			ISalesStatementService salesStatementService) {
		this.salesStatementService = salesStatementService;
	}

	public CustomerPushBalanceMapper getCustomerPushBalanceMapper() {
		return customerPushBalanceMapper;
	}

	public void setCustomerPushBalanceMapper(
			CustomerPushBalanceMapper customerPushBalanceMapper) {
		this.customerPushBalanceMapper = customerPushBalanceMapper;
	}

	public ISalesExtService getSalesExtService() {
		return salesExtService;
	}

	public void setSalesExtService(ISalesExtService salesExtService) {
		this.salesExtService = salesExtService;
	}

	public ISalesOutService getSalesOutService() {
		return salesOutService;
	}

	public void setSalesOutService(ISalesOutService salesOutService) {
		this.salesOutService = salesOutService;
	}

	public IStorageSaleService getStorageSaleService() {
		return storageSaleService;
	}

	public void setStorageSaleService(IStorageSaleService storageSaleService) {
		this.storageSaleService = storageSaleService;
	}

	public SalesInvoiceBillMapper getSalesInvoiceBillMapper() {
		return salesInvoiceBillMapper;
	}

	public void setSalesInvoiceBillMapper(
			SalesInvoiceBillMapper salesInvoiceBillMapper) {
		this.salesInvoiceBillMapper = salesInvoiceBillMapper;
	}

	public CustomerCapitalMapper getCustomerCapitalMapper() {
		return customerCapitalMapper;
	}

	public void setCustomerCapitalMapper(CustomerCapitalMapper customerCapitalMapper) {
		this.customerCapitalMapper = customerCapitalMapper;
	}

	public FinanceMapper getFinanceMapper() {
		return financeMapper;
	}

	public void setFinanceMapper(FinanceMapper financeMapper) {
		this.financeMapper = financeMapper;
	}

	@Override
	public PageData getSalesInvoiceBillData(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_account_sales_invoicebill", "t");
		pageMap.setDataSql(dataSql);
//		String dataSqlPush = getDataAccessRule("t_account_customer_push_balance", "t");
//		pageMap.getCondition().put("dataSqlPush", dataSqlPush);
		List<SalesInvoiceBill> list = salesInvoiceBillMapper.getSalesInvoiceBillList(pageMap);
        Map condition = pageMap.getCondition();

        //对存在多个销售发货回单编号的进行拆分查询
        if(condition.containsKey("stockid") && list.size() == 0){
            Receipt receipt = receiptMapper.getReceiptBySaleorderid((String) condition.get("stockid"));
            if(null != receipt){
                condition.remove("stockid");
                condition.put("id",receipt.getId());
                pageMap.setCondition(condition);
                list = salesInvoiceBillMapper.getSalesInvoiceBillList(pageMap);
            }
        }
        //多个来源单据编号中存在销售退货通知单的
        if(list.size() == 0){
            SaleRejectEnter saleRejectEnter = getBaseSaleRejectEnterMapper().getSaleRejectEnterByID((String) condition.get("stockid"));
            if(null != saleRejectEnter){
                condition.remove("stockid");
                condition.put("id",saleRejectEnter.getSourceid());
                pageMap.setCondition(condition);
                list = salesInvoiceBillMapper.getSalesInvoiceBillList(pageMap);
            }

        }
		BigDecimal totalCstAmount = new BigDecimal(0);//总账户余额
		for(SalesInvoiceBill salesInvoiceBill : list){
			CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(salesInvoiceBill.getCustomerid());
			if(null!=customerCapital){
				salesInvoiceBill.setCustomeramount(customerCapital.getAmount());
				totalCstAmount = totalCstAmount.add(salesInvoiceBill.getCustomeramount());
			}else{
				salesInvoiceBill.setCustomeramount(BigDecimal.ZERO);
			}
			Customer customer = getCustomerByID(salesInvoiceBill.getCustomerid());
			if(null!=customer){
				salesInvoiceBill.setCustomername(customer.getName());
			}
			if(null!=salesInvoiceBill.getChlidcustomerid()){
				String[] childcustomer = salesInvoiceBill.getChlidcustomerid().split(",");
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
				salesInvoiceBill.setChlidcustomername(childcustomername);
			}
			DepartMent departMent = getDepartmentByDeptid(salesInvoiceBill.getSalesdept());
			if(null!=departMent){
				salesInvoiceBill.setSalesdeptname(departMent.getName());
			}
			Personnel personnel = getPersonnelById(salesInvoiceBill.getSalesuser());
			if(null!=personnel){
				salesInvoiceBill.setSalesusername(personnel.getName());
			}
		}
		PageData pageData = new PageData(salesInvoiceBillMapper.getSalesInvoiceBillCount(pageMap),list,pageMap);
		
		//合计
		SalesInvoiceBill salesInvoiceBill = salesInvoiceBillMapper.getSalesInvoiceBillDataSum(pageMap);
		if(null!=salesInvoiceBill){
			List footer = new ArrayList();
			salesInvoiceBill.setId("合计");
			footer.add(salesInvoiceBill);
			pageData.setFooter(footer);
		}
		return pageData;
	}

	@Override
	public Map addSalesInvoiceBillByReceiptAndRejectbill(String ids,
														 String customerid, String iswriteoff) throws Exception {
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
				boolean canAddFlag = true;
				Map billObjectMap = new HashMap();
				for(int i=0;i<idArr.size();i++){
					JSONObject detailObject = (JSONObject) idArr.get(i);
					//单据类型 1回单2销售退货通知单
					String billtype = detailObject.getString("billtype");
					//单据编号
					String id = detailObject.getString("billid");
					//明细编号
					String detailid = detailObject.getString("detailid");
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
											salesInvoiceBillDetailList.add(salesInvoiceBillDetail);
										}else{
											canAddFlag = false;
										}
									}
								}else{
									salesInvoiceBillDetail = new SalesInvoiceBillDetail();
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
									salesInvoiceBillDetail.setTaxamount(receiptDetail.getReceipttaxamount());
									salesInvoiceBillDetail.setTaxtype(receiptDetail.getTaxtype());
									//重新计算税额税额
									BigDecimal notaxamount = getNotaxAmountByTaxAmount(salesInvoiceBillDetail.getTaxamount(), salesInvoiceBillDetail.getTaxtype());
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
										salesInvoiceBillDetailList.add(salesInvoiceBillDetail);
									}else{
										canAddFlag = false;
									}
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
								salesInvoiceBillDetail.setTaxamount(rejectBillDetail.getTaxamount().negate());
								salesInvoiceBillDetail.setTaxtype(rejectBillDetail.getTaxtype());

								//重新计算税额税额
								BigDecimal notaxamount = getNotaxAmountByTaxAmount(salesInvoiceBillDetail.getTaxamount(), salesInvoiceBillDetail.getTaxtype());
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
									salesInvoiceBillDetailList.add(salesInvoiceBillDetail);
								}else{
									canAddFlag = false;
								}
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
							salesInvoiceBillDetail.setTaxamount(customerPushBalance.getAmount());
							salesInvoiceBillDetail.setNotaxprice(BigDecimal.ZERO);
							salesInvoiceBillDetail.setTaxtype(customerPushBalance.getDefaulttaxtype());

							//重新计算税额税额
							BigDecimal notaxamount = getNotaxAmountByTaxAmount(salesInvoiceBillDetail.getTaxamount(), salesInvoiceBillDetail.getTaxtype());
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
								salesInvoiceBillDetailList.add(salesInvoiceBillDetail);
							}else{
								canAddFlag = false;
							}

						}
					}
				}
				if(canAddFlag){
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
								//回写来源单据开票日期
								writeBackSalesInvoiceBillSourceInvoiceDate(salesInvoiceid, "1");
							} else {
								throw new Exception("申请开票出错");
							}
						}

					}
				}else{
					msg = "明细中税种不正确。不能生成。";
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
	public Map getSalesInvoiceBillInfo(String id) throws Exception {
		Map map = new HashMap();
		SalesInvoiceBill salesInvoiceBill = salesInvoiceBillMapper.getSalesInvoiceBillInfo(id);
		if(null != salesInvoiceBill){
			CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(salesInvoiceBill.getCustomerid());
			if(null!=customerCapital){
				salesInvoiceBill.setCustomeramount(customerCapital.getAmount());
			}else{
				salesInvoiceBill.setCustomeramount(BigDecimal.ZERO);
			}
			Customer customer = getCustomerByID(salesInvoiceBill.getCustomerid());
			if(null!=customer){
				salesInvoiceBill.setCustomername(customer.getName());
			}
			Personnel personnel = getPersonnelById(salesInvoiceBill.getSalesuser());
			if(null!=personnel){
				salesInvoiceBill.setSalesusername(personnel.getName());
			}
			List<SalesInvoiceBillDetail> detailList = salesInvoiceBillMapper.getSalesInvoiceBillDetailListSumBranddiscount(id);
			for(SalesInvoiceBillDetail salesInvoiceBillDetail : detailList){
				if("3".equals(salesInvoiceBillDetail.getSourcetype())){
					GoodsInfo goodsInfo = new GoodsInfo();
					Brand brand = getGoodsBrandByID(salesInvoiceBillDetail.getBrandid());
					if(null!=brand){
						goodsInfo.setName("(折扣)"+brand.getName());
						salesInvoiceBillDetail.setGoodsInfo(goodsInfo);
						salesInvoiceBillDetail.setUnitnum(new BigDecimal(1));
						salesInvoiceBillDetail.setTaxprice(salesInvoiceBillDetail.getTaxamount());
						salesInvoiceBillDetail.setNotaxprice(salesInvoiceBillDetail.getNotaxamount());
						if(StringUtils.isNotEmpty(salesInvoiceBillDetail.getTaxtype())){
							TaxType taxType = getTaxType(salesInvoiceBillDetail.getTaxtype());
							if(null!=taxType){
								salesInvoiceBillDetail.setTaxtypename(taxType.getName());
							}
						}
					}
				}else{
					if(StringUtils.isNotEmpty(salesInvoiceBillDetail.getTaxtype())){
						TaxType taxType = getTaxType(salesInvoiceBillDetail.getTaxtype());
						if(null!=taxType){
							salesInvoiceBillDetail.setTaxtypename(taxType.getName());
						}
					}
					Map auxmap = countGoodsInfoNumber(salesInvoiceBillDetail.getGoodsid(), salesInvoiceBillDetail.getAuxunitid(), salesInvoiceBillDetail.getUnitnum());
					String auxnumdetail = (String) auxmap.get("auxnumdetail");
					salesInvoiceBillDetail.setAuxnumdetail(auxnumdetail);
					
					//折扣显示处理
					GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(getAllGoodsInfoByID(salesInvoiceBillDetail.getGoodsid()));
					if("1".equals(salesInvoiceBillDetail.getIsdiscount())){
						goodsInfo.setBarcode(null);
						goodsInfo.setBoxnum(null);
						goodsInfo.setName("(折扣)"+goodsInfo.getName());
						//salesInvoiceDetail.setUnitnum(null);
						salesInvoiceBillDetail.setUnitnum(new BigDecimal(1));
						salesInvoiceBillDetail.setAuxnumdetail(null);
						salesInvoiceBillDetail.setTaxprice(null);
						if("1".equals(salesInvoiceBillDetail.getIsbranddiscount())){
							salesInvoiceBillDetail.setGoodsid("");
							goodsInfo.setName("(折扣)"+goodsInfo.getBrandName());
//							salesInvoiceDetail.setIsdiscount("2");
						}
					}
					salesInvoiceBillDetail.setGoodsInfo(goodsInfo);
				}
				//返利折扣
				if("2".equals(salesInvoiceBillDetail.getIsdiscount())){
					GoodsInfo goodsInfo = new GoodsInfo();
					goodsInfo.setName("折扣");
					salesInvoiceBillDetail.setGoodsInfo(goodsInfo);
					salesInvoiceBillDetail.setUnitnum(new BigDecimal(1));
				}
			}
			List<String> customerList = salesInvoiceBillMapper.getSalesInvoiceBillCustomerList(id);
			boolean isHeader = true;
			String customerids = "";
			List customerArr = new ArrayList();
			for(String cusotmerid : customerList){
				if(cusotmerid.equals(salesInvoiceBill.getCustomerid())){
					isHeader = false;
				}
				Customer newcustomer = getCustomerByID(cusotmerid);
				customerArr.add(newcustomer);
			}
			if(isHeader){
				Customer newcustomer = (Customer) CommonUtils.deepCopy(getCustomerByID(salesInvoiceBill.getCustomerid())) ;
				String invoicecustomername = salesInvoiceBillMapper.getSalesInvoiceBillNameByCustomerid(salesInvoiceBill.getCustomerid());
				if(StringUtils.isNotEmpty(invoicecustomername)){
					newcustomer.setName(invoicecustomername);
				}
				customerArr.add(newcustomer);
			}
			map.put("salesInvoiceBill", salesInvoiceBill);
			map.put("detailList", detailList);
			map.put("customerArr", customerArr);
		}
		return map;
	}

	@Override
	public int getSalesInvoiceBillHasBlance(String invoicebillid)
			throws Exception {
		int icount=0;
		if(null==invoicebillid || "".equals(invoicebillid.trim())){
			return icount;
		}
		Map map=new HashMap();
		map.put("invoiceid", invoicebillid.trim());
		map.put("statusarr", "3,4");
		icount=customerPushBalanceMapper.getCustomerPushBanlanceCountBy(map);
		return icount;
	}

	@Override
	public Map editSalesInvoiceBill(SalesInvoiceBill salesInvoiceBill,
			String delgoodsids) throws Exception {
		boolean flag = false;
		String msg = "";
		SalesInvoiceBill oldSalesInvoiceBill = salesInvoiceBillMapper.getSalesInvoiceBillInfo(salesInvoiceBill.getId());
		if(null != oldSalesInvoiceBill && "2".equals(oldSalesInvoiceBill.getStatus()) || "1".equals(oldSalesInvoiceBill.getStatus()) || "5".equals(oldSalesInvoiceBill.getStatus())){
			
			int icount=salesInvoiceBillMapper.getSalesInvoiceBillDetailTaxtypeCount(salesInvoiceBill.getId());
			if(icount>1){
				Map map = new HashMap();
				map.put("flag", false);
				map.put("msg", "相同税种的明细才能开票，当前明细中存在不同税种。");
				map.put("id", salesInvoiceBill.getId());
				return map;
			}
			
			String[] goodsidArr = delgoodsids.split(",");
			//明细删除前 更新上游单据开票状态
			if(!"".equals(delgoodsids) && null!=goodsidArr && goodsidArr.length>0){
				List sourceidList = salesInvoiceBillMapper.getSalesInvoiceBillSouceidList(salesInvoiceBill.getId());
                if("1".equals(oldSalesInvoiceBill.getBilltype())){//正常开票
                    salesOutService.updateReceiptAndRejectBillInvoiceBill(sourceidList,salesInvoiceBill,"0");
                }else if("2".equals(oldSalesInvoiceBill.getBilltype())){//预开票
                    storageSaleOutService.updateSaleoutAdvanceBill(sourceidList,salesInvoiceBill,"0");
                }
			}
			for(String goodsid : goodsidArr){
				salesInvoiceBillMapper.deleteSalesInvoiceBillDetailByGoodsid(salesInvoiceBill.getId(), goodsid);
			}
			List<SalesInvoiceBillDetail> detailList = salesInvoiceBillMapper.getSalesInvoiceBillDetailList(salesInvoiceBill.getId());
			BigDecimal taxamount = BigDecimal.ZERO;
			BigDecimal notaxamount = BigDecimal.ZERO;
			
			for(SalesInvoiceBillDetail salesInvoiceBillDetail : detailList){
				salesInvoiceBillDetail.setBillid(salesInvoiceBill.getId());
				//商品品牌 和品牌业务员信息
				GoodsInfo goodsInfo = getAllGoodsInfoByID(salesInvoiceBillDetail.getGoodsid());
				if(null!=goodsInfo){
					salesInvoiceBillDetail.setGoodssort(goodsInfo.getDefaultsort());
					salesInvoiceBillDetail.setBrandid(goodsInfo.getBrand());
					salesInvoiceBillDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceBillDetail.getCustomerid()));
					//厂家业务员
					salesInvoiceBillDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceBillDetail.getCustomerid()));
					//默认供应商
					salesInvoiceBillDetail.setSupplierid(goodsInfo.getDefaultsupplier());
				}
				taxamount = taxamount.add(salesInvoiceBillDetail.getTaxamount());
				notaxamount = notaxamount.add(salesInvoiceBillDetail.getNotaxamount());
			}
			List<String> sourceidList = salesInvoiceBillMapper.getSalesInvoiceBillSouceidList(salesInvoiceBill.getId());
			String billsourceid = "";
			for(String sourceid : sourceidList){
				if("".equals(billsourceid)){
					billsourceid = sourceid;
				}else{
					billsourceid += ","+sourceid;
				}
			}
			salesInvoiceBill.setSourceid(billsourceid);
			salesInvoiceBill.setTaxamount(taxamount);
			salesInvoiceBill.setNotaxamount(notaxamount);
			SysUser sysUser = getSysUser();
			salesInvoiceBill.setModifyuserid(sysUser.getUserid());
			salesInvoiceBill.setModifyusername(sysUser.getName());
			if("5".equals(salesInvoiceBill.getStatus())){
				salesInvoiceBill.setStatus("2");
			}
			int i = salesInvoiceBillMapper.editSalesInvoiceBill(salesInvoiceBill);
			flag = i>0;
			if(flag && ((!"".equals(delgoodsids) && null!=goodsidArr && goodsidArr.length>0) || null!=salesInvoiceBill.getInvoiceno() && !"".equals(salesInvoiceBill.getInvoiceno()))){
                if("1".equals(oldSalesInvoiceBill.getBilltype())){//正常开票
                    salesOutService.updateReceiptAndRejectBillInvoiceBill(sourceidList,salesInvoiceBill,"1");
                }else if("2".equals(oldSalesInvoiceBill.getBilltype())){//预开票
                    storageSaleOutService.updateSaleoutAdvanceBill(sourceidList,salesInvoiceBill,"1");
                }
			}
		}else{
			msg = "销售开票已审核或者核销。不能进行保存。";	
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("id", salesInvoiceBill.getId());
		return map;
	}

	/**
	 * 回写销售开票来源单据开票日期
	 * @param invoicebillid
	 * @throws Exception
	 */
	private boolean writeBackSalesInvoiceBillSourceInvoiceDate(String invoicebillid,String hasinvoicedate)throws Exception{
		Map map = new HashMap();
		map.put("hasinvoicedate",hasinvoicedate);
		List<String> list = salesInvoiceBillMapper.getSalesInvoiceBillSouceidList(invoicebillid);
		String invoicebilldate = getAuditBusinessdate(CommonUtils.getTodayDataStr());
		map.put("invoicebilldate",invoicebilldate);
		for(String sourceid : list){
			map.put("id",sourceid);
			Receipt receipt = salesOutService.getReceiptInfo(sourceid);
			if(null != receipt){
				//回写回单开票日期
				salesOutService.updateReceiptInvoicedate(map);
				//根据回单编号更新回单冲差开票日期
				customerPushBalanceMapper.updateReceiptCustomerPushBalanceInvoicebilldate(sourceid,hasinvoicedate,invoicebilldate);
				//回写发货单开票日期
				storageSaleService.updateSaleOutInvoicebilldate(receipt.getId(),hasinvoicedate,invoicebilldate);
			}else{
				RejectBill rejectBill = salesOutService.getRejectBillInfo(sourceid);
				if(null != rejectBill){
					//回写销售通知单单开票日期
					salesOutService.updateRejectBillInvoicedate(map);
					//回写销售退货入库单开票日期
					storageSaleService.updateRejectEnterInvoicebilldate(rejectBill.getId(),hasinvoicedate,invoicebilldate);
				} else {
					CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(sourceid);
					if(null != customerPushBalance){
						customerPushBalanceMapper.updateCustomerPushBanlanceInvoicedate(map);
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean auditSalesInvoiceBill(String id) throws Exception {
		SalesInvoiceBill salesInvoiceBill = salesInvoiceBillMapper.getSalesInvoiceBillInfo(id);
		boolean flag = false;
		if("2".equals(salesInvoiceBill.getStatus()) || "6".equals(salesInvoiceBill.getStatus())){
			//审核业务日期
			String auditBusinessdate = getAuditBusinessdate(salesInvoiceBill.getBusinessdate());
			SysUser sysUser = getSysUser();
			int i = salesInvoiceBillMapper.auditSalesInvoiceBill(id, sysUser.getUserid(), sysUser.getName(),auditBusinessdate);
			flag = i>0;

			//销售开票审核
			if(flag ){
				//回写来源单据开票日期
				writeBackSalesInvoiceBillSourceInvoiceDate(id,"1");
				if(StringUtils.isNotEmpty(salesInvoiceBill.getSalesinvoiceid())){
					//判断销售开票明细与销售核销明细是否一致，若一致,判断开票明细是否全部核销，若全部已核销关闭销售开票且回写销售核销的发票号、发票代码，开票状态为“已开票”
					boolean flag1 = checkSalesInvoiceBillDetailsSameAsSalesInvoiceDetails(id,salesInvoiceBill.getSalesinvoiceid());
					if(flag1){
						boolean flag2 = salesInvoiceBillMapper.checkSalesInvoiceBillDetailHasNoWriteoff(salesInvoiceBill.getId()) > 0;
						if(!flag2){
							salesInvoiceBillMapper.closeSalesInvoiceBill(salesInvoiceBill.getId());
						}

						Map map2 = new HashMap();
						map2.put("invoiceno",salesInvoiceBill.getInvoiceno());
						map2.put("invoicecode",salesInvoiceBill.getInvoicecode());
						map2.put("isinvoicebill","1");
						map2.put("invoiceid",salesInvoiceBill.getSalesinvoiceid());
						salesInvoiceMapper.updateSalesInvoiceBack(map2);
					}
				}
			}
		}
		return flag;
	}

	@Override
	public boolean oppauditSalesInvoiceBill(String id) throws Exception {
		String billtype = "";
		SalesInvoiceBill salesInvoiceBill = salesInvoiceBillMapper.getSalesInvoiceBillInfo(id);
		boolean flag = false;
		if("3".equals(salesInvoiceBill.getStatus())){
			SysUser sysUser = getSysUser();
			int i = salesInvoiceBillMapper.oppauditSalesInvoiceBill(id, sysUser.getUserid(), sysUser.getName());
			flag = i>0;
			//若存在关联的销售核销编号，清除销售开票发票号、发票代码同时清除销售核销中的发票号、发票代码,开票状态为“未开票”
			if(flag){
				//回写来源单据开票日期
//				writeBackSalesInvoiceBillSourceInvoiceDate(id,"0");
				//判断销售开票关联的销售核销编号是否存在
				if(StringUtils.isNotEmpty(salesInvoiceBill.getSalesinvoiceid())){
					//销售开票明细是否与销售核销的明细一致，一致则关闭关闭审核通过状态的销售开票，核销状态为“已核销”，否则不操作
					boolean flag1 = checkSalesInvoiceBillDetailsSameAsSalesInvoiceDetails(id,salesInvoiceBill.getSalesinvoiceid());
					if(flag1){
						Map map = new HashMap();
						map.put("invoiceno","");
						map.put("invoicecode","");
						map.put("invoicebillid",id);
						salesInvoiceBillMapper.updateSalesInvoiceBillBack(map);

						map.put("isinvoicebill","0");
						map.put("invoiceid",salesInvoiceBill.getSalesinvoiceid());
						salesInvoiceMapper.updateSalesInvoiceBack(map);
					}
				}
			}
		}
		return flag;
	}

	@Override
	public Boolean deleteSalesInvoiceBill(String id) throws Exception {
		SalesInvoiceBill salesInvoiceBill = salesInvoiceBillMapper.getSalesInvoiceBillInfo(id);
		if(null==salesInvoiceBill){
			return false;
		}
		boolean flag = false;
		if("1".equals(salesInvoiceBill.getStatus()) || "2".equals(salesInvoiceBill.getStatus()) || "5".equals(salesInvoiceBill.getStatus())){
			List<String> sourceidList = salesInvoiceBillMapper.getSalesInvoiceBillSouceidList(salesInvoiceBill.getId());
			//先更新回单以及上游单据开票状态
			salesOutService.updateReceiptAndRejectBillInvoiceBill(sourceidList,salesInvoiceBill,"0");
			//回写来源单据开票时间
			writeBackSalesInvoiceBillSourceInvoiceDate(id,"0");
			//删除销售发票明细
			int i = salesInvoiceBillMapper.deleteSalesInvoiceBill(id);
			salesInvoiceBillMapper.deleteSalesInvoiceBillDetail(id);
			flag = i>0;
			if(flag){
				if(StringUtils.isNotEmpty(salesInvoiceBill.getSourceid())){
					 String[] sourceidArr = salesInvoiceBill.getSourceid().split(",");
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
				//清除销售核销单据中关联的销售开票编号
				if(StringUtils.isNotEmpty(salesInvoiceBill.getSalesinvoiceid())){
					SalesInvoice salesInvoice = salesInvoiceMapper.getSalesInvoiceInfo(salesInvoiceBill.getSalesinvoiceid());
					if(null != salesInvoice){
						Map map = new HashMap();
						map.put("salesinvoicebillid","");
						map.put("invoiceid",salesInvoiceBill.getSalesinvoiceid());
						salesInvoiceMapper.updateSalesInvoiceBack(map);
					}
				}
			}
		}
		return flag;
	}

	@Override
	public List showSalesInvoiceBillSourceListReferPage(
			String salesinvoicebillid, String sourcetype) throws Exception {
		String sourceid = null;
		SalesInvoiceBill salesInvoiceBill = salesInvoiceBillMapper.getSalesInvoiceBillInfo(salesinvoicebillid);
		if(null!=salesInvoiceBill){
			sourceid = salesInvoiceBill.getSourceid();
		}
		if(null!=sourceid){
			String[] ids = sourceid.split(",");
			List list = new ArrayList();
            if("1".equals(salesInvoiceBill.getBilltype())){//正常开票
                for(String id : ids){
                    //开票金额
                    BigDecimal invoiceAmount = salesInvoiceBillMapper.getSalesInvoiceBillDetailAmount(salesinvoicebillid, id);
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
                            }
                        }

                    }else{
                        receipt.setField04(invoiceAmount.toString());
                        list.add(receipt);
                    }
                }
            }else if("2".equals(salesInvoiceBill.getBilltype())){//预开票
                for(String id : ids){
                    //开票金额
                    BigDecimal invoiceAmount = salesInvoiceBillMapper.getSalesInvoiceBillDetailAmount(salesinvoicebillid, id);
                    if(null == invoiceAmount){
                        invoiceAmount = BigDecimal.ZERO;
                    }
                    Map map = storageSaleOutService.getSaleOutInfo(id);
                    Saleout saleout = (Saleout)map.get("saleOut");
                    if(null!=saleout){
                        saleout.setField04(invoiceAmount.toString());
                        list.add(saleout);
                    }
                }
            }
			return list;
		}
		return null;
	}

	@Override
	public Map deleteSalesInvoiceBillSource(Map map) throws Exception {
		String invoiceid = (String)map.get("invoiceid");
		String sourceid = (String)map.get("sourceid");
		String delgoodsids = "";
		List<SalesInvoiceBillDetail> detailList = salesInvoiceBillMapper.getSalesInvoiceBillDetailListByBillidAndSourceid(invoiceid, sourceid);
		for(SalesInvoiceBillDetail salesInvoiceBillDetail : detailList){
			if(StringUtils.isEmpty(delgoodsids)){
				delgoodsids = salesInvoiceBillDetail.getGoodsid();
			}else{
				delgoodsids += "," + salesInvoiceBillDetail.getGoodsid();
			}
		}
		Map map2 = new HashMap();
		SalesInvoiceBill salesInvoiceBill = salesInvoiceBillMapper.getSalesInvoiceBillInfo(invoiceid);
		if("2".equals(salesInvoiceBill.getStatus())){
			if(StringUtils.isNotEmpty(delgoodsids)){
				 map2 = editSalesInvoiceBill(salesInvoiceBill,delgoodsids,sourceid);
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
				map2.put("msg", "不存在明细,无法从开票中删除!");
			}
		}else{
			map2.put("flag", false);
			map2.put("msg", "只有保存状态下才能执行此操作!");
		}
		map2.put("delgoodsids", delgoodsids);
		return map2;
	}
	
	/**
	 * 删除开票明细 回写
	 * @param salesInvoiceBill
	 * @param delgoodsids
	 * @param sourceid2
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 16, 2015
	 */
	private Map editSalesInvoiceBill(SalesInvoiceBill salesInvoiceBill,String delgoodsids,String sourceid2)throws Exception{
		boolean flag = false;
		String msg = "";
		SalesInvoiceBill oldSalesInvoiceBill = salesInvoiceBillMapper.getSalesInvoiceBillInfo(salesInvoiceBill.getId());
		if(null != oldSalesInvoiceBill && "2".equals(oldSalesInvoiceBill.getStatus()) || "1".equals(oldSalesInvoiceBill.getStatus()) || "5".equals(oldSalesInvoiceBill.getStatus())){
			String[] goodsidArr = delgoodsids.split(",");
			//明细删除前 更新上游单据开票状态
			if(!"".equals(delgoodsids) && null!=goodsidArr && goodsidArr.length>0){
				List sourceidList = salesInvoiceBillMapper.getSalesInvoiceBillSouceidList(salesInvoiceBill.getId());
				salesOutService.updateReceiptAndRejectBillInvoiceBill(sourceidList,salesInvoiceBill,"0");
			}
			for(String goodsid : goodsidArr){
				salesInvoiceBillMapper.deleteSalesInvoiceBillDetailByGoodsidBoth(salesInvoiceBill.getId(), goodsid,sourceid2);
			}
			List<SalesInvoiceBillDetail> detailList = salesInvoiceBillMapper.getSalesInvoiceBillDetailList(salesInvoiceBill.getId());
			BigDecimal taxamount = BigDecimal.ZERO;
			BigDecimal notaxamount = BigDecimal.ZERO;
			
			for(SalesInvoiceBillDetail salesInvoiceBillDetail : detailList){
				salesInvoiceBillDetail.setBillid(salesInvoiceBill.getId());
				//商品品牌 和品牌业务员信息
				GoodsInfo goodsInfo = getAllGoodsInfoByID(salesInvoiceBillDetail.getGoodsid());
				if(null!=goodsInfo){
					salesInvoiceBillDetail.setGoodssort(goodsInfo.getDefaultsort());
					salesInvoiceBillDetail.setBrandid(goodsInfo.getBrand());
					salesInvoiceBillDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceBillDetail.getCustomerid()));
					//厂家业务员
					salesInvoiceBillDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceBillDetail.getCustomerid()));
					//默认供应商
					salesInvoiceBillDetail.setSupplierid(goodsInfo.getDefaultsupplier());
				}
				taxamount = taxamount.add(salesInvoiceBillDetail.getTaxamount());
				notaxamount = notaxamount.add(salesInvoiceBillDetail.getNotaxamount());
			}
			List<String> sourceidList = salesInvoiceBillMapper.getSalesInvoiceBillSouceidList(salesInvoiceBill.getId());
			String billsourceid = "";
			for(String sourceid : sourceidList){
				if("".equals(billsourceid)){
					billsourceid = sourceid;
				}else{
					billsourceid += ","+sourceid;
				}
			}
			salesInvoiceBill.setSourceid(billsourceid);
			salesInvoiceBill.setTaxamount(taxamount);
			salesInvoiceBill.setNotaxamount(notaxamount);
			SysUser sysUser = getSysUser();
			salesInvoiceBill.setModifyuserid(sysUser.getUserid());
			salesInvoiceBill.setModifyusername(sysUser.getName());
			if("5".equals(salesInvoiceBill.getStatus())){
				salesInvoiceBill.setStatus("2");
			}
			int i = salesInvoiceBillMapper.editSalesInvoiceBill(salesInvoiceBill);
			flag = i>0;
			if(flag && ((!"".equals(delgoodsids) && null!=goodsidArr && goodsidArr.length>0) || null!=salesInvoiceBill.getInvoiceno() && !"".equals(salesInvoiceBill.getInvoiceno()))){
				salesOutService.updateReceiptAndRejectBillInvoiceBill(sourceidList,salesInvoiceBill,"1");
			}
		}else{
			msg = "销售开票已审核或者核销。不能进行保存。";	
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("id", salesInvoiceBill.getId());
		return map;
	}

	@Override
	public PageData showSalesInvoiceBillDetailData(PageMap pageMap)
			throws Exception {
		String id = (String) pageMap.getCondition().get("id");
		List<Map> list = salesInvoiceBillMapper.showSalesInvoiceBillDetailData(pageMap);
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
			}else if("4".equals(sourcetype)){
                map.put("sourcetypename", "发货单");
            }
		}
		PageData pageData = new PageData(salesInvoiceBillMapper.showSalesInvoiceBillDetailDataCount(pageMap), list, pageMap);
		Map map = salesInvoiceBillMapper.getSalesInvoiceBillDetailSumData(id);
		if(null!=map){
			List footer = new ArrayList();
			map.put("businessdate", "合计");
			footer.add(map);
			pageData.setFooter(footer);
		}
		return pageData;
	}
	
	@Override
	public List getSalesInvoiceBillListByInvoiceids(String invoiceid)
			throws Exception {
		List list = new ArrayList();
		if(null!=invoiceid && !"".equals(invoiceid)){
			String[] invoiceidArr = invoiceid.split(",");
			for(String id : invoiceidArr){
				SalesInvoiceBill salesInvoiceBill = salesInvoiceBillMapper.getSalesInvoiceBillInfo(id);
				if(null==salesInvoiceBill){
					CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(id);
					if(null != customerPushBalance){
						salesInvoiceBill = new SalesInvoiceBill();
						salesInvoiceBill.setOrdertype("2");
						salesInvoiceBill.setId(customerPushBalance.getId());
						salesInvoiceBill.setCustomerid(customerPushBalance.getCustomerid());
						salesInvoiceBill.setBusinessdate(customerPushBalance.getBusinessdate());
						salesInvoiceBill.setTaxamount(customerPushBalance.getAmount());
						if("1".equals(customerPushBalance.getIsrelate())){
							BigDecimal invoiceamount = salesStatementMapper.getRelateCollectionOrderInvoiceAmount(customerPushBalance.getId());
							salesInvoiceBill.setTaxamount(invoiceamount);
						}else{
							salesInvoiceBill.setTaxamount(customerPushBalance.getAmount());
						}
						salesInvoiceBill.setIsrelate(customerPushBalance.getIsrelate());
					}
				}else{
					if("1".equals(salesInvoiceBill.getIsrelate())){
						BigDecimal invoiceamount = salesStatementMapper.getRelateCollectionOrderInvoiceAmount(salesInvoiceBill.getId());
						salesInvoiceBill.setTaxamount(invoiceamount);
					}
					salesInvoiceBill.setOrdertype("1");
				}
				list.add(salesInvoiceBill);
			}
		}
		return list;
	}

	@Override
	public boolean updateSalesInvoiceBillCancel(String id) throws Exception {
		SalesInvoiceBill salesInvoiceBill = salesInvoiceBillMapper.getSalesInvoiceBillInfo(id);
		boolean flag = false;
		if(null!=salesInvoiceBill && "2".equals(salesInvoiceBill.getStatus())){
			SysUser sysUser = getSysUser();
			int i = salesInvoiceBillMapper.updateSalesInvoiceBillCancel(id, sysUser.getUserid(), sysUser.getName());
			flag = i>0;
		}
		return flag;
	}

	@Override
	public SalesInvoiceBill getPureSalesInvoiceBillPureInfo(String id)
			throws Exception {
		return salesInvoiceBillMapper.getSalesInvoiceBillInfo(id);
	}

	@Override
	public List showSalesInvoiceBillListPageByCustomer(String customerid,String billtype)
			throws Exception {
		List<SalesInvoiceBill> list = salesInvoiceBillMapper.showSalesInvoiceBillListPageByCustomer(customerid,billtype);
		for(SalesInvoiceBill salesInvoiceBill : list){
			CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(salesInvoiceBill.getCustomerid());
			if(null!=customerCapital){
				salesInvoiceBill.setCustomeramount(customerCapital.getAmount());
			}else{
				salesInvoiceBill.setCustomeramount(BigDecimal.ZERO);
			}
			Customer customer = getCustomerByID(salesInvoiceBill.getCustomerid());
			if(null!=customer){
				salesInvoiceBill.setCustomername(customer.getName());
			}
			if(null!=salesInvoiceBill.getChlidcustomerid()){
				String[] childcustomer = salesInvoiceBill.getChlidcustomerid().split(",");
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
				salesInvoiceBill.setChlidcustomername(childcustomername);
			}
			DepartMent departMent = getDepartmentByDeptid(salesInvoiceBill.getSalesdept());
			if(null!=departMent){
				salesInvoiceBill.setSalesdeptname(departMent.getName());
			}
			Personnel personnel = getPersonnelById(salesInvoiceBill.getSalesuser());
			if(null!=personnel){
				salesInvoiceBill.setSalesusername(personnel.getName());
			}
			String taxtype=salesInvoiceBillMapper.getSalesInvoiceBillDetailOneTaxtype(salesInvoiceBill.getId());
			salesInvoiceBill.setTaxtype(taxtype);
			if(StringUtils.isNotEmpty(salesInvoiceBill.getTaxtype())){
				TaxType taxTypeInfo=getTaxType(salesInvoiceBill.getTaxtype());
				if(null!=taxTypeInfo){
					salesInvoiceBill.setTaxtypename(taxTypeInfo.getName());
				}
			}
		}
		return list;
	}

	@Override
	public Map addToSalesInvoiceBillByReceiptAndRejectbill(String ids,
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
			SalesInvoiceBill salesInvoiceBill = salesInvoiceBillMapper.getSalesInvoiceBillInfo(billid);
			if(null!=salesInvoiceBill && "2".equals(salesInvoiceBill.getStatus()) && salesInvoiceBill.getCustomerid().equals(customerid)){
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
								msg = "不是同一客户(总店)下的销售发货回单，不能生成销售开票";
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
								msg = "不是同一客户(总店)下的销售发货回单，不能生成销售开票";
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
								msg = "不是同一客户(总店)下的销售发货回单，不能生成销售开票";
								break;
							}
							billMap.put(id, customerPushBalance);
						}
					}
				}
			}else{
				addFlag = false;
			}
			//判断是否可以生成销售发票
			if(addFlag){
				List<SalesInvoiceBillDetail> salesInvoiceBillDetailList = new ArrayList<SalesInvoiceBillDetail>();
				
				for(int i=0;i<idArr.size();i++){
					JSONObject detailObject = (JSONObject) idArr.get(i);
					//单据类型 1回单2销售退货通知单
					String billtype = detailObject.getString("billtype");
					//单据编号
					String id = detailObject.getString("billid");
					//明细编号
					String detailid = detailObject.getString("detailid");
					SalesInvoiceBillDetail salesInvoiceBillDetail = null;
					
					if("1".equals(billtype)){
						Receipt receipt = salesOutService.getReceipt(id);
						if(null!=receipt){
							ReceiptDetail receiptDetail = salesOutService.getReceiptDetailInfo(detailid,id);
							if(null!=receiptDetail){
								if("1".equals(receiptDetail.getIsdiscount()) && "1".equals(receiptDetail.getIsbranddiscount())){
									List<ReceiptDetail> list = salesOutService.getReceiptDetailBrandDiscountList(receiptDetail.getBillid(), receiptDetail.getBrandid());
									for(ReceiptDetail receiptDetail2 : list){
										salesInvoiceBillDetail = new SalesInvoiceBillDetail();
										salesInvoiceBillDetail.setBillid(salesInvoiceBill.getId());
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
											salesInvoiceBillDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceBill.getCustomerid()));
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
										salesInvoiceBillDetail.setTaxamount(receiptDetail2.getReceipttaxamount());
										salesInvoiceBillDetail.setTaxtype(receiptDetail2.getTaxtype());
										//重新计算税额税额
										BigDecimal notaxamount = getNotaxAmountByTaxAmount(salesInvoiceBillDetail.getTaxamount(), salesInvoiceBillDetail.getTaxtype());
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
										salesInvoiceBillDetailList.add(salesInvoiceBillDetail);
									}
								}else{
									salesInvoiceBillDetail = new SalesInvoiceBillDetail();
									salesInvoiceBillDetail.setBillid(salesInvoiceBill.getId());
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
										salesInvoiceBillDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceBill.getCustomerid()));
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
									salesInvoiceBillDetail.setTaxamount(receiptDetail.getReceipttaxamount());
									salesInvoiceBillDetail.setTaxtype(receiptDetail.getTaxtype());
									//重新计算税额税额
									BigDecimal notaxamount = getNotaxAmountByTaxAmount(salesInvoiceBillDetail.getTaxamount(), salesInvoiceBillDetail.getTaxtype());
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
									salesInvoiceBillDetailList.add(salesInvoiceBillDetail);
								}
							}
						}
					}else if("2".equals(billtype)){
						RejectBill rejectBill = salesOutService.getRejectBill(id);
						if(null!=rejectBill){
							RejectBillDetail rejectBillDetail = salesOutService.getRejectBillDetailInfo(detailid, id);
							if(null!=rejectBillDetail){
								salesInvoiceBillDetail = new SalesInvoiceBillDetail();
								salesInvoiceBillDetail.setBillid(salesInvoiceBill.getId());
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
									salesInvoiceBillDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceBill.getCustomerid()));
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
								salesInvoiceBillDetail.setTaxamount(rejectBillDetail.getTaxamount().negate());
								salesInvoiceBillDetail.setTaxtype(rejectBillDetail.getTaxtype());
								//重新计算税额税额
								BigDecimal notaxamount = getNotaxAmountByTaxAmount(salesInvoiceBillDetail.getTaxamount(), salesInvoiceBillDetail.getTaxtype());
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
								salesInvoiceBillDetailList.add(salesInvoiceBillDetail);
							}
						}
					}else if("3".equals(billtype)){
						CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(id);
						if(null!=customerPushBalance){
							salesInvoiceBillDetail = new SalesInvoiceBillDetail();
							salesInvoiceBillDetail.setBillid(salesInvoiceBill.getId());
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
							salesInvoiceBillDetail.setTaxamount(customerPushBalance.getAmount());
							salesInvoiceBillDetail.setNotaxprice(BigDecimal.ZERO);
							salesInvoiceBillDetail.setTaxtype(customerPushBalance.getDefaulttaxtype());
							salesInvoiceBillDetail.setNotaxamount(customerPushBalance.getNotaxamount());
							salesInvoiceBillDetail.setTax(customerPushBalance.getTax());
							salesInvoiceBillDetail.setRemark(customerPushBalance.getRemark());
							salesInvoiceBillDetail.setSeq(999);
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
				
				SysUser sysUser = getSysUser();
				salesInvoiceBill.setAdddeptid(sysUser.getDepartmentid());
				salesInvoiceBill.setAdddeptname(sysUser.getDepartmentname());
				salesInvoiceBill.setAdduserid(sysUser.getUserid());
				salesInvoiceBill.setAddusername(sysUser.getName());
				//含税总金额
				BigDecimal alltaxamount = new BigDecimal(0);
				//未税总金额
				BigDecimal allnotaxamount = new BigDecimal(0);
				List<SalesInvoiceBillDetail> newSalesInvoiceBillList = salesInvoiceBillMapper.getSalesInvoiceBillDetailList(salesInvoiceBill.getId());
				for(SalesInvoiceBillDetail salesInvoiceBillDetail : newSalesInvoiceBillList){
					alltaxamount = alltaxamount.add(salesInvoiceBillDetail.getTaxamount());
					allnotaxamount = allnotaxamount.add(salesInvoiceBillDetail.getNotaxamount());
				}
				salesInvoiceBill.setTaxamount(alltaxamount);
				salesInvoiceBill.setNotaxamount(allnotaxamount);
				salesInvoiceBill.setSourceid(salesInvoiceBill.getSourceid()+","+sourceids);
				int i = salesInvoiceBillMapper.editSalesInvoiceBill(salesInvoiceBill);
				flag = i>0;
				if(flag){
//					List sourceidList = salesInvoiceMapper.getSalesInvoiceSouceidList(salesInvoice.getId());
					List sourceidList = new ArrayList();
					if(null!=sourceids){
						String[] sourceidArr = sourceids.split(",");
						for(String sourceid : sourceidArr){
							sourceidList.add(sourceid);
						}
						salesOutService.updateReceiptAndRejectBillInvoiceBill(sourceidList, salesInvoiceBill, "1");
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
	public boolean editSalesInvoiceBillInvoiceno(
			SalesInvoiceBill salesInvoiceBill) throws Exception {
		return salesInvoiceBillMapper.editSalesInvoiceBill(salesInvoiceBill) > 0;
	}

    @Override
    public Map salesInvoiceBillMutiDelete(String ids) throws Exception {
        String[] idArr = ids.split(",");
        String sucids = "",unsucids = "";
        for(String id : idArr){
            boolean flag = deleteSalesInvoiceBill(id);
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
    public boolean checkSalesInvoiceBillCanMuApplyWriteOff(String id)throws Exception{
        boolean flag = salesInvoiceBillMapper.checkSalesInvoiceBillCanMuApplyWriteOff(id) > 0;
        return flag;
    }

    @Override
    public boolean checkSalesInvoiceBillCanMuApplyWriteOffAll(String id) throws Exception {
        return salesInvoiceBillMapper.checkSalesInvoiceBillCanMuApplyWriteOffAll(id) > 0;
    }

    @Override
    public Map doSalesInvoiceBillMuApplyWriteOff(String id)throws Exception{
        Map retmap = new HashMap();
        SalesInvoiceBill salesInvoiceBill = salesInvoiceBillMapper.getSalesInvoiceBillInfo(id);
        if(null != salesInvoiceBill && "3".equals(salesInvoiceBill.getStatus())){
            String customerid = salesInvoiceBill.getCustomerid();
            String ids = "";
            List<Map> list2 = new ArrayList<Map>();
            List<SalesInvoiceBillDetail> list = new ArrayList<SalesInvoiceBillDetail>();
            if("1".equals(salesInvoiceBill.getBilltype())){//申请开票
                list = salesInvoiceBillMapper.getUnWriteoffSalesInvoiceBillList(id);
            }else if("2".equals(salesInvoiceBill.getBilltype())){//预开票
                list = salesInvoiceBillMapper.getUnWriteoffSalesInvoiceBillListCaseAdvance(id);
            }
            if(list.size() != 0){
                for(SalesInvoiceBillDetail salesInvoiceBillDetail : list){
                    Map map = new HashMap();
                    map.put("billtype",salesInvoiceBillDetail.getSourcetype());
                    map.put("billid",salesInvoiceBillDetail.getSourceid());
                    map.put("detailid",salesInvoiceBillDetail.getSourcedetailid());
                    map.put("isdiscount",salesInvoiceBillDetail.getIsdiscount());
                    list2.add(map);
                }
            }
            if(list2.size() != 0){
                JSONArray json = JSONArray.fromObject(list2);
                ids = json.toString();
            }
			//开票列表申请核销时生过去的数据是审核状态，且销售核销中的开票状态是已开票
            Map map2 = addSalerInvoiceBillByApplyWriteOff(salesInvoiceBill,ids, customerid, "1");
			//写入销售开票、销售核销关系
			if(null != map2 && !map2.isEmpty() && map2.get("flag").equals(true)){
				String salesinvoiceid = null != map2.get("id") ? (String)map2.get("id") : "";
				map2.put("salesinvoiceid",salesinvoiceid);
				map2.put("invoicebillid",id);
				salesInvoiceBillMapper.updateSalesInvoiceBillBack(map2);

				map2.put("salesinvoicebillid",id);
				map2.put("invoiceid",salesinvoiceid);
				map2.put("isinvoicebill","1");
				salesInvoiceMapper.updateSalesInvoiceBack(map2);
			}
            return map2;
        }
        return null;
    }

    /**
     * 根据申请核销生成销售核销发票
     * @param ids,customerid,iswriteoff
     * @return
     * @author panxiaoxiao
     * @date 2015-03-03
     * @throws Exception
     */
    private Map addSalerInvoiceBillByApplyWriteOff(SalesInvoiceBill salesInvoiceBill,String ids,String customerid,String iswriteoff)throws Exception{
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
                }
            }
            //判断是否可以生成销售发票
            if(addFlag){
                SalesInvoice salesInvoice = new SalesInvoice();

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                salesInvoice.setBusinessdate(dateFormat.format(calendar.getTime()));
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
                        salesInvoice.setApplytype(customer.getCanceltype());
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
				if (isAutoCreate("t_account_sales_invoice")) {
                    // 获取自动编号
                    String id = getAutoCreateSysNumbderForeign(salesInvoice, "t_account_sales_invoice");
                    salesInvoice.setId(id);
                }else{
                    salesInvoice.setId("XSFP-"+CommonUtils.getDataNumberSendsWithRand());
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
                                        salesInvoiceDetail.setTaxamount(receiptDetail2.getReceipttaxamount());
                                        salesInvoiceDetail.setTaxtype(receiptDetail2.getTaxtype());
                                        //重新计算税额税额
                                        BigDecimal notaxamount = getNotaxAmountByTaxAmount(salesInvoiceDetail.getTaxamount(), salesInvoiceDetail.getTaxtype());
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
                                    salesInvoiceDetail.setTaxamount(receiptDetail.getReceipttaxamount());
                                    salesInvoiceDetail.setTaxtype(receiptDetail.getTaxtype());
                                    //重新计算税额税额
                                    BigDecimal notaxamount = getNotaxAmountByTaxAmount(salesInvoiceDetail.getTaxamount(), salesInvoiceDetail.getTaxtype());
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
                                salesInvoiceDetail.setTaxamount(rejectBillDetail.getTaxamount().negate());
                                salesInvoiceDetail.setTaxtype(rejectBillDetail.getTaxtype());
                                //重新计算税额税额
                                BigDecimal notaxamount = getNotaxAmountByTaxAmount(salesInvoiceDetail.getTaxamount(), salesInvoiceDetail.getTaxtype());
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
                            salesInvoiceDetail.setTaxamount(customerPushBalance.getAmount());
                            salesInvoiceDetail.setNotaxprice(BigDecimal.ZERO);
                            salesInvoiceDetail.setTaxtype(customerPushBalance.getDefaulttaxtype());
                            //重新计算税额税额
                            BigDecimal notaxamount = getNotaxAmountByTaxAmount(salesInvoiceDetail.getTaxamount(), salesInvoiceDetail.getTaxtype());
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
                //获取销售开票的发票号、发票代码、发票类型
                if(null != salesInvoiceBill){
                    salesInvoice.setInvoicecode(salesInvoiceBill.getInvoicecode());
                    salesInvoice.setInvoiceno(salesInvoiceBill.getInvoiceno());
                    salesInvoice.setInvoicetype(salesInvoiceBill.getInvoicetype());
                }
                int i = salesInvoiceMapper.addSalesInvoice(salesInvoice);
                flag = i>0;
                if(flag){
                    salesInvoiceid = salesInvoice.getId();
                    salesOutService.updateReceiptAndRejectBillInvoice(sourceidList,salesInvoice,"1");
                }else{
					throw new Exception("开票申请核销出错");
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
    public Map addSalesAdvanceBillByRefer(String ids, String customerid)throws Exception {
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
                //单据类型 1回单2销售退货通知单3冲差单4发货单
                String billtype = "4";
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

                if(!billMap.containsKey(id)){
                    Saleout saleout = storageSaleOutService.getBaseSaleOutInfo(id);
                    childcustomerMap.put(saleout.getCustomerid(), saleout.getCustomerid());
                    //获取总店客户档案信息 没有总店信息则获取自身
                    Customer headCutomer = salesExtService.getHeadCustomer(saleout.getCustomerid());
                    if(null==customerid){
                        customerid = headCutomer.getId();
                    }
                    //判断是否同一个客户
                    if(customerid.equals(saleout.getCustomerid()) || customerid.equals(headCutomer.getId())){
                    }else{
                        addFlag = false;
                        msg = "不是同一客户(总店)下的销售发货回单，不能生成销售发票";
                        break;
                    }
                    billMap.put(id, saleout);
                }
            }
            //判断是否可以生成销售发票
            if(addFlag){
				List<TaxType> taxList=financeMapper.getTaxTypeListData();

				Map invoiceBillMap=new HashMap();
				for(TaxType taxType:taxList){
					Map taxMap=new HashMap();
					invoiceBillMap.put(taxType.getId(),taxMap);
				}

                List<SalesInvoiceBillDetail> salesInvoiceBillDetailList = new ArrayList<SalesInvoiceBillDetail>();

                Map billObjectMap = new HashMap();
                for(int i=0;i<idArr.size();i++){
                    JSONObject detailObject = (JSONObject) idArr.get(i);
                    //单据编号
                    String id = detailObject.getString("billid");
                    //明细编号
                    String detailid = detailObject.getString("detailid");
                    SalesInvoiceBillDetail salesInvoiceBillDetail = null;

                    Saleout saleout = null;
                    if(billObjectMap.containsKey(id)){
                        saleout = (Saleout) billObjectMap.get(id);
                    }else{
                        saleout = storageSaleOutService.getBaseSaleOutInfo(id);
                        billObjectMap.put(id, saleout);
                    }
                    if(null!=saleout){
                        SaleoutDetail saleoutDetail = storageSaleOutService.getSaleoutDetailInfo(detailid,id);
                        if(null!=saleoutDetail){
                            if("1".equals(saleoutDetail.getIsdiscount()) && "1".equals(saleoutDetail.getIsbranddiscount())){
                                List<SaleoutDetail> list = storageSaleOutService.getSaleoutDetailBrandDiscountList(saleoutDetail.getSaleoutid(),saleoutDetail.getBrandid());
                                for(SaleoutDetail saleoutDetail2 : list){
                                    salesInvoiceBillDetail = new SalesInvoiceBillDetail();
                                    salesInvoiceBillDetail.setIsdiscount(saleoutDetail2.getIsdiscount());
                                    salesInvoiceBillDetail.setIsbranddiscount(saleoutDetail2.getIsbranddiscount());
                                    //来源销售发货单
                                    salesInvoiceBillDetail.setSourcetype("4");
                                    salesInvoiceBillDetail.setSourceid(id);
                                    salesInvoiceBillDetail.setSourcedetailid(saleoutDetail2.getId().toString());
                                    salesInvoiceBillDetail.setBillno(id);
                                    salesInvoiceBillDetail.setBilldetailid(saleoutDetail2.getId().toString());
                                    salesInvoiceBillDetail.setCustomerid(saleout.getCustomerid()!=null?saleout.getCustomerid():"");
                                    salesInvoiceBillDetail.setPcustomerid(saleout.getPcustomerid()!=null?saleout.getPcustomerid():"");
                                    salesInvoiceBillDetail.setCustomersort(saleout.getCustomersort()!=null?saleout.getCustomersort():"");
                                    salesInvoiceBillDetail.setSalesarea(saleout.getSalesarea()!=null?saleout.getSalesarea():"");
                                    salesInvoiceBillDetail.setSalesdept(saleout.getSalesdept()!=null?saleout.getSalesdept():"");
                                    salesInvoiceBillDetail.setSalesuser(saleout.getSalesuser()!=null?saleout.getSalesuser():"");
                                    salesInvoiceBillDetail.setGoodsid(saleoutDetail2.getGoodsid()!=null?saleoutDetail2.getGoodsid():"");
                                    salesInvoiceBillDetail.setBranddept(saleoutDetail2.getBranddept()!=null?saleoutDetail2.getBranddept():"");
                                    salesInvoiceBillDetail.setCostprice(saleoutDetail2.getCostprice());
                                    //商品品牌 和品牌业务员信息
                                    GoodsInfo goodsInfo = getAllGoodsInfoByID(saleoutDetail2.getGoodsid());
                                    if(null!=goodsInfo){
                                        salesInvoiceBillDetail.setGoodssort(goodsInfo.getDefaultsort());
                                        salesInvoiceBillDetail.setBrandid(goodsInfo.getBrand());
                                        salesInvoiceBillDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceBillDetail.getCustomerid()));
                                        //厂家业务员
                                        salesInvoiceBillDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), customerid));
                                        //商品默认供应商
                                        salesInvoiceBillDetail.setSupplierid(goodsInfo.getDefaultsupplier());
                                    }
                                    salesInvoiceBillDetail.setUnitid(saleoutDetail2.getUnitid());
                                    salesInvoiceBillDetail.setUnitname(saleoutDetail2.getUnitname());
                                    salesInvoiceBillDetail.setUnitnum(saleoutDetail2.getUnitnum());
                                    salesInvoiceBillDetail.setAuxunitid(saleoutDetail2.getAuxunitid());
                                    salesInvoiceBillDetail.setAuxunitname(saleoutDetail2.getAuxunitname());
                                    Map auxMap = countGoodsInfoNumber(saleoutDetail2.getGoodsid(), saleoutDetail2.getAuxunitid(), saleoutDetail2.getUnitnum());
                                    salesInvoiceBillDetail.setAuxnum((BigDecimal) auxMap.get("auxnum"));
                                    salesInvoiceBillDetail.setAuxnumdetail( (String) auxMap.get("auxnumdetail"));

                                    //退货通知单生成的发票 金额为负
                                    salesInvoiceBillDetail.setTaxprice(saleoutDetail2.getTaxprice());
                                    salesInvoiceBillDetail.setTaxamount(saleoutDetail2.getTaxamount());
                                    salesInvoiceBillDetail.setTaxtype(saleoutDetail2.getTaxtype());
                                    //重新计算税额税额
                                    BigDecimal notaxamount = getNotaxAmountByTaxAmount(salesInvoiceBillDetail.getTaxamount(), salesInvoiceBillDetail.getTaxtype());
                                    salesInvoiceBillDetail.setNotaxamount(notaxamount);
                                    //未税单价 = 未税金额/数量
                                    if(null!=notaxamount && notaxamount.compareTo(BigDecimal.ZERO)!=0
                                            && null!=salesInvoiceBillDetail.getUnitnum() && salesInvoiceBillDetail.getUnitnum().compareTo(BigDecimal.ZERO)!=0){
                                        salesInvoiceBillDetail.setNotaxprice(notaxamount.divide(salesInvoiceBillDetail.getUnitnum(), 6,BigDecimal.ROUND_HALF_UP));
                                    }else{
                                        salesInvoiceBillDetail.setNotaxprice(saleoutDetail2.getNotaxprice());
                                    }
                                    salesInvoiceBillDetail.setTax(salesInvoiceBillDetail.getTaxamount().subtract(salesInvoiceBillDetail.getNotaxamount()));
                                    salesInvoiceBillDetail.setRemark(saleoutDetail2.getRemark());
                                    salesInvoiceBillDetail.setSeq(saleoutDetail2.getSeq());
									//针对不同税种生成多条单据，用来保存单据的金额信息
									if(invoiceBillMap.containsKey(saleoutDetail2.getTaxtype())){
										Map tmap= (Map) invoiceBillMap.get(saleoutDetail2.getTaxtype());
										tmap=getSalesInvoiceBillMap(tmap,saleoutDetail2.getTaxamount(),notaxamount,null,customerid,saleoutDetail2.getCustomerid());
										invoiceBillMap.put(saleoutDetail2.getTaxtype(),tmap);
										SalesInvoiceBill bill=(SalesInvoiceBill)tmap.get("bill");
										salesInvoiceBillDetail.setBillid(bill.getId());
									}
                                    salesInvoiceBillDetailList.add(salesInvoiceBillDetail);
                                }
                            }else{
                                salesInvoiceBillDetail = new SalesInvoiceBillDetail();
                                //来源销售发货单
                                salesInvoiceBillDetail.setSourcetype("4");
                                salesInvoiceBillDetail.setSourceid(id);
                                salesInvoiceBillDetail.setSourcedetailid(detailid);
                                salesInvoiceBillDetail.setBillno(id);
                                salesInvoiceBillDetail.setBilldetailid(detailid);
                                salesInvoiceBillDetail.setIsdiscount(saleoutDetail.getIsdiscount());
                                salesInvoiceBillDetail.setCustomerid(saleout.getCustomerid()!=null?saleout.getCustomerid():"");
                                salesInvoiceBillDetail.setPcustomerid(saleout.getPcustomerid()!=null?saleout.getPcustomerid():"");
                                salesInvoiceBillDetail.setCustomersort(saleout.getCustomersort()!=null?saleout.getCustomersort():"");
                                salesInvoiceBillDetail.setSalesarea(saleout.getSalesarea()!=null?saleout.getSalesarea():"");
                                salesInvoiceBillDetail.setSalesdept(saleout.getSalesdept()!=null?saleout.getSalesdept():"");
                                salesInvoiceBillDetail.setSalesuser(saleout.getSalesuser()!=null?saleout.getSalesuser():"");
                                salesInvoiceBillDetail.setGoodsid(saleoutDetail.getGoodsid()!=null?saleoutDetail.getGoodsid():"");
                                salesInvoiceBillDetail.setBranddept(saleoutDetail.getBranddept()!=null?saleoutDetail.getBranddept():"");
                                salesInvoiceBillDetail.setCostprice(saleoutDetail.getCostprice());
                                //商品品牌 和品牌业务员信息
                                GoodsInfo goodsInfo = getAllGoodsInfoByID(saleoutDetail.getGoodsid());
                                if(null!=goodsInfo){
                                    salesInvoiceBillDetail.setGoodssort(goodsInfo.getDefaultsort());
                                    salesInvoiceBillDetail.setBrandid(goodsInfo.getBrand());
                                    salesInvoiceBillDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceBillDetail.getCustomerid()));
                                    //厂家业务员
                                    salesInvoiceBillDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), customerid));
                                    //商品默认供应商
                                    salesInvoiceBillDetail.setSupplierid(goodsInfo.getDefaultsupplier());
                                }
                                salesInvoiceBillDetail.setUnitid(saleoutDetail.getUnitid());
                                salesInvoiceBillDetail.setUnitname(saleoutDetail.getUnitname());
                                salesInvoiceBillDetail.setUnitnum(saleoutDetail.getUnitnum());
                                salesInvoiceBillDetail.setAuxunitid(saleoutDetail.getAuxunitid());
                                salesInvoiceBillDetail.setAuxunitname(saleoutDetail.getAuxunitname());
                                Map auxMap = countGoodsInfoNumber(saleoutDetail.getGoodsid(), saleoutDetail.getAuxunitid(), saleoutDetail.getUnitnum());
                                salesInvoiceBillDetail.setAuxnum((BigDecimal) auxMap.get("auxnum"));
                                salesInvoiceBillDetail.setAuxnumdetail( (String) auxMap.get("auxnumdetail"));

                                //退货通知单生成的发票 金额为负
                                salesInvoiceBillDetail.setTaxprice(saleoutDetail.getTaxprice());
								BigDecimal taxamount=saleoutDetail.getTaxamount();
                                salesInvoiceBillDetail.setTaxamount(taxamount );
                                salesInvoiceBillDetail.setTaxtype(saleoutDetail.getTaxtype());
                                //重新计算税额税额
                                BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, salesInvoiceBillDetail.getTaxtype());
                                salesInvoiceBillDetail.setNotaxamount(notaxamount);
                                //未税单价 = 未税金额/数量
                                if(null!=notaxamount && notaxamount.compareTo(BigDecimal.ZERO)!=0
                                        && null!=salesInvoiceBillDetail.getUnitnum() && salesInvoiceBillDetail.getUnitnum().compareTo(BigDecimal.ZERO)!=0){
                                    salesInvoiceBillDetail.setNotaxprice(notaxamount.divide(salesInvoiceBillDetail.getUnitnum(), 6,BigDecimal.ROUND_HALF_UP));
                                }else{
                                    salesInvoiceBillDetail.setNotaxprice(saleoutDetail.getNotaxprice());
                                }
                                salesInvoiceBillDetail.setTax(salesInvoiceBillDetail.getTaxamount().subtract(salesInvoiceBillDetail.getNotaxamount()));
                                salesInvoiceBillDetail.setRemark(saleoutDetail.getRemark());
                                salesInvoiceBillDetail.setSeq(saleoutDetail.getSeq());
								//针对不同税种生成多条单据，用来保存单据的金额信息
								if(invoiceBillMap.containsKey(saleoutDetail.getTaxtype())){
									Map tmap= (Map) invoiceBillMap.get(saleoutDetail.getTaxtype());
									tmap=getSalesInvoiceBillMap(tmap,saleoutDetail.getTaxamount(),notaxamount,null,customerid,saleoutDetail.getCustomerid());
									invoiceBillMap.put(saleoutDetail.getTaxtype(),tmap);
									SalesInvoiceBill bill=(SalesInvoiceBill)tmap.get("bill");
									salesInvoiceBillDetail.setBillid(bill.getId());
								}
                                salesInvoiceBillDetailList.add(salesInvoiceBillDetail);
                            }
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
						salesInvoiceBill.setBusinessdate(getCurrentDate());
						salesInvoiceBill.setAdddeptid(sysUser.getDepartmentid());
						salesInvoiceBill.setAdddeptname(sysUser.getDepartmentname());
						salesInvoiceBill.setAdduserid(sysUser.getUserid());
						salesInvoiceBill.setAddusername(sysUser.getName());
						salesInvoiceBill.setChlidcustomerid(customerids);
						salesInvoiceBill.setTaxamount(taxamount );
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
							// 回写发货单、发货回单标记
							storageSaleOutService.updateSaleoutAdvanceBill(sourceidList, salesInvoiceBill, "1");
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

    @Override
    public boolean deleteSalesAdvanceBill(String id) throws Exception {
        SalesInvoiceBill salesInvoiceBill = salesInvoiceBillMapper.getSalesInvoiceBillInfo(id);
        if(null==salesInvoiceBill){
            return false;
        }
        boolean flag = false;
        if("1".equals(salesInvoiceBill.getStatus()) || "2".equals(salesInvoiceBill.getStatus()) || "5".equals(salesInvoiceBill.getStatus())){
            List<String> sourceidList = salesInvoiceBillMapper.getSalesInvoiceBillSouceidList(salesInvoiceBill.getId());
            //先更新发货单以及发货回单开票状态
            storageSaleOutService.updateSaleoutAdvanceBill(sourceidList, salesInvoiceBill, "0");
            //删除销售开票明细
            int i = salesInvoiceBillMapper.deleteSalesInvoiceBill(id);
            salesInvoiceBillMapper.deleteSalesInvoiceBillDetail(id);
            flag = i>0;
            if(flag){
                if(StringUtils.isNotEmpty(salesInvoiceBill.getSourceid())){
                    String[] sourceidArr = salesInvoiceBill.getSourceid().split(",");
                    for(String saleoutid : sourceidArr){
                        Saleout saleout = storageSaleOutService.getBaseSaleOutInfo(saleoutid);
                        if(null != saleout){
                            String billid = saleout.getReceiptid();
                            Receipt receipt = salesOutService.getReceiptInfo(billid);
                            if(null != receipt){
                                //清除发票号
                                storageSaleService.clearSaleoutInvoiceidByReceiptid(receipt.getId());
//                                storageSaleService.clearSaleRejectEnterInvoiceidByReceiptid(receipt.getId());
                            }
                        }
                    }
                }
            }
        }
        return flag;
    }

    @Override
    public Map addToSalesInvoiceBillBySaleout(String ids, String customerid, String billid)throws Exception{
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
            SalesInvoiceBill salesInvoiceBill = salesInvoiceBillMapper.getSalesInvoiceBillInfo(billid);
            if(null!=salesInvoiceBill && "2".equals(salesInvoiceBill.getStatus()) && salesInvoiceBill.getCustomerid().equals(customerid)){
                //验证多个回单是否能组成一张销售发票
                for(int i=0;i<idArr.size();i++){
                    JSONObject detailObject = (JSONObject) idArr.get(i);
                    //单据类型 1回单2销售退货通知单3冲差单5发货单
                    String billtype = "4";
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

                    if(!billMap.containsKey(id)){
                        Saleout saleout = storageSaleOutService.getBaseSaleOutInfo(id);
                        childcustomerMap.put(saleout.getCustomerid(), saleout.getCustomerid());
                        //获取总店客户档案信息 没有总店信息则获取自身
                        Customer headCutomer = salesExtService.getHeadCustomer(saleout.getCustomerid());
                        if(null==customerid){
                            customerid = headCutomer.getId();
                        }
                        //判断是否同一个客户
                        if(customerid.equals(saleout.getCustomerid()) || customerid.equals(headCutomer.getId())){
                        }else{
                            addFlag = false;
                            msg = "不是同一客户(总店)下的销售发货单，不能生成销售发票";
                            break;
                        }
                        billMap.put(id, saleout);
                    }
                }
            }else{
                addFlag = false;
            }
            //判断是否可以生成销售发票
            if(addFlag){
                String customerids = salesInvoiceBill.getChlidcustomerid();
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
                salesInvoiceBill.setChlidcustomerid(customerids);
                List<SalesInvoiceBillDetail> salesInvoiceBillDetailList = new ArrayList<SalesInvoiceBillDetail>();

                for(int i=0;i<idArr.size();i++){
                    JSONObject detailObject = (JSONObject) idArr.get(i);
                    //单据编号
                    String id = detailObject.getString("billid");
                    //明细编号
                    String detailid = detailObject.getString("detailid");
                    SalesInvoiceBillDetail salesInvoiceBillDetail = null;

                    Saleout saleout = storageSaleOutService.getBaseSaleOutInfo(id);
                    if(null!=saleout){
                        SaleoutDetail saleoutDetail = storageSaleOutService.getSaleoutDetailInfo(detailid,id);
                        if(null!=saleoutDetail){
                            if("1".equals(saleoutDetail.getIsdiscount()) && "1".equals(saleoutDetail.getIsbranddiscount())){
                                List<SaleoutDetail> list = storageSaleOutService.getSaleoutDetailBrandDiscountList(saleoutDetail.getSaleoutid(),saleoutDetail.getBrandid());
                                for(SaleoutDetail saleoutDetail2 : list){
                                    salesInvoiceBillDetail = new SalesInvoiceBillDetail();
                                    salesInvoiceBillDetail.setBillid(salesInvoiceBill.getId());
                                    salesInvoiceBillDetail.setIsdiscount(saleoutDetail2.getIsdiscount());
                                    salesInvoiceBillDetail.setIsbranddiscount(saleoutDetail2.getIsbranddiscount());
                                    //来源销售发货单
                                    salesInvoiceBillDetail.setSourcetype("4");
                                    salesInvoiceBillDetail.setSourceid(id);
                                    salesInvoiceBillDetail.setSourcedetailid(saleoutDetail2.getId().toString());
                                    salesInvoiceBillDetail.setBillno(id);
                                    salesInvoiceBillDetail.setBilldetailid(saleoutDetail2.getId().toString());
                                    salesInvoiceBillDetail.setCustomerid(saleout.getCustomerid()!=null?saleout.getCustomerid():"");
                                    salesInvoiceBillDetail.setPcustomerid(saleout.getPcustomerid()!=null?saleout.getPcustomerid():"");
                                    salesInvoiceBillDetail.setCustomersort(saleout.getCustomersort()!=null?saleout.getCustomersort():"");
                                    salesInvoiceBillDetail.setSalesarea(saleout.getSalesarea()!=null?saleout.getSalesarea():"");
                                    salesInvoiceBillDetail.setSalesdept(saleout.getSalesdept()!=null?saleout.getSalesdept():"");
                                    salesInvoiceBillDetail.setSalesuser(saleout.getSalesuser()!=null?saleout.getSalesuser():"");
                                    salesInvoiceBillDetail.setGoodsid(saleoutDetail2.getGoodsid()!=null?saleoutDetail2.getGoodsid():"");
                                    salesInvoiceBillDetail.setBranddept(saleoutDetail2.getBranddept()!=null?saleoutDetail2.getBranddept():"");
                                    salesInvoiceBillDetail.setCostprice(saleoutDetail2.getCostprice());
                                    //商品品牌 和品牌业务员信息
                                    GoodsInfo goodsInfo = getAllGoodsInfoByID(saleoutDetail2.getGoodsid());
                                    if(null!=goodsInfo){
                                        salesInvoiceBillDetail.setGoodssort(goodsInfo.getDefaultsort());
                                        salesInvoiceBillDetail.setBrandid(goodsInfo.getBrand());
                                        salesInvoiceBillDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceBillDetail.getCustomerid()));
                                        //厂家业务员
                                        salesInvoiceBillDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceBill.getCustomerid()));
                                        //商品默认供应商
                                        salesInvoiceBillDetail.setSupplierid(goodsInfo.getDefaultsupplier());
                                    }
                                    salesInvoiceBillDetail.setUnitid(saleoutDetail2.getUnitid());
                                    salesInvoiceBillDetail.setUnitname(saleoutDetail2.getUnitname());
                                    salesInvoiceBillDetail.setUnitnum(saleoutDetail2.getUnitnum());
                                    salesInvoiceBillDetail.setAuxunitid(saleoutDetail2.getAuxunitid());
                                    salesInvoiceBillDetail.setAuxunitname(saleoutDetail2.getAuxunitname());
                                    Map auxMap = countGoodsInfoNumber(saleoutDetail2.getGoodsid(), saleoutDetail2.getAuxunitid(), saleoutDetail2.getUnitnum());
                                    salesInvoiceBillDetail.setAuxnum((BigDecimal) auxMap.get("auxnum"));
                                    salesInvoiceBillDetail.setAuxnumdetail( (String) auxMap.get("auxnumdetail"));

                                    //退货通知单生成的发票 金额为负
                                    salesInvoiceBillDetail.setTaxprice(saleoutDetail2.getTaxprice());
                                    salesInvoiceBillDetail.setTaxamount(saleoutDetail2.getTaxamount());
                                    salesInvoiceBillDetail.setTaxtype(saleoutDetail2.getTaxtype());
                                    //重新计算税额税额
                                    BigDecimal notaxamount = getNotaxAmountByTaxAmount(salesInvoiceBillDetail.getTaxamount(), salesInvoiceBillDetail.getTaxtype());
                                    salesInvoiceBillDetail.setNotaxamount(notaxamount);
                                    //未税单价 = 未税金额/数量
                                    if(null!=notaxamount && notaxamount.compareTo(BigDecimal.ZERO)!=0
                                            && null!=salesInvoiceBillDetail.getUnitnum() && salesInvoiceBillDetail.getUnitnum().compareTo(BigDecimal.ZERO)!=0){
                                        salesInvoiceBillDetail.setNotaxprice(notaxamount.divide(salesInvoiceBillDetail.getUnitnum(), 6,BigDecimal.ROUND_HALF_UP));
                                    }else{
                                        salesInvoiceBillDetail.setNotaxprice(saleoutDetail2.getNotaxprice());
                                    }
                                    salesInvoiceBillDetail.setTax(salesInvoiceBillDetail.getTaxamount().subtract(salesInvoiceBillDetail.getNotaxamount()));
                                    salesInvoiceBillDetail.setRemark(saleoutDetail2.getRemark());
                                    salesInvoiceBillDetail.setSeq(saleoutDetail2.getSeq());
                                    salesInvoiceBillDetailList.add(salesInvoiceBillDetail);
                                }
                            }else{
                                salesInvoiceBillDetail = new SalesInvoiceBillDetail();
                                salesInvoiceBillDetail.setBillid(salesInvoiceBill.getId());
                                //来源销售发货单
                                salesInvoiceBillDetail.setSourcetype("4");
                                salesInvoiceBillDetail.setSourceid(id);
                                salesInvoiceBillDetail.setSourcedetailid(detailid);
                                salesInvoiceBillDetail.setBillno(id);
                                salesInvoiceBillDetail.setBilldetailid(detailid);
                                salesInvoiceBillDetail.setIsdiscount(saleoutDetail.getIsdiscount());
                                salesInvoiceBillDetail.setCustomerid(saleout.getCustomerid()!=null?saleout.getCustomerid():"");
                                salesInvoiceBillDetail.setPcustomerid(saleout.getPcustomerid()!=null?saleout.getPcustomerid():"");
                                salesInvoiceBillDetail.setCustomersort(saleout.getCustomersort()!=null?saleout.getCustomersort():"");
                                salesInvoiceBillDetail.setSalesarea(saleout.getSalesarea()!=null?saleout.getSalesarea():"");
                                salesInvoiceBillDetail.setSalesdept(saleout.getSalesdept()!=null?saleout.getSalesdept():"");
                                salesInvoiceBillDetail.setSalesuser(saleout.getSalesuser()!=null?saleout.getSalesuser():"");
                                salesInvoiceBillDetail.setGoodsid(saleoutDetail.getGoodsid()!=null?saleoutDetail.getGoodsid():"");
                                salesInvoiceBillDetail.setBranddept(saleoutDetail.getBranddept()!=null?saleoutDetail.getBranddept():"");
                                salesInvoiceBillDetail.setCostprice(saleoutDetail.getCostprice());
                                //商品品牌 和品牌业务员信息
                                GoodsInfo goodsInfo = getAllGoodsInfoByID(saleoutDetail.getGoodsid());
                                if(null!=goodsInfo){
                                    salesInvoiceBillDetail.setGoodssort(goodsInfo.getDefaultsort());
                                    salesInvoiceBillDetail.setBrandid(goodsInfo.getBrand());
                                    salesInvoiceBillDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceBillDetail.getCustomerid()));
                                    //厂家业务员
                                    salesInvoiceBillDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), salesInvoiceBill.getCustomerid()));
                                    //商品默认供应商
                                    salesInvoiceBillDetail.setSupplierid(goodsInfo.getDefaultsupplier());
                                }
                                salesInvoiceBillDetail.setUnitid(saleoutDetail.getUnitid());
                                salesInvoiceBillDetail.setUnitname(saleoutDetail.getUnitname());
                                salesInvoiceBillDetail.setUnitnum(saleoutDetail.getUnitnum());
                                salesInvoiceBillDetail.setAuxunitid(saleoutDetail.getAuxunitid());
                                salesInvoiceBillDetail.setAuxunitname(saleoutDetail.getAuxunitname());
                                Map auxMap = countGoodsInfoNumber(saleoutDetail.getGoodsid(), saleoutDetail.getAuxunitid(), saleoutDetail.getUnitnum());
                                salesInvoiceBillDetail.setAuxnum((BigDecimal) auxMap.get("auxnum"));
                                salesInvoiceBillDetail.setAuxnumdetail( (String) auxMap.get("auxnumdetail"));

                                //退货通知单生成的发票 金额为负
                                salesInvoiceBillDetail.setTaxprice(saleoutDetail.getTaxprice());
                                salesInvoiceBillDetail.setTaxamount(saleoutDetail.getTaxamount());
                                salesInvoiceBillDetail.setTaxtype(saleoutDetail.getTaxtype());
                                //重新计算税额税额
                                BigDecimal notaxamount = getNotaxAmountByTaxAmount(salesInvoiceBillDetail.getTaxamount(), salesInvoiceBillDetail.getTaxtype());
                                salesInvoiceBillDetail.setNotaxamount(notaxamount);
                                //未税单价 = 未税金额/数量
                                if(null!=notaxamount && notaxamount.compareTo(BigDecimal.ZERO)!=0
                                        && null!=salesInvoiceBillDetail.getUnitnum() && salesInvoiceBillDetail.getUnitnum().compareTo(BigDecimal.ZERO)!=0){
                                    salesInvoiceBillDetail.setNotaxprice(notaxamount.divide(salesInvoiceBillDetail.getUnitnum(), 6,BigDecimal.ROUND_HALF_UP));
                                }else{
                                    salesInvoiceBillDetail.setNotaxprice(saleoutDetail.getNotaxprice());
                                }
                                salesInvoiceBillDetail.setTax(salesInvoiceBillDetail.getTaxamount().subtract(salesInvoiceBillDetail.getNotaxamount()));
                                salesInvoiceBillDetail.setRemark(saleoutDetail.getRemark());
                                salesInvoiceBillDetail.setSeq(saleoutDetail.getSeq());
                                salesInvoiceBillDetailList.add(salesInvoiceBillDetail);
                            }
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
                SysUser sysUser = getSysUser();
                salesInvoiceBill.setAdddeptid(sysUser.getDepartmentid());
                salesInvoiceBill.setAdddeptname(sysUser.getDepartmentname());
                salesInvoiceBill.setAdduserid(sysUser.getUserid());
                salesInvoiceBill.setAddusername(sysUser.getName());
                //含税总金额
                BigDecimal alltaxamount = new BigDecimal(0);
                //未税总金额
                BigDecimal allnotaxamount = new BigDecimal(0);
                List<SalesInvoiceBillDetail> newSalesInvoiceBillList = salesInvoiceBillMapper.getSalesInvoiceBillDetailList(salesInvoiceBill.getId());
                for(SalesInvoiceBillDetail salesInvoiceBillDetail : newSalesInvoiceBillList){
                    alltaxamount = alltaxamount.add(salesInvoiceBillDetail.getTaxamount());
                    allnotaxamount = allnotaxamount.add(salesInvoiceBillDetail.getNotaxamount());
                }
                salesInvoiceBill.setTaxamount(alltaxamount);
                salesInvoiceBill.setNotaxamount(allnotaxamount);
                salesInvoiceBill.setSourceid(salesInvoiceBill.getSourceid()+","+sourceids);
                int i = salesInvoiceBillMapper.editSalesInvoiceBill(salesInvoiceBill);
                flag = i>0;
                if(flag){
                    List sourceidList = new ArrayList();
                    if(null!=sourceids){
                        String[] sourceidArr = sourceids.split(",");
                        for(String sourceid : sourceidArr){
                            sourceidList.add(sourceid);
                        }
                        storageSaleOutService.updateSaleoutAdvanceBill(sourceidList,salesInvoiceBill,"1");
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
    public List<SalesInvoiceBill> showSalesInvoiceBillPrintListBy(Map map) throws Exception {
        PageMap pageMap=new PageMap();
        pageMap.setCondition(map);
        String dataSql = getDataAccessRule("t_account_sales_invoicebill", null);
        pageMap.setDataSql(dataSql);

        boolean showdetail=false;
        if(map.containsKey("showdetail") && null!=map.get("showdetail") && "1".equals(map.get("showdetail").toString())){
            showdetail=true;
        }
		
		String isShowListDataForPrint=(String)map.get("isShowListDataForPrint");

		String neggoodstodiscount=(String) map.get("neggoodstodiscount");
		String zkgoodstodiscount=(String) map.get("zkgoodstodiscount");
		String isShowDiscountSum=(String) map.get("isShowDiscountSum");
		
        List<SalesInvoiceBill> list = salesInvoiceBillMapper.getSalesInvoiceBillList(pageMap);
        for(SalesInvoiceBill salesInvoiceBill : list){
            Customer customer = getCustomerByID(salesInvoiceBill.getCustomerid());
            if(null!=customer){
                salesInvoiceBill.setCustomername(customer.getName());
                salesInvoiceBill.setCustomerInfo(customer);
            }
            DepartMent departMent = getDepartmentByDeptid(salesInvoiceBill.getSalesdept());
            if(null!=departMent){
                salesInvoiceBill.setSalesdeptname(departMent.getName());
            }
            Personnel personnel = getPersonnelById(salesInvoiceBill.getSalesuser());
            if(null!=personnel){
                salesInvoiceBill.setSalesusername(personnel.getName());
            }

            if(showdetail){

				Map queryMap=new HashMap();
				queryMap.put("billid", salesInvoiceBill.getId());
				queryMap.put("orderby", "print");
                List<SalesInvoiceBillDetail> detailList = salesInvoiceBillMapper.getSalesInvoiceBillDetailListSumBranddiscountForPrint(queryMap);
                List<SalesInvoiceBillDetail> tmpList=new ArrayList<SalesInvoiceBillDetail>();
                SalesInvoiceBillDetail discountSum=new SalesInvoiceBillDetail();
                SalesInvoiceBillDetail salesInvoiceBillDetailSum=new SalesInvoiceBillDetail();
				TaxType aTaxType=null;
                for(SalesInvoiceBillDetail salesInvoiceBillDetail : detailList){
                	//打印时，金额为零
					if(null!=isShowListDataForPrint && 
							(null == salesInvoiceBillDetail.getNotaxamount() || BigDecimal.ZERO.compareTo(salesInvoiceBillDetail.getNotaxamount())==0  )){
						continue;
					}
					TaxType taxType = getTaxType(salesInvoiceBillDetail.getTaxtype());
                    salesInvoiceBillDetail.setTaxtypename(taxType.getName());					
                    salesInvoiceBillDetail.setTaxTypeInfo(taxType);
					if(null==aTaxType){
						aTaxType=taxType;
					}
                    if(!"3".equals(salesInvoiceBillDetail.getSourcetype())){
                        
                        Map auxmap = countGoodsInfoNumber(salesInvoiceBillDetail.getGoodsid(), salesInvoiceBillDetail.getAuxunitid(), salesInvoiceBillDetail.getUnitnum());
                        String auxnumdetail = (String) auxmap.get("auxnumdetail");
                        salesInvoiceBillDetail.setAuxnumdetail(auxnumdetail);

                        //折扣显示处理
                        GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(getAllGoodsInfoByID(salesInvoiceBillDetail.getGoodsid()));
                        if("1".equals(salesInvoiceBillDetail.getIsdiscount())){
                            goodsInfo.setBarcode(null);
                            goodsInfo.setBoxnum(null);
							if("1".equals(zkgoodstodiscount)){
								//冲差、折扣是否转成折扣
								goodsInfo.setName("折扣");
							}else{
								goodsInfo.setName("(折扣)"+goodsInfo.getName());								
							}
                            if("1".equals(salesInvoiceBillDetail.getIsbranddiscount())){
                                salesInvoiceBillDetail.setGoodsid("");
								if("1".equals(zkgoodstodiscount)){
									//冲差、折扣是否转成折扣
									goodsInfo.setName("折扣");
								}else{	
									goodsInfo.setName("(折扣)"+goodsInfo.getBrandName());								
								}
                            }
							salesInvoiceBillDetail.setUnitname(null);
							salesInvoiceBillDetail.setUnitnum(null);
							salesInvoiceBillDetail.setTaxprice(null);
							salesInvoiceBillDetail.setNotaxprice(null);
							salesInvoiceBillDetail.setAuxnumdetail(null);
							salesInvoiceBillDetail.setAuxunitname(null);
                        }
                      //负商品转为折扣
						if("1".equals(neggoodstodiscount)
								&& "0".equals(salesInvoiceBillDetail.getIsbranddiscount())
								&& "0".equals(salesInvoiceBillDetail.getIsdiscount())
								&& BigDecimal.ZERO.compareTo(salesInvoiceBillDetail.getNotaxamount())>0 ){
							goodsInfo.setBarcode(null);
							goodsInfo.setBoxnum(null);

							//冲差、折扣是否转成折扣
							goodsInfo.setName("折扣");

							salesInvoiceBillDetail.setUnitname(null);
							salesInvoiceBillDetail.setUnitnum(null);
							salesInvoiceBillDetail.setTaxprice(null);
							salesInvoiceBillDetail.setNotaxprice(null);
							salesInvoiceBillDetail.setAuxnumdetail(null);
							salesInvoiceBillDetail.setAuxunitname(null);
							//负商品转为折扣
							salesInvoiceBillDetail.setIsdiscount("1");

						}
                        salesInvoiceBillDetail.setGoodsInfo(goodsInfo);
                    }else{
                        GoodsInfo goodsInfo = new GoodsInfo();
                        Brand brand = getGoodsBrandByID(salesInvoiceBillDetail.getBrandid());
                        if(null!=brand){
							if("1".equals(zkgoodstodiscount)){
								//冲差、折扣是否转成折扣
								goodsInfo.setName("折扣");
							}else{
								goodsInfo.setName("(折扣)"+brand.getName());								
							}
                            salesInvoiceBillDetail.setGoodsInfo(goodsInfo);
                        }
                        
						
						salesInvoiceBillDetail.setUnitname(null);
						salesInvoiceBillDetail.setUnitnum(null);
						salesInvoiceBillDetail.setTaxprice(null);
						salesInvoiceBillDetail.setNotaxprice(null);
						salesInvoiceBillDetail.setAuxnumdetail(null);
						salesInvoiceBillDetail.setAuxunitname(null);
						
                        salesInvoiceBillDetail.setIsbranddiscount("1");
                    }
                    //返利折扣
                    if("2".equals(salesInvoiceBillDetail.getIsdiscount())){
                        GoodsInfo goodsInfo = new GoodsInfo();
                        goodsInfo.setName("折扣");
                        salesInvoiceBillDetail.setGoodsInfo(goodsInfo);
                        
						salesInvoiceBillDetail.setUnitname(null);
						salesInvoiceBillDetail.setUnitnum(null);
						salesInvoiceBillDetail.setTaxprice(null);
						salesInvoiceBillDetail.setNotaxprice(null);
						salesInvoiceBillDetail.setAuxnumdetail(null);
						salesInvoiceBillDetail.setAuxunitname(null);
                    }
                    salesInvoiceBillDetail.setIsreportblank(0);

                    if("1".equals(salesInvoiceBillDetail.getIsbranddiscount()) || !"0".equals(salesInvoiceBillDetail.getIsdiscount())){
                    	if(null==discountSum.getTaxamount()){
							discountSum.setTaxamount(salesInvoiceBillDetail.getTaxamount());
						}else if(null!=salesInvoiceBillDetail.getTaxamount()){
							discountSum.setTaxamount(discountSum.getTaxamount().add(salesInvoiceBillDetail.getTaxamount()));
						}

						//无税金额 = 含税金额/（1+税率） 保存6位小数
						if(null != taxType.getRate()){
						    discountSum.setNotaxamount(discountSum.getTaxamount().divide(taxType.getRate().divide(new BigDecimal(100),6,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1)),6,BigDecimal.ROUND_HALF_UP));
						    discountSum.setTax(discountSum.getTaxamount().subtract(discountSum.getNotaxamount()));
						}else{
							discountSum.setNotaxamount(BigDecimal.ZERO);
							discountSum.setTax(BigDecimal.ZERO);
						}
					}else{												
						if(null==salesInvoiceBillDetailSum.getTaxamount()){
							salesInvoiceBillDetailSum.setTaxamount(salesInvoiceBillDetail.getTaxamount());
						}else if(null!=salesInvoiceBillDetail.getTaxamount()){
							salesInvoiceBillDetailSum.setTaxamount(salesInvoiceBillDetailSum.getTaxamount().add(salesInvoiceBillDetail.getTaxamount()));
						}

						//无税金额 = 含税金额/（1+税率） 保存6位小数
						if(null != taxType.getRate()){
							salesInvoiceBillDetailSum.setNotaxamount(salesInvoiceBillDetailSum.getTaxamount().divide(taxType.getRate().divide(new BigDecimal(100),6,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1)),6,BigDecimal.ROUND_HALF_UP));
							salesInvoiceBillDetailSum.setTax(salesInvoiceBillDetailSum.getTaxamount().subtract(salesInvoiceBillDetailSum.getNotaxamount()));
						}else{
							salesInvoiceBillDetailSum.setNotaxamount(BigDecimal.ZERO);
							salesInvoiceBillDetailSum.setTax(BigDecimal.ZERO);
						}
					}

					if("0".equals(salesInvoiceBillDetail.getIsbranddiscount()) && "0".equals(salesInvoiceBillDetail.getIsdiscount())){
						tmpList.add(salesInvoiceBillDetail);
					}else {
						if(!"1".equals(isShowDiscountSum)){
							tmpList.add(salesInvoiceBillDetail);
						}
					}
                }
                //折扣合并
                if("1".equals(isShowDiscountSum) && null!=discountSum.getNotaxamount()){
                	SalesInvoiceBillDetail detailDiscount=(SalesInvoiceBillDetail)CommonUtils.deepCopy(discountSum);
                	
                	GoodsInfo goodsInfo=new GoodsInfo();
					goodsInfo.setName("折扣");
					detailDiscount.setGoodsInfo(goodsInfo);
					detailDiscount.setIsdiscount("1");			
					
					detailDiscount.setTaxtypename(aTaxType.getName());
					detailDiscount.setTaxTypeInfo(aTaxType);
					
					tmpList.add(detailDiscount);                	
                }
                //原价合计
                if(null!=salesInvoiceBillDetailSum.getNotaxamount()){
					GoodsInfo goodsInfo=new GoodsInfo();
					goodsInfo.setName("原价合计");
					salesInvoiceBillDetailSum.setGoodsInfo(goodsInfo);

					salesInvoiceBillDetailSum.setTaxtypename(aTaxType.getName());
					salesInvoiceBillDetailSum.setTaxTypeInfo(aTaxType);
					
					salesInvoiceBillDetailSum.setIsdiscount("-90099");
					
					tmpList.add(salesInvoiceBillDetailSum);
					
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
                salesInvoiceBill.setDetaiList(tmpList);
            }
        }
        return list;
    }

    @Override
    public boolean updateOrderPrinttimes(SalesInvoiceBill salesInvoiceBill) throws Exception {
        return salesInvoiceBillMapper.updateOrderPrinttimes(salesInvoiceBill)>0;
    }

	@Override
	public void doCloseWriteoffSourcesInvoicebill() throws Exception {
		//获取未核销状态的开票单据（来源单据全部核销了的单据），将核销状态改为已核销
		Map paramMap = new HashMap();
		paramMap.put("iswriteoff","0");
		List<Map> list = salesInvoiceBillMapper.getTotalWriteoffSalesInvoiceBillList(paramMap);
		for(Map map1 : list){
			String salesInvoiceBillid = (String)map1.get("billid");
			Map map = new HashMap();
			map.put("iswriteoff","1");
			map.put("invoicebillid",salesInvoiceBillid);
			salesInvoiceBillMapper.updateSalesInvoiceBillBack(map);
		}
		//获取已核销状态的开票单据，且开票单据状态为审核通过的，关闭该单据
		paramMap.put("iswriteoff","1");
		paramMap.put("status","3");
		List<Map> list2 = salesInvoiceBillMapper.getTotalWriteoffSalesInvoiceBillList(paramMap);
		for(Map map1 : list2){
			String salesInvoiceBillid = (String)map1.get("billid");
			salesInvoiceBillMapper.closeSalesInvoiceBill(salesInvoiceBillid);
		}
		//销售核销单据的开票状态变为已开票（销售核销中来源单据全部开票）
		List<Map> list1 = salesInvoiceMapper.getTotalInvoicebillSalesInvoiceList();
		for(Map map1 : list1){
			String salesInvoiceid = (String)map1.get("salesinvoiceid");

			Map map = new HashMap();
			map.put("isinvoicebill","1");
			map.put("invoiceid",salesInvoiceid);
			salesInvoiceMapper.updateSalesInvoiceBack(map);
		}
	}

	@Override
	public Map doCheckSalesInvoiceBillCanMuApplyWriteOff(String ids) throws Exception {
		String msg = "",advancemsg="",unids = "",doids = "",unallids = "",unadvanceids="";
		Map map = new HashMap();
		if(StringUtils.isNotEmpty(ids)){
			String[] idArr = ids.split(",");
			for(String id : idArr){
				SalesInvoiceBill salesInvoiceBill = salesInvoiceBillMapper.getSalesInvoiceBillInfo(id);
				if("1".equals(salesInvoiceBill.getBilltype())){//正常开票
					//是否完全销售核销 false 完全 true 未完全
					boolean flag2 = checkSalesInvoiceBillCanMuApplyWriteOffAll(id);
					if(flag2){
						//是否已存在销售核销的单据
						boolean flag = checkSalesInvoiceBillCanMuApplyWriteOff(id);
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
				}else if("2".equals(salesInvoiceBill.getBilltype())){//预开票
					String sourceids = salesInvoiceBill.getSourceid();
					String[] sourceidArr = sourceids.split(",");
					int donum=0;//可执行申请核销数量
					for(String saleoutid :sourceidArr){
						boolean flag = salesOutService.checkIsDoneApplyWriteOffCaseReceipt(saleoutid);
						if(flag){
							donum++;
						}
					}
					if(donum == sourceidArr.length){
						if(StringUtils.isNotEmpty(doids)){
							doids += "," + id;
						}else {
							doids = id;
						}
					}else{
						if(StringUtils.isNotEmpty(unadvanceids)){
							unadvanceids += "," +id;
						}else{
							unadvanceids = id;
						}
					}
				}
			}
			if(StringUtils.isNotEmpty(unallids)){
				msg = "销售开票编号：" +unallids+ "来源单据已完全销售核销！";
			}
			if(StringUtils.isNotEmpty(unids)){
				if(StringUtils.isNotEmpty(msg)){
					msg += "<br>" + unids+ "存在已申请核销过的单据！";
				}else{
					msg = unids+ "存在已申请核销过的单据！";
				}
			}
			if(StringUtils.isNotEmpty(unadvanceids)){
				advancemsg = "销售开票编号："+unadvanceids+ "发货回单未全部验收,不允许申请核销！";
			}
			map.put("doids",doids);
			map.put("msg",msg);
			map.put("unadvanceids",unadvanceids);
			map.put("advancemsg",advancemsg);
		}
		return map;
	}

	@Override
	public PageData getSalesInvoiceBillDetailList(PageMap pageMap) throws Exception {
		List<SalesInvoiceBillDetail> detailList = salesInvoiceBillMapper.getSalesInvoiceBillDetailListSumBranddiscountByPageMap(pageMap);
		for(SalesInvoiceBillDetail salesInvoiceBillDetail : detailList){
			if("3".equals(salesInvoiceBillDetail.getSourcetype())){
				GoodsInfo goodsInfo = new GoodsInfo();
				Brand brand = getGoodsBrandByID(salesInvoiceBillDetail.getBrandid());
				if(null!=brand){
					goodsInfo.setName("(折扣)"+brand.getName());
					salesInvoiceBillDetail.setGoodsname("(折扣)"+brand.getName());
					salesInvoiceBillDetail.setIsrebate("1");
					salesInvoiceBillDetail.setGoodsInfo(goodsInfo);
					salesInvoiceBillDetail.setUnitnum(new BigDecimal(1));
					salesInvoiceBillDetail.setTaxprice(salesInvoiceBillDetail.getTaxamount());
					salesInvoiceBillDetail.setNotaxprice(salesInvoiceBillDetail.getNotaxamount());
					if(StringUtils.isNotEmpty(salesInvoiceBillDetail.getTaxtype())){
						TaxType taxType = getTaxType(salesInvoiceBillDetail.getTaxtype());
						if(null!=taxType){
							salesInvoiceBillDetail.setTaxtypename(taxType.getName());
						}
					}
				}
			}else{
				if(StringUtils.isNotEmpty(salesInvoiceBillDetail.getTaxtype())){
					TaxType taxType = getTaxType(salesInvoiceBillDetail.getTaxtype());
					if(null!=taxType){
						salesInvoiceBillDetail.setTaxtypename(taxType.getName());
					}
				}
				Map auxmap = countGoodsInfoNumber(salesInvoiceBillDetail.getGoodsid(), salesInvoiceBillDetail.getAuxunitid(), salesInvoiceBillDetail.getUnitnum());
				String auxnumdetail = (String) auxmap.get("auxnumdetail");
				salesInvoiceBillDetail.setAuxnumdetail(auxnumdetail);

				//折扣显示处理
				GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(getAllGoodsInfoByID(salesInvoiceBillDetail.getGoodsid()));
				if(null != goodsInfo){
					salesInvoiceBillDetail.setGoodsname(goodsInfo.getName());
					salesInvoiceBillDetail.setBarcode(goodsInfo.getBarcode());
					salesInvoiceBillDetail.setModel(goodsInfo.getModel());
					salesInvoiceBillDetail.setBrandname(goodsInfo.getBrandName());
					salesInvoiceBillDetail.setBoxnum(goodsInfo.getBoxnum());
					if("1".equals(salesInvoiceBillDetail.getIsdiscount())){
						goodsInfo.setBarcode(null);
						goodsInfo.setBoxnum(null);
						goodsInfo.setName("(折扣)"+goodsInfo.getName());
						salesInvoiceBillDetail.setBarcode(null);
						salesInvoiceBillDetail.setBoxnum(null);
						salesInvoiceBillDetail.setGoodsname("(折扣)" + goodsInfo.getName());
						salesInvoiceBillDetail.setIsrebate("1");
						//salesInvoiceDetail.setUnitnum(null);
						salesInvoiceBillDetail.setUnitnum(new BigDecimal(1));
						salesInvoiceBillDetail.setAuxnumdetail(null);
						salesInvoiceBillDetail.setTaxprice(null);
						if("1".equals(salesInvoiceBillDetail.getIsbranddiscount())){
							salesInvoiceBillDetail.setGoodsid("");
							goodsInfo.setName("(折扣)"+goodsInfo.getBrandName());
							salesInvoiceBillDetail.setGoodsname("(折扣)" + goodsInfo.getBrandName());
							salesInvoiceBillDetail.setIsrebate("1");
//							salesInvoiceDetail.setIsdiscount("2");
						}
					}
					salesInvoiceBillDetail.setGoodsInfo(goodsInfo);
				}
			}
			//返利折扣
			if("2".equals(salesInvoiceBillDetail.getIsdiscount())){
				GoodsInfo goodsInfo = new GoodsInfo();
				goodsInfo.setName("折扣");
				salesInvoiceBillDetail.setGoodsname("折扣");
				salesInvoiceBillDetail.setIsrebate("1");
				salesInvoiceBillDetail.setGoodsInfo(goodsInfo);
				salesInvoiceBillDetail.setUnitnum(new BigDecimal(1));
			}
		}
		int count = salesInvoiceBillMapper.getSalesInvoiceBillDetailCountSumBranddiscountByPageMap(pageMap);
		List<SalesInvoiceBillDetail> foot = salesInvoiceBillMapper.getSalesInvoiceBillDetailTotalSumBranddiscountByPageMap(pageMap);
		if(null != foot && foot.size() != 0){
			for(SalesInvoiceBillDetail salesInvoiceBillDetail : foot){
				salesInvoiceBillDetail.setGoodsid("合计");
			}
		}else{
			foot = new ArrayList();
		}
		PageData pageData = new PageData(count,detailList,pageMap);
		pageData.setFooter(foot);
		return pageData;
	}

    /**
     * 获取销售开票列表数据
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public List getSalesInvoiceBillListByPhone(Map map) throws Exception {
        String dataSql = getDataAccessRule("t_account_sales_invoice", "t");
        map.put("datasql", dataSql);
        List<Map> list = salesInvoiceBillMapper.getSalesInvoiceBillListByPhone(map);
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
            String iswriteoff = (String) dataMap.get("iswriteoff");
            if("1".equals(iswriteoff)){
                dataMap.put("applytype", "已核销");
            }else{
                dataMap.put("applytype", "未核销");
            }
        }
        return list;
    }

	@Override
	public List getSalesInvoiceBillListPageByIds(String ids) throws Exception {
		List<SalesInvoiceBill> list = salesInvoiceBillMapper.getSalesInvoiceBillListPageByIds(ids);
		for(SalesInvoiceBill salesInvoiceBill : list){
			Customer customer = getCustomerByID(salesInvoiceBill.getCustomerid());
			if(null!=customer){
				salesInvoiceBill.setCustomername(customer.getName());
			}
			if(null!=salesInvoiceBill.getChlidcustomerid()){
				String[] childcustomer = salesInvoiceBill.getChlidcustomerid().split(",");
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
				salesInvoiceBill.setChlidcustomername(childcustomername);
			}
			Contacter contacter = getContacterById(salesInvoiceBill.getHandlerid());
			if(null!=contacter){
				salesInvoiceBill.setHandlername(contacter.getName());
			}
			DepartMent departMent = getDepartmentByDeptid(salesInvoiceBill.getSalesdept());
			if(null!=departMent){
				salesInvoiceBill.setSalesdeptname(departMent.getName());
			}
			Personnel personnel = getPersonnelById(salesInvoiceBill.getSalesuser());
			if(null!=personnel){
				salesInvoiceBill.setSalesusername(personnel.getName());
			}
		}
		return list;
	}
	@Override
	public Map getSalesInvoiceBillForHTKP(String id) throws Exception {
		Map resultMap = new HashMap();
		resultMap.put("flag",false);
		SalesInvoiceBill salesInvoiceBill = salesInvoiceBillMapper.getSalesInvoiceBillInfo(id);
		if(null == salesInvoiceBill) {
			resultMap.put("flag",false);
			resultMap.put("msg","未能找到相关单据信息");
			return resultMap;
		}
		resultMap.put("flag",true);
		CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(salesInvoiceBill.getCustomerid());
		if(null!=customerCapital){
			salesInvoiceBill.setCustomeramount(customerCapital.getAmount());
		}else{
			salesInvoiceBill.setCustomeramount(BigDecimal.ZERO);
		}
		Customer customerInfo = getCustomerByID(salesInvoiceBill.getCustomerid());
		if(null!=customerInfo){
			salesInvoiceBill.setCustomername(customerInfo.getName());
			resultMap.put("customerInfo",customerInfo);
		}
		Contacter contacter = getContacterById(salesInvoiceBill.getHandlerid());
		if(null!=contacter){
			salesInvoiceBill.setHandlername(contacter.getName());
			resultMap.put("handlerInfo",contacter);
		}
		Personnel personnel = getPersonnelById(salesInvoiceBill.getSalesuser());
		if(null!=personnel){
			salesInvoiceBill.setSalesusername(personnel.getName());
		}
		List<SalesInvoiceBillDetail> detailList = salesInvoiceBillMapper.getSalesInvoiceBillDetailListSumForHTKP(id);

		List<SalesInvoiceBillDetail> kpdetailList=new ArrayList<SalesInvoiceBillDetail>();

		BigDecimal zsAmount=BigDecimal.ZERO;
		BigDecimal discountAmount=BigDecimal.ZERO;
		BigDecimal discountTax=BigDecimal.ZERO;
		SalesInvoiceBillDetail maxDetailItem=null;
		for(SalesInvoiceBillDetail salesInvoiceBillDetail : detailList){
			if(!"3".equals(salesInvoiceBillDetail.getSourcetype())&&
					"0".equals(salesInvoiceBillDetail.getIsdiscount()) &&
					"0".equals(salesInvoiceBillDetail.getIsbranddiscount()) &&
					BigDecimal.ZERO.compareTo(salesInvoiceBillDetail.getTaxamount())<0){
				//商品品牌 和品牌业务员信息
				GoodsInfo goodsInfo = getAllGoodsInfoByID(salesInvoiceBillDetail.getGoodsid());
				if(null==goodsInfo) {
					goodsInfo=new GoodsInfo();
				}
				salesInvoiceBillDetail.setGoodsInfo(goodsInfo);
				salesInvoiceBillDetail.setGoodsname(goodsInfo.getName());

				TaxType taxType = getTaxType(salesInvoiceBillDetail.getTaxtype());
				if(null!=taxType){
					salesInvoiceBillDetail.setTaxtypename(taxType.getName());
					salesInvoiceBillDetail.setTaxTypeInfo(taxType);

					salesInvoiceBillDetail.setTaxrate(taxType.getRate().divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
				}else{
					salesInvoiceBillDetail.setTaxTypeInfo(new TaxType());
				}
				salesInvoiceBillDetail.setTaxamount(salesInvoiceBillDetail.getTaxamount().setScale(2,BigDecimal.ROUND_HALF_UP));
				BigDecimal notaxamount=getNotaxAmountByTaxAmount(salesInvoiceBillDetail.getTaxamount(),salesInvoiceBillDetail.getTaxtype());
				salesInvoiceBillDetail.setNotaxamount(notaxamount);
				salesInvoiceBillDetail.setTax(salesInvoiceBillDetail.getTaxamount().subtract(notaxamount).setScale(2, BigDecimal.ROUND_HALF_UP));
				salesInvoiceBillDetail.setTaxprice(salesInvoiceBillDetail.getTaxamount().divide(salesInvoiceBillDetail.getUnitnum(),6,BigDecimal.ROUND_HALF_UP));
				if(null==maxDetailItem){
					maxDetailItem=salesInvoiceBillDetail;
				}else{
					if(maxDetailItem.getTaxamount().compareTo(salesInvoiceBillDetail.getTaxamount())<0){
						maxDetailItem=salesInvoiceBillDetail;
					}
				}
				zsAmount=zsAmount.add(salesInvoiceBillDetail.getTaxamount());
				kpdetailList.add(salesInvoiceBillDetail);
				continue;
			}
			discountAmount=discountAmount.add(salesInvoiceBillDetail.getTaxamount());
			discountTax=discountTax.add(salesInvoiceBillDetail.getTaxamount());
		}
		discountAmount=discountAmount.abs();
		discountTax=discountTax.abs();
		if(null==maxDetailItem){
			resultMap.put("flag",false);
			resultMap.put("msg", MessageFormat.format("单据{0}无法导出正数发票",id));
			return resultMap;
		}
		if(zsAmount.compareTo(discountAmount)<0){
			resultMap.put("flag",false);
			resultMap.put("msg",MessageFormat.format("单据{0},折扣金额:{1} 不能大于含税金额:{2}。",
					id,
					discountAmount,
					zsAmount.toString()));
			return resultMap;
		}
		List<Map> resultDetailList=new ArrayList<Map>();
		for(SalesInvoiceBillDetail detailItem:kpdetailList){
			Map itemMap= PropertyUtils.describe(detailItem);
			if(maxDetailItem.getId()==detailItem.getId()){
				itemMap.put("discountamount",discountAmount);
				itemMap.put("discounttax",discountTax);
			}else{
				itemMap.put("discountamount",BigDecimal.ZERO);
				itemMap.put("discounttax",BigDecimal.ZERO);
			}
			if (itemMap.containsKey("class")){
				itemMap.remove("class");
			}
			resultDetailList.add(itemMap);
		}
		Map billMap=PropertyUtils.describe(salesInvoiceBill);
		if(billMap.containsKey("class")){
			billMap.remove("class");
		}
		resultMap.put("salesInvoiceBill", billMap);
		resultMap.put("detailList", resultDetailList);
		resultMap.put("customerInfo",customerInfo);
		return resultMap;
	}

	@Override
	public Map getSalesInvoiceBillXMLForHTKP(String id) throws Exception {
		Map resultMap = new HashMap();
		resultMap.put("flag",false);
		SalesInvoiceBill salesInvoiceBill = salesInvoiceBillMapper.getSalesInvoiceBillInfo(id);
		if(null == salesInvoiceBill) {
			resultMap.put("flag",false);
			resultMap.put("msg","未能找到相关单据信息");
			return resultMap;
		}
		resultMap.put("flag",true);
		CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(salesInvoiceBill.getCustomerid());
		if(null!=customerCapital){
			salesInvoiceBill.setCustomeramount(customerCapital.getAmount());
		}else{
			salesInvoiceBill.setCustomeramount(BigDecimal.ZERO);
		}
		Customer customerInfo = getCustomerByID(salesInvoiceBill.getCustomerid());
		if(null!=customerInfo){
			salesInvoiceBill.setCustomername(customerInfo.getName());
			resultMap.put("customerInfo",customerInfo);
		}
		Contacter contacter = getContacterById(salesInvoiceBill.getHandlerid());
		if(null!=contacter){
			salesInvoiceBill.setHandlername(contacter.getName());
			resultMap.put("handlerInfo",contacter);
		}
		Personnel personnel = getPersonnelById(salesInvoiceBill.getSalesuser());
		if(null!=personnel){
			salesInvoiceBill.setSalesusername(personnel.getName());
		}
		List<SalesInvoiceBillDetail> detailList = salesInvoiceBillMapper.getSalesInvoiceBillDetailListSumXMLForHTKP(id);

		List<SalesInvoiceBillDetail> kpdetailList=new ArrayList<SalesInvoiceBillDetail>();

		BigDecimal zsAmount=BigDecimal.ZERO;
		BigDecimal discountAmount=BigDecimal.ZERO;
		BigDecimal discountTax=BigDecimal.ZERO;
		for(SalesInvoiceBillDetail salesInvoiceBillDetail : detailList){
			if(!"3".equals(salesInvoiceBillDetail.getSourcetype())&&
					"0".equals(salesInvoiceBillDetail.getIsdiscount()) &&
					"0".equals(salesInvoiceBillDetail.getIsbranddiscount()) &&
					BigDecimal.ZERO.compareTo(salesInvoiceBillDetail.getTaxamount())<0){
				//商品品牌 和品牌业务员信息
				GoodsInfo goodsInfo = getAllGoodsInfoByID(salesInvoiceBillDetail.getGoodsid());
				if(null==goodsInfo) {
					goodsInfo=new GoodsInfo();
				}
				salesInvoiceBillDetail.setGoodsInfo(goodsInfo);
				salesInvoiceBillDetail.setGoodsname(goodsInfo.getName());

				TaxType taxType = getTaxType(salesInvoiceBillDetail.getTaxtype());
				if(null!=taxType){
					salesInvoiceBillDetail.setTaxtypename(taxType.getName());
					salesInvoiceBillDetail.setTaxTypeInfo(taxType);

					salesInvoiceBillDetail.setTaxrate(taxType.getRate().divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
				}else{
					salesInvoiceBillDetail.setTaxTypeInfo(new TaxType());
				}
				salesInvoiceBillDetail.setTaxamount(salesInvoiceBillDetail.getTaxamount().setScale(2,BigDecimal.ROUND_HALF_UP));
				BigDecimal notaxamount=salesInvoiceBillDetail.getNotaxamount().setScale(2,BigDecimal.ROUND_HALF_UP);
				salesInvoiceBillDetail.setNotaxamount(notaxamount);
				salesInvoiceBillDetail.setTax(salesInvoiceBillDetail.getTax().setScale(2, BigDecimal.ROUND_HALF_UP));
				BigDecimal taxprice = salesInvoiceBillDetail.getTaxprice();
				if(BigDecimal.ZERO.compareTo(salesInvoiceBillDetail.getUnitnum())!=0) {
					taxprice = salesInvoiceBillDetail.getTaxamount().divide(salesInvoiceBillDetail.getUnitnum(), 6, BigDecimal.ROUND_HALF_UP);
				}
				salesInvoiceBillDetail.setTaxprice(taxprice);
				BigDecimal notaxprice =salesInvoiceBillDetail.getNotaxprice();
				if(BigDecimal.ZERO.compareTo(salesInvoiceBillDetail.getUnitnum())!=0) {
					//金税里是9位
					notaxprice =salesInvoiceBillDetail.getNotaxamount().divide(salesInvoiceBillDetail.getUnitnum(), 9, BigDecimal.ROUND_HALF_UP);
				}
				salesInvoiceBillDetail.setNotaxprice(notaxprice);

				zsAmount=zsAmount.add(salesInvoiceBillDetail.getTaxamount());
				kpdetailList.add(salesInvoiceBillDetail);
				continue;
			}
			discountAmount=discountAmount.add(salesInvoiceBillDetail.getTaxamount());
			discountTax=discountTax.add(salesInvoiceBillDetail.getTaxamount());
		}
		discountAmount=discountAmount;
		discountTax=discountTax;
		if(zsAmount.compareTo(discountAmount)<0){
			resultMap.put("flag",false);
			resultMap.put("msg",MessageFormat.format("单据{0},总折扣金额:{1} 不能大于总含税金额:{2}。",
					id,
					discountAmount,
					zsAmount.toString()));
			return resultMap;
		}
		List<Map> resultDetailList=new ArrayList<Map>();
		List<Map<String,Object>> errorDataList=new ArrayList<Map<String, Object>>();

		for(SalesInvoiceBillDetail detailItem:kpdetailList){
			Map itemMap= PropertyUtils.describe(detailItem);
			if (itemMap.containsKey("class")){
				itemMap.remove("class");
			}
			GoodsInfo goodsInfo=detailItem.getGoodsInfo();
			if(null==goodsInfo || StringUtils.isEmpty(goodsInfo.getJstaxsortid())){
				itemMap.put("jstaxsortid","");
				itemMap.put("errormessage","该商品编码没有对应金税分类编码，请维护该信息");
				errorDataList.add(itemMap);
				continue;
			}
			itemMap.put("jstaxsortid",goodsInfo.getJstaxsortid());


			TaxType itemTaxType=detailItem.getTaxTypeInfo();
			itemMap.put("syyhzcbz","0");
			itemMap.put("lslbz","");
			itemMap.put("yhzcsm","");
			if(null!=itemTaxType) {
				if (BigDecimal.ZERO.compareTo(itemTaxType.getRate()) == 0) {
					itemMap.put("lslbz", "3");
				}
				if ("1".equals(itemTaxType.getJsflag())) {
					itemMap.put("syyhzcbz", "1");
					itemMap.put("lslbz", "1");
					itemMap.put("yhzcsm", "免税");
				}
			}
			resultDetailList.add(itemMap);
		}
		if(errorDataList.size()>0){
			resultMap.put("flag",false);
			resultMap.put("errorDataList",errorDataList);
			return resultMap;
		}
		Map billMap=PropertyUtils.describe(salesInvoiceBill);
		if(billMap.containsKey("class")){
			billMap.remove("class");
		}
		resultMap.put("salesInvoiceBill", billMap);
		resultMap.put("detailList", resultDetailList);
		resultMap.put("customerInfo",customerInfo);
		resultMap.put("flag",true);
		if(BigDecimal.ZERO.compareTo(discountAmount)>0){
			String msg="生成金税导入文件时，单号“"+id+"”中负数金额：￥"+discountAmount.setScale(2,BigDecimal.ROUND_HALF_UP);
			resultMap.put("amountmsg",msg);
		}
		return resultMap;
	}

	@Override
	public Map getSalesInvoiceBillForHTKPHZ(String id) throws Exception {
		Map resultMap = new HashMap();
		resultMap.put("flag",false);
		SalesInvoiceBill salesInvoiceBill = salesInvoiceBillMapper.getSalesInvoiceBillInfo(id);
		if(null == salesInvoiceBill) {
			resultMap.put("flag",false);
			resultMap.put("msg","未能找到相关单据信息");
		}
		resultMap.put("flag",true);
		CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(salesInvoiceBill.getCustomerid());
		if(null!=customerCapital){
			salesInvoiceBill.setCustomeramount(customerCapital.getAmount());
		}else{
			salesInvoiceBill.setCustomeramount(BigDecimal.ZERO);
		}
		Customer customerInfo = getCustomerByID(salesInvoiceBill.getCustomerid());
		if(null!=customerInfo){
			salesInvoiceBill.setCustomername(customerInfo.getName());
			resultMap.put("customerInfo",customerInfo);
		}
		Contacter contacter = getContacterById(salesInvoiceBill.getHandlerid());
		if(null!=contacter){
			salesInvoiceBill.setHandlername(contacter.getName());
			resultMap.put("handlerInfo",contacter);
		}
		Personnel personnel = getPersonnelById(salesInvoiceBill.getSalesuser());
		if(null!=personnel){
			salesInvoiceBill.setSalesusername(personnel.getName());
		}
		List<SalesInvoiceBillDetail> detailList = salesInvoiceBillMapper.getSalesInvoiceBillDetailListSumForHTKP(id);

		List<SalesInvoiceBillDetail> kpdetailList=new ArrayList<SalesInvoiceBillDetail>();

		boolean isdiscount=false;
		boolean iszs=false;
		for(SalesInvoiceBillDetail salesInvoiceBillDetail : detailList){
			if(!"3".equals(salesInvoiceBillDetail.getSourcetype()) &&
					"0".equals(salesInvoiceBillDetail.getIsdiscount()) &&
					"0".equals(salesInvoiceBillDetail.getIsbranddiscount()) &&
					BigDecimal.ZERO.compareTo(salesInvoiceBillDetail.getTaxamount())<0){
				//商品品牌 和品牌业务员信息
				GoodsInfo goodsInfo = getAllGoodsInfoByID(salesInvoiceBillDetail.getGoodsid());
				if(null==goodsInfo) {
					goodsInfo=new GoodsInfo();
				}
				salesInvoiceBillDetail.setGoodsInfo(goodsInfo);
				salesInvoiceBillDetail.setGoodsname(goodsInfo.getName());

				TaxType taxType = getTaxType(salesInvoiceBillDetail.getTaxtype());
				if(null!=taxType){
					salesInvoiceBillDetail.setTaxtypename(taxType.getName());
					salesInvoiceBillDetail.setTaxTypeInfo(taxType);

					salesInvoiceBillDetail.setTaxrate(taxType.getRate().divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
				}else{
					salesInvoiceBillDetail.setTaxTypeInfo(new TaxType());
				}

				salesInvoiceBillDetail.setTaxamount(salesInvoiceBillDetail.getTaxamount().setScale(2,BigDecimal.ROUND_HALF_UP));
				BigDecimal notaxamount=salesInvoiceBillDetail.getNotaxamount().setScale(2,BigDecimal.ROUND_HALF_UP);
				salesInvoiceBillDetail.setNotaxamount(notaxamount);
				salesInvoiceBillDetail.setTax(salesInvoiceBillDetail.getTax().setScale(2, BigDecimal.ROUND_HALF_UP));
				BigDecimal taxprice = salesInvoiceBillDetail.getTaxprice();
				if(BigDecimal.ZERO.compareTo(salesInvoiceBillDetail.getUnitnum())!=0) {
					taxprice = salesInvoiceBillDetail.getTaxamount().divide(salesInvoiceBillDetail.getUnitnum(), 6, BigDecimal.ROUND_HALF_UP);
				}
				salesInvoiceBillDetail.setTaxprice(taxprice);
				BigDecimal notaxprice =salesInvoiceBillDetail.getNotaxprice();
				if(BigDecimal.ZERO.compareTo(salesInvoiceBillDetail.getUnitnum())!=0) {
					//金税里是9位
					notaxprice =salesInvoiceBillDetail.getNotaxamount().divide(salesInvoiceBillDetail.getUnitnum(), 9, BigDecimal.ROUND_HALF_UP);
				}
				salesInvoiceBillDetail.setNotaxprice(notaxprice);

				kpdetailList.add(salesInvoiceBillDetail);
				if(BigDecimal.ONE.compareTo(salesInvoiceBillDetail.getTaxamount())<0){
					iszs=true;
					break;
				}
				continue;
			}
			isdiscount=true;
		}
		if(isdiscount){
			resultMap.put("flag",false);
			resultMap.put("msg",MessageFormat.format("单据{0}，有折扣无法导出负数发票",id));
			return resultMap;
		}
		if(iszs){
			resultMap.put("flag",false);
			resultMap.put("msg",MessageFormat.format("单据{0}，有正数明细无法导出负数发票",id));
			return resultMap;
		}
		List<Map> resultDetailList=new ArrayList<Map>();
		for(SalesInvoiceBillDetail detailItem:kpdetailList){
			Map itemMap= PropertyUtils.describe(detailItem);

			TaxType itemTaxType=detailItem.getTaxTypeInfo();
			itemMap.put("syyhzcbz","0");
			itemMap.put("lslbz","");
			itemMap.put("yhzcsm","");
			if(null!=itemTaxType) {
				if (BigDecimal.ZERO.compareTo(itemTaxType.getRate()) == 0) {
					itemMap.put("lslbz", "3");
				}
				if ("1".equals(itemTaxType.getJsflag())) {
					itemMap.put("syyhzcbz", "1");
					itemMap.put("lslbz", "1");
					itemMap.put("yhzcsm", "免税");
				}
			}

			itemMap.put("discountamount",BigDecimal.ZERO);
			itemMap.put("discounttax",BigDecimal.ZERO);
			if (itemMap.containsKey("class")){
				itemMap.remove("class");
			}
			resultDetailList.add(itemMap);
		}
		Map billMap= PropertyUtils.describe(salesInvoiceBill);
		if(billMap.containsKey("class")){
			billMap.remove("class");
		}
		resultMap.put("salesInvoiceBill", billMap);
		resultMap.put("detailList", resultDetailList);
		resultMap.put("customerInfo",customerInfo);
		return resultMap;
	}
	@Override
	public boolean updateOrderJSExportTimes(SalesInvoiceBill salesInvoiceBill) throws Exception{
		if(null==salesInvoiceBill.getJxexportdatetime()){
			salesInvoiceBill.setJxexportdatetime(new Date());
		}
		return salesInvoiceBillMapper.updateOrderJSExportTimes(salesInvoiceBill)>0;
	}

	public SalesInvoiceBill getSalesInvoiceBillInfoById(String id) throws Exception {
		SalesInvoiceBill salesInvoiceBill = salesInvoiceBillMapper.getSalesInvoiceBillInfo(id);
		if(null != salesInvoiceBill) {
			CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(salesInvoiceBill.getCustomerid());
			if (null != customerCapital) {
				salesInvoiceBill.setCustomeramount(customerCapital.getAmount());
			} else {
				salesInvoiceBill.setCustomeramount(BigDecimal.ZERO);
			}
			Customer customer = getCustomerByID(salesInvoiceBill.getCustomerid());
			if (null != customer) {
				salesInvoiceBill.setCustomername(customer.getName());
				salesInvoiceBill.setCustomerInfo(customer);
			}
			Personnel personnel = getPersonnelById(salesInvoiceBill.getSalesuser());
			if (null != personnel) {
				salesInvoiceBill.setSalesusername(personnel.getName());
			}
		}
		return salesInvoiceBill;
	}

	@Override
	public Map andCheckSalesInvoiceBillListByImportForHTKP(String orderId,List<Map<String,Object>> dataList) throws Exception{

		Map resultMap=new HashMap();
		resultMap.put("flag",true);
		if(null==dataList || dataList.size()==0){
			resultMap.put("flag",false);
			resultMap.put("msg","无法读取上传文件内容");
			return resultMap;
		}
		if(null==orderId || "".equals(orderId.trim())){
			resultMap.put("flag",false);
			resultMap.put("msg","当前单据号为空");
			return resultMap;
		}
		SalesInvoiceBill salesInvoiceBill=getSalesInvoiceBillInfoById(orderId);
		if(null==salesInvoiceBill){
			resultMap.put("flag",false);
			resultMap.put("msg","未能找到相关单据信息");
			return resultMap;
		}
		List<Map<String,Object>> errorDataList=new ArrayList<Map<String, Object>>();


		BigDecimal notaxamountSum=BigDecimal.ZERO;
		BigDecimal taxSum=BigDecimal.ZERO;
		boolean isbillidcheck=true;

		List<Map<String,Object>> billDetailList=new ArrayList<Map<String,Object>>();
		for(Map<String,Object> itemData:dataList){
			StringBuilder errorsb=new StringBuilder();
			int ierror=1;
			String tmpstr=(String)itemData.get("billid");
			if(null==tmpstr || "".equals(tmpstr.trim())){
				isbillidcheck=false;
				errorsb.append(ierror);
				errorsb.append(")");
				errorsb.append("上传文件中的单据号与当前单据号不一致，");
				errorsb.append("当前行：");
				errorsb.append(tmpstr);
				errorsb.append("当前单据号：");
				errorsb.append(orderId);
				errorsb.append("。 ");
				ierror=ierror+1;
			}
			GoodsInfo goodsInfo=null;
			TaxType taxType=null;
			tmpstr=(String)itemData.get("goodsid");
			if(null==tmpstr || "".equals(tmpstr.trim())){
				errorsb.append(ierror);
				errorsb.append(")商品编号不能为空。 ");
				ierror=ierror+1;
			}else{
				goodsInfo=getGoodsInfoByID(tmpstr);
				if(goodsInfo==null){
					errorsb.append(ierror);
					errorsb.append(")");
					errorsb.append(tmpstr);
					errorsb.append("未能找到商品档案相关信息。 ");
					ierror=ierror+1;
				}else{
					if(StringUtils.isEmpty(goodsInfo.getJstaxsortid())){
						errorsb.append(ierror);
						errorsb.append(")");
						errorsb.append(tmpstr);
						errorsb.append("未能找到对应航天金税税收分类编码信息。 ");
						ierror=ierror+1;
					}
					if(StringUtils.isEmpty(goodsInfo.getDefaulttaxtype())){
						errorsb.append(ierror);
						errorsb.append(")");
						errorsb.append(tmpstr);
						errorsb.append("商品档案里税目未知。 ");
						ierror=ierror+1;
					}else{
						taxType=getTaxType(goodsInfo.getDefaulttaxtype());
						if(taxType==null || null==taxType.getRate()){
							errorsb.append(ierror);
							errorsb.append(")");
							errorsb.append(tmpstr);
							errorsb.append("商品档案里税目未知。 ");
							ierror=ierror+1;
						}
					}
				}
			}
			String unitname=(String)itemData.get("unitname");
			if(null==unitname || "".equals(unitname.trim())){
				unitname=goodsInfo.getMainunitName();
			}
			String tmpStr=(String)itemData.get("notaxamount");
			if(StringUtils.isEmpty(tmpStr) || !CommonUtils.isNumStr(tmpStr)){
				errorsb.append(ierror);
				errorsb.append(")");
				errorsb.append("未税金额不能为空。 ");
				ierror=ierror+1;
			}
			BigDecimal notaxamount=new BigDecimal(tmpStr.trim());
			notaxamountSum=notaxamountSum.add(notaxamount);


			tmpStr=(String)itemData.get("tax");
			if(StringUtils.isEmpty(tmpStr) || !CommonUtils.isNumStr(tmpStr)){
				errorsb.append(ierror);
				errorsb.append(")");
				errorsb.append("税额不能为空。 ");
				ierror=ierror+1;
			}
			BigDecimal tax=new BigDecimal(tmpStr.trim());
			taxSum=taxSum.add(tax);

			tmpStr=(String)itemData.get("unitnum");
			if(StringUtils.isEmpty(tmpStr) || !CommonUtils.isNumStr(tmpStr)){
				errorsb.append(ierror);
				errorsb.append(")");
				errorsb.append("数量不能为空。 ");
				ierror=ierror+1;
			}
			BigDecimal unitnum=new BigDecimal(tmpStr.trim());
			if(BigDecimal.ZERO.compareTo(unitnum)==0 && BigDecimal.ZERO.compareTo(notaxamount)==0){
				continue;
			}
			if(BigDecimal.ZERO.compareTo(unitnum)==0){
				errorsb.append(ierror);
				errorsb.append(")");
				errorsb.append("数量为零。 ");
				ierror=ierror+1;
			}
			if(BigDecimal.ZERO.compareTo(notaxamount)==0){
				errorsb.append(ierror);
				errorsb.append(")");
				errorsb.append("未税金额为零。 ");
				ierror=ierror+1;
			}

			if(ierror>1){
				itemData.put("errormessage",errorsb.toString());
				errorDataList.add(itemData);
				continue;
			}
			//未税单价
			BigDecimal notaxprice=notaxamount.divide(unitnum,9,BigDecimal.ROUND_HALF_UP);

			Map billDetail=new HashMap();
			billDetail.put("billid",orderId);
			billDetail.put("goodsid",goodsInfo.getId());
			billDetail.put("goodsInfo",goodsInfo);
			billDetail.put("goodsname",goodsInfo.getName());
			billDetail.put("notaxamount",notaxamount);
			billDetail.put("notaxprice",notaxprice);
			billDetail.put("unitnum",unitnum);
			billDetail.put("unitname",unitname);
			billDetail.put("taxrate",taxType.getRate().divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
			billDetail.put("syyhzcbz","0");
			billDetail.put("lslbz","");
			billDetail.put("yhzcsm","");
			if(BigDecimal.ZERO.compareTo(taxType.getRate())==0){
				billDetail.put("lslbz","3");
			}
			if("1".equals(taxType.getJsflag())){
				billDetail.put("syyhzcbz","1");
				billDetail.put("lslbz","1");
				billDetail.put("yhzcsm","免税");
			}
			billDetailList.add(billDetail);
		}
		if(errorDataList.size()>0){
			resultMap.put("flag",false);
			resultMap.put("isbillidcheck",isbillidcheck);
			resultMap.put("errorDataList",errorDataList);
			return resultMap;
		}

		Map conditionMap=new HashMap();
		conditionMap.put("billid",orderId);
		PageMap queryPageMap=new PageMap();
		queryPageMap.setCondition(conditionMap);
		List<SalesInvoiceBillDetail> sumFootList = salesInvoiceBillMapper.getSalesInvoiceBillDetailTotalSumBranddiscountByPageMap(queryPageMap);
		if(null != sumFootList && sumFootList.size() != 0){
			BigDecimal sumFootNotaxamount=BigDecimal.ZERO;
			BigDecimal sumFootTax=BigDecimal.ZERO;
			for(SalesInvoiceBillDetail salesInvoiceBillDetail : sumFootList){
				sumFootNotaxamount=sumFootNotaxamount.add(salesInvoiceBillDetail.getNotaxamount());
				sumFootTax= sumFootTax.add(salesInvoiceBillDetail.getTax());
			}
			sumFootNotaxamount = sumFootNotaxamount.setScale(2,BigDecimal.ROUND_HALF_UP);
			sumFootTax = sumFootTax.setScale(2,BigDecimal.ROUND_HALF_UP);
			if(notaxamountSum.compareTo(sumFootNotaxamount)!=0){
				//resultMap.put("flag",false);
				String msg="生成金税导入文件时，单号“"+orderId+"”的导入税额(未税金额+税额)与系统中税额(未税金额+税额)不相等，导入税额（" +(notaxamountSum.add(taxSum))+ "）-单据税额（"+sumFootNotaxamount.add(sumFootTax)+"）= ";
				resultMap.put("diffamountmsg",msg+(notaxamountSum.add(taxSum).subtract(sumFootNotaxamount.add(sumFootTax))));
				//return resultMap;
			}
		}
		resultMap.put("flag",true);
		resultMap.put("salesInvoiceBill",salesInvoiceBill);
		resultMap.put("billDetailList",billDetailList);

		return resultMap;
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

	@Override
	public Map updateJSKPSysParamConfig(Map paramMap) throws Exception{
		SysParam receipterSysParam=getBaseSysParamMapper().getSysParam("JSKPDefaultReceipter");
		Map resultMap=new HashMap();
		if(null==receipterSysParam){
			resultMap.put("flag",false);
			resultMap.put("msg","系统参数“JSKPDefaultReceipter(默认收款人)”不存在，联系管理员进行配置.");
			return resultMap;
		}
		SysParam checkerSysParam=getBaseSysParamMapper().getSysParam("JSKPDefaultChecker");
		resultMap=new HashMap();
		if(null==receipterSysParam){
			resultMap.put("flag",false);
			resultMap.put("msg","系统参数“JSKPDefaultChecker(默认复核人)”不存在，联系管理员进行配置.");
			return resultMap;
		}
		String receiperid=(String)paramMap.get("receipterid");
		if(null==receiperid){
			receiperid="";
		}
		String receipername=(String)paramMap.get("receiptername");
		if(null==receipername){
			receipername="";
		}
		String checkerid=(String)paramMap.get("checkerid");
		if(null==checkerid){
			checkerid="";
		}
		String checkername=(String)paramMap.get("checkername");
		if(null==checkername){
			checkername="";
		}
		SysUser sysUser= getSysUser();
		Date nowDate=new Date();
		SysParam upSysParam=new SysParam();
		upSysParam.setParamid(receipterSysParam.getParamid());
		upSysParam.setModifyuserid(sysUser.getUserid());
		upSysParam.setModifytime(nowDate);
		//更新系统参数值
		upSysParam.setPvalue(receiperid.trim());
		upSysParam.setPvdescription(receipername.trim());
		boolean flag=getBaseSysParamMapper().updateSysParam(upSysParam)>0;

		String msg="";
		if(flag){
			msg="默认收款人更新成功;";
		}else{
			msg="默认收款人更新失败;";
		}

		upSysParam=new SysParam();
		upSysParam.setParamid(checkerSysParam.getParamid());
		upSysParam.setModifyuserid(sysUser.getUserid());
		upSysParam.setModifytime(nowDate);
		//更新系统参数值
		upSysParam.setPvalue(checkerid.trim());
		upSysParam.setPvdescription(checkername.trim());
		boolean chkflag=getBaseSysParamMapper().updateSysParam(upSysParam)>0 ;

		if(flag){
			msg=msg+"默认复核人更新成功;";
		}else{
			msg=msg+"默认复核人更新失败;";
		}

		resultMap.put("flag",flag);
		resultMap.put("msg",msg);
		return resultMap;
	}

	@Override
	public List<Map> getSalesInvoiceBillByIdList(List<String> ids) throws Exception {
		List list=salesInvoiceBillMapper.getSalesInvoiceBillSumData(ids);
		return list;
	}

	/**
	 * 获取销售开票导出数据
	 * @param pageMap
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Mar 02, 2018
	 */
	@Override
	public List getSalesInvoiceBillExportData(PageMap pageMap) throws Exception{
		List<SalesInvoiceBillExport> list=salesInvoiceBillMapper.getSalesInvoiceBillExportData(pageMap);
		for(SalesInvoiceBillExport salesInvoiceBillExport:list){
			CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(salesInvoiceBillExport.getCustomerid());
			if (null != customerCapital) {
				salesInvoiceBillExport.setCustomeramount(customerCapital.getAmount());
			} else {
				salesInvoiceBillExport.setCustomeramount(BigDecimal.ZERO);
			}
			Customer customer = getCustomerByID(salesInvoiceBillExport.getCustomerid());
			if (null != customer) {
				salesInvoiceBillExport.setCustomername(customer.getName());
			}
			Personnel personnel = getPersonnelById(salesInvoiceBillExport.getSalesuser());
			if(null!=personnel){
				salesInvoiceBillExport.setSalesusername(personnel.getName());
			}
			if("3".equals(salesInvoiceBillExport.getSourcetype())){
				GoodsInfo goodsInfo = new GoodsInfo();
				Brand brand = getGoodsBrandByID
						(salesInvoiceBillExport.getBrandid());
				if(null!=brand){
					goodsInfo.setName("(折扣)"+brand.getName());
					salesInvoiceBillExport.setGoodsInfo(goodsInfo);
					salesInvoiceBillExport.setUnitnum(new BigDecimal(1));
					salesInvoiceBillExport.setDtaxprice(salesInvoiceBillExport.getDtaxamount());
					salesInvoiceBillExport.setDnotaxprice(salesInvoiceBillExport.getDnotaxamount());
					if(StringUtils.isNotEmpty
							(salesInvoiceBillExport.getTaxtype())){
						TaxType taxType = getTaxType
								(salesInvoiceBillExport.getTaxtype());
						if(null!=taxType){
							salesInvoiceBillExport.setTaxtypename
									(taxType.getName());
						}
					}
				}
			}else{
				if(StringUtils.isNotEmpty(salesInvoiceBillExport.getTaxtype())){
					TaxType taxType = getTaxType
							(salesInvoiceBillExport.getTaxtype());
					if(null!=taxType){
						salesInvoiceBillExport.setTaxtypename
								(taxType.getName());
					}
				}
				Map auxmap = countGoodsInfoNumber
						(salesInvoiceBillExport.getGoodsid(), salesInvoiceBillExport.getAuxunitid(),
								salesInvoiceBillExport.getUnitnum());
				String auxnumdetail = (String) auxmap.get("auxnumdetail");
				salesInvoiceBillExport.setAuxnumdetail(auxnumdetail);

				//折扣显示处理
				GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(getAllGoodsInfoByID(salesInvoiceBillExport.getGoodsid()));
				if("1".equals(salesInvoiceBillExport.getIsdiscount())&&goodsInfo!=null){
					goodsInfo.setBarcode(null);
					goodsInfo.setBoxnum(null);
					goodsInfo.setName("(折扣)"+goodsInfo.getName());
					//salesInvoiceDetail.setUnitnum(null);
					salesInvoiceBillExport.setUnitnum(new BigDecimal(1));
					salesInvoiceBillExport.setAuxnumdetail(null);
					salesInvoiceBillExport.setDtaxprice(null);
					if("1".equals(salesInvoiceBillExport.getIsbranddiscount
							())){
						salesInvoiceBillExport.setGoodsid("");
						goodsInfo.setName("(折扣)"+goodsInfo.getBrandName());
//							salesInvoiceDetail.setIsdiscount("2");
					}
				}
				salesInvoiceBillExport.setGoodsInfo(goodsInfo);
			}
			//返利折扣
			if("2".equals(salesInvoiceBillExport.getIsdiscount())){
				GoodsInfo goodsInfo = new GoodsInfo();
				goodsInfo.setName("折扣");
				salesInvoiceBillExport.setGoodsInfo(goodsInfo);
				salesInvoiceBillExport.setUnitnum(new BigDecimal(1));
			}
		}
		return list;
	}
}

