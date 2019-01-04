/**
 * @(#)ISysNumberService.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-25 panxiaoxiao 创建版本
 */
package com.hd.agent.system.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.SysNumber;
import com.hd.agent.system.model.SysNumberRule;

/**
 * 处理单据编号的业务逻辑的接口
 * 
 * @author panxiaoxiao
 */
public interface ISysNumberService{

	/**
	 * 获取单据名称列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-28
	 */
	public List showSysNumberBillNameList()throws Exception;
	/**
	 * 显示单据编号列表，实现分页
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-25
	 */
	public PageData showSysNumberPageListByBcode(PageMap pageMap) throws Exception;
	
	/**
	 * 获取单据编号详情信息
	 * @param numberid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-21
	 */
	public SysNumber getSysNumberInfo(String numberid)throws Exception;
	/**
	 * 添加单据编号
	 * @param sysNumber
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-25
	 */
	public boolean addSysNumber(SysNumber sysNumber) throws Exception;
	
	/**
	 * 修改单据编码
	 * @param sysNumber
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-25
	 */
	public boolean editSysNumber(SysNumber sysNumber) throws Exception;
	
	/**
	 * 禁用单据编码
	 * @param numberid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-25
	 */
	public boolean disableSysNumber(String numberid) throws Exception;
	
	/**
	 * 启用单据编码
	 * @param numberid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-25
	 */
	public boolean enableSysNumber(String numberid,String tablename,String type) throws Exception;
	
	/**
	 * 删除单据编码
	 * @param numberid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-30
	 */
	public boolean deleteSysNumber(String numberid) throws Exception;
	
	/**
	 * 批量修改单据编号是否自动生成
	 * @param tablename
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-15
	 */
	public boolean editSysNumbersAutoCreate(String billcode,String autoCreate)throws Exception;
	
	/**
	 * 批量修改单据是否允许修改
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-16
	 */
	public boolean editSysNumbersModifyFlag(String numberid,String modifyflag)throws Exception;
	
	/**
	 * 禁用所有单据编号
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-16
	 */
	public boolean disableAllSysNumbers(PageMap pageMap)throws Exception;
	
	/**
	 * 根据启用的单据编号billcode，判断是否自动生成
	 * @param billcode
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-19
	 */
	public SysNumber getSysNumberAutoCreate(String billcode) throws Exception;
	
	/**
	 * 根据单据名称类型获取自动生成值去重复
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-23
	 */
	public List getAutoCreateByBillCode(String billcode)throws Exception;
	
	/*---------------------------单据编号设置---------------------------------------------*/
	/**
	 * 批量新增单据编号规则
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 7, 2013
	 */
	public boolean addSysNumber(SysNumber sysNumber,List<SysNumberRule> list)throws Exception;
	
	/**
	 * 获取单据编号列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 8, 2013
	 */
	public PageData getSysNumberList(PageMap pageMap)throws Exception;
	
	/**
	 * 根据单据编号获取单据编号规则
	 * @param numberid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 8, 2013
	 */
	public List getSysNumberRuleList(String numberid)throws Exception;
	
	/**
	 * 获取单据编号规则详情
	 * @param numberruleid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 8, 2013
	 */
	public SysNumberRule getSysNumberRuleInfo(String numberruleid)throws Exception;
	
	/**
	 * 修改单据编号
	 * @param sysNumber
	 * @param delArr
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 10, 2013
	 */
	public boolean editSysNumber(SysNumber sysNumber,List<SysNumberRule> ruleList,String delStr)throws Exception;
	
	/**
	 * 启用单据编号
	 * @param numberid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 10, 2013
	 */
	public boolean enableSysNumber(String numberid)throws Exception;
	
	/**
	 * 根据单据编号规则返回自动生成的单据编号
	 * @param obj 传送过来的数据对象
	 * @param billcode 单据编号
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 11, 2013
	 */
	public Map autoCreateSysNumbderForeign(Object obj,String billcode)throws Exception;
	
	/**
	 * 验证固定值是否重复
	 * @param val
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 4, 2013
	 */
	public boolean isRepeatFixedVal(String val)throws Exception;

    /**
     * 判断单据编码是否重复(true重复false不重复)
     * @param billcode
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-10-13
     */
    public boolean isRepeatBillCode(String billcode)throws Exception;

    /**
     * 判断单据名称是否重复(true重复false不重复)
     * @param billname
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-10-14
     */
    public boolean isRepeatBillName(String billname)throws Exception;
}

