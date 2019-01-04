/**
 * @(#)FinanceServiceImpl.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 17, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.CollectionOrderMapper;
import com.hd.agent.basefiles.dao.FinanceMapper;
import com.hd.agent.basefiles.model.Bank;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.ExpensesSort;
import com.hd.agent.basefiles.model.Payment;
import com.hd.agent.basefiles.model.Settlement;
import com.hd.agent.basefiles.model.TaxType;
import com.hd.agent.basefiles.service.IFinanceService;
import com.hd.agent.common.model.Tree;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class FinanceServiceImpl extends FilesLevelServiceImpl implements IFinanceService{

	private FinanceMapper financeMapper;
	
	private CollectionOrderMapper collectionOrderMapper;
	
	public CollectionOrderMapper getCollectionOrderMapper() {
		return collectionOrderMapper;
	}

	public void setCollectionOrderMapper(CollectionOrderMapper collectionOrderMapper) {
		this.collectionOrderMapper = collectionOrderMapper;
	}
	public FinanceMapper getFinanceMapper() {
		return financeMapper;
	}

	public void setFinanceMapper(FinanceMapper financeMapper) {
		this.financeMapper = financeMapper;
	}
	/*--------------------------------------税种档案------------------------------------------------------------*/
	@Override
	public boolean addTaxType(TaxType taxType) throws Exception {
		return financeMapper.addTaxType(taxType) > 0;
	}

	@Override
	public boolean deleteTaxType(String id) throws Exception {
		return financeMapper.deleteTaxType(id) > 0;
	}

	@Override
	public boolean disableTaxType(TaxType taxType) throws Exception {
		return financeMapper.disableTaxType(taxType) > 0;
	}

	@Override
	public boolean editTaxType(TaxType taxType) throws Exception {
		return financeMapper.editTaxType(taxType) > 0;
	}

	@Override
	public boolean enableTaxType(TaxType taxType) throws Exception {
		return financeMapper.enableTaxType(taxType) > 0;
	}

	@Override
	public TaxType getTaxTypeInfo(String id) throws Exception {
		return financeMapper.getTaxTypeInfo(id);
	}

	@Override
	public PageData getTaxTypeList(PageMap pageMap) throws Exception {
		String cols = getAccessColumnList("t_base_finance_taxtype", null);
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_base_finance_taxtype", null);
		pageMap.setDataSql(sql);
		PageData pageDate = new PageData(financeMapper.getTaxTypeListCount(pageMap),
				financeMapper.getTaxTypeList(pageMap),pageMap);
		return pageDate;
	}

	@Override
	public boolean isUsedName(String name) throws Exception {
		return financeMapper.isUsedName(name) > 0;
	}

	@Override
	public boolean isUsedRate(String rate) throws Exception {
		return financeMapper.isUsedRate(rate) > 0;
	}
	
	public Map addDRTaxType(List<TaxType> list)throws Exception{
		String failStr = "",closeVal = "",repeatVal = "";
		int closeNum = 0, repeatNum = 0,failureNum = 0,successNum = 0;
		boolean flag = false;
		if(list.size() != 0){
			for(TaxType taxType : list){
				TaxType taxTypeInfo = financeMapper.getTaxTypeInfo(taxType.getId());
				if(taxTypeInfo == null){
					if(financeMapper.isUsedName(taxType.getName()) > 0){
						if(StringUtils.isEmpty(repeatVal)){
							repeatVal = taxType.getId();
						}
						else{
							repeatVal += "," + taxType.getId();
						}
						repeatNum++;
					}
					else{
						SysUser sysUser = getSysUser();
						taxType.setAdddeptid(sysUser.getDepartmentid());
						taxType.setAdddeptname(sysUser.getDepartmentname());
						taxType.setAdduserid(sysUser.getUserid());
						taxType.setAddusername(sysUser.getName());
						taxType.setState("2");
						flag = financeMapper.addTaxType(taxType) > 0;
						if(!flag){
							if(StringUtils.isNotEmpty(failStr)){
								failStr += "," + taxType.getId(); 
							}
							else{
								failStr = taxType.getId();
							}
							failureNum++;
						}
						else{
							successNum++;
						}
					}
				}
				else{
					if("0".equals(taxTypeInfo.getState())){//禁用状态，不允许导入
						if(StringUtils.isEmpty(closeVal)){
							closeVal = taxTypeInfo.getId();
						}
						else{
							closeVal += "," + taxTypeInfo.getId();
						}
						closeNum++;
					}
					else{
						if(StringUtils.isEmpty(repeatVal)){
							repeatVal = taxTypeInfo.getId();
						}
						else{
							repeatVal += "," + taxTypeInfo.getId();
						}
						repeatNum++;
					}
				}
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("success", successNum);
		map.put("failure", failureNum);
		map.put("failStr", failStr);
		map.put("repeatNum", repeatNum);
		map.put("closeNum", closeNum);
		map.put("closeVal", closeVal);
		map.put("repeatVal", repeatVal);
		return map;
	}

	/*--------------------------------------结算方式------------------------------------------------------------*/
	
	@Override
	public boolean addSettlement(Settlement settlement) throws Exception {
		return financeMapper.addSettlement(settlement) > 0;
	}

	@Override
	public boolean deleteSettlement(String id) throws Exception {
		return financeMapper.deleteSettlement(id) > 0;
	}

	@Override
	public boolean disableSettlement(Settlement settlement) throws Exception {
		return financeMapper.disableSettlement(settlement) > 0;
	}

	@Override
	public boolean editSettlement(Settlement settlement) throws Exception {
		return financeMapper.editSettlement(settlement) > 0;
	}

	@Override
	public boolean enableSettlement(Settlement settlement) throws Exception {
		return financeMapper.enableSettlement(settlement) > 0;
	}

	@Override
	public PageData getSettlementListPage(PageMap pageMap) throws Exception {
		String cols = getAccessColumnList("t_base_finance_settlement",null); //字段权限
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_base_finance_settlement",null); //数据权限
		pageMap.setDataSql(sql);
		PageData pageData = new PageData(financeMapper.getSettlementListCountPage(pageMap),
				financeMapper.getSettlementListPage(pageMap),pageMap);
		return pageData;
	}

	@Override
	public Settlement getSettlemetDetail(String id) throws Exception {
		Map map = getAccessColumnMap("t_base_finance_settlement", null);
		map.put("id", id);
		return financeMapper.getSettlemetDetail(map);
	}

	@Override
	public boolean isUsedSettlementName(String name) throws Exception {
		return financeMapper.isUsedSettlementName(name) > 0;
	}
	
	@Override
	public Map addDRSettlement(List<Settlement> list) throws Exception {
		boolean flag = false;
		String failStr = "",closeVal = "",repeatVal = "";
		int closeNum = 0, repeatNum = 0,failureNum = 0,successNum = 0;
		Map map = new HashMap();
		if(list.size() != 0){
			for(Settlement settlement : list){
				map.put("id", settlement.getId());
				Settlement settlementInfo = financeMapper.getSettlemetDetail(map);
				if(settlementInfo == null){
					settlement.setState("1");
					SysUser sysUser = getSysUser();
					settlement.setAdddeptid(sysUser.getDepartmentid());
					settlement.setAdddeptname(sysUser.getDepartmentname());
					settlement.setAdduserid(sysUser.getUserid());
					settlement.setAddusername(sysUser.getName());
					flag = financeMapper.addSettlement(settlement) > 0;
					if(!flag){
						if(StringUtils.isNotEmpty(failStr)){
							failStr += "," + settlement.getId(); 
						}
						else{
							failStr = settlement.getId();
						}
						failureNum++;
					}
					else{
						successNum++;
					}
				}
				else{
					if("0".equals(settlementInfo.getState())){//禁用状态，不允许导入
						if(StringUtils.isEmpty(closeVal)){
							closeVal = settlementInfo.getId();
						}
						else{
							closeVal += "," + settlementInfo.getId();
						}
						closeNum++;
					}
					else{
						if(StringUtils.isEmpty(repeatVal)){
							repeatVal = settlementInfo.getId();
						}
						else{
							repeatVal += "," + settlementInfo.getId();
						}
						repeatNum++;
					}
				}
			}
		}
		map.put("flag", flag);
		map.put("success", successNum);
		map.put("failure", failureNum);
		map.put("failStr", failStr);
		map.put("repeatNum", repeatNum);
		map.put("closeNum", closeNum);
		map.put("closeVal", closeVal);
		map.put("repeatVal", repeatVal);
		return map;
	}
	/*--------------------------------------支付方式------------------------------------------------------------*/
	
	@Override
	public boolean addPayment(Payment payment) throws Exception {
		return financeMapper.addPayment(payment) > 0;
	}

	@Override
	public boolean deletePayment(String id) throws Exception {
		return financeMapper.deletePayment(id) > 0;
	}

	@Override
	public boolean disablePayment(Payment payment) throws Exception {
		return financeMapper.disablePayment(payment) > 0;
	}

	@Override
	public boolean editPayment(Payment payment) throws Exception {
		return financeMapper.editPayment(payment) > 0;
	}

	@Override
	public boolean enablePayment(Payment payment) throws Exception {
		return financeMapper.enablePayment(payment) > 0;
	}

	@Override
	public Payment getPaymentDetail(String id) throws Exception {
		Map map = getAccessColumnMap("t_base_finance_payment", null);
		map.put("id", id);
		return financeMapper.getPaymentDetail(map);
	}

	@Override
	public PageData getPaymentListPage(PageMap pageMap) throws Exception {
		String cols = getAccessColumnList("t_base_finance_payment",null); //字段权限
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_base_finance_payment",null); //数据权限
		pageMap.setDataSql(sql);
		PageData pageData = new PageData(financeMapper.getPaymentListPageCount(pageMap),
				financeMapper.getPaymentListPage(pageMap),pageMap);
		return pageData;
	}

	@Override
	public boolean isUsedPaymentName(String name) throws Exception {
		return financeMapper.isUsedPaymentName(name) > 0;
	}
	
	@Override
	public Map addDRPayment(List<Payment> list)throws Exception{
		boolean flag = false;
		String failStr = "",closeVal = "",repeatVal = "";
		int closeNum = 0, repeatNum = 0,failureNum = 0,successNum = 0;
		Map map = new HashMap();
		if(list.size() != 0){
			for(Payment payment : list){
				map.put("id", payment.getId());
				Payment pay = financeMapper.getPaymentDetail(map);
				if(pay == null){
					if(StringUtils.isEmpty(payment.getState())){
						payment.setState("2");
					}
					SysUser sysUser = getSysUser();
					payment.setAdddeptid(sysUser.getDepartmentid());
					payment.setAdddeptname(sysUser.getDepartmentname());
					payment.setAdduserid(sysUser.getUserid());
					payment.setAddusername(sysUser.getName());
					flag = financeMapper.addPayment(payment) > 0;
					if(!flag){
						if(StringUtils.isNotEmpty(failStr)){
							failStr += "," + payment.getId(); 
						}
						else{
							failStr = payment.getId();
						}
						failureNum++;
					}
					else{
						successNum++;
					}
				}
				else{
					if("0".equals(pay.getState())){//禁用状态，不允许导入
						if(StringUtils.isEmpty(closeVal)){
							closeVal = pay.getId();
						}
						else{
							closeVal += "," + pay.getId();
						}
						closeNum++;
					}
					else{
						if(StringUtils.isEmpty(repeatVal)){
							repeatVal = pay.getId();
						}
						else{
							repeatVal += "," + pay.getId();
						}
						repeatNum++;
					}
				}
			}
		}
		map.put("flag", flag);
		map.put("success", successNum);
		map.put("failure", failureNum);
		map.put("failStr", failStr);
		map.put("repeatNum", repeatNum);
		map.put("repeatVal", repeatVal);
		map.put("closeNum", closeNum);
		map.put("closeVal", closeVal);
		return map;
	}
	
	/*--------------------------------------费用分类------------------------------------------------------------*/
	
	@Override
	public boolean addExpensesSort(ExpensesSort expensesSort) throws Exception {
		return financeMapper.addExpensesSort(expensesSort) > 0;
	}

	@Override
	public boolean deleteExpensesSort(String id) throws Exception {
		return financeMapper.deleteExpensesSort(id) > 0;
	}

	@Override
	public boolean disableExpensesSort(ExpensesSort expensesSort)
			throws Exception {
		return financeMapper.disableExpensesSort(expensesSort) > 0;
	}

	@Override
	public Map editExpensesSort(ExpensesSort expensesSort) throws Exception {
		Map map = new HashMap();
		Map map2 = new HashMap();
		ExpensesSort oldExpensesSort = getExpensesSortDetail(expensesSort.getOldId());
		if(null != oldExpensesSort){
			if(!oldExpensesSort.getThisid().equals(expensesSort.getThisid()) || !oldExpensesSort.getThisname().equals(expensesSort.getThisname())){
				//获取下级所有分类列表
				List<ExpensesSort> childList = financeMapper.getExpensesSortChildList(oldExpensesSort.getId());
				//若编码改变，下属所有的任务分类编码应做对应的替换
				if(childList.size() != 0){
					for(ExpensesSort repeatES : childList){
						repeatES.setOldId(repeatES.getId());
						if(!oldExpensesSort.getThisid().equals(expensesSort.getThisid())){
							String newid = repeatES.getId().replaceFirst(oldExpensesSort.getThisid(), expensesSort.getThisid()).trim();
							repeatES.setId(newid);
							String newpid = repeatES.getPid().replaceFirst(oldExpensesSort.getThisid(), expensesSort.getThisid()).trim();
							repeatES.setPid(newpid);
						}
						if(!oldExpensesSort.getThisname().equals(expensesSort.getThisname())){
							//若本级名称改变，下属所有的任务分类名称应做对应的替换
							String newname = repeatES.getName().replaceFirst(oldExpensesSort.getThisname(), expensesSort.getThisname());
							repeatES.setName(newname);
						}
						Tree node = new Tree();
						node.setId(repeatES.getId());
						node.setParentid(repeatES.getPid());
						node.setText(repeatES.getThisname());
						node.setState(repeatES.getState());
						map2.put(repeatES.getOldId(), node);
					}
					financeMapper.editExpensesSortBatch(childList);
				}
			}
		}
		boolean flag = financeMapper.editExpensesSort(expensesSort) > 0;
		if(flag){
			Tree node = new Tree();
			node.setId(expensesSort.getId());
			node.setParentid(expensesSort.getPid());
			node.setText(expensesSort.getThisname());
			node.setState(expensesSort.getState());
			map2.put(expensesSort.getOldId(), node);
		}
		map.put("flag", flag);
		map.put("nodes", map2);
		return map;
	}

	@Override
	public boolean enableExpensesSort(ExpensesSort expensesSort)
			throws Exception {
		return (financeMapper.enableExpensesSort(expensesSort) > 0) && isTreeLeaf();
	}

	@Override
	public ExpensesSort getExpensesSortDetail(String id) throws Exception {
		Map map = getAccessColumnMap("t_base_finance_expenses_sort", null);//加字段查看权限
		map.put("id", id);
		return financeMapper.getExpensesSortDetail(map);
	}

	@Override
	public List getExpensesSortList(PageMap pageMap) throws Exception {
		String cols = getAccessColumnList("t_base_finance_expenses_sort",null); //字段权限
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_base_finance_expenses_sort",null); //数据权限
		pageMap.setDataSql(sql);
		return financeMapper.getExpensesSortList(pageMap);
	}

	@Override
	public List getExpensesSortParentAllChildren(PageMap pageMap)
			throws Exception {
		String cols = getAccessColumnList("t_base_finance_expenses_sort",null); //字段权限
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_base_finance_expenses_sort",null); //数据权限
		pageMap.setDataSql(sql);
		return financeMapper.getExpensesSortParentAllChildren(pageMap);
	}

	@Override
	public boolean isUsedExpensesSortName(String name) throws Exception {
		return financeMapper.isUsedExpensesSortName(name) > 0;
	}
	
	/**
	 * 判断pid是否存在,若存在则为末及标志，否则，不是，进行级联替换
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public boolean isTreeLeaf()throws Exception{
		int count = 0;
		//获取暂存以外的费用分类数据列表
		List<ExpensesSort> list = financeMapper.getExpensesSortByStateList();
		if(list.size() != 0){
			for(ExpensesSort expenses:list){
				//判断pid是否存在,若存在则为末及标志，否则，不是
				String pid = financeMapper.isLeafExpensesSort(expenses.getId());
				if(!StringUtils.isNotEmpty(pid)){
					expenses.setLeaf("1");
				}
				else{
					expenses.setLeaf("0");
				}
				expenses.setOldId(expenses.getId());
				Map map = new HashMap();
				map.put("id", expenses.getId());
				ExpensesSort beforeExpenses = financeMapper.getExpensesSortDetail(map);
				//判断是否可以进行修改操作,如果可以修改，同时更新级联关系数据
				if(canBasefilesIsEdit("t_base_finance_expenses_sort", beforeExpenses, expenses)){
					int i = financeMapper.editExpensesSort(expenses);
					count++;
				}
			}
			if(count == list.size()){
				return true;
			}
		}
		return false;
	}

	@Override
	public List getExpensesSortListData() throws Exception {
		return financeMapper.getExpensesSortListData();
	}

	@Override
	public List getPaymentListData() throws Exception {
		return financeMapper.getPaymentListData();
	}

	@Override
	public List getSettlementListData() throws Exception {
		return financeMapper.getSettlementListData();
	}

	@Override
	public List getTaxTypeListData() throws Exception {
		return financeMapper.getTaxTypeListData();
	}

	/*-------------------------银行档案-------------------------------*/
	
	@Override
	public boolean addBank(Bank bank) throws Exception {
		return financeMapper.addBank(bank) > 0;
	}

	@Override
	public boolean checkBandAccountUserd(String account) throws Exception {
		return financeMapper.checkBandAccountUserd(account) > 0;
	}

	@Override
	public boolean checkBankidUserd(String id) throws Exception {
		return financeMapper.checkBankidUserd(id) > 0;
	}

	@Override
	public boolean closeBank(String id) throws Exception {
		SysUser sysUser = getSysUser();
		Map map = new HashMap();
		map.put("closeuserid", sysUser.getUserid());
		map.put("closeusername", sysUser.getName());
		map.put("id", id);
		return financeMapper.closeBank(map) > 0;
	}

	@Override
	public boolean deleteBank(String id) throws Exception {
		return financeMapper.deleteBank(id) > 0;
	}

	@Override
	public boolean editBank(Bank bank) throws Exception {
		Bank oldBank = financeMapper.getBankDetail(bank.getOldid());
		boolean  flag = financeMapper.editBank(bank) > 0;
		if(flag){
			//银行部门修改，收款单对应银行部门同步更新
			if(null != oldBank){
				String oldbankdeptid = (null != oldBank.getBankdeptid()) ? oldBank.getBankdeptid() : "";
				String newbankdeptid = (null != bank.getBankdeptid()) ? bank.getBankdeptid() : "";
				if(!oldbankdeptid.equals(newbankdeptid)){
					collectionOrderMapper.updateCollectionOrderBankdept(newbankdeptid, oldbankdeptid);
				}
			}
		}
		return flag;
	}

	@Override
	public Bank getBankDetail(String id) throws Exception {
		return financeMapper.getBankDetail(id);
	}

	@Override
	public PageData getBankList(PageMap pageMap) throws Exception {
		String cols = getAccessColumnList("t_base_finance_bank",null); //字段权限
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_base_finance_bank",null); //数据权限
		pageMap.setDataSql(sql);
		List<Bank> bList = financeMapper.getBankListPage(pageMap);
		if(bList.size() != 0){
			for(Bank bank : bList){
				SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(bank.getState(), "state");
				if(null != sysCode){
					bank.setStateName(sysCode.getCodename());
				}
				DepartMent departMent = getDepartmentByDeptid(bank.getBankdeptid());
				if(null != departMent){
					bank.setBankdeptname(departMent.getName());
				}
			}
		}
		PageData pageData = new PageData(financeMapper.getBankCountPage(pageMap),
				bList,pageMap);
		return pageData;
	}

    @Override
    public Bank getBankInfoByName(String name) throws Exception {
        return financeMapper.getBankByName(name);
    }

    @Override
	public boolean openBank(String id) throws Exception {
		SysUser sysUser = getSysUser();
		Map map = new HashMap();
		map.put("openuserid", sysUser.getUserid());
		map.put("openusername", sysUser.getName());
		map.put("id", id);
		return financeMapper.openBank(map) > 0;
	}

	@Override
	public Map addDRbank(List<Bank> list) throws Exception {
		boolean flag = false;
		String failStr = "",closeVal = "",repeatVal = "";
		int closeNum = 0, repeatNum = 0,failureNum = 0,successNum = 0;
		if(list.size() != 0){
			for(Bank bank :list){
				Bank bank2 = financeMapper.getBankDetail(bank.getId());
				if(null == bank2){
					SysUser sysUser = getSysUser();
					bank.setAdddeptid(sysUser.getDepartmentid());
					bank.setAdddeptname(sysUser.getDepartmentname());
					bank.setAdduserid(sysUser.getUserid());
					bank.setAddusername(sysUser.getName());
					bank.setState("1");
					flag = financeMapper.addBank(bank) > 0;
					if(!flag){
						if(StringUtils.isNotEmpty(failStr)){
							failStr += "," + bank.getId(); 
						}
						else{
							failStr = bank.getId();
						}
						failureNum++;
					}
					else{
						successNum++;
					}
				}
				else{
					if("0".equals(bank2.getState())){//禁用状态，不允许导入
						if(StringUtils.isEmpty(closeVal)){
							closeVal = bank2.getId();
						}
						else{
							closeVal += "," + bank2.getId();
						}
						closeNum++;
					}
					else{
						if(StringUtils.isEmpty(repeatVal)){
							repeatVal = bank2.getId();
						}
						else{
							repeatVal += "," + bank2.getId();
						}
						repeatNum++;
					}
				}
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("success", successNum);
		map.put("failure", failureNum);
		map.put("failStr", failStr);
		map.put("repeatNum", repeatNum);
		map.put("closeNum", closeNum);
		map.put("closeVal", closeVal);
		map.put("repeatVal", repeatVal);
		return map;
	}

	@Override
	public List getBankList() throws Exception {
		List list =financeMapper.getBankList();
		return list;
	}

	@Override
	public List getAllBankList() throws Exception {
		List list =financeMapper.getAllBankList();
		return list;
	}

    @Override
    public Map addDRExpensesSortExcel(List<ExpensesSort> list) throws Exception {
        Map returnMap = new HashMap();
        boolean flag = false;
        int successNum = 0,failureNum = 0,repeatNum = 0,closeNum = 0,errorNum = 0,levelNum=0,reptthisNum = 0;
        String closeVal = "",repeatVal = "",failStr = "",errorVal = "",levelVal = "",reptthisnameVal = "";
        if(list.size() != 0){
            String tn = "t_base_finance_expenses_sort";
            List levelList = showFilesLevelList(tn);
            if(levelList.size() != 0){
                for(ExpensesSort expensesSort : list){
                    if(StringUtils.isEmpty(expensesSort.getId())){
                        continue;
                    }
                    //根据档案级次信息根据编码获取本级编码
                    Map map = getObjectThisidByidCaseFilesLevel(tn,expensesSort.getId());
                    if(null != map && !map.isEmpty()){
                        String id = expensesSort.getId();
                        String thisid = (null != map.get("thisid")) ? map.get("thisid").toString() : "";
                        String pid = (null != map.get("pid")) ? map.get("pid").toString() : "";
                        try {
                            //判断商品分类是否重复
                            Map map1 = new HashMap();
                            map1.put("id",id);
                            ExpensesSort expensesSortInfo = financeMapper.getExpensesSortDetail(map1);
                            if(expensesSortInfo == null){//不重复
								//判断本机名称是否重复
								if(financeMapper.isUsedExpensesSortName(expensesSort.getThisname()) == 0){
									expensesSort.setThisid(thisid);
									expensesSort.setPid(pid);
									SysUser sysUser = getSysUser();
									expensesSort.setAdduserid(sysUser.getUserid());
									expensesSort.setAdddeptid(sysUser.getDepartmentid());
									expensesSort.setAdddeptname(sysUser.getDepartmentname());
									expensesSort.setAddusername(sysUser.getName());
									if(StringUtils.isEmpty(expensesSort.getPid())){
										expensesSort.setName(expensesSort.getThisname());
									}
									expensesSort.setState("1");
									flag = financeMapper.addExpensesSort(expensesSort) > 0;
									if(!flag){
										if(StringUtils.isNotEmpty(failStr)){
											failStr += "," + expensesSort.getId();
										}
										else{
											failStr = expensesSort.getId();
										}
										failureNum++;
									}
									else{
										successNum++;
									}
								}else{
									if(StringUtils.isEmpty(reptthisnameVal)){
										reptthisnameVal = expensesSort.getId();
									}else{
										reptthisnameVal += "," + expensesSort.getId();
									}
									reptthisNum++;
								}
                            }
                            else{
                                if("0".equals(expensesSortInfo.getState())){//禁用状态，不允许导入
                                    if(StringUtils.isEmpty(closeVal)){
                                        closeVal = expensesSortInfo.getId();
                                    }
                                    else{
                                        closeVal += "," + expensesSortInfo.getId();
                                    }
                                    closeNum++;
                                }
                                else{
                                    if(StringUtils.isEmpty(repeatVal)){
                                        repeatVal = expensesSortInfo.getId();
                                    }
                                    else{
                                        repeatVal += "," + expensesSortInfo.getId();
                                    }
                                    repeatNum++;
                                }
                            }
                        } catch (Exception e) {
                            if(StringUtils.isEmpty(errorVal)){
                                errorVal = id;
                            }
                            else{
                                errorVal += "," + id;
                            }
                            errorNum++;
                            continue;
                        }
                    }else{
                        levelNum++;
                        if(StringUtils.isNotEmpty(levelVal)){
                            levelVal += "," + expensesSort.getId();
                        }
                        else{
                            levelVal = expensesSort.getId();
                        }
                    }
                }
                //末级标志
                isTreeLeaf();
                //名称
                List<ExpensesSort> nonameList = financeMapper.getExpensesSortWithoutName();
                if(nonameList.size() != 0){
                    for(ExpensesSort nonameES : nonameList){
                        ExpensesSort pES = getExpensesSortDetail(nonameES.getPid());
                        if(null != pES){
                            nonameES.setOldId(nonameES.getId());
                            String name = "";
                            if(StringUtils.isEmpty(pES.getName())){
                                name = nonameES.getThisname();
                            }else{
                                name = pES.getName() + "/" + nonameES.getThisname();
                            }
                            if(StringUtils.isNotEmpty(pES.getName())){
                                nonameES.setName(name);
                            }else{
                                nonameES.setName(pES.getThisname());
                            }
                            financeMapper.editExpensesSort(nonameES);
                        }
                    }
                }
            }else{
                returnMap.put("nolevel", true);
            }
        }
        returnMap.put("flag", flag);
        returnMap.put("success", successNum);
        returnMap.put("failure", failureNum);
        returnMap.put("failStr", failStr);
        returnMap.put("repeatNum", repeatNum);
        returnMap.put("repeatVal", repeatVal);
        returnMap.put("closeNum", closeNum);
        returnMap.put("closeVal", closeVal);
        returnMap.put("errorNum", errorNum);
        returnMap.put("errorVal", errorVal);
        returnMap.put("levelNum", levelNum);
        returnMap.put("levelVal", levelVal);
		returnMap.put("reptthisNum", reptthisNum);
		returnMap.put("reptthisnameVal", reptthisnameVal);
        return returnMap;
    }

}

