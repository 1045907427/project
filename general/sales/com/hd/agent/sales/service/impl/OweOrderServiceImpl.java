/**
 * @(#)OwnOrderServiceImpl.java
 *
 * @author wanghongteng
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月21日 wanghongteng 创建版本
 */
package com.hd.agent.sales.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.accesscontrol.model.Operate;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.dao.SysUserMapper;
import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.dao.OrderDetailMapper;
import com.hd.agent.sales.dao.OrderMapper;
import com.hd.agent.sales.model.DispatchBill;
import com.hd.agent.sales.model.DispatchBillDetail;
import com.hd.agent.sales.model.Order;
import com.hd.agent.sales.model.OrderDetail;
import com.hd.agent.sales.model.OweOrder;
import com.hd.agent.sales.model.OweOrderDetail;
import com.hd.agent.sales.service.IOweOrderService;
import com.hd.agent.storage.dao.StorageSummaryMapper;
import com.hd.agent.storage.model.StorageSummary;

/**
 * 
 * 
 * @author wanghongteng
 */
/**
 * @author Administrator
 *
 */
public class OweOrderServiceImpl extends BaseSalesServiceImpl implements IOweOrderService{
	
	private OrderMapper salesOrderMapper;
	private OrderDetailMapper salesOrderDetailMapper;
    private StorageSummaryMapper storageSummaryMapper;
    private SysUserMapper sysUserMapper;
    
	public SysUserMapper getSysUserMapper() {
		return sysUserMapper;
	}

	public void setSysUserMapper(SysUserMapper sysUserMapper) {
		this.sysUserMapper = sysUserMapper;
	}

	public StorageSummaryMapper getStorageSummaryMapper() {
		return storageSummaryMapper;
	}

	public void setStorageSummaryMapper(StorageSummaryMapper storageSummaryMapper) {
		this.storageSummaryMapper = storageSummaryMapper;
	}


	public OrderMapper getSalesOrderMapper() {
		return salesOrderMapper;
	}

	public void setSalesOrderMapper(OrderMapper salesOrderMapper) {
		this.salesOrderMapper = salesOrderMapper;
	}

	public OrderDetailMapper getSalesOrderDetailMapper() {
		return salesOrderDetailMapper;
	}

	public void setSalesOrderDetailMapper(OrderDetailMapper salesOrderDetailMapper) {
		this.salesOrderDetailMapper = salesOrderDetailMapper;
	}

	/**
     *  获取欠货单列表
     *
     * @throws Exception
     * @author wanghongteng
     * @date 9,21,2015
     */
	@Override
	public PageData getOweOrderList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_sales_owe_order", "t");
		pageMap.setDataSql(dataSql);
		int count = getSalesOweOrderMapper().getSalesOweOrderCount(pageMap);
		List<OweOrder> list = getSalesOweOrderMapper().getSalesOweOrderList(pageMap);
		for(OweOrder oweOrder : list){
			Customer customer;
			if (StringUtils.isNotEmpty(oweOrder.getCustomerid())) {
				customer = getCustomerByID(oweOrder.getCustomerid());
				if(null!=customer){
					oweOrder.setCustomername(customer.getName());
				}
			}
			String salesuser=oweOrder.getSalesuser();
			Personnel personnel=getPersonnelById(salesuser);
			if(null!=personnel){
				oweOrder.setSalesusername(personnel.getName());
			}
			Personnel indoorPerson = getPersonnelById(oweOrder.getIndooruserid());
            if (null != indoorPerson) {
            	oweOrder.setIndoorusername(indoorPerson.getName());
            }
			//销售部门
			DepartMent departMent = getDepartMentById(oweOrder.getSalesdept());
			if(null != departMent){
				oweOrder.setSalesdeptname(departMent.getName());
			}
		}
		PageData pageData = new PageData(count, list, pageMap);
		return pageData;
	}

	@Override
	public OweOrder getOweOrder(String id) throws Exception {
		OweOrder oweOrder=getSalesOweOrderMapper().getOweOrder(id);
		List<OweOrderDetail> detailList=getSalesOweOrderMapper().getOweOrderDetail(id);
		Customer customer;
		if (StringUtils.isNotEmpty(oweOrder.getCustomerid())) {
			customer = getCustomerByID(oweOrder.getCustomerid());
			if(null!=customer){
				oweOrder.setCustomername(customer.getName());
			}
		    String salesuser=oweOrder.getSalesuser();
			Personnel personnel=getPersonnelById(salesuser);
			if(null!=personnel){
				oweOrder.setSalesusername(personnel.getName());
			}

		}
		for(OweOrderDetail oweOrderDetail : detailList){
			GoodsInfo goodsInfo =getAllGoodsInfoByID(oweOrderDetail.getGoodsid());
			oweOrderDetail.setGoodsInfo(goodsInfo);
			BigDecimal useable = new BigDecimal("0");
			if (StringUtils.isNotEmpty(oweOrder.getStorageid())) {
				StorageSummary storageSummary=storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid( oweOrderDetail.getGoodsid(),oweOrder.getStorageid());
				if(storageSummary!=null){
					useable = storageSummary.getUsablenum();
				}
			} else {
				if(storageSummaryMapper.getStorageSummarySumByGoodsid(oweOrderDetail.getGoodsid())!=null){
				useable = storageSummaryMapper.getStorageSummarySumByGoodsid(oweOrderDetail.getGoodsid()).getUsablenum();
				}
			}
			if("0".equals(oweOrder.getStatus())){
				if(useable.intValue()>0){
					if (oweOrderDetail.getUnitnum().intValue() <= useable.intValue()) {
						oweOrderDetail.setOrdernum(oweOrderDetail.getUnitnum());
					}
					else{
						oweOrderDetail.setOrdernum(useable);
					}
				}
			}

			oweOrderDetail.setUseable(useable);
		}
		oweOrder.setDetailList(detailList);
		return oweOrder;
	}

	@Override
	public Map OweOrderAddOrder(String id) throws Exception {
		Map map = new HashMap();
		boolean flag = false;
		String msg="",errmsg="";
		OweOrder oweOrder = getSalesOweOrderMapper().getOweOrder(id);
		List<OweOrderDetail> list = getSalesOweOrderMapper().getOweOrderDetail(id);
		List<OweOrderDetail>  detailList = new ArrayList();
		List<OweOrderDetail> owedetailList=new ArrayList();
		for (OweOrderDetail oweOrderDetail : list) {//将原欠单分离，可用量满足的加入生成销售单list，不满足的加入重新生成欠单list
			BigDecimal useable = new BigDecimal("0");
			if (StringUtils.isNotEmpty(oweOrder.getStorageid())) {
				StorageSummary storageSummary=storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid(oweOrderDetail.getGoodsid(),oweOrder.getStorageid());
				if(storageSummary!=null) {
					useable = storageSummary.getUsablenum();
				}
			} else {
				StorageSummary storageSummary=storageSummaryMapper.getStorageSummarySumByGoodsid(oweOrderDetail.getGoodsid());
				if(storageSummary!=null) {
					useable =storageSummary.getUsablenum();
				}
			}

			if (StringUtils.isNotEmpty(oweOrder.getStorageid())) {
				StorageSummary storageSummary=storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid( oweOrderDetail.getGoodsid(),oweOrder.getStorageid());
				if(storageSummary!=null)
					useable = storageSummary.getUsablenum();
			} else {
				if(storageSummaryMapper.getStorageSummarySumByGoodsid(oweOrderDetail.getGoodsid())!=null){
					useable = storageSummaryMapper.getStorageSummarySumByGoodsid(oweOrderDetail.getGoodsid()).getUsablenum();
				}
			}
			if(oweOrderDetail.getOrdernum().compareTo(new BigDecimal("0"))>0){
				if(useable.compareTo(oweOrderDetail.getOrdernum())>=0){
					if(oweOrderDetail.getUnitnum().compareTo(oweOrderDetail.getOrdernum())>0){
						OweOrderDetail cloneOrderDetail=(OweOrderDetail) BeanUtils.cloneBean(oweOrderDetail);
						cloneOrderDetail.setUnitnum(cloneOrderDetail.getOrdernum());
						setOweOrderDetailAux(cloneOrderDetail);
						detailList.add(cloneOrderDetail);

						OweOrderDetail cloneoweOrderDetail=(OweOrderDetail) BeanUtils.cloneBean(oweOrderDetail);
						cloneoweOrderDetail.setUnitnum(cloneoweOrderDetail.getUnitnum().subtract(cloneoweOrderDetail.getOrdernum()));
						setOweOrderDetailAux(cloneoweOrderDetail);
						owedetailList.add(cloneoweOrderDetail);
					}else{
						detailList.add(oweOrderDetail);
					}
				}else{
					if(StringUtils.isEmpty(errmsg)){
						errmsg=oweOrderDetail.getGoodsid();
					}else{
						errmsg += ","+oweOrderDetail.getGoodsid();
					}
					owedetailList.add(oweOrderDetail);
				}
			}else{
				owedetailList.add(oweOrderDetail);
			}
		}
		if (detailList.size() > 0) {//生成销售订单明细list不为0时，进行生成操作
			if (oweOrder.getStatus().equals("0")) {//欠单状态为0（未生成订单时）进行生成操作
				Order order = new Order();
				List<OrderDetail> orderDetailList = new ArrayList();
				if (isAutoCreate("t_sales_order")) {
					// 获取自动编号
					String orderid = getAutoCreateSysNumbderForeign(order, "t_sales_order");
					order.setId(orderid);
				} else {
					order.setId("DD-" + CommonUtils.getDataNumberSendsWithRand());
				}
				order.setBusinessdate(CommonUtils.getTodayDataStr());
				order.setStatus("2");
				order.setRemark(oweOrder.getRemark());
				SysUser sysUser = getSysUser();
				order.setAdddeptid(sysUser.getDepartmentid());
				order.setAdddeptname(sysUser.getDepartmentname());
				order.setAdduserid(sysUser.getUserid());
				order.setAddusername(sysUser.getName());
				order.setAddtime(new Date());
				order.setCustomerid(oweOrder.getCustomerid());
				order.setPcustomerid(oweOrder.getPcustomerid());
				order.setCustomersort(oweOrder.getCustomersort());
				order.setHandlerid(oweOrder.getHandlerid());
				order.setSalesarea(oweOrder.getSalesarea());
				order.setSalesdept(oweOrder.getSalesdept());
				order.setSalesuser(oweOrder.getSalesuser());
				order.setStorageid(oweOrder.getStorageid());
				order.setIndooruserid(oweOrder.getIndooruserid());
				for (OweOrderDetail oweOrderDetail : detailList) {
					OrderDetail orderDetail = new OrderDetail();
					orderDetail.setOrderid(order.getId());
					orderDetail.setGoodsid(oweOrderDetail.getGoodsid());
					orderDetail.setGroupid(oweOrderDetail.getGroupid());
					orderDetail.setGoodssort(oweOrderDetail.getGoodssort());
					orderDetail.setBrandid(oweOrderDetail.getBrandid());
					orderDetail.setBranduser(oweOrderDetail.getBranduser());
					orderDetail.setBranddept(oweOrderDetail.getBranddept());
					orderDetail.setSupplierid(oweOrderDetail.getSupplierid());
					orderDetail.setSupplieruser(oweOrderDetail.getSupplieruser());
					// orderDetail.setIsdiscount(oweOrderDetail.geti);
					// orderDetail.setIsbranddiscount(isbranddiscount);
					orderDetail.setUnitid(oweOrderDetail.getUnitid());
					orderDetail.setUnitname(oweOrderDetail.getUnitname());
					orderDetail.setFixnum(oweOrderDetail.getUnitnum());
					orderDetail.setUnitnum(oweOrderDetail.getUnitnum());
					orderDetail.setAuxunitid(oweOrderDetail.getAuxunitid());
					orderDetail.setAuxunitname(oweOrderDetail.getAuxunitname());
					orderDetail.setAuxnum(oweOrderDetail.getAuxnum());
					orderDetail.setOvernum(oweOrderDetail.getOvernum());
					orderDetail.setAuxnumdetail(oweOrderDetail.getAuxnumdetail());
					orderDetail.setTotalbox(oweOrderDetail.getTotalbox());
					orderDetail.setFixprice(oweOrderDetail.getFixprice());
					orderDetail.setOffprice(oweOrderDetail.getOffprice());
					orderDetail.setTaxprice(oweOrderDetail.getTaxprice());
					orderDetail.setTaxamount(oweOrderDetail.getTaxamount());
					orderDetail.setNotaxprice(oweOrderDetail.getNotaxprice());
					orderDetail.setNotaxamount(oweOrderDetail.getNotaxamount());
					orderDetail.setCostprice(oweOrderDetail.getCostprice());
					orderDetail.setTaxtype(oweOrderDetail.getTaxtype());
					orderDetail.setTax(oweOrderDetail.getTax());
					orderDetail.setRemark(oweOrderDetail.getRemark());
					orderDetail.setDeliverytype(oweOrderDetail.getDeliverytype());
					orderDetail.setDeliverydate(oweOrderDetail.getDeliverydate());
					orderDetail.setStorageid(oweOrderDetail.getStorageid());
					// orderDetail.setBatchno();
					// orderDetail.setExpirationdate(oweOrderDetail);
					orderDetail.setBillno(oweOrderDetail.getBillno());
					orderDetail.setBilldetailno(oweOrderDetail.getBilldetailno());
					orderDetail.setOldprice(oweOrderDetail.getOldprice());
					orderDetail.setDemandprice(oweOrderDetail.getDemandprice());
					orderDetail.setDemandamount(oweOrderDetail.getDemandamount());
					orderDetail.setSeq(oweOrderDetail.getSeq());
					orderDetailList.add(orderDetail);
				}

				for (OrderDetail detail : orderDetailList) {
					salesOrderDetailMapper.addOrderDetail(detail);
				}
				flag = salesOrderMapper.addOrder(order) > 0;
				if(flag){
					msg="欠单编号:"+id+"生成销售订单成功";
				}

				if(flag&&owedetailList.size()>0){//销售订单生成成功，并且重新生成欠单的list不为0时，将剩余商品重新生成欠单
					if (isAutoCreate("t_sales_owe_order")) {
						// 获取自动编号
						String newOweOrderid = getAutoCreateSysNumbderForeign(order, "t_sales_owe_order");
						oweOrder.setId(newOweOrderid);
					} else {
						oweOrder.setId("QHDD-" + CommonUtils.getDataNumberSendsWithRand());
					}
					for(OweOrderDetail oweOrderDetail :owedetailList){//明细重新计算
						oweOrderDetail.setId(null);
						oweOrderDetail.setOrderid(oweOrder.getId());
						getSalesOweOrderMapper().addOweOrderDetail(oweOrderDetail);
					}
					oweOrder.setAddtime(new Date());
					flag=getSalesOweOrderMapper().addOweOrder(oweOrder)>0;
					if(StringUtils.isNotEmpty(errmsg)){
						msg = msg + ",可用量已发生变化,订单中商品："+errmsg+"生成数量已大于实时可用量！";
					}
					msg+="本订单中有商品可用量不充足，已重新生成销售欠单。销售欠单编号:"+oweOrder.getId();
				}
				if (flag) {
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Calendar calendar = Calendar.getInstance();
					String businessdate = dateFormat.format(calendar.getTime());
					getSalesOweOrderMapper().auditOweOrder(id, sysUser.getUserid(), sysUser.getName(), new Date(), businessdate);
					for (OweOrderDetail oweOrderDetail : detailList) {
						getSalesOweOrderMapper().editOrdernumById(oweOrderDetail.getId(),oweOrderDetail.getOrdernum());
					}
				}
				map.put("msg",msg);
				map.put("flag", flag);
				return map;
			} else {//欠单状态为1（已生成订单时）不进行生成操作，返回false
				map.put("msg","欠单编号："+id+"已生成销售订单，不能重复生成");
				map.put("flag", flag);
				return map;
			}
		} else {//生成列表为0则不进行任何操作，返回false
			map.put("msg","欠单编号："+id+"中无商品可用量充足，无法生成销售订单");
			map.put("flag", flag);
			return map;
		}
	}

	public void setOweOrderDetailAux(OweOrderDetail oweOrderDetail) throws  Exception{
		BigDecimal unitnum = oweOrderDetail.getUnitnum();
		String goodsid = oweOrderDetail.getGoodsid();
		GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
		BigDecimal boxnum = goodsInfo.getBoxnum();
		BigDecimal overnum = unitnum.remainder(boxnum);
		oweOrderDetail.setOvernum(overnum);
		BigDecimal auxnum =  unitnum.subtract(overnum).divide(boxnum,2,BigDecimal.ROUND_HALF_UP);
		oweOrderDetail.setAuxnum(auxnum);
		oweOrderDetail.setAuxnumdetail(auxnum.intValue()+oweOrderDetail.getAuxunitname()+overnum.intValue()+oweOrderDetail.getUnitname());
		oweOrderDetail.setTotalbox(unitnum.divide(boxnum,2,BigDecimal.ROUND_HALF_UP));
		oweOrderDetail.setTaxamount(unitnum.multiply(oweOrderDetail.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
		oweOrderDetail.setNotaxamount(unitnum.multiply(oweOrderDetail.getNotaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
		oweOrderDetail.setTax(oweOrderDetail.getTaxamount().subtract(oweOrderDetail.getNotaxamount()));
	}

	@Override
	public Map updateOweOrder(OweOrder oweOrder) throws Exception {
		String id=oweOrder.getId();
		boolean flag=false;
		boolean closeflag=false;
		String msg="";
		if("0".equals(oweOrder.getStatus())){
			getSalesOweOrderMapper().deleteOweOrderDetail(id);
			List<OweOrderDetail> oweOrderDetailList=oweOrder.getDetailList();
			if(null!=oweOrderDetailList&&0!=oweOrderDetailList.size()){
				for(OweOrderDetail oweOrderDetail : oweOrderDetailList){
					oweOrderDetail.setBillno(id);
					getSalesOweOrderMapper().addOweOrderDetail(oweOrderDetail);
					flag=true;
				}
			}else{
				flag=getSalesOweOrderMapper().closeOweOrder(id,new Date())>0;
				msg="修改成功，订单中无明细商品，订单已关闭";
				closeflag=true;
			}
		}else{
			msg="该订单已生成销售订单或已关闭！";
		}
		Map map=new HashMap();
		map.put("flag",flag);
		map.put("msg",msg);
		map.put("closeflag",closeflag);
		return map;
	}

	/**
     *  删除销售欠货单
     *
     * @throws Exception
     * @author wanghongteng
     * @date 9,21,2015
     */
	@Override
	public Map deleteOweOrder(String id) throws Exception {
		OweOrder oweOrder=getSalesOweOrderMapper().getOweOrder(id);
		boolean flag=false;
		if(oweOrder.getStatus().equals("0")||oweOrder.getStatus().equals("4")){
		    getSalesOweOrderMapper().deleteOweOrderDetail(id);
		    flag=getSalesOweOrderMapper().deleteOweOrder(id)>0;
		}
		Map map = new HashMap();
		map.put("id", id);
		map.put("flag", flag);
		return map;
	}
	
	 /**
     *  关闭销售欠货单
     *
     * @throws Exception
     * @author wanghongteng
     * @date 9,21,2015
     */
	@Override
	public Map closeOweOrder(String id) throws Exception {
		OweOrder oweOrder=getSalesOweOrderMapper().getOweOrder(id);
		boolean flag=false;
		if(oweOrder.getStatus().equals("0")){
			flag=getSalesOweOrderMapper().closeOweOrder(id,new Date())>0;
		}
		Map map = new HashMap();
		map.put("id", id);
		map.put("flag", flag);
		return map;
	}
	
	 /**
     *  发货通知单审核后新增销售欠货单
     *
     * @throws Exception
     * @author wanghongteng
     * @date 9,21,2015
     */
	@Override
	public Map addOweOrderByDispatchBill(String id) throws Exception {
		 List i=new ArrayList();
		boolean flag=false;
		String newId="";
		Map map = new HashMap();
		String msg="";
		DispatchBill dispatchBill=getSalesDispatchBillMapper().getDispatchBill(id);
		List dispatchBillDetailList = getSalesDispatchBillMapper().getDispatchBillDetailListByBill(id);
		List<OrderDetail> orderDetaillist=getSalesOrderDetailMapper().getOrderDetailByOrder(dispatchBill.getBillno(), null);
		//生成欠货单
   	    List list=new ArrayList();
		if (getSalesOweOrderMapper().getOweOrderDetailByBillno(id) != 0) {
			msg = "该订单已生成过销售欠单!";
		}
		else {
			for (OrderDetail orderDetail : orderDetaillist) {
				if (orderDetail.getNosendnummain().compareTo(BigDecimal.ZERO)==1) {
					orderDetail.setUnitnum(orderDetail.getNosendnummain());
					    list.add(orderDetail);
				}
			}
			if (list.size() > 0) {
				OweOrder oweOrder = new OweOrder();
				if (isAutoCreate("t_sales_owe_order")) {
					// 获取自动编号
					String oweOrderId = getAutoCreateSysNumbderForeign(oweOrder, "t_sales_owe_order");
					oweOrder.setId(oweOrderId);
				} else {
					oweOrder.setId("QHDD-" + CommonUtils.getDataNumberSendsWithRand());
				}
				oweOrder.setBusinessdate(CommonUtils.getTodayDataStr());
				oweOrder.setStatus("0");
				oweOrder.setRemark(dispatchBill.getRemark());
				SysUser sysUser = getSysUser();
				oweOrder.setAdddeptid(sysUser.getDepartmentid());
				oweOrder.setAdddeptname(sysUser.getDepartmentname());
				oweOrder.setAdduserid(sysUser.getUserid());
				oweOrder.setAddusername(sysUser.getName());
				oweOrder.setAddtime(new Date());
				oweOrder.setCustomerid(dispatchBill.getCustomerid());
				oweOrder.setPcustomerid(dispatchBill.getPcustomerid());
				oweOrder.setCustomersort(dispatchBill.getCustomersort());
				oweOrder.setHandlerid(dispatchBill.getHandlerid());
				oweOrder.setSalesarea(dispatchBill.getSalesarea());
				oweOrder.setSalesdept(dispatchBill.getSalesdept());
				oweOrder.setSalesuser(dispatchBill.getSalesuser());
				oweOrder.setStorageid(dispatchBill.getStorageid());
				oweOrder.setBillno(dispatchBill.getBillno());
				oweOrder.setIndooruserid(dispatchBill.getIndooruserid());
				List<OweOrderDetail> detaillist = new ArrayList();
				for (Object obj : list) {
					OrderDetail orderDetail = (OrderDetail) obj;
					OweOrderDetail oweOrderDetail = new OweOrderDetail();
					oweOrderDetail.setOrderid(oweOrder.getId());
					oweOrderDetail.setGoodsid(orderDetail.getGoodsid());
					oweOrderDetail.setGroupid(orderDetail.getGroupid());
					oweOrderDetail.setGoodssort(orderDetail.getGoodssort());
					oweOrderDetail.setBrandid(orderDetail.getBrandid());
					oweOrderDetail.setBranduser(orderDetail.getBranduser());
					oweOrderDetail.setBranddept(orderDetail.getBranddept());
					oweOrderDetail.setSupplierid(orderDetail.getSupplierid());
					oweOrderDetail.setSupplieruser(orderDetail.getSupplieruser());
					oweOrderDetail.setUnitid(orderDetail.getUnitid());
					oweOrderDetail.setUnitname(orderDetail.getUnitname());
					oweOrderDetail.setFixnum(orderDetail.getFixnum());
					oweOrderDetail.setFixprice(orderDetail.getFixprice());
					oweOrderDetail.setOffprice(orderDetail.getOffprice());
					oweOrderDetail.setTaxprice(orderDetail.getTaxprice());
					oweOrderDetail.setNotaxamount(orderDetail.getNotaxamount());
					oweOrderDetail.setNotaxprice(orderDetail.getNotaxprice());
					oweOrderDetail.setCostprice(orderDetail.getCostprice());
					oweOrderDetail.setOldprice(orderDetail.getOldprice());
					oweOrderDetail.setDemandprice(orderDetail.getDemandprice());
					oweOrderDetail.setDemandamount(orderDetail.getDemandamount());
					oweOrderDetail.setUnitnum(orderDetail.getUnitnum());
					GoodsInfo goodsInfo = getAllGoodsInfoByID(oweOrderDetail.getGoodsid());
					oweOrderDetail.setAuxnum(oweOrderDetail.getUnitnum().divideAndRemainder(goodsInfo.getBoxnum())[0]);
					oweOrderDetail.setOvernum(oweOrderDetail.getUnitnum().divideAndRemainder(goodsInfo.getBoxnum())[1]);
					oweOrderDetail.setAuxunitid(orderDetail.getAuxunitid());
					oweOrderDetail.setAuxunitname(orderDetail.getAuxunitname());
					oweOrderDetail.setAuxnumdetail(CommonUtils.strDigitNumDeal(oweOrderDetail.getAuxnum().toString() + oweOrderDetail.getAuxunitname() + oweOrderDetail.getOvernum().toString() + oweOrderDetail.getUnitname()));
					oweOrderDetail.setTotalbox(oweOrderDetail.getAuxnum().add(oweOrderDetail.getOvernum().divide(goodsInfo.getBoxnum(), 3, BigDecimal.ROUND_HALF_UP)));
					oweOrderDetail.setTaxamount(oweOrderDetail.getUnitnum().multiply(oweOrderDetail.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
					oweOrderDetail.setNotaxamount(oweOrderDetail.getUnitnum().multiply(oweOrderDetail.getNotaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
					oweOrderDetail.setTaxtype(orderDetail.getTaxtype());
					oweOrderDetail.setTax(oweOrderDetail.getTaxamount().subtract(oweOrderDetail.getNotaxamount()));
					oweOrderDetail.setRemark(orderDetail.getRemark());
					oweOrderDetail.setDeliverytype(orderDetail.getDeliverytype());
					oweOrderDetail.setDeliverydate(orderDetail.getDeliverydate());
					oweOrderDetail.setBillno(dispatchBill.getId());
					oweOrderDetail.setBilldetailno(orderDetail.getId());
					oweOrderDetail.setSeq(orderDetail.getSeq());
					oweOrderDetail.setStorageid(orderDetail.getStorageid());
					detaillist.add(oweOrderDetail);
				}
				for (OweOrderDetail oweOrderDetail : detaillist) {
					getSalesOweOrderMapper().addOweOrderDetail(oweOrderDetail);
				}
				flag = getSalesOweOrderMapper().addOweOrder(oweOrder) > 0;
				if (!flag) {
					getSalesOweOrderMapper().deleteOweOrderDetail(oweOrder.getId());
				} else {
					newId = oweOrder.getId();
				}
				msg = newId;
			} else {
				msg = "所有商品可用量都充足，不需要生成销售欠单";
			}
		}
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}
	 /**
     *  销售单新增销售欠货单
     *
     * @throws Exception
     * @author wanghongteng
     * @date 9,21,2015
     */
	@Override
	public Map addOweOrderByOrder(String Id) throws Exception {
		List i=new ArrayList();
		boolean flag=false;
		String newId="";
		Map map = new HashMap();
		String msg="";
		DispatchBill dispatchBill=getSalesDispatchBillMapper().getDispatchBillByOrder(Id);
		String id=dispatchBill.getId();
		List dispatchBillDetailList = getSalesDispatchBillMapper().getDispatchBillDetailListByBill(id);
		List<OrderDetail> orderDetaillist=getSalesOrderDetailMapper().getOrderDetailByOrder(Id, null);
		//生成欠货单
   	    List list=new ArrayList();
		if (getSalesOweOrderMapper().getOweOrderDetailByBillno(id) != 0) {
			msg = "该订单已生成过销售欠单!";
		}
		else {
			for (OrderDetail orderDetail : orderDetaillist) {
	   			if ( orderDetail.getUnitnum().compareTo(orderDetail.getFixnum())==-1) {
					orderDetail.setUnitnum(orderDetail.getFixnum().subtract(orderDetail.getUnitnum()));
					list.add(orderDetail);
				}
			}
			if (list.size() > 0) {
				OweOrder oweOrder = new OweOrder();
				if (isAutoCreate("t_sales_owe_order")) {
					// 获取自动编号
					String oweOrderId = getAutoCreateSysNumbderForeign(oweOrder, "t_sales_owe_order");
					oweOrder.setId(oweOrderId);
				} else {
					oweOrder.setId("QHDD-" + CommonUtils.getDataNumberSendsWithRand());
				}
				oweOrder.setBusinessdate(CommonUtils.getTodayDataStr());
				oweOrder.setStatus("0");
				oweOrder.setRemark(dispatchBill.getRemark());
				SysUser sysUser = getSysUser();
				oweOrder.setAdddeptid(sysUser.getDepartmentid());
				oweOrder.setAdddeptname(sysUser.getDepartmentname());
				oweOrder.setAdduserid(sysUser.getUserid());
				oweOrder.setAddusername(sysUser.getName());
				oweOrder.setAddtime(new Date());
				oweOrder.setCustomerid(dispatchBill.getCustomerid());
				oweOrder.setPcustomerid(dispatchBill.getPcustomerid());
				oweOrder.setCustomersort(dispatchBill.getCustomersort());
				oweOrder.setHandlerid(dispatchBill.getHandlerid());
				oweOrder.setSalesarea(dispatchBill.getSalesarea());
				oweOrder.setSalesdept(dispatchBill.getSalesdept());
				oweOrder.setSalesuser(dispatchBill.getSalesuser());
				oweOrder.setStorageid(dispatchBill.getStorageid());
				oweOrder.setBillno(dispatchBill.getBillno());
				oweOrder.setIndooruserid(dispatchBill.getIndooruserid());
				List<OweOrderDetail> detaillist = new ArrayList();
				for (Object obj : list) {
					OrderDetail orderDetail = (OrderDetail) obj;
					OweOrderDetail oweOrderDetail = new OweOrderDetail();
					oweOrderDetail.setOrderid(oweOrder.getId());
					oweOrderDetail.setGoodsid(orderDetail.getGoodsid());
					oweOrderDetail.setGroupid(orderDetail.getGroupid());
					oweOrderDetail.setGoodssort(orderDetail.getGoodssort());
					oweOrderDetail.setBrandid(orderDetail.getBrandid());
					oweOrderDetail.setBranduser(orderDetail.getBranduser());
					oweOrderDetail.setBranddept(orderDetail.getBranddept());
					oweOrderDetail.setSupplierid(orderDetail.getSupplierid());
					oweOrderDetail.setSupplieruser(orderDetail.getSupplieruser());
					oweOrderDetail.setUnitid(orderDetail.getUnitid());
					oweOrderDetail.setUnitname(orderDetail.getUnitname());
					oweOrderDetail.setFixnum(orderDetail.getFixnum());
					oweOrderDetail.setFixprice(orderDetail.getFixprice());
					oweOrderDetail.setOffprice(orderDetail.getOffprice());
					oweOrderDetail.setTaxprice(orderDetail.getTaxprice());
					oweOrderDetail.setNotaxamount(orderDetail.getNotaxamount());
					oweOrderDetail.setNotaxprice(orderDetail.getNotaxprice());
					oweOrderDetail.setCostprice(orderDetail.getCostprice());
					oweOrderDetail.setOldprice(orderDetail.getOldprice());
					oweOrderDetail.setDemandprice(orderDetail.getDemandprice());
					oweOrderDetail.setDemandamount(orderDetail.getDemandamount());
					oweOrderDetail.setUnitnum(orderDetail.getUnitnum());
					GoodsInfo goodsInfo = getAllGoodsInfoByID(oweOrderDetail.getGoodsid());
					oweOrderDetail.setAuxnum(oweOrderDetail.getUnitnum().divideAndRemainder(goodsInfo.getBoxnum())[0]);
					oweOrderDetail.setOvernum(oweOrderDetail.getUnitnum().divideAndRemainder(goodsInfo.getBoxnum())[1]);
					oweOrderDetail.setAuxunitid(orderDetail.getAuxunitid());
					oweOrderDetail.setAuxunitname(orderDetail.getAuxunitname());
					oweOrderDetail.setAuxnumdetail(CommonUtils.strDigitNumDeal(oweOrderDetail.getAuxnum().toString() + oweOrderDetail.getAuxunitname() + oweOrderDetail.getOvernum().toString() + oweOrderDetail.getUnitname()));
					oweOrderDetail.setTotalbox(oweOrderDetail.getAuxnum().add(oweOrderDetail.getOvernum().divide(goodsInfo.getBoxnum(), 3, BigDecimal.ROUND_HALF_UP)));
					oweOrderDetail.setTaxamount(oweOrderDetail.getUnitnum().multiply(oweOrderDetail.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
					oweOrderDetail.setNotaxamount(oweOrderDetail.getUnitnum().multiply(oweOrderDetail.getNotaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
					oweOrderDetail.setTaxtype(orderDetail.getTaxtype());
					oweOrderDetail.setTax(oweOrderDetail.getTaxamount().subtract(oweOrderDetail.getNotaxamount()));
					oweOrderDetail.setRemark(orderDetail.getRemark());
					oweOrderDetail.setDeliverytype(orderDetail.getDeliverytype());
					oweOrderDetail.setDeliverydate(orderDetail.getDeliverydate());
					oweOrderDetail.setBillno(dispatchBill.getId());
					oweOrderDetail.setBilldetailno(orderDetail.getId());
					oweOrderDetail.setSeq(orderDetail.getSeq());
					oweOrderDetail.setStorageid(orderDetail.getStorageid());
					detaillist.add(oweOrderDetail);
				}
				for (OweOrderDetail oweOrderDetail : detaillist) {
					getSalesOweOrderMapper().addOweOrderDetail(oweOrderDetail);
				}
				flag = getSalesOweOrderMapper().addOweOrder(oweOrder) > 0;
				if (!flag) {
					getSalesOweOrderMapper().deleteOweOrderDetail(oweOrder.getId());
				} else {
					newId = oweOrder.getId();
				}
				msg = newId;
			} else {
				msg = "所有商品可用量都充足，不需要生成销售欠单";
			}
		}
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}
	/**
     *  到货后发送内部信息
     *
     * @throws Exception
     * @author wanghongteng
     * @date 9,21,2015
     */
	@Override
	public void sendMessage() throws Exception {
		List<OweOrder> list=getSalesOweOrderMapper().getAllSalesOweOrderList();
		List<Map> sendList= new ArrayList(); 
		for(OweOrder oweOrder : list){
			if(oweOrder.getStatus().equals("0")){
				List<OweOrderDetail> detailList=getSalesOweOrderMapper().getOweOrderDetail(oweOrder.getId());
				Customer customer;
				String Salesuserid="";
				String indoorstaff="";
				if (StringUtils.isNotEmpty(oweOrder.getCustomerid())) {
					customer = getCustomerByID(oweOrder.getCustomerid());
					List<SysUser> Salesuseridlist=sysUserMapper.getSysUserListByPersonnelid(customer.getSalesuserid());
					List<SysUser> indoorstafflist=sysUserMapper.getSysUserListByPersonnelid(customer.getIndoorstaff());
					if(Salesuseridlist.size()!=0){
						Salesuserid=Salesuseridlist.get(0).getUserid();
					}
					if(indoorstafflist.size()!=0){
					    indoorstaff=indoorstafflist.get(0).getUserid();
					}
				}
				//获取各个订单中数量充足的商品列表
				List<String> goodsList=new ArrayList();
				for(OweOrderDetail oweOrderDetail :detailList){
					BigDecimal useable=new BigDecimal("0");
					if (StringUtils.isNotEmpty(oweOrder.getStorageid())) {
						StorageSummary storageSummary= storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid(oweOrderDetail.getGoodsid(),oweOrder.getStorageid());
						if(storageSummary!=null)
						    useable = storageSummary.getUsablenum();
		    		} else {
		    			 if(storageSummaryMapper.getStorageSummarySumByGoodsid(oweOrderDetail.getGoodsid())!=null){
		    			       useable = storageSummaryMapper.getStorageSummarySumByGoodsid(oweOrderDetail.getGoodsid()).getUsablenum();
		    			 }
		    		}
					if(oweOrderDetail.getUnitnum().intValue()<=useable.intValue()){
						goodsList.add("["+oweOrderDetail.getGoodsid()+"]"+getGoodsInfoByID(oweOrderDetail.getGoodsid()).getName());
					}
				}
				//整合成消息内容
				if (goodsList.size() > 0) {
					if (sendList.size() == 0) {
						if (StringUtils.isNotEmpty(Salesuserid) && Salesuserid != "") {
							Map map = new HashMap();
							map.put("title", "销售欠单到货通知");
							map.put("receivers", Salesuserid);
							String url="sales/oweOrderListPage.do?id="+oweOrder.getId();
							String contentStr = "订单:" +oweOrder.getId() + "中的";
							int index = 0;
							for (String goods : goodsList) {
								if (index < goodsList.size() - 1)
									contentStr = contentStr + goods + ",";
								else
									contentStr = contentStr + goods + "数量充足";
								index++;
							}
							map.put("remindurl", url);
							map.put("content", contentStr);
							map.put("mtiptype", "1");
							map.put("msgtype", "6");
							sendList.add(map);
						}
						if (StringUtils.isNotEmpty(indoorstaff) && indoorstaff != "") {
							Map map = new HashMap();
							map.put("title", "销售欠单到货通知");
							map.put("receivers", indoorstaff);
							String url="sales/oweOrderListPage.do?id="+oweOrder.getId();
							String contentStr = "订单:" +oweOrder.getId() + "中的";
							int index = 0;
							for (String goods : goodsList) {
								if (index < goodsList.size() - 1)
									contentStr = contentStr + goods + ",";
								else
									contentStr = contentStr + goods + "数量充足";
								index++;
							}
							map.put("remindurl", url);
							map.put("content", contentStr);
							map.put("mtiptype", "1");
							map.put("msgtype", "6");
							sendList.add(map);
						}
					} else {
						if (StringUtils.isNotEmpty(Salesuserid) && Salesuserid != "") {
							int index = 0, mapSize = 0;
							mapSize = sendList.size();
							for (Map sendMap : sendList) {
								if (sendMap.get("receivers").equals(Salesuserid)) {
									String url="sales/oweOrderListPage.do?id="+oweOrder.getId();
									String contentStr = "<br>订单:" +oweOrder.getId() + "中的";
									int i = 0;
									for (String goods : goodsList) {
										if (i < goodsList.size() - 1)
											contentStr = contentStr + goods + ",";
										else
											contentStr = contentStr + goods + "数量充足";
										i++;
									}
									sendMap.put("remindurl", sendMap.get("remindurl")+","+oweOrder.getId());
									sendMap.put("content", sendMap.get("content") + "<br>" + contentStr);
									break;
								}
								index++;
							}
							if (index == mapSize) {
								Map map = new HashMap();
								map.put("title", "销售欠单到货通知");
								map.put("receivers", Salesuserid);
								String url="sales/oweOrderListPage.do?id="+oweOrder.getId();
								String contentStr = "订单:" +oweOrder.getId() + "中的";
								int i = 0;
								for (String goods : goodsList) {
									if (i < goodsList.size() - 1)
										contentStr = contentStr + goods + ",";
									else
										contentStr = contentStr + goods + "数量充足";
									i++;
								}
								map.put("remindurl", url);
								map.put("content", contentStr);
								map.put("mtiptype", "1");
								map.put("msgtype", "6");
								sendList.add(map);
							}
						}
						if (StringUtils.isNotEmpty(indoorstaff) && indoorstaff != "") {
							int index = 0, mapSize = 0;
							mapSize = sendList.size();
							for (Map sendMap : sendList) {
								if (sendMap.get("receivers").equals(indoorstaff)) {
									String url="sales/oweOrderListPage.do?id="+oweOrder.getId();
									String contentStr = "<br>订单:" +oweOrder.getId() + "中的";
									int i = 0;
									for (String goods : goodsList) {
										if (i < goodsList.size() - 1)
											contentStr = contentStr + goods + ",";
										else
											contentStr = contentStr + goods + "数量充足";
										i++;
									}
									sendMap.put("remindurl", sendMap.get("remindurl")+","+oweOrder.getId());
									sendMap.put("content", sendMap.get("content") + "<br>" + contentStr);
									break;
								}
								index++;
							}
							if (index == mapSize) {
								Map map = new HashMap();
								map.put("title", "销售欠单到货通知");
								map.put("receivers", indoorstaff);
								String url="sales/oweOrderListPage.do?id="+oweOrder.getId();
								String contentStr = "订单:" +oweOrder.getId() + "中的";
								int i = 0;
								for (String goods : goodsList) {
									if (i < goodsList.size() - 1)
										contentStr = contentStr + goods + ",";
									else
										contentStr = contentStr + goods + "数量充足";
									i++;
								}
								map.put("remindurl", url);
								map.put("content", contentStr);
								map.put("mtiptype", "1");
								map.put("msgtype", "6");
								sendList.add(map);
							}
						}
					}
				}
			}
		}
		for(Map map : sendList){
//			map.put("remindurl", map.get("remindurl")+"&ismsgphoneurlshow=0");
			map.put("remindurl", "sales/oweOrderListPage.do?ismsgphoneurlshow=0");
		    addMessageReminder(map);
		}
	}

	/**
    * 检查订单是否存在
    *
    * @throws Exception
    * @author wanghongteng
    * @date 9,21,2015
    */
	@Override
	public Map checkOweOrderId(String id) throws Exception {
		boolean flag=false;
		OweOrder oweOrder=getSalesOweOrderMapper().getOweOrder(id);
		if(oweOrder!=null){
			flag=true;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		return map;
	}
}

