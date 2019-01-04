package com.hd.agent.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.NetLock;

/**
 * 网络互斥dao
 * @author chenwei
 */
public interface NetLockMapper {
	/**
	 * 添加网络互斥（给数据加锁）
	 * @param netLock
	 * @return
	 * @author chenwei 
	 * @date Mar 12, 2013
	 */
	public int addNetLock(NetLock netLock);
	/**
	 * 根据表名和加锁数据编号获取 该数据加锁的详细信息
	 * @param tablename
	 * @param lockid
	 * @return
	 * @author chenwei 
	 * @date Mar 12, 2013
	 */
	public NetLock getNetLock(@Param("tablename")String tablename,@Param("lockid")String lockid);
	/**
	 * 更新加锁时间
	 * @param tablename
	 * @param lockid
	 * @param userid
	 * @return
	 * @author chenwei 
	 * @date Mar 12, 2013
	 */
	public int updateNetLock(@Param("tablename")String tablename,@Param("lockid")String lockid,@Param("userid")String userid);
	/**
	 * 删除加锁数据（解锁）
	 * @param tablename
	 * @param lockid
	 * @return
	 * @author chenwei 
	 * @date Mar 13, 2013
	 */
	public int deleteNetLock(@Param("tablename")String tablename,@Param("lockid")String lockid);
	/**
	 * 清除超时的加锁数据
	 * @return
	 * @author chenwei 
	 * @date Mar 13, 2013
	 */
	public int clearNetLock();
	/**
	 * 获取网络互斥数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Mar 13, 2013
	 */
	public List showNetLockList(PageMap pageMap);
	/**
	 * 获取网络互斥列表数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Mar 13, 2013
	 */
	public int showNetLockCount(PageMap pageMap);
	/**
	 * 删除网络互斥（解锁）
	 * @param idList
	 * @return
	 * @author chenwei 
	 * @date Mar 13, 2013
	 */
	public int deleteNetLocks(@Param("id")String id);
}