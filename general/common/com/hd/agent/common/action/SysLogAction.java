/**
 * @(#)SysLogAction.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-24 panxiaoxiao 创建版本
 */
package com.hd.agent.common.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.JSONUtils;
import org.apache.commons.lang.StringUtils;

import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.model.SysLog;
import com.hd.agent.common.service.ISysLogService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class SysLogAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8827488649722548708L;
	private ISysLogService sysLogService;
	private SysLog sysLog;
	
	public SysLog getSysLog() {
		return sysLog;
	}

	public void setSysLog(SysLog sysLog) {
		this.sysLog = sysLog;
	}

	public ISysLogService getSysLogService() {
		return sysLogService;
	}

	public void setSysLogService(ISysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}

	/**
	 * 显示首页
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-20
	 */
	@UserOperateLog(key="sysLog",type=0,value="显示首页")
	public String Index() throws Exception{
		return "success";
	}
	
	/**
	 * 显示系统日志页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-24
	 */
	public String showSysLogPage() throws Exception{
		return "success";
	}

	/**
	 * 显示查询的系统用户日志列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-24
	 */
	public String showSearchSysLogList()throws Exception{
		Map mapMap=request.getParameterMap();
		Map map=CommonUtils.changeMap(mapMap);
		pageMap.setCondition(map);
		PageData pageData=sysLogService.showSearchSysLog(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	
	/**
	 * 显示系统日志详情
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-24
	 */
	public String showLogInfo() throws Exception{
		String id=request.getParameter("id");
		SysLog sysLog=sysLogService.showSysLogInfo(id);
		request.setAttribute("sysLog", sysLog);
		return "success";
	}

    /**
     * 显示系统日志数据
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2015-11-10
     */
    public String showLogInfoData() throws Exception{
        String id=request.getParameter("id");
        SysLog sysLog=sysLogService.showSysLogInfoData(id);

        request.setAttribute("oldData", sysLog.getOlddata());
        request.setAttribute("newData", sysLog.getNewdata());
        request.setAttribute("changeData", sysLog.getChangedata());

        return "success";
    }
	
	/**
	 * 删除日志
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-24
	 */
	public String deleteSysLog() throws Exception{
		String id=request.getParameter("id");
		boolean flag=sysLogService.deleteSysLog(id);
		addJSONObject("flag",flag);
		return "success";
	}
	
	/**
	 * 显示系统打印日志页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-24
	 */
	public String showSysPrintLogPage() throws Exception{
		return "success";
	}

	/**
	 * 显示查询的系统用户打印日志列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-24
	 */
	public String showSearchSysPrintLogList()throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		if(null==map){
			map=new HashMap();
		}
		map.put("type", "5");
		pageMap.setCondition(map);
		PageData pageData=sysLogService.showSearchSysLog(pageMap);
		if(null!=pageData){
			List<SysLog> list=pageData.getList();
			if(null!=list){
				for(SysLog item : list){
					if(null!=item ){
						if(StringUtils.isNotEmpty(item.getContent())){
							item.setContent(item.getContent().replaceFirst("\\b回调参数[^。]*\\s*。", ""));
							item.setContent(item.getContent().replaceFirst("\\b回调地址[^。]*\\s*。", ""));
							item.setContent(item.getContent().replaceFirst("\\b回调地址参数[^。]*\\s*。", ""));
							//item.setContent(item.getContent().replaceFirst("\\b申请打印[^。]*\\s*。", ""));
							item.setContent(item.getContent().replaceFirst("\\b打印数据来源*[^。]*\\s*。", ""));
						}
					}
				}
			}
		}
		addJSONObject(pageData);
		return "success";
	}
}

