/**
 * @(#)IReferWindowService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-2-4 chenwei 创建版本
 */
package com.hd.agent.system.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.ReferWindow;
import com.hd.agent.system.model.ReferWindowColumn;

/**
 * 
 * 参照窗口相关service
 * 
 * @author chenwei
 */
public interface IReferWindowService {
	/**
	 * 根据sql获取参照窗口数据
	 * 
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2013-2-5
	 */
	public List getReferWindowBySQL(PageMap pageMap) throws Exception;
	/**
	 * 添加参照窗口
	 * @param referWindow
	 * @param referWColumnList
	 * @param referWParamList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-18
	 */
	public boolean addReferWindow(ReferWindow referWindow,
			List<ReferWindowColumn> referWColumnList) throws Exception;
	/**
	 * 获取参照窗口
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-18
	 */
	public PageData showReferWindowList(PageMap pageMap) throws Exception;
	/**
	 * 验证参照窗口编号是否重复。
	 * true不重复false重复
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-18
	 */
	public boolean checkReferWindowID(String id) throws Exception;
	/**
	 * 删除参照窗口
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-19
	 */
	public boolean deleteReferWindow(String id) throws Exception;
	/**
	 * 启用参照窗口
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-19
	 */
	public boolean openReferWindow(String id) throws Exception;
	/**
	 * 禁用参照窗口
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-19
	 */
	public boolean closeReferWindow(String id) throws Exception;
	/**
	 * 获取参照窗口信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-19
	 */
	public Map showReferWindowInfo(String id) throws Exception;
	/**
	 * 获取参照窗口的数据
	 * @param pageMap
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-19
	 */
	public PageData getReferWindowData(PageMap pageMap,String id) throws Exception;
	/**
	 * 获取下拉参照控件数据
	 * @param map
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 4, 2013
	 */
	public List getReferWindowDataByReferid(Map map) throws Exception;
	/**
	 * 获取参照窗口全部数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Feb 27, 2013
	 */
	public Map getReferWindowWidget(PageMap pageMap,String id) throws Exception;
	/**
	 * 获取参照窗口下的列明细
	 * @param referWid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-20
	 */
	public List getReferWindowColumn(String referWid) throws Exception;
	/**
	 * 修改参照窗口
	 * @param referWindow
	 * @param referWColumnList
	 * @param referWParamList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-25
	 */
	public boolean editReferWindow(ReferWindow referWindow,
			List<ReferWindowColumn> referWColumnList) throws Exception;
	/**
	 * 获取参照窗口列表
	 * @param map
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-25
	 */
	public List getReferWindowList(Map map) throws Exception;
	/**
	 * 根据参照窗口名称和值获取数据的名称
	 * @param referWname			参照窗口名称
	 * @param value					需要转换的值
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 19, 2013
	 */
	public String getReferWindowNameByValue(String referWname,String value) throws Exception;
	
	/**
	 * 根据参照窗口名称和名称获取数据的名称
	 * @param referWname
	 * @param name
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 19, 2013
	 */
	public String getReferWindowNameByName(String referWname,String name)throws Exception;
}
