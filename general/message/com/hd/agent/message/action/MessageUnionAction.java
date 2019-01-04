/**
 * @(#)MessageAction.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-4-30 zhanghonghui 创建版本
 */
package com.hd.agent.message.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.hr.action.BaseAction;
import com.hd.agent.message.service.IEmailService;
import com.hd.agent.message.service.IInnerMessageService;
import com.hd.agent.message.service.INoticeService;


/**
 * 
 * 
 * @author zhanghonghui
 */
public class MessageUnionAction extends BaseAction {
	private IInnerMessageService innerMessageService;
	private IEmailService emailService;
	public IInnerMessageService getInnerMessageService() {
		return innerMessageService;
	}
	public void setInnerMessageService(IInnerMessageService innerMessageService) {
		this.innerMessageService = innerMessageService;
	}
	public IEmailService getEmailService() {
		return emailService;
	}
	public void setEmailService(IEmailService emailService) {
		this.emailService = emailService;
	}
	private INoticeService noticeService;
	public INoticeService getNoticeService() {
		return noticeService;
	}

	public void setNoticeService(INoticeService noticeService) {
		this.noticeService = noticeService;
	}
	public String showUnreadMsgEmail() throws Exception{
		SysUser sysUser=getSysUser();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("recvuserid", sysUser.getUserid());
		map.put("delflag","1");
		map.put("viewflag", "1");
		pageMap.setCondition(map);
		int ureadmsg=innerMessageService.getMsgReceiveUserReadCount(pageMap);
		
		int ureademail=emailService.getEmailUserReadCount(pageMap);
		
		//有无提醒信息
		map.put("recvflag", "1");	//未接收
		map.put("delflag", "1");	//未删除
		map.put("viewflag", "1");	//未阅读
		map.put("recvuserid", sysUser.getUserid());
		pageMap.setCondition(map);
		int reminds=innerMessageService.getMsgReceiveCount(pageMap);
		
		map.clear();
		map.put("ureadmsg", ureadmsg);
		map.put("reminds", reminds);
		map.put("ureademail", ureademail);
		addJSONObject(map);
		return SUCCESS;
	}
	
	public String showUnreadMessageCount() throws Exception{

		SysUser sysUser=getSysUser();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("recvuserid", sysUser.getUserid());
		map.put("delflag","1");
		map.put("viewflag", "1");
		pageMap.setCondition(map);
		int ureadmsg=innerMessageService.getMsgReceiveUserReadCount(pageMap);
		
		int ureademail=emailService.getEmailUserReadCount(pageMap);
		

		map.clear();
		//有无提醒信息
		map.put("recvflag", "1");	//未接收
		map.put("delflag", "1");	//未删除
		map.put("viewflag", "1");	//未阅读
		map.put("recvuserid", sysUser.getUserid());
		pageMap.setCondition(map);
		int reminds=innerMessageService.getMsgReceiveCount(pageMap);
		

		map.clear();
		map.put("isqueryviewrang", "1");

		map.put("delflag", "1");	//默认取未删除数据
		map.put("state", "1");		//读取启用状态的数据
		map.put("enddate", (new Date()));	//读取终止时间未到的数据
		map.put("isshowcuruserrc", 1);	//显示阅读数
		if(!map.containsKey("rows")){
			map.put("rows", 10);
		}
		if(!map.containsKey("order") && ! map.containsKey("sort")){
			map.put("sort","istop desc");
			map.put("order", ",addtime desc");
		}
		map.put("curuserid", sysUser.getUserid());	//当前用户编号
		if(StringUtils.isNotEmpty(sysUser.getPersonnelid())){
			Personnel personnel=getPersonnelInfoById(sysUser.getPersonnelid());
			if(null!=personnel && StringUtils.isNotEmpty(personnel.getId())){
				map.put("curuserdept", personnel.getBelongdeptid());	//当前用户部门编号					
			}else{
				map.put("curuserdept", sysUser.getDepartmentid());	//当前用户部门编号
			}
		}else{
			map.put("curuserdept", sysUser.getDepartmentid());	//当前用户部门编号
		}
		
		
		//示读的公告
		map.put("isCurUserNotRead", "1");
		map.put("notReadCountUserid", sysUser.getUserid());
		
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
				map.put("curusrolelist", roleList);
			}
		}

		pageMap.setCondition(map);
		int ureadnotice=noticeService.getMsgNoticeCount(pageMap);
		
		
		map.clear();
		map.put("ureadmsg", ureadmsg);
		map.put("reminds", reminds);
		map.put("ureademail", ureademail);
		map.put("ureadnotice", ureadnotice);
		addJSONObject(map);
		return SUCCESS;
	}
	/**
	 * 需要提醒的消息数量
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月20日
	 */
	public String showMsgRemindCount() throws Exception{
		SysUser sysUser=getSysUser();
		Map<String, Object> map=new HashMap<String, Object>();
		
		//有无提醒信息
		map.put("recvflag", "1");	//未接收
		map.put("delflag", "1");	//未删除
		map.put("viewflag", "1");	//未阅读
		map.put("recvuserid", sysUser.getUserid());
		pageMap.setCondition(map);
		int reminds=innerMessageService.getMsgReceiveCount(pageMap);
		
		map.clear();
		map.put("reminds", reminds);
		addJSONObject(map);
		return SUCCESS;
	}
}

