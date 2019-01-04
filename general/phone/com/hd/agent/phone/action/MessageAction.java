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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.common.model.AttachFile;
import com.hd.agent.common.service.IAttachFileService;
import com.hd.agent.common.util.OfficeUtils;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.message.model.EmailContent;
import com.hd.agent.message.model.MsgContent;
import com.hd.agent.message.model.MsgNotice;
import com.hd.agent.message.model.MsgNoticeread;
import com.hd.agent.message.service.IEmailService;
import com.hd.agent.message.service.IInnerMessageService;
import com.hd.agent.message.service.INoticeService;
import com.hd.agent.phone.service.IPhoneService;

/**
 * 
 * 手机端消息中心
 * @author zhengziyong
 */
public class MessageAction extends BaseAction {
	
	private IPhoneService phoneService;
	
	public IPhoneService getPhoneService() {
		return phoneService;
	}

	public void setPhoneService(IPhoneService phoneService) {
		this.phoneService = phoneService;
	}
	
	private IEmailService emailService;

	public IEmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(IEmailService emailService) {
		this.emailService = emailService;
	}
	
	private EmailContent emailContent;


	public EmailContent getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(EmailContent emailContent) {
		this.emailContent = emailContent;
	}
	
	private MsgContent msgContent;
	public MsgContent getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(MsgContent msgContent) {
		this.msgContent = msgContent;
	}
	
	private INoticeService noticeService;
	

	public INoticeService getNoticeService() {
		return noticeService;
	}

	public void setNoticeService(INoticeService noticeService) {
		this.noticeService = noticeService;
	}

	/**
	 * 短消息
	 */
	private IInnerMessageService innerMessageService;
	
	
	public IInnerMessageService getInnerMessageService() {
		return innerMessageService;
	}

	public void setInnerMessageService(IInnerMessageService innerMessageService) {
		this.innerMessageService = innerMessageService;
	}
	/**
	 * 附件
	 */
	private IAttachFileService attachFileService;

	public IAttachFileService getAttachFileService() {
		return attachFileService;
	}

	public void setAttachFileService(IAttachFileService attachFileService) {
		this.attachFileService = attachFileService;
	}

	/**
	 * 消息中心列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月23日
	 */
	public String showMessageIndexPage() throws Exception{
		SysUser sysUser=getSysUser();
        Map queryMap=new HashMap();
        queryMap.put("wdelflag", "1");	//未删除
        queryMap.put("wviewflag", "1");	//未阅读
        queryMap.put("wrecvuserid", sysUser.getUserid());
		int unReadCount=innerMessageService.getMsgReceiveCountBy(queryMap);		
		request.setAttribute("unReadMsgCount", unReadCount);		
		queryMap.clear();
		queryMap.put("recvuserid", sysUser.getUserid());
		queryMap.put("viewflag", "1");
        queryMap.put("delflag", "1");	//未删除
		unReadCount =	emailService.getEmailReceiveCountBy(queryMap);	
		
		request.setAttribute("unReadEmailCount", unReadCount);	
		
		queryMap.clear();	
		queryMap.put("viewflag", "1"); 	//未读
		//显示查看范围
		queryMap.put("isqueryviewrang", "1");

		queryMap.put("delflag", "1");	//默认取未删除数据
		queryMap.put("state", "1");		//读取启用状态的数据
		queryMap.put("enddate", (new Date()));	//读取终止时间未到的数据
		
		queryMap.put("isshowcuruserrc", 1);		//显示当前用户是否已读
		queryMap.put("readcountuserid", sysUser.getUserid()); 	//当前用户是否已经阅读
		
		queryMap.put("curuserid", sysUser.getUserid());	//当前用户编号
		queryMap.put("curuserdept", sysUser.getDepartmentid());	//当前用户部门编号
		
		//当前权限
		List<String> authList=getUserAuthorityList();
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
		
		
		unReadCount =noticeService.getMsgNoticeCount(pageMap);
		request.setAttribute("unReadNoticeCount", unReadCount);
		return SUCCESS;
	}

	/**
	 * 显示邮件首页信息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月10日
	 */
	public String showEmailIndexPage() throws Exception{
		SysUser sysUser=getSysUser();
        Map queryMap=new HashMap();		
		queryMap.put("recvuserid", sysUser.getUserid());
		queryMap.put("viewflag", "1");
        queryMap.put("delflag", "1");	//未删除
		int unReadCount =	emailService.getEmailReceiveCountBy(queryMap);	
		
		request.setAttribute("unReadEmailCount", unReadCount);		
		
		return SUCCESS;
	}
	/**
	 * 显示邮件发送页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月14日
	 */
	public String showEmailSendPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 显示邮件发件箱列表页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月10日
	 */
	public String showEmailSendListPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 获取邮件发件箱信息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月14日
	 */
	public String getEmailSendPageList() throws Exception{
		Map queryMap=CommonUtils.changeMap(request.getParameterMap());

		String pageString = request.getParameter("page");
		int ipage = 1;
		if(StringUtils.isNotEmpty(pageString)){
			ipage = Integer.parseInt(pageString);
		}
		String rowString = request.getParameter("rows");
		int irow = 20;
		if(StringUtils.isNotEmpty(rowString)){
			irow = Integer.parseInt(rowString);
		}
		SysUser sysUser=getSysUser();
		queryMap.put("adduserid", sysUser.getUserid());

		if(!queryMap.containsKey("delflag")){
			queryMap.put("delflag", "1");	//未删除数据
		}
		queryMap.put("contentFilterHtmlShort", "true");
		if(!queryMap.containsKey("sort") || !queryMap.containsKey("order")){
			queryMap.put("sort", "addtime");
			queryMap.put("order", "desc");
		}
		pageMap.setCondition(queryMap);
		pageMap.setPage(ipage);
		pageMap.setRows(irow);
		PageData pageData=emailService.showEmailContentSimplePageList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;		
		
	}
	/**
	 * 显示邮件收件箱列表页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月10日
	 */
	public String showEmailReceiveListPage() throws Exception{

		String viewflag=request.getParameter("viewflag");
		if(null==viewflag || !"1".equals(viewflag.trim())){
			viewflag="0";
		}
		viewflag=viewflag.trim();
		request.setAttribute("viewflag", viewflag);
		
		return SUCCESS;
	}
	/**
	 * 获取邮件收件箱信息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月14日
	 */
	public String getEmailReceivePageList() throws Exception{
		Map queryMap=CommonUtils.changeMap(request.getParameterMap());

		String pageString = request.getParameter("page");
		int ipage = 1;
		if(StringUtils.isNotEmpty(pageString)){
			ipage = Integer.parseInt(pageString);
		}
		String rowString = request.getParameter("rows");
		int irow = 20;
		if(StringUtils.isNotEmpty(rowString)){
			irow = Integer.parseInt(rowString);
		}
		SysUser sysUser=getSysUser();
		
		queryMap.put("queryByReceiveUser", sysUser.getUserid());
		
		queryMap.put("contentFilterHtmlShort", "true");
		
		if(!queryMap.containsKey("sort") || !queryMap.containsKey("order")){
			queryMap.put("sort", "addtime");
			queryMap.put("order", "desc");
		}
		pageMap.setCondition(queryMap);
		pageMap.setPage(ipage);
		pageMap.setRows(irow);
		PageData pageData=emailService.showEmailContentSimplePageList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;	
		
	}
	/**
	 * 显示邮件废弃箱列表页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月10日
	 */
	public String showEmailDropListPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 获取邮件垃圾箱列表信息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月14日
	 */
	public String getEmailDropPageList() throws Exception{
		Map queryMap=CommonUtils.changeMap(request.getParameterMap());

		String pageString = request.getParameter("page");
		int ipage = 1;
		if(StringUtils.isNotEmpty(pageString)){
			ipage = Integer.parseInt(pageString);
		}
		String rowString = request.getParameter("rows");
		int irow = 20;
		if(StringUtils.isNotEmpty(rowString)){
			irow = Integer.parseInt(rowString);
		}
		SysUser sysUser=getSysUser();
		
		queryMap.put("queryByReceiveUser", sysUser.getUserid());
		
		queryMap.put("contentFilterHtmlShort", "true");
		if(!queryMap.containsKey("sort") || !queryMap.containsKey("order")){
			queryMap.put("sort", "addtime");
			queryMap.put("order", "desc");
		}
		pageMap.setCondition(queryMap);
		pageMap.setPage(ipage);
		pageMap.setRows(irow);
		PageData pageData=emailService.showEmailContentSimplePageList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;	
		
	}
	/**
	 * 邮件发送页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月14日
	 */
	public String showEmailSendDetailPage() throws Exception{
		String id = request.getParameter("id");
		EmailContent emlContent=emailService.showEmailContent(id);
		handleEmailDetailInfo(emlContent);
		request.setAttribute("emailContent", emlContent);
		return SUCCESS;
	}
	
	/**
	 * 邮件接收页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月14日
	 */
	public String showEmailReceiveDetailPage() throws Exception{
		String id = request.getParameter("id");
		EmailContent emlContent=emailService.showEmailContent(id);
		if(null!=emlContent){
			
			
			Map queryMap=new HashMap();
			SysUser sysUser=getSysUser();
			Date date=new Date();		

			queryMap.put("recvuserid", sysUser.getUserid());
			queryMap.put("notviewflag", "0");
			queryMap.put("emailid ", emlContent.getId());
			//判断是否已经阅读，没有的，更新阅读标志
			if(emailService.getEmailReceiveCountBy(queryMap)>0){
				queryMap.clear();
				queryMap.put("viewflag", "0");
				queryMap.put("viewtime", date);
				queryMap.put("wrecvuserid", sysUser.getUserid());
				queryMap.put("wnotviewflag", "0");
				queryMap.put("wemailid", emlContent.getId());
				emailService.updateEmailReceiveBy(queryMap);
			}
		}
		
		handleEmailDetailInfo(emlContent);
		request.setAttribute("emailContent", emlContent);
		return SUCCESS;
	}
	
	/**
	 * 邮件垃圾邮件页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月14日
	 */
	public String showEmailDropDetailPage() throws Exception{
		String id = request.getParameter("id");
		EmailContent emlContent=emailService.showEmailContent(id);
		handleEmailDetailInfo(emlContent);
		request.setAttribute("emailContent", emlContent);
		return SUCCESS;
	}
	/**
	 * 处理相同邮件详细
	 * @param emlContent
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月21日
	 */
	private void handleEmailDetailInfo(EmailContent emlContent) throws Exception{
		Map queryMap=new HashMap();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm E");
		if(null!=emlContent){
			SysUser user = phoneService.getSysUser(emlContent.getAdduserid());
			if(user != null){
				emlContent.setAddusername(user.getName());
			}

			request.setAttribute("date", dateFormat.format(emlContent.getAddtime()));
			
			if(StringUtils.isNotEmpty(emlContent.getReceiveuser())){
				List<SysUser> recvuserList=null;
				
				String userlist[]=emlContent.getReceiveuser().split(",");
				 
				queryMap.put("userList", userlist);
				pageMap.setCondition(queryMap);
				PageData pageData=getSysUserListByConditon(pageMap,false);
				recvuserList=pageData.getList();
				request.setAttribute("recvuserList", recvuserList);
				if(recvuserList.size()>3){
					request.setAttribute("showmore", "1");
				}
			}
		}
	}
	/**
	 * 发送邮件
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月17日
	 */
	public String addEmailSend() throws Exception{
		boolean flag=false;
		Map<String,Object> msgMap=new HashMap<String,Object>();
		emailContent.setSendflag("1"); //发送邮件
		if(StringUtils.isEmpty(emailContent.getTitle()) || "".equals(emailContent.getTitle().trim())){	
			msgMap.put("msg", "请填写电子邮件标题");
			msgMap.put("flag", flag);	
			addJSONObject(msgMap);
			return "success";			
		}
		if(StringUtils.isEmpty(emailContent.getReceiveuser()) && StringUtils.isEmpty(emailContent.getTowebmail())){	
			msgMap.put("msg", "请添加接收人");
			msgMap.put("flag", flag);	
			addJSONObject(msgMap);
			return "success";				
		}
		emailContent.setAddtime(new Date());
		if(null==emailContent.getIsreceipt() || "".equals(emailContent.getIsreceipt().trim())){
			emailContent.setIsreceipt("0");	//不需要收条
		}
		if(null==emailContent.getIsmsg() || "".equals(emailContent.getIsmsg().trim())){
			emailContent.setIsmsg("0");	//不需要提醒
		}
		String attachdelete=request.getParameter("attachdelete");
		Map attachParam=new HashMap();
		SysUser sysUser=getSysUser();
		//attachParam.put("delAttachIdarrs", attachdelete);
		attachParam.put("sysUser", sysUser);
		attachParam.put("authList", getUserAuthorityList());

		emailContent.setAdduserid(sysUser.getUserid());
		flag=emailService.addEmail(emailContent,attachParam);
		
		if(flag && "1".equals(emailContent.getSendflag()) && "1".equals(emailContent.getIsmsg())){
			Map<String,Object> map=new HashMap<String,Object>();
			StringBuilder receiverSb=new StringBuilder();
			if(StringUtils.isNotEmpty(emailContent.getReceiveuser())){
				receiverSb.append(emailContent.getReceiveuser());
				receiverSb.append(",");
			}
			if(StringUtils.isNotEmpty(emailContent.getCopytouser())){
				receiverSb.append(emailContent.getCopytouser());
				receiverSb.append(",");
			}
			if(StringUtils.isNotEmpty(emailContent.getSecrettouser())){
				receiverSb.append(emailContent.getSecrettouser());
				receiverSb.append(",");
			}			
			
			map.put("mtiptype", "1");	//短信
			map.put("msgtype", "3"); //电子邮件
			map.put("senduserid",sysUser.getUserid());
			map.put("tabtitle", "邮件详情查看");
			map.put("receivers", receiverSb.toString());
			map.put("title", "请查收我的邮件！主题："+emailContent.getTitle());
			map.put("content", "请查收我的邮件！<br/>主题："+emailContent.getTitle());
			map.put("remindurl", "message/email/emailDetailPage.do?id="+emailContent.getId()+"&showoper=1");
			addMessageReminder(map);
		}
		if(flag){
			logStr="手机端邮件发送成功";
		}else{
			logStr="手机端邮件发送失败";
		}
		addJSONObject("flag", flag);
		return "success";
	}

	/**
	 * 显示邮件附件列表
	 * @return
	 * @throws Exception
	 */
	public String showEmailAttachListPage() throws Exception{
		String id=request.getParameter("id");
		EmailContent emlContent=emailService.showEmailContent(id);
		if(null==emlContent){
			request.setAttribute("msg","未能找到相关附件信息");
			return SUCCESS;
		}
		if(StringUtils.isEmpty(emlContent.getAttach())){
			request.setAttribute("msg","未能找到相关附件信息");
			return SUCCESS;
		}
		Map queryMap=new HashMap();
		queryMap.put("idarrs",emlContent.getAttach());
		List<AttachFile> attachFileList=attachFileService.getAttachFileList(queryMap);
		if(attachFileList==null || attachFileList.size()==0){
			request.setAttribute("msg","未能找到相关附件信息");
			return SUCCESS;
		}
		request.setAttribute("attachDataList",attachFileList);
		return SUCCESS;
	}
	
	/**
	 * 短消息首页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月20日
	 */
	public String showInnerMessageIndexPage() throws Exception{
		SysUser sysUser=getSysUser();
        Map queryMap=new HashMap();
        queryMap.put("wdelflag", "1");	//未删除
        queryMap.put("wviewflag", "1");	//未阅读
        queryMap.put("wrecvuserid", sysUser.getUserid());
		int unReadCount=innerMessageService.getMsgReceiveCountBy(queryMap);		
		request.setAttribute("unReadMsgCount", unReadCount);	
		
		return SUCCESS;
	}
	/**
	 * 短消息发送页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月20日
	 */
	public String showInnerMessageSendPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 发送短消息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月20日
	 */
	public String sendInnerMessage() throws Exception{
		//操作标志
		boolean flag=false;
		Map msgMap=new HashMap();
		if("".equals(msgContent.getReceivers()) || msgContent.getReceivers().split(",").length==0){
			msgMap.put("flag", flag);
			msgMap.put("msg", "请输入短信接收人不能为空");
			
			addJSONObject(msgMap);
			return "success";		
			
		}		
		if("".equals( msgContent.getContent())){
			msgMap.put("flag", flag);
			msgMap.put("msg", "请输入短信内容不能为空");
			
			addJSONObject(msgMap);
			return "success";			
		}
		String txtcontent=CommonUtils.htmlFilter(msgContent.getContent());
		if(txtcontent.length()>300){
			msgContent.setTitle(txtcontent.substring(0, 300));
		}else{
			msgContent.setTitle(txtcontent);
		}
		//立即发送
		msgContent.setClocktype("1");
		
		SysUser sysUser=getSysUser();
		msgContent.setAdduserid(sysUser.getUserid());
		msgContent.setMsgtype("1");	//消息类型为内部短信
		msgContent.setUrl(""); //内部消息详细地址为空
		
		
		
		flag=innerMessageService.addSendMessage(msgContent);
		
		//返回json字符串
		addJSONObject("flag",flag);
		logStr="手机端发送短消息";
		return "success";
	}

	/**
	 * 短消息详细页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月20日
	 */
	public String showInnerMessageDetailPage() throws Exception{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm E");
		String id = request.getParameter("id");
		MsgContent msgContent=innerMessageService.showMsgContent(id);
		Map queryMap=new HashMap();
		if(null!=msgContent){
			//更新
			String markreceiveflag=request.getParameter("markreceiveflag");
			if("1".equals(markreceiveflag) || "true".equals(markreceiveflag)){
				markViewFlagInnerMessageByRemind(id.trim(),true);
			}
			
			if("system".equals(msgContent.getAdduserid())){
				msgContent.setAddusername("系统");
			}else{
				SysUser user = phoneService.getSysUser(msgContent.getAdduserid());
				if(user != null){
					msgContent.setAddusername(user.getName());
				}			
			}
			request.setAttribute("date", dateFormat.format(msgContent.getAddtime()));
			
			if(StringUtils.isNotEmpty(msgContent.getReceivers())){
				List<SysUser> recvuserList=null;
				
				String userlist[]=msgContent.getReceivers().split(",");
				 
				queryMap.put("userList", userlist);
				pageMap.setCondition(queryMap);
				PageData pageData=getSysUserListByConditon(pageMap,false);
				recvuserList=pageData.getList();
				request.setAttribute("recvuserList", recvuserList);
				if(recvuserList.size()>3){
					request.setAttribute("showmore", "1");
				}
			}
			Pattern pattern=null;   
			Matcher matcher=null;    
			String urlTemp="";
			if(StringUtils.isNotEmpty(msgContent.getUrl())){
				if("2".equals(msgContent.getMsgtype())){
					//公告
					urlTemp="phone/message/showNoticeDetailPage.do?id=";
					pattern=Pattern.compile("noticeid=(\\d+)");
					matcher=pattern.matcher(msgContent.getUrl());
					if(matcher.find()){
						if(null!=matcher.group(1)){
							msgContent.setUrl(urlTemp+matcher.group(1));
						}
					}
					
				}else if("3".equals(msgContent.getMsgtype())){
					//邮件
					urlTemp="phone/message/showEmailReceiveDetailPage.do?id=";
					pattern=Pattern.compile("id=(\\d+)");
					matcher=pattern.matcher(msgContent.getUrl());
					if(matcher.find()){
						if(null!=matcher.group(1)){
							msgContent.setUrl(urlTemp+matcher.group(1));
						}
					}
				}else if("4".equals(msgContent.getMsgtype())){
					msgContent.setUrl(msgContent.getUrl().replace("act/", "act/phone/"));
				}
			}
		}
		request.setAttribute("msgContent", msgContent);
		return SUCCESS;
	}
	
	/**
	 * 短消息接收页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月20日
	 */
	public String showInnerMessageSendListPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 短消息发送的列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月20日
	 */
	public String getInnerMessageSendPageData() throws Exception{
		int ipage = 1;
		String pageString = request.getParameter("page");
		if(StringUtils.isNotEmpty(pageString)){
			ipage = Integer.parseInt(pageString);
		}
		String rowString = request.getParameter("rows");
		int irow = 20;
		if(StringUtils.isNotEmpty(rowString)){
			irow = Integer.parseInt(rowString);
		}
		SysUser sysUser=getSysUser();
		Map queryMap=CommonUtils.changeMap(request.getParameterMap());
		queryMap.put("delflag", "1"); // 1表示未删除，默认不显示已经删除的发送内容
		queryMap.put("recvuserid", sysUser.getUserid());
		
		
		if(!queryMap.containsKey("sort") || !queryMap.containsKey("order")){
			queryMap.put("sort", "addtime");
			queryMap.put("order", "desc");
		}
		
		pageMap.setCondition(queryMap);
		pageMap.setPage(ipage);
		pageMap.setRows(irow);
		//setQueryRulesWithAlias("msgr");
		PageData pageData=innerMessageService.showMsgContentSimplePageList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	/**
	 * 短消息接收页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月20日
	 */
	public String showInnerMessageReceiveListPage() throws Exception{
		String viewflag=request.getParameter("viewflag");
		if(null==viewflag || !"1".equals(viewflag.trim())){
			viewflag="0";
		}
		viewflag=viewflag.trim();
		request.setAttribute("viewflag", viewflag);
		
		String markreceiveflag=request.getParameter("markreceiveflag");
		if("1".equals(markreceiveflag) || "true".equals(markreceiveflag)){
			markViewFlagInnerMessageByRemind(null,false);
		}
		return SUCCESS;
	}
	/**
	 * 接收短消息列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月20日
	 */
	public String getInnerMessageReceivePageData() throws Exception{
		Map queryMap=CommonUtils.changeMap(request.getParameterMap());
		int ipage = 1;
		String pageString = request.getParameter("page");
		if(StringUtils.isNotEmpty(pageString)){
			ipage = Integer.parseInt(pageString);
		}
		String rowString = request.getParameter("rows");
		int irow = 20;
		if(StringUtils.isNotEmpty(rowString)){
			irow = Integer.parseInt(rowString);
		}
		SysUser sysUser=getSysUser();
		queryMap.put("delflag", "1"); // 1表示未删除，默认不显示已经删除的发送内容
		queryMap.put("recvuserid", sysUser.getUserid());

		queryMap.put("contentFilterHtmlShort", "true");
		
		if(!queryMap.containsKey("sort") || !queryMap.containsKey("order")){
			queryMap.put("sort", "sendtime");
			queryMap.put("order", "desc");
		}
		
		pageMap.setCondition(queryMap);
		pageMap.setPage(ipage);
		pageMap.setRows(irow);
		//setQueryRulesWithAlias("msgr");
		PageData pageData=innerMessageService.getMsgReceivePageList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}

	/**
	 * 标记内部短消息为已经
	 * @param msgid 消息内容编号
	 * @param upviewflag 是否更新已阅读标志
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月22日
	 */
	private boolean markViewFlagInnerMessageByRemind(String msgid,boolean upviewflag) throws Exception{
		SysUser sysUser=getSysUser();
		Date date=new Date();
		Map map=new HashMap();
		boolean flag=false;
		
		//如果有传入消息内容编号的，先按内容编号查询，如果没有的，按查询出所有发送时间小于当前的日期的
		if(null!=msgid && !"".equals(msgid.trim())){
			map.put("msgid", msgid.trim());
		}else {
			map.put("wsendtimeBeforeTime",date);//靠时间
		}

		//条件
		map.put("wrecvuserid", sysUser.getUserid());
		if(upviewflag){
			map.put("wnotviewflag", "0");		
		}else{
			map.put("wnotrecvflag", "0");
		}	
		
		if(innerMessageService.getMsgReceiveCountBy(map)>0){
			//如果更新查看标记，则需要更新接收标志
			map.put("recvflag","0");
			map.put("recvtime", date);
			if(upviewflag){
				map.put("viewflag","0");
				map.put("viewtime",date);			
			}
			flag=innerMessageService.updateMsgReceiveBy(map);
		}
		return flag;
	}
	
	/**
	 * 公告列表页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月20日
	 */
	public String showNoticeListPage() throws Exception{
		String viewflag=request.getParameter("viewflag");
		if(null==viewflag || !"1".equals(viewflag.trim())){
			viewflag="0";
		}
		viewflag=viewflag.trim();
		request.setAttribute("viewflag", viewflag);
		return SUCCESS;
	}
	/**
	 * 公告列表信息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月20日
	 */
	public String getNoticePageData() throws Exception{
		int ipage = 1;
		String pageString = request.getParameter("page");
		if(StringUtils.isNotEmpty(pageString)){
			ipage = Integer.parseInt(pageString);
		}
		String rowString = request.getParameter("rows");
		int irow = 20;
		if(StringUtils.isNotEmpty(rowString)){
			irow = Integer.parseInt(rowString);
		}
		SysUser sysUser=getSysUser();
		Map queryMap=CommonUtils.changeMap(request.getParameterMap());
		
		//显示查看范围
		queryMap.put("isqueryviewrang", "1");

		queryMap.put("delflag", "1");	//默认取未删除数据
		queryMap.put("state", "1");		//读取启用状态的数据
		queryMap.put("enddate", (new Date()));	//读取终止时间未到的数据
		
		queryMap.put("isshowcuruserrc", 1);		//显示当前用户是否已读
		queryMap.put("readcountuserid", sysUser.getUserid()); 	//当前用户是否已经阅读
		
		queryMap.put("curuserid", sysUser.getUserid());	//当前用户编号
		queryMap.put("curuserdept", sysUser.getDepartmentid());	//当前用户部门编号
		
		//当前权限
		List<String> authList=getUserAuthorityList();
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
		
		
		if(!queryMap.containsKey("sort") || !queryMap.containsKey("order")){
			queryMap.put("sort", "addtime");
			queryMap.put("order", "desc");
		}
		

		pageMap.setCondition(queryMap);
		pageMap.setPage(ipage);
		pageMap.setRows(irow);

		queryMap.put("contentFilterHtmlShort", "true");
		PageData pageData=noticeService.showMsgNoticePageList(pageMap);
		
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 公告详细页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月20日
	 */
	public String showNoticeDetailPage() throws Exception{

		SysUser sysUser=getSysUser();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm E");
		String id = request.getParameter("id");
		MsgNotice msgNotice=noticeService.showMsgNotice(id);
		if(null!=msgNotice){
			

			//更新
			String markreceiveflag=request.getParameter("markreceiveflag");
			if("1".equals(markreceiveflag) || "true".equals(markreceiveflag)){
				if(noticeService.getMsgNoticereadCountBy(id.trim(),sysUser.getUserid())==0){
					MsgNoticeread msgNoticeread=new MsgNoticeread();
					msgNoticeread.setNoticeid(msgNotice.getId());
					msgNoticeread.setReceivetime(new Date());
					msgNoticeread.setReceiveuserid(sysUser.getUserid());
					noticeService.addMsgNoticeread(msgNoticeread);
				}
			}

			
			//request.setAttribute("date", dateFormat.format(msgNotice.getAddtime()));
			
		}
		request.setAttribute("msgNotice", msgNotice);
		return SUCCESS;
	}

	/**
	 * 显示邮件附件列表
	 * @return
	 * @throws Exception
	 */
	public String showNoticeAttachListPage() throws Exception{
		String id=request.getParameter("id");
		MsgNotice msgNotice=noticeService.showMsgNotice(id);
		if(null==msgNotice){
			request.setAttribute("msg","未能找到相关附件信息");
			return SUCCESS;
		}
		if(StringUtils.isEmpty(msgNotice.getAttach())){
			request.setAttribute("msg","未能找到相关附件信息");
			return SUCCESS;
		}
		Map queryMap=new HashMap();
		queryMap.put("idarrs",msgNotice.getAttach());
		List<AttachFile> attachFileList=attachFileService.getAttachFileList(queryMap);
		if(attachFileList==null || attachFileList.size()==0){
			request.setAttribute("msg","未能找到相关附件信息");
			return SUCCESS;
		}
		request.setAttribute("attachDataList",attachFileList);
		return SUCCESS;
	}

	public String showMessageAttachViewPage() throws Exception{
		String id = request.getParameter("id");
		request.setAttribute("image", "");
		request.setAttribute("text", false);
		String content = "";        // 文本文件内容
		String charset = "UTF-8";   // 文本文件编码
		String information = "";

		AttachFile attach = attachFileService.getAttachFile(id);
		String ext = CommonUtils.nullToEmpty(attach.getExt()).toLowerCase();
        request.setAttribute("attach", attach);
        String WebOffice = getSysParamValue("WebOffice");
        if("1".equals(WebOffice) || "0".equals(WebOffice)) {
            // 图片
            if (".jpg".equals(ext) || ".jpeg".equals(ext) || ".bmp".equals(ext) || ".gif".equals(ext) || ".png".equals(ext)) {

                request.setAttribute("image", attach.getFullpath());

                // 文本
            } else if (".txt".equals(ext)) {

                request.setAttribute("text", true);
                String path = attach.getFullpath();
                //文件存放路径
                String filepath = OfficeUtils.getFilepath();
                filepath = filepath.replace("upload", "");
                String fullPath = filepath + path;
                File file = new File(fullPath);

                String charset2 = CommonUtils.getFileCharset(file);
                if (StringUtils.isNotEmpty(charset2)) {

                    charset = charset2;
                }

                FileInputStream fis = null;
                BufferedInputStream bis = null;
                byte[] bytes = new byte[1024];
                StringBuilder sb = new StringBuilder();

                try {

                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    int length = 0;
                    long total = 0L;

                    while ((length = bis.read(bytes)) > 0) {

                        total = total + length;
                        sb.append(new String(bytes, 0, length, charset));
                        if (total >= 1024 * 1024) {
                            information = "内容过多，无法全部显示！";
                            break;
                        }
                    }

                    content = new String(sb);
                    // content = content.replace(" ", "&nbsp;").replace("\r\n", "<br/>").replace("\n", "<br/>").replace("\r", "<br/>");
                    request.setAttribute("information", information);
                    request.setAttribute("content", content);

                } catch (Exception e) {

                } finally {

                    if (bis != null) {
                        bis.close();
                    }
                    if (fis != null) {
                        fis.close();
                    }
                }
            }
            return SUCCESS;
        }else if("2".equals(WebOffice)){
            if(".xls".equals(ext)
                    || ".xlsx".equals(ext)
                    || ".doc".equals(ext)
                    || ".docx".equals(ext)
                    || ".pdf".equals(ext)
                    || ".pdf".equals(ext)
                    || ".pptx".equals(ext)
                    || ".ppt".equals(ext)){
                //永中在线文件查看API地址
                String YongZhongWebApi = getSysParamValue("YongZhongWebApi");
                if(StringUtils.isNotEmpty(YongZhongWebApi)){
                    String FileWebUrl = getSysParamValue("FileWebUrl");
                    if(!FileWebUrl.endsWith("/")){
                        FileWebUrl += "/";
                    }
                    YongZhongWebApi += "&url="+FileWebUrl+attach.getFullpath();
                    request.setAttribute("url",YongZhongWebApi);
                    response.sendRedirect(YongZhongWebApi);
//                    return "YongZhongWebSuccess";
                }
            }else if(".jpg".equals(ext) || ".gif".equals(ext) || ".png".equals(ext)
                    || ".bmp".equals(ext) || ".jpeg".equals(ext)){
                request.setAttribute("image", attach.getFullpath());
                return SUCCESS;
            }else{
                response.sendRedirect("../"+attach.getFullpath());
                return SUCCESS;
            }
        }
		return null;
	}
}

