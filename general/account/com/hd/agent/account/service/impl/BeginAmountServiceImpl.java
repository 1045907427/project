/**
 * @(#)BeginAmountServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年10月31日 chenwei 创建版本
 */
package com.hd.agent.account.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.BeginAmountMapper;
import com.hd.agent.account.model.BeginAmount;
import com.hd.agent.account.service.IBeginAmountService;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 客户应收款期初service实现类
 * 
 * @author chenwei
 */
public class BeginAmountServiceImpl extends BaseAccountServiceImpl implements
		IBeginAmountService {
	/**
	 * 客户应收款期初dao
	 */
	private BeginAmountMapper beginAmountMapper;

	public BeginAmountMapper getBeginAmountMapper() {
		return beginAmountMapper;
	}

	public void setBeginAmountMapper(BeginAmountMapper beginAmountMapper) {
		this.beginAmountMapper = beginAmountMapper;
	}

	@Override
	public boolean addBeignAmount(BeginAmount beginAmount) throws Exception {

		if (null != beginAmount) {
			if (isAutoCreate("t_account_begin_amount")) {
				// 获取自动编号
				String id = getAutoCreateSysNumbderForeign(beginAmount,
						"t_account_begin_amount");
				beginAmount.setId(id);
			} else {
				beginAmount.setId("QC-"
						+ CommonUtils.getDataNumberSendsWithRand());
			}
			Customer customer = getCustomerByID(beginAmount.getCustomerid());
			if (null != customer) {
				beginAmount.setPcustomerid(customer.getPid());
				beginAmount.setSalesarea(customer.getSalesarea());
				beginAmount.setSalesdept(customer.getSalesdeptid());
				beginAmount.setSalesuser(customer.getSalesuserid());
				beginAmount.setCustomersort(customer.getCustomersort());
			}
			//应收日期不存在时， 根据系统设置的结算方式计算
			if(StringUtils.isEmpty(beginAmount.getDuefromdate())){
				String duefromdate = getReceiptDateBySettlement(CommonUtils.stringToDate(beginAmount.getBusinessdate()),beginAmount.getCustomerid(),null);
				beginAmount.setDuefromdate(duefromdate);
			}
			SysUser sysUser = getSysUser();
			beginAmount.setAdddeptid(sysUser.getDepartmentid());
			beginAmount.setAdddeptname(sysUser.getDepartmentname());
			beginAmount.setAdduserid(sysUser.getUserid());
			beginAmount.setAddusername(sysUser.getName());
			int i = beginAmountMapper.addBeignAmount(beginAmount);
			return i > 0;
		} else {
			return false;
		}
	}

	@Override
	public PageData showBeignAmountList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_account_begin_amount", null);
		pageMap.setDataSql(dataSql);
		List<BeginAmount> list = beginAmountMapper.showBeignAmountList(pageMap);
		for (BeginAmount beginAmount : list) {
			Customer customer = getCustomerByID(beginAmount.getCustomerid());
			if (null != customer) {
				beginAmount.setCustomername(customer.getName());
			}
			if ("2".equals(beginAmount.getStatus())) {
				beginAmount.setStatusname("保存");
			} else if ("3".equals(beginAmount.getStatus())) {
				beginAmount.setStatusname("审核通过");
			} else if ("4".equals(beginAmount.getStatus())) {
				beginAmount.setStatusname("关闭");
			}
		}
		PageData pageData = new PageData(
				beginAmountMapper.showBeignAmountCount(pageMap), list, pageMap);
		BeginAmount beginAmount = beginAmountMapper.showBeignAmountSum(pageMap);
		List footer = new ArrayList();
		if (null != beginAmount) {
			beginAmount.setBusinessdate("合计");
			footer.add(beginAmount);
		}
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public BeginAmount getBeginAmountByID(String id) throws Exception {
		BeginAmount beginAmount = beginAmountMapper.getBeginAmountByID(id);
		if (null != beginAmount) {
			Customer customer = getCustomerByID(beginAmount.getCustomerid());
			if (null != customer) {
				beginAmount.setCustomername(customer.getName());
			}
		}
		return beginAmount;
	}

	@Override
	public boolean editBeginAmount(BeginAmount beginAmount) throws Exception {
		BeginAmount oldBeginAmount = beginAmountMapper
				.getBeginAmountByID(beginAmount.getId());
		if ("2".equals(oldBeginAmount.getStatus())) {
			SysUser sysUser = getSysUser();
			Customer customer = getCustomerByID(beginAmount.getCustomerid());
			if (null != customer) {
				beginAmount.setPcustomerid(customer.getPid());
				beginAmount.setSalesarea(customer.getSalesarea());
				beginAmount.setSalesdept(customer.getSalesdeptid());
				beginAmount.setSalesuser(customer.getSalesuserid());
				beginAmount.setCustomersort(customer.getCustomersort());
			}
			//应收日期不存在时， 根据系统设置的结算方式计算
			if(StringUtils.isEmpty(beginAmount.getDuefromdate())){
				String duefromdate = getReceiptDateBySettlement(CommonUtils.stringToDate(beginAmount.getBusinessdate()),beginAmount.getCustomerid(),null);
				beginAmount.setDuefromdate(duefromdate);
			}
			beginAmount.setModifyuserid(sysUser.getUserid());
			beginAmount.setModifyusername(sysUser.getName());
			int i = beginAmountMapper.editBeginAmount(beginAmount);
			return i > 0;
		} else {
			return false;
		}
	}

	@Override
	public boolean deleteBeginAmount(String id) throws Exception {
		BeginAmount oldBeginAmount = beginAmountMapper.getBeginAmountByID(id);
		if ("2".equals(oldBeginAmount.getStatus())) {
			int i = beginAmountMapper.deleteBeginAmount(id);
			return i > 0;
		}
		return false;
	}

	@Override
	public Map auditBeignAmount(String id) throws Exception {
		boolean flag = false;
		String msg = "";
		BeginAmount oldBeginAmount = beginAmountMapper.getBeginAmountByID(id);
		if ("2".equals(oldBeginAmount.getStatus())) {
			SysUser sysUser = getSysUser();
			int i = beginAmountMapper.auditBeignAmount(id, sysUser.getUserid(),
					sysUser.getName());
			flag = i > 0;
		} else {
			msg = "单据:" + id + "状态不对或者不存在";
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}

	@Override
	public Map oppauditBeignAmount(String id) throws Exception {
		boolean flag = false;
		String msg = "";
		BeginAmount oldBeginAmount = beginAmountMapper.getBeginAmountByID(id);
		if ("3".equals(oldBeginAmount.getStatus()) && "0".equals(oldBeginAmount.getIsinvoice())) {
			int i = beginAmountMapper.oppauditBeignAmount(id);
			flag = i > 0;
		} else {
			msg = "单据:" + id + "状态不对或者不存在";
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}

	@Override
	public Map importBeignAmount(List<Map> list) throws Exception {
		boolean flag = false;
		String msg = "";
		int truenum = 0;
		int falsenum = 0;
		String ids = "";
		for (Map map : list) {
			String id = "";
			String businessdate = "";
			String customerid = "";
			String customername = "";
			String amountstr = "";
			String duefromdate ="";
			BigDecimal amount = BigDecimal.ZERO;
			BeginAmount beginAmount = new BeginAmount();
			if(map.containsKey("id")){
				id = (String) map.get("id");
			}
			if(map.containsKey("businessdate")){
				businessdate = (String) map.get("businessdate");
			}
			if(map.containsKey("customerid")){
				customerid = (String) map.get("customerid");
			}
			if(map.containsKey("customername")){
				customername = (String) map.get("customername");
			}
			if(map.containsKey("amount")){
				amountstr = (String) map.get("amount");
				amount = new BigDecimal(amountstr);
			}
			if(map.containsKey("duefromdate")){
				duefromdate = (String) map.get("duefromdate");
			}else{
				if(StringUtils.isNotEmpty(businessdate)){
					duefromdate = getReceiptDateBySettlement(CommonUtils.stringToDate(businessdate),customerid,null);
				}else{
					duefromdate = getReceiptDateBySettlement(CommonUtils.stringToDate(CommonUtils.getTodayDataStr()),customerid,null);
				}
				
			}
			boolean isrepeat = false;
			if(StringUtils.isEmpty(id)){
				if (isAutoCreate("t_account_begin_amount")) {
					// 获取自动编号
					id = getAutoCreateSysNumbderForeign(beginAmount,"t_account_begin_amount");
				} else {
					id ="QC-"+ CommonUtils.getDataNumberSendsWithRand();
				}
			}else{
				BeginAmount oldBeginAmount = beginAmountMapper.getBeginAmountByID(id);
				if(null!=oldBeginAmount){
					isrepeat = true;
				}
			}
			if(!isrepeat){
				if(StringUtils.isEmpty(businessdate)){
					businessdate = CommonUtils.getTodayDataStr();
				}
				Customer customer =null;
				if(StringUtils.isNotEmpty(customername)){
					customer = getCustomerByName(customername);

				}
				if(StringUtils.isNotEmpty(customerid)){
					customer = getCustomerByID(customerid);
				}
				if(null!=customer){
					customerid = customer.getId();
				}
				beginAmount.setId(id);
				beginAmount.setBusinessdate(businessdate);
				beginAmount.setDuefromdate(duefromdate);
				beginAmount.setCustomerid(customerid);
				beginAmount.setAmount(amount);
				beginAmount.setStatus("2");
				if (null != customer) {
					beginAmount.setPcustomerid(customer.getPid());
					beginAmount.setSalesarea(customer.getSalesarea());
					beginAmount.setSalesdept(customer.getSalesdeptid());
					beginAmount.setSalesuser(customer.getSalesuserid());
					beginAmount.setCustomersort(customer.getCustomersort());
					
					SysUser sysUser = getSysUser();
					beginAmount.setAdddeptid(sysUser.getDepartmentid());
					beginAmount.setAdddeptname(sysUser.getDepartmentname());
					beginAmount.setAdduserid(sysUser.getUserid());
					beginAmount.setAddusername(sysUser.getName());
					int i = beginAmountMapper.addBeignAmount(beginAmount);
					if(i>0){
						flag = true;
						truenum ++;
						ids += id+",";
					}else{
						msg +="导入失败 客户:"+customerid+"，金额"+amountstr+";";
						falsenum ++;
					}
				}else{
					if(StringUtils.isNotEmpty(customerid) || StringUtils.isNotEmpty(customername)){
						msg +="找不到客户:"+customerid+customername+";";
					}
					falsenum ++;
				}
			}else{
				msg +="导入失败 单据号:"+id+"重复;";
				falsenum ++;
			}
			
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("truenum", truenum);
		map.put("falsenum", falsenum);
		map.put("ids", ids);
		return map;
	}

}
