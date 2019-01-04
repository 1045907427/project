/**
 * @(#)IWorkJobService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Apr 11, 2013 chenwei 创建版本
 */
package com.hd.agent.basefiles.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.model.WorkJob;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 工作岗位service
 * @author chenwei
 */
public interface IWorkJobService {
	/**
	 * 
	 * @param workJob
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 11, 2013
	 */
	public boolean addWorkJob(WorkJob workJob) throws Exception;
	
	/**
	 * 导入工作岗位
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 7, 2013
	 */
	public Map addDRWorkJob(List<WorkJob> list)throws Exception;
	/**
	 * 获取工作岗位列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 12, 2013
	 */
	public PageData getWorkJobList(PageMap pageMap) throws Exception;
	/**
	 * 获取工作岗位详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 12, 2013
	 */
	public WorkJob showWorkJobInfo(String id) throws Exception;
	/**
	 * 修改工作岗位
	 * @param workJob
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 12, 2013
	 */
	public boolean editWorkJob(WorkJob workJob) throws Exception;
	/**
	 * 删除工作岗位
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 12, 2013
	 */
	public boolean deleteWorkJob(String id) throws Exception;
	/**
	 * 启用工作岗位
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 13, 2013
	 */
	public boolean openWorkJob(String id) throws Exception;
	/**
	 * 禁用工作岗位
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 13, 2013
	 */
	public boolean closeWorkJob(String id) throws Exception;
	/**
	 * 验证工作岗位编号是否重复
	 * true 正常 false重复
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 13, 2013
	 */
	public boolean checkWorkJobID(String id) throws Exception;
	/**
	 * 验证工作岗位名称是否重复
	 * true 正常 false重复
	 * @param name
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 13, 2013
	 */
	public boolean checkWorkJobName(String name) throws Exception;
	/**
	 * 根据工作岗位获取角色列表
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 27, 2013
	 */
	public List getRoleListByWorkjob(String id) throws Exception;
}

