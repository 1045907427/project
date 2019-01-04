/**
 * @(#)TableInfo.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-14 zhanghonghui 创建版本
 */
package com.hd.agent.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.TableInfo;

/**
 * 数据字典，表信息管理数据库操作
 * 
 * @author zhanghonghui
 */
public interface TableInfoMapper {
	/**
	 * 获取所有表数据
	 * @return List
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public List getTableInfoList();
	/**
	 * 获取所有表数据
	 * queryMap里键值参数<br/> 
	 * tablename:表名<br/> 
	 * tabletype:表类型<br/> 
	 * usehistory：使用历史库，0不使用，1使用<br/> 
	 * useversion：使用版本库，0不使用，1使用<br/> 
	 * moduletype：所属模块<br/> 
	 * state：使用状态，0停用，1启用<br/> 
	 * createmethod：创建方式，1系统预制，2自建<br/> 
	 * datasource：数据来源，1人工添加，2自动导入，3人工导入，4其他<br/>
	 * useautoencoded:是否支持自动编号,0不支持，1支持<br/>
	 * @param queryMap
	 * @return List
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public List getTableInfoListBy(Map queryMap);
	/**
	 * 根据分页参数获取相关的表数据
	 * @param pageMap
	 * @return List
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public List getTableInfoPageList(PageMap pageMap);
	/**
	 * 根据分页参数获取相关数据的总页数
	 * @param pageMap
	 * @return int
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public int getTableInfoCount(PageMap pageMap);
	/**
	 * 根据表名获取表信息
	 * @param tablename
	 * @return
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public TableInfo showTableInfo(@Param("tablename")String tablename);
	/**
	 * 添加一个表信息
	 * @param tableInfo
	 * @return Long
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public int addTableInfo(TableInfo tableInfo);
	/**
	 * 更新一条表信息
	 * @param tableInfo
	 * @return int
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public int updateTableInfo(TableInfo tableInfo);
	/**
	 * 删除一个表信息
	 * @param tablename
	 * @return int
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public int deleteTableInfo(@Param("tablename")String tablename);
	/**
	 * 根据表名获取表信息数量
	 * @param tablename
	 * @return
	 * @author zhanghonghui 
	 * @date 2012-12-19
	 */
	public int getTableInfoCountBy(@Param("tablename")String tablename);
	
	/**
	 * 获取用户所有未引入的表
	 * queryMap所对应key值<br/> 
	 * dbname:数据库名<br/> 
	 * @param queryMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2012-12-25
	 */
	public List getDBUnimportTableList(Map queryMap);
}

