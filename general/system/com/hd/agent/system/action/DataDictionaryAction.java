/**
 * @(#)TableInfoAction.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-15 zhanghonghui 创建版本
 */
package com.hd.agent.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.util.TokenHelper;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.system.model.TableColumn;
import com.hd.agent.system.model.TableInfo;
import com.hd.agent.system.model.TableRelation;
import com.hd.agent.system.service.IDataDictionaryService;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class DataDictionaryAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7243334215444039966L;
	/**
	 * 表描述信息
	 */
	private TableInfo tableInfo;
	/**
	 * 表结构信息
	 */
	private TableColumn tableColumn;
	/**
	 * 表关联信息
	 */
	private TableRelation tableRelation;
	/**
	 * 表描述信息业务层
	 */
	private IDataDictionaryService dataDictionaryService;

	public TableInfo getTableInfo() {
		return tableInfo;
	}

	public void setTableInfo(TableInfo tableInfo) {
		this.tableInfo = tableInfo;
	}
	
	public TableColumn getTableColumn() {
		return tableColumn;
	}
	public void setTableColumn(TableColumn tableColumn) {
		this.tableColumn = tableColumn;
	}

	public TableRelation getTableRelation() {
		return tableRelation;
	}

	public void setTableRelation(TableRelation tableRelation) {
		this.tableRelation = tableRelation;
	}

    public IDataDictionaryService getDataDictionaryService() {
        return dataDictionaryService;
    }

    public void setDataDictionaryService(IDataDictionaryService dataDictionaryService) {
        this.dataDictionaryService = dataDictionaryService;
    }

    /**
	 * 显示首页
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-13
	 */
	public String index() throws Exception{
		return "success";
	}
	
	/**
	 * 显示所有表描述信息列表信息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public String showTableInfoAllList() throws Exception{
		List list=dataDictionaryService.showTableInfoList();
		addJSONArray(list);
		return "success";
	}

	/**
	 * 显示表描述信息管理列表页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public String showTableInfoListPage() throws Exception{
		return "success";
	}
	
	/**
	 * 显示表描述信息管理列表数据，返回Json格式数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public String showTableInfoPageList() throws Exception {
		// 获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		// map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData=dataDictionaryService.showTableInfoPageList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 显示表描述信息管理添加页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public String showTableInfoAddPage() throws Exception{
		return "success";
	}	
	/**
	 * 显示表描述信息管理修改页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public String showTableInfoEditPage() throws Exception{
		String tablename=request.getParameter("tablename");
		TableInfo tableInfo=dataDictionaryService.showTableInfo(tablename);
		//往页面传值
		request.setAttribute("tableInfo", tableInfo);
		return "success";
	}
	/**
	 * 显示表描述信息管理复制页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public String showTableInfoCopyPage() throws Exception{
		String tablename=request.getParameter("tablename");
		TableInfo tableInfo=dataDictionaryService.showTableInfo(tablename);
		//往页面传值
		request.setAttribute("tableInfo", tableInfo);
		return "success";
	}
	/**
	 * 添加表描述信息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="DataDictionary-TableInfo",type=2)
	public String addTableInfo() throws Exception{
		//验证表单是否重复提交
		//需要在jsp页面的form表单下加上<s:token/>标签
		if(!TokenHelper.validToken()){
			return "error";
		}
		//操作标志
		boolean flag=false;
		Map msgMap=new HashMap();
		if("".equals(tableInfo.getTablename())){
			msgMap.put("flag", flag);
			msgMap.put("msg", "请输入表名");
			
			addJSONObject(msgMap);
			return "success";
		}
		if(dataDictionaryService.getTableInfoCountBy(tableInfo.getTablename())>0){
			msgMap.put("flag", flag);
			msgMap.put("msg", "已经表名("+tableInfo.getTablename()+")存在");
			
			addJSONObject(msgMap);
			return "success";
		}
		SysUser sysUser=getSysUser();
		tableInfo.setAdduserid(sysUser.getUserid());
		if("".equals(tableInfo.getAddsignname())){
			tableInfo.setAddsignname(sysUser.getName());
		}
		tableInfo.setModifysignname(tableInfo.getAddsignname());
		tableInfo.setModifyuserid(sysUser.getUserid());
		flag = dataDictionaryService.addTableInfo(tableInfo);
		//返回json字符串
		addJSONObject("flag",flag);
		
		addLog("添加数据字典 表名:"+tableInfo.getTablename(), flag);
		return "success";
	}

	/**
	 * 编辑表描述信息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="DataDictionary-TableInfo",type=3)
	public String editTableInfo() throws Exception{
		//操作标志
		boolean flag=false;
		Map msgMap=new HashMap();
		if("".equals(tableInfo.getOldtablename())){
			msgMap.put("flag", flag);
			msgMap.put("msg", "抱歉，参数不完整，请刷新表单");
			
			addJSONObject(msgMap);
			return "success";
		}
		if("".equals(tableInfo.getTablename())){
			msgMap.put("flag", flag);
			msgMap.put("msg", "请输入表名");

			addJSONObject(msgMap);
			return "success";
		}
		if(!tableInfo.getTablename().toLowerCase().equals(tableInfo.getOldtablename().toLowerCase()) && dataDictionaryService.getTableInfoCountBy(tableInfo.getTablename())>0){
			msgMap.put("flag", flag);
			msgMap.put("msg", "已经表名("+tableInfo.getTablename()+")存在");
			addJSONObject(msgMap);
			return "success";
		}
		tableInfo.setTablename(tableInfo.getTablename().toLowerCase());
		tableInfo.setOldtablename(tableInfo.getOldtablename().toLowerCase());
		//用户实例
		SysUser sysUser=getSysUser();
		tableInfo.setAdduserid(sysUser.getUserid());
		if("".equals(tableInfo.getModifysignname())){
			tableInfo.setModifysignname(sysUser.getName());
		}
		tableInfo.setModifyuserid(sysUser.getUserid());
		flag = dataDictionaryService.editTableInfo(tableInfo);
		//返回json字符串
		addJSONObject("flag",flag);
		
		addLog("修改数据字典 表名:"+tableInfo.getTablename(), flag);
		return "success";
	}
	
	/**
	 * 添加表描述信息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="DataDictionary-TableInfo",type=2)
	public String tableInfoCopy() throws Exception{
		//操作标志
		Map msgMap=new HashMap();
		if("".equals(tableInfo.getTablename())){
			msgMap.put("flag", false);
			msgMap.put("msg", "请输入表名");
			
			addJSONObject(msgMap);
			return "success";
		}
		SysUser sysUser=getSysUser();
		tableInfo.setAdduserid(sysUser.getUserid());
		if("".equals(tableInfo.getAddsignname())){
			tableInfo.setAddsignname(sysUser.getName());
		}
		tableInfo.setModifysignname(tableInfo.getAddsignname());
		tableInfo.setModifyuserid(sysUser.getUserid());
		Map resultMap = dataDictionaryService.copyTableInfoAddColumn(tableInfo);
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
				resultMap.put("flag", false);
			}
		}else{
			resultMap=new HashMap();
			resultMap.put("flag", false);
		}
		//返回json字符串
		addJSONObject(resultMap);
		
		addLog("复制数据字典 表名:"+tableInfo.getTablename(), flag);
		return "success";
	}
	
	/**
	 * 删除表描述信息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-18
	 */
	@UserOperateLog(key="DataDictionary-TableInfo",type=4)
	public String deleteTableInfo() throws Exception{
		String tablename = request.getParameter("tablename");
		Map mapMsg=new HashMap();
		boolean flag = false;
		if("".equals(tablename)){
			mapMsg.put("flag", flag);
			mapMsg.put("msg", "抱歉，表名不能为空！");

			addJSONObject(mapMsg);
			return "success";
		}
		flag = dataDictionaryService.deleteTableInfo(tablename.toLowerCase());
		//返回json字符串
		addJSONObject("flag",flag);
		
		addLog("删除数据字典 表名:"+tablename, flag);
		return "success";
	}
	/**
	 * 判断表名是否存在
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-19
	 */
	public String existsTableInfoByTablename() throws Exception{
		String tablename=request.getParameter("tablename");
		int irows=dataDictionaryService.getTableInfoCountBy(tablename);
		boolean flag=irows>0?true:false;
		addJSONObject("flag", flag);
		return "success";
	}

	/**
	 * 显示表描述导入页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-19
	 */
	public String showTableInfoDBImportListPage() throws Exception{
		return "success";		
	}
	
	/**
	 * 显示表描述导入数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-19
	 */
	public String showTableInfoDBImportList() throws Exception{
		Map queryMap=new HashMap();
		queryMap.put("selectcolumnlist", null);
		List list=dataDictionaryService.getDBUnimportTableList(queryMap);
		addJSONArray(list);
		return "success";		
	}
	
	/**
	 * 导入表名
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-19
	 */
	@UserOperateLog(key="DataDictionary-TableInfo",type=2)
	public String addTableInfoDBImport() throws Exception{
		String selectedtables=request.getParameter("selectedtables");
		String[] tablearr=selectedtables.split(",");
		if(tablearr.length==0){
			Map msgMap=new HashMap();
			msgMap.put("flag", false);
			msgMap.put("msg", "请选择要导入的表名");
			
			addJSONObject(msgMap);
			return "success";
		}
		boolean flag=dataDictionaryService.addTableInfoDBImport(tablearr);
		addJSONObject("flag",flag);
		
		addLog("导入数据字典 ", true);
		return "success";
	}
	
	// **************************************//
	// 表结构Action
	// **************************************//

	
	/**
	 * 显示表结构信息管理列表页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public String showTableColumnListPage() throws Exception{
		return "success";
	}
	
	/**
	 * 显示表结构信息管理列表数据，返回Json格式数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public String showTableColumnPageAllList() throws Exception {
		// 获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		// map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData=dataDictionaryService.getTableColumnPageAllList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 显示表结构信息管理添加页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public String showTableColumnAddPage() throws Exception{
		String tablename=request.getParameter("tablename");
		String divid = request.getParameter("divid");
		request.setAttribute("tablename", tablename);
		request.setAttribute("divid", divid);
		int icount=10;
		if(null!=tablename && !"".equals(tablename.trim())){
			Map queryMap=new HashMap();
			queryMap.put("tablename", tablename);
			icount=dataDictionaryService.getTableColumnCountBy(queryMap);
			icount=icount+1;
			request.setAttribute("icount", icount);
		}
		return "success";
	}	
	/**
	 * 显示表结构信息管理修改页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public String showTableColumnEditPage() throws Exception{
		String id=request.getParameter("id");
		String divid = request.getParameter("divid");
		TableColumn tableColumn=dataDictionaryService.showTableColumn(id);
		//往页面传值
		request.setAttribute("tableColumn", tableColumn);
		request.setAttribute("divid", divid);
		return "success";
	}
	/**
	 * 添加表结构信息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="TableManage-TableColumn",type=2)
	public String addTableColumn() throws Exception{
		//操作标志
		boolean flag=false;
		Map msgMap=new HashMap();
		if("".equals(tableColumn.getTablename())){
			msgMap.put("flag", flag);
			msgMap.put("msg", "请输入表名");
			
			addJSONObject(msgMap);
			return "success";
		}
		if("".equals(tableColumn.getColumnname())){
			msgMap.put("flag", flag);
			msgMap.put("msg", "请输入列名");
			
			addJSONObject(msgMap);
			return "success";
		}
		Map queryMap=new HashMap();
		queryMap.put("tablename", tableColumn.getTablename());
		queryMap.put("columnname", tableColumn.getColumnname());
		//usecoded="1" 表示使用编码字段
		if("1".equals(tableColumn.getUsecoded())&& dataDictionaryService.getTableRelationCountBySubNames(queryMap)>0){
			msgMap.put("flag", flag);
			msgMap.put("msg", "表"+tableColumn.getTablename()+"的中列名"+tableColumn.getColumnname()+"已经在表关联中使用，因此不能做为编码字段");

			addJSONObject(msgMap);
			return "success";
			
		}
		if(dataDictionaryService.getTableColumnCountBy(queryMap)>0){
			msgMap.put("flag", flag);
			msgMap.put("msg", "表"+tableColumn.getTablename()+"的中列名"+tableColumn.getColumnname()+"已经存在");

			addJSONObject(msgMap);
			return "success";
		}
		SysUser sysUser=getSysUser();
		tableColumn.setAdduserid(sysUser.getUserid());
		tableColumn.setModifyuserid(sysUser.getUserid());
		tableColumn.setTablename(tableColumn.getTablename().toLowerCase());
		tableColumn.setColumnname(tableColumn.getColumnname().toLowerCase());
		flag = dataDictionaryService.addTableColumn(tableColumn);
		//返回json字符串
		addJSONObject("flag",flag);
		addLog("添加 表结构信息 表名:"+tableColumn.getTablename(), flag);
		return "success";
	}

	/**
	 * 编辑表结构信息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="TableManage-TableColumn",type=3)
	public String editTableColumn() throws Exception{
		//操作标志
		boolean flag=false;
		Map msgMap=new HashMap();
		if("".equals(tableColumn.getTablename())){
			msgMap.put("flag", flag);
			msgMap.put("msg", "请填写表名");
			
			addJSONObject(msgMap);
			return "success";
		}
		if("".equals(tableColumn.getColumnname())){
			msgMap.put("flag", flag);
			msgMap.put("msg", "请填写列名");
			
			addJSONObject(msgMap);
			return "success";
		}

		Map queryMap=new HashMap();
		queryMap.put("tablename", tableColumn.getTablename());
		queryMap.put("columnname", tableColumn.getColumnname());
		//usecoded="1" 表示使用编码字段
//		if("1".equals(tableColumn.getUsecoded())&& dataDictionaryService.getTableRelationCountBySubNames(queryMap)>0){
//			msgMap.put("flag", flag);
//			msgMap.put("msg", "表"+tableColumn.getTablename()+"的中列名"+tableColumn.getColumnname()+"已经在表关联中使用，因此不能做为编码字段");
//
//			addJSONObject(msgMap);
//			return "success";
//			
//		}
		if(!"".equals(tableColumn.getOldcolumnname())
				&& !tableColumn.getTablename().toLowerCase().equals(tableColumn.getOldcolumnname().toLowerCase()) 
				&& dataDictionaryService.getTableColumnCountBy(queryMap)>0){
			msgMap.put("flag", flag);
			msgMap.put("msg", "已经表"+tableColumn.getTablename()+"的中列名"+tableColumn.getColumnname()+"存在");

			addJSONObject(msgMap);
			return "success";
		}
		SysUser sysUser=getSysUser();
		tableColumn.setModifyuserid(sysUser.getUserid());
		tableColumn.setTablename(tableColumn.getTablename().toLowerCase());
		tableColumn.setColumnname(tableColumn.getColumnname().toLowerCase());
		
		flag = dataDictionaryService.editTableColumn(tableColumn);
		//返回json字符串
		addJSONObject("flag",flag);
		
		addLog("修改 表结构信息 表名:"+tableColumn.getTablename(), flag);
		return "success";
	}
	

	/**
	 * 删除表结构信息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-18
	 */
	@UserOperateLog(key="TableManage-TableColumn",type=4)
	public String deleteTableColumn() throws Exception{
		String id=request.getParameter("id");
		boolean flag=dataDictionaryService.deleteTableColumn(id);
		//返回json字符串
		addJSONObject("flag", flag);
		
		addLog("删除 表结构信息 编号:"+id, flag);
		return "success";
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
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-19
	 */
	public String existsTableColumnBy() throws Exception{
		Map queryMap= CommonUtils.changeMap(request.getParameterMap());
		int irows=dataDictionaryService.getTableColumnCountBy(queryMap);
		boolean flag=irows>0?true:false;
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 显示表结构导入页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-19
	 */
	public String showTableColumnDBImportListPage() throws Exception{
		String tablename=request.getParameter("tablename");
		String divid = request.getParameter("divid");
		//往页面传值
		request.setAttribute("tablename", tablename);
		request.setAttribute("divid", divid);
		return "success";		
	}
	
	/**
	 * 显示表结构导入数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-19
	 */
	public String showTableColumnDBImportList() throws Exception{
		String tablename=request.getParameter("tablename");
		Map queryMap=new HashMap();
		queryMap.put("dbname", getCurrentSetOfBookDbName());
		queryMap.put("tablename", tablename);
		queryMap.put("selectcolumnlist", null);
		List list=dataDictionaryService.getDBUnimportColumnList(queryMap);
		addJSONArray(list);
		return "success";		
	}
	
	/**
	 * 导入表结构
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-19
	 */
	@UserOperateLog(key="DataDictionary-TableColumn",type=2)
	public String addTableColumnDBImport() throws Exception{
		//操作标志
		boolean flag=false;
		Map msgMap=new HashMap();
		String tablename=request.getParameter("tablename");
		if("".equals(tablename)){
			msgMap.put("flag", flag);
			msgMap.put("msg", "请输入表名");
			
			addJSONObject(msgMap);
			return "success";
		}
		String selectedcolumns=request.getParameter("selectedcolumns");
		if("".equals(selectedcolumns)){
			msgMap.put("flag", flag);
			msgMap.put("msg", "请选择列名");
			
			addJSONObject(msgMap);
			return "success";
		}
		String[] columnarr=selectedcolumns.split(",");
		if(columnarr.length==0){
			msgMap.put("flag", flag);
			msgMap.put("msg", "请选择列名");
			
			addJSONObject(msgMap);
			return "success";
		}
		flag=dataDictionaryService.addTableColumnDBImport(tablename, columnarr);
		addJSONObject("flag",flag);
		
		addLog("导入表结构信息 表名:"+tablename, true);
		return "success";
	}
	
	/**
	 * 显示数据库中的对应的表结构
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-19
	 */
	public String showDBTableColumnList() throws Exception{
		String tablename=request.getParameter("tablename");
		Map queryMap=new HashMap();
		queryMap.put("tablename", tablename);
		List list=dataDictionaryService.getDBTableColumnList(queryMap);
		addJSONArray(list);
		return "success";		
	}
	

	// **************************************//
	// 表关联Action
	// **************************************//
	
	/**
	 * 显示表关联信息管理列表页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public String showTableRelationListPage() throws Exception{
		return "success";
	}
	
	/**
	 * 显示表关联信息管理列表数据，返回Json格式数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public String showTableRelationPageList() throws Exception {
		// 获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		// map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData=dataDictionaryService.showTableRelationPageList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 显示表关联信息管理添加页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public String showTableRelationAddPage() throws Exception{
		return "success";
	}	
	/**
	 * 显示表描述信息管理修改页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public String showTableRelationEditPage() throws Exception{
		String id=request.getParameter("id");
		TableRelation tableRelation=dataDictionaryService.showTableRelation(id);
		//往页面传值
		request.setAttribute("tableRelation", tableRelation);
		return "success";
	}
	/**
	 * 添加表描述信息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="DataDictionary-TableRelation",type=2)
	public String addTableRelation() throws Exception{
		boolean flag=false;
		Map msgMap=new HashMap();
		
		if("".equals(tableRelation.getMaintablename())){
			msgMap.put("flag", flag);
			msgMap.put("msg", "请填写主表名");
			
			addJSONObject(msgMap);
			return "success";
		}
		if("".equals(tableRelation.getMaincolumnname())){
			msgMap.put("flag", flag);
			msgMap.put("msg", "请填写主表列名");
			
			addJSONObject(msgMap);
			return "success";
		}
		if("".equals(tableRelation.getTablename())){
			msgMap.put("flag", flag);
			msgMap.put("msg", "请填写从属表名");
			
			addJSONObject(msgMap);
			return "success";
		}
		if("".equals(tableRelation.getColumnname())){
			msgMap.put("flag", flag);
			msgMap.put("msg", "请填写从属表列名");
			
			addJSONObject(msgMap);
			return "success";
		}

		tableRelation.setMaintablename(tableRelation.getMaintablename().toLowerCase());
		tableRelation.setMaincolumnname(tableRelation.getMaincolumnname().toLowerCase());
		tableRelation.setTablename(tableRelation.getTablename().toLowerCase());
		tableRelation.setColumnname(tableRelation.getColumnname().toLowerCase());
		
		Map queryMap=new HashMap();
		queryMap.put("tablename", tableRelation.getTablename());
		queryMap.put("columnname", tableRelation.getColumnname());
		queryMap.put("usecoded", "1");//使用编码
		if(dataDictionaryService.getTableColumnCountBy(queryMap)>0){

			msgMap.put("flag", flag);
			msgMap.put("msg", "从属表"+tableRelation.getTablename()+"中的列"+tableRelation.getColumnname()+"已经做为编码使用");
			
			addJSONObject(msgMap);
			return "success";
		}
		if(dataDictionaryService.getTableRelationCountBySubNames(queryMap)>0){
			msgMap.put("flag", flag);
			msgMap.put("msg", "从属表"+tableRelation.getTablename()+"中的列"+tableRelation.getColumnname()+"已经存在");
			
			addJSONObject(msgMap);
			return "success";			
		}
		
		SysUser sysUser=getSysUser();
		tableRelation.setAdduserid(sysUser.getUserid());
		tableRelation.setModifyuserid(sysUser.getUserid());
		flag = dataDictionaryService.addTableRelation(tableRelation);
		//返回json字符串
		addJSONObject("flag",flag);
		
		addLog("添加表关联信息 编号:"+tableRelation.getId(), true);
		return "success";
	}

	/**
	 * 编辑表关联信息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="DataDictionary-TableRelation",type=3)
	public String editTableRelation() throws Exception{
		//操作标志
		boolean flag=false;
		Map msgMap=new HashMap();
		
		if("".equals(tableRelation.getMaintablename())){
			msgMap.put("flag", flag);
			msgMap.put("msg", "请填写主表名");
			
			addJSONObject(msgMap);
			return "success";
		}
		if("".equals(tableRelation.getMaincolumnname())){
			msgMap.put("flag", flag);
			msgMap.put("msg", "请填写主表列名");
			
			addJSONObject(msgMap);
			return "success";
		}
		if("".equals(tableRelation.getTablename())){
			msgMap.put("flag", flag);
			msgMap.put("msg", "请填写从属表名");
			
			addJSONObject(msgMap);
			return "success";
		}
		if("".equals(tableRelation.getColumnname())){
			msgMap.put("flag", flag);
			msgMap.put("msg", "请填写从属表列名");
			
			addJSONObject(msgMap);
			return "success";
		}
		String oldmaintablename=request.getParameter("oldmaintablename");
		String oldmaincolumnname=request.getParameter("oldmaincolumnname");
		String oldtablename=request.getParameter("oldtablename");
		String oldcolumnname=request.getParameter("oldcolumnname");
		if("".equals(oldtablename)&&"".equals(oldtablename)&&"".equals(oldtablename)&&"".equals(oldtablename)){
			msgMap.put("flag", flag);
			msgMap.put("msg", "参数不正确，请重新打开编辑页面");
			
			addJSONObject(msgMap);
			return "success";
		}

		
		tableRelation.setMaintablename(tableRelation.getMaintablename().toLowerCase());
		tableRelation.setMaincolumnname(tableRelation.getMaincolumnname().toLowerCase());
		tableRelation.setTablename(tableRelation.getTablename().toLowerCase());
		tableRelation.setColumnname(tableRelation.getColumnname().toLowerCase());
		
		Map queryMap=new HashMap();
		queryMap.put("tablename", tableRelation.getTablename());
		queryMap.put("columnname", tableRelation.getColumnname());
		queryMap.put("oldtablename", oldtablename);
		queryMap.put("oldcolumnname", oldcolumnname);
		
		queryMap.put("usecoded", "1");//使用编码
		if(dataDictionaryService.getTableColumnCountBy(queryMap)>0){

			msgMap.put("flag", flag);
			msgMap.put("msg", "从属表"+tableRelation.getTablename()+"中的列"+tableRelation.getColumnname()+"已经做为编码使用");
			
			addJSONObject(msgMap);
			return "success";
		}
		
		if(dataDictionaryService.getTableRelationCountBySubNames(queryMap)>0){
			msgMap.put("flag", flag);
			msgMap.put("msg", "您要更新的表名或者列名已经存在");
			
			addJSONObject(msgMap);
			return "success";			
		}
		
		
		//用户实例
		SysUser sysUser=getSysUser();
		tableRelation.setModifyuserid(sysUser.getUserid());
		flag = dataDictionaryService.editTableRelation(tableRelation);
		//返回json字符串
		addJSONObject("flag",flag);

		addLog("修改表关联信息 编号:"+tableRelation.getId(), true);
		return "success";
	}
	
	/**
	 * 删除表关联信息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-18
	 */
	@UserOperateLog(key="DataDictionary-TableRelation",type=4)
	public String deleteTableRelation() throws Exception{
		String id = request.getParameter("id");
		Map mapMsg=new HashMap();
		boolean flag = false;
		flag = dataDictionaryService.deleteTableRelation(id);
		//返回json字符串
		addJSONObject("flag",flag);
		addLog("删除表关联信息 编号:"+id, true);
		return "success";
	}
	/**
	 * 根据主表名、主字段、关联表名、关联表字段判断否存在表关联
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-19
	 */
	public String existsTableRelationBySubNames() throws Exception{
		Map queryMap = CommonUtils.changeMap(request.getParameterMap());
		int irows=dataDictionaryService.getTableRelationCountBySubNames(queryMap);
		boolean flag=irows>0?true:false;
		addJSONObject("flag", flag);
		return "success";
	}
}
