/**
 * @(#)MsgFileDistributeMapper.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-9-12 zhanghonghui 创建版本
 */
package com.hd.agent.message.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.message.model.FileDistrib;
import com.hd.agent.message.model.FileDistribReceive;

/**
 * 
 * 
 * @author zhanghonghui
 */
public interface FileDistribMapper {

	/**
	 * 添加传阅件
	 * @param fileDistribution
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-13
	 */
	public int insertFileDistrib(FileDistrib fileDistrib);
	/**
	 * 修改传阅件
	 * @param fileDistrib
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-13
	 */
	public int updateFileDistrib(FileDistrib fileDistrib);
	/**
	 * 显示传阅件
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-16
	 */
	public FileDistrib showPureFileDistrib(@Param("id")String id);
	/**
	 * 显示传阅件
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-16
	 */
	public FileDistrib showFileDistrib(Map map);
	/**
	 * 接收时的分页列表<br/>
	 * pageMap  condition参数<br/>
	 * isshowcuruserrc:1，显示用户统计数<br/>
	 * curuserid ：isshowcuruserrc启用时，显示当前用户统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-14
	 */
	public List getFileDistribReceivePageList(PageMap pageMap);
	
	/**
	 * 接收时的分页列表分页计算
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-14
	 */
	public int getFileDistribReceivePageCount(PageMap pageMap);
	/**
	 * 发布时的分页列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-14
	 */
	public List getFileDistribPublishPageList(PageMap pageMap);
	
	/**
	 * 发布时的分页列表分页计算
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-14
	 */
	public int getFileDistribPublishPageCount(PageMap pageMap);
	
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
	public List getFileDistribList(Map queryMap);
	
	/**
	 * 更新传阅件<br/>
	 * map中参数：<br/>
	 * 可更新参数：<br/>
	 * type : 公告类型<br/>
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
	public int updateFileDistribBy(Map map);
	
	/**
	 * 直接删除传阅件<br/>
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
	public int deleteFileDistribBy(Map map);

	/**
	 * 添加传阅读阅读记录
	 * @param fileDistribReceive
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-23
	 */
	public int insertFileDistribReceive(FileDistribReceive fileDistribReceive);
	/**
	 * 根据条件查询议传阅件
	 * @param fid
	 * @param receiveuserid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-30
	 */
	public int getFileDistribReceiveCountBy(@Param("fid")String fid,@Param("receiveuserid")String receiveuserid);
	/**
	 * 更新传阅件阅读记录
	 * @param fid
	 * @param receiveuserid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-23
	 */
	public int updateFileDistribReceiveRead(@Param("fid")String fid,@Param("receiveuserid")String receiveuserid);
	/**
	 * 传阅件阅读情况列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-25
	 */
	public List getFileDistribReadPageList(PageMap pageMap);
	/**
	 * 传阅件阅读情况分页计算
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-25
	 */
	public int getFileDistribReadCount(PageMap pageMap);
}

