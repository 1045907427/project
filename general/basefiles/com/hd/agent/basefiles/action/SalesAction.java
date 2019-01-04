/**
 * @(#)SalesAction.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Apr 10, 2013 zhengziyong 创建版本
 */
package com.hd.agent.basefiles.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.IContacterService;
import com.hd.agent.basefiles.service.IDepartMentService;
import com.hd.agent.basefiles.service.IPersonnelService;
import com.hd.agent.basefiles.service.ISalesService;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.model.AttachFile;
import com.hd.agent.common.service.IAttachFileService;
import com.hd.agent.common.service.IExcelService;
import com.hd.agent.common.util.*;
import com.hd.agent.storage.service.IStorageSaleOutService;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.model.SysParam;
import com.hd.agent.system.model.TableColumn;
import com.hd.agent.system.service.ITaskScheduleService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 基础档案-销售
 * @author zhengziyong
 */
public class SalesAction extends BaseAction {

	private ISalesService salesService;
	private IContacterService contacterService;
	private SalesArea salesArea;
	private CustomerSort customerSort;
	private Customer customer;
	private CustomerAndSort customerAndSort;
	private CustomerPrice customerPrice;
	private IExcelService excelService;
	private ITaskScheduleService taskScheduleService;
	private IPersonnelService personnelService;
	private IDepartMentService departMentService ;
    private IAttachFileService attachFileService ;
	private CustomerBrandSettletype customerBrandSettletype;

    private DistributionRule distributionRule;

    public IAttachFileService getAttachFileService() {
        return attachFileService;
    }

    public void setAttachFileService(IAttachFileService attachFileService) {
        this.attachFileService = attachFileService;
    }

    public IDepartMentService getDepartMentService() {
		return departMentService;
	}
	
	public void setDepartMentService(IDepartMentService departMentService) {
		this.departMentService = departMentService;
	}	
	
	public IExcelService getExcelService() {
		return excelService;
	}

	public void setExcelService(IExcelService excelService) {
		this.excelService = excelService;
	}

	public IPersonnelService getPersonnelService() {
		return personnelService;
	}

	public void setPersonnelService(IPersonnelService personnelService) {
		this.personnelService = personnelService;
	}

	public CustomerPrice getCustomerPrice() {
		return customerPrice;
	}

	public void setCustomerPrice(CustomerPrice customerPrice) {
		this.customerPrice = customerPrice;
	}

	public ISalesService getSalesService() {
		return salesService;
	}

	public void setSalesService(ISalesService salesService) {
		this.salesService = salesService;
	}

	public IContacterService getContacterService() {
		return contacterService;
	}

	public void setContacterService(IContacterService contacterService) {
		this.contacterService = contacterService;
	}

	public SalesArea getSalesArea() {
		return salesArea;
	}

	public void setSalesArea(SalesArea salesArea) {
		this.salesArea = salesArea;
	}

	public CustomerSort getCustomerSort() {
		return customerSort;
	}

	public void setCustomerSort(CustomerSort customerSort) {
		this.customerSort = customerSort;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public CustomerAndSort getCustomerAndSort() {
		return customerAndSort;
	}

	public void setCustomerAndSort(CustomerAndSort customerAndSort) {
		this.customerAndSort = customerAndSort;
	}
	
	public ITaskScheduleService getTaskScheduleService() {
		return taskScheduleService;
	}

	public void setTaskScheduleService(ITaskScheduleService taskScheduleService) {
		this.taskScheduleService = taskScheduleService;
	}

	public CustomerBrandSettletype getCustomerBrandSettletype() {
		return customerBrandSettletype;
	}

	public void setCustomerBrandSettletype(CustomerBrandSettletype customerBrandSettletype) {
		this.customerBrandSettletype = customerBrandSettletype;
	}

    public DistributionRule getDistributionRule() {
        return distributionRule;
    }

    public void setDistributionRule(DistributionRule distributionRule) {
        this.distributionRule = distributionRule;
    }
    /**销售区域开始*/
	/**
	 * 销售区域显示页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 10, 2013
	 */
	public String salesArea() throws Exception {
		List list = getBaseFilesLevelService().showFilesLevelList("t_base_sales_area");
		int len = 0;
		if(null != list && list.size() != 0){
			len = list.size();
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<list.size();i++){
				FilesLevel level = (FilesLevel)list.get(i);
				sb.append(level.getLen() + ",");
			}
			if(sb.length()>1){
				request.setAttribute("lenStr", sb.deleteCharAt(sb.length()-1).toString());
			}
		}
		request.setAttribute("len", len);
		return SUCCESS;
	}
	
	/**
	 * 获取销售区域树状结构数据
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 10, 2013
	 */
	public String getSalesAreaTree() throws Exception{
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		List<SalesArea> list = salesService.getSalesAreaList(pageMap);
		Map<String, String> first = new HashMap<String, String>();
		first.put("id", "");
		first.put("name", "销售区域");
		first.put("open", "true");
		result.add(first);
		for(SalesArea area : list){
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", area.getId());
			map.put("pid", area.getPid());
			map.put("name", area.getThisname());
			map.put("state", area.getState());
			result.add(map);
		}
		addJSONArray(result);
		return SUCCESS;
	}
	
	/**
	 * 销售区域添加页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public String salesAreaAddPage() throws Exception{
		String id = request.getParameter("id"); //当为空时添加的销售区域为一级目录，否则该编号为添加区域的父级
		SalesArea salesArea = salesService.getSalesAreaDetail(id);
		int len = 0;
		if(salesArea != null && StringUtils.isNotEmpty(salesArea.getId())){
			len = salesArea.getId().length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_sales_area", len);
		request.setAttribute("salesArea", salesArea);
		request.setAttribute("len", nextLen);
		return SUCCESS;
	}
	
	/**
	 * 添加销售区域
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="SalesArea",type=2,value="")
	public String addSalesArea() throws Exception{
		String addType = request.getParameter("addType");
		if("temp".equals(addType)){
			salesArea.setState("3");
		}
		if("real".equals(addType)){
			salesArea.setState("2");
		}
		SysUser sysUser = getSysUser();
		salesArea.setAdduserid(sysUser.getUserid());
		salesArea.setAdddeptid(sysUser.getDepartmentid());
		salesArea.setAdddeptname(sysUser.getDepartmentname());
		salesArea.setAddusername(sysUser.getName());
		boolean flag = salesService.addSalesArea(salesArea);
		addLog("新增销售区域 编号:"+salesArea.getId(),flag);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", flag);
		map.put("backid", salesArea.getId());
		map.put("type", "add");
		Map<String, String> node = new HashMap<String, String>();
		node.put("id", salesArea.getId());
		node.put("pid", salesArea.getPid());
		node.put("name", salesArea.getThisname());
		node.put("state", salesArea.getState());
		map.put("node", node);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 修改销售区域信息页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public String salesAreaEditPage() throws Exception{
		String id = request.getParameter("id");
		SalesArea salesArea = salesService.getSalesAreaDetail(id);
		List stateList = getBaseSysCodeService().showSysCodeListByType("state");
		if(StringUtils.isNotEmpty(salesArea.getPid())){
			SalesArea area = salesService.getSalesAreaDetail(salesArea.getPid());
			request.setAttribute("parentName", area.getName());
		}
		int len = 0;
		if(salesArea != null && StringUtils.isNotEmpty(salesArea.getPid())){
			len = salesArea.getPid().length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_sales_area", len);
		request.setAttribute("len", nextLen);
		request.setAttribute("editFlag", canTableDataDelete("t_base_sales_area", id));
		request.setAttribute("stateList", stateList);
		request.setAttribute("salesArea", salesArea);
		return SUCCESS;
	}
	
	/**
	 * 修改销售区域信息
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="SalesArea",type=3,value="")
	public String updateSalesArea() throws Exception{
		String addType = request.getParameter("addType");
		if("0".equals(salesArea.getState()) || "1".equals(salesArea.getState())){
			
		}
		else{
			if("real".equals(addType)){
				salesArea.setState("2");
			}
		}
		SysUser sysUser = getSysUser();
		salesArea.setModifyuserid(sysUser.getUserid());
		salesArea.setModifyusername(sysUser.getName());
		Map map = salesService.updateSalesArea(salesArea);
		addLog("修改销售区域 编号:"+salesArea.getId(),map.get("flag").equals(true));
		map.put("backid", salesArea.getId());
		map.put("oldid", salesArea.getOldid());
		map.put("type", "edit");
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 销售区域复制页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 12, 2013
	 */
	public String salesAreaCopyPage() throws Exception{
		String id = request.getParameter("id");
		SalesArea salesArea = salesService.getSalesAreaDetail(id);
		if(StringUtils.isNotEmpty(salesArea.getPid())){
			SalesArea area = salesService.getSalesAreaDetail(salesArea.getPid());
			request.setAttribute("parentName", area.getName());
		}
		request.setAttribute("salesArea", salesArea);
		return SUCCESS;
	}
	
	/**
	 * 销售区域查看页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public String salesAreaViewPage() throws Exception{
		String id = request.getParameter("id");
		SalesArea salesArea = salesService.getSalesAreaDetail(id);
		List stateList = getBaseSysCodeService().showSysCodeListByType("state");
		request.setAttribute("stateList", stateList);
		request.setAttribute("salesArea", salesArea);
		return SUCCESS;
	}
	
	/**
	 * 删除销售区域信息，删除时判断该信息是否被引用，引用则无法删除。
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="SalesArea",type=4,value="")
	public String deleteSalesArea() throws Exception{
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_base_sales_area", id); 
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag = salesService.deleteSalesArea(id);
		addLog("删除销售区域 编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 启用销售区域
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="SalesArea",type=0,value="")
	public String openSalesArea() throws Exception{
		String id = request.getParameter("id");
		SalesArea area = new SalesArea();
		SysUser sysUser = getSysUser();
		area.setOpenuserid(sysUser.getUserid());
		area.setOpenusername(sysUser.getName());
		area.setId(id);
		boolean flag = salesService.updateSalesAreaOpen(area);
		addLog("启用销售区域 编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 禁用销售区域，如果该节点是父节点，则禁用下面所有为启用状态的子节点。
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="SalesArea",type=0,value="")
	public String closeSalesArea() throws Exception{
		String id = request.getParameter("id");
		SysUser sysUser = getSysUser();
		Map condition = new HashMap();
		condition.put("id", id);
		pageMap.setCondition(condition);
		String retids = null;
		List<SalesArea> list = salesService.getSalesAreaParentAllChildren(pageMap); //查询该节点及所有子节点信息
		int successNum = 0,failureNum = 0,notAllowNum = 0,userNum = 0;
		List<String> ids = new ArrayList<String>();
		for(SalesArea salesArea: list){ //循环所有节点信息，判断可以禁用的则禁用。
			if(!"1".equals(salesArea.getState())){
				notAllowNum++;
			}
			else{
				if(!canTableDataDelete("t_base_sales_area",id)){
					userNum++;
				}else{
					SalesArea area = new SalesArea();
					area.setCloseuserid(sysUser.getUserid());
					area.setCloseusername(sysUser.getName());
					area.setId(salesArea.getId());
					boolean flag = salesService.updateSalesAreaClose(area);
					if(flag){
						successNum++;
						ids.add(salesArea.getId()); //返回所有禁用的记录编号，供前台更新树节点信息
						if(StringUtils.isNotEmpty(retids)){
							retids += "," + salesArea.getId();
						}else{
							retids = salesArea.getId();
						}
					}
					else{
						failureNum++;
					}
				}
			}
		}
		if(successNum > 0){
			addLog("禁用销售区域 编号:"+retids,true);
		}
		Map map = new HashMap();
		map.put("successNum", successNum);
		map.put("failureNum", failureNum);
		map.put("notAllowNum", notAllowNum);
		map.put("userNum", userNum);
		map.put("ids", ids);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 验证编号是否被使用
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 13, 2013
	 */
	public String salesAreaNOUsed() throws Exception{
		String id = request.getParameter("id");
		SalesArea salesArea = salesService.getSalesAreaDetail(id);
		boolean flag = false;
		if(salesArea != null){
			flag = true;
		}
		addJSONObject("flag", flag);
		return SUCCESS;
	}

    /**
     * 判断店号是否重复
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date 2015-05-20
     */
    public String shopnoIsRepeat()throws Exception{
        String pcustomerid = request.getParameter("pcustomerid");
        String shopno = request.getParameter("shopno");
        boolean flag = salesService.getShopnoIsRepeatFlag(pcustomerid,shopno);
        addJSONObject("flag", flag);
        return SUCCESS;
    }
	
	/**
	 * 判断本级名称是否重复，true 不重复，false 重复
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 26, 2014
	 */
	public String isRepeatSalesAreaThisname()throws Exception{
		String thisname = request.getParameter("thisname");
		boolean flag = salesService.isRepeatSalesAreaThisname(thisname);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**销售区域结束*/
	
	/**客户关系开始*/
	/**
	 * 客户关系显示页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 10, 2013
	 */
	public String customerSort() throws Exception {
		List list = getBaseFilesLevelService().showFilesLevelList("t_base_sales_customersort");
		int len = 0;
		if(null != list && list.size() != 0){
			len = list.size();
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<list.size();i++){
				FilesLevel level = (FilesLevel)list.get(i);
				sb.append(level.getLen() + ",");
			}
			if(sb.length()>1){
				request.setAttribute("lenStr", sb.deleteCharAt(sb.length()-1).toString());
			}
		}
		request.setAttribute("len", len);
		return SUCCESS;
	}
	
	/**
	 * 获取客户关系树状结构数据
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 10, 2013
	 */
	public String getCustomerSortTree() throws Exception{
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		List<CustomerSort> list = salesService.getCustomerSortList(pageMap);
		Map<String, String> first = new HashMap<String, String>();
		first.put("id", "");
		first.put("name", "客户分类");
		first.put("open", "true");
		result.add(first);
		for(CustomerSort customer : list){
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", customer.getId());
			map.put("pid", customer.getPid());
			map.put("name", customer.getThisname());
			map.put("state", customer.getState());
			result.add(map);
		}
		addJSONArray(result);
		return SUCCESS;
	}
	
	/**
	 * 客户关系添加页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public String customerSortAddPage() throws Exception{
		String id = request.getParameter("id"); //当为空时添加的客户关系为一级目录，否则该编号为添加客户的父级
		CustomerSort customerSort = salesService.getCustomerSortDetail(id);
		int len = 0;
		if(customerSort != null && StringUtils.isNotEmpty(customerSort.getId())){
			len = customerSort.getId().length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_sales_customersort", len);
		request.setAttribute("customerSort", customerSort);
		request.setAttribute("len", nextLen);
		return SUCCESS;
	}
	
	/**
	 * 添加客户关系
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="CustomerSort",type=2,value="")
	public String addCustomerSort() throws Exception{
		String addType = request.getParameter("addType");
		if("temp".equals(addType)){
			customerSort.setState("3");
		}
		if("real".equals(addType)){
			customerSort.setState("2");
		}
		SysUser sysUser = getSysUser();
		customerSort.setAdduserid(sysUser.getUserid());
		customerSort.setAdddeptid(sysUser.getDepartmentid());
		customerSort.setAdddeptname(sysUser.getDepartmentname());
		customerSort.setAddusername(sysUser.getName());
		boolean flag = salesService.addCustomerSort(customerSort);
		addLog("新增客户分类 编号:"+customerSort.getId(),flag);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", flag);
		map.put("backid", customerSort.getId());
		map.put("type", "add");
		Map<String, String> node = new HashMap<String, String>();
		node.put("id", customerSort.getId());
		node.put("pid", customerSort.getPid());
		node.put("name", customerSort.getThisname());
		node.put("state", customerSort.getState());
		map.put("node", node);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 修改客户关系信息页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public String customerSortEditPage() throws Exception{
		String id = request.getParameter("id");
		CustomerSort customerSort = salesService.getCustomerSortDetail(id);
		List stateList = getBaseSysCodeService().showSysCodeListByType("state");
		if(StringUtils.isNotEmpty(customerSort.getPid())){
			CustomerSort sort = salesService.getCustomerSortDetail(customerSort.getPid());
			request.setAttribute("parentName", sort.getName());
		}
		int len = 0;
		if(customerSort != null && StringUtils.isNotEmpty(customerSort.getPid())){
			len = customerSort.getPid().length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_sales_customersort", len);
		request.setAttribute("len", nextLen);
		request.setAttribute("editFlag", canTableDataDelete("t_base_sales_customersort", id));
		request.setAttribute("stateList", stateList);
		request.setAttribute("customerSort", customerSort);
		return SUCCESS;
	}
	
	/**
	 * 修改客户关系信息
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="CustomerSort",type=3,value="")
	public String updateCustomerSort() throws Exception{
		String addType = request.getParameter("addType");
		if("0".equals(customerSort.getState()) || "1".equals(customerSort.getState())){
			
		}
		else{
			if("real".equals(addType)){
				customerSort.setState("2");
			}
		}
		SysUser sysUser = getSysUser();
		customerSort.setModifyuserid(sysUser.getUserid());
		customerSort.setModifyusername(sysUser.getName());
		Map map = salesService.updateCustomerSort(customerSort);
		addLog("修改客户分类 编号:"+customerSort.getId(),map.get("flag").equals(true));
		map.put("backid", customerSort.getId());
		map.put("oldid", customerSort.getOldid());
		map.put("type", "edit");
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 客户关系复制页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 12, 2013
	 */
	public String customerSortCopyPage() throws Exception{
		String id = request.getParameter("id");
		CustomerSort customerSort = salesService.getCustomerSortDetail(id);
		if(StringUtils.isNotEmpty(customerSort.getPid())){
			CustomerSort sort = salesService.getCustomerSortDetail(customerSort.getPid());
			request.setAttribute("parentName", sort.getName());
		}
		request.setAttribute("customerSort", customerSort);
		return SUCCESS;
	}
	
	/**
	 * 客户关系查看页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public String customerSortViewPage() throws Exception{
		String id = request.getParameter("id");
		CustomerSort customerSort = salesService.getCustomerSortDetail(id);
		List stateList = getBaseSysCodeService().showSysCodeListByType("state");
		request.setAttribute("stateList", stateList);
		request.setAttribute("customerSort", customerSort);
		return SUCCESS;
	}
	
	/**
	 * 删除客户关系信息，删除时判断该信息是否被引用，引用则无法删除。
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="CustomerSort",type=4,value="")
	public String deleteCustomerSort() throws Exception{
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_base_sales_customersort", id); 
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag = salesService.deleteCustomerSort(id);
		addLog("删除客户分类 编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 启用客户关系
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="CustomerSort",type=0,value="")
	public String openCustomerSort() throws Exception{
		String id = request.getParameter("id");
		CustomerSort customerSort = new CustomerSort();
		SysUser sysUser = getSysUser();
		customerSort.setOpenuserid(sysUser.getUserid());
		customerSort.setOpenusername(sysUser.getName());
		customerSort.setId(id);
		boolean flag = salesService.updateCustomerSortOpen(customerSort);
		addLog("启用客户分类 编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 禁用客户关系，如果该节点是父节点，则禁用下面所有为启用状态的子节点。
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="CustomerSort",type=0,value="")
	public String closeCustomerSort() throws Exception{
		String id = request.getParameter("id");
		SysUser sysUser = getSysUser();
		Map condition = new HashMap();
		condition.put("id", id);
		pageMap.setCondition(condition);
		List<CustomerSort> list = salesService.getCustomerSortParentAllChildren(pageMap); //查询该节点及所有子节点信息
		int successNum = 0,failureNum = 0,notAllowNum = 0,userNum = 0;
		String retids = null;
		List<String> ids = new ArrayList<String>();
		for(CustomerSort customerSort: list){ //循环所有节点信息，判断可以禁用的则禁用。
			if(!"1".equals(customerSort.getState())){
				notAllowNum++;
			}
			else{
                CustomerSort sort = new CustomerSort();
                sort.setCloseuserid(sysUser.getUserid());
                sort.setCloseusername(sysUser.getName());
                sort.setId(customerSort.getId());
                boolean flag = salesService.updateCustomerSortClose(sort);
                if(flag){
                    successNum++;
                    ids.add(customerSort.getId()); //返回所有禁用的记录编号，供前台更新树节点信息
                    if(StringUtils.isNotEmpty(retids)){
                        retids += "," + sort.getId();
                    }else{
                        retids = sort.getId();
                    }
                }
                else{
                    failureNum++;
                }
			}
		}
		if(successNum > 0){
			addLog("禁用客户分类 编号:"+retids,true);
		}
		Map map = new HashMap();
		map.put("successNum", successNum);
		map.put("failureNum", failureNum);
		map.put("notAllowNum", notAllowNum);
		map.put("userNum", userNum);
		map.put("ids", ids);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 验证编号是否被使用
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 13, 2013
	 */
	public String customerSortNOUsed() throws Exception{
		String id = request.getParameter("id");
		CustomerSort customerSort = salesService.getCustomerSortDetail(id);
		boolean flag = false;
		if(customerSort != null){
			flag = true;
		}
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 判断本级名称是否重复，true 不重复，false 重复
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 26, 2014
	 */
	public String isRepeatCustomerSortThisname()throws Exception{
		String thisname = request.getParameter("thisname");
		boolean flag = salesService.isRepeatCustomerSortThisname(thisname);
		addJSONObject("flag", flag);
		return "success";
	}

	/**
	 * 获取启用状态的客户分类列表
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author zhanghonghui
	 * @date Oct 12, 2016
	 */
	public String getCustomerSortEnableList() throws Exception{
		Map paramMap=new HashMap();
		paramMap.put("state","1");
		List<CustomerSort> list=salesService.getCustomerSortListByMap(paramMap);
		addJSONArray(list);
		return SUCCESS;
	}
	/**客户关系结束*/
	
	/**客户档案开始*/
	/**
	 * 客户档案页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 15, 2013
	 */
	public String customerPage() throws Exception{
		List list = getBaseFilesLevelService().showFilesLevelList("t_base_sales_customer");
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
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		request.setAttribute("type", type);
		request.setAttribute("id", id);
		return SUCCESS;
	}
	
	/**
	 * 获取客户档案树状结构数据
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 10, 2013
	 */
	public String getCustomerTree() throws Exception{
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		pageMap.setRows(10000);
		List<Customer> list = salesService.getCustomerList(pageMap);
		Map<String, String> first = new HashMap<String, String>();
		first.put("id", "");
		first.put("name", "客户档案");
		first.put("open", "true");
		result.add(first);
		for(Customer customer : list){
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", customer.getId());
			map.put("pid", customer.getPid());
			map.put("name", customer.getShortname());
			map.put("state", customer.getState());
			result.add(map);
		}
		addJSONArray(result);
		return SUCCESS;
	}
	
	/**
	 * 客户档案添加页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 15, 2013
	 */
	public String customerAddPage() throws Exception{
		String id = request.getParameter("id"); //当为空时添加的客户关系为一级目录，否则该编号为添加客户的父级
		Customer customer = salesService.getCustomerInfo(id);
		int len = 0;
		if(customer != null && StringUtils.isNotEmpty(customer.getId())){
			len = customer.getId().length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_sales_customer", len);
		List natureList = getBaseSysCodeService().showSysCodeListByType("firm_nature");
		List priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
		List dayList = new ArrayList();
		for(int i=1;i<=31;i++){
			dayList.add(String.valueOf(i));
		}
		List deptList = getDeptListByOperationType("4"); //销售部门
		List salesList = getPersListByOperationType("1");
		List tallyList = getPersListByOperationType("2");
		List promotionsortList = getBaseSysCodeService().showSysCodeListByType("promotionsort");
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		List creditratingList = getBaseSysCodeService().showSysCodeListByType("creditrating");
		request.setAttribute("canceltypeList", canceltypeList); 
		request.setAttribute("creditratingList", creditratingList); 
		request.setAttribute("deptList", deptList); 
		request.setAttribute("salesList", salesList); 
		request.setAttribute("tallyList", tallyList); 
		request.setAttribute("dayList", dayList); 
		request.setAttribute("natureList", natureList); //企业性质
		request.setAttribute("priceList", priceList); //价格套
		request.setAttribute("promotionsortList", promotionsortList);
		request.setAttribute("customer", customer); 
		request.setAttribute("len", nextLen); //级次对应长度
		return SUCCESS;
	}
	
	/**
	 * 获取自定义字段名称
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 16, 2013
	 */
	public String customerDIYFieldName() throws Exception{
		Map queryMap = new HashMap();
		queryMap.put("tablename", "t_base_sales_customer");
		List<TableColumn> list = getBaseDataDictionaryService().getTableColumnListBy(queryMap);
		Map map = new HashMap();
		for(TableColumn column : list){
			if(column.getColumnname().indexOf("field")>-1){
				map.put(column.getColumnname(), column.getColchnname());
			}
		}
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 添加客户档案信息
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 15, 2013
	 */
	@UserOperateLog(key="Customer",type=2,value="")
	public String addCustomer() throws Exception{
		String addType = request.getParameter("addType");
		if("temp".equals(addType)){
			customer.setState("3");
		}
		if("real".equals(addType)){
			if(StringUtils.isEmpty(customer.getState())){
				customer.setState("2");
			}
		}
		customer.setCustomerAndSort(customerAndSort);
		String priceJson = request.getParameter("priceJson");
		if(StringUtils.isNotEmpty(priceJson)){
			List<CustomerPrice> priceList = JSONUtils.jsonStrToList(priceJson, new CustomerPrice());
			customer.setPriceList(priceList);
		}
		boolean flag = salesService.addCustomer(customer);
		addLog("新增客户 编号:"+customer.getId(),flag);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("backid", customer.getId());
		map.put("type", "add");
		map.put("pid",customer.getPid());
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 修改客户档案信息页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public String customerEditPage() throws Exception{
		String id = request.getParameter("id");
		Customer customer = salesService.getCustomerInfo(id);
		//获取t_base_sales_customer表的字段编辑权限
		Map mapCols =  getBaseFilesEditWithAuthority("t_base_sales_customer", customer);
		if(!lockData("t_base_sales_customer", id)){
			request.setAttribute("lock", true);
			request.setAttribute("editMap", null);
			request.setAttribute("id", id);
			return SUCCESS;
		}
		request.setAttribute("editMap", mapCols);
		if(customer == null){
			return SUCCESS;
		}
		List stateList = getBaseSysCodeService().showSysCodeListByType("state");
		List natureList = getBaseSysCodeService().showSysCodeListByType("firm_nature");
		List priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
		int len = 0;
		if(customer != null && StringUtils.isNotEmpty(customer.getPid())){
			len = customer.getPid().length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_sales_customer", len);
		List dayList = new ArrayList();
		for(int i=1;i<=31;i++){
			dayList.add(String.valueOf(i));
		}
		String sortName = "";
		String contaterName = "";
		if(StringUtils.isNotEmpty(customer.getCustomersort())){
			CustomerSort customerSort = salesService.getCustomerSortDetail(customer.getCustomersort());
			if(customerSort != null){
				sortName = customerSort.getName();
			}
		}
		if(StringUtils.isNotEmpty(customer.getContact())){
			Contacter contacter = contacterService.getContacterDetail(customer.getContact());
			if(contacter != null){
				contaterName = contacter.getName();
				customer.setContactmobile(contacter.getMobile());
			}
		}
		List<Contacter> contacterList = contacterService.getContacterListByCustomer("1", id);
		for(Contacter contacter : contacterList){ //联系人状态
			for(int i=0; i< stateList.size(); i++){
				SysCode sysCode = (SysCode)stateList.get(i);
				if(contacter.getState().equals(sysCode.getCode())){
					contacter.setState(sysCode.getCodename());
				}
			}
		}
		List deptList = getDeptListByOperationType("4"); //销售部门
		List salesList = getPersListByOperationType("1");
		List tallyList = getPersListByOperationType("2");
		String jsonStr = JSONUtils.listToJsonStr(customer.getPriceList());
		List promotionsortList = getBaseSysCodeService().showSysCodeListByType("promotionsort");
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		List creditratingList = getBaseSysCodeService().showSysCodeListByType("creditrating");
		//结算方式
		if(StringUtils.isNotEmpty(customer.getSettletype())){
			Settlement settlement = getBaseFinanceService().getSettlemetDetail(customer.getSettletype());
			if(null != settlement){
				request.setAttribute("monthorday", settlement.getType());
			}
		}
		request.setAttribute("canceltypeList", canceltypeList); 
		request.setAttribute("creditratingList", creditratingList); 
		request.setAttribute("promotionsortList", promotionsortList);
		request.setAttribute("goodsJson", jsonStr);
		request.setAttribute("deptList", deptList); 
		request.setAttribute("salesList", salesList); 
		request.setAttribute("tallyList", tallyList); 
		request.setAttribute("contacterList", contacterList); //联系人列表
		request.setAttribute("dayList", dayList); 
		request.setAttribute("sortName", sortName);
		request.setAttribute("contaterName", contaterName);
		request.setAttribute("len", nextLen);
		request.setAttribute("editFlag", canTableDataDelete("t_base_sales_customer", id));
		request.setAttribute("stateList", stateList);
		request.setAttribute("natureList", natureList);
		request.setAttribute("priceList", priceList); //价格套
		request.setAttribute("customer", customer);
		return SUCCESS;
	}
	
	/**
	 * 修改客户档案信息
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="Customer",type=3,value="")
	public String updateCustomer() throws Exception{
		boolean lock = isLockEdit("t_base_sales_customer", customer.getId()); // 判断锁定并解锁
		if (!lock) { // 被锁定不能进行修改
			addJSONObject("lock", true);
			return SUCCESS;
		}
		String addType = request.getParameter("addType");
		String sortEdit = request.getParameter("sortEdit"); //判断对应分类没无修改
		String[] contacter = request.getParameterValues("contacterId");
		String[] isdefault = request.getParameterValues("contacterIsdefault");
		if("0".equals(customer.getState()) || "1".equals(customer.getState())){
			
		}
		else{
			if("real".equals(addType)){
				if(StringUtils.isEmpty(customer.getState())){
					customer.setState("2");
				}
			}
		}
		SysUser sysUser = getSysUser();
		customer.setModifyuserid(sysUser.getUserid());
		customer.setModifyusername(sysUser.getName());
		customer.setContacterId(contacter);
		customer.setContacterDefaultSort(isdefault);
		customer.setCustomerAndSort(customerAndSort);
		String priceJson = request.getParameter("priceJson");
		if(StringUtils.isNotEmpty(priceJson)){
			List<CustomerPrice> priceList = JSONUtils.jsonStrToList(priceJson, new CustomerPrice());
			customer.setPriceList(priceList);
		}
		//修改前客户档案
		Customer oldCustomer = salesService.getCustomerInfo(customer.getId());
		boolean flag = salesService.updateCustomer(customer, sortEdit);
		if(flag && customer.getOldid().equals(customer.getId())){
			addEditCustomerTaskSchedule(customer,oldCustomer,1);
		}
		addLog("修改客户 编号:"+customer.getId(),flag);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", flag);
		map.put("backid", customer.getId());
		map.put("customername", customer.getName());
		map.put("oldid", customer.getOldid());
		map.put("type", "edit");
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 客户档案复制页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 12, 2013
	 */
	public String customerCopyPage() throws Exception{
		String id = request.getParameter("id");
		Customer customer = salesService.getCustomerInfo(id);
		customer.setOldid(id);
		List stateList = getBaseSysCodeService().showSysCodeListByType("state");
		List natureList = getBaseSysCodeService().showSysCodeListByType("firm_nature");
		List priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
		int len = 0;
		if(customer != null && StringUtils.isNotEmpty(customer.getId())){
			len = customer.getId().length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_sales_customer", len);
		List dayList = new ArrayList();
		for(int i=1;i<=31;i++){
			dayList.add(String.valueOf(i));
		}
		String sortName = "";
		String contaterName = "";
		if(StringUtils.isNotEmpty(customer.getCustomersort())){
			CustomerSort customerSort = salesService.getCustomerSortDetail(customer.getCustomersort());
			if(customerSort != null){
				sortName = customerSort.getName();
			}
		}
		if(StringUtils.isNotEmpty(customer.getContact())){
			Contacter contacter = contacterService.getContacterDetail(customer.getContact());
			if(contacter != null){
				contaterName = contacter.getName();
				customer.setContactmobile(contacter.getMobile());
			}
		}
		List<Contacter> contacterList = contacterService.getContacterListByCustomer("1", id);
		for(Contacter contacter : contacterList){ //联系人状态
			for(int i=0; i< stateList.size(); i++){
				SysCode sysCode = (SysCode)stateList.get(i);
				if(contacter.getState().equals(sysCode.getCode())){
					contacter.setState(sysCode.getCodename());
				}
			}
		}
		List deptList = getDeptListByOperationType("4"); //销售部门
		List salesList = getPersListByOperationType("1");
		List tallyList = getPersListByOperationType("2");
		String jsonStr = JSONUtils.listToJsonStr(customer.getPriceList());
		List promotionsortList = getBaseSysCodeService().showSysCodeListByType("promotionsort");
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		List creditratingList = getBaseSysCodeService().showSysCodeListByType("creditrating");
		//结算方式
		if(StringUtils.isNotEmpty(customer.getSettletype())){
			Settlement settlement = getBaseFinanceService().getSettlemetDetail(customer.getSettletype());
			if(null != settlement){
				request.setAttribute("monthorday", settlement.getType());
			}
		}
		request.setAttribute("canceltypeList", canceltypeList); 
		request.setAttribute("creditratingList", creditratingList); 
		request.setAttribute("promotionsortList", promotionsortList);
		request.setAttribute("goodsJson", jsonStr);
		request.setAttribute("deptList", deptList); 
		request.setAttribute("salesList", salesList); 
		request.setAttribute("tallyList", tallyList); 
		request.setAttribute("contacterList", contacterList); //联系人列表
		request.setAttribute("dayList", dayList); 
		request.setAttribute("sortName", sortName);
		request.setAttribute("contaterName", contaterName);
		request.setAttribute("natureList", natureList);
		request.setAttribute("priceList", priceList); //价格套
		request.setAttribute("len", nextLen);
		request.setAttribute("customer", customer);
		return SUCCESS;
	}
	
	/**
	 * 客户档案查看页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 15, 2013
	 */
	public String customerViewPage() throws Exception{
		String id = request.getParameter("id");
		Customer customer = salesService.getCustomerInfo(id);
		if(customer == null){
			return SUCCESS;
		}
		List stateList = getBaseSysCodeService().showSysCodeListByType("state");
		List natureList = getBaseSysCodeService().showSysCodeListByType("firm_nature");
		List priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
		List dayList = new ArrayList();
		for(int i=1;i<=31;i++){
			dayList.add(String.valueOf(i));
		}
		String sortName = "";
		String contacterName = "";
		if(StringUtils.isNotEmpty(customer.getCustomersort())){
			CustomerSort customerSort = salesService.getCustomerSortDetail(customer.getCustomersort());
			if(customerSort != null){
				sortName = customerSort.getName();
			}
		}
		if(StringUtils.isNotEmpty(customer.getContact())){
			Contacter contacter = contacterService.getContacterDetail(customer.getContact());
			if(contacter != null){
				contacterName = contacter.getName();
				customer.setContactmobile(contacter.getMobile());
			}
		}
		List<Contacter> contacterList = contacterService.getContacterListByCustomer("1", id);
		for(Contacter contacter : contacterList){ //联系人状态
			for(int i=0; i< stateList.size(); i++){
				SysCode sysCode = (SysCode)stateList.get(i);
				if(contacter.getState().equals(sysCode.getCode())){
					contacter.setState(sysCode.getCodename());
				}
			}
		}
		List deptList = getDeptListByOperationType("4"); //销售部门
		List salesList = getPersListByOperationType("1");
		List tallyList = getPersListByOperationType("2");
		String jsonStr = JSONUtils.listToJsonStr(customer.getPriceList());
		List promotionsortList = getBaseSysCodeService().showSysCodeListByType("promotionsort");
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		List creditratingList = getBaseSysCodeService().showSysCodeListByType("creditrating");
		//获取t_base_sales_customer表的字段编辑权限
		Map colMap = getAccessColumn("t_base_sales_customer");
		request.setAttribute("showMap", colMap);
		request.setAttribute("canceltypeList", canceltypeList); 
		request.setAttribute("creditratingList", creditratingList); 
		request.setAttribute("promotionsortList", promotionsortList);
		request.setAttribute("goodsJson", jsonStr);
		request.setAttribute("deptList", deptList); 
		request.setAttribute("salesList", salesList); 
		request.setAttribute("tallyList", tallyList); 
		request.setAttribute("contacterList", contacterList); //联系人列表
		request.setAttribute("dayList", dayList); 
		request.setAttribute("sortName", sortName);
		request.setAttribute("contacterName", contacterName);
		request.setAttribute("stateList", stateList);
		request.setAttribute("natureList", natureList);
		request.setAttribute("priceList", priceList); //价格套
		request.setAttribute("customer", customer);
		return SUCCESS;
	}
	
	/**
	 * 删除客户档案信息，删除时判断该信息是否被引用，引用则无法删除。
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="Customer",type=4,value="")
	public String deleteCustomer() throws Exception{
		String id = request.getParameter("id");
		//判断数据是否被加锁,True已被加锁。Fasle未加锁。（网络互斥）
		boolean delFlag = canTableDataDelete("t_base_sales_customer", id); 
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag = salesService.deleteCustomer(id);
		addLog("删除客户 编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 批量删除客户
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 4, 2013
	 */
	@UserOperateLog(key="Customer",type=4,value="")
	public String deleteCustomerFromListPage()throws  Exception{
		String idStr = request.getParameter("idStr");
		int useNum = 0,sucNum = 0,failNum = 0,lockNum = 0;
		String retids = null;
		if(StringUtils.isNotEmpty(idStr)){
			String[] idArr = idStr.split(",");
			for(String id : idArr){
				//判断数据是否被加锁,True已被加锁。Fasle未加锁。（网络互斥）
				if(!isLock("t_base_sales_customer", id)){
					//判断是否被引用
					boolean delFlag = canTableDataDelete("t_base_sales_customer", id);
					if(!delFlag){
						useNum++;
					}
					else{
						boolean flag = salesService.deleteCustomer(id);
						if(flag){
							sucNum++;
							if(StringUtils.isNotEmpty(retids)){
								retids += "," + id;
							}else{
								retids = id;
							}
						}
						else{
							failNum++;
						}
					}
				}
				else{
					lockNum++;
				}
			}
		}
		if(sucNum > 0){
			addLog("删除客户 编号:"+retids,true);
		}
		Map map = new HashMap();
		map.put("useNum", useNum);
		map.put("sucNum", sucNum);
		map.put("lockNum", lockNum);
		map.put("failNum", failNum);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 启用客户档案
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="Customer",type=0,value="")
	public String openCustomer() throws Exception{
		String id = request.getParameter("id");
		Customer customer = new Customer();
		SysUser sysUser = getSysUser();
		customer.setOpenuserid(sysUser.getUserid());
		customer.setOpenusername(sysUser.getName());
		customer.setId(id);
		boolean flag = salesService.updateCustomerOpen(customer);
		addLog("启用客户 编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 禁用客户档案，如果该节点是父节点，则禁用下面所有为启用状态的子节点。
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	@UserOperateLog(key="Customer",type=0,value="")
	public String closeCustomer() throws Exception{
		String id = request.getParameter("id");
		SysUser sysUser = getSysUser();
		Map condition = new HashMap();
		condition.put("id", id);
		pageMap.setCondition(condition);
		List<Customer> list = salesService.getCustomerParentAllChildren(pageMap); //查询该节点及所有子节点信息
		int successNum = 0;
		int failureNum = 0;
		int notAllowNum = 0;
		String retids = null;
		List<String> ids = new ArrayList<String>();
		for(Customer customer: list){ //循环所有节点信息，判断可以禁用的则禁用。
			if(!"1".equals(customer.getState())){
				notAllowNum++;
			}
			else{
				Customer cust = new Customer();
				cust.setCloseuserid(sysUser.getUserid());
				cust.setCloseusername(sysUser.getName());
				cust.setId(customer.getId());
				boolean flag = salesService.updateCustomerClose(cust);
				if(flag){
					successNum++;
					ids.add(customer.getId()); //返回所有禁用的记录编号，供前台更新树节点信息
					if(StringUtils.isNotEmpty(retids)){
						retids += "," + cust.getId();
					}else{
						retids = cust.getId();
					}
				}
				else{
					failureNum++;
				}
			}
		}
		if(successNum > 0){
			addLog("禁用客户 编号:"+retids,true);
		}
		Map map = new HashMap();
		map.put("successNum", successNum);
		map.put("failureNum", failureNum);
		map.put("notAllowNum", notAllowNum);
		map.put("ids", ids);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 验证编号是否被使用
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 13, 2013
	 */
	public String customerNOUsed() throws Exception{
		String id = request.getParameter("id");
		Customer customer = salesService.getCustomerDetail(id);
		boolean flag = false;
		if(customer != null){
			flag = true;
		}
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 验证名称是否重复
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 7, 2013
	 */
	public String customerNameNoUsed()throws Exception{
		String name = request.getParameter("name");
		boolean flag = salesService.customerNameNoUsed(name);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 根据客户编码获取下级编码长度
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 21, 2013
	 */
	public String getNextCustomerLenById()throws Exception{
		String id = request.getParameter("id");
		int nextLen = getBaseTreeFilesNext("t_base_sales_customer", id.length());
		Map map = new HashMap();
		map.put("len", nextLen);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 获取客户档案对应分类列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 22, 2013
	 */
	public String getCustomerAndSortList() throws Exception{
		String id = request.getParameter("id");
		List list = salesService.getCustomerAndSortListByCustomer(id);
		Map map = new HashMap();
		map.put("rows", list);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 客户列表页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jul 4, 2013
	 */
	public String customerListPage() throws Exception{
		
		return SUCCESS;
	}
	
	/**
	 * 获取客户列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jul 4, 2013
	 */
	public String getCustomerList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesService.getCustomerData(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 获取合同商品客户列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jul 4, 2013
	 */
	public String getCustomerListForPact() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesService.getCustomerDataForPact(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 获取未选择客户列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 25, 2013
	 */
	public String getCustomerListForCombobox()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesService.getCustomerListForCombobox(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 客户选择页面（通用客户控件使用）
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 3, 2013
	 */
	public String showCustomerSelectPage() throws Exception{
		String id = request.getParameter("id");
		String divid = request.getParameter("divid");
		String paramRule = request.getParameter("paramRule");
		String ishead = request.getParameter("ishead");
		request.setAttribute("paramRule", paramRule);
		request.setAttribute("divid", divid);
		request.setAttribute("id", id);
		request.setAttribute("ishead", ishead);
		return "success";
	}
	/**
	 * 根据id值获取客户列表
	 * id查询客户编号 客户助记码
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 3, 2013
	 */
	public String getCustomerSelectListData() throws Exception{
		Map map = CommonUtils.changeMapByEscapeSql(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesService.getCustomerSelectListData(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	
	/*--------------------客户档案快捷操作----------------------------------*/
	
	/**
	 * 显示客户档案快捷页面
	 */
	public String showCustomerShortcutPage()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 显示客户档案简化版页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 13, 2013
	 */
	public String showCustomerShortcutMainPage()throws Exception{
		List list = getBaseFilesLevelService().showFilesLevelList("t_base_sales_customer");
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
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		request.setAttribute("type", type);
		request.setAttribute("id", id);
		return SUCCESS;
	}
	
	/**
	 * 显示客户档案新增快捷页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 7, 2013
	 */
	public String showCustomerShortcutAddPage()throws Exception{
		String id = request.getParameter("id"); //当为空时添加的客户关系为一级目录，否则该编号为添加客户的父级
		Customer customer = salesService.getCustomerInfo(id);
		int len = 0;
		if(customer != null && StringUtils.isNotEmpty(customer.getId())){
			len = customer.getId().length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_sales_customer", len);
		List priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
		List natureList = getBaseSysCodeService().showSysCodeListByType("firm_nature");
		List promotionsortList = getBaseSysCodeService().showSysCodeListByType("promotionsort");
		List dayList = new ArrayList();
		for(int i=1;i<=31;i++){
			dayList.add(String.valueOf(i));
		}
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		List creditratingList = getBaseSysCodeService().showSysCodeListByType("creditrating");
		request.setAttribute("creditratingList", creditratingList); 
		request.setAttribute("canceltypeList", canceltypeList); 
		request.setAttribute("dayList", dayList); 
		request.setAttribute("promotionsortList", promotionsortList);
		request.setAttribute("priceList", priceList);
		request.setAttribute("natureList", natureList);
		request.setAttribute("len", nextLen);
		return SUCCESS;
	}
	
	/**
	 * 显示客户档案复制快捷页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 7, 2013
	 */
	public String showCustomerShortcutCopyPage()throws Exception{
		String id = request.getParameter("id");
		Customer customer = salesService.getCustomerInfo(id);
		List priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
		List natureList = getBaseSysCodeService().showSysCodeListByType("firm_nature");
		List promotionsortList = getBaseSysCodeService().showSysCodeListByType("promotionsort");
		List dayList = new ArrayList();
		for(int i=1;i<=31;i++){
			dayList.add(String.valueOf(i));
		}
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		int len = 0;
		if(customer != null && StringUtils.isNotEmpty(customer.getId())){
			len = customer.getId().length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_sales_customer", len);
		List creditratingList = getBaseSysCodeService().showSysCodeListByType("creditrating");
		//结算方式
		if(StringUtils.isNotEmpty(customer.getSettletype())){
			Settlement settlement = getBaseFinanceService().getSettlemetDetail(customer.getSettletype());
			if(null != settlement){
				request.setAttribute("monthorday", settlement.getType());
			}
		}
		request.setAttribute("creditratingList", creditratingList); 
		request.setAttribute("canceltypeList", canceltypeList); 
		request.setAttribute("dayList", dayList); 
		request.setAttribute("promotionsortList", promotionsortList);
		request.setAttribute("priceList", priceList);
		request.setAttribute("natureList", natureList);
		request.setAttribute("len", nextLen);
		request.setAttribute("customer", customer);
		return SUCCESS;
	}
	
	/**
	 * 显示客户档案修改快捷页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 7, 2013
	 */
	public String showCustomerShortcutEditPage()throws Exception{
		String id = request.getParameter("id");
		Customer customer = salesService.getCustomerInfo(id);
		//获取t_base_sales_customer表的字段编辑权限
		Map mapCols =  getBaseFilesEditWithAuthority("t_base_sales_customer", customer);
		if(!lockData("t_base_sales_customer", id)){
			request.setAttribute("lock", true);
			request.setAttribute("editMap", null);
			request.setAttribute("id", id);
			return SUCCESS;
		}
		request.setAttribute("editMap", mapCols);
		List priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
		List natureList = getBaseSysCodeService().showSysCodeListByType("firm_nature");
		List promotionsortList = getBaseSysCodeService().showSysCodeListByType("promotionsort");
		List stateList = getBaseSysCodeService().showSysCodeListByType("state");
		List dayList = new ArrayList();
		for(int i=1;i<=31;i++){
			dayList.add(String.valueOf(i));
		}
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		List creditratingList = getBaseSysCodeService().showSysCodeListByType("creditrating");
		//结算方式
		if(StringUtils.isNotEmpty(customer.getSettletype())){
			Settlement settlement = getBaseFinanceService().getSettlemetDetail(customer.getSettletype());
			if(null != settlement){
				request.setAttribute("monthorday", settlement.getType());
			}
		}
		request.setAttribute("editFlag", canTableDataDelete("t_base_sales_customer", id));
		request.setAttribute("creditratingList", creditratingList); 
		request.setAttribute("canceltypeList", canceltypeList); 
		request.setAttribute("dayList", dayList); 
		request.setAttribute("stateList", stateList);
		request.setAttribute("promotionsortList", promotionsortList);
		request.setAttribute("priceList", priceList);
		request.setAttribute("natureList", natureList);
		request.setAttribute("customer", customer);
		return SUCCESS;
	}
	
	/**
	 * 显示客户档案查看快捷页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 7, 2013
	 */
	public String showCustomerShortcutViewPage()throws Exception{
		String id = request.getParameter("id");
		Customer customer = salesService.getCustomerInfo(id);
		List priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
		List natureList = getBaseSysCodeService().showSysCodeListByType("firm_nature");
		List promotionsortList = getBaseSysCodeService().showSysCodeListByType("promotionsort");
		List stateList = getBaseSysCodeService().showSysCodeListByType("state");
		List dayList = new ArrayList();
		for(int i=1;i<=31;i++){
			dayList.add(String.valueOf(i));
		}
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		List creditratingList = getBaseSysCodeService().showSysCodeListByType("creditrating");
		//获取t_base_sales_customer表的字段编辑权限
		Map colMap = getAccessColumn("t_base_sales_customer");
		request.setAttribute("showMap", colMap);
		request.setAttribute("creditratingList", creditratingList); 
		request.setAttribute("canceltypeList", canceltypeList); 
		request.setAttribute("dayList", dayList); 
		request.setAttribute("stateList", stateList);
		request.setAttribute("promotionsortList", promotionsortList);
		request.setAttribute("priceList", priceList);
		request.setAttribute("natureList", natureList);
		request.setAttribute("customer", customer);
		return SUCCESS;
	}
	
	/**
	 * 客户档案快捷新增
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 7, 2013
	 */
	@UserOperateLog(key="Customer",type=2,value="")
	public String addCustomerShortcut()throws Exception{
		SysUser sysUser = getSysUser();
		customer.setAdddeptid(sysUser.getDepartmentid());
		customer.setAdddeptname(sysUser.getDepartmentname());
		customer.setAdduserid(sysUser.getUserid());
		customer.setAddusername(sysUser.getName());
		if(StringUtils.isEmpty(customer.getState())){
			customer.setState("2");
		}
		boolean flag = salesService.addCustomerShortcut(customer);
		addLog("新增客户 编号:"+customer.getId(),flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 客户档案快捷修改
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 8, 2013
	 */
	@UserOperateLog(key="Customer",type=3,value="")
	public String editCustomerShortcut()throws Exception{
		SysUser sysUser = getSysUser();
		customer.setModifyuserid(sysUser.getUserid());
		customer.setModifyusername(sysUser.getName());
		if(!"1".equals(customer.getState()) && !"0".equals(customer.getState())){
			customer.setState("2");
		}
		//修改前客户档案
		Customer oldCustomer = salesService.getCustomerInfo(customer.getId());
		boolean flag = salesService.editCustomerShortcut(customer);
		if(flag && customer.getOldid().equals(customer.getId())){
			addEditCustomerTaskSchedule(customer,oldCustomer,1);
		}
		addLog("修改客户 编号:"+customer.getId(),flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 批量启用客户
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 8, 2013
	 */
	@UserOperateLog(key="Customer",type=0,value="")
	public String openMultiCustomer()throws Exception{
		String ids = request.getParameter("ids");
		int sucNum = 0,noHandleNum = 0,failNum = 0;
		String retids = null;
		Map map = new HashMap();
		if(StringUtils.isNotEmpty(ids)){
			String[] idArr = ids.split(",");
			for(String id : idArr){
				Customer customer = salesService.getCustomerDetail(id);
				if(null != customer){
					if(!"2".equals(customer.getState()) && !"0".equals(customer.getState())){
						noHandleNum++;
					}
					else{
						Customer customer2 = new Customer();
						SysUser sysUser = getSysUser();
						customer2.setOpenuserid(sysUser.getUserid());
						customer2.setOpenusername(sysUser.getName());
						customer2.setId(id);
						boolean flag = salesService.updateCustomerOpen(customer2);
						map.put("flag", flag);
						if(flag){
							sucNum++;
							if(StringUtils.isNotEmpty(retids)){
								retids += "," + id;
							}else{
								retids = id;
							}
						}
						else{
							failNum++;
						}
					}
				}
				else{
					noHandleNum++;
				}
			}
		}
		if(sucNum > 0){
			addLog("启用客户 编号:"+retids,true);
		}
		map.put("sucNum", sucNum);
		map.put("noHandleNum", noHandleNum);
		map.put("failNum", failNum);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 批量禁用客户
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 8, 2013
	 */
	@UserOperateLog(key="Customer",type=0,value="")
	public String closeMultiCustomer()throws Exception{
		String ids = request.getParameter("ids");
		int sucNum = 0,noHandleNum = 0,failNum = 0;
		String retids = null;
		Map map = new HashMap();
		if(StringUtils.isNotEmpty(ids)){
			String[] idArr = ids.split(",");
			for(String id : idArr){
				Customer customer = salesService.getCustomerInfo(id);
				if(null != customer){
					if(!"1".equals(customer.getState())){
						noHandleNum++;
					}
					else{
						SysUser sysUser = getSysUser();
						Customer cust = new Customer();
						cust.setCloseuserid(sysUser.getUserid());
						cust.setCloseusername(sysUser.getName());
						cust.setId(customer.getId());
						boolean flag = salesService.updateCustomerClose(cust);
						map.put("flag", flag);
						if(flag){
							sucNum++;
							if(StringUtils.isNotEmpty(retids)){
								retids += "," + id;
							}else{
								retids = id;
							}
						}
						else{
							failNum++;
						}
					}
				}
				else{
					noHandleNum++;
				}
			}
		}
		if(sucNum > 0){
			addLog("禁用客户 编号:"+retids,true);
		}
		map.put("sucNum", sucNum);
		map.put("noHandleNum", noHandleNum);
		map.put("failNum", failNum);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 批量删除客户
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 8, 2013
	 */
	@UserOperateLog(key="Customer",type=4,value="")
	public String deleteMultiCustomer()throws Exception{
		String ids = request.getParameter("ids");
		int sucNum = 0,noHandleNum = 0,failNum = 0,userNum = 0,lockNum = 0;
		Map map = new HashMap();
		String retids = null;
		if(StringUtils.isNotEmpty(ids)){
			String[] idArr = ids.split(",");
			for(String id : idArr){
				Customer customer = salesService.getCustomerDetail(id);
				if(null != customer){
					//判断数据是否被加锁,True已被加锁。Fasle未加锁。（网络互斥）
					if(!isLock("t_base_sales_customer", id)){
						if(!canTableDataDelete("t_base_sales_customer",id)){//true可以操作，false不可以操作
							userNum++;
						}
						else{
							boolean flag = salesService.deleteCustomer(id);
							map.put("flag", flag);
							if(flag){
								sucNum++;
								if(StringUtils.isNotEmpty(retids)){
									retids += "," + id;
								}else{
									retids = id;
								}
							}
							else{
								failNum++;
							}
						}
					}
					else{
						lockNum++;
					}
				}
				else{
					noHandleNum++;
				}
			}
		}
		if(sucNum > 0){
			addLog("删除客户 编号:"+retids,true);
		}
		map.put("sucNum", sucNum);
		map.put("noHandleNum", noHandleNum);
		map.put("failNum", failNum);
		map.put("userNum", userNum);
		map.put("lockNum", lockNum);
		addJSONObject(map);
		return SUCCESS;
	}
	/**客户档案结束*/
	
	/**
	 * 根据当前页page，每页显示条数rows获取合同商品列表数据
	 */
	public String getPriceListPage()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesService.getPriceListPage(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 显示客户档案批量修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 28, 2013
	 */
	public String customerMoreEditPage()throws Exception{
		String idStr = request.getParameter("idStr");
		String unInvNum = request.getParameter("unInvNum");
		List priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		List dayList = new ArrayList();
		for(int i=1;i<=31;i++){
			dayList.add(String.valueOf(i));
		}
		request.setAttribute("dayList", dayList);
		request.setAttribute("canceltypeList", canceltypeList); 
		request.setAttribute("idStr", idStr);
		request.setAttribute("unInvNum", unInvNum);
		request.setAttribute("priceList", priceList); //价格套
		return SUCCESS;
	}
	
	/**
	 * 批量修改客户档案
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 28, 2013
	 */
	@UserOperateLog(key="Customer",type=3,value="")
	public String editCustomerMore()throws Exception{
		String idStr = request.getParameter("idStr");
		int sucNum = 0,failNum = 0,unEditNum = 0,lockNum = 0;
		String retids = null;
		if(StringUtils.isNotEmpty(idStr)){
			String[] idArr = idStr.substring(0, idStr.lastIndexOf(",")).split(",");
			if(idArr.length != 0){
				int i = 0;
				for(String id : idArr){
					Customer copyCustomer = (Customer)CommonUtils.deepCopy(customer);
					copyCustomer.setId(id);
					copyCustomer.setOldid(id);
					Customer oldCustomer = salesService.getCustomerInfo(id);
					if(null != oldCustomer){
						copyCustomer.setPid(oldCustomer.getPid());
						if(StringUtils.isEmpty(copyCustomer.getSalesarea())){
							copyCustomer.setSalesarea(oldCustomer.getSalesarea());
							copyCustomer.setOldsalesarea(oldCustomer.getSalesarea());
						}
						if(StringUtils.isEmpty(copyCustomer.getSalesdeptid())){
							copyCustomer.setSalesdeptid(oldCustomer.getSalesdeptid());
							copyCustomer.setOldsalesdeptid(oldCustomer.getSalesdeptid());
						}
						if(StringUtils.isEmpty(copyCustomer.getSalesuserid())){
							copyCustomer.setSalesuserid(oldCustomer.getSalesuserid());
							copyCustomer.setOldsalesuserid(oldCustomer.getSalesuserid());
						}
						if(StringUtils.isEmpty(copyCustomer.getIndoorstaff())){
							copyCustomer.setIndoorstaff(oldCustomer.getIndoorstaff());
							copyCustomer.setOldindoorstaff(oldCustomer.getIndoorstaff());
						}
						if(StringUtils.isEmpty(copyCustomer.getCustomersort())){
							copyCustomer.setCustomersort(oldCustomer.getCustomersort());
							copyCustomer.setOldcustomersort(oldCustomer.getCustomersort());
						}
						if(StringUtils.isEmpty(copyCustomer.getPricesort())){
							copyCustomer.setPricesort(oldCustomer.getPricesort());
						}
						if(StringUtils.isEmpty(copyCustomer.getSettleday())){
							copyCustomer.setSettleday(oldCustomer.getSettleday());
						}
						if(StringUtils.isEmpty(copyCustomer.getSettletype())){
							copyCustomer.setSettletype(oldCustomer.getSettletype());
						}
					}
					List<CustomerAndSort> sortList = new ArrayList<CustomerAndSort>();
					boolean csflag = false;
					//默认分类-对应分类
					if(StringUtils.isNotEmpty(copyCustomer.getCustomersort()) && !copyCustomer.getCustomersort().equals(oldCustomer.getCustomersort())){
						sortList = salesService.getCustomerAndSortListByCustomer(id);
						if(sortList.size() != 0){
							for(CustomerAndSort customerAndSort : sortList){
								if("1".equals(customerAndSort.getDefaultsort())){
									customerAndSort.setSortid(copyCustomer.getCustomersort());
									CustomerSort customerSort = salesService.getCustomerSortDetail(copyCustomer.getCustomersort());
									if(null != customerSort){
										customerAndSort.setSortname(customerSort.getName());
									}
									csflag = true;
									break;
								}
							}
							if(!csflag){
								CustomerAndSort customerAndSort = new CustomerAndSort();
								customerAndSort.setCustomerid(id);
								customerAndSort.setDefaultsort("1");
								customerAndSort.setSortid(copyCustomer.getCustomersort());
								CustomerSort customerSort = salesService.getCustomerSortDetail(copyCustomer.getCustomersort());
								if(null != customerSort){
									customerAndSort.setSortname(customerSort.getName());
								}
								sortList.add(customerAndSort);
							}
						}
						else{
							CustomerAndSort customerAndSort = new CustomerAndSort();
							customerAndSort.setCustomerid(id);
							customerAndSort.setDefaultsort("1");
							customerAndSort.setSortid(copyCustomer.getCustomersort());
							CustomerSort customerSort = salesService.getCustomerSortDetail(copyCustomer.getCustomersort());
							if(null != customerSort){
								customerAndSort.setSortname(customerSort.getName());
							}
							sortList.add(customerAndSort);
						}
					}
					//修改前客户档案
					Map map = salesService.editCustomer(copyCustomer,sortList);
					if(map.get("lockFlag").equals(true)){
						if(map.get("unEditFlag").equals(true)){
							if(map.get("retFlag").equals(true)){
								sucNum++;
								i++;
                                addEditCustomerTaskSchedule(copyCustomer,oldCustomer,i);
								if(StringUtils.isNotEmpty(retids)){
									retids += "," + id;
								}else{
									retids = id;
								}
							}
							else{
								failNum++;
							}
						}
						else{
							unEditNum++;
						}
					}
					else{
						lockNum++;
					}
				}
			}
		}
		if(sucNum > 0){
			addLog("修改客户 编号:"+retids,true);
		}
		Map map = new HashMap();
		map.put("sucNum", sucNum);
		map.put("failNum", failNum);
		map.put("unEditNum", unEditNum);
		map.put("lockNum", lockNum);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 获取已买客户商品
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 10, 2013
	 */
	public String getGoodsListPage()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesService.getGoodsListPage(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 显示合同商品和客户商品页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 7, 2013
	 */
	public String showCustomerPriceAndGoodsPage()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 显示合同商品新增页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 7, 2013
	 */
	public String showCustomerPriceAndGoodsAddPage()throws Exception{
		String customerid = request.getParameter("customerid");
		String goodsStr = "";
		List<CustomerPrice> cplist = salesService.getCustoemrPriceListByCustomerid(customerid);
		if(cplist.size() != 0){
			for(CustomerPrice customerPrice : cplist){
				if(StringUtils.isEmpty(goodsStr)){
					goodsStr = customerPrice.getGoodsid();
				}
				else{
					goodsStr += "," + customerPrice.getGoodsid();
				}
			}
			request.setAttribute("goodsStr", goodsStr);
		}
		Customer customer = salesService.getCustomerInfo(customerid);
		if(null != customer){
			request.setAttribute("pricesort", customer.getPricesort());
			request.setAttribute("customername", customer.getName());
		}
		request.setAttribute("customerid", customerid);
		return SUCCESS;
	}
	
	/**
	 * 合同商品新增
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 7, 2013
	 */
	@UserOperateLog(key="CustomerPrice",type=2,value="")
	public String addCustomerPrice()throws Exception{
		boolean flag = salesService.addCustomerPrice(customerPrice);
		addLog("新增合同商品 客户编号:"+customerPrice.getCustomerid()+"商品编码:"+customerPrice.getGoodsid(),flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 合同商品复制
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 12, 2015
	 */
	@UserOperateLog(key="CustomerPrice",type=2,value="")
	public String doCopyCustomerPrice()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		boolean flag = salesService.copyCustomerPrice(map);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 判断是否已存在该合同商品
	 * @return true存在，false 不存在
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 12, 2015
	 */
	public String doCheckIsExistCustomerPrice()throws Exception{
		String goodsids = request.getParameter("goodsids");
		String customerids = request.getParameter("customerids");
		Map map = salesService.doCheckIsExistCustomerPrice(goodsids,customerids);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 显示合同商品修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 7, 2013
	 */
	public String showCustomerPriceAndGoodsEditPage()throws Exception{
		String customerid = request.getParameter("customerid");
		String id = request.getParameter("id");
		CustomerPrice customerPrice2 = salesService.getCustomerPriceInfo(id);
		if(null != customerPrice2){
			GoodsInfo goodsInfo = getBaseGoodsService().showGoodsInfo(customerPrice2.getGoodsid());
			if(null != goodsInfo){
				request.setAttribute("goodsname", goodsInfo.getName());
                request.setAttribute("boxnum", goodsInfo.getBoxnum());
			}
		}
		String goodsStr = "";
		List<CustomerPrice> cplist = salesService.getCustoemrPriceListByCustomerid(customerid);
		if(cplist.size() != 0){
			for(CustomerPrice customerPrice : cplist){
				if(!customerPrice2.getGoodsid().equals(customerPrice.getGoodsid())){
					if(StringUtils.isEmpty(goodsStr)){
						goodsStr = customerPrice.getGoodsid();
					}
					else{
						goodsStr += "," + customerPrice.getGoodsid();
					}
				}
			}
			request.setAttribute("goodsStr", goodsStr);
		}
		Customer customer = salesService.getCustomerInfo(customerid);
		if(null != customer){
			request.setAttribute("pricesort", customer.getPricesort());
			request.setAttribute("customername", customer.getName());
		}
		request.setAttribute("customerPrice", customerPrice2);
		request.setAttribute("customerid", customerid);
		return SUCCESS;
	}
	
	/**
	 * 显示合同商品复制页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 11, 2015
	 */
	public String showCustomerPriceAndGoodsCopyPage()throws Exception{
		String customerid = request.getParameter("customerid");
		request.setAttribute("customerid", customerid);
		return SUCCESS;
	}
	
	/**
	 * 合同商品修改
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 7, 2013
	 */
	@UserOperateLog(key="CustomerPrice",type=3,value="")
	public String updateCustomerPrice()throws Exception{
		boolean flag = salesService.updateCustomerPrice(customerPrice);
		addLog("修改合同商品 客户编号:"+customerPrice.getCustomerid()+"商品编码:"+customerPrice.getGoodsid(),flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 合同商品删除
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 7, 2013
	 */
	@UserOperateLog(key="CustomerPrice",type=4,value="")
	public String deleteCustomerPrices()throws Exception{
		String idstr = request.getParameter("idstr");
		String customerid = request.getParameter("customerid");
		boolean flag = salesService.deleteCustomerPrices(idstr);
		addLog("删除合同商品 客户编号:"+customerid+"商品编码:"+idstr,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}

	 /**
	  * 客户合同商品价格变化 1 含税合同价 2 未税合同价 3 合同箱价
	  * @author lin_xx
	  * @date 2017/2/7
	  */
	 public String getCustomerPriceChanger() throws Exception{
		 Map map = new HashedMap();
		 String price = request.getParameter("price");
		 String type = request.getParameter("type");
		 String taxrate = request.getParameter("taxrate");
		 String num = request.getParameter("boxnum");
		 BigDecimal boxnum = new BigDecimal(num);
		 BigDecimal rate = new BigDecimal(taxrate);
		 BigDecimal getPrice = new BigDecimal(price);
		 if("1".equals(type)){
			 BigDecimal noprice = getPrice.divide(rate,6,BigDecimal.ROUND_HALF_UP);
			 map.put("notaxprice",noprice);
			 map.put("taxprice",price);
			 BigDecimal ctcboxprice = getPrice.multiply(boxnum);
			 map.put("ctcboxprice",ctcboxprice);
		 }else if("2".equals(type)){
			 map.put("notaxprice",price);
			 BigDecimal taxprice = getPrice.multiply(rate);
			 map.put("taxprice",taxprice);
			 BigDecimal ctcboxprice = taxprice.multiply(boxnum);
			 map.put("ctcboxprice",ctcboxprice);
		 }else if("3".equals(type)){
			 map.put("ctcboxprice",price);
			 BigDecimal taxprice = getPrice.divide(boxnum,6,BigDecimal.ROUND_HALF_UP);
			 map.put("taxprice",taxprice);
			 BigDecimal noprice = taxprice.divide(rate,6,BigDecimal.ROUND_HALF_UP);
			 map.put("notaxprice",noprice);
		 }
		 addJSONObject(map);
		 return SUCCESS;
	 }

	
	/**
	 * 根据商品编码、默认价格套编码获取价格套价格
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 7, 2013
	 */
	public String getTaxPrice()throws Exception{
		String goodsid = request.getParameter("goodsid");
		String pricesort = request.getParameter("pricesort");
		Map map = new HashMap();
		Map map2 = getBaseGoodsService().getTaxPriceByGoodsidAndPriceCode(goodsid, pricesort);
		BigDecimal taxrate = new BigDecimal(0);
		GoodsInfo goodsInfo = getBaseGoodsService().showGoodsInfo(goodsid);
		if(null != goodsInfo){
            map.put("boxnum", goodsInfo.getBoxnum());
			TaxType taxType = getBaseFinanceService().getTaxTypeInfo(goodsInfo.getDefaulttaxtype());
			if(null != taxType){
				BigDecimal rate = (BigDecimal)taxType.getRate();
				taxrate = rate.divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1));
				map.put("taxrate", taxrate);
			}
		}
		if(null != map2){
			map.putAll(map2);
		}
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 合同商品导入
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 8, 2013
	 */
	@UserOperateLog(key="CustomerPrice",type=2,value="合同商品导入")
	public String importPriceCustomerData()throws Exception{
		Map retmap = new HashMap();
		try{
			String clazz = request.getParameter("clazz");
			String meth = request.getParameter("method");
			String module = request.getParameter("module");
			String pojo = request.getParameter("pojo");
			String repeatVal = "",closeVal = "",failStr = "";
			Object object = SpringContextUtils.getBean(clazz);
			Class entity = Class.forName("com.hd.agent." + module + ".model." +pojo);
			Method[] methods = object.getClass().getMethods();
			Method method = null;
			for(Method m : methods){
				if(m.getName().equals(meth)){
					method = m;
				}
			}
			List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
			List<String> paramList2 = new ArrayList<String>();
			for(String str : paramList){
				if("客户编码".equals(str)){
					paramList2.add("customerid");
				}
				else if("客户名称".equals(str)){
					paramList2.add("customername");
				}
				else if("商品助记符".equals(str)){
					paramList2.add("goodsspell");
				}
				else if("客户助记符".equals(str)){
					paramList2.add("shortcode");
				}
				else if("上级客户".equals(str)){
					paramList2.add("pcustomername");
				}
				else if("商品编码".equals(str)){
					paramList2.add("goodsid");
				}
				else if("商品名称".equals(str)){
					paramList2.add("goodsname");
				}
				else if("条形码".equals(str)){
					paramList2.add("barcode");
				}
				else if("店内码".equals(str)){
					paramList2.add("shopid");
				}
				else if("含税合同价格".equals(str)){
					paramList2.add("price");
				}
                else if("合同箱价".equals(str)){
                    paramList2.add("ctcboxprice");
                }
				else if("未税合同价格".equals(str)){
					paramList2.add("noprice");
				}
				else if("备注".equals(str)){
					paramList2.add("remark");
				}else {
					paramList2.add("null");
				}
			}
				List result = new ArrayList();
				List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
				if(list.size() != 0){
					Map detialMap = new HashMap();
					for(Map<String, Object> map4 : list){
						Object object2 = entity.newInstance();
						Field[] fields = entity.getDeclaredFields();
						//获取的导入数据格式转换
						DRCastTo(map4,fields);
						//BeanUtils.populate(object2, map4);
						PropertyUtils.copyProperties(object2, map4);
						result.add(object2);
					}
					if(result.size() != 0){
						retmap = excelService.insertSalesOrder(object, result, method);
					}
				}else{
					retmap.put("excelempty", true);
				}
		}
		catch (Exception e) {
			e.printStackTrace();
			retmap.put("error",true);
		}
		addJSONObject(retmap);
		return SUCCESS;
	}
	
	/**
	 * 合同商品导出
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 8, 2013
	 */
	@UserOperateLog(key="CustomerPrice",type=0,value="合同商品导出")
	public void exportPriceCustomerData()throws Exception{
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
		if(map.containsKey("customerid")){
			PageData pageData = salesService.showPriceCustomerData(pageMap);
			ExcelUtils.exportExcel(exportPriceCustomerDataFilter(pageData.getList()), title);
		}else{
			ExcelUtils.exportExcel(exportPriceCustomerDataFilter(new ArrayList<CustomerPrice>()), title);
		}
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(合同商品导出)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 8, 2013
	 */
	private List<Map<String, Object>> exportPriceCustomerDataFilter(List<CustomerPrice> list) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("customerid", "客户编码");
		firstMap.put("customername", "客户名称");
		firstMap.put("shortcode", "客户助记符");
		firstMap.put("pcustomername", "上级客户");
		firstMap.put("goodsid", "商品编码");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("goodsspell", "商品助记符");
		firstMap.put("barcode", "条形码");
		firstMap.put("shopid", "店内码");
		firstMap.put("price", "含税合同价格");
        firstMap.put("ctcboxprice", "合同箱价");
		firstMap.put("noprice", "未税合同价格");
		firstMap.put("remark", "备注");
		result.add(firstMap);
		if(list.size() != 0){
			for(CustomerPrice customerPrice : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(customerPrice);
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
	 * 获取客户列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 16, 2013
	 */
	public String getCustomerListData()throws Exception{
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		Map map = new HashMap();
		map.put("type", type);
		map.put("id", id);
		pageMap.setCondition(map);
		PageData pageData = salesService.getCustomerListData(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
		
	/**
	 * 显示客户分配业务员信息页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 6, 2014
	 */
	public String showAllotPSNCustomerPage()throws Exception{
		String employetype = request.getParameter("employetype");
		Map map4 = new HashMap();
		List<Map> paramlist = new LinkedList<Map>();
		String customerids = request.getParameter("idStr");
		if(StringUtils.isEmpty(employetype)){
			employetype = "3";
		}
		List<Map> deptList = getDeptListByOpertype(employetype);
		if(null != deptList && deptList.size() != 0){
			for(Map map2 : deptList){
				Map map3 = new HashMap();
				map3.put("deptid", map2.get("deptid"));
				map3.put("deptname", map2.get("deptname"));
				if(null != map2.get("deptid")){
					List<Map> bclist = new ArrayList<Map>();
        			if("3".equals(employetype)){
        				bclist = getBasePersonnelService().getPersonListByCustoemrids(customerids);
        			}else if("7".equals(employetype)){
        				bclist = getBasePersonnelService().getSupplierPersonListByCustoemrids(customerids);
        			}
					if(bclist.size() != 0){
						for(Map map : bclist){
							if(null != map.get("belongdeptid") && map.get("belongdeptid").equals(map2.get("deptid"))){
								if(null != map.get("personid")){
									map3.put("personid", map.get("personid"));
								}
							}
						}
					}
					if(StringUtils.isNotEmpty(customerids) && customerids.indexOf(",") == -1){
						request.setAttribute("type", "1");//单个客户分配业务员，显示默认业务员
					}else{
						request.setAttribute("type", "0");//多个客户分配业务员，不显示默认业务员
					}
				}
				paramlist.add(map3);
			}
		}
		request.setAttribute("paramlist", paramlist);
		request.setAttribute("customerids", customerids);
		request.setAttribute("employetype",employetype);
		return SUCCESS;
	}

	/**
	 * 分配业务员
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 6, 2014
	 */
	@UserOperateLog(key="Customer",type=0,value="")
	public String allotCustomerToPsn()throws Exception{
		Map paramMap = CommonUtils.changeMap(request.getParameterMap());
		String customerids = request.getParameter("customerids");
		String personids = request.getParameter("personids");
		String employetype = request.getParameter("employetype");
		Map map = new HashMap();
		String msg = null;
		if(StringUtils.isNotEmpty(customerids)){
			String succustomerids = "",unsuccustomerids = "";
            if(StringUtils.isNotEmpty(personids)){
                String[] personArr = personids.split(",");
				String personsql = "";
                for(String personid : personArr){
					if(StringUtils.isNotEmpty(personsql)){
						personsql += "," + personid;
					}else{
						personsql = personid;
					}
                }
				String sql = "";
				if("3".equals(employetype)){
					sql = " SELECT * from t_base_personnel_brand t where t.personid in ( "+personsql+" )";
				}else if("7".equals(employetype)){
					sql = " SELECT * from t_base_personnel_supplier_brand t where t.personid in ( "+personsql+" )";
				}
                //分配的业务员之间是否有重复的品牌，true没有，false有
                Map repeatmap = personnelService.checkPersonsIsRepeatBrandids(sql);
				if (repeatmap.get("flag").equals(true)){
					boolean flag2 = getBasePersonnelService().addOfEmployetypeToPsnBrandCustomer2(paramMap);
                    if(flag2){
						succustomerids = customerids;
					}else{
						unsuccustomerids = customerids;
					}
                    if(StringUtils.isNotEmpty(succustomerids)){
                        if(StringUtils.isEmpty(msg)){
                            msg = "客户"+succustomerids+"分配人员"+personids;
                        }else{
                            msg += "客户"+succustomerids+"分配人员"+personids;
                        }
                        msg = msg+"成功!";
                    }
                    if(StringUtils.isNotEmpty(unsuccustomerids)){
                        if(StringUtils.isEmpty(msg)){
                            msg = "客户"+unsuccustomerids+"分配人员"+personids;
                        }else{
                            msg += "客户"+unsuccustomerids+"分配人员"+personids;
                        }
                        msg = msg+"失败!";
                    }
                    if("3".equals(employetype)){
                        addLog("分配品牌业务员 编号:"+msg,true);
                    }else if("7".equals(employetype)){
                        addLog("分配厂家业务员 编号:"+msg,true);
                    }
                    map.put("msg", msg);
                }else{
                    String msg1 = (String)repeatmap.get("msg");
                    msg = "客户"+customerids+"分配人员"+personids+"失败!<br>"+msg1;
                    map.put("msg", msg);
                    map.put("flag",false);
                }
            }
			addJSONObject(map);
		}
		return SUCCESS;
	}

	/**
	 * 显示清除业务员页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-06-06
	 */
	public String showClearPSNCustomerPage()throws Exception{
		String customerids = request.getParameter("customerids");
		request.setAttribute("customerids",customerids);
		return SUCCESS;
	}

	/**
	 * 清除业务员
	 * @return
	 * @throws Exception
	 */
	@UserOperateLog(key="Customer",type=0,value="")
	public String doClearCustomerToPsn()throws Exception{
		String employetype = request.getParameter("employetype");
		String customerids = request.getParameter("customerids");
		boolean flag = personnelService.doClearCustomerToPsn(employetype,customerids);
		addJSONObject("flag",flag);
		String employetypename = "品牌业务员";
		if("3".equals(employetype)){
			employetypename = "品牌业务员";
		}else if("7".equals(employetype)){
			employetypename = "厂家业务员";
		}
		addLog("清除 客户编号:"+customerids+"对应"+employetypename,flag);
		return SUCCESS;
	}
	
	/**
	 * 添加客户修改定时器
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 3, 2014
	 */
	private void addEditCustomerTaskSchedule(Customer customer,Customer oldCustomer,int i)throws Exception{
        //获取是否执行客户历史数据系统参数
        String doEditCustomerTask = "1";
        SysParam sysParam = getBaseSysParamService().getSysParam("doEditCustomerTask");
        if(null != sysParam){
            doEditCustomerTask = sysParam.getPvalue();
        }
        if(null!=customer && null!=oldCustomer && customer.getOldid().equals(customer.getId()) && "1".equals(doEditCustomerTask)){
			if((null != customer.getCustomersort() && !customer.getCustomersort().equals(oldCustomer.getCustomersort())) ||
				(null != customer.getIndoorstaff() && !customer.getIndoorstaff().equals(oldCustomer.getIndoorstaff())) ||
				(null != customer.getSalesarea() && !customer.getSalesarea().equals(oldCustomer.getSalesarea())) ||
				(null != customer.getSalesdeptid() && !customer.getSalesdeptid().equals(oldCustomer.getSalesdeptid())) ||
				(null != customer.getSalesuserid() && !customer.getSalesuserid().equals(oldCustomer.getSalesuserid())) ||
				(null != customer.getPid() && !customer.getPid().equals(oldCustomer.getPid()))
			){
				String taskid = CommonUtils.getDataNumberSendsWithRand();
				Calendar nowTime = Calendar.getInstance();
				nowTime.add(Calendar.MINUTE, 1);
				nowTime.add(Calendar.SECOND, i);
				Date afterDate = (Date) nowTime.getTime();
				
				//把执行时间转成quartz表达式 适合单次执行
				String con = CommonUtils.getQuartzCronExpression(afterDate);
				
				Map dataMap = new HashMap();
				dataMap.put("customer", customer);
				taskScheduleService.addTaskScheduleAndStart(taskid,"客户档案修改后更新相关单据", "com.hd.agent.basefiles.job.CustomerChangeJob", "customer", con, "1",dataMap);
			}

		}
		//修改应收日期
		if( (null != customer.getSettleday() && !customer.getSettleday().equals(oldCustomer.getSettleday())) ||
					(null != customer.getSettletype() && !customer.getSettletype().equals(oldCustomer.getSettletype()))
				){
			IStorageSaleOutService storageSaleOutService = (IStorageSaleOutService)SpringContextUtils.getBean("storageSaleOutService");
			storageSaleOutService.updateCustomerSaleOutNoWriteoffDuefromdateByCustomerid(customer.getId());
		}
	}
	
	/*---------------------------客户档案精简版-----------------------------*/
	
	public String showCustomerSimplifyListPage()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 显示客户档案精简版页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 18, 2014
	 */
	public String showCustomerSimplifyPage()throws Exception{
		List list = getBaseFilesLevelService().showFilesLevelList("t_base_sales_customer");
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
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		request.setAttribute("type", type);
		request.setAttribute("id", id);
		String area = request.getParameter("area");
		String sort = request.getParameter("sort");
		request.setAttribute("area", area);
		request.setAttribute("sort", sort);
		return SUCCESS;
	}
	
	/**
	 * 显示客户档案精简版新增页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 18, 2014
	 */
	public String showCustomerSimplifyAddPage()throws Exception{
		String id = request.getParameter("id"); //当为空时添加的客户关系为一级目录，否则该编号为添加客户的父级
		Customer customer = salesService.getCustomerInfo(id);
		int len = 0;
		if(customer != null && StringUtils.isNotEmpty(customer.getId())){
			len = customer.getId().length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_sales_customer", len);
		List natureList = getBaseSysCodeService().showSysCodeListByType("firm_nature");
		List priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
		List dayList = new ArrayList();
		for(int i=1;i<=31;i++){
			dayList.add(String.valueOf(i));
		}
		List deptList = getDeptListByOperationType("4"); //销售部门
		List salesList = getPersListByOperationType("1");
		List tallyList = getPersListByOperationType("2");
		List promotionsortList = getBaseSysCodeService().showSysCodeListByType("promotionsort");
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		List creditratingList = getBaseSysCodeService().showSysCodeListByType("creditrating");
		String area = request.getParameter("area");
		String sort = request.getParameter("sort");
		request.setAttribute("area", area);
		request.setAttribute("sort", sort);
		request.setAttribute("canceltypeList", canceltypeList); 
		request.setAttribute("creditratingList", creditratingList); 
		request.setAttribute("deptList", deptList); 
		request.setAttribute("salesList", salesList); 
		request.setAttribute("tallyList", tallyList); 
		request.setAttribute("dayList", dayList); 
		request.setAttribute("natureList", natureList); //企业性质
		request.setAttribute("priceList", priceList); //价格套
		request.setAttribute("promotionsortList", promotionsortList);
		request.setAttribute("customer", customer); 
		request.setAttribute("len", nextLen); //级次对应长度
		Map colMap = getRequiredColumn("t_base_sales_customer");
		request.setAttribute("colMap", colMap);
		return SUCCESS;
	}
	
	/**
	 * 显示客户档案精简版复制页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 18, 2014
	 */
	public String showCustomerSimplifyCopyPage()throws Exception{
		String id = request.getParameter("id");
		Customer customer = salesService.getCustomerInfo(id);
		List priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
		List natureList = getBaseSysCodeService().showSysCodeListByType("firm_nature");
		List promotionsortList = getBaseSysCodeService().showSysCodeListByType("promotionsort");
		List dayList = new ArrayList();
		for(int i=1;i<=31;i++){
			dayList.add(String.valueOf(i));
		}
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		int len = 0;
		if(customer != null && StringUtils.isNotEmpty(customer.getId())){
			len = customer.getId().length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_sales_customer", len);
		List creditratingList = getBaseSysCodeService().showSysCodeListByType("creditrating");
		//结算方式
		if(StringUtils.isNotEmpty(customer.getSettletype())){
			Settlement settlement = getBaseFinanceService().getSettlemetDetail(customer.getSettletype());
			if(null != settlement){
				request.setAttribute("monthorday", settlement.getType());
			}
		}
		request.setAttribute("creditratingList", creditratingList); 
		request.setAttribute("canceltypeList", canceltypeList); 
		request.setAttribute("dayList", dayList); 
		request.setAttribute("promotionsortList", promotionsortList);
		request.setAttribute("priceList", priceList);
		request.setAttribute("natureList", natureList);
		request.setAttribute("len", nextLen);
		request.setAttribute("customer", customer);
		Map colMap = getRequiredColumn("t_base_sales_customer");
		request.setAttribute("colMap", colMap);
		return SUCCESS;
	}
	
	/**
	 * 显示客户档案精简版修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 18, 2014
	 */
	public String showCustomerSimplifyEditPage()throws Exception{
		String id = request.getParameter("id");
		Customer customer = salesService.getCustomerInfo(id);
		//获取t_base_sales_customer表的字段编辑权限
		Map mapCols =  getBaseFilesEditWithAuthority("t_base_sales_customer", customer);
		if(!lockData("t_base_sales_customer", id)){
			request.setAttribute("lock", true);
			request.setAttribute("editMap", null);
			request.setAttribute("id", id);
			return SUCCESS;
		}
		request.setAttribute("editMap", mapCols);
		List priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
		List natureList = getBaseSysCodeService().showSysCodeListByType("firm_nature");
		List promotionsortList = getBaseSysCodeService().showSysCodeListByType("promotionsort");
		List stateList = getBaseSysCodeService().showSysCodeListByType("state");
		List dayList = new ArrayList();
		for(int i=1;i<=31;i++){
			dayList.add(String.valueOf(i));
		}
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		List creditratingList = getBaseSysCodeService().showSysCodeListByType("creditrating");
		//结算方式
		if(StringUtils.isNotEmpty(customer.getSettletype())){
			Settlement settlement = getBaseFinanceService().getSettlemetDetail(customer.getSettletype());
			if(null != settlement){
				request.setAttribute("monthorday", settlement.getType());
			}
		}
		request.setAttribute("editFlag", canTableDataDelete("t_base_sales_customer", id));
		request.setAttribute("creditratingList", creditratingList); 
		request.setAttribute("canceltypeList", canceltypeList); 
		request.setAttribute("dayList", dayList); 
		request.setAttribute("stateList", stateList);
		request.setAttribute("promotionsortList", promotionsortList);
		request.setAttribute("priceList", priceList);
		request.setAttribute("natureList", natureList);
		request.setAttribute("customer", customer);
		Map colMap = getRequiredColumn("t_base_sales_customer");
		request.setAttribute("colMap", colMap);
		return SUCCESS;
	}
	
	/**
	 * 显示客户档案精简版查看页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 18, 2014
	 */
	public String showCustomerSimplifyViewPage()throws Exception{
		String id = request.getParameter("id");
		Customer customer = salesService.getCustomerInfo(id);
		List priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
		List natureList = getBaseSysCodeService().showSysCodeListByType("firm_nature");
		List promotionsortList = getBaseSysCodeService().showSysCodeListByType("promotionsort");
		List stateList = getBaseSysCodeService().showSysCodeListByType("state");
		List dayList = new ArrayList();
		for(int i=1;i<=31;i++){
			dayList.add(String.valueOf(i));
		}
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		List creditratingList = getBaseSysCodeService().showSysCodeListByType("creditrating");
		//获取t_base_sales_customer表的字段编辑权限
		Map colMap = getAccessColumn("t_base_sales_customer");
		request.setAttribute("showMap", colMap);
		request.setAttribute("creditratingList", creditratingList); 
		request.setAttribute("canceltypeList", canceltypeList); 
		request.setAttribute("dayList", dayList); 
		request.setAttribute("stateList", stateList);
		request.setAttribute("promotionsortList", promotionsortList);
		request.setAttribute("priceList", priceList);
		request.setAttribute("natureList", natureList);
		request.setAttribute("customer", customer);
		Map colMap1 = getRequiredColumn("t_base_sales_customer");
		request.setAttribute("colMap", colMap1);
		return SUCCESS;
	}
	
	/**
	 * 显示客户档案精简版批量修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 19, 2014
	 */
	public String showCustomerSimplifyMoreEditPage()throws Exception{
		String idStr = request.getParameter("idStr");
		String unInvNum = request.getParameter("unInvNum");
		List priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		List dayList = new ArrayList();
		for(int i=1;i<=31;i++){
			dayList.add(String.valueOf(i));
		}
		request.setAttribute("dayList", dayList);
		request.setAttribute("canceltypeList", canceltypeList); 
		request.setAttribute("idStr", idStr);
		request.setAttribute("unInvNum", unInvNum);
		request.setAttribute("priceList", priceList); //价格套
		return SUCCESS;
	}
	
	/**
	 * 导出客户档案精简版
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 19, 2014
	 */
	public void exportCustomerSimplifyListData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
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
		firstMap.put("id", "编码");
		firstMap.put("name", "客户名称");
		firstMap.put("pname", "上级客户");
		firstMap.put("islastname", "是否总店");
        firstMap.put("statename", "状态");
		firstMap.put("shortcode", "助记符");
		firstMap.put("shortname", "简称");
		firstMap.put("shopno", "店号");
		firstMap.put("taxno", "税号");
		firstMap.put("bank", "开户银行");
		firstMap.put("cardno", "开户账号");
		firstMap.put("caraccount", "开户名");
		firstMap.put("fund", "注册资金");
		firstMap.put("storearea", "门店面积m^2");
		firstMap.put("contact", "联系人");
		firstMap.put("mobile", "联系人电话");
		firstMap.put("payeename", "收款人");
		firstMap.put("faxno", "传真");
		firstMap.put("address", "详细地址");
		firstMap.put("settletypename", "结算方式");
		firstMap.put("settleday", "结算日");
		firstMap.put("iscashname", "现款");
		firstMap.put("islongtermname", "账期");
		firstMap.put("credit", "信用额度");
		firstMap.put("creditratingname", "信用等级");
		firstMap.put("canceltypename", "核销方式");
		firstMap.put("pricesortname", "价格套");
		firstMap.put("promotionsortname", "促销分类");
		firstMap.put("salesareaname", "所属区域");
		firstMap.put("customersortname", "所属分类");
		firstMap.put("salesdeptname", "默认销售部门");
		firstMap.put("salesusername", "客户业务员");
		firstMap.put("tallyusername", "默认理货员");
		firstMap.put("indoorstaffname", "默认内勤");
		firstMap.put("payeename", "收款人");
		firstMap.put("remark", "备注");
		result.add(firstMap);
		
		List<Customer> list = excelService.getCustomerList(pageMap);
		for(Customer customer : list){
			Map<String, Object> retMap = new LinkedHashMap<String, Object>();
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2 = PropertyUtils.describe(customer);
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
	 * 导入客户档案精简版
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 19, 2014
	 */
	@UserOperateLog(key="Customer",type=2,value="客户档案导入")
	public String importCustomerSimplifyListData()throws Exception{
		Map<String,Object> retMap = new HashMap<String,Object>();
		try{
			String clazz = "salesService",meth = "addShortcutCustomerExcel",module = "basefiles",pojo = "Customer";
			Object object2 = SpringContextUtils.getBean(clazz);
			Class entity = Class.forName("com.hd.agent." + module + ".model." +pojo);
			Method[] methods = object2.getClass().getMethods();
			Method method = null;
			for(Method m : methods){
				if(m.getName().equals(meth)){
					method = m;
				}
			}
			
			List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
			List<String> paramList2 = new ArrayList<String>();
			for(String str : paramList){
				if("编码".equals(str)){
					paramList2.add("id");
				}
				else if("客户名称".equals(str)){
					paramList2.add("name");
				}
				else if("上级客户".equals(str)){
					paramList2.add("pname");
				}
				else if("是否总店".equals(str)){
					paramList2.add("islastname");
				}
				else if("助记符".equals(str)){
					paramList2.add("shortcode");
				}
				else if("简称".equals(str)){
					paramList2.add("shortname");
				}
				else if("店号".equals(str)){
					paramList2.add("shopno");
				}
				else if("税号".equals(str)){
					paramList2.add("taxno");
				}
				else if("开户银行".equals(str)){
					paramList2.add("bank");
				}
				else if("开户账号".equals(str)){
					paramList2.add("cardno");
				}
				else if("开户名".equals(str)){
					paramList2.add("caraccount");
				}
				else if("注册资金".equals(str)){
					paramList2.add("fund");
				}
				else if("门店面积m^2".equals(str)){
					paramList2.add("storearea");
				}
				else if("联系人".equals(str)){
					paramList2.add("contact");
				}
				else if("联系人电话".equals(str)){
					paramList2.add("mobile");
				}
				else if("收款人".equals(str)){
					paramList2.add("payeename");
				}
				else if("传真".equals(str)){
					paramList2.add("faxno");
				}
				else if("详细地址".equals(str)){
					paramList2.add("address");
				}
				else if("结算方式".equals(str)){
					paramList2.add("settletypename");
				}
				else if("结算日".equals(str)){
					paramList2.add("settleday");
				}
				else if("现款".equals(str)){
					paramList2.add("iscashname");
				}
				else if("账期".equals(str)){
					paramList2.add("islongtermname");
				}
				else if("信用额度".equals(str)){
					paramList2.add("credit");
				}
				else if("信用等级".equals(str)){
					paramList2.add("creditratingname");
				}
				else if("核销方式".equals(str)){
					paramList2.add("canceltypename");
				}
				else if("价格套".equals(str)){
					paramList2.add("pricesortname");
				}
				else if("促销分类".equals(str)){
					paramList2.add("promotionsortname");
				}
				else if("所属区域".equals(str)){
					paramList2.add("salesareaname");
				}
				else if("所属分类".equals(str)){
					paramList2.add("customersortname");			
				}
				else if("默认销售部门".equals(str)){
					paramList2.add("salesdeptname");			
				}
				else if("客户业务员".equals(str)){
					paramList2.add("salesusername");			
				}
				else if("默认理货员".equals(str)){
					paramList2.add("tallyusername");
				}
				else if("默认内勤".equals(str)){
					paramList2.add("indoorstaffname");
				}
				else if("收款人".equals(str)){
					paramList2.add("payeename");
				}
				else if("备注".equals(str)){
					paramList2.add("remark");
				}
				else{
					paramList2.add("null");
				}
			}
			
			if(paramList.size() == paramList2.size()){
				List result = new ArrayList();
				List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
                List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
				if(list.size() != 0){
					Map detialMap = new HashMap();
					for(Map<String, Object> map4 : list){
						Object object = entity.newInstance();
						Field[] fields = entity.getDeclaredFields();
                        try{
                            //获取的导入数据格式转换
                            DRCastTo(map4,fields);
                            //BeanUtils.populate(object, map4);
                            PropertyUtils.copyProperties(object, map4);
                            result.add(object);

                        }catch (Exception e){
                            errorList.add(map4);
                        }
					}
					if(result.size() != 0){
						retMap = excelService.insertSalesOrder(object2, result, method);
					}
                    if(errorList.size() > 0){
                        String fileid=createErrorFile(list);
                        retMap.put("msg","导入失败"+errorList.size()+"条");
                        retMap.put("errorid",fileid);
                    }
				}else{
					retMap.put("excelempty", true);
				}
			}
			else{
				retMap.put("versionerror", true);
			}
		}catch (Exception e) {
			e.printStackTrace();
			retMap.put("error", true);
		}
		addJSONObject(retMap);
		return SUCCESS;
	}

    public String createErrorFile(List<Map<String, Object>> errorList) throws  Exception{

        //模板文件路径
        String tempFilePath = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/Customer.xls");

        List<String> dataListCell = new ArrayList<String>();

        dataListCell = adddataListCell(dataListCell);

        List<Map<String,Object>> dataList = new  ArrayList<Map<String,Object>>();

        //商品模板里的字段和传过来的商品信息进行匹配
        for(Map<String, Object> map4 : errorList){
            map4.put("cid",map4.get("id"));
            map4.put("cname",map4.get("name"));
            map4.put("settletype",map4.get("settletypename"));
            map4.put("iscash",map4.get("iscashname"));
            map4.put("islongterm",map4.get("islongtermname"));
            map4.put("creditrating",map4.get("creditratingname"));
            map4.put("canceltype",map4.get("canceltypename"));
            map4.put("pricesort",map4.get("pricesortname"));
            map4.put("promotionsort",map4.get("promotionsortname"));
            map4.put("isstoragelocation",map4.get("isstoragelocationname"));
            map4.put("salesarea",map4.get("salesareaname"));
            map4.put("customersort",map4.get("customersortname"));
            map4.put("salesdept",map4.get("salesdeptname"));
            map4.put("salesuser",map4.get("salesusername"));
            map4.put("tallyuser",map4.get("tallyusername"));
            map4.put("indoorstaff",map4.get("indoorstaffname"));
            dataList.add(map4);
        }

		ExcelFileUtils handle = new ExcelFileUtils();
        handle.writeListData(tempFilePath, dataListCell, dataList, 0);

        SysUser sysUser=getSysUser();
        String filepath = OfficeUtils.getFilepath();//获取临时文件总目录
        String subPath = CommonUtils.getYearMonthDirPath();	//年月路径 格式yyyy/MM
        String path = filepath +"/errorimportfile/" + subPath;//创建该临时文件目录
		path=path.replaceAll("\\\\","/");
        File file = new File(path);

        if(!file .exists()  && !file .isDirectory()){
            file.mkdirs();
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss") ;
        Random rand = new Random();
        String randString = "" + rand.nextInt(100000);
        String fileName = dateFormat.format(new Date()) + randString;
        fileName= sysUser.getUserid()+fileName+ ".xls";

        File errorFile = new File(path, fileName);

        if(!errorFile.exists()){
            errorFile.createNewFile();
        }
        OutputStream os = new FileOutputStream(errorFile);

        //写到输出流并关闭资源
        handle.writeAndClose(tempFilePath, os);

        os.flush();
        os.close();

        handle.readClose(tempFilePath);


        String fullPath = "upload/errorimportfile/"+ subPath + "/" + fileName;

        AttachFile attachFile = new AttachFile();
        attachFile.setExt(".xls");
        attachFile.setFilename(fileName);
        attachFile.setFullpath(fullPath);
        attachFile.setOldfilename(fileName);
        //将临时文件信息插入数据库
        attachFileService.addAttachFile(attachFile);

        String id = "";

        if(null!=attachFile){
            id=attachFile.getId();
        }
        return id;
    }
	
	/**
	 * 显示合同商品批量修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 20, 2014
	 */
	public String showCustomerPriceAndGoodsMoreEditPage()throws Exception{
		String customerids = request.getParameter("customerids");
		String goodsid = request.getParameter("goodsid");
		GoodsInfo goodsInfo = getBaseGoodsService().showGoodsInfo(goodsid);
		//税率
		BigDecimal taxrate = BigDecimal.ZERO;
		TaxType taxType = getBaseFinanceService().getTaxTypeInfo(goodsInfo.getDefaulttaxtype());
		if(null != taxType){
			taxrate = taxType.getRate().divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP).add(new BigDecimal("1"));
		}
		request.setAttribute("taxrate", taxrate);
		request.setAttribute("goodsInfo", goodsInfo);
		request.setAttribute("customerids", customerids);
		request.setAttribute("goodsid", goodsid);
		return SUCCESS;
	}
	
	/**
	 * 批量修改合同商品
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 20, 2014
	 */
	@UserOperateLog(key="CustomerPrice",type=3,value="")
	public String editCustomerPriceAndGoodsMore()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		boolean flag = salesService.updateMoreCustomerPrice(map,customerPrice);
		map.put("flag", flag);
		addJSONObject(map);
		addLog("批量修改合同商品 客户编号:"+(String)map.get("customerids")+"商品编码:"+(String)map.get("goodsid"),flag);
		return SUCCESS;
	}
	
	/**
	 * 保存合同商品
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 29, 2014
	 */
	@UserOperateLog(key="CustomerPrice",type=3,value="")
	public String saveCustomerPrice()throws Exception{
		String rowsjsonstr = request.getParameter("rowsjsonstr");
		boolean retflag = true;
		String sucgoodsids = "",unsucgoodsids = "",customerid = "";
		if(StringUtils.isNotEmpty(rowsjsonstr)){
			JSONArray array = JSONArray.fromObject(rowsjsonstr);
			List<CustomerPrice> list = array.toList(array, CustomerPrice.class);
			customerid = list.get(0).getCustomerid();
			for(CustomerPrice customerPrice : list){
				boolean flag = salesService.updateCustomerPrice(customerPrice);
				if(flag){
					if(StringUtils.isNotEmpty(sucgoodsids)){
						sucgoodsids += "," + customerPrice.getGoodsid();
					}else{
						sucgoodsids = customerPrice.getGoodsid();
					}
				}else{
					if(StringUtils.isNotEmpty(unsucgoodsids)){
						unsucgoodsids += "," + customerPrice.getGoodsid();
					}else{
						unsucgoodsids = customerPrice.getGoodsid();
					}
				}
				retflag = retflag & flag;
			}
			if(StringUtils.isNotEmpty(sucgoodsids)){
				addLog("修改合同商品 客户编号:"+customerid+"商品编码:"+sucgoodsids,true);
			}else{
				addLog("修改合同商品 客户编号:"+customerid+"商品编码:"+unsucgoodsids,false);
			}
		}
		addJSONObject("flag", retflag);
		return SUCCESS;
	}
	/**
	 * 显示客户品牌价格套列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月9日
	 */
	public String showCustomerBrandPricesortListPage() throws Exception{
		return "success";
	}
	/**
	 * 显示客户品牌价格套添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月9日
	 */
	public String showCustomerBrandPricesortAddPage() throws Exception{
		return "success";
	}
	/**
	 * 添加客户品牌对应价格套
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月9日
	 */
	@UserOperateLog(key="CustomerBrandPricesort",type=2)
	public String addCustomerBrandPricesort() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		boolean flag  = salesService.addCustomerBrandPricesort(map);
		addJSONObject("flag", flag);
		addLog("添加客户品牌对应价格套  "+map.toString(), flag);
		return "success";
	}
	/**
	 * 获取客户品牌价格套列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月9日
	 */
	public String showCustomerBrandPricesort() throws Exception{
		String customerid = request.getParameter("customerid");
		List list = salesService.showCustomerBrandPricesort(customerid);
		addJSONArray(list);
		return "success";
	}
	
	/**
	 * 修改品牌价格套
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 12, 2015
	 */
	@UserOperateLog(key="CustomerBrandPricesort",type=3,value="")
	public String editCustomerBrandPricesort()throws Exception{
		String rowsjsonstr = request.getParameter("rowsjsonstr");
		String sucbrands = "",unsucbrands = "",customerid = "";
		int sucnum = 0,unsucnum = 0;
		if(StringUtils.isNotEmpty(rowsjsonstr)){
			JSONArray array = JSONArray.fromObject(rowsjsonstr);
			List<CustomerBrandPricesort> list = array.toList(array, CustomerBrandPricesort.class);
			customerid = list.get(0).getCustomerid();
			for(CustomerBrandPricesort brandPricesort : list){
				boolean flag = salesService.editCustomerBrandPricesort(brandPricesort);
				if(flag){
					sucnum++;
					if(StringUtils.isNotEmpty(sucbrands)){
						sucbrands += "," + brandPricesort.getBrandid();
					}else{
						sucbrands = brandPricesort.getBrandid();
					}
				}else{
					unsucnum++;
					if(StringUtils.isNotEmpty(unsucbrands)){
						unsucbrands += "," + brandPricesort.getBrandid();
					}else{
						unsucbrands = brandPricesort.getBrandid();
					}
				}
			}
			if(StringUtils.isNotEmpty(sucbrands)){
				addLog("修改品牌价格套 客户编号:"+customerid+"品牌编码:"+sucbrands,true);
			}else{
				addLog("修改品牌价格套 客户编号:"+customerid+"品牌编码:"+unsucbrands,false);
			}
		}
		
		String msg = "修改品牌价格套 客户编码："+customerid+"<br>成功"+sucnum+"条记录;<br>失败"+unsucnum+"条记录;";
		addJSONObject("msg", msg);
		return SUCCESS;
	}
	
	/**
	 * 删除品牌价格套
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 12, 2015
	 */
	@UserOperateLog(key="CustomerBrandPricesort",type=4,value="")
	public String deleteCustomerBrandPricesorts()throws Exception{
		String idstr = request.getParameter("idstr");
		String customerid = request.getParameter("customerid");
		Map map = salesService.deleteCustomerBrandPricesorts(idstr);
		Integer sucnum = (Integer)map.get("sucnum");
		String sucbrands = (String)map.get("sucbrands");
		Integer unsucnum = (Integer)map.get("unsucnum");
		String unsucbrands = (String)map.get("unsucbrands");
		String msg = "";
		if(sucnum > 0){
			msg = "成功"+sucnum+"条记录;<br>删除品牌价格套 客户编号:"+customerid+"<br>成功品牌编码:"+sucbrands;
			addLog("删除品牌价格套 客户编号:"+customerid+"品牌编码:"+sucbrands,true);
		}
		if(unsucnum > 0){
			msg += "失败"+unsucnum+"条记录;<br>删除品牌价格套 客户编号:"+customerid+"<br>失败品牌编码:"+unsucbrands;
			addLog("删除品牌价格套 客户编号:"+customerid+"品牌编码:"+unsucbrands,false);
		}
		addJSONObject("msg", msg);
		return SUCCESS;
	}
	
	//显示部门选择页面
	public String showDeptPage(){
		return SUCCESS;
	}
	/**
	 * 获取销售（采购）部门id 及部门名称
	 * @return
	 * @throws Exception
	 * @author lin_xx 
	 * @date 2015年1月2日
	 */
	public String getDeptList() throws Exception{	
		Map map = CommonUtils.changeMap(request.getParameterMap());				
		pageMap.setCondition(map);		
		PageData pageData = salesService.getSalesDepartmentSelectListData(pageMap);				
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 为客户合同商品获取客户列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 12, 2015
	 */
	public String getCustomerListForCustomerprice()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesService.getCustomerListForCustomerprice(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}

    /**
     * 模板导出
     * @return
     * @throws Exception
     * @author lin_xx
     * @date July 6, 2015
     */
    public String getCustomerExportMod() throws  Exception{

        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        String title = "";
        if(map.containsKey("excelTitle")){
            title = map.get("excelTitle").toString();
        }
        else{
            title = "list";
        }
        if(org.apache.commons.lang.StringUtils.isEmpty(title)){
            title = "list";
        }
        //模板文件路径
        String tempFilePath = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/Customer.xls");
        String filename = title+".xls";

        String idarrs = request.getParameter("idarrs");
        String[] idlist = idarrs.split(",");

        List<String> dataListCell = new ArrayList<String>();

        dataListCell = adddataListCell(dataListCell);

        List<Map<String,Object>> dataList = new  ArrayList<Map<String,Object>>();
        List<Customer> customerInfos = salesService.getUpLoadCustomerMod(idlist);
        for(int i = 0 ;i < customerInfos.size() ;i ++){
            Map<String,Object> result = new HashMap<String, Object>();
            result.put("cid",customerInfos.get(i).getId());
            result.put("cname",customerInfos.get(i).getName());
            result.put("pname",customerInfos.get(i).getPname());
            result.put("islast",customerInfos.get(i).getIslastname());
            result.put("shortcode",customerInfos.get(i).getShortcode());
            result.put("shortname",customerInfos.get(i).getShortname());
            result.put("shopno",customerInfos.get(i).getShopno());
            result.put("taxno",customerInfos.get(i).getTaxno());
            result.put("bank",customerInfos.get(i).getBank());
            result.put("cardno",customerInfos.get(i).getCardno());
            result.put("caraccount",customerInfos.get(i).getCaraccount());
            result.put("fund",customerInfos.get(i).getFund());
            result.put("storearea",customerInfos.get(i).getStorearea());
            result.put("faxno",customerInfos.get(i).getFaxno());
            result.put("contact",customerInfos.get(i).getContact());
            result.put("mobile",customerInfos.get(i).getMobile());
            result.put("payeename",customerInfos.get(i).getPayeename());
            result.put("address",customerInfos.get(i).getAddress());
            result.put("settletype",customerInfos.get(i).getSettletypename());
            result.put("settleday",customerInfos.get(i).getSettleday());
            result.put("iscash",customerInfos.get(i).getIscashname());
            result.put("islongterm",customerInfos.get(i).getIslongtermname());
            result.put("credit",customerInfos.get(i).getCredit());
            result.put("creditrating",customerInfos.get(i).getCreditratingname());
            result.put("canceltype",customerInfos.get(i).getCanceltypename());
            result.put("pricesort",customerInfos.get(i).getPricesortname());
            result.put("promotionsort",customerInfos.get(i).getPromotionsortname());
            result.put("salesarea",customerInfos.get(i).getSalesareaname());
            result.put("customersort",customerInfos.get(i).getCustomersortname());
            result.put("salesdept",customerInfos.get(i).getSalesdeptname());
            result.put("salesuser",customerInfos.get(i).getSalesusername());
            result.put("tallyuser",customerInfos.get(i).getTallyusername());
            result.put("indoorstaff",customerInfos.get(i).getIndoorstaffname());
            result.put("remark",customerInfos.get(i).getRemark());
            //result.put("statename",customerInfos.get(i).getStatename());

            dataList.add(result);
        }

		ExcelFileUtils handle = new ExcelFileUtils();
        handle.writeListData(tempFilePath, dataListCell, dataList, 0);

        //文件导出路径
        String path = ServletActionContext.getServletContext().getRealPath("common");
        File file = new File(path, filename);
        if(!file.exists()){
            file.createNewFile();
        }
        OutputStream os = new FileOutputStream(file);

        //写到输出流并关闭资源
        handle.writeAndClose(tempFilePath, os);

        os.flush();
        os.close();

        handle.readClose(tempFilePath);
        //下载已经导出的文件到客户端
        ExcelUtils.downloadExcel(path, filename);


        return SUCCESS;
    }

    public List<String> adddataListCell(List<String> dataListCell){

        dataListCell.add("cid");
        dataListCell.add("cname");
        dataListCell.add("pname");
        dataListCell.add("islast");
        dataListCell.add("shortcode");
        dataListCell.add("shortname");
        dataListCell.add("shopno");
        dataListCell.add("taxno");
        dataListCell.add("bank");
        dataListCell.add("cardno");
        dataListCell.add("caraccount");
        dataListCell.add("fund");
        dataListCell.add("storearea");
        dataListCell.add("faxno");
        dataListCell.add("contact");
        dataListCell.add("mobile");
        dataListCell.add("payeename");
        dataListCell.add("address");
        dataListCell.add("settletype");
        dataListCell.add("settleday");
        dataListCell.add("iscash");
        dataListCell.add("islongterm");
        dataListCell.add("credit");
        dataListCell.add("creditrating");
        dataListCell.add("canceltype");
        dataListCell.add("pricesort");
        dataListCell.add("promotionsort");
        dataListCell.add("salesarea");
        dataListCell.add("customersort");
        dataListCell.add("salesdept");
        dataListCell.add("salesuser");
        dataListCell.add("tallyuser");
        dataListCell.add("indoorstaff");
        dataListCell.add("remark");

        return  dataListCell ;
    }

	/**
	 * 显示客户品牌结算方式列表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-29
	 */
	public String showCustomerBrandSettletypeListPage()throws Exception{
		List dayList = new ArrayList();
		for(int i=1;i<=31;i++){
			Map map = null;
			map = new HashMap();
			map.put("value",String.valueOf(i));
			map.put("text",String.valueOf(i));
			dayList.add(map);
		}
		String daylistjsonstr = JSONUtils.listToJsonStr(dayList);
		request.setAttribute("daylistjsonstr",daylistjsonstr);
		return SUCCESS;
	}

	/**
	 * 根据客户编码获取对应品牌结算方式
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-29
	 */
	public String showCustomerBrandSettletypeList()throws Exception{
		String customerid = request.getParameter("customerid");
		List list = salesService.getCustomerBrandSettletypeList(customerid);
		addJSONArray(list);
		return SUCCESS;
	}

	/**
	 * 显示客户品牌结算方式添加页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-29
	 */
	public String showCustomerBrandSettletypeAddPage() throws Exception{
		return "success";
	}

	/**
	 * 新增客户品牌结算方式
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-30
	 */
	@UserOperateLog(key="CustomerBrandSettletype",type=2,value="")
	public String addCustomerBrandSettletype()throws Exception{
		boolean flag  = salesService.addCustomerBrandSettletype(customerBrandSettletype);
		addJSONObject("flag", flag);
		addLog("添加客户品牌结算方式 "+customerBrandSettletype.getCustomerid(), flag);
		return SUCCESS;
	}

	/**
	 * 修改客户品牌结算方式
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-30
	 */
	@UserOperateLog(key="CustomerBrandSettletype",type=3,value="")
	public String editCustomerBrandSettletype()throws Exception{
		String rowsjsonstr = request.getParameter("rowsjsonstr");
		String sucbrands = "",unsucbrands = "",customerid = "";
		int sucnum = 0,unsucnum = 0;
		if(StringUtils.isNotEmpty(rowsjsonstr)){
			JSONArray array = JSONArray.fromObject(rowsjsonstr);
			List<CustomerBrandSettletype> list = array.toList(array, CustomerBrandSettletype.class);
			customerid = list.get(0).getCustomerid();
			for(CustomerBrandSettletype customerBrandSettletype : list){
				boolean flag = salesService.editCustomerBrandSettletype(customerBrandSettletype);
				if(flag){
					sucnum++;
					if(StringUtils.isNotEmpty(sucbrands)){
						sucbrands += "," + customerBrandSettletype.getBrandid();
					}else{
						sucbrands = customerBrandSettletype.getBrandid();
					}
				}else{
					unsucnum++;
					if(StringUtils.isNotEmpty(unsucbrands)){
						unsucbrands += "," + customerBrandSettletype.getBrandid();
					}else{
						unsucbrands = customerBrandSettletype.getBrandid();
					}
				}
			}
			if(StringUtils.isNotEmpty(sucbrands)){
				addLog("修改品牌结算方式 客户编号:"+customerid+"品牌编码:"+sucbrands,true);
			}else{
				addLog("修改品牌结算方式 客户编号:"+customerid+"品牌编码:"+unsucbrands,false);
			}
		}

		String msg = "修改品牌结算方式 客户编码："+customerid+"<br>成功"+sucnum+"条记录;<br>失败"+unsucnum+"条记录;";
		addJSONObject("msg", msg);
		return SUCCESS;
	}

	/**
	 * 删除品牌结算方式
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-30
	 */
	@UserOperateLog(key="CustomerBrandSettletype",type=4,value="")
	public String deleteCustomerBrandSettletypes()throws Exception{
		String idstr = request.getParameter("idstr");
		String customerid = request.getParameter("customerid");
		Map map = salesService.deleteCustomerBrandSettletypes(idstr);
		Integer sucnum = (Integer)map.get("sucnum");
		String sucbrands = (String)map.get("sucbrands");
		Integer unsucnum = (Integer)map.get("unsucnum");
		String unsucbrands = (String)map.get("unsucbrands");
		String msg = "";
		if(sucnum > 0){
			msg = "成功"+sucnum+"条记录;<br>删除品牌结算方式 客户编号:"+customerid+"品牌编码:"+sucbrands+"成功";
			addLog("删除品牌结算方式 客户编号:"+customerid+"品牌编码:"+sucbrands,true);
		}
		if(unsucnum > 0){
			msg += "失败"+unsucnum+"条记录;<br>删除品牌结算方式 客户编号:"+customerid+"品牌编码:"+unsucbrands+"失败";
			addLog("删除品牌结算方式 客户编号:"+customerid+"品牌编码:"+unsucbrands,false);
		}
		addJSONObject("msg", msg);
		return SUCCESS;
	}

	/**
	 * 导入品牌结算方式
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-30
	 */
	@UserOperateLog(key="CustomerBrandSettletype",type=2,value="客户品牌结算方式导入")
	public String importCustomerBrandSettletype()throws Exception{
		Map map = new HashMap();
		List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
		if(null != paramList){
			List<String> paramList2 = new ArrayList<String>();
			for(String str : paramList){
				if("客户编码".equals(str)){
					paramList2.add("customerid");
				}
				else if("客户名称".equals(str)){
					paramList2.add("customername");
				}
				else if("品牌编码".equals(str)){
					paramList2.add("brandid");
				}
				else if("品牌名称".equals(str)){
					paramList2.add("brandname");
				}
				else if("结算方式".equals(str)){
					paramList2.add("settletypename");
				}
				else if("每月结算日".equals(str)){
					paramList2.add("settleday");
				}else{
					paramList2.add("null");
				}
			}
			String ids = "";
			boolean flag = false;
			List list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
			if(list.size() != 0){
				map = salesService.importCustomerBrandSettletype(list);
				if(map.containsKey("sucstr")){
					ids = (String) map.get("sucstr");
				}
				if(StringUtils.isNotEmpty(ids)){
					flag = true;
				}
				addLog("客户品牌结算方式导入:"+ids, flag);
			}else{
				map.put("excelempty", true);
			}
		}else{
			map.put("msg", "文件损坏!");
		}

		addJSONObject(map);
		return SUCCESS;
	}

    /**
     * 客户地图页面
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 22, 2016
     */
	public String customerSimplifyMapPage() throws Exception {

        String id = request.getParameter("id");
        String type = request.getParameter("type");
        JSONArray result = new JSONArray();

        String center = getSysParamValue("LocationPoint");
        request.setAttribute("center", center);

        CustomerLocation location = salesService.selectCustomerLocation(id);
        request.setAttribute("location", location);

        Customer customer = salesService.getCustomerInfo(id);
        request.setAttribute("customer", customer);

        String q1 = customer.getName();
        String q2 = CommonUtils.nullToEmpty(customer.getAddress()).length() >= 5 ?  CommonUtils.nullToEmpty(customer.getAddress()) : null;

        q1 = URLEncoder.encode(q1, "UTF-8");
        String[] params1 = {q1, getSysParamValue("MapSearchDefaultRegion"), "72a3befb0f0e332109e8d9dc7c1961d6"};

        String url = "http://api.map.baidu.com/place/v2/search?query=%s&region=%s&scope=1&ak=%s&output=json&page_size=20";

        String responseText1 = get(url, params1);
        JSONObject json1 = JSONObject.fromObject(responseText1);

        // type 为“edit”时，表明有权限修改坐标，故取相似地址。
        if("edit".equals(type)) {

            result = json1.getJSONArray("results");

            if(StringUtils.isNotEmpty(q2)) {

                q2 = URLEncoder.encode(q2, "UTF-8");
                String[] param2 = {q2, getSysParamValue("MapSearchDefaultRegion"), "72a3befb0f0e332109e8d9dc7c1961d6"};
                String responseText2 = get(url, param2);
                JSONObject json2 = JSONObject.fromObject(responseText2);
                JSONArray result2 = json2.getJSONArray("results");

                result.addAll(result2);
            }

            Collections.sort(result, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    JSONObject j1 = (JSONObject.fromObject(o1));
                    JSONObject j2 = (JSONObject.fromObject(o2));
                    return -CommonUtils.nullToEmpty(j1.getString("name")).compareTo(CommonUtils.nullToEmpty(j2.getString("name")));
                }
            });
        }

        JSONArray result2 = new JSONArray();
        for(int i = 0; i < result.size(); i ++) {

            JSONObject json = (JSONObject)result.get(i);
            // 5362 修改客户档案中定位不到客户地址的提示
            if(!json.containsKey("location")) {
                continue;
            }
            String lng = json.getJSONObject("location").getString("lng");
            String lat = json.getJSONObject("location").getString("lat");
            if(location != null
                    && StringUtils.isNotEmpty(location.getLocation())
                    && location.getLocation().equals(lng + "," + lat)) {
                continue;
            }
            result2.add(json);
        }

        request.setAttribute("candidates", result2);
        return SUCCESS;
    }

    /**
     * 修改客户定位
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Set 23, 2016
     */
    public String editCustomerLocation() throws Exception {

        String customerid = request.getParameter("customerid");
        String location = request.getParameter("location");
        String address = request.getParameter("address");

        CustomerLocation customerLocation = new CustomerLocation();
        customerLocation.setCustomerid(customerid);
        customerLocation.setLocation(location);
        customerLocation.setAddress(address);

        int ret = salesService.editCustomerLocation(customerLocation);

        Map map = new HashMap();
        map.put("flag", ret > 0);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * get
     *
     * @param url
     * @param params
     * @return
     * @author limin
     * @date Set 22, 2016
     */
    private String get(String url, Object[] params) {;

        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(String.format(url, params));

        try {
            HttpResponse res = client.execute(get);
            HttpEntity entity = res.getEntity();
            return EntityUtils.toString(entity, "UTF-8");

        } catch (Exception e) {
        } finally {

            // 关闭连接 ,释放资源
            if(client != null) {
                client.getConnectionManager().shutdown();
            }
        }

        return null;
    }

	/**
	 * 查询地址
	 *
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Oct 17, 2016
	 */
	public String searchPlace() throws Exception {

		String keyword = request.getParameter("keyword");
		keyword = URLEncoder.encode(keyword, "UTF-8");
		String[] params1 = {keyword, getSysParamValue("MapSearchDefaultRegion"), "72a3befb0f0e332109e8d9dc7c1961d6"};

		String url = "http://api.map.baidu.com/place/v2/search?query=%s&region=%s&scope=1&ak=%s&output=json&page_size=20";

		String responseText1 = get(url, params1);
		JSONObject json1 = JSONObject.fromObject(responseText1);

		JSONArray result = json1.getJSONArray("results");
		addJSONArray(result);
		return SUCCESS;
	}

    /**
     * 分销规则页面
     *
     * @return
     * @throws Exception
     * @author  limin
     * @date Nov 1, 2016
     */
    public String distributionRulePage() throws Exception {

        String id = request.getParameter("id");
        DistributionRule distributionRule = salesService.selectDistributionRule(id);
        List<DistributionRuleDetail> list = salesService.selectDistributionRuleDetailListByRuleid(id);

        request.setAttribute("detailList", JSONUtils.listToJsonStr(list));
        request.setAttribute("distributionRule", distributionRule);
        return SUCCESS;
    }

    /**
     * 分销规则列表页面
     *
     * @return
     * @throws Exception
     * @author  limin
     * @date Nov 1, 2016
     */
    public String distributionRuleListPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 分销规则编辑页面
     *
     * @return
     * @throws Exception
     * @author  limin
     * @date Nov 1, 2016
     */
    public String distributionRuleEditPage() throws Exception {

        String id = request.getParameter("id");
        DistributionRule distributionRule = salesService.selectDistributionRule(id);

        request.setAttribute("distributionRule", distributionRule);
        return SUCCESS;
    }

    /**
     * 分销规则新增页面
     *
     * @return
     * @throws Exception
     * @author  limin
     * @date Nov 1, 2016
     */
    public String distributionRuleAddPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 分销规则批量添加商品
     *
     * @return
     * @throws Exception
     * @author  limin
     * @date Nov 2, 2016
     */
    public String distributionRuleGoodsAddPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 分销规则批量添加品牌
     *
     * @return
     * @throws Exception
     * @author  limin
     * @date Nov 2, 2016
     */
    public String distributionRuleBrandAddPage() throws Exception {

        List<Brand> brands = salesService.getBrandList();
        request.setAttribute("brands", JSONUtils.listToJsonStr(brands));
        return SUCCESS;
    }

    /**
     * 分销规则批量添加供应商
     *
     * @return
     * @throws Exception
     * @author  limin
     * @date Nov 2, 2016
     */
    public String distributionRuleSupplierAddPage() throws Exception {

        List<BuySupplier> suppliers = salesService.getSupplierList();
        request.setAttribute("suppliers", JSONUtils.listToJsonStr(suppliers));
        return SUCCESS;
    }

    /**
     * 分销规则批量添加商品类型
     *
     * @return
     * @throws Exception
     * @author  limin
     * @date Nov 2, 2016
     */
    public String distributionRuleGoodsTypeAddPage() throws Exception {

        List<SysCode> codes = getBaseSysCodeService().showSysCodeListByType("goodstype");
        request.setAttribute("goodstypes", JSONUtils.listToJsonStr(codes));
        return SUCCESS;
    }

    /**
     * 分销规则批量添加商品分类
     *
     * @return
     * @throws Exception
     * @author  limin
     * @date Nov 2, 2016
     */
    public String distributionRuleGoodsSortAddPage() throws Exception {

        List<WaresClass> wares = salesService.getWaresClassList();
        request.setAttribute("wares", JSONUtils.listToJsonStr(wares));
        return SUCCESS;
    }

    /**
     * 分销规则查询商品
     *
     * @return
     * @throws Exception
     * @author  limin
     * @date Nov 2, 2016
     */
    public String getGoodsList() throws Exception {

        Map condition = CommonUtils.changeMap(request.getParameterMap());
        condition.put("nopackage", "1");
        condition.put("state", "1");
        String brandids = (String) condition.get("brandids");
        if(StringUtils.isNotEmpty(brandids)){
            condition.put("brandArr", brandids.split(","));
        }
        String defaultsorts = (String) condition.get("defaultsorts");
        if(StringUtils.isNotEmpty(defaultsorts)){
            condition.put("defaultsortArr", defaultsorts.split(","));
        }
        pageMap.setCondition(condition);

        PageData data = salesService.getGoodsList(pageMap);
        addJSONObject(data);
        return SUCCESS;
    }

    /**
     * 新增分销规则
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Nov 3, 2016
     */
    @UserOperateLog(key = "DistributionRule", type = 2)
    public String addDistributionRule() throws Exception {

        String detaillist = request.getParameter("detaillist");
        List<DistributionRuleDetail> details = JSONUtils.jsonStrToList(detaillist, new DistributionRuleDetail());

        int ret = salesService.addDistributionRule(distributionRule, details);

        Map result = new HashMap();
        result.put("flag", ret > 0);
        result.put("id", distributionRule.getId());
        addJSONObject(result);
        addLog("客户分销规则新增 编号：" + distributionRule.getId(), ret > 0);
        return SUCCESS;
    }

    /**
     * 修改分销规则
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Nov 3, 2016
     */
    @UserOperateLog(key = "DistributionRule", type = 3)
    public String editDistributionRule() throws Exception {

        String detaillist = request.getParameter("detaillist");
        List<DistributionRuleDetail> details = JSONUtils.jsonStrToList(detaillist, new DistributionRuleDetail());

        int ret = salesService.editDistributionRule(distributionRule, details);

        Map result = new HashMap();
        result.put("flag", ret > 0);
        result.put("id", distributionRule.getId());
        addJSONObject(result);
        addLog("客户分销规则修改 编号：" + distributionRule.getId(), ret > 0);
        return SUCCESS;
    }

    /**
     * 删除分销规则
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Nov 3, 2016
     */
    @UserOperateLog(key = "DistributionRule", type = 4)
    public String deleteDistributionRule() throws Exception {

        String ids = request.getParameter("ids");
        if(StringUtils.isEmpty(ids)) {
            Map map = new HashMap();
            map.put("flag", false);
            map.put("msg", "规则未选中！");
            addJSONObject(map);
        }

        Map result = salesService.deleteDistributionRule(ids.split(","));
        String successIds = (String) result.get("successIds");

        if(StringUtils.isNotEmpty(successIds)) {
            addLog("客户分销规则删除 编号：" + successIds, true);
        }
        addJSONObject(result);
        return SUCCESS;
    }

    /**
     * 查询客户分销规则分页数据
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Nov 3, 2016
     */
    public String selectDistributionRulePageData() throws Exception {

        Map condition = CommonUtils.changeMap(request.getParameterMap());

        // 客户群名称
        String customerids = (String) condition.get("customerids");
        String pcustomerids = (String) condition.get("pcustomerids");
        String customersorts = (String) condition.get("customersorts");
        String promotionsorts = (String) condition.get("promotionsorts");
        String salesareas = (String) condition.get("salesareas");
        String creditratings = (String) condition.get("creditratings");
        String canceltypes = (String) condition.get("canceltypes");
        if(StringUtils.isNotEmpty(customerids)) {

            condition.put("customerids", customerids.split(","));
        }
        if(StringUtils.isNotEmpty(pcustomerids)) {

            condition.put("pcustomerids", pcustomerids.split(","));
        }
        if(StringUtils.isNotEmpty(customersorts)) {

            condition.put("customersorts", customersorts.split(","));
        }
        if(StringUtils.isNotEmpty(promotionsorts)) {

            condition.put("promotionsorts", promotionsorts.split(","));
        }
        if(StringUtils.isNotEmpty(salesareas)) {

            condition.put("salesareas", salesareas.split(","));
        }
        if(StringUtils.isNotEmpty(creditratings)) {

            condition.put("creditratings", creditratings.split(","));
        }
        if(StringUtils.isNotEmpty(canceltypes)) {

            condition.put("canceltypes", canceltypes.split(","));
        }

        // 商品规则详情
//        String goodsids = (String) condition.get("goodsids");
//        String brandids = (String) condition.get("brandids");
//        String goodssorts = (String) condition.get("goodssorts");
//        String goodstypes = (String) condition.get("goodstypes");
//        String supplierids = (String) condition.get("supplierids");
//        if(StringUtils.isNotEmpty(goodsids)) {
//
//            condition.put("goodsids", goodsids.split(","));
//        }
//        if(StringUtils.isNotEmpty(brandids)) {
//
//            condition.put("brandids", brandids.split(","));
//        }
//        if(StringUtils.isNotEmpty(goodssorts)) {
//
//            condition.put("goodssorts", goodssorts.split(","));
//        }
//        if(StringUtils.isNotEmpty(goodstypes)) {
//
//            condition.put("goodstypes", goodstypes.split(","));
//        }
//        if(StringUtils.isNotEmpty(supplierids)) {
//
//            condition.put("supplierids", supplierids.split(","));
//        }

        pageMap.setCondition(condition);
        PageData pageData = salesService.selectDistributionRulePageData(pageMap);

        addJSONObject(pageData);
        return SUCCESS;
    }

    /**
     * 启用分销规则
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Nov 4, 2016
     */
    @UserOperateLog(key = "DistributionRule", type = 0)
    public String enableDistributionRule() throws Exception {

        String ids = request.getParameter("ids");
        if(StringUtils.isEmpty(ids)) {
            Map map = new HashMap();
            map.put("flag", false);
            map.put("msg", "规则未选中！");
            addJSONObject(map);
        }

        Map result = salesService.enableDistributionRule(ids.split(","));
        String successIds = (String) result.get("successIds");

        if(StringUtils.isNotEmpty(successIds)) {
            addLog("客户分销规则启用 编号：" + successIds, true);
        }
        addJSONObject(result);
        return SUCCESS;
    }

    /**
     * 启用分销规则
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Nov 4, 2016
     */
    @UserOperateLog(key = "DistributionRule", type = 0)
    public String disableDistributionRule() throws Exception {

        String ids = request.getParameter("ids");
        if(StringUtils.isEmpty(ids)) {
            Map map = new HashMap();
            map.put("flag", false);
            map.put("msg", "规则未选中！");
            addJSONObject(map);
        }

        Map result = salesService.disableDistributionRule(ids.split(","));
        String successIds = (String) result.get("successIds");

        if(StringUtils.isNotEmpty(successIds)) {
            addLog("客户分销规则禁用 编号：" + successIds, true);
        }
        addJSONObject(result);
        return SUCCESS;
    }

    /**
     * 分销规则查看页面
     *
     * @return
     * @throws Exception
     * @author  limin
     * @date Nov 1, 2016
     */
    public String distributionRuleViewPage() throws Exception {

        String id = request.getParameter("id");
        DistributionRule distributionRule = salesService.selectDistributionRule(id);

        request.setAttribute("distributionRule", distributionRule);
        return SUCCESS;
    }
}

