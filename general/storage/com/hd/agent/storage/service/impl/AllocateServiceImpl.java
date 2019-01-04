/**
 * @(#)AllocateServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 24, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.OrderDetail;
import com.hd.agent.storage.dao.AllocateEnterMapper;
import com.hd.agent.storage.dao.AllocateNoticeMapper;
import com.hd.agent.storage.dao.AllocateOutMapper;
import com.hd.agent.storage.dao.StorageSummaryMapper;
import com.hd.agent.storage.model.*;
import com.hd.agent.storage.service.IAllocateService;

import com.hd.agent.storage.service.IStorageForSalesService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

/**
 * 
 * 调拨相关service实现类
 * @author chenwei
 */
public class AllocateServiceImpl extends BaseStorageServiceImpl implements
		IAllocateService {
	/**
	 * 调拨入库单dao
	 */
	private AllocateEnterMapper allocateEnterMapper;
	/**
	 * 调拨通知单dao
	 */
	private AllocateNoticeMapper allocateNoticeMapper;
	/**
	 * 调拨出库单dao
	 */
	private AllocateOutMapper allocateOutMapper;

	private IStorageForSalesService storageForSalesService;


	public AllocateEnterMapper getAllocateEnterMapper() {
		return allocateEnterMapper;
	}
	public void setAllocateEnterMapper(AllocateEnterMapper allocateEnterMapper) {
		this.allocateEnterMapper = allocateEnterMapper;
	}
	public AllocateNoticeMapper getAllocateNoticeMapper() {
		return allocateNoticeMapper;
	}
	public void setAllocateNoticeMapper(AllocateNoticeMapper allocateNoticeMapper) {
		this.allocateNoticeMapper = allocateNoticeMapper;
	}
	public AllocateOutMapper getAllocateOutMapper() {
		return allocateOutMapper;
	}
	public void setAllocateOutMapper(AllocateOutMapper allocateOutMapper) {
		this.allocateOutMapper = allocateOutMapper;
	}

	public IStorageForSalesService getStorageForSalesService() {
		return storageForSalesService;
	}

	public void setStorageForSalesService(IStorageForSalesService storageForSalesService) {
		this.storageForSalesService = storageForSalesService;
	}


	@Override
	public Map addallocateNotice(AllocateNotice allocateNotice,
			List<AllocateNoticeDetail> detailList) throws Exception {
		if (isAutoCreate("t_storage_allocate_notice")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(allocateNotice, "t_storage_allocate_notice");
			allocateNotice.setId(id);
		}else{
			allocateNotice.setId("DBTZ-"+CommonUtils.getDataNumberSendsWithRand());
		} 
		for(AllocateNoticeDetail allocateNoticeDetail : detailList){
			//计算辅单位数量
			Map auxmap = countGoodsInfoNumber(allocateNoticeDetail.getGoodsid(), allocateNoticeDetail.getAuxunitid(), allocateNoticeDetail.getUnitnum());
			if(auxmap.containsKey("auxnum")){
				allocateNoticeDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
			}
			allocateNoticeDetail.setStorageid(allocateNotice.getOutstorageid());
//			if(allocateNoticeDetail.getCostprice()==null)
			allocateNoticeDetail.setBillno(allocateNotice.getId());
			allocateNoticeMapper.addAllocateNoticeDetail(allocateNoticeDetail);
		}
		SysUser sysUser = getSysUser();
		allocateNotice.setAdddeptid(sysUser.getDepartmentid());
		allocateNotice.setAdddeptname(sysUser.getDepartmentname());
		allocateNotice.setAdduserid(sysUser.getUserid());
		allocateNotice.setAddusername(sysUser.getName());
		int i = allocateNoticeMapper.addAllocateNotice(allocateNotice);
		Map map = new HashMap();
		map.put("flag", i>0);
		map.put("id", allocateNotice.getId());
		return map;
	}
	@Override
	public PageData showAllocateNoticeList(PageMap pageMap) throws Exception{
		String dataSql = getDataAccessRule("t_storage_allocate_notice", null);
		pageMap.setDataSql(dataSql);
		List<AllocateNotice> list = allocateNoticeMapper.showAllocateNoticeList(pageMap);
		for(AllocateNotice allocateNotice : list){
			StorageInfo outstorageInfo = getStorageInfoByID(allocateNotice.getOutstorageid());
			if(null!=outstorageInfo){
				allocateNotice.setOutstoragename(outstorageInfo.getName());
			}
			StorageInfo enterstorageInfo = getStorageInfoByID(allocateNotice.getEnterstorageid());
			if(null!=enterstorageInfo){
				allocateNotice.setEnterstoragename(enterstorageInfo.getName());
			}
		}
		PageData pageData = new PageData(allocateNoticeMapper.showAllocateNoticeCount(pageMap),list,pageMap);
		return pageData;
	}

	/**
	 * 获取批量添加的商品
	 *
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2015-12-2
	 */
	@Override
	public PageData getGoodsByBrandAndSort(PageMap pageMap,String storageid)  throws Exception {
		BigDecimal unitNum = BigDecimal.ZERO;
		String sql = getDataAccessRule("t_base_goods_info", null); //数据权限
		pageMap.setDataSql(sql);

		List<GoodsInfo> GoodsInfoList = getBaseFilesGoodsMapper().getGoodsInfoListByBrandAndSort(pageMap);
		List<AllocateNoticeDetail> list=new ArrayList();
		for(GoodsInfo goodsInfo : GoodsInfoList){
			GoodsInfo backInfo = getAllGoodsInfoByID(goodsInfo.getId());
			if(null == backInfo){
				goodsInfo = getAllGoodsInfoByID(goodsInfo.getOldId());
			}else{
				goodsInfo = getAllGoodsInfoByID(goodsInfo.getId());
			}
			AllocateNoticeDetail allocateNoticeDetail = new AllocateNoticeDetail();
			allocateNoticeDetail.setGoodsid(goodsInfo.getId());
			allocateNoticeDetail.setGoodsInfo(goodsInfo);
			allocateNoticeDetail.setUnitid(goodsInfo.getMainunit());
			allocateNoticeDetail.setUnitname(goodsInfo.getMainunitName());
			allocateNoticeDetail.setStorageid(storageid);

			StorageSummary storageSummary = new StorageSummary();
			if (StringUtils.isNotEmpty(storageid)) {
				storageSummary = storageForSalesService.getStorageSummarySumByGoodsidAndStorageid(goodsInfo.getId(), storageid);
			} else {
				storageSummary = storageForSalesService.getStorageSummarySumByGoodsid(goodsInfo.getId());
			}
			StorageInfo storageInfo = getStorageInfoByID(storageid);
			//仓库独立核算 成本价设置为该仓库的成本价


			if (null != storageSummary) {
				allocateNoticeDetail.setUsablenum(storageSummary.getUsablenum());
				if(null!=storageInfo && "1".equals(storageInfo.getIsaloneaccount())){
					allocateNoticeDetail.setTaxprice(storageSummary.getCostprice());
				}else{
					allocateNoticeDetail.setTaxprice(goodsInfo.getNewstorageprice());
				}
			} else {
				allocateNoticeDetail.setUsablenum(BigDecimal.ZERO);
				allocateNoticeDetail.setTaxprice(goodsInfo.getNewstorageprice());
			}

			list.add(allocateNoticeDetail);
		}
		int count = getBaseFilesGoodsMapper().getGoodsInfoListByBrandAndSortCount(pageMap);
		return new PageData(count, list, pageMap) ;
	}

	/**
	 * 添加批量商品明细
	 *
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2015-12-2
	 */
	@Override
	public List AddOrderDetailByBrandAndSort(List<AllocateNoticeDetail>  allocateNoticeDetailList) throws Exception {
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
		List<AllocateNoticeDetail>  list =new ArrayList();
		for(AllocateNoticeDetail allocateNoticeDetail:allocateNoticeDetailList){
			if(null != allocateNoticeDetail.getAuxremainder() || null != allocateNoticeDetail.getAuxnum()){
				BigDecimal boxnum = allocateNoticeDetail.getGoodsInfo().getBoxnum();
				BigDecimal auxremainder = BigDecimal.ZERO;
				BigDecimal auxnum = BigDecimal.ZERO;
				if(null != allocateNoticeDetail.getAuxremainder()){
					auxremainder=allocateNoticeDetail.getAuxremainder();
				}
				if(null != allocateNoticeDetail.getAuxnum()){
					auxnum=allocateNoticeDetail.getAuxnum();
				}
				BigDecimal unitnum = boxnum.multiply(auxnum).add(auxremainder);
				if(auxremainder.compareTo(boxnum) >= 0){
					BigDecimal mod=auxremainder.divide(boxnum,0,BigDecimal.ROUND_DOWN);
					auxnum = auxnum.add(mod);
					auxremainder = auxremainder.subtract(mod.multiply(boxnum));
				}
				GoodsInfo goodsInfo = getGoodsInfoByID(allocateNoticeDetail.getGoodsid());
				allocateNoticeDetail.setAuxunitid(goodsInfo.getAuxunitid());
				allocateNoticeDetail.setAuxunitname(goodsInfo.getAuxunitname());
				allocateNoticeDetail.setCostprice(allocateNoticeDetail.getTaxprice());
				allocateNoticeDetail.setAuxremainder(auxremainder);
				allocateNoticeDetail.setAuxnum(auxnum);
				allocateNoticeDetail.setUnitnum(unitnum);
				allocateNoticeDetail.setBoxprice(allocateNoticeDetail.getTaxprice().multiply(boxnum).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
				allocateNoticeDetail.setTaxamount(allocateNoticeDetail.getTaxprice().multiply(allocateNoticeDetail.getUnitnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
				allocateNoticeDetail.setAuxnumdetail(CommonUtils.strDigitNumDeal(Integer.toString(auxnum.intValue())+allocateNoticeDetail.getAuxunitname()+auxremainder.setScale(decimalScale,BigDecimal.ROUND_HALF_UP)+allocateNoticeDetail.getUnitname()));
				allocateNoticeDetail.setGoodsInfo(goodsInfo);

				if("1".equals(goodsInfo.getIsbatch())){
					StorageSummaryBatch storageSummaryBatch = getStorageSummaryMapper().getStorageSummaryBatchByStorageidNew(allocateNoticeDetail.getStorageid(),allocateNoticeDetail.getGoodsid());
					if(null==storageSummaryBatch){
						storageSummaryBatch = getStorageSummaryMapper().getStorageSummaryBatchByStorageidLast(allocateNoticeDetail.getStorageid(),allocateNoticeDetail.getGoodsid());
					}
					if(null!=storageSummaryBatch){
						allocateNoticeDetail.setSummarybatchid(storageSummaryBatch.getId());
						allocateNoticeDetail.setBatchno(storageSummaryBatch.getBatchno());
						allocateNoticeDetail.setProduceddate(storageSummaryBatch.getProduceddate());
						allocateNoticeDetail.setDeadline(storageSummaryBatch.getDeadline());
						allocateNoticeDetail.setStoragelocationid(storageSummaryBatch.getStoragelocationid());
						allocateNoticeDetail.setEnterbatchno(storageSummaryBatch.getBatchno());
						allocateNoticeDetail.setEnterproduceddate(storageSummaryBatch.getProduceddate());
						allocateNoticeDetail.setEnterdeadline(storageSummaryBatch.getDeadline());
						allocateNoticeDetail.setEnterstoragelocationid(storageSummaryBatch.getDeadline());

						allocateNoticeDetail.setUsablenum(storageSummaryBatch.getUsablenum());
					}
				}

				list.add(allocateNoticeDetail);
			}
		}
		return list;
	}


	/**
     * 为手机提供调拨通知单列表
     *
     * @param pageMap
     * @return
     * @throws Exception
     */
    @Override
    public List showAllocateNoticeListForPhone(PageMap pageMap) throws Exception {
        String dataSql = getDataAccessRule("t_storage_allocate_notice", null);
        pageMap.setDataSql(dataSql);
        List<AllocateNotice> list = allocateNoticeMapper.showAllocateNoticeListForPhone(pageMap);
        return list;
    }

    @Override
	public Map getAllocateNoticeInfo(String id) throws Exception {
		AllocateNotice allocateNotice = allocateNoticeMapper.getAllocateNoticeInfo(id);
		List<AllocateNoticeDetail> list = allocateNoticeMapper.getAllocateNoticeDetailListByBillno(id);
		for(AllocateNoticeDetail allocateNoticeDetail : list){
			if("2".equals(allocateNotice.getStatus())){
                if(StringUtils.isNotEmpty(allocateNoticeDetail.getSummarybatchid())){
                    StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(allocateNoticeDetail.getSummarybatchid());
                    if(null!=storageSummaryBatch){
                        allocateNoticeDetail.setUsablenum(storageSummaryBatch.getUsablenum());
                    }
                }else{
                    StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(allocateNotice.getOutstorageid(),allocateNoticeDetail.getGoodsid());
                    if(null!=storageSummaryBatch){
                        allocateNoticeDetail.setUsablenum(storageSummaryBatch.getUsablenum());
                    }
                }
			}
			GoodsInfo goodsInfo = getGoodsInfoByID(allocateNoticeDetail.getGoodsid());
			if(goodsInfo!=null){
				goodsInfo.setItemno(getItemnoByGoodsAndStorage(allocateNoticeDetail.getGoodsid(),allocateNoticeDetail.getStorageid()));
			}
			allocateNoticeDetail.setGoodsInfo(goodsInfo);
            if(null!=goodsInfo){
                allocateNoticeDetail.setBoxprice(allocateNoticeDetail.getTaxprice().multiply(goodsInfo.getBoxnum()).setScale(2,BigDecimal.ROUND_HALF_UP));
            }
			StorageInfo storageInfo = getStorageInfoByID(allocateNoticeDetail.getStorageid());
			if(null!=storageInfo){
				allocateNoticeDetail.setStoragename(storageInfo.getName());
			}
			StorageLocation storageLocation = getStorageLocation(allocateNoticeDetail.getStoragelocationid());
			if(null!=storageLocation){
				allocateNoticeDetail.setStoragelocationname(storageLocation.getName());
			}
			StorageLocation enterstorageLocation = getStorageLocation(allocateNoticeDetail.getEnterstoragelocationid());
			if(null!=enterstorageLocation){
				allocateNoticeDetail.setEnterstoragelocationname(enterstorageLocation.getName());
			}
			TaxType taxType = getTaxType(allocateNoticeDetail.getTaxtype());
			if(null!=taxType){
				allocateNoticeDetail.setTaxtypename(taxType.getName());
			}
		}
		Map map = new HashMap();
		map.put("allocateNotice", allocateNotice);
		map.put("detailList", list);
		return map;
	}
	@Override
	public boolean editAllocateNotice(AllocateNotice allocateNotice,
			List<AllocateNoticeDetail> detailList) throws Exception {
		AllocateNotice oldAllocateNotice = allocateNoticeMapper.getAllocateNoticeInfo(allocateNotice.getId());
		if(null==oldAllocateNotice ||"3".equals(oldAllocateNotice.getStatus()) || "4".equals(oldAllocateNotice.getStatus())){
			return false;
		}
		allocateNoticeMapper.deleteAllocateNoticeDetailByBillno(allocateNotice.getId());
		for(AllocateNoticeDetail allocateNoticeDetail : detailList){
			//计算辅单位数量
			Map auxmap = countGoodsInfoNumber(allocateNoticeDetail.getGoodsid(), allocateNoticeDetail.getAuxunitid(), allocateNoticeDetail.getUnitnum());
			if(auxmap.containsKey("auxnum")){
				allocateNoticeDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
			}
			allocateNoticeDetail.setStorageid(allocateNotice.getOutstorageid());
			allocateNoticeDetail.setBillno(allocateNotice.getId());
			allocateNoticeMapper.addAllocateNoticeDetail(allocateNoticeDetail);
		}
		SysUser sysUser = getSysUser();
		allocateNotice.setModifyusername(sysUser.getUserid());
		allocateNotice.setModifyusername(sysUser.getName());
		int i = allocateNoticeMapper.editAllocateNotice(allocateNotice);
		return i>0;
	}
	@Override
	public boolean deleteAllocateNotice(String id) throws Exception {
		boolean flag = false;
		AllocateNotice allocateNotice = allocateNoticeMapper.getAllocateNoticeInfo(id);
		if("1".equals(allocateNotice.getStatus()) || "2".equals(allocateNotice.getStatus())){
			allocateNoticeMapper.deleteAllocateNoticeDetailByBillno(id);
			int i = allocateNoticeMapper.deleteAllocateNotice(id);
			flag = i>0;
		}
		return flag;
	}
	@Override
	public Map auditAllocateNotice(String id) throws Exception {
		boolean flag = false;
		//下游单据编号
		String downid = "";
		String msg = "";
		AllocateNotice allocateNotice = allocateNoticeMapper.getAllocateNoticeInfo(id);
		if(null!=allocateNotice && ("2".equals(allocateNotice.getStatus()) || "6".equals(allocateNotice.getStatus()))){
			List<AllocateNoticeDetail> detailList = allocateNoticeMapper.getAllocateNoticeDetailListByBillno(id);
			boolean allotFlag = true;
			if(allotFlag){
				List allocateOutDetailList = new ArrayList();
				//生成下游单据
				AllocateOut allocateOut = new AllocateOut();
				allocateOut.setOutstorageid(allocateNotice.getOutstorageid());
				allocateOut.setEnterstorageid(allocateNotice.getEnterstorageid());
				allocateOut.setBusinessdate(allocateNotice.getBusinessdate());
				allocateOut.setStatus("2");
				allocateOut.setRemark(allocateNotice.getRemark());
				allocateOut.setSourcetype("1");
				allocateOut.setSourceid(allocateNotice.getId());
				allocateOut.setOutdeptid(allocateNotice.getOutdeptid());
				allocateOut.setEnterdeptid(allocateNotice.getEnterdeptid());
				allocateOut.setBilltype(allocateNotice.getBilltype());

				allocateOut.setField01(allocateNotice.getField01());
				allocateOut.setField02(allocateNotice.getField02());
				allocateOut.setField03(allocateNotice.getField03());
				allocateOut.setField04(allocateNotice.getField04());
				allocateOut.setField05(allocateNotice.getField05());
				allocateOut.setField06(allocateNotice.getField06());
				allocateOut.setField07(allocateNotice.getField07());
				allocateOut.setField08(allocateNotice.getField08());
				
				allocateOut.setAdddeptid(allocateNotice.getAdddeptid());
				allocateOut.setAdddeptname(allocateNotice.getAdddeptname());
				allocateOut.setAdduserid(allocateNotice.getAdduserid());
				allocateOut.setAddusername(allocateNotice.getAddusername());
				
				for(AllocateNoticeDetail allocateNoticeDetail : detailList){
					AllocateOutDetail allocateOutDetail = new AllocateOutDetail();
					allocateOutDetail.setSourceid(id);
					allocateOutDetail.setSourcedetailid(allocateNoticeDetail.getId()+"");
					
					allocateOutDetail.setSummarybatchid(allocateNoticeDetail.getSummarybatchid());
					allocateOutDetail.setStorageid(allocateNoticeDetail.getStorageid());
					allocateOutDetail.setStoragelocationid(allocateNoticeDetail.getStoragelocationid());
					allocateOutDetail.setEnterstoragelocationid(allocateNoticeDetail.getEnterstoragelocationid());
					allocateOutDetail.setGoodsid(allocateNoticeDetail.getGoodsid());
					allocateOutDetail.setBatchno(allocateNoticeDetail.getBatchno());
					allocateOutDetail.setProduceddate(allocateNoticeDetail.getProduceddate());
					allocateOutDetail.setDeadline(allocateNoticeDetail.getDeadline());
                    allocateOutDetail.setEnterbatchno(allocateNoticeDetail.getEnterbatchno());
                    allocateOutDetail.setEnterproduceddate(allocateNoticeDetail.getEnterproduceddate());
                    allocateOutDetail.setEnterdeadline(allocateNoticeDetail.getEnterdeadline());

					
					allocateOutDetail.setUnitid(allocateNoticeDetail.getUnitid());
					allocateOutDetail.setUnitname(allocateNoticeDetail.getUnitname());
					allocateOutDetail.setUnitnum(allocateNoticeDetail.getUnitnum());
					allocateOutDetail.setAuxunitid(allocateNoticeDetail.getAuxunitid());
					allocateOutDetail.setAuxnumdetail(allocateNoticeDetail.getAuxnumdetail());
					allocateOutDetail.setAuxunitname(allocateNoticeDetail.getAuxunitname());
					allocateOutDetail.setAuxnum(allocateNoticeDetail.getAuxnum());
					allocateOutDetail.setAuxremainder(allocateNoticeDetail.getAuxremainder());
					allocateOutDetail.setTotalbox(allocateNoticeDetail.getTotalbox());
					
					allocateOutDetail.setTaxprice(allocateNoticeDetail.getTaxprice());
					allocateOutDetail.setTaxamount(allocateNoticeDetail.getTaxamount());
					allocateOutDetail.setNotaxprice(allocateNoticeDetail.getNotaxprice());
					allocateOutDetail.setNotaxamount(allocateNoticeDetail.getNotaxamount());
					allocateOutDetail.setTax(allocateNoticeDetail.getTax());
					allocateOutDetail.setTaxtype(allocateNoticeDetail.getTaxtype());
					allocateOutDetail.setRemark(allocateNoticeDetail.getRemark());
					allocateOutDetail.setCostprice(allocateNoticeDetail.getCostprice());

					allocateOutDetailList.add(allocateOutDetail);
				}
				
				Map returnmap = addAllocateOut(allocateOut, allocateOutDetailList);
				boolean addFlag = (Boolean) returnmap.get("flag");
				if(addFlag){
					SysUser sysUser = getSysUser();
					String billdate=getAuditBusinessdate(allocateNotice.getBusinessdate());
					int i = allocateNoticeMapper.auditAllocateNotice(id, sysUser.getUserid(), sysUser.getName(),billdate);
					flag = i>0;
					downid = (String) returnmap.get("id");
					if(!flag){
					    throw new Exception("调拨通知单"+id+"审核失败.回滚数据");
                    }
				}else{
					flag = false;
					msg = (String) returnmap.get("msg");
				}
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("downid", downid);
		return map;
	}
	@Override
	public Map oppauditAllocateNotice(String id) throws Exception {
		Map map = new HashMap();
		String msg = "";
		boolean flag = false;
		AllocateNotice allocateNotice = allocateNoticeMapper.getAllocateNoticeInfo(id);
		if(null!=allocateNotice  && "3".equals(allocateNotice.getStatus())){
			//获取下游单据调拨出库单信息
			AllocateOut allocateOut = allocateOutMapper.getAllocateOutBySourceid(id);
			//判断下游单据是否删除 
			boolean delFlag = false;
			String allocateoutid = "";
			if(null!=allocateOut){
				allocateoutid = allocateOut.getId();
				delFlag = deleteAllocateOut(allocateOut.getId());
			}else{
				delFlag = true;
			}
			//无下游单据 或者下游单据已删除 则可以反审
			if(delFlag){
				SysUser sysUser = getSysUser();
				int i = allocateNoticeMapper.oppauditAllocateNotice(id, sysUser.getUserid(), sysUser.getName());
				flag = i>0;
			}else{
				msg = "下游调拨单:"+allocateoutid+"处于审核状态,不能反审.";
			}
		}else{
			msg = "单据未审核，不能反审。";
		}
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}
	@Override
	public Map addAllocateNoticeByImport(List<Map> list) throws Exception {
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
        String msg = "";
        List<String> disablegoodsid = new ArrayList<String>();
        List<String> nogoodsid = new ArrayList<String>();
        List<Integer> disabledIndex = new ArrayList();
        List<Integer> noIndex = new ArrayList();
        //循环找出禁用商品
        for(int i = 0 ; i < list.size() ; ++ i){
            Map<String,Object> data = (Map<String, Object>) list.get(i);
            String goodsid = (String) data.get("goodsid");
            GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
            if(null == goodsInfo  && StringUtils.isEmpty(goodsid)){
                goodsInfo = getGoodsInfoByBarcode((String) data.get("barcode"));
            }
            if(null == goodsInfo){
                nogoodsid.add(goodsid);
                noIndex.add(i);
            }else if( "0".equals(goodsInfo.getState()) && null != goodsInfo){
                disablegoodsid.add(goodsid);
                disabledIndex.add(i);
            }else{
                data.put("goodsid",goodsInfo.getId());
            }
        }
        for(int i = list.size() - 1; i >= 0; i--) {
            for(int index : disabledIndex){
                if(index == i){
                    list.remove(i);
                }
            }
        }
        if(null != disablegoodsid && disablegoodsid.size() > 0){
            msg = msg + "商品" + disablegoodsid.toString() + "禁用\n";
        }
        for(int i = list.size() - 1; i >= 0; i--) {
            for(int index : noIndex){
                if(index == i){
                    list.remove(i);
                }
            }
        }
        if(null != nogoodsid && nogoodsid.size() > 0){
            msg = msg + "商品编码" + nogoodsid.toString() + "不存在\n";
        }
        // 非批次管理商品重复check，剔除重复商品
        if(list.size() > 1) {
            Map records = new HashMap();
            String compareitem = "goodsid";
            for(Map map : list) {
                String goodsid = (String) map.get("goodsid");
                String barcode = (String) map.get("barcode");
                compareitem = StringUtils.isEmpty(goodsid) ? "barcode" : "goodsid";
                String compareid = CommonUtils.nullToEmpty(StringUtils.isEmpty(goodsid) ? barcode : goodsid);
                GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
                //非批次管理商品重复判断
                if("0".equals(goodsInfo.getIsbatch()) ){
                    if(records.get(compareid) == null) {
                        records.put(compareid, "1");
                    } else {
                        records.put(compareid, "2");
                    }
                }
            }
            for(String key : (Set<String>) records.keySet()) {
                String num = (String) records.get(key);
                if("2".equals(num)) {
                    for(int i = list.size() - 1; i >= 0; i--) {
                        Map map = list.get(i);
                        if(key.equals((String) map.get(compareitem))) {
                            list.remove(i);
                        }
                    }
                    msg = msg + "商品[" + key + "]重复\n";
                }
            }
        }
 
		Map returnMap = new HashMap();
		boolean flag = false;
		String ids = "";
		Map billMap = new HashMap();
		for(Map map :list){
			//判断excel导入的 调出仓库 调入仓库 是否存在
			String outstoragename = (String) map.get("outstorageid");
			String enterstoragename = (String) map.get("enterstorageid");
			StorageInfo outstorageInfo = getStorageInfoByName(outstoragename);
			StorageInfo enterstorageInfo = getStorageInfoByName(enterstoragename);
			if(null!=outstorageInfo && null!=enterstorageInfo){
				String outstorageid = outstorageInfo.getId();
				String enterstorageid = enterstorageInfo.getId();
				String billtype="";
				if(map.containsKey("billtype")){
					billtype=(String)map.get("billtype");
				}else{
					billtype="成本调拨";
				}
				map.put("outstorageid", outstorageid);
				map.put("enterstorageid", enterstorageid);
				if(StringUtils.isNotEmpty(outstorageid) && StringUtils.isNotEmpty(enterstorageid)){
					if(billMap.containsKey(outstorageid+":"+enterstorageid+","+billtype)){
						List datalist = (List) billMap.get(outstorageid+":"+enterstorageid+","+billtype);
						datalist.add(map);
						billMap.put(outstorageid+":"+enterstorageid+","+billtype, datalist);
					}else{
						List datalist = new ArrayList();
						datalist.add(map);
						billMap.put(outstorageid+":"+enterstorageid+","+billtype, datalist);
					}
				}
			}else{
				msg += "仓库："+outstoragename+"，"+enterstoragename+"不存在\n";
			}
		}
		Set set = billMap.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Entry) it.next();
			String storageids = (String) entry.getKey();
			List<Map> datalist = (List<Map>) entry.getValue();
			if(null!=datalist && datalist.size()>0){
				Map map = datalist.get(0);
				String outstorageid = (String) map.get("outstorageid");
				String enterstorageid = (String) map.get("enterstorageid");
				AllocateNotice allocateNotice = new AllocateNotice();
				if (isAutoCreate("t_storage_allocate_notice")) {
					// 获取自动编号
					String id = getAutoCreateSysNumbderForeign(allocateNotice, "t_storage_allocate_notice");
					allocateNotice.setId(id);
				}else{
					allocateNotice.setId("DBTZ-"+CommonUtils.getDataNumberSendsWithRand());
				}
				String billtype="";
				if(map.containsKey("billtype")){
					billtype=(String)map.get("billtype");
					if(StringUtils.isEmpty(billtype)||"成本调拨".equals(billtype)){
						allocateNotice.setBilltype("1");
					}else if("异价调拨".equals(billtype)){
						allocateNotice.setBilltype("2");
					}
				}else{
					allocateNotice.setBilltype("1");
				}
				allocateNotice.setOutstorageid(outstorageid);
				allocateNotice.setEnterstorageid(enterstorageid);
                int countNoInsertData = 0 ;
				for(Map dataMap : datalist){
					String goodsid = (String) dataMap.get("goodsid");
					AllocateNoticeDetail allocateNoticeDetail = new AllocateNoticeDetail();
					allocateNoticeDetail.setBillno(allocateNotice.getId());
                    //获取商品的批次号
                    if(dataMap.containsKey("batchno")){
                        allocateNoticeDetail.setBatchno((String) dataMap.get("batchno"));
                    }
					String auxnumstr = (String) dataMap.get("auxnum");
					String auxremainderstr = (String) dataMap.get("auxremainder");
					BigDecimal auxnum = BigDecimal.ZERO;
					BigDecimal auxremainder = BigDecimal.ZERO;
					BigDecimal unitnum = BigDecimal.ZERO;
					if(null!=auxnumstr){
						auxnum = (new BigDecimal(auxnumstr)).setScale(0,BigDecimal.ROUND_DOWN);
					}
					if(null!=auxremainderstr){
						if(decimalScale == 0){
							auxremainder = (new BigDecimal(auxremainderstr)).setScale(decimalScale,BigDecimal.ROUND_DOWN);
						}else{
							auxremainder = (new BigDecimal(auxremainderstr)).setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
						}
					}
					GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
					if(null==goodsInfo){
						String barcode = (String) dataMap.get("barcode");
						goodsInfo = getGoodsInfoByBarcode(barcode);
					}
					String remark = (String) dataMap.get("remark");
					allocateNoticeDetail.setRemark(remark);
					Map returnmap = retMainUnitByUnitAndGoodid(auxnum, goodsInfo.getId());
					unitnum = (BigDecimal) returnmap.get("mainUnitNum");
					unitnum = unitnum.add(auxremainder);
					if(null!=goodsInfo){
						allocateNoticeDetail.setGoodsid(goodsInfo.getId());
						allocateNoticeDetail.setStorageid(outstorageid);
						//根据商品是否批次管理 获取商品确定批次商品
						StorageSummaryBatch storageSummaryBatch = null;
						if("1".equals(goodsInfo.getIsbatch())){
							if(StringUtils.isNotEmpty(allocateNoticeDetail.getProduceddate())){
								storageSummaryBatch = getStorageSummaryBatchByStorageidAndProduceddate(outstorageid, goodsid, allocateNoticeDetail.getProduceddate());
							}else if(StringUtils.isNotEmpty(allocateNoticeDetail.getBatchno())){
								storageSummaryBatch = getStorageSummaryBatchByStorageidAndBatchno(outstorageid, allocateNoticeDetail.getBatchno(), goodsid);
							}
						}else{
							storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(outstorageid, goodsid);
						}
						if(null!=storageSummaryBatch){
							allocateNoticeDetail.setStorageid(outstorageid);
							allocateNoticeDetail.setSummarybatchid(storageSummaryBatch.getId());
							allocateNoticeDetail.setBatchno(storageSummaryBatch.getBatchno());
							allocateNoticeDetail.setStoragelocationid(storageSummaryBatch.getStoragelocationid());
							allocateNoticeDetail.setProduceddate(storageSummaryBatch.getProduceddate());
							allocateNoticeDetail.setDeadline(storageSummaryBatch.getDeadline());
							//主单位名称
							allocateNoticeDetail.setUnitid(goodsInfo.getMainunit());
							MeteringUnit unitInfo = getMeteringUnitById(goodsInfo.getMainunit());
							if(unitInfo != null){
								allocateNoticeDetail.setUnitname(unitInfo.getName());
							}
							allocateNoticeDetail.setUnitnum(unitnum);
							//获取辅单位
							GoodsInfo_MteringUnitInfo goodsInfo_MteringUnitInfo = getDefaultGoodsAuxMeterUnitInfo(goodsInfo.getId());
							if(null!=goodsInfo_MteringUnitInfo){
								allocateNoticeDetail.setAuxunitid(goodsInfo_MteringUnitInfo.getMeteringunitid());
								allocateNoticeDetail.setAuxunitname(goodsInfo_MteringUnitInfo.getMeteringunitName());
							}
							Map auxmap = countGoodsInfoNumber(goodsInfo.getId(), null, unitnum);
							if(null!=auxmap){
								String auxnumdetail = (String) auxmap.get("auxnumdetail");
								String auxnumstr1 = (String) auxmap.get("auxInteger");
								String auxremainderstr1 = (String) auxmap.get("auxremainder");
								BigDecimal realauxnum = BigDecimal.ZERO;
								if(StringUtils.isNotEmpty(auxnumstr1)){
									realauxnum = new BigDecimal(auxnumstr1);
								}
								BigDecimal realauxnumremainder = BigDecimal.ZERO;
								if(StringUtils.isNotEmpty(auxnumstr1)){
									realauxnumremainder = new BigDecimal(auxremainderstr1);
								}
								
								allocateNoticeDetail.setAuxnum(realauxnum);
								allocateNoticeDetail.setAuxremainder(realauxnumremainder);
								allocateNoticeDetail.setAuxnumdetail(auxnumdetail);

								TaxType taxtype = getTaxType(goodsInfo.getDefaulttaxtype());
								String taxtypestr = "";
								if(null!=taxtype){
									taxtypestr = taxtype.getId();
								}
								allocateNoticeDetail.setTaxtype(taxtypestr);

								BigDecimal taxprice=getStorageSummaryCostprice(goodsid,outstorageid);
								allocateNoticeDetail.setTaxprice(taxprice);
								allocateNoticeDetail.setTaxamount(taxprice.multiply(allocateNoticeDetail.getUnitnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
								BigDecimal notaxamount = getNotaxAmountByTaxAmount(allocateNoticeDetail.getTaxamount(), allocateNoticeDetail.getTaxtype());
								allocateNoticeDetail.setNotaxamount(notaxamount);
								if(allocateNoticeDetail.getUnitnum().compareTo(BigDecimal.ZERO) != 0){
									BigDecimal notaxprice = notaxamount.divide(allocateNoticeDetail.getUnitnum(), BigDecimal.ROUND_HALF_UP);
									allocateNoticeDetail.setNotaxprice(notaxprice);
								}
								allocateNoticeDetail.setTax(allocateNoticeDetail.getTaxamount().subtract(notaxamount));
							}
							//成本价
							allocateNoticeDetail.setCostprice(getStorageSummaryCostprice(goodsid,outstorageid));
							//如果异价调拨传了调拨价就取传过来的调拨价
							BigDecimal price=BigDecimal.ZERO;
							if(dataMap.containsKey("taxprice")&&"2".equals(allocateNotice.getBilltype())){
								price=new BigDecimal(dataMap.get("taxprice").toString());
								allocateNoticeDetail.setTaxprice(price);
								allocateNoticeDetail.setTaxamount(price.multiply(allocateNoticeDetail.getUnitnum()));
								BigDecimal notaxamount = getNotaxAmountByTaxAmount(allocateNoticeDetail.getTaxamount(), allocateNoticeDetail.getTaxtype());
								allocateNoticeDetail.setNotaxamount(notaxamount);
								if(allocateNoticeDetail.getUnitnum().compareTo(BigDecimal.ZERO) != 0){
									BigDecimal notaxprice = notaxamount.divide(allocateNoticeDetail.getUnitnum(), BigDecimal.ROUND_HALF_UP);
									allocateNoticeDetail.setNotaxprice(notaxprice);
								}
								allocateNoticeDetail.setTax(allocateNoticeDetail.getTaxamount().subtract(notaxamount));
							}
							allocateNoticeMapper.addAllocateNoticeDetail(allocateNoticeDetail);
						}else{
                            ++ countNoInsertData;
							msg += "商品:"+goodsid+"不存在对应批次\n";
						}
					}else{
                        ++ countNoInsertData;
						msg += "商品编码:"+goodsid+goodsInfo.getName()+"，在仓库中不存在或者指定生产日期或者批次不存在\n";
					}
				}
				SysUser sysUser = getSysUser();
				allocateNotice.setBusinessdate(CommonUtils.getTodayDataStr());
				allocateNotice.setStatus("2");
				allocateNotice.setAdddeptid(sysUser.getDepartmentid());
				allocateNotice.setAdddeptname(sysUser.getDepartmentname());
				allocateNotice.setAdduserid(sysUser.getUserid());
				allocateNotice.setAddusername(sysUser.getName());
                if(countNoInsertData < datalist.size()){
                    int i = allocateNoticeMapper.addAllocateNotice(allocateNotice);
                    if(i>0){
                        flag = true;
                        ids += allocateNotice.getId()+",";
                    }
                }
			}
		}
        if(StringUtils.isNotEmpty(ids)){
            returnMap.put("msg","生成调拨通知单："+ids+"\n"+ msg);
        }else{
            returnMap.put("msg","\n"+ msg);
        }
        returnMap.put("id", ids);
		returnMap.put("flag", flag);
		return returnMap;
	}
	
	@Override
	public List getAllocateNoticeByExport(PageMap pageMap)
			throws Exception {
		List<Map<String, Object>> list =  allocateNoticeMapper.getAllocateNoticeListExport(pageMap);
        for(Map<String, Object> map :list){

            String id = (String)map.get("id");

            //调出仓库
            String outstorageid = (String)map.get("outstorageid");
            StorageInfo outstorageInfo = getStorageInfoByID(outstorageid);
            if(null != outstorageInfo){
                map.put("outstoragename", outstorageInfo.getName());
            }

            //调入仓库
            String enterstorageid = (String)map.get("enterstorageid");
            StorageInfo enterstorageInfo = getStorageInfoByID(enterstorageid);
            if(null != enterstorageInfo){
                map.put("enterstoragename", enterstorageInfo.getName());
            }

            String goodsid = (String)map.get("goodsid");
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
			String billtype=(String)map.get("billtype");
			if("1".equals(billtype)){
				map.put("billtypename","成本调拨");
			}else if("2".equals(billtype)){
				map.put("billtypename","异价调拨");
			}
        }
        return list;
	}
	
	@Override
	public AllocateNoticeDetail getAllocateNoticeDetailInfo(String billno,
			String id) throws Exception {
		AllocateNoticeDetail allocateNoticeDetail = allocateNoticeMapper.getAllocaeNoticeDetail(billno, id);
		return allocateNoticeDetail;
	}
	@Override
	public Map addAllocateOut(AllocateOut allocateOut,
			List<AllocateOutDetail> detailList) throws Exception {
		String msg = "";
		boolean flag = false;
		boolean canAudit = true;
		StorageInfo storageInfo = getStorageInfoByID(allocateOut.getOutstorageid());
		for(AllocateOutDetail allocateOutDetail : detailList){
			StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(allocateOutDetail.getSummarybatchid());
			//判断商品是否存在
			GoodsInfo goodsInfo = getAllGoodsInfoByID(allocateOutDetail.getGoodsid());
			if(null==goodsInfo){
				msg += "商品:"+allocateOutDetail.getGoodsid()+",不存在";
				flag = false;
				break;
			}
			//获取要出库的商品批次 没指定批次 则获取指定仓库默认的商品批次
			if( ("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())) 
					|| StringUtils.isNotEmpty(allocateOutDetail.getSummarybatchid())){
				storageSummaryBatch = getStorageSummaryBatchById(allocateOutDetail.getSummarybatchid());
			}else{
				storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(allocateOut.getOutstorageid(), allocateOutDetail.getGoodsid());
			}
			//判断批次商品可用量是否充足
			if(null!=storageSummaryBatch && storageSummaryBatch.getUsablenum().compareTo(allocateOutDetail.getUnitnum())!=-1){
			}else{
				canAudit = false;
				if(null!=storageInfo && null!=goodsInfo){
					msg += storageInfo.getName()+"仓库中,商品："+goodsInfo.getName()+",可用量不足.";
				}
			}
		}
		if(canAudit){
			if (isAutoCreate("t_storage_allocate_out")) {
				// 获取自动编号
				String id = getAutoCreateSysNumbderForeign(allocateOut, "t_storage_allocate_out");
				allocateOut.setId(id);
			}else{
				allocateOut.setId("DBCK-"+CommonUtils.getDataNumberSendsWithRand());
			}
            allocateOut.setBusinessdate(CommonUtils.getTodayDataStr());
			for(AllocateOutDetail allocateOutDetail : detailList){
				StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(allocateOutDetail.getSummarybatchid());
				//判断商品是否存在
				GoodsInfo goodsInfo = getAllGoodsInfoByID(allocateOutDetail.getGoodsid());
				//获取要出库的商品批次 没指定批次 则获取指定仓库默认的商品批次
				if(("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch()))
						|| StringUtils.isNotEmpty(allocateOutDetail.getSummarybatchid())){
					storageSummaryBatch = getStorageSummaryBatchById(allocateOutDetail.getSummarybatchid());
				}else{
					storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(allocateOut.getOutstorageid(), allocateOutDetail.getGoodsid());
				}
				
				//生成调拨待发量
				Map ret = updateStorageSummaryAllotwaitnum(storageSummaryBatch.getId(), allocateOutDetail.getUnitnum());
				boolean returnFlag = (Boolean) ret.get("flag");
                if(returnFlag == false){
					throw new RuntimeException("仓库更新调拨待发量失败，回滚数据");
				}
				//计算辅单位数量
				Map auxmap = countGoodsInfoNumber(allocateOutDetail.getGoodsid(), allocateOutDetail.getAuxunitid(), allocateOutDetail.getUnitnum());
				if(auxmap.containsKey("auxnum")){
					allocateOutDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
				}
				allocateOutDetail.setBillno(allocateOut.getId());
				allocateOutDetail.setStorageid(allocateOut.getOutstorageid());
				//关联商品批次库存
				allocateOutDetail.setSummarybatchid(storageSummaryBatch.getId());
				allocateOutMapper.addAllocateOutDetail(allocateOutDetail);
			}
			SysUser sysUser = getSysUser();
			allocateOut.setAdddeptid(sysUser.getDepartmentid());
			allocateOut.setAdddeptname(sysUser.getDepartmentname());
			allocateOut.setAdduserid(sysUser.getUserid());
			allocateOut.setAddusername(sysUser.getName());
			int i = allocateOutMapper.addAllocateOut(allocateOut);
			flag = i>0;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("id", allocateOut.getId());
		return map;
	}
	@Override
	public boolean deleteAllocateOut(String id) throws Exception {
		AllocateOut allocateOut = allocateOutMapper.getAllocateOutByID(id);
		boolean flag = false;
		if(null==allocateOut){
			return true;
		}else if("1".equals(allocateOut.getStatus())||"2".equals(allocateOut.getStatus())){
			List<AllocateOutDetail> list = allocateOutMapper.getAllocateOutDetailList(id);
			boolean rollFlag = true;
			for(AllocateOutDetail allocateOutDetail : list){
				//回滚调拨待发量
				boolean rflag = rollBackStorageSummaryAllotwaitnum(allocateOutDetail.getSummarybatchid(), allocateOutDetail.getUnitnum());
				if(rflag==false){
					throw new RuntimeException("回滚调拨待发量失败。");
				}
			}
			allocateOutMapper.deleteAllocateOutDetail(id);
			int i = allocateOutMapper.deleteAllocateOut(id);
			flag = i>0;
			if(flag && "1".equals(allocateOut.getSourcetype())){
				allocateNoticeMapper.updateAllocateNoticeRefer("0", allocateOut.getSourceid());
			}
		}
		return flag;
	}
	@Override
	public PageData showAllocateOutList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_storage_allocate_out", null);
		pageMap.setDataSql(dataSql);
		List<AllocateOut> list = allocateOutMapper.showAllocateOutList(pageMap);
		for(AllocateOut allocateOut : list){
			StorageInfo outstorageInfo = getStorageInfoByID(allocateOut.getOutstorageid());
			if(null!=outstorageInfo){
				allocateOut.setOutstoragename(outstorageInfo.getName());
			}
			StorageInfo enterstorageInfo = getStorageInfoByID(allocateOut.getEnterstorageid());
			if(null!=enterstorageInfo){
				allocateOut.setEnterstoragename(enterstorageInfo.getName());
			}
		}
		PageData pageData = new PageData(allocateOutMapper.showAllocateOutCount(pageMap),list,pageMap);
		return pageData;
	}
	@Override
	public Map getAllocateOutInfo(String id) throws Exception {
		AllocateOut allocateOut  = allocateOutMapper.getAllocateOutByID(id);
		List<AllocateOutDetail> list = allocateOutMapper.getAllocateOutDetailList(id);
		for(AllocateOutDetail allocateOutDetail : list){
			GoodsInfo goodsInfo = getGoodsInfoByID(allocateOutDetail.getGoodsid());
			if(goodsInfo!=null){
				goodsInfo.setItemno(getItemnoByGoodsAndStorage(allocateOutDetail.getGoodsid(),allocateOutDetail.getStorageid()));
			}
			allocateOutDetail.setGoodsInfo(goodsInfo);

            if(null!=goodsInfo){
                allocateOutDetail.setBoxprice(allocateOutDetail.getTaxprice().multiply(goodsInfo.getBoxnum()).setScale(2,BigDecimal.ROUND_HALF_UP));
            }
			StorageInfo storageInfo = getStorageInfoByID(allocateOutDetail.getStorageid());
			if(null!=storageInfo){
				allocateOutDetail.setStoragename(storageInfo.getName());
			}
			StorageLocation storageLocation = getStorageLocation(allocateOutDetail.getStoragelocationid());
			if(null!=storageLocation){
				allocateOutDetail.setStoragelocationname(storageLocation.getName());
			}
			StorageLocation enterstorageLocation = getStorageLocation(allocateOutDetail.getEnterstoragelocationid());
			if(null!=enterstorageLocation){
				allocateOutDetail.setEnterstoragelocationname(enterstorageLocation.getName());
			}
			TaxType taxType = getTaxType(allocateOutDetail.getTaxtype());
			if(null!=taxType){
				allocateOutDetail.setTaxtypename(taxType.getName());
			}
		}
		Map map = new HashMap();
		map.put("allocateOut", allocateOut);
		map.put("detailList", list);
		return map;
	}
	@Override
	public Map editAllocateOut(AllocateOut allocateOut,
			List<AllocateOutDetail> list) throws Exception {
		Map returnMap = new HashMap();
		boolean flag = true;
		String msg = "";
		AllocateOut oldAllocateOut = allocateOutMapper.getAllocateOutByID(allocateOut.getId());
		if(null==oldAllocateOut || "3".equals(oldAllocateOut.getStatus()) || "4".equals(oldAllocateOut.getStatus())){
			returnMap.put("flag", false);
			returnMap.put("msg", "单据已审核，请刷新后操作。");
			return returnMap;
		}
		//回滚调拨待发量
		List<AllocateOutDetail> oldlist = allocateOutMapper.getAllocateOutDetailList(allocateOut.getId());
		for(AllocateOutDetail allocateOutDetail : list){
			StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(allocateOutDetail.getSummarybatchid());
			//判断商品是否存在
			GoodsInfo goodsInfo = getAllGoodsInfoByID(allocateOutDetail.getGoodsid());
			//获取要出库的商品批次 没指定批次 则获取指定仓库默认的商品批次
			if("1".equals(goodsInfo.getIsbatch()) || StringUtils.isNotEmpty(allocateOutDetail.getSummarybatchid())){
				storageSummaryBatch = getStorageSummaryBatchById(allocateOutDetail.getSummarybatchid());
			}else{
				storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(allocateOut.getOutstorageid(), allocateOutDetail.getGoodsid());
			}
			if(null!=allocateOutDetail.getId()){
				AllocateOutDetail oldallocateOutDetail = allocateOutMapper.getAllocateOutDetailInfo(allocateOut.getId(), allocateOutDetail.getId()+"");
				if(null!=storageSummaryBatch && storageSummaryBatch.getExistingnum().compareTo(allocateOutDetail.getUnitnum())!=-1){
					//修改后的数量大于修改前的数量 判断可用量是否足够
					if(null!=oldallocateOutDetail && allocateOutDetail.getUnitnum().compareTo(oldallocateOutDetail.getUnitnum())==1){
						//批次现存量中的可用量是否大于 修改后数量-修改前数量
						if(storageSummaryBatch.getUsablenum().compareTo(allocateOutDetail.getUnitnum().subtract(oldallocateOutDetail.getUnitnum()))==-1){
							StorageInfo storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
							msg += "商品:"+goodsInfo.getName()+",在仓库:"+storageInfo.getName()+"中,可用量不足</br>";
							flag = false;
						}
					}
				}else{
					StorageInfo storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
					msg += "商品:"+goodsInfo.getName()+",在仓库:"+storageInfo.getName()+"中,可用量不足</br>";
					flag = false;
				}
			}else{
				if(storageSummaryBatch.getUsablenum().compareTo(allocateOutDetail.getUnitnum())==-1){
					StorageInfo storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
					msg += "商品:"+goodsInfo.getName()+",在仓库:"+storageInfo.getName()+"中,可用量不足</br>";
					flag = false;
				}
			}
		}
		if(flag){
			for(AllocateOutDetail allocateOutDetail : oldlist){
				//回滚调拨待发量
				boolean rflag = rollBackStorageSummaryAllotwaitnum(allocateOutDetail.getSummarybatchid(), allocateOutDetail.getUnitnum());
				if(rflag==false){
					throw new RuntimeException("回滚调拨待发量失败。");
				}
			}
			allocateOutMapper.deleteAllocateOutDetail(allocateOut.getId());
			for(AllocateOutDetail allocateOutDetail : list){
				StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(allocateOutDetail.getSummarybatchid());
				//判断商品是否存在
				GoodsInfo goodsInfo = getAllGoodsInfoByID(allocateOutDetail.getGoodsid());
				//获取要出库的商品批次 没指定批次 则获取指定仓库默认的商品批次
				if("1".equals(goodsInfo.getIsbatch()) || StringUtils.isNotEmpty(allocateOutDetail.getSummarybatchid())){
					storageSummaryBatch = getStorageSummaryBatchById(allocateOutDetail.getSummarybatchid());
				}else{
					storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(allocateOut.getOutstorageid(), allocateOutDetail.getGoodsid());
				}
				//生成调拨待发量
				Map ret = updateStorageSummaryAllotwaitnum(storageSummaryBatch.getId(), allocateOutDetail.getUnitnum());
				boolean returnFlag = (Boolean) ret.get("flag");
                if(returnFlag == false){
					throw new RuntimeException("仓库更新调拨待发量失败，回滚数据");
				}
				//计算辅单位数量
				Map auxmap = countGoodsInfoNumber(allocateOutDetail.getGoodsid(), allocateOutDetail.getAuxunitid(), allocateOutDetail.getUnitnum());
				if(auxmap.containsKey("auxnum")){
					allocateOutDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
				}
				allocateOutDetail.setBillno(allocateOut.getId());
				allocateOutDetail.setStorageid(allocateOut.getOutstorageid());
				allocateOutDetail.setSummarybatchid(storageSummaryBatch.getId());
				allocateOutMapper.addAllocateOutDetail(allocateOutDetail);
			}
			
			SysUser sysUser = getSysUser();
			allocateOut.setModifyusername(sysUser.getUserid());
			allocateOut.setModifyusername(sysUser.getName());
			int i = allocateOutMapper.editAllocateOut(allocateOut);
			flag= i>0;
		}
		returnMap.put("flag", flag);
		returnMap.put("msg", msg);
		return returnMap;
	}
	@Override
	public Map addAllocateOutByRefer(String sourceid) throws Exception {
		AllocateNotice allocateNotice = allocateNoticeMapper.getAllocateNoticeInfo(sourceid);
		List<AllocateNoticeDetail> detailList = allocateNoticeMapper.getAllocateNoticeDetailListByBillno(sourceid);
		//生成下游单据
		AllocateOut allocateOut = new AllocateOut();
		allocateOut.setOutstorageid(allocateNotice.getOutstorageid());
		allocateOut.setEnterstorageid(allocateNotice.getEnterstorageid());
		allocateOut.setBusinessdate(allocateNotice.getBusinessdate());
		allocateOut.setStatus("2");
		allocateOut.setRemark(allocateNotice.getRemark());
		allocateOut.setSourcetype("1");
		allocateOut.setSourceid(allocateNotice.getId());
		
		allocateOut.setField01(allocateNotice.getField01());
		allocateOut.setField02(allocateNotice.getField02());
		allocateOut.setField03(allocateNotice.getField03());
		allocateOut.setField04(allocateNotice.getField04());
		allocateOut.setField05(allocateNotice.getField05());
		allocateOut.setField06(allocateNotice.getField06());
		allocateOut.setField07(allocateNotice.getField07());
		allocateOut.setField08(allocateNotice.getField08());
		
		List allocateOutDetailList = new ArrayList();
		
		for(AllocateNoticeDetail allocateNoticeDetail : detailList){
			AllocateOutDetail allocateOutDetail = new AllocateOutDetail();
			allocateOutDetail.setSourceid(sourceid);
			allocateOutDetail.setSourcedetailid(allocateNoticeDetail.getId()+"");
			
			allocateOutDetail.setSummarybatchid(allocateNoticeDetail.getSummarybatchid());
			allocateOutDetail.setStorageid(allocateNoticeDetail.getStorageid());
			allocateOutDetail.setStoragelocationid(allocateNoticeDetail.getStoragelocationid());
			allocateOutDetail.setGoodsid(allocateNoticeDetail.getGoodsid());
			allocateOutDetail.setBatchno(allocateNoticeDetail.getBatchno());
			allocateOutDetail.setProduceddate(allocateNoticeDetail.getProduceddate());
			allocateOutDetail.setDeadline(allocateNoticeDetail.getDeadline());
			
			allocateOutDetail.setUnitid(allocateNoticeDetail.getUnitid());
			allocateOutDetail.setUnitname(allocateNoticeDetail.getUnitname());
			allocateOutDetail.setUnitnum(allocateNoticeDetail.getUnitnum());
			allocateOutDetail.setAuxunitid(allocateNoticeDetail.getAuxunitid());
			allocateOutDetail.setAuxnumdetail(allocateNoticeDetail.getAuxnumdetail());
			allocateOutDetail.setAuxunitname(allocateNoticeDetail.getAuxunitname());
			allocateOutDetail.setAuxnum(allocateNoticeDetail.getAuxnum());
			allocateOutDetail.setTotalbox(allocateNoticeDetail.getTotalbox());
			
			allocateOutDetail.setTaxprice(allocateNoticeDetail.getTaxprice());
			allocateOutDetail.setTaxamount(allocateNoticeDetail.getTaxamount());
			allocateOutDetail.setNotaxprice(allocateNoticeDetail.getNotaxprice());
			allocateOutDetail.setNotaxamount(allocateNoticeDetail.getNotaxamount());
			allocateOutDetail.setTax(allocateNoticeDetail.getTax());
			allocateOutDetail.setTaxtype(allocateNoticeDetail.getTaxtype());
			allocateOutDetail.setRemark(allocateNoticeDetail.getRemark());
			
			allocateOutDetailList.add(allocateOutDetail);
		}
		Map returnmap = addAllocateOut(allocateOut, allocateOutDetailList);
		boolean flag = (Boolean) returnmap.get("flag");
		if(flag){
			allocateNoticeMapper.updateAllocateNoticeRefer("1", sourceid);
		}
		return returnmap;
	}
	@Override
	public Map auditAllocateOut(String id) throws Exception {
		AllocateOut allocateOut = allocateOutMapper.getAllocateOutByID(id);
		boolean flag = false;
		String msg = "";
		Map map = new HashMap();
		if(null==allocateOut){
		}else if("2".equals(allocateOut.getStatus()) || "6".equals(allocateOut.getStatus())){
			List<AllocateOutDetail> list = allocateOutMapper.getAllocateOutDetailList(id);
			boolean allotFlag = true;
			StorageInfo storageInfo = getStorageInfoByID(allocateOut.getOutstorageid());
            StorageInfo enterStorageInfo = getStorageInfoByID(allocateOut.getEnterstorageid());
            if(0==list.size()){
				map.put("flag", false);
				map.put("msg", "调拨单明细为空，不允许审核！");
				return map;
			}
			//验证现存量量是否足够
			for(AllocateOutDetail allocateOutDetail : list){
                //判断商品是否存在
                GoodsInfo goodsInfo = getAllGoodsInfoByID(allocateOutDetail.getGoodsid());
                if("1".equals(enterStorageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch()) && StringUtils.isEmpty(allocateOutDetail.getEnterproduceddate())){
                    msg += "商品编码:"+goodsInfo.getId()+",商品名称:"+goodsInfo.getName()+",入库生产日期未输入</br>";
                    allotFlag = false;
                }
				StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(allocateOutDetail.getSummarybatchid());

				//获取要出库的商品批次 没指定批次 则获取指定仓库默认的商品批次
				if(("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())) 
						|| StringUtils.isNotEmpty(allocateOutDetail.getSummarybatchid())){
					storageSummaryBatch = getStorageSummaryBatchById(allocateOutDetail.getSummarybatchid());
				}else{
					storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(allocateOut.getOutstorageid(), allocateOutDetail.getGoodsid());
				}
				if(null!=storageSummaryBatch && (storageSummaryBatch.getExistingnum().compareTo(allocateOutDetail.getUnitnum())!=-1)){
				}else{
					msg += "商品编码:"+goodsInfo.getId()+",商品名称:"+goodsInfo.getName()+",在"+storageInfo.getName()+"仓库中,现存量不足</br>";
					allotFlag = false;
				}
			}
			if(allotFlag){
				for(AllocateOutDetail allocateOutDetail : list){
					//调拨出库 更新调出仓库调拨待发量  仓库调拨入库量
					boolean sendFlag = sendStorageSummaryAllotwaitnum(allocateOutDetail.getSummarybatchid(), allocateOutDetail.getUnitnum(),allocateOutDetail.getUnitnum(), allocateOut.getEnterstorageid(),allocateOutDetail.getEnterstoragelocationid(),allocateOutDetail.getEnterproduceddate(),"allocateOut",id,"调拨出库");
					if(sendFlag==false){
						throw new RuntimeException("更新调拨出库失败。");
					}else{
                        //调拨后 更新出库仓库与入库仓库的成本价
                        updateStorageCostpriceByAllot(allocateOut.getId(),allocateOutDetail.getId()+"",allocateOut.getOutstorageid(),allocateOut.getEnterstorageid(),allocateOutDetail.getGoodsid(),allocateOutDetail.getTaxprice(),allocateOutDetail.getCostprice(),allocateOutDetail.getUnitnum());
                    }
				}
				SysUser sysUser = getSysUser();
				String billdate=getAuditBusinessdate(allocateOut.getBusinessdate());
				int i = allocateOutMapper.auditAllocateOut(id, sysUser.getUserid(), sysUser.getName(),billdate);
				flag = i>0;
				if(flag){
					//回写调拨通知单状态为关闭
					if(StringUtils.isNotEmpty(allocateOut.getSourceid())){
						AllocateNotice allocateNotice = allocateNoticeMapper.getAllocateNoticeInfo(allocateOut.getSourceid());
						allocateNotice.setStatus("4");
						allocateNoticeMapper.editAllocateNotice(allocateNotice);
					}
				}
			}
			
		}
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}
	@Override
	public boolean oppauditAllocateOut(String id) throws Exception {
		AllocateOut allocateOut = allocateOutMapper.getAllocateOutByID(id);
		boolean flag = false;
		if(null==allocateOut){
			return false;
		}else if("3".equals(allocateOut.getStatus())){
			//获取下游单据调拨入库单信息
			AllocateEnter allocateEnter = allocateEnterMapper.getAllocateEnterBySourceid(id);
			//判断下游单据是否删除 
			boolean delFlag = true;
			if(null!=allocateOut){
				if("1".equals(allocateEnter.getStatus()) || "2".equals(allocateEnter.getStatus())){
					allocateEnterMapper.deleteAllocateEnterDetail(allocateEnter.getId());
					int i = allocateEnterMapper.deleteAllocateEnter(allocateEnter.getId());
					delFlag = i>0;
				}
			}
			if(delFlag){
				List<AllocateOutDetail> list = allocateOutMapper.getAllocateOutDetailList(id);
				boolean allotFlag = true;
				for(AllocateOutDetail allocateOutDetail : list){
					allotFlag = rollBackSendStorageSummaryAllotoutnum(allocateOutDetail.getSummarybatchid(), allocateOutDetail.getUnitnum(), allocateOut.getEnterstorageid(), "allocateOut", id, "调拨出库单反审，回滚调拨出库操作");
					if(allotFlag==false){
						throw new RuntimeException("回滚调拨出库操作失败");
					}
				}
				if(allotFlag){
					SysUser sysUser = getSysUser();
					int i = allocateOutMapper.oppauditAllocateOut(id, sysUser.getUserid(), sysUser.getName());
					flag = i>0;
				}
			}
		}
		return flag;
	}
	@Override
	public AllocateOutDetail getAllocateOutDetailInfo(String id, String detailid)
			throws Exception {
		AllocateOutDetail allocateOutDetail = allocateOutMapper.getAllocateOutDetailInfo(id, detailid);
		return allocateOutDetail;
	}
	@Override
	public PageData showAllocateOutDetailListQuery(PageMap pageMap)
			throws Exception {
		String dataSql = getDataAccessRule("t_storage_allocate_out", "t");
		pageMap.setDataSql(dataSql);
		List<Map<String,Object>> list = allocateOutMapper.showAllocateOutDetailListQuery(pageMap);
		for(Map<String,Object> map : list){
			String id = (String) map.get("id");
			String businessdate = (String) map.get("businessdate");
			if(id.equals(businessdate)){
				map.put("businessdate", businessdate+" 小计");
				map.put("id", "");
				map.put("taxprice", "");
				map.put("unitname", "");
				map.put("auxunitname", "");
				BigDecimal auxnum = (BigDecimal) map.get("auxnum");
				BigDecimal auxremainder = (BigDecimal) map.get("auxremainder");
				String unitname = (String) map.get("unitname");
				String auxunitname = (String) map.get("auxunitname");
				String auxnumdetail = "";
				if(null!=auxnum && auxnum.compareTo(BigDecimal.ZERO)==1){
					auxnumdetail = auxnum.setScale(0,BigDecimal.ROUND_DOWN).toString();
				}else{
					auxnumdetail = "0,";
				}
				if(null!=auxremainder &&  auxremainder.compareTo(BigDecimal.ZERO)==1){
					auxnumdetail += auxremainder.setScale(BillGoodsNumDecimalLenUtils.decimalLen,BigDecimal.ROUND_HALF_UP).toString();
				}
				map.put("auxunitnumdetail", CommonUtils.strDigitNumDeal(auxnumdetail));
			}else{
				String goodsid = (String) map.get("goodsid");
				GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
				if(null!=goodsInfo){
					map.put("goodsname", goodsInfo.getName());
					map.put("brandname", goodsInfo.getBrandName());
					map.put("model", goodsInfo.getModel());
					map.put("barcode", goodsInfo.getBarcode());
				}
				
				String outstorageid = (String) map.get("outstorageid");
				StorageInfo outstorageInfo = getStorageInfoByID(outstorageid);
				if(null!=outstorageInfo){
					map.put("outstoragename", outstorageInfo.getName());
				}
				String enterstorageid = (String) map.get("enterstorageid");
				StorageInfo enterstorageInfo = getStorageInfoByID(enterstorageid);
				if(null!=enterstorageInfo){
					map.put("enterstoragename", enterstorageInfo.getName());
				}
				
				BigDecimal unitnum = (BigDecimal) map.get("unitnum");
				Map auxunitmap  = countGoodsInfoNumber(goodsid,(String) map.get("auxunitid"), unitnum);
				map.put("auxunitnumdetail", CommonUtils.strDigitNumDeal((String)auxunitmap.get("auxnumdetail")));
				BigDecimal costprice=(BigDecimal)map.get("costprice");
				BigDecimal taxprice=(BigDecimal)map.get("taxprice");
				BigDecimal diffamount=(taxprice.subtract(costprice)).multiply(unitnum);
				map.put("diffamount",diffamount);
			}
		}
		PageData pageData = new PageData(allocateOutMapper.showAllocateOutDetailCountQuery(pageMap),list,pageMap);
		return pageData;
	}

    @Override
    public Map addAllocateOutByImport(List<Map> list) throws Exception {
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
        String msg = "";
        List<String> noGoodsid = new ArrayList<String>();
        List<Integer> noIndex = new ArrayList();
        // 非批次管理商品重复check，剔除重复商品
        if(list.size() > 1) {
            Map records = new HashMap();
            String compareitem = "goodsid";
            for(Map map : list) {
                String goodsid = (String) map.get("goodsid");
                String barcode = (String) map.get("barcode");
                compareitem = StringUtils.isEmpty(goodsid) ? "barcode" : "goodsid";
                String compareid = CommonUtils.nullToEmpty(StringUtils.isEmpty(goodsid) ? barcode : goodsid);
                GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
                if(null == goodsInfo){
                    noGoodsid.add(goodsid);
                    noIndex.add(list.indexOf(map));
                }
                //非批次管理商品重复判断
                if( null != goodsInfo && "0".equals(goodsInfo.getIsbatch()) ){
                    if(records.get(compareid) == null) {
                        records.put(compareid, "1");
                    } else {
                        records.put(compareid, "2");
                    }
                }
            }
            for(String key : (Set<String>) records.keySet()) {
                String num = (String) records.get(key);
                if("2".equals(num)) {
                    for(int i = list.size() - 1; i >= 0; i--) {
                        Map map = list.get(i);
                        if(key.equals((String) map.get(compareitem))) {
                            list.remove(i);
                        }
                    }
                    msg = msg + "商品[" + key + "]重复\n";
                }
            }
        }
        for(int i = list.size() - 1; i >= 0; i--) {
            for(int index : noIndex){
                if(index == i){
                    list.remove(i);
                }
            }
        }
        if(noGoodsid.size() > 0){
            msg += "商品"+noGoodsid.toString()+"不存在";
        }

        Map returnMap = new HashMap();
        boolean flag = false;
        String ids = "";
        Map billMap = new HashMap();
        for(Map map :list){
            //判断excel导入的 调出仓库 调入仓库 是否存在
            String outstoragename = (String) map.get("outstorageid");
            String enterstoragename = (String) map.get("enterstorageid");
            StorageInfo outstorageInfo = getStorageInfoByName(outstoragename);
            StorageInfo enterstorageInfo = getStorageInfoByName(enterstoragename);
            if(null!=outstorageInfo && null!=enterstorageInfo){
                String outstorageid = outstorageInfo.getId();
                String enterstorageid = enterstorageInfo.getId();
				String billtype="";
				if(map.containsKey("billtype")){
					billtype=(String)map.get("billtype");
				}else{
					billtype="成本调拨";
				}

                map.put("outstorageid", outstorageid);
                map.put("enterstorageid", enterstorageid);
                if(StringUtils.isNotEmpty(outstorageid) && StringUtils.isNotEmpty(enterstorageid)){
                    if(billMap.containsKey(outstorageid+":"+enterstorageid+","+billtype)){
                        List datalist = (List) billMap.get(outstorageid+":"+enterstorageid+","+billtype);
                        datalist.add(map);
                        billMap.put(outstorageid+":"+enterstorageid+","+billtype, datalist);
                    }else{
                        List datalist = new ArrayList();
                        datalist.add(map);
                        billMap.put(outstorageid+":"+enterstorageid+","+billtype, datalist);
                    }
                }
            }else{
                msg += "仓库："+outstoragename+"，"+enterstoragename+"不存在\n";
            }
        }
        Set set = billMap.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Entry) it.next();
            String storageids = (String) entry.getKey();
            List<Map> datalist = (List<Map>) entry.getValue();
            if(null!=datalist && datalist.size()>0){
                Map map = datalist.get(0);
                String outstorageid = (String) map.get("outstorageid");
                String enterstorageid = (String) map.get("enterstorageid");
                AllocateOut allocateOut = new AllocateOut();
                if (isAutoCreate("t_storage_allocate_out")) {
                    // 获取自动编号
                    String id = getAutoCreateSysNumbderForeign(allocateOut, "t_storage_allocate_out");
                    allocateOut.setId(id);
                }else{
                    allocateOut.setId("DBTZ-"+CommonUtils.getDataNumberSendsWithRand());
                }
				String billtype="";
				if(map.containsKey("billtype")){
					billtype=(String)map.get("billtype");
					if(StringUtils.isEmpty(billtype)||"成本调拨".equals(billtype)){
						allocateOut.setBilltype("1");
					}else if("异价调拨".equals(billtype)){
						allocateOut.setBilltype("2");
					}
				}else{
					allocateOut.setBilltype("1");
				}
                allocateOut.setOutstorageid(outstorageid);
                allocateOut.setEnterstorageid(enterstorageid);
                
                StorageInfo outStorageInfo = getStorageInfoByID(outstorageid);
                for(Map dataMap : datalist){
                    String goodsid = (String) dataMap.get("goodsid");
                    AllocateOutDetail allocateOutDetail = new AllocateOutDetail();
                    allocateOutDetail.setBillno(allocateOut.getId());
                    String auxnumstr = (String) dataMap.get("auxnum");
                    String auxremainderstr = (String) dataMap.get("auxremainder");
                    BigDecimal auxnum = BigDecimal.ZERO;
                    BigDecimal auxremainder = BigDecimal.ZERO;
                    BigDecimal unitnum = BigDecimal.ZERO;

                    if(null != auxnumstr){
                        auxnum = (new BigDecimal(auxnumstr)).setScale(0,BigDecimal.ROUND_DOWN);
                    }
                    if(null!=auxremainderstr){
						if(decimalScale == 0){
							auxremainder = (new BigDecimal(auxremainderstr)).setScale(decimalScale,BigDecimal.ROUND_DOWN);
						}else{
							auxremainder = (new BigDecimal(auxremainderstr)).setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
						}
                    }
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
                    if(null==goodsInfo){
                        String barcode = (String) dataMap.get("barcode");
                        goodsInfo = getGoodsInfoByBarcode(barcode);
                    }
                    String remark = (String) dataMap.get("remark");
                    allocateOutDetail.setRemark(remark);
                    Map returnmap = retMainUnitByUnitAndGoodid(auxnum, goodsInfo.getId());
                    unitnum = (BigDecimal) returnmap.get("mainUnitNum");
                    unitnum = unitnum.add(auxremainder);
                    if(null!=goodsInfo){
                        allocateOutDetail.setGoodsid(goodsInfo.getId());
                        allocateOutDetail.setStorageid(outstorageid);
                        //如果存在对应的批次号或生产日期
                        if(dataMap.containsKey("produceddate")){
                            allocateOutDetail.setProduceddate((String) dataMap.get("produceddate"));
                        }else if(dataMap.containsKey("batchno")){
                            allocateOutDetail.setBatchno((String) dataMap.get("batchno"));
                        }
                        //根据商品是否批次管理 获取商品确定批次商品
						StorageSummaryBatch storageSummaryBatch = null;
						if("1".equals(outStorageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
							if(StringUtils.isNotEmpty(allocateOutDetail.getProduceddate())){
								storageSummaryBatch = getStorageSummaryBatchByStorageidAndProduceddate(outstorageid, goodsid, allocateOutDetail.getProduceddate());
							}else if(StringUtils.isNotEmpty(allocateOutDetail.getBatchno())){
								storageSummaryBatch = getStorageSummaryBatchByStorageidAndBatchno(outstorageid, allocateOutDetail.getBatchno(), goodsid);
							}
						}else{
							storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(outstorageid, goodsid);
						}
                        if(null!=storageSummaryBatch){
                        	allocateOutDetail.setStorageid(outstorageid);
                            allocateOutDetail.setSummarybatchid(storageSummaryBatch.getId());
                            allocateOutDetail.setBatchno(storageSummaryBatch.getBatchno());
                            allocateOutDetail.setStoragelocationid(storageSummaryBatch.getStoragelocationid());
                            allocateOutDetail.setProduceddate(storageSummaryBatch.getProduceddate());
                            allocateOutDetail.setDeadline(storageSummaryBatch.getDeadline());
                            //主单位名称
                            allocateOutDetail.setUnitid(goodsInfo.getMainunit());
                            MeteringUnit unitInfo = getMeteringUnitById(goodsInfo.getMainunit());
                            if(unitInfo != null){
                                allocateOutDetail.setUnitname(unitInfo.getName());
                            }
                            allocateOutDetail.setUnitnum(unitnum);
                            //获取辅单位
                            GoodsInfo_MteringUnitInfo goodsInfo_MteringUnitInfo = getDefaultGoodsAuxMeterUnitInfo(goodsInfo.getId());
                            if(null!=goodsInfo_MteringUnitInfo){
                                allocateOutDetail.setAuxunitid(goodsInfo_MteringUnitInfo.getMeteringunitid());
                                allocateOutDetail.setAuxunitname(goodsInfo_MteringUnitInfo.getMeteringunitName());
                            }
                            Map auxmap = countGoodsInfoNumber(goodsInfo.getId(), null, unitnum);
                            if(null!=auxmap){
                                String auxnumdetail = (String) auxmap.get("auxnumdetail");
                                String auxnumstr1 = (String) auxmap.get("auxInteger");
                                String auxremainderstr1 = (String) auxmap.get("auxremainder");
                                BigDecimal realauxnum = BigDecimal.ZERO;
                                if(StringUtils.isNotEmpty(auxnumstr1)){
                                    realauxnum = new BigDecimal(auxnumstr1);
                                }
                                BigDecimal realauxnumremainder = BigDecimal.ZERO;
                                if(StringUtils.isNotEmpty(auxnumstr1)){
                                    realauxnumremainder = new BigDecimal(auxremainderstr1);
                                }

                                allocateOutDetail.setAuxnum(realauxnum);
                                allocateOutDetail.setAuxremainder(realauxnumremainder);
                                allocateOutDetail.setAuxnumdetail(auxnumdetail);

                                TaxType taxtype = getTaxType(goodsInfo.getDefaulttaxtype());
                                String taxtypestr = "";
                                if(null!=taxtype){
                                    taxtypestr = taxtype.getId();
                                }

								//调拨价默认取成本价
								BigDecimal taxprice=getStorageSummaryCostprice(goodsid, outstorageid);
                                allocateOutDetail.setTaxtype(taxtypestr);
								allocateOutDetail.setTaxprice(taxprice);
								allocateOutDetail.setTaxamount(taxprice.multiply(allocateOutDetail.getUnitnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
								BigDecimal notaxamount = getNotaxAmountByTaxAmount(allocateOutDetail.getTaxamount(), allocateOutDetail.getTaxtype());
								allocateOutDetail.setNotaxamount(notaxamount);
								if(allocateOutDetail.getUnitnum().compareTo(BigDecimal.ZERO) != 0){
									BigDecimal notaxprice = notaxamount.divide(allocateOutDetail.getUnitnum(), BigDecimal.ROUND_HALF_UP);
									allocateOutDetail.setNotaxprice(notaxprice);
								}
								allocateOutDetail.setTax(allocateOutDetail.getTaxamount().subtract(notaxamount));
                            }

							//成本价
							allocateOutDetail.setCostprice(getStorageSummaryCostprice(goodsid,outstorageid));
							//如果异价调拨传了调拨价就取传过来的调拨价
							BigDecimal price=BigDecimal.ZERO;
							if(dataMap.containsKey("taxprice")&&"2".equals(allocateOut.getBilltype())){
								price=new BigDecimal(dataMap.get("taxprice").toString());
								allocateOutDetail.setTaxprice(price);
								allocateOutDetail.setTaxamount(price.multiply(allocateOutDetail.getUnitnum()));
								BigDecimal notaxamount = getNotaxAmountByTaxAmount(allocateOutDetail.getTaxamount(), allocateOutDetail.getTaxtype());
								allocateOutDetail.setNotaxamount(notaxamount);
								if(allocateOutDetail.getUnitnum().compareTo(BigDecimal.ZERO) != 0){
									BigDecimal notaxprice = notaxamount.divide(allocateOutDetail.getUnitnum(), BigDecimal.ROUND_HALF_UP);
									allocateOutDetail.setNotaxprice(notaxprice);
								}
								allocateOutDetail.setTax(allocateOutDetail.getTaxamount().subtract(notaxamount));
							}

                            //生成调拨待发量
            				Map ret = updateStorageSummaryAllotwaitnum(allocateOutDetail.getSummarybatchid(), allocateOutDetail.getUnitnum());
                            boolean returnFlag = (Boolean) ret.get("flag");
                            String errMsg = (String) ret.get("msg");
                            if(returnFlag == false && errMsg == null){
            					throw new RuntimeException("仓库更新调拨待发量失败，回滚数据");
            				} else if(returnFlag == false && errMsg != null) {
                                msg = msg + errMsg;
                            } else {
                                allocateOutMapper.addAllocateOutDetail(allocateOutDetail);
                            }
                        }else{
                            msg += "商品编码:"+goodsid+goodsInfo.getName()+"，在仓库中不存在或者指定生产日期或者批次不存在\n";
                        }
                    }else{
                        msg += "商品编码:"+goodsid+"不存在\n";
                    }
                }
                SysUser sysUser = getSysUser();
                allocateOut.setBusinessdate(CommonUtils.getTodayDataStr());
                allocateOut.setStatus("2");
                allocateOut.setSourcetype("0");//来源类型为无
                allocateOut.setAdddeptid(sysUser.getDepartmentid());
                allocateOut.setAdddeptname(sysUser.getDepartmentname());
                allocateOut.setAdduserid(sysUser.getUserid());
                allocateOut.setAddusername(sysUser.getName());
                int i = allocateOutMapper.addAllocateOut(allocateOut);
                if(i>0){
                    flag = true;
                    ids += "," + allocateOut.getId();
                }
            }
        }
        if(StringUtils.isNotEmpty(ids)) {
            ids = ids.substring(1);
            returnMap.put("id", ids);
            returnMap.put("msg","生成调拨单："+ids+"\n"+ (StringUtils.isNotEmpty(msg) ? "部分记录未能导入，原因如下：\r" + msg : msg));
        }else{
            returnMap.put("msg","导入失败！"+msg);
        }

        returnMap.put("flag", flag);
        return returnMap;
    }

    @Override
    public List<Map<String, Object>> getAllocateOutByExport(PageMap pageMap) throws Exception {
        List<Map<String, Object>> allocateOutList =  allocateOutMapper.getAllocateOutListExport(pageMap);
        for(Map<String, Object> map :allocateOutList){

            String id = (String)map.get("id");

            //调出仓库
            String outstorageid = (String)map.get("outstorageid");
            StorageInfo outstorageInfo = getStorageInfoByID(outstorageid);
            if(null != outstorageInfo){
                map.put("outstoragename", outstorageInfo.getName());
            }

            //调入仓库
            String enterstorageid = (String)map.get("enterstorageid");
            StorageInfo enterstorageInfo = getStorageInfoByID(enterstorageid);
            if(null != enterstorageInfo){
                map.put("enterstoragename", enterstorageInfo.getName());
            }

//            AllocateOut allocateOutInfo = allocateOutMapper.getAllocateOutByID(id);
//            if(null != allocateOutInfo){
//                map.put("adjuststoragename", allocateOutInfo.getName());
//            }

            String goodsid = (String)map.get("goodsid");
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

			String billtype=(String)map.get("billtype");
			if("1".equals(billtype)){
				map.put("billtypename","成本调拨");
			}else if("2".equals(billtype)){
				map.put("billtypename","异价调拨");
			}
        }
        return allocateOutList;
    }

    @Override
	public Map addAllocateEnter(AllocateEnter allocateEnter,
			List<AllocateEnterDetail> list) throws Exception {
		if (isAutoCreate("t_storage_allocate_enter")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(allocateEnter, "t_storage_allocate_enter");
			allocateEnter.setId(id);
		}else{
			allocateEnter.setId("DBRK-"+CommonUtils.getDataNumberSendsWithRand());
		} 
		for(AllocateEnterDetail allocateEnterDetail : list){
			//计算辅单位数量
			Map auxmap = countGoodsInfoNumber(allocateEnterDetail.getGoodsid(), allocateEnterDetail.getAuxunitid(), allocateEnterDetail.getUnitnum());
			if(auxmap.containsKey("auxnum")){
				allocateEnterDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
			}
			allocateEnterDetail.setBillno(allocateEnter.getId());
			allocateEnterMapper.addAllocateEnterDetail(allocateEnterDetail);
		}
		SysUser sysUser = getSysUser();
		allocateEnter.setAdddeptid(sysUser.getDepartmentid());
		allocateEnter.setAdddeptname(sysUser.getDepartmentname());
		allocateEnter.setAdduserid(sysUser.getUserid());
		allocateEnter.setAddusername(sysUser.getName());
		int i = allocateEnterMapper.addAllocateEnter(allocateEnter);
		Map map = new HashMap();
		map.put("flag", i>0);
		map.put("id", allocateEnter.getId());
		return map;
	}
	@Override
	public PageData showAllocateEnterList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_storage_allocate_enter", null);
		pageMap.setDataSql(dataSql);
		List<AllocateEnter> list = allocateEnterMapper.showAllocateEnterList(pageMap);
		for(AllocateEnter allocateEnter : list){
			StorageInfo outstorageInfo = getStorageInfoByID(allocateEnter.getOutstorageid());
			if(null!=outstorageInfo){
				allocateEnter.setOutstoragename(outstorageInfo.getName());
			}
			StorageInfo enterstorageInfo = getStorageInfoByID(allocateEnter.getEnterstorageid());
			if(null!=enterstorageInfo){
				allocateEnter.setEnterstoragename(enterstorageInfo.getName());
			}
		}
		PageData pageData = new PageData(allocateEnterMapper.showAllocateEnterCount(pageMap),list,pageMap);
		return pageData;
	}
	@Override
	public Map getAllocateEnterInfo(String id) throws Exception {
		AllocateEnter allocateEnter = allocateEnterMapper.getAllocateEnter(id);
		List<AllocateEnterDetail> list = allocateEnterMapper.getAllocateEnterDetailListByBillno(id);
		for(AllocateEnterDetail allocateEnterDetail : list){
			GoodsInfo goodsInfo = getGoodsInfoByID(allocateEnterDetail.getGoodsid());
			allocateEnterDetail.setGoodsInfo(goodsInfo);
			StorageInfo storageInfo = getStorageInfoByID(allocateEnterDetail.getStorageid());
			if(null!=storageInfo){
				allocateEnterDetail.setStoragename(storageInfo.getName());
			}
			StorageLocation storageLocation = getStorageLocation(allocateEnterDetail.getStoragelocationid());
			if(null!=storageLocation){
				allocateEnterDetail.setStoragelocationname(storageLocation.getName());
			}
			TaxType taxType = getTaxType(allocateEnterDetail.getTaxtype());
			if(null!=taxType){
				allocateEnterDetail.setTaxtypename(taxType.getName());
			}
		}
		Map map = new HashMap();
		map.put("allocateEnter", allocateEnter);
		map.put("detailList", list);
		return map;
	}
	@Override
	public boolean editAllocateEnter(AllocateEnter allocateEnter,
			List<AllocateEnterDetail> detailList) throws Exception {
		AllocateEnter oldAllocateEnter = allocateEnterMapper.getAllocateEnter(allocateEnter.getId());
		if("1".equals(allocateEnter.getStatus()) || "2".equals(allocateEnter.getStatus()) || "6".equals(allocateEnter.getStatus())){
			allocateEnterMapper.deleteAllocateEnterDetail(allocateEnter.getId());
			for(AllocateEnterDetail allocateEnterDetail : detailList){
				//计算辅单位数量
				Map auxmap = countGoodsInfoNumber(allocateEnterDetail.getGoodsid(), allocateEnterDetail.getAuxunitid(), allocateEnterDetail.getUnitnum());
				if(auxmap.containsKey("auxnum")){
					allocateEnterDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
				}
				allocateEnterDetail.setBillno(allocateEnter.getId());
				allocateEnterMapper.addAllocateEnterDetail(allocateEnterDetail);
			}
			SysUser sysUser = getSysUser();
			allocateEnter.setModifyuserid(sysUser.getUserid());
			allocateEnter.setModifyusername(sysUser.getName());
			int i = allocateEnterMapper.editAllocateEnter(allocateEnter);
			return i>0;
		}else{
			return false;
		}
	}
	@Override
	public boolean deleteAllocateEnter(String id) throws Exception {
		AllocateEnter allocateEnter = allocateEnterMapper.getAllocateEnter(id);
		boolean flag = false;
		if(null==allocateEnter){
			return true;
		}else if("1".equals(allocateEnter.getStatus()) || "2".equals(allocateEnter.getStatus())){
			allocateEnterMapper.deleteAllocateEnterDetail(id);
			int i = allocateEnterMapper.deleteAllocateEnter(id);
			flag = i>0;
			if(flag){
				//更新上游单据调拨出库单参照状态
				allocateOutMapper.updateAllocateOutRefer("0", allocateEnter.getSourceid());
			}
		}
		return flag;
	}
	@Override
	public Map auditAllocateEnter(String id) throws Exception {
		AllocateEnter allocateEnter = allocateEnterMapper.getAllocateEnter(id);
		boolean flag = false;
		String msg = "";
		if(null==allocateEnter){
			flag = false;
		}else if("2".equals(allocateEnter.getStatus()) || "6".equals(allocateEnter.getStatus())){
			List<AllocateEnterDetail> list = allocateEnterMapper.getAllocateEnterDetailListByBillno(id);
			boolean auditFlag  = true;
			for(AllocateEnterDetail allocateEnterDetail : list){
				GoodsInfo goodsInfo = getAllGoodsInfoByID(allocateEnterDetail.getGoodsid());
				if("1".equals(goodsInfo.getIsbatch())){
				}else if("1".equals(goodsInfo.getIsstoragelocation())){
					if(null==allocateEnterDetail.getStoragelocationid() || "".equals(allocateEnterDetail.getStoragelocationid())){
						msg += "商品："+goodsInfo.getName()+",属于库位管理，必须指定所属库位</br>";
						auditFlag = false;
					}
				}
			}
			if(auditFlag){
				for(AllocateEnterDetail allocateEnterDetail : list){
					StorageSummaryBatch storageSummaryBatch = new StorageSummaryBatch();
					storageSummaryBatch.setId(CommonUtils.getDataNumberSendsWithRand());
					storageSummaryBatch.setGoodsid(allocateEnterDetail.getGoodsid());
					storageSummaryBatch.setStorageid(allocateEnterDetail.getStorageid());
					storageSummaryBatch.setStoragelocationid(allocateEnterDetail.getStoragelocationid());
					storageSummaryBatch.setBatchno(allocateEnterDetail.getBatchno());
					storageSummaryBatch.setProduceddate(allocateEnterDetail.getProduceddate());
					storageSummaryBatch.setDeadline(allocateEnterDetail.getDeadline());
					
					storageSummaryBatch.setExistingnum(allocateEnterDetail.getUnitnum());
					storageSummaryBatch.setUsablenum(allocateEnterDetail.getUnitnum());
					storageSummaryBatch.setIntinum(new BigDecimal(0));
					
					storageSummaryBatch.setUnitid(allocateEnterDetail.getUnitid());
					storageSummaryBatch.setUnitname(allocateEnterDetail.getUnitname());
					storageSummaryBatch.setAuxunitid(allocateEnterDetail.getAuxunitid());
					storageSummaryBatch.setAuxunitname(allocateEnterDetail.getAuxunitname());
					storageSummaryBatch.setPrice(allocateEnterDetail.getTaxprice());
					storageSummaryBatch.setAmount(allocateEnterDetail.getTaxamount());
					
					storageSummaryBatch.setEnterdate(CommonUtils.dataToStr(new Date(), "yyyy-MM-dd"));
					
					enterStorageSummaryAllotenternum(storageSummaryBatch, "alllocateEnter", id, "调拨入库审核通过，更新库存");
				}
				SysUser sysUser = getSysUser();
				int i = allocateEnterMapper.auditAllocateEnter(id, sysUser.getUserid(), sysUser.getName());
				flag = i>0;
				//回写调拨出库单与调拨通知单
				if(flag){
					writeBackAllocateOut(allocateEnter, list);
				}
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}
	/**
	 * 调拨入库单审核通过后回写
	 * @param allocateEnter
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public boolean writeBackAllocateOut(AllocateEnter allocateEnter,List<AllocateEnterDetail> list) throws Exception{
		boolean flag = false;
		AllocateOut allocateOut = allocateOutMapper.getAllocateOutByID(allocateEnter.getSourceid());
		//调拨出库单来源调拨通知单时，回写调拨通知单信息 并且关闭调拨通知单
		if("1".equals(allocateOut.getSourcetype())){
			AllocateNotice allocateNotice = allocateNoticeMapper.getAllocateNoticeInfo(allocateOut.getSourceid());
			List<AllocateOutDetail> outList = allocateOutMapper.getAllocateOutDetailList(allocateEnter.getSourceid());
			//回写调拨通知单明细
			for(AllocateOutDetail allocateOutDetail : outList){
				AllocateNoticeDetail allocateNoticeDetail = allocateNoticeMapper.getAllocaeNoticeDetail(allocateNotice.getId(), allocateOutDetail.getSourcedetailid());
				allocateNoticeDetail.setAllotoutnum(allocateOutDetail.getUnitnum());
				allocateNoticeDetail.setAllotoutamount(allocateOutDetail.getTaxamount());
				allocateNoticeDetail.setAllotenternum(allocateOutDetail.getUnitnum());
				allocateNoticeDetail.setAllotenteramount(allocateOutDetail.getTaxamount());
				
				allocateNoticeDetail.setNoallotenternum(allocateNoticeDetail.getUnitnum().subtract(allocateOutDetail.getUnitnum()));
				allocateNoticeDetail.setNoallotenteramount(allocateNoticeDetail.getTaxamount().subtract(allocateOutDetail.getTaxamount()));
				allocateNoticeDetail.setNoallotoutamount(allocateNoticeDetail.getTaxamount().subtract(allocateOutDetail.getTaxamount()));
				allocateNoticeDetail.setNoallotoutnum(allocateNoticeDetail.getUnitnum().subtract(allocateOutDetail.getUnitnum()));
				
				Map auxOutMap = countGoodsInfoNumber(allocateNoticeDetail.getGoodsid(), allocateNoticeDetail.getAuxunitid(), allocateNoticeDetail.getAllotoutnum());
				allocateNoticeDetail.setAuxallotoutnum((BigDecimal) auxOutMap.get("auxnum"));
				
				Map auxEnterMap = countGoodsInfoNumber(allocateNoticeDetail.getGoodsid(), allocateNoticeDetail.getAuxunitid(), allocateNoticeDetail.getAllotenternum());
				allocateNoticeDetail.setAuxallotenternum((BigDecimal) auxEnterMap.get("auxnum"));
				
				Map auxnoOutMap = countGoodsInfoNumber(allocateNoticeDetail.getGoodsid(), allocateNoticeDetail.getAuxunitid(), allocateNoticeDetail.getNoallotoutnum());
				allocateNoticeDetail.setAuxnoallotoutnum((BigDecimal) auxnoOutMap.get("auxnum"));
				
				Map auxnoEnterMap = countGoodsInfoNumber(allocateNoticeDetail.getGoodsid(), allocateNoticeDetail.getAuxunitid(), allocateNoticeDetail.getNoallotenternum());
				allocateNoticeDetail.setAuxnoallotenternum((BigDecimal) auxEnterMap.get("auxnum"));
				
				allocateNoticeMapper.updateAllocateNoticeDetail(allocateNoticeDetail);
			}
			//关闭调拨通知单
			int i= allocateNoticeMapper.closeAllocateNotice(allocateOut.getSourceid());
			//关闭调拨入库单
			int j = allocateOutMapper.closeAllocateOut(allocateEnter.getSourceid());
			flag = i>0&&j>0;
		}else if("0".equals(allocateOut.getSourcetype())){
			//关闭调拨入库单
			int j = allocateOutMapper.closeAllocateOut(allocateEnter.getSourceid());
			flag = j>0;
		}
		return flag;
	}
	@Override
	public Map addAllocateEnterByRefer(String sourceid) throws Exception {
		boolean flag = false;
		String downid = "";
		AllocateOut allocateOut = allocateOutMapper.getAllocateOutByID(sourceid);
		List<AllocateOutDetail> list = allocateOutMapper.getAllocateOutDetailList(sourceid);
		if(null!=allocateOut){
			List allocateEnterDetailList = new ArrayList();
			//生成下游单据
			AllocateEnter allocateEnter = new AllocateEnter();
			allocateEnter.setOutstorageid(allocateOut.getOutstorageid());
			allocateEnter.setEnterstorageid(allocateOut.getEnterstorageid());
			allocateEnter.setBusinessdate(allocateOut.getBusinessdate());
			allocateEnter.setStatus("2");
			allocateEnter.setRemark(allocateOut.getRemark());
			allocateEnter.setSourcetype("1");
			allocateEnter.setSourceid(allocateOut.getId());
			
			allocateEnter.setField01(allocateOut.getField01());
			allocateEnter.setField02(allocateOut.getField02());
			allocateEnter.setField03(allocateOut.getField03());
			allocateEnter.setField04(allocateOut.getField04());
			allocateEnter.setField05(allocateOut.getField05());
			allocateEnter.setField06(allocateOut.getField06());
			allocateEnter.setField07(allocateOut.getField07());
			allocateEnter.setField08(allocateOut.getField08());
			
			for(AllocateOutDetail allocateOutDetail : list){
				AllocateEnterDetail allocateEnterDetail = new AllocateEnterDetail();
				allocateEnterDetail.setSourceid(sourceid);
				allocateEnterDetail.setSourcedetailid(allocateOutDetail.getId()+"");
				
				allocateEnterDetail.setSummarybatchid(allocateOutDetail.getSummarybatchid());
				allocateEnterDetail.setStorageid(allocateOut.getEnterstorageid());
				allocateEnterDetail.setGoodsid(allocateOutDetail.getGoodsid());
				allocateEnterDetail.setBatchno(allocateOutDetail.getBatchno());
				allocateEnterDetail.setProduceddate(allocateOutDetail.getProduceddate());
				allocateEnterDetail.setDeadline(allocateOutDetail.getDeadline());
				
				allocateEnterDetail.setUnitid(allocateOutDetail.getUnitid());
				allocateEnterDetail.setUnitname(allocateOutDetail.getUnitname());
				allocateEnterDetail.setUnitnum(allocateOutDetail.getUnitnum());
				allocateEnterDetail.setAuxunitid(allocateOutDetail.getAuxunitid());
				allocateEnterDetail.setAuxnumdetail(allocateOutDetail.getAuxnumdetail());
				allocateEnterDetail.setAuxunitname(allocateOutDetail.getAuxunitname());
				allocateEnterDetail.setAuxnum(allocateOutDetail.getAuxnum());
				allocateEnterDetail.setTotalbox(allocateOutDetail.getTotalbox());
				
				allocateEnterDetail.setTaxprice(allocateOutDetail.getTaxprice());
				allocateEnterDetail.setTaxamount(allocateOutDetail.getTaxamount());
				allocateEnterDetail.setNotaxprice(allocateOutDetail.getNotaxprice());
				allocateEnterDetail.setNotaxamount(allocateOutDetail.getNotaxamount());
				allocateEnterDetail.setTax(allocateOutDetail.getTax());
				allocateEnterDetail.setTaxtype(allocateOutDetail.getTaxtype());
				allocateEnterDetail.setRemark(allocateOutDetail.getRemark());
				
				allocateEnterDetailList.add(allocateEnterDetail);
			}
			
			Map returnmap = addAllocateEnter(allocateEnter, allocateEnterDetailList);
			boolean addFlag = (Boolean) returnmap.get("flag");
			if(addFlag){
				flag = true;
				downid = (String) returnmap.get("id");
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", downid);
		return map;
	}
	@Override
	public Map submitAllocateOutPageProcess(String id) throws Exception {
		Map map = new HashMap();
		return map;
	}
	@Override
	public Map submitAllocateEnterPageProcess(String id) throws Exception {
		Map map = new HashMap();
		return map;
	}
	@Override
	public boolean submitAllocateNoticePageProcess(String id) throws Exception {
		return false;
	}
	
	@Override
	public List showAllocateOutListBy(Map map) throws Exception{
		String dataSql = getDataAccessRule("t_storage_allocate_out", null);
		map.put("dataSql", dataSql);
		List<AllocateOut> list= allocateOutMapper.showAllocateOutListBy(map);
		
		boolean showdetail=false;
		if(null!=map.get("showdetail") && StringUtils.isNotEmpty(map.get("showdetail").toString()) &&"1".equals(map.get("showdetail").toString()) ){
			showdetail=true;
		}
		for(AllocateOut item : list){
			StorageInfo outstorageInfo = getStorageInfoByID(item.getOutstorageid());
			if(null!=outstorageInfo){
				item.setOutstoragename(outstorageInfo.getName());
			}
			StorageInfo enterstorageInfo = getStorageInfoByID(item.getEnterstorageid());
			if(null!=enterstorageInfo){
				item.setEnterstoragename(enterstorageInfo.getName());
			}
			if(showdetail){
				List<AllocateOutDetail> aodlist = allocateOutMapper.getAllocateOutDetailList(item.getId());
				for(AllocateOutDetail allocateOutDetail : aodlist){
					GoodsInfo goodsInfo = getGoodsInfoByID(allocateOutDetail.getGoodsid());
					allocateOutDetail.setGoodsInfo(goodsInfo);
					StorageInfo storageInfo = getStorageInfoByID(allocateOutDetail.getStorageid());
					if(null!=storageInfo){
						allocateOutDetail.setStoragename(storageInfo.getName());
					}
					StorageLocation storageLocation = getStorageLocation(allocateOutDetail.getStoragelocationid());
					if(null!=storageLocation){
						allocateOutDetail.setStoragelocationname(storageLocation.getName());
					}
					TaxType taxType = getTaxType(allocateOutDetail.getTaxtype());
					if(null!=taxType){
						allocateOutDetail.setTaxtypename(taxType.getName());
					}
				}
				item.setAllocateOutDetailList(aodlist);				
			}
		}
		return list;
	}
	@Override
	public boolean updateAllocateOutPrinttimes(AllocateOut allocateOut) throws Exception{
		return allocateOutMapper.updateAllocateOutPrinttimes(allocateOut)>0;
	}

	@Override
	public void updateAllocateOutPrinttimes(List<AllocateOut> list) throws Exception{
		if(null!=list){
			for(AllocateOut item : list){
				allocateOutMapper.updateAllocateOutPrinttimes(item);
			}
		}
	}
	@Override
	public List showAllocateOutJournalList(PageMap pageMap) throws Exception{
		String dataSql = getDataAccessRule("t_storage_allocate_out", "t");
		pageMap.setDataSql(dataSql);
		List<AllocateOutJournal> list = allocateOutMapper.showAllocateOutJournalList(pageMap);
		for(AllocateOutJournal item : list){
			String id = item.getId();
			String businessdate = item.getBusinessdate();
			if(id.equals(businessdate)){
				item.setBusinessdate("");
				item.setBusinessdate( businessdate+" 小计");
				item.setId("");
				item.setTaxprice(null);
				item.setUnitname("");
				item.setAuxunitname("");
				BigDecimal auxnum = item.getAuxnum();
				BigDecimal auxremainder = item.getAuxremainder();
				String auxnumdetail = "";
				if(null!=auxnum && auxnum.compareTo(BigDecimal.ZERO)==1){
					auxnumdetail = auxnum.setScale(0,BigDecimal.ROUND_DOWN).toString();
				}else{
					auxnumdetail = "0,";
				}
				if(null!=auxremainder &&  auxremainder.compareTo(BigDecimal.ZERO)==1){
					auxnumdetail += auxremainder.setScale(0,BigDecimal.ROUND_DOWN).toString();
				}
				item.setAuxnumdetail( auxnumdetail);
			}else{
				String goodsid = item.getGoodsid();
				GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
				if(null!=goodsInfo){
					item.setGoodsInfo(goodsInfo);
					item.setGoodsname(goodsInfo.getName());
				}
				
				String outstorageid = item.getOutstorageid();
				StorageInfo outstorageInfo = getStorageInfoByID(outstorageid);
				if(null!=outstorageInfo){
					item.setOutstoragename( outstorageInfo.getName());
				}
				String enterstorageid = item.getEnterstorageid();
				StorageInfo enterstorageInfo = getStorageInfoByID(enterstorageid);
				if(null!=enterstorageInfo){
					item.setEnterstoragename(enterstorageInfo.getName());
				}
				
				BigDecimal unitnum = item.getUnitnum();
				Map auxunitmap  = countGoodsInfoNumber(goodsid,item.getAuxunitid(), unitnum);
				item.setAuxnumdetail( (String)auxunitmap.get("auxnumdetail"));
			}
		}
		return list;
	}
	@Override
	public AllocateOut getAllocateOutPureInfo(String id) throws Exception{
		return allocateOutMapper.getAllocateOutByID(id);
	}

    /**
     * 根据手机调拨申请单编号 获取是否上传
     *
     * @param billid
     * @return
     * @throws Exception
     */
    @Override
    public String hasPhoneBillByAllocateNotice(String billid) throws Exception {
        int i = allocateNoticeMapper.hasPhoneBillByAllocateNotice(billid);
        if(i>0){
            return allocateNoticeMapper.getAllocateNoticeBillIDByPhoneBillid(billid);
        }
        return null;
    }
	/**
	 * 调拨单审核出库
	 * @param id
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Nov 08, 2017
	 */
	@Override
	public Map auditAllocateStorageOut(String id) throws Exception{
		AllocateOut allocateOut  = allocateOutMapper.getAllocateOutByID(id);
		Map resMap=new HashMap();
		if("1".equals(allocateOut.getOutstatus())){
			resMap.put("flag",false);
			return resMap;
		}
		Map map = new HashMap();
		boolean flag = false;
		String msg = "";
		if(null==allocateOut){
		}else if("2".equals(allocateOut.getStatus()) || "6".equals(allocateOut.getStatus())){
			List<AllocateOutDetail> list = allocateOutMapper.getAllocateOutDetailList(id);
			boolean allotFlag = true;
			StorageInfo storageInfo = getStorageInfoByID(allocateOut.getOutstorageid());
			StorageInfo enterStorageInfo = getStorageInfoByID(allocateOut.getEnterstorageid());
			//验证现存量量是否足够
			if(0==list.size()){
				map.put("flag", false);
				map.put("msg", "调拨单明细为空，审核失败！");
				return map;
			}
			for(AllocateOutDetail allocateOutDetail : list){
				//判断商品是否存在
				GoodsInfo goodsInfo = getAllGoodsInfoByID(allocateOutDetail.getGoodsid());
				if("1".equals(enterStorageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch()) && StringUtils.isEmpty(allocateOutDetail.getEnterproduceddate())){
					msg += "商品编码:"+goodsInfo.getId()+",商品名称:"+goodsInfo.getName()+",入库生产日期未输入</br>";
					allotFlag = false;
				}
				StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(allocateOutDetail.getSummarybatchid());

				//获取要出库的商品批次 没指定批次 则获取指定仓库默认的商品批次
				if(("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch()))
						|| StringUtils.isNotEmpty(allocateOutDetail.getSummarybatchid())){
					storageSummaryBatch = getStorageSummaryBatchById(allocateOutDetail.getSummarybatchid());
				}else{
					storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(allocateOut.getOutstorageid(), allocateOutDetail.getGoodsid());
				}
				if(null!=storageSummaryBatch && (storageSummaryBatch.getExistingnum().compareTo(allocateOutDetail.getUnitnum())!=-1)){
				}else{
					msg += "商品编码:"+goodsInfo.getId()+",商品名称:"+goodsInfo.getName()+",在"+storageInfo.getName()+"仓库中,现存量不足</br>";
					allotFlag = false;
				}
			}
			if(allotFlag){
				for(AllocateOutDetail allocateOutDetail : list){
					//调拨出库 更新调出仓库调拨待发量  仓库调拨入库量
					boolean sendFlag = sendStorageOutSummaryAllotwaitnum(allocateOutDetail.getSummarybatchid(), allocateOutDetail.getUnitnum(),allocateOutDetail.getUnitnum(),allocateOut.getEnterstorageid(),"allocateOut",id,"调拨出库");
					if(sendFlag==false){
						throw new RuntimeException("更新调拨出库失败。");
					}else{
						//调拨后 更新出库仓库成本价
						updateStorageOutCostpriceByAllot(allocateOut.getId(),allocateOutDetail.getId()+"",allocateOut.getOutstorageid(),allocateOut.getEnterstorageid(),allocateOutDetail.getGoodsid(),allocateOutDetail.getTaxprice(),allocateOutDetail.getCostprice(),allocateOutDetail.getUnitnum());
					}
				}
				//是否自动验收退货通知单
				SysUser sysUser=getSysUser();
				String billdate=getAuditBusinessdate(allocateOut.getBusinessdate());
				int i=allocateOutMapper.auditAllocateStorageOut(allocateOut.getId(), sysUser.getUserid(), sysUser.getName(), billdate);
				flag = i>0;
			}

		}

		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 调拨单审核入库
	 * @param id
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Nov 08, 2017
	 */
	@Override
	public Map auditAllocatStorageEnter(String id) throws Exception{
		AllocateOut allocateOut  = allocateOutMapper.getAllocateOutByID(id);
		Map resMap=new HashMap();
		if("1".equals(allocateOut.getEnterstatus())){
			resMap.put("flag",false);
			return resMap;
		}

		boolean flag = false;
		String msg = "";
		if(null==allocateOut){
		}else if("7".equals(allocateOut.getStatus()) || "6".equals(allocateOut.getStatus())){
			List<AllocateOutDetail> list = allocateOutMapper.getAllocateOutDetailList(id);
			boolean allotFlag = true;
			StorageInfo storageInfo = getStorageInfoByID(allocateOut.getOutstorageid());
			StorageInfo enterStorageInfo = getStorageInfoByID(allocateOut.getEnterstorageid());
			//验证现存量量是否足够
			for(AllocateOutDetail allocateOutDetail : list){
				//判断商品是否存在
				GoodsInfo goodsInfo = getAllGoodsInfoByID(allocateOutDetail.getGoodsid());
				if("1".equals(enterStorageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch()) && StringUtils.isEmpty(allocateOutDetail.getEnterproduceddate())){
					msg += "商品编码:"+goodsInfo.getId()+",商品名称:"+goodsInfo.getName()+",入库生产日期未输入</br>";
					allotFlag = false;
				}
//				StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(allocateOutDetail.getSummarybatchid());
//
//				//获取要出库的商品批次 没指定批次 则获取指定仓库默认的商品批次
//				if(("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch()))
//						|| StringUtils.isNotEmpty(allocateOutDetail.getSummarybatchid())){
//					storageSummaryBatch = getStorageSummaryBatchById(allocateOutDetail.getSummarybatchid());
//				}else{
//					storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(allocateOut.getOutstorageid(), allocateOutDetail.getGoodsid());
//				}
//				if(null!=storageSummaryBatch && (storageSummaryBatch.getExistingnum().compareTo(allocateOutDetail.getUnitnum())!=-1)){
//				}else{
//					msg += "商品编码:"+goodsInfo.getId()+",商品名称:"+goodsInfo.getName()+",在"+storageInfo.getName()+"仓库中,现存量不足</br>";
//					allotFlag = false;
//				}
			}
			if(allotFlag){
				for(AllocateOutDetail allocateOutDetail : list){
					//调拨入库 更新调入仓库调拨待发量  仓库调拨入库量
					boolean sendFlag = sendStorageEnterSummaryAllotwaitnum(allocateOutDetail.getSummarybatchid(), allocateOutDetail.getUnitnum(), allocateOutDetail.getUnitnum(), allocateOut.getEnterstorageid(), allocateOutDetail.getEnterstoragelocationid(), allocateOutDetail.getEnterproduceddate(), "allocateOut", id, "调拨入库");
					if(sendFlag==false){
						throw new RuntimeException("更新调拨入库失败。");
					}else{
						//调拨后 更新出库仓库与入库仓库的成本价
						updateStorageEnterCostpriceByAllot(allocateOut.getId(), allocateOutDetail.getId() + "", allocateOut.getEnterstorageid(), allocateOutDetail.getGoodsid(), allocateOutDetail.getTaxprice(), allocateOutDetail.getCostprice(), allocateOutDetail.getUnitnum());
					}
				}
				int t=allocateOutMapper.auditAllocateStorageEnter(allocateOut.getId());
				flag = t>0;
				if(flag&&"1".equals(allocateOut.getOutstatus())){
					//回写调拨通知单状态为关闭
					if(StringUtils.isNotEmpty(allocateOut.getSourceid())){
						AllocateNotice allocateNotice = allocateNoticeMapper.getAllocateNoticeInfo(allocateOut.getSourceid());
						allocateNotice.setStatus("4");
						allocateNoticeMapper.editAllocateNotice(allocateNotice);
					}
				}
			}

		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}
	/**
	 * 调拨单反审出库
	 * @param id
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Nov 08, 2017
	 */
	@Override
	public Map oppauditAllocateStorageOut(String id) throws Exception{
		AllocateOut allocateOut  = allocateOutMapper.getAllocateOutByID(id);
		Map resMap=new HashMap();
		if("4".equals(allocateOut.getStatus())){
			resMap.put("flag",false);
			return resMap;
		}
		boolean flag = false;
		String msg = "";
		if(null==allocateOut){
		}else if("7".equals(allocateOut.getStatus()) || "6".equals(allocateOut.getStatus())){
			List<AllocateOutDetail> list = allocateOutMapper.getAllocateOutDetailList(id);
			boolean allotFlag = true;
			StorageInfo storageInfo = getStorageInfoByID(allocateOut.getOutstorageid());
			StorageInfo enterStorageInfo = getStorageInfoByID(allocateOut.getEnterstorageid());
//			//验证现存量量是否足够
//			for(AllocateOutDetail allocateOutDetail : list){
//				//判断商品是否存在
//				GoodsInfo goodsInfo = getAllGoodsInfoByID(allocateOutDetail.getGoodsid());
//				if("1".equals(enterStorageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch()) && StringUtils.isEmpty(allocateOutDetail.getEnterproduceddate())){
//					msg += "商品编码:"+goodsInfo.getId()+",商品名称:"+goodsInfo.getName()+",入库生产日期未输入</br>";
//					allotFlag = false;
//				}
//				StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(allocateOutDetail.getSummarybatchid());
//
//				//获取要出库的商品批次 没指定批次 则获取指定仓库默认的商品批次
//				if(("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch()))
//						|| StringUtils.isNotEmpty(allocateOutDetail.getSummarybatchid())){
//					storageSummaryBatch = getStorageSummaryBatchById(allocateOutDetail.getSummarybatchid());
//				}else{
//					storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(allocateOut.getOutstorageid(), allocateOutDetail.getGoodsid());
//				}
//				if(null!=storageSummaryBatch && (storageSummaryBatch.getExistingnum().compareTo(allocateOutDetail.getUnitnum())!=-1)){
//				}else{
//					msg += "商品编码:"+goodsInfo.getId()+",商品名称:"+goodsInfo.getName()+",在"+storageInfo.getName()+"仓库中,现存量不足</br>";
//					allotFlag = false;
//				}
//			}
			if(allotFlag){
				for(AllocateOutDetail allocateOutDetail : list){
					//调拨出库 更新调出仓库调拨待发量  仓库调拨入库量
					boolean sendFlag = sendOppauditStorageOutSummaryAllotwaitnum(allocateOutDetail.getSummarybatchid(), allocateOutDetail.getUnitnum(), allocateOutDetail.getUnitnum().negate(), allocateOut.getEnterstorageid(), "allocateOut", id, "调拨出库反审");
					if(sendFlag==false){
						throw new RuntimeException("反审出库更新调拨出库失败。");
					}else{
						//调拨后 更新出库仓库成本价
						updateOppauditStorageOutCostpriceByAllot(allocateOut.getId(), allocateOutDetail.getId() + "", allocateOut.getOutstorageid(), allocateOut.getEnterstorageid(), allocateOutDetail.getGoodsid(), allocateOutDetail.getTaxprice(), allocateOutDetail.getCostprice(), allocateOutDetail.getUnitnum().negate());
					}
				}
				int i = allocateOutMapper.oppauditAllocateStorageOut(allocateOut.getId());
				flag = i>0;
			}

		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 获取商品库存成本价
	 * @param goodsid
	 * @param storageid
	 * @return java.math.BigDecimal
	 * @throws
	 * @author luoqiang
	 * @date Nov 14, 2017
	 */
	@Override
	public BigDecimal getStorageSummaryCostprice(String goodsid, String storageid) throws Exception{
		GoodsInfo goodsInfo=getGoodsInfoByID(goodsid);
		return getGoodsCostprice(storageid,goodsInfo);
	}

	/**
	 * 获取调拨差额报表数据
	 * @param pageMap
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author luoqiang
	 * @date Dec 01, 2017
	 */
	@Override
	public PageData getAllocateDiffAmountData(PageMap pageMap) throws Exception{
		Map condition=pageMap.getCondition();
		if(!condition.containsKey("groupcols")){
			condition.put("groupcols","goodsid,storageid,deptid");
		}
		String groupcols=(String)condition.get("groupcols");
		List<Map> list=allocateOutMapper.getAllocateDiffAmountList(pageMap);
		for(Map map:list){
			String goodsid=(String)map.get("goodsid");
			String storageid=(String)map.get("storageid");
			String deptid=(String)map.get("deptid");
			BigDecimal taxamount=(BigDecimal)map.get("taxamount");
			BigDecimal costamount=(BigDecimal)map.get("costamount");

			GoodsInfo goodsInfo=getGoodsInfoByID(goodsid);
			if(goodsInfo!=null){
				map.put("goodsname",goodsInfo.getName());
			}
			DepartMent departMent=getDepartMentById(deptid);
			if(departMent!=null){
				map.put("deptname",departMent.getName());
			}
			StorageInfo storageInfo=getStorageInfoByID(storageid);
			if(storageInfo!=null){
				map.put("storagename",storageInfo.getName());
			}
			map.put("diffamount",taxamount.subtract(costamount));
		}

		int count=allocateOutMapper.getAllocateDiffAmountCount(pageMap);
		Map footer=allocateOutMapper.getSumAllocateDiffAmount(pageMap);
		if(groupcols.indexOf("goodsid")>-1){
			footer.put("goodsid","合计");
		}else if(groupcols.indexOf("storageid")>-1){
			footer.put("storageid","合计");
		}else if(groupcols.indexOf("deptid")>-1){
			footer.put("deptid","合计");
		}

		PageData pageData=new PageData(count,list,pageMap);
		List footlist=new ArrayList();
		footlist.add(footer);
		pageData.setFooter(footlist);
		return pageData;
	}

	/**
	 * 获取调拨单待发商品数据
	 * @param goodsid
	 * @param storageid
	 * @param summarybatchid
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Jan 26, 2018
	 */
	@Override
	public List showAllocateOutGoodsWaitListData(String goodsid,String storageid,String summarybatchid) throws Exception{
		List<Map> list = allocateOutMapper.showGoodsAllocateOutWaitListData(goodsid,storageid,summarybatchid);
		if(list.size() != 0){
			Map totalMap = new HashMap();
			totalMap.put("businessdate", "合计");
			BigDecimal totalnum = BigDecimal.ZERO;
			BigDecimal totaltaxamount = BigDecimal.ZERO;
			for(Map map : list){
				BigDecimal unitnum = (BigDecimal) map.get("unitnum");
				BigDecimal taxamount = (BigDecimal) map.get("taxamount");
				totalnum = totalnum.add(unitnum);
				totaltaxamount = totaltaxamount.add(taxamount);
				GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
				if(null!=goodsInfo){
					map.put("goodsname", goodsInfo.getName());
					map.put("boxnum", goodsInfo.getBoxnum());
					map.put("barcode", goodsInfo.getBarcode());
					Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
					if(null!=brand){
						map.put("brandname", brand.getName());
					}
				}
				String outstorageid=(String)map.get("outstorageid");
				StorageInfo outstorage=getStorageInfoByID(outstorageid);
				if(outstorage!=null){
					map.put("outstoragename",outstorage.getName());
				}
				String enterstorageid=(String)map.get("enterstorageid");
				StorageInfo enterstorage=getStorageInfoByID(enterstorageid);
				if(enterstorage!=null){
					map.put("enterstoragename",enterstorage.getName());
				}
			}
			totalMap.put("unitnum", totalnum);
			totalMap.put("taxamount", totaltaxamount);
			Map auxMap = countGoodsInfoNumber(goodsid, null, totalnum);
			String auxnumdetail = (String) auxMap.get("auxnumdetail");
			totalMap.put("auxnumdetail", auxnumdetail);
			list.add(totalMap);
		}
		return list;
	}

	/**
	 * 根据手机调拨申请单编号 获取是否上传
	 *
	 * @param billid
	 * @return
	 * @throws Exception
	 */
	@Override
	public String hasPhoneBillByAllocateOut(String billid) throws Exception {
		int i = allocateOutMapper.hasPhoneBillByAllocateOut(billid);
		if(i>0){
			return allocateOutMapper.getAllocateOutBillIDByPhoneBillid(billid);
		}
		return null;
	}
	/**
	 * 为手机提供调拨出库单列表
	 *
	 * @param pageMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public List showAllocateOutListForPhone(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_storage_allocate_out", "t");
		pageMap.setDataSql(dataSql);
		List<AllocateNotice> list = allocateOutMapper.showAllocateOutListForPhone(pageMap);
		return list;
	}

	/**
	 * 根据billid获取调拨出库明细
	 *
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2015-12-2
	 */
	@Override
	public List<AllocateOutDetail> getAllocateOutDetailList(String billid)throws Exception {
		List<AllocateOutDetail> allocateOutDetailList = allocateOutMapper.getAllocateOutDetailList(billid);
		return  allocateOutDetailList;
	}
}

