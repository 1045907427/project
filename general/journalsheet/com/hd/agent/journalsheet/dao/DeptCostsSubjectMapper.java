/**
 * @(#)SysCodeMapper.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-15 panxiaoxiao 创建版本
 */
package com.hd.agent.journalsheet.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.journalsheet.model.DeptCostsSubject;

/**
 * 
 * 部门费用科目相关数据库操作
 * @author zhang_hh
 */
public interface DeptCostsSubjectMapper {
	/**
	 * 获取部门费用科目列表
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public List getDeptCostsSubjectList();
	/**
	 * 获取部门费用科目启用列表，state='1'
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public List showDeptCostsSubjectEnableList();

	/**
	 * 根据map中参数，获取部门费用科目有效列表<br/>
	 * map中参数：<br/>
	 * cols:字段权限<br/>
	 * dataSql：数据权限<br/>
	 * state:状态<br/>
	 * likeid: 模糊查询 id%<br/>
	 * leaf ：是否末级，0不是，1是
	 * nameLenZero ： 名称是否为空<br/>
	 * @param map
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public List getDeptCostsSubjectListByMap(Map map);
	
	
	/**
	 * 根据条件获取部门费用科目列表
	 * @param pageMap
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public List getDeptCostsSubjectPageList(PageMap pageMap);
	
	/**
	 * 根据条件获取部门费用科目分页总数量
	 * @param pageMap
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public int getDeptCostsSubjectCount(PageMap pageMap);
	
	/**
	 * 获取部门费用科目详情信息
	 * @param id
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public DeptCostsSubject getDeptCostsSubjectById(@Param("id")String id);
	
	/**
	 * 添加部门费用科目
	 * @param deptCostsSubject
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public int addDeptCostsSubject(DeptCostsSubject deptCostsSubject);
	
	/**
	 * 修改部门费用科目信息
	 * @param deptCostsSubject
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public int updateDeptCostsSubject(DeptCostsSubject deptCostsSubject);
	
	/**
	 * 禁用代码
	 * @param id
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public int disableDeptCostsSubject(DeptCostsSubject deptCostsSubject);
	
	/**
	 * 启用代码
	 * @param id 
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-19
	 */
	public int enableDeptCostsSubject(DeptCostsSubject deptCostsSubject);
	
	/**
	 * 删除代码
	 * @param paramMap
	 * @return
	 * @author zhang_hh 
	 * @date 2013-3-27
	 */
	public int deleteDeptCostsSubjectById(@Param("id")String id);
	
	/**
	 * 根据条件获取部门费用科目数量
	 * @param map
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public int getDeptCostsSubjectCountByMap(Map map);
	/**
	 * 根据map中参数，获取部门费用科目<br/>
	 * map中参数：<br/>
	 * id : 编号<br/>
	 * cols:字段权限<br/>
	 * dataSql：数据权限<br/>
	 * state:状态<br/>
	 * @param map
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public DeptCostsSubject getDeptCostsSubjectByMap(Map map);
	/**
	 * 批量更新部门费用科目
	 * @param list 部门费用列表信息
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年9月28日
	 */
	public int updateDeptCostsSubjectBatch(List<DeptCostsSubject> list );
	/**
	 * 科目名称是否被使用
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年9月28日
	 */
	public int isUsedDeptCostsSubjectName(@Param("name")String name);
	/**
	 * 费用分类是否为末及标志
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年9月28日
	 */
	public String isLeafDeptCostsSubject(@Param("id")String id);
}

