/**
 * @(#)IMsgContentService.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-19 zhanghonghui 创建版本
 */
package com.hd.agent.message.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.message.dao.InnerMessageMapper;
import com.hd.agent.message.model.MsgContent;
import com.hd.agent.message.model.MsgReceive;
import com.hd.agent.message.service.IInnerMessageService;
import org.apache.log4j.Logger;

/**
 * 消息业务实现层
 * 
 * @author zhanghonghui
 */
public class InnerMessageService extends BaseServiceImpl implements IInnerMessageService {
	private static final Logger logger = Logger.getLogger(InnerMessageService.class);
	
	/**
	 * 内部消息
	 */
	private InnerMessageMapper innerMessageMapper;
	
	public InnerMessageMapper getInnerMessageMapper() {
		return innerMessageMapper;
	}

	public void setInnerMessageMapper(InnerMessageMapper innerMessageMapper) {
		this.innerMessageMapper = innerMessageMapper;
	}

	//**************************************//
	//消息内容部分
	//**************************************//
	@Override
	public MsgContent showMsgContent(String id) throws Exception{
		MsgContent msgContent=innerMessageMapper.showMsgContent(id);
		return msgContent;
	}

	public MsgContent showMsgContentBy(Map queryMap) throws Exception{
		MsgContent msgContent=innerMessageMapper.showMsgContentBy(queryMap);
		return msgContent;
	}
	/**
	 * 添加消息内容
	 */
	@Override
	public boolean addMsgContent(MsgContent msgContent) throws Exception{
		int irow=innerMessageMapper.addMsgContent(msgContent);
		return irow>0;
	}


	/**
	 * 更新消息内容
	 */
	@Override
	public boolean updateMsgContent(MsgContent msgContent) throws Exception{
		int irow=innerMessageMapper.updateMsgContent(msgContent);
		return irow>0;
	}
	/**
	 * 更新一条消息内容
	 */
	public boolean updateMsgContentBy(Map map) throws Exception {
		if(!map.containsKey("isdataauth") || !"0".equals(map.get("isdataauth").toString())){
			String sql = getDataAccessRule("t_msg_content",null);
			map.put("authDataSql", sql);
		}
		int irow=innerMessageMapper.updateMsgContentBy(map);
		return irow>0;		
	}
	
	/**
	 * 根据相应MAP查询数据<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-5
	 */
	public List getMsgContentListBy(Map map) throws Exception{
		List list=innerMessageMapper.getMsgContentListBy(map);
		return list;
	}
	
	/**
	 * 显示表分页信息
	 * @return 
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	@Override
	public PageData showMsgContentPageList(PageMap pageMap) throws Exception{
		String cols = getAccessColumnList("t_msg_content","msgc");
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_msg_content","msgc");
		pageMap.setDataSql(sql);
		Map condition=pageMap.getCondition();
		String queryreceiver=(String)condition.get("query_msgr_receivers");
		if(null!=queryreceiver && !"".equals(queryreceiver.trim())){
			String[] queryArr=StringUtils.split(queryreceiver.trim(),",");
			condition.put("queryreceiverArr",queryArr);
		}
		pageMap.setQuerySqlOnlyAlias("msgc");
		List<MsgContent> list=innerMessageMapper.getMsgContentPageList(pageMap);
		for(MsgContent item: list){
			List<MsgReceive> msgReceiveList=new ArrayList<MsgReceive>();
			if(StringUtils.isNotEmpty(item.getReceivers())){
				String[] receiverArr=StringUtils.split(item.getReceivers(),",");
				item.setReceivernum(receiverArr.length);
				if(receiverArr.length==1) {
					SysUser receiverUser = getSysUserById(receiverArr[0]);
					if(null!=receiverUser){
						item.setReceiveusername(receiverUser.getName());
					}
					item.setReceivernum(1);
				}else{
					if(!"2".equals(item.getClocktype())){
						Map recvCondMap=new HashMap();
						recvCondMap.put("msgid",item.getId());
						int recvcount=innerMessageMapper.getMsgReceiveCountBy(recvCondMap);
						item.setReceivernum(recvcount);
					}
				}
			}
			item.setMsgReceive(msgReceiveList);
		}
		int itotal=innerMessageMapper.getMsgContentCount(pageMap);
		PageData pageData = new PageData(itotal, list, pageMap);

		return pageData;
	}
	
	
	/**
	 * 邮件发送分页列表
	 * 
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-2-7
	 */
	public PageData showMsgContentSimplePageList(PageMap pageMap) throws Exception {
		Map condition=pageMap.getCondition();
		String sql = getDataAccessRule("t_msg_content",null);
		pageMap.setDataSql(sql);
		boolean isContentFilterHtmlShort=false;
		if(condition.containsKey("contentFilterHtmlShort") && "true".equals(condition.get("contentFilterHtmlShort"))){
			isContentFilterHtmlShort=true;
		}
		int iCount=innerMessageMapper.getMsgContentSimplePageCount(pageMap);
		List<MsgContent> list=innerMessageMapper.getMsgContentSimplePageList(pageMap);
		for(MsgContent item : list){
			if(null!=item){
				if(StringUtils.isNotEmpty(item.getReceivers())){
					String[] useridList=item.getReceivers().split(",");
					if(useridList.length==1){
						SysUser sysUser=getSysUserById(useridList[0]);
						if(null!=sysUser){
							item.setReceiveusername(sysUser.getName());
						}
					}else if(item.getReceivers().indexOf(',')>=0){
						item.setReceiveusername("群发");
					}
				}
				if(isContentFilterHtmlShort && StringUtils.isNotEmpty(item.getContent())){
					String txtcontent=CommonUtils.htmlFilter(item.getContent());
					if(txtcontent.length()>300){
						item.setContent(txtcontent.substring(0, 300));
					}else{
						item.setContent(txtcontent);
					}
				}
			}
		}
		PageData pageData = new PageData(iCount,list , pageMap);
		return pageData;
	}
	


	//**************************************//
	//消息接收人部分
	//**************************************//

	/**
	 * 添加接收人
	 */
	@Override
	public boolean addMsgReceive(MsgReceive msgReceive) throws Exception{
		int irow=innerMessageMapper.addMsgReceive(msgReceive);
		return irow>0;
	}

	/**
	 * 更新消息接收人
	 */
	@Override
	public boolean updateMsgReceive(MsgReceive msgReceive) throws Exception{
		int irow=innerMessageMapper.updateMsgReceive(msgReceive);
		return irow>0;
	}
	@Override
	public int getMsgReceiveCountBy(Map map) throws Exception{
		int irows=innerMessageMapper.getMsgReceiveCountBy(map);
		return irows;
	}

	/**
	 * 更新消息接收人
	 */
	public boolean updateMsgReceiveBy(Map map) throws Exception{
		int irows=innerMessageMapper.updateMsgReceiveBy(map);
		return irows>0;
	}
	/**
	 * 只获取消息列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年5月28日
	 */
	public List getMsgReceivePageOnlyList(PageMap pageMap) throws Exception{
		return innerMessageMapper.getMsgReceivePageList(pageMap);
	}
	
	/**
	 * 消息接收人分页
	 * @param pageMap
	 * @return
	 * @throws Exception
	 */
	public PageData getMsgReceivePageList(PageMap pageMap) throws Exception{
		Map<String,Object> condition=pageMap.getCondition();
		boolean isContentFilterHtmlShort=false;
		if(condition.containsKey("contentFilterHtmlShort") && "true".equals(condition.get("contentFilterHtmlShort"))){
			isContentFilterHtmlShort=true;
		}
		pageMap.setQuerySqlOnlyAlias("msgc");
		List<MsgReceive> list=innerMessageMapper.getMsgReceivePageList(pageMap);
		int iCount=innerMessageMapper.getMsgReceiveCount(pageMap);
		for(MsgReceive item : list){
			if("system".equals(item.getSenduserid())){
				item.setSendusername("系统");
			}
			if(isContentFilterHtmlShort && null != item.getMsgContent() && StringUtils.isNotEmpty(item.getMsgContent().getContent())){
				String txtcontent=CommonUtils.htmlFilter(item.getMsgContent().getContent());
				if(txtcontent.length()>300){
					item.getMsgContent().setContent(txtcontent.substring(0, 300));
				}else{
					item.getMsgContent().setContent(txtcontent);
				}
			}
		}
		PageData pageData = new PageData(iCount, list, pageMap);
		return pageData;
	}
	/**
	 * 消息接收人统计
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-11
	 */
	public int getMsgReceiveCount(PageMap pageMap) throws Exception{
		int irows=innerMessageMapper.getMsgReceiveCount(pageMap);
		return irows;
	}
	/**
	 * 查看用户阅读统计
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-30
	 */
	public int getMsgReceiveUserReadCount(PageMap pageMap) throws Exception{
		int irows=innerMessageMapper.getMsgReceiveUserReadCount(pageMap);
		return irows;
	}

	//**************************************//
	//消息
	//**************************************//
	/**
	 * 添加内部短信
	 * @param msgContent
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-23
	 */
	public boolean addSendMessage(MsgContent msgContent) throws Exception{
		boolean isok=false;
		msgContent.setDelflag("1"); //未删除状态
		
		if(null==msgContent.getTabtitle() || "".equals(msgContent.getTabtitle().trim())){
			msgContent.setTabtitle("短信提醒查看");
		}
		if(!"1".equals(msgContent.getClocktype()) 
				&& CommonUtils.isDateTimeStandStr(msgContent.getClocktime()) ){
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date clockDate=sf.parse(msgContent.getClocktime());
			Date now=new Date();
			if(clockDate.compareTo(now)<=0){
				msgContent.setClocktype("1");
			}
		}
		
		if("1".equals(msgContent.getClocktype()) || null==msgContent.getClocktime() || "".equals( msgContent.getClocktime().trim() )){
			msgContent.setClocktype("1");
			msgContent.setAddtime(new Date());
			isok=innerMessageMapper.addMsgContent(msgContent)>0;
			if(isok){
				String[] recvidarr=msgContent.getReceivers().split(",");
				MsgReceive msgReceive=new MsgReceive();
				msgReceive.setMsgid(msgContent.getId());
				msgReceive.setDelflag("1"); //未删除
				msgReceive.setRecvflag("1"); //未接收
				msgReceive.setSendtime(msgContent.getAddtime());
				msgReceive.setSenduserid(msgContent.getAdduserid());
				msgReceive.setViewflag("1");

                String phoneUrl="phone/message/showInnerMessageDetailPage.do?id="+msgContent.getId()+"&markreceiveflag=true&backlistpagetip=true";
                String title="";
            	if(StringUtils.isNotEmpty(msgContent.getAddusername())){
            		title="用户："+msgContent.getAddusername()+"向您发送了新消息！主题:"+msgContent.getTitle();
            	}else{
            		title="请查收新消息！主题："+msgContent.getTitle();
            	}
				
				String tempcont=CommonUtils.htmlFilter(msgContent.getContent());
				if(tempcont.length()>300){
					tempcont=tempcont.substring(0, 300);
				}
				//是否启用内部消息推送
				//1启用时，内部短信会推荐到手机
				//0禁用时，内部短信不会推荐到手机
				//		默认值为1
				String isInnerMessagePushToMobile = getSysParamValue("InnerMessagePushToMobile");
				if(null==isInnerMessagePushToMobile || "".equals(isInnerMessagePushToMobile.trim())){
					isInnerMessagePushToMobile="1";
				}
				for(String item : recvidarr){
					if(!"".equals(item.trim())){
						msgReceive.setRecvuserid(item.trim());
						boolean flag=innerMessageMapper.addMsgReceive(msgReceive)>0;
						if(flag){
							if("1".equals(isInnerMessagePushToMobile.trim())) {
								try {
									sendPhoneMsg(item, "1", title, tempcont, phoneUrl);
								}catch (Exception ex){
									logger.error("推送到手机时，异常",ex);
								}
							}
						}
					}
				}
			}
		}else{
			msgContent.setClocktype("2");
			isok=innerMessageMapper.addMsgContent(msgContent)>0;
		}
		return isok;
	}
	
	/**
	 * 发送延时内部短信
	 * @param msgContent
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-23
	 */
	public boolean addDelaySendMessage(MsgContent msgContent) throws Exception{
		MsgReceive msgReceive=new MsgReceive();
		msgReceive.setMsgid(msgContent.getId());
		msgReceive.setDelflag("1"); //未删除
		msgReceive.setRecvflag("1"); //未接收
		msgReceive.setSendtime(msgContent.getAddtime());
		msgReceive.setSenduserid(msgContent.getAdduserid());
		msgReceive.setViewflag("1");
		boolean flag=false;
		if(null!=msgContent.getReceivers() && !"".equals(msgContent.getReceivers().trim())){
			String[] recvidarr=msgContent.getReceivers().split(",");
			String phoneUrl="phone/message/showInnerMessageDetailPage.do?id="+msgContent.getId()+"&markreceiveflag=true&backlistpagetip=true";
			String title="";
        	if(StringUtils.isNotEmpty(msgContent.getAddusername())){
        		title="用户："+msgContent.getAddusername()+"向您发送了新消息！主题:"+msgContent.getTitle();
        	}else{
        		title="请查收新消息！主题："+msgContent.getTitle();
        	}
			
			String tempcont=CommonUtils.htmlFilter(msgContent.getContent());
			if(tempcont.length()>300){
				tempcont=tempcont.substring(0, 300);
			}
			//是否启用内部消息推送
			//1启用时，内部短信会推荐到手机
			//0禁用时，内部短信不会推荐到手机
			//		默认值为1
			String isInnerMessagePushToMobile = getSysParamValue("InnerMessagePushToMobile");
			if(null==isInnerMessagePushToMobile || "".equals(isInnerMessagePushToMobile.trim())){
				isInnerMessagePushToMobile="1";
			}
			for(String item : recvidarr){
				if(!"".equals(item.trim())){
					msgReceive.setRecvuserid(item.trim());
					boolean addFlag=addMsgReceive(msgReceive);
					flag=addFlag||flag;					
					if(addFlag){
						if("1".equals(isInnerMessagePushToMobile.trim())) {
							try {
								sendPhoneMsg(item, "1", title, tempcont, phoneUrl);
							}catch (Exception ex){
								logger.error("推送到手机时，异常",ex);
							}
						}
					}
				}
			}
		}
		if(flag){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id", msgContent.getId());
			map.put("wclocktype", "2");	//定时发送数据
			map.put("wdelflag", "1");	//未删除数据
			map.put("clocktype", "0");	//定时发送成功
			map.put("addtime", (new Date()));
			map.put("isdataauth", "0");	//不需要数据权限判断
			flag=updateMsgContentBy(map);
		}
		return flag;
	}
	/**
	 * 已经接收用户列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-25
	 */
	public PageData getReciveUserPageList(PageMap pageMap) throws Exception{
		PageData pageData = new PageData(innerMessageMapper
				.getReceiveUserCount(pageMap), innerMessageMapper
				.getReceiveUserPageList(pageMap), pageMap);
		return pageData;
	}
	@Override
	public PageData showMessageReceiveUserPageList(PageMap pageMap) throws Exception{
		int icount=0;
		List<SysUser> sysUserList=new ArrayList<SysUser>();
		PageData pageData=new PageData(icount,sysUserList,pageMap);
		Map conditionMap=pageMap.getCondition();
		String id=(String)conditionMap.get("msgid");
		if(id==null){
			pageData=new PageData(icount,sysUserList,pageMap);
			return pageData;
		}
		SysUser sysUser=getSysUser();
		Map queryMap=new HashMap();
		queryMap.put("id", id);
		//queryMap.put("hasrecvuserid", sysUser.getUserid());
		queryMap.put("showcontcol", "0");	//不显示内容字段
		MsgContent msgContent=innerMessageMapper.showMsgContentBy(queryMap);
		if(msgContent==null){
			return pageData;
		}
		/**
		 * modifyBy zhanghonghui
		 * modifyContent 定时发送的消息，才需要解析消息内容里的接收人
		 * modifyDate 2015-07-28
		 */
		if("2".equals(msgContent.getClocktype())){
			String[] userList=msgContent.getReceivers().split(",");
			pageMap.setCondition(queryMap);
			sysUserList = getBaseSysUserMapper().showSysUserList(pageMap);
			icount=getBaseSysUserMapper().showSysUserCount(pageMap);
			if(sysUserList.size()>0){
				List<Map> jsonList = new ArrayList();
				for(SysUser item : sysUserList){
					Map map = new HashMap();
					map.put("recvuserid", item.getUserid());
					map.put("recvusername",item.getName());
					map.put("clocktime",msgContent.getClocktime());
					jsonList.add(map);
				}
				pageData=new PageData(icount,jsonList,pageMap);
			}
		}else{
			queryMap.put("msgid", id);
			pageMap.setCondition(queryMap);
			pageData=getReciveUserPageList(pageMap);
		}
		return pageData;
	}
}

