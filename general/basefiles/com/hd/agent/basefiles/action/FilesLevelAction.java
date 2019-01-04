/**
 * @(#)FilesLevelAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-17 chenwei 创建版本
 */
package com.hd.agent.basefiles.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.FilesLevel;
import com.hd.agent.basefiles.service.IFilesLevelService;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;

/**
 * 
 * 基础档案级次定义
 * @author chenwei
 */
public class FilesLevelAction extends BaseAction{
	
	/**
	 * 基础档案级次定义service
	 */
	private IFilesLevelService filesLevelService;
	
	public IFilesLevelService getFilesLevelService() {
		return filesLevelService;
	}
	public void setFilesLevelService(IFilesLevelService filesLevelService) {
		this.filesLevelService = filesLevelService;
	}
	/**
	 * 显示基础档案级次定义页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-17
	 */
	public String showFilesLevelSet() throws Exception{
		
		return "success";
	}
	/**
	 * 显示基础档案级次定义页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-18
	 */
	public String showFilesLevelPage() throws Exception{
		String name = request.getParameter("name");
		name = StringEscapeUtils.escapeSql(name);
		List<FilesLevel> list = filesLevelService.showFilesLevelList(name);
		int length = filesLevelService.getTableFileLevelLength(name);
		int i = 0;
		for(FilesLevel filesLevel : list){
			i += filesLevel.getLen();
			if(i<=length&&length!=0){
				filesLevel.setFlag("1");
			}
		}
		request.setAttribute("list", list);
		request.setAttribute("name", name);
		request.setAttribute("size", list.size());
		
		return "success";
	}
	/**
	 * 保存档案级次定义
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-19
	 */
	@UserOperateLog(key="FilesLevel",type=2)
	public String saveFilesLevel() throws Exception{
		SysUser sysUser = getSysUser();
		String filesLevel = request.getParameter("filesLevel");
		String tablename = request.getParameter("name");
		//json字符串转为list对象
		JSONArray filesLevelJson = JSONArray.fromObject(filesLevel);
		List<FilesLevel> list = new ArrayList();
		for(int i=0;i<filesLevelJson.size();i++){
			JSONObject jsonObject = filesLevelJson.getJSONObject(i);
			FilesLevel files = new FilesLevel();
			int len = jsonObject.getInt("len");
			int level = jsonObject.getInt("level");
			files.setLen(len);
			files.setLevel(level);
			files.setTablename(tablename);
			files.setAdduserid(sysUser.getUserid());
			list.add(files);
		}
		boolean flag = filesLevelService.saveFilesLevel(list, tablename);
		addJSONObject("flag", flag);
		
		addLog("保存档案级次定义 表名:"+tablename, flag);
		return "success";
	}
	
	
}

