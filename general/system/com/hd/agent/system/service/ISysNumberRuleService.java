/**
 * @(#)ISysNumberRuleService.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-26 panxiaoxiao 创建版本
 */
package com.hd.agent.system.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.system.model.SysNumberRule;

/**
 * 单据编号规则的业务逻辑的接口
 * 
 * @author panxiaoxiao
 */
public interface ISysNumberRuleService {

	/**
	 * 显示单据编号规则列表
	 * @param numberid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-26
	 */
	public List showSysNumberRuleList(String numberid) throws Exception;
	
	/**
	 * 显示单据编号规则详情信息 
	 * @param numberruleid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-11
	 */
	public SysNumberRule showSysNumberRuleInfo(String numberruleid)throws Exception;
	
	/**
	 * 添加单据编号规则
	 * @param sysNumber
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-26
	 */
	public boolean addSysNumberRule(SysNumberRule sysNumberRule) throws Exception;
	
	/**
	 * 修改单据编号规则
	 * @param sysNumber
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-26
	 */
	public boolean editSysNumberRule(SysNumberRule sysNumberRule) throws Exception;
	
	/**
	 * 删除单据编号规则
	 * @param numberruleid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-26
	 */
	public boolean deleteSysNumberRule(String numberruleid) throws Exception; 
	
	/**
	 * 获取sysNumber预览效果
	 * @param numberid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-14
	 */
	public String getSysNumberPreview(String numberid)throws Exception;
	
	/**
	 * 提供自动生成方法
	 * 以map形式返回结果<br/>
	 * map返回结果格式:<br/>
	 * {'sysNumberCode':billno数值,'autoCreate':'1是或者2否','modifyFlag':'1允许或者2不允许'}<br/>
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-17
	 */
	public Map setAutoCreateSysNumbderForeign(Object obj,String tablename)throws Exception;
	
	/**
	 * 根据表名tablename获取是否允许修改(接口)
	 * @param tablename
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-22
	 */
	public String isModifyFlagForeign(String tablename)throws Exception;
}

