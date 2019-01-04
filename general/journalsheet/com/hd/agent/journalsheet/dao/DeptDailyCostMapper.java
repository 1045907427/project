package com.hd.agent.journalsheet.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.journalsheet.model.DeptDailyCost;
import com.hd.agent.journalsheet.model.DeptDailyCostSupplier;

/**
 * 部门日常费用dao
 * @author chenwei
 */
public interface DeptDailyCostMapper {
	/**
	 * 添加部门日常费用
	 * @param deptDailyCost
	 * @return
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public int addDeptDailyCost(DeptDailyCost deptDailyCost);
	/**
	 * 获取部门日常费用列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public List showDeptDailyCostList(PageMap pageMap);
	/**
	 * 获取部门日常费用列表数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public int showDeptDailyCostListCount(PageMap pageMap);
	/**
	 * 获取部门日常费用列表合计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年10月9日
	 */
	public DeptDailyCost showDeptDailyCostListSum(PageMap pageMap);
	/**
	 * 获取部门日常费用详情
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public DeptDailyCost getDeptDailyCostInfo(@Param("id")String id);
	/**
	 * 修改部门日常费用
	 * @param deptDailyCost
	 * @return
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public int editDeptDailyCost(DeptDailyCost deptDailyCost);
	/**
	 * 删除部门日常费用
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public int deleteDeptDailyCost(@Param("id")String id);
	/**
	 * 审核部门日常费用
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public int auditDeptDailyCost(@Param("id")String id,@Param("userid")String userid,@Param("username")String username);
	/**
	 * 反审部门日常费用
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public int oppauditDeptDailyCost(@Param("id")String id,@Param("userid")String userid,@Param("username")String username);
	/**
	 * 根据部门编号 获取该部门 各科目合计费用
	 * @param year
	 * @param month
	 * @param deptid
	 * @return
	 * @author chenwei 
	 * @date 2014年11月22日
	 */
	public List getDeptDailyCostSumByDeptid(@Param("year")String year,@Param("month")String month,@Param("deptid")String deptid);
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
	public Map getDeptDailyCostSumsAll(Map map);
	/**
	 * 根据条件，统计金额<br/>
	 * map中参数：<br/>
	 * begintime:开始时间<br/>
	 * end:结束时间<br/>
	 * businessyearmonth:按年月，格式yyyy-MM<br/>
	 * deptid :部门<br/>
	 * supplierid :供应商<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui
	 * @date 2014-12-3
	 */
	public Map getDeptDailyCostSupplierSum(Map map);
	/**
	 * 添加供应商日常费用
	 * @param deptDailyCostSupplier
	 * @return
	 * @author chenwei 
	 * @date 2014年11月22日
	 */
	public int addDeptDailyCostSupplier(DeptDailyCostSupplier deptDailyCostSupplier);
	/**
	 * 删除供应商日常费用
	 * @param year
	 * @param month
	 * @return
	 * @author chenwei 
	 * @date 2014年11月22日
	 */
	public int deleteDeptDailyCostSupplier(@Param("year")String year,@Param("month")String month);
	/**
	 * 关闭部门日常费用单
	 * @param year
	 * @param month
	 * @return
	 * @author chenwei 
	 * @date 2014年11月22日
	 */
	public int closeDeptDailyCost(@Param("year")String year,@Param("month")String month);
	
	// ==================================================================================
	// 报表 
	// ==================================================================================
	
	/**
	 * 部门日常费用分页明细列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public List getDeptDailyCostReportPageList(Map map);

	/**
	 * 部门日常费用分页明细，金额合计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public Map getDeptDailyCostReportPageSums(Map map);
	/**
	 * 根据部门编号获取该部门费用科目数据
	 * @param deptid
	 * @param map
	 * @return
	 * @author chenwei 
	 * @date 2015年10月13日
	 */
	public List getDeptDailySubjectList(@Param("deptid")String deptid,@Param("con")Map map);
	
	/**
	 * 根据部门编号获取该部门费用科目数据
	 * @param deptid
	 * @param map
	 * @return
	 * @author chenwei 
	 * @date 2015年10月13日
	 */
	public List getDeptDailySubjectListData(@Param("deptid")String deptid,@Param("con")Map map);
	
	/**
	 * 部门日常费用分页按年明细列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public List getDeptDailyCostYearReportPageList(PageMap pageMap);

	/**
	 * 部门日常费用分页按年明细列表,条数计数
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public int getDeptDailyCostYearReportPageCount(PageMap pageMap);
	/**
	 * 部门日常费用分页按年明细，金额合计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public Map getDeptDailyCostYearReportPageSums(PageMap pageMap);
	
	
	/**
	 * 部门分供应商日常费用摊派分页列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public List getDeptDailySupplierCostReportPageList(PageMap pageMap);

	/**
	 * 部门分供应商日常费用摊派分页明细列表,条数计数
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public int getDeptDailySupplierCostReportPageCount(PageMap pageMap);
	/**
	 * 部门分供应商日常费用摊派分页明细，金额合计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public Map getDeptDailySupplierCostReportPageSums(PageMap pageMap);
	/**
	 * 供应商日常费用摊派费用科目数据列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public List getSupplierSubjectReportList(PageMap pageMap);
	/**
	 * 获取供应商费用科目明细数据
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
	public List<DeptDailyCost> selectDeptDailyCostByOaid(@Param("oaid")String oaid);
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
	public List<DeptDailyCost> showDeptDailyCostListBy(Map map);
	/**
	 * 更新打印次数
	 * @param id
	 * @param printtimes
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-30
	 */
	public int updateDeptDailyCostPrinttimes(DeptDailyCost deptDailyCost);

    public List<Map> getDeptDailyCostSumData(List<String> idarr);

	/**
	 * 按单据获取凭证数据
	 * @param idarr
	 * @return java.util.List<java.util.Map>
	 * @throws
	 * @author luoqiang
	 * @date Dec 20, 2017
	 */
	public List<Map> getDeptDailyCostSumDataForThird(List<String> idarr);

	/**
	 * 人员日常费用分页明细列表
	 * @param map
	 * @return
	 * @author zhanghonghui
	 * @date 2014-6-20
	 */
	public List getPersonnelDailyCostReportPageList(Map map);

	/**
	 * 部门日常费用分页明细，金额合计
	 * @param map
	 * @return
	 * @author zhanghonghui
	 * @date 2014-6-20
	 */
	public Map getPersonnelDailyCostReportPageSums(Map map);

	/**
	 * 获取人员下的费用科目金额
	 * @param personnelid
	 * @param map
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Feb 12, 2018
	 */
	public List getPersonnelDailySubjectList(@Param("personnelid")String personnelid,@Param("con")Map map);
}