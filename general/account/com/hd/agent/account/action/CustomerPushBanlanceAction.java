/**
 * @(#)CustomerPushBanlanceAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 18, 2013 chenwei 创建版本
 */
package com.hd.agent.account.action;

import com.hd.agent.account.model.CustomerPushBalance;
import com.hd.agent.account.service.ICustomerPushBanlanceService;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.system.model.SysParam;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 
 * 客户应收款冲差
 * @author chenwei
 */
public class CustomerPushBanlanceAction extends BaseFilesAction {
	
	private CustomerPushBalance customerPushBalance;
	
	private ICustomerPushBanlanceService customerPushBanlanceService;

	public ICustomerPushBanlanceService getCustomerPushBanlanceService() {
		return customerPushBanlanceService;
	}

	public void setCustomerPushBanlanceService(
			ICustomerPushBanlanceService customerPushBanlanceService) {
		this.customerPushBanlanceService = customerPushBanlanceService;
	}
	
	public CustomerPushBalance getCustomerPushBalance() {
		return customerPushBalance;
	}

	public void setCustomerPushBalance(CustomerPushBalance customerPushBalance) {
		this.customerPushBalance = customerPushBalance;
	}

	/**
	 * 显示客户应收款冲差列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 18, 2013
	 */
	public String showCustomerPushBanlaceListPage() throws Exception{
		String id = request.getParameter("id");
        String status = request.getParameter("status");
		request.setAttribute("id", id);
        request.setAttribute("status", status);
		SysParam sysParam = getBaseSysParamService().getSysParam("pushstatus");
		if(null != sysParam){
			request.setAttribute("pushstatus", sysParam.getPvalue());
		}else{
			request.setAttribute("pushstatus", "0");
		}
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		return "success";
	}
	/**
	 * 获取客户应收款冲差数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 18, 2013
	 */
	public String showCustomerPushBanlanceList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = customerPushBanlanceService.showCustomerPushBanlanceList(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 显示客户应收款冲差添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 18, 2013
	 */
	public String showCustomerPushBanlanceAddPage() throws Exception{
		String invoiceid = request.getParameter("invoiceid");
		request.setAttribute("invoiceid", invoiceid);
		request.setAttribute("date", CommonUtils.getTodayDataStr());
		List pushtypeList = getBaseSysCodeService().showSysCodeListByType("pushtypeprint");
		request.setAttribute("pushtypeList",pushtypeList);
		return "success";
	}
	/**
	 * 显示客户应收款冲差批量添加数据页面
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jul 18, 2013
	 */
	public String showCustomerPushBanlanceDetailAddPage() throws Exception{
		String invoiceid = request.getParameter("invoiceid");
		request.setAttribute("invoiceid", invoiceid);
		request.setAttribute("date", CommonUtils.getTodayDataStr());
		List pushtypeList = getBaseSysCodeService().showSysCodeListByType("pushtypeprint");
		request.setAttribute("pushtypeList",pushtypeList);
		return "success";
	}
	/**
	 * 应收款冲差页面
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date May 19, 2017
	 */
	public String showCustomerPushBanlancePage() throws Exception {
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		request.setAttribute("id", id);
		request.setAttribute("type", type);
		return SUCCESS;
	}
	/**
	 * 显示客户应收款按客户冲差批量添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jul 18, 2013
	 */
	public String showCustomerPushBanlanceMoreAddPage() throws Exception{
		String invoiceid = request.getParameter("invoiceid");
		request.setAttribute("invoiceid", invoiceid);
		request.setAttribute("date", CommonUtils.getTodayDataStr());
		List pushtypeList = getBaseSysCodeService().showSysCodeListByType("pushtypeprint");
		request.setAttribute("pushtypeList",pushtypeList);
		return "success";
	}
	/**
	 * 客户应收款冲差新增页面修改明细记录
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date May 19, 2017
	 */
	public String showCustomerPushBanlanceDetailEditPage() throws Exception {
		String invoiceid = request.getParameter("invoiceid");
		request.setAttribute("invoiceid", invoiceid);
		request.setAttribute("date", CommonUtils.getTodayDataStr());
		List pushtypeList = getBaseSysCodeService().showSysCodeListByType("pushtypeprint");
		request.setAttribute("pushtypeList",pushtypeList);
		return "success";
	}
	/**
	 * 按客户批量添加应收款冲差单
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date May 19, 2017
	 */
	@UserOperateLog(key="CustomerPushBanlance",type=2)
	public String addMoreCustomerPushBanlace() throws Exception{
		String saveaudit = request.getParameter("saveaudit");

		Map result = new HashMap();
		String detailStr = request.getParameter("detailStr");
		List<CustomerPushBalance> detailList = JSONUtils.jsonStrToList(detailStr,new CustomerPushBalance());

		Map  map = customerPushBanlanceService.addMoreCustomerPushBanlace(customerPushBalance, detailList);
		String successid=(String)map.get("successid");
		Boolean flag=(Boolean)map.get("flag");
		addLog("应收款冲差单新增 编号：" + successid, flag);
		result.put("flag",flag);
		result.put("addsuccessid",successid);

		if("1".equals(saveaudit)) {
			if(StringUtils.isNotEmpty(successid)){
				Map auditMap = customerPushBanlanceService.auditMoreCustomerPushBanlace(successid);
				String auditsuccessid=(String)auditMap.get("auditsuccessid");
				result.put("auditsuccessid",auditsuccessid);
				Boolean auditflag=(Boolean)auditMap.get("auditflag");
				if (StringUtils.isNotEmpty(auditsuccessid)) {
					addLog("应收款冲差单保存并审核 编号：" + auditsuccessid, auditflag);
				}
			}
		}
		addJSONObject(result);
		return "success";
	}

	/**
	 * 客户应收款冲差添加保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 18, 2013
	 */
	@UserOperateLog(key="CustomerPushBanlance",type=2)
	public String addCustomerPushBanlance() throws Exception{
		boolean flag = customerPushBanlanceService.addCustomerPushBanlance(customerPushBalance);
		Map map = new HashMap();
		map.put("flag", flag);
		if(null!=customerPushBalance){
			map.put("id", customerPushBalance.getId());
		}
		addJSONObject(map);
		addLog("冲差单新增,编号:"+customerPushBalance.getId(), flag);
		return "success";
	}
	/**
	 * 显示客户应收款冲差修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 19, 2013
	 */
	public String showCustomerPushBanlanceEditPage() throws Exception{
		String id = request.getParameter("id");
		CustomerPushBalance customerPushBalance = customerPushBanlanceService.showCustomerPushBanlanceInfo(id);
		request.setAttribute("customerPushBalance", customerPushBalance);
		List pushtypeList = getBaseSysCodeService().showSysCodeListByType("pushtypeprint");
		request.setAttribute("pushtypeList",pushtypeList);
		if(null!=customerPushBalance && ("2".equals(customerPushBalance.getStatus()) || "1".equals(customerPushBalance.getStatus())) && "0".equals(customerPushBalance.getIsinvoice())){
			return "success";
		}else if(null!=customerPushBalance && ("3".equals(customerPushBalance.getStatus()) || "4".equals(customerPushBalance.getStatus())) || !"0".equals(customerPushBalance.getIsinvoice())){
			return "viewSuccess";
		}else{
			return "addSuccess";
		}
		
	}
	/**
	 *  显示客户应收款冲差查看页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 20, 2013
	 */
	public String showCustomerPushBanlanceViewPage() throws Exception{
		String id = request.getParameter("id");
		CustomerPushBalance customerPushBalance = customerPushBanlanceService.showCustomerPushBanlanceInfo(id);
		request.setAttribute("customerPushBalance", customerPushBalance);
		return "success";
	}
	/**
	 * 客户应收款冲差修改
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 19, 2013
	 */
	@UserOperateLog(key="CustomerPushBanlance",type=3)
	public String editCustomerPushBanlance() throws Exception{
		boolean flag = customerPushBanlanceService.editCustomerPushBanlance(customerPushBalance);
		addJSONObject("flag", flag);
		addLog("冲差单修改,编号:"+customerPushBalance.getId(), flag);
		return "success";
	}
	
	/**
	 * 客户应收款冲差备注修改
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 27, 2014
	 */
	public String editCustomerPushBanlanceRemark()throws Exception{
		boolean flag = customerPushBanlanceService.editCustomerPushBanlanceRemark(customerPushBalance);
		addJSONObject("flag", flag);
		addLog("冲差单备注修改,编号:"+customerPushBalance.getId(), flag);
		return SUCCESS;
	}
	
	/**
	 * 客户应收款冲差删除
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 19, 2013
	 */
	@UserOperateLog(key="CustomerPushBanlance",type=2)
	public String deleteCustomerPushBanlance() throws Exception{
		String ids = request.getParameter("ids");
		boolean flag = true;
		String successids = "";
		String errorids = "";
		if(null!=ids){
			String[] idArr = ids.split(",");
			for(String id:idArr){
				boolean delflag = customerPushBanlanceService.deleteCustomerPushBanlance(id);
				if(delflag){
					successids += id+",";
				}else{
					errorids += id+",";
				}
			}
			
		}else{
			flag = false;
		}
		Map map = new HashMap();
		map.put("successids", successids);
		map.put("errorids", errorids);
		map.put("flag", flag);
		addJSONObject(map);
		addLog("冲差单删除,编号:"+ids+".成功编号:"+successids+"，失败编号:"+errorids, flag);
		return "success";
	}
	/**
	 * 客户应收款冲差审核
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 19, 2013
	 */
	@UserOperateLog(key="CustomerPushBanlance",type=2)
	public String auditCustomerPushBanlance() throws Exception{
		String ids = request.getParameter("ids");
		boolean flag = true;
		String successids = "";
		String errorids = "";
		if(null!=ids){
			String[] idArr = ids.split(",");
			for(String id:idArr){
				boolean delflag = customerPushBanlanceService.auditCustomerPushBanlance(id);
				if(delflag){
					if(StringUtils.isEmpty(successids)){
						successids = id;
					}else{
						successids += "," + id;
					}
				}else{
					if(StringUtils.isEmpty(errorids)){
						errorids = id;
					}else{
						errorids += "," + id;
					}
					errorids += id+",";
				}
			}
			
		}else{
			flag = false;
		}
		Map map = new HashMap();
		map.put("successids", successids);
		map.put("errorids", errorids);
		map.put("flag", flag);
		addJSONObject(map);
		String msg = "冲差单审核,编号:"+ids + ".";
		if(StringUtils.isNotEmpty(successids)){
			msg += "成功编号:" + successids + " ";
		}
		if(StringUtils.isNotEmpty(errorids)){
			msg += "失败编号:" + errorids + " ";
		}
		addLog(msg, flag);
		return "success";
	}
	/**
	 * 客户应收款冲差反审
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 19, 2013
	 */
	@UserOperateLog(key="CustomerPushBanlance",type=2)
	public String oppauditCustomerPushBanlance() throws Exception{
		String ids = request.getParameter("ids");
		boolean flag = true;
		String successids = "";
		String errorids = "";
		if(null!=ids){
			String[] idArr = ids.split(",");
			for(String id:idArr){
				boolean delflag = customerPushBanlanceService.oppauditCustomerPushBanlance(id);
				if(delflag){
					if(StringUtils.isEmpty(successids)){
						successids = id;
					}else{
						successids += "," + id;
					}
				}else{
					if(StringUtils.isEmpty(errorids)){
						errorids = id;
					}else{
						errorids += "," + id;
					}
					errorids += id+",";
				}
			}
			
		}else{
			flag = false;
		}
		Map map = new HashMap();
		map.put("successids", successids);
		map.put("errorids", errorids);
		map.put("flag", flag);
		addJSONObject(map);
		String msg = "冲差单反审,编号:"+ids + ".";
		if(StringUtils.isNotEmpty(successids)){
			msg += "成功编号:" + successids + " ";
		}
		if(StringUtils.isNotEmpty(errorids)){
			msg += "失败编号:" + errorids + " ";
		}
		addLog(msg, flag);
		return "success";
	}
	

	
	/**
	 * 客户冲差单导出
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 8, 2014
	 */
	public void exportCustomerPushBanlance() throws Exception{
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
		PageData pageData = customerPushBanlanceService.showCustomerPushBanlanceList(pageMap);
		ExcelUtils.exportExcel(exportCustomerPushBanlanceFilter(pageData), title);
	}
	/**
	 * 客户冲差单导出格式
	 * @param pageData
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 8, 2014
	 */
	private List<Map<String,Object>> exportCustomerPushBanlanceFilter(PageData pageData)throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		
		firstMap.put("id", "编号");
		firstMap.put("businessdate", "业务日期");
		firstMap.put("customerid", "客户编码");
		firstMap.put("customername", "客户名称");
		firstMap.put("salesdeptname", "销售部门");
		firstMap.put("salesusername", "客户业务员");
		firstMap.put("isinvoicename", "单据类型");
		firstMap.put("invoiceid", "相关单据号");
		firstMap.put("pushtypename", "冲差类型");
		firstMap.put("brandid", "品牌编号");
		firstMap.put("brandname", "品牌名称");
        firstMap.put("defaulttaxtypename", "默认税种");
        firstMap.put("subjectname", "费用科目");
		firstMap.put("amount", "冲差金额");
        firstMap.put("notaxamount", "冲差未税金额");
        firstMap.put("tax", "税额");
        firstMap.put("writeoffamount", "核销金额");
        firstMap.put("tailamount", "尾差金额");
		firstMap.put("statusname", "状态");
		firstMap.put("isrefername", "抽单状态");
        firstMap.put("isinvoicebillname", "开票状态");
		firstMap.put("iswriteoffname", "核销状态");
		firstMap.put("remark", "备注");
		firstMap.put("addusername", "制单人");
		firstMap.put("addtime", "制单时间");
		firstMap.put("auditusername", "审核人");
		firstMap.put("audittime", "审核时间");
		result.add(firstMap);
		List<CustomerPushBalance> list = pageData.getList();
        if(list.size() != 0){
            for(CustomerPushBalance customerPushBalance : new ArrayList<CustomerPushBalance>(list)){
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map = new HashMap<String, Object>();
                map = PropertyUtils.describe(customerPushBalance);
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
	 * 显示客户应收款核销页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 24, 2014
	 */
	public String showCustomerPushWritePage() throws Exception{
		String customerid = request.getParameter("customerid");
		String id = request.getParameter("id");
		request.setAttribute("customerid", customerid);
		request.setAttribute("id", id);
		return "success";
	}

    /**
     * 根据默认税种计算未税金额、税额
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-11-20
     */
    public String getPushBanlanceNoTaxAmount()throws Exception{
        Map map = request.getParameterMap();
        map = CommonUtils.changeMap(map);
        Map retmap = customerPushBanlanceService.getPushBanlanceNoTaxAmount(map);
        addJSONObject(retmap);
        return SUCCESS;
    }

    /**
     * 显示客户应收款冲差报表页面
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016年8月1日
     */
    public String showCustomerPushBalanceReportPage() throws Exception{
        String today = CommonUtils.getTodayDataStr();
        String firstDay = CommonUtils.getMonthDateStr();
        request.setAttribute("firstday", firstDay);
        request.setAttribute("today", today);
		List pushList = getBaseSysCodeService().showSysCodeListByType("pushtypeprint");
		request.setAttribute("pushList",pushList);
        return "success";
    }
    /**
     * 获取客户应收款冲差报表数据
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016年8月1日
     */
    public String showCustomerPushBalanceReportData() throws Exception{
        Map map=CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = customerPushBanlanceService.getPushBalanceReportData(pageMap);
        addJSONObjectWithFooter(pageData);
        return "success";
    }

     /**
      * 导出客户应收款冲差报表数据
      * @author lin_xx
      * @date 2016/12/19
      */
     public void overalExportCutomerPushBalanceReport() throws Exception {
         Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
         Map queryMap = new HashMap();
         queryMap.put("isflag", "true");
         String query = (String) map.get("param");
         JSONObject object = JSONObject.fromObject(query);
         for (Object k : object.keySet()) {
             Object v = object.get(k);
             if(StringUtils.isNotEmpty((String) v)){
                 queryMap.put(k.toString(), (String) v);
             }
         }
         pageMap.setCondition(queryMap);
         PageData pageData =  customerPushBanlanceService.getPushBalanceReportData(pageMap);
         List list = pageData.getList();
         if(null != pageData.getFooter()){
             List footer = pageData.getFooter();
             list.addAll(footer);
         }
         ExcelUtils.exportAnalysExcel(map,list);
     }

      /**
       * 显示客户及业务员冲差报表页面
       * @author lin_xx
       * @date 2016/12/24
       */
      public String showCustomerAndUserPushReportPage() throws Exception {
		  String today = CommonUtils.getTodayDataStr();
		  String firstDay = CommonUtils.getMonthDateStr();
		  request.setAttribute("firstday", firstDay);
		  request.setAttribute("today", today);
		  List pushList = getBaseSysCodeService().showSysCodeListByType("pushtypeprint");
		  request.setAttribute("pushList",pushList);
		  return SUCCESS;
	  }

       /**
        * 查询 客户及业务员冲差报表 数据
        * @author lin_xx
        * @date 2016/12/24
        */
	  public String showCustomerAndUserPushReportData() throws Exception {
          Map map=CommonUtils.changeMap(request.getParameterMap());
          pageMap.setCondition(map);
          PageData pageData = customerPushBanlanceService.getCustomerAndUserPushData(pageMap);
          addJSONObjectWithFooter(pageData);
          return SUCCESS;
      }
	   /**
	    * 全局导出 客户及业务员冲差报表 数据
	    * @author lin_xx
	    * @date 2016/12/26
	    */
	   public void overalExportCutomerAndUserPushReport() throws Exception {
		   Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		   Map queryMap = new HashMap();
		   queryMap.put("isflag", "true");
		   String query = (String) map.get("param");
		   JSONObject object = JSONObject.fromObject(query);
		   for (Object k : object.keySet()) {
			   Object v = object.get(k);
			   if(StringUtils.isNotEmpty((String) v)){
				   queryMap.put(k.toString(), (String) v);
			   }
		   }
		   pageMap.setCondition(queryMap);
		   PageData pageData =  customerPushBanlanceService.getCustomerAndUserPushData(pageMap);
		   List list = pageData.getList();
		   if(null != pageData.getFooter()){
			   List footer = pageData.getFooter();
			   list.addAll(footer);
		   }
		   ExcelUtils.exportAnalysExcel(map,list);


	   }



}

