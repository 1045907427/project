/**
 * @(#)AdjustmentsServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 24, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.dao.AdjustmentsMapper;
import com.hd.agent.storage.dao.CheckListMapper;
import com.hd.agent.storage.model.*;
import com.hd.agent.storage.service.IAdjustmentsService;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 库存调账单service实现类
 * @author chenwei
 */
public class AdjustmentsServiceImpl extends BaseStorageServiceImpl implements
		IAdjustmentsService {
	/**
	 * 调账单dao
	 */
	private AdjustmentsMapper adjustmentsMapper;
	/**
	 * 盘点单dao
	 */
	private CheckListMapper checkListMapper;
	
	public AdjustmentsMapper getAdjustmentsMapper() {
		return adjustmentsMapper;
	}

	public void setAdjustmentsMapper(AdjustmentsMapper adjustmentsMapper) {
		this.adjustmentsMapper = adjustmentsMapper;
	}
	
	public CheckListMapper getCheckListMapper() {
		return checkListMapper;
	}

	public void setCheckListMapper(CheckListMapper checkListMapper) {
		this.checkListMapper = checkListMapper;
	}

	@Override
	public PageData showAdjustmentsList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_storage_adjustments", null);
		pageMap.setDataSql(dataSql);
		List<Adjustments> list = adjustmentsMapper.showAdjustmentsList(pageMap);
		for(Adjustments adjustments : list){
			StorageInfo storageInfo = getStorageInfoByID(adjustments.getStorageid());
			if(null!=storageInfo){
				adjustments.setStoragename(storageInfo.getName());
			}
		}
		PageData pageData = new PageData(adjustmentsMapper.showAdjustmentsCount(pageMap),list,pageMap);
		return pageData;
	}

	@Override
	public Map getAdjustmentsInfo(String id) throws Exception {
		Adjustments adjustments = adjustmentsMapper.getAdjustmentsInfo(id);
		List<AdjustmentsDetail> list =adjustmentsMapper.getAdjustmentsDetailList(id, adjustments.getStorageid());
		for(AdjustmentsDetail adjustmentsDetail : list){
			if("2".equals(adjustments.getStatus())){
				if(StringUtils.isNotEmpty(adjustmentsDetail.getSummarybatchid())){
					StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(adjustmentsDetail.getSummarybatchid());
					if(null!=storageSummaryBatch){
						adjustmentsDetail.setExistingnum(storageSummaryBatch.getExistingnum());
						adjustmentsDetail.setUsablenum(storageSummaryBatch.getUsablenum());
					}
				}else{
					StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(adjustments.getStorageid(), adjustmentsDetail.getGoodsid());
					if(null!=storageSummary){
						adjustmentsDetail.setExistingnum(storageSummary.getExistingnum());
						adjustmentsDetail.setUsablenum(storageSummary.getUsablenum());
					}
				}
			}
			GoodsInfo goodsInfo=getGoodsInfoByID(adjustmentsDetail.getGoodsid());
			if(goodsInfo!=null){
				goodsInfo.setItemno(getItemnoByGoodsAndStorage(adjustmentsDetail.getGoodsid(),adjustmentsDetail.getStorageid()));
			}
			adjustmentsDetail.setGoodsInfo(goodsInfo);
			StorageInfo storageInfo = getStorageInfoByID(adjustmentsDetail.getStorageid());
			if(null!=storageInfo){
				adjustmentsDetail.setStoragename(storageInfo.getName());
			}
			StorageLocation storageLocation = getStorageLocationByID(adjustmentsDetail.getStoragelocationid());
			if(null!=storageLocation){
				adjustmentsDetail.setStoragelocationname(storageLocation.getName());
			}
		}
		Map map = new HashMap();
		map.put("adjustments", adjustments);
		map.put("adjustmentsDetail", list);
		return map;
	}

	@Override
	public boolean addAdjustments(Adjustments adjustments,List<AdjustmentsDetail> list) throws Exception{
		SysUser sysUser = getSysUser();
		adjustments.setAdddeptid(sysUser.getDepartmentid());
		adjustments.setAdddeptname(sysUser.getDepartmentname());
		adjustments.setAdduserid(sysUser.getUserid());
		adjustments.setAddusername(sysUser.getName());
		int i = adjustmentsMapper.addAdjustments(adjustments);
		int j = 0;
		for(AdjustmentsDetail adjustmentsDetail : list){
			//计算辅单位数量
			Map auxmap = countGoodsInfoNumber(adjustmentsDetail.getGoodsid(), adjustmentsDetail.getAuxunitid(), adjustmentsDetail.getAdjustnum());
			if(auxmap.containsKey("auxnum")){
				adjustmentsDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
			}
			
			adjustmentsDetail.setStorageid(adjustments.getStorageid());
			adjustmentsDetail.setId(null);
			adjustmentsDetail.setAdjustmentsid(adjustments.getId());
			adjustmentsDetail.setSeq(j);
			j++;
			adjustmentsMapper.addAdjustmentsDetail(adjustmentsDetail);
		}
		return i>0;
	}

	@Override
	public boolean deleteAdjustments(String id) throws Exception {
		Adjustments adjustments = adjustmentsMapper.getAdjustmentsInfo(id);
		if("1".equals(adjustments.getStatus()) || "2".equals(adjustments.getStatus())){
			int i = adjustmentsMapper.deleteAdjustments(id);
			adjustmentsMapper.deleteAdjustmentsDetail(id);
			return i>0;
		}else{
			return false;
		}
		
	}

	@Override
	public boolean editAdjustments(Adjustments adjustments,
			List<AdjustmentsDetail> list) throws Exception {
		Adjustments oldAdjustments = adjustmentsMapper.getAdjustmentsInfo(adjustments.getId());
		if(null==oldAdjustments || "3".equals(oldAdjustments.getStatus()) || "4".equals(oldAdjustments.getStatus())){
			return false;
		}
			SysUser sysUser = getSysUser();
			adjustments.setModifyuserid(sysUser.getUserid());
			adjustments.setModifyusername(sysUser.getName());
			int i = adjustmentsMapper.editAdjustments(adjustments);
			adjustmentsMapper.deleteAdjustmentsDetail(adjustments.getId());
			for(AdjustmentsDetail adjustmentsDetail : list){
				//计算辅单位数量
				Map auxmap = countGoodsInfoNumber(adjustmentsDetail.getGoodsid(), adjustmentsDetail.getAuxunitid(), adjustmentsDetail.getAdjustnum());
				if(auxmap.containsKey("auxnum")){
					adjustmentsDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
				}
			
				adjustmentsDetail.setAdjustmentsid(adjustments.getId());
				adjustmentsMapper.addAdjustmentsDetail(adjustmentsDetail);
			}
			return i>0;
	}

	@Override
	public Map auditAdjustments(String id) throws Exception {
		Map map = new HashMap();
		boolean flag = false;
		String msg = "";
		Adjustments adjustments = adjustmentsMapper.getAdjustmentsInfo(id);
		List<AdjustmentsDetail> list =adjustmentsMapper.getAdjustmentsDetailList(id, null);
		boolean auditflag = true;
		List<AdjustmentsDetail> copyList = (List<AdjustmentsDetail>) CommonUtils.deepCopy(list);
		StorageInfo storageInfo = getStorageInfoByID(adjustments.getStorageid());
		//报溢单不需要判断可用量是否充足
		if("2".equals(adjustments.getBilltype())){
			String adjustmentCheckUnitumType=getSysParamValue("AdjustmentCheckUnitumType");
			//根据参数修改提示是现存量还是可用量
			String checkmsg="现存量";
			if("1".equals(adjustmentCheckUnitumType)){
				checkmsg="可用量";
			}
			for(AdjustmentsDetail adjustmentsDetail : copyList){
				GoodsInfo goodsInfo = getGoodsInfoByID(adjustmentsDetail.getGoodsid());
				if(null ==goodsInfo){
					auditflag = false;
					msg +="商品："+adjustmentsDetail.getGoodsid()+"不存在;";
				}
				StorageSummaryBatch storageSummaryBatch = null;
				if("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
                    if(StringUtils.isNotEmpty(adjustmentsDetail.getSummarybatchid())){
                        storageSummaryBatch = getStorageSummaryBatchByID(adjustmentsDetail.getSummarybatchid());
                    }else{
                        storageSummaryBatch = getStorageSummaryBatchByStorageidAndProduceddate(adjustments.getStorageid(),adjustmentsDetail.getGoodsid(),adjustmentsDetail.getProduceddate());
                    }
				}else{
                    if(StringUtils.isNotEmpty(adjustmentsDetail.getSummarybatchid())){
                        storageSummaryBatch = getStorageSummaryBatchByID(adjustmentsDetail.getSummarybatchid());
                    }else{
                        storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(adjustments.getStorageid(), adjustmentsDetail.getGoodsid());
                    }
				}
				//默认现存量
				BigDecimal storageSummaryUnitnum=storageSummaryBatch.getExistingnum();
				if("1".equals(adjustmentCheckUnitumType)){
					storageSummaryUnitnum=storageSummaryBatch.getUsablenum();
				}
				//库存批次现存量
				if(null!=storageSummaryBatch){
					BigDecimal newusenum = storageSummaryUnitnum.subtract(adjustmentsDetail.getAdjustnum());
					if(newusenum.compareTo(BigDecimal.ZERO)==-1){
						auditflag = false;
						if(null!=goodsInfo){
							msg +="商品："+adjustmentsDetail.getGoodsid()+goodsInfo.getName()+"在该仓库"+checkmsg+"不足;";
						}
					}
				}else{
					auditflag = false;
					msg +="商品："+adjustmentsDetail.getGoodsid()+goodsInfo.getName()+"在该仓库数量不足或者指定批次商品数量不足;";
				}
			}
		}
		
		if(auditflag){
			for(AdjustmentsDetail adjustmentsDetail : list){
				//报损调账单 调账减库存
				if("2".equals(adjustments.getBilltype())){
					adjustmentsDetail.setAdjustnum(adjustmentsDetail.getAdjustnum().negate());
				}
				//库存总现存量
				StorageSummary storageSummary = getStorageSummaryMapper().getStorageSummaryInfoByGoodsidAndStorageid(adjustmentsDetail.getGoodsid(), adjustments.getStorageid());
                if(null==storageSummary){
                    boolean addflag = initStorageSummary(adjustments.getStorageid(),adjustmentsDetail.getGoodsid());
                    if(!addflag){
                        throw new Exception("仓库："+adjustments.getStorageid()+",商品："+adjustmentsDetail.getGoodsid()+"库存不存在，初始化失败。");
                    }
                    storageSummary = getStorageSummaryMapper().getStorageSummaryInfoByGoodsidAndStorageid(adjustmentsDetail.getGoodsid(), adjustments.getStorageid());
                }
				if(null!=storageSummary){
					GoodsInfo goodsInfo = getGoodsInfoByID(adjustmentsDetail.getGoodsid());
					StorageSummaryBatch storageSummaryBatch = null;
					//仓库+商品批次管理
					if("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
						storageSummaryBatch = getStorageSummaryBatchByID(adjustmentsDetail.getSummarybatchid());
                        if(null==storageSummaryBatch){
                            storageSummaryBatch = addOrGetStorageSummaryBatchByStorageidAndProduceddate(adjustments.getStorageid(),adjustmentsDetail.getGoodsid(),adjustmentsDetail.getProduceddate());
                        }
					}else{
                        if(StringUtils.isNotEmpty(adjustmentsDetail.getSummarybatchid())){
                            storageSummaryBatch = getStorageSummaryBatchByID(adjustmentsDetail.getSummarybatchid());
                        }else{
                            storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(adjustments.getStorageid(), adjustmentsDetail.getGoodsid());
                        }
					}
					//业务数量>0时
					if(adjustmentsDetail.getAdjustnum().compareTo(new BigDecimal(0))==1){
                        addStorageSummaryNum(storageSummaryBatch,adjustmentsDetail.getAdjustnum(), "adjustments", id,"报溢调账单");
					}else{
                        rollbackStorageSummaryNum(storageSummaryBatch, adjustmentsDetail.getAdjustnum().negate(), "adjustments", id, "报损调账单");
					}
					
					adjustmentsMapper.updateAdjustmentsDetailSummarybatchid(adjustmentsDetail.getId(), storageSummaryBatch.getId(),getGoodsCostprice(adjustments.getStorageid(),goodsInfo));
				}
			}
			SysUser sysUser = getSysUser();
			//调账单审核通过 并且关闭
			//是否自动验收退货通知单
			String billdate=getAuditBusinessdate(adjustments.getBusinessdate());
			int i = adjustmentsMapper.auditAdjustments(id, sysUser.getUserid(), sysUser.getName(),billdate);
			if(i>0){
				flag = true;
			}
		}
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}

    /**
     * 反审调账单
     *
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date May 27, 2013
     */
    @Override
    public Map oppauditAdjustments(String id) throws Exception {
        Map map = new HashMap();
        boolean flag = false;
        String msg = "";
        Adjustments adjustments = adjustmentsMapper.getAdjustmentsInfo(id);
        if("4".equals(adjustments.getStatus())){
            List<AdjustmentsDetail> list =adjustmentsMapper.getAdjustmentsDetailList(id, null);
            boolean auditflag = true;
            List<AdjustmentsDetail> copyList = (List<AdjustmentsDetail>) CommonUtils.deepCopy(list);
            StorageInfo storageInfo = getStorageInfoByID(adjustments.getStorageid());
            //反审 报损单不需要判断可用量是否充足
            if("1".equals(adjustments.getBilltype())){
                for(AdjustmentsDetail adjustmentsDetail : copyList){
                    GoodsInfo goodsInfo = getGoodsInfoByID(adjustmentsDetail.getGoodsid());
                    if(null ==goodsInfo){
                        auditflag = false;
                        msg +="商品："+adjustmentsDetail.getGoodsid()+"不存在;";
                    }
                    StorageSummaryBatch storageSummaryBatch = null;
                    if("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
                        if(StringUtils.isNotEmpty(adjustmentsDetail.getSummarybatchid())){
                            storageSummaryBatch = getStorageSummaryBatchByID(adjustmentsDetail.getSummarybatchid());
                        }else{
                            storageSummaryBatch = getStorageSummaryBatchByStorageidAndProduceddate(adjustments.getStorageid(),adjustmentsDetail.getGoodsid(),adjustmentsDetail.getProduceddate());
                        }
                    }else{
                        if(StringUtils.isNotEmpty(adjustmentsDetail.getSummarybatchid())){
                            storageSummaryBatch = getStorageSummaryBatchByID(adjustmentsDetail.getSummarybatchid());
                        }else{
                            storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(adjustments.getStorageid(), adjustmentsDetail.getGoodsid());
                        }

                    }
                    //库存批次现存量
                    if(null!=storageSummaryBatch){
                        BigDecimal newusenum = storageSummaryBatch.getExistingnum().subtract(adjustmentsDetail.getAdjustnum());
                        if(newusenum.compareTo(BigDecimal.ZERO)==-1){
                            auditflag = false;
                            if(null!=goodsInfo){
                                msg +="商品："+adjustmentsDetail.getGoodsid()+goodsInfo.getName()+"在该仓库现存量不足;";
                            }
                        }
                    }else{
                        auditflag = false;
                        msg +="商品："+adjustmentsDetail.getGoodsid()+goodsInfo.getName()+"在该仓库数量不足或者指定批次商品数量不足;";
                    }
                }
            }

            if(auditflag){
                for(AdjustmentsDetail adjustmentsDetail : list){
                    //报损调账单 调账减库存
                    if("1".equals(adjustments.getBilltype())){
                        adjustmentsDetail.setAdjustnum(adjustmentsDetail.getAdjustnum().negate());
                    }
                    //库存总现存量
                    StorageSummary storageSummary = getStorageSummaryMapper().getStorageSummaryInfoByGoodsidAndStorageid(adjustmentsDetail.getGoodsid(), adjustments.getStorageid());
                    if(null==storageSummary){
                        boolean addflag = initStorageSummary(adjustments.getStorageid(),adjustmentsDetail.getGoodsid());
                        if(!addflag){
                            throw new Exception("仓库："+adjustments.getStorageid()+",商品："+adjustmentsDetail.getGoodsid()+"库存不存在，初始化失败。");
                        }
                        storageSummary = getStorageSummaryMapper().getStorageSummaryInfoByGoodsidAndStorageid(adjustmentsDetail.getGoodsid(), adjustments.getStorageid());
                    }
                    if(null!=storageSummary){
                        GoodsInfo goodsInfo = getGoodsInfoByID(adjustmentsDetail.getGoodsid());
                        StorageSummaryBatch storageSummaryBatch = null;
                        //仓库+商品批次管理
                        if("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
                            storageSummaryBatch = getStorageSummaryBatchByID(adjustmentsDetail.getSummarybatchid());
                            if(null==storageSummaryBatch){
                                storageSummaryBatch = addOrGetStorageSummaryBatchByStorageidAndProduceddate(adjustments.getStorageid(),adjustmentsDetail.getGoodsid(),adjustmentsDetail.getProduceddate());
                            }
                        }else{
                            if(StringUtils.isNotEmpty(adjustmentsDetail.getSummarybatchid())){
                                storageSummaryBatch = getStorageSummaryBatchByID(adjustmentsDetail.getSummarybatchid());
                            }else{
                                storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(adjustments.getStorageid(), adjustmentsDetail.getGoodsid());
                            }
                        }
                        boolean rollFlag = false;
                        //业务数量>0时
                        if(adjustmentsDetail.getAdjustnum().compareTo(new BigDecimal(0))==1){
                            addStorageSummaryNum(storageSummaryBatch,adjustmentsDetail.getAdjustnum(), "adjustments", id,"反审报损调账单");
                            rollFlag = true;
                        }else{
                            rollFlag = rollbackStorageSummaryNum(storageSummaryBatch, adjustmentsDetail.getAdjustnum().negate(), "adjustments", id, "反审报溢调账单");
                        }
                        if(rollFlag){
                            //反审时，根据调账单的价格 重新计算商品成本价
                            // 更新商品成本价
                            updateGoodsPriceByAdd(adjustments.getStorageid(),adjustmentsDetail.getGoodsid(),adjustmentsDetail.getAdjustnum(),adjustmentsDetail.getPrice(), true, false,false,adjustments.getId(),adjustmentsDetail.getId()+"");
                        }
                        adjustmentsMapper.updateAdjustmentsDetailSummarybatchid(adjustmentsDetail.getId(), storageSummaryBatch.getId(),getGoodsCostprice(adjustments.getStorageid(),goodsInfo));
                    }
                }
                SysUser sysUser = getSysUser();
                //调账单审核通过 并且关闭
                int i = adjustmentsMapper.oppauditAdjustments(id);
                if(i>0){
                    flag = true;
                }
            }
        }

        map.put("flag", flag);
        map.put("msg", msg);
        return map;
    }

    @Override
	public String addAdjustmentsByRefer(String checklistid) throws Exception {
		CheckList checkList = checkListMapper.getCheckListInfo(checklistid);
		boolean isAdd = false;
		//判断盘点单是否生成调账单
		if(null!=checkList){
			List<CheckList> list = checkListMapper.getCheckListBySourceid(checkList.getSourceid());
			for(CheckList check : list){
				Adjustments adjustments = adjustmentsMapper.getAdjustmentsByCheckListid(check.getId());
				if(null!=adjustments){
					isAdd = true;
					break;
				}
			}
		}
		if(!isAdd){
			List<CheckListDetail> detailList = checkListMapper.getCheckListDetailListByCheckListid(checklistid);
			String adjustmentsid = null;
			//报溢调账单明细
			List<AdjustmentsDetail> adjustmentsDetailList = new ArrayList();
			//报损调账单明细
			List<AdjustmentsDetail> lossAdjustmentsDetailList = new ArrayList();
			for(CheckListDetail checkListDetail : detailList){
				//判断盈亏数量是否等于0
				//如果盈亏数量不等于0 表示盘点单需要生成调账单
				if(checkListDetail.getProfitlossnum().compareTo(new BigDecimal(0))!=0){
					AdjustmentsDetail adjustmentsDetail = new AdjustmentsDetail();
					adjustmentsDetail.setAdjustmentsid(adjustmentsid);
					adjustmentsDetail.setSummarybatchid(checkListDetail.getSummarybatchid());
					adjustmentsDetail.setGoodsid(checkListDetail.getGoodsid());
					adjustmentsDetail.setStorageid(checkListDetail.getStorageid());
					adjustmentsDetail.setStoragelocationid(checkListDetail.getStoragelocationid());
					adjustmentsDetail.setUnitid(checkListDetail.getUnitid());
					adjustmentsDetail.setUnitname(checkListDetail.getUnitname());
					adjustmentsDetail.setAuxunitid(checkListDetail.getAuxunitid());
					adjustmentsDetail.setAuxunitname(checkListDetail.getAuxunitname());
					//自定义字段
					adjustmentsDetail.setField01(checkListDetail.getField01());
					adjustmentsDetail.setField02(checkListDetail.getField02());
					adjustmentsDetail.setField03(checkListDetail.getField03());
					adjustmentsDetail.setField04(checkListDetail.getField04());
					adjustmentsDetail.setField05(checkListDetail.getField05());
					adjustmentsDetail.setField06(checkListDetail.getField06());
					adjustmentsDetail.setField07(checkListDetail.getField07());
					adjustmentsDetail.setField08(checkListDetail.getField08());
					//数量=盘点单明细中的盈亏数量
					adjustmentsDetail.setAdjustnum(checkListDetail.getRealnum().subtract(checkListDetail.getBooknum()));
					Map map = countGoodsInfoNumber(checkListDetail.getGoodsid(), checkListDetail.getAuxunitid(), adjustmentsDetail.getAdjustnum());
					if(null!=map){
						String auxIntegerStr = (String) map.get("auxInteger");
						String auxremainderStr = (String) map.get("auxremainder");
						String auxnumdetail = (String) map.get("auxnumdetail");
						BigDecimal auxInteger = new BigDecimal(auxIntegerStr);
						BigDecimal auxremainder = new BigDecimal(auxremainderStr);
						adjustmentsDetail.setAuxadjustnum(auxInteger);
						adjustmentsDetail.setAuxadjustremainder(auxremainder);
						adjustmentsDetail.setAuxadjustnumdetail(auxnumdetail);
						adjustmentsDetail.setTotalbox((BigDecimal) map.get("auxnum"));
					}
					
					//批次相关
					adjustmentsDetail.setBatchno(checkListDetail.getBatchno());
					adjustmentsDetail.setProduceddate(checkListDetail.getProduceddate());
					adjustmentsDetail.setDeadline(checkListDetail.getDeadline());
					GoodsInfo goodsInfo = getAllGoodsInfoByID(checkListDetail.getGoodsid());
					if(null!=goodsInfo){
						adjustmentsDetail.setPrice(getGoodsCostprice(checkList.getStorageid(),goodsInfo));
					}else{
						adjustmentsDetail.setPrice(checkListDetail.getPrice());
					}
					
					//金额=单价*盈亏数量
					adjustmentsDetail.setAmount(adjustmentsDetail.getPrice().multiply(adjustmentsDetail.getAdjustnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					adjustmentsDetail.setSeq(checkListDetail.getSeq());
					
					if(adjustmentsDetail.getAdjustnum().compareTo(BigDecimal.ZERO)==1){
						adjustmentsDetailList.add(adjustmentsDetail);
					}else{
						lossAdjustmentsDetailList.add(adjustmentsDetail);
					}
				}
			}
			boolean adjustFlag = false;
			boolean flag = false;
			//盘点单中盈亏数量不为0的明细条数大于0时，表示需要生成调账单
			if(adjustmentsDetailList.size()>0 || lossAdjustmentsDetailList.size()>0){
				boolean addflag = false;
				if(adjustmentsDetailList.size()>0){
					String billid = null;
					if (isAutoCreate("t_storage_adjustments")) {
						// 获取自动编号
						billid = getAutoCreateSysNumbderForeign(new Adjustments(), "t_storage_adjustments");
					}else{
						billid = "DZD-"+CommonUtils.getDataNumberSendsWithRand();
					}
					SysUser sysUser = getSysUser();
					//调账单基本信息
					Adjustments adjustments = new Adjustments();
					adjustments.setId(billid);
					adjustments.setBusinessdate(checkList.getBusinessdate());
					adjustments.setBilltype("1");
					adjustments.setStatus("2");
					adjustments.setSourcetype("2");
					adjustments.setSourceid(checkList.getId());
					adjustments.setStorageid(checkList.getStorageid());
					adjustments.setAdddeptid(sysUser.getDepartmentid());
					adjustments.setAdddeptname(sysUser.getDepartmentname());
					adjustments.setAdduserid(sysUser.getUserid());
					adjustments.setAddusername(sysUser.getName());
					
					int i = adjustmentsMapper.addAdjustments(adjustments);
					
					for(AdjustmentsDetail adjustmentsDetail : adjustmentsDetailList){
						adjustmentsDetail.setAdjustmentsid(billid);
						adjustmentsMapper.addAdjustmentsDetail(adjustmentsDetail);
					}
					if(i>0){
						addflag = true;
						adjustmentsid = "报溢调账单:"+billid;
					}
					
				}
				if(lossAdjustmentsDetailList.size()>0){
					String billid = null;
					if (isAutoCreate("t_storage_adjustments")) {
						// 获取自动编号
						billid = getAutoCreateSysNumbderForeign(new Adjustments(), "t_storage_adjustments");
					}else{
						billid = "DZD-"+CommonUtils.getDataNumberSendsWithRand();
					}
					SysUser sysUser = getSysUser();
					//调账单基本信息
					Adjustments adjustments = new Adjustments();
					adjustments.setId(billid);
					adjustments.setBusinessdate(checkList.getBusinessdate());
					adjustments.setBilltype("2");
					adjustments.setStatus("2");
					adjustments.setSourcetype("2");
					adjustments.setSourceid(checkList.getId());
					adjustments.setStorageid(checkList.getStorageid());
					adjustments.setAdddeptid(sysUser.getDepartmentid());
					adjustments.setAdddeptname(sysUser.getDepartmentname());
					adjustments.setAdduserid(sysUser.getUserid());
					adjustments.setAddusername(sysUser.getName());
					
					int i = adjustmentsMapper.addAdjustments(adjustments);
					
					for(AdjustmentsDetail adjustmentsDetail : lossAdjustmentsDetailList){
						adjustmentsDetail.setAdjustnum(adjustmentsDetail.getAdjustnum().negate());
						adjustmentsDetail.setAmount(adjustmentsDetail.getAmount().negate());
						Map map = countGoodsInfoNumber(adjustmentsDetail.getGoodsid(), adjustmentsDetail.getAuxunitid(), adjustmentsDetail.getAdjustnum());
						if(null!=map){
							String auxIntegerStr = (String) map.get("auxInteger");
							String auxremainderStr = (String) map.get("auxremainder");
							String auxnumdetail = (String) map.get("auxnumdetail");
							BigDecimal auxInteger = new BigDecimal(auxIntegerStr);
							BigDecimal auxremainder = new BigDecimal(auxremainderStr);
							adjustmentsDetail.setAuxadjustnum(auxInteger);
							adjustmentsDetail.setAuxadjustremainder(auxremainder);
							adjustmentsDetail.setAuxadjustnumdetail(auxnumdetail);
						}
						adjustmentsDetail.setAdjustmentsid(billid);
						adjustmentsMapper.addAdjustmentsDetail(adjustmentsDetail);
					}
					if(i>0){
						addflag = true;
						if(StringUtils.isNotEmpty(adjustmentsid)){
							adjustmentsid += ",报损调账单:"+billid;
						}else{
							adjustmentsid = "报损调账单:"+billid;
						}
					}
				}
				
				if(addflag){
					checkListMapper.updateCheckListRefer("1", checklistid);
					//关闭盘点单
					checkListMapper.closeCheckListBySourceid(checkList.getSourceid());
					return adjustmentsid;
				}else{
					return null;
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

	@Override
	public boolean submitAdjustmentsPageProcess(String id) throws Exception {
		return false;
	}

	@Override
	public List<Map<String, Object>> getAdjustmentListExport(PageMap pageMap) throws Exception {
		List<Map<String, Object>> adjustmentList = adjustmentsMapper.getAdjustmentListExport(pageMap);
		for(Map<String, Object> map :adjustmentList){
			//调账仓库
			String adjuststorageid = (String)map.get("adjuststorageid");
			String goodsid = (String)map.get("goodsid");
			//商品所属仓库
			String storageid = (String)map.get("storageid");
			String storagelocationid = (String)map.get("storagelocationid");
			
			StorageInfo adjuststorageInfo = getStorageInfoByID(adjuststorageid);
			if(null != adjuststorageInfo){
				map.put("adjuststoragename", adjuststorageInfo.getName());
			}
			GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
			if(null != goodsInfo){
				map.put("goodsname", goodsInfo.getName());
				map.put("barcode", goodsInfo.getBarcode());
				//获取箱装量
				GoodsInfo_MteringUnitInfo mUnitInfo = getDefaultGoodsAuxMeterUnitInfo(goodsid);
				if(null != mUnitInfo){
					map.put("boxnum", mUnitInfo.getRate());
				}
			}
			StorageInfo storageInfo = getStorageInfoByID(storageid);
			if(null != storageInfo){
				map.put("storagename", storageInfo.getName());
			}
			StorageLocation storageLocation = getStorageLocationByID(storagelocationid);
			if(null != storageLocation){
				map.put("storagelocationname", storageLocation.getName());
			}
		}
		return adjustmentList;
	}
	@Override
	public List showAdjustmentsListBy(Map map) throws Exception{

		boolean showdetail=false;
		if(null!=map.get("showdetail") && "1".equals(map.get("showdetail").toString()) ){
			showdetail=true;
		}
		List<Adjustments> list = adjustmentsMapper.showAdjustmentsListBy(map);
		StorageInfo storageInfo=null;
		DepartMent departMent=null;
		
		for(Adjustments item : list){
			if(StringUtils.isNotEmpty(item.getStorageid())){
				storageInfo = getStorageInfoByID(item.getStorageid());
				if(null!=storageInfo){
					item.setStoragename(storageInfo.getName());
				}
				if(null==departMent || !departMent.getStorageid().equals(item.getStorageid())){
					departMent=getBaseFilesDepartmentMapper().getDepartmentInfoByStorage(item.getStorageid());
					if(null!=departMent){
						item.setDeptid(departMent.getId());
						item.setDeptname(departMent.getName());
					}
				}
			}
			List<AdjustmentsDetail> detailList =adjustmentsMapper.getAdjustmentsDetailList(item.getId(), null);
	
			if(showdetail){
				for(AdjustmentsDetail adjustmentsDetail : detailList){
					GoodsInfo goodsInfo=getGoodsInfoByID(adjustmentsDetail.getGoodsid());
					adjustmentsDetail.setGoodsInfo(goodsInfo);
					storageInfo = getStorageInfoByID(adjustmentsDetail.getStorageid());
					if(null!=storageInfo){
						adjustmentsDetail.setStoragename(storageInfo.getName());
					}
					StorageLocation storageLocation = getStorageLocationByID(adjustmentsDetail.getStoragelocationid());
					if(null!=storageLocation){
						adjustmentsDetail.setStoragelocationname(storageLocation.getName());
					}
					if(null!=goodsInfo){
						Map amountMap= getAmountDetailByTaxWithUnitnum(adjustmentsDetail.getPrice(),goodsInfo.getDefaulttaxtype(),adjustmentsDetail.getAdjustnum() );
						if(null!=amountMap){
							BigDecimal tmpd=(BigDecimal)amountMap.get("notaxprice");
							adjustmentsDetail.setNotaxprice(tmpd);
							tmpd=(BigDecimal)amountMap.get("notaxamount");
							adjustmentsDetail.setNotaxamount(tmpd.setScale(decimalLen,BigDecimal.ROUND_HALF_UP) );
						}
					}
				}
			}
			item.setAdjustmentsDetailList(detailList);
		}
		return list;
	}
	@Override
	public boolean updateOrderPrinttimes(Adjustments adjustments) throws Exception{
		return adjustmentsMapper.updateOrderPrinttimes(adjustments)>0;
	}
	@Override
	public Adjustments showPureAdjustments(String id) throws Exception{
		return adjustmentsMapper.getAdjustmentsInfo(id);
	}
	/**
	 * 获取报溢调账单生成凭证的数据
	 * @param ids
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Jan 03, 2018
	 */
	public List getAdjustmentsSumData(List ids,String billtype){
		List list=adjustmentsMapper.getAdjustmentsSumData(ids,billtype);
		return list;
	}
}

