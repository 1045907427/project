/**
 * @(#)SalesStatementServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 11, 2013 chenwei 创建版本
 */
package com.hd.agent.account.service.impl;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.CollectionOrderMapper;
import com.hd.agent.account.dao.CustomerCapitalMapper;
import com.hd.agent.account.dao.CustomerPushBalanceMapper;
import com.hd.agent.account.dao.SalesInvoiceBillMapper;
import com.hd.agent.account.dao.SalesInvoiceMapper;
import com.hd.agent.account.dao.SalesStatementMapper;
import com.hd.agent.account.model.CollectionOrder;
import com.hd.agent.account.model.CollectionOrderRelate;
import com.hd.agent.account.model.CollectionOrderSatement;
import com.hd.agent.account.model.CustomerCapital;
import com.hd.agent.account.model.CustomerCapitalLog;
import com.hd.agent.account.model.CustomerPushBalance;
import com.hd.agent.account.model.SalesInvoice;
import com.hd.agent.account.model.SalesInvoiceBill;
import com.hd.agent.account.model.SalesStatement;
import com.hd.agent.account.service.ISalesStatementService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.service.ISalesOutService;
import com.hd.agent.storage.dao.SaleRejectEnterMapper;
import com.hd.agent.storage.dao.SaleoutMapper;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 销售发票核销 对账单service实现类
 * @author chenwei
 */
public class SalesStatementServiceImpl extends BaseAccountServiceImpl implements
		ISalesStatementService{
	/**
	 * 收款单dao
	 */
	private CollectionOrderMapper collectionOrderMapper;
	/**
	 * 销售发票dao
	 */
	private SalesInvoiceMapper salesInvoiceMapper;
	
	/**
	 * 销售开票dao
	 */
	private SalesInvoiceBillMapper salesInvoiceBillMapper;
	/**
	 * 销售发票收款单核销dao
	 */
	private SalesStatementMapper salesStatementMapper;
	
	private CustomerCapitalMapper customerCapitalMapper;
	/**
	 * 客户应收款冲差单
	 */
	private CustomerPushBalanceMapper customerPushBalanceMapper;
	/**
	 * 销售模块接口service
	 */
	private ISalesOutService salesOutService;
	/**
	 * 发货单dao
	 */
	private SaleoutMapper saleoutMapper;
	/**
	 * 退货入库单dao
	 */
	private SaleRejectEnterMapper saleRejectEnterMapper;
	
	public SalesInvoiceBillMapper getSalesInvoiceBillMapper() {
		return salesInvoiceBillMapper;
	}

	public void setSalesInvoiceBillMapper(
			SalesInvoiceBillMapper salesInvoiceBillMapper) {
		this.salesInvoiceBillMapper = salesInvoiceBillMapper;
	}

	public CollectionOrderMapper getCollectionOrderMapper() {
		return collectionOrderMapper;
	}

	public void setCollectionOrderMapper(CollectionOrderMapper collectionOrderMapper) {
		this.collectionOrderMapper = collectionOrderMapper;
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
	
	public CustomerPushBalanceMapper getCustomerPushBalanceMapper() {
		return customerPushBalanceMapper;
	}

	public void setCustomerPushBalanceMapper(
			CustomerPushBalanceMapper customerPushBalanceMapper) {
		this.customerPushBalanceMapper = customerPushBalanceMapper;
	}
	
	public SaleoutMapper getSaleoutMapper() {
		return saleoutMapper;
	}

	public void setSaleoutMapper(SaleoutMapper saleoutMapper) {
		this.saleoutMapper = saleoutMapper;
	}
	
	public SaleRejectEnterMapper getSaleRejectEnterMapper() {
		return saleRejectEnterMapper;
	}

	public void setSaleRejectEnterMapper(SaleRejectEnterMapper saleRejectEnterMapper) {
		this.saleRejectEnterMapper = saleRejectEnterMapper;
	}

	@Override
	public Map auditWriteoffCollectionOrder(String customerid,List<SalesStatement> salesStatementList) throws Exception {
		CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(customerid);
		if(null==customerCapital){
			customerCapital = new CustomerCapital();
			customerCapital.setId(customerid);
			customerCapital.setAmount(BigDecimal.ZERO);
		}
		SysUser sysUser = getSysUser();
		boolean flag = false;
		Map map = new HashMap();
		if(null != customerCapital){
			BigDecimal totalWriteoffAmount = new BigDecimal(0);
			String writeoffdate = getAuditBusinessdate(CommonUtils.getTodayDataStr());

			//正数尾差
			BigDecimal positeTailAmount = BigDecimal.ZERO;
            String positeTailAmountStr = getSysParamValue("positeTailAmountLimit");
            if(StringUtils.isNotEmpty(positeTailAmountStr)){
                positeTailAmount = new BigDecimal(positeTailAmountStr);
            }
            //负数尾差
            String negateTailAmountStr = getSysParamValue("negateTailAmountLimit");
            BigDecimal negateTailAmount = BigDecimal.ZERO;
            if(StringUtils.isNotEmpty(negateTailAmountStr)){
                negateTailAmount = new BigDecimal(negateTailAmountStr);
            }

			for(SalesStatement salesStatement : salesStatementList){
				SalesInvoice salesInvoice = salesInvoiceMapper.getSalesInvoiceInfo(salesStatement.getBillid());
				if(null!=salesInvoice && "3".equals(salesInvoice.getStatus()) && "0".equals(salesInvoice.getIswriteoff())){
					BigDecimal writeoffamount = salesStatementMapper.getSalesInvoiceRelateAmount(salesStatement.getBillid());
					if(null==writeoffamount){
						writeoffamount = BigDecimal.ZERO;
					}
					BigDecimal tailamount = salesInvoice.getInvoiceamount().subtract(writeoffamount);
					//尾差金额大于等于负数尾差 小于等于正数尾差
					if(tailamount.compareTo(negateTailAmount)==-1 && tailamount.compareTo(positeTailAmount)==1){
                        throw new Exception("销售核销"+salesStatement.getBillid()+",尾差金额超出范围");
                    }
					//销售发票核销
					flag = salesInvoiceMapper.writeOffSalesInvoice(salesStatement.getBillid(), writeoffamount, tailamount,salesInvoice.getWriteoffamount(),salesInvoice.getTailamount(), writeoffdate,sysUser.getUserid(),sysUser.getName()) > 0;
					if(flag){
						//回写销售发票来源单据的数据
						writeBackSalesInvoiceSource(salesInvoice,writeoffdate);
//					//根据销售发票是否折扣 关闭相关应收款冲差单
//					closeCustomerPushBalanceByInvoice(salesInvoice);
						//总核销金额
						totalWriteoffAmount = totalWriteoffAmount.add(writeoffamount);
						//生成对账单
						salesStatement.setCustomerid(customerid);
						salesStatement.setBusinessdate(writeoffdate);
						salesStatement.setBilltype("1");
						salesStatement.setBillid(salesInvoice.getId());
						//salesStatement.setAmount(writeoffamount);
						salesStatement.setBillamount(salesInvoice.getInvoiceamount());
						salesStatement.setAdduserid(sysUser.getUserid());
						salesStatement.setAddusername(sysUser.getName());

						salesStatementMapper.addSalesStatement(salesStatement);
						//更新收款单发票关联对账单 核销日期
						salesStatementMapper.updateCollectionOrderStatementWritedateByInvoiceid(salesInvoice.getId(),writeoffdate);
						//更新客户资金情况
						salesInvoice.setWriteoffamount(writeoffamount);

						updateCustomerCapital(salesInvoice,customerid);

						//判断销售核销关联的销售开票编号是否存在
						if(StringUtils.isNotEmpty(salesInvoice.getSalesinvoicebillid())){
							//销售开票明细是否与销售核销的明细一致，一致则关闭审核通过状态的销售开票，核销状态为“已核销”，否则不操作
							boolean flag1 = checkSalesInvoiceBillDetailsSameAsSalesInvoiceDetails(salesInvoice.getSalesinvoicebillid(),salesInvoice.getId());
							if(flag1){
								SalesInvoiceBill salesInvoiceBill = salesInvoiceBillMapper.getSalesInvoiceBillInfo(salesInvoice.getSalesinvoicebillid());
								if(null != salesInvoiceBill){
									if("3".equals(salesInvoiceBill.getStatus())){
										salesInvoiceBillMapper.closeSalesInvoiceBill(salesInvoiceBill.getId());
									}
									Map map1 = new HashMap();
									map1.put("iswriteoff","1");
									map1.put("invoicebillid",salesInvoiceBill.getId());
									salesInvoiceBillMapper.updateSalesInvoiceBillBack(map1);
								}
							}
						}
					}
				}else if(null==salesInvoice ){
					CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(salesStatement.getBillid());
					if(null!=customerPushBalance){
						flag = customerPushBalanceMapper.writeoffCustomerPushBanlance(salesStatement.getBillid(),salesStatement.getWriteoffamount(),customerPushBalance.getWriteoffamount(),customerPushBalance.getTailamount(),sysUser.getUserid(),sysUser.getName(),writeoffdate) > 0;
						if(flag){
							//总核销金额
							totalWriteoffAmount = totalWriteoffAmount.add(salesStatement.getWriteoffamount());
							//生成对账单
							salesStatement.setCustomerid(customerid);
							salesStatement.setBusinessdate(writeoffdate);
							salesStatement.setBilltype("3");
							salesStatement.setBillid(customerPushBalance.getId());
							//salesStatement.setAmount(customerPushBalance.getAmount());
							salesStatement.setBillamount(customerPushBalance.getAmount());
							salesStatement.setAdduserid(sysUser.getUserid());
							salesStatement.setAddusername(sysUser.getName());

							customerPushBalance.setWriteoffamount(salesStatement.getWriteoffamount());
							customerPushBalance.setTailamount(salesStatement.getWriteoffamount().subtract(customerPushBalance.getAmount()));
							salesStatementMapper.addSalesStatement(salesStatement);

							updateCustomerCapital(customerPushBalance,customerid);

							//更新收款单发票关联对账单 核销日期
							salesStatementMapper.updateCollectionOrderStatementWritedateByInvoiceid(customerPushBalance.getId(),writeoffdate);
						}
					}
				}
			}
		}
		if(!flag){
			throw new Exception("回滚销售核销核销失败。客户："+customerid+"，销售核销编号："+salesStatementList.get(0).getBillid());
		}
		map.put("flag", flag);
		return map;
	}

	/**
	 * 发票核销后 回写销售发票来源单据的相关信息
	 * @param salesInvoice
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 11, 2013
	 */
	public boolean writeBackSalesInvoiceSource(SalesInvoice salesInvoice,String writeoffdate) throws Exception{
		if(null!=salesInvoice){
			List sourceidList = salesInvoiceMapper.getSalesInvoiceSouceidList(salesInvoice.getId());
			salesOutService.updateReceiptAndRejectBillInvoiceWriteoff(sourceidList, salesInvoice,writeoffdate);
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 回写反核销销售发票来源单据的相关信息
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 4, 2014
	 */
	public boolean writeBackUnSalesInvoiceSource(SalesInvoice salesInvoice)throws Exception{
		if(null!=salesInvoice){
			List sourceidList = salesInvoiceMapper.getSalesInvoiceSouceidList(salesInvoice.getId());
			salesOutService.updateReceiptAndRejectBillInvoiceBackWriteoff(sourceidList, salesInvoice);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 更新客户资金情况
	 * @param salesInvoice
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 12, 2013
	 */
	public boolean updateCustomerCapital(SalesInvoice salesInvoice,String customerid) throws Exception{
		CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(customerid);
		if(null==customerCapital){
			customerCapital = new CustomerCapital();
			customerCapital.setId(customerid);
			customerCapital.setAmount(BigDecimal.ZERO);
			customerCapitalMapper.addCustomerCapital(customerCapital);
		}
		SysUser sysUser = getSysUser();
		//流水明细
		CustomerCapitalLog customerCapitalLog = new CustomerCapitalLog();
		customerCapitalLog.setCustomerid(customerid);
		customerCapitalLog.setBillid(salesInvoice.getId());
		customerCapitalLog.setBilltype("2");
		customerCapitalLog.setPrtype("2");
		customerCapitalLog.setPayamount(salesInvoice.getWriteoffamount());
		customerCapitalLog.setBalanceamount(customerCapital.getAmount().subtract(salesInvoice.getWriteoffamount()));
		customerCapitalLog.setAdddeptid(sysUser.getDepartmentid());
		customerCapitalLog.setAdddeptname(sysUser.getDepartmentname());
		customerCapitalLog.setAdduserid(sysUser.getUserid());
		customerCapitalLog.setAddusername(sysUser.getName());
		if(StringUtils.isNotEmpty(salesInvoice.getRemark())){
			salesInvoice.setRemark(salesInvoice.getRemark()+",核销");
		}else{
			salesInvoice.setRemark("核销");
		}
		customerCapitalLog.setRemark(salesInvoice.getRemark());
		int i = customerCapitalMapper.addCustomerCapitalLog(customerCapitalLog);
		customerCapital.setAmount(customerCapital.getAmount().subtract(salesInvoice.getWriteoffamount()));
		int j = customerCapitalMapper.writeoffCustomerCapitalWithSalesInvoice(customerid, salesInvoice.getId());
		return i>0&&j>0;
	}
	
	/**
	 * 更新客户资金情况(申请开票)
	 * @param salesInvoiceBill
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 25, 2015
	 */
	public boolean updateCustomerCapital(SalesInvoiceBill salesInvoiceBill,String customerid) throws Exception{
		CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(customerid);
		if(null==customerCapital){
			customerCapital = new CustomerCapital();
			customerCapital.setId(customerid);
			customerCapital.setAmount(BigDecimal.ZERO);
			customerCapitalMapper.addCustomerCapital(customerCapital);
		}
		SysUser sysUser = getSysUser();
		//流水明细
		CustomerCapitalLog customerCapitalLog = new CustomerCapitalLog();
		customerCapitalLog.setCustomerid(customerid);
		customerCapitalLog.setBillid(salesInvoiceBill.getId());
		customerCapitalLog.setBilltype("5");
		customerCapitalLog.setPrtype("2");
		customerCapitalLog.setPayamount(salesInvoiceBill.getTaxamount());
		customerCapitalLog.setBalanceamount(customerCapital.getAmount().subtract(salesInvoiceBill.getTaxamount()));
		customerCapitalLog.setAdddeptid(sysUser.getDepartmentid());
		customerCapitalLog.setAdddeptname(sysUser.getDepartmentname());
		customerCapitalLog.setAdduserid(sysUser.getUserid());
		customerCapitalLog.setAddusername(sysUser.getName());
		if(StringUtils.isNotEmpty(salesInvoiceBill.getRemark())){
			salesInvoiceBill.setRemark(salesInvoiceBill.getRemark()+"，核销");
		}else{
			salesInvoiceBill.setRemark("核销");
		}
		customerCapitalLog.setRemark(salesInvoiceBill.getRemark());
		int i = customerCapitalMapper.addCustomerCapitalLog(customerCapitalLog);
		customerCapital.setAmount(customerCapital.getAmount().subtract(salesInvoiceBill.getTaxamount()));
		int j = customerCapitalMapper.writeoffCustomerCapitalWithSalesInvoiceBill(customerid, salesInvoiceBill.getId());
		return i>0&&j>0;
	}
	
	/**
	 * 根据冲差单 更新客户资金情况
	 * @param customerPushBalance
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 8, 2013
	 */
	public boolean updateCustomerCapital(CustomerPushBalance customerPushBalance,String customerid) throws Exception{
		CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(customerid);
		if(null==customerCapital){
			return false;
		}
		SysUser sysUser = getSysUser();
		//流水明细
		CustomerCapitalLog customerCapitalLog = new CustomerCapitalLog();
		customerCapitalLog.setCustomerid(customerid);
		customerCapitalLog.setBillid(customerPushBalance.getId());
		customerCapitalLog.setBilltype("4");
		customerCapitalLog.setPrtype("2");
		customerCapitalLog.setPayamount(customerPushBalance.getWriteoffamount());
		customerCapitalLog.setBalanceamount(customerCapital.getAmount().subtract(customerPushBalance.getWriteoffamount()));
		customerCapitalLog.setAdddeptid(sysUser.getDepartmentid());
		customerCapitalLog.setAdddeptname(sysUser.getDepartmentname());
		customerCapitalLog.setAdduserid(sysUser.getUserid());
		customerCapitalLog.setAddusername(sysUser.getName());
		if(StringUtils.isNotEmpty(customerPushBalance.getRemark())){
			customerPushBalance.setRemark(customerPushBalance.getRemark()+",核销");
		}else{
			customerPushBalance.setRemark("核销");
		}
		customerCapitalLog.setRemark(customerPushBalance.getRemark());
		int i = customerCapitalMapper.addCustomerCapitalLog(customerCapitalLog);
		customerCapital.setAmount(customerCapital.getAmount().subtract(customerPushBalance.getWriteoffamount()));
		int j = customerCapitalMapper.updateCustomerCapital(customerCapital);
		return i>0&&j>0;
	}
	@Override
	public List showSalesStatementList(String orderid) throws Exception {
		List list = salesStatementMapper.showSalesStatementList(orderid);
		return list;
	}

	@Override
	public PageData showSalesStatementData(PageMap pageMap) throws Exception {
		List list = salesStatementMapper.showSalesStatementListPage(pageMap);
		PageData pageData = new PageData(salesStatementMapper.showSalesStatementCount(pageMap),list,pageMap);
		return pageData;
	}

	@Override
	public boolean addRelateCollectionOrder(String billid, String billtype,
			BigDecimal amount, List<CollectionOrderRelate> detailList) throws Exception {
		for(CollectionOrderRelate collectionOrderRelate : detailList){
			CollectionOrderSatement collectionOrderSatement = new CollectionOrderSatement();
			collectionOrderSatement.setOrderid(collectionOrderRelate.getId());
			collectionOrderSatement.setBillid(billid);
			collectionOrderSatement.setBilltype(billtype);
			collectionOrderSatement.setOrderamount(collectionOrderRelate.getAmount());
			collectionOrderSatement.setInvoiceamount(amount);
			collectionOrderSatement.setRelateamount(collectionOrderRelate.getRelateamount());
			salesStatementMapper.addRelateCollectionOrder(collectionOrderSatement);
			
			CollectionOrder collectionOrder = collectionOrderMapper.getCollectionOrderInfo(collectionOrderRelate.getId());
			if(null != collectionOrder){
				BigDecimal writeoffamount = collectionOrder.getWriteoffamount().add(collectionOrderRelate.getRelateamount());
				BigDecimal remainderamount = collectionOrder.getRemainderamount().subtract(collectionOrderRelate.getRelateamount());
				String iswriteoff = "2";
				if(remainderamount.compareTo(BigDecimal.ZERO)==0){
					iswriteoff = "1";
				}else if(remainderamount.compareTo(collectionOrder.getAmount())==0){
					iswriteoff = "0";
				}
				int i = collectionOrderMapper.writeOffCollectionOrder(collectionOrder.getId(), writeoffamount, remainderamount, iswriteoff,collectionOrder.getVersion());
				if(i==0){
					throw new Exception("收款单重复关联核销");
				}
			}
		}
		if("1".equals(billtype)){
			salesInvoiceMapper.updateSalesInvoiceIsrelate(billid,"1");
		}else if("2".equals(billtype)){
			customerPushBalanceMapper.updateCustomerPushIsrelate(billid, "1");
		}
		return true;
	}
	
	@Override
	public boolean addInvoiceBillRelateCollectionOrder(String billid,
			String billtype, BigDecimal amount,
			List<CollectionOrderRelate> detailList) throws Exception {
		for(CollectionOrderRelate collectionOrderRelate : detailList){
			CollectionOrderSatement collectionOrderSatement = new CollectionOrderSatement();
			collectionOrderSatement.setOrderid(collectionOrderRelate.getId());
			collectionOrderSatement.setBillid(billid);
			collectionOrderSatement.setBilltype(billtype);
			collectionOrderSatement.setOrderamount(collectionOrderRelate.getAmount());
			collectionOrderSatement.setInvoiceamount(amount);
			collectionOrderSatement.setRelateamount(collectionOrderRelate.getRelateamount());
			salesStatementMapper.addRelateCollectionOrder(collectionOrderSatement);
			
			CollectionOrder collectionOrder = collectionOrderMapper.getCollectionOrderInfo(collectionOrderRelate.getId());
			if(null != collectionOrder){
				BigDecimal writeoffamount = collectionOrder.getWriteoffamount().add(collectionOrderRelate.getRelateamount());
				BigDecimal remainderamount = collectionOrder.getRemainderamount().subtract(collectionOrderRelate.getRelateamount());
				String iswriteoff = "2";
				if(remainderamount.compareTo(BigDecimal.ZERO)==0){
					iswriteoff = "1";
				}else if(remainderamount.compareTo(collectionOrder.getAmount())==0){
					iswriteoff = "0";
				}
				int i = collectionOrderMapper.writeOffCollectionOrder(collectionOrder.getId(), writeoffamount, remainderamount, iswriteoff,collectionOrder.getVersion());
				if(i==0){
			        throw new Exception("收款单关联核销失败");
                }
			}
		}
		if("1".equals(billtype)){
			salesInvoiceBillMapper.updateSalesInvoiceBillIsrelate(billid,"1");
		}else if("2".equals(billtype)){
			customerPushBalanceMapper.updateCustomerPushIsrelate(billid, "1");
		}
		return true;
	}

	@Override
	public boolean deleteRelateCollectionOrder(String billid, String billtype)
			throws Exception {
		//更新收款单金额信息
		List<CollectionOrderSatement> list = salesStatementMapper.getRelateCollectionOrderListByBillid(billid);
		for(CollectionOrderSatement collectionOrderSatement : list){
			CollectionOrder collectionOrder = collectionOrderMapper.getCollectionOrderInfo(collectionOrderSatement.getOrderid());
			if(null != collectionOrder){
				BigDecimal writeoffamount = collectionOrder.getWriteoffamount().subtract(collectionOrderSatement.getRelateamount());
				BigDecimal remainderamount = collectionOrder.getRemainderamount().add(collectionOrderSatement.getRelateamount());
				String iswriteoff = "2";
				if(remainderamount.compareTo(BigDecimal.ZERO)==0){
					iswriteoff = "1";
				}else if(remainderamount.compareTo(collectionOrder.getAmount())==0){
					iswriteoff = "0";
				}
				int i = collectionOrderMapper.writeOffCollectionOrder(collectionOrder.getId(), writeoffamount, remainderamount, iswriteoff,collectionOrder.getVersion());
				if(i==0){
			        throw new Exception("收款单关联核销删除失败");
                }
			}
		}
		//删除关联的收款单信息
		int i = salesStatementMapper.deleteRelateCollectionOrderListByBillid(billid);
		//更新单据上的关联状态
		if("1".equals(billtype)){
			salesInvoiceMapper.updateSalesInvoiceIsrelate(billid,"0");
		}else if("2".equals(billtype)){
			customerPushBalanceMapper.updateCustomerPushIsrelate(billid, "0");
		}
		return i>0;
	}

	@Override
	public boolean deleteInvoiceBillRelateCollectionOrder(String billid,
			String billtype) throws Exception {
		//更新收款单金额信息
		List<CollectionOrderSatement> list = salesStatementMapper.getRelateCollectionOrderListByBillid(billid);
		for(CollectionOrderSatement collectionOrderSatement : list){
			CollectionOrder collectionOrder = collectionOrderMapper.getCollectionOrderInfo(collectionOrderSatement.getOrderid());
			if(null != collectionOrder){
				BigDecimal writeoffamount = collectionOrder.getWriteoffamount().subtract(collectionOrderSatement.getRelateamount());
				BigDecimal remainderamount = collectionOrder.getRemainderamount().add(collectionOrderSatement.getRelateamount());
				String iswriteoff = "2";
				if(remainderamount.compareTo(BigDecimal.ZERO)==0){
					iswriteoff = "1";
				}else if(remainderamount.compareTo(collectionOrder.getAmount())==0){
					iswriteoff = "0";
				}
				int i = collectionOrderMapper.writeOffCollectionOrder(collectionOrder.getId(), writeoffamount, remainderamount, iswriteoff,collectionOrder.getVersion());
				if(i==0){
			        throw new Exception("收款单关联核销删除失败");
                }
			}
		}
		//删除关联的收款单信息
		int i = salesStatementMapper.deleteRelateCollectionOrderListByBillid(billid);
		//更新单据上的关联状态
		if("1".equals(billtype)){
			salesInvoiceBillMapper.updateSalesInvoiceBillIsrelate(billid,"0");
		}else if("2".equals(billtype)){
			customerPushBalanceMapper.updateCustomerPushIsrelate(billid, "0");
		}
		return i>0;
	}

	@Override
	public boolean deleteRelateCollectionOrderInvoicebill(String billid,
			String billtype) throws Exception {
		//更新收款单金额信息
		List<CollectionOrderSatement> list = salesStatementMapper.getRelateCollectionOrderListByBillid(billid);
		for(CollectionOrderSatement collectionOrderSatement : list){
			CollectionOrder collectionOrder = collectionOrderMapper.getCollectionOrderInfo(collectionOrderSatement.getOrderid());
			if(null != collectionOrder){
				BigDecimal writeoffamount = collectionOrder.getWriteoffamount().subtract(collectionOrderSatement.getRelateamount());
				BigDecimal remainderamount = collectionOrder.getRemainderamount().add(collectionOrderSatement.getRelateamount());
				String iswriteoff = "2";
				if(remainderamount.compareTo(BigDecimal.ZERO)==0){
					iswriteoff = "1";
				}else if(remainderamount.compareTo(collectionOrder.getAmount())==0){
					iswriteoff = "0";
				}
				int i = collectionOrderMapper.writeOffCollectionOrder(collectionOrder.getId(), writeoffamount, remainderamount, iswriteoff,collectionOrder.getVersion());
				if(i==0){
			        throw new Exception("收款单关联核销删除失败");
                }
			}
		}
		//删除关联的收款单信息
		int i = salesStatementMapper.deleteRelateCollectionOrderListByBillid(billid);
		//更新单据上的关联状态
		if("1".equals(billtype)){
			salesInvoiceBillMapper.updateSalesInvoiceBillIsrelate(billid,"0");
		}else if("2".equals(billtype)){
			customerPushBalanceMapper.updateCustomerPushIsrelate(billid, "0");
		}
		return i>0;
	}

	@Override
	public List showRelateCollectionList(String billid) throws Exception {
		List<CollectionOrderSatement> list = salesStatementMapper.getRelateCollectionOrderListByBillid(billid);
		return list;
	}

	@Override
	public boolean cancelBackSalesInvoice(String invoiceid) throws Exception {
		boolean flag = false;
		SalesInvoice salesInvoice = getSalesInvoiceMapper().getSalesInvoiceInfo(invoiceid);
		if(null != salesInvoice && "4".equals(salesInvoice.getStatus())){
			//核销金额
			BigDecimal writeoffamount = salesInvoice.getWriteoffamount();
			//尾差金额
			BigDecimal tailamount = salesInvoice.getTailamount();
			//删除核销生成的对账单
			salesStatementMapper.deleteSalesStatement(salesInvoice.getCustomerid(), salesInvoice.getId(), "1");
			//回写销售发票 
			salesInvoiceMapper.unWriteOffSalesInvoice(invoiceid);
			//回写销售发票来源单据
			writeBackUnSalesInvoiceSource(salesInvoice);
			//取消关联收款单
			deleteRelateCollectionOrder(invoiceid, "1");
			//更新客户资金情况
			backCustomerCapital(salesInvoice,writeoffamount);
			//判断销售核销关联的销售开票编号是否存在
			if(StringUtils.isNotEmpty(salesInvoice.getSalesinvoicebillid())){
				//销售开票明细是否与销售核销的明细一致，一致则将关闭状态的销售开票回写为“审核通过”，核销状态为“未核销”，否则不操作
				boolean flag1 = checkSalesInvoiceBillDetailsSameAsSalesInvoiceDetails(salesInvoice.getSalesinvoicebillid(),salesInvoice.getId());
				if(flag1){
					SalesInvoiceBill salesInvoiceBill = salesInvoiceBillMapper.getSalesInvoiceBillInfo(salesInvoice.getSalesinvoicebillid());
					if(null != salesInvoiceBill){
						Map map1 = new HashMap();
						if("4".equals(salesInvoiceBill.getStatus())){
							map1.put("status","3");
						}
						map1.put("iswriteoff","0");
						map1.put("invoicebillid",salesInvoiceBill.getId());
						salesInvoiceBillMapper.updateSalesInvoiceBillBack(map1);
					}
				}
			}
			flag = true;
		}
		return flag;
	}

	/**
	 * 回写反核销后客户资金情况
	 * @param salesInvoice
	 * @param backWriteoffAmount
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 5, 2014
	 */
	protected boolean backCustomerCapital(SalesInvoice salesInvoice,BigDecimal backWriteoffAmount)throws Exception {
		CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(salesInvoice.getCustomerid());
		if(null==customerCapital){
			customerCapital = new CustomerCapital();
			customerCapital.setId(salesInvoice.getInvoicecustomerid());
			customerCapital.setAmount(BigDecimal.ZERO);
			customerCapitalMapper.addCustomerCapital(customerCapital);
		}
		SysUser sysUser = getSysUser();
		//流水明细
		CustomerCapitalLog customerCapitalLog = new CustomerCapitalLog();
		customerCapitalLog.setCustomerid(salesInvoice.getCustomerid());
		customerCapitalLog.setBillid(salesInvoice.getId());
		customerCapitalLog.setBilltype("2");
		customerCapitalLog.setPrtype("2");
		customerCapitalLog.setPayamount(backWriteoffAmount.negate());
		customerCapitalLog.setBalanceamount(customerCapital.getAmount().add(backWriteoffAmount));
		customerCapitalLog.setAdddeptid(sysUser.getDepartmentid());
		customerCapitalLog.setAdddeptname(sysUser.getDepartmentname());
		customerCapitalLog.setAdduserid(sysUser.getUserid());
		customerCapitalLog.setAddusername(sysUser.getName());
		if(StringUtils.isNotEmpty(salesInvoice.getRemark())){
			salesInvoice.setRemark(salesInvoice.getRemark() + ",反核销");
		}else{
			salesInvoice.setRemark("反核销");
		}
		customerCapitalLog.setRemark(salesInvoice.getRemark());
		int i = customerCapitalMapper.addCustomerCapitalLog(customerCapitalLog);
		//客户资金余额
		customerCapital.setAmount(customerCapital.getAmount().add(backWriteoffAmount));
		int j = customerCapitalMapper.oppWriteoffCustomerCapitalWithSalesInvoice(salesInvoice.getCustomerid(), backWriteoffAmount);
		return i>0&&j>0;
	}
}

