/**
 * @(#)FinanceAction.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 17, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.Bank;
import com.hd.agent.basefiles.model.ExpensesSort;
import com.hd.agent.basefiles.model.FilesLevel;
import com.hd.agent.basefiles.model.Payment;
import com.hd.agent.basefiles.model.Settlement;
import com.hd.agent.basefiles.model.TaxType;
import com.hd.agent.basefiles.service.IFinanceService;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.model.Tree;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.system.model.SysCode;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class FinanceAction extends FilesLevelAction{

	private IFinanceService financeService;
	private TaxType taxType;
	private ExpensesSort expensesSort;
	private Settlement settlement;
	private Payment payment;
	private Bank bank;
	
	public IFinanceService getFinanceService() {
		return financeService;
	}
	public void setFinanceService(IFinanceService financeService) {
		this.financeService = financeService;
	}
	public TaxType getTaxType() {
		return taxType;
	}
	public void setTaxType(TaxType taxType) {
		this.taxType = taxType;
	}
	
	public ExpensesSort getExpensesSort() {
		return expensesSort;
	}
	public void setExpensesSort(ExpensesSort expensesSort) {
		this.expensesSort = expensesSort;
	}
	
	public Settlement getSettlement() {
		return settlement;
	}
	public void setSettlement(Settlement settlement) {
		this.settlement = settlement;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	public Bank getBank() {
		return bank;
	}
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	/*-------------------------------------税种档案--------------------------------------------------*/
	
	/**
	 * 显示税种档案页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 14, 2013
	 */
	public String showTaxTypePage()throws Exception{
		String useHTKPExport=getSysParamValue("USEHTKPEXPORT");
		if(!"1".equals(useHTKPExport)){
			useHTKPExport="0";
		}
		request.setAttribute("useHTKPExport",useHTKPExport);
		return SUCCESS;
	}
	
	/**
	 * 获取税种档案列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 15, 2013
	 */
	public String getTaxTypeList()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeService.getTaxTypeList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 显示税种详情页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 15, 2013
	 */
	public String showTaxTypeViewPage()throws Exception{
		String id = request.getParameter("id");
		Map colMap = getAccessColumn("t_base_finance_taxtype");
		TaxType taxType = financeService.getTaxTypeInfo(id);
		if(taxType != null){
			request.setAttribute("showMap", colMap);
			request.setAttribute("taxType", taxType);
		}
		String useHTKPExport=getSysParamValue("USEHTKPEXPORT");
		if(!"1".equals(useHTKPExport)){
			useHTKPExport="0";
		}
		request.setAttribute("useHTKPExport",useHTKPExport);
		return SUCCESS;
	}
	
	/**
	 * 显示税种新增页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 15, 2013
	 */
	public String showTaxTypeAddPage()throws Exception{

		String useHTKPExport=getSysParamValue("USEHTKPEXPORT");
		if(!"1".equals(useHTKPExport)){
			useHTKPExport="0";
		}
		request.setAttribute("useHTKPExport",useHTKPExport);
		return SUCCESS;
	}
	
	/**
	 * 显示税种修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 15, 2013
	 */
	public String showTaxTypeEditPage()throws Exception{
		String id = request.getParameter("id");
		TaxType taxType = financeService.getTaxTypeInfo(id);
		if(taxType != null){
			request.setAttribute("taxType", taxType);
			request.setAttribute("editFlag", canTableDataDelete("t_base_finance_taxtype",null));
		}
		String useHTKPExport=getSysParamValue("USEHTKPEXPORT");
		if(!"1".equals(useHTKPExport)){
			useHTKPExport="0";
		}
		request.setAttribute("useHTKPExport",useHTKPExport);
		return SUCCESS;
	}
	
	/**
	 * 新增税种
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 15, 2013
	 */
	@UserOperateLog(key="TaxType",type=2,value="")
	public String addTaxType()throws Exception{
		String type = request.getParameter("type");
		if("save".equals(type)){
			taxType.setState("2");
		}
		else{
			taxType.setState("3");
		}
		Map resultMap=new HashMap();
		if("1".equals(taxType.getJsflag()) && BigDecimal.ZERO.compareTo(taxType.getRate())!=0){
			resultMap.put("flag",false);
			resultMap.put("msg","当税率等于零时，金税标识才可以选择免税");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		SysUser sysUser = getSysUser();
		taxType.setAdddeptid(sysUser.getDepartmentid());
		taxType.setAdddeptname(sysUser.getDepartmentname());
		taxType.setAdduserid(sysUser.getUserid());
		taxType.setAddusername(sysUser.getName());
		boolean flag = financeService.addTaxType(taxType);
		addJSONObject("flag", flag);
		addLog("新增税种 编号:"+taxType.getId(),flag);
		return SUCCESS;
	}
	
	/**
	 * 编码是否被使用，true 被使用，false 未被使用
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 15, 2013
	 */
	public String isUsedId()throws Exception{
		String id = request.getParameter("id");
		boolean flag = false;
		TaxType taxType = financeService.getTaxTypeInfo(id);
		if(taxType != null){
			flag = true;
		}
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 名称是否被使用，true已使用，false 未使用
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 15, 2013
	 */
	public String isUsedName()throws Exception{
		String name = request.getParameter("name");
		boolean flag = financeService.isUsedName(name);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 税率是否被使用，true已使用，false 未使用
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 15, 2013
	 */
	public String isUsedRate()throws Exception{
		String rate = request.getParameter("rate");
		boolean flag = financeService.isUsedName(rate);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 修改税种
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 15, 2013
	 */
	@UserOperateLog(key="TaxType",type=3,value="")
	public String editTaxType()throws Exception{
		String type = request.getParameter("type");
		if(!"1".equals(taxType.getState())){
			if("save".equals(type)){
				taxType.setState("2");
			}
			else{
				taxType.setState("3");
			}
		}
		Map resultMap=new HashMap();
		if("1".equals(taxType.getJsflag()) && BigDecimal.ZERO.compareTo(taxType.getRate())!=0){
			resultMap.put("flag",false);
			resultMap.put("msg","当税率等于零时，金税标识才可以选择免税");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		SysUser sysUser = getSysUser();
		taxType.setModifyuserid(sysUser.getUserid());
		taxType.setModifyusername(sysUser.getName());
		boolean flag = financeService.editTaxType(taxType);
		addLog("修改税种 编号:"+taxType.getId(),flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 启用税种
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 15, 2013
	 */
	@UserOperateLog(key="TaxType",type=0,value="")
	public String enableTaxType()throws Exception{
		String id = request.getParameter("id");
		SysUser sysUser = getSysUser();
		TaxType taxTypeInfo = new TaxType();
		taxTypeInfo.setOpenuserid(sysUser.getUserid());
		taxTypeInfo.setOpenusername(sysUser.getName());
		taxTypeInfo.setId(id);
		boolean flag = financeService.enableTaxType(taxTypeInfo);
		addLog("启用税种 编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 禁用税种
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 15, 2013
	 */
	@UserOperateLog(key="TaxType",type=0,value="")
	public String disableTaxType()throws Exception{
		String id = request.getParameter("id");
		SysUser sysUser = getSysUser();
		TaxType taxTypeInfo = new TaxType();
		taxTypeInfo.setCloseuserid(sysUser.getUserid());
		taxTypeInfo.setCloseusername(sysUser.getName());
		taxTypeInfo.setId(id);
		boolean flag = financeService.disableTaxType(taxTypeInfo);
		addLog("禁用税种 编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 删除税种，删除时判断是否被引用，被引用不可删除
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 15, 2013
	 */
	@UserOperateLog(key="TaxType",type=0,value="")
	public String deleteTaxType()throws Exception{
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_base_finance_taxtype",id);
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag = financeService.deleteTaxType(id);
		addLog("删除税种 编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	/*-------------------------------------结算方式--------------------------------------------------*/
	
	/**
	 * 显示结算方式页面
	 */
	public String settlementPage()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 显示结算方式新增页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	public String settlementAddPage()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 新增结算方式
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	@UserOperateLog(key="Settlement",type=2)
	public String addSettlement()throws Exception{
		String type = request.getParameter("type");
		if("save".equals(type)){
			settlement.setState("2");
		}
		else{
			settlement.setState("3");
		}
		SysUser sysUser = getSysUser();
		settlement.setAdddeptid(sysUser.getDepartmentid());
		settlement.setAdddeptname(sysUser.getDepartmentname());
		settlement.setAdduserid(sysUser.getUserid());
		settlement.setAddusername(sysUser.getName());
		boolean flag = financeService.addSettlement(settlement);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", settlement.getId());
		addJSONObject(map);
		addLog("结算方式新增 编码："+settlement.getId(), flag);
		return "success";
	}
	
	/**
	 * 验证结算方式编码是否被使用，true 被使用，false 未被使用
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	public String isUsedSettlementID()throws Exception{
		String id = request.getParameter("id");
		Settlement settlement = financeService.getSettlemetDetail(id);
		boolean flag = false;
		if(settlement != null){
			flag = true;
		}
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 验证结算方式名称是否被使用，true 被使用，false 未被使用
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	public String isUsedSettlementName()throws Exception{
		String name = request.getParameter("name");
		boolean flag = financeService.isUsedSettlementName(name);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 显示结算方式详细页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	public String settlementViewPage()throws Exception{
		String id = request.getParameter("id");
		Settlement settlement = financeService.getSettlemetDetail(id);
		if(settlement != null){
			request.setAttribute("settlement", settlement);
		}
		return SUCCESS;
	}
	
	/**
	 * 获取结算方式列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	public String getSettlementListPage()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeService.getSettlementListPage(pageMap);
		List<Settlement> list = pageData.getList();
		for(Settlement settlement : list){
			if(StringUtils.isNotEmpty(settlement.getState())){//状态
				SysCode sysCode = super.getBaseSysCodeService().showSysCodeInfo(settlement.getState(), "state");
				if(sysCode != null){
					settlement.setStateName(sysCode.getCodename());
				}
			}
		}
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 显示结算方式修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	public String settlementEditPage()throws Exception{
		String id = request.getParameter("id");
		Settlement settlement = financeService.getSettlemetDetail(id);
		request.setAttribute("settlement", settlement);
		request.setAttribute("editFlag", canTableDataDelete("t_base_finance_settlement", id));
		return SUCCESS;
	}
	
	/**
	 * 修改结算方式
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	@UserOperateLog(key="Settlement",type=3,value="")
	public String editSettlement()throws Exception{
		String type = request.getParameter("type");
		SysUser sysUser = getSysUser();
		if(!"0".equals(settlement.getState()) && !"1".equals(settlement.getState())){
			if("save".equals(type)){
				settlement.setState("2");
			}
			else{
				settlement.setState("3");
			}
		}
		settlement.setModifyuserid(sysUser.getUserid());
		settlement.setModifyusername(sysUser.getName());
		boolean flag = financeService.editSettlement(settlement);
		addLog("修改结算方式 编号:"+settlement.getId(),flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 删除结算方式，删除时判断该信息是否被引用，引用则无法删除
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	@UserOperateLog(key="Settlement",type=4,value="")
	public String deleteSettlement()throws Exception{
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_base_finance_settlement", id);
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag = financeService.deleteSettlement(id);
		addLog("删除结算方式 编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 启用结算方式
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	@UserOperateLog(key="Settlement",type=0,value="")
	public String enableSettlement()throws Exception{
		String id = request.getParameter("id");
		SysUser sysUser = getSysUser();
		Settlement settlement = new Settlement();
		settlement.setOpenuserid(sysUser.getUserid());
		settlement.setOpenusername(sysUser.getName());
		settlement.setId(id);
		boolean flag = financeService.enableSettlement(settlement);
		addLog("启用结算方式 编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 禁用结算方式
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	@UserOperateLog(key="Settlement",type=0,value="")
	public String disableSettlement()throws Exception{
		String id = request.getParameter("id");
		SysUser sysUser = getSysUser();
		Settlement settlement = new Settlement();
		settlement.setCloseuserid(sysUser.getUserid());
		settlement.setCloseusername(sysUser.getName());
		settlement.setId(id);
		boolean flag = financeService.disableSettlement(settlement);
		addLog("禁用结算方式 编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	/*-------------------------------------支付方式--------------------------------------------------*/
	
	/**
	 * 显示支付方式页面
	 */
	public String paymentPage()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 显示支付方式新增页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	public String paymentAddPage()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 显示支付方式修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	public String paymentEidtPage()throws Exception{
		String id = request.getParameter("id");
		Payment payment = financeService.getPaymentDetail(id);
		request.setAttribute("payment", payment);
		request.setAttribute("editFlag", canTableDataDelete("t_base_finance_payment", id));
		return SUCCESS;
	}
	
	/**
	 * 显示支付方式详情页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	public String paymentViewPage()throws Exception{
		String id = request.getParameter("id");
		Payment payment = financeService.getPaymentDetail(id);
		request.setAttribute("payment", payment);
		return SUCCESS;

	}
	
	/**
	 * 获取支付方式列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	public String getPaymentListPage()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeService.getPaymentListPage(pageMap);
		List<Payment> list = pageData.getList();
		for(Payment payment : list){
			if(StringUtils.isNotEmpty(payment.getState())){//状态
				SysCode sysCode = super.getBaseSysCodeService().showSysCodeInfo(payment.getState(), "state");
				if(sysCode != null){
					payment.setStateName(sysCode.getCodename());
				}
			}
		}
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 验证支付方式名称是否被使用，true 被使用，false 未被使用
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	public String isUsedPaymentName()throws Exception{
		String name = request.getParameter("name");
		boolean flag = financeService.isUsedPaymentName(name);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 验证支付方式编码是否被使用，true 被使用，false 未被使用
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	public String isUsedPaymentID()throws Exception{
		String id = request.getParameter("id");
		Payment payment = financeService.getPaymentDetail(id);
		boolean flag = false;
		if(payment != null){
			flag = true;
		}
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 新增支付方式
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	@UserOperateLog(key="Payment",type=2,value="")
	public String addPayment()throws Exception{
		String type = request.getParameter("type");
		if("save".equals(type)){
			payment.setState("2");
		}
		else{
			payment.setState("3");
		}
		SysUser sysUser = getSysUser();
		payment.setAdddeptid(sysUser.getDepartmentid());
		payment.setAdddeptname(sysUser.getDepartmentname());
		payment.setAdduserid(sysUser.getUserid());
		payment.setAddusername(sysUser.getName());
		boolean flag = financeService.addPayment(payment);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", payment.getId());
		addJSONObject(map);
		addLog("支付方式新增 编码："+payment.getId(), flag);
		return "success";
	}
	
	/**
	 * 修改支付方式
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	@UserOperateLog(key="Payment",type=3,value="")
	public String editPayment()throws Exception{
		String type = request.getParameter("type");
		SysUser sysUser = getSysUser();
		if(!"0".equals(payment.getState()) && !"1".equals(payment.getState())){
			if("save".equals(type)){
				payment.setState("2");
			}
			else{
				payment.setState("3");
			}
		}
		payment.setModifyuserid(sysUser.getUserid());
		payment.setModifyusername(sysUser.getName());
		boolean flag = financeService.editPayment(payment);
		addLog("修改支付方式 编号:"+payment.getId(),flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 删除支付方式
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	@UserOperateLog(key="Payment",type=4,value="")
	public String deletePayment()throws Exception{
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_base_finance_payment", id);
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag = financeService.deletePayment(id);
		addLog("删除支付方式 编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 启用支付方式
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	@UserOperateLog(key="Payment",type=0,value="")
	public String enablePayment()throws Exception{
		String id = request.getParameter("id");
		SysUser sysUser = getSysUser();
		Payment pay = new Payment();
		pay.setOpenuserid(sysUser.getUserid());
		pay.setOpenusername(sysUser.getName());
		pay.setId(id);
		boolean flag = financeService.enablePayment(pay);
		addLog("启用支付方式 编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 禁用支付方式
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2013
	 */
	@UserOperateLog(key="Payment",type=0,value="")
	public String disablePayment()throws Exception{
		String id = request.getParameter("id");
		SysUser sysUser = getSysUser();
		Payment pay = new Payment();
		pay.setCloseuserid(sysUser.getUserid());
		pay.setCloseusername(sysUser.getName());
		pay.setId(id);
		boolean flag = financeService.disablePayment(pay);
		addLog("禁用支付方式 编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/*-------------------------------------费用分类--------------------------------------------------*/
	
	/**
	 * 显示费用分类页面
	 */
	public String expensesSortPage()throws Exception{
		List list = getBaseFilesLevelService().showFilesLevelList("t_base_finance_expenses_sort");
		int len = 0;
		if(null != list && list.size() != 0){
			len = list.size();
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<list.size();i++){
				FilesLevel level = (FilesLevel)list.get(i);
				sb.append(level.getLen() + ",");
			}
			if(sb.length() > 0){
				request.setAttribute("lenStr", sb.deleteCharAt(sb.length()-1).toString());
			}
		}
		request.setAttribute("len", len);
		return SUCCESS;
	}
	
	/**
	 * 显示费用分类新增页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public String expensesSortAddPage()throws Exception{
		String id = request.getParameter("id");//当为空时添加的费用分类为一级目录，否则该编号为添加费用分类的父级
		ExpensesSort expensesSort = financeService.getExpensesSortDetail(id);
		int len = 0;
		if(expensesSort != null && StringUtils.isNotEmpty(expensesSort.getId())){
			len = expensesSort.getId().length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_finance_expenses_sort", len);
		request.setAttribute("expensesSort", expensesSort);
		request.setAttribute("len", nextLen);
		return SUCCESS;
	}
	
	/**
	 * 显示费用分类修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public String expensesSortEditPage()throws Exception{
		String id = request.getParameter("id");
		ExpensesSort expensesSort = financeService.getExpensesSortDetail(id);
		int len = 0;
		if(null != expensesSort && StringUtils.isNotEmpty(expensesSort.getPid())){
			len = expensesSort.getPid().length();
			ExpensesSort expenses = financeService.getExpensesSortDetail(expensesSort.getPid());
            if(null != expenses){
                request.setAttribute("parentName", expenses.getName());
            }
		}
		int nextLen = getBaseTreeFilesNext("t_base_finance_expenses_sort", len);
		request.setAttribute("editFlag", canTableDataDelete("t_base_finance_expenses_sort", id));
		request.setAttribute("len", nextLen);
		request.setAttribute("expensesSort", expensesSort);
		return SUCCESS;
	}
	
	/**
	 * 显示费用分复制页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public String expensesSortCopyPage()throws Exception{
		String id = request.getParameter("id");
		ExpensesSort expensesSort = financeService.getExpensesSortDetail(id);
		if(expensesSort != null && StringUtils.isNotEmpty(expensesSort.getPid())){
			ExpensesSort expenses = financeService.getExpensesSortDetail(expensesSort.getPid());
			request.setAttribute("parentName", expenses.getName());
		}
		int len = 0;
		String name="",pname="";
		if(expensesSort != null && StringUtils.isNotEmpty(expensesSort.getPid())){
			len = expensesSort.getPid().length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_finance_expenses_sort", len);
		request.setAttribute("len", nextLen);
		request.setAttribute("expensesSort", expensesSort);
		return SUCCESS;
	}
	
	/**
	 * 显示费用分类详情页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public String expensesSortViewPage()throws Exception{
		String id = request.getParameter("id");
		ExpensesSort expensesSort = financeService.getExpensesSortDetail(id);
		request.setAttribute("expensesSort", expensesSort);
		return SUCCESS;
	}
	
	/**
	 * 显示费用分类树型数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public String getExpensesSortTree()throws Exception{
		List result = new ArrayList();
		List<ExpensesSort> list = financeService.getExpensesSortList(pageMap);
		Map<String,String> first = new HashMap<String,String>();
		Tree pTree = new Tree();
		pTree.setId("");
		pTree.setText("费用分类");
		pTree.setOpen("true");
		result.add(pTree);
		if(list.size() != 0){
			for(ExpensesSort expensesSort:list){
				Tree cTree = new Tree();
				cTree.setId(expensesSort.getId());
				cTree.setParentid(expensesSort.getPid());
				cTree.setText(expensesSort.getThisname());
				cTree.setState(expensesSort.getState());
				result.add(cTree);
			}
		}
		addJSONArray(result);
		return SUCCESS;
	}
	
	/**
	 * 新增费用分类
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	@UserOperateLog(key="ExpensesSort",type=2,value="")
	public String addExpensesSort()throws Exception{
		String type = request.getParameter("type");
		if("hold".equals(type)){
			expensesSort.setState("3");
		}
		else{
			expensesSort.setState("2");
		}
		SysUser sysUser = getSysUser();
		expensesSort.setAdddeptid(sysUser.getDepartmentid());
		expensesSort.setAdddeptname(sysUser.getDepartmentname());
		expensesSort.setAdduserid(sysUser.getUserid());
		expensesSort.setAddusername(sysUser.getName());
		boolean flag = financeService.addExpensesSort(expensesSort);
		addLog("新增费用分类 编号:"+expensesSort.getId(),flag);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("flag", flag);
		Tree node = new Tree();
		node.setId(expensesSort.getId());
		node.setParentid(expensesSort.getPid());
		node.setState(expensesSort.getState());
		node.setText(expensesSort.getThisname());
		map.put("node", node);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 修改费用分类
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	@UserOperateLog(key="ExpensesSort",type=3,value="")
	public String editExpensesSort()throws Exception{
		String type = request.getParameter("type");
		if(!"1".equals(expensesSort.getState()) && !"0".equals(expensesSort.getState())){
			if("save".equals(type)){
				expensesSort.setState("2");
			}
		}
		SysUser sysUser = getSysUser();
		expensesSort.setModifyuserid(sysUser.getUserid());
		expensesSort.setModifyusername(sysUser.getName());
		Map map = financeService.editExpensesSort(expensesSort);
		addLog("修改费用分类 编号:"+expensesSort.getOldId(),map.get("flag").equals(true));
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 删除费用分类
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	@UserOperateLog(key="ExpensesSort",type=4,value="")
	public String deleteExpensesSort()throws Exception{
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_base_finance_expenses_sort", id);
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag = financeService.deleteExpensesSort(id);
		addLog("删除费用分类 编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 启用费用分类
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	@UserOperateLog(key="ExpensesSort",type=0,value="")
	public String enableExpensesSort()throws Exception{
		String id = request.getParameter("id");
		ExpensesSort expenses = new ExpensesSort();
		SysUser sysUser = getSysUser();
		expenses.setOpenuserid(sysUser.getUserid());
		expenses.setOpenusername(sysUser.getName());
		expenses.setId(id);
		boolean flag = financeService.enableExpensesSort(expenses);
		addLog("启用费用分类 编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 禁用费用分类，如果该节点是父节点，则禁用下面所有为启用状态的子节点
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	@UserOperateLog(key="ExpensesSort",type=0,value="禁用费用分类")
	public String disableExpensesSort()throws Exception{
		String id = request.getParameter("id");
		SysUser sysUser = getSysUser();
		Map map = new HashMap();
		map.put("id", id);
		pageMap.setCondition(map);
		List<ExpensesSort> list = financeService.getExpensesSortParentAllChildren(pageMap);//查询该节点及其所有子节点信息
		int successNum = 0,failureNum = 0,notAllowNum = 0;
		List<String> ids = new ArrayList<String>();
		for(ExpensesSort expensesSort : list){
			if(!"1".equals(expensesSort.getState())){
				notAllowNum++;
			}
			else{
				ExpensesSort expenses = new ExpensesSort();
				expenses.setCloseuserid(sysUser.getUserid());
				expenses.setCloseusername(sysUser.getName());
				expenses.setId(expensesSort.getId());
				boolean flag = financeService.disableExpensesSort(expenses);
				if(flag){
					successNum++;
					ids.add(expensesSort.getId());//返回所有禁用的记录编号，供前台更新树节点信息
				}
				else{
					failureNum++;
				}
			}
		}
		Map retMap = new HashMap();
		retMap.put("successNum", successNum);
		retMap.put("failureNum", failureNum);
		retMap.put("notAllowNum", notAllowNum);
		retMap.put("ids", ids);
		addJSONObject(retMap);
		return SUCCESS;
	}
	
	/**
	 * 验证费用分类编码是否被使用，true 被使用，false 未被使用
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public String isUsedExpensesSortID()throws Exception{
		String id = request.getParameter("id");
		ExpensesSort expensesSort = financeService.getExpensesSortDetail(id);
		boolean flag = false;
		if(expensesSort != null){
			flag = true;
		}
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 验证费用分类名称是否被使用，true 被使用，false 未被使用
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public String isUsedExpensesSortName()throws Exception{
		String name = request.getParameter("name");
		boolean flag = financeService.isUsedExpensesSortName(name);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/*---------------------银行档案---------------------------------*/
	
	/**
	 * 显示银行档案页面
	 */
	public String showBankPage()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 显示银行档案新增页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public String showBankAddPage()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 显示银行档案修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public String showBankEditPage()throws Exception{
		String id = request.getParameter("id");
		Bank bank = financeService.getBankDetail(id);
		request.setAttribute("bank", bank);
		request.setAttribute("editFlag", canTableDataDelete("t_base_finance_bank", id));
		return SUCCESS;
	}
	
	/**
	 * 显示银行档案查看页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public String showBankViewPage()throws Exception{
		String id = request.getParameter("id");
		Bank bank = financeService.getBankDetail(id);
		request.setAttribute("bank", bank);
		return SUCCESS;
	}
	
	/**
	 * 获取银行档案列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public String getBankListPage()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeService.getBankList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 新增银行档案
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public String addBank()throws Exception{
		String type = request.getParameter("type");
		if("hold".equals(type)){
			bank.setState("3");
		}
		else if("save".equals(type)){
			bank.setState("2");
		}
		SysUser sysUser = getSysUser();
		bank.setAdddeptid(sysUser.getDepartmentid());
		bank.setAdddeptname(sysUser.getDepartmentname());
		bank.setAdduserid(sysUser.getUserid());
		bank.setAddusername(sysUser.getName());
		boolean flag = financeService.addBank(bank);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 修改银行档案
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public String editBank()throws Exception{
		String type = request.getParameter("type");
		if(!"1".equals(bank.getState()) && !"0".equals(bank.getState())){
			if("hold".equals(type)){
				bank.setState("3");
			}
			else if("save".equals(type)){
				bank.setState("2");
			}
		}
		SysUser sysUser = getSysUser();
		bank.setModifyuserid(sysUser.getUserid());
		bank.setModifyusername(sysUser.getName());
		boolean flag = financeService.editBank(bank);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 启用银行档案
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 12, 2013
	 */
	public String openBank() throws Exception{
		String id = request.getParameter("id");
		boolean flag = financeService.openBank(id);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 禁用银行档案
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public String closeBank()throws Exception{
		String id = request.getParameter("id");
		boolean flag = financeService.closeBank(id);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 删除银行档案
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public String deleteBank()throws Exception{
		String id = request.getParameter("id");
		//判断是否被引用true 可以删除 false不可以删除
		boolean delFlag = canTableDataDelete("t_base_finance_bank", id);
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag = financeService.deleteBank(id);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 验证银行编码是否重复
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public String checkBankidUserd()throws Exception{
		String id = request.getParameter("id");
		boolean flag = financeService.checkBankidUserd(id);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 验证银行名称是否重复
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public String checkBandAccountUserd()throws Exception{
		String account = request.getParameter("account");
		boolean flag = financeService.checkBandAccountUserd(account);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
}

