/**
 * @(#)LendServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Aug 3, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.dao.StorageMapper;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.dao.LendMapper;
import com.hd.agent.storage.model.*;
import com.hd.agent.storage.service.ILendService;
import com.hd.agent.storage.service.IStorageOtherEnterService;
import com.hd.agent.storage.service.IStorageOtherOutService;
import org.apache.commons.lang3.StringUtils;
import com.hd.agent.basefiles.model.BuySupplier;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * 借货还货单service
 * @author chenwei
 */
public class LendServiceImpl extends BaseStorageServiceImpl implements ILendService {

	private LendMapper lendMapper;

	private StorageMapper storageMapper;

	private String out = "1";

	private String in = "2";

	private String customer = "2";

	private String supply = "1";

	private IStorageOtherOutService storageOtherOutService;

	private IStorageOtherEnterService storageOtherEnterService;

	public LendMapper getLendMapper() {
		return lendMapper;
	}

	public void setLendMapper(LendMapper lendMapper) {
		this.lendMapper = lendMapper;
	}

	public IStorageOtherOutService getStorageOtherOutService() {
		return storageOtherOutService;
	}

	public void setStorageOtherOutService(IStorageOtherOutService storageOtherOutService) {
		this.storageOtherOutService = storageOtherOutService;
	}

	public IStorageOtherEnterService getStorageOtherEnterService() {
		return storageOtherEnterService;
	}

	public void setStorageOtherEnterService(IStorageOtherEnterService storageOtherEnterService) {
		this.storageOtherEnterService = storageOtherEnterService;
	}

	public StorageMapper getStorageMapper() {
		return storageMapper;
	}

	public void setStorageMapper(StorageMapper storageMapper) {
		this.storageMapper = storageMapper;
	}

	@Override
	public Map addLend(Lend lend,
					   List<LendDetail> list) throws Exception {
		if (isAutoCreate("t_storage_lend")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(lend, "t_storage_lend");
			lend.setId(id);
		}else{
			lend.setId("JHHH-"+CommonUtils.getDataNumberSendsWithRand());
		}
		boolean flag = true;
		Map map = new HashMap();
		if (in.equals(lend.getBilltype())) {
			for(LendDetail lendDetail : list){
				lendDetail.setBillid(lend.getId());
				GoodsInfo goodsInfo = getAllGoodsInfoByID(lendDetail.getGoodsid());
				if(null!=goodsInfo){
					lendDetail.setBrandid(goodsInfo.getBrand());
					lendDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
					Map auxmap = countGoodsInfoNumber(lendDetail.getGoodsid(), lendDetail.getAuxunitid(), lendDetail.getUnitnum());
					if(auxmap.containsKey("auxnum")){
						lendDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
					}
				}
				lendDetail.setStorageid(lend.getStorageid());
				int i =lendMapper.addLendDetail(lendDetail);
				if (i <= 0) {
					throw new Exception("保存失败");
				}
			}
			SysUser sysUser = getSysUser();

			lend.setAdddeptid(sysUser.getDepartmentid());
			lend.setAdddeptname(sysUser.getDepartmentname());
			lend.setAdduserid(sysUser.getUserid());
			lend.setAddusername(sysUser.getName());
			int i = lendMapper.addLend(lend);
			if (i <= 0) {
				throw new Exception("保存失败");
			}
			flag = i>0;
		} else {
			String msg = "";
			//状态为保存状态时 判断可用量是否足够
			if("2".equals(lend.getStatus())){
				for(LendDetail lendDetail : list){
					GoodsInfo goodsInfo = getAllGoodsInfoByID(lendDetail.getGoodsid());
					if(null==goodsInfo){
						msg = "商品:"+lendDetail.getGoodsid()+",不存在";
						break;
					}

					StorageSummaryBatch storageSummaryBatch = null;
					//判断商品是否批次管理 获取是否指定了商品批次

					StorageInfo storageInfo = getStorageInfoByID(lend.getStorageid());
					if ("1".equals(storageInfo.getIsbatch())) {
						if("1".equals(goodsInfo.getIsbatch()) || StringUtils.isNotEmpty(lendDetail.getSummarybatchid())){
							storageSummaryBatch = getStorageSummaryBatchById(lendDetail.getSummarybatchid());
						}else{
							storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(lend.getStorageid(), lendDetail.getGoodsid());
						}
						if (storageSummaryBatch == null) {
							msg = "该仓库无该商品:"+goodsInfo.getName();
							flag = false;
							break;
						}
						if(storageSummaryBatch.getUsablenum().compareTo(lendDetail.getUnitnum())==-1) {
							if("1".equals(goodsInfo.getIsbatch())){
								msg = "商品:"+goodsInfo.getName()+",在批次:"+storageSummaryBatch.getBatchno()+"中,数量不足，该批次只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量";
							}else{
								storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
								msg = "商品:"+goodsInfo.getName()+",在仓库:"+storageInfo.getName()+"中,数量不足，该仓库只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量";
							}
							flag = false;
							break;
						}
					} else {
						storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(lend.getStorageid(), lendDetail.getGoodsid());
						if (storageSummaryBatch == null) {
							msg = "该仓库无该商品:"+goodsInfo.getName();
							flag = false;
							break;
						}
						if(storageSummaryBatch.getUsablenum().compareTo(lendDetail.getUnitnum())==-1) {
							storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
							msg = "商品:"+goodsInfo.getName()+",在仓库:"+storageInfo.getName()+"中,数量不足，该仓库只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量";
							flag = false;
							break;
						}
					}
				}
			}
			if(flag){
				for(LendDetail lendDetail : list){
					lendDetail.setBillid(lend.getId());
					GoodsInfo goodsInfo = getAllGoodsInfoByID(lendDetail.getGoodsid());
					if(null!=goodsInfo){
						lendDetail.setBrandid(goodsInfo.getBrand());
						lendDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
						//计算辅单位数量
						Map auxmap = countGoodsInfoNumber(lendDetail.getGoodsid(), lendDetail.getAuxunitid(), lendDetail.getUnitnum());
						if(auxmap.containsKey("auxnum")){
							lendDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
						}
					}
					lendDetail.setStorageid(lend.getStorageid());
					StorageSummaryBatch storageSummaryBatch = null;
					StorageInfo storageInfo = getStorageInfoByID(lend.getStorageid());
					if ("1".equals(storageInfo.getIsbatch())) {
						if ("1".equals(goodsInfo.getIsbatch()) || StringUtils.isNotEmpty(lendDetail.getSummarybatchid())) {
							storageSummaryBatch = getStorageSummaryBatchById(lendDetail.getSummarybatchid());
						} else {
							storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(lend.getStorageid(), lendDetail.getGoodsid());
						}
					} else {
						storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(lend.getStorageid(), lendDetail.getGoodsid());
					}
					lendDetail.setSummarybatchid(storageSummaryBatch.getId());
					int i = lendMapper.addLendDetail(lendDetail);
					if (i <= 0) {
						throw new Exception("保存失败");
					}
				}
				SysUser sysUser = getSysUser();

				lend.setAdddeptid(sysUser.getDepartmentid());
				lend.setAdddeptname(sysUser.getDepartmentname());
				lend.setAdduserid(sysUser.getUserid());
				lend.setAddusername(sysUser.getName());
				int i = lendMapper.addLend(lend);
				if (i <= 0) {
					throw new Exception("保存失败");
				}
				flag = i>0;
			}
			map.put("msg", msg);
		}
		map.put("flag", flag);
		return map;
	}


	@Override
	public Map addImportLend(Lend lend,List<LendDetail> list) throws Exception{
		Map map = new HashMap();
		if(list.size()> 0){
			Map returnMap = addLend(lend,list);
			map.putAll(returnMap);
		}
		return map ;
	}

	@Override
	public Map getLendInfo(String id) throws Exception {
		Lend lend = lendMapper.getLendInfo(id);
		if (supply.equals(lend.getLendtype())) {
			BuySupplier buySupplier = getSupplierInfoById(lend.getLendid());
			lend.setLendname(buySupplier.getName());
		} else {
			Customer customer = getCustomerByID(lend.getLendid());
			lend.setLendname(customer.getName());
		}
		List<LendDetail> list = lendMapper.getLendDetailListByID(id);
		for(LendDetail lendDetail : list){
			GoodsInfo goodsInfo = getGoodsInfoByID(lendDetail.getGoodsid());
			lendDetail.setGoodsInfo(goodsInfo);
			//StorageLocation storageLocation = getStorageLocation(lendDetail.getStoragelocationid());
			//if(null!=storageLocation){
			//	lendDetail.setStoragelocationname(storageLocation.getName());
			//	}
		}
		Map map = new HashMap();
		map.put("lend", lend);
		map.put("detailList", list);
		return map;
	}

	@Override
	public PageData showLendList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_storage_lend", null);
		pageMap.setDataSql(dataSql);
		List<Lend> list = lendMapper.showLendList(pageMap);
		for(Lend lend : list ){
			StorageInfo storageInfo = getStorageInfoByID(lend.getStorageid());
			if(null!=storageInfo){
				lend.setStoragename(storageInfo.getName());
			}
			DepartMent departMent = getDepartmentByDeptid(lend.getDeptid());
			if(null!=departMent){
				lend.setDeptname(departMent.getName());
			}
			if (supply.equals(lend.getLendtype())) {
				BuySupplier buySupplier = getSupplierInfoById(lend.getLendid());
				lend.setLendname(buySupplier.getName());
			} else {
				Customer customer = getCustomerByID(lend.getLendid());
				lend.setLendname(customer.getName());
			}
		}
		PageData pageData = new PageData(lendMapper.showLendListCount(pageMap),list,pageMap);
		return pageData;
	}

	@Override
	public Map editLend(Lend lend,
						List<LendDetail> list) throws Exception {
		boolean flag = true;
		String msg = "";
		Map map = new HashMap();
		Lend oldLend = lendMapper.getLendInfo(lend.getId());
		if(null== oldLend || "3".equals(oldLend.getStatus()) || "4".equals(oldLend.getStatus())){
			map.put("flag", false);
			map.put("msg", "单据已审核，请刷新后再操作。");
			return map;
		}
		if (in.equals(lend.getBilltype())) {
			int i = lendMapper.deleteLendDetailListByBillid(lend.getId());
			if (i <= 0) {
				map.put("flag", false);
				map.put("msg", "保存失败。");
				return map;
			}
			for(LendDetail lendDetail : list){
				lendDetail.setBillid(lend.getId());
				GoodsInfo goodsInfo = getAllGoodsInfoByID(lendDetail.getGoodsid());
				if(null!=goodsInfo){
					lendDetail.setBrandid(goodsInfo.getBrand());
					lendDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
					Map auxmap = countGoodsInfoNumber(lendDetail.getGoodsid(), lendDetail.getAuxunitid(), lendDetail.getUnitnum());
					if(auxmap.containsKey("auxnum")){
						lendDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
					}
				}
				lendDetail.setStorageid(lend.getStorageid());
				i = lendMapper.addLendDetail(lendDetail);
				if (i <= 0) {
					throw new Exception("保存失败");
				}
			}
			SysUser sysUser = getSysUser();
			lend.setModifyuserid(sysUser.getUserid());
			lend.setModifyusername(sysUser.getName());
			i = lendMapper.editLend(lend);
			if (i <= 0){
				throw new Exception("保存失败");
			}
			flag = i>0;
		} else {
			for(LendDetail lendDetail : list){
				StorageSummaryBatch storageSummaryBatch = null;
				//判断商品是否存在
				GoodsInfo goodsInfo = getAllGoodsInfoByID(lendDetail.getGoodsid());
				if(null==goodsInfo){
					msg += "商品:"+lendDetail.getGoodsid()+",不存在";
					flag = false;
					break;
				}
				StorageInfo storageInfo = getStorageInfoByID(lend.getStorageid());
				if ("1".equals(storageInfo.getIsbatch())) {
					//获取要出库的商品批次 没指定批次 则获取指定仓库默认的商品批次
					if("1".equals(goodsInfo.getIsbatch()) || StringUtils.isNotEmpty(lendDetail.getSummarybatchid())){
						storageSummaryBatch = getStorageSummaryBatchById(lendDetail.getSummarybatchid());
					}else{
						storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(lend.getStorageid(), lendDetail.getGoodsid());
					}
				} else {
					storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(lend.getStorageid(), lendDetail.getGoodsid());
				}

				if(null!=lendDetail.getId()){
					LendDetail oldlendDetail = lendMapper.getLendDetail(lendDetail.getId()+"");
					//修改后的数量大于修改前的数量 判断可用量是否足够
					if(lendDetail.getUnitnum().compareTo(oldlendDetail.getUnitnum())==1){
						lendDetail.setStorageid(lend.getStorageid());
						//批次现存量中的可用量是否大于 修改后数量-修改前数量
						if(storageSummaryBatch.getUsablenum().compareTo(lendDetail.getUnitnum().subtract(oldlendDetail.getUnitnum()))==-1){
							if ("1".equals(storageInfo.getIsbatch())) {
								if("1".equals(goodsInfo.getIsbatch())){
									msg += "商品:"+goodsInfo.getName()+",在批次:"+storageSummaryBatch.getBatchno()+"中,数量不足，该批次只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
								} else{
									storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
									msg += "商品:"+goodsInfo.getName()+",在仓库:"+storageInfo.getName()+"中,数量不足，该仓库只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
								}
							} else {
								storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
								msg += "商品:"+goodsInfo.getName()+",在仓库:"+storageInfo.getName()+"中,数量不足，该仓库只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
							}
							flag = false;
						}
					}
				}else{
					if(storageSummaryBatch.getUsablenum().compareTo(lendDetail.getUnitnum())==-1){
						if ("1".equals(storageInfo.getIsbatch())) {
							if("1".equals(goodsInfo.getIsbatch())){
								msg += "商品:"+goodsInfo.getName()+",在批次:"+storageSummaryBatch.getBatchno()+"中,数量不足，该批次只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
							}else{
								storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
								msg += "商品:"+goodsInfo.getName()+",在仓库:"+storageInfo.getName()+"中,数量不足，该仓库只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
							}
						} else {
							storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
							msg += "商品:"+goodsInfo.getName()+",在仓库:"+storageInfo.getName()+"中,数量不足，该仓库只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
						}
						flag = false;
					}
				}
			}
			if(flag){
				//删除明细
				lendMapper.deleteLendDetailListByBillid(lend.getId());
				for(LendDetail lendDetail : list){
					lendDetail.setBillid(lend.getId());
					GoodsInfo goodsInfo = getAllGoodsInfoByID(lendDetail.getGoodsid());
					if(null!=goodsInfo){
						lendDetail.setBrandid(goodsInfo.getBrand());
						//实际成本价 不包括核算成本价
						lendDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
						//计算辅单位数量
						Map auxmap = countGoodsInfoNumber(lendDetail.getGoodsid(), lendDetail.getAuxunitid(), lendDetail.getUnitnum());
						if(auxmap.containsKey("auxnum")){
							lendDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
						}
					}
					lendDetail.setStorageid(lend.getStorageid());
					//根据商品是否批次管理 获取对应的商品批次
					StorageSummaryBatch storageSummaryBatch = null;
					StorageInfo storageInfo = getStorageInfoByID(lend.getStorageid());
					if ("1".equals(storageInfo.getIsbatch())) {
						if("1".equals(goodsInfo.getIsbatch()) || StringUtils.isNotEmpty(lendDetail.getSummarybatchid())){
							storageSummaryBatch = getStorageSummaryBatchById(lendDetail.getSummarybatchid());
						}else{
							storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(lend.getStorageid(), lendDetail.getGoodsid());
						}
					} else {
						storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(lend.getStorageid(), lendDetail.getGoodsid());
					}
					lendDetail.setSummarybatchid(storageSummaryBatch.getId());
					int i = lendMapper.addLendDetail(lendDetail);
					if (i <= 0){
						throw new Exception("保存失败");
					}
				}
				SysUser sysUser = getSysUser();
				lend.setModifyuserid(sysUser.getUserid());
				lend.setModifyusername(sysUser.getName());
				int i = lendMapper.editLend(lend);
				if (i <= 0) {
					throw new Exception("保存失败");
				}
				flag = i>0;
			}
			map.put("msg", msg);
		}
		map.put("flag", flag);
		return map;
	}

	@Override
	public boolean deleteLend(String id) throws Exception {
		Lend lend = lendMapper.getLendInfo(id);
		boolean flag = false;
		if(null==lend){
			return true;
		}else{
			//只有暂存和保存状态才能删除
			if("1".equals(lend.getStatus()) || "2".equals(lend.getStatus())){
				int i = lendMapper.deleteLend(id);
				lendMapper.deleteLendDetailListByBillid(id);
				flag = i>0;
			}
		}
		return flag;
	}

	/**
	 *  审核
	 * @param id
	 * @param storageOtherEnter
	 * @param storageEnterList
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map auditLend(String id,StorageOtherEnter storageOtherEnter,List storageEnterList) throws Exception {
		Map auditMap = new HashMap();
		boolean flag = false;
		String otherId = "";
		Lend lend = lendMapper.getLendInfo(id);
		if (null == lend) {
			auditMap.put("auditflag",flag);
			auditMap.put("msg","单据不存在");
			return auditMap;
		}
		if("2".equals(lend.getStatus()) || "6".equals(lend.getStatus())){
			flag = storageOtherEnterService.addStorageOtherEnter(storageOtherEnter,storageEnterList);
			if (flag) {
				flag=storageOtherEnterService.auditStorageOtherEnter(storageOtherEnter.getId());
				if (!flag) {
					throw new Exception("审核失败！");
				}
				otherId = storageOtherEnter.getId();
			} else {
				throw new Exception("审核失败！");
			}
			SysUser sysUser = getSysUser();
			String billDate=getAuditBusinessdate(lend.getBusinessdate());
			int i = lendMapper.auditLend(id, sysUser.getUserid(), sysUser.getName(),billDate);
			if (i <= 0) {
				throw new Exception("审核失败！");
			}
		} else {
			auditMap.put("auditflag",flag);
			auditMap.put("msg","单据状态不能被审核");
			return auditMap;
		}
		auditMap.put("otherId",otherId);
		auditMap.put("auditflag",flag);
		return auditMap;
	}

	/**
	 *
	 * @param id
	 * @param storageOtherOut
	 * @param storageOutList
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map auditLend(String id,StorageOtherOut storageOtherOut,List storageOutList) throws Exception {
		Map auditMap = new HashMap();
		boolean flag=false;
		String otherId = "";
		Lend lend = lendMapper.getLendInfo(id);
		if (null == lend) {
			auditMap.put("auditflag",flag);
			auditMap.put("msg","单据不存在");
			return auditMap;
		}
		if("2".equals(lend.getStatus()) || "6".equals(lend.getStatus())){
			Map map = storageOtherOutService.addStorageOtherOut(storageOtherOut,storageOutList);
			flag = (Boolean)map.get("flag");
			if (flag) {
				flag = storageOtherOutService.auditStorageOtherOut(storageOtherOut.getId());
				if (!flag) {
					throw new Exception("审核失败！");
				}
				otherId = storageOtherOut.getId();
			} else {
				auditMap.put("auditflag",flag);
				auditMap.put("msg","库存不够");
				return auditMap;
			}
			SysUser sysUser = getSysUser();
			String billdate=getAuditBusinessdate(lend.getBusinessdate());
			int i = lendMapper.auditLend(id, sysUser.getUserid(), sysUser.getName(),billdate);
			if (i <= 0) {
				throw new Exception("审核失败！");
			}
		} else {
			auditMap.put("auditflag",flag);
			auditMap.put("msg","单据状态不能被审核");
			return auditMap;
		}
		auditMap.put("otherId",otherId);
		auditMap.put("auditflag",flag);
		return auditMap;
	}


	@Override
	public Map oppauditLend(String id) throws Exception {
		boolean oppaudiflag = false;
		Map oppauditMap = new HashMap();
		Lend lend = lendMapper.getLendInfo(id);
		if (null == lend) {
			oppauditMap.put("flag",oppaudiflag);
			oppauditMap.put("msg","单据不存在");
			return oppauditMap;
		}
		if (StringUtils.isEmpty(lend.getBilltype())) {
			oppauditMap.put("flag",oppaudiflag);
			oppauditMap.put("msg","单据不存在");
			return oppauditMap;
		}
		if("4".equals(lend.getStatus()) || "3".equals(lend.getStatus())) {
			if (in.equals(lend.getBilltype())) {
				String sid = storageOtherEnterService.getStorageOtherEnterIdBySourceId(id);
				Map map = storageOtherEnterService.oppauditStorageOtherEnter(sid);
				oppaudiflag = (Boolean) map.get("flag");
				if (oppaudiflag) {
					oppaudiflag = storageOtherEnterService.deleteStorageOtherEnter(sid);
					if (!oppaudiflag) {
						throw new Exception("反审失败！");
					}
				} else {
					oppauditMap.put("flag",oppaudiflag);
					oppauditMap.put("msg","库存不够");
					return oppauditMap;
				}
			} else if (out.equals(lend.getBilltype())) {
				String sid = storageOtherOutService.getStorageOtherOutIdBySourceId(id);
				oppaudiflag = storageOtherOutService.oppauditStorageOtherOut(sid);
				if (oppaudiflag) {
					oppaudiflag = storageOtherOutService.deleteStorageOtherOut(sid);
					if (!oppaudiflag) {
						throw new Exception("反审失败！");
					}
				} else {
					throw new Exception("反审失败！");
				}
			} else {
				oppauditMap.put("flag",oppaudiflag);
				oppauditMap.put("msg","单据不存在");
				return oppauditMap;
			}
			SysUser sysUser = getSysUser();
			int i = lendMapper.oppauditLend(id, sysUser.getUserid(), sysUser.getName());
			if (i <= 0) {
				throw new Exception("反审失败！");
			};
			oppauditMap.put("flag",true);
			return oppauditMap;
		} else {
			oppauditMap.put("flag",oppaudiflag);
			oppauditMap.put("msg","单据状态不能被审核");
			return oppauditMap;
		}
	}

	@Override
	public Map submitLendProcess(String id) throws Exception {
		Map map = new HashMap();
		return map;
	}

	@Override
	public LendDetail getLendDetailInfo(String id)
			throws Exception {
		LendDetail lendDetail = lendMapper.getLendDetail(id);
		return lendDetail;
	}
	@Override
	public List showLendListBy(Map map) throws Exception{
		//String datasql = getDataAccessRule("t_storage_other_out",null);
		//map.put("dataSql", datasql);
		boolean showdetail=false;
		if(null!=map.get("showdetail") && StringUtils.isNotEmpty(map.get("showdetail").toString()) &&"1".equals(map.get("showdetail").toString()) ){
			showdetail=true;
		}
		List<Lend> list = lendMapper.showLendListBy(map);
		for(Lend lend : list ){
			StorageInfo storageInfo = getStorageInfoByID(lend.getStorageid());
			if(null!=storageInfo){
				lend.setStoragename(storageInfo.getName());
			}
			DepartMent departMent = getDepartmentByDeptid(lend.getDeptid());
			if(null!=departMent){
				lend.setDeptname(departMent.getName());
			}
			if (supply.equals(lend.getLendtype())) {
				BuySupplier buySupplier = getSupplierInfoById(lend.getLendid());
				lend.setLendname(buySupplier.getName());
			} else {
				Customer customer = getCustomerByID(lend.getLendid());
				lend.setLendname(customer.getName());
			}
			if(showdetail){
				List<LendDetail> detailList = lendMapper.getLendDetailListByID(lend.getId());
				for(LendDetail lendDetail : detailList){
					GoodsInfo goodsInfo = getGoodsInfoByID(lendDetail.getGoodsid());
					lendDetail.setGoodsInfo(goodsInfo);
					//StorageLocation storageLocation = getStorageLocation(lendDetail.getStoragelocationid());
					//if(null!=storageLocation){
						//	lendDetail.setStoragelocationname(storageLocation.getName());
				//	}
				}
				lend.setLendDetailList(detailList);

			}
		}
		return list;
	}
	@Override
	public boolean updateOrderPrinttimes(Lend lend) throws Exception{
		return lendMapper.updateOrderPrinttimes(lend)>0;
	}
	@Override
	public Lend showPureLend(String id) throws Exception{
		return lendMapper.getLendInfo(id);
	}

	/**
	 *
	 * @param storageid
	 * @param goodsid
	 * @param produceddate
	 * @return
	 * @throws Exception
	 */
	@Override
	public StorageSummaryBatch addOrGetSummaryBatchByStorageidAndProduceddate(String storageid,String goodsid,String produceddate) throws Exception{
		return addOrGetStorageSummaryBatchByStorageidAndProduceddate(storageid,goodsid,produceddate);
	}
	/**
	 * 根据仓库编号和批次号 获取批次库存
	 * @param storageid         仓库编号
	 * @param batchno           批次号
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jun 18, 2013
	 */
	@Override
	public StorageSummaryBatch getSummaryBatchByStorageidAndBatchno(String storageid,String batchno,String goodsid) throws Exception{
		return getStorageSummaryBatchByStorageidAndBatchno(storageid,batchno,goodsid);
	}

	/**
	 * 根据仓库编码和商品编码 获取批次现存量（商品不进行批次管理 库位管理时）
	 * @param storageid             仓库编号
	 * @param goodsid               商品编号
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jun 4, 2013
	 */
	@Override
	public StorageSummaryBatch getSummaryBatchByStorageidAndGoodsid(String storageid,String goodsid) throws Exception{
		return getStorageSummaryBatchByStorageidAndGoodsid(storageid,goodsid);
	}

	/**
	 * 单据导入
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> lendImport(List<Map<String, Object>> list,String type) throws Exception {
		String msg="";
		Map lendMap = new HashMap();
		List<ImportLend> importLendList = new ArrayList<ImportLend>();
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
		Map<ImportLend, List<LendDetail>> exportList = new HashMap<ImportLend, List<LendDetail>>();
		List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
		for(Map m : list){
			LendDetail lendDetail = new LendDetail();
			ImportLend importLend = new ImportLend();
			String storageid = (String) m.get("storageid");
			String lendtype = (String) m.get("lendtype");
			if ("客户".equals(StringUtils.trim(lendtype))) {
				lendtype = "2";
			} else {
				lendtype = "1";
			}
			String lendid = (String) m.get("lendid");
			importLend.setStorageid(storageid);
			importLend.setLendid(lendid);
			importLend.setLendtype(lendtype);

			BigDecimal unitnum = new BigDecimal(0);
			if (StringUtils.isNotEmpty((String) m.get("unitnum"))){
				unitnum = new BigDecimal((String)m.get("unitnum"));
			}
			BigDecimal taxprice = new BigDecimal(0);
			BigDecimal taxamount = new BigDecimal(0);
			if(StringUtils.isNotEmpty((String)m.get("taxprice"))) {
				taxprice = new BigDecimal((String)m.get("taxprice"));
			}
			if (StringUtils.isNotEmpty((String)m.get("taxamount"))) {
				taxamount = new BigDecimal((String)m.get("taxamount"));
			}
			if(decimalScale == 0){
				unitnum = unitnum.setScale(decimalScale,BigDecimal.ROUND_DOWN);
				taxprice = taxprice.setScale(decimalScale,BigDecimal.ROUND_DOWN);
				taxamount = taxamount.setScale(decimalScale,BigDecimal.ROUND_DOWN);
			}else{
				unitnum = unitnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
				taxprice = taxprice.setScale(decimalScale,BigDecimal.ROUND_DOWN);
				taxamount = taxamount.setScale(decimalScale,BigDecimal.ROUND_DOWN);
			}
			GoodsInfo goodsInfo = null;
			String goodid = (String)m.get("goodsid");
			if(StringUtils.isNotEmpty(goodid)){
				goodsInfo = getGoodsInfoByID(goodid);
				if (null == goodsInfo) {
					m.put("errors","无该商品，商品编号不对。");
					errorList.add(m);
					continue;
				}else if(!"1".equals(goodsInfo.getState())){
					m.put("errors","商品状态不对。");
					errorList.add(m);
					continue;
				}
			} else {
				m.put("errors","商品编号不能空。");
				errorList.add(m);
				continue;
			}
			if (StringUtils.isNotEmpty(storageid)) {
				StorageInfo storageInfo = storageMapper.showStorageInfo(storageid);
				if(null == storageInfo){
					m.put("errors","无该仓库，仓库编号不对。");
					errorList.add(m);
					continue;
				} else {
					StorageSummaryBatch storageSummaryBatch = null;
					if("1".equals(storageInfo.getIsbatch())) {
						String batchno = (String) m.get("batchno");
						if("1".equals(goodsInfo.getIsbatch())){
							if(StringUtils.isEmpty((batchno))){
								String produceddate = (String) m.get("produceddate");
								if(StringUtils.isEmpty(produceddate)){
									msg = "批次商品:"+goodid+"没有生产日期，无法导入。";
									m.put("errors",msg);
									errorList.add(m);
									continue;
								}else{
									storageSummaryBatch = addOrGetSummaryBatchByStorageidAndProduceddate(storageid, goodid,produceddate);
									lendDetail.setSummarybatchid(storageSummaryBatch.getId());
									lendDetail.setDeadline(storageSummaryBatch.getDeadline());
									lendDetail.setProduceddate(storageSummaryBatch.getProduceddate());
								}
							}else{
								storageSummaryBatch = getSummaryBatchByStorageidAndBatchno(storageid, batchno, goodid);
								if(null == storageSummaryBatch){
									msg = "批次商品:"+goodid+"无法导入";
									m.put("errors",msg);
									errorList.add(m);
									continue;
								}
								lendDetail.setSummarybatchid(storageSummaryBatch.getId());
								lendDetail.setDeadline(storageSummaryBatch.getDeadline());
								lendDetail.setProduceddate(storageSummaryBatch.getProduceddate());
							}
						}
					}
					if(null == storageSummaryBatch){
						storageSummaryBatch = getSummaryBatchByStorageidAndGoodsid(storageid, goodid);
						//非批次仓库不存在该非批次商品
						if(null == storageSummaryBatch){
							msg = "仓库中不存在商品："+goodid;
							m.put("errors",msg);
							errorList.add(m);
							continue;
						}else{
							lendDetail.setSummarybatchid(storageSummaryBatch.getId());
						}
					}
					//对批次管理的商品进行批次号添加
					if("1".equals(goodsInfo.getIsbatch()) &&  StringUtils.isEmpty(lendDetail.getBatchno())){
						if(StringUtils.isNotEmpty(storageSummaryBatch.getBatchno())){
							lendDetail.setBatchno(storageSummaryBatch.getBatchno());
						}else {
							lendDetail.setBatchno(goodsInfo.getId());
						}
					}
					//商品可用量不足判断
					if(storageSummaryBatch.getUsablenum().compareTo(unitnum)==-1){
						if("1".equals(goodsInfo.getIsbatch())){
							msg = "商品编码:"+goodid+",在批次:"+storageSummaryBatch.getBatchno()+"中,可用量不足";
							m.put("errors",msg);
							errorList.add(m);
						}else{
							msg = "商品编码:"+goodid+",在该仓库中,可用量不足";
							m.put("errors",msg);
							errorList.add(m);
						}
					}
				}
			} else {
				m.put("errors","仓库编号不能空。");
				errorList.add(m);
				continue;
			}
			if (lendMap.containsKey(importLend)) {
				List<String> goodlist = (List<String>) lendMap.get(importLend);
				if (goodlist.contains(goodid)) {
					m.put("errors","商品重复导入。");
					errorList.add(m);
				} else {
					lendDetail.setStorageid(storageid);
					lendDetail.setLendtype(lendtype);
					lendDetail.setLendid(lendid);
					lendDetail.setUnitnum(unitnum);
					lendDetail.setGoodsid(goodid);
					lendDetail.setGoodsInfo(goodsInfo);

					if(m.containsKey("batchno")){
						lendDetail.setBatchno((String) m.get("batchno"));
					}
					if(m.containsKey("produceddate")){
						lendDetail.setProduceddate((String) m.get("produceddate"));
					}
					if(m.containsKey("deadline")){
						lendDetail.setDeadline((String) m.get("deadline"));
					}
					if(m.containsKey("remark")){
						lendDetail.setRemark((String) m.get("remark"));
					}
					List<LendDetail> lendDetailList = exportList.get(importLend);
					lendDetailList.add(lendDetail);
					exportList.put(importLend,lendDetailList);
				}
			} else {
				List<String> goodlist = new ArrayList<String>();
				goodlist.add(goodid);
				lendMap.put(importLend,goodlist);
				lendDetail.setStorageid(storageid);
				lendDetail.setLendtype(lendtype);
				lendDetail.setLendid(lendid);
				lendDetail.setUnitnum(unitnum);
				lendDetail.setGoodsid(goodid);
				lendDetail.setGoodsInfo(goodsInfo);

				if(m.containsKey("batchno")){
					lendDetail.setBatchno((String) m.get("batchno"));
				}
				if(m.containsKey("produceddate")){
					lendDetail.setProduceddate((String) m.get("produceddate"));
				}
				if(m.containsKey("deadline")){
					lendDetail.setDeadline((String) m.get("deadline"));
				}
				if(m.containsKey("remark")){
					lendDetail.setRemark((String) m.get("remark"));
				}
				if (m.containsKey("taxprice")) {
					lendDetail.setTaxprice(taxprice);
				}
				if (m.containsKey("taxamount")) {
					lendDetail.setTaxamount(taxamount);
				}
				List<LendDetail> lendDetailList = new ArrayList<LendDetail>();
				lendDetailList.add(lendDetail);
				exportList.put(importLend,lendDetailList);
				importLendList.add(importLend);
			}
		}
		for (int i = 0 ; i < importLendList.size(); i++) {
			ImportLend importLend = importLendList.get(i);
			Lend lend = new Lend();
			lend.setLendtype(importLend.getLendtype());
			lend.setLendid(importLend.getLendid());
			lend.setStorageid(importLend.getStorageid());
			lend.setStatus("2");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			lend.setBusinessdate(dateFormat.format(new Date()));
			if ("2".equals(type)) {
				lend.setBilltype("2");
			} else {
				lend.setBilltype("1");
			}
			Map map = addImportLend(lend, exportList.get(importLend));
		}
		return  errorList;
	}
}