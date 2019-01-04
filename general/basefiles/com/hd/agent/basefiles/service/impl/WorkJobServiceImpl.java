/**
 * @(#)WorkJobServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Apr 11, 2013 chenwei 创建版本
 */
package com.hd.agent.basefiles.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.dao.WorkJobMapper;
import com.hd.agent.basefiles.model.WorkJob;
import com.hd.agent.basefiles.service.IWorkJobService;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 工作岗位service实现类
 * @author chenwei
 */
public class WorkJobServiceImpl extends BaseServiceImpl implements
		IWorkJobService {
	
	private WorkJobMapper workJobMapper;

	public WorkJobMapper getWorkJobMapper() {
		return workJobMapper;
	}

	public void setWorkJobMapper(WorkJobMapper workJobMapper) {
		this.workJobMapper = workJobMapper;
	}

	@Override
	public Map addDRWorkJob(List<WorkJob> list) throws Exception {
		String failStr = "",closeVal = "",repeatVal = "";
		int closeNum = 0, repeatNum = 0,failureNum = 0,successNum = 0;
		boolean flag = false;
		if(null != list && list.size() != 0){
			for(WorkJob workJob : list){
				if(workJobMapper.getWorkJobContById(workJob.getId())==0){
					if(workJobMapper.getWorkJobCountByName(workJob.getJobname())==0){
						SysUser sysUser = getSysUser();
						if(StringUtils.isEmpty(workJob.getState())){
							workJob.setState("2");
						}
						workJob.setAdduserid(sysUser.getUserid());
						workJob.setAddusername(sysUser.getName());
						workJob.setAdddeptid(sysUser.getDepartmentid());
						workJob.setAdddeptname(sysUser.getDepartmentname());
						flag = workJobMapper.addWorkJob(workJob) > 0;
						if(null!=workJob.getRoleList()){
							String[] ids = workJob.getRoleList().split(",");
							for(String id : ids){
								workJobMapper.addWorkJobAuthority(workJob.getId(), id.trim());
							}
						}
						if(flag){
							successNum++;
						}else{
							if(StringUtils.isNotEmpty(failStr)){
								failStr += "," + workJob.getId(); 
							}
							else{
								failStr = workJob.getId();
							}
							failureNum++;
						}
					}
					else{
						if(StringUtils.isNotEmpty(failStr)){
							failStr += "," + workJob.getId(); 
						}
						else{
							failStr = workJob.getId();
						}
						failureNum++;
					}
				}
				else{
					WorkJob workJob2 = workJobMapper.showWorkJobInfo(workJob.getId());
					if(null != workJob2){
						if("0".equals(workJob2.getState())){//禁用状态，不允许导入
							if(StringUtils.isEmpty(closeVal)){
								closeVal = workJob2.getId();
							}
							else{
								closeVal += "," + workJob2.getId();
							}
							closeNum++;
						}
						else{
							if(StringUtils.isEmpty(repeatVal)){
								repeatVal = workJob2.getId();
							}
							else{
								repeatVal += "," + workJob2.getId();
							}
							repeatNum++;
						}
					}
				}
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("success", successNum);
		map.put("failure", failureNum);
		map.put("failStr", failStr);
		map.put("repeatNum", repeatNum);
		map.put("closeNum", closeNum);
		map.put("closeVal", closeVal);
		map.put("repeatVal", repeatVal);
		return map;
	}

	@Override
	public boolean addWorkJob(WorkJob workJob) throws Exception {
		//判断主键或者名称是否存在
		if(workJobMapper.getWorkJobContById(workJob.getId())==0 && workJobMapper.getWorkJobCountByName(workJob.getJobname())==0){
			int i = workJobMapper.addWorkJob(workJob);
			if(null!=workJob.getRoleList()){
				String[] ids = workJob.getRoleList().split(",");
				for(String id : ids){
					workJobMapper.addWorkJobAuthority(workJob.getId(), id.trim());
				}
			}
			return i>0;
		}else{
			return false;
		}
	}

	@Override
	public PageData getWorkJobList(PageMap pageMap) throws Exception {
		//单表取字段权限
		String cols = getAccessColumnList("t_base_workjob","t");
		pageMap.setCols(cols);
		//数据权限sql
		String dataSql = getDataAccessRule("t_base_workjob","t");
		pageMap.setDataSql(dataSql);
		List list = workJobMapper.getWorkJobList(pageMap);
		PageData pageData = new PageData(workJobMapper.getWorkJobCount(pageMap),list,pageMap);
		return pageData;
	}

	@Override
	public WorkJob showWorkJobInfo(String id) throws Exception {
		WorkJob workJob = workJobMapper.showWorkJobInfo(id);
		if(null!=workJob){
			List list = workJobMapper.getWorkJobAuthorityList(id);
			String roles = "";
			for(int i=0;i<list.size();i++){
				if(i==0){
					roles = (String) list.get(i);
				}else{
					String roleid = (String) list.get(i);
					roleid = roleid.trim();
					roles +=","+roleid;
				}
			}
			workJob.setRoleList(roles.trim());
		}
		return workJob;
	}

	@Override
	public boolean editWorkJob(WorkJob workJob) throws Exception {
		int i = workJobMapper.editWorkJob(workJob);
		workJobMapper.deleteWorkJobAuthority(workJob.getOldid());
		if(null!=workJob.getRoleList()){
			String[] ids = workJob.getRoleList().split(",");
			for(String id : ids){
				workJobMapper.addWorkJobAuthority(workJob.getId(), id.trim());
			}
		}
		return i>0;
	}

	@Override
	public boolean deleteWorkJob(String id) throws Exception {
		int i = workJobMapper.deleteWorkJob(id);
		workJobMapper.deleteWorkJobAuthority(id);
		return i>0;
	}

	@Override
	public boolean openWorkJob(String id) throws Exception {
		SysUser sysUser = getSysUser();
		int i = workJobMapper.openWorkJob(id, sysUser.getUserid(),sysUser.getName());
		return i>0;
	}

	@Override
	public boolean closeWorkJob(String id) throws Exception {
		SysUser sysUser = getSysUser();
		int i = workJobMapper.closeWorkJob(id, sysUser.getUserid(),sysUser.getName());
		return i>0;
	}

	@Override
	public boolean checkWorkJobID(String id) throws Exception {
		int count =  workJobMapper.getWorkJobContById(id);
		return count==0;
	}

	@Override
	public boolean checkWorkJobName(String name) throws Exception {
		int count =  workJobMapper.getWorkJobCountByName(name);
		return count==0;
	}

	@Override
	public List getRoleListByWorkjob(String id) throws Exception {
		List list = workJobMapper.getWorkJobAuthorityList(id);
		return list;
	}
	
	
}

