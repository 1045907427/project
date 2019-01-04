/**
 * @(#)ArrivalOrderServiceImpl.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-5-21 zhanghonghui 创建版本
 */
package com.hd.agent.purchase.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.MeteringUnit;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.model.TaxType;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.purchase.dao.LimitPirceOrderMapper;
import com.hd.agent.purchase.model.LimitPriceOrder;
import com.hd.agent.purchase.model.LimitPriceOrderDetail;
import com.hd.agent.purchase.service.ILimitPriceOrderService;

/**
 * 采购调价单
 * 
 * @author zhanghonghui
 */
public class LimitPriceOrderServiceImpl extends BasePurchaseServiceImpl implements ILimitPriceOrderService {
	protected LimitPirceOrderMapper limitPriceOrderMapper;
	
	public LimitPirceOrderMapper getLimitPriceOrderMapper() {
		return limitPriceOrderMapper;
	}

	public void setLimitPriceOrderMapper(LimitPirceOrderMapper limitPriceOrderMapper) {
		this.limitPriceOrderMapper = limitPriceOrderMapper;
	}
	/**
	 * 添加单采购调价单
	 */
	private boolean insertLimitPriceOrder(LimitPriceOrder limitPriceOrder) throws Exception{
		return limitPriceOrderMapper.insertLimitPriceOrder(limitPriceOrder)>0;
	}
	
	public boolean addLimitPriceOrderAddDetail(LimitPriceOrder limitPriceOrder) throws Exception{
		limitPriceOrder.setId(getBillNumber(limitPriceOrder, "t_purchase_limitPriceorder"));
		if(StringUtils.isEmpty(limitPriceOrder.getId())){
			return false;
		}
		boolean flag=insertLimitPriceOrder(limitPriceOrder);
		List<LimitPriceOrderDetail> list=limitPriceOrder.getLimitPriceOrderDetailList();
		if(flag && list!=null && list.size()>0){
			int iseq=1;
			for(LimitPriceOrderDetail item : list){				
				item.setOrderid(limitPriceOrder.getId());
				item.setSeq(iseq);
				insertLimitPriceOrderDetail(item);
				iseq=iseq+1;
			}
		}		
		return flag;
		
	}
	public LimitPriceOrder showLimitPriceOrderAndDetail(String id) throws Exception{
		LimitPriceOrder order= limitPriceOrderMapper.getLimitPriceOrder(id);
		if(null!=order){
			DepartMent departMent =null;
			MeteringUnit meteringUnit=null;
			if(StringUtils.isNotEmpty(order.getApplydeptid())){
				departMent  = getBaseFilesDepartmentMapper().getDepartmentInfo(order.getApplydeptid());
				if(departMent != null){
					order.setApplydeptname(departMent.getName());
				}
			}
			
			List<LimitPriceOrderDetail> list=showPureLimitPriceOrderDetailListByOrderId(order.getId());
			if(null!=list && list.size()>0){
				for(LimitPriceOrderDetail item :list){
					if(null!=item ){
						if(StringUtils.isNotEmpty(item.getGoodsid())){
							item.setGoodsInfo(getGoodsInfoByID(item.getGoodsid()));
						}
						if(StringUtils.isNotEmpty(item.getAuxunitid())){
							meteringUnit=getMeteringUnitById(item.getAuxunitid());
							if(null!=meteringUnit){
								item.setAuxunitname(meteringUnit.getName());
							}
						}
					}
				}
				order.setLimitPriceOrderDetailList(list);
			}else{
				order.setLimitPriceOrderDetailList(new ArrayList<LimitPriceOrderDetail>());
			}
		}
		return order;
	}

	public boolean updateLimitPriceOrderAddDetail(LimitPriceOrder limitPriceOrder) throws Exception{
		boolean flag=limitPriceOrderMapper.updateLimitPriceOrder(limitPriceOrder)>0;
		if(flag){
			deleteLimitPriceOrderDetailByOrderid(limitPriceOrder.getId());
			List<LimitPriceOrderDetail> list=limitPriceOrder.getLimitPriceOrderDetailList();
			if(list!=null && list.size()>0){
				int iseq=1;
				for(LimitPriceOrderDetail item : list){				
					item.setOrderid(limitPriceOrder.getId());
					item.setSeq(iseq);
					insertLimitPriceOrderDetail(item);
					iseq=iseq+1;
				}
			}
		}
		return flag;
	}
	
	public boolean deleteLimitPriceOrderAndDetail(String id) throws Exception{
		boolean flag=limitPriceOrderMapper.deleteLimitPriceOrder(id)>0;
		if(flag){
			limitPriceOrderMapper.deleteLimitPriceOrderDetailByOrderid(id);
		}
		return flag;
	}
	@Override
	public LimitPriceOrder showPureLimitPriceOrder(String id) throws Exception{
		return limitPriceOrderMapper.getLimitPriceOrder(id);
	}
	@Override
	public LimitPriceOrder showLimitPriceOrder(String id) throws Exception{
		Map map=new HashMap();
		map.put("id", id.trim());
		String cols = getAccessColumnList("t_purchase_limitPriceorder",null);
		String datasql = getDataAccessRule("t_purchase_limitPriceorder",null);
		map.put("cols", cols);
		map.put("authDataSql", datasql);		
		LimitPriceOrder limitPriceOrder= limitPriceOrderMapper.getLimitPriceOrderBy(map);
		if(null!=limitPriceOrder){
			if(StringUtils.isNotEmpty(limitPriceOrder.getApplydeptid())){
				DepartMent departMent  = getBaseFilesDepartmentMapper().getDepartmentInfo(limitPriceOrder.getApplydeptid());
				if(departMent != null){
					limitPriceOrder.setApplydeptname(departMent.getName());
				}
			}
		}
		return limitPriceOrder;
	}
	@Override
	public LimitPriceOrder showLimitPriceOrderByDataAuth(String id) throws Exception{
		Map map=new HashMap();
		map.put("id", id.trim());
		String datasql = getDataAccessRule("t_purchase_limitPriceorder",null);
		map.put("authDataSql", datasql);		
		return limitPriceOrderMapper.getLimitPriceOrderBy(map);		
	}
	
	@Override
	public PageData showLimitPriceOrderPageList(PageMap pageMap) throws Exception{
		String cols = getAccessColumnList("t_purchase_limitPriceorder",null);
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_purchase_limitPriceorder",null);
		pageMap.setDataSql(sql);
		List<LimitPriceOrder> orderList=limitPriceOrderMapper.getLimitPriceOrderPageList(pageMap);
		if(orderList!=null && orderList.size()>0){
			DepartMent departMent =null;
			Personnel personnel = null;
			for(LimitPriceOrder item : orderList){
				if(StringUtils.isNotEmpty(item.getApplydeptid())){
					departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(item.getApplydeptid());
					if(departMent != null){
						item.setApplydeptname(departMent.getName());
					}
				}
				if(StringUtils.isNotEmpty(item.getApplyuserid())){
					personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(item.getApplyuserid());
					if(personnel != null){
						item.setApplyusername(personnel.getName());
					}
				}
			}
		}
		PageData pageData=new PageData(limitPriceOrderMapper.getLimitPriceOrderPageCount(pageMap), 
						orderList, pageMap);
		return pageData;
	}
	/**
	 * 根据参数更新采购调价单<br/>
	 * map中的参数：<br/>
	 * 更新参数条件：<br/>
	 * limitPriceOrder :采购调价单对象<br/>
	 * 条件参数：<br/>
	 * id : 编号
	 * @param limitPriceOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public boolean updateLimitPriceOrderBy(Map map) throws Exception{
		String sql = getDataAccessRule("t_purchase_limitPriceorder",null);
		map.put("authDataSql", sql);
		return limitPriceOrderMapper.updateLimitPriceOrderBy(map)>0;
	}
	
	/**
	 * 审核
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	public Map auditLimitPriceOrder(String id) throws Exception{
		Map map=new HashMap();
		LimitPriceOrder pOrder=showLimitPriceOrderByDataAuth(id);
		if(null==pOrder){
			map.put("flag", false);
			return map;
		}
		boolean flag = false;
		boolean filterflag=true;
		SysUser sysUser = getSysUser();
		if("2".equals(pOrder.getStatus())){
			List<LimitPriceOrderDetail> list=showLimitPriceOrderDetailListByOrderId(id);
			if(null!=list && list.size()>0){
				LimitPriceOrderDetail limitPriceOrderDetail=null;
				Map queryMap=new HashMap();
				for(LimitPriceOrderDetail item : list){
					queryMap.put("effectstartdate", item.getEffectstartdate());
					queryMap.put("effectenddate ", item.getEffectenddate());
					queryMap.put("goodsid", item.getGoodsid());
					queryMap.put("notcurid", item.getId());
					limitPriceOrderDetail=getLimitPriceOrderDetailValidBy(map);
					if(null!=limitPriceOrderDetail){
						filterflag=false;
						map.clear();
						map.put("flag", false);
						map.put("limitpriceflag", "1");
						map.put("goodsid", item.getGoodsid());
						map.put("orderid", item.getOrderid());
						map.put("efstartdate", item.getEffectstartdate());
						map.put("efenddate", item.getEffectenddate());
						break;
					}
				}
				if(!filterflag){
					return map;
				}
			}
			LimitPriceOrder limitPriceOrder=new LimitPriceOrder();
			limitPriceOrder.setId(id);
			limitPriceOrder.setStatus("3");
			limitPriceOrder.setAudituserid(sysUser.getUserid());
			limitPriceOrder.setAuditusername(sysUser.getName());
			limitPriceOrder.setAudittime(new Date());
			flag=updateLimitPriceOrderStatus(limitPriceOrder);
		}
		map.put("flag", flag);
		return map;
	}
	/**
	 * 反审核
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	public Map oppauditLimitPriceOrder(String id) throws Exception{
		Map map=new HashMap();		
		LimitPriceOrder pOrder=showLimitPriceOrderByDataAuth(id);
		if(null==pOrder){
			map.put("flag", false);
			return map;
		}
		boolean flag = false;
		SysUser sysUser = getSysUser();
		if("3".equals(pOrder.getStatus())){
			LimitPriceOrder limitPriceOrder=new LimitPriceOrder();
			limitPriceOrder.setId(id);
			limitPriceOrder.setStatus("2");
			limitPriceOrder.setAudituserid(sysUser.getUserid());
			limitPriceOrder.setAuditusername(sysUser.getName());
			limitPriceOrder.setAudittime(new Date());
			flag=updateLimitPriceOrderStatus(limitPriceOrder);			
		}
		map.put("flag", flag);
		return map;
	}
	
	public boolean updateLimitPriceOrderStatus(LimitPriceOrder limitPriceOrder) throws Exception{
		return limitPriceOrderMapper.updateLimitPriceOrderStatus(limitPriceOrder)>0;
	}

	@Override
	public List getLimitPriceOrderUnEffectList() throws Exception{
		Map map=new HashMap();
		map.put("notEffectData", "1");
		return limitPriceOrderMapper.getLimitPriceOrderListBy(map);
	}
	//------------------------------------------------------------------------//
	//采购调价单详细
	//-----------------------------------------------------------------------//
	
	public boolean insertLimitPriceOrderDetail(LimitPriceOrderDetail limitPriceOrderDetail) throws Exception{
		return limitPriceOrderMapper.insertLimitPriceOrderDetail(limitPriceOrderDetail)>0;
	}
	
	public List showLimitPriceOrderDetailListByOrderId(String orderid) throws Exception{
		if(null==orderid || "".equals(orderid)){
			return new ArrayList<LimitPriceOrderDetail>();
		}
		orderid=orderid.trim();
		Map map=new HashMap();
		map.put("orderid", orderid);
		String cols = getAccessColumnList("t_purchase_limitPriceorder_detail",null);
		map.put("cols", cols);
		String sql = getDataAccessRule("t_purchase_limitPriceorder_detail",null);
		map.put("authDataSql", sql);
		List<LimitPriceOrderDetail> list= limitPriceOrderMapper.getLimitPriceOrderDetailListBy(map);
		if(null!=list && list.size()>0){
			TaxType taxType=null;
			MeteringUnit meteringUnit=null;
			for(LimitPriceOrderDetail item :list){
				if(null!=item ){
					if(StringUtils.isNotEmpty(item.getGoodsid())){
						item.setGoodsInfo(getGoodsInfoByID(item.getGoodsid()));
					}
					if(StringUtils.isNotEmpty(item.getAuxunitid())){
						meteringUnit=getMeteringUnitById(item.getAuxunitid());
						if(null!=meteringUnit){
							item.setAuxunitname(meteringUnit.getName());
						}
					}
				}
			}
		}
		return list;
	}
	@Override
	public List showPureLimitPriceOrderDetailListByOrderId(String orderid) throws Exception{
		orderid=orderid.trim();
		Map map=new HashMap();
		map.put("orderid", orderid);
		return limitPriceOrderMapper.getLimitPriceOrderDetailListBy(map);
	}
	
	public boolean deleteLimitPriceOrderDetailByOrderid(String orderid) throws Exception{
		return limitPriceOrderMapper.deleteLimitPriceOrderDetailByOrderid(orderid)>0;
	}
	@Override
	public int updateLimitPriceOrderDetail(LimitPriceOrderDetail limitPriceOrderDetail) throws Exception{
		return limitPriceOrderMapper.updateLimitPriceOrderDetail(limitPriceOrderDetail);
	}
	@Override
	public LimitPriceOrderDetail getLimitPriceOrderDetailValid(String goodsid,String businessdate) throws Exception{
		return limitPriceOrderMapper.getLimitPriceOrderDetailValid(goodsid,businessdate);
	}
	@Override
	public LimitPriceOrderDetail getLimitPriceOrderDetailValidBy(Map map) throws Exception{
		return limitPriceOrderMapper.getLimitPriceOrderDetailValidBy(map);
	}	
}

