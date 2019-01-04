package com.hd.agent.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.model.Shortcut;

/**
 * 快捷操作dao
 * @author chenwei
 */
public interface ShortcutMapper {
	/**
	 * 添加页面快捷方式
	 * @param shortcut
	 * @return
	 * @author chenwei 
	 * @date Mar 20, 2013
	 */
	public int addShortcut(Shortcut shortcut);
	/**
	 * 获取用户快捷方式列表
	 * @param userid
	 * @return
	 * @author chenwei 
	 * @date Mar 20, 2013
	 */
	public List showShortcutList(@Param("userid")String userid);
	/**
	 * 删除页面快捷方式
	 * @param id
	 * @param userid
	 * @return
	 * @author chenwei 
	 * @date Mar 20, 2013
	 */
	public int deleteShortcut(@Param("id")String id,@Param("userid")String userid);
}