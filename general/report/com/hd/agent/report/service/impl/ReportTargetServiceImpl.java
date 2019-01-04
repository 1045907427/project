/**
 * @(#)ReportTargetServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年12月5日 chenwei 创建版本
 */
package com.hd.agent.report.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import com.hd.agent.basefiles.model.Brand;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.report.dao.ReportTargetMapper;
import com.hd.agent.report.model.ReportTarget;
import com.hd.agent.report.service.IReportTargetService;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 各类报表考核目标service实现方法
 * @author chenwei
 */
public class ReportTargetServiceImpl extends BaseFilesServiceImpl implements
		IReportTargetService {

	private ReportTargetMapper reportTargetMapper;

	public ReportTargetMapper getReportTargetMapper() {
		return reportTargetMapper;
	}

	public void setReportTargetMapper(ReportTargetMapper reportTargetMapper) {
		this.reportTargetMapper = reportTargetMapper;
	}

	@Override
	public boolean addReportTarget(ReportTarget reportTarget) throws Exception {
		int t = reportTargetMapper.updateReportTargetState(reportTarget);
		int count = reportTargetMapper.getReportTargetCount(reportTarget);
		reportTarget.setNums(count+1);
		int i = reportTargetMapper.addReportTarget(reportTarget);
		return i>0;
	}

	@Override
	public Map addMapToReportTarget(Map<String, Object> map, String billtype,String businessdate) throws Exception {
		Map map1 = new HashMap();
		boolean flag = false;
		String msg = "";
		ReportTarget reportTarget = null;
		if(null != map && !map.isEmpty()){
			reportTarget = new ReportTarget();
			reportTarget.setBilltype(billtype);

			String begindate = null != map.get("begindate") ? (String)map.get("begindate") : null;
			if(begindate == null){
				begindate = businessdate;
			}

			String branduser = null != map.get("branduser") ? (String)map.get("branduser") : "";
			String customersort = null != map.get("customersort") ? (String)map.get("customersort") : "";
			String brandid = null != map.get("brandid") ? (String)map.get("brandid") : "";
			String salesuser = null != map.get("salesuser") ? (String)map.get("salesuser") : "";
			String salesdept = null != map.get("salesdept") ? (String)map.get("salesdept") : "";

			BigDecimal saletargetamount ;

			if(!"".equals(map.get("saletargetamount").toString())){
				saletargetamount = null != map.get("saletargetamount") ? new BigDecimal(map.get("saletargetamount").toString()) : null;
			}else{
				saletargetamount = null != map.get("saletargetamount") ? new BigDecimal(0) : null;
			}

			BigDecimal marginratetarget;
			if("".equals(map.get("marginratetarget").toString())){
				marginratetarget= null != map.get("marginratetarget") ? new BigDecimal(0) : null;
			}else{
				marginratetarget= null != map.get("marginratetarget") ? new BigDecimal(map.get("marginratetarget").toString()) : null;
			}
			if(null != marginratetarget && marginratetarget.compareTo(new BigDecimal(100)) > 0){
				marginratetarget = new BigDecimal(100);
			}

			BigDecimal withdrawntargetamount ;
			if(!"".equals(map.get("withdrawntargetamount").toString())){
				withdrawntargetamount = null != map.get("withdrawntargetamount") ? new BigDecimal(map.get("withdrawntargetamount").toString()) : null;
			}else{
				withdrawntargetamount = null != map.get("withdrawntargetamount") ? new BigDecimal(0) : null;
			}

			BigDecimal writeoffratetarget ;
			if(!"".equals(map.get("writeoffratetarget").toString())){
				writeoffratetarget = null != map.get("writeoffratetarget") ? new BigDecimal(map.get("writeoffratetarget").toString()) : null;
			}else{
				writeoffratetarget = null != map.get("writeoffratetarget") ? new BigDecimal(0) : null;
			}
			if(null != writeoffratetarget && writeoffratetarget.compareTo(new BigDecimal(100)) > 0){
				writeoffratetarget = new BigDecimal(100);
			}

			//如果日期为空则添加当前日期
			if(null == begindate){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				begindate = sdf.format(new Date());
			}

			reportTarget.setBegindate(begindate);
			if("SalesWithdrawnBranduser".equals(billtype)){
				reportTarget.setBusid(branduser);
			}else if("SalesWithdrawnBrand".equals(billtype)){
				reportTarget.setBusid(brandid);
			}else if("SalesWithdrawnCustomersort".equals(billtype)){
				reportTarget.setBusid(customersort);
			}else if("SalesWithdrawnCustomerUser".equals(billtype)){
				reportTarget.setBusid(salesuser);
			}else if("SalesWithdrawnCustomerBrand".equals(billtype)){
				reportTarget.setBusid(salesuser);
				reportTarget.setField01(brandid);
			}else if("SalesWithdrawnBrandByBrand".equals(billtype)){
				reportTarget.setBusid(branduser);
				reportTarget.setField01(brandid);
			}else if("SalesWithdrawnSalesdeptBrand".equals(billtype)){
				reportTarget.setBusid(salesdept);
				reportTarget.setField01(brandid);
			}

			reportTarget.setTargetamount(saletargetamount);
			reportTarget.setField04(marginratetarget);
			reportTarget.setField05(withdrawntargetamount);
			reportTarget.setField06(writeoffratetarget);
			reportTarget.setState("1");

			if("SalesWithdrawnBranduser".equals(billtype)){
				if(StringUtils.isNotEmpty(branduser)){
					msg = "添加品牌业务员销售回笼考核目标  品牌业务员编号："+branduser+",日期："+begindate;
				}else{
					msg = "添加品牌业务员销售回笼考核目标  品牌业务员编号：其他（未指定品牌业务员）,日期："+begindate;
				}
			}else if("SalesWithdrawnBrand".equals(billtype)){
				if(StringUtils.isNotEmpty(brandid)){
					msg = "添加品牌销售考核回笼目标  品牌编号："+brandid+",日期："+begindate;
				}else{
					msg = "添加品牌销售考核回笼目标  品牌编号：其他（未指定品牌）,日期："+begindate;
				}
			}else if("SalesWithdrawnCustomersort".equals(billtype)){
				if(StringUtils.isNotEmpty(customersort)){
					msg = "添加客户分类销售考核回笼目标  品牌编号："+customersort+",日期："+begindate;
				}else{
					msg = "添加客户分类销售考核回笼目标  品牌编号：其他（未指定客户分类）,日期："+begindate;
				}
			}else if("SalesWithdrawnCustomerUser".equals(billtype)){
				if(StringUtils.isNotEmpty(salesuser)){
					msg = "添加客户业务员销售考核回笼目标  客户业务员编号："+salesuser+",日期："+begindate;
				}else{
					msg = "添加客户业务员销售考核回笼目标  客户业务员编号：其他（未指定客户业务员）,日期："+begindate;
				}
			}else if("SalesWithdrawnCustomerBrand".equals(billtype)){
				if(StringUtils.isNotEmpty(salesuser)){
					msg = "添加客户业务员品牌销售考核回笼目标  客户业务员编号："+salesuser+",日期："+begindate;
				}else{
					msg = "添加客户业务员品牌销售考核回笼目标  客户业务员编号：其他（未指定客户业务员）,日期："+begindate;
				}
			}else if("SalesWithdrawnBrandByBrand".equals(billtype)){
				if(StringUtils.isNotEmpty(branduser)){
					msg = "添加品牌业务员品牌销售考核回笼目标  品牌业务员编号："+branduser+",日期："+begindate;
				}else{
					msg = "添加品牌业务员品牌销售考核回笼目标  品牌业务员编号：其他（未指定品牌业务员）,日期："+begindate;
				}
			}else if("SalesWithdrawnSalesdeptBrand".equals(billtype)){
				if(StringUtils.isNotEmpty(salesdept)){
					msg = "添加销售部门品牌销售考核回笼目标  销售部门编号："+salesdept+",日期："+begindate;
				}else{
					msg = "添加销售部门品牌销售考核回笼目标  销售部门编号：其他（未指定销售部门）,日期："+begindate;
				}
			}
			flag = addReportTarget(reportTarget);
		}
		map1.put("flag",flag);
		map1.put("msg",msg);
		return map1;
	}

	@Override
	public Map importSalesuserBrandSWAData(List<Map<String, Object>> list,String billtype) throws Exception {
		Map retmap1 = new HashMap();

		int index = 2;
		int sucnum = 0,unsucnum = 0;
		String emptyindexs = "",msg = "";
		List<Map> newList = new ArrayList<Map>();
		for(Map<String, Object> map : list){
			String begindate = null != map.get("begindate") ? (String)map.get("begindate") : "";
			String brandname = null != map.get("brandname") ? (String)map.get("brandname") : "";
			String salesusername = null != map.get("salesusername") ? (String)map.get("salesusername") : "";
			String salesdeptname = null != map.get("salesdeptname") ? (String)map.get("salesdeptname") : "";
			if("SalesWithdrawnCustomerBrand".equals(billtype)){
				if(StringUtils.isNotEmpty(begindate) && StringUtils.isNotEmpty(brandname) && StringUtils.isNotEmpty(salesusername)){
					Brand brand = getGoodsBrandByName(brandname);
					if(null != brand){
						map.put("brandid",brand.getId());
					}
					Personnel personnel = getPersonnelByName(salesusername);
					if(null != personnel){
						map.put("salesuser",personnel.getId());
					}
					newList.add(map);
				}else{
					if(StringUtils.isEmpty(emptyindexs)){
						emptyindexs = String.valueOf(index);
					}else{
						emptyindexs += "," + String.valueOf(index);
					}
				}
			}else if("SalesWithdrawnSalesdeptBrand".equals(billtype)){
				if(StringUtils.isNotEmpty(begindate) && StringUtils.isNotEmpty(brandname) && StringUtils.isNotEmpty(salesdeptname)){
					Brand brand = getGoodsBrandByName(brandname);
					if(null != brand){
						map.put("brandid",brand.getId());
					}
					DepartMent departMent = getDepartMentByName(salesdeptname);
					if(null != departMent){
						map.put("salesdept",departMent.getId());
					}
					newList.add(map);
				}else{
					if(StringUtils.isEmpty(emptyindexs)){
						emptyindexs = String.valueOf(index);
					}else{
						emptyindexs += "," + String.valueOf(index);
					}
				}
			}

			index++;
		}
		if(newList.size() != 0){
			for(Map map : newList){
				String begindate = null != map.get("begindate") ? (String)map.get("begindate") : "";
				Map retmap = addMapToReportTarget(map, billtype,begindate);
				boolean flag = retmap.get("flag").equals(true);
				if(flag){
					sucnum++;
				}else{
					unsucnum++;
				}
			}
		}
		if(StringUtils.isNotEmpty(emptyindexs)){
			if("SalesWithdrawnCustomerBrand".equals(billtype)){
				msg = "第"+emptyindexs+"行中的数据，客户业务员、品牌、日期需必填，否则不允许导入数据。";
			}else if("SalesWithdrawnSalesdeptBrand".equals(billtype)){
				msg = "第"+emptyindexs+"行中的数据，销售部门、品牌、日期需必填，否则不允许导入数据。";
			}
		}
		if(StringUtils.isNotEmpty(msg)){
			msg += "<br>" + "导入成功"+sucnum+"条数据，导入失败"+unsucnum+"条数据.";
		}else{
			msg = "导入成功"+sucnum+"条数据，导入失败"+unsucnum+"条数据.";
		}
		retmap1.put("msg",msg);
		return retmap1;
	}

	@Override
	public ReportTarget getReportTargetSumCaseSalesWithdrawnAssess(Map paramMap) throws Exception {
		String billtype = (String)paramMap.get("billtype");
		if("SalesWithdrawnBranduser".equals(billtype)){
			String branduser = null != paramMap.get("branduser") ? (String)paramMap.get("branduser") : "";
			if(StringUtils.isNotEmpty(branduser)){
				paramMap.put("busid",branduser);
			}
		}else if("SalesWithdrawnBrand".equals(billtype)){
			String brandid = null != paramMap.get("brandid") ? (String)paramMap.get("brandid") : "";
			if(StringUtils.isNotEmpty(brandid)){
				paramMap.put("busid",brandid);
			}
			paramMap.remove("brandid");
		}else if("SalesWithdrawnBrandByBrand".equals(billtype)){
			String branduser = null != paramMap.get("branduser") ? (String)paramMap.get("branduser") : "";
			if(StringUtils.isNotEmpty(branduser)){
				paramMap.put("busid",branduser);
			}
		}else if("SalesWithdrawnCustomerBrand".equals(billtype)){
			String salesuser = null != paramMap.get("salesuser") ? (String)paramMap.get("salesuser") : "";
			if(StringUtils.isNotEmpty(salesuser)){
				paramMap.put("busid",salesuser);
			}
		}else if("SalesWithdrawnCustomersort".equals(billtype)){
			String customersort = null != paramMap.get("customersort") ? (String)paramMap.get("customersort") : "";
			if(StringUtils.isNotEmpty(customersort)){
				paramMap.put("busid",customersort);
			}
		}else if("SalesWithdrawnCustomerUser".equals(billtype)){
			String salesuser = null != paramMap.get("salesuser") ? (String)paramMap.get("salesuser") : "";
			if(StringUtils.isNotEmpty(salesuser)){
				paramMap.put("busid",salesuser);
			}
		}else if("SalesWithdrawnSalesdeptBrand".equals(billtype)){
			String salesdept = null != paramMap.get("salesdept") ? (String)paramMap.get("salesdept") : "";
			if(StringUtils.isNotEmpty(salesdept)){
				paramMap.put("busid",salesdept);
			}
		}
		return  reportTargetMapper.getReportTargetSumCaseSalesWithdrawnAssess(paramMap);
	}

	@Override
	public ReportTarget getReportTargetInfo(Map map) throws Exception {
		return reportTargetMapper.getReportTargetInfo(map);
	}

}

