/**
 * @(#)PhoneOaServiceImpl.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Dec 30, 2013 zhengziyong 创建版本
 */
package com.hd.agent.phone.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.message.dao.EmailMapper;
import com.hd.agent.message.dao.NoticeMapper;
import com.hd.agent.message.model.EmailReceive;
import com.hd.agent.message.model.MsgNotice;
import com.hd.agent.message.model.MsgNoticeread;
import com.hd.agent.phone.service.IPhoneMessageService;

/**
 * 
 * 手机OAservice
 * @author zhengziyong
 */
public class PhoneMessageServiceImpl extends BaseFilesServiceImpl implements IPhoneMessageService {
	
	private EmailMapper emailMapper;
	private NoticeMapper noticeMapper;
	
	public EmailMapper getEmailMapper() {
		return emailMapper;
	}

	public void setEmailMapper(EmailMapper emailMapper) {
		this.emailMapper = emailMapper;
	}

	public NoticeMapper getNoticeMapper() {
		return noticeMapper;
	}

	public void setNoticeMapper(NoticeMapper noticeMapper) {
		this.noticeMapper = noticeMapper;
	}

	@Override
	public PageData getEmailData(PageMap pageMap) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		int count = emailMapper.getEmailReceiveCount(pageMap);
		List<EmailReceive> list = emailMapper.getEmailReceivePageList(pageMap);
		List<Map> result = new ArrayList<Map>();
		for(EmailReceive email : list){
			Map map = new HashMap();
			map.put("id", email.getEmailContent().getId() );
			map.put("title", email.getEmailContent().getTitle());
			map.put("adduser", email.getEmailContent().getAddusername());
			map.put("addtime", dateFormat.format(email.getEmailContent().getAddtime()));
			result.add(map);
		}
		PageData pageData = new PageData(count, result, pageMap);
		return pageData;
	}

	@Override
	public MsgNotice getNoticeNoread(String userId) throws Exception {
		return null;
	}

	@Override
	public PageData getNoticeData(PageMap pageMap) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<MsgNotice> list = noticeMapper.getMsgNoticePageList(pageMap);
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for(MsgNotice notice: list){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", notice.getId());
			map.put("title", notice.getTitle());
			map.put("intro", notice.getIntro());
			map.put("adduser", notice.getAddusername());
			map.put("addtime", dateFormat.format(notice.getAddtime()));
			result.add(map);
		}
		PageData pageData = new PageData(noticeMapper.getMsgNoticeCount(pageMap), result, pageMap);
		return pageData;
	}

	@Override
	public MsgNotice getNoticeDetail(String id, String userId) throws Exception {
		MsgNotice notice = noticeMapper.showMsgNotice(id);
		if(notice != null){
			try {
				if(noticeMapper.getMsgNoticereadCountBy(id, userId)<1){
					MsgNoticeread read = new MsgNoticeread();
					read.setNoticeid(Integer.parseInt(id));
					read.setReceiveuserid(userId);
					read.setReceivetime(new Date());
					noticeMapper.insertMsgNoticeread(read);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			SysUser sysUser = getSysUserById(notice.getAdduserid());
			if(sysUser != null){
				notice.setAddusername(sysUser.getName());
				notice.setAdddeptname(sysUser.getDepartmentname());
			}
		}
		return notice;
	}

	@Override
	public Map getMailItemCount(Map queryMap) throws Exception{
		Map resultMap=new HashMap();
		if(null==queryMap || queryMap.size()==0){
			resultMap.put("sendcount", 0);
			resultMap.put("recvcount", 0);
			resultMap.put("dropcount", 0);
			return resultMap;
		}
		resultMap=emailMapper.getMailItemCountForPhone(queryMap);
		return resultMap;
	}
}

