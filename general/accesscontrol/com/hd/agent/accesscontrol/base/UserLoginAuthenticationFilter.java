/**
 * @(#)UserLoginAuthenticationFilter.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-12 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.base;

import com.hd.agent.accesscontrol.hasp.AgentHasp;
import com.hd.agent.accesscontrol.model.SysLoginRule;
import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.accesscontrol.model.UserLoginNum;
import com.hd.agent.accesscontrol.service.ISecurityService;
import com.hd.agent.accesscontrol.service.ISysUserService;
import com.hd.agent.common.model.SysLog;
import com.hd.agent.common.service.ISysLogService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.WechatProperties;
import com.hd.agent.common.util.WechatUtils;
import com.hd.agent.system.model.SysParam;
import com.hd.agent.system.service.ISysParamService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 安全登录验证
 * @author chenwei
 */
public class UserLoginAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {
	private static final Logger logger = Logger.getLogger(Logger.class);
	private ISysUserService sysUserService;
	private ISysLogService sysLogService;
	private ISecurityService securityService;

	private ISysParamService sysParamService;

	public ISysParamService getSysParamService() {
		return sysParamService;
	}

	public void setSysParamService(ISysParamService sysParamService) {
		this.sysParamService = sysParamService;
	}

	/**
	 * 用户名
	 */
    public static final String USERNAME = "username";  
    /**
     * 密码
     */
    public static final String PASSWORD = "password";

	/**
	 * 系统用户登录
	 */
	private static final String LOGIN_TYPE_SYSUSER = "sysuser";

	/**
	 * 微信用户登录
	 */
	private static final String LOGIN_TYPE_WECHAT= "wechat";


	/**
	 * 客户端用户登录
	 */
	private static final String LOGIN_TYPE_CLIENT= "client";


    /**
     * 操作业务日期
     */
    public static final String BUSDATE = "busdate";
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
    		HttpServletResponse response) throws AuthenticationException {
    	if (!request.getMethod().equals("POST")) {  
            throw new AuthenticationServiceException("不支持该方式提交:" + request.getMethod());  
        }  
        //检测验证码  
        //checkValidateCode(request);
        request.getSession().setAttribute(BUSDATE,  CommonUtils.getTodayDataStr());
        String username = obtainUsername(request);
        String username1 = username;
        String password = obtainPassword(request);  
        String sid = request.getParameter("sid");
        String cid = request.getParameter("cid");
		String code = request.getParameter("code");
		String url = request.getParameter("url");
		String urltype = request.getParameter("urltype");
		String msgid = request.getParameter("msgid");
        boolean isPhoneUser = false;
        String type = request.getParameter("type");
        String deptid = request.getParameter("deptid");

		String userAgent = request.getHeader("USER-AGENT" );
		// 判断user-agent是否为微信登陆
		if(StringUtils.isNotEmpty(userAgent) && userAgent.toLowerCase().indexOf("micromessenger") > 0) {
			type = "wechat";
		} else if("wechat".equals(type)) {
			type = "";
		}

		SysUser user = null;

        if("phone".equals(type)){
        	username = "P_"+username;
        	isPhoneUser = true;
        } else if("wechat".equals(type)) {
			if("company".equals(urltype)||"msg".equals(urltype)){    //企业号登录或者微信推送
				String[] param = {WechatUtils.getAccessToken(), code};
				JSONObject authJson = WechatUtils.get("https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=%s&code=%s", param);

				if(authJson.containsKey("errcode") || !authJson.containsKey("UserId")) {

					logger.error(new AuthenticationServiceException(code + "微信登陆验证失败！"));
					throw new AuthenticationServiceException(code + "微信登陆验证失败！");
				}

				try {

					String userid = authJson.getString("UserId");
					user = sysUserService.showSysUserInfo(userid);
					if(user != null) {

						type = LOGIN_TYPE_WECHAT;
						username = user.getUsername();
						password = user.getPassword();
						username1=username;
						username="CW_"+username;
						Cookie cookie = new Cookie("wechatLoginType","company");
						response.addCookie(cookie);
					}
				}catch (Exception e) {

					logger.error(new AuthenticationServiceException(code + "微信登陆验证异常！"));
					throw new AuthenticationServiceException(code + "微信登陆验证异常！");
				}
				if("msg".equals(urltype)){
					request.getSession().setAttribute("url", url+"?id="+msgid);
					request.getSession().setAttribute("redirect", url+"?id="+msgid);
				}else{
					url=url.substring(0,url.length()-3)+"v_1_1_001.do";
					request.getSession().setAttribute("url", url);
					request.getSession().setAttribute("redirect", url);
				}

			}else if("service".equals(urltype)){    //服务号登录
				String[] param = {WechatProperties.getValue("serviceid"),WechatProperties.getValue("servicesecret"), code};
				JSONObject authJson = WechatUtils.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code", param);
//                    JSONObject authJson = WechatUtils.get("https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=%s&code=%s", param);
				if(authJson.containsKey("errcode") || !authJson.containsKey("openid")) {

					logger.error(new AuthenticationServiceException(code + "微信登陆验证失败！"));
					throw new AuthenticationServiceException(code + "微信登陆验证失败！");
				}

				try {

					String openid = authJson.getString("openid");
//					user = sysUserService.getSysUserInfoByWechatserid(openid);
					if(user != null) {

						type = LOGIN_TYPE_WECHAT;
						username = user.getUsername();
						password = user.getPassword();
						username1=username;
						username="CW_"+username;
						url=url.substring(0,url.length()-3)+"v_1_1_001.do";
						request.getSession().setAttribute("url", url);
						request.getSession().setAttribute("redirect", url);
						Cookie cookie = new Cookie("wechatLoginType","service");
						response.addCookie(cookie);
					}else{
						request.getSession().setAttribute("wechatserid", openid);
						request.getSession().setAttribute("AuthenticationFailureFailureUrl", "wechat");
					}
				}catch (Exception e) {

					logger.error(new AuthenticationServiceException(code + "微信登陆验证异常！"));
					throw new AuthenticationServiceException(code + "微信登陆验证异常！");
				}

			}
		}else if("client".equals(type)) {
			username = "C_" + username + "@@" + deptid;
			type = LOGIN_TYPE_CLIENT;
		}
        try {
        	boolean flag = true;
        	boolean isEnough = true;
            //判断用户是否已经登录了
      		if(!securityService.isLogin(username)){
      			UserLoginNum userLoginNum = securityService.getUserLoginNum();
      			if(isPhoneUser){
      				int phonenum = AgentHasp.getPhoneNum();
      				if(phonenum>0 && phonenum<=userLoginNum.getPhonenum()){
      					isEnough = false;
      				}
      			}else{
      				int sysnum = AgentHasp.getSysNum();
      				if(sysnum>0 && sysnum <=userLoginNum.getSysnum()){
      					isEnough = false;
      				}
      			}
      		}
      		user = sysUserService.getUser(username1);
      		if(isEnough){
      			String logStr = "";
      			String companyname = securityService.getCompanyName();
      			if(AgentHasp.isInTryDate()){
					if(isPhoneUser && null!=user && "0".equals(user.getIsphone())){
						flag = false;
						logStr = "登录系统"+" 失败 用户名或者密码错误,或者不是手机用户";
					}else{
						//判断用户密码是否正确
						if(user==null|| "0".equals(user.getState()) || !user.getPassword().equals(CommonUtils.MD5(password))){
							flag = false;
							logStr = "登录系统"+" 失败 用户名或者密码错误";
						}else{
							logStr = "登录系统"+" 成功";
						}
					}
      			}else{
      				flag = false;
          			logStr = "登录系统"+" 失败 系统验证不通过 试用结束";
      			}
    			//用户登录IP地址
    			String ip = CommonUtils.getIP(request);
    			//判断用户是否符合登录规则
    			if(flag){
    				SysLoginRule sysLoginRule = sysUserService.getSysLoginRuleInfo(user.getUserid());
    				if(null!=sysLoginRule){
    					//判断是否手机用户
    					if(isPhoneUser){
    						if("2".equals(sysLoginRule.getPtype())){
    							if(null!=sid && sid.equals(sysLoginRule.getPsid())){
    								flag  = true;
    							}else{
    								logStr = "登录系统"+" 失败 手机SID与系统中设置的SID不一致";
    								flag = false;
    							}
    						}
    					}
    					// 微信用户登陆密码验证
						else if(LOGIN_TYPE_WECHAT.equals(type)) {

							//判断用户密码是否正确
							if(user==null|| "0".equals(user.getState()) || !user.getPassword().equals(password)){
								flag = false;
								logStr = "登录系统"+" 失败 用户名或者密码错误";
							}else{
								logStr = "登录系统"+" 成功";
							}

						}  else{
    						//判断系统用户登录
    						//内网登录
    						if("1".equals(sysLoginRule.getLogintype())){
    							if(StringUtils.isNotEmpty(sysLoginRule.getIp()) && sysLoginRule.getIp().indexOf("-")>0){
    								flag = CommonUtils.ipIsValid(sysLoginRule.getIp(), ip);
    								if(!flag){
    									logStr = "登录系统"+" 失败  用户登录的IP地址不在"+sysLoginRule.getIp()+"范围内。";
    								}
    							}else{
    								if(ip.indexOf("192.168.")>=0){
    									flag = true;
    								}else{
    									flag  = false;
    									logStr = "登录系统"+" 失败  用户登录的IP地址不在内网IP地址内。";
    								}
    							}
    						}else if("2".equals(sysLoginRule.getLogintype())){
    							//外网登录 不需要验证IP地址
    						}else if("3".equals(sysLoginRule.getLogintype())){
    							if(StringUtils.isNotEmpty(sysLoginRule.getIp()) && ip.equals(sysLoginRule.getIp().trim())){
    								flag = true;
    							}else{
    								flag = false;
    								logStr = "登录系统"+" 失败  用户登录的IP地址与指定IP地址："+sysLoginRule.getIp()+"不一致";
    							}
    						}
    					}
    				}
    			}
    			if(flag){
    				if(isPhoneUser){
    					if(null==sid || "".equals(sid)){
    						sid = "获取失败";
    					}
                        if(StringUtils.isEmpty(cid)){
                            cid = null;
                        }
    					sysUserService.updateSysLogin(user.getUserid(), null, null, sid,cid);
    				}else{
    					if(StringUtils.isEmpty(ip)){
    						ip = "获取失败";
    					}
    					sysUserService.updateSysLogin(user.getUserid(), ip, null, null,null);
    				}
    			}
    			SysLog sysLog = new SysLog();
            	sysLog.setKeyname("Login");
            	sysLog.setContent(logStr);
            	sysLog.setType("0");
            	sysLog.setIp(ip);
				if(null!=user){
					sysLog.setUserid(user.getUserid());
					if(isPhoneUser){
						sysLog.setName(user.getName()+"(手机)");
					} else if(LOGIN_TYPE_SYSUSER.equals(type)) {
						sysLog.setName(user.getName()+"(系统)");
					} else if(LOGIN_TYPE_CLIENT.equals(type)) {
						sysLog.setName(user.getName()+"(客户端)");
					} else if(LOGIN_TYPE_WECHAT.equals(type)) {
						sysLog.setName(user.getName()+"(微信)");
					} else{
						sysLog.setName(user.getName());
					}
				}
            	sysLogService.addSysLog(sysLog);
            	if(!flag){
            		password = "111111";
					if("client".equals(type)) {
						request.getSession().setAttribute("clientLoginError", "1");
					}
            		logger.error(new AuthenticationServiceException(username+"用户名密码错误或者不符合登录规则！"));
    				throw new AuthenticationServiceException(username+"用户名密码错误或者不符合登录规则！");
            	}
      		}else{
      			flag = false;
      			request.getSession().setAttribute("loginType", "900");
      			password = "111111";
      			SysLog sysLog = new SysLog();
            	sysLog.setKeyname("Login");
            	sysLog.setContent("登录失败 用户登录数量超过系统限制");
            	sysLog.setType("0");
            	sysLog.setIp(CommonUtils.getIP(request));
				if(null!=user){
					sysLog.setUserid(user.getUserid());
					if(isPhoneUser){
						sysLog.setName(user.getName()+"(手机)");
					} else if(LOGIN_TYPE_SYSUSER.equals(type)) {
						sysLog.setName(user.getName()+"(系统)");
					} else if(LOGIN_TYPE_CLIENT.equals(type)) {
						sysLog.setName(user.getName()+"(客户端)");
					} else if(LOGIN_TYPE_WECHAT.equals(type)) {
						sysLog.setName(user.getName()+"(微信)");
					} else{
						sysLog.setName(user.getName());
					}
				}

            	if(null!=user){
            		sysLog.setUserid(user.getUserid());
                	if(isPhoneUser){
                		sysLog.setName(user.getName()+"(手机)");
                	} else if("client".equals(type)){
						sysLog.setName(user.getName()+"(客户端)");
					} else{
                		sysLog.setName(user.getName());
                	}
            	}
            	sysLogService.addSysLog(sysLog);
        		logger.error(new AuthenticationServiceException("用户点数不足或者验证不通过"));
				throw new AuthenticationServiceException("用户点数不足或者验证不通过");
      		}

			if("client".equals(type)) {

				SysParam param = sysParamService.getSysParam("RoleCanLogOnClient");
				if(param == null || StringUtils.isEmpty(param.getPvalue())) {
					throw new AuthenticationServiceException("用户没有权限登录客户端");
				}
				List<SysUser> authUsers = new ArrayList<SysUser>();
				String[] auths = param.getPvalue().split(",");
				for(String authStr : auths) {
					authUsers.addAll(sysUserService.getSysUserListByRole(authStr));
				}

				boolean authFlag = false;
				for(SysUser authUser : authUsers) {

					if(user.getUserid().equals(authUser.getUserid())) {
						authFlag = true;
						break;
					}
				}

				if(!authFlag) {
					request.getSession().setAttribute("clientLoginNoAuth", "1");
					throw new AuthenticationServiceException("用户没有登录零售客户端权限");
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e,e);
		}
        //UsernamePasswordAuthenticationToken实现 Authentication  
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        // 允许子类设置详细属性  
        setDetails(request, authRequest);  
    	return this.getAuthenticationManager().authenticate(authRequest); 
    }
    
    /**
     * 获取用户名
     */
    @Override  
    protected String obtainUsername(HttpServletRequest request) {  
        Object obj = request.getParameter(USERNAME);  
        return null == obj ? "" : obj.toString();  
    }  
    /**
     * 获取密码
     */
    @Override  
    protected String obtainPassword(HttpServletRequest request) {  
        Object obj = request.getParameter(PASSWORD);  
        return null == obj ? "" : obj.toString();  
    }

	public ISysUserService getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(ISysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public ISysLogService getSysLogService() {
		return sysLogService;
	}

	public void setSysLogService(ISysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}

	public ISecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(ISecurityService securityService) {
		this.securityService = securityService;
	}
    
}

