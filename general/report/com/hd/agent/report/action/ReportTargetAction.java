/**
 * @(#)ReportTargetAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年12月5日 chenwei 创建版本
 */
package com.hd.agent.report.action;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.report.model.ReportTarget;
import com.hd.agent.report.service.IReportTargetService;

/**
 * 
 * 各类报表考核指标配置action
 * @author chenwei
 */
public class ReportTargetAction extends BaseFilesAction {

	private IReportTargetService reportTargetService;

	public IReportTargetService getReportTargetService() {
		return reportTargetService;
	}

	public void setReportTargetService(IReportTargetService reportTargetService) {
		this.reportTargetService = reportTargetService;
	}
	/**
	 * 显示各类报表考核指标配置action
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月5日
	 */
	public String showTargetAddPage() throws Exception{
		return "success";
	}
	/**
	 * 添加品牌销售考核指标
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月5日
	 */
	@UserOperateLog(key="reportTarget",type=2)
	public String addReportTargetBySalesBrand() throws Exception{
		String brandid = request.getParameter("brandid");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String amountStr = request.getParameter("amount");
		BigDecimal amount = new BigDecimal(amountStr);
		if(StringUtils.isNotEmpty(amountStr)){
			ReportTarget reportTarget = new ReportTarget();
			reportTarget.setBilltype("SalesBrand");
			reportTarget.setBusid(brandid);
			reportTarget.setYear(year);
			reportTarget.setMonth(month);
			reportTarget.setTargetamount(amount);
			reportTarget.setState("1");
			boolean flag = reportTargetService.addReportTarget(reportTarget);
			addJSONObject("flag", flag);
			addLog("添加品牌销售考核目标  品牌编号："+brandid+",年："+year+"月："+month+",金额："+amountStr , flag);
		}else{
			addLog("添加品牌销售考核目标  品牌编号："+brandid+",年："+year+"月："+month+",金额未填写" , false);
		}
		return "success";
	}
	
	/**
	 * 添加销售回笼考核指标
	 * 根据billtype（SalesWithdrawnBranduser/SalesWithdrawnBrand）判断是品牌业务员或品牌
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 23, 2015
	 */
	@UserOperateLog(key="reportTarget",type=2)
	public String addSalesWithdrawnAssess()throws Exception{
		Map paramMap = CommonUtils.changeMap(request.getParameterMap());
		String billtype = (String)paramMap.get("billtype");
		String businessdate = (String)paramMap.get("businessdate");
		String rowstr = (String)paramMap.get("rowstr");;
		JSONObject obj = JSONObject.fromObject(rowstr);
		Map<String, Object> map = JSONObject.fromObject(obj);
		Map map1 = reportTargetService.addMapToReportTarget(map,billtype,businessdate);
		boolean flag = map1.get("flag").equals(true);
		String msg = null != map1.get("msg") ? (String)map1.get("msg") : "";
		if(StringUtils.isNotEmpty(msg)){
			addLog(msg, flag);
		}
		if(flag){
			paramMap.put("begindate",businessdate);
			ReportTarget reportTarget = reportTargetService.getReportTargetSumCaseSalesWithdrawnAssess(paramMap);
			if(null != reportTarget){
				map1.put("reportTarget",reportTarget);
			}
		}
		addJSONObject(map1);
		return SUCCESS;
	}

	/**
	 * 导入分客户业务员品牌销售回笼考核表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2017-01-06
	 */
	public String importSalesuserBrandSWAData()throws Exception{
		String billtype = request.getParameter("billtype");
		Map<String, Object> retMap = new HashMap<String, Object>();
		//获取第一行数据为字段的描述列表
		List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile);
		List<String> paramList2 = new ArrayList<String>();
		for(String str : paramList){
			if("客户业务员".equals(str)){
				paramList2.add("salesusername");
			}else if("销售部门".equals(str)){
				paramList2.add("salesdeptname");
			}else if("品牌".equals(str)){
				paramList2.add("brandname");
			}else if("日期".equals(str)){
				paramList2.add("begindate");
			}else if("回笼目标".equals(str)){
				paramList2.add("withdrawntargetamount");
			}else if("回笼毛利率目标%".equals(str)){
				paramList2.add("writeoffratetarget");
			}else if("销售目标".equals(str)){
				paramList2.add("saletargetamount");
			}else if("毛利率目标%".equals(str)){
				paramList2.add("marginratetarget");
			}else{
				paramList2.add("null");
			}
		}
		List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
		List result = new ArrayList();
		if(null != list && list.size() != 0){
			Map map = reportTargetService.importSalesuserBrandSWAData(list,billtype);
			retMap.putAll(map);
		} else{
			retMap.put("excelempty", true);
		}
		addJSONObject(retMap);
		return SUCCESS;
	}
	
    public String addSalesUserTarget() throws Exception {

        String rowstr = request.getParameter("rowstr");
        String billtype = request.getParameter("billtype");

        JSONObject obj = JSONObject.fromObject(rowstr);
        Map<String, Object> map = JSONObject.fromObject(obj);
        if(null != map && !map.isEmpty()) {
            ReportTarget reportTarget = new ReportTarget();
            reportTarget.setBilltype(billtype);
            reportTarget.setState("1");
            String begindate = null != map.get("bqstartdate") ? (String)map.get("bqstartdate") : null;
            //如果日期为空则添加当前日期
            if(null == begindate){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                begindate = sdf.format(new Date());
            }
            reportTarget.setBegindate(begindate);

            String enddate = null != map.get("bqenddate") ? (String)map.get("bqenddate") : null;
            //如果日期为空则添加当前日期
            if(null == enddate){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                enddate = sdf.format(new Date());
            }
            reportTarget.setEnddate(enddate);

            String brand = null != map.get("brand") ? (String)map.get("brand") : null;
            reportTarget.setField01(brand);
            String customerid = null != map.get("customerid") ? (String)map.get("customerid") : null;
            reportTarget.setBusid(customerid);
            String salesuser = null != map.get("salesuser") ? (String)map.get("salesuser") : null;
            reportTarget.setField02(salesuser);

            BigDecimal targets;
            if("".equals(map.get("targets").toString())){
                targets= null != map.get("targets") ? new BigDecimal(0) : null;
            }else{
                targets= null != map.get("targets") ? new BigDecimal(map.get("targets").toString()) : null;
            }
            reportTarget.setTargetamount(targets);

            BigDecimal nweektargets;
            if("".equals(map.get("nweektargets").toString())){
                nweektargets= null != map.get("nweektargets") ? new BigDecimal(0) : null;
            }else{
                nweektargets= null != map.get("nweektargets") ? new BigDecimal(map.get("nweektargets").toString()) : null;
            }
            reportTarget.setField05(nweektargets);
            boolean flag = reportTargetService.addReportTarget(reportTarget);
            addJSONObject("flag", flag);
            addLog("分客户业务员品牌目标考核报表  客户业务员编号："+salesuser+",日期："+begindate+"-"+enddate , flag);

        }
        return SUCCESS;
    }


}

