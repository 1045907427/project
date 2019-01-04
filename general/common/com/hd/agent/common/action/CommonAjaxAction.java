/**
 * @(#)CommonAjaxAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-25 chenwei 创建版本
 */
package com.hd.agent.common.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.Operate;
import com.hd.agent.basefiles.model.GoodsInfo_MteringUnitInfo;
import com.hd.agent.basefiles.model.MeteringUnit;
import com.hd.agent.basefiles.service.IDepartMentService;
import com.hd.agent.basefiles.service.IGoodsService;
import com.hd.agent.basefiles.service.IPersonnelService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.EhcacheUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.system.model.ReferWindowColumn;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.model.TableColumn;
import com.hd.agent.system.model.TableInfo;
import com.hd.agent.system.model.TableRelation;
import com.hd.agent.system.service.IDataDictionaryService;
import com.hd.agent.system.service.IReferWindowService;
import com.hd.agent.system.service.ISysCodeService;

/**
 * 
 * 公共方法Ajax调用
 * @author chenwei
 */
public class CommonAjaxAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1790114273908059832L;
	/**
	 * 公共代码service
	 */
	private ISysCodeService sysCodeService;
	/**
	 * 数据字典service
	 */
	private IDataDictionaryService dataDictionaryService;
	/**
	 * 参照窗口service
	 */
	private IReferWindowService referWindowService;
	
	/**
	 * 部门档案service
	 */
	private IDepartMentService departMentService;
	
	/**
	 * 人员档案service
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-30
	 */
	private IPersonnelService personnelService;
	
	/**
	 * 商品service
	 */
	private IGoodsService goodsService;
	
	public IPersonnelService getPersonnelService() {
		return personnelService;
	}
	public void setPersonnelService(IPersonnelService personnelService) {
		this.personnelService = personnelService;
	}
	public IDepartMentService getDepartMentService() {
		return departMentService;
	}
	public void setDepartMentService(IDepartMentService departMentService) {
		this.departMentService = departMentService;
	}
	
	public ISysCodeService getSysCodeService() {
		return sysCodeService;
	}

	public void setSysCodeService(ISysCodeService sysCodeService) {
		this.sysCodeService = sysCodeService;
	}
	
	public IDataDictionaryService getDataDictionaryService() {
		return dataDictionaryService;
	}

	public void setDataDictionaryService(
			IDataDictionaryService dataDictionaryService) {
		this.dataDictionaryService = dataDictionaryService;
	}

	public IReferWindowService getReferWindowService() {
		return referWindowService;
	}

	public void setReferWindowService(IReferWindowService referWindowService) {
		this.referWindowService = referWindowService;
	}
	
	public IGoodsService getGoodsService() {
		return goodsService;
	}
	public void setGoodsService(IGoodsService goodsService) {
		this.goodsService = goodsService;
	}
	/**
	 * 通过代码类型(如：type=module)调用代码列表，返货json数组
	 * 例如[{"id":"123","name":"名称"},{"id":"123","name":"名称"}]
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-25
	 */
	public String sysCodeList() throws Exception{
		String type = request.getParameter("type");
		List<SysCode> list = sysCodeService.showSysCodeListByType(type);
		List jsonList = new ArrayList();
		for(SysCode sysCode : list){
			Map map = new HashMap();
			map.put("id", sysCode.getCode());
			map.put("name", sysCode.getCodename());
			jsonList.add(map);
		}
		addJSONArray(jsonList);
		return "success";
	}
	/**
	 * 获取全部系统代码
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 29, 2013
	 */
	public String getAllSysCode() throws Exception{
		//获取缓存数据
		Map map = (Map) EhcacheUtils.getCacheData("SysCodeCache");
		if(null==map){
			List<SysCode> list = sysCodeService.showSysCodeList();
			Map datamap = new HashMap();
			for(SysCode sysCode : list){
				if(datamap.containsKey(sysCode.getType())){
					List codeList = (List) datamap.get(sysCode.getType());
					codeList.add(sysCode);
				}else{
					List codeList = new ArrayList();
					codeList.add(sysCode);
					datamap.put(sysCode.getType(), codeList);
				}
			}
			EhcacheUtils.addCache("SysCodeCache", datamap);
			map = datamap;
		}
		addJSONObject(map);
		return "success";
	}
	/**
	 * 调用代码类型，代码类型名称，返回Json数组
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-14
	 */
	public String sysCodeType() throws Exception{
		List<SysCode> list=sysCodeService.backSysCodeType();
		List jsonType = new ArrayList();
		for(SysCode sysCode : list){
			Map map = new HashMap();
			map.put("type", sysCode.getType());
			map.put("typename", sysCode.getTypename());
			jsonType.add(map);
		}
		addJSONArray(jsonType);
		return "success";
	}
	
	/**
	 * 获取数据字典定义表的列表，返回json数组 <br/>
	 * name:表名<br/> 
	 * tabletype:表类型<br/> 
	 * usehistory：使用历史库，0不使用，1使用<br/> 
	 * useversion：使用版本库，0不使用，1使用<br/> 
	 * moduletype：所属模块<br/> 
	 * state：使用状态，0停用，1启用<br/> 
	 * createmethod：创建方式，1系统预制，2自建<br/> 
	 * datasource：数据来源，1人工添加，2自动导入，3人工导入，4其他<br/> 
	 * useautoencoded:是否支持自动编号,0不支持，1支持<br/>
	 * 
	 * 返回结果： {"id":"表名","name":"表描述名"}<br/>
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-25
	 */
	public String getTableList() throws Exception{
		Map queryMap = new HashMap();
		queryMap = CommonUtils.changeMap(request.getParameterMap());
		//name表示表名 转成tablename
		if(queryMap.containsKey("name")){
			String name = (String) queryMap.get("name");
			//处理页面传递过来的表名
			name = CommonUtils.tablenameDealWith(name);
			queryMap.put("tablename", name);
		}
		if(!queryMap.containsKey("queryMap")){
			//表类型 1系统表2业务表
			queryMap.put("tabletype", "2");
		}
		if(!queryMap.containsKey("state")){
			//使用状态，0停用，1启用
			queryMap.put("state", "1");
		}
		List<TableInfo> list = dataDictionaryService.getTableInfoListBy(queryMap);
		List jsonList = new ArrayList();
		for(TableInfo tableInfo : list){
			Map map = new HashMap();
			map.put("id", tableInfo.getTablename());
			map.put("name", tableInfo.getTabledescname());
			jsonList.add(map);
		}
		addJSONArray(jsonList);
		return "success";
	}
	/**
	 * 根据表名等条件获取表拥有的字段列表<br/>
	 * name:表名<br/>
	 * usefixed：是否支持固定字段，0不使用，1使用<br/>
	 * usecoded：是否支持编码字段，0不使用，1使用<br/>
	 * colapplytype：应用类型<br/>
	 * usedataprivilege：是否支持数据权限，0不使用，1使用<br/>
	 * usecolprivilege：是否支持字段权限，0不使用，1使用<br/>
	 * usecolquery：是否可做查询条件，0不使用，1使用<br/>
	 * userAccess:查询条件是否使用字段权限控制 0不使用1使用<br/>
	 * usedataexport：是否支持数据导出，0不使用，1使用<br/>
	 * usecolrefer：是否可做参照字段，0不使用，1使用 <br/>
	 * useautoencoded:是否支持自动编号,0不支持，1支持<br/>
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-25
	 */
	public String getTableColList() throws Exception{
		Map queryMap = CommonUtils.changeMap(request.getParameterMap());
		//name表示表名 转成tablename
		if(queryMap.containsKey("name")){
			String name = (String) queryMap.get("name");
			//处理页面传递过来的表名
			name = CommonUtils.tablenameDealWith(name);
			queryMap.put("tablename", name);
		}
		List<TableColumn> list = dataDictionaryService.getTableColumnListBy(queryMap);
		List jsonList = new ArrayList();
		String tablename = request.getParameter("tablename");
		String userAccess = request.getParameter("userAccess");
		//当取查询字段时，根据字段权限返回能访问的字段
		if("1".equals(userAccess)){
			//获取字段权限
			Map colsMap = getAccessColumn(tablename);
			for(TableColumn tableColumn : list){
				if(colsMap.containsKey(tableColumn.getColumnname())||colsMap.size()==0){
					Map map = new HashMap();
					map.put("id", tableColumn.getColumnname());
					map.put("name", tableColumn.getColchnname());
					map.put("datatype", tableColumn.getColdatatype());
					map.put("usecoded", null!=tableColumn.getUsecoded()?tableColumn.getUsecoded():"");
					map.put("codedcoltype",null!=tableColumn.getCodedcoltype()?tableColumn.getCodedcoltype():"");
					jsonList.add(map);
				}
			}
		}else{
			for(TableColumn tableColumn : list){
				Map map = new HashMap();
				map.put("id", tableColumn.getColumnname());
				map.put("name", tableColumn.getColchnname());
				map.put("datatype", tableColumn.getColdatatype());
				map.put("usecoded", null!=tableColumn.getUsecoded()?tableColumn.getUsecoded():"");
				map.put("codedcoltype",null!=tableColumn.getCodedcoltype()?tableColumn.getCodedcoltype():"");
				jsonList.add(map);
			}
		}
		addJSONArray(jsonList);
		return "success";
	}
	
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
	public String getTableRelationList() throws Exception{
		Map queryMap = CommonUtils.changeMap(request.getParameterMap());
		//name表示表名 转成tablename
		if(queryMap.containsKey("name")){
			String name = (String) queryMap.get("name");
			//处理页面传递过来的表名
			name = CommonUtils.tablenameDealWith(name);
			queryMap.put("tablename", name);
		}
		List<TableRelation> list = dataDictionaryService.getTableRelationListBy(queryMap);
		List jsonList = new ArrayList();
		for(TableRelation tableRelation : list){
			Map map = new HashMap();
			map.put("maintablename", tableRelation.getMaintablename());
			map.put("maincolumnname", tableRelation.getMaincolumnname());
			map.put("tablename", tableRelation.getTablename());
			map.put("columnname", tableRelation.getColumnname());
			map.put("deleteverify", tableRelation.getDeleteverify());
			map.put("cascadechange", tableRelation.getCascadechange());
			jsonList.add(map);
		}
		addJSONArray(jsonList);
		return "success";
	}
	
	/**
	 * 根据条件以及数据字典中定义的级联关系，获取表的数据<br/>
	 * pageMap中的参数<br/>
	 * name: 从属表名，必需参数<br/>
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
	 * @return 
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-8
	 */
	public String getTableDataDict() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//name表示表名 转成tablename
		if(map.containsKey("name")){
			String name = (String) map.get("name");
			//处理页面传递过来的表名
			name = CommonUtils.tablenameDealWith(name);
			map.put("tablename", name);
		}
		// map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		Map result=dataDictionaryService.getTableDataDictHandleResult(pageMap);
		addJSONObject(result);
		return "success";
	}
	/**
	 * 获取用户列表<br/>
	 * name姓名
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-21
	 */
	public String getSysUserList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = this.getBaseSysUserService().showSysUserList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 获取控件
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Feb 26, 2013
	 */
	public String getWidget() throws Exception{
		String name = request.getParameter("name");
		name = CommonUtils.tablenameDealWith(name);
		String col = request.getParameter("col");
		String content = request.getParameter("content");
		String paramRule = request.getParameter("paramRule");
		String view = request.getParameter("view");
		String initValue = request.getParameter("initValue");
		//是否去树状重复数据
		String treeDistint = request.getParameter("treeDistint");
		//数据参照窗口名称作为树状顶级父节点
		String treePName = request.getParameter("treePName");
		//是否根据参数窗口字段treenodenocheck数据值(1或”true” 表示不可选)，值（0 或”false” 表示可选）开启结点是否可选
		String treeNodeDataUseNocheck=request.getParameter("treeNodeDataUseNocheck");
		//tree树状是否全选 1是0否
		String isAllSelect = request.getParameter("isAllSelect");
		//参照窗口数据 不超过该数量时 全部显示下拉
		String listnum = request.getParameter("listnum");
		Map map = new HashMap();
		TableColumn tableColumn = dataDictionaryService.getTableColumnInfo(name, col);
		if(null!=tableColumn){
			//支持编码字段
			if("1".equals(tableColumn.getUsecoded()) && null != tableColumn.getCodedcoltype()){
				List<SysCode> list = sysCodeService.showSysCodeListByType(tableColumn.getCodedcoltype());
				List codeList = new ArrayList();
				for(SysCode sysCode : list){
					Map codemap = new HashMap();
					codemap.put("id", sysCode.getCode());
					codemap.put("name", sysCode.getCodename());
					codeList.add(codemap);
				}
				map.put("data", codeList);
				map.put("type", "code");
			}else if("1".equals(tableColumn.getUsecolrefer()) && null != tableColumn.getReferwid()){
				//控件参数 [{field:'state',op:'equal',value:'1'}]数组
				pageMap.getCondition().put("paramRule", paramRule);
				pageMap.getCondition().put("view", view);
				pageMap.getCondition().put("treeDistint", treeDistint);
				pageMap.getCondition().put("treePName", treePName);
				pageMap.getCondition().put("treeNodeDataUseNocheck", treeNodeDataUseNocheck);
				pageMap.getCondition().put("isAllSelect", isAllSelect);
				pageMap.getCondition().put("listnum", listnum);
				pageMap.getCondition().put("content", content);
				if(null!=initValue && !"".equals(initValue)){
					pageMap.getCondition().put("initvalue", initValue);
				}
				Map referMap = referWindowService.getReferWindowWidget(pageMap, tableColumn.getReferwid());
				String model = (String) referMap.get("model");
				if("none".equals(model)){
					map.put("model", "none");
				}else{
					//参照窗口下拉框的字段
					Map fieldMap = new HashMap();
					if("normal".equals(model)){
						int width = 0;
						List fieldList = new ArrayList();
						//参照窗口下拉框的字段
						List<ReferWindowColumn> columnList = (List) referMap.get("column");
						for (ReferWindowColumn referWindowColumn : columnList) {
							if("1".equals(referWindowColumn.getIsquote())){
								fieldMap.put("id", referWindowColumn.getColname());
								fieldMap.put("idvalue", referWindowColumn.getCol());
							}else if("2".equals(referWindowColumn.getIsquote())){
								fieldMap.put("name", referWindowColumn.getColname());
								fieldMap.put("namevalue", referWindowColumn.getCol());
							}else if("3".equals(referWindowColumn.getIsquote())){
								fieldMap.put("id", referWindowColumn.getColname());
								fieldMap.put("idvalue", referWindowColumn.getCol());
								fieldMap.put("name", referWindowColumn.getColname());
								fieldMap.put("namevalue", referWindowColumn.getCol());
							}
							//参照窗口下拉框的字段
							Map fieldColunmMap = new HashMap();
							if("1".equals(referWindowColumn.getIsquote()) || "2".equals(referWindowColumn.getIsquote())
									|| "3".equals(referWindowColumn.getIsquote()) ||"6".equals(referWindowColumn.getIsquote())){
								fieldColunmMap.put("field", referWindowColumn.getCol());
								fieldColunmMap.put("title", referWindowColumn.getColname());
								fieldColunmMap.put("sortable", "true");
								if(null==referWindowColumn.getWidth()){
									fieldColunmMap.put("width", 100);
									width += 100;
								}else{
									fieldColunmMap.put("width", referWindowColumn.getWidth());
									width += referWindowColumn.getWidth();
								}
								fieldList.add(fieldColunmMap);
							}
						}
						if(width<=420){
							Map dataMmap = (Map) fieldList.get(fieldList.size()-1);
							int widthlen = (Integer) dataMmap.get("width");
							widthlen = widthlen+420-width;
							dataMmap.put("width", widthlen);
							fieldList.remove(fieldList.size()-1);
							fieldList.add(dataMmap);
						}
						map.put("columnFieldList", fieldList);
						map.put("column", fieldMap);
					}else{
						map.put("initList", referMap.get("initList"));
					}
					map.put("refertype",referMap.get("refertype"));
					map.put("data", referMap.get("list"));
					map.put("dataCount", referMap.get("dataCount"));
					map.put("ajaxLoad", referMap.get("ajaxLoad"));
					map.put("column", fieldMap);
					map.put("type", "refer");
					map.put("model", model);
					map.put("referid", tableColumn.getReferwid());
					map.put("wname", tableColumn.getWname());
				}
			}
		}
		addJSONObject(map);
		return "success";
	}
	
	/**
	 * 显示数据窗口
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 1, 2013
	 */
	public String showTableDataWidget() throws Exception{
		String tablename = request.getParameter("name");
		tablename = CommonUtils.tablenameDealWith(tablename);
		String col = request.getParameter("col");
		String mname = request.getParameter("mname");
		String vid = request.getParameter("vid");
		String divid = request.getParameter("divid");
		String state = request.getParameter("state");
		String singleSelect = request.getParameter("singleSelect");
		String onlyLeafCheck = request.getParameter("onlyLeafCheck");
		String tree = request.getParameter("tree");
		String paramRule = request.getParameter("paramRule");
		
		TableRelation tableRelation = dataDictionaryService.getTableRelationBySubNames(tablename, col);
		if(null!=tableRelation){
			Map queryMap = new HashMap();
			queryMap.put("tablename", tableRelation.getMaintablename());
			queryMap.put("isshow", "1");
			List<TableColumn> list = dataDictionaryService.getTableColumnListBy(queryMap);
			//生成需要编码转换的字段列表字符串
			String codeColumnString = "";
			for(TableColumn tableColumn : list){
				if("1".equals(tableColumn.getUsecoded())){
					if("".equals(codeColumnString)){
						codeColumnString += "'"+tableColumn.getColumnname()+"'";
					}else{
						codeColumnString += ",'"+tableColumn.getColumnname()+"'";
					}
				}
			}
			request.setAttribute("list", list);
			request.setAttribute("codeColumn", codeColumnString);
			request.setAttribute("key", tableRelation.getMaincolumnname());
			request.setAttribute("colname", tableRelation.getMaintitlecolname());
		}
		request.setAttribute("divid", divid);
		request.setAttribute("vid", vid);
		request.setAttribute("state", state);
		request.setAttribute("singleSelect", singleSelect);
		request.setAttribute("name", tableRelation.getMaintablename());
		request.setAttribute("onlyLeafCheck", onlyLeafCheck);
		request.setAttribute("tree", tree);
		request.setAttribute("paramRule", paramRule);
		return "success";
	}
	/**
	 * 获取树状表的数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 1, 2013
	 */
	public String getWidgetTableDataList() throws Exception{
		//需要查询的字段
		String col = request.getParameter("col");
		//查询的内容
		String content = request.getParameter("content");
		String tablename = request.getParameter("name");
		String paramRule = request.getParameter("paramRule");
		//是否树状表 true是false否
		String tree = request.getParameter("tree");
		pageMap.getCondition().put("tree", tree);
		pageMap.getCondition().put("col", col);
		pageMap.getCondition().put("content", content);
		tablename = CommonUtils.tablenameDealWith(tablename);
		PageData pageData = dataDictionaryService.getWidgetTableDataList(tablename, pageMap, paramRule);
		addJSONObject(pageData);
		return "success";
	}
	
//	/**
//	 * 判断编号是否自动生成
//	 * @return
//	 * @throws Exception
//	 * @author panxiaoxiao 
//	 * @date 2013-3-4
//	 */
//	public String canCreateAuto()throws Exception{
//		String tablename = request.getParameter("tablename");
//		boolean flag = isAutoCreate(tablename);
//		addJSONObject("flag", flag);
//		return "success";
//	}
	
	/**
	 * 根据URL，获取资源信息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-28
	 */
	public String getOperateByURL() throws Exception{
		String url=request.getParameter("operurl");
		Map<String,Object> map=new HashMap<String, Object>();
		if(null ==url || "".equals(url.trim())){
			map.put("flag", false);
			addJSONObject(map);
			return "success";
		}
		Operate operate=getOperateByURL(url.trim());
		if(null==operate){
			map.put("flag", false);
			addJSONObject(map);
			return "success";			
		}
		map.put("flag", true);
		map.put("name", operate.getOperatename());
		map.put("type", operate.getType());
		addJSONObject(map);
		return "success";
	}
	/**
	 * 判断基础档案是否可以删除
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 9, 2013
	 */
	public String baseFilesCanDel() throws Exception{
		String id = request.getParameter("id");
		String name = request.getParameter("tname");
		boolean flag = dataDictionaryService.canTableDataDelete(name, id);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 获取数据的状态等信息
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 10, 2013
	 */
	public String getDataState() throws Exception{
		String id = request.getParameter("id");
		String tname = request.getParameter("tname");
		String model = request.getParameter("model");
		String stateStr = "state";
		Map map = new HashMap();
		if("base".equals(model)){
			stateStr = "state";
			String value = dataDictionaryService.getDataState(tname, id, stateStr);
			map.put("state", value);
		}else{
			stateStr = "status";
			String value = dataDictionaryService.getDataState(tname, id, stateStr);
			map.put("status", value);
		}
		
		addJSONObject(map);
		return "success";
	}
	
	/**
	 * 获取档案下一级次长度
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 12, 2013
	 */
	public String getBaseFilesLevelLength() throws Exception{
		String id = request.getParameter("id");
		String tname = request.getParameter("tname");
		int len = 0;
		if(StringUtils.isNotEmpty(id)){
			len = id.length();
		}
		int nextLen=getBaseTreeFilesNext(tname, len);
		addJSONObject("len", nextLen);
		return SUCCESS;
	}
	
	/**
	 * 根据商品档案编码获取辅助计量单位
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 9, 2013
	 */
	public String getGoodsInfoMUList()throws Exception{
		String goodsid = request.getParameter("goodsid");
		List<GoodsInfo_MteringUnitInfo> MUList = goodsService.getMUListByGoodsId(goodsid);
		if(MUList.size() != 0){
			for(GoodsInfo_MteringUnitInfo MUInfo : MUList){
				if(StringUtils.isNotEmpty(MUInfo.getMeteringunitid())){
					MeteringUnit meteringUnit = goodsService.showMeteringUnitInfo(MUInfo.getMeteringunitid());
					if(null!=meteringUnit){
						MUInfo.setMeteringunitName(meteringUnit.getName());
					}
				}
			}
		}
		addJSONArray(MUList);
		return SUCCESS;
	}
	
	/**
	 * 获取表名获取数字字典中字段列表
	 * @param tablename
	 * @param columnname
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-8
	 */
	public String getDictTableColumnList() throws Exception{
		String tablename = request.getParameter("tablename");
		//从数据字典中获取自定义描述
		Map queryMap=new HashMap();
		queryMap.put("tablename",tablename);
		TableColumn tableColumn=null;
		List<TableColumn> list=getBaseDataDictionaryService().getTableColumnListBy(queryMap);
		addJSONArray(list);
		return SUCCESS;
	}

	/**
	 * 获取单据商品明细数量小数位
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-06-12
	 */
	public String getDecimalBillGoodsNumDecimalLen()throws Exception{
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
		addJSONObject("decimallen",decimalScale);
		return SUCCESS;
	}


	public String basePrintDialog() throws Exception {
		return SUCCESS;
	}
}

