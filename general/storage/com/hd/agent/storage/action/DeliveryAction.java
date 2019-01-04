/**
 * @(#)DeliveryAction.java
 *
 * @author yezhenyu
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 29, 2014 yezhenyu 创建版本
 */
package com.hd.agent.storage.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.LogisticsCar;
import com.hd.agent.basefiles.model.LogisticsLine;
import com.hd.agent.basefiles.model.LogisticsLineCustomer;
import com.hd.agent.basefiles.service.ILogisticsService;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.storage.model.Delivery;
import com.hd.agent.storage.model.DeliveryCustomer;
import com.hd.agent.storage.model.DeliverySaleOut;
import com.hd.agent.storage.service.IDeliveryService;
import com.hd.agent.system.model.SysParam;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 
 * @author yezhenyu
 */
public class DeliveryAction extends BaseFilesAction {

	private IDeliveryService deliveryService;

    private ILogisticsService logisticsService;

	private Delivery delivery;

	public IDeliveryService getDeliveryService() {
		return deliveryService;
	}

	public void setDeliveryService(IDeliveryService deliveryService) {
		this.deliveryService = deliveryService;
	}

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

    public ILogisticsService getLogisticsService() {
        return logisticsService;
    }

    public void setLogisticsService(ILogisticsService logisticsService) {
        this.logisticsService = logisticsService;
    }

    /**
	 * 显示配送单新增页面
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 29, 2014
	 */
	public String showDeliveryAddPage() throws Exception {
		SysParam sysParam = getBaseSysParamService().getSysParam("StatusOfAddDeliverySourceList");
		if(null != sysParam){
			if("3".equals(sysParam.getPvalue())){
				request.setAttribute("customerwidget", "1");
			}else{
				request.setAttribute("customerwidget", "2");
			}
		}else{
			request.setAttribute("customerwidget", "1");
		}
		String deliveryPriority = getSysParamValue("DeliveryPriority");
		String deliveryAddAllocate = getSysParamValue("DeliveryAddAllocate");
		request.setAttribute("deliveryPriority", deliveryPriority);
		request.setAttribute("deliveryAddAllocate", deliveryAddAllocate);
		return SUCCESS;
	}

	/**
	 * 获取发货单列表
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 30, 2014
	 */
	public String getSaleOutListForDelivery() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		String ids = (String) map.get("saleoutids");
		String customerid = (String) map.get("customerid");
		String deliveryid = (String) map.get("deliveryid");

		// customerid 为搜索条件
		if (StringUtils.isNotEmpty(customerid)) {
			String[] idsArr = customerid.split(",");
			map.put("customerIdsArr", idsArr);
		}

		// saleoutids ids 不为空,处理获取deliveryAddSaleoutPage.jsp的列表
		if (StringUtils.isNotEmpty(ids)) {
			String[] idsArr = ids.split(",");
			map.put("idsArr", idsArr);
		}
		pageMap.setCondition(map);

		String deliveryAddAllocate = getSysParamValue("DeliveryAddAllocate");
		pageMap.getCondition().put("deliveryAddAllocate", deliveryAddAllocate);

		PageData pageData = deliveryService.getSaleOutListForDelivery(pageMap);
		addJSONObjectWithFooter(pageData);

		String id = request.getParameter("lineid");
		String carid = request.getParameter("carid");
		BigDecimal volume = new BigDecimal(0);
		BigDecimal weight = new BigDecimal(0);
		Integer boxnum = 0;

		if (StringUtils.isNotEmpty(id)) {
			LogisticsLine logisticsLine = logisticsService.showLineInfo(id);
			LogisticsCar car = logisticsService.getDefaultCarByLineId(carid);
			if(null != car){
				volume = (null != car.getVolume() ? car.getVolume() : BigDecimal.ZERO).setScale(4, BigDecimal.ROUND_HALF_EVEN);
				weight = (null != car.getWeight() ? car.getWeight() : BigDecimal.ZERO).setScale(2, BigDecimal.ROUND_HALF_EVEN);
				boxnum = null != car.getBoxnum() ? car.getBoxnum() : 0;
			}

			jsonResult.put("volume", volume);
			jsonResult.put("weight", weight);
			jsonResult.put("boxnum", boxnum);
			HashMap dataSum = (HashMap) pageData.getFooter().get(0);
			dataSum.put("businessdate", "装载限重");
			dataSum.put("orderid", weight + " kg");
			dataSum.put("customerid", "装载体积");
			dataSum.put("customername", volume + " m³");
		}
		return SUCCESS;
	}

	/**
	 * 获取需添加发货单列表
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 30, 2014
	 */
	public String getSaleOutListForAddDelivery() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		String ids = (String) map.get("saleoutids");
		String customerid = (String) map.get("customerid");
		String deliveryid = (String) map.get("deliveryid");
		// customerid 为搜索条件
		if (StringUtils.isNotEmpty(customerid)) {
			String[] idsArr = customerid.split(",");
			map.put("customerIdsArr", idsArr);
		}

		// saleoutids ids 不为空,处理获取deliveryAddSaleoutPage.jsp的列表
		if (StringUtils.isNotEmpty(ids)) {
			String[] idsArr = ids.split(",");
			map.put("idsArr", idsArr);
		}
		map.put("alreadyadd",true);
		pageMap.setCondition(map);

		String deliveryAddAllocate = getSysParamValue("DeliveryAddAllocate");
		pageMap.getCondition().put("deliveryAddAllocate", deliveryAddAllocate);

		PageData pageData = deliveryService.getSaleOutListForAddDelivery(pageMap);
		addJSONObjectWithFooter(pageData);

		String id = request.getParameter("lineid");
		String carid = request.getParameter("carid");
		// 获取已装车的体重和体积
		String loadedvolumeStr = request.getParameter("volume");
		String loadedweightStr = request.getParameter("weight");
		String loadedboxnumStr = request.getParameter("boxnum");
		BigDecimal loadedvolume = new BigDecimal(0);
		BigDecimal loadedweight = new BigDecimal(0);
		BigDecimal loadedboxnum = new BigDecimal(0);
		if (StringUtils.isNotEmpty(loadedvolumeStr))
			loadedvolume = new BigDecimal(loadedvolumeStr);
		if (StringUtils.isNotEmpty(loadedweightStr))
			loadedweight = new BigDecimal(loadedweightStr);
		if (StringUtils.isNotEmpty(loadedboxnumStr)) {
			loadedboxnum = new BigDecimal(loadedboxnumStr);
		}
		if (StringUtils.isNotEmpty(carid)) {
//			LogisticsLine logisticsLine = logisticsService.showLineInfo(id);
			LogisticsCar car = logisticsService.getDefaultCarByLineId(carid);

			BigDecimal volume =  car.getVolume().subtract(loadedvolume).setScale(4, BigDecimal.ROUND_HALF_EVEN);
			BigDecimal weight =car.getWeight().subtract(loadedweight).setScale(2, BigDecimal.ROUND_HALF_EVEN);
			BigDecimal boxnum = new BigDecimal(car.getBoxnum()).subtract(loadedboxnum).setScale(3, BigDecimal.ROUND_HALF_EVEN);
			
			jsonResult.put("volume", volume);
			jsonResult.put("weight", weight);
			jsonResult.put("boxnum", boxnum);
			HashMap dataSum = (HashMap) pageData.getFooter().get(0);
			dataSum.put("businessdate", "装载限重");
			dataSum.put("orderid", car.getWeight().setScale(2, BigDecimal.ROUND_HALF_UP) + " kg");
			dataSum.put("customerid", "装载体积");
			dataSum.put("customername", car.getVolume().setScale(4, BigDecimal.ROUND_HALF_UP)  + " m³");
		}
		return SUCCESS;
	}
	
	/**
	 * 添加配送单
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 3, 2014
	 */
	@SuppressWarnings("unchecked")
	@UserOperateLog(key = "Delivery", type = 2)
	public String addDeliveryList() throws Exception {
		// saleout ids
		String ids = request.getParameter("ids");
		String lineid = request.getParameter("lineid");
		String carid = request.getParameter("carid");
		String[] idList = ids.split(",");
        String start = request.getParameter("start");

		// init Delivery
		Delivery delivery = new Delivery();
		if (isAutoCreate("t_storage_logistics_delivery")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(delivery, "t_storage_logistics_delivery");
			delivery.setId(id);
		} else {
			delivery.setId("WUPS-" + CommonUtils.getDataNumberSeconds());
		}
		SysUser sysUser = getSysUser();
		delivery.setAdddeptid(sysUser.getDepartmentid());
		delivery.setAdddeptname(sysUser.getDepartmentname());
		delivery.setAdduserid(sysUser.getUserid());
		delivery.setAddusername(sysUser.getName());
		// 状态为保存
		delivery.setStatus("2");
        delivery.setStartpoint(start);

		/** 修改 增加客户代配送 **/
		/** 修改 增加调拨单 **/
		List<DeliverySaleOut> deliverySaleoutList = deliveryService.getDeliverySaleAndDeliveryListByIds(ids);
		/** 修改 增加客户代配送 **/
		/** 修改 增加调拨单 **/
		List<DeliveryCustomer> deliveryCustomerList = deliveryService.getDeliveryCustomerByIds(ids, delivery.getId(), lineid);

		LogisticsLine logisticsLine = logisticsService.showLineInfo(lineid);
		LogisticsCar car = logisticsService.showCar(carid);

		delivery.setLineid(logisticsLine.getId());
		delivery.setCarid(car.getId());
		delivery.setCartype(car.getCartype());
		delivery.setDriverid(car.getDriverid());
		delivery.setFollowid(car.getFollowid());
		delivery.setCarallowance(logisticsLine.getCarallowance());
		delivery.setCarsubsidy(logisticsLine.getCarsubsidy());

		// 设置装车次数
		Map mapTruck = new HashMap();
		mapTruck.put("carid", car.getId());
		mapTruck.put("businessdate", getDate());

		int truck = deliveryService.getTruckCount(mapTruck);
		delivery.setTruck(truck);
		if (truck == 1)
			delivery.setTrucksubsidy(car.getTruck1());
		else if (truck == 2)
			delivery.setTrucksubsidy(car.getTruck2());
		else if (truck == 3)
			delivery.setTrucksubsidy(car.getTruck3());
		else if (truck == 4)
			delivery.setTrucksubsidy(car.getTruck4());
		else if (truck >= 5)
			delivery.setTrucksubsidy(car.getTruck5());

		BigDecimal auxnum = new BigDecimal(0);
		BigDecimal auxremainder = new BigDecimal(0);
		BigDecimal boxnum = new BigDecimal(0);
		// 设置deliverid 并 计算总箱数和总件数
		for (DeliverySaleOut deliverySaleOut : deliverySaleoutList) {
			deliverySaleOut.setDeliveryid(delivery.getId());
		}
		// 设置deliverid
		for (DeliveryCustomer deliveryCustomer : deliveryCustomerList) {
			auxnum = auxnum.add(deliveryCustomer.getAuxnum());
			auxremainder = auxremainder.add(deliveryCustomer.getAuxremainder());
			boxnum = boxnum.add(deliveryCustomer.getBoxnum());
			deliveryCustomer.setDeliveryid(delivery.getId());
			deliveryCustomer.setIssaleout("1");
		}

		delivery.setAuxnum(auxnum);
		delivery.setAuxremainder(auxremainder);
		delivery.setCustomernums(deliveryCustomerList.size());
		delivery.setBoxnum(boxnum);

		// 计算家数补助
		if (deliveryCustomerList.size() > logisticsLine.getBasecustomers())
			delivery.setCustomersubsidy(logisticsLine.getSingleallowance().multiply(new BigDecimal(deliveryCustomerList.size())));
		else {
			delivery.setCustomersubsidy(logisticsLine.getBaseallowance());
		}
		boolean flag = deliveryService.addDelivery(delivery, ids, deliverySaleoutList, deliveryCustomerList);

		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", delivery.getId());
		addJSONObject(map);

		addLog("配送单新增 编号：" + delivery.getId(), map);
		return SUCCESS;
	}

	public String getDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(new Date());
	}

	/**
	 * 显示配送单列表页面
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 5, 2014
	 */
	public String showDeliveryListPage() throws Exception {
		String firstday = CommonUtils.getMonthDateStr();
		request.setAttribute("firstday", firstday);
		return SUCCESS;
	}

	/**
	 * 获取配送单列表
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 5, 2014
	 */
	public String showDeliveryList() throws Exception {
		// 获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		// map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = deliveryService.showDeliveryList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}

	/**
	 * 显示配送单页面
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 5, 2014
	 */
	public String showDeliveryPage() throws Exception {
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		request.setAttribute("id", id);
		request.setAttribute("type", type);
		return SUCCESS;
	}

	/**
	 * 显示配送单详情页面
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 7, 2014
	 */
	public String showDeliveryViewPage() throws Exception {
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		request.setAttribute("id", id);
		request.setAttribute("type", type);

		Delivery delivery = deliveryService.getDelivery(id);
		if(null != delivery) {
			request.setAttribute("delivery", delivery);
			LogisticsLine line = logisticsService.showLineInfo(delivery.getLineid());
			request.setAttribute("line", line);
			//车辆
			LogisticsCar car = logisticsService.showCar(delivery.getCarid());
			if(null != car){
				request.setAttribute("carvolume", car.getVolume());
			}else{
				request.setAttribute("carvolume", BigDecimal.ZERO);
			}
		}
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
        BigDecimal base = BigDecimal.ZERO;
        SysParam baseSysParam = getBaseSysParamService().getSysParam("collectionSurpassAmount");
        if(null != baseSysParam){
            base = new BigDecimal(baseSysParam.getPvalue());
        }
        request.setAttribute("base",base);
        BigDecimal s = BigDecimal.ZERO;
        SysParam sSysParam = getBaseSysParamService().getSysParam("surpassRate");
        if(null != sSysParam){
            s = new BigDecimal(sSysParam.getPvalue());
        }
        request.setAttribute("s",s);
        BigDecimal us = BigDecimal.ZERO;
        SysParam usSysParam = getBaseSysParamService().getSysParam("unSurpassRate");
        if(null != usSysParam){
            us = new BigDecimal(usSysParam.getPvalue());
        }
        request.setAttribute("us",us);
		return SUCCESS;
	}

	/**
	 * 显示配送单修改页面
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 7, 2014
	 */
	public String showDeliveryEditPage() throws Exception {
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		request.setAttribute("id", id);
		request.setAttribute("type", type);

		Delivery delivery = deliveryService.getDelivery(id);
		if(null != delivery){
			LogisticsLine line = logisticsService.showLineInfo(delivery.getLineid());
			request.setAttribute("delivery", delivery);
			request.setAttribute("line", line);
			//车辆
			LogisticsCar car = logisticsService.showCar(delivery.getCarid());
			if(null != car){
				request.setAttribute("carvolume", car.getVolume());
			}else{
				request.setAttribute("carvolume", BigDecimal.ZERO);
			}
		}
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		
		Map colMap = getEditAccessColumn("t_storage_logistics_delivery");
		request.setAttribute("showMap", colMap);
        BigDecimal base = BigDecimal.ZERO;
        SysParam baseSysParam = getBaseSysParamService().getSysParam("collectionSurpassAmount");
        if(null != baseSysParam){
            base = new BigDecimal(baseSysParam.getPvalue());
        }
        request.setAttribute("base",base);
        BigDecimal s = BigDecimal.ZERO;
        SysParam sSysParam = getBaseSysParamService().getSysParam("surpassRate");
        if(null != sSysParam){
            s = new BigDecimal(sSysParam.getPvalue());
        }
        request.setAttribute("s",s);
        BigDecimal us = BigDecimal.ZERO;
        SysParam usSysParam = getBaseSysParamService().getSysParam("unSurpassRate");
        if(null != usSysParam){
            us = new BigDecimal(usSysParam.getPvalue());
        }
        request.setAttribute("us",us);
		String LogisticsFollow = getSysParamValue("LogisticsFollow");
		request.setAttribute("LogisticsFollow",LogisticsFollow);
		return SUCCESS;
	}

	/**
	 * 显示配送单_交接单列表
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 7, 2014
	 */
	public String showDeliveryCustomerList() throws Exception {
		String id = request.getParameter("id");
		Map map = deliveryService.getDeliveryCustomerList(id);
		jsonResult = new HashMap();
		jsonResult.put("rows", map.get("list"));
		jsonResult.put("footer", map.get("footer"));
		return SUCCESS;
	}

	/**
	 * 显示配送单_发货单列表
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 10, 2014
	 */
	public String showDeliverySaleoutList() throws Exception {
		String id = request.getParameter("id");
		Map map = deliveryService.getDeliverySaleoutList(id);
		jsonResult = new HashMap();
		jsonResult.put("rows", map.get("list"));
		jsonResult.put("footer", map.get("footer"));
		return SUCCESS;
	}

	/**
	 * 显示配送单_商品汇总
	 *
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Nov 22, 2014
	 */
	public String showDeliveryGoodsSumData() throws Exception {
		String id = request.getParameter("id");
		List list = deliveryService.getDeliveryGoodsSumData(id);
		addJSONArray(list);
		return SUCCESS;
	}

	/**
	 * 显示发货单添加页面
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 7, 2014
	 */
	public String showDeliveryAddSaleoutPage() throws Exception {
		String lineid = request.getParameter("lineid");
		String carid = request.getParameter("carid");
		String deliveryid = request.getParameter("deliveryid");
		String ids = request.getParameter("ids");
		String loadedvolumeStr = request.getParameter("volume");
		String loadedweightStr = request.getParameter("weight");
		String loadedboxnumStr = request.getParameter("boxnum");

		request.setAttribute("lineid", lineid);
		// LogisticsCar car = logisticsMapper.getDefaultCarByLineId(carid);
		// request.setAttribute("carname", car.getName());
		request.setAttribute("carid", carid);
		request.setAttribute("deliveryid", deliveryid);
		request.setAttribute("ids", ids);
		request.setAttribute("volume", loadedvolumeStr);
		request.setAttribute("weight", loadedweightStr);
		request.setAttribute("boxnum", loadedboxnumStr);
		SysParam sysParam = getBaseSysParamService().getSysParam("StatusOfAddDeliverySourceList");
		if(null != sysParam){
			if("3".equals(sysParam.getPvalue())){
				request.setAttribute("customerwidget", "1");
			}else{
				request.setAttribute("customerwidget", "2");
			}
		}else{
			request.setAttribute("customerwidget", "1");
		}
		return SUCCESS;
	}

	/**
	 * 获取saleoutids显示配送单_交接单列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 6, 2014
	 */
	public String getDeliveryCustomerListBySaleoutids() throws Exception {
		String id = request.getParameter("id");
		String deliveryid = request.getParameter("deliveryid");
		String lineid = request.getParameter("lineid");
		Map map = deliveryService.getDeliveryCustomerListBySaleoutids(id,lineid);
		List list = (List)map.get("list");
		addJSONArray(list);
		return SUCCESS;
	}
	
	/**
	 * 编辑配送单
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 14, 2014
	 */
	@UserOperateLog(key = "Delivery", type = 3)
	public String editDelivery() throws Exception {

		String saleout = request.getParameter("saleoutList");
		String customer = request.getParameter("customerList");
		List<DeliverySaleOut> saleoutList = null;
		List<DeliveryCustomer> customerList = null;
		
		if (saleout != null) {
			saleoutList = JSONUtils.jsonStrToList(saleout, new DeliverySaleOut());
		}
		if (customer != null) {
			customerList = JSONUtils.jsonStrToList(customer, new DeliveryCustomer());
			int seq = 1;
			for (DeliveryCustomer deliveryCustomer : customerList) {
				deliveryCustomer.setDeliveryid(delivery.getId());
				deliveryCustomer.setSeq(seq++);
				if(StringUtils.isEmpty(deliveryCustomer.getIssaleout())){
					deliveryCustomer.setIssaleout("1");
				}
			}
		}

		SysUser sysUser = getSysUser();
		delivery.setModifyuserid(sysUser.getUserid());
		delivery.setModifyusername(sysUser.getName());

		Map map = deliveryService.editDelivery(delivery, saleoutList, customerList);
		map.put("status", delivery.getStatus());
		addLog("编辑配送单 编号:" + delivery.getId(), map);
		addJSONObject(map);
		return SUCCESS;
	}

	/**
	 * 审核配送单
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 16, 2014
	 */
	@UserOperateLog(key = "Delivery", type = 0)
	public String auditDeliverys() throws Exception {
		// String id = request.getParameter("id");
		String oldIdStr = request.getParameter("ids");
		
		Map retMap = deliveryService.auditDeliverys(oldIdStr);
		addLog("审核配送单 编号:" + retMap.get("id"), retMap);
		addJSONObject(retMap);

		return SUCCESS;
	}

	/**
	 * 显示审核页面
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 17, 2014
	 */
	public String showDeliveryAuditPage() throws Exception {
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		request.setAttribute("id", id);
		request.setAttribute("type", type);

		Delivery delivery = deliveryService.getDelivery(id);
		if(null != delivery) {
			LogisticsLine line = logisticsService.showLineInfo(delivery.getLineid());
			request.setAttribute("delivery", delivery);
			request.setAttribute("line", line);
			//车辆
			LogisticsCar car = logisticsService.showCar(delivery.getCarid());
			if(null != car){
				request.setAttribute("carvolume", car.getVolume());
			}else{
				request.setAttribute("carvolume", BigDecimal.ZERO);
			}
		}
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
        BigDecimal base = BigDecimal.ZERO;
        SysParam baseSysParam = getBaseSysParamService().getSysParam("collectionSurpassAmount");
        if(null != baseSysParam){
            base = new BigDecimal(baseSysParam.getPvalue());
        }
        request.setAttribute("base",base);
        BigDecimal s = BigDecimal.ZERO;
        SysParam sSysParam = getBaseSysParamService().getSysParam("surpassRate");
        if(null != sSysParam){
            s = new BigDecimal(sSysParam.getPvalue());
        }
        request.setAttribute("s",s);
        BigDecimal us = BigDecimal.ZERO;
        SysParam usSysParam = getBaseSysParamService().getSysParam("unSurpassRate");
        if(null != usSysParam){
            us = new BigDecimal(usSysParam.getPvalue());
        }
        request.setAttribute("us",us);
		return SUCCESS;
	}

	/**
	 * 保存并审核配送单
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 18, 2014
	 */
	@UserOperateLog(key = "Delivery", type = 3)
	public String saveAuditDelivery() throws Exception {
		String saleout = request.getParameter("saleoutList");
		String customer = request.getParameter("customerList");
		Map retMap = deliveryService.saveAuditDelivery(delivery, saleout, customer);
		addLog("保存并审核配送单 编号:" + retMap.get("id"), retMap);
		addJSONObject(retMap);
		return SUCCESS;
	}

	/**
	 * 验收配送单,并生成物流报表
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 18, 2014
	 */
    @UserOperateLog(key = "Delivery", type = 0)
	public String checkDelivery() throws Exception {
		String id = request.getParameter("id");
		String customer = request.getParameter("customerList");
		editDelivery();
		//物流现金报表数据
		Map map = deliveryService.doCheckDelivery(id, customer);
		boolean flag = (Boolean) map.get("flag");
		if(flag){
			//物流现金(含多跟车)报表数据
			deliveryService.addLogisticsReport(id,"1");
		}
        addLog("验收配送单 编号:" + id, flag);
		addJSONObject(map);
		return SUCCESS;
	}

	/**
	 * 批量删除配送单
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 23, 2014
	 */
	@UserOperateLog(key = "Delivery", type = 4)
	public String deleteDeliverys() throws Exception {
		String oldIdStr = request.getParameter("ids");
		String newIdStr = "";
		String[] idArr = oldIdStr.split(",");
		int invalidNum = 0, num = 0,userNum=0,lockNum=0;
		Map retMap = new HashMap();
		// 检测要删除的状态，选中记录的状态为“保存”状态下才可删除
		for (int i = 0; i < idArr.length; i++) {
			delivery = deliveryService.getDelivery(idArr[i]);
			if (delivery != null) {
				//判断数据是否被加锁,True已被加锁。Fasle未加锁。（网络互斥）
				if(!isLock("t_storage_logistics_delivery", idArr[i])){
					//判断是否被引用
					if(!canTableDataDelete("t_storage_logistics_delivery",idArr[i])){//true可以操作，false不可以操作
						userNum++;
					}else{
						if (!"2".equals(delivery.getStatus())) {
							invalidNum += 1;
						} else {
							if (StringUtils.isNotEmpty(newIdStr)) {
								newIdStr += "," + idArr[i];
							} else {
								newIdStr = idArr[i];
							}
						}
					}
				}else{
					lockNum++;
				}
			}
		}
		if (StringUtils.isNotEmpty(newIdStr)) {
			num = idArr.length - invalidNum-userNum-lockNum;
			Map map = new HashMap();
			map.put("idsArr", newIdStr.split(","));
//			 boolean flag = logisticsService.enableLineInfos(map);
			boolean flag = deliveryService.deleteDeliverys(map, newIdStr);

			retMap.put("flag", flag);
			// 添加日志内容
			addLog("删除配送单 编号:" + newIdStr, retMap);
		}
		retMap.put("invalidNum", invalidNum);
		retMap.put("userNum", userNum);
		retMap.put("lockNum", lockNum);
		retMap.put("num", num);
		addJSONObject(retMap);
		return SUCCESS;
	}

	/**
	 * 显示交接单添加页面
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 30, 2014
	 */
	public String showDeliveryAddCustomerPage() throws Exception {
		return SUCCESS;
	}

	/**
	 * 导出交接单
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jul 7, 2014
	 */
	public void deliveryCustomerExport() throws Exception {

		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
		String id = (String) map.get("deliveryid");
		pageMap.setCondition(map);
		String title = "";
		if (map.containsKey("excelTitle")) {
			title = map.get("excelTitle").toString();
		} else {
			title = "list";
		}
		if (StringUtils.isEmpty(title)) {
			title = "list";
		}
		Map retMap = deliveryService.getDeliveryCustomerList(id);
		ExcelUtils.exportExcel(exportLogisticsFilter((List) retMap.get("list"), (List) retMap.get("footer")), title);
	}

	/**
	 * 数据转换，list专程符合excel导出的数据格式(物流统计情况表)
	 * 
	 * @param list
	 * @param footerlist
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jul 5, 2014
	 */
	private List<Map<String, Object>> exportLogisticsFilter(List list, List footerlist) throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("customerid", "编号");
		firstMap.put("customername", "客户名称");
		firstMap.put("billnums", "单据数");
		firstMap.put("salesamount", "销售额");
		firstMap.put("weight", "重量");
		firstMap.put("volume", "体积");
		firstMap.put("boxnum", "箱数");
		firstMap.put("collectionamount", "收款金额");
		firstMap.put("isreceipt", "回单");
		firstMap.put("remark", "备注");
		result.add(firstMap);

		if (list.size() != 0) {
			for (Object object : list) {
				DeliveryCustomer dc = (DeliveryCustomer) object;

				dc.setIsreceipt(dc.getIsreceipt().equals("1") ? "是" : "否");
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(object);
				for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
					if (map.containsKey(fentry.getKey())) { // 如果记录中包含该Key，则取该Key的Value
						for (Map.Entry<String, Object> entry : map.entrySet()) {
							if (fentry.getKey().equals(entry.getKey())) {
								objectCastToRetMap(retMap, entry);
							}
						}
					} else {
						retMap.put(fentry.getKey(), "");
					}
				}
				result.add(retMap);
			}
		}
		if (footerlist.size() != 0) {
			Map<String, Object> retMap = new LinkedHashMap<String, Object>();
			Map<String, Object> map = (HashMap) footerlist.get(0);
			for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
				if (map.containsKey(fentry.getKey())) { // 如果记录中包含该Key，则取该Key的Value
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						if (fentry.getKey().equals(entry.getKey())) {
							objectCastToRetMap(retMap, entry);
						}
					}
				} else {
					retMap.put(fentry.getKey(), "");
				}
			}
			result.add(retMap);
		}
		return result;
	}

	/**
	 * 反审配送单
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jul 8, 2014
	 */
	@UserOperateLog(key = "Delivery", type = 3)
	public String oppauditDeliverys() throws Exception {
		String oldIdStr = request.getParameter("ids");
		Map retMap = deliveryService.oppauditDeliverys(oldIdStr);
		addLog("反审配送单 编号:" + retMap.get("id"), retMap);
		addJSONObject(retMap);

		return SUCCESS;
	}

	/**
	 * 反验收配送单
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jul 8, 2014
	 */
	@UserOperateLog(key = "Delivery", type = 0)
	public String unCheckDelivery() throws Exception {
		String id = request.getParameter("id");
		Map map = deliveryService.doUnCheckDelivery(id);
		addJSONObject(map);
		addLog("反验收配送单 编号:" + id, map.get("flag").equals(true));
		return SUCCESS;
	}
	
	/**
	 * 根据装车次数变动获取装车补助
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 15, 2014
	 */
	public String getTruckSubsidyByTruck()throws Exception{
		String truckstr = request.getParameter("truck");
		String carid = request.getParameter("carid");
		Map map = deliveryService.getTruckSubsidyByTruck(truckstr,carid);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 根据送货家数变动获取家数补助
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 15, 2014
	 */
	public String getCustomersubsidyByCustomernums()throws Exception{
		String customernumsStr = request.getParameter("customernums");
		String lineid = request.getParameter("lineid");
		Map map = deliveryService.getCustomersubsidyByCustomernums(customernumsStr,lineid);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 根据车辆编码获取车辆的装载体积和装载重量
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 28, 2014
	 */
	public String getVolumeAndWeightForCar()throws Exception{
		String carid = request.getParameter("carid");
		LogisticsCar car = logisticsService.showCar(carid);
		Map map = new HashMap();
		if(null != car){
			map.put("volume", car.getVolume());
			map.put("weight", car.getWeight());
			map.put("boxnum", car.getBoxnum());
		}
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 根据装车次数，车辆编号， 出车日期判断是否已存在审核通过的配送单true存在，false不存在
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 11, 2014
	 */
	public String isExistSameTruck()throws Exception{
		String id = request.getParameter("id");
		String truckstr = request.getParameter("truck");
		String carid = request.getParameter("carid");
		String businessdate = request.getParameter("businessdate");
		Map map = new HashMap();
		Delivery delivery = deliveryService.getDelivery(id);
		if(null != delivery){
			map.put("truck", delivery.getTruck());
		}else{
			map.put("truck", 0);
		}
		boolean flag = deliveryService.isExistSameTruck(truckstr,carid,businessdate);
		map.put("flag", flag);
		addJSONObject(map);
		return SUCCESS;
	}

    /**
     * 客户地图页面
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 13, 2016
     */
	public String deliveryAddMapPage() throws Exception {

        String lineid = request.getParameter("lineid");
        String deliveryid = request.getParameter("deliveryid");

        if(StringUtils.isNotEmpty(lineid)) {

            LogisticsLine line = logisticsService.showLineInfo(lineid);
            request.setAttribute("line", line);
        }

        if(StringUtils.isNotEmpty(deliveryid)) {

            Delivery delivery = deliveryService.getDelivery(deliveryid);
            request.setAttribute("delivery", delivery);
        }

        List<Map> locations = getBaseSalesService().getCustomerLocationList(null);
        String center = getSysParamValue("LocationPoint");
        String companyName = getSysParamValue("COMPANYNAME");

        List<LogisticsLine> lines = logisticsService.getLogisticsLines();
        List<LogisticsLineCustomer> lineCustomers = logisticsService.getLineCustomerMapList(null, true);

        request.setAttribute("locations", locations);
        request.setAttribute("center", center);
        request.setAttribute("companyName", companyName);
        request.setAttribute("lines", lines);
        request.setAttribute("lineCustomers", lineCustomers);
        return SUCCESS;
    }

    /**
     * 配送单新增页面（按地图）
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 29, 2016
     */
    public String deliveryAddByMapPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 配送单新增页面（按地图）
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 26, 2016
     */
    public String deliveryAddByMapPage2() throws Exception {

        String center = getSysParamValue("LocationPoint");
        String companyName = getSysParamValue("COMPANYNAME");
        List<LogisticsLine> lines = logisticsService.getLogisticsLines();

        pageMap.setRows(99999999);
        PageData pageData = deliveryService.getSaleOutListForDelivery(pageMap);

        List<String> customerids = new ArrayList<String>();
        List<DeliverySaleOut> list = (List<DeliverySaleOut>) pageData.getList();
        for(DeliverySaleOut ds : list) {
            String customerid = ds.getCustomerid();
            customerids.add(customerid);
        }

        List<Map> locations = getBaseSalesService().getCustomerLocationList(customerids);
        List<LogisticsLineCustomer> lineCustomers = logisticsService.getLineCustomerMapList(customerids, true);

        Map customerLocationsMap = new HashMap();
        for(Map locationMap : locations) {
            String customerid = (String) locationMap.get("customerid");
            String name = (String) locationMap.get("name");
            locationMap.remove("location");
            locationMap.remove("salesarea");
            locationMap.remove("customersort");
            locationMap.remove("salesusername");
            locationMap.put("name", StringEscapeUtils.escapeHtml3(name));
            customerLocationsMap.put(customerid, locationMap);
        }

        request.setAttribute("locations", locations);
        request.setAttribute("center", center);
        request.setAttribute("companyName", companyName);
        request.setAttribute("lines", lines);
        request.setAttribute("lineCustomers", lineCustomers);
        request.setAttribute("data", pageData.getList());
        request.setAttribute("orderStr", JSONUtils.listToJsonStr(pageData.getList()));
        request.setAttribute("customerLocations", JSONUtils.mapToJsonStr(customerLocationsMap));

        return SUCCESS;
    }

	/**
	 * 全局导出配送单列表
	 * @param
	 * @return void
	 * @throws
	 * @author wanghongteng
	 * @date 2017-12-26
	 */
	public void exportDeliveryListData()throws Exception{
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
		PageData pageData = deliveryService.showDeliveryList(pageMap);
		List list = pageData.getList();
		if(null != pageData.getFooter()){
			List footer = pageData.getFooter();
			list.addAll(footer);
		}
		ExcelUtils.exportAnalysExcel(map, list);
	}
}
