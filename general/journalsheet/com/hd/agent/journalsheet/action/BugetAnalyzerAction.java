/**
 * @(#)BugetAnalyzerAction.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月28日 huangzhiqian 创建版本
 */
package com.hd.agent.journalsheet.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.journalsheet.model.BugetAnalyzer;
import com.hd.agent.journalsheet.service.IBugetAnalyzerService;
import com.hd.agent.system.service.ISysCodeService;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class BugetAnalyzerAction  extends BaseFilesAction{
	private static final long serialVersionUID = 1L;
	
	private IBugetAnalyzerService bugetAnalyzerService;
	private ISysCodeService sysCodeService;
	
	private BugetAnalyzer bugetAnalyzer;
	
	public BugetAnalyzer getBugetAnalyzer() {
		return bugetAnalyzer;
	}

	public void setBugetAnalyzer(BugetAnalyzer bugetAnalyzer) {
		this.bugetAnalyzer = bugetAnalyzer;
	}

	public ISysCodeService getSysCodeService() {
		return sysCodeService;
	}

	public void setSysCodeService(ISysCodeService sysCodeService) {
		this.sysCodeService = sysCodeService;
	}

	public IBugetAnalyzerService getBugetAnalyzerService() {
		return bugetAnalyzerService;
	}

	public void setBugetAnalyzerService(IBugetAnalyzerService bugetAnalyzerService) {
		this.bugetAnalyzerService = bugetAnalyzerService;
	}
	
	
	/**
	 * 主页面
	 * @return
	 * @author huangzhiqian
	 * @throws Exception 
	 * @date 2015年12月29日
	 */
	public String showPage() throws Exception{
		List budgettypeList = sysCodeService.showSysCodeListByType("budgettype");
		request.setAttribute("budgettypeList", budgettypeList);
		return SUCCESS;
	}
	
	/**
	 * 首页数据
	 * @return
	 * @author huangzhiqian
	 * @throws Exception 
	 * @date 2015年12月29日
	 */
	public String firstpageData() throws Exception{
		
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		pageMap.setOrderSql(" addtime desc ");
		PageData pageData = bugetAnalyzerService.getbugetAnalyzerList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	
	/**
	 * 添加或者修改页面
	 * @return
	 * @author huangzhiqian
	 * @throws Exception 
	 * @date 2015年12月29日
	 */
	public String showaddOrEditPage() throws Exception{
		List budgettypeList = sysCodeService.showSysCodeListByType("budgettype");
		request.setAttribute("budgettypeList", budgettypeList);
		String id = request.getParameter("id");
		String type = "add";
		if(StringUtils.isNotEmpty(id)){
			//修改查看
			type = request.getParameter("type");
			BugetAnalyzer bugetAnalyzer = bugetAnalyzerService.getBugetAnalyzerById(id);
			bugetAnalyzer.setYearMonth(bugetAnalyzer.getYear()+"-"+bugetAnalyzer.getMonth());
			if(bugetAnalyzer == null){
				request.setAttribute("error","未找到要修改的数据");
			}else{
				request.setAttribute("addtime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(bugetAnalyzer.getAddtime()));
				request.setAttribute("bugetAnalyzer", bugetAnalyzer);
			}
		}else{
			//添加
			request.setAttribute("current", new SimpleDateFormat("yyyy-MM").format(new Date()));
		}
		request.setAttribute("type", type);
		return SUCCESS;
	}
	
	/**
	 * 预算录入
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年12月29日
	 */
	public String addBuget()throws Exception{
		SysUser user = getSysUser();
		bugetAnalyzer.setAdduserid(user.getUserid());
		bugetAnalyzer.setAddtime(new Date());
		String[] yearMonth = bugetAnalyzer.getYearMonth().split("-");
		bugetAnalyzer.setYear(yearMonth[0]);
		bugetAnalyzer.setMonth(yearMonth[1]);
		Map rs = bugetAnalyzerService.addbuget(bugetAnalyzer);
		addJSONObject(rs);
		return SUCCESS;
	}
	
	/**
	 * 删除以及批量删除
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年12月30日
	 */
	public String deleteBugetAnalyzer()throws Exception{
		String ids = request.getParameter("ids");
		String[] idArr = ids.split(",");
		Map rs =new HashMap();
		try{
			rs = bugetAnalyzerService.deletebugetAnalyzer(idArr);
			
		}catch(Exception e){
			e.printStackTrace();
			rs.put("flag",false);
			rs.put("msg",e.getLocalizedMessage());
		}
		addJSONObject(rs);
		return SUCCESS;
	}
	
	/**
	 * 启用以及批量启用
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年12月30日
	 */
	public String enableBugetAnalyzer()throws Exception{
		String ids = request.getParameter("ids");
		String[] idArr = ids.split(",");
		Map rs =new HashMap();
		try{
			rs = bugetAnalyzerService.updateState(idArr,"enable");
		}catch(Exception e){
			e.printStackTrace();
			rs.put("flag",false);
			rs.put("msg",e.getLocalizedMessage());
		}
		addJSONObject(rs);
		return SUCCESS;
	}
	
	/**
	 * 禁用以及批量禁用
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年12月30日
	 */
	public String diableBugetAnalyzer()throws Exception{
		String ids = request.getParameter("ids");
		String[] idArr = ids.split(",");
		Map rs =new HashMap();
		try{
			rs = bugetAnalyzerService.updateState(idArr,"diable");
		}catch(Exception e){
			e.printStackTrace();
			rs.put("flag",false);
			rs.put("msg",e.getLocalizedMessage());
		}
		addJSONObject(rs);
		return SUCCESS;
	}
	
	/**
	 * 修改保存
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年12月30日
	 */
	public String editBugetSave()throws Exception{
		SysUser user = getSysUser();
		bugetAnalyzer.setAdduserid(user.getUserid());
//		bugetAnalyzer.setAddtime(new Date());
		String[] yearMonth = bugetAnalyzer.getYearMonth().split("-");
		bugetAnalyzer.setYear(yearMonth[0]);
		bugetAnalyzer.setMonth(yearMonth[1]);
		Map rs = bugetAnalyzerService.updatebugetAnalyzer(bugetAnalyzer);
		addJSONObject(rs);
		return SUCCESS;
	}
	
	
}

