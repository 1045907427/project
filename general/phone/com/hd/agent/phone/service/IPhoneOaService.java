/**
 * @(#)IPhoneOaService.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Dec 30, 2013 zhengziyong 创建版本
 */
package com.hd.agent.phone.service;

import java.util.List;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.message.model.MsgNotice;

/**
 * 
 * 
 * @author zhengziyong
 */
public interface IPhoneOaService {
	
	/**
	 * 获取邮件列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jan 8, 2014
	 */
	public PageData getEmailData(PageMap pageMap) throws Exception;

	/**
	 * 最新未读公告通知
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jan 6, 2014
	 */
	public MsgNotice getNoticeNoread(String userId) throws Exception;
	
	/**
	 * 公告通知列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Dec 30, 2013
	 */
	public PageData getNoticeData(PageMap pageMap) throws Exception;
	
	/**
	 * 公告通知详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jan 4, 2014
	 */
	public MsgNotice getNoticeDetail(String id, String userId) throws Exception;
	/**
	 * 手机签到
	 * @param remark		签到说明
	 * @param x				x坐标
	 * @param y				y坐标
	 * @param file			图片文件路径
	 * @param num			1表示上午上班签到  2表示上午下班签到  3表示下午上班签到 4表示下午下班签到
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年9月19日
	 */
	public boolean addOrUpdateSingIn(String remark,String x,String y,String file,int num) throws Exception;
	/**
	 * 获取用户的的考勤信息
	 * @param begindate
	 * @param enddate
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年9月26日
	 */
	public List showSigninList(String begindate,String enddate) throws Exception;
}

