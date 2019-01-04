/**
 * @(#)LogisticsReportAction.java
 *
 * @author yezhenyu
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 4, 2014 yezhenyu 创建版本
 */
package com.hd.agent.report.action;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.report.dao.LogisticsReportMapper;
import com.hd.agent.report.model.LogisticsReport;
import com.hd.agent.report.service.ILogisticsReportService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 
 * 
 * @author yezhenyu
 */
public class LogisticsReportAction extends BaseFilesAction {
	private ILogisticsReportService logisticsReportService;
	private LogisticsReportMapper logisticsReportMapper;

	public ILogisticsReportService getLogisticsReportService() {
		return logisticsReportService;
	}

	public void setLogisticsReportService(ILogisticsReportService logisticsReportService) {
		this.logisticsReportService = logisticsReportService;
	}

	public LogisticsReportMapper getLogisticsReportMapper() {
		return logisticsReportMapper;
	}

	public void setLogisticsReportMapper(LogisticsReportMapper logisticsReportMapper) {
		this.logisticsReportMapper = logisticsReportMapper;
	}

	/**
	 * 显示物流报表列表页面
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jul 5, 2014
	 */
	public String showLogisticsReportListPage() throws Exception {
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}

	/**
	 * 获取物流统计列表
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jul 5, 2014
	 */

	public String getLogisticsReportList() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = logisticsReportService.getLogisticsReportList(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	 /**
	  * 显示物流报表列表页面(含多个跟车)
	  * @author lin_xx
	  * @date 2017/3/12
	  */
	 public String showLogisticsFollowReportListPage() throws Exception {
		 String today = CommonUtils.getTodayDataStr();
		 String firstDay = CommonUtils.getMonthDateStr();
		 request.setAttribute("firstDay", firstDay);
		 request.setAttribute("today", today);
		 return SUCCESS;
	 }

	/**
	 * 导出物流统计情况表
	 * 
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jul 5, 2014
	 */
	public void exportLogisticsData() throws Exception {
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
		pageMap.setCondition(map);
		String title = "";
		if (map.containsKey("excelTitle")) {
			title = map.get("excelTitle").toString();
		} else {
			title = "list";
		}
		if (StringUtils.isEmpty(title)) {
			title = "list";
		}
		PageData pageData = logisticsReportService.getLogisticsReportList(pageMap);
		ExcelUtils.exportExcel(exportLogisticsFilter(pageData.getList(), pageData.getFooter(), false), title);
	}

	/**
	 * 数据转换，list专程符合excel导出的数据格式(物流统计情况表)
	 * 
	 * @param list
	 * @param footerlist
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jul 5, 2014
	 */
	private List<Map<String, Object>> exportLogisticsFilter(List<LogisticsReport> list, List footerlist,
			boolean isDetail) throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		if (isDetail)
			firstMap.put("deliveryid", "配送单号");
		firstMap.put("driverid", "人员名称");
		firstMap.put("isdriver", "职位");
		firstMap.put("customernums", "家数");
		firstMap.put("salesamount", "销售额");
		firstMap.put("collectionamount", "收款金额");
		firstMap.put("boxnum", "箱数");
		firstMap.put("trucksubsidy", "装车补助");
		firstMap.put("carallowance", "出车津贴");
		firstMap.put("carsubsidy", "出车补助");
		firstMap.put("customersubsidy", "家数补助");
		firstMap.put("salessubsidy", "销售补助");
		firstMap.put("collectionsubsidy", "收款补助");
		firstMap.put("othersubsidy", "其他");
		firstMap.put("subamount", "合计金额");
		firstMap.put("safebonus", "安全奖金");
		firstMap.put("receiptbonus", "回单奖金");
		firstMap.put("nightbonus", "晚上出车");
		firstMap.put("totalamount", "总计金额");
		result.add(firstMap);

		if (list.size() != 0) {
			for (LogisticsReport logisticsReport : list) {
				logisticsReport.setIsdriver(logisticsReport.getIsdriver().equals("1") ? "司机" : "跟车");
				logisticsReport.setDriverid(logisticsReport.getDrivername());
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(logisticsReport);
				for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
					if (map.containsKey(fentry.getKey())) { // 如果记录中包含该Key，则取该Key的Value
						for (Map.Entry<String, Object> entry : map.entrySet()) {
							if (fentry.getKey().equals(entry.getKey())) {
								objectCastToRetMap(retMap, entry);
							}
						}
					} else {
						retMap.put(fentry.getKey(), "");
					}
				}
				result.add(retMap);
			}
		}
		if (footerlist.size() != 0) {
			Map<String, Object> retMap = new LinkedHashMap<String, Object>();
			Map<String, Object> map = (HashMap) footerlist.get(0);
			for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
				if (map.containsKey(fentry.getKey())) { // 如果记录中包含该Key，则取该Key的Value
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						if (fentry.getKey().equals(entry.getKey())) {
							objectCastToRetMap(retMap, entry);
						}
					}
				} else {
					retMap.put(fentry.getKey(), "");
				}
			}
			result.add(retMap);
		}
		return result;
	}

	/**
	 * 显示物流奖金详细页面
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jul 5, 2014
	 */
	public String showLogisticsReportDetailPage() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		String businessdate1 = (String) map.get("businessdate1");
		String businessdate2 = (String) map.get("businessdate2");
		String driverid = (String) map.get("driverid");
		String drivername = (String) map.get("drivername");

		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		request.setAttribute("driverid", driverid);
		request.setAttribute("drivername", drivername);
		return SUCCESS;
	}

	/**
	 * 显示物流奖金详细列表
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jul 5, 2014
	 */
	public String getLogisticsReportDetailList() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = logisticsReportService.getLogisticsReportDetailList(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}

	/**
	 * 导出物流奖金详细列表
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jul 7, 2014
	 */
	public void exportLogisticsDetailData() throws Exception {
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
		pageMap.setCondition(map);
		String title = "";
		if (map.containsKey("excelTitle")) {
			title = map.get("excelTitle").toString();
		} else {
			title = "list";
		}
		if (StringUtils.isEmpty(title)) {
			title = "list";
		}
		PageData pageData = logisticsReportService.getLogisticsReportDetailList(pageMap);
		ExcelUtils.exportExcel(exportLogisticsFilter(pageData.getList(), pageData.getFooter(), true), title);

	}
}
