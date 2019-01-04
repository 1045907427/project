/**
 * @(#)SalesReportSericeImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 15, 2013 chenwei 创建版本
 */
package com.hd.agent.report.service.impl;

import com.hd.agent.account.dao.CustomerCapitalMapper;
import com.hd.agent.basefiles.dao.GoodsMapper;
import com.hd.agent.basefiles.dao.PersonnelMapper;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.ISalesService;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.report.dao.ReportTargetMapper;
import com.hd.agent.report.dao.SalesCustomerReportMapper;
import com.hd.agent.report.dao.SalesGoodsReportMapper;
import com.hd.agent.report.dao.SalesRejectMapper;
import com.hd.agent.report.model.*;
import com.hd.agent.report.service.ISalesReportService;
import com.hd.agent.sales.dao.OrderGoodsMapper;
import com.hd.agent.sales.model.OrderGoods;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 销售报表相关service实现类
 * @author chenwei
 */
public class SalesReportSericeImpl extends BaseFilesServiceImpl implements
		ISalesReportService {
	/**
	 * 按商品销售情况统计报表dao
	 */
	private SalesGoodsReportMapper salesGoodsReportMapper;
	/**
	 * 按客户销售情况统计报表
	 */
	private SalesCustomerReportMapper salesCustomerReportMapper;
	/**
	 * 报表考核目标dao
	 */
	private ReportTargetMapper reportTargetMapper;
    /**
     * 人员档案dao
     */
    private PersonnelMapper personnelMapper ;

    /**
     * 客户余额
     */
    private CustomerCapitalMapper customerCapitalMapper;

    /**
     * 商品档案dao
     * @return
     */
    private GoodsMapper goodsMapper ;

	/**
	 * 销售退货mapper
	 */
	private SalesRejectMapper salesRejectMapper;

	/**
	 * 销售订货单dao
	 * @return
	 */
	private OrderGoodsMapper orderGoodsMapper;

	private ISalesService salesService;

    public GoodsMapper getGoodsMapper() {
        return goodsMapper;
    }

    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    public PersonnelMapper getPersonnelMapper() {
        return personnelMapper;
    }

    public void setPersonnelMapper(PersonnelMapper personnelMapper) {
        this.personnelMapper = personnelMapper;
    }

    public SalesGoodsReportMapper getSalesGoodsReportMapper() {
		return salesGoodsReportMapper;
	}

	public void setSalesGoodsReportMapper(
			SalesGoodsReportMapper salesGoodsReportMapper) {
		this.salesGoodsReportMapper = salesGoodsReportMapper;
	}
	
	public SalesCustomerReportMapper getSalesCustomerReportMapper() {
		return salesCustomerReportMapper;
	}

	public void setSalesCustomerReportMapper(
			SalesCustomerReportMapper salesCustomerReportMapper) {
		this.salesCustomerReportMapper = salesCustomerReportMapper;
	}
	
	public ReportTargetMapper getReportTargetMapper() {
		return reportTargetMapper;
	}

	public void setReportTargetMapper(ReportTargetMapper reportTargetMapper) {
		this.reportTargetMapper = reportTargetMapper;
	}

    public CustomerCapitalMapper getCustomerCapitalMapper() {
        return customerCapitalMapper;
    }

    public void setCustomerCapitalMapper(CustomerCapitalMapper customerCapitalMapper) {
        this.customerCapitalMapper = customerCapitalMapper;
    }

	public SalesRejectMapper getSalesRejectMapper() {
		return salesRejectMapper;
	}

	public void setSalesRejectMapper(SalesRejectMapper salesRejectMapper) {
		this.salesRejectMapper = salesRejectMapper;
	}

	public OrderGoodsMapper getOrderGoodsMapper() {
		return orderGoodsMapper;
	}

	public void setOrderGoodsMapper(OrderGoodsMapper orderGoodsMapper) {
		this.orderGoodsMapper = orderGoodsMapper;
	}

	public ISalesService getSalesService() {
		return salesService;
	}

	public void setSalesService(ISalesService salesService) {
		this.salesService = salesService;
	}

	@Override
	public Map showSalesCompanyReportList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_report_sales_base", null);
		pageMap.setDataSql(dataSql);
		Map map2 = new HashMap();
		String query_sql = " 1=1 ";
		String query_sqlall = " 1=1 ";
		String query_sql_push = "";
		if(pageMap.getCondition().containsKey("branddept")){
			String str = (String) pageMap.getCondition().get("branddept");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				if(!"nodata".equals(str)){
					query_sql += " and t1.branddept like concat( '"+str+"','%') ";
					query_sqlall += " and t1.branddept like concat( '"+str+"','%') ";
				}else{
					query_sql += " and (t1.branddept is null or t1.branddept = '') ";
					query_sqlall += " and (t1.branddept is null or t1.branddept = '') ";
				}
				map2.put("branddepts", str);
			}else{
				String retStr = "";
				String[] branddeptArr = str.split(",");
				for(String branddept : branddeptArr){
					Map map = new HashMap();
					map.put("deptid", branddept);
					List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
					if(dList.size() != 0){
						for(DepartMent departMent : dList){
							if(StringUtils.isNotEmpty(retStr)){
								retStr += "," + departMent.getId();
							}else{
								retStr = departMent.getId();
							}
						}
					}
				}
				query_sql += " and FIND_IN_SET(t1.branddept,'"+retStr+"')";
				query_sqlall += " and FIND_IN_SET(t1.branddept,'"+retStr+"')";
				map2.put("branddepts", retStr);
			}
		}
		if(pageMap.getCondition().containsKey("salesuser")){
			String str = (String) pageMap.getCondition().get("salesuser");
			str = StringEscapeUtils.escapeSql(str);
			if(!"nodata".equals(str)){
				query_sql += " and t.salesuser = '"+str+"' ";
				query_sqlall += " and t.salesuser = '"+str+"' ";
			}else{
				query_sql += " and (t.salesuser is null or t.salesuser = '') ";
				query_sqlall += " and (t.salesuser is null or t.salesuser = '') ";
			}
		}
		if(pageMap.getCondition().containsKey("businessdate1")){
			String str = (String) pageMap.getCondition().get("businessdate1");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t.businessdate >= '"+str+"'";
			query_sqlall += " and t.businessdate >= '"+str+"'";
		}
		if(pageMap.getCondition().containsKey("businessdate2")){
			String str = (String) pageMap.getCondition().get("businessdate2");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t.businessdate <= '"+str+"'";
			query_sqlall += " and t.businessdate <= '"+str+"'";
		}
		//小计列
		pageMap.getCondition().put("groupcols", "branddept");

		if(StringUtils.isNotEmpty(query_sqlall)){
			query_sql_push = query_sqlall.replaceAll("t1.","t.");
			query_sql_push = query_sql_push.replaceAll("t.goodsid","t.brandid");
		}
		pageMap.getCondition().put("query_sql", query_sql);
		pageMap.getCondition().put("query_sqlall", query_sqlall);
		pageMap.getCondition().put("query_sql_push", query_sql_push);

		//排序
		String orderstr = "";
		if(pageMap.getCondition().containsKey("sort")){
			String sort = (String) pageMap.getCondition().get("sort");
			String order = (String) pageMap.getCondition().get("order");
			if(null==order){
				order = "asc";
			}
			orderstr = sort +" " +order;
		}
		pageMap.getCondition().put("orderstr", orderstr);
		pageMap.getCondition().put("isflag", "true");
		//分公司销售情况列表
		List<BaseSalesReport> compnyList = new ArrayList<BaseSalesReport>();
		List<BaseSalesReport> footer = new ArrayList<BaseSalesReport>();
		Map<String,List<BaseSalesReport>> map3 = new LinkedHashMap<String, List<BaseSalesReport>>();
		List<BaseSalesReport> list = salesCustomerReportMapper.showBaseSalesReportData(pageMap);
		map2.put("pid", "");
		List<DepartMent> deptList = getBaseDepartMentMapper().getDeptListByParam(map2);
		if(null != deptList && deptList.size() != 0){
			for(DepartMent dept: deptList){
				for(BaseSalesReport brandDeptReport: list){
					if(StringUtils.isNotEmpty(brandDeptReport.getBranddept())){
						//判断获取的部门数据属于该公司
						if(map3.containsKey(dept.getId())){
							if(brandDeptReport.getBranddept().indexOf(dept.getId()) == 0){
								List<BaseSalesReport> list1 = map3.get(dept.getId());
								list1.add(brandDeptReport);
								map3.put(dept.getId(), list1);
							}
						}else{
							if(brandDeptReport.getBranddept().indexOf(dept.getId()) == 0){
								List<BaseSalesReport> list1 = new ArrayList<BaseSalesReport>();
								list1.add(brandDeptReport);
								map3.put(dept.getId(), list1);
							}
						}
					}else{
						List<BaseSalesReport> list1 = new ArrayList<BaseSalesReport>();
						list1.add(brandDeptReport);
						map3.put("nodata", list1);
					}
				}
			}
		}else{
			if(list.size() > 0){
				for(BaseSalesReport brandDeptReport: list){
					if(StringUtils.isNotEmpty(brandDeptReport.getBranddept())){
						List<BaseSalesReport> list1 = new ArrayList<BaseSalesReport>();
						list1.add(brandDeptReport);
						map3.put(brandDeptReport.getBranddept(), list1);
					}else{
						List<BaseSalesReport> list1 = new ArrayList<BaseSalesReport>();
						list1.add(brandDeptReport);
						map3.put("nodata", list1);
					}
				}
			}
		}
		if(null != map3 && !map3.isEmpty()){
			BaseSalesReport baseSalesReportSum = new BaseSalesReport();
			baseSalesReportSum.setBranddeptname("合计");
			Iterator it = map3.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				BaseSalesReport cmpBaseSalesReport = new BaseSalesReport();
				Object key = (String)entry.getKey();
				List<BaseSalesReport> list2 = (List<BaseSalesReport>)entry.getValue();
				if(null != list2 && list2.size() != 0){
					String deptid = (String)entry.getKey();
					if(!"nodata".equals(deptid)){
						DepartMent dept = getBaseDepartMentMapper().getDepartmentInfo(deptid);
						if(null != dept){
							cmpBaseSalesReport.setBranddept(dept.getId());
							cmpBaseSalesReport.setBranddeptname(dept.getName());
						}else{
							cmpBaseSalesReport.setBranddeptname("其他未定义");
						}
					}else{
						cmpBaseSalesReport.setBranddept("nodata");
						cmpBaseSalesReport.setBranddeptname("其他未指定");
					}
					for(BaseSalesReport brandDeptReport : list2){
						//订单数量
						if(null != brandDeptReport.getOrdernum()){
							cmpBaseSalesReport.setOrdernum(brandDeptReport.getOrdernum().add(null == cmpBaseSalesReport.getOrdernum() ? new BigDecimal(0) : cmpBaseSalesReport.getOrdernum()));
						}
						//订单箱数
						if(null != brandDeptReport.getOrdertotalbox()){
							cmpBaseSalesReport.setOrdertotalbox(brandDeptReport.getOrdertotalbox().add(null == cmpBaseSalesReport.getOrdertotalbox() ? new BigDecimal(0) : cmpBaseSalesReport.getOrdertotalbox()));
						}
						//订单金额
						if(null != brandDeptReport.getOrderamount()){
							cmpBaseSalesReport.setOrderamount(brandDeptReport.getOrderamount().add(null == cmpBaseSalesReport.getOrderamount() ? new BigDecimal(0) : cmpBaseSalesReport.getOrderamount()));
						}
						//订单未税金额
						if(null != brandDeptReport.getOrdernotaxamount()){
							cmpBaseSalesReport.setOrdernotaxamount(brandDeptReport.getOrdernotaxamount().add(null == cmpBaseSalesReport.getOrdernotaxamount() ? new BigDecimal(0) : cmpBaseSalesReport.getOrdernotaxamount()));
						}
						//发货单数量
						if(null != brandDeptReport.getInitsendnum()){
							cmpBaseSalesReport.setInitsendnum(brandDeptReport.getInitsendnum().add(null == cmpBaseSalesReport.getInitsendnum() ? new BigDecimal(0) : cmpBaseSalesReport.getInitsendnum()));
						}
						//发货单箱数
						if(null != brandDeptReport.getInitsendtotalbox()){
							cmpBaseSalesReport.setInitsendtotalbox(brandDeptReport.getInitsendtotalbox().add(null == cmpBaseSalesReport.getInitsendtotalbox() ? new BigDecimal(0) : cmpBaseSalesReport.getInitsendtotalbox()));
						}
						//发货单金额
						if(null != brandDeptReport.getInitsendamount()){
							cmpBaseSalesReport.setInitsendamount(brandDeptReport.getInitsendamount().add(null == cmpBaseSalesReport.getInitsendamount() ? new BigDecimal(0) : cmpBaseSalesReport.getInitsendamount()));
						}
						//发货单未税金额
						if(null != brandDeptReport.getInitsendnotaxamount()){
							cmpBaseSalesReport.setInitsendnotaxamount(brandDeptReport.getInitsendnotaxamount().add(null == cmpBaseSalesReport.getInitsendnotaxamount() ? new BigDecimal(0) : cmpBaseSalesReport.getInitsendnotaxamount()));
						}
						//发货出库数量
						if(null != brandDeptReport.getSendnum()){
							cmpBaseSalesReport.setSendnum(brandDeptReport.getSendnum().add(null == cmpBaseSalesReport.getSendnum() ? new BigDecimal(0) : cmpBaseSalesReport.getSendnum()));
						}
						//发货出库箱数
						if(null != brandDeptReport.getSendtotalbox()){
							cmpBaseSalesReport.setSendtotalbox(brandDeptReport.getSendtotalbox().add(null == cmpBaseSalesReport.getSendtotalbox() ? new BigDecimal(0) : cmpBaseSalesReport.getSendtotalbox()));
						}
						//发货出库金额
						if(null != brandDeptReport.getSendamount()){
							cmpBaseSalesReport.setSendamount(brandDeptReport.getSendamount().add(null == cmpBaseSalesReport.getSendamount() ? new BigDecimal(0) : cmpBaseSalesReport.getSendamount()));
						}
						//发货出库未税金额
						if(null != brandDeptReport.getSendnotaxamount()){
							cmpBaseSalesReport.setSendnotaxamount(brandDeptReport.getSendnotaxamount().add(null == cmpBaseSalesReport.getSendnotaxamount() ? new BigDecimal(0) : cmpBaseSalesReport.getSendnotaxamount()));
						}
						//直退数量
						if(null != brandDeptReport.getDirectreturnnum()){
							cmpBaseSalesReport.setDirectreturnnum(brandDeptReport.getDirectreturnnum().add(null == cmpBaseSalesReport.getDirectreturnnum() ? new BigDecimal(0) : cmpBaseSalesReport.getDirectreturnnum()));
						}
						//直退箱数
						if(null != brandDeptReport.getDirectreturntotalbox()){
							cmpBaseSalesReport.setDirectreturntotalbox(brandDeptReport.getDirectreturntotalbox().add(null == cmpBaseSalesReport.getDirectreturntotalbox() ? new BigDecimal(0) : cmpBaseSalesReport.getDirectreturntotalbox()));
						}
						//直退金额
						if(null != brandDeptReport.getDirectreturnamount()){
							cmpBaseSalesReport.setDirectreturnamount(brandDeptReport.getDirectreturnamount().add(null == cmpBaseSalesReport.getDirectreturnamount() ? new BigDecimal(0) : cmpBaseSalesReport.getDirectreturnamount()));
						}
						//退货数量
						if(null != brandDeptReport.getCheckreturnnum()){
							cmpBaseSalesReport.setCheckreturnnum(brandDeptReport.getCheckreturnnum().add(null == cmpBaseSalesReport.getCheckreturnnum() ? new BigDecimal(0) : cmpBaseSalesReport.getCheckreturnnum()));
						}
						//退货箱数
						if(null != brandDeptReport.getCheckreturntotalbox()){
							cmpBaseSalesReport.setCheckreturntotalbox(brandDeptReport.getCheckreturntotalbox().add(null == cmpBaseSalesReport.getCheckreturntotalbox() ? new BigDecimal(0) : cmpBaseSalesReport.getCheckreturntotalbox()));
						}
						//退货金额
						if(null != brandDeptReport.getCheckreturnamount()){
							cmpBaseSalesReport.setCheckreturnamount(brandDeptReport.getCheckreturnamount().add(null == cmpBaseSalesReport.getCheckreturnamount() ? new BigDecimal(0) : cmpBaseSalesReport.getCheckreturnamount()));
						}
						//退货总数量
						if(null != brandDeptReport.getReturnnum()){
							cmpBaseSalesReport.setReturnnum(brandDeptReport.getReturnnum().add(null == cmpBaseSalesReport.getReturnnum() ? new BigDecimal(0) : cmpBaseSalesReport.getReturnnum()));
						}
						//退货总箱数
						if(null != brandDeptReport.getReturntotalbox()){
							cmpBaseSalesReport.setReturntotalbox(brandDeptReport.getReturntotalbox().add(null == cmpBaseSalesReport.getReturntotalbox() ? new BigDecimal(0) : cmpBaseSalesReport.getReturntotalbox()));
						}
						//退货合计
						if(null != brandDeptReport.getReturnamount()){
							cmpBaseSalesReport.setReturnamount(brandDeptReport.getReturnamount().add(null == cmpBaseSalesReport.getReturnamount() ? new BigDecimal(0) : cmpBaseSalesReport.getReturnamount()));
						}
						//退货未税合计
						if(null != brandDeptReport.getReturnnotaxamount()){
							cmpBaseSalesReport.setReturnnotaxamount(brandDeptReport.getReturnnotaxamount().add(null == cmpBaseSalesReport.getReturnnotaxamount() ? new BigDecimal(0) : cmpBaseSalesReport.getReturnnotaxamount()));
						}
						//成本金额
						if(null != brandDeptReport.getCostamount()){
							cmpBaseSalesReport.setCostamount(brandDeptReport.getCostamount().add(null == cmpBaseSalesReport.getCostamount() ? new BigDecimal(0) : cmpBaseSalesReport.getCostamount()));
						}
//						//回笼金额
//						if(null != brandDeptReport.getWriteoffamount()){
//							cmpBaseSalesReport.setWriteoffamount(brandDeptReport.getWriteoffamount().add(null == cmpBaseSalesReport.getWriteoffamount() ? new BigDecimal(0) : cmpBaseSalesReport.getWriteoffamount()));
//						}
//						//回笼成本金额
//						if(null != brandDeptReport.getCostwriteoffamount()){
//							cmpBaseSalesReport.setCostwriteoffamount(brandDeptReport.getCostwriteoffamount().add(null == cmpBaseSalesReport.getCostwriteoffamount() ? new BigDecimal(0) : cmpBaseSalesReport.getCostwriteoffamount()));
//						}
						//冲差金额
						if(null != brandDeptReport.getPushbalanceamount()){
							cmpBaseSalesReport.setPushbalanceamount(brandDeptReport.getPushbalanceamount().add(null == cmpBaseSalesReport.getPushbalanceamount() ? new BigDecimal(0) : cmpBaseSalesReport.getPushbalanceamount()));
						}
						if(null != brandDeptReport.getPushbalancenotaxamount()){
							cmpBaseSalesReport.setPushbalancenotaxamount(brandDeptReport.getPushbalancenotaxamount().add(null == cmpBaseSalesReport.getPushbalancenotaxamount() ? new BigDecimal(0) : cmpBaseSalesReport.getPushbalancenotaxamount()));
						}
					}
//					//回笼毛利额
//					if(null != cmpBaseSalesReport.getWriteoffamount() && null != cmpBaseSalesReport.getCostwriteoffamount()){
//						BigDecimal writeoffmarginamount = cmpBaseSalesReport.getWriteoffamount().subtract(cmpBaseSalesReport.getCostwriteoffamount());
//						cmpBaseSalesReport.setWriteoffmarginamount(writeoffmarginamount);
//					}
					//销售数量
					if(null != cmpBaseSalesReport.getSendnum() && null != cmpBaseSalesReport.getReturnnum()){
						cmpBaseSalesReport.setSalenum(cmpBaseSalesReport.getSendnum().subtract(cmpBaseSalesReport.getReturnnum()));
					}
					//销售箱数
					if(null != cmpBaseSalesReport.getSendtotalbox() && null != cmpBaseSalesReport.getReturntotalbox()){
						cmpBaseSalesReport.setSaletotalbox(cmpBaseSalesReport.getSendtotalbox().subtract(cmpBaseSalesReport.getReturntotalbox()));
					}
					//销售金额
					if(null != cmpBaseSalesReport.getSendamount() && null != cmpBaseSalesReport.getReturnamount()){
						cmpBaseSalesReport.setSaleamount(cmpBaseSalesReport.getSendamount().subtract(cmpBaseSalesReport.getReturnamount()).add(cmpBaseSalesReport.getPushbalanceamount()));
					}
					//销售未税金额
					if(null != cmpBaseSalesReport.getSendnotaxamount() && null != cmpBaseSalesReport.getReturnnotaxamount() && null != cmpBaseSalesReport.getPushbalancenotaxamount()){
						cmpBaseSalesReport.setSalenotaxamount(cmpBaseSalesReport.getSendnotaxamount().subtract(cmpBaseSalesReport.getReturnnotaxamount()).add(cmpBaseSalesReport.getPushbalancenotaxamount()));
					}
					//销售税额
					if(null != cmpBaseSalesReport.getSaleamount() && null != cmpBaseSalesReport.getSalenotaxamount()){
						cmpBaseSalesReport.setSaletax(cmpBaseSalesReport.getSaleamount().subtract(cmpBaseSalesReport.getSalenotaxamount()));
					}
					//brandDeptReport.setSaleamount(null != brandDeptReport.getSaleamount()? brandDeptReport.getSaleamount() : new BigDecimal(0));
					//brandDeptReport.setSalenotaxamount(null != brandDeptReport.getSalenotaxamount()? brandDeptReport.getSalenotaxamount() : new BigDecimal(0));
					//退货率=退货金额（不包括直退）/销售金额
					if(null != cmpBaseSalesReport.getSaleamount() && cmpBaseSalesReport.getSaleamount().compareTo(new BigDecimal(0))==1 && null != cmpBaseSalesReport.getCheckreturnnum()){
						BigDecimal checkreturnrate = cmpBaseSalesReport.getCheckreturnamount().divide(cmpBaseSalesReport.getSaleamount(), 6, BigDecimal.ROUND_HALF_UP);
						cmpBaseSalesReport.setCheckreturnrate(checkreturnrate.multiply(new BigDecimal(100)));
					}
					if(null != cmpBaseSalesReport.getCostamount() && null != cmpBaseSalesReport.getSaleamount()){
						//毛利额 = 销售金额 - 成本金额
						cmpBaseSalesReport.setSalemarginamount(cmpBaseSalesReport.getSaleamount().subtract(cmpBaseSalesReport.getCostamount()));
						//实际毛利率 = （销售金额 - 成本金额）/销售金额*100
						if(cmpBaseSalesReport.getSaleamount().compareTo(BigDecimal.ZERO)!=0){
							BigDecimal realrate = cmpBaseSalesReport.getSaleamount().subtract(cmpBaseSalesReport.getCostamount()).divide(cmpBaseSalesReport.getSaleamount(), 6, BigDecimal.ROUND_HALF_UP);
							realrate = realrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
							cmpBaseSalesReport.setRealrate(realrate);
						}
					}
//					//回笼毛利率 = （回笼金额-回笼成本金额）/回笼金额
//					if(null != cmpBaseSalesReport.getWriteoffamount() && cmpBaseSalesReport.getWriteoffamount().compareTo(BigDecimal.ZERO)!=0 && null != cmpBaseSalesReport.getCostwriteoffamount()){
//						BigDecimal writeoffrate = cmpBaseSalesReport.getWriteoffamount().subtract(cmpBaseSalesReport.getCostwriteoffamount()).divide(cmpBaseSalesReport.getWriteoffamount(), 6, BigDecimal.ROUND_HALF_UP);
//						writeoffrate = writeoffrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
//						cmpBaseSalesReport.setWriteoffrate(writeoffrate);
//					}
					compnyList.add(cmpBaseSalesReport);
				}
				//合计
				//订单数量
				if(null != cmpBaseSalesReport.getOrdernum()){
					baseSalesReportSum.setOrdernum(cmpBaseSalesReport.getOrdernum().add(null == baseSalesReportSum.getOrdernum() ? new BigDecimal(0) : baseSalesReportSum.getOrdernum()));
				}
				//订单箱数
				if(null != cmpBaseSalesReport.getOrdertotalbox()){
					baseSalesReportSum.setOrdertotalbox(cmpBaseSalesReport.getOrdertotalbox().add(null == baseSalesReportSum.getOrdertotalbox() ? new BigDecimal(0) : baseSalesReportSum.getOrdertotalbox()));
				}
				//订单金额
				if(null != cmpBaseSalesReport.getOrderamount()){
					baseSalesReportSum.setOrderamount(cmpBaseSalesReport.getOrderamount().add(null == baseSalesReportSum.getOrderamount() ? new BigDecimal(0) : baseSalesReportSum.getOrderamount()));
				}
				//订单未税金额
				if(null != cmpBaseSalesReport.getOrdernotaxamount()){
					baseSalesReportSum.setOrdernotaxamount(cmpBaseSalesReport.getOrdernotaxamount().add(null == baseSalesReportSum.getOrdernotaxamount() ? new BigDecimal(0) : baseSalesReportSum.getOrdernotaxamount()));
				}
				//发货单数量
				if(null != cmpBaseSalesReport.getInitsendnum()){
					baseSalesReportSum.setInitsendnum(cmpBaseSalesReport.getInitsendnum().add(null == baseSalesReportSum.getInitsendnum() ? new BigDecimal(0) : baseSalesReportSum.getInitsendnum()));
				}
				//发货单箱数
				if(null != cmpBaseSalesReport.getInitsendtotalbox()){
					baseSalesReportSum.setInitsendtotalbox(cmpBaseSalesReport.getInitsendtotalbox().add(null == baseSalesReportSum.getInitsendtotalbox() ? new BigDecimal(0) : baseSalesReportSum.getInitsendtotalbox()));
				}
				//发货单金额
				if(null != cmpBaseSalesReport.getInitsendamount()){
					baseSalesReportSum.setInitsendamount(cmpBaseSalesReport.getInitsendamount().add(null == baseSalesReportSum.getInitsendamount() ? new BigDecimal(0) : baseSalesReportSum.getInitsendamount()));
				}
				//发货单未税金额
				if(null != cmpBaseSalesReport.getInitsendnotaxamount()){
					baseSalesReportSum.setInitsendnotaxamount(cmpBaseSalesReport.getInitsendnotaxamount().add(null == baseSalesReportSum.getInitsendnotaxamount() ? new BigDecimal(0) : baseSalesReportSum.getInitsendnotaxamount()));
				}
				//发货出库数量
				if(null != cmpBaseSalesReport.getSendnum()){
					baseSalesReportSum.setSendnum(cmpBaseSalesReport.getSendnum().add(null == baseSalesReportSum.getSendnum() ? new BigDecimal(0) : baseSalesReportSum.getSendnum()));
				}
				//发货出库箱数
				if(null != cmpBaseSalesReport.getSendtotalbox()){
					baseSalesReportSum.setSendtotalbox(cmpBaseSalesReport.getSendtotalbox().add(null == baseSalesReportSum.getSendtotalbox() ? new BigDecimal(0) : baseSalesReportSum.getSendtotalbox()));
				}
				//发货出库金额
				if(null != cmpBaseSalesReport.getSendamount()){
					baseSalesReportSum.setSendamount(cmpBaseSalesReport.getSendamount().add(null == baseSalesReportSum.getSendamount() ? new BigDecimal(0) : baseSalesReportSum.getSendamount()));
				}
				//发货出库未税金额
				if(null != cmpBaseSalesReport.getSendnotaxamount()){
					baseSalesReportSum.setSendnotaxamount(cmpBaseSalesReport.getSendnotaxamount().add(null == baseSalesReportSum.getSendnotaxamount() ? new BigDecimal(0) : baseSalesReportSum.getSendnotaxamount()));
				}
				//直退数量
				if(null != cmpBaseSalesReport.getDirectreturnnum()){
					baseSalesReportSum.setDirectreturnnum(cmpBaseSalesReport.getDirectreturnnum().add(null == baseSalesReportSum.getDirectreturnnum() ? new BigDecimal(0) : baseSalesReportSum.getDirectreturnnum()));
				}
				//直退箱数
				if(null != cmpBaseSalesReport.getDirectreturntotalbox()){
					baseSalesReportSum.setDirectreturntotalbox(cmpBaseSalesReport.getDirectreturntotalbox().add(null == baseSalesReportSum.getDirectreturntotalbox() ? new BigDecimal(0) : baseSalesReportSum.getDirectreturntotalbox()));
				}
				//直退金额
				if(null != cmpBaseSalesReport.getDirectreturnamount()){
					baseSalesReportSum.setDirectreturnamount(cmpBaseSalesReport.getDirectreturnamount().add(null == baseSalesReportSum.getDirectreturnamount() ? new BigDecimal(0) : baseSalesReportSum.getDirectreturnamount()));
				}
				//退货数量
				if(null != cmpBaseSalesReport.getCheckreturnnum()){
					baseSalesReportSum.setCheckreturnnum(cmpBaseSalesReport.getCheckreturnnum().add(null == baseSalesReportSum.getCheckreturnnum() ? new BigDecimal(0) : baseSalesReportSum.getCheckreturnnum()));
				}
				//退货箱数
				if(null != cmpBaseSalesReport.getCheckreturntotalbox()){
					baseSalesReportSum.setCheckreturntotalbox(cmpBaseSalesReport.getCheckreturntotalbox().add(null == baseSalesReportSum.getCheckreturntotalbox() ? new BigDecimal(0) : baseSalesReportSum.getCheckreturntotalbox()));
				}
				//退货金额
				if(null != cmpBaseSalesReport.getCheckreturnamount()){
					baseSalesReportSum.setCheckreturnamount(cmpBaseSalesReport.getCheckreturnamount().add(null == baseSalesReportSum.getCheckreturnamount() ? new BigDecimal(0) : baseSalesReportSum.getCheckreturnamount()));
				}
				//退货总数量
				if(null != cmpBaseSalesReport.getReturnnum()){
					baseSalesReportSum.setReturnnum(cmpBaseSalesReport.getReturnnum().add(null == baseSalesReportSum.getReturnnum() ? new BigDecimal(0) : baseSalesReportSum.getReturnnum()));
				}
				//退货总箱数
				if(null != cmpBaseSalesReport.getReturntotalbox()){
					baseSalesReportSum.setReturntotalbox(cmpBaseSalesReport.getReturntotalbox().add(null == baseSalesReportSum.getReturntotalbox() ? new BigDecimal(0) : baseSalesReportSum.getReturntotalbox()));
				}
				//退货合计
				if(null != cmpBaseSalesReport.getReturnamount()){
					baseSalesReportSum.setReturnamount(cmpBaseSalesReport.getReturnamount().add(null == baseSalesReportSum.getReturnamount() ? new BigDecimal(0) : baseSalesReportSum.getReturnamount()));
				}
				//退货未税合计
				if(null != cmpBaseSalesReport.getReturnnotaxamount()){
					baseSalesReportSum.setReturnnotaxamount(cmpBaseSalesReport.getReturnnotaxamount().add(null == baseSalesReportSum.getReturnnotaxamount() ? new BigDecimal(0) : baseSalesReportSum.getReturnnotaxamount()));
				}
				//成本金额
				if(null != cmpBaseSalesReport.getCostamount()){
					baseSalesReportSum.setCostamount(cmpBaseSalesReport.getCostamount().add(null == baseSalesReportSum.getCostamount() ? new BigDecimal(0) : baseSalesReportSum.getCostamount()));
				}
//				//回笼金额
//				if(null != cmpBaseSalesReport.getWriteoffamount()){
//					baseSalesReportSum.setWriteoffamount(cmpBaseSalesReport.getWriteoffamount().add(null == baseSalesReportSum.getWriteoffamount() ? new BigDecimal(0) : baseSalesReportSum.getWriteoffamount()));
//				}
//				//回笼成本金额
//				if(null != cmpBaseSalesReport.getCostwriteoffamount()){
//					baseSalesReportSum.setCostwriteoffamount(cmpBaseSalesReport.getCostwriteoffamount().add(null == baseSalesReportSum.getCostwriteoffamount() ? new BigDecimal(0) : baseSalesReportSum.getCostwriteoffamount()));
//				}
				//冲差金额
				if(null != cmpBaseSalesReport.getPushbalanceamount()){
					baseSalesReportSum.setPushbalanceamount(cmpBaseSalesReport.getPushbalanceamount().add(null == baseSalesReportSum.getPushbalanceamount() ? new BigDecimal(0) : baseSalesReportSum.getPushbalanceamount()));
				}
				if(null != cmpBaseSalesReport.getPushbalancenotaxamount()){
					baseSalesReportSum.setPushbalancenotaxamount(cmpBaseSalesReport.getPushbalancenotaxamount().add(null == baseSalesReportSum.getPushbalancenotaxamount() ? new BigDecimal(0) : baseSalesReportSum.getPushbalancenotaxamount()));
				}
			}
			//合计
			//销售数量
			if(null != baseSalesReportSum.getSendnum() && null != baseSalesReportSum.getReturnnum()){
				baseSalesReportSum.setSalenum(baseSalesReportSum.getSendnum().subtract(baseSalesReportSum.getReturnnum()));
			}
			//销售箱数
			if(null != baseSalesReportSum.getSendtotalbox() && null != baseSalesReportSum.getReturntotalbox()){
				baseSalesReportSum.setSaletotalbox(baseSalesReportSum.getSendtotalbox().subtract(baseSalesReportSum.getReturntotalbox()));
			}
			//销售金额
			if(null != baseSalesReportSum.getSendamount() && null != baseSalesReportSum.getReturnamount()){
				baseSalesReportSum.setSaleamount(baseSalesReportSum.getSendamount().subtract(baseSalesReportSum.getReturnamount()).add(baseSalesReportSum.getPushbalanceamount()));
			}
			//销售未税金额
			if(null != baseSalesReportSum.getSendnotaxamount() && null != baseSalesReportSum.getReturnnotaxamount() && null != baseSalesReportSum.getPushbalancenotaxamount()){
				baseSalesReportSum.setSalenotaxamount(baseSalesReportSum.getSendnotaxamount().subtract(baseSalesReportSum.getReturnnotaxamount()).add(baseSalesReportSum.getPushbalancenotaxamount()));
			}
			//销售税额
			if(null != baseSalesReportSum.getSaleamount() && null != baseSalesReportSum.getSalenotaxamount()){
				baseSalesReportSum.setSaletax(baseSalesReportSum.getSaleamount().subtract(baseSalesReportSum.getSalenotaxamount()));
			}
			//退货率=退货金额（不包括直退）/销售金额
			if(null != baseSalesReportSum.getSaleamount() && baseSalesReportSum.getSaleamount().compareTo(new BigDecimal(0))==1 && null != baseSalesReportSum.getCheckreturnnum()){
				BigDecimal checkreturnrate = baseSalesReportSum.getCheckreturnamount().divide(baseSalesReportSum.getSaleamount(), 6, BigDecimal.ROUND_HALF_UP);
				baseSalesReportSum.setCheckreturnrate(checkreturnrate.multiply(new BigDecimal(100)));
			}
			if(null != baseSalesReportSum.getCostamount() && null != baseSalesReportSum.getSaleamount()){
				//毛利额 = 销售金额 - 成本金额
				baseSalesReportSum.setSalemarginamount(baseSalesReportSum.getSaleamount().subtract(baseSalesReportSum.getCostamount()));
				//实际毛利率 = （销售金额 - 成本金额）/销售金额*100
				if(baseSalesReportSum.getSaleamount().compareTo(BigDecimal.ZERO)==1){
					BigDecimal realrate = baseSalesReportSum.getSaleamount().subtract(baseSalesReportSum.getCostamount()).divide(baseSalesReportSum.getSaleamount(), 6, BigDecimal.ROUND_HALF_UP);
					realrate = realrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
					baseSalesReportSum.setRealrate(realrate);
				}
			}
//			//回笼毛利额
//			if(null != baseSalesReportSum.getWriteoffamount() && null != baseSalesReportSum.getCostwriteoffamount()){
//				BigDecimal writeoffmarginamount = baseSalesReportSum.getWriteoffamount().subtract(baseSalesReportSum.getCostwriteoffamount());
//				baseSalesReportSum.setWriteoffmarginamount(writeoffmarginamount);
//			}
//			//回笼毛利率 = （回笼金额-回笼成本金额）/回笼金额
//			if(null != baseSalesReportSum.getWriteoffamount() && baseSalesReportSum.getWriteoffamount().compareTo(BigDecimal.ZERO)==1){
//				BigDecimal writeoffrate = baseSalesReportSum.getWriteoffamount().subtract(baseSalesReportSum.getCostwriteoffamount()).divide(baseSalesReportSum.getWriteoffamount(), 6, BigDecimal.ROUND_HALF_UP);
//				writeoffrate = writeoffrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
//				baseSalesReportSum.setWriteoffrate(writeoffrate);
//			}
			footer.add(baseSalesReportSum);
		}
		Map map = new HashMap();
		map.put("list", compnyList);
		map.put("footer", footer);
		return map;
	}

	@Override
	public PageData showBaseSalesReportData(PageMap pageMap) throws Exception {
		//数据权限类型
		String datasqltype = null != pageMap.getCondition().get("datasqltype") ? (String) pageMap.getCondition().get("datasqltype") : "";
		//小计列
        String groupcols = (String) pageMap.getCondition().get("groupcols");
        if (!pageMap.getCondition().containsKey("groupcols")) {
            groupcols = "customerid";
            pageMap.getCondition().put("groupcols", groupcols);
        }
        if (datasqltype.equals("adduserid")) {
            String dataSql = getDataAccessRule("t_report_adduser_base", "z");
            pageMap.setDataSql(dataSql);
        } else {
            String dataSql = getDataAccessRule("t_report_sales_base", "z");
            pageMap.setDataSql(dataSql);
        }
        String query_sql = " 1=1 ";
        String query_sqlall = " 1=1 ";
        String query_sql_push = "";
        if (pageMap.getCondition().containsKey("goodsid")) {
            String str = (String) pageMap.getCondition().get("goodsid");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") == -1) {
                query_sql += " and t1.goodsid = '" + str + "' ";
                query_sqlall += " and t1.goodsid = '" + str + "' ";
            } else {
                query_sql += " and FIND_IN_SET(t1.goodsid,'" + str + "')";
                query_sqlall += " and FIND_IN_SET(t1.goodsid,'" + str + "')";
            }
        }
        if (pageMap.getCondition().containsKey("brandid")) {
            String str = (String) pageMap.getCondition().get("brandid");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") == -1) {
                if (!"nodata".equals(str)) {
                    query_sql += " and t1.brandid = '" + str + "' ";
                    query_sqlall += " and t1.brandid = '" + str + "' ";
                } else {
                    query_sql += " and (t1.brandid is null or t1.brandid = '')";
                    query_sqlall += " and (t1.brandid is null or t1.brandid = '')";
                }
            } else {
                query_sql += " and FIND_IN_SET(t1.brandid,'" + str + "')";
                query_sqlall += " and FIND_IN_SET(t1.brandid,'" + str + "')";
            }
        } else if (pageMap.getCondition().containsKey("brandids")) {
            String str = (String) pageMap.getCondition().get("brandids");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") == -1) {
                if (!"nodata".equals(str)) {
                    query_sql += " and t1.brandid = '" + str + "' ";
                    query_sqlall += " and t1.brandid = '" + str + "' ";
                } else {
                    query_sql += " and (t1.brandid is null or t1.brandid = '')";
                    query_sqlall += " and (t1.brandid is null or t1.brandid = '')";
                }
            } else {
                query_sql += " and FIND_IN_SET(t1.brandid,'" + str + "')";
                query_sqlall += " and FIND_IN_SET(t1.brandid,'" + str + "')";
            }
        }

        if (pageMap.getCondition().containsKey("branduser")) {
            String str = (String) pageMap.getCondition().get("branduser");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") == -1) {
                if (!"nodata".equals(str)) {
                    query_sql += " and t1.branduser = '" + str + "' ";
                    query_sqlall += " and t1.branduser = '" + str + "' ";
                } else {
                    query_sql += " and (t1.branduser is null or t1.branduser = '')";
                    query_sqlall += " and (t1.branduser is null or t1.branduser = '')";
                }
            } else {
                query_sql += " and FIND_IN_SET(t1.branduser,'" + str + "')";
                query_sqlall += " and FIND_IN_SET(t1.branduser,'" + str + "')";
            }
        }
        if (pageMap.getCondition().containsKey("supplieruser")) {
            String str = (String) pageMap.getCondition().get("supplieruser");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") == -1) {
                if (!"nodata".equals(str)) {
                    query_sql += " and t1.supplieruser = '" + str + "' ";
                    query_sqlall += " and t1.supplieruser = '" + str + "' ";
                } else {
                    query_sql += " and (t1.supplieruser is null or t1.supplieruser = '')";
                    query_sqlall += " and (t1.supplieruser is null or t1.supplieruser = '')";
                }
            } else {
                query_sql += " and FIND_IN_SET(t1.supplieruser,'" + str + "')";
                query_sqlall += " and FIND_IN_SET(t1.supplieruser,'" + str + "')";
            }
        }
        if (pageMap.getCondition().containsKey("branddept")) {
            String str = (String) pageMap.getCondition().get("branddept");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") == -1) {
                if (!"nodata".equals(str)) {
                    query_sql += " and t1.branddept like '" + str + "%' ";
                    query_sqlall += " and t1.branddept like '" + str + "%' ";
                } else {
                    query_sql += " and (t1.branddept is null or t1.branddept = '')";
                    query_sqlall += " and (t1.branddept is null or t1.branddept = '')";
                }
            } else {
                String retStr = "";
                String[] branddeptArr = str.split(",");
                for (String branddept : branddeptArr) {
                    Map map = new HashMap();
                    map.put("deptid", branddept);
                    List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
                    if (dList.size() != 0) {
                        for (DepartMent departMent : dList) {
                            if (StringUtils.isNotEmpty(retStr)) {
                                retStr += "," + departMent.getId();
                            } else {
                                retStr = departMent.getId();
                            }
                        }
                    }
                }
                query_sql += " and FIND_IN_SET(t1.branddept,'" + retStr + "')";
                query_sqlall += " and FIND_IN_SET(t1.branddept,'" + retStr + "')";
            }
        }
        if (pageMap.getCondition().containsKey("salesdept")) {
            String str = (String) pageMap.getCondition().get("salesdept");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") == -1) {
                if (!"nodata".equals(str)) {
                    query_sql += " and t.salesdept like '" + str + "%' ";
                    query_sqlall += " and t.salesdept like '" + str + "%' ";
                } else {
                    query_sql += " and (t.salesdept is null or t.salesdept = '')";
                    query_sqlall += " and (t.salesdept is null or t.salesdept = '')";
                }
            } else {
                String retStr = "";
                String[] salesdeptArr = str.split(",");
                for (String salesdept : salesdeptArr) {
                    Map map = new HashMap();
                    map.put("deptid", salesdept);
                    List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
                    if (dList.size() != 0) {
                        for (DepartMent departMent : dList) {
                            if (StringUtils.isNotEmpty(retStr)) {
                                retStr += "," + departMent.getId();
                            } else {
                                retStr = departMent.getId();
                            }
                        }
                    }
                }
                query_sql += " and FIND_IN_SET(t.salesdept,'" + retStr + "')";
                query_sqlall += " and FIND_IN_SET(t.salesdept,'" + retStr + "')";
            }
        }
        if (pageMap.getCondition().containsKey("customersort")) {
            String str = (String) pageMap.getCondition().get("customersort");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") == -1) {
                if (!"nodata".equals(str)) {
                    query_sql += " and t.customersort like '" + str + "%' ";
                    query_sqlall += " and t.customersort like '" + str + "%' ";
                } else {
                    query_sql += " and (t.customersort is null or t.customersort = '')";
                    query_sqlall += " and (t.customersort is null or t.customersort = '')";
                }
            } else {
                query_sql += " and FIND_IN_SET(t.customersort,'" + str + "')";
                query_sqlall += " and FIND_IN_SET(t.customersort,'" + str + "')";
            }
        }
        if (pageMap.getCondition().containsKey("salesarea")) {
            String str = (String) pageMap.getCondition().get("salesarea");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") == -1) {
                if (!"nodata".equals(str)) {
                    query_sql += " and t.salesarea = '" + str + "' ";
                    query_sqlall += " and t.salesarea = '" + str + "' ";
                } else {
                    query_sql += " and (t.salesarea is null or t.salesarea = '')";
                    query_sqlall += " and (t.salesarea is null or t.salesarea = '')";
                }
            } else {
                query_sql += " and FIND_IN_SET(t.salesarea,'" + str + "')";
                query_sqlall += " and FIND_IN_SET(t.salesarea,'" + str + "')";
            }
        }
        if (pageMap.getCondition().containsKey("salesuser")) {
            String str = (String) pageMap.getCondition().get("salesuser");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") == -1) {
                if (!"nodata".equals(str)) {
                    query_sql += " and t.salesuser = '" + str + "' ";
                    query_sqlall += " and t.salesuser = '" + str + "' ";
                } else {
                    query_sql += " and (t.salesuser is null or t.salesuser = '')";
                    query_sqlall += " and (t.salesuser is null or t.salesuser = '')";
                }
            } else {
                query_sql += " and FIND_IN_SET(t.salesuser,'" + str + "')";
                query_sqlall += " and FIND_IN_SET(t.salesuser,'" + str + "')";
            }
        }
        if (pageMap.getCondition().containsKey("supplierid")) {
            String str = (String) pageMap.getCondition().get("supplierid");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") == -1) {
                if (!"nodata".equals(str)) {
                    query_sql += " and t1.supplierid = '" + str + "' ";
                    query_sqlall += " and t1.supplierid = '" + str + "' ";
                } else {
                    query_sql += " and (t1.supplierid is null or t1.supplierid = '')";
                    query_sqlall += " and (t1.supplierid is null or t1.supplierid = '')";
                }
            } else {
                query_sql += " and FIND_IN_SET(t1.supplierid,'" + str + "')";
                query_sqlall += " and FIND_IN_SET(t1.supplierid,'" + str + "')";
            }
        }
        if (pageMap.getCondition().containsKey("goodssort")) {
            String str = (String) pageMap.getCondition().get("goodssort");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") == -1) {
                if (!"nodata".equals(str)) {
                    query_sql += " and t1.goodssort = '" + str + "' ";
                } else {
                    query_sql += " and (t1.goodssort is null or t1.goodssort = '')";
                }
            } else {
                query_sql += " and FIND_IN_SET(t1.goodssort,'" + str + "')";
            }
        }
        if (pageMap.getCondition().containsKey("goodstype")) {
            String str = (String) pageMap.getCondition().get("goodstype");
            str = StringEscapeUtils.escapeSql(str);
            query_sql += " and FIND_IN_SET(g.goodstype,'" + str + "')";
        }
        if (pageMap.getCondition().containsKey("customerid")) {
            String str = (String) pageMap.getCondition().get("customerid");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") == -1) {
                if (!"nodata".equals(str)) {
                    query_sql += " and t.customerid = '" + str + "' ";
                    query_sqlall += " and t.customerid = '" + str + "' ";
                } else {
                    query_sql += " and (t.customerid is null or t.customerid = '')";
                    query_sqlall += " and (t.customerid is null or t.customerid = '')";
                }
            } else {
                query_sql += " and FIND_IN_SET(t.customerid,'" + str + "')";
                query_sqlall += " and FIND_IN_SET(t.customerid,'" + str + "')";
            }
        }
        if (pageMap.getCondition().containsKey("pcustomerid")) {
            String str = (String) pageMap.getCondition().get("pcustomerid");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") == -1) {
                if (!"nodata".equals(str)) {
                    query_sql += " and t.pcustomerid = '" + str + "' ";
                    query_sqlall += " and t.pcustomerid = '" + str + "' ";
                } else {
                    query_sql += " and (t.pcustomerid is null or t.pcustomerid = '')";
                    query_sqlall += " and (t.pcustomerid is null or t.pcustomerid = '')";
                }
            } else {
                query_sql += " and FIND_IN_SET(t.pcustomerid,'" + str + "')";
                query_sqlall += " and FIND_IN_SET(t.pcustomerid,'" + str + "')";
            }
        }
        if (pageMap.getCondition().containsKey("adduserid")) {
            String str = (String) pageMap.getCondition().get("adduserid");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") == -1) {
                if (!"nodata".equals(str)) {
                    query_sql += " and t.adduserid = '" + str + "' ";
                    query_sqlall += " and t.adduserid = '" + str + "' ";
                } else {
                    query_sql += " and (t.adduserid is null or t.adduserid = '')";
                    query_sqlall += " and (t.adduserid is null or t.adduserid = '')";
                }
            } else {
                query_sql += " and FIND_IN_SET(t.adduserid,'" + str + "')";
                query_sqlall += " and FIND_IN_SET(t.adduserid,'" + str + "')";
            }
        }
        if (pageMap.getCondition().containsKey("storageid")) {
            String str = (String) pageMap.getCondition().get("storageid");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") == -1) {
                if (!"nodata".equals(str)) {
                    query_sql += " and t.storageid = '" + str + "' ";
                } else {
                    query_sql += " and (t.storageid is null or t.storageid = '')";
                }
            } else {
                query_sql += " and FIND_IN_SET(t.storageid,'" + str + "')";
            }
        }
        // 销售类型
		if (pageMap.getCondition().containsKey("salestype")) {
			String str = (String) pageMap.getCondition().get("salestype");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and salestype = '" + str + "' ";
				} else {
					query_sql += " and (salestype is null or salestype = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(salestype,'" + str + "')";
			}
		}
        if (pageMap.getCondition().containsKey("businessdate1")) {
            String str = (String) pageMap.getCondition().get("businessdate1");
            str = StringEscapeUtils.escapeSql(str);
            query_sql += " and t.businessdate >= '" + str + "'";
            query_sqlall += " and t.businessdate >= '" + str + "'";
        }
        if (pageMap.getCondition().containsKey("businessdate2")) {
            String str = (String) pageMap.getCondition().get("businessdate2");
            str = StringEscapeUtils.escapeSql(str);
            query_sql += " and t.businessdate <= '" + str + "'";
            query_sqlall += " and t.businessdate <= '" + str + "'";
        }
        if (StringUtils.isNotEmpty(query_sqlall)) {
            query_sql_push = query_sqlall.replaceAll("t1.", "t.");
            query_sql_push = query_sql_push.replaceAll("t.goodsid", "t.brandid");
        }
		if (pageMap.getCondition().containsKey("storageid")) {
			query_sql_push = " and 1!= 1";
		}
        pageMap.getCondition().put("query_sql", query_sql);
        pageMap.getCondition().put("query_sqlall", query_sqlall);
        pageMap.getCondition().put("query_sql_push", query_sql_push);
        //排序
        String orderstr = "";
        if (pageMap.getCondition().containsKey("sort")) {
            String sort = (String) pageMap.getCondition().get("sort");
            String order = (String) pageMap.getCondition().get("order");
            if (null == order) {
                order = "asc";
            }
            orderstr = sort + " " + order;
        }
        pageMap.getCondition().put("orderstr", orderstr);

        List<BaseSalesReport> list = salesCustomerReportMapper.showBaseSalesReportData(pageMap);
        for (BaseSalesReport baseSalesReport : list) {
            if (null == baseSalesReport) {
                continue;
            }
            BigDecimal sendnum = null != baseSalesReport.getSendnum() ? baseSalesReport.getSendnum() : BigDecimal.ZERO;
            BigDecimal checkreturnnum = null != baseSalesReport.getCheckreturnnum() ? baseSalesReport.getCheckreturnnum() : BigDecimal.ZERO;
            BigDecimal returnnum = null != baseSalesReport.getReturnnum() ? baseSalesReport.getReturnnum() : BigDecimal.ZERO;
            BigDecimal sendtotalbox = null != baseSalesReport.getSendtotalbox() ? baseSalesReport.getSendtotalbox() : BigDecimal.ZERO;
            BigDecimal returntotalbox = null != baseSalesReport.getReturntotalbox() ? baseSalesReport.getReturntotalbox() : BigDecimal.ZERO;
            BigDecimal sendamount = null != baseSalesReport.getSendamount() ? baseSalesReport.getSendamount() : BigDecimal.ZERO;
            BigDecimal returnamount = null != baseSalesReport.getReturnamount() ? baseSalesReport.getReturnamount() : BigDecimal.ZERO;
            BigDecimal pushbalanceamount = null != baseSalesReport.getPushbalanceamount() ? baseSalesReport.getPushbalanceamount() : BigDecimal.ZERO;
            BigDecimal pushbalancenotaxamount = null != baseSalesReport.getPushbalancenotaxamount() ? baseSalesReport.getPushbalancenotaxamount() : BigDecimal.ZERO;
            BigDecimal sendnotaxamount = null != baseSalesReport.getSendnotaxamount() ? baseSalesReport.getSendnotaxamount() : BigDecimal.ZERO;
            BigDecimal returnnotaxamount = null != baseSalesReport.getReturnnotaxamount() ? baseSalesReport.getReturnnotaxamount() : BigDecimal.ZERO;
            BigDecimal costamount = null != baseSalesReport.getCostamount() ? baseSalesReport.getCostamount() : BigDecimal.ZERO;
            BigDecimal saleamount=sendamount.subtract(returnamount).add(pushbalanceamount);
            BigDecimal checkreturnamount=null!=baseSalesReport.getCheckreturnamount()?baseSalesReport.getCheckreturnamount():BigDecimal.ZERO;

            //退货率=退货金额(不包括直退)/销售金额
            if (saleamount.compareTo(BigDecimal.ZERO) != 0) {
                BigDecimal checkreturnrate = checkreturnamount.divide(saleamount, 6, BigDecimal.ROUND_HALF_UP);
				baseSalesReport.setCheckreturnrate(checkreturnrate.multiply(new BigDecimal(100)));
            }
            //销售数量 = 发货单数量 - 直退数量
            baseSalesReport.setSalenum(sendnum.subtract(returnnum));
            //销售箱数
            baseSalesReport.setSaletotalbox(sendtotalbox.subtract(returntotalbox));
            baseSalesReport.setSaleamount(saleamount);
            baseSalesReport.setSalenotaxamount(sendnotaxamount.subtract(returnnotaxamount).add(pushbalancenotaxamount));
            if (null != baseSalesReport.getSaleamount()) {
                //毛利额 = 销售金额 - 成本金额
                baseSalesReport.setSalemarginamount(baseSalesReport.getSaleamount().subtract(costamount));
                //实际毛利率 = （销售金额 - 成本金额）/销售金额*100
                if (baseSalesReport.getSaleamount().compareTo(BigDecimal.ZERO) != 0) {
                    BigDecimal realrate = baseSalesReport.getSaleamount().subtract(costamount).divide(baseSalesReport.getSaleamount(), 6, BigDecimal.ROUND_HALF_UP);
                    realrate = realrate.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    if (baseSalesReport.getSaleamount().compareTo(BigDecimal.ZERO) == -1) {
                        baseSalesReport.setRealrate(realrate.negate());
                    } else {
                        baseSalesReport.setRealrate(realrate);
                    }
                }
            } else if (costamount.compareTo(BigDecimal.ZERO) == 1) {
                baseSalesReport.setRealrate(new BigDecimal(100).negate());
            }
            baseSalesReport.setId(baseSalesReport.getCustomerid());
            //销售税额
            if (null != baseSalesReport.getSalenotaxamount() && null != baseSalesReport.getSaleamount()) {
                baseSalesReport.setSaletax(baseSalesReport.getSaleamount().subtract(baseSalesReport.getSalenotaxamount()));
            }

            //根据获取的商品编码获取条形码
            if (StringUtils.isNotEmpty(baseSalesReport.getGoodsid())) {
                GoodsInfo goodsInfo = getGoodsInfoByID(baseSalesReport.getGoodsid());
                if (null != goodsInfo) {
                    baseSalesReport.setBarcode(goodsInfo.getBarcode());
                    baseSalesReport.setGoodsname(goodsInfo.getName());
                    baseSalesReport.setSpell(goodsInfo.getSpell());
                }
            }
            //判断是否为导出使用，若为导出，则将直退数量、售后退货数量显示为负数
            if (pageMap.getCondition().containsKey("exportflag") && null != pageMap.getCondition().get("exportflag")) {
                //直退数量
                baseSalesReport.setDirectreturnnum(baseSalesReport.getDirectreturnnum().negate());
                //直退箱数
                baseSalesReport.setDirectreturntotalbox(baseSalesReport.getDirectreturntotalbox().negate());
                //直退金额
                baseSalesReport.setDirectreturnamount(baseSalesReport.getDirectreturnamount().negate());
                //售后退货数量
                baseSalesReport.setCheckreturnnum(baseSalesReport.getCheckreturnnum().negate());
                //售后退货箱数
                baseSalesReport.setCheckreturntotalbox(baseSalesReport.getCheckreturntotalbox().negate());
                //退货金额
                baseSalesReport.setCheckreturnamount(baseSalesReport.getCheckreturnamount().negate());
                //退货总数量
                baseSalesReport.setReturnnum(baseSalesReport.getReturnnum().negate());
                //退货总箱数
                baseSalesReport.setReturntotalbox(baseSalesReport.getReturntotalbox().negate());
                //退货合计
                baseSalesReport.setReturnamount(baseSalesReport.getReturnamount().negate());
            }
            if (groupcols.indexOf("customerid") != -1) {
                if (StringUtils.isNotEmpty(baseSalesReport.getCustomerid())) {
                    Customer customer = getCustomerByID(baseSalesReport.getCustomerid());
                    if (null != customer) {
                        baseSalesReport.setCustomername(customer.getName());
                        baseSalesReport.setShortcode(customer.getShortcode());
                    } else {
                        baseSalesReport.setCustomername("其他未定义");
                    }
                } else {
                    baseSalesReport.setCustomerid("nodata");
                    baseSalesReport.setCustomername("其他未指定");
                }
                if (groupcols.indexOf("pcustomerid") == -1) {
                    Customer pcustomer = getCustomerByID(baseSalesReport.getPcustomerid());
                    if (null != pcustomer) {
                        baseSalesReport.setPcustomername(pcustomer.getName());
                    }
                }
                if (groupcols.indexOf("salesdept") == -1) {
                    DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getSalesdept());
                    if (null != departMent) {
                        baseSalesReport.setSalesdeptname(departMent.getName());
                    }
                }
                if (groupcols.indexOf("customersort") == -1) {
                    CustomerSort customerSort = getCustomerSortByID(baseSalesReport.getCustomersort());
                    if (null != customerSort) {
                        baseSalesReport.setCustomersortname(customerSort.getName());
                    }
                }
                if (groupcols.indexOf("salesarea") == -1) {
                    SalesArea salesArea = getSalesareaByID(baseSalesReport.getSalesarea());
                    if (null != salesArea) {
                        baseSalesReport.setSalesareaname(salesArea.getThisname());
                    }
                }
                if (groupcols.indexOf("salesuser") == -1) {
                    Personnel personnel = getPersonnelById(baseSalesReport.getSalesuser());
                    if (null != personnel) {
                        baseSalesReport.setSalesusername(personnel.getName());
                    }
                    DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getSalesdept());
                    if (null != departMent) {
                        baseSalesReport.setSalesdeptname(departMent.getName());
                    }
                }
            } else {
                baseSalesReport.setCustomerid("");
                baseSalesReport.setCustomername("");
                if (groupcols.indexOf("salesuser") == -1 && groupcols.indexOf("salesdept") == -1) {
                    baseSalesReport.setSalesdeptname("");
                }
            }
            if (groupcols.indexOf("pcustomerid") != -1) {
                if (StringUtils.isNotEmpty(baseSalesReport.getPcustomerid())) {
                    Customer pcustomer = getCustomerByID(baseSalesReport.getPcustomerid());
                    if (null != pcustomer) {
                        baseSalesReport.setPcustomername(pcustomer.getName());
                    } else {
                        baseSalesReport.setPcustomername("其他未定义");
                    }
                } else {
                    baseSalesReport.setPcustomerid("nodata");
                    baseSalesReport.setPcustomername("其他未指定");
                }
                if (groupcols.indexOf("customerid") == 1) {
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setCustomername("");
                }
            }
            if (groupcols.indexOf("customersort") != -1) {
                if (StringUtils.isNotEmpty(baseSalesReport.getCustomersort())) {
                    CustomerSort customerSort = getCustomerSortByID(baseSalesReport.getCustomersort());
                    if (null != customerSort) {
                        baseSalesReport.setCustomersortname(customerSort.getName());
                    } else {
                        baseSalesReport.setCustomersortname("其他未定义");
                    }
                } else {
                    baseSalesReport.setCustomersort("nodata");
                    baseSalesReport.setCustomersortname("其他未指定");
                }
            }
            if (groupcols.indexOf("salesarea") != -1) {
                if (StringUtils.isNotEmpty(baseSalesReport.getSalesarea())) {
                    SalesArea salesArea = getSalesareaByID(baseSalesReport.getSalesarea());
                    if (null != salesArea) {
                        baseSalesReport.setSalesareaname(salesArea.getThisname());
                    } else {
                        baseSalesReport.setSalesareaname("其他未定义");
                    }
                } else {
                    baseSalesReport.setSalesarea("nodata");
                    baseSalesReport.setSalesareaname("其他未指定");
                }
            }
            if (groupcols.indexOf("salesdept") != -1) {
                if (StringUtils.isNotEmpty(baseSalesReport.getSalesdept())) {
                    DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getSalesdept());
                    if (null != departMent) {
                        baseSalesReport.setSalesdeptname(departMent.getName());
                    } else {
                        baseSalesReport.setSalesdeptname("其他未定义");
                    }
                } else {
                    baseSalesReport.setSalesdept("nodata");
                    baseSalesReport.setSalesdeptname("其他未指定");
                }
            }
            if (groupcols.indexOf("salesuser") != -1) {
                if (StringUtils.isNotEmpty(baseSalesReport.getSalesuser())) {
                    Personnel personnel = getPersonnelById(baseSalesReport.getSalesuser());
                    if (null != personnel) {
                        baseSalesReport.setSalesusername(personnel.getName());
                    } else {
                        baseSalesReport.setSalesusername("其他未定义");
                    }
                } else {
                    baseSalesReport.setSalesuser("nodata");
                    baseSalesReport.setSalesusername("其他未指定");
                }
                DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getSalesdept());
                if (null != departMent) {
                    baseSalesReport.setSalesdeptname(departMent.getName());
                }
                if (groupcols.indexOf("salesarea") == -1) {
                    SalesArea salesArea = getSalesareaByID(baseSalesReport.getSalesarea());
                    if (null != salesArea) {
                        baseSalesReport.setSalesareaname(salesArea.getThisname());
                    }
                }
            }
            if (groupcols.indexOf("goodsid") != -1) {
                GoodsInfo goodsInfo = getAllGoodsInfoByID(baseSalesReport.getGoodsid());
                if (null != goodsInfo) {
                    baseSalesReport.setGoodsname(goodsInfo.getName());
                    baseSalesReport.setSpell(goodsInfo.getSpell());
					baseSalesReport.setBoxnum(goodsInfo.getBoxnum());
                    WaresClass waresClass = getBaseGoodsMapper().getWaresClassInfo(baseSalesReport.getGoodssort());
                    if (null != waresClass) {
                        baseSalesReport.setGoodssortname(waresClass.getThisname());
                    }
                    if (StringUtils.isNotEmpty(goodsInfo.getGoodstype())) {
                        baseSalesReport.setGoodstype(goodsInfo.getGoodstype());
                        SysCode goodstype = getBaseSysCodeMapper().getSysCodeInfo(goodsInfo.getGoodstype(), "goodstype");
                        if (null != goodstype) {
                            baseSalesReport.setGoodstypename(goodstype.getCodename());
                        }
                    }
                    BuySupplier buySupplier = getSupplierInfoById(baseSalesReport.getSupplierid());
                    if (null != buySupplier) {
                        baseSalesReport.setSuppliername(buySupplier.getName());
                    }
                } else {
                    Brand brand = getGoodsBrandByID(baseSalesReport.getGoodsid());
                    if (null != brand) {
                        baseSalesReport.setGoodsname("（折扣）" + brand.getName());
                    } else {
                        baseSalesReport.setGoodsname("（折扣）其他");
                    }
                }
                if (groupcols.indexOf("brandid") == -1) {
                    Brand brand = getGoodsBrandByID(baseSalesReport.getBrandid());
                    if (null != brand) {
                        baseSalesReport.setBrandname(brand.getName());
                    }
                    Personnel personnel = getPersonnelById(baseSalesReport.getBranduser());
                    if (null != personnel) {
                        baseSalesReport.setBrandusername(personnel.getName());
                    }
                    if ("QC".equals(baseSalesReport.getSupplieruser())) {
                        baseSalesReport.setSupplierusername("期初");
                    } else {
                        Personnel personnel2 = getPersonnelById(baseSalesReport.getSupplieruser());
                        if (null != personnel2) {
                            baseSalesReport.setSupplierusername(personnel2.getName());
                        }
                    }
                }
                if (groupcols.indexOf("branddept") == -1) {
                    DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getBranddept());
                    if (null != departMent) {
                        baseSalesReport.setBranddeptname(departMent.getName());
                    }
                }
                if (groupcols.indexOf("branduser") == -1) {
                    Personnel personnel = getPersonnelById(baseSalesReport.getBranduser());
                    if (null != personnel) {
                        baseSalesReport.setBrandusername(personnel.getName());
                    }
                }
            }
            if (groupcols.indexOf("goodssort") != -1) {
                if (StringUtils.isNotEmpty(baseSalesReport.getGoodssort())) {
                    WaresClass waresClass = getBaseGoodsMapper().getWaresClassInfo(baseSalesReport.getGoodssort());
                    if (null != waresClass) {
                        baseSalesReport.setGoodssortname(waresClass.getThisname());
                    } else {
                        baseSalesReport.setGoodssortname("其他未定义");
                    }
                } else {
                    baseSalesReport.setGoodssort("nodata");
                    baseSalesReport.setGoodssortname("其他未指定");
                }
            }
            if (groupcols.indexOf("brandid") != -1) {
                if (StringUtils.isNotEmpty(baseSalesReport.getBrandid())) {
                    Brand brand = getGoodsBrandByID(baseSalesReport.getBrandid());
                    if (null != brand) {
                        baseSalesReport.setBrandname(brand.getName());
                    } else {
                        baseSalesReport.setBrandname("其他未定义");
                    }
                } else {
                    baseSalesReport.setBrandid("nodata");
                    baseSalesReport.setBrandname("其他未指定");
                }
                if (groupcols.indexOf("branddept") == -1) {
                    DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getBranddept());
                    if (null != departMent) {
                        baseSalesReport.setBranddeptname(departMent.getName());
                    }
                }
                Personnel personnel = getPersonnelById(baseSalesReport.getBranduser());
                if (null != personnel) {
                    baseSalesReport.setBrandusername(personnel.getName());
                }
                if ("QC".equals(baseSalesReport.getSupplieruser())) {
                    baseSalesReport.setSupplierusername("期初");
                } else {
                    Personnel personnel2 = getPersonnelById(baseSalesReport.getSupplieruser());
                    if (null != personnel2) {
                        baseSalesReport.setSupplierusername(personnel2.getName());
                    }
                }
            }
            if (groupcols.indexOf("branduser") != -1) {
                if (StringUtils.isNotEmpty(baseSalesReport.getBranduser())) {
                    Personnel personnel = getPersonnelById(baseSalesReport.getBranduser());
                    if (null != personnel) {
                        baseSalesReport.setBrandusername(personnel.getName());
                    } else {
                        baseSalesReport.setBrandusername("其他未定义");
                    }
                } else {
                    baseSalesReport.setBranduser("nodata");
                    baseSalesReport.setBrandusername("其他未指定");
                }
                if (groupcols.indexOf("branddept") == -1) {
                    //分公司
                    Map map2 = new HashMap();
                    map2.put("pid", "");
                    List<DepartMent> deptList = getBaseDepartMentMapper().getDeptListByParam(map2);
                    for (DepartMent dept : deptList) {
                        if (baseSalesReport.getBranddept().indexOf(dept.getId()) == 0) {
                            baseSalesReport.setBranddeptname(dept.getName());
                        }
                    }
                }
            }
            if (groupcols.indexOf("branddept") != -1) {
                if (StringUtils.isNotEmpty(baseSalesReport.getBranddept())) {
                    DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getBranddept());
                    if (null != departMent) {
                        baseSalesReport.setBranddeptname(departMent.getName());
                    } else {
                        baseSalesReport.setBranddeptname("其他未定义");
                    }
                } else {
                    baseSalesReport.setBranddept("nodata");
                    baseSalesReport.setBranddeptname("其他未指定");
                }
            }
            if (groupcols.indexOf("supplierid") != -1) {
                if (StringUtils.isNotEmpty(baseSalesReport.getSupplierid())) {
                    BuySupplier supplier = getSupplierInfoById(baseSalesReport.getSupplierid());
                    if (null != supplier) {
                        baseSalesReport.setSuppliername(supplier.getName());
                    } else {
                        baseSalesReport.setSuppliername("其他未定义");
                    }
                } else {
                    baseSalesReport.setSupplierid("nodata");
                    baseSalesReport.setSuppliername("其他未指定");
                }
            }
            if (groupcols.indexOf("supplieruser") != -1) {
                if (StringUtils.isNotEmpty(baseSalesReport.getSupplieruser())) {
                    if ("QC".equals(baseSalesReport.getSupplieruser())) {
                        baseSalesReport.setSupplierusername("期初");
                    } else {
                        Personnel personnel2 = getPersonnelById(baseSalesReport.getSupplieruser());
                        if (null != personnel2) {
                            baseSalesReport.setSupplierusername(personnel2.getName());
                        } else {
                            baseSalesReport.setSupplierusername("其他未定义");
                        }
                    }
                } else {
                    baseSalesReport.setSupplieruser("nodata");
                    baseSalesReport.setSupplierusername("其他未指定");
                }
            }
            if (groupcols.indexOf("storageid") != -1) {
                if (StringUtils.isNotEmpty(baseSalesReport.getStorageid())) {
                    StorageInfo storageInfo = getStorageInfoByID(baseSalesReport.getStorageid());
                    if (null != storageInfo) {
                        baseSalesReport.setStoragename(storageInfo.getName());
                    } else {
                        baseSalesReport.setStoragename("其他未定义");
                    }
                } else {
                    baseSalesReport.setStoragename("nodata");
                    baseSalesReport.setStoragename("其他未指定");
                }
            }
        }
        int count = salesCustomerReportMapper.showBaseSalesReportDataCount(pageMap);
        PageData pageData = new PageData(count, list, pageMap);
        //取合计数据
        pageMap.getCondition().put("groupcols", "all");
        List<BaseSalesReport> footer = salesCustomerReportMapper.showBaseSalesReportData(pageMap);
        footer = returnBaseSaleReportFooter(footer, pageMap, groupcols);
        pageData.setFooter(footer);
        return pageData;
	}

    /**
     * 获取基础统计报表的页脚数据合计
     * @param footer
     * @param pageMap
     * @param groupcols
     * @return
     * @throws Exception
     */
    public List<BaseSalesReport> returnBaseSaleReportFooter(List<BaseSalesReport> footer,PageMap pageMap ,String groupcols ) throws  Exception {

        for (BaseSalesReport baseSalesReport : footer) {
            if (null != baseSalesReport) {
                BigDecimal sendnum = null != baseSalesReport.getSendnum() ? baseSalesReport.getSendnum() : BigDecimal.ZERO;
                BigDecimal checkreturnnum = null != baseSalesReport.getCheckreturnnum() ? baseSalesReport.getCheckreturnnum() : BigDecimal.ZERO;
                BigDecimal returnnum = null != baseSalesReport.getReturnnum() ? baseSalesReport.getReturnnum() : BigDecimal.ZERO;
                BigDecimal sendtotalbox = null != baseSalesReport.getSendtotalbox() ? baseSalesReport.getSendtotalbox() : BigDecimal.ZERO;
                BigDecimal returntotalbox = null != baseSalesReport.getReturntotalbox() ? baseSalesReport.getReturntotalbox() : BigDecimal.ZERO;
                BigDecimal sendamount = null != baseSalesReport.getSendamount() ? baseSalesReport.getSendamount() : BigDecimal.ZERO;
                BigDecimal returnamount = null != baseSalesReport.getReturnamount() ? baseSalesReport.getReturnamount() : BigDecimal.ZERO;
                BigDecimal pushbalanceamount = null != baseSalesReport.getPushbalanceamount() ? baseSalesReport.getPushbalanceamount() : BigDecimal.ZERO;
                BigDecimal pushbalancenotaxamount = null != baseSalesReport.getPushbalancenotaxamount() ? baseSalesReport.getPushbalancenotaxamount() : BigDecimal.ZERO;
                BigDecimal sendnotaxamount = null != baseSalesReport.getSendnotaxamount() ? baseSalesReport.getSendnotaxamount() : BigDecimal.ZERO;
                BigDecimal returnnotaxamount = null != baseSalesReport.getReturnnotaxamount() ? baseSalesReport.getReturnnotaxamount() : BigDecimal.ZERO;
                BigDecimal costamount = null != baseSalesReport.getCostamount() ? baseSalesReport.getCostamount() : BigDecimal.ZERO;
				BigDecimal saleamount=sendamount.subtract(returnamount).add(pushbalanceamount);;
				BigDecimal checkreturnamount=null!=baseSalesReport.getCheckreturnamount()?baseSalesReport.getCheckreturnamount():BigDecimal.ZERO;

				//退货率=退货金额(不包括直退)/销售金额
				if (saleamount.compareTo(BigDecimal.ZERO) != 0) {
					BigDecimal checkreturnrate = checkreturnamount.divide(saleamount, 6, BigDecimal.ROUND_HALF_UP);
					baseSalesReport.setCheckreturnrate(checkreturnrate.multiply(new BigDecimal(100)));
				}
                //销售数量 = 发货单数量 - 直退数量
                baseSalesReport.setSalenum(sendnum.subtract(returnnum));
                //销售箱数
                baseSalesReport.setSaletotalbox(sendtotalbox.subtract(returntotalbox));
                baseSalesReport.setSaleamount(saleamount);
                baseSalesReport.setSalenotaxamount(sendnotaxamount.subtract(returnnotaxamount).add(pushbalancenotaxamount));
                if (null != baseSalesReport.getSaleamount()) {
                    //毛利额 = 销售金额 - 成本金额
                    baseSalesReport.setSalemarginamount(baseSalesReport.getSaleamount().subtract(costamount));
                    //实际毛利率 = （销售金额 - 成本金额）/销售金额*100
                    if (baseSalesReport.getSaleamount().compareTo(BigDecimal.ZERO) != 0) {
                        BigDecimal realrate = baseSalesReport.getSaleamount().subtract(costamount).divide(baseSalesReport.getSaleamount(), 6, BigDecimal.ROUND_HALF_UP);
                        realrate = realrate.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                        if (baseSalesReport.getSaleamount().compareTo(BigDecimal.ZERO) == -1) {
                            baseSalesReport.setRealrate(realrate.negate());
                        } else {
                            baseSalesReport.setRealrate(realrate);
                        }
                    }
                }
                baseSalesReport.setId(baseSalesReport.getCustomerid());
                //销售税额
                if (null != baseSalesReport.getSalenotaxamount() && null != baseSalesReport.getSaleamount()) {
                    baseSalesReport.setSaletax(baseSalesReport.getSaleamount().subtract(baseSalesReport.getSalenotaxamount()));
                }
                //判断是否为导出使用，若为导出，则将直退数量、售后退货数量显示为负数
                if (pageMap.getCondition().containsKey("exportflag") && null != pageMap.getCondition().get("exportflag")) {
                    //直退数量
                    baseSalesReport.setDirectreturnnum(baseSalesReport.getDirectreturnnum().negate());
                    //直退箱数
                    baseSalesReport.setDirectreturntotalbox(baseSalesReport.getDirectreturntotalbox().negate());
                    //直退金额
                    baseSalesReport.setDirectreturnamount(baseSalesReport.getDirectreturnamount().negate());
                    //售后退货数量
                    baseSalesReport.setCheckreturnnum(baseSalesReport.getCheckreturnnum().negate());
                    //售后退货箱数
                    baseSalesReport.setCheckreturntotalbox(baseSalesReport.getCheckreturntotalbox().negate());
                    //退货金额
                    baseSalesReport.setCheckreturnamount(baseSalesReport.getCheckreturnamount().negate());
                    //退货总数量
                    baseSalesReport.setReturnnum(baseSalesReport.getReturnnum().negate());
                    //退货总箱数
                    baseSalesReport.setReturntotalbox(baseSalesReport.getReturntotalbox().negate());
                    //退货合计
                    baseSalesReport.setReturnamount(baseSalesReport.getReturnamount().negate());
                }
                baseSalesReport.setAuxunitname("");
                baseSalesReport.setUnitname("");
                String[] groupArr = groupcols.split(",");
                if (groupArr[0].indexOf("branddept") != -1) {
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setBranddeptname("合计");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("");
                    baseSalesReport.setStorageid("");
                    baseSalesReport.setStoragename("");
                } else if (groupArr[0].indexOf("branduser") != -1) {
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setBrandusername("合计");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("");
                    baseSalesReport.setStorageid("");
                    baseSalesReport.setStoragename("");
                } else if (groupArr[0].indexOf("brandid") != -1) {
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setBrandname("合计");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("");
                    baseSalesReport.setStorageid("");
                    baseSalesReport.setStoragename("");
                } else if (groupArr[0].indexOf("salesdept") != -1) {
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setSalesdeptname("合计");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("");
                    baseSalesReport.setStorageid("");
                    baseSalesReport.setStoragename("");
                } else if (groupArr[0].indexOf("salesuser") != -1) {
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setSalesusername("合计");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("");
                    baseSalesReport.setStorageid("");
                    baseSalesReport.setStoragename("");
                } else if (groupArr[0].indexOf("goodsid") != -1) {
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setGoodsname("合计");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setGoodssort("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("");
                    baseSalesReport.setStorageid("");
                    baseSalesReport.setStoragename("");
                } else if (groupArr[0].indexOf("goodssort") != -1) {
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setGoodssort("");
                    baseSalesReport.setGoodssortname("合计");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("");
                    baseSalesReport.setStorageid("");
                    baseSalesReport.setStoragename("");
                } else if (groupArr[0].indexOf("pcustomerid") != -1) {
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("合计");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("");
                    baseSalesReport.setStorageid("");
                    baseSalesReport.setStoragename("");
                } else if (groupArr[0].indexOf("customerid") != -1) {
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setCustomername("合计");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("");
                    baseSalesReport.setStorageid("");
                    baseSalesReport.setStoragename("");
                } else if (groupArr[0].indexOf("customersort") != -1) {
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("合计");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("");
                    baseSalesReport.setStorageid("");
                    baseSalesReport.setStoragename("");
                } else if (groupArr[0].indexOf("supplierid") != -1) {
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("合计");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("");
                    baseSalesReport.setStorageid("");
                    baseSalesReport.setStoragename("");
                } else if (groupArr[0].indexOf("supplieruser") != -1) {
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("合计");
                    baseSalesReport.setAddusername("");
                    baseSalesReport.setStorageid("");
                    baseSalesReport.setStoragename("");
                } else if (groupArr[0].indexOf("adduserid") != -1) {
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("合计");
                    baseSalesReport.setStorageid("");
                    baseSalesReport.setStoragename("");
                } else if (groupArr[0].indexOf("storageid") != -1) {
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("");
                    baseSalesReport.setStorageid("");
                    baseSalesReport.setStoragename("合计");
                }
            } else {
                footer = new ArrayList<BaseSalesReport>();
            }
        }
        return  footer ;
    }


    @Override
    public PageData showSalesDeptReportData(PageMap pageMap) throws Exception {
        //小计列
        String groupcols = (String) pageMap.getCondition().get("groupcols");
        if(!pageMap.getCondition().containsKey("groupcols")){
            groupcols = "customerid";
            pageMap.getCondition().put("groupcols", groupcols);
        }
        if(groupcols.equals("adduserid")){
            String dataSql = getDataAccessRule("t_report_adduser_base", "z");
            pageMap.setDataSql(dataSql);
        }else{
            String dataSql = getDataAccessRule("t_report_branduser_dept", "z");
            pageMap.setDataSql(dataSql);
        }
        String query_sql = " 1=1 ";
        String query_sqlall = " 1=1 ";
        String query_sql_push = "";
        if(pageMap.getCondition().containsKey("goodsid")){
            String str = (String) pageMap.getCondition().get("goodsid");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                query_sql += " and t1.goodsid = '"+str+"' ";
                query_sqlall += " and t1.goodsid = '"+str+"' ";
            }
            else{
                query_sql += " and FIND_IN_SET(t1.goodsid,'"+str+"')";
                query_sqlall += " and FIND_IN_SET(t1.goodsid,'"+str+"')";
            }
        }
        if(pageMap.getCondition().containsKey("brandid")){
            String str = (String) pageMap.getCondition().get("brandid");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                query_sql += " and t1.brandid = '"+str+"' ";
                query_sqlall += " and t1.brandid = '"+str+"' ";
            }
            else{
                query_sql += " and FIND_IN_SET(t1.brandid,'"+str+"')";
                query_sqlall += " and FIND_IN_SET(t1.brandid,'"+str+"')";
            }
        }else if(pageMap.getCondition().containsKey("brandids")){
            String str = (String) pageMap.getCondition().get("brandids");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                query_sql += " and t1.brandid = '"+str+"' ";
                query_sqlall += " and t1.brandid = '"+str+"' ";
            }
            else{
                query_sql += " and FIND_IN_SET(t1.brandid,'"+str+"')";
                query_sqlall += " and FIND_IN_SET(t1.brandid,'"+str+"')";
            }
        }

        if(pageMap.getCondition().containsKey("branduser")){
            String str = (String) pageMap.getCondition().get("branduser");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                query_sql += " and t1.branduser = '"+str+"' ";
                query_sqlall += " and t1.branduser = '"+str+"' ";
            }
            else{
                query_sql += " and FIND_IN_SET(t1.branduser,'"+str+"')";
                query_sqlall += " and FIND_IN_SET(t1.branduser,'"+str+"')";
            }
        }
        if(pageMap.getCondition().containsKey("branduserdept")){
            String str = (String) pageMap.getCondition().get("branduserdept");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                query_sql += " and p.belongdeptid = '"+str+"' ";
                query_sqlall += " and p.belongdeptid = '"+str+"' ";
            }
            else{
                query_sql += " and FIND_IN_SET(p.belongdeptid,'"+str+"')";
                query_sqlall += " and FIND_IN_SET(p.belongdeptid,'"+str+"')";
            }
        }

        if(pageMap.getCondition().containsKey("supplieruser")){
            String str = (String) pageMap.getCondition().get("supplieruser");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                query_sql += " and t1.supplieruser = '"+str+"' ";
                query_sqlall += " and t1.supplieruser = '"+str+"' ";
            }
            else{
                query_sql += " and FIND_IN_SET(t1.supplieruser,'"+str+"')";
                query_sqlall += " and FIND_IN_SET(t1.supplieruser,'"+str+"')";
            }
        }
        if(pageMap.getCondition().containsKey("branddept")){
            String str = (String) pageMap.getCondition().get("branddept");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                if(pageMap.getCondition().containsKey("branddept") && "null".equals(pageMap.getCondition().get("branddept"))){
                    query_sql += " and t1.branddept = '' ";
                    query_sqlall += " and t1.branddept = '' ";
                }else{
                    query_sql += " and t1.branddept like '"+str+"%' ";
                    query_sqlall += " and t1.branddept like '"+str+"%' ";
                }
            }else{
                String retStr = "";
                String[] branddeptArr = str.split(",");
                for(String branddept : branddeptArr){
                    Map map = new HashMap();
                    map.put("deptid", branddept);
                    List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
                    if(dList.size() != 0){
                        for(DepartMent departMent : dList){
                            if(StringUtils.isNotEmpty(retStr)){
                                retStr += "," + departMent.getId();
                            }else{
                                retStr = departMent.getId();
                            }
                        }
                    }
                }
                query_sql += " and FIND_IN_SET(t1.branddept,'"+retStr+"')";
                query_sqlall += " and FIND_IN_SET(t1.branddept,'"+retStr+"')";
            }
        }
        if(pageMap.getCondition().containsKey("salesdept")){
            String str = (String) pageMap.getCondition().get("salesdept");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                if(pageMap.getCondition().containsKey("salesdept") && "null".equals(pageMap.getCondition().get("salesdept"))){
                    query_sql += " and t.salesdept = '' ";
                    query_sqlall += " and t.salesdept = '' ";
                }else{
                    query_sql += " and t.salesdept like '"+str+"%' ";
                    query_sqlall += " and t.salesdept like '"+str+"%' ";
                }
            }else{
                String retStr = "";
                String[] salesdeptArr = str.split(",");
                for(String salesdept : salesdeptArr){
                    Map map = new HashMap();
                    map.put("deptid", salesdept);
                    List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
                    if(dList.size() != 0){
                        for(DepartMent departMent : dList){
                            if(StringUtils.isNotEmpty(retStr)){
                                retStr += "," + departMent.getId();
                            }else{
                                retStr = departMent.getId();
                            }
                        }
                    }
                }
                query_sql += " and FIND_IN_SET(t.salesdept,'"+retStr+"')";
                query_sqlall += " and FIND_IN_SET(t.salesdept,'"+retStr+"')";
            }
        }
        if(pageMap.getCondition().containsKey("customersort")){
            String str = (String) pageMap.getCondition().get("customersort");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                query_sql += " and t.customersort like '"+str+"%' ";
                query_sqlall += " and t.customersort like '"+str+"%' ";
            }else{
                query_sql += " and FIND_IN_SET(t.customersort,'"+str+"')";
                query_sqlall += " and FIND_IN_SET(t.customersort,'"+str+"')";
            }
        }
        if(pageMap.getCondition().containsKey("salesarea")){
            String str = (String) pageMap.getCondition().get("salesarea");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                query_sql += " and t.salesarea = '"+str+"' ";
                query_sqlall += " and t.salesarea = '"+str+"' ";
            }else{
                query_sql += " and FIND_IN_SET(t.salesarea,'"+str+"')";
                query_sqlall += " and FIND_IN_SET(t.salesarea,'"+str+"')";
            }
        }
        if(pageMap.getCondition().containsKey("salesuser")){
            String str = (String) pageMap.getCondition().get("salesuser");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                query_sql += " and t.salesuser = '"+str+"' ";
                query_sqlall += " and t.salesuser = '"+str+"' ";
            }else{
                query_sql += " and FIND_IN_SET(t.salesuser,'"+str+"')";
                query_sqlall += " and FIND_IN_SET(t.salesuser,'"+str+"')";
            }
        }
        if(pageMap.getCondition().containsKey("supplierid")){
            String str = (String) pageMap.getCondition().get("supplierid");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                query_sql += " and t1.supplierid = '"+str+"' ";
                query_sqlall += " and t1.supplierid = '"+str+"' ";
            }else{
                query_sql += " and FIND_IN_SET(t1.supplierid,'"+str+"')";
                query_sqlall += " and FIND_IN_SET(t1.supplierid,'"+str+"')";
            }
        }
        if(pageMap.getCondition().containsKey("salesdept")){
            String str = (String) pageMap.getCondition().get("salesdept");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                query_sql += " and t.salesdept = '"+str+"' ";
                query_sqlall += " and t.salesdept = '"+str+"' ";
            }else{
                query_sql += " and FIND_IN_SET(t.salesdept,'"+str+"')";
                query_sqlall += " and FIND_IN_SET(t.salesdept,'"+str+"')";
            }
        }
        if(pageMap.getCondition().containsKey("goodssort")){
            String str = (String) pageMap.getCondition().get("goodssort");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                query_sql += " and t1.goodssort = '"+str+"' ";
            }else{
                query_sql += " and FIND_IN_SET(t1.goodssort,'"+str+"')";
            }
        }
        if(pageMap.getCondition().containsKey("goodstype")){
            String str = (String) pageMap.getCondition().get("goodstype");
            str = StringEscapeUtils.escapeSql(str);
            query_sql += " and FIND_IN_SET(g.goodstype,'"+str+"')";
        }
        if(pageMap.getCondition().containsKey("customerid")){
            String str = (String) pageMap.getCondition().get("customerid");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                query_sql += " and t.customerid = '"+str+"' ";
                query_sqlall += " and t.customerid = '"+str+"' ";
            }
            else{
                query_sql += " and FIND_IN_SET(t.customerid,'"+str+"')";
                query_sqlall += " and FIND_IN_SET(t.customerid,'"+str+"')";
            }
        }
        if(pageMap.getCondition().containsKey("pcustomerid")){
            String str = (String) pageMap.getCondition().get("pcustomerid");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                query_sql += " and t.pcustomerid = '"+str+"' ";
                query_sqlall += " and t.pcustomerid = '"+str+"' ";
            }
            else{
                query_sql += " and FIND_IN_SET(t.pcustomerid,'"+str+"')";
                query_sqlall += " and FIND_IN_SET(t.pcustomerid,'"+str+"')";
            }
        }
        if(pageMap.getCondition().containsKey("adduserid")){
            String str = (String) pageMap.getCondition().get("adduserid");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                query_sql += " and t.adduserid = '"+str+"' ";
                query_sqlall += " and t.adduserid = '"+str+"' ";
            }
            else{
                query_sql += " and FIND_IN_SET(t.adduserid,'"+str+"')";
                query_sqlall += " and FIND_IN_SET(t.adduserid,'"+str+"')";
            }
        }
        if(pageMap.getCondition().containsKey("businessdate1")){
            String str = (String) pageMap.getCondition().get("businessdate1");
            str = StringEscapeUtils.escapeSql(str);
            query_sql += " and t.businessdate >= '"+str+"'";
            query_sqlall += " and t.businessdate >= '"+str+"'";
        }
        if(pageMap.getCondition().containsKey("businessdate2")){
            String str = (String) pageMap.getCondition().get("businessdate2");
            str = StringEscapeUtils.escapeSql(str);
            query_sql += " and t.businessdate <= '"+str+"'";
            query_sqlall += " and t.businessdate <= '"+str+"'";
        }
        if(StringUtils.isNotEmpty(query_sqlall)){
            query_sql_push = query_sqlall.replaceAll("t1.","t.");
            query_sql_push = query_sql_push.replaceAll("t.goodsid","t.brandid");
        }
        pageMap.getCondition().put("query_sql", query_sql);
        pageMap.getCondition().put("query_sqlall", query_sqlall);
        pageMap.getCondition().put("query_sql_push", query_sql_push);
        //排序
        String orderstr = "";
        if(pageMap.getCondition().containsKey("sort")){
            String sort = (String) pageMap.getCondition().get("sort");
            String order = (String) pageMap.getCondition().get("order");
            if(null==order){
                order = "asc";
            }
            orderstr = sort +" " +order;
        }
        pageMap.getCondition().put("orderstr", orderstr);

        List<BaseSalesReport> list = salesCustomerReportMapper.showSalesDeptReportData(pageMap);
        for(BaseSalesReport baseSalesReport : list){
            if(null == baseSalesReport){
                continue;
            }
            BigDecimal sendnum = null != baseSalesReport.getSendnum() ? baseSalesReport.getSendnum() : BigDecimal.ZERO;
            BigDecimal checkreturnnum = null != baseSalesReport.getCheckreturnnum() ? baseSalesReport.getCheckreturnnum() : BigDecimal.ZERO;
            BigDecimal returnnum = null != baseSalesReport.getReturnnum() ? baseSalesReport.getReturnnum() : BigDecimal.ZERO;
            BigDecimal sendtotalbox = null != baseSalesReport.getSendtotalbox() ? baseSalesReport.getSendtotalbox() : BigDecimal.ZERO;
            BigDecimal returntotalbox = null != baseSalesReport.getReturntotalbox() ? baseSalesReport.getReturntotalbox() : BigDecimal.ZERO;
            BigDecimal sendamount = null != baseSalesReport.getSendamount() ? baseSalesReport.getSendamount() : BigDecimal.ZERO;
            BigDecimal returnamount = null != baseSalesReport.getReturnamount() ? baseSalesReport.getReturnamount() : BigDecimal.ZERO;
            BigDecimal pushbalanceamount = null != baseSalesReport.getPushbalanceamount() ?  baseSalesReport.getPushbalanceamount() : BigDecimal.ZERO;
            BigDecimal pushbalancenotaxamount = null != baseSalesReport.getPushbalancenotaxamount() ? baseSalesReport.getPushbalancenotaxamount() : BigDecimal.ZERO;
            BigDecimal sendnotaxamount = null != baseSalesReport.getSendnotaxamount() ? baseSalesReport.getSendnotaxamount() : BigDecimal.ZERO;
            BigDecimal returnnotaxamount = null != baseSalesReport.getReturnnotaxamount() ? baseSalesReport.getReturnnotaxamount() : BigDecimal.ZERO;
            BigDecimal costamount = null != baseSalesReport.getCostamount() ? baseSalesReport.getCostamount() : BigDecimal.ZERO;
			BigDecimal saleamount=sendamount.subtract(returnamount).add(pushbalanceamount);;
			BigDecimal checkreturnamount=null!=baseSalesReport.getCheckreturnamount()?baseSalesReport.getCheckreturnamount():BigDecimal.ZERO;


			//退货率=退货金额(不包括直退)/销售金额
            if(saleamount.compareTo(BigDecimal.ZERO) != 0){
                BigDecimal checkreturnrate = checkreturnamount.divide(saleamount, 6, BigDecimal.ROUND_HALF_UP);
                baseSalesReport.setCheckreturnrate(checkreturnrate.multiply(new BigDecimal(100)));
            }
            //销售数量 = 发货单数量 - 直退数量
            baseSalesReport.setSalenum(sendnum.subtract(returnnum));
            //销售箱数
            baseSalesReport.setSaletotalbox(sendtotalbox.subtract(returntotalbox));
            baseSalesReport.setSaleamount(sendamount.subtract(returnamount).add(pushbalanceamount));
            baseSalesReport.setSalenotaxamount(sendnotaxamount.subtract(returnnotaxamount).add(pushbalancenotaxamount));
            if(null != baseSalesReport.getSaleamount()){
                //毛利额 = 销售金额 - 成本金额
                baseSalesReport.setSalemarginamount(baseSalesReport.getSaleamount().subtract(costamount));
                //实际毛利率 = （销售金额 - 成本金额）/销售金额*100
                if(baseSalesReport.getSaleamount().compareTo(BigDecimal.ZERO)!=0){
                    BigDecimal realrate = baseSalesReport.getSaleamount().subtract(costamount).divide(baseSalesReport.getSaleamount(), 6, BigDecimal.ROUND_HALF_UP);
                    realrate = realrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
                    if(baseSalesReport.getSaleamount().compareTo(BigDecimal.ZERO)==-1){
                        baseSalesReport.setRealrate(realrate.negate());
                    }else{
                        baseSalesReport.setRealrate(realrate);
                    }
                }
            }else if(costamount.compareTo(BigDecimal.ZERO)==1){
                baseSalesReport.setRealrate(new BigDecimal(100).negate());
            }
            baseSalesReport.setId(baseSalesReport.getCustomerid());
            //销售税额
            if(null != baseSalesReport.getSalenotaxamount() && null != baseSalesReport.getSaleamount()){
                baseSalesReport.setSaletax(baseSalesReport.getSaleamount().subtract(baseSalesReport.getSalenotaxamount()));
            }

            //根据获取的商品编码获取条形码
            if(StringUtils.isNotEmpty(baseSalesReport.getGoodsid())){
                GoodsInfo goodsInfo = getGoodsInfoByID(baseSalesReport.getGoodsid());
                if(null != goodsInfo){
                    baseSalesReport.setBarcode(goodsInfo.getBarcode());
                    baseSalesReport.setGoodsname(goodsInfo.getName());
                    baseSalesReport.setSpell(goodsInfo.getSpell());
                }
            }
            //判断是否为导出使用，若为导出，则将直退数量、售后退货数量显示为负数
            if(pageMap.getCondition().containsKey("exportflag") && null != pageMap.getCondition().get("exportflag")){
                //直退数量
                baseSalesReport.setDirectreturnnum(baseSalesReport.getDirectreturnnum().negate());
                //直退箱数
                baseSalesReport.setDirectreturntotalbox(baseSalesReport.getDirectreturntotalbox().negate());
                //直退金额
                baseSalesReport.setDirectreturnamount(baseSalesReport.getDirectreturnamount().negate());
                //售后退货数量
                baseSalesReport.setCheckreturnnum(baseSalesReport.getCheckreturnnum().negate());
                //售后退货箱数
                baseSalesReport.setCheckreturntotalbox(baseSalesReport.getCheckreturntotalbox().negate());
                //退货金额
                baseSalesReport.setCheckreturnamount(baseSalesReport.getCheckreturnamount().negate());
                //退货总数量
                baseSalesReport.setReturnnum(baseSalesReport.getReturnnum().negate());
                //退货总箱数
                baseSalesReport.setReturntotalbox(baseSalesReport.getReturntotalbox().negate());
                //退货合计
                baseSalesReport.setReturnamount(baseSalesReport.getReturnamount().negate());
            }
			if(groupcols.indexOf("customerid")!=-1){
				if(StringUtils.isNotEmpty(baseSalesReport.getCustomerid())){
					Customer customer = getCustomerByID(baseSalesReport.getCustomerid());
					if(null!=customer){
						baseSalesReport.setCustomername(customer.getName());
						baseSalesReport.setShortcode(customer.getShortcode());
					}else{
						baseSalesReport.setCustomername("其他未定义");
					}
				}else{
					baseSalesReport.setCustomerid("nodata");
					baseSalesReport.setCustomername("其他未指定");
				}
				if(groupcols.indexOf("pcustomerid")==-1){
					Customer pcustomer = getCustomerByID(baseSalesReport.getPcustomerid());
					if(null!=pcustomer ){
						baseSalesReport.setPcustomername(pcustomer.getName());
					}
				}
				if(groupcols.indexOf("salesdept")==-1){
					DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getSalesdept());
					if(null!=departMent){
						baseSalesReport.setSalesdeptname(departMent.getName());
					}
				}
				if(groupcols.indexOf("customersort")==-1){
					CustomerSort customerSort = getCustomerSortByID(baseSalesReport.getCustomersort());
					if(null!=customerSort){
						baseSalesReport.setCustomersortname(customerSort.getName());
					}
				}
				if(groupcols.indexOf("salesarea")==-1){
					SalesArea salesArea = getSalesareaByID(baseSalesReport.getSalesarea());
					if(null!=salesArea){
						baseSalesReport.setSalesareaname(salesArea.getThisname());
					}
				}
				if(groupcols.indexOf("salesuser")==-1){
					Personnel personnel = getPersonnelById(baseSalesReport.getSalesuser());
					if(null!=personnel){
						baseSalesReport.setSalesusername(personnel.getName());
					}
					DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getSalesdept());
					if(null!=departMent){
						baseSalesReport.setSalesdeptname(departMent.getName());
					}
				}
			}else{
				baseSalesReport.setCustomerid("");
				baseSalesReport.setCustomername("");
				if(groupcols.indexOf("salesuser")==-1 && groupcols.indexOf("salesdept")==-1){
					baseSalesReport.setSalesdeptname("");
				}
			}
			if(groupcols.indexOf("pcustomerid")!=-1){
				if(StringUtils.isNotEmpty(baseSalesReport.getPcustomerid())){
					Customer pcustomer = getCustomerByID(baseSalesReport.getPcustomerid());
					if(null!=pcustomer ){
						baseSalesReport.setPcustomername(pcustomer.getName());
					}else{
						baseSalesReport.setPcustomername("其他未定义");
					}
				}else{
					baseSalesReport.setPcustomerid("nodata");
					baseSalesReport.setPcustomername("其他未指定");
				}
				if(groupcols.indexOf("customerid")==1){
					baseSalesReport.setCustomerid("");
					baseSalesReport.setCustomername("");
				}
			}
			if(groupcols.indexOf("customersort")!=-1){
				if(StringUtils.isNotEmpty(baseSalesReport.getCustomersort())){
					CustomerSort customerSort = getCustomerSortByID(baseSalesReport.getCustomersort());
					if(null!=customerSort){
						baseSalesReport.setCustomersortname(customerSort.getName());
					}else{
						baseSalesReport.setCustomersortname("其他未定义");
					}
				}else{
					baseSalesReport.setCustomersort("nodata");
					baseSalesReport.setCustomersortname("其他未指定");
				}
			}
			if(groupcols.indexOf("salesarea")!=-1){
				if(StringUtils.isNotEmpty(baseSalesReport.getSalesarea())){
					SalesArea salesArea = getSalesareaByID(baseSalesReport.getSalesarea());
					if(null!=salesArea){
						baseSalesReport.setSalesareaname(salesArea.getThisname());
					}else{
						baseSalesReport.setSalesareaname("其他未定义");
					}
				}else{
					baseSalesReport.setSalesarea("nodata");
					baseSalesReport.setSalesareaname("其他未指定");
				}
			}
			if(groupcols.indexOf("salesdept")!=-1){
				if(StringUtils.isNotEmpty(baseSalesReport.getSalesdept())){
					DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getSalesdept());
					if(null!=departMent){
						baseSalesReport.setSalesdeptname(departMent.getName());
					}else{
						baseSalesReport.setSalesdeptname("其他未定义");
					}
				}else{
					baseSalesReport.setSalesdept("nodata");
					baseSalesReport.setSalesdeptname("其他未指定");
				}
			}
			if(groupcols.indexOf("salesuser")!=-1){
				if(StringUtils.isNotEmpty(baseSalesReport.getSalesuser())){
					Personnel personnel = getPersonnelById(baseSalesReport.getSalesuser());
					if(null!=personnel){
						baseSalesReport.setSalesusername(personnel.getName());
					}else{
						baseSalesReport.setSalesusername("其他未定义");
					}
				}else{
					baseSalesReport.setSalesuser("nodata");
					baseSalesReport.setSalesusername("其他未指定");
				}
				DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getSalesdept());
				if(null!=departMent){
					baseSalesReport.setSalesdeptname(departMent.getName());
				}
				if(groupcols.indexOf("salesarea")==-1){
					SalesArea salesArea = getSalesareaByID(baseSalesReport.getSalesarea());
					if(null!=salesArea){
						baseSalesReport.setSalesareaname(salesArea.getThisname());
					}
				}
			}
			if(groupcols.indexOf("goodsid")!=-1){
				GoodsInfo goodsInfo = getAllGoodsInfoByID(baseSalesReport.getGoodsid());
				if(null!=goodsInfo){
					baseSalesReport.setGoodsname(goodsInfo.getName());
					baseSalesReport.setSpell(goodsInfo.getSpell());
					WaresClass waresClass = getBaseGoodsMapper().getWaresClassInfo(baseSalesReport.getGoodssort());
					if(null != waresClass){
						baseSalesReport.setGoodssortname(waresClass.getThisname());
					}
					if(StringUtils.isNotEmpty(goodsInfo.getGoodstype())){
						baseSalesReport.setGoodstype(goodsInfo.getGoodstype());
						SysCode goodstype = getBaseSysCodeMapper().getSysCodeInfo(goodsInfo.getGoodstype(), "goodstype");
						if(null != goodstype){
							baseSalesReport.setGoodstypename(goodstype.getCodename());
						}
					}
					BuySupplier buySupplier = getSupplierInfoById(baseSalesReport.getSupplierid());
					if(null != buySupplier){
						baseSalesReport.setSuppliername(buySupplier.getName());
					}
				}else{
					Brand brand = getGoodsBrandByID(baseSalesReport.getGoodsid());
					if(null!=brand){
						baseSalesReport.setGoodsname("（折扣）"+brand.getName());
					}else{
						baseSalesReport.setGoodsname("（折扣）其他");
					}
				}
				if(groupcols.indexOf("brandid")==-1){
					Brand brand = getGoodsBrandByID(baseSalesReport.getBrandid());
					if(null!=brand){
						baseSalesReport.setBrandname(brand.getName());
					}
					Personnel personnel = getPersonnelById(baseSalesReport.getBranduser());
					if(null!=personnel){
						baseSalesReport.setBrandusername(personnel.getName());
					}
					if("QC".equals(baseSalesReport.getSupplieruser())){
						baseSalesReport.setSupplierusername("期初");
					}else{
						Personnel personnel2 = getPersonnelById(baseSalesReport.getSupplieruser());
						if(null!=personnel2){
							baseSalesReport.setSupplierusername(personnel2.getName());
						}
					}
				}
				if(groupcols.indexOf("branddept")==-1){
					DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getBranddept());
					if(null!=departMent){
						baseSalesReport.setBranddeptname(departMent.getName());
					}
				}
				if(groupcols.indexOf("branduser")==-1){
					Personnel personnel = getPersonnelById(baseSalesReport.getBranduser());
					if(null!=personnel){
						baseSalesReport.setBrandusername(personnel.getName());
					}
				}
			}
			if(groupcols.indexOf("goodssort")!=-1){
				if(StringUtils.isNotEmpty(baseSalesReport.getGoodssort())){
					WaresClass waresClass = getBaseGoodsMapper().getWaresClassInfo(baseSalesReport.getGoodssort());
					if(null != waresClass){
						baseSalesReport.setGoodssortname(waresClass.getThisname());
					}else{
						baseSalesReport.setGoodssortname("其他未定义");
					}
				}else{
					baseSalesReport.setGoodssort("nodata");
					baseSalesReport.setGoodssortname("其他未指定");
				}
			}
			if(groupcols.indexOf("brandid")!=-1){
				if(StringUtils.isNotEmpty(baseSalesReport.getBrandid())){
					Brand brand = getGoodsBrandByID(baseSalesReport.getBrandid());
					if(null!=brand){
						baseSalesReport.setBrandname(brand.getName());
					}else{
						baseSalesReport.setBrandname("其他未定义");
					}
				}else{
					baseSalesReport.setBrandid("nodata");
					baseSalesReport.setBrandname("其他未指定");
				}
				if(groupcols.indexOf("branddept")==-1){
					DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getBranddept());
					if(null!=departMent){
						baseSalesReport.setBranddeptname(departMent.getName());
					}
				}
				Personnel personnel = getPersonnelById(baseSalesReport.getBranduser());
				if(null!=personnel){
					baseSalesReport.setBrandusername(personnel.getName());
				}
				if("QC".equals(baseSalesReport.getSupplieruser())){
					baseSalesReport.setSupplierusername("期初");
				}else{
					Personnel personnel2 = getPersonnelById(baseSalesReport.getSupplieruser());
					if(null!=personnel2){
						baseSalesReport.setSupplierusername(personnel2.getName());
					}
				}
			}
			//品牌业务员所属部门
			if(groupcols.indexOf("branduserdept")!=-1){
				if(StringUtils.isNotEmpty(baseSalesReport.getBranduserdept())){
					DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getBranduserdept());
					if(null!=departMent){
						baseSalesReport.setBranduserdeptname(departMent.getName());
					}else{
						baseSalesReport.setBranduserdeptname("其他未定义");
					}
				}else{
					baseSalesReport.setBranduserdept("nodata");
					baseSalesReport.setBranduserdeptname("其他未指定");
				}
			}
			if(groupcols.indexOf("branduser")!=-1){
				if(StringUtils.isNotEmpty(baseSalesReport.getBranduser())){
					Personnel personnel = getPersonnelById(baseSalesReport.getBranduser());
					if(null!=personnel){
						baseSalesReport.setBrandusername(personnel.getName());
					}else{
						baseSalesReport.setBrandusername("其他未定义");
					}
				}else{
					baseSalesReport.setBranduser("nodata");
					baseSalesReport.setBrandusername("其他未指定");
				}
				if(StringUtils.isNotEmpty(baseSalesReport.getBranduserdept())){
					DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getBranduserdept());
					if(null!=departMent){
						baseSalesReport.setBranduserdeptname(departMent.getName());
					}else{
						baseSalesReport.setBranduserdeptname("其他未定义");
					}
				}else{
					baseSalesReport.setBranduserdept("nodata");
					baseSalesReport.setBranduserdeptname("其他未指定");
				}
				if(groupcols.indexOf("branddept")==-1){
					//分公司
					Map map2 = new HashMap();
					map2.put("pid", "");
					List<DepartMent> deptList = getBaseDepartMentMapper().getDeptListByParam(map2);
					for(DepartMent dept : deptList){
						if(baseSalesReport.getBranddept().indexOf(dept.getId()) == 0){
							baseSalesReport.setBranddeptname(dept.getName());
						}
					}
				}
			}
			if(groupcols.indexOf("branddept")!=-1){
				if(StringUtils.isNotEmpty(baseSalesReport.getBranddept())){
					DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getBranddept());
					if(null!=departMent){
						baseSalesReport.setBranddeptname(departMent.getName());
					}else{
						baseSalesReport.setBranddeptname("其他未定义");
					}
				}else{
					baseSalesReport.setBranddept("nodata");
					baseSalesReport.setBranddeptname("其他未指定");
				}
			}
			if(groupcols.indexOf("supplierid")!=-1){
				if(StringUtils.isNotEmpty(baseSalesReport.getSupplierid())){
					BuySupplier supplier = getSupplierInfoById(baseSalesReport.getSupplierid());
					if(null != supplier){
						baseSalesReport.setSuppliername(supplier.getName());
					}else{
						baseSalesReport.setSuppliername("其他未定义");
					}
				}else{
					baseSalesReport.setSupplierid("nodata");
					baseSalesReport.setSuppliername("其他未指定");
				}
			}
			if(groupcols.indexOf("supplieruser")!=-1){
				if(StringUtils.isNotEmpty(baseSalesReport.getSupplieruser())){
					if("QC".equals(baseSalesReport.getSupplieruser())){
						baseSalesReport.setSupplierusername("期初");
					}else{
						Personnel personnel2 = getPersonnelById(baseSalesReport.getSupplieruser());
						if(null!=personnel2){
							baseSalesReport.setSupplierusername(personnel2.getName());
						}else{
							baseSalesReport.setSupplierusername("其他未定义");
						}
					}
				}else{
					baseSalesReport.setSupplieruser("nodata");
					baseSalesReport.setSupplierusername("其他未指定");
				}
			}
        }
        int count = salesCustomerReportMapper.showSalesDeptReportDataCount(pageMap);
        PageData pageData = new PageData(count,list,pageMap);
        //取合计数据
        pageMap.getCondition().put("groupcols", "all");
        List<BaseSalesReport> footer = salesCustomerReportMapper.showSalesDeptReportData(pageMap);
        for(BaseSalesReport baseSalesReport : footer){
            if(null!=baseSalesReport){
                BigDecimal sendnum = null != baseSalesReport.getSendnum() ? baseSalesReport.getSendnum() : BigDecimal.ZERO;
                BigDecimal checkreturnnum = null != baseSalesReport.getCheckreturnnum() ? baseSalesReport.getCheckreturnnum() : BigDecimal.ZERO;
                BigDecimal returnnum = null != baseSalesReport.getReturnnum() ? baseSalesReport.getReturnnum() : BigDecimal.ZERO;
                BigDecimal sendtotalbox = null != baseSalesReport.getSendtotalbox() ? baseSalesReport.getSendtotalbox() : BigDecimal.ZERO;
                BigDecimal returntotalbox = null != baseSalesReport.getReturntotalbox() ? baseSalesReport.getReturntotalbox() : BigDecimal.ZERO;
                BigDecimal sendamount = null != baseSalesReport.getSendamount() ? baseSalesReport.getSendamount() : BigDecimal.ZERO;
                BigDecimal returnamount = null != baseSalesReport.getReturnamount() ? baseSalesReport.getReturnamount() : BigDecimal.ZERO;
                BigDecimal pushbalanceamount = null != baseSalesReport.getPushbalanceamount() ?  baseSalesReport.getPushbalanceamount() : BigDecimal.ZERO;
                BigDecimal pushbalancenotaxamount = null != baseSalesReport.getPushbalancenotaxamount() ? baseSalesReport.getPushbalancenotaxamount() : BigDecimal.ZERO;
                BigDecimal sendnotaxamount = null != baseSalesReport.getSendnotaxamount() ? baseSalesReport.getSendnotaxamount() : BigDecimal.ZERO;
                BigDecimal returnnotaxamount = null != baseSalesReport.getReturnnotaxamount() ? baseSalesReport.getReturnnotaxamount() : BigDecimal.ZERO;
                BigDecimal costamount = null != baseSalesReport.getCostamount() ? baseSalesReport.getCostamount() : BigDecimal.ZERO;
				BigDecimal saleamount=sendamount.subtract(returnamount).add(pushbalanceamount);;
				BigDecimal checkreturnamount=null!=baseSalesReport.getCheckreturnamount()?baseSalesReport.getCheckreturnamount():BigDecimal.ZERO;

				//退货率=退货金额(不包括直退)/销售金额
                if(saleamount.compareTo(BigDecimal.ZERO) != 0){
                    BigDecimal checkreturnrate = checkreturnamount.divide(saleamount, 6, BigDecimal.ROUND_HALF_UP);
                    baseSalesReport.setCheckreturnrate(checkreturnrate.multiply(new BigDecimal(100)));
                }
                //销售数量 = 发货单数量 - 直退数量
                baseSalesReport.setSalenum(sendnum.subtract(returnnum));
                //销售箱数
                baseSalesReport.setSaletotalbox(sendtotalbox.subtract(returntotalbox));
                baseSalesReport.setSaleamount(sendamount.subtract(returnamount).add(pushbalanceamount));
                baseSalesReport.setSalenotaxamount(sendnotaxamount.subtract(returnnotaxamount).add(pushbalancenotaxamount));
                if(null != baseSalesReport.getSaleamount()){
                    //毛利额 = 销售金额 - 成本金额
                    baseSalesReport.setSalemarginamount(baseSalesReport.getSaleamount().subtract(costamount));
                    //实际毛利率 = （销售金额 - 成本金额）/销售金额*100
                    if(baseSalesReport.getSaleamount().compareTo(BigDecimal.ZERO)!=0){
                        BigDecimal realrate = baseSalesReport.getSaleamount().subtract(costamount).divide(baseSalesReport.getSaleamount(), 6, BigDecimal.ROUND_HALF_UP);
                        realrate = realrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
                        if(baseSalesReport.getSaleamount().compareTo(BigDecimal.ZERO)==-1){
                            baseSalesReport.setRealrate(realrate.negate());
                        }else{
                            baseSalesReport.setRealrate(realrate);
                        }
                    }
                }
                baseSalesReport.setId(baseSalesReport.getCustomerid());
                //销售税额
                if(null != baseSalesReport.getSalenotaxamount() && null != baseSalesReport.getSaleamount()){
                    baseSalesReport.setSaletax(baseSalesReport.getSaleamount().subtract(baseSalesReport.getSalenotaxamount()));
                }
                //判断是否为导出使用，若为导出，则将直退数量、售后退货数量显示为负数
                if(pageMap.getCondition().containsKey("exportflag") && null != pageMap.getCondition().get("exportflag")){
                    //直退数量
                    baseSalesReport.setDirectreturnnum(baseSalesReport.getDirectreturnnum().negate());
                    //直退箱数
                    baseSalesReport.setDirectreturntotalbox(baseSalesReport.getDirectreturntotalbox().negate());
                    //直退金额
                    baseSalesReport.setDirectreturnamount(baseSalesReport.getDirectreturnamount().negate());
                    //售后退货数量
                    baseSalesReport.setCheckreturnnum(baseSalesReport.getCheckreturnnum().negate());
                    //售后退货箱数
                    baseSalesReport.setCheckreturntotalbox(baseSalesReport.getCheckreturntotalbox().negate());
                    //退货金额
                    baseSalesReport.setCheckreturnamount(baseSalesReport.getCheckreturnamount().negate());
                    //退货总数量
                    baseSalesReport.setReturnnum(baseSalesReport.getReturnnum().negate());
                    //退货总箱数
                    baseSalesReport.setReturntotalbox(baseSalesReport.getReturntotalbox().negate());
                    //退货合计
                    baseSalesReport.setReturnamount(baseSalesReport.getReturnamount().negate());
                }
                baseSalesReport.setAuxunitname("");
                baseSalesReport.setUnitname("");
                String[] groupArr = groupcols.split(",");
                if(groupArr[0].indexOf("branddept")!=-1){
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setBranddeptname("合计");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("");
                }else if(groupArr[0].indexOf("branduser")!=-1){
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setBrandusername("合计");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("");
                }else if(groupArr[0].indexOf("brandid")!=-1){
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setBrandname("合计");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("");
                }else if(groupArr[0].indexOf("salesdept")!=-1){
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setSalesdeptname("合计");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("");
                }else if(groupArr[0].indexOf("salesuser")!=-1){
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setSalesusername("合计");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("");
                }else if(groupArr[0].indexOf("goodsid")!=-1){
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setGoodsname("合计");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setGoodssort("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("");
                }else if(groupArr[0].indexOf("goodssort")!=-1){
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setGoodssort("");
                    baseSalesReport.setGoodssortname("合计");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("");
                }else if(groupArr[0].indexOf("pcustomerid")!=-1){
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("合计");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("");
                }else if(groupArr[0].indexOf("customerid")!=-1){
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setCustomername("合计");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("");
                }else if(groupArr[0].indexOf("customersort")!=-1){
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("合计");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("");
                }else if(groupArr[0].indexOf("supplierid")!=-1){
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("合计");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("");
                }else if(groupArr[0].indexOf("supplieruser")!=-1){
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("合计");
                    baseSalesReport.setAddusername("");
                }else if(groupArr[0].indexOf("adduserid")!=-1){
                    baseSalesReport.setPcustomerid("");
                    baseSalesReport.setPcustomername("");
                    baseSalesReport.setBranddeptname("");
                    baseSalesReport.setBranddept("");
                    baseSalesReport.setBrandid("");
                    baseSalesReport.setBrandname("");
                    baseSalesReport.setSalesdept("");
                    baseSalesReport.setSalesdeptname("");
                    baseSalesReport.setSalesuser("");
                    baseSalesReport.setSalesusername("");
                    baseSalesReport.setGoodsid("");
                    baseSalesReport.setGoodsname("");
                    baseSalesReport.setGoodssortname("");
                    baseSalesReport.setGoodstypename("");
                    baseSalesReport.setBranduser("");
                    baseSalesReport.setBrandusername("");
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setCustomername("");
                    baseSalesReport.setCustomersort("");
                    baseSalesReport.setCustomersortname("");
                    baseSalesReport.setSupplierid("");
                    baseSalesReport.setSuppliername("");
                    baseSalesReport.setSupplieruser("");
                    baseSalesReport.setSupplierusername("");
                    baseSalesReport.setAddusername("合计");
                }
            }else{
                footer = new ArrayList<BaseSalesReport>();
            }
        }
        pageData.setFooter(footer);
        return pageData;
    }


	@Override
	public PageData showSalesCorrespondPeriodReportData(PageMap pageMap) throws Exception{
		String dataSql = getDataAccessRule("t_report_sales_base", "z");
		pageMap.setDataSql(dataSql);
		Map condition=pageMap.getCondition();
		//小计列
		String groupcols = (String) condition.get("groupcols");
		if(null==groupcols || "".equals(groupcols.trim())) {
			condition.put("groupcols", "customerid");
			groupcols = "customerid";
		}
		if(condition.containsKey("brandid")){
			String str = (String) condition.get("brandid");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") >0){
				condition.put("isbrandarr", "1");
			}
		}
		if(condition.containsKey("customerid")){
			String str = (String) condition.get("customerid");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") >0){
				condition.put("iscustomerarr", "1");
			}
		}
		if(condition.containsKey("pcustomerid")){
			String str = (String) condition.get("pcustomerid");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") >0){
				condition.put("ispcustomerarr", "1");
			}
		}
		if(condition.containsKey("branduser")){
			String str = (String) condition.get("branduser");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") >0){
				condition.put("isbranduserarr", "1");
			}
		}
		if(condition.containsKey("salesuser")){
            String str = (String) condition.get("salesuser");
            str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") >0){
				condition.put("issalesuserarr", "1");
			}
		}
		if(condition.containsKey("branddept")){
			String str = (String) condition.get("branddept");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") >0){
				condition.put("isbranddeptarr", "1");
			}
		}
		if(condition.containsKey("salesdept")){
			String str = (String) condition.get("salesdept");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") >0){
				condition.put("isdeptidarr", "1");
			}
		}
		if(condition.containsKey("salesarea")){
			String str = (String) condition.get("salesarea");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") >0){
				condition.put("issalesareaarr", "1");
			}
		}
		if(condition.containsKey("goodsid")){
			String str = (String) condition.get("goodsid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") >0){
				condition.put("isgoodsidarr", "1");
			}
		}
		if(condition.containsKey("goodssort")){
			String str = (String) condition.get("goodssort");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") >0){
				condition.put("isgoodssortidarr", "1");
			}
		}
		if(condition.containsKey("supplierid")){
			condition.put("querywithsupplier", "1");
			String str = (String) condition.get("supplierid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") >0){
				condition.put("issupplieridarr", "1");
			}
		}
		if(groupcols.indexOf("supplierid")!=-1){
			condition.put("querywithsupplier", "1");			
		}
		List<SalesCorrespondPeriodReport> list = salesCustomerReportMapper.showSalesCorrespondPeriodReportData(pageMap);
		for(SalesCorrespondPeriodReport item : list){
			if(null!=item){
				item.setId(item.getCustomerid());
				//销售箱数增长率 ：(本期销售箱数-前期销售箱数)/前期销售箱数
				item.setSaletotalboxgrowth(BigDecimal.ZERO);
				//销售增长率 ：(本期销售-前期销售)/前期销售
				item.setSalegrowth(BigDecimal.ZERO);
				//销售毛利增长率 ： (本期毛利-前期毛利)/前期毛利
				item.setSalegrossgrowth(BigDecimal.ZERO);
				//销售毛利率：(本期销售-成本金额)/本期销售 或 本期毛利金额/本期销售
				item.setSalegrossrate(BigDecimal.ZERO);
				//前期销售毛利率：(前期销售-前期成本金额)/前期销售 或 前期毛利金额/前期销售
				item.setQqsalegrossrate(BigDecimal.ZERO);
				//销售毛利率增长率：(本期销售毛利率-前期销售毛利率)/前期销售毛利率
				item.setSalegrossrategrowth(BigDecimal.ZERO);
				//回笼增长率：(本期回笼-前期回笼) / 前期回笼 
				item.setWriteoffgrowth(BigDecimal.ZERO);
				//回笼毛利率：(回笼金额-回笼成本金额) / 回笼金额   或   回笼毛利金额 / 回笼金额
				item.setWriteoffgrate(BigDecimal.ZERO);
				//前期回笼毛利率：(前期回笼金额-前期回笼成本金额) / 前期回笼金额  或 前期回笼毛利金额  / 回笼金额
				item.setQqwriteoffgrate(BigDecimal.ZERO);
				//回笼毛利率增长率：(本期回笼率-前期回笼率) / 前期回笼率
				item.setWriteoffgrategrowth(BigDecimal.ZERO);
				//回笼毛利金额增长率 ： (本期回笼毛利金额 - 前期回笼毛利金额) / 前期回笼毛利金额 
				item.setWriteoffgrossgrowth(BigDecimal.ZERO);

				//前期回笼毛利 为null时，设置为零
				if(null == item.getQqwriteoffgrossamount()){
					item.setQqwriteoffgrossamount(BigDecimal.ZERO);
				}
				//回笼毛利金额为null时，设置为零
				if(null == item.getWriteoffgrossamount()){
					item.setWriteoffgrossamount(BigDecimal.ZERO);
				}
				//前期回笼金额为null 时，设置为零
				if(null == item.getQqwriteoffamount()){
					item.setQqwriteoffamount(BigDecimal.ZERO);
				}

				if(item.getQqsaletotalbox().compareTo(BigDecimal.ZERO)!=0){
					//销售箱数增长率 ：(本期销售箱数-前期销售箱数)/前期销售箱数(绝对值)
					item.setSaletotalboxgrowth(item.getSaletotalbox().subtract(item.getQqsaletotalbox()).divide(item.getQqsaletotalbox().abs(), 6, BigDecimal.ROUND_HALF_UP));
					item.setSaletotalboxgrowth(item.getSaletotalboxgrowth().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
				}
				if(item.getSaleamount().compareTo(BigDecimal.ZERO)!=0){
					//销售毛利率：(本期销售-成本金额)/本期销售 或 本期毛利金额/本期销售
					item.setSalegrossrate(item.getSalegrossamount().divide(item.getSaleamount(), 6, BigDecimal.ROUND_HALF_UP)); 
					item.setSalegrossrate(item.getSalegrossrate().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
					//销售为负时，销售毛利率为负
					if(item.getSaleamount().compareTo(BigDecimal.ZERO)<0){
						item.setSalegrossrate(item.getSalegrossrate().negate());
					}
				}
				if(item.getQqsaleamount().compareTo(BigDecimal.ZERO)!=0){
					//销售增长率 ：(本期销售-前期销售)/前期销售(绝对值)
					item.setSalegrowth(item.getSaleamount().subtract(item.getQqsaleamount()).divide(item.getQqsaleamount().abs(), 6, BigDecimal.ROUND_HALF_UP));
					item.setSalegrowth(item.getSalegrowth().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
					//前期销售毛利率：(前期销售-前期成本金额)/前期销售 或 前期毛利金额/前期销售
					item.setQqsalegrossrate(item.getQqsalegrossamount().divide(item.getQqsaleamount(), 6, BigDecimal.ROUND_HALF_UP));
					item.setQqsalegrossrate(item.getQqsalegrossrate().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
					//前期销售为负时，前期销售毛利率为负
					if(item.getQqsaleamount().compareTo(BigDecimal.ZERO)<0){
						item.setQqsalegrossrate(item.getQqsalegrossrate().negate());
					}
					
					if(item.getQqsalegrossrate().compareTo(BigDecimal.ZERO)!=0){
						//销售毛利率增长率：(本期销售毛利率-前期销售毛利率)/前期销售毛利率(绝对值)
						item.setSalegrossrategrowth(item.getSalegrossrate().subtract(item.getQqsalegrossrate()).divide(item.getQqsalegrossrate().abs(), 6, BigDecimal.ROUND_HALF_UP));
						item.setSalegrossrategrowth(item.getSalegrossrategrowth().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
					}
				}
				if(item.getQqsalegrossamount().compareTo(BigDecimal.ZERO)!=0){
					//销售毛利增长率 ： (本期毛利-前期毛利)/前期毛利(绝对值)
					item.setSalegrossgrowth(item.getSalegrossamount().subtract(item.getQqsalegrossamount()).divide(item.getQqsalegrossamount().abs(), 6, BigDecimal.ROUND_HALF_UP));
					item.setSalegrossgrowth(item.getSalegrossgrowth().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
				}
				if(item.getQqwriteoffamount().compareTo(BigDecimal.ZERO)!=0){
					//回笼增长率：(本期回笼-前期回笼) / 前期回笼 (绝对值)
					item.setWriteoffgrowth(item.getWriteoffamount().subtract(item.getQqwriteoffamount()).divide(item.getQqwriteoffamount().abs(), 6, BigDecimal.ROUND_HALF_UP));
					item.setWriteoffgrowth(item.getWriteoffgrowth().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
				}
				if(item.getWriteoffamount().compareTo(BigDecimal.ZERO)!=0){
					//回笼毛利率：(回笼金额-回笼成本金额) / 回笼金额
					item.setWriteoffgrate(item.getWriteoffgrossamount().divide(item.getWriteoffamount(),6, BigDecimal.ROUND_HALF_UP));
					item.setWriteoffgrate(item.getWriteoffgrate().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
					//回笼为负时，回笼毛利率为负
					if(item.getWriteoffamount().compareTo(BigDecimal.ZERO)<0){
						item.setWriteoffgrate(item.getWriteoffgrate().negate());
					}
				}
				if(item.getQqwriteoffamount().compareTo(BigDecimal.ZERO)!=0){
				
					//前期回笼毛利率：(前期回笼金额-前期回笼成本金额) / 前期回笼金额
					item.setQqwriteoffgrate(item.getQqwriteoffgrossamount().divide(item.getQqwriteoffamount(),6, BigDecimal.ROUND_HALF_UP));
					item.setQqwriteoffgrate(item.getQqwriteoffgrate().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
					
					//回笼为负时，回笼毛利率为负
					if(item.getQqwriteoffamount().compareTo(BigDecimal.ZERO)<0){
						item.setQqwriteoffgrate(item.getQqwriteoffamount().negate());
					}
				}
				if(item.getQqwriteoffgrate().compareTo(BigDecimal.ZERO)!=0){
					//回笼率增长率：(本期回笼率-前期回笼率) / 前期回笼率(绝对值)
					item.setWriteoffgrategrowth(item.getWriteoffgrate().subtract(item.getQqwriteoffgrate()).divide(item.getQqwriteoffgrate().abs(),6, BigDecimal.ROUND_HALF_UP));
					item.setWriteoffgrategrowth(item.getWriteoffgrategrowth().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
				}
				if(item.getQqwriteoffgrossamount().compareTo(BigDecimal.ZERO)!=0){
					//回笼毛利金额增长率 ： (本期回笼毛利金额 - 前期回笼毛利金额) / 前期回笼毛利金额 (绝对值)
					item.setWriteoffgrossgrowth(item.getWriteoffgrossamount().subtract(item.getQqwriteoffgrossamount()).divide(item.getQqwriteoffgrossamount().abs(),6, BigDecimal.ROUND_HALF_UP));
					item.setWriteoffgrossgrowth(item.getWriteoffgrossgrowth().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
				}
				if(StringUtils.isNotEmpty(item.getSupplierid())){
					BuySupplier buySupplier=getSupplierInfoById(item.getSupplierid());
					if(null!=buySupplier){
						item.setSuppliername(buySupplier.getName());
					}
				}
				if(StringUtils.isEmpty(item.getSuppliername())){
					item.setSuppliername("其他");
				}
				if(groupcols.indexOf("customerid")!=-1){
					Customer customer = getCustomerByID(item.getCustomerid());
					if(null!=customer){
						item.setCustomername(customer.getName());
					}
					if(groupcols.indexOf("pcustomerid")==-1){
						Customer pcustomer = getCustomerByID(item.getPcustomerid());
						if(null!=pcustomer ){
							item.setPcustomername(pcustomer.getName());
						}
					}
					if(groupcols.indexOf("salesdept")==-1){
						DepartMent departMent = getDepartmentByDeptid(item.getSalesdept());
						if(null!=departMent){
							item.setSalesdeptname(departMent.getName());
						}else{
							item.setSalesdeptname("其他");
						}
					}
					if(groupcols.indexOf("branduser")==-1){
						if(StringUtils.isNotEmpty(item.getBranduser())){
							Personnel personnel = getPersonnelById(item.getBranduser());
							if(null!=personnel){
								item.setBrandusername(personnel.getName());
							}else{
								item.setBrandusername("其他");
							}
						}else{
							item.setBrandusername("其他");
						}
					}
					if(groupcols.indexOf("salesarea")==-1){
						SalesArea salesArea = getSalesareaByID(item.getSalesarea());
						if(null!=salesArea){
							item.setSalesareaname(salesArea.getName());
						}else{
							item.setSalesareaname("其他");
						}
					}

					if(groupcols.indexOf("salesuser")==-1){
						if(StringUtils.isNotEmpty(item.getSalesuser())){
							Personnel personnel = getPersonnelById(item.getSalesuser());
							if(null!=personnel){
								item.setSalesusername(personnel.getName());
							}else{
								item.setSalesusername("其他");
							}
						}else{
							item.setSalesusername("其他");
						}
					}
				}else{
					item.setCustomerid("");
					item.setCustomername("");
					
				}
				if(groupcols.indexOf("pcustomerid")!=-1){
					Customer pcustomer = getCustomerByID(item.getPcustomerid());
					if(null!=pcustomer ){
						item.setPcustomername(pcustomer.getName());
					}else{
						item.setPcustomername("其他客户总和");
					}
					if(groupcols.indexOf("customerid")==1){
						item.setCustomerid("");
						item.setCustomername("");
						
					}
					
				}
				if(groupcols.indexOf("salesarea")!=-1){
					SalesArea salesArea = getSalesareaByID(item.getSalesarea());
					if(null!=salesArea){
						item.setSalesareaname(salesArea.getName());
					}else{
						item.setSalesareaname("其他");
					}
				}
				if(groupcols.indexOf("salesuser")!=-1){
					if(StringUtils.isNotEmpty(item.getSalesuser())){
						Personnel personnel = getPersonnelById(item.getSalesuser());
						if(null!=personnel){
							item.setSalesusername(personnel.getName());
						}else{
							item.setSalesusername("其他");
						}
					}else{
						item.setSalesusername("其他");
					}
					if(StringUtils.isNotEmpty(item.getSalesarea())){
						SalesArea salesArea = getSalesareaByID(item.getSalesarea());
						if(null!=salesArea){
							item.setSalesareaname(salesArea.getName());
						}else{
							item.setSalesareaname("其他");
						}
					}
				}
				if(groupcols.indexOf("salesdept")!=-1){
					if(StringUtils.isNotEmpty(item.getSalesdept())){
						DepartMent departMent = getDepartmentByDeptid(item.getSalesdept());
						if(null!=departMent){
							item.setSalesdeptname(departMent.getName());
						}else{
							item.setSalesdeptname("其他");
						}
					}else{
						item.setSalesdeptname("其他");						
					}
				}
				if(groupcols.indexOf("goodsid")!=-1){
					GoodsInfo goodsInfo = getAllGoodsInfoByID(item.getGoodsid());
					if(null!=goodsInfo){
						item.setBarcode(goodsInfo.getBarcode());
						item.setGoodsname(goodsInfo.getName());
					}else{
						Brand brand = getGoodsBrandByID(item.getGoodsid());
						if(null!=brand){
							item.setGoodsname("（折扣）"+brand.getName());
						}else{
							item.setGoodsname("（折扣）其他");
						}
					}

					if(groupcols.indexOf("goodssort")==-1){
						WaresClass waresClass = getBaseGoodsMapper().getWaresClassInfo(item.getGoodssort());
						if (waresClass != null) {
							item.setGoodssortname(waresClass.getName());
						}else{
							item.setGoodssortname("其他");
						}
					}
					if(groupcols.indexOf("brandid")==-1){
						Brand brand = getGoodsBrandByID(item.getBrandid());
						if(null!=brand){
							item.setBrandname(brand.getName());
						}
					}
					if(groupcols.indexOf("branddept")==-1){
						DepartMent departMent = getDepartmentByDeptid(item.getBranddept());
						if(null!=departMent){
							item.setBranddeptname(departMent.getName());
						}else{
							item.setBranddeptname("其他");
						}
					}
					if(groupcols.indexOf("branduser")==-1){
						if(StringUtils.isNotEmpty(item.getBranduser())){
							Personnel personnel = getPersonnelById(item.getBranduser());
							if(null!=personnel){
								item.setBrandusername(personnel.getName());
							}else{
								item.setBrandusername("其他");
							}
						}else{
							item.setBrandusername("其他");
						}
					}
				}else{
					item.setGoodsid("");
				}

				if(groupcols.indexOf("goodssort")!=-1){
					WaresClass waresClass = getBaseGoodsMapper().getWaresClassInfo(item.getGoodssort());
					if (waresClass != null) {
						item.setGoodssortname(waresClass.getName());
					}else{
						item.setGoodssortname("其他");
					}
				}
				if(groupcols.indexOf("brandid")!=-1){
					Brand brand = getGoodsBrandByID(item.getBrandid());
					if(null!=brand){
						item.setBrandname(brand.getName());
					}else{
						item.setBrandname("其他");
					}
				}
				if(groupcols.indexOf("branduser")!=-1){
					if(StringUtils.isNotEmpty(item.getBranduser())){
						Personnel personnel = getPersonnelById(item.getBranduser());
						if(null!=personnel){
							item.setBrandusername(personnel.getName());
						}else{
							item.setBrandusername("其他");
						}
					}else{
						item.setBrandusername("其他");
					}
					if(groupcols.indexOf("brand")==-1){
						item.setBrandname("");
					}
					if(groupcols.indexOf("branddept")==-1){
						DepartMent departMent = getDepartmentByDeptid(item.getBranddept());
						if(null!=departMent){
							item.setBranddeptname(departMent.getName());
						}else{
							item.setBranddeptname("其他");
						}
					}
				}
				if(groupcols.indexOf("branddept")!=-1){
					DepartMent departMent = getDepartmentByDeptid(item.getBranddept());
					if(null!=departMent){
						item.setBranddeptname(departMent.getName());
					}else{
						item.setBranddeptname("其他");
					}
				}
			}
		}
		int total=salesCustomerReportMapper.showSalesCorrespondPeriodReportCount(pageMap);
		PageData pageData=new PageData(total, list, pageMap);
		//取合计数据
		condition.put("groupcols", "all");
		List<SalesCorrespondPeriodReport> footer = salesCustomerReportMapper.showSalesCorrespondPeriodReportData(pageMap);
		if(null!=footer && footer.size()>0 && !(footer.size()==1 && null ==footer.get(0))){
			for(SalesCorrespondPeriodReport item : footer){
				if(null!=item){
					item.setId(item.getCustomerid());
					item.setGoodsid("");
	
					item.setCustomerid("");
					item.setCustomername("合计");
					item.setSalesdeptname("");
					

					//销售箱数增长率 ：(本期销售箱数-前期销售箱数)/前期销售箱数
					item.setSaletotalboxgrowth(BigDecimal.ZERO);
					//销售增长率 ：(本期销售-前期销售)/前期销售
					item.setSalegrowth(BigDecimal.ZERO);
					//销售毛利增长率 ： (本期毛利-前期毛利)/前期毛利
					item.setSalegrossgrowth(BigDecimal.ZERO);
					//销售毛利率：(本期销售-成本金额)/本期销售 或 本期毛利金额/本期销售
					item.setSalegrossrate(BigDecimal.ZERO);
					//前期销售毛利率：(前期销售-前期成本金额)/前期销售 或 前期毛利金额/前期销售
					item.setQqsalegrossrate(BigDecimal.ZERO);
					//销售毛利率增长率：(本期销售毛利率-前期销售毛利率)/前期销售毛利率
					item.setSalegrossrategrowth(BigDecimal.ZERO);
					//回笼增长率：(本期回笼-前期回笼) / 前期回笼 
					item.setWriteoffgrowth(BigDecimal.ZERO);
					//回笼毛利率：(回笼金额-回笼成本金额) / 回笼金额   或   回笼毛利金额 / 回笼金额
					item.setWriteoffgrate(BigDecimal.ZERO);
					//前期回笼毛利率：(前期回笼金额-前期回笼成本金额) / 前期回笼金额  或 前期回笼毛利金额  / 回笼金额
					item.setQqwriteoffgrate(BigDecimal.ZERO);
					//回笼毛利率增长率：(本期回笼率-前期回笼率) / 前期回笼率
					item.setWriteoffgrategrowth(BigDecimal.ZERO);
					//回笼毛利金额增长率 ： (本期回笼毛利金额 - 前期回笼毛利金额) / 前期回笼毛利金额 
					item.setWriteoffgrossgrowth(BigDecimal.ZERO);

					
					//前期回笼毛利 为null时，设置为零
					if(null == item.getQqwriteoffgrossamount()){
						item.setQqwriteoffgrossamount(BigDecimal.ZERO);
					}
					//回笼毛利金额为null时，设置为零
					if(null == item.getWriteoffgrossamount()){
						item.setWriteoffgrossamount(BigDecimal.ZERO);
					}
					//前期回笼金额为null 时，设置为零
					if(null == item.getQqwriteoffamount()){
						item.setQqwriteoffamount(BigDecimal.ZERO);
					}

					if(item.getQqsaletotalbox().compareTo(BigDecimal.ZERO)!=0){
						//销售箱数增长率 ：(本期销售箱数-前期销售箱数)/前期销售箱数(绝对值)
						item.setSaletotalboxgrowth(item.getSaletotalbox().subtract(item.getQqsaletotalbox()).divide(item.getQqsaletotalbox().abs(), 6, BigDecimal.ROUND_HALF_UP));
						item.setSaletotalboxgrowth(item.getSaletotalboxgrowth().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
					}
					if(item.getSaleamount().compareTo(BigDecimal.ZERO)!=0){
						//销售毛利率：(本期销售-成本金额)/本期销售 或 本期毛利金额/本期销售
						item.setSalegrossrate(item.getSalegrossamount().divide(item.getSaleamount(), 6, BigDecimal.ROUND_HALF_UP)); 
						item.setSalegrossrate(item.getSalegrossrate().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
						//销售为负时，销售毛利率为负
						if(item.getSaleamount().compareTo(BigDecimal.ZERO)<0){
							item.setSalegrossrate(item.getSalegrossrate().negate());
						}
					}
					if(item.getQqsaleamount().compareTo(BigDecimal.ZERO)!=0){
						//销售增长率 ：(本期销售-前期销售)/前期销售(绝对值)
						item.setSalegrowth(item.getSaleamount().subtract(item.getQqsaleamount()).divide(item.getQqsaleamount().abs(), 6, BigDecimal.ROUND_HALF_UP));
						item.setSalegrowth(item.getSalegrowth().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
						//前期销售毛利率：(前期销售-前期成本金额)/前期销售 或 前期毛利金额/前期销售
						item.setQqsalegrossrate(item.getQqsalegrossamount().divide(item.getQqsaleamount(), 6, BigDecimal.ROUND_HALF_UP));
						item.setQqsalegrossrate(item.getQqsalegrossrate().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
						//前期销售为负时，前期销售毛利率为负
						if(item.getQqsaleamount().compareTo(BigDecimal.ZERO)<0){
							item.setQqsalegrossrate(item.getQqsalegrossrate().negate());
						}

						if(item.getQqsalegrossrate().compareTo(BigDecimal.ZERO)!=0 && null != item.getSalegrossrate()){
							//销售毛利率增长率：(本期销售毛利率-前期销售毛利率)/前期销售毛利率(绝对值)
							item.setSalegrossrategrowth(item.getSalegrossrate().divide(item.getQqsalegrossrate().abs(), 6, BigDecimal.ROUND_HALF_UP));
							item.setSalegrossrategrowth(item.getSalegrossrategrowth().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
						}
					}
					if(item.getQqsalegrossamount().compareTo(BigDecimal.ZERO)!=0){
						//销售毛利增长率 ： (本期毛利-前期毛利)/前期毛利(绝对值)
						item.setSalegrossgrowth(item.getSalegrossamount().subtract(item.getQqsalegrossamount()).divide(item.getQqsalegrossamount().abs(), 6, BigDecimal.ROUND_HALF_UP));
						item.setSalegrossgrowth(item.getSalegrossgrowth().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
					}
					if(item.getQqwriteoffamount().compareTo(BigDecimal.ZERO)!=0){
						//回笼增长率：(本期回笼-前期回笼) / 前期回笼 (绝对值)
						item.setWriteoffgrowth(item.getWriteoffamount().subtract(item.getQqwriteoffamount()).divide(item.getQqwriteoffamount().abs(), 6, BigDecimal.ROUND_HALF_UP));
						item.setWriteoffgrowth(item.getWriteoffgrowth().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
					}
					if(null!=item.getWriteoffgrossamount() && item.getWriteoffamount().compareTo(BigDecimal.ZERO)!=0){
						//回笼毛利率：(回笼金额-回笼成本金额) / 回笼金额
						item.setWriteoffgrate(item.getWriteoffgrossamount().divide(item.getWriteoffamount(),6, BigDecimal.ROUND_HALF_UP));
						item.setWriteoffgrate(item.getWriteoffgrate().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
						//回笼为负时，回笼毛利率为负
						if(item.getWriteoffamount().compareTo(BigDecimal.ZERO)<0){
							item.setWriteoffgrate(item.getWriteoffgrate().negate());
						}
					}
					if( item.getQqwriteoffamount().compareTo(BigDecimal.ZERO)!=0){
						//前期回笼毛利率：(前期回笼金额-前期回笼成本金额) / 前期回笼金额
						item.setQqwriteoffgrate(item.getQqwriteoffgrossamount().divide(item.getQqwriteoffamount(),6, BigDecimal.ROUND_HALF_UP));
						item.setQqwriteoffgrate(item.getQqwriteoffgrate().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
						
						//回笼为负时，回笼毛利率为负
						if(item.getQqwriteoffamount().compareTo(BigDecimal.ZERO)<0){
							item.setQqwriteoffgrate(item.getQqwriteoffamount().negate());
						}
					}
					if(null != item.getWriteoffgrate() && null != item.getQqwriteoffgrate() && item.getQqwriteoffgrate().compareTo(BigDecimal.ZERO)!=0){
						//回笼率增长率：(本期回笼率-前期回笼率) / 前期回笼率(绝对值)
						item.setWriteoffgrategrowth(item.getWriteoffgrate().subtract(item.getQqwriteoffgrate()).divide(item.getQqwriteoffgrate().abs(),6, BigDecimal.ROUND_HALF_UP));
						item.setWriteoffgrategrowth(item.getWriteoffgrategrowth().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
					}
					if(item.getQqwriteoffgrossamount().compareTo(BigDecimal.ZERO)!=0){
						//回笼毛利金额增长率 ： (本期回笼毛利金额 - 前期回笼毛利金额) / 前期回笼毛利金额 (绝对值)
						item.setWriteoffgrossgrowth(item.getWriteoffgrossamount().subtract(item.getQqwriteoffgrossamount()).divide(item.getQqwriteoffgrossamount().abs(),6, BigDecimal.ROUND_HALF_UP));
						item.setWriteoffgrategrowth(item.getWriteoffgrossgrowth().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
					}
				}
			}
		}else{
			footer=new ArrayList<SalesCorrespondPeriodReport>();
		}
		pageData.setFooter(footer);
		return pageData;
	}

	/**
	 * 获取部门采购销售汇总报表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-11-28
	 */
	public PageData showBuySalesBillCountReportData(PageMap pageMap) throws Exception{
		String dataSql = getDataAccessRule("t_report_buysalebillcount", null);
		pageMap.setDataSql(dataSql);
		//小计列
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		if(!pageMap.getCondition().containsKey("groupcols")){
			pageMap.getCondition().put("groupcols", "goodsid");
			groupcols = "goodsid";
		}
		if(pageMap.getCondition().containsKey("brand")){
			String str = (String) pageMap.getCondition().get("brand");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") >0){
				pageMap.getCondition().put("isbrandarr", "1");
			}
		}
		if(pageMap.getCondition().containsKey("branduser")){
			String str = (String) pageMap.getCondition().get("branduser");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") >0){
				pageMap.getCondition().put("isbranduserarr", "1");
			}
		}
		if(pageMap.getCondition().containsKey("branddept")){
			String str = (String) pageMap.getCondition().get("branddept");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") >0){
				pageMap.getCondition().put("isbranddeptarr", "1");
			}
		}
		if(pageMap.getCondition().containsKey("goodsid")){
			String str = (String) pageMap.getCondition().get("goodsid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") >0){
				pageMap.getCondition().put("isgoodsidarr", "1");
			}
		}
		if(pageMap.getCondition().containsKey("supplierid")){
			String str = (String) pageMap.getCondition().get("supplierid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") >0){
				pageMap.getCondition().put("issupplieridarr", "1");
			}
		}
		List<BuySalesBillCountReport> list = salesCustomerReportMapper.showBuySalesBillCountReportData(pageMap);
		
		for(BuySalesBillCountReport item : list){
			if(null!=item){
				item.setId(item.getGoodsid());
				//销售毛利率：(本期销售-成本金额)/本期销售 或 本期毛利金额/本期销售
				item.setSalegrossrate(BigDecimal.ZERO);
				//回笼毛利率：(回笼金额-回笼成本金额) / 回笼金额   或   回笼毛利金额 / 回笼金额
				item.setWriteoffgrate(BigDecimal.ZERO);
				if(item.getSaleamount().compareTo(BigDecimal.ZERO)!=0){
					//销售毛利率：(本期销售-成本金额)/本期销售 或 本期毛利金额/本期销售
					item.setSalegrossrate(item.getSalegrossamount().divide(item.getSaleamount(), 6, BigDecimal.ROUND_HALF_UP)); 
					item.setSalegrossrate(item.getSalegrossrate().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
				}
				if(null!=item.getWriteoffgrossamount() && item.getWriteoffamount().compareTo(BigDecimal.ZERO)!=0){
					//回笼毛利率：(回笼金额-回笼成本金额) / 回笼金额
					item.setWriteoffgrate(item.getWriteoffgrossamount().divide(item.getWriteoffamount(),6, BigDecimal.ROUND_HALF_UP));
					item.setWriteoffgrate(item.getWriteoffgrate().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
				}
				
				if(groupcols.indexOf("goodsid")!=-1){
					GoodsInfo goodsInfo = getAllGoodsInfoByID(item.getGoodsid());
					if(null!=goodsInfo){
						item.setBarcode(goodsInfo.getBarcode());
						item.setGoodsname(goodsInfo.getName());
					}else{
						Brand brand = getGoodsBrandByID(item.getGoodsid());
						if(null!=brand){
							item.setGoodsname("（折扣）"+brand.getName());
						}else{
							item.setGoodsname("（折扣）其他");
						}
					}
					if(groupcols.indexOf("brand")==-1){
						Brand brand = getGoodsBrandByID(item.getBrand());
						if(null!=brand){
							item.setBrandname(brand.getName());
						}
					}
					if(groupcols.indexOf("branddept")==-1){
						DepartMent departMent = getDepartmentByDeptid(item.getBranddept());
						if(null!=departMent){
							item.setBranddeptname(departMent.getName());
						}else{
							item.setBranddeptname("其他");
						}
					}
					if(groupcols.indexOf("branduser")==-1){
						if(StringUtils.isNotEmpty(item.getBranduser())){
							Personnel personnel = getPersonnelById(item.getBranduser());
							if(null!=personnel){
								item.setBrandusername(personnel.getName());
							}else{
								item.setBrandusername("其他");
							}
						}else{
							item.setBrandusername("其他");
						}
					}
					if(groupcols.indexOf("supplierid")==-1){
						if(StringUtils.isNotEmpty(item.getSupplierid())){
							//供应商名称
							BuySupplier buySupplier = getSupplierInfoById(item.getSupplierid());
							if(null != buySupplier){
								item.setSuppliername(buySupplier.getName());
							}
						}
					}
				}else{
					item.setGoodsid("");
				}
				if(groupcols.indexOf("brand")!=-1){
					Brand brand = getGoodsBrandByID(item.getBrand());
					if(null!=brand){
						item.setBrandname(brand.getName());
					}else{
						item.setBrandname("其他");
					}
					if(groupcols.indexOf("branddept")==-1){
						DepartMent departMent = getDepartmentByDeptid(item.getBranddept());
						if(null!=departMent){
							item.setBranddeptname(departMent.getName());
						}else{
							item.setBranddeptname("其他");
						}
					}
					if(StringUtils.isNotEmpty(item.getBranduser())){
						Personnel personnel = getPersonnelById(item.getBranduser());
						if(null!=personnel){
							item.setBrandusername(personnel.getName());
						}else{
							item.setBrandusername("其他");
						}
					}else{
						item.setBrandusername("其他");
					}
				}
				if(groupcols.indexOf("branduser")!=-1){
					if(StringUtils.isNotEmpty(item.getBranduser())){
						Personnel personnel = getPersonnelById(item.getBranduser());
						if(null!=personnel){
							item.setBrandusername(personnel.getName());
						}else{
							item.setBrandusername("其他");
						}
					}else{
						item.setBrandusername("其他");
					}
					if(groupcols.indexOf("brand")==-1){
						item.setBrandname("");
					}
					if(groupcols.indexOf("branddept")==-1){
						DepartMent departMent = getDepartmentByDeptid(item.getBranddept());
						if(null!=departMent){
							item.setBranddeptname(departMent.getName());
						}else{
							item.setBranddeptname("其他");
						}
					}
				}
				if(groupcols.indexOf("branddept")!=-1){
					DepartMent departMent = getDepartmentByDeptid(item.getBranddept());
					if(null!=departMent){
						item.setBranddeptname(departMent.getName());
					}else{
						item.setBranddeptname("其他");
					}
				}

				if(groupcols.indexOf("supplierid")!=-1){
					if(StringUtils.isNotEmpty(item.getSupplierid())){
						//供应商名称
						BuySupplier buySupplier = getSupplierInfoById(item.getSupplierid());
						if(null != buySupplier){
							item.setSuppliername(buySupplier.getName());
						}
					}
				}
				item.setBillcount(item.getBuybillcount()+item.getSalebillcount());
			}
			
		}
		int total=salesCustomerReportMapper.showBuySalesBillCountReportCount(pageMap);
		PageData pageData=new PageData(total, list, pageMap);
		//取合计数据
		pageMap.getCondition().put("groupcols", "all");
		List<BuySalesBillCountReport> footer = salesCustomerReportMapper.showBuySalesBillCountReportData(pageMap);
		if(null!=footer && footer.size()>0 && !(footer.size()==1 && null ==footer.get(0))){
			for(BuySalesBillCountReport item : footer){
				if(null!=item){
					item.setId(item.getGoodsid());
//					item.setGoodsid("");
//					item.setBrandusername("合计");
//					item.setBranddept("");
	
					item.setBillcount(item.getBuybillcount()+item.getSalebillcount());
					
					if(groupcols.indexOf(",") == -1){
						if(groupcols.indexOf("branddept")!=-1){
							item.setBranddept("");
							item.setBranddeptname("合计");
						}
						else if(groupcols.indexOf("brand")!=-1){
							if(groupcols.indexOf("branduser")!=-1){
								item.setBranduser("");
								item.setBrandusername("合计");
							}
							else{
								item.setBrand("");
								item.setBrandname("合计");
							}
						}
						else if(groupcols.indexOf("branduser")!=-1){
							item.setBranduser("");
							item.setBrandusername("合计");
						}
						else if(groupcols.indexOf("goodsid")!=-1){
							item.setGoodsid("");
							item.setGoodsname("合计");
						}
					}else{
						String[] groupArr = groupcols.split(",");
						if(groupArr[0].indexOf("branddept")!=-1){
							item.setBranddeptname("合计");
							item.setBranddept("");
							item.setBrand("");
							item.setBranduser("");
							item.setGoodsid("");
							item.setBrandname("");
							item.setBrandusername("");
							item.setGoodsname("");
						}
						else if(groupArr[0].indexOf("brand")!=-1){
							item.setBranddeptname("");
							item.setBranddept("");
							item.setBrand("");
							item.setBranduser("");
							item.setGoodsid("");
							item.setBrandname("合计");
							item.setBrandusername("");
							item.setGoodsname("");
						}
						else if(groupArr[0].indexOf("branduser")!=-1){
							item.setBranddeptname("");
							item.setBranddept("");
							item.setBrand("");
							item.setBranduser("");
							item.setGoodsid("");
							item.setBrandname("");
							item.setBrandusername("合计");
							item.setGoodsname("");
						}
						else if(groupArr[0].indexOf("goodsid")!=-1){
							item.setBranddeptname("");
							item.setBranddept("");
							item.setBrand("");
							item.setBranduser("");
							item.setGoodsid("");
							item.setBrandname("");
							item.setBrandusername("");
							item.setGoodsname("合计");
						}
					}
				}
			}
		}else{
			footer=new ArrayList<BuySalesBillCountReport>();
		}
		pageData.setFooter(footer);
		return pageData;
		
	}
	@Override
	public PageData showSalesOrderTrackReportData(PageMap pageMap)
			throws Exception {
		String dataSql = getDataAccessRule("t_sales_order", "t");
		pageMap.setDataSql(dataSql);
		List<Map> list = salesCustomerReportMapper.showSalesOrderTrackReportData(pageMap);
		BigDecimal indexOrdernum=new BigDecimal("0");
		BigDecimal indexOrderamount=new BigDecimal("0");
		BigDecimal indexOrdertotalbox=new BigDecimal("0");
		for(Map map:list){
            String sourcetype = "0";
            if(map.containsKey("sourcetype")){
                sourcetype = (String) map.get("sourcetype");
            }
            if("0".equals(sourcetype)){
                map.put("sourcetypename","普通订单");
            }else if("1".equals(sourcetype)){
                map.put("sourcetypename","手机订单");
            }else if("2".equals(sourcetype)){
                map.put("sourcetypename","零售车销");
            }
			String goodsid1 = (String) map.get("goodsid1");
			String goodsid2 = (String) map.get("goodsid2");
			String goodsid="";
			if(goodsid2!=null)
				goodsid=goodsid2;
			else
				goodsid=goodsid1;
			GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
			if(null!=goodsInfo){
				map.put("goodsname", goodsInfo.getName());
				map.put("barcode", goodsInfo.getBarcode());
				map.put("goodsid", goodsid);
                map.put("spell",goodsInfo.getSpell());
			}
			//start
			if(goodsid2!=null&&!goodsid2.equals(goodsid1)){
				BigDecimal ordernum=(BigDecimal)map.get("ordernum");
				BigDecimal ordertotalbox=(BigDecimal)map.get("ordertotalbox");
				BigDecimal orderamount=(BigDecimal)map.get("orderamount");
				indexOrdernum=indexOrdernum.add(ordernum);
				indexOrderamount=indexOrderamount.add(orderamount);
				indexOrdertotalbox=indexOrdertotalbox.add(ordertotalbox);
				map.put("ordernum", 0);
				map.put("ordertotalbox", 0);
				map.put("orderamount", 0);
				map.put("taxprice", 0);
			}
			//end
			String customerid = (String) map.get("customerid");
			Customer customer = getCustomerByID(customerid);
			if(null!=customer){
				map.put("customername", customer.getName());
			}
			String pcustomerid= (String) map.get("pcustomerid");
			Customer pCustomer = getCustomerByID(pcustomerid);
			if(null!=pCustomer){
				map.put("pcustomername", pCustomer.getName());
			}
			String brandid = (String) map.get("brandid");
			Brand brand = getGoodsBrandByID(brandid);
			if(null!=brand){
				map.put("brandname", brand.getName());
			}
			String salesuser = (String)map.get("salesuser");
			Personnel personnel = getPersonnelById(salesuser);
			if(null != personnel){
				map.put("salesusername", personnel.getName());
			}
			String indooruserid = (String)map.get("indooruserid");
			Personnel personnel1 = getPersonnelById(indooruserid);
			if(null != personnel1){
				map.put("indoorusername", personnel1.getName());
			}
			BigDecimal initprice = getGoodsPriceByCustomer(goodsid, customerid);
			map.put("initprice", initprice);
			BigDecimal salesamount = (BigDecimal) map.get("salesamount");
			BigDecimal salemarginamount = (BigDecimal) map.get("salemarginamount");
			if(null==salemarginamount){
				salemarginamount = BigDecimal.ZERO;
			}
			if(null!=salesamount ){
				if(salesamount.compareTo(BigDecimal.ZERO)!=0){
					//销售毛利率：(本期销售-成本金额)/本期销售 或 本期毛利金额/本期销售
					BigDecimal rate = salemarginamount.divide(salesamount, 6, BigDecimal.ROUND_HALF_UP);
					map.put("rate", rate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
				}else{
					map.put("rate", new BigDecimal(100).negate());
				}
			}
			String goodssort = (String)map.get("goodssort");
			WaresClass waresClass = getWaresClassByID(goodssort);
			if(null != waresClass){
				map.put("goodssortname", waresClass.getName());
			}
		}
		PageData pageData = new PageData(salesCustomerReportMapper.showSalesOrderTrackReportCount(pageMap),list,pageMap);
		pageMap.getCondition().put("groupcols", "all");
		List<Map> footer = salesCustomerReportMapper.showSalesOrderTrackReportDataSum(pageMap);
		for(Map map : footer){
			if(null!=map){
				map.put("price", null);
				map.put("orderid", null);
				map.put("businessdate", "合计");
				map.put("customerid", null);
				map.put("goodsid", null);
				map.put("unitname", null);
				
				BigDecimal ordernum=(BigDecimal)map.get("ordernum");
				BigDecimal ordertotalbox=(BigDecimal)map.get("ordertotalbox");
				BigDecimal orderamount=(BigDecimal)map.get("orderamount");
				map.put("ordernum",ordernum.subtract(indexOrdernum));
				map.put("ordertotalbox", ordertotalbox.subtract(indexOrdertotalbox));
				map.put("orderamount", orderamount.subtract(indexOrderamount));
				
				BigDecimal salesamount = (BigDecimal) map.get("salesamount");
				BigDecimal salemarginamount = (BigDecimal) map.get("salemarginamount");
				if(null==salemarginamount){
					salemarginamount = BigDecimal.ZERO;
				}
				if(null!=salesamount ){
					if(salesamount.compareTo(BigDecimal.ZERO)!=0){
						//销售毛利率：(本期销售-成本金额)/本期销售 或 本期毛利金额/本期销售
						BigDecimal rate = salemarginamount.divide(salesamount, 6, BigDecimal.ROUND_HALF_UP);
						map.put("rate", rate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
					}else{
						map.put("rate", new BigDecimal(100).negate());
					}
				}
			}else{
				footer = new ArrayList<Map>();
			}
		}
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public PageData getSalesRejectTrackReportData(PageMap pageMap) throws Exception {
		List<Map> list = salesCustomerReportMapper.getSalesRejectTrackReportList(pageMap);
		for(Map map: list){
			String goodsid = (String) map.get("goodsid");
			GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
			if(null!=goodsInfo){
				map.put("goodsname", goodsInfo.getName());
				map.put("barcode", goodsInfo.getBarcode());
			}
			String customerid = (String) map.get("customerid");
			Customer customer = getCustomerByID(customerid);
			if(null!=customer){
				map.put("customername", customer.getName());
			}
			String brandid = (String) map.get("brandid");
			Brand brand = getGoodsBrandByID(brandid);
			if(null!=brand){
				map.put("brandname", brand.getName());
			}
		}
		pageMap.getCondition().put("ispageflag", false);
		List<Map> l = salesCustomerReportMapper.getSalesRejectTrackReportList(pageMap);
		List<Map> footer = new ArrayList<Map>();
		Map m = new HashMap();
		BigDecimal tnum = BigDecimal.ZERO;
		BigDecimal tamount = BigDecimal.ZERO;
		BigDecimal rnum = BigDecimal.ZERO;
		BigDecimal ramount = BigDecimal.ZERO;
		for(Map map : l){
			BigDecimal tn = (BigDecimal)map.get("qnum");
			BigDecimal ta = (BigDecimal)map.get("qamount");
			BigDecimal rn = (BigDecimal)map.get("tunitnum");
			BigDecimal ra = (BigDecimal)map.get("ttaxamount");
			tnum = tnum.add(tn == null?BigDecimal.ZERO:tn);
			tamount = tamount.add(ta == null?BigDecimal.ZERO:ta);
			rnum = rnum.add(rn == null?BigDecimal.ZERO:rn);
			ramount = ramount.add(ra == null?BigDecimal.ZERO:ra);
		}
		m.put("businessdate", "合计");
		m.put("qnum", tnum);
		m.put("qamount", tamount);
		m.put("tunitnum", rnum);
		m.put("ttaxamount", ramount);
		PageData pageData = new PageData(l.size(), list, pageMap);
		footer.add(m);
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public List getUNJIASalesReportData(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_report_sales_base", "z");
		pageMap.setDataSql(dataSql);
		List<SalesUNJIAReport> list = salesCustomerReportMapper.getUNJIASalesReportData(pageMap);
		if(null != list && list.size() != 0){
			for(SalesUNJIAReport salesUNJIAReport : list){
				if(null != salesUNJIAReport){
					salesUNJIAReport.setCustomerid1(salesUNJIAReport.getCustomerid());
					//时间格式转换yyyy-mm-dd 转 yyyyMMdd
					 SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
				     SimpleDateFormat sf2 =new SimpleDateFormat("yyyy-MM-dd");
					if(StringUtils.isNotEmpty(salesUNJIAReport.getBusinessdate())){
						String fmtdate = sf1.format(sf2.parse(salesUNJIAReport.getBusinessdate()));
						salesUNJIAReport.setBusinessdate(fmtdate);
						salesUNJIAReport.setBusinessdate1(fmtdate);
						salesUNJIAReport.setBusinessdate2(fmtdate);
					}
				}
			}
		}
		return list;
	}

	@Override
	public PageData showSalesGoodsSandPriceReportData(PageMap pageMap) throws Exception{
		String dataSql = getDataAccessRule("t_base_sales_customer", "cus");
		Map condition=pageMap.getCondition();
		if(null!=dataSql && !"".equals(dataSql)){
			condition.put("customerDataSql", dataSql);
		}else{
			if(condition.containsKey("customerDataSql")){
				condition.remove("customerDataSql");
			}
		}
		List<SalesCustomerPriceReport> list=salesCustomerReportMapper.getSalesGoodsSandPriceReportData(pageMap);
		for(SalesCustomerPriceReport item : list){
			if(StringUtils.isNotEmpty(item.getGoodsid())){
				GoodsInfo goodsInfo = getAllGoodsInfoByID(item.getGoodsid());
				if(null!=goodsInfo){
					item.setGoodsname(goodsInfo.getName());
					item.setBrandid(goodsInfo.getBrand());
					item.setNewbuyprice(goodsInfo.getNewbuyprice());
					if(goodsInfo.getNewbuyprice().compareTo(BigDecimal.ZERO) != 0){
						if( null != item.getPrice() && item.getPrice().compareTo(BigDecimal.ZERO) != 0){
							BigDecimal rate = (item.getPrice().subtract(goodsInfo.getNewbuyprice())).divide(item.getPrice(),BigDecimal.ROUND_HALF_DOWN,6);
							item.setRate(rate.multiply(new BigDecimal(100)));
						}
					}else{
						item.setRate(new BigDecimal(100));
					}
					if(StringUtils.isNotEmpty(item.getBrandid())){
						Brand brand = getBaseGoodsMapper().getBrandInfo(item.getBrandid());
						if(null!=brand){
							item.setBrandname(brand.getName());
						}				
					}
				}
			}
			if(StringUtils.isNotEmpty(item.getCustomerid())){
				Customer customer = getCustomerByID(item.getCustomerid());
				if(null!=customer){
					item.setCustomername(customer.getName());
				}
			}
			if(StringUtils.isNotEmpty(item.getCustomerid()) && StringUtils.isNotEmpty(item.getGoodsid())){
				GoodsInfo_PriceInfo priceInfo = getPriceInfo(item.getGoodsid(),item.getCustomerid()); //客户的价格套信息
				if( null != priceInfo ){
					item.setTaxprice(priceInfo.getTaxprice());
					item.setPricetype(priceInfo.getName());
				}
			}else{
				item.setTaxprice(BigDecimal.ZERO);
				item.setPricetype("");
			}

		}
		int count=salesCustomerReportMapper.getSalesGoodsSandPriceReportDataCount(pageMap);
		PageData pageData=new PageData(count,list,pageMap);
		return pageData;
	}
	@Override
	public PageData showSalesGoodsTradeReportData(PageMap pageMap) throws Exception{
		String dataSql = getDataAccessRule("t_base_sales_customer", "cus");
		Map condition=pageMap.getCondition();
		if(null!=dataSql && !"".equals(dataSql)){
			condition.put("customerDataSql", dataSql);
		}else{
			if(condition.containsKey("customerDataSql")){
				condition.remove("customerDataSql");
			}
		}
		
		List<SalesCustomerPriceReport> list=salesCustomerReportMapper.getSalesGoodsTradeReportData(pageMap);
		for(SalesCustomerPriceReport item : list){
			if(StringUtils.isNotEmpty(item.getGoodsid())){
				GoodsInfo goodsInfo = getAllGoodsInfoByID(item.getGoodsid());
				if(null!=goodsInfo){
					item.setGoodsname(goodsInfo.getName());
					item.setBrandid(goodsInfo.getBrand());
					item.setBoxnum(goodsInfo.getBoxnum());
					if(StringUtils.isNotEmpty(item.getBrandid())){
						Brand brand = getBaseGoodsMapper().getBrandInfo(item.getBrandid());
						if(null!=brand){
							item.setBrandname(brand.getName());
						}				
					}
					item.setBarcode(goodsInfo.getBarcode());
				}
			}
			if(StringUtils.isNotEmpty(item.getCustomerid())){
				Customer customer = getCustomerByID(item.getCustomerid());
				if(null!=customer){
					item.setCustomername(customer.getName());
				}
			}
            BigDecimal price=getGoodsPriceByCustomer(item.getGoodsid(),item.getCustomerid());
            item.setPrice(price);
		}
		int count=salesCustomerReportMapper.getSalesGoodsTradeReportDataCount(pageMap);
		PageData pageData=new PageData(count,list,pageMap);
		return pageData;
	}
	@Override
	public PageData showSalesGoodsNotTradeReportData(PageMap pageMap) throws Exception{
		String dataSql = getDataAccessRule("t_base_sales_customer", "cus");
		Map condition=pageMap.getCondition();
		if(null!=dataSql && !"".equals(dataSql)){
			condition.put("customerDataSql", dataSql);
		}else{
			if(condition.containsKey("customerDataSql")){
				condition.remove("customerDataSql");
			}
		}
		boolean showCustomerNotTrade=false;
		String customerid=(String)condition.get("customerid");
		String goodsid=(String) condition.get("goodsid");
		String customername="";

		if(condition.containsKey("showCustomerNotTrade")){
			condition.remove("showCustomerNotTrade");
		}
		if(null!=customerid && !"".equals(customerid.trim())){
			Customer customer = getCustomerByID(customerid.trim());
			if(null!=customer){
				customername=customer.getName();
			}
		}else{
			if(condition.containsKey("customerid")){
				condition.remove("customerid");
			}
			if(null!=goodsid && !"".equals(goodsid)){
				condition.put("showCustomerNotTrade", "true");
				showCustomerNotTrade=true;
			}else{
				condition.remove("showCustomerNotTrade");
			}
		}
		
		List<SalesCustomerPriceReport> list=salesCustomerReportMapper.getSalesGoodsNotTradeReportData(pageMap);
		for(SalesCustomerPriceReport item : list){
			if(showCustomerNotTrade){
				if(StringUtils.isNotEmpty(item.getCustomerid())){
					Customer customer = getCustomerByID(item.getCustomerid());
					if(null!=customer){
						item.setCustomername(customer.getName());
					}
				}
			}else{
				item.setCustomerid(customerid);
				item.setCustomername(customername);
			}
			if(StringUtils.isNotEmpty(item.getBrandid())){
				Brand brand = getBaseGoodsMapper().getBrandInfo(item.getBrandid());
				if(null!=brand){
					item.setBrandname(brand.getName());
				}				
			}
			BigDecimal price=getGoodsPriceByCustomer(item.getGoodsid(),item.getCustomerid());
			item.setPrice(price);
		}
		int count=salesCustomerReportMapper.getSalesGoodsNotTradeReportDataCount(pageMap);
		PageData pageData=new PageData(count,list,pageMap);
		return pageData;
	}
	
	@Override
	public PageData getFinanceSalesReportData(PageMap pageMap)throws Exception {
		String dataSql = getDataAccessRule("t_report_sales_base", "z");
		pageMap.setDataSql(dataSql);
		String query_sql = " 1=1 ";
		if(pageMap.getCondition().containsKey("goodsid")){
			String str = (String) pageMap.getCondition().get("goodsid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t1.goodsid = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t1.goodsid,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("brandid")){
			String str = (String) pageMap.getCondition().get("brandid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t1.brandid = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t1.brandid,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("branduser")){
			String str = (String) pageMap.getCondition().get("branduser");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t1.branduser = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t1.branduser,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("branddept")){
			String str = (String) pageMap.getCondition().get("branddept");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t1.branddept like concat( '"+str+"','%') ";
			}else{
				String retStr = "";
				String[] branddeptArr = str.split(",");
				for(String branddept : branddeptArr){
					Map map = new HashMap();
					map.put("deptid", branddept);
					List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
					if(dList.size() != 0){
						for(DepartMent departMent : dList){
							if(StringUtils.isNotEmpty(retStr)){
								retStr += "," + departMent.getId();
							}else{
								retStr = departMent.getId();
							}
						}
					}
				}
				query_sql += " and FIND_IN_SET(t1.branddept,'"+retStr+"')";
			}
		}
		if(pageMap.getCondition().containsKey("salesdept")){
			String str = (String) pageMap.getCondition().get("salesdept");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t.salesdept = '"+str+"' ";
			}else{
				String retStr = "";
				String[] salesdeptArr = str.split(",");
				for(String salesdept : salesdeptArr){
					Map map = new HashMap();
					map.put("deptid", salesdept);
					List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
					if(dList.size() != 0){
						for(DepartMent departMent : dList){
							if(StringUtils.isNotEmpty(retStr)){
								retStr += "," + departMent.getId();
							}else{
								retStr = departMent.getId();
							}
						}
					}
				}
				query_sql += " and FIND_IN_SET(t.salesdept,'"+retStr+"')";
			}
		}
		if(pageMap.getCondition().containsKey("customersort")){
			String str = (String) pageMap.getCondition().get("customersort");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and x.customersort like '"+str+"%' ";
			}else{
				query_sql += " and FIND_IN_SET(x.customersort,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("salesarea")){
			String str = (String) pageMap.getCondition().get("salesarea");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t.salesarea = '"+str+"' ";
			}else{
				query_sql += " and FIND_IN_SET(t.salesarea,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("salesuser")){
			String str = (String) pageMap.getCondition().get("salesuser");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t.salesuser = '"+str+"' ";
			}else{
				query_sql += " and FIND_IN_SET(t.salesuser,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("customerid")){
			String str = (String) pageMap.getCondition().get("customerid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t.customerid = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t.customerid,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("pcustomerid")){
			String str = (String) pageMap.getCondition().get("pcustomerid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t.pcustomerid = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t.pcustomerid,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("businessdate1")){
			String str = (String) pageMap.getCondition().get("businessdate1");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t.businessdate >= '"+str+"'";
		}
		if(pageMap.getCondition().containsKey("businessdate2")){
			String str = (String) pageMap.getCondition().get("businessdate2");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t.businessdate <= '"+str+"'";
		}
		//小计列
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		if(!pageMap.getCondition().containsKey("groupcols")){
			pageMap.getCondition().put("groupcols", "");
			groupcols = "customerid,goodsid";
		}
		pageMap.getCondition().put("query_sql", query_sql);
		//排序
		String orderstr = "";
		if(pageMap.getCondition().containsKey("sort")){
			String sort = (String) pageMap.getCondition().get("sort");
			String order = (String) pageMap.getCondition().get("order");
			if(null==order){
				order = "asc";
			}
			orderstr = sort +" " +order;
		}
		pageMap.getCondition().put("orderstr", orderstr);
		List<BaseSalesReport> list = salesCustomerReportMapper.getFinanceSalesReportList(pageMap);
		for(BaseSalesReport baseSalesReport : list){
			//销售数量 = 发货单数量 - 退货总数量
			if(null != baseSalesReport.getSendnum() && null != baseSalesReport.getReturnnum()){
				baseSalesReport.setSalenum(baseSalesReport.getSendnum().subtract(baseSalesReport.getReturnnum()));
			}
			//销售金额 = 发货单金额+冲差金额-退货金额
			if(null != baseSalesReport.getInitsendamount() && null != baseSalesReport.getReturnamount() && null != baseSalesReport.getPushbalanceamount()){
				baseSalesReport.setSaleamount(baseSalesReport.getInitsendamount().add(baseSalesReport.getPushbalanceamount()).subtract(baseSalesReport.getReturnamount()));
			}
			if(null != baseSalesReport.getInitsendnotaxamount() && null != baseSalesReport.getReturnnotaxamount() && null != baseSalesReport.getPushbalancenotaxamount()){
				baseSalesReport.setSalenotaxamount(baseSalesReport.getInitsendnotaxamount().add(baseSalesReport.getPushbalancenotaxamount()).subtract(baseSalesReport.getReturnnotaxamount()));
			}
			if(null!=baseSalesReport.getCostamount() && null != baseSalesReport.getSaleamount()){
				//毛利额 = 销售金额 - 成本金额
				baseSalesReport.setSalemarginamount(baseSalesReport.getSaleamount().subtract(baseSalesReport.getCostamount()));
				//实际毛利率 = （销售金额 - 成本金额）/销售金额*100
				if(baseSalesReport.getSaleamount().compareTo(BigDecimal.ZERO)!=0){
					BigDecimal realrate = baseSalesReport.getSaleamount().subtract(baseSalesReport.getCostamount()).divide(baseSalesReport.getSaleamount(), 6, BigDecimal.ROUND_HALF_UP);
					realrate = realrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
					if(baseSalesReport.getSaleamount().compareTo(BigDecimal.ZERO)==-1){
						baseSalesReport.setRealrate(realrate.negate());
					}else{
						baseSalesReport.setRealrate(realrate);
					}
				}
			}else if(baseSalesReport.getCostamount().compareTo(BigDecimal.ZERO)==1){
				baseSalesReport.setRealrate(new BigDecimal(100).negate());
			}
			baseSalesReport.setId(baseSalesReport.getCustomerid());
			//销售税额
			if(null != baseSalesReport.getSalenotaxamount() && null != baseSalesReport.getSaleamount()){
				baseSalesReport.setSaletax(baseSalesReport.getSaleamount().subtract(baseSalesReport.getSalenotaxamount()));
			}
			
			if(null != baseSalesReport.getWriteoffamount() && null != baseSalesReport.getCostwriteoffamount()){
				//回笼毛利额 = 回笼金额 - 回笼成本金额
				BigDecimal writeoffmarginamount = baseSalesReport.getWriteoffamount().subtract(baseSalesReport.getCostwriteoffamount());
				baseSalesReport.setWriteoffmarginamount(writeoffmarginamount);
				//回笼毛利率 = （回笼金额-回笼成本金额）/回笼金额
				if(baseSalesReport.getWriteoffamount().compareTo(BigDecimal.ZERO)!=0){
					BigDecimal writeoffrate = baseSalesReport.getWriteoffamount().subtract(baseSalesReport.getCostwriteoffamount()).divide(baseSalesReport.getWriteoffamount(), 6, BigDecimal.ROUND_HALF_UP);
					writeoffrate = writeoffrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
					baseSalesReport.setWriteoffrate(writeoffrate);
				}
			}
			//根据获取的商品编码获取条形码
			if(StringUtils.isNotEmpty(baseSalesReport.getGoodsid())){
				GoodsInfo goodsInfo = getGoodsInfoByID(baseSalesReport.getGoodsid());
				if(null != goodsInfo){
					baseSalesReport.setBarcode(goodsInfo.getBarcode());
				}
			}
			//判断是否为导出使用，若为导出，则将直退数量、售后退货数量显示为负数
			if(pageMap.getCondition().containsKey("isflag") && null != pageMap.getCondition().get("isflag")){
				//直退数量
				baseSalesReport.setDirectreturnnum(baseSalesReport.getDirectreturnnum().negate());
				//售后退货数量
				baseSalesReport.setCheckreturnnum(baseSalesReport.getCheckreturnnum().negate());
				//退货总数量
				baseSalesReport.setReturnnum(baseSalesReport.getReturnnum().negate());
			}
			if(groupcols.indexOf("customerid")!=-1){
				Customer customer = getCustomerByID(baseSalesReport.getCustomerid());
				if(null!=customer){
					baseSalesReport.setCustomername(customer.getName());
				}
				if(groupcols.indexOf("pcustomerid")==-1){
					Customer pcustomer = getCustomerByID(baseSalesReport.getPcustomerid());
					if(null!=pcustomer ){
						baseSalesReport.setPcustomername(pcustomer.getName());
					}
				}
				if(groupcols.indexOf("salesdept")==-1){
					DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getSalesdept());
					if(null!=departMent){
						baseSalesReport.setSalesdeptname(departMent.getName());
					}else{
						baseSalesReport.setSalesdeptname("其他");
					}
				}
				if(groupcols.indexOf("customersort")==-1){
					CustomerSort customerSort = getCustomerSortByID(baseSalesReport.getCustomersort());
					if(null!=customerSort){
						baseSalesReport.setCustomersortname(customerSort.getName());
					}else{
						baseSalesReport.setCustomersortname("未指定分类");
					}
				}
				if(groupcols.indexOf("salesarea")==-1){
					SalesArea salesArea = getSalesareaByID(baseSalesReport.getSalesarea());
					if(null!=salesArea){
						baseSalesReport.setSalesareaname(salesArea.getName());
					}else{
						baseSalesReport.setSalesareaname("其他");
					}
				}
				if(groupcols.indexOf("salesuser")==-1){
					Personnel personnel = getPersonnelById(baseSalesReport.getSalesuser());
					if(null!=personnel){
						baseSalesReport.setSalesusername(personnel.getName());
					}else{
						baseSalesReport.setSalesusername("其他");
					}
					DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getSalesdept());
					if(null!=departMent){
						baseSalesReport.setSalesdeptname(departMent.getName());
					}else{
						baseSalesReport.setSalesdeptname("其他");
					}
				}
			}else{
				baseSalesReport.setCustomerid("");
				baseSalesReport.setCustomername("");
				if(groupcols.indexOf("salesuser")==-1 && groupcols.indexOf("salesdept")==-1){
					baseSalesReport.setSalesdeptname("");
				}
				
			}
			if(groupcols.indexOf("pcustomerid")!=-1){
				Customer pcustomer = getCustomerByID(baseSalesReport.getPcustomerid());
				if(null!=pcustomer ){
					baseSalesReport.setPcustomername(pcustomer.getName());
				}else{
					baseSalesReport.setPcustomername("其他客户总和");
				}
				if(groupcols.indexOf("customerid")==1){
					baseSalesReport.setCustomerid("");
					baseSalesReport.setCustomername("");
					
				}
				
			}
			if(groupcols.indexOf("customersort")!=-1){
				CustomerSort customerSort = getCustomerSortByID(baseSalesReport.getCustomersort());
				if(null!=customerSort){
					baseSalesReport.setCustomersortname(customerSort.getName());
				}else{
					baseSalesReport.setCustomersortname("未指定分类");
				}
			}
			if(groupcols.indexOf("salesarea")!=-1){
				SalesArea salesArea = getSalesareaByID(baseSalesReport.getSalesarea());
				if(null!=salesArea){
					baseSalesReport.setSalesareaname(salesArea.getName());
				}else{
					baseSalesReport.setSalesareaname("其他");
				}
			}
			if(groupcols.indexOf("salesdept")!=-1){
				DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getSalesdept());
				if(null!=departMent){
					baseSalesReport.setSalesdeptname(departMent.getName());
				}else{
					baseSalesReport.setSalesdeptname("其他");
				}
			}
			if(groupcols.indexOf("salesuser")!=-1){
				Personnel personnel = getPersonnelById(baseSalesReport.getSalesuser());
				if(null!=personnel){
					baseSalesReport.setSalesusername(personnel.getName());
				}else{
					baseSalesReport.setSalesusername("其他");
				}
				DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getSalesdept());
				if(null!=departMent){
					baseSalesReport.setSalesdeptname(departMent.getName());
				}else{
					baseSalesReport.setSalesdeptname("其他");
				}
				if(groupcols.indexOf("salesarea")==-1){
					SalesArea salesArea = getSalesareaByID(baseSalesReport.getSalesarea());
					if(null!=salesArea){
						baseSalesReport.setSalesareaname(salesArea.getName());
					}
				}
			}
			if(groupcols.indexOf("goodsid")!=-1){
				GoodsInfo goodsInfo = getAllGoodsInfoByID(baseSalesReport.getGoodsid());
				if(null!=goodsInfo){
					baseSalesReport.setGoodsname(goodsInfo.getName());
				}else{
					Brand brand = getGoodsBrandByID(baseSalesReport.getGoodsid());
					if(null!=brand){
						baseSalesReport.setGoodsname("（折扣）"+brand.getName());
					}else{
						baseSalesReport.setGoodsname("（折扣）其他");
					}
				}
				if(groupcols.indexOf("brandid")==-1){
					Brand brand = getGoodsBrandByID(baseSalesReport.getBrandid());
					if(null!=brand){
						baseSalesReport.setBrandname(brand.getName());
					}
					Personnel personnel = getPersonnelById(baseSalesReport.getBranduser());
					if(null!=personnel){
						baseSalesReport.setBrandusername(personnel.getName());
					}else{
						baseSalesReport.setBrandusername("其他");
					}
				}
				if(groupcols.indexOf("branddept")==-1){
					DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getBranddept());
					if(null!=departMent){
						baseSalesReport.setBranddeptname(departMent.getName());
					}else{
						baseSalesReport.setBranddeptname("其他");
					}
				}
				if(groupcols.indexOf("branduser")==-1){
					Personnel personnel = getPersonnelById(baseSalesReport.getBranduser());
					if(null!=personnel){
						baseSalesReport.setBrandusername(personnel.getName());
					}else{
						baseSalesReport.setBrandusername("其他");
					}
				}
			}else{
				baseSalesReport.setGoodsid("");
			}
			if(groupcols.indexOf("brandid")!=-1){
				Brand brand = getGoodsBrandByID(baseSalesReport.getBrandid());
				if(null!=brand){
					baseSalesReport.setBrandname(brand.getName());
				}else{
					baseSalesReport.setBrandname("其他");
				}
				if(groupcols.indexOf("branddept")==-1){
					DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getBranddept());
					if(null!=departMent){
						baseSalesReport.setBranddeptname(departMent.getName());
					}else{
						baseSalesReport.setBranddeptname("其他");
					}
				}
				Personnel personnel = getPersonnelById(baseSalesReport.getBranduser());
				if(null!=personnel){
					baseSalesReport.setBrandusername(personnel.getName());
				}else{
					baseSalesReport.setBrandusername("其他");
				}
			}
			if(groupcols.indexOf("branduser")!=-1){
				Personnel personnel = getPersonnelById(baseSalesReport.getBranduser());
				if(null!=personnel){
					baseSalesReport.setBrandusername(personnel.getName());
				}else{
					baseSalesReport.setBrandusername("其他");
				}
				if(groupcols.indexOf("brandid")==-1){
					baseSalesReport.setBrandname("");
				}
				if(groupcols.indexOf("branddept")==-1){
					DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getBranddept());
					if(null!=departMent){
						baseSalesReport.setBranddeptname(departMent.getName());
					}else{
						baseSalesReport.setBranddeptname("其他");
					}
				}
			}
			if(groupcols.indexOf("branddept")!=-1){
				DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getBranddept());
				if(null!=departMent){
					baseSalesReport.setBranddeptname(departMent.getName());
				}else{
					baseSalesReport.setBranddeptname("其他");
				}
			}
		}
		int count = salesCustomerReportMapper.getFinanceSalesReportListCount(pageMap);
		PageData pageData = new PageData(count,list,pageMap);
		//取合计数据
		pageMap.getCondition().put("groupcols", "all");
		List<BaseSalesReport> footer = salesCustomerReportMapper.getFinanceSalesReportList(pageMap);
		for(BaseSalesReport baseSalesReport : footer){
			if(null!=baseSalesReport){
				//销售数量 = 发货单数量 - 直退数量
				if(null != baseSalesReport.getSendnum() && null != baseSalesReport.getReturnnum()){
					baseSalesReport.setSalenum(baseSalesReport.getSendnum().subtract(baseSalesReport.getReturnnum()));
				}
				//销售金额 = 发货单金额 + 冲差金额 - 退货合计
				if(null != baseSalesReport.getInitsendamount() && null != baseSalesReport.getReturnamount() && null != baseSalesReport.getPushbalanceamount()){
					baseSalesReport.setSaleamount(baseSalesReport.getInitsendamount().add(baseSalesReport.getPushbalanceamount()).subtract(baseSalesReport.getReturnamount()));
				}
                if(null != baseSalesReport.getInitsendnotaxamount() && null != baseSalesReport.getReturnnotaxamount() && null != baseSalesReport.getPushbalancenotaxamount()){
                    baseSalesReport.setSalenotaxamount(baseSalesReport.getInitsendnotaxamount().add(baseSalesReport.getPushbalancenotaxamount()).subtract(baseSalesReport.getReturnnotaxamount()));
                }
				if(null!=baseSalesReport.getCostamount() && null != baseSalesReport.getSaleamount()){
					//毛利额 = 销售金额 - 成本金额
					baseSalesReport.setSalemarginamount(baseSalesReport.getSaleamount().subtract(baseSalesReport.getCostamount()));
					//实际毛利率 = （销售金额 - 成本金额）/销售金额*100
					if(baseSalesReport.getSaleamount().compareTo(BigDecimal.ZERO)!=0){
						BigDecimal realrate = baseSalesReport.getSaleamount().subtract(baseSalesReport.getCostamount()).divide(baseSalesReport.getSaleamount(), 6, BigDecimal.ROUND_HALF_UP);
						realrate = realrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
						if(baseSalesReport.getSaleamount().compareTo(BigDecimal.ZERO)==-1){
							baseSalesReport.setRealrate(realrate.negate());
						}else{
							baseSalesReport.setRealrate(realrate);
						}
					}
				}
				baseSalesReport.setId(baseSalesReport.getCustomerid());
				//销售税额
				if(null != baseSalesReport.getSalenotaxamount() && null != baseSalesReport.getSaleamount()){
					baseSalesReport.setSaletax(baseSalesReport.getSaleamount().subtract(baseSalesReport.getSalenotaxamount()));
				}
				//回笼毛利额 = 回笼金额-回笼成本金额
				if(null != baseSalesReport.getWriteoffamount() && null != baseSalesReport.getCostwriteoffamount()){
					BigDecimal writeoffmarginamount = baseSalesReport.getWriteoffamount().subtract(baseSalesReport.getCostwriteoffamount());
					baseSalesReport.setWriteoffmarginamount(writeoffmarginamount);
					//回笼毛利率 = （回笼金额-回笼成本金额）/回笼金额
					if(baseSalesReport.getWriteoffamount().compareTo(BigDecimal.ZERO)!=0){
						BigDecimal writeoffrate = baseSalesReport.getWriteoffamount().subtract(baseSalesReport.getCostwriteoffamount()).divide(baseSalesReport.getWriteoffamount(), 6, BigDecimal.ROUND_HALF_UP);
						writeoffrate = writeoffrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
						baseSalesReport.setWriteoffrate(writeoffrate);
					}
				}
				//判断是否为导出使用，若为导出，则将直退数量、售后退货数量显示为负数
				if(pageMap.getCondition().containsKey("isflag") && null != pageMap.getCondition().get("isflag")){
					//直退数量
					baseSalesReport.setDirectreturnnum(baseSalesReport.getDirectreturnnum().negate());
					//售后退货数量
					baseSalesReport.setCheckreturnnum(baseSalesReport.getCheckreturnnum().negate());
					//退货总数量
					baseSalesReport.setReturnnum(baseSalesReport.getReturnnum().negate());
				}
				baseSalesReport.setAuxunitname("");
				baseSalesReport.setUnitname("");
				if(groupcols.indexOf(",") == -1){
					if(groupcols.indexOf("branddept")!=-1){
						baseSalesReport.setBranddept("");
						baseSalesReport.setBranddeptname("合计");
					}
					else if(groupcols.indexOf("brandid")!=-1){
						if(groupcols.indexOf("branduser")!=-1){
							baseSalesReport.setBranduser("");
							baseSalesReport.setBrandusername("合计");
						}
						else{
							baseSalesReport.setBrandid("");
							baseSalesReport.setBrandname("合计");
						}
					}
					else if(groupcols.indexOf("salesdept")!=-1){
						baseSalesReport.setSalesdept("");
						baseSalesReport.setSalesdeptname("合计");
					}
					else if(groupcols.indexOf("salesuser")!=-1){
						baseSalesReport.setSalesuser("");
						baseSalesReport.setSalesusername("合计");
					}
					else if(groupcols.indexOf("branduser")!=-1){
						baseSalesReport.setBranduser("");
						baseSalesReport.setBrandusername("合计");
					}
					else if(groupcols.indexOf("goodsid")!=-1){
						baseSalesReport.setGoodsid("");
						baseSalesReport.setGoodsname("合计");
					}
					else if(groupcols.indexOf("customerid")!=-1){
						if(groupcols.indexOf("pcustomerid")!=-1){
							baseSalesReport.setPcustomerid("");
							baseSalesReport.setPcustomername("合计");
						}else{
							baseSalesReport.setCustomerid("");
							baseSalesReport.setCustomername("合计");
							baseSalesReport.setSalesdeptname("");
						}
					}
					else if(groupcols.indexOf("customersort")!=-1){
						baseSalesReport.setCustomersort("");
						baseSalesReport.setCustomersortname("合计");
					}
					else if(groupcols.indexOf("pcustomerid")!=-1){
						baseSalesReport.setPcustomerid("");
						baseSalesReport.setPcustomername("合计");
					}
					else if(groupcols.indexOf("salesarea")!=-1){
						baseSalesReport.setSalesarea("");
						baseSalesReport.setSalesareaname("合计");
					}
				}else{
					String[] groupArr = groupcols.split(",");
					if(groupArr[0].indexOf("branddept")!=-1){
						baseSalesReport.setBranddeptname("合计");
						baseSalesReport.setBranddept("");
						baseSalesReport.setBrandid("");
						baseSalesReport.setSalesdept("");
						baseSalesReport.setSalesuser("");
						baseSalesReport.setBranduser("");
						baseSalesReport.setGoodsid("");
						baseSalesReport.setCustomerid("");
						baseSalesReport.setBrandname("");
						baseSalesReport.setSalesdeptname("");
						baseSalesReport.setSalesusername("");
						baseSalesReport.setBrandusername("");
						baseSalesReport.setGoodsname("");
						baseSalesReport.setCustomername("");
					}
					else if(groupArr[0].indexOf("brandid")!=-1){
						baseSalesReport.setBrandname("合计");
						baseSalesReport.setBranddept("");
						baseSalesReport.setBrandid("");
						baseSalesReport.setSalesdept("");
						baseSalesReport.setSalesuser("");
						baseSalesReport.setBranduser("");
						baseSalesReport.setGoodsid("");
						baseSalesReport.setCustomerid("");
						baseSalesReport.setBranddeptname("");
						baseSalesReport.setSalesdeptname("");
						baseSalesReport.setSalesusername("");
						baseSalesReport.setBrandusername("");
						baseSalesReport.setGoodsname("");
						baseSalesReport.setCustomername("");
					}
					else if(groupArr[0].indexOf("salesdept")!=-1){
						baseSalesReport.setSalesdeptname("合计");
						baseSalesReport.setBranddept("");
						baseSalesReport.setBrandid("");
						baseSalesReport.setSalesdept("");
						baseSalesReport.setSalesuser("");
						baseSalesReport.setBranduser("");
						baseSalesReport.setGoodsid("");
						baseSalesReport.setCustomerid("");
						baseSalesReport.setBrandname("");
						baseSalesReport.setBranddeptname("");
						baseSalesReport.setSalesusername("");
						baseSalesReport.setBrandusername("");
						baseSalesReport.setGoodsname("");
						baseSalesReport.setCustomername("");
					}
					else if(groupArr[0].indexOf("salesuser")!=-1){
						baseSalesReport.setSalesusername("合计");
						baseSalesReport.setBranddept("");
						baseSalesReport.setBrandid("");
						baseSalesReport.setSalesdept("");
						baseSalesReport.setSalesuser("");
						baseSalesReport.setBranduser("");
						baseSalesReport.setGoodsid("");
						baseSalesReport.setCustomerid("");
						baseSalesReport.setBrandname("");
						baseSalesReport.setSalesdeptname("");
						baseSalesReport.setBranddeptname("");
						baseSalesReport.setBrandusername("");
						baseSalesReport.setGoodsname("");
						baseSalesReport.setCustomername("");
					}
					else if(groupArr[0].indexOf("branduser")!=-1){
						baseSalesReport.setBrandusername("合计");
						baseSalesReport.setBranddept("");
						baseSalesReport.setBrandid("");
						baseSalesReport.setSalesdept("");
						baseSalesReport.setSalesuser("");
						baseSalesReport.setBranduser("");
						baseSalesReport.setGoodsid("");
						baseSalesReport.setCustomerid("");
						baseSalesReport.setBrandname("");
						baseSalesReport.setSalesdeptname("");
						baseSalesReport.setSalesusername("");
						baseSalesReport.setBranddeptname("");
						baseSalesReport.setGoodsname("");
						baseSalesReport.setCustomername("");
					}
					else if(groupArr[0].indexOf("goodsid")!=-1){
						baseSalesReport.setGoodsname("合计");
						baseSalesReport.setBranddept("");
						baseSalesReport.setBrandid("");
						baseSalesReport.setSalesdept("");
						baseSalesReport.setSalesuser("");
						baseSalesReport.setBranduser("");
						baseSalesReport.setGoodsid("");
						baseSalesReport.setCustomerid("");
						baseSalesReport.setBrandname("");
						baseSalesReport.setSalesdeptname("");
						baseSalesReport.setSalesusername("");
						baseSalesReport.setBrandusername("");
						baseSalesReport.setBranddeptname("");
						baseSalesReport.setCustomername("");
					}
					else if(groupArr[0].indexOf("customerid")!=-1){
						baseSalesReport.setCustomername("合计");
						baseSalesReport.setBranddept("");
						baseSalesReport.setBrandid("");
						baseSalesReport.setSalesdept("");
						baseSalesReport.setSalesuser("");
						baseSalesReport.setBranduser("");
						baseSalesReport.setGoodsid("");
						baseSalesReport.setCustomerid("");
						baseSalesReport.setBrandname("");
						baseSalesReport.setSalesdeptname("");
						baseSalesReport.setSalesusername("");
						baseSalesReport.setBrandusername("");
						baseSalesReport.setGoodsname("");
						baseSalesReport.setBranddeptname("");
					}
				}
			}
		}
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public PageData getSalesDemandReportList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_report_sales_base", "z");
		pageMap.setDataSql(dataSql);
		String groupcols = (String)pageMap.getCondition().get("groupcols");
		if(StringUtils.isEmpty(groupcols)){
			groupcols = "customerid,goodsid";
		}
		if(pageMap.getCondition().containsKey("branddept") && null != pageMap.getCondition().get("branddept")){
			String str = (String) pageMap.getCondition().get("branddept");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				Map map = new HashMap();
				map.put("deptid", str);
				List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
				if(dList.size() != 0){
					String retStr = "";
					for(DepartMent departMent : dList){
						if(StringUtils.isNotEmpty(retStr)){
							retStr += "," + departMent.getId();
						}else{
							retStr = departMent.getId();
						}
					}
					pageMap.getCondition().put("branddept", retStr);
				}
			}else{
				String retStr = "";
				String[] branddeptArr = str.split(",");
				for(String branddept : branddeptArr){
					Map map = new HashMap();
					map.put("deptid", branddept);
					List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
					if(dList.size() != 0){
						for(DepartMent departMent : dList){
							if(StringUtils.isNotEmpty(retStr)){
								retStr += "," + departMent.getId();
							}else{
								retStr = departMent.getId();
							}
						}
						pageMap.getCondition().put("branddept", retStr);
					}
				}
			}
		}
		List<SalesDemandReport> list = salesCustomerReportMapper.getSalesDemandReportList(pageMap);
		for(SalesDemandReport salesDemandReport : list){
			GoodsInfo goodsInfo2 = getGoodsInfoByID(salesDemandReport.getGoodsid());
			if(null != goodsInfo2){
				salesDemandReport.setBarcode(goodsInfo2.getBarcode());
			}
			if(pageMap.getCondition().containsKey("isflag")){
				salesDemandReport.setUnitnumInteger(salesDemandReport.getUnitnum().intValue());
				salesDemandReport.setAuxInteger(salesDemandReport.getAuxnum().intValue());
				salesDemandReport.setOvernumInteger(salesDemandReport.getOvernum().intValue());
			}
			if(groupcols.indexOf("customerid")!=-1){
				Customer customer = getCustomerByID(salesDemandReport.getCustomerid());
				if(null!=customer){
					salesDemandReport.setCustomername(customer.getName());
					salesDemandReport.setShortcode(customer.getShortcode());
				}
				if(groupcols.indexOf("salesdept")==-1){
					DepartMent departMent = getDepartmentByDeptid(salesDemandReport.getSalesdept());
					if(null!=departMent){
						salesDemandReport.setSalesdeptname(departMent.getName());
					}else{
						salesDemandReport.setSalesdeptname("其他");
					}
				}
				if(groupcols.indexOf("customersort")==-1){
					CustomerSort customerSort = getCustomerSortByID(salesDemandReport.getCustomersort());
					if(null!=customerSort){
						salesDemandReport.setCustomersortname(customerSort.getName());
					}else{
						salesDemandReport.setCustomersortname("未指定分类");
					}
				}
				if(groupcols.indexOf("salesarea")==-1){
					SalesArea salesArea = getSalesareaByID(salesDemandReport.getSalesarea());
					if(null!=salesArea){
						salesDemandReport.setSalesareaname(salesArea.getName());
					}else{
						salesDemandReport.setSalesareaname("其他");
					}
				}
				if(groupcols.indexOf("salesuser")==-1){
					Personnel personnel = getPersonnelById(salesDemandReport.getSalesuser());
					if(null!=personnel){
						salesDemandReport.setSalesusername(personnel.getName());
					}else{
						salesDemandReport.setSalesusername("其他");
					}
					DepartMent departMent = getDepartmentByDeptid(salesDemandReport.getSalesdept());
					if(null!=departMent){
						salesDemandReport.setSalesdeptname(departMent.getName());
					}else{
						salesDemandReport.setSalesdeptname("其他");
					}
				}
			}else{
				salesDemandReport.setCustomerid("");
				salesDemandReport.setCustomername("");
				if(groupcols.indexOf("salesuser")==-1 && groupcols.indexOf("salesdept")==-1){
					salesDemandReport.setSalesdeptname("");
				}
				
			}
			if(groupcols.indexOf("customersort")!=-1){
				CustomerSort customerSort = getCustomerSortByID(salesDemandReport.getCustomersort());
				if(null!=customerSort){
					salesDemandReport.setCustomersortname(customerSort.getName());
				}else{
					salesDemandReport.setCustomersortname("未指定分类");
				}
			}
			if(groupcols.indexOf("salesarea")!=-1){
				SalesArea salesArea = getSalesareaByID(salesDemandReport.getSalesarea());
				if(null!=salesArea){
					salesDemandReport.setSalesareaname(salesArea.getName());
				}else{
					salesDemandReport.setSalesareaname("其他");
				}
			}
			if(groupcols.indexOf("salesdept")!=-1){
				DepartMent departMent = getDepartmentByDeptid(salesDemandReport.getSalesdept());
				if(null!=departMent){
					salesDemandReport.setSalesdeptname(departMent.getName());
				}else{
					salesDemandReport.setSalesdeptname("其他");
				}
			}
			if(groupcols.indexOf("salesuser")!=-1){
				Personnel personnel = getPersonnelById(salesDemandReport.getSalesuser());
				if(null!=personnel){
					salesDemandReport.setSalesusername(personnel.getName());
				}else{
					salesDemandReport.setSalesusername("其他");
				}
				DepartMent departMent = getDepartmentByDeptid(salesDemandReport.getSalesdept());
				if(null!=departMent){
					salesDemandReport.setSalesdeptname(departMent.getName());
				}else{
					salesDemandReport.setSalesdeptname("其他");
				}
				if(groupcols.indexOf("salesarea")==-1){
					SalesArea salesArea = getSalesareaByID(salesDemandReport.getSalesarea());
					if(null!=salesArea){
						salesDemandReport.setSalesareaname(salesArea.getName());
					}
				}
			}
			if(groupcols.indexOf("goodsid")!=-1){
				GoodsInfo goodsInfo = getAllGoodsInfoByID(salesDemandReport.getGoodsid());
				if(null!=goodsInfo){
					salesDemandReport.setGoodsname(goodsInfo.getName());
					salesDemandReport.setSpell(goodsInfo.getSpell());
					Brand brand = getGoodsBrandByID(salesDemandReport.getBrandid());
					if(null!=brand){
						salesDemandReport.setBrandname(brand.getName());
					}
				}else{
					Brand brand = getGoodsBrandByID(salesDemandReport.getGoodsid());
					if(null!=brand){
						salesDemandReport.setGoodsname("（折扣）"+brand.getName());
					}else{
						salesDemandReport.setGoodsname("（折扣）其他");
					}
				}
				if(groupcols.indexOf("brandid")==-1){
					Brand brand = getGoodsBrandByID(salesDemandReport.getBrandid());
					if(null!=brand){
						salesDemandReport.setBrandname(brand.getName());
					}
					Personnel personnel = getPersonnelById(salesDemandReport.getBranduser());
					if(null!=personnel){
						salesDemandReport.setBrandusername(personnel.getName());
					}else{
						salesDemandReport.setBrandusername("其他");
					}
				}
				if(groupcols.indexOf("branddept")==-1){
					DepartMent departMent = getDepartmentByDeptid(salesDemandReport.getBranddept());
					if(null!=departMent){
						salesDemandReport.setBranddeptname(departMent.getName());
					}else{
						salesDemandReport.setBranddeptname("其他");
					}
				}
				if(groupcols.indexOf("branduser")==-1){
					Personnel personnel = getPersonnelById(salesDemandReport.getBranduser());
					if(null!=personnel){
						salesDemandReport.setBrandusername(personnel.getName());
					}else{
						salesDemandReport.setBrandusername("其他");
					}
				}
				WaresClass waresClass = getWaresClassByID(salesDemandReport.getGoodssort());
				if (null != waresClass) {
					salesDemandReport.setGoodssortname(waresClass.getName());
				} else {
					salesDemandReport.setGoodssortname("其他");
				}

			}else{
				salesDemandReport.setGoodsid("");
			}
			if(groupcols.indexOf("brandid")!=-1){
				Brand brand = getGoodsBrandByID(salesDemandReport.getBrandid());
				if(null!=brand){
					salesDemandReport.setBrandname(brand.getName());
				}else{
					salesDemandReport.setBrandname("其他");
				}
				if(groupcols.indexOf("branddept")==-1){
					DepartMent departMent = getDepartmentByDeptid(salesDemandReport.getBranddept());
					if(null!=departMent){
						salesDemandReport.setBranddeptname(departMent.getName());
					}else{
						salesDemandReport.setBranddeptname("其他");
					}
				}
				Personnel personnel = getPersonnelById(salesDemandReport.getBranduser());
				if(null!=personnel){
					salesDemandReport.setBrandusername(personnel.getName());
				}else{
					salesDemandReport.setBrandusername("其他");
				}
			}
			if(groupcols.indexOf("branduser")!=-1){
				Personnel personnel = getPersonnelById(salesDemandReport.getBranduser());
				if(null!=personnel){
					salesDemandReport.setBrandusername(personnel.getName());
				}else{
					salesDemandReport.setBrandusername("其他");
				}
				if(groupcols.indexOf("branddept")==-1){
					DepartMent departMent = getDepartmentByDeptid(salesDemandReport.getBranddept());
					if(null!=departMent){
						salesDemandReport.setBranddeptname(departMent.getName());
					}else{
						salesDemandReport.setBranddeptname("其他");
					}
				}
			}
			if(groupcols.indexOf("branddept")!=-1){
				DepartMent departMent = getDepartmentByDeptid(salesDemandReport.getBranddept());
				if(null!=departMent){
					salesDemandReport.setBranddeptname(departMent.getName());
				}else{
					salesDemandReport.setBranddeptname("其他");
				}
			}
			//主计量
			if(StringUtils.isNotEmpty(salesDemandReport.getUnitid())){
				MeteringUnit meteringUnit = getMeteringUnitById(salesDemandReport.getUnitid());
				if(null != meteringUnit){
					salesDemandReport.setUnitname(meteringUnit.getName());
				}
			}
			//辅计量
			if(StringUtils.isNotEmpty(salesDemandReport.getAuxunitid())){
				MeteringUnit meteringUnit = getMeteringUnitById(salesDemandReport.getAuxunitid());
				if(null != meteringUnit){
					salesDemandReport.setAuxunitname(meteringUnit.getName());
				}
			}
			if(groupcols.indexOf("goodssort")!=-1){
				WaresClass waresClass=getWaresClassByID(salesDemandReport.getGoodssort());
				if(null!=waresClass){
					salesDemandReport.setGoodssortname(waresClass.getName());
				}else{
					salesDemandReport.setGoodssortname("其他");
				}
			}
		}
		
		PageData pageData = new PageData(salesCustomerReportMapper.getSalesDemandReportCount(pageMap),list,pageMap);
		
		//合计
		pageMap.getCondition().put("groupcols", "all");
		List<SalesDemandReport> footer = salesCustomerReportMapper.getSalesDemandReportList(pageMap);
		for(SalesDemandReport salesDemandReportSum : footer){
			if(null != salesDemandReportSum){
				if(pageMap.getCondition().containsKey("isflag")){
					salesDemandReportSum.setUnitnumInteger(salesDemandReportSum.getUnitnum().intValue());
					salesDemandReportSum.setAuxInteger(salesDemandReportSum.getAuxnum().intValue());
					salesDemandReportSum.setOvernumInteger(salesDemandReportSum.getOvernum().intValue());
				}
				salesDemandReportSum.setAuxunitname("");
				salesDemandReportSum.setUnitname("");
				salesDemandReportSum.setTaxprice(null);
				if(groupcols.indexOf(",") == -1){
					if(groupcols.indexOf("brandid")!=-1){
						salesDemandReportSum.setBrandid("");
						salesDemandReportSum.setBrandname("合计");
					}
					else if(groupcols.indexOf("branddept")!=-1){
						salesDemandReportSum.setBranddept("");
						salesDemandReportSum.setBranddeptname("合计");
					}
					else if(groupcols.indexOf("salesdept")!=-1){
						salesDemandReportSum.setSalesdept("");
						salesDemandReportSum.setSalesdeptname("合计");
					}
					else if(groupcols.indexOf("salesuser")!=-1){
						salesDemandReportSum.setSalesuser("");
						salesDemandReportSum.setSalesusername("合计");
					}
					else if(groupcols.indexOf("goodsid")!=-1){
						salesDemandReportSum.setGoodsid("");
						salesDemandReportSum.setGoodsname("合计");
					}
					else if(groupcols.indexOf("customerid")!=-1){
						salesDemandReportSum.setCustomerid("");
						salesDemandReportSum.setCustomername("合计");
						salesDemandReportSum.setSalesdeptname("");
					}
					else if(groupcols.indexOf("customersort")!=-1){
						salesDemandReportSum.setCustomersort("");
						salesDemandReportSum.setCustomersortname("合计");
					}
					else if(groupcols.indexOf("salesarea")!=-1){
						salesDemandReportSum.setSalesarea("");
						salesDemandReportSum.setSalesareaname("合计");
					}
					else if(groupcols.indexOf("branduser")!=-1){
						salesDemandReportSum.setBranduser("");
						salesDemandReportSum.setBrandusername("合计");
					}
				}else{
					String[] groupArr = groupcols.split(",");
					if(groupArr[0].indexOf("brandid")!=-1){
						salesDemandReportSum.setBrandname("合计");
						salesDemandReportSum.setBrandid("");
						salesDemandReportSum.setSalesdept("");
						salesDemandReportSum.setSalesuser("");
						salesDemandReportSum.setGoodsid("");
						salesDemandReportSum.setCustomerid("");
						salesDemandReportSum.setSalesdeptname("");
						salesDemandReportSum.setSalesusername("");
						salesDemandReportSum.setGoodsname("");
						salesDemandReportSum.setCustomername("");
						salesDemandReportSum.setBranddeptname("");
						salesDemandReportSum.setBranddept("");
						salesDemandReportSum.setBranduser("");
						salesDemandReportSum.setBrandusername("");
						salesDemandReportSum.setSalesarea("");
						salesDemandReportSum.setSalesareaname("");
					}
					else if(groupArr[0].indexOf("branddept")!=-1){
						salesDemandReportSum.setBranddeptname("合计");
						salesDemandReportSum.setBranddept("");
						salesDemandReportSum.setBrandname("");
						salesDemandReportSum.setBrandid("");
						salesDemandReportSum.setSalesdept("");
						salesDemandReportSum.setSalesuser("");
						salesDemandReportSum.setGoodsid("");
						salesDemandReportSum.setCustomerid("");
						salesDemandReportSum.setSalesdeptname("");
						salesDemandReportSum.setSalesusername("");
						salesDemandReportSum.setGoodsname("");
						salesDemandReportSum.setCustomername("");
						salesDemandReportSum.setBranduser("");
						salesDemandReportSum.setBrandusername("");
						salesDemandReportSum.setSalesarea("");
						salesDemandReportSum.setSalesareaname("");
					}
					else if(groupArr[0].indexOf("salesdept")!=-1){
						salesDemandReportSum.setBrandname("");
						salesDemandReportSum.setBrandid("");
						salesDemandReportSum.setSalesdept("");
						salesDemandReportSum.setSalesuser("");
						salesDemandReportSum.setGoodsid("");
						salesDemandReportSum.setCustomerid("");
						salesDemandReportSum.setSalesdeptname("合计");
						salesDemandReportSum.setSalesusername("");
						salesDemandReportSum.setGoodsname("");
						salesDemandReportSum.setCustomername("");
						salesDemandReportSum.setBranddeptname("");
						salesDemandReportSum.setBranddept("");
						salesDemandReportSum.setBranduser("");
						salesDemandReportSum.setBrandusername("");
						salesDemandReportSum.setSalesarea("");
						salesDemandReportSum.setSalesareaname("");
					}
					else if(groupArr[0].indexOf("salesuser")!=-1){
						salesDemandReportSum.setBrandname("");
						salesDemandReportSum.setBrandid("");
						salesDemandReportSum.setSalesdept("");
						salesDemandReportSum.setSalesuser("");
						salesDemandReportSum.setGoodsid("");
						salesDemandReportSum.setCustomerid("");
						salesDemandReportSum.setSalesdeptname("");
						salesDemandReportSum.setSalesusername("合计");
						salesDemandReportSum.setGoodsname("");
						salesDemandReportSum.setCustomername("");
						salesDemandReportSum.setBranddeptname("");
						salesDemandReportSum.setBranddept("");
						salesDemandReportSum.setBranduser("");
						salesDemandReportSum.setBrandusername("");
						salesDemandReportSum.setSalesarea("");
						salesDemandReportSum.setSalesareaname("");
					}
					else if(groupArr[0].indexOf("goodsid")!=-1){
						salesDemandReportSum.setBrandname("");
						salesDemandReportSum.setBrandid("");
						salesDemandReportSum.setSalesdept("");
						salesDemandReportSum.setSalesuser("");
						salesDemandReportSum.setGoodsid("");
						salesDemandReportSum.setCustomerid("");
						salesDemandReportSum.setSalesdeptname("");
						salesDemandReportSum.setSalesusername("");
						salesDemandReportSum.setGoodsname("合计");
						salesDemandReportSum.setCustomername("");
						salesDemandReportSum.setBranddeptname("");
						salesDemandReportSum.setBranddept("");
						salesDemandReportSum.setBranduser("");
						salesDemandReportSum.setBrandusername("");
						salesDemandReportSum.setSalesarea("");
						salesDemandReportSum.setSalesareaname("");
					}
					else if(groupArr[0].indexOf("customerid")!=-1){
						salesDemandReportSum.setBrandname("");
						salesDemandReportSum.setBrandid("");
						salesDemandReportSum.setSalesdept("");
						salesDemandReportSum.setSalesuser("");
						salesDemandReportSum.setGoodsid("");
						salesDemandReportSum.setCustomerid("合计");
						salesDemandReportSum.setSalesdeptname("");
						salesDemandReportSum.setSalesusername("");
						salesDemandReportSum.setGoodsname("");
						salesDemandReportSum.setCustomername("");
						salesDemandReportSum.setBranddeptname("");
						salesDemandReportSum.setBranddept("");
						salesDemandReportSum.setBranduser("");
						salesDemandReportSum.setBrandusername("");
						salesDemandReportSum.setSalesarea("");
						salesDemandReportSum.setSalesareaname("");
					}
					else if(groupArr[0].indexOf("branduser")!=-1){
						salesDemandReportSum.setBrandname("");
						salesDemandReportSum.setBrandid("");
						salesDemandReportSum.setSalesdept("");
						salesDemandReportSum.setSalesuser("");
						salesDemandReportSum.setGoodsid("");
						salesDemandReportSum.setCustomerid("");
						salesDemandReportSum.setSalesdeptname("");
						salesDemandReportSum.setSalesusername("");
						salesDemandReportSum.setGoodsname("");
						salesDemandReportSum.setCustomername("");
						salesDemandReportSum.setBranddeptname("");
						salesDemandReportSum.setBranddept("");
						salesDemandReportSum.setBranduser("");
						salesDemandReportSum.setBrandusername("合计");
						salesDemandReportSum.setSalesarea("");
						salesDemandReportSum.setSalesareaname("");
					}
					else if(groupArr[0].indexOf("salesarea")!=-1){
						salesDemandReportSum.setBrandname("");
						salesDemandReportSum.setBrandid("");
						salesDemandReportSum.setSalesdept("");
						salesDemandReportSum.setSalesuser("");
						salesDemandReportSum.setGoodsid("");
						salesDemandReportSum.setCustomerid("");
						salesDemandReportSum.setSalesdeptname("");
						salesDemandReportSum.setSalesusername("");
						salesDemandReportSum.setGoodsname("");
						salesDemandReportSum.setCustomername("");
						salesDemandReportSum.setBranddeptname("");
						salesDemandReportSum.setBranddept("");
						salesDemandReportSum.setBranduser("");
						salesDemandReportSum.setBrandusername("");
						salesDemandReportSum.setSalesarea("");
						salesDemandReportSum.setSalesareaname("合计");
					}
				}
			}else{
				footer = new ArrayList<SalesDemandReport>();
			}
		}
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public PageData getSalesCarReportList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_report_sales_base", "z");
		pageMap.setDataSql(dataSql);
		String groupcols = (String)pageMap.getCondition().get("groupcols");
		if(StringUtils.isEmpty(groupcols)){
			groupcols = "customerid,goodsid";
		}
//		if(groupcols.indexOf("caruser") != -1){
//			pageMap.getCondition().put("iscaruser",true);
//		}
		if(pageMap.getCondition().containsKey("branddept") && null != pageMap.getCondition().get("branddept")){
			String str = (String) pageMap.getCondition().get("branddept");
			str = StringEscapeUtils.escapeSql(str);
			String retStr = "";
			String[] branddeptArr = str.split(",");
			for(String branddept : branddeptArr){
				Map map = new HashMap();
				map.put("deptid", branddept);
				List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
				if(dList.size() != 0){
					for(DepartMent departMent : dList){
						if(StringUtils.isNotEmpty(retStr)){
							retStr += "," + departMent.getId();
						}else{
							retStr = departMent.getId();
						}
					}
					pageMap.getCondition().put("branddept", retStr);
				}
			}
		}
		if(pageMap.getCondition().containsKey("salesdept") && null != pageMap.getCondition().get("salesdept")){
			String str = (String) pageMap.getCondition().get("salesdept");
			str = StringEscapeUtils.escapeSql(str);
			String retStr = "";
			String[] salesdeptArr = str.split(",");
			for(String salesdept : salesdeptArr){
				Map map = new HashMap();
				map.put("deptid", salesdept);
				List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
				if(dList.size() != 0){
					for(DepartMent departMent : dList){
						if(StringUtils.isNotEmpty(retStr)){
							retStr += "," + departMent.getId();
						}else{
							retStr = departMent.getId();
						}
					}
					pageMap.getCondition().put("salesdept", retStr);
				}
			}
		}
		List<SalesCarReport> list = salesCustomerReportMapper.getSalesCarReportList(pageMap);
		for(SalesCarReport salesCarReport : list){
			GoodsInfo goodsInfo2 = getGoodsInfoByID(salesCarReport.getGoodsid());
			if(null != goodsInfo2){
				salesCarReport.setBarcode(goodsInfo2.getBarcode());
			}
			if(pageMap.getCondition().containsKey("isflag")){
				salesCarReport.setUnitnumInteger(salesCarReport.getUnitnum().intValue());
				salesCarReport.setAuxInteger(salesCarReport.getAuxnum().intValue());
				salesCarReport.setOvernumInteger(salesCarReport.getOvernum().intValue());
			}
			if(StringUtils.isNotEmpty(salesCarReport.getCaruser())){
				Personnel personnel = getPersonnelById(salesCarReport.getCaruser());
				if(null != personnel){
					salesCarReport.setCarusername(personnel.getName());
				}else{
					salesCarReport.setCarusername("其他未定义");
				}
			}else{
				salesCarReport.setCaruser("nodata");
				salesCarReport.setCarusername("其他未指定");
			}
			if(groupcols.indexOf("customerid")!=-1){
				Customer customer = getCustomerByID(salesCarReport.getCustomerid());
				if(null!=customer){
					salesCarReport.setCustomername(customer.getName());
					salesCarReport.setShortcode(customer.getShortcode());
				}
				if(groupcols.indexOf("pcustomerid")==-1){
					Customer pcustomer = getCustomerByID(salesCarReport.getPcustomerid());
					if(null!=pcustomer ){
						salesCarReport.setPcustomername(pcustomer.getName());
					}
				}
				if(groupcols.indexOf("salesdept")==-1){
					DepartMent departMent = getDepartmentByDeptid(salesCarReport.getSalesdept());
					if(null!=departMent){
						salesCarReport.setSalesdeptname(departMent.getName());
					}else{
						salesCarReport.setSalesdeptname("其他");
					}
				}
				if(groupcols.indexOf("customersort")==-1){
					CustomerSort customerSort = getCustomerSortByID(salesCarReport.getCustomersort());
					if(null!=customerSort){
						salesCarReport.setCustomersortname(customerSort.getThisname());
					}else{
						salesCarReport.setCustomersortname("未指定分类");
					}
				}
				if(groupcols.indexOf("salesarea")==-1){
					SalesArea salesArea = getSalesareaByID(salesCarReport.getSalesarea());
					if(null!=salesArea){
						salesCarReport.setSalesareaname(salesArea.getThisname());
					}else{
						salesCarReport.setSalesareaname("其他");
					}
				}
				if(groupcols.indexOf("salesuser")==-1){
					Personnel personnel = getPersonnelById(salesCarReport.getSalesuser());
					if(null!=personnel){
						salesCarReport.setSalesusername(personnel.getName());
					}else{
						salesCarReport.setSalesusername("其他");
					}
					DepartMent departMent = getDepartmentByDeptid(salesCarReport.getSalesdept());
					if(null!=departMent){
						salesCarReport.setSalesdeptname(departMent.getName());
					}else{
						salesCarReport.setSalesdeptname("其他");
					}
				}
			}else{
				salesCarReport.setCustomerid("");
				salesCarReport.setCustomername("");
				if(groupcols.indexOf("salesuser")==-1 && groupcols.indexOf("salesdept")==-1){
					salesCarReport.setSalesdeptname("");
				}
				
			}
			if(groupcols.indexOf("pcustomerid")!=-1){
				Customer pcustomer = getCustomerByID(salesCarReport.getPcustomerid());
				if(null!=pcustomer ){
					salesCarReport.setPcustomername(pcustomer.getName());
				}else{
					salesCarReport.setPcustomername("其他客户总和");
				}
				if(groupcols.indexOf("customerid")==1){
					salesCarReport.setCustomerid("");
					salesCarReport.setCustomername("");
					
				}
				
			}
			if(groupcols.indexOf("customersort")!=-1){
				CustomerSort customerSort = getCustomerSortByID(salesCarReport.getCustomersort());
				if(null!=customerSort){
					salesCarReport.setCustomersortname(customerSort.getThisname());
				}else{
					salesCarReport.setCustomersortname("未指定分类");
				}
			}
			if(groupcols.indexOf("salesarea")!=-1){
				SalesArea salesArea = getSalesareaByID(salesCarReport.getSalesarea());
				if(null!=salesArea){
					salesCarReport.setSalesareaname(salesArea.getThisname());
				}else{
					salesCarReport.setSalesareaname("其他");
				}
			}
			if(groupcols.indexOf("salesdept")!=-1){
				DepartMent departMent = getDepartmentByDeptid(salesCarReport.getSalesdept());
				if(null!=departMent){
					salesCarReport.setSalesdeptname(departMent.getName());
				}else{
					salesCarReport.setSalesdeptname("其他");
				}
			}
			if(groupcols.indexOf("salesuser")!=-1){
				Personnel personnel = getPersonnelById(salesCarReport.getSalesuser());
				if(null!=personnel){
					salesCarReport.setSalesusername(personnel.getName());
				}else{
					salesCarReport.setSalesusername("其他");
				}
				DepartMent departMent = getDepartmentByDeptid(salesCarReport.getSalesdept());
				if(null!=departMent){
					salesCarReport.setSalesdeptname(departMent.getName());
				}else{
					salesCarReport.setSalesdeptname("其他");
				}
				if(groupcols.indexOf("salesarea")==-1){
					SalesArea salesArea = getSalesareaByID(salesCarReport.getSalesarea());
					if(null!=salesArea){
						salesCarReport.setSalesareaname(salesArea.getThisname());
					}
				}
			}
			if(groupcols.indexOf("goodsid")!=-1){
				GoodsInfo goodsInfo = getAllGoodsInfoByID(salesCarReport.getGoodsid());
				if(null!=goodsInfo){
					salesCarReport.setGoodsname(goodsInfo.getName());
					salesCarReport.setSpell(goodsInfo.getSpell());
				}else{
					Brand brand = getGoodsBrandByID(salesCarReport.getGoodsid());
					if(null!=brand){
						salesCarReport.setGoodsname("（折扣）"+brand.getName());
					}else{
						salesCarReport.setGoodsname("（折扣）其他");
					}
				}
				if(groupcols.indexOf("brandid")==-1){
					Brand brand = getGoodsBrandByID(salesCarReport.getBrandid());
					if(null!=brand){
						salesCarReport.setBrandname(brand.getName());
					}
					Personnel personnel = getPersonnelById(salesCarReport.getBranduser());
					if(null!=personnel){
						salesCarReport.setBrandusername(personnel.getName());
					}else{
						salesCarReport.setBrandusername("其他");
					}
				}
				if(groupcols.indexOf("branddept")==-1){
					DepartMent departMent = getDepartmentByDeptid(salesCarReport.getBranddept());
					if(null!=departMent){
						salesCarReport.setBranddeptname(departMent.getName());
					}else{
						salesCarReport.setBranddeptname("其他");
					}
				}
				if(groupcols.indexOf("branduser")==-1){
					Personnel personnel = getPersonnelById(salesCarReport.getBranduser());
					if(null!=personnel){
						salesCarReport.setBrandusername(personnel.getName());
					}else{
						salesCarReport.setBrandusername("其他");
					}
				}
			}else{
				salesCarReport.setGoodsid("");
			}
			if(groupcols.indexOf("brandid")!=-1){
				Brand brand = getGoodsBrandByID(salesCarReport.getBrandid());
				if(null!=brand){
					salesCarReport.setBrandname(brand.getName());
				}else{
					salesCarReport.setBrandname("其他");
				}
				if(groupcols.indexOf("branddept")==-1){
					DepartMent departMent = getDepartmentByDeptid(salesCarReport.getBranddept());
					if(null!=departMent){
						salesCarReport.setBranddeptname(departMent.getName());
					}else{
						salesCarReport.setBranddeptname("其他");
					}
				}
				Personnel personnel = getPersonnelById(salesCarReport.getBranduser());
				if(null!=personnel){
					salesCarReport.setBrandusername(personnel.getName());
				}else{
					salesCarReport.setBrandusername("其他");
				}
			}
			if(groupcols.indexOf("branduser")!=-1){
				Personnel personnel = getPersonnelById(salesCarReport.getBranduser());
				if(null!=personnel){
					salesCarReport.setBrandusername(personnel.getName());
				}else{
					salesCarReport.setBrandusername("其他");
				}
				if(groupcols.indexOf("brandid")==-1){
					salesCarReport.setBrandname("");
				}
				if(groupcols.indexOf("branddept")==-1){
					DepartMent departMent = getDepartmentByDeptid(salesCarReport.getBranddept());
					if(null!=departMent){
						salesCarReport.setBranddeptname(departMent.getName());
					}else{
						salesCarReport.setBranddeptname("其他");
					}
				}
			}
			if(groupcols.indexOf("branddept")!=-1){
				DepartMent departMent = getDepartmentByDeptid(salesCarReport.getBranddept());
				if(null!=departMent){
					salesCarReport.setBranddeptname(departMent.getName());
				}else{
					salesCarReport.setBranddeptname("其他");
				}
			}
		}
		int count = salesCustomerReportMapper.getSalesCarReportCount(pageMap);
		PageData pageData = new PageData(count,list,pageMap);
		//取合计数据
		pageMap.getCondition().put("groupcols", "all");
		List<SalesCarReport> footer = salesCustomerReportMapper.getSalesCarReportList(pageMap);
		for(SalesCarReport salesCarReportSum : footer){
			if(null!=salesCarReportSum){
				if(pageMap.getCondition().containsKey("isflag")){
					salesCarReportSum.setUnitnumInteger(salesCarReportSum.getUnitnum().intValue());
					salesCarReportSum.setAuxInteger(salesCarReportSum.getAuxnum().intValue());
					salesCarReportSum.setOvernumInteger(salesCarReportSum.getOvernum().intValue());
				}
				salesCarReportSum.setAuxunitname("");
				salesCarReportSum.setUnitname("");
				salesCarReportSum.setTaxprice(null);
				String[] groupArr = groupcols.split(",");
				if(groupArr[0].indexOf("branddept")!=-1){
					salesCarReportSum.setBranddept("");
					salesCarReportSum.setBranddeptname("合计");
					salesCarReportSum.setBrandid("");
					salesCarReportSum.setBrandname("");
					salesCarReportSum.setSalesdept("");
					salesCarReportSum.setSalesdeptname("");
					salesCarReportSum.setSalesuser("");
					salesCarReportSum.setSalesusername("");
					salesCarReportSum.setSalesarea("");
					salesCarReportSum.setSalesareaname("");
					salesCarReportSum.setBranduser("");
					salesCarReportSum.setBrandusername("");
					salesCarReportSum.setGoodsid("");
					salesCarReportSum.setGoodsname("");
					salesCarReportSum.setCustomerid("");
					salesCarReportSum.setCustomername("");
					salesCarReportSum.setCustomersort("");
					salesCarReportSum.setCustomersortname("");
					salesCarReportSum.setPcustomerid("");
					salesCarReportSum.setPcustomername("");
					salesCarReportSum.setCaruser("");
					salesCarReportSum.setCarusername("");
				}
				else if(groupArr[0].indexOf("brandid")!=-1){
					salesCarReportSum.setBranddept("");
					salesCarReportSum.setBranddeptname("");
					salesCarReportSum.setBrandid("");
					salesCarReportSum.setBrandname("合计");
					salesCarReportSum.setSalesdept("");
					salesCarReportSum.setSalesdeptname("");
					salesCarReportSum.setSalesuser("");
					salesCarReportSum.setSalesusername("");
					salesCarReportSum.setSalesarea("");
					salesCarReportSum.setSalesareaname("");
					salesCarReportSum.setBranduser("");
					salesCarReportSum.setBrandusername("");
					salesCarReportSum.setGoodsid("");
					salesCarReportSum.setGoodsname("");
					salesCarReportSum.setCustomerid("");
					salesCarReportSum.setCustomername("");
					salesCarReportSum.setCustomersort("");
					salesCarReportSum.setCustomersortname("");
					salesCarReportSum.setPcustomerid("");
					salesCarReportSum.setPcustomername("");
					salesCarReportSum.setCaruser("");
					salesCarReportSum.setCarusername("");
				}
				else if(groupArr[0].indexOf("salesarea")!=-1){
					salesCarReportSum.setBranddept("");
					salesCarReportSum.setBranddeptname("");
					salesCarReportSum.setBrandid("");
					salesCarReportSum.setBrandname("");
					salesCarReportSum.setSalesdept("");
					salesCarReportSum.setSalesdeptname("");
					salesCarReportSum.setSalesuser("");
					salesCarReportSum.setSalesusername("");
					salesCarReportSum.setSalesarea("");
					salesCarReportSum.setSalesareaname("合计");
					salesCarReportSum.setBranduser("");
					salesCarReportSum.setBrandusername("");
					salesCarReportSum.setGoodsid("");
					salesCarReportSum.setGoodsname("");
					salesCarReportSum.setCustomerid("");
					salesCarReportSum.setCustomername("");
					salesCarReportSum.setCustomersort("");
					salesCarReportSum.setCustomersortname("");
					salesCarReportSum.setPcustomerid("");
					salesCarReportSum.setPcustomername("");
					salesCarReportSum.setCaruser("");
					salesCarReportSum.setCarusername("");
				}
				else if(groupArr[0].indexOf("salesdept")!=-1){
					salesCarReportSum.setBranddept("");
					salesCarReportSum.setBranddeptname("");
					salesCarReportSum.setBrandid("");
					salesCarReportSum.setBrandname("");
					salesCarReportSum.setSalesdept("");
					salesCarReportSum.setSalesdeptname("合计");
					salesCarReportSum.setSalesuser("");
					salesCarReportSum.setSalesusername("");
					salesCarReportSum.setSalesarea("");
					salesCarReportSum.setSalesareaname("");
					salesCarReportSum.setBranduser("");
					salesCarReportSum.setBrandusername("");
					salesCarReportSum.setGoodsid("");
					salesCarReportSum.setGoodsname("");
					salesCarReportSum.setCustomerid("");
					salesCarReportSum.setCustomername("");
					salesCarReportSum.setCustomersort("");
					salesCarReportSum.setCustomersortname("");
					salesCarReportSum.setPcustomerid("");
					salesCarReportSum.setPcustomername("");
					salesCarReportSum.setCaruser("");
					salesCarReportSum.setCarusername("");
				}
				else if(groupArr[0].indexOf("salesuser")!=-1){
					salesCarReportSum.setBranddept("");
					salesCarReportSum.setBranddeptname("");
					salesCarReportSum.setBrandid("");
					salesCarReportSum.setBrandname("");
					salesCarReportSum.setSalesdept("");
					salesCarReportSum.setSalesdeptname("");
					salesCarReportSum.setSalesuser("");
					salesCarReportSum.setSalesusername("合计");
					salesCarReportSum.setSalesarea("");
					salesCarReportSum.setSalesareaname("");
					salesCarReportSum.setBranduser("");
					salesCarReportSum.setBrandusername("");
					salesCarReportSum.setGoodsid("");
					salesCarReportSum.setGoodsname("");
					salesCarReportSum.setCustomerid("");
					salesCarReportSum.setCustomername("");
					salesCarReportSum.setCustomersort("");
					salesCarReportSum.setCustomersortname("");
					salesCarReportSum.setPcustomerid("");
					salesCarReportSum.setPcustomername("");
					salesCarReportSum.setCaruser("");
					salesCarReportSum.setCarusername("");
				}
				else if(groupArr[0].indexOf("branduser")!=-1){
					salesCarReportSum.setBranddept("");
					salesCarReportSum.setBranddeptname("");
					salesCarReportSum.setBrandid("");
					salesCarReportSum.setBrandname("");
					salesCarReportSum.setSalesdept("");
					salesCarReportSum.setSalesdeptname("");
					salesCarReportSum.setSalesuser("");
					salesCarReportSum.setSalesusername("");
					salesCarReportSum.setSalesarea("");
					salesCarReportSum.setSalesareaname("");
					salesCarReportSum.setBranduser("");
					salesCarReportSum.setBrandusername("合计");
					salesCarReportSum.setGoodsid("");
					salesCarReportSum.setGoodsname("");
					salesCarReportSum.setCustomerid("");
					salesCarReportSum.setCustomername("");
					salesCarReportSum.setCustomersort("");
					salesCarReportSum.setCustomersortname("");
					salesCarReportSum.setPcustomerid("");
					salesCarReportSum.setPcustomername("");
					salesCarReportSum.setCaruser("");
					salesCarReportSum.setCarusername("");
				}
				else if(groupArr[0].indexOf("goodsid")!=-1){
					salesCarReportSum.setBranddept("");
					salesCarReportSum.setBranddeptname("");
					salesCarReportSum.setBrandid("");
					salesCarReportSum.setBrandname("");
					salesCarReportSum.setSalesdept("");
					salesCarReportSum.setSalesdeptname("");
					salesCarReportSum.setSalesuser("");
					salesCarReportSum.setSalesusername("");
					salesCarReportSum.setSalesarea("");
					salesCarReportSum.setSalesareaname("");
					salesCarReportSum.setBranduser("");
					salesCarReportSum.setBrandusername("");
					salesCarReportSum.setGoodsid("");
					salesCarReportSum.setGoodsname("合计");
					salesCarReportSum.setCustomerid("");
					salesCarReportSum.setCustomername("");
					salesCarReportSum.setCustomersort("");
					salesCarReportSum.setCustomersortname("");
					salesCarReportSum.setPcustomerid("");
					salesCarReportSum.setPcustomername("");
					salesCarReportSum.setCaruser("");
					salesCarReportSum.setCarusername("");
				}
				else if(groupArr[0].indexOf("pcustomerid")!=-1){
					salesCarReportSum.setBranddept("");
					salesCarReportSum.setBranddeptname("");
					salesCarReportSum.setBrandid("");
					salesCarReportSum.setBrandname("");
					salesCarReportSum.setSalesdept("");
					salesCarReportSum.setSalesdeptname("");
					salesCarReportSum.setSalesuser("");
					salesCarReportSum.setSalesusername("");
					salesCarReportSum.setSalesarea("");
					salesCarReportSum.setSalesareaname("");
					salesCarReportSum.setBranduser("");
					salesCarReportSum.setBrandusername("");
					salesCarReportSum.setGoodsid("");
					salesCarReportSum.setGoodsname("");
					salesCarReportSum.setCustomerid("");
					salesCarReportSum.setCustomername("");
					salesCarReportSum.setCustomersort("");
					salesCarReportSum.setCustomersortname("");
					salesCarReportSum.setPcustomerid("");
					salesCarReportSum.setPcustomername("合计");
					salesCarReportSum.setCaruser("");
					salesCarReportSum.setCarusername("");
				}
				else if(groupArr[0].indexOf("customerid")!=-1){
					salesCarReportSum.setBranddept("");
					salesCarReportSum.setBranddeptname("");
					salesCarReportSum.setBrandid("");
					salesCarReportSum.setBrandname("");
					salesCarReportSum.setSalesdept("");
					salesCarReportSum.setSalesdeptname("");
					salesCarReportSum.setSalesuser("");
					salesCarReportSum.setSalesusername("");
					salesCarReportSum.setSalesarea("");
					salesCarReportSum.setSalesareaname("");
					salesCarReportSum.setBranduser("");
					salesCarReportSum.setBrandusername("");
					salesCarReportSum.setGoodsid("");
					salesCarReportSum.setGoodsname("");
					salesCarReportSum.setCustomerid("");
					salesCarReportSum.setCustomername("合计");
					salesCarReportSum.setCustomersort("");
					salesCarReportSum.setCustomersortname("");
					salesCarReportSum.setPcustomerid("");
					salesCarReportSum.setPcustomername("");
					salesCarReportSum.setCaruser("");
					salesCarReportSum.setCarusername("");
				}
				else if(groupArr[0].indexOf("customersort")!=-1){
					salesCarReportSum.setBranddept("");
					salesCarReportSum.setBranddeptname("");
					salesCarReportSum.setBrandid("");
					salesCarReportSum.setBrandname("");
					salesCarReportSum.setSalesdept("");
					salesCarReportSum.setSalesdeptname("");
					salesCarReportSum.setSalesuser("");
					salesCarReportSum.setSalesusername("");
					salesCarReportSum.setSalesarea("");
					salesCarReportSum.setSalesareaname("");
					salesCarReportSum.setBranduser("");
					salesCarReportSum.setBrandusername("");
					salesCarReportSum.setGoodsid("");
					salesCarReportSum.setGoodsname("");
					salesCarReportSum.setCustomerid("");
					salesCarReportSum.setCustomername("");
					salesCarReportSum.setCustomersort("");
					salesCarReportSum.setCustomersortname("合计");
					salesCarReportSum.setPcustomerid("");
					salesCarReportSum.setPcustomername("");
					salesCarReportSum.setCaruser("");
					salesCarReportSum.setCarusername("");
				}
				else if(groupArr[0].indexOf("caruser")!=-1){
					salesCarReportSum.setBranddept("");
					salesCarReportSum.setBranddeptname("");
					salesCarReportSum.setBrandid("");
					salesCarReportSum.setBrandname("");
					salesCarReportSum.setSalesdept("");
					salesCarReportSum.setSalesdeptname("");
					salesCarReportSum.setSalesuser("");
					salesCarReportSum.setSalesusername("");
					salesCarReportSum.setSalesarea("");
					salesCarReportSum.setSalesareaname("");
					salesCarReportSum.setBranduser("");
					salesCarReportSum.setBrandusername("");
					salesCarReportSum.setGoodsid("");
					salesCarReportSum.setGoodsname("");
					salesCarReportSum.setCustomerid("");
					salesCarReportSum.setCustomername("");
					salesCarReportSum.setCustomersort("");
					salesCarReportSum.setCustomersortname("");
					salesCarReportSum.setPcustomerid("");
					salesCarReportSum.setPcustomername("");
					salesCarReportSum.setCaruser("");
					salesCarReportSum.setCarusername("合计");
				}
			}else{
				footer = new ArrayList<SalesCarReport>();
			}
		}
		pageData.setFooter(footer);
		return pageData;
	}
	
	public PageData showSalesGoodsQuotationReportData(PageMap pageMap) throws Exception{
		Map condition=pageMap.getCondition();

		String dataSql = getDataAccessRule("t_base_sales_customer", "cus");
		if(null!=dataSql && !"".equals(dataSql)){
			condition.put("customerDataSql", dataSql);
		}else{
			if(condition.containsKey("customerDataSql")){
				condition.remove("customerDataSql");
			}
		}

		boolean queryByCustomer=false;
		String customerid=(String)condition.get("customerid");
		String pricecode=(String) condition.get("pricecode");
		String customername="";

		if(null!=customerid && !"".equals(customerid.trim())){
			Customer customer = getCustomerByID(customerid.trim());
			if(null!=customer){
				customername=customer.getName();
			}
			queryByCustomer=true;
		}else{
			if(condition.containsKey("customerid")){
				condition.remove("customerid");
			}
			if(null==pricecode || "".equals(pricecode.trim())){
				condition.remove("pricecode");
			}
		}

		/***********************************************************************
		 * 分销规则 start
		 **********************************************************************/
		{
			if(StringUtils.isNotEmpty(customerid)) {

				List okDistributions = salesService.selectDistributionRuleIdByCustomer(customerid, "1");
				if(okDistributions.size() > 0) {

					pageMap.getCondition().put("okDistributions", okDistributions);
				}
				List ngDistributions = salesService.selectDistributionRuleIdByCustomer(customerid, "0");
				if(ngDistributions.size() > 0) {

					pageMap.getCondition().put("ngDistributions", ngDistributions);
				}
			}
		}
		/***********************************************************************
		 * 分销规则 end
		 **********************************************************************/

		List<SalesCustomerPriceReport> list=salesCustomerReportMapper.getSalesGoodsQuotationReportData(pageMap);
		for(SalesCustomerPriceReport item : list){
			if(queryByCustomer){
				BigDecimal price=getGoodsPriceByCustomer(item.getGoodsid(),customerid);
				item.setPrice(price);
			}else{
				GoodsInfo_PriceInfo priceInfo=getBaseGoodsMapper().getPriceInfoByGoodsAndCode(item.getGoodsid(), pricecode);
				if(null!=priceInfo){
					item.setPrice(priceInfo.getTaxprice());
				}else{
					item.setPrice(BigDecimal.ZERO);
				}
			}
			if(StringUtils.isNotEmpty(item.getBrandid())){
				Brand brand = getBaseGoodsMapper().getBrandInfo(item.getBrandid());
				if(null!=brand){
					item.setBrandname(brand.getName());
				}				
			}
			if(StringUtils.isNotEmpty(item.getGoodsid())){
				GoodsInfo goodsInfo=getAllGoodsInfoByID(item.getGoodsid());
				item.setGoodsInfo(goodsInfo);
				if(null!=goodsInfo && null!=goodsInfo.getBoxnum()){
					item.setBoxprice(goodsInfo.getBoxnum().multiply(item.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
				}
			}
			
		}
		int count=salesCustomerReportMapper.getSalesGoodsQuotationReportDataCount(pageMap);
		PageData pageData=new PageData(count,list,pageMap);
		return pageData;
	}
	public PageData showSalesQuantityReportData(PageMap pageMap) throws Exception{
		Map condition=pageMap.getCondition();
		if(null==condition){
			condition=new HashMap();
		}
		String groupby="";
		if(null!=condition.get("groupbystorage") && StringUtils.isNotEmpty(condition.get("groupbystorage").toString())){
			condition.put("groupbystorage", "true");
			groupby="groupbystorage";
			condition.remove("groupbybrand");
		}
		if(null!=condition.get("groupbybrand") && StringUtils.isNotEmpty(condition.get("groupbybrand").toString())){
			condition.put("groupbybrand", "true");
			groupby="groupbybrand";
			condition.remove("groupbystorage");
		}
		List<SalesQuantityReport> list= salesGoodsReportMapper.getSalesQuantityReport(pageMap);
		boolean isRemainder=false;
		for(SalesQuantityReport item : list){
			if(StringUtils.isNotEmpty(item.getStorageid())){
				StorageInfo storageInfo = getStorageInfoByID(item.getStorageid());
				if(null!=storageInfo){
					item.setStoragename(storageInfo.getName());
				}			
			}
			if("groupbybrand".equals(groupby)){
				if(StringUtils.isNotEmpty(item.getBrandid())){
					Brand brand = getGoodsBrandByID(item.getBrandid());
					if(null!=brand){
						item.setBrandname(brand.getName());
					}			
				}			
			}
		}
		int total =salesGoodsReportMapper.getSalesQuantityReportCount(pageMap);
		PageData pageData=new PageData(total, list, pageMap);
		
		condition.put("isShowSumTotal", "true");
		condition.put("ispageflag", "true");
		List<SalesQuantityReport> footer= salesGoodsReportMapper.getSalesQuantityReport(pageMap);
		if(null!=footer && footer.size()>0 && !(footer.size()==1 && null ==footer.get(0))){
			for(SalesQuantityReport item : footer){
				if(null!=item){
					item.setStorageid("");
					item.setBrandid("");
					item.setBrandname("合计");
					item.setAuxunitname(null);
					item.setAuxremainder(null);
					
				}
			}
		}else{
			footer=new ArrayList<SalesQuantityReport>();
		}
		pageData.setFooter(footer);
		return pageData;
	}

    /**
     * @author lin_xx
     * @param pageMap
     * @return
     * @throws Exception
     */
    @Override
    public PageData showSalesUserTargetReportData(PageMap pageMap) throws Exception {
        	/*
		String cols = getAccessColumnList("t_report_customertarget",null); //字段权限
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_report_customertarget",null); //数据权限
		pageMap.setDataSql(sql);
		*/
        //获取客户业务员和所有客户的集合
        List<Map> list=salesCustomerReportMapper.getSalesTargetReportSalesUserCustomerData(pageMap);
        for(Map map : list){
            String customerid = (String) map.get("customerid");
            Customer customer = getCustomerByID(customerid);
            if(null != customer){
                map.put("customername",customer.getName());
            }
            String salesuser = (String) map.get("salesuser");
            Personnel personnel = getPersonnelById(salesuser);
            if(null != personnel){
                map.put("salesusername",personnel.getName());
            }
            String brandid = (String) map.get("brand");
            Brand brand = getGoodsBrandByID(brandid);
            if(null != personnel){
                map.put("brandname",brand.getName());
            }
            BigDecimal saleamount = (BigDecimal) map.get("saleamount");
            //获取销售目标
            Map targetMap = new HashMap();
            targetMap.put("billtype","SalesUserTargetCustomerBrand");
            targetMap.put("brandid",brandid);
            targetMap.put("busid",customerid);
            targetMap.put("salesuser",salesuser);
            ReportTarget reportTarget = reportTargetMapper.getReportTargetInfo(targetMap);
            if(null != reportTarget){
                BigDecimal targetamount = reportTarget.getTargetamount();
                if(null != targetamount && targetamount.compareTo(BigDecimal.ZERO) > 0){
                    map.put("targets",targetamount);
                    BigDecimal salerate = saleamount.divide(targetamount,BigDecimal.ROUND_HALF_UP);
                    map.put("salerate",salerate);
                }
                if(null != reportTarget.getField05()){
                    map.put("nweektargets",reportTarget.getField05());
                }
            }else{
                map.put("targets",new BigDecimal(0));
                map.put("nweektargets",new BigDecimal(0));
            }
        }
        int icount= salesCustomerReportMapper.getSalesTargetReportSalesUserCustomerCount(pageMap);
        PageData pageData = new PageData(icount,list,pageMap);
        Map footerMap = salesCustomerReportMapper.sumSalesTargetReportSalesUser(pageMap);
        List<Map> footer = new ArrayList<Map>();
        Map condition = pageMap.getCondition();
        Map reportMap = new HashMap();
        if(condition.containsKey("brandidarr")){
            reportMap.put("brandid",condition.get("brandidarr"));
        }
        if(condition.containsKey("customeridarr")){
            reportMap.put("busid",condition.get("customeridarr"));
        }
        if(condition.containsKey("salesuser")){
            reportMap.put("salesuser",condition.get("salesuser"));
        }
        reportMap.put("billtype","SalesUserTargetCustomerBrand");
        Map m = reportTargetMapper.getReportTargetSum(reportMap);
        if(null != footerMap){
            footerMap.put("customername","合计");
            if(null != m){
                footerMap.putAll(m);
            }
            footer.add(footerMap);
        }else if(null != m){
            m.put("customername","合计");
            footer.add(m);
        }
        pageData.setFooter(footer);
        return pageData;
    }

    @Override
	public PageData showSalesCustomerTargetReportData(PageMap pageMap) throws Exception{
		/*
		String cols = getAccessColumnList("t_report_customertarget",null); //字段权限
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_report_customertarget",null); //数据权限
		pageMap.setDataSql(sql);
		*/
		Map condition=pageMap.getCondition();
		StringBuilder columnsb=new StringBuilder();
		StringBuilder brandSalessb=new StringBuilder();
		StringBuilder brandTargetsb=new StringBuilder();

		List<String> brandIdList=new ArrayList<String>();
		List<Brand> brandList=getBaseGoodsMapper().getBrandList();
		String brandidarr=null;
		for(Brand itemBrand : brandList){
			if(null==itemBrand || StringUtils.isEmpty(itemBrand.getId())){
				continue;
			}
			brandIdList.add(itemBrand.getId());
			
			columnsb.append(",0.000000 AS saleamount_");
			columnsb.append(itemBrand.getId());
			columnsb.append(",0.000000 AS qqsaleamount_");
			columnsb.append(itemBrand.getId());
			columnsb.append(",0.000000 AS targets_");
			columnsb.append(itemBrand.getId());
			columnsb.append(",0.000000 AS nweektargets_");
			columnsb.append(itemBrand.getId());
			
			brandSalessb.append(",SUM(IF(IFNULL(brand,'') ='");
			brandSalessb.append(itemBrand.getId());
			brandSalessb.append("' ,saleamount,0.000000)) AS saleamount_");
			brandSalessb.append(itemBrand.getId());
			
			brandSalessb.append(",SUM(IF(IFNULL(brand,'') ='");
			brandSalessb.append(itemBrand.getId());
			brandSalessb.append("' ,qqsaleamount,0.000000)) AS qqsaleamount_");
			brandSalessb.append(itemBrand.getId());
			
			brandTargetsb.append(",SUM(IF(IFNULL(brand,'') ='");
			brandTargetsb.append(itemBrand.getId());
			brandTargetsb.append("' ,targets,0.000000)) AS targets_");
			brandTargetsb.append(itemBrand.getId());
			
			brandTargetsb.append(",SUM(IF(IFNULL(brand,'') ='");
			brandTargetsb.append(itemBrand.getId());
			brandTargetsb.append("' ,nweektargets,0.000000)) AS nweektargets_");
			brandTargetsb.append(itemBrand.getId());
			
			
		}
		if(columnsb.length()>0){
			condition.put("dyncBrandCustomerColumn", columnsb.toString());
			condition.put("dyncSalesColumn", brandSalessb.toString());
			condition.put("dyncTargetsColumn", brandTargetsb.toString());
		}else{
			if(condition.containsKey("dyncBrandCustomerColumn")){
				condition.remove("dyncBrandCustomerColumn");
			}
			if(condition.containsKey("dyncSalesColumn")){
				condition.remove("dyncSalesColumn");
			}
			if(condition.containsKey("dyncTargetsColumn")){
				condition.remove("dyncTargetsColumn");
			}
		}
		if(brandIdList.size()>0){
			String brandIds[]=new String[brandIdList.size()];
			brandIdList.toArray(brandIds);
			brandidarr=StringUtils.join(brandIds, ",");
		}
		
		List<Map> list=salesCustomerReportMapper.getSalesTargetReportBranduserCustomerData(pageMap);
		int icount=salesCustomerReportMapper.getSalesTargetReportBranduserCustomerCount(pageMap);

		if(null!=brandidarr && !"".equals(brandidarr.trim())){
			condition.put("brandidarr", brandidarr.trim());
		}else{
			if(condition.containsKey("brandidarr")){
				condition.remove("brandidarr");
			}
		}
		for(Map itemMap : list){
			itemMap.put("brandusername", "");
			itemMap.put("customername", "");
			String branduser=(String) itemMap.get("branduser");
			String customerid=(String) itemMap.get("customerid");
			if(null != branduser && !"".equals(branduser.trim())){
				Personnel personnel = getPersonnelById(branduser.trim());
				if(null!=personnel){
					itemMap.put("brandusername", personnel.getName());
				}
			}
			if(null != customerid && !"".equals(customerid.trim())){
				Customer customer = getCustomerByID(customerid.trim());
				if(null!=customer){
					itemMap.put("customername", customer.getName());
				}
			}
			if(!(null != branduser && !"".equals(branduser.trim()) && null != customerid && !"".equals(customerid.trim()))){
				continue;
			}
			condition.put("branduser", branduser.trim());
			condition.put("customerid", customerid.trim());
			
			Map salesMap=salesCustomerReportMapper.getSalesCustomerTargetReportSalesData(pageMap);
			Map targetsMap=salesCustomerReportMapper.getSalesCustomerTargetReportTargetsData(pageMap);
			BigDecimal saleamount=new BigDecimal(0);
			BigDecimal qqsaleamount=new BigDecimal(0);
			BigDecimal tmpa=new BigDecimal(0);
			for(Brand itemBrand : brandList){
				tmpa=new BigDecimal(0);
				
				if(null!=salesMap){
					if(salesMap.containsKey("saleamount_"+itemBrand.getId())){
						tmpa=(BigDecimal) salesMap.get("saleamount_"+itemBrand.getId());
						if(null!=tmpa && BigDecimal.ZERO.compareTo(tmpa)!=0){
							itemMap.put("saleamount_"+itemBrand.getId(), tmpa);
						}
					}
					if(salesMap.containsKey("qqsaleamount_"+itemBrand.getId())){
						tmpa=(BigDecimal) salesMap.get("qqsaleamount_"+itemBrand.getId());
						if(null!=tmpa && BigDecimal.ZERO.compareTo(tmpa)!=0){
							itemMap.put("qqsaleamount_"+itemBrand.getId(), tmpa);
						}
					}
				}
				
				if(itemMap.containsKey("saleamount_"+itemBrand.getId())){
					tmpa=(BigDecimal) itemMap.get("saleamount_"+itemBrand.getId());
					if(null!=tmpa && BigDecimal.ZERO.compareTo(tmpa)!=0){
						saleamount=saleamount.add(tmpa);
					}
				}
				tmpa=new BigDecimal(0);
				if(itemMap.containsKey("qqsaleamount_"+itemBrand.getId())){
					tmpa=(BigDecimal) itemMap.get("qqsaleamount_"+itemBrand.getId());
					if(null!=tmpa && BigDecimal.ZERO.compareTo(tmpa)!=0){
						qqsaleamount=qqsaleamount.add(tmpa);
					}
				}
				if(null!=targetsMap){
					if(targetsMap.containsKey("targets_"+itemBrand.getId())){
						tmpa=(BigDecimal) targetsMap.get("targets_"+itemBrand.getId());
						if(null!=tmpa && BigDecimal.ZERO.compareTo(tmpa)!=0){
							itemMap.put("targets_"+itemBrand.getId(), tmpa);
						}
					}
					if(targetsMap.containsKey("nweektargets_"+itemBrand.getId())){
						tmpa=(BigDecimal) targetsMap.get("nweektargets_"+itemBrand.getId());
						if(null!=tmpa && BigDecimal.ZERO.compareTo(tmpa)!=0){
							itemMap.put("nweektargets_"+itemBrand.getId(), tmpa);
						}
					}
				}
				if( BigDecimal.ZERO.compareTo(saleamount)!=0 && itemMap.containsKey("targets_"+itemBrand.getId())){
					BigDecimal targets=(BigDecimal) itemMap.get("targets_"+itemBrand.getId());
					if( null!=targets && BigDecimal.ZERO.compareTo(targets) != 0 ){
						BigDecimal rate=saleamount.divide(targets, 4, BigDecimal.ROUND_HALF_UP);
						itemMap.put("salerate_"+itemBrand.getId(), rate);
					}
				}
			}
			itemMap.put("saleamount", saleamount.setScale(6, BigDecimal.ROUND_HALF_UP));
			itemMap.put("qqsaleamount", qqsaleamount.setScale(6, BigDecimal.ROUND_HALF_UP));
		}
		
		PageData pageData = new PageData(icount,list,pageMap);
		return pageData;
		
	}
	@Override
	public boolean saveSalesCustomerTargetReport(Map map) throws Exception{
		String targetsList=(String)map.get("targetsList");
		String branddept=(String)map.get("branddept");
		String startdate=(String)map.get("bqstartdate");
		String enddate=(String)map.get("bqenddate");
		List<Map> list=null;
		if(null!=targetsList && !"".equals(targetsList.trim())){
			list=JSONUtils.jsonStrToList(targetsList.trim(),new HashMap());
		}
		if(null==list || list.size()==0){
			return false;
		}
		List<Brand> brandList=getBaseGoodsMapper().getBrandListWithParentByDeptid(branddept);
		if(null==brandList || brandList.size()==0){
			return false;
		}
		int isuccess=0;
		for(Map item : list){
			for(Brand itemBrand : brandList){				
				String branduser=(String) item.get("branduser");
				String customerid=(String)item.get("customerid");
				if(null==itemBrand || "".equals(itemBrand.getId()) ||
						null==customerid || "".equals(customerid.trim()) ||
						null==branduser || "".equals(branduser.trim())){
					continue;
				}
				SalesCustomerTargetReport targetReport =new SalesCustomerTargetReport();
				String bigdString=(String)item.get("targets_"+itemBrand.getId());
				if(null==bigdString || "".equals(bigdString.trim())){
					bigdString="0";
				}
				BigDecimal targets=new BigDecimal(bigdString);
				bigdString=(String)item.get("nweektargets_"+itemBrand.getId());
				if(null==bigdString || "".equals(bigdString.trim())){
					bigdString="0";
				}
				BigDecimal nweektargets=new BigDecimal(bigdString);
				targetReport.setBranddept(branddept);
				targetReport.setStartdate(startdate);
				targetReport.setEnddate(enddate);
				targetReport.setBrand(itemBrand.getId());
				targetReport.setBranduser(branduser.trim());
				targetReport.setCustomerid(customerid);
				targetReport.setTargets(targets);
				targetReport.setNweektargets(nweektargets);
				if(salesCustomerReportMapper.getSalesCustomerTargetReportTargetsCount(targetReport)>0){
					if(salesCustomerReportMapper.updateSalesCustomerTargetReport(targetReport)>0){
						isuccess++;
					}
				}else{
					if(targets.compareTo(BigDecimal.ZERO)==0 && nweektargets.compareTo(BigDecimal.ZERO)==0){
						isuccess++;
					}else{
						if(salesCustomerReportMapper.addSalesCustomerTargetReport(targetReport)>0){
							isuccess++;
						}
					}
				}
			}
			if(isuccess>0){
				return true;
			}else{
				return false;
			}
		}
		
		return true;
	}
	
	public PageData showSalesBrandGrossReportData(PageMap pageMap) throws Exception{
		Map condition=pageMap.getCondition();
		List<Map> list=salesGoodsReportMapper.getSalesBrandGrossReportData(pageMap);
		String brandid="";
		for(Map item : list){
			brandid=(String)item.get("brandid");
			if(null!=brandid && !"".equals(brandid.trim())){
				Brand brand= getBaseGoodsMapper().getBrandInfo(brandid.trim());
				if(null!=brand){
					item.put("brandname", brand.getName());
				}
			}
		}
		int icount=salesGoodsReportMapper.getSalesBrandGrossReportDataCount(pageMap);
		PageData pageData = new PageData(icount,list,pageMap);
		condition.put("isShowSumTotal", "true");
		condition.put("isPageflag", "true");
		List<Map> footer= salesGoodsReportMapper.getSalesBrandGrossReportData(pageMap);
		if(null!=footer && footer.size()>0 && !(footer.size()==1 && null ==footer.get(0))){
			for(Map item : footer){
				if(null!=item){
					item.put("brandid", "");
					item.put("brandname", "合计");					
				}
			}
		}else{
			footer=new ArrayList<Map>();
		}
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public List getSalesReportListByPcustomer(String pcustomerid,
			String begindate, String enddate) throws Exception {
		List list = salesCustomerReportMapper.getSalesReportListByPcustomer(pcustomerid, begindate, enddate);
		return list;
	}
	@Override
	public List getSupplierSalesAmountByDetpid(String begindate,
			String enddate, String detpid) throws Exception {
		List list = salesCustomerReportMapper.getSupplierSalesAmountByDetpid(detpid, begindate, enddate);
		return list;
	}

    /**
     * 根据日期 获取客户的销售金额
     *
     * @param date
     * @param salesDataType
     * @return
     * @throws Exception
     */
    @Override
    public List getCustomerSalesAmountByDate(String date,String salesDataType,String salesdept) throws Exception {
        List list = salesCustomerReportMapper.getCustomerSalesAmountByDate(date,salesDataType,salesdept);
        return list;
    }

	/**
	 * 根据日期 获取客户的销售金额,按每条单据汇总数据
	 *
	 * @param date
	 * @param salesDataType
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getCustomerSalesAmountByDateForThird(String date,String salesDataType,String salesdept) throws Exception {
		List list = salesCustomerReportMapper.getCustomerSalesAmountByDateForThird(date, salesDataType, salesdept);
		return list;
	}

    @Override
	public PageData showBrandAssessReportData(PageMap pageMap) throws Exception{
		String dataSql = getDataAccessRule("t_report_sales_base", "z");
		pageMap.setDataSql(dataSql);
		List<Map> list = salesCustomerReportMapper.getBrandSalesReportList(pageMap);
		BigDecimal totalSalesamount = BigDecimal.ZERO;
		BigDecimal totalTargetamount = BigDecimal.ZERO;
		BigDecimal totalgrossamount = BigDecimal.ZERO;
		
		String year = (String) pageMap.getCondition().get("year");
		String month = (String) pageMap.getCondition().get("month");
		for(Map map : list){
			String branddept = (String) map.get("branddept");
			DepartMent departMent = getDepartmentByDeptid(branddept);
			if(null!=departMent){
				map.put("branddeptname", departMent.getName());	
			}
			String brandid = (String) map.get("brandid");
			Brand brand = getGoodsBrandByID(brandid);
			if(null!=brand){
				map.put("brandname", brand.getName());
			}
			BigDecimal salesamount = (BigDecimal) map.get("salesamount");
			BigDecimal costamount = (BigDecimal) map.get("costamount");
			BigDecimal grossamount = salesamount.subtract(costamount);
			BigDecimal targetamount = BigDecimal.ZERO;
			BigDecimal salesrate = BigDecimal.ZERO;
			ReportTarget reportTarget = reportTargetMapper.getReportTargetByYearAndMonth("SalesBrand", brandid, year, month);
			if(null!=reportTarget){
				targetamount = reportTarget.getTargetamount();
				if(targetamount.compareTo(BigDecimal.ZERO)!=0){
                    if(targetamount.compareTo(BigDecimal.ZERO) > 0 && salesamount.compareTo(BigDecimal.ZERO) >= 0){
                        //销售完成率  = （销售额/销售指标）*100%
                        salesrate = salesamount.divide(targetamount, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    }else{
                       if(salesamount.compareTo(targetamount) >= 0){
                           salesrate = new BigDecimal(100);
                       }else{
                            BigDecimal midData = salesamount.subtract(targetamount);
                           salesrate = midData.divide(targetamount.abs(), 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                       }
                    }
				}
			}
			map.put("salesrate", salesrate);
			map.put("salestarget", targetamount);
			map.put("grossamount", grossamount);
			totalSalesamount = totalSalesamount.add(salesamount);
			totalTargetamount = totalTargetamount.add(targetamount);
			totalgrossamount = totalgrossamount.add(grossamount);
		}
		PageData pageData = new PageData(list.size(), list, pageMap);
		List footer = new ArrayList();
		Map fmap = new HashMap();
		fmap.put("brandname", "合计");
		fmap.put("salesamount", totalSalesamount);
		fmap.put("salestarget", totalTargetamount);
		fmap.put("grossamount", totalgrossamount);
		if(totalTargetamount.compareTo(BigDecimal.ZERO)!=0){
			BigDecimal totalSalesrate = BigDecimal.ZERO;
			if(totalTargetamount.compareTo(BigDecimal.ZERO) > 0 && totalSalesamount.compareTo(BigDecimal.ZERO) >= 0){
				//销售完成率  = （销售额/销售指标）*100%
				totalSalesrate = totalSalesamount.divide(totalTargetamount, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
			}else{
				if(totalSalesamount.compareTo(totalTargetamount) >= 0){
					totalSalesrate = new BigDecimal(100);
				}else{
					BigDecimal midData = totalSalesamount.subtract(totalTargetamount);
					totalSalesrate = midData.divide(totalTargetamount.abs(), 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
				}
			}
			//销售完成率  = （销售额/销售指标）*100%
			BigDecimal salesrate = totalSalesamount.divide(totalTargetamount, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
			fmap.put("salesrate", totalSalesrate);
		}
		footer.add(fmap);
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public PageData showCustomerNewGoodsReportData(PageMap pageMap)
			throws Exception {
		String computeBeginDate = getSysParamValue("CustomerGoodsBeginDate");
		if(StringUtils.isNotEmpty(computeBeginDate)){
			pageMap.getCondition().put("computeBeginDate", computeBeginDate);
		}
		String dataSql = getDataAccessRule("t_report_sales_base", "z");
		pageMap.setDataSql(dataSql);
		List<Map> list = salesCustomerReportMapper.showCustomerNewGoodsReportList(pageMap);
		for(Map map : list){
			String goodsid = (String) map.get("goodsid");
			GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
			if(null!=goodsInfo){
				map.put("goodsname", goodsInfo.getName());
			}
			String customerid = (String) map.get("customerid");
			Customer customer = getCustomerByID(customerid);
			if(null!=customer){
				map.put("customername", customer.getName());
			}
			String pcustomerid = (String) map.get("pcustomerid");
			Customer pcustomer = getCustomerByID(pcustomerid);
			if(null!=pcustomer){
				map.put("pcustomername", pcustomer.getName());
			}
			String salesarea = (String) map.get("salesarea");
			SalesArea salesArea = getSalesareaByID(salesarea);
			if(null!=salesArea){
				map.put("salesareaname", salesArea.getName());
			}
			String salesuser = (String) map.get("salesuser");
			Personnel personnel = getPersonnelById(salesuser);
			if(null!=personnel){
				map.put("salesusername", personnel.getName());
			}
			
			String brandid = (String) map.get("brandid");
			Brand brand = getGoodsBrandByID(brandid);
			if(null!=brand){
				map.put("brandname", brand.getName());
			}
		}
		PageData pageData = new PageData(salesCustomerReportMapper.showCustomerNewGoodsReportCount(pageMap), list, pageMap);
		Map footerMap = salesCustomerReportMapper.showCustomerNewGoodsReportSum(pageMap);
		if(null!=footerMap){
			footerMap.put("customername", "合计");
			List footerList = new ArrayList();
			footerList.add(footerMap);
			pageData.setFooter(footerList);
		}
		return pageData;
	}

	@Override
	public PageData showCustomerOldGoodsReportData(PageMap pageMap)
			throws Exception {
		String computeBeginDate = getSysParamValue("CustomerGoodsBeginDate");
		if(StringUtils.isNotEmpty(computeBeginDate)){
			pageMap.getCondition().put("computeBeginDate", computeBeginDate);
		}
		String dataSql = getDataAccessRule("t_report_sales_base", "z");
		pageMap.setDataSql(dataSql);
		List<Map> list = salesCustomerReportMapper.showCustomerOldGoodsReportList(pageMap);
		for(Map map : list){
			String goodsid = (String) map.get("goodsid");
			GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
			BigDecimal unitnum = (BigDecimal) map.get("unitnum");
			if(null!=goodsInfo){
				map.put("goodsname", goodsInfo.getName());
				Map returnMap = countGoodsInfoNumber(goodsid, null, unitnum);
				if(null!=returnMap){
					String auxnumdetail = (String) returnMap.get("auxnumdetail");
					map.put("auxnumdetail", auxnumdetail);
				}
			}
			String customerid = (String) map.get("customerid");
			Customer customer = getCustomerByID(customerid);
			if(null!=customer){
				map.put("customername", customer.getName());
			}
			String pcustomerid = (String) map.get("pcustomerid");
			Customer pcustomer = getCustomerByID(pcustomerid);
			if(null!=pcustomer){
				map.put("pcustomername", pcustomer.getName());
			}
			String salesarea = (String) map.get("salesarea");
			SalesArea salesArea = getSalesareaByID(salesarea);
			if(null!=salesArea){
				map.put("salesareaname", salesArea.getName());
			}
			String salesuser = (String) map.get("salesuser");
			Personnel personnel = getPersonnelById(salesuser);
			if(null!=personnel){
				map.put("salesusername", personnel.getName());
			}
			
			String brandid = (String) map.get("brandid");
			Brand brand = getGoodsBrandByID(brandid);
			if(null!=brand){
				map.put("brandname", brand.getName());
			}
		}
		PageData pageData = new PageData(salesCustomerReportMapper.showCustomerOldGoodsReportCount(pageMap), list, pageMap);
		Map footerMap = salesCustomerReportMapper.showCustomerOldGoodsReportSum(pageMap);
		if(null!=footerMap){
			footerMap.put("customername", "合计");
			BigDecimal auxnum = (BigDecimal) footerMap.get("auxnum");
			BigDecimal auxremainder = (BigDecimal) footerMap.get("auxremainder");
			String auxnumdetail = auxnum.intValue()+","+auxremainder;
			footerMap.put("auxnumdetail", CommonUtils.strDigitNumDeal(auxnumdetail));
			List footerList = new ArrayList();
			footerList.add(footerMap);
			pageData.setFooter(footerList);
		}
		return pageData;
	}

	@Override
	public PageData getSalesRejectEnterReportData(PageMap pageMap)
			throws Exception {
		String dataSql = getDataAccessRule("t_report_sales_base", "z");
		pageMap.setDataSql(dataSql);
		List<BaseSalesReport> list = salesCustomerReportMapper.getSalesRejectEnterReportList(pageMap);
		for(BaseSalesReport baseSalesReport : list){
			if(null == baseSalesReport){
				continue;
			}
			Personnel personnel = getPersonnelById(baseSalesReport.getDriverid());
			if(null != personnel){
				baseSalesReport.setDrivername(personnel.getName());
			}else{
				baseSalesReport.setDrivername("其他");
			}
			//退货率=退货金额(不包括直退)/销售金额
			if(null != baseSalesReport.getSendnum() && baseSalesReport.getSendnum().compareTo(new BigDecimal(0))==1 && null != baseSalesReport.getCheckreturnnum()){
				BigDecimal checkreturnrate = baseSalesReport.getCheckreturnnum().divide(baseSalesReport.getSendnum(), 6, BigDecimal.ROUND_HALF_UP);
				baseSalesReport.setCheckreturnrate(checkreturnrate);
			}
			String auxreturnnumdetail = baseSalesReport.getAuxreturnnum().setScale(0) + baseSalesReport.getAuxunitname() + baseSalesReport.getAuxremainderreturnnum().setScale(0);
			baseSalesReport.setAuxreturnnumdetail(auxreturnnumdetail);
			String auxdirectnumdetail = baseSalesReport.getAuxdirectnum().setScale(0) + baseSalesReport.getAuxunitname() + baseSalesReport.getAuxremainderdirectnum().setScale(0);
			baseSalesReport.setAuxdirectnumdetail(auxdirectnumdetail);
			String auxchecknumdetail = baseSalesReport.getAuxchecknum().setScale(0) + baseSalesReport.getAuxunitname() + baseSalesReport.getAuxremainderchecknum().setScale(0);
			baseSalesReport.setAuxchecknumdetail(auxchecknumdetail);
			
			//判断是否为导出使用，若为导出，则将直退数量、售后退货数量显示为负数
			if(pageMap.getCondition().containsKey("isflag") && null != pageMap.getCondition().get("isflag")){
				//直退数量
				baseSalesReport.setDirectreturnnum(baseSalesReport.getDirectreturnnum().negate());
				//直退金额
				baseSalesReport.setDirectreturnamount(baseSalesReport.getDirectreturnamount().negate());
				//售后退货数量
				baseSalesReport.setCheckreturnnum(baseSalesReport.getCheckreturnnum().negate());
				//退货金额
				baseSalesReport.setCheckreturnamount(baseSalesReport.getCheckreturnamount().negate());
				//退货总数量
				baseSalesReport.setReturnnum(baseSalesReport.getReturnnum().negate());
				//退货合计
				baseSalesReport.setReturnamount(baseSalesReport.getReturnamount().negate());
			}
		}
		PageData pageData = new PageData(salesCustomerReportMapper.getSalesRejectEnterReportCount(pageMap),list,pageMap);
		List<BaseSalesReport> foot = new ArrayList<BaseSalesReport>();
		BaseSalesReport baseSalesReportSum = salesCustomerReportMapper.getSalesRejectEnterReportSum(pageMap);
		if(null != baseSalesReportSum){
			//退货率=退货数量/发货主数量
			if(null != baseSalesReportSum.getSendnum() && baseSalesReportSum.getSendnum().compareTo(new BigDecimal(0))==1 && null != baseSalesReportSum.getCheckreturnnum()){
				BigDecimal checkreturnrate = baseSalesReportSum.getCheckreturnnum().divide(baseSalesReportSum.getSendnum(), 6, BigDecimal.ROUND_HALF_UP);
				baseSalesReportSum.setCheckreturnrate(checkreturnrate);
			}
			String auxreturnnumdetail = baseSalesReportSum.getAuxreturnnum().setScale(0) + baseSalesReportSum.getAuxunitname() + baseSalesReportSum.getAuxremainderreturnnum().setScale(0);
			baseSalesReportSum.setAuxreturnnumdetail(auxreturnnumdetail);
			String auxdirectnumdetail = baseSalesReportSum.getAuxdirectnum().setScale(0) + baseSalesReportSum.getAuxunitname() + baseSalesReportSum.getAuxremainderdirectnum().setScale(0);
			baseSalesReportSum.setAuxdirectnumdetail(auxdirectnumdetail);
			String auxchecknumdetail = baseSalesReportSum.getAuxchecknum().setScale(0) + baseSalesReportSum.getAuxunitname() + baseSalesReportSum.getAuxremainderchecknum().setScale(0);
			baseSalesReportSum.setAuxchecknumdetail(auxchecknumdetail);
			
			//判断是否为导出使用，若为导出，则将直退数量、售后退货数量显示为负数
			if(pageMap.getCondition().containsKey("isflag") && null != pageMap.getCondition().get("isflag")){
				//直退数量
				baseSalesReportSum.setDirectreturnnum(baseSalesReportSum.getDirectreturnnum().negate());
				//直退金额
				baseSalesReportSum.setDirectreturnamount(baseSalesReportSum.getDirectreturnamount().negate());
				//售后退货数量
				baseSalesReportSum.setCheckreturnnum(baseSalesReportSum.getCheckreturnnum().negate());
				//退货金额
				baseSalesReportSum.setCheckreturnamount(baseSalesReportSum.getCheckreturnamount().negate());
				//退货总数量
				baseSalesReportSum.setReturnnum(baseSalesReportSum.getReturnnum().negate());
				//退货合计
				baseSalesReportSum.setReturnamount(baseSalesReportSum.getReturnamount().negate());
			}
			baseSalesReportSum.setDrivername("合计");
		}else{
			baseSalesReportSum = new BaseSalesReport();
			baseSalesReportSum.setDrivername("合计");
		}
		foot.add(baseSalesReportSum);
		pageData.setFooter(foot);
		return pageData;
	}

	@Override
	public PageData getSalesWithdrawnAssessData(PageMap pageMap)
			throws Exception {
		String dataSql = getDataAccessRule("t_report_sales_base", "z");
		pageMap.setDataSql(dataSql);

        if(pageMap.getCondition().containsKey("customerbrand")){//分客户业务员品牌销售回笼考核表
            Map m = pageMap.getCondition();
            m.put("groupcols","salesuser,brandid");
            pageMap.setCondition(m);
        }else if(pageMap.getCondition().containsKey("personbranduser")){//分品牌业务员品牌销售回笼考核表
            Map m = pageMap.getCondition();
            m.put("groupcols","branduser,brandid");
            m.put("sort","branduser,brandid");
            pageMap.setCondition(m);
        }

		String groupcols = "",begindate = "";

        //customer参数 表示客户业务员报表
        String customer = "";
        if(pageMap.getCondition().containsKey("customer")){
            customer = (String) pageMap.getCondition().get("customer");
        }else if(pageMap.getCondition().containsKey("customerbrand")){
            customer = (String) pageMap.getCondition().get("customerbrand");
        }

		Map map3 = new HashMap();
		Map map4 = new HashMap();
		if(pageMap.getCondition().containsKey("businessdate1")){
			String businessdate = (String) pageMap.getCondition().get("businessdate1");
			businessdate = StringEscapeUtils.escapeSql(businessdate);
			begindate = businessdate;
			
			map3.put("begindate", businessdate);
			map4.put("begindate", businessdate);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");  
		    Date date = sdf.parse(businessdate);
			String businessdate1 = CommonUtils.getNowMonthDay(date);
			pageMap.getCondition().put("businessdate1", businessdate1);
		    String businessdate2 = (new SimpleDateFormat("yyyy-MM-dd")).format(CommonUtils.getLastDayOfMonth(date));
		    pageMap.getCondition().put("businessdate2", businessdate2);
		}
		if(pageMap.getCondition().containsKey("groupcols")){
			groupcols = (String) pageMap.getCondition().get("groupcols");
		}
		if(pageMap.getCondition().containsKey("branduser")){
			String branduser = (String) pageMap.getCondition().get("branduser");
			branduser = StringEscapeUtils.escapeSql(branduser);
			map4.put("busid", branduser);
		}
        if(pageMap.getCondition().containsKey("salesuser")){
            String branduser = (String) pageMap.getCondition().get("salesuser");
            branduser = StringEscapeUtils.escapeSql(branduser);
            if(branduser.contains(",")){
                map4.put("busid1", branduser);
            }else{
                map4.put("busid", branduser);
            }
        }
		if(pageMap.getCondition().containsKey("brandid")){
			String brandid = (String) pageMap.getCondition().get("brandid");
			brandid = StringEscapeUtils.escapeSql(brandid);
            map4.put("brandid", brandid);
		}
		if(pageMap.getCondition().containsKey("personbranduser")){
			map3.put("billtype", "SalesWithdrawnBrandByBrand");
			map4.put("billtype", "SalesWithdrawnBrandByBrand");
		}else if("branduser".equals(groupcols)){
            map3.put("billtype", "SalesWithdrawnBranduser");
            map4.put("billtype", "SalesWithdrawnBranduser");
        }else if("brandid".equals(groupcols)){
			map3.put("billtype", "SalesWithdrawnBrand");
			map4.put("billtype", "SalesWithdrawnBrand");
			if(map4.containsKey("brandid")){
				map4.put("busid",map4.get("brandid"));
				map4.remove("brandid");
			}
		}else if("1".equals(customer)){
            map3.put("billtype", "SalesWithdrawnCustomerUser");
            map4.put("billtype", "SalesWithdrawnCustomerUser");
        }else if("2".equals(customer)){
            map3.put("billtype", "SalesWithdrawnCustomerBrand");
            map4.put("billtype", "SalesWithdrawnCustomerBrand");
        }else if("customersort".equals(groupcols)){
			map3.put("billtype", "SalesWithdrawnCustomersort");
			map4.put("billtype", "SalesWithdrawnCustomersort");
			if(pageMap.getCondition().containsKey("customersort")){
				String customersort = (String) pageMap.getCondition().get("customersort");
				customersort = StringEscapeUtils.escapeSql(customersort);
				map4.put("busid", customersort);
			}
		}else if(pageMap.getCondition().containsKey("salesdeptbrand")){
			map3.put("billtype", "SalesWithdrawnSalesdeptBrand");
			map4.put("billtype", "SalesWithdrawnSalesdeptBrand");
		}
        List<Map> list = salesCustomerReportMapper.getSalesWithdrawnAssessList(pageMap);

        String userEmpty = "null" ;
        for(Map map : list){
            if(null != map && !map.isEmpty()){
				map.put("begindate",pageMap.getCondition().containsKey("businessdate1"));
                String branduser = (String)map.get("branduser");
                String branddept = (String)map.get("branddept");
				String customersort = (String)map.get("customersort");
                String salesuser = (String)map.get("salesuser");
				String salesdept = (String)map.get("salesdept");
				DepartMent departMent1 = getDepartMentById(salesdept);
				if(null != departMent1){
					map.put("salesdeptname",departMent1.getName());
				}else{
					map.put("salesdeptname","其他未指定");
				}
                //判断是否为分客户报表
                if(StringUtils.isNotEmpty(customer)){

                    int flag = 0 ;
                    Personnel personnel = getPersonnelById(salesuser);
                    if(null != personnel){
                        map.put("salesusername", personnel.getName());
                        //客户员所在分公司
                        if(StringUtils.isNotEmpty(personnel.getBelongdeptid())){
                            map.put("salesdeptname", getDepartmentByDeptid(personnel.getBelongdeptid()).getName());
                        }

                    }else{
                        if(flag == 0){
                            ++ flag ;
                        }else{
                            userEmpty = salesuser;//获取最后一个在员工表中没有数据的客户业务员编码
                        }
                        map.put("salesusername", "其他(未指定客户业务员)");
                    }
                }else{
                    if("".equals(branduser)){
                        branddept = "";
                    }
                    int flag = 0 ;

                    Personnel personnel = getPersonnelById(branduser);
                    if(null != personnel){
                        map.put("brandusername", personnel.getName());
                    }else{
                        if(flag == 0){
                            ++ flag ;
                        }else{
                            userEmpty = branduser;//获取最后一个在员工表中没有数据的品牌业务员编码
                        }
                        map.put("brandusername", "其他(未指定品牌业务员)");
                    }
                    //分公司
                    Map map2 = new HashMap();
					map2.put("pid", "");
                    List<DepartMent> deptList = getBaseDepartMentMapper().getDeptListByParam(map2);
                    for(DepartMent dept : deptList){
                        if(branddept.indexOf(dept.getId()) == 0){
                            map.put("branddeptname", dept.getName());
                        }
                    }
                    if(!map.containsKey("branddeptname") && branduser != ""){
                        Personnel person = getPersonnelByName((String)map.get("brandusername"));
                        if(null != person){
                            DepartMent departMent = getDepartMentById(person.getBelongdeptid());
                            if(null != departMent){
                                map.put("branddeptname", departMent.getName());
                            }
                        }
                    }
                }

                String brandid = (String)map.get("brandid");
                BigDecimal sendamount = null != map.get("sendamount") ? (BigDecimal)map.get("sendamount") : BigDecimal.ZERO;
                BigDecimal returnamount = null != map.get("returnamount") ? (BigDecimal)map.get("returnamount") : BigDecimal.ZERO;
                BigDecimal costamount = null != map.get("costamount") ? (BigDecimal)map.get("costamount") : BigDecimal.ZERO;
                BigDecimal pushbalanceamount = null != map.get("pushbalanceamount") ? (BigDecimal)map.get("pushbalanceamount") : BigDecimal.ZERO;
                BigDecimal withdrawnamount = null != map.get("withdrawnamount") ? (BigDecimal)map.get("withdrawnamount") : BigDecimal.ZERO;
                BigDecimal costwriteoffamount = null != map.get("costwriteoffamount") ? (BigDecimal)map.get("costwriteoffamount") : BigDecimal.ZERO;

                Brand brand = getGoodsBrandByID(brandid);
                if(null != brand){
                    map.put("brandname", brand.getName());
                }else{
                    map.put("brandname", "其它");
                }
				if(StringUtils.isNotEmpty(customersort)){
					CustomerSort customerSort = getCustomerSortByID(customersort);
					if(null != customerSort){
						map.put("customersortname",customerSort.getThisname());
					}else{
						map.put("customersortname","其他未定义");
					}
				}else{
					map.put("customersortname","其他未指定");
				}

                map.put("begindate", begindate);
                //销售金额=发货出库金额-退货合计+冲差单
                BigDecimal saleamount = sendamount.subtract(returnamount).add(pushbalanceamount);
                map.put("saleamount", saleamount);
                //销售毛利额=销售金额 - 成本金额
                BigDecimal salemarginamount = saleamount.subtract(costamount);
                map.put("salemarginamount", salemarginamount);
                //实际毛利率=（销售金额 - 成本金额）/销售金额*100
                if(saleamount.compareTo(BigDecimal.ZERO) == 1){
                    BigDecimal realrate = salemarginamount.divide(saleamount, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
                    map.put("realrate", realrate);
                }else if(saleamount.compareTo(BigDecimal.ZERO) == -1){
                    BigDecimal realrate = salemarginamount.divide(saleamount, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
                    map.put("realrate", realrate.negate());
                }
                //回笼毛利额=回笼金额-回笼成本金额
                BigDecimal writeoffmarginamount = withdrawnamount.subtract(costwriteoffamount);
                map.put("writeoffmarginamount", writeoffmarginamount);
                //回笼毛利率=（回笼金额-回笼成本金额）/回笼金额
                if(withdrawnamount.compareTo(BigDecimal.ZERO)==1){
                    BigDecimal writeoffrate = writeoffmarginamount.divide(withdrawnamount, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
                    map.put("writeoffrate", writeoffrate);
                }else if(withdrawnamount.compareTo(BigDecimal.ZERO)==-1){
                    map.put("writeoffrate", new BigDecimal(100).negate());
                }

                if(pageMap.getCondition().containsKey("personbranduser")){//分品牌业务员品牌销售回笼考核表
                    map3.put("busid", branduser);
                    map3.put("brandid", brandid);
                }else if("branduser".equals(groupcols)){
                    map3.put("busid", branduser);
                }else if("brandid".equals(groupcols)){
                    map3.put("busid", brandid);
                }else if("salesuser".equals(groupcols) && "1".equals(customer)){//分客户业务员销售回笼考核表
                    map3.put("busid", salesuser);
                }else if(pageMap.getCondition().containsKey("customerbrand")){//分客户业务员品牌销售回笼考核表
                    map3.put("busid", salesuser);
                    map3.put("brandid", brandid);
                }else if("customersort".equals(groupcols)){//分客户分类销售回笼考核表
					map3.put("busid", customersort);
				}else if(pageMap.getCondition().containsKey("salesdeptbrand")){
					map3.put("busid", salesdept);
					map3.put("brandid",brandid);
				}
                //获取对应业务属性对应人员和其对应品牌的销售目标
                ReportTarget reportTarget = reportTargetMapper.getReportTargetInfo(map3);
                if(null != reportTarget){
                    BigDecimal saletargetamount = null != reportTarget.getTargetamount() ? reportTarget.getTargetamount() : BigDecimal.ZERO;
                    BigDecimal marginratetarget =  null != reportTarget.getField04() ? reportTarget.getField04() : BigDecimal.ZERO;
                    BigDecimal withdrawntargetamount =  null != reportTarget.getField05() ? reportTarget.getField05() : BigDecimal.ZERO;
                    BigDecimal writeoffratetarget =  null != reportTarget.getField06() ? reportTarget.getField06() : BigDecimal.ZERO;

                    map.put("saletargetamount", saletargetamount);
                    map.put("marginratetarget", marginratetarget);
                    map.put("withdrawntargetamount", withdrawntargetamount);
                    map.put("writeoffratetarget", writeoffratetarget);
					map.put("initsaletargetamount", saletargetamount);
					map.put("initmarginratetarget", marginratetarget);
					map.put("initwithdrawntargetamount", withdrawntargetamount);
					map.put("initwriteoffratetarget", writeoffratetarget);

                    //销售完成率=销售金额/本期销售目标
                    if(saletargetamount.compareTo(BigDecimal.ZERO) > 0 && saleamount.compareTo(BigDecimal.ZERO)>=0){//销售金额 本期销售目标 均为正数 时两者相除
                        BigDecimal saledonerate = saleamount.divide(saletargetamount, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                        map.put("saledonerate", saledonerate);

                    }else if(saletargetamount.compareTo(BigDecimal.ZERO) != 0){

                        if(saleamount.compareTo(saletargetamount) >= 0 ){//销售金额大于等于销售目标
                            map.put("saledonerate", 100);

                        }else{
                            //（销售金额-销售目标 ÷ |销售目标|
                            BigDecimal miniDate = saleamount.subtract(saletargetamount) ;
                            BigDecimal saledonerate = miniDate.divide(saletargetamount.abs(), 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                            map.put("saledonerate", saledonerate);
                        }
                    }
                    //本期目标毛利=本期销售目标*本期毛利率目标
                    BigDecimal salemargintargetamount = saletargetamount.multiply(marginratetarget.divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_UP));
                    //销售业绩超率计算
                    if(salemargintargetamount.compareTo(BigDecimal.ZERO) != 0){
                        if(salemargintargetamount.compareTo(BigDecimal.ZERO) > 0 && salemarginamount.compareTo(BigDecimal.ZERO) >= 0) {
                            BigDecimal salemargindonesurpassrate = salemarginamount.divide(salemargintargetamount, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                            map.put("salemargindonesurpassrate", salemargindonesurpassrate);

                        }else if( salemarginamount.compareTo(salemargintargetamount)>=0 ){
                            map.put("salemargindonesurpassrate", 100);
                        }else{
                            //销售业绩超率=销售毛利额/本期目标毛利
                            BigDecimal minData = salemarginamount.subtract(salemargintargetamount);
                            BigDecimal salemargindonesurpassrate = minData.divide(salemargintargetamount.abs(), 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                            map.put("salemargindonesurpassrate", salemargindonesurpassrate);
                        }
                    }

                    //回笼完成率=回笼金额/回笼目标
                    if(withdrawntargetamount.compareTo(BigDecimal.ZERO) > 0 &&
                            withdrawnamount.compareTo(BigDecimal.ZERO) >= 0){

                        BigDecimal withdrawndonerate = withdrawnamount.divide(withdrawntargetamount, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                        map.put("withdrawndonerate", withdrawndonerate);

                    }else if(withdrawntargetamount.compareTo(BigDecimal.ZERO) != 0){

                        if(withdrawnamount.compareTo(withdrawntargetamount) >=0){
                            map.put("withdrawndonerate", 100);
                        }else{
                            BigDecimal miniData = withdrawnamount.subtract(withdrawntargetamount);
                            BigDecimal withdrawndonerate = miniData.divide(withdrawntargetamount.abs(), 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                            map.put("withdrawndonerate", withdrawndonerate);
                        }
                    }
                    //回笼目标毛利=回笼目标*回笼毛利率目标
                    BigDecimal withdrawnmargintargetamount = withdrawntargetamount.multiply(writeoffratetarget.divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_UP));

                    //回笼业绩超率=回笼毛利额/回笼目标毛利
                    if(withdrawnmargintargetamount.compareTo(BigDecimal.ZERO) != 0){
                        if(withdrawnmargintargetamount.compareTo(BigDecimal.ZERO)> 0 &&
                                writeoffmarginamount.compareTo(BigDecimal.ZERO) >= 0 ){
                            //回笼毛利额 回笼目标毛利 均为正数 两者相除
                            BigDecimal withdrawnmargindonesurpassrate = writeoffmarginamount.divide(withdrawnmargintargetamount, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                            map.put("withdrawnmargindonesurpassrate", withdrawnmargindonesurpassrate);

                        }else if(writeoffmarginamount.compareTo(withdrawnmargintargetamount)<0){
                            //（回笼毛利额-回笼目标毛利) ÷ |回笼目标毛利|
                            BigDecimal miniData = writeoffmarginamount.subtract(withdrawnmargintargetamount);
                            BigDecimal withdrawnmargindonesurpassrate = miniData.divide(withdrawnmargintargetamount.abs(), 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                            map.put("withdrawnmargindonesurpassrate", withdrawnmargindonesurpassrate);

                        }else{
                            map.put("withdrawnmargindonesurpassrate", 100);
                        }
                    }
                }else{
                    map.put("saletargetamount", BigDecimal.ZERO);
                    map.put("marginratetarget", BigDecimal.ZERO);
                    map.put("withdrawntargetamount", BigDecimal.ZERO);
                    map.put("writeoffratetarget", BigDecimal.ZERO);
                }
            }
        }

        int count = salesCustomerReportMapper.getSalesWithdrawnAssessCount(pageMap);
        //移除多余的其它数据(只显示一条品牌业务员中业务员为空的数据)
        int index = -1 ;
        for(int i = 0 ;i<list.size();i++){
            Map m = list.get(i);
            if(m.containsValue(userEmpty)){
                index = i ;
            }
        }
        if(index != -1 && "branduser".equals(groupcols)){
            list.remove(index);
            -- count ;
        }

        PageData pageData = new PageData(count,list,pageMap);
        //合计
         Map map = new HashMap();
        List<Map> footer = new ArrayList<Map>();

        Map fixmap = salesCustomerReportMapper.getSalesWithdrawnAssessSum(pageMap);
        if(null == fixmap){
            map = showSalesWithdrawnAssessByBrandFooter(list);
        }else{
            map = fixmap ;
        }

        map = showSalesWithdrawnAssessFooter(map,pageMap,map4);
		//行编辑后计算合计时判断是否合计行
		map.put("issum","1");
		if(customer != ""){
			map.put("salesusername", "合计");
		}else if(groupcols.indexOf("branduser") != -1){
			map.put("brandusername", "合计");
		}else if("salesdept,brandid".equals(groupcols)){
			map.put("salesdeptname", "合计");
		}else if("brandid".equals(groupcols)){
			map.put("brandname", "合计");
		}else if("customersort".equals(groupcols)){
			map.put("customersortname", "合计");
		}
        footer.add(map);
        pageData.setFooter(footer);

        return pageData;
	}

    /**
     * 考核数据合计为空时页脚合计显示
      * @param list
     * @return
     * @throws Exception
     */
    public Map showSalesWithdrawnAssessByBrandFooter(List<Map> list) throws Exception {

        Map map = new HashMap();
        BigDecimal saleamount = BigDecimal.ZERO ;
        BigDecimal salemarginamount = BigDecimal.ZERO;
        BigDecimal realrate = BigDecimal.ZERO;
        BigDecimal writeoffmarginamount = BigDecimal.ZERO;
        BigDecimal writeoffrate = BigDecimal.ZERO;
        BigDecimal saletargetamount = BigDecimal.ZERO;
        BigDecimal withdrawntargetamount = BigDecimal.ZERO;
        BigDecimal withdrawnamount = BigDecimal.ZERO;

        for(int i = 0 ;i<list.size() ;++ i){
            Map dataMap = list.get(i);
            if(dataMap.containsKey("saleamount")){
                saleamount = saleamount.add((BigDecimal)dataMap.get("saleamount"));
            }
            if(dataMap.containsKey("salemarginamount")){
                salemarginamount = salemarginamount.add((BigDecimal)dataMap.get("salemarginamount"));
            }
            if(dataMap.containsKey("realrate")){
                realrate = realrate.add((BigDecimal)dataMap.get("realrate"));
            }
            if(dataMap.containsKey("writeoffmarginamount")){
                writeoffmarginamount = writeoffmarginamount.add((BigDecimal)dataMap.get("writeoffmarginamount"));
            }
            if(dataMap.containsKey("writeoffrate")){
                writeoffrate = writeoffrate.add((BigDecimal)dataMap.get("writeoffrate"));
            }
            if(dataMap.containsKey("saletargetamount")){
                saletargetamount = saletargetamount.add((BigDecimal)dataMap.get("saletargetamount"));
            }
            if(dataMap.containsKey("withdrawntargetamount")){
                withdrawntargetamount = withdrawntargetamount.add((BigDecimal)dataMap.get("withdrawntargetamount"));
            }
            if(dataMap.containsKey("withdrawnamount")){
                withdrawnamount = withdrawnamount.add((BigDecimal)dataMap.get("withdrawnamount"));
            }
        }

        map.put("saleamount", saleamount);
        map.put("salemarginamount", salemarginamount);
        map.put("realrate", realrate);
        map.put("writeoffmarginamount", writeoffmarginamount);
        map.put("writeoffrate", writeoffrate);
        map.put("saletargetamount", saletargetamount);
        map.put("withdrawntargetamount", withdrawntargetamount);
        map.put("withdrawnamount",withdrawnamount);

        return map;
    }

    /**
     * 销售考核报表页脚数据
     * @param map
     * @param pageMap
     * @return
     * @throws Exception
     */
    public Map showSalesWithdrawnAssessFooter(Map map,PageMap pageMap,Map map4) throws Exception {

        if(null != map && !map.isEmpty()){
            BigDecimal sendamount = null != map.get("sendamount") ? (BigDecimal)map.get("sendamount") : BigDecimal.ZERO;
            BigDecimal returnamount = null != map.get("returnamount") ? (BigDecimal)map.get("returnamount") : BigDecimal.ZERO;
            BigDecimal costamount = null != map.get("costamount") ? (BigDecimal)map.get("costamount") : BigDecimal.ZERO;
            BigDecimal pushbalanceamount = null != map.get("pushbalanceamount") ? (BigDecimal)map.get("pushbalanceamount") : BigDecimal.ZERO;
            BigDecimal withdrawnamount = null != map.get("withdrawnamount") ? (BigDecimal)map.get("withdrawnamount") : BigDecimal.ZERO;
            BigDecimal costwriteoffamount = null != map.get("costwriteoffamount") ? (BigDecimal)map.get("costwriteoffamount") : BigDecimal.ZERO;

            //销售金额=发货出库金额-退货合计+冲差单
            BigDecimal saleamount = sendamount.subtract(returnamount).add(pushbalanceamount);
            map.put("saleamount", saleamount);
            //销售毛利额=销售金额 - 成本金额
            BigDecimal salemarginamount = saleamount.subtract(costamount);
            map.put("salemarginamount", salemarginamount);
            //实际毛利率=（销售金额 - 成本金额）/销售金额*100
            if(saleamount.compareTo(BigDecimal.ZERO) == 1){
                BigDecimal realrate = salemarginamount.divide(saleamount, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
                map.put("realrate", realrate);
            }else if(saleamount.compareTo(BigDecimal.ZERO) == -1){
                BigDecimal realrate = salemarginamount.divide(saleamount, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
                map.put("realrate", realrate.negate());
            }
            //回笼毛利额=回笼金额-回笼成本金额
            BigDecimal writeoffmarginamount = withdrawnamount.subtract(costwriteoffamount);
            map.put("writeoffmarginamount", writeoffmarginamount);
            //回笼毛利率=（回笼金额-回笼成本金额）/回笼金额
            if(withdrawnamount.compareTo(BigDecimal.ZERO)==1){
                BigDecimal writeoffrate = writeoffmarginamount.divide(withdrawnamount, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
                map.put("writeoffrate", writeoffrate);
            }else if(withdrawnamount.compareTo(BigDecimal.ZERO)==-1){
                map.put("writeoffrate", new BigDecimal(100).negate());
            }
            ReportTarget reportTarget = reportTargetMapper.getReportTargetSumCaseSalesWithdrawnAssess(map4);
            if(null != reportTarget){
                BigDecimal saletarget = BigDecimal.ZERO;
                BigDecimal withdrawntarget = BigDecimal.ZERO;
				BigDecimal salemargintarget =  BigDecimal.ZERO;
				BigDecimal withdrawnmargintarget = BigDecimal.ZERO;

                BigDecimal saletargetamount = null != reportTarget.getTargetamount() ? reportTarget.getTargetamount() : BigDecimal.ZERO;
                BigDecimal withdrawntargetamount =  null != reportTarget.getField05() ? reportTarget.getField05() : BigDecimal.ZERO;
				BigDecimal salemargintargetamount =  null != reportTarget.getSalemargintargetamount() ? reportTarget.getSalemargintargetamount() : BigDecimal.ZERO;
				BigDecimal withdrawnmargintargetamount =  null != reportTarget.getWithdrawnmargintargetamount() ? reportTarget.getWithdrawnmargintargetamount() : BigDecimal.ZERO;

                if(withdrawntarget.compareTo(BigDecimal.ZERO) != 0){
                    withdrawntargetamount = withdrawntargetamount.subtract(withdrawntarget);
                }
                if(saletarget.compareTo(BigDecimal.ZERO) != 0){
                    saletargetamount = saletargetamount.subtract(saletarget);
                }
				if(salemargintarget.compareTo(BigDecimal.ZERO) != 0){
					salemargintargetamount = salemargintargetamount.subtract(salemargintarget);
				}
				if(withdrawnmargintarget.compareTo(BigDecimal.ZERO) != 0){
					withdrawnmargintargetamount = withdrawnmargintargetamount.subtract(withdrawnmargintarget);
				}

				//销售完成率=销售金额/本期销售目标
				if(saletargetamount.compareTo(BigDecimal.ZERO) > 0 && saleamount.compareTo(BigDecimal.ZERO)>=0){//销售金额 本期销售目标 均为正数 时两者相除
					BigDecimal saledonerate = saleamount.divide(saletargetamount, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
					map.put("saledonerate", saledonerate);

				}else if(saletargetamount.compareTo(BigDecimal.ZERO) != 0){
					if(saleamount.compareTo(saletargetamount) >= 0 ){//销售金额大于等于销售目标
						map.put("saledonerate", 100);

					}else{
						//（销售金额-销售目标 ÷ |销售目标|
						BigDecimal miniDate = saleamount.subtract(saletargetamount) ;
						BigDecimal saledonerate = miniDate.divide(saletargetamount.abs(), 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
						map.put("saledonerate", saledonerate);
					}
				}
				//销售业绩超率计算
				if(salemargintargetamount.compareTo(BigDecimal.ZERO) != 0){
					if(salemargintargetamount.compareTo(BigDecimal.ZERO) > 0 && salemarginamount.compareTo(BigDecimal.ZERO) >= 0) {
						BigDecimal salemargindonesurpassrate = salemarginamount.divide(salemargintargetamount, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
						map.put("salemargindonesurpassrate", salemargindonesurpassrate);

					}else if( salemarginamount.compareTo(salemargintargetamount)>=0 ){
						map.put("salemargindonesurpassrate", 100);
					}else{
						//销售业绩超率=销售毛利额/本期目标毛利
						BigDecimal minData = salemarginamount.subtract(salemargintargetamount);
						BigDecimal salemargindonesurpassrate = minData.divide(salemargintargetamount.abs(), 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
						map.put("salemargindonesurpassrate", salemargindonesurpassrate);
					}
				}

				//回笼完成率=回笼金额/回笼目标
				if(withdrawntargetamount.compareTo(BigDecimal.ZERO) > 0 &&
						withdrawnamount.compareTo(BigDecimal.ZERO) >= 0){
					BigDecimal withdrawndonerate = withdrawnamount.divide(withdrawntargetamount, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
					map.put("withdrawndonerate", withdrawndonerate);

				}else if(withdrawntargetamount.compareTo(BigDecimal.ZERO) != 0){
					if(withdrawnamount.compareTo(withdrawntargetamount) >=0){
						map.put("withdrawndonerate", 100);
					}else{
						BigDecimal miniData = withdrawnamount.subtract(withdrawntargetamount);
						BigDecimal withdrawndonerate = miniData.divide(withdrawntargetamount.abs(), 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
						map.put("withdrawndonerate", withdrawndonerate);
					}
				}
				//回笼业绩超率=回笼毛利额/回笼目标毛利
				if(withdrawnmargintargetamount.compareTo(BigDecimal.ZERO) != 0){
					if(withdrawnmargintargetamount.compareTo(BigDecimal.ZERO)> 0 &&
							writeoffmarginamount.compareTo(BigDecimal.ZERO) >= 0 ){
						//回笼毛利额 回笼目标毛利 均为正数 两者相除
						BigDecimal withdrawnmargindonesurpassrate = writeoffmarginamount.divide(withdrawnmargintargetamount, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
						map.put("withdrawnmargindonesurpassrate", withdrawnmargindonesurpassrate);

					}else if(writeoffmarginamount.compareTo(withdrawnmargintargetamount)<0){
						//（回笼毛利额-回笼目标毛利) ÷ |回笼目标毛利|
						BigDecimal miniData = writeoffmarginamount.subtract(withdrawnmargintargetamount);
						BigDecimal withdrawnmargindonesurpassrate = miniData.divide(withdrawnmargintargetamount.abs(), 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
						map.put("withdrawnmargindonesurpassrate", withdrawnmargindonesurpassrate);

					}else{
						map.put("withdrawnmargindonesurpassrate", 100);
					}
				}
				map.put("saletargetamount", saletargetamount);
				map.put("withdrawntargetamount", withdrawntargetamount);
				map.put("salemargintargetamount", salemargintargetamount);
				map.put("withdrawnmargintargetamount", withdrawnmargintargetamount);
            }
            return  map;
        }else{
            Map queryMap = new HashMap();
            String groupcols = "",begindate = "";
            if(pageMap.getCondition().containsKey("groupcols")){
                groupcols = (String) pageMap.getCondition().get("groupcols");
            }
            if(pageMap.getCondition().containsKey("begindate")){
                groupcols = (String) pageMap.getCondition().get("begindate");
            }

            if("branduser".equals(groupcols)){
                queryMap.put("billtype","SalesWithdrawnBranduser");
            }else if("brandid".equals(groupcols)){
                queryMap.put("billtype","SalesWithdrawnBrand");
            }

            queryMap.put("begindate",begindate);

            pageMap.setCondition(queryMap);

            String dataSql1 = getDataAccessRule("t_report_target", "z");
            pageMap.setDataSql(dataSql1);

            Map newmap = reportTargetMapper.reportTargetInfoSum(pageMap);

            if(newmap != null){
                newmap.put("saletargetamount",newmap.get("targetamount"));
                newmap.put("saleamount", 0);
                newmap.put("salemarginamount", 0);
                newmap.put("writeoffmarginamount", 0);
                newmap.put("withdrawnamount",0);
            }else{
                newmap = new HashMap();
                newmap.put("saletargetamount",0);
                newmap.put("saleamount", 0);
                newmap.put("salemarginamount", 0);
                newmap.put("writeoffmarginamount", 0);
                newmap.put("withdrawnamount",0);
            }
            return newmap;
        }
    }

    @Override
    public PageData showCollectReportData(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_collect_customer_report", "z");
		pageMap.setDataSql(dataSql);
        //排序
        String orderstr = "";
        if(pageMap.getCondition().containsKey("sort")){
            String sort = (String) pageMap.getCondition().get("sort");
            String order = (String) pageMap.getCondition().get("order");
            if(null==order){
                order = "asc";
            }
            orderstr = sort +" " +order;
        }
        pageMap.getCondition().put("orderstr", orderstr);

        List<Map> list = salesCustomerReportMapper.showCollectReportData(pageMap);
        for(Map map :list){
            if(null == map || map.isEmpty()){
                continue;
            }
            String customerid = null != map.get("customerid") ? (String)map.get("customerid") : "";
            //获取客户应收金额
            BigDecimal allunwithdrawnamount = salesCustomerReportMapper.getAllunwithdrawnamount(customerid);
            map.put("allunwithdrawnamount",allunwithdrawnamount);

            String pcustomerid = null != map.get("pcustomerid") ? (String)map.get("pcustomerid") : "";
            String customersort = null != map.get("customersort") ? (String)map.get("customersort") : "";
            String salesarea = null != map.get("salesarea") ? (String)map.get("salesarea") : "";
            String salesdept = null != map.get("salesdept") ? (String)map.get("salesdept") : "";
            String salesuser = null != map.get("salesuser") ? (String)map.get("salesuser") : "";

            BigDecimal sendnum = null != map.get("sendnum") ? (BigDecimal)map.get("sendnum") : BigDecimal.ZERO;
            BigDecimal returnnum = null != map.get("returnnum") ? (BigDecimal)map.get("returnnum") : BigDecimal.ZERO;
            BigDecimal sendamount = null != map.get("sendamount") ? (BigDecimal)map.get("sendamount") : BigDecimal.ZERO;
            BigDecimal returnamount = null != map.get("returnamount") ? (BigDecimal)map.get("returnamount") : BigDecimal.ZERO;
            BigDecimal pushbalanceamount = null != map.get("pushbalanceamount") ?  (BigDecimal)map.get("pushbalanceamount") : BigDecimal.ZERO;
            BigDecimal pushbalancenotaxamount = null != map.get("pushbalancenotaxamount") ?  (BigDecimal)map.get("pushbalancenotaxamount") : BigDecimal.ZERO;
			BigDecimal sendnotaxamount = null != map.get("sendnotaxamount") ? (BigDecimal)map.get("sendnotaxamount") : BigDecimal.ZERO;
            BigDecimal returnnotaxamount = null != map.get("returnnotaxamount") ? (BigDecimal)map.get("returnnotaxamount") : BigDecimal.ZERO;
            BigDecimal costamount = null != map.get("costamount") ? (BigDecimal)map.get("costamount") : BigDecimal.ZERO;

            map.put("customerid",customerid);
            Customer customer = getCustomerByID(customerid);
            if(null != customer){
                map.put("customername",customer.getName());
            }
            Customer pcustomer = getCustomerByID(pcustomerid);
            if(null != pcustomer){
                map.put("pcustomername",pcustomer.getName());
            }
            CustomerSort customerSort = getCustomerSortByID(customersort);
            if(null != customerSort){
                map.put("customersortname",customerSort.getThisname());
            }
            SalesArea salesArea = getSalesareaByID(salesarea);
            if(null != salesArea){
                map.put("salesareaname",salesArea.getThisname());
            }
            DepartMent departMent = getDepartMentById(salesdept);
            if(null != departMent){
                map.put("salesdeptname",departMent.getName());
            }
            Personnel personnel = getPersonnelById(salesuser);
            if(null != personnel){
                map.put("salesusername",personnel.getName());
            }

            //销售数量 = 发货单数量 - 直退数量
            BigDecimal salenum = sendnum.subtract(returnnum);
            map.put("salenum",salenum);
            //销售金额
            BigDecimal saleamount = sendamount.subtract(returnamount).add(pushbalanceamount);
            BigDecimal salenotaxamount = sendnotaxamount.subtract(returnnotaxamount).add(pushbalancenotaxamount);
            //毛利额 = 销售金额 - 成本金额
            BigDecimal salemarginamount = saleamount.subtract(costamount);
            map.put("saleamount",saleamount);
            map.put("salenotaxamount",salenotaxamount);
            map.put("salemarginamount",salemarginamount);
            //实际毛利率 = （销售金额 - 成本金额）/销售金额*100
            if(saleamount.compareTo(BigDecimal.ZERO) != 0){
                BigDecimal realrate = salemarginamount.divide(saleamount, 6, BigDecimal.ROUND_HALF_UP);
                realrate = realrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
                if(saleamount.compareTo(BigDecimal.ZERO)==-1){
                    map.put("realrate",realrate.negate());
                }else{
                    map.put("realrate",realrate);
                }
            }else if(costamount.compareTo(BigDecimal.ZERO)==1){
                map.put("realrate",new BigDecimal(100).negate());
            }
            //销售税额
            BigDecimal saletax = saleamount.subtract(salenotaxamount);
            map.put("saletax",saletax);
//            //客户余额
//            CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(customerid);
//            if(null != customerCapital){
//                map.put("customeramount",customerCapital.getAmount());
//            }
        }

        int count = salesCustomerReportMapper.showCollectReportDataCount(pageMap);
        PageData pageData = new PageData(count,list,pageMap);
        //取合计数据
        pageMap.getCondition().put("groupcols", "all");
        List<Map> footer = salesCustomerReportMapper.showCollectReportData(pageMap);
        for(Map map : footer){
            if(null != map && !map.isEmpty()){
                BigDecimal sendnum = null != map.get("sendnum") ? (BigDecimal)map.get("sendnum") : BigDecimal.ZERO;
                BigDecimal returnnum = null != map.get("returnnum") ? (BigDecimal)map.get("returnnum") : BigDecimal.ZERO;
                BigDecimal sendamount = null != map.get("sendamount") ? (BigDecimal)map.get("sendamount") : BigDecimal.ZERO;
                BigDecimal returnamount = null != map.get("returnamount") ? (BigDecimal)map.get("returnamount") : BigDecimal.ZERO;
                BigDecimal pushbalanceamount = null != map.get("pushbalanceamount") ?  (BigDecimal)map.get("pushbalanceamount") : BigDecimal.ZERO;
				BigDecimal pushbalancenotaxamount = null != map.get("pushbalancenotaxamount") ?  (BigDecimal)map.get("pushbalancenotaxamount") : BigDecimal.ZERO;
                BigDecimal sendnotaxamount = null != map.get("sendnotaxamount") ? (BigDecimal)map.get("sendnotaxamount") : BigDecimal.ZERO;
                BigDecimal returnnotaxamount = null != map.get("returnnotaxamount") ? (BigDecimal)map.get("returnnotaxamount") : BigDecimal.ZERO;
                BigDecimal costamount = null != map.get("costamount") ? (BigDecimal)map.get("costamount") : BigDecimal.ZERO;

                //销售数量 = 发货单数量 - 直退数量
                BigDecimal salenum = sendnum.subtract(returnnum);
                map.put("salenum",salenum);
                //销售金额
                BigDecimal saleamount = sendamount.subtract(returnamount).add(pushbalanceamount);
                BigDecimal salenotaxamount = sendnotaxamount.subtract(returnnotaxamount).add(pushbalancenotaxamount);
                //毛利额 = 销售金额 - 成本金额
                BigDecimal salemarginamount = saleamount.subtract(costamount);
                map.put("saleamount",saleamount);
                map.put("salenotaxamount", salenotaxamount);
                map.put("salemarginamount",salemarginamount);
                //实际毛利率 = （销售金额 - 成本金额）/销售金额*100
                if(saleamount.compareTo(BigDecimal.ZERO) != 0){
                    BigDecimal realrate = salemarginamount.divide(saleamount, 6, BigDecimal.ROUND_HALF_UP);
                    realrate = realrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
                    if(saleamount.compareTo(BigDecimal.ZERO)==-1){
                        map.put("realrate",realrate.negate());
                    }else{
                        map.put("realrate",realrate);
                    }
                }else if(costamount.compareTo(BigDecimal.ZERO)==1){
                    map.put("realrate",new BigDecimal(100).negate());
                }
                //销售税额
                BigDecimal saletax = saleamount.subtract(salenotaxamount);
                map.put("saletax", saletax);
                map.put("customerid","");
                map.put("customername","合计");
            }else{
                footer = new ArrayList<Map>();
            }
        }
        pageData.setFooter(footer);
        return pageData;
    }

    @Override
    public PageData getCustomerUnsaleQueryReportList(PageMap pageMap) throws Exception {
        List<Map> list = salesCustomerReportMapper.getCustomerUnsaleQueryReportList(pageMap);
        for(Map map : list){
            String customerid = (String)map.get("customerid");
            String salesarea = (String)map.get("salesarea");
            String customersort = (String)map.get("customersort");
            String salesuser = (String)map.get("salesuser");

            Customer customer = getCustomerByID(customerid);
            if(null != customer){
                map.put("customername",customer.getName());
            }
            SalesArea salesArea = getSalesareaByID(salesarea);
            if(null != salesArea){
                map.put("salesareaname",salesArea.getThisname());
            }
            CustomerSort customerSort = getCustomerSortByID(customersort);
            if(null != customerSort){
                map.put("customersortname",customerSort.getThisname());
            }
            Personnel person = getPersonnelById(salesuser);
            if(null != person){
                map.put("salesusername",person.getName());
            }
        }
        PageData pageData = new PageData(salesCustomerReportMapper.getCustomerUnsaleQueryReportCount(pageMap),list,pageMap);

        List<Map> foot = salesCustomerReportMapper.getCustomerUnsaleQueryReportSum(pageMap);
        if(null != foot && foot.size() != 0){
            for(Map map : foot){
                map.put("customerid","合计");
            }
        }else{
            foot = new ArrayList<Map>();
        }
        pageData.setFooter(foot);
        return pageData;
    }
    
    
	public PageData showMonthSaleData(PageMap pageMap) throws Exception {
		//小计
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		if(!pageMap.getCondition().containsKey("groupcols")){
			groupcols = "customerid";
			pageMap.getCondition().put("groupcols", groupcols);
		}
		//数据权限
		if(groupcols.equals("adduserid")){
			String dataSql = getDataAccessRule("t_report_adduser_base", "z");
			pageMap.setDataSql(dataSql);
		}else{
			String dataSql = getDataAccessRule("t_report_sales_base", "z");
			pageMap.setDataSql(dataSql);
		}
		
		List<SaleMonthReport> list = salesCustomerReportMapper.showMonthSalesReportData(pageMap);
		for(SaleMonthReport monthReport : list){
//			//数据库查出来的值部分需要计算
			setDatasForMonthReport(monthReport);
			//把类似customerid获得name 用于前台显示
			bindDatas(groupcols, monthReport);
		}
		int count = salesCustomerReportMapper.getMonthSalesReportCount(pageMap);
		PageData pageData = new PageData(count,list,pageMap);
		//取合计数据
		pageMap.getCondition().put("group", "false");//不用group
		pageMap.getCondition().put("limit", "false"); //不用limit
		List<SaleMonthReport> rsList = null;
		rsList = salesCustomerReportMapper.showMonthSalesReportData(pageMap);
		if(rsList!=null&&rsList.size()>0&&rsList.get(0)!=null){
			//数据库查出来的值部分需要计算
			setDatasForMonthReport(rsList.get(0));
			//"合计"字符串的位置
			getFieldHeji(rsList.get(0), groupcols);
		}else{
			rsList = new ArrayList<SaleMonthReport>();
		}
		pageData.setFooter(rsList);
		return pageData;
	}
	
	/**
	 * 计算赋值,分月汇总报表的值
	 * @param monthReport
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @author huangzhiqian
	 * @date 2016年1月15日
	 */
	private void setDatasForMonthReport(SaleMonthReport monthReport) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		for(int i = 1 ; i <13;i++){
			String monthStr = i+"";
			if(i<10){
				monthStr="0"+monthStr;
			}
//				-t1.unitnum*t1.costprice as costamount,	0 AS sendamount	, t1.taxamount AS returnamount,0 as pushbalanceamount
			Method getMethodCostAmount = SaleMonthReport.class.getMethod("getCostamount"+monthStr);
			Method getMethodSendAmount = SaleMonthReport.class.getMethod("getSendamount"+monthStr);
			Method getMethodReturnamount = SaleMonthReport.class.getMethod("getReturnamount"+monthStr);
			Method getMethodPushbalanceamount = SaleMonthReport.class.getMethod("getPushbalanceamount"+monthStr);
			Method getRealRateMethod = SaleMonthReport.class.getMethod("getRealrate"+monthStr);
			Method setMethodSaleAmount = SaleMonthReport.class.getMethod("setSaleamount"+monthStr, new Class[]{java.math.BigDecimal.class});
			Method setMethodSaleRate =  SaleMonthReport.class.getMethod("setRealrate"+monthStr, new Class[]{java.math.BigDecimal.class});
			BigDecimal coustamount =(BigDecimal) getMethodCostAmount.invoke(monthReport);//成本金额
			BigDecimal sendamount = (BigDecimal) getMethodSendAmount.invoke(monthReport);//发货金额
			BigDecimal returnamount = (BigDecimal) getMethodReturnamount.invoke(monthReport);//退货金额
			BigDecimal pushbalanceamount = (BigDecimal) getMethodPushbalanceamount.invoke(monthReport);//冲差金额
			BigDecimal saleAmount = sendamount.subtract(returnamount).add(pushbalanceamount);//销售金额
			setMethodSaleAmount.invoke(monthReport,saleAmount);
			Method setMethodRealSaleAmount = SaleMonthReport.class.getMethod("setRealsaleamount"+monthStr, new Class[]{java.math.BigDecimal.class});
			setMethodRealSaleAmount.invoke(monthReport, saleAmount.subtract(coustamount));
			//实际毛利率 = （销售金额 - 成本金额）/销售金额*100
			if(saleAmount.compareTo(BigDecimal.ZERO)!=0){
				BigDecimal realrate = saleAmount.subtract(coustamount).divide(saleAmount, 6, BigDecimal.ROUND_HALF_UP);
				realrate = realrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
				if(saleAmount.compareTo(BigDecimal.ZERO)==-1){
					realrate=realrate.negate();
				}
				setMethodSaleRate.invoke(monthReport, realrate);
			}
			
		}
	}
	
	/**
	 * 把类似customerid获得name 用于前台显示
	 * @param groupcols
	 * @param monthReport
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2016年1月15日
	 */
	private void bindDatas(String groupcols, SaleMonthReport monthReport) throws Exception {
		if(StringUtils.isNotEmpty(monthReport.getGoodsid())){
			GoodsInfo goodsInfo = getGoodsInfoByID(monthReport.getGoodsid());
			if(null != goodsInfo){
				monthReport.setBarcode(goodsInfo.getBarcode());
				monthReport.setGoodsname(goodsInfo.getName());
			}
		}
		if(groupcols.indexOf("customerid")!=-1){
			Customer customer = getCustomerByID(monthReport.getCustomerid());
			if(null!=customer){
				monthReport.setCustomername(customer.getName());
			}
			if(groupcols.indexOf("pcustomerid")==-1){
				Customer pcustomer = getCustomerByID(monthReport.getPcustomerid());
				if(null!=pcustomer ){
					monthReport.setPcustomername(pcustomer.getName());
				}
			}
			if(groupcols.indexOf("salesdept")==-1){
				DepartMent departMent = getDepartmentByDeptid(monthReport.getSalesdept());
				if(null!=departMent){
					monthReport.setSalesdeptname(departMent.getName());
				}else{
					monthReport.setSalesdeptname("其他");
				}
			}
			if(groupcols.indexOf("customersort")==-1){
				CustomerSort customerSort = getCustomerSortByID(monthReport.getCustomersort());
				if(null!=customerSort){
					monthReport.setCustomersortname(customerSort.getName());
				}else{
					monthReport.setCustomersortname("未指定分类");
				}
			}
			if(groupcols.indexOf("salesarea")==-1){
				SalesArea salesArea = getSalesareaByID(monthReport.getSalesarea());
				if(null!=salesArea){
					monthReport.setSalesareaname(salesArea.getName());
				}else{
					monthReport.setSalesareaname("其他");
				}
			}
			if(groupcols.indexOf("salesuser")==-1){
				Personnel personnel = getPersonnelById(monthReport.getSalesuser());
				if(null!=personnel){
					monthReport.setSalesusername(personnel.getName());
				}else{
					monthReport.setSalesusername("其他");
				}
				DepartMent departMent = getDepartmentByDeptid(monthReport.getSalesdept());
				if(null!=departMent){
					monthReport.setSalesdeptname(departMent.getName());
				}else{
					monthReport.setSalesdeptname("其他");
				}
			}
		}else{
			monthReport.setCustomerid("");
			monthReport.setCustomername("");
			if(groupcols.indexOf("salesuser")==-1 && groupcols.indexOf("salesdept")==-1){
				monthReport.setSalesdeptname("");
			}
			
		}
		if(groupcols.indexOf("pcustomerid")!=-1){
			Customer pcustomer = getCustomerByID(monthReport.getPcustomerid());
			if(null!=pcustomer ){
				monthReport.setPcustomername(pcustomer.getName());
			}else{
				monthReport.setPcustomername("其他客户总和");
			}
			if(groupcols.indexOf("customerid")==1){
				monthReport.setCustomerid("");
				monthReport.setCustomername("");
				
			}
			
		}
		if(groupcols.indexOf("customersort")!=-1){
			CustomerSort customerSort = getCustomerSortByID(monthReport.getCustomersort());
			if(null!=customerSort){
				monthReport.setCustomersortname(customerSort.getName());
			}else{
				monthReport.setCustomersortname("未指定分类");
			}
		}
		if(groupcols.indexOf("salesarea")!=-1){
			SalesArea salesArea = getSalesareaByID(monthReport.getSalesarea());
			if(null!=salesArea){
				monthReport.setSalesareaname(salesArea.getName());
			}else{
				monthReport.setSalesareaname("其他");
			}
		}
		if(groupcols.indexOf("salesdept")!=-1){
			DepartMent departMent = getDepartmentByDeptid(monthReport.getSalesdept());
			if(null!=departMent){
				monthReport.setSalesdeptname(departMent.getName());
			}else{
				monthReport.setSalesdeptname("其他");
			}
		}
		if(groupcols.indexOf("salesuser")!=-1){
			Personnel personnel = getPersonnelById(monthReport.getSalesuser());
			if(null!=personnel){
				monthReport.setSalesusername(personnel.getName());
			}else{
				monthReport.setSalesusername("其他");
			}
			DepartMent departMent = getDepartmentByDeptid(monthReport.getSalesdept());
			if(null!=departMent){
				monthReport.setSalesdeptname(departMent.getName());
			}else{
				monthReport.setSalesdeptname("其他");
			}
			if(groupcols.indexOf("salesarea")==-1){
				SalesArea salesArea = getSalesareaByID(monthReport.getSalesarea());
				if(null!=salesArea){
					monthReport.setSalesareaname(salesArea.getName());
				}
			}
		}
		if(groupcols.indexOf("goodsid")!=-1){
			GoodsInfo goodsInfo = getAllGoodsInfoByID(monthReport.getGoodsid());
			if(null!=goodsInfo){
				monthReport.setGoodsname(goodsInfo.getName());
				WaresClass waresClass = getBaseGoodsMapper().getWaresClassInfo(monthReport.getGoodssort());
				if(null != waresClass){
					monthReport.setGoodssortname(waresClass.getThisname());
				}
				if(StringUtils.isNotEmpty(goodsInfo.getGoodstype())){
					monthReport.setGoodstype(goodsInfo.getGoodstype());
					SysCode goodstype = getBaseSysCodeMapper().getSysCodeInfo(goodsInfo.getGoodstype(), "goodstype");
					if(null != goodstype){
						monthReport.setGoodstypename(goodstype.getCodename());
					}
				}
				BuySupplier buySupplier = getSupplierInfoById(monthReport.getSupplierid());
				if(null != buySupplier){
					monthReport.setSuppliername(buySupplier.getName());
				}
			}else{
				Brand brand = getGoodsBrandByID(monthReport.getGoodsid());
				if(null!=brand){
					monthReport.setGoodsname("（折扣）"+brand.getName());
				}else{
					monthReport.setGoodsname("（折扣）其他");
				}
			}
			if(groupcols.indexOf("brandid")==-1){
				Brand brand = getGoodsBrandByID(monthReport.getBrandid());
				if(null!=brand){
					monthReport.setBrandname(brand.getName());
				}
				Personnel personnel = getPersonnelById(monthReport.getBranduser());
				if(null!=personnel){
					monthReport.setBrandusername(personnel.getName());
				}else{
					monthReport.setBrandusername("其他");
				}
				if("QC".equals(monthReport.getSupplieruser())){
					monthReport.setSupplierusername("期初");
				}else{
					Personnel personnel2 = getPersonnelById(monthReport.getSupplieruser());
					if(null!=personnel2){
						monthReport.setSupplierusername(personnel2.getName());
					}else{
						monthReport.setSupplierusername("其他");
					}
				}
			}
			if(groupcols.indexOf("branddept")==-1){
				DepartMent departMent = getDepartmentByDeptid(monthReport.getBranddept());
				if(null!=departMent){
					monthReport.setBranddeptname(departMent.getName());
				}else{
					monthReport.setBranddeptname("其他");
				}
			}
			if(groupcols.indexOf("branduser")==-1){
				Personnel personnel = getPersonnelById(monthReport.getBranduser());
				if(null!=personnel){
					monthReport.setBrandusername(personnel.getName());
				}else{
					monthReport.setBrandusername("其他");
				}
			}
		}
		if(groupcols.indexOf("goodssort")!=-1){
			WaresClass waresClass = getBaseGoodsMapper().getWaresClassInfo(monthReport.getGoodssort());
			if(null != waresClass){
				monthReport.setGoodssortname(waresClass.getThisname());
			}else{
				monthReport.setGoodssortname("其他");
			}
		}
		if(groupcols.indexOf("brandid")!=-1){
			Brand brand = getGoodsBrandByID(monthReport.getBrandid());
			if(null!=brand){
				monthReport.setBrandname(brand.getName());
			}else{
				monthReport.setBrandname("其他");
			}
			if(groupcols.indexOf("branddept")==-1){
				DepartMent departMent = getDepartmentByDeptid(monthReport.getBranddept());
				if(null!=departMent){
					monthReport.setBranddeptname(departMent.getName());
				}else{
					monthReport.setBranddeptname("其他");
				}
			}
			Personnel personnel = getPersonnelById(monthReport.getBranduser());
			if(null!=personnel){
				monthReport.setBrandusername(personnel.getName());
			}else{
				monthReport.setBrandusername("其他");
			}
			if("QC".equals(monthReport.getSupplieruser())){
				monthReport.setSupplierusername("期初");
			}else{
				Personnel personnel2 = getPersonnelById(monthReport.getSupplieruser());
				if(null!=personnel2){
					monthReport.setSupplierusername(personnel2.getName());
				}else{
					monthReport.setSupplierusername("其他");
				}
			}
		}
		if(groupcols.indexOf("branduser")!=-1){
			Personnel personnel = getPersonnelById(monthReport.getBranduser());
			if(null!=personnel){
				monthReport.setBrandusername(personnel.getName());
			}else{
				monthReport.setBrandusername("其他");
			}
			if(groupcols.indexOf("branddept")==-1){
				//分公司
				Map map2 = new HashMap();
				map2.put("pid", "");
				List<DepartMent> deptList = getBaseDepartMentMapper().getDeptListByParam(map2);
				for(DepartMent dept : deptList){
					if(monthReport.getBranddept().indexOf(dept.getId()) == 0){
						monthReport.setBranddeptname(dept.getName());
					}
				}
			}
		}
		if(groupcols.indexOf("branddept")!=-1){
			DepartMent departMent = getDepartmentByDeptid(monthReport.getBranddept());
			if(null!=departMent){
				monthReport.setBranddeptname(departMent.getName());
			}else{
				monthReport.setBranddeptname("其他");
			}
		}
		if(groupcols.indexOf("supplierid")!=-1){
			BuySupplier supplier = getSupplierInfoById(monthReport.getSupplierid());
			if(null != supplier){
				monthReport.setSuppliername(supplier.getName());
			}else{
				monthReport.setSuppliername("未指定供应商");
			}
		}
		if(groupcols.indexOf("supplieruser")!=-1){
			if("QC".equals(monthReport.getSupplieruser())){
				monthReport.setSupplierusername("期初");
			}else{
				Personnel personnel2 = getPersonnelById(monthReport.getSupplieruser());
				if(null!=personnel2){
					monthReport.setSupplierusername(personnel2.getName());
				}else{
					monthReport.setSupplierusername("其他");
				}
			}
		}
	}
	
	/**
	 * "合计" 所在位置
	 * @param baseSalesReport
	 * @param groupcols
	 * @author huangzhiqian
	 * @date 2016年1月13日
	 */
	private void getFieldHeji(SaleMonthReport baseSalesReport,String groupcols){
		String[] groupArr = groupcols.split(",");
		if(groupArr[0].indexOf("branddept")!=-1){
			baseSalesReport.setPcustomerid("");
			baseSalesReport.setPcustomername("");
			baseSalesReport.setBranddeptname("合计");
			baseSalesReport.setBranddept("");
			baseSalesReport.setBrandid("");
			baseSalesReport.setBrandname("");
			baseSalesReport.setSalesdept("");
			baseSalesReport.setSalesdeptname("");
			baseSalesReport.setSalesuser("");
			baseSalesReport.setSalesusername("");
			baseSalesReport.setGoodsid("");
			baseSalesReport.setGoodsname("");
			baseSalesReport.setGoodssortname("");
			baseSalesReport.setGoodstypename("");
			baseSalesReport.setBranduser("");
			baseSalesReport.setBrandusername("");
			baseSalesReport.setCustomerid("");
			baseSalesReport.setCustomername("");
			baseSalesReport.setCustomersort("");
			baseSalesReport.setCustomersortname("");
			baseSalesReport.setSupplierid("");
			baseSalesReport.setSuppliername("");
			baseSalesReport.setSupplieruser("");
			baseSalesReport.setSupplierusername("");
			baseSalesReport.setAddusername("");
		}else if(groupArr[0].indexOf("branduser")!=-1){
			baseSalesReport.setPcustomerid("");
			baseSalesReport.setPcustomername("");
			baseSalesReport.setBranduser("");
			baseSalesReport.setBrandusername("合计");
			baseSalesReport.setBranddept("");
			baseSalesReport.setBrandid("");
			baseSalesReport.setSalesdept("");
			baseSalesReport.setSalesuser("");
			baseSalesReport.setGoodsid("");
			baseSalesReport.setCustomerid("");
			baseSalesReport.setBrandname("");
			baseSalesReport.setSalesdeptname("");
			baseSalesReport.setSalesusername("");
			baseSalesReport.setBranddeptname("");
			baseSalesReport.setGoodsname("");
			baseSalesReport.setGoodssortname("");
			baseSalesReport.setGoodstypename("");
			baseSalesReport.setCustomername("");
			baseSalesReport.setCustomersort("");
			baseSalesReport.setCustomersortname("");
			baseSalesReport.setSupplierid("");
			baseSalesReport.setSuppliername("");
			baseSalesReport.setSupplieruser("");
			baseSalesReport.setSupplierusername("");
			baseSalesReport.setAddusername("");
		}else if(groupArr[0].indexOf("brandid")!=-1){
			baseSalesReport.setPcustomerid("");
			baseSalesReport.setPcustomername("");
			baseSalesReport.setBrandname("合计");
			baseSalesReport.setBranddept("");
			baseSalesReport.setBrandid("");
			baseSalesReport.setSalesdept("");
			baseSalesReport.setSalesuser("");
			baseSalesReport.setBranduser("");
			baseSalesReport.setGoodsid("");
			baseSalesReport.setCustomerid("");
			baseSalesReport.setBranddeptname("");
			baseSalesReport.setSalesdeptname("");
			baseSalesReport.setSalesusername("");
			baseSalesReport.setBrandusername("");
			baseSalesReport.setGoodsname("");
			baseSalesReport.setGoodssortname("");
			baseSalesReport.setGoodstypename("");
			baseSalesReport.setCustomername("");
			baseSalesReport.setCustomersort("");
			baseSalesReport.setCustomersortname("");
			baseSalesReport.setSupplierid("");
			baseSalesReport.setSuppliername("");
			baseSalesReport.setSupplieruser("");
			baseSalesReport.setSupplierusername("");
			baseSalesReport.setAddusername("");
		}else if(groupArr[0].indexOf("salesdept")!=-1){
			baseSalesReport.setPcustomerid("");
			baseSalesReport.setPcustomername("");
			baseSalesReport.setSalesdeptname("合计");
			baseSalesReport.setBranddept("");
			baseSalesReport.setBrandid("");
			baseSalesReport.setSalesdept("");
			baseSalesReport.setSalesuser("");
			baseSalesReport.setBranduser("");
			baseSalesReport.setGoodsid("");
			baseSalesReport.setCustomerid("");
			baseSalesReport.setBrandname("");
			baseSalesReport.setBranddeptname("");
			baseSalesReport.setSalesusername("");
			baseSalesReport.setBrandusername("");
			baseSalesReport.setGoodsname("");
			baseSalesReport.setGoodssortname("");
			baseSalesReport.setGoodstypename("");
			baseSalesReport.setCustomername("");
			baseSalesReport.setCustomersort("");
			baseSalesReport.setCustomersortname("");
			baseSalesReport.setSupplierid("");
			baseSalesReport.setSuppliername("");
			baseSalesReport.setSupplieruser("");
			baseSalesReport.setSupplierusername("");
			baseSalesReport.setAddusername("");
		}else if(groupArr[0].indexOf("salesuser")!=-1){
			baseSalesReport.setPcustomerid("");
			baseSalesReport.setPcustomername("");
			baseSalesReport.setSalesusername("合计");
			baseSalesReport.setBranddept("");
			baseSalesReport.setBrandid("");
			baseSalesReport.setSalesdept("");
			baseSalesReport.setSalesuser("");
			baseSalesReport.setBranduser("");
			baseSalesReport.setGoodsid("");
			baseSalesReport.setCustomerid("");
			baseSalesReport.setBrandname("");
			baseSalesReport.setSalesdeptname("");
			baseSalesReport.setBranddeptname("");
			baseSalesReport.setBrandusername("");
			baseSalesReport.setGoodsname("");
			baseSalesReport.setGoodssortname("");
			baseSalesReport.setGoodstypename("");
			baseSalesReport.setCustomername("");
			baseSalesReport.setCustomersort("");
			baseSalesReport.setCustomersortname("");
			baseSalesReport.setSupplierid("");
			baseSalesReport.setSuppliername("");
			baseSalesReport.setSupplieruser("");
			baseSalesReport.setSupplierusername("");
			baseSalesReport.setAddusername("");
		}else if(groupArr[0].indexOf("goodsid")!=-1){
			baseSalesReport.setPcustomerid("");
			baseSalesReport.setPcustomername("");
			baseSalesReport.setGoodsname("合计");
			baseSalesReport.setBranddept("");
			baseSalesReport.setBrandid("");
			baseSalesReport.setSalesdept("");
			baseSalesReport.setSalesuser("");
			baseSalesReport.setBranduser("");
			baseSalesReport.setGoodsid("");
			baseSalesReport.setGoodssort("");
			baseSalesReport.setGoodssortname("");
			baseSalesReport.setGoodstypename("");
			baseSalesReport.setCustomerid("");
			baseSalesReport.setBrandname("");
			baseSalesReport.setSalesdeptname("");
			baseSalesReport.setSalesusername("");
			baseSalesReport.setBrandusername("");
			baseSalesReport.setBranddeptname("");
			baseSalesReport.setCustomername("");
			baseSalesReport.setCustomersort("");
			baseSalesReport.setCustomersortname("");
			baseSalesReport.setSupplierid("");
			baseSalesReport.setSuppliername("");
			baseSalesReport.setSupplieruser("");
			baseSalesReport.setSupplierusername("");
			baseSalesReport.setAddusername("");
		}else if(groupArr[0].indexOf("goodssort")!=-1){
			baseSalesReport.setPcustomerid("");
			baseSalesReport.setPcustomername("");
			baseSalesReport.setGoodsname("");
			baseSalesReport.setBranddept("");
			baseSalesReport.setBrandid("");
			baseSalesReport.setSalesdept("");
			baseSalesReport.setSalesuser("");
			baseSalesReport.setBranduser("");
			baseSalesReport.setGoodsid("");
			baseSalesReport.setGoodssort("");
			baseSalesReport.setGoodssortname("合计");
			baseSalesReport.setGoodstypename("");
			baseSalesReport.setCustomerid("");
			baseSalesReport.setBrandname("");
			baseSalesReport.setSalesdeptname("");
			baseSalesReport.setSalesusername("");
			baseSalesReport.setBrandusername("");
			baseSalesReport.setBranddeptname("");
			baseSalesReport.setCustomername("");
			baseSalesReport.setCustomersort("");
			baseSalesReport.setCustomersortname("");
			baseSalesReport.setSupplierid("");
			baseSalesReport.setSuppliername("");
			baseSalesReport.setSupplieruser("");
			baseSalesReport.setSupplierusername("");
			baseSalesReport.setAddusername("");
		}else if(groupArr[0].indexOf("pcustomerid")!=-1){
			baseSalesReport.setPcustomerid("");
			baseSalesReport.setPcustomername("合计");
			baseSalesReport.setCustomerid("");
			baseSalesReport.setCustomername("");
			baseSalesReport.setBranddept("");
			baseSalesReport.setBrandid("");
			baseSalesReport.setSalesdept("");
			baseSalesReport.setSalesuser("");
			baseSalesReport.setBranduser("");
			baseSalesReport.setGoodsid("");
			baseSalesReport.setBrandname("");
			baseSalesReport.setSalesdeptname("");
			baseSalesReport.setSalesusername("");
			baseSalesReport.setBrandusername("");
			baseSalesReport.setGoodsname("");
			baseSalesReport.setGoodssortname("");
			baseSalesReport.setGoodstypename("");
			baseSalesReport.setBranddeptname("");
			baseSalesReport.setCustomersort("");
			baseSalesReport.setCustomersortname("");
			baseSalesReport.setSupplierid("");
			baseSalesReport.setSuppliername("");
			baseSalesReport.setSupplieruser("");
			baseSalesReport.setSupplierusername("");
			baseSalesReport.setAddusername("");
		}else if(groupArr[0].indexOf("customerid")!=-1){
			baseSalesReport.setPcustomerid("");
			baseSalesReport.setPcustomername("");
			baseSalesReport.setCustomername("合计");
			baseSalesReport.setBranddept("");
			baseSalesReport.setBrandid("");
			baseSalesReport.setSalesdept("");
			baseSalesReport.setSalesuser("");
			baseSalesReport.setBranduser("");
			baseSalesReport.setGoodsid("");
			baseSalesReport.setCustomerid("");
			baseSalesReport.setBrandname("");
			baseSalesReport.setSalesdeptname("");
			baseSalesReport.setSalesusername("");
			baseSalesReport.setBrandusername("");
			baseSalesReport.setGoodsname("");
			baseSalesReport.setGoodssortname("");
			baseSalesReport.setGoodstypename("");
			baseSalesReport.setBranddeptname("");
			baseSalesReport.setCustomersort("");
			baseSalesReport.setCustomersortname("");
			baseSalesReport.setSupplierid("");
			baseSalesReport.setSuppliername("");
			baseSalesReport.setSupplieruser("");
			baseSalesReport.setSupplierusername("");
			baseSalesReport.setAddusername("");
		}else if(groupArr[0].indexOf("customersort")!=-1){
			baseSalesReport.setPcustomerid("");
			baseSalesReport.setPcustomername("");
			baseSalesReport.setBranddeptname("");
			baseSalesReport.setBranddept("");
			baseSalesReport.setBrandid("");
			baseSalesReport.setBrandname("");
			baseSalesReport.setSalesdept("");
			baseSalesReport.setSalesdeptname("");
			baseSalesReport.setSalesuser("");
			baseSalesReport.setSalesusername("");
			baseSalesReport.setGoodsid("");
			baseSalesReport.setGoodsname("");
			baseSalesReport.setGoodssortname("");
			baseSalesReport.setGoodstypename("");
			baseSalesReport.setBranduser("");
			baseSalesReport.setBrandusername("");
			baseSalesReport.setCustomerid("");
			baseSalesReport.setCustomername("");
			baseSalesReport.setCustomersort("");
			baseSalesReport.setCustomersortname("合计");
			baseSalesReport.setSupplierid("");
			baseSalesReport.setSuppliername("");
			baseSalesReport.setSupplieruser("");
			baseSalesReport.setSupplierusername("");
			baseSalesReport.setAddusername("");
		}else if(groupArr[0].indexOf("supplierid")!=-1){
			baseSalesReport.setPcustomerid("");
			baseSalesReport.setPcustomername("");
			baseSalesReport.setBranddeptname("");
			baseSalesReport.setBranddept("");
			baseSalesReport.setBrandid("");
			baseSalesReport.setBrandname("");
			baseSalesReport.setSalesdept("");
			baseSalesReport.setSalesdeptname("");
			baseSalesReport.setSalesuser("");
			baseSalesReport.setSalesusername("");
			baseSalesReport.setGoodsid("");
			baseSalesReport.setGoodsname("");
			baseSalesReport.setGoodssortname("");
			baseSalesReport.setGoodstypename("");
			baseSalesReport.setBranduser("");
			baseSalesReport.setBrandusername("");
			baseSalesReport.setCustomerid("");
			baseSalesReport.setCustomername("");
			baseSalesReport.setCustomersort("");
			baseSalesReport.setCustomersortname("");
			baseSalesReport.setSupplierid("");
			baseSalesReport.setSuppliername("合计");
			baseSalesReport.setSupplieruser("");
			baseSalesReport.setSupplierusername("");
			baseSalesReport.setAddusername("");
		}else if(groupArr[0].indexOf("supplieruser")!=-1){
			baseSalesReport.setPcustomerid("");
			baseSalesReport.setPcustomername("");
			baseSalesReport.setBranddeptname("");
			baseSalesReport.setBranddept("");
			baseSalesReport.setBrandid("");
			baseSalesReport.setBrandname("");
			baseSalesReport.setSalesdept("");
			baseSalesReport.setSalesdeptname("");
			baseSalesReport.setSalesuser("");
			baseSalesReport.setSalesusername("");
			baseSalesReport.setGoodsid("");
			baseSalesReport.setGoodsname("");
			baseSalesReport.setGoodssortname("");
			baseSalesReport.setGoodstypename("");
			baseSalesReport.setBranduser("");
			baseSalesReport.setBrandusername("");
			baseSalesReport.setCustomerid("");
			baseSalesReport.setCustomername("");
			baseSalesReport.setCustomersort("");
			baseSalesReport.setCustomersortname("");
			baseSalesReport.setSupplierid("");
			baseSalesReport.setSuppliername("");
			baseSalesReport.setSupplieruser("");
			baseSalesReport.setSupplierusername("合计");
			baseSalesReport.setAddusername("");
		}else if(groupArr[0].indexOf("adduserid")!=-1){
			baseSalesReport.setPcustomerid("");
			baseSalesReport.setPcustomername("");
			baseSalesReport.setBranddeptname("");
			baseSalesReport.setBranddept("");
			baseSalesReport.setBrandid("");
			baseSalesReport.setBrandname("");
			baseSalesReport.setSalesdept("");
			baseSalesReport.setSalesdeptname("");
			baseSalesReport.setSalesuser("");
			baseSalesReport.setSalesusername("");
			baseSalesReport.setGoodsid("");
			baseSalesReport.setGoodsname("");
			baseSalesReport.setGoodssortname("");
			baseSalesReport.setGoodstypename("");
			baseSalesReport.setBranduser("");
			baseSalesReport.setBrandusername("");
			baseSalesReport.setCustomerid("");
			baseSalesReport.setCustomername("");
			baseSalesReport.setCustomersort("");
			baseSalesReport.setCustomersortname("");
			baseSalesReport.setSupplierid("");
			baseSalesReport.setSuppliername("");
			baseSalesReport.setSupplieruser("");
			baseSalesReport.setSupplierusername("");
			baseSalesReport.setAddusername("合计");
		}
	}

	@Override
	public PageData getSalesRejectReportListData(PageMap pageMap) throws Exception {

		Map condition = pageMap.getCondition();
		String groupcols = (String) condition.get("groupcols");
		String[] groupcolArr = groupcols.split(",");
		Arrays.sort(groupcolArr);

		if(org.apache.commons.lang3.StringUtils.isEmpty(groupcols)) {
			condition.put("groupcols", "goodsid");
		}

		String dataSql = getDataAccessRule("t_storage_salereject_enter", "t1");
		pageMap.setDataSql(dataSql);

		// xxxFlag。xxxFlg如果为false，则将xxx字段赋值为空
		boolean customeridFlag = false;
		boolean customernameFlag = false;
		boolean pcustomeridFlag = false;
		boolean pcustomernameFlag = false;
		boolean salesdeptFlag = false;
		boolean salesuserFlag = false;
		boolean branduserFlag = false;
		boolean customersortFlag = false;
		boolean driveridFlag = false;
		boolean storageidFlag = false;
		boolean goodsidFlag = false;
		boolean goodsnameFlag = false;
		boolean barcodeFlag = false;
		boolean brandidFlag = false;
		boolean goodssortFlag = false;
		boolean salesareaFlag = false;
		boolean supplieridFlag = false;
		boolean sourcetypeFlag = false;
		boolean rejectcategoryFlag = false;
		boolean storagerFlag = false;

		if(Arrays.binarySearch(groupcolArr, "customerid") >= 0) {
			customeridFlag = true;
			customernameFlag = true;
		}
		if(Arrays.binarySearch(groupcolArr, "pcustomerid") >= 0) {
			pcustomeridFlag = true;
			pcustomernameFlag = true;
		}
		if(Arrays.binarySearch(groupcolArr, "salesdept") >= 0) {
			salesdeptFlag = true;
		}
		if(Arrays.binarySearch(groupcolArr, "salesuser") >= 0) {
			salesuserFlag = true;
		}
		if(Arrays.binarySearch(groupcolArr, "branduser") >= 0) {
			branduserFlag = true;
		}
		if(Arrays.binarySearch(groupcolArr, "customersort") >= 0) {
			customersortFlag = true;
		}
		if(Arrays.binarySearch(groupcolArr, "driverid") >= 0) {
			driveridFlag = true;
		}
		if(Arrays.binarySearch(groupcolArr, "storageid") >= 0) {
			storageidFlag = true;
		}
		if(Arrays.binarySearch(groupcolArr, "goodsid") >= 0) {
			goodsidFlag = true;
			goodsnameFlag = true;
		}
		if(Arrays.binarySearch(groupcolArr, "goodsid") >= 0) {
			goodsidFlag = true;
		}
		if(Arrays.binarySearch(groupcolArr, "barcode") >= 0) {
			barcodeFlag = true;
		}
		if(Arrays.binarySearch(groupcolArr, "brandid") >= 0) {
			brandidFlag = true;
		}
		if(Arrays.binarySearch(groupcolArr, "goodssort") >= 0) {
			goodssortFlag = true;
		}
		if(Arrays.binarySearch(groupcolArr, "salesarea") >= 0) {
			salesareaFlag = true;
		}
		if(Arrays.binarySearch(groupcolArr, "supplierid") >= 0) {
			supplieridFlag = true;
		}
		if(Arrays.binarySearch(groupcolArr, "sourcetype") >= 0) {
			sourcetypeFlag = true;
		}
		if(Arrays.binarySearch(groupcolArr, "rejectcategory") >= 0) {
			rejectcategoryFlag = true;
		}
		if(Arrays.binarySearch(groupcolArr, "storager") >= 0) {
			storagerFlag = true;
		}

		List<Map> list = salesRejectMapper.getSalesRejectReportList(pageMap);
		for(Map map : list) {

			if(!customeridFlag) {
				map.remove("customerid");
				map.remove("customername");
			}
			if(!pcustomeridFlag) {
				map.remove("pcustomerid");
				map.remove("pcustomername");
			}
			if(!salesdeptFlag) {
				map.remove("salesdept");
				map.remove("salesdeptname");
			}
			if(!branduserFlag) {
				map.remove("branduser");
				map.remove("brandusername");
			}
			if(!salesuserFlag) {
				map.remove("salesuser");
				map.remove("salesusername");
			}
			if(!branduserFlag) {
				map.remove("branduser");
				map.remove("brandusername");
			}
			if(!customersortFlag) {
				map.remove("customersort");
				map.remove("customersortname");
			}
			if(!driveridFlag) {
				map.remove("driverid");
				map.remove("driveridname");
			}
			if(!storageidFlag) {
				map.remove("storageid");
				map.remove("storagename");
			}
			if(!goodsidFlag) {
				map.remove("goodsid");
				map.remove("goodsname");
			}
			if(!brandidFlag) {
				map.remove("brandid");
				map.remove("brandname");
			}
			if(!goodssortFlag) {
				map.remove("goodssort");
				map.remove("goodssortname");
			}
			if(!salesareaFlag) {
				map.remove("salesarea");
				map.remove("salesareaname");
			}
			if(!supplieridFlag) {
				map.remove("supplierid");
				map.remove("suppliername");
			}
			if(!sourcetypeFlag) {
				map.remove("sourcetype");
			}
			if(!rejectcategoryFlag) {
				map.remove("rejectcategory");
			}
//			if(!storagerFlag) {
//				map.remove("storager");
//				map.remove("storagername");
//			}
			String customerid = (String) map.get("customerid");
			map.put("customername", "其他未指定");
			if(StringUtils.isNotEmpty(customerid)) {

				Customer customer = getCustomerByID(customerid);
				if (customer != null) {
					map.put("customername", customer.getName());
				}
			}

			String pcustomerid = (String) map.get("pcustomerid");
			map.put("pcustomername", "其他未指定");
			if(StringUtils.isNotEmpty(pcustomerid)) {

				Customer customer = getCustomerByID(pcustomerid);
				if (customer != null) {
					map.put("pcustomername", customer.getName());
				}
			}

			String salesuser = (String) map.get("salesuser");
			map.put("salesusername", "其他未指定");
			if(StringUtils.isNotEmpty(salesuser)) {

				Personnel person = getPersonnelById(salesuser);
				if (person != null) {
					map.put("salesusername", person.getName());
				}
			}

			String customersort = (String) map.get("customersort");
			map.put("customersortname", "其他未指定");
			if(StringUtils.isNotEmpty(customersort)) {

				CustomerSort customerSort = getCustomerSortByID(customersort);
				if (customerSort != null) {
					map.put("customersortname", customerSort.getName());
				}
			}

			String goodsid = (String) map.get("goodsid");
			map.put("goodsname", "其他未指定");
			if(StringUtils.isNotEmpty(goodsid)) {

				GoodsInfo goods = getGoodsInfoByID(goodsid);
				if (goods != null) {
					map.put("goodsname", goods.getName());
					map.put("barcode", goods.getBarcode());
				}
			}

			String brandid = (String) map.get("brandid");
			map.put("brandname", "其他未指定");
			if(StringUtils.isNotEmpty(brandid)) {

				Brand brand = getGoodsBrandByID(brandid);
				if (brand != null) {
					map.put("brandname", brand.getName());
				}
			}

			String goodssort = (String) map.get("goodssort");
			map.put("goodssortname", "其他未指定");
			if(StringUtils.isNotEmpty(goodssort)) {

				WaresClass waresClass = getBaseGoodsMapper().getWaresClassInfo(goodssort);
				if (waresClass != null) {
					map.put("goodssortname", waresClass.getName());
				}
			}

			String salesarea = (String) map.get("salesarea");
			map.put("salesareaname", "其他未指定");
			if(StringUtils.isNotEmpty(salesarea)) {

				SalesArea salesArea = getSalesareaByID(salesarea);
				if (salesArea != null) {
					map.put("salesareaname", salesArea.getName());
				}
			}

			String supplierid = (String) map.get("supplierid");
			map.put("suppliername", "其他未指定");
			if(StringUtils.isNotEmpty(supplierid)) {

				BuySupplier supplier = getSupplierInfoById(supplierid);
				if (supplier != null) {
					map.put("suppliername", supplier.getName());
				}
			}

			String storageid = (String) map.get("storageid");
			map.put("storagename", "其他未指定");
			if(StringUtils.isNotEmpty(storageid)) {

				StorageInfo storage = getStorageInfoByID(storageid);
				if (storage != null) {
					map.put("storagename", storage.getName());
				}
			}

			String driverid = (String) map.get("driverid");
			map.put("drivername", "其他未指定");
			if(StringUtils.isNotEmpty(driverid)) {

				Personnel driver = getPersonnelById(driverid);
				if (driver != null) {
					map.put("drivername", driver.getName());
				}
			}

			String salesdept = (String) map.get("salesdept");
			map.put("salesdeptname", "其他未指定");
			if(StringUtils.isNotEmpty(salesdept)) {

				DepartMent dept = getDepartMentById(salesdept);
				if (dept != null) {
					map.put("salesdeptname", dept.getName());
				}
			}

			String branduser = (String) map.get("branduser");
			map.put("brandusername", "其他未指定");
			if(StringUtils.isNotEmpty(branduser)) {

				Personnel brandUser = getPersonnelById(branduser);
				if (brandUser != null) {
					map.put("brandusername", brandUser.getName());
				}
			}

			String storager = (String) map.get("storager");
			map.put("storagername", "其他未指定");
			if(org.apache.commons.lang3.StringUtils.isNotEmpty(storager)) {

				Personnel storager1 = getPersonnelById(storager);
				if (storager1 != null) {
					map.put("storagername", storager1.getName());
				}
			}
		}

		int count = salesRejectMapper.getSalesRejectReportListCount(pageMap);

		Map footer = salesRejectMapper.getSalesRejectReportListSum(pageMap);
		List footerList = new ArrayList();
		footerList.add(footer);

//			boolean customeridFlag = false;
//			boolean customernameFlag = false;
//			boolean pcustomeridFlag = false;
//			boolean pcustomernameFlag = false;
//			boolean salesdeptFlag = false;
//			boolean salesuserFlag = false;
//			boolean branduserFlag = false;
//			boolean customersortFlag = false;
//			boolean driveridFlag = false;
//			boolean storageidFlag = false;
//			boolean goodsidFlag = false;
//			boolean goodsnameFlag = false;
//			boolean barcodeFlag = false;
//			boolean brandidFlag = false;
//			boolean goodssortFlag = false;
//			boolean salesareaFlag = false;
//			boolean supplieridFlag = false;
//			boolean sourcetypeFlag = false;
//			boolean rejectcategoryFlag = false;

		if (customeridFlag) {
			footer.put("customername", "合计");
		} else if (pcustomeridFlag) {
			footer.put("pcustomername", "合计");
		} else if (salesdeptFlag) {
			footer.put("salesdeptname", "合计");
		} else if (salesdeptFlag) {
			footer.put("salesusername", "合计");
		} else if (branduserFlag) {
			footer.put("brandusername", "合计");
		} else if (customersortFlag) {
			footer.put("customersortname", "合计");
		} else if (driveridFlag) {
			footer.put("drivername", "合计");
		} else if (storageidFlag) {
			footer.put("storagename", "合计");
		} else if (goodsidFlag) {
			footer.put("goodsname", "合计");
		} else if (brandidFlag) {
			footer.put("brandname", "合计");
		} else if (goodssortFlag) {
			footer.put("goodssortname", "合计");
		} else if (salesareaFlag) {
			footer.put("salesareaname", "合计");
		} else if (supplieridFlag) {
			footer.put("suppliername", "合计");
		} else if (sourcetypeFlag) {
			footer.put("sourcetype", "合计");
		} else if (rejectcategoryFlag) {
			footer.put("rejectcategory", "合计");
		}else if (storagerFlag) {
			footer.put("storagername", "合计");
		}
		PageData data = new PageData(count, list, pageMap);
		data.setFooter(footerList);
		return data;
	}

    @Override
    public PageData getSalesPresentReportData(PageMap pageMap) throws Exception {

        //小计列
        String groupcols = (String) pageMap.getCondition().get("groupcols");
        if(!pageMap.getCondition().containsKey("groupcols")){
            groupcols = "customerid";
            pageMap.getCondition().put("groupcols", groupcols);
        }
        if(groupcols.equals("adduserid")){
            String dataSql = getDataAccessRule("t_report_adduser_base", "z");
            pageMap.setDataSql(dataSql);
        }else{
            String dataSql = getDataAccessRule("t_report_sales_base", "z");
            pageMap.setDataSql(dataSql);
        }

        //排序
        String orderstr = "";
        if(pageMap.getCondition().containsKey("sort")){
            String sort = (String) pageMap.getCondition().get("sort");
            String order = (String) pageMap.getCondition().get("order");
            if(null==order){
                order = "asc";
            }
            orderstr = sort +" " +order;
        }
        pageMap.getCondition().put("orderstr", orderstr);

        List<BaseSalesReport> list = salesCustomerReportMapper.getSalesPresentReportData(pageMap);
        for(BaseSalesReport baseSalesReport : list){
            if(null == baseSalesReport){
                continue;
            }
            BigDecimal sendnum = null != baseSalesReport.getSendnum() ? baseSalesReport.getSendnum() : BigDecimal.ZERO;
            BigDecimal returnnum = null != baseSalesReport.getReturnnum() ? baseSalesReport.getReturnnum() : BigDecimal.ZERO;
            BigDecimal sendtotalbox = null != baseSalesReport.getSendtotalbox() ? baseSalesReport.getSendtotalbox() : BigDecimal.ZERO;
            BigDecimal returntotalbox = null != baseSalesReport.getReturntotalbox() ? baseSalesReport.getReturntotalbox() : BigDecimal.ZERO;
            BigDecimal sendamount = null != baseSalesReport.getSendamount() ? baseSalesReport.getSendamount() : BigDecimal.ZERO;
            BigDecimal returnamount = null != baseSalesReport.getReturnamount() ? baseSalesReport.getReturnamount() : BigDecimal.ZERO;
            BigDecimal sendnotaxamount = null != baseSalesReport.getSendnotaxamount() ? baseSalesReport.getSendnotaxamount() : BigDecimal.ZERO;
            BigDecimal returnnotaxamount = null != baseSalesReport.getReturnnotaxamount() ? baseSalesReport.getReturnnotaxamount() : BigDecimal.ZERO;
            BigDecimal costamount = null != baseSalesReport.getCostamount() ? baseSalesReport.getCostamount() : BigDecimal.ZERO;

            //销售数量 = 发货单数量 - 直退数量
            baseSalesReport.setSalenum(sendnum.subtract(returnnum));
            //销售箱数
            baseSalesReport.setSaletotalbox(sendtotalbox.subtract(returntotalbox));
            baseSalesReport.setSaleamount(sendamount.subtract(returnamount));
            baseSalesReport.setSalenotaxamount(sendnotaxamount.subtract(returnnotaxamount));
            if(null != baseSalesReport.getSaleamount()){
                //毛利额 = 销售金额 - 成本金额
                baseSalesReport.setSalemarginamount(baseSalesReport.getSaleamount().subtract(costamount));
                //实际毛利率 = （销售金额 - 成本金额）/销售金额*100
                if(baseSalesReport.getSaleamount().compareTo(BigDecimal.ZERO)!=0){
                    BigDecimal realrate = baseSalesReport.getSaleamount().subtract(costamount).divide(baseSalesReport.getSaleamount(), 6, BigDecimal.ROUND_HALF_UP);
                    realrate = realrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
                    if(baseSalesReport.getSaleamount().compareTo(BigDecimal.ZERO)==-1){
                        baseSalesReport.setRealrate(realrate.negate());
                    }else{
                        baseSalesReport.setRealrate(realrate);
                    }
                }
            }else if(costamount.compareTo(BigDecimal.ZERO)==1){
                baseSalesReport.setRealrate(new BigDecimal(100).negate());
            }
            baseSalesReport.setId(baseSalesReport.getCustomerid());
            //销售税额
            if(null != baseSalesReport.getSalenotaxamount() && null != baseSalesReport.getSaleamount()){
                baseSalesReport.setSaletax(baseSalesReport.getSaleamount().subtract(baseSalesReport.getSalenotaxamount()));
            }

            //根据获取的商品编码获取条形码
            if(StringUtils.isNotEmpty(baseSalesReport.getGoodsid())){
                GoodsInfo goodsInfo = getGoodsInfoByID(baseSalesReport.getGoodsid());
                if(null != goodsInfo){
                    baseSalesReport.setBarcode(goodsInfo.getBarcode());
                    baseSalesReport.setGoodsname(goodsInfo.getName());
                    baseSalesReport.setSpell(goodsInfo.getSpell());
                }
            }
            //判断是否为导出使用，若为导出，则将直退数量、售后退货数量显示为负数
            if(pageMap.getCondition().containsKey("exportflag") && null != pageMap.getCondition().get("exportflag")){
                //直退数量
                baseSalesReport.setDirectreturnnum(baseSalesReport.getDirectreturnnum().negate());
                //直退箱数
                baseSalesReport.setDirectreturntotalbox(baseSalesReport.getDirectreturntotalbox().negate());
                //直退金额
                baseSalesReport.setDirectreturnamount(baseSalesReport.getDirectreturnamount().negate());
                //售后退货数量
                baseSalesReport.setCheckreturnnum(baseSalesReport.getCheckreturnnum().negate());
                //售后退货箱数
                baseSalesReport.setCheckreturntotalbox(baseSalesReport.getCheckreturntotalbox().negate());
                //退货金额
                baseSalesReport.setCheckreturnamount(baseSalesReport.getCheckreturnamount().negate());
                //退货总数量
                baseSalesReport.setReturnnum(baseSalesReport.getReturnnum().negate());
                //退货总箱数
                baseSalesReport.setReturntotalbox(baseSalesReport.getReturntotalbox().negate());
                //退货合计
                baseSalesReport.setReturnamount(baseSalesReport.getReturnamount().negate());
            }
            if(groupcols.indexOf("customerid")!=-1){
                if(StringUtils.isNotEmpty(baseSalesReport.getCustomerid())){
                    Customer customer = getCustomerByID(baseSalesReport.getCustomerid());
                    if(null!=customer){
                        baseSalesReport.setCustomername(customer.getName());
                        baseSalesReport.setShortcode(customer.getShortcode());
                    }else{
                        baseSalesReport.setCustomername("其他未定义");
                    }
                }else{
                    baseSalesReport.setCustomerid("nodata");
                    baseSalesReport.setCustomername("其他未指定");
                }
                if(groupcols.indexOf("pcustomerid")==-1){
                    Customer pcustomer = getCustomerByID(baseSalesReport.getPcustomerid());
                    if(null!=pcustomer ){
                        baseSalesReport.setPcustomername(pcustomer.getName());
                    }
                }
                if(groupcols.indexOf("salesdept")==-1){
                    DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getSalesdept());
                    if(null!=departMent){
                        baseSalesReport.setSalesdeptname(departMent.getName());
                    }
                }
                if(groupcols.indexOf("customersort")==-1){
                    CustomerSort customerSort = getCustomerSortByID(baseSalesReport.getCustomersort());
                    if(null!=customerSort){
                        baseSalesReport.setCustomersortname(customerSort.getName());
                    }
                }
                if(groupcols.indexOf("salesarea")==-1){
                    SalesArea salesArea = getSalesareaByID(baseSalesReport.getSalesarea());
                    if(null!=salesArea){
                        baseSalesReport.setSalesareaname(salesArea.getThisname());
                    }
                }
                if(groupcols.indexOf("salesuser")==-1){
                    Personnel personnel = getPersonnelById(baseSalesReport.getSalesuser());
                    if(null!=personnel){
                        baseSalesReport.setSalesusername(personnel.getName());
                    }
                    DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getSalesdept());
                    if(null!=departMent){
                        baseSalesReport.setSalesdeptname(departMent.getName());
                    }
                }
            }else{
                baseSalesReport.setCustomerid("");
                baseSalesReport.setCustomername("");
                if(groupcols.indexOf("salesuser")==-1 && groupcols.indexOf("salesdept")==-1){
                    baseSalesReport.setSalesdeptname("");
                }
            }
            if(groupcols.indexOf("pcustomerid")!=-1){
                if(StringUtils.isNotEmpty(baseSalesReport.getPcustomerid())){
                    Customer pcustomer = getCustomerByID(baseSalesReport.getPcustomerid());
                    if(null!=pcustomer ){
                        baseSalesReport.setPcustomername(pcustomer.getName());
                    }else{
                        baseSalesReport.setPcustomername("其他未定义");
                    }
                }else{
                    baseSalesReport.setPcustomerid("nodata");
                    baseSalesReport.setPcustomername("其他未指定");
                }
                if(groupcols.indexOf("customerid")==1){
                    baseSalesReport.setCustomerid("");
                    baseSalesReport.setCustomername("");
                }
            }
            if(groupcols.indexOf("customersort")!=-1){
                if(StringUtils.isNotEmpty(baseSalesReport.getCustomersort())){
                    CustomerSort customerSort = getCustomerSortByID(baseSalesReport.getCustomersort());
                    if(null!=customerSort){
                        baseSalesReport.setCustomersortname(customerSort.getName());
                    }else{
                        baseSalesReport.setCustomersortname("其他未定义");
                    }
                }else{
                    baseSalesReport.setCustomersort("nodata");
                    baseSalesReport.setCustomersortname("其他未指定");
                }
            }
            if(groupcols.indexOf("salesarea")!=-1){
                if(StringUtils.isNotEmpty(baseSalesReport.getSalesarea())){
                    SalesArea salesArea = getSalesareaByID(baseSalesReport.getSalesarea());
                    if(null!=salesArea){
                        baseSalesReport.setSalesareaname(salesArea.getThisname());
                    }else{
                        baseSalesReport.setSalesareaname("其他未定义");
                    }
                }else{
                    baseSalesReport.setSalesarea("nodata");
                    baseSalesReport.setSalesareaname("其他未指定");
                }
            }
            if(groupcols.indexOf("salesdept")!=-1){
                if(StringUtils.isNotEmpty(baseSalesReport.getSalesdept())){
                    DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getSalesdept());
                    if(null!=departMent){
                        baseSalesReport.setSalesdeptname(departMent.getName());
                    }else{
                        baseSalesReport.setSalesdeptname("其他未定义");
                    }
                }else{
                    baseSalesReport.setSalesdept("nodata");
                    baseSalesReport.setSalesdeptname("其他未指定");
                }
            }
            if(groupcols.indexOf("salesuser")!=-1){
                if(StringUtils.isNotEmpty(baseSalesReport.getSalesuser())){
                    Personnel personnel = getPersonnelById(baseSalesReport.getSalesuser());
                    if(null!=personnel){
                        baseSalesReport.setSalesusername(personnel.getName());
                    }else{
                        baseSalesReport.setSalesusername("其他未定义");
                    }
                }else{
                    baseSalesReport.setSalesuser("nodata");
                    baseSalesReport.setSalesusername("其他未指定");
                }
                DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getSalesdept());
                if(null!=departMent){
                    baseSalesReport.setSalesdeptname(departMent.getName());
                }
                if(groupcols.indexOf("salesarea")==-1){
                    SalesArea salesArea = getSalesareaByID(baseSalesReport.getSalesarea());
                    if(null!=salesArea){
                        baseSalesReport.setSalesareaname(salesArea.getThisname());
                    }
                }
            }
            if(groupcols.indexOf("goodsid")!=-1){
                GoodsInfo goodsInfo = getAllGoodsInfoByID(baseSalesReport.getGoodsid());
                if(null!=goodsInfo){
                    baseSalesReport.setGoodsname(goodsInfo.getName());
                    baseSalesReport.setSpell(goodsInfo.getSpell());
                    WaresClass waresClass = getBaseGoodsMapper().getWaresClassInfo(baseSalesReport.getGoodssort());
                    if(null != waresClass){
                        baseSalesReport.setGoodssortname(waresClass.getThisname());
                    }
                    BuySupplier buySupplier = getSupplierInfoById(baseSalesReport.getSupplierid());
                    if(null != buySupplier){
                        baseSalesReport.setSuppliername(buySupplier.getName());
                    }
                }else{
                    Brand brand = getGoodsBrandByID(baseSalesReport.getGoodsid());
                    if(null!=brand){
                        baseSalesReport.setGoodsname("（折扣）"+brand.getName());
                    }else{
                        baseSalesReport.setGoodsname("（折扣）其他");
                    }
                }
                if(groupcols.indexOf("brandid")==-1){
                    Brand brand = getGoodsBrandByID(baseSalesReport.getBrandid());
                    if(null!=brand){
                        baseSalesReport.setBrandname(brand.getName());
                    }
                    Personnel personnel = getPersonnelById(baseSalesReport.getBranduser());
                    if(null!=personnel){
                        baseSalesReport.setBrandusername(personnel.getName());
                    }
                    if("QC".equals(baseSalesReport.getSupplieruser())){
                        baseSalesReport.setSupplierusername("期初");
                    }else{
                        Personnel personnel2 = getPersonnelById(baseSalesReport.getSupplieruser());
                        if(null!=personnel2){
                            baseSalesReport.setSupplierusername(personnel2.getName());
                        }
                    }
                }
                if(groupcols.indexOf("branddept")==-1){
                    DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getBranddept());
                    if(null!=departMent){
                        baseSalesReport.setBranddeptname(departMent.getName());
                    }
                }
                if(groupcols.indexOf("branduser")==-1){
                    Personnel personnel = getPersonnelById(baseSalesReport.getBranduser());
                    if(null!=personnel){
                        baseSalesReport.setBrandusername(personnel.getName());
                    }
                }
            }
            if(groupcols.indexOf("goodssort")!=-1){
                if(StringUtils.isNotEmpty(baseSalesReport.getGoodssort())){
                    WaresClass waresClass = getBaseGoodsMapper().getWaresClassInfo(baseSalesReport.getGoodssort());
                    if(null != waresClass){
                        baseSalesReport.setGoodssortname(waresClass.getThisname());
                    }else{
                        baseSalesReport.setGoodssortname("其他未定义");
                    }
                }else{
                    baseSalesReport.setGoodssort("nodata");
                    baseSalesReport.setGoodssortname("其他未指定");
                }
            }
            if(groupcols.indexOf("brandid")!=-1){
                if(StringUtils.isNotEmpty(baseSalesReport.getBrandid())){
                    Brand brand = getGoodsBrandByID(baseSalesReport.getBrandid());
                    if(null!=brand){
                        baseSalesReport.setBrandname(brand.getName());
                    }else{
                        baseSalesReport.setBrandname("其他未定义");
                    }
                }else{
                    baseSalesReport.setBrandid("nodata");
                    baseSalesReport.setBrandname("其他未指定");
                }
                if(groupcols.indexOf("branddept")==-1){
                    DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getBranddept());
                    if(null!=departMent){
                        baseSalesReport.setBranddeptname(departMent.getName());
                    }
                }
                Personnel personnel = getPersonnelById(baseSalesReport.getBranduser());
                if(null!=personnel){
                    baseSalesReport.setBrandusername(personnel.getName());
                }
                if("QC".equals(baseSalesReport.getSupplieruser())){
                    baseSalesReport.setSupplierusername("期初");
                }else{
                    Personnel personnel2 = getPersonnelById(baseSalesReport.getSupplieruser());
                    if(null!=personnel2){
                        baseSalesReport.setSupplierusername(personnel2.getName());
                    }
                }
            }
            if(groupcols.indexOf("branduser")!=-1){
                if(StringUtils.isNotEmpty(baseSalesReport.getBranduser())){
                    Personnel personnel = getPersonnelById(baseSalesReport.getBranduser());
                    if(null!=personnel){
                        baseSalesReport.setBrandusername(personnel.getName());
                    }else{
                        baseSalesReport.setBrandusername("其他未定义");
                    }
                }else{
                    baseSalesReport.setBranduser("nodata");
                    baseSalesReport.setBrandusername("其他未指定");
                }
                if(groupcols.indexOf("branddept")==-1){
                    //分公司
                    Map map2 = new HashMap();
                    map2.put("pid", "");
                    List<DepartMent> deptList = getBaseDepartMentMapper().getDeptListByParam(map2);
                    for(DepartMent dept : deptList){
                        if(baseSalesReport.getBranddept().indexOf(dept.getId()) == 0){
                            baseSalesReport.setBranddeptname(dept.getName());
                        }
                    }
                }
            }
            if(groupcols.indexOf("branddept")!=-1){
                if(StringUtils.isNotEmpty(baseSalesReport.getBranddept())){
                    DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getBranddept());
                    if(null!=departMent){
                        baseSalesReport.setBranddeptname(departMent.getName());
                    }else{
                        baseSalesReport.setBranddeptname("其他未定义");
                    }
                }else{
                    baseSalesReport.setBranddept("nodata");
                    baseSalesReport.setBranddeptname("其他未指定");
                }
            }
            if(groupcols.indexOf("supplierid")!=-1){
                if(StringUtils.isNotEmpty(baseSalesReport.getSupplierid())){
                    BuySupplier supplier = getSupplierInfoById(baseSalesReport.getSupplierid());
                    if(null != supplier){
                        baseSalesReport.setSuppliername(supplier.getName());
                    }else{
                        baseSalesReport.setSuppliername("其他未定义");
                    }
                }else{
                    baseSalesReport.setSupplierid("nodata");
                    baseSalesReport.setSuppliername("其他未指定");
                }
            }
            if(groupcols.indexOf("supplieruser")!=-1){
                if(StringUtils.isNotEmpty(baseSalesReport.getSupplieruser())){
                    if("QC".equals(baseSalesReport.getSupplieruser())){
                        baseSalesReport.setSupplierusername("期初");
                    }else{
                        Personnel personnel2 = getPersonnelById(baseSalesReport.getSupplieruser());
                        if(null!=personnel2){
                            baseSalesReport.setSupplierusername(personnel2.getName());
                        }else{
                            baseSalesReport.setSupplierusername("其他未定义");
                        }
                    }
                }else{
                    baseSalesReport.setSupplieruser("nodata");
                    baseSalesReport.setSupplierusername("其他未指定");
                }
            }
            if(groupcols.indexOf("storageid")!=-1){
                if(StringUtils.isNotEmpty(baseSalesReport.getStorageid())){
                    StorageInfo storageInfo = getStorageInfoByID(baseSalesReport.getStorageid());
                    if(null!=storageInfo){
                        baseSalesReport.setStoragename(storageInfo.getName());
                    }else{
                        baseSalesReport.setStoragename("其他未定义");
                    }
                }else{
                    baseSalesReport.setStoragename("nodata");
                    baseSalesReport.setStoragename("其他未指定");
                }
            }
        }
        int count = salesCustomerReportMapper.getSalesPresentReportDataCount(pageMap);
        PageData pageData = new PageData(count,list,pageMap);
        //取合计数据
        pageMap.getCondition().put("groupcols", "all");
        List<BaseSalesReport> footer = salesCustomerReportMapper.getSalesPresentReportData(pageMap);
        footer = returnBaseSaleReportFooter(footer, pageMap, groupcols);
        pageData.setFooter(footer);
        return pageData ;
    }

	@Override
	public PageData getSalesPromotionReportData(PageMap pageMap) throws Exception {
		//小计列
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		if (!pageMap.getCondition().containsKey("groupcols")) {
			groupcols = "customerid";
		}
		groupcols = groupcols + ",ptype";
		pageMap.getCondition().put("groupcols", groupcols);
		List<Map> list = salesCustomerReportMapper.getSalesPromotionReportData(pageMap);
		for(Map map : list){
			String customerid = (String)map.get("customerid");
			String pcustomerid = (String)map.get("pcustomerid");
			String customersort = (String)map.get("customersort");
			String salesarea = (String)map.get("salesarea");
			String salesdept = (String)map.get("salesdept");
			String salesuser = (String)map.get("salesuser");
			String goodssort = (String)map.get("goodssort");
			String supplierid = (String)map.get("supplierid");
			String storageid = (String)map.get("storageid");
			String goodsid = (String)map.get("goodsid");
			String brandid = (String)map.get("brandid");
			String ptype = (String)map.get("ptype");

			if("0".equals(ptype)){
				map.put("ptypename","正常商品");
			}else if("1".equals(ptype)){
				map.put("ptypename","买赠");
			}else if("2".equals(ptype)){
				map.put("ptypename","捆绑");
			}else if("3".equals(ptype)){
				map.put("ptypename","满赠");
			}else if("4".equals(ptype)){
				map.put("ptypename","特价");
			}
			//根据获取的商品编码获取条形码
			if (StringUtils.isNotEmpty(goodsid)) {
				GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
				if (null != goodsInfo) {
					map.put("barcode",goodsInfo.getBarcode());
					map.put("goodsname",goodsInfo.getName());
					map.put("spell",goodsInfo.getSpell());
				}
			}
			if (groupcols.indexOf("customerid") != -1) {
				if (StringUtils.isNotEmpty(customerid)) {
					Customer customer = getCustomerByID(customerid);
					if (null != customer) {
						map.put("customername",customer.getName());
						map.put("shortcode",customer.getShortcode());
					} else {
						map.put("customername","其他未定义");
					}
				} else {
					map.put("customername","其他未指定");
				}
				if (groupcols.indexOf("pcustomerid") == -1) {
					Customer pcustomer = getCustomerByID(pcustomerid);
					if (null != pcustomer) {
						map.put("pcustomername",pcustomer.getName());
					}
				}
				if (groupcols.indexOf("salesdept") == -1) {
					DepartMent departMent = getDepartmentByDeptid(salesdept);
					if (null != departMent) {
						map.put("salesdeptname",departMent.getName());
					}
				}
				if (groupcols.indexOf("customersort") == -1) {
					CustomerSort customerSort = getCustomerSortByID(customersort);
					if (null != customerSort) {
						map.put("customersortname",customerSort.getName());
					}
				}
				if (groupcols.indexOf("salesarea") == -1) {
					SalesArea salesArea = getSalesareaByID(salesarea);
					if (null != salesArea) {
						map.put("salesareaname",salesArea.getThisname());
					}
				}
				if (groupcols.indexOf("salesuser") == -1) {
					Personnel personnel = getPersonnelById(salesuser);
					if (null != personnel) {
						map.put("salesusername",personnel.getName());
					}
					DepartMent departMent = getDepartmentByDeptid(salesdept);
					if (null != departMent) {
						map.put("salesdeptname",departMent.getName());
					}
				}
			} else {
				map.put("customerid","");
				map.put("customername","");
				if (groupcols.indexOf("salesuser") == -1 && groupcols.indexOf("salesdept") == -1) {
					map.put("salesdeptname","");
				}
			}
			if (groupcols.indexOf("pcustomerid") != -1) {
				if (StringUtils.isNotEmpty(pcustomerid)) {
					Customer pcustomer = getCustomerByID(pcustomerid);
					if (null != pcustomer) {
						map.put("pcustomername",pcustomer.getName());
					} else {
						map.put("pcustomername","其他未定义");
					}
				} else {
					map.put("pcustomername","其他未指定");
				}
				if (groupcols.indexOf("customerid") == 1) {
					map.put("customerid","");
					map.put("customername","");
				}
			}
			if (groupcols.indexOf("customersort") != -1) {
				if (StringUtils.isNotEmpty(customersort)) {
					CustomerSort customerSort = getCustomerSortByID(customersort);
					if (null != customerSort) {
						map.put("customersortname",customerSort.getName());
					} else {
						map.put("customersortname","其他未定义");
					}
				} else {
					map.put("customersortname","其他未指定");
				}
			}
			if (groupcols.indexOf("salesarea") != -1) {
				if (StringUtils.isNotEmpty(salesarea)) {
					SalesArea salesArea = getSalesareaByID(salesarea);
					if (null != salesArea) {
						map.put("salesareaname",salesArea.getThisname());
					} else {
						map.put("salesareaname","其他未定义");
					}
				} else {
					map.put("salesareaname","其他未指定");
				}
			}
			if (groupcols.indexOf("salesdept") != -1) {
				if (StringUtils.isNotEmpty(salesdept)) {
					DepartMent departMent = getDepartmentByDeptid(salesdept);
					if (null != departMent) {
						map.put("salesdeptname",departMent.getName());
					} else {
						map.put("salesdeptname","其他未定义");
					}
				} else {
					map.put("salesdeptname","其他未指定");
				}
			}
			if (groupcols.indexOf("salesuser") != -1) {
				if (StringUtils.isNotEmpty(salesuser)) {
					Personnel personnel = getPersonnelById(salesuser);
					if (null != personnel) {
						map.put("salesusername",personnel.getName());
					} else {
						map.put("salesusername","其他未定义");
					}
				} else {
					map.put("salesusername", "其他未指定");
				}
				DepartMent departMent = getDepartmentByDeptid(salesdept);
				if (null != departMent) {
					map.put("salesdeptname",departMent.getName());
				}
				if (groupcols.indexOf("salesarea") == -1) {
					SalesArea salesArea = getSalesareaByID(salesarea);
					if (null != salesArea) {
						map.put("salesareaname",salesArea.getThisname());
					}
				}
			}
			if (groupcols.indexOf("goodsid") != -1) {
				GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
				if (null != goodsInfo) {
					map.put("goodsname",goodsInfo.getName());
					map.put("spell",goodsInfo.getSpell());
					map.put("boxnum",goodsInfo.getBoxnum());
					WaresClass waresClass = getBaseGoodsMapper().getWaresClassInfo(goodssort);
					if (null != waresClass) {
						map.put("goodssortname",waresClass.getThisname());
					}
					BuySupplier buySupplier = getSupplierInfoById(supplierid);
					if (null != buySupplier) {
						map.put("suppliername",buySupplier.getName());
					}
				}
				if (groupcols.indexOf("brandid") == -1) {
					Brand brand = getGoodsBrandByID(brandid);
					if (null != brand) {
						map.put("brandname",brand.getName());
					}
				}
			}
			if (groupcols.indexOf("goodssort") != -1) {
				if (StringUtils.isNotEmpty(goodssort)) {
					WaresClass waresClass = getBaseGoodsMapper().getWaresClassInfo(goodssort);
					if (null != waresClass) {
						map.put("goodssortname",waresClass.getThisname());
					} else {
						map.put("goodssortname","其他未定义");
					}
				} else {
					map.put("goodssortname", "其他未指定");
				}
			}
			if (groupcols.indexOf("brandid") != -1) {
				if (StringUtils.isNotEmpty(brandid)) {
					Brand brand = getGoodsBrandByID(brandid);
					if (null != brand) {
						map.put("brandname", brand.getName());
					} else {
						map.put("brandname", "其他未定义");
					}
				} else {
					map.put("brandname", "其他未指定");
				}
			}
			if (groupcols.indexOf("supplierid") != -1) {
				if (StringUtils.isNotEmpty(supplierid)) {
					BuySupplier supplier = getSupplierInfoById(supplierid);
					if (null != supplier) {
						map.put("suppliername", supplier.getName());
					} else {
						map.put("suppliername", "其他未定义");
					}
				} else {
					map.put("suppliername", "其他未指定");
				}
			}
			if (groupcols.indexOf("storageid") != -1) {
				if (StringUtils.isNotEmpty(storageid)) {
					StorageInfo storageInfo = getStorageInfoByID(storageid);
					if (null != storageInfo) {
						map.put("storagename", storageInfo.getName());
					} else {
						map.put("storagename", "其他未定义");
					}
				} else {
					map.put("storagename", "其他未指定");
				}
			}
		}
		int count = salesCustomerReportMapper.getSalesPromotionReportDataCount(pageMap);
		PageData pageData = new PageData(count,list,pageMap);

		List<Map> foot = new ArrayList<Map>();
		//取合计数据
		pageMap.getCondition().put("groupcols", "all");
		Map map = salesCustomerReportMapper.getSalesPromotionReportDataSum(pageMap);
		if(null != map && !map.isEmpty()){
			map.put("auxunitname","");
			map.put("unitname","");
			String[] groupArr = groupcols.split(",");
			if (groupArr[0].indexOf("brandid") != -1) {
				map.put("pcustomerid","");
				map.put("pcustomername","");
				map.put("brandid","");
				map.put("brandname","合计");
				map.put("branddept","");
				map.put("salesdept","");
				map.put("salesdeptname","");
				map.put("salesuser","");
				map.put("salesusername","");
				map.put("goodsid","");
				map.put("goodsname","");
				map.put("customerid","");
				map.put("customername","");
				map.put("goodssortname","");
				map.put("customersort","");
				map.put("customersortname","");
				map.put("supplierid","");
				map.put("suppliername","");
				map.put("storageid","");
				map.put("storagename","");
			} else if (groupArr[0].indexOf("salesdept") != -1) {
				map.put("pcustomerid", "");
				map.put("pcustomername", "");
				map.put("brandid", "");
				map.put("brandname", "");
				map.put("branddept", "");
				map.put("salesdept", "");
				map.put("salesdeptname", "合计");
				map.put("salesuser", "");
				map.put("salesusername", "");
				map.put("goodsid", "");
				map.put("goodsname", "");
				map.put("customerid", "");
				map.put("customername", "");
				map.put("goodssortname", "");
				map.put("customersort", "");
				map.put("customersortname", "");
				map.put("supplierid", "");
				map.put("suppliername", "");
				map.put("storageid", "");
				map.put("storagename", "");
			} else if (groupArr[0].indexOf("salesuser") != -1) {
				map.put("pcustomerid", "");
				map.put("pcustomername", "");
				map.put("brandid", "");
				map.put("brandname", "");
				map.put("branddept", "");
				map.put("salesdept", "");
				map.put("salesdeptname", "");
				map.put("salesuser", "");
				map.put("salesusername", "合计");
				map.put("goodsid", "");
				map.put("goodsname", "");
				map.put("customerid", "");
				map.put("customername", "");
				map.put("goodssortname", "");
				map.put("customersort", "");
				map.put("customersortname", "");
				map.put("supplierid", "");
				map.put("suppliername", "");
				map.put("storageid", "");
				map.put("storagename", "");
			} else if (groupArr[0].indexOf("goodsid") != -1) {
				map.put("pcustomerid", "");
				map.put("pcustomername", "");
				map.put("brandid", "");
				map.put("brandname", "");
				map.put("branddept", "");
				map.put("salesdept", "");
				map.put("salesdeptname", "");
				map.put("salesuser", "");
				map.put("salesusername", "");
				map.put("goodsid", "");
				map.put("goodsname", "合计");
				map.put("customerid", "");
				map.put("customername", "");
				map.put("goodssortname", "");
				map.put("customersort", "");
				map.put("customersortname", "");
				map.put("supplierid", "");
				map.put("suppliername", "");
				map.put("storageid", "");
				map.put("storagename", "");
			} else if (groupArr[0].indexOf("goodssort") != -1) {
				map.put("pcustomerid", "");
				map.put("pcustomername", "");
				map.put("brandid", "");
				map.put("brandname", "");
				map.put("branddept", "");
				map.put("salesdept", "");
				map.put("salesdeptname", "");
				map.put("salesuser", "");
				map.put("salesusername", "");
				map.put("goodsid", "");
				map.put("goodsname", "");
				map.put("customerid", "");
				map.put("customername", "");
				map.put("goodssortname", "合计");
				map.put("customersort", "");
				map.put("customersortname", "");
				map.put("supplierid", "");
				map.put("suppliername", "");
				map.put("storageid", "");
				map.put("storagename", "");
			} else if (groupArr[0].indexOf("pcustomerid") != -1) {
				map.put("pcustomerid", "");
				map.put("pcustomername", "合计");
				map.put("brandid", "");
				map.put("brandname", "");
				map.put("branddept", "");
				map.put("salesdept", "");
				map.put("salesdeptname", "");
				map.put("salesuser", "");
				map.put("salesusername", "");
				map.put("goodsid", "");
				map.put("goodsname", "");
				map.put("customerid", "");
				map.put("customername", "");
				map.put("goodssortname", "");
				map.put("customersort", "");
				map.put("customersortname", "");
				map.put("supplierid", "");
				map.put("suppliername", "");
				map.put("storageid", "");
				map.put("storagename", "");
			}else if (groupArr[0].indexOf("customerid") != -1) {
				map.put("pcustomerid", "");
				map.put("pcustomername", "");
				map.put("brandid", "");
				map.put("brandname", "");
				map.put("branddept", "");
				map.put("salesdept", "");
				map.put("salesdeptname", "");
				map.put("salesuser", "");
				map.put("salesusername", "");
				map.put("goodsid", "");
				map.put("goodsname", "");
				map.put("customerid", "");
				map.put("customername", "合计");
				map.put("goodssortname", "");
				map.put("customersort", "");
				map.put("customersortname", "");
				map.put("supplierid", "");
				map.put("suppliername", "");
				map.put("storageid", "");
				map.put("storagename", "");
			} else if (groupArr[0].indexOf("customersort") != -1) {
				map.put("pcustomerid", "");
				map.put("pcustomername", "");
				map.put("brandid", "");
				map.put("brandname", "");
				map.put("branddept", "");
				map.put("salesdept", "");
				map.put("salesdeptname", "");
				map.put("salesuser", "");
				map.put("salesusername", "");
				map.put("goodsid", "");
				map.put("goodsname", "");
				map.put("customerid", "");
				map.put("customername", "");
				map.put("goodssortname", "");
				map.put("customersort", "");
				map.put("customersortname", "合计");
				map.put("supplierid", "");
				map.put("suppliername", "");
				map.put("storageid", "");
				map.put("storagename", "");
			} else if (groupArr[0].indexOf("supplierid") != -1) {
				map.put("pcustomerid", "");
				map.put("pcustomername", "");
				map.put("brandid", "");
				map.put("brandname", "");
				map.put("branddept", "");
				map.put("salesdept", "");
				map.put("salesdeptname", "");
				map.put("salesuser", "");
				map.put("salesusername", "");
				map.put("goodsid", "");
				map.put("goodsname", "");
				map.put("customerid", "");
				map.put("customername", "");
				map.put("goodssortname", "");
				map.put("customersort", "");
				map.put("customersortname", "");
				map.put("supplierid", "");
				map.put("suppliername", "合计");
				map.put("storageid", "");
				map.put("storagename", "");
			}  else if (groupArr[0].indexOf("storageid") != -1) {
				map.put("pcustomerid", "");
				map.put("pcustomername", "");
				map.put("brandid", "");
				map.put("brandname", "");
				map.put("branddept", "");
				map.put("salesdept", "");
				map.put("salesdeptname", "");
				map.put("salesuser", "");
				map.put("salesusername", "");
				map.put("goodsid", "");
				map.put("goodsname", "");
				map.put("customerid", "");
				map.put("customername", "");
				map.put("goodssortname", "");
				map.put("customersort", "");
				map.put("customersortname", "");
				map.put("supplierid", "");
				map.put("suppliername", "");
				map.put("storageid", "");
				map.put("storagename", "合计");
			}else if (groupArr[0].indexOf("promotionid") != -1) {
				map.put("pcustomerid", "");
				map.put("pcustomername", "");
				map.put("brandid", "");
				map.put("brandname", "");
				map.put("branddept", "");
				map.put("salesdept", "");
				map.put("salesdeptname", "");
				map.put("salesuser", "");
				map.put("salesusername", "");
				map.put("goodsid", "");
				map.put("goodsname", "");
				map.put("customerid", "");
				map.put("customername", "");
				map.put("goodssortname", "");
				map.put("customersort", "");
				map.put("customersortname", "");
				map.put("supplierid", "");
				map.put("suppliername", "");
				map.put("storageid", "");
				map.put("storagename", "");
				map.put("promotionid", "合计");
			}

			foot.add(map);
		}
		pageData.setFooter(foot);
		return pageData;
	}

	@Override
	public PageData getSalesScheduleActivityDiscountReportData(PageMap pageMap) throws Exception {
		//小计列
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		if (!pageMap.getCondition().containsKey("groupcols")) {
			groupcols = "schedule,customerid,goodsid";
		}
		pageMap.getCondition().put("groupcols", groupcols);
		List<Map> list = salesCustomerReportMapper.getSalesScheduleActivityDiscountReportData(pageMap);
		for(Map map : list){
			String customerid = (String)map.get("customerid");
			String pcustomerid = (String)map.get("pcustomerid");
			String customersort = (String)map.get("customersort");
			String salesarea = (String)map.get("salesarea");
			String salesdept = (String)map.get("salesdept");
			String salesuser = (String)map.get("salesuser");
			String goodssort = (String)map.get("goodssort");
			String supplierid = (String)map.get("supplierid");
			String storageid = (String)map.get("storageid");
			String goodsid = (String)map.get("goodsid");
			String brandid = (String)map.get("brandid");
			String schedule = (String)map.get("schedule");
			getGoodsCostprice(storageid,goodsid);

			if (groupcols.indexOf("schedule") != -1) {
				if(StringUtils.isEmpty(schedule)){
					map.put("schedule","其他（未指定档期）");
				}
			}
			if (groupcols.indexOf("customerid") != -1) {
				if (StringUtils.isNotEmpty(customerid)) {
					Customer customer = getCustomerByID(customerid);
					if (null != customer) {
						map.put("customername",customer.getName());
					} else {
						map.put("customername","其他未定义");
					}
				} else {
					map.put("customername","其他未指定");
				}
			}
			if (groupcols.indexOf("customersort") != -1) {
				if (StringUtils.isNotEmpty(customersort)) {
					CustomerSort customerSort = getCustomerSortByID(customersort);
					if (null != customerSort) {
						map.put("customersortname",customerSort.getThisname());
					} else {
						map.put("customersortname","其他未定义");
					}
				} else {
					map.put("customersortname","其他未指定");
				}
			}
			if (groupcols.indexOf("goodsid") != -1) {
				if(StringUtils.isNotEmpty(goodsid)){
					GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
					if (null != goodsInfo) {
						map.put("goodsname",goodsInfo.getName());
						map.put("barcode",goodsInfo.getBarcode());
						map.put("costprice",goodsInfo.getNewbuyprice());

						if(goodsInfo.getNewbuyprice().compareTo(BigDecimal.ZERO) != 0){
							BigDecimal offprice = (BigDecimal) map.get("offprice");
							if(offprice.compareTo(BigDecimal.ZERO) != 0){
								BigDecimal rate = (offprice.subtract(goodsInfo.getNewbuyprice())).divide(offprice,BigDecimal.ROUND_HALF_DOWN,6);
								map.put("rate",rate.multiply(new BigDecimal(100)));
							}
						}else{
							map.put("rate",100);
						}

					}else{
						map.put("goodsname", "其他未定义");
					}
				}else{
					map.put("goodsname", "其他未指定");
				}
			}
			if (groupcols.indexOf("brandid") != -1) {
				if (StringUtils.isNotEmpty(brandid)) {
					Brand brand = getGoodsBrandByID(brandid);
					if (null != brand) {
						map.put("brandname", brand.getName());
					} else {
						map.put("brandname", "其他未定义");
					}
				} else {
					map.put("brandname", "其他未指定");
				}
			}
		}
		int count = salesCustomerReportMapper.getSalesScheduleActivityDiscountReportDataCount(pageMap);
		PageData pageData = new PageData(count,list,pageMap);

		List<Map> foot = new ArrayList<Map>();
		//取合计数据
		pageMap.getCondition().put("groupcols", "all");
		Map map = salesCustomerReportMapper.getSalesScheduleActivityDiscountReportDataSum(pageMap);
		if(map != null && !map.isEmpty()){
			String[] groupArr = groupcols.split(",");
			if (groupArr[0].indexOf("brandid") != -1) {
				map.put("schedule","");
				map.put("brandid","");
				map.put("brandname","合计");
				map.put("goodsid","");
				map.put("goodsname","");
				map.put("customerid","");
				map.put("customername","");
				map.put("customersort","");
				map.put("customersortname","");
			} else if (groupArr[0].indexOf("schedule") != -1) {
				map.put("schedule","合计");
				map.put("brandid","");
				map.put("brandname","");
				map.put("goodsid","");
				map.put("goodsname","");
				map.put("customerid","");
				map.put("customername","");
				map.put("customersort","");
				map.put("customersortname","");
			} else if (groupArr[0].indexOf("goodsid") != -1) {
				map.put("schedule","");
				map.put("brandid","");
				map.put("brandname","");
				map.put("goodsid","");
				map.put("goodsname","合计");
				map.put("customerid","");
				map.put("customername","");
				map.put("customersort","");
				map.put("customersortname","");
			} else if (groupArr[0].indexOf("customerid") != -1) {
				map.put("schedule","");
				map.put("brandid","");
				map.put("brandname","");
				map.put("goodsid","");
				map.put("goodsname","");
				map.put("customerid","");
				map.put("customername","合计");
				map.put("customersort","");
				map.put("customersortname","");
			} else if (groupArr[0].indexOf("customersort") != -1) {
				map.put("schedule","");
				map.put("brandid","");
				map.put("brandname","");
				map.put("goodsid","");
				map.put("goodsname","");
				map.put("customerid","");
				map.put("customername","");
				map.put("customersort","");
				map.put("customersortname","合计 ");
			}
			foot.add(map);
		}
		pageData.setFooter(foot);
		return pageData;
	}

	@Override
	public PageData getSalesBillStatementPageData(PageMap pageMap) throws Exception {

		List<Map> salesBillStatementMapList = salesCustomerReportMapper.selectSalesBillStatementList(pageMap);
		for(Map salesBillStatementMap : salesBillStatementMapList) {

			// 客户
			String customerid = (String) salesBillStatementMap.get("customerid");
			Customer customer = getCustomerByID(customerid);
			if (customer != null) {
				salesBillStatementMap.put("customername", customer.getName());
			}

			// 总店
			String pcustomerid = (String) salesBillStatementMap.get("pcustomerid");
			Customer pcustomer = getCustomerByID(pcustomerid);
			if (pcustomer != null) {
				salesBillStatementMap.put("pcustomername", pcustomer.getName());
			}

			// 销售区域
			String salesarea = (String) salesBillStatementMap.get("salesarea");
			SalesArea salesArea = getSalesareaByID(salesarea);
			if (salesArea != null) {
				salesBillStatementMap.put("salesareaname", salesArea.getName());
			}

			// 客户分类
			String customersort = (String) salesBillStatementMap.get("customersort");
			CustomerSort customerSort = getCustomerSortByID(customersort);
			if (customerSort != null) {
				salesBillStatementMap.put("customersortname", customerSort.getName());
			}

			// 客户业务员
			String salesuser = (String) salesBillStatementMap.get("salesuser");
			Personnel salesuserPersonnel = getPersonnelById(salesuser);
			if (salesuserPersonnel != null) {
				salesBillStatementMap.put("salesusername", salesuserPersonnel.getName());
			}

			// 销售部门
			String salesdept = (String) salesBillStatementMap.get("salesdept");
			DepartMent salesDept = getDepartmentByDeptid(salesdept);
			if (salesDept != null) {
				salesBillStatementMap.put("salesdeptname", salesDept.getName());
			}
		}

		int totalCount = salesCustomerReportMapper.selectSalesBillStatementTotalCount(pageMap);
		PageData data = new PageData(totalCount, salesBillStatementMapList, pageMap);
		List<Map> footerMap = salesCustomerReportMapper.selectSalesBillStatementSumData(pageMap);
		data.setFooter(footerMap);
		return data;
	}

	@Override
	public PageData getSalesBillStatementDetailPageData(PageMap pageMap) throws Exception {

		List<Map> salesBillStatementDetailMapList = salesCustomerReportMapper.selectSalesBillStatementDetailList(pageMap);
		for(Map salesBillStatementDetailMap : salesBillStatementDetailMapList) {

			// 单据类型
			String billtype = (String) salesBillStatementDetailMap.get("billtype");
            if("1".equals(billtype)) {
                salesBillStatementDetailMap.put("billtypename", "销售订单");
            } else if("2".equals(billtype)) {
                salesBillStatementDetailMap.put("billtypename", "退货通知单");
            } else if("3".equals(billtype)) {
                salesBillStatementDetailMap.put("billtypename", "冲差单");
            }
		}

		int totalCount = salesCustomerReportMapper.selectSalesBillStatementDetailTotalCount(pageMap);
		PageData data = new PageData(totalCount, salesBillStatementDetailMapList, pageMap);
		List<Map> footerMap = salesCustomerReportMapper.selectSalesBillStatementDetailSumData(pageMap);
		data.setFooter(footerMap);
		return data;
	}

	@Override
	public PageData showSalesSuppliserGrossReportData(PageMap pageMap) throws Exception {
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		if (!pageMap.getCondition().containsKey("groupcols")) {
			groupcols = "supplierid";
			pageMap.getCondition().put("groupcols", groupcols);
		}
		String begindate = (String)pageMap.getCondition().get("begindate");
		if(StringUtils.isEmpty(begindate)){
			pageMap.getCondition().put("begindate","2010-01-01");
		}
		String enddate = (String)pageMap.getCondition().get("enddate");
		if(StringUtils.isEmpty(enddate)){
			pageMap.getCondition().put("enddate",CommonUtils.getTodayDataStr());
		}
		List<Map> salesBillStatementDetailMapList = salesCustomerReportMapper.showSalesSuppliserGrossReportDataList(pageMap);
		for(Map salesBillStatementDetailMap : salesBillStatementDetailMapList) {
			String goodsid = (String)salesBillStatementDetailMap.get("goodsid");
			GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
			if(null!=goodsInfo){
				salesBillStatementDetailMap.put("goodsname",goodsInfo.getName());
			}
			if("pushbalance".equals(goodsid)){
				salesBillStatementDetailMap.put("goodsname","冲差");
			}
			if("purchasepush".equals(goodsid)){
				salesBillStatementDetailMap.put("goodsname","采购冲差");
			}
			String supplierid = (String)salesBillStatementDetailMap.get("supplierid");
			BuySupplier supplier = getSupplierInfoById(supplierid);
			if(null!=supplier){
				salesBillStatementDetailMap.put("suppliername",supplier.getName());
			}

			String brandid = (String)salesBillStatementDetailMap.get("brandid");
			Brand brand = getGoodsBrandByID(brandid);
			if(null!=brand){
				salesBillStatementDetailMap.put("brandname",brand.getName());
			}
		}

		int totalCount = salesCustomerReportMapper.showSalesSuppliserGrossReportDataCount(pageMap);
		PageData data = new PageData(totalCount, salesBillStatementDetailMapList, pageMap);
		List<Map> footerMap = salesCustomerReportMapper.showSalesSuppliserGrossReportDataSum(pageMap);
		data.setFooter(footerMap);
		return data;
	}
	/**
	 * 获取销售订货单报表数据
	 * @param pageMap
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author luoqiang
	 * @date Jan 10, 2018
	 */
	public PageData getOrderGoodsReportData(PageMap pageMap) throws Exception{
		String datasql=getDataAccessRule("t_sales_order","t");
		pageMap.setDataSql(datasql);
		List<Map> list=new ArrayList<Map>();
		//isphone为手机端订货单报表查询
		if(!pageMap.getCondition().containsKey("isphone")){
			list=orderGoodsMapper.getOrderGoodsReportList(pageMap);
		}else{
			list=orderGoodsMapper.getPhoneOrderGoodsReportList(pageMap);
		}

		//商品库存缓存 防止同一张单多条商品 可用量问题
		Map storageGoodsMap = new HashMap();
		for(Map map:list){
			String goodsid=(String)map.get("goodsid");
			String customerid=(String)map.get("customerid");
			String salesdept=(String)map.get("salesdept");
			String salesuser=(String)map.get("salesuser");
			GoodsInfo goodsInfo=getGoodsInfoByID(goodsid);
			if(goodsInfo!=null){
				map.put("goodsname",goodsInfo.getName());
				map.put("barcode",goodsInfo.getBarcode());
				map.put("boxnum",goodsInfo.getBoxnum());
				map.put("goodsInfo",goodsInfo);
			}
			Customer customer=getCustomerByID(customerid);
			if(customer!=null){
				map.put("customername",customer.getName());
			}
			DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(salesdept);
			if (departMent != null) {
				map.put("salesdept",departMent.getName());
			}
			Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(salesuser);
			if (personnel != null) {
				map.put("salesuser",salesuser);
			}
			//商品客户价格套价格 ---》 参考价
			BigDecimal referenceprice = getGoodsPriceSetByCustomer(goodsid,customerid);
			map.put("referenceprice",referenceprice);
		}

		int count;
		Map footerMap=new HashMap();
        //isphone为手机端订货单报表查询
        if(!pageMap.getCondition().containsKey("isphone")){
            count=orderGoodsMapper.getOrderGoodsReportCount(pageMap);
			footerMap=orderGoodsMapper.getOrderGoodsReportSum(pageMap);
        }else{
            count=orderGoodsMapper.getPhoneOrderGoodsReportCount(pageMap);
        }

		PageData pageData=new PageData(count,list,pageMap);
		List footerList=new ArrayList();
		footerList.add(footerMap);
		pageData.setFooter(footerList);
		return pageData;
	}

	@Override
	public PageData showBaseSalesAnalysisReportData(PageMap pageMap) throws Exception {
		//数据权限类型
		String datasqltype = null != pageMap.getCondition().get("datasqltype") ? (String) pageMap.getCondition().get("datasqltype") : "";
		//小计列
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		if (!pageMap.getCondition().containsKey("groupcols")) {
			groupcols = "customerid";
			pageMap.getCondition().put("groupcols", groupcols);
		}
		if (datasqltype.equals("adduserid")) {
			String dataSql = getDataAccessRule("t_report_adduser_base", "z");
			pageMap.setDataSql(dataSql);
		} else {
			String dataSql = getDataAccessRule("t_report_sales_base", "z");
			pageMap.setDataSql(dataSql);
		}
		String query_sql = " 1=1 ";
		String query_sqlall = " 1=1 ";
		String query_sql_push = "";
		if (pageMap.getCondition().containsKey("goodsid")) {
			String str = (String) pageMap.getCondition().get("goodsid");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				query_sql += " and t1.goodsid = '" + str + "' ";
				query_sqlall += " and t1.goodsid = '" + str + "' ";
			} else {
				query_sql += " and FIND_IN_SET(t1.goodsid,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t1.goodsid,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("brandid")) {
			String str = (String) pageMap.getCondition().get("brandid");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t1.brandid = '" + str + "' ";
					query_sqlall += " and t1.brandid = '" + str + "' ";
				} else {
					query_sql += " and (t1.brandid is null or t1.brandid = '')";
					query_sqlall += " and (t1.brandid is null or t1.brandid = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t1.brandid,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t1.brandid,'" + str + "')";
			}
		} else if (pageMap.getCondition().containsKey("brandids")) {
			String str = (String) pageMap.getCondition().get("brandids");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t1.brandid = '" + str + "' ";
					query_sqlall += " and t1.brandid = '" + str + "' ";
				} else {
					query_sql += " and (t1.brandid is null or t1.brandid = '')";
					query_sqlall += " and (t1.brandid is null or t1.brandid = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t1.brandid,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t1.brandid,'" + str + "')";
			}
		}

		if (pageMap.getCondition().containsKey("branduser")) {
			String str = (String) pageMap.getCondition().get("branduser");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t1.branduser = '" + str + "' ";
					query_sqlall += " and t1.branduser = '" + str + "' ";
				} else {
					query_sql += " and (t1.branduser is null or t1.branduser = '')";
					query_sqlall += " and (t1.branduser is null or t1.branduser = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t1.branduser,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t1.branduser,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("supplieruser")) {
			String str = (String) pageMap.getCondition().get("supplieruser");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t1.supplieruser = '" + str + "' ";
					query_sqlall += " and t1.supplieruser = '" + str + "' ";
				} else {
					query_sql += " and (t1.supplieruser is null or t1.supplieruser = '')";
					query_sqlall += " and (t1.supplieruser is null or t1.supplieruser = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t1.supplieruser,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t1.supplieruser,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("branddept")) {
			String str = (String) pageMap.getCondition().get("branddept");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t1.branddept like '" + str + "%' ";
					query_sqlall += " and t1.branddept like '" + str + "%' ";
				} else {
					query_sql += " and (t1.branddept is null or t1.branddept = '')";
					query_sqlall += " and (t1.branddept is null or t1.branddept = '')";
				}
			} else {
				String retStr = "";
				String[] branddeptArr = str.split(",");
				for (String branddept : branddeptArr) {
					Map map = new HashMap();
					map.put("deptid", branddept);
					List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
					if (dList.size() != 0) {
						for (DepartMent departMent : dList) {
							if (org.apache.commons.lang.StringUtils.isNotEmpty(retStr)) {
								retStr += "," + departMent.getId();
							} else {
								retStr = departMent.getId();
							}
						}
					}
				}
				query_sql += " and FIND_IN_SET(t1.branddept,'" + retStr + "')";
				query_sqlall += " and FIND_IN_SET(t1.branddept,'" + retStr + "')";
			}
		}
		if (pageMap.getCondition().containsKey("salesdept")) {
			String str = (String) pageMap.getCondition().get("salesdept");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t.salesdept like '" + str + "%' ";
					query_sqlall += " and t.salesdept like '" + str + "%' ";
				} else {
					query_sql += " and (t.salesdept is null or t.salesdept = '')";
					query_sqlall += " and (t.salesdept is null or t.salesdept = '')";
				}
			} else {
				String retStr = "";
				String[] salesdeptArr = str.split(",");
				for (String salesdept : salesdeptArr) {
					Map map = new HashMap();
					map.put("deptid", salesdept);
					List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
					if (dList.size() != 0) {
						for (DepartMent departMent : dList) {
							if (org.apache.commons.lang.StringUtils.isNotEmpty(retStr)) {
								retStr += "," + departMent.getId();
							} else {
								retStr = departMent.getId();
							}
						}
					}
				}
				query_sql += " and FIND_IN_SET(t.salesdept,'" + retStr + "')";
				query_sqlall += " and FIND_IN_SET(t.salesdept,'" + retStr + "')";
			}
		}
		if (pageMap.getCondition().containsKey("customersort")) {
			String str = (String) pageMap.getCondition().get("customersort");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t.customersort like '" + str + "%' ";
					query_sqlall += " and t.customersort like '" + str + "%' ";
				} else {
					query_sql += " and (t.customersort is null or t.customersort = '')";
					query_sqlall += " and (t.customersort is null or t.customersort = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t.customersort,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t.customersort,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("salesarea")) {
			String str = (String) pageMap.getCondition().get("salesarea");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t.salesarea = '" + str + "' ";
					query_sqlall += " and t.salesarea = '" + str + "' ";
				} else {
					query_sql += " and (t.salesarea is null or t.salesarea = '')";
					query_sqlall += " and (t.salesarea is null or t.salesarea = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t.salesarea,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t.salesarea,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("salesuser")) {
			String str = (String) pageMap.getCondition().get("salesuser");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t.salesuser = '" + str + "' ";
					query_sqlall += " and t.salesuser = '" + str + "' ";
				} else {
					query_sql += " and (t.salesuser is null or t.salesuser = '')";
					query_sqlall += " and (t.salesuser is null or t.salesuser = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t.salesuser,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t.salesuser,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("supplierid")) {
			String str = (String) pageMap.getCondition().get("supplierid");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t1.supplierid = '" + str + "' ";
					query_sqlall += " and t1.supplierid = '" + str + "' ";
				} else {
					query_sql += " and (t1.supplierid is null or t1.supplierid = '')";
					query_sqlall += " and (t1.supplierid is null or t1.supplierid = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t1.supplierid,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t1.supplierid,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("goodssort")) {
			String str = (String) pageMap.getCondition().get("goodssort");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t1.goodssort = '" + str + "' ";
				} else {
					query_sql += " and (t1.goodssort is null or t1.goodssort = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t1.goodssort,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("goodstype")) {
			String str = (String) pageMap.getCondition().get("goodstype");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and FIND_IN_SET(g.goodstype,'" + str + "')";
		}
		if (pageMap.getCondition().containsKey("customerid")) {
			String str = (String) pageMap.getCondition().get("customerid");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t.customerid = '" + str + "' ";
					query_sqlall += " and t.customerid = '" + str + "' ";
				} else {
					query_sql += " and (t.customerid is null or t.customerid = '')";
					query_sqlall += " and (t.customerid is null or t.customerid = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t.customerid,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t.customerid,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("pcustomerid")) {
			String str = (String) pageMap.getCondition().get("pcustomerid");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t.pcustomerid = '" + str + "' ";
					query_sqlall += " and t.pcustomerid = '" + str + "' ";
				} else {
					query_sql += " and (t.pcustomerid is null or t.pcustomerid = '')";
					query_sqlall += " and (t.pcustomerid is null or t.pcustomerid = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t.pcustomerid,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t.pcustomerid,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("adduserid")) {
			String str = (String) pageMap.getCondition().get("adduserid");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t.adduserid = '" + str + "' ";
					query_sqlall += " and t.adduserid = '" + str + "' ";
				} else {
					query_sql += " and (t.adduserid is null or t.adduserid = '')";
					query_sqlall += " and (t.adduserid is null or t.adduserid = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t.adduserid,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t.adduserid,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("storageid")) {
			String str = (String) pageMap.getCondition().get("storageid");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t.storageid = '" + str + "' ";
				} else {
					query_sql += " and (t.storageid is null or t.storageid = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t.storageid,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("businessdate1")) {
			String str = (String) pageMap.getCondition().get("businessdate1");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t.businessdate >= '" + str + "'";
			query_sqlall += " and t.businessdate >= '" + str + "'";
		}
		if (pageMap.getCondition().containsKey("businessdate2")) {
			String str = (String) pageMap.getCondition().get("businessdate2");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t.businessdate <= '" + str + "'";
			query_sqlall += " and t.businessdate <= '" + str + "'";
		}
		if (org.apache.commons.lang.StringUtils.isNotEmpty(query_sqlall)) {
			query_sql_push = query_sqlall.replaceAll("t1.", "t.");
			query_sql_push = query_sql_push.replaceAll("t.goodsid", "t.brandid");
		}
		if (pageMap.getCondition().containsKey("storageid")) {
			query_sql_push = " and 1!= 1";
		}
		pageMap.getCondition().put("query_sql", query_sql);
		pageMap.getCondition().put("query_sqlall", query_sqlall);
		pageMap.getCondition().put("query_sql_push", query_sql_push);
		//排序
		String orderstr = "";
		if (pageMap.getCondition().containsKey("sort")) {
			String sort = (String) pageMap.getCondition().get("sort");
			String order = (String) pageMap.getCondition().get("order");
			if (null == order) {
				order = "asc";
			}
			orderstr = sort + " " + order;
		}

		String pushtype_sql = "";
		String pushtype_sqlall = "";
		String pushtype_sqlother = "";
		pageMap.getCondition().put("orderstr", orderstr);
		List<SysCode> pushtypeList = getBaseSysCodeMapper().getSysCodeListForeign("pushtypeprint");
		if(null!=pushtypeList && pushtypeList.size()>0){
			for(SysCode pushtypeprint : pushtypeList){
				pushtype_sql += ", (case when t.pushtype = '"+pushtypeprint.getCode()+"' then t.amount else 0 end) as pushtype"+pushtypeprint.getCode();
				pushtype_sqlall += " ,sum(z.pushtype"+pushtypeprint.getCode()+") as pushtype"+pushtypeprint.getCode()+"";
				pushtype_sqlother += " , 0 as pushtype"+pushtypeprint.getCode();
			}
		}
		pageMap.getCondition().put("pushtype_sql", pushtype_sql);
		pageMap.getCondition().put("pushtype_sqlall", pushtype_sqlall);
		pageMap.getCondition().put("pushtype_sqlother", pushtype_sqlother);

		String rejectcategory_sql = "";
		String rejectcategory_sqlall = "";
		String rejectcategory_sqlother = "";
		List<SysCode> rejectCategoryList = getBaseSysCodeMapper().getSysCodeListForeign("rejectCategory");
		if(null!=rejectCategoryList && rejectCategoryList.size()>0){
			for(SysCode rejectCategory : rejectCategoryList){
				rejectcategory_sql += ", (case when t1.rejectcategory = '"+rejectCategory.getCode()+"' then t1.taxamount else 0 end) as rejectcategory"+rejectCategory.getCode()
						+", (case when t1.rejectcategory = '"+rejectCategory.getCode()+"' and t.sourcetype='1' then t1.taxamount else 0 end) as afterrejectamount"+rejectCategory.getCode();
				rejectcategory_sqlall += " ,sum(z.rejectcategory"+rejectCategory.getCode()+") as rejectcategory"+rejectCategory.getCode()
						+" ,sum(z.afterrejectamount"+rejectCategory.getCode()+") as afterrejectamount"+rejectCategory.getCode();
				rejectcategory_sqlother += " , 0 as rejectcategory"+rejectCategory.getCode()+ " , 0 as afterrejectamount"+rejectCategory.getCode();
			}
		}
		pageMap.getCondition().put("rejectcategory_sql", rejectcategory_sql);

		pageMap.getCondition().put("rejectcategory_sqlall", rejectcategory_sqlall);
		pageMap.getCondition().put("rejectcategory_sqlother", rejectcategory_sqlother);

		List<Map> list = salesCustomerReportMapper.showBaseSalesAnalysisReportData(pageMap);
		for (Map baseSalesReportMap : list) {
			baseSalesReportMap.put("isfoot",false);
			if (null == baseSalesReportMap) {
				continue;
			}
			BigDecimal sendnum = null != (BigDecimal)baseSalesReportMap.get("sendnum") ? (BigDecimal)baseSalesReportMap.get("sendnum") : BigDecimal.ZERO;
			BigDecimal checkreturnnum = null != (BigDecimal)baseSalesReportMap.get("checkreturnnum") ? (BigDecimal)baseSalesReportMap.get("checkreturnnum")  : BigDecimal.ZERO;
			BigDecimal returnnum = null != (BigDecimal)baseSalesReportMap.get("returnnum") ? (BigDecimal)baseSalesReportMap.get("returnnum") : BigDecimal.ZERO;
			BigDecimal sendtotalbox = null != (BigDecimal)baseSalesReportMap.get("sendtotalbox") ? (BigDecimal)baseSalesReportMap.get("sendtotalbox") : BigDecimal.ZERO;
			BigDecimal returntotalbox = null != (BigDecimal)baseSalesReportMap.get("returntotalbox") ? (BigDecimal)baseSalesReportMap.get("returntotalbox") : BigDecimal.ZERO;
			BigDecimal sendamount = null != (BigDecimal)baseSalesReportMap.get("sendamount") ? (BigDecimal)baseSalesReportMap.get("sendamount") : BigDecimal.ZERO;
			BigDecimal returnamount = null != (BigDecimal)baseSalesReportMap.get("returnamount") ? (BigDecimal)baseSalesReportMap.get("returnamount") : BigDecimal.ZERO;
			BigDecimal pushbalanceamount = null != (BigDecimal)baseSalesReportMap.get("pushbalanceamount") ? (BigDecimal)baseSalesReportMap.get("pushbalanceamount") : BigDecimal.ZERO;
			BigDecimal pushbalancenotaxamount = null != (BigDecimal)baseSalesReportMap.get("pushbalancenotaxamount") ? (BigDecimal)baseSalesReportMap.get("pushbalancenotaxamount") : BigDecimal.ZERO;
			BigDecimal sendnotaxamount = null != (BigDecimal)baseSalesReportMap.get("sendnotaxamount") ? (BigDecimal)baseSalesReportMap.get("sendnotaxamount") : BigDecimal.ZERO;
			BigDecimal returnnotaxamount = null != (BigDecimal)baseSalesReportMap.get("returnnotaxamount") ? (BigDecimal)baseSalesReportMap.get("returnnotaxamount") : BigDecimal.ZERO;
			BigDecimal costamount = null != (BigDecimal)baseSalesReportMap.get("costamount") ? (BigDecimal)baseSalesReportMap.get("costamount") : BigDecimal.ZERO;
			BigDecimal saleamount=sendamount.subtract(returnamount).add(pushbalanceamount);
			BigDecimal checkreturnamount=null!=(BigDecimal)baseSalesReportMap.get("checkreturnamount") ? (BigDecimal)baseSalesReportMap.get("checkreturnamount") : BigDecimal.ZERO;

			//退货率=退货金额(不包括直退)/销售金额
			if (saleamount.compareTo(BigDecimal.ZERO) != 0) {
				BigDecimal checkreturnrate = checkreturnamount.divide(saleamount, 6, BigDecimal.ROUND_HALF_UP);
				baseSalesReportMap.put("checkreturnrate",checkreturnrate.multiply(new BigDecimal(100)));
			}
			//销售数量 = 发货单数量 - 直退数量
			baseSalesReportMap.put("salenum",sendnum.subtract(returnnum));
			//销售箱数
			baseSalesReportMap.put("saletotalbox",sendtotalbox.subtract(returntotalbox));
			baseSalesReportMap.put("saleamount",sendamount.subtract(returnamount).add(pushbalanceamount));
			baseSalesReportMap.put("salenotaxamount",sendnotaxamount.subtract(returnnotaxamount).add(pushbalancenotaxamount));
			if (null != (BigDecimal)baseSalesReportMap.get("saleamount")) {
				//毛利额 = 销售金额 - 成本金额
				baseSalesReportMap.put("salemarginamount",((BigDecimal)baseSalesReportMap.get("saleamount")).subtract(costamount));
				//实际毛利率 = （销售金额 - 成本金额）/销售金额*100
				if (((BigDecimal)baseSalesReportMap.get("saleamount")).compareTo(BigDecimal.ZERO) != 0) {
					BigDecimal realrate = ((BigDecimal)baseSalesReportMap.get("saleamount")).subtract(costamount).divide((BigDecimal)baseSalesReportMap.get("saleamount"), 6, BigDecimal.ROUND_HALF_UP);
					realrate = realrate.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
					if (((BigDecimal)baseSalesReportMap.get("saleamount")).compareTo(BigDecimal.ZERO) == -1) {
						baseSalesReportMap.put("realrate",realrate.negate());
					} else {
						baseSalesReportMap.put("realrate",realrate);
					}
				}
			} else if (costamount.compareTo(BigDecimal.ZERO) == 1) {
				baseSalesReportMap.put("realrate",new BigDecimal(100).negate());
			}
			baseSalesReportMap.put("id",(String)baseSalesReportMap.get("customerid"));
			//销售税额
			if (null != (BigDecimal)baseSalesReportMap.get("salenotaxamount") && null != (BigDecimal)baseSalesReportMap.get("saleamount")) {
				baseSalesReportMap.put("saletax",((BigDecimal)baseSalesReportMap.get("saleamount")).subtract((BigDecimal)baseSalesReportMap.get("salenotaxamount")));
			}

			//根据获取的商品编码获取条形码
			if (org.apache.commons.lang.StringUtils.isNotEmpty((String)baseSalesReportMap.get("goodsid"))) {
				GoodsInfo goodsInfo = getGoodsInfoByID((String)baseSalesReportMap.get("goodsid"));
				if (null != goodsInfo) {
					baseSalesReportMap.put("barcode",goodsInfo.getBarcode());
					baseSalesReportMap.put("goodsname",goodsInfo.getName());
					baseSalesReportMap.put("spell",goodsInfo.getSpell());
				}
			}
			//判断是否为导出使用，若为导出，则将直退数量、售后退货数量显示为负数
			if (pageMap.getCondition().containsKey("exportflag") && null != pageMap.getCondition().get("exportflag")) {
				//直退数量
				baseSalesReportMap.put("directreturnnum",((BigDecimal)baseSalesReportMap.get("directreturnnum")).negate());
				//直退箱数
				baseSalesReportMap.put("directreturntotalbox",((BigDecimal)baseSalesReportMap.get("directreturntotalbox")).negate());
				//直退金额
				baseSalesReportMap.put("directreturnamount",((BigDecimal)baseSalesReportMap.get("directreturnamount")).negate());
				//售后退数量
				baseSalesReportMap.put("aftersalereturnnum",((BigDecimal)baseSalesReportMap.get("aftersalereturnnum")).negate());
				//售后退箱数
				baseSalesReportMap.put("aftersalereturntotalbox",((BigDecimal)baseSalesReportMap.get("aftersalereturntotalbox")).negate());
				//售后退金额
				baseSalesReportMap.put("aftersalereturnamount",((BigDecimal)baseSalesReportMap.get("aftersalereturnamount")).negate());
				//售后退货数量
				baseSalesReportMap.put("checkreturnnum",((BigDecimal)baseSalesReportMap.get("checkreturnnum")).negate());
				//售后退货箱数
				baseSalesReportMap.put("checkreturntotalbox",((BigDecimal)baseSalesReportMap.get("checkreturntotalbox")).negate());
				//退货金额
				baseSalesReportMap.put("checkreturnamount",((BigDecimal)baseSalesReportMap.get("checkreturnamount")).negate());
				//退货总数量
				baseSalesReportMap.put("returnnum",((BigDecimal)baseSalesReportMap.get("returnnum")).negate());
				//退货总箱数
				baseSalesReportMap.put("returntotalbox",((BigDecimal)baseSalesReportMap.get("returntotalbox")).negate());
				//退货合计
				baseSalesReportMap.put("returnamount",((BigDecimal)baseSalesReportMap.get("returnamount")).negate());
			}
			if (groupcols.indexOf("customerid") != -1) {
				if (org.apache.commons.lang.StringUtils.isNotEmpty((String) baseSalesReportMap.get("customerid"))) {
					Customer customer = getCustomerByID((String) baseSalesReportMap.get("customerid"));
					if (null != customer) {
						baseSalesReportMap.put("customername",customer.getName());
						baseSalesReportMap.put("shortcode",customer.getShortcode());
					} else {
						baseSalesReportMap.put("customername","其他未定义");
					}
				} else {
					baseSalesReportMap.put("customerid","nodata");
					baseSalesReportMap.put("customername","其他未指定");
				}
				if (groupcols.indexOf("pcustomerid") == -1) {
					Customer pcustomer = getCustomerByID((String)baseSalesReportMap.get("pcustomerid"));
					if (null != pcustomer) {
						baseSalesReportMap.put("pcustomername",pcustomer.getName());
					}
				}
				if (groupcols.indexOf("salesdept") == -1) {
					DepartMent departMent = getDepartmentByDeptid((String)baseSalesReportMap.get("salesdept"));
					if (null != departMent) {
						baseSalesReportMap.put("salesdeptname",departMent.getName());
					}
				}
				if (groupcols.indexOf("customersort") == -1) {
					CustomerSort customerSort = getCustomerSortByID((String)baseSalesReportMap.get("customersort"));
					if (null != customerSort) {
						baseSalesReportMap.put("customersortname",customerSort.getName());
					}
				}
				if (groupcols.indexOf("salesarea") == -1) {
					SalesArea salesArea = getSalesareaByID((String)baseSalesReportMap.get("salesarea"));
					if (null != salesArea) {
						baseSalesReportMap.put("salesareaname",salesArea.getThisname());
					}
				}
				if (groupcols.indexOf("salesuser") == -1) {
					Personnel personnel = getPersonnelById((String)baseSalesReportMap.get("salesuser"));
					if (null != personnel) {
						baseSalesReportMap.put("salesusername",personnel.getName());
					}
					DepartMent departMent = getDepartmentByDeptid((String)baseSalesReportMap.get("salesdept"));
					if (null != departMent) {
						baseSalesReportMap.put("salesdeptname",departMent.getName());
					}
				}
			} else {
				baseSalesReportMap.put("customerid","");
				baseSalesReportMap.put("customername","");
				if (groupcols.indexOf("salesuser") == -1 && groupcols.indexOf("salesdept") == -1) {
					baseSalesReportMap.put("salesdeptname","");
				}
			}
			if (groupcols.indexOf("pcustomerid") != -1) {
				if (org.apache.commons.lang.StringUtils.isNotEmpty((String)baseSalesReportMap.get("pcustomerid"))) {
					Customer pcustomer = getCustomerByID((String)baseSalesReportMap.get("pcustomerid"));
					if (null != pcustomer) {
						baseSalesReportMap.put("pcustomername",pcustomer.getName());
					} else {
						baseSalesReportMap.put("pcustomername","其他未定义");
					}
				} else {
					baseSalesReportMap.put("pcustomerid","nodata");
					baseSalesReportMap.put("pcustomername","其他未指定");
				}
				if (groupcols.indexOf("customerid") == 1) {
					baseSalesReportMap.put("customerid","");
					baseSalesReportMap.put("customername","");
				}
			}
			if (groupcols.indexOf("customersort") != -1) {
				if (org.apache.commons.lang.StringUtils.isNotEmpty((String) baseSalesReportMap.get("customersort"))) {
					CustomerSort customerSort = getCustomerSortByID((String)baseSalesReportMap.get("customersort"));
					if (null != customerSort) {
						baseSalesReportMap.put("customersortname",customerSort.getName());
					} else {
						baseSalesReportMap.put("customersortname","其他未定义");
					}
				} else {
					baseSalesReportMap.put("customersort","nodata");
					baseSalesReportMap.put("customersortname","其他未指定");
				}
			}
			if (groupcols.indexOf("salesarea") != -1) {
				if (org.apache.commons.lang.StringUtils.isNotEmpty((String)baseSalesReportMap.get("salesarea"))) {
					SalesArea salesArea = getSalesareaByID((String)baseSalesReportMap.get("salesarea"));
					if (null != salesArea) {
						baseSalesReportMap.put("salesareaname",salesArea.getThisname());
					} else {
						baseSalesReportMap.put("salesareaname","其他未定义");
					}
				} else {
					baseSalesReportMap.put("salesarea","nodata");
					baseSalesReportMap.put("salesareaname","其他未指定");
				}
			}
			if (groupcols.indexOf("salesdept") != -1) {
				if (org.apache.commons.lang.StringUtils.isNotEmpty((String)baseSalesReportMap.get("salesdept"))) {
					DepartMent departMent = getDepartmentByDeptid((String)baseSalesReportMap.get("salesdept"));
					if (null != departMent) {
						baseSalesReportMap.put("salesdeptname",departMent.getName());
					} else {
						baseSalesReportMap.put("salesdeptname","其他未定义");
					}
				} else {
					baseSalesReportMap.put("salesdept","nodata");
					baseSalesReportMap.put("salesdeptname","其他未指定");
				}
			}
			if (groupcols.indexOf("salesuser") != -1) {
				if (org.apache.commons.lang.StringUtils.isNotEmpty((String)baseSalesReportMap.get("salesuser"))) {
					Personnel personnel = getPersonnelById((String)baseSalesReportMap.get("salesuser"));
					if (null != personnel) {
						baseSalesReportMap.put("salesusername",personnel.getName());
					} else {
						baseSalesReportMap.put("salesusername","其他未定义");
					}
				} else {
					baseSalesReportMap.put("salesuser","nodata");
					baseSalesReportMap.put("salesusername","其他未指定");
				}
				DepartMent departMent = getDepartmentByDeptid((String)baseSalesReportMap.get("salesdept"));
				if (null != departMent) {
					baseSalesReportMap.put("salesdeptname",departMent.getName());
				}
				if (groupcols.indexOf("salesarea") == -1) {
					SalesArea salesArea = getSalesareaByID((String)baseSalesReportMap.get("salesarea"));
					if (null != salesArea) {
						baseSalesReportMap.put("salesareaname",salesArea.getThisname());
					}
				}
			}
			if (groupcols.indexOf("goodsid") != -1) {
				GoodsInfo goodsInfo = getAllGoodsInfoByID((String)baseSalesReportMap.get("goodsid"));
				if (null != goodsInfo) {
					baseSalesReportMap.put("goodsname",goodsInfo.getName());
					baseSalesReportMap.put("spell",goodsInfo.getSpell());
					baseSalesReportMap.put("boxnum",goodsInfo.getBoxnum());
					WaresClass waresClass = getBaseGoodsMapper().getWaresClassInfo((String)baseSalesReportMap.get("goodssort"));
					if (null != waresClass) {
						baseSalesReportMap.put("goodssortname",waresClass.getThisname());
					}
					if (org.apache.commons.lang.StringUtils.isNotEmpty(goodsInfo.getGoodstype())) {
						baseSalesReportMap.put("goodstype",goodsInfo.getGoodstype());
						SysCode goodstype = getBaseSysCodeInfo(goodsInfo.getGoodstype(), "goodstype");
						if (null != goodstype) {
							baseSalesReportMap.put("goodstypename",goodstype.getCodename());
						}
					}
					BuySupplier buySupplier = getSupplierInfoById((String)baseSalesReportMap.get("supplierid"));
					if (null != buySupplier) {
						baseSalesReportMap.put("suppliername",buySupplier.getName());
					}
				} else {
					Brand brand = getGoodsBrandByID((String) baseSalesReportMap.get("goodsid"));
					if (null != brand) {
						baseSalesReportMap.put("goodsname","（折扣）" + brand.getName());
					} else {
						baseSalesReportMap.put("goodsname","（折扣）其他");
					}
				}
				if (groupcols.indexOf("brandid") == -1) {
					Brand brand = getGoodsBrandByID((String)baseSalesReportMap.get("brandid"));
					if (null != brand) {
						baseSalesReportMap.put("brandname",brand.getName());
					}
					Personnel personnel = getPersonnelById((String)baseSalesReportMap.get("branduser"));
					if (null != personnel) {
						baseSalesReportMap.put("brandusername",personnel.getName());
					}
					if ("QC".equals((String)baseSalesReportMap.get("supplieruser"))) {
						baseSalesReportMap.put("supplierusername","期初");
					} else {
						Personnel personnel2 = getPersonnelById((String)baseSalesReportMap.get("supplieruser"));
						if (null != personnel2) {
							baseSalesReportMap.put("supplierusername",personnel2.getName());
						}
					}
				}
				if (groupcols.indexOf("branddept") == -1) {
					DepartMent departMent = getDepartmentByDeptid((String)baseSalesReportMap.get("branddept"));
					if (null != departMent) {
						baseSalesReportMap.put("branddeptname",departMent.getName());
					}
				}
				if (groupcols.indexOf("branduser") == -1) {
					Personnel personnel = getPersonnelById((String)baseSalesReportMap.get("branduser"));
					if (null != personnel) {
						baseSalesReportMap.put("brandusername",personnel.getName());
					}
				}
			}
			if (groupcols.indexOf("goodssort") != -1) {
				if (org.apache.commons.lang.StringUtils.isNotEmpty((String)baseSalesReportMap.get("goodssort"))) {
					WaresClass waresClass = getBaseGoodsMapper().getWaresClassInfo((String)baseSalesReportMap.get("goodssort"));
					if (null != waresClass) {
						baseSalesReportMap.put("goodssortname",waresClass.getThisname());
					} else {
						baseSalesReportMap.put("goodssortname","其他未定义");
					}
				} else {
					baseSalesReportMap.put("goodssort","nodata");
					baseSalesReportMap.put("goodssortname","其他未指定");
				}
			}
			if (groupcols.indexOf("brandid") != -1) {
				if (org.apache.commons.lang.StringUtils.isNotEmpty((String)baseSalesReportMap.get("brandid"))) {
					Brand brand = getGoodsBrandByID((String)baseSalesReportMap.get("brandid"));
					if (null != brand) {
						baseSalesReportMap.put("brandname",brand.getName());
					} else {
						baseSalesReportMap.put("brandname","其他未定义");
					}
				} else {
					baseSalesReportMap.put("brandid","nodata");
					baseSalesReportMap.put("brandname","其他未指定");
				}
				if (groupcols.indexOf("branddept") == -1) {
					DepartMent departMent = getDepartmentByDeptid((String) baseSalesReportMap.get("branddept"));
					if (null != departMent) {
						baseSalesReportMap.put("branddeptname",departMent.getName());
					}
				}
				Personnel personnel = getPersonnelById((String) baseSalesReportMap.get("branduser"));
				if (null != personnel) {
					baseSalesReportMap.put("brandusername",personnel.getName());
				}
				if ("QC".equals((String)baseSalesReportMap.get("supplieruser"))) {
					baseSalesReportMap.put("supplierusername","期初");
				} else {
					Personnel personnel2 = getPersonnelById((String) baseSalesReportMap.get("supplieruser"));
					if (null != personnel2) {
						baseSalesReportMap.put("supplierusername",personnel2.getName());
					}
				}
			}
			if (groupcols.indexOf("branduser") != -1) {
				if (org.apache.commons.lang.StringUtils.isNotEmpty((String)baseSalesReportMap.get("branduser"))) {
					Personnel personnel = getPersonnelById((String)baseSalesReportMap.get("branduser"));
					if (null != personnel) {
						baseSalesReportMap.put("brandusername",personnel.getName());
					} else {
						baseSalesReportMap.put("brandusername","其他未定义");
					}
				} else {
					baseSalesReportMap.put("branduser","nodata");
					baseSalesReportMap.put("brandusername","其他未指定");
				}
				if (groupcols.indexOf("branddept") == -1) {
					//分公司
					Map map2 = new HashMap();
					map2.put("pid", "");
					List<DepartMent> deptList = getBaseDepartMentMapper().getDeptListByParam(map2);
					for (DepartMent dept : deptList) {
						if (((String)baseSalesReportMap.get("branddept")).indexOf(dept.getId()) == 0) {
							baseSalesReportMap.put("branddeptname",dept.getName());
						}
					}
				}
			}
			if (groupcols.indexOf("branddept") != -1) {
				if (org.apache.commons.lang.StringUtils.isNotEmpty((String)baseSalesReportMap.get("branddept"))) {
					DepartMent departMent = getDepartmentByDeptid((String)baseSalesReportMap.get("branddept"));
					if (null != departMent) {
						baseSalesReportMap.put("branddeptname",departMent.getName());
					} else {
						baseSalesReportMap.put("branddeptname","其他未定义");
					}
				} else {
					baseSalesReportMap.put("branddept","nodata");
					baseSalesReportMap.put("branddeptname","其他未指定");
				}
			}
			if (groupcols.indexOf("supplierid") != -1) {
				if (org.apache.commons.lang.StringUtils.isNotEmpty((String)baseSalesReportMap.get("supplierid"))) {
					BuySupplier supplier = getSupplierInfoById((String)baseSalesReportMap.get("supplierid"));
					if (null != supplier) {
						baseSalesReportMap.put("suppliername",supplier.getName());
					} else {
						baseSalesReportMap.put("suppliername","其他未定义");
					}
				} else {
					baseSalesReportMap.put("supplierid","nodata");
					baseSalesReportMap.put("suppliername","其他未指定");
				}
			}
			if (groupcols.indexOf("supplieruser") != -1) {
				if (org.apache.commons.lang.StringUtils.isNotEmpty((String) baseSalesReportMap.get("supplieruser"))) {
					if ("QC".equals((String) baseSalesReportMap.get("supplieruser"))) {
						baseSalesReportMap.put("supplierusername","期初");
					} else {
						Personnel personnel2 = getPersonnelById((String)baseSalesReportMap.get("supplieruser"));
						if (null != personnel2) {
							baseSalesReportMap.put("supplierusername",personnel2.getName());
						} else {
							baseSalesReportMap.put("supplierusername","其他未定义");
						}
					}
				} else {
					baseSalesReportMap.put("supplieruser","nodata");
					baseSalesReportMap.put("supplierusername","其他未指定");
				}
			}
			if (groupcols.indexOf("storageid") != -1) {
				if (org.apache.commons.lang.StringUtils.isNotEmpty((String)baseSalesReportMap.get("storageid"))) {
					StorageInfo storageInfo = getStorageInfoByID((String)baseSalesReportMap.get("storageid"));
					if (null != storageInfo) {
						baseSalesReportMap.put("storagename",storageInfo.getName());
					} else {
						baseSalesReportMap.put("storagename","其他未定义");
					}
				} else {
					baseSalesReportMap.put("storagename","nodata");
					baseSalesReportMap.put("storagename","其他未指定");
				}
			}
		}
		int count = salesCustomerReportMapper.showBaseSalesAnalysisReportDataCount(pageMap);
		PageData pageData = new PageData(count, list, pageMap);
		//取合计数据
		pageMap.getCondition().put("groupcols", "all");
		List<Map> footer = salesCustomerReportMapper.showBaseSalesAnalysisReportData(pageMap);
		footer = returnBaseSaleAnalysisReportFooter(footer, pageMap, groupcols);
		pageData.setFooter(footer);
		return pageData;
	}
	/**
	 * 获取基础统计报表的页脚数据合计
	 * @param footer
	 * @param pageMap
	 * @param groupcols
	 * @return
	 * @throws Exception
	 */
	public List<Map> returnBaseSaleAnalysisReportFooter(List<Map> footer, PageMap pageMap , String groupcols ) throws Exception {

		for (Map baseSalesReportMap : footer) {
			if (null != baseSalesReportMap) {
				baseSalesReportMap.put("isfoot",true);
				BigDecimal sendnum = null != (BigDecimal)baseSalesReportMap.get("sendnum") ? (BigDecimal)baseSalesReportMap.get("sendnum") : BigDecimal.ZERO;
				BigDecimal checkreturnnum = null != (BigDecimal)baseSalesReportMap.get("checkreturnnum") ? (BigDecimal)baseSalesReportMap.get("checkreturnnum")  : BigDecimal.ZERO;
				BigDecimal returnnum = null != (BigDecimal)baseSalesReportMap.get("returnnum") ? (BigDecimal)baseSalesReportMap.get("returnnum") : BigDecimal.ZERO;
				BigDecimal sendtotalbox = null != (BigDecimal)baseSalesReportMap.get("sendtotalbox") ? (BigDecimal)baseSalesReportMap.get("sendtotalbox") : BigDecimal.ZERO;
				BigDecimal returntotalbox = null != (BigDecimal)baseSalesReportMap.get("returntotalbox") ? (BigDecimal)baseSalesReportMap.get("returntotalbox") : BigDecimal.ZERO;
				BigDecimal sendamount = null != (BigDecimal)baseSalesReportMap.get("sendamount") ? (BigDecimal)baseSalesReportMap.get("sendamount") : BigDecimal.ZERO;
				BigDecimal returnamount = null != (BigDecimal)baseSalesReportMap.get("returnamount") ? (BigDecimal)baseSalesReportMap.get("returnamount") : BigDecimal.ZERO;
				BigDecimal pushbalanceamount = null != (BigDecimal)baseSalesReportMap.get("pushbalanceamount") ? (BigDecimal)baseSalesReportMap.get("pushbalanceamount") : BigDecimal.ZERO;
				BigDecimal pushbalancenotaxamount = null != (BigDecimal)baseSalesReportMap.get("pushbalancenotaxamount") ? (BigDecimal)baseSalesReportMap.get("pushbalancenotaxamount") : BigDecimal.ZERO;
				BigDecimal sendnotaxamount = null != (BigDecimal)baseSalesReportMap.get("sendnotaxamount") ? (BigDecimal)baseSalesReportMap.get("sendnotaxamount") : BigDecimal.ZERO;
				BigDecimal returnnotaxamount = null != (BigDecimal)baseSalesReportMap.get("returnnotaxamount") ? (BigDecimal)baseSalesReportMap.get("returnnotaxamount") : BigDecimal.ZERO;
				BigDecimal costamount = null != (BigDecimal)baseSalesReportMap.get("costamount") ? (BigDecimal)baseSalesReportMap.get("costamount") : BigDecimal.ZERO;
				BigDecimal saleamount=sendamount.subtract(returnamount).add(pushbalanceamount);
				BigDecimal checkreturnamount=null!=(BigDecimal)baseSalesReportMap.get("checkreturnamount") ? (BigDecimal)baseSalesReportMap.get("checkreturnamount") : BigDecimal.ZERO;

				//退货率=退货金额(不包括直退)/销售金额
				if (saleamount.compareTo(BigDecimal.ZERO) != 0) {
					BigDecimal checkreturnrate = checkreturnamount.divide(saleamount, 6, BigDecimal.ROUND_HALF_UP);
					baseSalesReportMap.put("checkreturnrate",checkreturnrate.multiply(new BigDecimal(100)));
				}
				//销售数量 = 发货单数量 - 直退数量
				baseSalesReportMap.put("salenum",sendnum.subtract(returnnum));
				//销售箱数
				baseSalesReportMap.put("saletotalbox",sendtotalbox.subtract(returntotalbox));
				baseSalesReportMap.put("saleamount",sendamount.subtract(returnamount).add(pushbalanceamount));
				baseSalesReportMap.put("salenotaxamount",sendnotaxamount.subtract(returnnotaxamount).add(pushbalancenotaxamount));

				if (null != (BigDecimal)baseSalesReportMap.get("saleamount")) {
					//毛利额 = 销售金额 - 成本金额
					baseSalesReportMap.put("salemarginamount",((BigDecimal)baseSalesReportMap.get("saleamount")).subtract(costamount));
					//实际毛利率 = （销售金额 - 成本金额）/销售金额*100
					if (((BigDecimal)baseSalesReportMap.get("saleamount")).compareTo(BigDecimal.ZERO) != 0) {
						BigDecimal realrate = ((BigDecimal)baseSalesReportMap.get("saleamount")).subtract(costamount).divide((BigDecimal)baseSalesReportMap.get("saleamount"), 6, BigDecimal.ROUND_HALF_UP);
						realrate = realrate.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
						if (((BigDecimal)baseSalesReportMap.get("saleamount")).compareTo(BigDecimal.ZERO) == -1) {
							baseSalesReportMap.put("realrate",realrate.negate());
						} else {
							baseSalesReportMap.put("realrate",realrate);
						}
					}
				} else if (costamount.compareTo(BigDecimal.ZERO) == 1) {
					baseSalesReportMap.put("realrate",new BigDecimal(100).negate());
				}
				baseSalesReportMap.put("id",(String)baseSalesReportMap.get("customerid"));

				//销售税额
				if (null != (BigDecimal)baseSalesReportMap.get("salenotaxamount") && null != (BigDecimal)baseSalesReportMap.get("saleamount")) {
					baseSalesReportMap.put("saletax",((BigDecimal)baseSalesReportMap.get("saleamount")).subtract((BigDecimal)baseSalesReportMap.get("salenotaxamount")));
				}
				//判断是否为导出使用，若为导出，则将直退数量、售后退货数量显示为负数
				if (pageMap.getCondition().containsKey("exportflag") && null != pageMap.getCondition().get("exportflag")) {
					//直退数量
					baseSalesReportMap.put("directreturnnum",((BigDecimal)baseSalesReportMap.get("directreturnnum")).negate());
					//直退箱数
					baseSalesReportMap.put("directreturntotalbox",((BigDecimal)baseSalesReportMap.get("directreturntotalbox")).negate());
					//直退金额
					baseSalesReportMap.put("directreturnamount",((BigDecimal)baseSalesReportMap.get("directreturnamount")).negate());
					//售后退数量
					baseSalesReportMap.put("aftersalereturnnum",((BigDecimal)baseSalesReportMap.get("aftersalereturnnum")).negate());
					//售后退箱数
					baseSalesReportMap.put("aftersalereturntotalbox",((BigDecimal)baseSalesReportMap.get("aftersalereturntotalbox")).negate());
					//售后退金额
					baseSalesReportMap.put("aftersalereturnamount",((BigDecimal)baseSalesReportMap.get("aftersalereturnamount")).negate());
					//售后退货数量
					baseSalesReportMap.put("checkreturnnum",((BigDecimal)baseSalesReportMap.get("checkreturnnum")).negate());
					//售后退货箱数
					baseSalesReportMap.put("checkreturntotalbox",((BigDecimal)baseSalesReportMap.get("checkreturntotalbox")).negate());
					//退货金额
					baseSalesReportMap.put("checkreturnamount",((BigDecimal)baseSalesReportMap.get("checkreturnamount")).negate());
					//退货总数量
					baseSalesReportMap.put("returnnum",((BigDecimal)baseSalesReportMap.get("returnnum")).negate());
					//退货总箱数
					baseSalesReportMap.put("returntotalbox",((BigDecimal)baseSalesReportMap.get("returntotalbox")).negate());
					//退货合计
					baseSalesReportMap.put("returnamount",((BigDecimal)baseSalesReportMap.get("returnamount")).negate());
				}

				baseSalesReportMap.put("auxunitname","");
				baseSalesReportMap.put("unitname","");
				String[] groupArr = groupcols.split(",");
				if (groupArr[0].indexOf("branddept") != -1) {
					baseSalesReportMap.put("pcustomerid","");
					baseSalesReportMap.put("pcustomername","");
					baseSalesReportMap.put("branddeptname","合计");
					baseSalesReportMap.put("branddept","");
					baseSalesReportMap.put("brandid","");
					baseSalesReportMap.put("brandname","");
					baseSalesReportMap.put("salesdept","");
					baseSalesReportMap.put("salesdeptname","");
					baseSalesReportMap.put("salesuser","");
					baseSalesReportMap.put("salesusername","");
					baseSalesReportMap.put("goodsid","");
					baseSalesReportMap.put("goodsname","");
					baseSalesReportMap.put("goodssortname","");
					baseSalesReportMap.put("goodstypename","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("customerid","");
					baseSalesReportMap.put("customername","");
					baseSalesReportMap.put("customersort","");
					baseSalesReportMap.put("customersortname","");
					baseSalesReportMap.put("supplierid","");
					baseSalesReportMap.put("suppliername","");
					baseSalesReportMap.put("supplieruser","");
					baseSalesReportMap.put("supplierusername","");
					baseSalesReportMap.put("addusername","");
					baseSalesReportMap.put("storageid","");
					baseSalesReportMap.put("storagename","");
				} else if (groupArr[0].indexOf("branduser") != -1) {
					baseSalesReportMap.put("pcustomerid","");
					baseSalesReportMap.put("pcustomername","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","合计");
					baseSalesReportMap.put("branddept","");
					baseSalesReportMap.put("brandid","");
					baseSalesReportMap.put("brandname","");
					baseSalesReportMap.put("salesdept","");
					baseSalesReportMap.put("salesdeptname","");
					baseSalesReportMap.put("salesuser","");
					baseSalesReportMap.put("salesusername","");
					baseSalesReportMap.put("goodsid","");
					baseSalesReportMap.put("goodsname","");
					baseSalesReportMap.put("goodssortname","");
					baseSalesReportMap.put("goodstypename","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("customerid","");
					baseSalesReportMap.put("customername","");
					baseSalesReportMap.put("customersort","");
					baseSalesReportMap.put("customersortname","");
					baseSalesReportMap.put("supplierid","");
					baseSalesReportMap.put("suppliername","");
					baseSalesReportMap.put("supplieruser","");
					baseSalesReportMap.put("supplierusername","");
					baseSalesReportMap.put("addusername","");
					baseSalesReportMap.put("storageid","");
					baseSalesReportMap.put("storagename","");
				} else if (groupArr[0].indexOf("brandid") != -1) {
					baseSalesReportMap.put("pcustomerid","");
					baseSalesReportMap.put("pcustomername","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("branddept","");
					baseSalesReportMap.put("brandid","");
					baseSalesReportMap.put("brandname","合计");
					baseSalesReportMap.put("salesdept","");
					baseSalesReportMap.put("salesdeptname","");
					baseSalesReportMap.put("salesuser","");
					baseSalesReportMap.put("salesusername","");
					baseSalesReportMap.put("goodsid","");
					baseSalesReportMap.put("goodsname","");
					baseSalesReportMap.put("goodssortname","");
					baseSalesReportMap.put("goodstypename","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("customerid","");
					baseSalesReportMap.put("customername","");
					baseSalesReportMap.put("customersort","");
					baseSalesReportMap.put("customersortname","");
					baseSalesReportMap.put("supplierid","");
					baseSalesReportMap.put("suppliername","");
					baseSalesReportMap.put("supplieruser","");
					baseSalesReportMap.put("supplierusername","");
					baseSalesReportMap.put("addusername","");
					baseSalesReportMap.put("storageid","");
					baseSalesReportMap.put("storagename","");

				} else if (groupArr[0].indexOf("salesdept") != -1) {
					baseSalesReportMap.put("pcustomerid","");
					baseSalesReportMap.put("pcustomername","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("branddept","");
					baseSalesReportMap.put("brandid","");
					baseSalesReportMap.put("brandname","");
					baseSalesReportMap.put("salesdept","");
					baseSalesReportMap.put("salesdeptname","合计");
					baseSalesReportMap.put("salesuser","");
					baseSalesReportMap.put("salesusername","");
					baseSalesReportMap.put("goodsid","");
					baseSalesReportMap.put("goodsname","");
					baseSalesReportMap.put("goodssortname","");
					baseSalesReportMap.put("goodstypename","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("customerid","");
					baseSalesReportMap.put("customername","");
					baseSalesReportMap.put("customersort","");
					baseSalesReportMap.put("customersortname","");
					baseSalesReportMap.put("supplierid","");
					baseSalesReportMap.put("suppliername","");
					baseSalesReportMap.put("supplieruser","");
					baseSalesReportMap.put("supplierusername","");
					baseSalesReportMap.put("addusername","");
					baseSalesReportMap.put("storageid","");
					baseSalesReportMap.put("storagename","");

				} else if (groupArr[0].indexOf("salesuser") != -1) {
					baseSalesReportMap.put("pcustomerid","");
					baseSalesReportMap.put("pcustomername","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("branddept","");
					baseSalesReportMap.put("brandid","");
					baseSalesReportMap.put("brandname","");
					baseSalesReportMap.put("salesdept","");
					baseSalesReportMap.put("salesdeptname","");
					baseSalesReportMap.put("salesuser","");
					baseSalesReportMap.put("salesusername","合计");
					baseSalesReportMap.put("goodsid","");
					baseSalesReportMap.put("goodsname","");
					baseSalesReportMap.put("goodssortname","");
					baseSalesReportMap.put("goodstypename","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("customerid","");
					baseSalesReportMap.put("customername","");
					baseSalesReportMap.put("customersort","");
					baseSalesReportMap.put("customersortname","");
					baseSalesReportMap.put("supplierid","");
					baseSalesReportMap.put("suppliername","");
					baseSalesReportMap.put("supplieruser","");
					baseSalesReportMap.put("supplierusername","");
					baseSalesReportMap.put("addusername","");
					baseSalesReportMap.put("storageid","");
					baseSalesReportMap.put("storagename","");

				} else if (groupArr[0].indexOf("goodsid") != -1) {
					baseSalesReportMap.put("pcustomerid","");
					baseSalesReportMap.put("pcustomername","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("branddept","");
					baseSalesReportMap.put("brandid","");
					baseSalesReportMap.put("brandname","");
					baseSalesReportMap.put("salesdept","");
					baseSalesReportMap.put("salesdeptname","");
					baseSalesReportMap.put("salesuser","");
					baseSalesReportMap.put("salesusername","");
					baseSalesReportMap.put("goodsid","");
					baseSalesReportMap.put("goodsname","合计");
					baseSalesReportMap.put("goodssortname","");
					baseSalesReportMap.put("goodstypename","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("customerid","");
					baseSalesReportMap.put("customername","");
					baseSalesReportMap.put("customersort","");
					baseSalesReportMap.put("customersortname","");
					baseSalesReportMap.put("supplierid","");
					baseSalesReportMap.put("suppliername","");
					baseSalesReportMap.put("supplieruser","");
					baseSalesReportMap.put("supplierusername","");
					baseSalesReportMap.put("addusername","");
					baseSalesReportMap.put("storageid","");
					baseSalesReportMap.put("storagename","");

				} else if (groupArr[0].indexOf("goodssort") != -1) {
					baseSalesReportMap.put("pcustomerid","");
					baseSalesReportMap.put("pcustomername","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("branddept","");
					baseSalesReportMap.put("brandid","");
					baseSalesReportMap.put("brandname","");
					baseSalesReportMap.put("salesdept","");
					baseSalesReportMap.put("salesdeptname","");
					baseSalesReportMap.put("salesuser","");
					baseSalesReportMap.put("salesusername","");
					baseSalesReportMap.put("goodsid","");
					baseSalesReportMap.put("goodsname","");
					baseSalesReportMap.put("goodssortname","合计");
					baseSalesReportMap.put("goodstypename","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("customerid","");
					baseSalesReportMap.put("customername","");
					baseSalesReportMap.put("customersort","");
					baseSalesReportMap.put("customersortname","");
					baseSalesReportMap.put("supplierid","");
					baseSalesReportMap.put("suppliername","");
					baseSalesReportMap.put("supplieruser","");
					baseSalesReportMap.put("supplierusername","");
					baseSalesReportMap.put("addusername","");
					baseSalesReportMap.put("storageid","");
					baseSalesReportMap.put("storagename","");
				} else if (groupArr[0].indexOf("pcustomerid") != -1) {
					baseSalesReportMap.put("pcustomerid","");
					baseSalesReportMap.put("pcustomername","合计");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("branddept","");
					baseSalesReportMap.put("brandid","");
					baseSalesReportMap.put("brandname","");
					baseSalesReportMap.put("salesdept","");
					baseSalesReportMap.put("salesdeptname","");
					baseSalesReportMap.put("salesuser","");
					baseSalesReportMap.put("salesusername","");
					baseSalesReportMap.put("goodsid","");
					baseSalesReportMap.put("goodsname","");
					baseSalesReportMap.put("goodssortname","");
					baseSalesReportMap.put("goodstypename","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("customerid","");
					baseSalesReportMap.put("customername","");
					baseSalesReportMap.put("customersort","");
					baseSalesReportMap.put("customersortname","");
					baseSalesReportMap.put("supplierid","");
					baseSalesReportMap.put("suppliername","");
					baseSalesReportMap.put("supplieruser","");
					baseSalesReportMap.put("supplierusername","");
					baseSalesReportMap.put("addusername","");
					baseSalesReportMap.put("storageid","");
					baseSalesReportMap.put("storagename","");

				} else if (groupArr[0].indexOf("customerid") != -1) {
					baseSalesReportMap.put("pcustomerid","");
					baseSalesReportMap.put("pcustomername","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("branddept","");
					baseSalesReportMap.put("brandid","");
					baseSalesReportMap.put("brandname","");
					baseSalesReportMap.put("salesdept","");
					baseSalesReportMap.put("salesdeptname","");
					baseSalesReportMap.put("salesuser","");
					baseSalesReportMap.put("salesusername","");
					baseSalesReportMap.put("goodsid","");
					baseSalesReportMap.put("goodsname","");
					baseSalesReportMap.put("goodssortname","");
					baseSalesReportMap.put("goodstypename","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("customerid","");
					baseSalesReportMap.put("customername","合计");
					baseSalesReportMap.put("customersort","");
					baseSalesReportMap.put("customersortname","");
					baseSalesReportMap.put("supplierid","");
					baseSalesReportMap.put("suppliername","");
					baseSalesReportMap.put("supplieruser","");
					baseSalesReportMap.put("supplierusername","");
					baseSalesReportMap.put("addusername","");
					baseSalesReportMap.put("storageid","");
					baseSalesReportMap.put("storagename","");

				} else if (groupArr[0].indexOf("customersort") != -1) {
					baseSalesReportMap.put("pcustomerid","");
					baseSalesReportMap.put("pcustomername","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("branddept","");
					baseSalesReportMap.put("brandid","");
					baseSalesReportMap.put("brandname","");
					baseSalesReportMap.put("salesdept","");
					baseSalesReportMap.put("salesdeptname","");
					baseSalesReportMap.put("salesuser","");
					baseSalesReportMap.put("salesusername","");
					baseSalesReportMap.put("goodsid","");
					baseSalesReportMap.put("goodsname","");
					baseSalesReportMap.put("goodssortname","");
					baseSalesReportMap.put("goodstypename","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("customerid","");
					baseSalesReportMap.put("customername","");
					baseSalesReportMap.put("customersort","");
					baseSalesReportMap.put("customersortname","合计");
					baseSalesReportMap.put("supplierid","");
					baseSalesReportMap.put("suppliername","");
					baseSalesReportMap.put("supplieruser","");
					baseSalesReportMap.put("supplierusername","");
					baseSalesReportMap.put("addusername","");
					baseSalesReportMap.put("storageid","");
					baseSalesReportMap.put("storagename","");

				} else if (groupArr[0].indexOf("supplierid") != -1) {
					baseSalesReportMap.put("pcustomerid","");
					baseSalesReportMap.put("pcustomername","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("branddept","");
					baseSalesReportMap.put("brandid","");
					baseSalesReportMap.put("brandname","");
					baseSalesReportMap.put("salesdept","");
					baseSalesReportMap.put("salesdeptname","");
					baseSalesReportMap.put("salesuser","");
					baseSalesReportMap.put("salesusername","");
					baseSalesReportMap.put("goodsid","");
					baseSalesReportMap.put("goodsname","");
					baseSalesReportMap.put("goodssortname","");
					baseSalesReportMap.put("goodstypename","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("customerid","");
					baseSalesReportMap.put("customername","");
					baseSalesReportMap.put("customersort","");
					baseSalesReportMap.put("customersortname","");
					baseSalesReportMap.put("supplierid","");
					baseSalesReportMap.put("suppliername","合计");
					baseSalesReportMap.put("supplieruser","");
					baseSalesReportMap.put("supplierusername","");
					baseSalesReportMap.put("addusername","");
					baseSalesReportMap.put("storageid","");
					baseSalesReportMap.put("storagename","");

				} else if (groupArr[0].indexOf("supplieruser") != -1) {
					baseSalesReportMap.put("pcustomerid","");
					baseSalesReportMap.put("pcustomername","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("branddept","");
					baseSalesReportMap.put("brandid","");
					baseSalesReportMap.put("brandname","");
					baseSalesReportMap.put("salesdept","");
					baseSalesReportMap.put("salesdeptname","");
					baseSalesReportMap.put("salesuser","");
					baseSalesReportMap.put("salesusername","");
					baseSalesReportMap.put("goodsid","");
					baseSalesReportMap.put("goodsname","");
					baseSalesReportMap.put("goodssortname","");
					baseSalesReportMap.put("goodstypename","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("customerid","");
					baseSalesReportMap.put("customername","");
					baseSalesReportMap.put("customersort","");
					baseSalesReportMap.put("customersortname","");
					baseSalesReportMap.put("supplierid","");
					baseSalesReportMap.put("suppliername","");
					baseSalesReportMap.put("supplieruser","");
					baseSalesReportMap.put("supplierusername","合计");
					baseSalesReportMap.put("addusername","");
					baseSalesReportMap.put("storageid","");
					baseSalesReportMap.put("storagename","");

				} else if (groupArr[0].indexOf("adduserid") != -1) {
					baseSalesReportMap.put("pcustomerid","");
					baseSalesReportMap.put("pcustomername","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("branddept","");
					baseSalesReportMap.put("brandid","");
					baseSalesReportMap.put("brandname","");
					baseSalesReportMap.put("salesdept","");
					baseSalesReportMap.put("salesdeptname","");
					baseSalesReportMap.put("salesuser","");
					baseSalesReportMap.put("salesusername","");
					baseSalesReportMap.put("goodsid","");
					baseSalesReportMap.put("goodsname","");
					baseSalesReportMap.put("goodssortname","");
					baseSalesReportMap.put("goodstypename","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("customerid","");
					baseSalesReportMap.put("customername","");
					baseSalesReportMap.put("customersort","");
					baseSalesReportMap.put("customersortname","");
					baseSalesReportMap.put("supplierid","");
					baseSalesReportMap.put("suppliername","");
					baseSalesReportMap.put("supplieruser","");
					baseSalesReportMap.put("supplierusername","");
					baseSalesReportMap.put("addusername","合计");
					baseSalesReportMap.put("storageid","");
					baseSalesReportMap.put("storagename","");
				} else if (groupArr[0].indexOf("storageid") != -1) {
					baseSalesReportMap.put("pcustomerid","");
					baseSalesReportMap.put("pcustomername","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("branddept","");
					baseSalesReportMap.put("brandid","");
					baseSalesReportMap.put("brandname","");
					baseSalesReportMap.put("salesdept","");
					baseSalesReportMap.put("salesdeptname","");
					baseSalesReportMap.put("salesuser","");
					baseSalesReportMap.put("salesusername","");
					baseSalesReportMap.put("goodsid","");
					baseSalesReportMap.put("goodsname","");
					baseSalesReportMap.put("goodssortname","");
					baseSalesReportMap.put("goodstypename","");
					baseSalesReportMap.put("branduser","");
					baseSalesReportMap.put("brandusername","");
					baseSalesReportMap.put("customerid","");
					baseSalesReportMap.put("customername","");
					baseSalesReportMap.put("customersort","");
					baseSalesReportMap.put("customersortname","");
					baseSalesReportMap.put("supplierid","");
					baseSalesReportMap.put("suppliername","");
					baseSalesReportMap.put("supplieruser","");
					baseSalesReportMap.put("supplierusername","");
					baseSalesReportMap.put("addusername","");
					baseSalesReportMap.put("storageid","");
					baseSalesReportMap.put("storagename","合计");

				}
			} else {
				footer = new ArrayList<Map>();
			}
		}
		return  footer ;
	}

	/**
	 * 获取费用科目明细
	 * @param pushtype
	 * @param groupcolList
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2017-12-26
	 */
	@Override
	public List<Map> showSalesAnalysisReportSubjectDetailPage(String pushtype,List<Map> groupcolList,String businessdate1,String businessdate2) throws Exception{
		String query_sql = "1 = 1";
		if(org.apache.commons.lang.StringUtils.isNotEmpty(businessdate1)){
			query_sql += " and t.businessdate >= '" + businessdate1 + "'";
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(businessdate2)){
			query_sql += " and t.businessdate <= '" + businessdate2 + "'";
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(pushtype)){
			query_sql += " and t.pushtype  = '" + pushtype + "'";
		}
		for(Map groupcol : groupcolList){
			String col = (String)groupcol.get("col");
			String value = (String)groupcol.get("value");
			if("customerid".equals(col)){
				query_sql += " and t.customerid  = '" + value + "'";
			}else if("pcustomerid".equals(col)){
				query_sql += " and t.pcustomerid  = '" + value + "'";
			}else if("salesuser".equals(col)){
				query_sql += " and t.salesuser  = '" + value + "'";
			}else if("salesarea".equals(col)){
				query_sql += " and t.salesarea  = '" + value + "'";
			}else if("salesdept".equals(col)){
				query_sql += " and t.salesdept  = '" + value + "'";
			}else if("goodsid".equals(col)){
				query_sql += " and t.brandid  = '" + value + "'";
			}else if("goodssort".equals(col)){
				query_sql += " and t.goodssort  = '" + value + "'";
			}else if("brandid".equals(col)){
				query_sql += " and t.brandid  = '" + value + "'";
			}else if("branduser".equals(col)){
				query_sql += " and t.branduser  = '" + value + "'";
			}else if("branddept".equals(col)){
				query_sql += " and t.branddept  = '" + value + "'";
			}else if("customersort".equals(col)){
				query_sql += " and t.customersort  = '" + value + "'";
			}else if("supplierid".equals(col)){
				query_sql += " and t.supplierid  = '" + value + "'";
			}else if("supplieruser".equals(col)){
				query_sql += " and t.supplieruser  = '" + value + "'";
			}else if("storageid".equals(col)){
				query_sql += " and t.storageid  = '" + value + "'";
			}
		}
		Map queryMap = new HashMap();
		queryMap.put("pushtype",pushtype);
		queryMap.put("businessdate1",businessdate1);
		queryMap.put("businessdate2",businessdate2);
		queryMap.put("query_sql",query_sql);
		List<Map> list = salesCustomerReportMapper.showSalesAnalysisReportSubjectDetailPage(queryMap);
		for(Map map : list){
			String subject = (String)map.get("subject");
			if(org.apache.commons.lang.StringUtils.isNotEmpty(subject)){
				ExpensesSort expensesSort =getExpensesSortByID(subject);
				if(null!=expensesSort){
					map.put("subjectname",expensesSort.getName());
				}
			}else{
				map.put("subjectname","未知科目");
			}
		}
		return list;
	}

	/**
	 * 德阳销售情况统计报表数据
	 * @param pageMap
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author luoqiang
	 * @date Feb 05, 2018
	 */
	public PageData getDyBaseSalesReportData(PageMap pageMap) throws Exception{
		//数据权限类型
		String datasqltype = null != pageMap.getCondition().get("datasqltype") ? (String) pageMap.getCondition().get("datasqltype") : "";
		//小计列
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		if (!pageMap.getCondition().containsKey("groupcols")) {
			groupcols = "customerid";
			pageMap.getCondition().put("groupcols", groupcols);
		}
		if (datasqltype.equals("adduserid")) {
			String dataSql = getDataAccessRule("t_report_adduser_base", "z");
			pageMap.setDataSql(dataSql);
		} else {
			String dataSql = getDataAccessRule("t_report_sales_base", "z");
			pageMap.setDataSql(dataSql);
		}
		String query_sql = " 1=1 ";
		String query_sqlall = " 1=1 ";
		String query_sql_push = "";
		if (pageMap.getCondition().containsKey("goodsid")) {
			String str = (String) pageMap.getCondition().get("goodsid");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				query_sql += " and t1.goodsid = '" + str + "' ";
				query_sqlall += " and t1.goodsid = '" + str + "' ";
			} else {
				query_sql += " and FIND_IN_SET(t1.goodsid,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t1.goodsid,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("brandid")) {
			String str = (String) pageMap.getCondition().get("brandid");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t1.brandid = '" + str + "' ";
					query_sqlall += " and t1.brandid = '" + str + "' ";
				} else {
					query_sql += " and (t1.brandid is null or t1.brandid = '')";
					query_sqlall += " and (t1.brandid is null or t1.brandid = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t1.brandid,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t1.brandid,'" + str + "')";
			}
		} else if (pageMap.getCondition().containsKey("brandids")) {
			String str = (String) pageMap.getCondition().get("brandids");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t1.brandid = '" + str + "' ";
					query_sqlall += " and t1.brandid = '" + str + "' ";
				} else {
					query_sql += " and (t1.brandid is null or t1.brandid = '')";
					query_sqlall += " and (t1.brandid is null or t1.brandid = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t1.brandid,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t1.brandid,'" + str + "')";
			}
		}

		if (pageMap.getCondition().containsKey("branduser")) {
			String str = (String) pageMap.getCondition().get("branduser");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t1.branduser = '" + str + "' ";
					query_sqlall += " and t1.branduser = '" + str + "' ";
				} else {
					query_sql += " and (t1.branduser is null or t1.branduser = '')";
					query_sqlall += " and (t1.branduser is null or t1.branduser = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t1.branduser,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t1.branduser,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("supplieruser")) {
			String str = (String) pageMap.getCondition().get("supplieruser");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t1.supplieruser = '" + str + "' ";
					query_sqlall += " and t1.supplieruser = '" + str + "' ";
				} else {
					query_sql += " and (t1.supplieruser is null or t1.supplieruser = '')";
					query_sqlall += " and (t1.supplieruser is null or t1.supplieruser = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t1.supplieruser,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t1.supplieruser,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("branddept")) {
			String str = (String) pageMap.getCondition().get("branddept");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t1.branddept like '" + str + "%' ";
					query_sqlall += " and t1.branddept like '" + str + "%' ";
				} else {
					query_sql += " and (t1.branddept is null or t1.branddept = '')";
					query_sqlall += " and (t1.branddept is null or t1.branddept = '')";
				}
			} else {
				String retStr = "";
				String[] branddeptArr = str.split(",");
				for (String branddept : branddeptArr) {
					Map map = new HashMap();
					map.put("deptid", branddept);
					List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
					if (dList.size() != 0) {
						for (DepartMent departMent : dList) {
							if (StringUtils.isNotEmpty(retStr)) {
								retStr += "," + departMent.getId();
							} else {
								retStr = departMent.getId();
							}
						}
					}
				}
				query_sql += " and FIND_IN_SET(t1.branddept,'" + retStr + "')";
				query_sqlall += " and FIND_IN_SET(t1.branddept,'" + retStr + "')";
			}
		}
		if (pageMap.getCondition().containsKey("salesdept")) {
			String str = (String) pageMap.getCondition().get("salesdept");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t.salesdept like '" + str + "%' ";
					query_sqlall += " and t.salesdept like '" + str + "%' ";
				} else {
					query_sql += " and (t.salesdept is null or t.salesdept = '')";
					query_sqlall += " and (t.salesdept is null or t.salesdept = '')";
				}
			} else {
				String retStr = "";
				String[] salesdeptArr = str.split(",");
				for (String salesdept : salesdeptArr) {
					Map map = new HashMap();
					map.put("deptid", salesdept);
					List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
					if (dList.size() != 0) {
						for (DepartMent departMent : dList) {
							if (StringUtils.isNotEmpty(retStr)) {
								retStr += "," + departMent.getId();
							} else {
								retStr = departMent.getId();
							}
						}
					}
				}
				query_sql += " and FIND_IN_SET(t.salesdept,'" + retStr + "')";
				query_sqlall += " and FIND_IN_SET(t.salesdept,'" + retStr + "')";
			}
		}
		if (pageMap.getCondition().containsKey("customersort")) {
			String str = (String) pageMap.getCondition().get("customersort");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t.customersort like '" + str + "%' ";
					query_sqlall += " and t.customersort like '" + str + "%' ";
				} else {
					query_sql += " and (t.customersort is null or t.customersort = '')";
					query_sqlall += " and (t.customersort is null or t.customersort = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t.customersort,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t.customersort,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("salesarea")) {
			String str = (String) pageMap.getCondition().get("salesarea");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t.salesarea = '" + str + "' ";
					query_sqlall += " and t.salesarea = '" + str + "' ";
				} else {
					query_sql += " and (t.salesarea is null or t.salesarea = '')";
					query_sqlall += " and (t.salesarea is null or t.salesarea = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t.salesarea,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t.salesarea,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("salesuser")) {
			String str = (String) pageMap.getCondition().get("salesuser");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t.salesuser = '" + str + "' ";
					query_sqlall += " and t.salesuser = '" + str + "' ";
				} else {
					query_sql += " and (t.salesuser is null or t.salesuser = '')";
					query_sqlall += " and (t.salesuser is null or t.salesuser = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t.salesuser,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t.salesuser,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("supplierid")) {
			String str = (String) pageMap.getCondition().get("supplierid");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t1.supplierid = '" + str + "' ";
					query_sqlall += " and t1.supplierid = '" + str + "' ";
				} else {
					query_sql += " and (t1.supplierid is null or t1.supplierid = '')";
					query_sqlall += " and (t1.supplierid is null or t1.supplierid = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t1.supplierid,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t1.supplierid,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("goodssort")) {
			String str = (String) pageMap.getCondition().get("goodssort");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t1.goodssort = '" + str + "' ";
				} else {
					query_sql += " and (t1.goodssort is null or t1.goodssort = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t1.goodssort,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("goodstype")) {
			String str = (String) pageMap.getCondition().get("goodstype");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and FIND_IN_SET(g.goodstype,'" + str + "')";
		}
		if (pageMap.getCondition().containsKey("customerid")) {
			String str = (String) pageMap.getCondition().get("customerid");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t.customerid = '" + str + "' ";
					query_sqlall += " and t.customerid = '" + str + "' ";
				} else {
					query_sql += " and (t.customerid is null or t.customerid = '')";
					query_sqlall += " and (t.customerid is null or t.customerid = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t.customerid,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t.customerid,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("pcustomerid")) {
			String str = (String) pageMap.getCondition().get("pcustomerid");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t.pcustomerid = '" + str + "' ";
					query_sqlall += " and t.pcustomerid = '" + str + "' ";
				} else {
					query_sql += " and (t.pcustomerid is null or t.pcustomerid = '')";
					query_sqlall += " and (t.pcustomerid is null or t.pcustomerid = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t.pcustomerid,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t.pcustomerid,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("adduserid")) {
			String str = (String) pageMap.getCondition().get("adduserid");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t.adduserid = '" + str + "' ";
					query_sqlall += " and t.adduserid = '" + str + "' ";
				} else {
					query_sql += " and (t.adduserid is null or t.adduserid = '')";
					query_sqlall += " and (t.adduserid is null or t.adduserid = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t.adduserid,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t.adduserid,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("storageid")) {
			String str = (String) pageMap.getCondition().get("storageid");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t.storageid = '" + str + "' ";
				} else {
					query_sql += " and (t.storageid is null or t.storageid = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t.storageid,'" + str + "')";
			}
		}
		// 销售类型
		if (pageMap.getCondition().containsKey("salestype")) {
			String str = (String) pageMap.getCondition().get("salestype");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and salestype = '" + str + "' ";
				} else {
					query_sql += " and (salestype is null or salestype = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(salestype,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("businessdate1")) {
			String str = (String) pageMap.getCondition().get("businessdate1");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t.businessdate >= '" + str + "'";
			query_sqlall += " and t.businessdate >= '" + str + "'";
		}
		if (pageMap.getCondition().containsKey("businessdate2")) {
			String str = (String) pageMap.getCondition().get("businessdate2");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t.businessdate <= '" + str + "'";
			query_sqlall += " and t.businessdate <= '" + str + "'";
		}
		if (pageMap.getCondition().containsKey("remark")) {
			String str = (String) pageMap.getCondition().get("remark");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t.remark like '%" + str + "%'";
			query_sqlall += " and t.remark like '%" + str + "%'";
		}
		if (pageMap.getCondition().containsKey("detailremark")) {
			String str = (String) pageMap.getCondition().get("detailremark");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t1.remark like '%" + str + "%'";
			query_sqlall += " and t1.remark like '%" + str + "%'";
		}
		if (pageMap.getCondition().containsKey("sourceid")) {
			String str = (String) pageMap.getCondition().get("sourceid");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t2.sourceid like '%" + str + "%'";
			query_sqlall += " and 1!=1";
		}
		if (StringUtils.isNotEmpty(query_sqlall)) {
			query_sql_push = query_sqlall.replaceAll("t1.", "t.");
			query_sql_push = query_sql_push.replaceAll("t.goodsid", "t.brandid");
		}
		if (pageMap.getCondition().containsKey("storageid")) {
			query_sql_push = " and 1!= 1";
		}
		pageMap.getCondition().put("query_sql", query_sql);
		pageMap.getCondition().put("query_sqlall", query_sqlall);
		pageMap.getCondition().put("query_sql_push", query_sql_push);
		//排序
		String orderstr = "";
		if (pageMap.getCondition().containsKey("sort")) {
			String sort = (String) pageMap.getCondition().get("sort");
			String order = (String) pageMap.getCondition().get("order");
			if (null == order) {
				order = "asc";
			}
			orderstr = sort + " " + order;
		}
		pageMap.getCondition().put("orderstr", orderstr);

		List<BaseSalesReport> list = salesCustomerReportMapper.showDyBaseSalesReportData(pageMap);
		for (BaseSalesReport baseSalesReport : list) {
			if (null == baseSalesReport) {
				continue;
			}
			BigDecimal sendnum = null != baseSalesReport.getSendnum() ? baseSalesReport.getSendnum() : BigDecimal.ZERO;
			BigDecimal checkreturnnum = null != baseSalesReport.getCheckreturnnum() ? baseSalesReport.getCheckreturnnum() : BigDecimal.ZERO;
			BigDecimal returnnum = null != baseSalesReport.getReturnnum() ? baseSalesReport.getReturnnum() : BigDecimal.ZERO;
			BigDecimal sendtotalbox = null != baseSalesReport.getSendtotalbox() ? baseSalesReport.getSendtotalbox() : BigDecimal.ZERO;
			BigDecimal returntotalbox = null != baseSalesReport.getReturntotalbox() ? baseSalesReport.getReturntotalbox() : BigDecimal.ZERO;
			BigDecimal sendamount = null != baseSalesReport.getSendamount() ? baseSalesReport.getSendamount() : BigDecimal.ZERO;
			BigDecimal returnamount = null != baseSalesReport.getReturnamount() ? baseSalesReport.getReturnamount() : BigDecimal.ZERO;
			BigDecimal pushbalanceamount = null != baseSalesReport.getPushbalanceamount() ? baseSalesReport.getPushbalanceamount() : BigDecimal.ZERO;
			BigDecimal pushbalancenotaxamount = null != baseSalesReport.getPushbalancenotaxamount() ? baseSalesReport.getPushbalancenotaxamount() : BigDecimal.ZERO;
			BigDecimal sendnotaxamount = null != baseSalesReport.getSendnotaxamount() ? baseSalesReport.getSendnotaxamount() : BigDecimal.ZERO;
			BigDecimal returnnotaxamount = null != baseSalesReport.getReturnnotaxamount() ? baseSalesReport.getReturnnotaxamount() : BigDecimal.ZERO;
			BigDecimal costamount = null != baseSalesReport.getCostamount() ? baseSalesReport.getCostamount() : BigDecimal.ZERO;
			BigDecimal saleamount=sendamount.subtract(returnamount).add(pushbalanceamount);
			BigDecimal checkreturnamount=null!=baseSalesReport.getCheckreturnamount()?baseSalesReport.getCheckreturnamount():BigDecimal.ZERO;

			//退货率=退货金额（不是退货合计，不包括直退的）/销售金额
			if (saleamount.compareTo(BigDecimal.ZERO) != 0) {
				BigDecimal checkreturnrate = checkreturnamount.divide(saleamount, 6, BigDecimal.ROUND_HALF_UP);
				baseSalesReport.setCheckreturnrate(checkreturnrate.multiply(new BigDecimal(100)));
			}
			//销售数量 = 发货单数量 - 直退数量
			baseSalesReport.setSalenum(sendnum.subtract(returnnum));
			//销售箱数
			baseSalesReport.setSaletotalbox(sendtotalbox.subtract(returntotalbox));
			baseSalesReport.setSaleamount(sendamount.subtract(returnamount).add(pushbalanceamount));
			baseSalesReport.setSalenotaxamount(sendnotaxamount.subtract(returnnotaxamount).add(pushbalancenotaxamount));
			if (null != baseSalesReport.getSaleamount()) {
				//毛利额 = 销售金额 - 成本金额
				baseSalesReport.setSalemarginamount(baseSalesReport.getSaleamount().subtract(costamount));
				//实际毛利率 = （销售金额 - 成本金额）/销售金额*100
				if (baseSalesReport.getSaleamount().compareTo(BigDecimal.ZERO) != 0) {
					BigDecimal realrate = baseSalesReport.getSaleamount().subtract(costamount).divide(baseSalesReport.getSaleamount(), 6, BigDecimal.ROUND_HALF_UP);
					realrate = realrate.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
					if (baseSalesReport.getSaleamount().compareTo(BigDecimal.ZERO) == -1) {
						baseSalesReport.setRealrate(realrate.negate());
					} else {
						baseSalesReport.setRealrate(realrate);
					}
				}
			} else if (costamount.compareTo(BigDecimal.ZERO) == 1) {
				baseSalesReport.setRealrate(new BigDecimal(100).negate());
			}
			baseSalesReport.setId(baseSalesReport.getCustomerid());
			//销售税额
			if (null != baseSalesReport.getSalenotaxamount() && null != baseSalesReport.getSaleamount()) {
				baseSalesReport.setSaletax(baseSalesReport.getSaleamount().subtract(baseSalesReport.getSalenotaxamount()));
			}

			//根据获取的商品编码获取条形码
			if (StringUtils.isNotEmpty(baseSalesReport.getGoodsid())) {
				GoodsInfo goodsInfo = getGoodsInfoByID(baseSalesReport.getGoodsid());
				if (null != goodsInfo) {
					baseSalesReport.setGoodsname(goodsInfo.getName());
					baseSalesReport.setSpell(goodsInfo.getSpell());
				}
			}
			//判断是否为导出使用，若为导出，则将直退数量、售后退货数量显示为负数
			if (pageMap.getCondition().containsKey("exportflag") && null != pageMap.getCondition().get("exportflag")) {
				//直退数量
				baseSalesReport.setDirectreturnnum(baseSalesReport.getDirectreturnnum().negate());
				//直退箱数
				baseSalesReport.setDirectreturntotalbox(baseSalesReport.getDirectreturntotalbox().negate());
				//直退金额
				baseSalesReport.setDirectreturnamount(baseSalesReport.getDirectreturnamount().negate());
				//售后退货数量
				baseSalesReport.setCheckreturnnum(baseSalesReport.getCheckreturnnum().negate());
				//售后退货箱数
				baseSalesReport.setCheckreturntotalbox(baseSalesReport.getCheckreturntotalbox().negate());
				//退货金额
				baseSalesReport.setCheckreturnamount(baseSalesReport.getCheckreturnamount().negate());
				//退货总数量
				baseSalesReport.setReturnnum(baseSalesReport.getReturnnum().negate());
				//退货总箱数
				baseSalesReport.setReturntotalbox(baseSalesReport.getReturntotalbox().negate());
				//退货合计
				baseSalesReport.setReturnamount(baseSalesReport.getReturnamount().negate());
			}
			if (groupcols.indexOf("customerid") != -1) {
				if (StringUtils.isNotEmpty(baseSalesReport.getCustomerid())) {
					Customer customer = getCustomerByID(baseSalesReport.getCustomerid());
					if (null != customer) {
						baseSalesReport.setCustomername(customer.getName());
						baseSalesReport.setShortcode(customer.getShortcode());
					} else {
						baseSalesReport.setCustomername("其他未定义");
					}
				} else {
					baseSalesReport.setCustomerid("nodata");
					baseSalesReport.setCustomername("其他未指定");
				}
				if (groupcols.indexOf("pcustomerid") == -1) {
					Customer pcustomer = getCustomerByID(baseSalesReport.getPcustomerid());
					if (null != pcustomer) {
						baseSalesReport.setPcustomername(pcustomer.getName());
					}
				}
				if (groupcols.indexOf("salesdept") == -1) {
					DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getSalesdept());
					if (null != departMent) {
						baseSalesReport.setSalesdeptname(departMent.getName());
					}
				}
				if (groupcols.indexOf("customersort") == -1) {
					CustomerSort customerSort = getCustomerSortByID(baseSalesReport.getCustomersort());
					if (null != customerSort) {
						baseSalesReport.setCustomersortname(customerSort.getName());
					}
				}
				if (groupcols.indexOf("salesarea") == -1) {
					SalesArea salesArea = getSalesareaByID(baseSalesReport.getSalesarea());
					if (null != salesArea) {
						baseSalesReport.setSalesareaname(salesArea.getThisname());
					}
				}
				if (groupcols.indexOf("salesuser") == -1) {
					Personnel personnel = getPersonnelById(baseSalesReport.getSalesuser());
					if (null != personnel) {
						baseSalesReport.setSalesusername(personnel.getName());
					}
					DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getSalesdept());
					if (null != departMent) {
						baseSalesReport.setSalesdeptname(departMent.getName());
					}
				}
			} else {
				baseSalesReport.setCustomerid("");
				baseSalesReport.setCustomername("");
				if (groupcols.indexOf("salesuser") == -1 && groupcols.indexOf("salesdept") == -1) {
					baseSalesReport.setSalesdeptname("");
				}
			}
			if (groupcols.indexOf("pcustomerid") != -1) {
				if (StringUtils.isNotEmpty(baseSalesReport.getPcustomerid())) {
					Customer pcustomer = getCustomerByID(baseSalesReport.getPcustomerid());
					if (null != pcustomer) {
						baseSalesReport.setPcustomername(pcustomer.getName());
					} else {
						baseSalesReport.setPcustomername("其他未定义");
					}
				} else {
					baseSalesReport.setPcustomerid("nodata");
					baseSalesReport.setPcustomername("其他未指定");
				}
				if (groupcols.indexOf("customerid") == 1) {
					baseSalesReport.setCustomerid("");
					baseSalesReport.setCustomername("");
				}
			}
			if (groupcols.indexOf("customersort") != -1) {
				if (StringUtils.isNotEmpty(baseSalesReport.getCustomersort())) {
					CustomerSort customerSort = getCustomerSortByID(baseSalesReport.getCustomersort());
					if (null != customerSort) {
						baseSalesReport.setCustomersortname(customerSort.getName());
					} else {
						baseSalesReport.setCustomersortname("其他未定义");
					}
				} else {
					baseSalesReport.setCustomersort("nodata");
					baseSalesReport.setCustomersortname("其他未指定");
				}
			}
			if (groupcols.indexOf("salesarea") != -1) {
				if (StringUtils.isNotEmpty(baseSalesReport.getSalesarea())) {
					SalesArea salesArea = getSalesareaByID(baseSalesReport.getSalesarea());
					if (null != salesArea) {
						baseSalesReport.setSalesareaname(salesArea.getThisname());
					} else {
						baseSalesReport.setSalesareaname("其他未定义");
					}
				} else {
					baseSalesReport.setSalesarea("nodata");
					baseSalesReport.setSalesareaname("其他未指定");
				}
			}
			if (groupcols.indexOf("salesdept") != -1) {
				if (StringUtils.isNotEmpty(baseSalesReport.getSalesdept())) {
					DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getSalesdept());
					if (null != departMent) {
						baseSalesReport.setSalesdeptname(departMent.getName());
					} else {
						baseSalesReport.setSalesdeptname("其他未定义");
					}
				} else {
					baseSalesReport.setSalesdept("nodata");
					baseSalesReport.setSalesdeptname("其他未指定");
				}
			}
			if (groupcols.indexOf("salesuser") != -1) {
				if (StringUtils.isNotEmpty(baseSalesReport.getSalesuser())) {
					Personnel personnel = getPersonnelById(baseSalesReport.getSalesuser());
					if (null != personnel) {
						baseSalesReport.setSalesusername(personnel.getName());
					} else {
						baseSalesReport.setSalesusername("其他未定义");
					}
				} else {
					baseSalesReport.setSalesuser("nodata");
					baseSalesReport.setSalesusername("其他未指定");
				}
				DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getSalesdept());
				if (null != departMent) {
					baseSalesReport.setSalesdeptname(departMent.getName());
				}
				if (groupcols.indexOf("salesarea") == -1) {
					SalesArea salesArea = getSalesareaByID(baseSalesReport.getSalesarea());
					if (null != salesArea) {
						baseSalesReport.setSalesareaname(salesArea.getThisname());
					}
				}
			}
			if (groupcols.indexOf("goodsid") != -1) {
				GoodsInfo goodsInfo = getAllGoodsInfoByID(baseSalesReport.getGoodsid());
				if (null != goodsInfo) {
					baseSalesReport.setGoodsname(goodsInfo.getName());
					baseSalesReport.setSpell(goodsInfo.getSpell());
					baseSalesReport.setBoxnum(goodsInfo.getBoxnum());
					WaresClass waresClass = getBaseGoodsMapper().getWaresClassInfo(baseSalesReport.getGoodssort());
					if (null != waresClass) {
						baseSalesReport.setGoodssortname(waresClass.getThisname());
					}
					if (StringUtils.isNotEmpty(goodsInfo.getGoodstype())) {
						baseSalesReport.setGoodstype(goodsInfo.getGoodstype());
						SysCode goodstype = getBaseSysCodeMapper().getSysCodeInfo(goodsInfo.getGoodstype(), "goodstype");
						if (null != goodstype) {
							baseSalesReport.setGoodstypename(goodstype.getCodename());
						}
					}
					BuySupplier buySupplier = getSupplierInfoById(baseSalesReport.getSupplierid());
					if (null != buySupplier) {
						baseSalesReport.setSuppliername(buySupplier.getName());
					}
				} else {
					Brand brand = getGoodsBrandByID(baseSalesReport.getGoodsid());
					if (null != brand) {
						baseSalesReport.setGoodsname("（折扣）" + brand.getName());
					} else {
						baseSalesReport.setGoodsname("（折扣）其他");
					}
				}
				if (groupcols.indexOf("brandid") == -1) {
					Brand brand = getGoodsBrandByID(baseSalesReport.getBrandid());
					if (null != brand) {
						baseSalesReport.setBrandname(brand.getName());
					}
					Personnel personnel = getPersonnelById(baseSalesReport.getBranduser());
					if (null != personnel) {
						baseSalesReport.setBrandusername(personnel.getName());
					}
					if ("QC".equals(baseSalesReport.getSupplieruser())) {
						baseSalesReport.setSupplierusername("期初");
					} else {
						Personnel personnel2 = getPersonnelById(baseSalesReport.getSupplieruser());
						if (null != personnel2) {
							baseSalesReport.setSupplierusername(personnel2.getName());
						}
					}
				}
				if (groupcols.indexOf("branddept") == -1) {
					DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getBranddept());
					if (null != departMent) {
						baseSalesReport.setBranddeptname(departMent.getName());
					}
				}
				if (groupcols.indexOf("branduser") == -1) {
					Personnel personnel = getPersonnelById(baseSalesReport.getBranduser());
					if (null != personnel) {
						baseSalesReport.setBrandusername(personnel.getName());
					}
				}
			}
			if (groupcols.indexOf("goodssort") != -1) {
				if (StringUtils.isNotEmpty(baseSalesReport.getGoodssort())) {
					WaresClass waresClass = getBaseGoodsMapper().getWaresClassInfo(baseSalesReport.getGoodssort());
					if (null != waresClass) {
						baseSalesReport.setGoodssortname(waresClass.getThisname());
					} else {
						baseSalesReport.setGoodssortname("其他未定义");
					}
				} else {
					baseSalesReport.setGoodssort("nodata");
					baseSalesReport.setGoodssortname("其他未指定");
				}
			}
			if (groupcols.indexOf("brandid") != -1) {
				if (StringUtils.isNotEmpty(baseSalesReport.getBrandid())) {
					Brand brand = getGoodsBrandByID(baseSalesReport.getBrandid());
					if (null != brand) {
						baseSalesReport.setBrandname(brand.getName());
					} else {
						baseSalesReport.setBrandname("其他未定义");
					}
				} else {
					baseSalesReport.setBrandid("nodata");
					baseSalesReport.setBrandname("其他未指定");
				}
				if (groupcols.indexOf("branddept") == -1) {
					DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getBranddept());
					if (null != departMent) {
						baseSalesReport.setBranddeptname(departMent.getName());
					}
				}
				Personnel personnel = getPersonnelById(baseSalesReport.getBranduser());
				if (null != personnel) {
					baseSalesReport.setBrandusername(personnel.getName());
				}
				if ("QC".equals(baseSalesReport.getSupplieruser())) {
					baseSalesReport.setSupplierusername("期初");
				} else {
					Personnel personnel2 = getPersonnelById(baseSalesReport.getSupplieruser());
					if (null != personnel2) {
						baseSalesReport.setSupplierusername(personnel2.getName());
					}
				}
			}
			if (groupcols.indexOf("branduser") != -1) {
				if (StringUtils.isNotEmpty(baseSalesReport.getBranduser())) {
					Personnel personnel = getPersonnelById(baseSalesReport.getBranduser());
					if (null != personnel) {
						baseSalesReport.setBrandusername(personnel.getName());
					} else {
						baseSalesReport.setBrandusername("其他未定义");
					}
				} else {
					baseSalesReport.setBranduser("nodata");
					baseSalesReport.setBrandusername("其他未指定");
				}
				if (groupcols.indexOf("branddept") == -1) {
					//分公司
					Map map2 = new HashMap();
					map2.put("pid", "");
					List<DepartMent> deptList = getBaseDepartMentMapper().getDeptListByParam(map2);
					for (DepartMent dept : deptList) {
						if (baseSalesReport.getBranddept().indexOf(dept.getId()) == 0) {
							baseSalesReport.setBranddeptname(dept.getName());
						}
					}
				}
			}
			if (groupcols.indexOf("branddept") != -1) {
				if (StringUtils.isNotEmpty(baseSalesReport.getBranddept())) {
					DepartMent departMent = getDepartmentByDeptid(baseSalesReport.getBranddept());
					if (null != departMent) {
						baseSalesReport.setBranddeptname(departMent.getName());
					} else {
						baseSalesReport.setBranddeptname("其他未定义");
					}
				} else {
					baseSalesReport.setBranddept("nodata");
					baseSalesReport.setBranddeptname("其他未指定");
				}
			}
			if (groupcols.indexOf("supplierid") != -1) {
				if (StringUtils.isNotEmpty(baseSalesReport.getSupplierid())) {
					BuySupplier supplier = getSupplierInfoById(baseSalesReport.getSupplierid());
					if (null != supplier) {
						baseSalesReport.setSuppliername(supplier.getName());
					} else {
						baseSalesReport.setSuppliername("其他未定义");
					}
				} else {
					baseSalesReport.setSupplierid("nodata");
					baseSalesReport.setSuppliername("其他未指定");
				}
			}
			if (groupcols.indexOf("supplieruser") != -1) {
				if (StringUtils.isNotEmpty(baseSalesReport.getSupplieruser())) {
					if ("QC".equals(baseSalesReport.getSupplieruser())) {
						baseSalesReport.setSupplierusername("期初");
					} else {
						Personnel personnel2 = getPersonnelById(baseSalesReport.getSupplieruser());
						if (null != personnel2) {
							baseSalesReport.setSupplierusername(personnel2.getName());
						} else {
							baseSalesReport.setSupplierusername("其他未定义");
						}
					}
				} else {
					baseSalesReport.setSupplieruser("nodata");
					baseSalesReport.setSupplierusername("其他未指定");
				}
			}
			if (groupcols.indexOf("storageid") != -1) {
				if (StringUtils.isNotEmpty(baseSalesReport.getStorageid())) {
					StorageInfo storageInfo = getStorageInfoByID(baseSalesReport.getStorageid());
					if (null != storageInfo) {
						baseSalesReport.setStoragename(storageInfo.getName());
					} else {
						baseSalesReport.setStoragename("其他未定义");
					}
				} else {
					baseSalesReport.setStoragename("nodata");
					baseSalesReport.setStoragename("其他未指定");
				}
			}
		}
		int count = salesCustomerReportMapper.showDyBaseSalesReportCount(pageMap);
		PageData pageData = new PageData(count, list, pageMap);
		//取合计数据
		pageMap.getCondition().put("groupcols", "all");
		List<BaseSalesReport> footer = salesCustomerReportMapper.showDyBaseSalesReportData(pageMap);
		if(footer.get(0)!=null){
			footer.get(0).setBarcode("");
		}

		footer = returnBaseSaleReportFooter(footer, pageMap, groupcols);
		pageData.setFooter(footer);
		return pageData;
	}

	/**
	 * 获取吉马销售日报表数据
	 * @param pageMap
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author luoqiang
	 * @date Mar 19, 2018
	 */
	public PageData showJmSalesTargetDayReportData(PageMap pageMap) throws Exception{
		List<Map> list=salesCustomerReportMapper.showJmSalesTargetDayReportList(pageMap);
		for(Map datamap:list){
			String deptid=(String)datamap.get("deptid");
			DepartMent departMent=getDepartmentByDeptid(deptid);
			if(departMent!=null){
				datamap.put("deptname",departMent.getName());
			}
			String branduser=(String)datamap.get("branduser");
			if("小计".equals(branduser)){
				datamap.put("branduser","");
				datamap.put("deptname","小计");
			}
			if(datamap.containsKey("targetamount")){
				datamap.put("targetamount",new BigDecimal(datamap.get("targetamount").toString()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
			}
			if(datamap.containsKey("daytarget")){
				datamap.put("daytarget",new BigDecimal(datamap.get("daytarget").toString()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
			}
			if(datamap.containsKey("dayorderamount")){
				datamap.put("dayorderamount",new BigDecimal(datamap.get("dayorderamount").toString()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
			}
			if(datamap.containsKey("daysaleoutamount")){
				datamap.put("daysaleoutamount",new BigDecimal(datamap.get("daysaleoutamount").toString()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
			}
			if(datamap.containsKey("daycompletepercent")){
				datamap.put("daycompletepercent",new BigDecimal(datamap.get("daycompletepercent").toString()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP)+"%");
			}
			if(datamap.containsKey("daydifference")){
				datamap.put("daydifference",new BigDecimal(datamap.get("daydifference").toString()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
			}
			if(datamap.containsKey("monthsaleoutamount")){
				datamap.put("monthsaleoutamount",new BigDecimal(datamap.get("monthsaleoutamount").toString()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
			}
			if(datamap.containsKey("monthcompletepercent")){
				datamap.put("monthcompletepercent",new BigDecimal(datamap.get("monthcompletepercent").toString()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP)+"%");
			}
			if(datamap.containsKey("monthdifference")){
				datamap.put("monthdifference",new BigDecimal(datamap.get("monthdifference").toString()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
			}
		}
		int count=salesCustomerReportMapper.showJmSalesTargetDayReportCount(pageMap);
		PageData pageData=new PageData(count,list,pageMap);
		Map footerMap=salesCustomerReportMapper.showJmSalesTargetDayReportSum(pageMap);
		if(footerMap.containsKey("targetamount")){
			footerMap.put("targetamount",new BigDecimal(footerMap.get("targetamount").toString()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
		}
		if(footerMap.containsKey("daytarget")){
			footerMap.put("daytarget",new BigDecimal(footerMap.get("daytarget").toString()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
		}
		if(footerMap.containsKey("dayorderamount")){
			footerMap.put("dayorderamount",new BigDecimal(footerMap.get("dayorderamount").toString()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
		}
		if(footerMap.containsKey("daysaleoutamount")){
			footerMap.put("daysaleoutamount",new BigDecimal(footerMap.get("daysaleoutamount").toString()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
		}
		if(footerMap.containsKey("daycompletepercent")){
			footerMap.put("daycompletepercent",new BigDecimal(footerMap.get("daycompletepercent").toString()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP)+"%");
		}
		if(footerMap.containsKey("daydifference")){
			footerMap.put("daydifference",new BigDecimal(footerMap.get("daydifference").toString()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
		}
		if(footerMap.containsKey("monthsaleoutamount")){
			footerMap.put("monthsaleoutamount",new BigDecimal(footerMap.get("monthsaleoutamount").toString()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
		}
		if(footerMap.containsKey("monthcompletepercent")){
			footerMap.put("monthcompletepercent",new BigDecimal(footerMap.get("monthcompletepercent").toString()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP)+"%");
		}
		if(footerMap.containsKey("monthdifference")){
			footerMap.put("monthdifference",new BigDecimal(footerMap.get("monthdifference").toString()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
		}
		List footerList=new ArrayList();
		footerList.add(footerMap);
		pageData.setFooter(footerList);
		return pageData;
	}

	/**
	 * 导入吉马销售目标数据
	 * @param list
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 * @throws
	 * @author luoqiang
	 * @date Mar 19, 2018
	 */
	public List<Map<String, Object>> importJmSalesTarget(List list) throws Exception {
		List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> temp =  (Map<String, Object>)list.get(i);
			String personnelid = (String) temp.get("personnelid");
			String targetdate = (String) temp.get("targetdate");
			BigDecimal targetamount = new BigDecimal(temp.get("targetamount").toString()) ;

			if(!temp.containsKey("workday")){
				Map errorMap = new HashMap(temp);
				errorMap.put("lineno", (i + 2));
				errorMap.put("errors", "工作天数为空");
				errorList.add(errorMap);
				continue;
			}
            BigDecimal workday =  new BigDecimal(temp.get("workday").toString()) ;

            if(StringUtils.isEmpty(targetdate)){
				Map errorMap = new HashMap(temp);
				errorMap.put("lineno", (i + 2));
				errorMap.put("errors", "任务日期为空");
				errorList.add(errorMap);
				continue;
			}



			Personnel personnel=getPersonnelById(personnelid);
			if(personnel==null){
				Map errorMap = new HashMap(temp);
				errorMap.put("lineno", (i + 2));
				errorMap.put("errors", "人员不存在");
				errorList.add(errorMap);
				continue;
			}

            JmSalesTarget oldjmSalesTarget=salesCustomerReportMapper.getJmSalesTarget(personnelid,targetdate);
			if(oldjmSalesTarget==null){
				JmSalesTarget jmSalesTarget=new JmSalesTarget();
				jmSalesTarget.setPersonnelid(personnelid);
				jmSalesTarget.setTargetdate(targetdate);
				jmSalesTarget.setTargetamount(targetamount);
				jmSalesTarget.setWorkday(workday);
				salesCustomerReportMapper.insertJmSalesTarget(jmSalesTarget);
			}else{
				oldjmSalesTarget.setTargetamount(targetamount);
				oldjmSalesTarget.setWorkday(workday);
				salesCustomerReportMapper.updateJmSalesTarget(oldjmSalesTarget);
			}



		}
		return errorList;
	}

	/**
	 * 保存销售日报表目标（吉马）
	 * @param targetdate
	 * @param list
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Mar 20, 2018
	 */
	@Override
	public Boolean saveJmSalesTargetDay(String targetdate,List<Map> list){
		targetdate=CommonUtils.getYearStr(targetdate)+"-"+CommonUtils.getMonthStr(targetdate);
		for(Map map:list){
			String branduser=(String)map.get("branduser");
			BigDecimal targetamount=new BigDecimal(map.get("targetamount").toString());
			BigDecimal workday=new BigDecimal(map.get("workday").toString());
			JmSalesTarget oldjmSalesTarget=salesCustomerReportMapper.getJmSalesTarget(branduser,targetdate);
			if(oldjmSalesTarget==null){
				JmSalesTarget jmSalesTarget=new JmSalesTarget();
				jmSalesTarget.setPersonnelid(branduser);
				jmSalesTarget.setTargetdate(targetdate);
				jmSalesTarget.setTargetamount(targetamount);
				jmSalesTarget.setWorkday(workday);
				salesCustomerReportMapper.insertJmSalesTarget(jmSalesTarget);
			}else{
				oldjmSalesTarget.setTargetamount(targetamount);
				oldjmSalesTarget.setWorkday(workday);
				salesCustomerReportMapper.updateJmSalesTarget(oldjmSalesTarget);
			}
		}
		return true;
	}
}

