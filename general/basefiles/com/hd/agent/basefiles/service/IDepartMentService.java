/**
 * @(#)IDepartMentService.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-21 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public interface IDepartMentService {
	/**
	 * 获取部门档案列表(树型)部门名称
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-23
	 */
	public List showDepartmentNameList() throws Exception;
	/**
	 * 获取部门档案启用列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 11, 2013
	 */
	public List showDepartmentOpenList() throws Exception;
	
	/**
	 * 获取部门列表，分页
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-28
	 */
	public PageData showDepartmentList(PageMap pageMap) throws Exception;
	
	/**
	 * 根据部门编码id获取部门信息
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-23
	 */
	public DepartMent showDepartMentInfo(String id) throws Exception;

	/**
	 * 根据名称获取部门信息
	 * @param name
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-10-18
	 */
	public DepartMent getDepartMentInfoByNameLimitOne(String name)throws Exception;
	
	/**
	 * 新增部门
	 * @param departMent
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-24
	 */
	public boolean addDepartMent(DepartMent departMent)throws Exception;
	
	/**
	 * 导入部门档案
	 * @param departMent
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 3, 2013
	 */
	public Map addDRdeptMent(List<DepartMent> list)throws Exception;
	
	/**
	 * 修改部门信息
	 * @param departMent
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-24
	 */
	public Map editDepartMent(DepartMent departMent)throws Exception;
	
	/**
	 * 修改部门类时,下级部门的变化
	 * @param departMent
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-26
	 */
	public boolean editDeptMentAllNextChange(DepartMent departMent)throws Exception;
	
	/**
	 * 删除部门
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-24
	 */
	public boolean deleteDepartMent(String id)throws Exception;
	
	/**
	 * 禁用部门
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-24
	 */
	public boolean disableDepartMent(String id)throws Exception;
	
	/**
	 * 启用部门
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-24
	 */
	public boolean enableDepartMent(String id)throws Exception;
	
	/**
	 * 是否存在该部门ID
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-25
	 */
	public String isExistDepartmentId(String id)throws Exception;
	
	/**
	 * 删除整个部门类
	 * @param pid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-25
	 */
	public Map deleteDepartMentAll(String pid,String id)throws Exception;
	
	/**
	 * 禁用整个部门类
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-25
	 */
	public Map disableDepartMentAll(PageMap pageMap)throws Exception;
	
	/**
	 * 启用整个部门类
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-25
	 */
	public Map enableDepartMentAll(PageMap pageMap)throws Exception;
	
	/**
	 * 获取表数据库中的部门
	 * @param queryMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-29
	 */
	public List getDBUnimportDeptList(Map queryMap) throws Exception;
	
	/**
	 * 部门档案中“状态”为“启用”且“末级标志”为“是”的部门；
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-31
	 */
	public List showDeptListByQuery()throws Exception;
	
	/**
	 * 判断父级以及下级部门类中是否存在暂存状态
	 * @param pId
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-22
	 */
	public boolean getPidAllDeptListIsHold(String pId,String gPid,String type)throws Exception;
	
	/**
	 * 根据部门id数组字符串获取部门详情列表
	 * @param idsStr
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-28
	 */
	public List<DepartMent> getDeptListByIdsStr(String idsStr)throws Exception;
	
	/**
	 * 根据父级id获取整个部门列表
	 * @param pid 父级id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-30
	 */
	public List<DepartMent> getPidAllDeptList(String pid)throws Exception;
	
	/**
	 * 检查部门名称的唯一性
	 * @param name
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-5
	 */
	public boolean checkSoleName(String name)throws Exception;
	
	/**
	 * 根据业务属性depttype，获取部门列表
	 * @param depttypeId 业务属性0行政组织1财务组织2人事组织3采购组织4销售组织5库存组织6运输组织
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-17
	 */
	public List<DepartMent> getDeptListByOperType(String depttypeId)throws Exception;

	/**
	 * 获取部门
	 *
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Aug 8, 2016
     */
	public List<DepartMent> getDeptListForMecshop() throws Exception;
}

