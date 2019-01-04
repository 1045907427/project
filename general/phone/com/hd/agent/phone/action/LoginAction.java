/**
 * @(#)LoginAction.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 13, 2013 zhengziyong 创建版本
 */
package com.hd.agent.phone.action;

import java.util.HashMap;
import java.util.Map;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.accesscontrol.service.ISysUserService;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.phone.service.IPhoneService;

/**
 * 
 * 
 * @author zhengziyong
 */
public class LoginAction extends BaseAction {

	private IPhoneService phoneService;
	
	public IPhoneService getPhoneService() {
		return phoneService;
	}

	public void setPhoneService(IPhoneService phoneService) {
		this.phoneService = phoneService;
	}

	/**
	 * 手机登录
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 11, 2014
	 */
	public String login() throws Exception{
		String accountName = request.getParameter("accountName");
		String password = request.getParameter("password");
		ISysUserService sysUserService = (ISysUserService)SpringContextUtils.getBean("sysUserService");
		SysUser user = sysUserService.getUser(accountName);
		boolean flag = false;
		Map map = new HashMap();
		if(null!=user && CommonUtils.MD5(password).equals(user.getPassword()) && "1".equals(user.getIsphone())){
			flag = true;
			map = phoneService.getLoginUserInfo(user.getUserid());
		}
		map.put("flag", flag);
		addJSONObject(map);
		return "success";
	}
	
	/**
	 * 查询用户
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 11, 2014
	 */
	public String getDepartmentUserList() throws Exception{
		String uid = request.getParameter("uid");
		String con = request.getParameter("con");
		addJSONArray(phoneService.getDepartmentUserList(uid, con));
		return SUCCESS;
	}
	/**
	 * 测试手机连接
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月2日
	 */
	public String testLink() throws Exception{
		addJSONObject("flag", true);
		return "success";
	}
	/**
	 * 获取手机外网访问地址
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年8月21日
	 */
	public String getPhoneOutURL() throws Exception{
		String url = getSysParamValue("PhoneOutURL");
		addJSONObject("url", url);
		return "success";
	}
}

