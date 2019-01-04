/**
 * @(#)EmailAction.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-2-2 zhanghonghui 创建版本
 */
package com.hd.agent.message.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.message.model.EmailBox;
import com.hd.agent.message.model.EmailContent;
import com.hd.agent.message.model.EmailReceive;
import com.hd.agent.message.model.WebMailConfig;
import com.hd.agent.message.service.IEmailService;
import org.apache.log4j.Logger;


/**
 * 
 * 
 * @author zhanghonghui
 */
public class EmailAction extends BaseAction  {
	private static final Logger logger = Logger.getLogger(EmailAction.class);
	/**
	 * 邮件内容
	 */
	private EmailContent emailContent;
	/**
	 * 邮箱
	 */
	private EmailBox emailBox;
	/**
	 * 外部邮箱设置
	 */
	private WebMailConfig webMailConfig;
	/**
	 * 邮件业务
	 */
	private IEmailService emailService;
	
	public EmailContent getEmailContent() {
		return emailContent;
	}
	public void setEmailContent(EmailContent emailContent) {
		this.emailContent = emailContent;
	}
	public EmailBox getEmailBox() {
		return emailBox;
	}
	public void setEmailBox(EmailBox emailBox) {
		this.emailBox = emailBox;
	}
	public IEmailService getEmailService() {
		return emailService;
	}
	public void setEmailService(IEmailService emailService) {
		this.emailService = emailService;
	}
	

	public WebMailConfig getWebMailConfig() {
		return webMailConfig;
	}
	public void setWebMailConfig(WebMailConfig webMailConfig) {
		this.webMailConfig = webMailConfig;
	}
	/**
	 * 邮件页面
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public String showEmailPage() throws Exception{
		return "success";
	}

	/**
	 * 邮件详细内容页面
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public String emailDetailPage() throws Exception{
		
		Map<String,Object> map=new HashMap<String,Object>();
		String id=request.getParameter("id");
		
		SysUser sysUser=getSysUser();
		EmailContent emlContent=emailService.showEmailContent(id);
		
		if(emlContent==null){
			request.setAttribute("showdata", "0");
			return "success";
		}
		if(!(StringUtils.isNotEmpty(emlContent.getAdduserid())
				&& emlContent.getAdduserid().equals(sysUser.getUserid()) ) ){			
			map.put("recvuserid", sysUser.getUserid());
			map.put("emailid", emlContent.getId());
			map.put("notdelflag", "0");
			int iemlc=emailService.getEmailReceiveCountBy(map);
			if(iemlc==0){
				request.setAttribute("showdata", "0");
				return "success";				
			}
		}
		//发送页面详细显示，发送者信息
		if(StringUtils.isNotEmpty(emlContent.getAdduserid())){
			SysUser addUser=getSysUserById(emlContent.getAdduserid());
			if(null!=addUser){
				emlContent.setAddusername(addUser.getName());
			}
		}
		
		//发送页面详细显示，发送者信息

		map.clear();//重置查询条件
		PageData pageData=null;
		
		//显示发送者、抄送者名字信息
		List<String> idList=new ArrayList<String>();
		String[] idarr=null;
		if(null!=emlContent.getReceiveuser()&&!"".equals(emlContent.getReceiveuser().trim())){
			idarr=emlContent.getReceiveuser().trim().split(",");
			for(String item : idarr){
				if(!"".equals(item.trim())){
					idList.add(item.trim());
				}
			}
		}
		if(null != emlContent.getCopytouser() && !"".equals(emlContent.getCopytouser().trim())){
			idarr=emlContent.getCopytouser().trim().split(",");
			for(String item : idarr){
				if(!"".equals(item.trim())&&!idList.contains(item)){
					idList.add(item.trim());
				}
			}
		}
		if(idList.size()>0){
			map.clear();//重置查询条件
			map.put("userList", idList);
			pageMap.setCondition(map);
			pageData=getSysUserListByConditon(pageMap,false);
			List<SysUser> userList=pageData.getList();
			if(userList.size()>0){
				StringBuffer sb=new StringBuffer();
				boolean isep=false;
				for(SysUser item : userList){
					if(isep){
						sb.append(",");
					}
					isep=true;
					sb.append(item.getName());
				}
				emlContent.setRecvusername(sb.toString());
			}
		}
		request.setAttribute("emailContent", emlContent);
		request.setAttribute("showdata", "1");
		
		String showoper=request.getParameter("showoper");	//是否显示操作按钮，默认显示
		if(!"0".equals(showoper)){	
			request.setAttribute("showoper", "1");
		}
		String showtitle=request.getParameter("showtitle");
		if("0".equals(showtitle)){
			request.setAttribute("showtitle", "0");
		}else{
			request.setAttribute("showtitle", "1");
		}
		String showreturn=request.getParameter("showreturn");
		if("1".equals(showreturn)){
			request.setAttribute("showreturn", "1");	
			request.setAttribute("showoper", "1");	
		}
		request.setAttribute("userviewtype", "recv");
		
		return "success";
	}
	
	/**
	 * 邮件详细内容页面
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public String emailSendDetailPage() throws Exception{
		
		Map<String,Object> map=new HashMap<String,Object>();
		String id=request.getParameter("id");
		
		
		SysUser sysUser=getSysUser();
		EmailContent emlContent=emailService.showEmailContent(id);
		
		if(emlContent==null 
				|| !(StringUtils.isNotEmpty(emlContent.getAdduserid()) && emlContent.getAdduserid().equals(sysUser.getUserid()) )
				|| "0".equals(emlContent.getDelflag())){
			request.setAttribute("showdata", "0");
			return "success";
		}
		if("2".equals(emlContent.getDelflag())){
			request.setAttribute("delstate", "0");
		}else{
			request.setAttribute("delstate", "2");
		}
		
		List<String> idList=new ArrayList<String>();
		String[] idarr=null;
		if( null!=emlContent.getReceiveuser() && !"".equals(emlContent.getReceiveuser().trim())){
			idarr=emlContent.getReceiveuser().trim().split(",");
			for(String item : idarr){
				if(!"".equals(item.trim())){
					idList.add(item.trim());
				}
			}
		}
		if(null!=emlContent.getCopytouser() && !"".equals(emlContent.getCopytouser().trim())){
			idarr=emlContent.getCopytouser().trim().split(",");
			for(String item : idarr){
				if(!"".equals(item.trim())&&!idList.contains(item)){
					idList.add(item.trim());
				}
			}
		}
		if(idList.size()>0){
			map.clear();//重置查询条件
			map.put("userList", idList);
			pageMap.setCondition(map);
			PageData pageData=getSysUserListByConditon(pageMap,false);
			List<SysUser> userList=pageData.getList();
			if(userList.size()>0){
				StringBuffer sb=new StringBuffer();
				boolean isep=false;
				for(SysUser item : userList){
					if(isep){
						sb.append(",");
					}
					isep=true;
					sb.append(item.getName());
				}
				emlContent.setRecvusername(sb.toString());
			}
		}
		request.setAttribute("emailContent", emlContent);
		request.setAttribute("showdata", "1");
		
		String showoper=request.getParameter("showoper");	//是否显示操作按钮，默认显示
		if(!"0".equals(showoper)){	
			request.setAttribute("showoper", "1");
		}
		String showtitle=request.getParameter("showtitle");
		if("0".equals(showtitle)){
			request.setAttribute("showtitle", "0");
		}else{
			request.setAttribute("showtitle", "1");
		}
		String showreturn=request.getParameter("showreturn");
		if("1".equals(showreturn)){
			request.setAttribute("showreturn", "1");	
			request.setAttribute("showoper", "1");	
		}

		request.setAttribute("userviewtype", "send");
		
		//不显示发送人
		request.setAttribute("showsender", "0");
		
		return "success";
	}
	
	/**
	 * 邮件添加页面
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public String emailAddPage() throws Exception{
		EmailContent emlContent=new EmailContent();
		String noshowtitle=request.getParameter("noshowtitle");
		if("1".equals(noshowtitle)){
			noshowtitle="1";
		}
		String touserids=request.getParameter("touserids");
		if(StringUtils.isNotEmpty(touserids)){
			emlContent.setReceiveuser(touserids);
		}
		//弹出div ID
		String emlSendPageId=request.getParameter("emlsendpageid");
		if(null==emlSendPageId){
			emlSendPageId="";
		}
		request.setAttribute("emlSendPageId",emlSendPageId);
		request.setAttribute("emailContent", emlContent);
		request.setAttribute("oper", "add");	//操作为新增
		request.setAttribute("noshowtitle", noshowtitle);
		return "success";
	}
	/**
	 * 邮件修改页面
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public String emailReplyPage() throws Exception{
		String id=request.getParameter("id");
		String oper=request.getParameter("oper");	//1是回复
		if(null==oper || "".equals(oper.trim())){
			oper="reply";
		}
		oper=oper.toLowerCase();
		if(!"reply".equals(oper) && !"forward".equals(oper) &&  !"replyall".equals(oper)){
			oper="reply";
		}
		
		EmailContent emlContent=new EmailContent();

		if(StringUtils.isNumeric(id)){
			EmailContent emlc=emailService.showEmailContent(id);
			if(emlc!=null ){
				Map<String,Object> map=new HashMap<String,Object>();
				PageData pageData=null;
				//标题
				emlContent.setTitle(emlc.getTitle());
				//内容
				emlContent.setContent(emlc.getContent());
				emlContent.setAddtime(emlc.getAddtime());
				emlContent.setWebmailflag("0");	//内部发邮件
				if("replyall".equals(oper)){
					emlContent.setCopytouser(emlc.getCopytouser());
				}
				
				if("reply".equals(oper)||"replyall".equals(oper)){
					//接收人
					emlContent.setReceiveuser(emlc.getAdduserid());				
					if(StringUtils.isNotEmpty(emlc.getAdduserid())){
						String userlist[]={emlc.getAdduserid()};
						map.put("userList", userlist);
						pageMap.setCondition(map);
						pageData=getSysUserListByConditon(pageMap,false);
						List<SysUser> uList=pageData.getList();
						if(uList!=null && uList.size()>0){
							SysUser addUser=uList.get(0);
							emlContent.setRecvusername(addUser.getName());
						}
					}
				}
				if("forward".equals(oper)){
					emlContent.setAttach(emlc.getAttach());
				}
				request.setAttribute("oper", oper);
			}
		}else{
			emlContent=new EmailContent();
		}
		//弹出div ID
		String emlSendPageId=request.getParameter("emlsendpageid");
		if(null==emlSendPageId){
			emlSendPageId="";
		}
		request.setAttribute(emlSendPageId, "emlSendPageId");
		request.setAttribute("emailContent", emlContent);
		return "success";
	}
	/**
	 * 邮件修改页面
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public String emailEditPage() throws Exception{
		String id=request.getParameter("id");
		String oper=request.getParameter("oper");
		EmailContent emlContent=null;
		if(StringUtils.isNumeric(id)){
			emlContent=emailService.showEmailContent(id);
			if(emlContent==null ){
				emlContent=new EmailContent();
			}else{
				/*
				StringBuffer sBuffer=new StringBuffer();
				//接收人姓名
				if(StringUtils.isNotEmpty(emlContent.getReceiveuser())){
					String[] idList=emlContent.getReceiveuser().split(",");
					Map map=new HashMap();
					map.put("userList", idList);
					pageMap.setCondition(map);
					PageData pageData=getSysUserListByConditon(pageMap,false);
					List<SysUser> userList=pageData.getList();
					if(userList.size()>0){
						sBuffer=new StringBuffer();
						boolean isep=false;
						for(SysUser item : userList){
							if(isep){
								sBuffer.append(",");
							}
							isep=true;
							sBuffer.append(item.getName());
						}
						emlContent.setRecvusername(sBuffer.toString());
					}
				}
				//抄送人姓名
				if(StringUtils.isNotEmpty(emlContent.getCopytouser())){
					String[] idList=emlContent.getCopytouser().split(",");
					Map map=new HashMap();
					map.put("userList", idList);
					pageMap.setCondition(map);
					PageData pageData=getSysUserListByConditon(pageMap,false);
					List<SysUser> userList=pageData.getList();
					if(userList.size()>0){
						sBuffer=new StringBuffer();
						boolean isep=false;
						for(SysUser item : userList){
							if(isep){
								sBuffer.append(",");
							}
							isep=true;
							sBuffer.append(item.getName());
						}
						emlContent.setCopytousername(sBuffer.toString());
					}
				}
				//密送人姓名
				if(StringUtils.isNotEmpty(emlContent.getSecrettouser())){
					String[] idList=emlContent.getSecrettouser().split(",");
					Map map=new HashMap();
					map.put("userList", idList);
					pageMap.setCondition(map);
					PageData pageData=getSysUserListByConditon(pageMap,false);
					List<SysUser> userList=pageData.getList();
					if(userList.size()>0){
						sBuffer=new StringBuffer();
						boolean isep=false;
						for(SysUser item : userList){
							if(isep){
								sBuffer.append(",");
							}
							isep=true;
							sBuffer.append(item.getName());
						}
						emlContent.setSecrettousername(sBuffer.toString());
					}
				}*/
			}
		}else{
			emlContent=new EmailContent();
		}
		request.setAttribute("emailContent", emlContent);
		if(StringUtils.isNotEmpty(oper) && "resend".equals(oper.trim())){
			request.setAttribute("oper", "resend");	//作为重新发送
		}else{
			request.setAttribute("oper", "edit");	//操作为编辑
		}
		//弹出div ID
		String emlSendPageId=request.getParameter("emlsendpageid");
		if(null==emlSendPageId){
			emlSendPageId="";
		}
		request.setAttribute(emlSendPageId, "emlSendPageId");
		return "success";
	}
	/**
	 * 邮件发送
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	@UserOperateLog(key="Message-Email",type=2)
	public String sendEmail() throws Exception{
		boolean flag=false;
		Map<String,Object> msgMap=new HashMap<String,Object>();
		if("1".equals(emailContent.getSendflag())){
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
			/*
			if(StringUtils.isNotEmpty(emailContent.getTowebmail())&& StringUtils.isEmpty(emailContent.getFromwebmail()) ){	
				msgMap.put("msg", "请选择Internet邮箱");
				msgMap.put("flag", flag);	
				addJSONObject(msgMap);
				return "success";					
			}
			*/
		}else{
			emailContent.setSendflag("0");
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
		attachParam.put("delAttachIdarrs", attachdelete);
		attachParam.put("sysUser", sysUser);
		attachParam.put("authList", getUserAuthorityList());
		if(emailContent.getId()!=null && emailContent.getId()>0){
			// 更新邮箱
			EmailContent emlContent=emailService.showEmailContent(emailContent.getId().toString());
			if(emlContent!=null){
				flag=false;
				if(StringUtils.isNotEmpty(sysUser.getUserid()) && sysUser.getUserid().equals(emlContent.getAdduserid()) ){
					emailContent.setAdduserid(emlContent.getAdduserid());
					emailContent.setAddtime((new Date()));
					flag=emailService.addEmail(emailContent,attachParam);
				}
			}
		}else{
			emailContent.setAdduserid(sysUser.getUserid());
			flag=emailService.addEmail(emailContent,attachParam);
		}
		if(flag && "1".equals(emailContent.getSendflag())){
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
			String tempcont=CommonUtils.htmlFilter(emailContent.getContent());
			if(tempcont.length()>300){
				tempcont=tempcont.substring(0, 300);
			}
			if("1".equals(emailContent.getIsmsg())){
				map.put("mtiptype", "1");	//短信
				map.put("msgtype", "3"); //电子邮件
				map.put("senduserid",sysUser.getUserid());
				map.put("tabtitle", "邮件详情查看");
				map.put("receivers", receiverSb.toString());
				map.put("title", "请查收我的邮件！主题："+emailContent.getTitle());
				map.put("content", "请查收我的邮件！<br/>主题："+tempcont);
				map.put("remindurl", "message/email/emailDetailPage.do?id="+emailContent.getId()+"&showoper=1");
				addMessageReminder(map);
			}else{
				String phoneUrl= "phone/message/showEmailReceiveDetailPage.do?id="+emailContent.getId()+"&backlistpagetip=true";
            	String title="";
            	if(StringUtils.isNotEmpty(sysUser.getName())){
            		title="用户："+sysUser.getName()+"向您发送了封邮件！主题:"+emailContent.getTitle();
            	}else{
            		title="请查收新邮件！主题："+emailContent.getTitle();
            	}
				String[] recArr  = receiverSb.toString().split(",");

				//是否启用内部消息推送
				//1启用时，内部短信会推荐到手机
				//0禁用时，内部短信不会推荐到手机
				//		默认值为1
				String isInnerMessagePushToMobile = getSysParamValue("InnerMessagePushToMobile");
				if(null==isInnerMessagePushToMobile || "".equals(isInnerMessagePushToMobile.trim())){
					isInnerMessagePushToMobile="1";
				}
				for(String userid : recArr){
                	if(null==userid || "".equals(userid.trim())){
                		continue;
                	}
					if("1".equals(isInnerMessagePushToMobile.trim())) {
						try {
							sendPhoneMsg(userid, "1", title, tempcont, phoneUrl);
						}catch (Exception ex){
							logger.error("推送到手机时，异常",ex);
						}
					}
				}
		}
		}
		if(flag){
			logStr="邮件发送成功";
		}else{
			logStr="邮件发送失败";
		}
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 当用户读了邮件时，发回条
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	@UserOperateLog(key="Message-Email",type=2)
	public String sendReceiptAfReadEmail() throws Exception{
		boolean flag=false;
		SysUser sysUser=getSysUser();
		String emlid=request.getParameter("emailid");
		String recvid=request.getParameter("recvid");
		Map<String,Object> map=new HashMap<String, Object>();
		if(null==emlid || "".equals(emlid.trim())){
			map.put("msg", "未找到相关编号");
			map.put("flag", flag);	
			addJSONObject(map);
			return "success";
		}
		emlid=emlid.trim();
		if(null!=recvid && !"".equals(recvid)){
			map.put("id", recvid.trim());
		}
		map.put("emailid", emlid.trim());
		map.put("receiptflag", "1");
		map.put("recvuserid", sysUser.getUserid());
	    EmailReceive emlReceive=emailService.showEmailReceiveBy(map);
		if(null==emlReceive){
			map.clear();
			map.put("msg", "未找到相关信息");
			map.put("flag", flag);	
			addJSONObject(map);
			return "success";
		}
		map.clear();

		map.put("id", emlReceive.getId());
		map.put("receiptflag", "0");	//设置已经回收
		boolean isok=emailService.updateEmailReceiveBy(map);
		if(isok && StringUtils.isNotEmpty(emlReceive.getSenduserid())){
			map.clear();
			map.put("certaincols", "title");
			map.put("id", emlid);
			EmailContent emlContent=emailService.showEmailContentBy(map);
			if(null !=emlContent && StringUtils.isNotEmpty(emlContent.getTitle())){
				map.clear();
				map.put("mtiptype", "1");	//短信
				map.put("msgtype", "3"); //电子邮件
				map.put("senduserid",sysUser.getUserid());
				map.put("receivers", emlReceive.getSenduserid());
				map.put("tabtitle", "邮件详情查看");
				String title=sysUser.getName()+" 于"+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))+" 阅读了您发的邮件。";
				map.put("title", title+"主题："+emlContent.getTitle());
				map.put("content", title+"<br/>主题："+emlContent.getTitle());
				map.put("remindurl", "message/email/emailDetailPage.do?id="+emlid+"&showoper=1");
				addMessageReminder(map);
				flag=true;
			}
		}
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 邮件接收预览页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-18
	 */
	public String emailReceivePreviewListPage() throws Exception{
		
		String useEmailListUIType=getSysParamValue("useEmailListUIType");		
		if(null==useEmailListUIType || "".equals(useEmailListUIType.trim())){
			useEmailListUIType="0";
		}
		useEmailListUIType=useEmailListUIType.trim();
		
		String boxid=request.getParameter("boxid");
		String boxtitle="收件箱";
		if(!StringUtils.isNumeric(boxid)){
			boxid="0";
		}
		if(!"0".equals(boxid)){
			EmailBox emlBox=emailService.showEmailBox(boxid);
			if(emlBox!=null){
				if(StringUtils.isNotEmpty(emlBox.getTitle())){
					boxtitle=emlBox.getTitle();
				}
			}
		}
		request.setAttribute("boxid", boxid.trim());
		request.setAttribute("boxtitle",boxtitle);
		if("1".equals(useEmailListUIType)){
			return "success";
		}else{
			return "successPreview";
		}
	}
	/**
	 * 邮件接收分页数据 
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-7
	 */
	public String showEmailReceivePageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		SysUser sysUser=getSysUser();
		map.put("recvuserid", sysUser.getUserid());
		if(!map.containsKey("delflag")){
			map.put("delflag", "1");	//未删除数据
		}
		pageMap.setCondition(map);
		PageData pageData=emailService.showEmailReceivePageList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	
	/**
	 * 邮件已经发送预览页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-18
	 */
	public String emailSendPreviewListPage() throws Exception{
		
		String useEmailListUIType=getSysParamValue("useEmailListUIType");		
		if(null==useEmailListUIType || "".equals(useEmailListUIType.trim())){
			useEmailListUIType="0";
		}
		useEmailListUIType=useEmailListUIType.trim();
		
		if("1".equals(useEmailListUIType)){
			return "success";
		}else{
			return "successPreview";
		}
	}
	/**
	 * 邮件发送分页数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-7
	 */
	public String showEmailSendPageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		SysUser sysUser=getSysUser();
		map.put("adduserid", sysUser.getUserid());
		if(!map.containsKey("delflag")){
			map.put("delflag", "1");	//未删除数据
		}
		if(!map.containsKey("sendflag")){
			map.put("sendflag", "1");	//发送标志
		}
		pageMap.setCondition(map);
		PageData pageData=emailService.showEmailSendPageList(pageMap);
		addJSONObject(pageData);
		return "success";
	}

	/**
	 * 显示邮件草稿箱
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-16
	 */
	public String emailDraftListPage() throws Exception{
		String useEmailListUIType=getSysParamValue("useEmailListUIType");		
		if(null==useEmailListUIType || "".equals(useEmailListUIType.trim())){
			useEmailListUIType="0";
		}
		useEmailListUIType=useEmailListUIType.trim();
		
		if("1".equals(useEmailListUIType)){
			return "success";
		}else{
			return "successPreview";
		}
	}
	/**
	 * 显示邮件废弃箱
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-16
	 */
	public String emailDropListPage() throws Exception{
		String useEmailListUIType=getSysParamValue("useEmailListUIType");		
		if(null==useEmailListUIType || "".equals(useEmailListUIType.trim())){
			useEmailListUIType="0";
		}
		useEmailListUIType=useEmailListUIType.trim();
		
		if("1".equals(useEmailListUIType)){
			return "success";
		}else{
			return "successPreview";
		}	
	}

	/**
	 * 显示邮件废纸篓分页列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-1
	 */
	public String showEmailDropPageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		SysUser sysUser=getSysUser();
		map.put("adduserid", sysUser.getUserid());
		map.put("recvuserid",sysUser.getUserid());
		map.put("emlr_delflag", "2");	//收件箱删除标志
		map.put("emlc_delflag", "2");	//发件箱发送标志
		pageMap.setCondition(map);
		PageData pageData=emailService.showEmailDropPageList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 显示邮件接收箱用户阅读列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-1
	 */
	public String emailInBoxUserListPage() throws Exception{
		String emailid=request.getParameter("emailid");
		String emltitle="";
		if(StringUtils.isNotEmpty(emailid)){
			EmailContent emlc=emailService.showEmailContent(emailid);
			if(emlc!=null && StringUtils.isNotEmpty(emlc.getTitle())){
				emltitle=emlc.getTitle();
			}
		}
		request.setAttribute("emailid", emailid);
		request.setAttribute("emltitle", emltitle);
		return "success";		
	}
	/**
	 * 显示邮件发件箱用户阅读分页列表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-1
	 */
	public String emailSendBoxUserListPage() throws Exception{
		String emailid=request.getParameter("emailid");
		String emltitle="";
		if(StringUtils.isNotEmpty(emailid)){
			EmailContent emlc=emailService.showEmailContent(emailid);
			if(emlc!=null && StringUtils.isNotEmpty(emlc.getTitle())){
				emltitle=emlc.getTitle();
			}
		}
		request.setAttribute("emailid", emailid);
		request.setAttribute("emltitle", emltitle);
		return "success";
	}
	/**
	 * 显示邮件用户阅读分页列表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-1
	 */
	public String showEmailUserReadPageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		if(!map.containsKey("emailid") || StringUtils.isEmpty(map.get("emailid").toString())){
			map.put("emailid", "0");
		}else{
			Map queryMap=new HashMap();
			queryMap.put("certaincols", "receiveuser,copytouser");
			queryMap.put("id", map.get("emailid").toString());
			EmailContent emlContent=emailService.showEmailContentBy(queryMap);
			if(emlContent!=null){
				String recvuseridarrs="";
				if(StringUtils.isNotEmpty(emlContent.getReceiveuser())){
					recvuseridarrs=emlContent.getReceiveuser();
				}
				if(StringUtils.isNotEmpty(emlContent.getCopytouser())){
					recvuseridarrs=recvuseridarrs+","+emlContent.getCopytouser();
				}
				map.put("recvuseridarrs", recvuseridarrs);
			}else{
				map.put("emailid", "0");
			}
		}
		if(!map.containsKey("notdelflag")){
			map.put("notdelflag", "0");
		}
		pageMap.setCondition(map);
		PageData pageData=emailService.showEmailUserReadPageList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 显示邮件用户阅读分页列表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-1
	 */
	public String showEmailUserReadPageListBySend() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		if(!map.containsKey("emailid") || StringUtils.isEmpty(map.get("emailid").toString())){
			map.put("emailid", "0");
		}
		if(!map.containsKey("notdelflag")){
			map.put("notdelflag", "0");
		}
		pageMap.setCondition(map);
		PageData pageData=emailService.showEmailUserReadPageList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 发件箱-删除邮件信息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-22
	 */
	@UserOperateLog(key="Message-Email",type=3)
	public String deleteEmail() throws Exception{	
		String ids=request.getParameter("ids");	//邮件编号
		String dstate=request.getParameter("dstate");	//dstate ：值0时删除，值为2时进入废纸篓
		if("".equals(ids)&& !"0".equals(dstate) && !"2".equals(dstate)){
			addJSONObject("flag", false);
			return "success";
		}
		SysUser sysUser=getSysUser();
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		boolean ismuti=false;
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("idarr", ids);
		queryMap.put("adduserid", sysUser.getUserid());
		List<EmailContent> list=emailService.showEmailContentList(queryMap);
		if(list.size()==0){
			addJSONObject("flag", false);
			return "success";			
		}
		if(list.size()>0){
			ismuti=true;
		}
		queryMap.clear();
		queryMap.put("delflag", dstate);
		queryMap.put("deltime", (new Date()));
		boolean flag=false;
		for(EmailContent item : list){
			if(!"0".equals(item.getDelflag())){
				queryMap.put("id", item.getId());
				if(emailService.updateEmailContentBy(queryMap)){
					iSuccess=iSuccess+1;
				}else{
					iFailure=iFailure+1;
				}
			}else{
				iNohandle=iNohandle+1;
			}
		}

		Map<String, Object> msgMap=new HashMap<String, Object>();
		msgMap.put("flag", true);
		msgMap.put("ismuti", ismuti);
		msgMap.put("isuccess", iSuccess);
		msgMap.put("ifailure", iFailure);
		msgMap.put("inohandle", iNohandle);
		addJSONObject(msgMap);
		if(iSuccess>0){
			addLog("发件箱批量删除邮件："+ids, true);
		}else{
			addLog("发件箱批量删除邮件："+ids, false);
		}
		return "success";
	}
	/**
	 * 收件箱-删除接收邮件信息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-22
	 */
	@UserOperateLog(key="Message-Email",type=4)
	public String deleteEmailReceive() throws Exception{
		boolean flag=false;
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		boolean ismuti=false;
		String oper=request.getParameter("oper");
		String ids=request.getParameter("ids");
		String dstate=request.getParameter("dstate");
		if("".equals(ids) && ( !"0".equals(dstate) || !"2".equals(dstate))){
			addJSONObject("flag", false);
			return "success";
		}
		SysUser sysUser=getSysUser();
		Map<String,Object> queryMap=new HashMap<String,Object>();
		String[] idarr=ids.split(",");
		queryMap.put("delflag", dstate);
		queryMap.put("deltime", (new Date()));
		if(StringUtils.isNotEmpty(oper) && "1".equals(oper.trim().toLowerCase())){
			queryMap.put("wsenduserid", sysUser.getUserid()); //发送者删除
		}else{
			queryMap.put("wrecvuserid", sysUser.getUserid()); //其他为接收人删除		
		}
		if(!"2".equals(dstate)){
			queryMap.put("wnotdelfag", "0");	//已经销毁的，不可进入废纸篓
		}
		if(idarr.length>0){
			ismuti=false;
		}

		for(String item : idarr){
			if(null!=item && !"".equals(item.trim())){
				queryMap.put("id", item.trim());
				if(emailService.updateEmailReceiveBy(queryMap)){
					iSuccess=iSuccess+1;
				}else{
					iFailure=iFailure+1;
				}
			}else{
				iNohandle=iNohandle+1;
			}
		}  
		Map<String, Object> msgMap=new HashMap<String, Object>();
		msgMap.put("flag", true);
		msgMap.put("ismuti", ismuti);
		msgMap.put("isuccess", iSuccess);
		msgMap.put("ifailure", iFailure);
		msgMap.put("inohandle", iNohandle);
		addJSONObject(msgMap);

		if(iSuccess>0){
			addLog("发件箱批量删除邮件："+ids, true);
		}else{
			addLog("发件箱批量删除邮件："+ids, false);
		}
		return "success";
	}
	
	/**
	 * 收件箱-删除所有已读邮件
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-22
	 */
	@UserOperateLog(key="Message-Email",type=4)
	public String deleteReadEmailReceive() throws Exception{
		boolean flag=false;
		String boxid=request.getParameter("boxid");
		if(!StringUtils.isNumeric(boxid)){
			addJSONObject("flag", flag);
			return "success";
		}
		SysUser sysUser=getSysUser();
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("delflag", "2");
		queryMap.put("deltime", (new Date()));
		queryMap.put("wrecvuserid", sysUser.getUserid());
		queryMap.put("wviewflag", "0");
		queryMap.put("wboxid", boxid);
		flag = emailService.updateEmailReceiveBy(queryMap);
		addJSONObject("flag", flag);

		addLog("收件箱-删除所有已读邮件", flag);
		return "success";
	}

	/**
	 * 发送人，收回邮件
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-22
	 */
	@UserOperateLog(key="Message-Email",type=4)
	public String deleteEmailReceiveBySend() throws Exception{
		boolean flag=false;
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		boolean ismuti=false;
		String emlids=request.getParameter("emlids");
		if(StringUtils.isEmpty(emlids)){
			addJSONObject("flag", false);
			return "success";	
		}
		String oper=request.getParameter("opertype");
		if(StringUtils.isEmpty(oper)|| !("1".equals(oper) )){
			addJSONObject("flag", false);
			return "success";	
		}
		String[] idarr=emlids.split(",");		
		Map<String,Object> queryMap=new HashMap<String,Object>();
		SysUser sysUser=getSysUser();
		if(idarr.length>0){
			ismuti=true;
		}
		if("1".equals(oper)){
			//收回收件人未读邮件
			queryMap.put("wviewflag", "1");
		}else if("2".equals(oper)){
			//收回收件人已读邮件
			queryMap.put("wviewflag", "0");
		}
		queryMap.put("delflag", "0");
		queryMap.put("deltime", (new Date()));
		queryMap.put("wsenduserid", sysUser.getUserid());

		for(String item : idarr){
			if(null!=item && !"".equals(item.trim())){
				queryMap.put("wemailid", item.trim());
				if(emailService.updateEmailReceiveBy(queryMap)){
					iSuccess=iSuccess+1;
				}else{
					iFailure=iFailure+1;
				}
			}else{
				iNohandle=iNohandle+1;
			}
		}
		Map<String, Object> msgMap=new HashMap<String, Object>();
		msgMap.put("flag", true);
		msgMap.put("ismuti", ismuti);
		msgMap.put("isuccess", iSuccess);
		msgMap.put("ifailure", iFailure);
		msgMap.put("inohandle", iNohandle);
		addJSONObject(msgMap);

		if(iSuccess>0){
			addLog("发送人，收回邮件："+emlids, true);
		}else{
			addLog("发送人，收回邮件："+emlids, false);
		}
		return "success";
	}

	/**
	 * 收件箱-标志选中的邮件为阅读
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-22
	 */
	public String readEmailReceive() throws Exception{
		boolean flag=false;
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		boolean ismuti=false;
		String ids=request.getParameter("ids");
		if(ids==null || "".equals(ids)){
			addJSONObject("flag", false);
			return "success";
		}
		String unread=request.getParameter("unreadflag");	//未阅读
		SysUser sysUser=getSysUser();
		Map<String,Object> queryMap=new HashMap<String,Object>();
		String[] idarr=ids.split(",");
		queryMap.put("viewflag", "0");
		queryMap.put("viewtime", (new Date()));
		queryMap.put("wrecvuserid", sysUser.getUserid());
		if(null!=unread && "1".equals(unread)){
			queryMap.put("wnotviewflag", "0");
		}
		if(idarr.length>0){
			ismuti=true;
		}

		for(String item : idarr){
			if(item!=null && !"".equals(item.trim())){
				queryMap.put("id", item.trim());
				if(emailService.updateEmailReceiveBy(queryMap)){
					iSuccess=iSuccess+1;
				}else{
					iFailure=iFailure+1;
				}
			}else{
				iNohandle=iNohandle+1;
			}
		}
		Map<String, Object> msgMap=new HashMap<String, Object>();
		msgMap.put("flag", true);
		msgMap.put("ismuti", ismuti);
		msgMap.put("isuccess", iSuccess);
		msgMap.put("ifailure", iFailure);
		msgMap.put("inohandle", iNohandle);
		addJSONObject(msgMap);
		return "success";
	}
	/**
	 * 收件箱-标志选中的邮件为阅读
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-22
	 */
	public String readEmailReceiveByEmailid() throws Exception{
		boolean flag=false;
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		boolean ismuti=false;
		String ids=request.getParameter("emailids");
		if(ids==null || "".equals(ids.trim())){
			addJSONObject("flag", false);
			return "success";
		}
		SysUser sysUser=getSysUser();
		Map<String,Object> queryMap=new HashMap<String,Object>();
		String[] idarr=ids.split(",");
		queryMap.put("viewflag", "0");
		queryMap.put("viewtime", (new Date()));
		queryMap.put("wrecvuserid", sysUser.getUserid());
		queryMap.put("wnotviewflag", "0");
		
		if(idarr.length>0){
			ismuti=true;
		}


		for(String item : idarr){
			if(item!=null && !"".equals(item.trim())){
				queryMap.put("wemailid", item.trim());
				if(emailService.updateEmailReceiveBy(queryMap)){
					iSuccess=iSuccess+1;
				}else{
					iFailure=iFailure+1;
				}
			}else{
				iNohandle=iNohandle+1;
			}
		}
		Map<String, Object> msgMap=new HashMap<String, Object>();
		msgMap.put("flag", true);
		msgMap.put("ismuti", ismuti);
		msgMap.put("isuccess", iSuccess);
		msgMap.put("ifailure", iFailure);
		msgMap.put("inohandle", iNohandle);
		addJSONObject(msgMap);
		return "success";
	}
	/**
	 * 收件箱-标志所有已读邮件
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-22
	 */
	public String readAllEmailReceive() throws Exception{
		boolean flag=false;
		SysUser sysUser=getSysUser();
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("viewflag", "0");
		queryMap.put("viewtime", (new Date()));
		queryMap.put("wrecvuserid", sysUser.getUserid());
		queryMap.put("wviewflag", "1");
		flag= emailService.updateEmailReceiveBy(queryMap);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 废纸篓-清空邮件
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-23
	 */
	@UserOperateLog(key="Message-Email",type=4)
	public String deleteDropEmail() throws Exception{
		boolean flag=false;
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		boolean ismuti=false;
		String emlcids=request.getParameter("emlcids");
		String emlrids=request.getParameter("emlrids");
		if(StringUtils.isEmpty(emlcids) && StringUtils.isEmpty(emlrids)){
			addJSONObject("flag", false);
			return "success";
		}
		SysUser sysUser=getSysUser();
		Map queryMap=new HashMap();
		//删除邮件内容
		String[] idarr=null;
		if(StringUtils.isNotEmpty(emlcids)){
			idarr=emlcids.split(",");
			if(idarr.length>0){
				ismuti=true;
				queryMap.put("delflag", "0");
				queryMap.put("deltime", (new Date()));
				queryMap.put("wdelflag", "2");
				queryMap.put("adduserid", sysUser.getUserid());
				for(String id : idarr){
					if(StringUtils.isNotEmpty(id)){
						queryMap.put("id", id.trim());
						if(emailService.updateEmailContentBy(queryMap)){
							iSuccess=iSuccess+1;
						}else{
							iFailure=iFailure+1;
						}
					}else{
						iNohandle=iNohandle+1;
					}
				}
			}
		}
		queryMap.clear();
		//删除接收人
		if(StringUtils.isNotEmpty(emlrids)){
			idarr=emlrids.split(",");
			if(idarr.length>0){
				ismuti=true;
				queryMap.put("delflag", "0");
				queryMap.put("deltime", (new Date()));
				queryMap.put("wdelflag", "2");
				queryMap.put("recvuserid", sysUser.getUserid());
				for(String id : idarr){
					if(StringUtils.isNotEmpty(id)){
						queryMap.put("id", id.trim());
						if(emailService.updateEmailReceiveBy(queryMap)){
							iSuccess=iSuccess+1;
						}else{
							iFailure=iFailure+1;
						}
					}else{
						iNohandle=iNohandle+1;
					}
				}
			}
		}
		Map<String, Object> msgMap=new HashMap<String, Object>();
		msgMap.put("flag", true);
		msgMap.put("ismuti", ismuti);
		msgMap.put("isuccess", iSuccess);
		msgMap.put("ifailure", iFailure);
		msgMap.put("inohandle", iNohandle);
		addJSONObject(msgMap);

		String logids="";
		if(StringUtils.isNotEmpty(emlcids)){
			logids=emlcids;
		}
		if(StringUtils.isNotEmpty(emlrids)){
			logids=logids+emlrids;
		}
		if(iSuccess>0){
			addLog("废纸篓-清空邮件："+logids, true);
		}else{
			addLog("废纸篓-清空邮件："+logids, false);
		}
		return "success";
	}


	/**
	 * 废纸篓-清空邮件
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-23
	 */
	@UserOperateLog(key="Message-Email",type=4)
	public String deleteAllDropEmail() throws Exception{
		boolean flag=false;
		String logmsg="";
		SysUser sysUser=getSysUser();
		Map queryMap=new HashMap();
		//清空发件箱
		queryMap.put("delflag", "0");
		queryMap.put("deltime", (new Date()));
		queryMap.put("wdelflag", "2");
		queryMap.put("adduserid", sysUser.getUserid());
		flag=emailService.updateEmailContentBy(queryMap);
		logmsg="废纸篓-清空发件箱";
		if(flag){
			logmsg=logmsg+"成功";
		}else{
			logmsg=logmsg+"失败";
		}
		//清空收件箱
		queryMap.clear();
		queryMap.put("delflag", "0");
		queryMap.put("deltime", (new Date()));
		queryMap.put("wdelflag", "2");
		queryMap.put("recvuserid", sysUser.getUserid());
		flag= emailService.updateEmailReceiveBy(queryMap) ;
		logmsg="废纸篓-清空收件箱";
		if(flag){
			logmsg=logmsg+"成功";
		}else{
			logmsg=logmsg+"失败";
		}
		addJSONObject("flag", flag);

		addLog(logmsg);
		
		return "success";
	}
	/**
	 * 废纸篓-中恢复邮件信息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-22
	 */
	@UserOperateLog(key="Message-Email",type=3)
	public String recoverDropEmail() throws Exception{
		boolean flag=false;
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		boolean ismuti=false;
		String emlcids=request.getParameter("emlcids");
		String emlrids=request.getParameter("emlrids");
		if(StringUtils.isEmpty(emlcids) && StringUtils.isEmpty(emlrids)){
			addJSONObject("flag", false);
			return "success";
		}
		SysUser sysUser=getSysUser();
		Map queryMap=new HashMap();
		//恢复邮件内容
		String[] idarr=null;
		if(StringUtils.isNotEmpty(emlcids)){
			idarr=emlcids.split(",");
			if(idarr.length>0){
				ismuti=true;
				queryMap.put("delflag", "1");
				queryMap.put("deltime", (new Date()));
				queryMap.put("wdelflag", "2");
				queryMap.put("adduserid", sysUser.getUserid());
				for(String id : idarr){
					if(StringUtils.isNotEmpty(id)){
						queryMap.put("id", id.trim());
						if(emailService.updateEmailContentBy(queryMap)){
							iSuccess=iSuccess+1;
						}else{
							iFailure=iFailure+1;
						}
					}else{
						iNohandle=iNohandle+1;
					}
				}
			}
		}
		queryMap.clear();
		//恢复接收人
		if(StringUtils.isNotEmpty(emlrids)){
			idarr=emlrids.split(",");
			if(idarr.length>0){
				ismuti=true;
				queryMap.put("delflag", "1");
				queryMap.put("deltime", (new Date()));
				queryMap.put("wdelflag", "2");
				queryMap.put("recvuserid", sysUser.getUserid());
				for(String id : idarr){
					if(StringUtils.isNotEmpty(id)){
						queryMap.put("id", id.trim());
						if(emailService.updateEmailReceiveBy(queryMap)){
							iSuccess=iSuccess+1;
						}else{
							iFailure=iFailure+1;
						}
					}else{
						iNohandle=iNohandle+1;
					}
				}
			}
		}
		Map<String, Object> msgMap=new HashMap<String, Object>();
		msgMap.put("flag", true);
		msgMap.put("ismuti", ismuti);
		msgMap.put("isuccess", iSuccess);
		msgMap.put("ifailure", iFailure);
		msgMap.put("inohandle", iNohandle);
		addJSONObject(msgMap);
		return "success";
	}
	/*
	 * --------------------------------------------
	 * 邮件信箱
	 * --------------------------------------------
	 */
	/**
	 * 显示其他自定义邮箱
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-16
	 */
	public String showEmailBoxList() throws Exception{
		SysUser sysUser=getSysUser();
		List list=emailService.showEmailBoxList(sysUser.getUserid());
		addJSONArray(list);
		return "success";
	}
	/**
	 * 显示自定义邮箱分页列表页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-16
	 */
	public String emailBoxListPage() throws Exception{
		return "success";
	}
	/**
	 * 自定义邮箱分页数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-16
	 */
	public String showEmailBoxPageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		if(map.containsKey("showmailpasscol")){
			map.remove("showmailpasscol");
		}
		SysUser sysUser=getSysUser();
		map.put("adduserid", sysUser.getUserid());
		pageMap.setCondition(map);
		PageData pageData=emailService.showEmailBoxPageList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 自定义邮箱添加页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-16
	 */
	public String showEmailBoxAddPage() throws Exception{
		return "success";		
	}
	/**
	 * 添加自定义邮箱
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-16
	 */
	public String addEmailBox() throws Exception{
		boolean flag=false;
		SysUser sysUser=getSysUser();
		emailBox.setAddtime(new Date());
		emailBox.setAdduserid(sysUser.getUserid());
		flag=emailService.addEmailBox(emailBox);
		addJSONObject("flag", flag);
		return "success";		
	}
	/**
	 * 自定义邮箱编辑页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-16
	 */
	public String showEmailBoxEditPage() throws Exception{
		String id=request.getParameter("id");
		EmailBox emlBox=emailService.showEmailBox(id);
		request.setAttribute("emailBox", emlBox);
		return "success";		
	}
	/**
	 * 添加自定义邮箱
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-16
	 */
	@UserOperateLog(key="Message-Email",type=3)
	public String editEmailBox() throws Exception{
		boolean flag=emailService.updateEmailBox(emailBox);
		addJSONObject("flag", flag);
		return "success";		
	}
	/**
	 * 删除自定义邮箱
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-16
	 */
	@UserOperateLog(key="Message-Email",type=4)
	public String deleteEmailBox() throws Exception{
		String id=request.getParameter("id");
		SysUser sysUser =getSysUser();
		boolean flag=emailService.deleteEmailBox(id,sysUser.getUserid());
		addJSONObject("flag", flag);
		return "success";		
	}
	/**
	 * 删除自定义邮箱
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-16
	 */
	@UserOperateLog(key="Message-Email",type=4)
	public String moveToEmailBox() throws Exception{
		boolean flag=false;
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		boolean ismuti=false;
		Map<String,Object> map=new HashMap<String,Object>();
		String ids=request.getParameter("ids");
		String boxid=request.getParameter("boxid");
		if("".equals(ids) || "".equals(boxid)){
			map.put("msg", "抱歉，未知参数");
			map.put("flag", flag);
			addJSONObject(map);
			return "success";			
		}
		map.clear();
		String[] idarr=ids.split(",");
		SysUser sysUser=getSysUser();
		map.put("boxid", boxid.trim());
		map.put("wrecvuserid", sysUser.getUserid());
		for(String item : idarr){
			if(!"".equals(item.trim())){
				map.put("id", item.trim());
				if(emailService.updateEmailReceiveBy(map)){
					iSuccess=iSuccess+1;
				}else{
					iFailure=iFailure+1;
				}
			}else{
				iNohandle=iNohandle+1;
			}
		}
		Map<String, Object> msgMap=new HashMap<String, Object>();
		msgMap.put("flag", true);
		msgMap.put("ismuti", ismuti);
		msgMap.put("isuccess", iSuccess);
		msgMap.put("ifailure", iFailure);
		msgMap.put("inohandle", iNohandle);
		addJSONObject(msgMap);
		return "success";				
	}
	
	/*
	 * --------------------------------------------
	 * 当前用户外部邮件配置
	 * --------------------------------------------
	 */
	/**
	 * 当前用户外部邮件配置
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-5
	 */
	public String showCurUserWebMailConfigList() throws Exception {
		Map<String,Object> queryMap=new HashMap<String,Object>();
		SysUser sysUser=getSysUser();
		queryMap.put("adduserid", sysUser.getUserid());
		queryMap.put("orderbysql", "isdefault desc,id desc");
		List list=emailService.showWebMailConfigList(queryMap);
		addJSONArray(list);
		return "success";
	}

	/**
	 * 外部邮件配置添加页面
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-6
	 */
	public String showWebMailConfigAddPage() throws Exception{
		return "success";
	}
	/**
	 * 外部邮件配置添加
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-6
	 */
	public String addWebMailConfig() throws Exception {
		SysUser sysUser=getSysUser();
		webMailConfig.setAdduserid(sysUser.getUserid());
		boolean flag=emailService.addWebMailConfig(webMailConfig);
		addJSONObject("flag", flag);
		return "success";		
	}
	/**
	 * 外部邮件配置编辑页面
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-6
	 */
	public String showWebMailConfigEditPage() throws Exception{
		String id=request.getParameter("id");
		WebMailConfig webMailConfig=emailService.showWebMailConfig(id);
		request.setAttribute("webMailConfig", webMailConfig);
		return "success";
	}
	/**
	 * 外部邮件配置编辑保存
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-6
	 */
	public String editWebMailConfig() throws Exception {
		boolean flag=emailService.updateWebMailConfig(webMailConfig);
		addJSONObject("flag", flag);
		return "success";		
	}

	/**
	 * 外部邮件配置分页页面
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-6
	 */
	public String webMailConfigListPage() throws Exception{
		return "success";
	}
	/**
	 * 外部邮件配置分页数据
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-6
	 */
	public String showWebMailConfigPageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		if(map.containsKey("showmailpasscol")){
			map.remove("showmailpasscol");
		}
		SysUser sysUser=getSysUser();
		map.put("adduserid", sysUser.getUserid());
		pageMap.setCondition(map);
		PageData pageData=emailService.showWebMailConfigPageList(pageMap);
		addJSONObject(pageData);
		return "success";		
	}

	/**
	 * 外部邮件配置删除
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-6
	 */
	@UserOperateLog(key="Message-Email-WebMailConfig",type=4)
	public String deleteWebMailConfig() throws Exception{
		String id=request.getParameter("id");
		Map<String,Object> msgMap=new HashMap<String,Object>();
		boolean flag=false;
		if("".equals(id) || "".equals(id.trim()) ){
			
			msgMap.put("msg", "抱歉，未能找到要删除的配置信息");
			msgMap.put("flag", flag);	
			addJSONObject(msgMap);
			return "success";	
		}
		flag=emailService.deleteWebMailConfig(id.trim());
		addJSONObject("flag", flag);
		return "success";
	}
	

	/**
	 * 将cookie封装到Map里面
	 * @param request
	 * @return
	 */
	private Map<String,Cookie> getCookieMap(){  
	    Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
	    Cookie[] cookies = request.getCookies();
	    if(null!=cookies){
	        for(Cookie cookie : cookies){
	            cookieMap.put(cookie.getName(), cookie);
	        }
	    }
	    return cookieMap;
	}
	/**
	 * 首页面邮件接收列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-11-7
	 */
	public String emailReceiveIndexListPage() throws Exception{
		Map queryMap=CommonUtils.changeMap(request.getParameterMap());
		SysUser sysUser=getSysUser();
		queryMap.put("recvuserid", sysUser.getUserid());
		if(!queryMap.containsKey("delflag")){
			queryMap.put("delflag", "1");	//未删除数据
		}
		if(!queryMap.containsKey("rows")){
			queryMap.put("rows", 10);
		}
		if(!queryMap.containsKey("viewflag")){
			queryMap.put("viewflag", "1");//未阅读
		}
		if(!queryMap.containsKey("order") && ! queryMap.containsKey("sort")){
			queryMap.put("sort","emlc_id desc");
			queryMap.put("order", ",recvtime desc");
		}
		pageMap.setCondition(queryMap);
		PageData pageData=emailService.showEmailReceivePageList(pageMap);
		List<EmailReceive> list=new ArrayList<EmailReceive>();
		if(pageData!=null && pageData.getList()!=null){
			list=pageData.getList();
		}
		request.setAttribute("dataList", list);
		return SUCCESS;
	}
}

