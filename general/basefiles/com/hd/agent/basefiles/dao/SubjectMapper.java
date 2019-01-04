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
package com.hd.agent.basefiles.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.basefiles.model.Subject;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 科目档案相关数据库操作
 * @author zhang_hh
 */
public interface SubjectMapper {
	/**
	 * 获取科目档案列表
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public List getSubjectList();
	/**
	 * 获取科目档案启用列表，state='1'
	 * @param typecode
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public List showSubjectEnableList(@Param("typecode")String typecode);

	/**
	 * 根据map中参数，获取科目档案有效列表<br/>
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
	public List getSubjectListByMap(Map map);
	
	
	/**
	 * 根据条件获取科目档案列表
	 * @param pageMap
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public List getSubjectPageList(PageMap pageMap);
	
	/**
	 * 根据条件获取科目档案分页总数量
	 * @param pageMap
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public int getSubjectCount(PageMap pageMap);
	
	/**
	 * 获取科目档案详情信息
	 * @param id
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public Subject getSubjectById(@Param("id")String id);
	
	/**
	 * 添加科目档案
	 * @param deptIncomeSubject
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public int addSubject(Subject deptIncomeSubject);
	
	/**
	 * 修改科目档案信息
	 * @param deptIncomeSubject
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public int updateSubject(Subject deptIncomeSubject);
	
	/**
	 * 禁用代码
	 * @param id
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public int disableSubject(Subject deptIncomeSubject);
	
	/**
	 * 启用代码
	 * @param id 
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-19
	 */
	public int enableSubject(Subject deptIncomeSubject);
	
	/**
	 * 删除代码
	 * @param paramMap
	 * @return
	 * @author zhang_hh 
	 * @date 2013-3-27
	 */
	public int deleteSubjectById(@Param("id")String id);
	
	/**
	 * 根据条件获取科目档案数量
	 * @param map
	 * @return
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public int getSubjectCountByMap(Map map);
	/**
	 * 根据map中参数，获取科目档案<br/>
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
	public Subject getSubjectByMap(Map map);
	/**
	 * 批量更新科目档案
	 * @param list 列表信息
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年9月28日
	 */
	public int updateSubjectBatch(List<Subject> list );
	/**
	 * 科目档案名称是否被使用
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年9月28日
	 */
	public int isUsedSubjectName(@Param("name")String name);
	/**
	 * 科目档案是否为末及标志
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年9月28日
	 */
	public String isLeafSubject(@Param("id")String id);
	/**
	 * 修改科目分类
	 * @param subject
	 * @return
	 * @author zhanghonghui 
	 * @date 2016年2月17日
	 */
	public int updateSubjectType(Subject subject);
	/**
	 * 获取科目分类信息
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2016年2月17日
	 */
	public Subject getSubjectTypeById(@Param("id")String id);
	/**
	 * 根据分类代码获取科目分类信息
	 * @param typecode
	 * @return
	 * @author zhanghonghui 
	 * @date 2016年2月18日
	 */
	public Subject getSubjectTypeByCode(@Param("typecode")String typecode);
}

