/**
 * @(#)OaAction.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Dec 16, 2013 zhengziyong 创建版本
 */
package com.hd.agent.phone.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.OfficeUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.message.model.EmailContent;
import com.hd.agent.message.model.MsgNotice;
import com.hd.agent.message.service.IEmailService;
import com.hd.agent.phone.service.IPhoneOaService;
import com.hd.agent.phone.service.IPhoneService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 
 * @author zhengziyong
 */
public class OaAction extends BaseAction {
	
	private IPhoneService phoneService;
	private IPhoneOaService phoneOaService;
	
	public IPhoneService getPhoneService() {
		return phoneService;
	}

	public void setPhoneService(IPhoneService phoneService) {
		this.phoneService = phoneService;
	}
	
	public IPhoneOaService getPhoneOaService() {
		return phoneOaService;
	}

	public void setPhoneOaService(IPhoneOaService phoneOaService) {
		this.phoneOaService = phoneOaService;
	}

	/**
	 * 我的工作
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Dec 30, 2013
	 */
	public String work() throws Exception{
		
		return SUCCESS;
	}
	
	/**
	 * 获取邮件列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jan 8, 2014
	 */
	public String getEmailList() throws Exception{
		String userId = request.getParameter("userid");
		String page = request.getParameter("page");
		int p = 1;
		if(StringUtils.isNotEmpty(page)){
			p = Integer.parseInt(page);
		}
		Map map = new HashMap();
		map.put("recvuserid", userId);
		if(!map.containsKey("delflag")){
			map.put("delflag", "1");	//未删除数据
		}
		pageMap.setCondition(map);
		pageMap.setPage(p);
		pageMap.setRows(20);
		PageData pageData=phoneOaService.getEmailData(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 邮件查询页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jan 8, 2014
	 */
	public String showEmailPage() throws Exception{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd E");
		String id = request.getParameter("id");
		IEmailService emailService = (IEmailService)SpringContextUtils.getBean("emailService");
		EmailContent emlContent=emailService.showEmailContent(id);
		SysUser user = phoneService.getSysUser(emlContent.getAdduserid());
		if(user != null){
			emlContent.setAddusername(user.getName());
		}
		request.setAttribute("date", dateFormat.format(emlContent.getAddtime()));
		request.setAttribute("content", emlContent);
		return SUCCESS;
	}
	
	/**
	 * 最新未读公告通知
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jan 6, 2014
	 */
	public String getNoticeNoread() throws Exception{
		String userId = request.getParameter("userid");
		
		return SUCCESS;
	}
	
	/**
	 * 获取公告通知列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Dec 30, 2013
	 */
	public String getNoticeList() throws Exception{
		String userId = request.getParameter("userid");
		String page = request.getParameter("page");
		int p = 1;
		if(StringUtils.isNotEmpty(page)){
			p = Integer.parseInt(page);
		}
		SysUser sysUser = phoneService.getSysUser(userId);
		Map queryMap = new HashMap();
		//显示查看范围
		queryMap.put("isqueryviewrang", "1");

		if(!queryMap.containsKey("delfalg")){
			queryMap.put("delflag", "1");	//默认取未删除数据
		}
		if(!queryMap.containsKey("state")){
			queryMap.put("state", "1");		//读取启用状态的数据
		}
		if(!queryMap.containsKey("enddate")){
			queryMap.put("enddate", (new Date()));	//读取终止时间未到的数据
		}
		if(!queryMap.containsKey("isshowcuruserrc")){
			queryMap.put("isshowcuruserrc", 1);		//显示当前用户是否已读
		}		
		queryMap.put("curuserid", sysUser.getUserid());	//当前用户编号
		queryMap.put("curuserdept", sysUser.getDepartmentid());	//当前用户部门编号
		//当前权限
		List<String> authList=phoneService.getUserAuthorityList(sysUser.getUserid());
		List<String> roleList=new ArrayList<String>();
		if(null!=authList && authList.size()>0 ){
			for(String item : authList){
				if(null!=item && !"".equals(item)){
					roleList.add(item.trim());
				}
			}
			if(roleList.size()>0){
				queryMap.put("curusrolelist", roleList);
			}
		}
		pageMap.setCondition(queryMap);
		pageMap.setPage(p);
		pageMap.setRows(20);
		PageData pageData=phoneOaService.getNoticeData(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 公告通知明细
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 11, 2014
	 */
	public String showNoticePage() throws Exception{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String userid = request.getParameter("userid");
		String id = request.getParameter("id");
		MsgNotice notice = phoneOaService.getNoticeDetail(id, userid);
		request.setAttribute("date", dateFormat.format(notice.getAddtime()));
		request.setAttribute("notice", notice);
		return SUCCESS;
	}
	/**
	 * 签到并上传附件
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年9月19日
	 */
	@UserOperateLog(key="OA",type=2)
	public String uploadSignIn() throws Exception{
		//生成上传的附件
		MultiPartRequestWrapper requestWrapper = (MultiPartRequestWrapper)request;
		File[] files = requestWrapper.getFiles("file");
		String[] fileNames = requestWrapper.getFileNames("file");
		//文件存放路径
		String filepath = OfficeUtils.getFilepath();
		String path = filepath + File.separator+"sign";
		String filestr = "";
		File pathfile = new File(path);
		if(!pathfile.exists()){
			pathfile.mkdir();
		}
		for(int i=0; i<files.length; i++){
            String newFilename = CommonUtils.getDataNumberWithRand()+".jpg";
            File file = new File(path,newFilename);
            filestr = "upload/sign/"+newFilename;
            FileUtils.copyFile(files[i], file);
		}
		//获取参数
		String remark = request.getParameter("remark");
		String x = request.getParameter("x");
		String y = request.getParameter("y");
		String numstr = request.getParameter("num");
		int num = 1;
		if(StringUtils.isNotEmpty(numstr)){
			num = Integer.parseInt(numstr);
		}
		boolean flag = phoneOaService.addOrUpdateSingIn(remark, x, y, filestr, num);
		Map returnMap = new HashMap();
		returnMap.put("flag", flag);
		returnMap.put("time", CommonUtils.dataToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
		if(!flag){
			returnMap.put("msg", "已签到，不能重复签到");
		}
		addJSONObject(returnMap);
		addLog("手机用户签到", flag);
		return SUCCESS;
	}
	/**
	 * 获取用户的考勤信息
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年9月26日
	 */
	public String showSigninList() throws Exception{
		String begindate = request.getParameter("begindate");
		String enddate = request.getParameter("enddate");
		List list = phoneOaService.showSigninList(begindate, enddate);
		addJSONArray(list);
		return "success";
	}
}

