/**
 * @(#)StorageReportAction.java
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
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.report.model.*;
import com.hd.agent.report.service.IStorageReportService;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 
 * 库存报表action
 * @author chenwei
 */
public class StorageReportAction extends BaseFilesAction {
	
	private IStorageReportService storageReportService;

	public IStorageReportService getStorageReportService() {
		return storageReportService;
	}
 
	public void setStorageReportService(IStorageReportService storageReportService) {
		this.storageReportService = storageReportService;
	}
	/**
	 * 显示仓库出入库统计页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 31, 2013
	 */
	public String showInOutReportListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 获取仓库出入库统计数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 14, 2013
	 */
	public String showInOutReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = storageReportService.showInOutReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
    /**
     * 显示配送员配送情况表页面
     * @return
     * @throws Exception
     * @author lin_xx
     * @date June 17, 2016
     */
    public String showDriverDeliveryReportPage() throws Exception {
        String today = CommonUtils.getTodayDataStr();
        String firstDay = CommonUtils.getMonthDateStr();
        request.setAttribute("firstday", firstDay);
        request.setAttribute("today", today);
        return SUCCESS;
    }

    /**
     * 显示配送员配送情况表数据
     * @return
     * @throws Exception
     * @author lin_xx
     * @date June 17, 2016
     */
    public String getDriverDeliveryReportData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = storageReportService.getDriverDeliveryReportData(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    public void exportDriverDeliveryReportData() throws Exception{

        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("isflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
        PageData pageData = storageReportService.getDriverDeliveryReportData(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);

    }

	/**
	 * 显示进销存汇总统计页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 21, 2013
	 */
	public String showBuySaleReportPage() throws Exception{
		String yestoday = CommonUtils.getYestodayDateStr();
		request.setAttribute("yestoday", yestoday);
		return "success";
	}
	/**
	 * 获取进销存汇总统计数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 21, 2013
	 */
	public String showBuySaleReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = storageReportService.showBuySaleReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	
	/**
	 * 显示进销存汇总统计页面（分月）
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 19, 2014
	 */
	public String showBuySaleReportMonthPage()throws Exception{
		String today = CommonUtils.getTodayMonStr();
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 获取进销存汇总统计数据（分月）
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 19, 2014
	 */
	public String showBuySaleReportMonthData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = storageReportService.showBuySaleReportMonthData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 显示仓库出入库流水账页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 9, 2013
	 */
	public String showInOutFlowListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_storageinout_flow");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 获取出入库流水账数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 9, 2013
	 */
	public String showInOutFlowListData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = storageReportService.showInOutFlowListData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	
	/**
	 * 显示代配送出入库流水账页面
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date  july 19, 2016
	 */
	public String showDeliveryInOutFlowListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_storageinout_flow");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 获取出代配送入库流水账数据
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date july 19, 2016
	 */
	public String showDeliveryInOutFlowListData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = storageReportService.showDeliveryInOutFlowListData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	
	/**
	 * 导出代配送出入库流水账
	 * @throws Exception
	 * @author wanghongteng 
	 * @date july 19, 2016
	 */
	public void exportDeliveryInOutFlowListData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
		pageMap.setCondition(map);
		String title = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}
		else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		PageData pageData = storageReportService.showDeliveryInOutFlowListData(pageMap);
		ExcelUtils.exportExcel(exportInOutFlowDataFilter(pageData.getList(),pageData.getFooter()), title);
	}
	
	
	/**
	 * 显示盘点报表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 17, 2013
	 */
	public String showCheckReportPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 获取盘点报表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 17, 2013
	 */
	public String showCheckReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = storageReportService.showCheckReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	
	/**
	 * 导出出入库流水账
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportInOutFlowListData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
		pageMap.setCondition(map);
		String title = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}
		else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		PageData pageData = storageReportService.showInOutFlowListData(pageMap);
		ExcelUtils.exportExcel(exportInOutFlowDataFilter(pageData.getList(),pageData.getFooter()), title);
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(出入库流水账)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportInOutFlowDataFilter(List<StorageInOutFlow> list,List<StorageInOutFlow> footerlist) throws Exception{
		Map map2 = getAccessColumn("t_report_storageinout_flow");
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("storagename", "仓库名称");
		firstMap.put("businessdate", "业务日期");
		firstMap.put("billtypename", "单据名称");
		firstMap.put("id", "编号");
		firstMap.put("customerid", "客户编号");
		firstMap.put("customername", "客户名称");
        firstMap.put("supplierid", "供应商编号");
        firstMap.put("suppliername", "供应商名称");
		firstMap.put("goodsid", "商品编号");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("barcode", "条形码");
		firstMap.put("spell", "助记符");
		firstMap.put("brandname", "品牌名称");
		firstMap.put("boxnum", "箱装量");
		firstMap.put("unitname", "主单位");
		firstMap.put("enternum", "入库数量");
		firstMap.put("auxenternumdetail", "入库辅数量");
		firstMap.put("outnum", "出库数量");
		firstMap.put("auxoutnumdetail", "出库辅数量");
		if(map2.containsKey("price")){
			firstMap.put("price", "单价");
		}
		if(map2.containsKey("amount")){
			firstMap.put("amount", "金额");
		}
        if(isSysUserHaveUrl("/report/storage/inOutFlowListStoragePrice.do")){
            firstMap.put("storagePrice", "成本单价");
            firstMap.put("storagePriceAmount", "成本金额");
        }
		firstMap.put("remark", "备注");
		result.add(firstMap);
		
		if(list.size() != 0){
			for(StorageInOutFlow storageInOutFlow : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(storageInOutFlow);
				for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
					if(map.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
						for(Map.Entry<String, Object> entry : map.entrySet()){
							if(fentry.getKey().equals(entry.getKey())){
								objectCastToRetMap(retMap,entry);
							}
						}
					}
					else{
						retMap.put(fentry.getKey(), "");
					}
				}
				result.add(retMap);
			}
		}
		if(footerlist.size() != 0){
			for(StorageInOutFlow storageInOutFlow : footerlist){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(storageInOutFlow);
				for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
					if(map.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
						for(Map.Entry<String, Object> entry : map.entrySet()){
							if(fentry.getKey().equals(entry.getKey())){
								objectCastToRetMap(retMap,entry);
							}
						}
					}
					else{
						retMap.put(fentry.getKey(), "");
					}
				}
				result.add(retMap);
			}
		}
		return result;
	}
	
	/**
	 * 导出仓库出入库情况表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportInOutReportData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("isflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
		PageData pageData = storageReportService.showInOutReportData(pageMap);

        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);
	}

	/**
	 * 导出进销存汇总统计报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportBuySaleReportData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
		pageMap.setCondition(map);
		String title = "",groupcols = "",type = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}
		else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
        if(map.containsKey("groupcols")){
            groupcols = map.get("groupcols").toString();
        }
        if(map.containsKey("type")){
            type = (String)map.get("type");
        }
        PageData pageData = null;
        if(StringUtils.isEmpty(type)){
            pageData = storageReportService.showBuySaleReportData(pageMap);
        }else if("real".equals(type)){
            pageData = storageReportService.getRealBuySaleReportDayData(pageMap);
        }
        pageData.getList().addAll(pageData.getFooter());
		ExcelUtils.exportExcel(exportBuySaleReportDataFilter(pageData.getList(),groupcols), title);
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(进销存汇总统计报表)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportBuySaleReportDataFilter(List<StorageBuySaleReport> list,String groupcols) throws Exception{
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        boolean goodsidflag = false;
        if(groupcols.indexOf("goodsid") != -1){
            goodsidflag = true;
        }
        if(StringUtils.isNotEmpty(groupcols)){
            String[] groupArr = groupcols.split(",");
            for(String col : groupArr){
                if(col.equals("goodsid")){
                    firstMap.put("goodsid", "商品编号");
                    firstMap.put("goodsname", "商品名称");
                    firstMap.put("barcode", "条形码");
                    firstMap.put("brandname", "品牌名称");
                    firstMap.put("unitname", "单位");
                    firstMap.put("price", "单价");
                }else if(col.equals("storageid")){
                    firstMap.put("storagename", "仓库名称");
                }else if(col.equals("deptid")){
                    firstMap.put("deptname", "品牌部门");
                }else if(col.equals("brandid")){
					firstMap.put("brandname", "品牌名称");
				}else if(col.equals("supplierid")){
					firstMap.put("supplierid", "供应商编码");
					firstMap.put("suppliername", "供应商名称");
				}
            }
        }else{
            firstMap.put("goodsid", "商品编号");
            firstMap.put("goodsname", "商品名称");
            firstMap.put("barcode", "条形码");
            firstMap.put("brandname", "品牌名称");
            //firstMap.put("storagename", "仓库名称");
            firstMap.put("unitname", "单位");
            firstMap.put("price", "单价");
        }
        firstMap.put("initnum", "期初数量");
        if(goodsidflag){
            firstMap.put("auxinitnumdetail", "期初箱数");
        }else{
            firstMap.put("inittotalbox", "期初箱数");
        }
        firstMap.put("initamount", "期初含税金额");
        firstMap.put("initnotaxamount", "期初未税金额");

        firstMap.put("buyinnum", "采购进货数量");
        if(goodsidflag){
            firstMap.put("auxbuyinnumdetail", "采购进货箱数");
        }else{
            firstMap.put("buyintotalbox", "采购进货箱数");
        }
        firstMap.put("buyinamount", "采购进货含税金额");
        firstMap.put("buyinnotaxamount", "采购进货未税金额");
        firstMap.put("buyoutnum", "采购退货数量");
        if(goodsidflag){
            firstMap.put("auxbuyoutnumdetail", "采购退货箱数");
        }else{
            firstMap.put("buyouttotalbox", "采购退货箱数");
        }
        firstMap.put("buyoutamount", "采购退货含税金额");
        firstMap.put("buyoutnotaxamount", "采购退货未税金额");

        firstMap.put("saleoutnum", "销售出库数量");
        if(goodsidflag){
            firstMap.put("auxsaleoutnumdetail", "销售出库箱数");
        }else{
            firstMap.put("saleouttotalbox", "销售出库箱数");
        }
        firstMap.put("saleoutamount", "销售出库含税金额");
        firstMap.put("saleoutnotaxamount", "销售出库未税金额");
        firstMap.put("saleinnum", "销售退货数量");
        if(goodsidflag){
            firstMap.put("auxsaleinnumdetail", "销售退货箱数");
        }else{
            firstMap.put("saleintotalbox", "销售退货箱数");
        }
        firstMap.put("saleinamount", "销售退货含税金额");
        firstMap.put("saleinnotaxamount", "销售退货未税金额");
        firstMap.put("costoutamount", "合计成本含税金额");
        firstMap.put("costnotaxoutamount", "合计成本未税金额");

        firstMap.put("allocateinnum", "调拨入库数量");
        if(goodsidflag){
            firstMap.put("auxallocateinnumdetail", "调拨入库箱数");
        }else{
            firstMap.put("allocateintotalbox", "调拨入库箱数");
        }
        firstMap.put("allocateinamount", "调拨入库含税金额");
        firstMap.put("allocateinnotaxamount", "调拨入库未税金额");
        firstMap.put("allocateoutnum", "调拨出库数量");
        if(goodsidflag){
            firstMap.put("auxallocateoutnumdetail", "调拨出库箱数");
        }else{
            firstMap.put("allocateouttotalbox", "调拨出库箱数");
        }
        firstMap.put("allocateoutamount", "调拨出库含税金额");
        firstMap.put("allocateoutnotaxamount", "调拨出库未税金额");

        firstMap.put("lossnum", "损益数量");
        if(goodsidflag){
            firstMap.put("auxlossnumdetail", "损益箱数");
        }else{
            firstMap.put("losstotalbox", "损益箱数");
        }
        firstMap.put("lossamount", "损益含税金额");
        firstMap.put("lossnotaxamount", "损益未税金额");
        firstMap.put("costlossamount", "损益成本含税金额");
        firstMap.put("costnotaxlossamount", "损益成本未税金额");

        firstMap.put("endnum", "期末数量");
        if(goodsidflag){
            firstMap.put("auxendnumdetail", "期末箱数");
        }else{
            firstMap.put("endtotalbox", "期末箱数");
        }
        firstMap.put("endamount", "期末含税金额");
        firstMap.put("endnotaxamount", "期末未税金额");
        result.add(firstMap);
		
		if(list.size() != 0){
			for(StorageBuySaleReport storageBuySaleReport : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(storageBuySaleReport);
				for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
					if(map.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
						for(Map.Entry<String, Object> entry : map.entrySet()){
							if(fentry.getKey().equals(entry.getKey())){
								objectCastToRetMap(retMap,entry);
							}
						}
					}
					else{
						retMap.put(fentry.getKey(), "");
					}
				}
				result.add(retMap);
			}
		}
		return result;
	}
	
	/**
	 * 导出分月进销存汇总统计报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 20, 2014
	 */
	public void exportBuySaleReportMonthData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
		pageMap.setCondition(map);
		String title = "",groupcols = "",type = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}
		else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		if(map.containsKey("groupcols")){
			groupcols = map.get("groupcols").toString();
		}
        if(map.containsKey("type")){
            type = (String)map.get("type");
        }
        PageData pageData = null;
        if(StringUtils.isEmpty(type)){
            pageData = storageReportService.showBuySaleReportMonthData(pageMap);
        }else if("real".equals(type)){
            pageData = storageReportService.getRealBuySaleReportMonthData(pageMap);
        }
        pageData.getList().addAll(pageData.getFooter());
		ExcelUtils.exportExcel(exportBuySaleReportDataFilter(pageData.getList(),groupcols), title);
	}
	
	/**
	 * 导出盘点报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportCheckReportData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("isflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
		PageData pageData = storageReportService.showCheckReportData(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map, list);
    }

	/**
	 * 库存周转天数报表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 28, 2013
	 */
	public String showStorageRevolutionDaysPage() throws Exception{
		int year = CommonUtils.getPreMonthYear();
		int mon = CommonUtils.getPreMonth();
		request.setAttribute("year", year);
		request.setAttribute("mon", mon);
		return "success";
	}

    /**
     *
     * @return
     * @throws Exception
     */
    public String showStorageRevolutionDaysBySection() throws Exception{
        String preMonthLastDay = CommonUtils.getPreMonthLastDay();
        request.setAttribute("preMonthLastDay",preMonthLastDay);
        return SUCCESS;
    }

	/**
	 * 库存周转天数统计报表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 28, 2013
	 */
	public String showStorageRevolutionDaysData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = storageReportService.showStorageRevolutionDaysData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
    /**
     * 库存周转天数按具体日期查询统计报表数据
     * @return
     * @throws Exception
     * @author chenwei
     * @date Nov 28, 2013
     */
    public String showStorageRevolutionDaysBySectionData() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = storageReportService.showStorageRevolutionDaysBySection(pageMap);
        addJSONObjectWithFooter(pageData);
        return "success";
    }

    /**
     * 库存周转天数报表销售金额流水单
     * @return
     * @throws Exception
     */
    public String showSalesamountDetail() throws Exception{
        request.setAttribute("begindate",request.getParameter("begindate"));
        request.setAttribute("enddate",request.getParameter("enddate"));
        request.setAttribute("col",request.getParameter("col"));
        request.setAttribute("value",request.getParameter("value"));
        return SUCCESS;
    }

    /**
     * 获取库存周转天数报表销售金额流水
     * @return
     * @throws Exception
     * @author lin_xx
     * @date June 14, 2016
     */
    public String salesAmountDetailData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = storageReportService.showStorageRevolutionDetails(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

    public String exportRevolutionDaysBySection() throws Exception {

        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("isflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
        PageData pageData = storageReportService.showStorageRevolutionDaysBySection(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);
        return SUCCESS;
    }

	/**
	 * 导出库存周转天数统计报表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 28, 2013
	 */
	public void exportRevolutionDaysData() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
		pageMap.setCondition(map);
		String title = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}
		else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		PageData pageData = storageReportService.showStorageRevolutionDaysData(pageMap);
		pageData.getList().addAll(pageData.getFooter());
		ExcelUtils.exportExcel(exportRevolutionDaysDataFilter(pageData.getList(),map), title);
	}
	/**
	 * 库存周转天数报表数据转换
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 28, 2013
	 */
	private List<Map<String, Object>> exportRevolutionDaysDataFilter(List<Map<String, Object>> list,Map querymap) throws Exception{
		String groupcols = (String) querymap.get("groupcols");
		if(null==groupcols || "".equals(groupcols)){
			groupcols = "goodsid";
		}
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("year", "年度");
		firstMap.put("mon", "月份");
		if(groupcols.indexOf("goodsid")!=-1){
			firstMap.put("goodsid", "商品编码");
			firstMap.put("goodsname", "商品名称");
			firstMap.put("barcode", "条形码");
			firstMap.put("brandname", "品牌名称");
		}
		if(groupcols.indexOf("brandid")!=-1){
			firstMap.put("brandname", "品牌名称");
			firstMap.put("branddeptname", "品牌部门");
			firstMap.put("supplierid", "供应商编号");
			firstMap.put("suppliername", "供应商名称");
		}
		if(groupcols.indexOf("branddept")!=-1){
			firstMap.put("branddeptname", "品牌部门");
			firstMap.put("supplierid", "供应商编号");
			firstMap.put("suppliername", "供应商名称");
		}
		if(groupcols.indexOf("supplierid")!=-1){
			firstMap.put("supplierid", "供应商编号");
			firstMap.put("suppliername", "供应商名称");
		}
		firstMap.put("storageunitnum", "库存平均数量");
		firstMap.put("storageamount", "库存平均金额");
		firstMap.put("salesamount", "销售金额");
		firstMap.put("days", "周转天数");
		result.add(firstMap);
		if(list.size() != 0){
			for(Map<String, Object> dataobject : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = dataobject;
				for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
					if(map.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
						for(Map.Entry<String, Object> entry : map.entrySet()){
							if(fentry.getKey().equals(entry.getKey())){
								objectCastToRetMap(retMap,entry);
							}
						}
					}
					else{
						retMap.put(fentry.getKey(), "");
					}
				}
				result.add(retMap);
			}
		}
		return result;
	}
	/**
	 * 显示库存平均金额统计页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 29, 2013
	 */
	public String showStorageAvgAmountListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 获取库存平均金额统计数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 29, 2013
	 */
	public String showStorageAvgAmountData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = storageReportService.showStorageAvgAmountData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 导出库存平均金额统计报表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 29, 2013
	 */
	public String exportStorageAvgAmountData() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
		pageMap.setCondition(map);
		String title = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}
		else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		PageData pageData = storageReportService.showStorageAvgAmountData(pageMap);
		pageData.getList().addAll(pageData.getFooter());
		ExcelUtils.exportExcel(exportStorageAvgAmountDataFilter(pageData.getList(),map), title);
		return "success";
	}
	/**
	 * 库存平均金额数据转换
	 * @param list
	 * @param querymap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 29, 2013
	 */
	private List<Map<String, Object>> exportStorageAvgAmountDataFilter(List<Map<String, Object>> list,Map querymap) throws Exception{
		String groupcols = (String) querymap.get("groupcols");
		if(null==groupcols || "".equals(groupcols)){
			groupcols = "goodsid";
		}
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		if(groupcols.indexOf("goodsid")!=-1){
			firstMap.put("goodsid", "商品编码");
			firstMap.put("goodsname", "商品名称");
			firstMap.put("barcode", "条形码");
            firstMap.put("existingnum", "数量");
            firstMap.put("auxnumdetail", "箱数");
			firstMap.put("brandname", "品牌名称");
		}
		if(groupcols.indexOf("brandid")!=-1){
			firstMap.put("brandname", "品牌名称");
			firstMap.put("branddeptname", "品牌部门");
			firstMap.put("suppliername", "供应商名称");
		}
		if(groupcols.indexOf("branddept")!=-1){
			firstMap.put("branddeptname", "品牌部门");
			firstMap.put("suppliername", "供应商名称");
		}
		if(groupcols.indexOf("supplierid")!=-1){
			firstMap.put("suppliername", "供应商名称");
		}
		firstMap.put("storageamount", "实际库存平均金额");
        firstMap.put("checkstorageamount", "库存平均金额");
		result.add(firstMap);
		if(list.size() != 0){
			for(Map<String, Object> dataobject : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = dataobject;
				for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
					if(map.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
						for(Map.Entry<String, Object> entry : map.entrySet()){
							if(fentry.getKey().equals(entry.getKey())){
								objectCastToRetMap(retMap,entry);
							}
						}
					}
					else{
						retMap.put(fentry.getKey(), "");
					}
				}
				result.add(retMap);
			}
		}
		return result;
	}
	/**
	 * 显示资金占用金额报表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 3, 2013
	 */
	public String showCapitalOccupyListPage() throws Exception{
		return "success";
	}
	/**
	 * 获取资金占用金额数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 3, 2013
	 */
	public String showCapitalOccupyListData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		List list = storageReportService.showCapitalOccupyListData(pageMap);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 导出资金占用金额报表
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 3, 2013
	 */
	public void exportCapitalOccupyListData() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
		pageMap.setCondition(map);
		String title = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}
		else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		List list = storageReportService.showCapitalOccupyListData(pageMap);
		ExcelUtils.exportExcel(exportCapitalOccupyDataFilter(list), title);
	}
	/**
	 * 资金占用报表excel格式
	 * @param list
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 3, 2013
	 */
	private List<Map<String, Object>> exportCapitalOccupyDataFilter(List<CapitalOccupyReport> list) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("supplierid", "供应商编号");
		firstMap.put("suppliername", "供应商名称");
		firstMap.put("branddeptname", "所属部门");
		firstMap.put("prepayamount", "预付金额");
		firstMap.put("storageamount", "库存金额");
		firstMap.put("receivableamount", "应收金额");
		firstMap.put("advanceamount", "代垫金额");
		firstMap.put("payableamount", "应付金额");
		firstMap.put("totalamount", "合计金额");
		result.add(firstMap);
		if(list.size() != 0){
			for(CapitalOccupyReport capitalOccupyReport : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(capitalOccupyReport);
				for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
					if(map.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
						for(Map.Entry<String, Object> entry : map.entrySet()){
							if(fentry.getKey().equals(entry.getKey())){
								objectCastToRetMap(retMap,entry);
							}
						}
					}
					else{
						retMap.put(fentry.getKey(), "");
					}
				}
				result.add(retMap);
			}
		}
		return result;
	}
	/**
	 * 显示资金占用平均金额报表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 3, 2013
	 */
	public String showCapitalOccupyAvgListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 获取资金占用平均金额数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 3, 2013
	 */
	public String showCapitalOccupyAvgListData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = storageReportService.showCapitalOccupyAvgListData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 导出资金占用金额报表
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 3, 2013
	 */
	public void exportCapitalOccupyAvgListData() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
		pageMap.setCondition(map);
		String title = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}
		else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		PageData pageData = storageReportService.showCapitalOccupyAvgListData(pageMap);
		pageData.getList().addAll(pageData.getFooter());
		ExcelUtils.exportExcel(exportCapitalOccupyDataFilter(pageData.getList()), title);
	}
	/**
	 * 显示库存统计表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 6, 2013
	 */
	public String showStorageAmountReportListPage() throws Exception{
		
		return "success";
	}
	/**
	 * 获取库存统计数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 6, 2013
	 */
	public String showStorageAmountReportListData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = storageReportService.showStorageAmountReportListData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 导出库存统计表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 6, 2013
	 */
	public void exportStorageAmountReportListData() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("isflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
		PageData pageData = storageReportService.showStorageAmountReportListData(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);
    }



	/**
	 * 显示仓库盈亏报表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 21, 2014
	 */
	public String showAdjustmentReportPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 获取仓库盈亏报表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 21, 2014
	 */
	public String getAdjustmentReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = storageReportService.getAdjustmentReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 导出仓库盈亏报表
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 21, 2014
	 */
	public void exportAdjustmentReportData() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("isflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
		PageData pageData = storageReportService.getAdjustmentReportData(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);
	}

	/**
	 * 显示库存树状报表
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2018-2-5
	 */
	public String showStorageTreeReportListPage() throws Exception{
		// 购销类型
		List bstypeList = super.getBaseSysCodeService().showSysCodeListByType("bstype");
		request.setAttribute("bstypeList", JSONUtils.listToJsonStr(bstypeList));
		return "success";
	}

	/**
	 * 获取库存树状报表数据
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2018-2-5
	 */
	public String showStorageTreeReportListData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = storageReportService.showStorageTreeReportListData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}

	/**
	 * 获取库存树状报表数据
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2018-2-5
	 */
	public String showStorageTreeReportListDataByVue() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = storageReportService.showStorageTreeReportListData(pageMap);
		Map resultMap = new HashMap();
		resultMap.put("list",pageData.getList());
		addJSONObject(resultMap);
		return "success";
	}


	/**
	 * 导出库存树状报表数据
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2018-2-12
	 */
	public void exportStorageTreeReportListDataByVue()throws Exception{
		String exgoods=request.getParameter("exportgoods");
		boolean  exportgoods=false;
		if("true".equals(exgoods)){
			exportgoods = true;
		}else{
			exportgoods=false;
		}
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("exportgoods",exgoods);
		pageMap.setCondition(map);
		PageData pageData = storageReportService.showStorageTreeReportListData(pageMap);
		List list = pageData.getList();
		List<Map> exportList = new ArrayList<Map>();
		List dataList = new ArrayList();
		int index = 0;
		dataList = getStorageTreeReportExportListData(exportList,list,index);

		String columns = request.getParameter("columns");
		List<Map> columnList = JSONUtils.jsonStrToList(columns, new HashMap());
		String commonCol = "";
		String colName = "";

		for(Map column : columnList){
			boolean flag = (Boolean)column.get("isexport");
			if(flag||exportgoods){
				if(StringUtils.isEmpty(commonCol)){
					commonCol=(String)column.get("field");
				}else{
					commonCol += "," + (String)column.get("field");
				}
				if(StringUtils.isEmpty(colName)){
					colName=(String)column.get("title");
				}else{
					colName += "," + (String)column.get("title");
				}
			}
		}
		map.put("commonCol",commonCol);
		map.put("colName",colName);
		map.put("fileType","xls");
		map.put("exportname","库存树状报表");
		ExcelUtils.exportAnalysExcel(map,dataList);
	}

	public List<Map> getStorageTreeReportExportListData(List<Map> exportList,List<Map> list,int index) throws  Exception{
		for(Map map : list){
			String name = (String)map.get("name");
			for(int i = 0;i<index;i++){
				name = "      " + name;
			}
			map.put("name",name);
			exportList.add(map);
			if(map.containsKey("children")){
				getStorageTreeReportExportListData(exportList,(List<Map>)map.get("children"),index+1);
			}
		}
		return exportList;
	}

	/**
     * 显示客户装车次数统计报表页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-20
     */
    public String showCustomerCarNumReportPage()throws Exception{
        return SUCCESS;
    }

    /**
     * 获取客户装车次数统计报表数据
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-20
     */
    public String getCustomerCarNumReportData()throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = storageReportService.getCustomerCarNumReportData(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

    /**
     * 导出客户装车次数统计报表数据
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-20
     */
    public void exportCustomerCarNumReportData()throws Exception{
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("isflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
        PageData pageData = storageReportService.getCustomerCarNumReportData(pageMap);

        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);
    }
    /**
     * 显示仓库收发存报表页面
     * @return
     * @throws Exception
     * @author chenwei 
     * @date 2015年8月18日
     */
    public String showStorageRecSendListPage() throws Exception{
    	String today = CommonUtils.getTodayDataStr();
		request.setAttribute("today", today);
    	return "success";
    }
    /**
     * 获取仓库收发存报表数据
     * @return
     * @throws Exception
     * @author chenwei 
     * @date 2015年8月18日
     */
    public String showStorageRecSendList() throws Exception{
    	Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = storageReportService.showStorageRecSendList(pageMap);
		addJSONObjectWithFooter(pageData);
    	return "success";
    }
    /**
	 * 显示指定仓库的商品收发存明细
	 * @return
	 * @throws Exception
	 * @author wanghongteng	 
	 * @date 11 9, 2015
	 */
	public String showStorageRecSendReportDetailPage() throws Exception{
		String storageid= request.getParameter("storageid");
		String goodsid = request.getParameter("goodsid");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		request.setAttribute("storageid", storageid);
		request.setAttribute("goodsid", goodsid);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return "success";
	}
	/**
	 * 显示入库明细
	 * @return
	 * @throws Exception
	 * @author wanghongteng	 
	 * @date 11 9, 2015
	 */
	public String showReceivenumDetailPage() throws Exception{
		String storageid= request.getParameter("storageid");
		String goodsid = request.getParameter("goodsid");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		request.setAttribute("storageid", storageid);
		request.setAttribute("goodsid", goodsid);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return "success";
	}
	/**
	 * 显示出库明细
	 * @return
	 * @throws Exception
	 * @author wanghongteng	 
	 * @date 11 9, 2015
	 */
	public String showSendnumDetailPage() throws Exception{
		String storageid= request.getParameter("storageid");
		String goodsid = request.getParameter("goodsid");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		request.setAttribute("storageid", storageid);
		request.setAttribute("goodsid", goodsid);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return "success";
	}
	
	/**
	 * 获取收货明细列表数据
	 * @return
	 * @throws Exception
	 * @author wanghongteng	 
	 * @date 11 9, 2015
	 */
	public String showReceivenumDetailList() throws Exception{
		String storageid= request.getParameter("storageid");
		String goodsid = request.getParameter("goodsid");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
        if(StringUtils.isEmpty(storageid) || "null".equals(storageid)){
            storageid = null;
        }
        if(StringUtils.isEmpty(goodsid) || "null".equals(goodsid)){
        	goodsid = null;
        }
        if(StringUtils.isEmpty(businessdate1) || "null".equals(businessdate1)){
        	businessdate1 = null;
        }
        if(StringUtils.isEmpty(businessdate2) || "null".equals(businessdate2)){
        	businessdate2 = null;
        }
		List list = storageReportService.showReceivenumDetailList(goodsid,storageid,businessdate1,businessdate2);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 获取发货明细列表数据
	 * @return
	 * @throws Exception
	 * @author wanghongteng	 
	 * @date 11 9, 2015
	 */
	public String showSendnumDetailPageList() throws Exception{
		String storageid= request.getParameter("storageid");
		String goodsid = request.getParameter("goodsid");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
        if(StringUtils.isEmpty(storageid) || "null".equals(storageid)){
            storageid = null;
        }
        if(StringUtils.isEmpty(goodsid) || "null".equals(goodsid)){
        	goodsid = null;
        }
        if(StringUtils.isEmpty(businessdate1) || "null".equals(businessdate1)){
        	businessdate1 = null;
        }
        if(StringUtils.isEmpty(businessdate2) || "null".equals(businessdate2)){
        	businessdate2 = null;
        }
        List list = storageReportService.showSendnumDetailPageList(goodsid,storageid,businessdate1,businessdate2);
		addJSONArray(list);
		return "success";
	}
	
	
	/**
	 * 获取指定仓库的商品收发存明细
	 * @return
	 * @throws Exception
	 * @author wanghongteng	 
	 * @date 11 9, 2015
	 */
	public String showStorageRecSendReportDetailList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = storageReportService.showStorageRecSendReportDetailList(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
    /**
     * 导出仓库收发存报表
     * @throws Exception
     * @author chenwei 
     * @date 2015年8月19日
     */
    public void exportStorageRecSendReportData() throws Exception{
    	Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("isflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);

        PageData pageData = storageReportService.showStorageRecSendList(pageMap);

        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);

    }
    /**
     * 导出仓库收发存报表明细
     * @throws Exception
     * @author chenwei 
     * @date 2015年8月19日
     */
    public void exportStorageRecSendReportDetailData() throws Exception{
    	Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isflag", "true");
        pageMap.setCondition(map);
        String title = "";
        if(map.containsKey("excelTitle")){
            title = map.get("excelTitle").toString();
        }
        else{
            title = "list";
        }
        if(StringUtils.isEmpty(title)){
            title = "list";
        }
        PageData pageData = storageReportService.showStorageRecSendReportDetailList(pageMap);
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("storagename", "仓库名称");
        firstMap.put("goodsid", "商品编码");
        firstMap.put("goodsname", "商品名称");
        firstMap.put("billmodelname", "业务单据类型");
        firstMap.put("barcode", "条形码");
        firstMap.put("brandname", "品牌名称");
        firstMap.put("goodssortname", "品牌名称");
        firstMap.put("boxnum", "箱装量");
        firstMap.put("unitname", "单位");
        firstMap.put("beginnum", "期初数量");
        firstMap.put("auxbeginnum", "期初箱数");
        firstMap.put("beginnumdetail", "期初辅数量");
        firstMap.put("beginamount", "期初金额");
        firstMap.put("receivenum", "入库数量");
        firstMap.put("auxreceivenum", "入库箱数");
        firstMap.put("receivenumdetail", "入库辅数量");
        firstMap.put("receiveamount", "入库金额");
        firstMap.put("sendnum", "出库数量");
        firstMap.put("auxsendnum", "出库箱数");
        firstMap.put("sendnumdetail", "出库辅数量");
        firstMap.put("sendamount", "出库金额");
        firstMap.put("endnum", "期末数量");
        firstMap.put("auxendnum", "期末箱数");
        firstMap.put("endnumdetail", "期末辅数量");
        firstMap.put("endamount", "期末金额");
        firstMap.put("addtime", "添加时间");
        result.add(firstMap);
        List<Map<String,Object>> list = pageData.getList();
        for(Map<String, Object> map2 : list){
            Map<String, Object> retMap = new LinkedHashMap<String, Object>();
            for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
                String key = fentry.getKey();
                if(map2.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
                    for(Map.Entry<String, Object> entry : map2.entrySet()){
                        if(fentry.getKey().equals(entry.getKey())){
                            objectCastToRetMap(retMap,entry);
                        }
                    }
                }
                else{
                    retMap.put(fentry.getKey(), "");
                }
            }
            result.add(retMap);
        }
        ExcelUtils.exportExcel(result, title);
    }

    /**
     * 显示实际进销存汇总统计页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-11-17
     */
    public String showRealBuySaleReportDayPage() throws Exception{
        String yestoday = CommonUtils.getYestodayDateStr();
        request.setAttribute("yestoday", yestoday);
        return "success";
    }
    /**
     * 获取实际进销存汇总统计数据
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-11-17
     */
    public String showRealBuySaleReportDayData() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = storageReportService.getRealBuySaleReportDayData(pageMap);
        addJSONObjectWithFooter(pageData);
        return "success";
    }

    /**
     * 显示每月实际进销存汇总统计页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-11-17
     */
    public String showRealBuySaleReportMonthPage()throws Exception{
        String today = CommonUtils.getTodayMonStr();
        request.setAttribute("today", today);
        return SUCCESS;
    }

    /**
     * 获取每月实际进销存汇总统计数据
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-11-17
     */
    public String showRealBuySaleReportMonthData()throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = storageReportService.getRealBuySaleReportMonthData(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }
    
    /**
     * 显示发货人报表页面
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016-4-7
     */
    public String showStorageDispatchUserReportPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
        return SUCCESS;
    }
    
    /**
	 * 获取发货人列表
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 2016-4-7
	 */
	public String showDispatchUserListData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = storageReportService.showDispatchUserListData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	
	/**
	 * 导出发货人报表
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 2016-4-8
	 */
	public void exportDispatchUserReportData() throws Exception{
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
		queryMap.put("isflag", "true");
		queryMap.put("isAll","isAll");
		pageMap.setCondition(queryMap);
		PageData pageData = storageReportService.showDispatchUserListData(pageMap);
		List<DispatchUserReport> list = pageData.getList();
		if(null != pageData.getFooter()){
			List<DispatchUserReport> footer = pageData.getFooter();
			list.addAll(footer);
		}
		ExcelUtils.exportAnalysExcel(map,list);
	}

    /**
     * 显示司机退货原始明细表页面
     * @return
     * @throws Exception
     * @author lin_xx
     * @date Aug 8, 2016
     */
    public String showDriverRejectEnterReportPage() throws Exception {
        String today = CommonUtils.getTodayDataStr();
        String firstDay = CommonUtils.getMonthDateStr();
        request.setAttribute("firstday", firstDay);
        request.setAttribute("today", today);
        return SUCCESS;
    }
    /**
     * 显示司机退货原始明细表数据
     * @return
     * @throws Exception
     * @author lin_xx
     * @date Aug 8, 2016
     */
    public String showDriverRejectEnterData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = storageReportService.showDriverRejectEnterData(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }
    /**
     * 导出司机退货原始明细表
     * @return
     * @throws Exception
     * @author lin_xx
     * @date Aug 8, 2016
     */
    public void exportDriverRejectEnterReportPage() throws Exception {

        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("isflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
        PageData pageData = storageReportService.showDriverRejectEnterData(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);

    }

    /**
     * 显示业务员发货报表页面
     * @return
     * @throws Exception
     * @author lin_xx
     * @date Jul 8, 2016
     */
    public String showSalesmanDeliveryPage() throws Exception{
        String today = CommonUtils.getTodayDataStr();
        String firstDay = CommonUtils.getMonthDateStr();
        request.setAttribute("firstday", firstDay);
        request.setAttribute("today", today);

        return SUCCESS;
    }
    /**
     * 显示业务员发货报表数据
     * @return
     * @throws Exception
     * @author lin_xx
     * @date Jul 8, 2016
     */
    public String showSalesmanDeliveryData() throws  Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = storageReportService.showSalesmanDeliveryData(pageMap);
        addJSONObjectWithFooter(pageData);

        return SUCCESS;
    }
    /**
     * 导出业务员发货报表数据
     * @return
     * @throws Exception
     * @author lin_xx
     * @date Jul 8, 2016
     */
    public String exportsalesmanDeliveryReportPage() throws Exception {

        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("isflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
        PageData pageData = storageReportService.showSalesmanDeliveryData(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);

        return SUCCESS ;
    }

    /**
     *显示库存商品盘点记录报表
     * @return
     * @throws Exception
     */
    public String storageGoodsCheckLogPage() throws Exception{
        return "success";
    }

    /**
     * 获取库存商品盘点记录数据
     * @return
     * @throws Exception
     */
    public String showStorageGoodsCheckLogData() throws Exception{
        //获取页面传过来的参数 封装到map里面
        Map map = CommonUtils.changeMap(request.getParameterMap());
        //map赋值到pageMap中作为查询条件
        pageMap.setCondition(map);
        PageData pageData = storageReportService.getStorageGoodsCheckLogData(pageMap);
        addJSONObjectWithFooter(pageData);
        return "success";
    }

    /**
     * 导出库存盘点记录
     * @throws Exception
     */
    public void expotStorageGoodsCheckLogData() throws Exception{
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
        queryMap.put("isflag", "true");
        pageMap.setCondition(queryMap);
        PageData pageData = storageReportService.getStorageGoodsCheckLogData(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);
    }
}

