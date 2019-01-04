/**
 * @(#)IndexAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-13 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.action;


import com.hd.agent.accesscontrol.hasp.AgentHasp;
import com.hd.agent.accesscontrol.model.*;
import com.hd.agent.accesscontrol.service.ISecurityService;
import com.hd.agent.accesscontrol.service.ISysUserService;
import com.hd.agent.accesscontrol.util.CodeUtils;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.MenuPropertiesUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import java.util.*;

/**
 * 
 * 首页登录页显示，用户登录验证
 * @author chenwei
 */
public class IndexAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	
	private ISysUserService sysUserService;
	private ISecurityService securityService;
	public ISysUserService getSysUserService() {
		return sysUserService;
	}
	public void setSysUserService(ISysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
	
	public ISecurityService getSecurityService() {
		return securityService;
	}
	public void setSecurityService(ISecurityService securityService) {
		this.securityService = securityService;
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

    /**
     * 获取默认主题版本
     * @return
     */
    public String getThemeName() throws Exception{
        String easyuiThemeName = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
                if (c.getName().equalsIgnoreCase("easyuiThemeName")) {
                    easyuiThemeName = c.getValue();
                    break;
                }
            }
        }
        if(StringUtils.isEmpty(easyuiThemeName)){
            easyuiThemeName = getSysParamValue("ThemeName");
            if(StringUtils.isEmpty(easyuiThemeName)){
                easyuiThemeName = "default";
            }
            Cookie cookie = new Cookie("easyuiThemeName",easyuiThemeName );
            cookie.setMaxAge(60 * 60 * 24 *7);// 设置为30min
            response.addCookie(cookie);
        }
        return easyuiThemeName;
    }
    /**
	 * 显示首页
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-13
	 */
	public String index() throws Exception{
		SysUser sysUser = getSysUser();
		String passwordmd5 = CommonUtils.MD5("123456");
		if(passwordmd5.equals(sysUser.getPassword())){
			request.setAttribute("passwordflag", "1");
		}
		request.setAttribute("sysUser", sysUser);
		//根据角色判断是否需要判断试用
		Boolean tryflag=isUserShowTry(sysUser.getUserid());
		if(AgentHasp.isTry()&&tryflag){
			String trydate = AgentHasp.getSysTryDate();
			int tryday = CommonUtils.daysBetween(CommonUtils.getTodayDataStr(), trydate);
			if(tryday<=30){
				request.setAttribute("istry", "1");
				request.setAttribute("tryday", tryday);
				request.setAttribute("trydate", trydate);
			}
		}
		String IsOpenBusDate = getSysParamValue("IsOpenBusDate");
		if(StringUtils.isEmpty(IsOpenBusDate)){
            IsOpenBusDate = "0";
        }
        request.setAttribute("IsOpenBusDate",IsOpenBusDate);
        String easyuiThemeName = getThemeName();
        if("default1".equals(easyuiThemeName)){
            return "default1";
        }else if("default2".equals(easyuiThemeName)){
            return "default2";
		}else if("default".equals(easyuiThemeName)){
			List<String> menuUrlList = new ArrayList();
			Map<String, String> pMap = MenuPropertiesUtils.getpMap();
			for(Map.Entry<String, String> vo : pMap.entrySet()){

				menuUrlList.add(vo.getValue());
			}
			request.setAttribute("isSuperAccess",MenuPropertiesUtils.getIsSuperAccess());
			request.setAttribute("menuUrlList",menuUrlList);
			return "success";
		}
		return "success";
	}

	/**
	 * 根据参数判断当前用户角色是否需要判断试用 true表示需要判断，false表示不需要判断
	 * @param userid
	 * @return java.lang.Boolean
	 * @throws
	 * @author luoqiang
	 * @date Mar 08, 2018
	 */
	private Boolean isUserShowTry(String userid) throws Exception {
		String TRYROLES=getSysParamValue("TRYROLES");
		if(StringUtils.isEmpty(TRYROLES)){
			return true;
		}
		if("null".equals(TRYROLES)){
			return false;
		}
		List<UserAuthority> userAuthorityList = sysUserService.showUserAuthorityInfo(userid);
		List list=Arrays.asList(TRYROLES.split(","));
		for(UserAuthority userAuthority:userAuthorityList){
			Authority authority=securityService.showAuthorityInfo(userAuthority.getAuthorityid());
			if(list.contains(authority.getAuthorityname())){
				return true;
			}
		}
		return false;
	}

	/**
	 * 未登录页面跳转
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-13
	 */
	public String noLogin() throws Exception{
		String logintype = (String) request.getSession().getAttribute("loginType");
		String clientLoginError = (String) request.getSession().getAttribute("clientLoginError");
		if("1".equals(clientLoginError)) {

			request.getSession().removeAttribute("clientLoginError");
			Map map = new HashMap();
			map.put("flag", false);
			map.put("msg", "用户名或密码错误");
			addJSONObject(map);
			return "json";
		}

		if(StringUtils.isNotEmpty(logintype)){
			response.setStatus(900);
		}else{
			response.setStatus(901);
		}
		return "success";
	}
	/**
	 * session并发处理
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 14, 2013
	 */
	public String sessionConcurrent() throws Exception{
		String requestType = request.getHeader("X-Requested-With");
		response.setHeader("Pragma","No-Cache");
		response.setHeader("Cache-Control","No-Cache");
		response.setDateHeader("Expires", 0);
		//判断请求是否为ajax
        if(requestType !=null && "XMLHttpRequest".equals(requestType)){
        	//设置返回状态代码 901表示未登录 902无权限 903session并发
        	response.setStatus(903);
        }else{
            return "success";
        }
		return null;
	}
	/**
	 * 显示登录页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-13
	 */
	public String login() throws Exception{
		//读cookie     
		Cookie[] cookies = request.getCookies();     
		if (cookies != null) {
			String name = "";
			String password = "";
			String ismemory = "";
			for (int i = 0; i < cookies.length; i++) {
				Cookie c = cookies[i];
				if (c.getName().equalsIgnoreCase("username")) {
					name = c.getValue();
				} else if (c.getName().equalsIgnoreCase("password")) {
					password = c.getValue();
				} else if (c.getName().equalsIgnoreCase("ismemory")) {
					ismemory = c.getValue();
				}
			}
			
			request.setAttribute("username", CodeUtils.aesDecrypt(name,"passwordagent"));
			request.setAttribute("ismemory", ismemory);

			if("1".equals(ismemory) && StringUtils.isNotEmpty(password)){
				String passwordstr = CodeUtils.aesDecrypt(password, "passwordagent");
				request.setAttribute("password", passwordstr);
			}else{
				request.setAttribute("password", "");
			}
		}
        String IsOpenBusDate = getSysParamValue("IsOpenBusDate");
        if(StringUtils.isEmpty(IsOpenBusDate)){
            IsOpenBusDate = "0";
        }
        request.setAttribute("IsOpenBusDate",IsOpenBusDate);
        request.setAttribute("today", CommonUtils.getTodayDataStr());
        String easyuiThemeName = getThemeName();
        if("default1".equals(easyuiThemeName)){
            return "default1";
        }else if("default2".equals(easyuiThemeName)){
            return "default2";
        }else if("default".equals(easyuiThemeName)){
            return "success";
        }
        return "default2";
	}
	/**
	 * 退出系统
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-22
	 */
	@UserOperateLog(key="Login",type=0,value="退出系统 成功")
	public String logout() throws Exception{
		addJSONObject("flag", true);
		return "success";
	}
	/**
	 * 显示用户的菜单树
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-5
	 */
	public String showUserMenuTree() throws Exception{
		//读cookie
		String easyuiThemeName = getThemeName();
		String url = request.getParameter("url");
		//获取用户拥有的权限列表
		List authorityList = getUserAuthorityList();
		List<Operate> list = null;
		List<Operate> dataList = securityService.showUserMenuTree(authorityList,easyuiThemeName);
        //根据URL地址 获取该url下的资源
		if(StringUtils.isNotEmpty(url)){
			url = "/"+url+".do";
			Operate operate = securityService.getOperateByURL(url);
			if(null!=operate){
				list = filterOperateByURL("",url, dataList);
			}
		}else{
			list = dataList;
		}
        //移除移动平台的权限 不显示在系统页面上
        List<Operate> list1 = removeOperateByURL("/PhoneAndScanModule.do", list);
		List<Operate>  treeIndexList = new ArrayList();
		if(null==url && !MenuPropertiesUtils.getIsSuperAccess()){
			for(Operate operate : list1){
				Map<String, String> pMap = MenuPropertiesUtils.getpMap();
				for(Map.Entry<String, String> vo : pMap.entrySet()){
					if(operate.getUrl().equals(vo.getValue())){
						treeIndexList.add(operate);
						break;
					}
				}
				if("0".equals(operate.getOperateid())){
					treeIndexList.add(operate);
				}
			}
		}else{
			treeIndexList=list1;
		}

		List treeList = new ArrayList();
		for(Operate operate : treeIndexList){
			MenuTree menuTree = new MenuTree();
			menuTree.setId(operate.getOperateid());
			menuTree.setpId(operate.getPid());
//			if("0".equals(operate.getPid())){
//				menuTree.setName("<img src='"+operate.getImage()+"'/>"+operate.getOperatename());
//			}
//			else{
//				menuTree.setName(operate.getOperatename());
//			}
			menuTree.setName(operate.getOperatename());
			menuTree.setDescription(operate.getDescription());
			menuTree.setIcon(operate.getImage());
			menuTree.setUrlStr(operate.getUrl());
			menuTree.setNavigation(operate.getNavigation());
			if(null==operate.getPid()){
				menuTree.setOpen("true");
			}else{
				treeList.add(menuTree);
			}
		}
		addJSONArray(treeList);
		return "success";
	}

    /**
     * 根据URL移除该URL下的全部资源
     * @param url
     * @param list
     * @return
     * @throws Exception
     */
    public List<Operate> removeOperateByURL(String url,List<Operate> list) throws Exception{
        List<Operate> dataList = (List<Operate>) CommonUtils.deepCopy(list);
        List<Operate> removeList = new ArrayList<Operate>();
        String pid = null;
        for(Operate operate : list){
            if(url.equals(operate.getUrl())){
                pid = operate.getOperateid();
                break;
            }
        }
        if(StringUtils.isNotEmpty(pid)){
            for(Operate operate : list){
                if(pid.equals(operate.getOperateid())){
                    removeList.add(operate);
                    List dataList1 = filterOperateByPid(operate.getNavigation(),pid, list);
                    if(null!=dataList1){
                        removeList.addAll(dataList1);
                    }
                    break;
                }
            }
        }
        for(Operate operate : removeList){
            for(Operate operate1 : dataList){
                if(operate.getOperateid().equals(operate1.getOperateid())){
                    dataList.remove(operate1);
                    break;
                }
            }
        }
        HashSet hs = new HashSet(dataList);
        Iterator i = hs.iterator();
        List newDatalist = new ArrayList();
        while(i.hasNext()){
            newDatalist.add(i.next());
        }
        Collections.sort(newDatalist, new OperateComparator());
        return newDatalist;
    }
    /**
     * 根据url获取该url下的资料列表
     * @param name
     * @param url
     * @param list
     * @return
     * @throws Exception
     */
	public List<Operate> filterOperateByURL(String name, String url,List<Operate> list) throws Exception{
		List<Operate> dataList = new ArrayList<Operate>();
		String pid = null;
		for(Operate operate : list){
			if(url.equals(operate.getUrl())){
				pid = operate.getOperateid();
				if(StringUtils.isNotEmpty(name)){
					name += ">>"+operate.getOperatename();
				}else{
					name = operate.getOperatename();
				}
				break;
			}
		}
		if(StringUtils.isNotEmpty(pid)){
			for(Operate operate : list){
				if(pid.equals(operate.getPid())){
					operate.setNavigation(name);
					dataList.add(operate);
					List dataList1 = filterOperateByPid(operate.getNavigation(),pid, list);
					if(null!=dataList1){
						dataList.addAll(dataList1);
					}
				}
			}
		}
		HashSet hs = new HashSet(dataList);  
        Iterator i = hs.iterator();  
        List newDatalist = new ArrayList();
        while(i.hasNext()){  
        	newDatalist.add(i.next());
        }
        Collections.sort(newDatalist,new OperateComparator());
		return newDatalist;
	}
	public List<Operate> filterOperateByPid(String name,String pid,List<Operate> list) throws Exception{
		List<Operate> dataList = null;
		if(StringUtils.isNotEmpty(pid)){
			dataList = new ArrayList<Operate>();
			for(Operate operate : list){
				if(pid.equals(operate.getPid())){
					operate.setNavigation(name+"&nbsp;>>&nbsp;"+operate.getOperatename());
					dataList.add(operate);
					List dataList1 = filterOperateByPid(operate.getNavigation(),operate.getOperateid(), list);
					if(null!=dataList1){
						dataList.addAll(dataList1);
					}
				}
			}
		}
		return dataList;
	}
	/**
	 * 验证密码
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 23, 2013
	 */
	public String checkPwd() throws Exception{
		SysUser user = sysUserService.getUser(username);
		boolean flag = false;
		String msg = "";
		boolean isEnough = true;
		if(!securityService.isLogin(username)){
			UserLoginNum userLoginNum = securityService.getUserLoginNum();
			int sysnum = AgentHasp.getSysNum();
			if(sysnum>0 && sysnum <=userLoginNum.getSysnum()){
				isEnough = false;
				msg = "系统限制登录用户数:"+sysnum+",当前用户登录数:"+userLoginNum.getSysnum();
			}
		}
		if(isEnough){
			if(AgentHasp.isInTryDate()){
				String companyname = getSysParamValue("COMPANYNAME");
				if(null!=user && CommonUtils.MD5(password).equals(user.getPassword())){
					flag = true;
				}
			}else{
				msg = "系统不能使用。未激活授权或者授权到期。";
			}
		}
		//写入cookie
		if(flag){
			//是否记录密码
			String ismemory = request.getParameter("ismemory");
			String password1 = CodeUtils.aesEncrypt(password, "passwordagent");
            Cookie[] cookies = request.getCookies();
            boolean hasNameCookie = false;
            boolean hasPwdCookie = false;
            boolean hasMenoryCookie = false;
            if (null!=cookies) {
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals("username")){
                        hasNameCookie = true;
                        cookie.setValue(CodeUtils.aesEncrypt(username,"passwordagent"));
                        cookie.setPath("/");
                        cookie.setMaxAge(3600*24*7);
                        response.addCookie(cookie);
                    }else if(cookie.getName().equals("ismemory")){
                        hasMenoryCookie = true;
                        cookie.setValue(ismemory);
                        cookie.setPath("/");
                        cookie.setMaxAge(3600*24*7);
                        response.addCookie(cookie);
                    }else if(cookie.getName().equals("password")){
                        if("1".equals(ismemory)){
                            hasPwdCookie = true;
                            cookie.setValue(password1);
                            cookie.setPath("/");
                            cookie.setMaxAge(3600*24*7);
                            response.addCookie(cookie);
                        }else{
                            cookie.setValue(null);
                            cookie.setPath("/");
                            cookie.setMaxAge(3600*24*7);
                            response.addCookie(cookie);
                        }
                    }
                }
            }
            if(!hasNameCookie){
                Cookie cookie = new Cookie("username", CodeUtils.aesEncrypt(username,"passwordagent"));
                cookie.setPath("/");
                cookie.setMaxAge(3600*24*7);
                response.addCookie(cookie);
            }
            if(!hasMenoryCookie){
                Cookie cookie = new Cookie("ismemory",ismemory);
                cookie.setPath("/");
                cookie.setMaxAge(3600*24*7);
                response.addCookie(cookie);
            }
            if(!hasPwdCookie && "1".equals(ismemory)){
                Cookie cookie = new Cookie("password", password1);
                cookie.setPath("/");
                cookie.setMaxAge(3600*24*7);
                response.addCookie(cookie);
            }
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		addJSONObject(map);
		return "success";
	}
	/**
	 * 显示无权限页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 7, 2013
	 */
	public String noAccessPage() throws Exception{
		return "success";
	}
	/**
	 * 显示登录中页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 21, 2013
	 */
	public String logining() throws Exception{
		SysUser sysUser = getSysUser();
		if(null!=sysUser && "2".equals(sysUser.getLoginType())){
            //手机用户的session失效时间设置
            request.getSession().setMaxInactiveInterval(60*60*8);
            Map map = new HashMap();
            map.put("personid", sysUser.getPersonnelid());
			map.put("userid", sysUser.getUserid());
			map.put("name", sysUser.getName());
			if("1".equals(sysUser.getIsphone())){
				map.put("flag", true);
			}else{
				map.put("flag", false);
			}
			Personnel personnel = getPersonnelInfoById(sysUser.getPersonnelid());
			if(null!=personnel){
				map.put("type", personnel.getEmployetype());
			}
			map.put("deptid", sysUser.getDepartmentid());
			map.put("deptname", sysUser.getDepartmentname());
			//获取系统设置的 行程计算时间段
			String sysparm = getSysParamValue("PhoneRouteTime");
			if(null!=sysparm){
				sysparm = sysparm.replaceAll(":", "@");
				map.put("routetime", sysparm);
			}else{
				map.put("routetime", "");
			}
			//获取系统设置的 行程计算时间段
			String phoneGPSTime = getSysParamValue("PhoneGPSTime");
			if(null!=phoneGPSTime){
				phoneGPSTime = phoneGPSTime.replaceAll(":", "@");
				map.put("GPSTime", phoneGPSTime);
			}else{
				map.put("GPSTime", "");
			}
			//获取手机车销是否可以修改价格参数
			String OrderCarPhoneEditPrice = getSysParamValue("OrderCarPhoneEditPrice");
			if(StringUtils.isEmpty(OrderCarPhoneEditPrice)){
				OrderCarPhoneEditPrice = "0";
			}
			//获取手机车销是否可以修改价格参数
			String OrderDemandPhoneEditPrice = getSysParamValue("OrderDemandPhoneEditPrice");
			if(StringUtils.isEmpty(OrderDemandPhoneEditPrice)){
				OrderDemandPhoneEditPrice = "0";
			}
            //小数位参数
            String isBillGoodsNumAllowDecimalPlace = getSysParamValue("isBillGoodsNumAllowDecimalPlace");
            if(StringUtils.isEmpty(isBillGoodsNumAllowDecimalPlace)){
                isBillGoodsNumAllowDecimalPlace = "1";
            }
			//判断用户是否拥有 允许采购入库数量超过订单数量的权限
            boolean purchaseEnterLimit = isSysUserHaveUrl("/storage/purchaseEnterGreaterBuyOrderNum.do");
            String purchaseEnterLimitFlag = "0";
            if(purchaseEnterLimit){
                purchaseEnterLimitFlag = "1";
            }
            //获取该用户拥有的移动平台资源权限
            List authorityList = getUserAuthorityList();
            String urlList = "";
            if(authorityList.size()>0){
                List<Operate> dataList = securityService.showUserOperate(authorityList,"");
                List<Operate> phoneOplist = filterOperateByURL("","/PhoneAndScanModule.do", dataList);
                for(Operate operate : phoneOplist){
                    if(StringUtils.isNotEmpty(urlList)){
                        urlList += ","+operate.getUrl();
                    }else{
                        urlList = operate.getUrl();
                    }
                }
            }

			Map authMap = new HashMap();
            if(StringUtils.isNotEmpty(urlList)){
                authMap.put("phoneAndScanUrl",urlList);
            }
            authMap.put("isBillGoodsNumAllowDecimalPlace", isBillGoodsNumAllowDecimalPlace);
            authMap.put("purchaseEnterLimitFlag", purchaseEnterLimitFlag);
			authMap.put("OrderCarPhoneEditPrice", OrderCarPhoneEditPrice);
			authMap.put("OrderDemandPhoneEditPrice", OrderDemandPhoneEditPrice);
			//是否上传定位
            authMap.put("isUploadLocation",sysUser.getIsuploadlocation());
			//百度鹰眼配置项
            String baiduAK = getSysParamValue("baiduAK");
            String baiduYYServerid = getSysParamValue("baiduYYServerid");
            String isOpenBaiduYY = getSysParamValue("isOpenBaiduYY");
            String baiduYYHZ = getSysParamValue("baiduYYHZ");
            if(StringUtils.isNotEmpty(baiduAK)){
                authMap.put("baiduAK",baiduAK);
            }
            if(StringUtils.isNotEmpty(baiduYYServerid)){
                authMap.put("baiduYYServerid",baiduYYServerid);
            }
            if(StringUtils.isNotEmpty(isOpenBaiduYY)){
                authMap.put("isOpenBaiduYY",isOpenBaiduYY);
            }
            if(StringUtils.isNotEmpty(baiduYYHZ)){
                authMap.put("baiduYYHZ",baiduYYHZ);
            }

            map.put("authJson", authMap);
			addJSONObject(map);
			return "phone";
		} else if(sysUser != null && "3".equals(sysUser.getLoginType())) {

			Map map = new HashMap();
			map.put("flag", true);
			map.put("personid", sysUser.getPersonnelid());
			map.put("userid", sysUser.getUserid());
			map.put("name", sysUser.getName());
			map.put("deptid", sysUser.getDeptid());


			DepartMent departMent = null;
			if(sysUser != null && StringUtils.isNotEmpty(sysUser.getDeptid())) {
				departMent = getBaseDepartMentService().showDepartMentInfo(sysUser.getDeptid());
			}

			if(departMent != null) {
				map.put("deptid", departMent.getId());
				map.put("deptname", departMent.getName());
			} else {
				map.put("storeid", "");
				map.put("storename", "");
			}


			map.put("token", getToken());

			// 是否可以改价
			map.put("editprice", false);
			String authRole = getSysParamValue("RoleCanEditPriceOnClient");

			List<SysUser> authUsers = new ArrayList<SysUser>();
			String[] auths = authRole.split(",");
			for(String authStr : auths) {
				authUsers.addAll(sysUserService.getSysUserListByRole(authStr));
			}
			boolean authFlag = false;
			for(SysUser authUser : authUsers) {

				if(sysUser.getUserid().equals(authUser.getUserid())) {
					map.put("editprice", true);
					break;
				}
			}
			addJSONObject(map);
			return "client";
		}
		return "success";
	}
	/**
	 * 首页显示中页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 21, 2013
	 */
	public String indexing() throws Exception{
		return "success";
	}
	
	/**
	 * 交款单登陆界面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年3月31日
	 */
	public String pvLogin() throws Exception{
		return SUCCESS;
	}
	/**
	 * 交款单主界面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年3月31日
	 */
	public String paymentVoucherMain() throws Exception{
		return SUCCESS;
	}
	/**
	 * 获取菜单配置文件
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2013-1-5
	 */
	public Map showMenuProperties() throws Exception{
		List<String> menuUrlList = new ArrayList();
		Map<String, String> pMap = MenuPropertiesUtils.getpMap();
		for(Map.Entry<String, String> vo : pMap.entrySet()){
			menuUrlList.add(vo.getValue());
		}
		Map map = new HashMap();
		map.put("menuUrlList",menuUrlList);
		return map;
	}
}

