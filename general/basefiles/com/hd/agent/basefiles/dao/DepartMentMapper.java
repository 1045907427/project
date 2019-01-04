/**
 * @(#)DepartMentMapper.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-21 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public interface DepartMentMapper {
	
	/**
	 * 获取部门档案列表(树型)部门名称
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-28
	 */
	public List getDepartmentNameList();
	/**
	 * 获取部门档案启用的列表
	 * @return
	 * @author chenwei 
	 * @date Mar 11, 2013
	 */
	public List showDepartmentOpenList();
	/**
	 * 获取部门档案列表，分页
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-28
	 */
	public List getDepartmentList(PageMap pageMap);

	/**
	 * 获取部门档案数量
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-28
	 */
	public int getDepartmentCount(PageMap pageMap);
	
	/**
	 * 根据部门编码id获取部门信息
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-23
	 */
	public DepartMent getDepartmentInfo(@Param("id")String id);
	
	/**
	 * 根据部门名称获取部门信息
	 * @param name
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 22, 2014
	 */
	public DepartMent getDepartmentInfoLimitOne(@Param("name")String name);
	
	/**
	 * 新增部门
	 * @param departMent
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-24
	 */
	public int addDepartment(DepartMent departMent);
	
	/**
	 * 修改部门信息
	 * @param departMent
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-24
	 */
	public int editDepartMent(DepartMent departMent);
	
	/**
	 * 修改部门类时,下级部门的变化
	 * @param departMent
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-26
	 */
	public int editDeptMentAllNextChange(DepartMent departMent);
	/**
	 * 根据部门编码id删除部门
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-24
	 */
	public int deleteDepartMent(@Param("id")String id);
	
	/**
	 * 根据部门编码id禁用部门
	 * @param departMent
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-24
	 */
	public int disableDepartMent(DepartMent departMent);
	
	/**
	 * 根据部门编码id启用部门
	 * @param departMent
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-24
	 */
	public int enableDepartMent(DepartMent departMent);
	
	/**
	 * 检验是否存在该部门id
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-25
	 */
	public String isExistDepartmentId(@Param("id")String id);
	
	/**
	 * 删除整个部门类
	 * @param delDeptIdArr
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-25
	 */
	public int deleteDepartMentAll(@Param("delDeptIdArr")String[] delDeptIdArr);
	
	/**
	 * 禁用整个部门类
	 * @param paramMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-25
	 */
	public int disableDepartMentAll(Map paramMap);
	
	/**
	 * 启用整个部门类
	 * @param paramMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-25
	 */
	public int enableDepartMentAll(Map paramMap);
	
	/**
	 * 获取表数据库中的部门
	 * @param queryMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-29
	 */
	public List getDBUnimportDeptList(Map queryMap);
	
	/**
	 * 部门档案中“状态”为“启用”且“末级标志”为“是”的部门；
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-31
	 */
	public List getDeptListByQuery();
	
	/**
	 * 根据部门id获取部门名称
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-26
	 */
	public String getDeptName(@Param("id")String id);
	
	/**
	 * 判断pid是否存在,若存在则为末及标志，否则，不是
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-5
	 */
	public String setIsTreeLeaf(@Param("id")String id);
	
	/**
	 * 状态为3暂存2保存的不作末级标志获取的列表 
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-19
	 */
	public List getDeptListByState();
	
	/**
	 * 根据父级Id获取父级与其下自己的整个部门列表
	 * @param pId
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-22
	 */
	public List getPidAllDeptList(@Param("pId")String pId);
	
	/**
	 * 根据部门id数组字符串获取部门详情列表
	 * @param idsArr
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-28
	 */
	public List<DepartMent> getDeptListByIdsStr(@Param("idsArr")String[] idsArr);
	
	/**
	 * 检查部门名称的唯一性
	 * @param name
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-5
	 */
	public String checkSoleName(@Param("name")String name);
	
	/**
	 * 获取所有部门列表
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-17
	 */
	public List<DepartMent> getAllDeptList();
	
	/**
	 * 根据业务属性depttype，获取部门列表
	 * @param depttypeStr
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-17
	 */
	public List<DepartMent> getDeptListByOperType(@Param("depttypeStr")String depttypeStr);
	
	public List returnDeptIdByName(String name);
	
	/**
	 * 根据对应的参数获取部门列表
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 11, 2014
	 */
	public List getDeptListByParam(Map map);
	/**
	 * 获取顶级部门列表
	 * @return
	 * @author chenwei 
	 * @date 2014年11月22日
	 */
	public List getPidDeptList();
	/**
	 * 根据关联仓库获取部门
	 * @param storageid
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年1月13日
	 */
	public DepartMent getDepartmentInfoByStorage(@Param("storageid")String storageid);

    /**
     * 获取除父级分类外所有自己部门列表数据
     * @author panxiaoxiao
     * @date 2015-03-19
     */
    public List<DepartMent> getDepartMentChildList(@Param("pid")String pid);
    /**
     * 批量修改部门
     * @param childList
     * @return
     * @author panxiaoxiao
     * @date 2015-03-19
     */
    public int editDepartMentBatch(List<DepartMent> childList);

	/**
	 * 根据部门档案更新客户档案中的销售部门名称
	 * @return
	 */
	public int updateCustomerSalesdeptname(@Param("deptid")String deptid);

	/**
	 * 读取全部的部门list
	 *
	 * @return
	 * @author limin
	 * @date Aug 8, 2016
     */
	public List<DepartMent> getDeptListForMecshop();
}

