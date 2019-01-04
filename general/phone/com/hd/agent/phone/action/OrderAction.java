/**
 * @(#)OrderAction.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jan 16, 2014 zhengziyong 创建版本
 */
package com.hd.agent.phone.action;

import com.hd.agent.account.service.ISalesFreeOrderService;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.phone.service.IPhoneOrderService;
import com.hd.agent.phone.service.IPhoneService;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 手机单据相关操作
 * @author zhengziyong
 */
public class OrderAction extends BaseAction {
	
	private IPhoneService phoneService;
	private IPhoneOrderService phoneOrderService;
	private ISalesFreeOrderService salesFreeOrderService;
	public IPhoneService getPhoneService() {
		return phoneService;
	}

	public void setPhoneService(IPhoneService phoneService) {
		this.phoneService = phoneService;
	}

	public IPhoneOrderService getPhoneOrderService() {
		return phoneOrderService;
	}

	public void setPhoneOrderService(IPhoneOrderService phoneOrderService) {
		this.phoneOrderService = phoneOrderService;
	}
	
	public ISalesFreeOrderService getSalesFreeOrderService() {
		return salesFreeOrderService;
	}

	public void setSalesFreeOrderService(
			ISalesFreeOrderService salesFreeOrderService) {
		this.salesFreeOrderService = salesFreeOrderService;
	}

	/**
	 * 开票查询页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jan 16, 2014
	 */
	public String invoiceQueryPage() throws Exception{
		String userId = request.getParameter("uid");
		String u = request.getParameter("u");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String cu = CommonUtils.MD5(userId + dateFormat.format(new Date()));
		if(u == null || !u.equals(cu)){
			return INPUT;
		}
		String today = dateFormat.format(new Date());
		request.setAttribute("today", today);
		request.setAttribute("uid", userId);
		return SUCCESS;
	}
	
	/**
	 * 获取业务员客户列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jan 19, 2014
     */
	public String getCustomerList() throws Exception{
		List<Customer> customerList = phoneService.getCustomerBySalesmanId();

		addJSONArray(customerList);
		return SUCCESS;
	}
	
	/**
	 * 获取客户应收款金额，余额等信息
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 28, 2014
	 */
	public String getCustomerInvoiceList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		List list = phoneOrderService.getCustomerInvoiceList(pageMap);
		addJSONArray(list);
		return SUCCESS;
	}
	/**
	 * 获取客户抽单单据列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 29, 2014
	 */
	public String getCustomerInvoiceBillList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		String customerid = request.getParameter("customerid");
		pageMap.setCondition(map);
		List list = phoneOrderService.getCustomerInvoiceBillList(pageMap);
		Map customerMap = phoneOrderService.getCustomerInvoiceInfo(customerid);
		Map returnMap = new HashMap();
		returnMap.put("billList", list);
		returnMap.put("customerinfo", customerMap);
		addJSONObject(returnMap);
		return SUCCESS;
	}
	/**
	 * 获取申请开票回单及退货单列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jan 17, 2014
	 */
	public String getInvoiceBillList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = phoneOrderService.getReceiptAndRejectBillList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 获取单据明细
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jan 18, 2014
	 */
	public String getInvoiceBillDetail() throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		String cid = request.getParameter("cid");
		Map map = phoneOrderService.getReceiptAndRejectBillDetailList(id, cid, type);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 生成发票
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jan 19, 2014
	 */
	public String makeInvoice() throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		String cid = request.getParameter("cid");
		String uid = request.getParameter("uid");
		Map map = phoneOrderService.makeInvoice(uid, id, cid, type);
		addJSONObject(map);
		return SUCCESS;
	}

    /**
     * 获取客户销售开票数据
     * @return
     * @throws Exception
     */
    public String getCustomerInvoiceBillInfoList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        List list = phoneOrderService.getCustomerInvoiceBillInfoList(pageMap);
        addJSONArray(list);
        return SUCCESS;
    }
    /**
     * 获取客户申请开票单据列表
     * @return
     * @throws Exception
     * @author chenwei
     * @date Apr 29, 2014
     */
    public String getCustomerInvoiceBillApplayList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        String customerid = request.getParameter("customerid");
        pageMap.setCondition(map);
        List list = phoneOrderService.getCustomerInvoiceBillApplayList(pageMap);
        Map customerMap = phoneOrderService.getCustomerInvoiceInfo(customerid);
        Map returnMap = new HashMap();
        returnMap.put("billList", list);
        returnMap.put("customerinfo", customerMap);
        addJSONObject(returnMap);
        return SUCCESS;
    }

    /**
     * 手机申请开票
     * @return
     * @throws Exception
     * @author chenwei
     * @date Apr 30, 2014
     */
    @UserOperateLog(key="SalesInvoiceBill",type=2)
    public String uploadCustomerInvoiceBillApplayList() throws Exception{
        String msg = "";
        Map map = CommonUtils.changeMap(request.getParameterMap());
        Map addMap = phoneOrderService.addCustomerInvoiceBillApplayList(map);
        boolean flag = false;
        String billid = "";
        if(null!=addMap){
            flag = (Boolean) addMap.get("flag");
            if(flag){
                billid = (String) addMap.get("id");
                msg = "上传成功，生成销售发票："+billid;
            }
        }
        Map returnMap = new HashMap();
        returnMap.put("flag", flag);
        returnMap.put("msg", msg);
        addJSONObject(returnMap);
        addLog("手机申请开票 编号："+billid, flag);
        return SUCCESS;
    }
	/**
	 * 要货申请查询页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jan 21, 2014
	 */
	public String orderTrackQueryPage() throws Exception{
		String userId = request.getParameter("uid");
		String u = request.getParameter("u");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String cu = CommonUtils.MD5(userId + dateFormat.format(new Date()));
//		if(u == null || !u.equals(cu)){
//			return INPUT;
//		}
		String today = dateFormat.format(new Date());
		request.setAttribute("today", today);
		request.setAttribute("uid", userId);
		return SUCCESS;
	}
	
	/**
	 * 要货申请页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jan 21, 2014
	 */
	public String orderTrackPage() throws Exception{
		String rows = request.getParameter("rows");
		String uid = request.getParameter("uid");
		String businessdate = request.getParameter("businessdate");
		String businessdate1 = request.getParameter("businessdate1");
		String customerid = request.getParameter("customerid");
		String id = request.getParameter("id");
		request.setAttribute("rows", rows);
		request.setAttribute("uid", uid);
		request.setAttribute("businessdate", businessdate);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("customerid", customerid);
		request.setAttribute("id", id);
		return SUCCESS;
	}
	
	/**
	 * 要货申请列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jan 21, 2014
	 */
	public String getOrderTrackList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		addJSONObject(phoneOrderService.getDemandData(pageMap));
		return SUCCESS;
	}
	
	/**
	 * 订单追踪
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jan 21, 2014
	 */
	public String orderTrack() throws Exception{
		String id = request.getParameter("id");
		Map map = phoneOrderService.getOrderTrack(id);
		request.setAttribute("track", map);
		return SUCCESS;
	}
	/**
	 * 手机申请抽单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 30, 2014
	 */
	@UserOperateLog(key="SalesInvoice",type=2)
	public String uploadCustomerInvoiceBillList() throws Exception{
		String msg = "";
		Map map = CommonUtils.changeMap(request.getParameterMap());
		Map addMap = phoneOrderService.addCustomerInvoiceBillList(map);
		boolean flag = false;
		String billid = "";
		if(null!=addMap){
			flag = (Boolean) addMap.get("flag");
			if(flag){
				billid = (String) addMap.get("id");
				msg = "上传成功，生成销售发票："+billid;
			}
		}
		Map returnMap = new HashMap();
		returnMap.put("flag", flag);
		returnMap.put("msg", msg);
		addJSONObject(returnMap);
		addLog("手机申请抽单 编号："+billid, flag);
		return SUCCESS;
	}
	/**
	 * 获取客户超账期数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年5月28日
	 */
	public String getCustomerReceivablePastdueList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		List list = phoneOrderService.getCustomerReceivablePastdueList(pageMap);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 超账期原因上传
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年5月29日
	 */
	@UserOperateLog(key="PastdueReason",type=2)
	public String uploadCustomerPastdueReason() throws Exception{
		String json = request.getParameter("json");
		boolean flag = salesFreeOrderService.addSalesFreeOrderByPhone(json);
		Map map = new HashMap();
		map.put("flag", flag);
		addJSONObject(map);
		addLog("超账期原因新增 信息："+json, map);
		return "success";
	}
	/**
	 * 获取销售发票列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月16日
	 */
	public String getSalesInvoiceListByPhone() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		List list = phoneOrderService.getSalesInvoiceListByPhone(map);
		addJSONArray(list);
		return "success";
	}
    /**
     * 获取销售发票列表
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年7月16日
     */
    public String getSalesInvoiceBillListByPhone() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        List list = phoneOrderService.getSalesInvoiceBillListByPhone(map);
        addJSONArray(list);
        return "success";
    }
	/**
	 * 销售发票申请抽单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月17日
	 */
	@UserOperateLog(key="phone",type=3)
	public String uploadSalesInvoiceWrite() throws Exception{
		String billid = request.getParameter("billid");
		boolean flag = phoneOrderService.updateSalesInvoiceWrite(billid);
		Map map = new HashMap();
		map.put("flag", flag);
		addJSONObject(map);
		addLog("手机销售发票申请核销，单据号："+billid, map);
		return "success";
	}
	/**
	 * 删除回退的销售核销
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月17日
	 */
	@UserOperateLog(key="phone",type=4)
	public String deleteSalesInvoiceBack() throws Exception{
		String billid = request.getParameter("billid");
		boolean flag = phoneOrderService.deleteSalesInvoiceBack(billid);
		Map map = new HashMap();
		map.put("flag", flag);
		addJSONObject(map);
		addLog("手机销售核销删除，单据号："+billid, map);
		return "success";
	}

    /**
     * 删除回退的销售发票
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年7月17日
     */
    @UserOperateLog(key="phone",type=4)
    public String deleteSalesInvoiceBillBack() throws Exception{
        String billid = request.getParameter("billid");
        boolean flag = phoneOrderService.deleteSalesInvoiceBillBack(billid);
        Map map = new HashMap();
        map.put("flag", flag);
        addJSONObject(map);
        addLog("手机销售发票删除，单据号："+billid, map);
        return "success";
    }
}

