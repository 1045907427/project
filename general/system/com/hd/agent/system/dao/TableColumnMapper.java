/**
 * @(#)DataDictionary.java
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
import com.hd.agent.system.model.TableColumn;

/**
 * 数据字典-表字段管理相关数据库操作
 * 
 * @author zhanghonghui
 */
public interface TableColumnMapper {
	/**
	 * 获取所有表字段数据
	 * @return List
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public List getTableColumnList();
	/**
	 * 获取所有表字段数据
	 * queryMap里键值参数<br/> 
	 * tablename:表名<br/> 
	 * usefixed：是否支持固定字段，0不使用，1使用<br/> 
	 * usecoded：是否支持编码字段，0不使用，1使用<br/> 
	 * colapplytype：应用类型<br/> 
	 * usedataprivilege：是否支持数据权限，0不使用，1使用<br/> 
	 * usecolprivilege：是否支持字段权限，0不使用，1使用<br/> 
	 * usecolquery：是否可做查询条件，0不使用，1使用<br/> 
	 * usedataexport：是否支持数据导出，0不使用，1使用<br/> 
	 * usecolrefer：是否可做参照字段，0不使用，1使用<br/> 
	 * @return queryMap
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public List getTableColumnListBy(Map queryMap);
	/**
	 * 根据PageMap参数获取相关的表字段所有数据
	 * @param pageMap
	 * @return List
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public List getTableColumnPageAllList(PageMap pageMap);
	/**
	 * 根据分页参数获取相关的表字段数据
	 * @param pageMap
	 * @return List
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public List getTableColumnPageList(PageMap pageMap);
	/**
	 * 根据分页参数获取相关数据的总页数
	 * @param pageMap
	 * @return int
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public int getTableColumnCount(PageMap pageMap);
	/**
	 * 显示表字段信息
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public TableColumn showTableColumn(@Param("id")String id);
	/**
	 * 根据表名和字段名获取字段详细信息
	 * @param tablename
	 * @param column
	 * @return
	 * @author chenwei 
	 * @date 2013-1-26
	 */
	public TableColumn getColumnByTableColumn(@Param("tablename")String tablename,@Param("column")String column);
	/**
	 * 添加一个表字段信息
	 * @param tableColumn
	 * @return Long
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public int addTableColumn(TableColumn tableColumn);
	/**
	 * 更新一条表字段信息
	 * @param tableColumn
	 * @return int
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public int updateTableColumn(TableColumn tableColumn);
	/**
	 * 删除一个表字段信息
	 * @param id
	 * @return int
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public int deleteTableColumn(@Param("id")String id);
	
	/**
	 * 根据表名删除相关表字段信息
	 * @param tablename
	 * @return int
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public int deleteTableColumnByTablename(@Param("tablename")String tablename);
	
	/**
	 * 更新表结构中的表名
	 * @param newtablename
	 * @param oldtablename
	 * @return
	 * @author zhanghonghui 
	 * @date 2012-12-20
	 */
	public int updateTableColumnByTablename(@Param("newtablename")String newtablename,@Param("oldtablename")String oldtablename);
	/**
	 * 根据相关参数统计数据<br/> 
	 * queryMap里键值参数<br/> 
	 * tablename:表名<br/> 
	 * columnname:列名<br/>
	 * usefixed：是否支持固定字段，0不使用，1使用<br/> 
	 * usecoded：是否支持编码字段，0不使用，1使用<br/> 
	 * colapplytype：应用类型<br/> 
	 * usedataprivilege：是否支持数据权限，0不使用，1使用<br/> 
	 * usecolprivilege：是否支持字段权限，0不使用，1使用<br/> 
	 * usecolquery：是否可做查询条件，0不使用，1使用<br/> 
	 * usedataexport：是否支持数据导出，0不使用，1使用<br/> 
	 * usecolrefer：是否可做参照字段，0不使用，1使用<br/> 
	 * @param queryMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2012-12-21
	 */
	public int getTableColumnCountBy(Map queryMap);
	/**
	 * 获取表字段详细信息
	 * @param tablename
	 * @param column
	 * @return
	 * @author chenwei 
	 * @date Feb 26, 2013
	 */
	public TableColumn getTableColumnInfo(@Param("tablename")String tablename,@Param("column")String column);
	/**
	 * 获取数据库中的未导入的表结构
	 * queryMap所对应key值<br/> 
	 * dbname:数据库名<br/> 
	 * tablename: 表名<br/> 
	 * selectcolumnlist: 选中导入的结构名<br/> 
	 * @param queryMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2012-12-25
	 */
	public List getDBUnimportColumnList(Map queryMap);
	/**
	 * 获取数据库中的对应的表结构
	 * queryMap所对应key值<br/> 
	 * dbname:数据库名<br/> 
	 * tablename: 表名<br/> 
	 * @param queryMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2012-12-25
	 */
	public List getDBTableColumnList(Map queryMap);
	
	/**
	 * 根据表名获取该必填项字段
	 * @param tablename
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 2, 2015
	 */
	public List getRequiredColumnByTable(@Param("tablename")String tablename);
	

	/**
	 * 根据表名获取该表字段
	 * @param tablename
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 2, 2015
	 */
	public List getTableColumnListByTable(@Param("tablename")String tablename);
}

