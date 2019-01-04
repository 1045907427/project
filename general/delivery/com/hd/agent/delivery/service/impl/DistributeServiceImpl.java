/**
 * @(#)DistributeServiceImpl.java
 *
 * @author wanghongteng
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年8月4日 wanghongteng 创建版本
 */
package com.hd.agent.delivery.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import com.hd.agent.sales.model.ModelOrder;
import com.hd.agent.sales.model.OrderDetail;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.delivery.dao.DeliveryAogorderMapper;
import com.hd.agent.delivery.dao.DeliveryAogreturnMapper;
import com.hd.agent.delivery.dao.DeliveryOrderMapper;
import com.hd.agent.delivery.dao.DeliveryRejectbillMapper;
import com.hd.agent.delivery.model.DeliveryAogorder;
import com.hd.agent.delivery.model.DeliveryAogorderDetail;
import com.hd.agent.delivery.model.DeliveryAogreturn;
import com.hd.agent.delivery.model.DeliveryAogreturnDetail;
import com.hd.agent.delivery.model.DeliveryOrder;
import com.hd.agent.delivery.model.DeliveryOrderDetail;
import com.hd.agent.delivery.model.DeliveryRejectbill;
import com.hd.agent.delivery.model.DeliveryRejectbillDetail;
import com.hd.agent.delivery.model.ExportDeliveryAogorder;
import com.hd.agent.delivery.model.ExportDeliveryAogreturn;
import com.hd.agent.delivery.model.ExportDeliveryOrder;
import com.hd.agent.delivery.model.ExportDeliveryRejectbill;
import com.hd.agent.delivery.service.IDistributeService;
import com.hd.agent.storage.dao.StorageDeliveryEnterMapper;
import com.hd.agent.storage.dao.StorageDeliveryOutMapper;
import com.hd.agent.storage.model.StorageDeliveryEnter;
import com.hd.agent.storage.model.StorageDeliveryEnterDetail;
import com.hd.agent.storage.model.StorageDeliveryOut;
import com.hd.agent.storage.model.StorageDeliveryOutDetail;
import com.hd.agent.storage.model.StorageSummaryBatch;
import com.hd.agent.storage.service.DeliveryOutService;
import com.hd.agent.storage.service.IDistributeRejectService;
import com.hd.agent.storage.service.impl.BaseStorageServiceImpl;

/**
 * 
 * 
 * @author wanghongteng
 */
/**
 * @author Administrator
 *
 */
public class DistributeServiceImpl extends 	BaseStorageServiceImpl implements IDistributeService {
	private DeliveryAogorderMapper deliveryAogorderMapper;
	private DeliveryAogreturnMapper deliveryAogreturnMapper;
	private DeliveryOrderMapper deliveryOrderMapper;
	private DeliveryRejectbillMapper deliveryRejectbillMapper;
	private IDistributeRejectService distributeRejectService;
	private StorageDeliveryEnterMapper storageDeliveryEnterMapper;
	private StorageDeliveryOutMapper storageDeliveryOutMapper;
	private DeliveryOutService deliveryOutService;

	public DeliveryOutService getDeliveryOutService() {
		return deliveryOutService;
	}

	public void setDeliveryOutService(DeliveryOutService deliveryOutService) {
		this.deliveryOutService = deliveryOutService;
	}

	public IDistributeRejectService getDistributeRejectService() {
		return distributeRejectService;
	}

	public StorageDeliveryEnterMapper getStorageDeliveryEnterMapper() {
		return storageDeliveryEnterMapper;
	}

	public StorageDeliveryOutMapper getStorageDeliveryOutMapper() {
		return storageDeliveryOutMapper;
	}

	public void setStorageDeliveryEnterMapper(StorageDeliveryEnterMapper storageDeliveryEnterMapper) {
		this.storageDeliveryEnterMapper = storageDeliveryEnterMapper;
	}

	public void setStorageDeliveryOutMapper(StorageDeliveryOutMapper storageDeliveryOutMapper) {
		this.storageDeliveryOutMapper = storageDeliveryOutMapper;
	}

	public void setDistributeRejectService(IDistributeRejectService distributeRejectService) {
		this.distributeRejectService = distributeRejectService;
	}

	public DeliveryAogorderMapper getDeliveryAogorderMapper() {
		return deliveryAogorderMapper;
	}

	public void setDeliveryAogorderMapper(DeliveryAogorderMapper deliveryAogorderMapper) {
		this.deliveryAogorderMapper = deliveryAogorderMapper;
	}

	public DeliveryAogreturnMapper getDeliveryAogreturnMapper() {
		return deliveryAogreturnMapper;
	}

	public void setDeliveryAogreturnMapper(DeliveryAogreturnMapper deliveryAogreturnMapper) {
		this.deliveryAogreturnMapper = deliveryAogreturnMapper;
	}

	public DeliveryOrderMapper getDeliveryOrderMapper() {
		return deliveryOrderMapper;
	}

	public void setDeliveryOrderMapper(DeliveryOrderMapper deliveryOrderMapper) {
		this.deliveryOrderMapper = deliveryOrderMapper;
	}

	public DeliveryRejectbillMapper getDeliveryRejectbillMapper() {
		return deliveryRejectbillMapper;
	}

	public void setDeliveryRejectbillMapper(DeliveryRejectbillMapper deliveryRejectbillMapper) {
		this.deliveryRejectbillMapper = deliveryRejectbillMapper;
	}

	@Override
	public PageData showDeliveryAogorderList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_delivery_aogorder", "t");
		pageMap.setDataSql(dataSql);
		int count = deliveryAogorderMapper.getDeliveryAogorderListCount(pageMap);
		List<DeliveryAogorder> list = deliveryAogorderMapper.getDeliveryAogorderList(pageMap);
		for (DeliveryAogorder item : list) {
			StorageInfo storageInfo;
			BuySupplier buySupplier;
			if (StringUtils.isNotEmpty(item.getStorageid())) {
				storageInfo = getStorageInfoByID(item.getStorageid());
				if(null!=storageInfo)
				     item.setStoragename(storageInfo.getName());
			}
			if (StringUtils.isNotEmpty(item.getSupplierid())) {
				buySupplier = getSupplierInfoById(item.getSupplierid());
				if(null!=buySupplier)
				item.setSuppliername(buySupplier.getName());
			}
		}
		PageData pageData = new PageData(count, list, pageMap);
		return pageData;
	}

	@Override
	public Map addDeliveryAogorder(DeliveryAogorder deliveryAogorder, List<DeliveryAogorderDetail> detailList) throws Exception {
		String msg = "";
		boolean flag = false;
		boolean canAudit = true;
		if (canAudit) {
			if (isAutoCreate("t_delivery_aogorder")) {
				// 获取自动编号
				String id = getAutoCreateSysNumbderForeign(deliveryAogorder, "t_delivery_aogorder");
				deliveryAogorder.setId(id);
			} else {
				deliveryAogorder.setId("DHDD-" + CommonUtils.getDataNumberSendsWithRand());
			}
			deliveryAogorder.setAddtime(new Date());
			deliveryAogorder.setBusinessdate(CommonUtils.getTodayDataStr());
			BigDecimal totalBox = new BigDecimal("0");// 总箱数
			BigDecimal totalWeight = new BigDecimal("0");// 总重量
			BigDecimal totalVolume = new BigDecimal("0");// 总体积
			BigDecimal totalaMount = new BigDecimal("0");// 总金额
			for (DeliveryAogorderDetail deliveryAogorderDetail : detailList) {
				deliveryAogorderDetail.setBillid(deliveryAogorder.getId());
				GoodsInfo goodsInfo = getGoodsInfoByID(deliveryAogorderDetail.getGoodsid());
				deliveryAogorderDetail.setTotalbox(deliveryAogorderDetail.getAuxnum().add(deliveryAogorderDetail.getOvernum().divide(goodsInfo.getBoxnum(), 2, BigDecimal.ROUND_HALF_UP)));
				// 获取总箱数
				totalBox = totalBox.add(deliveryAogorderDetail.getTotalbox());
				// 获取总重量
				if (goodsInfo.getGrossweight() != null)
					totalWeight = totalWeight.add(goodsInfo.getGrossweight().multiply(deliveryAogorderDetail.getUnitnum()));
				// 获取总体积
				if (goodsInfo.getSinglevolume() != null)
					totalVolume = totalVolume.add(goodsInfo.getSinglevolume().multiply(deliveryAogorderDetail.getUnitnum()));
				// 获取总金额
				totalaMount = totalaMount.add(deliveryAogorderDetail.getTaxamount());
				deliveryAogorderMapper.addDeliveryAogorderDetail(deliveryAogorderDetail);
			}
			SysUser sysUser = getSysUser();
			deliveryAogorder.setAdddeptid(sysUser.getDepartmentid());
			deliveryAogorder.setAdddeptname(sysUser.getDepartmentname());
			deliveryAogorder.setAdduserid(sysUser.getUserid());
			deliveryAogorder.setAddusername(sysUser.getName());
			deliveryAogorder.setTotalbox(totalBox);
			deliveryAogorder.setTotalweight(totalWeight);
			deliveryAogorder.setTotalvolume(totalVolume);
			deliveryAogorder.setTotalamount(totalaMount);
			int i = deliveryAogorderMapper.addDeliveryAogorder(deliveryAogorder);
			flag = i > 0;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("id", deliveryAogorder.getId());
		return map;
	}

	@Override
	public Map auditDeliveryAogorder(String id) throws Exception {
		boolean flag=false;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		String businessdate = dateFormat.format(calendar.getTime());
		SysUser sysUser = getSysUser();
		DeliveryAogorder deliveryAogorder = deliveryAogorderMapper.getDeliveryAogorderByID(id);
		if ("2".equals(deliveryAogorder.getStatus())) {
			int i = deliveryAogorderMapper.auditDeliveryAogorder(id, sysUser.getUserid(), sysUser.getName(), new Date(), businessdate);
			flag = i > 0;
		} else {
			flag = false;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		return map;
	}

	@Override
	public Map oppauditDeliveryAogorder(String id) throws Exception {
		boolean flag=false;
		SysUser sysUser = getSysUser();
		StorageDeliveryEnter storageDeliveryEnter = new StorageDeliveryEnter();
		storageDeliveryEnter = storageDeliveryEnterMapper.getStorageDeliveryEnterBySourceId(id);
		if (null!=storageDeliveryEnter) {
			if ("2".equals(storageDeliveryEnter.getStatus())) {
				int i = deliveryAogorderMapper.oppauditDeliveryAogorder(id, sysUser.getUserid(), sysUser.getName(), new Date());
				flag = i > 0;
				if (flag == true) {
					distributeRejectService.deleteEnterAndDetailById(storageDeliveryEnter.getId());
				}
			} 
			else{
				flag = false;
			}
		}
		else{
			int i = deliveryAogorderMapper.oppauditDeliveryAogorder(id, sysUser.getUserid(), sysUser.getName(), new Date());
			flag = i > 0;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		return map;
	}

	@Override
	public Map getDeliveryAogorderInfo(String id) throws Exception {
		DeliveryAogorder deliveryAogorder = deliveryAogorderMapper.getDeliveryAogorderByID(id);
		List<DeliveryAogorderDetail> list = deliveryAogorderMapper.getDeliveryAogorderDetailList(id);
		for (DeliveryAogorderDetail deliveryAogorderDetail : list) {
			GoodsInfo goodsInfo = getGoodsInfoByID(deliveryAogorderDetail.getGoodsid());
			deliveryAogorderDetail.setGoodsInfo(goodsInfo);
		}
		Map map = new HashMap();
		map.put("deliveryAogorder", deliveryAogorder);
		map.put("detailList", list);
		return map;
	}

	@Override
	public boolean deleteDeliveryAogorder(String id) throws Exception {
		DeliveryAogorder deliveryAogorder = deliveryAogorderMapper.getDeliveryAogorderByID(id);
		if ("2".equals(deliveryAogorder.getStatus())&&deliveryAogorder!=null) {
			deliveryAogorderMapper.deleteDeliveryAogorderDetail(id);
			int i = deliveryAogorderMapper.deleteDeliveryAogorder(id);
			if (i > 0) {
				return true;
			}
			else{
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public Map editDeliveryAogorder(DeliveryAogorder deliveryAogorder, List<DeliveryAogorderDetail> detailList) throws Exception {
		String msg = "";
		boolean flag = false;
		DeliveryAogorder checkDeliveryAogorder = deliveryAogorderMapper.getDeliveryAogorderByID(deliveryAogorder.getId());
		if ("2".equals(checkDeliveryAogorder.getStatus())) {
			deliveryAogorderMapper.deleteDeliveryAogorderDetail(deliveryAogorder.getId());
			BigDecimal totalBox = new BigDecimal("0");// 总箱数
			BigDecimal totalWeight = new BigDecimal("0");// 总重量
			BigDecimal totalVolume = new BigDecimal("0");// 总体积
			BigDecimal totalaMount = new BigDecimal("0");// 总金额
			for (DeliveryAogorderDetail deliveryAogorderDetail : detailList) {
				deliveryAogorderDetail.setBillid(deliveryAogorder.getId());
				GoodsInfo goodsInfo = getGoodsInfoByID(deliveryAogorderDetail.getGoodsid());
				deliveryAogorderDetail.setTotalbox(deliveryAogorderDetail.getAuxnum().add(deliveryAogorderDetail.getOvernum().divide(goodsInfo.getBoxnum(), 2, BigDecimal.ROUND_HALF_UP)));
				// 获取总箱数
				totalBox = totalBox.add(deliveryAogorderDetail.getTotalbox());
				// 获取总重量
				if (null != goodsInfo.getGrossweight() && goodsInfo.getGrossweight().compareTo(new BigDecimal("0")) != 0)
					totalWeight = totalWeight.add(goodsInfo.getGrossweight().multiply(deliveryAogorderDetail.getUnitnum()));
				// 获取总体积
				if (null != goodsInfo.getSinglevolume() && goodsInfo.getSinglevolume().compareTo(new BigDecimal("0")) != 0)
					totalVolume = totalVolume.add(goodsInfo.getSinglevolume().multiply(deliveryAogorderDetail.getUnitnum()));
				// 获取总金额
				totalaMount = totalaMount.add(deliveryAogorderDetail.getTaxamount());
				deliveryAogorderMapper.addDeliveryAogorderDetail(deliveryAogorderDetail);
			}
			SysUser sysUser = getSysUser();
			deliveryAogorder.setModifyuserid(sysUser.getUserid());
			deliveryAogorder.setModifyusername(sysUser.getName());
			deliveryAogorder.setModifytime(new Date());
			deliveryAogorder.setTotalbox(totalBox);
			deliveryAogorder.setTotalweight(totalWeight);
			deliveryAogorder.setTotalvolume(totalVolume);
			deliveryAogorder.setTotalamount(totalaMount);
			int i = deliveryAogorderMapper.editDeliveryAogorder(deliveryAogorder);
			flag = i > 0;
			
		} 
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("id", deliveryAogorder.getId());
		return map;
	}
	// 退货订单部分
	@Override
	public PageData showDeliveryAogreturnList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_delivery_aogreturn", "t");
		pageMap.setDataSql(dataSql);
		int count = deliveryAogreturnMapper.getDeliveryAogreturnListCount(pageMap);
		List<DeliveryAogreturn> list = deliveryAogreturnMapper.getDeliveryAogreturnList(pageMap);
		for (DeliveryAogreturn item : list) {
			StorageInfo storageInfo;
			BuySupplier buySupplier;
			if (StringUtils.isNotEmpty(item.getStorageid())) {
				storageInfo = getStorageInfoByID(item.getStorageid());
				if(null!=storageInfo)
				item.setStoragename(storageInfo.getName());
			}
			if (StringUtils.isNotEmpty(item.getSupplierid())) {
				buySupplier = getSupplierInfoById(item.getSupplierid());
				if(null!=buySupplier)
				item.setSuppliername(buySupplier.getName());
			}
		}
		PageData pageData = new PageData(count, list, pageMap);
		return pageData;
	}

	@Override
	public Map addDeliveryAogreturn(DeliveryAogreturn deliveryAogreturn, List<DeliveryAogreturnDetail> detailList) throws Exception {
		String msg = "";
		boolean flag = false;
		boolean canAudit = true;
		if (canAudit) {
			if (isAutoCreate("t_delivery_aogorder")) {
				// 获取自动编号
				String id = getAutoCreateSysNumbderForeign(deliveryAogreturn, "t_delivery_aogreturn");
				deliveryAogreturn.setId(id);
			} else {
				deliveryAogreturn.setId("THDD-" + CommonUtils.getDataNumberSendsWithRand());
			}
			deliveryAogreturn.setAddtime(new Date());
			deliveryAogreturn.setBusinessdate(CommonUtils.getTodayDataStr());
			BigDecimal totalBox = new BigDecimal("0");// 总箱数
			BigDecimal totalWeight = new BigDecimal("0");// 总重量
			BigDecimal totalVolume = new BigDecimal("0");// 总体积
			BigDecimal totalaMount = new BigDecimal("0");// 总金额
			for (DeliveryAogreturnDetail deliveryAogreturnDetail : detailList) {
				deliveryAogreturnDetail.setBillid(deliveryAogreturn.getId());
				GoodsInfo goodsInfo = getGoodsInfoByID(deliveryAogreturnDetail.getGoodsid());
				deliveryAogreturnDetail.setTotalbox(deliveryAogreturnDetail.getAuxnum().add(deliveryAogreturnDetail.getOvernum().divide(goodsInfo.getBoxnum(),  2, BigDecimal.ROUND_HALF_UP)));
				// 获取总箱数
				totalBox = totalBox.add(deliveryAogreturnDetail.getTotalbox());
				// 获取总重量
				if (goodsInfo.getGrossweight() != null)
					totalWeight = totalWeight.add(goodsInfo.getGrossweight().multiply(deliveryAogreturnDetail.getUnitnum()));
				// 获取总体积
				if (goodsInfo.getSinglevolume() != null)
					totalVolume = totalVolume.add(goodsInfo.getSinglevolume().multiply(deliveryAogreturnDetail.getUnitnum()));
				// 获取总金额
				totalaMount = totalaMount.add(deliveryAogreturnDetail.getTaxamount());
				deliveryAogreturnMapper.addDeliveryAogreturnDetail(deliveryAogreturnDetail);
			}
			SysUser sysUser = getSysUser();
			deliveryAogreturn.setAdddeptid(sysUser.getDepartmentid());
			deliveryAogreturn.setAdddeptname(sysUser.getDepartmentname());
			deliveryAogreturn.setAdduserid(sysUser.getUserid());
			deliveryAogreturn.setAddusername(sysUser.getName());
			deliveryAogreturn.setTotalbox(totalBox);
			deliveryAogreturn.setTotalweight(totalWeight);
			deliveryAogreturn.setTotalvolume(totalVolume);
			deliveryAogreturn.setTotalamount(totalaMount);
			int i = deliveryAogreturnMapper.addDeliveryAogreturn(deliveryAogreturn);
			flag = i > 0;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("id", deliveryAogreturn.getId());
		return map;
	}

	@Override
	public Map auditDeliveryAogreturn(String id) throws Exception {

		boolean flag=false;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		String businessdate = dateFormat.format(calendar.getTime());
		SysUser sysUser = getSysUser();
		DeliveryAogreturn deliveryAogreturn = deliveryAogreturnMapper.getDeliveryAogreturnByID(id);
		if ("2".equals(deliveryAogreturn.getStatus())) {
			int i = deliveryAogreturnMapper.auditDeliveryAogreturn(id, sysUser.getUserid(), sysUser.getName(), new Date(), businessdate);
			flag = i > 0;
		} else {
			flag = false;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		return map;
	}

	@Override
	public Map getDeliveryAogreturnInfo(String id) throws Exception {
		DeliveryAogreturn deliveryAogreturn = deliveryAogreturnMapper.getDeliveryAogreturnByID(id);
		List<DeliveryAogreturnDetail> list = deliveryAogreturnMapper.getDeliveryAogreturnDetailList(id);
		for (DeliveryAogreturnDetail deliveryAogreturnDetail : list) {
			GoodsInfo goodsInfo = getGoodsInfoByID(deliveryAogreturnDetail.getGoodsid());
			deliveryAogreturnDetail.setGoodsInfo(goodsInfo);
		}
		Map map = new HashMap();
		map.put("deliveryAogreturn", deliveryAogreturn);
		map.put("detailList", list);
		return map;
	}

	@Override
	public Map oppauditDeliveryAogreturn(String id) throws Exception {
		boolean flag=false;
		SysUser sysUser = getSysUser();
		StorageDeliveryOut storageDeliveryOut = new StorageDeliveryOut();
		storageDeliveryOut = storageDeliveryOutMapper.getStorageDeliveryOutBySourceId(id);
		if (null != storageDeliveryOut) {
			if ("2".equals(storageDeliveryOut.getStatus())) {
				int i = deliveryAogreturnMapper.oppauditDeliveryAogreturn(id, sysUser.getUserid(), sysUser.getName(), new Date());
				flag = i > 0;
				if (flag == true) {
					deliveryOutService.deleteOutAndDetailById(storageDeliveryOut.getId());
				}
			} else{
				flag = false;
			}
		}
		else{
			int i = deliveryAogreturnMapper.oppauditDeliveryAogreturn(id, sysUser.getUserid(), sysUser.getName(), new Date());
			flag = i > 0;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		return map;
	}

	@Override
	public Map editDeliveryAogreturn(DeliveryAogreturn deliveryAogreturn, List<DeliveryAogreturnDetail> detailList) throws Exception {
		String msg = "";
		boolean flag = false;
		DeliveryAogreturn checkDeliveryAogreturn = deliveryAogreturnMapper.getDeliveryAogreturnByID(deliveryAogreturn.getId());
		if ("2".equals(checkDeliveryAogreturn.getStatus())) {
		deliveryAogreturnMapper.deleteDeliveryAogreturnDetail(deliveryAogreturn.getId());
		BigDecimal totalBox = new BigDecimal("0");// 总箱数
		BigDecimal totalWeight = new BigDecimal("0");// 总重量
		BigDecimal totalVolume = new BigDecimal("0");// 总体积
		BigDecimal totalaMount = new BigDecimal("0");// 总金额
		for (DeliveryAogreturnDetail deliveryAogreturnDetail : detailList) {
			deliveryAogreturnDetail.setBillid(deliveryAogreturn.getId());
			GoodsInfo goodsInfo = getGoodsInfoByID(deliveryAogreturnDetail.getGoodsid());
			deliveryAogreturnDetail.setTotalbox(deliveryAogreturnDetail.getAuxnum().add(deliveryAogreturnDetail.getOvernum().divide(goodsInfo.getBoxnum(),2, BigDecimal.ROUND_HALF_UP)));
			// 获取总箱数
			totalBox = totalBox.add(deliveryAogreturnDetail.getTotalbox());
			// 获取总重量
			if (goodsInfo.getGrossweight() != null)
				totalWeight = totalWeight.add(goodsInfo.getGrossweight().multiply(deliveryAogreturnDetail.getUnitnum()));
			// 获取总体积
			if (goodsInfo.getSinglevolume() != null)
				totalVolume = totalVolume.add(goodsInfo.getSinglevolume().multiply(deliveryAogreturnDetail.getUnitnum()));
			// 获取总金额
			totalaMount = totalaMount.add(deliveryAogreturnDetail.getTaxamount());
			deliveryAogreturnMapper.addDeliveryAogreturnDetail(deliveryAogreturnDetail);
		}
		SysUser sysUser = getSysUser();
		deliveryAogreturn.setModifyuserid(sysUser.getUserid());
		deliveryAogreturn.setModifyusername(sysUser.getName());
		deliveryAogreturn.setModifytime(new Date());
		deliveryAogreturn.setTotalbox(totalBox);
		deliveryAogreturn.setTotalweight(totalWeight);
		deliveryAogreturn.setTotalvolume(totalVolume);
		deliveryAogreturn.setTotalamount(totalaMount);
		int i = deliveryAogreturnMapper.editDeliveryAogreturn(deliveryAogreturn);
		flag = i > 0;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("id", deliveryAogreturn.getId());
		return map;
	}

	@Override
	public boolean deleteDeliveryAogreturn(String id) throws Exception {
		DeliveryAogreturn deliveryAogreturn = deliveryAogreturnMapper.getDeliveryAogreturnByID(id);
		if ("2".equals(deliveryAogreturn.getStatus())&&deliveryAogreturn!=null) {
			deliveryAogreturnMapper.deleteDeliveryAogreturnDetail(id);
			int i = deliveryAogreturnMapper.deleteDeliveryAogreturn(id);
			if (i > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	// 客户订单
	@Override
	public PageData showDeliveryOrderList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_delivery_Order", "t");
		pageMap.setDataSql(dataSql);
		int count = deliveryOrderMapper.getDeliveryOrderListCount(pageMap);
		List<DeliveryOrder> list = deliveryOrderMapper.getDeliveryOrderList(pageMap);
		for (DeliveryOrder item : list) {
			StorageInfo storageInfo;
			BuySupplier buySupplier;
			if (StringUtils.isNotEmpty(item.getStorageid())) {
				storageInfo = getStorageInfoByID(item.getStorageid());
                if(null!=storageInfo)
				item.setStoragename(storageInfo.getName());
			}
			if (StringUtils.isNotEmpty(item.getSupplierid())) {
				buySupplier = getSupplierInfoById(item.getSupplierid());
				if(null!=buySupplier)
				item.setSuppliername(buySupplier.getName());
			}
		}
		PageData pageData = new PageData(count, list, pageMap);
		return pageData;
	}

	@Override
	public Map getDeliveryOrderInfo(String id) throws Exception {
		DeliveryOrder deliveryOrder = deliveryOrderMapper.getDeliveryOrderByID(id);
	
		List<DeliveryOrderDetail> list = deliveryOrderMapper.getDeliveryOrderDetailList(id);
		for (DeliveryOrderDetail deliveryOrderDetail : list) {
			GoodsInfo goodsInfo = getGoodsInfoByID(deliveryOrderDetail.getGoodsid());
			deliveryOrderDetail.setGoodsInfo(goodsInfo);
		}
		Map map = new HashMap();
		map.put("deliveryOrder", deliveryOrder);
		map.put("detailList", list);
		return map;
	}

	@Override
	public Map addDeliveryOrder(DeliveryOrder deliveryOrder, List<DeliveryOrderDetail> detailList) throws Exception {
		String msg = "";
		boolean flag = false;
		boolean canAudit = true;
		if (canAudit) {
			if (isAutoCreate("t_delivery_order")) {
				// 获取自动编号
				String id = getAutoCreateSysNumbderForeign(deliveryOrder, "t_delivery_order");
				deliveryOrder.setId(id);
			} else {
				deliveryOrder.setId("KHDD-" + CommonUtils.getDataNumberSendsWithRand());
			}
			deliveryOrder.setAddtime(new Date());
			deliveryOrder.setBusinessdate(CommonUtils.getTodayDataStr());
			BigDecimal totalBox = new BigDecimal("0");// 总箱数
			BigDecimal totalWeight = new BigDecimal("0");// 总重量
			BigDecimal totalVolume = new BigDecimal("0");// 总体积
			BigDecimal totalaMount = new BigDecimal("0");// 总金额
			for (DeliveryOrderDetail deliveryOrderDetail : detailList) {
				deliveryOrderDetail.setBillid(deliveryOrder.getId());
				GoodsInfo goodsInfo = getGoodsInfoByID(deliveryOrderDetail.getGoodsid());
				deliveryOrderDetail.setTotalbox(deliveryOrderDetail.getAuxnum().add(deliveryOrderDetail.getOvernum().divide(goodsInfo.getBoxnum(),2, BigDecimal.ROUND_HALF_UP)));
				// 获取总箱数
				totalBox = totalBox.add(deliveryOrderDetail.getTotalbox());
				// 获取总重量
				if (goodsInfo.getGrossweight() != null)
					totalWeight = totalWeight.add(goodsInfo.getGrossweight().multiply(deliveryOrderDetail.getUnitnum()));
				// 获取总体积
				if (goodsInfo.getSinglevolume() != null)
					totalVolume = totalVolume.add(goodsInfo.getSinglevolume().multiply(deliveryOrderDetail.getUnitnum()));
				// 获取总金额
				totalaMount = totalaMount.add(deliveryOrderDetail.getTaxamount());
				deliveryOrderMapper.addDeliveryOrderDetail(deliveryOrderDetail);
			}
			SysUser sysUser = getSysUser();
			deliveryOrder.setAdddeptid(sysUser.getDepartmentid());
			deliveryOrder.setAdddeptname(sysUser.getDepartmentname());
			deliveryOrder.setAdduserid(sysUser.getUserid());
			deliveryOrder.setAddusername(sysUser.getName());
			deliveryOrder.setTotalbox(totalBox);
			deliveryOrder.setTotalweight(totalWeight);
			deliveryOrder.setTotalvolume(totalVolume);
			deliveryOrder.setTotalamount(totalaMount);
			int i = deliveryOrderMapper.addDeliveryOrder(deliveryOrder);
			flag = i > 0;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("id", deliveryOrder.getId());
		return map;
	}

	@Override
	public Map auditDeliveryOrder(String id) throws Exception {
		boolean flag=false;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		String businessdate = dateFormat.format(calendar.getTime());
		SysUser sysUser = getSysUser();
		DeliveryOrder deliveryOrder = deliveryOrderMapper.getDeliveryOrderByID(id);
		if ("2".equals(deliveryOrder.getStatus())) {
			int i = deliveryOrderMapper.auditDeliveryOrder(id, sysUser.getUserid(), sysUser.getName(), new Date(), businessdate);
			flag = i > 0;
		} else {
			flag = false;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		return map;
	}

	@Override
	public Map editDeliveryOrder(DeliveryOrder deliveryOrder, List<DeliveryOrderDetail> detailList) throws Exception {
		String msg = "";
		boolean flag = false;
		DeliveryOrder checkDeliveryOrder = deliveryOrderMapper.getDeliveryOrderByID(deliveryOrder.getId());
		if ("2".equals(checkDeliveryOrder.getStatus())) {
		deliveryOrderMapper.deleteDeliveryOrderDetail(deliveryOrder.getId());
		BigDecimal totalBox = new BigDecimal("0");// 总箱数
		BigDecimal totalWeight = new BigDecimal("0");// 总重量
		BigDecimal totalVolume = new BigDecimal("0");// 总体积
		BigDecimal totalaMount = new BigDecimal("0");// 总金额
		for (DeliveryOrderDetail deliveryOrderDetail : detailList) {
			deliveryOrderDetail.setBillid(deliveryOrder.getId());
			GoodsInfo goodsInfo = getGoodsInfoByID(deliveryOrderDetail.getGoodsid());
			deliveryOrderDetail.setTotalbox(deliveryOrderDetail.getAuxnum().add(deliveryOrderDetail.getOvernum().divide(goodsInfo.getBoxnum(), 2, BigDecimal.ROUND_HALF_UP)));
			// 获取总箱数
			totalBox = totalBox.add(deliveryOrderDetail.getTotalbox());
			// 获取总重量
			if (goodsInfo.getGrossweight() != null)
				totalWeight = totalWeight.add(goodsInfo.getGrossweight().multiply(deliveryOrderDetail.getUnitnum()));
			// 获取总体积
			if (goodsInfo.getSinglevolume() != null)
				totalVolume = totalVolume.add(goodsInfo.getSinglevolume().multiply(deliveryOrderDetail.getUnitnum()));
			// 获取总金额
			totalaMount = totalaMount.add(deliveryOrderDetail.getTaxamount());
			deliveryOrderMapper.addDeliveryOrderDetail(deliveryOrderDetail);
		}
		SysUser sysUser = getSysUser();
		deliveryOrder.setModifyuserid(sysUser.getUserid());
		deliveryOrder.setModifyusername(sysUser.getName());
		deliveryOrder.setModifytime(new Date());
		deliveryOrder.setTotalbox(totalBox);
		deliveryOrder.setTotalweight(totalWeight);
		deliveryOrder.setTotalvolume(totalVolume);
		deliveryOrder.setTotalamount(totalaMount);
		int i = deliveryOrderMapper.editDeliveryOrder(deliveryOrder);
		flag = i > 0;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("id", deliveryOrder.getId());
		return map;
	}

	@Override
	public boolean deleteDeliveryOrder(String id) throws Exception {
		DeliveryOrder deliveryOrder = deliveryOrderMapper.getDeliveryOrderByID(id);
		if ("2".equals(deliveryOrder.getStatus())&&deliveryOrder!=null) {
			deliveryOrderMapper.deleteDeliveryOrderDetail(id);
			int i = deliveryOrderMapper.deleteDeliveryOrder(id);
			if (i > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public Map oppauditDeliveryOrder(String id) throws Exception {
		boolean flag=false;
		SysUser sysUser = getSysUser();
		StorageDeliveryOut storageDeliveryOut = new StorageDeliveryOut();
		storageDeliveryOut = storageDeliveryOutMapper.getStorageDeliveryOutBySourceId(id);
		if (null != storageDeliveryOut) {
			if ("2".equals(storageDeliveryOut.getStatus())) {
				int i = deliveryOrderMapper.oppauditDeliveryOrder(id, sysUser.getUserid(), sysUser.getName(), new Date());
				flag = i > 0;
				if (flag == true) {
					deliveryOutService.deleteOutAndDetailById(storageDeliveryOut.getId());
				}
			} else {
				flag = false;
			}
		} else {
			int i = deliveryOrderMapper.oppauditDeliveryOrder(id, sysUser.getUserid(), sysUser.getName(), new Date());
			flag = i > 0;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		return map;
	}

	// 客户退货订单
	@Override
	public PageData showDeliveryRejectbillList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_delivery_rejectbill", "t");
		pageMap.setDataSql(dataSql);
		int count = deliveryRejectbillMapper.getDeliveryRejectbillListCount(pageMap);
		List<DeliveryRejectbill> list = deliveryRejectbillMapper.getDeliveryRejectbillList(pageMap);
		for (DeliveryRejectbill item : list) {
			StorageInfo storageInfo;
			BuySupplier buySupplier;
			if (StringUtils.isNotEmpty(item.getStorageid())) {
				storageInfo = getStorageInfoByID(item.getStorageid());
				 if(null!=storageInfo)
				item.setStoragename(storageInfo.getName());
			}
			if (StringUtils.isNotEmpty(item.getSupplierid())) {
				buySupplier = getSupplierInfoById(item.getSupplierid());
				if(null!=buySupplier)
				item.setSuppliername(buySupplier.getName());
			}
			
		}
		PageData pageData = new PageData(count, list, pageMap);
		return pageData;
	}

	@Override
	public Map addDeliveryRejectbill(DeliveryRejectbill deliveryRejectbill, List<DeliveryRejectbillDetail> detailList) throws Exception {
		String msg = "";
		boolean flag = false;
		boolean canAudit = true;
		if (canAudit) {
			if (isAutoCreate("t_delivery_rejectbill")) {
				// 获取自动编号
				String id = getAutoCreateSysNumbderForeign(deliveryRejectbill, "t_delivery_rejectbill");
				deliveryRejectbill.setId(id);
			} else {
				deliveryRejectbill.setId("KHTH-" + CommonUtils.getDataNumberSendsWithRand());
			}
			deliveryRejectbill.setAddtime(new Date());
			deliveryRejectbill.setBusinessdate(CommonUtils.getTodayDataStr());
			BigDecimal totalBox = new BigDecimal("0");// 总箱数
			BigDecimal totalWeight = new BigDecimal("0");// 总重量
			BigDecimal totalVolume = new BigDecimal("0");// 总体积
			BigDecimal totalaMount = new BigDecimal("0");// 总金额
			for (DeliveryRejectbillDetail deliveryRejectbillDetail : detailList) {
				deliveryRejectbillDetail.setBillid(deliveryRejectbill.getId());
				GoodsInfo goodsInfo = getGoodsInfoByID(deliveryRejectbillDetail.getGoodsid());
				deliveryRejectbillDetail.setTotalbox(deliveryRejectbillDetail.getAuxnum().add(deliveryRejectbillDetail.getOvernum().divide(goodsInfo.getBoxnum(), 2, BigDecimal.ROUND_HALF_UP)));
				// 获取总箱数
				totalBox = totalBox.add(deliveryRejectbillDetail.getTotalbox());
				// 获取总重量
				if (goodsInfo.getGrossweight() != null)
					totalWeight = totalWeight.add(goodsInfo.getGrossweight().multiply(deliveryRejectbillDetail.getUnitnum()));
				// 获取总体积
				if (goodsInfo.getSinglevolume() != null)
					totalVolume = totalVolume.add(goodsInfo.getSinglevolume().multiply(deliveryRejectbillDetail.getUnitnum()));
				// 获取总金额
				totalaMount = totalaMount.add(deliveryRejectbillDetail.getTaxamount());
				deliveryRejectbillMapper.addDeliveryRejectbillDetail(deliveryRejectbillDetail);
			}
			SysUser sysUser = getSysUser();
			deliveryRejectbill.setAdddeptid(sysUser.getDepartmentid());
			deliveryRejectbill.setAdddeptname(sysUser.getDepartmentname());
			deliveryRejectbill.setAdduserid(sysUser.getUserid());
			deliveryRejectbill.setAddusername(sysUser.getName());
			deliveryRejectbill.setTotalbox(totalBox);
			deliveryRejectbill.setTotalweight(totalWeight);
			deliveryRejectbill.setTotalvolume(totalVolume);
			deliveryRejectbill.setTotalamount(totalaMount);
			int i = deliveryRejectbillMapper.addDeliveryRejectbill(deliveryRejectbill);
			flag = i > 0;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("id", deliveryRejectbill.getId());
		return map;
	}

	@Override
	public Map auditDeliveryRejectbill(String id) throws Exception {
		boolean flag=false;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		String businessdate = dateFormat.format(calendar.getTime());
		SysUser sysUser = getSysUser();
		DeliveryRejectbill deliveryRejectbill = deliveryRejectbillMapper.getDeliveryRejectbillByID(id);
		if ("2".equals(deliveryRejectbill.getStatus())) {
			int i = deliveryRejectbillMapper.auditDeliveryRejectbill(id, sysUser.getUserid(), sysUser.getName(), new Date(), businessdate);
			flag = i > 0;
		} else {
			flag = false;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		return map;
	}

	@Override
	public Map getDeliveryRejectbillInfo(String id) throws Exception {
		DeliveryRejectbill deliveryRejectbill = deliveryRejectbillMapper.getDeliveryRejectbillByID(id);
		
		List<DeliveryRejectbillDetail> list = deliveryRejectbillMapper.getDeliveryRejectbillDetailList(id);
		for (DeliveryRejectbillDetail deliveryRejectbillDetail : list) {
			GoodsInfo goodsInfo = getGoodsInfoByID(deliveryRejectbillDetail.getGoodsid());
			deliveryRejectbillDetail.setGoodsInfo(goodsInfo);
		}
		Map map = new HashMap();
		map.put("deliveryRejectbill", deliveryRejectbill);
		map.put("detailList", list);
		return map;
	}

	@Override
	public Map editDeliveryRejectbill(DeliveryRejectbill deliveryRejectbill, List<DeliveryRejectbillDetail> detailList) throws Exception {
		String msg = "";
		boolean flag = false;
		DeliveryRejectbill checkDeliveryRejectbill = deliveryRejectbillMapper.getDeliveryRejectbillByID(deliveryRejectbill.getId());
		if ("2".equals(checkDeliveryRejectbill.getStatus())) {
		deliveryRejectbillMapper.deleteDeliveryRejectbillDetail(deliveryRejectbill.getId());
		BigDecimal totalBox = new BigDecimal("0");// 总箱数
		BigDecimal totalWeight = new BigDecimal("0");// 总重量
		BigDecimal totalVolume = new BigDecimal("0");// 总体积
		BigDecimal totalaMount = new BigDecimal("0");// 总金额
		for (DeliveryRejectbillDetail deliveryRejectbillDetail : detailList) {
			deliveryRejectbillDetail.setBillid(deliveryRejectbill.getId());
			GoodsInfo goodsInfo = getGoodsInfoByID(deliveryRejectbillDetail.getGoodsid());
			deliveryRejectbillDetail.setTotalbox(deliveryRejectbillDetail.getAuxnum().add(deliveryRejectbillDetail.getOvernum().divide(goodsInfo.getBoxnum(), 2, BigDecimal.ROUND_HALF_UP)));
			// 获取总箱数
			totalBox = totalBox.add(deliveryRejectbillDetail.getTotalbox());
			// 获取总重量
			if (goodsInfo.getGrossweight() != null)
				totalWeight = totalWeight.add(goodsInfo.getGrossweight().multiply(deliveryRejectbillDetail.getUnitnum()));
			// 获取总体积
			if (goodsInfo.getSinglevolume() != null)
				totalVolume = totalVolume.add(goodsInfo.getSinglevolume().multiply(deliveryRejectbillDetail.getUnitnum()));
			// 获取总金额
			totalaMount = totalaMount.add(deliveryRejectbillDetail.getTaxamount());
			deliveryRejectbillMapper.addDeliveryRejectbillDetail(deliveryRejectbillDetail);
		}
		SysUser sysUser = getSysUser();
		deliveryRejectbill.setModifyuserid(sysUser.getUserid());
		deliveryRejectbill.setModifyusername(sysUser.getName());
		deliveryRejectbill.setModifytime(new Date());
		deliveryRejectbill.setTotalbox(totalBox);
		deliveryRejectbill.setTotalweight(totalWeight);
		deliveryRejectbill.setTotalvolume(totalVolume);
		deliveryRejectbill.setTotalamount(totalaMount);
		int i = deliveryRejectbillMapper.editDeliveryRejectbill(deliveryRejectbill);
		flag = i > 0;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("id", deliveryRejectbill.getId());
		return map;
	}

	@Override
	public boolean deleteDeliveryRejectbill(String id) throws Exception {
		DeliveryRejectbill deliveryRejectbill = deliveryRejectbillMapper.getDeliveryRejectbillByID(id);
		if ("2".equals(deliveryRejectbill.getStatus())&&deliveryRejectbill!=null) {
			deliveryRejectbillMapper.deleteDeliveryRejectbillDetail(id);
			int i = deliveryRejectbillMapper.deleteDeliveryRejectbill(id);
			if (i > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public Map oppauditDeliveryRejectbill(String id) throws Exception {
		boolean flag=false;
		SysUser sysUser = getSysUser();
		StorageDeliveryEnter storageDeliveryEnter = new StorageDeliveryEnter();
		storageDeliveryEnter = storageDeliveryEnterMapper.getStorageDeliveryEnterBySourceId(id);
		if (null != storageDeliveryEnter) {
			if ("2".equals(storageDeliveryEnter.getStatus())) {
				int i = deliveryRejectbillMapper.oppauditDeliveryRejectbill(id, sysUser.getUserid(), sysUser.getName(), new Date());
				flag = i > 0;
				if (flag == true) {
					distributeRejectService.deleteEnterAndDetailById(storageDeliveryEnter.getId());
				}
			} else {
				flag = false;
			}
		} else {
			int i = deliveryRejectbillMapper.oppauditDeliveryRejectbill(id, sysUser.getUserid(), sysUser.getName(), new Date());
			flag = i > 0;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		return map;
	}

	/**
	 * 到货订单审核后添加到供应商代配送入库单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 24, 2015
	 */
	@Override
	public boolean addDistributeRejectByAogorder(DeliveryAogorder deliveryAogorder, List detailList) throws Exception {
		// 到货订单转换成供应商代配送入库单
		StorageDeliveryEnter storageDeliveryEnter = new StorageDeliveryEnter();
		storageDeliveryEnter.setBusinessdate(CommonUtils.getTodayDataStr());
		storageDeliveryEnter.setStatus("2");
		storageDeliveryEnter.setRemark(deliveryAogorder.getRemark());
		storageDeliveryEnter.setAdduserid(deliveryAogorder.getAdduserid());
		storageDeliveryEnter.setAddusername(deliveryAogorder.getAddusername());
		storageDeliveryEnter.setAdddeptid(deliveryAogorder.getAdddeptid());
		storageDeliveryEnter.setAdddeptname(deliveryAogorder.getAdddeptname());
		storageDeliveryEnter.setAddtime(new Date());
		storageDeliveryEnter.setModifyuserid(deliveryAogorder.getModifyuserid());
		storageDeliveryEnter.setModifyusername(deliveryAogorder.getModifyusername());
		storageDeliveryEnter.setModifytime(deliveryAogorder.getModifytime());
		storageDeliveryEnter.setAudituserid(deliveryAogorder.getAudituserid());
		storageDeliveryEnter.setAuditusername(deliveryAogorder.getAuditusername());
		storageDeliveryEnter.setAudittime(deliveryAogorder.getAudittime());
		storageDeliveryEnter.setStopuserid(deliveryAogorder.getStopuserid());
		storageDeliveryEnter.setStopusername(deliveryAogorder.getStopusername());
		storageDeliveryEnter.setStoptime(deliveryAogorder.getStoptime());
		storageDeliveryEnter.setClosetime(deliveryAogorder.getClosetime());
		storageDeliveryEnter.setPrinttimes(deliveryAogorder.getPrinttimes());
		storageDeliveryEnter.setBilltype("1");
		storageDeliveryEnter.setSourcetype("1");
		storageDeliveryEnter.setSourceid(deliveryAogorder.getId());
		storageDeliveryEnter.setSupplierid(deliveryAogorder.getSupplierid());
		storageDeliveryEnter.setSuppliername(deliveryAogorder.getSuppliername());
		storageDeliveryEnter.setStorageid(deliveryAogorder.getStorageid());
		storageDeliveryEnter.setStoragename(deliveryAogorder.getStoragename());
		storageDeliveryEnter.setTotalamount(deliveryAogorder.getTotalamount());
		storageDeliveryEnter.setTotalbox(deliveryAogorder.getTotalbox());
		storageDeliveryEnter.setTotalvolume(deliveryAogorder.getTotalvolume());
		storageDeliveryEnter.setTotalweight(deliveryAogorder.getTotalweight());
		// 到货订单明细转换成供应商代配送入库单明细
		List<StorageDeliveryEnterDetail> list = new ArrayList();
		for (Object obj : detailList) {
			DeliveryAogorderDetail aogorderDetail = (DeliveryAogorderDetail) obj;
			StorageDeliveryEnterDetail storageDetail = new StorageDeliveryEnterDetail();
			storageDetail.setSourcebilldetailid(aogorderDetail.getId() + "");
			storageDetail.setSourcebillid(deliveryAogorder.getId());
			storageDetail.setGoodsid(aogorderDetail.getGoodsid());
			storageDetail.setGoodssort(aogorderDetail.getGoodssort());
			storageDetail.setBrandid(aogorderDetail.getBrandid());
			storageDetail.setUnitid(aogorderDetail.getUnitid());
			storageDetail.setUnitname(aogorderDetail.getUnitname());
			storageDetail.setUnitnum(aogorderDetail.getUnitnum());
			storageDetail.setAuxunitid(aogorderDetail.getAuxunitid());
			storageDetail.setAuxunitname(aogorderDetail.getAuxunitname());
			storageDetail.setAuxnum(aogorderDetail.getAuxnum());
			storageDetail.setAuxnumdetail(aogorderDetail.getAuxnumdetail());
			storageDetail.setOvernum(aogorderDetail.getOvernum());
			storageDetail.setTotalbox(aogorderDetail.getTotalbox());
			GoodsInfo goodsInfo =getAllGoodsInfoByID(aogorderDetail.getGoodsid());
			storageDetail.setBuyprice(goodsInfo.getHighestbuyprice());
			storageDetail.setPrice(aogorderDetail.getPrice());
			storageDetail.setTaxamount(aogorderDetail.getTaxamount());
			storageDetail.setSeq(aogorderDetail.getSeq());
			storageDetail.setRemark(aogorderDetail.getRemark());
            storageDetail.setAddcostprice(getGoodsCostprice(deliveryAogorder.getStorageid(),goodsInfo));
            
            storageDetail.setProduceddate(aogorderDetail.getProduceddate());
            storageDetail.setDeadline(aogorderDetail.getDeadline());
            storageDetail.setBatchno(aogorderDetail.getBatchno());
            
            if(StringUtils.isEmpty(aogorderDetail.getBatchno())){
            	StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchByStorageidAndGoodsid(deliveryAogorder.getStorageid(),aogorderDetail.getGoodsid());
            	if(null!=storageSummaryBatch){
            	    storageDetail.setSummarybatchid(storageSummaryBatch.getId());
            	}
            }else{
            	//批次管理时(入库), getStorageSummaryBatchByStorageidAndBatchno 为null
//            	storageDetail.setSummarybatchid(getStorageSummaryBatchByStorageidAndBatchno(deliveryAogorder.getStorageid(),aogorderDetail.getBatchno(),aogorderDetail.getGoodsid()).getId());
            }
            
			list.add(storageDetail);
		}
		boolean flag=distributeRejectService.insertDatas(storageDeliveryEnter, list);
		return flag;

	}

	/**
	 * 退货订单审核后添加到供应商代配送出库单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 24, 2015
	 */
	@Override
	public Map<String,Object> addDistributeOutByAogreturn(DeliveryAogreturn deliveryAogreturn, List detailList) throws Exception {
		// 退货订单转换成供应商代配送出库单
		StorageDeliveryOut storageDeliveryOut = new StorageDeliveryOut();
		storageDeliveryOut.setBusinessdate(CommonUtils.getTodayDataStr());
		storageDeliveryOut.setStatus("2");
		storageDeliveryOut.setRemark(deliveryAogreturn.getRemark());
		storageDeliveryOut.setAdduserid(deliveryAogreturn.getAdduserid());
		storageDeliveryOut.setAddusername(deliveryAogreturn.getAddusername());
		storageDeliveryOut.setAdddeptid(deliveryAogreturn.getAdddeptid());
		storageDeliveryOut.setAdddeptname(deliveryAogreturn.getAdddeptname());
		storageDeliveryOut.setAddtime(new Date());
		storageDeliveryOut.setModifyuserid(deliveryAogreturn.getModifyuserid());
		storageDeliveryOut.setModifyusername(deliveryAogreturn.getModifyusername());
		storageDeliveryOut.setModifytime(deliveryAogreturn.getModifytime());
		storageDeliveryOut.setAudituserid(deliveryAogreturn.getAudituserid());
		storageDeliveryOut.setAuditusername(deliveryAogreturn.getAuditusername());
		storageDeliveryOut.setAudittime(deliveryAogreturn.getAudittime());
		storageDeliveryOut.setStopuserid(deliveryAogreturn.getStopuserid());
		storageDeliveryOut.setStopusername(deliveryAogreturn.getStopusername());
		storageDeliveryOut.setStoptime(deliveryAogreturn.getStoptime());
		storageDeliveryOut.setClosetime(deliveryAogreturn.getClosetime());
		storageDeliveryOut.setPrinttimes(deliveryAogreturn.getPrinttimes());
		storageDeliveryOut.setBilltype("1");
		storageDeliveryOut.setSourcetype("1");
		storageDeliveryOut.setSourceid(deliveryAogreturn.getId());
		storageDeliveryOut.setSupplierid(deliveryAogreturn.getSupplierid());
		storageDeliveryOut.setSuppliername(deliveryAogreturn.getSuppliername());
		storageDeliveryOut.setStorageid(deliveryAogreturn.getStorageid());
		storageDeliveryOut.setStoragename(deliveryAogreturn.getStoragename());
		storageDeliveryOut.setTotalamount(deliveryAogreturn.getTotalamount());
		storageDeliveryOut.setTotalbox(deliveryAogreturn.getTotalbox());
		storageDeliveryOut.setTotalvolume(deliveryAogreturn.getTotalvolume());
		storageDeliveryOut.setTotalweight(deliveryAogreturn.getTotalweight());
		// 退货订单明细转换成供应商代配送出库单明细
		List<StorageDeliveryOutDetail> list = new ArrayList();
		for (Object obj : detailList) {
			DeliveryAogreturnDetail aogreturnDetail = (DeliveryAogreturnDetail) obj;
			StorageDeliveryOutDetail storageDeliveryOutDetail = new StorageDeliveryOutDetail();
			storageDeliveryOutDetail.setSourcebilldetailid(aogreturnDetail.getId() + "");
			storageDeliveryOutDetail.setSourcebillid(deliveryAogreturn.getId());
			storageDeliveryOutDetail.setGoodsid(aogreturnDetail.getGoodsid());
			storageDeliveryOutDetail.setGoodssort(aogreturnDetail.getGoodssort());
			storageDeliveryOutDetail.setBrandid(aogreturnDetail.getBrandid());
			storageDeliveryOutDetail.setUnitid(aogreturnDetail.getUnitid());
			storageDeliveryOutDetail.setUnitname(aogreturnDetail.getUnitname());
			storageDeliveryOutDetail.setUnitnum(aogreturnDetail.getUnitnum());
			storageDeliveryOutDetail.setAuxunitid(aogreturnDetail.getAuxunitid());
			storageDeliveryOutDetail.setAuxunitname(aogreturnDetail.getAuxunitname());
			storageDeliveryOutDetail.setAuxnum(aogreturnDetail.getAuxnum());
			storageDeliveryOutDetail.setAuxnumdetail(aogreturnDetail.getAuxnumdetail());
			storageDeliveryOutDetail.setOvernum(aogreturnDetail.getOvernum());
			storageDeliveryOutDetail.setTotalbox(aogreturnDetail.getTotalbox());
			storageDeliveryOutDetail.setPrice(aogreturnDetail.getPrice());
			GoodsInfo goodsInfo =getAllGoodsInfoByID(aogreturnDetail.getGoodsid());
			storageDeliveryOutDetail.setBuyprice(goodsInfo.getHighestbuyprice());
			storageDeliveryOutDetail.setTaxamount(aogreturnDetail.getTaxamount());
			storageDeliveryOutDetail.setSeq(aogreturnDetail.getSeq());
			storageDeliveryOutDetail.setRemark(aogreturnDetail.getRemark());
            storageDeliveryOutDetail.setAddcostprice(getGoodsCostprice(deliveryAogreturn.getStorageid(),goodsInfo));
            
            storageDeliveryOutDetail.setProduceddate(aogreturnDetail.getProduceddate());
            storageDeliveryOutDetail.setDeadline(aogreturnDetail.getDeadline());
            storageDeliveryOutDetail.setBatchno(aogreturnDetail.getBatchno());
            //出库
            if(StringUtils.isEmpty(aogreturnDetail.getBatchno())){
            	//非批次管理
            	StorageSummaryBatch storageSummaryBatch=getStorageSummaryBatchByStorageidAndGoodsid(deliveryAogreturn.getStorageid(),aogreturnDetail.getGoodsid());
            	if(null!=storageSummaryBatch){
            	    storageDeliveryOutDetail.setSummarybatchid(storageSummaryBatch.getId());
            	}
            }else{
            	//批次管理
            	StorageSummaryBatch batch = getStorageSummaryBatchByStorageidAndBatchno(deliveryAogreturn.getStorageid(),aogreturnDetail.getBatchno(),aogreturnDetail.getGoodsid());
            	//商品原先为非批次管理,后来为批次管理batch会为null
            	if(batch == null ){
            		batch = getStorageSummaryBatchByStorageidAndGoodsid(deliveryAogreturn.getStorageid(),aogreturnDetail.getGoodsid());
            	}
            	storageDeliveryOutDetail.setSummarybatchid(batch.getId());
            }
            
			list.add(storageDeliveryOutDetail);
		}
		Map<String,Object> map=deliveryOutService.insertDatas(storageDeliveryOut, list);
		return map;

	}

	/**
	 * 客户订单审核后添加到供应商代配送出库单
	 * 
	 * @param deliveryOrder
	 *            ,detailList
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 24, 2015
	 */
	@Override
	public Map<String,Object> addDistributeOutByOrder(DeliveryOrder deliveryOrder, List detailList) throws Exception {
		// 客户订单转换成供应商代配送出库单
		StorageDeliveryOut storageDeliveryOut = new StorageDeliveryOut();
		storageDeliveryOut.setBusinessdate(CommonUtils.getTodayDataStr());
		storageDeliveryOut.setStatus("2");
		storageDeliveryOut.setRemark(deliveryOrder.getRemark());
		storageDeliveryOut.setAdduserid(deliveryOrder.getAdduserid());
		storageDeliveryOut.setAddusername(deliveryOrder.getAddusername());
		storageDeliveryOut.setAdddeptid(deliveryOrder.getAdddeptid());
		storageDeliveryOut.setAdddeptname(deliveryOrder.getAdddeptname());
		storageDeliveryOut.setAddtime(new Date());
		storageDeliveryOut.setModifyuserid(deliveryOrder.getModifyuserid());
		storageDeliveryOut.setModifyusername(deliveryOrder.getModifyusername());
		storageDeliveryOut.setModifytime(deliveryOrder.getModifytime());
		storageDeliveryOut.setAudituserid(deliveryOrder.getAudituserid());
		storageDeliveryOut.setAuditusername(deliveryOrder.getAuditusername());
		storageDeliveryOut.setAudittime(deliveryOrder.getAudittime());
		storageDeliveryOut.setStopuserid(deliveryOrder.getStopuserid());
		storageDeliveryOut.setStopusername(deliveryOrder.getStopusername());
		storageDeliveryOut.setStoptime(deliveryOrder.getStoptime());
		storageDeliveryOut.setClosetime(deliveryOrder.getClosetime());
		storageDeliveryOut.setPrinttimes(deliveryOrder.getPrinttimes());
		storageDeliveryOut.setBilltype("2");
		storageDeliveryOut.setSourcetype("2");
		storageDeliveryOut.setSourceid(deliveryOrder.getId());
		storageDeliveryOut.setSupplierid(deliveryOrder.getSupplierid());
		storageDeliveryOut.setSuppliername(deliveryOrder.getSuppliername());
		storageDeliveryOut.setStorageid(deliveryOrder.getStorageid());
		storageDeliveryOut.setStoragename(deliveryOrder.getStoragename());
		storageDeliveryOut.setTotalamount(deliveryOrder.getTotalamount());
		storageDeliveryOut.setTotalbox(deliveryOrder.getTotalbox());
		storageDeliveryOut.setTotalvolume(deliveryOrder.getTotalvolume());
		storageDeliveryOut.setTotalweight(deliveryOrder.getTotalweight());
		storageDeliveryOut.setCustomerid(deliveryOrder.getCustomerid());
		storageDeliveryOut.setPcustomerid(deliveryOrder.getPcustomerid());
		storageDeliveryOut.setCustomername(deliveryOrder.getCustomername());
		storageDeliveryOut.setCustomersort(deliveryOrder.getCustomersort());
		storageDeliveryOut.setDeptid(deliveryOrder.getDeptid());
		storageDeliveryOut.setSupplierid(deliveryOrder.getSupplierid());
		storageDeliveryOut.setCustomerbill(deliveryOrder.getSourceid());
		// 客户订单明细转换成供应商代配送出库单明细
		List<StorageDeliveryOutDetail> list = new ArrayList();
		for (Object obj : detailList) {
			DeliveryOrderDetail oderDetail = (DeliveryOrderDetail) obj;
			StorageDeliveryOutDetail storageDeliveryOutDetail = new StorageDeliveryOutDetail();
			storageDeliveryOutDetail.setSourcebilldetailid(oderDetail.getId() + "");
			storageDeliveryOutDetail.setSourcebillid(deliveryOrder.getId());
			storageDeliveryOutDetail.setGoodsid(oderDetail.getGoodsid());
			storageDeliveryOutDetail.setGoodssort(oderDetail.getGoodssort());
			storageDeliveryOutDetail.setBrandid(oderDetail.getBrandid());
			storageDeliveryOutDetail.setUnitid(oderDetail.getUnitid());
			storageDeliveryOutDetail.setUnitname(oderDetail.getUnitname());
			storageDeliveryOutDetail.setUnitnum(oderDetail.getUnitnum());
			storageDeliveryOutDetail.setAuxunitid(oderDetail.getAuxunitid());
			storageDeliveryOutDetail.setAuxunitname(oderDetail.getAuxunitname());
			storageDeliveryOutDetail.setAuxnum(oderDetail.getAuxnum());
			storageDeliveryOutDetail.setAuxnumdetail(oderDetail.getAuxnumdetail());
			storageDeliveryOutDetail.setOvernum(oderDetail.getOvernum());
			storageDeliveryOutDetail.setTotalbox(oderDetail.getTotalbox());
			storageDeliveryOutDetail.setPrice(oderDetail.getPrice());
			GoodsInfo goodsInfo =getAllGoodsInfoByID(oderDetail.getGoodsid());
			storageDeliveryOutDetail.setBuyprice(goodsInfo.getHighestbuyprice());
			storageDeliveryOutDetail.setTaxamount(oderDetail.getTaxamount());
			storageDeliveryOutDetail.setSeq(oderDetail.getSeq());
			storageDeliveryOutDetail.setRemark(oderDetail.getRemark());
            storageDeliveryOutDetail.setAddcostprice(getGoodsCostprice(deliveryOrder.getStorageid(),goodsInfo));
            
            storageDeliveryOutDetail.setBatchno(oderDetail.getBatchno());
            storageDeliveryOutDetail.setProduceddate(oderDetail.getProduceddate());
            storageDeliveryOutDetail.setDeadline(oderDetail.getDeadline());
            //出库
            if(StringUtils.isEmpty(oderDetail.getBatchno())){
            	//非批次管理
            	StorageSummaryBatch storageSummaryBatch =getStorageSummaryBatchByStorageidAndGoodsid(deliveryOrder.getStorageid(),oderDetail.getGoodsid());
            	if(null!=storageSummaryBatch){
				    storageDeliveryOutDetail.setSummarybatchid(storageSummaryBatch.getId());
				}
            }else{
                //批次管理
            	StorageSummaryBatch batch =getStorageSummaryBatchByStorageidAndBatchno(deliveryOrder.getStorageid(), oderDetail.getBatchno(), oderDetail.getGoodsid());
            	//商品原先为非批次管理,后来为批次管理batch会为null
            	if(batch == null ){
            		batch = getStorageSummaryBatchByStorageidAndGoodsid(deliveryOrder.getStorageid(),oderDetail.getGoodsid());
            	}
            	storageDeliveryOutDetail.setSummarybatchid(batch.getId());
            }
			list.add(storageDeliveryOutDetail);
		}
		Map<String,Object> map=deliveryOutService.insertDatas(storageDeliveryOut, list);
		return map;
	}

	/**
	 * 客户退货订单审核后添加到供应商代配送入库单
	 * 
	 * @param deliveryRejectbill
	 *            ,detailList
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 24, 2015
	 */
	public boolean addDistributeRejectByRejectbill(DeliveryRejectbill deliveryRejectbill, List detailList) throws Exception {
		// 客户退货订单转换成供应商代配送入库单
		StorageDeliveryEnter storageDeliveryEnter = new StorageDeliveryEnter();
		storageDeliveryEnter.setBusinessdate(CommonUtils.getTodayDataStr());
		storageDeliveryEnter.setStatus("2");
		storageDeliveryEnter.setRemark(deliveryRejectbill.getRemark());
		storageDeliveryEnter.setAdduserid(deliveryRejectbill.getAdduserid());
		storageDeliveryEnter.setAddusername(deliveryRejectbill.getAddusername());
		storageDeliveryEnter.setAdddeptid(deliveryRejectbill.getAdddeptid());
		storageDeliveryEnter.setAdddeptname(deliveryRejectbill.getAdddeptname());
		storageDeliveryEnter.setAddtime(new Date());
		storageDeliveryEnter.setModifyuserid(deliveryRejectbill.getModifyuserid());
		storageDeliveryEnter.setModifyusername(deliveryRejectbill.getModifyusername());
		storageDeliveryEnter.setModifytime(deliveryRejectbill.getModifytime());
		storageDeliveryEnter.setAudituserid(deliveryRejectbill.getAudituserid());
		storageDeliveryEnter.setAuditusername(deliveryRejectbill.getAuditusername());
		storageDeliveryEnter.setAudittime(deliveryRejectbill.getAudittime());
		storageDeliveryEnter.setStopuserid(deliveryRejectbill.getStopuserid());
		storageDeliveryEnter.setStopusername(deliveryRejectbill.getStopusername());
		storageDeliveryEnter.setStoptime(deliveryRejectbill.getStoptime());
		storageDeliveryEnter.setClosetime(deliveryRejectbill.getClosetime());
		storageDeliveryEnter.setPrinttimes(deliveryRejectbill.getPrinttimes());
		storageDeliveryEnter.setBilltype("2");
		storageDeliveryEnter.setSourcetype("2");
		storageDeliveryEnter.setSourceid(deliveryRejectbill.getId());
		storageDeliveryEnter.setSupplierid(deliveryRejectbill.getSupplierid());
		storageDeliveryEnter.setSuppliername(deliveryRejectbill.getSuppliername());
		storageDeliveryEnter.setStorageid(deliveryRejectbill.getStorageid());
		storageDeliveryEnter.setStoragename(deliveryRejectbill.getStoragename());
		storageDeliveryEnter.setTotalamount(deliveryRejectbill.getTotalamount());
		storageDeliveryEnter.setTotalbox(deliveryRejectbill.getTotalbox());
		storageDeliveryEnter.setTotalvolume(deliveryRejectbill.getTotalvolume());
		storageDeliveryEnter.setTotalweight(deliveryRejectbill.getTotalweight());
		storageDeliveryEnter.setCustomerid(deliveryRejectbill.getCustomerid());
		storageDeliveryEnter.setPcustomerid(deliveryRejectbill.getPcustomerid());
		storageDeliveryEnter.setCustomername(deliveryRejectbill.getCustomername());
		storageDeliveryEnter.setCustomersort(deliveryRejectbill.getCustomersort());
		storageDeliveryEnter.setDeptid(deliveryRejectbill.getDeptid());
		storageDeliveryEnter.setSupplierid(deliveryRejectbill.getSupplierid());
		storageDeliveryEnter.setCustomerbill(deliveryRejectbill.getSourceid());
		// 客户退货订单明细转换成供应商代配送入库单明细
		List<StorageDeliveryEnterDetail> list = new ArrayList();
		for (Object obj : detailList) {
			DeliveryRejectbillDetail rejectbillDetail = (DeliveryRejectbillDetail) obj;
			StorageDeliveryEnterDetail storageDetail = new StorageDeliveryEnterDetail();
			storageDetail.setSourcebilldetailid(rejectbillDetail.getId() + "");
			storageDetail.setSourcebillid(deliveryRejectbill.getId());
			storageDetail.setGoodsid(rejectbillDetail.getGoodsid());
			storageDetail.setGoodssort(rejectbillDetail.getGoodssort());
			storageDetail.setBrandid(rejectbillDetail.getBrandid());
			storageDetail.setUnitid(rejectbillDetail.getUnitid());
			storageDetail.setUnitname(rejectbillDetail.getUnitname());
			storageDetail.setUnitnum(rejectbillDetail.getUnitnum());
			storageDetail.setAuxunitid(rejectbillDetail.getAuxunitid());
			storageDetail.setAuxunitname(rejectbillDetail.getAuxunitname());
			storageDetail.setAuxnum(rejectbillDetail.getAuxnum());
			storageDetail.setAuxnumdetail(rejectbillDetail.getAuxnumdetail());
			storageDetail.setOvernum(rejectbillDetail.getOvernum());
			storageDetail.setTotalbox(rejectbillDetail.getTotalbox());
			storageDetail.setPrice(rejectbillDetail.getPrice());
			GoodsInfo goodsInfo =getAllGoodsInfoByID(rejectbillDetail.getGoodsid());
			storageDetail.setBuyprice(goodsInfo.getHighestbuyprice());
			storageDetail.setTaxamount(rejectbillDetail.getTaxamount());
			storageDetail.setSeq(rejectbillDetail.getSeq());
			storageDetail.setRemark(rejectbillDetail.getRemark());
            storageDetail.setAddcostprice(getGoodsCostprice(deliveryRejectbill.getStorageid(),goodsInfo));
            
            
            storageDetail.setProduceddate(rejectbillDetail.getProduceddate());
            storageDetail.setDeadline(rejectbillDetail.getDeadline());
            storageDetail.setBatchno(rejectbillDetail.getBatchno());
            
            if(StringUtils.isEmpty(rejectbillDetail.getBatchno())){
            	StorageSummaryBatch storageSummaryBatch=getStorageSummaryBatchByStorageidAndGoodsid(deliveryRejectbill.getStorageid(),rejectbillDetail.getGoodsid());
            	if(null!=storageSummaryBatch){
            		storageDetail.setSummarybatchid(storageSummaryBatch.getId());
            	}
            }else{
            	//批次管理时(入库), getStorageSummaryBatchByStorageidAndBatchno 为null
//            	storageDetail.setSummarybatchid(getStorageSummaryBatchByStorageidAndBatchno(deliveryRejectbill.getStorageid(),rejectbillDetail.getBatchno(),rejectbillDetail.getGoodsid()).getId());
            }
			list.add(storageDetail);
		}
		boolean flag=distributeRejectService.insertDatas(storageDeliveryEnter, list);
		return flag;
	}

	/**
	 * 入库单审核通过关闭到货订单
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 24, 2015
	 */
	public boolean closeDeliveryAogorder(String id) throws Exception {
		boolean flag;

		int i = deliveryAogorderMapper.closeDeliveryAogorder(id, new Date());
		flag = i > 0;
		return flag;
	}

	/**
	 * 出库单审核通过关闭退货订单
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 24, 2015
	 */
	public boolean closeDeliveryAogreturn(String id) throws Exception {
		boolean flag;
		int i = deliveryAogreturnMapper.closeDeliveryAogreturn(id, new Date());
		flag = i > 0;
		return flag;
	}

	/**
	 * 客户出库单审核通过关闭客户订单
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 24, 2015
	 */
	public boolean closeDeliveryOrder(String id) throws Exception {
		boolean flag;
		int i = deliveryOrderMapper.closeDeliveryOrder(id, new Date());
		flag = i > 0;
		return flag;
	}

	/**
	 * 客户退货入库单审核通过关闭客户退货订单
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 24, 2015
	 */
	public boolean closeDeliveryRejectbill(String id) throws Exception {
		boolean flag;
		int i = deliveryRejectbillMapper.closeDeliveryRejectbill(id, new Date());
		flag = i > 0;
		return flag;
	}
	/**
	 * 导出获取到货订单列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 24, 2015
	 */
	public List<ExportDeliveryAogorder> getAogorderList(PageMap pageMap) throws Exception {
		String dataSql = getAccessColumnList("t_delivery_aogorder", "a");
		pageMap.setDataSql(dataSql);
		pageMap.setQueryAlias("a");
		List<ExportDeliveryAogorder> list = deliveryAogorderMapper.getAogorderList(pageMap);
		if(list.size() != 0){
			for(ExportDeliveryAogorder exportAogreturn : list){
				GoodsInfo goodsInfo;
				StorageInfo storageInfo;
				BuySupplier buySupplier;
				if(StringUtils.isNotEmpty(exportAogreturn.getGoodsid())){
					goodsInfo= getBaseGoodsMapper().getGoodsInfo(exportAogreturn.getGoodsid());
					exportAogreturn.setGoodsname(goodsInfo.getName());
				}
				
				if (StringUtils.isNotEmpty(exportAogreturn.getStorageid())) {
					storageInfo = getStorageInfoByID(exportAogreturn.getStorageid());
					exportAogreturn.setStoragename(storageInfo.getName());
				}
				if (StringUtils.isNotEmpty(exportAogreturn.getSupplierid())) {
					buySupplier = getSupplierInfoById(exportAogreturn.getSupplierid());
					exportAogreturn.setSuppliername(buySupplier.getName());
				}
			}
		}
		return list;	
	}
	/**
	 * 导出获取退货订单列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 24, 2015
	 */
	public List<ExportDeliveryAogreturn> getAogreturnList(PageMap pageMap) throws Exception {
		String dataSql = getAccessColumnList("t_delivery_aogreturn", "a");
		pageMap.setDataSql(dataSql);
		pageMap.setQueryAlias("a");
		List<ExportDeliveryAogreturn> list = deliveryAogreturnMapper.getAogreturnList(pageMap);
		if(list.size() != 0){
			for(ExportDeliveryAogreturn exportAogreturn : list){
				GoodsInfo goodsInfo;
				StorageInfo storageInfo;
				BuySupplier buySupplier;
				if(StringUtils.isNotEmpty(exportAogreturn.getGoodsid())){
					goodsInfo= getBaseGoodsMapper().getGoodsInfo(exportAogreturn.getGoodsid());
					exportAogreturn.setGoodsname(goodsInfo.getName());
				}
				
				if (StringUtils.isNotEmpty(exportAogreturn.getStorageid())) {
					storageInfo = getStorageInfoByID(exportAogreturn.getStorageid());
					exportAogreturn.setStoragename(storageInfo.getName());
				}
				if (StringUtils.isNotEmpty(exportAogreturn.getSupplierid())) {
					buySupplier = getSupplierInfoById(exportAogreturn.getSupplierid());
					exportAogreturn.setSuppliername(buySupplier.getName());
				}
			}
		}
		return list;	
	}
	/**
	 * 导出获取客户订单列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 24, 2015
	 */
	public List<ExportDeliveryOrder> getOrderList(PageMap pageMap) throws Exception {
		String dataSql = getAccessColumnList("t_delivery_order", "a");
		pageMap.setDataSql(dataSql);
		pageMap.setQueryAlias("a");
		List<ExportDeliveryOrder> list = deliveryOrderMapper.getOrderList(pageMap);
		if(list.size() != 0){
			for(ExportDeliveryOrder exportDeliveryOrder : list){
				GoodsInfo goodsInfo;
				StorageInfo storageInfo;
				BuySupplier buySupplier;
				if(StringUtils.isNotEmpty(exportDeliveryOrder.getGoodsid())){
					goodsInfo= getBaseGoodsMapper().getGoodsInfo(exportDeliveryOrder.getGoodsid());
					exportDeliveryOrder.setGoodsname(goodsInfo.getName());
				}
				
				if (StringUtils.isNotEmpty(exportDeliveryOrder.getStorageid())) {
					storageInfo = getStorageInfoByID(exportDeliveryOrder.getStorageid());
					exportDeliveryOrder.setStoragename(storageInfo.getName());
				}
				if (StringUtils.isNotEmpty(exportDeliveryOrder.getSupplierid())) {
					buySupplier = getSupplierInfoById(exportDeliveryOrder.getSupplierid());
					exportDeliveryOrder.setSuppliername(buySupplier.getName());
				}
			}
		}
		return list;	
	}
	/**
	 * 导出获取客户退货订单列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 24, 2015
	 */
	public List<ExportDeliveryRejectbill> getRejectbillList(PageMap pageMap) throws Exception {
		String dataSql = getAccessColumnList("t_delivery_rejectbill", "a");
		pageMap.setDataSql(dataSql);
		pageMap.setQueryAlias("a");
		List<ExportDeliveryRejectbill> list = deliveryRejectbillMapper.getRejectbillList(pageMap);
		if(list.size() != 0){
			for(ExportDeliveryRejectbill exportDeliveryRejectbill : list){
				GoodsInfo goodsInfo;
				StorageInfo storageInfo;
				BuySupplier buySupplier;
				if(StringUtils.isNotEmpty(exportDeliveryRejectbill.getGoodsid())){
					goodsInfo= getBaseGoodsMapper().getGoodsInfo(exportDeliveryRejectbill.getGoodsid());
					exportDeliveryRejectbill.setGoodsname(goodsInfo.getName());
				}
				
				if (StringUtils.isNotEmpty(exportDeliveryRejectbill.getStorageid())) {
					storageInfo = getStorageInfoByID(exportDeliveryRejectbill.getStorageid());
					exportDeliveryRejectbill.setStoragename(storageInfo.getName());
				}
				if (StringUtils.isNotEmpty(exportDeliveryRejectbill.getSupplierid())) {
					buySupplier = getSupplierInfoById(exportDeliveryRejectbill.getSupplierid());
					exportDeliveryRejectbill.setSuppliername(buySupplier.getName());
				}
			}
		}
		return list;	
	}
	/**
	 * 导入到货订单
	 * @param list
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 24, 2015
	 */
	public Map importAogorder(List<Map<String, Object>> list) throws Exception {
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
		//根据供应商编码分割单据
		boolean emptySupplier = false,emptyGoods = false;
		String nullgoodsids = "",nullspells = "",nullbarcode = "",failorders = "";
		String errormsg = "",drerrormsg = "";
		int emptySupplierNum = 0,emptyGoodsNum = 0,ordernum=0,success=0;
		List<DeliveryAogorder> deliveryAogorderList=new ArrayList<DeliveryAogorder>();
		List<DeliveryAogorderDetail> detaillist = new ArrayList<DeliveryAogorderDetail>();
		List<String> idlist=new ArrayList<String>();
		for(Map<String, Object> map : list){
			String indexID="";
			String supplierid = (null != map.get("supplierid")) ? (String)map.get("supplierid") : "";
	    	String storageid = (null != map.get("storageid")) ? (String)map.get("storageid") : "";
		    String businessdate = (null != map.get("businessdate")) ? (String)map.get("businessdate") : "";
		    if(businessdate=="")
		    	businessdate=CommonUtils.getTodayDataStr();
			if(storageid=="")
				storageid="1001";
			if(deliveryAogorderList.isEmpty()){
				DeliveryAogorder deliveryAogorder=new DeliveryAogorder();
				deliveryAogorder.setSupplierid(supplierid);
				deliveryAogorder.setStorageid(storageid);
				deliveryAogorder.setBusinessdate(businessdate);
				if (isAutoCreate("t_delivery_aogorder")) {
					// 获取自动编号
					String id = getAutoCreateSysNumbderForeign(deliveryAogorder, "t_delivery_aogorder");
					deliveryAogorder.setId(id);
				} else {
					deliveryAogorder.setId("DHDD-" + CommonUtils.getDataNumberSendsWithRand());
					
				}
				indexID=deliveryAogorder.getId();
				deliveryAogorderList.add(deliveryAogorder);
			}
			else{
				boolean haveid=false;
				for(DeliveryAogorder index : deliveryAogorderList){
					if(supplierid.equals(index.getSupplierid())&&storageid.equals(index.getStorageid())&&businessdate.equals(index.getBusinessdate())){
						indexID=index.getId();
						haveid=true;
						break;
					}
				}
				if(!haveid)
				{
					DeliveryAogorder deliveryAogorder=new DeliveryAogorder();
					deliveryAogorder.setSupplierid(supplierid);
					deliveryAogorder.setStorageid(storageid);
					deliveryAogorder.setBusinessdate(businessdate);
					if (isAutoCreate("t_delivery_aogorder")) {
						// 获取自动编号
						String id = getAutoCreateSysNumbderForeign(deliveryAogorder, "t_delivery_aogorder");
						deliveryAogorder.setId(id);
					} else {
						deliveryAogorder.setId("DHDD-" + CommonUtils.getDataNumberSendsWithRand());
					}
					indexID=deliveryAogorder.getId();
					deliveryAogorderList.add(deliveryAogorder);
				}
			}
			if(supplierid!=""){
				String goodsid = (null != map.get("goodsid")) ? (String)map.get("goodsid") : "";
				String spell = (null != map.get("spell")) ? (String)map.get("spell") : "";
				String barcode = (null != map.get("barcode")) ? (String)map.get("barcode") : "";
				//根据已知商品编码or助记符or条形码获取商品档案信息
				GoodsInfo goodsInfoQ = null;
				if(StringUtils.isNotEmpty(goodsid)){
					goodsInfoQ = getAllGoodsInfoByID(goodsid);
				}else if(StringUtils.isNotEmpty(spell)){
					goodsInfoQ = getGoodsInfoBySpell(spell);
				}else if(StringUtils.isNotEmpty(barcode)){
					goodsInfoQ = getGoodsInfoByBarcode(barcode);
				}
				GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(goodsInfoQ);
				if(null != goodsInfo){
					MeteringUnit meteringUnit = getGoodsDefaulAuxunit(goodsInfo.getId());
					if(null != meteringUnit){
						goodsInfo.setAuxunitid(meteringUnit.getId());
						goodsInfo.setAuxunitname(meteringUnit.getName());
					}
					try {
						BigDecimal boxnum = goodsInfo.getBoxnum();
						BigDecimal unitnum = (null != map.get("unitnum")) ? new BigDecimal((String) map.get("unitnum")) : BigDecimal.ZERO;// 数量
						BigDecimal auxnum = (null != map.get("auxnum")) ? new BigDecimal((String) map.get("auxnum")) : BigDecimal.ZERO;// 箱数
						BigDecimal overnum = (null != map.get("overnum")) ? new BigDecimal((String) map.get("overnum")) : BigDecimal.ZERO;
						if(decimalScale == 0){
							unitnum = unitnum.setScale(decimalScale,BigDecimal.ROUND_DOWN);
							auxnum = auxnum.setScale(decimalScale,BigDecimal.ROUND_DOWN);
							overnum = overnum.setScale(decimalScale,BigDecimal.ROUND_DOWN);
						}else{
							unitnum = unitnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
							auxnum = auxnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
							overnum = overnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
						}
						if (!unitnum.equals(BigDecimal.ZERO)) {
							DeliveryAogorderDetail deliveryAogorderDetail = new DeliveryAogorderDetail();
							deliveryAogorderDetail.setGoodsid(goodsInfo.getId());
							deliveryAogorderDetail.setBillid(indexID);
							deliveryAogorderDetail.setAuxunitid(goodsInfo.getAuxunitid());
							deliveryAogorderDetail.setAuxunitname(goodsInfo.getAuxunitname());
							deliveryAogorderDetail.setUnitnum(unitnum);
							deliveryAogorderDetail.setUnitid(goodsInfo.getMainunit());
							deliveryAogorderDetail.setUnitname(goodsInfo.getMainunitName());
							deliveryAogorderDetail.setBrandid(goodsInfo.getBrand());
							deliveryAogorderDetail.setGoodssort(goodsInfo.getDefaultsort());
							BigDecimal newauxnum = unitnum.divide(boxnum,0,BigDecimal.ROUND_DOWN);
							deliveryAogorderDetail.setAuxnum(newauxnum);
							BigDecimal newOvernum = unitnum.subtract(newauxnum.multiply(boxnum));
							deliveryAogorderDetail.setOvernum(newOvernum);
							deliveryAogorderDetail.setAuxnumdetail(CommonUtils.strDigitNumDeal(newauxnum + deliveryAogorderDetail.getAuxunitname() + newOvernum.setScale(BillGoodsNumDecimalLenUtils.decimalLen,BigDecimal.ROUND_HALF_UP) + deliveryAogorderDetail.getUnitname()));
							BigDecimal price = (null != map.get("price")) ? new BigDecimal((String) map.get("price")) : BigDecimal.ZERO;
							if (!price.equals(BigDecimal.ZERO)){
								deliveryAogorderDetail.setPrice(price);
							}
							else{
								deliveryAogorderDetail.setPrice(goodsInfo.getHighestbuyprice());
							}
							deliveryAogorderDetail.setTaxamount(deliveryAogorderDetail.getUnitnum().multiply(deliveryAogorderDetail.getPrice()));
							
							deliveryAogorderDetail.setTotalbox(deliveryAogorderDetail.getAuxnum().add(deliveryAogorderDetail.getOvernum().divide(boxnum, 2, BigDecimal.ROUND_HALF_UP)));
							detaillist.add(deliveryAogorderDetail);
						}
						else if (!auxnum.equals(BigDecimal.ZERO)||!overnum.equals(BigDecimal.ZERO)) {
							if(overnum.compareTo(boxnum) >= 0){
								BigDecimal i = overnum.divide(boxnum,0,BigDecimal.ROUND_HALF_DOWN);
								auxnum = auxnum.add(i);
								overnum = overnum.subtract(i.multiply(boxnum));
							}
							DeliveryAogorderDetail deliveryAogorderDetail = new DeliveryAogorderDetail();
							deliveryAogorderDetail.setGoodsid(goodsInfo.getId());
							deliveryAogorderDetail.setBillid(indexID);
							deliveryAogorderDetail.setAuxunitid(goodsInfo.getAuxunitid());
							deliveryAogorderDetail.setAuxunitname(goodsInfo.getAuxunitname());
							unitnum =  auxnum.multiply(boxnum).add(overnum);
							deliveryAogorderDetail.setUnitnum(unitnum);
							deliveryAogorderDetail.setUnitid(goodsInfo.getMainunit());
							deliveryAogorderDetail.setUnitname(goodsInfo.getMainunitName());
							deliveryAogorderDetail.setBrandid(goodsInfo.getBrand());
							deliveryAogorderDetail.setGoodssort(goodsInfo.getDefaultsort());
							deliveryAogorderDetail.setAuxnum(auxnum);
							deliveryAogorderDetail.setOvernum(overnum);
							deliveryAogorderDetail.setAuxnumdetail(CommonUtils.strDigitNumDeal(auxnum + deliveryAogorderDetail.getAuxunitname() + overnum.setScale(BillGoodsNumDecimalLenUtils.decimalLen, BigDecimal.ROUND_HALF_UP) + deliveryAogorderDetail.getUnitname()));
							BigDecimal price = (null != map.get("price")) ? new BigDecimal((String) map.get("price")) : BigDecimal.ZERO;
							if (!price.equals(BigDecimal.ZERO)){
								deliveryAogorderDetail.setPrice(price);
							}
							else{
								deliveryAogorderDetail.setPrice(goodsInfo.getHighestbuyprice());
							}
							deliveryAogorderDetail.setTaxamount(deliveryAogorderDetail.getUnitnum().multiply(deliveryAogorderDetail.getPrice()));
							
							deliveryAogorderDetail.setTotalbox(deliveryAogorderDetail.getAuxnum().add(deliveryAogorderDetail.getOvernum().divide(boxnum, 2, BigDecimal.ROUND_HALF_UP)));
							detaillist.add(deliveryAogorderDetail);
						}
					} catch (Exception e) {
						String msg = "";
						if(StringUtils.isNotEmpty(goodsid)){
							msg = "商品编码为："+goodsid;
						}else if(StringUtils.isNotEmpty(spell)){
							msg = "助记符为："+spell;
						}else if(StringUtils.isNotEmpty(barcode)){
							msg = "条形码为："+barcode;
						}
						if(StringUtils.isEmpty(errormsg)){
							errormsg = "供应商编码为："+supplierid+msg+"出错;";
						}else{
							errormsg += "<br>" + "供应商编码为："+supplierid+msg+"出错;";
						}
					}
				}else{
					if(StringUtils.isNotEmpty(goodsid)){
						if(StringUtils.isNotEmpty(nullgoodsids)){
							nullgoodsids += "," + goodsid;
						}else{
							nullgoodsids = goodsid;
						}
					}else if(StringUtils.isNotEmpty(spell)){
						if(StringUtils.isNotEmpty(nullspells)){
							nullspells += "," + spell;
						}else{
							nullspells = spell;
						}
					}else if(StringUtils.isNotEmpty(barcode)){
						if(StringUtils.isNotEmpty(nullbarcode)){
							nullbarcode += "," + barcode;
						}else{
							nullbarcode = barcode;
						}
					}else{
						emptyGoods = true;
						emptyGoodsNum++;
					}
				}
			}else{
				emptySupplier = true;
				emptySupplierNum++;
			}
		}
		//执行导入操作，即新增采购计划单
		boolean flag=false;
		if (!deliveryAogorderList.isEmpty()) {
			for (DeliveryAogorder deliveryAogorder : deliveryAogorderList) {
				deliveryAogorder.setStatus("2");
				BigDecimal totalBox = new BigDecimal("0");// 总箱数
				BigDecimal totalWeight = new BigDecimal("0");// 总重量
				BigDecimal totalVolume = new BigDecimal("0");// 总体积
				BigDecimal totalaMount = new BigDecimal("0");// 总金额
				for (DeliveryAogorderDetail detail : detaillist) {
					if (detail.getBillid().equals(deliveryAogorder.getId())) {
						GoodsInfo goodsInfo = getGoodsInfoByID(detail.getGoodsid());
						// 获取总箱数
						totalBox = totalBox.add(detail.getTotalbox());
						// 获取总重量
						if (goodsInfo.getGrossweight() != null)
							totalWeight = totalWeight.add(goodsInfo.getGrossweight().multiply(detail.getUnitnum()));
						// 获取总体积
						if (goodsInfo.getSinglevolume() != null)
							totalVolume = totalVolume.add(goodsInfo.getSinglevolume().multiply(detail.getUnitnum()));
						// 获取总金额
						totalaMount = totalaMount.add(detail.getTaxamount());
					}
				}
				deliveryAogorder.setTotalamount(totalaMount);
				deliveryAogorder.setTotalbox(totalBox);
				deliveryAogorder.setTotalvolume(totalVolume);
				deliveryAogorder.setTotalweight(totalWeight);
				SysUser sysUser = getSysUser();
				deliveryAogorder.setAdddeptid(sysUser.getDepartmentid());
				deliveryAogorder.setAdddeptname(sysUser.getDepartmentname());
				deliveryAogorder.setAdduserid(sysUser.getUserid());
				deliveryAogorder.setAddusername(sysUser.getName());
				deliveryAogorder.setRemark("excel导入");
				deliveryAogorder.setAddtime(new Date());
				int i=0;
				for(DeliveryAogorderDetail detail:detaillist){
					if(detail.getBillid().equals(deliveryAogorder.getId())){
						i = deliveryAogorderMapper.addDeliveryAogorder(deliveryAogorder);
					    break;
					}
				}
				
				if(i>0){
					flag=true;
					ordernum++;
					idlist.add(deliveryAogorder.getId());
				}
				else{
					flag=false;
				}
			}
		}
		if(!detaillist.isEmpty()){
			for (DeliveryAogorderDetail detail:detaillist) {
				int i = deliveryAogorderMapper.addDeliveryAogorderDetail(detail);
				if(i>0){
					flag=true;
					success++;
				}
				else{
					flag=false;
				}
			}
		}	
		Map map = new HashMap();
		if(flag){
			map.put("success", success);
			map.put("ordernum", ordernum);
		}
		else{
			map.put("error", true);
		}
		map.put("idlist", idlist);
		map.put("flag", flag);
		return map;
	}

    @Override
    public Map changeModelForDetail(List<ModelOrder> wareList, String gtype) throws Exception {
        Map map = new HashMap();
        List disableGoods = new ArrayList();
        List unimportGoods = new ArrayList();
        List<DeliveryOrderDetail> detailList = new ArrayList<DeliveryOrderDetail>();
        for(ModelOrder modelOrder : wareList){
            String barcode = modelOrder.getBarcode();
            GoodsInfo goodsInfo = new GoodsInfo();
			if("1".equals(gtype)){
				goodsInfo = getGoodsInfoByBarcode(barcode);
			}else if("2".equals(gtype)){//查询店内码
				goodsInfo = getGoodsInfoByCustomerGoodsid(modelOrder.getBusid(),modelOrder.getShopid());
			}else if("3".equals(gtype)){//查询助记符
				goodsInfo = getGoodsInfoBySpell(barcode);
			}else if("4".equals(gtype)){//查询商品编码
				goodsInfo = getGoodsInfoByID(barcode);
			}
            if(null != goodsInfo){
                goodsInfo = getAllGoodsInfoByID(goodsInfo.getId());
                //禁用商品存在相同条码或者相同编码的情况
                if( "0".equals(goodsInfo.getState())){
                    Map goodsMap = new HashMap();
                    goodsMap.put("id",goodsInfo.getId());
                    List<GoodsInfo> goodsInfoList = getBaseGoodsMapper().getGoodsInfoListByMap(goodsMap);
                    for(GoodsInfo gInfo : goodsInfoList){
                        if(null != gInfo && "1".equals(gInfo.getState())){
                            goodsInfo = getAllGoodsInfoByID(gInfo.getId());
                            break;
                        }
                    }
                    if( "0".equals(goodsInfo.getState())){
                        goodsMap.remove("id");
                        goodsMap.put("barcode",barcode);
                        goodsInfoList = getBaseGoodsMapper().getGoodsInfoListByMap(goodsMap);
                        for(GoodsInfo gInfo : goodsInfoList){
                            if(null != gInfo && "1".equals(gInfo.getState())){
                                goodsInfo = getAllGoodsInfoByID(gInfo.getId());
                                break;
                            }
                        }
                    }
                }
                //禁用商品不导入
                if(null != goodsInfo &&  "0".equals(goodsInfo.getState())){
                    disableGoods.add(barcode);
                    continue;
                }
                DeliveryOrderDetail deliveryOrderDetail = new DeliveryOrderDetail();
                deliveryOrderDetail.setGoodsid(goodsInfo.getId());
                deliveryOrderDetail.setGoodsInfo(goodsInfo);
                deliveryOrderDetail.setGoodssort(goodsInfo.getDefaultsort());
                deliveryOrderDetail.setBrandid(goodsInfo.getBrand());
                deliveryOrderDetail.setUnitid(goodsInfo.getMainunit());
                deliveryOrderDetail.setUnitname(goodsInfo.getMainunitName());

                BigDecimal boxnum = goodsInfo.getBoxnum();
                String num = modelOrder.getUnitnum();
                if(StringUtils.isEmpty(num)){
                    continue;
                }
                BigDecimal unitnum = new BigDecimal(num);

                BigDecimal auxnum = new BigDecimal(0);
                BigDecimal overnum = new BigDecimal(0);

                if(null != boxnum && boxnum.compareTo(BigDecimal.ZERO) > 0){
                    auxnum = unitnum.divide(boxnum,0,BigDecimal.ROUND_DOWN);
                    overnum = unitnum.subtract(auxnum.multiply(boxnum)).setScale(0,BigDecimal.ROUND_HALF_UP);
                }else{
                    overnum = unitnum;
                }
                deliveryOrderDetail.setUnitnum(unitnum);
                deliveryOrderDetail.setAuxunitid(goodsInfo.getAuxunitid());
                deliveryOrderDetail.setAuxunitname(goodsInfo.getAuxunitname());
                deliveryOrderDetail.setAuxnum(auxnum);
                deliveryOrderDetail.setAuxnumdetail(auxnum+goodsInfo.getAuxunitname()+overnum+goodsInfo.getMainunitName());
                deliveryOrderDetail.setOvernum(overnum);

                //0取基准销售价,1取合同价
                String priceType = getSysParamValue("DELIVERYORDERPRICE");
                BigDecimal price = new BigDecimal(0);
                if(StringUtils.isNotEmpty(modelOrder.getTaxprice())){
                    price = new BigDecimal(modelOrder.getTaxprice());
                }else if("1".equals(priceType) &&!"".equals(modelOrder.getBusid())){
                    //系统参数取价
                    price = getGoodsPriceByCustomer(goodsInfo.getId(),modelOrder.getBusid());
                }else {
                    price = goodsInfo.getBasesaleprice();
                }
                deliveryOrderDetail.setPrice(price);
                BigDecimal taxamount = price.multiply(unitnum).setScale(2,BigDecimal.ROUND_HALF_UP);
                deliveryOrderDetail.setTaxamount(taxamount);

                detailList.add(deliveryOrderDetail);

            }else{
                if(StringUtils.isNotEmpty(barcode) && "1".equals(gtype)){
                    unimportGoods.add(barcode);
                }
				if(StringUtils.isNotEmpty(modelOrder.getShopid()) && "2".equals(gtype)){
					unimportGoods.add(modelOrder.getShopid());
				}
            }
        }
        if(unimportGoods.size() > 0){
            String unimport = unimportGoods.toString();
            unimport = unimport.replace("[","");unimport = unimport.replace("]","");
            map.put("unimportGoods",unimport);
        }
        String disable = disableGoods.toString();
        disable = disable.replace("[","");disable = disable.replace("]","");
        map.put("disableGoods",disable);
        map.put("detailList",detailList);
        return map;
    }

    /**
	 * 导入退货订单
	 * @param list
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 24, 2015
	 */
	public Map importAogreturn(List<Map<String, Object>> list) throws Exception {
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
		//根据供应商编码分割单据
		boolean emptySupplier = false,emptyGoods = false;
		String nullgoodsids = "",nullspells = "",nullbarcode = "",failorders = "";
		String errormsg = "",drerrormsg = "";
		int emptySupplierNum = 0,emptyGoodsNum = 0,ordernum=0,success=0;
		List<DeliveryAogreturn> deliveryAogreturnList=new ArrayList<DeliveryAogreturn>();
		List<DeliveryAogreturnDetail> detaillist = new ArrayList<DeliveryAogreturnDetail>();
		List<String> idlist=new ArrayList<String>();
		for(Map<String, Object> map : list){
			String indexID="";
			String supplierid = (null != map.get("supplierid")) ? (String)map.get("supplierid") : "";
	    	String storageid = (null != map.get("storageid")) ? (String)map.get("storageid") : "";
		    String businessdate = (null != map.get("businessdate")) ? (String)map.get("businessdate") : "";
			if (businessdate=="")
				businessdate = CommonUtils.getTodayDataStr();
			if(storageid=="")
				storageid="1001";
		    if(deliveryAogreturnList.isEmpty()){
				DeliveryAogreturn deliveryAogreturn=new DeliveryAogreturn();
				deliveryAogreturn.setSupplierid(supplierid);
				deliveryAogreturn.setStorageid(storageid);
				deliveryAogreturn.setBusinessdate(businessdate);
				if (isAutoCreate("t_delivery_aogreturn")) {
					// 获取自动编号
					String id = getAutoCreateSysNumbderForeign(deliveryAogreturn, "t_delivery_aogreturn");
					deliveryAogreturn.setId(id);
				} else {
					deliveryAogreturn.setId("THDD-" + CommonUtils.getDataNumberSendsWithRand());
					
				}
				indexID=deliveryAogreturn.getId();
				deliveryAogreturnList.add(deliveryAogreturn);
			}
			else{
				boolean haveid=false;
				for(DeliveryAogreturn index : deliveryAogreturnList){
					if(supplierid.equals(index.getSupplierid())&&storageid.equals(index.getStorageid())&&businessdate.equals(index.getBusinessdate())){
						indexID=index.getId();
						haveid=true;
						break;
					}
				}
				if(!haveid){
					DeliveryAogreturn deliveryAogreturn=new DeliveryAogreturn();
					deliveryAogreturn.setSupplierid(supplierid);
					deliveryAogreturn.setStorageid(storageid);
					deliveryAogreturn.setBusinessdate(businessdate);
					if (isAutoCreate("t_delivery_aogreturn")) {
						// 获取自动编号
						String id = getAutoCreateSysNumbderForeign(deliveryAogreturn, "t_delivery_aogreturn");
						deliveryAogreturn.setId(id);
					} else {
						deliveryAogreturn.setId("THDD-" + CommonUtils.getDataNumberSendsWithRand());
					}
					indexID=deliveryAogreturn.getId();
					deliveryAogreturnList.add(deliveryAogreturn);
				}
			}
			if(supplierid!=""){
				String goodsid = (null != map.get("goodsid")) ? (String)map.get("goodsid") : "";
				String spell = (null != map.get("spell")) ? (String)map.get("spell") : "";
				String barcode = (null != map.get("barcode")) ? (String)map.get("barcode") : "";
				//根据已知商品编码or助记符or条形码获取商品档案信息
				GoodsInfo goodsInfoQ = null;
				if(StringUtils.isNotEmpty(goodsid)){
					goodsInfoQ = getAllGoodsInfoByID(goodsid);
				}else if(StringUtils.isNotEmpty(spell)){
					goodsInfoQ = getGoodsInfoBySpell(spell);
				}else if(StringUtils.isNotEmpty(barcode)){
					goodsInfoQ = getGoodsInfoByBarcode(barcode);
				}
				GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(goodsInfoQ);
				if(null != goodsInfo){
					MeteringUnit meteringUnit = getGoodsDefaulAuxunit(goodsInfo.getId());
					if(null != meteringUnit){
						goodsInfo.setAuxunitid(meteringUnit.getId());
						goodsInfo.setAuxunitname(meteringUnit.getName());
					}
					try {
						BigDecimal boxnum = goodsInfo.getBoxnum();
						BigDecimal unitnum = (null != map.get("unitnum")) ? new BigDecimal((String) map.get("unitnum")) : BigDecimal.ZERO;// 数量
						BigDecimal auxnum = (null != map.get("auxnum")) ? new BigDecimal((String) map.get("auxnum")) : BigDecimal.ZERO;// 箱数
						BigDecimal overnum = (null != map.get("overnum")) ? new BigDecimal((String) map.get("overnum")) : BigDecimal.ZERO;
						if(decimalScale == 0){
							unitnum = unitnum.setScale(decimalScale,BigDecimal.ROUND_DOWN);
							auxnum = auxnum.setScale(decimalScale,BigDecimal.ROUND_DOWN);
							overnum = overnum.setScale(decimalScale,BigDecimal.ROUND_DOWN);
						}else{
							unitnum = unitnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
							auxnum = auxnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
							overnum = overnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
						}
						if (!unitnum.equals(BigDecimal.ZERO)) {
							DeliveryAogreturnDetail deliveryAogreturnDetail = new DeliveryAogreturnDetail();
							deliveryAogreturnDetail.setGoodsid(goodsInfo.getId());
							deliveryAogreturnDetail.setBillid(indexID);
							deliveryAogreturnDetail.setAuxunitid(goodsInfo.getAuxunitid());
							deliveryAogreturnDetail.setAuxunitname(goodsInfo.getAuxunitname());
							deliveryAogreturnDetail.setUnitnum(unitnum);
							deliveryAogreturnDetail.setUnitid(goodsInfo.getMainunit());
							deliveryAogreturnDetail.setUnitname(goodsInfo.getMainunitName());
							deliveryAogreturnDetail.setBrandid(goodsInfo.getBrand());
							deliveryAogreturnDetail.setGoodssort(goodsInfo.getDefaultsort());
							BigDecimal newAuxnum = unitnum.divide(boxnum,0,BigDecimal.ROUND_DOWN);
							deliveryAogreturnDetail.setAuxnum(newAuxnum);
							BigDecimal newOvernum = unitnum.subtract(newAuxnum.multiply(boxnum));
							deliveryAogreturnDetail.setOvernum(newOvernum);
							deliveryAogreturnDetail.setAuxnumdetail(CommonUtils.strDigitNumDeal(newAuxnum + deliveryAogreturnDetail.getAuxunitname() + newOvernum.setScale(BillGoodsNumDecimalLenUtils.decimalLen,BigDecimal.ROUND_HALF_UP) + deliveryAogreturnDetail.getUnitname()));
							BigDecimal price = (null != map.get("price")) ? new BigDecimal((String) map.get("price")) : BigDecimal.ZERO;
							if (!price.equals(BigDecimal.ZERO)){
								deliveryAogreturnDetail.setPrice(price);
							}
							else{
								deliveryAogreturnDetail.setPrice(goodsInfo.getHighestbuyprice());
							}
							deliveryAogreturnDetail.setTaxamount(deliveryAogreturnDetail.getUnitnum().multiply(deliveryAogreturnDetail.getPrice()));
							
							deliveryAogreturnDetail.setTotalbox(deliveryAogreturnDetail.getAuxnum().add(deliveryAogreturnDetail.getOvernum().divide(boxnum, 2, BigDecimal.ROUND_HALF_UP)));
							detaillist.add(deliveryAogreturnDetail);
						}
						else if (!auxnum.equals(BigDecimal.ZERO)||!overnum.equals(BigDecimal.ZERO)) {
							if(overnum.compareTo(boxnum) >= 0){
								BigDecimal i = overnum.divide(boxnum,0,BigDecimal.ROUND_HALF_DOWN);
								auxnum = auxnum.add(i);
								overnum = overnum.subtract(i.multiply(boxnum));
							}
							DeliveryAogreturnDetail deliveryAogreturnDetail = new DeliveryAogreturnDetail();
							deliveryAogreturnDetail.setGoodsid(goodsInfo.getId());
							deliveryAogreturnDetail.setBillid(indexID);
							deliveryAogreturnDetail.setAuxunitid(goodsInfo.getAuxunitid());
							deliveryAogreturnDetail.setAuxunitname(goodsInfo.getAuxunitname());
							unitnum = auxnum.multiply(boxnum).add(overnum);
							deliveryAogreturnDetail.setUnitnum(unitnum);
							deliveryAogreturnDetail.setUnitid(goodsInfo.getMainunit());
							deliveryAogreturnDetail.setUnitname(goodsInfo.getMainunitName());
							deliveryAogreturnDetail.setBrandid(goodsInfo.getBrand());
							deliveryAogreturnDetail.setGoodssort(goodsInfo.getDefaultsort());
							deliveryAogreturnDetail.setAuxnum(auxnum);
							deliveryAogreturnDetail.setOvernum(overnum);
							deliveryAogreturnDetail.setAuxnumdetail(CommonUtils.strDigitNumDeal(auxnum + deliveryAogreturnDetail.getAuxunitname() + overnum.setScale(BillGoodsNumDecimalLenUtils.decimalLen,BigDecimal.ROUND_HALF_UP) + deliveryAogreturnDetail.getUnitname()));
							BigDecimal price = (null != map.get("price")) ? new BigDecimal((String) map.get("price")) : BigDecimal.ZERO;
							if (!price.equals(BigDecimal.ZERO)){
								deliveryAogreturnDetail.setPrice(price);
							}
							else{
								deliveryAogreturnDetail.setPrice(goodsInfo.getHighestbuyprice());
							}
							deliveryAogreturnDetail.setTaxamount(deliveryAogreturnDetail.getUnitnum().multiply(deliveryAogreturnDetail.getPrice()));
							
							deliveryAogreturnDetail.setTotalbox(deliveryAogreturnDetail.getAuxnum().add(deliveryAogreturnDetail.getOvernum().divide(boxnum, 2, BigDecimal.ROUND_HALF_UP)));
							detaillist.add(deliveryAogreturnDetail);
						}
					} catch (Exception e) {
						String msg = "";
						if(StringUtils.isNotEmpty(goodsid)){
							msg = "商品编码为："+goodsid;
						}else if(StringUtils.isNotEmpty(spell)){
							msg = "助记符为："+spell;
						}else if(StringUtils.isNotEmpty(barcode)){
							msg = "条形码为："+barcode;
						}
						if(StringUtils.isEmpty(errormsg)){
							errormsg = "供应商编码为："+supplierid+msg+"出错;";
						}else{
							errormsg += "<br>" + "供应商编码为："+supplierid+msg+"出错;";
						}
					}
				}else{
					if(StringUtils.isNotEmpty(goodsid)){
						if(StringUtils.isNotEmpty(nullgoodsids)){
							nullgoodsids += "," + goodsid;
						}else{
							nullgoodsids = goodsid;
						}
					}else if(StringUtils.isNotEmpty(spell)){
						if(StringUtils.isNotEmpty(nullspells)){
							nullspells += "," + spell;
						}else{
							nullspells = spell;
						}
					}else if(StringUtils.isNotEmpty(barcode)){
						if(StringUtils.isNotEmpty(nullbarcode)){
							nullbarcode += "," + barcode;
						}else{
							nullbarcode = barcode;
						}
					}else{
						emptyGoods = true;
						emptyGoodsNum++;
					}
				}
			}else{
				emptySupplier = true;
				emptySupplierNum++;
			}
		}
		//执行导入操作，即新增采购计划单
		boolean flag=false;
		if (!deliveryAogreturnList.isEmpty()) {
			for (DeliveryAogreturn deliveryAogreturn : deliveryAogreturnList) {
				deliveryAogreturn.setStatus("2");
				BigDecimal totalBox = new BigDecimal("0");// 总箱数
				BigDecimal totalWeight = new BigDecimal("0");// 总重量
				BigDecimal totalVolume = new BigDecimal("0");// 总体积
				BigDecimal totalaMount = new BigDecimal("0");// 总金额
				for (DeliveryAogreturnDetail detail : detaillist) {
					if (detail.getBillid().equals(deliveryAogreturn.getId())) {
					GoodsInfo goodsInfo = getGoodsInfoByID(detail.getGoodsid());
					// 获取总箱数
					totalBox = totalBox.add(detail.getTotalbox());
					// 获取总重量
					if (goodsInfo.getGrossweight() != null)
						totalWeight = totalWeight.add(goodsInfo.getGrossweight().multiply(detail.getUnitnum()));
					// 获取总体积
					if (goodsInfo.getSinglevolume() != null)
						totalVolume = totalVolume.add(goodsInfo.getSinglevolume().multiply(detail.getUnitnum()));
					// 获取总金额
					totalaMount = totalaMount.add(detail.getTaxamount());
					}
				}
				deliveryAogreturn.setTotalamount(totalaMount);
				deliveryAogreturn.setTotalbox(totalBox);
				deliveryAogreturn.setTotalvolume(totalVolume);
				deliveryAogreturn.setTotalweight(totalWeight);
				SysUser sysUser = getSysUser();
				deliveryAogreturn.setAdddeptid(sysUser.getDepartmentid());
				deliveryAogreturn.setAdddeptname(sysUser.getDepartmentname());
				deliveryAogreturn.setAdduserid(sysUser.getUserid());
				deliveryAogreturn.setAddusername(sysUser.getName());
				deliveryAogreturn.setRemark("excel导入");
				deliveryAogreturn.setAddtime(new Date());
				int i=0;
				for(DeliveryAogreturnDetail detail:detaillist){
					if(detail.getBillid().equals(deliveryAogreturn.getId())){
						i = deliveryAogreturnMapper.addDeliveryAogreturn(deliveryAogreturn);
					    break;
					}
				}
				if(i>0){
					flag=true;
					ordernum++;
					idlist.add(deliveryAogreturn.getId());
				}
				else{
					flag=false;
				}
			}
		}
		if(!detaillist.isEmpty()){
			for (DeliveryAogreturnDetail detail:detaillist) {
			
				int i = deliveryAogreturnMapper.addDeliveryAogreturnDetail(detail);
				if(i>0){
					flag=true;
					success++;
				}
				else{
					flag=false;
				}
			}
		}	
		Map map = new HashMap();
		if(flag){
			map.put("success", success);
			map.put("ordernum", ordernum);
		}
		else{
			map.put("error", true);
		}
		map.put("idlist", idlist);
		map.put("flag", flag);
		return map;
	}
	/**
	 * 导入客户订单
	 * @param list
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 24, 2015
	 */
	public Map importOrder(List<Map<String, Object>> list) throws Exception {
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
		//根据供应商编码分割单据
		boolean emptyCustomer = false,emptyGoods = false;
		String nullgoodsids = "",nullspells = "",nullbarcode = "",failorders = "";
		String errormsg = "",drerrormsg = "";
		int emptyCustomerNum = 0,emptyGoodsNum = 0,ordernum=0,success=0;
		List<DeliveryOrder> deliveryOrderList=new ArrayList<DeliveryOrder>();
		List<DeliveryOrderDetail> detaillist = new ArrayList<DeliveryOrderDetail>();
		List<String> idlist=new ArrayList<String>();
		for(Map<String, Object> map : list){
			String indexID="";
			String customerid = (null != map.get("customerid")) ? (String)map.get("customerid") : "";
			String supplierid = (null != map.get("supplierid")) ? (String)map.get("supplierid") : "";
			String customername = (null != map.get("customername")) ? (String)map.get("customername") : "";
	    	String storageid = (null != map.get("storageid")) ? (String)map.get("storageid") : "";
		    String businessdate = (null != map.get("businessdate")) ? (String)map.get("businessdate") : "";

		    //获取商品详情
		    String goodsid = (null != map.get("goodsid")) ? (String)map.get("goodsid") : "";
			String spell = (null != map.get("spell")) ? (String)map.get("spell") : "";
			String barcode = (null != map.get("barcode")) ? (String)map.get("barcode") : "";
			//根据已知商品编码or助记符or条形码获取商品档案信息
			GoodsInfo goodsInfoQ = null;
			if(StringUtils.isNotEmpty(goodsid)){
				goodsInfoQ = getAllGoodsInfoByID(goodsid);
			}else if(StringUtils.isNotEmpty(spell)){
				goodsInfoQ = getGoodsInfoBySpell(spell);
			}else if(StringUtils.isNotEmpty(barcode)){
				goodsInfoQ = getGoodsInfoByBarcode(barcode);
			}
			GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(goodsInfoQ);
		    
		    
		    if(businessdate=="")
		    	businessdate=CommonUtils.getTodayDataStr();
		    if(customername==""&&customerid!=""){
		    	customername=getCustomerByID(customerid).getName();
		    }
			if(storageid=="")
				storageid="1001";
			if(deliveryOrderList.isEmpty()){
				DeliveryOrder deliveryOrder=new DeliveryOrder();
				if(supplierid!="")
				    deliveryOrder.setSupplierid(supplierid);
				else
					deliveryOrder.setSupplierid(goodsInfo.getDefaultsupplier());
				deliveryOrder.setCustomerid(customerid);
				deliveryOrder.setStorageid(storageid);
				deliveryOrder.setBusinessdate(businessdate);
				deliveryOrder.setCustomername(customername);
				if (isAutoCreate("t_delivery_order")) {
					// 获取自动编号
					String id = getAutoCreateSysNumbderForeign(deliveryOrder, "t_delivery_order");
					deliveryOrder.setId(id);
				} else {
					deliveryOrder.setId("KHDD-" + CommonUtils.getDataNumberSendsWithRand());
					
				}
				indexID=deliveryOrder.getId();
				deliveryOrderList.add(deliveryOrder);
			}
			else{
				boolean haveid=false;
				for(DeliveryOrder index : deliveryOrderList){
					if(customerid.equals(index.getCustomerid())&&storageid.equals(index.getStorageid())&&businessdate.equals(index.getBusinessdate())&&supplierid.equals(index.getSupplierid())){
						indexID=index.getId();
						haveid=true;
						break;
					}
				}
				if(!haveid){
					DeliveryOrder deliveryOrder=new DeliveryOrder();
					if(supplierid!="")
					    deliveryOrder.setSupplierid(supplierid);
					else
						deliveryOrder.setSupplierid(goodsInfo.getDefaultsupplier());
					deliveryOrder.setCustomerid(customerid);
					deliveryOrder.setStorageid(storageid);
					deliveryOrder.setBusinessdate(businessdate);
					deliveryOrder.setCustomername(customername);
					if (isAutoCreate("t_delivery_order")) {
						// 获取自动编号
						String id = getAutoCreateSysNumbderForeign(deliveryOrder, "t_delivery_order");
						deliveryOrder.setId(id);
					} else {
						deliveryOrder.setId("KHDD-" + CommonUtils.getDataNumberSendsWithRand());
					}
					indexID=deliveryOrder.getId();
					deliveryOrderList.add(deliveryOrder);
				}
			}
			if(customerid!=""){
				if(null != goodsInfo){
					MeteringUnit meteringUnit = getGoodsDefaulAuxunit(goodsInfo.getId());
					if(null != meteringUnit){
						goodsInfo.setAuxunitid(meteringUnit.getId());
						goodsInfo.setAuxunitname(meteringUnit.getName());
					}
					try {
						BigDecimal boxnum = goodsInfo.getBoxnum();
						BigDecimal unitnum = (null != map.get("unitnum")) ? new BigDecimal((String) map.get("unitnum")) : BigDecimal.ZERO;// 数量
						BigDecimal auxnum = (null != map.get("auxnum")) ? new BigDecimal((String) map.get("auxnum")) : BigDecimal.ZERO;// 箱数
						BigDecimal overnum = (null != map.get("overnum")) ? new BigDecimal((String) map.get("overnum")) : BigDecimal.ZERO;
						if(decimalScale == 0){
							unitnum = unitnum.setScale(decimalScale,BigDecimal.ROUND_DOWN);
							auxnum = auxnum.setScale(decimalScale,BigDecimal.ROUND_DOWN);
							overnum = overnum.setScale(decimalScale,BigDecimal.ROUND_DOWN);
						}else{
							unitnum = unitnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
							auxnum = auxnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
							overnum = overnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
						}
						if (!unitnum.equals(BigDecimal.ZERO)) {
							DeliveryOrderDetail deliveryOrderDetail = new DeliveryOrderDetail();
							deliveryOrderDetail.setGoodsid(goodsInfo.getId());
							deliveryOrderDetail.setBillid(indexID);
							deliveryOrderDetail.setAuxunitid(goodsInfo.getAuxunitid());
							deliveryOrderDetail.setAuxunitname(goodsInfo.getAuxunitname());
							deliveryOrderDetail.setUnitnum(unitnum);
							deliveryOrderDetail.setUnitid(goodsInfo.getMainunit());
							deliveryOrderDetail.setUnitname(goodsInfo.getMainunitName());
							deliveryOrderDetail.setBrandid(goodsInfo.getBrand());
							deliveryOrderDetail.setGoodssort(goodsInfo.getDefaultsort());
							BigDecimal newAuxnum = unitnum.divide(boxnum,0,BigDecimal.ROUND_HALF_DOWN);
							deliveryOrderDetail.setAuxnum(newAuxnum);
							BigDecimal newOvernum = unitnum.subtract(newAuxnum.multiply(boxnum));
							deliveryOrderDetail.setOvernum(newOvernum);
							deliveryOrderDetail.setAuxnumdetail(CommonUtils.strDigitNumDeal(newAuxnum + deliveryOrderDetail.getAuxunitname() + newOvernum.setScale(BillGoodsNumDecimalLenUtils.decimalLen,BigDecimal.ROUND_HALF_UP) + deliveryOrderDetail.getUnitname()));
							//根据取价参数 获取不同的取价规则
							String priceType = getSysParamValue("DELIVERYORDERPRICE");
							BigDecimal price = (null != map.get("price")) ? new BigDecimal((String) map.get("price")) : BigDecimal.ZERO;
							if(!price.equals(BigDecimal.ZERO)){
								//0取基准销售价,1取合同价
								if("1".equals(priceType)){
									price = getGoodsPriceByCustomer(goodsInfo.getId(),customerid);
								}else{
									price = goodsInfo.getBasesaleprice();
								}
							}
							deliveryOrderDetail.setPrice(price);
							deliveryOrderDetail.setTaxamount(deliveryOrderDetail.getUnitnum().multiply(deliveryOrderDetail.getPrice()));
							deliveryOrderDetail.setTotalbox(deliveryOrderDetail.getAuxnum().add(deliveryOrderDetail.getOvernum().divide(boxnum, 2, BigDecimal.ROUND_HALF_UP)));
							detaillist.add(deliveryOrderDetail);
						}
						else if (!auxnum.equals(BigDecimal.ZERO)||!overnum.equals(BigDecimal.ZERO)) {
							if(overnum.compareTo(boxnum) >= 0){
								BigDecimal i = overnum.divide(boxnum,0,BigDecimal.ROUND_DOWN);
								auxnum = auxnum.add(i);
								overnum = overnum.subtract(i.multiply(boxnum));
							}
							DeliveryOrderDetail deliveryOrderDetail = new DeliveryOrderDetail();
							deliveryOrderDetail.setGoodsid(goodsInfo.getId());
							deliveryOrderDetail.setBillid(indexID);
							deliveryOrderDetail.setAuxunitid(goodsInfo.getAuxunitid());
							deliveryOrderDetail.setAuxunitname(goodsInfo.getAuxunitname());
							unitnum =  auxnum.multiply(boxnum).add(overnum);
							deliveryOrderDetail.setUnitnum(unitnum);
							deliveryOrderDetail.setUnitid(goodsInfo.getMainunit());
							deliveryOrderDetail.setUnitname(goodsInfo.getMainunitName());
							deliveryOrderDetail.setBrandid(goodsInfo.getBrand());
							deliveryOrderDetail.setGoodssort(goodsInfo.getDefaultsort());
							deliveryOrderDetail.setAuxnum(auxnum);
							deliveryOrderDetail.setOvernum(overnum);
							deliveryOrderDetail.setAuxnumdetail(CommonUtils.strDigitNumDeal(auxnum + deliveryOrderDetail.getAuxunitname() + overnum.setScale(BillGoodsNumDecimalLenUtils.decimalLen,BigDecimal.ROUND_HALF_UP) + deliveryOrderDetail.getUnitname()));
							BigDecimal price = (null != map.get("price")) ? new BigDecimal((String) map.get("price")) : BigDecimal.ZERO;
							if (!price.equals(BigDecimal.ZERO)){
								deliveryOrderDetail.setPrice(price);
							}
							else{
								deliveryOrderDetail.setPrice(goodsInfo.getBasesaleprice());
							}
							deliveryOrderDetail.setTaxamount(deliveryOrderDetail.getUnitnum().multiply(deliveryOrderDetail.getPrice()));
							deliveryOrderDetail.setTotalbox(deliveryOrderDetail.getAuxnum().add(deliveryOrderDetail.getOvernum().divide(boxnum, 2, BigDecimal.ROUND_HALF_UP)));
							detaillist.add(deliveryOrderDetail);
						}
					} catch (Exception e) {
						String msg = "";
						if(StringUtils.isNotEmpty(goodsid)){
							msg = "商品编码为："+goodsid;
						}else if(StringUtils.isNotEmpty(spell)){
							msg = "助记符为："+spell;
						}else if(StringUtils.isNotEmpty(barcode)){
							msg = "条形码为："+barcode;
						}
						if(StringUtils.isEmpty(errormsg)){
							errormsg = "客户编码为："+customerid+msg+"出错;";
						}else{
							errormsg += "<br>" + "客户编码为："+customerid+msg+"出错;";
						}
					}
				}else{
					if(StringUtils.isNotEmpty(goodsid)){
						if(StringUtils.isNotEmpty(nullgoodsids)){
							nullgoodsids += "," + goodsid;
						}else{
							nullgoodsids = goodsid;
						}
					}else if(StringUtils.isNotEmpty(spell)){
						if(StringUtils.isNotEmpty(nullspells)){
							nullspells += "," + spell;
						}else{
							nullspells = spell;
						}
					}else if(StringUtils.isNotEmpty(barcode)){
						if(StringUtils.isNotEmpty(nullbarcode)){
							nullbarcode += "," + barcode;
						}else{
							nullbarcode = barcode;
						}
					}else{
						emptyGoods = true;
						emptyGoodsNum++;
					}
				}
			}else{
				emptyCustomer = true;
				emptyCustomerNum++;
			}
		}
		//执行导入操作，即新增采购计划单
		boolean flag=false;
		if (!deliveryOrderList.isEmpty()) {
			for (DeliveryOrder deliveryOrder : deliveryOrderList) {
				deliveryOrder.setStatus("2");
				BigDecimal totalBox = new BigDecimal("0");// 总箱数
				BigDecimal totalWeight = new BigDecimal("0");// 总重量
				BigDecimal totalVolume = new BigDecimal("0");// 总体积
				BigDecimal totalaMount = new BigDecimal("0");// 总金额
				for (DeliveryOrderDetail detail : detaillist) {
					if(detail.getBillid().equals(deliveryOrder.getId())){
					GoodsInfo goodsInfo = getGoodsInfoByID(detail.getGoodsid());
					// 获取总箱数
					totalBox = totalBox.add(detail.getTotalbox());
					// 获取总重量
					if (goodsInfo.getGrossweight() != null)
						totalWeight = totalWeight.add(goodsInfo.getGrossweight().multiply(detail.getUnitnum()));
					// 获取总体积
					if (goodsInfo.getSinglevolume() != null)
						totalVolume = totalVolume.add(goodsInfo.getSinglevolume().multiply(detail.getUnitnum()));
					// 获取总金额
					totalaMount = totalaMount.add(detail.getTaxamount());
					}
				}
				deliveryOrder.setTotalamount(totalaMount);
				deliveryOrder.setTotalbox(totalBox);
				deliveryOrder.setTotalvolume(totalVolume);
				deliveryOrder.setTotalweight(totalWeight);
				SysUser sysUser = getSysUser();
				deliveryOrder.setAdddeptid(sysUser.getDepartmentid());
				deliveryOrder.setAdddeptname(sysUser.getDepartmentname());
				deliveryOrder.setAdduserid(sysUser.getUserid());
				deliveryOrder.setAddusername(sysUser.getName());
				deliveryOrder.setRemark("excel导入");
				deliveryOrder.setAddtime(new Date());
				Customer customer;
				if (StringUtils.isNotEmpty(deliveryOrder.getCustomerid())) {
					customer = getCustomerByID(deliveryOrder.getCustomerid());
					deliveryOrder.setCustomersort(customer.getCustomersort());
					deliveryOrder.setPcustomerid(customer.getPid());
			
				}
				
				int i=0;
				for(DeliveryOrderDetail detail:detaillist){
					if(detail.getBillid().equals(deliveryOrder.getId())){
						i = deliveryOrderMapper.addDeliveryOrder(deliveryOrder);
					    break;
					}
				}
				if(i>0){
					flag=true;
					ordernum++;
					idlist.add(deliveryOrder.getId());
				}
				else{
					flag=false;
				}
			}
		}
		if(!detaillist.isEmpty()){
			for (DeliveryOrderDetail detail:detaillist) {
				int i = deliveryOrderMapper.addDeliveryOrderDetail(detail);
				if(i>0){
					flag=true;
					success++;
				}
				else{
					flag=false;
				}
			}
		}	
		Map map = new HashMap();
		if(flag){
			map.put("success", success);
			map.put("ordernum", ordernum);
		}
		else{
			map.put("error", true);
		}
		map.put("idlist", idlist);
		map.put("flag", flag);
		return map;
	}
	/**
	 * 导入客户退货订单
	 * @param list
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 24, 2015
	 */
	public Map importRejectbill(List<Map<String, Object>> list) throws Exception {
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
		//根据供应商编码分割单据
		boolean emptyCustomer = false,emptyGoods = false;
		String nullgoodsids = "",nullspells = "",nullbarcode = "",failRejectbills = "";
		String errormsg = "",drerrormsg = "";
		int emptyCustomerNum = 0,emptyGoodsNum = 0,ordernum=0,success=0;
		List<DeliveryRejectbill> deliveryRejectbillList=new ArrayList<DeliveryRejectbill>();
		List<DeliveryRejectbillDetail> detaillist = new ArrayList<DeliveryRejectbillDetail>();
		List<String> idlist=new ArrayList<String>();
		for(Map<String, Object> map : list){
			String indexID="";
			String supplierid = (null != map.get("supplierid")) ? (String)map.get("supplierid") : "";
			String customerid = (null != map.get("customerid")) ? (String)map.get("customerid") : "";
			String customername = (null != map.get("customername")) ? (String)map.get("customername") : "";
	    	String storageid = (null != map.get("storageid")) ? (String)map.get("storageid") : "";
		    String businessdate = (null != map.get("businessdate")) ? (String)map.get("businessdate") : "";
		    //获取商品信息
		    String goodsid = (null != map.get("goodsid")) ? (String)map.get("goodsid") : "";
			String spell = (null != map.get("spell")) ? (String)map.get("spell") : "";
			String barcode = (null != map.get("barcode")) ? (String)map.get("barcode") : "";
			//根据已知商品编码or助记符or条形码获取商品档案信息
			GoodsInfo goodsInfoQ = null;
			if(StringUtils.isNotEmpty(goodsid)){
				goodsInfoQ = getAllGoodsInfoByID(goodsid);
			}else if(StringUtils.isNotEmpty(spell)){
				goodsInfoQ = getGoodsInfoBySpell(spell);
			}else if(StringUtils.isNotEmpty(barcode)){
				goodsInfoQ = getGoodsInfoByBarcode(barcode);
			}
			GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(goodsInfoQ);
		    
		    if(businessdate=="")
		    	businessdate=CommonUtils.getTodayDataStr();
		    if(customername==""&&customerid!=""){
		    	customername=getCustomerByID(customerid).getName();
		    }
			if(storageid=="")
				storageid="1001";
			if(deliveryRejectbillList.isEmpty()){
				DeliveryRejectbill deliveryRejectbill=new DeliveryRejectbill();
				if(supplierid!="")
			    	deliveryRejectbill.setSupplierid(supplierid);
				else
					deliveryRejectbill.setSupplierid(goodsInfo.getDefaultsupplier());
				deliveryRejectbill.setCustomerid(customerid);
				deliveryRejectbill.setCustomername(customername);
				deliveryRejectbill.setStorageid(storageid);
				deliveryRejectbill.setBusinessdate(businessdate);
				if (isAutoCreate("t_delivery_rejectbill")) {
					// 获取自动编号
					String id = getAutoCreateSysNumbderForeign(deliveryRejectbill, "t_delivery_rejectbill");
					deliveryRejectbill.setId(id);
				} else {
					deliveryRejectbill.setId("KHTH-" + CommonUtils.getDataNumberSendsWithRand());
					
				}
				indexID=deliveryRejectbill.getId();
				deliveryRejectbillList.add(deliveryRejectbill);
			}
			else{
				boolean haveid=false;
				for(DeliveryRejectbill index : deliveryRejectbillList){
					if(customerid.equals(index.getCustomerid())&&storageid.equals(index.getStorageid())&&businessdate.equals(index.getBusinessdate())&&supplierid.equals(index.getSupplierid())){
						indexID=index.getId();
						haveid=true;
						break;
					}
				}
				if(!haveid){
					DeliveryRejectbill deliveryRejectbill=new DeliveryRejectbill();
					deliveryRejectbill.setCustomerid(customerid);
					if(supplierid!="")
				    	deliveryRejectbill.setSupplierid(supplierid);
					else
						deliveryRejectbill.setSupplierid(goodsInfo.getDefaultsupplier());
					deliveryRejectbill.setCustomername(customername);
					deliveryRejectbill.setStorageid(storageid);
					deliveryRejectbill.setBusinessdate(businessdate);
					if (isAutoCreate("t_delivery_rejectbill")) {
						// 获取自动编号
						String id = getAutoCreateSysNumbderForeign(deliveryRejectbill, "t_delivery_rejectbill");
						deliveryRejectbill.setId(id);
					} else {
						deliveryRejectbill.setId("KHTH-" + CommonUtils.getDataNumberSendsWithRand());
					}
					indexID=deliveryRejectbill.getId();
					deliveryRejectbillList.add(deliveryRejectbill);
				}
			}
			if(customerid!=""){
				
				if(null != goodsInfo){
					MeteringUnit meteringUnit = getGoodsDefaulAuxunit(goodsInfo.getId());
					if(null != meteringUnit){
						goodsInfo.setAuxunitid(meteringUnit.getId());
						goodsInfo.setAuxunitname(meteringUnit.getName());
					}
					try {
						BigDecimal boxnum = goodsInfo.getBoxnum();
						BigDecimal unitnum = (null != map.get("unitnum")) ? new BigDecimal((String) map.get("unitnum")) : BigDecimal.ZERO;// 数量
						BigDecimal auxnum = (null != map.get("auxnum")) ? new BigDecimal((String) map.get("auxnum")) : BigDecimal.ZERO;// 箱数
						BigDecimal overnum = (null != map.get("overnum")) ? new BigDecimal((String) map.get("overnum")) : BigDecimal.ZERO;
						if(decimalScale == 0){
							unitnum = unitnum.setScale(decimalScale,BigDecimal.ROUND_DOWN);
							auxnum = auxnum.setScale(decimalScale,BigDecimal.ROUND_DOWN);
							overnum = overnum.setScale(decimalScale,BigDecimal.ROUND_DOWN);
						}else{
							unitnum = unitnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
							auxnum = auxnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
							overnum = overnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
						}
						if (!unitnum.equals(BigDecimal.ZERO)) {
							DeliveryRejectbillDetail deliveryRejectbillDetail = new DeliveryRejectbillDetail();
							deliveryRejectbillDetail.setGoodsid(goodsInfo.getId());
							deliveryRejectbillDetail.setBillid(indexID);
							deliveryRejectbillDetail.setAuxunitid(goodsInfo.getAuxunitid());
							deliveryRejectbillDetail.setAuxunitname(goodsInfo.getAuxunitname());
							deliveryRejectbillDetail.setUnitnum(unitnum);
							deliveryRejectbillDetail.setUnitid(goodsInfo.getMainunit());
							deliveryRejectbillDetail.setUnitname(goodsInfo.getMainunitName());
							deliveryRejectbillDetail.setBrandid(goodsInfo.getBrand());
							deliveryRejectbillDetail.setGoodssort(goodsInfo.getDefaultsort());
							BigDecimal newAuxnum = unitnum.divide(boxnum,0,BigDecimal.ROUND_HALF_DOWN);
							deliveryRejectbillDetail.setAuxnum(newAuxnum);
							BigDecimal newOvernum = unitnum.subtract(newAuxnum.multiply(boxnum));
							deliveryRejectbillDetail.setOvernum(newOvernum);
							deliveryRejectbillDetail.setAuxnumdetail(CommonUtils.strDigitNumDeal(newAuxnum + deliveryRejectbillDetail.getAuxunitname() + newOvernum.setScale(BillGoodsNumDecimalLenUtils.decimalLen,BigDecimal.ROUND_HALF_UP) + deliveryRejectbillDetail.getUnitname()));
							//根据取价参数 获取不同的取价规则
							String priceType = getSysParamValue("DELIVERYORDERPRICE");
							BigDecimal price = new BigDecimal(0);
							if(null != map.get("price")){
								price =  new BigDecimal((String) map.get("price"));
							}else{
								//0取基准销售价,1取合同价
								if("1".equals(priceType)){
									price = getGoodsPriceByCustomer(goodsInfo.getId(),customerid);
								}else{
									price = goodsInfo.getBasesaleprice();
								}
							}
							deliveryRejectbillDetail.setPrice(price);
							deliveryRejectbillDetail.setTaxamount(deliveryRejectbillDetail.getUnitnum().multiply(deliveryRejectbillDetail.getPrice()));
							deliveryRejectbillDetail.setTotalbox(deliveryRejectbillDetail.getAuxnum().add(deliveryRejectbillDetail.getOvernum().divide(boxnum, 2, BigDecimal.ROUND_HALF_UP)));
							detaillist.add(deliveryRejectbillDetail);
						}
						else if (!auxnum.equals(BigDecimal.ZERO)||!overnum.equals(BigDecimal.ZERO)) {
							if(overnum.compareTo(boxnum) >= 0){
								BigDecimal i = overnum.divide(boxnum,0,BigDecimal.ROUND_DOWN);
								auxnum = auxnum.add(i);
								overnum = overnum.subtract(i.multiply(boxnum));
							}
							DeliveryRejectbillDetail deliveryRejectbillDetail = new DeliveryRejectbillDetail();
							deliveryRejectbillDetail.setGoodsid(goodsInfo.getId());
							deliveryRejectbillDetail.setBillid(indexID);
							deliveryRejectbillDetail.setAuxunitid(goodsInfo.getAuxunitid());
							deliveryRejectbillDetail.setAuxunitname(goodsInfo.getAuxunitname());
							unitnum =  auxnum.multiply(boxnum).add(overnum);
							deliveryRejectbillDetail.setUnitnum(unitnum);
							deliveryRejectbillDetail.setUnitid(goodsInfo.getMainunit());
							deliveryRejectbillDetail.setUnitname(goodsInfo.getMainunitName());
							deliveryRejectbillDetail.setBrandid(goodsInfo.getBrand());
							deliveryRejectbillDetail.setGoodssort(goodsInfo.getDefaultsort());
							deliveryRejectbillDetail.setAuxnum(auxnum);
							deliveryRejectbillDetail.setOvernum(overnum);
							deliveryRejectbillDetail.setAuxnumdetail(CommonUtils.strDigitNumDeal(auxnum + deliveryRejectbillDetail.getAuxunitname() + overnum.setScale(BillGoodsNumDecimalLenUtils.decimalLen,BigDecimal.ROUND_HALF_UP) + deliveryRejectbillDetail.getUnitname()));
							//根据取价参数 获取不同的取价规则
							String priceType = getSysParamValue("DELIVERYORDERPRICE");
							BigDecimal price = new BigDecimal(0);
							if(null != map.get("price")){
								price =  new BigDecimal((String) map.get("price"));
							}else{
								//0取基准销售价,1取合同价
								if("1".equals(priceType)){
									price = getGoodsPriceByCustomer(goodsInfo.getId(),customerid);
								}else{
									price = goodsInfo.getBasesaleprice();
								}
							}
							deliveryRejectbillDetail.setPrice(price);
							deliveryRejectbillDetail.setTaxamount(deliveryRejectbillDetail.getUnitnum().multiply(deliveryRejectbillDetail.getPrice()));
							deliveryRejectbillDetail.setTotalbox(deliveryRejectbillDetail.getAuxnum().add(deliveryRejectbillDetail.getOvernum().divide(boxnum, 2, BigDecimal.ROUND_HALF_UP)));
							detaillist.add(deliveryRejectbillDetail);
						}
					} catch (Exception e) {
						String msg = "";
						if(StringUtils.isNotEmpty(goodsid)){
							msg = "商品编码为："+goodsid;
						}else if(StringUtils.isNotEmpty(spell)){
							msg = "助记符为："+spell;
						}else if(StringUtils.isNotEmpty(barcode)){
							msg = "条形码为："+barcode;
						}
						if(StringUtils.isEmpty(errormsg)){
							errormsg = "客户编码为："+customerid+msg+"出错;";
						}else{
							errormsg += "<br>" + "客户编码为："+customerid+msg+"出错;";
						}
					}
				}else{
					if(StringUtils.isNotEmpty(goodsid)){
						if(StringUtils.isNotEmpty(nullgoodsids)){
							nullgoodsids += "," + goodsid;
						}else{
							nullgoodsids = goodsid;
						}
					}else if(StringUtils.isNotEmpty(spell)){
						if(StringUtils.isNotEmpty(nullspells)){
							nullspells += "," + spell;
						}else{
							nullspells = spell;
						}
					}else if(StringUtils.isNotEmpty(barcode)){
						if(StringUtils.isNotEmpty(nullbarcode)){
							nullbarcode += "," + barcode;
						}else{
							nullbarcode = barcode;
						}
					}else{
						emptyGoods = true;
						emptyGoodsNum++;
					}
				}
			}else{
				emptyCustomer = true;
				emptyCustomerNum++;
			}
		}
		//执行导入操作，即新增采购计划单
		boolean flag=false;
		if (!deliveryRejectbillList.isEmpty()) {
			for (DeliveryRejectbill deliveryRejectbill : deliveryRejectbillList) {
				deliveryRejectbill.setStatus("2");
				BigDecimal totalBox = new BigDecimal("0");// 总箱数
				BigDecimal totalWeight = new BigDecimal("0");// 总重量
				BigDecimal totalVolume = new BigDecimal("0");// 总体积
				BigDecimal totalaMount = new BigDecimal("0");// 总金额
				for (DeliveryRejectbillDetail detail : detaillist) {
					if(detail.getBillid().equals(deliveryRejectbill.getId())){
					GoodsInfo goodsInfo = getGoodsInfoByID(detail.getGoodsid());
					// 获取总箱数
					totalBox = totalBox.add(detail.getTotalbox());
					// 获取总重量
					if (goodsInfo.getGrossweight() != null)
						totalWeight = totalWeight.add(goodsInfo.getGrossweight().multiply(detail.getUnitnum()));
					// 获取总体积
					if (goodsInfo.getSinglevolume() != null)
						totalVolume = totalVolume.add(goodsInfo.getSinglevolume().multiply(detail.getUnitnum()));
					// 获取总金额
					totalaMount = totalaMount.add(detail.getTaxamount());
					}
				}
				deliveryRejectbill.setTotalamount(totalaMount);
				deliveryRejectbill.setTotalbox(totalBox);
				deliveryRejectbill.setTotalvolume(totalVolume);
				deliveryRejectbill.setTotalweight(totalWeight);
				SysUser sysUser = getSysUser();
				deliveryRejectbill.setAdddeptid(sysUser.getDepartmentid());
				deliveryRejectbill.setAdddeptname(sysUser.getDepartmentname());
				deliveryRejectbill.setAdduserid(sysUser.getUserid());
				deliveryRejectbill.setAddusername(sysUser.getName());
				deliveryRejectbill.setRemark("excel导入");
				deliveryRejectbill.setAddtime(new Date());
				Customer customer;
				if (StringUtils.isNotEmpty(deliveryRejectbill.getCustomerid())) {
					customer = getCustomerByID(deliveryRejectbill.getCustomerid());
					deliveryRejectbill.setCustomersort(customer.getCustomersort());
					deliveryRejectbill.setPcustomerid(customer.getPid());
		
				}
				
				int i=0;
				for(DeliveryRejectbillDetail detail:detaillist){
					if(detail.getBillid().equals(deliveryRejectbill.getId())){
						i = deliveryRejectbillMapper.addDeliveryRejectbill(deliveryRejectbill);
					    break;
					}
				}
				if(i>0){
					flag=true;
					ordernum++;
					idlist.add(deliveryRejectbill.getId());
				}
				else{
					flag=false;
				}
			}
		}
		if(!detaillist.isEmpty()){
			for (DeliveryRejectbillDetail detail:detaillist) {
				int i = deliveryRejectbillMapper.addDeliveryRejectbillDetail(detail);
				if(i>0){
					flag=true;
					success++;
				}
				else{
					flag=false;
				}
			}
		}	
		Map map = new HashMap();
		if(flag){
			map.put("success", success);
			map.put("ordernum", ordernum);
		}
		else{
			map.put("error", true);
		}
		map.put("idlist", idlist);
		map.put("flag", flag);
		return map;
	}
	/**
	 * 打印到货订单
	 * @param map
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 24, 2015
	  */
	@Override
	public List printAogorder(Map map) throws Exception{
		String datasql = getDataAccessRule("t_delivery_aogorder",null);
		map.put("dataSql", datasql);
		boolean showdetail=false;
		if(null!=map.get("showdetail") && StringUtils.isNotEmpty(map.get("showdetail").toString()) &&"1".equals(map.get("showdetail").toString()) ){
			showdetail=true;
		}
		List<DeliveryAogorder> list=deliveryAogorderMapper.showAogorderListBy(map);
		for(DeliveryAogorder item : list){
			if(StringUtils.isNotEmpty(item.getSupplierid())){
				BuySupplier buySupplier=getSupplierInfoById(item.getSupplierid());
				if(null!=buySupplier){
					item.setSuppliername(buySupplier.getName());
				}
			}
			if(StringUtils.isNotEmpty(item.getStorageid())){
				StorageInfo storageInfo = getStorageInfoByID(item.getStorageid());
				if(null!=storageInfo){
					item.setStoragename(storageInfo.getName());
				}
			}
			if(showdetail){
				List<DeliveryAogorderDetail> detailList=deliveryAogorderMapper.getDeliveryAogorderDetailList(item.getId());
				if(null!=list && list.size()>0){
					for(DeliveryAogorderDetail detail :detailList){
						if(null!=detail ){
							if(StringUtils.isNotEmpty(detail.getGoodsid())){
								detail.setGoodsInfo(getGoodsInfoByID(detail.getGoodsid()));
							}
						
							if(StringUtils.isNotEmpty(detail.getAuxunitid())){
								MeteringUnit meteringUnit=getMeteringUnitById(detail.getAuxunitid());
								if(null!=meteringUnit){
									detail.setAuxunitname(meteringUnit.getName());
								}
							}
						}
					}
					item.setDeliveryAogorderDetail(detailList);
				}
			}
		}
		return list;
	}
	/**
	 * 打印退货订单
	 * @param map
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 24, 2015
	 */
	@Override
	public List printAogreturn(Map map) throws Exception{
		String datasql = getDataAccessRule("t_delivery_aogreturn",null);
		map.put("dataSql", datasql);
		boolean showdetail=false;
		if(null!=map.get("showdetail") && StringUtils.isNotEmpty(map.get("showdetail").toString()) &&"1".equals(map.get("showdetail").toString()) ){
			showdetail=true;
		}
		List<DeliveryAogreturn> list=deliveryAogreturnMapper.showAogreturnListBy(map);
		for(DeliveryAogreturn item : list){
			if(StringUtils.isNotEmpty(item.getSupplierid())){
				BuySupplier buySupplier=getSupplierInfoById(item.getSupplierid());
				if(null!=buySupplier){
					item.setSuppliername(buySupplier.getName());
				}
			}
			if(StringUtils.isNotEmpty(item.getStorageid())){
				StorageInfo storageInfo = getStorageInfoByID(item.getStorageid());
				if(null!=storageInfo){
					item.setStoragename(storageInfo.getName());
				}
			}
			if(showdetail){
				List<DeliveryAogreturnDetail> detailList=deliveryAogreturnMapper.getDeliveryAogreturnDetailList(item.getId());
				if(null!=list && list.size()>0){
					for(DeliveryAogreturnDetail detail :detailList){
						if(null!=detail ){
							if(StringUtils.isNotEmpty(detail.getGoodsid())){
								detail.setGoodsInfo(getGoodsInfoByID(detail.getGoodsid()));
							}
						
							if(StringUtils.isNotEmpty(detail.getAuxunitid())){
								MeteringUnit meteringUnit=getMeteringUnitById(detail.getAuxunitid());
								if(null!=meteringUnit){
									detail.setAuxunitname(meteringUnit.getName());
								}
							}
						}
					}
					item.setDeliveryAogreturnDetail(detailList);
				}
			}
		}
		return list;
	}
	/**
	 * 打印客户订单
	 * @param map
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 24, 2015
	 */
	@Override
	public List printOrder(Map map) throws Exception{
		String datasql = getDataAccessRule("t_delivery_order",null);
		map.put("dataSql", datasql);
		boolean showdetail=false;
		if(null!=map.get("showdetail") && StringUtils.isNotEmpty(map.get("showdetail").toString()) &&"1".equals(map.get("showdetail").toString()) ){
			showdetail=true;
		}
		List<DeliveryOrder> list=deliveryOrderMapper.showOrderListBy(map);
		for(DeliveryOrder item : list){
			if(StringUtils.isNotEmpty(item.getStorageid())){
				StorageInfo storageInfo = getStorageInfoByID(item.getStorageid());
				if(null!=storageInfo){
					item.setStoragename(storageInfo.getName());
				}
			}
			if(StringUtils.isNotEmpty(item.getCustomerid())){
				Customer customerInfo=getCustomerByID(item.getCustomerid());
				if(null!=customerInfo){
					item.setCustomerInfo(customerInfo);
				}
			}
			if(showdetail){
				List<DeliveryOrderDetail> detailList=deliveryOrderMapper.getDeliveryOrderDetailList(item.getId());
				if(null!=list && list.size()>0){
					for(DeliveryOrderDetail detail :detailList){
						if(null!=detail ){
							if(StringUtils.isNotEmpty(detail.getGoodsid())){
								detail.setGoodsInfo(getGoodsInfoByID(detail.getGoodsid()));

								if(StringUtils.isNotEmpty(item.getCustomerid())){
									Customer customer = getCustomerByID(item.getCustomerid());
									if(null!=customer){
										//获取客户店内码
										CustomerPrice customerPrice=getCustomerPriceByCustomerAndGoodsid(item.getCustomerid(), detail.getGoodsid());
										if(null!=customerPrice && StringUtils.isNotEmpty(customerPrice.getShopid())){
											detail.setShopid(customerPrice.getShopid());
										}else if(StringUtils.isNotEmpty(customer.getPid())){
											customerPrice=getCustomerPriceByCustomerAndGoodsid(customer.getPid(), detail.getGoodsid());
											if(null!=customerPrice){
												detail.setShopid(customerPrice.getShopid());
											}
										}
									}
								}
							}
						
							if(StringUtils.isNotEmpty(detail.getAuxunitid())){
								MeteringUnit meteringUnit=getMeteringUnitById(detail.getAuxunitid());
								if(null!=meteringUnit){
									detail.setAuxunitname(meteringUnit.getName());
								}
							}
						}
					}
					item.setDeliveryOrderDetail(detailList);
				}
			}
		}
		return list;
	}
	/**
	 * 打印客户退货订单
	 * @param map
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 24, 2015
	 */
	@Override
	public List printRejectbill(Map map) throws Exception{
		String datasql = getDataAccessRule("t_delivery_rejectbill",null);
		map.put("dataSql", datasql);
		boolean showdetail=false;
		if(null!=map.get("showdetail") && StringUtils.isNotEmpty(map.get("showdetail").toString()) &&"1".equals(map.get("showdetail").toString()) ){
			showdetail=true;
		}
		List<DeliveryRejectbill> list=deliveryRejectbillMapper.showRejectbillListBy(map);
		for(DeliveryRejectbill item : list){
			if(StringUtils.isNotEmpty(item.getStorageid())){
				StorageInfo storageInfo = getStorageInfoByID(item.getStorageid());
				if(null!=storageInfo){
					item.setStoragename(storageInfo.getName());
				}
			}
			if(showdetail){
				List<DeliveryRejectbillDetail> detailList=deliveryRejectbillMapper.getDeliveryRejectbillDetailList(item.getId());
				if(null!=list && list.size()>0){
					for(DeliveryRejectbillDetail detail :detailList){
						if(null!=detail ){
							if(StringUtils.isNotEmpty(detail.getGoodsid())){
								detail.setGoodsInfo(getGoodsInfoByID(detail.getGoodsid()));
							}
						
							if(StringUtils.isNotEmpty(detail.getAuxunitid())){
								MeteringUnit meteringUnit=getMeteringUnitById(detail.getAuxunitid());
								if(null!=meteringUnit){
									detail.setAuxunitname(meteringUnit.getName());
								}
							}
						}
					}
					item.setDeliveryRejectbillDetail(detailList);
				}
			}
		}
		return list;
	}
	/**
	 * 更新到货订单打印次数
	 * @param deliveryAogorder
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 24, 2015
	 */
	public boolean updateAogorderPrinttimes(DeliveryAogorder deliveryAogorder) throws Exception{
        return deliveryAogorderMapper.updateAogorderPrinttimes(deliveryAogorder)>0;
	}
	/**
	 * 更新退货订单打印次数
	 * @param deliveryAogreturn
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 24, 2015
	 */
	public boolean updateAogreturnPrinttimes(DeliveryAogreturn deliveryAogreturn) throws Exception{
        return deliveryAogreturnMapper.updateAogreturnPrinttimes(deliveryAogreturn)>0;
	}
	/**
	 * 更新客户订单打印次数
	 * @param deliveryOrder
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 24, 2015
	 */
	public boolean updateOrderPrinttimes(DeliveryOrder deliveryOrder) throws Exception{
        return deliveryOrderMapper.updateOrderPrinttimes(deliveryOrder)>0;
	}
	/**
	 * 更新客户退货订单打印次数
	 * @param deliveryRejectbill
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 24, 2015
	 */
	public boolean updateRejectbillPrinttimes(DeliveryRejectbill deliveryRejectbill) throws Exception{
        return deliveryRejectbillMapper.updateRejectbillPrinttimes(deliveryRejectbill)>0;
	}

	@Override
	public StorageSummaryBatch getStorageSummaryByStorageidAndGoodsidAndBatchId(String storageId, String goodsId, String batchNo) throws Exception {
		return getStorageSummaryBatchByStorageidAndBatchno(storageId, batchNo, goodsId);
	}

	@Override
	public StorageSummaryBatch getStorageSummaryByStoreIdAndGoodsIdService(String storageid, String goodsid) throws Exception {
		return getStorageSummaryBatchByStorageidAndGoodsid(storageid, goodsid);
	}

}
