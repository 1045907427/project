/**
 * @(#)PageSettingServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-28 chenwei 创建版本
 */
package com.hd.agent.common.service.impl;

import java.util.List;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.dao.PageSettingMapper;
import com.hd.agent.common.dao.QueryMapper;
import com.hd.agent.common.dao.ShortcutMapper;
import com.hd.agent.common.model.PageSetting;
import com.hd.agent.common.model.Query;
import com.hd.agent.common.model.Shortcut;
import com.hd.agent.common.service.IPageSettingService;

/**
 * 
 * 页面个性化service实现类
 * @author chenwei
 */
public class PageSettingServiceImpl extends BaseServiceImpl implements
		IPageSettingService {
	
	private PageSettingMapper pageSettingMapper;
	
	private QueryMapper queryMapper;
	
	private ShortcutMapper shortcutMapper;
	
	public PageSettingMapper getPageSettingMapper() {
		return pageSettingMapper;
	}

	public void setPageSettingMapper(PageSettingMapper pageSettingMapper) {
		this.pageSettingMapper = pageSettingMapper;
	}
	
	public QueryMapper getQueryMapper() {
		return queryMapper;
	}

	public void setQueryMapper(QueryMapper queryMapper) {
		this.queryMapper = queryMapper;
	}
	
	public ShortcutMapper getShortcutMapper() {
		return shortcutMapper;
	}

	public void setShortcutMapper(ShortcutMapper shortcutMapper) {
		this.shortcutMapper = shortcutMapper;
	}

	@Override
	public boolean savePageCulumn(PageSetting pageSetting) throws Exception{
		int count = pageSettingMapper.getPageCulumnSet(pageSetting.getGrid(), pageSetting.getUserid(),pageSetting.getTablename());
		int i = 0;
		//判断是否存在表格的个性化配置
		//如果是 更新 否则新增
		if(count>0){
			i = pageSettingMapper.updagePageCulumn(pageSetting);
		}else{
			i = pageSettingMapper.savePageCulumn(pageSetting);
		}
		return i>0;
	}
	@Override
	public boolean deletePageCulumn(String grid, String userid, String name)
			throws Exception {
		int i = pageSettingMapper.deletePageCulumn(grid, userid, name);
		return i>0;
	}
	@Override
	public PageSetting getPageCulumn(String grid, String userid,String name)
			throws Exception {
		PageSetting pageSetting = pageSettingMapper.getPageCulumnInfo(grid, userid,name);
		return pageSetting;
	}

	@Override
	public boolean addQuery(Query query) throws Exception {
		int i = queryMapper.addQuery(query);
		return i>0;
	}

	@Override
	public List showUserQuery(String userid,String divid) throws Exception {
		List list = queryMapper.showUserQuery(userid,divid);
		return list;
	}

	@Override
	public boolean deleteQuery(String id) throws Exception {
		int i = queryMapper.deleteQuery(id);
		return i>0;
	}

	@Override
	public boolean addShortcut(Shortcut shortcut) throws Exception {
		return shortcutMapper.addShortcut(shortcut)>0;
	}

	@Override
	public List showShortcutList() throws Exception {
		SysUser sysUser = getSysUser();
		List list = shortcutMapper.showShortcutList(sysUser.getUserid());
		return list;
	}

	@Override
	public boolean deleteShortcut(String id) throws Exception {
		SysUser sysUser = getSysUser();
		return shortcutMapper.deleteShortcut(id, sysUser.getUserid())>0;
	}

	
}

