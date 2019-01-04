/**
 * @(#)StorageOtherOutServiceImpl.java
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
import com.hd.agent.storage.dao.StorageOtherOutMapper;
import com.hd.agent.storage.model.*;
import com.hd.agent.storage.service.IStorageOtherOutService;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 
 * 其他出库单service
 * @author chenwei
 */
public class StorageOtherOutServiceImpl extends BaseStorageServiceImpl implements
		IStorageOtherOutService {
	
	private StorageOtherOutMapper storageOtherOutMapper;

	public StorageOtherOutMapper getStorageOtherOutMapper() {
		return storageOtherOutMapper;
	}

	public void setStorageOtherOutMapper(StorageOtherOutMapper storageOtherOutMapper) {
		this.storageOtherOutMapper = storageOtherOutMapper;
	}
	@Override
	public Map addStorageOtherOut(StorageOtherOut storageOtherOut,
			List<StorageOtherOutDetail> list) throws Exception {
		if (isAutoCreate("t_storage_other_out")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(storageOtherOut, "t_storage_other_out");
			storageOtherOut.setId(id);
		}else{
			storageOtherOut.setId("QTCK-"+CommonUtils.getDataNumberSendsWithRand());
		} 
		boolean flag = true;
		String msg = "";
		//状态为保存状态时 判断可用量是否足够
		if("2".equals(storageOtherOut.getStatus())){
			for(StorageOtherOutDetail storageOtherOutDetail : list){
				GoodsInfo goodsInfo = getAllGoodsInfoByID(storageOtherOutDetail.getGoodsid());
				if(null==goodsInfo){
					msg = "商品:"+storageOtherOutDetail.getGoodsid()+",不存在";
					break;
				}
				StorageSummaryBatch storageSummaryBatch = null;
				//判断商品是否批次管理 获取是否指定了商品批次
				if("1".equals(goodsInfo.getIsbatch()) || StringUtils.isNotEmpty(storageOtherOutDetail.getSummarybatchid())){
					storageSummaryBatch = getStorageSummaryBatchById(storageOtherOutDetail.getSummarybatchid());
				}else{
					storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(storageOtherOut.getStorageid(), storageOtherOutDetail.getGoodsid());
				}
				if(storageSummaryBatch.getUsablenum().compareTo(storageOtherOutDetail.getUnitnum())==-1){
					if("1".equals(goodsInfo.getIsbatch())){
						msg = "商品:"+goodsInfo.getName()+",在批次:"+storageSummaryBatch.getBatchno()+"中,数量不足，该批次只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量";
					}else{
						StorageInfo storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
						msg = "商品:"+goodsInfo.getName()+",在仓库:"+storageInfo.getName()+"中,数量不足，该仓库只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量";
					}
					flag = false;
					break;
				}
			}
		}
		if(flag){
			for(StorageOtherOutDetail storageOtherOutDetail : list){
				storageOtherOutDetail.setBillid(storageOtherOut.getId());
				GoodsInfo goodsInfo = getAllGoodsInfoByID(storageOtherOutDetail.getGoodsid());
				if(null!=goodsInfo){
					storageOtherOutDetail.setBrandid(goodsInfo.getBrand());
					storageOtherOutDetail.setCostprice(storageOtherOutDetail.getTaxprice());
					//实际成本价 不包括核算成本价
					storageOtherOutDetail.setRealcostprice(storageOtherOutDetail.getTaxprice());
                    storageOtherOutDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
					//计算辅单位数量
					Map auxmap = countGoodsInfoNumber(storageOtherOutDetail.getGoodsid(), storageOtherOutDetail.getAuxunitid(), storageOtherOutDetail.getUnitnum());
					if(auxmap.containsKey("auxnum")){
						storageOtherOutDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
					}
				}
				storageOtherOutDetail.setStorageid(storageOtherOut.getStorageid());
				StorageSummaryBatch storageSummaryBatch = null;
				if("1".equals(goodsInfo.getIsbatch()) || StringUtils.isNotEmpty(storageOtherOutDetail.getSummarybatchid())){
					storageSummaryBatch = getStorageSummaryBatchById(storageOtherOutDetail.getSummarybatchid());
				}else{
					storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(storageOtherOut.getStorageid(), storageOtherOutDetail.getGoodsid());
				}
				if(null!=storageSummaryBatch){
					//保存状态时，更新待发量
					if("2".equals(storageOtherOut.getStatus())){
						//更新待发量
						updateStorageSummaryWaitnum(storageSummaryBatch.getId(), storageOtherOutDetail.getUnitnum());
					}
				}
				//关联相关商品批次
				storageOtherOutDetail.setSummarybatchid(storageSummaryBatch.getId());
				storageOtherOutMapper.addStorageOtherOutDetail(storageOtherOutDetail);
			}
			SysUser sysUser = getSysUser();
			
			storageOtherOut.setAdddeptid(sysUser.getDepartmentid());
			storageOtherOut.setAdddeptname(sysUser.getDepartmentname());
			storageOtherOut.setAdduserid(sysUser.getUserid());
			storageOtherOut.setAddusername(sysUser.getName());
			int i = storageOtherOutMapper.addStorageOtherOut(storageOtherOut);
			flag = i>0;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}

    public Map addImportStorageOtherOut(StorageOtherOut storageOtherOut,List<StorageOtherOutDetail> list) throws Exception{
        Map map = new HashMap();
        Map<String,String> disUsable = new HashMap();
        String disUsableMsg = "";
		List<StorageOtherOutDetail> addList = new ArrayList<StorageOtherOutDetail>();
        //筛选出可用量不足的商品
        for(StorageOtherOutDetail storageOtherOutDetail : list){
            String msg = "";
            GoodsInfo goodsInfo = getAllGoodsInfoByID(storageOtherOutDetail.getGoodsid());
            if(null==goodsInfo){
                msg += "商品:"+storageOtherOutDetail.getGoodsid()+",不存在";
                disUsable.put(storageOtherOutDetail.getGoodsid()+storageOtherOutDetail.getStorageid(),msg);
                continue;
            }
            StorageSummaryBatch storageSummaryBatch = null;
            //批次商品根据生产日期获取批次号
            if("1".equals(goodsInfo.getIsbatch())){
                if(StringUtils.isEmpty(storageOtherOutDetail.getBatchno())){
                    if(StringUtils.isEmpty(storageOtherOutDetail.getProduceddate())){
                        msg += "批次商品:"+storageOtherOutDetail.getGoodsid()+"没有生产日期，无法导入:";
                        disUsable.put(storageOtherOutDetail.getGoodsid()+storageOtherOutDetail.getStorageid(),msg);
                        continue;
                    }else{
                        storageSummaryBatch =
                                addOrGetStorageSummaryBatchByStorageidAndProduceddate(storageOtherOut.getStorageid(), storageOtherOutDetail.getGoodsid(),storageOtherOutDetail.getProduceddate());
                        storageOtherOutDetail.setSummarybatchid(storageSummaryBatch.getId());
                        storageOtherOutDetail.setDeadline(storageSummaryBatch.getDeadline());
                        storageOtherOutDetail.setProduceddate(storageSummaryBatch.getProduceddate());
                    }
                }else{
                    storageSummaryBatch =
                            getStorageSummaryBatchByStorageidAndBatchno(storageOtherOut.getStorageid(), storageOtherOutDetail.getBatchno(), storageOtherOutDetail.getGoodsid());
                    if(null == storageSummaryBatch){
                        msg += "批次商品:"+storageOtherOutDetail.getGoodsid()+"无法导入";
                        disUsable.put(storageOtherOutDetail.getGoodsid()+storageOtherOutDetail.getStorageid(),msg);
                        continue;
                    }
                    storageOtherOutDetail.setSummarybatchid(storageSummaryBatch.getId());
                    storageOtherOutDetail.setDeadline(storageSummaryBatch.getDeadline());
                    storageOtherOutDetail.setProduceddate(storageSummaryBatch.getProduceddate());
                }
            }
            //非批次商品获取库存
            if(null == storageSummaryBatch){
                storageSummaryBatch = getStorageSummaryBatchByStorageidAndGoodsid(storageOtherOut.getStorageid(), storageOtherOutDetail.getGoodsid());
                //非批次仓库不存在该非批次商品
                if(null == storageSummaryBatch){
                    msg = ".仓库中不存在商品："+storageOtherOutDetail.getGoodsid();
                    disUsable.put(storageOtherOutDetail.getGoodsid()+storageOtherOutDetail.getStorageid(),msg);
                    continue;
                }else{
                    storageOtherOutDetail.setSummarybatchid(storageSummaryBatch.getId());
                }
            }
            //对批次管理的商品进行批次号添加
            if("1".equals(goodsInfo.getIsbatch()) &&  StringUtils.isEmpty(storageOtherOutDetail.getBatchno())){
                if(StringUtils.isNotEmpty(storageSummaryBatch.getBatchno())){
                    storageOtherOutDetail.setBatchno(storageSummaryBatch.getBatchno());
                }else {
                    storageOtherOutDetail.setBatchno(goodsInfo.getId());
                }
            }
            //商品可用量不足判断
            if(storageSummaryBatch.getUsablenum().compareTo(storageOtherOutDetail.getUnitnum())==-1){
                if("1".equals(goodsInfo.getIsbatch())){
                    msg = "商品编码:"+goodsInfo.getId()+",在批次:"+storageSummaryBatch.getBatchno()+"中,可用量不足";
                }else{
                    msg = "商品编码:"+goodsInfo.getId()+",在该仓库中,可用量不足";
                }
                disUsable.put(storageOtherOutDetail.getGoodsid()+storageOtherOutDetail.getStorageid(),msg);
            }
            addList.add(storageOtherOutDetail);
        }
        //移除可用量不足的商品
        Iterator<StorageOtherOutDetail> outIter =  list.iterator();
        while(outIter.hasNext()){
            StorageOtherOutDetail storageOtherOutDetail = outIter.next();
            String key = storageOtherOutDetail.getGoodsid()+storageOtherOutDetail.getStorageid();//根据商品编码和所在仓库编码来移除对应可用量不足商品
            if(disUsable.containsKey(key)){
                if(StringUtils.isEmpty(disUsableMsg)){
                    disUsableMsg = disUsable.get(key);
                }else{
                    disUsableMsg += disUsable.get(key);
                }
            }
        }
        map.put("disUsableMsg",disUsableMsg);
        if(list.size()> 0){
            Map returnMap = addStorageOtherOut(storageOtherOut,addList);
            map.putAll(returnMap);
        }
        return map ;
    }

	@Override
	public Map getStorageOtherOutInfo(String id) throws Exception {
		StorageOtherOut storageOtherOut = storageOtherOutMapper.getStorageOtherOutInfo(id);
		if(null!=storageOtherOut){
			Personnel personnel = getPersonnelById(storageOtherOut.getUserid());
			if(null!=personnel){
				storageOtherOut.setUsername(personnel.getName());
			}
		}
		List<StorageOtherOutDetail> list = storageOtherOutMapper.getStorageOtherOutDetailListByID(id);
		for(StorageOtherOutDetail storageOtherOutDetail : list){
			GoodsInfo goodsInfo = getGoodsInfoByID(storageOtherOutDetail.getGoodsid());
			if(goodsInfo!=null){
				goodsInfo.setItemno(getItemnoByGoodsAndStorage(storageOtherOutDetail.getGoodsid(),storageOtherOutDetail.getStorageid()));
			}
			storageOtherOutDetail.setGoodsInfo(goodsInfo);
			StorageLocation storageLocation = getStorageLocation(storageOtherOutDetail.getStoragelocationid());
			if(null!=storageLocation){
				storageOtherOutDetail.setStoragelocationname(storageLocation.getName());
			}
		}
		Map map = new HashMap();
		map.put("storageOtherOut", storageOtherOut);
		map.put("detailList", list);
		return map;
	}

	@Override
	public PageData showStorageOtherOutList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_storage_other_out", null);
		pageMap.setDataSql(dataSql);
		List<StorageOtherOut> list = storageOtherOutMapper.showStorageOtherOutList(pageMap);
		for(StorageOtherOut storageOtherOut : list ){
			StorageInfo storageInfo = getStorageInfoByID(storageOtherOut.getStorageid());
			if(null!=storageInfo){
				storageOtherOut.setStoragename(storageInfo.getName());
			}
			DepartMent departMent = getDepartmentByDeptid(storageOtherOut.getDeptid());
			if(null!=departMent){
				storageOtherOut.setDeptname(departMent.getName());
			}
			Personnel personnel = getPersonnelById(storageOtherOut.getUserid());
			if(null!=personnel){
				storageOtherOut.setUsername(personnel.getName());
			}
			SysCode sysCode=getBaseSysCodeInfo(storageOtherOut.getOuttype(),"storageOtherOutType");
			if(sysCode!=null){
				storageOtherOut.setOuttypename(sysCode.getCodename());
			}
			//查询金额
			Map map = storageOtherOutMapper.getStorageOtherOutAmountByID(storageOtherOut.getId());
			if(map!=null && map.size() > 0){
				storageOtherOut.setTaxamount((BigDecimal)map.get("taxamount"));
				storageOtherOut.setNotaxamount((BigDecimal)map.get("notaxamount"));
			}
		}
		PageData pageData = new PageData(storageOtherOutMapper.showStorageOtherOutListCount(pageMap),list,pageMap);
		return pageData;
	}

	@Override
	public Map editStorageOtherOut(StorageOtherOut storageOtherOut,
			List<StorageOtherOutDetail> list) throws Exception {
		boolean flag = true;
		String msg = "";
		Map map = new HashMap();
		StorageOtherOut oldStorageOtherOut = storageOtherOutMapper.getStorageOtherOutInfo(storageOtherOut.getId());
		if(null== oldStorageOtherOut || "3".equals(oldStorageOtherOut.getStatus()) || "4".equals(oldStorageOtherOut.getStatus())){
			map.put("flag", false);
			map.put("msg", "单据已审核，请刷新后再操作。");
			return map;
		}
		for(StorageOtherOutDetail storageOtherOutDetail : list){
			StorageSummaryBatch storageSummaryBatch = null;
			//判断商品是否存在
			GoodsInfo goodsInfo = getAllGoodsInfoByID(storageOtherOutDetail.getGoodsid());
			if(null==goodsInfo){
				msg += "商品:"+storageOtherOutDetail.getGoodsid()+",不存在";
				flag = false;
				break;
			}
			//获取要出库的商品批次 没指定批次 则获取指定仓库默认的商品批次
			if("1".equals(goodsInfo.getIsbatch()) || StringUtils.isNotEmpty(storageOtherOutDetail.getSummarybatchid())){
				storageSummaryBatch = getStorageSummaryBatchById(storageOtherOutDetail.getSummarybatchid());
			}else{
				storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(storageOtherOut.getStorageid(), storageOtherOutDetail.getGoodsid());
			}
			if(null!=storageOtherOutDetail.getId()){
				StorageOtherOutDetail oldstorageOtherOutDetail = storageOtherOutMapper.getStorageOtherOutDetail(storageOtherOutDetail.getId()+"");
				//修改后的数量大于修改前的数量 判断可用量是否足够
				if(storageOtherOutDetail.getUnitnum().compareTo(oldstorageOtherOutDetail.getUnitnum())==1){
					storageOtherOutDetail.setStorageid(storageOtherOut.getStorageid());
					//批次现存量中的可用量是否大于 修改后数量-修改前数量
					if(storageSummaryBatch.getUsablenum().compareTo(storageOtherOutDetail.getUnitnum().subtract(oldstorageOtherOutDetail.getUnitnum()))==-1){
						if("1".equals(goodsInfo.getIsbatch())){
							msg += "商品:"+goodsInfo.getName()+",在批次:"+storageSummaryBatch.getBatchno()+"中,数量不足，该批次只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
						}else{
							StorageInfo storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
							msg += "商品:"+goodsInfo.getName()+",在仓库:"+storageInfo.getName()+"中,数量不足，该仓库只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
						}
						flag = false;
					}
				}
			}else{
				if(storageSummaryBatch.getUsablenum().compareTo(storageOtherOutDetail.getUnitnum())==-1){
					if("1".equals(goodsInfo.getIsbatch())){
						msg += "商品:"+goodsInfo.getName()+",在批次:"+storageSummaryBatch.getBatchno()+"中,数量不足，该批次只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
					}else{
						StorageInfo storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
						msg += "商品:"+goodsInfo.getName()+",在仓库:"+storageInfo.getName()+"中,数量不足，该仓库只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
					}
					flag = false;
				}
			}
		}
		if(flag){
			//修改前其他出库单信息 回滚待发量
			if("2".equals(oldStorageOtherOut.getStatus())){
				List<StorageOtherOutDetail> oldStorageOtherOutDetail = storageOtherOutMapper.getStorageOtherOutDetailListByID(storageOtherOut.getId());
				for(StorageOtherOutDetail storageOtherOutDetail : oldStorageOtherOutDetail){
					StorageSummaryBatch storageSummaryBatch = null;
					GoodsInfo goodsInfo = getAllGoodsInfoByID(storageOtherOutDetail.getGoodsid());
					if("1".equals(goodsInfo.getIsbatch()) || StringUtils.isNotEmpty(storageOtherOutDetail.getSummarybatchid())){
						storageSummaryBatch = getStorageSummaryBatchById(storageOtherOutDetail.getSummarybatchid());
					}else{
						storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(storageOtherOut.getStorageid(), storageOtherOutDetail.getGoodsid());
					}
					//回滚修改前的待发量
					rollBackStorageSummaryWaitnum(storageSummaryBatch.getId(), storageOtherOutDetail.getUnitnum());
				}
			}
			//删除明细
			storageOtherOutMapper.deleteStorageOtherOutDetailListByBillid(storageOtherOut.getId());
			
			
			for(StorageOtherOutDetail storageOtherOutDetail : list){
				storageOtherOutDetail.setBillid(storageOtherOut.getId());
				GoodsInfo goodsInfo = getAllGoodsInfoByID(storageOtherOutDetail.getGoodsid());
				if(null!=goodsInfo){
					storageOtherOutDetail.setBrandid(goodsInfo.getBrand());
					storageOtherOutDetail.setCostprice(storageOtherOutDetail.getTaxprice());
					//实际成本价 不包括核算成本价
					storageOtherOutDetail.setRealcostprice(storageOtherOutDetail.getTaxprice());
                    storageOtherOutDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
					//计算辅单位数量
					Map auxmap = countGoodsInfoNumber(storageOtherOutDetail.getGoodsid(), storageOtherOutDetail.getAuxunitid(), storageOtherOutDetail.getUnitnum());
					if(auxmap.containsKey("auxnum")){
						storageOtherOutDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
					}
				}
				storageOtherOutDetail.setStorageid(storageOtherOut.getStorageid());
				//根据商品是否批次管理 获取对应的商品批次
				StorageSummaryBatch storageSummaryBatch = null;
				if("1".equals(goodsInfo.getIsbatch()) || StringUtils.isNotEmpty(storageOtherOutDetail.getSummarybatchid())){
					storageSummaryBatch = getStorageSummaryBatchById(storageOtherOutDetail.getSummarybatchid());
				}else{
					storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(storageOtherOut.getStorageid(), storageOtherOutDetail.getGoodsid());
				}
				
				if(null!=storageSummaryBatch){
					//保存状态时，更新待发量
					if("2".equals(storageOtherOut.getStatus())){
						//更新待发量
						updateStorageSummaryWaitnum(storageSummaryBatch.getId(), storageOtherOutDetail.getUnitnum());
					}
				}
				storageOtherOutDetail.setSummarybatchid(storageSummaryBatch.getId());
				storageOtherOutMapper.addStorageOtherOutDetail(storageOtherOutDetail);
			}
			SysUser sysUser = getSysUser();
			storageOtherOut.setModifyuserid(sysUser.getUserid());
			storageOtherOut.setModifyusername(sysUser.getName());
			int i = storageOtherOutMapper.editStorageOtherOut(storageOtherOut);
			flag = i>0;
		}
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}

	@Override
	public boolean deleteStorageOtherOut(String id) throws Exception {
		StorageOtherOut storageOtherOut = storageOtherOutMapper.getStorageOtherOutInfo(id);
		boolean flag = false;
		if(null==storageOtherOut){
			return true;
		}else{
			//只有暂存和保存状态才能删除
			if("1".equals(storageOtherOut.getStatus()) || "2".equals(storageOtherOut.getStatus())){
				//当状态为保存时
				if("2".equals(storageOtherOut.getStatus())){
					List<StorageOtherOutDetail> detailList = storageOtherOutMapper.getStorageOtherOutDetailListByID(id);
					//删除明细 回滚待发量
					for(StorageOtherOutDetail storageOtherOutDetail : detailList){
						StorageSummaryBatch storageSummaryBatch = null;
						GoodsInfo goodsInfo = getAllGoodsInfoByID(storageOtherOutDetail.getGoodsid());
						//商品是否批次管理 获取指定商品批次 否则获取仓库默认批次
						if("1".equals(goodsInfo.getIsbatch()) || StringUtils.isNotEmpty(storageOtherOutDetail.getSummarybatchid())){
							storageSummaryBatch = getStorageSummaryBatchById(storageOtherOutDetail.getSummarybatchid());
						}else{
							storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(storageOtherOut.getStorageid(), storageOtherOutDetail.getGoodsid());
						}
						if(null!=storageSummaryBatch){
							//回滚待发量
							rollBackStorageSummaryWaitnum(storageSummaryBatch.getId(), storageOtherOutDetail.getUnitnum());
						}
					}
				}
				int i = storageOtherOutMapper.deleteStorageOtherOut(id);
				storageOtherOutMapper.deleteStorageOtherOutDetailListByBillid(id);
				flag = i>0;
			}
		}
		return flag;
	}

	@Override
	public boolean auditStorageOtherOut(String id) throws Exception {
		StorageOtherOut storageOtherOut = storageOtherOutMapper.getStorageOtherOutInfo(id);
		boolean flag = false;
		if("2".equals(storageOtherOut.getStatus()) || "6".equals(storageOtherOut.getStatus())){
			List<StorageOtherOutDetail> list = storageOtherOutMapper.getStorageOtherOutDetailListByID(id);
			//更新仓库数量
			for(StorageOtherOutDetail storageOtherOutDetail : list){
				StorageSummaryBatch storageSummaryBatch = null;
				GoodsInfo goodsInfo = getAllGoodsInfoByID(storageOtherOutDetail.getGoodsid());
				if("1".equals(goodsInfo.getIsbatch()) || StringUtils.isNotEmpty(storageOtherOutDetail.getSummarybatchid())){
					storageSummaryBatch = getStorageSummaryBatchById(storageOtherOutDetail.getSummarybatchid());
				}else{
					storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(storageOtherOut.getStorageid(), storageOtherOutDetail.getGoodsid());
				}
				if(null!=storageSummaryBatch){
					//更新现存量信息
					sendStorageSummaryWaitnum(storageSummaryBatch.getId(), storageOtherOutDetail.getUnitnum(),"storageOtherOut",id);
					if(storageOtherOutDetail.getUnitnum().compareTo(BigDecimal.ZERO)==0){
						// 更新商品成本价
						updateGoodsPriceBySubtract(storageOtherOut.getId(),storageOtherOutDetail.getId()+"",storageOtherOut.getStorageid(),
								storageOtherOutDetail.getGoodsid(),
								storageOtherOutDetail.getUnitnum(),
								storageOtherOutDetail.getTaxprice(), true,storageOtherOutDetail.getTaxamount());
					}else{
						// 更新商品成本价
						updateGoodsPriceBySubtract(storageOtherOut.getId(),storageOtherOutDetail.getId()+"",storageOtherOut.getStorageid(),
								storageOtherOutDetail.getGoodsid(),
								storageOtherOutDetail.getUnitnum(),
								storageOtherOutDetail.getTaxprice(), true);
					}
				}
			}
			SysUser sysUser = getSysUser();
			String billdate=getAuditBusinessdate(storageOtherOut.getBusinessdate());
			int i = storageOtherOutMapper.auditStorageOtherOut(id, sysUser.getUserid(), sysUser.getName(),billdate);
			flag = i>0;
		}
		return flag;
	}
	@Override
	public boolean oppauditStorageOtherOut(String id) throws Exception {
		StorageOtherOut storageOtherOut = storageOtherOutMapper.getStorageOtherOutInfo(id);
		boolean flag = false;
		if("4".equals(storageOtherOut.getStatus()) || "3".equals(storageOtherOut.getStatus())){
			List<StorageOtherOutDetail> list = storageOtherOutMapper.getStorageOtherOutDetailListByID(id);
			StorageInfo storageInfo = getStorageInfoByID(storageOtherOut.getStorageid());
			//更新仓库数量
			for(StorageOtherOutDetail storageOtherOutDetail : list){
				StorageSummaryBatch storageSummaryBatch = null;
				GoodsInfo goodsInfo = getAllGoodsInfoByID(storageOtherOutDetail.getGoodsid());
				//仓库商品是批次管理
				if( ("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch()) )
						|| StringUtils.isNotEmpty(storageOtherOutDetail.getSummarybatchid())){
					storageSummaryBatch = getStorageSummaryBatchById(storageOtherOutDetail.getSummarybatchid());
				}else{
					storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(storageOtherOut.getStorageid(), storageOtherOutDetail.getGoodsid());
				}
				if(null!=storageSummaryBatch){
					//回滚现存量信息
					rollBackSendStorageSummaryWaitnum(storageSummaryBatch.getId(), storageOtherOutDetail.getUnitnum(), "storageOtherOut", id);
					// 更新商品成本价
					updateGoodsPriceByAdd(storageOtherOut.getStorageid(),
							storageOtherOutDetail.getGoodsid(),
							storageOtherOutDetail.getUnitnum(),
							storageOtherOutDetail.getTaxprice(), true,false,true,storageOtherOut.getId(),storageOtherOutDetail.getId()+"");
				}
			}
			SysUser sysUser = getSysUser();
			int i = storageOtherOutMapper.oppauditStorageOtherOut(id, sysUser.getUserid(), sysUser.getName());
			flag = i>0;
		}
		return flag;
	}
	@Override
	public Map submitStorageOtherOutProcess(String id) throws Exception {
		Map map = new HashMap();
		return map;
	}

	@Override
	public StorageOtherOutDetail getStorageOtherOutDetailInfo(String id)
			throws Exception {
		StorageOtherOutDetail storageOtherOutDetail = storageOtherOutMapper.getStorageOtherOutDetail(id);
		return storageOtherOutDetail;
	}
	@Override
	public List showStorageOtherOutListBy(Map map) throws Exception{
		//String datasql = getDataAccessRule("t_storage_other_out",null);
		//map.put("dataSql", datasql);
		boolean showdetail=false;
		if(null!=map.get("showdetail") && StringUtils.isNotEmpty(map.get("showdetail").toString()) &&"1".equals(map.get("showdetail").toString()) ){
			showdetail=true;
		}
		List<StorageOtherOut> list = storageOtherOutMapper.showStorageOtherOutListBy(map);
		for(StorageOtherOut storageOtherOut : list ){
			StorageInfo storageInfo = getStorageInfoByID(storageOtherOut.getStorageid());
			if(null!=storageInfo){
				storageOtherOut.setStoragename(storageInfo.getName());
			}
			DepartMent departMent = getDepartmentByDeptid(storageOtherOut.getDeptid());
			if(null!=departMent){
				storageOtherOut.setDeptname(departMent.getName());
			}
			Personnel personnel = getPersonnelById(storageOtherOut.getUserid());
			if(null!=personnel){
				storageOtherOut.setUsername(personnel.getName());
			}
			StorageInout storageInout = getBaseFilesStorageMapper().showStorageInoutInfo(storageOtherOut.getOuttype());
			if(null!=storageInout){
				storageOtherOut.setOuttypename(storageInout.getName());
			}
			
			if(showdetail){
				List<StorageOtherOutDetail> detailList = storageOtherOutMapper.getStorageOtherOutDetailListByID(storageOtherOut.getId());
				for(StorageOtherOutDetail storageOtherOutDetail : detailList){
					GoodsInfo goodsInfo = getGoodsInfoByID(storageOtherOutDetail.getGoodsid());
					storageOtherOutDetail.setGoodsInfo(goodsInfo);
					StorageLocation storageLocation = getStorageLocation(storageOtherOutDetail.getStoragelocationid());
					if(null!=storageLocation){
						storageOtherOutDetail.setStoragelocationname(storageLocation.getName());
					}
				}
				storageOtherOut.setOtherOutDetailList(detailList);
			}
		}
		return list;
	}
	@Override
	public boolean updateOrderPrinttimes(StorageOtherOut storageOtherOut) throws Exception{
		return storageOtherOutMapper.updateOrderPrinttimes(storageOtherOut)>0;
	}
	@Override
	public StorageOtherOut showPureStorageOtherOut(String id) throws Exception{
		return storageOtherOutMapper.getStorageOtherOutInfo(id);
	}

	/**
	 *获取ID
	 * @param sourceid
	 * @return
	 * @throws Exception
	 */
	public String getStorageOtherOutIdBySourceId(String sourceid) throws Exception {
		return storageOtherOutMapper.getStorageOtherOutIdBySourceId(sourceid);
	}

	/**
	 * 获取其它出库单要生成凭证的数据
	 * @param list
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Dec 20, 2017
	 */
	@Override
	public List getStorageOtherOutSumData(List list){
		List datalist=storageOtherOutMapper.getStorageOtherOutSumData(list);
		return datalist;
	}

	/**
	 * 获取其它出库单的金额
	 * @param id
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Dec 26, 2017
	 */
	public Map getStorageOtherSumById(String id){
		Map map=storageOtherOutMapper.getStorageOtherSumById(id);
		return map;
	}
}

