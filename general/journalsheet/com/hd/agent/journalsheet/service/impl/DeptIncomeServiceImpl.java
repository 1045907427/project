/**
 * @(#)DeptIncomeServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年11月21日 chenwei 创建版本
 */
package com.hd.agent.journalsheet.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.account.model.BankAmountOthers;
import org.apache.commons.lang.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.service.IAccountForOAService;
import com.hd.agent.basefiles.dao.DepartMentMapper;
import com.hd.agent.basefiles.model.Brand;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.MeteringUnit;
import com.hd.agent.basefiles.model.Subject;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.journalsheet.dao.DeptIncomeMapper;
import com.hd.agent.journalsheet.model.DeptIncome;
import com.hd.agent.journalsheet.model.DeptIncomeSupplier;
import com.hd.agent.journalsheet.service.IDeptIncomeService;
import com.hd.agent.report.service.ISalesReportService;

/**
 * 部门收入service实现类
 * 
 * @author chenwei
 */
public class DeptIncomeServiceImpl extends BaseFilesServiceImpl implements
		IDeptIncomeService {
	/**
	 * 部门收入dao
	 */
	private DeptIncomeMapper deptIncomeMapper;
	
	/**
	 * 销售报表接口
	 */
	private ISalesReportService salesReportService; 
	/**
	 * 银行账户财务接口
	 */
	private IAccountForOAService accountForOAService;
	public DeptIncomeMapper getDeptIncomeMapper() {
		return deptIncomeMapper;
	}

	public void setDeptIncomeMapper(DeptIncomeMapper deptIncomeMapper) {
		this.deptIncomeMapper = deptIncomeMapper;
	}
	
	public ISalesReportService getSalesReportService() {
		return salesReportService;
	}

	public void setSalesReportService(ISalesReportService salesReportService) {
		this.salesReportService = salesReportService;
	}

	public IAccountForOAService getAccountForOAService() {
		return accountForOAService;
	}

	public void setAccountForOAService(IAccountForOAService accountForOAService) {
		this.accountForOAService = accountForOAService;
	}

	/**
	 * 部门
	 */
	private DepartMentMapper departMentMapper;

	public DepartMentMapper getDepartMentMapper() {
		return departMentMapper;
	}

	public void setDepartMentMapper(DepartMentMapper departMentMapper) {
		this.departMentMapper = departMentMapper;
	}

	@Override
	public boolean addDeptIncome(DeptIncome deptIncome)
			throws Exception {
		if (isAutoCreate("t_js_dept_income")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(deptIncome, "t_js_dept_income");
			deptIncome.setId(id);
		}else{
			deptIncome.setId(CommonUtils.getDataNumberWithRand());
		}
		SysUser sysUser = getSysUser();
		deptIncome.setAdduserid(sysUser.getUserid());
		deptIncome.setAddusername(sysUser.getName());
		if(StringUtils.isEmpty(deptIncome.getBusinessdate())){
			deptIncome.setBusinessdate(CommonUtils.getTodayDataStr());
		}
		String[] date = deptIncome.getBusinessdate().split("-");
		deptIncome.setYear(Integer.parseInt(date[0]));
		deptIncome.setMonth(Integer.parseInt(date[1]));
		int i = deptIncomeMapper.addDeptIncome(deptIncome);
		return i>0;
	}

	@Override
	public PageData showDeptIncomeList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_js_dept_income", null);
		pageMap.setDataSql(dataSql);
		List<DeptIncome> list = deptIncomeMapper.showDeptIncomeList(pageMap);
		for(DeptIncome deptIncome : list){
			if(StringUtils.isNotEmpty(deptIncome.getDeptid())){
				DepartMent departMent = getDepartmentByDeptid(deptIncome.getDeptid());
				if(null!=departMent){
					deptIncome.setDeptname(departMent.getName());
				}
			}
			if(StringUtils.isNotEmpty(deptIncome.getCostsort())){
				Subject subject=getSubjectInfoById(deptIncome.getCostsort());
				if(null!=subject){
					deptIncome.setCostsortname(subject.getThisname());
				}
			}
			if(StringUtils.isNotEmpty(deptIncome.getSupplierid())){
				BuySupplier buySupplier=getSupplierInfoById(deptIncome.getSupplierid());
				if(null!=buySupplier){
					deptIncome.setSuppliername(buySupplier.getName());
				}
			}
			if(StringUtils.isNotEmpty(deptIncome.getBrandid())){
				Brand brand=getGoodsBrandByID(deptIncome.getBrandid());
				if(null!=brand){
					deptIncome.setBrandname(brand.getName());
				}
			}
			if(StringUtils.isNotEmpty(deptIncome.getUnitid())){
				MeteringUnit meteringUnit=getMeteringUnitById(deptIncome.getUnitid());
				if(null!=meteringUnit){
					deptIncome.setUnitname(meteringUnit.getName());
				}
			}
		}
		PageData pageData = new PageData(deptIncomeMapper.showDeptIncomeListCount(pageMap), list, pageMap);
		List<DeptIncome> footerList = new ArrayList<DeptIncome>();
		DeptIncome deptIncomeSum=deptIncomeMapper.showDeptIncomeListSum(pageMap);
		if(null!=deptIncomeSum){
			deptIncomeSum.setId("合计");
			footerList.add(deptIncomeSum);
		}
		pageData.setFooter(footerList);
		return pageData;
	}

	@Override
	public DeptIncome getDeptIncomeInfo(String id) throws Exception {
		DeptIncome deptIncome = deptIncomeMapper.getDeptIncomeInfo(id);
		if(null!=deptIncome){
			if(StringUtils.isNotEmpty(deptIncome.getSupplierid())){
				BuySupplier buySupplier=getSupplierInfoById(deptIncome.getSupplierid());
				if(null!=buySupplier){
					deptIncome.setSuppliername(buySupplier.getName());
				}
			}
		}
		return deptIncome;
	}

	@Override
	public boolean editDeptIncome(DeptIncome deptIncome)
			throws Exception {
		boolean flag = false;
		DeptIncome oldDeptIncome = getDeptIncomeInfo(deptIncome.getId());
		if(null!=oldDeptIncome &&("1".equals(oldDeptIncome.getStatus()) || "2".equals(oldDeptIncome.getStatus()))){
			SysUser sysUser = getSysUser();
			deptIncome.setModifytime(new Date());
			deptIncome.setModifyuserid(sysUser.getUserid());
			deptIncome.setModifyusername(sysUser.getName());
			if(StringUtils.isEmpty(deptIncome.getBusinessdate())){
				deptIncome.setBusinessdate(CommonUtils.getTodayDataStr());
			}
			String[] date = deptIncome.getBusinessdate().split("-");
			deptIncome.setYear(Integer.parseInt(date[0]));
			deptIncome.setMonth(Integer.parseInt(date[1]));
			int i = deptIncomeMapper.editDeptIncome(deptIncome);
			flag = i>0;
		}
		return flag;
	}

	@Override
	public boolean deleteDeptIncome(String id) throws Exception {
		boolean flag = false;
		DeptIncome oldDeptIncome = getDeptIncomeInfo(id);
		if(null!=oldDeptIncome &&("1".equals(oldDeptIncome.getStatus()) || "2".equals(oldDeptIncome.getStatus()))){
			int i = deptIncomeMapper.deleteDeptIncome(id);
			flag = i>0;
		}
		return flag;
	}

	@Override
	public boolean auditDeptIncome(String id) throws Exception {
		boolean flag = false;
		DeptIncome oldDeptIncome = getDeptIncomeInfo(id);
		if(null!=oldDeptIncome &&"2".equals(oldDeptIncome.getStatus())){
			SysUser sysUser = getSysUser();
			int i = deptIncomeMapper.auditDeptIncome(id, sysUser.getUserid(), sysUser.getName());
			flag = i>0;

			if(flag && StringUtils.isNotEmpty(oldDeptIncome.getBankid())){
				BankAmountOthers bankAmountOthers = new BankAmountOthers();
				//借贷类型 1借(收入)2贷(支出)
				bankAmountOthers.setLendtype("1");
				bankAmountOthers.setBankid(oldDeptIncome.getBankid());
				bankAmountOthers.setDeptid(oldDeptIncome.getDeptid());
				//单据类型0期初1收款单2付款单3日常费用支付4客户费用支付5个人借款6预付款7转账10收入录入
				bankAmountOthers.setBilltype("10");
				bankAmountOthers.setBillid(oldDeptIncome.getId());
				bankAmountOthers.setOaid(oldDeptIncome.getOaid());
				bankAmountOthers.setAmount(oldDeptIncome.getAmount());
				bankAmountOthers.setUpamount(oldDeptIncome.getUpamount());
				bankAmountOthers.setOppid("");
				bankAmountOthers.setOppname(oldDeptIncome.getOppname());
				bankAmountOthers.setOppbank(oldDeptIncome.getOppbank());
				bankAmountOthers.setOppbankno(oldDeptIncome.getOppbankno());
				bankAmountOthers.setInvoiceamount(BigDecimal.ZERO);
				bankAmountOthers.setInvoicedate("");
				bankAmountOthers.setInvoiceid("");
				bankAmountOthers.setInvoicetype("");
				bankAmountOthers.setRemark(oldDeptIncome.getRemark());
				
				bankAmountOthers.setAdduserid(sysUser.getUserid());
				bankAmountOthers.setAddusername(sysUser.getName());
				bankAmountOthers.setAdddeptid(sysUser.getDepartmentid());
				bankAmountOthers.setAdddeptname(sysUser.getDepartmentname());
				accountForOAService.addBankAmountOthers(bankAmountOthers);
			}

		}
		return flag;
	}

	@Override
	public boolean oppauditDeptIncome(String id) throws Exception {
		boolean flag = false;
		DeptIncome oldDeptIncome = getDeptIncomeInfo(id);
		if(null!=oldDeptIncome &&"3".equals(oldDeptIncome.getStatus())){
			SysUser sysUser = getSysUser();
			int i = deptIncomeMapper.oppauditDeptIncome(id, sysUser.getUserid(), sysUser.getName());
			flag = i>0;
			accountForOAService.delteBankAmountOthers(id);
		}
		return flag;
	}

	@Override
	public boolean updateDeptIncomeSettle(String year,String month) throws Exception {
		boolean flag = false;
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.parseInt(year));
		c.set(Calendar.MONTH, Integer.parseInt(month)-1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		//该月第一天日期
		String begindate = CommonUtils.dataToStr(c.getTime(), "yyyy-MM-dd");
		//该月最后一天日期
		String enddate = CommonUtils.dataToStr(CommonUtils.getCurrentMonthLastDate(c.getTime()), "yyyy-MM-dd");
		List<DepartMent> list = getBaseFilesDepartmentMapper().getPidDeptList();
		//删除历史供应商费用金额
		deptIncomeMapper.deleteDeptIncomeSupplier(year, month);
		for(DepartMent departMent : list){
			List<DeptIncome> costSortList = deptIncomeMapper.getDeptIncomeSumByDeptid(year, month, departMent.getId());
			//获取该部门下的供应商销售金额  没有去全部供应商的销售金额
			List<Map> supplierSalesAmountList = salesReportService.getSupplierSalesAmountByDetpid(begindate, enddate, departMent.getId());
			if(null==supplierSalesAmountList || supplierSalesAmountList.size()==0){
				supplierSalesAmountList = salesReportService.getSupplierSalesAmountByDetpid(begindate, enddate, null);
			}
			if(null!=supplierSalesAmountList && supplierSalesAmountList.size()>0){
				//计算销售总金额
				BigDecimal totalSalesAmount = BigDecimal.ZERO;
				for(Map map : supplierSalesAmountList){
					BigDecimal salesAmount = (BigDecimal) map.get("salesamount");
					totalSalesAmount = totalSalesAmount.add(salesAmount);
				}
				
				for(DeptIncome deptIncome : costSortList){
					BigDecimal totalCostAmount = deptIncome.getAmount();
					BigDecimal useCostAmount = BigDecimal.ZERO;
					int i = 0;
					//按销售金额 分摊到各供应商中去
					for(Map map : supplierSalesAmountList){
						i ++;
						String supplierid = (String) map.get("supplierid");
						BigDecimal salesAmount = (BigDecimal) map.get("salesamount");
						//供应商费用金额 = （供应商销售金额/总销售金额） * 该科目代垫金额
						BigDecimal supplierAmount = salesAmount.divide(totalSalesAmount,2,BigDecimal.ROUND_HALF_UP).multiply(totalCostAmount).setScale(2,BigDecimal.ROUND_HALF_UP);
						//最后一个费用金额= 费用总额- 已分配费用金额
						if(i==(supplierSalesAmountList.size())){
							supplierAmount = totalCostAmount.subtract(useCostAmount);
						}
						useCostAmount = useCostAmount.add(supplierAmount);
						//添加供应商收入金额
						DeptIncomeSupplier deptIncomeSupplier = new DeptIncomeSupplier();
						deptIncomeSupplier.setSupplierid(supplierid);
						deptIncomeSupplier.setAmount(supplierAmount);
						deptIncomeSupplier.setDeptid(deptIncome.getDeptid());
						deptIncomeSupplier.setCostsort(deptIncome.getCostsort());;
						deptIncomeSupplier.setMonth(Integer.parseInt(month));
						deptIncomeSupplier.setYear(Integer.parseInt(year));
						deptIncomeSupplier.setBusinessdate(begindate);
						deptIncomeMapper.addDeptIncomeSupplier(deptIncomeSupplier);
					}
				}
				flag = true;
			}
		}
		Map queryMap=new HashMap();
		queryMap.put("statusarr", "3,4");
		queryMap.put("theyear", year);
		queryMap.put("themonth", month);
		queryMap.put("notEmptySupplier", "1");
		List<DeptIncome> deptIncomeList=deptIncomeMapper.showDeptIncomeListBy(queryMap);
		for(DeptIncome item: deptIncomeList){
			if(StringUtils.isEmpty(item.getSupplierid())){
				continue;
			}
			//添加供应商收入金额
			DeptIncomeSupplier deptIncomeSupplier = new DeptIncomeSupplier();
			deptIncomeSupplier.setSupplierid(item.getSupplierid());
			deptIncomeSupplier.setAmount(item.getAmount());
			deptIncomeSupplier.setDeptid(item.getDeptid());
			deptIncomeSupplier.setCostsort(item.getCostsort());;
			deptIncomeSupplier.setMonth(Integer.parseInt(month));
			deptIncomeSupplier.setYear(Integer.parseInt(year));
			deptIncomeSupplier.setBusinessdate(item.getBusinessdate());
			deptIncomeMapper.addDeptIncomeSupplier(deptIncomeSupplier);
			flag=true;
		}
		//关闭该月份的部门收入单
		if(flag){
			deptIncomeMapper.closeDeptIncome(year, month);
		}
		return flag;
	}

	@Override
	public boolean addAndAuditDeptIncome(DeptIncome deptIncome)
			throws Exception {
		boolean flag = false;
		if (isAutoCreate("t_js_dept_income")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(deptIncome, "t_js_dept_income");
			deptIncome.setId(id);
		}else{
			deptIncome.setId("BMFY-"+CommonUtils.getDataNumber1());
		}
		SysUser sysUser = getSysUser();
		if(StringUtils.isEmpty(deptIncome.getAdduserid())){
			deptIncome.setAdduserid(sysUser.getUserid());
			deptIncome.setAddusername(sysUser.getName());
		}
		if(StringUtils.isEmpty(deptIncome.getBusinessdate())){
			deptIncome.setBusinessdate(CommonUtils.getTodayDataStr());
		}
		String[] date = deptIncome.getBusinessdate().split("-");
		deptIncome.setYear(Integer.parseInt(date[0]));
		deptIncome.setMonth(Integer.parseInt(date[1]));
		deptIncome.setStatus("2");
		int i = deptIncomeMapper.addDeptIncome(deptIncome);
		if(i>0){
			flag = auditDeptIncome(deptIncome.getId());
		}
		return flag;
	}
	@Override
	public PageData getDeptIncomeReportPageData(PageMap pageMap) throws Exception{
		Map queryMap = pageMap.getCondition();
		String sql = getDataAccessRule("t_js_dept_income","t1"); //数据权限
		queryMap.put("dataSql", sql);
		List<Map> list = deptIncomeMapper.getDeptIncomeReportPageList(queryMap);
		List dataList = new ArrayList();
		if(null!=list && list.size()>0){
			for(Map map : list){
				String id = (String) map.get("id");
				String pid = (String) map.get("pid");
				String leaf = (String) map.get("leaf");
				if(StringUtils.isEmpty(pid)){
					if("0".equals(leaf)){
						List childrenList = getDeptIncomeSubjectDataList(id,queryMap);
						List childList = getDeptChildMap(list,id,queryMap);
						if(childrenList.size()>0){
							childList.addAll(childrenList);
						}
						if(childList.size()>0){
							map.put("rid", CommonUtils.getRandomWithTime());
							map.put("children", childList);
							map.put("state","closed");
						}
					}else if("1".equals(leaf)){
						List childrenList = getDeptIncomeSubjectDataList(id,queryMap);
						if(childrenList.size()>0){
							map.put("rid", CommonUtils.getRandomWithTime());
							map.put("children", childrenList);
							map.put("state","closed");
						}
					}
					dataList.add(map);
				}
			}
		}
		List footerList = new ArrayList();
		Map sumMap = deptIncomeMapper.getDeptIncomeReportPageSums(queryMap);
		if(null!=sumMap){
			sumMap.put("name","合计");
			footerList.add(sumMap);
		}
		PageData pageData = new PageData(dataList.size(),dataList,pageMap);
		pageData.setFooter(footerList);
		return pageData;
	}
	@Override
	public List getDeptIncomeReportExportData(Map queryMap) throws Exception{
		String sql = getDataAccessRule("t_js_dept_income","t1"); //数据权限
		queryMap.put("dataSql", sql);
		List<Map> list = deptIncomeMapper.getDeptIncomeReportPageList(queryMap);
		if(null!=list && list.size()>0){
			for(Map map : list){
				String id = (String) map.get("id");
				String pid = (String) map.get("pid");
				String name = (String) map.get("name");
				List<Map> childrenList = deptIncomeMapper.getDeptIncomeSubjectListData(id,queryMap);
				if(childrenList.size()>0){
					map.put("children", childrenList);
				}
			}
		}
		return list;
	}
	@Override
	public List getDeptIncomeReportExportSumData(Map queryMap) throws Exception{
		String sql = getDataAccessRule("t_js_dept_income","t1"); //数据权限
		queryMap.put("dataSql", sql);
		List footerList = new ArrayList();
		Map sumMap = deptIncomeMapper.getDeptIncomeReportPageSums(queryMap);
		if(null!=sumMap){
			sumMap.put("name","合计");
			footerList.add(sumMap);
		}
		return footerList;
	}
	/**
	 * 递归获取部门级次数据
	 * @param list
	 * @param id
	 * @param queryMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月13日
	 */
	private List<Map> getDeptChildMap(List<Map> list,String id,Map queryMap) throws Exception{
		List<Map> dataList = new ArrayList<Map>();
		for(Map map : list){
			String did = (String) map.get("id");
			String pid = (String) map.get("pid");
			String leaf = (String) map.get("leaf");
			if(id.equals(pid)){
				if("0".equals(leaf)){
					List childrenList = getDeptChildMap(list,did,queryMap);
					if(childrenList.size()>0){
						map.put("rid", CommonUtils.getRandomWithTime());
						map.put("children", childrenList);
						map.put("state","closed");
					}
				}else if("1".equals(leaf)){
					List childrenList = getDeptIncomeSubjectDataList(did,queryMap);
					if(childrenList.size()>0){
						map.put("rid", CommonUtils.getRandomWithTime());
						map.put("children", childrenList);
						map.put("state","closed");
					}
				}
				dataList.add(map);
			}
		}
		return dataList;
	}
	/**
	 * 获取部门下科目费用金额
	 * @param deptid
	 * @param queryMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月13日
	 */
	private List<Map> getDeptIncomeSubjectDataList(String deptid,Map queryMap) throws Exception{
		List<Map> dataList = deptIncomeMapper.getDeptIncomeSubjectList(deptid,queryMap);
		List list = new ArrayList();
		for(Map map : dataList){
			String sid = (String) map.get("sid");
			String pid = (String) map.get("pid");
			String leaf = (String) map.get("leaf");
			BigDecimal subamount = BigDecimal.ZERO;
			if(map.containsKey("subamount")){
				subamount = (BigDecimal) map.get(subamount);
			}
			if(StringUtils.isEmpty(pid)){
				List childrenList = getDeptIncomeSubjectChildList(dataList,sid);
				if(childrenList.size()>0){
					map.put("children", childrenList);
					map.put("state","closed");
				}
				map.put("rid", CommonUtils.getRandomWithTime());
				map.put("iconCls","icon-annex");
				list.add(map);
			}
		}
		return list;
	}
	/**
	 * 递归组装收入科目数据
	 * @param list
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月13日
	 */
	private List<Map> getDeptIncomeSubjectChildList(List<Map> list,String id) throws Exception{
		List<Map> dataList = new ArrayList<Map>();
		for(Map map : list){
			String sid = (String) map.get("sid");
			String pid = (String) map.get("pid");
			String leaf = (String) map.get("leaf");
			if(id.equals(pid)){
				List childrenList = getDeptIncomeSubjectChildList(list,sid);
				if(childrenList.size()>0){
					map.put("children", childrenList);
					map.put("state","closed");
				}
				map.put("rid", CommonUtils.getRandomWithTime());
				map.put("iconCls","icon-annex");
				dataList.add(map);
			}
		}
		return dataList;
	}
	@Override
	public PageData getDeptIncomeYearReportPageData(PageMap pageMap) throws Exception{
		String sql = getDataAccessRule("t_js_dept_income","d"); //数据权限
		pageMap.setDataSql(sql);
		Map condition=pageMap.getCondition();
		boolean showPageRowId=false;
		int intPageRowId=1;
		String isExportData=(String)condition.get("isExportData");
		if("true".equals(isExportData)){
			condition.put("isPageflag", "true");
			condition.put("showOrderOnly", "true");
		}
		String isPageflag=(String)condition.get("isPageflag");
		if(null==isPageflag || !"true".equals(isPageflag)){
			showPageRowId=true;
		}
		if(condition.containsKey("groupBycols")){
			condition.remove("groupBycols");
		}
		String groupcols=(String)condition.get("groupcols");
		if(null!=groupcols && !"".equals(groupcols.trim())){
			String[] groupcolsArr=groupcols.trim().split(",");
			List<String> groupcolsList=new ArrayList<String>();
			for(int i=0;i<groupcolsArr.length;i++){
				if(null!=groupcolsArr[i] && !"".equals(groupcolsArr[i].trim()) ){
					groupcolsList.add(groupcolsArr[i]);
				}
			}
			if(groupcolsList.size()>0){
				groupcols=StringUtils.join(groupcolsList, ",");
				if(null!=groupcols && !"".equals(groupcols.trim())){
					condition.put("groupBycols",groupcols.trim());					
				}
			}
		}
		String sort=(String)condition.get("sort");
		String order=(String)condition.get("order");
		if( (null==sort || "".equals(sort.trim())) ||
		    (null==order || "".equals(order.trim())) && StringUtils.isEmpty(pageMap.getOrderSql()) ){
			pageMap.setOrderSql(" year asc ,deptid asc,subjectid asc  ");
		}
		StringBuilder sb=new StringBuilder();
		StringBuilder ifSumSb=new StringBuilder();
		StringBuilder onlySumSb=new StringBuilder();
		for(int i=1;i<13;i++){
			String month=i+"";
			if(i<10){
				month="0"+i;
			}
			
			sb.append(",month_");
			sb.append(month);
			
			ifSumSb.append(",SUM(if(month='");
			ifSumSb.append(month);
			ifSumSb.append("',amount,0.000000))");
			ifSumSb.append(" AS month_");
			ifSumSb.append(month);
			
			onlySumSb.append(",SUM(month_");
			onlySumSb.append(month);
			onlySumSb.append(") AS month_");
			onlySumSb.append(month);
		}
		if(sb.length()>0){
			condition.put("dyncSubjectColumn", sb.toString());
			condition.put("dyncSubjectIfSumColumn", ifSumSb.toString());
			condition.put("dyncSubjectOnlySumColumn", onlySumSb.toString());
		}else {
			if(condition.containsKey("dyncSubjectColumn")){
				condition.remove("dyncSubjectColumn");
			}
			if(condition.containsKey("dyncSubjectIfSumColumn")){
				condition.remove("dyncSubjectIfSumColumn");
			}
			if(condition.containsKey("dyncSubjectOnlySumColumn")){
				condition.remove("dyncSubjectOnlySumColumn");
			}
		}
		
		List<Map> list = deptIncomeMapper.getDeptIncomeYearReportPageList(pageMap);
		if(list.size() != 0){
			String tmpstr="";
			for(Map map :list){
				tmpstr=(String)map.get("deptid");
				if(null!=tmpstr && !"".equals(tmpstr.trim())){//所属部门
					DepartMent departMent = departMentMapper.getDepartmentInfo(tmpstr.trim());
					if(null!=departMent){
						map.put("deptname",departMent.getName());
					}
				}
				if(showPageRowId){
					map.put("pageRowId", intPageRowId);
					intPageRowId=intPageRowId+1;
				}
			}
		}	
		List<Map> footerList = new ArrayList<Map>();
		condition.put("isSumAll", "true");
		Map sumMap = deptIncomeMapper.getDeptIncomeYearReportPageSums(pageMap);
		if(null!=sumMap){
			sumMap.put("year","");
			sumMap.put("subjectname","合计");
			footerList.add(sumMap);
		}
		int pageCount=deptIncomeMapper.getDeptIncomeYearReportPageCount(pageMap);
		PageData pageData = new PageData(pageCount,list,pageMap);
		pageData.setFooter(footerList);
		return pageData;
	}
	
	/**
	 * 部门分供应商费用明细报表分页数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public PageData getDeptSupplierIncomeReportPageData(PageMap pageMap) throws Exception{
		String sql = getDataAccessRule("t_js_dept_income_supplier","d"); //数据权限
		pageMap.setDataSql(sql);
		Map condition=pageMap.getCondition();
		boolean showPageRowId=false;
		int intPageRowId=1;
		String isExportData=(String)condition.get("isExportData");
		if("true".equals(isExportData)){
			condition.put("isPageflag", "true");
			condition.put("showOrderOnly", "true");
		}
		String isPageflag=(String)condition.get("isPageflag");
		if(null==isPageflag || !"true".equals(isPageflag)){
			showPageRowId=true;
		}
		if(condition.containsKey("groupBycols")){
			condition.remove("groupBycols");
		}
		String groupcols=(String)condition.get("groupcols");
		if(null!=groupcols && !"".equals(groupcols.trim())){
			String[] groupcolsArr=groupcols.trim().split(",");
			List<String> groupcolsList=new ArrayList<String>();
			for(int i=0;i<groupcolsArr.length;i++){
				if(null!=groupcolsArr[i] && !"".equals(groupcolsArr[i].trim()) ){
					groupcolsList.add(groupcolsArr[i]);
				}
			}
			if(groupcolsList.size()>0){
				groupcols=StringUtils.join(groupcolsList, ",");
				if(null!=groupcols && !"".equals(groupcols.trim())){
					condition.put("groupBycols",groupcols.trim());					
				}
			}
		}
		String sort=(String)condition.get("sort");
		String order=(String)condition.get("order");
		if( (null==sort || "".equals(sort.trim())) ||
		    (null==order || "".equals(order.trim())) && StringUtils.isEmpty(pageMap.getOrderSql()) ){
			pageMap.setOrderSql(" year asc ,supplierid asc ,deptid asc,subjectid asc ");
		}
		StringBuilder sb=new StringBuilder();
		StringBuilder ifSumSb=new StringBuilder();
		StringBuilder onlySumSb=new StringBuilder();
		for(int i=1;i<13;i++){
			String month=i+"";
			if(i<10){
				month="0"+i;
			}
			
			sb.append(",month_");
			sb.append(month);
			
			ifSumSb.append(",SUM(if(month='");
			ifSumSb.append(month);
			ifSumSb.append("',amount,0.000000))");
			ifSumSb.append(" AS month_");
			ifSumSb.append(month);
			
			onlySumSb.append(",SUM(month_");
			onlySumSb.append(month);
			onlySumSb.append(") AS month_");
			onlySumSb.append(month);
		}
		if(sb.length()>0){
			condition.put("dyncSubjectColumn", sb.toString());
			condition.put("dyncSubjectIfSumColumn", ifSumSb.toString());
			condition.put("dyncSubjectOnlySumColumn", onlySumSb.toString());
		}else {
			if(condition.containsKey("dyncSubjectColumn")){
				condition.remove("dyncSubjectColumn");
			}
			if(condition.containsKey("dyncSubjectIfSumColumn")){
				condition.remove("dyncSubjectIfSumColumn");
			}
			if(condition.containsKey("dyncSubjectOnlySumColumn")){
				condition.remove("dyncSubjectOnlySumColumn");
			}
		}
		
		List<Map> list = deptIncomeMapper.getDeptSupplierIncomeReportPageList(pageMap);
		if(list.size() != 0){
			String tmpstr="";
			for(Map map :list){
				tmpstr=(String)map.get("deptid");
				if(null!=tmpstr && !"".equals(tmpstr.trim())){//所属部门
					DepartMent departMent = departMentMapper.getDepartmentInfo(tmpstr.trim());
					if(null!=departMent){
						map.put("deptname",departMent.getName());
					}
				}
				tmpstr=(String)map.get("supplierid");
				if(null!=tmpstr && !"".equals(tmpstr.trim())){//所属供应商
					BuySupplier buySupplier = getSupplierInfoById(tmpstr.trim());
					if(null!=buySupplier){
						map.put("suppliername",buySupplier.getName());
					}
				}
				if(showPageRowId){
					map.put("pageRowId", intPageRowId);
					intPageRowId=intPageRowId+1;
				}
			}
		}	
		List<Map> footerList = new ArrayList<Map>();
		condition.put("isSumAll", "true");
		Map sumMap = deptIncomeMapper.getDeptSupplierIncomeReportPageSums(pageMap);
		if(null!=sumMap){
			sumMap.put("year","");
			sumMap.put("supplierid","");
			sumMap.put("suppliername","合计");
			footerList.add(sumMap);
		}
		int pageCount=deptIncomeMapper.getDeptSupplierIncomeReportPageCount(pageMap);
		PageData pageData = new PageData(pageCount,list,pageMap);
		pageData.setFooter(footerList);
		return pageData;
	}
	@Override
	public PageData getSupplierSubjectDetailData(PageMap pageMap)
			throws Exception {
		String sql = getDataAccessRule("t_js_dept_income_supplier","d"); //数据权限
		pageMap.setDataSql(sql);
		Map condition=pageMap.getCondition();
		StringBuilder sb=new StringBuilder();
		StringBuilder ifSumSb=new StringBuilder();
		StringBuilder onlySumSb=new StringBuilder();
		for(int i=1;i<13;i++){
			String month=i+"";
			if(i<10){
				month="0"+i;
			}
			
			sb.append(",month_");
			sb.append(month);
			
			ifSumSb.append(",SUM(if(month='");
			ifSumSb.append(month);
			ifSumSb.append("',amount,0.000000))");
			ifSumSb.append(" AS month_");
			ifSumSb.append(month);
			
			onlySumSb.append(",SUM(month_");
			onlySumSb.append(month);
			onlySumSb.append(") AS month_");
			onlySumSb.append(month);
		}
		if(sb.length()>0){
			condition.put("dyncSubjectColumn", sb.toString());
			condition.put("dyncSubjectIfSumColumn", ifSumSb.toString());
			condition.put("dyncSubjectOnlySumColumn", onlySumSb.toString());
		}else {
			if(condition.containsKey("dyncSubjectColumn")){
				condition.remove("dyncSubjectColumn");
			}
			if(condition.containsKey("dyncSubjectIfSumColumn")){
				condition.remove("dyncSubjectIfSumColumn");
			}
			if(condition.containsKey("dyncSubjectOnlySumColumn")){
				condition.remove("dyncSubjectOnlySumColumn");
			}
		}
		
		List<Map> list = deptIncomeMapper.getSupplierSubjectDetailData(pageMap);
		List<Map> footerList = new ArrayList<Map>();
		condition.put("isSumAll", "true");
		Map sumMap = deptIncomeMapper.getDeptSupplierIncomeReportPageSums(pageMap);
		if(null!=sumMap){
			sumMap.put("year","");
			sumMap.put("id","");
			sumMap.put("subjectname","合计");
			footerList.add(sumMap);
		}
		PageData pageData = new PageData(list.size(),list,pageMap);
		pageData.setFooter(footerList);
		return pageData;
	}
	/**
	 * 部门分供应商费用明细报表分页数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public PageData getDeptSupplierIncomeYearReportPageData(PageMap pageMap) throws Exception{
		String sql = getDataAccessRule("t_js_dept_income_supplier","d"); //数据权限
		pageMap.setDataSql(sql);
		Map condition=pageMap.getCondition();
		boolean showPageRowId=false;
		int intPageRowId=1;
		String isExportData=(String)condition.get("isExportData");
		if("true".equals(isExportData)){
			condition.put("isPageflag", "true");
			condition.put("showOrderOnly", "true");
		}
		String isPageflag=(String)condition.get("isPageflag");
		if(null==isPageflag || !"true".equals(isPageflag)){
			showPageRowId=true;
		}
		if(condition.containsKey("groupBycols")){
			condition.remove("groupBycols");
		}
		String groupcols=(String)condition.get("groupcols");
		if(null!=groupcols && !"".equals(groupcols.trim())){
			String[] groupcolsArr=groupcols.trim().split(",");
			List<String> groupcolsList=new ArrayList<String>();
			for(int i=0;i<groupcolsArr.length;i++){
				if(null!=groupcolsArr[i] && !"".equals(groupcolsArr[i].trim()) ){
					groupcolsList.add(groupcolsArr[i]);
				}
			}
			if(groupcolsList.size()>0){
				groupcols=StringUtils.join(groupcolsList, ",");
				if(null!=groupcols && !"".equals(groupcols.trim())){
					condition.put("groupBycols",groupcols.trim());					
				}
			}
		}
		String sort=(String)condition.get("sort");
		String order=(String)condition.get("order");
		if( (null==sort || "".equals(sort.trim())) ||
		    (null==order || "".equals(order.trim())) && StringUtils.isEmpty(pageMap.getOrderSql()) ){
			pageMap.setOrderSql(" year asc ,supplierid asc ,deptid asc,subjectid asc ");
		}
		StringBuilder sb=new StringBuilder();
		StringBuilder ifSumSb=new StringBuilder();
		StringBuilder onlySumSb=new StringBuilder();
		for(int i=1;i<13;i++){
			String month=i+"";
			if(i<10){
				month="0"+i;
			}
			
			sb.append(",month_");
			sb.append(month);
			
			ifSumSb.append(",SUM(if(month='");
			ifSumSb.append(month);
			ifSumSb.append("',amount,0.000000))");
			ifSumSb.append(" AS month_");
			ifSumSb.append(month);
			
			onlySumSb.append(",SUM(month_");
			onlySumSb.append(month);
			onlySumSb.append(") AS month_");
			onlySumSb.append(month);
		}
		if(sb.length()>0){
			condition.put("dyncSubjectColumn", sb.toString());
			condition.put("dyncSubjectIfSumColumn", ifSumSb.toString());
			condition.put("dyncSubjectOnlySumColumn", onlySumSb.toString());
		}else {
			if(condition.containsKey("dyncSubjectColumn")){
				condition.remove("dyncSubjectColumn");
			}
			if(condition.containsKey("dyncSubjectIfSumColumn")){
				condition.remove("dyncSubjectIfSumColumn");
			}
			if(condition.containsKey("dyncSubjectOnlySumColumn")){
				condition.remove("dyncSubjectOnlySumColumn");
			}
		}
		
		List<Map> list = deptIncomeMapper.getDeptSupplierIncomeReportPageList(pageMap);
		if(list.size() != 0){
			String tmpstr="";
			for(Map map :list){
				tmpstr=(String)map.get("deptid");
				if(null!=tmpstr && !"".equals(tmpstr.trim())){//所属部门
					DepartMent departMent = departMentMapper.getDepartmentInfo(tmpstr.trim());
					if(null!=departMent){
						map.put("deptname",departMent.getName());
					}
				}
				tmpstr=(String)map.get("supplierid");
				if(null!=tmpstr && !"".equals(tmpstr.trim())){//所属供应商
					BuySupplier buySupplier = getSupplierInfoById(tmpstr.trim());
					if(null!=buySupplier){
						map.put("suppliername",buySupplier.getName());
					}
				}
				if(showPageRowId){
					map.put("pageRowId", intPageRowId);
					intPageRowId=intPageRowId+1;
				}
			}
		}	
		List<Map> footerList = new ArrayList<Map>();
		condition.put("isSumAll", "true");
		Map sumMap = deptIncomeMapper.getDeptSupplierIncomeReportPageSums(pageMap);
		if(null!=sumMap){
			sumMap.put("year","");
			sumMap.put("subjectname","合计");
			footerList.add(sumMap);
		}
		int pageCount=deptIncomeMapper.getDeptSupplierIncomeReportPageCount(pageMap);
		PageData pageData = new PageData(pageCount,list,pageMap);
		pageData.setFooter(footerList);
		return pageData;
	}
	@Override
	public List showSupplierSubjectReportData(PageMap pageMap) throws Exception {
		String sql = getDataAccessRule("t_js_dept_income_supplier","d"); //数据权限
		pageMap.setDataSql(sql);
		Map condition=pageMap.getCondition();
		if(condition.containsKey("groupBycols")){
			condition.remove("groupBycols");
		}
		String groupcols=(String)condition.get("groupcols");
		if(null!=groupcols && !"".equals(groupcols.trim())){
			String[] groupcolsArr=groupcols.trim().split(",");
			List<String> groupcolsList=new ArrayList<String>();
			for(int i=0;i<groupcolsArr.length;i++){
				if(null!=groupcolsArr[i] && !"".equals(groupcolsArr[i].trim()) ){
					groupcolsList.add(groupcolsArr[i]);
				}
			}
			if(groupcolsList.size()>0){
				groupcols=StringUtils.join(groupcolsList, ",");
				if(null!=groupcols && !"".equals(groupcols.trim())){
					condition.put("groupBycols",groupcols.trim());					
				}
			}
		}
		String sort=(String)condition.get("sort");
		String order=(String)condition.get("order");
		if( (null==sort || "".equals(sort.trim())) ||
		    (null==order || "".equals(order.trim())) && StringUtils.isEmpty(pageMap.getOrderSql()) ){
			pageMap.setOrderSql(" year asc ,supplierid asc ,deptid asc,subjectid asc ");
		}
		StringBuilder sb=new StringBuilder();
		StringBuilder ifSumSb=new StringBuilder();
		StringBuilder onlySumSb=new StringBuilder();
		for(int i=1;i<13;i++){
			String month=i+"";
			if(i<10){
				month="0"+i;
			}
			
			sb.append(",month_");
			sb.append(month);
			
			ifSumSb.append(",SUM(if(month='");
			ifSumSb.append(month);
			ifSumSb.append("',amount,0.000000))");
			ifSumSb.append(" AS month_");
			ifSumSb.append(month);
			
			onlySumSb.append(",SUM(month_");
			onlySumSb.append(month);
			onlySumSb.append(") AS month_");
			onlySumSb.append(month);
		}
		if(sb.length()>0){
			condition.put("dyncSubjectColumn", sb.toString());
			condition.put("dyncSubjectIfSumColumn", ifSumSb.toString());
			condition.put("dyncSubjectOnlySumColumn", onlySumSb.toString());
		}else {
			if(condition.containsKey("dyncSubjectColumn")){
				condition.remove("dyncSubjectColumn");
			}
			if(condition.containsKey("dyncSubjectIfSumColumn")){
				condition.remove("dyncSubjectIfSumColumn");
			}
			if(condition.containsKey("dyncSubjectOnlySumColumn")){
				condition.remove("dyncSubjectOnlySumColumn");
			}
		}
		
		List<Map> list = deptIncomeMapper.getSupplierSubjectReportList(pageMap);
		List dataList = new ArrayList();
		for(Map map : list){
			String id = (String) map.get("id");
			String pid = (String) map.get("pid");
			if(StringUtils.isEmpty(pid)){
				List childList = getSupplierSubjectChildList(list, id);
				if(null!=childList && childList.size()>0){
					map.put("children", childList);
					map.put("state","closed");
				}
				dataList.add(map);
			}
		}
		return dataList;
	}
	/**
	 * 递归获取供应商收入科目的子节点
	 * @param list
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月14日
	 */
	private List getSupplierSubjectChildList(List<Map> list,String id) throws Exception{
		List dataList = new ArrayList();
		for(Map map : list){
			String sid = (String) map.get("id");
			String pid = (String) map.get("pid");
			if(id.equals(pid)){
				List childList = getSupplierSubjectChildList(list, sid);
				if(null!=childList && childList.size()>0){
					map.put("children", childList);
					map.put("state","closed");
				}
				dataList.add(map);
			}
		}
		return dataList;
	}
	@Override
	public List<DeptIncome> selectDeptIncomeByOaid(String oaid)
			throws Exception {

		return deptIncomeMapper.selectDeptIncomeByOaid(oaid);
	}

	@Override
	public boolean rollbackAndAuditDeptIncome(DeptIncome cost)
			throws Exception {
		boolean flag = false;
		
		//**********************************************************************
		// 是否已经rollback 判断
		//**********************************************************************
		List list = deptIncomeMapper.selectDeptIncomeByOaid(cost.getOaid());
		
		// 部门收入存在2n条记录，说明已经生成相反的部门收入
		if(list.size() % 2 == 0) {
			
			return true;
		}
		
		//**********************************************************************
		// rollback
		//**********************************************************************
		if (isAutoCreate("t_js_dept_income")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(cost, "t_js_dept_income");
			cost.setId(id);
		}else{
			cost.setId("BMFY-"+CommonUtils.getDataNumber1());
		}
		SysUser sysUser = getSysUser();
		if(StringUtils.isEmpty(cost.getAdduserid())){
			cost.setAdduserid(sysUser.getUserid());
			cost.setAddusername(sysUser.getName());
		}
		if(StringUtils.isEmpty(cost.getBusinessdate())){
			cost.setBusinessdate(CommonUtils.getTodayDataStr());
		}
		String[] date = cost.getBusinessdate().split("-");
		cost.setYear(Integer.parseInt(date[0]));
		cost.setMonth(Integer.parseInt(date[1]));
		cost.setStatus("2");
		int i = deptIncomeMapper.addDeptIncome(cost);
		if(i > 0){

			DeptIncome oldDeptIncome = getDeptIncomeInfo(cost.getId());
			if(null != oldDeptIncome && "2".equals(oldDeptIncome.getStatus())){

				int ret = deptIncomeMapper.auditDeptIncome(cost.getId(), sysUser.getUserid(), sysUser.getName());
				flag = ret > 0;
				if(flag && StringUtils.isNotEmpty(oldDeptIncome.getBankid())){
					/*
					BankAmountOthers bankAmountOthers = new BankAmountOthers();
					//借贷类型 1借(收入)2贷(支出)
					bankAmountOthers.setLendtype("1");
					bankAmountOthers.setBankid(oldDeptIncome.getBankid());
					bankAmountOthers.setDeptid(oldDeptIncome.getDeptid());
					//单据类型0期初1收款单2付款单3日常费用支付4客户费用支付5个人借款6预付款7转账
					bankAmountOthers.setBilltype("3");
					bankAmountOthers.setBillid(oldDeptIncome.getId());
					bankAmountOthers.setOaid(oldDeptIncome.getOaid());
					bankAmountOthers.setAmount(oldDeptIncome.getAmount() == null ? BigDecimal.ZERO : oldDeptIncome.getAmount().negate());
					bankAmountOthers.setUpamount(oldDeptIncome.getUpamount());
					bankAmountOthers.setOppid("");
					bankAmountOthers.setOppname(oldDeptIncome.getOppname());
					bankAmountOthers.setOppbank(oldDeptIncome.getOppbank());
					bankAmountOthers.setOppbankno(oldDeptIncome.getOppbankno());
					bankAmountOthers.setInvoiceamount(BigDecimal.ZERO);
					bankAmountOthers.setInvoicedate("");
					bankAmountOthers.setInvoiceid("");
					bankAmountOthers.setInvoicetype("");
					bankAmountOthers.setRemark("OA回退，编号：" + oldDeptIncome.getOaid());
					
					bankAmountOthers.setAdduserid(sysUser.getUserid());
					bankAmountOthers.setAddusername(sysUser.getName());
					bankAmountOthers.setAdddeptid(sysUser.getDepartmentid());
					bankAmountOthers.setAdddeptname(sysUser.getDepartmentname());
					accountForOAService.addBankAmountOthers(bankAmountOthers);
					*/
				}
			}
		}
		return flag;
	}
	@Override
	public List<DeptIncome> showDeptIncomeListBy(Map map) throws Exception{
		String noDataSql=(String)map.get("noDataSql");
		if(!"1".equals(noDataSql)){
			String dataSql = getDataAccessRule("t_js_dept_income", null);
			map.put("dataSql", dataSql);
		}
		List<DeptIncome> list = deptIncomeMapper.showDeptIncomeListBy(map);
		for(DeptIncome deptIncome : list){
			if(StringUtils.isNotEmpty(deptIncome.getDeptid())){
				DepartMent departMent = getDepartmentByDeptid(deptIncome.getDeptid());
				if(null!=departMent){
					deptIncome.setDeptname(departMent.getName());
				}
			}
			if(StringUtils.isNotEmpty(deptIncome.getCostsort())){
				Subject subject=getSubjectInfoById(deptIncome.getCostsort());
				if(null!=subject){
					deptIncome.setCostsortname(subject.getName());
				}
			}
			if(StringUtils.isNotEmpty(deptIncome.getSupplierid())){
				BuySupplier buySupplier=getSupplierInfoById(deptIncome.getSupplierid());
				if(null!=buySupplier){
					deptIncome.setSuppliername(buySupplier.getName());
				}
			}
			if(StringUtils.isNotEmpty(deptIncome.getBrandid())){
				Brand brand=getGoodsBrandByID(deptIncome.getBrandid());
				if(null!=brand){
					deptIncome.setBrandname(brand.getName());
				}
			}
			if(StringUtils.isNotEmpty(deptIncome.getUnitid())){
				MeteringUnit meteringUnit=getMeteringUnitById(deptIncome.getUnitid());
				if(null!=meteringUnit){
					deptIncome.setUnitname(meteringUnit.getName());
				}
			}
		}
		return list;
	}
	@Override
	public DeptIncome getDeptIncomePureInfo(String id) throws Exception{
		DeptIncome deptIncome = deptIncomeMapper.getDeptIncomeInfo(id);
		return deptIncome;
	}
	@Override
	public boolean updateDeptIncomePrinttimes(DeptIncome deptIncome) throws Exception{
		return deptIncomeMapper.updateDeptIncomePrinttimes(deptIncome)>0;
	}

	@Override
	public void updateDeptIncomePrinttimes(List<DeptIncome> list) throws Exception{
		if(null!=list){
			for(DeptIncome item : list){
				deptIncomeMapper.updateDeptIncomePrinttimes(item);
			}
		}
	}

}

