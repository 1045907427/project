/**
 * @(#)CostsFeeMapper.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-6-19 zhanghonghui 创建版本
 */
package com.hd.agent.journalsheet.dao;

import java.util.List;
import java.util.Map;

import com.hd.agent.journalsheet.model.*;
import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;

/**
 * 
 * 费用 Mapper
 * @author zhanghonghui
 */
public interface CostsFeeMapper {
	/**
	 * 部门费用分页列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public List getDepartmentCostsPageList(PageMap pageMap);

	/**
	 * 部门费用分页列表,条数计数
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public int getDepartmentCostsPageCount(PageMap pageMap);
	/**
	 * 部门费用分页，金额合计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public Map getDepartmentCostsPageSums(PageMap pageMap);
	
	/**
	 * 根据Map中参数，计算部门费用数<br/>
	 * Map中参数：<br/>
	 * businessdate:业务日期<br/>
	 * deptid:部门编码
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-25
	 */
	public int getDepartmentCostsCountByMap(Map map);
	
	/**
	 * 添加部门费用
	 * @param departmentCosts
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public int insertDepartmentCosts(DepartmentCosts departmentCosts);
	/**
	 * 更新部门费用
	 * @param departmentCosts
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public int updateDepartmentCosts(DepartmentCosts departmentCosts);
	/**
	 * 更新部门费用
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public int updateDepartmentCostsByMap(Map map);
	/**
	 * 删除部门费用
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public int deleteDepartmentCosts(String id);
	/**
	 * 删除部门费用信息
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-27
	 */
	public int deleteDepartmentCostsByMap(Map map);
	/**
	 * 根据map中参数获取费用信息<br/>
	 * map中参数：<br/>
	 * id: 编号<br/>
	 * businessdate:业务日期<br/>
	 * deptid：部门编号<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-27
	 */
	public DepartmentCosts getDepartmentCostsByMap(Map map);
	/**
	 * 获取费用信息<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-27
	 */
	public DepartmentCosts getDepartmentCosts(String id);

	/**
	 * 部门费用分页，金额合计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public Map getDepartmentCostsSumsAll(PageMap pageMap);
	
	/**
	 * 费用明细 ==================================================================================
	 */
	/**
	 * 添加部门费用明细
	 * @param departmentCosts
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public int insertDepartmentCostsDetail(DepartmentCostsDetail departmentCostsDetail);
	/**
	 * 更新部门费用明细
	 * @param departmentCosts
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public int updateDepartmentCostsDetail(DepartmentCostsDetail departmentCostsDetail);
	/**
	 * 根据费用编码，删除部门费用明细
	 * @param deptcostsid
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public int deleteDepartmentCostsDetailByDeptcostsId(String deptcostsid);
	/**
	 * 根据Map中参数，计算明细条数<br/>
	 * Map中参数：<br/>
	 * subjectid:科目编码<br/>
	 * deptcostsid:费用编号
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-26
	 */
	public int getDepartmentCostsDetailCountByMap(Map map);
	/**
	 * 根据费用编码，获取各科目费用
	 * @param deptcostsid
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-28
	 */
	public List getDepartmentCostsDetailListByDeptcostsId(String deptcostsid);
	
	// 费用供应商摊派明细 ==================================================================================
	/**
	 * 获取分供应商各科目费用
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-28
	 */
	 public List getDeptSupplierCostsPageList(PageMap pageMap);
	 /**
	  * 获取分供应商各科目费用，合计条数
	  * @param pageMap
	  * @return
	  * @author zhanghonghui 
	  * @date 2014-6-28
	  */
	 public int getDeptSupplierCostsPageCount(PageMap pageMap);
	 /**
	  * 获取分供应商各科目费用，合计金额
	  * @param pageMap
	  * @return
	  * @author zhanghonghui 
	  * @date 2014-7-19
	  */
	 public Map getDeptSupplierCostsPageSums(PageMap pageMap);
	/**
	 * 新增供应商摊派
	 * @param deptSupplierCosts
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-10
	 */
	public int insertDeptSupplierCosts(DeptSupplierCosts deptSupplierCosts);
	/**
	 * 更新供应商摊派
	 * @param deptSupplierCosts
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-10
	 */
	public int updateDeptSupplierCosts(DeptSupplierCosts deptSupplierCosts);
	/**
	 * 根据部门费用编号删除供应商摊派
	 * @param deptcostsid
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-10
	 */
	public int deleteDeptSupplierCostsByDeptcostsId(String deptcostsid);
	/**
	 * 根据部门费用编号删除供应商摊派
	 * @param deptcostsid
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-10
	 */
	public int deleteDeptSupplierCostsBy(@Param("deptcostsid")String deptcostsid,@Param("subjectid")String subjectid);
	/**
	 * 根据费用编号或科目编号或供应商，求相关供应商摊派条数
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-10
	 */
	public int getDeptSupplierCostsCountByMap(Map map);

	// ==================================================================================
	// 报表 
	// ==================================================================================
	// 部门费用明细报表
	/**
	 * 部门费用分页明细列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public List getDepartmentCostsMonthReportPageList(PageMap pageMap);

	/**
	 * 部门费用分页明细列表,条数计数
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public int getDepartmentCostsMonthReportPageCount(PageMap pageMap);
	/**
	 * 部门费用分页明细，金额合计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public Map getDepartmentCostsMonthReportPageSums(PageMap pageMap);
	
	/**
	 * 部门分供应商费用分页明细列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public List getDeptSupplierCostsMonthReportPageList(PageMap pageMap);

	/**
	 * 部门分供应商费用分页明细列表,条数计数
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public int getDeptSupplierCostsMonthReportPageCount(PageMap pageMap);
	/**
	 * 部门分供应商费用分页明细，金额合计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui
	 * @date 2014-6-20
	 */
	public Map getDeptSupplierCostsMonthReportPageSums(PageMap pageMap);
    /**
     * 分客户投入产出比报表数据
     * @param pageMap
     * @return
     * @author lin_xx
     * @date 2016-9-6
     */
    public List getCustomerInputOutputData(PageMap pageMap);
    /**
     * 分客户投入产出比报表数据汇总
     * @param pageMap
     * @return
     * @author lin_xx
     * @date 2016-9-6
     */
    public List getCustomerInputOutputDataSum(PageMap pageMap);
    /**
     * 分客户投入产出比报表数据合计
     * @param pageMap
     * @return
     * @author lin_xx
     * @date 2016-9-6
     */
    public int getCustomerInputOutputDataCount(PageMap pageMap);
    /**
     * 分客户投入产出比报表 费用合计数据（客户应付费用报表 借方金额）
     * @return
     * @author lin_xx
     * @date 2016-9-6
     */
    public  List<CustomerCostPayable>  getPayableDetailList(PageMap pageMap);

	public int getPayableDetailListCount(PageMap pageMap);
	 /**
	  *分客户投入产出比报表 费用支出数据(日常费用录入金额)
	  * @author lin_xx
	  * @date 2016/10/20
	  */
	public List<DeptDailyCost> getDailyCostDetailData(PageMap pageMap);

	public int getDailyCostDetailDataCount(PageMap pageMap);

}

