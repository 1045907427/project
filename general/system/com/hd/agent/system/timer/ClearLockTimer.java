/**
 * @(#)LockTimer.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 13, 2013 chenwei 创建版本
 */
package com.hd.agent.system.timer;

import java.util.Date;

import com.hd.agent.system.service.INetLockService;

/**
 * 
 * 网络互斥解锁定时器
 * @author chenwei
 */
public class ClearLockTimer {
	
	private INetLockService netLockService;

	public INetLockService getNetLockService() {
		return netLockService;
	}

	public void setNetLockService(INetLockService netLockService) {
		this.netLockService = netLockService;
	}
	/**
	 * 清除超时的加锁数据
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 13, 2013
	 */
	public void run() throws Exception{
		System.out.println(new Date());
		netLockService.clearLock();
	}
}

