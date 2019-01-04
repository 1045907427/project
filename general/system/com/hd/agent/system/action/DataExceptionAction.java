/**
 * @(#)DataExceptionAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-22 chenwei 创建版本
 */
package com.hd.agent.system.action;

import java.util.List;
import java.util.Map;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.system.model.DataException;
import com.hd.agent.system.model.DataExceptionOperate;
import com.hd.agent.system.service.IDataExceptionService;

/**
 * 
 * 数据异常规则相关action
 * @author chenwei
 */
public class DataExceptionAction extends BaseAction {

	private DataException dataException;
	
	private DataExceptionOperate dataExceptionOperate;
	
	private IDataExceptionService dataExceptionService;

	public IDataExceptionService getDataExceptionService() {
		return dataExceptionService;
	}

	public void setDataExceptionService(IDataExceptionService dataExceptionService) {
		this.dataExceptionService = dataExceptionService;
	}
	
	public DataException getDataException() {
		return dataException;
	}

	public void setDataException(DataException dataException) {
		this.dataException = dataException;
	}

	public DataExceptionOperate getDataExceptionOperate() {
		return dataExceptionOperate;
	}

	public void setDataExceptionOperate(DataExceptionOperate dataExceptionOperate) {
		this.dataExceptionOperate = dataExceptionOperate;
	}

	/**
	 * 显示数据异常规则定义页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-22
	 */
	public String showDataExceptionPage() throws Exception{
		return "success";
	}
	/**
	 * 显示数据异常规则添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-22
	 */
	public String showDataExceptionAddPage() throws Exception{
		
		return "success";
	}
	/**
	 * 添加数据异常规则
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-23
	 */
	@UserOperateLog(key="DataException",type=2)
	public String addDataException() throws Exception{
		SysUser sysUser = getSysUser();
		
		dataException.setAdduserid(sysUser.getUserid());
		boolean flag = dataExceptionService.addDataException(dataException);
		addJSONObject("flag", flag);
		addLog("添加数据异常规则 编号:"+dataException.getId(), flag);
		return "success";
	}
	/**
	 * 获取数据异常规则分页数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-23
	 */
	public String showDataExceptionList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = dataExceptionService.showDataExceptionList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 显示数据异常规则底下对应功能配置添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-23
	 */
	public String showDataExceptionOperateAddPage() throws Exception{
		String pid = request.getParameter("pid");
		String divid = request.getParameter("divid");
		request.setAttribute("pid", pid);
		request.setAttribute("divid", divid);
		return "success";
	}
	/**
	 * 添加数据异常规则对应功能
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-24
	 */
	@UserOperateLog(key="DataException",type=2)
	public String addDataExceptionOperate() throws Exception{
		SysUser sysUser = getSysUser();
		dataExceptionOperate.setAdduserid(sysUser.getUserid());
		boolean flag = dataExceptionService.addDataExceptionOperate(dataExceptionOperate);
		addJSONObject("flag", flag);
		
		addLog("添加数据异常规则对应操作功能 编号:"+dataExceptionOperate.getId(), flag);
		return "success";
	}
	/**
	 * 根据数据异常规则获取它下面对应操作功能列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-24
	 */
	public String showDataExceptionOperateList() throws Exception{
		String dataexceptionid = request.getParameter("dataexceptionid");
		List list = dataExceptionService.showDataExceptionOperateList(dataexceptionid);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 删除数据异常规则下的对应操作功能
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-24
	 */
	@UserOperateLog(key="DataException",type=4)
	public String deleteDataExceptionOperate() throws Exception{
		String id = request.getParameter("id");
		boolean flag = dataExceptionService.deleteDataExceptionOperate(id);
		addJSONObject("flag", flag);
		
		addLog("删除数据异常规则对应操作功能 编号:"+id, flag);
		return "success";
	}
	/**
	 * 删除数据异常规则
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-25
	 */
	@UserOperateLog(key="DataException",type=4)
	public String deleteDataException() throws Exception{
		String id = request.getParameter("id");
		boolean flag = dataExceptionService.deleteDataException(id);
		addJSONObject("flag", flag);
		
		addLog("删除数据异常规则 编号:"+id, flag);
		return "success";
	}
	/**
	 * 显示数据异常规则修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-25
	 */
	public String showDataExceptionEditPage() throws Exception{
		String id = request.getParameter("id");
		DataException dataException = dataExceptionService.showDataExceptionInfo(id);
		request.setAttribute("dataException", dataException);
		return "success";
	}
	/**
	 * 修改数据异常规则信息
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-25
	 */
	@UserOperateLog(key="DataException",type=3)
	public String editDataException() throws Exception{
		SysUser sysUser = getSysUser();
		dataException.setModifyuserid(sysUser.getUserid());
		boolean flag = dataExceptionService.editDataException(dataException);
		addJSONObject("flag", flag);
		
		addLog("修改数据异常规则 编号:"+dataException.getId(), flag);
		return "success";
	}
	/**
	 * 启用数据异常规则
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-25
	 */
	@UserOperateLog(key="DataException",type=3)
	public String openDataException() throws Exception{
		String id = request.getParameter("id");
		boolean flag = dataExceptionService.openDataException(id);
		addJSONObject("flag", flag);
		
		addLog("启用数据异常规则 编号:"+id, flag);
		return "success";
	}
	/**
	 * 停用数据异常规则
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-25
	 */
	@UserOperateLog(key="DataException",type=3)
	public String closeDataException() throws Exception{
		String id = request.getParameter("id");
		boolean flag = dataExceptionService.closeDataException(id);
		addJSONObject("flag", flag);
		
		addLog("关闭数据异常规则 编号:"+id, flag);
		return "success";
	}
}

