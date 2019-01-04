/**
 * @(#)StockInitServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 7, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.MeteringUnit;
import com.hd.agent.basefiles.model.StorageInfo;
import com.hd.agent.basefiles.model.TaxType;
import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.dao.StockInitMapper;
import com.hd.agent.storage.model.StockInit;
import com.hd.agent.storage.model.StorageSummaryBatch;
import com.hd.agent.storage.service.IStockInitService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 库存初始化service实现类
 * @author chenwei
 */
public class StockInitServiceImpl extends BaseStorageServiceImpl implements IStockInitService {
	
	/**
	 * 库存初始化dao
	 */
	private StockInitMapper stockInitMapper;

	public StockInitMapper getStockInitMapper() {
		return stockInitMapper;
	}

	public void setStockInitMapper(StockInitMapper stockInitMapper) {
		this.stockInitMapper = stockInitMapper;
	}

	@Override
	public boolean addStockInitList(List<StockInit> list, String status) throws Exception {
		SysUser sysUser = getSysUser();
		if(null==status){
			//默认暂存状态
			status = "1";
		}
		for(StockInit stockInit : list){
			stockInit.setStatus(status);
			stockInit.setAdduserid(sysUser.getUserid());
			stockInit.setAddusername(sysUser.getName());
			stockInit.setAdddeptid(sysUser.getDepartmentid());
			stockInit.setAdddeptname(sysUser.getDepartmentname());
			stockInitMapper.addStockInit(stockInit);
		}
		return true;
	}

	@Override
	public PageData showStockInitList(PageMap pageMap) throws Exception {
		//单表取字段权限
		String cols = getAccessColumnList("t_storage_stockinit",null);
		pageMap.setCols(cols);
		//数据权限
		String dataSql = getDataAccessRule("t_storage_stockinit", null);
		pageMap.setDataSql(dataSql);
		PageData pageData = new PageData(stockInitMapper.showStockInitCount(pageMap),stockInitMapper.showStockInitList(pageMap),pageMap);
		List<StockInit> list = pageData.getList();
		for(StockInit stockInit : list){
            GoodsInfo goodsInfo = getGoodsInfoByID(stockInit.getGoodsid());
            if(null != goodsInfo){
                stockInit.setGoodsInfo(goodsInfo);
                stockInit.setGoodsname(goodsInfo.getName());
            }
            StorageInfo storageInfo = getStorageInfoByID(stockInit.getStorageid());
            if(null != storageInfo){
                stockInit.setStorageInfo(storageInfo);
                stockInit.setStoragename(storageInfo.getName());
            }
			stockInit.setStorageLocation(getStorageLocationByID(stockInit.getStoragelocationid()));
		}
		return pageData;
	}

	@Override
	public PageData showStockInitListByStorageid(PageMap pageMap) throws Exception {
		List<StockInit> list = stockInitMapper.showStockInitList(pageMap);
		for(StockInit stockInit : list){
            GoodsInfo goodsInfo = getGoodsInfoByID(stockInit.getGoodsid());
            if(null != goodsInfo){
                stockInit.setGoodsInfo(goodsInfo);
                stockInit.setSpellcode(goodsInfo.getSpell());
                stockInit.setGoodsname(goodsInfo.getName());
            }
            StorageInfo storageInfo = getStorageInfoByID(stockInit.getStorageid());
            if(null != storageInfo){
                stockInit.setStorageInfo(storageInfo);
                stockInit.setStoragename(storageInfo.getName());
            }
			stockInit.setStorageLocation(getStorageLocationByID(stockInit.getStoragelocationid()));
		}
		PageData pageData = new PageData(stockInitMapper.showStockInitCount(pageMap),list,pageMap);
		StockInit stockInit = stockInitMapper.showStockInitSum(pageMap);
		if(null!=stockInit){
            GoodsInfo sumGoodsInfo = new GoodsInfo();
            sumGoodsInfo.setName("合计");
            stockInit.setGoodsname("合计");
            stockInit.setGoodsInfo(sumGoodsInfo);
			List footer = new ArrayList();
			stockInit.setAuxnumdetail(stockInit.getAuxnum().setScale(3, BigDecimal.ROUND_HALF_UP)+"箱");
			footer.add(stockInit);
			pageData.setFooter(footer);
		}
		return pageData;
	}

	@Override
	public boolean addStockInit(StockInit stockInit) throws Exception {
		SysUser sysUser = getSysUser();
		stockInit.setStatus("2");
		stockInit.setAdduserid(sysUser.getUserid());
		stockInit.setAddusername(sysUser.getName());
		stockInit.setAdddeptid(sysUser.getDepartmentid());
		stockInit.setAdddeptname(sysUser.getDepartmentname());
		int i = stockInitMapper.addStockInit(stockInit);
		return i>0;
	}
	@Override
	public Map addStockInitByImport(List<StockInit> list) throws Exception {
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
		String failStr = "",closeVal = "",repeatVal = "";
		int closeNum = 0, repeatNum = 0,failureNum = 0,successNum = 0;
		boolean flag = false;
		String msg = "";
		if(null != list && list.size() != 0){
			for(StockInit stockInit : list){
				if(null!=stockInit){
                    //仓库
                    if(StringUtils.isNotEmpty(stockInit.getStoragename())){
                        StorageInfo storageInfo = getStorageInfoByName(stockInit.getStoragename());
                        if(null != storageInfo){
                            stockInit.setStorageid(storageInfo.getId());
                        }
                    }
                    //单位
                    if(StringUtils.isNotEmpty(stockInit.getUnitname())){
                        MeteringUnit unit = getBaseFilesGoodsMapper().getMeteringUnitByName(stockInit.getUnitname());
                        if(null != unit){
                            stockInit.setUnitid(unit.getId());
                        }
                    }
                    //辅单位
                    if(StringUtils.isNotEmpty(stockInit.getAuxunitname())){
                        MeteringUnit auxunit = getBaseFilesGoodsMapper().getMeteringUnitByName(stockInit.getAuxunitname());
                        if(null != auxunit){
                            stockInit.setAuxunitid(auxunit.getId());
                        }
                    }
					SysUser sysUser = getSysUser();
					stockInit.setStatus("2");
					stockInit.setAdduserid(sysUser.getUserid());
					stockInit.setAddusername(sysUser.getName());
					stockInit.setAdddeptid(sysUser.getDepartmentid());
					stockInit.setAdddeptname(sysUser.getDepartmentname());
					
					GoodsInfo goodsInfo = null;
					if(null!=stockInit.getGoodsid() && !"".equals(stockInit.getGoodsid())){
						goodsInfo = getAllGoodsInfoByID(stockInit.getGoodsid());
						if(null==goodsInfo){
							if("".equals(msg)){
								msg = "未导入商品编码:"+stockInit.getGoodsid();
							}else{
								msg += ","+stockInit.getGoodsid();
							}
						}
					}else if(null!=stockInit.getSpellcode() && !"".equals(stockInit.getSpellcode())){
						goodsInfo = getGoodsInfoBySpell(stockInit.getSpellcode());
						if(null==goodsInfo){
							if("".equals(msg)){
								msg = "未导入商品助记符:"+stockInit.getSpellcode();
							}else{
								msg += ","+stockInit.getSpellcode();
							}
						}
					}
					if(null!=goodsInfo){
						if("1".equals(goodsInfo.getIsbatch()) && StringUtils.isNotEmpty(stockInit.getProduceddate())){
							Map batchMap = getBatchno(goodsInfo.getId(), stockInit.getProduceddate());
							if(null!=batchMap){
								String batchno = (String) batchMap.get("batchno");
								String deadline = (String) batchMap.get("deadline");
								stockInit.setBatchno(batchno);
								stockInit.setDeadline(deadline);
							}
						}
						if(decimalScale == 0){
							stockInit.setUnitnum(stockInit.getUnitnum().setScale(decimalScale,BigDecimal.ROUND_DOWN));
						}else{
							stockInit.setUnitnum(stockInit.getUnitnum().setScale(decimalScale,BigDecimal.ROUND_HALF_UP));
						}
						Map auxmap = countGoodsInfoNumber(goodsInfo.getId(), stockInit.getAuxunitid(), stockInit.getUnitnum());
						stockInit.setGoodsid(goodsInfo.getId());
						stockInit.setAuxnum((BigDecimal) auxmap.get("auxnum"));
						stockInit.setAuxnumdetail((String) auxmap.get("auxnumdetail"));
						stockInit.setAuxunitid ((String) auxmap.get("auxunitid"));
						stockInit.setAuxunitname( (String) auxmap.get("auxunitname"));
						stockInit.setUnitname( (String) auxmap.get("unitname"));
						//如果是单价或者金额是空值的话 单价就取采购价导入，其他的就按excel里面填的实际导入 0金额 就导入0金额
						if(stockInit.getPrice() == null){
                            if(stockInit.getUnitamount() != null){
                                stockInit.setPrice(stockInit.getUnitamount().divide(stockInit.getUnitnum(),6,BigDecimal.ROUND_HALF_UP));
                            }else{
                                stockInit.setPrice(goodsInfo.getNewbuyprice());
                                stockInit.setUnitamount(stockInit.getUnitnum().multiply(stockInit.getPrice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
                            }
                        }else{
                            stockInit.setUnitamount(stockInit.getUnitnum().multiply(stockInit.getPrice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
                        }
                        BigDecimal notaxamount = getNotaxAmountByTaxAmount(stockInit.getUnitnum().multiply(stockInit.getPrice()),goodsInfo.getDefaulttaxtype());
                        stockInit.setNotaxamount(notaxamount);
						BigDecimal tax = stockInit.getUnitamount().subtract(stockInit.getNotaxamount());
                        stockInit.setTax(tax);
                        stockInit.setTaxtype(goodsInfo.getDefaulttaxtype());
						flag = stockInitMapper.addStockInit(stockInit) > 0;
						if(flag){
							successNum++;
						}else{
							failureNum++;
						}
					}else{
						failureNum++;
					}
					
				}
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("success", successNum);
		map.put("failure", failureNum);
		map.put("msg", msg);
		return map;
	}
	@Override
	public boolean deleteStockInit(String id) throws Exception {
		StockInit stockInit = stockInitMapper.getStockInitInfo(id);
		boolean flag = false;
		//只有新增 暂存 保存状态才能删除
		if(null!=stockInit && ("0".equals(stockInit.getStatus()) || "1".equals(stockInit.getStatus()) || "2".equals(stockInit.getStatus()))){
			int i = stockInitMapper.deleteStockInit(id);
			flag = i>0;
		}
		return flag;
	}

	@Override
	public StockInit showStockInitInfo(String id) throws Exception {
		StockInit stockInit = stockInitMapper.getStockInitInfo(id);
		if(null!=stockInit){
            GoodsInfo goodsInfo = getAllGoodsInfoByID(stockInit.getGoodsid());
            stockInit.setGoodsInfo(goodsInfo);
            TaxType taxType = getTaxType(stockInit.getTaxtype());
            if(null!=taxType){
                stockInit.setTaxtypename(taxType.getName());
            }
        }
		return stockInit;
	}

	@Override
	public boolean editStockInit(StockInit stockInit) throws Exception {
		StockInit oldStockInit = stockInitMapper.getStockInitInfo(stockInit.getId()+"");
		if(null==oldStockInit || "3".equals(oldStockInit.getStatus()) || "4".equals(oldStockInit.getStatus())){
			return false;
		}
		int i = stockInitMapper.editStockInit(stockInit);
		return i>0;
	}

	@Override
	public boolean auditStockInit(String id) throws Exception {
		StockInit stockInit = stockInitMapper.getStockInitInfo(id);
		boolean flag = false;
		//保存状态才能审核
		if(null!=stockInit && "2".equals(stockInit.getStatus())){
			StorageSummaryBatch storageSummaryBatch = new StorageSummaryBatch();
			storageSummaryBatch.setGoodsid(stockInit.getGoodsid());
			storageSummaryBatch.setStorageid(stockInit.getStorageid());
			storageSummaryBatch.setStoragelocationid(stockInit.getStoragelocationid());
			storageSummaryBatch.setBatchno(stockInit.getBatchno());
			storageSummaryBatch.setPrice(stockInit.getPrice());
			storageSummaryBatch.setAmount(stockInit.getUnitamount());
			storageSummaryBatch.setUnitid(stockInit.getUnitid());
			storageSummaryBatch.setUnitname(stockInit.getUnitname());
			storageSummaryBatch.setAuxunitid(stockInit.getAuxunitid());
			storageSummaryBatch.setAuxunitname(stockInit.getAuxunitname());
			storageSummaryBatch.setProduceddate(stockInit.getProduceddate());
			storageSummaryBatch.setDeadline(stockInit.getDeadline());
			//入库日期
			storageSummaryBatch.setEnterdate(CommonUtils.getTodayDataStr());
			
			//更新库存现存量信息
			boolean flag1= initStorageSummaryBatch(storageSummaryBatch,stockInit.getUnitnum(),"stockInit",stockInit.getId()+"","库存初始化审核通过，更新添加库存现存量");
			//更新库存初始化数据
			if(flag1){
				SysUser sysUser = getSysUser();
				int i = stockInitMapper.auditStockInit(id, sysUser.getUserid(), sysUser.getName(),storageSummaryBatch.getId());
				flag = i>0;
				String storageInitPrice = getSysParamValue("StorageInitPrice");
				if(flag && "1".equals(storageInitPrice)){
					//更新最新采购价 成本价
					updateGoodsPriceByAdd(stockInit.getStorageid(), stockInit.getGoodsid(), stockInit.getUnitnum(), stockInit.getPrice(), true, true,true,id,id);
				}
			}
		}
		return flag;
	}

	@Override
	public boolean checkStockInitBatchno(String batchno) throws Exception {
		int i = stockInitMapper.getStockInitBatchnoCount(batchno);
		return i==0;
	}

	@Override
	public boolean oppauditStockInit(String id) throws Exception {
		StockInit stockInit = stockInitMapper.getStockInitInfo(id);
		boolean flag = false;
		//审核通过状态才能反审
		if(null!=stockInit && "3".equals(stockInit.getStatus())){
			GoodsInfo goodsInfo = getAllGoodsInfoByID(stockInit.getGoodsid());
			StorageSummaryBatch storageSummaryBatch = null;
			if(StringUtils.isNotEmpty(stockInit.getSummarybatchid())){
				storageSummaryBatch = getStorageSummaryBatchById(stockInit.getSummarybatchid());
			}else{
				if(null!=goodsInfo && "1".equals(goodsInfo.getIsbatch())){
					storageSummaryBatch = getStorageSummaryMapper().getStorageSummaryBatchInfoByBatchno(stockInit.getStorageid(),stockInit.getBatchno(),stockInit.getGoodsid());
				}else{
					storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(stockInit.getStorageid(), stockInit.getGoodsid());
				}
			}
            if(null!=storageSummaryBatch){
                //回滚库存初始化入库
                rollbackStorageSummaryNum(storageSummaryBatch,stockInit.getUnitnum(), "stockInit", stockInit.getId()+"", "库存初始化单据反审后，更新库存现存量信息");
                SysUser sysUser = getSysUser();
                int i = stockInitMapper.oppauditStockInit(id, sysUser.getUserid(), sysUser.getName());
                flag = i>0;
                String storageInitPrice = getSysParamValue("StorageInitPrice");
                if(flag && "1".equals(storageInitPrice)){
                    //更新成本价
                    updateGoodsPriceBySubtract(stockInit.getId()+"",stockInit.getId()+"",stockInit.getStorageid(), stockInit.getGoodsid(), stockInit.getUnitnum(), stockInit.getPrice(), true);
                }
            }
        }
		return flag;
	}
}

