/**
 * @(#)TableRelationMapper.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-24 zhanghonghui 创建版本
 */
package com.hd.agent.system.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.TableRelation;

/**
 * 
 * 
 * @author zhanghonghui
 */
public interface TableRelationMapper {
	/**
	 * 获取所有表关联数据
	 * 
	 * @return List
	 * @author zhanghonghui
	 * @date 2012-12-14
	 */
	public List getTableRelationList();
	/**
	 * 根据查询条件获取相关数据
	 * @param queryMap
	 * queryMap里键值参数<br/> 
	 * maintablename:主表名<br/> 
	 * maincolumnname:主表列名<br/> 
	 * tablename:从属表名<br/> 
	 * columnname:从属表列名<br/> 
	 * deleteverify:删除校验<br/>
	 * cascadechange:级联更新<br/>
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-3
	 */
	public List getTableRelationListBy(Map queryMap);

	/**
	 * 根据分页参数获取相关的表关联数据
	 * @param pageMap
	 * @return List
	 * @author zhanghonghui
	 * @date 2012-12-14
	 */
	public List getTableRelationPageList(PageMap pageMap);

	/**
	 * 根据分页参数获取相关数据的表关联总页数
	 * 
	 * @param pageMap
	 * @return int
	 * @author zhanghonghui
	 * @date 2012-12-14
	 */
	public int getTableRelationCount(PageMap pageMap);

	/**
	 * 根据编号获取表关联信息
	 * 
	 * @param id
	 * @return
	 * @author zhanghonghui
	 * @date 2012-12-14
	 */
	public TableRelation showTableRelation(@Param("id") String id);

	/**
	 * 添加一个表关联信息
	 * 
	 * @param tableInfo
	 * @return Long
	 * @author zhanghonghui
	 * @date 2012-12-14
	 */
	public int addTableRelation(TableRelation tableRelation);

	/**
	 * 更新一条表关联信息
	 * 
	 * @param tableRelation
	 * @return int
	 * @author zhanghonghui
	 * @date 2012-12-14
	 */
	public int updateTableRelation(TableRelation tableRelation);

	/**
	 * 删除一个表关联信息
	 * 
	 * @param id
	 * @return int
	 * @author zhanghonghui
	 * @date 2012-12-14
	 */
	public int deleteTableRelation(@Param("id") String id);

	/**
	 * 根据主表名、请表字段名、子表名和子表字段名判断，表关联中是否存在该数据数
	 * queryMap里键值参数<br/> 
	 * maintablename:主表名<br/> 
	 * maincolumnname:表名<br/> 
	 * tablename:从属表名<br/> 
	 * columnname:从属表字段名<br/> 
	 * oldmaintablename:原始表名<br/> 
	 * oldmaincolumnname:原始表字段名<br/> 
	 * oldtablename:原始从属表名<br/> 
	 * oldcolumnname:原始从属表字段名<br/> 
	 * @param queryMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-19
	 */
	public int getTableRelationCountBySubNames(Map queryMap);
	/**
	 * 根据从表表名和从表字段名，获取表关联信息
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-5
	 */
	public TableRelation getTableRelationBySubNames(@Param("tablename") String tablename,@Param("columnname") String columnname);
	/**
	 * 根据分页参数获取相关的表数据字典
	 * @param pageMap
	 * @return List
	 * @author zhanghonghui
	 * @date 2012-12-14
	 */
	public List getTableDataDictList(PageMap pageMap);
	
	/**
	 * 根据分页参数获取相关表数据字典总页数
	 * 
	 * @param queryMap
	 * @return int
	 * @author zhanghonghui
	 * @date 2012-12-14
	 */
	public int getTableDataDictCount(Map queryMap);
	/**
	 * 获取表tablename 字段column中的值
	 * @param tablename表名
	 * @param column字段名
	 * @param rcolumn关联字段（查询条件）
	 * @param value值（查询值）
	 * @return
	 * @author chenwei 
	 * @date 2013-1-26
	 */
	public BigDecimal getTableNumber(String tablename,String column,String rcolumn,String value);
	/**
	 * 根据表名获取用户能访问的数据数量
	 * @param tablename
	 * @param sql
	 * @return
	 * @author chenwei 
	 * @date Feb 28, 2013
	 */
	public int getTableDataCount(@Param("tablename")String tablename,@Param("sql")String sql);
	/**
	 * 根据表名获取该表的数据列表
	 * @param tablename
	 * @param column
	 * @param sql
	 * @return
	 * @author chenwei 
	 * @date Feb 28, 2013
	 */
	public List getTableDataList(@Param("tablename")String tablename,@Param("column")String column,@Param("sql")String sql);
	/**
	 * 根据表名获取该表的树状数据列表
	 * @param tablename
	 * @param column
	 * @param sql
	 * @return
	 * @author chenwei 
	 * @date Mar 1, 2013
	 */
	public List getTableDataTreeList(@Param("tablename")String tablename,@Param("column")String column,@Param("sql")String sql);
	/**
	 * 获取树状表的数据列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Mar 1, 2013
	 */
	public List getWidgetTreeDataList(PageMap pageMap);
	/**
	 * 获取树状表的数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Mar 1, 2013
	 */
	public int getWidgetTreeDataCount(PageMap pageMap);
	/**
	 * 获取数据的状态
	 * @param tablename
	 * @param id
	 * @param state
	 * @return
	 * @author chenwei 
	 * @date Apr 10, 2013
	 */
	public String getDataState(@Param("tablename")String tablename,@Param("id")String id,@Param("state")String state);
	/**
	 * 基础档案修改后更新级联关系表数据
	 * 
	 * @param tablename
	 * @param column
	 * @param beforeValue
	 * @param updateValue
	 * @return
	 * @author chenwei
	 * @date 2013-2-2
	 */
	public int updateRelatedTable(@Param("tablename") String tablename,
			@Param("column") String column,
			@Param("beforeValue") String beforeValue,
			@Param("updateValue") String updateValue);
}
