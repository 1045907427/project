/**
 * @(#)NoticeService.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-26 zhanghonghui 创建版本
 */
package com.hd.agent.message.service.impl;

import java.util.*;

import com.hd.agent.accesscontrol.model.Authority;
import com.hd.agent.basefiles.model.DepartMent;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.service.IAttachFileService;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.message.dao.NoticeMapper;
import com.hd.agent.message.model.MsgNotice;
import com.hd.agent.message.model.MsgNoticeread;
import com.hd.agent.message.service.INoticeService;
import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class NoticeServiceImpl extends BaseServiceImpl implements INoticeService {
	private static final Logger logger = Logger.getLogger(NoticeServiceImpl.class);
	/**
	 * 通知通告数据操作
	 */
	private NoticeMapper noticeMapper;

	/**
	 * 附件
	 */
	private IAttachFileService attachFileService;
	
	public NoticeMapper getNoticeMapper() {
		return noticeMapper;
	}
	public void setNoticeMapper(NoticeMapper noticeMapper) {
		this.noticeMapper = noticeMapper;
	}
	public IAttachFileService getAttachFileService() {
		return attachFileService;
	}

	public void setAttachFileService(IAttachFileService attachFileService) {
		this.attachFileService = attachFileService;
	}
	/**
	 * 添加一条通知通告
	 * @param msgNotice
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-25
	 */
	@Override
	public boolean addMsgNotice(MsgNotice msgNotice) throws Exception{
		int irows=noticeMapper.insertMsgNotice(msgNotice);
		boolean isok= irows>0;
		if(isok){
			if(StringUtils.isNotEmpty(msgNotice.getAttach())){
			//添加附件权限
				//attachFileService.updateAttachFileAuth(msgNotice.getAttach(),msgNotice.getReceiveuser(),msgNotice.getReceivedept(),msgNotice.getReceiverole());
			}
		}
		return isok;
	}
	/**
	 * 添加一条通知通告
	 * @param msgNotice
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-10-26
	 */
	@Override
	public boolean addMsgNotice(MsgNotice msgNotice,Map map) throws Exception{
		boolean isok=addMsgNotice(msgNotice);
		if(isok){
			if(map.containsKey("delAttachIdarrs") && null!=map.get("delAttachIdarrs") && !"".equals(map.get("delAttachIdarrs").toString())){
				map.put("idarrs", map.get("delAttachIdarrs"));
				map.remove("delAttachIdarrs");
				attachFileService.deleteAttachWithPhysical(map);
			}
		}
		return isok;
	}
	/**
	 * 更新一条通知通告
	 * @param msgNotice
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-25
	 */
	@Override
	public boolean updateMsgNotice(MsgNotice msgNotice) throws Exception{
		int irows=noticeMapper.updateMsgNotice(msgNotice);
		boolean isok= irows>0;
		if(StringUtils.isNotEmpty(msgNotice.getAttach())){
		//添加附件权限
			//attachFileService.updateAttachFileAuth(msgNotice.getAttach(),msgNotice.getReceiveuser(),msgNotice.getReceivedept(),msgNotice.getReceiverole());
		}
		if(isok){
			if("1".equals(msgNotice.getState()) && "1".equals(msgNotice.getIsmsg())){
				//保存成功并且，需要发短信通知
				sendNoticeInnerMsg(msgNotice);
			}
		}
		return isok;
	}
	/**
	 * 更新一条通知通告
	 * @param msgNotice
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-10-26
	 */
	@Override
	public boolean updateMsgNotice(MsgNotice msgNotice,Map map) throws Exception{
		boolean isok=updateMsgNotice(msgNotice);
		if(isok){
			if(map.containsKey("delAttachIdarrs") && null!=map.get("delAttachIdarrs") && !"".equals(map.get("delAttachIdarrs").toString())){
				map.put("idarrs", map.get("delAttachIdarrs"));
				map.remove("delAttachIdarrs");
				attachFileService.deleteAttachWithPhysical(map);
			}
		}
		return isok;
	}
	/**
	 * 直接删除公告通知
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-25
	 */
	@Override
	public boolean deleteMsgNoticeBy(Map map) throws Exception{
		String sql = getDataAccessRule("t_msg_notice",null);
		map.put("authDataSql", sql);
		int irows=noticeMapper.deleteMsgNoticeBy(map);
		return irows>0;
	}
	/**
	 * 显示一条通知通告内容
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-25
	 */
	@Override
	public MsgNotice showMsgNotice(String id) throws Exception{
		MsgNotice msgNotice=noticeMapper.showMsgNotice(id);
		if(msgNotice!=null){
			if(StringUtils.isNotEmpty(msgNotice.getPublisherid())){
				SysUser sysUser=getSysUserById(msgNotice.getPublisherid());
				if(null!=sysUser){
					msgNotice.setPublishername(sysUser.getName());
					if(StringUtils.isNotEmpty(sysUser.getDepartmentname())){
						msgNotice.setPublishdeptname(sysUser.getDepartmentname());
					}else{
						if(StringUtils.isNotEmpty(msgNotice.getPublishdeptid())) {
							DepartMent departMent = getDepartmentByDeptid(msgNotice.getPublishdeptid());
							if(null!=departMent){
								msgNotice.setPublishdeptname(departMent.getName());
							}
						}
					}
				}
			}
		}
		return msgNotice;
	}
	/**
	 * 显示通知通告分页列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	@Override
	public PageData showMsgNoticePageList(PageMap pageMap) throws Exception{

		Map condition=pageMap.getCondition();
		boolean isContentFilterHtmlShort=false;
		if(condition.containsKey("contentFilterHtmlShort") && "true".equals(condition.get("contentFilterHtmlShort"))){
			isContentFilterHtmlShort=true;
		}
		pageMap.setQuerySqlOnlyAlias("n");

		String queryreceiver=(String)condition.get("queryreceiver");
		if(null!=queryreceiver && !"".equals(queryreceiver.trim())){
			String[] queryArr=StringUtils.split(queryreceiver.trim(),",");
			condition.put("queryreceiverArr",queryArr);
		}
		String querydept=(String)condition.get("querydept");
		if(null!=querydept && !"".equals(querydept.trim())){
			String[] queryArr=StringUtils.split(querydept.trim(),",");
			condition.put("querydeptArr",queryArr);
		}
		String queryrole=(String)condition.get("queryrole");
		if(null!=queryrole && !"".equals(queryrole.trim())){
			String[] queryArr=StringUtils.split(queryrole.trim(),",");
			condition.put("queryroleArr",queryArr);
		}

		List<MsgNotice> list=noticeMapper.getMsgNoticePageList(pageMap);
		int count=0;
		String notQueryPageCount=(String)condition.get("notQueryPageCount");
		if(null==notQueryPageCount || !"true".equals(notQueryPageCount)){
			count=noticeMapper.getMsgNoticeCount(pageMap);
		}
		for(MsgNotice item : list){
			if(null==item){
				continue;
			}
			if(StringUtils.isNotEmpty(item.getPublisherid())){
				SysUser sysUser=getSysUserById(item.getPublisherid());
				if(null!=sysUser){
					item.setPublishername(sysUser.getName());
					if(StringUtils.isNotEmpty(sysUser.getDepartmentname())){
						item.setPublishdeptname(sysUser.getDepartmentname());
					}else{
						if(StringUtils.isNotEmpty(item.getPublishdeptid())) {
							DepartMent departMent = getDepartmentByDeptid(item.getPublishdeptid());
							if(null!=departMent){
								item.setPublishdeptname(departMent.getName());
							}
						}
					}
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
			setDeptUserRoleName(item,10,"...");
		}
		PageData pageData = new PageData(count, list, pageMap);
		return pageData;
	}
	@Override
	public int getMsgNoticeCount(PageMap pageMap) throws Exception{
		return noticeMapper.getMsgNoticeCount(pageMap);
	}
	
	/**
	 * 公告通知发布分布列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-31
	 */
	@Override
	public PageData getMsgNoticePublishPageList(PageMap pageMap) throws Exception{
		String cols = getAccessColumnList("t_msg_notice","n");
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_msg_notice","n");
		pageMap.setDataSql(sql);
		pageMap.setQuerySqlOnlyAlias("n");

		Map condition=pageMap.getCondition();
		String queryreceiver=(String)condition.get("queryreceiver");
		if(null!=queryreceiver && !"".equals(queryreceiver.trim())){
			String[] queryArr=StringUtils.split(queryreceiver.trim(),",");
			condition.put("queryreceiverArr",queryArr);
		}
		String querydept=(String)condition.get("querydept");
		if(null!=querydept && !"".equals(querydept.trim())){
			String[] queryArr=StringUtils.split(querydept.trim(),",");
			condition.put("querydeptArr",queryArr);
		}
		String queryrole=(String)condition.get("queryrole");
		if(null!=queryrole && !"".equals(queryrole.trim())){
			String[] queryArr=StringUtils.split(queryrole.trim(),",");
			condition.put("queryroleArr",queryArr);
		}

		int iCount=noticeMapper.getMsgNoticePublishCount(pageMap);
		List<MsgNotice> list=noticeMapper.getMsgNoticePublishPageList(pageMap);
		for (MsgNotice item : list){
			if(StringUtils.isNotEmpty(item.getPublisherid())){
				SysUser sysUser=getSysUserById(item.getPublisherid());
				if(sysUser!=null){
					item.setPublishername(sysUser.getName());

					if(StringUtils.isNotEmpty(sysUser.getDepartmentname())){
						item.setPublishdeptname(sysUser.getDepartmentname());
					}else{
						if(StringUtils.isNotEmpty(item.getPublishdeptid())) {
							DepartMent departMent = getDepartmentByDeptid(item.getPublishdeptid());
							if(null!=departMent){
								item.setPublishdeptname(departMent.getName());
							}
						}
					}
				}
			}
			setDeptUserRoleName(item,10,"...");
		}
		PageData pageData = new PageData(iCount,list , pageMap);
		return pageData;
	}
	
	/**
	 * 更新公告通知<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-31
	 */
	@Override
	public boolean updateMsgNoticeBy(Map map) throws Exception{
		if(!map.containsKey("isdataauth") || !"0".equals(map.get("isdataauth").toString().trim())){
			String sql = getDataAccessRule("t_msg_notice",null);
			map.put("authDataSql", sql);
		}
		int irows=noticeMapper.updateMsgNoticeBy(map);
		return irows>0;
	}
	
	/**
	 * 获取列表数据<br/>
	 * @param queryMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-31
	 */
	public List getMsgNoticeList(Map queryMap) throws Exception{
		List<MsgNotice> list=noticeMapper.getMsgNoticeList(queryMap);
		for (MsgNotice item : list){
			if(StringUtils.isNotEmpty(item.getPublisherid())){
				SysUser sysUser=getSysUserById(item.getPublisherid());
				if(sysUser!=null){
					item.setPublishername(sysUser.getName());

					if(StringUtils.isNotEmpty(sysUser.getDepartmentname())){
						item.setPublishdeptname(sysUser.getDepartmentname());
					}else{
						if(StringUtils.isNotEmpty(item.getPublishdeptid())) {
							DepartMent departMent = getDepartmentByDeptid(item.getPublishdeptid());
							if(null!=departMent){
								item.setPublishdeptname(departMent.getName());
							}
						}
					}
				}
			}
		}
		return list;
	}
	@Override
	public Map auditMsgNotice(String ids) throws Exception{
		Map<String,Object> queryMap=new HashMap<String,Object>();
		Map resultMap=new HashMap();
		if(null==ids || "".equals(ids.trim())){
			resultMap.put("flag", false);
			resultMap.put("ismuti", false);
			resultMap.put("isuccess", 0);
			resultMap.put("ifailure", 0);
			resultMap.put("inohandle", 0);
			return resultMap;
		}
		//系统用户
		SysUser sysUser=getSysUser();
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		boolean ismuti=false;
		queryMap.put("idarrs", ids);
		queryMap.put("withrecvuser", "0");
		queryMap.put("withcontent", "0");
		queryMap.put("withattachment", "0");
		List<MsgNotice> list=getMsgNoticeList(queryMap);

		if(list.size()>0){
			ismuti=true;
		}
		queryMap.clear();
		Date nowDate=new Date();
		queryMap.put("state", "1");	//启用状态
		queryMap.put("publishtime",nowDate);
		queryMap.put("publisherid",sysUser.getUserid());
		queryMap.put("publishdeptid",sysUser.getDepartmentid());
		queryMap.put("modifytime",nowDate);
		queryMap.put("modifyuserid",sysUser.getUserid());
		//queryMap.put("wstatearr", "0,2");
		for(MsgNotice item : list){
			if("2".equals(item.getState()) || "0".equals(item.getState())){
				queryMap.put("id", item.getId());
				if(updateMsgNoticeBy(queryMap)){
					iSuccess=iSuccess+1;
					//保存成功并且，需要发短信通知
					sendNoticeInnerMsg(item);
				}else{
					iFailure=iFailure+1;					
				}
			}else{
				iNohandle=iNohandle+1;				
			}
		}
        iFailure = iNohandle + iFailure;//将没有权限的和操作失败的合并为失败数
		resultMap.put("flag", true);
		resultMap.put("ismuti", ismuti);
		resultMap.put("isuccess", iSuccess);
		resultMap.put("ifailure", iFailure);
		resultMap.put("inohandle", iNohandle);
		return resultMap;
	}
	@Override
	public Map oppauditMsgNotice(String ids) throws Exception{
		Map<String,Object> queryMap=new HashMap<String,Object>();
		Map resultMap=new HashMap();
		if(null==ids || "".equals(ids.trim())){
			resultMap.put("flag", false);
			resultMap.put("ismuti", false);
			resultMap.put("isuccess", 0);
			resultMap.put("ifailure", 0);
			resultMap.put("inohandle", 0);
			return resultMap;
		}
		//系统用户
		SysUser sysUser=getSysUser();
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		boolean ismuti=false;
		queryMap.put("idarrs", ids);
		queryMap.put("withrecvuser", "0");
		queryMap.put("withcontent", "0");
		queryMap.put("withattachment", "0");
		List<MsgNotice> list=getMsgNoticeList(queryMap);

		if(list.size()>0){
			ismuti=true;
		}
		queryMap.clear();
		Date nowDate=new Date();
		queryMap.put("state", "0");	//禁用状态
		queryMap.put("modifytime",nowDate);
		queryMap.put("modifyuserid",sysUser.getUserid());
		//queryMap.put("wstatearr", "0,2");
		for(MsgNotice item : list){
			if("1".equals(item.getState())){
				queryMap.put("id", item.getId());
				if(updateMsgNoticeBy(queryMap)){
					iSuccess=iSuccess+1;
				}else{
					iFailure=iFailure+1;
				}
			}else{
				iNohandle=iNohandle+1;
			}
		}
		//iFailure = iNohandle + iFailure;//将没有权限的和操作失败的合并为失败数
		resultMap.put("flag", true);
		resultMap.put("ismuti", ismuti);
		resultMap.put("isuccess", iSuccess);
		resultMap.put("ifailure", iFailure);
		resultMap.put("inohandle", iNohandle);
		return resultMap;
	}
	/**
	 * 发送内部消息
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-15
	 */
	private void sendNoticeInnerMsg(MsgNotice msgNotice) throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("mtiptype", "1");	//短信
		map.put("msgtype", "2"); //公告通知
		map.put("senduserid",getSysUser().getUserid());
		String[] tmparr=null;
		List<String> list=new ArrayList<String>();
		List<SysUser> userList=null;
		if(StringUtils.isNotEmpty(msgNotice.getReceiveuser())){
			if("ALL".equals(msgNotice.getReceiveuser().toUpperCase())){
				userList=getSysUserListByEnable();
				if(null!=userList && userList.size()>0){
					for(SysUser userItem: userList ){
						if(StringUtils.isNotEmpty(userItem.getUserid())){
							list.add(userItem.getUserid());
						}
					}
				}
			}else{
				tmparr=msgNotice.getReceiveuser().split(",");
				if(null!=tmparr && tmparr.length>0){
					list.addAll(Arrays.asList(tmparr));
				}
			}			
		}
		if(StringUtils.isNotEmpty(msgNotice.getReceivedept())){
			if("ALL".equals(msgNotice.getReceivedept().toUpperCase())){
				userList=getSysUserListByEnable();
				if(null!=userList && userList.size()>0){
					for(SysUser userItem: userList ){
						if(StringUtils.isNotEmpty(userItem.getUserid())){
							list.add(userItem.getUserid());
						}
					}
				}
			}else{
				tmparr= msgNotice.getReceivedept().split(",");
				if(null!=tmparr && tmparr.length>0){
					for(String item : tmparr){
						if(null!=item && !"".equals(item.trim())){
							userList=getSysUserListByDept(item.trim());
							if(null!=userList && userList.size()>0){
								for(SysUser userItem: userList ){
									if(StringUtils.isNotEmpty(userItem.getUserid())){
										list.add(userItem.getUserid());
									}
								}
							}
						}
					}
				}
			}
		}
		if(StringUtils.isNotEmpty(msgNotice.getReceiverole())){
			if("ALL".equals(msgNotice.getReceiverole().toUpperCase())){
				userList=getSysUserListByEnable();
				if(null!=userList && userList.size()>0){
					for(SysUser userItem: userList ){
						if(StringUtils.isNotEmpty(userItem.getUserid())){
							list.add(userItem.getUserid());
						}
					}
				}
			}else{
				tmparr= msgNotice.getReceiverole().split(",");
				if(null!=tmparr && tmparr.length>0){
					for(String item : tmparr){
						if(null!=item && !"".equals(item.trim())){
							userList=getSysUserListByRoleid(item.trim());
							if(null!=userList && userList.size()>0){
								for(SysUser userItem: userList ){
									if(StringUtils.isNotEmpty(userItem.getUserid())){
										list.add(userItem.getUserid());
									}
								}
							}
						}
					}
				}
			}
		}
		if(list.size()>0){
			//去掉重复的
			HashSet<String> hs=new HashSet<String>(list);
			list.clear();
			list.addAll(hs);
			tmparr=(String[])list.toArray(new String[0]);

			String tempcont=CommonUtils.htmlFilter(msgNotice.getContent());
			if(tempcont.length()>300){
				tempcont=tempcont.substring(0, 300);
			}
			if(tmparr!=null && tmparr.length>0){
				if("1".equals(msgNotice.getIsmsg())){
					map.put("receivers",StringUtils.join(tmparr,","));
					map.put("titile", "请查收我的公告通知！标题："+msgNotice.getTitle());
					map.put("tabtitle", "通知详情查看");
					map.put("content", "请查收我的公告通知！<br/>标题："+msgNotice.getTitle()+"<br/>内容简介:"+tempcont);
					map.put("remindurl", "message/notice/noticeDetailPage.do?noticeid="+msgNotice.getId());	//提醒地址
					addMessageReminder(map);
				}else{

					String phoneUrl= "phone/message/showNoticeDetailPage.do?id="+msgNotice.getId()+"&backlistpagetip=true";
                	String title="";
                	if(StringUtils.isNotEmpty(msgNotice.getAddusername())){
                		title="用户："+msgNotice.getAddusername()+"发布了篇公告！主题:"+msgNotice.getTitle();
                	}else{
                		title="请查看收篇公告！主题："+msgNotice.getTitle();
                	}
					//是否启用内部消息推送
					//1启用时，内部短信会推荐到手机
					//0禁用时，内部短信不会推荐到手机
					//		默认值为1
					String isInnerMessagePushToMobile = getSysParamValue("InnerMessagePushToMobile");
					if(null==isInnerMessagePushToMobile || "".equals(isInnerMessagePushToMobile.trim())){
						isInnerMessagePushToMobile="1";
					}
					for(String userid : list){
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
		}
	}
	

	/**
	 * -------------------------------------------------------
	 **********************************************************
	 *通知通告阅读人
	 **********************************************************
	 *---------------------------------------------------------	 
	 */
	
	/**
	 * 添加阅读人
	 * @param msgNoticeread
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-25
	 */
	public boolean addMsgNoticeread(MsgNoticeread msgNoticeread){
		int irows=noticeMapper.insertMsgNoticeread(msgNoticeread);
		return irows>0;
	}
	
	/**
	 * 根据通知通告编号删除阅读人
	 * @param noticeid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-25
	 */
	public boolean deleteMsgNoticereadByNotice(String noticeid){
		int irows=noticeMapper.deleteMsgNoticereadByNotice(noticeid);
		return irows>0;
	}

	/**
	 * 显示通知通告阅读人分页列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	public PageData showMsgNoticereadPageList(PageMap pageMap) throws Exception{
		PageData pageData = new PageData(noticeMapper
				.getMsgNoticereadCount(pageMap), noticeMapper
				.getMsgNoticereadPageList(pageMap), pageMap);
		return pageData;
	}
	
	/**
	 * 根据条件查询公告通知阅读数
	 * @param noticeid
	 * @param receiveuserid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-30
	 */
	public int getMsgNoticereadCountBy(String noticeid,String receiveuserid) throws Exception{
		return noticeMapper.getMsgNoticereadCountBy(noticeid,receiveuserid);
	}
	@Override
	public PageData showMsgNoticeRangeRoleList(PageMap pageMap) throws Exception{
		Map conditionMap=(Map)pageMap.getCondition();
		String noticeid=(String)conditionMap.get("noticeid");
		List<Map> dataList=new ArrayList<Map>();
		int icount=0;
		PageData pageData=null;
		if(null==noticeid || "".equals(noticeid.trim())){
			pageData=new PageData(icount,dataList,pageMap);
			return pageData;
		}
		MsgNotice msgNotice=showMsgNotice(noticeid);
		if(msgNotice==null || StringUtils.isEmpty(msgNotice.getReceiverole())){
			pageData=new PageData(icount,dataList,pageMap);
			return pageData;
		}
		if(!"ALL".equals(msgNotice.getReceiverole())) {
			conditionMap.put("idarrs", msgNotice.getReceiverole());
		}
		String[] idarrs=StringUtils.split(msgNotice.getReceiverole(),",");
		List<Authority> list=getBaseAccessControlMapper().getAuthorityListByIds(idarrs);
		for(Authority item:list){
			Map dataMap=new HashMap();
			dataMap.put("rolename",item.getAuthorityname());
			dataMap.put("roledesc",item.getDescription());
			dataList.add(dataMap);
		}
		pageData=new PageData(icount,dataList,pageMap);
		return pageData;
	}

	@Override
	public PageData showMsgNoticeRangeUserList(PageMap pageMap) throws Exception{
		Map conditionMap=(Map)pageMap.getCondition();
		String noticeid=(String)conditionMap.get("noticeid");
		List<Map> userList=new ArrayList<Map>();
		int icount=0;
		PageData pageData=null;
		if(null==noticeid || "".equals(noticeid.trim())){
			pageData=new PageData(icount,userList,pageMap);
			return pageData;
		}
		MsgNotice msgNotice=showMsgNotice(noticeid);
		if(msgNotice==null || StringUtils.isEmpty(msgNotice.getReceiveuser())){
			pageData=new PageData(icount,userList,pageMap);
			return pageData;
		}
		if(!"ALL".equals(msgNotice.getReceiveuser())) {
			conditionMap.put("useridarrs", msgNotice.getReceiveuser());
		}
		List<SysUser> tmpList=getBaseSysUserMapper().showSysUserList(pageMap);
		icount=getBaseSysUserMapper().showSysUserCount(pageMap);
		for(SysUser item:tmpList){
			Map userMap=new HashMap();
			userMap.put("userid",item.getUserid());
			userMap.put("username",item.getUsername());
			userMap.put("name",item.getName());
			userMap.put("departmentid",item.getDepartmentid());
			userMap.put("departmentname",item.getDepartmentname());
			userList.add(userMap);
		}
		pageData=new PageData(icount,userList,pageMap);
		return pageData;
	}
	@Override
	public void setDeptUserRoleName(MsgNotice item,int shownum,String dotsign) throws Exception{
		if(null==item){
			return;
		}
		if(null==dotsign){
			dotsign="";
		}
		dotsign=dotsign.trim();
		Map<String, Object> queryMap=new HashMap<String, Object>();
		String[] idarr=null;
		List<String> list=null;
		if(null!=item.getReceivedept() && !"".equals(item.getReceivedept().trim())){
			if("ALL".equals(item.getReceivedept().trim())){
				item.setReceivedeptname("所有部门");
			}else{
				queryMap.clear();
				idarr=StringUtils.split(item.getReceivedept().trim(),",");
				int oldIdarrLen=idarr.length;
				if(oldIdarrLen>0){
					if(shownum>0 && shownum<oldIdarrLen){
						idarr=ArrayUtils.subarray(idarr,0,shownum);
					}
					String tmpname=getDeptNameStringList(idarr);
					if(shownum>0 && shownum<oldIdarrLen && !"".equals(dotsign.trim())){
						tmpname=tmpname+dotsign;
					}
					item.setReceivedeptname(tmpname);
				}
			}
		}
		if(null!=item.getReceiverole() && !"".equals(item.getReceiverole().trim())){
			if("ALL".equals(item.getReceiverole().trim())){
				item.setReceiverolename("所有权限");
			}else{
				queryMap.clear();
				idarr=StringUtils.split(item.getReceiverole().trim(),",");
				if(idarr.length>0){
					list=new ArrayList<String>();
					for(String str : idarr){
						if(null!=str && !"".equals(str.trim())){
							list.add(str);
						}
					}
					idarr=list.toArray((new String[0]));
					int oldIdarrLen=idarr.length;
					if(oldIdarrLen>0){
						if(shownum>0 && shownum<oldIdarrLen){
							idarr=ArrayUtils.subarray(idarr,0,shownum);
						}
						String tmpname=getRoleNameStringList(idarr);
						if(shownum>0 && shownum<oldIdarrLen && !"".equals(dotsign.trim())){
							tmpname=tmpname+dotsign;
						}
						item.setReceiverolename(tmpname);
					}
				}
			}
		}

		if(null!=item.getReceiveuser() && !"".equals(item.getReceiveuser().trim())){
			if("ALL".equals(item.getReceiveuser())){
				item.setReceiveusername("所有人员");
			}else{
				queryMap.clear();
				idarr=StringUtils.split(item.getReceiveuser(),",");
				int oldIdarrLen=idarr.length;
				if(oldIdarrLen>0){
					if(shownum>0 && shownum>oldIdarrLen){
						shownum=oldIdarrLen;
					}
					String tmpname=getUserNameStringList(idarr,shownum);
					if(shownum>0 && shownum<oldIdarrLen && !"".equals(dotsign.trim())){
						tmpname=tmpname+dotsign;
					}
					item.setReceiveusername(tmpname);
				}
			}
		}
	}
	/**
	 * 根据角色编号，获取用户名称字符串，返回格式：a,b,c
	 * @param useridarr
	 * @param shownum
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-3-13
	 */
	private String getUserNameStringList(String[] useridarr,int shownum) throws Exception{
		PageMap pageMap=new PageMap();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userList", useridarr);
		pageMap.setCondition(map);
		if(shownum>0){
			pageMap.setRows(shownum);
			pageMap.setPage(1);
		}else{
			map.put("isPage","false");
		}
		List<SysUser> list=getBaseSysUserMapper().showSysUserList(pageMap);
		StringBuffer sbBuffer=new StringBuffer();
		for(SysUser item : list){
			if(StringUtils.isNotEmpty(item.getName())){
				if(sbBuffer.length()>0){
					sbBuffer.append(",");
				}
				sbBuffer.append(item.getName());
			}
		}
		return sbBuffer.toString();
	}

	/**
	 * 根据角色编号，获取角色名称字符串，返回格式：a,b,c
	 * @param idarr
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-3-13
	 */
	private String getRoleNameStringList(String[] idarr) throws Exception{
		List<Authority> list = getBaseAccessControlMapper().getAuthorityListByIds(idarr);
		StringBuffer sbBuffer=new StringBuffer();
		if(list!=null && list.size()>0){
			for(Authority item: list){
				if(StringUtils.isNotEmpty(item.getAuthorityname())){
					if(sbBuffer.length()>0){
						sbBuffer.append(",");
					}
					sbBuffer.append(item.getAuthorityname());
				}
			}
		}
		return sbBuffer.toString();
	}

	/**
	 * 根据部门编号，获取部门名称字符串，返回格式：a,b,c
	 * @param idarr
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-3-13
	 */
	private String getDeptNameStringList(String[] idarr) throws Exception{
		List<DepartMent> list = getBaseDepartMentMapper().getDeptListByIdsStr(idarr);
		StringBuffer sbBuffer=new StringBuffer();
		if(list!=null && list.size()>0){
			for(DepartMent item: list){
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
}

