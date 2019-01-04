/**
 * @(#)SysNumberMapper.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-25 panxiaoxiao 创建版本
 */
package com.hd.agent.system.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.SysNumber;
import com.hd.agent.system.model.SysNumberRule;
import com.hd.agent.system.model.SysNumberSerial;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 单据编码表数据库相关操作
 * 
 * @author panxiaoxiao
 */
public interface SysNumberMapper {
	
	/**
	 * 获取单据名称列表
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-28
	 */
	public List getSysNumberBillNameList();
	/**
	 * 根据条件，获取单据编号列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-25
	 */
	public List getSysNumberPageListByBcode(PageMap pageMap);
	
	/**
	 * 根据条件获取公共代码数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-26
	 */
	public int getSysNumberCount(PageMap pageMap);
	
	/**
	 * 根据编号查询单据编码是否存在
	 * @param numberid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-27
	 */
	public SysNumber getSysNumberInfo(@Param("numberid")String numberid);
	
	/**
	 * 获取单据编号的流水号相关数据
	 * @param numberid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-17
	 */
	public SysNumber getSysNumberSerail(@Param("numberid")String numberid);
	
	/**
	 * 添加单据编码
	 * @param sysNumber
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-25
	 */
	public int insertSysNumber(SysNumber sysNumber);
	
	/**
	 * 修改单据编码
	 * @param sysNumber
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-25
	 */
	public int editSysNumber(SysNumber sysNumber);
	
	//查询，显示在列表中
	
	/**
	 * 停用单据编号
	 * @param paramid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-19
	 */
	public int disableSysNumber(@Param("numberid")String numberid);
	
	/**
	 * 启用单据编号
	 * @param paramid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-20
	 */
	public int enableSysNumber(@Param("numberid")String numberid);
	
	/**
	 * 删除单据编号
	 * @param numberid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-30
	 */
	public int deleteSysNumber(@Param("numberid")String numberid);
	
	/**
	 * 根据单据编码删除单据编号
	 * @param billcode
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-31
	 */
	public int deleteSysNumberByBillCode(@Param("billcode")String billcode);
	
	/**
	 * 根据billcode获取numberid
	 * @param billcode
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-8
	 */
	public List getNumIdByBcode(@Param("billcode")String billcode);
	
	/**
	 * 批量修改单据编号是否自动生成
	 * @param tablename
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-15
	 */
	public int editSysNumbersAutoCreate(Map paramMap);
	
	/**
	 * 批量修改单据编号是否允许修改
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-16
	 */
	public int editSysNumbersModifyFlag(SysNumber sysNumber);
	
	/**
	 * 禁用所有单据编号
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-16
	 */
	public int disableAllSysNumbers(PageMap pageMap);
	
	/**
	 * 根据启用的单据编号获取是否自动生成，是否允许修改
	 * @param billcode
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-19
	 */
	public SysNumber getSysNumberAutoCreate(@Param("billcode")String billcode);
	
	/**
	 * 根据表名(单据编码)获取单据编号列表
	 * @param tablename
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-7
	 */
	public List getSysNumberListByCode(@Param("tablename")String tablename);
	
	/**
	 * 根据单据名称类型获取自动生成值去重复
	 * @param billcode
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-23
	 */
	public List getAutoCreateByBillCode(@Param("billcode")String billcode);
	
	/*---------------------------------单据编号设置---------------------------------------------------*/
	/**
	 * 新增单据编号
	 * @param sysNumber
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 7, 2013
	 */
	public int addSysNumber(SysNumber sysNumber);
	
	/**
	 * 新增单据编码规则
	 * @param list
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 7, 2013
	 */
	public int addSysNumberRule(SysNumberRule sysNumberRule);
	
	/**
	 * 获取单据列表分页
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 8, 2013
	 */
	public List getSysNumberListPage(PageMap pageMap);
	
	/**
	 * 获取单据列表分页数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 8, 2013
	 */
	public int getSysNumberListCount(PageMap pageMap);
	
	/**
	 * 根据单据编号获取单据编号规则列表
	 * @param numberid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 8, 2013
	 */
	public List getSysNumberRuleList(String numberid);
	
	/**
	 * 获取单据编号规则详情
	 * @param numberruleid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 8, 2013
	 */
	public SysNumberRule getSysNumberRuleInfo(String numberruleid);
	
	/**
	 * 修改单据编号规则
	 * @param sysNumberRule
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 8, 2013
	 */
	public int editSysNumberRule(SysNumberRule sysNumberRule);
	
	/**
	 * 删除单据编号规则
	 * @param numberruleid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 8, 2013
	 */
	public int deleteSysNumberRule(@Param("delArr")String[] delArr);
	
	/**
	 * 根据单据编号删除单据编号规则
	 * @param numberid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 8, 2013
	 */
	public int deleteSysNumberRuleByNumid(String numberid);
	
	/**
	 * 根据单据编号获取单据编号规则数量
	 * @param numberid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 10, 2013
	 */
	public int getSysNumberRuleCountByNumid(String numberid);
	
	/**
	 * 验证固定值是否重复
	 * @param val
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 4, 2013
	 */
	public int isRepeatFixedVal(String val);

    /**
     * 根据单据编号获取是否存在该数据
     * @param billcode
     * @return
     * @author panxiaoxiao
     * @date 2015-10-13
     */
    public int isRepeatBillCode(@Param("billcode")String billcode);

    /**
     * 根据单据名称获取是否存在该数据
     * @param billname
     * @return
     * @author panxiaoxiao
     * @date 2015-10-15
     */
    public int isRepeatBillName(@Param("billname")String billname);

    /**
     * 根据单据编号规则和流水依据值 获取当前流水信息
     * @param numberid
     * @param serialkey
     * @return
     * @author limin
     * @date Dec 8, 2015
     */
    SysNumberSerial selectSysNumberSerialByColval(@Param("numberid")String numberid, @Param("serialkey")String serialkey);
    /**
     * 新增单据编号流水
     * @param serial
     * @return
     * @author limin
     * @date Dec 8, 2015
     */
    int insertSysNumberSerial(SysNumberSerial serial);

    /**
     * 更新单据编号流水
     * @param serial
     * @return
     * @author limin
     * @date Dec 8, 2015
     */
    int updateSysNumberSerial(SysNumberSerial serial);

    /**
     *
     * @param numberid
     * @return
     * @author limin
     * @date Dec 8, 2015
     */
    SysNumberSerial selectSysNumberSerial(@Param("numberid")String numberid);
}

