/**
 * @(#)ExceptionReportAction.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Nov 22, 2013 zhengziyong 创建版本
 */
package com.hd.agent.report.action;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.report.model.GoodsOut;
import com.hd.agent.report.service.IExceptionReportService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 
 * 
 * @author zhengziyong
 */
public class ExceptionReportAction extends BaseFilesAction {

	private IExceptionReportService exceptionReportService;
	
	public IExceptionReportService getExceptionReportService() {
		return exceptionReportService;
	}

	public void setExceptionReportService(
			IExceptionReportService exceptionReportService) {
		this.exceptionReportService = exceptionReportService;
	}

	/**
	 * 缺货商品统计页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Nov 23, 2013
	 */
	public String goodsOutReportPage() throws Exception{
		String firstday = CommonUtils.getMonthDateStr();
		request.setAttribute("date",firstday);
		return SUCCESS;
	}
	
	/**
	 * 获取缺货商品统计数据
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Nov 23, 2013
	 */
	public String getGoodsOutReport() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		addJSONObjectWithFooter(exceptionReportService.getGoodsOutReport(pageMap));
		return SUCCESS;
	}
	
	/**
	 * 导出缺货商品数据
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Nov 23, 2013
	 */
	public void exportGoodsOutReport() throws Exception{
		String groupcols = null;
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
		if(map.containsKey("groupcols") && null != map.get("groupcols")){
			groupcols = (String)map.get("groupcols");
		}
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
		PageData pageData = exceptionReportService.getGoodsOutReport(pageMap);
		ExcelUtils.exportExcel(exportGoodsOutReportFilter(pageData.getList(),pageData.getFooter()), title);
	}
	
	private List<Map<String, Object>> exportGoodsOutReportFilter(List<GoodsOut> list,List<GoodsOut> footerlist) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("orderid", "订单编号");
		firstMap.put("sourceid", "客户单号");
		firstMap.put("businessdate", "订单日期");
		firstMap.put("customername", "客户名称");
		firstMap.put("goodsid", "商品编码");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("barcode", "条形码");
		firstMap.put("fixnum", "订购数量");
        firstMap.put("fixauxnumdetail", "订购辅数量");
        firstMap.put("fixamount", "订购金额");
		firstMap.put("sendnum", "已发数量");
        firstMap.put("sendauxnumdetail", "已发辅数量");
        firstMap.put("sendamount", "已发金额");
		firstMap.put("outnum", "缺货数量");
        firstMap.put("outauxnumdetail", "缺货辅数量");
        firstMap.put("outamount", "缺货金额");
		firstMap.put("salesusername", "业务员");
		result.add(firstMap);
		if(list.size() != 0){
			for(GoodsOut goodsOut : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(goodsOut);
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
			for(GoodsOut goodsOut : footerlist){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(goodsOut);
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
	 * 多日未销售商品统计页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Nov 23, 2013
	 */
	public String goodsNotSalesReportPage() throws Exception{
		return SUCCESS;
	}
	
	public String getGoodsNotSalesReport() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		addJSONObject(exceptionReportService.getGoodsNotSalesReport(pageMap));
		return SUCCESS;
	}

    /**
     * 多日未销售商品库存明细
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016-6-2
     */
    public String showGoodsNotSalesInStoragePage() throws Exception {
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        List list= exceptionReportService.getGoodsNotSalesInStorage(pageMap);
        String detaillist =  JSONUtils.listToJsonStr(list);
        request.setAttribute("detaillist",detaillist);
        return SUCCESS;
    }

	public void exportGoodsNotSalesReport() throws Exception{
		String groupcols = null;
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
		if(map.containsKey("groupcols") && null != map.get("groupcols")){
			groupcols = (String)map.get("groupcols");
		}
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
		PageData pageData = exceptionReportService.getGoodsNotSalesReport(pageMap);
		ExcelUtils.exportExcel(exportGoodsNotSalesReportFilter(pageData.getList()), title);
	}
	
	private List<Map<String, Object>> exportGoodsNotSalesReportFilter(List<GoodsInfo> list) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("id", "商品编号");
		firstMap.put("name", "商品名称");
		firstMap.put("barcode", "条形码");
		firstMap.put("brandName", "商品品牌");
        firstMap.put("field02", "数量");
        firstMap.put("field03", "成本金额");
		//firstMap.put("defaultsalerName", "默认业务员");
		firstMap.put("newsaledate", "最新销售日期");
		firstMap.put("field01", "未发生交易天数");
		result.add(firstMap);
		if(list.size() != 0){
			for(GoodsInfo m : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(m);
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
	
	public String customerNotStockReportPage() throws Exception{		
		return SUCCESS;
	}
	
	public String getCustomerNotStockReport() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		addJSONObject(exceptionReportService.getCustomerNotStockReport(pageMap));
		return SUCCESS;
	}
	
	public void exportCustomerNotStockReport() throws Exception{
		String groupcols = null;
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
		if(map.containsKey("groupcols") && null != map.get("groupcols")){
			groupcols = (String)map.get("groupcols");
		}
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
		PageData pageData = exceptionReportService.getCustomerNotStockReport(pageMap);
		ExcelUtils.exportExcel(exportCustomerNotStockReportFielter(pageData.getList()), title);
	}
	
	private List<Map<String, Object>> exportCustomerNotStockReportFielter(List<Customer> list) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("id", "客户编号");
		firstMap.put("name", "客户名称");
		firstMap.put("salesusername", "默认业务员");
		firstMap.put("newsalesdate", "最新销售日期");
		firstMap.put("field01", "未发生交易天数");
		result.add(firstMap);
		if(list.size() != 0){
			for(Customer m : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(m);
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
	 * 销售发货出库差额统计报表（与发货通知单比较）
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 1, 2014
	 */
	public String showSalesOutBalanceReportPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 销售发货出库差额统计报表（与发货通知单比较）数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 1, 2014
	 */
	public String showSalesOutBalanceReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = exceptionReportService.showSalesOutBalanceReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 导出销售发货出库差额统计报表数据
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 3, 2014
	 */
	public void exportSalesOutBalanceReportData() throws Exception{
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
		PageData pageData = exceptionReportService.showSalesOutBalanceReportData(pageMap);
		ExcelUtils.exportExcel(exportSalesOutBalanceReportDataFielter(pageData.getList(),pageData.getFooter()), title);
	}
	/**
	 * 导出销售发货出库差额统计报表数据 excel格式
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 3, 2014
	 */
	private List<Map<String, Object>> exportSalesOutBalanceReportDataFielter(List<Map> list,List<Map> footer) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("id", "发货通知单编号");
		firstMap.put("billno", "销售订单编号");
		firstMap.put("businessdate", "业务日期");
		firstMap.put("customerid", "客户编号");
		firstMap.put("customername", "客户名称");
		firstMap.put("goodsid", "商品编码");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("branddeptname", "品牌部门");
		firstMap.put("brandusername", "品牌业务员");
		firstMap.put("taxprice", "单价");
		firstMap.put("costprice", "成本价");
		firstMap.put("unitnum", "发货数量");
		firstMap.put("taxamount", "发货金额");
		firstMap.put("outnummain", "实际出库数量");
		firstMap.put("outamounttax", "实际出库金额");
		firstMap.put("balancenum", "数量差");
		firstMap.put("balanceamount", "差额");
		result.add(firstMap);
		if(null!=list && list.size() != 0){
			for(Map<String, Object> map : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
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
		if(null!=footer && footer.size() != 0){
			for(Map<String, Object> map : footer){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
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
     * 显示多日未验收单据单据报表页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-04-23
     */
    public String showBillUnAuditReportPage()throws Exception{
        return SUCCESS;
    }

    /**
     * 获取多日未验收单据报表数据
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-04-23
     */
    public String getBillUnAuditReportList()throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = exceptionReportService.getBillUnAuditReportList(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

    /**
     * 导出多日未验收单据报表
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-04-23
     */
    public void exportBillUnAuditReportList()throws Exception{
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
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("id", "编号");
        firstMap.put("saleorderid", "订单编号");
        firstMap.put("businessdate", "业务日期");
        firstMap.put("customerid", "客户编号");
        firstMap.put("customername", "客户名称");
        firstMap.put("pcustomername", "总店");
        firstMap.put("handlername", "对方经手人");
        firstMap.put("salesdeptname", "销售部门");
        firstMap.put("salesusername", "客户业务员");
        firstMap.put("amount", "金额");
        firstMap.put("duefromdate", "应收日期");
        firstMap.put("canceldate", "核销日期");
        firstMap.put("statusname", "状态");
        firstMap.put("isinvoicename", "发票状态");
        firstMap.put("billbactypename", "退货类型");
        firstMap.put("indoorusername", "销售内勤");
        firstMap.put("addusername", "制单人");
        firstMap.put("remark", "备注");
        result.add(firstMap);
        PageData pageData = exceptionReportService.getBillUnAuditReportList(pageMap);
        if(null!=pageData.getList() && pageData.getList().size() != 0){
            for(Map<String, Object> map2 : new ArrayList<Map<String, Object>>(pageData.getList())){
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
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
        }
        ExcelUtils.exportExcel(result, title);
    }

    /**
     * 显示销售价格异常报表页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-12-07
     */
    public String showSalepriceReportPage()throws Exception{
        String today = CommonUtils.getTodayDataStr();
        request.setAttribute("today", today);
        return SUCCESS;
    }

    /**
     * 获取销售价格异常报表数据
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-12-07
     */
    public String getSalePriceReportList()throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = exceptionReportService.getSalePriceReportList(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 导出销售价格异常报表数据
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-12-07
     */
    public void exportSalePriceReportList()throws Exception{
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
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("saleorderid", "销售订单号");
        firstMap.put("receiptid", "回单编号");
        firstMap.put("businessdate", "业务日期");
		firstMap.put("customerid", "客户编码");
		firstMap.put("customername", "客户名称");
		firstMap.put("goodsid", "商品编码");
		firstMap.put("goodsname", "商品名称");
        firstMap.put("barcode", "条形码");
        firstMap.put("brandname", "商品品牌");
        firstMap.put("unitname", "单位");
        firstMap.put("taxprice", "交易价格");
        firstMap.put("unitnum", "交易数量");
        firstMap.put("taxamount", "交易金额");
        firstMap.put("costprice", "成本价");
        result.add(firstMap);
        PageData pageData = exceptionReportService.getSalePriceReportList(pageMap);
        List<Map> list = pageData.getList();
        list.addAll(pageData.getFooter());
        for(Map<String, Object> map2 : list){
            Map<String, Object> retMap = new LinkedHashMap<String, Object>();
            for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
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
     * 显示退货价格异常报表页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-12-07
     */
    public String showRejectPriceReportPage()throws Exception{
        String today = CommonUtils.getTodayDataStr();
        request.setAttribute("today", today);
        //客户退货时，商品价格取价方式 1取最近一次销售价2取最近一段时间的最低销售价0默认销售价
        String salesRejectCustomerGoodsPrice = getSysParamValue("SalesRejectCustomerGoodsPrice");
        if(StringUtils.isEmpty(salesRejectCustomerGoodsPrice)){
            salesRejectCustomerGoodsPrice = "0";
        }
        request.setAttribute("salesRejectCustomerGoodsPrice",salesRejectCustomerGoodsPrice);
        return SUCCESS;
    }

    /**
     * 获取退货价格异常报表数据
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-12-08
     */
    public String getRejectPriceReportList()throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = exceptionReportService.getRejectPriceReportList(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 导出退货价格异常报表数据
     * @throws Exception
     */
    public void exportRejectPriceReportList()throws Exception{
        //客户退货时，商品价格取价方式 1取最近一次销售价2取最近一段时间的最低销售价0默认销售价
        String salesRejectCustomerGoodsPrice = getSysParamValue("SalesRejectCustomerGoodsPrice");
        if(StringUtils.isEmpty(salesRejectCustomerGoodsPrice)){
            salesRejectCustomerGoodsPrice = "0";
        }

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
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("rejectid", "退货通知单号");
        firstMap.put("id", "销售退货入库单号");
        firstMap.put("businessdate", "业务日期");
        firstMap.put("customerid", "客户编码");
        firstMap.put("customername", "客户名称");
        firstMap.put("goodsid", "商品编码");
        firstMap.put("goodsname", "商品名称");
        firstMap.put("barcode", "条形码");
        firstMap.put("brandname", "商品品牌");
        firstMap.put("unitname", "单位");
        firstMap.put("taxprice", "交易价格");
        firstMap.put("unitnum", "交易数量");
        firstMap.put("taxamount", "交易金额");
        if("0".equals(salesRejectCustomerGoodsPrice)){
            firstMap.put("defaultprice", "参照价格");
        }else if("1".equals(salesRejectCustomerGoodsPrice)){
            firstMap.put("lastprice", "参照价格");
        }else if("2".equals(salesRejectCustomerGoodsPrice)){
            firstMap.put("lowestprice", "参照价格");
        }
        result.add(firstMap);
        PageData pageData = exceptionReportService.getRejectPriceReportList(pageMap);
        List<Map> list = pageData.getList();
        list.addAll(pageData.getFooter());
        for(Map<String, Object> map2 : list){
            Map<String, Object> retMap = new LinkedHashMap<String, Object>();
            for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
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
}

