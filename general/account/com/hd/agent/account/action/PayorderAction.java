/**
 * @(#)PayorderAction.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 16, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.account.action;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.hd.agent.account.model.Payorder;
import com.hd.agent.account.model.SupplierCapital;
import com.hd.agent.account.model.TransferOrder;
import com.hd.agent.account.service.IPayorderService;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.ExportSalesOrder;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class PayorderAction extends BaseFilesAction{

	private Payorder payorder;
	
	private IPayorderService payorderService;
	private TransferOrder transferOrder;
	public Payorder getPayorder() {
		return payorder;
	}

	public void setPayorder(Payorder payorder) {
		this.payorder = payorder;
	}

	public IPayorderService getPayorderService() {
		return payorderService;
	}

	public void setPayorderService(IPayorderService payorderService) {
		this.payorderService = payorderService;
	}
	
	
	/**
	 * 显示付款单新增页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	public String showPayorderAddPage()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 显示付款单新增详情页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	public String payorderAddPage()throws Exception{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_account_purchase_payorder"));
		request.setAttribute("userName", getSysUser().getName());
		return SUCCESS;
	}
	
	/**
	 * 新增付款单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	@UserOperateLog(key="Payorder",type=2)
	public String addPayorder()throws Exception{
		//String type = request.getParameter("type");
		//if("hold".equals(type)){
		//	payorder.setStatus("1");
		//}
		//else if("save".equals(type)){
			payorder.setStatus("2");
		//}
		boolean flag = payorderService.addPayorder(payorder);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", payorder.getId());
		addJSONObject(map);
		addLog("付款单新增,编号:"+payorder.getId(), flag);
		return SUCCESS;
	}
	
	/**
	 * 显示付款单查看页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	public String showPayorderViewPage()throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		if("handle".equals(type)){
			request.setAttribute("type", type);
		}else{
			request.setAttribute("type", "view");
		}
		request.setAttribute("id", id);
		return SUCCESS;
	}
	
	/**
	 * 显示付款单查看详情页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	public String payorderViewPage()throws Exception{
		String id = request.getParameter("id");
		Payorder payorder = payorderService.getPayorderInfo(id);
		request.setAttribute("payorder", payorder);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		return SUCCESS;
	}
	
	/**
	 * 显示付款单列表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	public String showPayorderListPage()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 获取付款单列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	public String showPayorderList()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = payorderService.showPayorderList(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 删除付款单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	@UserOperateLog(key="Payorder",type=4)
	public String deletePayorder()throws Exception{
		String id = request.getParameter("id");
		boolean flag = payorderService.deletePayorder(id);
		addJSONObject("flag", flag);
		addLog("付款单删除,编号:"+id, flag);
		return SUCCESS;
	}
	
	/**
	 * 批量删除付款单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 22, 2014
	 */
	public String deleteMutPayorder()throws Exception{
		String ids = request.getParameter("ids");
		if(null!=ids){
			String[] idArr = ids.split(",");
			String succssids = "";
			String errorids = "";
			for(String id : idArr){
				boolean flag = payorderService.deletePayorder(id);
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
			addLog("付款单批量删除 成功编号："+succssids+";失败编号："+errorids, true);
		}else{
			addJSONObject("flag", false);
		}
		return "success";
	}
	
	/**
	 * 显示付款单修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	public String showPayorderEditPage()throws Exception{
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.setAttribute("type", "edit");
		return "success";
	}
	
	/**
	 * 显示付款单修改详情页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	public String payorderEditPage()throws Exception{
		String id = request.getParameter("id");
		Payorder payorder = payorderService.getPayorderInfo(id);
		request.setAttribute("payorder", payorder);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		if(null!=payorder){
			if(!"1".equals(payorder.getStatus()) && !"2".equals(payorder.getStatus())){
				return "viewSuccess";
			}else{
				return "success";
			}
		}else{
			return "addSuccess";
		}
	}
	
	/**
	 * 修改付款单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	@UserOperateLog(key="Payorder",type=3)
	public String editPayorder()throws Exception{
		//String type = request.getParameter("type");
		//if("hold".equals(type)){
		//	payorder.setStatus("1");
		//}
		boolean flag = payorderService.editPayorder(payorder);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", payorder.getId());
		addJSONObject(map);
		addLog("付款单修改,编号:"+payorder.getId(), flag);
		return "success";
	}
	
	/**
	 * 审核付款单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	@UserOperateLog(key="Payorder",type=3)
	public String auditPayorder()throws Exception{
		String id = request.getParameter("id");
		boolean flag = payorderService.auditPayorder(id);
		addJSONObject("flag", flag);
		addLog("付款单审核,编号:"+id, flag);
		return "success";
	}
	
	/**
	 * 批量审核付款单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 22, 2014
	 */
	@UserOperateLog(key="Payorder",type=3)
	public String auditMutPayorder()throws Exception{
		String ids = request.getParameter("ids");
		if(null!=ids){
			String[] idArr = ids.split(",");
			String succssids = "";
			String errorids = "";
			for(String id : idArr){
				boolean flag = payorderService.auditPayorder(id);
				if(flag){
					succssids += id+",";
				}else{
					errorids += id+",";
				}
			}
			Map map = new HashMap();
			map.put("flag", true);
			if(!"".equals(succssids)){
				succssids = succssids.substring(0, succssids.length()-1);
				map.put("succssids", "审核成功编号:"+succssids);
			}else{
				map.put("succssids", "");
			}
			if(!"".equals(errorids)){
				errorids = errorids.substring(0, succssids.length()-1);
				map.put("errorids", "审核失败编号:"+errorids);
			}else{
				map.put("errorids", "");
			}
			addJSONObject(map);
			addLog("付款单批量审核 成功编号："+succssids+";审核失败编号："+errorids, true);
		}else{
			addJSONObject("flag", false);
		}
		return "success";
	}
	
	/**
	 * 反审付款单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	@UserOperateLog(key="Payorder",type=3)
	public String oppauditPayorder()throws Exception{
		String id = request.getParameter("id");
		Map map = payorderService.oppauditPayorder(id);
		addJSONObject(map);
		addLog("付款单反审,编号:"+id, map);
		return "success";
	}
	
	/**
	 * 批量反审付款单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 22, 2014
	 */
	public String oppauditMutPayorder()throws Exception{
		String ids = request.getParameter("ids");
		if(null!=ids){
			String[] idArr = ids.split(",");
			String succssids = "";
			String errorids = "";
			for(String id : idArr){
				Map map = payorderService.oppauditPayorder(id);
				boolean flag = (Boolean) map.get("flag");
				String msg = (String) map.get("msg");
				if(flag){
					succssids += id+",";
				}else{
					errorids += msg+",";
				}
			}
			Map map = new HashMap();
			map.put("flag", true);
			if(!"".equals(succssids)){
				map.put("succssids", "反审成功编号:"+succssids);
			}else{
				map.put("succssids", "");
			}
			if(!"".equals(errorids)){
				map.put("errorids", "反审失败:"+errorids);
			}else{
				map.put("errorids", "");
			}
			addJSONObject(map);
			addLog("收款单批量反审 成功编号："+succssids+";反审失败编号："+errorids, true);
		}else{
			addJSONObject("flag", false);
		}
		return "success";
	}
	
	/**
	 * 判断是否允许反审，若供应商资金情况余额<付款金额，不允许反审，返回false,反之，返回true
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 22, 2014
	 */
	public String backOppauditFlag()throws Exception{
		String supplierid = request.getParameter("supplierid");
		//付款金额
		String amount = request.getParameter("amount");
		BigDecimal bigAmount = new BigDecimal(amount);
		boolean flag = true;
		SupplierCapital supplierCapital = payorderService.getSupplierCapitalBySupplierid(supplierid);
		if(null != supplierCapital && null != supplierCapital.getAmount()){
			if(supplierCapital.getAmount().compareTo(bigAmount) == -1){
				flag = false;
			}
		}
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 显示付款单合并页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 17, 2013
	 */
	public String showPayorderMergePage()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 显示付款单合并确认页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 17, 2013
	 */
	public String showPayorderMergeSubmitPage()throws Exception{
		String ids = request.getParameter("ids");
		List<Payorder> list = payorderService.getPayorderListByIds(ids);
		BigDecimal totalAmount = new BigDecimal(0);
		BigDecimal totalWriteoffamount = new BigDecimal(0);
		BigDecimal totalRemainderamount = new BigDecimal(0);
		if(list.size() != 0){
			for(Payorder payorder : list){
				payorder.setAmount(payorder.getAmount().setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
				payorder.setWriteoffamount(payorder.getWriteoffamount().setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
				payorder.setRemainderamount(payorder.getRemainderamount().setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
				totalAmount = totalAmount.add(payorder.getAmount());
				totalWriteoffamount = totalWriteoffamount.add(payorder.getWriteoffamount());
				totalRemainderamount = totalRemainderamount.add(payorder.getRemainderamount());
			}
		}
		request.setAttribute("list", list);
		request.setAttribute("totalAmount", totalAmount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
		request.setAttribute("totalWriteoffamount", totalWriteoffamount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
		request.setAttribute("totalRemainderamount", totalRemainderamount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
		request.setAttribute("ids", ids);
		return SUCCESS;
	}
	
	/**
	 * 付款单合并
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 17, 2013
	 */
	public String setPayorderMerge()throws Exception{
		String orderid = request.getParameter("orderid");
		String ids = request.getParameter("ids");
		String remark = request.getParameter("remark");
		boolean flag = payorderService.setPayorderMerge(ids, orderid, remark);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 付款单提交工作流
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 26, 2013
	 */
	public String submitPayorderPageProcess() throws Exception{
		String id = request.getParameter("id");
		boolean flag = payorderService.submitPayorderPageProcess(id);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 显示供应商资金情况列表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 12, 2013
	 */
	public String showSupplierAccountListPage() throws Exception{
		return "success";
	}
	
	/**
	 * 获取供应商资金情况列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 12, 2013
	 */
	public String showSupplierAccountList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = payorderService.showSupplierAccountList(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	
	/**
	 * 显示供应商资金流水页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 12, 2013
	 */
	public String showSupplierCapitalLogListPage() throws Exception{
		String id = request.getParameter("id");
		if("".equals(id)){
			id = "000000";
		}
		request.setAttribute("id", id);
		return "success";
	}

    /**
     * 付款单导出
     * @return
     * @throws Exception
     * @author lin_xx
     */
    public String exportPayorder() throws Exception{
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isflag", "true");
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
        pageMap.setCondition(map);


        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("id","编码");
        firstMap.put("businessdate", "业务日期");
        firstMap.put("supplierid", "供应商编码");
        firstMap.put("suppliername", "供应商名称");
        firstMap.put("buydeptname", "采购部门");
        firstMap.put("buyusername", "采购员");
        firstMap.put("bank","银行名称");
        firstMap.put("amount", "收款金额");
        firstMap.put("addusername", "制单人");
        firstMap.put("status", "状态");
        firstMap.put("remark", "备注");
        result.add(firstMap);

        PageData pageData = payorderService.showPayorderList(pageMap);
        List<Payorder> list = pageData.getList();
        List<Payorder> footerList = pageData.getFooter();
        for(Payorder payorder : list){
            if("2".equals(payorder.getStatus())){
                payorder.setStatus("保存");
            }else if("4".equals(payorder.getStatus())){
                payorder.setStatus("关闭");
            }
        }
        if(list.size() != 0){
            for(Payorder payorder : list){
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2 = PropertyUtils.describe(payorder);
                for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
                    if(map2.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
                        for(Map.Entry<String, Object> entry : map2.entrySet()){
                            if(fentry.getKey().equals(entry.getKey())){
                                objectCastToRetMap(retMap,entry);
                            }
                        }
                    }else{
                        retMap.put(fentry.getKey(), "");
                    }
                }
                result.add(retMap);
            }
        }
        if(null!=footerList && footerList.size() != 0){
            for(Payorder payorder : footerList){
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map3 = new HashMap<String, Object>();
                map3 = PropertyUtils.describe(payorder);
                for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
                    if(map3.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
                        for(Map.Entry<String, Object> entry : map3.entrySet()){
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
        ExcelUtils.exportExcel(result, title);

        return "success";
    }
	
	/**
	 * 获取供应商资金流水列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 12, 2013
	 */
	public String showSupplierCapitalLogList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		String supplierid = (String) map.get("supplierid");
		//000000表示未指定供应商的数据 
		if("000000".equals(supplierid)){
			map.put("supplierid", "");
		}
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = payorderService.showSupplierCapitalLogList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	
	/**
	 * 获取供应商资金情况
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 12, 2013
	 */
	public String getSupplierCapital() throws Exception{
		String id = request.getParameter("id");
		SupplierCapital supplierCapital = payorderService.getSupplierCapitalBySupplierid(id);
		if(null!=supplierCapital){
			addJSONObject("amount", supplierCapital.getAmount());
		}else{
			addJSONObject("amount", 0);
		}
		return "success";
	}
	
	/**
	 * 显示转账确认页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 13, 2013
	 */
	public String showPayOrderTransferSubmitPage() throws Exception{
		String supplierid = request.getParameter("supplierid");
		SupplierCapital supplierCapital = payorderService.getSupplierCapitalBySupplierid(supplierid);
		request.setAttribute("supplierCapital", supplierCapital);
		return "success";
	}
	
	/**
	 * 付款单转账确认
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 13, 2013
	 */
	public String setPayOrderTransfer() throws Exception{
		boolean flag = payorderService.setPayOrderTransfer(transferOrder);
		addJSONObject("flag", flag);
		return "success";
	}

	public TransferOrder getTransferOrder() {
		return transferOrder;
	}

	public void setTransferOrder(TransferOrder transferOrder) {
		this.transferOrder = transferOrder;
	}
}

