/**
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-2-4 chenwei 创建版本
 */
package com.hd.agent.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.ReferWindow;
import com.hd.agent.system.model.ReferWindowColumn;

/**
 * 参照窗口dao
 * @author chenwei
 */
public interface ReferWindowMapper {
	/**
	 * 根据sql获取参照窗口数据
	 * @param sql
	 * @return
	 * @author chenwei 
	 * @date 2013-2-5
	 */
	public List getReferWindowData(PageMap pageMap);
	/**
	 * 获取参照窗口的全部数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Feb 27, 2013
	 */
	public List getReferWindowAllData(PageMap pageMap);
	/**
	 * 获取参照窗口一定数量的数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 5, 2013
	 */
	public List getReferWindowDataInCount(PageMap pageMap);
	/**
	 * 获取参照窗口的数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2013-2-6
	 */
	public int getReferWindowDataCount(PageMap pageMap);
	/**
	 * 添加参照窗口基本信息
	 * @param referWindow
	 * @return
	 * @author chenwei 
	 * @date 2013-2-18
	 */
	public int addReferWindowBaseInfo(ReferWindow referWindow);
	/**
	 * 添加参照窗口列明细
	 * @param referWindowColumn
	 * @return
	 * @author chenwei 
	 * @date 2013-2-18
	 */
	public int addReferWindowColumn(ReferWindowColumn referWindowColumn);
	/**
	 * 获取参照窗口列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2013-2-18
	 */
	public List showReferWindowList(PageMap pageMap);
	/**
	 * 获取参照窗口数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2013-2-18
	 */
	public int showReferWindowCount(PageMap pageMap);
	/**
	 * 获取参照窗口编号数量
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2013-2-18
	 */
	public int checkReferWindowID(@Param("id")String id);
	/**
	 * 删除参照窗口
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2013-2-19
	 */
	public int deleteReferWindow(@Param("id")String id);
	/**
	 * 删除参照窗口字段列表
	 * @param referWid
	 * @return
	 * @author chenwei 
	 * @date 2013-2-25
	 */
	public int deleteReferWindowColumm(@Param("referwinid")String referwinid);
	/**
	 * 启用参照窗口
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2013-2-19
	 */
	public int openReferWindow(@Param("id")String id);
	/**
	 * 禁用参照窗口
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2013-2-19
	 */
	public int closeReferWindow(@Param("id")String id);
	/**
	 * 获取参照窗口基本信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2013-2-19
	 */
	public ReferWindow getReferWindow(@Param("id")String id);
	/**
	 * 获取参照窗口列表信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2013-2-19
	 */
	public List getReferWindowColumnList(@Param("id")String id);
	/**
	 * 修改参照窗口
	 * @param referWindow
	 * @return
	 * @author chenwei 
	 * @date 2013-2-25
	 */
	public int editReferWindow(ReferWindow referWindow);
	/**
	 * 获取参照窗口列表
	 * @param map
	 * @return
	 * @author chenwei 
	 * @date 2013-2-25
	 */
	public List getReferWindowList(Map map);
}