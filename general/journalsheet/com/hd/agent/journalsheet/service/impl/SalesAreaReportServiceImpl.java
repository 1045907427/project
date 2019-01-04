/**
 * @(#)SalesAreaReportServiceImpl.java
 *
 * @author wanghongteng
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016年4月15日 wanghongteng 创建版本
 */
package com.hd.agent.journalsheet.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.journalsheet.dao.SalesAreaReportMapper;
import com.hd.agent.journalsheet.service.ISalesAreaReportService;
import com.hd.agent.report.model.BaseSalesReport;

/**
 * 
 * 
 * @author wanghongteng
 */
/**
 * @author Administrator
 *
 */
public class SalesAreaReportServiceImpl extends BaseFilesServiceImpl implements ISalesAreaReportService{
	
	private SalesAreaReportMapper salesAreaReportMapper;
	
	public SalesAreaReportMapper getSalesAreaReportMapper() {
		return salesAreaReportMapper;
	}

	public void setSalesAreaReportMapper(SalesAreaReportMapper salesAreaReportMapper) {
		this.salesAreaReportMapper = salesAreaReportMapper;
	}

	@Override
	public PageData showDSalesAreaReportData(PageMap pageMap) throws Exception {
		List<Map> list = salesAreaReportMapper.getSalesAreaDataList(pageMap);
		List removeList = new ArrayList();
		for(Map map : list){
			if(null == (BigDecimal)map.get("price")){
				removeList.add(map);
			}
			else{
				BigDecimal sendnum = null !=(BigDecimal)map.get("sendnum") ? (BigDecimal)map.get("sendnum") : BigDecimal.ZERO;
				BigDecimal checkreturnnum = null != (BigDecimal)map.get("checkreturnnum") ? (BigDecimal)map.get("checkreturnnum"): BigDecimal.ZERO;
				BigDecimal returnnum = null != (BigDecimal)map.get("returnnum") ? (BigDecimal)map.get("returnnum") : BigDecimal.ZERO;
				BigDecimal sendtotalbox = null != (BigDecimal)map.get("sendtotalbox") ? (BigDecimal)map.get("sendtotalbox"): BigDecimal.ZERO;
				BigDecimal returntotalbox = null != (BigDecimal)map.get("returntotalbox") ? (BigDecimal)map.get("returntotalbox") : BigDecimal.ZERO;
				BigDecimal sendamount = null != (BigDecimal)map.get("sendamount") ? (BigDecimal)map.get("sendamount") : BigDecimal.ZERO;
				BigDecimal returnamount = null !=(BigDecimal)map.get("returnamount") ? (BigDecimal)map.get("returnamount") : BigDecimal.ZERO;
				BigDecimal pushbalanceamount = null != (BigDecimal)map.get("pushbalanceamount") ?  (BigDecimal)map.get("pushbalanceamount") : BigDecimal.ZERO;
				BigDecimal pushbalancenotaxamount = null != (BigDecimal)map.get("pushbalancenotaxamount") ? (BigDecimal)map.get("pushbalancenotaxamount") : BigDecimal.ZERO;
	            BigDecimal sendnotaxamount = null != (BigDecimal)map.get("sendnotaxamount") ? (BigDecimal)map.get("sendnotaxamount") : BigDecimal.ZERO;
				BigDecimal returnnotaxamount = null != (BigDecimal)map.get("returnnotaxamount") ? (BigDecimal)map.get("returnnotaxamount") : BigDecimal.ZERO;
				BigDecimal costamount = null != (BigDecimal)map.get("costamount") ? (BigDecimal)map.get("costamount") : BigDecimal.ZERO;
				
				//退货率=退货数量/发货主数量
				if(sendnum.compareTo(BigDecimal.ZERO) != 0){
					BigDecimal checkreturnrate = checkreturnnum.divide(sendnum, 6, BigDecimal.ROUND_HALF_UP);
					map.put("checkreturnrate",checkreturnrate);
				}
				//销售数量 = 发货单数量 - 直退数量
				map.put("salenum",sendnum.subtract(returnnum));
				//销售箱数
				map.put("saletotalbox",sendtotalbox.subtract(returntotalbox));
				map.put("saleamount",sendamount.subtract(returnamount).add(pushbalanceamount));
				map.put("salenotaxamount",sendnotaxamount.subtract(returnnotaxamount).add(pushbalancenotaxamount));
				BigDecimal saleamount=sendnum.subtract(returnnum);
				if(null != saleamount){
					//毛利额 = 销售金额 - 成本金额
					map.put("salemarginamount",saleamount.subtract(costamount));
					//实际毛利率 = （销售金额 - 成本金额）/销售金额*100
					if(saleamount.compareTo(BigDecimal.ZERO)!=0){
						BigDecimal realrate = saleamount.subtract(costamount).divide(saleamount, 6, BigDecimal.ROUND_HALF_UP);
						realrate = realrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
						if(saleamount.compareTo(BigDecimal.ZERO)==-1){
							map.put("realrate",realrate.negate());
						}else{
							map.put("realrate",realrate);
						}
					}
				}else if(costamount.compareTo(BigDecimal.ZERO)==1){
					map.put("realrate",new BigDecimal(100).negate());
				}
				String salesarea =(String)map.get("id");
				if(!"".equals(salesarea)){
					map.put("salesareaname", getSalesareaByID(salesarea).getName());
				}
				map.put("salesarea", salesarea);
			}
		}
		list.removeAll(removeList);
		List dataList = new ArrayList();
		int index=0;
		int length=list.size();
		List indexList= new ArrayList();
		if(0==dataList.size()){
			for(Map map : list){
				if("".equals((String)map.get("pid"))){
					indexList.add(map);
				}
			}
		}
		dataList =getAreaChildMap(indexList,list);
		PageData pageData = new PageData(dataList.size(),dataList,pageMap);
		return pageData;
	}
	
	private List<Map> getAreaChildMap(List<Map> indexList,List<Map> list) throws Exception{
		List datalist =new ArrayList();
		for(Map indexmap : indexList){
			List<Map> childlist = new ArrayList<Map>();
			for(Map mainmap : list){
				if(mainmap.get("pid").equals(indexmap.get("id"))){
					childlist.add(mainmap);
				}
			}
			if(0==childlist.size()){
				//if(!"".equals((String)indexmap.get("pid"))){
					indexmap.put("iconCls","icon-annex");
				//}
				indexmap.put("rid", CommonUtils.getRandomWithTime());
			}
			else{
				List a =new ArrayList();
				a= getAreaChildMap(childlist,list);
				indexmap.put("children",a);
				indexmap.put("state","closed");
				indexmap.put("rid", CommonUtils.getRandomWithTime());
			}
			datalist.add(indexmap);
				
		}
		return datalist;
	}
}

