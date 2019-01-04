/**
 * @(#)SalesReportAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 17, 2013 chenwei 创建版本
 */
package com.hd.agent.report.action;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Brand;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.common.util.*;
import com.hd.agent.report.model.*;
import com.hd.agent.report.service.ISalesReportService;
import com.hd.agent.system.model.SysParam;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 销售报表action
 * @author chenwei
 */
public class SalesReportAction extends BaseFilesAction {
	/**
	 * 销售报表service
	 */
	private ISalesReportService salesReportService;

	public ISalesReportService getSalesReportService() {
		return salesReportService;
	}

	public void setSalesReportService(ISalesReportService salesReportService) {
		this.salesReportService = salesReportService;
	}
	/**
	 * 显示按客户销售情况报表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 17, 2013
	 */
	public String showSalesCustomerReportPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	
	/**
	 * 显示按商品销售情况报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 30, 2013
	 */
	public String showSalesGoodsReportPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		List goodstypeList = getBaseSysCodeService().showSysCodeListByType("goodstype");
		request.setAttribute("goodstypeList", goodstypeList);
		return "success";
	}
	/**
	 * 显示按商品分客户统计页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 13, 2013
	 */
	public String showSalesGoodsCustomerDetailPage() throws Exception{
		String goodsid= request.getParameter("goodsid");
		String goodsname= request.getParameter("goodsname");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("goodsid", goodsid);
		request.setAttribute("goodsname", goodsname);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return "success";
	}
	/**
	 * 显示按品牌销售情况报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 12, 2013
	 */
	public String showSalesBrandReportPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 显示按品牌分商品统计页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 13, 2013
	 */
	public String showSalesBrandGoodsDetailPage() throws Exception{
		String brand= request.getParameter("brand");
		String brandname = request.getParameter("brandname");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("brand", brand);
		request.setAttribute("brandname", brandname);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return "success";
	}
	/**
	 * 显示按部门销售情况报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 13, 2013
	 */
	public String showSalesDeptReportPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 显示按供应商销售情况报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 20, 2014
	 */
	public String showSalesSupplierReportPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 显示按供应商分商品销售情况表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 20, 2014
	 */
	public String showSalesSupplierDetailPage()throws Exception{
		String supplierid= request.getParameter("supplierid");
		String suppliername = request.getParameter("suppliername");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("supplierid", supplierid);
		request.setAttribute("suppliername", suppliername);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return SUCCESS;
	}
	
	/**
	 * 显示按品牌业务员销售情况报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 14, 2013
	 */
	public String showSalesBrandUserReportPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 显示分品牌业务员分品牌销售情况报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 25, 2013
	 */
	public String showSalesBrandUserAndBrandReportDataPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 显示分品牌业务员分客户销售情况统计报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 25, 2013
	 */
	public String showSalesBranduserCustomerReportDataPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return 	SUCCESS;
	}
	
	/**
	 * 显示分品牌业务员分品牌分客户销售情况统计报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 26, 2013
	 */
	public String showSalesBrandUserBrandCustomerReportDataPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return 	SUCCESS;
	}

	/**
	 * 显示按客户业务员销售情况报表页面(客户业务员)
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 20, 2013
	 */
	public String showSalesUserReportPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 显示按品牌部门销售统计情况报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 21, 2013
	 */
	public String showSalesBrandDeptReportPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 获取按分公司销售统计情况报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jan 14, 2014
	 */
	public String showSalesCompanyReportPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 获取按分公司销售情况统计表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jan 14, 2014
	 */
	public String showSalesCompanyReportData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        Map map2 = salesReportService.showSalesCompanyReportList(pageMap);
        jsonResult = new HashMap();
        jsonResult.put("rows", map2.get("list"));
        jsonResult.put("footer", map2.get("footer"));
		return SUCCESS;
	}
	
	/**
	 * 显示公司部门销售统计情况页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jan 14, 2014
	 */
	public String showSalesCompanyDetailPage()throws Exception{
		String branddept= request.getParameter("branddept");
		String branddeptname = request.getParameter("branddeptname");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("branddept", branddept);
		request.setAttribute("branddeptname", branddeptname);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return "success";
	}
	
	/**
	 * 显示按分公司分品牌部门分品牌销售统计情况页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 24, 2014
	 */
	public String showSalesCompanyBrandDetailPage()throws Exception{
		String branddept= request.getParameter("branddept");
		String branddeptname = request.getParameter("branddeptname");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		String company = request.getParameter("company");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("branddept", branddept);
		request.setAttribute("branddeptname", branddeptname);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		request.setAttribute("company", company);
		return SUCCESS;
	}
	
	/**
	 * 导出分公司销售情况统计报表
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jan 14, 2014
	 */
	public void exportSalesCompanyReportData()throws Exception{
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
        //导出字段权限
       // Map map3 = getAccessColumn("t_report_sales_base");
		Map map4 = salesReportService.showSalesCompanyReportList(pageMap);
        List list = (List) map4.get("list");
        list.addAll((List)map4.get("footer"));
        ExcelUtils.exportAnalysExcel(map,list);
	}
	
	/**
	 * 显示按品牌部门分品牌统计页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 14, 2013
	 */
	public String showSalesBrandDeptDetailPage() throws Exception{
		String deptid= request.getParameter("deptid");
		String branddeptname = request.getParameter("branddeptname");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("deptid", deptid);
		request.setAttribute("branddeptname", branddeptname);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return "success";
	}
	/**
	 * 显示按客户业务员明细统计页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 17, 2013
	 */
	public String showSalesUserDetailPage() throws Exception{
		String salesuser= request.getParameter("salesuser");
		String salesusername = request.getParameter("salesusername");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("salesuser", salesuser);
		request.setAttribute("salesusername", salesusername);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return "success";
	}

	/**
	 * 显示按部门明细统计页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 17, 2013
	 */
	public String showSalesDeptDetailPage() throws Exception{
		String deptid= request.getParameter("deptid");
		String deptname = request.getParameter("deptname");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("deptid", deptid);
		request.setAttribute("deptname", deptname);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return "success";
	}
	/**
	 * 显示按品牌业务员明细统计页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 17, 2013
	 */
	public String showSalesBranduserDetailPage() throws Exception{
		String branduser= request.getParameter("branduser");
		String brandusername = request.getParameter("brandusername");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("branduser", branduser);
		request.setAttribute("brandusername", brandusername);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return "success";
	}
	
	/**
	 * 导出销售情况统计报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 18, 2013
	 */
	public void exportBaseSalesReportData()throws Exception{
		String groupcols = null,exporttype = null;
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
		if(map.containsKey("groupcols") && null != map.get("groupcols")){
			groupcols = (String)map.get("groupcols");
            queryMap.put("groupcols",groupcols);
		}
		if(map.containsKey("exporttype") && null != map.get("exporttype")){
			exporttype = (String)map.get("exporttype");
		}else{
			exporttype = "base";
		}
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
		PageData pageData = null;
		if("base".equals(exporttype)){
			pageData = salesReportService.showBaseSalesReportData(pageMap);
		}else if("finance".equals(exporttype)){
			pageData = salesReportService.getFinanceSalesReportData(pageMap);
		}
        List<BaseSalesReport> list = pageData.getList();
        if(null != pageData.getFooter()){
            List<BaseSalesReport> footer = pageData.getFooter();
            list.addAll(footer);
        }
		setNodataToSpaceData(list);
        ExcelUtils.exportAnalysExcel(map,list);

	}

	/**
	 * 将编号为nodata的转换为""
	 */
	private void setNodataToSpaceData(List<BaseSalesReport> list){
		if(list.size() != 0){
			for(BaseSalesReport baseSalesReport : list){
				if("nodata".equals(baseSalesReport.getCustomerid())){
					baseSalesReport.setCustomerid("");
				}
				if("nodata".equals(baseSalesReport.getPcustomerid())){
					baseSalesReport.setPcustomerid("");
				}
				if("nodata".equals(baseSalesReport.getCustomersort())){
					baseSalesReport.setCustomersort("");
				}
				if("nodata".equals(baseSalesReport.getSalesarea())){
					baseSalesReport.setSalesarea("");
				}
				if("nodata".equals(baseSalesReport.getSalesuser())){
					baseSalesReport.setSalesuser("");
				}
				if("nodata".equals(baseSalesReport.getSalesdept())){
					baseSalesReport.setSalesdept("");
				}
				if("nodata".equals(baseSalesReport.getGoodsid())){
					baseSalesReport.setGoodsid("");
				}
				if("nodata".equals(baseSalesReport.getBrandid())){
					baseSalesReport.setBrandid("");
				}
				if("nodata".equals(baseSalesReport.getGoodssort())){
					baseSalesReport.setGoodssort("");
				}
				if("nodata".equals(baseSalesReport.getBranddept())){
					baseSalesReport.setBranddept("");
				}
				if("nodata".equals(baseSalesReport.getBranduser())){
					baseSalesReport.setBranduser("");
				}
				if("nodata".equals(baseSalesReport.getSupplieruser())){
					baseSalesReport.setSupplieruser("");
				}
				if("nodata".equals(baseSalesReport.getSupplierid())){
					baseSalesReport.setSupplierid("");
				}
				if("nodata".equals(baseSalesReport.getBranduserdept())){
					baseSalesReport.setBranduserdept("");
				}
				if("nodata".equals(baseSalesReport.getStorageid())){
					baseSalesReport.setStorageid("");
				}
			}
		}
	}

    /**
     * 导出销售情况统计报表
     * @throws Exception
     * @author lin_xx
     * @date April 29, 2016
     */
    public void exportSalesDeptReportData()throws Exception{
        String groupcols = null,exporttype = null;
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        if(map.containsKey("groupcols") && null != map.get("groupcols")){
            groupcols = (String)map.get("groupcols");
            queryMap.put("groupcols",groupcols);
        }
        if(map.containsKey("exporttype") && null != map.get("exporttype")){
            exporttype = (String)map.get("exporttype");
        }else{
            exporttype = "base";
        }
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

        PageData pageData = null;
        if("base".equals(exporttype)){
            pageData = salesReportService.showSalesDeptReportData(pageMap);
        }else if("finance".equals(exporttype)){
            pageData = salesReportService.getFinanceSalesReportData(pageMap);
        }
        List<BaseSalesReport> list = pageData.getList();
        if(null != pageData.getFooter()){
            List<BaseSalesReport> footer = pageData.getFooter();
            list.addAll(footer);
        }
		setNodataToSpaceData(list);
        ExcelUtils.exportAnalysExcel(map,list);
    }


    /**
	 * 根据定义类型获取显示的字段
	 * @param groupcols:customerid,pcustomerid,salesuserid,salesarea,deptid,goodsid,branddept,branduser,brand
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 18, 2013
	 */
	private Map<String, Object> getFirstMap(String groupcols,String exporttype,Map<String,String> map)throws Exception{
		Map map3 = getAccessColumn("t_report_sales_base");
		Map<String,Object> map2 = new LinkedHashMap<String, Object>();
		if(StringUtils.isNotEmpty(groupcols)){
			if(groupcols.indexOf(",") == -1){
				if("customerid".equals(groupcols)){
					map2.put("customerid", "客户编码");
					map2.put("customername", "客户名称");
					map2.put("pcustomerid", "总店编码");
					map2.put("pcustomername", "总店名称");
					map2.put("customersortname", "客户分类");
				}
				else if("pcustomerid".equals(groupcols)){
					map2.put("pcustomerid", "总店编码");
					map2.put("pcustomername", "总店名称");
				}
				else if("salesuser".equals(groupcols)){
					map2.put("salesusername", "客户业务员");
				}else if("customersort".equals(groupcols)){
					map2.put("customersortname", "客户分类");
				}
				else if("salesarea".equals(groupcols)){
					map2.put("salesareaname", "销售区域");
				}
				else if("salesdept".equals(groupcols)){
					map2.put("salesdeptname", "销售部门");
				}
				else if("goodsid".equals(groupcols)){
					map2.put("goodsid", "商品编码");
					map2.put("goodsname", "商品名称");
					map2.put("goodssortname", "商品分类");
					map2.put("goodstypename", "商品类型");
					map2.put("barcode", "条形码");
                    map2.put("price","单价");
					if(!map.containsKey("brandid")){
						map2.put("brandname", "品牌名称");
					}
					map2.put("unitname", "主单位");
					map2.put("auxunitname", "辅单位");
				}
				else if("goodssort".equals(groupcols)){
					map2.put("goodssortname", "商品分类");
				}
				else if("brandid".equals(groupcols)){
					map2.put("brandname", "品牌名称");
				}
				else if("branduser".equals(groupcols)){
					map2.put("brandusername", "品牌业务员");
					map2.put("branddeptname", "分公司");
				}
				else if("branddept".equals(groupcols)){
					map2.put("branddeptname", "品牌部门");
				}else if("supplierid".equals(groupcols)){
					map2.put("suppliername", "供应商名称");
				}else if("supplieruser".equals(groupcols)){
					map2.put("supplierusername", "厂家业务员");
				}else if("adduserid".equals(groupcols)){
					map2.put("addusername", "制单人");
				}
			}
			else{
				String[] groupcolsArr = groupcols.split(",");
				for(String  group: groupcolsArr){
					if("customerid".equals(group)){
						map2.put("customerid", "客户编码");
						map2.put("customername", "客户名称");
						map2.put("pcustomerid", "总店编码");
						map2.put("pcustomername", "总店名称");
						map2.put("customersortname", "客户分类");
					}
					else if("pcustomerid".equals(group)){
						map2.put("pcustomerid", "总店编码");
						map2.put("pcustomername", "总店名称");
					}
					else if("salesuser".equals(group)){
						map2.put("salesusername", "客户业务员");
					}else if("customersort".equals(group)){
						map2.put("customersortname", "客户分类");
					}
					else if("salesarea".equals(group)){
						map2.put("salesareaname", "销售区域");
					}
					else if("salesdept".equals(group)){
						map2.put("salesdeptname", "销售部门");
					}
					else if("goodsid".equals(group)){
						map2.put("goodsid", "商品编码");
						map2.put("goodsname", "商品名称");
						map2.put("goodssortname", "商品分类");
						map2.put("goodstypename", "商品类型");
						map2.put("barcode", "条形码");
                        map2.put("price","单价");
						if(!map.containsKey("brandid")){
							map2.put("brandname", "品牌名称");
						}
						map2.put("unitname", "主单位");
						map2.put("auxunitname", "辅单位");
					}
					else if("goodssort".equals(group)){
						map2.put("goodssortname", "商品分类");
					}
					else if("brandid".equals(group)){
						map2.put("brandname", "品牌名称");
					}
					else if("branduser".equals(group)){
						map2.put("brandusername", "品牌业务员");
					}
					else if("branddept".equals(group)){
						map2.put("branddeptname", "品牌部门");
					}else if("supplierid".equals(group)){
						map2.put("suppliername", "供应商名称");
					}else if("supplieruser".equals(group)){
						map2.put("supplierusername", "厂家业务员");
					}else if("adduserid".equals(group)){
						map2.put("addusername", "制单人");
					}
				}
			}
			if(!"finance".equals(exporttype)){
				map2.put("ordernum", "订单数量");
				map2.put("ordertotalbox", "订单箱数");
				map2.put("orderamount", "订单金额");
			}
			map2.put("initsendnum", "发货单数量");
			map2.put("initsendtotalbox", "发货单箱数");
			map2.put("initsendamount", "发货单金额");
			if(!"finance".equals(exporttype)){
				map2.put("sendnum", "发货出库数量");
				map2.put("sendtotalbox", "发货出库箱数");
				map2.put("sendamount", "发货出库金额");
			}
			map2.put("pushbalanceamount", "冲差金额");
			map2.put("directreturnnum", "直退数量");
			map2.put("directreturntotalbox", "直退箱数");
			map2.put("directreturnamount", "直退金额");
			map2.put("checkreturnnum", "退货数量");
			map2.put("checkreturntotalbox", "退货箱数");
			map2.put("checkreturnamount", "退货金额");
			map2.put("returnnum", "退货总数量");
			map2.put("returntotalbox", "退货总箱数");
			map2.put("returnamount", "退货合计");
			map2.put("salenum", "销售数量");
			map2.put("saletotalbox", "销售箱数");
			map2.put("saleamount", "销售金额");
			if(map3.containsKey("costamount")){
				map2.put("costamount", "成本金额");
			}
			if(map3.containsKey("salemarginamount")){
				map2.put("salemarginamount", "销售毛利额");
			}
			if(map3.containsKey("realrate")){
				map2.put("realrate", "实际毛利率%");
			}
		}
		else{
			map2.put("customerid", "客户编码");
			map2.put("customername", "客户名称");
			map2.put("pcustomerid", "总店编码");
			map2.put("pcustomername", "总店名称");
			if(!"finance".equals(exporttype)){
				map2.put("ordernum", "订单数量");
				map2.put("ordertotalbox", "订单箱数");
				map2.put("orderamount", "订单金额");
			}
			map2.put("initsendnum", "发货单数量");
			map2.put("initsendtotalbox", "发货单箱数");
			map2.put("initsendamount", "发货单金额");
			if(!"finance".equals(exporttype)){
				map2.put("sendnum", "发货出库数量");
				map2.put("sendtotalbox", "发货出库箱数");
				map2.put("sendamount", "发货出库金额");
			}
			map2.put("pushbalanceamount", "冲差金额");
			map2.put("directreturnnum", "直退数量");
			map2.put("directreturntotalbox", "直退箱数");
			map2.put("directreturnamount", "直退金额");
			map2.put("checkreturnnum", "退货数量");
			map2.put("checkreturntotalbox", "退货箱数");
			map2.put("checkreturnamount", "退货金额");
			map2.put("returnnum", "退货总数量");
			map2.put("returntotalbox", "退货总箱数");
			map2.put("returnamount", "退货合计");
			map2.put("salenum", "销售数量");
			map2.put("saletotalbox", "销售箱数");
			map2.put("saleamount", "销售金额");
			if(map3.containsKey("costamount")){
				map2.put("costamount", "成本金额");
			}
			if(map3.containsKey("salemarginamount")){
				map2.put("salemarginamount", "销售毛利额");
			}
			if(map3.containsKey("realrate")){
				map2.put("realrate", "实际毛利率%");
			}
		}
		return map2;
	}
	
	/**
	 * 显示销售情况基础统计报表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 12, 2013
	 */
	public String showBaseSalesReportPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		List goodstypeList = getBaseSysCodeService().showSysCodeListByType("goodstype");
		request.setAttribute("goodstypeList", goodstypeList);
		request.setAttribute("salestypeList", getBaseSysCodeService().showSysCodeListByType("salestype"));//销售类型
		return "success";
	}

    /**
     * 显示销售情况基础统计报表
     * @return
     * @throws Exception
     * @author lin_xx
     * @date April 25, 2016
     */
    public String showBaseSalesDeptReportPage() throws Exception{
        String today = CommonUtils.getTodayDataStr();
        String firstDay = CommonUtils.getMonthDateStr();
        Map map = getAccessColumn("t_report_sales_base");
        request.setAttribute("map", map);
        request.setAttribute("firstDay", firstDay);
        request.setAttribute("today", today);
        List goodstypeList = getBaseSysCodeService().showSysCodeListByType("goodstype");
        request.setAttribute("goodstypeList", goodstypeList);
        return "success";
    }
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 11, 2014
	 */
	public String showBaseSalesAllReportPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 获取销售情况基础统计数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 12, 2013
	 */
	public String showBaseSalesReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
		PageData pageData = salesReportService.showBaseSalesReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}

	/**
	 * 打印销售情况统计报表
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Feb 09, 2018
	 */
	public String printBaseSalesReportData() throws Exception {
		String strPageName=request.getParameter("strPageName");
		String intOrient=request.getParameter("intOrient");
		request.setAttribute("strPageName",strPageName);
		request.setAttribute("intOrient",intOrient);
		Integer rownum=Integer.parseInt(request.getParameter("rownum"));
		String title=request.getParameter("title");
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		Map queryMap = new HashMap();
		queryMap.put("isflag", "true");
		String query = map.get("param");
		JSONObject object = JSONObject.fromObject(query);
		for (Object k : object.keySet()) {
			Object v = object.get(k);
			if (StringUtils.isNotEmpty((String) v)) {
				queryMap.put(k.toString(), v);
			}
		}
		pageMap.setCondition(queryMap);
		PageData pageData = salesReportService.showBaseSalesReportData(pageMap);
		List list = pageData.getList();
		if (null != pageData.getFooter()) {
			List footer = pageData.getFooter();
			list.addAll(footer);
		}
		if (StringUtils.isEmpty(title))
			title = "销售情况基础统计报表";
		request.setAttribute("title", title);
		request.setAttribute("body", ExcelUtils.dataToHTML(map, list, rownum));
		return SUCCESS;
	}
    /**
     * 获取人员部门销售统计数据
     * @return
     * @throws Exception
     * @author lin_xx
     * @date April 29, 2016
     */
    public String showSalesDeptReportData() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = salesReportService.showSalesDeptReportData(pageMap);
        addJSONObjectWithFooter(pageData);
        return "success";
    }

	/**
	 * 打印人员部门销售报表
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Feb 09, 2018
	 */
    public String printSalesDeptReportData() throws Exception {
		String strPageName=request.getParameter("strPageName");
		String intOrient=request.getParameter("intOrient");
		request.setAttribute("strPageName",strPageName);
		request.setAttribute("intOrient",intOrient);
		Integer rownum=Integer.parseInt(request.getParameter("rownum"));
		String title=request.getParameter("title");
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		Map queryMap = new HashMap();
		queryMap.put("isflag", "true");
		String query = map.get("param");
		JSONObject object = JSONObject.fromObject(query);
		for (Object k : object.keySet()) {
			Object v = object.get(k);
			if (StringUtils.isNotEmpty((String) v)) {
				queryMap.put(k.toString(), v);
			}
		}
		pageMap.setCondition(queryMap);
		PageData pageData = salesReportService.showSalesDeptReportData(pageMap);
		List list = pageData.getList();
		if (null != pageData.getFooter()) {
			List footer = pageData.getFooter();
			list.addAll(footer);
		}
		if (StringUtils.isEmpty(title))
			title = "人员部门销售报表";
		request.setAttribute("title", title);
		request.setAttribute("body", ExcelUtils.dataToHTML(map, list, rownum));
		return SUCCESS;
	}

	/**
	 * 显示高级查询页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 16, 2013
	 */
	public String showAdvancedQueryPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 显示多选页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 16, 2013
	 */
	public String showSelectMoreParamPage()throws Exception{
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		return SUCCESS;
	}
	
	/**
	 * 将类对象转化为List<Map<String, Object>>
	 * @param pageData
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 26, 2013
	 */
	private List<Map<String, Object>> objectToMapList(PageData pageData,Map<String, Object> firstMap)throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		if(pageData.getList().size() != 0){
			for(BaseSalesReport baseSalesReport : new ArrayList<BaseSalesReport>(pageData.getList())){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(baseSalesReport);
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
		if(pageData.getFooter().size() != 0){
			for(BaseSalesReport baseSalesReport : new ArrayList<BaseSalesReport>(pageData.getFooter())){
				if(null != baseSalesReport){
					Map<String, Object> retMap = new LinkedHashMap<String, Object>();
					Map<String, Object> map = new HashMap<String, Object>();
					map = PropertyUtils.describe(baseSalesReport);
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
		}
		return result;
	}
	/**
	 * 显示销售同期比情况报表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 12, 2013
	 */
	public String showSalesCorrespondPeriodReportPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		request.setAttribute("prevyearfirstday", CommonUtils.getPrevYearFirstMonthDay());
		request.setAttribute("prevyearcurday", CommonUtils.getPrevYearCurrentMonthDay());
		return "success";
	}
	/**
	 * 获取销售同比环比情况统计数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 12, 2013
	 */
	public String showSalesCorrespondPeriodReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		if(null!=map && map.containsKey("ispageflag")){
			map.remove("ispageflag");
		}
		pageMap.setCondition(map);
		PageData pageData = salesReportService.showSalesCorrespondPeriodReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 导出销售同比环比情况统计数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-11-26
	 */
	public String exportSalesCorrespondPeriodReportData() throws Exception{
		String groupcols=null;
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("ispageflag", "true");
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
		PageData pageData = salesReportService.showSalesCorrespondPeriodReportData(pageMap);
		ExcelUtils.exportExcel(exportSalesCorrespondPeriodReportDataFilter(pageData.getList(),pageData.getFooter(),groupcols), title);	
		return SUCCESS;
	}
	

	/**
	 * 数据转换，list专程符合excel导出的数据格式(分商品)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportSalesCorrespondPeriodReportDataFilter(List<SalesCorrespondPeriodReport> list,List<SalesCorrespondPeriodReport> footerlist,String groupcols) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		if(StringUtils.isNotEmpty(groupcols)){
			if(groupcols.indexOf(",") == -1){
				if("customerid".equals(groupcols)){
					firstMap.put("customerid", "客户编码");
					firstMap.put("customername", "客户名称");
				}else if("pcustomerid".equals(groupcols)){
					firstMap.put("pcustomername", "总店名称");
				}else if("salesuser".equals(groupcols)){
					firstMap.put("salesusername", "客户业务员");
				}else if("salesarea".equals(groupcols)){
					firstMap.put("salesareaname", "销售区域");
				}else if("salesdept".equals(groupcols)){
					firstMap.put("salesdeptname", "销售部门");
				}else if("goodsid".equals(groupcols)){
					firstMap.put("goodsid", "商品编码");
					firstMap.put("goodsname", "商品名称");
					firstMap.put("barcode", "条形码");
					firstMap.put("brandname", "品牌名称");
					firstMap.put("goodssortname", "商品分类名称");
				}else if("goodssort".equals(groupcols)){
					firstMap.put("goodssortname", "商品分类名称");
				}else if("brandid".equals(groupcols)){
					firstMap.put("brandname", "品牌名称");
				}else if("branduser".equals(groupcols)){
					firstMap.put("brandusername", "品牌业务员");
				}else if("branddept".equals(groupcols)){
					firstMap.put("branddeptname", "品牌部门");
				}else if("supplierid".equals(groupcols)){
					firstMap.put("supplierid", "供应商编码");
					firstMap.put("suppliername", "供应商名称");
				}
			}else{
				String[] groupcolsArr = groupcols.split(",");
				for(String  group: groupcolsArr){
					if("customerid".equals(group)){
						firstMap.put("customerid", "客户编码");
						firstMap.put("customername", "客户名称");
					}else if("pcustomerid".equals(group)){
						firstMap.put("pcustomername", "总店名称");
					}else if("salesuser".equals(group)){
						firstMap.put("salesusername", "客户业务员");
					}else if("salesarea".equals(group)){
						firstMap.put("salesareaname", "销售区域");
					}else if("salesdept".equals(group)){
						firstMap.put("salesdeptname", "销售部门");
					}else if("goodsid".equals(group)){
						firstMap.put("goodsid", "商品编码");
						firstMap.put("goodsname", "商品名称");
						firstMap.put("barcode", "条形码");
						firstMap.put("brandname", "品牌名称");
						firstMap.put("goodssortname", "商品分类名称");
					}else if("goodssort".equals(groupcols)){
						firstMap.put("goodssortname", "商品分类名称");
					}else if("brandid".equals(group)){
						firstMap.put("brandname", "品牌名称");
					}else if("branduser".equals(group)){
						firstMap.put("brandusername", "品牌业务员");
					}
					else if("branddept".equals(group)){
						firstMap.put("branddeptname", "品牌部门");
					}else if("supplierid".equals(group)){
						firstMap.put("supplierid", "供应商编码");
						firstMap.put("suppliername", "供应商名称");
					}
				}
			}
			firstMap.put("qqsaletotalbox", "前期销售箱数");
			firstMap.put("saletotalbox", "本期销售箱数");
			firstMap.put("saletotalboxgrowth", "销售箱数增长比率%");
			firstMap.put("qqsaleamount", "前期销售金额");
			firstMap.put("saleamount", "本期销售金额");
			firstMap.put("salegrowth", "销售金额增长比率%");
			firstMap.put("qqsalegrossamount", "前期毛利金额");
			firstMap.put("salegrossamount", "本期毛利金额");
			firstMap.put("salegrossgrowth", "毛利金额增长比率%");
			firstMap.put("qqsalegrossrate", "前期毛利率%");
			firstMap.put("salegrossrate", "本期毛利率%");
			firstMap.put("salegrossrategrowth", "毛利率增长比率%");
			firstMap.put("qqwriteoffamount", "前期回笼金额");
			firstMap.put("writeoffamount", "本期回笼金额");
			firstMap.put("writeoffgrowth", "回笼金额增长比率%");
			firstMap.put("qqwriteoffgrossamount", "前期回笼毛利");
			firstMap.put("writeoffgrossamount", "本期回笼毛利");
			firstMap.put("writeoffgrossgrowth", "回笼毛利增长比率%");
			firstMap.put("qqwriteoffgrate", "前期回笼毛利率%");
			firstMap.put("writeoffgrate", "本期回笼毛利率%");
			firstMap.put("writeoffgrategrowth", "回笼毛利率增长比率%");
		}else{
			firstMap.put("customerid", "客户编码");
			firstMap.put("customername", "客户名称");
			firstMap.put("pcustomername", "总店名称");
			firstMap.put("salesusername", "客户业务员");
			firstMap.put("salesareaname", "销售区域");
			firstMap.put("salesdeptname", "销售部门");
			firstMap.put("branddeptname", "品牌部门");
			firstMap.put("brandusername", "品牌业务员");
			firstMap.put("supplierid", "供应商编码");
			firstMap.put("suppliername", "供应商名称");
			firstMap.put("goodsid", "商品编码");
			firstMap.put("goodsname", "商品名称");
			firstMap.put("goodssortname", "商品分类名称");
			firstMap.put("barcode", "条形码");
			firstMap.put("brandname", "品牌名称");
			firstMap.put("qqsaletotalbox", "前期销售箱数");
			firstMap.put("saletotalbox", "本期销售箱数");
			firstMap.put("saletotalboxgrowth", "销售箱数增长比率%");
			firstMap.put("qqsaleamount", "前期销售金额");
			firstMap.put("saleamount", "本期销售金额");
			firstMap.put("salegrowth", "销售金额增长比率%");
			firstMap.put("qqsalegrossamount", "前期毛利金额");
			firstMap.put("salegrossamount", "本期毛利金额");
			firstMap.put("salegrossgrowth", "毛利金额增长比率%");
			firstMap.put("qqsalegrossrate", "前期毛利率%");
			firstMap.put("salegrossrate", "本期毛利率%");
			firstMap.put("salegrossrategrowth", "毛利率增长比率%");
			firstMap.put("qqwriteoffamount", "前期回笼金额");
			firstMap.put("writeoffamount", "本期回笼金额");
			firstMap.put("writeoffgrowth", "回笼金额增长比率%");
			firstMap.put("qqwriteoffgrossamount", "前期回笼毛利");
			firstMap.put("writeoffgrossamount", "本期回笼毛利");
			firstMap.put("writeoffgrossgrowth", "回笼毛利增长比率%");
			firstMap.put("qqwriteoffgrate", "前期回笼毛利率%");
			firstMap.put("writeoffgrate", "本期回笼毛利率%");
			firstMap.put("writeoffgrategrowth", "回笼毛利率增长比率%");
		}
		result.add(firstMap);
		
		
		if(null!=list && list.size() > 0){
			for(SalesCorrespondPeriodReport item : new ArrayList<SalesCorrespondPeriodReport>(list)){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(item);
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
		if(null!=footerlist && footerlist.size() > 0){
			for(SalesCorrespondPeriodReport item : new ArrayList<SalesCorrespondPeriodReport>(footerlist)){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(item);
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
	 * 显示部门采购销售汇总表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 12, 2013
	 */
	public String showBuySalesBillCountReportPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		request.setAttribute("prevyearfirstday", CommonUtils.getPrevYearFirstMonthDay());
		request.setAttribute("prevyearcurday", CommonUtils.getPrevYearCurrentMonthDay());
		return "success";
	}
	/**
	 * 获取部门采购销售汇总表统计数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 12, 2013
	 */
	public String showBuySalesBillCountReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		if(null!=map && map.containsKey("ispageflag")){
			map.remove("ispageflag");
		}
		pageMap.setCondition(map);
		PageData pageData = salesReportService.showBuySalesBillCountReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 导出部门采购销售汇总表统计数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-11-26
	 */
	public String exportBuySalesBillCountReportData() throws Exception{
		String groupcols=null;
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		if(map.containsKey("groupcols") && null != map.get("groupcols")){
			groupcols = (String)map.get("groupcols");
		}
		map.put("ispageflag", "true");
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
		PageData pageData = salesReportService.showBuySalesBillCountReportData(pageMap);
		ExcelUtils.exportExcel(exportBuySalesBillCountReportDataFilter(pageData.getList(),pageData.getFooter(),groupcols), title);	
		return null;
	}
	

	/**
	 * 数据转换，list专程符合excel导出的数据格式(分商品)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportBuySalesBillCountReportDataFilter(List<BuySalesBillCountReport> list,List<BuySalesBillCountReport> footerlist,String groupcols) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		if(StringUtils.isNotEmpty(groupcols)){
			if(groupcols.indexOf(",") == -1){
				if("goodsid".equals(groupcols)){
					firstMap.put("supplierid", "供应商编号");
					firstMap.put("suppliername", "供应商名称");
					firstMap.put("branddeptname", "品牌部门");
					firstMap.put("brandusername", "品牌业务员");
					firstMap.put("goodsid", "商品编码");
					firstMap.put("goodsname", "商品名称");
					firstMap.put("barcode", "条形码");
					firstMap.put("brandname", "品牌名称");
				}
				else if("brand".equals(groupcols)){
					firstMap.put("brandname", "品牌名称");
				}
				else if("branduser".equals(groupcols)){
					firstMap.put("branddeptname", "品牌部门");
					firstMap.put("brandusername", "品牌业务员");
				}
				else if("branddept".equals(groupcols)){
					firstMap.put("branddeptname", "品牌部门");
				}
				else if("supplierid".equals(groupcols)){
					firstMap.put("supplierid", "供应商编号");
					firstMap.put("suppliername", "供应商名称");
				}
			}
			else{
				String[] groupcolsArr = groupcols.split(",");
				for(String  group: groupcolsArr){
					if("goodsid".equals(group)){
						firstMap.put("goodsid", "商品编码");
						firstMap.put("goodsname", "商品名称");
					}
					else if("brand".equals(group)){
						firstMap.put("brandname", "品牌名称");
					}
					else if("branduser".equals(group)){
						firstMap.put("branddeptname", "品牌部门");
						firstMap.put("brandusername", "品牌业务员");
					}
					else if("branddept".equals(group)){
						firstMap.put("branddeptname", "品牌部门");
					}
					else if("supplierid".equals(group)){
						firstMap.put("supplierid", "供应商编号");
						firstMap.put("suppliername", "供应商名称");
					}
				}
			}
			firstMap.put("buyamount", "采购金额");
			firstMap.put("buybillcount", "采购单据数");		
			firstMap.put("saleamount", "销售金额");		
			firstMap.put("salebillcount", "销售单据数");	
			firstMap.put("costamount", "销售成本");	
			firstMap.put("salegrossamount", "销售毛利");
			firstMap.put("salegrossrate", "销售毛利率%");
			firstMap.put("writeoffamount", "回笼金额");
			firstMap.put("costwriteoffamount", "回笼成本金额");
			firstMap.put("writeoffgrossamount", "回笼毛利额");
			firstMap.put("writeoffgrate", "回笼毛利率%");
			firstMap.put("billcount", "总单据数");
		}
		else{
			firstMap.put("supplierid", "供应商编号");
			firstMap.put("suppliername", "供应商名称");
			firstMap.put("branddeptname", "品牌部门");
			firstMap.put("branduser", "品牌业务员");
			firstMap.put("goodsid", "商品编号");
			firstMap.put("goodsname", "商品名称");
			firstMap.put("barcode", "条形码");
			firstMap.put("brandname", "品牌名称");
			firstMap.put("buyamount", "采购金额");
			firstMap.put("buybillcount", "采购单据数");		
			firstMap.put("saleamount", "销售金额");		
			firstMap.put("salebillcount", "销售单据数");	
			firstMap.put("costamount", "销售成本");	
			firstMap.put("salegrossamount", "销售毛利");
			firstMap.put("salegrossrate", "销售毛利率%");
			firstMap.put("writeoffamount", "回笼金额");
			firstMap.put("costwriteoffamount", "回笼成本金额");
			firstMap.put("writeoffgrossamount", "回笼毛利额");
			firstMap.put("writeoffgrate", "回笼毛利率%");
			firstMap.put("billcount", "总单据数");
		}
		result.add(firstMap);
		
		if(null!=list && list.size() > 0){
			for(BuySalesBillCountReport item : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(item);
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
		if(null!=footerlist && footerlist.size() > 0){
			for(BuySalesBillCountReport item : footerlist){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(item);
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
	 * 显示按客户分品牌分商品统计明细页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 10, 2013
	 */
	public String showSalesCustomerDetailPage()throws Exception{
		String customerid= request.getParameter("customerid");
		String customername = request.getParameter("customername");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("customerid", customerid);
		request.setAttribute("customername", customername);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return SUCCESS;
	}
	
	/**
	 * 导出按客户分品牌分商品销售情况明细表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 10, 2013
	 */
	public void exportSalesCustomerDetailReportData()throws Exception{
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
		Map<String,List<Map<String, Object>>> retMap = new HashMap<String, List<Map<String,Object>>>();
		String tnjson = "{",str = "";
		if(map.containsKey("groupcols")){
			String groupcols = (String)map.get("groupcols");
			if(groupcols.indexOf(";") != -1){
				String[] groupcolArr = groupcols.split(";");
				for(String group : groupcolArr){
					String str1 = "sales",str2 = "";
					List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
					pageMap.getCondition().put("groupcols", group);
					Map flagMap = new HashMap();
					if(pageMap.getCondition().containsKey("branduser")){
						flagMap.put("buflag", true);
					}
					if(pageMap.getCondition().containsKey("branddept")){
						flagMap.put("bdflag", true);
					}
					if(pageMap.getCondition().containsKey("customerid")){
						flagMap.put("cflag", true);
					}
					if(pageMap.getCondition().containsKey("brandid")){
						flagMap.put("bflag", true);
					}
					Map<String, Object> firstMap = getFirstMapForDetail(group,flagMap);
					result.add(firstMap);
					PageData pageData = null;
					List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
					if(!"company".equals(group)){
						pageData = salesReportService.showBaseSalesReportData(pageMap);
						list = objectToMapList(pageData,firstMap);
					}else{
						Map map3 = salesReportService.showSalesCompanyReportList(pageMap);
						List<BaseSalesReport> list1 = (List<BaseSalesReport>)map3.get("list");
						list1.addAll((List<BaseSalesReport>)map3.get("footer"));
						for(BaseSalesReport baseSalesReport : list1){
							Map<String, Object> retMap2 = new LinkedHashMap<String, Object>();
							Map<String, Object> map2 = new HashMap<String, Object>();
							map2 = PropertyUtils.describe(baseSalesReport);
							for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
								if(map2.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
									for(Map.Entry<String, Object> entry : map2.entrySet()){
										if(fentry.getKey().equals(entry.getKey())){
											objectCastToRetMap(retMap2,entry);
										}
									}
								}
								else{
									retMap2.put(fentry.getKey(), "");
								}
							}
							list.add(retMap2);
						}
					}
					result.addAll(list);
					if(group.indexOf(",") == -1){
						if("brandid".equals(group)){
							str1 += "brandid";
							str2 += "分品牌统计";
						}else if("goodsid".equals(group)){
							str1 += "goods";
							str2 += "分商品统计";
						}else if("customerid".equals(group)){
							str1 += "customer";
							str2 += "分客户统计";
						}
						else if("company".equals(group)){
							str1 += "company";
							str2 += "分公司统计";
						}
						str += "'"+str1+"':'"+str2+"',";
					}else{
						for(String group2 : group.split(",")){
							if("brandid".equals(group2)){
								str1 += "brandid";
								str2 += "分品牌";
							}else if("goodsid".equals(group2)){
								str1 += "goods";
								str2 += "分商品";
							}else if("customerid".equals(group2)){
								str1 += "customer";
								str2 += "分客户";
							}
						}
						str += "'"+str1+"':'"+str2+"统计',";
					}
					retMap.put(""+str1+"_list", result);
				}
			}
			else{
				if("brandid".equals(groupcols)){
					str += "'salesbrand':'分品牌统计',";
				}else if("goodsid".equals(groupcols)){
					str += "'salesgoods':'分商品统计',";
				}else if("customerid".equals(groupcols)){
					str += "'salescustomer':'分客户统计',";
				}
				else if("company".equals(groupcols)){
					str += "'salescompany':'分公司统计',";
				}
			}
			if(str.lastIndexOf(",") != -1){
				tnjson += str.substring(0, str.lastIndexOf(",")) + "}";
			}
			else{
				tnjson += str + "}";
			}
		}
		//String tnjson = "{'salesBrand':'分品牌统计','salesGoods':'分商品统计'}";
		ExcelUtils.exportMoreExcel(retMap, title,tnjson);
	}
	
	/**
	 * 根据定义类型获取显示的字段
	 * @param groupcols:customerid,brand,goodsid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 12, 2013
	 */
	private Map<String, Object> getFirstMapForDetail(String groupcols,Map map)throws Exception{
		Map map3 = getAccessColumn("t_report_sales_base");
		Map<String,Object> map2 = new LinkedHashMap<String, Object>();
		boolean otherflag = true;
		if(StringUtils.isNotEmpty(groupcols)){
			if(groupcols.indexOf(",") == -1){
				if("customerid".equals(groupcols)){
					map2.put("customerid", "客户编码");
					map2.put("customername", "客户名称");
					map2.put("pcustomername", "总店名称");
				}
				else if("goodsid".equals(groupcols)){
					map2.put("goodsid", "商品编码");
					map2.put("goodsname", "商品名称");
					map2.put("barcode", "条形码");
				}
				else if("brandid".equals(groupcols)){
					map2.put("brandname", "品牌名称");
					if(!map.containsKey("bdflag")){
						map2.put("branddeptname", "品牌部门");
					}
					if(!map.containsKey("buflag") && !map.containsKey("bdflag")
							&& !map.containsKey("cflag")){
						map2.put("brandusername", "品牌业务员");
					}
				}
				else if("company".equals(groupcols)){
					map2.put("branddeptname", "公司");
					otherflag = false;
				}
			}
			else{
				String[] groupcolsArr = groupcols.split(",");
				for(String  group: groupcolsArr){
					if("customerid".equals(group)){
						map2.put("customerid", "客户编码");
						map2.put("customername", "客户名称");
						map2.put("pcustomername", "总店名称");
					}
					else if("goodsid".equals(group)){
						map2.put("goodsid", "商品编码");
						map2.put("goodsname", "商品名称");
						map2.put("barcode", "条形码");
					}
					else if("brandid".equals(group)){
						map2.put("brandname", "商品品牌");
						if(!map.containsKey("bdflag")){
							map2.put("branddeptname", "品牌部门");
						}
						if(!map.containsKey("buflag") && !map.containsKey("bdflag")
								&& !map.containsKey("cflag")){
							map2.put("brandusername", "品牌业务员");
						}
					}
				}
			}
			if(otherflag){
				map2.put("unitname", "主单位");
				map2.put("auxunitname", "辅单位");
			}
			map2.put("ordernum", "订单数量");
			map2.put("ordertotalbox", "订单箱数");
			map2.put("orderamount", "订单金额");
			map2.put("initsendnum", "发货单数量");
			map2.put("initsendtotalbox", "发货单箱数");
			map2.put("initsendamount", "发货单金额");
			map2.put("sendnum", "发货出库数量");
			map2.put("sendtotalbox", "发货出库箱数");
			map2.put("sendamount", "发货出库金额");
			map2.put("pushbalanceamount", "冲差金额");
			map2.put("directreturnnum", "直退数量");
			map2.put("directreturntotalbox", "直退箱数");
			map2.put("directreturnamount", "直退金额");
			map2.put("checkreturnnum", "退货数量");
			map2.put("checkreturntotalbox", "退货箱数");
			map2.put("checkreturnamount", "退货金额");
			map2.put("returnnum", "退货总数量");
			map2.put("returntotalbox", "退货总箱数");
			map2.put("returnamount", "退货合计");
			map2.put("salenum", "销售数量");
			map2.put("saletotalbox", "销售箱数");
			map2.put("saleamount", "销售金额");
			if(map3.containsKey("costamount")){
				map2.put("costamount", "成本金额");
			}
			if(map3.containsKey("salemarginamount")){
				map2.put("salemarginamount", "销售毛利额");
			}
			if(map3.containsKey("realrate")){
				map2.put("realrate", "实际毛利率%");
			}
		}
		return map2;
	}
	/**
	 * 显示分客户类型销售统计报表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 23, 2013
	 */
	public String showCustomerSortSalesReportPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 显示销售订单追踪明细表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 31, 2013
	 */
	public String showSalesOrderTrackReportPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 获取销售订单追踪明细数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 31, 2013
	 */
	public String showSalesOrderTrackReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesReportService.showSalesOrderTrackReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	
	/**
	 * 导出销售订单追踪明细数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 31, 2013
	 */
	public void exportSalesOrderTrackReportData() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("ispageflag", "true");
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
		pageMap.setCondition(map);
		PageData pageData = salesReportService.showSalesOrderTrackReportData(pageMap);
		pageData.getList().addAll(pageData.getFooter());
		ExcelUtils.exportExcel(exportSalesOrderTrackReportDataFilter(pageData.getList()), title);	
	}
	
	/**
	 * 销售订单追踪明细数据excel数据转换
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 31, 2013
	 */
	private List<Map<String, Object>> exportSalesOrderTrackReportDataFilter(List<Map> list) throws Exception{
		Map map3 = getAccessColumn("t_report_sales_base");
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("businessdate", "业务日期");
		firstMap.put("orderid", "订单编号");
        firstMap.put("sourcetypename", "来源类型");
        firstMap.put("sourceid", "客户单号");
		firstMap.put("customerid", "客户编号");
		firstMap.put("customername", "客户名称");
        firstMap.put("salesusername","客户业务员");
		firstMap.put("pcustomername", "总店名称");
		firstMap.put("addusername", "制单人");
		firstMap.put("goodsid", "商品编号");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("goodssortname", "商品分类");
        firstMap.put("spell", "助记符");
		firstMap.put("barcode", "条形码");
        firstMap.put("brandname", "品牌名称");
		firstMap.put("unitname", "单位");
		firstMap.put("taxprice", "订单单价");
		firstMap.put("ordernum", "订单数量");
		firstMap.put("ordertotalbox", "订单箱数");
		firstMap.put("orderamount", "订单金额");
		firstMap.put("ordernotaxamount", "订单未税金额");
		firstMap.put("initsendnum", "发货数量");
		firstMap.put("initsendtotalbox", "发货箱数");
		firstMap.put("initsendamount", "发货金额");
		firstMap.put("initsendnotaxamount", "发货未税金额");
		firstMap.put("sendnum", "发货出库数量");
		firstMap.put("sendtotalbox", "发货出库箱数");
		firstMap.put("sendamount", "发货出库金额");
		firstMap.put("sendnotaxamount", "发货出库未税金额");
		firstMap.put("checkprice", "验收平均价");
		firstMap.put("checknum", "验收数量");
		firstMap.put("checktotalbox", "验收箱数");
		firstMap.put("checkamount", "验收金额");
		firstMap.put("unitprice", "单位差价");
		firstMap.put("unitpriceamount", "差价总额");
		firstMap.put("returnnum", "直退数量");
		firstMap.put("returntotalbox", "直退箱数");
		firstMap.put("returnamount", "直退金额");
		firstMap.put("salesnum", "销售数量");
		firstMap.put("salestotalbox", "销售箱数");
		firstMap.put("salesamount", "销售金额");
		if(map3.containsKey("salescostamount")){
			firstMap.put("salescostamount", "成本金额");
		}
		if(map3.containsKey("salemarginamount")){
			firstMap.put("salemarginamount", "毛利额");
		}
		if(map3.containsKey("rate")){
			firstMap.put("rate", "毛利率%");
		}
		result.add(firstMap);
		
		if(null!=list && list.size() > 0){
			for(Map<String,Object> map : list){
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
	 * 退货追踪明细页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jan 8, 2014
	 */
	public String selesRejectTrackReportPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 退货追踪明细数据
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jan 8, 2014
	 */
	public String getSalesRejectTrackReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesReportService.getSalesRejectTrackReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	public void exportSalesRejectTrackReportData() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("ispageflag", "true");
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
		pageMap.setCondition(map);
		PageData pageData = salesReportService.getSalesRejectTrackReportData(pageMap);
		pageData.getList().addAll(pageData.getFooter());
		ExcelUtils.exportExcel(exportSalesRejectTrackReportDataFilter(pageData.getList()), title);	
	}
	
	private List<Map<String, Object>> exportSalesRejectTrackReportDataFilter(List<Map> list) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("businessdate", "业务日期");
		firstMap.put("customerid", "客户编号");
		firstMap.put("customername", "客户名称");
		firstMap.put("goodsid", "商品编号");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("barcode", "条形码");
		firstMap.put("brandname", "品牌名称");
		firstMap.put("unitname", "单位");
		firstMap.put("qnum", "发货数量");
		firstMap.put("qamount", "发货金额");
		firstMap.put("tunitnum", "发货出库数量");
		firstMap.put("ttaxamount", "发货出库金额");
		result.add(firstMap);
		
		if(null!=list && list.size() > 0){
			for(Map<String,Object> map : list){
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
	 * 显示尤妮佳销售情况报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 31, 2013
	 */
	public String showSalesUNJIAReportPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		String brandids = null;
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		//从系统参数中获取尤妮佳、强生、大宝、舒士达、葛兰素史可、中美史可、蓝月亮、妮维雅(供应商编码)
		String UNJIAIDS = getSysParamValue("UNJIAIDS");
		//request.setAttribute("UNJIAID", UNJIAID);
		//根据获取到的供应商编码从商品品牌中获取所属于尤妮佳供应商的品牌列表数据
		if(StringUtils.isNotEmpty(UNJIAIDS)){
			List<Brand> list = getBaseGoodsService().getBrandListBySupplierids(UNJIAIDS);
			if(null != list && list.size() != 0){
				for(Brand brand : list){
					if(null != brand){
						if(StringUtils.isEmpty(brandids)){
							brandids = brand.getId();
						}else{
							brandids += "," + brand.getId();
						}
					}
				}
			}
		}
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("brandids", brandids);
		return SUCCESS;
	}
	
	/**
	 * 尤妮佳销售情况报表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 31, 2013
	 */
	public String getUNJIASalesReportData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
        map.put("groupcols", "customerid,goodsid");
		pageMap.setCondition(map);
		PageData pageData = salesReportService.showBaseSalesReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 导出尤妮佳销售情况统计报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 31, 2013
	 */
	public void exportSalesUNJIAReportData()throws Exception{
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
        queryMap.put("groupcols", "customerid,goodsid");
        pageMap.setCondition(queryMap);
        PageData pageData = salesReportService.showBaseSalesReportData(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);

	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(尤妮佳销售情况统计)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 26, 2013
	 */
	private List<Map<String, Object>> exportSalesUNJIAReportDataFilter(List<SalesUNJIAReport> list) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("customerid", "客户编码");//客户编码
		firstMap.put("customername", "客户名称");//客户名称
		firstMap.put("shortcode", "客户助记符");//客户助记符
		firstMap.put("businessdate", "时间");//时间
		firstMap.put("field1", "");
		firstMap.put("field2", "");
		firstMap.put("field3", "");
		firstMap.put("field4", "");
		firstMap.put("field5", "");
		firstMap.put("goodsid", "商品编码");//商品编码
		firstMap.put("spell", "商品助记符");//商品助记符
		firstMap.put("unitnum", "数量");//数量
		firstMap.put("notaxprice", "单价:不含税");//单价:不含税
		firstMap.put("businessdate1", "时间");//时间
		firstMap.put("field6", "");
		firstMap.put("businessdate2", "时间");//时间
		firstMap.put("field7", "");
		firstMap.put("barcode", "");
		firstMap.put("field8", "");
		firstMap.put("field9", "");
		firstMap.put("field10", "");
		firstMap.put("customerid1", "客户编码");//客户编码
		result.add(firstMap);
		
		if(list.size() != 0){
			for(SalesUNJIAReport salesUNJIAReport : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(salesUNJIAReport);
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
	 * 显示按客户商品情况报表页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date Jul 17, 2013
	 */
	public String showSalesGoodsSandPriceReportPage() throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 显示按客户商品情况报表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-1-18
	 */
	public String showSalesGoodsSandPriceReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesReportService.showSalesGoodsSandPriceReportData(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 导出 按客户商品情况报表数据
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 31, 2013
	 */
	public void exportSalesGoodsSandPriceReportData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("ispageflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
        PageData pageData = salesReportService.showSalesGoodsSandPriceReportData(pageMap);

        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);
    }
	/**
	 * 显示客户商品交易
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-1-22
	 */
	public String showSalesGoodsTradeReportPage() throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 显示客户交易商品情况报表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-1-18
	 */
	public String showSalesGoodsTradeReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesReportService.showSalesGoodsTradeReportData(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	/**
	 * 导出 按客户交易商品情况报表数据
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 31, 2013
	 */
	public void exportSalesGoodsTradeReportData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("ispageflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
		PageData pageData = salesReportService.showSalesGoodsTradeReportData(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);
    }
	
	/**
	 * 显示客户商品交易
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-1-22
	 */
	public String showSalesGoodsNotTradeReportPage() throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 显示客户未交易商品情况报表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-1-18
	 */
	public String showSalesGoodsNotTradeReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesReportService.showSalesGoodsNotTradeReportData(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	/**
	 * 导出 按客户未交易商品情况报表数据
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 31, 2013
	 */
	public void exportSalesGoodsNotTradeReportData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("ispageflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
		PageData pageData = salesReportService.showSalesGoodsNotTradeReportData(pageMap);

        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);
    }
	/**
	 * 显示财务销售情况统计报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 24, 2014
	 */
	public String showFinanceSalesReportPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 获取财务销售情况统计报表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 24, 2014
	 */
	public String getFinanceSalesReportData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		if(map.containsKey("branduser")){
			if("0".equals(map.get("branduser"))){
				map.put("branduser", "");
			}
		}
		pageMap.setCondition(map);
		PageData pageData = salesReportService.getFinanceSalesReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 显示要货金额报表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 6, 2014
	 */
	public String showSalesDemandReportListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 获取要货金额报表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 6, 2014
	 */
	public String getSalesDemandReportList()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
		PageData pageData = salesReportService.getSalesDemandReportList(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}

	/**
	 * 打印要货报表
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Feb 09, 2018
	 */
	public String printSalesDemandReportList() throws Exception {
		String strPageName=request.getParameter("strPageName");
		String intOrient=request.getParameter("intOrient");
		request.setAttribute("strPageName",strPageName);
		request.setAttribute("intOrient",intOrient);
		Integer rownum=Integer.parseInt(request.getParameter("rownum"));
		String title=request.getParameter("title");
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		Map queryMap = new HashMap();
		queryMap.put("isflag", "true");
		String query = map.get("param");
		JSONObject object = JSONObject.fromObject(query);
		for (Object k : object.keySet()) {
			Object v = object.get(k);
			if (StringUtils.isNotEmpty((String) v)) {
				queryMap.put(k.toString(), v);
			}
		}
		pageMap.setCondition(queryMap);
		PageData pageData = salesReportService.getSalesDemandReportList(pageMap);
		List list = pageData.getList();
		if (null != pageData.getFooter()) {
			List footer = pageData.getFooter();
			list.addAll(footer);
		}
		if (StringUtils.isEmpty(title))
			title = "要货金额报表";
		request.setAttribute("title", title);
		request.setAttribute("body", ExcelUtils.dataToHTML(map, list, rownum));
		return SUCCESS;
	}

	/**
	 * 导出要货金额报表数据
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 7, 2014
	 */
	public void exportDemandReportData()throws Exception{
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
		PageData pageData = salesReportService.getSalesDemandReportList(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);
    }
	
	/**
	 * 显示直营销售报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 21, 2014
	 */
	public String showSalesCarReportListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}

	/**
	 * 获取直营销售报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 21, 2014
	 */
	public String getSalesCarReportList()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
		PageData pageData = salesReportService.getSalesCarReportList(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}

	/**
	 * 直营销售报表打印
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Feb 09, 2018
	 */
	public String printSalesCarReportList() throws Exception{
		String strPageName=request.getParameter("strPageName");
		String intOrient=request.getParameter("intOrient");
		request.setAttribute("strPageName",strPageName);
		request.setAttribute("intOrient",intOrient);
		Integer rownum=Integer.parseInt(request.getParameter("rownum"));
		String title=request.getParameter("title");
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		Map queryMap = new HashMap();
		queryMap.put("isflag", "true");
		String query = map.get("param");
		JSONObject object = JSONObject.fromObject(query);
		for (Object k : object.keySet()) {
			Object v = object.get(k);
			if (StringUtils.isNotEmpty((String) v)) {
				queryMap.put(k.toString(), v);
			}
		}
		pageMap.setCondition(queryMap);
		PageData pageData = salesReportService.getSalesCarReportList(pageMap);
		List list = pageData.getList();
		if (null != pageData.getFooter()) {
			List footer = pageData.getFooter();
			list.addAll(footer);
		}
		if (StringUtils.isEmpty(title))
			title = "车销销售报表";
		request.setAttribute("title", title);
		request.setAttribute("body", ExcelUtils.dataToHTML(map, list, rownum));
		return SUCCESS;
	}

	/**
	 * 导出直营销售报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 21, 2014
	 */
	public void exportCarReportData()throws Exception{
		Map map3 = getAccessColumn("t_report_sales_base");
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("isflag", "true");
//        String groupcols = "";
//        if(map.containsKey("groupcols") && null != map.get("groupcols")){
//            groupcols = (String)map.get("groupcols");
//        }else{
//            groupcols = "customerid,goodsid";
//        }
//        queryMap.put("groupcols",groupcols);
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
		pageMap.setCondition(queryMap);
		PageData pageData = salesReportService.getSalesCarReportList(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);
    }
	/**
	 * 显示商品报价单
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-3-27
	 */
	public String showSalesGoodsQuotationReportPage() throws Exception{
		List priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
		request.setAttribute("priceList", priceList);
		return SUCCESS;
	}
	/**
	 * 显示商品报价单 数据
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-3-27
	 */
	public String showSalesGoodsQuotationReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesReportService.showSalesGoodsQuotationReportData(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	
	
	/**
	 * 导出 商品报价单报表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-21
	 */
	public void exportGoodsQuotationReportData() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("ispageflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
		PageData pageData = salesReportService.showSalesGoodsQuotationReportData(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);
	}
	/**
	 * 数据转换，商品报价单报表数据 导出
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportSalesGoodsQuotationReportFilter(List<SalesCustomerPriceReport> list) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("goodsid", "商品编码");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("barcode", "条形码");
		firstMap.put("goodsmainunitname", "单位");
		firstMap.put("goodsboxnum", "箱装量");
		firstMap.put("price", "供价");
		firstMap.put("boxprice", "箱价");
		result.add(firstMap);
		
		if(null!=list && list.size() > 0){
			for(SalesCustomerPriceReport item : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(item);
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
						if("goodsmainunitname".equals(fentry.getKey()) || "goodsboxnum".equals(fentry.getKey())){
							if(map.containsKey("goodsInfo")){
								GoodsInfo goodsInfo=(GoodsInfo)map.get("goodsInfo");
								if(null!=goodsInfo){
									if("goodsmainunitname".equals(fentry.getKey())){
										retMap.put(fentry.getKey(), goodsInfo.getMainunitName());
									}else if("goodsboxnum".equals(fentry.getKey())){
										retMap.put(fentry.getKey(), goodsInfo.getBoxnum());									
									}
								}
							}
						}
					}
				}
				result.add(retMap);
			}
		}
		return result;
	}
	
	/**
	 * 销售数量列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-21
	 */
	public String showSalesQuantityReportPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	/**
	 * 按品牌销售数量列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-21
	 */
	public String showSalesBrandQuantityReportPage() throws Exception{
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		String storageid=request.getParameter("storageid");
		String storagename=request.getParameter("storagename");
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		request.setAttribute("storageid", storageid);
		request.setAttribute("storagename", storagename);
		return SUCCESS;
	}
	/**
	 * 销售数量报表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-21
	 */
	public String showSalesQuantityReportData() throws Exception{		
		Map map = CommonUtils.changeMap(request.getParameterMap());
		if(null!=map && map.containsKey("ispageflag")){
			map.remove("ispageflag");
		}
		String groupby=(String) map.get("groupby");
		if(StringUtils.isNotEmpty(groupby) && "brand".equals(groupby.trim().toLowerCase())){
			map.put("groupbybrand", "true");	
			if(null== map.get("storageid") && StringUtils.isNotEmpty(map.get("storageid").toString())){
				map.put("queryNoneData","true");
			}
		}else{
			map.put("groupbystorage", "true");
		}
		pageMap.setCondition(map);
		
		PageData pageData = salesReportService.showSalesQuantityReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	/**
	 * 导出 销售数量报表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-21
	 */
	public String exportSalesQuantityReportData() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("ispageflag", "true");
		pageMap.setCondition(map);
		String title = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		if(map.containsKey("queryNoneData")){
			map.remove("queryNoneData");
		}
		String groupby=(String) map.get("groupby");
		if(StringUtils.isNotEmpty(groupby) && "brand".equals(groupby.trim().toLowerCase())){
			map.put("groupbybrand", "true");	
			groupby="brand";
			if(null== map.get("storageid") && StringUtils.isNotEmpty(map.get("storageid").toString())){
				map.put("queryNoneData","true");
			}
		}else{
			map.put("groupbystorage", "true");
			groupby="storage";
		}
		pageMap.setCondition(map);
		PageData pageData = salesReportService.showSalesQuantityReportData(pageMap);
		ExcelUtils.exportExcel(exportSalesQuantityReportDataFilter(pageData.getList(),pageData.getFooter(),groupby), title);
		return null;
	}
	/**
	 * 数据转换，销售数量报表数据 导出
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportSalesQuantityReportDataFilter(List<SalesQuantityReport> list,List<SalesQuantityReport> footerlist,String groupby) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("storageid", "仓库编号");
		firstMap.put("storagename", "仓库名称");	
		if("brand".equals(groupby)){
			firstMap.put("brandid", "品牌编号");
			firstMap.put("brandname", "品牌名称");
		}
		firstMap.put("auxunitnum", "销售总数量");
		firstMap.put("auxunitname", "销售单位");
		firstMap.put("totalweight", "销售总重量");
		firstMap.put("totalvolume", "销售总体积");

		result.add(firstMap);
		
		if(null!=list && list.size() > 0){
			for(SalesQuantityReport item : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(item);
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
		if(null!=footerlist && footerlist.size() > 0){
			for(SalesQuantityReport item : footerlist){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(item);
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
     * 显示客户业务员 客户目标考核报表
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016-7-22
     */
    public String showSalesUserTargetReportPage() throws Exception{
        String monthday = CommonUtils.getCurrentMonthLastDay();
        String firstDay = CommonUtils.getMonthDateStr();
        request.setAttribute("firstDay", firstDay);
        request.setAttribute("monthday", monthday);
        request.setAttribute("prevyearfirstday", CommonUtils.getPrevYearFirstMonthDay());
        request.setAttribute("prevyearcurday", CommonUtils.getPrevYearCurrentMonthLastDay());
        return SUCCESS;
    }

    /**
     * 显示客户业务员 客户目标考核报表
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016-7-22
     */
    public String showSalesUserTargetReportData() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isPageflag","");
        pageMap.setCondition(map);
        PageData pageData = salesReportService.showSalesUserTargetReportData(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }
    /**
     * 显示客户业务员 客户目标考核报表
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016-7-22
     */
    public String exportSalesUserTargetReportData() throws Exception{

        Map map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("ispageflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
        PageData pageData = salesReportService.showSalesUserTargetReportData(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);

        return SUCCESS;
    }


	/**
	 * 显示品牌业务员 客户目标报表页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-4-7
	 */
	public String showSalesCustomerTargetReportPage() throws Exception{
		String monthday = CommonUtils.getCurrentMonthLastDay();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("monthday", monthday);
		request.setAttribute("prevyearfirstday", CommonUtils.getPrevYearFirstMonthDay());
		request.setAttribute("prevyearcurday", CommonUtils.getPrevYearCurrentMonthLastDay());
		return SUCCESS;
	}
	/**
	 * 显示品牌业务员 客户目标报表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-4-7
	 */
	public String showSalesCustomerTargetReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		if(null!=map ){
			if(map.containsKey("isPageflag")){
				map.remove("isPageflag");
			}
		}
		
		pageMap.setCondition(map);
		
		PageData pageData = salesReportService.showSalesCustomerTargetReportData(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	/**
	 * 保存客户目标考核报表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-4-18
	 */
	public String saveSalesCustomerTargetReport() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		String targetsList=(String)map.get("targetsList");
		String branddept=(String)map.get("branddept");
		String startdate=(String)map.get("bqstartdate");
		String enddate=(String)map.get("bqenddate");
		Map resultMap=new HashMap();
		if(null==targetsList || "".equals(targetsList.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "抱歉，未能找到考核数据");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		if(null==branddept || "".equals(branddept.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "抱歉，请填写品牌部门");	
			addJSONObject(resultMap);
			return SUCCESS;	
		}
		if(null==startdate || "".equals(startdate.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "抱歉，请填写开始时间");
			addJSONObject(resultMap);
			return SUCCESS;			
		}
		if(null==enddate || "".equals(enddate.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "抱歉，请填写结束时间");	
			addJSONObject(resultMap);
			return SUCCESS;		
		}
		boolean flag=salesReportService.saveSalesCustomerTargetReport(map);
		if(flag){
			resultMap.put("flag", flag);
			resultMap.put("msg", "保存成功");
		}else{
			resultMap.put("flag", false);
			resultMap.put("msg", "抱歉，保存失败");			
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 导出销售目标报表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-4-8
	 */
	public String exportSalesCustomerTargetReportData() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isPageflag", "true");
		pageMap.setCondition(map);
		String title = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		String branddept=(String)map.get("branddept");
		
		pageMap.setCondition(map);
		
		PageData pageData = salesReportService.showSalesCustomerTargetReportData(pageMap);
		ExcelUtils.exportExcel(exportSalesCustomerTargetReportDataFilter(pageData.getList(),branddept), title);
		return null;
	}
	/**
	 * 数据转换，销售数量报表数据 导出
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportSalesCustomerTargetReportDataFilter(List<Map> list,String branddept) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("brandusername", "业务员");
		firstMap.put("customername", "客户");
		firstMap.put("saleamount", "本期销售合计");
		firstMap.put("qqsaleamount", "前期销售合计");
		if(null!=branddept && !"".equals(branddept)){
			List<Brand> brandList=getBaseGoodsService().getBrandListWithParentByDeptid(branddept);
			if(null!=brandList && brandList.size()>0){
				for(Brand itemBrand: brandList){
					firstMap.put("targets_"+itemBrand.getId(), itemBrand.getName()+"销售目标");					
					firstMap.put("saleamount_"+itemBrand.getId(), itemBrand.getName()+"本期销售");
					firstMap.put("qqsaleamount_"+itemBrand.getId(), itemBrand.getName()+"前期销售");
					firstMap.put("nweektargets_"+itemBrand.getId(), itemBrand.getName()+"下月销售目标");
				}
			}
		}

		result.add(firstMap);
		
		if(null!=list && list.size() > 0){
			for(Map<String,Object> map : list){
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
	 * 销售毛利分析报表页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-5-10
	 */
	public String showSalesBrandGrossReportPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	/**
	 * 销售毛利分析报表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-5-10
	 */
	public String showSalesBrandGrossReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		if(null!=map ){
			if(map.containsKey("isPageflag")){
				map.remove("isPageflag");
			}
			if(map.containsKey("isShowSumTotal")){
				map.remove("isShowSumTotal");
			}
		}
		
		pageMap.setCondition(map);
		
		PageData pageData = salesReportService.showSalesBrandGrossReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	/**
	 * 导出销售毛利分析报表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-4-8
	 */
	public String exportSalesBrandGrossReportData() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isPageflag", "true");
		pageMap.setCondition(map);
		String title = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		String branddept=(String)map.get("branddept");
		
		pageMap.setCondition(map);
		
		PageData pageData = salesReportService.showSalesBrandGrossReportData(pageMap);
		ExcelUtils.exportExcel(exportSalesBrandGrossReportDataFilter(pageData.getList()), title);
		return null;
	}
	/**
	 * 导出销售毛利分析报表数据 模板
	 * @param list
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-5-10
	 */
	private List<Map<String, Object>> exportSalesBrandGrossReportDataFilter(List<Map> list) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("brandid", "品牌编码");
		firstMap.put("brandname", "品牌名称");
		firstMap.put("saleamount", "销售金额");
		firstMap.put("grossamount", "毛利冲差");
		firstMap.put("balanceamount", "费用冲差");
		firstMap.put("writeoffamount", "回笼金额");
		firstMap.put("grossamountwriteoff", "回笼毛利冲差");
		firstMap.put("balanceamountwriteoff", "回笼费用冲差");
		result.add(firstMap);
		
		if(null!=list && list.size() > 0){
			for(Map<String,Object> map : list){
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
	 * 显示厂家业务员销售报表情况页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 12, 2014
	 */
	public String showSalesSupplieruserReportPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 显示厂家业务员销售报表明细页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 12, 2014
	 */
	public String showSalesSupplieruserDetailPage()throws Exception{
		String supplieruser= request.getParameter("supplieruser");
		String supplierusername = request.getParameter("supplierusername");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("supplieruser", supplieruser);
		request.setAttribute("supplierusername", supplierusername);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return SUCCESS;
	}

	/**
	 * 显示分制单人销售情况统计表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 31, 2014
	 */
	public String showSalesAdduserReportPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}

    /**
     * 显示分制单人销售情况明细统计表页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-04-24
     */
    public String showSalesAdduserDetailPage()throws Exception{
        String jsonstr = request.getParameter("jsonstr");
        JSONObject jsonObject = JSONObject.fromObject(jsonstr);
        String adduserid = null != jsonObject.get("adduserid") ? (String)jsonObject.get("adduserid") : "";
        String addusername = null != jsonObject.get("addusername") ? (String)jsonObject.get("addusername") : "";
        String businessdate1 = null != jsonObject.get("businessdate1") ? (String)jsonObject.get("businessdate1") : "";
        String businessdate2 = null != jsonObject.get("businessdate2") ? (String)jsonObject.get("businessdate2") : "";
        String salesarea = null != jsonObject.get("salesarea") ? (String)jsonObject.get("salesarea") : "";
        String customerid = null != jsonObject.get("customerid") ? (String)jsonObject.get("customerid") : "";
        String brandid = null != jsonObject.get("brandid") ? (String)jsonObject.get("brandid") : "";
        String salesdept = null != jsonObject.get("salesdept") ? (String)jsonObject.get("salesdept") : "";

        Map map = getAccessColumn("t_report_sales_base");
        request.setAttribute("map", map);
        request.setAttribute("adduserid", adduserid);
        request.setAttribute("addusername", addusername);
        request.setAttribute("businessdate1", businessdate1);
        request.setAttribute("businessdate2", businessdate2);
        request.setAttribute("salesarea", salesarea);
        request.setAttribute("customerid", customerid);
        request.setAttribute("brandid", brandid);
        request.setAttribute("salesdept", salesdept);
        return SUCCESS;
    }
	/**
	 * 显示品牌销售考核报表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月5日
	 */
	public String showBrandAssessReportPage() throws Exception{
		request.setAttribute("year", CommonUtils.getCurrentYearStr());
		request.setAttribute("month", CommonUtils.getCurrentMonthStr());
		return SUCCESS;
	}
	/**
	 * 获取品牌销售考核报表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月5日
	 */
	public String showBrandAssessReportData() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		if(Integer.parseInt(month)<10){
			month = "0"+month;
		}
		map.put("ymonth", year+"-"+month);
		pageMap.setCondition(map);
		PageData pageData = salesReportService.showBrandAssessReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 导出品牌销售考核报表数据
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月5日
	 */
	public void exportBrandAssessReportData() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		if(Integer.parseInt(month)<10){
			month = "0"+month;
		}
		map.put("ymonth", year+"-"+month);
		pageMap.setCondition(map);
		PageData pageData = salesReportService.showBrandAssessReportData(pageMap);
		
		String title = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		List list = pageData.getList();
		list.addAll(pageData.getFooter());
		ExcelUtils.exportExcel(exportBrandAssessReportDataFilter(list), title);
	}
	/**
	 * 品牌销售考核数据导出
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月5日
	 */
	private List<Map<String, Object>> exportBrandAssessReportDataFilter(List<Map> list) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("branddeptname", "品牌部门");
		firstMap.put("brandid", "品牌编码");
		firstMap.put("brandname", "品牌名称");
		firstMap.put("salestarget", "销售目标");
		firstMap.put("salesamount", "销售金额");
		firstMap.put("salesrate", "销售完成比率%");
		firstMap.put("grossamount", "销售毛利");
		result.add(firstMap);
		
		if(null!=list && list.size() > 0){
			for(Map<String,Object> map : list){
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
	 * 显示客户新品销售情况统计表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月23日
	 */
	public String showCustomerNewGoodsReportPage() throws Exception{
		request.setAttribute("day1", CommonUtils.getMonthDateStr());
		request.setAttribute("today", CommonUtils.getTodayDataStr());
		return "success";
	}
	/**
	 * 显示客户新品销售数据列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月23日
	 */
	public String showCustomerNewGoodsReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesReportService.showCustomerNewGoodsReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 导出客户新品销售数据
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月23日
	 */
	public void exportCustomerNewGoodsReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("isPageflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
		PageData pageData = salesReportService.showCustomerNewGoodsReportData(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);
    }
	/**
	 * 客户新品导出格式
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月23日
	 */
	private List<Map<String, Object>> exportCustomerNewGoodsReportData(List<Map> list) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("businessdate", "业务日期");
		firstMap.put("customerid", "客户编号");
		firstMap.put("customername", "客户名称");
		firstMap.put("pcustomername", "总店名称");
		firstMap.put("salesareaname", "所属区域");
		firstMap.put("goodsid", "商品编码");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("brandname", "品牌名称");
		firstMap.put("taxprice", "单价");
		firstMap.put("unitnum", "数量");
		firstMap.put("auxnumdetail", "辅数量");
		firstMap.put("taxamount", "销售金额");
		result.add(firstMap);
		
		if(null!=list && list.size() > 0){
			for(Map<String,Object> map : list){
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
	 * 显示客户老品销售情况统计表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月23日
	 */
	public String showCustomerOldGoodsReportPage() throws Exception{
		request.setAttribute("day1", CommonUtils.getMonthDateStr());
		request.setAttribute("today", CommonUtils.getTodayDataStr());
		return "success";
	}
	/**
	 * 显示客户老品销售数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月23日
	 */
	public String showCustomerOldGoodsReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesReportService.showCustomerOldGoodsReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 导出客户老品销售数据
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月23日
	 */
	public void exportCustomerOldGoodsReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("isPageflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
		PageData pageData = salesReportService.showCustomerOldGoodsReportData(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);
    }
	
	/**
	 * 显示分司机销售退货情况统计报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 31, 2014
	 */
	public String showSalesDriverReportPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		SysParam sysParam = getBaseSysParamService().getSysParam("DriverReportBrands");
		if(null != sysParam){
			request.setAttribute("brandids", sysParam.getPvalue());
		}
		return SUCCESS;
	}
	
	/**
	 * 获取分司机销售退货情况统计报表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 31, 2014
	 */
	public String getSalesRejectEnterReportData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesReportService.getSalesRejectEnterReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 导出分司机销售退货情况统计报表数据
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 31, 2014
	 */
	public void exportSalesRejectEnterReportData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
		pageMap.setCondition(map);
		String title = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}else{
			title = "list";
		}
		pageMap.setCondition(map);
		PageData pageData = salesReportService.getSalesRejectEnterReportData(pageMap);
		List<BaseSalesReport> list = pageData.getList();
		list.addAll(pageData.getFooter());
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("drivername", "司机");
		firstMap.put("directreturnnum", "直退数量");
		firstMap.put("auxdirectnumdetail", "直退辅数量");
		firstMap.put("directreturnamount", "直退金额");
		firstMap.put("checkreturnnum", "退货数量");
		firstMap.put("auxchecknumdetail", "退货辅数量");
		firstMap.put("checkreturnamount", "退货金额");
		firstMap.put("checkreturnrate", "退货率%");
		firstMap.put("returnnum", "退货总数量");
		firstMap.put("auxreturnnumdetail", "退货总辅数量");
		firstMap.put("returnamount", "退货合计");
		result.add(firstMap);
		
		for(BaseSalesReport baseSalesReport : list){
			Map<String, Object> retMap = new LinkedHashMap<String, Object>();
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2 = PropertyUtils.describe(baseSalesReport);
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
	 * 显示分品牌业务员销售回笼考核报表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 21, 2015
	 */
	public String showBranduserSalesWithdrawnAssessReportPage()throws Exception{
		String today = CommonUtils.getTodayMonStr();
		request.setAttribute("today", today);
		return SUCCESS;
	}

    /**
     * 显示分客户业务员销售回笼考核报表
     * @return
     * @throws Exception
     * @author lin_xx
     * @date Jan 20, 2016
     */
    public String showCustomeruserSalesWithdrawnAssessReportPage()throws Exception{
        String today = CommonUtils.getTodayMonStr();
        request.setAttribute("today", today);
        return SUCCESS;
    }

    /**
     * 显示分客户业务员品牌销售回笼考核报表
     * @return
     * @throws Exception
     * @author lin_xx
     * @date Jan 26, 2016
     */
    public String showCustomerBrandWithdrawnAssessReportPage()throws Exception{
        String today = CommonUtils.getTodayMonStr();
        request.setAttribute("today", today);
        return SUCCESS;
    }

    /**
     * 显示分品牌业务员品牌销售回笼考核报表
     * @return
     * @throws Exception
     * @author lin_xx
     * @date Jan 26, 2016
     */
    public String showBranduserByBrandSalesWithdrawnAssessReportPage()throws Exception{
        String today = CommonUtils.getTodayMonStr();
        request.setAttribute("today", today);
        return SUCCESS;
    }


    /**
	 * 显示分品牌销售回笼考核报表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 23, 2015
	 */
	public String showBrandSalesWithdrawnAssessReportPage()throws Exception{
		String today = CommonUtils.getTodayMonStr();
		request.setAttribute("today", today);
		return SUCCESS;
	}

	/**
	 * 显示分客户分类销售回笼考核报表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-07-20
	 */
	public String showCustomersortSalesWithdrawnAssessReportPage()throws Exception{
		String today = CommonUtils.getTodayMonStr();
		request.setAttribute("today", today);
		return SUCCESS;
	}

	/**
	 * 显示分销售部门品牌销售回笼考核报表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2017-01-09
	 */
	public String showSalesdeptBrandWithdrawnAssessReportPage()throws Exception{
		String today = CommonUtils.getTodayMonStr();
		request.setAttribute("today", today);
		return SUCCESS;
	}

	/**
	 * 获取销售回笼考核数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 21, 2015
	 */
	public String getSalesWithdrawnAssessData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesReportService.getSalesWithdrawnAssessData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 获取销售回笼考核数据数据
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 24, 2015
	 */
	public void exportSalesWithdrawnAssessData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
		pageMap.setCondition(map);
		String title = "",groupcols = "",customer ="";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}else{
			title = "list";
		}
		if(map.containsKey("groupcols")){
			groupcols = map.get("groupcols").toString();
		}

		pageMap.setCondition(map);
		PageData pageData = salesReportService.getSalesWithdrawnAssessData(pageMap);
		List<Map> list = pageData.getList();
		list.addAll(pageData.getFooter());
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		if(map.containsKey("user")){
			firstMap.put("brandusername", "品牌业务员");
			firstMap.put("branddeptname", "公司");
            firstMap = exportFirstRow(firstMap);

		}else if(map.containsKey("personbranduser")){
            firstMap.put("brandusername", "品牌业务员");
            firstMap.put("brandname", "品牌");
            firstMap = exportFirstRow1(firstMap);
        }else if("brandid".equals(groupcols)){
			firstMap.put("brandname", "品牌");
            firstMap = exportFirstRow(firstMap);
		}else if("salesuser".equals(groupcols)){
            firstMap.put("salesusername", "客户业务员");
            if(map.containsKey("customerbrand")){
                firstMap.put("brandname", "品牌");
				firstMap.put("begindate", "日期");
                firstMap = exportFirstRow1(firstMap);
            }else{
                firstMap.put("salesdeptname", "公司");
                firstMap = exportFirstRow(firstMap);
            }

        }else if("salesdept,brandid".equals(groupcols)){
			firstMap.put("salesdeptname", "销售部门");
			firstMap.put("brandname", "品牌");
			firstMap.put("begindate", "日期");
			firstMap = exportFirstRow1(firstMap);
		}else if("customersort".equals(groupcols)){
			firstMap.put("customersortname", "客户分类");
			firstMap = exportFirstRow(firstMap);
		}
		result.add(firstMap);
		
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
     * 销售考核报表中报表导出时销售目标放前面
     * @param firstMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> exportFirstRow(Map<String, Object> firstMap) throws Exception {

        firstMap.put("saletargetamount", "销售目标");
        firstMap.put("saleamount", "销售金额");
        firstMap.put("saledonerate", "销售完成率%");
        firstMap.put("salemarginamount", "销售毛利额");
        firstMap.put("marginratetarget", "毛利率目标%");
        firstMap.put("realrate", "实际毛利率%");
        firstMap.put("salemargindonesurpassrate", "毛利完成率%");
        firstMap.put("withdrawntargetamount", "回笼目标");
        firstMap.put("withdrawnamount", "回笼金额");
        firstMap.put("withdrawndonerate", "回笼完成率%");
        firstMap.put("writeoffmarginamount", "回笼毛利额");
        firstMap.put("writeoffratetarget", "回笼毛利率目标%");
        firstMap.put("writeoffrate", "回笼毛利率%");
        firstMap.put("withdrawnmargindonesurpassrate", "回笼毛利完成率%");

        return firstMap ;

    }

    /**
     * 销售考核报表中报表导出时回笼目标放前面
     * @param firstMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> exportFirstRow1(Map<String, Object> firstMap) throws Exception {

        firstMap.put("withdrawntargetamount", "回笼目标");
        firstMap.put("withdrawnamount", "回笼金额");
        firstMap.put("withdrawndonerate", "回笼完成率%");
        firstMap.put("writeoffmarginamount", "回笼毛利额");
        firstMap.put("writeoffratetarget", "回笼毛利率目标%");
        firstMap.put("writeoffrate", "回笼毛利率%");
        firstMap.put("withdrawnmargindonesurpassrate", "回笼毛利完成率%");

        firstMap.put("saletargetamount", "销售目标");
        firstMap.put("saleamount", "销售金额");
        firstMap.put("saledonerate", "销售完成率%");
        firstMap.put("salemarginamount", "销售毛利额");
        firstMap.put("marginratetarget", "毛利率目标%");
        firstMap.put("realrate", "实际毛利率%");
        firstMap.put("salemargindonesurpassrate", "毛利完成率%");

        return firstMap ;

    }

    /**
     * 导出按司机分商品分客户情况明细表
     * @throws Exception
     * @author panxiaoxiao
     * @date Dec 10, 2013
     */
    public void exportSalesRejectEnterDetailReportData()throws Exception{
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isflag", "true");
        if(map.containsKey("driverid") && "0".equals(map.get("driverid"))){
            map.put("driveridepmty", "");
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
        Map<String,List<Map<String, Object>>> retMap = new HashMap<String, List<Map<String,Object>>>();
        String tnjson = "{",str = "";
        if(map.containsKey("groupcols")){
            String groupcols = (String)map.get("groupcols");
            if(groupcols.indexOf(";") != -1){
                String[] groupcolArr = groupcols.split(";");
                for(String group : groupcolArr){
                    String str1 = "sales",str2 = "";
                    List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
                    pageMap.getCondition().put("groupcols", group);
                    Map<String, Object> firstMap = getSalesRejectEnterDetailReportFirstMap(group);
                    result.add(firstMap);
                    PageData pageData = salesReportService.getSalesRejectEnterReportData(pageMap);
                    List<Map<String, Object>> list = objectToMapList(pageData,firstMap);
                    result.addAll(list);
                    if(group.indexOf(",") == -1){
                        if("goodsid".equals(group)){
                            str1 += "goods";
                            str2 += "分商品统计";
                        }else if("customerid".equals(group)){
                            str1 += "customer";
                            str2 += "分客户统计";
                        }
                        str += "'"+str1+"':'"+str2+"',";
                    }else{
                        for(String group2 : group.split(",")){
                            if("goodsid".equals(group2)){
                                str1 += "goods";
                                str2 += "分商品";
                            }else if("customerid".equals(group2)){
                                str1 += "customer";
                                str2 += "分客户";
                            }
                        }
                        str += "'"+str1+"':'"+str2+"统计',";
                    }
                    retMap.put(""+str1+"_list", result);
                }
            }
            else{
                if("goodsid".equals(groupcols)){
                    str += "'salesgoods':'分商品统计',";
                }else if("customerid".equals(groupcols)){
                    str += "'salescustomer':'分客户统计',";
                }
            }
            if(str.lastIndexOf(",") != -1){
                tnjson += str.substring(0, str.lastIndexOf(",")) + "}";
            }
            else{
                tnjson += str + "}";
            }
        }
        //String tnjson = "{'salesBrand':'分品牌统计','salesGoods':'分商品统计'}";
        ExcelUtils.exportMoreExcel(retMap, title,tnjson);
    }

    private Map<String, Object> getSalesRejectEnterDetailReportFirstMap(String groupcols)throws Exception{
        Map map3 = getAccessColumn("t_report_sales_base");
        Map<String,Object> map2 = new LinkedHashMap<String, Object>();
        boolean otherflag = true;
        if(StringUtils.isNotEmpty(groupcols)){
            if(groupcols.indexOf(",") == -1){
                if("customerid".equals(groupcols)){
                    map2.put("customerid", "客户编码");
                    map2.put("customername", "客户名称");
                }
                else if("goodsid".equals(groupcols)){
                    map2.put("goodsid", "商品编码");
                    map2.put("goodsname", "商品名称");
                }
            }
            else{
                String[] groupcolsArr = groupcols.split(",");
                for(String  group: groupcolsArr){
                    if("customerid".equals(group)){
                        map2.put("customerid", "客户编码");
                        map2.put("customername", "客户名称");
                    }
                    else if("goodsid".equals(group)){
                        map2.put("goodsid", "商品编码");
                        map2.put("goodsname", "商品名称");
                    }
                }
            }
            map2.put("directreturnnum", "直退数量");
            map2.put("auxdirectnumdetail", "直退辅数量");
            map2.put("directreturntotalbox", "直退箱数");
            map2.put("directreturnamount", "直退金额");
            map2.put("checkreturnnum", "退货数量");
            map2.put("auxchecknumdetail", "退货辅数量");
            map2.put("checkreturntotalbox", "退货箱数");
            map2.put("checkreturnamount", "退货金额");
            map2.put("returnnum", "退货总数量");
            map2.put("auxreturnnumdetail", "退货总辅数量");
            map2.put("returntotalbox", "退货总箱数");
            map2.put("returnamount", "退货合计");
        }
        return map2;
    }

    /**
     * 显示客户汇总统计报表
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-10-13
     */
    public String showCollectCustomerReportPage()throws Exception{
        String today = CommonUtils.getTodayDataStr();
        Map map = getAccessColumn("t_report_sales_base");
        request.setAttribute("map", map);
        request.setAttribute("today", today);
        return SUCCESS;
    }

    /**
     * 获取客户汇总统计报表数据
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-10-13
     */
    public String showCollectReportData()throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = salesReportService.showCollectReportData(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 导出客户汇总统计报表数据
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-10-14
     */
    public void exportCollectReportData()throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isflag", "true");
        pageMap.setCondition(map);
        String title = "",groupcols = "";
        if(map.containsKey("excelTitle")){
            title = map.get("excelTitle").toString();
        }else{
            title = "list";
        }
        if(map.containsKey("groupcols")){
            groupcols = map.get("groupcols").toString();
        }
        pageMap.setCondition(map);
        PageData pageData = salesReportService.showCollectReportData(pageMap);
        List<Map> list = pageData.getList();
        list.addAll(pageData.getFooter());

        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("customerid", "客户编号");
        firstMap.put("customername", "客户名称");
        firstMap.put("pcustomerid", "总店编码");
        firstMap.put("pcustomername", "总店名称");
        firstMap.put("salesusername", "客户业务员");
        firstMap.put("customersortname", "客户分类");
        firstMap.put("salesareaname", "销售区域");
        firstMap.put("salesdeptname", "销售部门");
        firstMap.put("sendamount", "出库金额");
        firstMap.put("sendnotaxamount", "出库未税金额");
        firstMap.put("pushbalanceamount", "冲差金额");
        firstMap.put("returnamount", "退货合计");
        firstMap.put("saleamount", "销售金额");
        firstMap.put("salenotaxamount", "销售无税金额");
        firstMap.put("saletax", "销售税额");
        firstMap.put("costamount", "成本金额");
        firstMap.put("salemarginamount", "毛利额");
        firstMap.put("realrate", "毛利率%");
        firstMap.put("withdrawnamount", "回笼金额");
		firstMap.put("hidallunwithdrawnamount", "历史应收金额");
        firstMap.put("allunwithdrawnamount", "应收金额");
        firstMap.put("customeramount", "客户余额");
		firstMap.put("balanceamount", "结余金额");
        result.add(firstMap);

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
     * 显示客户未销售查询报表页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-12-11
     */
    public String showCustomerUnsaleQueryReportPage()throws Exception{
        String date = CommonUtils.getCurrentDateLastMonDate();
        request.setAttribute("date", date);
        String today = CommonUtils.getTodayDataStr();
        request.setAttribute("today", today);
        return SUCCESS;
    }

    /**
     * 获取客户未销售查询报表
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-12-11
     */
    public String showCustomerUnsaleQueryReportData()throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = salesReportService.getCustomerUnsaleQueryReportList(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 导出客户未销售查询报表
     * @throws Exception
     */
    public void exportCustomerUnsaleQueryReportData()throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isflag", "true");
        pageMap.setCondition(map);
        String title = "",groupcols = "";
        if(map.containsKey("excelTitle")){
            title = map.get("excelTitle").toString();
        }else{
            title = "list";
        }
        pageMap.setCondition(map);
        PageData pageData = salesReportService.getCustomerUnsaleQueryReportList(pageMap);
        List<Map> list = pageData.getList();
        list.addAll(pageData.getFooter());

        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("customerid", "客户编号");
        firstMap.put("customername", "客户名称");
        firstMap.put("salesareaname", "销售区域");
        firstMap.put("customersortname", "客户分类");
        firstMap.put("salesusername", "客户业务员");
        firstMap.put("contact", "客户联系人");
        firstMap.put("mobile", "联系电话");
        firstMap.put("demandamount", "要货金额");
        firstMap.put("orderamount", "订单金额");
        firstMap.put("saleoutamount", "发货单金额");
        firstMap.put("saleamount", "销售金额");
        result.add(firstMap);

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
     * 分月汇总销售报表
     * @return
     * @author huangzhiqian
     * @throws Exception 
     * @date 2016年1月12日
     */
    public String showSalesMonthGroupListPage() throws Exception{
    	request.setAttribute("current", new SimpleDateFormat("yyyy").format(new Date()));
  		List goodstypeList = getBaseSysCodeService().showSysCodeListByType("goodstype");
  		request.setAttribute("goodstypeList", goodstypeList);
    	return SUCCESS;
    }
    
    /**
     * 分月汇总销售报表数据
     * @return
     * @throws Exception
     * @author huangzhiqian
     * @date 2016年1月12日
     */
    public String showsalesMonthGroupData() throws Exception{
    	
	    	Map map = CommonUtils.changeMap(request.getParameterMap());
	    	pageMap.setCondition(map);
	    	PageData pageData = salesReportService.showMonthSaleData(pageMap);
	    	addJSONObjectWithFooter(pageData);
    	
	    	return SUCCESS;
    }
    
    
    
    /**
     * 分月汇总销售报表数据
     * @throws Exception
     * @author huangzhiqian
     * @date 2016年1月18日
     */
	public void exportMonthSalesReportData()throws Exception{
		String groupcols = null,exporttype = null;
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("limit", "false");//不用limit
		
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
		PageData pageData = null;
		
		pageData = salesReportService.showMonthSaleData(pageMap);
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		//根据定义类型获取显示的字段
		firstMap = getFirstMapForMonth(groupcols,exporttype,map);
		result.add(firstMap);
		List<Map<String, Object>> list = toMapList(pageData,firstMap);
		result.addAll(list);
		
		ExcelUtils.exportExcel(result, title);
	}
    
    
	private List<Map<String, Object>> toMapList(PageData pageData,Map<String, Object> firstMap)throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		if(pageData.getList().size() != 0){
			for(SaleMonthReport baseSalesReport : new ArrayList<SaleMonthReport>(pageData.getList())){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(baseSalesReport);
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
		if(pageData.getFooter().size() != 0){
			for(SaleMonthReport baseSalesReport : new ArrayList<SaleMonthReport>(pageData.getFooter())){
				if(null != baseSalesReport){
					Map<String, Object> retMap = new LinkedHashMap<String, Object>();
					Map<String, Object> map = new HashMap<String, Object>();
					map = PropertyUtils.describe(baseSalesReport);
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
		}
		return result;
	}

	private Map<String, Object> getFirstMapForMonth(String groupcols,String exporttype,Map<String,String> map)throws Exception{
		Map map3 = getAccessColumn("t_report_sales_base");
		Map<String,Object> map2 = new LinkedHashMap<String, Object>();
		if(StringUtils.isNotEmpty(groupcols)){
			if(groupcols.indexOf(",") == -1){
				if("customerid".equals(groupcols)){
					map2.put("customerid", "客户编码");
					map2.put("customername", "客户名称");
					map2.put("pcustomerid", "总店编码");
					map2.put("pcustomername", "总店名称");
					map2.put("customersortname", "客户分类");
				}
				else if("pcustomerid".equals(groupcols)){
					map2.put("pcustomerid", "总店编码");
					map2.put("pcustomername", "总店名称");
				}
				else if("salesuser".equals(groupcols)){
					map2.put("salesusername", "客户业务员");
				}else if("customersort".equals(groupcols)){
					map2.put("customersortname", "客户分类");
				}
				else if("salesarea".equals(groupcols)){
					map2.put("salesareaname", "销售区域");
				}
				else if("salesdept".equals(groupcols)){
					map2.put("salesdeptname", "销售部门");
				}
				else if("goodsid".equals(groupcols)){
					map2.put("goodsid", "商品编码");
					map2.put("goodsname", "商品名称");
					map2.put("goodssortname", "商品分类");
					map2.put("goodstypename", "商品类型");
					map2.put("barcode", "条形码");
                    map2.put("price","单价");
					if(!map.containsKey("brandid")){
						map2.put("brandname", "品牌名称");
					}
					map2.put("unitname", "主单位");
					map2.put("auxunitname", "辅单位");
				}
				else if("goodssort".equals(groupcols)){
					map2.put("goodssortname", "商品分类");
				}
				else if("brandid".equals(groupcols)){
					map2.put("brandname", "品牌名称");
				}
				else if("branduser".equals(groupcols)){
					map2.put("brandusername", "品牌业务员");
					map2.put("branddeptname", "分公司");
				}
				else if("branddept".equals(groupcols)){
					map2.put("branddeptname", "品牌部门");
				}else if("supplierid".equals(groupcols)){
					map2.put("suppliername", "供应商名称");
				}else if("supplieruser".equals(groupcols)){
					map2.put("supplierusername", "厂家业务员");
				}else if("adduserid".equals(groupcols)){
					map2.put("addusername", "制单人");
				}
			}else{
				String[] groupcolsArr = groupcols.split(",");
				for(String  group: groupcolsArr){
					if("customerid".equals(group)){
						map2.put("customerid", "客户编码");
						map2.put("customername", "客户名称");
						map2.put("pcustomerid", "总店编码");
						map2.put("pcustomername", "总店名称");
						map2.put("customersortname", "客户分类");
					}
					else if("pcustomerid".equals(group)){
						map2.put("pcustomerid", "总店编码");
						map2.put("pcustomername", "总店名称");
					}
					else if("salesuser".equals(group)){
						map2.put("salesusername", "客户业务员");
					}else if("customersort".equals(groupcols)){
						map2.put("customersortname", "客户分类");
					}
					else if("salesarea".equals(group)){
						map2.put("salesareaname", "销售区域");
					}
					else if("salesdept".equals(group)){
						map2.put("salesdeptname", "销售部门");
					}
					else if("goodsid".equals(group)){
						map2.put("goodsid", "商品编码");
						map2.put("goodsname", "商品名称");
						map2.put("goodssortname", "商品分类");
						map2.put("goodstypename", "商品类型");
						map2.put("barcode", "条形码");
                        map2.put("price","单价");
						if(!map.containsKey("brandid")){
							map2.put("brandname", "品牌名称");
						}
						map2.put("unitname", "主单位");
						map2.put("auxunitname", "辅单位");
					}
					else if("goodssort".equals(group)){
						map2.put("goodssortname", "商品分类");
					}
					else if("brandid".equals(group)){
						map2.put("brandname", "品牌名称");
					}
					else if("branduser".equals(group)){
						map2.put("brandusername", "品牌业务员");
					}
					else if("branddept".equals(group)){
						map2.put("branddeptname", "品牌部门");
					}else if("supplierid".equals(group)){
						map2.put("suppliername", "供应商名称");
					}else if("supplieruser".equals(group)){
						map2.put("supplierusername", "厂家业务员");
					}else if("adduserid".equals(groupcols)){
						map2.put("addusername", "制单人");
					}
				}
			}
		}else{
			map2.put("customerid", "客户编码");
			map2.put("customername", "客户名称");
			map2.put("pcustomerid", "总店编码");
			map2.put("pcustomername", "总店名称");
		}
		map2.put("saleboxnum01", "销售箱数(1月)");
		map2.put("saleamount01", "销售额(1月)");
		map2.put("realsaleamount01", "销售毛利额(1月)");
		map2.put("realrate01", "毛利率(1月)");
		map2.put("saleboxnum02", "销售箱数(2月)");
		map2.put("saleamount02", "销售额(2月)");
		map2.put("realsaleamount02", "销售毛利额(2月)");
		map2.put("realrate02", "毛利率(2月)");
		map2.put("saleboxnum03", "销售箱数(3月)");
		map2.put("saleamount03", "销售额(3月)");
		map2.put("realsaleamount03", "销售毛利额(3月)");
		map2.put("realrate03", "毛利率(3月)");
		map2.put("saleboxnum04", "销售箱数(4月)");
		map2.put("saleamount04", "销售额(4月)");
		map2.put("realsaleamount04", "销售毛利额(4月)");
		map2.put("realrate04", "毛利率(4月)");
		map2.put("saleboxnum05", "销售箱数(5月)");
		map2.put("saleamount05", "销售额(5月)");
		map2.put("realsaleamount05", "销售毛利额(5月)");
		map2.put("realrate05", "毛利率(5月)");
		map2.put("saleboxnum06", "销售箱数(6月)");
		map2.put("saleamount06", "销售额(6月)");
		map2.put("realsaleamount06", "销售毛利额(6月)");
		map2.put("realrate06", "毛利率(6月)");
		map2.put("saleboxnum07", "销售箱数(7月)");
		map2.put("saleamount07", "销售额(7月)");
		map2.put("realsaleamount07", "销售毛利额(7月)");
		map2.put("realrate07", "毛利率(7月)");
		map2.put("saleboxnum08", "销售箱数(8月)");
		map2.put("saleamount08", "销售额(8月)");
		map2.put("realsaleamount08", "销售毛利额(8月)");
		map2.put("realrate08", "毛利率(8月)");
		map2.put("saleboxnum09", "销售箱数(9月)");
		map2.put("saleamount09", "销售额(9月)");
		map2.put("realsaleamount09", "销售毛利额(9月)");
		map2.put("realrate09", "毛利率(9月)");
		map2.put("saleboxnum10", "销售箱数(10月)");
		map2.put("saleamount10", "销售额(10月)");
		map2.put("realsaleamount10", "销售毛利额(10月)");
		map2.put("realrate10", "毛利率(10月)");
		map2.put("saleboxnum11", "销售箱数(11月)");
		map2.put("saleamount11", "销售额(11月)");
		map2.put("realsaleamount11", "销售毛利额(11月)");
		map2.put("realrate11", "毛利率(11月)");
		map2.put("saleboxnum12", "销售箱数(12月)");
		map2.put("saleamount12", "销售额(12月)");
		map2.put("realsaleamount12", "销售毛利额(12月)");
		map2.put("realrate12", "毛利率(12月)");
		return map2;
	}

    /**
     * 显示赠品明细报表
     * @author lin_xx
     * @date 2016-8-16
     * @return
     * @throws Exception
     */
    public String showSalesPresentReportPage() throws Exception {
        String today = CommonUtils.getTodayDataStr();
        String firstDay = CommonUtils.getMonthDateStr();
        Map map = getAccessColumn("t_report_sales_base");
        request.setAttribute("map", map);
        request.setAttribute("firstDay", firstDay);
        request.setAttribute("today", today);
        return SUCCESS ;
    }
    /**
     * 显示赠品明细报表
     * @author lin_xx
     * @date 2016-8-16
     * @return
     * @throws Exception
     */
    public String getSalesPresentReportData() throws Exception {

        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = salesReportService.getSalesPresentReportData(pageMap);
        addJSONObjectWithFooter(pageData);

        return SUCCESS;
    }

	/**
	 * 打印赠品明细报表
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Feb 09, 2018
	 */
    public String printSalesPresentReportData() throws Exception {
		String strPageName=request.getParameter("strPageName");
		String intOrient=request.getParameter("intOrient");
		request.setAttribute("strPageName",strPageName);
		request.setAttribute("intOrient",intOrient);
		Integer rownum=Integer.parseInt(request.getParameter("rownum"));
		String title=request.getParameter("title");
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		Map queryMap = new HashMap();
		queryMap.put("isflag", "true");
		String query = map.get("param");
		JSONObject object = JSONObject.fromObject(query);
		for (Object k : object.keySet()) {
			Object v = object.get(k);
			if (StringUtils.isNotEmpty((String) v)) {
				queryMap.put(k.toString(), v);
			}
		}
		pageMap.setCondition(queryMap);
		PageData pageData = salesReportService.getSalesPresentReportData(pageMap);
		List list = pageData.getList();
		if (null != pageData.getFooter()) {
			List footer = pageData.getFooter();
			list.addAll(footer);
		}
		if (StringUtils.isEmpty(title))
			title = "赠品明细报表";
		request.setAttribute("title", title);
		request.setAttribute("body", ExcelUtils.dataToHTML(map, list, rownum));
		return SUCCESS;
	}

    public void exportSalesPresentReportData() throws Exception {
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
        PageData pageData = salesReportService.getSalesPresentReportData(pageMap);

        List<BaseSalesReport> list = pageData.getList();
        if(null != pageData.getFooter()){
            List<BaseSalesReport> footer = pageData.getFooter();
            list.addAll(footer);
        }
        setNodataToSpaceData(list);
        ExcelUtils.exportAnalysExcel(map,list);

    }

    /**
     * 销售退货统计报表
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 15, 2015
     */
    public String salesRejectReportPage() throws Exception {

        request.setAttribute("rejectCategory", getBaseSysCodeService().showSysCodeListByType("rejectCategory"));
        return SUCCESS;
    }

    /**
     * 查询销售退货统计报表数据
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 15, 2015
     */
    public String getSalesRejectReportListData() throws Exception {

        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);

        PageData data = salesReportService.getSalesRejectReportListData(pageMap);
        addJSONObjectWithFooter(data);
        return SUCCESS;
    }

	/**
	 * 销售退货统计表打印
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Feb 09, 2018
	 */
    public String printSalesRejectReportListData() throws Exception {
		String strPageName=request.getParameter("strPageName");
		String intOrient=request.getParameter("intOrient");
		request.setAttribute("strPageName",strPageName);
		request.setAttribute("intOrient",intOrient);
		Integer rownum=Integer.parseInt(request.getParameter("rownum"));
		String title=request.getParameter("title");
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		Map queryMap = new HashMap();
		queryMap.put("isflag", "true");
		String query = map.get("param");
		JSONObject object = JSONObject.fromObject(query);
		for (Object k : object.keySet()) {
			Object v = object.get(k);
			if (StringUtils.isNotEmpty((String) v)) {
				queryMap.put(k.toString(), v);
			}
		}
		pageMap.setCondition(queryMap);
		PageData pageData = salesReportService.getSalesRejectReportListData(pageMap);
		List list = pageData.getList();
		if (null != pageData.getFooter()) {
			List footer = pageData.getFooter();
			list.addAll(footer);
		}
		if (StringUtils.isEmpty(title))
			title = "销售退货统计表";
		request.setAttribute("title", title);
		request.setAttribute("body", ExcelUtils.dataToHTML(map, list, rownum));
		return SUCCESS;
	}

    /**
     * 导出销售退货统计报表数据
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 16, 2015
     */
    public void exportSalesRejectReportListData() throws Exception {

        Map map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();

        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(),  v);
            }
        }

        pageMap.setCondition(queryMap);
        pageMap.setRows(99999999);

        PageData data = salesReportService.getSalesRejectReportListData(pageMap);

        List<Map> list = data.getList();
        if(null != data.getFooter()){
            List<Map> footer = data.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map, list);
    }

	/**
	 * 显示促销活动统计报表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-09-06
	 */
	public String showSalesPromotionReportPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}

	/**
	 * 获取促销活动统计报表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-09-06
	 */
	public String getSalesPromotionReportData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesReportService.getSalesPromotionReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}

	/**
	 * 打印促销活动统计报表
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Feb 09, 2018
	 */
	public String printSalesPromotionReportData() throws Exception {
		String strPageName=request.getParameter("strPageName");
		String intOrient=request.getParameter("intOrient");
		request.setAttribute("strPageName",strPageName);
		request.setAttribute("intOrient",intOrient);
		Integer rownum=Integer.parseInt(request.getParameter("rownum"));
		String title=request.getParameter("title");
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		Map queryMap = new HashMap();
		queryMap.put("isflag", "true");
		String query = map.get("param");
		JSONObject object = JSONObject.fromObject(query);
		for (Object k : object.keySet()) {
			Object v = object.get(k);
			if (StringUtils.isNotEmpty((String) v)) {
				queryMap.put(k.toString(), v);
			}
		}
		pageMap.setCondition(queryMap);
		PageData pageData = salesReportService.getSalesPromotionReportData(pageMap);
		List list = pageData.getList();
		if (null != pageData.getFooter()) {
			List footer = pageData.getFooter();
			list.addAll(footer);
		}
		if (StringUtils.isEmpty(title))
			title = "促销活动统计报表";
		request.setAttribute("title", title);
		request.setAttribute("body", ExcelUtils.dataToHTML(map, list, rownum));
		return SUCCESS;
	}

	/**
	 * 导出促销活动统计报表
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-09-07
	 */
	public void exportSalesPromotionReportData()throws Exception{
		String groupcols = null,exporttype = null;
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		Map queryMap = new HashMap();
		if(map.containsKey("groupcols") && null != map.get("groupcols")){
			groupcols = (String)map.get("groupcols");
			groupcols += ",ptype";
			queryMap.put("groupcols",groupcols);
		}
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
		PageData pageData = salesReportService.getSalesPromotionReportData(pageMap);;
		List<Map> list = pageData.getList();
		if(null != pageData.getFooter()){
			List<Map> footer = pageData.getFooter();
			list.addAll(footer);
		}
		ExcelUtils.exportAnalysExcel(map,list);
	}

	/**
	 * 显示档期活动折让统计表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-11-09
	 */
	public String showSalesScheduleActivityDiscountReportPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}

	/**
	 * 获取档期活动折让统计表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-11-09
	 */
	public String getSalesScheduleActivityDiscountReportData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesReportService.getSalesScheduleActivityDiscountReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}

	/**
	 * 打印档期活动折让统计报表
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Feb 09, 2018
	 */
	public String printSalesScheduleActivityDiscountReportData() throws Exception {
		String strPageName=request.getParameter("strPageName");
		String intOrient=request.getParameter("intOrient");
		request.setAttribute("strPageName",strPageName);
		request.setAttribute("intOrient",intOrient);
		Integer rownum=Integer.parseInt(request.getParameter("rownum"));
		String title=request.getParameter("title");
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		Map queryMap = new HashMap();
		queryMap.put("isflag", "true");
		String query = map.get("param");
		JSONObject object = JSONObject.fromObject(query);
		for (Object k : object.keySet()) {
			Object v = object.get(k);
			if (StringUtils.isNotEmpty((String) v)) {
				queryMap.put(k.toString(), v);
			}
		}
		pageMap.setCondition(queryMap);
		PageData pageData = salesReportService.getSalesScheduleActivityDiscountReportData(pageMap);
		List list = pageData.getList();
		if (null != pageData.getFooter()) {
			List footer = pageData.getFooter();
			list.addAll(footer);
		}
		if (StringUtils.isEmpty(title))
			title = "档期活动折让统计报表";
		request.setAttribute("title", title);
		request.setAttribute("body", ExcelUtils.dataToHTML(map, list, rownum));
		return SUCCESS;
	}

	/**
	 * 导出档期活动折让统计表
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-11-09
	 */
	public void exportSalesScheduleActivityDiscountReportData()throws Exception{
		String groupcols = null,exporttype = null;
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
		PageData pageData = salesReportService.getSalesScheduleActivityDiscountReportData(pageMap);;
		List<Map> list = pageData.getList();
		if(null != pageData.getFooter()){
			List<Map> footer = pageData.getFooter();
			list.addAll(footer);
		}
		ExcelUtils.exportAnalysExcel(map,list);
	}

	/**
	 * 销售退货统计报表
	 *
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Apr 28, 2017
	 */
	public String salesBillStatementPage() throws Exception {
		return SUCCESS;
	}

	/**
	 * 查询对账单分页数据
	 *
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Apr 28, 2017
	 */
	public String getSalesBillStatementPageData() throws Exception {

		Map condition = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(condition);

		PageData pageData = salesReportService.getSalesBillStatementPageData(pageMap);
		addJSONObjectWithFooter(pageData);

		return SUCCESS;
	}

	/**
	 * 对账单明细页面
	 *
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Apr 28, 2017
	 */
	public String salesBillStatementDetailPage() throws Exception {
		return SUCCESS;
	}

	/**
	 * 查询对账单明细分页数据
	 *
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date May 2, 2016
	 */
	public String getSalesBillStatementDetailPageData() throws Exception {

		Map condition = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(condition);
		PageData pageData = salesReportService.getSalesBillStatementDetailPageData(pageMap);

		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}

	/**
	 * 导出客户对账单汇总报表
	 *
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date May 2, 2017
	 */
	public void exportSalesBillStatementData() throws Exception {

		Map map = CommonUtils.changeMap(request.getParameterMap());
		Map queryMap = new HashMap();

		String query = (String) map.get("param");
		JSONObject object = JSONObject.fromObject(query);
		for (Object k : object.keySet()) {
			Object v = object.get(k);
			if(StringUtils.isNotEmpty((String) v)){
				queryMap.put(k.toString(),  v);
			}
		}

		pageMap.setCondition(queryMap);
		pageMap.setRows(99999999);

		PageData data = salesReportService.getSalesBillStatementPageData(pageMap);

		List<Map> list = data.getList();
		if(null != data.getFooter()){
			List<Map> footer = data.getFooter();
			list.addAll(footer);
		}
		ExcelUtils.exportAnalysExcel(map, list);
	}

	/**
	 * 导出客户对账单汇总明细
	 *
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date May 2, 2017
	 */
	public void exportSalesBillStatementDetailData() throws Exception {

		Map map = CommonUtils.changeMap(request.getParameterMap());
		Map queryMap = new HashMap();

		String query = (String) map.get("param");
		JSONObject object = JSONObject.fromObject(query);
		for (Object k : object.keySet()) {
			Object v = object.get(k);
			if(StringUtils.isNotEmpty((String) v)){
				queryMap.put(k.toString(),  v);
			}
		}

		pageMap.setCondition(queryMap);
		pageMap.setRows(99999999);

		PageData data = salesReportService.getSalesBillStatementDetailPageData(pageMap);

		List<Map> list = data.getList();
		if(null != data.getFooter()){
			List<Map> footer = data.getFooter();
			list.addAll(footer);
		}
		ExcelUtils.exportAnalysExcel(map, list);
	}

	/**
	 * 显示厂商毛利率统计表
	 *
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2017-5-11
	 */
	public String showSalesSupplierGrossReportPage() throws Exception {
		return SUCCESS;
	}

	/**
	 * 获取厂商毛利率统计表数据
	 *
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2017-5-11
	 */
	public String showSalesSuppliserGrossReportData() throws Exception {

		Map condition = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(condition);
		PageData pageData = salesReportService.showSalesSuppliserGrossReportData(pageMap);

		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}

	/**
	 * 导出厂商毛利率统计表数据
	 *
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2017-5-11
	 */
	public void exportSupplierGrossData()throws Exception{
		String groupcols = null,exporttype = null;
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		Map queryMap = new HashMap();
		if(map.containsKey("groupcols") && null != map.get("groupcols")){
			groupcols = (String)map.get("groupcols");
			queryMap.put("groupcols",groupcols);
		}
		if(map.containsKey("exporttype") && null != map.get("exporttype")){
			exporttype = (String)map.get("exporttype");
		}else{
			exporttype = "base";
		}
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
		PageData pageData = null;
		if("base".equals(exporttype)){
			pageData = salesReportService.showSalesSuppliserGrossReportData(pageMap);
		}else if("finance".equals(exporttype)){
			pageData = salesReportService.showSalesSuppliserGrossReportData(pageMap);
		}
		List<Map> list = pageData.getList();
		if(null != pageData.getFooter()){
			List<Map> footer = pageData.getFooter();
			list.addAll(footer);
		}
//		setNodataToSpaceData(list);
		ExcelUtils.exportAnalysExcel(map,list);

	}
	/**
	 * 销售订货单报表，用来查询订货单数据
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Jan 10, 2018
	 */
	public String showOrderGoodsReportPage() {
		return SUCCESS;
	}

	/**
	 * 显示销售订货单报表数据
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Jan 10, 2018
	 */
	public String showOrderGoodsReportData() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData=salesReportService.getOrderGoodsReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}

	/**
	 * 导出订货单报表
	 * @param
	 * @return void
	 * @throws
	 * @author luoqiang
	 * @date Jan 12, 2018
	 */
	public void exportOrderGoodsReportData() throws Exception{

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
		String title = "";
		if(map.containsKey("exportname")){
			title = map.get("exportname").toString();
		}
		else{
			title = "list";
		}
		if(org.apache.commons.lang3.StringUtils.isEmpty(title)){
			title = "list";
		}

		List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("orderid", "单据编码");
		firstMap.put("businessdate", "业务日期");
		firstMap.put("customerid", "客户编号");
		firstMap.put("customername", "客户名称");
		firstMap.put("salesdept", "销售部门");
		firstMap.put("goodsid", "商品编码");
		firstMap.put("goodsname", "商品名称");


		firstMap.put("barcode", "条形码");
		firstMap.put("boxnum", "箱装量");
		firstMap.put("unitname", "单位");
		firstMap.put("unitnum", "数量");
		firstMap.put("notorderunitnum", "未生成订单数");
		firstMap.put("orderunitnum", "已生成订单数");
		firstMap.put("referenceprice", "参考价");
		firstMap.put("taxprice", "单价");
		firstMap.put("taxamount", "金额");
		firstMap.put("auxnumdetail", "辅数量");
		firstMap.put("remark", "备注");

		result.add(firstMap);

		PageData pageData=salesReportService.getOrderGoodsReportData(pageMap);
		List<Map> orderGoodsExportList=pageData.getList();
        orderGoodsExportList.addAll(pageData.getFooter());
		for(Map<String, Object> map2 : orderGoodsExportList){
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
	 * 显示销售情况基础分析报表
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2017-12-26
	 */
	public String showBaseSalesAnalysisReportPage() throws Exception{
		String today = CommonUtils.getTodayDateStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		List goodstypeList = getBaseSysCodeService().showSysCodeListByType("goodstype");
		request.setAttribute("goodstypeList", goodstypeList);
		List pushtypeList = getBaseSysCodeService().showSysCodeListByType("pushtypeprint");
		request.setAttribute("pushtypeList",pushtypeList);
		request.setAttribute("pushtypeListSize",pushtypeList.size());
		List rejectCategoryList = getBaseSysCodeService().showSysCodeListByType("rejectCategory");
		request.setAttribute("rejectCategoryList",rejectCategoryList);
		request.setAttribute("rejectCategoryListSize",rejectCategoryList.size());
		return SUCCESS;
	}

	/**
	 * 获取销售情况基础分析数据
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2017-12-26
	 */
	public String showBaseSalesAnalysisReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesReportService.showBaseSalesAnalysisReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}

	/**
	 * 销售情况分析表打印
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Feb 09, 2018
	 */
	public String printBaseSalesAnalysisReportData() throws Exception {
		String strPageName=request.getParameter("strPageName");
		String intOrient=request.getParameter("intOrient");
		request.setAttribute("strPageName",strPageName);
		request.setAttribute("intOrient",intOrient);
		Integer rownum=Integer.parseInt(request.getParameter("rownum"));
		String title=request.getParameter("title");
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		Map queryMap = new HashMap();
		queryMap.put("isflag", "true");
		String query = map.get("param");
		JSONObject object = JSONObject.fromObject(query);
		for (Object k : object.keySet()) {
			Object v = object.get(k);
			if (StringUtils.isNotEmpty((String) v)) {
				queryMap.put(k.toString(), v);
			}
		}
		pageMap.setCondition(queryMap);
		PageData pageData = salesReportService.showBaseSalesAnalysisReportData(pageMap);
		List list = pageData.getList();
		if (null != pageData.getFooter()) {
			List footer = pageData.getFooter();
			list.addAll(footer);
		}
		if (StringUtils.isEmpty(title))
			title = "销售情况分析表";
		request.setAttribute("title", title);
		request.setAttribute("body", ExcelUtils.dataToHTML(map, list, rownum));
		return SUCCESS;
	}

	/**
	 * 导出销售情况统计报表
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Nov 18, 2013
	 */
	public void exportBaseSalesAnalysisReportData()throws Exception{
		String groupcols = null,exporttype = null;
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		Map queryMap = new HashMap();
		if(map.containsKey("groupcols") && null != map.get("groupcols")){
			groupcols = (String)map.get("groupcols");
			queryMap.put("groupcols",groupcols);
		}
		if(map.containsKey("exporttype") && null != map.get("exporttype")){
			exporttype = (String)map.get("exporttype");
		}else{
			exporttype = "base";
		}
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
		PageData pageData = salesReportService.showBaseSalesAnalysisReportData(pageMap);
		List list = pageData.getList();
		if(null != pageData.getFooter()){
			List footer = pageData.getFooter();
			list.addAll(footer);
		}
		ExcelUtils.exportAnalysExcel(map,list);

	}
	/**
	 * 销售情况统计表选择凭证参数页面
	 * @param request
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Nov 09, 2017
	 */
	public String showSalesAnalysisReportSubjectDetailPage() throws Exception {
		String groupcolObjListJson = request.getParameter("groupcolObjListJson");
		String pushtype = request.getParameter("pushtype");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		List<Map> groupcolList = JSONUtils.jsonStrToList(groupcolObjListJson, new HashMap());
		List<Map>  subjecList = salesReportService.showSalesAnalysisReportSubjectDetailPage(pushtype,groupcolList,businessdate1,businessdate2);
		String jsonStr = JSONUtils.listToJsonStr(subjecList);
		request.setAttribute("jsonStr",jsonStr);
		return SUCCESS;

	}

	/**
	 * 德阳销售报表页面
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Feb 05, 2018
	 */
	public String showDyBaseSalesReportPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		List goodstypeList = getBaseSysCodeService().showSysCodeListByType("goodstype");
		request.setAttribute("goodstypeList", goodstypeList);
		request.setAttribute("salestypeList", getBaseSysCodeService().showSysCodeListByType("salestype"));
		return SUCCESS;
	}

	/**
	 * 德阳销售报表查询数据
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Feb 05, 2018
	 */
	public String getDyBaseSalesReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesReportService.getDyBaseSalesReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}

	/**
	 * 德阳销售情况报表打印
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Feb 09, 2018
	 */
	public String printDyBaseSalesReportData() throws Exception {
		String strPageName=request.getParameter("strPageName");
		String intOrient=request.getParameter("intOrient");
		request.setAttribute("strPageName",strPageName);
		request.setAttribute("intOrient",intOrient);
		Integer rownum=Integer.parseInt(request.getParameter("rownum"));
		String title=request.getParameter("title");
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		Map queryMap = new HashMap();
		queryMap.put("isflag", "true");
		String query = map.get("param");
		JSONObject object = JSONObject.fromObject(query);
		for (Object k : object.keySet()) {
			Object v = object.get(k);
			if (StringUtils.isNotEmpty((String) v)) {
				queryMap.put(k.toString(), v);
			}
		}
		pageMap.setCondition(queryMap);
		PageData pageData = salesReportService.getDyBaseSalesReportData(pageMap);
		List list = pageData.getList();
		if (null != pageData.getFooter()) {
			List footer = pageData.getFooter();
			list.addAll(footer);
		}
		if (StringUtils.isEmpty(title))
			title = "销售情况基础统计报表";
		request.setAttribute("title", title);
		request.setAttribute("body", ExcelUtils.dataToHTML(map, list, rownum));
		return SUCCESS;
	}


	/**
	 * 德阳导出销售情况统计报表
	 * @param
	 * @return void
	 * @throws
	 * @author luoqiang
	 * @date Feb 05, 2018
	 */
	public void exportDyBaseSalesReportPageData()throws Exception {
		String groupcols = null,exporttype = null;
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		Map queryMap = new HashMap();
		if(map.containsKey("groupcols") && null != map.get("groupcols")){
			groupcols = (String)map.get("groupcols");
			queryMap.put("groupcols",groupcols);
		}
		if(map.containsKey("exporttype") && null != map.get("exporttype")){
			exporttype = (String)map.get("exporttype");
		}else{
			exporttype = "base";
		}
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
		PageData pageData = salesReportService.getDyBaseSalesReportData(pageMap);
		List<BaseSalesReport> list = pageData.getList();
		if(null != pageData.getFooter()){
			List<BaseSalesReport> footer = pageData.getFooter();
			list.addAll(footer);
		}
		setNodataToSpaceData(list);
		ExcelUtils.exportAnalysExcel(map,list);

	}

	/**
	 * 吉马销售日报表
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Mar 19, 2018
	 */
	public String showJmSalesTargetDayReportPage() {
		request.setAttribute("today",CommonUtils.getTodayDateStr());
		request.setAttribute("month",Integer.parseInt(CommonUtils.getMonthStr(CommonUtils.getTodayDataStr())));
		return SUCCESS;
	}

	/**
	 * 查询吉马销售日报表数据
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Mar 19, 2018
	 */
	public String showJmSalesTargetDayReportData() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData=salesReportService.showJmSalesTargetDayReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}

	/**
	 * 导入吉马销售日报表目标数据
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Mar 19, 2018
	 */
	public String importJmSalesTarget() throws Exception {
		Map<String,Object> retMap = new HashMap<String,Object>();
		try{
			//获取第一行数据为字段的描述列表
			List<String> firstLine = ExcelUtils.importExcelFirstRow(excelFile);
			List<String> titleList = new ArrayList<String>();

			Map titleMap = new HashMap();
			titleMap.put("品牌业务员", "personnelid");
			titleMap.put("任务日期(年月)", "targetdate");
			titleMap.put("任务量(万)", "targetamount");
			titleMap.put("工作天数", "workday");

			for(String str : firstLine){

				titleList.add((String) titleMap.get(str));
			}

			//获取导入数据
			List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, titleList);
			if(list.size() != 0){

				List<Map<String, Object>> errorList =salesReportService.importJmSalesTarget(list);
				if(errorList.size() > 0){

					Map errorTitleMap = new LinkedHashMap();
					errorTitleMap.put("personnelid", "品牌业务员");
					errorTitleMap.put("targetdate", "任务日期(年月)");
					errorTitleMap.put("targetamount", "任务量(万)");
					errorTitleMap.put("workday", "工作天数");
					errorTitleMap.put("lineno", "行号");
					errorTitleMap.put("errors", "错误信息");

					String fileid = createErrorFile(errorTitleMap, errorList, request);
					retMap.put("msg", "导入失败" + errorList.size() + "条");
					retMap.put("errorid",fileid);
				} else {
					retMap.put("flag", true);
				}
			}else{
				retMap.put("excelempty", true);
			}

		}catch (Exception e) {
			e.printStackTrace();
			retMap.put("exception", true);
		}
		addJSONObject(retMap);
		return SUCCESS;
	}

	/**
	 * 吉马销售日报表导出
	 * @param
	 * @return void
	 * @throws
	 * @author luoqiang
	 * @date Mar 19, 2018
	 */
	public void exportJmSalesTargetDayReport() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		String targetdate=request.getParameter("targetdate");
		map.put("isflag",true);
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
		//根据定义类型获取显示的字段
		firstMap.put("targetdate","任务日期(年月)");
		firstMap.put("branduser","品牌业务员");
		firstMap.put("brandusername","品牌业务员名称");
		firstMap.put("deptname", "人员部门");
		firstMap.put("targetamount", "任务量(万)");
		firstMap.put("workday", "工作天数");
		firstMap.put("daytarget", "日均任务(万)");
		firstMap.put("dayorderamount", "今日订单金额");
		firstMap.put("daysaleoutamount", "今日发货单金额");
		firstMap.put("daycompletepercent", "今日完成占比");
		firstMap.put("daydifference", "今日差额");
		firstMap.put("monthsaleoutamount", "本月累计金额");
		firstMap.put("monthcompletepercent", "本月完成占比");

		firstMap.put("monthdifference","本月累计差额（万）");
		result.add(firstMap);
		PageData pageData=salesReportService.showJmSalesTargetDayReportData(pageMap);

		List<Map> list = pageData.getList();
		list.addAll(pageData.getFooter());

		if(list.size()!=0){
			for(Map datamap :list){
				datamap.put("targetdate",CommonUtils.getYearStr(targetdate)+"-"+CommonUtils.getMonthStr(targetdate));
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2 = datamap;
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
	 * 吉马日报表保存目标数据
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Mar 20, 2018
	 */
	public String saveJmSalesTargetDay() {
		String rowsjsonstr=request.getParameter("rowsjsonstr");
		String targetdate=request.getParameter("targetdate");
		List<Map> list=JSONUtils.jsonStrToList(rowsjsonstr,new HashMap());
		Boolean flag=salesReportService.saveJmSalesTargetDay(targetdate,list);
		addJSONObject("msg","保存成功");

		return SUCCESS;
	}

}

