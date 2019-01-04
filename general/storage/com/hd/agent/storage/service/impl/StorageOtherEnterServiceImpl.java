/**
 * @(#)StorageOtherEnterServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Aug 3, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.dao.StorageOtherEnterMapper;
import com.hd.agent.storage.model.StorageOtherEnter;
import com.hd.agent.storage.model.StorageOtherEnterDetail;
import com.hd.agent.storage.model.StorageSummaryBatch;
import com.hd.agent.storage.service.IStorageOtherEnterService;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 
 * 其他出库单service实现类
 * @author chenwei
 */
public class StorageOtherEnterServiceImpl extends BaseStorageServiceImpl
		implements IStorageOtherEnterService {

	private StorageOtherEnterMapper storageOtherEnterMapper;

	public StorageOtherEnterMapper getStorageOtherEnterMapper() {
		return storageOtherEnterMapper;
	}

	public void setStorageOtherEnterMapper(
			StorageOtherEnterMapper storageOtherEnterMapper) {
		this.storageOtherEnterMapper = storageOtherEnterMapper;
	}

	@Override
	public boolean addStorageOtherEnter(StorageOtherEnter storageOtherEnter,
			List<StorageOtherEnterDetail> list) throws Exception {
		if (isAutoCreate("t_storage_other_enter")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(storageOtherEnter, "t_storage_other_enter");
			storageOtherEnter.setId(id);
		}else{
			storageOtherEnter.setId("QTRK-"+CommonUtils.getDataNumberSendsWithRand());
		} 
		for(StorageOtherEnterDetail storageOtherEnterDetail : list){
			storageOtherEnterDetail.setBillid(storageOtherEnter.getId());
			GoodsInfo goodsInfo = getAllGoodsInfoByID(storageOtherEnterDetail.getGoodsid());
			if(null!=goodsInfo){
				storageOtherEnterDetail.setBrandid(goodsInfo.getBrand());
				storageOtherEnterDetail.setCostprice(storageOtherEnterDetail.getTaxprice());
				//实际成本价 不包括核算成本价
				storageOtherEnterDetail.setRealcostprice(storageOtherEnterDetail.getTaxprice());
                storageOtherEnterDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
				//计算辅单位数量
				Map auxmap = countGoodsInfoNumber(storageOtherEnterDetail.getGoodsid(), storageOtherEnterDetail.getAuxunitid(), storageOtherEnterDetail.getUnitnum());
				if(auxmap.containsKey("auxnum")){
					storageOtherEnterDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
				}
			}
			storageOtherEnterDetail.setStorageid(storageOtherEnter.getStorageid());
			
			storageOtherEnterMapper.addStorageOtherEnterDetail(storageOtherEnterDetail);
		}
		SysUser sysUser = getSysUser();
		
		storageOtherEnter.setAdddeptid(sysUser.getDepartmentid());
		storageOtherEnter.setAdddeptname(sysUser.getDepartmentname());
		storageOtherEnter.setAdduserid(sysUser.getUserid());
		storageOtherEnter.setAddusername(sysUser.getName());
		int i = storageOtherEnterMapper.addStorageOtherEnter(storageOtherEnter);
		return i>0;
	}

    @Override
	public Map addImportStorageOtherEnter(StorageOtherEnter storageOtherEnter, List<StorageOtherEnterDetail> list) throws Exception{
        Map map = new HashMap();
        Map<String,String> disUsable = new HashMap();
        String disUsableMsg = "";
        List detailList = new ArrayList();
        //筛选出可用量不足的商品
        for(StorageOtherEnterDetail storageOtherEnterDetail : list){
            String msg = "";
            int index = list.indexOf(storageOtherEnterDetail);
            GoodsInfo goodsInfo = getAllGoodsInfoByID(storageOtherEnterDetail.getGoodsid());
            if(null==goodsInfo){
                msg += "商品编码："+storageOtherEnterDetail.getGoodsid()+"不存在";
                disUsable.put(index+"",msg);
                continue;
            }
            StorageSummaryBatch storageSummaryBatch = null;
            //批次商品根据生产日期获取批次号
            if("1".equals(goodsInfo.getIsbatch())){
                if(StringUtils.isEmpty(storageOtherEnterDetail.getBatchno())){
                    if(StringUtils.isEmpty(storageOtherEnterDetail.getProduceddate())){
                        msg += "批次商品:"+storageOtherEnterDetail.getGoodsid()+"没有生产日期，无法导入";
                        disUsable.put(index+"",msg);
                        continue;
                    }else{
                        storageSummaryBatch = addOrGetStorageSummaryBatchByStorageidAndProduceddate(storageOtherEnter.getStorageid(), storageOtherEnterDetail.getGoodsid(),storageOtherEnterDetail.getProduceddate());
                        storageOtherEnterDetail.setSummarybatchid(storageSummaryBatch.getId());
                        storageOtherEnterDetail.setDeadline(storageSummaryBatch.getDeadline());
                        storageOtherEnterDetail.setProduceddate(storageSummaryBatch.getProduceddate());
                    }
                }else{
                    storageSummaryBatch = getStorageSummaryBatchByStorageidAndBatchno(storageOtherEnter.getStorageid(), storageOtherEnterDetail.getBatchno(), storageOtherEnterDetail.getGoodsid());
                    if(null == storageSummaryBatch){
                        msg += "批次商品:"+storageOtherEnterDetail.getGoodsid()+"无法导入:";
                        disUsable.put(index+"",msg);
                        continue;
                    }else{
                        storageOtherEnterDetail.setSummarybatchid(storageSummaryBatch.getId());
                        storageOtherEnterDetail.setDeadline(storageSummaryBatch.getDeadline());
                        storageOtherEnterDetail.setProduceddate(storageSummaryBatch.getProduceddate());
                    }

                }
            }
            //非批次商品获取库存
            if(null == storageSummaryBatch){
                storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(storageOtherEnter.getStorageid(), storageOtherEnterDetail.getGoodsid());
                //非批次仓库不存在该非批次商品
                if(null == storageSummaryBatch){
//                    msg = ".仓库中不存在商品："+storageOtherEnterDetail.getGoodsid();
//                    disUsable.put(index+"",msg);
//                    continue;
                }else{
                    storageOtherEnterDetail.setSummarybatchid(storageSummaryBatch.getId());
                }
            }
            //对批次管理的商品进行批次号添加
            if("1".equals(goodsInfo.getIsbatch()) &&  StringUtils.isEmpty(storageOtherEnterDetail.getBatchno())){
                if(StringUtils.isNotEmpty(storageSummaryBatch.getBatchno())){
                    storageOtherEnterDetail.setBatchno(storageSummaryBatch.getBatchno());
                }else {
                    storageOtherEnterDetail.setBatchno(goodsInfo.getId());
                }
            }
            detailList.add(storageOtherEnterDetail);
        }
        //移除不满足条件的商品
        for(Map.Entry<String,String> entry : disUsable.entrySet()){
            if(disUsableMsg == ""){
                disUsableMsg = entry.getValue();
            }else{
                disUsableMsg += entry.getValue();
            }
        }
        map.put("disUsableMsg",disUsableMsg);
        if(list.size()> 0){
            boolean flag = addStorageOtherEnter(storageOtherEnter,detailList);
            map.put("flag",flag);
        }
        return map ;

    }

	@Override
	public Map getStorageOtherEnterInfo(String id) throws Exception {
		StorageOtherEnter storageOtherEnter = storageOtherEnterMapper.getStorageOtherEnterInfo(id);
		if(null!=storageOtherEnter){
			Personnel personnel = getPersonnelById(storageOtherEnter.getUserid());
			if(null!=personnel){
				storageOtherEnter.setUsername(personnel.getName());
			}
		}
		List<StorageOtherEnterDetail> list = storageOtherEnterMapper.getStorageOtherEnterDetailListByID(id);
		for(StorageOtherEnterDetail storageOtherEnterDetail : list){
			GoodsInfo goodsInfo = getGoodsInfoByID(storageOtherEnterDetail.getGoodsid());
			if(goodsInfo!=null){
				goodsInfo.setItemno(getItemnoByGoodsAndStorage(storageOtherEnterDetail.getGoodsid(),storageOtherEnterDetail.getStorageid()));
			}
			storageOtherEnterDetail.setGoodsInfo(goodsInfo);
			StorageLocation storageLocation = getStorageLocation(storageOtherEnterDetail.getStoragelocationid());
			if(null!=storageLocation){
				storageOtherEnterDetail.setStoragelocationname(storageLocation.getName());
			}
		}
		Map map = new HashMap();
		map.put("storageOtherEnter", storageOtherEnter);
		map.put("detailList", list);
		return map;
	}

	@Override
	public PageData showStorageOtherEnterList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_storage_other_enter", null);
		pageMap.setDataSql(dataSql);
		List<StorageOtherEnter> list = storageOtherEnterMapper.showStorageOtherEnterList(pageMap);
		for(StorageOtherEnter storageOtherEnter : list ){
			StorageInfo storageInfo = getStorageInfoByID(storageOtherEnter.getStorageid());
			if(null!=storageInfo){
				storageOtherEnter.setStoragename(storageInfo.getName());
			}
			DepartMent departMent = getDepartmentByDeptid(storageOtherEnter.getDeptid());
			if(null!=departMent){
				storageOtherEnter.setDeptname(departMent.getName());
			}
			Personnel personnel = getPersonnelById(storageOtherEnter.getUserid());
			if(null!=personnel){
				storageOtherEnter.setUsername(personnel.getName());
			}
			StorageInout storageInout = getBaseFilesStorageMapper().showStorageInoutInfo(storageOtherEnter.getEntertype());
			if(null!=storageInout){
				storageOtherEnter.setEntertypename(storageInout.getName());
			}
			SysCode sysCode=getBaseSysCodeInfo(storageOtherEnter.getEntertype(),"storageOtherEnterType");
			if(sysCode!=null){
				storageOtherEnter.setEntertypename(sysCode.getCodename());
			}
			//查询金额
			Map map = storageOtherEnterMapper.getStorageOtherEnterAmountByID(storageOtherEnter.getId());
			if(null!=map && map.size() > 0){
				storageOtherEnter.setTaxamount((BigDecimal)map.get("taxamount"));
				storageOtherEnter.setNotaxamount((BigDecimal)map.get("notaxamount"));
			}
		}
		PageData pageData = new PageData(storageOtherEnterMapper.showStorageOtherEnterListCount(pageMap),list,pageMap);
		return pageData;
	}

	@Override
	public boolean editStorageOtherEnter(StorageOtherEnter storageOtherEnter,
			List<StorageOtherEnterDetail> list) throws Exception {
		//判断单据能否修改
		StorageOtherEnter oldStorageOtherEnter = storageOtherEnterMapper.getStorageOtherEnterInfo(storageOtherEnter.getId());
		if(null==oldStorageOtherEnter || "3".equals(oldStorageOtherEnter.getStatus()) || "4".equals(oldStorageOtherEnter.getStatus())){
			return false;
		}
		storageOtherEnterMapper.deleteStorageOtherEnterDetailListByBillid(storageOtherEnter.getId());
		for(StorageOtherEnterDetail storageOtherEnterDetail : list){
			storageOtherEnterDetail.setBillid(storageOtherEnter.getId());
			GoodsInfo goodsInfo = getAllGoodsInfoByID(storageOtherEnterDetail.getGoodsid());
			if(null!=goodsInfo){
				storageOtherEnterDetail.setBrandid(goodsInfo.getBrand());
				storageOtherEnterDetail.setCostprice(storageOtherEnterDetail.getTaxprice());
                storageOtherEnterDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
				//实际成本价 不包括核算成本价
				storageOtherEnterDetail.setRealcostprice(storageOtherEnterDetail.getTaxprice());
				//计算辅单位数量
				Map auxmap = countGoodsInfoNumber(storageOtherEnterDetail.getGoodsid(), storageOtherEnterDetail.getAuxunitid(), storageOtherEnterDetail.getUnitnum());
				if(auxmap.containsKey("auxnum")){
					storageOtherEnterDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
				}
			}
			storageOtherEnterDetail.setStorageid(storageOtherEnter.getStorageid());
			storageOtherEnterMapper.addStorageOtherEnterDetail(storageOtherEnterDetail);
		}
		SysUser sysUser = getSysUser();
		storageOtherEnter.setModifyuserid(sysUser.getUserid());
		storageOtherEnter.setModifyusername(sysUser.getName());
		int i = storageOtherEnterMapper.editStorageOtherEnter(storageOtherEnter);
		return i>0;
	}

	@Override
	public boolean deleteStorageOtherEnter(String id) throws Exception {
		int i = storageOtherEnterMapper.deleteStorageOtherEnter(id);
		storageOtherEnterMapper.deleteStorageOtherEnterDetailListByBillid(id);
		return i>0;
	}

	@Override
	public boolean auditStorageOtherEnter(String id) throws Exception {
		StorageOtherEnter storageOtherEnter = storageOtherEnterMapper.getStorageOtherEnterInfo(id);
		boolean flag = false;
		if("2".equals(storageOtherEnter.getStatus()) || "6".equals(storageOtherEnter.getStatus())){
			List<StorageOtherEnterDetail> list = storageOtherEnterMapper.getStorageOtherEnterDetailListByID(id);
			//更新仓库数量
			for(StorageOtherEnterDetail storageOtherEnterDetail : list){
				StorageSummaryBatch storageSummaryBatch = new StorageSummaryBatch();
				storageSummaryBatch.setGoodsid(storageOtherEnterDetail.getGoodsid());
				storageSummaryBatch.setStorageid(storageOtherEnterDetail.getStorageid());
				storageSummaryBatch.setStoragelocationid(storageOtherEnterDetail.getStoragelocationid());
				storageSummaryBatch.setBatchno(storageOtherEnterDetail.getBatchno());
				storageSummaryBatch.setProduceddate(storageOtherEnterDetail.getProduceddate());
				storageSummaryBatch.setDeadline(storageOtherEnterDetail.getDeadline());
				
				storageSummaryBatch.setUnitid(storageOtherEnterDetail.getUnitid());
				storageSummaryBatch.setUnitname(storageOtherEnterDetail.getUnitname());
				storageSummaryBatch.setAuxunitid(storageOtherEnterDetail.getAuxunitid());
				storageSummaryBatch.setAuxunitname(storageOtherEnterDetail.getAuxunitname());
				storageSummaryBatch.setPrice(storageOtherEnterDetail.getTaxprice());
				
				storageSummaryBatch.setEnterdate(CommonUtils.dataToStr(new Date(), "yyyy-MM-dd"));
				
				Map map = addStorageSummaryNum(storageSummaryBatch,storageOtherEnterDetail.getUnitnum(), "storageOtherEnter", id, "其他入库");
				if(null!=map && map.containsKey("summarybatchid")){
					String summarybatchid = (String) map.get("summarybatchid");
					if(StringUtils.isNotEmpty(summarybatchid)){
						storageOtherEnterMapper.updateStorageOtherEnterDetailSummarybatchid(storageOtherEnterDetail.getId()+"", summarybatchid);
					}
				}
				if(storageOtherEnterDetail.getUnitnum().compareTo(BigDecimal.ZERO)==0){
					// 更新商品成本价
					updateGoodsPriceByAdd(storageOtherEnter.getStorageid(),storageOtherEnterDetail.getGoodsid(),storageOtherEnterDetail.getUnitnum(),storageOtherEnterDetail.getTaxprice(), true, false,true,storageOtherEnter.getId(),storageOtherEnterDetail.getId()+"",storageOtherEnterDetail.getTaxamount());
				}else{
					// 更新商品成本价
					updateGoodsPriceByAdd(storageOtherEnter.getStorageid(),storageOtherEnterDetail.getGoodsid(),storageOtherEnterDetail.getUnitnum(),storageOtherEnterDetail.getTaxprice(), true, false,true,storageOtherEnter.getId(),storageOtherEnterDetail.getId()+"");
				}
			}
			SysUser sysUser = getSysUser();
			String billdate=getAuditBusinessdate(storageOtherEnter.getBusinessdate());
			int i = storageOtherEnterMapper.auditStorageOtherEnter(id, sysUser.getUserid(), sysUser.getName(),billdate);
			flag = i>0;
		}
		return flag;
	}
	@Override
	public Map oppauditStorageOtherEnter(String id) throws Exception {
		StorageOtherEnter storageOtherEnter = storageOtherEnterMapper.getStorageOtherEnterInfo(id);
		boolean flag = false;
		boolean oppflag = true;
		String msg = "";
		if("4".equals(storageOtherEnter.getStatus()) || "3".equals(storageOtherEnter.getStatus())){
			List<StorageOtherEnterDetail> list = storageOtherEnterMapper.getStorageOtherEnterDetailListByID(id);
			Map map = new HashMap();
			//由于商品可能有重复情况，所以用这个map去存放商品可用量
			Map usableMap=new HashMap();
			for(StorageOtherEnterDetail storageOtherEnterDetail : list){
				GoodsInfo goodsInfo = getAllGoodsInfoByID(storageOtherEnterDetail.getGoodsid());
				StorageInfo storageInfo = getStorageInfoByID(storageOtherEnterDetail.getStorageid());
				StorageSummaryBatch storageSummaryBatch = null;
				//判断商品管理方式 批次管理 库位管理 总量管理
				//商品如果是批次管理
				if("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
					//存在该批次现存量 则更新该批次 否则新增一个批次现存量
					storageSummaryBatch = getStorageSummaryBatchByStorageidAndProduceddate(storageOtherEnterDetail.getStorageid(), storageOtherEnterDetail.getGoodsid(), storageOtherEnterDetail.getProduceddate());
				}else{
					//商品进行总量管理时
					storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(storageOtherEnterDetail.getStorageid(), storageOtherEnterDetail.getGoodsid());
				}
				if(!usableMap.containsKey(storageOtherEnterDetail.getGoodsid())){
					usableMap.put(storageOtherEnterDetail.getGoodsid(),storageSummaryBatch.getUsablenum());
				}
				//获取最新可用量
				BigDecimal usablenum=new BigDecimal(usableMap.get(storageOtherEnterDetail.getGoodsid()).toString());

				//批次现存量中的可用量是否大于 入库数量
				if(usablenum.compareTo(storageOtherEnterDetail.getUnitnum())==-1){
					if("1".equals(goodsInfo.getIsbatch())){
						msg += "商品:"+goodsInfo.getName()+",在批次:"+storageSummaryBatch.getBatchno()+"中,数量不足，该批次只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
					}else{
						msg += "商品:"+goodsInfo.getName()+",在仓库:"+storageInfo.getName()+"中,数量不足，该仓库只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
					}
					oppflag = false;
				}
				//记录最新可用量
				usableMap.put(storageOtherEnterDetail.getGoodsid(),usablenum.subtract(storageOtherEnterDetail.getUnitnum()));
			}
			if(oppflag){
				//更新仓库数量
				for(StorageOtherEnterDetail storageOtherEnterDetail : list){
					StorageSummaryBatch storageSummaryBatch = new StorageSummaryBatch();
					storageSummaryBatch.setId(storageOtherEnterDetail.getSummarybatchid());
					storageSummaryBatch.setGoodsid(storageOtherEnterDetail.getGoodsid());
					storageSummaryBatch.setStorageid(storageOtherEnterDetail.getStorageid());
					storageSummaryBatch.setStoragelocationid(storageOtherEnterDetail.getStoragelocationid());
					storageSummaryBatch.setBatchno(storageOtherEnterDetail.getBatchno());
					storageSummaryBatch.setProduceddate(storageOtherEnterDetail.getProduceddate());
					storageSummaryBatch.setDeadline(storageOtherEnterDetail.getDeadline());
					
					storageSummaryBatch.setUnitid(storageOtherEnterDetail.getUnitid());
					storageSummaryBatch.setUnitname(storageOtherEnterDetail.getUnitname());
					storageSummaryBatch.setAuxunitid(storageOtherEnterDetail.getAuxunitid());
					storageSummaryBatch.setAuxunitname(storageOtherEnterDetail.getAuxunitname());
					storageSummaryBatch.setPrice(storageOtherEnterDetail.getTaxprice());
					
//					storageSummaryBatch.setEnterdate(CommonUtils.dataToStr(new Date(), "yyyy-MM-dd"));
					//回滚其他入库
					rollbackStorageSummaryNum(storageSummaryBatch,storageOtherEnterDetail.getUnitnum(), "storageOtherEnter", id, "其他入库");
					// 更新商品成本价
					updateGoodsPriceBySubtract(storageOtherEnter.getId(),storageOtherEnterDetail.getId()+"",storageOtherEnter.getStorageid(),
							storageOtherEnterDetail.getGoodsid(),
							storageOtherEnterDetail.getUnitnum(),
							storageOtherEnterDetail.getTaxprice(), true);
				}
				SysUser sysUser = getSysUser();
				int i = storageOtherEnterMapper.oppauditStorageOtherEnter(id, sysUser.getUserid(), sysUser.getName());
				flag = i>0;
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}
	@Override
	public Map submitStorageOtherEnterProcess(String id) throws Exception {
		Map map = new HashMap();
		return map;
	}
	
	@Override
	public List showStorageOtherEnterListBy(Map map) throws Exception{
		//String datasql = getDataAccessRule("t_storage_other_enter",null);
		//map.put("dataSql", datasql);
		boolean showdetail=false;
		if(null!=map.get("showdetail") && StringUtils.isNotEmpty(map.get("showdetail").toString()) &&"1".equals(map.get("showdetail").toString()) ){
			showdetail=true;
		}
		List<StorageOtherEnter> list = storageOtherEnterMapper.showStorageOtherEnterListBy(map);
		for(StorageOtherEnter storageOtherEnter : list ){
			StorageInfo storageInfo = getStorageInfoByID(storageOtherEnter.getStorageid());
			if(null!=storageInfo){
				storageOtherEnter.setStoragename(storageInfo.getName());
			}
			DepartMent departMent = getDepartmentByDeptid(storageOtherEnter.getDeptid());
			if(null!=departMent){
				storageOtherEnter.setDeptname(departMent.getName());
			}
			Personnel personnel = getPersonnelById(storageOtherEnter.getUserid());
			if(null!=personnel){
				storageOtherEnter.setUsername(personnel.getName());
			}
			StorageInout storageInout = getBaseFilesStorageMapper().showStorageInoutInfo(storageOtherEnter.getEntertype());
			if(null!=storageInout){
				storageOtherEnter.setEntertypename(storageInout.getName());
			}
			
			if(showdetail){
				List<StorageOtherEnterDetail> detailList = storageOtherEnterMapper.getStorageOtherEnterDetailListByID(storageOtherEnter.getId());
				for(StorageOtherEnterDetail storageOtherEnterDetail : detailList){
					GoodsInfo goodsInfo = getGoodsInfoByID(storageOtherEnterDetail.getGoodsid());
					storageOtherEnterDetail.setGoodsInfo(goodsInfo);
					StorageLocation storageLocation = getStorageLocation(storageOtherEnterDetail.getStoragelocationid());
					if(null!=storageLocation){
						storageOtherEnterDetail.setStoragelocationname(storageLocation.getName());
					}
				}
				storageOtherEnter.setOtherEnterDetailList(detailList);
			}
		}
		return list;
	}
	@Override
	public boolean updateOrderPrinttimes(StorageOtherEnter storageOtherEnter) throws Exception{
		return storageOtherEnterMapper.updateOrderPrinttimes(storageOtherEnter)>0;
	}
	@Override
	public StorageOtherEnter showPureStorageOtherEnter(String id) throws Exception{
		return storageOtherEnterMapper.getStorageOtherEnterInfo(id);
	}

	/**
	 * 获取ID
	 * @param sourceid
	 * @return
	 * @throws Exception
	 */
	public String getStorageOtherEnterIdBySourceId(String sourceid) throws Exception {
		return storageOtherEnterMapper.getStorageOtherEnterIdBySourceId(sourceid);
	}

	/**
	 * 获取其它入库单生成凭证数据
	 * @param list
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Dec 20, 2017
	 */
	public List getStorageOtherEnterSumData(List list){
		List datalist=storageOtherEnterMapper.getStorageOtherEnterSumData(list);
		return datalist;
	}
	/**
	 * 其它入库单据金额获取
	 * @param id
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Dec 26, 2017
	 */
	public Map getStorageOtherEnterSumById(String id){
		Map map=storageOtherEnterMapper.getStorageOtherEnterSumById(id);
		return map;
	}
}

