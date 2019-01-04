/**
 * @(#)SalesFreeOrderServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年5月29日 chenwei 创建版本
 */
package com.hd.agent.account.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.CollectionOrderMapper;
import com.hd.agent.account.dao.CustomerCapitalMapper;
import com.hd.agent.account.dao.SalesFreeOrderMapper;
import com.hd.agent.account.model.CustomerCapital;
import com.hd.agent.account.model.ReceivablePastDueReason;
import com.hd.agent.account.model.SalesFreeOrder;
import com.hd.agent.account.service.ISalesFreeOrderService;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.*;
import com.hd.agent.message.model.MsgContent;
import com.hd.agent.message.service.IInnerMessageService;
import com.hd.agent.report.dao.FinanceFundsReturnMapper;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.model.TaskSchedule;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 销售放单service实现类
 * @author chenwei
 */
public class SalesFreeOrderServiceImpl extends BaseAccountServiceImpl implements ISalesFreeOrderService{

	private SalesFreeOrderMapper salesFreeOrderMapper;
	
	private CollectionOrderMapper collectionOrderMapper;
	
	private CustomerCapitalMapper customerCapitalMapper;
	
	private FinanceFundsReturnMapper financeFundsReturnMapper;

	public FinanceFundsReturnMapper getFinanceFundsReturnMapper() {
		return financeFundsReturnMapper;
	}

	public void setFinanceFundsReturnMapper(
			FinanceFundsReturnMapper financeFundsReturnMapper) {
		this.financeFundsReturnMapper = financeFundsReturnMapper;
	}

	public CollectionOrderMapper getCollectionOrderMapper() {
		return collectionOrderMapper;
	}

	public void setCollectionOrderMapper(CollectionOrderMapper collectionOrderMapper) {
		this.collectionOrderMapper = collectionOrderMapper;
	}

	public CustomerCapitalMapper getCustomerCapitalMapper() {
		return customerCapitalMapper;
	}

	public void setCustomerCapitalMapper(CustomerCapitalMapper customerCapitalMapper) {
		this.customerCapitalMapper = customerCapitalMapper;
	}

	public SalesFreeOrderMapper getSalesFreeOrderMapper() {
		return salesFreeOrderMapper;
	}

	public void setSalesFreeOrderMapper(SalesFreeOrderMapper salesFreeOrderMapper) {
		this.salesFreeOrderMapper = salesFreeOrderMapper;
	}

	@Override
	public boolean addSalesFreeOrderByPhone(String jsonstr) throws Exception {
		if(StringUtils.isNotEmpty(jsonstr)){
			SysUser sysUser = getSysUser();
			JSONObject jsonObject = JSONObject.fromObject(jsonstr);
			String customerid = jsonObject.getString("customerid");
			String overreason = CommonUtils.escapeStr(jsonObject.getString("overreason"));
			String commitmentamount = jsonObject.getString("commitmentamount");
			String commitmentdate = jsonObject.getString("commitmentdate");
			
			//新增超账期原因
			ReceivablePastDueReason reason = new ReceivablePastDueReason();
			reason.setCustomerid(customerid);
			reason.setOverreason(overreason);
			reason.setCommitmentamount(new BigDecimal(commitmentamount));
			reason.setCommitmentdate(commitmentdate);
			
			PageMap pageMap = new PageMap();
            String query_sql = " 1=1 and t.customerid = '"+customerid+"' ";
            String query_sql_all = " 1=1 and t.customerid = '"+customerid+"' ";
            String query_sql_begin = " 1=1 and t.customerid = '"+customerid+"' ";
            String query_sql_z = " 1=1 ";
			//小计列
			pageMap.getCondition().put("groupcols", "customerid");
            pageMap.getCondition().put("query_sql", query_sql);
            pageMap.getCondition().put("query_sql_push", query_sql_all);
            pageMap.getCondition().put("query_sql_begin", query_sql_begin);
            pageMap.getCondition().put("query_sql_z", query_sql_z);
            pageMap.getCondition().put("today", CommonUtils.getTodayDataStr());
            pageMap.getCondition().put("ispastdue", "0");
			List<Map<String,Object>> list = financeFundsReturnMapper.showBaseReceivablePassDueListData(pageMap);
            if(null!=list && list.size()>0){
                Map<String,Object> dataObject = list.get(0);
                if(null != dataObject && !dataObject.isEmpty()){
                    BigDecimal saleamount = (BigDecimal) dataObject.get("saleamount");
                    BigDecimal unpassamount = (BigDecimal) dataObject.get("unpassamount");
                    BigDecimal totalpassamount = (BigDecimal) dataObject.get("totalpassamount");
                    reason.setSaleamount(saleamount);
                    reason.setUnpassamount(unpassamount);
                    reason.setTotalpassamount(totalpassamount);
                }
            }
			reason.setAdduserid(sysUser.getUserid());
			Map map = addCustomerReceivablePastDueReason(reason);
			boolean flag = false;
			if(map.containsKey("flag")){
				flag = (Boolean) map.get("flag");
			}
			return flag;
		}else{
			return false;
		}
	}
	
	
	@Override
	public PageData getSalesFreeOrderData(PageMap pageMap) throws Exception {
		List<SalesFreeOrder> list = salesFreeOrderMapper.getSalesFreeOrderList(pageMap);
		for(SalesFreeOrder salesFreeOrder : list){
			Customer customer = getCustomerByID(salesFreeOrder.getCustomerid());
			if(null != customer){
				salesFreeOrder.setCustomername(customer.getName());
			}
			if(StringUtils.isNotEmpty(salesFreeOrder.getFreetype())){
				if("1".equals(salesFreeOrder.getFreetype())){
					salesFreeOrder.setFreetypename("按审核当天放");
				}else if("2".equals(salesFreeOrder.getFreetype())){
					salesFreeOrder.setFreetypename("按指定单据号放");
				}
			}
			SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(salesFreeOrder.getStatus(), "status");
			if(null != sysCode){
				salesFreeOrder.setStatusname(sysCode.getCodename());
			}
			Personnel personnel = getPersonnelById(salesFreeOrder.getApplyid());
			if(null != personnel){
				salesFreeOrder.setApplyname(personnel.getName());
			}
		}
		PageData pageData = new PageData(salesFreeOrderMapper.getSalesFreeOrderListCount(pageMap),list,pageMap);
		return pageData;
	}

	@Override
	public Map auditSalesFreeOrder(String ids) throws Exception {
		Map retMap = new HashMap();
		SysUser sysUser = getSysUser();
		String[] idsArr = ids.split(",");
		boolean flag = false;
		List<SalesFreeOrder> auditList = new ArrayList<SalesFreeOrder>();
		for(String id : idsArr){
			SalesFreeOrder order = salesFreeOrderMapper.getSalesFreeOrderByID(id);
			order.setAudituserid(sysUser.getUserid());
			order.setAuditusername(sysUser.getName());
			auditList.add(order);
		}
		if(auditList.size() > 0){
			flag = salesFreeOrderMapper.auditSalesFreeOrderBatch(auditList) > 0;
		}
		retMap.put("flag", flag);
		return retMap;
	}

	@Override
	public Map oppauditSalesFreeOrder(String ids) throws Exception {
		Map retMap = new HashMap();
		String[] idsArr = ids.split(",");
		boolean flag = salesFreeOrderMapper.oppauditSalesFreeOrderBatch(idsArr) > 0;
		retMap.put("flag", flag);
		return retMap;
	}

	@Override
	public boolean deleteSalesFreeOrder(String ids) throws Exception {
		String[] idsArr = ids.split(",");
		boolean flag = salesFreeOrderMapper.deleteSalesFreeOrder(idsArr) > 0;
		return flag;
	}

	@Override
	public PageData showBaseReceivablePassDueReasonListDataTest(PageMap pageMap)
			throws Exception {
		SysUser sysUser = getSysUser();
		pageMap.getCondition().put("sysuserid", sysUser.getUserid());
		String dataSql = getDataAccessRule("t_report_reason_base", "z");
		pageMap.setDataSql(dataSql);
		String query_sql = " 1=1 ";
		String query_sql_all = " 1=1 ";
		String customerids = "";
		boolean queryFlag = false;
		Map queryMap = new HashMap();
		if(pageMap.getCondition().containsKey("customerid")){
			String str = (String) pageMap.getCondition().get("customerid");
			str = StringEscapeUtils.escapeSql(str);
			queryMap.put("customerid", str);
		}
		if(pageMap.getCondition().containsKey("pcustomerid")){
			String str = (String) pageMap.getCondition().get("pcustomerid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t.pcustomerid = '"+str+"' ";
				query_sql_all += " and t.pcustomerid = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t.pcustomerid,'"+str+"')";
				query_sql_all += " and FIND_IN_SET(t.pcustomerid,'"+str+"')";
			}
		} 
		if(pageMap.getCondition().containsKey("salesuser")){
			String str = (String) pageMap.getCondition().get("salesuser");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t.salesuser = '"+str+"' ";
				query_sql_all += " and t.salesuser = '"+str+"' ";
			}else{
				query_sql += " and FIND_IN_SET(t.salesuser,'"+str+"')";
				query_sql_all += " and FIND_IN_SET(t.salesuser,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("branduser")){
			String str = (String) pageMap.getCondition().get("branduser");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t1.branduser = '"+str+"' ";
				query_sql_all += " and t1.branduser = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t1.branduser,'"+str+"')";
				query_sql_all += " and FIND_IN_SET(t1.branduser,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("branddept")){
			String str = (String) pageMap.getCondition().get("branddept");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t1.branddept like '"+str+"%' ";
				query_sql_all += " and t1.branddept like '"+str+"%' ";
			}else{
				query_sql += " and FIND_IN_SET(t1.branddept,'"+str+"')";
				query_sql_all += " and FIND_IN_SET(t1.branddept,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("salesarea")){
			String str = (String) pageMap.getCondition().get("salesarea");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t.salesarea like '"+str+"%' ";
				query_sql_all += " and t.salesarea like '"+str+"%' ";
			}else{
				query_sql += " and FIND_IN_SET(t.salesarea,'"+str+"')";
				query_sql_all += " and FIND_IN_SET(t.salesarea,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("payeeid")){
			String str = (String) pageMap.getCondition().get("payeeid");
			str = StringEscapeUtils.escapeSql(str);
			queryMap.put("payeeid", str);
		}
		if(pageMap.getCondition().containsKey("commitmentdate1")){
			String str = (String) pageMap.getCondition().get("commitmentdate1");
			str = StringEscapeUtils.escapeSql(str);
			queryMap.put("commitmentdate1", str);
		}
		if(pageMap.getCondition().containsKey("commitmentdate2")){
			String str = (String) pageMap.getCondition().get("commitmentdate2");
			str = StringEscapeUtils.escapeSql(str);
			queryMap.put("commitmentdate2", str);
		}
		if(!queryMap.isEmpty()){
			List<ReceivablePastDueReason> list = salesFreeOrderMapper.getReceivablePastDueReasonByMap(queryMap);
			if(list.size() != 0){
				for(ReceivablePastDueReason reason : list){
					if(null != reason){
						if(StringUtils.isEmpty(customerids)){
							customerids = reason.getCustomerid();
						}else{
							if(customerids.indexOf(reason.getCustomerid()) == -1){
								customerids += "," + reason.getCustomerid();
							}
						}
					}
				}
			}
		}
		if(StringUtils.isNotEmpty(customerids)){
			query_sql += " and FIND_IN_SET(t.customerid,'"+customerids+"')";
			query_sql_all += " and FIND_IN_SET(t.customerid,'"+customerids+"')";
		}else if(!queryMap.isEmpty()){
			queryFlag = true;
		}
		//小计列
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		if(!pageMap.getCondition().containsKey("groupcols")){
			pageMap.getCondition().put("groupcols", "");
			groupcols = "customerid,goodsid";
		}
		pageMap.getCondition().put("query_sql", query_sql);
		pageMap.getCondition().put("query_sql_all", query_sql_all);
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
		
		PageMap copyPageMap = new PageMap();
		Map copyMap = (Map)CommonUtils.deepCopy(pageMap.getCondition());
		copyPageMap.setDataSql(dataSql);
		copyPageMap.setCondition(copyMap);
		
		PageData pageData = null;
		if(!queryFlag){
			List<Map<String,Object>> list = salesFreeOrderMapper.showBaseReceivablePassDueReasonListData(pageMap);
			for(Map<String,Object> dataObject : list){
				
				BigDecimal returnamount = (BigDecimal) dataObject.get("returnamount");
				BigDecimal saleamount = (BigDecimal) dataObject.get("saleamount");
				
				String customerid = (String) dataObject.get("customerid");
				String pcustomerid = (String) dataObject.get("pcustomerid");
				String salesarea = (String) dataObject.get("salesarea");
				String salesdept = (String) dataObject.get("salesdept");
				String salesuser = (String) dataObject.get("salesuser");
				String goodsid = (String) dataObject.get("goodsid");
				String brandid = (String) dataObject.get("brandid");
				String branduser = (String) dataObject.get("branduser");
				String branddept = (String) dataObject.get("branddept");
				
				//根据客户获取超账期原因
				ReceivablePastDueReason reason = salesFreeOrderMapper.getCustomerReceivablePastDueReason(customerid);
				if(null != reason){
					dataObject.put("overreason", reason.getOverreason());
					dataObject.put("commitmentamount", reason.getCommitmentamount());
					dataObject.put("commitmentdate", reason.getCommitmentdate());
					//实际到款金额
					if(StringUtils.isNotEmpty(reason.getCommitmentdate())){
						Map paramMap = new HashMap();
						paramMap.put("customerid", customerid);
						paramMap.put("businessdate1", reason.getAddtime());
						paramMap.put("businessdate2", reason.getCommitmentdate());
						BigDecimal actualamount = collectionOrderMapper.getCollectionOrderAmountSum(paramMap);
						if(null != actualamount){
							dataObject.put("actualamount", actualamount);
						}
					}
				}
				//收款人
				Customer customer2 = getCustomerByID(customerid);
				if(null != customer2){
					if(StringUtils.isNotEmpty(customer2.getPayeeid())){
						Personnel personnel = getPersonnelById(customer2.getPayeeid());
						if(null != personnel){
							dataObject.put("payeename", personnel.getName());
						}
					}
				}
				//变更次数
				int changenum = salesFreeOrderMapper.getCustomerReceivablePastDueReasonChangenum(customerid);
				dataObject.put("changenum", changenum);
				//余额
				CustomerCapital capital = customerCapitalMapper.getCustomerCapital(customerid);
				if(null != capital){
					dataObject.put("cstmerbalance", capital.getAmount());
				}
				if(groupcols.indexOf("customerid")!=-1){
					Customer customer = getCustomerByID(customerid);
					if(null!=customer){
						if(StringUtils.isNotEmpty(customer.getSettletype())){
							dataObject.put("settletype", customer.getSettletype());
							Settlement settlement = getSettlementByID(customer.getSettletype());
							if(null != settlement){
								dataObject.put("settletypename", settlement.getName());
							}
						}
						dataObject.put("customername", customer.getName());
					}else{
						dataObject.put("customername", "未指定客户");
					}
					if(groupcols.indexOf("pcustomerid")==-1){
						Customer pcustomer = getCustomerByID(pcustomerid);
						if(null!=pcustomer ){
							dataObject.put("pcustomername", pcustomer.getName());
						}
					}
					if(groupcols.indexOf("salesdept")==-1){
						DepartMent departMent = getDepartmentByDeptid(salesdept);
						if(null!=departMent){
							dataObject.put("salesdeptname", departMent.getName());
						}else{
							dataObject.put("salesdeptname", "其他(未指定销售部门)");
						}
					}
					if(groupcols.indexOf("salesarea")==-1){
						SalesArea salesArea = getSalesareaByID(salesarea);
						if(null!=salesArea){
							dataObject.put("salesareaname", salesArea.getName());
						}else{
							dataObject.put("salesareaname","其他（未指定销售区域）");
						}
					}
					if(groupcols.indexOf("salesuser")==-1){
						Personnel personnel = getPersonnelById(salesuser);
						if(null!=personnel){
							dataObject.put("salesusername", personnel.getName());
						}else{
							dataObject.put("salesusername","其他（未指定客户业务员）");
						}
					}
				}
				if(groupcols.indexOf("branduser")!=-1){
					Personnel personnel = getPersonnelById(branduser);
					if(null!=personnel){
						dataObject.put("brandusername", personnel.getName());
					}else{
						dataObject.put("brandusername","其他（未指定品牌业务员）");
					}
				}
			}
			
			pageData = new PageData(salesFreeOrderMapper.showBaseReceivablePassDueReasonListCount(pageMap),list,pageMap);
			//取合计数据
			pageMap.getCondition().put("groupcols", "all");
			List<Map<String,Object>> footer = salesFreeOrderMapper.showBaseReceivablePassDueReasonListData(pageMap);
			for(Map<String,Object> map : footer){
				if(null!=map){
					BigDecimal returnamount = (BigDecimal) map.get("returnamount");
					BigDecimal saleamount = (BigDecimal) map.get("saleamount");
					map.put("customerid", "");
					map.put("customername", "合计");
					map.put("overreason", "");
					map.put("commitmentamount", "");
					map.put("commitmentdate", "");
					//合计客户余额
					List<Map<String,Object>> sumList = new ArrayList<Map<String,Object>>();
					if(!pageMap.getCondition().containsKey("isflag")){
						copyPageMap.getCondition().put("isflag", "true");
						sumList = salesFreeOrderMapper.showBaseReceivablePassDueReasonListData(copyPageMap);
					}else{
						sumList.addAll(list);
					}
					String customerstr = "";
					for(Map<String,Object> map2 : sumList){
						String customerid = (String) map2.get("customerid");
						if(StringUtils.isEmpty(customerid)){
							customerstr = customerid;
						}else{
							customerstr += "," + customerid;
						}
					}
					if(StringUtils.isNotEmpty(customerstr)){
						BigDecimal amountSum = customerCapitalMapper.getCustomerCapitalSum(customerstr);
						if(null != amountSum){
							map.put("cstmerbalance", amountSum);
						}else{
							map.put("cstmerbalance", "");
						}
					}
				}else{
					footer = new ArrayList<Map<String,Object>>();
				}
			}
			pageData.setFooter(footer);
		}else{
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			pageData = new PageData(0,list,pageMap);
			pageData.setFooter(list);
		}
		
		return pageData;
	}

	@Override
	public PageData showBaseReceivablePassDueReasonListData(PageMap pageMap)throws Exception{
		SysUser sysUser = getSysUser();
		pageMap.getCondition().put("sysuserid", sysUser.getUserid());
		String dataSql = getDataAccessRule("t_report_reason_base", "z");
		pageMap.setDataSql(dataSql);
		String query_sql = " 1=1 ";
		String reason_sql = " 1=1 ";
		String query_sql_begin = "1=1";
		String query_sql_z = " 1=1 ";
		String customerids = "";
		if(pageMap.getCondition().containsKey("customerid")){
			String str = (String) pageMap.getCondition().get("customerid");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and FIND_IN_SET(t.customerid,'"+str+"')";
			reason_sql += " and FIND_IN_SET(t.customerid,'"+str+"')";
			query_sql_begin += " and FIND_IN_SET(t.customerid,'"+str+"')";

		}
		if(pageMap.getCondition().containsKey("pcustomerid")){
			String str = (String) pageMap.getCondition().get("pcustomerid");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and (FIND_IN_SET(t.pcustomerid,'"+str+"') or FIND_IN_SET(t.customerid,'"+str+"'))";
			reason_sql += " and (FIND_IN_SET(z.pcustomerid,'"+str+"') or FIND_IN_SET(z.customerid,'"+str+"'))";
			query_sql_begin += " and (FIND_IN_SET(t.pcustomerid,'"+str+"') or FIND_IN_SET(t.customerid,'"+str+"'))";
		}
		if(pageMap.getCondition().containsKey("salesuser")){
			String str = (String) pageMap.getCondition().get("salesuser");
			str = StringEscapeUtils.escapeSql(str);
            if("null".equals(str)){
                query_sql += " and t.salesuser = ''";
				reason_sql += " and z.salesuser = ''";
				query_sql_begin += " and t.salesuser = ''";
            }else{
                query_sql += " and FIND_IN_SET(t.salesuser,'"+str+"')";
				reason_sql += " and FIND_IN_SET(z.salesuser,'"+str+"')";
				query_sql_begin += " and FIND_IN_SET(t.salesuser,'"+str+"')";
            }
		}
		if(pageMap.getCondition().containsKey("branduser")){
			String str = (String) pageMap.getCondition().get("branduser");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and FIND_IN_SET(t1.branduser,'"+str+"')";
			query_sql_z += " and FIND_IN_SET(z.branduser,'"+str+"')";
		}
		if(pageMap.getCondition().containsKey("branddept")){
			String str = (String) pageMap.getCondition().get("branddept");
			str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                if(pageMap.getCondition().containsKey("branddept") && "null".equals(pageMap.getCondition().get("branddept"))){
                    query_sql += " and t1.branddept = '' ";
					query_sql_z += " and z.branddept = '' ";
                }else{
                    query_sql += " and t1.branddept like '"+str+"%' ";
					query_sql_z += " and z.branddept like '"+str+"%' ";
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
				query_sql_z += " and FIND_IN_SET(z.branddept,'"+retStr+"')";
            }
		}
		if(pageMap.getCondition().containsKey("salesdept")){
			String str = (String) pageMap.getCondition().get("salesdept");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				if(pageMap.getCondition().containsKey("salesdept") && "null".equals(pageMap.getCondition().get("salesdept"))){
					query_sql += " and t.salesdept = '' ";
					reason_sql += " and z.salesdept = '' ";
					query_sql_begin += " and t.salesdept = '' ";
				}else{
					query_sql += " and t.salesdept like '"+str+"%' ";
					reason_sql += " and z.salesdept like '"+str+"%' ";
					query_sql_begin += " and t.salesdept like '"+str+"%' ";
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
				reason_sql += " and FIND_IN_SET(z.salesdept,'"+retStr+"')";
				query_sql_begin += " and FIND_IN_SET(t.salesdept,'"+retStr+"')";
			}
		}
		if(pageMap.getCondition().containsKey("salesarea")){
			String str = (String) pageMap.getCondition().get("salesarea");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and FIND_IN_SET(t.salesarea,'"+str+"')";
			reason_sql += " and FIND_IN_SET(z.salesarea,'"+str+"')";
			query_sql_begin += " and FIND_IN_SET(t.salesarea,'"+str+"')";
		}
		if(pageMap.getCondition().containsKey("payeeid")){
			String str = (String) pageMap.getCondition().get("payeeid");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and FIND_IN_SET(c.payeeid,'"+str+"')";
			reason_sql += " and FIND_IN_SET(z.payeeid,'"+str+"')";
			query_sql_begin += " and FIND_IN_SET(c.payeeid,'"+str+"')";
		}
		if(pageMap.getCondition().containsKey("commitmentdate1")){
			String str = (String) pageMap.getCondition().get("commitmentdate1");
			str = StringEscapeUtils.escapeSql(str);
			reason_sql += " and s.commitmentdate >= '"+str+"'";
		}
		if(pageMap.getCondition().containsKey("commitmentdate2")){
			String str = (String) pageMap.getCondition().get("commitmentdate2");
			str = StringEscapeUtils.escapeSql(str);
			reason_sql += " and s.commitmentdate <= '"+str+"'";
		}
		//小计列
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		pageMap.getCondition().put("groupcolsall", "");
		if(!pageMap.getCondition().containsKey("groupcols")){
			pageMap.getCondition().put("groupcols", "");
			groupcols = "customerid,goodsid";
		}
		pageMap.getCondition().put("query_sql", query_sql);
        reason_sql = reason_sql.replace("z.","t.");
		pageMap.getCondition().put("reason_sql", reason_sql);

        String query_sql_push=query_sql.replace("t1", "t");
        query_sql_push=query_sql_push.replace("goodsid", "brandid");
        pageMap.getCondition().put("query_sql_push", query_sql_push);
        pageMap.getCondition().put("query_sql", query_sql);
		pageMap.getCondition().put("query_sql_begin", query_sql_begin);
		pageMap.getCondition().put("query_sql_z",query_sql_z);
		//排序
		String orderstr = "";
		if(pageMap.getCondition().containsKey("sort")){
			String sort = (String) pageMap.getCondition().get("sort");
			String order = (String) pageMap.getCondition().get("order");
			if("cstmerbalance".equals(sort)){
				sort = "amount";
			}
			if(null==order){
				order = "asc";
			}
			orderstr = sort +" " +order;
		}
		pageMap.getCondition().put("orderstr", orderstr);

		//根据小计列分组判断表数据调用那一个方法：客户应收款分析表（customerid）调用（BaseReceivablePassDueReasonListData_sql），分品牌业务员分析表（customerid,branduser）调用（CSTBranduserReceivablePassDueReasonListData_sql）
		if("customerid".equals(groupcols)){
			pageMap.getCondition().put("iscustomersql", true);
		}else if("customerid,branduser".equals(groupcols)){
			pageMap.getCondition().put("iscstbrandusersql", true);
		}else{
			pageMap.getCondition().put("iscustomersql", true);
		}

        String pass_sql="";
        String column_sum="";
        String column="";
        String column_pass = "";
        String column_reson = "";
        List<Map<String,Object>> RSlist = financeFundsReturnMapper.getBaseReceivablePassDueListDataRS(pageMap);
        int RSCount = financeFundsReturnMapper.getBaseReceivablePassDueListDataRSCount(pageMap);
        for(Map<String,Object> rsMap :RSlist){
            int beginday =(Integer)rsMap.get("beginday");
            int endday =(Integer)rsMap.get("endday");
            int seq =(Integer)rsMap.get("seq");
            column_sum=column_sum+",sum(z.passamount"+seq+") as passamount"+seq;
            column=column+",0 as passamount"+seq;
            column_reson = column_reson +",t.passamount"+seq;
            column_pass = column_pass +",0 as passamount"+seq;
            pass_sql=pass_sql+"union all select t.customerid,t.pcustomerid,t.salesarea,t.salesdept,t.salesuser,c.payeeid,t1.goodsid AS goodsid,t1.brandid,t1.branduser,t1.branddept,0 AS beginamount,0 AS saleamount, 0 AS salenotaxamount, 0 as returnamount, 0 as pushamount,0 as costamount,0 as unpassamount ,0 as totalpassamount";
            int v=1;
            while(v<RSCount+1){
                if(v!=seq){
                    pass_sql=pass_sql+",0 as passamount"+v;
                }
                if(v==seq){
                    pass_sql=pass_sql+",t1.taxamount as passamount"+v;
                }
                v=v+1;
            }
            String beginDate = CommonUtils.getBeforeDateInDays(beginday);
            pass_sql=pass_sql+" from t_storage_saleout t RIGHT JOIN t_storage_saleout_detail t1 on t.id=t1.saleoutid LEFT JOIN t_base_sales_customer c ON c.id = t.customerid where  t1.duefromdate<= '"+beginDate+"' ";
            if(endday!=0){
                String enddateStr = CommonUtils.getBeforeDateInDays(new Date(),endday);
                pass_sql=pass_sql+" and (t1.duefromdate='' or t1.duefromdate>= '"+enddateStr+"') ";
            }
            String today = CommonUtils.getTodayDataStr();
            pass_sql=pass_sql+" and "+query_sql+" and t1.duefromdate<>'' and t1.duefromdate < '"+today+"' and t1.iswriteoff='0' and t.status in('3','4') ";
            //退货的超账期
            pass_sql=pass_sql+"union all select t.customerid,t.pcustomerid,t.salesarea,t.salesdept,t.salesuser,c.payeeid,t1.goodsid AS goodsid,t1.brandid,t1.branduser,t1.branddept,0 AS beginamount,0 AS saleamount, 0 AS salenotaxamount, 0 as returnamount, 0 as pushamount,0 as costamount,0 as unpassamount ,0 as totalpassamount";
            v=1;
            while(v<RSCount+1){
                if(v!=seq){
                    pass_sql=pass_sql+",0 as passamount"+v;
                }
                if(v==seq){
                    pass_sql=pass_sql+",-t1.taxamount as passamount"+v;
                }
                v=v+1;
            }
            pass_sql=pass_sql+" from t_storage_salereject_enter t RIGHT JOIN t_storage_salereject_enter_detail t1 on t.id=t1.salerejectid LEFT JOIN t_base_sales_customer c ON c.id = t.customerid where  t1.duefromdate<= '"+beginDate+"' ";
            if(endday!=0){
                String enddateStr = CommonUtils.getBeforeDateInDays(new Date(),endday);
                pass_sql=pass_sql+" and (t1.duefromdate='' or t1.duefromdate>= '"+enddateStr+"') ";
            }
            pass_sql=pass_sql+" and "+query_sql+" and t1.duefromdate<>'' and t1.duefromdate < '"+today+"' and t.ischeck='1' and t1.iswriteoff='0' and t.status in('3','4') ";
			//应收款期初
			pass_sql=pass_sql+"union all select t.customerid,t.pcustomerid,t.salesarea,t.salesdept,t.salesuser,c.payeeid,'QC' goodsid,'QC' brandid,'QC' branduser,'QC' branddept,0 AS beginamount,0 AS saleamount, 0 AS salenotaxamount, 0 as returnamount, 0 as pushamount,0 as costamount,0 as unpassamount ,0 as totalpassamount";
			v=1;
			while(v<RSCount+1){
				if(v!=seq){
					pass_sql=pass_sql+",0 as passamount"+v;
				}
				if(v==seq){
					pass_sql=pass_sql+",t.amount as passamount"+v;
				}
				v=v+1;
			}
			pass_sql=pass_sql+" from t_account_begin_amount t  LEFT JOIN t_base_sales_customer c ON c.id = t.customerid where  t.duefromdate<= '"+beginDate+"' ";
			if(endday!=0){
				String enddateStr = CommonUtils.getBeforeDateInDays(new Date(),endday);
				pass_sql=pass_sql+" and (t.duefromdate='' or t.duefromdate>= '"+enddateStr+"') ";
			}
			pass_sql=pass_sql+" and "+query_sql_begin+" and t.duefromdate<>'' and t.duefromdate < '"+today+"' and t.iswriteoff='0' and t.status = '3' ";
        }
        pageMap.getCondition().put("pass_sql", pass_sql);
        pageMap.getCondition().put("column_sum", column_sum);
        pageMap.getCondition().put("column", column);
        pageMap.getCondition().put("column_reson", column_reson);
        pageMap.getCondition().put("column_pass", column_pass);
        pageMap.getCondition().put("today", CommonUtils.getTodayDataStr());
		List<Map<String,Object>> list = salesFreeOrderMapper.showBaseReceivablePassDueReasonListData(pageMap);
		for(Map<String,Object> dataObject : list){
			String customerid = (String) dataObject.get("customerid");
			String pcustomerid = (String) dataObject.get("pcustomerid");
			String salesarea = (String) dataObject.get("salesarea");
			String salesdept = (String) dataObject.get("salesdept");
			String salesuser = (String) dataObject.get("salesuser");
			String goodsid = (String) dataObject.get("goodsid");
			String brandid = (String) dataObject.get("brandid");
			String branduser = (String) dataObject.get("branduser");
			String branddept = (String) dataObject.get("branddept");

			//实际到款金额
			if(null != dataObject.get("commitmentdate") && null != dataObject.get("addtime")){
				Map paramMap = new HashMap();
				paramMap.put("customerid", customerid);
				String addtime = dataObject.get("addtime").toString();
				SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
				paramMap.put("businessdate1", fm.parse(addtime));
				paramMap.put("businessdate2", (String)dataObject.get("commitmentdate"));
				
				BigDecimal actualamount = collectionOrderMapper.getCollectionOrderAmountSum(paramMap);
				if(null != actualamount){
					dataObject.put("actualamount", actualamount);
				}
			}
			//收款人
			if(null != dataObject.get("payeeid")){
				Personnel personnel = getPersonnelById(dataObject.get("payeeid").toString());
				if(null != personnel){
					dataObject.put("payeename", personnel.getName());
				}
			}
			//变更次数
			int changenum = salesFreeOrderMapper.getCustomerReceivablePastDueReasonChangenum(customerid);
			dataObject.put("changenum", changenum);

            //余额
            BigDecimal cstmerbalance = (null != dataObject.get("amount")) ? (BigDecimal)dataObject.get("amount") :BigDecimal.ZERO;
            dataObject.put("cstmerbalance", cstmerbalance);
			//实际应收款 = 应收款-客户余额
			BigDecimal saleamount = (null != dataObject.get("saleamount")) ? (BigDecimal)dataObject.get("saleamount") :BigDecimal.ZERO;
			dataObject.put("realsaleamount", saleamount.subtract(cstmerbalance));
			
			if(groupcols.indexOf("customerid")!=-1){
				Customer customer = getCustomerByID(customerid);
				if(null!=customer){
					if(StringUtils.isNotEmpty(customer.getSettletype())){
						dataObject.put("settletype", customer.getSettletype());
						Settlement settlement = getSettlementByID(customer.getSettletype());
						if(null != settlement){
							dataObject.put("settletypename", settlement.getName());
						}
                        dataObject.put("settleday", customer.getSettleday());
					}
					dataObject.put("customername", customer.getName());
				}else{
					dataObject.put("customername", "未指定客户");
				}
				if(groupcols.indexOf("pcustomerid")==-1){
					Customer pcustomer = getCustomerByID(pcustomerid);
					if(null!=pcustomer ){
						dataObject.put("pcustomername", pcustomer.getName());
					}
				}
				if(groupcols.indexOf("salesdept")==-1){
					DepartMent departMent = getDepartmentByDeptid(salesdept);
					if(null!=departMent){
						dataObject.put("salesdeptname", departMent.getName());
					}else{
						dataObject.put("salesdeptname", "其他(未指定销售部门)");
					}
				}
				if(groupcols.indexOf("salesarea")==-1){
					SalesArea salesArea = getSalesareaByID(salesarea);
					if(null!=salesArea){
						dataObject.put("salesareaname", salesArea.getName());
					}else{
						dataObject.put("salesareaname","其他（未指定销售区域）");
					}
				}
				if(groupcols.indexOf("salesuser")==-1){
					Personnel personnel = getPersonnelById(salesuser);
					if(null!=personnel){
						dataObject.put("salesusername", personnel.getName());
					}else{
						dataObject.put("salesusername","其他（未指定客户业务员）");
					}
				}
				if(groupcols.indexOf("branddept")==-1){
					DepartMent departMent = getDepartmentByDeptid(branddept);
					if(null!=departMent){
						dataObject.put("branddeptname", departMent.getName());
					}else{
						dataObject.put("branddeptname", "其他(未指定品牌部门)");
					}
				}
			}
			if(groupcols.indexOf("branduser")!=-1){
				Personnel personnel = getPersonnelById(branduser);
				if(null!=personnel){
					dataObject.put("brandusername", personnel.getName());
				}else{
					if("QC".equals(branduser)){
						dataObject.put("brandusername","应收款期初");
					}else{
						dataObject.put("brandusername","其他（未指定品牌业务员）");
					}
				}
			}
            if(groupcols.indexOf("salesuser")!=-1){
                Personnel personnel = getPersonnelById(salesuser);
                if(null!=personnel){
                    dataObject.put("salesusername", personnel.getName());
                }else{
                    dataObject.put("salesusername","其他（未指定客户业务员）");
                }
            }
		}
		
		PageData pageData = new PageData(salesFreeOrderMapper.showBaseReceivablePassDueReasonListCount(pageMap),list,pageMap);
		//取合计数据
        pageMap.getCondition().put("groupcolsall", "all");
		List<Map<String,Object>> footer = salesFreeOrderMapper.showBaseReceivablePassDueReasonListData(pageMap);

		for(Map<String,Object> map : footer){
			if(null!=map){
                map.put("commitmentamount","");
                if(groupcols.indexOf("customerid")!=-1){
					map.put("customerid", "");
                    map.put("customername", "合计");
                }else if(groupcols.indexOf("salesuser")!=-1){
					map.put("salesuser", "");
                    map.put("salesusername", "合计");
                }
				if("customerid".equals(groupcols)){
					BigDecimal amount = null != map.get("amount") ? (BigDecimal)map.get("amount") : BigDecimal.ZERO;
					map.put("cstmerbalance", amount);
					//实际应收款 = 应收款-客户余额
					BigDecimal saleamountSum = (null != map.get("saleamount")) ? (BigDecimal)map.get("saleamount") :BigDecimal.ZERO;
					map.put("realsaleamount", saleamountSum.subtract(amount));
				}
				map.put("overreason", "");
				map.put("commitmentdate", "");
			}else{
				footer = new ArrayList<Map<String,Object>>();
			}
		}
		pageData.setFooter(footer);
		
		return pageData;
	}
	
	@Override
	public Map addCustomerReceivablePastDueReason(
			ReceivablePastDueReason receivablePastDueReason) throws Exception {
		Map map = new HashMap();
		SysUser sysUser = getSysUser();
		if(null==sysUser){
			sysUser = getSysUserById(receivablePastDueReason.getAdduserid());
		}
		receivablePastDueReason.setAdduserid(sysUser.getUserid());
		receivablePastDueReason.setAddusername(sysUser.getName());
		salesFreeOrderMapper.editCustomerReceivablePastDueReason(receivablePastDueReason.getCustomerid());
		boolean flag = salesFreeOrderMapper.addCustomerReceivablePastDueReason(receivablePastDueReason) > 0;
		if(flag){
			Map paramMap = new HashMap();
			paramMap.put("customerid", receivablePastDueReason.getCustomerid());
			paramMap.put("businessdate", receivablePastDueReason.getCommitmentdate());
			BigDecimal actualamount = collectionOrderMapper.getCollectionOrderAmountSum(paramMap);
			if(null != actualamount){
				map.put("actualamount", actualamount);
			}
			int changenum = salesFreeOrderMapper.getCustomerReceivablePastDueReasonChangenum(receivablePastDueReason.getCustomerid());
			map.put("changenum", changenum);
			map.put("overreason", receivablePastDueReason.getOverreason());
			map.put("commitmentamount", receivablePastDueReason.getCommitmentamount());
			map.put("commitmentdate", receivablePastDueReason.getCommitmentdate());
			
			//添加放单,判断保存状态放单是否已存在，若以存在，则更新该放单，否则，添加。
			Map checkMap = new HashMap();
			checkMap.put("businessdate", CommonUtils.getTodayDataStr());
			checkMap.put("customerid", receivablePastDueReason.getCustomerid());
			int count = salesFreeOrderMapper.getSalesFreeOrderCount(checkMap);
			if(count > 0){
				SalesFreeOrder salesFreeOrder = salesFreeOrderMapper.getSalesFreeOrder(checkMap);
				salesFreeOrder.setBusinessdate(CommonUtils.getTodayDataStr());
				salesFreeOrder.setCustomerid(receivablePastDueReason.getCustomerid());
				Personnel personnel = getPersonnelById(sysUser.getPersonnelid());
				if(null!=personnel){
					salesFreeOrder.setApplyid(sysUser.getPersonnelid());
					salesFreeOrder.setApplydeptid(personnel.getBelongdeptid());
				}
				salesFreeOrder.setFreetype("1");
				salesFreeOrder.setOverreason(receivablePastDueReason.getOverreason());
				if(null != receivablePastDueReason.getCommitmentamount()){
					salesFreeOrder.setCommitmentamount(receivablePastDueReason.getCommitmentamount());
				}else{
					salesFreeOrder.setCommitmentamount(BigDecimal.ZERO);
				}
				salesFreeOrder.setCommitmentdate(receivablePastDueReason.getCommitmentdate());
				salesFreeOrder.setSaleamount(receivablePastDueReason.getSaleamount());
				salesFreeOrder.setUnpassamount(receivablePastDueReason.getUnpassamount());
				salesFreeOrder.setTotalpassamount(receivablePastDueReason.getTotalpassamount());
				salesFreeOrder.setStatus("2");
				salesFreeOrder.setModifyuserid(sysUser.getUserid());
				salesFreeOrder.setModifyusername(sysUser.getName());
				
				salesFreeOrderMapper.editSalesFreeOrder(salesFreeOrder);
			}else{
				SalesFreeOrder salesFreeOrder = new SalesFreeOrder();
				if (isAutoCreate("t_account_sales_freeorder")) {
					// 获取自动编号
					String id = getAutoCreateSysNumbderForeign(salesFreeOrder, "t_account_sales_freeorder");
					salesFreeOrder.setId(id);
				}else{
					salesFreeOrder.setId("FD-"+CommonUtils.getDataNumber());
				}
				salesFreeOrder.setBusinessdate(CommonUtils.getTodayDataStr());
				salesFreeOrder.setCustomerid(receivablePastDueReason.getCustomerid());
				Personnel personnel = getPersonnelById(sysUser.getPersonnelid());
				if(null!=personnel){
					salesFreeOrder.setApplyid(sysUser.getPersonnelid());
					salesFreeOrder.setApplydeptid(personnel.getBelongdeptid());
				}
				salesFreeOrder.setFreetype("1");
				salesFreeOrder.setOverreason(receivablePastDueReason.getOverreason());
				if(null != receivablePastDueReason.getCommitmentamount()){
					salesFreeOrder.setCommitmentamount(receivablePastDueReason.getCommitmentamount());
				}else{
					salesFreeOrder.setCommitmentamount(BigDecimal.ZERO);
				}
				salesFreeOrder.setCommitmentdate(receivablePastDueReason.getCommitmentdate());
				salesFreeOrder.setSaleamount(receivablePastDueReason.getSaleamount());
				salesFreeOrder.setUnpassamount(receivablePastDueReason.getUnpassamount());
				salesFreeOrder.setTotalpassamount(receivablePastDueReason.getTotalpassamount());
				salesFreeOrder.setStatus("2");
				salesFreeOrder.setAdduserid(sysUser.getUserid());
				salesFreeOrder.setAddusername(sysUser.getName());
				salesFreeOrder.setAdddeptid(sysUser.getDepartmentid());
				salesFreeOrder.setAdddeptname(sysUser.getDepartmentname());
				
				salesFreeOrderMapper.addSalesFreeOrder(salesFreeOrder);
				
			}
		}
		map.put("flag", flag);
		return map;
	}

	@Override
	public ReceivablePastDueReason getCustomerReceivablePastDueReason(
			String customerid) {
		return salesFreeOrderMapper.getCustomerReceivablePastDueReason(customerid);
	}

	@Override
	public int getCustomerReceivablePastDueReasonChangenum(String customerid)
			throws Exception {
		return salesFreeOrderMapper.getCustomerReceivablePastDueReasonChangenum(customerid);
	}

	@Override
	public boolean oneClearReceivablePastDueReason() throws Exception {
		return salesFreeOrderMapper.oneClearReceivablePastDueReason() > 0;
	}

	@Override
	public PageData getHistoryCustomerReceivablePastDueReasonList(PageMap pageMap)
			throws Exception {
		List<ReceivablePastDueReason> list = salesFreeOrderMapper.getHistoryCustomerReceivablePastDueReasonList(pageMap);
		for(ReceivablePastDueReason resDueReason : list){
			Customer customer = getCustomerByID(resDueReason.getCustomerid());
			if(null != customer){
				resDueReason.setCustomername(customer.getName());
			}
		}
		int count = salesFreeOrderMapper.getHistoryCustomerReceivablePastDueReasonCount(pageMap);
		PageData pageData = new PageData(count,list,pageMap);
		return pageData;
	}

	/**
	 * 代垫应收分析报表数据
	 * @param pageMap
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author luoqiang
	 * @date Oct 27, 2017
	 */
	@Override
	public PageData showSupplierReceivablePastDueReasonListData(PageMap pageMap) throws Exception{
		SysUser sysUser = getSysUser();
		pageMap.getCondition().put("sysuserid", sysUser.getUserid());
		pageMap.getCondition().put("type", 3);
//		String dataSql = getDataAccessRule("t_report_reason_base", "z");
//		pageMap.setDataSql(dataSql);
		String query_sql = " 1=1 AND t.billtype='1'";
		String reason_sql = " 1=1";
		String query_sql_begin = "1=1";
		String query_sql_z = " 1=1 ";

		if(pageMap.getCondition().containsKey("supplierid")){
			String str = (String) pageMap.getCondition().get("supplierid");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and FIND_IN_SET(t.supplierid,'"+str+"')";
			reason_sql += " and FIND_IN_SET(y.supplierid,'"+str+"')";
			query_sql_begin += " and FIND_IN_SET(t.supplierid,'"+str+"')";

		}
		if(pageMap.getCondition().containsKey("brandid")){
			String str = (String) pageMap.getCondition().get("brandid");
			str = StringEscapeUtils.escapeSql(str);
			if("null".equals(str)){
				query_sql += " and t.brandid = ''";
				query_sql_begin += " and t.brandid = ''";
			}else{
				query_sql += " and FIND_IN_SET(t.brandid,'"+str+"')";
				query_sql_begin += " and FIND_IN_SET(t.brandid,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("subjectid")){
			String str = (String) pageMap.getCondition().get("subjectid");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and FIND_IN_SET(t.subjectid,'"+str+"')";
		}

		if(pageMap.getCondition().containsKey("supplierdeptid")){
			String str = (String) pageMap.getCondition().get("supplierdeptid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				if(pageMap.getCondition().containsKey("supplierdeptid") && "null".equals(pageMap.getCondition().get("supplierdeptid"))){
					query_sql += " and t.supplierdeptid = '' ";
					query_sql_begin += " and t.supplierdeptid = '' ";
				}else{
					query_sql += " and t.supplierdeptid like '"+str+"%' ";
					query_sql_begin += " and t.supplierdeptid like '"+str+"%' ";
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
				query_sql += " and FIND_IN_SET(t.supplierdeptid,'"+retStr+"')";
				query_sql_begin += " and FIND_IN_SET(t.supplierdeptid,'"+retStr+"')";
			}
		}
		//小计列
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		pageMap.getCondition().put("groupcolsall", "");
		if(!pageMap.getCondition().containsKey("groupcols")){
			pageMap.getCondition().put("groupcols", "");
			groupcols = "supplierid";
		}
		pageMap.getCondition().put("query_sql", query_sql);
		reason_sql = reason_sql.replace("z.","y.");
		pageMap.getCondition().put("reason_sql", reason_sql);

		String query_sql_push=query_sql.replace("t1", "t");
		query_sql_push=query_sql_push.replace("goodsid", "brandid");
		pageMap.getCondition().put("query_sql_push", query_sql_push);
		pageMap.getCondition().put("query_sql", query_sql);
		pageMap.getCondition().put("query_sql_begin", query_sql_begin);
		pageMap.getCondition().put("query_sql_z",query_sql_z);
		//排序
		String orderstr = "";
		if(pageMap.getCondition().containsKey("sort")){
			String sort = (String) pageMap.getCondition().get("sort");
			String order = (String) pageMap.getCondition().get("order");
			if("cstmerbalance".equals(sort)){
				sort = "amount";
			}
			if(null==order){
				order = "asc";
			}
			orderstr = sort +" " +order;
		}
		pageMap.getCondition().put("orderstr", orderstr);


		String pass_sql="";
		String column_sum="";
		String column="";
		String column_pass = "";
		String column_reson = "";
		List<Map<String,Object>> RSlist = salesFreeOrderMapper.getSupplierPassDueListDataRS(pageMap);
		int RSCount = financeFundsReturnMapper.getSupplierPassDueListDataRSCount(pageMap);
		String businessdate1="1900-01-01";
		String businessdate2="2999-12-30";
		Map condition=pageMap.getCondition();
		if(condition.containsKey("businessdate1")){
			businessdate1=(String)pageMap.getCondition().get("businessdate1");
		}
		if(condition.containsKey("businessdate2")){
			businessdate2=(String)pageMap.getCondition().get("businessdate2");
		}

		for(Map<String,Object> rsMap :RSlist){
			int beginday =(Integer)rsMap.get("beginday");
			int endday =(Integer)rsMap.get("endday");
			int seq =(Integer)rsMap.get("seq");
			column_sum=column_sum+",sum(z.passamount"+seq+") as passamount"+seq;
			column=column+",0 as passamount"+seq;
			column_reson = column_reson +",y.passamount"+seq;
			column_pass = column_pass +",0 as passamount"+seq;
			pass_sql=pass_sql+"union all select t.supplierid,t.supplierdeptid,t.brandid,0 as beginamount,0 AS saleamount,0 as costamount,0 as unpassamount,0 as totalpassamount";
			int v=1;
			while(v<RSCount+1){
				if(v!=seq){
					pass_sql=pass_sql+",0 as passamount"+v;
				}
				if(v==seq){
					pass_sql=pass_sql+",t.remainderamount as passamount"+v;
				}
				v=v+1;
			}
			String beginDate = CommonUtils.getBeforeDateInDays(beginday);
			pass_sql=pass_sql+" from t_js_matcostsinput t  LEFT JOIN t_base_buy_supplier s ON s.id = t.supplierid where t.billtype='1' AND t.duefromdate<= '"+beginDate+"' and t.businessdate>= '"+ businessdate1+"' and t.businessdate<='"+businessdate2+"'";
			if(endday!=0){
				String enddateStr = CommonUtils.getBeforeDateInDays(new Date(),endday);
				pass_sql=pass_sql+" and (t.duefromdate='' or t.duefromdate>= '"+enddateStr+"') ";
			}
			String today = CommonUtils.getTodayDataStr();
			pass_sql=pass_sql+" and "+query_sql+" and t.duefromdate<>'' and t.duefromdate < '"+today+"' and t.iswriteoff='0' ";
		}
		pageMap.getCondition().put("pass_sql", pass_sql);
		pageMap.getCondition().put("column_sum", column_sum);
		pageMap.getCondition().put("column", column);
		pageMap.getCondition().put("column_reson", column_reson);
		pageMap.getCondition().put("column_pass", column_pass);
		pageMap.getCondition().put("today", CommonUtils.getTodayDataStr());
		List<Map<String,Object>> list = salesFreeOrderMapper.showSupplierReceivablePastDueReasonList(pageMap);
		for(Map<String,Object> dataObject : list){
			String supplier = (String) dataObject.get("supplierid");


			if(groupcols.indexOf("supplierid")!=-1){
				BuySupplier buySupplier = getSupplierInfoById(supplier);
				if(null!=buySupplier){
					if(StringUtils.isNotEmpty(buySupplier.getSettletype())){
						dataObject.put("settletype", buySupplier.getSettletype());
						Settlement settlement = getSettlementByID(buySupplier.getSettletype());
						if(null != settlement){
							dataObject.put("settletypename", settlement.getName());
						}
						dataObject.put("settleday", buySupplier.getSettleday());
					}
					dataObject.put("suppliername", buySupplier.getName());
				}else{
					dataObject.put("suppliername", "未指定客户");
				}
			}
		}

		PageData pageData = new PageData(salesFreeOrderMapper.showSupplierReceivablePastDueReasonCount(pageMap),list,pageMap);
		//取合计数据
		pageMap.getCondition().put("groupcolsall", "all");
		List<Map<String,Object>> footer = salesFreeOrderMapper.showSupplierReceivablePastDueReasonList(pageMap);

		for(Map<String,Object> map : footer){
			if(null!=map){
				if(groupcols.indexOf("supplierid")!=-1){
					map.put("supplierid", "");
					map.put("suppliername", "合计");
				}else if(groupcols.indexOf("salesuser")!=-1){
					map.put("salesuser", "");
					map.put("salesusername", "合计");
				}
			}else{
				footer = new ArrayList<Map<String,Object>>();
			}
		}
		pageData.setFooter(footer);

		return pageData;
	}
}

