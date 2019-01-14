/**
 * @(#)BaseStorageServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 15, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.Brand;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.MeteringUnit;
import com.hd.agent.basefiles.model.StorageInfo;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.storage.dao.StorageSummaryMapper;
import com.hd.agent.storage.model.StorageSummary;
import com.hd.agent.storage.model.StorageSummaryBatch;
import com.hd.agent.storage.model.StorageSummaryLog;
import com.hd.agent.storage.service.IBaseStorageService;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 
 * 库存基础service类，提供库存业务公共的方法
 * 
 * @author chenwei
 */
public class BaseStorageServiceImpl extends BaseFilesServiceImpl implements
		IBaseStorageService {
	/**
	 * 库存现存量dao
	 */
	private StorageSummaryMapper storageSummaryMapper;

	public StorageSummaryMapper getStorageSummaryMapper() {
		return storageSummaryMapper;
	}

	public void setStorageSummaryMapper(StorageSummaryMapper storageSummaryMapper) {
		this.storageSummaryMapper = storageSummaryMapper;
	}

	/**
     * 初始化商品仓库0库存
     * @param storageid
     * @param goodsid
     * @return
     * @throws Exception
     */
	public boolean initStorageSummary(String storageid,String goodsid) throws Exception{
        boolean flag = false;
        GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
        StorageInfo storageInfo = getStorageInfoByID(storageid);
        if(null!=goodsInfo && null!=storageInfo){
            Brand brand = getGoodsBrandByID(goodsInfo.getBrand());

            StorageSummary storageSummary = new StorageSummary();
            storageSummary.setId(CommonUtils.getDataNumberSendsWithRand());
            storageSummary.setGoodsid(goodsid);
            storageSummary.setStorageid(storageid);
            storageSummary.setBrandid(goodsInfo.getBrand());
            //获取品牌部门
            if(null!=brand){
                storageSummary.setBranddept(brand.getDeptid());
            }
            storageSummary.setBarcode(goodsInfo.getBarcode());
            storageSummary.setIstotalcontrol(storageInfo.getIstotalcontrol());
            storageSummary.setIssendstorage(storageInfo.getIssendstorage());
            // 现存量=批次现存量
            storageSummary.setExistingnum(BigDecimal.ZERO);
            // 安全库存
            BigDecimal safenum = getStorageInventoryByGoodsidAndStorageid(storageSummary.getGoodsid(), storageSummary.getStorageid());
            storageSummary.setUsablenum(BigDecimal.ZERO);

            // 安全库存
            storageSummary.setSafenum(safenum);
            // 预计可用量=可用量+在途量+调拨待入量
            // 库存初始化时，没有在途量与调拨待入量
            storageSummary.setProjectedusablenum(storageSummary.getUsablenum());

            storageSummary.setUnitid(goodsInfo.getMainunit());
            storageSummary.setUnitname(goodsInfo.getMainunitName());
            storageSummary.setAuxunitid(goodsInfo.getAuxunitid());
            storageSummary.setAuxunitname(goodsInfo.getAuxunitname());
            // 添加库存现存量总量信息
            int i = storageSummaryMapper.addStorageSummary(storageSummary);
            if(!"1".equals(goodsInfo.getIsbatch()) || !"1".equals(storageInfo.getIsbatch())){
                // 添加批次库存现存量信息
                StorageSummaryBatch storageSummaryBatch = new StorageSummaryBatch();
                storageSummaryBatch.setId(CommonUtils.getDataNumberSendsWithRand());
                storageSummaryBatch.setSummaryid(storageSummary.getId());
                storageSummaryBatch.setGoodsid(goodsid);
                storageSummaryBatch.setStorageid(storageid);
                storageSummaryBatch.setStoragelocationid("");
                storageSummaryBatch.setBatchstate("0");
                storageSummaryBatch.setBarcode(goodsInfo.getBarcode());
                storageSummaryBatch.setBatchno("");

                storageSummaryBatch.setBrandid(goodsInfo.getBrand());
                //获取品牌部门
                if(null!=brand){
                    storageSummaryBatch.setBranddept(brand.getDeptid());
                }
                //批次现存量
                storageSummaryBatch.setExistingnum(BigDecimal.ZERO);
                //批次可用量
                storageSummaryBatch.setUsablenum(BigDecimal.ZERO);
                //批次初始量
                storageSummaryBatch.setIntinum(BigDecimal.ZERO);

                storageSummaryBatch.setPrice(goodsInfo.getNewbuyprice());
                storageSummaryBatch.setAmount(BigDecimal.ZERO);

                storageSummaryBatch.setUnitid(goodsInfo.getMainunit());
                storageSummaryBatch.setUnitname(goodsInfo.getMainunitName());
                storageSummaryBatch.setAuxunitid(goodsInfo.getAuxunitid());
                storageSummaryBatch.setAuxunitname(goodsInfo.getAuxunitname());

                storageSummaryBatch.setProduceddate("");
                storageSummaryBatch.setDeadline("");
                //入库日期
                storageSummaryBatch.setEnterdate(CommonUtils.getTodayDataStr());
                //库存汇总信息 需要在仓库现存量更新前生成
                //添加库存汇总信息
                int j = storageSummaryMapper.addStorageSummaryBatch(storageSummaryBatch);
            }
            flag = i>0;
        }
        return flag;
    }
	/**
	 * 更新批次库存现存量信息，同时更新总库存现存量信息
	 * @param storageSummaryBatch		新增的批次库存量
     * @param enterNum                    入库数量
	 * @param billmodel					单据类型
	 * @param billid					单据编号
	 * @param remark					备注
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 30, 2013
	 */
	public boolean initStorageSummaryBatch(StorageSummaryBatch storageSummaryBatch,BigDecimal enterNum,String billmodel,String billid,String remark) throws Exception {
		boolean flag = false;
		if (null == storageSummaryBatch) {
			return false;
		}
		GoodsInfo goodsInfo = getAllGoodsInfoByID(storageSummaryBatch.getGoodsid());
		if(null == goodsInfo){
			return false;
		}
		storageSummaryBatch.setBarcode(goodsInfo.getBarcode());
		storageSummaryBatch.setBrandid(goodsInfo.getBrand());
		Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
		//获取品牌部门
		if(null!=brand){
			storageSummaryBatch.setBranddept(brand.getDeptid());
		}
		StorageInfo storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
		// 判断库存现存量中是否存在该商品
		// 更新库存现存量总量信息
		StorageSummary storageSummary = storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid(storageSummaryBatch.getGoodsid(), storageSummaryBatch.getStorageid());
		String summaryid = null;
		if(null!=storageSummary){
			// 添加批次库存现存量信息
			storageSummaryBatch.setSummaryid(storageSummary.getId().toString());
			summaryid = storageSummary.getId().toString();
		}else{
			//生成仓库现存量编号
			summaryid = CommonUtils.getDataNumberSendsWithRand();
			storageSummaryBatch.setSummaryid(summaryid);
		}
		StorageSummaryBatch goodsBatch =  null;
		//只有仓库与商品 同时是批次管理时， 在该仓库中 商品进行批次管理
		if("1".equals(goodsInfo.getIsbatch()) && "1".equals(storageInfo.getIsbatch())){
			goodsBatch = getStorageSummaryBatchByStorageidAndProduceddate(storageSummaryBatch.getStorageid(), storageSummaryBatch.getGoodsid(), storageSummaryBatch.getProduceddate());
		}else{
			storageSummaryBatch.setProduceddate("");
			storageSummaryBatch.setBatchno("");
			storageSummaryBatch.setDeadline("");
			goodsBatch = storageSummaryMapper.getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(storageSummaryBatch.getStorageid(),storageSummaryBatch.getGoodsid());
		}
		if(null==goodsBatch){
			// 添加批次库存现存量信息
			storageSummaryBatch.setSummaryid(summaryid);
			storageSummaryBatch.setId(CommonUtils.getDataNumberSendsWithRand());
            storageSummaryBatch.setExistingnum(enterNum);
            storageSummaryBatch.setUsablenum(enterNum);
            storageSummaryBatch.setIntinum(enterNum);
			//库存汇总信息 需要在仓库现存量更新前生成
			//添加库存汇总信息
			addStorageSummaryReceiveLog(billmodel, billid, storageSummaryBatch, enterNum,remark);
			int i = storageSummaryMapper.addStorageSummaryBatch(storageSummaryBatch);
			flag = i > 0;
		}else{
			addStorageSummaryReceiveLog(billmodel, billid, goodsBatch, enterNum,remark);
			int i = storageSummaryMapper.updateStorageSummaryBacthEnterByID(goodsBatch.getId(), goodsBatch.getExistingnum(),enterNum,CommonUtils.getTodayDataStr(),goodsBatch.getVersion());
			flag = i > 0;
			storageSummaryBatch.setId(goodsBatch.getId());
		}
		// 仓库现存量总量不存在时，添加仓库现存量信息
		if (null == storageSummary) {
			storageSummary = new StorageSummary();
			storageSummary.setId(summaryid);
			storageSummary.setGoodsid(storageSummaryBatch.getGoodsid());
			storageSummary.setBrandid(goodsInfo.getBrand());
			//获取品牌部门
			if(null!=brand){
				storageSummary.setBranddept(brand.getDeptid());
			}
			storageSummary.setBarcode(storageSummaryBatch.getBarcode());
			storageSummary.setStorageid(storageSummaryBatch.getStorageid());
			storageSummary.setIstotalcontrol(storageInfo.getIstotalcontrol());
			storageSummary.setIssendstorage(storageInfo.getIssendstorage());
			// 现存量=批次现存量
			storageSummary.setExistingnum(enterNum);
			// 安全库存
			BigDecimal safenum = getStorageInventoryByGoodsidAndStorageid(storageSummary.getGoodsid(), storageSummary.getStorageid());
			storageSummary.setUsablenum(enterNum);
			
			// 安全库存
			storageSummary.setSafenum(safenum);
			// 预计可用量=可用量+在途量+调拨待入量
			// 库存初始化时，没有在途量与调拨待入量
			storageSummary.setProjectedusablenum(enterNum);

			storageSummary.setUnitid(storageSummaryBatch.getUnitid());
			storageSummary.setUnitname(storageSummaryBatch.getUnitname());
			storageSummary.setAuxunitid(storageSummaryBatch.getAuxunitid());
			storageSummary.setAuxunitname(storageSummaryBatch.getAuxunitname());
			// 添加库存现存量总量信息
			storageSummaryMapper.addStorageSummary(storageSummary);
			
		} else {
			// 更新库存现存量总量信息
			storageSummaryMapper.updateStorageSummaryEnterByGoodsidAndStorageid(storageSummary.getStorageid(), storageSummary.getGoodsid(),storageSummary.getExistingnum(),enterNum,storageSummary.getVersion());
		}
		return flag;
	}

	/**
	 * 根据编号获取批次现存量信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 27, 2013
	 */
	public StorageSummaryBatch getStorageSummaryBatchById(String id) throws Exception{
		StorageSummaryBatch storageSummaryBatch = storageSummaryMapper.getStorageSummaryBatchInfoById(id);
		return storageSummaryBatch;
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
	public StorageSummaryBatch getStorageSummaryBatchByStorageidAndBatchno(String storageid,String batchno,String goodsid) throws Exception{
		StorageSummaryBatch storageSummaryBatch = storageSummaryMapper.getStorageSummaryBatchInfoByBatchno(storageid, batchno,goodsid);
		return storageSummaryBatch;
	}
	/**
	 * 根据仓库编号和生产日期 获取批次库存
	 * @param storageid         仓库编号
	 * @param goodsid           商品编号
	 * @param produceddate      生产日期
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月21日
	 */
	public StorageSummaryBatch getStorageSummaryBatchByStorageidAndProduceddate(String storageid,String goodsid,String produceddate) throws Exception{
		StorageSummaryBatch storageSummaryBatch = storageSummaryMapper.getStorageSummaryBatchInfoByProduceddate(storageid, produceddate,goodsid);
		return storageSummaryBatch;
	}

    /**
     *根据仓库编号和生产日期 获取批次库存
     * 没有批次库存初始化批次库存
     * @param storageid
     * @param goodsid
     * @param produceddate
     * @return
     * @throws Exception
     */
    public StorageSummaryBatch addOrGetStorageSummaryBatchByStorageidAndProduceddate(String storageid,String goodsid,String produceddate) throws Exception{
        StorageSummaryBatch storageSummaryBatch = storageSummaryMapper.getStorageSummaryBatchInfoAllByProduceddate(storageid, produceddate, goodsid);
        if(null==storageSummaryBatch){
            StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageid,goodsid);
            GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
            if(null!=storageSummary && null!=goodsInfo){
                storageSummaryBatch = new StorageSummaryBatch();
                storageSummaryBatch.setId(CommonUtils.getDataNumberSendsWithRand());
                storageSummaryBatch.setSummaryid(storageSummary.getId());
                storageSummaryBatch.setGoodsid(goodsid);
                storageSummaryBatch.setBrandid(goodsInfo.getBrand());
                Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                if(null!=brand){
                    storageSummaryBatch.setBranddept(brand.getDeptid());
                }
                storageSummaryBatch.setBarcode(goodsInfo.getBarcode());
                storageSummaryBatch.setStorageid(storageid);
                Map batchMap = getBatchno(goodsid,produceddate);
                if(null!=batchMap){
                    String batchno = (String) batchMap.get("batchno");
                    String deadline = (String) batchMap.get("deadline");
                    storageSummaryBatch.setBatchno(batchno);
                    storageSummaryBatch.setProduceddate(produceddate);
                    storageSummaryBatch.setDeadline(deadline);
                }

                storageSummaryBatch.setExistingnum(BigDecimal.ZERO);
                storageSummaryBatch.setUsablenum(BigDecimal.ZERO);
                storageSummaryBatch.setIntinum(BigDecimal.ZERO);
                storageSummaryBatch.setUnitid(storageSummary.getUnitid());
                storageSummaryBatch.setUnitname(storageSummary.getUnitname());
                storageSummaryBatch.setAuxunitid(storageSummary.getAuxunitid());
                storageSummaryBatch.setAuxunitname(storageSummary.getAuxunitname());
                storageSummaryBatch.setPrice(storageSummary.getCostprice());
                storageSummaryBatch.setEnterdate(CommonUtils.getTodayDataStr());
                storageSummaryMapper.addStorageSummaryBatch(storageSummaryBatch);
            }else if(null != goodsInfo){
                storageSummaryBatch = new StorageSummaryBatch();
                storageSummaryBatch.setId(CommonUtils.getDataNumberSendsWithRand());
                storageSummaryBatch.setSummaryid(CommonUtils.getDataNumberSendsWithRand());
                storageSummaryBatch.setGoodsid(goodsid);
                storageSummaryBatch.setBrandid(goodsInfo.getBrand());
                Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                if(null!=brand){
                    storageSummaryBatch.setBranddept(brand.getDeptid());
                }
                storageSummaryBatch.setBarcode(goodsInfo.getBarcode());
                storageSummaryBatch.setStorageid(storageid);
                Map batchMap = getBatchno(goodsid,produceddate);
                if(null!=batchMap){
                    String batchno = (String) batchMap.get("batchno");
                    String deadline = (String) batchMap.get("deadline");
                    storageSummaryBatch.setBatchno(batchno);
                    storageSummaryBatch.setProduceddate(produceddate);
                    storageSummaryBatch.setDeadline(deadline);
                }

                storageSummaryBatch.setExistingnum(BigDecimal.ZERO);
                storageSummaryBatch.setUsablenum(BigDecimal.ZERO);
                storageSummaryBatch.setIntinum(BigDecimal.ZERO);
                storageSummaryBatch.setUnitid(goodsInfo.getMainunit());
                storageSummaryBatch.setUnitname(goodsInfo.getMainunitName());
                storageSummaryBatch.setAuxunitid(goodsInfo.getAuxunitid());
                storageSummaryBatch.setAuxunitname(goodsInfo.getAuxunitname());
                storageSummaryBatch.setPrice(goodsInfo.getBasesaleprice());
                storageSummaryBatch.setEnterdate(CommonUtils.getTodayDataStr());
                storageSummaryMapper.addStorageSummaryBatch(storageSummaryBatch);
            }

        }
        return storageSummaryBatch;
    }
	/**
	 * 根据仓库编号和商品编号  获取仓库中最早的批次库存
	 * 当仓库中的批次库存都已经为0的时候 取最近发货批次
	 * @param storageid			仓库编号
	 * @param goodsid             商品编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月21日
	 */
	public StorageSummaryBatch getStorageSummaryBatchByStorageidNew(String storageid,String goodsid) throws Exception{
		StorageSummaryBatch storageSummaryBatch = storageSummaryMapper.getStorageSummaryBatchByStorageidNew(storageid,goodsid);
		if(null==storageSummaryBatch){
			storageSummaryBatch = storageSummaryMapper.getStorageSummaryBatchByStorageidLast(storageid,goodsid);
		}
		return storageSummaryBatch;
	}
	/**
	 * 更新库存现存量信息和批次现存量信息
	 * @param storageSummary				仓库商品库存信息
	 * @param storageSummaryBatch		仓库商品批次信息
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 27, 2013
	 */
	public boolean updateStorageSummaryInfo(StorageSummary storageSummary,StorageSummaryBatch storageSummaryBatch) throws Exception{
		int i = storageSummaryMapper.updateStorageSummary(storageSummary);
		int j = storageSummaryMapper.updateStorageSummaryBacth(storageSummaryBatch);
		return i>0 && j>0;
	}
	/**
	 * 添加库存数量收货汇总数据
	 * 需要在库存现存量和批次现存量更新前操作
	 * @param billmodel				业务单据类型
	 * @param billid				业务单据名称
	 * @param storageSummaryBatch	操作的批次现存量
	 * @param num					现存量变化数量
	 * @param remark				备注
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 29, 2013
	 */
	public boolean addStorageSummaryReceiveLog(String billmodel,String billid,StorageSummaryBatch storageSummaryBatch,BigDecimal num,String remark) throws Exception{
		if(null==storageSummaryBatch){
			return false;
		}
		List<StorageSummary> storageSummaryList = storageSummaryMapper.getStorageSummaryInfoByGoodsid(storageSummaryBatch.getGoodsid());
		//期初库存总量
		BigDecimal begintotalnum = new BigDecimal(0);
		//期初仓库总量
		BigDecimal beginstoragenum = new BigDecimal(0);
		//期初批次总量（库位数量）
		BigDecimal beginbatchnum = new BigDecimal(0);
		//库存总量 = 各仓库总量相加
		for(StorageSummary storageSummary : storageSummaryList){
			begintotalnum = begintotalnum.add(storageSummary.getExistingnum());
		}
		//获取仓库现存量数据
		StorageSummary storageSummary = storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid(storageSummaryBatch.getGoodsid(), storageSummaryBatch.getStorageid());
		if(null!=storageSummary){
			beginstoragenum = beginstoragenum.add(storageSummary.getExistingnum());
		}
		if(null!=storageSummaryBatch.getId()){
			StorageSummaryBatch storageSummaryBatch2 = storageSummaryMapper.getStorageSummaryBatchInfoById(storageSummaryBatch.getId());
			if(null!=storageSummaryBatch2){
				beginbatchnum =  storageSummaryBatch2.getExistingnum();
			}
		}
		SysUser sysUser = getSysUser();
		
		StorageSummaryLog storageSummaryBatchLog = new StorageSummaryLog();
		if(null!=sysUser){
			storageSummaryBatchLog.setAddusername(sysUser.getName());
			storageSummaryBatchLog.setAddusrid(sysUser.getUserid());
		}else{
			storageSummaryBatchLog.setAddusername("车销");
		}
		storageSummaryBatchLog.setRemark(remark);
		
		storageSummaryBatchLog.setSummarybatchid(storageSummaryBatch.getId());
		storageSummaryBatchLog.setGoodsid(storageSummaryBatch.getGoodsid());
		storageSummaryBatchLog.setStorageid(storageSummaryBatch.getStorageid());
		storageSummaryBatchLog.setStoragelocationid(storageSummaryBatch.getStoragelocationid());
		storageSummaryBatchLog.setBatchno(storageSummaryBatch.getBatchno());
		
		storageSummaryBatchLog.setUnitid(storageSummaryBatch.getUnitid());
		storageSummaryBatchLog.setUnitname(storageSummaryBatch.getUnitname());
		storageSummaryBatchLog.setAuxunitid(storageSummaryBatch.getAuxunitid());
		storageSummaryBatchLog.setAuxunitname(storageSummaryBatch.getAuxunitname());
		
		storageSummaryBatchLog.setBillmodel(billmodel);
		storageSummaryBatchLog.setBillid(billid);
		//收货数量
		storageSummaryBatchLog.setReceivenum(num);
		
		//期初期末数量
		storageSummaryBatchLog.setBegintotalnum(begintotalnum);
		storageSummaryBatchLog.setBeginstoragenum(beginstoragenum);
		storageSummaryBatchLog.setBeginbatchnum(beginbatchnum);
		storageSummaryBatchLog.setEndtotalnum(begintotalnum.add(num));
		storageSummaryBatchLog.setEndstoragenum(beginstoragenum.add(num));
		storageSummaryBatchLog.setEndbatchnum(beginbatchnum.add(num));
		//计算辅单位数量
		//辅单位收获量
		Map map = countGoodsInfoNumber(storageSummaryBatchLog.getGoodsid(),storageSummaryBatchLog.getAuxunitid(),num);
		storageSummaryBatchLog.setAuxreceivenum((BigDecimal) map.get("auxnum"));
		storageSummaryBatchLog.setAuxreceivenumdetail((String) map.get("auxnumdetail"));
		//辅单位期初总量
		Map totalmap = countGoodsInfoNumber(storageSummaryBatchLog.getGoodsid(),storageSummaryBatchLog.getAuxunitid(),storageSummaryBatchLog.getBegintotalnum());
		storageSummaryBatchLog.setAuxbegintotalnum((BigDecimal) totalmap.get("auxnum"));
		storageSummaryBatchLog.setAuxbegintotalnumdetail((String) totalmap.get("auxnumdetail"));
		//辅单位期末总量
		Map endmap = countGoodsInfoNumber(storageSummaryBatchLog.getGoodsid(),storageSummaryBatchLog.getAuxunitid(),storageSummaryBatchLog.getEndtotalnum());
		storageSummaryBatchLog.setAuxendtotalnum((BigDecimal) endmap.get("auxnum"));
		storageSummaryBatchLog.setAuxendtotalnumdetail((String) endmap.get("auxnumdetail"));
		//辅单位期初仓库总量
		Map storagebeginmap = countGoodsInfoNumber(storageSummaryBatchLog.getGoodsid(),storageSummaryBatchLog.getAuxunitid(),storageSummaryBatchLog.getBeginstoragenum());
		storageSummaryBatchLog.setAuxbeginstoragenum((BigDecimal) storagebeginmap.get("auxnum"));
		storageSummaryBatchLog.setAuxbeginstoragenumdetail((String) storagebeginmap.get("auxnumdetail"));
		//辅单位期末仓库总量
		Map storageendmap = countGoodsInfoNumber(storageSummaryBatchLog.getGoodsid(),storageSummaryBatchLog.getAuxunitid(),storageSummaryBatchLog.getEndstoragenum());
		storageSummaryBatchLog.setAuxendstoragenum((BigDecimal) storageendmap.get("auxnum"));
		storageSummaryBatchLog.setAuxendstoragenumdetail((String) storageendmap.get("auxnumdetail"));
		//辅单位期初批次数量
		Map batchbeginmap = countGoodsInfoNumber(storageSummaryBatchLog.getGoodsid(),storageSummaryBatchLog.getAuxunitid(),storageSummaryBatchLog.getBeginbatchnum());
		storageSummaryBatchLog.setAuxbeginbatchnum((BigDecimal) batchbeginmap.get("auxnum"));
		storageSummaryBatchLog.setAuxbeginbatchnumdetail((String) batchbeginmap.get("auxnumdetail"));
		//辅单位期末批次数量
		Map batchendmap = countGoodsInfoNumber(storageSummaryBatchLog.getGoodsid(),storageSummaryBatchLog.getAuxunitid(),storageSummaryBatchLog.getEndbatchnum());
		storageSummaryBatchLog.setAuxendbatchnum((BigDecimal) batchendmap.get("auxnum"));
		storageSummaryBatchLog.setAuxendbatchnumdetail((String) batchendmap.get("auxnumdetail"));
		
		int i = storageSummaryMapper.addStorageSummaryLog(storageSummaryBatchLog);
		return i>0;
	}
	/**
	 * 添加库存数量发货汇总数据
	 * 需要在库存现存量和批次现存量更新前操作
	 * @param billmodel			      单据类型
	 * @param billid                    单据编号
	 * @param storageSummaryBatch     商品库存批次信息
	 * @param num                       数量
	 * @param remark                    备注
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 30, 2013
	 */
	public boolean addStorageSummarySendLog(String billmodel,String billid,StorageSummaryBatch storageSummaryBatch,BigDecimal num,String remark) throws Exception{
		if(null==storageSummaryBatch){
			return false;
		}
		List<StorageSummary> storageSummaryList = storageSummaryMapper.getStorageSummaryInfoByGoodsid(storageSummaryBatch.getGoodsid());
		//期初库存总量
		BigDecimal begintotalnum = new BigDecimal(0);
		//期初仓库总量
		BigDecimal beginstoragenum = new BigDecimal(0);
		//期初批次总量（库位数量）
		BigDecimal beginbatchnum = new BigDecimal(0);
		//库存总量 = 各仓库总量相加
		for(StorageSummary storageSummary : storageSummaryList){
			begintotalnum = begintotalnum.add(storageSummary.getExistingnum());
		}
		//获取仓库现存量数据
		StorageSummary storageSummary = storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid(storageSummaryBatch.getGoodsid(), storageSummaryBatch.getStorageid());
		if(null!=storageSummary){
			beginstoragenum = beginstoragenum.add(storageSummary.getExistingnum());
		}
		StorageSummaryBatch storageSummaryBatch2 = storageSummaryMapper.getStorageSummaryBatchInfoById(storageSummaryBatch.getId());
		if(null!=storageSummaryBatch2){
			beginbatchnum =  storageSummaryBatch2.getExistingnum();
		}
		StorageSummaryLog storageSummaryBatchLog = new StorageSummaryLog();
		SysUser sysUser = getSysUser();
		if(null!=sysUser){
			storageSummaryBatchLog.setAddusername(sysUser.getName());
			storageSummaryBatchLog.setAddusrid(sysUser.getUserid());
			storageSummaryBatchLog.setRemark(remark);
		}
		
		storageSummaryBatchLog.setSummarybatchid(storageSummaryBatch.getId());
		storageSummaryBatchLog.setGoodsid(storageSummaryBatch.getGoodsid());
		storageSummaryBatchLog.setStorageid(storageSummaryBatch.getStorageid());
		storageSummaryBatchLog.setStoragelocationid(storageSummaryBatch.getStoragelocationid());
		storageSummaryBatchLog.setBatchno(storageSummaryBatch.getBatchno());
		
		storageSummaryBatchLog.setUnitid(storageSummaryBatch.getUnitid());
		storageSummaryBatchLog.setUnitname(storageSummaryBatch.getUnitname());
		storageSummaryBatchLog.setAuxunitid(storageSummaryBatch.getAuxunitid());
		storageSummaryBatchLog.setAuxunitname(storageSummaryBatch.getAuxunitname());
		
		storageSummaryBatchLog.setBillmodel(billmodel);
		storageSummaryBatchLog.setBillid(billid);
		//发货数量
		storageSummaryBatchLog.setSendnum(num);
		
		//期初期末数量
		storageSummaryBatchLog.setBegintotalnum(begintotalnum);
		storageSummaryBatchLog.setBeginstoragenum(beginstoragenum);
		storageSummaryBatchLog.setBeginbatchnum(beginbatchnum);
		storageSummaryBatchLog.setEndtotalnum(begintotalnum.subtract(num));
		storageSummaryBatchLog.setEndstoragenum(beginstoragenum.subtract(num));
		storageSummaryBatchLog.setEndbatchnum(beginbatchnum.subtract(num));
		//计算辅单位数量
		//辅单位发货量
		Map map = countGoodsInfoNumber(storageSummaryBatchLog.getGoodsid(),storageSummaryBatchLog.getAuxunitid(),num);
		storageSummaryBatchLog.setAuxsendnum((BigDecimal) map.get("auxnum"));
		storageSummaryBatchLog.setAuxsendnumdetail((String) map.get("auxnumdetail"));
		//辅单位期初总量
		Map totalmap = countGoodsInfoNumber(storageSummaryBatchLog.getGoodsid(),storageSummaryBatchLog.getAuxunitid(),storageSummaryBatchLog.getBegintotalnum());
		storageSummaryBatchLog.setAuxbegintotalnum((BigDecimal) totalmap.get("auxnum"));
		storageSummaryBatchLog.setAuxbegintotalnumdetail((String) totalmap.get("auxnumdetail"));
		//辅单位期末总量
		Map endmap = countGoodsInfoNumber(storageSummaryBatchLog.getGoodsid(),storageSummaryBatchLog.getAuxunitid(),storageSummaryBatchLog.getEndtotalnum());
		storageSummaryBatchLog.setAuxendtotalnum((BigDecimal) endmap.get("auxnum"));
		storageSummaryBatchLog.setAuxendtotalnumdetail((String) endmap.get("auxnumdetail"));
		//辅单位期初仓库总量
		Map storagebeginmap = countGoodsInfoNumber(storageSummaryBatchLog.getGoodsid(),storageSummaryBatchLog.getAuxunitid(),storageSummaryBatchLog.getBeginstoragenum());
		storageSummaryBatchLog.setAuxbeginstoragenum((BigDecimal) storagebeginmap.get("auxnum"));
		storageSummaryBatchLog.setAuxbeginstoragenumdetail((String) storagebeginmap.get("auxnumdetail"));
		//辅单位期末仓库总量
		Map storageendmap = countGoodsInfoNumber(storageSummaryBatchLog.getGoodsid(),storageSummaryBatchLog.getAuxunitid(),storageSummaryBatchLog.getEndstoragenum());
		storageSummaryBatchLog.setAuxendstoragenum((BigDecimal) storageendmap.get("auxnum"));
		storageSummaryBatchLog.setAuxendstoragenumdetail((String) storageendmap.get("auxnumdetail"));
		//辅单位期初批次数量
		Map batchbeginmap = countGoodsInfoNumber(storageSummaryBatchLog.getGoodsid(),storageSummaryBatchLog.getAuxunitid(),storageSummaryBatchLog.getBeginbatchnum());
		storageSummaryBatchLog.setAuxbeginbatchnum((BigDecimal) batchbeginmap.get("auxnum"));
		storageSummaryBatchLog.setAuxbeginbatchnumdetail((String) batchbeginmap.get("auxnumdetail"));
		//辅单位期末批次数量
		Map batchendmap = countGoodsInfoNumber(storageSummaryBatchLog.getGoodsid(),storageSummaryBatchLog.getAuxunitid(),storageSummaryBatchLog.getEndbatchnum());
		storageSummaryBatchLog.setAuxendbatchnum((BigDecimal) batchendmap.get("auxnum"));
		storageSummaryBatchLog.setAuxendbatchnumdetail((String) batchendmap.get("auxnumdetail"));
		
		int i = storageSummaryMapper.addStorageSummaryLog(storageSummaryBatchLog);
		return i>0;
	}
	/**
	 * 根据仓库编码和商品编码获取该仓库的批次现存量列表
	 * 按生产日期排序
	 * @param storageid         仓库编号
	 * @param goodsid           商品编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 4, 2013
	 */
	public List<StorageSummaryBatch> getStorageSummaryBatchListByStorageidAndGoodsid(String storageid,String goodsid) throws Exception{
		List<StorageSummaryBatch> list = storageSummaryMapper.getStorageSummaryBatchListByStorageidAndGoodsid(storageid, goodsid);
		return list;
	}
	/**
	 * 根据仓库编码和商品编码获取仓库批次量数据列表(只包含批次号与生产日期的记录)
	 * @param storageid	仓库编号
	 * @param goodsid		商品编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月21日
	 */
	public List<StorageSummaryBatch> getStorageSummaryBatchListWithoutNoBatchByStorageidAndGoodsid(String storageid,String goodsid) throws Exception{
		List<StorageSummaryBatch> list = storageSummaryMapper.getStorageSummaryBatchListWithoutNoBatchByStorageidAndGoodsid(storageid, goodsid);
		return list;
	}
	/**
	 * 根据仓库编码和商品编码获取该仓库商品现存量信息
	 * @param summaryid		仓库商品编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 4, 2013
	 */
	public StorageSummary getStorageSummaryByID(String summaryid) throws Exception{
		StorageSummary StorageSummary = storageSummaryMapper.getStorageSummaryInfoByID(summaryid);
		return StorageSummary;
	}
	/**
	 * 根据仓库编码和商品编码获取该仓库商品现存量信息
	 * @param storageid         仓库编号
	 * @param goodsid           商品编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 4, 2013
	 */
	public StorageSummary getStorageSummaryByStorageidAndGoodsid(String storageid,String goodsid) throws Exception{
		StorageSummary StorageSummary = storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid(goodsid, storageid);
		return StorageSummary;
	}
	/**
	 * 根据仓库编码，库位编码，商品编码获取批次现存量（库位现存量）
	 * @param storageid             仓库编号
	 * @param storagelocationid     库位编号
	 * @param goodsid               商品编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 4, 2013
	 */
	public StorageSummaryBatch getStorageSummaryBatchByStorageidAndStoragelocationid(String storageid,String storagelocationid,String goodsid) throws Exception{
		StorageSummaryBatch storageSummaryBatch = storageSummaryMapper.getStorageSummaryBatchByStorageidAndStoragelocationid(goodsid, storageid, storagelocationid);
		return storageSummaryBatch;
	}
	/**
	 * 根据库存批次号获取该批次信息
	 * @param summarybatchid           商品库存批次编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月21日
	 */
	public StorageSummaryBatch getStorageSummaryBatchByID(String summarybatchid) throws Exception{
		StorageSummaryBatch storageSummaryBatch = storageSummaryMapper.getStorageSummaryBatchInfoById(summarybatchid);
		return storageSummaryBatch;
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
	public StorageSummaryBatch getStorageSummaryBatchByStorageidAndGoodsid(String storageid,String goodsid) throws Exception{
		StorageSummaryBatch storageSummaryBatch = storageSummaryMapper.getStorageSummaryBatchByStorageidAndGoodsid(storageid, goodsid);
		return storageSummaryBatch;
	}
	/**
	 * 根据仓库编码和商品编码 获取没有批次号的库存
	 * @param storageid             仓库编号
	 * @param goodsid               商品编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月21日
	 */
	public StorageSummaryBatch getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(String storageid,String goodsid) throws Exception{
		StorageSummaryBatch storageSummaryBatch = storageSummaryMapper.getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(storageid, goodsid);
		return storageSummaryBatch;
	}
	/**
	 * 根据仓库编码和商品编码 获取该仓库的各库位现存量信息列表
	 * 根据可用数量多少排序 asc
	 * @param storageid             仓库编号
	 * @param goodsid               商品编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 4, 2013
	 */
	public List getStorageSummaryBatchListOrderNums(String storageid,String goodsid) throws Exception{
		List list = storageSummaryMapper.getStorageSummaryBatchListOrderNums(storageid, goodsid);
		return list;
	}
	/**
	 * 根据仓库编码和商品编码 获取该仓库的各库位现存量信息列表
	 * 根据入库日期 现存量排序 asc
     * @param storageid         仓库编号
     * @param goodsid           商品编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年6月17日
	 */
	public List getStorageSummaryBatchListOrderEnterdate(String storageid,String goodsid) throws Exception{
		List list = storageSummaryMapper.getStorageSummaryBatchListOrderEnterdate(storageid, goodsid);
		return list;
	}
	/**
	 * 根据商品编号获取商品现存量列表
	 * 发货仓库的列表
	 * @param goodsid			商品编号
	 * @param storageid		仓库编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 10, 2013
	 */
	public List getStorageSummaryListByGoodsWithoutStorageid(String goodsid,String storageid) throws Exception{
		List list = storageSummaryMapper.getStorageSummaryListByGoodsWithoutStorageid(goodsid, storageid);
		return list;
	}
	/**
	 * 根据仓库编码，商品编码，和发货数量 判断仓库中是否能发货
	 * Map flag:true能发货 false不能发货  useablenum可用量
	 * 	   msg不能发货原因
	 * @param storageid         仓库ibanh
	 * @param goodsid           商品编号
	 * @param sendNum           发货熟练
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 4, 2013
	 */
	public Map isSendGoodsByStorageidAndGoodsid(String storageid,String goodsid,BigDecimal sendNum) throws Exception{
		boolean flag = false;
		String msg = "";
		Map map = new HashMap();
		StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageid, goodsid);
		GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
		StorageInfo storageInfo = getStorageInfoByID(storageid);
		if(null!=storageSummary && null!=goodsInfo){
			BigDecimal totalNum = BigDecimal.ZERO;
			BigDecimal usablenum = storageSummary.getUsablenum();
			if(null==usablenum){
				usablenum = BigDecimal.ZERO;
			}
			//判断仓库是否允许超可用量发货 允许的话 
			//可销售的数量 = 可用量+在途量
			BigDecimal transitnum = BigDecimal.ZERO;
			if("1".equals(storageInfo.getIssendusable()) && null!=storageSummary.getTransitnum()){
				transitnum = storageSummary.getTransitnum();
			}
			totalNum = usablenum.add(transitnum);
			//判断仓库现存量是否大于销售发货通知单明细中商品数量
			//小于销售发货通知单明显中商品数量
			if(totalNum.compareTo(sendNum)==-1){
				//允许负库存
				if("1".equals(storageInfo.getIslosestorage())){
				}else{
					flag = false;
					msg = "商品编码:"+goodsid+",商品名称:"+goodsInfo.getName()+",在"+storageInfo.getName()+"仓库中,可用量不足,可用量："+totalNum.intValue()+",发货量："+sendNum.intValue();
				}
			}else{
				flag = true;
			}
			map.put("useablenum", storageSummary.getUsablenum());
		}else{
			flag = false;
			if(null!=storageInfo){
				msg = "商品编码:"+goodsid+",商品名称:"+goodsInfo.getName()+",在"+storageInfo.getName()+"仓库中不存在";
			}else{
				msg = "未指定仓库";
			}
		}
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}
	/**
	 * 根据库存批次号 判断商品是否足够发货
	 * @param goodsid               商品编号
	 * @param summarybatchid        商品库存批次编号
	 * @param batchno               批次号
	 * @param sendNum               发货数量
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月21日
	 */
	public Map isSendGoodsBySummarybatchid(String goodsid,String summarybatchid,String batchno,BigDecimal sendNum) throws Exception{
		boolean flag = false;
		String msg = "";
		Map map = new HashMap();
		StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchByID(summarybatchid);
		if(null!=storageSummaryBatch){
			GoodsInfo goodsInfo = getGoodsInfoByID(storageSummaryBatch.getGoodsid());
			StorageInfo storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
			BigDecimal totalNum = BigDecimal.ZERO;
			BigDecimal usablenum = storageSummaryBatch.getUsablenum();
			if(null==usablenum){
				usablenum = BigDecimal.ZERO;
			}
			//判断仓库是否允许超可用量发货 允许的话 
			//可销售的数量 = 可用量+在途量
			BigDecimal transitnum = BigDecimal.ZERO;
			totalNum = usablenum.add(transitnum);
			//判断仓库现存量是否大于销售发货通知单明细中商品数量
			//小于销售发货通知单明显中商品数量
			if(totalNum.compareTo(sendNum)==-1){
				flag = false;
				msg = "商品编码:"+storageSummaryBatch.getGoodsid()+",商品名称:"+goodsInfo.getName()+",在"+storageInfo.getName()+"仓库中,可用量不足,可用量："+totalNum.intValue()+",发货量："+sendNum.intValue();
			}else{
				flag = true;
			}
			map.put("useablenum", storageSummaryBatch.getUsablenum());
		}else{
			flag = false;
			msg = "商品编码:"+goodsid+",批次号："+batchno+",在仓库中不存在";
		}
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}
	/**
	 * 盘点商品在发货仓库中总量是否足够（参与总量控制的仓库，发货仓库）
	 * @param goodsid           商品编号
     * @param sendNum           f发货数量
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 10, 2013
	 */
	public Map isSendGoodsByGoodsid(String goodsid,BigDecimal sendNum) throws Exception{
		
		boolean flag = false;
		String msg = "";
		Map map = new HashMap();
		StorageSummary storageSummary = storageSummaryMapper.getStorageSummarySumByGoodsid(goodsid);
		GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
		
		if(null!=storageSummary && null!=goodsInfo){
			BigDecimal totalNum = BigDecimal.ZERO;
			BigDecimal usablenum = storageSummary.getUsablenum();
			if(null==usablenum){
				usablenum = BigDecimal.ZERO;
			}
			//判断仓库是否允许超可用量发货 允许的话 
			
			StorageSummary outuseableStorageSummary = storageSummaryMapper.getStorageSummaryTransitnumSumByOutusable(goodsid);
			BigDecimal transitnum = BigDecimal.ZERO;
			if(null!=outuseableStorageSummary && null!=outuseableStorageSummary.getTransitnum()){
				transitnum = outuseableStorageSummary.getTransitnum();
			}
			//可销售的数量 = 可用量+在途量
			totalNum = usablenum.add(transitnum);
			//判断仓库现存量是否大于销售发货通知单明细中商品数量
			//小于销售发货通知单明显中商品数量
			if(totalNum.compareTo(sendNum)==-1){
				msg = "商品编码:"+goodsid+",商品名称:"+goodsInfo.getName()+",在仓库中,可用量不足";
			}else{
				flag = true;
			}
			map.put("useablenum", storageSummary.getUsablenum());
		}else{
			flag = false;
			msg = "商品编码:"+goodsid+",商品名称:"+goodsInfo.getName()+",在仓库中不存在";
		}
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}
	/**
	 * 根据批次现存量 更新库存的待发量
	 * @param summarybatchid		批次现存量（库位现存量，仓库现存量，根据商品是否批次管理 库位管理确定）
	 * @param waitnum					待发量
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 4, 2013
	 */
	public boolean updateStorageSummaryWaitnum(String summarybatchid,BigDecimal waitnum) throws Exception{
        StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(summarybatchid);
        StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageSummaryBatch.getStorageid(),storageSummaryBatch.getGoodsid());
		//更新批次现存量和仓库现存量信息
		int i = storageSummaryMapper.updateStorageSummaryWaitAddByGoodsidAndStorageid(storageSummaryBatch.getStorageid(),storageSummaryBatch.getGoodsid(),storageSummary.getWaitnum(),waitnum,storageSummary.getVersion());
		int j = storageSummaryMapper.updateStorageSummaryBacthWaitAddByID(storageSummaryBatch.getId(),storageSummaryBatch.getWaitnum(),waitnum,storageSummaryBatch.getVersion());
        boolean flag  = i>0&&j>0;
        if(!flag){
            throw new Exception("更新待发量失败。商品："+storageSummaryBatch.getGoodsid()+"，仓库："+storageSummaryBatch.getStorageid());
        }
		return flag;
	}
	/**
	 * 回滚批次现存量 更新库存的待发量
	 * @param summarybatchid		批次现存量（库位现存量，仓库现存量，根据商品是否批次管理 库位管理确定）
	 * @param waitnum					待发量
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	public boolean rollBackStorageSummaryWaitnum(String summarybatchid,BigDecimal waitnum) throws Exception{
        StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(summarybatchid);
        StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageSummaryBatch.getStorageid(),storageSummaryBatch.getGoodsid());
		//更新批次现存量和仓库现存量信息
		int i = storageSummaryMapper.updateStorageSummaryWaitBackByGoodsidAndStorageid(storageSummaryBatch.getStorageid(),storageSummaryBatch.getGoodsid(),storageSummary.getWaitnum(),waitnum,storageSummary.getVersion());
		int j = storageSummaryMapper.updateStorageSummaryBacthWaitBackByID(storageSummaryBatch.getId(),storageSummaryBatch.getWaitnum(),waitnum,storageSummaryBatch.getVersion());
        boolean flag  = i>0&&j>0;
        if(!flag){
            throw new Exception("回滚待发量失败。商品："+storageSummaryBatch.getGoodsid()+"，仓库："+storageSummaryBatch.getStorageid());
        }
		return flag;
	}
	/**
	 * 商品发货，更新待发量
	 * 减去待发量和现存量
	 * @param summarybatchid		批次现存量（哪些商品发货）
	 * @param waitnum					待发量
	 * @param billmodel					单据类型
	 * @param billid					单据编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 8, 2013
	 */
	public boolean sendStorageSummaryWaitnum(String summarybatchid,BigDecimal waitnum,String billmodel,String billid) throws Exception{
        StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(summarybatchid);
        //批次现存量需要出库
        //待发量 = 待发量+当前待发量
        storageSummaryBatch.setWaitnum(storageSummaryBatch.getWaitnum().subtract(waitnum));
        //现存量 = 现存量+当前待发量
        storageSummaryBatch.setExistingnum(storageSummaryBatch.getExistingnum().subtract(waitnum));
        //现存量不为0时
        if(storageSummaryBatch.getExistingnum().compareTo(new BigDecimal(0))==1){
            GoodsInfo goodsInfo = getAllGoodsInfoByID(storageSummaryBatch.getGoodsid());
            StorageInfo storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
            //当商品是批次管理时 更新状态 关闭该批次现存量
            if("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
                storageSummaryBatch.setBatchstate("1");
            }
        }
		//批次现存量需要出库
		//待发量 = 待发量-当前待发量
		//现存量 = 现存量-当前待发量
		String batchstate = "1";
		//现存量为0时
		if(storageSummaryBatch.getExistingnum().compareTo(new BigDecimal(0))==0){
			GoodsInfo goodsInfo = getAllGoodsInfoByID(storageSummaryBatch.getGoodsid());
			StorageInfo storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
			//当仓库和商品是批次管理时 更新状态 关闭该批次现存量
			if("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
				storageSummaryBatch.setBatchstate("2");
				batchstate = "2";
			}
		}

		String remark="销售出库单审核通过，仓库出库";
		if("purchaseRejectOut".equals(billmodel)){
			remark="采购退货出库审核通过，仓库出库";
		}
		//添加现存量改变日志
		addStorageSummarySendLog(billmodel, billid, storageSummaryBatch, waitnum, remark);
		StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageSummaryBatch.getStorageid(),storageSummaryBatch.getGoodsid());
        StorageSummaryBatch storageSummaryBatch1 = getStorageSummaryBatchById(storageSummaryBatch.getId());
		//更新批次现存量和仓库现存量信息
		int i = storageSummaryMapper.updateStorageSummarySendByGoodsidAndStorageid(storageSummaryBatch.getStorageid(),storageSummaryBatch.getGoodsid(),storageSummary.getExistingnum(),storageSummary.getWaitnum(),waitnum,storageSummary.getVersion());
		int j = storageSummaryMapper.updateStorageSummaryBacthSendByID(storageSummaryBatch.getId(),storageSummaryBatch1.getExistingnum(),storageSummaryBatch1.getWaitnum(),waitnum,batchstate,storageSummaryBatch1.getVersion());
        boolean flag  = i>0&&j>0;
        if(!flag){
            throw new Exception("出库更新现存量，可用量，待发量失败。商品："+storageSummaryBatch.getGoodsid()+"，仓库："+storageSummaryBatch.getStorageid());
        }
		return flag;
	}
	/**
	 * 出库后，反审数据 回滚已发货的数据 变为待发量
	 * @param summarybatchid           商品库存批次信息
	 * @param waitnum                         待发数量
	 * @param billmodel                       单据类型
	 * @param billid                           单据编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 8, 2013
	 */
	public boolean rollBackSendStorageSummaryWaitnum(String summarybatchid,BigDecimal waitnum,String billmodel,String billid) throws Exception{
        StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(summarybatchid);
		//批次现存量需要出库
		//待发量 = 待发量+当前待发量
		storageSummaryBatch.setWaitnum(storageSummaryBatch.getWaitnum().add(waitnum));
		//现存量 = 现存量+当前待发量
		storageSummaryBatch.setExistingnum(storageSummaryBatch.getExistingnum().add(waitnum));
		//现存量不为0时
		if(storageSummaryBatch.getExistingnum().compareTo(new BigDecimal(0))==1){
			GoodsInfo goodsInfo = getAllGoodsInfoByID(storageSummaryBatch.getGoodsid());
			StorageInfo storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
			//当商品是批次管理时 更新状态 关闭该批次现存量
			if("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
				storageSummaryBatch.setBatchstate("1");
			}
		}
		String remark="销售出库单反审，回滚发货量为待发量";
		if("purchaseRejectOut".equals(billmodel)){
			remark="采购退货出库单反审，回滚发货量为待发量";
		}
		//回滚待发量
		addStorageSummaryReceiveLog(billmodel, billid, storageSummaryBatch, waitnum, remark);
        StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageSummaryBatch.getStorageid(),storageSummaryBatch.getGoodsid());
        StorageSummaryBatch storageSummaryBatch1 = getStorageSummaryBatchById(storageSummaryBatch.getId());

		//更新批次现存量和仓库现存量信息
		int i = storageSummaryMapper.updateStorageSummarySendBackByGoodsidAndStorageid(storageSummaryBatch.getStorageid(),storageSummaryBatch.getGoodsid(),storageSummary.getExistingnum(),storageSummary.getWaitnum(),waitnum,storageSummary.getVersion());
		int j = storageSummaryMapper.updateStorageSummaryBacthBackByID(storageSummaryBatch.getId(),storageSummaryBatch1.getExistingnum(),storageSummaryBatch1.getWaitnum(),waitnum,storageSummaryBatch1.getVersion());
//		//更新最新库存以及库存平均单价
//		updateGoodsAveragePrice(storageSummaryBatch.getGoodsid());
		boolean flag = i>0&&j>0;
        if(!flag){
            throw new Exception("回滚出库更新现存量，可用量，待发量失败。商品："+storageSummaryBatch.getGoodsid()+"，仓库："+storageSummaryBatch.getStorageid());
        }
        return flag;
	}
	/**
	 * 更新仓库中商品的在途量
	 * @param storageid		    仓库编码
     * @param goodsid             商品编码
	 * @param transitnum			在途量
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 10, 2013
	 */
	public boolean updateStorageSummaryTransitnum(String storageid,String goodsid,BigDecimal transitnum) throws Exception{
		//获取商品在仓库中的现存量信息
		StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageid, goodsid);
		if(null!=storageSummary){
			//在途量 = 当前在途量 + 新增的在途量
			storageSummary.setTransitnum(storageSummary.getTransitnum().add(transitnum));
			//预计可用量 = 当前预计可用量 + 新增的在途量
			storageSummary.setProjectedusablenum(storageSummary.getProjectedusablenum().add(transitnum));
			int i = storageSummaryMapper.updateStorageSummary(storageSummary);
            if(i==0){
                throw new Exception("更新途量失败。商品："+storageSummary.getGoodsid()+"，仓库："+storageSummary.getStorageid());
            }
			return i>0;
		}else{
			GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
			StorageInfo storageInfo = getStorageInfoByID(storageid);
			storageSummary = new StorageSummary();
			String summaryid = CommonUtils.getDataNumberSendsWithRand();
			storageSummary.setId(summaryid);
			storageSummary.setGoodsid(goodsid);
			if(null!=goodsInfo){
				storageSummary.setBrandid(goodsInfo.getBrand());
				Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
				if(null!=brand){
					storageSummary.setBranddept(brand.getDeptid());
				}
			}
			storageSummary.setBarcode(goodsInfo.getBarcode());
			storageSummary.setStorageid(storageid);
			storageSummary.setIssendstorage(storageInfo.getIssendstorage());
			storageSummary.setIstotalcontrol(storageInfo.getIstotalcontrol());
			// 现存量=批次现存量
			storageSummary.setExistingnum(new BigDecimal(0));
			// 安全库存
			BigDecimal safenum = getStorageInventoryByGoodsidAndStorageid(goodsid, storageid);
			
			// 可用量=现存量-安全库存
			storageSummary.setUsablenum(new BigDecimal(0));
			// 安全库存
			storageSummary.setSafenum(safenum);
			//在途量 = 当前在途量 + 新增的在途量
			storageSummary.setTransitnum(transitnum);
			// 预计可用量=可用量+在途量+调拨待入量
			storageSummary.setProjectedusablenum(transitnum);
			//主单位信息
			storageSummary.setUnitid(goodsInfo.getMainunit());
			MeteringUnit meteringUnit = getMeteringUnitById(goodsInfo.getMainunit());
			if(null!=meteringUnit){
				storageSummary.setUnitname(meteringUnit.getName());
			}
			//默认辅单位信息
			MeteringUnit auxunit = getGoodsAuxUnitInfoByGoodsid(goodsid);
			if(null!=auxunit){
				storageSummary.setAuxunitid(auxunit.getId());
				storageSummary.setAuxunitname(auxunit.getName());
			}
			// 添加库存现存量总量信息
			int i = storageSummaryMapper.addStorageSummary(storageSummary);
            if(i==0){
                throw new Exception("更新途量失败。商品："+storageSummary.getGoodsid()+"，仓库："+storageSummary.getStorageid());
            }
			return i>0;
		}
	}
	/**
	 * 回滚仓库的在途量
	 * @param storageid		  仓库编码
     * @param goodsid           商品编码
	 * @param transitnum		  在途量
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 10, 2013
	 */
	public boolean rollBackStorageSummaryTransitnum(String storageid,String goodsid,BigDecimal transitnum) throws Exception{
		//获取商品在仓库中的现存量信息
		StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageid, goodsid);
		//在途量 = 当前在途量 - 新增的在途量
		storageSummary.setTransitnum(storageSummary.getTransitnum().subtract(transitnum));
		//预计可用量 = 当前预计可用量 - 新增的在途量
		storageSummary.setProjectedusablenum(storageSummary.getProjectedusablenum().subtract(transitnum));
		int i = storageSummaryMapper.updateStorageSummary(storageSummary);
		if(i==0){
            throw new Exception("回滚更新途量失败。商品："+storageSummary.getGoodsid()+"，仓库："+storageSummary.getStorageid());
        }
		return i>0;
	}
	/**
	 * 在途量入库（采购入库单审核通过后，更新现存量等信息）
	 * @param storageSummaryBatch	入库信息
     * @param enterNum                 入库数量
	 * @param billmodel				单据类型
	 * @param billid				单据编号
	 * @param remark				备注
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 14, 2013
	 */
	public boolean enterStorageSummaryTransitnum(StorageSummaryBatch storageSummaryBatch,BigDecimal enterNum,String billmodel,String billid,String remark) throws Exception{
		GoodsInfo goodsInfo = getAllGoodsInfoByID(storageSummaryBatch.getGoodsid());
		if(null!=goodsInfo){
			if(StringUtils.isEmpty(storageSummaryBatch.getBarcode())){
				storageSummaryBatch.setBarcode(goodsInfo.getBarcode());
			}
			storageSummaryBatch.setBrandid(goodsInfo.getBrand());
			Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
			if(null!=brand){
				storageSummaryBatch.setBranddept(brand.getDeptid());
			}
		}
		boolean flag = false;
		StorageInfo storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
		//更新仓库现存量
		StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageSummaryBatch.getStorageid(), storageSummaryBatch.getGoodsid());
		
		storageSummaryBatch.setSummaryid(storageSummary.getId());
		StorageSummaryBatch storageSummaryBatch2 = null;
		//仓库和商品是批次管理时 生成新的批次量 更新仓库现存量
		//其中一个不是批次管理时， 当正常的商品入库
		if("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
			//商品进行库位管理时
			storageSummaryBatch2 = storageSummaryMapper.getStorageSummaryBatchByStorageidAndBatchno(storageSummaryBatch.getGoodsid(), storageSummaryBatch.getStorageid(), storageSummaryBatch.getBatchno());
		}else{
			storageSummaryBatch2 = storageSummaryMapper.getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(storageSummaryBatch.getStorageid(), storageSummaryBatch.getGoodsid());
			storageSummaryBatch.setBatchno("");
			storageSummaryBatch.setProduceddate("");
			storageSummaryBatch.setDeadline("");
		}
		if(null!=storageSummaryBatch2){
			//现存量更新日志
			addStorageSummaryReceiveLog(billmodel, billid, storageSummaryBatch2, enterNum, remark);
			int j = storageSummaryMapper.updateStorageSummaryBacthEnterTransitnumByID(storageSummaryBatch2.getId(), storageSummaryBatch2.getExistingnum(),enterNum,CommonUtils.getTodayDataStr(),storageSummaryBatch2.getVersion());
			flag = j>0;
		}else{
            storageSummaryBatch.setExistingnum(enterNum);
            storageSummaryBatch.setUsablenum(enterNum);
            storageSummaryBatch.setEnterdate(CommonUtils.getTodayDataStr());
			storageSummaryBatch.setSummaryid(storageSummary.getId());
			storageSummaryBatch.setId(CommonUtils.getDataNumberSendsWithRand());
			//现存量更新日志
			addStorageSummaryReceiveLog(billmodel, billid, storageSummaryBatch, enterNum, remark);
			int j = storageSummaryMapper.addStorageSummaryBatch(storageSummaryBatch);
			flag = j>0;
		}
        if(flag){
            //更新库存现存量
            int i = storageSummaryMapper.updateStorageSummaryEnterTransitnumByGoodsidAndStorageid(storageSummary.getStorageid(),storageSummary.getGoodsid(),storageSummary.getExistingnum(),enterNum,storageSummary.getVersion());
            flag = i>0;
        }
        if(!flag){
            throw new Exception("采购入库,回滚在途量失败。商品："+storageSummaryBatch.getGoodsid()+"，仓库："+storageSummaryBatch.getStorageid());
        }
        return flag;
	}
	/**
	 * 回滚入库操作 
	 * @param storageSummaryBatch       商品库存批次信息
     * @param enterNum                    入库数量
	 * @param billmodel                   单据类型
	 * @param billid                       单据编号
	 * @param remark                        备注
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 14, 2013
	 */
	public boolean rollBackEnterStorageSummaryTransitnum(StorageSummaryBatch storageSummaryBatch,BigDecimal enterNum,String billmodel,String billid,String remark) throws Exception{
		GoodsInfo goodsInfo = getAllGoodsInfoByID(storageSummaryBatch.getGoodsid());
		StorageInfo storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
		boolean flag = false;
		//更新仓库现存量
		StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageSummaryBatch.getStorageid(), storageSummaryBatch.getGoodsid());
		
		//商品是批次管理时 生成新的批次量 更新仓库现存量
		if("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
			//商品进行库位管理时
			StorageSummaryBatch storageSummaryBatch2 = storageSummaryMapper.getStorageSummaryBatchByStorageidAndBatchno(storageSummaryBatch.getGoodsid(), storageSummaryBatch.getStorageid(), storageSummaryBatch.getBatchno());
			if(storageSummaryBatch2.getUsablenum().compareTo(enterNum)!=-1){
				//现存量更新日志
				addStorageSummarySendLog(billmodel, billid, storageSummaryBatch2, enterNum, remark);
                String batchstate = "1";
                if(storageSummaryBatch2.getExistingnum().compareTo(enterNum)==0){
                    batchstate = "2";
                }
				int j = storageSummaryMapper.updateStorageSummaryBacthEnterTransitnumRollbackByID(storageSummaryBatch2.getId(),storageSummaryBatch2.getExistingnum(),enterNum,batchstate,storageSummaryBatch2.getVersion());
				flag = j>0;
			}
		}else{
			StorageSummaryBatch storageSummaryBatch2 = storageSummaryMapper.getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(storageSummaryBatch.getStorageid(), storageSummaryBatch.getGoodsid());
			if(storageSummaryBatch2.getUsablenum().compareTo(enterNum) != -1) {
                //现存量更新日志
                addStorageSummarySendLog(billmodel, billid, storageSummaryBatch2, enterNum, remark);
                String batchstate = "1";
                if(storageSummaryBatch2.getExistingnum().compareTo(enterNum)==0){
                    batchstate = "2";
                }
				int j = storageSummaryMapper.updateStorageSummaryBacthEnterTransitnumRollbackByID(storageSummaryBatch2.getId(),storageSummaryBatch2.getExistingnum(),enterNum,batchstate,storageSummaryBatch2.getVersion());
				flag = j>0;
			}
		}
		if(flag){
			//更新库存现存量
			int i = storageSummaryMapper.updateStorageSummaryEnterTransitnumRollbackByGoodsidAndStorageid(storageSummary.getStorageid(), storageSummary.getGoodsid(),storageSummary.getExistingnum(),enterNum,storageSummary.getVersion());
			flag = i>0;
		}
        if(!flag){
            throw new Exception("回滚出库更新现存量，可用量，在途量失败。商品："+storageSummaryBatch.getGoodsid()+"，仓库："+storageSummaryBatch.getStorageid());
        }
		return flag;
	}
	/**
	 * 商品入库
	 * @param storageSummaryBatch		退货入库信息
     * @param enterNum                    入库数量
	 * @param billmodel					相关单据业务	
	 * @param billid					单据编号
	 * @param remark					备注
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 18, 2013
	 */
	public Map addStorageSummaryNum(StorageSummaryBatch storageSummaryBatch,BigDecimal enterNum,String billmodel,String billid,String remark) throws Exception{
		Map map = new HashMap();
		String summarybatchid = null;
		GoodsInfo goodsInfo = getAllGoodsInfoByID(storageSummaryBatch.getGoodsid());
		StorageInfo storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
		boolean flag = false;
		if(null!=goodsInfo){
			storageSummaryBatch.setBarcode(goodsInfo.getBarcode());
			storageSummaryBatch.setBrandid(goodsInfo.getBrand());
			Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
			if(null!=brand){
				storageSummaryBatch.setBranddept(brand.getDeptid());
			}
			//仓库现存量
			StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageSummaryBatch.getStorageid(), storageSummaryBatch.getGoodsid());
			String summaryid = CommonUtils.getDataNumberSendsWithRand();
			if(null!=storageSummary){
				summaryid = storageSummary.getId();
			}

			StorageSummaryBatch oldbatch = null;
			//指定商品库存批次时 直接获取 不通过批次号获取
			if(StringUtils.isNotEmpty(storageSummaryBatch.getId())){
				oldbatch = getStorageSummaryBatchById(storageSummaryBatch.getId());
                //判断通过库存批次编号获取的库存批次数据 是否与需要变更的仓库数据一致
                if(null!=oldbatch ){
                    if(!storageSummaryBatch.getStorageid().equals(oldbatch.getStorageid())){
                        throw new RuntimeException("库存变更时，变更仓库与通过库存批次编号获取的仓库不一致。");
                    }
                }
			}else{
				//判断商品管理方式 批次管理 总量管理
				//仓库和商品如果是批次管理
				if("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
					//生产日期不为空的时候 通过生产日期获取库存批次
					if(StringUtils.isNotEmpty(storageSummaryBatch.getProduceddate())){
                        oldbatch = addOrGetStorageSummaryBatchByStorageidAndProduceddate(storageSummaryBatch.getStorageid(), storageSummaryBatch.getGoodsid(), storageSummaryBatch.getProduceddate());
					}else{
						//获取库存中存在的最早的批次库存
						oldbatch = getStorageSummaryBatchByStorageidNew(storageSummaryBatch.getStorageid(), storageSummaryBatch.getGoodsid());
					}
                    if(null!=oldbatch){
                        if(null==storageSummary){
                            summaryid = oldbatch.getSummaryid();
                        }
                        storageSummaryBatch.setBatchno(oldbatch.getBatchno());
                    }
				}else{
					//商品进行总量管理时
					oldbatch = getStorageSummaryBatchByStorageidAndGoodsid(storageSummaryBatch.getStorageid(), storageSummaryBatch.getGoodsid());
                    if(null!=oldbatch){
                        storageSummaryBatch.setId(oldbatch.getId());
                    }
					//不是批次管理是 不需要添加批次号与生产日期等
					storageSummaryBatch.setBatchno("");
					storageSummaryBatch.setProduceddate("");
					storageSummaryBatch.setDeadline("");
				}
			}
            if(StringUtils.isEmpty(storageSummaryBatch.getId())){
                storageSummaryBatch.setId(CommonUtils.getDataNumberSendsWithRand());
            }
            //添加入库日志
            addStorageSummaryReceiveLog(billmodel, billid, storageSummaryBatch, enterNum, remark);
			//更新或者添加批次现存量
			if(null!=oldbatch){
				storageSummaryMapper.updateStorageSummaryBacthEnterByID(oldbatch.getId(), oldbatch.getExistingnum(),enterNum,CommonUtils.getTodayDataStr(),oldbatch.getVersion());
				summarybatchid = oldbatch.getId();
			}else{
				storageSummaryBatch.setSummaryid(summaryid);
                if("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch()) && StringUtils.isNotEmpty(storageSummaryBatch.getProduceddate())){
                    Map batchnoMap = getBatchno(goodsInfo.getId(),storageSummaryBatch.getProduceddate());
                    if(null!=batchnoMap){
                        String batchno = (String) batchnoMap.get("batchno");
                        String deadline = (String) batchnoMap.get("deadline");
                        storageSummaryBatch.setBatchno(batchno);
                        storageSummaryBatch.setDeadline(deadline);
                    }
                }
                storageSummaryBatch.setExistingnum(enterNum);
                storageSummaryBatch.setUsablenum(enterNum);
                storageSummaryBatch.setIntinum(enterNum);
				storageSummaryMapper.addStorageSummaryBatch(storageSummaryBatch);
				summarybatchid = storageSummaryBatch.getId();
			}
			//该仓库存在该商品 更新该仓库现存量
			if(null!=storageSummary){
				int i = storageSummaryMapper.updateStorageSummaryEnterByGoodsidAndStorageid(storageSummary.getStorageid(),storageSummary.getGoodsid(),storageSummary.getExistingnum(),enterNum,storageSummary.getVersion());
				flag = i>0;
			}else{
				if(null==storageSummary){
					storageSummary = new StorageSummary();
					storageSummary.setId(summaryid);
				}
				storageSummary.setGoodsid(storageSummaryBatch.getGoodsid());
				storageSummary.setBrandid(goodsInfo.getBrand());
				if(null!=brand){
					storageSummary.setBranddept(brand.getDeptid());
				}
				storageSummary.setUnitid(storageSummaryBatch.getUnitid());
				storageSummary.setBarcode(storageSummaryBatch.getBarcode());
				storageSummary.setStorageid(storageSummaryBatch.getStorageid());
				storageSummary.setIstotalcontrol(storageInfo.getIstotalcontrol());
				storageSummary.setIssendstorage(storageInfo.getIssendstorage());
				// 现存量=批次现存量
				storageSummary.setExistingnum(enterNum);
				// 安全库存
				BigDecimal safenum = getStorageInventoryByGoodsidAndStorageid(storageSummary.getGoodsid(), storageSummary.getStorageid());
				//实际可用量 = 现存量-待发量-调拨待发量
				storageSummary.setUsablenum(enterNum);
				// 安全库存
				storageSummary.setSafenum(safenum);
				//在途量 = 当前在途量 + 新增的在途量
				storageSummary.setTransitnum(new BigDecimal(0));
				// 预计可用量=可用量+在途量+调拨待入量
				storageSummary.setProjectedusablenum(enterNum);
				//主单位信息
				storageSummary.setUnitid(goodsInfo.getMainunit());
				MeteringUnit meteringUnit = getMeteringUnitById(goodsInfo.getMainunit());
				if(null!=meteringUnit){
					storageSummary.setUnitname(meteringUnit.getName());
				}
				//默认辅单位信息
				MeteringUnit auxunit = getGoodsAuxUnitInfoByGoodsid(storageSummaryBatch.getGoodsid());
				if(null!=auxunit){
					storageSummary.setAuxunitid(auxunit.getId());
					storageSummary.setAuxunitname(auxunit.getName());
				}
				int i = storageSummaryMapper.addStorageSummary(storageSummary);
				flag = i>0;
			}
		}
		if(!flag){
		    throw new Exception("库存更新失败;"+"仓库："+storageSummaryBatch.getStorageid()+",商品："+storageSummaryBatch.getGoodsid());
        }
		map.put("flag", flag);
		map.put("summarybatchid", summarybatchid);
		return map;
	}
	
	/**
	 * 回滚销售退货入库
	 * @param storageSummaryBatch           商品库存批次信息
     * @param enterNum                        入库数量
	 * @param billmodel                       单据类型
	 * @param billid                            单据编号
	 * @param remark                            备注
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 27, 2013
	 */
	public boolean rollbackStorageSummaryNum(StorageSummaryBatch storageSummaryBatch,BigDecimal enterNum,String billmodel,String billid,String remark) throws Exception{
		GoodsInfo goodsInfo = getAllGoodsInfoByID(storageSummaryBatch.getGoodsid());
		StorageInfo storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
		boolean flag = false;
		if(null!=goodsInfo && null!=storageInfo){
			storageSummaryBatch.setBarcode(goodsInfo.getBarcode());
			storageSummaryBatch.setBrandid(goodsInfo.getBrand());
			Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
			if(null!=brand){
				storageSummaryBatch.setBranddept(brand.getDeptid());
			}
			//添加入库日志
			addStorageSummarySendLog(billmodel, billid, storageSummaryBatch, enterNum, remark);
			//仓库现存量
			StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageSummaryBatch.getStorageid(), storageSummaryBatch.getGoodsid());
			StorageSummaryBatch oldbatch = null;
			//指定商品库存批次时 直接获取 不通过批次号获取
			if(StringUtils.isNotEmpty(storageSummaryBatch.getId())){
				oldbatch = getStorageSummaryBatchById(storageSummaryBatch.getId());
                //判断通过库存批次编号获取的库存批次数据 是否与需要变更的仓库数据一致
                if(null!=oldbatch ){
                    if(!storageSummaryBatch.getStorageid().equals(oldbatch.getStorageid())){
                        throw new RuntimeException("库存变更时，变更仓库与通过库存批次编号获取的仓库不一致。");
                    }
                }
			}else{
				//判断商品管理方式 批次管理 总量管理
				//仓库和商品如果是批次管理
				if("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
					//生产日期不为空的时候 通过生产日期获取库存批次
					if(StringUtils.isNotEmpty(storageSummaryBatch.getProduceddate())){
						oldbatch = getStorageSummaryBatchByStorageidAndProduceddate(storageSummaryBatch.getStorageid(), storageSummaryBatch.getGoodsid(), storageSummaryBatch.getProduceddate());
					}else{
						//获取库存中存在的最早的批次库存
						oldbatch = getStorageSummaryBatchByStorageidNew(storageSummaryBatch.getStorageid(), storageSummaryBatch.getGoodsid());
					}
				}else{
					//商品进行总量管理时
					oldbatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(storageSummaryBatch.getStorageid(), storageSummaryBatch.getGoodsid());
				}
			}
			//更新或者添加批次现存量
			if(null!=oldbatch){
                //更新库存批次的状态
                if("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())) {
                    if (oldbatch.getExistingnum().compareTo(BigDecimal.ZERO) == 0) {
                        oldbatch.setBatchstate("2");
                    } else {
                        oldbatch.setBatchstate("1");
                    }
                }
				storageSummaryMapper.updateStorageSummaryBacthEnterRollbackByID(oldbatch.getId(), oldbatch.getExistingnum(),enterNum,oldbatch.getBatchstate(),oldbatch.getVersion());
				//该仓库存在该商品 更新该仓库现存量
				if(null!=storageSummary){
                    //更新仓库现存量 可用量 预计可用量
					int i = storageSummaryMapper.updateStorageSummaryEnterRollbackByGoodsidAndStorageid(storageSummary.getStorageid(),storageSummary.getGoodsid(),storageSummary.getExistingnum(),enterNum,storageSummary.getVersion());
					flag = i>0;
				}
			}
            if(!flag){
                throw new Exception("库存更新失败;"+"仓库："+storageSummaryBatch.getStorageid()+",商品："+storageSummaryBatch.getGoodsid());
            }
			return flag;
		}else{
			return flag;
		}
	}
	/**
	 * 更新调拨待发量
	 * @param summarybatchid            商品库存批次编号
	 * @param allotwaitnum              调拨待发数量
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	public Map updateStorageSummaryAllotwaitnum(String summarybatchid,BigDecimal allotwaitnum) throws Exception{
		StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(summarybatchid);
		if(null!=storageSummaryBatch && storageSummaryBatch.getUsablenum().compareTo(allotwaitnum)!=-1){
			//批次现存量需要出库
			//可用量 = 可用量-当前待发量
			storageSummaryBatch.setUsablenum(storageSummaryBatch.getUsablenum().subtract(allotwaitnum));
			//调拨待发量 = 调拨待发量+当前调拨量
			storageSummaryBatch.setAllotwaitnum(storageSummaryBatch.getAllotwaitnum().add(allotwaitnum));
			storageSummaryBatch.setBatchstate("1");
			StorageSummary storageSummary = storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid(storageSummaryBatch.getGoodsid(), storageSummaryBatch.getStorageid());
			//仓库现存量发生变化
			//仓库可用量 = 可用量 - 当前待发量
			storageSummary.setUsablenum(storageSummary.getUsablenum().subtract(allotwaitnum));
			//仓库调拨待发量 = 调拨待发量+ 当前调拨量
			storageSummary.setAllotwaitnum(storageSummary.getAllotwaitnum().add(allotwaitnum));
			//仓库预计可用量  = 预计可用量 - 当前调拨量
			storageSummary.setProjectedusablenum(storageSummary.getProjectedusablenum().subtract(allotwaitnum));
			//更新批次现存量和仓库现存量信息
			int i = storageSummaryMapper.updateStorageSummary(storageSummary);
			int j = storageSummaryMapper.updateStorageSummaryBacth(storageSummaryBatch);
            boolean flag = i > 0 && j > 0;
            if(!flag){
                throw new Exception("库存调拨待发量更新失败;"+"仓库："+storageSummaryBatch.getStorageid()+",商品："+storageSummaryBatch.getGoodsid());
            }
            Map map = new HashMap();
            map.put("flag", flag);
			return map;
		} else {
            Map map = new HashMap();
            map.put("flag", false);
            map.put("msg", storageSummaryBatch != null ? "商品[" + storageSummaryBatch.getGoodsid() + "]的数量超过可用量\n" : "");
			return map;
		}
	}
	/**
	 * 回滚调拨待发量
	 * @param summarybatchid            商品库存批次编号
	 * @param allotwaitnum              调拨待发数量
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	public boolean rollBackStorageSummaryAllotwaitnum(String summarybatchid,BigDecimal allotwaitnum) throws Exception{
		StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(summarybatchid);
		if(null!=storageSummaryBatch){
			//批次现存量需要出库
			//可用量 = 可用量+当前待发量
			storageSummaryBatch.setUsablenum(storageSummaryBatch.getUsablenum().add(allotwaitnum));
			//调拨待发量 = 调拨待发量-当前调拨量
			storageSummaryBatch.setAllotwaitnum(storageSummaryBatch.getAllotwaitnum().subtract(allotwaitnum));
			storageSummaryBatch.setBatchstate("1");
			StorageSummary storageSummary = storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid(storageSummaryBatch.getGoodsid(), storageSummaryBatch.getStorageid());
			//仓库现存量发生变化
			//仓库可用量 = 可用量 + 当前待发量
			storageSummary.setUsablenum(storageSummary.getUsablenum().add(allotwaitnum));
			//仓库调拨待发量 = 调拨待发量- 当前调拨量
			storageSummary.setAllotwaitnum(storageSummary.getAllotwaitnum().subtract(allotwaitnum));
			//仓库预计可用量  = 预计可用量 + 当前待发量
			storageSummary.setProjectedusablenum(storageSummary.getProjectedusablenum().add(allotwaitnum));
			//更新批次现存量和仓库现存量信息
			int i = storageSummaryMapper.updateStorageSummary(storageSummary);
			int j = storageSummaryMapper.updateStorageSummaryBacth(storageSummaryBatch);
            boolean flag = i > 0 && j > 0;
            if(!flag){
                throw new Exception("库存调拨待发量更新失败;"+"仓库："+storageSummaryBatch.getStorageid()+",商品："+storageSummaryBatch.getGoodsid());
            }
			return flag;
		}else{
			return false;
		}
	}
	/**
	 * 调拨出库，减去调拨待发量 生成仓库调拨入库量
	 * @param summarybatchid			调拨的批次现存量编号
	 * @param allotwaitnum				调拨待发数量
	 * @param realallotnum				实际调出数量
	 * @param enterStorageid			调入仓库
	 * @param enterstoragelocationid	调入库位
	 * @param enterproduceddate				入库生产日期
	 * @param billmodel					单据类型
	 * @param billid					单据编号
	 * @param remark					备注
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	public boolean sendStorageSummaryAllotwaitnum(String summarybatchid,BigDecimal allotwaitnum,BigDecimal realallotnum,String enterStorageid,String enterstoragelocationid,String enterproduceddate,String billmodel,String billid,String remark) throws Exception{
		StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(summarybatchid);
        GoodsInfo goodsInfo = getAllGoodsInfoByID(storageSummaryBatch.getGoodsid());
		boolean flag = false;
		if(null!=storageSummaryBatch){
			addStorageSummarySendLog(billmodel, billid, storageSummaryBatch, realallotnum, remark);
            storageSummaryBatch.setBatchstate("1");
            //商品批次数量为0是 标记关闭
            StorageInfo outStorage = getStorageInfoByID(storageSummaryBatch.getStorageid());
            if("1".equals(outStorage.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
                if(storageSummaryBatch.getExistingnum().compareTo(BigDecimal.ZERO)==0){
                    storageSummaryBatch.setBatchstate("2");
                }
            }
			StorageSummary storageSummary = storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid(storageSummaryBatch.getGoodsid(), storageSummaryBatch.getStorageid());
			int i = storageSummaryMapper.updateStorageSummaryAlloteSubtract(storageSummary.getStorageid(),storageSummary.getGoodsid(),storageSummary.getAllotwaitnum(),realallotnum,storageSummary.getVersion());
			int z = storageSummaryMapper.updateStorageSummaryBacthAlloteSubtract(storageSummaryBatch.getId(),storageSummaryBatch.getAllotwaitnum(),realallotnum,storageSummaryBatch.getBatchstate(),storageSummaryBatch.getVersion());
			if(i>0&&z>0){
				StorageSummary enterStorageSummay = storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid(storageSummaryBatch.getGoodsid(), enterStorageid);
                boolean addEnterStorageSummaryFlag = false;
                if(null==enterStorageSummay){
                    addEnterStorageSummaryFlag = true;
                    enterStorageSummay = new StorageSummary();
                    String summaryid = CommonUtils.getDataNumberSendsWithRand();
                    enterStorageSummay.setId(summaryid);
                }
				StorageInfo enterStorage = getStorageInfoByID(enterStorageid);
				StorageSummaryBatch storageSummaryBatch2 = null;
				//仓库和商品是批次管理时 生成新的批次量 更新仓库现存量
				if("1".equals(enterStorage.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
					storageSummaryBatch2 = addOrGetStorageSummaryBatchByStorageidAndProduceddate(enterStorageid, storageSummaryBatch.getGoodsid(), enterproduceddate);
				}else{
					storageSummaryBatch2 = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(enterStorageid, storageSummaryBatch.getGoodsid());
				}
				if(null!=storageSummaryBatch2){
					//现存量更新日志
					addStorageSummaryReceiveLog(billmodel, billid, storageSummaryBatch2,realallotnum, remark);
					storageSummaryMapper.updateStorageSummaryBacthAlloteAdd(storageSummaryBatch2.getId(),storageSummaryBatch2.getExistingnum(),realallotnum,storageSummaryBatch2.getVersion());
				}else{
					StorageSummaryBatch enterstorageSummaryBatch = new StorageSummaryBatch();
					enterstorageSummaryBatch.setId(CommonUtils.getDataNumberSendsWithRand());
					enterstorageSummaryBatch.setSummaryid(enterStorageSummay.getId());
					enterstorageSummaryBatch.setGoodsid(storageSummaryBatch.getGoodsid());
					enterstorageSummaryBatch.setBrandid(goodsInfo.getBrand());
					Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
					if(null!=brand){
						enterstorageSummaryBatch.setBranddept(brand.getDeptid());
					}
					enterstorageSummaryBatch.setBarcode(goodsInfo.getBarcode());
					enterstorageSummaryBatch.setStorageid(enterStorageid);
					//调入仓库和商品 是批次管理时 根据生产日期生成批次号与截止日期
					if("1".equals(enterStorage.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
						enterstorageSummaryBatch.setStoragelocationid(enterstoragelocationid);
						Map batchMap = getBatchno(goodsInfo.getId(), enterproduceddate);
						if(null!=batchMap){
							String batchno = (String) batchMap.get("batchno");
							String deadline = (String) batchMap.get("deadline");
							enterstorageSummaryBatch.setBatchno(batchno);
							enterstorageSummaryBatch.setProduceddate(enterproduceddate);
							enterstorageSummaryBatch.setDeadline(deadline);
						}
					}

					enterstorageSummaryBatch.setExistingnum(realallotnum);
					enterstorageSummaryBatch.setUsablenum(realallotnum);
					enterstorageSummaryBatch.setIntinum(new BigDecimal(0));
					
					enterstorageSummaryBatch.setUnitid(storageSummaryBatch.getUnitid());
					enterstorageSummaryBatch.setUnitname(storageSummaryBatch.getUnitname());
					enterstorageSummaryBatch.setAuxunitid(storageSummaryBatch.getAuxunitid());
					enterstorageSummaryBatch.setAuxunitname(storageSummaryBatch.getAuxunitname());
					enterstorageSummaryBatch.setPrice(storageSummaryBatch.getPrice());
					enterstorageSummaryBatch.setEnterdate(CommonUtils.getTodayDataStr());
					//现存量更新日志
					addStorageSummaryReceiveLog(billmodel, billid, enterstorageSummaryBatch, realallotnum, remark);
					storageSummaryMapper.addStorageSummaryBatch(enterstorageSummaryBatch);
				}

                if(!addEnterStorageSummaryFlag){
                    int y = storageSummaryMapper.updateStorageSummaryAlloteAdd(enterStorageSummay.getStorageid(),enterStorageSummay.getGoodsid(),enterStorageSummay.getExistingnum(),realallotnum,enterStorageSummay.getVersion());
                    flag = y>0;
                }else{

                    enterStorageSummay.setGoodsid(storageSummaryBatch.getGoodsid());
                    enterStorageSummay.setBrandid(goodsInfo.getBrand());
                    Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                    if(null!=brand){
                        enterStorageSummay.setBranddept(brand.getDeptid());
                    }
                    enterStorageSummay.setBarcode(goodsInfo.getBarcode());
                    enterStorageSummay.setStorageid(enterStorageid);
                    StorageInfo storageInfo = getStorageInfoByID(enterStorageid);
                    enterStorageSummay.setIstotalcontrol(storageInfo.getIstotalcontrol());
                    enterStorageSummay.setIssendstorage(storageInfo.getIssendstorage());
                    // 安全库存
                    BigDecimal safenum = getStorageInventoryByGoodsidAndStorageid(storageSummaryBatch.getGoodsid(), enterStorageid);

                    // 安全库存
                    enterStorageSummay.setSafenum(safenum);
                    //仓库现存量 = 调拨量
                    enterStorageSummay.setExistingnum(realallotnum);
                    //仓库可用量  = 调拨量
                    enterStorageSummay.setUsablenum(realallotnum);
                    //仓库预计可用量 = 调拨量
                    enterStorageSummay.setProjectedusablenum(realallotnum);
                    enterStorageSummay.setCostprice(storageSummary.getCostprice());
                    //主单位信息
                    enterStorageSummay.setUnitid(goodsInfo.getMainunit());
                    MeteringUnit meteringUnit = getMeteringUnitById(goodsInfo.getMainunit());
                    if(null!=meteringUnit){
                        enterStorageSummay.setUnitname(meteringUnit.getName());
                    }
                    //默认辅单位信息
                    MeteringUnit auxunit = getGoodsAuxUnitInfoByGoodsid(storageSummaryBatch.getGoodsid());
                    if(null!=auxunit){
                        enterStorageSummay.setAuxunitid(auxunit.getId());
                        enterStorageSummay.setAuxunitname(auxunit.getName());
                    }
                    // 添加库存现存量总量信息
                    int y = storageSummaryMapper.addStorageSummary(enterStorageSummay);
                    flag = y>0;
                }
			}
		}
        if(!flag){
            throw new Exception("库存调拨待发量更新失败;"+"仓库："+storageSummaryBatch.getStorageid()+",商品："+storageSummaryBatch.getGoodsid());
        }
		return flag;
	}

    /**
     * 调拨后 更新仓库成本价（需要在库存数量更新后执行）
     * @param billid                 单据编号
     * @param detailid               明细编号
     * @param outStorageid          出库仓库
     * @param inStorageid           入库仓库
     * @param goodsid               商品编号
     * @param price                 调拨出库价格
	 * @param cprice                 调拨成本价
     * @param unitnum               调拨出库数量
     * @return
     * @throws Exception
     */
    public boolean updateStorageCostpriceByAllot(String billid,String detailid,String outStorageid,String inStorageid,String goodsid,BigDecimal price,BigDecimal cprice,BigDecimal unitnum) throws Exception{
        GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
        if(null==goodsInfo){
            throw new Exception("找不到商品："+goodsid+"，回滚数据。");
        }
        //更新出库仓库成本价
        StorageSummary outStorageSummary = getStorageSummaryByStorageidAndGoodsid(outStorageid,goodsid);
		//出库使用成本价计算
		BigDecimal outAmount = unitnum.multiply(cprice);
        BigDecimal outStorageAmount = outStorageSummary.getExistingnum().add(unitnum).multiply(outStorageSummary.getCostprice()).subtract(outAmount);
        //库存为0时，成本价为最新成本价 库存金额为剩余的金额
        int i = 0;
        if(outStorageSummary.getExistingnum().compareTo(BigDecimal.ZERO)!=0){
            //调拨已经库存变更了 成本价=（当前库存金额-调拨出库金额）/当前数量
            BigDecimal newOutStoragePrice = outStorageAmount.divide(outStorageSummary.getExistingnum(),6,BigDecimal.ROUND_HALF_UP);
            //更新仓库成本价
            i = getStorageSummaryMapper().updateStorageGoodsCostprice(outStorageid, goodsid, outStorageSummary.getExistingnum(),newOutStoragePrice,BigDecimal.ZERO);
        }else{
            //更新仓库成本价
            i = getStorageSummaryMapper().updateStorageGoodsCostprice(outStorageid, goodsid, outStorageSummary.getExistingnum(),outStorageSummary.getCostprice(),outStorageAmount);
        }
        //调拨入库 成本价
        StorageSummary inStorageSummary = getStorageSummaryByStorageidAndGoodsid(inStorageid,goodsid);
        BigDecimal oldUnitnum = inStorageSummary.getExistingnum().subtract(unitnum);
		//入库使用调拨单价计算
		BigDecimal enterAmount = unitnum.multiply(price);
        BigDecimal inStorageAmount = oldUnitnum.multiply(inStorageSummary.getCostprice()).add(enterAmount);

        BigDecimal newInStoragePrice = BigDecimal.ZERO;
        if(BigDecimal.ZERO.compareTo(inStorageSummary.getExistingnum()) != 0) {
			newInStoragePrice = inStorageAmount.divide(inStorageSummary.getExistingnum(),6,BigDecimal.ROUND_HALF_UP);
		}
        //更新仓库成本价
        int j = getStorageSummaryMapper().updateStorageGoodsCostprice(inStorageid, goodsid, inStorageSummary.getExistingnum(),newInStoragePrice,inStorageAmount);
        if(i>0 && j>0){
            //出库仓库或者入库仓库不是参与总量控制的话 需要更新商品总的成本价
            StorageInfo outStorage = getStorageInfoByID(outStorageid);
            StorageInfo inStorage = getStorageInfoByID(inStorageid);
            if(!"1".equals(outStorage.getIstotalcontrol()) || !"1".equals(inStorage.getIstotalcontrol())){
                //成本价变更记录 公式
                String costChangeRemark = "调拨造成成本价变更，调出仓库或调入仓库不参与总量控制，单据："+billid;
                StorageSummary storageSummary = getStorageSummaryMapper().getStorageSummarySumByGoodsidInTotal(goodsInfo.getId());
                BigDecimal totalAmount = BigDecimal.ZERO;
                if (null != storageSummary) {
                    if ("1".equals(outStorage.getIstotalcontrol())) {
                        BigDecimal eNum = storageSummary.getExistingnum().add(unitnum);
                        totalAmount = eNum.multiply(goodsInfo.getNewstorageprice()).setScale(6, BigDecimal.ROUND_HALF_UP);
                        totalAmount = totalAmount.subtract(enterAmount);
                        costChangeRemark +="，调入仓库不参与总量控制，成本=（（当前库存数量+调拨数量）*当前成本价-调拨金额）/当前库存数量"
                                +"(("+storageSummary.getExistingnum().toString()+"+"+unitnum.toString()+")*"+goodsInfo.getNewstorageprice().toString()+"-("+unitnum.toString()+"*"+price.toString()+"))/"+storageSummary.getExistingnum().toString();
                    }else{
                        BigDecimal eNum = storageSummary.getExistingnum().subtract(unitnum);
                        totalAmount = eNum.multiply(goodsInfo.getNewstorageprice()).setScale(6, BigDecimal.ROUND_HALF_UP);
                        totalAmount = totalAmount.add(outAmount);
                        costChangeRemark +="，调出仓库不参与总量控制，成本=（（当前库存数量-调拨数量）*当前成本价+调拨金额）/当前库存数量"
                                +"(("+storageSummary.getExistingnum().toString()+"-"+unitnum.toString()+")*"+goodsInfo.getNewstorageprice().toString()+"+("+unitnum.toString()+"*"+price.toString()+"))/"+storageSummary.getExistingnum().toString();
                    }
                    BigDecimal costprice = goodsInfo.getNewstorageprice();
					if (storageSummary.getExistingnum().compareTo(BigDecimal.ZERO) != 0) {
						costprice = totalAmount.divide(storageSummary.getExistingnum(), 6, BigDecimal.ROUND_HALF_UP);
					}else{
						if(totalAmount.compareTo(BigDecimal.ZERO)!=0){
							if ("1".equals(outStorage.getIstotalcontrol())) {
								addCostDiffAmountShare("3", billid, detailid, outStorageid, goodsid, totalAmount, "调拨0库存差额");
							}
						}
					}
					GoodsInfo upGoodsInfo = new GoodsInfo();
					upGoodsInfo.setId(goodsInfo.getId());
					//更新商品成本价
					upGoodsInfo.setNewstorageprice(costprice);
					//更新商品成本价
					updateGoodsCostprice(upGoodsInfo,costChangeRemark,billid);
                }
            }
            return true;
        }else{
            throw new Exception("仓库成本价更新失败，回滚数据。");
        }
    }
    /**
	 * 直接调拨出库 不减去调拨待发量 生成仓库调入量 （调拨出库单无来源类型） 
	 * @param summarybatchid            商品库存批次编号
	 * @param allotwaitnum              调拨待发数量
	 * @param enterStorageid            入库仓库编号
	 * @param billmodel                 单据类型
	 * @param billid                    单据编号
	 * @param remark                    备注
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 29, 2013
	 */
	public boolean sendStorageSummaryAllotnum(String summarybatchid,BigDecimal allotwaitnum,String enterStorageid,String billmodel,String billid,String remark) throws Exception{
		StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(summarybatchid);
		boolean flag = false;
		if(null!=storageSummaryBatch){
			addStorageSummarySendLog(billmodel, billid, storageSummaryBatch, allotwaitnum, remark);
			//现存量 = 当前现存量 -调拨量
			storageSummaryBatch.setExistingnum(storageSummaryBatch.getExistingnum().subtract(allotwaitnum));
			storageSummaryBatch.setUsablenum(storageSummaryBatch.getUsablenum().subtract(allotwaitnum));
			storageSummaryBatch.setBatchstate("1");
			StorageSummary storageSummary = storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid(storageSummaryBatch.getGoodsid(), storageSummaryBatch.getStorageid());
			//仓库现存量发生变化 = 当前现存量 - 调拨量
			storageSummary.setExistingnum(storageSummary.getExistingnum().subtract(allotwaitnum));
			storageSummary.setUsablenum(storageSummary.getUsablenum().subtract(allotwaitnum));
			storageSummary.setProjectedusablenum(storageSummary.getProjectedusablenum().subtract(allotwaitnum));
			//更新批次现存量和仓库现存量信息
			int i = storageSummaryMapper.updateStorageSummary(storageSummary);
			int y = storageSummaryMapper.updateStorageSummaryBacth(storageSummaryBatch);
			
			if(i>0&&y>0){
				GoodsInfo goodsInfo = getAllGoodsInfoByID(storageSummaryBatch.getGoodsid());
				StorageSummary enterStorageSummay = storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid(storageSummaryBatch.getGoodsid(), enterStorageid);
				if(null!=enterStorageSummay){
					//仓库现存量 = 当前现存量 + 调拨量
					enterStorageSummay.setExistingnum(enterStorageSummay.getExistingnum().add(allotwaitnum));
					//仓库可用量 = 当前可用量 + 调拨量
					enterStorageSummay.setUsablenum(enterStorageSummay.getUsablenum().add(allotwaitnum));
					//仓库预计可用量  = 预计可用量 + 当前待发量
					enterStorageSummay.setProjectedusablenum(enterStorageSummay.getProjectedusablenum().add(allotwaitnum));
					int z = storageSummaryMapper.updateStorageSummary(enterStorageSummay);
					flag = z>0;
				}else{
					enterStorageSummay = new StorageSummary();
					String summaryid = CommonUtils.getDataNumberSendsWithRand();
					enterStorageSummay.setId(summaryid);
					enterStorageSummay.setGoodsid(storageSummaryBatch.getGoodsid());
					enterStorageSummay.setBrandid(goodsInfo.getBrand());
					Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
					if(null!=brand){
						enterStorageSummay.setBranddept(brand.getDeptid());
					}
					enterStorageSummay.setBarcode(goodsInfo.getBarcode());
					enterStorageSummay.setStorageid(enterStorageid);
					StorageInfo storageInfo = getStorageInfoByID(enterStorageid);
					if(null!=storageInfo){
						enterStorageSummay.setIssendstorage(storageInfo.getIssendstorage());
						enterStorageSummay.setIstotalcontrol(storageInfo.getIstotalcontrol());
					}
					// 安全库存
					BigDecimal safenum = getStorageInventoryByGoodsidAndStorageid(storageSummaryBatch.getGoodsid(), enterStorageid);
					
					// 安全库存
					enterStorageSummay.setSafenum(safenum);
					//仓库现存量 = 当前现存量 + 调拨量
					enterStorageSummay.setExistingnum(allotwaitnum);
					//仓库可用量 = 当前可用量 + 调拨量
					enterStorageSummay.setUsablenum(allotwaitnum);
					// 预计可用量=调拨量
					enterStorageSummay.setProjectedusablenum(allotwaitnum);
					//主单位信息
					enterStorageSummay.setUnitid(goodsInfo.getMainunit());
					MeteringUnit meteringUnit = getMeteringUnitById(goodsInfo.getMainunit());
					if(null!=meteringUnit){
						enterStorageSummay.setUnitname(meteringUnit.getName());
					}
					//默认辅单位信息
					MeteringUnit auxunit = getGoodsAuxUnitInfoByGoodsid(storageSummaryBatch.getGoodsid());
					if(null!=auxunit){
						enterStorageSummay.setAuxunitid(auxunit.getId());
						enterStorageSummay.setAuxunitname(auxunit.getName());
					}
					// 添加库存现存量总量信息
					int z = storageSummaryMapper.addStorageSummary(enterStorageSummay);
					flag = z>0;
				}
				StorageSummaryBatch enterstorageSummaryBatch = new StorageSummaryBatch();
				enterstorageSummaryBatch.setId(CommonUtils.getDataNumberSendsWithRand());
				enterstorageSummaryBatch.setSummaryid(enterStorageSummay.getId());
				enterstorageSummaryBatch.setGoodsid(storageSummaryBatch.getGoodsid());
				enterstorageSummaryBatch.setBrandid(goodsInfo.getBrand());
				Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
				if(null!=brand){
					enterstorageSummaryBatch.setBranddept(brand.getDeptid());
				}
				enterstorageSummaryBatch.setBarcode(goodsInfo.getBarcode());
				enterstorageSummaryBatch.setStorageid(enterStorageid);
				enterstorageSummaryBatch.setStoragelocationid(storageSummaryBatch.getStoragelocationid());
				enterstorageSummaryBatch.setBatchno(storageSummaryBatch.getBatchno());
				enterstorageSummaryBatch.setProduceddate(storageSummaryBatch.getProduceddate());
				enterstorageSummaryBatch.setDeadline(storageSummaryBatch.getDeadline());
				
				enterstorageSummaryBatch.setExistingnum(allotwaitnum);
				enterstorageSummaryBatch.setUsablenum(allotwaitnum);
				enterstorageSummaryBatch.setIntinum(new BigDecimal(0));
				
				enterstorageSummaryBatch.setUnitid(storageSummaryBatch.getUnitid());
				enterstorageSummaryBatch.setUnitname(storageSummaryBatch.getUnitname());
				enterstorageSummaryBatch.setAuxunitid(storageSummaryBatch.getAuxunitid());
				enterstorageSummaryBatch.setAuxunitname(storageSummaryBatch.getAuxunitname());
				enterstorageSummaryBatch.setPrice(storageSummaryBatch.getPrice());
				
				enterstorageSummaryBatch.setEnterdate(CommonUtils.getTodayDataStr());
				
				//商品是批次管理时 生成新的批次量 更新仓库现存量
				if("1".equals(goodsInfo.getIsbatch())){
					StorageSummaryBatch storageSummaryBatch1 = getStorageSummaryBatchByStorageidAndBatchno(enterStorageid, enterstorageSummaryBatch.getBatchno(), enterstorageSummaryBatch.getGoodsid());
					if(null==storageSummaryBatch1){
						//现存量更新日志
						addStorageSummaryReceiveLog(billmodel, billid, enterstorageSummaryBatch, allotwaitnum, remark);
						storageSummaryMapper.addStorageSummaryBatch(enterstorageSummaryBatch);
					}else{
						//现存量更新日志
						addStorageSummaryReceiveLog(billmodel, billid, storageSummaryBatch1, allotwaitnum, remark);
						storageSummaryBatch1.setExistingnum(storageSummaryBatch1.getExistingnum().add(allotwaitnum));
						storageSummaryBatch1.setUsablenum(storageSummaryBatch1.getUsablenum().add(allotwaitnum));
						storageSummaryBatch1.setEnterdate(enterstorageSummaryBatch.getEnterdate());
						storageSummaryMapper.updateStorageSummaryBacth(storageSummaryBatch1);
					}
				}else{
					StorageSummaryBatch storageSummaryBatch2 = storageSummaryMapper.getStorageSummaryBatchByStorageidAndGoodsid(enterstorageSummaryBatch.getStorageid(), enterstorageSummaryBatch.getGoodsid());
					if(null!=storageSummaryBatch2){
						//现存量更新日志
						addStorageSummaryReceiveLog(billmodel, billid, storageSummaryBatch2, allotwaitnum, remark);
						//现存量 = 当前现存量+调拨量
						storageSummaryBatch2.setExistingnum(storageSummaryBatch2.getExistingnum().add(allotwaitnum));
						//可用量 = 可用量+调拨量
						storageSummaryBatch2.setUsablenum(storageSummaryBatch2.getUsablenum().add(allotwaitnum));
						storageSummaryBatch2.setEnterdate(enterstorageSummaryBatch.getEnterdate());
						storageSummaryMapper.updateStorageSummaryBacth(storageSummaryBatch2);
					}else{
						//现存量更新日志
						addStorageSummaryReceiveLog(billmodel, billid, enterstorageSummaryBatch, allotwaitnum, remark);
						storageSummaryMapper.addStorageSummaryBatch(enterstorageSummaryBatch);
					}
				}
			}
		}
		return flag;
	}
	/**
	 * 回滚调拨出库操作（调拨出库单来源调拨通知单）
	 * 已废弃
	 * @param summarybatchid			调拨的批次现存量编号
	 * @param allotwaitnum				调拨数量(调拨通知单中调拨待发数量)
	 * @param realallotnum 				实际调拨出库数量
	 * @param enterStorageid			调入仓库
	 * @param billmodel					单据类型
	 * @param billid					单据编号
	 * @param remark					备注
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	public boolean rollBackSendStorageSummaryAllotwaitnum(String summarybatchid,BigDecimal allotwaitnum,BigDecimal realallotnum,String enterStorageid,String billmodel,String billid,String remark) throws Exception{
		StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(summarybatchid);
		boolean flag = false;
		if(null!=storageSummaryBatch){
			//仓库商品数量发生改变日志
			addStorageSummaryReceiveLog(billmodel, billid, storageSummaryBatch, realallotnum, remark);
			//现存量 = 当前现存量 +实际调拨量
			storageSummaryBatch.setExistingnum(storageSummaryBatch.getExistingnum().add(realallotnum));
			//调拨待发量 = 调拨待发量+当前调拨量
			storageSummaryBatch.setAllotwaitnum(storageSummaryBatch.getAllotwaitnum().add(allotwaitnum));
			storageSummaryBatch.setUsablenum(storageSummaryBatch.getUsablenum().subtract(allotwaitnum.subtract(realallotnum)));
			storageSummaryBatch.setBatchstate("1");
			StorageSummary storageSummary = storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid(storageSummaryBatch.getGoodsid(), storageSummaryBatch.getStorageid());
			//仓库现存量发生变化 = 当前现存量 + 调拨量
			storageSummary.setExistingnum(storageSummary.getExistingnum().add(realallotnum));
			storageSummary.setUsablenum(storageSummary.getUsablenum().subtract(allotwaitnum.subtract(realallotnum)));
			//仓库调拨待发量 = 调拨待发量+ 当前调拨量
			storageSummary.setAllotwaitnum(storageSummary.getAllotwaitnum().add(allotwaitnum));
			//仓库预计可用量  = 预计可用量 + 当前待发量
			storageSummary.setProjectedusablenum(storageSummary.getProjectedusablenum().add(realallotnum).subtract(allotwaitnum));
			//更新批次现存量和仓库现存量信息
			int i = storageSummaryMapper.updateStorageSummary(storageSummary);
			int j = storageSummaryMapper.updateStorageSummaryBacth(storageSummaryBatch);
			//调入仓库现存量回滚
			StorageSummary enterStorageSummay = storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid(storageSummaryBatch.getGoodsid(), enterStorageid);
			//仓库调拨待入量 = 当前待入量 - 调拨量
			enterStorageSummay.setAllotenternum(enterStorageSummay.getAllotenternum().subtract(realallotnum));
			//仓库预计可用量  = 预计可用量 - 当前待发量
			enterStorageSummay.setProjectedusablenum(enterStorageSummay.getProjectedusablenum().subtract(realallotnum));
			int z = storageSummaryMapper.updateStorageSummary(enterStorageSummay);
			flag = i>0&&j>0&&z>0;
		}
		return flag;
	}
	/**
	 * 回滚调拨出库（调拨出库单无来源类型）
	 * 已废弃
	 * @param summarybatchid            商品库存批次编号
	 * @param allotwaitnum              调拨待发数量
	 * @param enterStorageid            入库仓库编号
	 * @param billmodel                 单据类型
	 * @param billid                    单据编号
	 * @param remark                    备注
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 29, 2013
	 */
	public boolean rollBackSendStorageSummaryAllotoutnum(String summarybatchid,BigDecimal allotwaitnum,String enterStorageid,String billmodel,String billid,String remark) throws Exception{
		StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(summarybatchid);
		boolean flag = false;
		if(null!=storageSummaryBatch){
			//仓库商品数量发生改变日志
			addStorageSummaryReceiveLog(billmodel, billid, storageSummaryBatch, allotwaitnum, remark);
			//现存量 = 当前现存量 +调拨量
			storageSummaryBatch.setExistingnum(storageSummaryBatch.getExistingnum().add(allotwaitnum));
			//可用量 = 当前可用量 + 调拨量
			storageSummaryBatch.setUsablenum(storageSummaryBatch.getUsablenum().add(allotwaitnum));
			storageSummaryBatch.setBatchstate("1");
			StorageSummary storageSummary = storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid(storageSummaryBatch.getGoodsid(), storageSummaryBatch.getStorageid());
            //调入仓库现存量回滚
            StorageSummary enterStorageSummay = storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid(storageSummaryBatch.getGoodsid(), enterStorageid);
            //仓库现存量发生变化 = 当前现存量 + 调拨量
			storageSummary.setExistingnum(storageSummary.getExistingnum().add(allotwaitnum));
			//可用量 = 当前可用量 + 调拨量
			storageSummary.setUsablenum(storageSummary.getUsablenum().add(allotwaitnum));
			//仓库预计可用量  = 预计可用量 + 当前待发量
			storageSummary.setProjectedusablenum(storageSummary.getProjectedusablenum().add(allotwaitnum));
            //更新仓库的商品成本价  仓库成本独立核算
            if(null!=storageSummary){
                //仓库最新数量
                BigDecimal newStoragenum = BigDecimal.ZERO;
                BigDecimal oldStoragenum = BigDecimal.ZERO;
                BigDecimal totalStorageAmount = BigDecimal.ZERO;
                if(null!=storageSummary){
                    newStoragenum = storageSummary.getExistingnum();
                    oldStoragenum = storageSummary.getExistingnum().subtract(allotwaitnum);
                    totalStorageAmount = oldStoragenum.multiply(storageSummary.getCostprice()).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                BigDecimal enterStorageAmount = allotwaitnum.multiply(enterStorageSummay.getCostprice());
                totalStorageAmount = totalStorageAmount.add(enterStorageAmount);
                BigDecimal storageCostprice = storageSummary.getCostprice();
                if(newStoragenum.compareTo(BigDecimal.ZERO)==1){
                    storageCostprice = totalStorageAmount.divide(newStoragenum,6,BigDecimal.ROUND_HALF_UP);
                }
                storageSummary.setCostprice(storageCostprice);
            }
			//更新批次现存量和仓库现存量信息
			int i = storageSummaryMapper.updateStorageSummary(storageSummary);
			int j = storageSummaryMapper.updateStorageSummaryBacth(storageSummaryBatch);
            //更新仓库的商品成本价  仓库成本独立核算
            if(null!=enterStorageSummay){
                //仓库最新数量
                BigDecimal newStoragenum = BigDecimal.ZERO;
                BigDecimal oldStoragenum = BigDecimal.ZERO;
                BigDecimal totalStorageAmount = BigDecimal.ZERO;
                if(null!=enterStorageSummay){
                    newStoragenum = enterStorageSummay.getExistingnum();
                    oldStoragenum = enterStorageSummay.getExistingnum().add(allotwaitnum);
                    totalStorageAmount = oldStoragenum.multiply(enterStorageSummay.getCostprice()).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                BigDecimal enterStorageAmount = allotwaitnum.multiply(storageSummary.getCostprice());
                totalStorageAmount = totalStorageAmount.add(enterStorageAmount);
                BigDecimal storageCostprice = enterStorageSummay.getCostprice();
                if(newStoragenum.compareTo(BigDecimal.ZERO)==1){
                    storageCostprice = totalStorageAmount.divide(newStoragenum,6,BigDecimal.ROUND_HALF_UP);
                }
                enterStorageSummay.setCostprice(storageCostprice);
            }
			//仓库调拨待入量 = 当前待入量 - 调拨量
			enterStorageSummay.setAllotenternum(enterStorageSummay.getAllotenternum().subtract(allotwaitnum));
			//仓库预计可用量  = 预计可用量 - 当前待发量
			enterStorageSummay.setProjectedusablenum(enterStorageSummay.getProjectedusablenum().subtract(allotwaitnum));

			int z = storageSummaryMapper.updateStorageSummary(enterStorageSummay);
			flag = i>0&&j>0&&z>0;
		}
		return flag;
	}
	/**
	 * 仓库调拨待入量入库
	 * @param storageSummaryBatch       商品库存批次信息
	 * @param billmodel                  单据类型
	 * @param billid                      单据编号
	 * @param remark                       备注
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public boolean enterStorageSummaryAllotenternum(StorageSummaryBatch storageSummaryBatch,String billmodel,String billid,String remark) throws Exception{
		GoodsInfo goodsInfo = getAllGoodsInfoByID(storageSummaryBatch.getGoodsid());
		if(null!=goodsInfo){
			storageSummaryBatch.setBarcode(goodsInfo.getBarcode());
			storageSummaryBatch.setBrandid(goodsInfo.getBrand());
			Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
			if(null!=brand){
				storageSummaryBatch.setBranddept(brand.getDeptid());
			}
		}
		boolean flag = false;
		//更新仓库现存量
		StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageSummaryBatch.getStorageid(), storageSummaryBatch.getGoodsid());
		
		//商品是批次管理时 生成新的批次量 更新仓库现存量
		if("1".equals(goodsInfo.getIsbatch())){
			//现存量更新日志
			addStorageSummaryReceiveLog(billmodel, billid, storageSummaryBatch, storageSummaryBatch.getExistingnum(), remark);
			int j = storageSummaryMapper.addStorageSummaryBatch(storageSummaryBatch);
			flag = j>0;
		}else{
			StorageSummaryBatch storageSummaryBatch2 = storageSummaryMapper.getStorageSummaryBatchByStorageidAndGoodsid(storageSummaryBatch.getStorageid(), storageSummaryBatch.getGoodsid());
			if(null!=storageSummaryBatch2){
				//现存量更新日志
				addStorageSummaryReceiveLog(billmodel, billid, storageSummaryBatch2, storageSummaryBatch.getExistingnum(), remark);
				//现存量 = 当前现存量+调拨量
				storageSummaryBatch2.setExistingnum(storageSummaryBatch2.getExistingnum().add(storageSummaryBatch.getExistingnum()));
				//可用量 = 可用量+调拨量
				storageSummaryBatch2.setUsablenum(storageSummaryBatch2.getUsablenum().add(storageSummaryBatch.getExistingnum()));
				storageSummaryBatch2.setEnterdate(storageSummaryBatch.getEnterdate());
				int j = storageSummaryMapper.updateStorageSummaryBacth(storageSummaryBatch2);
				flag = j>0;
			}else{
				storageSummaryBatch.setSummaryid(storageSummary.getId());
				//现存量更新日志
				addStorageSummaryReceiveLog(billmodel, billid, storageSummaryBatch, storageSummaryBatch.getExistingnum(), remark);
				int j = storageSummaryMapper.addStorageSummaryBatch(storageSummaryBatch);
				flag = j>0;
			}
		}
		//现存量 = 当前现存量+ 调拨量
		storageSummary.setExistingnum(storageSummary.getExistingnum().add(storageSummaryBatch.getExistingnum()));
		// 可用量=当前可用量 + 调拨量
		BigDecimal realUseNum = storageSummary.getUsablenum().add(storageSummaryBatch.getExistingnum());
		storageSummary.setUsablenum(realUseNum);
		//调拨待入量 = 当前调拨待入量-调拨量
		storageSummary.setAllotenternum(storageSummary.getAllotenternum().subtract(storageSummaryBatch.getExistingnum()));
		//更新库存现存量
		storageSummaryMapper.updateStorageSummary(storageSummary);
		return flag;
	}

    /**
     * 判断采购出入库后的成本价 是否在限制范围内
     * @param compareCostprice
     * @param newBuyprice
     * @return
     * @throws Exception
     */
    public boolean isPurcahseUpdateCostpriceInLimit(String storageid,GoodsInfo goodsInfo,BigDecimal compareCostprice,BigDecimal newBuyprice) throws Exception{
        //仓库独立核算时 如果仓库独立核算时 成本价上下限不进行判断
        String isStorageAccount = getStorageIsAccount(storageid);
        if("1".equals(isStorageAccount)){
            return true;
        }
        //1取单据中的采购价
        //2取最新采购价
        //3取采购价
        //4不判断
        String purchaseComparePrice = getSysParamValue("PurchaseComparePrice");
        if(StringUtils.isEmpty(purchaseComparePrice)){
            purchaseComparePrice = "1";
        }
        if("4".equals(purchaseComparePrice)){
            return true;
        }
        //计算后成本价小于0时，也不调整成本
        if(null!=compareCostprice && compareCostprice.compareTo(BigDecimal.ZERO)==-1){
            return false;
        }
        boolean isUpdateCostprice = true;
        if(null!=goodsInfo){
            BigDecimal buypriceUp = BigDecimal.ZERO;
            BigDecimal buypriceLower = BigDecimal.ZERO;

            if(newBuyprice.compareTo(BigDecimal.ZERO)==0){
                newBuyprice = goodsInfo.getNewbuyprice();
            }

            if("1".equals(purchaseComparePrice)){
            }else if("2".equals(purchaseComparePrice)){
                newBuyprice = goodsInfo.getNewbuyprice();
            }else if("3".equals(purchaseComparePrice)){
                newBuyprice = goodsInfo.getHighestbuyprice();
            }
            //采购价上限 按百分比
            String costChangeUpLimit = getSysParamValue("CostChangeUpLimit");
            //采购价下限 按百分比
            String costChangeLowerLimit = getSysParamValue("CostChangeLowerLimit");
            //当成本价与最新采购价差异超过系统参数设置的区间时
            //本次入库先按最新采购价入库
            //上下限计算公式 上限：采购价+百分比上限 下限：采购价+百分比下限
            if(StringUtils.isNotEmpty(costChangeUpLimit) && StringUtils.isNumeric(costChangeUpLimit)){
                BigDecimal upLimit = new BigDecimal(costChangeUpLimit);
                if(upLimit.compareTo(BigDecimal.ZERO)==1){
                    buypriceUp = newBuyprice.multiply(BigDecimal.ONE.add(upLimit.divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP)));
                }else{
                    buypriceUp = newBuyprice;
                }
            }
            if(StringUtils.isNotEmpty(costChangeLowerLimit) && StringUtils.isNumeric(costChangeLowerLimit)){
                BigDecimal lowerLimit = new BigDecimal(costChangeLowerLimit);
                if(lowerLimit.compareTo(new BigDecimal(100))==-1){
                    buypriceLower = newBuyprice.multiply(BigDecimal.ONE.subtract(lowerLimit.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP)));
                }else{
                    buypriceLower = BigDecimal.ZERO;
                }
            }
            if(buypriceUp.compareTo(BigDecimal.ZERO)==1 && buypriceUp.compareTo(compareCostprice)==-1){
                isUpdateCostprice = false;
            }
            if(buypriceLower.compareTo(BigDecimal.ZERO)==1 && compareCostprice.compareTo(buypriceLower)==-1){
                isUpdateCostprice = false;
            }
        }
        return isUpdateCostprice;
    }
    /**
     * 获取本次入库后+上次进货退货审核产生的差额
     * 计算的成本价
     * @param storageid             仓库编号
     * @param goodsid               商品编码
     * @param unitnum               本次采购数量
     * @param buyprice              本次采购价
     * @param isChangeNum           是否已经变更库存数量
     * @return
     * @throws Exception
     */
	public BigDecimal getGoodsCostpriceByPreMarginAdd(String storageid,String goodsid,BigDecimal unitnum,BigDecimal buyprice,boolean isChangeNum) throws Exception{
        BigDecimal returnPrice = BigDecimal.ZERO;
        GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
        if(null!=goodsInfo) {
            //更新商品总的成本价
            //入库前现存量
            BigDecimal oldExistingnum = BigDecimal.ZERO;
            //总金额=入库前现存量*入库前成本价+入库量*入库单价
            BigDecimal totalAmount = BigDecimal.ZERO;
            StorageSummary storageSummary = getStorageSummaryMapper().getStorageSummarySumByGoodsidInTotal(goodsInfo.getId());
            if (null != storageSummary) {
                if(isChangeNum){
                    oldExistingnum = storageSummary.getExistingnum();
                }else{
                    oldExistingnum = storageSummary.getExistingnum().subtract(unitnum);
                }
                totalAmount = oldExistingnum.multiply(goodsInfo.getNewstorageprice()).setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            BigDecimal marginAmount = unitnum.multiply(buyprice.subtract(goodsInfo.getNewstorageprice()));
            //仓库独立核算时 相同仓库的未分摊成本 只能摊到同一个仓库中去
            String isStorageAccount = getStorageIsAccount(storageid);
            String countStorageid = storageid;
            if(!"1".equals(isStorageAccount)){
                countStorageid = null;
            }
            BigDecimal costDiffAmount = getBaseGoodsMapper().getCostDiffAmountByGoodsid(countStorageid,goodsid);
            if(null==costDiffAmount){
                costDiffAmount = BigDecimal.ZERO;
            }
            totalAmount = totalAmount.add(marginAmount).add(costDiffAmount);
            if(totalAmount.compareTo(BigDecimal.ZERO)!=0 && oldExistingnum.compareTo(BigDecimal.ZERO)!=0){
                returnPrice =  totalAmount.divide(oldExistingnum, 6, BigDecimal.ROUND_HALF_UP);
            }
        }
        return returnPrice;
    }

	/**
	 * 库存商品数量增加时，更新最新采购价 最新成本价
	 * @param storageid			仓库编号
	 * @param goodsid			商品编号
	 * @param unitnum			商品数量
	 * @param buyprice			商品单价
	 * @param isChangeNum		是否变更库存数量
	 * @param isUpdateNewBuyprice	是否更新最新采购价
     * @param isPurchaseEnter       是否采购入库
     * @param billid                    单据编号
     * @param detailid                 明细编号
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年11月3日
	 */
	public boolean updateGoodsPriceByAdd(String storageid,String goodsid,BigDecimal unitnum,BigDecimal buyprice,boolean isChangeNum,boolean isUpdateNewBuyprice,boolean isPurchaseEnter,String billid,String detailid) throws Exception{
		boolean flag = false;
        StorageInfo storageInfo = getStorageInfoByID(storageid);
        GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
        //入库金额 = 实际入库金额+成本未摊分金额
        BigDecimal enterAmount = unitnum.multiply(buyprice).setScale(6,BigDecimal.ROUND_HALF_UP);
        //判断仓库是否参与总量控制，参与总量控制的仓库 计算成本价
        if("1".equals(storageInfo.getIstotalcontrol())) {
            //更新商品总的成本价
            //入库前现存量
            BigDecimal oldExistingnum = BigDecimal.ZERO;
            BigDecimal newExistingnum = BigDecimal.ZERO;
            BigDecimal totalAmount = BigDecimal.ZERO;
            //成本变更记录 公式
            String costChangeRemark = "单据："+billid+ "，成本=（变更前库存数量*变更前成本价+入库金额(成本未分摊)）/当前库存数量";
            StorageSummary storageSummary = getStorageSummaryMapper().getStorageSummarySumByGoodsidInTotal(goodsInfo.getId());
            if (null != storageSummary) {
                newExistingnum = storageSummary.getExistingnum();
                if (isChangeNum) {
                    oldExistingnum = storageSummary.getExistingnum().subtract(unitnum);
                } else {
                    oldExistingnum = storageSummary.getExistingnum();
                }
                totalAmount = oldExistingnum.multiply(goodsInfo.getNewstorageprice()).setScale(6, BigDecimal.ROUND_HALF_UP);
                costChangeRemark += "成本=(("+oldExistingnum.toString()+"*"+goodsInfo.getNewstorageprice().toString();
            }
            if (isPurchaseEnter) {
                //仓库独立核算时 相同仓库的未分摊成本 只能摊到同一个仓库中去
                String isStorageAccount = getStorageIsAccount(storageid);
                String countStorageid = storageid;
                if(!"1".equals(isStorageAccount)){
                    countStorageid = null;
                }
                //未计算到成本的采购差额
                BigDecimal costDiffAmount = getBaseGoodsMapper().getCostDiffAmountByGoodsid(countStorageid, goodsid);
                if (null == costDiffAmount) {
                    costDiffAmount = BigDecimal.ZERO;
                }
                BigDecimal totalEnterAmount = enterAmount.add(costDiffAmount);
                //总金额 =  入库前现存量*入库前成本价+入库量*入库单价+差额
                totalAmount = totalAmount.add(totalEnterAmount);
                //差额已摊分 关闭成本差额记录
                getBaseGoodsMapper().closeCostDiffAmountByGoodsid(countStorageid,goodsid,billid,detailid);

                costChangeRemark += "+("+unitnum.toString()+"*"+buyprice.toString()+"+(成本未分摊金额："+costDiffAmount.toString()+")))";
            } else {
                totalAmount = totalAmount.add(enterAmount);
                costChangeRemark += "+("+unitnum.toString()+"*"+buyprice.toString()+"))";
            }

            BigDecimal costprice = goodsInfo.getNewstorageprice();
            if (newExistingnum.compareTo(BigDecimal.ZERO) == 1) {
                costprice = totalAmount.divide(newExistingnum, 6, BigDecimal.ROUND_HALF_UP);
            }
            costChangeRemark += "/"+newExistingnum.toString();
            GoodsInfo upGoodsInfo = new GoodsInfo();
            upGoodsInfo.setId(goodsInfo.getId());
            //更新商品成本价
            upGoodsInfo.setNewstorageprice(costprice);
            if (isUpdateNewBuyprice) {
                upGoodsInfo.setNewbuydate(CommonUtils.getTodayDataStr());
                if (BigDecimal.ZERO.compareTo(buyprice) != 0) {
                    upGoodsInfo.setNewbuyprice(buyprice);
                }
            }
            //更新商品成本价
            updateGoodsCostprice(upGoodsInfo,costChangeRemark,billid);
        }
        //仓库不参与总量控制 也计算该仓库的成本
        //更新仓库的商品成本价  仓库成本独立核算
        StorageSummary storageSum = getStorageSummaryByStorageidAndGoodsid(storageid, goodsInfo.getId());
        if(null!=storageSum){
            //仓库最新数量
            BigDecimal oldStoragenum = BigDecimal.ZERO;
            BigDecimal totalStorageAmount = BigDecimal.ZERO;
            BigDecimal newStoragenum = storageSum.getExistingnum();
            if(isChangeNum){
                oldStoragenum = storageSum.getExistingnum().subtract(unitnum);
            }else{
                oldStoragenum = storageSum.getExistingnum();
            }
            //库存数量为0的时候 库存金额直接取库存金额
            if(oldStoragenum.compareTo(BigDecimal.ZERO)==0){
                totalStorageAmount = storageSum.getStorageamount();
            }else{
                totalStorageAmount = oldStoragenum.multiply(storageSum.getCostprice()).setScale(6, BigDecimal.ROUND_HALF_UP);
            }
            totalStorageAmount = totalStorageAmount.add(enterAmount);
            if(newStoragenum.compareTo(BigDecimal.ZERO)!=0){
                BigDecimal storageCostprice = totalStorageAmount.divide(newStoragenum,6,BigDecimal.ROUND_HALF_UP);
                //更新仓库成本价
                getStorageSummaryMapper().updateStorageGoodsCostprice(storageid, goodsInfo.getId(),storageSum.getExistingnum(), storageCostprice,BigDecimal.ZERO);
            }else{
                //更新仓库成本价
                getStorageSummaryMapper().updateStorageGoodsCostprice(storageid, goodsInfo.getId(),storageSum.getExistingnum(), storageSum.getCostprice(),totalStorageAmount);
            }
        }
        flag = true;
        return flag;
	}
	/**
	 * 库存商品数量减少时，更新最新采购价 最新成本价
	 * @param storageid		仓库编号
	 * @param goodsid		商品编号
	 * @param unitnum		商品数量
	 * @param buyprice		商品单价
	 * @param isChangeNum	  是否变更库存数量
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年11月3日
	 */
	public void updateGoodsPriceBySubtract(String billid,String detailid,String storageid,String goodsid,BigDecimal unitnum,BigDecimal buyprice,boolean isChangeNum) throws Exception{
        StorageInfo storageInfo = getStorageInfoByID(storageid);
        GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
        //出库金额
        BigDecimal outAmount = unitnum.multiply(buyprice).setScale(6,BigDecimal.ROUND_HALF_UP);
        //判断仓库是否参与总量控制，参与总量控制的仓库 计算成本价
        if("1".equals(storageInfo.getIstotalcontrol())) {
            //入库前现存量
            BigDecimal oldExistingnum = BigDecimal.ZERO;
            BigDecimal newExistingnum = BigDecimal.ZERO;
            //总金额=入库前现存量*入库前成本价-入库量*入库单价
            BigDecimal totalAmount = BigDecimal.ZERO;
            StorageSummary storageSummary = getStorageSummaryMapper().getStorageSummarySumByGoodsidInTotal(goodsInfo.getId());
            //成本变更记录 公式
            String costChangeRemark = "单据："+billid+ "，成本=（变更前库存数量*变更前成本价-出库金额）/当前库存数量";
            if (null != oldExistingnum && null != storageSummary) {
                newExistingnum = storageSummary.getExistingnum();
                if (isChangeNum) {
                    oldExistingnum = storageSummary.getExistingnum().add(unitnum);
                } else {
                    oldExistingnum = storageSummary.getExistingnum();
                }
                totalAmount = oldExistingnum.multiply(goodsInfo.getNewstorageprice()).setScale(6, BigDecimal.ROUND_HALF_UP);
                costChangeRemark += "成本=(("+oldExistingnum.toString()+"*"+goodsInfo.getNewstorageprice().toString();
            }
            totalAmount = totalAmount.subtract(outAmount);
            BigDecimal costprice = goodsInfo.getNewstorageprice();
            costChangeRemark += "-("+unitnum.toString()+"*"+buyprice.toString()+"))/"+newExistingnum.toString();
            if (newExistingnum.compareTo(BigDecimal.ZERO) != 0) {
                costprice = totalAmount.divide(newExistingnum, 6, BigDecimal.ROUND_HALF_UP);
            }else{
                if(totalAmount.compareTo(BigDecimal.ZERO)!=0){
                    addCostDiffAmountShare("3",billid,detailid,storageid,goodsid,totalAmount,"0库存差额");
                    costChangeRemark += ";库存为0，生成未分摊金额："+totalAmount.toString();
                }
            }

            GoodsInfo upGoodsInfo = new GoodsInfo();
            upGoodsInfo.setId(goodsInfo.getId());
            //更新商品成本价
            upGoodsInfo.setNewstorageprice(costprice);
            //更新商品成本价
            updateGoodsCostprice(upGoodsInfo,costChangeRemark,billid);
        }
        //仓库不参与总量控制也要计算该仓库的成本价
        //更新仓库的商品成本价  仓库成本独立核算
        StorageSummary storageSum = getStorageSummaryByStorageidAndGoodsid(storageid, goodsInfo.getId());
        if (null != storageSum) {
            BigDecimal oldStoragenum = BigDecimal.ZERO;
            BigDecimal totalStorageAmount = BigDecimal.ZERO;
            BigDecimal newStoragenum = storageSum.getExistingnum();
            if (isChangeNum) {
                oldStoragenum = storageSum.getExistingnum().add(unitnum);
            } else {
                oldStoragenum = storageSum.getExistingnum();
            }
            totalStorageAmount = oldStoragenum.multiply(storageSum.getCostprice()).setScale(6, BigDecimal.ROUND_HALF_UP);
            totalStorageAmount = totalStorageAmount.subtract(outAmount);
            if (newStoragenum.compareTo(BigDecimal.ZERO) !=0) {
                BigDecimal storageCostprice = totalStorageAmount.divide(newStoragenum, 6, BigDecimal.ROUND_HALF_UP);
                //更新仓库成本价
                getStorageSummaryMapper().updateStorageGoodsCostprice(storageid, goodsInfo.getId(), storageSum.getExistingnum(),storageCostprice,BigDecimal.ZERO);
            }else{
                //更新仓库成本价
                getStorageSummaryMapper().updateStorageGoodsCostprice(storageid, goodsid, storageSum.getExistingnum(),storageSum.getCostprice(),totalStorageAmount);
            }

        }
	}
	/**
	 * 库存商品数量减少时，更新最新采购价 最新成本价
	 * @param storageid		仓库编号
	 * @param goodsid		商品编号
	 * @param unitnum		商品数量
	 * @param buyprice		商品单价
	 * @param isChangeNum	  是否变更库存数量
	 * @param taxamount	  是否变更库存数量
	 * @throws Exception
	 * @author chenwei
	 * @date 2015年11月3日
	 */
	public void updateGoodsPriceBySubtract(String billid,String detailid,String storageid,String goodsid,BigDecimal unitnum,BigDecimal buyprice,boolean isChangeNum,BigDecimal taxamount) throws Exception{
		StorageInfo storageInfo = getStorageInfoByID(storageid);
		GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
		//出库金额
		BigDecimal outAmount = taxamount;
		//判断仓库是否参与总量控制，参与总量控制的仓库 计算成本价
		if("1".equals(storageInfo.getIstotalcontrol())) {
			//入库前现存量
			BigDecimal oldExistingnum = BigDecimal.ZERO;
			BigDecimal newExistingnum = BigDecimal.ZERO;
			//总金额=入库前现存量*入库前成本价-入库量*入库单价
			BigDecimal totalAmount = BigDecimal.ZERO;
			StorageSummary storageSummary = getStorageSummaryMapper().getStorageSummarySumByGoodsidInTotal(goodsInfo.getId());
			//成本变更记录 公式
			String costChangeRemark = "单据："+billid+ "，成本=（变更前库存数量*变更前成本价-出库金额）/当前库存数量";
			if (null != oldExistingnum && null != storageSummary) {
				newExistingnum = storageSummary.getExistingnum();
				if (isChangeNum) {
					oldExistingnum = storageSummary.getExistingnum().add(unitnum);
				} else {
					oldExistingnum = storageSummary.getExistingnum();
				}
				totalAmount = oldExistingnum.multiply(goodsInfo.getNewstorageprice()).setScale(6, BigDecimal.ROUND_HALF_UP);
				costChangeRemark += "成本=(("+oldExistingnum.toString()+"*"+goodsInfo.getNewstorageprice().toString();
			}
			totalAmount = totalAmount.subtract(outAmount);
			BigDecimal costprice = goodsInfo.getNewstorageprice();
			costChangeRemark += "-("+outAmount+"))/"+newExistingnum.toString();
			if (newExistingnum.compareTo(BigDecimal.ZERO) != 0) {
				costprice = totalAmount.divide(newExistingnum, 6, BigDecimal.ROUND_HALF_UP);
			}else{
				if(totalAmount.compareTo(BigDecimal.ZERO)!=0){
					addCostDiffAmountShare("3",billid,detailid,storageid,goodsid,totalAmount,"0库存差额");
					costChangeRemark += ";库存为0，生成未分摊金额："+totalAmount.toString();
				}
			}

			GoodsInfo upGoodsInfo = new GoodsInfo();
			upGoodsInfo.setId(goodsInfo.getId());
			//更新商品成本价
			upGoodsInfo.setNewstorageprice(costprice);
			//更新商品成本价
			updateGoodsCostprice(upGoodsInfo,costChangeRemark,billid);
		}
		//仓库不参与总量控制也要计算该仓库的成本价
		//更新仓库的商品成本价  仓库成本独立核算
		StorageSummary storageSum = getStorageSummaryByStorageidAndGoodsid(storageid, goodsInfo.getId());
		if (null != storageSum) {
			BigDecimal oldStoragenum = BigDecimal.ZERO;
			BigDecimal totalStorageAmount = BigDecimal.ZERO;
			BigDecimal newStoragenum = storageSum.getExistingnum();
			if (isChangeNum) {
				oldStoragenum = storageSum.getExistingnum().add(unitnum);
			} else {
				oldStoragenum = storageSum.getExistingnum();
			}
			totalStorageAmount = oldStoragenum.multiply(storageSum.getCostprice()).setScale(6, BigDecimal.ROUND_HALF_UP);
			totalStorageAmount = totalStorageAmount.subtract(outAmount);
			if (newStoragenum.compareTo(BigDecimal.ZERO) !=0) {
				BigDecimal storageCostprice = totalStorageAmount.divide(newStoragenum, 6, BigDecimal.ROUND_HALF_UP);
				//更新仓库成本价
				getStorageSummaryMapper().updateStorageGoodsCostprice(storageid, goodsInfo.getId(), storageSum.getExistingnum(),storageCostprice,BigDecimal.ZERO);
			}else{
				//更新仓库成本价
				getStorageSummaryMapper().updateStorageGoodsCostprice(storageid, goodsid, storageSum.getExistingnum(),storageSum.getCostprice(),totalStorageAmount);
			}

		}
	}
	/**
	 * 库存商品数量增加时，更新最新采购价 最新成本价
	 * @param storageid			仓库编号
	 * @param goodsid			商品编号
	 * @param unitnum			商品数量
	 * @param buyprice			商品单价
	 * @param isChangeNum		是否变更库存数量
	 * @param isUpdateNewBuyprice	是否更新最新采购价
	 * @param isPurchaseEnter       是否采购入库
	 * @param billid                    单据编号
	 * @param detailid                 明细编号
	 * @throws Exception
	 * @author chenwei
	 * @date 2015年11月3日
	 */
	public boolean updateStoragePriceByAdd(String storageid,String goodsid,BigDecimal unitnum,BigDecimal buyprice,boolean isChangeNum,boolean isUpdateNewBuyprice,boolean isPurchaseEnter,String billid,String detailid) throws Exception{
		boolean flag = false;
		StorageInfo storageInfo = getStorageInfoByID(storageid);
		GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
		//入库金额 = 实际入库金额+成本未摊分金额
		BigDecimal enterAmount = unitnum.multiply(buyprice).setScale(6,BigDecimal.ROUND_HALF_UP);
		//仓库不参与总量控制 也计算该仓库的成本
		//更新仓库的商品成本价  仓库成本独立核算
		StorageSummary storageSum = getStorageSummaryByStorageidAndGoodsid(storageid, goodsInfo.getId());
		if(null!=storageSum){
			//仓库最新数量
			BigDecimal oldStoragenum = BigDecimal.ZERO;
			BigDecimal totalStorageAmount = BigDecimal.ZERO;
			BigDecimal newStoragenum = storageSum.getExistingnum();
			if(isChangeNum){
				oldStoragenum = storageSum.getExistingnum().subtract(unitnum);
			}else{
				oldStoragenum = storageSum.getExistingnum();
			}
			//库存数量为0的时候 库存金额直接取库存金额
			if(oldStoragenum.compareTo(BigDecimal.ZERO)==0){
				totalStorageAmount = storageSum.getStorageamount();
			}else{
				totalStorageAmount = oldStoragenum.multiply(storageSum.getCostprice()).setScale(6, BigDecimal.ROUND_HALF_UP);
			}
			totalStorageAmount = totalStorageAmount.add(enterAmount);
			if(newStoragenum.compareTo(BigDecimal.ZERO)!=0){
				BigDecimal storageCostprice = totalStorageAmount.divide(newStoragenum,6,BigDecimal.ROUND_HALF_UP);
				//更新仓库成本价
				getStorageSummaryMapper().updateStorageGoodsCostprice(storageid, goodsInfo.getId(),storageSum.getExistingnum(), storageCostprice,BigDecimal.ZERO);
			}else{
				//更新仓库成本价
				getStorageSummaryMapper().updateStorageGoodsCostprice(storageid, goodsInfo.getId(),storageSum.getExistingnum(), storageSum.getCostprice(),totalStorageAmount);
			}
		}
		flag = true;
		return flag;
	}
	/**
	 * 库存商品数量减少时，更新最新采购价 最新成本价
	 * @param storageid		仓库编号
	 * @param goodsid		商品编号
	 * @param unitnum		商品数量
	 * @param buyprice		商品单价
	 * @param isChangeNum	  是否变更库存数量
	 * @throws Exception
	 * @author chenwei
	 * @date 2015年11月3日
	 */
	public void updateStoragePriceBySubtract(String billid,String detailid,String storageid,String goodsid,BigDecimal unitnum,BigDecimal buyprice,boolean isChangeNum) throws Exception{
		StorageInfo storageInfo = getStorageInfoByID(storageid);
		GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
		//出库金额
		BigDecimal outAmount = unitnum.multiply(buyprice).setScale(6,BigDecimal.ROUND_HALF_UP);
		//更新仓库的商品成本价  仓库成本独立核算
		StorageSummary storageSum = getStorageSummaryByStorageidAndGoodsid(storageid, goodsInfo.getId());
		if (null != storageSum) {
			BigDecimal oldStoragenum = BigDecimal.ZERO;
			BigDecimal totalStorageAmount = BigDecimal.ZERO;
			BigDecimal newStoragenum = storageSum.getExistingnum();
			if (isChangeNum) {
				oldStoragenum = storageSum.getExistingnum().add(unitnum);
			} else {
				oldStoragenum = storageSum.getExistingnum();
			}
			totalStorageAmount = oldStoragenum.multiply(storageSum.getCostprice()).setScale(6, BigDecimal.ROUND_HALF_UP);
			totalStorageAmount = totalStorageAmount.subtract(outAmount);
			if (newStoragenum.compareTo(BigDecimal.ZERO) !=0) {
				BigDecimal storageCostprice = totalStorageAmount.divide(newStoragenum, 6, BigDecimal.ROUND_HALF_UP);
				//更新仓库成本价
				getStorageSummaryMapper().updateStorageGoodsCostprice(storageid, goodsInfo.getId(), storageSum.getExistingnum(),storageCostprice,BigDecimal.ZERO);
			}else{
				//更新仓库成本价
				getStorageSummaryMapper().updateStorageGoodsCostprice(storageid, goodsid, storageSum.getExistingnum(),storageSum.getCostprice(),totalStorageAmount);
			}

		}
	}
    /**
     * 根据条件 获取计算后的商品成本价
     * @param storageid                 库存编号
     * @param goodsid                   商品编号
     * @param unitnum                   商品数量
     * @param buyprice                  采购价
     * @param addcostprice              入库成本价
     * @return
     * @throws Exception
     */
    public BigDecimal getGoodsPirceByDiffPriceSubtract(String storageid,String goodsid,BigDecimal unitnum,BigDecimal buyprice,BigDecimal addcostprice) throws Exception{
        BigDecimal costprice = BigDecimal.ZERO;
        StorageInfo storageInfo = getStorageInfoByID(storageid);
        GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
        if (null != goodsInfo) {
            costprice = goodsInfo.getNewstorageprice();
            //判断仓库是否参与总量控制，参与总量控制的仓库 计算成本价
            if("1".equals(storageInfo.getIstotalcontrol())) {
                //入库前现存量
                BigDecimal existingnum = BigDecimal.ZERO;
                BigDecimal totalAmount = BigDecimal.ZERO;
                StorageSummary storageSummary = getStorageSummaryMapper().getStorageSummarySumByGoodsidInTotal(goodsInfo.getId());
                if (null != storageSummary) {
                    existingnum = storageSummary.getExistingnum();
                    totalAmount = existingnum.multiply(goodsInfo.getNewstorageprice()).setScale(6, BigDecimal.ROUND_HALF_UP);
                }
                BigDecimal diffprice = buyprice.subtract(addcostprice);
                //总金额 = 库存总金额+差价金额
                totalAmount = totalAmount.subtract(diffprice.multiply(unitnum)).setScale(6, BigDecimal.ROUND_HALF_UP);
                if (existingnum.compareTo(BigDecimal.ZERO) == 1) {
                    costprice = totalAmount.divide(existingnum, 6, BigDecimal.ROUND_HALF_UP);
                }
            }
        }
        return costprice ;
    }
    /**
     * 库存金额减少 采购价变化时，根据差价调整成本
     * @param billid                 单据编号
     * @param detailid               单据明细编号
     * @param storageid             仓库编号
     * @param goodsid               商品编号
     * @param unitnum               商品数量
     * @param buyprice              采购价
     * @param addcostprice          出入库时成本价
     * @param isUpdateNewBuyprice  是否更新最新采购价
     * @param isChangeTotalNum      是否更新采购总数量
     * @throws Exception
     */
	public void updateGoodsPirceByDiffPriceSubtract(String billid,String detailid,String storageid,String goodsid,BigDecimal unitnum,BigDecimal buyprice,BigDecimal addcostprice,boolean isUpdateNewBuyprice,boolean isChangeTotalNum) throws Exception{
        StorageInfo storageInfo = getStorageInfoByID(storageid);
        GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
        BigDecimal diffprice = buyprice.subtract(addcostprice);
        //判断仓库是否参与总量控制，参与总量控制的仓库 计算成本价
        if("1".equals(storageInfo.getIstotalcontrol())) {
            //入库前现存量
            BigDecimal existingnum = BigDecimal.ZERO;
            //总金额=入库前现存量*入库前成本价-入库量*入库单价
            BigDecimal totalAmount = BigDecimal.ZERO;
            StorageSummary storageSummary = getStorageSummaryMapper().getStorageSummarySumByGoodsidInTotal(goodsid);
            //成本变更记录 公式
            String costChangeRemark = "采购出库或者进货单反审时，采购价变更导致成本调整变更，单据："+billid+ "，成本=（库存总金额-差价金额(采购价-出入库时成本价)）/当前库存数量";
            if (null != storageSummary) {
                existingnum = storageSummary.getExistingnum();
                totalAmount = existingnum.multiply(goodsInfo.getNewstorageprice()).setScale(6, BigDecimal.ROUND_HALF_UP);
            }
            //出库时 总金额 = 库存总金额-差价金额
            totalAmount = totalAmount.subtract(diffprice.multiply(unitnum)).setScale(6, BigDecimal.ROUND_HALF_UP);
            BigDecimal costprice = goodsInfo.getNewstorageprice();
            if (existingnum.compareTo(BigDecimal.ZERO) != 0) {
                costprice = totalAmount.divide(existingnum, 6, BigDecimal.ROUND_HALF_UP);
            }
            costChangeRemark += ";成本=(（"+existingnum.toString()+"*"+goodsInfo.getNewstorageprice().toString()+")-("+buyprice.toString()+"-"+addcostprice.toString()+")*"+unitnum.toString()+")/"+existingnum.toString();

            GoodsInfo upGoodsInfo = new GoodsInfo();
            upGoodsInfo.setId(goodsInfo.getId());
            //更新商品成本价
            upGoodsInfo.setNewstorageprice(costprice);
            if (isChangeTotalNum) {
                if (null != goodsInfo.getNewtotalbuynum()) {
                    upGoodsInfo.setNewtotalbuynum(goodsInfo.getNewtotalbuynum().subtract(unitnum));
                }
                if (null != goodsInfo.getNewtotalbuyamount()) {
                    BigDecimal amount = unitnum.multiply(buyprice).setScale(2, BigDecimal.ROUND_HALF_UP);
                    upGoodsInfo.setNewtotalbuyamount(goodsInfo.getNewtotalbuyamount().subtract(amount));
                }
            } else {
                if (null != goodsInfo.getNewtotalbuyamount()) {
                    BigDecimal amount = unitnum.multiply(diffprice).setScale(2, BigDecimal.ROUND_HALF_UP);
                    upGoodsInfo.setNewtotalbuyamount(goodsInfo.getNewtotalbuyamount().subtract(amount));
                }
            }
            if (isUpdateNewBuyprice) {
                if (BigDecimal.ZERO.compareTo(buyprice) != 0) {
                    upGoodsInfo.setNewbuyprice(buyprice);
                }
            }

            //更新商品成本价
            updateGoodsCostprice(upGoodsInfo,costChangeRemark,billid);
        }
        //更新仓库商品成本价
        updateStoragePriceByDiffPriceSubtract(storageid,goodsid,unitnum,buyprice,addcostprice);
    }

    /**
     * 库存金额减少 采购价变化时，根据差价调整成本
     * 更新仓库商品成本价
     * @param storageid
     * @param goodsid
     * @param unitnum
     * @param buyprice
     * @param addcostprice
     * @return
     * @throws Exception
     */
    public boolean updateStoragePriceByDiffPriceSubtract(String storageid,String goodsid,BigDecimal unitnum,BigDecimal buyprice,BigDecimal addcostprice) throws Exception{
        GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
        BigDecimal diffprice = buyprice.subtract(addcostprice);
        //更新仓库的商品成本价  仓库成本独立核算
        StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageid, goodsid);
        if (null != storageSummary) {
            BigDecimal totalStorageAmount = BigDecimal.ZERO;
            BigDecimal soragenum = storageSummary.getExistingnum();
            totalStorageAmount = soragenum.multiply(storageSummary.getCostprice()).setScale(6, BigDecimal.ROUND_HALF_UP);
            totalStorageAmount = totalStorageAmount.subtract(diffprice.multiply(unitnum)).setScale(6, BigDecimal.ROUND_HALF_UP);
            BigDecimal storageCostprice = storageSummary.getCostprice();
            if (soragenum.compareTo(BigDecimal.ZERO) != 0) {
                storageCostprice = totalStorageAmount.divide(soragenum, 6, BigDecimal.ROUND_HALF_UP);
                //更新仓库成本价 当仓库中有商品现存量时 库存未分摊金额为0
                getStorageSummaryMapper().updateStorageGoodsCostprice(storageid, goodsInfo.getId(), storageSummary.getExistingnum(),storageCostprice,BigDecimal.ZERO);
            }else{
                //更新仓库成本价
                getStorageSummaryMapper().updateStorageGoodsCostprice(storageid, goodsid, storageSummary.getExistingnum(),storageSummary.getCostprice(),totalStorageAmount);
            }
        }
        return true;
    }
    /**
     * 采购退货出库后 调整仓库商品成本价
     * @param storageid
     * @param goodsid
     * @param unitnum
     * @param outCostPrice
     * @return
     * @throws Exception
     */
    public boolean updateStorageGoodsPriceByPurchaseOut(String storageid,String goodsid,BigDecimal unitnum,BigDecimal outCostPrice) throws Exception{
        GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
        //更新仓库的商品成本价  仓库成本独立核算
        StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageid, goodsid);
        if (null != storageSummary) {
            BigDecimal totalStorageAmount = BigDecimal.ZERO;
            BigDecimal soragenum = storageSummary.getExistingnum().add(unitnum);
            totalStorageAmount = soragenum.multiply(storageSummary.getCostprice());
            totalStorageAmount = totalStorageAmount.subtract(outCostPrice.multiply(unitnum)).setScale(6, BigDecimal.ROUND_HALF_UP);
            BigDecimal storageCostprice = storageSummary.getCostprice();
            if (storageSummary.getExistingnum().compareTo(BigDecimal.ZERO) != 0) {
                storageCostprice = totalStorageAmount.divide(storageSummary.getExistingnum(), 6, BigDecimal.ROUND_HALF_UP);
                //更新仓库成本价 当仓库中有商品现存量时 库存未分摊金额为0
                getStorageSummaryMapper().updateStorageGoodsCostprice(storageid, goodsid, storageSummary.getExistingnum(),storageCostprice,BigDecimal.ZERO);
            }else{
                //更新仓库成本价
                getStorageSummaryMapper().updateStorageGoodsCostprice(storageid, goodsid, storageSummary.getExistingnum(),storageCostprice,totalStorageAmount);
            }
        }
        return true;
    }

    /**
     * 采购退货出库后 调整仓库商品成本价
     * @param storageid
     * @param goodsid
     * @param unitnum
     * @param enterCostPrice
     * @return
     * @throws Exception
     */
    public boolean updateStorageGoodsPriceByPurchaseEnter(String storageid,String goodsid,BigDecimal unitnum,BigDecimal enterCostPrice) throws Exception{
        GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
        //更新仓库的商品成本价  仓库成本独立核算
        StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageid, goodsid);
        if (null != storageSummary) {
            BigDecimal totalStorageAmount = BigDecimal.ZERO;
            BigDecimal soragenum = storageSummary.getExistingnum().subtract(unitnum);
            totalStorageAmount = soragenum.multiply(storageSummary.getCostprice()).setScale(6, BigDecimal.ROUND_HALF_UP);
            totalStorageAmount = totalStorageAmount.add(enterCostPrice.multiply(unitnum)).setScale(6, BigDecimal.ROUND_HALF_UP);
            BigDecimal storageCostprice = storageSummary.getCostprice();
            if(storageSummary.getExistingnum().compareTo(BigDecimal.ZERO)==1){
                storageCostprice = totalStorageAmount.divide(storageSummary.getExistingnum(), 6, BigDecimal.ROUND_HALF_UP);
                //更新仓库成本价 当仓库中有商品现存量时 库存未分摊金额为0
                getStorageSummaryMapper().updateStorageGoodsCostprice(storageid, goodsid, storageSummary.getExistingnum(),storageCostprice,BigDecimal.ZERO);
            }
        }
        return true;
    }
    /**
     * 根据条件 获取计算后的商品成本价
     * @param storageid             仓库编号
     * @param goodsid               商品编号
     * @param unitnum               入库数量
     * @param buyprice              当前采购价
     * @param addcostprice         入库时成本价
     * @return
     * @throws Exception
     */
    public BigDecimal getGoodsPirceByDiffPriceAdd(String storageid,String goodsid,BigDecimal unitnum,BigDecimal buyprice,BigDecimal addcostprice) throws Exception{
        BigDecimal costprice = BigDecimal.ZERO;
        StorageInfo storageInfo = getStorageInfoByID(storageid);
        GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
        if (null != goodsInfo) {
            costprice = goodsInfo.getNewstorageprice();
            //判断仓库是否参与总量控制，参与总量控制的仓库 计算成本价
            if("1".equals(storageInfo.getIstotalcontrol())) {
                //入库前现存量
                BigDecimal existingnum = BigDecimal.ZERO;
                BigDecimal totalAmount = BigDecimal.ZERO;
                StorageSummary storageSummary = getStorageSummaryMapper().getStorageSummarySumByGoodsidInTotal(goodsInfo.getId());
                if (null != storageSummary) {
                    existingnum = storageSummary.getExistingnum();
                    totalAmount = existingnum.multiply(goodsInfo.getNewstorageprice()).setScale(6, BigDecimal.ROUND_HALF_UP);
                }
                BigDecimal diffprice = buyprice.subtract(addcostprice);
                //总金额 = 库存总金额+差价金额
                totalAmount = totalAmount.add(diffprice.multiply(unitnum)).setScale(6, BigDecimal.ROUND_HALF_UP);
                if (existingnum.compareTo(BigDecimal.ZERO) == 1) {
                    costprice = totalAmount.divide(existingnum, 6, BigDecimal.ROUND_HALF_UP);
                }
            }
        }
        return costprice;
    }
    /**
     * 库存金额增加 采购价变化时，根据差价调整成本
     * @param billid                单据编号
     * @param detailid              明细编号
     * @param storageid             仓库编号
     * @param goodsid               商品编号
     * @param unitnum               商品数量
     * @param buyprice              采购价
     * @param addcostprice          出入库时成本价
     * @param isUpdateNewBuyprice  是否更新最新采购价
     * @param isChangeTotalNum      是否更新采购总数量
     * @throws Exception
     */
    public void updateGoodsPirceByDiffPriceAdd(String billid,String detailid,String storageid,String goodsid,BigDecimal unitnum,BigDecimal buyprice,BigDecimal addcostprice,boolean isUpdateNewBuyprice,boolean isChangeTotalNum) throws Exception{
        StorageInfo storageInfo = getStorageInfoByID(storageid);
        GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
        BigDecimal diffprice = buyprice.subtract(addcostprice);
        BigDecimal diffAmount = diffprice.multiply(unitnum).setScale(6, BigDecimal.ROUND_HALF_UP);
        //判断仓库是否参与总量控制，参与总量控制的仓库 计算成本价
        if("1".equals(storageInfo.getIstotalcontrol())) {

            //入库前现存量
            BigDecimal existingnum = BigDecimal.ZERO;
            BigDecimal totalAmount = BigDecimal.ZERO;
            StorageSummary storageSummary = getStorageSummaryMapper().getStorageSummarySumByGoodsidInTotal(goodsInfo.getId());
            if (null != storageSummary) {
                existingnum = storageSummary.getExistingnum();
                totalAmount = existingnum.multiply(goodsInfo.getNewstorageprice()).setScale(6, BigDecimal.ROUND_HALF_UP);
            }
            //成本变更记录 公式
            String costChangeRemark = "采购入库时，采购价变更导致成本调整变更，单据：+"+billid+ "，成本=（库存总金额+差价金额(采购价-出入库时成本价)）/当前库存数量";
            //总金额 = 库存总金额+差价金额
            totalAmount = totalAmount.add(diffAmount);
            BigDecimal costprice = goodsInfo.getNewstorageprice();
            if (existingnum.compareTo(BigDecimal.ZERO) != 0) {
                costprice = totalAmount.divide(existingnum, 6, BigDecimal.ROUND_HALF_UP);
                costChangeRemark += ";成本=(（"+existingnum.toString()+"*"+goodsInfo.getNewstorageprice().toString()+")+(("+buyprice.toString()+"-"+addcostprice.toString()+")*"+unitnum.toString()+"))/"+existingnum.toString();
            }else{
                if(totalAmount.compareTo(BigDecimal.ZERO)!=0){
                    addCostDiffAmountShare("3",billid,detailid,storageid,goodsid,totalAmount,"0库存差额");
                }
            }
            GoodsInfo upGoodsInfo = new GoodsInfo();
            upGoodsInfo.setId(goodsInfo.getId());
            //更新商品成本价
            upGoodsInfo.setNewstorageprice(costprice);
            //是否更新总数量 总金额
            if (isChangeTotalNum) {
                if (null != goodsInfo.getNewtotalbuynum()) {
                    upGoodsInfo.setNewtotalbuynum(goodsInfo.getNewtotalbuynum().add(unitnum));
                }
                if (null != goodsInfo.getNewtotalbuyamount()) {
                    BigDecimal amount = unitnum.multiply(buyprice).setScale(2, BigDecimal.ROUND_HALF_UP);
                    upGoodsInfo.setNewtotalbuyamount(goodsInfo.getNewtotalbuyamount().add(amount));
                }
            } else {
                if (null != goodsInfo.getNewtotalbuyamount()) {
                    BigDecimal amount = unitnum.multiply(diffprice).setScale(2, BigDecimal.ROUND_HALF_UP);
                    upGoodsInfo.setNewtotalbuyamount(goodsInfo.getNewtotalbuyamount().add(amount));
                }
            }
            if (isUpdateNewBuyprice) {
                if (BigDecimal.ZERO.compareTo(buyprice) != 0) {
                    upGoodsInfo.setNewbuyprice(buyprice);
                }
            }
            //更新商品成本价
            updateGoodsCostprice(upGoodsInfo,costChangeRemark,billid);
        }
        //更新仓库商品成本价
        updateStorageGoodsPriceByDiffPriceAdd(storageid,goodsid,unitnum,buyprice,addcostprice);
    }

    /**
     * 更新仓库商品成本价
     * @param storageid
     * @param goodsid
     * @param unitnum
     * @param buyprice
     * @param addcostprice
     * @return
     * @throws Exception
     */
    public boolean updateStorageGoodsPriceByDiffPriceAdd(String storageid,String goodsid,BigDecimal unitnum,BigDecimal buyprice,BigDecimal addcostprice) throws Exception{
        GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
        BigDecimal diffprice = buyprice.subtract(addcostprice);
        BigDecimal diffAmount = diffprice.multiply(unitnum).setScale(6, BigDecimal.ROUND_HALF_UP);
        //仓库是否参与总量控制 都要计算该仓库的成本价
        //更新仓库的商品成本价  仓库成本独立核算
        StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageid, goodsInfo.getId());
        if (null != storageSummary) {
            BigDecimal totalStorageAmount = BigDecimal.ZERO;
            BigDecimal soragenum = storageSummary.getExistingnum();
            //库存数量为0的时候 取库存金额
            if(soragenum.compareTo(BigDecimal.ZERO)==0){
                totalStorageAmount = storageSummary.getStorageamount();
            }else{
                totalStorageAmount = soragenum.multiply(storageSummary.getCostprice()).setScale(6, BigDecimal.ROUND_HALF_UP);
            }
            totalStorageAmount = totalStorageAmount.add(diffAmount);
            if(soragenum.compareTo(BigDecimal.ZERO)!=0){
                BigDecimal storageCostprice = totalStorageAmount.divide(soragenum, 6, BigDecimal.ROUND_HALF_UP);
                //更新仓库成本价
                getStorageSummaryMapper().updateStorageGoodsCostprice(storageid, goodsInfo.getId(),storageSummary.getExistingnum(), storageCostprice,BigDecimal.ZERO);
            }else{
                //更新仓库成本价
                getStorageSummaryMapper().updateStorageGoodsCostprice(storageid, goodsInfo.getId(),storageSummary.getExistingnum(), storageSummary.getCostprice(),totalStorageAmount);
            }

        }
        return true;
    }
    /**
     * 添加成本差额摊分记录
     * @param billtype      单据类型1进货单2退货单3采购入库单
     * @param billid        单据编号
     * @param detailid      单据明细编号
     * @param storageid     仓库编号
     * @param goodsid       商品编号
     * @param amount        差额
     * @param remark        备注
     * @return
     * @throws Exception
     */
    public boolean addCostDiffAmountShare(String billtype,String billid,String detailid,String storageid,String goodsid,BigDecimal amount,String remark) throws Exception{
        int i = getBaseGoodsMapper().addGoodsCostpriceShare(billtype,billid,detailid,storageid,goodsid,amount,remark);
        return i>0;
    }

    /**
     * 判断成本差额是否摊分
     * @param billtype
     * @param billid
     * @param detailid
     * @param goodsid
     * @return
     * @throws Exception
     */
    public boolean hasCostDiffAmountNoShare(String billtype,String billid,String detailid,String goodsid) throws Exception{
        int i = getBaseGoodsMapper().hasCostDiffAmountNoShare(billtype, billid, detailid, goodsid);
        return i>0;
    }
    /**
     * 删除成本差额摊分记录
     * @param billtype
     * @param billid
     * @param detailid
     * @param goodsid
     * @return
     * @throws Exception
     */
    public boolean delteCostDiffAmountShare(String billtype,String billid,String detailid,String goodsid) throws Exception{
        int i = getBaseGoodsMapper().delteCostDiffAmountShare(billtype, billid, detailid, goodsid);
        return i>0;
    }
	public List<Map> getGoodsStorageLocationInfoList(String goodsid,String storageid) throws Exception{
		List<Map> list = storageSummaryMapper.getGoodsStorageLocationInfoList(goodsid, storageid);
		return list;
	}
	/**
	 * 获取批次号
	 * @param goodsid
	 * @param produceddate
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年11月4日
	 */
	public Map getBatchno(String goodsid,String produceddate) throws Exception{
		//批次号规则 保质期+生产日期 
		//第一位字母P后面数字表示生产日期
		//第二位字母表示保质期 时间单位D天W星期M月Y年  字母后面数字表示时间长短
		//例如P20140501M6 表示生产日期为2014-05-01 保质期为6月
		String batchno = "";
		Calendar calendar = Calendar.getInstance();
	//	String nowTime = CommonUtils.dataToStr(calendar.getTime(),"yyyy-MM-dd");
		String produceddateTemp = CommonUtils.dateStringChange(produceddate);
		if(StringUtils.isNotEmpty(produceddate)){
			batchno += produceddateTemp.substring(5,6)+produceddateTemp.substring(7,8)+produceddateTemp.substring(4,5)+produceddateTemp.substring(2,3)+produceddateTemp.substring(6,7)+produceddateTemp.substring(3,4);
			produceddateTemp = produceddateTemp.substring(2,4)+produceddateTemp.substring(6,8);
			Integer temp = Integer.parseInt(produceddateTemp)*4;
			batchno = temp.toString()+batchno;
			//batchno += "P"+CommonUtils.dateStringChange(produceddate);
		}
		GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
		String deadline = "";


		if(null!=goodsInfo){
			//获取保质期 并计算商品到期日期
			//BigDecimal shelflife = goodsInfo.getShelflife();
			//if(null==shelflife){
			//	shelflife = BigDecimal.ZERO;
			//}
			//String shelflifeunit = goodsInfo.getShelflifeunit();
			//Date produced = CommonUtils.stringToDate(produceddate);
			//calendar.setTime(produced);
			//1天2周3月4年
			/*if("1".equals(shelflifeunit)){
				batchno+="D"+shelflife.intValue();
				calendar.add(Calendar.DATE,+shelflife.intValue());
			}else if("2".equals(shelflifeunit)){
				batchno+="W"+shelflife.intValue();
				calendar.add(Calendar.DATE,+shelflife.multiply(new BigDecimal(7)).intValue());
			}else if("3".equals(shelflifeunit)){
				batchno+="M"+shelflife.intValue();
				calendar.add(Calendar.MONTH,+shelflife.intValue());
			}else if("4".equals(shelflifeunit)){
				batchno+="Y"+shelflife.intValue();
				calendar.add(Calendar.YEAR,+shelflife.intValue());
			}*/
			calendar.add(Calendar.DATE,15);
			deadline = CommonUtils.dataToStr(calendar.getTime(),"yyyy-MM-dd");
			if(produceddate.equals(deadline)){
				deadline = "";
			}
		}
		Map map = new HashMap();
		map.put("batchno", batchno);
		map.put("deadline", deadline);
		return map;
	}
    /**
     * 根据截止日期获取批次号与生产日期
     * @param goodsid           商品编码
     * @param deadline          截止日期
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2015年11月4日
     */
    public Map getBatchnoByDeadline(String goodsid,String deadline) throws Exception{
        //批次号规则 保质期+生产日期
        //第一位字母P后面数字表示生产日期
        //第二位字母表示保质期 时间单位D天W星期M月Y年  字母后面数字表示时间长短
        //例如P20140501M6 表示生产日期为2014-05-01 保质期为6月
        String batchno = "";
        String batchno1 = "";
        GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
        String produceddate = "";
        if(null!=goodsInfo){
            //获取保质期 并计算商品到期日期
            BigDecimal shelflife = goodsInfo.getShelflife();
            if(null==shelflife){
                shelflife = BigDecimal.ZERO;
            }
            String shelflifeunit = goodsInfo.getShelflifeunit();
            Date deadlineDate = CommonUtils.stringToDate(deadline);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(deadlineDate);
            //1天2周3月4年
            if("1".equals(shelflifeunit)){
                batchno1+="D"+shelflife.intValue();
                calendar.add(Calendar.DATE, -shelflife.intValue());
            }else if("2".equals(shelflifeunit)){
                batchno1+="W"+shelflife.intValue();
                calendar.add(Calendar.DATE,-shelflife.multiply(new BigDecimal(7)).intValue());
            }else if("3".equals(shelflifeunit)){
                batchno1+="M"+shelflife.intValue();
                calendar.add(Calendar.MONTH,-shelflife.intValue());
            }else if("4".equals(shelflifeunit)){
                batchno1+="Y"+shelflife.intValue();
                calendar.add(Calendar.YEAR,-shelflife.intValue());
            }
            produceddate = CommonUtils.dataToStr(calendar.getTime(),"yyyy-MM-dd");
        }
        if(StringUtils.isNotEmpty(produceddate)){
            batchno = "P"+CommonUtils.dateStringChange(produceddate)+batchno1;
        }
        Map map = new HashMap();
        map.put("batchno", batchno);
        map.put("produceddate", produceddate);
        return map;
    }
	/**
	 * 调拨出库，减去调拨待发量 生成仓库调拨入库量
	 * @param summarybatchid			调拨的批次现存量编号
	 * @param allotwaitnum				调拨待发数量
	 * @param realallotnum				实际调出数量
	 * @param enterStorageid			调入仓库
	 * @param billmodel					单据类型
	 * @param billid					单据编号
	 * @param remark					备注
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jun 27, 2013
	 */
	public boolean sendStorageOutSummaryAllotwaitnum(String summarybatchid,BigDecimal allotwaitnum,BigDecimal realallotnum,String enterStorageid,String billmodel,String billid,String remark) throws Exception{
		StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(summarybatchid);
		GoodsInfo goodsInfo = getAllGoodsInfoByID(storageSummaryBatch.getGoodsid());
		boolean flag = false;
		boolean enterflag = false;
		if(null!=storageSummaryBatch){
			addStorageSummarySendLog(billmodel, billid, storageSummaryBatch, realallotnum, remark);
			storageSummaryBatch.setBatchstate("1");
			//商品批次数量为0是 标记关闭
			StorageInfo outStorage = getStorageInfoByID(storageSummaryBatch.getStorageid());
			if("1".equals(outStorage.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
				if(storageSummaryBatch.getExistingnum().compareTo(BigDecimal.ZERO)==0){
					storageSummaryBatch.setBatchstate("2");
				}
			}
			StorageSummary storageSummary = storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid(storageSummaryBatch.getGoodsid(), storageSummaryBatch.getStorageid());
			int i = storageSummaryMapper.updateStorageSummaryAlloteSubtract(storageSummary.getStorageid(),storageSummary.getGoodsid(),storageSummary.getAllotwaitnum(),realallotnum,storageSummary.getVersion());
			int z = storageSummaryMapper.updateStorageSummaryBacthAlloteSubtract(storageSummaryBatch.getId(),storageSummaryBatch.getAllotwaitnum(),realallotnum,storageSummaryBatch.getBatchstate(),storageSummaryBatch.getVersion());
			flag=i>0&&z>0;
		}
		if(!flag){
			throw new Exception("库存调拨出库待发量更新失败;"+"仓库："+storageSummaryBatch.getStorageid()+",商品："+storageSummaryBatch.getGoodsid());
		}
		int t=0;
		StorageSummary enterStorageSummay = storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid(storageSummaryBatch.getGoodsid(), enterStorageid);
		if(enterStorageSummay==null){
			enterStorageSummay=new StorageSummary();
			String summaryid = CommonUtils.getDataNumberSendsWithRand();
			enterStorageSummay.setId(summaryid);
			enterStorageSummay.setGoodsid(storageSummaryBatch.getGoodsid());
			enterStorageSummay.setBrandid(goodsInfo.getBrand());
			Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
			if(null!=brand){
				enterStorageSummay.setBranddept(brand.getDeptid());
			}
			enterStorageSummay.setBarcode(goodsInfo.getBarcode());
			enterStorageSummay.setStorageid(enterStorageid);
			StorageInfo storageInfo = getStorageInfoByID(enterStorageid);
			enterStorageSummay.setIstotalcontrol(storageInfo.getIstotalcontrol());
			enterStorageSummay.setIssendstorage(storageInfo.getIssendstorage());
			// 安全库存
			BigDecimal safenum = getStorageInventoryByGoodsidAndStorageid(storageSummaryBatch.getGoodsid(), enterStorageid);

			// 安全库存
			enterStorageSummay.setSafenum(safenum);
			//仓库现存量 = 调拨量
			enterStorageSummay.setExistingnum(BigDecimal.ZERO);
			//仓库可用量  = 调拨量
			enterStorageSummay.setUsablenum(BigDecimal.ZERO);
			//仓库预计可用量 = 调拨量
			enterStorageSummay.setProjectedusablenum(BigDecimal.ZERO);
			enterStorageSummay.setCostprice(storageSummaryBatch.getCostprice());
			//主单位信息
			enterStorageSummay.setUnitid(goodsInfo.getMainunit());
			MeteringUnit meteringUnit = getMeteringUnitById(goodsInfo.getMainunit());
			if(null!=meteringUnit){
				enterStorageSummay.setUnitname(meteringUnit.getName());
			}
			enterStorageSummay.setAllotenternum(realallotnum);
			//默认辅单位信息
			MeteringUnit auxunit = getGoodsAuxUnitInfoByGoodsid(storageSummaryBatch.getGoodsid());
			if(null!=auxunit){
				enterStorageSummay.setAuxunitid(auxunit.getId());
				enterStorageSummay.setAuxunitname(auxunit.getName());
			}
			// 添加库存现存量总量信息
			t=storageSummaryMapper.addStorageSummary(enterStorageSummay);
		}else{
			t=storageSummaryMapper.updateStorageSummaryAllotEnter(enterStorageid,storageSummaryBatch.getGoodsid(),realallotnum,enterStorageSummay.getVersion());
		}
		enterflag=t>0;
		if(!enterflag){
			throw new Exception("库存调拨入库待入量更新失败;"+"仓库："+enterStorageid+",商品："+storageSummaryBatch.getGoodsid());
		}
		return flag;
	}
	/**
	 * 调拨出库审核 更新出库仓库成本价（需要在库存数量更新后执行）
	 * @param billid                 单据编号
	 * @param detailid               明细编号
	 * @param outStorageid          出库仓库
	 * @param inStorageid           入库仓库
	 * @param goodsid               商品编号
	 * @param price                 调拨出库价格
	 * @param cprice                 调拨成本价
	 * @param unitnum               调拨出库数量
	 * @return
	 * @throws Exception
	 */
	public boolean updateStorageOutCostpriceByAllot(String billid,String detailid,String outStorageid,String inStorageid,String goodsid,BigDecimal price,BigDecimal cprice,BigDecimal unitnum) throws Exception{
		GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
		if(null==goodsInfo){
			throw new Exception("找不到商品："+goodsid+"，回滚数据。");
		}
		//更新出库仓库成本价
		StorageSummary outStorageSummary = getStorageSummaryByStorageidAndGoodsid(outStorageid,goodsid);
		//出库使用成本价计算
		BigDecimal outAmount = unitnum.multiply(cprice);
		BigDecimal outStorageAmount = outStorageSummary.getExistingnum().add(unitnum).multiply(outStorageSummary.getCostprice()).subtract(outAmount);
		//库存为0时，成本价为最新成本价 库存金额为剩余的金额
		int i = 0;
		if(outStorageSummary.getExistingnum().compareTo(BigDecimal.ZERO)!=0){
			//调拨已经库存变更了 成本价=（当前库存金额-调拨出库金额）/当前数量
			BigDecimal newOutStoragePrice = outStorageAmount.divide(outStorageSummary.getExistingnum(),6,BigDecimal.ROUND_HALF_UP);
			//更新仓库成本价
			i = getStorageSummaryMapper().updateStorageGoodsCostprice(outStorageid, goodsid, outStorageSummary.getExistingnum(), newOutStoragePrice, outStorageAmount);
		}else{
			//更新仓库成本价
			i = getStorageSummaryMapper().updateStorageGoodsCostprice(outStorageid, goodsid, outStorageSummary.getExistingnum(),outStorageSummary.getCostprice(),BigDecimal.ZERO);
		}
		if(i>0){
			//出库仓库或者入库仓库不是参与总量控制的话 需要更新商品总的成本价
			StorageInfo outStorage = getStorageInfoByID(outStorageid);
			if(!"1".equals(outStorage.getIstotalcontrol())){
				//成本价变更记录 公式
				String costChangeRemark = "调拨造成成本价变更，调出仓库不参与总量控制，单据："+billid;
				StorageSummary storageSummary = getStorageSummaryMapper().getStorageSummarySumByGoodsidInTotal(goodsInfo.getId());
				BigDecimal totalAmount = BigDecimal.ZERO;
				if (null != storageSummary) {
					BigDecimal eNum = storageSummary.getExistingnum().subtract(unitnum);
					totalAmount = eNum.multiply(goodsInfo.getNewstorageprice()).setScale(6, BigDecimal.ROUND_HALF_UP);
					totalAmount = totalAmount.add(outAmount);
					costChangeRemark +="，调出仓库不参与总量控制，成本=（（当前库存数量-调拨数量）*当前成本价+调拨金额）/当前库存数量"
							+"(("+storageSummary.getExistingnum().toString()+"-"+unitnum.toString()+")*"+goodsInfo.getNewstorageprice().toString()+"+("+unitnum.toString()+"*"+price.toString()+"))/"+storageSummary.getExistingnum().toString();

				}
				BigDecimal costprice = goodsInfo.getNewstorageprice();
				if (storageSummary.getExistingnum().compareTo(BigDecimal.ZERO) != 0) {
					costprice = totalAmount.divide(storageSummary.getExistingnum(), 6, BigDecimal.ROUND_HALF_UP);
				}else{
					if(totalAmount.compareTo(BigDecimal.ZERO)!=0){
						if ("1".equals(outStorage.getIstotalcontrol())) {
							addCostDiffAmountShare("3", billid, detailid, outStorageid, goodsid, totalAmount, "调拨0库存差额");
						}
					}
				}
				GoodsInfo upGoodsInfo = new GoodsInfo();
				upGoodsInfo.setId(goodsInfo.getId());
				//更新商品成本价
				upGoodsInfo.setNewstorageprice(costprice);
				//更新商品成本价
				updateGoodsCostprice(upGoodsInfo,costChangeRemark,billid);
			}
			return true;
		}else{
			throw new Exception("仓库成本价更新失败，回滚数据。");
		}
	}

	/**
	 * 调拨入库，减去调拨待发量 生成仓库调拨入库量
	 * @param summarybatchid			调拨的批次现存量编号
	 * @param allotwaitnum				调拨待发数量
	 * @param realallotnum				实际调出数量
	 * @param enterStorageid			调入仓库
	 * @param billmodel					单据类型
	 * @param billid					单据编号
	 * @param remark					备注
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jun 27, 2013
	 */
	public boolean sendStorageEnterSummaryAllotwaitnum(String summarybatchid,BigDecimal allotwaitnum,BigDecimal realallotnum,String enterStorageid,String enterstoragelocationid,String enterproduceddate,
													   String billmodel,String billid,String remark) throws Exception{
		StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(summarybatchid);
		GoodsInfo goodsInfo = getAllGoodsInfoByID(storageSummaryBatch.getGoodsid());
		boolean flag = false;
		if(null!=storageSummaryBatch){
//			addStorageSummarySendLog(billmodel, billid, storageSummaryBatch, realallotnum, remark);
//			storageSummaryBatch.setBatchstate("1");
//			//商品批次数量为0是 标记关闭
//			StorageInfo outStorage = getStorageInfoByID(storageSummaryBatch.getStorageid());
//			if("1".equals(outStorage.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
//				if(storageSummaryBatch.getExistingnum().compareTo(BigDecimal.ZERO)==0){
//					storageSummaryBatch.setBatchstate("2");
//				}
//			}
			StorageSummary storageSummary = storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid(storageSummaryBatch.getGoodsid(), storageSummaryBatch.getStorageid());
//			int i = storageSummaryMapper.updateStorageSummaryAlloteSubtract(storageSummary.getStorageid(),storageSummary.getGoodsid(),storageSummary.getAllotwaitnum(),realallotnum);
//			int z = storageSummaryMapper.updateStorageSummaryBacthAlloteSubtract(storageSummaryBatch.getId(),storageSummaryBatch.getAllotwaitnum(),realallotnum,storageSummaryBatch.getBatchstate());
//			if(i>0&&z>0){
			StorageSummary enterStorageSummay = storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid(storageSummaryBatch.getGoodsid(), enterStorageid);
			boolean addEnterStorageSummaryFlag = false;
			if(null==enterStorageSummay){
				addEnterStorageSummaryFlag = true;
				enterStorageSummay = new StorageSummary();
				String summaryid = CommonUtils.getDataNumberSendsWithRand();
				enterStorageSummay.setId(summaryid);
			}
			StorageInfo enterStorage = getStorageInfoByID(enterStorageid);
			StorageSummaryBatch storageSummaryBatch2 = null;
			//仓库和商品是批次管理时 生成新的批次量 更新仓库现存量
			if("1".equals(enterStorage.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
				storageSummaryBatch2 = addOrGetStorageSummaryBatchByStorageidAndProduceddate(enterStorageid, storageSummaryBatch.getGoodsid(), enterproduceddate);
			}else{
				storageSummaryBatch2 = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(enterStorageid, storageSummaryBatch.getGoodsid());
			}
			if(null!=storageSummaryBatch2){
				//现存量更新日志
				addStorageSummaryReceiveLog(billmodel, billid, storageSummaryBatch2,realallotnum, remark);
				storageSummaryMapper.updateStorageSummaryBacthAlloteAdd(storageSummaryBatch2.getId(),storageSummaryBatch2.getExistingnum(),realallotnum,storageSummaryBatch2.getVersion());
			}else{
				StorageSummaryBatch enterstorageSummaryBatch = new StorageSummaryBatch();
				enterstorageSummaryBatch.setId(CommonUtils.getDataNumberSendsWithRand());
				enterstorageSummaryBatch.setSummaryid(enterStorageSummay.getId());
				enterstorageSummaryBatch.setGoodsid(storageSummaryBatch.getGoodsid());
				enterstorageSummaryBatch.setBrandid(goodsInfo.getBrand());
				Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
				if(null!=brand){
					enterstorageSummaryBatch.setBranddept(brand.getDeptid());
				}
				enterstorageSummaryBatch.setBarcode(goodsInfo.getBarcode());
				enterstorageSummaryBatch.setStorageid(enterStorageid);
				//调入仓库和商品 是批次管理时 根据生产日期生成批次号与截止日期
				if("1".equals(enterStorage.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
					enterstorageSummaryBatch.setStoragelocationid(enterstoragelocationid);
					Map batchMap = getBatchno(goodsInfo.getId(), enterproduceddate);
					if(null!=batchMap){
						String batchno = (String) batchMap.get("batchno");
						String deadline = (String) batchMap.get("deadline");
						enterstorageSummaryBatch.setBatchno(batchno);
						enterstorageSummaryBatch.setProduceddate(enterproduceddate);
						enterstorageSummaryBatch.setDeadline(deadline);
					}
				}

				enterstorageSummaryBatch.setExistingnum(realallotnum);
				enterstorageSummaryBatch.setUsablenum(realallotnum);
				enterstorageSummaryBatch.setIntinum(new BigDecimal(0));

				enterstorageSummaryBatch.setUnitid(storageSummaryBatch.getUnitid());
				enterstorageSummaryBatch.setUnitname(storageSummaryBatch.getUnitname());
				enterstorageSummaryBatch.setAuxunitid(storageSummaryBatch.getAuxunitid());
				enterstorageSummaryBatch.setAuxunitname(storageSummaryBatch.getAuxunitname());
				enterstorageSummaryBatch.setPrice(storageSummaryBatch.getPrice());
				enterstorageSummaryBatch.setEnterdate(CommonUtils.getTodayDataStr());
				//现存量更新日志
				addStorageSummaryReceiveLog(billmodel, billid, enterstorageSummaryBatch, realallotnum, remark);
				storageSummaryMapper.addStorageSummaryBatch(enterstorageSummaryBatch);
			}

			if(!addEnterStorageSummaryFlag){
                //需要计算调拨待入量
                int y = storageSummaryMapper.updateStorageSummaryAlloteAddEnter(enterStorageSummay.getStorageid(),enterStorageSummay.getGoodsid(),enterStorageSummay.getExistingnum(),realallotnum,enterStorageSummay.getVersion());
                flag = y>0;
			}else{
				enterStorageSummay.setGoodsid(storageSummaryBatch.getGoodsid());
				enterStorageSummay.setBrandid(goodsInfo.getBrand());
				Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
				if(null!=brand){
					enterStorageSummay.setBranddept(brand.getDeptid());
				}
				enterStorageSummay.setBarcode(goodsInfo.getBarcode());
				enterStorageSummay.setStorageid(enterStorageid);
				StorageInfo storageInfo = getStorageInfoByID(enterStorageid);
				enterStorageSummay.setIstotalcontrol(storageInfo.getIstotalcontrol());
				enterStorageSummay.setIssendstorage(storageInfo.getIssendstorage());
				// 安全库存
				BigDecimal safenum = getStorageInventoryByGoodsidAndStorageid(storageSummaryBatch.getGoodsid(), enterStorageid);

				// 安全库存
				enterStorageSummay.setSafenum(safenum);
				//仓库现存量 = 调拨量
				enterStorageSummay.setExistingnum(realallotnum);
				//仓库可用量  = 调拨量
				enterStorageSummay.setUsablenum(realallotnum);
				//仓库预计可用量 = 调拨量
				enterStorageSummay.setProjectedusablenum(realallotnum);
				enterStorageSummay.setCostprice(storageSummary.getCostprice());
				//主单位信息
				enterStorageSummay.setUnitid(goodsInfo.getMainunit());
				MeteringUnit meteringUnit = getMeteringUnitById(goodsInfo.getMainunit());
				if(null!=meteringUnit){
					enterStorageSummay.setUnitname(meteringUnit.getName());
				}
				//默认辅单位信息
				MeteringUnit auxunit = getGoodsAuxUnitInfoByGoodsid(storageSummaryBatch.getGoodsid());
				if(null!=auxunit){
					enterStorageSummay.setAuxunitid(auxunit.getId());
					enterStorageSummay.setAuxunitname(auxunit.getName());
				}
				// 添加库存现存量总量信息
				int y = storageSummaryMapper.addStorageSummary(enterStorageSummay);
				flag = y>0;
			}
//			}
		}
		if(!flag){
			throw new Exception("库存调拨入库待发量更新失败;"+"仓库："+storageSummaryBatch.getStorageid()+",商品："+storageSummaryBatch.getGoodsid());
		}
		return flag;
	}

	/**
	 * 调拨入库审核 更新出库仓库成本价（需要在库存数量更新后执行）
	 * @param billid                 单据编号
	 * @param detailid               明细编号
	 * @param inStorageid           入库仓库
	 * @param goodsid               商品编号
	 * @param price                 调拨出库价格
	 * @param cprice                 调拨成本价
	 * @param unitnum               调拨出库数量
	 * @return
	 * @throws Exception
	 */
	public boolean updateStorageEnterCostpriceByAllot(String billid,String detailid,String inStorageid,String goodsid,BigDecimal price,BigDecimal cprice,BigDecimal unitnum) throws Exception{
		GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
		if(null==goodsInfo){
			throw new Exception("找不到商品："+goodsid+"，回滚数据。");
		}
		//调拨入库 成本价
		StorageSummary inStorageSummary = getStorageSummaryByStorageidAndGoodsid(inStorageid,goodsid);
		BigDecimal oldUnitnum = inStorageSummary.getExistingnum().subtract(unitnum);
		//入库使用调拨单价计算
		BigDecimal enterAmount = unitnum.multiply(price);
		BigDecimal inStorageAmount = oldUnitnum.multiply(inStorageSummary.getCostprice()).add(enterAmount);

		BigDecimal newInStoragePrice = BigDecimal.ZERO;
		if(BigDecimal.ZERO.compareTo(inStorageSummary.getExistingnum()) != 0) {
			newInStoragePrice = inStorageAmount.divide(inStorageSummary.getExistingnum(),6,BigDecimal.ROUND_HALF_UP);
		}
		//更新仓库成本价
		int j = getStorageSummaryMapper().updateStorageGoodsCostprice(inStorageid, goodsid, inStorageSummary.getExistingnum(),newInStoragePrice,inStorageAmount);
		if(j>0){
			//出库仓库或者入库仓库不是参与总量控制的话 需要更新商品总的成本价
			StorageInfo inStorage = getStorageInfoByID(inStorageid);
			if(!"1".equals(inStorage.getIstotalcontrol())){
				//成本价变更记录 公式
				String costChangeRemark = "调拨造成成本价变更，调出仓库或调入仓库不参与总量控制，单据："+billid;
				StorageSummary storageSummary = getStorageSummaryMapper().getStorageSummarySumByGoodsidInTotal(goodsInfo.getId());
				BigDecimal totalAmount = BigDecimal.ZERO;
				if (null != storageSummary) {
					BigDecimal eNum = storageSummary.getExistingnum().add(unitnum);
					totalAmount = eNum.multiply(goodsInfo.getNewstorageprice()).setScale(6, BigDecimal.ROUND_HALF_UP);
					totalAmount = totalAmount.subtract(enterAmount);
					costChangeRemark +="，调入仓库不参与总量控制，成本=（（当前库存数量+调拨数量）*当前成本价-调拨金额）/当前库存数量"
							+"(("+storageSummary.getExistingnum().toString()+"+"+unitnum.toString()+")*"+goodsInfo.getNewstorageprice().toString()+"-("+unitnum.toString()+"*"+price.toString()+"))/"+storageSummary.getExistingnum().toString();

				}
				BigDecimal costprice = goodsInfo.getNewstorageprice();
				if (storageSummary.getExistingnum().compareTo(BigDecimal.ZERO) != 0) {
					costprice = totalAmount.divide(storageSummary.getExistingnum(), 6, BigDecimal.ROUND_HALF_UP);
				}else{
//					if(totalAmount.compareTo(BigDecimal.ZERO)!=0){
//						if ("1".equals(outStorage.getIstotalcontrol())) {
//							addCostDiffAmountShare("3", billid, detailid, outStorageid, goodsid, totalAmount, "调拨0库存差额");
//						}
//					}
				}
				GoodsInfo upGoodsInfo = new GoodsInfo();
				upGoodsInfo.setId(goodsInfo.getId());
				//更新商品成本价
				upGoodsInfo.setNewstorageprice(costprice);
				//更新商品成本价
				updateGoodsCostprice(upGoodsInfo,costChangeRemark,billid);
			}
			return true;
		}else{
			throw new Exception("仓库调拨入库成本价更新失败，回滚数据。");
		}
	}
	/**
	 * 调拨出库，减去调拨待发量 生成仓库调拨入库量
	 * @param summarybatchid			调拨的批次现存量编号
	 * @param allotwaitnum				调拨待发数量
	 * @param realallotnum				实际调出数量
	 * @param enterStorageid			调入仓库
	 * @param billmodel					单据类型
	 * @param billid					单据编号
	 * @param remark					备注
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jun 27, 2013
	 */
	public boolean sendOppauditStorageOutSummaryAllotwaitnum(String summarybatchid,BigDecimal allotwaitnum,BigDecimal realallotnum,String enterStorageid,String billmodel,String billid,String remark) throws Exception{
		StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(summarybatchid);
		GoodsInfo goodsInfo = getAllGoodsInfoByID(storageSummaryBatch.getGoodsid());
		boolean flag = false;
		boolean enterflag = false;
		if(null!=storageSummaryBatch){
			addStorageSummarySendLog(billmodel, billid, storageSummaryBatch, realallotnum, remark);
			storageSummaryBatch.setBatchstate("1");
			//商品批次数量为0是 标记关闭
			StorageInfo outStorage = getStorageInfoByID(storageSummaryBatch.getStorageid());
			if("1".equals(outStorage.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
				if(storageSummaryBatch.getExistingnum().compareTo(BigDecimal.ZERO)==0){
					storageSummaryBatch.setBatchstate("2");
				}
			}
			StorageSummary storageSummary = storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid(storageSummaryBatch.getGoodsid(), storageSummaryBatch.getStorageid());
			int i = storageSummaryMapper.updateStorageSummaryAlloteSubtract(storageSummary.getStorageid(),storageSummary.getGoodsid(),storageSummary.getAllotwaitnum(),realallotnum,storageSummary.getVersion());
			int z = storageSummaryMapper.updateStorageSummaryBacthAlloteSubtract(storageSummaryBatch.getId(),storageSummaryBatch.getAllotwaitnum(),realallotnum,storageSummaryBatch.getBatchstate(),storageSummaryBatch.getVersion());
			StorageSummary enterStorageSummary = storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid(storageSummaryBatch.getGoodsid(), enterStorageid);
			int t=storageSummaryMapper.updateStorageSummaryAllotEnter(enterStorageid,storageSummary.getGoodsid(),realallotnum,enterStorageSummary.getVersion());
			flag=i>0&&z>0;
			enterflag=t>0;
		}
		if(!flag){
			throw new Exception("反审库存调拨出库待发量更新失败;"+"仓库："+storageSummaryBatch.getStorageid()+",商品："+storageSummaryBatch.getGoodsid());
		}
		if(!enterflag){
			throw new Exception("库存调拨出库审核，入库库待入量更新失败;"+"仓库："+enterStorageid+",商品："+storageSummaryBatch.getGoodsid());
		}
		return flag;
	}

	/**
	 * 调拨出库反审 更新出库仓库成本价（需要在库存数量更新后执行）
	 * @param billid                 单据编号
	 * @param detailid               明细编号
	 * @param outStorageid          出库仓库
	 * @param inStorageid           入库仓库
	 * @param goodsid               商品编号
	 * @param price                 调拨出库价格
	 * @param cprice                 调拨成本价
	 * @param unitnum               调拨出库数量
	 * @return
	 * @throws Exception
	 */
	public boolean updateOppauditStorageOutCostpriceByAllot(String billid,String detailid,String outStorageid,String inStorageid,String goodsid,BigDecimal price,BigDecimal cprice,BigDecimal unitnum) throws Exception{
		GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
		if(null==goodsInfo){
			throw new Exception("找不到商品："+goodsid+"，回滚数据。");
		}
		//更新出库仓库成本价
		StorageSummary outStorageSummary = getStorageSummaryByStorageidAndGoodsid(outStorageid,goodsid);
		//出库使用成本价计算
		BigDecimal outAmount = unitnum.multiply(cprice);
		BigDecimal outStorageAmount = outStorageSummary.getExistingnum().add(unitnum).multiply(outStorageSummary.getCostprice()).subtract(outAmount);
		//库存为0时，成本价为最新成本价 库存金额为剩余的金额
		int i = 0;
		if(outStorageSummary.getExistingnum().compareTo(BigDecimal.ZERO)!=0){
			//调拨已经库存变更了 成本价=（当前库存金额-调拨出库金额）/当前数量
			BigDecimal newOutStoragePrice = outStorageAmount.divide(outStorageSummary.getExistingnum(),6,BigDecimal.ROUND_HALF_UP);
			//更新仓库成本价
			i = getStorageSummaryMapper().updateStorageGoodsCostprice(outStorageid, goodsid, outStorageSummary.getExistingnum(), newOutStoragePrice, outStorageAmount);
		}else{
			//更新仓库成本价
			i = getStorageSummaryMapper().updateStorageGoodsCostprice(outStorageid, goodsid, outStorageSummary.getExistingnum(),outStorageSummary.getCostprice(),BigDecimal.ZERO);
		}
		if(i>0){
			//出库仓库或者入库仓库不是参与总量控制的话 需要更新商品总的成本价
			StorageInfo outStorage = getStorageInfoByID(outStorageid);
			if(!"1".equals(outStorage.getIstotalcontrol())){
				//成本价变更记录 公式
				String costChangeRemark = "调拨造成成本价变更，调出仓库不参与总量控制，单据："+billid;
				StorageSummary storageSummary = getStorageSummaryMapper().getStorageSummarySumByGoodsidInTotal(goodsInfo.getId());
				BigDecimal totalAmount = BigDecimal.ZERO;
				if (null != storageSummary) {
					BigDecimal eNum = storageSummary.getExistingnum().subtract(unitnum);
					totalAmount = eNum.multiply(goodsInfo.getNewstorageprice()).setScale(6, BigDecimal.ROUND_HALF_UP);
					totalAmount = totalAmount.add(outAmount);
					costChangeRemark +="，调出仓库不参与总量控制，成本=（（当前库存数量-调拨数量）*当前成本价+调拨金额）/当前库存数量"
							+"(("+storageSummary.getExistingnum().toString()+"-"+unitnum.toString()+")*"+goodsInfo.getNewstorageprice().toString()+"+("+unitnum.toString()+"*"+price.toString()+"))/"+storageSummary.getExistingnum().toString();

				}
				BigDecimal costprice = goodsInfo.getNewstorageprice();
				if (storageSummary.getExistingnum().compareTo(BigDecimal.ZERO) != 0) {
					costprice = totalAmount.divide(storageSummary.getExistingnum(), 6, BigDecimal.ROUND_HALF_UP);
				}else{
					if(totalAmount.compareTo(BigDecimal.ZERO)!=0){
						if ("1".equals(outStorage.getIstotalcontrol())) {
							addCostDiffAmountShare("3", billid, detailid, outStorageid, goodsid, totalAmount, "调拨0库存差额");
						}
					}
				}
				GoodsInfo upGoodsInfo = new GoodsInfo();
				upGoodsInfo.setId(goodsInfo.getId());
				//更新商品成本价
				upGoodsInfo.setNewstorageprice(costprice);
				//更新商品成本价
				updateGoodsCostprice(upGoodsInfo,costChangeRemark,billid);
			}
			return true;
		}else{
			throw new Exception("仓库成本价更新失败，回滚数据。");
		}
	}

	/**
	 * 库存商品数量增加时，更新最新采购价 最新成本价
	 * @param storageid			仓库编号
	 * @param goodsid			商品编号
	 * @param unitnum			商品数量
	 * @param buyprice			商品单价
	 * @param isChangeNum		是否变更库存数量
	 * @param isUpdateNewBuyprice	是否更新最新采购价
	 * @param taxamount	是否调整金额
	 * @param isPurchaseEnter       是否采购入库
	 * @param billid                    单据编号
	 * @param detailid                 明细编号
	 * @throws Exception
	 * @author luoq
	 * @date 2018年3月23日
	 */
	public boolean updateGoodsPriceByAdd(String storageid,String goodsid,BigDecimal unitnum,BigDecimal buyprice,boolean isChangeNum,boolean isUpdateNewBuyprice, boolean isPurchaseEnter,String billid,String detailid,BigDecimal taxamount) throws Exception{
		boolean flag = false;
		StorageInfo storageInfo = getStorageInfoByID(storageid);
		GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
		//入库金额 = 实际入库金额+成本未摊分金额
		BigDecimal enterAmount = taxamount;
		//判断仓库是否参与总量控制，参与总量控制的仓库 计算成本价
		if("1".equals(storageInfo.getIstotalcontrol())) {
			//更新商品总的成本价
			//入库前现存量
			BigDecimal oldExistingnum = BigDecimal.ZERO;
			BigDecimal newExistingnum = BigDecimal.ZERO;
			BigDecimal totalAmount = BigDecimal.ZERO;
			//成本变更记录 公式
			String costChangeRemark = "单据："+billid+ "，成本=（变更前库存数量*变更前成本价+入库金额(成本未分摊)）/当前库存数量";
			StorageSummary storageSummary = getStorageSummaryMapper().getStorageSummarySumByGoodsidInTotal(goodsInfo.getId());
			if (null != storageSummary) {
				newExistingnum = storageSummary.getExistingnum();
				if (isChangeNum) {
					oldExistingnum = storageSummary.getExistingnum().subtract(unitnum);
				} else {
					oldExistingnum = storageSummary.getExistingnum();
				}
				totalAmount = oldExistingnum.multiply(goodsInfo.getNewstorageprice()).setScale(6, BigDecimal.ROUND_HALF_UP);
				costChangeRemark += "成本=(("+oldExistingnum.toString()+"*"+goodsInfo.getNewstorageprice().toString();
			}
			if (isPurchaseEnter) {
				//仓库独立核算时 相同仓库的未分摊成本 只能摊到同一个仓库中去
				String isStorageAccount = getStorageIsAccount(storageid);
				String countStorageid = storageid;
				if(!"1".equals(isStorageAccount)){
					countStorageid = null;
				}
				//未计算到成本的采购差额
				BigDecimal costDiffAmount = getBaseGoodsMapper().getCostDiffAmountByGoodsid(countStorageid, goodsid);
				if (null == costDiffAmount) {
					costDiffAmount = BigDecimal.ZERO;
				}
				BigDecimal totalEnterAmount = enterAmount.add(costDiffAmount);
				//总金额 =  入库前现存量*入库前成本价+入库量*入库单价+差额
				totalAmount = totalAmount.add(totalEnterAmount);
				//差额已摊分 关闭成本差额记录
				getBaseGoodsMapper().closeCostDiffAmountByGoodsid(countStorageid,goodsid,billid,detailid);

				costChangeRemark += "+("+taxamount+"+(成本未分摊金额："+costDiffAmount.toString()+")))";
			} else {
				totalAmount = totalAmount.add(enterAmount);
				costChangeRemark += "+("+taxamount+"))";
			}

			BigDecimal costprice = goodsInfo.getNewstorageprice();
			if (newExistingnum.compareTo(BigDecimal.ZERO) == 1) {
				costprice = totalAmount.divide(newExistingnum, 6, BigDecimal.ROUND_HALF_UP);
			}
			costChangeRemark += "/"+newExistingnum.toString();
			GoodsInfo upGoodsInfo = new GoodsInfo();
			upGoodsInfo.setId(goodsInfo.getId());
			//更新商品成本价
			upGoodsInfo.setNewstorageprice(costprice);
			if (isUpdateNewBuyprice) {
				upGoodsInfo.setNewbuydate(CommonUtils.getTodayDataStr());
				if (BigDecimal.ZERO.compareTo(buyprice) != 0) {
					upGoodsInfo.setNewbuyprice(buyprice);
				}
			}
			//更新商品成本价
			updateGoodsCostprice(upGoodsInfo,costChangeRemark,billid);
		}
		//仓库不参与总量控制 也计算该仓库的成本
		//更新仓库的商品成本价  仓库成本独立核算
		StorageSummary storageSum = getStorageSummaryByStorageidAndGoodsid(storageid, goodsInfo.getId());
		if(null!=storageSum){
			//仓库最新数量
			BigDecimal oldStoragenum = BigDecimal.ZERO;
			BigDecimal totalStorageAmount = BigDecimal.ZERO;
			BigDecimal newStoragenum = storageSum.getExistingnum();
			if(isChangeNum){
				oldStoragenum = storageSum.getExistingnum().subtract(unitnum);
			}else{
				oldStoragenum = storageSum.getExistingnum();
			}
			//库存数量为0的时候 库存金额直接取库存金额
			if(oldStoragenum.compareTo(BigDecimal.ZERO)==0){
				totalStorageAmount = storageSum.getStorageamount();
			}else{
				totalStorageAmount = oldStoragenum.multiply(storageSum.getCostprice()).setScale(6, BigDecimal.ROUND_HALF_UP);
			}
			totalStorageAmount = totalStorageAmount.add(enterAmount);
			if(newStoragenum.compareTo(BigDecimal.ZERO)!=0){
				BigDecimal storageCostprice = totalStorageAmount.divide(newStoragenum,6,BigDecimal.ROUND_HALF_UP);
				//更新仓库成本价
				getStorageSummaryMapper().updateStorageGoodsCostprice(storageid, goodsInfo.getId(),storageSum.getExistingnum(), storageCostprice,BigDecimal.ZERO);
			}else{
				//更新仓库成本价
				getStorageSummaryMapper().updateStorageGoodsCostprice(storageid, goodsInfo.getId(),storageSum.getExistingnum(), storageSum.getCostprice(),totalStorageAmount);
			}
		}
		flag = true;
		return flag;
	}

}
