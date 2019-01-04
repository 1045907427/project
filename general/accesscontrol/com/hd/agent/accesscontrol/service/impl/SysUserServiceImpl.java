/**
 * @(#)UserServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-13 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.service.impl;

import com.hd.agent.accesscontrol.dao.SysUserMapper;
import com.hd.agent.accesscontrol.dao.UserAuthorityMapper;
import com.hd.agent.accesscontrol.model.Authority;
import com.hd.agent.accesscontrol.model.SysLoginRule;
import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.accesscontrol.model.UserAuthority;
import com.hd.agent.accesscontrol.service.ISysUserService;
import com.hd.agent.basefiles.dao.DepartMentMapper;
import com.hd.agent.basefiles.dao.PersonnelMapper;
import com.hd.agent.basefiles.dao.WorkJobMapper;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.model.WorkJob;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * IUserService接口实现类
 * @author chenwei
 */
public class SysUserServiceImpl extends BaseServiceImpl implements ISysUserService {
	
	private SysUserMapper sysUserMapper;

	private UserAuthorityMapper userAuthorityMapper;
	
	private PersonnelMapper personnelMapper;
	
	private DepartMentMapper departMentMapper;

	private WorkJobMapper workJobMapper;
	
	public DepartMentMapper getDepartMentMapper() {
		return departMentMapper;
	}

	public void setDepartMentMapper(DepartMentMapper departMentMapper) {
		this.departMentMapper = departMentMapper;
	}

	public PersonnelMapper getPersonnelMapper() {
		return personnelMapper;
	}

	public void setPersonnelMapper(PersonnelMapper personnelMapper) {
		this.personnelMapper = personnelMapper;
	}

	public UserAuthorityMapper getUserAuthorityMapper() {
		return userAuthorityMapper;
	}

	public void setUserAuthorityMapper(UserAuthorityMapper userAuthorityMapper) {
		this.userAuthorityMapper = userAuthorityMapper;
	}
	
	public SysUserMapper getSysUserMapper() {
		return sysUserMapper;
	}

	public void setSysUserMapper(SysUserMapper sysUserMapper) {
		this.sysUserMapper = sysUserMapper;
	}
	
	public WorkJobMapper getWorkJobMapper() {
		return workJobMapper;
	}

	public void setWorkJobMapper(WorkJobMapper workJobMapper) {
		this.workJobMapper = workJobMapper;
	}

	@Override
	public SysUser getUser(String username) throws Exception {
		SysUser sysUser = sysUserMapper.getUser(username);
		
		return sysUser;
	}

	@Override
	public List getSysUserList() throws Exception {
		List list = sysUserMapper.getSysUserList();
		return list;
	}
	@Override
	public List getSysUserListByDeptid(String deptid) throws Exception {
		if(StringUtils.isNotEmpty(deptid)){
			List list = sysUserMapper.getSysUserListByDeptid(deptid);
			return list;
		}else{
			List list = sysUserMapper.getSysUserListByNoDeptid();
			return list;
		}
	}
	@Override
	public PageData showSysUserList(PageMap pageMap) throws Exception {
		//单表取字段权限
		String cols = getAccessColumnList("t_sys_user","t");
		pageMap.setCols(cols);

		//数据权限
		String sql = getDataAccessRule("t_sys_user","t");
		pageMap.setDataSql(sql);
		
		pageMap.setQueryAlias("t");
		List<SysUser> list = sysUserMapper.showSysUserList(pageMap);
		for(SysUser user : list){
			if(StringUtils.isNotEmpty(user.getState())){ //状态
				SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(user.getState(), "state");
				if(sysCode != null){
					user.setStateName(sysCode.getCodename());
				}
			}
			if(StringUtils.isNotEmpty(user.getSex())){ //性别
				SysCode sysCode = super.getBaseSysCodeMapper().getSysCodeInfo(user.getSex(), "sex");
				if(sysCode != null){
					user.setSexName(sysCode.getCodename());
				}
			}
			if(StringUtils.isNotEmpty(user.getWorkjobid())){//所属岗位
				WorkJob workJob = workJobMapper.showWorkJobInfo(user.getWorkjobid());
				if(workJob != null){
					user.setWorkjobName(workJob.getJobname());
				}
			}
		}
		PageData pageData = new PageData(sysUserMapper.showSysUserCount(pageMap), list, pageMap);
		return pageData;
	}
	
	/**
	 * 分配系统用户
	 * @param paramMap 分配人员信息id数组集合  addUserid 添加人员id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-8
	 */
	public boolean addAllotSystemUser(Map paramMap)throws Exception{
		if(null != paramMap.get("belongdeptid")){
			DepartMent deptInfo = departMentMapper.getDepartmentInfo(paramMap.get("belongdeptid").toString());//根据人员获取所属的部门
			if(null != deptInfo){//选取其部门名称
				paramMap.put("departmentname", deptInfo.getName());
			}
		}
		String userid = "";
		String id = (String) paramMap.get("id");
		if(StringUtils.isNotEmpty(id)){
            String s = String.valueOf((Math.random()*10000));
			userid = id+s.substring(0,s.indexOf("."));
		}
		else{
			userid = CommonUtils.getDataNumberSendsWithRand();
		}
		paramMap.put("userid", userid);
		if(null!=id){
			Personnel personnel = personnelMapper.getPersonnelInfo(id);
			if(null!=personnel){
				//来源工作岗位的角色
				List<String> roleList  = workJobMapper.getWorkJobAuthorityList(personnel.getBelongpost());
				if(null!=roleList){
					for(String roleid : roleList){
						if(StringUtils.isNotEmpty(roleid)){
							UserAuthority userAuthority = new UserAuthority();
							userAuthority.setAuthorityid(roleid);
							userAuthority.setUserid(userid);
							userAuthorityMapper.addUserAuthority(userAuthority);
						}
					}
				}
			}
		}
		return sysUserMapper.addAllotSystemUser(paramMap) > 0;
	}

	@Override
	public List getUserListByUsernames(List userList) throws Exception {
		List list = sysUserMapper.getUserListByUsernames(userList);
		return list;
	}

	/**
	 * 系统用户新增
	 * @param user
     * @param userAuthority
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-14
	 */
	public boolean addSysUser(SysUser user,UserAuthority userAuthority)throws Exception{
		if(StringUtils.isNotEmpty(user.getUserid())){
			user.setUserid(null);
		}
		if(null!=user &&  "".equals(user.getSeq())){
			user.setSeq(null);
		}else{
			Integer seq = new Integer(user.getSeq());
			user.setSeq(seq.toString());
		}
		int i = sysUserMapper.addSysUser(user);
		if(i > 0){
			SysUser user1 = sysUserMapper.getUserByUsername(user.getUsername());
			if(user1 == null){
				return false;
			}
			//来源工作岗位的角色
			List<String> roleList  = workJobMapper.getWorkJobAuthorityList(user.getWorkjobid());
			if(null!=roleList){
				for(String roleid : roleList){
					if(StringUtils.isNotEmpty(roleid)){
						userAuthority.setAuthorityid(roleid);
						userAuthority.setUserid(user1.getUserid());
						userAuthorityMapper.addUserAuthority(userAuthority);
					}
				}
			}
			String authorityidArr[] = userAuthority.getAuthorityid().split(",");
			for(int j=0;j<authorityidArr.length;j++){
				boolean addFlag = true;
				if(null!=roleList){
					for(String roleid : roleList){
						if(authorityidArr[j].equals(roleid)){
							addFlag = false;
							break;
						}
					}
				}
				if(addFlag){
					if(StringUtils.isNotEmpty(authorityidArr[j])){
						userAuthority.setAuthorityid(authorityidArr[j]);
						userAuthority.setUserid(user1.getUserid());
						userAuthorityMapper.addUserAuthority(userAuthority);
					}
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 用户名的唯一性检验
	 * @param username
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-15
	 */
	public boolean userNameCheck(String username)throws Exception{
		return sysUserMapper.usernameCheck(username) == 0;
	}
	
	/**
	 * 显示系统用户信息
	 * @param userid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-16
	 */
	public SysUser showSysUserInfo(String userid)throws Exception{
		SysUser sysUser = sysUserMapper.showSysUserInfo(userid);
		return sysUser;
	}
	
	
	public SysUser showSysUserMoreInfo(String userid) throws Exception{
		SysUser sysUser=sysUserMapper.showSysUserInfo(userid);
		if(null!=sysUser){
			if(StringUtils.isNotEmpty(sysUser.getState())){ //状态
				SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(sysUser.getState(), "state");
				if(sysCode != null){
					sysUser.setStateName(sysCode.getCodename());
				}
			}
			if(StringUtils.isNotEmpty(sysUser.getSex())){ //性别
				SysCode sysCode = super.getBaseSysCodeMapper().getSysCodeInfo(sysUser.getSex(), "sex");
				if(sysCode != null){
					sysUser.setSexName(sysCode.getCodename());
				}
			}
			if(StringUtils.isNotEmpty(sysUser.getWorkjobid())){//所属岗位
				WorkJob workJob = workJobMapper.showWorkJobInfo(sysUser.getWorkjobid());
				if(workJob != null){
					sysUser.setWorkjobName(workJob.getJobname());
				}
			}
		}
		return sysUser;
	}
	/**
	 * 显示用户权限信息
	 * @param userid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-18
	 */
	public List showUserAuthorityInfo(String userid)throws Exception{
		List userAuthorityList = userAuthorityMapper.showUserAuthorityInfo(userid);
		return userAuthorityList;
	}
	
	/**
	 * 修改系统用户信息
	 * @param sysUser
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-18
	 */
	public boolean editSysUser(SysUser sysUser,UserAuthority userAuthority)throws Exception{
		//保存修改前判断数据是否已经被加锁 可以修改
		boolean flag = isLockEdit("t_sys_user", sysUser.getUserid());
		String authorityidArr[] = userAuthority.getAuthorityid().split(",");
		int d = 0;
		if(flag){
			if(null!=sysUser.getSeq() && "".equals(sysUser.getSeq())){
				sysUser.setSeq("9999");
			}
			int i = sysUserMapper.editSysUser(sysUser);
			if(i > 0){
				d = userAuthorityMapper.deleteUserAuthority(sysUser.getUserid());
				//来源工作岗位的角色
				List<String> roleList  = workJobMapper.getWorkJobAuthorityList(sysUser.getWorkjobid());
				if(null!=roleList){
					for(String roleid : roleList){
						if(StringUtils.isNotEmpty(roleid)){
							userAuthority.setAuthorityid(roleid);
							userAuthority.setUserid(sysUser.getUserid());
							userAuthorityMapper.addUserAuthority(userAuthority);
						}
					}
				}
				for(int j=0;j<authorityidArr.length;j++){
					boolean addFlag = true;
					if(null!=roleList){
						for(String roleid : roleList){
							if(authorityidArr[j].equals(roleid)){
								addFlag = false;
								break;
							}
						}
					}
					if(addFlag){
						if(StringUtils.isNotEmpty(authorityidArr[j])){
							userAuthority.setAuthorityid(authorityidArr[j]);
							userAuthority.setUserid(sysUser.getUserid());
							userAuthorityMapper.addUserAuthority(userAuthority);
						}
					}
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 启用系统用户
	 * @param userid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-18
	 */
	public boolean enableSysUser(String userid)throws Exception{
		int i = sysUserMapper.enableSysUser(userid);
		return i > 0;
	}
	
	/**
	 * 禁用系统用户
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-18
	 */
	public boolean disableSysUser(String userid)throws Exception{
		int i = sysUserMapper.disableSysUser(userid);
		return i > 0;
	}
	
	/**
	 * 删除系统用户
	 * @param userid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-18
	 */
	public boolean deleteSysUser(String userid)throws Exception{
		int i = sysUserMapper.deleteSysUser(userid);
		if(i > 0){
			List userAuthorityList = userAuthorityMapper.showUserAuthorityInfo(userid);
			if(userAuthorityList.size() != 0){
				int a = userAuthorityMapper.deleteUserAuthority(userid);
				if(a > 0){
					return true;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 重置密码
	 * @param map
	 * @return
	 * @author panxiaoxiao
	 * @date 2013-3-22
	 */
	public boolean editResetSysUserPwd(Map map)throws Exception{
		int i = sysUserMapper.editResetSysUserPwd(map);
		return i>0;
	}
	
	/**
	 * 密码修改
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-27
	 */
	public boolean modifySysUserPwd(Map map)throws Exception{
		int i = sysUserMapper.modifySysUserPwd(map);
		return i > 0;
	}
	
	/**
	 * Excel导入添加系统，
	 * 判断导入的系统用户是否重复，状态为保存2
	 * @return
	 * @author panxiaoxiao
	 * @date 2013-4-2
	 */
	public boolean addDRSysUser(SysUser user)throws Exception{
		SysUser obj = sysUserMapper.showSysUserInfo(user.getUserid());
		if(null == obj){
			SysUser sysUser = getSysUser();
			user.setAdduserid(sysUser.getUserid());
			user.setUsername(sysUser.getName());
			user.setState("2");
			int i = sysUserMapper.addSysUser(user);
			return i>0;
		}
		return false;
	}
	
	/**
	 * 根据用户权限id（Authorityid）数组字符串，显示已选择的用户（权限）列表
	 * @param userid 根据用户编号userid获取用户权限列表
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-8
	 */
	public List<Authority> getAuthoritiesListByUserid(String userid)throws Exception{
		String authorityidStr = "";
		List<Authority> authoritiesList = new ArrayList<Authority>();
		List<UserAuthority> userAuthoritiesList = userAuthorityMapper.getUserAuthoritiesListByUserid(userid);
		if(userAuthoritiesList.size() != 0){
			for(UserAuthority userAuthority :userAuthoritiesList){
				if(null != userAuthority){
					authorityidStr = userAuthority.getAuthorityid() + "," + authorityidStr;
				}
			}
			String[] authorityidArr = authorityidStr.split(",");
			authoritiesList = userAuthorityMapper.getAuthorityByAuthorityidArr(authorityidArr);
		}
		return authoritiesList;
	}

	@Override
	public boolean updateSysLogin(String userid, String ip, String mac,
			String sid,String cid) throws Exception{
		int i = sysUserMapper.updateSysLogin(userid, ip, mac, sid,cid);
		return i>0;
	}

	@Override
	public PageData showSysLoginRuleList(PageMap pageMap) throws Exception {
		List<SysLoginRule> list = sysUserMapper.showSysLoginRuleList(pageMap);
		for(SysLoginRule sysLoginRule : list){
			DepartMent departMent = getDepartmentByDeptid(sysLoginRule.getDepartmentid());
			if(null!=departMent){
				sysLoginRule.setDepartmentname(departMent.getName());
			}
		}
		PageData pageData = new PageData(sysUserMapper.showSysLoginRuleListCount(pageMap),list,pageMap);
		return pageData;
	}

	@Override
	public boolean addSysLoginRule(Map map) throws Exception {
		String userid = (String) map.get("userid");
		String logintype = (String) map.get("logintype");
		String ptype = (String) map.get("ptype");
		String ip1 = (String) map.get("ip1");
		String ip2 = (String) map.get("ip2");
		if(null!=userid){
			String[] userArr = userid.split(",");
			for(String id : userArr){
				SysUser sysUser = sysUserMapper.getSysUserByUseridWithoutCache(id);
				if(null!=sysUser){
					//先清楚用户历史登录规则
					sysUserMapper.deleteSysLoginRule(id);
					
					SysLoginRule sysLoginRule = new SysLoginRule();
					sysLoginRule.setUserid(id);
					sysLoginRule.setLogintype(logintype);
					sysLoginRule.setPtype(ptype);
					//logintype 1内网登录2外网登录3指定IP地址
					//内网登录可以指定内网IP地址段
					if("3".equals(logintype)){
						sysLoginRule.setIp(sysUser.getLastip());
					}else if("1".equals(logintype)){
						if(StringUtils.isNotEmpty(ip1) && StringUtils.isNotEmpty(ip2)){
							sysLoginRule.setIp(ip1+"-"+ip2);
						}
					}
					if("2".equals(ptype)){
						sysLoginRule.setPsid(sysUser.getLastsid());
					}
					sysUserMapper.addSysLoginRule(sysLoginRule);
				}
			}
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean deleteSysLoginRule(String ids) throws Exception {
		String[] userArr = ids.split(",");
		boolean flag = false;
		for(String userid : userArr){
			int i = sysUserMapper.deleteSysLoginRule(userid);
			if(i>0 && !flag){
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public SysLoginRule getSysLoginRuleInfo(String userid) throws Exception {
		SysLoginRule sysLoginRule = sysUserMapper.getSysLoginRuleInfo(userid);
		SysUser sysUser = getSysUserById(userid);
		if(null!=sysUser && null!=sysLoginRule){
			sysLoginRule.setUsername(sysUser.getUsername());
			sysLoginRule.setName(sysUser.getName());
		}
		return sysLoginRule;
	}

	@Override
	public boolean updateSysLoginRule(SysLoginRule sysLoginRule)
			throws Exception {
		int i = sysUserMapper.updateSysLoginRule(sysLoginRule);
		return i>0;
	}

    @Override
    public Map associatedPersonnelid() throws Exception {
        Map map = new HashMap();
        List<SysUser> list = sysUserMapper.getSysUserList();
        for(SysUser sysUser : list){
            if(StringUtils.isNotEmpty(sysUser.getPersonnelid())){
                map.put(sysUser.getPersonnelid(),sysUser.getName());
            }
        }
        return map;
    }


	@Override
	public List getSysUserListByRole(String rolename) throws Exception {

		return sysUserMapper.getSysUserListByRole(rolename);
	}
}

