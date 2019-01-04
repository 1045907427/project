/**
 * @(#)EventHandlerMapper.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015-1-2 limin 创建版本
 */
package com.hd.agent.activiti.dao;

import java.util.List;
import java.util.Map;

import com.hd.agent.activiti.model.HandlerLog;
import com.hd.agent.common.util.PageMap;
import org.apache.ibatis.annotations.Param;

import com.hd.agent.activiti.model.EventHandler;

/**
 * Event Handler Mapper
 * 
 * @author limin
 */
public interface EventHandlerMapper {

	/**
	 * 
	 * @param param
	 * @return
	 * @author limin 
	 * @date 2015-1-2
	 */
	public List<EventHandler> selectEventHandlerByTask(Map param);
	
	/**
	 * 
	 * @param handler
	 * @return
	 * @author limin 
	 * @date 2015-1-2
	 */
	public int insertEventHandler(EventHandler handler);
	
	/**
	 * 
	 * @param definitionkey
     * @param definitionid
	 * @param taskkey
	 * @param event
	 * @return
	 * @author limin 
	 * @date 2015-1-5
	 */
	public int deleteEventHandlerByTask(@Param("definitionkey")String definitionkey, @Param("definitionid")String definitionid, @Param("taskkey")String taskkey, @Param("event")String event);

    /**
     * 将旧版本的handler设定clone到新版本
     * @param param
     * @return
     * @author limin
     * @date May 18, 2015
     */
    public int doCloneOldVerson2NewVersion(Map param);

	/**
	 * 插入handle执行log
	 * @param log
	 * @return
	 * @author limin
	 * @date Apr 13, 2016
     */
	public int insertHandlerLog(HandlerLog log);

	/**
	 * 根据taskkeys查询EventHandlers
	 * @param definitionid
	 * @param taskkeys
	 * @return
	 * @author limin
	 * @date Apr 14, 2016
	 */
	public List<EventHandler> selectExecutedHandlers(@Param("definitionid")String definitionid, @Param("taskkeys")List<String> taskkeys);

	/**
	 * 根据complete handler查询对应的create handler
	 * @param handlers
	 * @return
	 * @author limin
	 * @date Apr 14, 2016
	 */
	public List<Map> selectOppositeHandlerByCompleteHandler(/*@Param("definitionid")String definitionid, */@Param("handlers")List<String> handlers);

	/**
	 * 查询 Handler 日志
	 * @param map
	 * @return
	 * @author limin
	 * @date Apr 20, 2016
	 */
	public List<HandlerLog> selectHandlerLogList(PageMap map);

	/**
	 * 查询 Handler日志记录数
	 * @param map
	 * @return
	 * @author limin
	 * @date Apr 20, 2016
	 */
	public int selectHandlerLogListCount(PageMap map);

	/**
	 *
	 * @param url
	 * @param handlertype
	 * @return
	 * @author limin
	 * @date Apr 19, 2016
     */
	public List<Map> selectHanderItemsByUrl(@Param("url")String url, @Param("handlertype")String handlertype);

	/**
	 *
	 * @param id
	 * @return
	 * @author limin
	 * @date Apr 19, 2016
	 */
	public HandlerLog selectHandlerLogById(@Param("id")String id);

	/**
	 * 获取所有的handler list
	 * @return
	 * @author limin
	 * @date Apr 20, 2016
	 */
	public List<Map> selectListenerClazzes();
}

