/**
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-28 chenwei 创建版本
 */
package com.hd.agent.common.dao;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.model.PageSetting;

/**
 * 页面个性化配置dao
 * @author chenwei
 */
public interface PageSettingMapper {
	/**
	 * 保存页面table表格的个性化配置
	 * @param pageSetting
	 * @return
	 * @author chenwei 
	 * @date 2012-12-28
	 */
	public int savePageCulumn(PageSetting pageSetting);
	/**
	 * 根据表格编号(grid)和用户编号(userid)判断是否存在
	 * @param grid
	 * @param userid
	 * @param tablename
	 * @return
	 * @author chenwei 
	 * @date 2012-12-28
	 */
	public int getPageCulumnSet(@Param("grid")String grid,@Param("userid")String userid,@Param("tablename")String tablename);
	/**
	 * 更新页面table表格的个性化配置
	 * @param pageSetting
	 * @return
	 * @author chenwei 
	 * @date 2012-12-28
	 */
	public int updagePageCulumn(PageSetting pageSetting);
	/**
	 * 根据表格编号跟用户编号获取表格个性化配置
	 * @param grid
	 * @param userid
	 * @param name
	 * @return
	 * @author chenwei 
	 * @date Jul 11, 2013
	 */
	public int deletePageCulumn(@Param("grid")String grid,@Param("userid")String userid,@Param("tablename")String name);
	/**
	 * 根据表格编号跟用户编号获取表格个性化配置
	 * @param grid
	 * @param userid
	 * @param tablename
	 * @return
	 * @author chenwei 
	 * @date 2012-12-28
	 */
	public PageSetting getPageCulumnInfo(@Param("grid")String grid,@Param("userid")String userid,@Param("tablename")String name);
	/**
	 * 根据表名或者其他编号删除表格配置信息
	 * @param tablename
	 * @return
	 * @author chenwei 
	 * @date Mar 8, 2013
	 */
	public int deletePageCulmnAll(@Param("tablename")String tablename);
	
}