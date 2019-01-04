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
import java.util.List;
import java.util.Map;

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
public class BugetAnalyzerGroupAction  extends BaseFilesAction{
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
		String year = new SimpleDateFormat("yyyy").format(new Date());
		request.setAttribute("current", year);
		return SUCCESS;
	}
	
	
	/**
	 * 数据
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年12月30日
	 */
	public String bugetAnalyzerGroupData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = bugetAnalyzerService.getbugetAnalyzerGroupData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	
}

