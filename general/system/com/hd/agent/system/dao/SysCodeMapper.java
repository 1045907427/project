/**
 * @(#)SysCodeMapper.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-15 panxiaoxiao 创建版本
 */
package com.hd.agent.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.SysCode;

/**
 * 
 * 公共代码相关数据库操作
 * @author panxiaoxiao
 */
public interface SysCodeMapper {
	/**
	 * 获取公共代码列表
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-15
	 */
	public List getSysCodeList();
	
	/**
	 * 获取代码类型
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-18
	 */
	public List getSysCodeTypes();
	
	/**
	 * 获取模块id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-20
	 */
	public List getSysCodeCodes();
	
	/**
	 * 根据条件获取公共代码列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-15
	 */
	public List getSysCodePageList(PageMap pageMap);
	
	/**
	 * 根据条件获取公共代码数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-15
	 */
	public int getSysCodeCount(PageMap pageMap);
	
	/**
	 * 获取公共代码详情信息
	 * @param code
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-15
	 */
	public SysCode getSysCodeInfo(@Param("code")String code,@Param("type")String type);
	/**
	 * 根据代码及代码类型，获取启用状态下的代码
	 * @param code
	 * @param type
	 * @return com.hd.agent.system.model.SysCode
	 * @throws
	 * @author zhang_honghui
	 * @date Dec 28, 2016
	 */
	public SysCode getEnableSysCodeInfo(@Param("code")String code,@Param("type")String type);
	
	/**
	 * 添加公共代码
	 * @param sysCode
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-15
	 */
	public int addSysCode(SysCode sysCode);
	
	/**
	 * 修改公共代码信息
	 * @param sysCode
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-15
	 */
	public int editSysCode(SysCode sysCode);
	
	/**
	 * 禁用代码
	 * @param code
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-15
	 */
	public int disableSysCode(@Param("code")String code,@Param("type")String type);
	
	/**
	 * 启用代码
	 * @param code 
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-19
	 */
	public int enableSysCode(@Param("code")String code,@Param("type")String type);
	
	/**
	 * 查询模块名称
	 * @param code
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-21
	 */
	public String searchCodename(@Param("code")String code,@Param("type")String type);
	
	/**
	 * 查询模块ID是否存在
	 * @param code
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-22
	 */
	public String searchCode(@Param("code")String code,@Param("type")String type);
	
	public String codenametocode(@Param("codename")String codename,@Param("type")String type);
	/**
	 * 对外调用，根据代码类型(type)调用数据
	 * @param type
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-25
	 */
	public List getSysCodeListForeign(@Param("type")String type);
	
	/**
	 * 对外调用，调用代码类型，代码类型名称
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-14
	 */
	public List getSysCodeTypeForeign();
	/**
	 * 根据代码类型(type)调用缓存过数据
	 * @param type
	 * @return
	 * @author panxiaoxiao
	 * @date 2012-12-25
	 */
	public List getCodeListCacheByType(@Param("type")String type);
	/**
	 * 根据单据编码和类型为单据的，删除代码
	 * @param code
	 * @param type
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-31
	 */
	public int deleteSCBillName(@Param("code")String code,@Param("type")String type);
	
	/**
	 * 检验代码唯一性
	 * @param type
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-22
	 */
	public List checkSysCodeList(@Param("type")String type);
	
	/**
	 * 删除代码
	 * @param paramMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-27
	 */
	public int deleteSysCode(Map paramMap);

	/**
	 * 根据类型获取第一个编码信息
	 * @param type
	 * @return com.hd.agent.system.model.SysCode
	 * @throws
	 * @author zhang_honghui
	 * @date Jan 14, 2017
	 */
	public SysCode getEnableSysCodeFirstInfoByType(String type);
}

