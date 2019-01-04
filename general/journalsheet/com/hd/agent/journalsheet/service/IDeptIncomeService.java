/**
 * @(#)IDeptIncomeService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年11月21日 chenwei 创建版本
 */
package com.hd.agent.journalsheet.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.journalsheet.model.DeptIncome;

/**
 * 
 * 部门收入service
 * @author chenwei
 */
public interface IDeptIncomeService {
	
	/**
	 * 添加部门收入
	 * @param deptIncome
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public boolean addDeptIncome(DeptIncome deptIncome) throws Exception;
	/**
	 * 获取部门收入列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public PageData showDeptIncomeList(PageMap pageMap) throws Exception;
	/**
	 * 获取部门收入详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public DeptIncome getDeptIncomeInfo(String id) throws Exception;
	/**
	 * 修改部门收入
	 * @param deptIncome
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public boolean editDeptIncome(DeptIncome deptIncome) throws Exception;
	/**
	 * 删除部门收入
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public boolean deleteDeptIncome(String id) throws Exception;
	/**
	 * 审核部门收入
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public boolean auditDeptIncome(String id) throws Exception;
	/**
	 * 反审部门收入
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public boolean oppauditDeptIncome(String id) throws Exception;
	/**
	 * 部门收入结算 分摊到各供应商中去
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月22日
	 */
	public boolean updateDeptIncomeSettle(String year,String month) throws Exception;
	/**
	 * 添加并且审核 部门收入单
	 * @param deptIncome
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public boolean addAndAuditDeptIncome(DeptIncome deptIncome) throws Exception;
	/**
	 * 显示部门日常费月统计报表
	 * @param map
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月13日
	 */
	public PageData getDeptIncomeReportPageData(PageMap pageMap) throws Exception;
	/**
	 * 获取部门收入导出数据
	 * @param map
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月13日
	 */
	public List getDeptIncomeReportExportData(Map map) throws Exception;
	/**
	 * 获取部门收入导出合计数据
	 * @param map
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月13日
	 */
	public List getDeptIncomeReportExportSumData(Map map) throws Exception;
	/**
	 * 部门分供应商费用明细报表分页数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-26
	 */
	public PageData getDeptSupplierIncomeReportPageData(PageMap pageMap) throws Exception;
	/**
	 * 获取供应商下的收入科目明细数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月14日
	 */
	public PageData getSupplierSubjectDetailData(PageMap pageMap) throws Exception;
	/**
	 * 部门分供应商费用明细按年报表分页数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-26
	 */
	public PageData getDeptSupplierIncomeYearReportPageData(PageMap pageMap) throws Exception;
	/**
	 * 获取指定供应商下的收入科目报表数据
	 * @param map
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月14日
	 */
	public List showSupplierSubjectReportData(PageMap pageMap) throws Exception;
	/**
	 * 显示部门日常费月按年统计报表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-11-27
	 */
	public PageData getDeptIncomeYearReportPageData(PageMap pageMap) throws Exception;
	
	/**
	 * 根据OA编号查询部门收入
	 * @param oaid
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-10
	 */
	public List<DeptIncome> selectDeptIncomeByOaid(String oaid) throws Exception;

	/**
	 * 部门收入单(用于OA驳回操作)
	 * @param deptIncome
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-10
	 */
	public boolean rollbackAndAuditDeptIncome(DeptIncome cost) throws Exception;
	/**
	 * 根据map中，获取部门收入信息
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2016年1月27日
	 */
	public List<DeptIncome> showDeptIncomeListBy(Map map) throws Exception;
	/**
	 * 获取只获取最原始的部门收入单信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2016年1月27日
	 */
	public DeptIncome getDeptIncomePureInfo(String id) throws Exception;
	
	/**
	 * 更新调拨单打印次数
	 * @param list
	 * @author zhanghonghui 
	 * @date 2013-9-10
	 */
	public boolean updateDeptIncomePrinttimes(DeptIncome deptIncome) throws Exception;
	/**
	 * 更新打印次数
	 * @param list
	 * @author zhanghonghui 
	 * @date 2013-9-10
	 */
	public void updateDeptIncomePrinttimes(List<DeptIncome> list) throws Exception;
}

