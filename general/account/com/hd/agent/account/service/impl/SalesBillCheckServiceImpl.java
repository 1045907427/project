/**
 * @(#)SalesBillCheckService.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 19, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.account.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.SalesBillCheckMapper;
import com.hd.agent.account.model.SalesBillCheck;
import com.hd.agent.account.service.ISalesBillCheckService;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.SysParam;

/**
 * 
 * 销售单据核销service实现类
 * @author panxiaoxiao
 */
public class SalesBillCheckServiceImpl extends BaseAccountServiceImpl implements ISalesBillCheckService{
	private SalesBillCheckMapper salesBillCheckMapper;
	
	public SalesBillCheckMapper getSalesBillCheckMapper() {
		return salesBillCheckMapper;
	}

	public void setSalesBillCheckMapper(SalesBillCheckMapper salesBillCheckMapper) {
		this.salesBillCheckMapper = salesBillCheckMapper;
	}

	@Override
	public PageData showSalesBillCheckData(PageMap pageMap)
			throws Exception {
		String sql = getDataAccessRule("t_report_sales_base", "z");
		pageMap.setDataSql(sql);
		boolean isPrintFlag=false;
		if(pageMap.getCondition().containsKey("isPrintFlag")){
			if("true".equals((String)pageMap.getCondition().get("isPrintFlag"))){
				isPrintFlag=true;
			}
		}
		//单据数查询条件
		String paramBillnums = "";
		if(pageMap.getCondition().containsKey("billnums")){
			paramBillnums = (String)pageMap.getCondition().get("billnums");
		}
		Map copyMap = (Map)CommonUtils.deepCopy(pageMap.getCondition());
		Map sumMap = (Map)CommonUtils.deepCopy(pageMap.getCondition());
		SysParam printSysParam = getBaseSysParamMapper().getSysParam("printnum");
		List<Map> retlist = new ArrayList<Map>();
		List<Map> list = salesBillCheckMapper.getSalesBillCheckList(pageMap);
		for(Map map : list){
			if(null != map){
				//销售金额
				BigDecimal salesamount = BigDecimal.ZERO;
				BigDecimal sendamount = ((null != map.get("sendamount")) ? (BigDecimal)map.get("sendamount"):BigDecimal.ZERO);
				BigDecimal returnamount = ((null != map.get("returnamount")) ? (BigDecimal)map.get("returnamount"):BigDecimal.ZERO);
				BigDecimal pushbalanceamount = ((null != map.get("pushbalanceamount")) ? (BigDecimal)map.get("pushbalanceamount"):BigDecimal.ZERO);
				salesamount = sendamount.subtract(returnamount).add(pushbalanceamount);
				map.put("salesamount", salesamount);
				//单据数
				String customerid = (String)map.get("customerid");
				PageMap pageMap2 = new PageMap();
				pageMap2.setCondition(copyMap);
				pageMap2.setDataSql(pageMap.getDataSql());
				pageMap2.getCondition().put("customerid", customerid);
				List<Map> detailNumList = salesBillCheckMapper.getSalesBillCheckDetailNum(pageMap2);
				int billnums = 0;
				for(Map detailNumMap : detailNumList){
					//根据销售单据明细数量计算单据数量
					if(null != detailNumMap){
						int detailNum = Integer.parseInt(detailNumMap.get("detailnum").toString());
						if(null != printSysParam){
							int printnum = Integer.parseInt(printSysParam.getPvalue()) != 0 ? Integer.parseInt(printSysParam.getPvalue()) : detailNum;
							//实际打印单据数
							int num = (int)Math.floor((double)detailNum/printnum);
							if((detailNum % printnum) != 0){
								num++;
							}
							billnums += num;
						}else{
							billnums++;
						}
					}else{
						billnums++;
					}
				}
				//int billnums = salesBillCheckMapper.getSalesBillCheckNum(pageMap2);
				map.put("billnums", billnums);
				//客户名称
				Customer customer = getCustomerByID(customerid);
				if(null != customer){
					map.put("customername", customer.getName());
                    map.put("pcustomerid",customer.getPid());
                    Customer pcustomer = getCustomerByID(customer.getPid());
                    if(null != pcustomer){
                        map.put("pcustomername",pcustomer.getName());
                    }
				}
				//录入销售金额、单据数
				SalesBillCheck salesBillCheck = salesBillCheckMapper.getSalesBillCheckInfoByPageMap(pageMap2);
				if(null != salesBillCheck){
					map.put("inputsalesamount", salesBillCheck.getSalesamount());
					map.put("inputbillnums", salesBillCheck.getBillnums());
					map.put("remark", salesBillCheck.getRemark());
					if((null != salesBillCheck.getSalesamount() && salesBillCheck.getSalesamount().setScale(1, BigDecimal.ROUND_HALF_UP).compareTo(salesamount.setScale(1, BigDecimal.ROUND_HALF_UP)) != 0) || 
						(billnums != salesBillCheck.getBillnums())){
						map.put("eqflag", false);
					}else{
						map.put("eqflag", true);
					}
				}else{
					map.put("eqflag", true);
					map.put("inputsalesamount", "");
					map.put("inputbillnums", "");
					map.put("remark", "");
				}
				String isbillnums = "";
				if(billnums == 0){
					isbillnums = "0";
				}else{
					isbillnums = "1";
				}
				if("0".equals(paramBillnums) && "0".equals(isbillnums)){
					retlist.add(map);
				}else if("1".equals(paramBillnums) && "1".equals(isbillnums)){
					retlist.add(map);
				}else if(StringUtils.isEmpty(paramBillnums)){
					retlist.add(map);
				}
			}
		}
		PageData pageData=null;
		if(!isPrintFlag){
			int count = salesBillCheckMapper.getSalesBillCheckCount(pageMap);
			pageData = new PageData(count,retlist,pageMap);
			
			//合计
			pageMap.getCondition().put("groupcols", "all");
			List<Map> footer = salesBillCheckMapper.getSalesBillCheckList(pageMap);
			for(Map footerMap : footer){
				if(null != footerMap){
					footerMap.put("customerid", "");
					footerMap.put("customername", "合计");
                    footerMap.put("businessdate", "");
					footerMap.put("pcustomerid", "");
					footerMap.put("pcustomername", "");
					//销售金额合计
					BigDecimal salesamountSUM = BigDecimal.ZERO;
					BigDecimal sendamountSUM = ((null != footerMap.get("sendamount")) ? (BigDecimal)footerMap.get("sendamount"):BigDecimal.ZERO);
					BigDecimal returnamountSUM = ((null != footerMap.get("returnamount")) ? (BigDecimal)footerMap.get("returnamount"):BigDecimal.ZERO);
					BigDecimal pushbalanceamountSUM = ((null != footerMap.get("pushbalanceamount")) ? (BigDecimal)footerMap.get("pushbalanceamount"):BigDecimal.ZERO);
					salesamountSUM = sendamountSUM.subtract(returnamountSUM).add(pushbalanceamountSUM);
					footerMap.put("salesamount", salesamountSUM);
					//单据数合计
					PageMap pageMap3 = new PageMap();
					pageMap3.setCondition(sumMap);
					pageMap3.setDataSql(pageMap.getDataSql());
					List<Map> detailNumList = salesBillCheckMapper.getSalesBillCheckDetailNum(pageMap3);
					int billnumSum = 0;
					for(Map detailNumMap : detailNumList){
						//根据销售单据明细数量计算单据数量
						if(null != detailNumMap){
							int detailNum = Integer.parseInt(detailNumMap.get("detailnum").toString());
							if(null != printSysParam){
								int printnum = Integer.parseInt(printSysParam.getPvalue()) != 0 ? Integer.parseInt(printSysParam.getPvalue()) : detailNum;
								//实际打印单据数
								int num = (int)Math.floor((double)detailNum/printnum);
								if((detailNum % printnum) != 0){
									num++;
								}
								billnumSum += num;
							}else{
								billnumSum++;
							}
						}else{
							billnumSum++;
						}
					}
					//int billnums = salesBillCheckMapper.getSalesBillCheckNum(pageMap);
					footerMap.put("billnums", billnumSum);
					Map paramMap = new HashMap();
					paramMap.putAll(pageMap.getCondition());
					Map retMap = salesBillCheckMapper.getSalesBillCheckDataSum(paramMap);
					if(null != retMap){
						footerMap.put("inputsalesamount", retMap.get("inputsalesamount"));
						footerMap.put("inputbillnums", retMap.get("inputbillnums"));
					}

					if("0".equals(paramBillnums) || "1".equals(paramBillnums)){
						footerMap.remove("initsendamount");
						footerMap.remove("sendamount");
						footerMap.remove("pushbalanceamount");
						footerMap.remove("directreturnamount");
						footerMap.remove("checkreturnamount");
						footerMap.remove("returnamount");
					}
				}else{
					footer = new ArrayList<Map>();
				}
			}

			pageData.setFooter(footer);
		}else{
			pageData=new PageData(0, retlist, pageMap);
		}
		return pageData;
	}

	@Override
	public Map addSalesBillCheck(SalesBillCheck salesBillCheck)
			throws Exception {
		Map map2 = new HashMap();
		SysUser sysUser = getSysUser();
		salesBillCheck.setAdduserid(sysUser.getUserid());
		salesBillCheck.setAddusername(sysUser.getName());
		PageMap pageMap = new PageMap();
		String sql = getDataAccessRule("t_report_sales_base", "z");
		pageMap.setDataSql(sql);
		pageMap.getCondition().put("businessdate1", salesBillCheck.getBusinessdate());
		pageMap.getCondition().put("businessdate2", salesBillCheck.getBusinessdate());
		pageMap.getCondition().put("customerid", salesBillCheck.getCustomerid());
		List<Map> list = salesBillCheckMapper.getSalesBillCheckList(pageMap);
		for(Map map :list){
			if(null != map && !map.isEmpty()){
				//销售金额
				BigDecimal salesamount = BigDecimal.ZERO;
				BigDecimal sendamount = ((null != map.get("sendamount")) ? (BigDecimal)map.get("sendamount"):BigDecimal.ZERO);
				BigDecimal returnamount = ((null != map.get("returnamount")) ? (BigDecimal)map.get("returnamount"):BigDecimal.ZERO);
				BigDecimal pushbalanceamount = ((null != map.get("pushbalanceamount")) ? (BigDecimal)map.get("pushbalanceamount"):BigDecimal.ZERO);
				salesamount = sendamount.subtract(returnamount).add(pushbalanceamount).setScale(1, BigDecimal.ROUND_HALF_UP);
				//单据数
				//int billnums = salesBillCheckMapper.getSalesBillCheckNum(pageMap);
				SysParam printSysParam = getBaseSysParamMapper().getSysParam("printnum");
				List<Map> detailNumList = salesBillCheckMapper.getSalesBillCheckDetailNum(pageMap);
				int billnums = 0;
				for(Map detailNumMap : detailNumList){
					//根据销售单据明细数量计算单据数量
					if(null != detailNumMap){
						int detailNum = Integer.parseInt(detailNumMap.get("detailnum").toString());
						if(null != printSysParam){
							int printnum = Integer.parseInt(printSysParam.getPvalue()) != 0 ? Integer.parseInt(printSysParam.getPvalue()) : detailNum;
							//实际打印单据数
							int num = (int)Math.floor((double)detailNum/printnum);
							if((detailNum % printnum) != 0){
								num++;
							}
							billnums += num;
						}else{
							billnums++;
						}
					}else{
						billnums++;
					}
				}
				BigDecimal insalesamount = (null != salesBillCheck.getSalesamount() ? salesBillCheck.getSalesamount() : BigDecimal.ZERO);
				Integer inbillnums = (null != salesBillCheck.getBillnums()) ? salesBillCheck.getBillnums() : 0;
				if((insalesamount.setScale(1, BigDecimal.ROUND_HALF_UP).compareTo(salesamount) != 0) || 
					(billnums != inbillnums)){
					map2.put("eqflag", false);
				}else{
					map2.put("eqflag", true);
				}
				map2.put("inputsalesamount", insalesamount);
				map2.put("inputbillnums", inbillnums);
				map2.put("remark", (null != salesBillCheck.getRemark()) ? salesBillCheck.getRemark() : "");
				boolean flag = salesBillCheckMapper.addSalesBillCheck(salesBillCheck) > 0;
				map2.put("flag", flag);
			}else{
				map2.put("flag", false);
			}
		}
		return map2;
	}

	@Override
	public Map editSalesBillCheck(SalesBillCheck salesBillCheck)
			throws Exception {
		Map map2 = new HashMap();
		SysUser sysUser = getSysUser();
		salesBillCheck.setModifyuserid(sysUser.getUserid());
		salesBillCheck.setModifyusername(sysUser.getName());
		PageMap pageMap = new PageMap();
		String sql = getDataAccessRule("t_report_sales_base", "z");
		pageMap.setDataSql(sql);
		pageMap.getCondition().put("businessdate1", salesBillCheck.getBusinessdate());
		pageMap.getCondition().put("businessdate2", salesBillCheck.getBusinessdate());
		pageMap.getCondition().put("customerid", salesBillCheck.getCustomerid());
		List<Map> list = salesBillCheckMapper.getSalesBillCheckList(pageMap);
		for(Map map :list){
			if(null != map && !map.isEmpty()){
				//销售金额
				BigDecimal salesamount = BigDecimal.ZERO;
				BigDecimal sendamount = ((null != map.get("sendamount")) ? (BigDecimal)map.get("sendamount"):BigDecimal.ZERO);
				BigDecimal returnamount = ((null != map.get("returnamount")) ? (BigDecimal)map.get("returnamount"):BigDecimal.ZERO);
				BigDecimal pushbalanceamount = ((null != map.get("pushbalanceamount")) ? (BigDecimal)map.get("pushbalanceamount"):BigDecimal.ZERO);
				salesamount = sendamount.subtract(returnamount).add(pushbalanceamount).setScale(1, BigDecimal.ROUND_HALF_UP);
				//单据数
				//int billnums = salesBillCheckMapper.getSalesBillCheckNum(pageMap);
				SysParam printSysParam = getBaseSysParamMapper().getSysParam("printnum");
				List<Map> detailNumList = salesBillCheckMapper.getSalesBillCheckDetailNum(pageMap);
				int billnums = 0;
				for(Map detailNumMap : detailNumList){
					//根据销售单据明细数量计算单据数量
					if(null != detailNumMap){
						int detailNum = Integer.parseInt(detailNumMap.get("detailnum").toString());
						if(null != printSysParam){
							int printnum = Integer.parseInt(printSysParam.getPvalue()) != 0 ? Integer.parseInt(printSysParam.getPvalue()) : detailNum;
							//实际打印单据数
							int num = (int)Math.floor((double)detailNum/printnum);
							if((detailNum % printnum) != 0){
								num++;
							}
							billnums += num;
						}else{
							billnums++;
						}
					}else{
						billnums++;
					}
				}
				BigDecimal insalesamount = (null != salesBillCheck.getSalesamount() ? salesBillCheck.getSalesamount() : BigDecimal.ZERO);
				Integer inbillnums = (null != salesBillCheck.getBillnums()) ? salesBillCheck.getBillnums() : 0;
				if((insalesamount.setScale(1, BigDecimal.ROUND_HALF_UP).compareTo(salesamount) != 0) || 
					(billnums != inbillnums)){
					map2.put("eqflag", false);
				}else{
					map2.put("eqflag", true);
				}
				map2.put("inputsalesamount", insalesamount);
				map2.put("inputbillnums", inbillnums);
				map2.put("remark", (null != salesBillCheck.getRemark()) ? salesBillCheck.getRemark() : "");
				boolean flag = salesBillCheckMapper.editSalesBillCheck(salesBillCheck) > 0;
				map2.put("flag", flag);
			}else{
				map2.put("flag", false);
			}
		}
		return map2;
	}

	@Override
	public SalesBillCheck getSalesBillCheckInfo(String customerid,String businessdate)
			throws Exception {
		return salesBillCheckMapper.getSalesBillCheckInfo(customerid,businessdate);
	}
	
	@Override
	public boolean repeatSalesBillCheck(String customerid, String businessdate)
			throws Exception {
		return salesBillCheckMapper.repeatSalesBillCheck(customerid, businessdate) > 0;
	}

	@Override
	public SalesBillCheck getSalesBillCheckInfoByPageMap(PageMap pageMap)
			throws Exception {
		return salesBillCheckMapper.getSalesBillCheckInfoByPageMap(pageMap);
	}

	@Override
	public List<SalesBillCheck> getExportSalesBillCheckList(PageMap pageMap)
			throws Exception {
		List<SalesBillCheck> list = salesBillCheckMapper.getExportSalesBillCheckList(pageMap);
		for(SalesBillCheck salesBillCheck : list){
			Customer customer = getCustomerByID(salesBillCheck.getCustomerid());
			if(null != customer){
				salesBillCheck.setCustomername(customer.getName());
			}
		}
		
		//合计
		SalesBillCheck salesBillCheckSum = new SalesBillCheck();
		Map paramMap = new HashMap();
		paramMap.putAll(pageMap.getCondition());
		Map retMap = salesBillCheckMapper.getSalesBillCheckDataSum(paramMap);
		if(null != retMap){
			salesBillCheckSum.setCustomername("合计");
			salesBillCheckSum.setSalesamount((BigDecimal)retMap.get("inputsalesamount"));
			BigDecimal inputbillnums = (BigDecimal)retMap.get("inputbillnums");
			salesBillCheckSum.setBillnums(Integer.parseInt(inputbillnums.toString()));
		}
		list.add(salesBillCheckSum);
		return list;
	}

	@Override
	public Map addDRSalesBillCheck(List<SalesBillCheck> list) throws Exception {
		boolean flag = true;
		int successNum = 0,failureNum = 0,errorNum = 0;
		String failStr = "",errorVal = "";
		Map returnMap = new HashMap();
		SysUser sysUser = getSysUser();
		for(SalesBillCheck salesBillCheck : list){
			if(null != salesBillCheck){
				try {
					int count = salesBillCheckMapper.repeatSalesBillCheck(salesBillCheck.getCustomerid(),salesBillCheck.getBusinessdate());
					if(count != 0){
						SalesBillCheck salesBillCheck2 = salesBillCheckMapper.getSalesBillCheckInfo(salesBillCheck.getCustomerid(),salesBillCheck.getBusinessdate());
						salesBillCheck2.setBusinessdate(salesBillCheck.getBusinessdate());
						salesBillCheck2.setSalesamount(salesBillCheck.getSalesamount());
						salesBillCheck2.setBillnums(salesBillCheck.getBillnums());
						salesBillCheck2.setRemark(salesBillCheck.getRemark());
						salesBillCheck2.setModifyuserid(sysUser.getUserid());
						salesBillCheck2.setModifyusername(sysUser.getName());
						boolean editflag = salesBillCheckMapper.editSalesBillCheck(salesBillCheck2) > 0;
						if(editflag){
							successNum++;
						}else{
							failureNum++;
							if(StringUtils.isEmpty(failStr)){
								failStr = salesBillCheck.getCustomerid();
							}else{
								failStr += "," + salesBillCheck.getCustomerid();
							}
						}
					}else{
                        //只允许导入 当前日期有数据的客户 的录入信息 其余的不给它导入
						salesBillCheck.setAdduserid(sysUser.getUserid());
						salesBillCheck.setAddusername(sysUser.getName());
						boolean addflag = salesBillCheckMapper.addSalesBillCheck(salesBillCheck) > 0;
						if(addflag){
							successNum++;
						}else{
							failureNum++;
							if(StringUtils.isEmpty(failStr)){
								failStr = salesBillCheck.getCustomerid();
							}else{
								failStr += "," + salesBillCheck.getCustomerid();
							}
						}
					}
					
				} catch (Exception e) {
					if(StringUtils.isEmpty(errorVal)){
						errorVal = salesBillCheck.getCustomerid();
					}
					else{
						errorVal += "," + salesBillCheck.getCustomerid();
					}
					errorNum++;
					continue;
				}
			}
		}
		returnMap.put("flag", flag);
		returnMap.put("success", successNum);
		returnMap.put("failure", failureNum);
		returnMap.put("failStr", failStr);
		returnMap.put("errorNum", errorNum);
		returnMap.put("errorVal", errorVal);
		return returnMap;
	}
}

