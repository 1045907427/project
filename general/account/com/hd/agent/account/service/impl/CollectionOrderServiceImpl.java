/**
 * @(#)CollectionOrderServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 6, 2013 chenwei 创建版本
 */
package com.hd.agent.account.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.*;
import com.hd.agent.account.model.*;
import com.hd.agent.account.service.ICollectionOrderService;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.SysCode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 
 * 收款单service实现类
 * @author chenwei
 */
public class CollectionOrderServiceImpl extends BaseAccountServiceImpl implements ICollectionOrderService {
	
	private CollectionOrderMapper collectionOrderMapper;
	
	private CustomerCapitalMapper customerCapitalMapper;
	
	private SalesStatementMapper salesStatementMapper;
	
	private SalesInvoiceMapper salesInvoiceMapper;
	
	private CustomerPushBalanceMapper customerPushBalanceMapper;
	
	public CollectionOrderMapper getCollectionOrderMapper() {
		return collectionOrderMapper;
	}

	public void setCollectionOrderMapper(CollectionOrderMapper collectionOrderMapper) {
		this.collectionOrderMapper = collectionOrderMapper;
	}
	
	public CustomerCapitalMapper getCustomerCapitalMapper() {
		return customerCapitalMapper;
	}

	public void setCustomerCapitalMapper(CustomerCapitalMapper customerCapitalMapper) {
		this.customerCapitalMapper = customerCapitalMapper;
	}
	
	public SalesStatementMapper getSalesStatementMapper() {
		return salesStatementMapper;
	}

	public void setSalesStatementMapper(SalesStatementMapper salesStatementMapper) {
		this.salesStatementMapper = salesStatementMapper;
	}
	
	public SalesInvoiceMapper getSalesInvoiceMapper() {
		return salesInvoiceMapper;
	}

	public void setSalesInvoiceMapper(SalesInvoiceMapper salesInvoiceMapper) {
		this.salesInvoiceMapper = salesInvoiceMapper;
	}

	public CustomerPushBalanceMapper getCustomerPushBalanceMapper() {
		return customerPushBalanceMapper;
	}

	public void setCustomerPushBalanceMapper(
			CustomerPushBalanceMapper customerPushBalanceMapper) {
		this.customerPushBalanceMapper = customerPushBalanceMapper;
	}

	@Override
	public boolean addCollectionOrder(CollectionOrder collectionOrder)
			throws Exception {
		if (isAutoCreate("t_account_collection_order")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(collectionOrder, "t_account_collection_order");
			collectionOrder.setId(id);
		}else{
			collectionOrder.setId("SKD-"+CommonUtils.getDataNumberSendsWithRand());
		} 
		SysUser sysUser = getSysUser();
		collectionOrder.setAdddeptid(sysUser.getDepartmentid());
		collectionOrder.setAdddeptname(sysUser.getDepartmentname());
		collectionOrder.setAdduserid(sysUser.getUserid());
		collectionOrder.setAddusername(sysUser.getName());
		collectionOrder.setRemainderamount(collectionOrder.getAmount());
		collectionOrder.setInitamount(collectionOrder.getAmount());
		//判断收款单是否指定了客户 1是0否
		if(null==collectionOrder.getCustomerid() || "".equals(collectionOrder.getCustomerid())){
			collectionOrder.setIscustomer("0");
		}
		int i = collectionOrderMapper.addCollectionOrder(collectionOrder);
		return i>0;
	}

	@Override
	public Map addCollectionOrderSaveAudit(CollectionOrder collectionOrder)
			throws Exception {
		Map  retmap = new HashMap();
		boolean addflag = addCollectionOrder(collectionOrder);
		if(addflag){
			Map map = auditCollectionOrder(collectionOrder.getId(),false);
			if(map.get("flag").equals(true)){
				retmap.put("auditflag", true);
                retmap.put("collectionOrderId",collectionOrder.getId());
			}else{
				retmap.put("auditflag", false);
				retmap.putAll(map);
			}
			retmap.put("addflag", true);
		}else{
			retmap.put("addflag", false);
		}
		return retmap;
	}

	@Override
	public CollectionOrder getCollectionOrderInfo(String id) throws Exception {
		CollectionOrder collectionOrder = collectionOrderMapper.getCollectionOrderInfo(id);
		if(null!=collectionOrder){
			Customer customer = getCustomerByID(collectionOrder.getCustomerid());
			if(null!=customer){
				collectionOrder.setCustomername(customer.getName());
			}
			return collectionOrder;
		}else{
			return null;
		}
	}

	@Override
	public PageData showCollectionOrderList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_account_collection_order", "t");
		pageMap.setDataSql(dataSql);
		List<CollectionOrder> list = collectionOrderMapper.showCollectionOrderList(pageMap);
		for(CollectionOrder collectionOrder : list){
			if(StringUtils.isNotEmpty(collectionOrder.getCollectiondept())) {
				DepartMent departMent = getDepartmentByDeptid(collectionOrder.getCollectiondept());
				if (null != departMent) {
					collectionOrder.setCollectiondeptname(departMent.getName());
				}
			}
			if(StringUtils.isNotEmpty(collectionOrder.getCollectionuser())) {
				Personnel personnel = getPersonnelById(collectionOrder.getCollectionuser());
				if (null != personnel) {
					collectionOrder.setCollectionusername(personnel.getName());
				}
			}
			if(StringUtils.isNotEmpty(collectionOrder.getCustomerid())) {
				Customer customer = getCustomerByID(collectionOrder.getCustomerid());
				if (null != customer) {
					collectionOrder.setCustomername(customer.getName());
					DepartMent saleDept = getDepartmentByDeptid(customer.getSalesdeptid());
					if (null != saleDept) {
						collectionOrder.setSalesdeptname(saleDept.getName());
					}
				}
			}
			if(StringUtils.isNotEmpty(collectionOrder.getHandlerid())) {
				Contacter contacter = getContacterById(collectionOrder.getHandlerid());
				if (null != contacter) {
					collectionOrder.setHandlername(contacter.getName());
				}
			}
			if(StringUtils.isNotEmpty(collectionOrder.getBank())) {
				Bank bank = getBankInfoByID(collectionOrder.getBank());
				if (null != bank) {
					collectionOrder.setBankname(bank.getName());
				}
			}
			if(StringUtils.isNotEmpty(collectionOrder.getBankdeptid())) {
				DepartMent departMent2 = getDepartMentById(collectionOrder.getBankdeptid());
				if (null != departMent2) {
					collectionOrder.setBankdeptname(departMent2.getName());
				}
			}
			if("2".equals(collectionOrder.getStatus())){
				collectionOrder.setStatusname("保存");
			}else if("3".equals(collectionOrder.getStatus())){
				collectionOrder.setStatusname("审核通过");
			}else if("4".equals(collectionOrder.getStatus())){
				collectionOrder.setStatusname("关闭");
			}
		}
		PageData pageData = new PageData(collectionOrderMapper.showCollectionOrderCount(pageMap),list,pageMap);
		CollectionOrder collectionOrder = collectionOrderMapper.showCollectionOrderSumList(pageMap);
		List footer = new ArrayList();
		if(null!=collectionOrder){
			collectionOrder.setBusinessdate("合计");
			collectionOrder.setBankname("");
			footer.add(collectionOrder);
		}
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public boolean deleteCollectionOrder(String id) throws Exception {
		CollectionOrder collectionOrder = collectionOrderMapper.getCollectionOrderInfo(id);
		boolean flag = false;
		if("1".equals(collectionOrder.getStatus()) || "2".equals(collectionOrder.getStatus())){
			int i = collectionOrderMapper.deleteCollectionOrder(id);
			flag = i>0;
		}
		return flag;
	}

	@Override
	public boolean editCollectionOrder(CollectionOrder collectionOrder)
			throws Exception {
		CollectionOrder order = collectionOrderMapper.getCollectionOrderInfo(collectionOrder.getId());
		if("1".equals(order.getStatus()) || "2".equals(order.getStatus()) || "6".equals(order.getStatus())
				||"".equals(order.getCustomerid())){
			SysUser sysUser = getSysUser();
			collectionOrder.setModifyuserid(sysUser.getUserid());
			collectionOrder.setModifyusername(sysUser.getName());
			collectionOrder.setInitamount(collectionOrder.getAmount());
			collectionOrder.setRemainderamount(collectionOrder.getAmount());
			//判断收款单是否指定了客户 1是0否
			if(null==collectionOrder.getCustomerid() || "".equals(collectionOrder.getCustomerid())){
				collectionOrder.setIscustomer("0");
			}
			int i = collectionOrderMapper.editCollectionOrder(collectionOrder);
			return i>0;
		}else if("3".equals(order.getStatus()) || "4".equals(order.getStatus())){
			SysUser sysUser = getSysUser();
			
			CollectionOrder newCollectionOrder = new CollectionOrder();
			newCollectionOrder.setId(collectionOrder.getId());
//			newCollectionOrder.setBusinessdate(collectionOrder.getBusinessdate());
			newCollectionOrder.setBank(collectionOrder.getBank());
			newCollectionOrder.setBankdeptid(collectionOrder.getBankdeptid());
			newCollectionOrder.setModifyuserid(sysUser.getUserid());
			newCollectionOrder.setModifyusername(sysUser.getName());
			int i = collectionOrderMapper.editCollectionOrder(newCollectionOrder);
			if(i >0){
				//添加修改后银行的借贷单
				BankAmountOthers bankAmountOthers = new BankAmountOthers();
				bankAmountOthers.setBankid(collectionOrder.getBank());
				bankAmountOthers.setDeptid(collectionOrder.getBankdeptid());
				bankAmountOthers.setBillid(collectionOrder.getId());
				bankAmountOthers.setBilltype("1");
				bankAmountOthers.setLendtype("1");
				bankAmountOthers.setAmount(collectionOrder.getAmount());
				if(StringUtils.isEmpty(collectionOrder.getRemark())){
					bankAmountOthers.setRemark("收款单："+collectionOrder.getId());
				}else{
					bankAmountOthers.setRemark(collectionOrder.getRemark()+" 收款单："+collectionOrder.getId());
				}
				bankAmountOthers.setBusinessdate(CommonUtils.getTodayDataStr());
				addAndAuditBankAmountOthers(bankAmountOthers);

				//添加修改前银行的反向负的借贷单
				BankAmountOthers oppBankAmountOthers = new BankAmountOthers();
				oppBankAmountOthers.setBankid(order.getBank());
				oppBankAmountOthers.setDeptid(order.getBankdeptid());
				oppBankAmountOthers.setBillid(order.getId());
				oppBankAmountOthers.setBilltype("1");
				oppBankAmountOthers.setLendtype("2");
				oppBankAmountOthers.setAmount(collectionOrder.getAmount());
				if(StringUtils.isEmpty(collectionOrder.getRemark())){
					oppBankAmountOthers.setRemark("收款单："+collectionOrder.getId());
				}else{
					oppBankAmountOthers.setRemark(collectionOrder.getRemark()+" 收款单："+collectionOrder.getId());
				}
				oppBankAmountOthers.setBusinessdate(CommonUtils.getTodayDataStr());
				addAndAuditBankAmountOthers(oppBankAmountOthers);

			}
			return i>0;
		}else{
			return false;
		}
	}

	@Override
	public Map auditCollectionOrder(String id,boolean issuper) throws Exception {
		Map map = new HashMap();
		CollectionOrder collectionOrder = collectionOrderMapper.getCollectionOrderInfo(id);
		SysUser sysUser = getSysUser();
		boolean flag = false;
		String msg = "";
		if(null!=collectionOrder){
			if(collectionOrder.getAmount().compareTo(BigDecimal.ZERO)!=-1 || issuper){
				if(("2".equals(collectionOrder.getStatus()) || "6".equals(collectionOrder.getStatus()))){
					CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(collectionOrder.getCustomerid());
					if(null!=customerCapital){
						customerCapital.setAmount(customerCapital.getAmount().add(collectionOrder.getAmount()));
						//更新客户余额
						customerCapitalMapper.updateCustomerCapitalAmont(customerCapital.getId(),collectionOrder.getAmount());
					}else{
						customerCapital = new CustomerCapital();
						customerCapital.setId(collectionOrder.getCustomerid());
						customerCapital.setAmount(collectionOrder.getAmount());
						customerCapitalMapper.addCustomerCapital(customerCapital);
					}
					//更新银行账户金额 添加借贷单
					BankAmountOthers bankAmountOthers = new BankAmountOthers();
					bankAmountOthers.setBankid(collectionOrder.getBank());
					bankAmountOthers.setDeptid(collectionOrder.getBankdeptid());
					bankAmountOthers.setBillid(collectionOrder.getId());
					bankAmountOthers.setBilltype("1");
					bankAmountOthers.setLendtype("1");
					bankAmountOthers.setAmount(collectionOrder.getAmount());
                    if(StringUtils.isEmpty(collectionOrder.getRemark())){
                        bankAmountOthers.setRemark("收款单："+collectionOrder.getId());
                    }else{
                        bankAmountOthers.setRemark(collectionOrder.getRemark()+" 收款单："+collectionOrder.getId());
                    }
					bankAmountOthers.setBusinessdate(getAuditBusinessdate(collectionOrder.getBusinessdate()));
					addAndAuditBankAmountOthers(bankAmountOthers);
					
					//流水明细
					CustomerCapitalLog customerCapitalLog = new CustomerCapitalLog();
					customerCapitalLog.setCustomerid(collectionOrder.getCustomerid());
					customerCapitalLog.setBillid(id);
					customerCapitalLog.setBilltype("1");
					customerCapitalLog.setPrtype("1");
					customerCapitalLog.setIncomeamount(collectionOrder.getAmount());
					customerCapitalLog.setBalanceamount(customerCapital.getAmount());
					customerCapitalLog.setAdddeptid(sysUser.getDepartmentid());
					customerCapitalLog.setAdddeptname(sysUser.getDepartmentname());
					customerCapitalLog.setAdduserid(sysUser.getUserid());
					customerCapitalLog.setAddusername(sysUser.getName());
					if(StringUtils.isNotEmpty(collectionOrder.getRemark())){
						collectionOrder.setRemark(collectionOrder.getRemark()+",收款单审核");
					}else {
						collectionOrder.setRemark("收款单审核");
					}
					customerCapitalLog.setRemark(collectionOrder.getRemark());
					customerCapitalMapper.addCustomerCapitalLog(customerCapitalLog);
					
					//生成客户对账单
					SalesStatement salesStatement = new SalesStatement();
					salesStatement.setCustomerid(collectionOrder.getCustomerid());
					salesStatement.setBusinessdate(collectionOrder.getBusinessdate());
					salesStatement.setBilltype("4");
					salesStatement.setBillid(collectionOrder.getId());
					salesStatement.setAmount(collectionOrder.getAmount());
					salesStatement.setBillamount(collectionOrder.getAmount());
					salesStatement.setRemark(collectionOrder.getRemark());
					salesStatement.setAdduserid(sysUser.getUserid());
					salesStatement.setAddusername(sysUser.getName());
					salesStatementMapper.addSalesStatement(salesStatement);
//					//当收款单指定客户后 审核通过后 自动关闭
//					//未指定客户 收款单处于审核通过状态
//					if(null!=collectionOrder.getCustomerid() && !"".equals(collectionOrder.getCustomerid())){
//						int i = collectionOrderMapper.auditCollectionOrder(id,"3", sysUser.getUserid(), sysUser.getName());
//						flag = i>0;
//					}else{
//						int i = collectionOrderMapper.auditCollectionOrder(id,"3", sysUser.getUserid(), sysUser.getName());
//						flag = i>0;
//					}
					String billdate=getAuditBusinessdate(collectionOrder.getBusinessdate());
					int i = collectionOrderMapper.auditCollectionOrder(id,"3", sysUser.getUserid(), sysUser.getName(),collectionOrder.getVersion(),billdate);
					flag = i>0;
					if(!flag){
						throw new Exception("收款单"+id+"审核失败，回滚数据");
					}
				}else{
					msg = "收款单+"+id+"已审核";
				}
			}else{
				msg = "收款单"+id+"金额小于0,需要超级审核";
			}
		}else{
			msg = "未找到收款单"+id;
		}
		
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}

	@Override
	public Map oppauditCollectionOrder(String id) throws Exception {
		CollectionOrder collectionOrder = collectionOrderMapper.getCollectionOrderInfo(id);
		boolean flag = false;
		String msg = "";
		if(null!=collectionOrder && "3".equals(collectionOrder.getStatus()) && "0".equals(collectionOrder.getIswriteoff())){
			if((null==collectionOrder.getCustomerid() || "".equals(collectionOrder.getCustomerid()))
					&&collectionOrder.getInitamount().compareTo(collectionOrder.getAmount())!=0){
				Map map = new HashMap();
				map.put("flag", false);
				map.put("msg", "收款单:"+collectionOrder.getId()+",已经分配金额");
				return map;
			}
			CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(collectionOrder.getCustomerid());
            SysUser sysUser = getSysUser();
			//回滚收款单金额
			if(null!=customerCapital){
				customerCapital.setAmount(customerCapital.getAmount().subtract(collectionOrder.getAmount()));
				//更新客户余额
				customerCapitalMapper.updateCustomerCapitalAmont(customerCapital.getId(),collectionOrder.getAmount().negate());
//				customerCapitalMapper.deleteCustomerCapitalLog(collectionOrder.getCustomerid(), collectionOrder.getId(), "1");
                CustomerCapitalLog customerCapitalLog = new CustomerCapitalLog();
                customerCapitalLog.setCustomerid(customerCapital.getId());
                customerCapitalLog.setBillid(collectionOrder.getId());
                customerCapitalLog.setBilltype("1");
                customerCapitalLog.setPrtype("1");
                customerCapitalLog.setIncomeamount(collectionOrder.getAmount().multiply(new BigDecimal(-1)));
                customerCapitalLog.setBalanceamount(customerCapital.getAmount());
                customerCapitalLog.setAdddeptid(sysUser.getDepartmentid());
                customerCapitalLog.setAdddeptname(sysUser.getDepartmentname());
                customerCapitalLog.setAdduserid(sysUser.getUserid());
                customerCapitalLog.setAddusername(sysUser.getName());
				if(StringUtils.isNotEmpty(collectionOrder.getRemark())){
					collectionOrder.setRemark(collectionOrder.getRemark()+",收款单反审");
				}else {
					collectionOrder.setRemark("收款单反审");
				}
				customerCapitalLog.setRemark(collectionOrder.getRemark());
                customerCapitalMapper.addCustomerCapitalLog(customerCapitalLog);
			}

			//更新银行账户金额 添加借贷单
			BankAmountOthers bankAmountOthers = new BankAmountOthers();
			bankAmountOthers.setBankid(collectionOrder.getBank());
			bankAmountOthers.setDeptid(collectionOrder.getBankdeptid());
			bankAmountOthers.setBillid(collectionOrder.getId());
			bankAmountOthers.setBilltype("1");
			bankAmountOthers.setLendtype("2");
			bankAmountOthers.setAmount(collectionOrder.getAmount());
			bankAmountOthers.setRemark("反审收款单："+collectionOrder.getId());
			bankAmountOthers.setBusinessdate(CommonUtils.getTodayDataStr());
			addAndAuditBankAmountOthers(bankAmountOthers);
			
			int i = collectionOrderMapper.oppauditCollectionOrder(id, sysUser.getUserid(), sysUser.getName(),collectionOrder.getVersion());
			if(i==0){
			    throw new Exception("收款单反审失败");
            }
			flag = i>0;
		}else {
			msg = "收款单正在核销中，或者收款单不是审核通过状态，不能反审";
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}

	@Override
	public PageData showCustomerAccountList(PageMap pageMap)
			throws Exception {
		String dataSql = getDataAccessRule("t_base_sales_customer", "t");
		pageMap.getCondition().put("datasql", dataSql);
		pageMap.setDataSql(dataSql);
		//设置通用查询别名
		pageMap.setQueryAlias("t");
		//判断是否品牌业务员
		String brandUserRoleName = getSysParamValue("BrandUserRoleName");
		SysUser sysUser = getSysUser();
		boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
		if(isBrandUser){
			pageMap.getCondition().put("isBrandUser", true);
			pageMap.getCondition().put("personnelid", sysUser.getPersonnelid());
		}
		List<Customer> list = customerCapitalMapper.showCustomerAccountList(pageMap);
		for(Customer customer : list){
			SalesArea salesArea = getSalesareaByID(customer.getSalesarea());
			if(null!=salesArea){
				customer.setSalesareaname(salesArea.getName());
			}
			Personnel personnel = getPersonnelById(customer.getSalesuserid());
			if(null!=personnel){
				customer.setSalesusername(personnel.getName());
			}
		}
		PageData pageData = new PageData(customerCapitalMapper.showCustomerAccountCount(pageMap),list,pageMap);
		List<Customer> footer = new ArrayList<Customer>();
		BigDecimal totalamount = customerCapitalMapper.showCustomerAccountSum(pageMap);
		Customer customerSum = new Customer();
		if(null != totalamount){
			customerSum.setName("合计");
			customerSum.setAmount(totalamount);
		}
		footer.add(customerSum);
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public PageData showCustomerCapitalLogList(PageMap pageMap)
			throws Exception {
		List list = customerCapitalMapper.showCustomerCapitalLogList(pageMap);
		PageData pageData = new PageData(customerCapitalMapper.showCustomerCapitalLogCount(pageMap),list,pageMap);
		return pageData;
	}

	@Override
	public List getCollectionOrderListByIds(String ids) throws Exception {
		List list = new ArrayList();
		if(null!=ids && !"".equals(ids)){
			String[] idArr = ids.split(",");
			for(String id : idArr){
				CollectionOrder collectionOrder = collectionOrderMapper.getCollectionOrderInfo(id);
				if(null != collectionOrder){
                    Bank bank = getBankInfoByID(collectionOrder.getBank());
                    if(null!=bank && null!=collectionOrder){
                        collectionOrder.setBankname(bank.getName());
                    }
                    list.add(collectionOrder);
                }
			}
		}
		return list;
	}
	@Override
	public List getCollectionOrderListByCustomerid(String customerid)
			throws Exception {
		List list = collectionOrderMapper.getCollectionOrderListByCustomerid(customerid);
		return list;
	}
	
	@Override
	public boolean setCollectionOrderMerge(String ids, String orderid,String remark)
			throws Exception {
		boolean flag = false;
		if(null!=ids && !"".equals(ids)){
			String[] idArr = ids.split(",");
			//合并过来的余额
			BigDecimal addRemainderamount = new BigDecimal(0);
			SysUser sysUser = getSysUser();
			CollectionOrder collectionOrderInit = collectionOrderMapper.getCollectionOrderInfo(orderid);
			Customer customer = getCustomerByID(collectionOrderInit.getCustomerid());
			for(String id : idArr){
				CollectionOrder collectionOrder = collectionOrderMapper.getCollectionOrderInfo(id);
				if(!id.equals(orderid)){
					CollectionOrderSatement collectionOrderSatement = new CollectionOrderSatement();
					collectionOrderSatement.setOrderid(collectionOrder.getId());
					collectionOrderSatement.setBillid(collectionOrder.getId());
					collectionOrderSatement.setBilltype("3");
					collectionOrderSatement.setCustomerid(collectionOrder.getCustomerid());
					collectionOrderSatement.setCustomername(customer.getName());
					collectionOrderSatement.setOrderamount(collectionOrder.getAmount());
					collectionOrderSatement.setInvoiceamount(collectionOrder.getRemainderamount());
					collectionOrderSatement.setWriteoffamount(collectionOrder.getWriteoffamount().add(collectionOrder.getRemainderamount()));
					collectionOrderSatement.setWriteoffdate(CommonUtils.getTodayDataStr());
					collectionOrderSatement.setIswrieteoff("1");
					collectionOrderSatement.setRelateamount(collectionOrder.getRemainderamount());
					salesStatementMapper.addRelateCollectionOrder(collectionOrderSatement);
					
					CollectionOrderSatement collectionOrderSatement1 = new CollectionOrderSatement();
					collectionOrderSatement1.setOrderid(orderid);
					collectionOrderSatement1.setBillid(collectionOrder.getId());
					collectionOrderSatement1.setBilltype("3");
					collectionOrderSatement1.setCustomerid(collectionOrder.getCustomerid());
					collectionOrderSatement1.setCustomername(customer.getName());
					collectionOrderSatement1.setOrderamount(collectionOrder.getAmount());
					collectionOrderSatement1.setInvoiceamount(collectionOrder.getRemainderamount().negate());
					collectionOrderSatement1.setRelateamount(collectionOrder.getRemainderamount().negate());
					collectionOrderSatement1.setWriteoffamount(collectionOrderInit.getWriteoffamount().subtract(collectionOrder.getRemainderamount()));
					collectionOrderSatement1.setWriteoffdate(CommonUtils.getTodayDataStr());
					collectionOrderSatement1.setIswrieteoff("1");
					salesStatementMapper.addRelateCollectionOrder(collectionOrderSatement1);
					//合并关闭
					int i = collectionOrderMapper.writeOffCollectionOrder(id, collectionOrder.getWriteoffamount().add(collectionOrder.getRemainderamount()), new BigDecimal(0), "1", collectionOrder.getVersion());
					if(i>0){
						addRemainderamount = addRemainderamount.add(collectionOrder.getRemainderamount());
					}else{
						throw new Exception("收款单合并失败");
					}
				}
			}
			BigDecimal writeoffamount = collectionOrderInit.getWriteoffamount().subtract(addRemainderamount); 
			BigDecimal remainderamount = collectionOrderInit.getRemainderamount().add(addRemainderamount);
			if(remainderamount.compareTo(BigDecimal.ZERO)!=0){
				int i = collectionOrderMapper.writeOffCollectionOrder(orderid, writeoffamount, remainderamount, "2", collectionOrderInit.getVersion());
				flag = i>0;
			}else{
				int i = collectionOrderMapper.writeOffCollectionOrder(orderid, writeoffamount, remainderamount, "1", collectionOrderInit.getVersion());
				flag = i>0;
			}
			if(!flag){
				throw new Exception("收款单合并失败");
			}
		}

		return flag;
	}

	@Override
	public boolean setCollectionOrderTransfer(TransferOrder transferOrder)
			throws Exception {
		boolean flag = false;
		SysUser sysUser = getSysUser();
		CollectionOrder collectionOrder = collectionOrderMapper.getCollectionOrderInfo(transferOrder.getOutorderid());
		Customer customerOut = getCustomerByID(transferOrder.getOutcustomerid());
		Customer customerIn = getCustomerByID(transferOrder.getIncustomerid());
		if(null!=collectionOrder){
			//根据转账金额 转出客户生成负的收款单 转入客户生成正的收款单
			//负的收款单
			CollectionOrder collectionOrderOut = new CollectionOrder();
			if (isAutoCreate("t_account_collection_order")) {
				// 获取自动编号
				String id = getAutoCreateSysNumbderForeign(collectionOrder, "t_account_collection_order");
				collectionOrderOut.setId(id);
			}else{
				collectionOrderOut.setId("SKD-"+CommonUtils.getDataNumberSendsWithRand());
			} 
			collectionOrderOut.setCustomerid(collectionOrder.getCustomerid());
			collectionOrderOut.setBank(collectionOrder.getBank());
			collectionOrderOut.setHandlerid(collectionOrder.getHandlerid());
			collectionOrderOut.setBusinessdate(CommonUtils.getTodayDataStr());
			collectionOrderOut.setAdddeptid(sysUser.getDepartmentid());
			collectionOrderOut.setAdddeptname(sysUser.getDepartmentname());
			collectionOrderOut.setAdduserid(sysUser.getUserid());
			collectionOrderOut.setAddusername(sysUser.getName());
			collectionOrderOut.setAmount(transferOrder.getTransferamount().negate());
			collectionOrderOut.setRemainderamount(transferOrder.getTransferamount().negate());
			collectionOrderOut.setInitamount(transferOrder.getTransferamount().negate());
			collectionOrderOut.setIscustomer("1");
			collectionOrderOut.setStatus("2");
			collectionOrderOut.setRemark("转账给客户:"+transferOrder.getIncustomerid()+customerIn.getName());
			collectionOrderMapper.addCollectionOrder(collectionOrderOut);
			Map outMap = auditCollectionOrder(collectionOrderOut.getId(), true);
			setCollectionOrderMerge(collectionOrder.getId()+","+collectionOrderOut.getId(), collectionOrder.getId(), "转账合并："+collectionOrder.getId()+","+collectionOrderOut.getId());
			//正的收款单
			CollectionOrder collectionOrderIn = new CollectionOrder();
			if (isAutoCreate("t_account_collection_order")) {
				// 获取自动编号
				String id = getAutoCreateSysNumbderForeign(collectionOrder, "t_account_collection_order");
				collectionOrderIn.setId(id);
			}else{
				collectionOrderIn.setId("SKD-"+CommonUtils.getDataNumberSendsWithRand());
			} 
			collectionOrderIn.setCustomerid(customerIn.getId());
			collectionOrderIn.setBank(collectionOrder.getBank());
			collectionOrderIn.setHandlerid(customerIn.getSalesuserid());
			collectionOrderIn.setBusinessdate(CommonUtils.getTodayDataStr());
			collectionOrderIn.setAdddeptid(sysUser.getDepartmentid());
			collectionOrderIn.setAdddeptname(sysUser.getDepartmentname());
			collectionOrderIn.setAdduserid(sysUser.getUserid());
			collectionOrderIn.setAddusername(sysUser.getName());
			collectionOrderIn.setAmount(transferOrder.getTransferamount());
			collectionOrderIn.setRemainderamount(transferOrder.getTransferamount());
			collectionOrderIn.setInitamount(transferOrder.getTransferamount());
			collectionOrderIn.setIscustomer("1");
			collectionOrderIn.setStatus("2");
			collectionOrderIn.setRemark("从客户:"+transferOrder.getOutcustomerid()+customerOut.getName()+",转账过来");
			collectionOrderMapper.addCollectionOrder(collectionOrderIn);
			Map inMap = auditCollectionOrder(collectionOrderIn.getId(), true);
			if(outMap.containsKey("flag") && inMap.containsKey("flag")){
				boolean outflag = (Boolean) outMap.get("flag");
				boolean inflag = (Boolean) inMap.get("flag");
				flag = outflag & inflag;
			}
		}
		return flag;
	}

	@Override
	public boolean submitCollectionOrderPageProcess(String id) throws Exception {
		return false;
	}

	@Override
	public Map updateCollectionOrderAssignCustomer(String id,String detailList)
			throws Exception {
		SysUser sysUser = getSysUser();
		boolean flag = false;
		String msg = "";
		String newid = "";
		BigDecimal lastamount = BigDecimal.ZERO;
		JSONArray array = JSONArray.fromObject(detailList);
		CollectionOrder collectionOrder = collectionOrderMapper.getCollectionOrderInfo(id);
		String otherCustomerid = getSysParamValue("collectionOrderOtherCustomer");
		String customerids = "";
		if(null!=collectionOrder && (otherCustomerid.equals(collectionOrder.getCustomerid()))){
			String remark = collectionOrder.getRemark();
			BigDecimal totalamount = BigDecimal.ZERO;
			for(int i=0; i<array.size(); i++){
				JSONObject jsonObject = (JSONObject)array.get(i);
				if(!jsonObject.isEmpty()){
					String customerid = jsonObject.getString("customerid");
					customerids += customerid+",";
					String amountStr = jsonObject.getString("amount");
					if(null!=customerid && !"".equals(customerid) && null!=amountStr && !"".equals(amountStr)){
						BigDecimal amount = BigDecimal.ZERO;
						if(null!=amountStr){
							amount = new BigDecimal(amountStr);
						}
						
						CollectionOrder addCollectionOrder = (CollectionOrder) CommonUtils.deepCopy(collectionOrder);
						String addid = addAssignCustomerCollectionOrder(addCollectionOrder, customerid, amount);
						//生成指定客户的收款单
						newid += addid+",";
						if(null!=addid && !"".equals(addid)){
							CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(collectionOrder.getCustomerid());
							//回滚收款单金额
							if(null!=customerCapital){
								customerCapital.setAmount(customerCapital.getAmount().subtract(amount));
								CustomerCapitalLog customerCapitalLog = new CustomerCapitalLog();
								customerCapitalLog.setCustomerid(customerCapital.getId());
								customerCapitalLog.setBillid(collectionOrder.getId());
								customerCapitalLog.setBilltype("1");
								customerCapitalLog.setPrtype("1");
								customerCapitalLog.setIncomeamount(amount.negate());
								customerCapitalLog.setBalanceamount(customerCapital.getAmount());
								customerCapitalLog.setAdddeptid(sysUser.getDepartmentid());
								customerCapitalLog.setAdddeptname(sysUser.getDepartmentname());
								customerCapitalLog.setAdduserid(sysUser.getUserid());
								customerCapitalLog.setAddusername(sysUser.getName());
								customerCapitalLog.setRemark("指定客户："+customerid+",金额:"+amount+",收款单号:"+addid);
								customerCapitalMapper.addCustomerCapitalLog(customerCapitalLog);
								
								//生成客户对账单
								SalesStatement salesStatement = new SalesStatement();
								salesStatement.setCustomerid(collectionOrder.getCustomerid());
								salesStatement.setBusinessdate(collectionOrder.getBusinessdate());
								salesStatement.setBilltype("4");
								salesStatement.setBillid(collectionOrder.getId());
								salesStatement.setAmount(amount.negate());
								salesStatement.setBillamount(amount.negate());
								salesStatement.setRemark("指定客户："+customerid+",金额:"+amount+",收款单号:"+addid);
								salesStatement.setAdduserid(sysUser.getUserid());
								salesStatement.setAddusername(sysUser.getName());
								salesStatementMapper.addSalesStatement(salesStatement);
								//更新未指定客户余额
								customerCapitalMapper.updateCustomerCapital(customerCapital);
							}
							
							//已分配金额
							totalamount = totalamount.add(amount);
							flag = true;
							//更新收款单信息
							if(null==remark || "".equals(remark)){
								remark = "";
							}else{
								remark += "\n";
							}
							remark += "指定客户："+customerid+",金额:"+amount+",收款单号:"+addid+";";
						}
						
					}
					
				}
				//更新未指定收款单金额以及状态
				CollectionOrder collectionOrder2 = new CollectionOrder();
				collectionOrder2.setId(collectionOrder.getId());
				collectionOrder2.setAmount(collectionOrder.getAmount().subtract(totalamount));
				collectionOrder2.setRemainderamount(collectionOrder2.getAmount());
				if(collectionOrder2.getAmount().compareTo(BigDecimal.ZERO)==0){
					collectionOrder2.setStatus("4");
					collectionOrder2.setIscustomer("1");
					collectionOrder2.setIswriteoff("1");
				}
				collectionOrder2.setRemark(remark);
				collectionOrderMapper.editCollectionOrder(collectionOrder2);
				lastamount = collectionOrder2.getAmount();
			}
		}else{
			msg = "收款单已指定客户，不能再重新指定";
		}
		Map map = new HashMap();
		map.put("msg", msg);
		map.put("flag", flag);
		map.put("newid", newid);
		map.put("lastamount", lastamount);
		map.put("customerids", customerids);
		return map;
	}
	/**
	 * 根据未指定客户的收款单，指定客户生成新客户的收款单
	 * @param collectionOrder
	 * @param customerid
	 * @param amount
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 30, 2013
	 */
	public String addAssignCustomerCollectionOrder(CollectionOrder collectionOrder,String customerid,BigDecimal amount) throws Exception{
		String oldCollectionOrderid = collectionOrder.getId();
		BigDecimal initamount = collectionOrder.getInitamount();
		SysUser sysUser = getSysUser();
		if (isAutoCreate("t_account_collection_order")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(collectionOrder, "t_account_collection_order");
			collectionOrder.setId(id);
		}else{
			collectionOrder.setId("SKD-"+CommonUtils.getDataNumberSendsWithRand());
		} 
		collectionOrder.setCustomerid(customerid);
		collectionOrder.setInitamount(initamount);
		collectionOrder.setAmount(amount);
		collectionOrder.setRemainderamount(amount);
		collectionOrder.setStatus("3");
		collectionOrder.setIscustomer("1");
		collectionOrder.setRemark("来源收款单："+oldCollectionOrderid+",金额:"+amount);
		collectionOrder.setAdduserid(sysUser.getAdduserid());
		collectionOrder.setAddusername(sysUser.getName());
		collectionOrder.setAdddeptid(sysUser.getDepartmentid());
		collectionOrder.setAdddeptname(sysUser.getDepartmentname());
		collectionOrder.setAudituserid(sysUser.getAdduserid());
		collectionOrder.setAuditusername(sysUser.getName());
		collectionOrder.setAudittime(new Date());
		collectionOrderMapper.addCollectionOrder(collectionOrder);
		
		//添加未指定收款单分配客户金额信息
		CollectionOrderAssign collectionOrderAssign = new CollectionOrderAssign();
		collectionOrderAssign.setSourceid(oldCollectionOrderid);
		collectionOrderAssign.setAssignid(collectionOrder.getId());
		collectionOrderAssign.setCustomerid(collectionOrder.getCustomerid());
		collectionOrderAssign.setTotalamount(initamount);
		collectionOrderAssign.setAmount(amount);
		collectionOrderAssign.setBankid(collectionOrder.getBank());
		collectionOrderMapper.addCollectionOrderAssign(collectionOrderAssign);
		
		CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(collectionOrder.getCustomerid());
		if(null!=customerCapital){
			customerCapital.setAmount(customerCapital.getAmount().add(collectionOrder.getAmount()));
			customerCapitalMapper.updateCustomerCapital(customerCapital);
		}else{
			customerCapital = new CustomerCapital();
			customerCapital.setId(collectionOrder.getCustomerid());
			customerCapital.setAmount(collectionOrder.getAmount());
			customerCapitalMapper.addCustomerCapital(customerCapital);
		}
		
		//流水明细
		CustomerCapitalLog customerCapitalLog = new CustomerCapitalLog();
		customerCapitalLog.setCustomerid(collectionOrder.getCustomerid());
		customerCapitalLog.setBillid(collectionOrder.getId());
		customerCapitalLog.setBilltype("1");
		customerCapitalLog.setPrtype("1");
		customerCapitalLog.setIncomeamount(collectionOrder.getAmount());
		customerCapitalLog.setBalanceamount(customerCapital.getAmount());
		customerCapitalLog.setAdddeptid(sysUser.getDepartmentid());
		customerCapitalLog.setAdddeptname(sysUser.getDepartmentname());
		customerCapitalLog.setAdduserid(sysUser.getUserid());
		customerCapitalLog.setAddusername(sysUser.getName());
		
		String othercustomerid = getSysParamValue("collectionOrderOtherCustomer");
		if(null==othercustomerid){
			othercustomerid = "";
		}
		customerCapitalLog.setRemark("金额来源于客户:"+othercustomerid+"的收款单："+oldCollectionOrderid);
		customerCapitalMapper.addCustomerCapitalLog(customerCapitalLog);
		//生成客户对账单
		SalesStatement salesStatement = new SalesStatement();
		salesStatement.setCustomerid(collectionOrder.getCustomerid());
		salesStatement.setBusinessdate(collectionOrder.getBusinessdate());
		salesStatement.setBilltype("4");
		salesStatement.setBillid(collectionOrder.getId());
		salesStatement.setAmount(collectionOrder.getAmount());
		salesStatement.setBillamount(collectionOrder.getAmount());
		salesStatement.setRemark("收款单金额来源于未指定客户的收款单："+oldCollectionOrderid);
		salesStatement.setAdduserid(sysUser.getUserid());
		salesStatement.setAddusername(sysUser.getName());
		int i = salesStatementMapper.addSalesStatement(salesStatement);
		return collectionOrder.getId();
	}
	@Override
	public CustomerCapital getCustomerCapitalByCustomerid(String customerid)
			throws Exception {
		CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(customerid);
		if(null!=customerCapital){
			Customer customer = getCustomerByID(customerCapital.getId());
			if(null!=customerCapital){
				customerCapital.setCustomername(customer.getName());
			}
		}else{
			Customer customer = getCustomerByID(customerid);
			if(null!=customer){
				customerCapital = new CustomerCapital();
				customerCapital.setId(customerid);
				customerCapital.setCustomername(customer.getName());
				customerCapital.setAmount(BigDecimal.ZERO);
			}
		}
		return customerCapital;
	}
	@Override
	public List showCollectionStatementDetailList(String id) throws Exception {
		List<CollectionOrderSatement> list = salesStatementMapper.showCollectionStatementDetailList(id);
		//收款单
		CollectionOrder collectionOrderInfo = getCollectionOrderInfo(id);
		//剩余收款金额
		BigDecimal surplusAmount =  (null != collectionOrderInfo && null != collectionOrderInfo.getAmount()) ? collectionOrderInfo.getAmount():BigDecimal.ZERO;
		//单据金额
		BigDecimal invoiceamountSum = BigDecimal.ZERO;
		//核销总金额
		BigDecimal writeoffamountSum = BigDecimal.ZERO;
		//已关联收款金额
		BigDecimal relateamountSum = BigDecimal.ZERO;
		//收款单未核销金额
		BigDecimal unwriteoffamountSum = BigDecimal.ZERO;
		if(list.size() != 0){
			for(CollectionOrderSatement collectionOrderSatement : list){
				if("1".equals(collectionOrderSatement.getBilltype())){
					SalesInvoice salesInvoice = salesInvoiceMapper.getSalesInvoiceInfo(collectionOrderSatement.getBillid());
					if(null!=salesInvoice){
						Customer customer = getCustomerByID(salesInvoice.getCustomerid());
						if(null!=customer){
							collectionOrderSatement.setCustomerid(customer.getId());
							collectionOrderSatement.setCustomername(customer.getName());
						}
						collectionOrderSatement.setInvoiceamount(salesInvoice.getInvoiceamount());
						collectionOrderSatement.setWriteoffamount(salesInvoice.getWriteoffamount());
						collectionOrderSatement.setBusinessdate(salesInvoice.getBusinessdate());
						collectionOrderSatement.setInvoiceid(salesInvoice.getInvoiceno());
						collectionOrderSatement.setRemark(salesInvoice.getRemark());
						collectionOrderSatement.setWriteoffdate(salesInvoice.getWriteoffdate());
					}
				}else if("2".equals(collectionOrderSatement.getBilltype())){
					CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(collectionOrderSatement.getBillid());
					if(null!=customerPushBalance){
						Customer customer = getCustomerByID(customerPushBalance.getCustomerid());
						if(null!=customer){
							collectionOrderSatement.setCustomerid(customer.getId());
							collectionOrderSatement.setCustomername(customer.getName());
						}
						collectionOrderSatement.setInvoiceamount(customerPushBalance.getAmount());
						collectionOrderSatement.setWriteoffamount(customerPushBalance.getAmount());
						collectionOrderSatement.setBusinessdate(customerPushBalance.getBusinessdate());
						collectionOrderSatement.setRemark(customerPushBalance.getRemark());
						collectionOrderSatement.setWriteoffdate(customerPushBalance.getWriteoffdate());
					}
				}else if("3".equals(collectionOrderSatement.getBilltype())){
					CollectionOrder collectionOrder = collectionOrderMapper.getCollectionOrderInfo(collectionOrderSatement.getOrderid());
					if(null!=collectionOrder){
						Customer customer = getCustomerByID(collectionOrder.getCustomerid());
						if(null!=customer){
							collectionOrderSatement.setCustomerid(customer.getId());
							collectionOrderSatement.setCustomername(customer.getName());
						}
						collectionOrderSatement.setInvoiceamount(collectionOrderSatement.getInvoiceamount());
						collectionOrderSatement.setWriteoffamount(collectionOrderSatement.getRelateamount());
						collectionOrderSatement.setBusinessdate(collectionOrder.getBusinessdate());
						collectionOrderSatement.setRemark(collectionOrder.getRemark());
						collectionOrderSatement.setWriteoffdate(collectionOrderSatement.getWriteoffdate());
						collectionOrderSatement.setInvoiceid(collectionOrderSatement.getBillid());
					}
				}
				//收款单未核销金额=剩余收款金额 - 已关联收款金额
				surplusAmount = surplusAmount.subtract(collectionOrderSatement.getRelateamount());
				collectionOrderSatement.setUnwriteoffamount(surplusAmount);
				invoiceamountSum = invoiceamountSum.add(collectionOrderSatement.getInvoiceamount());
				writeoffamountSum = writeoffamountSum.add(collectionOrderSatement.getWriteoffamount());
				relateamountSum = relateamountSum.add(collectionOrderSatement.getRelateamount());
				unwriteoffamountSum = surplusAmount;
			}
			CollectionOrderSatement collectionOrderSatementSum = new CollectionOrderSatement();
			collectionOrderSatementSum.setInvoiceamount(invoiceamountSum);
			collectionOrderSatementSum.setWriteoffamount(writeoffamountSum);
			collectionOrderSatementSum.setRelateamount(relateamountSum);
			collectionOrderSatementSum.setUnwriteoffamount(unwriteoffamountSum);
			collectionOrderSatementSum.setBillid("合计");
			list.add(collectionOrderSatementSum);
		}
		return list;
	}

	@Override
	public List showBillRelateCollectionList(String billid, String billtype)
			throws Exception {
		List<CollectionOrderSatement> list = salesStatementMapper.getCollectionStatementDetailList(billid,billtype);
		for(CollectionOrderSatement collectionOrderSatement : list){
			CollectionOrder collectionOrder = getCollectionOrderInfo(collectionOrderSatement.getOrderid());
			if(null != collectionOrder){
				Customer customer = getCustomerByID(collectionOrder.getCustomerid());
				if(null!=customer){
					collectionOrderSatement.setCustomerid(customer.getId());
					collectionOrderSatement.setCustomername(customer.getName());
				}
				collectionOrderSatement.setInvoiceamount(collectionOrder.getAmount());
				collectionOrderSatement.setRemark(collectionOrder.getRemark());
			}
			if("1".equals(billtype)){
				SalesInvoice salesInvoice = salesInvoiceMapper.getSalesInvoiceInfo(billid);
				if(null!=salesInvoice){
					collectionOrderSatement.setWriteoffamount(salesInvoice.getWriteoffamount());
					collectionOrderSatement.setBusinessdate(salesInvoice.getBusinessdate());
					collectionOrderSatement.setInvoiceid(salesInvoice.getInvoiceno());
					collectionOrderSatement.setWriteoffdate(salesInvoice.getWriteoffdate());
				}
			}else if("2".equals(billtype)){
				CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(collectionOrderSatement.getBillid());
				if(null!=customerPushBalance){
					collectionOrderSatement.setWriteoffamount(customerPushBalance.getAmount());
					collectionOrderSatement.setBusinessdate(customerPushBalance.getBusinessdate());
					collectionOrderSatement.setWriteoffdate(customerPushBalance.getWriteoffdate());
				}
			}
		}
		return list;
	}

	@Override
	public List getBankWriteReportCollectionList(Map map) throws Exception {
		List<CollectionOrderSatement> cosList = salesStatementMapper.getCollectionStatementDetailListByMap(map);
		BigDecimal orderamount = BigDecimal.ZERO;
		BigDecimal relateamount = BigDecimal.ZERO;
		if(cosList.size() != 0){
			for(CollectionOrderSatement cos : cosList){
				Customer customer = getCustomerByID(cos.getCustomerid());
				if(null != customer){
					cos.setCustomername(customer.getName());
				}
				orderamount = orderamount.add(cos.getOrderamount());
				relateamount = relateamount.add(cos.getRelateamount());
				//收款单明细
				CollectionOrder collectionOrder = getCollectionOrderInfo(cos.getOrderid());
				if(null != collectionOrder){
					cos.setBank(collectionOrder.getBank());
					Bank bank = getBankInfoByID(collectionOrder.getBank());
					if(null != bank){
						cos.setBankname(bank.getName());
					}
				}
			}
			CollectionOrderSatement cosSum = new CollectionOrderSatement();
			cosSum.setOrderid("合计");
			cosSum.setOrderamount(orderamount);
			cosSum.setRelateamount(relateamount);
			cosList.add(cosSum);
		}
		return cosList;
	}

	@Override
	public List<CollectionOrder>  selectCollectionOrderByOaid(String oaid) throws Exception{
		return collectionOrderMapper.selectCollectionOrderByOaid(oaid);
	}

    @Override
    public List<Map> getCollectionAmountData(List idarr) {
        if(null!=idarr && idarr.size()>0){
            List<Map> list = collectionOrderMapper.getCollectionAmountData(idarr);
            return list;
        }
        return new ArrayList<Map>();
    }

	@Override
	public List<CustomerCapital> getCustomerCapitalListForSync() throws Exception {

		return customerCapitalMapper.getCustomerCapitalListForSync();
	}

	@Override
	public List<CustomerCapitalLog> getCustomerCapitalLogListForSync(int startindex, int endindex) throws Exception {
		return customerCapitalMapper.getCustomerCapitalLogListForSync(startindex, endindex);
	}

	@Override
	public PageData getCustomerCapitalListForSync(PageMap pageMap) throws Exception {

		List<Customer> list = customerCapitalMapper.showCustomerAccountList(pageMap);

		PageData pageData = new PageData(customerCapitalMapper.showCustomerAccountCount(pageMap),list,pageMap);
		List<Customer> footer = new ArrayList<Customer>();
		BigDecimal totalamount = customerCapitalMapper.showCustomerAccountSum(pageMap);
		Customer customerSum = new Customer();
		if(null != totalamount){
			customerSum.setName("合计");
			customerSum.setAmount(totalamount);
		}
		footer.add(customerSum);
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public PageData getCustomerCapitalLogListForSync(PageMap pageMap) throws Exception {

		List list = customerCapitalMapper.showCustomerCapitalLogList(pageMap);
		PageData pageData = new PageData(customerCapitalMapper.showCustomerCapitalLogCount(pageMap), list, pageMap);
		return pageData;
	}

	@Override
	public CollectionOrder getCollectionOrderBySource(String source, String sourceno) throws Exception {

		return collectionOrderMapper.getCollectionOrderBySource(source, sourceno);
	}
	@Override
	public List showCollectionOrderListBy(Map map) throws Exception{
		String datasql = getDataAccessRule("t_account_collection_order",null);
		map.put("dataSql", datasql);

		List<CollectionOrder> list=collectionOrderMapper.showCollectionOrderListBy(map);
		for(CollectionOrder collectionOrder : list){

			if(StringUtils.isNotEmpty(collectionOrder.getCollectiondept())) {
				DepartMent departMent = getDepartmentByDeptid(collectionOrder.getCollectiondept());
				if (null != departMent) {
					collectionOrder.setCollectiondeptname(departMent.getName());
				}
			}
			if(StringUtils.isNotEmpty(collectionOrder.getCollectionuser())) {
				Personnel personnel = getPersonnelById(collectionOrder.getCollectionuser());
				if (null != personnel) {
					collectionOrder.setCollectionusername(personnel.getName());
				}
			}
			if(StringUtils.isNotEmpty(collectionOrder.getCustomerid())) {
				Customer customer = getCustomerByID(collectionOrder.getCustomerid());
				if (null != customer) {
					collectionOrder.setCustomername(customer.getName());
					DepartMent saleDept = getDepartmentByDeptid(customer.getSalesdeptid());
					if (null != saleDept) {
						collectionOrder.setSalesdeptname(saleDept.getName());
					}
				}
			}
			if(StringUtils.isNotEmpty(collectionOrder.getHandlerid())) {
				Contacter contacter = getContacterById(collectionOrder.getHandlerid());
				if (null != contacter) {
					collectionOrder.setHandlername(contacter.getName());
				}
			}
			if(StringUtils.isNotEmpty(collectionOrder.getBank())) {
				Bank bank = getBankInfoByID(collectionOrder.getBank());
				if (null != bank) {
					collectionOrder.setBankname(bank.getName());
				}
			}
			if(StringUtils.isNotEmpty(collectionOrder.getBankdeptid())) {
				DepartMent departMent2 = getDepartMentById(collectionOrder.getBankdeptid());
				if (null != departMent2) {
					collectionOrder.setBankdeptname(departMent2.getName());
				}
			}

			if(StringUtils.isNotEmpty(collectionOrder.getCollectiontype())){
				SysCode sysCode=getBaseSysCodeInfo(collectionOrder.getCollectiontype(),"collectionType");
				if(null != sysCode){
					collectionOrder.setCollectiontypename(sysCode.getCodename());
				}
			}

			if("2".equals(collectionOrder.getStatus())){
				collectionOrder.setStatusname("保存");
			}else if("3".equals(collectionOrder.getStatus())){
				collectionOrder.setStatusname("审核通过");
			}else if("4".equals(collectionOrder.getStatus())){
				collectionOrder.setStatusname("关闭");
			}
		}
		return list;
	}

	@Override
	public boolean updateOrderPrinttimes(CollectionOrder collectionOrder) throws Exception{
		return collectionOrderMapper.updateOrderPrinttimes(collectionOrder)>0;
	}
	@Override
	public void updateOrderPrinttimes(List<CollectionOrder> list) throws Exception{
		if(null!=list){
			for(CollectionOrder item : list){
				collectionOrderMapper.updateOrderPrinttimes(item);
			}
		}
	}
}

