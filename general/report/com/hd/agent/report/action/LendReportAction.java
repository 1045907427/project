/**
 * @(#)LendReportAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 31, 2013 chenwei 创建版本
 */
package com.hd.agent.report.action;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.report.service.ILendReportService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 
 * 借货还货报表action
 * @author chenwei
 */
public class LendReportAction extends BaseFilesAction {
	
	private ILendReportService lendReportService;

	public ILendReportService getLendReportService() {
		return lendReportService;
	}
 
	public void setLendReportService(ILendReportService lendReportService) {
		this.lendReportService = lendReportService;
	}
	/**
	 * 显示仓库借货还货统计页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 31, 2013
	 */
	public String showLendReportListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 获取仓库借货还货统计数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 14, 2013
	 */
	public String showLendReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = lendReportService.showInOutReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}

	/**
	 *
	 * @throws Exception
	 */
	public void exportLendRecSendReportData() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		Map queryMap = new HashMap();
		String query = (String) map.get("param");
		JSONObject object = JSONObject.fromObject(query);
		for (Object k : object.keySet()) {
			Object v = object.get(k);
			if(StringUtils.isNotEmpty((String) v)){
				queryMap.put(k.toString(), (String) v);
			}
		}
		pageMap.setCondition(queryMap);

		PageData pageData = lendReportService.showInOutReportData(pageMap);

		List list = pageData.getList();
		if(null != pageData.getFooter()){
			List footer = pageData.getFooter();
			list.addAll(footer);
		}
		ExcelUtils.exportAnalysExcel(map,list);
	}

}

