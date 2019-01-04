/**
 * @(#)MsgContentMapper.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-18 zhanghonghui 创建版本
 */
package com.hd.agent.message.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.message.model.MsgContent;
import com.hd.agent.message.model.MsgReceive;

/**
 * 消息内容数据操作层
 * 
 * @author zhanghonghui
 */
public interface InnerMessageMapper {
	
	public MsgContent showMsgContentList(Map map);
	/**
	 * 显示一条消息内容
	 * @param id
	 * @return
	 */
	public MsgContent showMsgContent(@Param("id") String id);
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
	public MsgContent showMsgContentBy(Map queryMap);
	/**
	 * 添加一条消息内容
	 * @param msgContent
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-19
	 */
	public int addMsgContent(MsgContent msgContent );
	/**
	 * 更新一条消息内容
	 * @param msgContent
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-19
	 */
	public int updateMsgContent(MsgContent msgContent);
	/**
	 * 更新一条消息内容<br/>
	 * 更新参数：<br/>
	 * title : 标题<br/>
	 * addtime : 添加时间<br/>
	 * clocktime : 定时发送<br/>
	 * clocktype : 是否定时<br/>
	 * msgtype : 消息类型<br/>
	 * delflag : 删除标志<br/>
	 * deltime : 删除时间<br/>
	 * 条件参数：<br/>
	 * id: 编号<br/> 
	 * idarr:多编号，格式如：1,2,3<br/>
	 * wadduserid : 添加用户编号<br/>
	 * wclocktype : 是否定时，定时发送是否执行1即时发2定时发0定时已发送<br/>
	 * wmsgtype : 消息类型<br/>
	 * wdelflag : 删除标志，1未删除，0已删除<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-5
	 */
	public int updateMsgContentBy(Map map);
	
	/**
	 * 更新一条消息内容<br/>
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
	public List<MsgContent> getMsgContentListBy(Map map);
	/**
	 * 设置删除标志位，标志一条消息内容的删除
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-19
	 */
	public int deleteFlagMsgContent(@Param("id") String id);
	/**
	 * 发送内容信息
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-23
	 */
	public List getMsgContentPageList(PageMap pageMap);
	
	/**
	 * 发送内容分页
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-23
	 */
	public int getMsgContentCount(PageMap pageMap);
	
	
	
	/*
	//----------------------------------------------------------//
	*接收人
	//----------------------------------------------------------//
	*/
	/**
	 * 根据编号显示接收人信息
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-21
	 */
	public MsgReceive showMsgReceive(@Param("id")String id);
	
	/**
	 * 添加一消息接收人
	 * @param msgReceive
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-19
	 */
	public int addMsgReceive(MsgReceive msgReceive);
	/**
	 * 更新一消息接收人
	 * @param msgReceive
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-19
	 */
	public int updateMsgReceive(MsgReceive msgReceive);
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
	public int updateMsgReceiveBy(Map map);
	
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
	public int getMsgReceiveCountBy(Map map);
	/**
	 * 消息接收分页列表
	 * @param pageMap
	 * @return
	 */
	public List getMsgReceivePageList(PageMap pageMap);
	/**
	 * 消息接收人总人数
	 * @param pageMap
	 * @return
	 */
	public int getMsgReceiveCount(PageMap pageMap);

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
	 * @return java.util.List
	 * @throws
	 * @author zhanghonghui
	 * @date Aug 30, 2017
	 */
	public List getMsgReceiveListBy(Map map);
	
	/**
	 * 已经
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-25
	 */
	public List getReceiveUserPageList(PageMap pageMap);
	/**
	 * 已经发送用户统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-25
	 */
	public int getReceiveUserCount(PageMap pageMap);
	/**
	 * 查看用户
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-30
	 */
	public int getMsgReceiveUserReadCount(PageMap pageMap);
	/**
	 * 消息内容分页列表
	 * @param pageMap
	 * @return
	 */
	public List getMsgContentSimplePageList(PageMap pageMap);
	/**
	 * 消息内容分页合计
	 * @param pageMap
	 * @return
	 */
	public int getMsgContentSimplePageCount(PageMap pageMap);
}

