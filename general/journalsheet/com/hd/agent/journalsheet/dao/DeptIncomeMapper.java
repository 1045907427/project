package com.hd.agent.journalsheet.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.journalsheet.model.DeptIncome;
import com.hd.agent.journalsheet.model.DeptIncomeSupplier;

/**
 * 部门收入dao
 * @author chenwei
 */
public interface DeptIncomeMapper {
	/**
	 * 添加部门收入
	 * @param deptIncome
	 * @return
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public int addDeptIncome(DeptIncome deptIncome);
	/**
	 * 获取部门收入列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public List showDeptIncomeList(PageMap pageMap);
	/**
	 * 获取部门收入列表数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public int showDeptIncomeListCount(PageMap pageMap);
	/**
	 * 获取部门收入列表合计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年10月9日
	 */
	public DeptIncome showDeptIncomeListSum(PageMap pageMap);
	/**
	 * 获取部门收入详情
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public DeptIncome getDeptIncomeInfo(@Param("id")String id);
	/**
	 * 修改部门收入
	 * @param deptIncome
	 * @return
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public int editDeptIncome(DeptIncome deptIncome);
	/**
	 * 删除部门收入
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public int deleteDeptIncome(@Param("id")String id);
	/**
	 * 审核部门收入
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public int auditDeptIncome(@Param("id")String id,@Param("userid")String userid,@Param("username")String username);
	/**
	 * 反审部门收入
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public int oppauditDeptIncome(@Param("id")String id,@Param("userid")String userid,@Param("username")String username);
	/**
	 * 根据部门编号 获取该部门 各科目合计费用
	 * @param year
	 * @param month
	 * @param deptid
	 * @return
	 * @author chenwei 
	 * @date 2014年11月22日
	 */
	public List getDeptIncomeSumByDeptid(@Param("year")String year,@Param("month")String month,@Param("deptid")String deptid);
	/**
	 * 根据条件，统计金额<br/>
	 * map中参数：<br/>
	 * begintime:开始时间<br/>
	 * end:结束时间<br/>
	 * businessyearmonth:按年月，格式yyyy-MM<br/>
	 * deptid :部门<br/>
	 * isAudit : 2，只查询 status='3' or status='4' <br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-12-3
	 */
	public Map getDeptIncomeSumsAll(Map map);
	/**
	 * 添加供应商收入
	 * @param deptIncomeSupplier
	 * @return
	 * @author chenwei 
	 * @date 2014年11月22日
	 */
	public int addDeptIncomeSupplier(DeptIncomeSupplier deptIncomeSupplier);
	/**
	 * 删除供应商收入
	 * @param year
	 * @param month
	 * @return
	 * @author chenwei 
	 * @date 2014年11月22日
	 */
	public int deleteDeptIncomeSupplier(@Param("year")String year,@Param("month")String month);
	/**
	 * 关闭部门收入单
	 * @param year
	 * @param month
	 * @return
	 * @author chenwei 
	 * @date 2014年11月22日
	 */
	public int closeDeptIncome(@Param("year")String year,@Param("month")String month);
	
	// ==================================================================================
	// 报表 
	// ==================================================================================
	
	/**
	 * 部门收入分页明细列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public List getDeptIncomeReportPageList(Map map);

	/**
	 * 部门收入分页明细，金额合计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public Map getDeptIncomeReportPageSums(Map map);
	/**
	 * 根据部门编号获取该部门收入科目数据
	 * @param deptid
	 * @param map
	 * @return
	 * @author chenwei 
	 * @date 2015年10月13日
	 */
	public List getDeptIncomeSubjectList(@Param("deptid")String deptid,@Param("con")Map map);
	
	/**
	 * 根据部门编号获取该部门收入科目数据
	 * @param deptid
	 * @param map
	 * @return
	 * @author chenwei 
	 * @date 2015年10月13日
	 */
	public List getDeptIncomeSubjectListData(@Param("deptid")String deptid,@Param("con")Map map);
	
	/**
	 * 部门收入分页按年明细列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public List getDeptIncomeYearReportPageList(PageMap pageMap);

	/**
	 * 部门收入分页按年明细列表,条数计数
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public int getDeptIncomeYearReportPageCount(PageMap pageMap);
	/**
	 * 部门收入分页按年明细，金额合计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public Map getDeptIncomeYearReportPageSums(PageMap pageMap);
	
	
	/**
	 * 部门分供应商收入摊派分页列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public List getDeptSupplierIncomeReportPageList(PageMap pageMap);

	/**
	 * 部门分供应商收入摊派分页明细列表,条数计数
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public int getDeptSupplierIncomeReportPageCount(PageMap pageMap);
	/**
	 * 部门分供应商收入摊派分页明细，金额合计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public Map getDeptSupplierIncomeReportPageSums(PageMap pageMap);
	/**
	 * 供应商收入摊派收入科目数据列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public List getSupplierSubjectReportList(PageMap pageMap);
	/**
	 * 获取供应商收入科目明细数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2015年10月14日
	 */
	public List getSupplierSubjectDetailData(PageMap pageMap);
	/**
	 * 根据OA编号查询部门费用
	 * @param oaid
	 * @return
	 * @author limin 
	 * @date 2015-1-10
	 */
	public List<DeptIncome> selectDeptIncomeByOaid(@Param("oaid")String oaid);
	/**
	 * 根据map中参数查询部门费用<br/>
	 * map中参数:<br/>
	 * dataSql：数据权限<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * status: 表示状态3<br/>
	 * statusarr: 表示状态，类似 1,2,3<br/>
	 * deptid: 部门编号<br/>
	 * costsort: 科目<br/>
	 * costsortlike: 模板查询，以costsortlike开头的科目<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2016年1月27日
	 */
	public List<DeptIncome> showDeptIncomeListBy(Map map);
	/**
	 * 更新打印次数
	 * @param id
	 * @param printtimes
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-30
	 */
	public int updateDeptIncomePrinttimes(DeptIncome deptIncome);
}