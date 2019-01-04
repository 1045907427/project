/**
 * @(#)InnerMessageAction.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-21 zhanghonghui 创建版本
 */
package com.hd.agent.message.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.activiti.service.IWorkService;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.message.model.MsgContent;
import com.hd.agent.message.model.MsgReceive;
import com.hd.agent.message.service.IEmailService;
import com.hd.agent.message.service.IInnerMessageService;
import com.hd.agent.message.service.INoticeService;
import com.hd.agent.system.service.ITaskScheduleService;

/**
 * 内部消息Action
 * 
 * @author zhanghonghui
 */
public class InnerMessageAction extends BaseAction {
	
	private ITaskScheduleService taskScheduleService;
	
	/**
	 * 内部消息服务
	 */
	private IInnerMessageService innerMessageService;
	
	public IInnerMessageService getInnerMessageService() {
		return innerMessageService;
	}
	public void setInnerMessageService(IInnerMessageService innerMessageService) {
		this.innerMessageService = innerMessageService;
	}

	public ITaskScheduleService getTaskScheduleService() {
		return taskScheduleService;
	}
	public void setTaskScheduleService(ITaskScheduleService taskScheduleService) {
		this.taskScheduleService = taskScheduleService;
	}

	/**
	 * 短信内容
	 */
	private MsgContent msgContent;
	/**
	 * 短信接收人
	 */
	private MsgReceive msgReceive;

	public MsgContent getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(MsgContent msgContent) {
		this.msgContent = msgContent;
	}
	public MsgReceive getMsgReceive() {
		return msgReceive;
	}
	public void setMsgReceive(MsgReceive msgReceive) {
		this.msgReceive = msgReceive;
	}
	/**
	 * 内部消息显示页面
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-21
	 */
	public String messageListPage(){
		return "success";
	}
	/**
	 * 内部消息发送页面
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-22
	 */
	public String messageSendPage() throws Exception{
		String msgSendPageId=request.getParameter("msgsendpageid");
		request.setAttribute("msgSendPageId", msgSendPageId);
		String touserids=request.getParameter("touserids");
		if(StringUtils.isNotEmpty(touserids)){
			request.setAttribute("touserids", touserids);
		}
		return "success";
	}
	/**
	 * 内部消息回复页面
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-22
	 */
	public String messageReplyPage() throws Exception{
		String msgSendPageId=request.getParameter("msgsendpageid");
		request.setAttribute("msgSendPageId", msgSendPageId);
		String touserids=request.getParameter("touserids");
		if(StringUtils.isNotEmpty(touserids)){
			request.setAttribute("touserids", touserids);
		}
		request.setAttribute("msgtype", "reply");
		return "success";
	}
	/**
	 * 内部消息发送
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-22
	 */
	@UserOperateLog(key="Message-InnerMessage",type=2)
	public String messageSend()  throws Exception{
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
		if(StringUtils.isNotEmpty(msgContent.getClocktime())){
			if(!CommonUtils.isDateTimeStandStr(msgContent.getClocktime())){
				msgMap.put("flag", flag);
				msgMap.put("msg", "抱歉，您输入的发送时间格式不正确");
				
				addJSONObject(msgMap);
				return "success";	
			}else{
				msgContent.setClocktype("2");
			}
		}else{
			msgContent.setClocktype("1");
		}
		SysUser sysUser=getSysUser();
		msgContent.setAdduserid(sysUser.getUserid());
		msgContent.setMsgtype("1");	//消息类型为内部短信
		msgContent.setUrl(""); //内部消息详细地址为空
		
		flag=innerMessageService.addSendMessage(msgContent);
		if(flag 
				&& "2".equals(msgContent.getClocktype()) 
				&& CommonUtils.isDateTimeStandStr(msgContent.getClocktime()) ){
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date clockDate=sf.parse(msgContent.getClocktime());
				Date now=new Date();
				if(clockDate.compareTo(now)<=0){
					msgContent.setClocktype("1");
					innerMessageService.addDelaySendMessage(msgContent);
				}else{
					String con = CommonUtils.getQuartzCronExpression(clockDate);
					
					Map<String,Object> dataMap = new HashMap<String,Object>();
					dataMap.put("id",msgContent.getId());
					taskScheduleService.addTaskScheduleAndStart( String.valueOf(msgContent.getId())  ,"内部短信延时发送(按单次计划)", "com.hd.agent.message.job.InnerMessageOneDelaySendJob", "InnerMessageOneDelaySend", con, "1",dataMap);
				}
		}
		//返回json字符串
		addJSONObject("flag",flag);

		if(flag){
			logStr="添加内部短信成功";
		}else{
			logStr="添加内部短信失败";
		}
		return "success";
	}
	/**
	 * 内部消息详细内容
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-5
	 */
	public String messageDetailPage() throws Exception{
		String id=request.getParameter("id");
		MsgContent msgContent=innerMessageService.showMsgContent(id);
		if(StringUtils.isNotEmpty(msgContent.getAdduserid())){
			if("system".equals(msgContent.getAdduserid())){
				msgContent.setAddusername("系统");
			}else{
				String userlist[]={msgContent.getAdduserid()};
				Map map=new HashMap();
				map.put("userList", userlist);
				pageMap.setCondition(map);
				PageData pageData=getSysUserListByConditon(pageMap,false);
				List<SysUser> uList=pageData.getList();
				if(uList!=null && uList.size()>0){
					SysUser addUser=uList.get(0);
					msgContent.setAddusername(addUser.getName());
				}
			}
		}
		String remindurl=msgContent.getUrl();
		int ismsgphoneurlshow=remindurl.indexOf("ismsgphoneurlshow=0"); 
		if(ismsgphoneurlshow==-1){
			msgContent.setIsmsgphoneurlshow("1");
		}
		else{
			msgContent.setIsmsgphoneurlshow("0");
		}
		request.setAttribute("msgContent", msgContent);
		request.setAttribute("userviewtype", "recv");
		return "success";
	}
	/**
	 * 内部消息发送查看详细内容
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-5
	 */
	public String messageSendDetailPage() throws Exception{
		String id=request.getParameter("id");
		MsgContent msgContent=innerMessageService.showMsgContent(id);
		if(null!=msgContent){
			if("system".equals(msgContent.getAdduserid())){
				msgContent.setAddusername("系统");
			}
			else if(StringUtils.isNotEmpty(msgContent.getAdduserid())){
				String userlist[]={msgContent.getAdduserid()};
				Map map=new HashMap();
				map.put("userList", userlist);
				pageMap.setCondition(map);
				PageData pageData=getSysUserListByConditon(pageMap,false);
				List<SysUser> uList=pageData.getList();
				if(uList!=null && uList.size()>0){
					SysUser addUser=uList.get(0);
					msgContent.setAddusername(addUser.getName());
				}
			}
		}
		request.setAttribute("msgContent", msgContent);
		request.setAttribute("userviewtype", "send");
		return "success";
	} 
	/**
	 * 内部消息发送页面
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-21
	 */
	public String messageSendListPage() throws Exception{
		logStr="内部短信已发送页面";
		return "success";
	}
	/**
	 * 内部消息已发送分页数据
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-21
	 */
	public String showMessageSendPageList() throws Exception{
		SysUser sysUser=getSysUser();
		Map map=new HashMap();
		map.put("delflag", "1"); // 1表示未删除，默认不显示已经删除的发送内容
		//map.put("msgr_delflag", "1"); //1表示未删除，默认不显示已经的接收人列表
		//map.put("adduserid", sysUser.getUserid());
		
		// 获取页面传过来的参数 封装到map里面
		Map queryMap = CommonUtils.changeMap(request.getParameterMap());
		
		map.putAll(queryMap);
		//别名
		//setQueryRulesWithAlias("msgc");
		// map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData=innerMessageService.showMsgContentPageList(pageMap);
		addJSONObject(pageData);
		logStr="内部消息已发送分页数据";
		return "success";
	}
	/**
	 * 删除发送的内部消息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-30
	 */
	@UserOperateLog(key="Message-InnerMessage",type=4)
	public String deleteMessageSend() throws Exception{
		boolean flag=false;
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		boolean ismuti=false;
		Map<String,Object> map=new HashMap<String,Object>();
		String ids=request.getParameter("ids");
		if(ids==null || "".equals(ids.trim())){
			map.put("flag", flag);
			map.put("msg", "未能找到要删除的内部消息");
			addJSONObject(map);
			return "success";
		}
		String[] idarr=ids.trim().split(",");
		if(idarr.length==0){
			map.put("flag", flag);
			map.put("msg", "未能找到要删除的内部消息");
			addJSONObject(map);
			return "success";			
		}
		if(idarr.length>0){
			ismuti=true;
		}
		map.clear();
		//SysUser sysUser=getSysUser();
		//map.put("wadduserid", sysUser.getUserid());
		map.put("delflag", "0");
		map.put("deltime", (new Date()));
		for(String item : idarr){
			if(item!=null && !"".equals(item.trim())){
				map.put("id", item.trim());
				if(innerMessageService.updateMsgContentBy(map)){
					iSuccess=iSuccess+1;
				}else{
					iFailure=iFailure+1;
				}
			}else{
				iNohandle=iNohandle+1;
			}
		}
		logStr="发送的内部消息更新删除标志";
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
	 * 显示接收人列表
	 * @return
	 * @throws Exception
	 */
	public String messageReceiveUserListPage() throws Exception{
		String id=request.getParameter("msgid");
		MsgContent msgContent=innerMessageService.showMsgContent(id);
		request.setAttribute("msgContent", msgContent);
		return "success";
	}

	/**
	 * 已经接收短信页面弹出消息 接收人页面
	 * @return
	 * @throws Exception
	 * @date
	 * @modify 2015-07-28 修改定时发送的，才需要解析消息内容里的接收人
	 */
	public String showMessageReceiveUserPageList() throws Exception {
		Map queryMap = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(queryMap);
		PageData pageData=innerMessageService.showMessageReceiveUserPageList(pageMap);
		addJSONObject(pageData);
		logStr="已经接收短信页面弹出消息 接收人页面";
		return SUCCESS;
	}
	/**
	 * 内部消息接收页面
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-21
	 */
	public String messageReceiveListPage() throws Exception{
		logStr="内部短信已接收页面";
		return "success";		
	}

	/**
	 * 内部消息接收分页数据
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-21
	 */
	public String showMessageReceivePageList() throws Exception{
		SysUser sysUser=getSysUser();
		Map map=new HashMap();
		map.put("delflag", "1"); // 1表示未删除，默认不显示已经删除的发送内容
		map.put("recvuserid", sysUser.getUserid());
		
		Map queryMap=CommonUtils.changeMap(request.getParameterMap());
		map.putAll(queryMap);
		
		pageMap.setCondition(map);
		//setQueryRulesWithAlias("msgr");
		PageData pageData=innerMessageService.getMsgReceivePageList(pageMap);
		addJSONObject(pageData);
		logStr="内部消息接收分页数据";
		return "success";
	}


	/**
	 * 内部消息阅读页面
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-11
	 */
	public String messageReceiveReadList() throws Exception{
		String msgwindowpageid=request.getParameter("msgwindowpageid");
		if(null==msgwindowpageid || "".equals(msgwindowpageid.trim()) ){
			msgwindowpageid="";
		}
		SysUser sysUser=getSysUser();
		Map map=new HashMap();
		map.put("delflag", "1"); // 1表示未删除，默认不显示已经删除的发送内容
		map.put("viewflag", "1");
		map.put("recvuserid", sysUser.getUserid());
		Map queryMap=CommonUtils.changeMap(request.getParameterMap());
		map.putAll(queryMap);
		
		map.put("isPageLimit", false);
		if( (!map.containsKey("sort") || StringUtils.isEmpty(map.get("sort").toString()))  
				&& ( !map.containsKey("order") || StringUtils.isEmpty(map.get("order").toString()) )){
			map.put("sort", "sendtime");
			map.put("order", "desc");
		}
		
		pageMap.setCondition(map);
		
		if(pageMap.getRows()>0){
			map.put("isPageLimit", true);
		}
		
		PageData pageData=innerMessageService.getMsgReceivePageList(pageMap);
		List<MsgReceive> list=new ArrayList<MsgReceive>();
		int total=0;
		if(pageData!=null && pageData.getList()!=null){
			list=pageData.getList();
			total=pageData.getTotal();
		}
		request.setAttribute("total",  total);
		request.setAttribute("msgList", list);
		request.setAttribute("msgwindowpageid", msgwindowpageid);
		return "success";		
	}
	/**
	 * 内部消息阅读首页页面
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-11
	 */
	public String messageReceiveIndexListPage() throws Exception{
		String msgwindowpageid=request.getParameter("msgwindowpageid");
		if(null==msgwindowpageid || "".equals(msgwindowpageid.trim()) ){
			msgwindowpageid="";
		}
		SysUser sysUser=getSysUser();
		Map map=new HashMap();
		map.put("delflag", "1"); // 1表示未删除，默认不显示已经删除的发送内容
		if(!map.containsKey("viewflag")){
			map.put("viewflag", "1");	//未阅读
		}
		map.put("recvuserid", sysUser.getUserid());
		Map queryMap=CommonUtils.changeMap(request.getParameterMap());
		map.putAll(queryMap);
		
		map.put("isPageLimit", true);
		if(!map.containsKey("rows")){
			map.put("rows", 10);
		}
		if( (!map.containsKey("sort") || StringUtils.isEmpty(map.get("sort").toString()))  
				&& ( !map.containsKey("order") || StringUtils.isEmpty(map.get("order").toString()) )){
			map.put("sort", "sendtime");
			map.put("order", "desc");
		}
		
		pageMap.setCondition(map);
		PageData pageData=innerMessageService.getMsgReceivePageList(pageMap);
		List<MsgReceive> list=new ArrayList<MsgReceive>();
		int total=0;
		if(pageData!=null && pageData.getList()!=null){
			list=pageData.getList();
			total=pageData.getTotal();
		}
		request.setAttribute("total",  total);
		request.setAttribute("msgList", list);
		return "success";		
	}
	/**
	 * 有无未阅读短信
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-11
	 */
	public String showMessageRemindCount() throws Exception{
		SysUser sysUser=getSysUser();
		Map map=new HashMap();
		map.put("recvflag", "1");	//未接收
		map.put("delflag", "1");	//未删除
		map.put("viewflag", "1");	//未阅读
		map.put("recvuserid", sysUser.getUserid());
		pageMap.setCondition(map);
		int irows=innerMessageService.getMsgReceiveCount(pageMap);
		addJSONObject("reminds", irows);
		return "success";		
	}
	/**
	 * 设置已经提醒
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-11
	 */
	public String setMessageRemindFlag() throws Exception{
		SysUser sysUser=getSysUser();
		Map map=new HashMap();
		map.put("recvflag", "0");
		map.put("recvtime", (new Date()));
		map.put("wrecvuserid", sysUser.getUserid());
		boolean flag=innerMessageService.updateMsgReceiveBy(map);
		addJSONObject("isok",flag);
		return "success";
	}
	
	/**
	 * 删除接收的内部消息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-30
	 */
	@UserOperateLog(key="Message-InnerMessage",type=4)
	public String deleteMessageReceive() throws Exception{
		boolean flag=false;
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		boolean ismuti=false;
		Map<String,Object> map=new HashMap<String,Object>();
		String ids=request.getParameter("ids");
		if("".equals(ids)){
			map.put("flag", flag);
			map.put("msg", "未能找到要删除的内部消息");
			addJSONObject(map);
			return "success";
		}
		String[] idarr=ids.split(",");
		if(idarr.length==0){
			map.put("flag", flag);
			map.put("msg", "未能找到要删除的内部消息");
			addJSONObject(map);
			return "success";			
		}
		if(idarr.length>0){
			ismuti=true;
		}
		map.clear();
		SysUser sysUser=getSysUser();
		map.put("wrecvuserid", sysUser.getUserid());
		map.put("delflag","0");
		map.put("deltime",(new Date()));

		for(String item : idarr){
			if(item!=null && !"".equals(item.trim())){
				map.put("id", item.trim());
				if(innerMessageService.updateMsgReceiveBy(map)){
					iSuccess=iSuccess+1;
				}else{
					iFailure=iFailure+1;
				}
			}else{
				iNohandle=iNohandle+1;
			}
		}
		logStr="接收的内部消息更新删除标志";		
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
	 * 设置已经阅读
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-30
	 */
	@UserOperateLog(key="Message-InnerMessage",type=3)
	public String setMessageReceiveReadFlag() throws Exception{
		boolean flag=false;
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		boolean ismuti=false;
		Map<String,Object> map=new HashMap<String,Object>();
		String ids=request.getParameter("ids");
		if("".equals(ids)){
			map.put("flag", flag);
			map.put("msg", "未能找到要标记阅读的内部消息");
			addJSONObject(map);
			return "success";
		}
		String[] idarr=ids.split(",");
		if(idarr.length==0){
			map.put("flag", flag);
			map.put("msg", "未能找到要标记阅读的内部消息");
			addJSONObject(map);
			return "success";			
		}
		if(idarr.length>0){
			ismuti=true;
		}
		map.clear();
		SysUser sysUser=getSysUser();
		Date date=new Date();
		map.put("wrecvuserid", sysUser.getUserid());
		map.put("viewflag","0");
		map.put("viewtime",date);
		map.put("recvflag","0");
		map.put("recvtime", date);
	
		for(String item : idarr){
			if(item!=null && !"".equals(item.trim())){
				map.put("id", item.trim());
				if(innerMessageService.updateMsgReceiveBy(map)){
					iSuccess=iSuccess+1;
				}else{
					iFailure=iFailure+1;
				}
			}else{
				iNohandle=iNohandle+1;
			}
		}
		logStr="更新接收的内部消息阅读标志";
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
	 * 根据消息编号，设置已经阅读
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-30
	 */
	@UserOperateLog(key="Message-InnerMessage",type=3)
	public String messageReceiveReadByMsgid() throws Exception{
		boolean flag=false;
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		boolean ismuti=false;
		Map<String,Object> map=new HashMap<String,Object>();
		String msgids=request.getParameter("msgids");
		if("".equals(msgids)){
			map.put("flag", flag);
			map.put("msg", "未能找到要标记阅读的内部消息");
			addJSONObject(map);
			return "success";
		}
		String[] idarr=msgids.split(",");
		if(idarr.length==0){
			map.put("flag", flag);
			map.put("msg", "未能找到要标记阅读的内部消息");
			addJSONObject(map);
			return "success";			
		}
		if(idarr.length>0){
			ismuti=true;
		}
		map.clear();
		SysUser sysUser=getSysUser();
		Date date=new Date();
		map.put("wrecvuserid", sysUser.getUserid());
		map.put("viewflag","0");
		map.put("viewtime",date);
		map.put("recvflag","0");
		map.put("recvtime", date);
		map.put("wnotviewflag", "0");

		for(String item : idarr){
			if(item!=null && !"".equals(item.trim())){
				map.put("msgid", item.trim());
				if(innerMessageService.updateMsgReceiveBy(map)){
					iSuccess=iSuccess+1;
				}else{
					iFailure=iFailure+1;
				}
			}else{
				iNohandle=iNohandle+1;
			}
		}
		logStr="更新接收的内部消息阅读标志";
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
	 * 收信箱-标志所有未读内部短消息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-2-22
	 */
	public String readAllMessageReceive() throws Exception{
		boolean flag=false;
		SysUser sysUser=getSysUser();
		Map<String,Object> queryMap=new HashMap<String,Object>();
		Date date=new Date();
		queryMap.put("wrecvuserid", sysUser.getUserid());
		queryMap.put("viewflag","0");
		queryMap.put("viewtime",date);
		queryMap.put("recvflag","0");
		queryMap.put("recvtime", date);
		queryMap.put("wnotviewflag", "0");
		flag= innerMessageService.updateMsgReceiveBy(queryMap);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 根据角色编号，获取用户名称字符串，返回格式：a,b,c
	 * @param useridarr
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-13
	 */
	private String getUserNameStringList(String[] useridarr) throws Exception{
		PageMap pageMap=new PageMap();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userList", useridarr);
		pageMap.setCondition(map);
		PageData pageData=getSysUserListByConditon(pageMap,false);
		StringBuffer sbBuffer=new StringBuffer();
		if(pageData.getList()!=null){
			List<SysUser> list=pageData.getList();
			for(SysUser item : list){
				if(StringUtils.isNotEmpty(item.getName())){
					if(sbBuffer.length()>0){
						sbBuffer.append(",");
					}
					sbBuffer.append(item.getName());
				}
			}
		}
		return sbBuffer.toString();
	}
	/**
	 * 获取当前用户的提醒数量 比如待办工作 消息 邮件 公告通知等
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年3月31日
	 */
	public String getRemindCount() throws Exception{
		int msgcount = 0;
		int jobcount = 0;
		int mailcount = 0;
		int noticecount = 0;
		int totalcount = 0;
		SysUser sysUser = getSysUser();
		//待办工作数量
		Map jobmap = new HashMap();
		jobmap.put("userid", sysUser.getUserid());
		jobmap.put("type", "10");
        pageMap.setCondition(jobmap);
        pageMap.setRows(1000);
        IWorkService workService = (IWorkService) SpringContextUtils.getBean("workService");
        PageData jobpageData = workService.getProcessData(pageMap);
        jobcount = jobpageData.getTotal();
        //未读信息数量
        pageMap = new PageMap( 1,1000);
        Map nsgmap=new HashMap();
        nsgmap.put("recvflag", "1");	//未接收
        nsgmap.put("delflag", "1");	//未删除
        nsgmap.put("viewflag", "1");	//未阅读
        nsgmap.put("recvuserid", sysUser.getUserid());
		pageMap.setCondition(nsgmap);
		msgcount = innerMessageService.getMsgReceiveCount(pageMap);
		
		//未读邮件数量
		pageMap = new PageMap( 1,1000);
		Map mailMap=new HashMap();
		mailMap.put("recvuserid", sysUser.getUserid());
		mailMap.put("delflag", "1");	//未删除数据
		mailMap.put("viewflag", "1");//未阅读
		mailMap.put("sort","emlc_id desc");
		mailMap.put("order", ",recvtime desc");
		pageMap.setCondition(mailMap);
		IEmailService emailService = (IEmailService) SpringContextUtils.getBean("emailService");
		PageData mailpageData=emailService.showEmailReceivePageList(pageMap);
		if(mailpageData!=null && mailpageData.getList()!=null){
			mailcount = mailpageData.getTotal();
		}
		
		pageMap = new PageMap( 1,1000);
		//未读公告数量
		Map queryMap = new HashMap();
		queryMap.put("isqueryviewrang", "1");
		queryMap.put("delflag", "1");	//默认取未删除数据
		queryMap.put("state", "1");		//读取启用状态的数据
		queryMap.put("enddate", (new Date()));	//读取终止时间未到的数据
		queryMap.put("isshowcuruserrc", 1);	//显示阅读数
		queryMap.put("curuserid", sysUser.getUserid());	//当前用户编号
		queryMap.put("curuserdept", sysUser.getDepartmentid());	//当前用户部门编号
		//示读的公告
		queryMap.put("isCurUserNotRead", "1");
		queryMap.put("notReadCountUserid", sysUser.getUserid());
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
		INoticeService noticeService = (INoticeService) SpringContextUtils.getBean("noticeService");
		pageMap.setCondition(queryMap);
		noticecount=noticeService.getMsgNoticeCount(pageMap);
		
		totalcount = msgcount + jobcount + mailcount + noticecount;
		Map map = new HashMap();
		map.put("msgcount", msgcount);
		map.put("jobcount", jobcount);
		map.put("mailcount", mailcount);
		map.put("noticecount", noticecount);
		map.put("totalcount", totalcount);
		addJSONObject(map);
		return "success";
	}
}

