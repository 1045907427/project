/**
 * @(#)PerformanceReportService.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-7-8 zhanghonghui 创建版本
 */
package com.hd.agent.report.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.SalesBillCheckMapper;
import com.hd.agent.basefiles.dao.DepartMentMapper;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.journalsheet.dao.DeptDailyCostMapper;
import com.hd.agent.journalsheet.dao.JournalSheetMapper;
import com.hd.agent.report.dao.PerformanceKPISubjectMapper;
import com.hd.agent.report.dao.PerformanceReportMapper;
import com.hd.agent.report.dao.StorageReportMapper;
import com.hd.agent.report.model.CapitalOccupyReport;
import com.hd.agent.report.model.PerformanceKPIScore;
import com.hd.agent.report.model.PerformanceKPISubject;
import com.hd.agent.report.model.PerformanceKPISummary;
import com.hd.agent.report.service.IPerformanceReportService;
import com.hd.agent.system.model.SysCode;

/**
 * 
 * 考核报表
 * @author zhanghonghui
 */
public class PerformanceReportServiceImpl extends BaseFilesServiceImpl implements IPerformanceReportService {
	private PerformanceReportMapper performanceReportMapper;
	private DepartMentMapper departMentMapper;
	private SalesBillCheckMapper salesBillCheckMapper;
	private JournalSheetMapper journalSheetMapper;
	private StorageReportMapper storageReportMapper;

	private PerformanceKPISubjectMapper performanceKPISubjectMapper;
	
	public PerformanceReportMapper getPerformanceReportMapper() {
		return performanceReportMapper;
	}
	public void setPerformanceReportMapper(
			PerformanceReportMapper performanceReportMapper) {
		this.performanceReportMapper = performanceReportMapper;
	}
	public DepartMentMapper getDepartMentMapper() {
		return departMentMapper;
	}
	public void setDepartMentMapper(DepartMentMapper departMentMapper) {
		this.departMentMapper = departMentMapper;
	}
	public SalesBillCheckMapper getSalesBillCheckMapper() {
		return salesBillCheckMapper;
	}
	public void setSalesBillCheckMapper(SalesBillCheckMapper salesBillCheckMapper) {
		this.salesBillCheckMapper = salesBillCheckMapper;
	}
	public JournalSheetMapper getJournalSheetMapper() {
		return journalSheetMapper;
	}
	public void setJournalSheetMapper(JournalSheetMapper journalSheetMapper) {
		this.journalSheetMapper = journalSheetMapper;
	}
	public StorageReportMapper getStorageReportMapper() {
		return storageReportMapper;
	}
	public void setStorageReportMapper(StorageReportMapper storageReportMapper) {
		this.storageReportMapper = storageReportMapper;
	}

	public PerformanceKPISubjectMapper getPerformanceKPISubjectMapper() {
		return performanceKPISubjectMapper;
	}

	public void setPerformanceKPISubjectMapper(PerformanceKPISubjectMapper performanceKPISubjectMapper) {
		this.performanceKPISubjectMapper = performanceKPISubjectMapper;
	}
	
	
	private DeptDailyCostMapper deptDailyCostMapper;	
	
	public DeptDailyCostMapper getDeptDailyCostMapper() {
		return deptDailyCostMapper;
	}
	public void setDeptDailyCostMapper(DeptDailyCostMapper deptDailyCostMapper) {
		this.deptDailyCostMapper = deptDailyCostMapper;
	}
	//================================================================
	//部门考核
	//================================================================
	@Override
	public Map<String, Object> addPerformanceKPISummary(Map<String, Object> requestMap) throws Exception{
		Map<String, Object> resultMap=new HashMap<String, Object>(); 
		String businessdate=(String)requestMap.get("businessdate");
		String deptid=(String)requestMap.get("deptid");
		
		if(null==businessdate || "".equals(businessdate.trim()) || !businessdate.trim().matches("^\\d{4}-\\d{2}$")){
			resultMap.put("flag", false);
			resultMap.put("msg", "业务日期不能为空");
			return resultMap;
		}
		if(!businessdate.trim().matches("^\\d{4}-\\d{2}$")){
			resultMap.put("flag", false);
			resultMap.put("msg", "业务日期格式不对，格式为yyyy-mm");
			return resultMap;
		}
		if(null==deptid || "".equals(deptid.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "部门编号不能为空");
			return resultMap;
		}
		Map queryMap=new HashMap();
		queryMap.put("businessdate", businessdate);
		queryMap.put("deptid", deptid);

		int iCount=performanceReportMapper.getPerformanceKPISummaryCountByMap(queryMap);
		if(iCount>0){
			resultMap.put("flag", false);
			resultMap.put("msg", "业务日期与部门相关的考核指标汇总数据已经存在");
			return resultMap;			
		}
		PerformanceKPISummary performanceKPISummary=new PerformanceKPISummary();

		performanceKPISummary.setStatus("2");
		performanceKPISummary.setBusinessdate(businessdate);
		performanceKPISummary.setYear(businessdate.split("-")[0]);
		performanceKPISummary.setMonth(businessdate.split("-")[1]);
		performanceKPISummary.setDeptid(deptid);
		SysUser sysUser=getSysUser();
		performanceKPISummary.setAddtime(new Date());
		performanceKPISummary.setAdduserid(sysUser.getUserid());
		performanceKPISummary.setAddusername(sysUser.getName());
		
		updateCalcPerformanceKPISummaryBasicData(performanceKPISummary);
		
		boolean flag=performanceReportMapper.insertPerformanceKPISummary(performanceKPISummary)>0;
		resultMap.put("flag", flag);
		return resultMap;			
	}
	@Override
	public Map<String, Object> updatePerformanceKPISummary(String id) throws Exception{
		Map<String, Object> resultMap=new HashMap<String, Object>(); 
		if(null==id || "".equals(id.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "抱歉，未能找到部门相关的考核指标汇总数据");
			return resultMap;			
		}
		PerformanceKPISummary performanceKPISummary=performanceReportMapper.getPerformanceKPISummary(id);
		if(null==performanceKPISummary){
			resultMap.put("flag", false);
			resultMap.put("msg", "抱歉，未能找到部门相关的考核指标汇总数据");
			return resultMap;			
		}

		SysUser sysUser=getSysUser();
		performanceKPISummary.setModifytime(new Date());
		performanceKPISummary.setModifyuserid(sysUser.getUserid());
		performanceKPISummary.setModifyusername(sysUser.getName());
		
		updateCalcPerformanceKPISummaryBasicData(performanceKPISummary);
		
		boolean flag=performanceReportMapper.updatePerformanceKPISummary(performanceKPISummary)>0;
		resultMap.put("flag", flag);
		return resultMap;			
	}
	/**
	 * 更新、计算并组装来自各种接口处的数据
	 * @param performanceKPISummary
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-8-2
	 */
	private void updateCalcPerformanceKPISummaryBasicData(PerformanceKPISummary performanceKPISummary) throws Exception{
		String businessdate=performanceKPISummary.getBusinessdate();
		String deptid=performanceKPISummary.getDeptid();
		String supplierid=performanceKPISummary.getSupplierid();
		String salesdeptid=performanceKPISummary.getSalesdeptid();
		
		if(null!=supplierid){
			if(!"".equals(supplierid.trim())){
				supplierid=supplierid.trim();
			}else{
				supplierid=null;
			}
		}else{
			supplierid=null;
		}
		
		Date businessDate=CommonUtils.stringToDate(businessdate+"-01");
		Date nowDate=new Date();
		boolean isEarlyDate=false;
		if(businessDate.compareTo(nowDate)<0){
			isEarlyDate=true;
		}
		
		
		Map salesAmountMap=null;
		if(isEarlyDate){
			salesAmountMap=getDeptSalesamountMap(businessdate.trim(),deptid,supplierid); //销售金额
		}
		
		BigDecimal tmpd=BigDecimal.ZERO;
		
		if(null!=salesAmountMap){
			tmpd=(BigDecimal)salesAmountMap.get("salesamount");
			performanceKPISummary.setSalesamount(tmpd);
			tmpd=(BigDecimal)salesAmountMap.get("grossamount");
			performanceKPISummary.setHsmlamount(tmpd);
		}
		if(null==performanceKPISummary.getSalesamount()){
			performanceKPISummary.setSalesamount(BigDecimal.ZERO);
		}
		if(null==performanceKPISummary.getHsmlamount()){
			performanceKPISummary.setHsmlamount(BigDecimal.ZERO);
		}
		if(isEarlyDate){
			//通用版  签呈返还
			// 不使用代垫中的转入分公司
			//tmpd=getMatcostsInputBrandch(businessdate.trim(),deptid);
			//使用代垫中的核销金额
			//tmpd=getMatcostsInputWriteoffAmount(businessdate.trim(),deptid);
			//所有版本使用代垫收支的结余
			tmpd=getMatcostsBalanceAmount(businessdate.trim(),deptid,salesdeptid,supplierid);
		}else{
			tmpd=BigDecimal.ZERO;
		}
		//签呈返还
		performanceKPISummary.setJcfhamount(tmpd);
		if(null==performanceKPISummary.getJcfhamount()){
			performanceKPISummary.setJcfhamount(BigDecimal.ZERO);
		}
		//小计
		performanceKPISummary.setXjamount(performanceKPISummary.getHsmlamount().add(performanceKPISummary.getJcfhamount()));
		//小计率 清零
		performanceKPISummary.setXjrate(BigDecimal.ZERO);
		if(isEarlyDate){
			//部门费用
			tmpd=getCostsDeptAmount(businessdate.trim(),deptid,salesdeptid,supplierid);
		}else{
			tmpd=BigDecimal.ZERO;
		}
		performanceKPISummary.setFyamount(tmpd);
		if(null==performanceKPISummary.getFyamount()){
			performanceKPISummary.setFyamount(BigDecimal.ZERO);
		}
		//费用率清零
		performanceKPISummary.setFyrate(BigDecimal.ZERO);
		//净利
		performanceKPISummary.setJlamount(performanceKPISummary.getXjamount().subtract(performanceKPISummary.getFyamount()));
		if(null==performanceKPISummary.getJlamount()){
			performanceKPISummary.setJlamount(BigDecimal.ZERO);
		}
		//净利率清零
		performanceKPISummary.setJlrate(BigDecimal.ZERO);
		if(isEarlyDate){
			//平均资金占用
			tmpd=getFundAverageStatistics(businessdate.trim(), deptid,salesdeptid,supplierid);
		}else{
			tmpd=BigDecimal.ZERO;
		}
		//平均资金占用
		performanceKPISummary.setPjzjzyamount(tmpd);
		if(null==performanceKPISummary.getPjzjzyamount()){
			performanceKPISummary.setPjzjzyamount(BigDecimal.ZERO);
		}
		//资金利润率清零
		performanceKPISummary.setZjlrrate(BigDecimal.ZERO);
		//平均期末库存金额
		performanceKPISummary.setPjqmkcamount(
				getStorageRevolutionDaysData(businessdate.trim(),deptid,salesdeptid,supplierid)
				);
		if(null==performanceKPISummary.getPjqmkcamount()){
			performanceKPISummary.setPjqmkcamount(BigDecimal.ZERO);
		}
		//平均库存周转天数
		performanceKPISummary.setPjkczzday(BigDecimal.ZERO);
		if(isEarlyDate){
			//期末代垫费用余额
			tmpd=getQMMatcostsReimburse(businessdate.trim(),deptid,salesdeptid,supplierid);
		}else{
			tmpd=BigDecimal.ZERO;
		}
		//期末代垫费用余额
		performanceKPISummary.setQmddfyyeamount(tmpd);
		if(null==performanceKPISummary.getQmddfyyeamount()){
			performanceKPISummary.setQmddfyyeamount(BigDecimal.ZERO);
		}
		//代垫费占用率清零
		performanceKPISummary.setDdfyzyrate(BigDecimal.ZERO);
		//率的计算
		updateCalcPerformanceKPISummaryCommonData(performanceKPISummary);
	}
	
	@Override
	public boolean deletePerformanceKPISummary(String id) throws Exception{
		return performanceReportMapper.deletePerformanceKPISummary(id)>0;
	}
	@Override
	public Map deletePerformanceKPISummaryMore(String idarrs) throws Exception{
		Map map=new HashMap();
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		
		if(null==idarrs || "".equals(idarrs.trim())){

			map.put("flag", false);
			map.put("isuccess", iSuccess);
			map.put("ifailure", iFailure);
			return map;
		}
		String[] idarr=idarrs.trim().split(",");
		Map delMap=new HashMap();
		delMap.put("notAudit", "true");
		for(String id : idarr){
			if(null==id || "".equals(id.trim())){
				continue;
			}
			delMap.put("id", id.trim());
			if(performanceReportMapper.deletePerformanceKPISummaryBy(delMap)>0){
				iSuccess=iSuccess+1;
			}else{
				iFailure=iFailure+1;
			}
		}
		map.clear();
		if(iSuccess>0){
			map.put("flag", true);
		}else{
			map.put("flag", false);
		}
		map.put("isuccess", iSuccess);
		map.put("ifailure", iFailure);
		return map;
	}
	
	@Override
	public Map<String,Object> auditPerformanceKPISummaryMore(String idarrs)throws Exception{
		Map map=new HashMap();
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		
		if(null==idarrs || "".equals(idarrs.trim())){
			map.put("flag", false);
			map.put("isuccess", iSuccess);
			map.put("ifailure", iFailure);
			return map;
		}
		String[] idarr=idarrs.trim().split(",");
		String sql = getDataAccessRule("t_report_performance_kpisummary",null); //数据权限
		SysUser sysUser=getSysUser();
		Map queryMap=new HashMap();
		queryMap.put("dataAuthSql", "true");
		PerformanceKPISummary performanceKPISummary=new PerformanceKPISummary();
		performanceKPISummary.setStatus("3");
		performanceKPISummary.setAudittime(new Date());
		performanceKPISummary.setAudituserid(sysUser.getUserid());
		performanceKPISummary.setAuditusername(sysUser.getName());
		queryMap.put("performanceKPISummary", performanceKPISummary);
		for(String id : idarr){
			if(null==id || "".equals(id.trim())){
				iFailure=iFailure+1;
				continue;
			}
			queryMap.put("id", id.trim());
			if(performanceReportMapper.updatePerformanceKPISummaryByMap(queryMap)>0){
				iSuccess=iSuccess+1;
			}else{
				iFailure=iFailure+1;
			}
		}
		map.clear();
		if(iSuccess>0){
			map.put("flag", true);
		}else{
			map.put("flag", false);
		}
		map.put("isuccess", iSuccess);
		map.put("ifailure", iFailure);
		return map;
	}
	@Override
	public Map<String,Object> oppauditPerformanceKPISummaryMore(String idarrs)throws Exception{
		Map map=new HashMap();
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		
		if(null==idarrs || "".equals(idarrs.trim())){
			map.put("flag", false);
			map.put("isuccess", iSuccess);
			map.put("ifailure", iFailure);
			return map;
		}
		String[] idarr=idarrs.trim().split(",");
		String sql = getDataAccessRule("t_report_performance_kpisummary",null); //数据权限
		SysUser sysUser=getSysUser();
		Map queryMap=new HashMap();
		queryMap.put("dataAuthSql", "true");
		PerformanceKPISummary performanceKPISummary=new PerformanceKPISummary();
		performanceKPISummary.setStatus("2");
		performanceKPISummary.setAudittime(new Date());
		performanceKPISummary.setAudituserid(sysUser.getUserid());
		performanceKPISummary.setAuditusername(sysUser.getName());
		queryMap.put("performanceKPISummary", performanceKPISummary);
		for(String id : idarr){
			if(null==id || "".equals(id.trim())){
				iFailure=iFailure+1;
				continue;
			}
			queryMap.put("id", id.trim());
			if(performanceReportMapper.updatePerformanceKPISummaryByMap(queryMap)>0){
				iSuccess=iSuccess+1;
			}else{
				iFailure=iFailure+1;
			}
		}
		map.clear();
		if(iSuccess>0){
			map.put("flag", true);
		}else{
			map.put("flag", false);
		}
		map.put("isuccess", iSuccess);
		map.put("ifailure", iFailure);
		return map;
	}
	
	
	@Override
	public PageData getPerformanceKPISummaryPageData(PageMap pageMap) throws Exception{
		String sql = getDataAccessRule("t_report_performance_kpisummary",null); //数据权限
		pageMap.setDataSql(sql);
		Map condition=pageMap.getCondition();

		String isExportData=(String)condition.get("isExportData");
		if("true".equals(isExportData)){
			condition.put("isPageflag", "true");
			condition.put("showOrderOnly", "true");
		}
		List<PerformanceKPISummary> list=performanceReportMapper.getPerformanceKPISummaryPageList(pageMap);
		if(list.size() != 0){
			for(PerformanceKPISummary item :list){
				if(StringUtils.isNotEmpty(item.getDeptid())){//所属部门
					DepartMent departMent = departMentMapper.getDepartmentInfo(item.getDeptid());
					if(null!=departMent){
						item.setDeptname(departMent.getName());
					}
				}
			}
		}		
		int total=0;
		if(!"true".equals(isExportData)){
			total=performanceReportMapper.getPerformanceKPISummaryPageCount(pageMap);
		}
		PageData pageData=new PageData(total, list, pageMap);
		PerformanceKPISummary performanceKPISummary=performanceReportMapper.getPerformanceKPISummaryPageSum(pageMap);
		List<PerformanceKPISummary> footList=new ArrayList<PerformanceKPISummary>();
		if(null!=performanceKPISummary){
			performanceKPISummary.setDeptid("");
			performanceKPISummary.setDeptname("合计金额");
			
			updateCalcPerformanceKPISummaryCommonData(performanceKPISummary);
			
			footList.add(performanceKPISummary);
		}
		pageData.setFooter(footList);
		
		return pageData;
	}
	@Override
	public PerformanceKPISummary showPerformanceKPISummary(String id) throws Exception{
		return performanceReportMapper.getPerformanceKPISummary(id);
	}
	/**
	 * 率和其他的计算
	 * @param performanceKPISummary
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-8-5
	 */
	private void updateCalcPerformanceKPISummaryCommonData(PerformanceKPISummary performanceKPISummary) throws Exception{
		performanceKPISummary.setXjrate(BigDecimal.ZERO);
		performanceKPISummary.setFyrate(BigDecimal.ZERO);
		performanceKPISummary.setJlrate(BigDecimal.ZERO);
		performanceKPISummary.setDdfyzyrate(BigDecimal.ZERO);
		performanceKPISummary.setPjkczzday(BigDecimal.ZERO);
		performanceKPISummary.setZjlrrate(BigDecimal.ZERO);
		performanceKPISummary.setDdfyzyrate(BigDecimal.ZERO);
		
		if(null!=performanceKPISummary.getSalesamount() && BigDecimal.ZERO.compareTo(performanceKPISummary.getSalesamount())!=0){
			//小计率  = 小计金额  / 销售额
			performanceKPISummary.setXjrate(performanceKPISummary.getXjamount().divide(performanceKPISummary.getSalesamount(),6,BigDecimal.ROUND_HALF_UP));
			performanceKPISummary.setXjrate(performanceKPISummary.getXjrate().multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_DOWN));
			//费用率 = 费用额 / 销售额
			performanceKPISummary.setFyrate(performanceKPISummary.getFyamount().divide(performanceKPISummary.getSalesamount(),6,BigDecimal.ROUND_HALF_UP));
			performanceKPISummary.setFyrate(performanceKPISummary.getFyrate().multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_DOWN));
			//净利率 = 净利  /销售额
			performanceKPISummary.setJlrate(performanceKPISummary.getJlamount().divide(performanceKPISummary.getSalesamount(),6,BigDecimal.ROUND_HALF_UP));
			performanceKPISummary.setJlrate(performanceKPISummary.getJlrate().multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_DOWN));
			
			//代垫费用占用率  = 期末代垫费用余额  /销售额
			performanceKPISummary.setDdfyzyrate(performanceKPISummary.getQmddfyyeamount().divide(performanceKPISummary.getSalesamount(),6,BigDecimal.ROUND_HALF_UP));
			performanceKPISummary.setDdfyzyrate(performanceKPISummary.getDdfyzyrate().multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_DOWN));
			//平均库存周转天数 = 平均期末库存额 /销售额
			performanceKPISummary.setPjkczzday(performanceKPISummary.getPjqmkcamount().divide(performanceKPISummary.getSalesamount(),6,BigDecimal.ROUND_HALF_UP));
			performanceKPISummary.setPjkczzday(performanceKPISummary.getPjkczzday().multiply(new BigDecimal(30)).setScale(2, BigDecimal.ROUND_HALF_DOWN));			
		}
		if(null!=performanceKPISummary.getPjzjzyamount() && BigDecimal.ZERO.compareTo(performanceKPISummary.getPjzjzyamount())!=0){
			//资金利润率=净利/平均资金占用额
			performanceKPISummary.setZjlrrate(performanceKPISummary.getJlamount().divide(performanceKPISummary.getPjzjzyamount(),6,BigDecimal.ROUND_HALF_UP));
			performanceKPISummary.setZjlrrate(performanceKPISummary.getZjlrrate().multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_DOWN));
			
		}
	}
	
	//===========================================================================
	//其他数据接口
	//===========================================================================
	/**
	 * 部门销售金额
	 * @param businessdate
	 * @param deptid
	 * @param supplierid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-2
	 */
	private Map getDeptSalesamountMap(String businessdate,String deptid,String supplierid) throws Exception{
		if(null==businessdate || "".equals(businessdate.trim())){
			return null;
		}
		Map requestMap=new HashMap();
		
		requestMap.put("businessyearmonth", businessdate.trim());
		
		if(null!=deptid && !"".equals(deptid.trim())){
			requestMap.put("branddeptlike", deptid.trim());
		}
		if(null!=supplierid && !"".equals(supplierid.trim())){
			requestMap.put("supplierid", supplierid.trim());
		}
		PageMap pageMap=new PageMap();
		pageMap.setCondition(requestMap);
		Map saleAmountMap=salesBillCheckMapper.getSalesBillSalesAmountSum(pageMap);
		return saleAmountMap;
	}	
	/**
	 * 部门所在供应商销售金额
	 * @param businessdate
	 * @param deptid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-2
	 */
	private List<Map<String, Object>> getCostsSupplierSalesAmountSum(String businessdate,String deptid) throws Exception{
		if(null==businessdate || "".equals(businessdate.trim())){
			return null;
		}
		DepartMent departMent=departMentMapper.getDepartmentInfo(deptid.trim());
		
		Map requestMap=new HashMap();
		
		requestMap.put("businessyearmonth", businessdate.trim());

		if(null!=deptid && !"".equals(deptid.trim())){
			requestMap.put("branddeptlike", deptid.trim());
		}
		PageMap pageMap=new PageMap();
		pageMap.setCondition(requestMap);
		List<Map<String, Object>> list=salesBillCheckMapper.getSalesBillSupplierSalesAmountSum(pageMap);
		
		return list;
	}
	/**
	 * 资金占用
	 * @param businessdate
	 * @param deptid
	 * @param salesdeptid
	 * @param supplierid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-5
	 */
	private BigDecimal getFundAverageStatistics(String businessdate,String deptid,String salesdeptid,String supplierid) throws Exception{
		Map requestMap=new HashMap();
		requestMap.put("businessyearmonth", businessdate.trim());
		if(null!=deptid && !"".equals(deptid.trim())){
			requestMap.put("branddept", deptid.trim());
		}
		if(null!=salesdeptid && !"".equals(salesdeptid.trim())){
			requestMap.put("salesdeptid", salesdeptid.trim());
		}
		if(null!=supplierid && !"".equals(supplierid.trim())){
			requestMap.put("supplierid", supplierid.trim());
		}
		requestMap.put("groupcolsAll", "all");
		PageMap pageMap=new PageMap();
		pageMap.setCondition(requestMap);
		BigDecimal amount=BigDecimal.ZERO;
		CapitalOccupyReport capitalOccupyReport =  storageReportMapper.showCapitalOccupyAvgSumData(pageMap);
		if(null!=capitalOccupyReport){
			amount=capitalOccupyReport.getTotalamount();
		}
		return amount;
	}
	/**
	 * 部门费用金额
	 * @param businessdate
	 * @param deptid
	 * @param salesdeptid
	 * @param supplierid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-2
	 */
	private BigDecimal getCostsDeptAmount(String businessdate,String deptid,String salesdeptid,String supplierid) throws Exception{
		if(null==businessdate || "".equals(businessdate.trim())){
			return BigDecimal.ZERO;
		}
		Map requestMap=new HashMap();
		
		requestMap.put("businessyearmonth", businessdate.trim());
		if(null!=deptid && !"".equals(deptid.trim())){
			requestMap.put("deptid", deptid.trim());
		}
		if(null!=salesdeptid && !"".equals(salesdeptid.trim())){
			requestMap.put("salesdeptid", salesdeptid.trim());
		}
		if(null!=supplierid && !"".equals(supplierid.trim())){
			requestMap.put("supplierid",supplierid.trim());
		}
		requestMap.put("isAudit", 2);
		
		Map amountMap=null;
//		if(null!=supplierid && !"".equals(supplierid.trim())){
//			amountMap=deptDailyCostMapper.getDeptDailyCostSupplierSum(requestMap);
//		}else{
//			amountMap=deptDailyCostMapper.getDeptDailyCostSumsAll(requestMap);
//		}
		amountMap=deptDailyCostMapper.getDeptDailyCostSumsAll(requestMap);
		BigDecimal amount=BigDecimal.ZERO;
		if(null!=amountMap){
			amount=(BigDecimal)amountMap.get("amount");
			if(null==amount){
				amount=BigDecimal.ZERO;
			}
		}
		return amount;
	}
	/**
	 * 代垫
	 * @param businessdate
	 * @param deptid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-12-2
	 */
	private BigDecimal getMatcostsInputBrandch(String businessdate,String deptid) throws Exception{
		if(null==businessdate || "".equals(businessdate.trim())){
			return BigDecimal.ZERO;
		}
		Map requestMap=new HashMap();
		requestMap.put("businessyearmonth", businessdate.trim());

		if(null!=deptid && !"".equals(deptid.trim())){
			requestMap.put("supplierdeptid", deptid.trim());
		}
		PageMap pageMap=new PageMap();
		pageMap.setCondition(requestMap);
		Map amountMap=journalSheetMapper.getMatcostsInputSums(pageMap);
		BigDecimal amount=BigDecimal.ZERO;
		if(null!=amountMap){
			amount=(BigDecimal)amountMap.get("branchaccount");
			if(null==amount){
				amount=BigDecimal.ZERO;
			}
		}
		return amount;
	}
	/**
	 * 代垫核销金额,目前通用版及瑞家，签呈返还=代垫核销金额
	 * @param businessdate
	 * @param deptid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-12-2
	 */
	private BigDecimal getMatcostsInputWriteoffAmount(String businessdate,String deptid) throws Exception{
		if(null==businessdate || "".equals(businessdate.trim())){
			return BigDecimal.ZERO;
		}
		Map requestMap=new HashMap();
		requestMap.put("writeoffstatus", '4');	//取核销或核销部份的数据
		requestMap.put("writeoffdateyearmonth", businessdate.trim());

		if(null!=deptid && !"".equals(deptid.trim())){
			requestMap.put("supplierdeptid", deptid.trim());
		}
		PageMap pageMap=new PageMap();
		pageMap.setCondition(requestMap);
		Map amountMap=journalSheetMapper.getMatcostsInputSums(pageMap);
		BigDecimal amount=BigDecimal.ZERO;
		if(null!=amountMap){
			amount=(BigDecimal)amountMap.get("writeoffamount");
			if(null==amount){
				amount=BigDecimal.ZERO;
			}
		}
		return amount;
	}
	/**
	 * 代垫核代垫收支的余额,目前通用版及瑞家，签呈返还=代垫收支的余额
	 * @param businessdate
	 * @param deptid
	 * @param salesdeptid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-12-2
	 */
	private BigDecimal getMatcostsBalanceAmount(String businessdate,String deptid,String salesdeptid,String supplierid) throws Exception{
		if(null==businessdate || "".equals(businessdate.trim())){
			return BigDecimal.ZERO;
		}
		Map requestMap=new HashMap();
		requestMap.put("businessyearmonth", businessdate.trim());

		if(null!=deptid && !"".equals(deptid.trim())){
			requestMap.put("deptid", deptid.trim());
		}
		if(null!=salesdeptid && !"".equals(salesdeptid.trim())){
			requestMap.put("salesdeptid", salesdeptid.trim());
		}
		if(null!=supplierid && !"".equals(supplierid.trim())){
			requestMap.put("supplierid", supplierid.trim());
		}
		//其他报表引用代垫金额时，使用代垫收回金额还是代垫核销金额，默认为代垫核销金额
		String DDSZSRAmountUseHXOrSH=getSysParamValue("DDSZSRAmountUseHXOrSH");
		if(null==DDSZSRAmountUseHXOrSH ){
			DDSZSRAmountUseHXOrSH="";
		}
		DDSZSRAmountUseHXOrSH=DDSZSRAmountUseHXOrSH.trim();
		requestMap.put("queryAndUseMatcostsinputHXorSH", DDSZSRAmountUseHXOrSH);
		PageMap pageMap=new PageMap();
		pageMap.setCondition(requestMap);
		Map amountMap=journalSheetMapper.selectMatcostsBalanceSum(pageMap);
		BigDecimal amount=BigDecimal.ZERO;
		if(null!=amountMap){
			amount=(BigDecimal)amountMap.get("balance");
			if(null==amount){
				amount=BigDecimal.ZERO;
			}
		}
		return amount;
	}
	/**
	 * 平均期末库存金额
	 * @param businessdate
	 * @param deptid
	 * @param salesdeptid
	 * @param supplierid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-8-1
	 */
	private BigDecimal getStorageRevolutionDaysData(String businessdate,String deptid,String salesdeptid,String supplierid) throws Exception{
		if(null==businessdate || "".equals(businessdate.trim())){
			return BigDecimal.ZERO;
		}
		Map requestMap=new HashMap();
		requestMap.put("businessyearmonth", businessdate.trim());
		if(null!=deptid && !"".equals(deptid.trim())){
			requestMap.put("branddept", deptid.trim());
		}
		if(null!=salesdeptid && !"".equals(salesdeptid.trim())){
			requestMap.put("salesdeptid", salesdeptid.trim());
		}
		if(null!=supplierid && !"".equals(supplierid.trim())){
			requestMap.put("supplierid", supplierid.trim());
		}
		requestMap.put("groupcolsAll", "all");
		PageMap pageMap=new PageMap();
		pageMap.setCondition(requestMap);
		List<Map> amountList=storageReportMapper.showStorageAvgAmountData(pageMap);
		BigDecimal amount=BigDecimal.ZERO;
		if(null!=amountList && amountList.size()>0){
			String tmpd=amountList.toString();
			if(!"[null]".equals(tmpd)){
				Map resultMap=amountList.get(0);
				amount=(BigDecimal)resultMap.get("storageamount");
				if(null==amount){
					amount=BigDecimal.ZERO;
				}
			}
		}
		return amount;
	}
	/**
	 * 期末代垫
	 * @param businessdate
	 * @param deptid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-12-2
	 */
	private BigDecimal getQMMatcostsReimburse(String businessdate,String deptid,String salesdeptid,String supplierid) throws Exception{
		if(null==businessdate || "".equals(businessdate.trim())){
			return BigDecimal.ZERO;
		}
		String begindate=businessdate+"-01";
		Date beginDate=CommonUtils.stringToDate(begindate);
		Date endDate=CommonUtils.getCurrentMonthLastDate(beginDate);
		String enddate=CommonUtils.dataToStr(endDate, "yyyy-MM-dd");

		Map requestMap=new HashMap();

		requestMap.put("begindate", begindate.trim());
		requestMap.put("enddate", enddate.trim());
		if(null!=deptid && !"".equals(deptid.trim())){
			requestMap.put("deptid", deptid.trim());
		}
		if(null!=salesdeptid && !"".equals(salesdeptid.trim())){
			requestMap.put("salesdeptid", salesdeptid.trim());
		}
		if(null!=supplierid && !"".equals(supplierid.trim())){
			requestMap.put("supplierid", supplierid.trim());
		}
		requestMap.put("isPageflag", "true");
		//小计列
		requestMap.put("groupcols", "all");
		
		PageMap pageMap=new PageMap();
		pageMap.setCondition(requestMap);
		List<Map> amountList=journalSheetMapper.showMatcostsReportData(pageMap);
		BigDecimal amount=BigDecimal.ZERO;
		if(null!=amountList && amountList.size()>0){
			String tmpd=amountList.toString();
			if(!"[null]".equals(tmpd)){
				Map resultMap=amountList.get(0);
				if(null!=requestMap){
					amount=(BigDecimal)resultMap.get("endamount");
					if(null==amount){
						amount=BigDecimal.ZERO;
					}
				}
			}
		}
		return amount;
	}
	
	
	//*================================================================*/
	// 部门考核指标
	//*================================================================*/
	

	/**
	 * 显示部门考核指标列表
	 */
	@Override
	public List showPerformanceKPISubjectList() throws Exception{
		List list=performanceKPISubjectMapper.getPerformanceKPISubjectList();
		return list;
	}
	@Override
	public List showPerformanceKPISubjectEnableList() throws Exception{
		List list=performanceKPISubjectMapper.showPerformanceKPISubjectEnableList();
		return list;
	}
	
	
	public PageData showPerformanceKPISubjectPageList(PageMap pageMap) throws Exception{
		int total=performanceKPISubjectMapper.getPerformanceKPISubjectCount(pageMap);
		List<PerformanceKPISubject> list=performanceKPISubjectMapper.getPerformanceKPISubjectPageList(pageMap);
		for(PerformanceKPISubject kpiSubject: list){
			if(StringUtils.isNotEmpty(kpiSubject.getDeptid())){
				DepartMent departMent = departMentMapper.getDepartmentInfo(kpiSubject.getDeptid());
				if(null!=departMent){
					kpiSubject.setDeptname(departMent.getName());
				}
			}
		}
		PageData pageData=new PageData(total, list, pageMap);
		return pageData;
	}
	
	public PerformanceKPISubject showPerformanceKPISubjectByCode(String code) throws Exception{
		PerformanceKPISubject performanceKPISubject=performanceKPISubjectMapper.getPerformanceKPISubjectByCode(code);
		return performanceKPISubject;
	} 
	public PerformanceKPISubject showPerformanceKPISubjectById(String id) throws Exception{
		PerformanceKPISubject performanceKPISubject=performanceKPISubjectMapper.getPerformanceKPISubjectById(id);
		return performanceKPISubject;
	} 
	
	/**
	 * 新增考核指标
	 */
	@Override
	public Map addPerformanceKPISubject(PerformanceKPISubject performanceKPISubject) throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		boolean flag=false;
		if(StringUtils.isEmpty(performanceKPISubject.getCode())){
			map.put("flag", false);
			map.put("msg", "代码不能为空");
			return map;
		}
		if(StringUtils.isEmpty(performanceKPISubject.getDeptid())){
			map.put("flag", false);
			map.put("msg", "所属部门不能为空");
			return map;
		}
		map.put("code", performanceKPISubject.getCode());
		map.put("deptid", performanceKPISubject.getDeptid());
		int icount=performanceKPISubjectMapper.getPerformanceKPISubjectCountByMap(map);
		if(icount==1){
			map.put("flag", false);
			map.put("msg", "代码已经存在");
			return map;
		}
		flag=performanceKPISubjectMapper.addPerformanceKPISubject(performanceKPISubject)>0;
		map.put("flag", flag);
		return map;
	}
	
	/**
	 * 修改考核指标信息
	 */
	@Override
	public Map editPerformanceKPISubject(PerformanceKPISubject performanceKPISubject) throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		boolean flag=false;
		if(null==performanceKPISubject.getId()){
			map.put("flag", false);
			map.put("msg", "编码不能为空");
			return map;
		}
		if(StringUtils.isEmpty(performanceKPISubject.getCode())){
			map.put("flag", false);
			map.put("msg", "代码不能为空");
			return map;
		}
		if(StringUtils.isEmpty(performanceKPISubject.getOldcode())){
			map.put("flag", false);
			map.put("msg", "代码不能为空");
			return map;
		}
		if(!performanceKPISubject.getCode().equals(performanceKPISubject.getOldcode())){
			if(!canTableDataDictDelete("t_report_performance_kpisubject", performanceKPISubject.getOldcode())){
				map.put("flag", false);
				map.put("msg", "代码已经被引用，不能被修改");
				return map;
			}
		}
		map.put("code", performanceKPISubject.getCode());
		map.put("notCurId", performanceKPISubject.getId());
		int icount=performanceKPISubjectMapper.getPerformanceKPISubjectCountByMap(map);
		if(icount==1){
			map.put("flag", false);
			map.put("msg", "代码已经存在");
			return map;
		}
		flag=performanceKPISubjectMapper.updatePerformanceKPISubject(performanceKPISubject)>0;
		map.put("flag", flag);
		return map;
	}
	/**
	 * 禁用考核指标
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	@Override
	public boolean disablePerformanceKPISubject(String id) throws Exception{
		int i=performanceKPISubjectMapper.disablePerformanceKPISubject(id);
		return i>0;		
	}
	
	/**
	 * 启用考核指标
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-19
	 */
	@Override
	public boolean enablePerformanceKPISubject(String id) throws Exception{
		int i=performanceKPISubjectMapper.enablePerformanceKPISubject(id);
		return i>0;
	}

	@Override
	public boolean deletePerformanceKPISubjectByCode(String id)throws Exception{
		int i=performanceKPISubjectMapper.deletePerformanceKPISubjectByCode(id);
		return i>0;
	}

	@Override
	public boolean deletePerformanceKPISubjectById(String id)throws Exception{
		PerformanceKPISubject performanceKPISubject=performanceKPISubjectMapper.getPerformanceKPISubjectById(id);
		boolean delFlag=false;
		if(null==performanceKPISubject || StringUtils.isEmpty(performanceKPISubject.getCode())){
			delFlag=performanceKPISubjectMapper.deletePerformanceKPISubjectById(id)>0;
			return delFlag;
		}
		delFlag=canTableDataDictDelete("t_report_performance_kpisubject", performanceKPISubject.getCode());
		if(delFlag){
			delFlag=performanceKPISubjectMapper.deletePerformanceKPISubjectById(id)>0;
		}
		return delFlag;
	}
	/**
	 * 批量删除考核指标
	 */
	@Override
	public Map deletePerformanceKPISubjectMore(String idarrs)throws Exception{
		Map map=new HashMap();
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		
		if(null==idarrs || "".equals(idarrs.trim())){

			map.put("flag", false);
			map.put("isuccess", iSuccess);
			map.put("ifailure", iFailure);
			return map;
		}
		String[] idarr=idarrs.trim().split(",");
		for(String id : idarr){
			if(null==id || "".equals(id.trim())){
				continue;
			}
			if(deletePerformanceKPISubjectById(id)){
				iSuccess=iSuccess+1;
			}else{
				iFailure=iFailure+1;
			}
		}
		map.clear();
		if(iSuccess>0){
			map.put("flag", true);
		}else{
			map.put("flag", false);
		}
		map.put("isuccess", iSuccess);
		map.put("ifailure", iFailure);
		return map;
	}
	
	//==================================================================================
	//部门业绩考核数据
	//==================================================================================

	@Override
	public PerformanceKPIScore showPerformanceKPIScore(String id) throws Exception{
		return performanceReportMapper.getPerformanceKPIScore(id);
	}
	/**
	 * 部门业绩考核数据
	 * @param requestMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-9-3
	 */
	public Map addPerformanceKPIScore(Map<String, Object> requestMap) throws Exception{
		Map<String, Object> resultMap=new HashMap<String, Object>(); 
		String businessdate=(String)requestMap.get("businessdate");
		String deptid=(String)requestMap.get("deptid");
		
		if(null==businessdate || "".equals(businessdate.trim()) || !businessdate.trim().matches("^\\d{4}-\\d{2}$")){
			resultMap.put("flag", false);
			resultMap.put("msg", "业务日期不能为空");
			return resultMap;
		}
		if(!businessdate.trim().matches("^\\d{4}-\\d{2}$")){
			resultMap.put("flag", false);
			resultMap.put("msg", "业务日期格式不对，格式为yyyy-mm");
			return resultMap;
		}
		if(null==deptid || "".equals(deptid.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "部门编号不能为空");
			return resultMap;
		}
		Map queryMap=new HashMap();
		queryMap.put("businessdate", businessdate);
		queryMap.put("deptid", deptid);

		int iCount=performanceReportMapper.getPerformanceKPIScoreCountByMap(queryMap);
		if(iCount>0){
			resultMap.put("flag", false);
			resultMap.put("msg", "业务日期与部门相关的业绩考核数据已经存在");
			return resultMap;			
		}
		PerformanceKPIScore performanceKPIScore=new PerformanceKPIScore();

		performanceKPIScore.setStatus("2");
		performanceKPIScore.setBusinessdate(businessdate);
		performanceKPIScore.setYear(businessdate.split("-")[0]);
		performanceKPIScore.setMonth(businessdate.split("-")[1]);
		performanceKPIScore.setDeptid(deptid);
		SysUser sysUser=getSysUser();
		performanceKPIScore.setAddtime(new Date());
		performanceKPIScore.setAdduserid(sysUser.getUserid());
		performanceKPIScore.setAddusername(sysUser.getName());

		Map paramMap=new HashMap();
		paramMap.put("isedit", false);
		updateCalcPerformanceKPIScore(performanceKPIScore,paramMap);
		
		boolean flag=performanceReportMapper.insertPerformanceKPIScore(performanceKPIScore)>0;
		resultMap.put("flag", flag);
		return resultMap;
	}
	@Override
	public Map<String, Object> updatePerformanceKPIScore(PerformanceKPIScore performanceKPIScore,Map<String,Object> requestMap) throws Exception{
		Map<String, Object> resultMap=new HashMap<String, Object>(); 
		if(null==performanceKPIScore.getId()){
			resultMap.put("flag", false);
			resultMap.put("msg", "抱歉，未能找到部门相关的业绩考核数据");
			return resultMap;			
		}
		PerformanceKPIScore oldKPIScore=performanceReportMapper.getPerformanceKPIScore(performanceKPIScore.getId().toString());
		if(null==oldKPIScore){
			resultMap.put("flag", false);
			resultMap.put("msg", "抱歉，未能找到部门相关的业绩考核数据");
			return resultMap;			
		}
		
		if("3".equals(oldKPIScore.getStatus()) || "4".equals(oldKPIScore.getStatus())){
			resultMap.put("flag", false);
			resultMap.put("msg", "抱歉，审核之后的数据不能修改");
			return resultMap;	
		}

		SysUser sysUser=getSysUser();
		oldKPIScore.setModifytime(new Date());
		oldKPIScore.setModifyuserid(sysUser.getUserid());
		oldKPIScore.setModifyusername(sysUser.getName());
		/**
		 * 更新各种指标
		 */
		if(null!=performanceKPIScore.getSalesamountindex()){
			oldKPIScore.setSalesamountindex(performanceKPIScore.getSalesamountindex());
		}else{
			oldKPIScore.setSalesamountindex(BigDecimal.ZERO);
		}
		if(null!=performanceKPIScore.getMlamountindex()){
			oldKPIScore.setMlamountindex(performanceKPIScore.getMlamountindex());
		}else{
			oldKPIScore.setMlamountindex(BigDecimal.ZERO);
		}
		if(null!=performanceKPIScore.getMlrateindex()){
			oldKPIScore.setMlrateindex(performanceKPIScore.getMlrateindex());
		}else{
			oldKPIScore.setMlrateindex(BigDecimal.ZERO);
		}
		if(null!=performanceKPIScore.getKczlrsindex()){
			oldKPIScore.setKczlrsindex(performanceKPIScore.getKczlrsindex());
		}else{
			oldKPIScore.setKczlrsindex(BigDecimal.ZERO);
		}
		if(null!=performanceKPIScore.getFyrateindex()){
			oldKPIScore.setFyrateindex(performanceKPIScore.getFyrateindex());
		}else{
			oldKPIScore.setFyrateindex(BigDecimal.ZERO);
		}
		
		Map paramMap=new HashMap();
		paramMap.put("isedit", true);
		if(requestMap.containsKey("useNewKPISubject")){
			paramMap.put("useNewKPISubject", requestMap.get("useNewKPISubject"));
		}
		updateCalcPerformanceKPIScore(oldKPIScore,paramMap);
		
		boolean flag=performanceReportMapper.updatePerformanceKPIScore(oldKPIScore)>0;
		resultMap.put("flag", flag);
		return resultMap;			
	}
	
	/**
	 * 更新业绩考核中要计算的数据
	 * @param performanceKPIScore
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-9-4
	 */
	private void updateCalcPerformanceKPIScore(PerformanceKPIScore performanceKPIScore,Map<String, Object> paramMap) throws Exception{
		Boolean isedit=false;
		boolean useNewKPISubject=false;
		String tmp="";
		if(paramMap.containsKey("isedit")){
			isedit=(Boolean)paramMap.get("isedit");
			if(null==isedit){
				isedit=false;
			}
		}
		if(paramMap.containsKey("useNewKPISubject")){
			tmp=(String)paramMap.get("useNewKPISubject");
			if("1".equals(tmp) || "true".equals(tmp)){
				useNewKPISubject=true;
			}
		}
		Map map=new HashMap();
		if(!isedit || isedit && useNewKPISubject){
			map.put("state", "1");
			map.put("deptid", performanceKPIScore.getDeptid());
			List<PerformanceKPISubject> subjectList=performanceKPISubjectMapper.getPerformanceKPISubjectListByMap(map);
			PerformanceKPISubject performanceKPISubject=getPerformanceKPISubjectByCode("salesamountindex", subjectList);
			if(null!=performanceKPISubject){
				performanceKPIScore.setSalesamountindexscore(performanceKPISubject.getScore());
				performanceKPIScore.setSalesamountindexvalue(performanceKPISubject.getSvalue());
			}else{
				if(null==performanceKPIScore.getSalesamountindexscore()){
					performanceKPIScore.setSalesamountindexscore(BigDecimal.ZERO);
				}
				if(null==performanceKPIScore.getSalesamountindexvalue()){
					performanceKPIScore.setSalesamountindexvalue(BigDecimal.ZERO);
				}
			}
			performanceKPISubject=getPerformanceKPISubjectByCode("mlindex", subjectList);
			if(null!=performanceKPISubject){
				performanceKPIScore.setMlindexscore(performanceKPISubject.getScore());
				performanceKPIScore.setMlindexvalue(performanceKPISubject.getSvalue());
			}else{
				if(null==performanceKPIScore.getMlindexscore()){
					performanceKPIScore.setMlindexscore(BigDecimal.ZERO);
				}
				if(null==performanceKPIScore.getMlindexvalue()){
					performanceKPIScore.setMlindexvalue(BigDecimal.ZERO);
				}
			}
			performanceKPISubject=getPerformanceKPISubjectByCode("kczzindex", subjectList);
			if(null!=performanceKPISubject){
				performanceKPIScore.setKczlindexscore(performanceKPISubject.getScore());
				performanceKPIScore.setKczlindexvalue(performanceKPISubject.getSvalue());
			}else{
				if(null==performanceKPIScore.getKczlindexscore()){
					performanceKPIScore.setKczlindexscore(BigDecimal.ZERO);
				}
				if(null==performanceKPIScore.getKczlindexvalue()){
					performanceKPIScore.setKczlindexvalue(BigDecimal.ZERO);
				}				
			}
			performanceKPISubject=getPerformanceKPISubjectByCode("fyrateindex", subjectList);
			if(null!=performanceKPISubject){
				performanceKPIScore.setFyrateindexscore(performanceKPISubject.getScore());
				performanceKPIScore.setFyrateindexvalue(performanceKPISubject.getSvalue());
			}else{
				if(null==performanceKPIScore.getFyrateindexscore()){
					performanceKPIScore.setFyrateindexscore(BigDecimal.ZERO);
				}
				if(null==performanceKPIScore.getFyrateindexvalue()){
					performanceKPIScore.setFyrateindexvalue(BigDecimal.ZERO);
				}				
			}
		}
		map.clear();
		map.put("businessdate", performanceKPIScore.getBusinessdate());
		map.put("deptid", performanceKPIScore.getDeptid());
		map.put("isaudit", "true");
		PerformanceKPISummary performanceKPISummary=performanceReportMapper.getPerformanceKPISummaryBy(map);
		if(null!=performanceKPISummary){
			performanceKPIScore.setSalesamount(performanceKPISummary.getSalesamount());		

			if(null==performanceKPIScore.getSalesamount()){
				performanceKPIScore.setSalesamount(BigDecimal.ZERO);
			}
			
			performanceKPIScore.setMlamount(performanceKPISummary.getXjamount());
			
			if(BigDecimal.ZERO.compareTo(performanceKPIScore.getSalesamount())!=0){
				performanceKPIScore.setMlrate(performanceKPIScore.getMlamount().divide(performanceKPIScore.getSalesamount(),6,BigDecimal.ROUND_HALF_UP));
				performanceKPIScore.setMlrate(performanceKPIScore.getMlrate().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
			}
			performanceKPIScore.setKczlrs(performanceKPISummary.getPjkczzday());
			performanceKPIScore.setFyrate(performanceKPISummary.getFyrate());
			
			
			
		}
		if(null==performanceKPIScore.getSalesamount()){
			performanceKPIScore.setSalesamount(BigDecimal.ZERO);
		}
		if(null==performanceKPIScore.getMlamount()){
			performanceKPIScore.setMlamount(BigDecimal.ZERO);
		}
		if(null==performanceKPIScore.getMlrate()){
			performanceKPIScore.setMlrate(BigDecimal.ZERO);
		}
		if(null==performanceKPIScore.getKczlrs()){
			performanceKPIScore.setKczlrs(BigDecimal.ZERO);
		}
		
		if(isedit){
			if(BigDecimal.ZERO.compareTo(performanceKPIScore.getSalesamountindex())!=0){
				//销售金额指标得分
				performanceKPIScore.setSalesamountscore(performanceKPIScore.getSalesamount().divide(performanceKPIScore.getSalesamountindex(), 6,BigDecimal.ROUND_HALF_UP));
				performanceKPIScore.setSalesamountscore(performanceKPIScore.getSalesamountscore().multiply(performanceKPIScore.getSalesamountindexscore()).setScale(2,BigDecimal.ROUND_HALF_UP));
			}else{
				performanceKPIScore.setSalesamountscore(BigDecimal.ZERO);
			}
			if(performanceKPIScore.getSalesamount().compareTo(performanceKPIScore.getSalesamountindex())<0){
				//毛利金额得分：当销售金额-销售指标<0时，诸毛利金额得分
				if(BigDecimal.ZERO.compareTo(performanceKPIScore.getMlamountindex())!=0){
					performanceKPIScore.setMlamountscore(performanceKPIScore.getMlamount().divide(performanceKPIScore.getMlamountindex(),6,BigDecimal.ROUND_HALF_UP));
					performanceKPIScore.setMlamountscore(performanceKPIScore.getMlamountscore().multiply(performanceKPIScore.getMlindexscore()).setScale(2,BigDecimal.ROUND_HALF_UP));
				}else{
					performanceKPIScore.setMlamountscore(BigDecimal.ZERO);
				}
				performanceKPIScore.setMlscore(performanceKPIScore.getMlamountscore());
				performanceKPIScore.setMlratescore(BigDecimal.ZERO);
			}else{
				if(BigDecimal.ZERO.compareTo(performanceKPIScore.getMlrateindex())!=0){
					performanceKPIScore.setMlratescore(performanceKPIScore.getMlrate().divide(performanceKPIScore.getMlrateindex(),6,BigDecimal.ROUND_HALF_UP));
					performanceKPIScore.setMlratescore(performanceKPIScore.getMlratescore().multiply(performanceKPIScore.getMlindexscore()).setScale(2,BigDecimal.ROUND_HALF_UP));
				}else{
					performanceKPIScore.setMlratescore(BigDecimal.ZERO);
				}
				performanceKPIScore.setMlscore(performanceKPIScore.getMlratescore());
				performanceKPIScore.setMlamountscore(BigDecimal.ZERO);
			}
			if(BigDecimal.ZERO.compareTo(performanceKPIScore.getKczlrsindex())!=0){
				//库存周转得分=库存周转指标总分-(库存周转实绩-库存周围指标)/库存周围指标 * 库存周转指标总分
				performanceKPIScore.setKczlrsscore(performanceKPIScore.getKczlrs().subtract(performanceKPIScore.getKczlrsindex())
																				  .divide(performanceKPIScore.getKczlrsindex(),6,BigDecimal.ROUND_HALF_UP)
																				  .multiply(performanceKPIScore.getKczlindexscore())
						);
				performanceKPIScore.setKczlrsscore(performanceKPIScore.getKczlindexscore().subtract(performanceKPIScore.getKczlrsscore()).setScale(2,BigDecimal.ROUND_HALF_UP));
			}else{
				performanceKPIScore.setKczlrsscore(BigDecimal.ZERO);
			}
			
			if(BigDecimal.ZERO.compareTo(performanceKPIScore.getFyrateindex())!=0){
				//费用指标得分=费用指标总分-(费用率-费用指标)/费用指标 * 费用指标总分
				performanceKPIScore.setFyratescore(performanceKPIScore.getFyrate().subtract(performanceKPIScore.getFyrateindex())
																				  .divide(performanceKPIScore.getFyrateindex(),6,BigDecimal.ROUND_HALF_UP)
																				  .multiply(performanceKPIScore.getFyrateindexscore())
						);
				performanceKPIScore.setFyratescore(performanceKPIScore.getFyrateindexscore().subtract(performanceKPIScore.getFyratescore()).setScale(2,BigDecimal.ROUND_HALF_UP));
			}else{
				performanceKPIScore.setFyratescore(BigDecimal.ZERO);
			}
			
			//总得分计算
			performanceKPIScore.setTotalscore(performanceKPIScore.getSalesamountscore()
					.add(performanceKPIScore.getMlamountscore())
					.add(performanceKPIScore.getMlratescore())
					.add(performanceKPIScore.getKczlrsscore())
					.add(performanceKPIScore.getFyratescore()));
			//奖金得分
			performanceKPIScore.setBonus(BigDecimal.ZERO);
			
			if(null!=performanceKPIScore.getSalesamountindexvalue()){
				performanceKPIScore.setBonus(performanceKPIScore.getSalesamountscore().multiply(performanceKPIScore.getSalesamountindexvalue()));
			}
			if(null!=performanceKPIScore.getMlindexvalue()){
				performanceKPIScore.setBonus(performanceKPIScore.getBonus().add(performanceKPIScore.getMlamountscore().multiply(performanceKPIScore.getMlindexvalue())));
				
				performanceKPIScore.setBonus(performanceKPIScore.getBonus().add(performanceKPIScore.getMlratescore().multiply(performanceKPIScore.getMlindexvalue())));
			}
			if(null!=performanceKPIScore.getKczlindexvalue()){
				performanceKPIScore.setBonus(performanceKPIScore.getBonus().add(performanceKPIScore.getKczlrsscore().multiply(performanceKPIScore.getKczlindexvalue())));
			}
			if(null!=performanceKPIScore.getFyrateindexvalue()){
				performanceKPIScore.setBonus(performanceKPIScore.getBonus().add(performanceKPIScore.getFyratescore().multiply(performanceKPIScore.getFyrateindexvalue())));
			}
		}
	}
	/**
	 * 根据code 去找出PerformanceKPISubject
	 * @param code
	 * @param list
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-9-4
	 */
	private PerformanceKPISubject getPerformanceKPISubjectByCode(String code,List<PerformanceKPISubject> list){
		PerformanceKPISubject result=null;
		if(null==code || "".equals(code.trim())){
			return result;
		}
		if(null!=list){
			for(PerformanceKPISubject subject: list){
				if(code.trim().equals(subject.getCode())){
					result=subject;
					break;
				}
			}
		}
		return result;
	}
	
	public PageData getPerformanceKPIScorePageData(PageMap pageMap) throws Exception{
		String sql = getDataAccessRule("t_report_performance_kpiscore",null); //数据权限
		pageMap.setDataSql(sql);
		Map condition=pageMap.getCondition();

		String isExportData=(String)condition.get("isExportData");
		if("true".equals(isExportData)){
			condition.put("isPageflag", "true");
			condition.put("showOrderOnly", "true");
		}
		List<PerformanceKPIScore> list=performanceReportMapper.getPerformanceKPIScorePageList(pageMap);
		if(list.size() != 0){
			for(PerformanceKPIScore item :list){
				if(StringUtils.isNotEmpty(item.getDeptid())){//所属部门
					DepartMent departMent = departMentMapper.getDepartmentInfo(item.getDeptid());
					if(null!=departMent){
						item.setDeptname(departMent.getName());
					}
				}
			}
		}		
		int total=0;
		if(!"true".equals(isExportData)){
			total=performanceReportMapper.getPerformanceKPIScorePageCount(pageMap);
		}
		PageData pageData=new PageData(total, list, pageMap);
		PerformanceKPIScore performanceKPIScore=performanceReportMapper.getPerformanceKPIScorePageSum(pageMap);
		List<PerformanceKPIScore> footList=new ArrayList<PerformanceKPIScore>();
		if(null!=performanceKPIScore){
			performanceKPIScore.setDeptid("");
			performanceKPIScore.setDeptname("合计金额");
			
			
			footList.add(performanceKPIScore);
		}
		pageData.setFooter(footList);
		
		return pageData;
	}
	@Override
	public boolean deletePerformanceKPIScore(String id){
		return performanceReportMapper.deletePerformanceKPIScore(id)>0;
	}
	
	@Override
	public Map deletePerformanceKPIScoreMore(String idarrs) throws Exception{
		Map map=new HashMap();
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		
		if(null==idarrs || "".equals(idarrs.trim())){

			map.put("flag", false);
			map.put("isuccess", iSuccess);
			map.put("ifailure", iFailure);
			return map;
		}
		String[] idarr=idarrs.trim().split(",");
		Map delMap=new HashMap();
		delMap.put("notAudit", "true");
		for(String id : idarr){
			if(null==id || "".equals(id.trim())){
				continue;
			}
			delMap.put("id", id.trim());
			if(performanceReportMapper.deletePerformanceKPIScoreBy(delMap)>0){
				iSuccess=iSuccess+1;
			}else{
				iFailure=iFailure+1;
			}
		}
		map.clear();
		if(iSuccess>0){
			map.put("flag", true);
		}else{
			map.put("flag", false);
		}
		map.put("isuccess", iSuccess);
		map.put("ifailure", iFailure);
		return map;
	}
	@Override
	public Map<String,Object> auditPerformanceKPIScoreMore(String idarrs)throws Exception{
		Map map=new HashMap();
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		
		if(null==idarrs || "".equals(idarrs.trim())){
			map.put("flag", false);
			map.put("isuccess", iSuccess);
			map.put("ifailure", iFailure);
			return map;
		}
		String[] idarr=idarrs.trim().split(",");
		String sql = getDataAccessRule("t_report_performance_kpiscore",null); //数据权限
		SysUser sysUser=getSysUser();
		Map queryMap=new HashMap();
		queryMap.put("dataAuthSql", "true");
		PerformanceKPIScore performanceKPIScore=new PerformanceKPIScore();
		performanceKPIScore.setStatus("3");
		performanceKPIScore.setAudittime(new Date());
		performanceKPIScore.setAudituserid(sysUser.getUserid());
		performanceKPIScore.setAuditusername(sysUser.getName());
		queryMap.put("performanceKPIScore", performanceKPIScore);
		for(String id : idarr){
			if(null==id || "".equals(id.trim())){
				iFailure=iFailure+1;
				continue;
			}
			queryMap.put("id", id.trim());
			if(performanceReportMapper.updatePerformanceKPIScoreByMap(queryMap)>0){
				iSuccess=iSuccess+1;
			}else{
				iFailure=iFailure+1;
			}
		}
		map.clear();
		if(iSuccess>0){
			map.put("flag", true);
		}else{
			map.put("flag", false);
		}
		map.put("isuccess", iSuccess);
		map.put("ifailure", iFailure);
		return map;
	}
	@Override
	public Map<String,Object> oppauditPerformanceKPIScoreMore(String idarrs)throws Exception{
		Map map=new HashMap();
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		
		if(null==idarrs || "".equals(idarrs.trim())){
			map.put("flag", false);
			map.put("isuccess", iSuccess);
			map.put("ifailure", iFailure);
			return map;
		}
		String[] idarr=idarrs.trim().split(",");
		String sql = getDataAccessRule("t_report_performance_kpiscore",null); //数据权限
		SysUser sysUser=getSysUser();
		Map queryMap=new HashMap();
		queryMap.put("dataAuthSql", "true");
		PerformanceKPIScore performanceKPIScore=new PerformanceKPIScore();
		performanceKPIScore.setStatus("2");
		performanceKPIScore.setAudittime(new Date());
		performanceKPIScore.setAudituserid(sysUser.getUserid());
		performanceKPIScore.setAuditusername(sysUser.getName());
		queryMap.put("performanceKPIScore", performanceKPIScore);
		for(String id : idarr){
			if(null==id || "".equals(id.trim())){
				iFailure=iFailure+1;
				continue;
			}
			queryMap.put("id", id.trim());
			if(performanceReportMapper.updatePerformanceKPIScoreByMap(queryMap)>0){
				iSuccess=iSuccess+1;
			}else{
				iFailure=iFailure+1;
			}
		}
		map.clear();
		if(iSuccess>0){
			map.put("flag", true);
		}else{
			map.put("flag", false);
		}
		map.put("isuccess", iSuccess);
		map.put("ifailure", iFailure);
		return map;
	}
	
	//===================================================================
	// 报表
	//===================================================================
	@Override
	public PageData getPerformanceKPISummaryReportData(PageMap pageMap) throws Exception{
		Map condition=pageMap.getCondition();

		String salesdeptid = (String) condition.get("salesdeptid");
		String deptid=(String)condition.get("deptid");
		String year=(String)condition.get("year");
		String month=(String) condition.get("month");
		String supplierid=(String)condition.get("supplierid");
		
		if(null==month || "".equals(month.trim())){
			month="";
		}
		month=month.trim();
		
		PageData pageData=null;
		//要返回的结果数据
		List<Map> summaryReportList=new ArrayList<Map>();
		if(null==year || "".equals(year.trim())){
			pageData=new PageData(0, summaryReportList, pageMap);
			return pageData;
		}

		
		//初始化返回结果数据
		getInitPerformanceSummaryReportData(summaryReportList);
		
		PerformanceKPISummary performanceKPISummarySum=new PerformanceKPISummary();
		
		String yearmonth="";
		String sMonth="";
		
		for(int iMonth=1;iMonth<13;iMonth++){
			if(iMonth<10){
				sMonth="0"+iMonth;
				yearmonth=year+"-0"+iMonth;
			}else{
				sMonth=""+iMonth;
				yearmonth=year+"-"+iMonth;
			}
			PerformanceKPISummary performanceKPISummary=new PerformanceKPISummary();
			performanceKPISummary.setSalesdeptid(salesdeptid);
			performanceKPISummary.setDeptid(deptid);
			performanceKPISummary.setBusinessdate(yearmonth);
			performanceKPISummary.setYear(year);
			performanceKPISummary.setMonth(sMonth);
			performanceKPISummary.setSupplierid(supplierid);
			if(!"".equals(month)){
				if( month.equals(iMonth+"")){
					//计算
					updateCalcPerformanceKPISummaryBasicData(performanceKPISummary);
				}else{
					performanceKPISummary.setDdfyzyrate(BigDecimal.ZERO);
					performanceKPISummary.setFyamount(BigDecimal.ZERO);
					performanceKPISummary.setFyrate(BigDecimal.ZERO);
					performanceKPISummary.setHsmlamount(BigDecimal.ZERO);
					performanceKPISummary.setJcfhamount(BigDecimal.ZERO);
					performanceKPISummary.setJlamount(BigDecimal.ZERO);
					performanceKPISummary.setJlrate(BigDecimal.ZERO);
					performanceKPISummary.setPjkczzday(BigDecimal.ZERO);
					performanceKPISummary.setPjqmkcamount(BigDecimal.ZERO);
					performanceKPISummary.setPjzjzyamount(BigDecimal.ZERO);
					performanceKPISummary.setQmddfyyeamount(BigDecimal.ZERO);
					performanceKPISummary.setSalesamount(BigDecimal.ZERO);
					performanceKPISummary.setXjamount(BigDecimal.ZERO);
					performanceKPISummary.setXjrate(BigDecimal.ZERO);
					performanceKPISummary.setZjlrrate(BigDecimal.ZERO);
				}
			}else{
				//计算
				updateCalcPerformanceKPISummaryBasicData(performanceKPISummary);
			}

			setIntoPerformanceSummaryReportData(summaryReportList,performanceKPISummary,performanceKPISummarySum);
		}		

		///计算按月合计时金额
		updateCalcPerformanceKPISummaryCommonData(performanceKPISummarySum);
		//设置合计数据
		setPerformanceKPISummaryReportTotal(summaryReportList, performanceKPISummarySum);
		
		int total=0;
		
		pageData=new PageData(total, summaryReportList, pageMap);
		
		return pageData;
	}	
	/**
	 * 转储考核指标数据汇总
	 * @param list
	 * @param performanceKPISummary
	 * @author zhanghonghui 
	 * @date 2014-11-29
	 */
	private void setIntoPerformanceSummaryReportData(List<Map> list,PerformanceKPISummary performanceKPISummary,PerformanceKPISummary performanceKPISummarySum){
		if(null==performanceKPISummary){
			return;
		}
		if(StringUtils.isEmpty(performanceKPISummary.getMonth())){
			return;
		}
		int month=Integer.parseInt(performanceKPISummary.getMonth());
		for(Map map : list){
			String subject=(String)map.get("subject");
			
			if("salesamount".equals(subject)){
				//销售额
				performanceKPISummary.setSalesamount(performanceKPISummary.getSalesamount().setScale(2, BigDecimal.ROUND_HALF_UP));
				map.put("month_"+month, performanceKPISummary.getSalesamount());
				
				if(null==performanceKPISummarySum.getSalesamount()){
					performanceKPISummarySum.setSalesamount(BigDecimal.ZERO);
				}
				performanceKPISummarySum.setSalesamount(performanceKPISummarySum.getSalesamount().add(performanceKPISummary.getSalesamount()));
				
			}else if("jcfhamount".equals(subject)){
				//签呈返还
				performanceKPISummary.setJcfhamount(performanceKPISummary.getJcfhamount().setScale(2, BigDecimal.ROUND_HALF_UP));
				map.put("month_"+month, performanceKPISummary.getJcfhamount());
				
				if(null==performanceKPISummarySum.getJcfhamount()){
					performanceKPISummarySum.setJcfhamount(BigDecimal.ZERO);
				}
				performanceKPISummarySum.setJcfhamount(performanceKPISummarySum.getJcfhamount().add(performanceKPISummary.getJcfhamount()));
				
			}else if("hsmlamount".equals(subject)){
				//含税毛利
				performanceKPISummary.setHsmlamount(performanceKPISummary.getHsmlamount().setScale(2, BigDecimal.ROUND_HALF_UP));
				map.put("month_"+month, performanceKPISummary.getHsmlamount());
				if(null==performanceKPISummarySum.getHsmlamount()){
					performanceKPISummarySum.setHsmlamount(BigDecimal.ZERO);
				}
				performanceKPISummarySum.setHsmlamount(performanceKPISummarySum.getHsmlamount().add(performanceKPISummary.getHsmlamount()));
				
			}else if("xjamount".equals(subject)){
				//小计
				performanceKPISummary.setXjamount(performanceKPISummary.getXjamount().setScale(2, BigDecimal.ROUND_HALF_UP));
				map.put("month_"+month, performanceKPISummary.getXjamount());
				
				if(null==performanceKPISummarySum.getXjamount()){
					performanceKPISummarySum.setXjamount(BigDecimal.ZERO);
				}
				performanceKPISummarySum.setXjamount(performanceKPISummarySum.getXjamount().add(performanceKPISummary.getXjamount()));
				
			}else if("xjrate".equals(subject)){
				//小计率
				map.put("month_"+month, performanceKPISummary.getXjrate() + "%");
			}else if("fyamount".equals(subject)){
				//费用额
				performanceKPISummary.setFyamount(performanceKPISummary.getFyamount().setScale(2, BigDecimal.ROUND_HALF_UP));
				map.put("month_"+month, performanceKPISummary.getFyamount());
				
				if(null==performanceKPISummarySum.getFyamount()){
					performanceKPISummarySum.setFyamount(BigDecimal.ZERO);
				}
				performanceKPISummarySum.setFyamount(performanceKPISummarySum.getFyamount().add(performanceKPISummary.getFyamount()));
				
			}else if("fyrate".equals(subject)){
				//费用率
				map.put("month_"+month, performanceKPISummary.getFyrate()+ "%");
			}else if("jlamount".equals(subject)){
				//净利
				performanceKPISummary.setJlamount(performanceKPISummary.getJlamount().setScale(2, BigDecimal.ROUND_HALF_UP));
				map.put("month_"+month, performanceKPISummary.getJlamount());
				
				if(null==performanceKPISummarySum.getJlamount()){
					performanceKPISummarySum.setJlamount(BigDecimal.ZERO);
				}
				performanceKPISummarySum.setJlamount(performanceKPISummarySum.getJlamount().add(performanceKPISummary.getJlamount()));
				
			}else if("jlrate".equals(subject)){
				//净利率
				map.put("month_"+month, performanceKPISummary.getJlrate()+ "%");
			}else if("pjqmkcamount".equals(subject)){
				//平均期末库存额
				performanceKPISummary.setPjqmkcamount(performanceKPISummary.getPjqmkcamount().setScale(2, BigDecimal.ROUND_HALF_UP));				
				map.put("month_"+month, performanceKPISummary.getPjqmkcamount());
				
				if(null==performanceKPISummarySum.getPjqmkcamount()){
					performanceKPISummarySum.setPjqmkcamount(BigDecimal.ZERO);
				}
				performanceKPISummarySum.setPjqmkcamount(performanceKPISummarySum.getPjqmkcamount().add(performanceKPISummary.getPjqmkcamount()));
				
			}else if("pjkczzday".equals(subject)){
				//平均库存周转天数
				map.put("month_"+month, performanceKPISummary.getPjkczzday());
			}else if("pjzjzyamount".equals(subject)){
				//平均资金占用额
				performanceKPISummary.setPjzjzyamount(performanceKPISummary.getPjzjzyamount().setScale(2, BigDecimal.ROUND_HALF_UP));
				map.put("month_"+month, performanceKPISummary.getPjzjzyamount());
				if(null==performanceKPISummarySum.getPjzjzyamount()){
					performanceKPISummarySum.setPjzjzyamount(BigDecimal.ZERO);
				}
				performanceKPISummarySum.setPjzjzyamount(performanceKPISummarySum.getPjzjzyamount().add(performanceKPISummary.getPjzjzyamount()));
				
			}else if("zjlrrate".equals(subject)){
				//资金利润率
				map.put("month_"+month, performanceKPISummary.getZjlrrate()+ "%");
			}else if("qmddfyyeamount".equals(subject)){
				//期末代垫费用余额
				performanceKPISummary.setQmddfyyeamount(performanceKPISummary.getQmddfyyeamount().setScale(2, BigDecimal.ROUND_HALF_UP));
				map.put("month_"+month, performanceKPISummary.getQmddfyyeamount());
				if(null==performanceKPISummarySum.getQmddfyyeamount()){
					performanceKPISummarySum.setQmddfyyeamount(BigDecimal.ZERO);
				}
				performanceKPISummarySum.setQmddfyyeamount(performanceKPISummarySum.getQmddfyyeamount().add(performanceKPISummary.getQmddfyyeamount()));
								
			}else if("ddfyzyrate".equals(subject)){
				//代垫费占用率
				map.put("month_"+month, performanceKPISummary.getDdfyzyrate()+ "%");				
			}
		}
	}
	/**
	 * 初始化考核指标数据汇总
	 * @param list
	 * @author zhanghonghui 
	 * @date 2014-11-29
	 */
	private void getInitPerformanceSummaryReportData(List<Map> list) throws Exception{
		Map map=new HashMap();
		//如果有代码存在，则使用代码表数据
		List<SysCode> sysCodeList=getBaseSysCodeMapper().getSysCodeListForeign("kpiSummaryReportSubject");
		if(null !=sysCodeList && sysCodeList.size()>0){
			for(SysCode item : sysCodeList){
				map=new HashMap();
				map.put("subject",item.getCode());
				map.put("subjectname", item.getCodename());
				
				for(int i=1;i<13;i++){
					if("xjrate".equals(item.getCode()) || ("fyrate".equals(item.getCode())) 
							|| ("jlrate".equals(item.getCode())) || ("zjlrrate".equals(item.getCode())) 
							|| ("ddfyzyrate".equals(item.getCode()))){
						map.put("month_", "0.00%");
					}else{
						map.put("month_"+i, 0.00);
					}
				}
				list.add(map);
			}
		}
	}
	/**
	 * 设置List中totalamount
	 * @param list
	 * @param performanceKPISummary
	 * @author zhanghonghui 
	 * @date 2014-12-3
	 */
	private void setPerformanceKPISummaryReportTotal(List<Map> list,PerformanceKPISummary performanceKPISummary){
		if(null==performanceKPISummary){
			return;
		}
		for(Map map : list){
			String subject=(String)map.get("subject");			
			if("salesamount".equals(subject)){
				map.put("totalamount", performanceKPISummary.getSalesamount());
			}else if("jcfhamount".equals(subject)){
				map.put("totalamount", performanceKPISummary.getJcfhamount());
			}else if("hsmlamount".equals(subject)){
				map.put("totalamount", performanceKPISummary.getHsmlamount());
			}else if("xjamount".equals(subject)){
				map.put("totalamount", performanceKPISummary.getXjamount());
			}else if("xjrate".equals(subject)){
				map.put("totalamount", performanceKPISummary.getXjrate() + "%");
			}else if("fyamount".equals(subject)){
				map.put("totalamount", performanceKPISummary.getFyamount());
			}else if("fyrate".equals(subject)){
				map.put("totalamount", performanceKPISummary.getFyrate()+ "%");
			}else if("jlamount".equals(subject)){
				map.put("totalamount", performanceKPISummary.getJlamount());
			}else if("jlrate".equals(subject)){
				map.put("totalamount", performanceKPISummary.getJlrate()+ "%");
			}else if("pjqmkcamount".equals(subject)){
				map.put("totalamount", performanceKPISummary.getPjqmkcamount());
			}else if("pjkczzday".equals(subject)){
				map.put("totalamount", performanceKPISummary.getPjkczzday());
			}else if("pjzjzyamount".equals(subject)){
				map.put("totalamount", performanceKPISummary.getPjzjzyamount());
			}else if("zjlrrate".equals(subject)){
				map.put("totalamount", performanceKPISummary.getZjlrrate()+ "%");
			}else if("qmddfyyeamount".equals(subject)){
				map.put("totalamount", performanceKPISummary.getQmddfyyeamount());
			}else if("ddfyzyrate".equals(subject)){
				map.put("totalamount", performanceKPISummary.getDdfyzyrate()+ "%");				
			}
		}
	}
	
	@Override
	public PageData getPerformanceKPIScoreReportData(PageMap pageMap) throws Exception{
		String sql = getDataAccessRule("t_report_performance_kpiscore",null); //数据权限
		pageMap.setDataSql(sql);
		Map condition=pageMap.getCondition();

		String isExportData=(String)condition.get("isExportData");
		boolean showPageRowId=false;
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
			pageMap.setOrderSql(" businessdate asc ");
		}
		List<PerformanceKPIScore> list=performanceReportMapper.getPerformanceKPIScoreReportList(pageMap);
		if(list.size() != 0){
			int rowId=1;
			for(PerformanceKPIScore item :list){
				if(showPageRowId){
					item.setId(rowId);
					rowId=rowId+1;
				}
				if(StringUtils.isNotEmpty(item.getDeptid())){//所属部门
					DepartMent departMent = departMentMapper.getDepartmentInfo(item.getDeptid());
					if(null!=departMent){
						item.setDeptname(departMent.getName());
					}
				}
			}
		}		
		int total=0;
		if(showPageRowId){
			total=performanceReportMapper.getPerformanceKPIScoreReportCount(pageMap);
		}
		PageData pageData=new PageData(total, list, pageMap);
		if(condition.containsKey("groupBycols")){
			condition.remove("groupBycols");
		}
		condition.put("isSumAll","true");
		PerformanceKPIScore performanceKPIScore=performanceReportMapper.getPerformanceKPIScoreReportSum(pageMap);
		List<PerformanceKPIScore> footList=new ArrayList<PerformanceKPIScore>();
		if(null!=performanceKPIScore){
			performanceKPIScore.setDeptid("");
			performanceKPIScore.setDeptname("合计金额");
			footList.add(performanceKPIScore);
		}
		pageData.setFooter(footList);
		
		return pageData;
	}	
}

