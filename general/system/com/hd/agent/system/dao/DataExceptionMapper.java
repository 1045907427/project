/**
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-01-22 chenwei 创建版本
 */
package com.hd.agent.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.DataException;
import com.hd.agent.system.model.DataExceptionOperate;

/**
 * 数据异常规则dao
 * @author chenwei
 */
public interface DataExceptionMapper {
	/**
	 * 添加数据异常规则
	 * @param dataException
	 * @return
	 * @author chenwei 
	 * @date 2013-1-23
	 */
	public int addDataException(DataException dataException);
	/**
	 * 获取数据异常数据列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2013-1-23
	 */
	public List showDataExceptionList(PageMap pageMap);
	/**
	 * 获取数据异常数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2013-1-23
	 */
	public int showDataExceptionCount(PageMap pageMap);
	/**
	 * 添加数据异常规则对应操作功能
	 * @param dataExceptionOperate
	 * @return
	 * @author chenwei 
	 * @date 2013-1-24
	 */
	public int addDataExceptionOperate(DataExceptionOperate dataExceptionOperate);
	/**
	 * 获取数据异常骨子下对应操作功能列表
	 * @param dataExceptionID
	 * @return
	 * @author chenwei 
	 * @date 2013-1-24
	 */
	public List showDataExceptionOperateList(@Param("dataExceptionID")String dataExceptionID);
	/**
	 * 删除数据异常规则下对应的操作功能
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2013-1-24
	 */
	public int deleteDataExceptionOperate(@Param("id")String id);
	/**
	 * 删除数据异常规则
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2013-1-25
	 */
	public int deleteDataException(@Param("id")String id);
	/**
	 * 删除数据异常规则下的操作功能
	 * @param dataExceptionID
	 * @return
	 * @author chenwei 
	 * @date 2013-1-25
	 */
	public int deleteOperateBydataExceptionID(@Param("dataExceptionID")String dataExceptionID);
	/**
	 * 获取数据异常规则详细信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2013-1-25
	 */
	public DataException showDataExceptionInfo(@Param("id")String id);
	/**
	 * 修改数据异常规则
	 * @param dataException
	 * @return
	 * @author chenwei 
	 * @date 2013-1-25
	 */
	public int editDataException(DataException dataException);
	/**
	 * 启用停用数据异常规则
	 * @param id
	 * @param state
	 * @return
	 * @author chenwei 
	 * @date 2013-1-25
	 */
	public int setDataExceptionState(@Param("id")String id,@Param("state")String state);
	/**
	 * 根据url地址获取数据异常规则信息列表
	 * @param url
	 * @return
	 * @author chenwei 
	 * @date 2013-1-25
	 */
	public List getDataExceptionByURL(@Param("url")String url);
}