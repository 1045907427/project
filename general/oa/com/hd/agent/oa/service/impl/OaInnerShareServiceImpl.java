/**
 * @(#)OaInnerShareServiceImpl.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-11-24 limin 创建版本
 */
package com.hd.agent.oa.service.impl;

import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.oa.dao.OaInnerShareMapper;
import com.hd.agent.oa.model.OaInnerShare;
import com.hd.agent.oa.service.IOaInnerShareService;

/**
 * 内部分摊申请单Service 实现类
 * 
 * @author limin
 */
public class OaInnerShareServiceImpl extends BaseFilesServiceImpl implements
		IOaInnerShareService {

	private OaInnerShareMapper oaInnerShareMapper;

	public OaInnerShareMapper getOaInnerShareMapper() {
		return oaInnerShareMapper;
	}

	public void setOaInnerShareMapper(OaInnerShareMapper oaInnerShareMapper) {
		this.oaInnerShareMapper = oaInnerShareMapper;
	}

	@Override
	public int deleteOaInnerShare(String id) {

		return oaInnerShareMapper.deleteOaInnerShare(id);
	}

	@Override
	public int insertOaInnerShare(OaInnerShare share) {

		return oaInnerShareMapper.insertOaInnerShare(share);
	}

	@Override
	public OaInnerShare selectOaInnerShare(String id) {

		return oaInnerShareMapper.selectOaInnerShare(id);
	}

	@Override
	public int updateOaInnerShare(OaInnerShare share) {

		return oaInnerShareMapper.updateOaInnerShare(share);
	}
	
}

