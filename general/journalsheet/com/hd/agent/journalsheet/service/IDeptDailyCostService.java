/**
 * @(#)IDeptDailyCostService.java
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
import com.hd.agent.journalsheet.model.DeptDailyCost;

/**
 * 
 * 部门日常费用service
 * @author chenwei
 */
public interface IDeptDailyCostService {
	/**
	 * 添加部门日常费用
	 * @param deptDailyCost
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public boolean addDeptDailyCost(DeptDailyCost deptDailyCost) throws Exception;
	/**
	 * 获取部门日常费用列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public PageData showDeptDailyCostList(PageMap pageMap) throws Exception;
	/**
	 * 获取部门日常费用详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public DeptDailyCost getDeptDailyCostInfo(String id) throws Exception;
	/**
	 * 修改部门日常费用
	 * @param deptDailyCost
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public boolean editDeptDailyCost(DeptDailyCost deptDailyCost) throws Exception;
	/**
	 * 删除部门日常费用
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public boolean deleteDeptDailyCost(String id) throws Exception;
	/**
	 * 审核部门日常费用
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public boolean auditDeptDailyCost(String id) throws Exception;
	/**
	 * 反审部门日常费用
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public boolean oppauditDeptDailyCost(String id) throws Exception;
	/**
	 * 部门日常费用结算 分摊到各供应商中去
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月22日
	 */
	public boolean updateDeptDailyCostSettle(String year,String month) throws Exception;
	/**
	 * 添加并且审核 部门日常费用单
	 * @param deptDailyCost
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public boolean addAndAuditDeptDailyCost(DeptDailyCost deptDailyCost) throws Exception;
	/**
	 * 显示部门日常费月统计报表
	 * @param map
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月13日
	 */
	public PageData getDeptDailyCostCostReportPageData(PageMap pageMap) throws Exception;
	/**
	 * 获取部门日常费用导出数据
	 * @param map
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月13日
	 */
	public List getDeptDailyCostCostReportExportData(Map map) throws Exception;
	/**
	 * 获取部门日常费用导出合计数据
	 * @param map
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月13日
	 */
	public List getDeptDailyCostCostReportExportSumData(Map map) throws Exception;
	/**
	 * 部门分供应商费用明细报表分页数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-26
	 */
	public PageData getDeptDailySupplierCostReportPageData(PageMap pageMap) throws Exception;
	/**
	 * 获取供应商下的费用科目明细数据
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
	public PageData getDeptDailySupplierCostYearReportPageData(PageMap pageMap) throws Exception;
	/**
	 * 获取指定供应商下的费用科目报表数据
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
	public PageData getDeptDailyCostCostYearReportPageData(PageMap pageMap) throws Exception;
	
	/**
	 * 根据OA编号查询部门日常费用
	 * @param oaid
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-10
	 */
	public List<DeptDailyCost> selectDeptDailyCostByOaid(String oaid) throws Exception;

	/**
	 * 部门日常费用单(用于OA驳回操作)
	 * @param deptDailyCost
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-10
	 */
	public boolean rollbackAndAuditDeptDailyCost(DeptDailyCost cost) throws Exception;
	/**
	 * 根据map中，获取部门日常费用信息
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2016年1月27日
	 */
	public List<DeptDailyCost> showDeptDailyCostListBy(Map map) throws Exception;
	/**
	 * 获取只获取最原始的部门日常费用单信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2016年1月27日
	 */
	public DeptDailyCost getDeptDailyCostPureInfo(String id) throws Exception;
	
	/**
	 * 更新调拨单打印次数
	 * @author zhanghonghui 
	 * @date 2013-9-10
	 */
	public boolean updateDeptDailyCostPrinttimes(DeptDailyCost deptDailyCost) throws Exception;
	/**
	 * 更新打印次数
	 * @param list
	 * @author zhanghonghui 
	 * @date 2013-9-10
	 */
	public void updateDeptDailyCostPrinttimes(List<DeptDailyCost> list) throws Exception;
    /**
     * 根据编号获取凭证数据
     * @author lin_xx
     * @date 2016-9-10
     */
    public List<Map> getDeptDailyCostSumData (List<String> idarr) throws Exception;

	/**
	 * 生成凭证的数据按单据获取
	 * @param idarr
	 * @return java.util.List<java.util.Map>
	 * @throws
	 * @author luoqiang
	 * @date Dec 20, 2017
	 */
	public List<Map> getDeptDailyCostSumDataForThird (List<String> idarr) throws Exception;

	/**
	 * 人员日常费用报表数据获取
	 * @param pageMap
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author luoqiang
	 * @date Feb 12, 2018
	 */
	public PageData getPersonnelDailyCostCostReportPageData(PageMap pageMap) throws Exception;

	/**
	 * 导出人员日常费用数据
	 * @param map
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Feb 12, 2018
	 */
	public List getPersonnelDailyCostCostReportExportData(Map map) throws Exception;

	/**
	 * 获取人员日常费用导出合计数据
	 * @param map
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2015年10月13日
	 */
	public List getPersonnelDailyCostCostReportExportSumData(Map map) throws Exception;
}

