/**
 * @(#)AttachMapper.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-10-10 limin 创建版本
 */
package com.hd.agent.activiti.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 流程附件
 * 
 * @author limin
 */
public interface AttachMapper {

	/**
	 * 
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2014-10-10
	 */
	public int addAttach(Map map);
	
	/**
	 * 
	 * @param id
	 * @return
	 * @author limin 
	 * @date 2014-10-10
	 */
	public int deleteAttach(@Param("attachid")String attachid);
	
	/**
	 * 获取附件一览
	 * @param processid
	 * @param commentid
     * @param userid
	 * @return
	 * @author limin 
	 * @date 2014-10-10
	 */
	public List<Map> selectAttachList(@Param("processid")String processid, @Param("commentid")String commentid, @Param("userid")String userid);
}

