/**
 * @(#)ExceptionReportServiceImpl.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Nov 22, 2013 zhengziyong 创建版本
 */
package com.hd.agent.report.service.impl;

import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.report.dao.ExceptionReportMapper;
import com.hd.agent.report.model.GoodsOut;
import com.hd.agent.report.service.IExceptionReportService;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhengziyong
 */
public class ExceptionReportServiceImpl extends BaseFilesServiceImpl implements IExceptionReportService {
	
	private ExceptionReportMapper exceptionReportMapper;

	public ExceptionReportMapper getExceptionReportMapper() {
		return exceptionReportMapper;
	}

	public void setExceptionReportMapper(ExceptionReportMapper exceptionReportMapper) {
		this.exceptionReportMapper = exceptionReportMapper;
	}

	@Override
	public PageData getGoodsOutReport(PageMap pageMap) throws Exception {
	    //是否启用发货通知单
	    String isDispatchProcessUse = getSysParamValue("IsDispatchProcessUse");
	    if(StringUtils.isEmpty(isDispatchProcessUse)){
	        isDispatchProcessUse = "0";
        }
        pageMap.getCondition().put("isDispatchProcessUse",isDispatchProcessUse);
		List<GoodsOut> list = exceptionReportMapper.getGoodsOutReportReal(pageMap);
		int count = exceptionReportMapper.getGoodsOutReportRealCount(pageMap);
		for(GoodsOut m: list){
			Customer customer = getCustomerByID(m.getCustomerid());
			if(customer != null){
				m.setCustomername(customer.getName());
			}
			Personnel personnel = getPersonnelById(m.getSalesuserid());
			if(personnel != null){
				m.setSalesusername(personnel.getName());
			}
			GoodsInfo goodsInfo = getGoodsInfoByID(m.getGoodsid());
			if(goodsInfo != null){
				m.setBarcode(goodsInfo.getBarcode());
				m.setGoodsname(goodsInfo.getName());
			}
			Brand brand = getBaseFilesGoodsMapper().getBrandInfo(m.getBrandid());
			if(brand != null){
				m.setBrandname(brand.getName());
			}
			Map map = countGoodsInfoNumber(m.getGoodsid(), m.getAuxunitid(), m.getFixnum());
			if(map.containsKey("auxnumdetail")){
				m.setFixauxnumdetail(map.get("auxnumdetail").toString());
			}
			Map map2 = countGoodsInfoNumber(m.getGoodsid(), m.getAuxunitid(), m.getSendnum());
			if(map2.containsKey("auxnumdetail")){
				m.setSendauxnumdetail(map2.get("auxnumdetail").toString());
			}
			Map map3 = countGoodsInfoNumber(m.getGoodsid(), m.getAuxunitid(), m.getOutnum());
			if(map3.containsKey("auxnumdetail")){
				m.setOutauxnumdetail(map3.get("auxnumdetail").toString());
			}
		}
		pageMap.getCondition().put("isflag", true);
		GoodsOut fGoodsout = exceptionReportMapper.getGoodsOutReportRealSum(pageMap);
		List<GoodsOut> footer = new ArrayList<GoodsOut>();
        if(fGoodsout!=null) {
            fGoodsout.setFixauxnumdetail(CommonUtils.strDigitNumDeal(fGoodsout.getFixauxnum().toString() + "箱" + fGoodsout.getFixnumint().toString()));
            fGoodsout.setSendauxnumdetail(CommonUtils.strDigitNumDeal(fGoodsout.getSendauxnum().toString() + "箱" + fGoodsout.getSendnumint().toString()));
            fGoodsout.setOutauxnumdetail(CommonUtils.strDigitNumDeal(fGoodsout.getOutauxnum().toString() + "箱" + fGoodsout.getOutnumint().toString()));
            fGoodsout.setOrderid("合计");
            footer.add(fGoodsout);
        }
		PageData pageData = new PageData(count, list, pageMap);
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public PageData getGoodsNotSalesReport(PageMap pageMap) throws Exception {
		List<GoodsInfo> list = exceptionReportMapper.getGoodsNotSalesReport(pageMap);
		for(GoodsInfo m: list){
			Brand brand = getBaseFilesGoodsMapper().getBrandInfo(m.getBrand());
			if(brand != null){
				m.setBrandName(brand.getName());
			}
			Personnel personnel = getPersonnelById(m.getDefaultsaler());
			if(personnel != null){
				m.setDefaultsalerName(personnel.getName());
			}
			if(StringUtils.isEmpty(m.getField01())){
				m.setField01("未查询到最新销售日期");
			}else{
				m.setField01(m.getField01()+ "天");
			}
            if(StringUtils.isNotEmpty(m.getField02())){
                BigDecimal num = new BigDecimal(m.getField02());
                if(num.compareTo(BigDecimal.ZERO) == 1 && null != m.getNewstorageprice()){
                    BigDecimal price = num.multiply(m.getNewstorageprice()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    m.setField03(price.toString());
                }
            }
		}
		int count = exceptionReportMapper.getGoodsNotSalesReportCount(pageMap);
		PageData pageData = new PageData(count, list, pageMap);
		return pageData;
	}

    @Override
    public List getGoodsNotSalesInStorage(PageMap pageMap) throws Exception {
        return exceptionReportMapper.getGoodsNotSalesInStorage(pageMap);
    }

    @Override
	public PageData getCustomerNotStockReport(PageMap pageMap) throws Exception {
		List<Customer> list = exceptionReportMapper.getCustomerNotStockReport(pageMap);
		int count = exceptionReportMapper.getCustomerNotStockReportCount(pageMap);
		for(Customer m: list){
			Personnel personnel = getPersonnelById(m.getSalesuserid());
			if(personnel != null){
				m.setSalesusername(personnel.getName());
			}
			if(StringUtils.isEmpty(m.getField01())){
				m.setField01("未查询到最新销售日期");
			}
			else{
				m.setField01(m.getField01()+ "天");
			}
		}
		PageData pageData = new PageData(count, list, pageMap);
		return pageData;
	}
	@Override
	public PageData showSalesOutBalanceReportData(PageMap pageMap)
			throws Exception {
		List<Map> list = exceptionReportMapper.showSalesOutBalanceReportData(pageMap);
		for(Map map : list){
			if(map.containsKey("customerid")){
				String customerid = (String) map.get("customerid");
				Customer customer = getCustomerByID(customerid);
				if(null!=customer){
					map.put("customername", customer.getName());
				}
			}
			if(map.containsKey("goodsid")){
				String goodsid = (String) map.get("goodsid");
				GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
				if(null!=goodsInfo){
					map.put("goodsname", goodsInfo.getName());
					map.put("brandname", goodsInfo.getBrandName());
				}
			}
			if(map.containsKey("branddept")){
				String branddept = (String) map.get("branddept");
				DepartMent departMent = getDepartmentByDeptid(branddept);
				if(null!=departMent){
					map.put("branddeptname", departMent.getName());
				}
			}
			if(map.containsKey("branduser")){
				String branduser = (String) map.get("branduser");
				Personnel personnel = getPersonnelById(branduser);
				if(null!=personnel){
					map.put("brandusername", personnel.getName());
				}
			}
		}
		PageData pageData = new PageData(exceptionReportMapper.showSalesOutBalanceReportDataCount(pageMap),list,pageMap);
		Map map = exceptionReportMapper.showSalesOutBalanceReportSumData(pageMap);
		List footer = new ArrayList();
		footer.add(map);
		pageData.setFooter(footer);
		return pageData;
	}

    @Override
    public PageData getBillUnAuditReportList(PageMap pageMap) throws Exception {
        String sql1 = getDataAccessRule("t_sales_receipt", "t");
        pageMap.getCondition().put("dataSql1",sql1);
        String sql2 = getDataAccessRule("t_sales_rejectbill", "t");
        pageMap.getCondition().put("dataSql2",sql2);
        if(null != pageMap.getCondition().get("isflag") && null != pageMap.getCondition().get("exportids")){
            String exportids = (String)pageMap.getCondition().get("exportids");
            String[] idsArr = exportids.split(",");
            if(idsArr.length != 0){
                pageMap.getCondition().put("idsArr",idsArr);
            }
        }
        List<Map> list = exceptionReportMapper.getBillUnAuditReportList(pageMap);
        for(Map map : list){
            String customerid = null != map.get("customerid") ? (String)map.get("customerid") : "";
            String pcustomerid = null != map.get("pcustomerid") ? (String)map.get("pcustomerid") : "";
            String handlerid = null != map.get("handlerid") ? (String)map.get("handlerid") : "";
            String salesdept = null != map.get("salesdept") ? (String)map.get("salesdept") : "";
            String salesuser = null != map.get("salesuser") ? (String)map.get("salesuser") : "";
            String indooruserid = null != map.get("indooruserid") ? (String)map.get("indooruserid") : "";
            String status = (String)map.get("status");
            String isinvoice = null != map.get("isinvoice") ? (String)map.get("isinvoice") : "";
            String billbactype = null != map.get("billbactype") ? (String)map.get("billbactype") : "";
            if(StringUtils.isNotEmpty(customerid)){
                Customer customer = getCustomerByID(customerid);
                if(null != customer){
                    map.put("customername",customer.getName());
                }
            }
            if(StringUtils.isNotEmpty(pcustomerid)){
                Customer pcustomer = getCustomerByID(pcustomerid);
                if(null != pcustomer){
                    map.put("pcustomername",pcustomer.getName());
                }
            }
            if(StringUtils.isNotEmpty(handlerid)){
                Personnel personnel = getPersonnelById(handlerid);
                if(null != personnel){
                    map.put("handlername",personnel.getName());
                }
            }
            if(StringUtils.isNotEmpty(salesdept)){
                DepartMent departMent = getDepartMentById(salesdept);
                if(null != departMent){
                    map.put("salesdeptname",departMent.getName());
                }
            }
            if(StringUtils.isNotEmpty(salesuser)){
                Personnel personnel = getPersonnelById(salesuser);
                if(null != personnel){
                    map.put("salesusername",personnel.getName());
                }
            }
            if(StringUtils.isNotEmpty(indooruserid)){
                Personnel personnel = getPersonnelById(indooruserid);
                if(null != personnel){
                    map.put("indoorusername",personnel.getName());
                }
            }
            SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(status,"status");
            if(null != sysCode){
                map.put("statusname",sysCode.getCodename());
            }
            if("0".equals(isinvoice)){
                map.put("isinvoicename","未开票");
            }else if("1".equals(isinvoice)){
                map.put("isinvoicename","已开票");
            }else if("2".equals(isinvoice)){
                map.put("isinvoicename","已核销");
            }else if("3".equals(isinvoice)){
                map.put("isinvoicename","未开票");
            }else if("4".equals(isinvoice)){
                map.put("isinvoicename","开票中");
            }else if("5".equals(isinvoice)){
                map.put("isinvoicename","部分核销");
            }
            if("1".equals(billbactype)){
                map.put("billbactypename","直退退货");
            }else if("2".equals(billbactype)){
                map.put("billbactypename","售后退货");
            }
        }
        PageData pageData = new PageData(exceptionReportMapper.getBillUnAuditReportCount(pageMap),list,pageMap);
        return pageData;
    }

    @Override
    public PageData getSalePriceReportList(PageMap pageMap) throws Exception {
        List<Map> list = exceptionReportMapper.getSalePriceReportList(pageMap);
        for(Map map : list){
            String goodsid = (String)map.get("goodsid");
            String brandid = (String)map.get("brandid");
			String customerid = (String) map.get("customerid");

            GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
            if(null != goodsInfo){
                map.put("goodsname",goodsInfo.getName());
            }
            Brand brand = getGoodsBrandByID(brandid);
            if(null != brand){
                map.put("brandname",brand.getName());
            }

			if(StringUtils.isNotEmpty(customerid)) {

				Customer customer = getCustomerByID(customerid);
				if(customer != null) {
					map.put("customername", customer.getName());
				}
			}
        }
        PageData pageData = new PageData(exceptionReportMapper.getSalePriceReportListCount(pageMap),list,pageMap);

        List<Map> foot = exceptionReportMapper.getSalePriceReportListSum(pageMap);
        if(null != foot && foot.size() != 0){
            for(Map map : foot){
                map.put("saleorderid","合计");
            }
        }else{
            foot = new ArrayList<Map>();
        }
        pageData.setFooter(foot);
        return pageData;
    }

    @Override
    public PageData getRejectPriceReportList(PageMap pageMap) throws Exception {
		//客户退货时，商品价格取价方式 1取最近一次销售价2取最近一段时间的最低销售价0默认销售价
		String salesRejectCustomerGoodsPrice = getSysParamValue("SalesRejectCustomerGoodsPrice");
		if(StringUtils.isEmpty(salesRejectCustomerGoodsPrice)){
			salesRejectCustomerGoodsPrice = "0";
		}
		pageMap.getCondition().put("salesRejectCustomerGoodsPrice",salesRejectCustomerGoodsPrice);
        List<Map> list = exceptionReportMapper.getRejectPriceReportList(pageMap);
        for(Map map : list){
            String goodsid = (String)map.get("goodsid");
            String brandid = (String)map.get("brandid");
            String customerid = (String)map.get("customerid");

            GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
            if(null != goodsInfo){
                map.put("goodsname",goodsInfo.getName());
            }
            Brand brand = getGoodsBrandByID(brandid);
            if(null != brand){
                map.put("brandname",brand.getName());
            }
            Customer customer = getCustomerByID(customerid);
            if(null != customer){
                map.put("customername",customer.getName());
            }
        }
        PageData pageData = new PageData(exceptionReportMapper.getRejectPriceReportListCount(pageMap),list,pageMap);

        List<Map> foot = new ArrayList<Map>();
        Map map = exceptionReportMapper.getRejectPriceReportListSum(pageMap);
        if(null != map && !map.isEmpty()){
            map.put("rejectid","合计");
        }
        pageData.setFooter(foot);
        return pageData;
    }
}

