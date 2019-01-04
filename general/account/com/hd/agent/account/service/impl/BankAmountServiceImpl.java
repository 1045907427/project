/**
 * @(#)BankAmountServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年11月24日 chenwei 创建版本
 */
package com.hd.agent.account.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.model.BankAmount;
import com.hd.agent.account.model.BankAmountBegin;
import com.hd.agent.account.model.BankAmountOthers;
import com.hd.agent.account.service.IBankAmountService;
import com.hd.agent.basefiles.model.Bank;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.report.model.GoodsOut;

/**
 * 
 * 银行账户金额业务service实现类
 * @author chenwei
 */
public class BankAmountServiceImpl extends BaseAccountServiceImpl implements
		IBankAmountService {
	
	@Override
	public boolean addBankAmountBegin(BankAmountBegin bankAmountBegin)
			throws Exception {
		if (isAutoCreate("t_account_bankamount_begin")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(bankAmountBegin,"t_account_bankamount_begin");
			bankAmountBegin.setId(id);
		} else {
			bankAmountBegin.setId("YHQC-"+ CommonUtils.getDataNumber1());
		}
		SysUser sysUser = getSysUser();
		bankAmountBegin.setStatus("2");
		bankAmountBegin.setAdduserid(sysUser.getUserid());
		bankAmountBegin.setAddusername(sysUser.getName());
		bankAmountBegin.setAdddeptid(sysUser.getDepartmentid());
		bankAmountBegin.setAdddeptname(sysUser.getDepartmentname());
		int i = bankAmountMapper.addBankAmountBegin(bankAmountBegin);
		return i>0;
	}

	@Override
	public boolean editBankAmountBegin(BankAmountBegin bankAmountBegin)
			throws Exception {
		boolean flag = false;
		BankAmountBegin oldBankAmountBegin = bankAmountMapper.getBankAmountBeginByID(bankAmountBegin.getId());
		if(null!=bankAmountBegin && ("2".equals(oldBankAmountBegin.getStatus()) || "1".equals(oldBankAmountBegin.getStatus()))){
			SysUser sysUser = getSysUser();
			bankAmountBegin.setStatus("2");
			bankAmountBegin.setModifyuserid(sysUser.getUserid());
			bankAmountBegin.setModifyusername(sysUser.getName());
			int i = bankAmountMapper.editBankAmountBegin(bankAmountBegin);
			flag = i>0;
		}
		return flag;
	}

	@Override
	public BankAmountBegin getBankAmountBeginById(String id) throws Exception {
		BankAmountBegin bankAmountBegin = bankAmountMapper.getBankAmountBeginByID(id);
		return bankAmountBegin;
	}

	@Override
	public boolean deleteBankAmountBegin(String id) throws Exception {
		boolean flag = false;
		BankAmountBegin oldBankAmountBegin = bankAmountMapper.getBankAmountBeginByID(id);
		if(null!=oldBankAmountBegin && ("2".equals(oldBankAmountBegin.getStatus()) || "1".equals(oldBankAmountBegin.getStatus()))){
			int i = bankAmountMapper.deleteBankAmountBegin(id);
			flag = i>0;
		}
		return flag;
	}

	@Override
	public PageData showBankAmountBeginList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_account_bankamount_begin", null);
		pageMap.setDataSql(dataSql);
		List<BankAmountBegin> list = bankAmountMapper.showBankAmountBeginList(pageMap);
		for(BankAmountBegin bankAmountBegin : list){
			Bank bank = getBankInfoByID(bankAmountBegin.getBankid());
			if(null!=bank){
				bankAmountBegin.setBankname(bank.getName());
			}
		}
		PageData pageData = new PageData(bankAmountMapper.showBankAmountBeginCount(pageMap), list, pageMap);
		return pageData;
	}

	@Override
	public boolean auditBankAmountBegin(String id) throws Exception {
		boolean flag = false;
		BankAmountBegin oldBankAmountBegin = bankAmountMapper.getBankAmountBeginByID(id);
		if(null!=oldBankAmountBegin && "2".equals(oldBankAmountBegin.getStatus())){
			
			SysUser sysUser = getSysUser();
			int i = bankAmountMapper.auditBankAmountBegin(id,sysUser.getUserid(),sysUser.getName());
			flag = i>0;
			if(flag){
				//更新银行账户金额 添加借贷单
				BankAmountOthers bankAmountOthers = new BankAmountOthers();
				bankAmountOthers.setBankid(oldBankAmountBegin.getBankid());
				Bank bank = getBankInfoByID(oldBankAmountBegin.getBankid());
				if(null!=bank){
					bankAmountOthers.setDeptid(bank.getBankdeptid());
				}
				bankAmountOthers.setBillid(oldBankAmountBegin.getId());
				bankAmountOthers.setBilltype("0");
				bankAmountOthers.setLendtype("1");
				bankAmountOthers.setAmount(oldBankAmountBegin.getAmount());
				bankAmountOthers.setBusinessdate(CommonUtils.getTodayDataStr());
				addAndAuditBankAmountOthers(bankAmountOthers);
			}
		}
		return flag;
	}

	@Override
	public boolean oppauditBankAmountBegin(String id) throws Exception {
		boolean flag = false;
		BankAmountBegin oldBankAmountBegin = bankAmountMapper.getBankAmountBeginByID(id);
		if(null!=oldBankAmountBegin && "3".equals(oldBankAmountBegin.getStatus())){
			int i = bankAmountMapper.oppauditBankAmountBegin(id);
			flag = i>0;
			if(flag){
				//更新银行账户金额 添加借贷单
				BankAmountOthers bankAmountOthers = new BankAmountOthers();
				bankAmountOthers.setBankid(oldBankAmountBegin.getBankid());
				Bank bank = getBankInfoByID(oldBankAmountBegin.getBankid());
				if(null!=bank){
					bankAmountOthers.setDeptid(bank.getBankdeptid());
				}
				bankAmountOthers.setBillid(oldBankAmountBegin.getId());
				bankAmountOthers.setBilltype("0");
				bankAmountOthers.setLendtype("2");
				bankAmountOthers.setAmount(oldBankAmountBegin.getAmount());
				bankAmountOthers.setRemark("反审期初："+oldBankAmountBegin.getId());
				bankAmountOthers.setBusinessdate(CommonUtils.getTodayDataStr());
				addAndAuditBankAmountOthers(bankAmountOthers);
			}
		}
		return flag;
	}

	@Override
	public boolean closeBankAmountBegin(String id) throws Exception {
		boolean flag = false;
		BankAmountBegin oldBankAmountBegin = bankAmountMapper.getBankAmountBeginByID(id);
		if(null!=oldBankAmountBegin && "3".equals(oldBankAmountBegin.getStatus())){
			int i = bankAmountMapper.closeBankAmountBegin(id);
			flag = i>0;
		}
		return flag;
	}

	@Override
	public PageData showBankAmountOthersList(PageMap pageMap)
			throws Exception {
		String dataSql = getDataAccessRule("t_account_bankamount_others", null);
		pageMap.setDataSql(dataSql);
		List<BankAmountOthers> list = bankAmountMapper.showBankAmountOthersList(pageMap);
		for(BankAmountOthers bankAmountOthers : list){
			Bank bank = getBankInfoByID(bankAmountOthers.getBankid());
			if(null!=bank){
				bankAmountOthers.setBankname(bank.getName());
			}
			DepartMent departMent = getDepartmentByDeptid(bankAmountOthers.getDeptid());
			if(null!=departMent){
				bankAmountOthers.setDeptname(departMent.getName());
			}
		}
		PageData pageData = new PageData(bankAmountMapper.showBankAmountOthersCount(pageMap), list, pageMap);
		Map footer = bankAmountMapper.showBankAmountOthersSum(pageMap);
		if(null!=footer){
			footer.put("bankname", "合计");
			List footerlist = new ArrayList();
			footerlist.add(footer);
			pageData.setFooter(footerlist);
		}
		return pageData;
	}

	@Override
	public boolean addBankAmountOthers(BankAmountOthers bankAmountOthers)
			throws Exception {
		if (isAutoCreate("t_account_bankamount_others")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(bankAmountOthers, "t_account_bankamount_others");
			bankAmountOthers.setId(id);
		}else{
			bankAmountOthers.setId("YHQT-"+CommonUtils.getDataNumber1());
		}
		SysUser sysUser = getSysUser();
		bankAmountOthers.setBusinessdate(CommonUtils.getTodayDataStr());
		bankAmountOthers.setAdduserid(sysUser.getUserid());
		bankAmountOthers.setAddusername(sysUser.getName());
		bankAmountOthers.setAdddeptid(sysUser.getDepartmentid());
		bankAmountOthers.setAdddeptname(sysUser.getDepartmentname());
		bankAmountOthers.setStatus("2");
		int i = bankAmountMapper.addBankAmountOthers(bankAmountOthers);
		return i>0;
	}

	@Override
	public boolean editBankAmountOthers(BankAmountOthers bankAmountOthers)
			throws Exception {
		boolean flag = false;
		BankAmountOthers oldBankAmountOthers = bankAmountMapper.getBankAmountOthersByID(bankAmountOthers.getId());
		if(null!=oldBankAmountOthers && "2".equals(oldBankAmountOthers.getStatus())){
			SysUser sysUser = getSysUser();
			bankAmountOthers.setModifyuserid(sysUser.getUserid());
			bankAmountOthers.setModifyusername(sysUser.getName());
			int i = bankAmountMapper.editBankAmountOthers(bankAmountOthers);
			flag = i>0;
		}
		return flag;
	}

	@Override
	public boolean auditBankAmountOthers(String id)
			throws Exception {
		boolean flag = false;
		BankAmountOthers oldBankAmountOthers = bankAmountMapper.getBankAmountOthersByID(id);
		if(null!=oldBankAmountOthers && "2".equals(oldBankAmountOthers.getStatus())){
			SysUser sysUser = getSysUser();
			if("1".equals(oldBankAmountOthers.getLendtype())){
				updateBankAmountIncome(oldBankAmountOthers.getBilltype(), oldBankAmountOthers.getId(), oldBankAmountOthers.getBankid(),oldBankAmountOthers.getDeptid(), oldBankAmountOthers.getAmount(), oldBankAmountOthers.getRemark());
			}else if("2".equals(oldBankAmountOthers.getLendtype())){
				updateBankAmountPay(oldBankAmountOthers.getBilltype(), oldBankAmountOthers.getId(), oldBankAmountOthers.getBankid(),oldBankAmountOthers.getDeptid(), oldBankAmountOthers.getAmount(), oldBankAmountOthers.getRemark());
			}
			int i = bankAmountMapper.auditBankAmountOthers(id,sysUser.getUserid(),sysUser.getName());
			flag = i>0;
		}
		return flag;
	}

	@Override
	public boolean oppauditBankAmountOthers(String id) throws Exception {
		boolean flag = false;
		BankAmountOthers oldBankAmountOthers = bankAmountMapper.getBankAmountOthersByID(id);
		if(null!=oldBankAmountOthers && "3".equals(oldBankAmountOthers.getStatus())){
			int i = bankAmountMapper.oppauditBankAmountOthers(id);
			flag = i>0;
			if("1".equals(oldBankAmountOthers.getLendtype())){
				updateBankAmountPay(oldBankAmountOthers.getBilltype(), oldBankAmountOthers.getId(), oldBankAmountOthers.getBankid(),oldBankAmountOthers.getDeptid(), oldBankAmountOthers.getAmount(), "反审");
			}else if("2".equals(oldBankAmountOthers.getLendtype())){
				updateBankAmountIncome(oldBankAmountOthers.getBilltype(), oldBankAmountOthers.getId(), oldBankAmountOthers.getBankid(),oldBankAmountOthers.getDeptid(), oldBankAmountOthers.getAmount(), "反审");
			}
		}
		return flag;
	}

	@Override
	public boolean deleteBankAmountOthers(String id) throws Exception {
		boolean flag = false;
		BankAmountOthers oldBankAmountOthers = bankAmountMapper.getBankAmountOthersByID(id);
		if(null!=oldBankAmountOthers && "2".equals(oldBankAmountOthers.getStatus())){
			int i = bankAmountMapper.deleteBankAmountOthers(id);
			flag = i>0;
		}
		return flag;
	}

	@Override
	public BankAmountOthers getBankAmountOthersByID(String id) throws Exception {
		BankAmountOthers bankAmountOthers = bankAmountMapper.getBankAmountOthersByID(id);
		return bankAmountOthers;
	}

	@Override
	public boolean closeBankAmountOthers(String id) throws Exception {
		boolean flag = false;
		BankAmountOthers oldBankAmountOthers = bankAmountMapper.getBankAmountOthersByID(id);
		if(null!=oldBankAmountOthers && "3".equals(oldBankAmountOthers.getStatus())){
			int i = bankAmountMapper.closeBankAmountOthers(id);
			flag = i>0;
		}
		return flag;
	}

	@Override
	public PageData showBankAmountList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_base_finance_bank", "t");
		pageMap.setDataSql(dataSql);
		List<BankAmount> list = bankAmountMapper.showBankAmountList(pageMap);
		for(BankAmount bankAmount : list){
			DepartMent departMent = getDepartmentByDeptid(bankAmount.getDeptid());
			if(null!=departMent){
				bankAmount.setDeptname(departMent.getName());
			}
		}
		int count = bankAmountMapper.showBankAmountListCount(pageMap);

		BankAmount bankAmountsum = bankAmountMapper.showBankAmountListSum(pageMap);
		List<BankAmount> footer = new ArrayList<BankAmount>();
		footer.add(bankAmountsum);
		PageData pageData = new PageData(count, list, pageMap);
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public PageData showBankAmountLogList(PageMap pageMap) throws Exception {

		List list = bankAmountMapper.showBankAmountLogList(pageMap);
		PageData pageData = new PageData(bankAmountMapper.showBankAmountLogCount(pageMap), list, pageMap);
		return pageData;
	}

	@Override
	public boolean addBankAmountTransfer(String outbankid, String inbankid,
			BigDecimal amount) throws Exception {
		boolean flag = false;
		Bank outbank = getBankInfoByID(outbankid);
		Bank inbank = getBankInfoByID(inbankid);
		if(null!=outbank && null!=inbank){
			BankAmountOthers bankAmountOthersOut = new BankAmountOthers();
			bankAmountOthersOut.setBankid(outbankid);
			bankAmountOthersOut.setBilltype("7");
			bankAmountOthersOut.setLendtype("2");
			bankAmountOthersOut.setAmount(amount);
			bankAmountOthersOut.setRemark("转账到银行："+inbank.getName());
			bankAmountOthersOut.setBusinessdate(CommonUtils.getTodayDataStr());
			boolean outflag = addAndAuditBankAmountOthers(bankAmountOthersOut);
			
			BankAmountOthers bankAmountOthersIn = new BankAmountOthers();
			bankAmountOthersIn.setBankid(inbankid);
			bankAmountOthersIn.setBilltype("7");
			bankAmountOthersIn.setLendtype("1");
			bankAmountOthersIn.setAmount(amount);
			bankAmountOthersIn.setRemark("从银行："+outbank.getName()+"转账");
			bankAmountOthersIn.setBusinessdate(CommonUtils.getTodayDataStr());
			boolean inflag = addAndAuditBankAmountOthers(bankAmountOthersIn);
			flag = outflag && inflag;
		}
		return flag;
	}
	

	@Override
	public BankAmount getBankAmountInfo(String id) throws Exception {
		BankAmount bankAmount = bankAmountMapper.getBankAmountByBankid(id);
		return bankAmount;
	}
}

