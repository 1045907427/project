/**
 * @(#)BasePurchaseServiceImpl.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-5-28 zhanghonghui 创建版本
 */
package com.hd.agent.purchase.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.account.dao.BeginDueMapper;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.basefiles.dao.GoodsMapper;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.purchase.dao.ArrivalOrderMapper;
import com.hd.agent.purchase.dao.BuyOrderMapper;
import com.hd.agent.purchase.dao.PlannedOrderMapper;
import com.hd.agent.purchase.dao.ReturnOrderMapper;
import com.hd.agent.purchase.model.ArrivalOrder;
import com.hd.agent.purchase.model.ArrivalOrderDetail;
import com.hd.agent.purchase.model.BuyOrder;
import com.hd.agent.purchase.model.BuyOrderDetail;
import com.hd.agent.purchase.model.PlannedOrder;
import com.hd.agent.purchase.model.PlannedOrderDetail;
import com.hd.agent.purchase.model.ReturnOrder;
import com.hd.agent.purchase.model.ReturnOrderDetail;
import com.hd.agent.purchase.service.IBasePurchaseService;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class BasePurchaseServiceImpl extends BaseFilesServiceImpl implements
	IBasePurchaseService {
	protected ArrivalOrderMapper arrivalOrderMapper;
	protected BuyOrderMapper buyOrderMapper;
	protected PlannedOrderMapper plannedOrderMapper;	
	protected ReturnOrderMapper returnOrderMapper;
	protected BeginDueMapper beginDueMapper;
	protected GoodsMapper goodsMapper;
	
	public GoodsMapper getGoodsMapper() {
		return goodsMapper;
	}

	public void setGoodsMapper(GoodsMapper goodsMapper) {
		this.goodsMapper = goodsMapper;
	}

	public ArrivalOrderMapper getArrivalOrderMapper() {
		return arrivalOrderMapper;
	}

	public void setArrivalOrderMapper(ArrivalOrderMapper arrivalOrderMapper) {
		this.arrivalOrderMapper = arrivalOrderMapper;
	}
	public BuyOrderMapper getBuyOrderMapper() {
		return buyOrderMapper;
	}

	public void setBuyOrderMapper(BuyOrderMapper buyOrderMapper) {
		this.buyOrderMapper = buyOrderMapper;
	}
	public PlannedOrderMapper getPlannedOrderMapper() {
		return plannedOrderMapper;
	}

	public void setPlannedOrderMapper(PlannedOrderMapper plannedOrderMapper) {
		this.plannedOrderMapper = plannedOrderMapper;
	}

	public ReturnOrderMapper getReturnOrderMapper() {
		return returnOrderMapper;
	}

	public void setReturnOrderMapper(ReturnOrderMapper returnOrderMapper) {
		this.returnOrderMapper = returnOrderMapper;
	}

	public BeginDueMapper getBeginDueMapper() {
		return beginDueMapper;
	}

	public void setBeginDueMapper(BeginDueMapper beginDueMapper) {
		this.beginDueMapper = beginDueMapper;
	}

	/**
	 * 调用业务编码规则生成编码，如果编码规则没有，则生成时间串
	 * @param object
	 * @param tablename
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-18
	 */
	public String getBillNumber(Object object,String tablename) throws Exception{
		String id=null;
		if (isAutoCreate(tablename)) {
			// 获取自动编号
			id= getAutoCreateSysNumbderForeign(object, tablename);
		}else{
	    	SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");	//比uuid_short 多一位
	    	id="ST"+sf.format(new Date());
		}
		return id;
	}
	/**
	 * 获取采购计划单及其明细，没有经过字段权限和数据权限处理
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-18
	 */
	public PlannedOrder showBasePurePlannedOrderAndDetail(String id){
		PlannedOrder plannedOrder=null;
		plannedOrder=plannedOrderMapper.getPlannedOrder(id);
		if(plannedOrder!=null && StringUtils.isNotEmpty(plannedOrder.getId())){
			Map map=new HashMap();
			map.put("orderid", plannedOrder.getId());
			List<PlannedOrderDetail> list= plannedOrderMapper.getPlannedOrderDetailListBy(map);
			plannedOrder.setPlannedOrderDetailList(list);
		}else{
			plannedOrder.setPlannedOrderDetailList(new ArrayList<PlannedOrderDetail>());
		}
		return plannedOrder; 
	}
	
	/**
	 * 根据编码获取采购订单明细
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-18
	 */
	public PlannedOrderDetail showBasePurePlannedOrderDetail(String id) throws Exception{
		return plannedOrderMapper.getPlannedOrderDetail(id);
	}
	/**
	 * 设置采购计划单引用状态
	 * @param id 编码
	 * @param isrefer 是否引用，1引用，0未引用
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-18
	 */
	public boolean updateBasePlannedOrderRefer(String id,String isrefer) throws Exception{
		if(null==id || "".equals(id.trim())){
			return false;
		}
		boolean flag=false;
		PlannedOrder order=new PlannedOrder();
		order.setIsrefer(isrefer);
		order.setId(id);
		flag = plannedOrderMapper.updatePlannedOrderStatus(order)>0;
		return flag;
	}
	/**
	 * 获取采购订单及其明细，没有经过字段权限和数据权限处理
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-18
	 */
	public BuyOrder showBasePureBuyOrderAndDetail(String id) throws Exception{
		BuyOrder buyOrder=null;
		buyOrder=buyOrderMapper.getBuyOrder(id);
		if(buyOrder!=null && StringUtils.isNotEmpty(buyOrder.getId())){
			Map map=new HashMap();
			map.put("orderid", buyOrder.getId());
			List<BuyOrderDetail> list= buyOrderMapper.getBuyOrderDetailListByOrderid(id);
			buyOrder.setBuyOrderDetailList(list);
		}else{
			buyOrder.setBuyOrderDetailList(new ArrayList<BuyOrderDetail>());
		}
		return buyOrder; 
	}
	/**
	 * 根据采购订单编码获取采购订单明细列表
	 * @param orderid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-18
	 */
	public List showBasePureBuyOrderDetailListByOrderId(String orderid) throws Exception{
		orderid=orderid.trim();
		Map map=new HashMap();
		map.put("orderid", orderid);
		return buyOrderMapper.getBuyOrderDetailListBy(map);
	}
	/**
	 * 设置采购进货单引用状态
	 * @param id 编码
	 * @param isrefer 是否引用，1引用，0未引用
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-18
	 */
	public boolean updateBaseArrivalOrderRefer(String id,String isrefer) throws Exception{
		if(null==id || "".equals(id.trim())){
			return false;
		}
		boolean flag=false;
		ArrivalOrder aOrder=new ArrivalOrder();
		aOrder.setIsrefer(isrefer);
		aOrder.setId(id);
		flag = arrivalOrderMapper.updateArrivalOrderStatus(aOrder)>0;
		return flag;
	}
	/**
	 * 根据上游单据编码获取采购订单 
	 * @param billno
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-18
	 */
	public ArrivalOrder showBaseArrivalOrderByBillno(String billno) throws Exception{
		Map map=new HashMap();
		map.put("billno", billno.trim());
		return arrivalOrderMapper.getArrivalOrderBy(map);				
	}
	/**
	 * 采购退货通知单明细分页列表，不判断权限
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-14
	 */
	public List showBasePureReturnOrderDetailListByOrderId(String orderid) throws Exception{
		orderid=orderid.trim();
		Map map=new HashMap();
		map.put("orderid", orderid);
		return returnOrderMapper.getReturnOrderDetailListBy(map);
	}
	
	/**
	 * 设置采购进货单引用状态
	 * @param id 编码
	 * @param isrefer 是否引用，1引用，0未引用
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-18
	 */
	public boolean updateBaseReturnOrderRefer(String id,String isrefer) throws Exception{
		if(null==id || "".equals(id.trim())){
			return false;
		}
		boolean flag=false;
		ReturnOrder order=new ReturnOrder();
		order.setIsrefer(isrefer);
		order.setId(id);
		flag = returnOrderMapper.updateReturnOrderStatus(order)>0;
		return flag;
	}
	/**
	 * 获取采购退货通知单及其明细，没有经过字段权限和数据权限处理
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-18
	 */
	public ReturnOrder showBasePureReturnOrderAndDetail(String id) throws Exception{
		ReturnOrder returnOrder=null;
		returnOrder=returnOrderMapper.getReturnOrder(id);
		if(returnOrder!=null && StringUtils.isNotEmpty(returnOrder.getId())){

			List<ReturnOrderDetail> list= returnOrderMapper.getReturnOrderDetailListByOrderid(id);
			returnOrder.setReturnOrderDetailList(list);
		}else{
			returnOrder.setReturnOrderDetailList(new ArrayList<ReturnOrderDetail>());
		}
		return returnOrder; 
	}
	
	/**
	 * 获取采购进货单及其明细，没有经过字段权限和数据权限处理
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-18
	 */
	public ArrivalOrder showBasePureArrivalOrderAndDetail(String id) throws Exception{
		ArrivalOrder arrivalOrder=null;
		arrivalOrder=arrivalOrderMapper.getArrivalOrder(id);
		if(arrivalOrder!=null && StringUtils.isNotEmpty(arrivalOrder.getId())){

			List<ArrivalOrderDetail> list= arrivalOrderMapper.getArrivalOrderDetailListByOrderid(id);
			arrivalOrder.setArrivalOrderDetailList(list);
		}else{
			arrivalOrder.setArrivalOrderDetailList(new ArrayList<ArrivalOrderDetail>());
		}
		return arrivalOrder; 		
	}
}

