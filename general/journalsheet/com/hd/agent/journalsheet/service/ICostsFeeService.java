/**
 * @(#)ICostsFeeService.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-6-19 zhanghonghui 创建版本
 */
package com.hd.agent.journalsheet.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.journalsheet.model.DepartmentCosts;
import com.hd.agent.journalsheet.model.DepartmentCostsDetail;
import com.hd.agent.journalsheet.model.DeptCostsSubject;

/**
 * 
 * 
 * @author zhanghonghui
 */
public interface ICostsFeeService {
	
	//================================================================
	//部门费用科目
	//================================================================
	/**
	 * 显示所有部门费用科目列表
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public List showDeptCostsSubjectList() throws Exception;
	/**
	 * 获取启用的部门费用科目列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-24
	 */
	public List showDeptCostsSubjectEnableList() throws Exception;
	/**
	 * 显示部门费用科目分页数据
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public PageData showDeptCostsSubjectPageList(PageMap pageMap) throws Exception;
	
	
	/**
	 * 获取部门费用科目详情
	 * @param code
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public DeptCostsSubject showDeptCostsSubjectById(String id) throws Exception;
	/**
	 * 获取部门费用科目详情，含数据权限
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年9月28日
	 */
	public DeptCostsSubject getDeptCostsSubjectDetail(String id) throws Exception;
	/**
	 * 添加部门费用科目
	 * @param deptCostsSubject
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public Map addDeptCostsSubject(DeptCostsSubject deptCostsSubject) throws Exception;
	
	/**
	 * 修改部门费用科目
	 * @param deptCostsSubject
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public Map editDeptCostsSubject(DeptCostsSubject deptCostsSubject) throws Exception;
	
	/**
	 * 禁用费用科目
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public Map disableDeptCostsSubject(String id) throws Exception;
	
	/**
	 * 启用费用科目
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-19
	 */
	public boolean enableDeptCostsSubject(String id) throws Exception;
	
	/**
	 * 根据编码删除费用科目
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2013-3-27
	 */
	public Map deleteDeptCostsSubjectById(String id)throws Exception;
	/**
	 * 指删除费用科目
	 * @param idarrs
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-23
	 */
	public Map deleteDeptCostsSubjectMore(String idarrs)throws Exception;
	/**
	 * 根据map中参数获取费用科目列表
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年9月25日
	 */
	public List showDeptCostsSubjectListByMap(Map map) throws Exception;
	/**
	 * 获取父节点及其全部子节点<br/>
	 * map 中参数：<br/>
	 * id 或likeid : 模糊查询 id%<br/>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年9月28日
	 */
	public List getDeptCostsSubjectParentAllChildren(Map map) throws Exception;
	/**
	 * 科目名称是否被使用
	 * @param name
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年9月28日
	 */
	public boolean isUsedDeptCostsSubjectName(String name) throws Exception;
	
	/**
     * Excel费用科目导入列表数据
     * @param list
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2015年9月28日
     */
    public Map addDRDeptCostsSubjectExcel(List<DeptCostsSubject> list)throws Exception;
	
	
	//================================================================
	//部门费用
	//================================================================
	
	
	/**
	 * 部门费用分页数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public PageData getDepartmentCostsPageData(PageMap pageMap) throws Exception;
	/**
	 * 添加部门费用
	 * @param requestMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public Map<String, Object> addDepartmentCosts(Map<String,Object> requestMap) throws Exception;
	/**
	 * 编辑部门费用
	 * @param requestMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public Map<String, Object> editDepartmentCosts(Map<String,Object> requestMap) throws Exception;
	/**
	 * 删除部门费用
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public boolean deleteDepartmentCosts(String id) throws Exception;
	
	/**
	 * 批量删除部门费用
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public Map<String,Object> deleteDepartmentCostsMore(String idarrs)throws Exception;
	/**
	 * 批量审核部门费用
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public Map<String,Object> auditDepartmentCostsMore(String idarrs)throws Exception;
	/**
	 * 批量反审核部门费用
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public Map<String,Object> oppauditDepartmentCostsMore(String idarrs)throws Exception;
	/**
	 * 显示部门费用<br/>
	 * map中参数：<br/>
	 * id: 费用编码，必须的<br/>
	 * showDetail:是否查询费用明细列表,默认为true,不显示为false<br/>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-28
	 */
	public DepartmentCosts showDepartmentCostsByMap(Map map) throws Exception;
	/**
	 * 对科目列表，赋对应费用值
	 * @param subjectList
	 * @param detailList
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-28
	 */
	public void setDeptCostsSubjectListWithAmount(List<DeptCostsSubject> subjectList,List<DepartmentCostsDetail> detailList) throws Exception;
	
	//================================================================
	//供应商费用分摊
	//================================================================
	public PageData getDeptSupplierCostsPageData(PageMap pageMap) throws Exception;
	//================================================================
	//报表
	//================================================================
	/**
	 * 部门费用明细报表分页数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public PageData getDepartmentCostsMonthPageData(PageMap pageMap) throws Exception;
	/**
	 * 部门分供应商费用明细报表分页数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-26
	 */
	public PageData getDeptSupplierCostsMonthPageData(PageMap pageMap) throws Exception;

    /**
     * 分客户投入产出比报表分页数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2014-9-6
     */
    public PageData getCustomerInputOutputData(PageMap pageMap) throws Exception;
    /**
     * 分客户投入产出比报表 查询费用合计明细数据（客户应付费用报表 借方金额）
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016-7-26
     */
    public PageData getPayableDetailList(PageMap pageMap) throws Exception ;
	/**
	 * 分客户投入产出比报表 查询费用支出明细数据(日常费用录入金额)
	 * @return
	 * @throws Exception
	 * @author lin_xx
	 * @date 2016-10-20
	 */
	public PageData getDailyCostDetailList(PageMap pageMap) throws Exception ;

    /**
     * 分客户投入产出比报表分页数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2014-9-12
     */
    public PageData getSalesuserInputOutputData(PageMap pageMap) throws Exception;

}

