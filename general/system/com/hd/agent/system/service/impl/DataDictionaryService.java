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
package com.hd.agent.system.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.common.util.RuleJSONUtils;
import com.hd.agent.system.dao.SysCodeMapper;
import com.hd.agent.system.dao.TableColumnMapper;
import com.hd.agent.system.dao.TableInfoMapper;
import com.hd.agent.system.dao.TableRelationMapper;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.model.TableColumn;
import com.hd.agent.system.model.TableDataDict;
import com.hd.agent.system.model.TableInfo;
import com.hd.agent.system.model.TableRelation;
import com.hd.agent.system.service.IDataDictionaryService;

/**
 * 数据字典-表管理业务实现层
 * 
 * @author zhanghonghui
 */
public class DataDictionaryService extends BaseServiceImpl implements IDataDictionaryService {

	/**
	 * 表信息数据操作
	 */
	private TableInfoMapper tableInfoMapper;
	/**
	 * 表结构数据操作
	 */
	private TableColumnMapper tableColumnMapper;
	/**
	 * 表关联数据操作
	 */
	private TableRelationMapper tableRelationMapper;
	/**
	 * 代码表数据操作
	 */
	private SysCodeMapper sysCodeMapper;

	public TableInfoMapper getTableInfoMapper() {
		return tableInfoMapper;
	}

	public void setTableInfoMapper(TableInfoMapper tableInfoMapper) {
		this.tableInfoMapper = tableInfoMapper;
	}

	public TableColumnMapper getTableColumnMapper() {
		return tableColumnMapper;
	}

	public void setTableColumnMapper(TableColumnMapper tableColumnMapper) {
		this.tableColumnMapper = tableColumnMapper;
	}

	public TableRelationMapper getTableRelationMapper() {
		return tableRelationMapper;
	}

	public void setTableRelationMapper(TableRelationMapper tableRelationMapper) {
		this.tableRelationMapper = tableRelationMapper;
	}

	public SysCodeMapper getSysCodeMapper() {
		return sysCodeMapper;
	}

	public void setSysCodeMapper(SysCodeMapper sysCodeMapper) {
		this.sysCodeMapper = sysCodeMapper;
	}

	/**
	 * 显示所有表列表
	 */
	@Override
	public List showTableInfoList() throws Exception {
		// TODO Auto-generated method stub
		List list = tableInfoMapper.getTableInfoList();
		return list;
	}

	/**
	 * 显示表分页信息
	 */
	@Override
	public PageData showTableInfoPageList(PageMap pageMap) throws Exception {
		List<TableInfo> list=tableInfoMapper
				.getTableInfoPageList(pageMap);
		for(TableInfo tableInfo : list){
			if(StringUtils.isNotEmpty(tableInfo.getModuletype())){
				List<SysCode> sysCodeList=getCodeByType("module");
				String[] moduleTypeArr=tableInfo.getModuletype().split(",");
				if(null!=moduleTypeArr && moduleTypeArr.length>0){
					StringBuilder sb=new StringBuilder();
					for(String module:moduleTypeArr){
						if(null!=module && !"".equals(module.trim())){
							for(SysCode sysCode:sysCodeList){
								if(null!=sysCode && module.equals(sysCode.getCode())){
									if(sb.length()>0){
										sb.append(",");
									}
									sb.append(sysCode.getCodename());
								}
							}
						}
					}
					tableInfo.setModuletypename(sb.toString());
				}
			}
		}
		
		PageData pageData = new PageData(tableInfoMapper
				.getTableInfoCount(pageMap), list, pageMap);
		return pageData;
	}

	@Override
	public boolean addTableInfo(TableInfo tableInfo) throws Exception {
		int irows = tableInfoMapper.addTableInfo(tableInfo);
		return irows > 0;
	}

	@Override
	public boolean editTableInfo(TableInfo tableInfo) throws Exception {
		int irows = tableInfoMapper.updateTableInfo(tableInfo);
		if (!tableInfo.getTablename().equals(tableInfo.getOldtablename())) {
			tableColumnMapper.updateTableColumnByTablename(tableInfo
					.getTablename(), tableInfo.getOldtablename());
		}
		return irows > 0;
	}
	@Override
	public Map copyTableInfoAddColumn(TableInfo tableInfo) throws Exception{
		Map resultMap=new HashMap();
		resultMap.put("flag", false);
		
		if(StringUtils.isEmpty(tableInfo.getOldtablename())){
			resultMap.put("flag", false);
			resultMap.put("msg", "要复制的表不存在，请重新选择");
			return resultMap;
		}
		if(getTableInfoCountBy(tableInfo.getTablename())>0){
			resultMap.put("flag", false);
			resultMap.put("msg", "已经表名("+tableInfo.getTablename()+")存在");
			return resultMap;
		}
		int irows = tableInfoMapper.addTableInfo(tableInfo);
		if(irows>0){
			resultMap.put("flag", true);
			
			Map queryMap=new HashMap();
			queryMap.put("tablename", tableInfo.getOldtablename());
			List<TableColumn> columnList=tableColumnMapper.getTableColumnListBy(queryMap);
			if(null!=columnList && columnList.size()>0){
				for(TableColumn tableColumn : columnList){
					tableColumn.setTablename(tableInfo.getTablename());
					tableColumnMapper.addTableColumn(tableColumn);
				}
			}
		}
		return resultMap;
	}
	@Override
	public boolean deleteTableInfo(String tablename) throws Exception {
		int irows = tableInfoMapper.deleteTableInfo(tablename);
		tableColumnMapper.deleteTableColumnByTablename(tablename);
		return irows > 0;
	}

	@Override
	public TableInfo showTableInfo(String tablename) throws Exception {
		TableInfo tableInfo = tableInfoMapper.showTableInfo(tablename);
		return tableInfo;
	}

	@Override
	public int getTableInfoCountBy(String tablename) throws Exception {
		int irows = tableInfoMapper.getTableInfoCountBy(tablename);
		return irows;
	}

	@Override
	public List getTableInfoListBy(Map queryMap) throws Exception {
		List list=tableInfoMapper.getTableInfoListBy(queryMap);
		return list;
	}
	
	@Override
	public List getDBUnimportTableList(Map queryMap) throws Exception {
		List list=tableInfoMapper.getDBUnimportTableList(queryMap);
		return list;
	}
	@Override
	public boolean addTableInfoDBImport(String[] tablearr) throws Exception{
		if(null == tablearr || tablearr.length==0){
			return false;
		}
		SysUser sysUser=getSysUser();

		Map queryMap=new HashMap();
		queryMap.put("selecttablelist", tablearr);
		
		List<TableInfo> list=getDBUnimportTableList(queryMap);
		boolean flag=false;
		for(TableInfo model : list){
			if(model!=null && !"".equals(model.getTablename())){
				if(getTableInfoCountBy(model.getTablename())==0){
					model.setTablename(model.getTablename());
					if("".equals(model.getTabledescname())){
						model.setTabledescname(model.getTablename());
					}else{
						model.setTabledescname(model.getTabledescname());
					}
					model.setAdduserid(sysUser.getUserid());
					model.setAddsignname(sysUser.getName());
					model.setModifyuserid(sysUser.getUserid());
					model.setModifysignname(sysUser.getName());
					model.setState("1");
					model.setDatasource("2");
					flag=addTableInfo(model) || flag;
					if(flag){
							queryMap=new HashMap();
							queryMap.put("selectcolumnlist", null);
							queryMap.put("tablename", model.getTablename());
							List<TableColumn> tclist=getDBUnimportColumnList(queryMap);
							if(tclist!=null && tclist.size()>0){
								queryMap=new HashMap();
								int i = 0;
								for(TableColumn tcmodel : tclist){
									queryMap.put("tablename", tcmodel.getTablename());
									queryMap.put("columnname", tcmodel.getColumnname());
									if(!"".equals(tcmodel.getTablename())
											&& !"".equals(tcmodel.getColumnname())
											&& getTableColumnCountBy(queryMap)==0){
										i++;
										tcmodel.setAdduserid(sysUser.getUserid());
										tcmodel.setModifyuserid(sysUser.getUserid());;
										tcmodel.setColorder(i);
										addTableColumn(tcmodel);
									}
								}
							}
					}
				}
			}
		}
		return flag;
	}

	// **************************************//
	// 表结构部分
	// **************************************//


	/**
	 * 显示所有表列表
	 */
	@Override
	public List showTableColumnList() throws Exception {
		List list = tableColumnMapper.getTableColumnList();
		return list;
	}

	@Override
	public PageData getTableColumnPageAllList(PageMap pageMap) throws Exception {
		PageData pageData = new PageData(tableColumnMapper
				.getTableColumnCount(pageMap), tableColumnMapper
				.getTableColumnPageAllList(pageMap), pageMap);
		return pageData;
	}

	/**
	 * 显示表分页信息
	 */
	@Override
	public PageData showTableColumnPageList(PageMap pageMap) throws Exception {
		PageData pageData = new PageData(tableColumnMapper
				.getTableColumnCount(pageMap), tableColumnMapper
				.getTableColumnPageList(pageMap), pageMap);
		return pageData;
	}

	@Override
	public TableColumn showTableColumn(String id) throws Exception {
		TableColumn tableColumn = tableColumnMapper.showTableColumn(id);
		return tableColumn;
	}
	@Override
	public TableColumn getColumnByTableColumn(String tablename,String column) throws Exception{
		TableColumn tableColumn = tableColumnMapper.getColumnByTableColumn(tablename, column);
		return tableColumn;
	}
	@Override
	public boolean editTableColumn(TableColumn tableColumn) throws Exception {
		int irows = tableColumnMapper.updateTableColumn(tableColumn);
		return irows > 0;
	}

	@Override
	public boolean addTableColumn(TableColumn tableColumn) throws Exception {
		int irows = tableColumnMapper.addTableColumn(tableColumn);
		return irows > 0;
	}

	@Override
	public boolean deleteTableColumn(String id) throws Exception {
		int irows = tableColumnMapper.deleteTableColumn(id);
		return irows > 0;
	}

	/**
	 * 根据表名删除表结构信息
	 * 
	 * @param tablename
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2012-12-10
	 */
	public boolean deleteTableColumnByTablename(String tablename)
			throws Exception {
		int irows = tableColumnMapper.deleteTableColumnByTablename(tablename);
		return irows > 0;
	}

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
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2012-12-19
	 */
	@Override
	public int getTableColumnCountBy(Map queryMap)
			throws Exception {
		int irows = tableColumnMapper.getTableColumnCountBy(queryMap);
		return irows;
	}
	@Override
	public TableColumn getTableColumnInfo(String tablename,String column) throws Exception{
		TableColumn tableColumn = tableColumnMapper.getTableColumnInfo(tablename, column);
		return tableColumn;
	}
	@Override
	public List getTableColumnListBy(Map queryMap) throws Exception {
		List list=tableColumnMapper.getTableColumnListBy(queryMap);
		return list;
	}
	

	/**
	 * 获取表数据库中的结构
	 * queryMap所对应key值
	 * tablename: 表名
	 * selectcolumnlist: 选中导入的结构名
	 * @param queryMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2012-12-25
	 */
	@Override
	public List getDBUnimportColumnList(Map queryMap) throws Exception{
		List list= tableColumnMapper.getDBUnimportColumnList(queryMap);
		return list;
	}
	
	/**
	 * 获取数据库中的对应的表结构<br/> 
	 * queryMap所对应key值<br/> 
	 * tablename: 表名<br/> 
	 * @param queryMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2012-12-25
	 */
	public List getDBTableColumnList(Map queryMap) throws Exception{
		List list=tableColumnMapper.getDBTableColumnList(queryMap);
		return list;
	}
	@Override
	public boolean addTableColumnDBImport(String tablename,String[] columnarr) throws Exception{
		Map queryMap=new HashMap();
		queryMap.put("selectcolumnlist", columnarr);
		queryMap.put("tablename", tablename);
		List<TableColumn> list=getDBUnimportColumnList(queryMap);
		if(list==null || list.size()==0){
			if(columnarr.length==0){
				return false;
			}
		}
		boolean flag=false;
		SysUser sysUser=getSysUser();
		queryMap=new HashMap();
		for(TableColumn model : list ){
			queryMap.put("tablename", model.getTablename());
			queryMap.put("columnname", model.getColumnname());
			int i = 0;
			if(!"".equals(model.getTablename())
					&& !"".equals(model.getColumnname())
					&& getTableColumnCountBy(queryMap)==0){
				i++;
				model.setAdduserid(sysUser.getUserid());
				model.setModifyuserid(sysUser.getUserid());;
				model.setColorder(i);
				flag=addTableColumn(model) || flag;
			}
		}
		return flag;
	}
	
	

	// **************************************//
	// 表关联Service
	// **************************************//

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
	@Override
	public List getTableRelationListBy(Map queryMap) throws Exception{
		List list=tableRelationMapper.getTableRelationListBy(queryMap);
		return list;
	}
	@Override
	public boolean addTableRelation(TableRelation tableRelation)
			throws Exception {
		int irows = tableRelationMapper.addTableRelation(tableRelation) ;
		return irows>0;
	}

	@Override
	public boolean deleteTableRelation(String id) throws Exception {
		int irows = tableRelationMapper.deleteTableRelation(id) ;
		return irows>0;
	}

	@Override
	public boolean editTableRelation(TableRelation tableRelation)
			throws Exception {
		int irows = tableRelationMapper.updateTableRelation(tableRelation) ;
		return irows>0;
	}

	@Override
	public TableRelation showTableRelation(String id) throws Exception {
		return tableRelationMapper.showTableRelation(id);
	}

	/**
	 * 获取所有数据
	 * 
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2012-12-19
	 */
	@Override
	public List showTableRelationList() throws Exception {
		List list=tableRelationMapper.getTableRelationList();
		return list;
	}

	/**
	 * 根据分页参数查询分页数据量
	 * 
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2012-12-19
	 */
	@Override
	public PageData showTableRelationPageList(PageMap pageMap) throws Exception {
		PageData pageData = new PageData(tableRelationMapper
				.getTableRelationCount(pageMap), tableRelationMapper
				.getTableRelationPageList(pageMap), pageMap);
		return pageData;
	}


	/**
	 * 根据主表名、请表字段名、子表名和子表字段名判断，表关联中是否存在该数据数
	 * queryMap里键值参数<br/>  
	 * tablename:从属表名<br/> 
	 * columnname:从属表字段名<br/> 
	 * oldtablename:原始从属表名<br/> 
	 * oldcolumnname:原始从属表字段名<br/> 
	 * @param queryMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-19
	 */
	public int getTableRelationCountBySubNames(Map queryMap) throws Exception {
		int irows = tableRelationMapper.getTableRelationCountBySubNames(queryMap);
		return irows;
	}
	/**
	 * 根据从表表名和从表字段名，获取表关联信息
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-5
	 */
	public TableRelation getTableRelationBySubNames(String tablename,String columnname)throws Exception {
		TableRelation tableRelation = tableRelationMapper.getTableRelationBySubNames(tablename, columnname);
		return tableRelation;
	}
	/**
	 * 根据分页参数获取相关的表数据字典
	 * @param pageMap
	 * @return List
	 * @author zhanghonghui
	 * @date 2012-12-14
	 */
	public List getTableDataDictList(PageMap pageMap)throws Exception{
		List list=tableRelationMapper.getTableDataDictList(pageMap);
		return list;
	}
	
	/**
	 * 根据参数获取相关分页后的表数据字典
	 * @return 
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-14
	 */
	public PageData getTableDataDictPageList(PageMap pageMap) throws Exception{
		PageData pageData = new PageData(tableRelationMapper
				.getTableDataDictCount(pageMap.getCondition()), tableRelationMapper
				.getTableDataDictList(pageMap), pageMap);
		return pageData;
	}
	
	public Map getTableDataDictHandleResult(PageMap pageMap) throws Exception{
		boolean usepagelist=false;
		boolean usetreelist=false;
		boolean flag=false;
		Map condition=pageMap.getCondition();
		Map msgMap=new HashMap();
		if(!condition.containsKey("tablename") || !condition.containsKey("columnname")|| "".equals(condition.get("tablename").toString()) || "".equals(condition.get("columnname").toString()) ){
			msgMap.put("flag", flag);
			msgMap.put("msg", "抱歉，参数不全");
			return msgMap;
		}
		if(condition.containsKey("page") && !"".equals(condition.get("page").toString())){
			//是否使用分页
			condition.put("usepagelist", true);
			usepagelist=true;
		}else{
			if(condition.containsKey("usepagelist")){
				condition.remove("usepagelist");
			}
		}
		TableRelation  tableRelation=this.getTableRelationBySubNames(condition.get("tablename").toString().toLowerCase(), condition.get("columnname").toString().toLowerCase());
		if(tableRelation==null){			
			msgMap.put("flag", flag);
			msgMap.put("msg", "抱歉，未能找到相关信息");
			return msgMap;
		}
		TableInfo tableInfo=this.showTableInfo(tableRelation.getMaintablename());
		if(tableInfo==null){			
			msgMap.put("flag", flag);
			msgMap.put("msg", "抱歉，未能找到相关信息");
			return msgMap;
		}
		//usetreelist为1的时候，表示使用树,分页信息无效
		if("1".equals(tableInfo.getUsetreelist())&& !"".equals(tableInfo.getRefertreecol())){
			condition.put("refertreecol", tableInfo.getRefertreecol());
			usepagelist=false;
			usetreelist=true;
			if(condition.containsKey("usepagelist")){
				condition.remove("usepagelist");
			}
		}else{
			if(condition.containsKey("refertreecol")){
				condition.remove("refertreecol");
			}
		}
		//从属表表名
		condition.put("tablename", tableRelation.getMaintablename());
		//从属表列名
		condition.put("columnname", tableRelation.getMaincolumnname());
		//从属表显示列名
		condition.put("coltitlename", tableRelation.getMaintitlecolname());
		pageMap.setCondition(condition);
		msgMap.put("flag", true);
		msgMap.put("msg", "查询成功！");
		if(usepagelist){
			PageData pageData=this.getTableDataDictPageList(pageMap);
			msgMap.put("dataview", "page");
			msgMap.put("data", pageData);
		}else{
			List<TableDataDict> list=this.getTableDataDictList(pageMap);
			if(usetreelist){
				List treeList = getTableDataDictTree(list);
				msgMap.put("dataview", "tree");
				msgMap.put("data", treeList);
			}else{
				msgMap.put("dataview", "list");
				msgMap.put("data", list);
			}
		}
		return msgMap;
	}
	/**
	 * 把list转成tree格式
	 * @param list
	 * @return
	 * @author chenwei 
	 * @date 2013-1-8
	 */
	public List getTableDataDictTree(List<TableDataDict> list){
		List treeList = new ArrayList();
		for(TableDataDict tableDataDict : list){
			if(null==tableDataDict.getParentid()||"".equals(tableDataDict.getParentid())||"root".equals(tableDataDict.getParentid())){
				getTableDataDictTreeChild(tableDataDict,list);
				treeList.add(tableDataDict);
			}
		}
		return treeList;
	}
	/**
	 * 递归查找子节点
	 * @param tableDataDict
	 * @param list
	 * @author chenwei 
	 * @date 2013-1-8
	 */
	public void getTableDataDictTreeChild(TableDataDict tableDataDict,List<TableDataDict> list){
		List childList = new ArrayList();
		for(TableDataDict tableDataDict2 : list){
			if(tableDataDict.getId().equals(tableDataDict2.getParentid())){
				getTableDataDictTreeChild(tableDataDict2,list);
				childList.add(tableDataDict2);
			}
		}
		tableDataDict.setChildren(childList);
	}
	
	@Override
	public BigDecimal getTableNumber(String tablename, String column,
			String rcolumn, String value) throws Exception{
		tablename = StringEscapeUtils.escapeSql(tablename);
		column = StringEscapeUtils.escapeSql(column);
		rcolumn = StringEscapeUtils.escapeSql( rcolumn);
		BigDecimal bigDecimal = tableRelationMapper.getTableNumber(tablename, column, rcolumn, value);
		return bigDecimal;
	}

	@Override
	public Map getTableRalationWidget(String tablename, String column,String paramRule)
			throws Exception {
		//sql防注入
		tablename = StringEscapeUtils.escapeSql(tablename);
		column = StringEscapeUtils.escapeSql(column);
		
		TableRelation tableRelation = tableRelationMapper.getTableRelationBySubNames(tablename, column);
		Map returnMap = new HashMap();
		if(null != tableRelation){
			//获取该表的数据权限
			String sqlAccess = getDataAccessRule(tableRelation.getMaintablename(), null);
			String sqlParam = "";
			if(null!=paramRule){
				Map map = new HashMap();
				map.put("alias", null);
				sqlParam = RuleJSONUtils.widgetParamToSql(paramRule,null);
				//sqlParam = RuleJSONUtils.dataRuleToSQLString(jsonrule, map);
			}else{
				sqlParam = " 1=1 ";
			}
			String sql = "";
			if(null != sqlAccess){
				sql = sqlParam +" and "+ sqlAccess;
			}else{
				sql = sqlParam;
			}
			//获取关联关系主表信息
			TableInfo tableInfo = tableInfoMapper.showTableInfo(tableRelation.getMaintablename());
			
			//获取主表能显示的字段
			Map queryMap = new HashMap();
			queryMap.put("tablename",tableRelation.getMaintablename());
			queryMap.put("isshow", "1");
			List<TableColumn> columList = tableColumnMapper.getTableColumnListBy(queryMap);
			String columnSql = "";
			for(TableColumn tableColumn : columList){
				if("".equals(columnSql)){
					columnSql += tableColumn.getColumnname(); 
				}else{
					columnSql += ","+tableColumn.getColumnname(); 
				}
			}
			//当表结构为树状时
			if("1".equals(tableInfo.getUsetreelist())){
				String columnTreeSql = tableRelation.getMaincolumnname()+" as id,"+tableRelation.getMaintitlecolname()+" as name,"+ tableInfo.getRefertreecol()+" as pId ";
				String initsql = "";
				for(TableColumn tableColumn : columList){
					if(!"id".equals(tableColumn.getColumnname()) || !"name".equals(tableColumn.getColumnname()) || !"pId".equals(tableColumn.getColumnname())){
						if("".equals(initsql)){
							initsql += tableColumn.getColumnname(); 
						}else{
							initsql += ","+tableColumn.getColumnname(); 
						}
					}
				}
				List list = tableRelationMapper.getTableDataList(tableRelation.getMaintablename(), columnTreeSql+","+initsql, sql);
				//数据转为树状结构数据
//				List treeList = getTableDataDictTree(list);
				List data = CommonUtils.getTableMapDataTree(list);
				//组装返回的map对象
				returnMap.put("data", data);
				returnMap.put("tree", true);
			}else{	//当表结构不是树状时
				
				List list = tableRelationMapper.getTableDataList(tableRelation.getMaintablename(), columnSql, sql);
				TableColumn column1 = tableColumnMapper.getTableColumnInfo(tableRelation.getMaintablename(), tableRelation.getMaincolumnname());
				Map columnMap = new HashMap();
				columnMap.put("idvalue", tableRelation.getMaincolumnname());
				columnMap.put("id", column1.getColchnname());
				TableColumn column2 = tableColumnMapper.getTableColumnInfo(tableRelation.getMaintablename(), tableRelation.getMaintitlecolname());
				columnMap.put("namevalue", tableRelation.getMaintitlecolname());
				columnMap.put("name", column2.getColchnname());
				//组装返回的map对象
				returnMap.put("column", columnMap);
				returnMap.put("data", list);
				returnMap.put("tree", false);
			}
			returnMap.put("type", "relation");
		}else{
			returnMap.put("type", "normal");
		}
		return returnMap;
	}

	@Override
	public PageData getWidgetTableDataList(String tablename,PageMap pageMap,String paramRule) throws Exception {
		tablename = StringEscapeUtils.escapeSql(tablename);
		
		TableInfo tableInfo = tableInfoMapper.showTableInfo(tablename);
		
		//获取该表的数据权限
		String sqlAccess = getDataAccessRule(tablename, null);
		//生成访问该表的sql
		String sqlParam = "";
		if(null!=paramRule){
			Map map = new HashMap();
			map.put("alias", null);
			sqlParam = RuleJSONUtils.widgetParamToSql(paramRule,null);
		}else{
			sqlParam = " 1=1 ";
		}
		String querySql = "";
		if(pageMap.getCondition().containsKey("col")&&pageMap.getCondition().containsKey("content")){
			String col = StringEscapeUtils.escapeSql((String) pageMap.getCondition().get("col"));
			String content = StringEscapeUtils.escapeSql((String) pageMap.getCondition().get("content"));
			if(null!=col && !"".equals(col) && null!=content && !"".equals(content)){
				querySql = " and " +col +" like '%"+content+"%'";
			}
			
		}
		String sql = "";
		if(null != sqlAccess){
			sql = sqlParam +" and "+ sqlAccess;
		}else{
			sql = sqlParam;
		}
		sql += querySql;
		//获取该表能显示的字段
		Map queryMap = new HashMap();
		queryMap.put("tablename",tablename);
		queryMap.put("isshow", "1");
		List<TableColumn> columList = tableColumnMapper.getTableColumnListBy(queryMap);
		String columnSql = "";
		for(TableColumn tableColumn : columList){
			if("".equals(columnSql)){
				columnSql += tableColumn.getColumnname(); 
			}else{
				columnSql += ","+tableColumn.getColumnname(); 
			}
		}
		String tree = (String) pageMap.getCondition().get("tree");
		pageMap.getCondition().put("columnSql", columnSql);
		pageMap.getCondition().put("tablenameSql", tablename);
		pageMap.getCondition().put("sqlStr", sql);
		
		List<Map> list = tableRelationMapper.getWidgetTreeDataList(pageMap);
		//获取需要转码的字段列表以及编码表数据
		List<String[]> codeList = new ArrayList();
		Map codeMap = new HashMap();
		for(TableColumn tableColumn : columList){
			if("1".equals(tableColumn.getUsecoded())){
				if(!codeMap.containsKey(tableColumn.getCodedcoltype())){
					//获取编码列表
					Map code =  getCodeListByType(tableColumn.getCodedcoltype());
					codeMap.put(tableColumn.getCodedcoltype(), code);
				}
				String[] type = new String[2];
				type[0] = tableColumn.getColumnname();
				type[1] = tableColumn.getCodedcoltype();
				codeList.add(type);
			}
		}
		//数组克隆复制 对数据进行转码时，防止影响缓存的数据
		List<Map> dataList = (List<Map>) CommonUtils.deepCopy(list);
		//对数据进行转码
		for(Map map : dataList){
			for(String[] codetype : codeList){
				String col = codetype[0];
				String type = codetype[1];
				Map sysCode = (Map) codeMap.get(type);
				
				Object o = map.get(col);
				String value = (String) sysCode.get(o);
				map.put(col, value);
			}
		}
		PageData pageData = new PageData(tableRelationMapper.getWidgetTreeDataCount(pageMap),dataList,pageMap);
		return pageData;
	}

	@Override
	public boolean canTableDataDelete(String tname, String id)
			throws Exception {
		boolean flag = canTableDataDictDelete(tname, id);
		return flag;
	}

	@Override
	public String getDataState(String tablename, String id, String state)
			throws Exception {
		tablename =  StringEscapeUtils.escapeSql(tablename);
		id =  StringEscapeUtils.escapeSql(id);
		state =  StringEscapeUtils.escapeSql(state);
		String value = tableRelationMapper.getDataState(tablename, id, state);
		return value;
	}
	@Override
	public Map getBaseFilesEditState(String tablename, Object object)
			throws Exception {
		Map map = canTableDataDictEdit(tablename, object);
		
		Map objectMap = BeanUtils.describe(object);
		String state = (String) objectMap.get("state");
		//对象状态处于启用时，获取该表处于启用时，不可以修改的字段列表
		if("1".equals(state)){
			Map queryMap = new HashMap();
			queryMap.put("tablename", tablename);
			queryMap.put("isopenedit", "0");
			List<TableColumn> list =  tableColumnMapper.getTableColumnListBy(queryMap);
			for (TableColumn column : list){
				map.put(column.getColumnname(), false);
			}
		}
		return map;
	}

	@Override
	public List getRequiredColumnByTable(String tablename) throws Exception {
		List<String> retList = new ArrayList<String>();
		List<TableColumn> list = tableColumnMapper.getRequiredColumnByTable(tablename);
		if(list.size() != 0){
			for(TableColumn tableColumn : list){
				if(null != tableColumn){
					retList.add(tableColumn.getColumnname());
				}
			}
		}
		return retList;
	}
	
}
