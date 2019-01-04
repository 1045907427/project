/**
 * @(#)INoticeService.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-25 zhanghonghui 创建版本
 */
package com.hd.agent.message.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.message.model.MsgNotice;
import com.hd.agent.message.model.MsgNoticeread;

/**
 * 
 * 通知通告业务接口层
 * @author zhanghonghui
 */
public interface INoticeService {
	/**
	 * 添加一条通知通告
	 * @param msgNotice
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-25
	 */
	public boolean addMsgNotice(MsgNotice msgNotice) throws Exception;
	/**
	 * 添加一条通知通告
	 * @param msgNotice
	 * @param map 其他参数
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-10-26
	 */
	public boolean addMsgNotice(MsgNotice msgNotice,Map map) throws Exception;
	/**
	 * 更新一条通知通告
	 * @param msgNotice
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-25
	 */
	public boolean updateMsgNotice(MsgNotice msgNotice) throws Exception;
	/**
	 * 更新一条通知通告
	 * @param msgNotice
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-25
	 */
	public boolean updateMsgNotice(MsgNotice msgNotice,Map map) throws Exception;
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
	public boolean deleteMsgNoticeBy(Map map) throws Exception;
	/**
	 * 显示一条通知通告内容
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-25
	 */
	public MsgNotice showMsgNotice(String id) throws Exception;
	/**
	 * 显示公告分页列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	public PageData showMsgNoticePageList(PageMap pageMap) throws Exception;
	/**
	 * 公告统计
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-11-7
	 */
	public int getMsgNoticeCount(PageMap pageMap) throws Exception;
	
	/**
	 * 公告通知发布分布列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-31
	 */
	public PageData getMsgNoticePublishPageList(PageMap pageMap) throws Exception;
	
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
	 * isdataauth : 是否有数据权限判断，0为不需要，其他值为需要，默认为需要。<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-31
	 */
	public boolean updateMsgNoticeBy(Map map) throws Exception;
	
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
	public List getMsgNoticeList(Map queryMap) throws Exception;
	/**
	 * 审核通知公告
	 * @param ids
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-26
	 */
	public Map auditMsgNotice(String ids) throws Exception;
	/**
	 * 反审核通知公告
	 * @param ids
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-6-26
	 */
	public Map oppauditMsgNotice(String ids) throws Exception;

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
	public boolean addMsgNoticeread(MsgNoticeread msgNoticeread);
	
	/**
	 * 根据通知通告编号删除阅读人
	 * @param noticeid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-25
	 */
	public boolean deleteMsgNoticereadByNotice(String noticeid);

	/**
	 * 显示通知通告阅读人分页列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	public PageData showMsgNoticereadPageList(PageMap pageMap) throws Exception;
	
	/**
	 * 根据条件查询公告通知阅读数
	 * @param noticeid
	 * @param receiveuserid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-30
	 */
	public int getMsgNoticereadCountBy(String noticeid,String receiveuserid) throws Exception;

	/**
	 * 根据条件查询公告发布角色列表
	 * @param pageMap 基中list 为 SysUser
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author zhanghonghui
	 * @date Aug 31, 2017
	 */
	public PageData showMsgNoticeRangeRoleList(PageMap pageMap) throws Exception;
	/**
	 * 根据条件查询公告发布人员列表
	 * @param pageMap 基中list 为 SysUser
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author zhanghonghui
	 * @date Aug 31, 2017
	 */
	public PageData showMsgNoticeRangeUserList(PageMap pageMap) throws Exception;

	/**
	 * -------------------------------------------------------
	 **********************************************************
	 *其他方法
	 **********************************************************
	 *---------------------------------------------------------
	 */

	/**
	 * 设置部门、用户、权限名称
	 * @param item
	 * @param shownum 显示个数，小于等于0表示不限制
	 * @param dotsign 名称后缀符号
	 * @return void
	 * @throws
	 * @author zhanghonghui
	 * @date Sep 05, 2017
	 */
	public void setDeptUserRoleName(MsgNotice item,int shownum,String dotsign) throws Exception;
}

