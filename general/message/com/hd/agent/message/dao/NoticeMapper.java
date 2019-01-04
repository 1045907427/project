/**
 * @(#)NoticeMapper.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-25 zhanghonghui 创建版本
 */
package com.hd.agent.message.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.message.model.MsgNotice;
import com.hd.agent.message.model.MsgNoticeread;

/**
 * 公告通知数据接口层
 * 
 * @author zhanghonghui
 */
public interface NoticeMapper {
	/**
	 * 添加一条公告通知内容
	 * @param msgNotice
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-25
	 */
	public int insertMsgNotice(MsgNotice msgNotice);
	/**
	 * 更新一条公告通知内容
	 * @param msgNotice
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-25
	 */
	public int updateMsgNotice(MsgNotice msgNotice);
	/**
	 * 更新公告通知<br/>
	 * map中参数：<br/>
	 * 可更新参数：<br/>
	 * type : 公告类型<br/>
	 * form : 格式:1普通格式，2MHT格式,3超级链接<br/>
	 * istop : 是否置顶1是0否<br/>
	 * topday : 置顶天数<br/>
	 * ismsg : 是否内部短信提醒1是0否<br/>
	 * state : 4新增/3暂存/2保存/1启用/0禁用<br/>
	 * audstate : 审核状态0未审核1审核通过2审核不通过<br/>
	 * modifyuserid : 修改人编号<br/>
	 * modifytime : 修改人时间<br/>
	 * delflag : 删除标志:1未删除0已删除<br/>
	 * deltime : 删除时间<br/>
	 * 条件参数：<br/>
	 * id : 编号<br/>
	 * idarrs : 多编号字符串，格式：1,2,3<br/>
	 * wadduserid : 添加用户编号<br/>
	 * wstate : 状态<br/>
	 * wstatearr : 多状态,格式：1,2,3<br/>
	 * wisaud : 是否审核<br/>
	 * waudstate : 审核状态<br/>
	 * wmodifyuserid : 修改人编号<br/>
	 * wdelflag : 删除标志<br/>
	 * @param map
	 * @param state
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-31
	 */
	public int updateMsgNoticeBy(Map map);
	/**
	 * 直接删除公告通知<br/>
	 * map中的参数<br/>
	 * id : 编号<br/> 
	 * idarrs:多编号字符串，格式如：1,2,3<br/>
	 * wadduserid : 添加用户编号<br/>
	 * wstate : 状态<br/>
	 * wstatearr : 包含于多状态,格式：1,2,3<br/>
	 * wnotstatearr : 不包含于多状态,格式：1,2,3<br/>
	 * wisaud : 是否审核<br/>
	 * waudstate : 审核状态<br/>
	 * wdelflag : 删除标志<br/>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-25
	 */
	public int deleteMsgNoticeBy(Map map) throws Exception;
	
	/**
	 * 获取一条公告通知内容
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-25
	 */
	public MsgNotice showMsgNotice(@Param("id") String id);
	
	/**
	 * 获取列表数据<br/>
	 * queryMap参数<br/>
	 * idarrs:多编号字符串，格式如：1,2,3<br/>
	 * adduserid : 添加用户编号<br/>
	 * withrecvuser:为1时列出接收人，按角色查看，按部门查看，默认不显示<br/>
	 * withcontent:为1时列出内容<br/>
	 * withattchment:为1时列出附件<br/>
	 * delflag : 是不删除,1未删除，0删除<br/>
	 * state : 状态，4新增/3暂存/2保存/1启用/0禁用 <br/>
	 * overTopday : 置顶失效<br/>
	 * overEndday : 已过有效期的数据
	 * @param queryMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-31
	 */
	public List getMsgNoticeList(Map queryMap);
	/**
	 * 公告通知分页列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	public List getMsgNoticePageList(PageMap pageMap);
	/**
	 * 根据pageMap 统计总数
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	public int getMsgNoticeCount(PageMap pageMap);
	/**
	 * 公告通知发布分布列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-31
	 */
	public List getMsgNoticePublishPageList(PageMap pageMap);
	/**
	 * 公告通知发布，根据pageMap 统计总数
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	public int getMsgNoticePublishCount(PageMap pageMap);
	
	/*
	* //------------------------------------------------------//
	* 公告通知阅读人
	* //------------------------------------------------------//
	*/
	
	/**
	 * 添加公告通知阅读人
	 */
	public int insertMsgNoticeread(MsgNoticeread msgNoticeread);
	/**
	 * 更新公告通知阅读人
	 * @param msgNoticeread
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-25
	 */
	public int updateMsgNoticeread(MsgNoticeread msgNoticeread);
	/**
	 * 根据公告通知编号，删除公告通知接收人
	 * @param noticeid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-25
	 */
	public int deleteMsgNoticereadByNotice(@Param("noticeid") String noticeid);
	/**
	 * 公告通知阅读人分页列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	public List getMsgNoticereadPageList(PageMap pageMap);
	/**
	 * 公告通知阅读人总数
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	public int getMsgNoticereadCount(PageMap pageMap);
	/**
	 * 根据条件查询公告通知阅读数
	 * @param noticeid
	 * @param receiveuserid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-30
	 */
	public int getMsgNoticereadCountBy(@Param("noticeid")String noticeid,@Param("receiveuserid")String receiveuserid);
}

