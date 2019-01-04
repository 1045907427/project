/**
 * @(#)DataExceptionServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-22 chenwei 创建版本
 */
package com.hd.agent.system.service.impl;

import java.util.List;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.dao.DataExceptionMapper;
import com.hd.agent.system.model.DataException;
import com.hd.agent.system.model.DataExceptionOperate;
import com.hd.agent.system.service.IDataExceptionService;

/**
 * 
 * 数据异常规则service实现类
 * 
 * @author chenwei
 */
public class DataExceptionServiceImpl implements IDataExceptionService {

	private DataExceptionMapper dataExceptionMapper;

	public DataExceptionMapper getDataExceptionMapper() {
		return dataExceptionMapper;
	}

	public void setDataExceptionMapper(DataExceptionMapper dataExceptionMapper) {
		this.dataExceptionMapper = dataExceptionMapper;
	}

	@Override
	public boolean addDataException(DataException dataException)
			throws Exception {
		int i = dataExceptionMapper.addDataException(dataException);
		return i > 0;
	}

	@Override
	public PageData showDataExceptionList(PageMap pageMap) throws Exception {
		PageData pageData = new PageData(dataExceptionMapper.showDataExceptionCount(pageMap), dataExceptionMapper.showDataExceptionList(pageMap), pageMap);
		return pageData;
	}

	@Override
	public boolean addDataExceptionOperate(
			DataExceptionOperate dataExceptionOperate) throws Exception {
		int i = dataExceptionMapper.addDataExceptionOperate(dataExceptionOperate);
		return i>0;
	}

	@Override
	public List showDataExceptionOperateList(String dataExceptionId)
			throws Exception {
		List list = dataExceptionMapper.showDataExceptionOperateList(dataExceptionId);
		return list;
	}

	@Override
	public boolean deleteDataExceptionOperate(String id) throws Exception {
		int i = dataExceptionMapper.deleteDataExceptionOperate(id);
		return i>0;
	}

	@Override
	public boolean deleteDataException(String id) throws Exception {
		int i = dataExceptionMapper.deleteDataException(id);
		dataExceptionMapper.deleteOperateBydataExceptionID(id);
		return i>0;
	}

	@Override
	public DataException showDataExceptionInfo(String id) throws Exception {
		DataException dataException = dataExceptionMapper.showDataExceptionInfo(id);
		return dataException;
	}

	@Override
	public boolean editDataException(DataException dataException)
			throws Exception {
		int i = dataExceptionMapper.editDataException(dataException);
		return i>0;
	}

	@Override
	public boolean openDataException(String id) throws Exception {
		int i = dataExceptionMapper.setDataExceptionState(id,"1");
		return i>0;
	}

	@Override
	public boolean closeDataException(String id) throws Exception {
		int i = dataExceptionMapper.setDataExceptionState(id,"0");
		return i>0;
	}

	@Override
	public List<DataException> getDataExceptionByURL(String url) throws Exception {
		List list = dataExceptionMapper.getDataExceptionByURL(url);
		return list;
	}

}
