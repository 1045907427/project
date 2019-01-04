/**
 * @(#)IService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年11月21日 chenwei 创建版本
 */
package com.hd.agent.basefiles.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.model.Subject;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 部门service
 * @author chenwei
 */
public interface ISubjectService {
	/**
	 * ===================================================================
	 * 科目分类
	 * ===================================================================
	 */
	
	/**
	 * 添加科目分类
	 * @param deptCostsSubject
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public Map addSubjectType(Subject deptCostsSubject) throws Exception;
	
	/**
	 * 修改科目分类
	 * @param deptCostsSubject
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public Map editSubjectType(Subject deptCostsSubject) throws Exception;
	
	/**
	 * 删除科目分类
	 * @param deptCostsSubject
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public Map deleteSubjectType(String id) throws Exception;
	
	/**
	 * 启用科目分类
	 * @param deptCostsSubject
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public Map enableSubjectType(String id) throws Exception;
	
	/**
	 * 禁用科目分类
	 * @param deptCostsSubject
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public Map disableSubjectType(String id) throws Exception;
	/**
	 * 获取科目分类信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2016年2月17日
	 */
	public Subject getSubjectTypeById(String id) throws Exception;
	/**
	 * 根据分类代码获取科目分类信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2016年2月17日
	 */
	public Subject getSubjectTypeByCode(String typecode) throws Exception;
	
	/**
	 * ===================================================================
	 * 科目档案
	 * ===================================================================
	 */
	
	/**
	 * 显示所有科目档案列表
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public List showSubjectList() throws Exception;
	/**
	 * 获取启用的科目档案列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-24
	 */
	public List showSubjectEnableList(String typecode) throws Exception;
	/**
	 * 显示科目档案分页数据
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public PageData showSubjectPageList(PageMap pageMap) throws Exception;
	
	/**
	 * 获取科目档案详情，含数据权限
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年9月28日
	 */
	public Subject getSubjectDetail(String id) throws Exception;
	/**
	 * 添加科目档案
	 * @param deptCostsSubject
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public Map addSubject(Subject deptCostsSubject) throws Exception;
	
	/**
	 * 修改科目档案
	 * @param deptCostsSubject
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public Map editSubject(Subject deptCostsSubject) throws Exception;
	
	/**
	 * 禁用科目
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public Map disableSubject(String id) throws Exception;
	
	/**
	 * 启用科目
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-19
	 */
	public Map enableSubject(String id) throws Exception;
	
	/**
	 * 根据编码删除科目
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2013-3-27
	 */
	public Map deleteSubjectById(String id)throws Exception;
	/**
	 * 指删除科目
	 * @param idarrs
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-23
	 */
	public Map deleteSubjectMore(String idarrs)throws Exception;
	/**
	 * 根据map中参数获取科目列表
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年9月25日
	 */
	public List showSubjectListByMap(Map map) throws Exception;
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
	public List getSubjectParentAllChildren(Map map) throws Exception;
	
	/**
     * Excel科目导入列表数据
     * @param list
     * @param typeid
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2015年9月28日
     */
    public Map addDRSubjectExcel(List<Subject> list,String typeid)throws Exception;
    /**
     * 科目合计条数<br/>
     * map中参数：<br/>
     * id:编号<br/>
     * pid:上级编号<br/>
     * typecode:分类代码<br/>
     * typeid:分类编号<br/>
     * notCurId : 非id编号<br/>
     * @param map
     * @return
     * @throws Exception
     * @author zhanghonghui 
     * @date 2016年2月19日
     */
    public int getSubjectCountByMap(Map map) throws Exception;
    /**
	 * ===================================================================
	 * 科目档案
	 * ===================================================================
	 */
	
	/**
	 * 获取科目档案详情
	 * @param code
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public Subject showSubjectById(String id) throws Exception;
	/**
	 * 科目名称是否被使用
	 * @param name
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年9月28日
	 */
	public boolean isUsedSubjectName(String name) throws Exception;
	/**
	 * 分类代码是否被使用
	 * @param name
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年9月28日
	 */
	public boolean isUsedSubjectType(String typecode) throws Exception;
	
}

