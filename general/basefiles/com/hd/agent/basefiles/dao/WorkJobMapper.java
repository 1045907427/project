/**
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-4-11 chenwei 创建版本
 */
package com.hd.agent.basefiles.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.basefiles.model.WorkJob;
import com.hd.agent.common.util.PageMap;

/**
 * 工作岗位dao
 * @author chenwei
 */
public interface WorkJobMapper {
    /**
     * 添加工作岗位
     * @param workJob
     * @return
     * @author chenwei 
     * @date Apr 12, 2013
     */
	public int addWorkJob(WorkJob workJob);
	/**
	 * 添加工作岗位与角色的关系
	 * @param workjobid
	 * @param authorityid
	 * @return
	 * @author chenwei 
	 * @date Apr 12, 2013
	 */
	public int addWorkJobAuthority(@Param("workjobid")String workjobid,@Param("authorityid")String authorityid);
	/**
	 * 获取工作岗位列表数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Apr 12, 2013
	 */
	public List getWorkJobList(PageMap pageMap);
	/**
	 * 获取工作岗位数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Apr 12, 2013
	 */
	public int getWorkJobCount(PageMap pageMap);
	/**
	 * 获取工作岗位信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Apr 12, 2013
	 */
	public WorkJob showWorkJobInfo(@Param("id")String id);
	/**
	 * 获取工作岗位拥有的角色
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Apr 12, 2013
	 */
	public List getWorkJobAuthorityList(@Param("workjobid")String id);
	/**
	 * 修改工作岗位
	 * @param workJob
	 * @return
	 * @author chenwei 
	 * @date Apr 12, 2013
	 */
	public int editWorkJob(WorkJob workJob);
	/**
	 * 删除工作岗位与角色关系
	 * @return
	 * @author chenwei 
	 * @date Apr 12, 2013
	 */
	public int deleteWorkJobAuthority(@Param("workjobid")String workjobid);
	/**
	 * 删除工作岗位
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Apr 12, 2013
	 */
	public int deleteWorkJob(@Param("id")String id);
	/**
	 * 启用工作岗位
	 * @param id
	 * @param userid
	 * @return
	 * @author chenwei 
	 * @date Apr 13, 2013
	 */
	public int openWorkJob(@Param("id")String id,@Param("userid")String userid,@Param("name")String name);
	/**
	 * 禁用工作岗位
	 * @param id
	 * @param userid
	 * @return
	 * @author chenwei 
	 * @date Apr 13, 2013
	 */
	public int closeWorkJob(@Param("id")String id,@Param("userid")String userid,@Param("name")String name);
	/**
	 * 根据编号获取工作岗位的数量
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Apr 13, 2013
	 */
	public int getWorkJobContById(@Param("id")String id);
	/**
	 * 根据岗位名称获取岗位数量
	 * @param name
	 * @return
	 * @author chenwei 
	 * @date Apr 13, 2013
	 */
	public int getWorkJobCountByName(@Param("jobname")String jobname);
}