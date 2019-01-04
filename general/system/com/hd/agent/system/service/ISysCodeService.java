/**
 * @(#)ISysCodeService.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-15 panxiaoxiao 创建版本
 */
package com.hd.agent.system.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.SysCode;

/**
 * 
 * 处理公共代码的业务逻辑的接口
 * @author panxiaoxiao
 */
public interface ISysCodeService {
	
	/**
	 * 显示公共代码列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-15
	 */
	public List showSysCodeList() throws Exception;
	
	/**
	 * 显示代码类型
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-18
	 */
	public List showSysCodeTypes() throws Exception;
	
	/**
	 * 显示模块ID
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-20
	 */
	public List showSysCodeCodes() throws Exception;
	/**
	 * 显示公共代码分页数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-15
	 */
	public PageData showSysCodePageList(PageMap pageMap) throws Exception;
	
	/**
	 * 获取公共代码详情
	 * @param code
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-15
	 */
	public SysCode showSysCodeInfo(String code,String type) throws Exception;
	
	/**
	 * 添加公共代码
	 * @param sysCode
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-15
	 */
	public boolean addSysCode(SysCode sysCode) throws Exception;
	
	/**
	 * 修改公共代码
	 * @param sysCode
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-15
	 */
	public boolean editSysCode(SysCode sysCode) throws Exception;
	
	/**
	 * 禁用代码
	 * @param code
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-15
	 */
	public boolean disableSysCode(String code,String type) throws Exception;
	
	/**
	 * 启用代码
	 * @param code
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-19
	 */
	public boolean enableSysCode(String code,String type) throws Exception;
	
	/**
	 * 查询模块名称
	 * @param code
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-21
	 */
	public String searchCodeName(String code,String type) throws Exception;
	
	/**
	 * 查询模块ID是否存在
	 * @param code
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-22
	 */
	public boolean searchCode(String code,String type) throws Exception;
	
	/**
	 * 验证模块是否存在过程
	 * @param codeName
	 * @param type
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-25
	 */
	public String codenametocode(String codeName,String type)throws Exception;
	
	/**
	 * 根据代码类型(type)显示代码列表，对外调用
	 * @param type
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-25
	 */
	public List showSysCodeListByType(String type) throws Exception;
	/**
	 * 获取全部系统代码
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 29, 2013
	 */
	public String getAllSysCodeList() throws Exception;
	/**
	 * 返回代码类型，代码名称，对外调用
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-14
	 */
	public List backSysCodeType() throws Exception;
	
	/**
	 * 根据单据编码和类型为单据的，删除代码
	 * @param code
	 * @param type
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-31
	 */
	public boolean deleteSCBillName(String code,String type) throws Exception;
	
	/**
	 * 删除代码
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-27
	 */
	public boolean deleteSysCode(Map paramMap)throws Exception;
	/**
	 * 根据类型获取第一个编码信息
	 * @param type
	 * @return com.hd.agent.system.model.SysCode
	 * @throws
	 * @author zhang_honghui
	 * @date Jan 14, 2017
	 */
	public SysCode getEnableSysCodeFirstInfoByType(String type) throws Exception;
	
}

