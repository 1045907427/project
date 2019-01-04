/**
 * @(#)CheckListServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 18, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.report.dao.StorageCheckListReportMapper;
import com.hd.agent.report.model.StorageCheckListReport;
import com.hd.agent.storage.dao.AdjustmentsMapper;
import com.hd.agent.storage.dao.CheckListMapper;
import com.hd.agent.storage.model.CheckList;
import com.hd.agent.storage.model.CheckListDetail;
import com.hd.agent.storage.model.StorageSummary;
import com.hd.agent.storage.model.StorageSummaryBatch;
import com.hd.agent.storage.service.ICheckListService;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 盘点单service实现类
 * @author chenwei
 */
public class CheckListServiceImpl extends BaseStorageServiceImpl implements
		ICheckListService {
	/**
	 * 盘点单dao
	 */
	private CheckListMapper checkListMapper;
	/**
	 * 调账单dao
	 */
	private AdjustmentsMapper adjustmentsMapper;
	/**
	 * 盘点单报表dao
	 */
	private StorageCheckListReportMapper storageCheckListReportMapper;
	
	public CheckListMapper getCheckListMapper() {
		return checkListMapper;
	}

	public void setCheckListMapper(CheckListMapper checkListMapper) {
		this.checkListMapper = checkListMapper;
	}
	public AdjustmentsMapper getAdjustmentsMapper() {
		return adjustmentsMapper;
	}

	public void setAdjustmentsMapper(AdjustmentsMapper adjustmentsMapper) {
		this.adjustmentsMapper = adjustmentsMapper;
	}
	
	public StorageCheckListReportMapper getStorageCheckListReportMapper() {
		return storageCheckListReportMapper;
	}

	public void setStorageCheckListReportMapper(
			StorageCheckListReportMapper storageCheckListReportMapper) {
		this.storageCheckListReportMapper = storageCheckListReportMapper;
	}

	@Override
	public List getCheckListDetail(String storageid,String brands,String goodssorts) throws Exception {
		List<StorageSummaryBatch> summaryBatchlist = null;
		//是否按批次盘点
		String IsCheckListUseBatch = getSysParamValue("IsCheckListUseBatch");

		Map map = new HashMap();
		map.put("storageid",storageid);
		if(StringUtils.isNotEmpty(brands)){
			String[] brandArr = brands.split(",");
			if(brandArr.length != 0){
				map.put("brands",brandArr);
			}
		}
		if(StringUtils.isNotEmpty(goodssorts)){
			String[] goodssortsArr = goodssorts.split(",");
			if(goodssortsArr.length != 0){
				map.put("goodssorts",goodssortsArr);
			}
		}
		if("1".equals(IsCheckListUseBatch)){
			summaryBatchlist = getStorageSummaryMapper().getStorageSummaryBatchListByMap(map);
		}else{
			summaryBatchlist = getStorageSummaryMapper().getStorageSummaryBatchSumListByMap(map);
		}
		
		List list = new ArrayList();
		for(StorageSummaryBatch storageSummaryBatch : summaryBatchlist){
			CheckListDetail checkListDetail = new CheckListDetail();
			checkListDetail.setSummarybatchid(storageSummaryBatch.getId()+"");
			checkListDetail.setGoodsid(storageSummaryBatch.getGoodsid());
			//商品详情
			GoodsInfo goodsInfo = getAllGoodsInfoByID(storageSummaryBatch.getGoodsid());
			if(null!=goodsInfo){
				checkListDetail.setGoodsname(goodsInfo.getName());
				checkListDetail.setBrandid(goodsInfo.getBrand());
				checkListDetail.setBrandName(goodsInfo.getBrandName());
				checkListDetail.setModel(goodsInfo.getModel());
				checkListDetail.setBarcode(goodsInfo.getBarcode());
				checkListDetail.setUnitid(goodsInfo.getMainunit());
				checkListDetail.setUnitname(goodsInfo.getMainunitName());
				checkListDetail.setBoxnum(goodsInfo.getBoxnum());
				//商品分类名称
				WaresClass waresClass = getBaseFilesGoodsMapper().getWaresClassInfo(goodsInfo.getDefaultsort());
				if(null!=waresClass){
					checkListDetail.setGoodssortname(waresClass.getName());
				}
			}
			checkListDetail.setStorageid(storageSummaryBatch.getStorageid());
			checkListDetail.setStoragelocationid(storageSummaryBatch.getStoragelocationid());
			StorageLocation storageLocation = getStorageLocationByID(storageSummaryBatch.getStoragelocationid());
			if(null!=storageLocation){
				checkListDetail.setStoragelocationname(storageLocation.getName());
			}
			Personnel personnel = getPersonnelById(checkListDetail.getCheckuserid());
			if(null!=personnel){
				checkListDetail.setCheckusername(personnel.getName());
			}
			
			checkListDetail.setBooknum(storageSummaryBatch.getExistingnum());
			checkListDetail.setRealnum(new BigDecimal(0));
			checkListDetail.setProfitlossnum(storageSummaryBatch.getExistingnum().negate());
			checkListDetail.setAuxunitid(storageSummaryBatch.getAuxunitid());
			checkListDetail.setAuxunitname(storageSummaryBatch.getAuxunitname());
			checkListDetail.setPrice(goodsInfo.getNewbuyprice());
			//盘点单金额 = 批次单价*现存量
			checkListDetail.setAmount(goodsInfo.getNewbuyprice().multiply(storageSummaryBatch.getExistingnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
			checkListDetail.setBatchno(storageSummaryBatch.getBatchno());
			checkListDetail.setProduceddate(storageSummaryBatch.getProduceddate());
			checkListDetail.setDeadline(storageSummaryBatch.getDeadline());
			
			Map auxbooknumMap = countGoodsInfoNumber(storageSummaryBatch.getGoodsid(), storageSummaryBatch.getAuxunitid(), storageSummaryBatch.getExistingnum());
			BigDecimal bigDecimal = (BigDecimal) auxbooknumMap.get("auxnum");
			checkListDetail.setAuxbooknum(bigDecimal);
			checkListDetail.setAuxbooknumdetail((String)auxbooknumMap.get("auxnumdetail"));
			checkListDetail.setAuxprofitlossnumdetail("-"+(String)auxbooknumMap.get("auxnumdetail"));
			list.add(checkListDetail);
		}
		return list;
	}
	@Override
	public List getCheckListDetailByGoodssorts(String storageid,String goodssorts) throws Exception {
		List<StorageSummaryBatch> summaryBatchlist = null;
		//是否按批次盘点
		String IsCheckListUseBatch = getSysParamValue("IsCheckListUseBatch"); 
		if("1".equals(IsCheckListUseBatch)){
			if(null!=goodssorts){
				String[] goodssortsArr = goodssorts.split(",");
				summaryBatchlist = getStorageSummaryMapper().getStorageSummaryBatchListByStorageidAndGoodssorts(storageid, goodssortsArr);
			}else{
				summaryBatchlist = getStorageSummaryMapper().getStorageSummaryBatchListByStorageid(storageid);
			}
		}else{
			if(null!=goodssorts){
				String[] goodssortsArr = goodssorts.split(",");
				summaryBatchlist = getStorageSummaryMapper().getStorageSummaryBatchSumListByStorageidAndGoodssorts(storageid, goodssortsArr);
			}else{
				summaryBatchlist = getStorageSummaryMapper().getStorageSummaryBatchSumListByStorageid(storageid);
			}
		}
		
		List list = new ArrayList();
		for(StorageSummaryBatch storageSummaryBatch : summaryBatchlist){
			CheckListDetail checkListDetail = new CheckListDetail();
			checkListDetail.setSummarybatchid(storageSummaryBatch.getId()+"");
			checkListDetail.setGoodsid(storageSummaryBatch.getGoodsid());
			//商品详情
			GoodsInfo goodsInfo = getAllGoodsInfoByID(storageSummaryBatch.getGoodsid());
			if(null!=goodsInfo){
				checkListDetail.setGoodsname(goodsInfo.getName());
				checkListDetail.setBrandid(goodsInfo.getBrand());
				checkListDetail.setBrandName(goodsInfo.getBrandName());
				checkListDetail.setModel(goodsInfo.getModel());
				checkListDetail.setBarcode(goodsInfo.getBarcode());
				checkListDetail.setUnitid(goodsInfo.getMainunit());
				checkListDetail.setUnitname(goodsInfo.getMainunitName());
				checkListDetail.setBoxnum(goodsInfo.getBoxnum());
				//商品分类名称
				WaresClass waresClass = getBaseFilesGoodsMapper().getWaresClassInfo(goodsInfo.getDefaultsort());
				if(null!=waresClass){
					checkListDetail.setGoodssortname(waresClass.getName());
				}
			}
			checkListDetail.setStorageid(storageSummaryBatch.getStorageid());
			checkListDetail.setStoragelocationid(storageSummaryBatch.getStoragelocationid());
			StorageLocation storageLocation = getStorageLocationByID(storageSummaryBatch.getStoragelocationid());
			if(null!=storageLocation){
				checkListDetail.setStoragelocationname(storageLocation.getName());
			}
			Personnel personnel = getPersonnelById(checkListDetail.getCheckuserid());
			if(null!=personnel){
				checkListDetail.setCheckusername(personnel.getName());
			}
			
			checkListDetail.setBooknum(storageSummaryBatch.getExistingnum());
			checkListDetail.setRealnum(new BigDecimal(0));
			checkListDetail.setProfitlossnum(storageSummaryBatch.getExistingnum().negate());
			checkListDetail.setAuxunitid(storageSummaryBatch.getAuxunitid());
			checkListDetail.setAuxunitname(storageSummaryBatch.getAuxunitname());
			checkListDetail.setPrice(goodsInfo.getNewbuyprice());
			//盘点单金额 = 批次单价*现存量
			checkListDetail.setAmount(goodsInfo.getNewbuyprice().multiply(storageSummaryBatch.getExistingnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
			checkListDetail.setBatchno(storageSummaryBatch.getBatchno());
			checkListDetail.setProduceddate(storageSummaryBatch.getProduceddate());
			checkListDetail.setDeadline(storageSummaryBatch.getDeadline());
			
			Map auxbooknumMap = countGoodsInfoNumber(storageSummaryBatch.getGoodsid(), storageSummaryBatch.getAuxunitid(), storageSummaryBatch.getExistingnum());
			BigDecimal bigDecimal = (BigDecimal) auxbooknumMap.get("auxnum");
			checkListDetail.setAuxbooknum(bigDecimal);
			checkListDetail.setAuxbooknumdetail((String)auxbooknumMap.get("auxnumdetail"));
			checkListDetail.setAuxprofitlossnumdetail("-"+(String)auxbooknumMap.get("auxnumdetail"));
			list.add(checkListDetail);
		}
		return list;
	}

	@Override
	public boolean addCheckList(CheckList checkList, List<CheckListDetail> detailList)
			throws Exception {
		SysUser sysUser = getSysUser();
		checkList.setAdduserid(sysUser.getUserid());
		checkList.setAddusername(sysUser.getName());
		checkList.setAdddeptid(sysUser.getDepartmentid());
		checkList.setAdddeptname(sysUser.getDepartmentname());

        int j = 0;
        for(CheckListDetail checkListDetail : detailList){
            checkListDetail.setId(null);
            GoodsInfo goodsInfo = getAllGoodsInfoByID(checkListDetail.getGoodsid());
            if(null!=goodsInfo){
                checkListDetail.setBrandid(goodsInfo.getBrand());
            }
            checkListDetail.setCheckuserid(checkList.getCheckuserid());
            checkListDetail.setChecklistid(checkList.getId());
            checkListDetail.setStorageid(checkList.getStorageid());
            checkListDetail.setSeq(j);
            checkListDetail.setProfitlossnum(checkListDetail.getRealnum().subtract(checkListDetail.getBooknum()));
            Map auxMap = countGoodsInfoNumber(checkListDetail.getGoodsid(), checkListDetail.getAuxunitid(), checkListDetail.getProfitlossnum());
            if(null!=auxMap){
                String auxprofitlossnumdetail = (String) auxMap.get("auxnumdetail");
                BigDecimal auxprofitlossnum  = (BigDecimal) auxMap.get("auxnum");
                checkListDetail.setAuxprofitlossnum(auxprofitlossnum);
                checkListDetail.setAuxprofitlossnumdetail(auxprofitlossnumdetail);
            }
            j++;
            if(checkListDetail.getBooknum().compareTo(checkListDetail.getRealnum())==0){
                checkListDetail.setIstrue("1");
            }else{
                checkListDetail.setIstrue("0");
            }
        }
        int z = checkListMapper.addCheckListDetailBatch(detailList);
        checkList.setChecknum(z);
        checkList.setIsfinish("0");
        checkList.setStatus("2");
		checkList.setCheckno(1);
		checkList.setSourceid(checkList.getId());
		int i = checkListMapper.addCheckList(checkList);
		return i>0;
	}

	@Override
	public Map getCheckListInfo(String id) throws Exception {
		CheckList checkList = checkListMapper.getCheckListInfo(id);
		if(null!=checkList && "3".equals(checkList.getStatus())){
			CheckList newCheckList = checkListMapper.getCheckListInfoByChecklistidAndNo(checkList.getSourceid(), checkList.getCheckno()+1);
			if(null!=newCheckList){
				checkList.setIsaddnew("1");
			}
		}
		if(null != checkList){
			//所属仓库
			StorageInfo storageInfo = getStorageInfoByID(checkList.getStorageid());
			if(null != storageInfo){
				checkList.setStoragename(storageInfo.getName());
			}
			//盘点人
			Personnel personnel = getPersonnelById(checkList.getCheckuserid());
			if(null != personnel){
				checkList.setCheckusername(personnel.getName());
			}
			//根据盘点单明细统计品牌
			String brandname = checkListMapper.getCheckListInfoBrandList(checkList.getId());
			checkList.setBrandname(brandname);
		}
		List<CheckListDetail> detailList = checkListMapper.getCheckListDetailListByCheckListid(id);
		for(CheckListDetail checkListDetail : detailList){
			//商品详情
			GoodsInfo goodsInfo = getGoodsInfoByID(checkListDetail.getGoodsid());
			if(null!=goodsInfo){
				checkListDetail.setGoodsname(goodsInfo.getName());
				checkListDetail.setBrandName(goodsInfo.getBrandName());
				checkListDetail.setModel(goodsInfo.getModel());
				checkListDetail.setItemno(getItemnoByGoodsAndStorage(checkListDetail.getGoodsid(),checkListDetail.getStorageid()));
				checkListDetail.setBarcode(goodsInfo.getBarcode());
				checkListDetail.setBoxnum(goodsInfo.getBoxnum());
				
				//商品分类名称
				WaresClass waresClass = getBaseFilesGoodsMapper().getWaresClassInfo(goodsInfo.getDefaultsort());
				if(null!=waresClass){
					checkListDetail.setGoodssortname(waresClass.getName());
				}
			}
			checkListDetail.setGoodsInfo(goodsInfo);
			StorageLocation storageLocation = getStorageLocationByID(checkListDetail.getStoragelocationid());
			if(null!=storageLocation){
				checkListDetail.setStoragelocationname(storageLocation.getName());
			}
			Personnel personnel = getPersonnelById(checkListDetail.getCheckuserid());
			if(null!=personnel){
				checkListDetail.setCheckusername(personnel.getName());
			}
			StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(checkList.getStorageid(), checkListDetail.getGoodsid());
			if(null!=storageSummary){
				checkListDetail.setExistingnum(storageSummary.getExistingnum());
			}
		}
		Map map = new HashMap();
		map.put("checkList", checkList);
		map.put("checkListDetail", detailList);
		return map;
	}

	@Override
	public PageData showCheckListPageData(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_storage_checklist", "t");
		pageMap.setDataSql(dataSql);
		List<CheckList> list = checkListMapper.showCheckListData(pageMap);
		for(CheckList checkList : list){
			StorageInfo storageInfo = getStorageInfoByID(checkList.getStorageid());
			if(null!=storageInfo){
				checkList.setStoragename(storageInfo.getName());
			}
			String brandname = checkListMapper.getCheckListInfoBrandList(checkList.getId());
			checkList.setBrandname(brandname);
			
			Personnel personnel = getPersonnelById(checkList.getCheckuserid());
			if(null!=personnel){
				checkList.setCheckusername(personnel.getName());
			}
		}
		PageData pageData = new PageData(checkListMapper.showCheckListCount(pageMap),list,pageMap);
		return pageData;
	}

	@Override
	public boolean editCheckList(CheckList checkList, List<CheckListDetail> list) throws Exception{
		CheckList oldCheckList1 = checkListMapper.getCheckListInfo(checkList.getId());
		if(null==oldCheckList1 || "3".equals(oldCheckList1.getStatus()) || "4".equals(oldCheckList1.getStatus())){
			return false;
		}
		
		if(null!=oldCheckList1 &&"1".equals(oldCheckList1.getIsfinish()) && !isSysUserHaveUrl("/storage/getCheckListNumIsTure.do")){
			return false;
		}
		SysUser sysUser = getSysUser();
		checkList.setModifyuserid(sysUser.getUserid());
		checkList.setModifyusername(sysUser.getName());

		//更新盘点单明细
		//先删除盘点单明细 再添加
		checkListMapper.deleteCheckListDetailByCheckListid(checkList.getId());
		int j = 0;
		int falsenum = 0;
		for(CheckListDetail checkListDetail : list){
			GoodsInfo goodsInfo = getAllGoodsInfoByID(checkListDetail.getGoodsid());
			if(null!=goodsInfo){
				checkListDetail.setBrandid(goodsInfo.getBrand());
			}
			checkListDetail.setCheckuserid(checkList.getCheckuserid());
			checkListDetail.setChecklistid(checkList.getId());
			checkListDetail.setStorageid(checkList.getStorageid());
			checkListDetail.setSeq(j);
			checkListDetail.setProfitlossnum(checkListDetail.getRealnum().subtract(checkListDetail.getBooknum()));
			Map auxMap = countGoodsInfoNumber(checkListDetail.getGoodsid(), checkListDetail.getAuxunitid(), checkListDetail.getProfitlossnum());
			if(null!=auxMap){
				String auxprofitlossnumdetail = (String) auxMap.get("auxnumdetail");
				BigDecimal auxprofitlossnum  = (BigDecimal) auxMap.get("auxnum");
				checkListDetail.setAuxprofitlossnum(auxprofitlossnum);
				checkListDetail.setAuxprofitlossnumdetail(auxprofitlossnumdetail);
			}
			j++;
			if(checkListDetail.getBooknum().compareTo(checkListDetail.getRealnum())==0){
				checkListDetail.setIstrue("1");
			}else{
				falsenum ++;
				checkListDetail.setIstrue("0");
			}
		}
		int z = 0;
		if(null != list && list.size() != 0){
			z = checkListMapper.addCheckListDetailBatch(list);
		}
		if(1==checkList.getCheckno()){
			checkList.setChecknum(z);
		}else{
			CheckList oldCheckList = checkListMapper.getCheckListInfo(checkList.getSourceid());
			checkList.setChecknum(oldCheckList.getChecknum());
		}
		checkList.setIsfinish("0");
		checkList.setStatus("2");
		int i = checkListMapper.editCheckList(checkList);
		return i>0;
	}

	@Override
	public boolean editJustCheckList(CheckList checkList) throws Exception {
		boolean flag = false;
		if(null != checkList){
			CheckList oldCheckList1 = checkListMapper.getCheckListInfo(checkList.getId());
			if(null==oldCheckList1 || "3".equals(oldCheckList1.getStatus()) || "4".equals(oldCheckList1.getStatus())){
				return false;
			}

			if(null!=oldCheckList1 &&"1".equals(oldCheckList1.getIsfinish()) && !isSysUserHaveUrl("/storage/getCheckListNumIsTure.do")){
				return false;
			}
			SysUser sysUser = getSysUser();
			checkList.setModifyuserid(sysUser.getUserid());
			checkList.setModifyusername(sysUser.getName());

			int z = checkListMapper.getCheckListDetailListByCheckListid(checkList.getId()).size();
			if(1==checkList.getCheckno()){
				checkList.setChecknum(z);
			}else{
				CheckList oldCheckList = checkListMapper.getCheckListInfo(checkList.getSourceid());
				checkList.setChecknum(oldCheckList.getChecknum());
			}
			checkList.setIsfinish("0");
			checkList.setStatus("2");
			flag = checkListMapper.editCheckList(checkList) > 0;

            //更新明细的状态
            checkListMapper.updateCheckListDetailTrue(checkList.getId());
            checkListMapper.updateCheckListDetailFalse(checkList.getId());
		}
		return flag;
	}

	@Override
	public boolean deleteCheckList(String id) throws Exception{
		int i = checkListMapper.deleteCheckList(id);
		checkListMapper.deleteCheckListDetailByCheckListid(id);
		return i>0;
	}

	@Override
	public Map isHaveCheckListByStorageid(String storageid) throws Exception {
		int i = checkListMapper.getCheckListCountByStorageid(storageid);
		String oldid = null;
		if(i>0){
			oldid = checkListMapper.getCheckListIdByStorageid(storageid);
		}
		Map map = new HashMap();
		map.put("flag", i>0);
		map.put("oldid", oldid);
		return map;
	}

	@Override
	public Map auditCheckList(String id) throws Exception {
		boolean flag = false;
		CheckList checkList = checkListMapper.getCheckListInfo(id);
		if(null!=checkList && ("2".equals(checkList.getStatus()) || "6".equals(checkList.getStatus())) ){
            //盘点未完成时，审核自动完成盘点
            if("0".equals(checkList.getIsfinish())){
                updateCheckListFinish(id);
            }
			SysUser sysUser = getSysUser();
			//审核并且关闭盘点单
			int j = checkListMapper.auditAndCloseCheckList(id, sysUser.getUserid(), sysUser.getName());
			flag = j>0;
			//生成盘点单报表数据
			if(j>0){
				addCheckListReportByID(id);
			}
			//关闭来源单据编号一致的盘点单
			if(!"".equals(checkList.getSourceid())){
				checkListMapper.closeCheckListBySourceid(checkList.getSourceid());
			}
		}
		//返回信息
		Map map = new HashMap();
		map.put("flag", flag);
		if(null!=checkList && "0".equals(checkList.getIsfinish())){
			map.put("msg", "盘点未完成");
		}
		return map;
	}

	@Override
	public Map oppauditCheckList(String id) throws Exception {
		boolean flag = false;
		SysUser sysUser = getSysUser();
        boolean isOppAudit = true;
        CheckList checkList = checkListMapper.getCheckListInfo(id);
        if(null!=checkList && "3".equals(checkList.getStatus())){
            CheckList newCheckList = checkListMapper.getCheckListInfoByChecklistidAndNo(checkList.getSourceid(), checkList.getCheckno()+1);
            if(null!=newCheckList){
                isOppAudit = false;
            }
        }
        if(isOppAudit){
            int i = checkListMapper.oppauditCheckList(id, sysUser.getUserid(), sysUser.getName());
            flag = i>0;
            //删除盘点单报表数据
            if(flag){
                deleteCheckListReportByID(id);
            }
        }
		Map map = new HashMap();
		map.put("flag", flag);
		return map;
	}

	@Override
	public List showCheckListDetailData(String id) throws Exception {
		List<CheckListDetail> list = checkListMapper.getCheckListDetailListByCheckListid(id);
		for(CheckListDetail checkListDetail : list){
			GoodsInfo goodsInfo = getGoodsInfoByID(checkListDetail.getGoodsid());
			checkListDetail.setGoodsInfo(goodsInfo);
			StorageLocation storageLocation = getStorageLocation(checkListDetail.getStoragelocationid());
			if(null!=storageLocation){
				checkListDetail.setStoragelocationname(storageLocation.getName());
			}
		}
		return list;
	}

	@Override
	public boolean submitCheckListPageProcess(String id) throws Exception {
		return false;
	}

	@Override
	public Map getCheckListNumIsTure(String id) throws Exception {
		CheckList checkList = checkListMapper.getCheckListInfo(id);
		boolean flag = false;
		boolean checkFlag = false;
		int num = 0;
		if("2".equals(checkList.getStatus())){
			num = checkListMapper.getCheckListNumNotTureCount(id);
			if(num==0){
				flag = true;
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("num", num);
		return map;
	}

	@Override
	public boolean updateCheckListFinish(String id) throws Exception {
		CheckList checkList = checkListMapper.getCheckListInfo(id);
		boolean flag = false;
		if("2".equals(checkList.getStatus())){
			int j = 0;
			int falsenum = 0;
			List<CheckListDetail> list = checkListMapper.getCheckListDetailListByCheckListid(id);
			for(CheckListDetail checkListDetail : list){
				j++;
				if(checkListDetail.getBooknum().compareTo(checkListDetail.getRealnum())==0){
					checkListDetail.setIstrue("1");
				}else{
					falsenum ++;
					checkListDetail.setIstrue("0");
				}
			}
			if(1==checkList.getCheckno()){
				checkList.setChecknum(checkList.getChecknum());
				checkList.setTruenum(j-falsenum);
			}else{
				CheckList oldCheckList = checkListMapper.getCheckListInfo(checkList.getSourceid());
				if(null!=oldCheckList){
					checkList.setChecknum(oldCheckList.getChecknum());
					checkList.setTruenum(oldCheckList.getChecknum()-falsenum);
				}
			}
			if(checkList.getChecknum()==checkList.getTruenum()){
				checkList.setIstrue("1");
			}else{
				checkList.setIstrue("0");
			}
			checkListMapper.editCheckList(checkList);
			int i = checkListMapper.updateCheckListFinish(id);
            //更新明细的状态
            checkListMapper.updateCheckListDetailTrue(id);
            checkListMapper.updateCheckListDetailFalse(id);
			flag = i >0;
		}
		return flag;
	}
	@Override
	public Map closeCheckListByCheck(String id) throws Exception {
		CheckList checkList = checkListMapper.getCheckListInfo(id);

		SysUser sysUser = getSysUser();
		boolean flag = false;
		String newid = "";
		if("2".equals(checkList.getStatus()) ){
            if("0".equals(checkList.getIsfinish())){
                updateCheckListFinish(id);
            }
            //盘点不正确的数量
            int num = checkListMapper.getCheckListNumNotTureCount(id);
            if(num>0){
                //审核盘点单
                int j = checkListMapper.auditCheckList(id, sysUser.getUserid(), sysUser.getName());
                //生成盘点单报表数据
                if(j>0){
                    addCheckListReportByID(id);
                    flag = true;
                }
            }else{
                Map returnMap = auditCheckList(id);
                if(null!=returnMap){
                    flag = (Boolean)returnMap.get("flag");
                }
            }
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("newid", newid);
		return map;
	}
	@Override
	public Map addCheckListByCheck(String id) throws Exception {
		CheckList checkList = checkListMapper.getCheckListInfo(id);
		//盘点不正确的数量
		int num = checkListMapper.getCheckListNumNotTureCount(id);
		SysUser sysUser = getSysUser();
		boolean flag = true;
		String newid = "";
		if("3".equals(checkList.getStatus()) && "0".equals(checkList.getIstrue()) && num>0){
			//生成新的盘点单
			CheckList newCheckList = new CheckList();
			if (isAutoCreate("t_storage_checklist")) {
				// 获取自动编号
				newid = getAutoCreateSysNumbderForeign(checkList, "t_storage_checklist");
				newCheckList.setId(newid);
			}else{
				newid = "PDD-"+CommonUtils.getDataNumberSendsWithRand();
				newCheckList.setId(newid);
			}
			newCheckList.setBusinessdate(checkList.getBusinessdate());
			newCheckList.setSourceid(checkList.getSourceid());
			newCheckList.setStorageid(checkList.getStorageid());
			newCheckList.setCheckuserid(checkList.getCheckuserid());
			newCheckList.setCreatetype(checkList.getCreatetype());
			newCheckList.setCheckno(checkList.getCheckno()+1);
			newCheckList.setIsfinish("0");
			newCheckList.setChecknum(checkList.getChecknum());
			newCheckList.setStatus("2");
			newCheckList.setAdduserid(sysUser.getUserid());
			newCheckList.setAddusername(sysUser.getName());
			newCheckList.setAdddeptid(sysUser.getDepartmentid());
			newCheckList.setAdddeptname(sysUser.getDepartmentname());
			int i = checkListMapper.addCheckList(newCheckList);
			flag = i>0;
			List<CheckListDetail> detailList = checkListMapper.getCheckListNumNotTureList(id);
			int y = 0;
			for(CheckListDetail checkListDetail : detailList){
				checkListDetail.setId(null);
				checkListDetail.setChecklistid(newCheckList.getId());
				checkListDetail.setCheckuserid(newCheckList.getCheckuserid());
				//获取仓库商品现存量
				StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(checkListDetail.getStorageid(), checkListDetail.getGoodsid());
				if(null!=storageSummary){
					checkListDetail.setBooknum(storageSummary.getExistingnum());
					Map auxMap = countGoodsInfoNumber(checkListDetail.getGoodsid(), checkListDetail.getAuxunitid(), storageSummary.getExistingnum());
					String auxbooknumdetail = (String) auxMap.get("auxnumdetail");
					checkListDetail.setAuxbooknumdetail(auxbooknumdetail);
				}else{
					checkListDetail.setBooknum(BigDecimal.ZERO);
					checkListDetail.setAuxbooknumdetail("");
				}
				GoodsInfo goodsInfo = getAllGoodsInfoByID(checkListDetail.getGoodsid());
				if(null!=goodsInfo){
					checkListDetail.setBrandid(goodsInfo.getBrand());
				}
				checkListDetail.setStorageid(checkList.getStorageid());
				checkListDetail.setRealnum(BigDecimal.ZERO);
				checkListDetail.setProfitlossnum(checkListDetail.getBooknum());
				checkListDetail.setAuxprofitlossnum(checkListDetail.getAuxbooknum());
				checkListDetail.setAuxprofitlossnumdetail(checkListDetail.getAuxbooknumdetail());
				checkListDetail.setAuxrealnum(BigDecimal.ZERO);
				checkListDetail.setAuxrealremainder(BigDecimal.ZERO);
				checkListDetail.setAuxrealnumdetail("");
				checkListDetail.setSeq(y);
				y++;
			}
			checkListMapper.addCheckListDetailBatch(detailList);
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("newid", newid);
		return map;
	}
	/**
	 * 根据盘点单编号 生成盘点报表
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 9, 2013
	 */
	public boolean addCheckListReportByID(String id) throws Exception{
		CheckList checkList = checkListMapper.getCheckListInfo(id);
		if(null!=checkList){
			int checkno = checkList.getCheckno();
			String userid = checkList.getCheckuserid();
			if(checkno==1){
				while(checkno<=3){
					if(checkList.getId().equals(checkList.getSourceid())){
						StorageCheckListReport storageCheckListReport = new StorageCheckListReport();
						storageCheckListReport.setBusinessdate(checkList.getBusinessdate());
						storageCheckListReport.setBillid(checkList.getId());
						storageCheckListReport.setChecklistid(checkList.getId());
						storageCheckListReport.setStorageid(checkList.getStorageid());
						storageCheckListReport.setCheckuserid(userid);
						storageCheckListReport.setCheckno(checkno);
						storageCheckListReport.setChecknum(checkList.getChecknum());
						storageCheckListReport.setTruenum(checkList.getTruenum());
						storageCheckListReportMapper.addStorageCheckListReport(storageCheckListReport);
					}else{
						StorageCheckListReport storageCheckListReport = new StorageCheckListReport();
						storageCheckListReport.setBusinessdate(checkList.getBusinessdate());
						storageCheckListReport.setBillid(checkList.getSourceid());
						storageCheckListReport.setChecklistid(checkList.getId());
						storageCheckListReport.setStorageid(checkList.getStorageid());
						storageCheckListReport.setCheckuserid(userid);
						storageCheckListReport.setCheckno(checkno);
						storageCheckListReport.setChecknum(checkList.getChecknum());
						storageCheckListReport.setTruenum(checkList.getTruenum());
						storageCheckListReportMapper.addStorageCheckListReport(storageCheckListReport);
					}
					checkno++;
				}
			}else{
				while(checkno<=3){
					StorageCheckListReport storageCheckListReport = storageCheckListReportMapper.getStorageCheckListReportByChecklistid(checkList.getSourceid(), checkno);
					if(null!=storageCheckListReport){
						storageCheckListReport.setBusinessdate(checkList.getBusinessdate());
						storageCheckListReport.setBillid(checkList.getSourceid());
						storageCheckListReport.setChecklistid(checkList.getId());
						storageCheckListReport.setStorageid(checkList.getStorageid());
						storageCheckListReport.setCheckuserid(userid);
						storageCheckListReport.setCheckno(checkno);
						storageCheckListReport.setChecknum(checkList.getChecknum());
						storageCheckListReport.setTruenum(checkList.getTruenum());
						storageCheckListReportMapper.updateStorageCheckListReport(storageCheckListReport);
					}else{
						storageCheckListReport = new StorageCheckListReport();
						storageCheckListReport.setBusinessdate(checkList.getBusinessdate());
						storageCheckListReport.setBillid(checkList.getSourceid());
						storageCheckListReport.setChecklistid(checkList.getId());
						storageCheckListReport.setStorageid(checkList.getStorageid());
						storageCheckListReport.setCheckuserid(userid);
						storageCheckListReport.setCheckno(checkno);
						storageCheckListReport.setChecknum(checkList.getChecknum());
						storageCheckListReport.setTruenum(checkList.getTruenum());
						storageCheckListReportMapper.addStorageCheckListReport(storageCheckListReport);
					}
					checkno++;
				}
				
			}
			
		}
		return true;
	}
	/**
	 * 根据盘点单编号删除盘点单报表数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 9, 2013
	 */
	public boolean deleteCheckListReportByID(String id)throws Exception{
		int i= storageCheckListReportMapper.deleteStorageCheckListReportByChecklistid(id);
		return i>0;
	}

	@Override
	public List exportCheckListData(PageMap pageMap) throws Exception {
		String dataSql = getAccessColumnList("t_storage_checklist", "t");
		pageMap.setDataSql(dataSql);
		pageMap.setQueryAlias("t");
		List<Map> list = checkListMapper.exportCheckListData(pageMap);
		for(Map map : list){
			BigDecimal price = (BigDecimal) map.get("price");
			if(null==price){
				price = BigDecimal.ZERO;
			}
			map.put("detailid", map.get("detailid").toString());
			String goodsid = (String) map.get("goodsid");
			GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
			if(null!=goodsInfo){
				map.put("name", goodsInfo.getName());
				map.put("brandname", goodsInfo.getBrandName());
				map.put("model", goodsInfo.getModel());
				map.put("barcode", goodsInfo.getBarcode());
				GoodsInfo_MteringUnitInfo mUnitInfo = getBaseGoodsMapper().getMUInfoByGoodsIdAndIsdefault(goodsid);
				if(null != mUnitInfo){
					map.put("boxnum", mUnitInfo.getRate());
				}
			}
			BigDecimal booknum = (BigDecimal) map.get("booknum");
			Map booknumMap = countGoodsInfoNumber(goodsid, (String)map.get("auxunitid"), booknum);
			if(null!=booknumMap && booknumMap.containsKey("auxInteger")){
				String auxIntegerStr = (String)booknumMap.get("auxInteger");
				if("".equals(auxIntegerStr)){
					auxIntegerStr = "0";
				}
				map.put("auxbooknum",new Integer(auxIntegerStr));
			}
			if(null!=booknumMap && booknumMap.containsKey("auxremainder")){
				String auxremainderStr = (String)booknumMap.get("auxremainder");
				if("".equals(auxremainderStr)){
					auxremainderStr = "0";
				}
				map.put("auxbooknumremainder",new BigDecimal(auxremainderStr));
			}
			BigDecimal realnum = (BigDecimal) map.get("realnum");
			Map realnumMap = countGoodsInfoNumber(goodsid, (String)map.get("auxunitid"), realnum);
			map.put("auxrealnum", new Integer((String)realnumMap.get("auxInteger")));
			map.put("auxrealnumremainder", new BigDecimal((String)realnumMap.get("auxremainder")));
			
			BigDecimal profitlossnum = (BigDecimal) map.get("profitlossnum");
			Map profitlossnumMap = countGoodsInfoNumber(goodsid, (String)map.get("auxunitid"), profitlossnum);
			map.put("auxprofitlossnum", new Integer((String)profitlossnumMap.get("auxInteger")));
			map.put("auxprofitlossnumremainder", new BigDecimal((String)profitlossnumMap.get("auxremainder")));
			
			String checkuserid = (String) map.get("checkuserid");
			if(null!=checkuserid){
				Personnel personnel = getPersonnelById(checkuserid);
				if(null!=personnel){
					map.put("checkusername", personnel.getName());
				}
			}
			
			String storageid = (String) map.get("storageid");
			StorageInfo storageInfo  = getStorageInfoByID(storageid);
			if(null!=storageInfo){
				map.put("storagename", storageInfo.getName());
			}
			
			map.put("bookamount", price.multiply(booknum).setScale(2, BigDecimal.ROUND_HALF_UP));
			map.put("realamount", price.multiply(realnum).setScale(2, BigDecimal.ROUND_HALF_UP));
			map.put("profitlossamount", price.multiply(profitlossnum).setScale(2, BigDecimal.ROUND_HALF_UP));

		}
		return list;
	}

	@Override
	public Map updateCheckListDataByImport(List<Map> list,String id) throws Exception {
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
		SysUser sysUser = getSysUser();
		int i = 0;
		int truenum = 0;
		int falsenum = 0;
		for(Map map : list){
			String checklistid = (String) map.get("id");
			map.put("checklistid", id);
			if(id.equals(checklistid)){
				String goodsid = (String) map.get("goodsid");
				//明细编号
				String detailid = (String) map.get("detailid");
				String realnumStr = (String) map.get("realnum");
				String auxrealnumStr = (String) map.get("auxrealnum");
				String auxrealnumremainderStr = (String) map.get("auxrealnumremainder");
				
				String checkuserid = null;
				Personnel personnel = getPersonnelByName((String) map.get("checkusername"));
				if(null!=personnel){
					checkuserid = personnel.getId();
				}else{
					checkuserid = sysUser.getPersonnelid();
					if(null==checkuserid){
						checkuserid = "";
					}
				}
				CheckListDetail checkListDetail = checkListMapper.getCheckListDetailInfo(detailid);
				if(null==checkListDetail){
					checkListDetail = checkListMapper.getCheckListDetailInfoByGoodsid(checklistid, goodsid);
				}
				if(null!=checkListDetail){
					BigDecimal totalnum = BigDecimal.ZERO;
					//计算实际数量
					BigDecimal realnum = BigDecimal.ZERO;
					if(null!=realnumStr && !"".equals(realnumStr)){
						realnum = new BigDecimal(realnumStr);
					}
					if(decimalScale == 0){
						realnum = realnum.setScale(decimalScale,BigDecimal.ROUND_DOWN);
					}else{
						realnum = realnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
					}
					if(realnum.compareTo(BigDecimal.ZERO)!=0){
						totalnum = realnum;
					}else{
						BigDecimal auxrealnum =  BigDecimal.ZERO;
						if(null!=auxrealnumStr && !"".equals(auxrealnumStr)){
							auxrealnum = new BigDecimal(auxrealnumStr);
						}
						if(decimalScale == 0){
							auxrealnum = auxrealnum.setScale(decimalScale,BigDecimal.ROUND_DOWN);
						}else{
							auxrealnum = auxrealnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
						}
						BigDecimal auxrealnumremainder = BigDecimal.ZERO;
						if(null!=auxrealnumremainderStr && !"".equals(auxrealnumremainderStr)){
							auxrealnumremainder = new BigDecimal(auxrealnumremainderStr);
						}
						if(decimalScale == 0){
							auxrealnumremainder = auxrealnumremainder.setScale(decimalScale,BigDecimal.ROUND_DOWN);
						}else{
							auxrealnumremainder = auxrealnumremainder.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
						}
						//辅单位转成主单位
						Map returnmap = retMainUnitByUnitAndGoodid(auxrealnum, goodsid);
						totalnum = (BigDecimal) returnmap.get("mainUnitNum");
						totalnum = totalnum.add(auxrealnumremainder);
					}
					Map auxmap = countGoodsInfoNumber(checkListDetail.getGoodsid(), checkListDetail.getAuxunitid(), totalnum);
					String auxrealnumdetail = (String) auxmap.get("auxnumdetail");
					String auxrealnumstr = (String) auxmap.get("auxInteger");
					String auxremainderstr = (String) auxmap.get("auxremainder");
					BigDecimal auxrealnum = new BigDecimal(auxrealnumstr);
					BigDecimal auxrealnumremainder = new BigDecimal(auxremainderstr);
					
					checkListDetail.setRealnum(totalnum);
					checkListDetail.setAuxrealnum(auxrealnum);
					checkListDetail.setAuxrealremainder(auxrealnumremainder);
					checkListDetail.setAuxrealnumdetail(auxrealnumdetail);
					
					//计算盈亏数量=实际数量-账面数量
					BigDecimal profitlossnum = totalnum.subtract(checkListDetail.getBooknum());
					Map profitlossnumMap = countGoodsInfoNumber(checkListDetail.getGoodsid(), checkListDetail.getAuxunitid(), profitlossnum);
					checkListDetail.setProfitlossnum(profitlossnum);
					checkListDetail.setAuxprofitlossnumdetail((String) profitlossnumMap.get("auxnumdetail"));
					checkListDetail.setCheckuserid(checkuserid);
					if(profitlossnum.compareTo(BigDecimal.ZERO)==0){
						checkListDetail.setIstrue("1");
						truenum ++;
					}else{
						checkListDetail.setIstrue("0");
						falsenum ++;
					}
					int j = checkListMapper.updateCheckListDetail(checkListDetail);
					if(j>0){
						i++;
					}
				}
			}
		}
		CheckList checkList = checkListMapper.getCheckListInfo(id);
		if(1==checkList.getCheckno()){
			checkList.setChecknum(truenum+falsenum);
//			checkList.setTruenum(truenum);
		}else{
			CheckList oldCheckList = checkListMapper.getCheckListInfo(checkList.getSourceid());
			if(null!=oldCheckList){
				checkList.setChecknum(oldCheckList.getChecknum());
//				checkList.setTruenum(oldCheckList.getChecknum()-falsenum);
			}
		}
//		if(checkList.getChecknum()==checkList.getTruenum()){
//			checkList.setIstrue("1");
//		}else{
//			checkList.setIstrue("0");
//		}
		checkListMapper.editCheckList(checkList);
		Map returnMap = new HashMap();
		returnMap.put("flag", true);
		returnMap.put("success", i);
		returnMap.put("failure", list.size()-i);
		return returnMap;
	}

	@Override
	public List getImoportTxtData(
			File file, List<String> paramList,String id) throws Exception {
		List resultList = new ArrayList();
		try {
			InputStream is = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();;//用来保存每行读取的内容
            while (null != line) {//如果 line 为空说明读完了
				Map<String, Object> map2 = new HashMap<String, Object>();
				if(StringUtils.isNotEmpty(line)){
					String goodsid = line.substring(0, 15);
					int charlen = 15;
			        char[] chara = line.toCharArray();
			        char[] namechar = new char[78];
			        int y = 0;
			        for (int i = 15; i < chara.length; i++) {
						String b = chara[i] + "";
						byte[] bytes = b.getBytes();
						charlen = charlen + bytes.length;
						if(charlen <= 78){
							namechar[y] = chara[i];
							y++;
						}
					}
			        String goodsname = String.valueOf(namechar);
			        String realnumstr = line.substring(15+y, line.length());
			        Map<String,Object> map3 = new HashMap<String, Object>();
			        map3.put("id", id);
			        map3.put("goodsid", goodsid.trim());
			        map3.put("name", goodsname);
			        map3.put("realnum", realnumstr.trim());
			        resultList.add(map3);
				}
				line = reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	@Override
	public PageData getCheckListDetailData(PageMap pageMap) throws Exception {
		String id = (String)pageMap.getCondition().get("checklistid");
		CheckList checkList = checkListMapper.getCheckListInfo(id);
		List<CheckListDetail> detailList = new ArrayList<CheckListDetail>();
		if(null != checkList){
			detailList = checkListMapper.getCheckListDetailListByPageMap(pageMap);
			for(CheckListDetail checkListDetail : detailList){
				//商品详情
				GoodsInfo goodsInfo = getGoodsInfoByID(checkListDetail.getGoodsid());
				if(goodsInfo!=null){
					goodsInfo.setItemno(getItemnoByGoodsAndStorage(checkListDetail.getGoodsid(),checkList.getStorageid()));
				}
				checkListDetail.setGoodsInfo(goodsInfo);
				checkListDetail.setItemno(getItemnoByGoodsAndStorage(checkListDetail.getGoodsid(),checkListDetail.getStorageid()));
				if(null!=goodsInfo){
					checkListDetail.setGoodsname(goodsInfo.getName());
					checkListDetail.setBrandName(goodsInfo.getBrandName());
					checkListDetail.setModel(goodsInfo.getModel());
					checkListDetail.setBarcode(goodsInfo.getBarcode());
					checkListDetail.setBoxnum(goodsInfo.getBoxnum());
					checkListDetail.setSpell(goodsInfo.getSpell());
					//商品分类名称
					WaresClass waresClass = getBaseFilesGoodsMapper().getWaresClassInfo(goodsInfo.getDefaultsort());
					if(null!=waresClass){
						checkListDetail.setGoodssortname(waresClass.getName());
					}
				}
				StorageLocation storageLocation = getStorageLocationByID(checkListDetail.getStoragelocationid());
				if(null!=storageLocation){
					checkListDetail.setStoragelocationname(storageLocation.getName());
				}
				Personnel personnel = getPersonnelById(checkListDetail.getCheckuserid());
				if(null!=personnel){
					checkListDetail.setCheckusername(personnel.getName());
				}
				StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(checkList.getStorageid(), checkListDetail.getGoodsid());
				if(null!=storageSummary){
					checkListDetail.setExistingnum(storageSummary.getExistingnum());
				}
			}
		}
		int count = checkListMapper.getCheckListDetailCountByPageMap(pageMap);
		PageData pageData = new PageData(count,detailList,pageMap);
		List<CheckListDetail> foot = new ArrayList<CheckListDetail>();
		CheckListDetail checkListDetailSum = checkListMapper.getCheckListDetailSumByPageMap(pageMap);
		if(null != checkListDetailSum){
			checkListDetailSum.setGoodsid("合计");
			foot.add(checkListDetailSum);
		}
		pageData.setFooter(foot);
		return pageData;
	}

	@Override
	public Map addCheckListDetail(CheckList checkList,String storageid, String brands, String goodssorts) throws Exception {
		boolean flag =false;
		Map map = new HashMap();
		if(null!=checkList){
			List detailList = getCheckListDetail(storageid, brands, goodssorts);
			if(null != detailList && detailList.size() != 0){
				if(StringUtils.isEmpty(checkList.getId())){
					if (isAutoCreate("t_storage_checklist")) {
						// 获取自动编号
						String id = getAutoCreateSysNumbderForeign(checkList, "t_storage_checklist");
						checkList.setId(id);
					}
					checkList.setStatus("2");
					flag = addCheckList(checkList, detailList);
				}else{
					flag = editCheckList(checkList, detailList);
				}
				map.put("id",checkList.getId());
			}
		}
		map.put("flag",flag);
		return map;
	}

	@Override
	public boolean doCheckListDetailIsHadGoods(String goodsid, String checklistid) throws Exception {
		boolean flag = false;
		if(StringUtils.isNotEmpty(checklistid)){
			flag = checkListMapper.doCheckListDetailIsHadGoods(goodsid,checklistid) > 0;
		}
		return flag;
	}

	@Override
	public Map addSaveCheckListDetail(CheckList checkList,List<CheckListDetail> detailList) throws Exception {
		boolean flag =false;
		Map map = new HashMap();
		if(null!=checkList && null != detailList && detailList.size() != 0){
			if(StringUtils.isEmpty(checkList.getId())){
				if (isAutoCreate("t_storage_checklist")) {
					// 获取自动编号
					String id = getAutoCreateSysNumbderForeign(checkList, "t_storage_checklist");
					checkList.setId(id);
				}
				checkList.setStatus("2");
				flag = addCheckList(checkList, detailList);
			}else{
				CheckList checkList1 = checkListMapper.getCheckListInfo(checkList.getId());
				int j=checkListMapper.getCheckListDetailCountByChecklistid(checkList.getId());
				for(CheckListDetail checkListDetail : detailList){
					checkListDetail.setId(null);
					GoodsInfo goodsInfo = getAllGoodsInfoByID(checkListDetail.getGoodsid());
					if(null!=goodsInfo){
						checkListDetail.setBrandid(goodsInfo.getBrand());
					}
					checkListDetail.setCheckuserid(checkList1.getCheckuserid());
					checkListDetail.setChecklistid(checkList1.getId());
					checkListDetail.setStorageid(checkList1.getStorageid());
					checkListDetail.setSeq(j);
					checkListDetail.setProfitlossnum(checkListDetail.getRealnum().subtract(checkListDetail.getBooknum()));
					Map auxMap = countGoodsInfoNumber(checkListDetail.getGoodsid(), checkListDetail.getAuxunitid(), checkListDetail.getProfitlossnum());
					if(null!=auxMap){
						String auxprofitlossnumdetail = (String) auxMap.get("auxnumdetail");
						BigDecimal auxprofitlossnum  = (BigDecimal) auxMap.get("auxnum");
						checkListDetail.setAuxprofitlossnum(auxprofitlossnum);
						checkListDetail.setAuxprofitlossnumdetail(auxprofitlossnumdetail);
					}
					if(checkListDetail.getBooknum().compareTo(checkListDetail.getRealnum())==0){
						checkListDetail.setIstrue("1");
					}else{
						checkListDetail.setIstrue("0");
					}
				}
				flag = checkListMapper.addCheckListDetailBatch(detailList)>0;
			}
			map.put("id",checkList.getId());
		}
		map.put("flag",flag);
		return map;
	}

	@Override
	public Map editSaveCheckListDetail(CheckList checkList, List<CheckListDetail> detailList) throws Exception {
		boolean flag =false;
		Map map = new HashMap();
		CheckList oldCheckList = checkListMapper.getCheckListInfo(checkList.getId());
		if(null==oldCheckList){
			map.put("flag",false);
			map.put("msg","找不到对应的盘点单");
			return map;
		}else if(!("2".equals(oldCheckList.getStatus()) || "1".equals(oldCheckList.getStatus()))){
			map.put("flag",false);
			map.put("msg","审核或者关闭的盘点单，不允许调整。");
			return map;
		}
		if(null!=checkList && null != detailList && detailList.size() != 0){
			for(CheckListDetail checkListDetail : detailList){
				if("null".equals(checkListDetail.getBatchno())){
					checkListDetail.setBatchno("");
				}
				checkListDetail.setProfitlossnum(checkListDetail.getRealnum().subtract(checkListDetail.getBooknum()));
				//获取金额 辅单位数量 辅单位数量描述
				Map profitlossmap = countGoodsInfoNumber(checkListDetail.getGoodsid(), checkListDetail.getAuxunitid(), checkListDetail.getProfitlossnum());
				checkListDetail.setAuxprofitlossnum((BigDecimal) profitlossmap.get("auxnum"));
				checkListDetail.setAuxprofitlossnumdetail((String) profitlossmap.get("auxnumdetail"));
				checkListDetail.setProfitlossamount(checkListDetail.getProfitlossnum().multiply(checkListDetail.getPrice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
				flag = checkListMapper.updateCheckListDetailByGoodsid(checkListDetail)>0;
			}

			map.put("id",checkList.getId());
		}
		map.put("flag",flag);
		return map;
	}

	@Override
	public Map addDynamicCheckList(Map map) throws Exception {
		//根据选中的时间获取其期间内库存出入库记录商品
        String storageid = null;
        String days = "1";
        String checkuserid = null;
        String remark = "";
        boolean flag = false;
        String msg = "";
        String billids = "";
        //1不拆分2按品牌拆分3按供应商拆分4按商品分类拆分
        String splitbill = "1";
        if(map.containsKey("storageid")){
            storageid = (String) map.get("storageid");
            if(map.containsKey("days")){
                days = (String) map.get("days");
            }
            if(map.containsKey("checkuserid")){
                checkuserid = (String) map.get("checkuserid");
            }
            if(map.containsKey("remark")){
                remark = (String) map.get("remark");
            }
            if(map.containsKey("splitbill")){
                splitbill = (String) map.get("splitbill");
            }
            String daysBdate = CommonUtils.getBeforeDateInDays(Integer.parseInt(days)-1);
            String[] storageidArr = storageid.split(",");
            //是否按批次盘点
            String IsCheckListUseBatch = getSysParamValue("IsCheckListUseBatch");
            if(StringUtils.isEmpty(IsCheckListUseBatch)){
                IsCheckListUseBatch ="0";
            }
            SysUser sysUser = getSysUser();
            for(String aStorageid : storageidArr){
                List<String> splitList = new ArrayList<String>();
                if("1".equals(splitbill)){
                    splitList.add("1");
                }else if("2".equals(splitbill)){
                    splitList = getStorageSummaryMapper().getStorageSummaryBatchChangeBrandListInDays(aStorageid,daysBdate);
                }else if("3".equals(splitbill)){
                    splitList = getStorageSummaryMapper().getStorageSummaryBatchChangeSupplierListInDays(aStorageid,daysBdate);
                }else if("4".equals(splitbill)){
                    splitList = getStorageSummaryMapper().getStorageSummaryBatchChangeGoodsSortListInDays(aStorageid,daysBdate);
                }
                for(String sid : splitList){
                    CheckList checkList = new CheckList();
                    if (isAutoCreate("t_storage_checklist")) {
                        // 获取自动编号
                        String id = getAutoCreateSysNumbderForeign(checkList, "t_storage_checklist");
                        checkList.setId(id);
                    }else{
                        checkList.setId("KCPD-"+CommonUtils.getDataNumber1());
                    }
                    checkList.setStorageid(aStorageid);
                    checkList.setBusinessdate(CommonUtils.getTodayDataStr());
                    checkList.setRemark(remark);
                    StorageInfo storageInfo = getStorageInfoByID(aStorageid);
                    if(StringUtils.isEmpty(checkuserid)){
                        if(null!=storageInfo && StringUtils.isNotEmpty(storageInfo.getManageruserid())){
                            checkList.setCheckuserid(storageInfo.getManageruserid());
                        }else{
                            checkList.setCheckuserid(checkuserid);
                        }
                    }else {
                        checkList.setCheckuserid(checkuserid);
                    }
                    checkList.setAdduserid(sysUser.getUserid());
                    checkList.setAddusername(sysUser.getName());
                    checkList.setAdddeptid(sysUser.getDepartmentid());
                    checkList.setAdddeptname(sysUser.getDepartmentname());

                    List<StorageSummaryBatch> summaryBatchlist = getStorageSummaryMapper().getStorageSummaryBatchChangeListInDays(aStorageid,daysBdate,IsCheckListUseBatch,splitbill,sid);
                    List list = new ArrayList();
                    for(StorageSummaryBatch storageSummaryBatch : summaryBatchlist){
                        CheckListDetail checkListDetail = new CheckListDetail();
                        checkListDetail.setChecklistid(checkList.getId());
                        checkListDetail.setCheckuserid(checkList.getCheckuserid());
                        checkListDetail.setIstrue("0");
                        if(StringUtils.isNotEmpty(storageSummaryBatch.getId())){
                            checkListDetail.setSummarybatchid(storageSummaryBatch.getId());
                        }
                        checkListDetail.setGoodsid(storageSummaryBatch.getGoodsid());
                        //商品详情
                        GoodsInfo goodsInfo = getAllGoodsInfoByID(storageSummaryBatch.getGoodsid());
                        if(null!=goodsInfo){
                            checkListDetail.setGoodsname(goodsInfo.getName());
                            checkListDetail.setBrandid(goodsInfo.getBrand());
                            checkListDetail.setBrandName(goodsInfo.getBrandName());
                            checkListDetail.setModel(goodsInfo.getModel());
                            checkListDetail.setBarcode(goodsInfo.getBarcode());
                            checkListDetail.setUnitid(goodsInfo.getMainunit());
                            checkListDetail.setUnitname(goodsInfo.getMainunitName());
                            checkListDetail.setBoxnum(goodsInfo.getBoxnum());
                            //商品分类名称
                            WaresClass waresClass = getBaseFilesGoodsMapper().getWaresClassInfo(goodsInfo.getDefaultsort());
                            if(null!=waresClass){
                                checkListDetail.setGoodssortname(waresClass.getName());
                            }
                        }
                        checkListDetail.setStorageid(storageSummaryBatch.getStorageid());
                        checkListDetail.setStoragelocationid(storageSummaryBatch.getStoragelocationid());
                        StorageLocation storageLocation = getStorageLocationByID(storageSummaryBatch.getStoragelocationid());
                        if(null!=storageLocation){
                            checkListDetail.setStoragelocationname(storageLocation.getName());
                        }
                        Personnel personnel = getPersonnelById(checkListDetail.getCheckuserid());
                        if(null!=personnel){
                            checkListDetail.setCheckusername(personnel.getName());
                        }

                        checkListDetail.setBooknum(storageSummaryBatch.getExistingnum());
                        checkListDetail.setRealnum(new BigDecimal(0));
                        checkListDetail.setProfitlossnum(storageSummaryBatch.getExistingnum().negate());
                        checkListDetail.setAuxunitid(storageSummaryBatch.getAuxunitid());
                        checkListDetail.setAuxunitname(storageSummaryBatch.getAuxunitname());
                        checkListDetail.setPrice(goodsInfo.getNewbuyprice());
                        //盘点单金额 = 批次单价*现存量
                        checkListDetail.setAmount(goodsInfo.getNewbuyprice().multiply(storageSummaryBatch.getExistingnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
                        checkListDetail.setBatchno(storageSummaryBatch.getBatchno());
                        checkListDetail.setProduceddate(storageSummaryBatch.getProduceddate());
                        checkListDetail.setDeadline(storageSummaryBatch.getDeadline());

                        Map auxbooknumMap = countGoodsInfoNumber(storageSummaryBatch.getGoodsid(), storageSummaryBatch.getAuxunitid(), storageSummaryBatch.getExistingnum());
                        BigDecimal bigDecimal = (BigDecimal) auxbooknumMap.get("auxnum");
                        checkListDetail.setAuxbooknum(bigDecimal);
                        checkListDetail.setAuxbooknumdetail((String)auxbooknumMap.get("auxnumdetail"));
                        checkListDetail.setAuxprofitlossnumdetail("-"+(String)auxbooknumMap.get("auxnumdetail"));
                        list.add(checkListDetail);
                    }
                    if(list.size()>0){
                        int z = checkListMapper.addCheckListDetailBatch(list);
                        checkList.setChecknum(z);
                        checkList.setIsfinish("0");
                        checkList.setStatus("2");
                        checkList.setCheckno(1);
                        checkList.setSourceid(checkList.getId());
                        int i = checkListMapper.addCheckList(checkList);
                        if(i>0){
                            if(msg.indexOf(storageInfo.getName())==-1){
                                if(StringUtils.isEmpty(msg)){
                                    msg ="盘点仓库："+storageInfo.getName();
                                }else{
                                    msg +=","+storageInfo.getName();
                                }
                            }
                            billids +=checkList.getId()+",";
                        }
                        flag = true;
                    }else{
                        flag = false;
                        msg += "没有需要盘点的明细";
                    }
                }
            }
            if(flag){
                msg +=";生成盘点单："+billids;
            }
        }else{
            msg = "仓库为空";
        }
        Map rMap = new HashMap();
        rMap.put("flag",flag);
        rMap.put("msg",msg);
		return rMap;
	}

	@Override
	public Map deleteCheckListDetails(CheckList checkList,List<CheckListDetail> delDetailList) throws Exception {
		boolean flag =false;
		Map map = new HashMap();
		if(null!=checkList && null != delDetailList && delDetailList.size() != 0){
			if(StringUtils.isNotEmpty(checkList.getId())){
				for(CheckListDetail checkListDetail : delDetailList){
					if("null".equals(checkListDetail.getBatchno())){
						checkListDetail.setBatchno("");
					}
					flag=checkListMapper.deleteCheckListDetailByGoodsid(checkListDetail)>0;
				}

			}
			map.put("id",checkList.getId());
		}
		map.put("flag",flag);
		return map;
	}

	@Override
	public CheckListDetail getCheckListDetailById(String checklistid,String id)throws Exception {
		CheckList checkList = checkListMapper.getCheckListInfo(checklistid);
		CheckListDetail checkListDetail= checkListMapper.getCheckListDetailByGoodsidAndChecklistid(id);
		//商品详情
		GoodsInfo goodsInfo = getGoodsInfoByID(checkListDetail.getGoodsid());
		checkListDetail.setGoodsInfo(goodsInfo);
		checkListDetail.setItemno(getItemnoByGoodsAndStorage(checkListDetail.getGoodsid(),checkListDetail.getStorageid()));
		if(null!=goodsInfo){
			checkListDetail.setGoodsname(goodsInfo.getName());
			checkListDetail.setBrandName(goodsInfo.getBrandName());
			checkListDetail.setModel(goodsInfo.getModel());
			checkListDetail.setBarcode(goodsInfo.getBarcode());
			checkListDetail.setBoxnum(goodsInfo.getBoxnum());

			//商品分类名称
			WaresClass waresClass = getBaseFilesGoodsMapper().getWaresClassInfo(goodsInfo.getDefaultsort());
			if(null!=waresClass){
				checkListDetail.setGoodssortname(waresClass.getName());
			}
		}
		StorageLocation storageLocation = getStorageLocationByID(checkListDetail.getStoragelocationid());
		if(null!=storageLocation){
			checkListDetail.setStoragelocationname(storageLocation.getName());
		}
		Personnel personnel = getPersonnelById(checkListDetail.getCheckuserid());
		if(null!=personnel){
			checkListDetail.setCheckusername(personnel.getName());
		}
		StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(checkList.getStorageid(), checkListDetail.getGoodsid());
		if(null!=storageSummary){
			checkListDetail.setExistingnum(storageSummary.getExistingnum());
		}
		return checkListDetail;
	}

	@Override
	public boolean updatePrintTimes(String id) throws Exception {
		return checkListMapper.updatePrintTimes(id) > 0;
	}
}

