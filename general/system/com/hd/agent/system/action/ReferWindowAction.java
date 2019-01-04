/**
 * @(#)ReferWindowAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-2-4 chenwei 创建版本
 */
package com.hd.agent.system.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.ReferWindow;
import com.hd.agent.system.model.ReferWindowColumn;
import com.hd.agent.system.model.TableColumn;
import com.hd.agent.system.service.IDataDictionaryService;
import com.hd.agent.system.service.IReferWindowService;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 参照窗口action
 * @author chenwei
 */
public class ReferWindowAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2826198116837695986L;

	private ReferWindow referWindow;
	
	private List<ReferWindowColumn> referWColumnList;
	
	private IReferWindowService referWindowService;
	/**
	 * 数据字典接口
	 */
	private IDataDictionaryService dataDictionaryService;
	
	public IReferWindowService getReferWindowService() {
		return referWindowService;
	}

	public void setReferWindowService(IReferWindowService referWindowService) {
		this.referWindowService = referWindowService;
	}
	
	public ReferWindow getReferWindow() {
		return referWindow;
	}

	public void setReferWindow(ReferWindow referWindow) {
		this.referWindow = referWindow;
	}

	public List<ReferWindowColumn> getReferWColumnList() {
		return referWColumnList;
	}

	public void setReferWColumnList(List<ReferWindowColumn> referWColumnList) {
		this.referWColumnList = referWColumnList;
	}

	public IDataDictionaryService getDataDictionaryService() {
		return dataDictionaryService;
	}

	public void setDataDictionaryService(
			IDataDictionaryService dataDictionaryService) {
		this.dataDictionaryService = dataDictionaryService;
	}

	/**
	 * 显示参照窗口页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-4
	 */
	public String showReferenceWindowPage() throws Exception{
		return "success";
	}
	/**
	 * 显示参照窗口新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-4
	 */
	public String showReferWindowAddPage() throws Exception{
		
		return "success";
	}
	/**
	 * 根据sql语句生成参照窗口
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-5
	 */
	public String getReferWindowBySQL() throws Exception{
		String sql = request.getParameter("sql");
		sql = CommonUtils.sqlFilter(sql);
		Map map = new HashMap();
		if(null!=sql&&!"".equals(sql)){
			if(sql.toLowerCase().indexOf("where")==-1){
				sql +=" where 1=1 ";
			}
			Map condition = new HashMap();
			condition.put("sql", sql);
			String sqlcount = sql.substring(sql.toLowerCase().indexOf("from"),sql.length());
			condition.put("sqlcount", sqlcount);
			pageMap.setCondition(condition);
			
			//解析sql语句中的字段
			List<Map> refercolList = referWindowService.getReferWindowBySQL(pageMap);
			List colList = new ArrayList();
			for(Map colMap : refercolList){
				ReferWindowColumn column  = new ReferWindowColumn();
				column.setCol((String) colMap.get("column"));
				column.setColname((String) colMap.get("column"));
				colList.add(column);
			}
			
			map.put("colList", colList);
		}
		addJSONObject(map);
		return "success";
	}
	/**
	 * 添加参照窗口
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-18
	 */
	@UserOperateLog(key="ReferWindow",type=2)
	public String addReferWindow() throws Exception{
		SysUser sysUser = getSysUser();
		referWindow.setAdduserid(sysUser.getUserid());
		referWindow.setState("2");
		boolean flag = referWindowService.addReferWindow(referWindow, referWColumnList);
		addJSONObject("flag", flag);
		
		addLog("添加参照窗口 编号:"+referWindow.getId(), flag);
		return "success";
	}
	/**
	 * 获取参照窗口列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-18
	 */
	public String showReferWindowList() throws Exception{
		// 获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		// map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		
		PageData pageData = referWindowService.showReferWindowList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 验证参照窗口编号是否重复。
	 * 返回true不重复false重复
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-18
	 */
	public String checkReferWindowID() throws Exception{
		String id = request.getParameter("id");
		boolean flag = referWindowService.checkReferWindowID(id);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 删除参照窗口
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-19
	 */
	@UserOperateLog(key="ReferWindow",type=4)
	public String deleteReferWindow() throws Exception{
		String id = request.getParameter("id");
		boolean flag = referWindowService.deleteReferWindow(id);
		addJSONObject("flag", flag);
		
		addLog("删除参照窗口 编号:"+id, flag);
		return "success";
	}
	/**
	 * 启用参照窗口
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-19
	 */
	@UserOperateLog(key="ReferWindow",type=3)
	public String openReferWindow() throws Exception{
		String id = request.getParameter("id");
		boolean flag = referWindowService.openReferWindow(id);
		addJSONObject("flag", flag);
		
		addLog("启用参照窗口 编号:"+id, flag);
		return "success";
	}
	/**
	 * 禁用参照窗口
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-19
	 */
	@UserOperateLog(key="ReferWindow",type=3)
	public String closeReferWindow() throws Exception{
		String id = request.getParameter("id");
		boolean flag = referWindowService.closeReferWindow(id);
		addJSONObject("flag", flag);
		
		addLog("禁用参照窗口 编号:"+id, flag);
		return "success";
	}
	/**
	 * 显示参照窗口页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-19
	 */
	public String showReferWindow() throws Exception{
		String id = request.getParameter("id");
		String vid = request.getParameter("vid");
		String divid = request.getParameter("divid");
		String state = request.getParameter("state");
		String singleSelect = request.getParameter("singleSelect");
		if(null==singleSelect || "".equals(singleSelect)){
			singleSelect = "true";
		}
		String paramRule = request.getParameter("paramRule");
		String onlyLeafCheck = request.getParameter("onlyLeafCheck");
		Map map = referWindowService.showReferWindowInfo(id);
		ReferWindow referWindow = (ReferWindow) map.get("referWindow");
		
		List<ReferWindowColumn> list = (List) map.get("column");
		String key = "";
		String name = "";
		for (ReferWindowColumn referWindowColumn : list) {
			if("1".equals(referWindowColumn.getIsquote())){
				key = referWindowColumn.getCol();
			}else if("3".equals(referWindowColumn.getIsquote())){
				key = referWindowColumn.getCol();
				name = referWindowColumn.getCol();
			}else if("2".equals(referWindowColumn.getIsquote())){
				name = referWindowColumn.getCol();
			}
			if(null==referWindowColumn.getWidth()){
				referWindowColumn.setWidth(100);
			}
		}
		request.setAttribute("id", id);
		request.setAttribute("referWindow", referWindow);
		request.setAttribute("columnList", list);
		if(null!=list){
			request.setAttribute("size", list.size());
		}
		request.setAttribute("divid", divid);
		request.setAttribute("vid", vid);
		request.setAttribute("state", state);
		request.setAttribute("singleSelect", singleSelect);
		request.setAttribute("key", key);
		request.setAttribute("name", name);
		request.setAttribute("paramRule", paramRule);
		request.setAttribute("onlyLeafCheck", onlyLeafCheck);
		return "success";
	}
	/**
	 * 获取参照窗口数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-19
	 */
	public String getReferWindowData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		String col = StringEscapeUtils.escapeSql(request.getParameter("col"));
		String colname = StringEscapeUtils.escapeSql(request.getParameter("colname"));
		String content = StringEscapeUtils.escapeSql(request.getParameter("content"));
		String paramRule = request.getParameter("paramRule");
		map.put("col", col);
		map.put("colname", colname);
		map.put("content", content);
		map.put("paramRule", paramRule);
		// map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		String id = request.getParameter("id");
		PageData pageData = referWindowService.getReferWindowData(pageMap, id);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 获取参照窗口下拉数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 4, 2013
	 */
	public String getReferWindowDataByReferid() throws Exception{
		String referid = request.getParameter("referid");
		String col = request.getParameter("col");
		String colname = request.getParameter("colname");
		String q = request.getParameter("q");
		String paramRule = request.getParameter("paramRule");
		Map map = new HashMap();
		map.put("referid", referid);
		map.put("col", col);
		map.put("colname", colname);
		map.put("content", q);
		map.put("paramRule", paramRule);
		// map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		List list = referWindowService.getReferWindowDataByReferid(map);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 显示参照窗口修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-19
	 */
	public String showReferWindowEditPage() throws Exception{
		String id = request.getParameter("id");
		Map map = referWindowService.showReferWindowInfo(id);
		ReferWindow referWindow = (ReferWindow) map.get("referWindow");
		List columnList = (List) map.get("column");
		request.setAttribute("referWindow", referWindow);
		request.setAttribute("id", id);
		request.setAttribute("columnList", columnList);
		return "success";
	}
	/**
	 * 获取参照窗口下的列明细
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-20
	 */
	public String getReferWindowColumn() throws Exception{
		String referWid = request.getParameter("referWid");
		List list = referWindowService.getReferWindowColumn(referWid);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 修改参照窗口
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-25
	 */
	@UserOperateLog(key="ReferWindow",type=3)
	public String editReferWindow() throws Exception{
		SysUser sysUser = getSysUser();
		referWindow.setModifyuserid(sysUser.getUserid());
		if("1".equals(referWindow.getState())){
			referWindow.setState("1");
		}else{
			referWindow.setState("2");
		}
		
		boolean flag = referWindowService.editReferWindow(referWindow, referWColumnList);
		addJSONObject("flag", flag);
		
		addLog("修改参照窗口 编号:"+referWindow.getId(), flag);
		return "success";
	}
	/**
	 * 参照窗口复制
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-25
	 */
	public String showReferWindowCopyPage() throws Exception{
		String id = request.getParameter("id");
		Map map = referWindowService.showReferWindowInfo(id);
		ReferWindow referWindow = (ReferWindow) map.get("referWindow");
		List columnList = (List) map.get("column");
		request.setAttribute("referWindow", referWindow);
		request.setAttribute("id", id);
		request.setAttribute("columnList", columnList);
		return "success";
	}
	/**
	 * 获取参照窗口列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-25
	 */
	public String getReferWindowList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		List list = referWindowService.getReferWindowList(map);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 获取参照窗口字段列表。提供数据权限配置使用
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-25
	 */
	public String getReferWindowColumnList() throws Exception{
		String id = request.getParameter("id");
		List<ReferWindowColumn> list = referWindowService.getReferWindowColumn(id);
		List jsonlist = new ArrayList();
		for(ReferWindowColumn referWindowColumn : list){
			Map map = new HashMap();
			map.put("id", referWindowColumn.getCol());
			map.put("name", referWindowColumn.getColname());
			Map queryMap = new HashMap();
			queryMap.put("tablename", referWindowColumn.getTablename());
			queryMap.put("columnname", referWindowColumn.getTablecol());
			List columnList = dataDictionaryService.getTableColumnListBy(queryMap);
			if(columnList.size()>0){
				TableColumn column = (TableColumn) columnList.get(0);
				map.put("datatype", column.getColdatatype());
			}
			if(null!=referWindowColumn.getCodetype()&&!"".equals(referWindowColumn.getCodetype())){
				map.put("usecoded","1");
				map.put("codedcoltype",null!=referWindowColumn.getCodetype()?referWindowColumn.getCodetype():"");
			}
			jsonlist.add(map);
		}
		addJSONArray(jsonlist);
		return "success";
	}
	/**
	 * 获取参照窗口控件
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Feb 28, 2013
	 */
	public String getReferWindowWidget() throws Exception{
		String referwid = request.getParameter("id");
		String content = request.getParameter("content");
		String paramRule = request.getParameter("paramRule");
		String view = request.getParameter("view");
		String initvalue = request.getParameter("initValue");
		//是否去树状重复数据
		String treeDistint = request.getParameter("treeDistint");
		//数据参照窗口名称作为树状顶级父节点
		String treePName = request.getParameter("treePName");
		//tree树状是否全选 1是0否
		String isAllSelect = request.getParameter("isAllSelect");
		//参照窗口数据 不超过该数量时 全部显示下拉
		String listnum = request.getParameter("listnum");
		pageMap.getCondition().put("paramRule", paramRule);
		pageMap.getCondition().put("view", view);
		pageMap.getCondition().put("treeDistint", treeDistint);
		pageMap.getCondition().put("treePName", treePName);
		pageMap.getCondition().put("isAllSelect", isAllSelect);
		pageMap.getCondition().put("listnum", listnum);
		pageMap.getCondition().put("content", content);
		if(null!=initvalue && !"".equals(initvalue)){
			pageMap.getCondition().put("initvalue", initvalue);
		}
		
		Map referMap = referWindowService.getReferWindowWidget(pageMap, referwid);
		String model = (String) referMap.get("model");
		Map map = new HashMap();
		//参照窗口未启用或者不存在
		if("none".equals(model)){
			map.put("model", "none");
		}else{
			if("normal".equals(model)){
				List fieldList = new ArrayList();
				//参照窗口下拉框的字段
				Map fieldMap = new HashMap();
				List<ReferWindowColumn> columnList = (List) referMap.get("column");
				int width = 0;
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
							|| "3".equals(referWindowColumn.getIsquote()) ||"6".equals(referWindowColumn.getIsquote())
							||"0".equals(referWindowColumn.getIsquote())){
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
			}
			map.put("refertype",referMap.get("refertype"));
			map.put("data", referMap.get("list"));
			ReferWindow referWindow = (ReferWindow) referMap.get("referWindow");
			map.put("type", "refer");
			map.put("dataCount", referMap.get("dataCount"));
			map.put("ajaxLoad", referMap.get("ajaxLoad"));
			map.put("model", model);
			map.put("referid", referwid);
			map.put("wname", referWindow.getWname());
		}
		addJSONObject(map);
		return "success";
	}


	/**
	 * 获取参照窗口下拉数据
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jul 4, 2013
	 */
	public String getWidgetDataByReferid() throws Exception {
		String referid = request.getParameter("referid");
		String col = request.getParameter("col");
		String colname = request.getParameter("colname");
		String q = request.getParameter("q");
		String paramRule = request.getParameter("paramRule");
		String sqlformat = request.getParameter("sqlformat");
		Map map = new HashMap();
		map.put("referid", referid);
		map.put("col", col);
		map.put("colname", colname);
		map.put("content", q);
		map.put("paramRule", paramRule);
		map.put("sqlformat", sqlformat);
		// map赋值到pageMap中作为查询条件
		List list = referWindowService.getReferWindowDataByReferid(map);
		Map resultMap = new HashMap();
		resultMap.put("listStr",list);
		addJSONObject(resultMap);
		return "success";
	}
}

