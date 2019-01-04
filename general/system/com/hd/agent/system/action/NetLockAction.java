/**
 * @(#)NetLockAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 12, 2013 chenwei 创建版本
 */
package com.hd.agent.system.action;

import java.util.Map;

import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.system.service.INetLockService;

/**
 * 
 * 网络互斥action
 * @author chenwei
 */
public class NetLockAction extends BaseAction {
	
	private INetLockService netLockService;

	public INetLockService getNetLockService() {
		return netLockService;
	}

	public void setNetLockService(INetLockService netLockService) {
		this.netLockService = netLockService;
	}
	/**
	 * 显示网络互斥解锁页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 13, 2013
	 */
	public String showNetLockPage() throws Exception{
		return "success";
	}
	/**
	 * 获取网络互斥数据列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 13, 2013
	 */
	public String showNetLockList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = netLockService.showNetLockList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 删除加锁（解锁）
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 13, 2013
	 */
	@UserOperateLog(key="NetLock",type=4)
	public String deleteNetLocks() throws Exception{
		String ids = request.getParameter("ids");
		boolean flag = netLockService.deleteNetLocks(ids);
		addJSONObject("flag", flag);
		
		addLog("给异常数据解锁 编号:"+ids, flag);
		return "success";
	}
	/**
	 * 判断数据是否可以操作。如果能操作 则给数据加锁
	 * true 可以操作未加锁 false不可以操作 加锁
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 3, 2013
	 */
	public String isDoLockData() throws Exception{
		String tablename = request.getParameter("tname");
		String lockid = request.getParameter("id");
		boolean flag = lockData(tablename, lockid);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 判断用户是否可以操作
	 * 如果可以操作 解锁该数据
	 * true 可以操作 false不可以操作
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 3, 2013
	 */
	public String unLockData() throws Exception{
		String tablename = request.getParameter("tname");
		String lockid = request.getParameter("id");
		boolean flag = isLockEdit(tablename, lockid);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 判断数据是否被加锁
	 * true 已被加锁，不可以操作 false未加锁 可以操作
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 3, 2013
	 */
	public String isLockData() throws Exception{
		String tablename = request.getParameter("tname");
		String lockid = request.getParameter("id");
		boolean flag = isLock(tablename, lockid);
		addJSONObject("flag", flag);
		return "success";
	}
}

