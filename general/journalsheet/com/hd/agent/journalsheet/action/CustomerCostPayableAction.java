/**
 * @(#)CustomerCostPayableAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年11月12日 chenwei 创建版本
 */
package com.hd.agent.journalsheet.action;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.service.IAttachFileService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.journalsheet.model.CustomerCostPayable;
import com.hd.agent.journalsheet.service.ICustomerCostPayableService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 客户应付费用action
 * @author chenwei
 */
public class CustomerCostPayableAction extends BaseFilesAction {

	private CustomerCostPayable customerCostPayableInit;

	private CustomerCostPayable customerCostPayable;
	
	private ICustomerCostPayableService customerCostPayableService;

	public ICustomerCostPayableService getCustomerCostPayableService() {
		return customerCostPayableService;
	}

	public void setCustomerCostPayableService(
			ICustomerCostPayableService customerCostPayableService) {
		this.customerCostPayableService = customerCostPayableService;
	}
	
	public CustomerCostPayable getCustomerCostPayableInit() {
		return customerCostPayableInit;
	}

	public void setCustomerCostPayableInit(
			CustomerCostPayable customerCostPayableInit) {
		this.customerCostPayableInit = customerCostPayableInit;
	}

	public CustomerCostPayable getCustomerCostPayable() {
		return customerCostPayable;
	}

	public void setCustomerCostPayable(CustomerCostPayable customerCostPayable) {
		this.customerCostPayable = customerCostPayable;
	}

	/**
	 * 显示客户应付费用金额合计页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月12日
	 */
	public String showCustomerCostPayableListPage() throws Exception{
		return "success";
	}
	/**
	 * 获取客户应付费用合计金额列表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月12日
	 */
	public String showCustomerCostPayableListData() throws Exception{

		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = customerCostPayableService.showCustomerCostPayableListData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}

    /**
     * 导出客户应付费用报表
     * @return
     * @throws Exception
     * @author limin
     * @date 2015年6月9日
     */
    public void exportCustomerCostPayableListData() throws Exception{

        Map map=CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        pageMap.setRows(9999);

        PageData pageData = customerCostPayableService.showCustomerCostPayableListData(pageMap);

        map.put("isflag", "true");
        pageMap.setCondition(map);
        String title = null;

        if(map.containsKey("excelTitle")){

            title = map.get("excelTitle").toString();

        } else {

            title = "list";
        }

        if(StringUtils.isEmpty(title)){
            title = "list";
        }

        ExcelUtils.exportExcel(exportCustomerCostPayableListDataFilter(pageData), title);
    }

    /**
     * 数据转换，list专程符合excel导出的数据格式(客户应付费用报表)
     * @param data
     * @return
     * @throws Exception
     * @author limin
     * @date Jun 9, 2015
     */
    private List<Map<String, Object>> exportCustomerCostPayableListDataFilter(PageData data) throws Exception{

        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();

        Map<String, Object> header = new LinkedHashMap<String, Object>();
        header.put("customerid", "客户编码");
        header.put("customername", "客户名称");
        header.put("salesareaname", "销售区域");
        header.put("salesusername", "客户业务员");
		header.put("beginamount", "期初应付金额");
        header.put("lendamount", "本期应付(借)");
		header.put("payamount", "本期已付(贷)");
        header.put("endamount", "期末应付金额");

        result.add(header);

        List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) data.getList();

        for(HashMap<String, Object> item2 : list){

            Map<String, Object> content = new LinkedHashMap<String, Object>();
            content.put("customerid", item2.get("customerid"));
            content.put("customername", item2.get("customername"));
            content.put("salesareaname", item2.get("salesareaname"));
            content.put("salesusername", item2.get("salesusername"));
			content.put("beginamount", item2.get("beginamount"));
            content.put("lendamount", item2.get("lendamount"));
            content.put("payamount", item2.get("payamount"));
            content.put("endamount", item2.get("endamount"));
            result.add(content);
        }

        //按品牌合计库存
        Map<String, Object> total = new LinkedHashMap<String, Object>();
        total.put("customerid", "");
        total.put("customername", "合计");
        total.put("salesareaname", "");
        total.put("salesusername", "");
		total.put("beginamount", ((Map<String, String>)data.getFooter().get(0)).get("beginamount"));
		total.put("lendamount", ((Map<String, String>)data.getFooter().get(0)).get("lendamount"));
        total.put("payamount", ((Map<String, String>)data.getFooter().get(0)).get("payamount"));
        total.put("endamount", ((Map<String, String>)data.getFooter().get(0)).get("endamount"));
        result.add(total);

        return result;
    }

    /**
	 * 显示客户应付费用明细页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月12日
	 */
	public String showCustomerCostPayableDetailListPage() throws Exception{
//		String customerid = request.getParameter("customerid");
//		String businessdate = request.getParameter("businessdate");
//		String businessdate1 = request.getParameter("businessdate1");
//		String supplierid = request.getParameter("supplierid");
//		String branddeptid = request.getParameter("branddeptid");
//		request.setAttribute("customerid", customerid);
//		request.setAttribute("businessdate", businessdate);
//		request.setAttribute("businessdate1", businessdate1);
//		request.setAttribute("supplierid", supplierid);
//		request.setAttribute("branddeptid", branddeptid);
		return SUCCESS;
	}
	/**
	 * 获取客户应付费用明细数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月12日
	 */
	public String showCustomerCostPayableDetailList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = customerCostPayableService.showCustomerCostPayableDetailList(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 获取客户应付费用列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月12日
	 */
	public String showCustomerCostPayableListNormalPage() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = customerCostPayableService.showCustomerCostPayableDetailList(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 显示客户应付费用期初列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月20日
	 */
	public String showCustomerCostPayableInitListPage() throws Exception{
		return "success";
	}
	/**
	 * 显示客户应付费用期初添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月20日
	 */
	public String showCustomerCostPayableInitAddPage() throws Exception{
		request.setAttribute("today", CommonUtils.getTodayDataStr());
		return "success";
	}
	/**
	 * 添加客户应付费用期初
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月20日
	 */
	@UserOperateLog(key="customerCostPayable",type=2)
	public String addCustomerCostPayableInit() throws Exception{
		boolean flag  =  customerCostPayableService.addCustomerCostPayableInit(customerCostPayableInit);
		addJSONObject("flag", flag);
		addLog("客户应付费用期初新增   编号："+customerCostPayableInit.getId(),flag);
		return "success";
	}
	/**
	 * 获取客户应付费用期初数据列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月20日
	 */
	public String showCustomerCostPayableInitList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = customerCostPayableService.showCustomerCostPayableInitList(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 显示客户应付费用修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月20日
	 */
	public String showCustomerCostPayableInitEditPage() throws Exception{
		String id = request.getParameter("id");
		CustomerCostPayable customerCostPayable = customerCostPayableService.getCustomerCostPayableByID(id);
		if(null==customerCostPayable){
			return "addSuccess";
		}else{
			request.setAttribute("customerCostPayableInit", customerCostPayable);
			return "success";
		}
	}
	/**
	 * 修改客户应付费用期初
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月20日
	 */
	@UserOperateLog(key="customerCostPayable",type=3)
	public String editCustomerCostPayableInit() throws Exception{
		boolean flag = customerCostPayableService.editCustomerCostPayableInit(customerCostPayableInit);
		addJSONObject("flag", flag);
		addLog("客户应付费用期初修改   编号："+customerCostPayableInit.getId(),flag);
		return "success";
	}
	/**
	 * 删除客户应付费用期初
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月20日
	 */
	@UserOperateLog(key="customerCostPayable",type=4)
	public String deleteCustomerCostPayableInit() throws Exception{
		String ids = request.getParameter("ids");
		if(null!=ids){
			String[] idArr = ids.split(",");
			String succssids = "";
			String errorids = "";
			for(String id : idArr){
				boolean flag = customerCostPayableService.deleteCustomerCostPayableInit(id);
				if(flag){
					succssids += id+",";
				}else{
					errorids += id+",";
				}
				
			}
			Map map = new HashMap();
			map.put("flag", true);
			if(!"".equals(succssids)){
				map.put("succssids", "删除成功编号:"+succssids);
			}else{
				map.put("succssids", "");
			}
			if(!"".equals(errorids)){
				map.put("errorids", "删除失败编号:"+errorids);
			}else{
				map.put("errorids", "");
			}
			addJSONObject(map);
			addLog("删除客户应付费用期初  成功编号："+succssids+";失败编号："+errorids, true);
		}else{
			addJSONObject("flag", false);
		}
		return "success";
	}
	/**
	 * 显示客户应付费用期初查看页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月20日
	 */
	public String showCustomerCostPayableInitViewPage() throws Exception{
		String id = request.getParameter("id");
		CustomerCostPayable customerCostPayable = customerCostPayableService.getCustomerCostPayableByID(id);
		if(null==customerCostPayable){
			return "addSuccess";
		}else{
			request.setAttribute("customerCostPayableInit", customerCostPayable);
			return "success";
		}
	}
	/**
	 * 导出客户应付费用期初数据
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月20日
	 */
	public void exportCustomerCostPayableInitList() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isPageflag", "true");
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
		PageData pageData = customerCostPayableService.showCustomerCostPayableInitList(pageMap);
		ExcelUtils.exportExcel(exportCustomerCostPayableInitFilter(pageData.getList(),pageData.getFooter()), title);
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(费用明细报表)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportCustomerCostPayableInitFilter(List<CustomerCostPayable> list,List<Map> footerList) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("id", "编号");
		firstMap.put("businessdate", "业务日期");
		firstMap.put("customerid", "客户编号");
		firstMap.put("customername", "客户名称");
		firstMap.put("salesareaname", "销售区域");
		firstMap.put("expensesortname", "费用分类");
		firstMap.put("amount", "应付金额");
		firstMap.put("remark", "备注");
		firstMap.put("applyusername", "制单人");
		firstMap.put("addtime", "制单时间");
		
		result.add(firstMap);
		
		if(list.size() != 0){
			for(CustomerCostPayable customerCostPayable : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2 = PropertyUtils.describe(customerCostPayable);
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
		if(footerList.size() != 0){
			for(Map<String,Object> map : footerList){
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
	 * 导入客户应付费用期初
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月20日
	 */
	@UserOperateLog(key="customerCostPayable",type=2)
	public String importCustomerCostPayableInitList() throws Exception{
		List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
		List<String> paramList2 = new ArrayList<String>();
		Map reimburseMap=new HashMap();
		for(String str : paramList){
			if(null==str || "".equals(str.trim())){
				continue;
			}
			if("编号".equals(str.trim())){
				paramList2.add("id");
			}
			else if("业务日期".equals(str.trim())){
				paramList2.add("businessdate");
			}else if("客户编号".equals(str.trim())){
				paramList2.add("customerid");
			}else if("客户名称".equals(str.trim())){
				paramList2.add("customername");
			}else if("销售区域".equals(str.trim())){
				paramList2.add("salesareaname");
			}else if("费用分类".equals(str.trim())){
				paramList2.add("expensesortname");
			}else if("应付金额".equals(str.trim())){
				paramList2.add("amount");
			}else if("备注".equals(str.trim())){
				paramList2.add("remark");
			}else{
				paramList2.add("null");
			}
		}
		Map map = new HashMap();
		List list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
		if(list.size() != 0){
			map = customerCostPayableService.addCustomerCostPayableInitList(list);
		}else{
			map.put("excelempty", true);
		}
		addJSONObject(map);
		Boolean flag=false;
		String successMsg = "";
		if(null!=map){
			flag = (Boolean)map.get("flag");
			if(null==flag){
				flag=false;
			}
			successMsg = (String) map.get("successMsg");
		}
		addLog("批量导入客户应付费用  " +successMsg,flag);
		return "success";
	}
	
	/**
	 * 显示客户应付费用新增页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public String showCustomerCostPayableAddPage()throws Exception{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sf.format(new Date());
		request.setAttribute("businessdate", date);
		return SUCCESS;
	}
	
	/**
	 * 显示客户应付费用修改页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public String showCustomerCostPayableEditPage()throws Exception{
		String id = request.getParameter("id");
		CustomerCostPayable customerCostPayable = customerCostPayableService.getCustomerCostPayableByID(id);
		if(null!=customerCostPayable ){
			request.setAttribute("customerCostPayable", customerCostPayable);
			if(!"0".equals(customerCostPayable.getSourcefrom())
//					|| !"2".equals(customerCostPayable.getBilltype())
					|| !"0".equals(customerCostPayable.getIsbegin())
					|| "1".equals(customerCostPayable.getHcflag()) 
					|| "2".equals(customerCostPayable.getHcflag())){
				return "ViewSuccess";
			}
		}else{
			return "AddSuccess";			
		}
		return SUCCESS;
	}
	
	/**
	 * 显示客户应付费用红冲新增页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public String showCustomerCostPayableHCAddPage()throws Exception{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sf.format(new Date());
		request.setAttribute("businessdate", date);
		
		String id = request.getParameter("id");
		CustomerCostPayable customerCostPayable = customerCostPayableService.getCustomerCostPayableByID(id);
		if(null!=customerCostPayable ){
			request.setAttribute("customerCostPayable", customerCostPayable);
			
			if("11".equals(customerCostPayable.getSourcefrom())
					|| !"2".equals(customerCostPayable.getBilltype())
					|| !"0".equals(customerCostPayable.getIsbegin())
					|| "1".equals(customerCostPayable.getHcflag()) 
					|| "2".equals(customerCostPayable.getHcflag())){
				return "ViewSuccess";
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 显示客户应付费用详情页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public String showCustomerCostPayableViewPage()throws Exception{
		String id = request.getParameter("id");
		Map colMap = getAccessColumn("t_js_customercost_payable");
		CustomerCostPayable customerCostPayable = customerCostPayableService.getCustomerCostPayableByID(id);
		if(null != customerCostPayable){
			request.setAttribute("showMap", colMap);
			request.setAttribute("customerCostPayable", customerCostPayable);
		}
		return SUCCESS;
	}
	
	/**
	 * 新增客户应付费用
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	@UserOperateLog(key="CustomerCostPayable",type=2,value="")
	public String addCustomerCostPayable()throws Exception{
		Map resultMap=new HashMap();
		if(!"1".equals(customerCostPayable.getBilltype()) && !"2".equals(customerCostPayable.getBilltype())){
			resultMap.put("falg", false);
			resultMap.put("msg", "未知的单据类型");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		if(BigDecimal.ZERO.compareTo(customerCostPayable.getAmount())==0){
			resultMap.put("falg", false);
			resultMap.put("msg", "请填写费用金额");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		if(StringUtils.isEmpty(customerCostPayable.getCustomerid())){
			resultMap.put("falg", false);
			resultMap.put("msg", "请选择客户");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		boolean flag= customerCostPayableService.addCustomerCostPayable(customerCostPayable);
		resultMap.put("flag", flag);
		addJSONObject(resultMap);
		
		StringBuffer logSb=new StringBuffer();
		logSb.append("新增客户应付费用");
		if(StringUtils.isNotEmpty(customerCostPayable.getId())){
			logSb.append("编号：");
			logSb.append(customerCostPayable.getId());
		}else{
			if(StringUtils.isNotEmpty(customerCostPayable.getBusinessdate())){
				logSb.append("业务日期:"+customerCostPayable.getBusinessdate());
			}
			if(StringUtils.isNotEmpty(customerCostPayable.getOaid())){
				logSb.append("OA编号:"+customerCostPayable.getOaid());
			}
			if(StringUtils.isNotEmpty(customerCostPayable.getSupplierid())){
				logSb.append("供应商编号:"+customerCostPayable.getSupplierid());				
			}
		}
		addLog(logSb.toString(),flag);
		return SUCCESS;
	}
	
	/**
	 * 修改客户应付费用
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	@UserOperateLog(key="CustomerCostPayable",type=3,value="")
	public String editCustomerCostPayable()throws Exception{
		Map resultMap=new HashMap();
		if(StringUtils.isEmpty(customerCostPayable.getId())){
			resultMap.put("falg", false);
			resultMap.put("msg", "未找到相关客户应付信息");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		if(BigDecimal.ZERO.compareTo(customerCostPayable.getAmount())==0){
			resultMap.put("falg", false);
			resultMap.put("msg", "请填写费用金额");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		if(StringUtils.isEmpty(customerCostPayable.getCustomerid())){
			resultMap.put("falg", false);
			resultMap.put("msg", "请选择客户");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		resultMap = customerCostPayableService.editCustomerCostPayable(customerCostPayable);
		Boolean flag=false;
		if(null==resultMap){
			resultMap=new HashMap();
			resultMap.put("flag", false);
			flag=false;
		}else{
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
				resultMap.put("flag", false);
			}
		}
		addJSONObject(resultMap);
		
		StringBuffer logSb=new StringBuffer();
		logSb.append("新增客户应付费用");
		if(StringUtils.isNotEmpty(customerCostPayable.getId())){
			logSb.append("编号：");
			logSb.append(customerCostPayable.getId());
		}else{
			if(StringUtils.isNotEmpty(customerCostPayable.getBusinessdate())){
				logSb.append("业务日期:"+customerCostPayable.getBusinessdate());
			}
			if(StringUtils.isNotEmpty(customerCostPayable.getOaid())){
				logSb.append("OA编号:"+customerCostPayable.getOaid());
			}
			if(StringUtils.isNotEmpty(customerCostPayable.getSupplierid())){
				logSb.append("供应商编号:"+customerCostPayable.getSupplierid());				
			}
		}
		addLog(logSb.toString(),flag);
		return SUCCESS;
	}
	
	/**
	 * 新增户应付费用红冲
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	@UserOperateLog(key="CustomerCostPayable",type=2,value="")
	public String addCustomerCostPayableHC()throws Exception{
		Map resultMap = customerCostPayableService.addCustomerCostPayableHC(customerCostPayable);
		Boolean flag=false;
		if(null==resultMap){
			resultMap=new HashMap();
			resultMap.put("flag", false);
			flag=false;
		}else{
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
				resultMap.put("flag", false);
			}
		}
		addJSONObject(resultMap);
		StringBuffer logSb=new StringBuffer();
		logSb.append("新增客户应付费用红冲");
		if(StringUtils.isNotEmpty(customerCostPayable.getId())){
			logSb.append("编号：");
			logSb.append(customerCostPayable.getId());
		}else{
			if(StringUtils.isNotEmpty(customerCostPayable.getBusinessdate())){
				logSb.append("业务日期:"+customerCostPayable.getBusinessdate());
			}
			if(StringUtils.isNotEmpty(customerCostPayable.getOaid())){
				logSb.append("OA编号:"+customerCostPayable.getOaid());
			}
			if(StringUtils.isNotEmpty(customerCostPayable.getSupplierid())){
				logSb.append("供应商编号:"+customerCostPayable.getSupplierid());				
			}
		}
		addLog(logSb.toString(),flag);
		return SUCCESS;
	}
	/**
	 * 撤销客户应付费用红冲
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	@UserOperateLog(key="CustomerCostPayable",type=0,value="")
	public String removeCustomerCostPayableHC()throws Exception{
		String id=request.getParameter("id");
		Map resultMap = new HashMap();
		if(null==id || "".equals(id.trim())){				
			resultMap.put("flag","false");
			resultMap.put("msg", "抱歉，未能找到关联的客户应付费用");
			addLog("抱歉，未能找到关联的客户应付费用");
			return SUCCESS;
		}
		StringBuffer logSb=new StringBuffer();
		logSb.append("撤销客户应付费用红冲");
		resultMap = customerCostPayableService.removeCustomerCostPayableHC(id);
		Boolean flag=false;
		if(null==resultMap){
			resultMap=new HashMap();
			resultMap.put("flag", false);
			flag=false;
		}else{
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
				resultMap.put("flag", false);
			}
		}
		addLog(logSb.toString(),flag);
		addJSONObject(resultMap);
		return SUCCESS;
	}
	
	/**
	 * 删除客户应付费用
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	@UserOperateLog(key="CustomerCostPayable",type=4)
	public String deleteCustomerCostPayable()throws Exception{		
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_js_customercost_payable",id);
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag = customerCostPayableService.deleteCustomerCostPayable(id);
		addLog("删除客户应付费用 编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	/**
	 * 删除客户应付费用
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	@UserOperateLog(key="CustomerCostPayable",type=4)
	public String deleteCustomerCostPayableMore()throws Exception{
		String idarrs = request.getParameter("idarrs");
		Map map= customerCostPayableService.deleteCustomerCostPayableMore(idarrs);
		Boolean flag=false;
		if(null!=map){
			flag=(Boolean)map.get("flag");
			if(null==flag){
				flag=false;
			}
			addLog("批量删除客户应付费用 编号:"+idarrs,flag);
		}else{
			addLog("批量删除客户应付费用 编号失败:"+idarrs);
		}
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 导出-客户应付费用统计报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportCustomerCostPayableData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isPageflag", "true");
		map.put("isExportData", "true");	//是否导出数据
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
		PageData pageData = customerCostPayableService.showCustomerCostPayableDetailList(pageMap);
		ExcelUtils.exportExcel(exportCustomerCostPayableDataFilter(pageData.getList(),pageData.getFooter()), title);
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(客户应付费用表)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportCustomerCostPayableDataFilter(List<CustomerCostPayable> list,List<CustomerCostPayable> footerList) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("id", "单据编号");
		firstMap.put("oaid", "OA编号");
		firstMap.put("businessdate", "业务日期");
		firstMap.put("customerid", "客户编码");
		firstMap.put("customername", "客户名称");
		firstMap.put("expensesortname", "费用类别");
		firstMap.put("supplierid", "供应商编码");
		firstMap.put("suppliername", "供应商名称");
		firstMap.put("bankname", "银行名称");
		firstMap.put("lendamount", "借");
		firstMap.put("payamount", "贷");
		firstMap.put("hcflagname", "是否红冲");
		firstMap.put("isbeginname", "是否期初");
		firstMap.put("remark", "备注");
		firstMap.put("relateoaid", "关联OA号");
		firstMap.put("applyusername", "申请人");
		firstMap.put("addtime", "申请时间");
		firstMap.put("billtypename", "单据类型");
		firstMap.put("paytypename", "支付类型");
		firstMap.put("sourcefromname", "数据来源");
		firstMap.put("billno", "来源单据号");
		firstMap.put("hcreferid", "红冲关联单据号");
		
		result.add(firstMap);
		
		if(list.size() != 0){
			for(CustomerCostPayable customerCostPayable : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2 = PropertyUtils.describe(customerCostPayable);
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
		if(footerList.size() != 0){
			for(CustomerCostPayable customerCostPayable : footerList){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(customerCostPayable);
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
	 * 导入客户应付费用表
	 *
	 * @return
	 * @throws Exception
	 * @author zhang_honghui
	 * @date JUL. 29, 2016
	 */
	@UserOperateLog(key = "CustomerCostPayable", type = 2)
	public String importCustomerCostPayableData() throws Exception {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			String errorTempletFilePath = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/CustomerCostPayableTemplet.xls");
			List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
			List<String> paramList2 = new ArrayList<String>();
			List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
			for (String str : paramList) {
				if (null == str || "".equals(str.trim())) {
					continue;
				}
				if ("OA编号".equals(str.toUpperCase().trim())) {
					paramList2.add("oaid");
				} else if ("业务日期".equals(str.trim())) {
					paramList2.add("businessdate");
				} else if ("客户编码".equals(str.trim())) {
					paramList2.add("customerid");
				} else if ("客户名称".equals(str.trim())) {
					paramList2.add("customername");
				} else if ("费用类别".equals(str.trim())) {
					paramList2.add("expensesortname");
				} else if ("供应商编码".equals(str.trim())) {
					paramList2.add("supplierid");
				} else if ("供应商名称".equals(str.trim())) {
					paramList2.add("suppliername");
				} else if ("供应商所属部门".equals(str.trim())) {
					paramList2.add("supplierdeptname");
				}  else if ("借".equals(str.trim())) {
					paramList2.add("lendamount");
				} else if ("贷".equals(str.trim())) {
					paramList2.add("payamount");
				}  else if ("单据类型".equals(str.trim())) {
					paramList2.add("billtypename");
				}  else if ("费用金额".equals(str.trim()) || "金额".equals(str.trim())) {
					paramList2.add("amount");
				} else if ("银行编码".equals(str.trim())) {
					paramList2.add("bankid");
				} else if ("支付类型".equals(str.trim())) {
					paramList2.add("paytypename");
				} else if ("备注".equals(str.trim())) {
					paramList2.add("remark");
				} else {
					paramList2.add("null");
				}
			}
			List<String> dataCellList = new ArrayList<String>();
			dataCellList.add("oaid");
			dataCellList.add("businessdate");
			dataCellList.add("customerid");
			dataCellList.add("customername");
			dataCellList.add("supplierid");
			dataCellList.add("suppliername");
			dataCellList.add("expensesortname");
			dataCellList.add("amount");
			dataCellList.add("billtypename");
			dataCellList.add("paytypename");
			dataCellList.add("bankid");
			dataCellList.add("remark");
			dataCellList.add("errormessage");

			List list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
			if (list!=null && list.size() != 0) {
				resultMap = customerCostPayableService.addDRCustomerCostPayable(list);
				if(resultMap.containsKey("errorDataList") && null!=resultMap.get("errorDataList")){
					List<CustomerCostPayable> errorDataList=(List<CustomerCostPayable>)resultMap.get("errorDataList");
					for(CustomerCostPayable item:errorDataList){
						Map itemMap = PropertyUtils.describe(item);
						if(null!=itemMap){
							errorList.add(itemMap);
						}
					}
				}

				Boolean flag = false;
				if (null != resultMap) {
					flag = (Boolean) resultMap.get("flag");
					if (null == flag) {
						flag = false;
					}
				}
				StringBuffer logBuffer=new StringBuffer();
				logBuffer.append("批量导入客户应付费用表");
				if(errorList.size() > 0){
					try {
						IAttachFileService attachFileService = (IAttachFileService) SpringContextUtils.getBean("attachFileService");
						String fileid = attachFileService.createExcelAndAttachFile(errorList, dataCellList, errorTempletFilePath,"客户应付费用导入失败");
						resultMap.put("msg", "导入失败" + errorList.size() + "条");
						resultMap.put("errorid", fileid);
						if (errorList.size() > 0) {
							logBuffer.append("导入失败" + errorList.size() + "条");
						}
					}catch (Exception ex){
						resultMap.put("msg", "生成导入出错的excel文件失败");
					}
				}
				addLog(logBuffer.toString(), flag);
			} else {
				resultMap.put("excelempty", true);
			}
		}catch (Exception ex){
			resultMap.put("msg","导入时系统异常");
		}
		addJSONObject(resultMap);

		return "success";
	}
}

