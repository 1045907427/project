/**
 * @(#)MsgContentService.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-19 zhanghonghui 创建版本
 */
package com.hd.agent.message.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.message.model.MsgContent;
import com.hd.agent.message.model.MsgReceive;

/**
 * 消息业务接口
 * 
 * @author zhanghonghui
 */
public interface IInnerMessageService {

	//**************************************//
	//消息内容部分
	//**************************************//

	/**
	 * 显示一条消息内容
	 * @param id
	 * @return
	 */
	public MsgContent showMsgContent(String id) throws Exception;
	/**
	 * 显示一条消息内容<br/>
	 * queryMap参数：<br/>
	 * showcontcol : 值不为0时，显示内容字段<br/>
	 * showrecvcol ：值不为0时，显示接收人字段<br/>
	 * id:编号<br/>
	 * adduserid:发送用户编号<br/>
	 * hasrecvuserid：是否有接收用户编号<br/>
	 * @param queryMap
	 * @return
	 */
	public MsgContent showMsgContentBy(Map queryMap) throws Exception;
	/**
	 * 添加一条消息内容
	 * @param msgContent
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-19
	 */
	public boolean addMsgContent(MsgContent msgContent ) throws Exception;
	/**
	 * 更新一条消息内容
	 * @param msgContent
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-19
	 */
	public boolean updateMsgContent(MsgContent msgContent) throws Exception ;
	/**
	 * 更新一条消息内容<br/>
	 * 更新参数：<br/>
	 * title : 标题<br/>
	 * addtime : 添加时间<br/>
	 * clocktime : 定时发送<br/>
	 * clocktype : 是否定时<br/>
	 * msgtype : 消息类型<br/>
	 * 条件参数：<br/>
	 * id: 编号<br/> 
	 * idarr:多编号，格式如：1,2,3<br/>
	 * wadduserid : 添加用户编号<br/>
	 * wclocktype : 是否定时<br/>
	 * wmsgtype : 消息类型<br/>
	 * isdataauth : 是否有数据权限判断，0为不需要，其他值为需要，默认为需要。<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-5
	 */
	public boolean updateMsgContentBy(Map map) throws Exception ;	
	/**
	 * 根据相应MAP查询数据<br/>
	 * 条件参数：<br/>
	 * wadduserid : 添加用户编号<br/>
	 * wclocktype : 是否定时<br/>
	 * wdelflag : 删除标志，1未删除，0已删除<br/>
	 * woverclocktime： 值为1时 ，查询已经过定时的数据<br/>
	 * showcontentcol : 值为1时，显示内容字段<br/>
	 * showreceiverscol : 值为1时，显示接收人字段<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-5
	 */
	public List getMsgContentListBy(Map map) throws Exception;
	
	/**
	 * 显示表分页信息
	 * @return 
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public PageData showMsgContentPageList(PageMap pageMap) throws Exception;
	/**
	 * 显示发送的信息分页面列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月21日
	 */
	public PageData showMsgContentSimplePageList(PageMap pageMap) throws Exception;
	


	//**************************************//
	//消息接收人部分
	//**************************************//

	/**
	 * 添加一消息接收人
	 * @param msgReceive
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-19
	 */
	public boolean addMsgReceive(MsgReceive msgReceive) throws Exception;
	/**
	 * 更新一消息接收人
	 * @param msgReceive
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-19
	 */
	public boolean updateMsgReceive(MsgReceive msgReceive) throws Exception;
	/**
	 * 更新一消息接收人<br/>
	 * map中参数：<br/>
	 * 更新值参数：<br/>
	 * senduserid : 发送用户编号<br/>
	 * sendtime : 发送时间<br/>
	 * recvuserid : 接收用户编号<br/>
	 * recvtime : 接收时间<br/>
	 * viewflag : 阅读标志<br/>
	 * viewtime : 阅读时间<br/>
	 * delflag : 删除标志<br/>
	 * deltime : 删除时间<br/>
	 * recvflag : 接收标志<br/>
	 * 条件参数：<br/>
	 * id : 信息编号<br/>
	 * idarr : 多编号，格式：1,2,3<br/>
	 * msgid : 消息编号<br/>
	 * msgidarrs : 多消息编号<br/>,格式：1,2,3<br/>
	 * wsenduserid : 发送用户编号<br/>
	 * wrecvuserid : 接收用户编号<br/>
	 * wviewflag : 阅读标志<br/>
	 * wnotviewflag : 不等于该阅读标志<br/>
	 * wdelflag : 删除标志<br/>
	 * wrecvflag : 接收标志<br/>
	 * wnotrecvflag : 不等于接收标志<br/>
	 * wsendtimeBeforeTime : 传入时间，用做比较 时间>发送时间
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-19
	 */
	public boolean updateMsgReceiveBy(Map map) throws Exception;
	
	/**
	 * 根据参数统计接收人<br/>
	 * map中参数：<br/>
	 * msgid : 消息编号<br/>
	 * msgidarrs : 多消息编号<br/>,格式：1,2,3<br/>
	 * wsenduserid : 发送用户编号<br/>
	 * wrecvuserid : 接收用户编号<br/>
	 * wviewflag : 阅读标志<br/>
	 * wnotviewflag : 不等于该阅读标志<br/>
	 * wdelflag : 删除标志<br/>
	 * wrecvflag : 接收标志<br/>
	 * wnotrecvflag : 不等于接收标志<br/>
	 * wsendtimeBeforeTime : 传入时间，用做比较 时间>发送时间
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-19
	 */
	public int getMsgReceiveCountBy(Map map) throws Exception;
	/**
	 * 只获取消息列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年5月28日
	 */
	public List getMsgReceivePageOnlyList(PageMap pageMap) throws Exception;
	/**
	 * 消息接收人分页
	 * @param pageMap
	 * @return
	 * @throws Exception
	 */
	public PageData getMsgReceivePageList(PageMap pageMap) throws Exception;
	/**
	 * 消息接收人统计
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-11
	 */
	public int getMsgReceiveCount(PageMap pageMap) throws Exception;
	/**
	 * 已经接收用户列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-25
	 */
	public PageData getReciveUserPageList(PageMap pageMap) throws Exception;
	/**
	 * 查看用户阅读统计
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-30
	 */
	public int getMsgReceiveUserReadCount(PageMap pageMap) throws Exception;

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
	public boolean addSendMessage(MsgContent msgContent) throws Exception;
	
	/**
	 * 发送延时内部短信
	 * @param msgContent
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-23
	 */
	public boolean addDelaySendMessage(MsgContent msgContent) throws Exception;

	/**
	 * 获取接收的用户列表
	 * @param pageMap
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author zhanghonghui
	 * @date Sep 05, 2017
	 */
	public PageData showMessageReceiveUserPageList(PageMap pageMap) throws Exception;
}

