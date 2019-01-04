/**
 * @(#)ReferWindowServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-2-4 chenwei 创建版本
 */
package com.hd.agent.system.service.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.hd.agent.common.dao.PageSettingMapper;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.EhcacheUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.common.util.RuleJSONUtils;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.dao.ReferWindowMapper;
import com.hd.agent.system.model.ReferWindow;
import com.hd.agent.system.model.ReferWindowColumn;
import com.hd.agent.system.service.IReferWindowService;

/**
 * 
 * 参照窗口service实现类
 * 
 * @author chenwei
 */
public class ReferWindowServiceImpl extends BaseServiceImpl implements
		IReferWindowService {
	/**
	 * 参照窗口dao
	 */
	private ReferWindowMapper referWindowMapper;
	/**
	 * 页面配置dao 来源common
	 */
	private PageSettingMapper pageSettingMapper;
	
	public ReferWindowMapper getReferWindowMapper() {
		return referWindowMapper;
	}

	public void setReferWindowMapper(ReferWindowMapper referWindowMapper) {
		this.referWindowMapper = referWindowMapper;
	}
	
	public PageSettingMapper getPageSettingMapper() {
		return pageSettingMapper;
	}

	public void setPageSettingMapper(PageSettingMapper pageSettingMapper) {
		this.pageSettingMapper = pageSettingMapper;
	}

	@Override
	public List getReferWindowBySQL(PageMap pageMap) throws Exception {
		SqlSessionFactory sqlMapper = (SqlSessionFactory) SpringContextUtils.getBean("sqlSessionFactory");
		SqlSession session =sqlMapper.openSession();
		String sql = (String) pageMap.getCondition().get("sql");
		ResultSet resultSet = session.getConnection().createStatement().executeQuery(sql);
		ResultSetMetaData rsmd = resultSet.getMetaData() ; 
		List colList = new ArrayList();
		for(int i=1;i<=rsmd.getColumnCount();i++){
			Map map = new HashMap();
			map.put("column", rsmd.getColumnLabel(i));
			colList.add(map);
		}
		return colList;
	}

	@Override
	public boolean addReferWindow(ReferWindow referWindow,
			List<ReferWindowColumn> referWColumnList) throws Exception {
		int i = referWindowMapper.addReferWindowBaseInfo(referWindow);
		// 添加参照窗口列明细
		if (null != referWColumnList) {
			for (ReferWindowColumn referWindowColumn : referWColumnList) {
				if(null!=referWindowColumn){
					referWindowColumn.setReferwinid(referWindow.getId());
					referWindowMapper.addReferWindowColumn(referWindowColumn);
				}
			}
		}
		return i > 0;
	}

	@Override
	public PageData showReferWindowList(PageMap pageMap) throws Exception {
		PageData pageData = new PageData(referWindowMapper
				.showReferWindowCount(pageMap), referWindowMapper
				.showReferWindowList(pageMap), pageMap);
		return pageData;
	}

	@Override
	public boolean checkReferWindowID(String id) throws Exception {
		int i = referWindowMapper.checkReferWindowID(id);
		return i == 0;
	}

	@Override
	public boolean deleteReferWindow(String id) throws Exception {
		int i = referWindowMapper.deleteReferWindow(id);
		referWindowMapper.deleteReferWindowColumm(id);
		pageSettingMapper.deletePageCulmnAll("t_"+id);
		return i > 0;
	}

	@Override
	public boolean openReferWindow(String id) throws Exception {
		int i = referWindowMapper.openReferWindow(id);
		return i > 0;
	}

	@Override
	public boolean closeReferWindow(String id) throws Exception {
		int i = referWindowMapper.closeReferWindow(id);
		return i > 0;
	}

	@Override
	public Map showReferWindowInfo(String id) throws Exception {
		ReferWindow referWindow = referWindowMapper.getReferWindow(id);
		List list = referWindowMapper.getReferWindowColumnList(id);
		Map map = new HashMap();
		map.put("referWindow", referWindow);
		map.put("column", list);
		return map;
	}

	@Override
	public PageData getReferWindowData(PageMap pageMap, String id)
			throws Exception {
		ReferWindow referWindow = referWindowMapper.getReferWindow(id);
//		//解析拼装sql
//		List<ReferWindowParam> paramList = referWindowMapper.getReferWindowParamList(id);
		if(null==referWindow){
			return null;
		}
		String sql = CommonUtils.sqlFilter(referWindow.getSqlstr());
		if (sql.toLowerCase().indexOf("where") ==-1) {
			sql += " where 1=1 ";
		}
		String sqlcount = sql.substring(sql.toLowerCase().indexOf("from"), sql.length());
		pageMap.getCondition().put("sql", sql);
		pageMap.getCondition().put("sqlcount", sqlcount);
		
		//获取参照窗口的数据权限
		String dataSql = getDataAccessRule(referWindow.getId(), "referWindow");
		pageMap.setDataSql(dataSql);
		//控件传过来的参数 生成sql语句
		String paramRule = (String) pageMap.getCondition().get("paramRule");
		String paramRuleSql = RuleJSONUtils.widgetParamToSql(paramRule,"referWindow");
		pageMap.getCondition().put("paramRuleSql", paramRuleSql);

        //多条件模糊查询 以空格为标识
        if(pageMap.getCondition().containsKey("content")){
            String content = (String) pageMap.getCondition().get("content");
            if(StringUtils.isNotEmpty(content) && content.indexOf(" ")>=0){
                String[] conArr = content.split(" ");
                List<String> conList  = new ArrayList<String>();
                for(String con : conArr){
                    con = con.trim();
                    if(StringUtils.isNotEmpty(con)){
                        conList.add(con);
                    }
                }
                pageMap.getCondition().put("con",conList);
            }
        }
		//列表中的数据 如果有代码类型的 进行转码
		List<Map> list = referWindowMapper.getReferWindowData(pageMap);
		
		//获取需要转换编码类型的列列表
		List<ReferWindowColumn> columnList = referWindowMapper.getReferWindowColumnList(id);
		List<String[]> codeList = new ArrayList();
		String treeKeyCol =  null;
		String treePidCol = null;
		for(ReferWindowColumn referWindowColumn : columnList){
			if(null!=referWindowColumn.getCodetype()&&!"".equals(referWindowColumn.getCodetype())){
				String[] type = new String[2];
				type[0] = referWindowColumn.getCol();
				type[1] = referWindowColumn.getCodetype();
				codeList.add(type);
			}
			if("1".equals(referWindowColumn.getIsquote()) || "3".equals(referWindowColumn.getIsquote())){
				treeKeyCol = referWindowColumn.getCol();
			}else if("5".equals(referWindowColumn.getIsquote())){
				treePidCol = referWindowColumn.getCol();
			}
		}
		//数据做改动时，需要先克隆缓存的数据对象
		//数据对象克隆 反正影响缓存的数据
		List<Map> dataList = (List<Map>) CommonUtils.deepCopy(list);
		//当参照窗口数据是树形时， 给该数据添加是否子节点的判断 leaf:1子节点0父节点
		if("tree".equals(referWindow.getModel())){
			Map pMap = new HashMap();
			for(Map map : dataList){
				for(String[] codetype : codeList){
					String col = codetype[0];
					String type = codetype[1];
					Map sysCode = getCodeListByType(type);
					Object o = map.get(col);
					String value = (String) sysCode.get(o);
					map.put(col, value);
				}
				String pid = (String) map.get(treePidCol);
				pMap.put("pid-"+pid, true);
			}
			for(Map map : dataList){
				String treeid = (String) map.get(treeKeyCol);
				if(pMap.containsKey("pid-"+treeid)){
					map.put("leaf", "0");
				}else{
					map.put("leaf", "1");
				}
			}
		}else if("normal".equals(referWindow.getModel())){
			for(Map map : dataList){
				for(String[] codetype : codeList){
					String col = codetype[0];
					String type = codetype[1];
					Map sysCode = getCodeListByType(type);
					Object o = map.get(col);
					String value = (String) sysCode.get(o);
					map.put(col, value);
				}
			}
		}
		PageData pageData = new PageData(referWindowMapper.getReferWindowDataCount(pageMap),dataList, pageMap);
		
		return pageData;
	}
	@Override
	public List getReferWindowDataByReferid(Map map) throws Exception {
		String referid = (String) map.get("referid");
		ReferWindow referWindow = referWindowMapper.getReferWindow(referid);
//		//解析拼装sql
//		List<ReferWindowParam> paramList = referWindowMapper.getReferWindowParamList(id);
		String sql = CommonUtils.sqlFilter(referWindow.getSqlstr());
		if (sql.toLowerCase().indexOf("where") ==-1) {
			sql += " where 1=1 ";
		}
		String sqlcount = sql.substring(sql.toLowerCase().indexOf("from"), sql.length());
		
		PageMap pageMap = new PageMap();
		pageMap.setRows(200);
		pageMap.setPage(1);
		pageMap.setCondition(map);
		pageMap.getCondition().put("sql", sql);
		pageMap.getCondition().put("sqlcount", sqlcount);
		
		//获取参照窗口的数据权限
		String dataSql = getDataAccessRule(referWindow.getId(), "referWindow");
		pageMap.setDataSql(dataSql);
		//控件传过来的参数 生成sql语句
		String paramRule = (String) pageMap.getCondition().get("paramRule");
		String paramRuleSql = RuleJSONUtils.widgetParamToSql(paramRule,"referWindow");
		pageMap.getCondition().put("paramRuleSql", paramRuleSql);

        //多条件模糊查询 以空格为标识
        if(pageMap.getCondition().containsKey("content")){
            String content = (String) pageMap.getCondition().get("content");
            if(StringUtils.isNotEmpty(content) && content.indexOf(" ")>=0){
                String[] conArr = content.split(" ");
                List<String> conList  = new ArrayList<String>();
                for(String con : conArr){
                    con = con.trim();
                    if(StringUtils.isNotEmpty(con)){
                        conList.add(con);
                    }
                }
                pageMap.getCondition().put("con",conList);
            }
        }
		//列表中的数据 如果有代码类型的 进行转码
		List<Map> list = referWindowMapper.getReferWindowAllData(pageMap);
		
		//获取需要转换编码类型的列列表
		List<ReferWindowColumn> columnList = referWindowMapper.getReferWindowColumnList(referid);
		List<String[]> codeList = new ArrayList();
		for(ReferWindowColumn referWindowColumn : columnList){
			if(null!=referWindowColumn.getCodetype()&&!"".equals(referWindowColumn.getCodetype())){
				String[] type = new String[2];
				type[0] = referWindowColumn.getCol();
				type[1] = referWindowColumn.getCodetype();
				codeList.add(type);
			}
		}
		//数据做改动时，需要先克隆缓存的数据对象
		//数据对象克隆 反正影响缓存的数据
		List<Map> dataList = (List<Map>) CommonUtils.deepCopy(list);
		for(Map mapObject : dataList){
			for(String[] codetype : codeList){
				String col = codetype[0];
				String type = codetype[1];
				Map sysCode = getCodeListByType(type);
				Object o = mapObject.get(col);
				String value = (String) sysCode.get(o);
				mapObject.put(col, value);
			}
		}
		return dataList;
	}
	@Override
	public Map getReferWindowWidget(PageMap pageMap,String id) throws Exception{
		ReferWindow referWindow = referWindowMapper.getReferWindow(id);
		Map returnmap = new HashMap();
		//参照窗口未启用或者不存在
		if(null==referWindow || !"1".equals(referWindow.getState())){
			returnmap.put("model", "none");
			return returnmap;
		}
		//根据view 获取不同的sql 查询数据
		String view = (String) pageMap.getCondition().get("view");
		String sql = "";
		if(null!=view && "true".equals(view)){
			sql = CommonUtils.sqlFilter(referWindow.getViewsql());
		}else{
			sql = CommonUtils.sqlFilter(referWindow.getSqlstr());
		}
		//解析拼装sql
		if (sql.toLowerCase().indexOf("where") ==-1) {
			sql += " where 1=1 ";
		}
		String sqlcount = sql.substring(sql.toLowerCase().indexOf("from"), sql.length());
		pageMap.getCondition().put("sql", sql);
		pageMap.getCondition().put("sqlcount", sqlcount);
		
		//获取参照窗口的数据权限
		String dataSql = getDataAccessRule(referWindow.getId(), "referWindow");
		pageMap.setDataSql(dataSql);
		//控件传过来的参数 生成sql语句
		String paramRule = (String) pageMap.getCondition().get("paramRule");
		String paramRuleSql = RuleJSONUtils.widgetParamToSql(paramRule,"referWindow");
		pageMap.getCondition().put("paramRuleSql", paramRuleSql);



		if("tree".equals(referWindow.getModel())){
			pageMap.getCondition().remove("content");
			//列表中的数据 如果有代码类型的 进行转码
			List<Map> list = referWindowMapper.getReferWindowAllData(pageMap);
			//list数据克隆
			List<Map> dataList = (List<Map>) CommonUtils.deepCopy(list);
			List<ReferWindowColumn> columnList = referWindowMapper.getReferWindowColumnList(id);
			String idCol = null;
			String pidCol = null;
			String showCol = null;
			for(ReferWindowColumn referWindowColumn : columnList){
				//主键
				if(null==idCol&&"1".equals(referWindowColumn.getIsquote())){
					idCol = referWindowColumn.getCol();
				}else if(null==pidCol&&"5".equals(referWindowColumn.getIsquote())){
					//父节点
					pidCol = referWindowColumn.getCol();
				}else if(null==showCol&&"2".equals(referWindowColumn.getIsquote())){
					//显示
					showCol = referWindowColumn.getCol();
				}else if("3".equals(referWindowColumn.getIsquote())){
					idCol = referWindowColumn.getCol();
					showCol = referWindowColumn.getCol();
				}
			}
			//是否根据参数窗口字段treenodenocheck数据值(1或”true” 表示不可选)，值（0 或”false” 表示可选）开启结点是否可选
			String treeNodeDataUseNocheck = (String) pageMap.getCondition().get("treeNodeDataUseNocheck");
			for(Map map : dataList){
				map.put("id", map.get(idCol));
				map.put("name", map.get(showCol));
				if(null==map.get(pidCol)){
					map.put("pId", "");
				}else{
					map.put("pId", map.get(pidCol));
				}
				if(map.containsKey("isparent")){
					String isparent = (String) map.get("isparent");
					//判断该节点是否为父节点
					if(null!=isparent && "1".equals(isparent)){
						map.put("isParent", "true");
					}else{
						map.put("isParent", "false");
					}
				}
				if("true".equals(treeNodeDataUseNocheck) && map.containsKey("treenodenocheck")){
					String treenodenocheck=(String)map.get("treenodenocheck");
					if("1".equals(treenodenocheck) || "true".equals(treenodenocheck)){
						map.put("nocheck", "true");
					}
				}
				
			}
			//数据去重复
			String treeDistint = (String) pageMap.getCondition().get("treeDistint");
			//参照窗口名称作为最顶级父节点
			String treePName = (String) pageMap.getCondition().get("treePName");
			String isAllSelect  = (String) pageMap.getCondition().get("isAllSelect");
			if("true".equals(treePName)){
				Map firstMap = new HashMap();
				firstMap.put("id", "");
				firstMap.put("name", referWindow.getWname());
				firstMap.put("open", "true");
				if("0".equals(isAllSelect)){
					firstMap.put("nocheck", "true");
				}
				dataList.add(firstMap);
			}
			if("true".equals(treeDistint)){
				//去掉树状中 id重复的数据
				List<Map> dataL = new ArrayList();
				for(int i=0;i<dataList.size();i++){
					Map map = dataList.get(i);
					String idvalue = (String) map.get(idCol);
					boolean flag = true;
					for(int j=0;j<dataL.size();j++){
						Map map1 = dataL.get(j);
						String idvalue1 = (String) map1.get(idCol);
						if(null!= idvalue && idvalue.equals(idvalue1)){
							flag = false;
							break;
						}
					}
					if(flag){
						dataL.add(map);
					}
				}
				CommonUtils.getTableMapDataTree(dataL);
				//数据
				returnmap.put("list", dataL);
			}else{
				CommonUtils.getTableMapDataTree(dataList);
				//数据
				returnmap.put("list", dataList);
			}
			//字段列表
			returnmap.put("model", "tree");
			returnmap.put("referWindow", referWindow);
		}else{
			
			List<ReferWindowColumn> columnList = referWindowMapper.getReferWindowColumnList(id);
			String idCol = null;
			//获取需要转换编码类型的列列表
			List<String[]> codeList = new ArrayList();
			String colQ = "";
			String colnameQ = "";
			
			StringBuilder orderBysb=new StringBuilder();
			for(ReferWindowColumn referWindowColumn : columnList){
				//主键
				if(null==idCol&&"1".equals(referWindowColumn.getIsquote())){
					idCol = referWindowColumn.getCol();
				}
				if("1".equals(referWindowColumn.getIsquote())){
					colQ = referWindowColumn.getCol();
				}else if("2".equals(referWindowColumn.getIsquote())){
					colnameQ = referWindowColumn.getCol();
				}else if("3".equals(referWindowColumn.getIsquote())){
					colQ = referWindowColumn.getCol();
					colnameQ = referWindowColumn.getCol();
				}
				if(null!=referWindowColumn.getCodetype()&&!"".equals(referWindowColumn.getCodetype())){
					String[] type = new String[2];
					type[0] = referWindowColumn.getCol();
					type[1] = referWindowColumn.getCodetype();
					codeList.add(type);
				}
				String orderby="";
				if("1".equals(referWindowColumn.getOrderbyseq())){
					orderby=referWindowColumn.getCol()+" "+"ASC";
				}else if("2".equals(referWindowColumn.getOrderbyseq())){
					orderby=referWindowColumn.getCol()+" "+"DESC";
				}
				if(null!=orderby &&!"".equals(orderby.trim())){
					if(orderBysb.length()>0){
						orderBysb.append(",");
					}
					orderBysb.append(orderby);
				}
			}
	        if(orderBysb.length()>0){
	        	pageMap.setOrderSql(orderBysb.toString());
	        }
			//多条件模糊查询 以空格为标识
	        if(pageMap.getCondition().containsKey("content")){
	        	String content = (String) pageMap.getCondition().get("content");
	        	if(StringUtils.isNotEmpty(content) && StringUtils.isNotEmpty(colQ) && StringUtils.isNotEmpty(colnameQ)){
	        		pageMap.getCondition().put("col", colQ);
		        	pageMap.getCondition().put("colname", colnameQ);
		            if(StringUtils.isNotEmpty(content) && content.indexOf(" ")>=0){
		                String[] conArr = content.split(" ");
		                List<String> conList  = new ArrayList<String>();
		                for(String con : conArr){
		                    con = con.trim();
		                    if(StringUtils.isNotEmpty(con)){
		                        conList.add(con);
		                    }
		                }
		                pageMap.getCondition().put("con",conList);
		            }
	        	}else{
	        		pageMap.getCondition().remove("content");
	        	}
	        }
	        
			int dataCount = referWindowMapper.getReferWindowDataCount(pageMap);
			String listnumStr = (String) pageMap.getCondition().get("listnum");
			int listnum = 50;
			if(null!=listnumStr){
				listnum = Integer.parseInt(listnumStr);
			}
			if(dataCount<=listnum){
				//列表中的数据 如果有代码类型的 进行转码
				List<Map> list = referWindowMapper.getReferWindowAllData(pageMap);
				//list数据克隆
				List<Map> dataList = (List<Map>) CommonUtils.deepCopy(list);
				for(Map map : dataList){
					for(String[] codetype : codeList){
						String col = codetype[0];
						String type = codetype[1];
						Map sysCode = getCodeListByType(type);
						Object o = map.get(col);
						String value = (String) sysCode.get(o);
						map.put(col, value);
					}
				}
				returnmap.put("list", list);
				returnmap.put("refertype", "all");
			}else{
				pageMap.getCondition().put("idcol", idCol);
				String initValue = (String) pageMap.getCondition().get("initvalue");
				//列表中的数据 如果有代码类型的 进行转码
				List<Map> list = referWindowMapper.getReferWindowData(pageMap);
				//list数据克隆
				List<Map> dataList = (List<Map>) CommonUtils.deepCopy(list);
				for(Map map : dataList){
					for(String[] codetype : codeList){
						String col = codetype[0];
						String type = codetype[1];
						Map sysCode = getCodeListByType(type);
						Object o = map.get(col);
						String value = (String) sysCode.get(o);
						map.put(col, value);
					}
				}
				returnmap.put("list", list);
			}
			//字段列表
			returnmap.put("column", columnList);
			returnmap.put("referWindow", referWindow);
			if("tree".equals(referWindow.getModel())){
				returnmap.put("model", "tree");
			}else{
				returnmap.put("model", "normal");
			}
		}
		return returnmap;
	}
	@Override
	public List getReferWindowColumn(String referWid) throws Exception {
		List list = referWindowMapper.getReferWindowColumnList(referWid);
		return list;
	}


	@Override
	public boolean editReferWindow(ReferWindow referWindow,
			List<ReferWindowColumn> referWColumnList) throws Exception {
		int i = referWindowMapper.editReferWindow(referWindow);
		//删除修改前字段与参数列表
		referWindowMapper.deleteReferWindowColumm(referWindow.getId());
		// 添加参照窗口列明细
		for (ReferWindowColumn referWindowColumn : referWColumnList) {
			if(null!=referWindowColumn){
				referWindowColumn.setReferwinid(referWindow.getId());
				referWindowMapper.addReferWindowColumn(referWindowColumn);
			}
		}
		//更新表格配置
		pageSettingMapper.deletePageCulmnAll("t_"+referWindow.getId());
		return i>0;
	}

	@Override
	public List getReferWindowList(Map map) throws Exception {
		List list = referWindowMapper.getReferWindowList(map);
		return list;
	}

	@Override
	public String getReferWindowNameByValue(String referWname, String value)
			throws Exception {
		//获取缓存数据
		String valuename = (String) EhcacheUtils.getCacheData(referWname+"-"+value);
		if(null==valuename){
			ReferWindow referWindow = referWindowMapper.getReferWindow(referWname);
			Map returnmap = new HashMap();
			//参照窗口未启用或者不存在
			if(null==referWindow || !"1".equals(referWindow.getState())){
				return "";
			}
			PageMap pageMap = new PageMap();
			String sql = CommonUtils.sqlFilter(referWindow.getViewsql());
			//解析拼装sql
			if (sql.toLowerCase().indexOf("where") ==-1) {
				sql += " where 1=1 ";
			}
			pageMap.getCondition().put("sql", sql);
			
	//		//获取参照窗口的数据权限
	//		String dataSql = getDataAccessRule(referWindow.getId(), "referWindow");
	//		pageMap.setDataSql(dataSql);
			//控件传过来的参数 生成sql语句
			
			List<ReferWindowColumn> columnList = referWindowMapper.getReferWindowColumnList(referWname);
			String valueColumn = null;
			String nameColumn = null;
			for(ReferWindowColumn referWindowColumn : columnList){
				if("1".equals(referWindowColumn.getIsquote())){
					valueColumn = referWindowColumn.getCol();
				}else if("2".equals(referWindowColumn.getIsquote())){
					nameColumn = referWindowColumn.getCol();
				}
			}
			String paramRuleSql = valueColumn +" = '"+value+"'";
			pageMap.getCondition().put("paramRuleSql", paramRuleSql);
			//列表中的数据 如果有代码类型的 进行转码
			List<Map> list = referWindowMapper.getReferWindowAllData(pageMap);
			if(list.size()==1){
				Map map = list.get(0);
				valuename = (String) map.get(nameColumn);
				EhcacheUtils.addCache(referWname+"-"+value, valuename);
				return valuename;
			}
			return "";
		}else{
			return valuename;
		}
	}

	@Override
	public String getReferWindowNameByName(String referWname, String name)
			throws Exception {
		//获取缓存数据
		String value = (String) EhcacheUtils.getCacheData(referWname+"-"+name);
		if(null==value){
			ReferWindow referWindow = referWindowMapper.getReferWindow(referWname);
			Map returnmap = new HashMap();
			//参照窗口未启用或者不存在
			if(null==referWindow || !"1".equals(referWindow.getState())){
				return "";
			}
			PageMap pageMap = new PageMap();
			String sql = CommonUtils.sqlFilter(referWindow.getViewsql());
			//解析拼装sql
			if (sql.toLowerCase().indexOf("where") ==-1) {
				sql += " where 1=1 ";
			}
			pageMap.getCondition().put("sql", sql);
			
	//		//获取参照窗口的数据权限
	//		String dataSql = getDataAccessRule(referWindow.getId(), "referWindow");
	//		pageMap.setDataSql(dataSql);
			//控件传过来的参数 生成sql语句
			
			List<ReferWindowColumn> columnList = referWindowMapper.getReferWindowColumnList(referWname);
			String valueColumn = null;
			String nameColumn = null;
			for(ReferWindowColumn referWindowColumn : columnList){
				if("1".equals(referWindowColumn.getIsquote())){
					valueColumn = referWindowColumn.getCol();
				}else if("2".equals(referWindowColumn.getIsquote())){
					nameColumn = referWindowColumn.getCol();
				}
			}
			String paramRuleSql = nameColumn +" = '"+name+"'";
			pageMap.getCondition().put("paramRuleSql", paramRuleSql);
			//列表中的数据 如果有代码类型的 进行转码
			List<Map> list = referWindowMapper.getReferWindowAllData(pageMap);
			if(list.size()>=1){
				Map map = list.get(0);
				value = (String) map.get(valueColumn);
				EhcacheUtils.addCache(referWname+"-"+name, value);
				return value;
			}
			return "";
		}else{
			return value;
		}
	}


}
