/**
 * @(#)DeptDailyCostServiceImpl.java
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

import com.hd.agent.basefiles.model.*;
import org.apache.commons.lang.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.model.BankAmountOthers;
import com.hd.agent.account.service.IAccountForOAService;
import com.hd.agent.basefiles.dao.DepartMentMapper;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.journalsheet.dao.DeptCostsSubjectMapper;
import com.hd.agent.journalsheet.dao.DeptDailyCostMapper;
import com.hd.agent.journalsheet.model.DeptCostsSubject;
import com.hd.agent.journalsheet.model.DeptDailyCost;
import com.hd.agent.journalsheet.model.DeptDailyCostSupplier;
import com.hd.agent.journalsheet.service.IDeptDailyCostService;
import com.hd.agent.report.service.ISalesReportService;

/**
 * 部门日常费用service实现类
 * 
 * @author chenwei
 */
public class DeptDailyCostServiceImpl extends BaseFilesServiceImpl implements
		IDeptDailyCostService {
	/**
	 * 部门日常费用dao
	 */
	private DeptDailyCostMapper deptDailyCostMapper;
	/**
	 * 
	 */
	private DeptCostsSubjectMapper deptCostsSubjectMapper;
	
	/**
	 * 销售报表接口
	 */
	private ISalesReportService salesReportService; 
	/**
	 * 银行账户财务接口
	 */
	private IAccountForOAService accountForOAService;
	public DeptDailyCostMapper getDeptDailyCostMapper() {
		return deptDailyCostMapper;
	}

	public void setDeptDailyCostMapper(DeptDailyCostMapper deptDailyCostMapper) {
		this.deptDailyCostMapper = deptDailyCostMapper;
	}
	
	public DeptCostsSubjectMapper getDeptCostsSubjectMapper() {
		return deptCostsSubjectMapper;
	}

	public void setDeptCostsSubjectMapper(
			DeptCostsSubjectMapper deptCostsSubjectMapper) {
		this.deptCostsSubjectMapper = deptCostsSubjectMapper;
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
	public boolean addDeptDailyCost(DeptDailyCost deptDailyCost)
			throws Exception {
		if (isAutoCreate("t_js_dept_dailycost")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(deptDailyCost, "t_js_dept_dailycost");
			deptDailyCost.setId(id);
		}else{
			deptDailyCost.setId("FY-"+CommonUtils.getDataNumber());
		}
		SysUser sysUser = getSysUser();
		deptDailyCost.setAdduserid(sysUser.getUserid());
		deptDailyCost.setAddusername(sysUser.getName());
		if(StringUtils.isEmpty(deptDailyCost.getBusinessdate())){
			deptDailyCost.setBusinessdate(CommonUtils.getTodayDataStr());
		}
		String[] date = deptDailyCost.getBusinessdate().split("-");
		deptDailyCost.setYear(Integer.parseInt(date[0]));
		deptDailyCost.setMonth(Integer.parseInt(date[1]));
		int i = deptDailyCostMapper.addDeptDailyCost(deptDailyCost);
		return i>0;
	}

	@Override
	public PageData showDeptDailyCostList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_js_dept_dailycost", null);
		pageMap.setDataSql(dataSql);
		List<DeptDailyCost> list = deptDailyCostMapper.showDeptDailyCostList(pageMap);
		for(DeptDailyCost deptDailyCost : list){
			if(StringUtils.isNotEmpty(deptDailyCost.getDeptid())){
				DepartMent departMent = getDepartmentByDeptid(deptDailyCost.getDeptid());
				if(null!=departMent){
					deptDailyCost.setDeptname(departMent.getName());
				}
			}
            if(StringUtils.isNotEmpty(deptDailyCost.getSalesuser())){
                Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(deptDailyCost.getSalesuser());
                if(null != personnel){
                    deptDailyCost.setSalesusername(personnel.getName());
                }
            }
			if(StringUtils.isNotEmpty(deptDailyCost.getCostsort())){
				DeptCostsSubject deptCostsSubject = deptCostsSubjectMapper.getDeptCostsSubjectById(deptDailyCost.getCostsort());
				if(null!=deptCostsSubject){
					deptDailyCost.setCostsortname(deptCostsSubject.getName());
				}
			}
			if(StringUtils.isNotEmpty(deptDailyCost.getSupplierid())){
				BuySupplier buySupplier=getSupplierInfoById(deptDailyCost.getSupplierid());
				if(null!=buySupplier){
					deptDailyCost.setSuppliername(buySupplier.getName());
				}
			}
			if(StringUtils.isNotEmpty(deptDailyCost.getBrandid())){
				Brand brand=getGoodsBrandByID(deptDailyCost.getBrandid());
				if(null!=brand){
					deptDailyCost.setBrandname(brand.getName());
				}
			}
			if(StringUtils.isNotEmpty(deptDailyCost.getUnitid())){
				MeteringUnit meteringUnit=getMeteringUnitById(deptDailyCost.getUnitid());
				if(null!=meteringUnit){
					deptDailyCost.setUnitname(meteringUnit.getName());
				}
			}
			if(StringUtils.isNotEmpty(deptDailyCost.getBankid())){
				Bank bank = getBankInfoByID(deptDailyCost.getBankid());
				if(null != bank){
					deptDailyCost.setBankname(bank.getName());
				}
			}
		}
		PageData pageData = new PageData(deptDailyCostMapper.showDeptDailyCostListCount(pageMap), list, pageMap);
		List<DeptDailyCost> footerList = new ArrayList<DeptDailyCost>();
		DeptDailyCost deptDailyCostSum=deptDailyCostMapper.showDeptDailyCostListSum(pageMap);
		if(null!=deptDailyCostSum){
			deptDailyCostSum.setId("合 计");
			footerList.add(deptDailyCostSum);
		}
		pageData.setFooter(footerList);
		return pageData;
	}

	@Override
	public DeptDailyCost getDeptDailyCostInfo(String id) throws Exception {
		DeptDailyCost deptDailyCost = deptDailyCostMapper.getDeptDailyCostInfo(id);
		if(null!=deptDailyCost){
			if(StringUtils.isNotEmpty(deptDailyCost.getSupplierid())){
				BuySupplier buySupplier=getSupplierInfoById(deptDailyCost.getSupplierid());
				if(null!=buySupplier){
					deptDailyCost.setSuppliername(buySupplier.getName());
				}
			}
		}
		return deptDailyCost;
	}

	@Override
	public boolean editDeptDailyCost(DeptDailyCost deptDailyCost)
			throws Exception {
		boolean flag = false;
		DeptDailyCost oldDeptDailyCost = getDeptDailyCostInfo(deptDailyCost.getId());
		if(null!=oldDeptDailyCost &&("1".equals(oldDeptDailyCost.getStatus()) || "2".equals(oldDeptDailyCost.getStatus()))){
			SysUser sysUser = getSysUser();
			deptDailyCost.setModifytime(new Date());
			deptDailyCost.setModifyuserid(sysUser.getUserid());
			deptDailyCost.setModifyusername(sysUser.getName());
			if(StringUtils.isEmpty(deptDailyCost.getBusinessdate())){
				deptDailyCost.setBusinessdate(CommonUtils.getTodayDataStr());
			}
			String[] date = deptDailyCost.getBusinessdate().split("-");
			deptDailyCost.setYear(Integer.parseInt(date[0]));
			deptDailyCost.setMonth(Integer.parseInt(date[1]));
			int i = deptDailyCostMapper.editDeptDailyCost(deptDailyCost);
			flag = i>0;
		}
		return flag;
	}

	@Override
	public boolean deleteDeptDailyCost(String id) throws Exception {
		boolean flag = false;
		DeptDailyCost oldDeptDailyCost = getDeptDailyCostInfo(id);
		if(null!=oldDeptDailyCost &&("1".equals(oldDeptDailyCost.getStatus()) || "2".equals(oldDeptDailyCost.getStatus()))){
			int i = deptDailyCostMapper.deleteDeptDailyCost(id);
			flag = i>0;
		}
		return flag;
	}

	@Override
	public boolean auditDeptDailyCost(String id) throws Exception {
		boolean flag = false;
		DeptDailyCost oldDeptDailyCost = getDeptDailyCostInfo(id);
		if(null!=oldDeptDailyCost &&"2".equals(oldDeptDailyCost.getStatus())){
			SysUser sysUser = getSysUser();
			int i = deptDailyCostMapper.auditDeptDailyCost(id, sysUser.getUserid(), sysUser.getName());
			flag = i>0;
			if(flag && StringUtils.isNotEmpty(oldDeptDailyCost.getBankid())){
				BankAmountOthers bankAmountOthers = new BankAmountOthers();
				//借贷类型 1借(收入)2贷(支出)
				bankAmountOthers.setLendtype("2");
				bankAmountOthers.setBankid(oldDeptDailyCost.getBankid());
				bankAmountOthers.setDeptid(oldDeptDailyCost.getDeptid());
				//单据类型0期初1收款单2付款单3日常费用支付4客户费用支付5个人借款6预付款7转账
				bankAmountOthers.setBilltype("3");
				bankAmountOthers.setBillid(oldDeptDailyCost.getId());
				bankAmountOthers.setOaid(oldDeptDailyCost.getOaid());
				bankAmountOthers.setAmount(oldDeptDailyCost.getAmount());
				bankAmountOthers.setUpamount(oldDeptDailyCost.getUpamount());
				bankAmountOthers.setOppid("");
				bankAmountOthers.setOppname(oldDeptDailyCost.getOppname());
				bankAmountOthers.setOppbank(oldDeptDailyCost.getOppbank());
				bankAmountOthers.setOppbankno(oldDeptDailyCost.getOppbankno());
				bankAmountOthers.setInvoiceamount(BigDecimal.ZERO);
				bankAmountOthers.setInvoicedate("");
				bankAmountOthers.setInvoiceid("");
				bankAmountOthers.setInvoicetype("");
				bankAmountOthers.setRemark(oldDeptDailyCost.getRemark());
				
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
	public boolean oppauditDeptDailyCost(String id) throws Exception {
		boolean flag = false;
		DeptDailyCost oldDeptDailyCost = getDeptDailyCostInfo(id);
		if(null!=oldDeptDailyCost &&"3".equals(oldDeptDailyCost.getStatus())){
			SysUser sysUser = getSysUser();
			int i = deptDailyCostMapper.oppauditDeptDailyCost(id, sysUser.getUserid(), sysUser.getName());
			flag = i>0;
			accountForOAService.delteBankAmountOthers(id);
		}
		return flag;
	}

	@Override
	public boolean updateDeptDailyCostSettle(String year,String month) throws Exception {
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
		deptDailyCostMapper.deleteDeptDailyCostSupplier(year, month);
		for(DepartMent departMent : list){
			List<DeptDailyCost> costSortList = deptDailyCostMapper.getDeptDailyCostSumByDeptid(year, month, departMent.getId());
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
				
				for(DeptDailyCost deptDailyCost : costSortList){
					BigDecimal totalCostAmount = deptDailyCost.getAmount();
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
						//添加供应商日常费用金额
						DeptDailyCostSupplier deptDailyCostSupplier = new DeptDailyCostSupplier();
						deptDailyCostSupplier.setSupplierid(supplierid);
						deptDailyCostSupplier.setAmount(supplierAmount);
						deptDailyCostSupplier.setDeptid(deptDailyCost.getDeptid());
						deptDailyCostSupplier.setCostsort(deptDailyCost.getCostsort());;
						deptDailyCostSupplier.setMonth(Integer.parseInt(month));
						deptDailyCostSupplier.setYear(Integer.parseInt(year));
						deptDailyCostSupplier.setBusinessdate(begindate);
						deptDailyCostMapper.addDeptDailyCostSupplier(deptDailyCostSupplier);
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
		List<DeptDailyCost> deptDailyCostList=deptDailyCostMapper.showDeptDailyCostListBy(queryMap);
		for(DeptDailyCost item: deptDailyCostList){
			if(StringUtils.isEmpty(item.getSupplierid())){
				continue;
			}
			//添加供应商日常费用金额
			DeptDailyCostSupplier deptDailyCostSupplier = new DeptDailyCostSupplier();
			deptDailyCostSupplier.setSupplierid(item.getSupplierid());
			deptDailyCostSupplier.setAmount(item.getAmount());
			deptDailyCostSupplier.setDeptid(item.getDeptid());
			deptDailyCostSupplier.setCostsort(item.getCostsort());;
			deptDailyCostSupplier.setMonth(Integer.parseInt(month));
			deptDailyCostSupplier.setYear(Integer.parseInt(year));
			deptDailyCostSupplier.setBusinessdate(item.getBusinessdate());
			deptDailyCostMapper.addDeptDailyCostSupplier(deptDailyCostSupplier);
			flag=true;
		}
		//关闭该月份的部门日常费用单
		if(flag){
			deptDailyCostMapper.closeDeptDailyCost(year, month);
		}
		return flag;
	}

	@Override
	public boolean addAndAuditDeptDailyCost(DeptDailyCost deptDailyCost)
			throws Exception {
		boolean flag = false;
		if (isAutoCreate("t_js_dept_dailycost")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(deptDailyCost, "t_js_dept_dailycost");
			deptDailyCost.setId(id);
		}else{
			deptDailyCost.setId("BMFY-"+CommonUtils.getDataNumber1());
		}
		SysUser sysUser = getSysUser();
		if(StringUtils.isEmpty(deptDailyCost.getAdduserid())){
			deptDailyCost.setAdduserid(sysUser.getUserid());
			deptDailyCost.setAddusername(sysUser.getName());
		}
		if(StringUtils.isEmpty(deptDailyCost.getBusinessdate())){
			deptDailyCost.setBusinessdate(CommonUtils.getTodayDataStr());
		}
		String[] date = deptDailyCost.getBusinessdate().split("-");
		deptDailyCost.setYear(Integer.parseInt(date[0]));
		deptDailyCost.setMonth(Integer.parseInt(date[1]));
		deptDailyCost.setStatus("2");
		int i = deptDailyCostMapper.addDeptDailyCost(deptDailyCost);
		if(i>0){
			flag = auditDeptDailyCost(deptDailyCost.getId());
		}
		return flag;
	}
	@Override
	public PageData getDeptDailyCostCostReportPageData(PageMap pageMap) throws Exception{
		Map queryMap = pageMap.getCondition();
		String sql = getDataAccessRule("t_js_dept_dailycost","t1"); //数据权限
		queryMap.put("dataSql", sql);
		List<Map> list = deptDailyCostMapper.getDeptDailyCostReportPageList(queryMap);
		List dataList = new ArrayList();
		if(null!=list && list.size()>0){
			for(Map map : list){
				String id = (String) map.get("id");
				String pid = (String) map.get("pid");
				String leaf = (String) map.get("leaf");
				if(StringUtils.isEmpty(pid)){
					if("0".equals(leaf)){
						List childrenList = getDeptCostSubjectDataList(id,queryMap);
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
						List childrenList = getDeptCostSubjectDataList(id,queryMap);
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
		Map sumMap = deptDailyCostMapper.getDeptDailyCostReportPageSums(queryMap);
		if(null!=sumMap){
			sumMap.put("name","合计");
			footerList.add(sumMap);
		}
		PageData pageData = new PageData(dataList.size(),dataList,pageMap);
		pageData.setFooter(footerList);
		return pageData;
	}
	@Override
	public List getDeptDailyCostCostReportExportData(Map queryMap) throws Exception{
		String sql = getDataAccessRule("t_js_dept_dailycost","t1"); //数据权限
		queryMap.put("dataSql", sql);
		List<Map> list = deptDailyCostMapper.getDeptDailyCostReportPageList(queryMap);
		if(null!=list && list.size()>0){
			for(Map map : list){
				String id = (String) map.get("id");
				String pid = (String) map.get("pid");
				String name = (String) map.get("name");
				List<Map> childrenList = deptDailyCostMapper.getDeptDailySubjectListData(id,queryMap);
				if(childrenList.size()>0){
					map.put("children", childrenList);
				}
			}
		}
		return list;
	}
	@Override
	public List getDeptDailyCostCostReportExportSumData(Map queryMap) throws Exception{
		String sql = getDataAccessRule("t_js_dept_dailycost","t1"); //数据权限
		queryMap.put("dataSql", sql);
		List footerList = new ArrayList();
		Map sumMap = deptDailyCostMapper.getDeptDailyCostReportPageSums(queryMap);
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
					List childrenList = getDeptCostSubjectDataList(did,queryMap);
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
	private List<Map> getDeptCostSubjectDataList(String deptid,Map queryMap) throws Exception{
		List<Map> dataList = deptDailyCostMapper.getDeptDailySubjectList(deptid,queryMap);
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
				List childrenList = getDeptCostSubjectChildList(dataList,sid);
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
	 * 递归组装费用科目数据
	 * @param list
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月13日
	 */
	private List<Map> getDeptCostSubjectChildList(List<Map> list,String id) throws Exception{
		List<Map> dataList = new ArrayList<Map>();
		for(Map map : list){
			String sid = (String) map.get("sid");
			String pid = (String) map.get("pid");
			String leaf = (String) map.get("leaf");
			if(id.equals(pid)){
				List childrenList = getDeptCostSubjectChildList(list,sid);
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
	public PageData getDeptDailyCostCostYearReportPageData(PageMap pageMap) throws Exception{
		String sql = getDataAccessRule("t_js_dept_dailycost","d"); //数据权限
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
		
		List<Map> list = deptDailyCostMapper.getDeptDailyCostYearReportPageList(pageMap);
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
		Map sumMap = deptDailyCostMapper.getDeptDailyCostYearReportPageSums(pageMap);
		if(null!=sumMap){
			sumMap.put("year","");
			sumMap.put("subjectname","合计");
			footerList.add(sumMap);
		}
		int pageCount=deptDailyCostMapper.getDeptDailyCostYearReportPageCount(pageMap);
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
	public PageData getDeptDailySupplierCostReportPageData(PageMap pageMap) throws Exception{
		String sql = getDataAccessRule("t_js_dept_dailycost_supplier","d"); //数据权限
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
		
		List<Map> list = deptDailyCostMapper.getDeptDailySupplierCostReportPageList(pageMap);
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
		Map sumMap = deptDailyCostMapper.getDeptDailySupplierCostReportPageSums(pageMap);
		if(null!=sumMap){
			sumMap.put("year","");
			sumMap.put("supplierid","");
			sumMap.put("suppliername","合计");
			footerList.add(sumMap);
		}
		int pageCount=deptDailyCostMapper.getDeptDailySupplierCostReportPageCount(pageMap);
		PageData pageData = new PageData(pageCount,list,pageMap);
		pageData.setFooter(footerList);
		return pageData;
	}
	@Override
	public PageData getSupplierSubjectDetailData(PageMap pageMap)
			throws Exception {
		String sql = getDataAccessRule("t_js_dept_dailycost_supplier","d"); //数据权限
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
		
		List<Map> list = deptDailyCostMapper.getSupplierSubjectDetailData(pageMap);
		List<Map> footerList = new ArrayList<Map>();
		condition.put("isSumAll", "true");
		Map sumMap = deptDailyCostMapper.getDeptDailySupplierCostReportPageSums(pageMap);
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
	public PageData getDeptDailySupplierCostYearReportPageData(PageMap pageMap) throws Exception{
		String sql = getDataAccessRule("t_js_dept_dailycost_supplier","d"); //数据权限
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
		
		List<Map> list = deptDailyCostMapper.getDeptDailySupplierCostReportPageList(pageMap);
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
		Map sumMap = deptDailyCostMapper.getDeptDailySupplierCostReportPageSums(pageMap);
		if(null!=sumMap){
			sumMap.put("year","");
			sumMap.put("subjectname","合计");
			footerList.add(sumMap);
		}
		int pageCount=deptDailyCostMapper.getDeptDailySupplierCostReportPageCount(pageMap);
		PageData pageData = new PageData(pageCount,list,pageMap);
		pageData.setFooter(footerList);
		return pageData;
	}
	@Override
	public List showSupplierSubjectReportData(PageMap pageMap) throws Exception {
		String sql = getDataAccessRule("t_js_dept_dailycost_supplier","d"); //数据权限
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
		
		List<Map> list = deptDailyCostMapper.getSupplierSubjectReportList(pageMap);
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
	 * 递归获取供应商费用科目的子节点
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
	public List<DeptDailyCost> selectDeptDailyCostByOaid(String oaid)
			throws Exception {

		return deptDailyCostMapper.selectDeptDailyCostByOaid(oaid);
	}

	@Override
	public boolean rollbackAndAuditDeptDailyCost(DeptDailyCost cost)
			throws Exception {
		boolean flag = false;
		
		//**********************************************************************
		// 是否已经rollback 判断
		//**********************************************************************
		List list = deptDailyCostMapper.selectDeptDailyCostByOaid(cost.getOaid());
		
		// 部门费用存在2n条记录，说明已经生成相反的部门费用
		if(list.size() % 2 == 0) {
			
			return true;
		}
		
		//**********************************************************************
		// rollback
		//**********************************************************************
		if (isAutoCreate("t_js_dept_dailycost")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(cost, "t_js_dept_dailycost");
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
		int i = deptDailyCostMapper.addDeptDailyCost(cost);
		if(i > 0){

			DeptDailyCost oldDeptDailyCost = getDeptDailyCostInfo(cost.getId());
			if(null != oldDeptDailyCost && "2".equals(oldDeptDailyCost.getStatus())){

				int ret = deptDailyCostMapper.auditDeptDailyCost(cost.getId(), sysUser.getUserid(), sysUser.getName());
				flag = ret > 0;
				if(flag && StringUtils.isNotEmpty(oldDeptDailyCost.getBankid())){
					BankAmountOthers bankAmountOthers = new BankAmountOthers();
					//借贷类型 1借(收入)2贷(支出)
					bankAmountOthers.setLendtype("1");
					bankAmountOthers.setBankid(oldDeptDailyCost.getBankid());
					bankAmountOthers.setDeptid(oldDeptDailyCost.getDeptid());
					//单据类型0期初1收款单2付款单3日常费用支付4客户费用支付5个人借款6预付款7转账
					bankAmountOthers.setBilltype("3");
					bankAmountOthers.setBillid(oldDeptDailyCost.getId());
					bankAmountOthers.setOaid(oldDeptDailyCost.getOaid());
					bankAmountOthers.setAmount(oldDeptDailyCost.getAmount() == null ? BigDecimal.ZERO : oldDeptDailyCost.getAmount().negate());
					bankAmountOthers.setUpamount(oldDeptDailyCost.getUpamount());
					bankAmountOthers.setOppid("");
					bankAmountOthers.setOppname(oldDeptDailyCost.getOppname());
					bankAmountOthers.setOppbank(oldDeptDailyCost.getOppbank());
					bankAmountOthers.setOppbankno(oldDeptDailyCost.getOppbankno());
					bankAmountOthers.setInvoiceamount(BigDecimal.ZERO);
					bankAmountOthers.setInvoicedate("");
					bankAmountOthers.setInvoiceid("");
					bankAmountOthers.setInvoicetype("");
					bankAmountOthers.setRemark("OA回退，编号：" + oldDeptDailyCost.getOaid());
					
					bankAmountOthers.setAdduserid(sysUser.getUserid());
					bankAmountOthers.setAddusername(sysUser.getName());
					bankAmountOthers.setAdddeptid(sysUser.getDepartmentid());
					bankAmountOthers.setAdddeptname(sysUser.getDepartmentname());
					accountForOAService.addBankAmountOthers(bankAmountOthers);
				}
			}
		}
		return flag;
	}
	@Override
	public List<DeptDailyCost> showDeptDailyCostListBy(Map map) throws Exception{
		String noDataSql=(String)map.get("noDataSql");
		if(!"1".equals(noDataSql)){
			String dataSql = getDataAccessRule("t_js_dept_dailycost", null);
			map.put("dataSql", dataSql);
		}
		List<DeptDailyCost> list = deptDailyCostMapper.showDeptDailyCostListBy(map);
		for(DeptDailyCost deptDailyCost : list){
			if(StringUtils.isNotEmpty(deptDailyCost.getDeptid())){
				DepartMent departMent = getDepartmentByDeptid(deptDailyCost.getDeptid());
				if(null!=departMent){
					deptDailyCost.setDeptname(departMent.getName());
				}
			}
			if(StringUtils.isNotEmpty(deptDailyCost.getCostsort())){
				DeptCostsSubject deptCostsSubject = deptCostsSubjectMapper.getDeptCostsSubjectById(deptDailyCost.getCostsort());
				if(null!=deptCostsSubject){
					deptDailyCost.setCostsortname(deptCostsSubject.getName());
				}
			}
			if(StringUtils.isNotEmpty(deptDailyCost.getSupplierid())){
				BuySupplier buySupplier=getSupplierInfoById(deptDailyCost.getSupplierid());
				if(null!=buySupplier){
					deptDailyCost.setSuppliername(buySupplier.getName());
				}
			}
			if(StringUtils.isNotEmpty(deptDailyCost.getBrandid())){
				Brand brand=getGoodsBrandByID(deptDailyCost.getBrandid());
				if(null!=brand){
					deptDailyCost.setBrandname(brand.getName());
				}
			}
			if(StringUtils.isNotEmpty(deptDailyCost.getUnitid())){
				MeteringUnit meteringUnit=getMeteringUnitById(deptDailyCost.getUnitid());
				if(null!=meteringUnit){
					deptDailyCost.setUnitname(meteringUnit.getName());
				}
			}
		}
		return list;
	}
	@Override
	public DeptDailyCost getDeptDailyCostPureInfo(String id) throws Exception{
		DeptDailyCost deptDailyCost = deptDailyCostMapper.getDeptDailyCostInfo(id);
		return deptDailyCost;
	}
	@Override
	public boolean updateDeptDailyCostPrinttimes(DeptDailyCost deptDailyCost) throws Exception{
		return deptDailyCostMapper.updateDeptDailyCostPrinttimes(deptDailyCost)>0;
	}

	@Override
	public void updateDeptDailyCostPrinttimes(List<DeptDailyCost> list) throws Exception{
		if(null!=list){
			for(DeptDailyCost item : list){
				deptDailyCostMapper.updateDeptDailyCostPrinttimes(item);
			}
		}
	}

    @Override
    public List<Map> getDeptDailyCostSumData(List<String> idarr) throws Exception {
        if(null!=idarr && idarr.size()>0){
            List<Map> list = deptDailyCostMapper.getDeptDailyCostSumData(idarr);
            return list;
        }
        return new ArrayList<Map>();
    }
	@Override
	public List<Map> getDeptDailyCostSumDataForThird(List<String> idarr) throws Exception {
		if(null!=idarr && idarr.size()>0){
			List<Map> list = deptDailyCostMapper.getDeptDailyCostSumDataForThird(idarr);
			return list;
		}
		return new ArrayList<Map>();
	}

	/**
	 * 人员日常费用报表数据获取
	 * @param pageMap
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author luoqiang
	 * @date Feb 12, 2018
	 */
	@Override
	public PageData getPersonnelDailyCostCostReportPageData(PageMap pageMap) throws Exception{
		Map queryMap = pageMap.getCondition();
		String sql = getDataAccessRule("t_js_dept_dailycost","t1"); //数据权限
		queryMap.put("dataSql", sql);
		List<Map> list = deptDailyCostMapper.getPersonnelDailyCostReportPageList(queryMap);
		List dataList = new ArrayList();
		if(null!=list && list.size()>0){
			for(Map map : list){
				map.put("rid", CommonUtils.getRandomWithTime());
				if(!map.containsKey("name")){
					map.put("name","其它未指定");
				}
				String personnelid="";
				if(map.containsKey("personnelid")){
					personnelid=(String)map.get("personnelid");
				}else{
					map.put("personnelid","");
				}
				List<Map> childrenList=deptDailyCostMapper.getPersonnelDailySubjectList(personnelid,queryMap);
				for(Map childmap:childrenList){
					childmap.put("rid", CommonUtils.getRandomWithTime());
					childmap.put("personnelid", personnelid);
					childmap.put("iconCls","icon-annex");
				}
				map.put("children",childrenList);
			}
		}
		List footerList = new ArrayList();
		Map sumMap = deptDailyCostMapper.getPersonnelDailyCostReportPageSums(queryMap);
		if(null!=sumMap){
			sumMap.put("name","合计");
			footerList.add(sumMap);
		}
		PageData pageData = new PageData(list.size(),list,pageMap);
		pageData.setFooter(footerList);
		return pageData;
	}
	@Override
	public List getPersonnelDailyCostCostReportExportData(Map queryMap) throws Exception{
		String sql = getDataAccessRule("t_js_dept_dailycost","t1"); //数据权限
		queryMap.put("dataSql", sql);
		List<Map> list = deptDailyCostMapper.getPersonnelDailyCostReportPageList(queryMap);
		if(null!=list && list.size()>0){
			for(Map map : list){
				if(!map.containsKey("name")){
					map.put("name","其它未指定");
				}
				String personnelid="";
				if(map.containsKey("personnelid")){
					personnelid=(String)map.get("personnelid");
				}else{
					map.put("personnelid","");
				}
				List<Map> childrenList = deptDailyCostMapper.getPersonnelDailySubjectList(personnelid,queryMap);
				if(childrenList.size()>0){
					map.put("name","");
					map.put("children", childrenList);
				}
			}
		}
		return list;
	}

    /**
     * 获取人员日常费用导出合计数据
     * @param map
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2015年10月13日
     */
    @Override
    public List getPersonnelDailyCostCostReportExportSumData(Map map) throws Exception{
        String sql = getDataAccessRule("t_js_dept_dailycost","t1"); //数据权限
        map.put("dataSql", sql);
        List footerList = new ArrayList();
        Map sumMap = deptDailyCostMapper.getPersonnelDailyCostReportPageSums(map);
        if(null!=sumMap){
            sumMap.put("name","合计");
            footerList.add(sumMap);
        }
        return footerList;
    }
}

