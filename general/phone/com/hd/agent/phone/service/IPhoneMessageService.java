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

import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.message.model.MsgNotice;

/**
 * 
 * 
 * @author zhengziyong
 */
public interface IPhoneMessageService {
	
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
	 * 手机端，统计邮件箱，已发送条件，已收未阅读条数，已经删除条数
	 * @param queryMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月14日
	 */
	public Map getMailItemCount(Map queryMap) throws Exception;
}

