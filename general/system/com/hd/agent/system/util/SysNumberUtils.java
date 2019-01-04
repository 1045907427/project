/**
 * @(#)SysNumber.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年10月9日 chenwei 创建版本
 */
package com.hd.agent.system.util;

import java.util.Map;

import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.service.ISysNumberService;

/**
 * 
 * 获取单据编号（同步方法，防止生成的单据号重复）
 * @author chenwei
 */
public class SysNumberUtils {
	/**
	 * 生成单据编号
	 * @param obj			实体类
	 * @param billcode		单据编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月9日
	 */
	public synchronized static Map getSysNumber(Object obj, String billcode) throws Exception{
		ISysNumberService sysNumberService = (ISysNumberService) SpringContextUtils.getBean("sysNumberService");
		Map sysNumberCodeRetMap=sysNumberService.autoCreateSysNumbderForeign(obj, billcode);
		return sysNumberCodeRetMap;
	}
}

