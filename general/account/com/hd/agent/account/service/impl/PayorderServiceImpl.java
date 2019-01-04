/**
 * @(#)PayorderServiceImpl.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 16, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.account.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.PayorderMapper;
import com.hd.agent.account.dao.PurchaseStatementMapper;
import com.hd.agent.account.dao.SupplierCapitalMapper;
import com.hd.agent.account.model.*;
import com.hd.agent.account.service.IPayorderService;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.Contacter;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class PayorderServiceImpl extends BaseAccountServiceImpl implements IPayorderService{
	
	private PayorderMapper payorderMapper;
	
	private PurchaseStatementMapper purchaseStatementMapper;
	
	private SupplierCapitalMapper supplierCapitalMapper;

	public SupplierCapitalMapper getSupplierCapitalMapper() {
		return supplierCapitalMapper;
	}

	public void setSupplierCapitalMapper(SupplierCapitalMapper supplierCapitalMapper) {
		this.supplierCapitalMapper = supplierCapitalMapper;
	}

	public PurchaseStatementMapper getPurchaseStatementMapper() {
		return purchaseStatementMapper;
	}

	public void setPurchaseStatementMapper(
			PurchaseStatementMapper purchaseStatementMapper) {
		this.purchaseStatementMapper = purchaseStatementMapper;
	}

	public PayorderMapper getPayorderMapper() {
		return payorderMapper;
	}

	public void setPayorderMapper(PayorderMapper payorderMapper) {
		this.payorderMapper = payorderMapper;
	}

	@Override
	public boolean addPayorder(Payorder payorder) throws Exception {
		if (isAutoCreate("t_account_purchase_payorder")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(payorder, "t_account_purchase_payorder");
			payorder.setId(id);
		}else{
			payorder.setId("FKD-"+CommonUtils.getDataNumberSendsWithRand());
		}
		SysUser sysUser = getSysUser();
		payorder.setAdddeptid(sysUser.getDepartmentid());
		payorder.setAdddeptname(sysUser.getDepartmentname());
		payorder.setAdduserid(sysUser.getUserid());
		payorder.setAddusername(sysUser.getName());
		payorder.setRemainderamount(payorder.getAmount().subtract((payorder.getWriteoffamount()!=null?payorder.getWriteoffamount():(new BigDecimal(0)))));
		int i = payorderMapper.addPayorder(payorder);
		return i > 0;
	}

	@Override
	public boolean auditPayorder(String id) throws Exception {
		Payorder payorder = payorderMapper.getPayorderInfo(id);
		SysUser sysUser = getSysUser();
		boolean flag = false;
		if(null != payorder){
			if("2".equals(payorder.getStatus()) || "6".equals(payorder.getStatus())){
				SupplierCapital supplierCapital = supplierCapitalMapper.getSupplierCapital(payorder.getSupplierid());
				if(null != supplierCapital){
					supplierCapital.setAmount(supplierCapital.getAmount().add(payorder.getAmount()));
					supplierCapitalMapper.updateSupplierCapital(supplierCapital);
				}
				else{
					supplierCapital = new SupplierCapital();
					supplierCapital.setId(payorder.getSupplierid());
					supplierCapital.setAmount(payorder.getAmount());
					supplierCapitalMapper.addSupplierCapital(supplierCapital);
				}
				//流水明细
				SupplierCapitalLog supplierCapitalLog = new SupplierCapitalLog();
				supplierCapitalLog.setSupplierid(payorder.getSupplierid());
				supplierCapitalLog.setBillid(id);
				supplierCapitalLog.setBilltype("1");
				supplierCapitalLog.setPrtype("1");
				supplierCapitalLog.setIncomeamount(payorder.getAmount());
				supplierCapitalLog.setBalanceamount(supplierCapital.getAmount());
				supplierCapitalLog.setAdddeptid(sysUser.getDepartmentid());
				supplierCapitalLog.setAdddeptname(sysUser.getDepartmentname());
				supplierCapitalLog.setAdduserid(sysUser.getUserid());
				supplierCapitalLog.setAddusername(sysUser.getName());
				if(StringUtils.isNotEmpty(payorder.getRemark())){
					payorder.setRemark(payorder.getRemark()+",付款单审核");
				}else{
					payorder.setRemark("付款单审核");
				}
				supplierCapitalLog.setRemark(payorder.getRemark());
				supplierCapitalMapper.addSupplierCapitalLog(supplierCapitalLog);
				//当收款单指定供应商后 审核通过后 自动关闭
				//未指定供应商 收款单处于审核通过状态
				Map map = new HashMap();
				map.put("audituserid", sysUser.getUserid());
				map.put("auditusername", sysUser.getName());
				map.put("id", id);
				map.put("businessdate",getAuditBusinessdate(payorder.getBusinessdate()));
				if(StringUtils.isNotEmpty(payorder.getSupplierid())){
					map.put("status", "4");
				}
				else{
					map.put("status", "3");
				}
				//更新银行账户金额 添加借贷单
				BankAmountOthers bankAmountOthers = new BankAmountOthers();
				bankAmountOthers.setBankid(payorder.getBank());
				bankAmountOthers.setDeptid(payorder.getBuydeptid());
				bankAmountOthers.setBillid(payorder.getId());
				bankAmountOthers.setBilltype("2");
				bankAmountOthers.setLendtype("2");
				bankAmountOthers.setAmount(payorder.getAmount());
                if(StringUtils.isEmpty(payorder.getRemark())){
                    bankAmountOthers.setRemark("付款单："+payorder.getId());
                }else{
                    bankAmountOthers.setRemark(payorder.getRemark()+" 付款单："+payorder.getId());
                }
				bankAmountOthers.setBusinessdate(getAuditBusinessdate(payorder.getBusinessdate()));
				bankAmountOthers.setOaid(payorder.getOaid());
				bankAmountOthers.setUpamount(CommonUtils.AmountUnitCnChange(payorder.getAmount().doubleValue()));
				addAndAuditBankAmountOthers(bankAmountOthers);
				
				int i = payorderMapper.auditPayorder(map);
				flag = i > 0;
			}
		}
		return flag;
	}

	@Override
	public boolean deletePayorder(String id) throws Exception {
		Payorder payorder = payorderMapper.getPayorderInfo(id);
		boolean flag = false;
		if(null!=payorder && ("1".equals(payorder.getStatus()) || "2".equals(payorder.getStatus()))){
			int i = payorderMapper.deletePayorder(id);
			flag = i > 0;
		}
		return flag;
	}

	@Override
	public boolean editPayorder(Payorder payorder) throws Exception {
		Payorder order = payorderMapper.getPayorderInfo(payorder.getId());
		if(null!=order && ("1".equals(order.getStatus()) || "2".equals(order.getStatus()) || "6".equals(order.getStatus()))){
			SysUser sysUser = getSysUser();
			payorder.setModifyuserid(sysUser.getUserid());
			payorder.setModifyusername(sysUser.getName());
			int i = payorderMapper.editPayorder(payorder);
			return i>0;
		}else{
			return false;
		}
	}

	@Override
	public Payorder getPayorderInfo(String id) throws Exception {
		Payorder payorder = payorderMapper.getPayorderInfo(id);
        if(null!=payorder){
            BuySupplier buySupplier = getSupplierInfoById(payorder.getSupplierid());
            if(null!=buySupplier){
                payorder.setSuppliername(buySupplier.getName());
            }
        }
		return payorder;
	}

	@Override
	public Map oppauditPayorder(String id) throws Exception {
		Payorder payorder = payorderMapper.getPayorderInfo(id);
		boolean flag = false;
		String msg = "";
		if(null != payorder && "4".equals(payorder.getStatus()) && "0".equals(payorder.getIswriteoff())){
			SupplierCapital supplierCapital = supplierCapitalMapper.getSupplierCapital(payorder.getSupplierid());
            SysUser sysUser = getSysUser();
			//回滚付款单金额
			if(null != supplierCapital){
				supplierCapital.setAmount(supplierCapital.getAmount().subtract(payorder.getAmount()));
				supplierCapitalMapper.updateSupplierCapital(supplierCapital);
                //增加负的供应商资金流水日志
                SupplierCapitalLog supplierCapitalLog =  new SupplierCapitalLog();
                supplierCapitalLog.setSupplierid(payorder.getSupplierid());
                supplierCapitalLog.setBillid(payorder.getId());
                supplierCapitalLog.setBilltype("1");
                supplierCapitalLog.setPrtype("1");
                supplierCapitalLog.setIncomeamount(payorder.getAmount().multiply(new BigDecimal(-1)));
                supplierCapitalLog.setBalanceamount(supplierCapital.getAmount());
                supplierCapitalLog.setAdddeptid(sysUser.getDepartmentid());
                supplierCapitalLog.setAdddeptname(sysUser.getDepartmentname());
                supplierCapitalLog.setAdduserid(sysUser.getUserid());
                supplierCapitalLog.setAddusername(sysUser.getName());
				if(StringUtils.isNotEmpty(payorder.getRemark())){
					payorder.setRemark(payorder.getRemark()+",付款单反审");
				}else{
					payorder.setRemark("付款单反审");
				}
				supplierCapitalLog.setRemark(payorder.getRemark());
                supplierCapitalMapper.addSupplierCapitalLog(supplierCapitalLog);
			}

			Map map = new HashMap();
			map.put("audituserid", sysUser.getUserid());
			map.put("auditusername", sysUser.getName());
			map.put("id", id);
			flag = payorderMapper.oppauditPayorder(map) > 0;
			
			//更新银行账户金额 添加借贷单
			BankAmountOthers bankAmountOthers = new BankAmountOthers();
			bankAmountOthers.setBankid(payorder.getBank());
			bankAmountOthers.setDeptid(payorder.getBuydeptid());
			bankAmountOthers.setBillid(payorder.getId());
			bankAmountOthers.setBilltype("2");
			bankAmountOthers.setLendtype("1");
			bankAmountOthers.setAmount(payorder.getAmount());
			bankAmountOthers.setRemark("反审付款单："+payorder.getId());
			bankAmountOthers.setBusinessdate(CommonUtils.getTodayDataStr());
			addAndAuditBankAmountOthers(bankAmountOthers);
		}
		else{
			msg = "付款单正在核销中，或者付款单不是审核通过状态，不能反审";
		}
		Map map2 = new HashMap();
		map2.put("flag", flag);
		map2.put("msg", msg);
		return map2;
	}

	@Override
	public PageData showPayorderList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_report_finance_payments", "z");
		pageMap.setDataSql(dataSql);
		List<Payorder> list = payorderMapper.getPayorderList(pageMap);
		if(list.size() != 0){
			for(Payorder payorder : list){
				DepartMent departMent = getDepartmentByDeptid(payorder.getBuydeptid());
				if(null!=departMent){
					payorder.setBuydeptname(departMent.getName());
				}
				Personnel personnel = getPersonnelById(payorder.getBuyuserid());
				if(null!=personnel){
					payorder.setBuyusername(personnel.getName());
				}
				BuySupplier buySupplier =getSupplierInfoById(payorder.getSupplierid());
				if(null!=buySupplier){
					payorder.setSuppliername(buySupplier.getName());
				}
				Contacter contacter = getContacterById(payorder.getHandlerid());
				if(null!=contacter){
					payorder.setHandlername(contacter.getName());
				}
			}
		}
		PageData pageData = new PageData(payorderMapper.getPayorderListCount(pageMap),list,pageMap);
		//合计
		Payorder payorder = payorderMapper.getPayorderSum(pageMap);
		List footer = new ArrayList();
		if(null!=payorder){
			payorder.setId("合计");
			footer.add(payorder);
		}
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public List getPayorderListByIds(String ids) throws Exception {
		List list = new ArrayList();
		if(StringUtils.isNotEmpty(ids)){
			String[] idArr = ids.split(",");
			for(String id : idArr){
				Payorder payorder = payorderMapper.getPayorderInfo(id);
				list.add(payorder);
			}
		}
		return list;
	}

	@Override
	public boolean setPayorderMerge(String ids, String orderid, String remark)
			throws Exception {
		boolean flag = false;
		if(StringUtils.isNotEmpty(ids)){
			String[] idArr = ids.split(",");
			//合并过来的余额
			BigDecimal addRemainderamount = new BigDecimal(0);
			SysUser sysUser = getSysUser();
			for(String id : idArr){
				Payorder payorder = payorderMapper.getPayorderInfo(id);
				if(!id.equals(orderid)){
					//合并的付款单 加上被合并付款单 对账单
					PurchaseStatement purchaseStatement = new PurchaseStatement();
					//purchaseStatement.setOrderid(orderid);
					purchaseStatement.setBusinessdate(CommonUtils.getTodayDataStr());
					purchaseStatement.setBilltype("2");
					purchaseStatement.setBillid(payorder.getId());
					purchaseStatement.setAmount(payorder.getRemainderamount().negate());
					purchaseStatement.setBillamount(payorder.getRemainderamount().negate());
					purchaseStatement.setAdduserid(sysUser.getUserid());
					purchaseStatement.setAddusername(sysUser.getName());
					purchaseStatement.setRemark(remark);
					purchaseStatementMapper.addPurchaseStatement(purchaseStatement);
					
					//合并的付款单 加上被合并付款但 对账单
					PurchaseStatement purchaseStatement2 = new PurchaseStatement();
					//purchaseStatement2.setOrderid(payorder.getId());
					purchaseStatement2.setBusinessdate(CommonUtils.getTodayDataStr());
					purchaseStatement2.setBilltype("2");
					purchaseStatement2.setBillid(payorder.getId());
					purchaseStatement2.setAmount(payorder.getRemainderamount());
					purchaseStatement2.setBillamount(payorder.getRemainderamount());
					purchaseStatement2.setAdduserid(sysUser.getUserid());
					purchaseStatement2.setAddusername(sysUser.getName());
					purchaseStatement2.setRemark(remark);
					purchaseStatementMapper.addPurchaseStatement(purchaseStatement2);
					//合并关闭
					int i = payorderMapper.writeOffPayorder(id, payorder.getWriteoffamount().add(payorder.getRemainderamount()), new BigDecimal(0), "1");
					if(i > 0){
						addRemainderamount = addRemainderamount.add(payorder.getRemainderamount());
					}
				}
			}
			Payorder payorder = payorderMapper.getPayorderInfo(orderid);
			int i = payorderMapper.writeOffPayorder(orderid,payorder.getWriteoffamount(),payorder.getRemainderamount().add(addRemainderamount),"2");
			flag = i > 0;
		}
		return flag;
	}

	@Override
	public boolean submitPayorderPageProcess(String id) throws Exception {
		return false;
	}

	@Override
	public PageData showSupplierAccountList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_base_buy_supplier", "t1");
		pageMap.setDataSql(dataSql);
		List<SupplierCapital> list = supplierCapitalMapper.showSupplierAccountList(pageMap);
		for(SupplierCapital supplierCapital : list){
			BuySupplier buySupplier = getSupplierInfoById(supplierCapital.getId());
			if(null!=buySupplier){
				supplierCapital.setSuppliername(buySupplier.getName());
			}
			if("".equals(supplierCapital.getId())){
				supplierCapital.setSuppliername("未指定供应商");
				
			}
		}
		PageData pageData = new PageData(supplierCapitalMapper.showSupplierAccountCount(pageMap),list,pageMap);
		List footer = new ArrayList();
		BigDecimal totalamount = supplierCapitalMapper.showSupplierAccountSum(pageMap);
		Map map = new HashMap();
		map.put("id", "合计");
		map.put("amount", totalamount);
		footer.add(map);
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public PageData showSupplierCapitalLogList(PageMap pageMap)
			throws Exception {
		List list = supplierCapitalMapper.showSupplierCapitalLogList(pageMap);
		PageData pageData = new PageData(supplierCapitalMapper.showSupplierCapitalLogCount(pageMap),list,pageMap);
		return pageData;
	}

	@Override
	public SupplierCapital getSupplierCapitalBySupplierid(String supplierid)
			throws Exception {
		SupplierCapital supplierCapital = supplierCapitalMapper.getSupplierCapital(supplierid);
		if(null!=supplierCapital){
			BuySupplier buySupplier = getSupplierInfoById(supplierCapital.getId());
			if(null!=buySupplier){
				supplierCapital.setSuppliername(buySupplier.getName());
			}
		}else{
			BuySupplier buySupplier = getSupplierInfoById(supplierid);
			if(null!=buySupplier){
				supplierCapital = new SupplierCapital();
				supplierCapital.setId(supplierid);
				supplierCapital.setSuppliername(buySupplier.getName());
				supplierCapital.setAmount(BigDecimal.ZERO);
			}
		}
		return supplierCapital;
	}

	@Override
	public boolean setPayOrderTransfer(TransferOrder transferOrder)
			throws Exception {
		boolean flag = false;
		SupplierCapital supplierCapitalout = supplierCapitalMapper.getSupplierCapital(transferOrder.getOutsupplierid());
		SupplierCapital supplierCapitalin = supplierCapitalMapper.getSupplierCapital(transferOrder.getInsupplierid());
		if(null != supplierCapitalout){
			SysUser sysUser = getSysUser();
			//付款单转账 生成对账单 
			//转出付款单
			PurchaseStatement purchaseStatement = new PurchaseStatement();
			purchaseStatement.setSupplierid(supplierCapitalout.getId());
			purchaseStatement.setBusinessdate(CommonUtils.getTodayDataStr());
			purchaseStatement.setBilltype("2");
			purchaseStatement.setBillid(supplierCapitalout.getId());
			purchaseStatement.setAmount(transferOrder.getTransferamount());
			purchaseStatement.setBillamount(transferOrder.getTransferamount());
			purchaseStatement.setAdduserid(sysUser.getUserid());
			purchaseStatement.setAddusername(sysUser.getName());
			purchaseStatement.setRemark(transferOrder.getRemark());
			purchaseStatementMapper.addPurchaseStatement(purchaseStatement);
			//更新转出供应商资金余额
			//生成供应商资金流水
			//流水明细
			SupplierCapitalLog supplierCapitalLog = new SupplierCapitalLog();
			supplierCapitalLog.setSupplierid(transferOrder.getOutsupplierid());
			supplierCapitalLog.setBillid(transferOrder.getInsupplierid());
			supplierCapitalLog.setBilltype("3");
			supplierCapitalLog.setPrtype("4");
			supplierCapitalLog.setIncomeamount(transferOrder.getTransferamount().negate());
			supplierCapitalLog.setBalanceamount(supplierCapitalout.getAmount().subtract(transferOrder.getTransferamount()));
			supplierCapitalLog.setAdddeptid(sysUser.getDepartmentid());
			supplierCapitalLog.setAdddeptname(sysUser.getDepartmentname());
			supplierCapitalLog.setAdduserid(sysUser.getUserid());
			supplierCapitalLog.setAddusername(sysUser.getName());
			supplierCapitalLog.setRemark(transferOrder.getRemark());
			supplierCapitalMapper.addSupplierCapitalLog(supplierCapitalLog);
			supplierCapitalout.setAmount(supplierCapitalout.getAmount().subtract(transferOrder.getTransferamount()));
			supplierCapitalMapper.updateSupplierCapital(supplierCapitalout);
			//转入付款单
			PurchaseStatement purchaseStatement2 = new PurchaseStatement();
			purchaseStatement2.setSupplierid(transferOrder.getInsupplierid());
			purchaseStatement2.setBusinessdate(CommonUtils.getTodayDataStr());
			purchaseStatement2.setBilltype("2");
			purchaseStatement2.setBillid(transferOrder.getOutsupplierid());
			purchaseStatement2.setAmount(transferOrder.getTransferamount().negate());
			purchaseStatement2.setBillamount(transferOrder.getTransferamount().negate());
			purchaseStatement2.setAdduserid(sysUser.getUserid());
			purchaseStatement2.setAddusername(sysUser.getName());
			purchaseStatement2.setRemark(transferOrder.getRemark());
			purchaseStatementMapper.addPurchaseStatement(purchaseStatement2);
			//流水明细
			SupplierCapitalLog supplierCapitalLog2 = new SupplierCapitalLog();
			supplierCapitalLog2.setSupplierid(transferOrder.getInsupplierid());
			supplierCapitalLog2.setBillid(transferOrder.getOutsupplierid());
			supplierCapitalLog2.setBilltype("3");
			supplierCapitalLog2.setPrtype("3");
			supplierCapitalLog2.setIncomeamount(transferOrder.getTransferamount());
			supplierCapitalLog2.setBalanceamount(supplierCapitalin.getAmount().add(transferOrder.getTransferamount()));
			supplierCapitalLog2.setAdddeptid(sysUser.getDepartmentid());
			supplierCapitalLog2.setAdddeptname(sysUser.getDepartmentname());
			supplierCapitalLog2.setAdduserid(sysUser.getUserid());
			supplierCapitalLog2.setAddusername(sysUser.getName());
			supplierCapitalLog2.setRemark(transferOrder.getRemark());
			supplierCapitalMapper.addSupplierCapitalLog(supplierCapitalLog2);
			if(null!=supplierCapitalin){
				//生成客户资金流水
				supplierCapitalin.setAmount(supplierCapitalin.getAmount().add(transferOrder.getTransferamount()));
				supplierCapitalMapper.updateSupplierCapital(supplierCapitalin);
			}else{
				supplierCapitalin = new SupplierCapital();
				supplierCapitalin.setId(transferOrder.getInsupplierid());
				supplierCapitalin.setAmount(transferOrder.getTransferamount());
				supplierCapitalMapper.addSupplierCapital(supplierCapitalin);
			}
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean addAndAuditPayOrder(Payorder payorder) throws Exception {
		boolean flag = false;
		if (isAutoCreate("t_account_purchase_payorder")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(payorder, "t_account_purchase_payorder");
			payorder.setId(id);
		}else{
			payorder.setId("FKD-"+CommonUtils.getDataNumberSendsWithRand());
		}
		
		if(StringUtils.isEmpty(payorder.getAdduserid())){
			SysUser sysUser = getSysUser();
			payorder.setAdddeptid(sysUser.getDepartmentid());
			payorder.setAdddeptname(sysUser.getDepartmentname());
			payorder.setAdduserid(sysUser.getUserid());
			payorder.setAddusername(sysUser.getName());
		}
		payorder.setStatus("2");
		payorder.setRemainderamount(payorder.getAmount().subtract((payorder.getWriteoffamount()!=null?payorder.getWriteoffamount():(new BigDecimal(0)))));
		int i = payorderMapper.addPayorder(payorder);
		if(i>0){
			flag = auditPayorder(payorder.getId());
		}
		return flag;
	}

	@Override
	public List<Payorder> selectPayOrderByOaid(String oaid) throws Exception {

		return payorderMapper.selectPayOrderByOaid(oaid);
	}

	@Override
	public boolean rollbackAndAuditPayOrder(Payorder payorder) throws Exception {

		boolean flag = false;
		if (isAutoCreate("t_account_purchase_payorder")) {

			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(payorder, "t_account_purchase_payorder");
			payorder.setId(id);
		} else {

			payorder.setId("FKD-" + CommonUtils.getDataNumberSendsWithRand());
		}

		if (StringUtils.isEmpty(payorder.getAdduserid())) {

			SysUser sysUser = getSysUser();
			payorder.setAdddeptid(sysUser.getDepartmentid());
			payorder.setAdddeptname(sysUser.getDepartmentname());
			payorder.setAdduserid(sysUser.getUserid());
			payorder.setAddusername(sysUser.getName());
		}
		payorder.setStatus("2");
		payorder.setRemainderamount(payorder.getAmount().subtract((payorder.getWriteoffamount() != null ? payorder.getWriteoffamount() : (new BigDecimal(0)))));
		int i = payorderMapper.addPayorder(payorder);
		if (i > 0) {
			flag = rollbackPayorder(payorder.getId());
		}
		return flag;
	}

	@Override
	public boolean rollbackPayorder(String id) throws Exception {

		Payorder payorder = payorderMapper.getPayorderInfo(id);
		SysUser sysUser = getSysUser();
		boolean flag = false;
		if(null != payorder){

			if("2".equals(payorder.getStatus()) || "6".equals(payorder.getStatus())){

				SupplierCapital supplierCapital = supplierCapitalMapper.getSupplierCapital(payorder.getSupplierid());
				if(null != supplierCapital){

					supplierCapital.setAmount(supplierCapital.getAmount().add(payorder.getAmount()));
					supplierCapitalMapper.updateSupplierCapital(supplierCapital);
				} else {

					supplierCapital = new SupplierCapital();
					supplierCapital.setId(payorder.getSupplierid());
					supplierCapital.setAmount(payorder.getAmount());
					supplierCapitalMapper.addSupplierCapital(supplierCapital);
				}

				//流水明细
				SupplierCapitalLog supplierCapitalLog = new SupplierCapitalLog();
				supplierCapitalLog.setSupplierid(payorder.getSupplierid());
				supplierCapitalLog.setBillid(id);
				supplierCapitalLog.setBilltype("1");
				supplierCapitalLog.setPrtype("1");
				supplierCapitalLog.setIncomeamount(payorder.getAmount());
				supplierCapitalLog.setBalanceamount(supplierCapital.getAmount());
				supplierCapitalLog.setAdddeptid(sysUser.getDepartmentid());
				supplierCapitalLog.setAdddeptname(sysUser.getDepartmentname());
				supplierCapitalLog.setAdduserid(sysUser.getUserid());
				supplierCapitalLog.setAddusername(sysUser.getName());
				if(StringUtils.isNotEmpty(payorder.getRemark())){
					payorder.setRemark(payorder.getRemark()+",付款单回滚");
				}else{
					payorder.setRemark("付款单回滚");
				}
				supplierCapitalLog.setRemark(payorder.getRemark());
				supplierCapitalMapper.addSupplierCapitalLog(supplierCapitalLog);
				//当收款单指定供应商后 审核通过后 自动关闭
				//未指定供应商 收款单处于审核通过状态
				Map map = new HashMap();
				map.put("audituserid", sysUser.getUserid());
				map.put("auditusername", sysUser.getName());
				map.put("id", id);
				if(StringUtils.isNotEmpty(payorder.getSupplierid())){
					map.put("status", "4");
				}
				else{
					map.put("status", "3");
				}
				//更新银行账户金额 添加借贷单
				BankAmountOthers bankAmountOthers = new BankAmountOthers();
				bankAmountOthers.setBankid(payorder.getBank());
				bankAmountOthers.setDeptid(payorder.getBuydeptid());
				bankAmountOthers.setBillid(payorder.getId());
				bankAmountOthers.setBilltype("2");
				bankAmountOthers.setLendtype("1");
				bankAmountOthers.setAmount(payorder.getAmount() == null ? BigDecimal.ZERO : payorder.getAmount().negate());
				bankAmountOthers.setRemark("付款单：" + payorder.getId());
				bankAmountOthers.setBusinessdate(CommonUtils.getTodayDataStr());
				bankAmountOthers.setOaid(payorder.getOaid());
				bankAmountOthers.setUpamount(CommonUtils.AmountUnitCnChange(payorder.getAmount().doubleValue()));
				addAndAuditBankAmountOthers(bankAmountOthers);
				
				int i = payorderMapper.auditPayorder(map);
				flag = i > 0;
			}
		}
		return flag;
	}

    @Override
    public List getSupplierPaySumData(List idarr) throws Exception {
        if(null!=idarr && idarr.size()>0){
            List<Map> list = payorderMapper.getSupplierPaySumData(idarr);
            return list;
        }
        return new ArrayList<Map>();
    }

	@Override
	public boolean updatePayorderVouchertimes(Payorder payorder) throws Exception {
		return payorderMapper.updatePayorderVouchertimes(payorder) >0;
	}

}

