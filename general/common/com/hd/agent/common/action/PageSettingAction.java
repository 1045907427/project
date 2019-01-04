/**
 * @(#)PageSettingAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-28 chenwei 创建版本
 */
package com.hd.agent.common.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.druid.util.StringUtils;
import com.hd.agent.common.util.JSONUtils;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.model.PageSetting;
import com.hd.agent.common.model.Query;
import com.hd.agent.common.model.Shortcut;
import com.hd.agent.common.service.IPageSettingService;
import com.hd.agent.common.util.CommonUtils;

/**
 * 
 * 界面个性化相关操作
 * @author chenwei
 */
public class PageSettingAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5468788045288716L;

	private Query query;
	
	private Shortcut shortcut;
	
	private IPageSettingService pageSettingService;
	
	public IPageSettingService getPageSettingService() {
		return pageSettingService;
	}

	public void setPageSettingService(IPageSettingService pageSettingService) {
		this.pageSettingService = pageSettingService;
	}
	
	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}
	
	public Shortcut getShortcut() {
		return shortcut;
	}

	public void setShortcut(Shortcut shortcut) {
		this.shortcut = shortcut;
	}

	/**
	 * 保存页面table样式
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-28
	 */
	public String savePageCulumn() throws Exception{
		SysUser sysUser = getSysUser();
		String frozenColumns = request.getParameter("frozenColumns");
		String columns = request.getParameter("columns");
		String grid = request.getParameter("grid");
		//表名
		String name = request.getParameter("name");
		String columnOld = request.getParameter("columnOld");
		
		//原字段
		JSONArray columnOldArray = JSONArray.fromObject(columnOld).getJSONArray(0);
		//冻结字段
		JSONArray frozenColumnsArray = JSONArray.fromObject(frozenColumns).getJSONArray(0);
		//普通字段
		JSONArray columnsArray = JSONArray.fromObject(columns).getJSONArray(0);
		//
		Map map = new HashMap();
		for(int j=0;j<frozenColumnsArray.size();j++){
			JSONObject frozenObject = frozenColumnsArray.getJSONObject(j);
			String field = frozenObject.getString("field");
			map.put(field, field);
		}
		for(int z=0;z<columnsArray.size();z++){
			JSONObject columnsObject = columnsArray.getJSONObject(z);
			String field = columnsObject.getString("field");
			map.put(field, field);
		}
		JSONArray columnOldNoSelect = new JSONArray();
		for(int i=0;i<columnOldArray.size();i++){
			JSONObject commonOldObject = columnOldArray.getJSONObject(i);
			String field = commonOldObject.getString("field");
			String flag = (String) map.get(field);
			if(null==flag){
				columnOldNoSelect.add(commonOldObject);
			}
		}
		for(int i=0;i<columnOldNoSelect.size();i++){
			columnsArray.add(columnOldNoSelect.getJSONObject(i));
		}
		JSONArray commonEnd = new JSONArray();
		commonEnd.add(columnsArray);
		
		PageSetting pageSetting = new PageSetting();
		pageSetting.setFrozencol(frozenColumns);
		pageSetting.setGrid(grid);
		pageSetting.setUserid(sysUser.getUserid());
		pageSetting.setTablename(name);
		pageSetting.setCommoncol(commonEnd.toString());
		boolean flag = pageSettingService.savePageCulumn(pageSetting);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 删除表单样式
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 11, 2013
	 */
	public String deletePageCulumn() throws Exception{
		String grid = request.getParameter("grid");
		//表名
		String name = request.getParameter("name");
		SysUser sysUser = getSysUser();
		boolean flag = pageSettingService.deletePageCulumn(grid, sysUser.getUserid(), name);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 根据表格id获取该用户针对该表格的个性化配置
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-28
	 */
	public String getPageCulumn() throws Exception{
		SysUser sysUser = getSysUser();
		String grid = request.getParameter("grid");
		//表名
		String names = request.getParameter("name");
		String[] tablename = names.split(",");

		String frozenCol = request.getParameter("frozenCol");
		String commonCol = request.getParameter("commonCol");

        //页面中拥有的字段列表
		JSONArray pagefrozenJson = JSONArray.fromObject(frozenCol);
		Map pageColMap = new HashMap();
		for(int i=0;i<pagefrozenJson.getJSONArray(0).size();i++){
			JSONObject jsonObject = pagefrozenJson.getJSONArray(0).getJSONObject(i);
			if(null!=jsonObject && jsonObject.has("field")){
				String field = jsonObject.getString("field");
				pageColMap.put(field, field);
			}
		}
		JSONArray pagecommonJson = JSONArray.fromObject(commonCol);
		for(int i=0;i<pagecommonJson.getJSONArray(0).size();i++){
			JSONObject jsonObject = pagecommonJson.getJSONArray(0).getJSONObject(i);
			if(null!=jsonObject && jsonObject.has("field")){
				String field = jsonObject.getString("field");
				pageColMap.put(field, field);
			}
		}
		Map addMap = new HashMap();
		Map map = new HashMap();
		//判断是否进行字段权限控制
		if(null!=names&&!"".equals(names)){
			Map colsMap = null;
			for(String name :tablename){
				//处理页面传递过来的表名
				name = CommonUtils.tablenameDealWith(name);
				if(null == colsMap){
					//获取该用户能访问该表的字段列表
					colsMap = getAccessColumn(name);
				}else{
					Map colsMap1 = getAccessColumn(name);
					colsMap.putAll(colsMap1);
				}
			}
			if(colsMap.size()>0){
				colsMap.put("ck", "ck");
			}
			//获取该用户的保存的个性化表格配置
			PageSetting pageSetting = pageSettingService.getPageCulumn(grid, sysUser.getUserid(),names);
			//判断是否有用户个性化配置信息
			if(null!=pageSetting){
				//判断该表格中是否有不能访问的字段 并且移除
				JSONArray frozenJson = JSONArray.fromObject(pageSetting.getFrozencol());
				JSONArray frozen = new JSONArray();
				for(int i=0;i<frozenJson.getJSONArray(0).size();i++){
					JSONObject jsonObject = frozenJson.getJSONArray(0).getJSONObject(i);
					String oldfield = jsonObject.getString("field");
					String field = jsonObject.getString("field");
					//判断是否有别名的字段
					if(jsonObject.containsKey("aliascol")){
						field = jsonObject.getString("aliascol");
					}
					String cols = (String) colsMap.get(field);
					//判断该字段是否受字段权限控制
					if(null!=cols||colsMap.size()==0){
						frozen.add(frozenJson.getJSONArray(0).get(i));
                        addMap.put(oldfield,oldfield);
					}else if(jsonObject.containsKey("isShow") && jsonObject.getBoolean("isShow")){
						boolean flag = true;
						for(int j=0;j<frozen.size();j++){
							JSONObject frozenObject = frozen.getJSONObject(j);
							String frozenField = frozenObject.getString("field");
							if(frozenField.equals(field)){
								flag = false;
								break;
							}
						}
						if(!pageColMap.containsKey(oldfield)){
							flag = false;
						}
						if(flag){
							frozen.add(frozenJson.getJSONArray(0).get(i));
                            addMap.put(oldfield,oldfield);
						}
					}
				}
				JSONArray commonJson = JSONArray.fromObject(pageSetting.getCommoncol());
				JSONArray common = new JSONArray();
				Map columnMap = new HashMap();
				columnMap.putAll(colsMap);
				for(int i=0;i<commonJson.getJSONArray(0).size();i++){
					JSONObject jsonObject = commonJson.getJSONArray(0).getJSONObject(i);
					String oldfield = jsonObject.getString("field");
					String field = jsonObject.getString("field");
					//判断是否有别名的字段
					if(jsonObject.containsKey("aliascol")){
						field = jsonObject.getString("aliascol");
					}
					String cols = (String) colsMap.get(field);
					boolean flag = true;
					if(!pageColMap.containsKey(oldfield)){
						flag = false;
					}
					if(flag){
						if(null!=cols||columnMap.size()==0){
							common.add(commonJson.getJSONArray(0).get(i));
							columnMap.remove(field);
                            addMap.put(oldfield,oldfield);
						}else if(jsonObject.containsKey("isShow") && jsonObject.getBoolean("isShow")){
							common.add(commonJson.getJSONArray(0).get(i));
                            addMap.put(oldfield,oldfield);
						}
					}
				}
				//当页面中字段存在时 字段权限中也存在时，但是页面个性化配置中没有该字段的时候
				//把这些字段添加到列显示中
				//页面设置的列列表 
				JSONArray commonPageJson = JSONArray.fromObject(commonCol);
				if(columnMap.size()>0){
					for(int i=0;i<commonPageJson.getJSONArray(0).size();i++){
						JSONObject jsonObject = commonPageJson.getJSONArray(0).getJSONObject(i);
						String oldfield = jsonObject.getString("field");
						String field = jsonObject.getString("field");
						//判断是否有别名的字段
						if(jsonObject.containsKey("aliascol")){
							field = jsonObject.getString("aliascol");
						}
						String cols = (String) columnMap.get(field);
						if(null!=cols||columnMap.size()==0){
                            boolean flag = true;
                            if(addMap.containsKey(oldfield)){
                                flag = false;
                            }
                            if(flag){
                                common.add(commonPageJson.getJSONArray(0).get(i));
                                columnMap.remove(field);
                                addMap.put(oldfield,oldfield);
                            }
						}else if(jsonObject.containsKey("isShow") && jsonObject.getBoolean("isShow")){
							boolean flag = true;
							for(int j=0;j<common.size();j++){
								JSONObject commonObject = common.getJSONObject(j);
								String commonField = commonObject.getString("field");
								if(commonField.equals(field)){
									flag = false;
									break;
								}
							}
							if(!pageColMap.containsKey(oldfield)){
								flag = false;
							}
							if(flag){
								common.add(commonPageJson.getJSONArray(0).get(i));
                                addMap.put(oldfield,oldfield);
							}
						}
					}
				}
				JSONArray frozenCols = new JSONArray();
				frozenCols.add(frozen);
				JSONArray commonCols = new JSONArray();
				commonCols.add(common);
				map.put("frozen", frozenCols.toString());
				map.put("common", commonCols.toString());
				map.put("flag", true);
			}else{
				JSONArray frozenJson = JSONArray.fromObject(frozenCol);
				JSONArray frozen = new JSONArray();
				for(int i=0;i<frozenJson.getJSONArray(0).size();i++){
					JSONObject jsonObject = frozenJson.getJSONArray(0).getJSONObject(i);
					String field = jsonObject.getString("field");
					//判断是否有别名的字段
					if(jsonObject.containsKey("aliascol")){
						field = jsonObject.getString("aliascol");
					}
					String cols = (String) colsMap.get(field);
					if(null!=cols||colsMap.size()==0){
						frozen.add(frozenJson.getJSONArray(0).get(i));
					}else if(jsonObject.containsKey("isShow") && jsonObject.getBoolean("isShow")){
						frozen.add(frozenJson.getJSONArray(0).get(i));
					}
				}
				JSONArray commonJson = JSONArray.fromObject(commonCol);
				JSONArray common = new JSONArray();
				for(int i=0;i<commonJson.getJSONArray(0).size();i++){
					JSONObject jsonObject = commonJson.getJSONArray(0).getJSONObject(i);
					String field = jsonObject.getString("field");
					//判断是否有别名的字段
					if(jsonObject.containsKey("aliascol")){
						field = jsonObject.getString("aliascol");
					}
					String cols = (String) colsMap.get(field);
					if(null!=cols||colsMap.size()==0){
						common.add(commonJson.getJSONArray(0).get(i));
					}else if(jsonObject.containsKey("isShow") && jsonObject.getBoolean("isShow")){
						common.add(commonJson.getJSONArray(0).get(i));
					}
				}
				JSONArray frozenCols = new JSONArray();
				frozenCols.add(frozen);
				JSONArray commonCols = new JSONArray();
				commonCols.add(common);
				map.put("frozen", frozenCols.toString());
				map.put("common", commonCols.toString());
				map.put("flag", true);
			}
		}else{
			PageSetting pageSetting = pageSettingService.getPageCulumn(grid, sysUser.getUserid(),names);
			if(null!=pageSetting){
				//判断该表格中是否有不能访问的字段 并且移除
				JSONArray frozenJson = JSONArray.fromObject(pageSetting.getFrozencol());
				JSONArray frozen = new JSONArray();
				for(int i=0;i<frozenJson.getJSONArray(0).size();i++){
					JSONObject jsonObject = frozenJson.getJSONArray(0).getJSONObject(i);
					String field = jsonObject.getString("field");
					boolean flag =true;
					if(!pageColMap.containsKey(field)){
						flag = false;
					}
					if(flag){
						frozen.add(frozenJson.getJSONArray(0).get(i));
                        addMap.put(field,field);
					}
				}
				JSONArray commonJson = JSONArray.fromObject(pageSetting.getCommoncol());
				JSONArray common = new JSONArray();
				for(int i=0;i<commonJson.getJSONArray(0).size();i++){
					JSONObject jsonObject = commonJson.getJSONArray(0).getJSONObject(i);
					String field = jsonObject.getString("field");
					boolean flag = true;
					if(!pageColMap.containsKey(field)){
						flag = false;
					}
					if(flag){
						common.add(commonJson.getJSONArray(0).get(i));
                        addMap.put(field,field);
					}
				}
				//当页面中字段存在时 字段权限中也存在时，但是页面个性化配置中没有该字段的时候
				//把这些字段添加到列显示中
				//页面设置的列列表 
				JSONArray commonPageJson = JSONArray.fromObject(commonCol);
				for(int i=0;i<commonPageJson.getJSONArray(0).size();i++){
					JSONObject jsonObject = commonPageJson.getJSONArray(0).getJSONObject(i);
					String field = jsonObject.getString("field");
					boolean flag = true;
					for(int j=0;j<common.size();j++){
						JSONObject commonObject = common.getJSONObject(j);
						String commonField = commonObject.getString("field");
						if(commonField.equals(field)){
							flag = false;
							break;
						}
					}
					if(!pageColMap.containsKey(field)){
						flag = false;
					}
                    if(addMap.containsKey(field)){
                        flag = false;
                    }
					if(flag){
						common.add(commonPageJson.getJSONArray(0).get(i));
                        addMap.put(field,field);
					}
				}
				JSONArray frozenCols = new JSONArray();
				frozenCols.add(frozen);
				JSONArray commonCols = new JSONArray();
				commonCols.add(common);
				map.put("frozen", frozenCols.toString());
				map.put("common", commonCols.toString());
				map.put("flag", true);
			}else{
				map.put("frozen", frozenCol);
				map.put("common", commonCol);
				map.put("flag", false);
			}
		}
		//返回全部字段列表
		JSONArray frozenJsonOld = JSONArray.fromObject(frozenCol);
		JSONArray commonJsonOld = JSONArray.fromObject(commonCol);
		for(int i=0;i<commonJsonOld.getJSONArray(0).size();i++){
			JSONObject jsonObject = commonJsonOld.getJSONArray(0).getJSONObject(i);
			frozenJsonOld.getJSONArray(0).add(jsonObject);
		}
		map.put("columnOld", frozenJsonOld);
		addJSONObject(map);
		return "success";
	}
	/**
	 * 显示高级通用查询页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-11
	 */
	public String showAdvancedQueryPage() throws Exception{
		String name = request.getParameter("name");
		String divID = request.getParameter("divID");
		String gridid = request.getParameter("gridid");
		
		SysUser sysUser = getSysUser();
		List<Query> list = pageSettingService.showUserQuery(sysUser.getUserid(),gridid);
		String queryrule = "";
		String orderrule = "";
		String defaulQueryId = "";
		for(Query query : list){
			//获取默认通用查询
			if("2".equals(query.getType())){
				queryrule = query.getQueryrule();
				orderrule = query.getOrderrule();
				defaulQueryId = query.getId();
				break;
			}
		}
		request.setAttribute("queryrule", queryrule);
		request.setAttribute("orderrule", orderrule);
		request.setAttribute("defaulQueryId", defaulQueryId);
		request.setAttribute("gridid", gridid);
		request.setAttribute("name", name);
		request.setAttribute("divID", divID);
		return "success";
	}
	/**
	 * 添加通用查询
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-12
	 */
	@UserOperateLog(key="CommonQuery",type=2,value="")
	public String addQuery() throws Exception{
		SysUser sysUser = getSysUser();
		query.setAdduserid(sysUser.getUserid());
		boolean flag = pageSettingService.addQuery(query);
		addJSONObject("flag", flag);
		
		//添加日志内容
		addLog("添加通用查询记录 编号:"+query.getId(),flag);
		return "success";
	}
	/**
	 * 获取用户的通用查询
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-12
	 */
	public String showUserQuery() throws Exception{
		SysUser sysUser = getSysUser();
		String id = request.getParameter("id");
		List list = pageSettingService.showUserQuery(sysUser.getUserid(),id);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 删除通用查询
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-12
	 */
	@UserOperateLog(key="CommonQuery",type=4,value="")
	public String deleteQuery() throws Exception{
		String id = request.getParameter("id");
		boolean flag = pageSettingService.deleteQuery(id);
		addJSONObject("flag", flag);
		
		//添加日志内容
		addLog("删除通用查询记录 编号:"+id,flag);
		return "success";
	}
	/**
	 * 显示快捷操作页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 20, 2013
	 */
	public String showShortcutPage() throws Exception{
		List list = pageSettingService.showShortcutList();
		request.setAttribute("list", list);
		return "success";
	}
	/**
	 * 显示快捷方式添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 20, 2013
	 */
	public String showShortcutAddPage() throws Exception{
		return "success";
	}
	/**
	 * 添加页面快捷方式
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 20, 2013
	 */
	@UserOperateLog(key="CommonShortcut",type=2,value="")
	public String addShortcut() throws Exception{
		SysUser sysUser = getSysUser();
		shortcut.setUserid(sysUser.getUserid());
		boolean flag = pageSettingService.addShortcut(shortcut);
		addJSONObject("flag", flag);
		
		//添加日志内容
		addLog("添加页面快捷方式 编号:"+shortcut.getId(),flag);
		return "success";
	}
	/**
	 * 删除页面快捷方式
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 20, 2013
	 */
	@UserOperateLog(key="CommonShortcut",type=4,value="")
	public String deleteShortcut() throws Exception{
		String id = request.getParameter("id");
		boolean flag = pageSettingService.deleteShortcut(id);
		addJSONObject("flag", flag);
		
		//添加日志内容
		addLog("删除页面快捷方式 编号:"+id,flag);
		return "success";
	}
}

