/**
 * @(#)IDataExceptionService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-22 chenwei 创建版本
 */
package com.hd.agent.system.service;

import java.util.List;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.DataException;
import com.hd.agent.system.model.DataExceptionOperate;

/**
 * 
 * 数据异常规则相关service方法
 * @author chenwei
 */
public interface IDataExceptionService {
	
	/**
	 * 添加数据异常规则
	 * @param dataException
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-23
	 */
	public boolean addDataException(DataException dataException) throws Exception;
	/**
	 * 获取数据异常分页数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-23
	 */
	public PageData showDataExceptionList(PageMap pageMap) throws Exception;
	/**
	 * 添加数据异常规则对应功能
	 * @param dataExceptionOperate
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-24
	 */
	public boolean addDataExceptionOperate(DataExceptionOperate dataExceptionOperate) throws Exception;
	/**
	 * 获取数据异常规则下对应的操作功能列表
	 * @param dataExceptionId
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-24
	 */
	public List showDataExceptionOperateList(String dataExceptionId) throws Exception;
	
	/**
	 * 删除数据异常规则下对应的操作功能
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-24
	 */
	public boolean deleteDataExceptionOperate(String id) throws Exception;
	/**
	 * 删除数据异常规则
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-25
	 */
	public boolean deleteDataException(String id) throws Exception;
	/**
	 * 获取数据异常规则信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-25
	 */
	public DataException showDataExceptionInfo(String id) throws Exception;
	/**
	 * 修改数据异常规则
	 * @param dataException
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-25
	 */
	public boolean editDataException(DataException dataException) throws Exception;
	/**
	 * 启用数据异常规则
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-25
	 */
	public boolean openDataException(String id) throws Exception;
	/**
	 * 停用数据异常规则
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-25
	 */
	public boolean closeDataException(String id) throws Exception;
	/**
	 * 根据url获取数据异常规则
	 * @param url
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-25
	 */
	public List<DataException> getDataExceptionByURL(String url) throws Exception;
}

