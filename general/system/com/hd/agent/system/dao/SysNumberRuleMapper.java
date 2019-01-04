/**
 * @(#)SysNumberRuleMapper.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-26 panxiaoxiao 创建版本
 */
package com.hd.agent.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.system.model.SysNumberRule;

/**
 * 单据编号表相关数据操作
 * 
 * @author panxiaoxiao
 */
public interface SysNumberRuleMapper {

	/**
	 * 根据条件获取单据编号规则列表
	 * @param numberid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-27
	 */
	public List getSysNumberRuleList(@Param("numberid")String numberid);
	
	/**
	 * 修改单据编号规则
	 * @param sysNumber
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-26
	 */
	public int editSysNumberRule(SysNumberRule sysNumberRule);
	
	/**
	 * 添加单据编号规则
	 * @param sysNumber
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-26
	 */
	public int addSysNumberRule(SysNumberRule sysNumberRule);
	
	/**
	 * 删除单据编号规则
	 * @param numberruleid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-26
	 */
	public int deleteSysNumberRule(@Param("numberruleid")String numberruleid);
	
	/**
	 * 根据numberid，删除单据编号规则
	 * @param numberid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-7
	 */
	public int deleteSysNumberRuleByNumID(@Param("numberid")String numberid);
	
	/**
	 * 获取单据编码规则详情信息 
	 * @param numberruleid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-11
	 */
	public SysNumberRule getSysNumberRuleInfo(@Param("numberruleid")String numberruleid);
}

