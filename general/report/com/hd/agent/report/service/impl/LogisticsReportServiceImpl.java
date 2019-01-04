/**
 * @(#)LogisticsReportService.java
 *
 * @author yezhenyu
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 4, 2014 yezhenyu 创建版本
 */
package com.hd.agent.report.service.impl;

import com.hd.agent.basefiles.dao.LogisticsMapper;
import com.hd.agent.basefiles.dao.PersonnelMapper;
import com.hd.agent.basefiles.model.LogisticsCar;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.report.dao.LogisticsReportMapper;
import com.hd.agent.report.model.LogisticsReport;
import com.hd.agent.report.service.ILogisticsReportService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author yezhenyu
 */
public class LogisticsReportServiceImpl extends BaseFilesServiceImpl implements ILogisticsReportService {

	private LogisticsReportMapper logisticsReportMapper;
	private PersonnelMapper personnelMapper;
	private LogisticsMapper logisticsMapper;

	public LogisticsMapper getLogisticsMapper() {
		return logisticsMapper;
	}

	public void setLogisticsMapper(LogisticsMapper logisticsMapper) {
		this.logisticsMapper = logisticsMapper;
	}

	public LogisticsReportMapper getLogisticsReportMapper() {
		return logisticsReportMapper;
	}

	public void setLogisticsReportMapper(LogisticsReportMapper logisticsReportMapper) {
		this.logisticsReportMapper = logisticsReportMapper;
	}

	public PersonnelMapper getPersonnelMapper() {
		return personnelMapper;
	}

	public void setPersonnelMapper(PersonnelMapper personnelMapper) {
		this.personnelMapper = personnelMapper;
	}

	@Override
	public PageData getLogisticsReportList(PageMap pageMap) throws Exception {
		int count = logisticsReportMapper.getLogisticsReportListCount(pageMap);
		List list = logisticsReportMapper.getLogisticsReportList(pageMap);
		BigDecimal totalamount = new BigDecimal(0);
		Integer customernums = 0;
		BigDecimal salesamount = new BigDecimal(0);
		BigDecimal collectionamount = new BigDecimal(0);
		BigDecimal boxnum = new BigDecimal(0);
		BigDecimal trucksubsidy = new BigDecimal(0);
		BigDecimal carallowance = new BigDecimal(0);
		BigDecimal carsubsidy = new BigDecimal(0);
		BigDecimal customersubsidy = new BigDecimal(0);
		BigDecimal salessubsidy = new BigDecimal(0);
		BigDecimal collectionsubsidy = new BigDecimal(0);
		BigDecimal othersubsidy = new BigDecimal(0);
		BigDecimal subamount = new BigDecimal(0);
		BigDecimal safebonus = new BigDecimal(0);
		BigDecimal receiptbonus = new BigDecimal(0);
		BigDecimal nightbonus = new BigDecimal(0);

		for (Object object : list) {
			LogisticsReport logisticsReport = (LogisticsReport) object;
			if(StringUtils.isNotEmpty(logisticsReport.getDriverid())){
				Personnel personnel = personnelMapper.getPersonnelInfo(logisticsReport.getDriverid());
				if(null != personnel){
					logisticsReport.setDrivername(personnel.getName());
				}
				else{
					logisticsReport.setDrivername("其他（未指定）");
				}
			}else{
				logisticsReport.setDrivername("其他（未指定）");
			}
//			LogisticsCar car = logisticsCarMapper.getCar(logisticsReport.getCarid());
//			logisticsReport.setCarname(car.getName());
//			String linename = logisticsLineMapper.getLineInfo(logisticsReport.getLineid()).getName();
//			logisticsReport.setLinename(linename);

			totalamount = totalamount.add(logisticsReport.getTotalamount());
			customernums +=logisticsReport.getCustomernums();
			salesamount = salesamount.add(logisticsReport.getSalesamount());
			collectionamount = collectionamount.add(logisticsReport.getCollectionamount());
			boxnum = boxnum.add(logisticsReport.getBoxnum());
			trucksubsidy = trucksubsidy.add(logisticsReport.getTrucksubsidy());
			carallowance = carallowance.add(logisticsReport.getCarallowance());
			carsubsidy = carsubsidy.add(logisticsReport.getCarsubsidy());
			customersubsidy = customersubsidy.add(logisticsReport.getCustomersubsidy());
			salessubsidy = salessubsidy.add(logisticsReport.getSalessubsidy());
			collectionsubsidy = collectionsubsidy.add(logisticsReport.getCollectionsubsidy());
			othersubsidy = othersubsidy.add(logisticsReport.getOthersubsidy());
			subamount = subamount.add(logisticsReport.getSubamount());
			safebonus = safebonus.add(logisticsReport.getSafebonus());
			receiptbonus = receiptbonus.add(logisticsReport.getReceiptbonus());
			nightbonus = nightbonus.add(logisticsReport.getNightbonus());
		}

		Map dataSum = new HashMap();
		dataSum.put("totalamount", totalamount);
		dataSum.put("customernums", customernums);
		dataSum.put("salesamount", salesamount);
		dataSum.put("collectionamount", collectionamount);
		dataSum.put("boxnum", boxnum);
		dataSum.put("trucksubsidy", trucksubsidy);
		dataSum.put("carallowance", carallowance);
		dataSum.put("carsubsidy", carsubsidy);
		dataSum.put("customersubsidy", customersubsidy);
		dataSum.put("salessubsidy", salessubsidy);
		dataSum.put("collectionsubsidy", collectionsubsidy);
		dataSum.put("othersubsidy", othersubsidy);
		dataSum.put("subamount", subamount);
		dataSum.put("safebonus", safebonus);
		dataSum.put("receiptbonus", receiptbonus);
		dataSum.put("nightbonus", nightbonus);

		PageData pageData = new PageData(count, list, pageMap);

		if (null != dataSum) {
			List footer = new ArrayList();
			dataSum.put("driverid", "合计");
			footer.add(dataSum);
			pageData.setFooter(footer);
		}

		return pageData;
	}

	@Override
	public PageData getLogisticsReportDetailList(PageMap pageMap) throws Exception {
		List list = logisticsReportMapper.getLogisticsReportDetailList(pageMap);

		BigDecimal totalamount = new BigDecimal(0);
		Integer customernums = 0;
		BigDecimal salesamount = new BigDecimal(0);
		BigDecimal collectionamount = new BigDecimal(0);
		BigDecimal boxnum = new BigDecimal(0);
		BigDecimal trucksubsidy = new BigDecimal(0);
		BigDecimal carallowance = new BigDecimal(0);
		BigDecimal carsubsidy = new BigDecimal(0);
		BigDecimal customersubsidy = new BigDecimal(0);
		BigDecimal salessubsidy = new BigDecimal(0);
		BigDecimal collectionsubsidy = new BigDecimal(0);
		BigDecimal othersubsidy = new BigDecimal(0);
		BigDecimal subamount = new BigDecimal(0);
		BigDecimal safebonus = new BigDecimal(0);
		BigDecimal receiptbonus = new BigDecimal(0);
		BigDecimal nightbonus = new BigDecimal(0);

		for (Object object : list) {
			LogisticsReport logisticsReport = (LogisticsReport) object;
			if(StringUtils.isNotEmpty(logisticsReport.getDriverid())){
				Personnel personnel = personnelMapper.getPersonnelInfo(logisticsReport.getDriverid());
				if(null != personnel){
					logisticsReport.setDrivername(personnel.getName());
				}else{
					logisticsReport.setDrivername("其他（未指定）");
				}
			}else{
				logisticsReport.setDrivername("其他（未指定）");
			}
			LogisticsCar car = logisticsMapper.getCar(logisticsReport.getCarid());
			logisticsReport.setCarname(car.getName());
			String linename = logisticsMapper.getLineInfo(logisticsReport.getLineid()).getName();
			logisticsReport.setLinename(linename);

			totalamount = totalamount.add(logisticsReport.getTotalamount());
			customernums +=logisticsReport.getCustomernums();
			salesamount = salesamount.add(logisticsReport.getSalesamount());
			collectionamount = collectionamount.add(logisticsReport.getCollectionamount());
			boxnum = boxnum.add(logisticsReport.getBoxnum());
			trucksubsidy = trucksubsidy.add(logisticsReport.getTrucksubsidy());
			carallowance = carallowance.add(logisticsReport.getCarallowance());
			carsubsidy = carsubsidy.add(logisticsReport.getCarsubsidy());
			customersubsidy = customersubsidy.add(logisticsReport.getCustomersubsidy());
			salessubsidy = salessubsidy.add(logisticsReport.getSalessubsidy());
			collectionsubsidy = collectionsubsidy.add(logisticsReport.getCollectionsubsidy());
			othersubsidy = othersubsidy.add(logisticsReport.getOthersubsidy());
			subamount = subamount.add(logisticsReport.getSubamount());
			safebonus = safebonus.add(logisticsReport.getSafebonus());
			receiptbonus = receiptbonus.add(logisticsReport.getReceiptbonus());
			nightbonus = nightbonus.add(logisticsReport.getNightbonus());
		}

		Map dataSum = new HashMap();
		dataSum.put("totalamount", totalamount);
		dataSum.put("customernums", customernums);
		dataSum.put("salesamount", salesamount);
		dataSum.put("collectionamount", collectionamount);
		dataSum.put("boxnum", boxnum);
		dataSum.put("trucksubsidy", trucksubsidy);
		dataSum.put("carallowance", carallowance);
		dataSum.put("carsubsidy", carsubsidy);
		dataSum.put("customersubsidy", customersubsidy);
		dataSum.put("salessubsidy", salessubsidy);
		dataSum.put("collectionsubsidy", collectionsubsidy);
		dataSum.put("othersubsidy", othersubsidy);
		dataSum.put("subamount", subamount);
		dataSum.put("safebonus", safebonus);
		dataSum.put("receiptbonus", receiptbonus);
		dataSum.put("nightbonus", nightbonus);

		PageData pageData = new PageData(list.size(), list, pageMap);

		if (null != dataSum) {
			List footer = new ArrayList();
			dataSum.put("deliveryid", "合计");
			footer.add(dataSum);
			pageData.setFooter(footer);
		}

		return pageData;
	}


}
