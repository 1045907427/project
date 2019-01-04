/**
 * @(#)INetLockService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 12, 2013 chenwei 创建版本
 */
package com.hd.agent.system.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 网络互斥service接口
 * @author chenwei
 */
public interface INetLockService {
	
	/**
	 * 给数据加锁
	 * @param tablename 表名
	 * @param lockid	需要加锁的数据编号
	 * @param isOverTime 是否超时true是false否 选择超时后，系统将会自动给该数据解锁
	 * @return true:该数据被当前用户加锁 false:该数据被其他用户加锁
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 12, 2013
	 */
	public boolean doLockData(String tablename,String lockid,boolean isOverTime) throws Exception;
	/**
	 * 判断数据对当前用户是否已经加锁
	 * @param tablename
	 * @param lockid
	 * @return true是false否
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 12, 2013
	 */
	public boolean isLock(String tablename,String lockid) throws Exception;
	/**
	 * 判断数据是否可以操作。
	 * 如果加锁人是自己，则对该数据进行解锁
	 * true可以操作。false不可以操作
	 * @param tablename
	 * @param lockid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 29, 2013
	 */
	public boolean setUnLock(String tablename,String lockid) throws Exception;
	/**
	 * 清楚超时的加锁数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 13, 2013
	 */
	public boolean clearLock() throws Exception;
	/**
	 * 获取网络互斥分页数据列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 13, 2013
	 */
	public PageData showNetLockList(PageMap pageMap) throws Exception;
	/**
	 * 删除加锁（解锁）
	 * @param ids
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 13, 2013
	 */
	public boolean deleteNetLocks(String ids) throws Exception;
}

