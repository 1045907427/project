/**
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-14 zhanghonghui 创建版本
 * 2013-01-19 chenwei 修改 添加数据字典提供基础档案是否能修改接口
 */
package com.hd.agent.system.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.TableColumn;
import com.hd.agent.system.model.TableInfo;
import com.hd.agent.system.model.TableRelation;

/**
 * 数据字典-表管理业务接口层
 * 
 * @author zhanghonghui
 */
public interface IDataDictionaryService {
	/**
	 * 显示所有表列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public List showTableInfoList() throws Exception;
	
	/**
	 * 显示表分页信息
	 * @return 
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public PageData showTableInfoPageList(PageMap pageMap) throws Exception;
	
	/**
	 * 根据表名获取相关数量
	 * @param tablename
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-19
	 */
	public int getTableInfoCountBy(String tablename) throws Exception;	

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
	 * usetreelist:是否树状菜单
	 * @param queryMap
	 * @return List
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public List getTableInfoListBy(Map queryMap) throws Exception;
	
	/**
	 * 显示表信息
	 * @param tablename
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-18
	 */
	public TableInfo showTableInfo(String tablename) throws Exception;
	
	/**
	 * 添加表信息
	 * @param tableInfo
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-10
	 */
	public boolean addTableInfo(TableInfo tableInfo) throws Exception;
	/**
	 * 修改表信息
	 * @param tableInfo
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-10
	 */
	public boolean editTableInfo(TableInfo tableInfo) throws Exception;
	/**
	 * 复制表及表描述字段
	 * @param tableInfo
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月25日
	 */
	public Map copyTableInfoAddColumn(TableInfo tableInfo) throws Exception;
	/**
	 * 删除表信息
	 * @param tablename
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-10
	 */
	public boolean deleteTableInfo(String tablename) throws Exception;
	
	/**
	 * 获取用户所有未引入的表<br/> 
	 * queryMap所对应key值<br/> 
	 * dbname:数据库名<br/> 
	 * @param queryMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2012-12-25
	 */
	public List getDBUnimportTableList(Map queryMap) throws Exception;
	/**
	 * 
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-7-4
	 */
	public boolean addTableInfoDBImport(String[] tablearr) throws Exception;
	
	
	
	//**************************************//
	//表结构部分
	//**************************************//

	/**
	 * 显示所有表结构列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public List showTableColumnList() throws Exception;
	/**
	 * 根据PageMap参数获取相关的表字段所有数据
	 * @param pageMap
	 * @return List
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public PageData getTableColumnPageAllList(PageMap pageMap) throws Exception;
	/**
	 * 显示表分页信息
	 * @return 
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public PageData showTableColumnPageList(PageMap pageMap) throws Exception;
	
	/**
	 * 显示表结构信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-18
	 */
	public TableColumn showTableColumn(String id) throws Exception;
	/**
	 * 根据表名和字段名获取字段详细信息
	 * @param tablename
	 * @param column
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-26
	 */
	public TableColumn getColumnByTableColumn(String tablename,String column) throws Exception;
	/**
	 * 添加表结构信息
	 * @param tableInfo
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-10
	 */
	public boolean addTableColumn(TableColumn tableColumn) throws Exception;
	/**
	 * 修改表结构信息
	 * @param tableColumn
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-10
	 */
	public boolean editTableColumn(TableColumn tableColumn) throws Exception;
	/**
	 * 删除表结构信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-10
	 */
	public boolean deleteTableColumn(String id) throws Exception;
	
	/**
	 * 根据表名删除表结构信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-10
	 */
	public boolean deleteTableColumnByTablename(String tablename) throws Exception;
	
	/**
	 * 根据相关参数统计数据<br/> 
	 * queryMap里键值参数<br/> 
	 * tablename:表名<br/> 
	 * columnname:列名<br/>
	 * usefixed：是否支持固定字段，0不使用，1使用<br/> 
	 * usecoded：是否支持编码字段，0不使用，1使用<br/> 
	 * usepk:是否主键，1是，0否<br/>
	 * colapplytype：应用类型<br/> 
	 * usedataprivilege：是否支持数据权限，0不使用，1使用<br/> 
	 * usecolprivilege：是否支持字段权限，0不使用，1使用<br/> 
	 * usecolquery：是否可做查询条件，0不使用，1使用<br/> 
	 * usedataexport：是否支持数据导出，0不使用，1使用<br/> 
	 * usecolrefer：是否可做参照字段，0不使用，1使用<br/> 
	 * @param queryMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-19
	 */
	public int getTableColumnCountBy(Map queryMap) throws Exception;
	/**
	 * 获取表字段信息
	 * @param tablename
	 * @param column
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Feb 26, 2013
	 */
	public TableColumn getTableColumnInfo(String tablename,String column) throws Exception;
	/**
	 * 获取所有表字段数据<br/> 
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
	 * isshow:是否引用显示1是0否
	 * @return queryMap
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public List getTableColumnListBy(Map queryMap) throws Exception;
	
	/**
	 * 获取表数据库中的结构<br/> 
	 * queryMap所对应key值<br/> 
	 * dbname:数据库名<br/> 
	 * tablename: 表名<br/> 
	 * selectcolumnlist: 选中导入的结构名<br/> 
	 * @param queryMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2012-12-25
	 */
	public List getDBUnimportColumnList(Map queryMap) throws Exception;
	
	/**
	 * 获取数据库中的对应的表结构<br/> 
	 * queryMap所对应key值<br/> 
	 * dbname:数据库名<br/> 
	 * tablename: 表名<br/> 
	 * @param queryMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2012-12-25
	 */
	public List getDBTableColumnList(Map queryMap) throws Exception;
	/**
	 * 根据表名和字段数组导入，字段信息
	 * @param tablename
	 * @param columnarr
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-7-4
	 */
	public boolean addTableColumnDBImport(String tablename,String[] columnarr) throws Exception;
	
	
	
	
	//**************************************//
	//表关联Service
	//**************************************//
	/**
	 * 显示所有表关联列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public List showTableRelationList() throws Exception;
	
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
	public List getTableRelationListBy(Map queryMap) throws Exception;
	
	/**
	 * 显示表关联分页信息
	 * @return 
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public PageData showTableRelationPageList(PageMap pageMap) throws Exception;
	
	/**
	 * 显示表关联信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-18
	 */
	public TableRelation showTableRelation(String id) throws Exception;
	
	/**
	 * 添加表关联信息
	 * @param tableInfo
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-10
	 */
	public boolean addTableRelation(TableRelation tableRelation) throws Exception;
	/**
	 * 修改表关联信息
	 * @param tableInfo
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-10
	 */
	public boolean editTableRelation(TableRelation tableRelation) throws Exception;
	/**
	 * 删除表信息
	 * @param tablename
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-10
	 */
	public boolean deleteTableRelation(String id) throws Exception;
	
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
	public int getTableRelationCountBySubNames(Map queryMap) throws Exception;
	
	/**
	 * 根据从表表名和从表字段名，获取表关联信息
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-5
	 */
	public TableRelation getTableRelationBySubNames(String tablename,String columnname)throws Exception;
	
	/**
	 * 根据参数获取相关的表数据字典
	 * @param pageMap
	 * @return List
	 * @author zhanghonghui
	 * @date 2012-12-14
	 */
	public List getTableDataDictList(PageMap pageMap)throws Exception;
	
	/**
	 * 根据参数获取相关分页后的表数据字典
	 * @return 
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public PageData getTableDataDictPageList(PageMap pageMap) throws Exception;
	
	/**
	 * 根据相应的参数，返回数据字典<br/>
	 * pageMap中的参数<br/>
	 * tablename: 从属表名，必需参数<br/>
	 * columnname: 从属表列名，必需参数<br/>
	 * 分页相关参数以page为主，如果需要分页，此参数必填。如果，返回的数据为树形结构，则分页参数无效。<br/>
	 * 返回值结果：<br/>
	 * 以map形式返回结果<br/>
	 * map返回结果格式:<br/>
	 * {'flag':false或者true,'msg':'xxx','dataview':'数据格式','data':'数据'}<br/>
	 * flag:当值为false时，dataview和data为无值，或者不显示<br/>
	 * 数据格式：<br/>
	 * 1)tree:返回树形
	 * 2)page:返回分页列表
	 * 3)list:返回列表 
	 * @param pageMap
	 * @return 以map形式返回结果
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-8
	 */
	public Map getTableDataDictHandleResult(PageMap pageMap) throws Exception;
	/**
	 * 获取表中的数值大小。
	 * @param tablename 表名
	 * @param column 字段名
	 * @param rcolumn 关联字段（使用该字段查询）
	 * @param value 关联字段的值（查询条件）
	 * @return
	 * @author chenwei 
	 * @date 2013-1-26
	 */
	public BigDecimal getTableNumber(String tablename,String column,String rcolumn,String value) throws Exception;
	/**
	 * 获取级联关系定义的控件数据
	 * @param tablename
	 * @param column
	 * @param jsonrule
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Feb 28, 2013
	 */
	public Map getTableRalationWidget(String tablename,String column,String jsonrule) throws Exception;
	/**
	 * 获取树状表的数据列表
	 * @param tablename
	 * @param pageMap
	 * @param jsonrule
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 1, 2013
	 */
	public PageData getWidgetTableDataList(String tablename,PageMap pageMap,String jsonrule) throws Exception;
	/**
	 * 根据表名和数据编号 判断基础档案是否可以删除
	 * true:可以删除 false不可以删除
	 * @param tname
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 9, 2013
	 */
	public boolean canTableDataDelete(String tname,String id) throws Exception;
	/**
	 * 获取数据状态
	 * @param tablename
	 * @param id
	 * @param state
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 10, 2013
	 */
	public String getDataState(String tablename,String id,String state) throws Exception;
	/**
	 * 获取基础档案可以修改操作状态
	 * @param tablename
	 * @param object
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-21
	 */
	public Map getBaseFilesEditState(String tablename,Object object) throws Exception; 
	
	/**
	 * 根据表名获取该必填字段
	 * @param tablename
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 2, 2015
	 */
	public List getRequiredColumnByTable(String tablename)throws Exception;
}

