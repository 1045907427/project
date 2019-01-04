/**
 * @(#)IPageSettingService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-28 chenwei 创建版本
 */
package com.hd.agent.common.service;

import java.util.List;

import com.hd.agent.common.model.PageSetting;
import com.hd.agent.common.model.Query;
import com.hd.agent.common.model.Shortcut;

/**
 * 
 * 页面个性化配置service
 * @author chenwei
 */
public interface IPageSettingService {
	/**
	 * 保存页面table表格个性化配置
	 * @param pageSetting
	 * @return
	 * @author chenwei 
	 * @date 2012-12-28
	 */
	public boolean savePageCulumn(PageSetting pageSetting) throws Exception;
	/**
	 * 删除表单样式
	 * @param grid
	 * @param userid
	 * @param name
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 11, 2013
	 */
	public boolean deletePageCulumn(String grid,String userid,String name) throws Exception;
	/**
	 * 根据表格编号跟用户编号获取表格个性化配置
	 * @param grid
	 * @param userid
	 * @param name
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-28
	 */
	public PageSetting getPageCulumn(String grid,String userid,String name) throws Exception;
	/**
	 * 添加通用查询记录
	 * @param query
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-12
	 */
	public boolean addQuery(Query query) throws Exception;
	/**
	 * 获取用户拥有的通用查询
	 * @param userid
	 * @param divid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-12
	 */
	public List showUserQuery(String userid,String divid) throws Exception;
	/**
	 * 删除通用查询
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-12
	 */
	public boolean deleteQuery(String id) throws Exception;
	/**
	 * 添加页面快捷方式
	 * @param shortcut
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 20, 2013
	 */
	public boolean addShortcut(Shortcut shortcut) throws Exception;
	/**
	 * 获取当前用户快捷方式列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 20, 2013
	 */
	public List showShortcutList() throws Exception;
	/**
	 * 删除页面快捷方式
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 20, 2013
	 */
	public boolean deleteShortcut(String id) throws Exception;
}

