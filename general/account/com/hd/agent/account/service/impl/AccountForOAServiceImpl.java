/**
 * @(#)AccountForOAServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年11月24日 chenwei 创建版本
 */
package com.hd.agent.account.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.model.Bank;
import org.apache.commons.lang.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.model.BankAmountOthers;
import com.hd.agent.account.model.Payorder;
import com.hd.agent.account.service.IAccountForOAService;
import com.hd.agent.account.service.IPayorderService;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.common.util.CommonUtils;

/**
 * 针对OA模块--财务模块接口实现类
 * 
 * @author chenwei
 */
public class AccountForOAServiceImpl extends BaseAccountServiceImpl implements IAccountForOAService {
	
	private IPayorderService payorderService;

	public IPayorderService getPayorderService() {
		return payorderService;
	}

	public void setPayorderService(IPayorderService payorderService) {
		this.payorderService = payorderService;
	}
	@Override
	public boolean addPayOrder(Payorder payorder) throws Exception{
		boolean flag = false;
		BuySupplier buySupplier = getSupplierInfoById(payorder.getSupplierid());
		if(null!=buySupplier){
			payorder.setBusinessdate(CommonUtils.getTodayDataStr());
			payorder.setSupplierid(payorder.getSupplierid());
			if(StringUtils.isEmpty(payorder.getBuydeptid())){
				payorder.setBuydeptid(buySupplier.getBuydeptid());
			}
			if(StringUtils.isEmpty(payorder.getBuyuserid())){
				payorder.setBuyuserid(buySupplier.getBuyuserid());
			}
			payorder.setHandlerid(buySupplier.getContact());
			payorder.setPaytype("1");
			flag = payorderService.addAndAuditPayOrder(payorder);
		}
		return flag;
	}

	@Override
	public boolean addBankAmountOthers(BankAmountOthers bankAmountOthers)
			throws Exception {
		boolean flag = false;
		if(null!=bankAmountOthers && StringUtils.isNotEmpty(bankAmountOthers.getBankid()) && StringUtils.isNotEmpty(bankAmountOthers.getLendtype())){
			if (isAutoCreate("t_account_bankamount_others")) {
				// 获取自动编号
				String id = getAutoCreateSysNumbderForeign(bankAmountOthers, "t_account_bankamount_others");
				bankAmountOthers.setId(id);
			}else{
				bankAmountOthers.setId("YHQT-"+CommonUtils.getDataNumber1());
			}
			SysUser sysUser = getSysUser();
			bankAmountOthers.setBusinessdate(CommonUtils.getTodayDataStr());
			if(StringUtils.isEmpty(bankAmountOthers.getAdduserid())){
				bankAmountOthers.setAdduserid(sysUser.getUserid());
				bankAmountOthers.setAddusername(sysUser.getName());
				bankAmountOthers.setAdddeptid(sysUser.getDepartmentid());
				bankAmountOthers.setAdddeptname(sysUser.getDepartmentname());
			}
			if(StringUtils.isEmpty(bankAmountOthers.getDeptid())){
				Bank bank = getBankInfoByID(bankAmountOthers.getBankid());
				if(null!=bank){
					bankAmountOthers.setDeptid(bank.getBankdeptid());
				}
			}
			bankAmountOthers.setAudittime(new Date());
			bankAmountOthers.setAudituserid(sysUser.getUserid());
			bankAmountOthers.setAuditusername(sysUser.getName());
			bankAmountOthers.setClosetime(new Date());
			bankAmountOthers.setStatus("4");
			int i = bankAmountMapper.addBankAmountOthers(bankAmountOthers);
			if("1".equals(bankAmountOthers.getLendtype())){
				updateBankAmountIncome(bankAmountOthers.getBilltype(), bankAmountOthers.getId(), bankAmountOthers.getBankid(),bankAmountOthers.getDeptid(), bankAmountOthers.getAmount(), bankAmountOthers.getRemark());
			}else if("2".equals(bankAmountOthers.getLendtype())){
				updateBankAmountPay(bankAmountOthers.getBilltype(), bankAmountOthers.getId(), bankAmountOthers.getBankid(),bankAmountOthers.getDeptid(), bankAmountOthers.getAmount(), bankAmountOthers.getRemark());
			}
			flag = i>0;
		}
		return flag;
	}

	@Override
	public boolean delteBankAmountOthers(String billid) throws Exception {
		boolean flag = false;
		BankAmountOthers bankAmountOthers = bankAmountMapper.getBankAmountOthersByBillid(billid);
		if(null!=bankAmountOthers){
			if("1".equals(bankAmountOthers.getLendtype())){
				updateBankAmountPay(bankAmountOthers.getBilltype(), bankAmountOthers.getId(), bankAmountOthers.getBankid(),bankAmountOthers.getDeptid(), bankAmountOthers.getAmount(), "单据反审");
			}else if("2".equals(bankAmountOthers.getLendtype())){
				updateBankAmountIncome(bankAmountOthers.getBilltype(), bankAmountOthers.getId(), bankAmountOthers.getBankid(),bankAmountOthers.getDeptid(), bankAmountOthers.getAmount(), "单据反审");
			}
			int i = bankAmountMapper.delteBankAmountOthersByBillid(billid);
			flag = i>0;
		}
		return flag;
	}

	@Override
	public boolean deleteBankAmountOthersByOaId(String oaId) throws Exception {

		int ret = bankAmountMapper.deleteBankAmountOthersByOaId(oaId);
		return ret > 0;
	}

	@Override
	public List<BankAmountOthers> selectBankAmountOthersList(Map param)
			throws Exception {

		return bankAmountMapper.selectBankAmountOthersForOA(param);
	}

	@Override
	public int deletePayOrder(String oaid) throws Exception {
		
		List<Payorder> orderList = payorderService.selectPayOrderByOaid(oaid);
		
		for(Payorder order : orderList) {
			
			payorderService.deletePayorder(order.getId());
		}
		
		return 0;
	}

	@Override
	public boolean rollbackdPayOrder(Payorder order) throws Exception {
		
		//**********************************************************************
		// 是否rollback 判断
		//**********************************************************************
		String oaid = order.getOaid();
		List list = payorderService.selectPayOrderByOaid(oaid);
		if(list.size() % 2 == 0) {
			
			return true;
		}
		
		//**********************************************************************
		// rollback
		//**********************************************************************
		boolean flag = true;
		BuySupplier supplier = getSupplierInfoById(order.getSupplierid());
		if(supplier != null){

			order.setBusinessdate(CommonUtils.getTodayDataStr());
			order.setSupplierid(order.getSupplierid());
			if(StringUtils.isEmpty(order.getBuydeptid())){

				order.setBuydeptid(supplier.getBuydeptid());
			}
			if(StringUtils.isEmpty(order.getBuyuserid())){

				order.setBuyuserid(supplier.getBuyuserid());
			}

			order.setHandlerid(supplier.getContact());
			order.setPaytype("1");
			flag = payorderService.rollbackAndAuditPayOrder(order);
		}
		return flag;
	}

    @Override
    public List selectBankAmountOthersByOaid(String oaid) throws Exception {

        return bankAmountMapper.selectBankAmountOthersByOaid(oaid);
    }
}

