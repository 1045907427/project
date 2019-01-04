/**
 * @(#)StorageSaleOutServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 31, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.model.SalesInvoiceBill;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.dao.ReceiptMapper;
import com.hd.agent.sales.model.DispatchBill;
import com.hd.agent.sales.model.DispatchBillDetail;
import com.hd.agent.sales.model.Receipt;
import com.hd.agent.sales.service.ISalesOutService;
import com.hd.agent.storage.dao.SaleRejectEnterMapper;
import com.hd.agent.storage.dao.SaleoutMapper;
import com.hd.agent.storage.model.*;
import com.hd.agent.storage.service.IStorageSaleOutService;
import com.hd.agent.system.model.SysCode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

/**
 * 
 * 销售出库单service实现类
 * @author chenwei
 */
public class StorageSaleOutServiceImpl extends BaseStorageServiceImpl implements
		IStorageSaleOutService {
	/**
	 * 销售模块接口
	 */
	protected ISalesOutService salesOutService;
	
	protected SaleoutMapper saleoutMapper;

    private ReceiptMapper receiptMapper;

    private SaleRejectEnterMapper saleRejectEnterMapper;

	public SaleoutMapper getSaleoutMapper() {
		return saleoutMapper;
	}

	public void setSaleoutMapper(SaleoutMapper saleoutMapper) {
		this.saleoutMapper = saleoutMapper;
	}
	
	public ISalesOutService getSalesOutService() {
		return salesOutService;
	}

	public void setSalesOutService(ISalesOutService salesOutService) {
		this.salesOutService = salesOutService;
	}

    public ReceiptMapper getReceiptMapper() {
        return receiptMapper;
    }

    public void setReceiptMapper(ReceiptMapper receiptMapper) {
        this.receiptMapper = receiptMapper;
    }

    public SaleRejectEnterMapper getSaleRejectEnterMapper() {
        return saleRejectEnterMapper;
    }

    public void setSaleRejectEnterMapper(SaleRejectEnterMapper saleRejectEnterMapper) {
        this.saleRejectEnterMapper = saleRejectEnterMapper;
    }

    /**
	 * 根据销售发货通知单生成销售出库单
	 * @param dispatchBill
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 7, 2013
	 */
	@Override
	public Map addSaleOutByDispatchbill(DispatchBill dispatchBill,List<DispatchBillDetail> detailList) throws Exception{
        boolean flag = true;
        String msg = "";
        String saleoutid = "";
        String id = "";
        //商品仓库发货方式
        //1先按商品默认仓库发货，默认仓库不足发其他仓库
        //2按设置的默认仓库发货,默认仓库不足时，发其他仓库
        //3先按设置默认仓库发货，再发部门关联的仓库，最后发其他仓库
        String goodsStorageDeployType = getSysParamValue("GoodsStorageDeployType");
        if(StringUtils.isEmpty(goodsStorageDeployType)){
            goodsStorageDeployType = "1";
        }
		//根据发货通知单明显列表 拆分属于不同仓库的数据
		//根据仓库生成出库单
		Map<String,List<DispatchBillDetail>> storageMap = new HashMap();
		//克隆发货通知单明细列表
        List<DispatchBillDetail> dataList = (List<DispatchBillDetail>) CommonUtils.deepCopy(detailList);
        if("1".equals(goodsStorageDeployType)){
            deploySaleoutByGoodsDefaulStorage(storageMap,dispatchBill,dataList);
        }else if("2".equals(goodsStorageDeployType)){
            deploySaleoutBySetDefaulStorage(storageMap,dispatchBill,dataList);
        }else if("3".equals(goodsStorageDeployType)){
            deploySaleoutByDefaulStorageAndDeptStorage(storageMap,dispatchBill,dataList);
        }
        //生成发货单
		Set set = storageMap.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<String, List<DispatchBillDetail>> entry = (Entry<String, List<DispatchBillDetail>>) it.next();
			String storageid = entry.getKey();
			List detailAddList = entry.getValue();
			if(detailAddList.size()>0){
				//判断发货通知单针对该仓库是否生成了发货单
				Saleout storageSaleout = getSaleoutMapper().getSaleOutInfoByStorageidAndSourceid(dispatchBill.getId(), storageid);
				if(null==storageSaleout){
					//生成出库单
					Map returnMap = addSaleOutByDispatchbillWithStorageid(storageid, dispatchBill, detailAddList);
					flag = flag&&(Boolean) returnMap.get("flag");
					msg += returnMap.get("msg");
					if("".equals(saleoutid)){
						saleoutid += (String) returnMap.get("saleoutid");
					}else{
						saleoutid += ","+(String) returnMap.get("saleoutid");
					}
					id = (String) returnMap.get("saleoutid");
				}
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("saleoutid", saleoutid);
		map.put("id", id);
		return map;
	}

    /**
     * 商品发货选择仓库模式 对应GoodsStorageDeployType 1
     * 1先按商品默认仓库发货，默认仓库不足发其他仓库
     * @param storageMap
     * @param dispatchBill
     * @param dataList
     * @throws Exception
     */
    public void deploySaleoutByGoodsDefaulStorage(Map storageMap,DispatchBill dispatchBill,List<DispatchBillDetail> dataList) throws Exception{
        Map<String,String> goodsMap = new HashMap<String,String>();
        for(DispatchBillDetail dispatchBillDetail : dataList){
            if(!dispatchBill.getIsSupperAudit()){
                GoodsInfo goodsInfo = getAllGoodsInfoByID(dispatchBillDetail.getGoodsid());
                String storageid = goodsInfo.getStorageid();
                if(StringUtils.isNotEmpty(dispatchBillDetail.getStorageid())){
                    storageid = dispatchBillDetail.getStorageid();
                }
                //判断商品是否指定了库存批次
                if(StringUtils.isEmpty(dispatchBillDetail.getSummarybatchid()) && "0".equals(dispatchBillDetail.getIsdiscount())){
                    Map returnMap = isSendGoodsByStorageidAndGoodsid(storageid, dispatchBillDetail.getGoodsid(), dispatchBillDetail.getUnitnum());
                    boolean useflag = (Boolean) returnMap.get("flag");
                    //默认仓库数量不够时 获取其他足够仓库
                    if(!useflag){
                        //不是超级审核，需要根据库存可用量，生成发货单
                        //是否指定仓库
                        if(StringUtils.isNotEmpty(dispatchBillDetail.getStorageid())){
                            //默认仓库可用量
                            BigDecimal storageNum = (BigDecimal) returnMap.get("useablenum");
                            if(null==storageNum){
                                storageNum = BigDecimal.ZERO;
                            }
                            if(null!=storageNum && storageNum.compareTo(BigDecimal.ZERO)==-1){
                                storageNum = BigDecimal.ZERO;
                            }
                            dispatchBillDetail.setUnitnum(storageNum);
                        }else{
                            //默认仓库可用量
                            BigDecimal storageNum = (BigDecimal) returnMap.get("useablenum");
                            if(null==storageNum){
                                storageNum = BigDecimal.ZERO;
                            }
                            if(null!=storageNum && storageNum.compareTo(BigDecimal.ZERO)==-1){
                                storageNum = BigDecimal.ZERO;
                            }
                            //不足数量
                            BigDecimal noEnoughNum = dispatchBillDetail.getUnitnum().subtract(storageNum);
                            //判断默认仓库中是否存在该商品
                            if(null!=storageNum && storageNum.compareTo(BigDecimal.ZERO)!=0){
                                dispatchBillDetail.setUnitnum(storageNum);
                                if(storageMap.containsKey(storageid)){
                                    List otherList = (List) storageMap.get(storageid);
                                    otherList.add(dispatchBillDetail);
                                    storageMap.put(storageid, otherList);
                                }else{
                                    List otherList = new ArrayList();
                                    otherList.add(dispatchBillDetail);
                                    storageMap.put(storageid, otherList);
                                }
                            }else{
                                dispatchBillDetail.setUnitnum(BigDecimal.ZERO);
                            }
                            //获取商品能发货的仓库库存列表
                            List<StorageSummary> otherStorageList = getStorageSummaryListByGoodsWithoutStorageid(dispatchBillDetail.getGoodsid(), storageid);
                            //配置其他仓库的发货单明细
                            deploySaleoutDetailListByOtherStorage(storageMap,goodsMap,otherStorageList,noEnoughNum,dispatchBillDetail);
                        }
                    }
                }
                if(null!=dispatchBillDetail.getUnitnum() && dispatchBillDetail.getUnitnum().compareTo(BigDecimal.ZERO)==1){
                    if(storageMap.containsKey(storageid)){
                        List otherList = (List) storageMap.get(storageid);
                        otherList.add(dispatchBillDetail);
                        storageMap.put(storageid, otherList);
                    }else{
                        List otherList = new ArrayList();
                        otherList.add(dispatchBillDetail);
                        storageMap.put(storageid, otherList);
                    }
                }
            }else{
                //超级审核 取默认仓库
                GoodsInfo goodsInfo = getAllGoodsInfoByID(dispatchBillDetail.getGoodsid());
                String storageid = goodsInfo.getStorageid();
                if(StringUtils.isNotEmpty(dispatchBillDetail.getStorageid())){
                    storageid = dispatchBillDetail.getStorageid();
                    if(storageMap.containsKey(storageid)){
                        List otherList = (List) storageMap.get(storageid);
                        otherList.add(dispatchBillDetail);
                        storageMap.put(storageid, otherList);
                    }else{
                        List otherList = new ArrayList();
                        otherList.add(dispatchBillDetail);
                        storageMap.put(storageid, otherList);
                    }
                }else{
                    //当默认仓库商品未入库过时 取其他仓库有数据的
                    StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageid,dispatchBillDetail.getGoodsid());
                    if(null==storageSummary){
                        //获取商品能发货的仓库库存列表
                        List<StorageSummary> otherStorageList = getStorageSummaryListByGoodsWithoutStorageid(dispatchBillDetail.getGoodsid(), storageid);
                        //配置其他仓库的发货单明细
                        deploySaleoutDetailListByOtherStorage(storageMap,goodsMap,otherStorageList,dispatchBillDetail.getUnitnum(),dispatchBillDetail);
                    }else{
                        if(storageMap.containsKey(storageid)){
                            List otherList = (List) storageMap.get(storageid);
                            otherList.add(dispatchBillDetail);
                            storageMap.put(storageid, otherList);
                        }else{
                            List otherList = new ArrayList();
                            otherList.add(dispatchBillDetail);
                            storageMap.put(storageid, otherList);
                        }
                    }
                }
            }

        }
        if(!dispatchBill.getIsSupperAudit()){
            //折扣商品选择仓库
            for(DispatchBillDetail dispatchBillDetail : dataList) {
                if ("1".equals(dispatchBillDetail.getIsdiscount())) {
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(dispatchBillDetail.getGoodsid());
                    String discountStorageid = goodsInfo.getStorageid();
                    if(StringUtils.isNotEmpty(dispatchBillDetail.getStorageid())){
                        discountStorageid = dispatchBillDetail.getStorageid();
                    }
                    if (storageMap.containsKey(discountStorageid)) {
                        List otherList = (List) storageMap.get(discountStorageid);
                        otherList.add(dispatchBillDetail);
                        storageMap.put(discountStorageid, otherList);
                    } else {
                        if(goodsMap.containsKey(dispatchBillDetail.getGoodsid())){
                            String dstorageid = goodsMap.get(dispatchBillDetail.getGoodsid());
                            if(storageMap.containsKey(dstorageid)){
                                List otherList = (List) storageMap.get(dstorageid);
                                otherList.add(dispatchBillDetail);
                                storageMap.put(dstorageid, otherList);
                            }else{
                                List otherList = new ArrayList();
                                otherList.add(dispatchBillDetail);
                                storageMap.put(discountStorageid, otherList);
                            }
                        }else{
                            List otherList = new ArrayList();
                            otherList.add(dispatchBillDetail);
                            storageMap.put(discountStorageid, otherList);
                        }
                    }
                }
            }
        }

    }

    /**
     * 商品发货选择仓库模式 对应GoodsStorageDeployType 2
     * 2按设置的默认仓库发货,默认仓库不足时，发其他仓库(未设置默认仓库，则取商品的默认仓库)
     * @param storageMap
     * @param dispatchBill
     * @param dataList
     * @throws Exception
     */
    public void deploySaleoutBySetDefaulStorage(Map storageMap,DispatchBill dispatchBill,List<DispatchBillDetail> dataList) throws Exception{
        //默认发货仓
        String defaultStorageid = getSysParamValue("DefaultSendStorage");
        if(StringUtils.isEmpty(defaultStorageid)){
            defaultStorageid = null;
        }else{
            StorageInfo storageInfo = getStorageInfoByID(defaultStorageid);
            if(null==storageInfo){
                defaultStorageid = null;
            }
        }
        Map<String,String> goodsMap = new HashMap<String,String>();
        for(DispatchBillDetail dispatchBillDetail : dataList){
            //不是超级审核，需要根据库存可用量，生成发货单
            if(!dispatchBill.getIsSupperAudit()){
                //默认发货仓不存在时，取商品的默认仓库
                String storageid = defaultStorageid;
                if(StringUtils.isEmpty(defaultStorageid)){
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(dispatchBillDetail.getGoodsid());
                    storageid = goodsInfo.getStorageid();
                }
                if(StringUtils.isNotEmpty(dispatchBillDetail.getStorageid())){
                    storageid = dispatchBillDetail.getStorageid();
                }
                //判断商品是否指定了库存批次
                if(StringUtils.isEmpty(dispatchBillDetail.getSummarybatchid()) && "0".equals(dispatchBillDetail.getIsdiscount())){
                    Map returnMap = isSendGoodsByStorageidAndGoodsid(storageid, dispatchBillDetail.getGoodsid(), dispatchBillDetail.getUnitnum());
                    boolean useflag = (Boolean) returnMap.get("flag");
                    //默认仓库数量不够时 获取其他足够仓库
                    if(!useflag){
                        //是否指定仓库
                        if(StringUtils.isNotEmpty(dispatchBillDetail.getStorageid())){
                            //默认仓库可用量
                            BigDecimal storageNum = (BigDecimal) returnMap.get("useablenum");
                            if(null==storageNum){
                                storageNum = BigDecimal.ZERO;
                            }
                            if(null!=storageNum && storageNum.compareTo(BigDecimal.ZERO)==-1){
                                storageNum = BigDecimal.ZERO;
                            }
                            dispatchBillDetail.setUnitnum(storageNum);
                        }else{
                            //默认仓库可用量
                            BigDecimal storageNum = (BigDecimal) returnMap.get("useablenum");
                            if(null==storageNum){
                                storageNum = BigDecimal.ZERO;
                            }
                            if(null!=storageNum && storageNum.compareTo(BigDecimal.ZERO)==-1){
                                storageNum = BigDecimal.ZERO;
                            }
                            //不足数量
                            BigDecimal noEnoughNum = dispatchBillDetail.getUnitnum().subtract(storageNum);
                            //判断默认仓库中是否存在该商品
                            if(null!=storageNum && storageNum.compareTo(BigDecimal.ZERO)!=0){
                                dispatchBillDetail.setUnitnum(storageNum);
                                if(storageMap.containsKey(storageid)){
                                    List otherList = (List) storageMap.get(storageid);
                                    otherList.add(dispatchBillDetail);
                                    storageMap.put(storageid, otherList);
                                }else{
                                    List otherList = new ArrayList();
                                    otherList.add(dispatchBillDetail);
                                    storageMap.put(storageid, otherList);
                                }
                            }else{
                                dispatchBillDetail.setUnitnum(BigDecimal.ZERO);
                            }
                            //获取商品能发货的仓库库存列表
                            List<StorageSummary> otherStorageList = getStorageSummaryListByGoodsWithoutStorageid(dispatchBillDetail.getGoodsid(), storageid);
                            //配置其他仓库的发货单明细
                            deploySaleoutDetailListByOtherStorage(storageMap,goodsMap,otherStorageList,noEnoughNum,dispatchBillDetail);
                        }
                    }
                }
                if(null!=dispatchBillDetail.getUnitnum() && dispatchBillDetail.getUnitnum().compareTo(BigDecimal.ZERO)==1){
                    if(storageMap.containsKey(storageid)){
                        List otherList = (List) storageMap.get(storageid);
                        otherList.add(dispatchBillDetail);
                        storageMap.put(storageid, otherList);
                    }else{
                        List otherList = new ArrayList();
                        otherList.add(dispatchBillDetail);
                        storageMap.put(storageid, otherList);
                    }
                }

            }else{
                //超级审核 取默认仓库
                GoodsInfo goodsInfo = getAllGoodsInfoByID(dispatchBillDetail.getGoodsid());
                String storageid = goodsInfo.getStorageid();
                if(StringUtils.isNotEmpty(dispatchBillDetail.getStorageid())){
                    storageid = dispatchBillDetail.getStorageid();
                    if(storageMap.containsKey(storageid)){
                        List otherList = (List) storageMap.get(storageid);
                        otherList.add(dispatchBillDetail);
                        storageMap.put(storageid, otherList);
                    }else{
                        List otherList = new ArrayList();
                        otherList.add(dispatchBillDetail);
                        storageMap.put(storageid, otherList);
                    }
                }else{
                    //当默认仓库商品未入库过时 取其他仓库有数据的
                    StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageid,dispatchBillDetail.getGoodsid());
                    if(null==storageSummary){
                        //获取商品能发货的仓库库存列表
                        List<StorageSummary> otherStorageList = getStorageSummaryListByGoodsWithoutStorageid(dispatchBillDetail.getGoodsid(), storageid);
                        if(null!=otherStorageList && otherStorageList.size()>0){
                            storageSummary = otherStorageList.get(0);
                            storageid = storageSummary.getStorageid();
                        }
                        //获取商品能发货的仓库库存列表
                        //配置其他仓库的发货单明细
                        deploySaleoutDetailListByOtherStorage(storageMap,goodsMap,otherStorageList,dispatchBillDetail.getUnitnum(),dispatchBillDetail);
                    }else{
                        if(storageMap.containsKey(storageid)){
                            List otherList = (List) storageMap.get(storageid);
                            otherList.add(dispatchBillDetail);
                            storageMap.put(storageid, otherList);
                        }else{
                            List otherList = new ArrayList();
                            otherList.add(dispatchBillDetail);
                            storageMap.put(storageid, otherList);
                        }
                    }
                }
            }

        }
        if(!dispatchBill.getIsSupperAudit()) {
            //折扣商品选择仓库
            for (DispatchBillDetail dispatchBillDetail : dataList) {
                if ("1".equals(dispatchBillDetail.getIsdiscount())) {
                    String discountStorageid = defaultStorageid;
                    if (StringUtils.isNotEmpty(dispatchBillDetail.getStorageid())) {
                        discountStorageid = dispatchBillDetail.getStorageid();
                    }
                    if (storageMap.containsKey(discountStorageid)) {
                        List otherList = (List) storageMap.get(discountStorageid);
                        otherList.add(dispatchBillDetail);
                        storageMap.put(discountStorageid, otherList);
                    } else {
                        if (goodsMap.containsKey(dispatchBillDetail.getGoodsid())) {
                            String dstorageid = goodsMap.get(dispatchBillDetail.getGoodsid());
                            if (storageMap.containsKey(dstorageid)) {
                                List otherList = (List) storageMap.get(dstorageid);
                                otherList.add(dispatchBillDetail);
                                storageMap.put(dstorageid, otherList);
                            } else {
                                List otherList = new ArrayList();
                                otherList.add(dispatchBillDetail);
                                storageMap.put(discountStorageid, otherList);
                            }
                        } else {
                            List otherList = new ArrayList();
                            otherList.add(dispatchBillDetail);
                            storageMap.put(discountStorageid, otherList);
                        }
                    }
                }
            }
        }
    }

    /**
     * 商品发货选择仓库模式 对应GoodsStorageDeployType 3
     * 3先按设置默认仓库发货，再发部门关联的仓库，最后发其他仓库
     * @param storageMap
     * @param dispatchBill
     * @param dataList
     * @throws Exception
     */
    public void deploySaleoutByDefaulStorageAndDeptStorage(Map storageMap,DispatchBill dispatchBill,List<DispatchBillDetail> dataList) throws Exception{
        //部门关联的仓库
        String deptStorageid = null;
        if(StringUtils.isNotEmpty(dispatchBill.getSalesdept())){
            DepartMent dept = getDepartMentById(dispatchBill.getSalesdept());
            StorageInfo deptStorage = getStorageInfoByID(dept.getStorageid());
            if(null!=deptStorage){
                deptStorageid = deptStorage.getId();
            }
        }

        //默认发货仓
        String defaultStorageid = getSysParamValue("DefaultSendStorage");
        if(StringUtils.isEmpty(defaultStorageid)){
            defaultStorageid = null;
        }else{
            StorageInfo storageInfo = getStorageInfoByID(defaultStorageid);
            if(null==storageInfo){
                defaultStorageid = null;
            }
        }
        Map<String,String> goodsMap = new HashMap<String,String>();
        for(DispatchBillDetail dispatchBillDetail : dataList){
            //不是超级审核，需要根据库存可用量，生成发货单
            if(!dispatchBill.getIsSupperAudit()){
                //默认发货仓不存在时，取商品的默认仓库
                String storageid = defaultStorageid;
                if(StringUtils.isEmpty(defaultStorageid)){
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(dispatchBillDetail.getGoodsid());
                    storageid = goodsInfo.getStorageid();
                }
                if(StringUtils.isNotEmpty(dispatchBillDetail.getStorageid())){
                    storageid = dispatchBillDetail.getStorageid();
                }
                //判断商品是否指定了库存批次 未指定库存批次 才进行仓库配货
                if(StringUtils.isEmpty(dispatchBillDetail.getSummarybatchid()) && "0".equals(dispatchBillDetail.getIsdiscount())) {
                    Map returnMap = isSendGoodsByStorageidAndGoodsid(storageid, dispatchBillDetail.getGoodsid(), dispatchBillDetail.getUnitnum());
                    boolean useflag = (Boolean) returnMap.get("flag");
                    //默认仓库数量不够时 获取其他足够仓库
                    if (!useflag) {
                        //是否指定仓库
                        if (StringUtils.isNotEmpty(dispatchBillDetail.getStorageid())) {
                            //默认仓库可用量
                            BigDecimal storageNum = (BigDecimal) returnMap.get("useablenum");
                            if (null == storageNum) {
                                storageNum = BigDecimal.ZERO;
                            }
                            if (null != storageNum && storageNum.compareTo(BigDecimal.ZERO) == -1) {
                                storageNum = BigDecimal.ZERO;
                            }
                            dispatchBillDetail.setUnitnum(storageNum);
                        } else {
                            //默认仓库可用量
                            BigDecimal storageNum = (BigDecimal) returnMap.get("useablenum");
                            if (null == storageNum) {
                                storageNum = BigDecimal.ZERO;
                            }
                            if (null != storageNum && storageNum.compareTo(BigDecimal.ZERO) == -1) {
                                storageNum = BigDecimal.ZERO;
                            }
                            //不足数量
                            BigDecimal noEnoughNum = dispatchBillDetail.getUnitnum().subtract(storageNum);
                            //判断默认仓库中是否存在该商品
                            if (null != storageNum && storageNum.compareTo(BigDecimal.ZERO) != 0) {
                                dispatchBillDetail.setUnitnum(storageNum);
                                if (storageMap.containsKey(storageid)) {
                                    List otherList = (List) storageMap.get(storageid);
                                    otherList.add(dispatchBillDetail);
                                    storageMap.put(storageid, otherList);
                                } else {
                                    List otherList = new ArrayList();
                                    otherList.add(dispatchBillDetail);
                                    storageMap.put(storageid, otherList);
                                }
                            } else {
                                dispatchBillDetail.setUnitnum(BigDecimal.ZERO);
                            }
                            List<StorageSummary> otherStorageList = new ArrayList<StorageSummary>();
                            StorageSummary deptStorageSummary = getStorageSummaryByStorageidAndGoodsid(deptStorageid, dispatchBillDetail.getGoodsid());
                            if (null != deptStorageSummary) {
                                otherStorageList.add(deptStorageSummary);
                            }
                            //获取商品能发货的仓库库存列表
                            List<StorageSummary> otherStorageSummaryList = getStorageSummaryListByGoodsWithoutStorageid(dispatchBillDetail.getGoodsid(), storageid);
                            //排除部门关联仓库 防止重复配置
                            for (StorageSummary storageSummary : otherStorageSummaryList) {
                                if (!storageSummary.getStorageid().equals(deptStorageid)) {
                                    otherStorageList.add(storageSummary);
                                }
                            }
                            deploySaleoutDetailListByOtherStorage(storageMap, goodsMap, otherStorageList, noEnoughNum, dispatchBillDetail);
                        }
                    }
                }
                if(null!=dispatchBillDetail.getUnitnum() && dispatchBillDetail.getUnitnum().compareTo(BigDecimal.ZERO)==1){
                    if(storageMap.containsKey(storageid)){
                        List otherList = (List) storageMap.get(storageid);
                        otherList.add(dispatchBillDetail);
                        storageMap.put(storageid, otherList);
                    }else{
                        List otherList = new ArrayList();
                        otherList.add(dispatchBillDetail);
                        storageMap.put(storageid, otherList);
                    }
                }
            }else{
                //已指定发货批次 或者超级审核或者默认仓库可用量足够 直接默认仓库发货
                //默认发货仓不存在时，取商品的默认仓库
                GoodsInfo goodsInfo = getAllGoodsInfoByID(dispatchBillDetail.getGoodsid());
                String storageid = goodsInfo.getStorageid();
                if(StringUtils.isNotEmpty(dispatchBillDetail.getStorageid())){
                    storageid = dispatchBillDetail.getStorageid();
                    if(storageMap.containsKey(storageid)){
                        List otherList = (List) storageMap.get(storageid);
                        otherList.add(dispatchBillDetail);
                        storageMap.put(storageid, otherList);
                    }else{
                        List otherList = new ArrayList();
                        otherList.add(dispatchBillDetail);
                        storageMap.put(storageid, otherList);
                    }
                }else{
                    //当默认仓库商品未入库过时 取其他仓库有数据的
                    StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageid,dispatchBillDetail.getGoodsid());
                    if(null==storageSummary){
                        List<StorageSummary> otherStorageList = new ArrayList<StorageSummary>();
                        StorageSummary deptStorageSummary = getStorageSummaryByStorageidAndGoodsid(deptStorageid, dispatchBillDetail.getGoodsid());
                        if (null != deptStorageSummary) {
                            otherStorageList.add(deptStorageSummary);
                        }
                        //获取商品能发货的仓库库存列表
                        List<StorageSummary> otherStorageSummaryList = getStorageSummaryListByGoodsWithoutStorageid(dispatchBillDetail.getGoodsid(), storageid);
                        //排除部门关联仓库 防止重复配置
                        for (StorageSummary storageSummaryother : otherStorageSummaryList) {
                            if (!storageSummaryother.getStorageid().equals(deptStorageid)) {
                                otherStorageList.add(storageSummaryother);
                            }
                        }
                        deploySaleoutDetailListByOtherStorage(storageMap, goodsMap, otherStorageList, dispatchBillDetail.getUnitnum(), dispatchBillDetail);
                    }else{
                        if(storageMap.containsKey(storageid)){
                            List otherList = (List) storageMap.get(storageid);
                            otherList.add(dispatchBillDetail);
                            storageMap.put(storageid, otherList);
                        }else{
                            List otherList = new ArrayList();
                            otherList.add(dispatchBillDetail);
                            storageMap.put(storageid, otherList);
                        }
                    }
                }
            }
        }
        //超级审核 折扣明细不需要重新分配仓库
        if(!dispatchBill.getIsSupperAudit()) {
            for (DispatchBillDetail dispatchBillDetail : dataList) {
                if ("1".equals(dispatchBillDetail.getIsdiscount())) {
                    String discountStorageid = defaultStorageid;
                    if (StringUtils.isNotEmpty(dispatchBillDetail.getStorageid())) {
                        discountStorageid = dispatchBillDetail.getStorageid();
                    }
                    //判断折扣商品的默认仓库 是否存在明细
                    if (storageMap.containsKey(discountStorageid)) {
                        List otherList = (List) storageMap.get(discountStorageid);
                        otherList.add(dispatchBillDetail);
                        storageMap.put(defaultStorageid, otherList);
                    } else {
                        //不存在 取商品存在的仓库
                        if (goodsMap.containsKey(dispatchBillDetail.getGoodsid())) {
                            String dstorageid = goodsMap.get(dispatchBillDetail.getGoodsid());
                            if (storageMap.containsKey(dstorageid)) {
                                List otherList = (List) storageMap.get(dstorageid);
                                otherList.add(dispatchBillDetail);
                                storageMap.put(dstorageid, otherList);
                            } else {
                                List otherList = new ArrayList();
                                otherList.add(dispatchBillDetail);
                                storageMap.put(discountStorageid, otherList);
                            }
                        } else {
                            List otherList = new ArrayList();
                            otherList.add(dispatchBillDetail);
                            storageMap.put(discountStorageid, otherList);
                        }
                    }
                }
            }
        }
    }

    /**
     * 配置其他仓库的发货单
     * @param storageMap                仓库集合
     * @param goodsMap                  商品对应仓库集合
     * @param otherStorageList          其他发货仓库存数据
     * @param noEnoughNum               发货不足数量
     * @param dispatchBillDetail        发货通知单明细
     * @throws Exception
     */
    public void deploySaleoutDetailListByOtherStorage(Map storageMap,Map goodsMap,List<StorageSummary> otherStorageList,BigDecimal noEnoughNum,DispatchBillDetail dispatchBillDetail) throws Exception{
        //配置各仓库要发货的数量
        for(StorageSummary storageSummary : otherStorageList){
            //仓库可用量大于等于不足数量时
            if(storageSummary.getUsablenum().compareTo(noEnoughNum)!=-1){
                if(storageMap.containsKey(storageSummary.getStorageid())){
                    List otherList = (List) storageMap.get(storageSummary.getStorageid());
                    DispatchBillDetail newDispatchBillDetail = (DispatchBillDetail) BeanUtils.cloneBean(dispatchBillDetail);
                    newDispatchBillDetail.setUnitnum(noEnoughNum);
                    if(newDispatchBillDetail.getUnitnum().compareTo(BigDecimal.ZERO)!=0){
                        otherList.add(newDispatchBillDetail);
                        storageMap.put(storageSummary.getStorageid(), otherList);
                        goodsMap.put(dispatchBillDetail.getGoodsid(),storageSummary.getStorageid());
                    }
                }else{
                    List otherList = new ArrayList();
                    DispatchBillDetail newDispatchBillDetail = (DispatchBillDetail) BeanUtils.cloneBean(dispatchBillDetail);
                    newDispatchBillDetail.setUnitnum(noEnoughNum);
                    if(newDispatchBillDetail.getUnitnum().compareTo(BigDecimal.ZERO)!=0){
                        otherList.add(newDispatchBillDetail);
                        storageMap.put(storageSummary.getStorageid(), otherList);
                        goodsMap.put(dispatchBillDetail.getGoodsid(),storageSummary.getStorageid());
                    }
                }
                break;
            }else{
                //仓库商品可用量不够时
                if(storageMap.containsKey(storageSummary.getStorageid())){
                    List otherList = (List) storageMap.get(storageSummary.getStorageid());
                    DispatchBillDetail newDispatchBillDetail = (DispatchBillDetail) BeanUtils.cloneBean(dispatchBillDetail);
                    newDispatchBillDetail.setUnitnum(storageSummary.getUsablenum());
                    if(newDispatchBillDetail.getUnitnum().compareTo(BigDecimal.ZERO)!=0){
                        otherList.add(newDispatchBillDetail);
                        storageMap.put(storageSummary.getStorageid(), otherList);
                        goodsMap.put(dispatchBillDetail.getGoodsid(),storageSummary.getStorageid());
                    }
                }else{
                    List otherList = new ArrayList();
                    DispatchBillDetail newDispatchBillDetail = (DispatchBillDetail) BeanUtils.cloneBean(dispatchBillDetail);
                    newDispatchBillDetail.setUnitnum(storageSummary.getUsablenum());
                    if(newDispatchBillDetail.getUnitnum().compareTo(BigDecimal.ZERO)!=0){
                        otherList.add(newDispatchBillDetail);
                        storageMap.put(storageSummary.getStorageid(), otherList);
                        goodsMap.put(dispatchBillDetail.getGoodsid(),storageSummary.getStorageid());
                    }
                }
                noEnoughNum = noEnoughNum.subtract(storageSummary.getUsablenum());
            }
        }
    }
	/**
	 * 根据仓库编码和销售发货通知单单信息生成销售出库单
	 * @param stroageid			仓库编号
	 * @param dispatchBill		发货通知单基本信息
	 * @param detailList		发货通知单明细列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 11, 2013
	 */
	public Map addSaleOutByDispatchbillWithStorageid(String stroageid,DispatchBill dispatchBill,List<DispatchBillDetail> detailList) throws Exception{
		Map map = new HashMap();
		boolean flag = true;
		String msg = "";
		StorageInfo storageInfo = getStorageInfoByID(stroageid);
		if(null!=dispatchBill && null!=storageInfo){
			Saleout saleout = new Saleout();
			if (isAutoCreate("t_storage_saleout")) {
				// 获取自动编号
				String id = getAutoCreateSysNumbderForeign(saleout, "t_storage_saleout");
				saleout.setId(id);
			}else{
				saleout.setId("CKD"+CommonUtils.getDataNumber());
			} 
			//根据销售发货通知单 生成销售出库单
			saleout.setBusinessdate(dispatchBill.getBusinessdate());
			saleout.setStatus("2");
			saleout.setSalesarea(dispatchBill.getSalesarea());
			saleout.setSalesdept(dispatchBill.getSalesdept());
			saleout.setSalesuser(dispatchBill.getSalesuser());
			saleout.setStorageid(stroageid);
			//订单编号
			saleout.setSaleorderid(dispatchBill.getBillno());
			saleout.setSourcetype("1");
			saleout.setSourceid(dispatchBill.getId());
			saleout.setCustomerid(dispatchBill.getCustomerid());
			saleout.setPcustomerid(dispatchBill.getPcustomerid());
			saleout.setCustomersort(dispatchBill.getCustomersort());
			saleout.setHandlerid(dispatchBill.getHandlerid());
			saleout.setSettletype(dispatchBill.getSettletype());
			saleout.setPaytype(dispatchBill.getPaytype());
			saleout.setRemark(dispatchBill.getRemark());
			saleout.setAdduserid(dispatchBill.getAdduserid());
			saleout.setAddusername(dispatchBill.getAddusername());
			saleout.setAdddeptid(dispatchBill.getAdddeptid());
			saleout.setAdddeptname(dispatchBill.getAdddeptname());
			saleout.setIndooruserid(dispatchBill.getIndooruserid());
			//可用量是否足够 true足够 false不足
			boolean sendFlag = true;
			if(!dispatchBill.getIsSupperAudit()){
				for(DispatchBillDetail dispatchBillDetail : detailList){
					if(null!=dispatchBillDetail.getUnitnum() && dispatchBillDetail.getUnitnum().compareTo(BigDecimal.ZERO)==1){
						Map returnMap = isSendGoodsByStorageidAndGoodsid(saleout.getStorageid(), dispatchBillDetail.getGoodsid(), dispatchBillDetail.getUnitnum());
						sendFlag = (Boolean) returnMap.get("flag");
						if(sendFlag==false){
							flag = false;
							msg = (String) returnMap.get("msg");
							saleout.setStatus("1");
							break;
						}
					}
				}
			}
			
			//可用量足够
			if(sendFlag){
				//销售发货通知单明细
				for(DispatchBillDetail dispatchBillDetail : detailList){
					if(dispatchBillDetail.getUnitnum().compareTo(BigDecimal.ZERO)!=0 || "1".equals(dispatchBillDetail.getIsdiscount())){
						//添加折扣
						if("1".equals(dispatchBillDetail.getIsdiscount())){
							//销售出库单明细基本信息
							SaleoutDetail saleoutDetail = new SaleoutDetail();
							saleoutDetail.setIsdiscount("1");
							saleoutDetail.setIsbranddiscount(dispatchBillDetail.getIsbranddiscount());
							saleoutDetail.setSaleoutid(saleout.getId());
							saleoutDetail.setDispatchbillid(dispatchBillDetail.getBillid());
							saleoutDetail.setDispatchbilldetailid(dispatchBillDetail.getId());
							saleoutDetail.setRemark(dispatchBillDetail.getRemark());
							saleoutDetail.setGoodsid(dispatchBillDetail.getGoodsid());
							saleoutDetail.setSeq(dispatchBillDetail.getSeq());
							//成本价
							saleoutDetail.setCostprice(dispatchBillDetail.getCostprice());
							//获取商品品牌编号 和 品牌业务员
							GoodsInfo goodsInfo = getAllGoodsInfoByID(dispatchBillDetail.getGoodsid());
							if(null!=goodsInfo){
								saleoutDetail.setGoodssort(goodsInfo.getDefaultsort());
								saleoutDetail.setBrandid(goodsInfo.getBrand());
								saleoutDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), saleout.getCustomerid()));
								//厂家业务员
								saleoutDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), saleout.getCustomerid()));
								//获取品牌部门
								Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
								if(null!=brand){
									saleoutDetail.setBranddept(brand.getDeptid());
								}
                                //实际成本价 商品总成本价
								saleoutDetail.setRealcostprice(goodsInfo.getNewstorageprice());
								//商品供应商
								saleoutDetail.setSupplierid(goodsInfo.getDefaultsupplier());
							}
							saleoutDetail.setStorageid(saleout.getStorageid());
							saleoutDetail.setUnitid(dispatchBillDetail.getUnitid());
							saleoutDetail.setUnitname(dispatchBillDetail.getUnitname());
							saleoutDetail.setAuxunitid(dispatchBillDetail.getAuxunitid());
							saleoutDetail.setAuxunitname(dispatchBillDetail.getAuxunitname());
							
							//自定义字段
							saleoutDetail.setField01(dispatchBillDetail.getField01());
							saleoutDetail.setField02(dispatchBillDetail.getField02());
							saleoutDetail.setField03(dispatchBillDetail.getField03());
							saleoutDetail.setField04(dispatchBillDetail.getField04());
							saleoutDetail.setField05(dispatchBillDetail.getField05());
							saleoutDetail.setField06(dispatchBillDetail.getField06());
							saleoutDetail.setField07(dispatchBillDetail.getField07());
							saleoutDetail.setField08(dispatchBillDetail.getField08());
							//含税单价 无税单价 税种
							saleoutDetail.setTaxprice(dispatchBillDetail.getTaxprice());
							saleoutDetail.setNotaxprice(dispatchBillDetail.getNotaxprice());
							saleoutDetail.setTaxtype(dispatchBillDetail.getTaxtype());
							
							saleoutDetail.setInittaxprice(dispatchBillDetail.getTaxprice());
							
							saleoutDetail.setInitnum(BigDecimal.ZERO);
							saleoutDetail.setUnitnum(BigDecimal.ZERO);
							saleoutDetail.setAuxinitnum(BigDecimal.ZERO);
							saleoutDetail.setAuxnum(BigDecimal.ZERO);
							saleoutDetail.setAuxremainder(BigDecimal.ZERO);
							saleoutDetail.setAuxnumdetail("");
							saleoutDetail.setInittaxamount(dispatchBillDetail.getTaxamount());
							saleoutDetail.setInitnotaxamount(dispatchBillDetail.getNotaxamount());
							saleoutDetail.setTaxamount(dispatchBillDetail.getTaxamount());
							saleoutDetail.setNotaxamount(dispatchBillDetail.getNotaxamount());
							//税额 = 含税金额-无税金额
							saleoutDetail.setTax(dispatchBillDetail.getTax());
							saleoutMapper.addSaleOutDetail(saleoutDetail);
							
						}else{
							//销售发货通知单明细中商品数量 出库后的剩余数量 用来判断是否需要再添加批次数量
							BigDecimal remainderNum = dispatchBillDetail.getUnitnum();
							GoodsInfo goodsInfo = getAllGoodsInfoByID(dispatchBillDetail.getGoodsid());
							//商品指定库存批次后 直接获取库存批次信息处理
							if(StringUtils.isNotEmpty(dispatchBillDetail.getSummarybatchid())){
								StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(dispatchBillDetail.getSummarybatchid());
								if(null!=storageSummaryBatch){
									BigDecimal totalNum = BigDecimal.ZERO;
									BigDecimal usablenum = storageSummaryBatch.getUsablenum();
									if(null==usablenum){
										usablenum = BigDecimal.ZERO;
									}
									//判断仓库是否允许超可用量发货 允许的话 
									//可销售的数量 = 可用量+在途量
									BigDecimal transitnum = BigDecimal.ZERO;
									totalNum = usablenum.add(transitnum);
									BigDecimal saleOutNum = new BigDecimal(0);
									//批次可用量大于等于发货通知单中商品数量时，可以直接使用该批次发货
									if(totalNum.compareTo(remainderNum)!=-1){
										//销售出库单明细 商品数量=销售出库通知单中商品数量
										saleOutNum = remainderNum;
									}else{
										saleOutNum =totalNum;
									}
									if(dispatchBill.getIsSupperAudit()){
										saleOutNum = dispatchBillDetail.getUnitnum();
									}
									//添加销售出库单明细 并且更新现存量信息
									addSaleOutDetail(saleout, dispatchBillDetail, storageSummaryBatch, saleOutNum);
								}
							}else{
								//如果仓库与商品是批次管理 不允许超可用量发货
								if("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
									//批次管理 商品档案中设置批次管理
									List<StorageSummaryBatch> storageSummaryBatchList = getStorageSummaryBatchListByStorageidAndGoodsid(stroageid, goodsInfo.getId());
									for(StorageSummaryBatch storageSummaryBatch : storageSummaryBatchList){
										BigDecimal saleOutNum = new BigDecimal(0);
										//批次可用量大于等于发货通知单中商品数量时，可以直接使用该批次发货
										if(storageSummaryBatch.getUsablenum().compareTo(remainderNum)!=-1){
											//销售出库单明细 商品数量=销售出库通知单中商品数量
											saleOutNum = remainderNum;
										}else{
											saleOutNum = storageSummaryBatch.getUsablenum();
										}
										//添加销售出库单明细 并且更新现存量信息
										addSaleOutDetail(saleout, dispatchBillDetail, storageSummaryBatch, saleOutNum);
										
										//判断销售出库通知单明细中商品数量是否已生成完
										//当该数量=销售出库数量 则表示已经生成完 否则继续生成下一个批次的销售出库数量
										if(remainderNum.compareTo(saleOutNum)==0){
											remainderNum = remainderNum.subtract(saleOutNum);
											break;
										}else{
											remainderNum = remainderNum.subtract(saleOutNum);
										}
									}
								}else{
									//不是批次管理时，优先取历史的批次库存
									//批次库存不足时，再取不是批次管理的库存
									//批次管理 商品档案中设置批次管理
									List<StorageSummaryBatch> storageSummaryBatchList = getStorageSummaryBatchListWithoutNoBatchByStorageidAndGoodsid(stroageid, goodsInfo.getId());
									for(StorageSummaryBatch storageSummaryBatch : storageSummaryBatchList){
										BigDecimal saleOutNum = new BigDecimal(0);
										//批次可用量大于等于发货通知单中商品数量时，可以直接使用该批次发货
										if(storageSummaryBatch.getUsablenum().compareTo(remainderNum)!=-1){
											//销售出库单明细 商品数量=销售出库通知单中商品数量
											saleOutNum = remainderNum;
										}else{
											saleOutNum = storageSummaryBatch.getUsablenum();
										}
										//添加销售出库单明细 并且更新现存量信息
										addSaleOutDetail(saleout, dispatchBillDetail, storageSummaryBatch, saleOutNum);
										
										//判断销售出库通知单明细中商品数量是否已生成完
										//当该数量=销售出库数量 则表示已经生成完 否则继续生成下一个批次的销售出库数量
										if(remainderNum.compareTo(saleOutNum)==0){
											remainderNum = remainderNum.subtract(saleOutNum);
											break;
										}else{
											remainderNum = remainderNum.subtract(saleOutNum);
										}
									}
                                    if(remainderNum.compareTo(BigDecimal.ZERO)==1){
                                        StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(saleout.getStorageid(), dispatchBillDetail.getGoodsid());
                                        if(null!=storageSummaryBatch){
                                            StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(saleout.getStorageid(), dispatchBillDetail.getGoodsid());
                                            BigDecimal totalNum = BigDecimal.ZERO;
                                            BigDecimal usablenum = storageSummaryBatch.getUsablenum();
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
                                            BigDecimal saleOutNum = remainderNum;
                                            //批次可用量大于等于发货通知单中商品数量时，可以直接使用该批次发货
                                            if(dispatchBill.getIsSupperAudit()){
                                                saleOutNum = remainderNum;
                                            }else if(totalNum.compareTo(saleOutNum)==-1){
                                                saleOutNum =totalNum;
                                            }
                                            //添加销售出库单明细 并且更新现存量信息
                                            addSaleOutDetail(saleout, dispatchBillDetail, storageSummaryBatch, saleOutNum);
                                        }
                                    }
                                }

								
							}
						}
					}
				}
			}
			if(flag){
				SaleoutDetail detailSum = saleoutMapper.getSaleOutSumAmountBySaleoutid(saleout.getId());
				if(null!=detailSum){
					saleout.setInitsendamount(detailSum.getInittaxamount());
					saleout.setInitsendcostamount(detailSum.getInitcostamount());
					saleout.setSendamount(detailSum.getTaxamount());
					saleout.setSendnotaxamount(detailSum.getNotaxamount());
					saleout.setSendcostamount(detailSum.getCostamount());
				}
				//添加销售出库单
				saleoutMapper.addSaleOut(saleout);
				//更新销售发货通知单是否被参照的状态
				salesOutService.updateDispatchBillRefer("1", dispatchBill.getId());
				map.put("saleoutid", saleout.getId());
			}
		}
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}
	@Override
	public boolean deleteSaleOutBySourceid(String sourceid) throws Exception {
		List<Saleout> saleoutList = saleoutMapper.getSaleOutInfoBySourceid(sourceid);
		boolean flag = true;
		for(Saleout saleout : saleoutList){
			if(!"1".equals(saleout.getStatus()) && !"2".equals(saleout.getStatus())){
				flag = false;
				break;
			}
		}
		if(flag){
			for(Saleout saleout : saleoutList){
				List<SaleoutDetail> detailList = saleoutMapper.getSaleOutDetailList(saleout.getId());
				int i = 0;
				//删除明细 回滚待发量
				for(SaleoutDetail saleoutDetail : detailList){
					//保存状态时，回滚待发量
					if("2".equals(saleout.getStatus())){
						StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(saleoutDetail.getSummarybatchid());
						boolean rollBackFlag = true;
						if(null!=storageSummaryBatch){
							//回滚待发量
							rollBackFlag = rollBackStorageSummaryWaitnum(storageSummaryBatch.getId(), saleoutDetail.getUnitnum());
							if(rollBackFlag){
								i += saleoutMapper.deleteSaleOutDetail(saleoutDetail.getId()+"", saleout.getId());
							}
						}
					}else if("1".equals(saleout.getStatus())){
						i += saleoutMapper.deleteSaleOutDetail(saleoutDetail.getId()+"", saleout.getId());
					}
				}
				//删除销售出库单
				boolean delFlag = false;
				int j = saleoutMapper.deleteSaleOut(saleout.getId());
				delFlag = j>0;
			}
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 添加销售出库明细 并且更新现存量信息
	 * @param saleout				销售出库单信息
	 * @param dispatchBillDetail	销售出库通知单明细
	 * @param storageSummaryBatch	商品批次量
	 * @param unitnum				出库数量
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 4, 2013
	 */
	public boolean addSaleOutDetail(Saleout saleout,DispatchBillDetail dispatchBillDetail,
			StorageSummaryBatch storageSummaryBatch,BigDecimal unitnum) throws Exception{
		if(unitnum.compareTo(BigDecimal.ZERO)!=0){
			//销售出库单明细基本信息
			SaleoutDetail saleoutDetail = new SaleoutDetail();
			saleoutDetail.setIsdiscount("0");
			saleoutDetail.setSaleoutid(saleout.getId());
			saleoutDetail.setDispatchbillid(dispatchBillDetail.getBillid());
			saleoutDetail.setDispatchbilldetailid(dispatchBillDetail.getId());
			saleoutDetail.setRemark(dispatchBillDetail.getRemark());
			saleoutDetail.setSummarybatchid(storageSummaryBatch.getId());
			saleoutDetail.setGoodsid(dispatchBillDetail.getGoodsid());
			saleoutDetail.setGroupid(dispatchBillDetail.getGroupid());
			saleoutDetail.setDeliverytype(dispatchBillDetail.getDeliverytype());
			saleoutDetail.setBranddept(dispatchBillDetail.getBranddept());
			saleoutDetail.setSeq(dispatchBillDetail.getSeq());
			//成本价
//			saleoutDetail.setCostprice(dispatchBillDetail.getCostprice());
			//获取商品品牌编号 和 品牌业务员
			GoodsInfo goodsInfo = getAllGoodsInfoByID(dispatchBillDetail.getGoodsid());
			if(null!=goodsInfo){
				saleoutDetail.setGoodssort(goodsInfo.getDefaultsort());
				saleoutDetail.setBrandid(goodsInfo.getBrand());
				saleoutDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), saleout.getCustomerid()));
				//厂家业务员
				saleoutDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), saleout.getCustomerid()));
				//成本价
				saleoutDetail.setCostprice(getGoodsCostprice(saleout.getStorageid(),goodsInfo));
                //实际成本价 商品总成本价
				saleoutDetail.setRealcostprice(goodsInfo.getNewstorageprice());
				//商品供应商
				saleoutDetail.setSupplierid(goodsInfo.getDefaultsupplier());
			}
			
			saleoutDetail.setStorageid(saleout.getStorageid());
			saleoutDetail.setStoragelocationid(storageSummaryBatch.getStoragelocationid());
			saleoutDetail.setBatchno(storageSummaryBatch.getBatchno());
			saleoutDetail.setProduceddate(storageSummaryBatch.getProduceddate());
			saleoutDetail.setDeadline(storageSummaryBatch.getDeadline());
			saleoutDetail.setUnitid(dispatchBillDetail.getUnitid());
			saleoutDetail.setUnitname(dispatchBillDetail.getUnitname());
			saleoutDetail.setAuxunitid(dispatchBillDetail.getAuxunitid());
			saleoutDetail.setAuxunitname(dispatchBillDetail.getAuxunitname());
			
			//自定义字段
			saleoutDetail.setField01(dispatchBillDetail.getField01());
			saleoutDetail.setField02(dispatchBillDetail.getField02());
			saleoutDetail.setField03(dispatchBillDetail.getField03());
			saleoutDetail.setField04(dispatchBillDetail.getField04());
			saleoutDetail.setField05(dispatchBillDetail.getField05());
			saleoutDetail.setField06(dispatchBillDetail.getField06());
			saleoutDetail.setField07(dispatchBillDetail.getField07());
			saleoutDetail.setField08(dispatchBillDetail.getField08());
			//含税单价 无税单价 税种
			saleoutDetail.setTaxprice(dispatchBillDetail.getTaxprice());
			saleoutDetail.setNotaxprice(dispatchBillDetail.getNotaxprice());
			saleoutDetail.setTaxtype(dispatchBillDetail.getTaxtype());
			saleoutDetail.setInittaxprice(dispatchBillDetail.getTaxprice());
			
			saleoutDetail.setInitnum(unitnum);
			saleoutDetail.setUnitnum(unitnum);
			
			Map auxnummap = countGoodsInfoNumber(saleoutDetail.getGoodsid(), saleoutDetail.getAuxunitid(), saleoutDetail.getUnitnum());
			BigDecimal auxInteger = new BigDecimal((String) auxnummap.get("auxInteger"));
			BigDecimal auxremainder = new BigDecimal((String) auxnummap.get("auxremainder"));
			saleoutDetail.setAuxinitnum((BigDecimal) auxnummap.get("auxnum"));
			saleoutDetail.setAuxnum(auxInteger);
			saleoutDetail.setAuxremainder(auxremainder);
			saleoutDetail.setAuxnumdetail((String) auxnummap.get("auxnumdetail"));
			saleoutDetail.setTotalbox((BigDecimal) auxnummap.get("auxnum"));
			//含税金额 = 含税单价*数量
            BigDecimal taxamount=saleoutDetail.getTaxprice().multiply(saleoutDetail.getUnitnum());
            saleoutDetail.setTaxamount(taxamount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
			saleoutDetail.setInittaxamount(saleoutDetail.getTaxamount().setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
			BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, saleoutDetail.getTaxtype());
			//无税金额 = 无税单价*数量
			saleoutDetail.setNotaxamount(notaxamount);
			saleoutDetail.setInitnotaxamount(notaxamount);
			//税额 = 含税金额-无税金额
			saleoutDetail.setTax(saleoutDetail.getTaxamount().subtract(saleoutDetail.getNotaxamount()));
			//更新待发量
			boolean flag1 = updateStorageSummaryWaitnum(storageSummaryBatch.getId(), unitnum);
			boolean flag = false;
			if(flag1){
				int i = saleoutMapper.addSaleOutDetail(saleoutDetail);
				flag = i>0;
			}
			return flag;
		}else{
			return false;
		}
	}
	/**
	 * 添加销售出库明细 不更新待发量
	 * @param saleout
	 * @param dispatchBillDetail
	 * @param storageSummaryBatch
	 * @param unitnum
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 11, 2013
	 */
	public boolean addSaleOutDetailWithoutUpdateWaitnum(Saleout saleout,DispatchBillDetail dispatchBillDetail,
			StorageSummaryBatch storageSummaryBatch,BigDecimal unitnum) throws Exception{
		if(unitnum.compareTo(new BigDecimal(0))==1){
			//销售出库单明细基本信息
			SaleoutDetail saleoutDetail = new SaleoutDetail();
			saleoutDetail.setIsdiscount("0");
			saleoutDetail.setSaleoutid(saleout.getId());
			saleoutDetail.setDispatchbillid(dispatchBillDetail.getBillid());
			saleoutDetail.setDispatchbilldetailid(dispatchBillDetail.getId());
			saleoutDetail.setRemark(dispatchBillDetail.getRemark());
			saleoutDetail.setSummarybatchid(storageSummaryBatch.getId());
			saleoutDetail.setGoodsid(dispatchBillDetail.getGoodsid());
			saleoutDetail.setBranddept(dispatchBillDetail.getBranddept());
			//获取商品品牌编号 和 品牌业务员
			GoodsInfo goodsInfo = getAllGoodsInfoByID(dispatchBillDetail.getGoodsid());
			if(null!=goodsInfo){
				saleoutDetail.setGoodssort(goodsInfo.getDefaultsort());
				saleoutDetail.setBrandid(goodsInfo.getBrand());
				saleoutDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), saleout.getCustomerid()));
				//厂家业务员
				saleoutDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), saleout.getCustomerid()));
				//成本价
				saleoutDetail.setCostprice(dispatchBillDetail.getCostprice());
                //实际成本价 商品总成本价
				saleoutDetail.setRealcostprice(goodsInfo.getNewstorageprice());
				//商品供应商
				saleoutDetail.setSupplierid(goodsInfo.getDefaultsupplier());
			}
			saleoutDetail.setStorageid(saleout.getStorageid());
			saleoutDetail.setStoragelocationid(storageSummaryBatch.getStoragelocationid());
			saleoutDetail.setBatchno(storageSummaryBatch.getBatchno());
			saleoutDetail.setProduceddate(storageSummaryBatch.getProduceddate());
			saleoutDetail.setDeadline(storageSummaryBatch.getDeadline());
			saleoutDetail.setUnitid(dispatchBillDetail.getUnitid());
			saleoutDetail.setUnitname(dispatchBillDetail.getUnitname());
			saleoutDetail.setAuxunitid(dispatchBillDetail.getAuxunitid());
			saleoutDetail.setAuxunitname(dispatchBillDetail.getAuxunitname());
			
			//自定义字段
			saleoutDetail.setField01(dispatchBillDetail.getField01());
			saleoutDetail.setField02(dispatchBillDetail.getField02());
			saleoutDetail.setField03(dispatchBillDetail.getField03());
			saleoutDetail.setField04(dispatchBillDetail.getField04());
			saleoutDetail.setField05(dispatchBillDetail.getField05());
			saleoutDetail.setField06(dispatchBillDetail.getField06());
			saleoutDetail.setField07(dispatchBillDetail.getField07());
			saleoutDetail.setField08(dispatchBillDetail.getField08());
			//含税单价 无税单价 税种
			saleoutDetail.setTaxprice(dispatchBillDetail.getTaxprice());
			saleoutDetail.setNotaxprice(dispatchBillDetail.getNotaxprice());
			saleoutDetail.setTaxtype(dispatchBillDetail.getTaxtype());
			
			saleoutDetail.setUnitnum(unitnum);
			
			Map auxnummap = countGoodsInfoNumber(saleoutDetail.getGoodsid(), saleoutDetail.getAuxunitid(), saleoutDetail.getUnitnum());
			saleoutDetail.setAuxnum((BigDecimal) auxnummap.get("auxnum"));
			saleoutDetail.setAuxnumdetail((String) auxnummap.get("auxnumdetail"));
			//含税金额 = 含税单价*数量
			saleoutDetail.setTaxamount(saleoutDetail.getTaxprice().multiply(saleoutDetail.getUnitnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
			//无税金额 = 无税单价*数量
			saleoutDetail.setNotaxamount(saleoutDetail.getNotaxprice().multiply(saleoutDetail.getUnitnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
			//税额 = 含税金额-无税金额
			saleoutDetail.setTax(saleoutDetail.getTaxamount().subtract(saleoutDetail.getNotaxamount()));
			int i = saleoutMapper.addSaleOutDetail(saleoutDetail);
			return  i>0;
		}else{
			return false;
		}
	}
	@Override
	public PageData showSaleOutList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_storage_saleout", "t");
		pageMap.setQueryAlias("t");
		pageMap.setDataSql(dataSql);
		PageData pageData = new PageData(saleoutMapper.showSaleOutCount(pageMap),saleoutMapper.showSaleOutList(pageMap),pageMap);
		List<Saleout> list = pageData.getList();
		for(Saleout saleout : list){
			Personnel indoorPerson = getPersonnelById(saleout.getIndooruserid());
			if(null!=indoorPerson){
				saleout.setIndoorusername(indoorPerson.getName());
			}
			StorageInfo storageInfo = getStorageInfoByID(saleout.getStorageid());
			if(null!=storageInfo){
				saleout.setStoragename(storageInfo.getName());
			}
			Map map = new HashMap();
			map.put("id", saleout.getCustomerid());
			Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(map);
			if(null!=customer){
				saleout.setCustomername(customer.getName());
			}
			DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(saleout.getSalesdept());
			if(null!=departMent){
				saleout.setSalesdeptname(departMent.getName());
			}
			Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(saleout.getSalesuser());
			if(null!=personnel){
				saleout.setSalesusername(personnel.getName());
			}
			Personnel storager = getBaseFilesPersonnelMapper().getPersonnelInfo(saleout.getStorager());
			if(null!= storager){
				saleout.setStoragername(storager.getName());
			}
		}
		return pageData;
	}

	@Override
	public Map getSaleOutInfo(String id) throws Exception {
		Saleout saleout = saleoutMapper.getSaleOutInfo(id);
        if(null != saleout){
            Customer customer = getCustomerByID(saleout.getCustomerid());
            if(null!=customer){
                saleout.setCustomername(customer.getName());
            }
            saleout.setCustomerInfo(customer);
            Personnel personnel = getPersonnelById(saleout.getSalesuser());
            if(null!=personnel){
                saleout.setSalesusername(personnel.getName());
            }
            DepartMent departMent = getDepartmentByDeptid(saleout.getSalesdept());
            if(null!=departMent){
                saleout.setSalesdeptname(departMent.getName());
            }

            Map total = saleoutMapper.getSaleoutDetailTotal(id);
            if (total != null) {
                if (total.containsKey("taxamount")) {
                    saleout.setField01(total.get("taxamount").toString());
                }
                if (total.containsKey("notaxamount")) {
                    saleout.setField02(total.get("notaxamount").toString());
                }
                if (total.containsKey("tax")) {
                    saleout.setField03(total.get("tax").toString());
                }
            }
        }
		List<SaleoutDetail> detailList = saleoutMapper.getSaleOutDetailListSumDiscount(id);
		//判断多条相同商品 现存量问题 
		Map storageGoodsMap = new HashMap();
		for(SaleoutDetail saleoutDetail : detailList){
			GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(getGoodsInfoByID(saleoutDetail.getGoodsid()));
			StorageInfo storageInfo = getStorageInfoByID(saleoutDetail.getStorageid());
			if(null!=storageInfo){
				saleoutDetail.setStoragename(storageInfo.getName());
			}
			StorageLocation storageLocation = getStorageLocation(saleoutDetail.getStoragelocationid());
			if(null!=storageLocation){
				saleoutDetail.setStoragelocationname(storageLocation.getName());
			}
			TaxType taxType = getTaxType(saleoutDetail.getTaxtype());
			if(null!=taxType){
				saleoutDetail.setTaxtypename(taxType.getName());
			}
			//折扣显示处理
			if("1".equals(saleoutDetail.getIsdiscount())){
				goodsInfo.setBarcode(null);
				goodsInfo.setBoxnum(null);
				saleoutDetail.setUnitnum(null);
				saleoutDetail.setAuxnumdetail(null);
				saleoutDetail.setTaxprice(null);
				if("1".equals(saleoutDetail.getIsbranddiscount())){
					saleoutDetail.setGoodsid("");
					goodsInfo.setName(goodsInfo.getBrandName());
					saleoutDetail.setIsdiscount("2");
				}
				saleoutDetail.setIsenough("1");
			}else{
				String keyid = saleoutDetail.getSummarybatchid();
				StorageSummaryBatch storageSummaryBatch = null;
				if(storageGoodsMap.containsKey(keyid)){
					storageSummaryBatch = (StorageSummaryBatch) storageGoodsMap.get(keyid);
				}else{
					storageSummaryBatch = getStorageSummaryBatchById(saleoutDetail.getSummarybatchid());
				}
				if(null!=storageSummaryBatch && storageSummaryBatch.getExistingnum().compareTo(saleoutDetail.getUnitnum())>=0){
					saleoutDetail.setIsenough("1");
				}else{
					saleoutDetail.setIsenough("0");
				}
				if(null!=storageSummaryBatch){
					if(storageSummaryBatch.getExistingnum().compareTo(saleoutDetail.getUnitnum())>=0){
						storageSummaryBatch.setExistingnum(storageSummaryBatch.getExistingnum().subtract(saleoutDetail.getUnitnum()));
					}else{
						storageSummaryBatch.setExistingnum(BigDecimal.ZERO);
					}
					storageGoodsMap.put(keyid, storageSummaryBatch);
				}
			}
            goodsInfo.setItemno(getItemnoByGoodsAndStorage(goodsInfo.getId(),saleoutDetail.getStorageid()));
			saleoutDetail.setGoodsInfo(goodsInfo);
		}
		Map map = new HashMap();
		map.put("saleOut", saleout);
		map.put("saleOutDetail", detailList);
		return map;
	}
    @Override
    public Saleout getSaleOutInfoById(String id) throws Exception {
        Saleout saleout = saleoutMapper.getSaleOutInfo(id);
        if(null != saleout){
            Customer customer = getCustomerByID(saleout.getCustomerid());
            if(null!=customer){
                saleout.setCustomername(customer.getName());
            }
            saleout.setCustomerInfo(customer);
            Personnel personnel = getPersonnelById(saleout.getSalesuser());
            if(null!=personnel){
                saleout.setSalesusername(personnel.getName());
            }
            DepartMent departMent = getDepartmentByDeptid(saleout.getSalesdept());
            if(null!=departMent){
                saleout.setSalesdeptname(departMent.getName());
            }

            Map total = saleoutMapper.getSaleoutDetailTotal(id);
            if (total != null) {
                if (total.containsKey("taxamount")) {
                    saleout.setField01(total.get("taxamount").toString());
                }
                if (total.containsKey("notaxamount")) {
                    saleout.setField02(total.get("notaxamount").toString());
                }
                if (total.containsKey("tax")) {
                    saleout.setField03(total.get("tax").toString());
                }
            }
        }

        return saleout;
    }
	@Override
	public List<SaleoutDetail> getSaleOutDetailListSumDiscountByMap(Map map) throws Exception{
        List<SaleoutDetail> detailList = saleoutMapper.getSaleOutDetailListSumDiscountByMap(map);
        //判断多条相同商品 现存量问题
        Map storageGoodsMap = new HashMap();
        for(SaleoutDetail saleoutDetail : detailList){
            GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(getGoodsInfoByID(saleoutDetail.getGoodsid()));
            StorageInfo storageInfo = getStorageInfoByID(saleoutDetail.getStorageid());
            if(null!=storageInfo){
                saleoutDetail.setStoragename(storageInfo.getName());
            }
            StorageLocation storageLocation = getStorageLocation(saleoutDetail.getStoragelocationid());
            if(null!=storageLocation){
                saleoutDetail.setStoragelocationname(storageLocation.getName());
            }
            TaxType taxType = getTaxType(saleoutDetail.getTaxtype());
            if(null!=taxType){
                saleoutDetail.setTaxtypename(taxType.getName());
            }
            //折扣显示处理
            if("1".equals(saleoutDetail.getIsdiscount())){
                goodsInfo.setBarcode(null);
                goodsInfo.setBoxnum(null);
                saleoutDetail.setUnitnum(null);
                saleoutDetail.setAuxnumdetail(null);
                saleoutDetail.setTaxprice(null);
                if("1".equals(saleoutDetail.getIsbranddiscount())){
                    saleoutDetail.setGoodsid("");
                    goodsInfo.setName(goodsInfo.getBrandName());
                    saleoutDetail.setIsdiscount("2");
                }
                saleoutDetail.setIsenough("1");
            }else{
                String keyid = saleoutDetail.getSummarybatchid();
                StorageSummaryBatch storageSummaryBatch = null;
                if(storageGoodsMap.containsKey(keyid)){
                    storageSummaryBatch = (StorageSummaryBatch) storageGoodsMap.get(keyid);
                }else{
                    storageSummaryBatch = getStorageSummaryBatchById(saleoutDetail.getSummarybatchid());
                }
                if(null!=storageSummaryBatch && storageSummaryBatch.getExistingnum().compareTo(saleoutDetail.getUnitnum())>=0){
                    saleoutDetail.setIsenough("1");
                }else{
                    saleoutDetail.setIsenough("0");
                }
                if(null!=storageSummaryBatch){
                    if(storageSummaryBatch.getExistingnum().compareTo(saleoutDetail.getUnitnum())>=0){
                        storageSummaryBatch.setExistingnum(storageSummaryBatch.getExistingnum().subtract(saleoutDetail.getUnitnum()));
                    }else{
                        storageSummaryBatch.setExistingnum(BigDecimal.ZERO);
                    }
                    storageGoodsMap.put(keyid, storageSummaryBatch);
                }
            }
            saleoutDetail.setGoodsInfo(goodsInfo);
        }
        return detailList;
    }

    @Override
    public List<Map> getSaleOutSumData(List<String> ids) throws Exception {
        return saleoutMapper.getSaleOutSumData(ids);
    }

    @Override
    public Saleout getBaseSaleOutInfo(String id) throws Exception {
        return saleoutMapper.getSaleOutInfo(id);
    }

    /**
     * 获取销售发货单详细信息 数据权限内
     *
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jun 5, 2013
     */
    @Override
    public Saleout getSaleOutInfoWithDataSql(String id) throws Exception {
        String dataSql = getDataAccessRule("t_storage_saleout", null);
        Saleout saleout = saleoutMapper.getSaleOutInfoWithDataSql(id,dataSql);
        return saleout;
    }

    @Override
	public Map getSaleOutInfoWithoutDiscount(String id) throws Exception {
		Saleout saleout = saleoutMapper.getSaleOutInfo(id);
		List<SaleoutDetail> detailList = saleoutMapper.getSaleOutDetailList(id);
//		//减去折扣后的 发货单明细列表
//		List<SaleoutDetail> dataList = new ArrayList();
//		//折扣列表
//		List<SaleoutDetail> discountList = new ArrayList();
//		if(null!=saleout){
//			Customer customer = getCustomerByID(saleout.getCustomerid());
//			if(null!=customer){
//				saleout.setCustomername(customer.getName());
//			}
//		}
//		for(SaleoutDetail saleoutDetail : detailList){
//			saleoutDetail.setGoodsInfo(getGoodsInfoByID(saleoutDetail.getGoodsid()));
//			StorageInfo storageInfo = getStorageInfoByID(saleoutDetail.getStorageid());
//			if(null!=storageInfo){
//				saleoutDetail.setStoragename(storageInfo.getName());
//			}
//			StorageLocation storageLocation = getStorageLocation(saleoutDetail.getStoragelocationid());
//			if(null!=storageLocation){
//				saleoutDetail.setStoragelocationname(storageLocation.getName());
//			}
//			TaxType taxType = getTaxType(saleoutDetail.getTaxtype());
//			if(null!=taxType){
//				saleoutDetail.setTaxtypename(taxType.getName());
//			}
//			if("0".equals(saleoutDetail.getIsdiscount())){
//				dataList.add(saleoutDetail);
//			}else{
//				discountList.add(saleoutDetail);
//			}
//		}
//		//计算折扣后的发货明细信息
//		for(SaleoutDetail saleoutDetail : dataList){
//			for(SaleoutDetail dissaleoutDetail : discountList){
//				if(saleoutDetail.getGoodsid().equals(dissaleoutDetail.getGoodsid())){
//					saleoutDetail.setTaxamount(saleoutDetail.getTaxamount().add(dissaleoutDetail.getTaxamount()));
//					//无税金额
//					BigDecimal notaxamount = getNotaxAmountByTaxAmount(saleoutDetail.getTaxamount(), saleoutDetail.getTaxtype());
//					saleoutDetail.setNotaxamount(notaxamount);
//					saleoutDetail.setTax(saleoutDetail.getTaxamount().subtract(notaxamount));
//					saleoutDetail.setRemark("折扣金额:"+dissaleoutDetail.getTaxamount().setScale(2, BigDecimal.ROUND_HALF_UP).negate());
//					saleoutDetail.setDiscountamount(dissaleoutDetail.getTaxamount().negate());
//				}
//			}
//		}
		Map map = new HashMap();
		map.put("saleOut", saleout);
		map.put("saleOutDetail", detailList);
		return map;
	}

	@Override
	public Map addSaleOut(Saleout saleout, List<SaleoutDetail> detailList)
			throws Exception {
		Map map = new HashMap();
		boolean flag = true;
		String msg = "";
		SysUser sysUser = getSysUser();
		saleout.setAdduserid(sysUser.getUserid());
		saleout.setAddusername(sysUser.getName());
		saleout.setAdddeptid(sysUser.getDepartmentid());
		saleout.setAdddeptname(sysUser.getDepartmentname());
		//状态为保存状态时 判断可用量是否足够
		if("2".equals(saleout.getStatus())){
			for(SaleoutDetail saleoutDetail : detailList){
				if("0".equals(saleoutDetail.getIsdiscount())){
					StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(saleoutDetail.getSummarybatchid());
					if(storageSummaryBatch.getUsablenum().compareTo(saleoutDetail.getUnitnum())==-1){
						GoodsInfo goodsInfo = getAllGoodsInfoByID(saleoutDetail.getGoodsid());
						if("1".equals(goodsInfo.getIsbatch())){
							msg = "商品:"+goodsInfo.getName()+",在批次:"+storageSummaryBatch.getBatchno()+"中,数量不足，该批次只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量";
						}else if("1".equals(goodsInfo.getIsstoragelocation())){
							StorageLocation storageLocation = getStorageLocation(storageSummaryBatch.getStoragelocationid());
							msg = "商品:"+goodsInfo.getName()+",在库位:"+storageLocation.getName()+"中,数量不足，该库位只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量";
						}else{
							StorageInfo storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
							msg = "商品:"+goodsInfo.getName()+",在仓库:"+storageInfo.getName()+"中,数量不足，该仓库只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量";
						}
						flag = false;
						break;
					}
				}
			}
		}
		if(flag){
			BigDecimal initsendamount = BigDecimal.ZERO;
			BigDecimal initsendcostamount = BigDecimal.ZERO;
			BigDecimal sendamount = BigDecimal.ZERO;
			BigDecimal sendnotaxamount = BigDecimal.ZERO;
			BigDecimal sendcostamount = BigDecimal.ZERO;
			for(SaleoutDetail saleoutDetail : detailList){
				if("0".equals(saleoutDetail.getIsdiscount())){
					StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(saleoutDetail.getSummarybatchid());
					if(null!=storageSummaryBatch){
						//保存状态时，更新待发量
						if("2".equals(saleout.getStatus())){
							//更新待发量
							updateStorageSummaryWaitnum(storageSummaryBatch.getId(), saleoutDetail.getUnitnum());
						}
						saleoutDetail.setSaleoutid(saleout.getId());
					}
				}
				//初始单价
				saleoutDetail.setInittaxprice(saleoutDetail.getTaxprice());
				saleoutDetail.setSaleoutid(saleout.getId());
				saleoutDetail.setStorageid(saleout.getStorageid());
				//获取商品品牌编号 和 品牌业务员
				GoodsInfo goodsInfo = getAllGoodsInfoByID(saleoutDetail.getGoodsid());
				if(null!=goodsInfo){
					saleoutDetail.setBrandid(goodsInfo.getBrand());
					//成本价
					saleoutDetail.setCostprice(getGoodsCostprice(saleout.getStorageid(),goodsInfo));
                    //实际成本价 商品总成本价
					saleoutDetail.setRealcostprice(goodsInfo.getNewstorageprice());
					Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
					if(null!=goodsInfo.getBrand()){
						saleoutDetail.setBranddept(saleoutDetail.getBranddept());
					}
					//商品供应商
					saleoutDetail.setSupplierid(goodsInfo.getDefaultsupplier());
				}
				saleoutMapper.addSaleOutDetail(saleoutDetail);
				
				initsendamount = initsendamount.add(saleoutDetail.getInittaxamount());
				initsendcostamount = initsendcostamount.add(saleoutDetail.getCostprice().multiply(saleoutDetail.getInitnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
				sendamount = sendamount.add(saleoutDetail.getTaxamount());
				sendnotaxamount = sendnotaxamount.add(saleoutDetail.getNotaxamount());
				sendcostamount = sendcostamount.add(saleoutDetail.getUnitnum().multiply(saleoutDetail.getCostprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
			}
			saleout.setInitsendamount(initsendamount);
			saleout.setInitsendcostamount(initsendcostamount);
			saleout.setSendamount(sendamount);
			saleout.setSendcostamount(sendcostamount);
			saleout.setSendnotaxamount(sendnotaxamount);
			//添加销售出库单
			int i = saleoutMapper.addSaleOut(saleout);
			map.put("flag", i>0);
			map.put("msg", "保存成功");
			return map;
		}else{
			map.put("flag", flag);
			map.put("msg", msg);
			return map;
		}
		
	}

	@Override
	public boolean deleteSaleOut(String id) throws Exception {
		Saleout saleout = saleoutMapper.getSaleOutInfo(id);
		if(null==saleout ||(!"1".equals(saleout.getStatus()) && !"2".equals(saleout.getStatus()))){
			return false;
		}
		List<SaleoutDetail> detailList = saleoutMapper.getSaleOutDetailList(id);
		int i = 0;
		boolean flag = true;
		//删除明细 回滚待发量
		for(SaleoutDetail saleoutDetail : detailList){
			//保存状态时，回滚待发量
			if("2".equals(saleout.getStatus())){
				StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(saleoutDetail.getSummarybatchid());
				if(null!=storageSummaryBatch && flag){
					//回滚待发量
					flag = rollBackStorageSummaryWaitnum(storageSummaryBatch.getId(), saleoutDetail.getUnitnum());
				}
			}
		}
		boolean delFlag = false;
		if(flag){
			saleoutMapper.deleteSaleOutDetailBySaleoutid(saleout.getId());
			//删除销售出库单
			int j = saleoutMapper.deleteSaleOut(id);
			//来源类型为销售发货通知单时
			if("1".equals(saleout.getSourcetype())){
				
				//更新上游单据状态
				boolean flag1 = salesOutService.updateDispatchBillRefer("0", saleout.getSourceid());
			}
			delFlag = j>0;
		}else{
			throw new Exception("待发量回滚失败");
		}
		return delFlag;
	}
	/**
	 * 根据销售发货通知单明细编号获取该商品在出库单中的数量
	 * @param list
	 * @param dispatchbilldetailid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 8, 2013
	 */
	public BigDecimal getGoodsNumByDispatchbilldetailidFromSaleOutDetailList(List<SaleoutDetail> list,String dispatchbilldetailid) throws Exception{
		BigDecimal goodsnum = new BigDecimal(0);
		for(SaleoutDetail saleoutDetail : list){
			if("0".equals(saleoutDetail.getIsdiscount())){
				if(dispatchbilldetailid.equals(saleoutDetail.getDispatchbilldetailid())){
					goodsnum = goodsnum.add(saleoutDetail.getUnitnum());
				}
			}
		}
		return goodsnum;
	}
	@Override
	public Map editSaleOut(Saleout saleout, List<SaleoutDetail> detailList)
			throws Exception {
		Map map = new HashMap();
		boolean flag = true;
		boolean editFlag = false;
		String msg = "";
		//判断当前发货单是否能修改
		Saleout oldSaleout = saleoutMapper.getSaleOutInfo(saleout.getId());
		if(null==oldSaleout || "3".equals(oldSaleout.getStatus()) || "4".equals(oldSaleout.getStatus())){
			map.put("flag", false);
			map.put("msg", "单据已审核，请刷新后再操作。");
			return map;
		}
		//状态为保存状态时 判断可用量是否足够
		if("2".equals(saleout.getStatus()) || "6".equals(saleout.getStatus())){
			if(flag){
				for(SaleoutDetail saleoutDetail : detailList){
					if("0".equals(saleoutDetail.getIsdiscount())){
						if(null!=saleoutDetail.getId()){
							SaleoutDetail oldsaleoutDetail = saleoutMapper.getSaleoutDetailById(saleoutDetail.getId()+"");
							//修改后的数量大于修改前的数量 判断可用量是否足够
							if(null!=oldsaleoutDetail && saleoutDetail.getUnitnum().compareTo(oldsaleoutDetail.getUnitnum())==1){
								StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(saleoutDetail.getSummarybatchid());
								//批次现存量中的可用量是否大于 修改后数量-修改前数量
								if(storageSummaryBatch.getUsablenum().compareTo(saleoutDetail.getUnitnum().subtract(oldsaleoutDetail.getUnitnum()))==-1){
									GoodsInfo goodsInfo = getAllGoodsInfoByID(saleoutDetail.getGoodsid());
									if("1".equals(goodsInfo.getIsbatch())){
										msg += "商品:"+goodsInfo.getName()+",在批次:"+storageSummaryBatch.getBatchno()+"中,数量不足，该批次只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
									}else if("1".equals(goodsInfo.getIsstoragelocation())){
										StorageLocation storageLocation = getStorageLocation(storageSummaryBatch.getStoragelocationid());
										msg += "商品:"+goodsInfo.getName()+",在库位:"+storageLocation.getName()+"中,数量不足，该库位只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
									}else{
										StorageInfo storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
										msg += "商品:"+goodsInfo.getName()+",在仓库:"+storageInfo.getName()+"中,数量不足，该仓库只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
									}
									flag = false;
								}
							}
						}else{
							StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(saleoutDetail.getSummarybatchid());
							if(storageSummaryBatch.getUsablenum().compareTo(saleoutDetail.getUnitnum())==-1){
								GoodsInfo goodsInfo = getAllGoodsInfoByID(saleoutDetail.getGoodsid());
								if("1".equals(goodsInfo.getIsbatch())){
									msg += "商品:"+goodsInfo.getName()+",在批次:"+storageSummaryBatch.getBatchno()+"中,数量不足，该批次只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
								}else if("1".equals(goodsInfo.getIsstoragelocation())){
									StorageLocation storageLocation = getStorageLocation(storageSummaryBatch.getStoragelocationid());
									msg += "商品:"+goodsInfo.getName()+",在库位:"+storageLocation.getName()+"中,数量不足，该库位只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
								}else{
									StorageInfo storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
									msg += "商品:"+goodsInfo.getName()+",在仓库:"+storageInfo.getName()+"中,数量不足，该仓库只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
								}
								flag = false;
							}
						}
					}
				}
			}
		}
		if(flag){
			//修改前出库单信息
			if("2".equals(oldSaleout.getStatus()) ||"6".equals(oldSaleout.getStatus())){
				List<SaleoutDetail> oldSaleoutDetailList = saleoutMapper.getSaleOutDetailList(saleout.getId());
				for(SaleoutDetail saleoutDetail : oldSaleoutDetailList){
					if("0".equals(saleoutDetail.getIsdiscount())){
						StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(saleoutDetail.getSummarybatchid());
						//回滚修改前的待发量
						rollBackStorageSummaryWaitnum(storageSummaryBatch.getId(), saleoutDetail.getUnitnum());
					}
				}
			}
			//删除明细
			saleoutMapper.deleteSaleOutDetailBySaleoutid(saleout.getId());
			
			BigDecimal initsendamount = BigDecimal.ZERO;
			BigDecimal initsendcostamount = BigDecimal.ZERO;
			BigDecimal sendamount = BigDecimal.ZERO;
			BigDecimal sendnotaxamount = BigDecimal.ZERO;
			BigDecimal sendcostamount = BigDecimal.ZERO;
			
			//品牌折扣列表
			List addDataList = new ArrayList();
			//添加明细
			for(SaleoutDetail saleoutDetail : detailList){
				//不是品牌折扣
				if(!"2".equals(saleoutDetail.getIsdiscount())){
					if("0".equals(saleoutDetail.getIsdiscount())){
						StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(saleoutDetail.getSummarybatchid());
						if(null!=storageSummaryBatch){
							//保存状态时，更新待发量
							if("2".equals(saleout.getStatus()) || "6".equals(saleout.getStatus())){
								updateStorageSummaryWaitnum(storageSummaryBatch.getId(), saleoutDetail.getUnitnum());
							}
						}
					}
					saleoutDetail.setSaleoutid(saleout.getId());
					saleoutDetail.setStorageid(saleout.getStorageid());
					//获取商品品牌编号 和 品牌业务员
					GoodsInfo goodsInfo = getAllGoodsInfoByID(saleoutDetail.getGoodsid());
					if(null!=goodsInfo){
						//计算辅单位数量
						Map auxmap = countGoodsInfoNumber(saleoutDetail.getGoodsid(), saleoutDetail.getAuxunitid(), saleoutDetail.getUnitnum());
						if(auxmap.containsKey("auxnum")){
							saleoutDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
						}
						saleoutDetail.setGoodssort(goodsInfo.getDefaultsort());
						saleoutDetail.setBrandid(goodsInfo.getBrand());
						//成本价
						saleoutDetail.setCostprice(getGoodsCostprice(saleout.getStorageid(),goodsInfo));
                        //实际成本价 商品总成本价
						saleoutDetail.setRealcostprice(goodsInfo.getNewstorageprice());
						saleoutDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), saleout.getCustomerid()));
						//厂家业务员
						saleoutDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), saleout.getCustomerid()));
						Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
						if(null!=brand){
							saleoutDetail.setBranddept(saleoutDetail.getBranddept());
						}
						//商品供应商
						saleoutDetail.setSupplierid(goodsInfo.getDefaultsupplier());
					}
					
					initsendamount = initsendamount.add(saleoutDetail.getInittaxamount());
					initsendcostamount = initsendcostamount.add(saleoutDetail.getCostprice().multiply(saleoutDetail.getInitnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					sendamount = sendamount.add(saleoutDetail.getTaxamount());
					sendnotaxamount = sendnotaxamount.add(saleoutDetail.getNotaxamount());
					sendcostamount = sendcostamount.add(saleoutDetail.getUnitnum().multiply(saleoutDetail.getCostprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					
					addDataList.add(saleoutDetail);
				}else{
					//是品牌折扣
					List<DispatchBillDetail> dispatchBillDetailList = salesOutService.getDispatchBillDetailBrandDiscountList(saleout.getSourceid(), saleoutDetail.getBrandid());
					for(DispatchBillDetail dispatchBillDetail : dispatchBillDetailList){
						//销售出库单明细基本信息
						SaleoutDetail newsaleoutDetail = new SaleoutDetail();
						newsaleoutDetail.setIsdiscount("1");
						newsaleoutDetail.setIsbranddiscount(dispatchBillDetail.getIsbranddiscount());
						newsaleoutDetail.setSaleoutid(saleout.getId());
						newsaleoutDetail.setDispatchbillid(dispatchBillDetail.getBillid());
						newsaleoutDetail.setDispatchbilldetailid(dispatchBillDetail.getId());
						newsaleoutDetail.setRemark(dispatchBillDetail.getRemark());
						newsaleoutDetail.setGoodsid(dispatchBillDetail.getGoodsid());
						newsaleoutDetail.setSeq(dispatchBillDetail.getSeq());
						//成本价
						newsaleoutDetail.setCostprice(dispatchBillDetail.getCostprice());
						//获取商品品牌编号 和 品牌业务员
						GoodsInfo goodsInfo = getAllGoodsInfoByID(dispatchBillDetail.getGoodsid());
						if(null!=goodsInfo){
							newsaleoutDetail.setGoodssort(goodsInfo.getDefaultsort());
							newsaleoutDetail.setBrandid(goodsInfo.getBrand());
							newsaleoutDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), saleout.getCustomerid()));
							//厂家业务员
							newsaleoutDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), saleout.getCustomerid()));
							//获取品牌部门
							Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
							if(null!=brand){
								newsaleoutDetail.setBranddept(brand.getDeptid());
							}
                            //实际成本价 商品总成本价
							newsaleoutDetail.setRealcostprice(goodsInfo.getNewstorageprice());
							//商品供应商
							newsaleoutDetail.setSupplierid(goodsInfo.getDefaultsupplier());
						}
						newsaleoutDetail.setStorageid(saleout.getStorageid());
						newsaleoutDetail.setUnitid(dispatchBillDetail.getUnitid());
						newsaleoutDetail.setUnitname(dispatchBillDetail.getUnitname());
						newsaleoutDetail.setAuxunitid(dispatchBillDetail.getAuxunitid());
						newsaleoutDetail.setAuxunitname(dispatchBillDetail.getAuxunitname());
						
						//自定义字段
						newsaleoutDetail.setField01(dispatchBillDetail.getField01());
						newsaleoutDetail.setField02(dispatchBillDetail.getField02());
						newsaleoutDetail.setField03(dispatchBillDetail.getField03());
						newsaleoutDetail.setField04(dispatchBillDetail.getField04());
						newsaleoutDetail.setField05(dispatchBillDetail.getField05());
						newsaleoutDetail.setField06(dispatchBillDetail.getField06());
						newsaleoutDetail.setField07(dispatchBillDetail.getField07());
						newsaleoutDetail.setField08(dispatchBillDetail.getField08());
						//含税单价 无税单价 税种
						newsaleoutDetail.setTaxprice(dispatchBillDetail.getTaxprice());
						newsaleoutDetail.setNotaxprice(dispatchBillDetail.getNotaxprice());
						newsaleoutDetail.setTaxtype(dispatchBillDetail.getTaxtype());
						
						newsaleoutDetail.setInittaxprice(dispatchBillDetail.getTaxprice());
						
						newsaleoutDetail.setInitnum(BigDecimal.ZERO);
						newsaleoutDetail.setUnitnum(BigDecimal.ZERO);
						newsaleoutDetail.setAuxinitnum(BigDecimal.ZERO);
						newsaleoutDetail.setAuxnum(BigDecimal.ZERO);
						newsaleoutDetail.setAuxremainder(BigDecimal.ZERO);
						newsaleoutDetail.setAuxnumdetail("");
						newsaleoutDetail.setInittaxamount(dispatchBillDetail.getTaxamount());
						newsaleoutDetail.setInitnotaxamount(dispatchBillDetail.getNotaxamount());
						newsaleoutDetail.setTaxamount(dispatchBillDetail.getTaxamount());
						newsaleoutDetail.setNotaxamount(dispatchBillDetail.getNotaxamount());
						//税额 = 含税金额-无税金额
						newsaleoutDetail.setTax(dispatchBillDetail.getTax());
						
						initsendamount = initsendamount.add(newsaleoutDetail.getInittaxamount());
						initsendcostamount = initsendcostamount.add(newsaleoutDetail.getCostprice().multiply(newsaleoutDetail.getInitnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
						sendamount = sendamount.add(newsaleoutDetail.getTaxamount());
						sendnotaxamount = sendnotaxamount.add(newsaleoutDetail.getNotaxamount());
						sendcostamount = sendcostamount.add(newsaleoutDetail.getUnitnum().multiply(newsaleoutDetail.getCostprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
						
						addDataList.add(newsaleoutDetail);
					}
				}
			}
			if(null!=addDataList && addDataList.size()>0){
				//添加明细
				saleoutMapper.addSaleOutDetailList(addDataList);
			}
			saleout.setInitsendamount(initsendamount);
			saleout.setInitsendcostamount(initsendcostamount);
			saleout.setSendamount(sendamount);
			saleout.setSendcostamount(sendcostamount);
			saleout.setSendnotaxamount(sendnotaxamount);
			
			SysUser sysUser = getSysUser();
			saleout.setModifyuserid(sysUser.getUserid());
			saleout.setModifyusername(sysUser.getName());
			
			//修改销售出库单
			int i = saleoutMapper.editSaleOut(saleout);
			map.put("flag", i>0);
			map.put("msg", msg);
			map.put("editFlag", editFlag);
			
			return map;
		}else{
			map.put("flag", flag);
			map.put("msg", msg);
			return map;
		}
		
	}

	@Override
	public Map auditSaleOut(String id) throws Exception {
		boolean flag = true;
		String msg = "";
		String receiptid = "";
		Saleout saleout = saleoutMapper.getSaleOutInfo(id);
		//审核业务日期
		String auditBusinessdate = getAuditBusinessdate(saleout.getBusinessdate());
        saleout.setBusinessdate(auditBusinessdate);
        Date businessdate = CommonUtils.stringToDate(saleout.getBusinessdate());
		//来源类型为销售发货通知单
		if(null!=saleout && ("2".equals(saleout.getStatus()) || "6".equals(saleout.getStatus()))){
			if(null==saleout.getBusinessdate() || !saleout.getBusinessdate().equals(CommonUtils.getTodayDataStr())){
				msg += "业务日期不是当天日期，审核后自动变更";
			}
			//清除数量为0的明细
			saleoutMapper.deleteSaleOutDetailZeroBySaleoutid(id);

			//按商品合计数量 用来判断现存量是否足够
			List<SaleoutDetail> saleoutDetailSumList = saleoutMapper.getSaleOutDetailGoodsSumList(id);
			for(SaleoutDetail saleoutDetail : saleoutDetailSumList){
				//不是折扣的明细数据
				if("0".equals(saleoutDetail.getIsdiscount())){
					if(null==saleoutDetail.getSummarybatchid() || "".equals(saleoutDetail.getSummarybatchid())){
						GoodsInfo goodsInfo = getAllGoodsInfoByID(saleoutDetail.getGoodsid());
						msg += "商品:"+goodsInfo.getName()+"未指定仓库发货，请先配置发货情况";
						flag = false;
					}else{
						StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(saleoutDetail.getSummarybatchid());
						if(null!=storageSummaryBatch && storageSummaryBatch.getExistingnum().compareTo(saleoutDetail.getUnitnum())>=0){
						}else{
							GoodsInfo goodsInfo = getAllGoodsInfoByID(saleoutDetail.getGoodsid());
							msg += "商品:["+goodsInfo.getId()+"]"+goodsInfo.getName()+"在仓库中现存量不足；";
							flag = false;
						}
					}
				}
			}
			if(flag){
                //仓库是否独立核算 0否1是 默认否
                String isStorageAccount = getStorageIsAccount(saleout.getStorageid());
                //更新发货单出库的成本价
                saleoutMapper.updateSaleOutDetailCostprice(saleout.getId(),isStorageAccount);
                List<SaleoutDetail> saleoutDetailList = saleoutMapper.getSaleOutDetailList(id);
				for(SaleoutDetail saleoutDetail : saleoutDetailList){
					//不是折扣的明细数据
					if("0".equals(saleoutDetail.getIsdiscount())){
						StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(saleoutDetail.getSummarybatchid());
						if(null!=storageSummaryBatch){
							//更新现存量信息
							boolean sendflag = sendStorageSummaryWaitnum(storageSummaryBatch.getId(), saleoutDetail.getUnitnum(),"saleout",id);
							if(sendflag){
							    // 更新仓库商品成本价
                                updateStoragePriceBySubtract(saleout.getId(),saleoutDetail.getId()+"",saleout.getStorageid(),
                                            saleoutDetail.getGoodsid(),
                                            saleoutDetail.getUnitnum(),
                                            saleoutDetail.getCostprice(), true);
                            }
						}
					}
				}

                SysUser sysUser = getSysUser();
				if(null==sysUser){
					sysUser = getSysUserById(saleout.getAdduserid());
				}
				saleout.setAudittime(new Date());
				
				//出库单明细根据发货通知单编号和明细编号合计
				List saleOutDetailSumList = saleoutMapper.getSaleOutDetailSumList(id);
				//回写发货通知单
				salesOutService.updateDispatchBillDetailBack(saleOutDetailSumList);
				//明细为空的发货单，审核后直接关闭 不生成回单
				if(null!=saleoutDetailList && saleoutDetailList.size()>0){
					//生成回单
					receiptid = salesOutService.addReceiptAuto(saleout,  saleoutDetailList);
                    String isrefere = "1";
					if(null==receiptid){
                        isrefere = "0";
					}

                    //判断客户是否拥有品牌结算方式
                    boolean isCustomerHasBrandSettle = isCustomerHasBrandSettle(saleout.getCustomerid());
                    if(isCustomerHasBrandSettle){
                        //获取发货单中的品牌列表
                        List<String> brandList = saleoutMapper.getSaleOutBrandListById(saleout.getId());
                        if(null!=brandList && brandList.size()>0){
                            if(brandList.size()==1){
                                String brandid = brandList.get(0);
                                String duefromdate = getReceiptDateBySettlement(businessdate,saleout.getCustomerid(),brandid);
                                saleoutMapper.updateSaleOutDetailDuefromdate(saleout.getId(),brandid,duefromdate);
                                saleoutMapper.updateReceiptDetailDuefromdateByOrderid(saleout.getSaleorderid(),brandid,duefromdate);
                                int j = saleoutMapper.auditSaleOut(id, isrefere,sysUser.getUserid(), sysUser.getName(),duefromdate,auditBusinessdate);
                                saleoutMapper.updateReceiptDuefromdateByOrderid(saleout.getSaleorderid(),duefromdate);
                                flag = j>0;
                            }else{
                                for(String brandid : brandList){
                                    String duefromdate = getReceiptDateBySettlement(businessdate,saleout.getCustomerid(),brandid);
                                    saleoutMapper.updateSaleOutDetailDuefromdate(saleout.getId(),brandid,duefromdate);
                                    saleoutMapper.updateReceiptDetailDuefromdateByOrderid(saleout.getSaleorderid(),brandid,duefromdate);
                                }
                                String billDuefromdate = getReceiptDateBySettlement(businessdate,saleout.getCustomerid(),null);
                                int j = saleoutMapper.auditSaleOut(id, isrefere,sysUser.getUserid(), sysUser.getName(),billDuefromdate,auditBusinessdate);
                                saleoutMapper.updateReceiptDuefromdateByOrderid(saleout.getSaleorderid(),billDuefromdate);
                                flag = j>0;
                            }
                        }else{
                            String billDuefromdate = getReceiptDateBySettlement(businessdate,saleout.getCustomerid(),null);
                            saleoutMapper.updateSaleOutDetailDuefromdate(saleout.getId(),null,billDuefromdate);
                            saleoutMapper.updateReceiptDetailDuefromdateByOrderid(saleout.getSaleorderid(),null,billDuefromdate);
                            int j = saleoutMapper.auditSaleOut(id, isrefere,sysUser.getUserid(), sysUser.getName(),billDuefromdate,auditBusinessdate);
                            saleoutMapper.updateReceiptDuefromdateByOrderid(saleout.getSaleorderid(),billDuefromdate);
                            flag = j>0;
                        }
                    }else{
                        String billDuefromdate = getReceiptDateBySettlement(businessdate,saleout.getCustomerid(),null);
                        saleoutMapper.updateSaleOutDetailDuefromdate(saleout.getId(),null,billDuefromdate);
                        saleoutMapper.updateReceiptDetailDuefromdateByOrderid(saleout.getSaleorderid(),null,billDuefromdate);
                        int j = saleoutMapper.auditSaleOut(id, isrefere,sysUser.getUserid(), sysUser.getName(),billDuefromdate,auditBusinessdate);
                        saleoutMapper.updateReceiptDuefromdateByOrderid(saleout.getSaleorderid(),billDuefromdate);
                        flag = j>0;
                    }
                    if(flag){
                        //销售发货回单是否自动审核 1是0否
                        String isReceiptAuditAuto = getSysParamValue("IsReceiptAuditAuto");
                        if("1".equals(isReceiptAuditAuto) && StringUtils.isNotEmpty(id)) {
                            salesOutService.auditReceipt(receiptid);
                        }
                    }
				}else{
					String duefromdate = getReceiptDateBySettlement(businessdate,saleout.getCustomerid(),null);
					int j = saleoutMapper.auditSaleOutClose(id, "1",sysUser.getUserid(), sysUser.getName(),duefromdate,auditBusinessdate);
					flag = j>0;
				}
				//判断来源单据编号一致的发货单是否全部审核通过
				List<Saleout> sourcelist = saleoutMapper.getSaleOutInfoBySourceid(saleout.getSourceid());
				boolean cloaseFLag = true;
				for(Saleout saleoutSoruce : sourcelist){
					if(!("3".equals(saleoutSoruce.getStatus()) || "4".equals(saleoutSoruce.getStatus()))){
						cloaseFLag = false;
					}
				}
				if(cloaseFLag &&"1".equals(saleout.getSourcetype())){
					//关闭发货通知单
					salesOutService.updateDispatchBillClose(saleout.getSourceid(),saleout.getSaleorderid());
				}
				if(!flag){
					//抛出异常 回滚数据
					throw new Exception("审核失败");
				}
			}
		}else{
			msg = "发货单:"+id+"已审核或单据不存在";
			flag = false;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("receiptid", receiptid);
		return map;
	}
	@Override
	public Map oppauditSaleOut(String id) throws Exception{
		Map map = new HashMap();
		boolean flag = false;
		Saleout saleout = saleoutMapper.getSaleOutInfo(id);
		//发货单是否能反审
//		String auditDate = CommonUtils.dataToStr(saleout.getAudittime(), "yyyy-MM-dd");
//		String nowDate = CommonUtils.getTodayDataStr();
//		if(!nowDate.equals(auditDate)){
//			map.put("flag", flag);
//			map.put("oppflag", false);
//			return map;
//		}
		if(null!=saleout && "3".equals(saleout.getStatus())){
			//反审 判断下游单据是否有回单存在
			boolean downflag = salesOutService.deleteReceipt(saleout.getId(),saleout.getSaleorderid());
			if(downflag){
				int i = 0;
				//反审后 回滚现存量信息
				List<SaleoutDetail> list = saleoutMapper.getSaleOutDetailList(saleout.getId());
				for(SaleoutDetail saleoutDetail : list){
					//不是折扣的明细数据
					if("0".equals(saleoutDetail.getIsdiscount())){
						StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(saleoutDetail.getSummarybatchid());
						boolean rollFlag = rollBackSendStorageSummaryWaitnum(storageSummaryBatch.getId(), saleoutDetail.getUnitnum(), "saleout", saleout.getId());
						if(rollFlag){
                            //反审时，商品的出库时的成本价 可能与当前的成本价不一致 需要重新计算成本价
                            // 更新商品成本价
                            updateGoodsPriceByAdd(saleout.getStorageid(),saleoutDetail.getGoodsid(),saleoutDetail.getUnitnum(),saleoutDetail.getCostprice(), true, false,false,saleout.getId(),saleoutDetail.getId()+"");
							i++;
						}
					}else{
						i++;
					}
				}
				if("1".equals(saleout.getSourcetype())){
                    //打开发货通知单
                    //salesOutService.updateDispatchBillOpen(saleout.getId());
                    //出库单明细根据发货通知单编号和明细编号合计
                    List saleOutDetailSumList = saleoutMapper.getSaleOutDetailSumList(saleout.getId());
                    //回写发货通知单
                    salesOutService.updateClearDispatchBillDetailBack(saleOutDetailSumList);
                    salesOutService.updateDispatchBillOpen(saleout.getSourceid(),saleout.getSaleorderid());
                }
				SysUser sysUser = getSysUser();
				int j = saleoutMapper.oppauditSaleOut(saleout.getId(),sysUser.getUserid(), sysUser.getName());
				flag = j>0;
				if(!flag){
					//抛出异常 回滚数据
					throw new Exception("反审失败");
				}
			}
		}
        //反审时判断是否大单发货，若是，则改为不为大单发货“0”
        if(flag && "1".equals(saleout.getIsbigsaleout())){
            saleoutMapper.updateIsBigSaleOut("0",id);
        }
		map.put("flag", flag);
		return map;
	}

    /**
     * 清除发货单核对人信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Map deleteSaleoutCheckuser(String id) throws Exception {
        Map map = new HashMap();
        boolean flag = false;
        String msg = "";
        Saleout saleout = saleoutMapper.getSaleOutInfo(id);
        if(null!=saleout && !"4".equals(saleout.getStatus())){
            flag = saleoutMapper.updateSaleoutCheckClearFlag(id)>0;
        }else{
            msg = "发货单"+id+"，不存在或者已经关闭";
        }
        map.put("flag",flag);
        map.put("msg",msg);
        return map;
    }

    @Override
	public List showDispatchBillDetailListBySourceid(String sourceid)
			throws Exception {
		DispatchBill dispatchBill = salesOutService.getDispatchBill(sourceid);
		if(null!=dispatchBill){
			List<DispatchBillDetail> list = dispatchBill.getBillDetailList();
			return list;
		}else{
			return null;
		}
	}
	@Override
	public List showDispatchBillDetailList(String saleoutid,
			String dispatchBillid) throws Exception {
		Saleout saleout = saleoutMapper.getSaleOutInfo(saleoutid);
		DispatchBill dispatchBill = salesOutService.getDispatchBill(dispatchBillid);
		if(null!=dispatchBill){
			List<DispatchBillDetail> list = dispatchBill.getBillDetailList();
			List<DispatchBillDetail> detailAddList = new ArrayList();
			for(DispatchBillDetail dispatchBillDetail : list){
				GoodsInfo goodsInfo = getAllGoodsInfoByID(dispatchBillDetail.getGoodsid());
				detailAddList.add(dispatchBillDetail);
			}
			return detailAddList;
		}else{
			return new ArrayList();
		}
	}

	@Override
	public Map addSaleOutByRefer(String dispatchbillid)
			throws Exception {
		DispatchBill dispatchBill = salesOutService.getDispatchBill(dispatchbillid);
		List<DispatchBillDetail> list = dispatchBill.getBillDetailList();
		Map map = new HashMap();
		map = addSaleOutByDispatchbill(dispatchBill, list);
		return map;
	}

	@Override
	public List getSaleOutDetailListByID(String id) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("saleoutid", id);
		map.put("order", "seq");
		return getSaleOutDetailListBy(map);
	}
	/**
	 * @modify 2015-08-03 打印时是否需要拆单
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年8月3日
	 */
	public List getSaleOutDetailListBy(Map map) throws Exception {
		String customerid=(String)map.get("customerid");
		
		String detailorderby=(String)map.get("order");
		if(null!=detailorderby && !"".equals(detailorderby.trim())){
			map.put("order",detailorderby.trim());
		}
		
		//系统精度
		map.put("Decimal_Length", decimalLen);
		
		List<SaleoutDetail> list = saleoutMapper.getSaleOutDetailListByOrder(map);
		for(SaleoutDetail saleoutDetail : list){			
			if(null!=customerid && !"".equals(customerid.trim())){
				Customer customer = getCustomerByID(customerid.trim());
				if(null!=customer){
					//获取客户店内码
					CustomerPrice customerPrice=getCustomerPriceByCustomerAndGoodsid(customerid, saleoutDetail.getGoodsid());
					if(null!=customerPrice && StringUtils.isNotEmpty(customerPrice.getShopid())){
						saleoutDetail.setShopid(customerPrice.getShopid());
					}else if(StringUtils.isNotEmpty(customer.getPid())){
						customerPrice=getCustomerPriceByCustomerAndGoodsid(customer.getPid(), saleoutDetail.getGoodsid());
						if(null!=customerPrice){
							saleoutDetail.setShopid(customerPrice.getShopid());
						}
					}
					saleoutDetail.setFixprice(getGoodsPriceByCustomer(saleoutDetail.getGoodsid(),customerid));
				}				
			}
			
			StorageInfo storageInfo = getStorageInfoByID(saleoutDetail.getStorageid());
			if(null!=storageInfo){
				saleoutDetail.setStoragename(storageInfo.getName());
			}
			StorageLocation storageLocation = getStorageLocation(saleoutDetail.getStoragelocationid());
			if(null!=storageLocation){
				saleoutDetail.setStoragelocationname(storageLocation.getName());
			}
			TaxType taxType = getTaxType(saleoutDetail.getTaxtype());
			if(null!=taxType){
				saleoutDetail.setTaxtypename(taxType.getName());
			}
			GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(getGoodsInfoByID(saleoutDetail.getGoodsid()));
			if(StringUtils.isNotEmpty(saleoutDetail.getBranduser())){
				Personnel brandPersonal=getPersonnelById(saleoutDetail.getBranduser());
				if(null!=brandPersonal){
					saleoutDetail.setBrandusername(brandPersonal.getName());
				}
			}
			
			//折扣显示处理
			if("1".equals(saleoutDetail.getIsdiscount())){
				goodsInfo.setName("(折扣)"+goodsInfo.getName());
				goodsInfo.setBarcode("");
				goodsInfo.setBoxnum(null);
				saleoutDetail.setUnitnum(null);
				saleoutDetail.setAuxnumdetail("");
				saleoutDetail.setTaxprice(null);
				saleoutDetail.setUnitname("");
				if("1".equals(saleoutDetail.getIsbranddiscount())){
					saleoutDetail.setGoodsid("");
					goodsInfo.setName("(折扣)"+goodsInfo.getBrandName());
					saleoutDetail.setIsdiscount("2");
				}
			}
			saleoutDetail.setGoodsInfo(goodsInfo);
		}
		return list;
	}
	
	@Override
	public List getSaleOutDetailListBySaleorder(String saleorderid) throws Exception {
		List<SaleoutDetail> list = saleoutMapper.getSaleOutDetailListBySaleorder(saleorderid);
		for(SaleoutDetail saleoutDetail : list){
			StorageInfo storageInfo = getStorageInfoByID(saleoutDetail.getStorageid());
			if(null!=storageInfo){
				saleoutDetail.setStoragename(storageInfo.getName());
			}
			StorageLocation storageLocation = getStorageLocation(saleoutDetail.getStoragelocationid());
			if(null!=storageLocation){
				saleoutDetail.setStoragelocationname(storageLocation.getName());
			}
			TaxType taxType = getTaxType(saleoutDetail.getTaxtype());
			if(null!=taxType){
				saleoutDetail.setTaxtypename(taxType.getName());
			}
			GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(getGoodsInfoByID(saleoutDetail.getGoodsid()));
			//折扣显示处理
			if("1".equals(saleoutDetail.getIsdiscount())){
				goodsInfo.setName("(折扣)"+goodsInfo.getName());
				goodsInfo.setBarcode("");
				goodsInfo.setBoxnum(null);
				saleoutDetail.setUnitnum(null);
				saleoutDetail.setAuxnumdetail("");
				saleoutDetail.setTaxprice(null);
				saleoutDetail.setUnitname("");
				if("1".equals(saleoutDetail.getIsbranddiscount())){
					saleoutDetail.setGoodsid("");
					goodsInfo.setName(goodsInfo.getBrandName());
					saleoutDetail.setIsdiscount("2");
				}
			}
			saleoutDetail.setGoodsInfo(goodsInfo);
		}
		return list;
	}

	@Override
	public Map submitSaleOutProcess(String id) throws Exception {
		Map map = new HashMap();
		return map;
	}
	@Override
	public List showSaleOutListBy(Map map) throws Exception{
		String noDataSql=(String)map.get("noDataSql");
		if(!"true".equals(noDataSql) && !"1".equals(noDataSql)){
			String dataSql = getDataAccessRule("t_storage_saleout", null);
			map.put("dataSql",dataSql);			
		}

		List<Saleout> list = saleoutMapper.showSaleOutListBy(map);
		fillSaleOutListWithInfo(list,map);
		return list;
	}
	@Override
	public List showSaleOutPureListBy(Map map) throws Exception{
		List<Saleout> list = saleoutMapper.showSaleOutListBy(map);
		fillSaleOutListWithInfo(list,map);
		return list;
	}
	private List fillSaleOutListWithInfo(List<Saleout> list,Map map) throws Exception{
		boolean showdetail=false;
		String vshowdetail=(String)map.get("showdetail");
		if(StringUtils.isNotEmpty(vshowdetail) && "1".equals(vshowdetail)  ){
			showdetail=true;
		}
		
		String showPCustomerName=(String)map.get("showPCustomerName");

		//总店客户
		Customer pCustomer =null;
		for(Saleout saleout : list){
			List<SaleoutDetail> detailList=null;
			if(showdetail){				
				Map paramMap=new HashMap();
				paramMap.put("customerid", saleout.getCustomerid());
				paramMap.put("saleoutid", saleout.getId());
				detailList=getSaleOutDetailListBy(paramMap);
				saleout.setSaleoutDetailList(detailList);
			}
            if(StringUtils.isNotEmpty(saleout.getStorageid())) {
                StorageInfo storageInfo = getStorageInfoByID(saleout.getStorageid());
                if (null != storageInfo) {
                    saleout.setStoragename(storageInfo.getName());
                }
            }

            if(StringUtils.isNotEmpty(saleout.getCustomerid())) {
                Map queryMap = new HashMap();
                queryMap.put("id", saleout.getCustomerid());
                Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(queryMap);
                if (null != customer) {
                    if ("true".equals(showPCustomerName) && StringUtils.isNotEmpty(customer.getPid())) {
                        if (null != pCustomer && customer.getPid().equals(pCustomer.getId())) {
                            customer.setPname(pCustomer.getName());
                        } else {
                            queryMap.put("id", customer.getPid());
                            pCustomer = getBaseFilesCustomerMapper().getCustomerDetail(queryMap);
                            if (null != pCustomer) {
                                customer.setPname(pCustomer.getName());
                            }
                        }
                    }
                    saleout.setCustomerInfo(customer);
                    saleout.setCustomername(customer.getName());
                    if (StringUtils.isNotEmpty(customer.getSalesarea())) {
                        queryMap.clear();
                        queryMap.put("id", customer.getSalesarea());
                        SalesArea salesArea = getBaseFilesSalesAreaMapper().getSalesAreaDetail(queryMap);
                        if (null != salesArea) {
                            customer.setSalesareaname(salesArea.getName());
                        }
                    }
                    /*
                     * 通用版本不用，客户联系人电话，直接存储在客户档案
                    if(StringUtils.isNotEmpty(customer.getContact())){
                        queryMap.clear();
                        queryMap.put("id", customer.getContact());
                        Contacter contacter = getBaseFilesContacterMapper().getContacterDetail(queryMap);
                        if(contacter != null){
                            customer.setContactname(contacter.getName());
                        }
                    }
                    */

                    if(StringUtils.isNotEmpty(customer.getSettletype())) {
                        Settlement settlement = getSettlementByID(customer.getSettletype());
                        if (null != settlement) {
                            saleout.setSettletypename(settlement.getName());
                        }
                    }
                }
            }
            if(StringUtils.isNotEmpty(saleout.getSalesdept())) {
                DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(saleout.getSalesdept());
                if (null != departMent) {
                    saleout.setSalesdeptname(departMent.getName());
                }
            }
            if(StringUtils.isNotEmpty(saleout.getSalesuser())) {
                Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(saleout.getSalesuser());
                if (null != personnel) {
                    saleout.setSalesusername(personnel.getName());
                }
            }
            if(StringUtils.isNotEmpty(saleout.getStorager())) {
                Personnel storager = getBaseFilesPersonnelMapper().getPersonnelInfo(saleout.getStorager());
                if (null != storager) {
                    saleout.setStoragername(storager.getName());
                }
            }
		}
		return list;
	}

	@Override
	public boolean updateSaleOutRefer(String id, String refer) throws Exception {
		int i = saleoutMapper.updateSaleoutReferByid(id, refer);
		return i>0;
	}

	@Override
	public List showSaleOutListBySaleorderid(String saleorderid)
			throws Exception {
		List<Saleout> list = saleoutMapper.getSaleOutInfoBySaleorderid(saleorderid);
		for(Saleout saleout : list){
			StorageInfo storageInfo = getStorageInfoByID(saleout.getStorageid());
			if(null!=storageInfo){
				saleout.setStoragename(storageInfo.getName());
			}
			Map map = new HashMap();
			map.put("id", saleout.getCustomerid());
			Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(map);
			if(null!=customer){
				saleout.setCustomername(customer.getName());
			}
			DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(saleout.getSalesdept());
			if(null!=departMent){
				saleout.setSalesdeptname(departMent.getName());
			}
			Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(saleout.getSalesuser());
			if(null!=personnel){
				saleout.setSalesusername(personnel.getName());
			}
		}
		return list;
	}

	@Override
	public List showGoodsWaitSaleListData(String goodsid,String storageid,String summarybatchid) throws Exception {
		List<Map> list = saleoutMapper.showGoodsWaitSaleListData(goodsid,storageid,summarybatchid);
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
                String customerid=(String)map.get("customerid");
                Customer customer=getCustomerByID(customerid);
                if(customer!=null){
                    map.put("customername",customer.getName());
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

	@Override
	public boolean updateCustomerSaleOutNoWriteoffDuefromdateByCustomerid(
			String customerid) throws Exception {
		Customer customer = getCustomerByID(customerid);
		boolean flag = true;
		//获取系统参数 客户档案修改结算方式后，是否调整应收日期
		String customerSettletypeChageDuefromdate = getSysParamValue("CustomerSettletypeChageDuefromdate");
		if(null!=customer && "1".equals(customerSettletypeChageDuefromdate)){
            boolean isHasBrandSettle = isCustomerHasBrandSettle(customerid);
            //更新发货单的应收日期
			//应收日期计算方式 1按发货日期计算 2按回单审核日期计算
			String receivabletype = getSysParamValue("RECEIVABLETYPE");
			if("2".equals(receivabletype)){
				List<Receipt> list = salesOutService.getReceiptNoWriteoffListByCustomerid(customerid);
				for(Receipt receipt : list){
					Date date = receipt.getAudittime();
					if(null==date){
						date = receipt.getAddtime();
					}
                    if(isHasBrandSettle){
                        List<String> brandList = receiptMapper.getReceiptBrandListById(receipt.getId());
                        if(null!=brandList && brandList.size()>0){
                            if(brandList.size()==1){
                                String brandid = brandList.get(0);
                                String duefromdate = getReceiptDateBySettlement(date,receipt.getCustomerid(),brandid);
                                receiptMapper.updateReceiptDetailDuefromdateByBrandidAndBillid(receipt.getId(), brandid, duefromdate);
                                saleoutMapper.updateSaleOutDetailDuefromdateByOrderid(receipt.getSaleorderid(), brandid, duefromdate);
                                //更新发货单应收日期
                                saleoutMapper.updateSaleOutDuefromdateByOrderid(receipt.getSaleorderid(),brandid, duefromdate);
                                //更新回单应收日期
                                saleoutMapper.updateReceiptDuefromdateByOrderid(receipt.getSaleorderid(), duefromdate);
                            }else{
                                for(String brandid : brandList){
                                    String duefromdate = getReceiptDateBySettlement(date,receipt.getCustomerid(),brandid);
                                    receiptMapper.updateReceiptDetailDuefromdateByBrandidAndBillid(receipt.getId(), brandid, duefromdate);
                                    saleoutMapper.updateSaleOutDetailDuefromdateByOrderid(receipt.getSaleorderid(), brandid, duefromdate);
                                }
                                String billDuefromdate = getReceiptDateBySettlement(date,receipt.getCustomerid(),null);
                                //更新回单应收日期
                                saleoutMapper.updateReceiptDuefromdateByOrderid(receipt.getSaleorderid(), billDuefromdate);
                                List<Saleout> saleoutList = saleoutMapper.getSaleOutInfoBySaleorderid(receipt.getSaleorderid());
                                for(Saleout saleout : saleoutList){
                                    //获取发货单中的品牌列表
                                    List<String> sbrandList = saleoutMapper.getSaleOutBrandListById(saleout.getId());
                                    if (null != sbrandList && sbrandList.size() > 0) {
                                        if (sbrandList.size() == 1) {
                                            String brandid = sbrandList.get(0);
                                            String duefromdate = getReceiptDateBySettlement(date, saleout.getCustomerid(), brandid);
                                            saleoutMapper.updateSaleOutDuefromdate(saleout.getId(), duefromdate);
                                        } else {
                                            String duefromdate = getReceiptDateBySettlement(date, saleout.getCustomerid(), null);
                                            saleoutMapper.updateSaleOutDuefromdate(saleout.getId(), duefromdate);
                                        }
                                    } else {
                                        String duefromdate = getReceiptDateBySettlement(date, saleout.getCustomerid(), null);
                                        saleoutMapper.updateSaleOutDuefromdate(saleout.getId(), duefromdate);
                                    }
                                }
                            }
                        }else{
                            String billDuefromdate = getReceiptDateBySettlement(date,receipt.getCustomerid(),null);
                            receiptMapper.updateReceiptDetailDuefromdateByBrandidAndBillid(receipt.getSaleorderid(), null, billDuefromdate);
                            saleoutMapper.updateSaleOutDetailDuefromdateByOrderid(receipt.getSaleorderid(), null, billDuefromdate);
                            //更新发货单应收日期
                            saleoutMapper.updateSaleOutDuefromdateByOrderid(receipt.getSaleorderid(),null, billDuefromdate);
                            //更新回单应收日期
                            saleoutMapper.updateReceiptDuefromdateByOrderid(receipt.getSaleorderid(), billDuefromdate);
                        }
                    }else{
                        String billDuefromdate = getReceiptDateBySettlement(date,receipt.getCustomerid(),null);
                        receiptMapper.updateReceiptDetailDuefromdateByBrandidAndBillid(receipt.getSaleorderid(), null, billDuefromdate);
                        saleoutMapper.updateSaleOutDetailDuefromdateByOrderid(receipt.getSaleorderid(), null, billDuefromdate);
                        //更新发货单应收日期
                        saleoutMapper.updateSaleOutDuefromdateByOrderid(receipt.getSaleorderid(), null,billDuefromdate);
                        //更新回单应收日期
                        saleoutMapper.updateReceiptDuefromdateByOrderid(receipt.getSaleorderid(), billDuefromdate);
                    }

				}
			}else{
				List<Saleout> list = saleoutMapper.getSaleOutNoWriteoffListByCustomerid(customerid);
				for(Saleout saleout : list){
                    if(isHasBrandSettle) {
                        //获取发货单中的品牌列表
                        List<String> brandList = saleoutMapper.getSaleOutBrandListById(saleout.getId());
                        if (null != brandList && brandList.size() > 0) {
                            if (brandList.size() == 1) {
                                String brandid = brandList.get(0);
                                String duefromdate = getReceiptDateBySettlement(saleout.getAudittime(), saleout.getCustomerid(), brandid);
                                saleoutMapper.updateSaleOutDetailDuefromdate(saleout.getId(), brandid, duefromdate);
                                saleoutMapper.updateSaleOutDuefromdate(saleout.getId(), duefromdate);
                                saleoutMapper.updateReceiptDuefromdateByOrderid(saleout.getSaleorderid(), duefromdate);
                                saleoutMapper.updateReceiptDetailDuefromdateByOrderid(saleout.getSaleorderid(), brandid, duefromdate);
                            } else {
                                for (String brandid : brandList) {
                                    String duefromdate = getReceiptDateBySettlement(saleout.getAudittime(), saleout.getCustomerid(), brandid);
                                    saleoutMapper.updateSaleOutDetailDuefromdate(saleout.getId(), brandid, duefromdate);
                                    saleoutMapper.updateReceiptDetailDuefromdateByOrderid(saleout.getSaleorderid(), brandid, duefromdate);
                                }
                                String billDuefromdate = getReceiptDateBySettlement(saleout.getAudittime(), saleout.getCustomerid(), null);
                                saleoutMapper.updateSaleOutDuefromdate(saleout.getId(), billDuefromdate);
                                //更新回单应收日期
                                saleoutMapper.updateReceiptDuefromdateByOrderid(saleout.getSaleorderid(), billDuefromdate);
                            }
                        } else {
                            String billDuefromdate = getReceiptDateBySettlement(saleout.getAudittime(), saleout.getCustomerid(), null);
                            saleoutMapper.updateSaleOutDetailDuefromdate(saleout.getId(), null, billDuefromdate);
                            saleoutMapper.updateSaleOutDuefromdate(saleout.getId(), billDuefromdate);
                            //更新回单应收日期
                            saleoutMapper.updateReceiptDuefromdateByOrderid(saleout.getSaleorderid(), billDuefromdate);
                            saleoutMapper.updateReceiptDetailDuefromdateByOrderid(saleout.getSaleorderid(), null, billDuefromdate);
                        }
                    }else{
                        String billDuefromdate = getReceiptDateBySettlement(saleout.getAudittime(), saleout.getCustomerid(), null);
                        saleoutMapper.updateSaleOutDetailDuefromdate(saleout.getId(), null, billDuefromdate);
                        saleoutMapper.updateSaleOutDuefromdate(saleout.getId(), billDuefromdate);
                        //更新回单应收日期
                        saleoutMapper.updateReceiptDuefromdateByOrderid(saleout.getSaleorderid(), billDuefromdate);
                        saleoutMapper.updateReceiptDetailDuefromdateByOrderid(saleout.getSaleorderid(), null, billDuefromdate);
                    }
				}
			}
            //更新退货单的应收日期
            List<SaleRejectEnter> saleRejectEnterlist = saleRejectEnterMapper.getSaleRejectEnterNoWriteoffListByCustomerid(customerid);
            for(SaleRejectEnter saleRejectEnter : saleRejectEnterlist){
                if(isHasBrandSettle) {
                    //获取发货单中的品牌列表
                    List<String> brandList = saleRejectEnterMapper.getSaleRejectEnterBrandListById(saleRejectEnter.getId());
                    if (null != brandList && brandList.size() > 0) {
                        if (brandList.size() == 1) {
                            String brandid = brandList.get(0);
                            String duefromdate = getReceiptDateBySettlement(saleRejectEnter.getAudittime(), saleRejectEnter.getCustomerid(), brandid);
                            saleRejectEnterMapper.updateSaleRejectEnterDetailDuefromdate(saleRejectEnter.getId(), brandid, duefromdate);
                            saleRejectEnterMapper.updateSaleRejectEnterDuefromdate(saleRejectEnter.getId(), duefromdate);
                            saleRejectEnterMapper.updateSalesrejectDuefromdateByBillid(saleRejectEnter.getSourceid(), duefromdate);
                            saleRejectEnterMapper.updateSalesRejectDetailDuefromdateByBillid(saleRejectEnter.getSourceid(), brandid, duefromdate);
                        } else {
                            for (String brandid : brandList) {
                                String duefromdate = getReceiptDateBySettlement(saleRejectEnter.getAudittime(), saleRejectEnter.getCustomerid(), brandid);
                                saleRejectEnterMapper.updateSaleRejectEnterDetailDuefromdate(saleRejectEnter.getId(), brandid, duefromdate);
                                saleRejectEnterMapper.updateSalesRejectDetailDuefromdateByBillid(saleRejectEnter.getSourceid(), brandid, duefromdate);
                            }
                            String billDuefromdate = getReceiptDateBySettlement(saleRejectEnter.getAudittime(), saleRejectEnter.getCustomerid(), null);
                            saleRejectEnterMapper.updateSaleRejectEnterDuefromdate(saleRejectEnter.getId(), billDuefromdate);
                            //更新退货通知单应收日期
                            saleRejectEnterMapper.updateSalesrejectDuefromdateByBillid(saleRejectEnter.getSourceid(), billDuefromdate);
                        }
                    } else {
                        String billDuefromdate = getReceiptDateBySettlement(saleRejectEnter.getAudittime(), saleRejectEnter.getCustomerid(), null);
                        saleRejectEnterMapper.updateSaleRejectEnterDetailDuefromdate(saleRejectEnter.getId(), null, billDuefromdate);
                        saleRejectEnterMapper.updateSaleRejectEnterDuefromdate(saleRejectEnter.getId(), billDuefromdate);
                        saleRejectEnterMapper.updateSalesrejectDuefromdateByBillid(saleRejectEnter.getSourceid(), billDuefromdate);
                        saleRejectEnterMapper.updateSalesRejectDetailDuefromdateByBillid(saleRejectEnter.getSourceid(), null, billDuefromdate);
                    }
                }else{
                    String billDuefromdate = getReceiptDateBySettlement(saleRejectEnter.getAudittime(), saleRejectEnter.getCustomerid(), null);
                    saleRejectEnterMapper.updateSaleRejectEnterDetailDuefromdate(saleRejectEnter.getId(), null, billDuefromdate);
                    saleRejectEnterMapper.updateSaleRejectEnterDuefromdate(saleRejectEnter.getId(), billDuefromdate);
                    saleRejectEnterMapper.updateSalesrejectDuefromdateByBillid(saleRejectEnter.getSourceid(), billDuefromdate);
                    saleRejectEnterMapper.updateSalesRejectDetailDuefromdateByBillid(saleRejectEnter.getSourceid(), null, billDuefromdate);
                }
            }

		}
		return flag;
	}

	@Override
	public List getSaleoutUnCheckList(String storageid, String date,String status)
			throws Exception {
		List list = saleoutMapper.getSaleoutUnCheckList(storageid, date,status);
		return list;
	}

    /**
     * 为扫描枪提供发货单列表
     *
     * @param status
     * @return
     * @throws Exception
     */
    @Override
    public List getSaleoutForScanList(String status,Map map) throws Exception {
        String dataSql = getDataAccessRule("t_storage_saleout", "t");
        SysUser sysUser = getSysUser();
        map.put("status",status);
        map.put("checkuserid",sysUser.getUserid());
        map.put("dataSql",dataSql);
        List list = saleoutMapper.getSaleoutForScanList(map);
        return list;
    }

    @Override
	public List getSaleoutDetailWithoutDiscount(String billid) throws Exception {
		List list = saleoutMapper.getSaleoutDetailWithoutDiscount(billid);
		return list;
	}

	@Override
	public boolean updateSaleoutCheckFlag(String billid) throws Exception {
		SysUser sysUser = getSysUser();
		int i = saleoutMapper.updateSaleoutCheckFlag(billid, sysUser.getUserid(), sysUser.getName());
		return i>0;
	}

    /**
     * 扫描枪 上传发货单并且审核
     *
     * @param json
     * @return
     * @throws Exception
     */
    @Override
    public Map updateSaleoutAndAudit(String json) throws Exception {
        Map map = new HashMap();
        boolean flag = true;
        boolean editFlag = true;
        String msg = "";
        JSONObject jsonObject = JSONObject.fromObject(json);
        if(null!=jsonObject){
            String billid = jsonObject.getString("billid");
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            //判断当前发货单是否能修改
            Saleout saleout = saleoutMapper.getSaleOutInfo(billid);
            if(null==saleout){
                map.put("flag", false);
                map.put("msg", "单据不存在");
                return map;
            }
            if("3".equals(saleout.getStatus()) || "4".equals(saleout.getStatus())){
                map.put("flag", false);
                map.put("msg", "单据已审核");
                return map;
            }
            List<SaleoutDetail> detailList = saleoutMapper.getSaleOutDetailList(billid);
            BigDecimal initsendamount = BigDecimal.ZERO;
            BigDecimal initsendcostamount = BigDecimal.ZERO;
            BigDecimal sendamount = BigDecimal.ZERO;
            BigDecimal sendnotaxamount = BigDecimal.ZERO;
            BigDecimal sendcostamount = BigDecimal.ZERO;
            for(SaleoutDetail saleoutDetail : detailList){
                //正常商品才能修改发货数量
                if("0".equals(saleoutDetail.getIsdiscount())) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject jdetail = jsonArray.getJSONObject(i);
                        String cid = jdetail.getString("cid");
                        if (cid.equals(saleoutDetail.getId().toString())) {
                            String goodsId = jdetail.getString("goodsid");
                            String num = jdetail.getString("num");
                            String auxnum = jdetail.getString("auxnum");
                            //根据商品编号 获取商品箱装量 计算商品数量
                            GoodsInfo goodsInfo = getAllGoodsInfoByID(saleoutDetail.getGoodsid());
                            GoodsInfo_MteringUnitInfo unitInfo = getDefaultGoodsAuxMeterUnitInfo(goodsId);
                            BigDecimal bNum = new BigDecimal(auxnum).multiply(unitInfo.getRate()).add(new BigDecimal(num));
                            if (saleoutDetail.getUnitnum().compareTo(bNum) != 0 && null!=goodsInfo) {
                                BigDecimal oldUnitnum = saleoutDetail.getUnitnum();
                                //修改后的数量大于修改前的数量 判断可用量是否足够
                                if(saleoutDetail.getUnitnum().compareTo(bNum)==1){
                                    StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(saleoutDetail.getSummarybatchid());
                                    //批次现存量中的可用量是否大于 修改后数量-修改前数量
                                    if(storageSummaryBatch.getUsablenum().compareTo(bNum.subtract(saleoutDetail.getUnitnum())) ==-1){
                                        if("1".equals(goodsInfo.getIsbatch())){
                                            msg += "商品:"+goodsInfo.getName()+",在批次:"+storageSummaryBatch.getBatchno()+"中,数量不足，该批次只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
                                        }else if("1".equals(goodsInfo.getIsstoragelocation())){
                                            StorageLocation storageLocation = getStorageLocation(storageSummaryBatch.getStoragelocationid());
                                            msg += "商品:"+goodsInfo.getName()+",在库位:"+storageLocation.getName()+"中,数量不足，该库位只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
                                        }else{
                                            StorageInfo storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
                                            msg += "商品:"+goodsInfo.getName()+",在仓库:"+storageInfo.getName()+"中,数量不足，该仓库只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
                                        }
                                        editFlag = false;
                                    }
                                }
                                //获取商品品牌编号 和 品牌业务员
                                if (null != goodsInfo && editFlag) {
                                    saleoutDetail.setUnitnum(bNum);
                                    //计算辅单位数量
                                    Map auxmap = countGoodsInfoNumber(saleoutDetail.getGoodsid(), saleoutDetail.getAuxunitid(), saleoutDetail.getUnitnum());
                                    if (auxmap.containsKey("auxnum")) {
                                        saleoutDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
                                        String auxremainderStr = (String) auxmap.get("auxremainder");
                                        String auxIntegerStr = (String) auxmap.get("auxInteger");
                                        String auxnumdetail = (String) auxmap.get("auxnumdetail");
                                        BigDecimal auxnumB = BigDecimal.ZERO;
                                        BigDecimal auxremainderB = BigDecimal.ZERO;
                                        if (StringUtils.isNotEmpty(auxIntegerStr)) {
                                            auxnumB = new BigDecimal(auxIntegerStr);
                                        }
                                        if (StringUtils.isNotEmpty(auxremainderStr)) {
                                            auxremainderB = new BigDecimal(auxremainderStr);
                                        }
                                        saleoutDetail.setAuxnum(auxnumB);
                                        saleoutDetail.setAuxremainder(auxremainderB);
                                        saleoutDetail.setAuxnumdetail(auxnumdetail);
                                        BigDecimal taxamount = saleoutDetail.getTaxprice().multiply(saleoutDetail.getUnitnum());
                                        BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, goodsInfo.getDefaulttaxtype());
                                        saleoutDetail.setTaxamount(taxamount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                                        saleoutDetail.setNotaxamount(notaxamount);
                                        BigDecimal tax = saleoutDetail.getTaxamount().subtract(saleoutDetail.getNotaxamount());
                                        saleoutDetail.setTax(tax);

                                    }
                                    //更新发货单明细数量与金额
                                    saleoutMapper.updateSaleoutDetailNumAndAmount(saleoutDetail);
                                    //回滚待发量
                                    StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(saleoutDetail.getSummarybatchid());
                                    //回滚修改前的待发量
                                    rollBackStorageSummaryWaitnum(storageSummaryBatch.getId(),oldUnitnum);
                                    //重新修改待发量
                                    updateStorageSummaryWaitnum(storageSummaryBatch.getId(), saleoutDetail.getUnitnum());
                                }
                            }
                            break;
                        }
                    }
                }

                initsendamount = initsendamount.add(saleoutDetail.getInittaxamount());
                initsendcostamount = initsendcostamount.add(saleoutDetail.getCostprice().multiply(saleoutDetail.getInitnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
                sendamount = sendamount.add(saleoutDetail.getTaxamount());
                sendnotaxamount = sendnotaxamount.add(saleoutDetail.getNotaxamount());
                sendcostamount = sendcostamount.add(saleoutDetail.getUnitnum().multiply(saleoutDetail.getCostprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
            }
            //判断发货单能否修改
            if(editFlag){
                saleout.setInitsendamount(initsendamount);
                saleout.setInitsendcostamount(initsendcostamount);
                saleout.setSendamount(sendamount);
                saleout.setSendcostamount(sendcostamount);
                saleout.setSendnotaxamount(sendnotaxamount);

                SysUser sysUser = getSysUser();
                saleout.setModifyuserid(sysUser.getUserid());
                saleout.setModifyusername(sysUser.getName());
                saleout.setStorager(sysUser.getPersonnelid());
                //修改销售出库单
                int i = saleoutMapper.editSaleOut(saleout);
                if(i>0){
                    Map returnMap = auditSaleOut(billid);
                    if(returnMap.containsKey("flag")){
                        flag  = (Boolean)returnMap.get("flag");
                        msg = (String) returnMap.get("msg");
                    }

                }
            }
        }
        map.put("flag",flag);
        map.put("msg",msg);
        return map;
    }

    @Override
	public List getCustomerHisGoodsSalesList(String customerid, String goodsid)
			throws Exception {
//		List list = saleoutMapper.getCustomerHisGoodsSalesList(customerid, goodsid);

        String salesGoodsHistorySource = getSysParamValue("SalesGoodsHistorySource");
        List<Map> list = null;
        if("2".equals(salesGoodsHistorySource)) {
            list = receiptMapper.getCustomerHisGoodsSalesList(customerid, goodsid);
        } else {
            list = saleoutMapper.getCustomerHisGoodsSalesList(customerid, goodsid);
        }

		return list;
	}

    @Override
    public PageData getSaleoutListForAdvanceBill(PageMap pageMap) throws Exception {
        String datasql = getDataAccessRule("t_storage_saleout", "t");
        pageMap.getCondition().put("datasql", datasql);
        SysUser sysUser = getSysUser();
        //判断是否品牌业务员
        String brandUserRoleName = getSysParamValue("BrandUserRoleName");
        boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
        if(isBrandUser){
            pageMap.getCondition().put("isBrandUser", true);
            pageMap.getCondition().put("personnelid", sysUser.getPersonnelid());
        }
        List<Map> list = saleoutMapper.getSaleoutListForAdvanceBill(pageMap);
        for(Map map : list){
            String id = (String) map.get("id");
            String salesdept = (String) map.get("salesdept");
            DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(salesdept);
            if(departMent != null){
                map.put("salesdept", departMent.getName());
            }
            String salesuser = (String) map.get("salesuser");
            Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(salesuser);
            if(personnel != null){
                map.put("salesuser", personnel.getName());
            }
            String customerid = (String) map.get("customerid");

            Customer customer = getCustomerByID(customerid);
            if(customer != null){
                map.put("customername", customer.getName());
                map.put("customerInfo", customer);

                Customer headCustomer = getCustomerByID(customer.getPid());
                if(null!=headCustomer){
                    map.put("headcustomername", headCustomer.getName());
                    map.put("headcustomerid", customer.getPid());
                }else{
                    map.put("headcustomerid", customer.getId());
                }
            }
        }
        PageData pageData = new PageData(saleoutMapper.getSaleoutListForAdvanceBillCount(pageMap),list,pageMap);
        Map dataSum = saleoutMapper.getSaleoutListForAdvanceBillSum(pageMap);
        if(null != dataSum){
            List footer = new ArrayList();
            dataSum.put("id", "合计");
            footer.add(dataSum);
            pageData.setFooter(footer);
        }
        return pageData;
    }

    @Override
    public SaleoutDetail getSaleoutDetailInfo(String id, String saleoutid) throws Exception {
        SaleoutDetail saleoutDetail = saleoutMapper.getSaleoutDetailByBillidAndId(id, saleoutid);
        return saleoutDetail;
    }

    @Override
    public List<SaleoutDetail> getSaleoutDetailBrandDiscountList(String saleoutid, String brandid) throws Exception {
        List list = saleoutMapper.getSaleoutDetailBrandDiscountList(saleoutid,brandid);
        return list;
    }

    @Override
    public boolean updateSaleoutAdvanceBill(List<String> sourceidList, SalesInvoiceBill salesInvoiceBill, String isinvoicebill) throws Exception{
        String invoicebilldate = getCurrentDate();
        //更新发货单明细列表是否实际开票状态
        int i = saleoutMapper.updateSaleoutDetailIsinvoicebillAdvance(salesInvoiceBill.getId(), isinvoicebill,invoicebilldate);
        //根据发货单的发货回单编码更新发货回单明细列表是否实际开票状态
        for (String sourceid : sourceidList) {
            receiptMapper.updateReceiptDetailIsinvoicebillCaseSaleout(sourceid, isinvoicebill);
        }

        //更新发货单单据开票状态，发货回单单据开票状态
        for (String saleoutid : sourceidList) {
            Saleout saleout = saleoutMapper.getSaleOutInfo(saleoutid);
            if (null != saleout) {
                SaleoutDetail saleoutDetail2 = saleoutMapper.getSaleOutSumInvoicebillAmountBySaleoutid(saleout.getId());
                //根据回单编号 获取相关的销售发票编号列表
                List<Map> invoiceList = saleoutMapper.getSaleInvoiceBillListByReceiptid(saleout.getId());
                String invoiceids = "";
                for(Map map : invoiceList){
                    String invoiceno = (String) map.get("invoiceno");
                    if(null==invoiceno || "".equals(invoiceno)){
                        invoiceno = (String) map.get("id");
                    }
                    if("".equals(invoiceids)){
                        invoiceids = invoiceno;
                    }else{
                        invoiceids += ","+invoiceno;
                    }
                }
                if("0".equals(isinvoicebill)){
                    saleout.setInvoiceid("");
                    saleout.setInvoicebilldate("");
                    saleout.setInvoicebillamount(BigDecimal.ZERO);
                    saleout.setInvoicebillnotaxamount(BigDecimal.ZERO);
                    saleoutMapper.editSaleOut(saleout);
                }else if("1".equals(isinvoicebill)){
                    saleout.setInvoiceid(invoiceids);
                    saleout.setInvoicebilldate(invoicebilldate);
                    if(null != saleoutDetail2){
                        saleout.setInvoicebillamount(saleoutDetail2.getRealtaxamount());
                        saleout.setInvoicebillnotaxamount(saleoutDetail2.getRealnotaxamount());
                    }else{
                        saleout.setInvoicebillamount(BigDecimal.ZERO);
                        saleout.setInvoicebillnotaxamount(BigDecimal.ZERO);
                    }
                    saleoutMapper.editSaleOut(saleout);
                }

                saleoutMapper.updateSaleoutIsvoicebillByBillid(saleoutid);

                //更新发货回单状态
                List<String> receiptList = receiptMapper.getReceiptidListFromInvoicebillBySaleoutid(saleoutid);
                if(receiptList.size() != 0){
                    for(String receiptid : receiptList){
                        Map map = new HashMap();
                        map.put("id",receiptid);
                        map.put("hasinvoicedate",isinvoicebill);
                        if("1".equals(isinvoicebill)){//新增预开票要打上的标记
                            int num = receiptMapper.getReceiptDetailListIsinvoicebillCount(receiptid,"0");
                            if(num == 0){//已开票
                                receiptMapper.updateReceiptInvoicebill("1", null, receiptid);
                                receiptMapper.updateReceiptInvoicedate(map);
                            }else{//部分开票
                                receiptMapper.updateReceiptInvoicebill("4", null, receiptid);
                            }
                        }else if("0".equals(isinvoicebill)){//删除预开票要打上的标记
                            int num = receiptMapper.getReceiptDetailListIsinvoicebillCount(receiptid,"1");
                            if(num == 0){//可以开票
                                receiptMapper.updateReceiptInvoicebill("3", null, receiptid);
                                receiptMapper.updateReceiptInvoicedate(map);
                            }else{//部分开票
                                receiptMapper.updateReceiptInvoicebill("4", null, receiptid);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    @Override
    public boolean updateSaleoutInvoicebill(String isinvoicebill, String id) throws Exception {
        return saleoutMapper.updateSaleoutInvoicebill(isinvoicebill,id) > 0;
    }
    @Override
    public int getSaleOutDetailCountBy(Map map) throws Exception{
    	return saleoutMapper.getSaleOutDetailCountBy(map);
    }
    @Override
    public int getSaleOutRightJoinDetailCountBy(Map map) throws Exception{
    	return saleoutMapper.getSaleOutRightJoinDetailCountBy(map);
    }
    @Override
    public Saleout getSaloutPrinttimesById(String id) throws Exception{
    	return saleoutMapper.getSaloutPrinttimesById(id);
    }
	@Override
	public List getSaleOutDetailListGroupBy(Map map) throws Exception{
    	return saleoutMapper.getSaleOutDetailListGroupBy(map);
	}
    @Override
    public List getSaleOutRightJoinDetailListGroupBy(Map map) throws Exception{
    	return saleoutMapper.getSaleOutRightJoinDetailListGroupBy(map);
    }
    @Override
    public Map getSaleOutDetailListDataSplitByBrand(List<SaleoutDetail> detailList) throws Exception{
		//Map以品牌为key存储明细列表
		Map<String,List<SaleoutDetail>> brandDataMap = new HashMap<String, List<SaleoutDetail>>();
		
		for(SaleoutDetail itemDetail:detailList){
			List<SaleoutDetail> aDetailList=null;
			//Map有已经存储品牌key
			if(brandDataMap.containsKey(itemDetail.getBrandid())){
				aDetailList=(List<SaleoutDetail>)brandDataMap.get(itemDetail.getBrandid());
				aDetailList.add(itemDetail);
			}else if(brandDataMap.containsKey("splitbygoodsid-"+itemDetail.getGoodsid())){
				//Map有商品编号key
				aDetailList=(List<SaleoutDetail>)brandDataMap.get(itemDetail.getGoodsid());
				aDetailList.add(itemDetail);
			}else if(null==aDetailList){
				//都没有存储过时，如果品牌编号不为空，保存到品牌MAP，否则，保存到商品MAP
				aDetailList=new ArrayList<SaleoutDetail>();
				aDetailList.add(itemDetail);
				if(StringUtils.isNotEmpty(itemDetail.getBrandid())){
					brandDataMap.put(itemDetail.getBrandid(), aDetailList);
				}else{
					brandDataMap.put("splitbygoodsid-"+itemDetail.getGoodsid(), aDetailList);
				}
			}
		}
		return brandDataMap;
    }
    
    @Override
    public Map getSaleOutDetailListDataSplitByZSD(List<SaleoutDetail> detailList) throws Exception{
    	Map resultMap=new HashMap();    	
    	//整箱明细
		List<SaleoutDetail> zxDetailList=new ArrayList<SaleoutDetail>();
		//散箱明细
		List<SaleoutDetail> sxDetailList=new ArrayList<SaleoutDetail>();
		//折扣明细
		List<SaleoutDetail> zkouDetailList=new ArrayList<SaleoutDetail>();
		

    	
    	resultMap.put("zxDetailList", zxDetailList);
    	resultMap.put("sxDetailList", sxDetailList);
    	resultMap.put("zkouDetailList", zkouDetailList);
		
		for(SaleoutDetail item : detailList){
			if(!"0".equals(item.getIsbranddiscount()) 
					|| !"0".equals(item.getIsdiscount())){
				zkouDetailList.add(item);
			}else {
				//判断辅单位数量是否大于0，是否有整箱
				if( BigDecimal.ZERO.compareTo(item.getAuxnum())<0){
					SaleoutDetail detailTmp=(SaleoutDetail)CommonUtils.deepCopy(item);
					
					//主单位数量是否等于余数，此时没有整箱
					if(detailTmp.getUnitnum().compareTo(detailTmp.getAuxremainder())==0){
						//余数为主单位数量
						detailTmp.setAuxremainder(detailTmp.getUnitnum());
					}else{
						//有整箱
						detailTmp.setUnitnum(detailTmp.getUnitnum().subtract(detailTmp.getAuxremainder()));
						//余数设置为零
						detailTmp.setAuxremainder(BigDecimal.ZERO);
					}
					//含税金额
					detailTmp.setTaxamount(detailTmp.getUnitnum().multiply(detailTmp.getTaxprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					//未税金额
					detailTmp.setNotaxamount(detailTmp.getUnitnum().multiply(detailTmp.getNotaxprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					//判断是否本来就是整箱，原先整箱就是不用改变,辅数量名称
					if(BigDecimal.ZERO.compareTo(item.getAuxremainder())!=0){
						detailTmp.setAuxnumdetail("");
					}
					
					GoodsInfo goodsInfo=detailTmp.getGoodsInfo();
					if(null!=goodsInfo){
						//改变辅数量名称
						if(null!=goodsInfo.getBoxnum() 
								&& BigDecimal.ZERO.compareTo(goodsInfo.getBoxnum())!=0
								&& BigDecimal.ZERO.compareTo(item.getAuxremainder())!=0 ){
							//计算辅单位数量
							detailTmp.setAuxnum(detailTmp.getUnitnum().divide(goodsInfo.getBoxnum(), 6,BigDecimal.ROUND_DOWN));
							if(null!=detailTmp.getAuxunitname()){
								detailTmp.setAuxnumdetail(detailTmp.getAuxnum().intValue()+detailTmp.getAuxunitname());
							}
						}
					}
					
					zxDetailList.add(detailTmp);
				}
				//取余数的
				if(BigDecimal.ZERO.compareTo(item.getAuxremainder())<0){						
					SaleoutDetail detailTmp=(SaleoutDetail)CommonUtils.deepCopy(item);
					//主单位数量为余数
					detailTmp.setUnitnum(detailTmp.getAuxremainder());
					//辅单位数量为零
					detailTmp.setAuxnum(BigDecimal.ZERO);
					//含税金额
					detailTmp.setTaxamount(detailTmp.getUnitnum().multiply(detailTmp.getTaxprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					//未税金额
					detailTmp.setNotaxamount(detailTmp.getUnitnum().multiply(detailTmp.getNotaxprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					
					//判断是否本来就是整箱，原先整箱就是不用改变,辅数量名称
					if(BigDecimal.ZERO.compareTo(item.getAuxnum())!=0){
						detailTmp.setAuxnumdetail("");
						if(null!=detailTmp.getAuxunitname()){
							detailTmp.setAuxnumdetail("0"+detailTmp.getAuxunitname());
						}
						if(null!=detailTmp.getUnitname()){
							detailTmp.setAuxnumdetail(detailTmp.getAuxnumdetail()+detailTmp.getUnitnum()+detailTmp.getUnitname());								
						}
					}
					sxDetailList.add(detailTmp);
				}
			}
		}
		return resultMap;
    }
    
    /**
     * 修改发货人
     * @param id
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016-4-6
     */
    @Override
    public boolean editDispatchUser(String id,String storager)throws Exception{
    	boolean flag=true;
    	flag=saleoutMapper.editDispatchUser(id,storager)>0;
    	return flag;
    	
    }

    @Override
    public SaleoutDetail getSaleOutSumAmountBySaleorderid(String saleorderid) throws Exception{
        return saleoutMapper.getSaleOutSumAmountBySaleorderid(saleorderid);
    }

    /**
     * 获取发货单导出明细数据
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Mar 02, 2018
     */
    @Override
    public List getSaleoutDetailExportData(PageMap pageMap) throws Exception{
        List<SaleoutExport> list=saleoutMapper.getSaleoutDetailExportData(pageMap);
        //判断多条相同商品 现存量问题
        Map storageGoodsMap = new HashMap();
        for(SaleoutExport saleoutExport:list){
            Personnel indoorPerson = getPersonnelById(saleoutExport.getIndooruserid());
            if (null != indoorPerson) {
                saleoutExport.setIndoorusername(indoorPerson.getName());
            }
            StorageInfo storageInfo = getStorageInfoByID(saleoutExport.getStorageid());
            if (null != storageInfo) {
                saleoutExport.setStoragename(storageInfo.getName());
            }
            Map map = new HashMap();
            map.put("id", saleoutExport.getCustomerid());
            Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(map);
            if (null != customer) {
                saleoutExport.setCustomername(customer.getName());
            }
            DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(saleoutExport.getSalesdept());
            if (null != departMent) {
                saleoutExport.setSalesdeptname(departMent.getName());
            }
            Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(saleoutExport.getSalesuser());
            if (null != personnel) {
                saleoutExport.setSalesusername(personnel.getName());
            }
            Personnel storager = getBaseFilesPersonnelMapper().getPersonnelInfo(saleoutExport.getStorager());
            if (null != storager) {
                saleoutExport.setStoragername(storager.getName());
            }


            GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(getGoodsInfoByID(saleoutExport.getGoodsid()));

            StorageLocation storageLocation = getStorageLocation(saleoutExport.getStoragelocationid());
            if(null!=storageLocation){
                saleoutExport.setStoragelocationname(storageLocation.getName());
            }
            TaxType taxType = getTaxType(saleoutExport.getTaxtype());
            if(null!=taxType){
                saleoutExport.setTaxtypename(taxType.getName());
            }
            //折扣显示处理
            if("1".equals(saleoutExport.getIsdiscount())){
                goodsInfo.setBarcode(null);
                goodsInfo.setBoxnum(null);
                saleoutExport.setUnitnum(null);
                saleoutExport.setAuxnumdetail(null);
                saleoutExport.setTaxprice(null);
                if("1".equals(saleoutExport.getIsbranddiscount())){
                    saleoutExport.setGoodsid("");
                    goodsInfo.setName(goodsInfo.getBrandName());
                    saleoutExport.setIsdiscount("2");
                }
                saleoutExport.setIsenough("1");
            }else{
                String keyid = saleoutExport.getSummarybatchid();
                StorageSummaryBatch storageSummaryBatch = null;
                if(storageGoodsMap.containsKey(keyid)){
                    storageSummaryBatch = (StorageSummaryBatch) storageGoodsMap.get(keyid);
                }else{
                    storageSummaryBatch = getStorageSummaryBatchById(saleoutExport.getSummarybatchid());
                }
                if(null!=storageSummaryBatch && storageSummaryBatch.getExistingnum().compareTo(saleoutExport.getUnitnum())>=0){
                    saleoutExport.setIsenough("1");
                }else{
                    saleoutExport.setIsenough("0");
                }
                if(null!=storageSummaryBatch){
                    if(storageSummaryBatch.getExistingnum().compareTo(saleoutExport.getUnitnum())>=0){
                        storageSummaryBatch.setExistingnum(storageSummaryBatch.getExistingnum().subtract(saleoutExport.getUnitnum()));
                    }else{
                        storageSummaryBatch.setExistingnum(BigDecimal.ZERO);
                    }
                    storageGoodsMap.put(keyid, storageSummaryBatch);
                }
            }
            goodsInfo.setItemno(getItemnoByGoodsAndStorage(goodsInfo.getId(),saleoutExport.getStorageid()));
            saleoutExport.setGoodsInfo(goodsInfo);
            saleoutExport.setBarcode(goodsInfo.getBarcode());
            saleoutExport.setGoodsname(goodsInfo.getName());

            SysCode sysCode=getBaseSysCodeInfo(saleoutExport.getStatus(),"status");
            if(sysCode!=null){
                saleoutExport.setStatusname(sysCode.getCodename());
            }
        }
        return list;
    }
}
