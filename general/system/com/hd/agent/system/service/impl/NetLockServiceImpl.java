/**
 * @(#)NetLockServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 12, 2013 chenwei 创建版本
 */
package com.hd.agent.system.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.dao.NetLockMapper;
import com.hd.agent.system.dao.TableInfoMapper;
import com.hd.agent.system.model.NetLock;
import com.hd.agent.system.model.TableInfo;
import com.hd.agent.system.service.INetLockService;

/**
 * 
 * 网络互斥service实现类
 * @author chenwei
 */
public class NetLockServiceImpl extends BaseServiceImpl implements
		INetLockService {
	/**
	 * 网络互斥dao
	 */
	private NetLockMapper netLockMapper;
	/**
	 * 数据字典dao
	 */
	private TableInfoMapper tableInfoMapper;
	public NetLockMapper getNetLockMapper() {
		return netLockMapper;
	}

	public void setNetLockMapper(NetLockMapper netLockMapper) {
		this.netLockMapper = netLockMapper;
	}
	
	public TableInfoMapper getTableInfoMapper() {
		return tableInfoMapper;
	}

	public void setTableInfoMapper(TableInfoMapper tableInfoMapper) {
		this.tableInfoMapper = tableInfoMapper;
	}

	@Override
	public boolean doLockData(String tablename, String lockid,boolean isOverTime) throws Exception {
		//获取当前用户对象
		SysUser sysUser = getSysUser();
		NetLock netLock = netLockMapper.getNetLock(tablename, lockid);
		boolean flag = false;
		//判断该用户是否已经锁定过该数据
		if(null!=netLock){
			//判断锁定人是否用户自己
			if(netLock.getLockuserid().equals(sysUser.getUserid())){
				flag = netLockMapper.updateNetLock(tablename, lockid, sysUser.getUserid())>0;
			}else{	
				//锁定人不是用户自己 表示该数据已经被其他用户加锁
				flag = false;
			}
		}else{	
			//该数据在互斥表中不存在 由当前用户给他进行加锁
			NetLock newNetLock = new NetLock();
			TableInfo tableInfo = tableInfoMapper.showTableInfo(tablename);
			if(null!=tableInfo){
				newNetLock.setName(tableInfo.getTabledescname());
			}
			newNetLock.setTablename(tablename);
			newNetLock.setLockid(lockid);
			newNetLock.setLockuserid(sysUser.getUserid());
			newNetLock.setLockname(sysUser.getName());
			newNetLock.setLockdeptid(sysUser.getDepartmentid());
			newNetLock.setLockdeptname(sysUser.getDepartmentname());
			if(isOverTime){
				newNetLock.setIsovertime("1");
			}else{
				newNetLock.setIsovertime("0");
			}
			flag = netLockMapper.addNetLock(newNetLock)>0;
		}
		return flag;
	}

	@Override
	public boolean isLock(String tablename, String lockid) throws Exception {
		//获取当前用户对象
		SysUser sysUser = getSysUser();
		NetLock netLock = netLockMapper.getNetLock(tablename, lockid);
		boolean flag = true;
		if(null!=netLock){
			//加锁人是当前用户 则返回未加锁的状态
			if(netLock.getLockuserid().equals(sysUser.getUserid())){
				flag = false;
			}else{
				flag = true;
			}
		}else{
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean clearLock() throws Exception {
		return netLockMapper.clearNetLock()>0;
	}

	@Override
	public PageData showNetLockList(PageMap pageMap) throws Exception {
		PageData pageData = new PageData(netLockMapper.showNetLockCount(pageMap),netLockMapper.showNetLockList(pageMap),pageMap);
		return pageData;
	}

	@Override
	public boolean deleteNetLocks(String ids) throws Exception {
		if(null!=ids){
			String[] idArr = ids.split(",");
			for(String id : idArr){
				netLockMapper.deleteNetLocks(id);
			}
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean setUnLock(String tablename, String lockid) throws Exception {
		boolean flag = isLockEdit(tablename, lockid);
		return flag;
	}
	
	
}

