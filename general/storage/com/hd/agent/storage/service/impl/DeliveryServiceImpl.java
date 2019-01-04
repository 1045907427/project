/**
 * @(#)DeliveryService.java
 *
 * @author yezhenyu
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 29, 2014 yezhenyu 创建版本
 */
package com.hd.agent.storage.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.dao.LogisticsMapper;
import com.hd.agent.basefiles.dao.PersonnelMapper;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.report.model.LogisticsReport;
import com.hd.agent.sales.dao.ReceiptMapper;
import com.hd.agent.sales.model.Receipt;
import com.hd.agent.storage.dao.DeliveryMapper;
import com.hd.agent.storage.model.Delivery;
import com.hd.agent.storage.model.DeliveryCustomer;
import com.hd.agent.storage.model.DeliverySaleOut;
import com.hd.agent.storage.model.Saleout;
import com.hd.agent.storage.service.IDeliveryService;
import com.hd.agent.system.model.SysParam;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 
 * @author yezhenyu
 */
public class DeliveryServiceImpl extends BaseFilesServiceImpl implements IDeliveryService {

	private DeliveryMapper deliveryMapper;
    private ReceiptMapper receiptMapper;
	private LogisticsMapper logisticsMapper;
	private PersonnelMapper personnelMapper;
	
    public ReceiptMapper getReceiptMapper() {
        return receiptMapper;
    }

    public void setReceiptMapper(ReceiptMapper receiptMapper) {
        this.receiptMapper = receiptMapper;
    }

    public DeliveryMapper getDeliveryMapper() {
		return deliveryMapper;
	}

	public void setDeliveryMapper(DeliveryMapper deliveryMapper) {
		this.deliveryMapper = deliveryMapper;
	}

	public PersonnelMapper getPersonnelMapper() {
		return personnelMapper;
	}

	public void setPersonnelMapper(PersonnelMapper personnelMapper) {
		this.personnelMapper = personnelMapper;
	}

	public LogisticsMapper getLogisticsMapper() {
		return logisticsMapper;
	}

	public void setLogisticsMapper(LogisticsMapper logisticsMapper) {
		this.logisticsMapper = logisticsMapper;
	}

	@Override
	public PageData getSaleOutListForDelivery(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_storage_saleout", "t");
		String dataSql1 = getDataAccessRule("t_storage_delivery_out", "t");
		String dataSql2 = getDataAccessRule("t_storage_allocate_out", "t");
		pageMap.getCondition().put("saleoutdataSql",dataSql);
		pageMap.getCondition().put("deliveryoutdataSql",dataSql1);
		pageMap.getCondition().put("allocatedataSql",dataSql2);
		SysParam sysParam = getBaseSysParamMapper().getSysParam("StatusOfAddDeliverySourceList");
		if(null != sysParam){
			pageMap.getCondition().put("status", sysParam.getPvalue());
		}else{
			pageMap.getCondition().put("status", "3");
		}
		/**	新增供应商代配送客户订单		**/
		List<DeliverySaleOut> list=deliveryMapper.getSaleOutAndDeliveryOutListForDelivery(pageMap);
		int count=deliveryMapper.getSaleOutAndDeliveryOutCountForDelivery(pageMap);
		/**	新增供应商代配送客户订单		**/
		Map dataSum = null;

		BigDecimal volume = BigDecimal.ZERO;
		BigDecimal weight = BigDecimal.ZERO;
		BigDecimal salesamount = BigDecimal.ZERO;
		BigDecimal boxnum = BigDecimal.ZERO;

		if (list != null && list.size() != 0) {
            //优先配车参数1重量2箱数3体积
            String deliveryPriority = null != pageMap.getCondition().get("deliveryPriority") ? (String)pageMap.getCondition().get("deliveryPriority") : "";

			BigDecimal volumeQ = (null != pageMap.getCondition().get("volume")) ? new BigDecimal((String)pageMap.getCondition().get("volume")) : BigDecimal.ZERO;
			BigDecimal boxnumQ = (null != pageMap.getCondition().get("boxnum")) ? new BigDecimal((String)pageMap.getCondition().get("boxnum")) : BigDecimal.ZERO;
			BigDecimal weightQ = (null != pageMap.getCondition().get("weight")) ? new BigDecimal((String)pageMap.getCondition().get("weight")) : BigDecimal.ZERO;
			boolean vflag = true,bflag =true,wflag = true,tflag = false;
			if((boxnumQ.compareTo(BigDecimal.ZERO) == 0) 
				&& (volumeQ.compareTo(BigDecimal.ZERO) == 0) 
				&& (weightQ.compareTo(BigDecimal.ZERO) == 0)){
				tflag = true;
			}

			for (DeliverySaleOut deliverySaleOut : list) {

				// 获取客户名称
				// 调拨单
				if("3".equals(deliverySaleOut.getBilltype())) {

					StorageInfo storage = getStorageInfoByID(deliverySaleOut.getCustomerid());
					if (storage != null) {
						deliverySaleOut.setCustomername(storage.getName());
					}
				// 发货单、代配送
				} else {
					Customer customer = getCustomerByID(deliverySaleOut.getCustomerid());
					if(null != customer){
						deliverySaleOut.setCustomername(customer.getName());
					}
				}

				Personnel salesuser = personnelMapper.getPersonnelInfo(deliverySaleOut.getSalesuser());
				if (salesuser != null){
					deliverySaleOut.setSalesusername(salesuser.getName());
				}
				Map<String,String> map = new HashMap<String,String>();
				map.put("id", deliverySaleOut.getSalesarea());
				SalesArea salesArea = getBaseSalesAreaMapper().getSalesAreaDetail(map);
				if(null != salesArea){
					deliverySaleOut.setSalesareaname(salesArea.getThisname());
				}
				LogisticsLine line = getBaseLogisticsMapper().getLineInfo(deliverySaleOut.getLineid());
				if(null != line){
					deliverySaleOut.setLinename(line.getName());
				}
				// 合计
				volume = volume.add(deliverySaleOut.getVolume() == null ? new BigDecimal(0) : deliverySaleOut
						.getVolume());
				weight = weight.add(deliverySaleOut.getWeight() == null ? new BigDecimal(0) : deliverySaleOut
						.getWeight());
				boxnum = boxnum.add(deliverySaleOut.getBoxnum() == null ? new BigDecimal(0) : deliverySaleOut
						.getBoxnum());
				salesamount = salesamount.add(deliverySaleOut.getSalesamount());
                //优先配车参数1重量2箱数3体积
                if(boxnumQ.compareTo(BigDecimal.ZERO) != 0 && deliveryPriority.indexOf("2") != -1){
					bflag = (boxnumQ.setScale(2, BigDecimal.ROUND_HALF_UP)).compareTo(boxnum.setScale(2, BigDecimal.ROUND_HALF_UP)) >= 0;
				}
				if(volumeQ.compareTo(BigDecimal.ZERO) != 0 && deliveryPriority.indexOf("3") != -1){
					vflag = (volumeQ.setScale(4, BigDecimal.ROUND_HALF_UP)).compareTo(volume.setScale(4, BigDecimal.ROUND_HALF_UP)) >= 0;
				}
				if(weightQ.compareTo(BigDecimal.ZERO) != 0 && deliveryPriority.indexOf("1") != -1){
					wflag = (weightQ.setScale(2, BigDecimal.ROUND_HALF_UP)).compareTo(weight.setScale(2, BigDecimal.ROUND_HALF_UP)) >= 0;
				}
				if(tflag){
					deliverySaleOut.setCheck("0");
				}else{
					if(vflag && bflag && wflag){
						deliverySaleOut.setCheck("1");
					}else{
						deliverySaleOut.setCheck("0");
					}
				}

				String storageid = deliverySaleOut.getStorageid();
                StorageInfo storageInfo = getStorageInfoByID(storageid);
				if (storageInfo != null) {
					deliverySaleOut.setStoragename(storageInfo.getName());
				}
			}
		}
		dataSum = new HashMap();
		dataSum.put("volume", volume);
		dataSum.put("weight", weight);
		dataSum.put("boxnum", boxnum);
		dataSum.put("salesamount", salesamount);

		PageData pageData = new PageData(count, list, pageMap);

		List footer = new ArrayList();
		if (null != dataSum) {
			dataSum.put("saleoutid", "合计");
			footer.add(dataSum);
		}
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public PageData getSaleOutListForAddDelivery(PageMap pageMap)
			throws Exception {
		String dataSql = getDataAccessRule("t_storage_saleout", "t");
		String dataSql1 = getDataAccessRule("t_storage_delivery_out", "t");
		pageMap.getCondition().put("saleoutdataSql",dataSql);
		pageMap.getCondition().put("deliveryoutdataSql", dataSql1);
		SysParam sysParam = getBaseSysParamMapper().getSysParam("StatusOfAddDeliverySourceList");
		if(null != sysParam){
			pageMap.getCondition().put("status", sysParam.getPvalue());
		}else{
			pageMap.getCondition().put("status", "3");
		}
		List<DeliverySaleOut> list = deliveryMapper.getSaleOutListForAddDelivery(pageMap);
		int count = deliveryMapper.getSaleOutListForAddDeliveryCount(pageMap);
		Map dataSum = null;

		BigDecimal volume = BigDecimal.ZERO;
		BigDecimal weight = BigDecimal.ZERO;
		BigDecimal salesamount = BigDecimal.ZERO;
		BigDecimal boxnum = BigDecimal.ZERO;

		if (list != null && list.size() != 0) {
			BigDecimal volumeQ = (null != pageMap.getCondition().get("volume")) ? new BigDecimal((String)pageMap.getCondition().get("volume")) : BigDecimal.ZERO;
			BigDecimal boxnumQ = (null != pageMap.getCondition().get("boxnum")) ? new BigDecimal((String)pageMap.getCondition().get("boxnum")) : BigDecimal.ZERO;
			BigDecimal weightQ = (null != pageMap.getCondition().get("weight")) ? new BigDecimal((String)pageMap.getCondition().get("weight")) : BigDecimal.ZERO;
			boolean vflag = true,bflag =true,wflag = true,tflag = false;;
			if((boxnumQ.compareTo(BigDecimal.ZERO) == 0) 
				&& (volumeQ.compareTo(BigDecimal.ZERO) == 0) 
				&& (weightQ.compareTo(BigDecimal.ZERO) == 0)){
				tflag = true;
			}
			for (DeliverySaleOut deliverySaleOut : list) {
				// 获取客户名称
                if("3".equals(deliverySaleOut.getBilltype())) {
                    StorageInfo storage = getStorageInfoByID(deliverySaleOut.getCustomerid());
                    if (storage != null) {
                        deliverySaleOut.setCustomername(storage.getName());
                    }
                } else {
                    Customer customer = getCustomerByID(deliverySaleOut.getCustomerid());
                    if(null != customer){
                        deliverySaleOut.setCustomername(customer.getName());
                    }
                }
				Personnel salesuser = personnelMapper.getPersonnelInfo(deliverySaleOut.getSalesuser());
				if (salesuser != null){
					deliverySaleOut.setSalesusername(salesuser.getName());
				}
				Map<String,String> map = new HashMap<String,String>();
				map.put("id", deliverySaleOut.getSalesarea());
				SalesArea salesArea = getBaseSalesAreaMapper().getSalesAreaDetail(map);
				if(null != salesArea){
					deliverySaleOut.setSalesareaname(salesArea.getThisname());
				}
				LogisticsLine line = getBaseLogisticsMapper().getLineInfo(deliverySaleOut.getLineid());
				if(null != line){
					deliverySaleOut.setLinename(line.getName());
				}
				// 合计
				volume = volume.add(deliverySaleOut.getVolume() == null ? new BigDecimal(0) : deliverySaleOut
						.getVolume());
				weight = weight.add(deliverySaleOut.getWeight() == null ? new BigDecimal(0) : deliverySaleOut
						.getWeight());
				boxnum = boxnum.add(deliverySaleOut.getBoxnum() == null ? new BigDecimal(0) : deliverySaleOut
						.getBoxnum());
				salesamount = salesamount.add(deliverySaleOut.getSalesamount());
				if(boxnumQ.compareTo(BigDecimal.ZERO) != 0){
					bflag = (boxnumQ.setScale(2, BigDecimal.ROUND_HALF_UP)).compareTo(boxnum.setScale(2, BigDecimal.ROUND_HALF_UP)) >= 0;
				}
				if(volumeQ.compareTo(BigDecimal.ZERO) != 0){
					vflag = (volumeQ.setScale(4, BigDecimal.ROUND_HALF_UP)).compareTo(volume.setScale(4, BigDecimal.ROUND_HALF_UP)) >= 0;
				}
				if(weightQ.compareTo(BigDecimal.ZERO) != 0){
					wflag = (weightQ.setScale(2, BigDecimal.ROUND_HALF_UP)).compareTo(weight.setScale(2, BigDecimal.ROUND_HALF_UP)) >= 0;
				}
				if(tflag){
					deliverySaleOut.setCheck("0");
				}else{
					if(vflag && bflag && wflag){
						deliverySaleOut.setCheck("1");
					}else{
						deliverySaleOut.setCheck("0");
					}
				}
			}
		}
//		String deliveryid = (String) pageMap.getCondition().get("deliveryid");
//		if (StringUtils.isNotEmpty(deliveryid)) {
//			String ids = (String) pageMap.getCondition().get("saleoutids");
//			List<DeliverySaleOut> oldSaleoutList = deliveryMapper.getDeliverySaleoutList(deliveryid);
//			for (DeliverySaleOut deliverySaleOut : oldSaleoutList) {
//
//				if (!ids.contains(deliverySaleOut.getSaleoutid())) {
//					Customer customer = deliveryMapper.getCustomer(deliverySaleOut.getCustomerid());
//					deliverySaleOut.setCustomername(customer.getName());
//					Personnel salesuser = personnelMapper.getPersonnelInfo(deliverySaleOut.getSalesuser());
//					if (salesuser != null)
//						deliverySaleOut.setSalesusername(salesuser.getName());
//					list.add(deliverySaleOut);
//					count++;
//				}
//			}
//		}

		dataSum = new HashMap();
		dataSum.put("volume", volume);
		dataSum.put("weight", weight);
		dataSum.put("boxnum", boxnum);
		dataSum.put("salesamount", salesamount);

		PageData pageData = new PageData(count, list, pageMap);

		if (null != dataSum) {
			List footer = new ArrayList();
			dataSum.put("saleoutid", "合计");
			footer.add(dataSum);
			pageData.setFooter(footer);
		}
		return pageData;
	}

	@Override
	public Saleout getSaleout(String saleoutid) throws Exception {
		return deliveryMapper.getSaleout(saleoutid);
	}

	@Override
	public boolean addDelivery(Delivery delivery, String ids, List<DeliverySaleOut> deliverySaleoutList,
			List<DeliveryCustomer> deliveryCustomerList) throws Exception {
		boolean retflag=true;
		retflag = addDeliverySaleOutList(deliverySaleoutList) > 0;
		if (retflag)
			retflag = addDeliveryCustomerList(deliveryCustomerList) > 0;
		if (retflag) {
            retflag = deliveryMapper.addDelivery(delivery) > 0;
        }

        if(retflag){
            //回写发货单配货状态和回单配送状态
            Map map2 = new HashMap();
            map2.put("idsArr",delivery.getId().split(","));
            updateSaleOutReceiptIsdelivery(1,map2);
        }
		return retflag;
	}

	@Override
	public int addDeliverySaleOutList(List<DeliverySaleOut> deliverySaleoutList) throws Exception {
		for (DeliverySaleOut deliverySaleOut : deliverySaleoutList) {
			int ret = deliveryMapper.addDeliverySaleOut(deliverySaleOut);
			if (ret <= 0)
				throw new Exception("添加配送发货单失败！");
		}
		return 1;
	}

	@Override
	public PageData showDeliveryList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_storage_logistics_delivery", "t");
		pageMap.setDataSql(dataSql);
		int count = deliveryMapper.getDeliveryListCount(pageMap);
		List list = deliveryMapper.getDeliveryList(pageMap);
		for (Object object : list) {
			Delivery delivery = (Delivery) object;
			String drivername = "";
			String followname = "";
			if(StringUtils.isNotEmpty(delivery.getDriverid())){
				Personnel driver = personnelMapper.getPersonnelInfo(delivery.getDriverid());
				if(null!=driver){
					drivername = driver.getName();
				}
			}
			if(StringUtils.isNotEmpty(delivery.getFollowid())){
				if(delivery.getFollowid().contains(",")){
					String [] followids = delivery.getFollowid().split(",");
					for (int i = 0; i < followids.length; i++) {
						Personnel follow = personnelMapper.getPersonnelInfo(followids[i]);
						if(null!=follow){
							followname += follow.getName()+",";
						}
					}
				}else{
					Personnel follow = personnelMapper.getPersonnelInfo(delivery.getFollowid());
					if(null!=follow){
						followname = follow.getName();
					}
				}

			}
			delivery.setDrivername(drivername);
			delivery.setFollowname(followname);
			if(StringUtils.isNotEmpty(delivery.getLineid())){
				String linename = logisticsMapper.getLineInfo(delivery.getLineid()).getName();
				delivery.setLinename(linename);
			}
			LogisticsCar car = logisticsMapper.getCar(delivery.getCarid());
			if(null != car){
				delivery.setCarname(car.getName());
				delivery.setCartype(getCarType(car.getCartype()));
			}

			Map map = getDeliveryCustomerList(delivery.getId());
			delivery.setBillnums((Integer) map.get("billnums"));
			delivery.setCollectionamount((BigDecimal)map.get("collectionamount"));

			delivery.setSalesamount((BigDecimal)map.get("salesamount"));
			delivery.setVolume((BigDecimal)map.get("volume"));
			delivery.setWeight((BigDecimal)map.get("weight"));
		}
		PageData pageData = new PageData(count, list, pageMap);
		return pageData;
	}

	@Override
	public boolean changeSaleOutDeliveryStatus(String ids, int flag) throws Exception {
		if(StringUtils.isNotEmpty(ids)){
			String[] idArr = ids.split(",");
			int num = 0;
			for(String id : idArr){
				boolean bfalg = deliveryMapper.changeSaleOutDeliveryStatus(id, flag) > 0;
				if(!bfalg){
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public Map getDeliveryCustomerList(String id) throws Exception {
		List<DeliveryCustomer> list = deliveryMapper.getDeliveryCustomerList(id);
		return fillDeliveryCustomerList(list,null);
	}

	@Override
	public Map getDeliveryCustomerList(Map map) throws Exception {
		List<DeliveryCustomer> list = deliveryMapper.getDeliveryCustomerListByMap(map);
		return fillDeliveryCustomerList(list,null);
	}
	/** 
	 * zhangHonghui 20150814 修改开始：
	 * 内容，根据是否拆单参数，重新计算单据数。
	 * 当参数值为2时，需要验证客户是否需要进行按品牌拆分。
	 * 验证规则，如果指定总店客户，则所有门店都要进行按品牌拆分单据；如果只指定门店，则该门店需要进行按品牌拆分单据。
	 * zhangHonghui 20150814 修改结束：
	 * @modifycontentEnd
	 */
	private Map fillDeliveryCustomerList(List<DeliveryCustomer> list,Map funcMap) throws Exception{
		BigDecimal auxnum = new BigDecimal(0);
		BigDecimal auxremainder = new BigDecimal(0);
		BigDecimal volume = BigDecimal.ZERO;
		BigDecimal weight = BigDecimal.ZERO;
		BigDecimal boxnum = new BigDecimal(0.00);
		BigDecimal collectionamount = new BigDecimal(0);
        BigDecimal salesamount = BigDecimal.ZERO;
		int index = 1;
		int billnums = 0;
		String statusarr="";
		String printnumStr="";
		String saleoutidStr="";
		
		boolean isComputeSplitType=false;
		
		if(null!=funcMap){
			String tmp=(String)funcMap.get("computeSplitType");
			if("1".equals(tmp) || "true".equals(tmp)){
				isComputeSplitType=true;
			}
			if(isComputeSplitType){
				statusarr=(String)funcMap.get("statusarr");
				printnumStr=(String)funcMap.get("printnum");
				saleoutidStr=(String)funcMap.get("saleoutidarr");
			}
		}
		
		//查询是否要进行拆单
		
		String saleOrderPrintSplitType="0";
		String[] brandSplitByCustomerArr=null;
		if(isComputeSplitType){
			SysParam saleOrderPrintSplitTypeParam=getBaseSysParamMapper().getSysParam("saleOrderPrintSplitType"); //销售打印拆单方式，0普通，1整散，2按客户进分按品牌拆单
			//获取的是否拆单的值
			if(null!=saleOrderPrintSplitTypeParam){			
				saleOrderPrintSplitType=saleOrderPrintSplitTypeParam.getPvalue();
				//如果为空或者没有，则时普通单据
		   	 	if(null==saleOrderPrintSplitType || "".equals(saleOrderPrintSplitType.trim())){
		   	 		saleOrderPrintSplitType="0";
		   	 	}else{
		   	 		saleOrderPrintSplitType=saleOrderPrintSplitType.trim();
		   	 	}
		   	 	//判断是否按指定客户进行品牌拆单
		   	 	if("2".equals(saleOrderPrintSplitType)){
		   	 		//设置默认普通单据
		   	 		saleOrderPrintSplitType="0";
		   	 		//是否按品牌拆单的，指定客户
		   	 		SysParam printBrandSplitByCustomerParam=getBaseSysParamMapper().getSysParam("saleOrderPrintBrandSplitByCustomer");
		   	 		if(null!=printBrandSplitByCustomerParam){
		   	 			String printBrandSplitByCustomer=printBrandSplitByCustomerParam.getPvalue();
		   	 			if(null!=printBrandSplitByCustomer && !"".equals(printBrandSplitByCustomer.trim())){
		   	 				printBrandSplitByCustomer=printBrandSplitByCustomer.trim();
		   	 				//需要进行按指定客户进行品牌拆单
		   	 				saleOrderPrintSplitType="2";
		   	 				brandSplitByCustomerArr=printBrandSplitByCustomer.split(",");
		   	 			}
		   	 		}
		   	 	}
			}
		}		

		for (DeliveryCustomer deliveryCustomer : list) {

		    Customer customer = null;
		    if("3".equals(deliveryCustomer.getBilltype())) {
                StorageInfo storage = getStorageInfoByID(deliveryCustomer.getCustomerid());
                deliveryCustomer.setCustomername(storage.getName());
            } else {
                customer = getCustomerByID(deliveryCustomer.getCustomerid());
                if(null!=customer){
                    deliveryCustomer.setCustomername(customer.getName());
                }
            }
			auxnum = auxnum.add(deliveryCustomer.getAuxnum());
			auxremainder = auxremainder.add(deliveryCustomer.getAuxremainder());
			boxnum = boxnum.add(deliveryCustomer.getBoxnum() == null ? new BigDecimal(0) : deliveryCustomer.getBoxnum());
			volume = volume.add(null != deliveryCustomer.getVolume() ? deliveryCustomer.getVolume() : BigDecimal.ZERO);
			weight = weight.add(null != deliveryCustomer.getWeight() ? deliveryCustomer.getWeight() : BigDecimal.ZERO);
			if (deliveryCustomer.getCollectionamount() != null)
				collectionamount = collectionamount.add(deliveryCustomer.getCollectionamount());
			else {
				deliveryCustomer.setCollectionamount(new BigDecimal(0));
			}
			
			//拆单开始
			//是否拆单临时值
			String myOrderPrintSplitType=saleOrderPrintSplitType;
			//按指定客户进行品牌拆单
			if("2".equals(myOrderPrintSplitType)){
				//默认普通单据
				myOrderPrintSplitType="0";
				//当前的客户是否是断指定的客户
				if(ArrayUtils.contains(brandSplitByCustomerArr, deliveryCustomer.getCustomerid())){
					//按指定客户进行品牌拆单
					myOrderPrintSplitType="2";
				}else{
					//查询客户信息，使用总店编号进行判断是否是指定客户
					if(null!=customer 
							&& StringUtils.isNotEmpty(customer.getPid()) 
							&& ArrayUtils.contains(brandSplitByCustomerArr, customer.getPid())){
						//按指定客户进行品牌拆单
						myOrderPrintSplitType="2";
					}
				}				
			}
			
			if("2".equals(myOrderPrintSplitType)){
				//重新计算单据数
				Map paramMap=new HashMap();
				paramMap.put("customerid", deliveryCustomer.getCustomerid());
				paramMap.put("statusarr", statusarr);
				paramMap.put("printnum", printnumStr);
				paramMap.put("saleoutidarr", saleoutidStr);
				int tmpBillnums=deliveryMapper.getDeliveryBrandSplitBillCountByCustomer(paramMap);
				
				deliveryCustomer.setBillnums(tmpBillnums);
			}
			//拆单结束
			billnums += deliveryCustomer.getBillnums();

			salesamount = salesamount.add(deliveryCustomer.getSalesamount());

			// if(deliveryCustomer.getId()==null)
			deliveryCustomer.setIndex(index++);
			if (deliveryCustomer.getIsreceipt() == null)
				deliveryCustomer.setIsreceipt("1");
		}
		Map dataSum = new HashMap();
		dataSum.put("auxnum", auxnum);
		dataSum.put("auxremainder", auxremainder);
		dataSum.put("boxnum", boxnum);
		dataSum.put("volume", volume);
		dataSum.put("weight", weight);
		dataSum.put("collectionamount", collectionamount);
		dataSum.put("billnums", billnums);
        dataSum.put("salesamount",salesamount);
		// dataSum.put("remark", "共：" + list.size() + " 家");

		List footer = new ArrayList();
		dataSum.put("customerid", "合计");
		footer.add(dataSum);

		Map map = new HashMap();
		map.put("list", list);
		map.put("footer", footer);
		String count = boxnum.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString();// auxnum.setScale(0).toPlainString()
		// +
		// "，"
		// +
		// auxremainder.setScale(0).toString();
		map.put("count", count);
		// 给打印提供数据
		map.put("auxnum", auxnum);
		map.put("boxnum", boxnum);
		map.put("volume", volume);
		map.put("weight", weight);
		map.put("auxremainder", auxremainder);
		map.put("collectionamount", collectionamount);
		// 给配送单列表提供数据
		map.put("billnums", billnums);
        map.put("salesamount",salesamount);
		return map;
	}

	@Override
	public Map getDeliverySaleoutList(String id) throws Exception {
		List<DeliverySaleOut> list = deliveryMapper.getDeliverySaleoutList(id);
		BigDecimal volume = new BigDecimal(0);
		BigDecimal weight = new BigDecimal(0);
		BigDecimal boxnum = new BigDecimal(0);
		BigDecimal salesamount = new BigDecimal(0);
		for (DeliverySaleOut deliverySaleOut : list) {
            // 调拨单
		    if("3".equals(deliverySaleOut.getBilltype())) {
		        StorageInfo storage = getStorageInfoByID(deliverySaleOut.getCustomerid());
                if (storage != null) {
                    deliverySaleOut.setCustomername(storage.getName());
                }
            } else {
                Customer customer = getCustomerByID(deliverySaleOut.getCustomerid());
                if(null != customer){
                    deliverySaleOut.setCustomername(customer.getName());
                }
            }

			Personnel salesuser = personnelMapper.getPersonnelInfo(deliverySaleOut.getSalesuser());
			if (salesuser != null)
				deliverySaleOut.setSalesusername(salesuser.getName());

			// 合计
			volume = volume.add(deliverySaleOut.getVolume() == null ? new BigDecimal(0) : deliverySaleOut.getVolume());
			weight = weight.add(deliverySaleOut.getWeight() == null ? new BigDecimal(0) : deliverySaleOut.getWeight());
			boxnum = boxnum.add(deliverySaleOut.getBoxnum() == null ? new BigDecimal(0) : deliverySaleOut.getBoxnum());
			salesamount = salesamount.add(deliverySaleOut.getSalesamount());
		}

		Map dataSum = new HashMap();
		dataSum.put("volume", volume);
		dataSum.put("weight", weight);
		dataSum.put("boxnum", boxnum);
		dataSum.put("salesamount", salesamount);

		List footer = new ArrayList();
		dataSum.put("orderid", "合计");
		footer.add(dataSum);

		Map map = new HashMap();
		map.put("list", list);
		map.put("footer", footer);

		// 给报表提供数据
		map.put("volume", volume);
		map.put("weight", weight);
		map.put("salesamount", salesamount);

		return map;
	}

    @Override
    public List<Map> getDeliveryGoodsSumData(String id) throws Exception {

	    // 配送单发货单列表
        List<DeliverySaleOut> list = deliveryMapper.getDeliverySaleoutList(id);
        // 商品汇总
        List<Map> goodsSumMapList = deliveryMapper.selectDeliveryGoodsSumData(list);
        for(Map goodsSumMap : goodsSumMapList) {
            String goodsid = (String) goodsSumMap.get("goodsid");
            BigDecimal unitnum = (BigDecimal) goodsSumMap.get("unitnum");
            GoodsInfo goods = getGoodsInfoByID(goodsid);
            Map goodsnum = countGoodsInfoNumber(goodsid, goods.getAuxunitid(), unitnum);
            goodsSumMap.put("auxnumdetail", goodsnum.get("auxnumdetail"));
            goodsSumMap.put("goodsname", goods.getName());
        }

        return goodsSumMapList;
    }

    /**
	 * 新增配送单时调用
	 * zhangHonghui 20150814 修改开始：
	 * 内容，根据是否拆单参数，重新计算单据数。
	 * 当参数值为2时，需要验证客户是否需要进行按品牌拆分。
	 * 验证规则，如果指定总店客户，则所有门店都要进行按品牌拆分单据；如果只指定门店，则该门店需要进行按品牌拆分单据。
	 * zhangHonghui 20150814 修改结束：
	 * @modifycontentEnd
	 */
	@Override
	public List getDeliveryCustomerByIds(String ids, String deliveryid,String lineid) {
		SysParam printSysParam = getBaseSysParamMapper().getSysParam("printnum");
		int printnum = (null != printSysParam && Integer.parseInt(printSysParam.getPvalue()) != 0) ? Integer.parseInt(printSysParam.getPvalue()) : 10;
		String status = "3";
		SysParam sysParam = getBaseSysParamMapper().getSysParam("StatusOfAddDeliverySourceList");
		if(null != sysParam){
			status = sysParam.getPvalue();
		}
		//查询是否要进行拆单
		SysParam saleOrderPrintSplitTypeParam=getBaseSysParamMapper().getSysParam("saleOrderPrintSplitType"); //销售打印拆单方式，0普通，1整散，2按客户进分按品牌拆单
		String saleOrderPrintSplitType="0";
		String[] brandSplitByCustomerArr=null;
		//获取的是否拆单的值
		if(null!=saleOrderPrintSplitTypeParam){
			saleOrderPrintSplitType=saleOrderPrintSplitTypeParam.getPvalue();
			//如果为空或者没有，则时普通单据
	   	 	if(null==saleOrderPrintSplitType || "".equals(saleOrderPrintSplitType.trim())){
	   	 		saleOrderPrintSplitType="0";
	   	 	}else{
	   	 		saleOrderPrintSplitType=saleOrderPrintSplitType.trim();
	   	 	}
	   	 	//判断是否按指定客户进行品牌拆单
	   	 	if("2".equals(saleOrderPrintSplitType)){
	   	 		//设置默认普通单据
	   	 		saleOrderPrintSplitType="0";
	   	 		//是否按品牌拆单的，指定客户
	   	 		SysParam printBrandSplitByCustomerParam=getBaseSysParamMapper().getSysParam("saleOrderPrintBrandSplitByCustomer");
	   	 		if(null!=printBrandSplitByCustomerParam){
	   	 			String printBrandSplitByCustomer=printBrandSplitByCustomerParam.getPvalue();
	   	 			if(null!=printBrandSplitByCustomer && !"".equals(printBrandSplitByCustomer.trim())){
	   	 				printBrandSplitByCustomer=printBrandSplitByCustomer.trim();
	   	 				//需要进行按指定客户进行品牌拆单
	   	 				saleOrderPrintSplitType="2";
	   	 				brandSplitByCustomerArr=printBrandSplitByCustomer.split(",");
	   	 			}
	   	 		}
	   	 	}
		}

		//
		/**新增代配送配送**/
		List<DeliveryCustomer> list=deliveryMapper.getCustomerListByAllDelevery(ids,printnum,deliveryid,status,lineid);
		/**新增代配送配送**/

		int seq = 1;
		for(DeliveryCustomer deliveryCustomer : list){

            // billtype = 3表示调拨单
            if("3".equals(deliveryCustomer.getBilltype())) {
                continue;
            }

            //是否拆单临时值
			String myOrderPrintSplitType=saleOrderPrintSplitType;
			//按指定客户进行品牌拆单
			if("2".equals(myOrderPrintSplitType)){
				//默认普通单据
				myOrderPrintSplitType="0";
				//当前的客户是否是断指定的客户
				if(ArrayUtils.contains(brandSplitByCustomerArr, deliveryCustomer.getCustomerid())){
					//按指定客户进行品牌拆单
					myOrderPrintSplitType="2";
				}else{
					//查询客户信息，使用总店编号进行判断是否是指定客户
                    Customer customerInfo=getBaseCustomerMapper().getCustomerById(deliveryCustomer.getCustomerid());
                    if(null!=customerInfo
                            && StringUtils.isNotEmpty(customerInfo.getPid())
                            && ArrayUtils.contains(brandSplitByCustomerArr, customerInfo.getPid())){
                        //按指定客户进行品牌拆单
                        myOrderPrintSplitType="2";
                    }
				}
			}
			
			if("2".equals(myOrderPrintSplitType)){
				//重新计算单据数
				Map paramMap=new HashMap();
				paramMap.put("customerid", deliveryCustomer.getCustomerid());
				paramMap.put("statusarr", status);
				paramMap.put("printnum", printnum);
				paramMap.put("saleoutidarr", ids);
				int billnums=deliveryMapper.getDeliveryBrandSplitBillCountByCustomer(paramMap);
				deliveryCustomer.setBillnums(billnums);
			}

			//排序
			deliveryCustomer.setSeq(seq++);
		}
		return list;
	}

	@Override
	public List getDeliveryCustomerNoRelateSaleout(String id,String delcustomerid) {
		Map map = new HashMap();
		map.put("deliveryid", id);
		if(StringUtils.isNotEmpty(delcustomerid)){
			map.put("delcustomerArr", delcustomerid.split(","));
		}
		return deliveryMapper.getDeliveryCustomerNoRelateSaleout(map);
	}

	@Override
	public int addDeliveryCustomerList(List<DeliveryCustomer> deliveryCustomerList) throws Exception {
		for (DeliveryCustomer deliveryCustomer : deliveryCustomerList) {
			int ret = deliveryMapper.addDeliveryCustomerList(deliveryCustomer);
			if (ret <= 0)
				throw new Exception("添加配送交接单失败!");
		}
		return 1;
	}

	@Override
	public Delivery getDelivery(String id) throws Exception {
		Delivery delivery = deliveryMapper.getDelivery(id);
		if(null != delivery){
			String drivername = "";
			String followname = "";
			if(StringUtils.isNotEmpty(delivery.getDriverid())){
				Personnel driver = personnelMapper.getPersonnelInfo(delivery.getDriverid());
				if(null!=driver){
					drivername = driver.getName();
				}
			}
			if(StringUtils.isNotEmpty(delivery.getFollowid())){
				Personnel follow = personnelMapper.getPersonnelInfo(delivery.getFollowid());
				if(null!=follow){
					followname = follow.getName();
				}
			}
			delivery.setDrivername(drivername);
			delivery.setFollowname(followname);
			delivery.setName("司机：" + drivername + "，跟车：" + followname);
			
			LogisticsCar car = logisticsMapper.getCar(delivery.getCarid());
			if(null != car){
				delivery.setCarname(car.getName());
			}
			
			if(StringUtils.isNotEmpty(delivery.getLineid())){
				String linename = logisticsMapper.getLineInfo(delivery.getLineid()).getName();
				delivery.setLinename(linename);
			}
			
			delivery.setCartypename(getCarType(delivery.getCartype()));
			delivery.setCount(delivery.getAuxnum().setScale(0,BigDecimal.ROUND_HALF_UP).toPlainString() + "，"
					+ delivery.getAuxremainder().setScale(0,BigDecimal.ROUND_HALF_UP).toPlainString());
		}
		return delivery;
	}

	private String getCarType(String cartype) {
		if (cartype.equals("1"))
			return "大车";
		if (cartype.equals("2"))
			return "中车";
		if (cartype.equals("3"))
			return "小车";
		return "大车";
	}

	@Override
	public int getTruckCount(Map map) throws Exception {
		return deliveryMapper.getTruckCount(map) + 1;
	}

	@Override
	public Map getDeliveryCustomerListBySaleoutids(String id,String lineid) throws Exception {
		SysParam printSysParam = getBaseSysParamMapper().getSysParam("printnum");
		int printnum = Integer.parseInt(printSysParam.getPvalue()) != 0 ? Integer.parseInt(printSysParam.getPvalue()) : 10;
		String status = "3";
		SysParam sysParam = getBaseSysParamMapper().getSysParam("StatusOfAddDeliverySourceList");
		if(null != sysParam){
			status = sysParam.getPvalue();
		}
		Map funcMap=new HashMap();
		//计算拆分单据
		funcMap.put("computeSplitType", "true");
		funcMap.put("statusarr", status);
		funcMap.put("printnum", printnum+"");
		funcMap.put("saleoutidarr", id);
		return fillDeliveryCustomerList(deliveryMapper.getDeliveryCustomerByIds(id, printnum,status,lineid),funcMap);
	}

	@Override
	public Map editDelivery(Delivery delivery, List<DeliverySaleOut> saleoutList, List<DeliveryCustomer> customerList)
			throws Exception {
		String id = delivery.getId();
		
		Delivery oldDelivery = deliveryMapper.getDelivery(id);
		if(null==oldDelivery || "4".equals(oldDelivery.getStatus())){
			Map map = new HashMap();
            map.put("msg","已关闭，不可保存!");
			map.put("flag", false);
			return map;
		}
		if (saleoutList != null) {
			//回写该配送单单据明细为为生成配送单状态
			Map map2 = new HashMap();
			map2.put("idsArr",id.split(","));
			updateSaleOutReceiptIsdelivery(0, map2);
			deliveryMapper.deleteDeliverySaleOut(id);
			// 添加发货单,并修改状态
			for (DeliverySaleOut deliverySaleOut : saleoutList) {
				deliveryMapper.addDeliverySaleOut(deliverySaleOut);
			}
		}else{
			if(StringUtils.isNotEmpty(delivery.getDelcustomerids())){
				String customeridstr = delivery.getDelcustomerids();
				String[] customerArr = customeridstr.substring(0, customeridstr.length()-1).split(",");
				Map map = new HashMap();
				map.put("deliveryid", delivery.getId());
				map.put("customerArr", customerArr);
				List<DeliverySaleOut> list = deliveryMapper.getDeliverySaleOutListByParam(map);
				for(DeliverySaleOut deliverySaleOut : list){
					if("0".equals(deliverySaleOut.getDeliverytype())){
						deliveryMapper.changeSaleOutDeliveryStatus(deliverySaleOut.getSaleoutid(), 0);
					}else if("1".equals(deliverySaleOut.getDeliverytype())){
						deliveryMapper.changeDeliveryOutDeliveryStatus(deliverySaleOut.getSaleoutid(), 0);
					}
					map.put("saleoutid", deliverySaleOut.getSaleoutid());
					deliveryMapper.deleteDeliverySaleOutByMap(map);
				}
			}
		}

		if (customerList != null) {
			BigDecimal auxnum = new BigDecimal(0);
			BigDecimal auxremainder = new BigDecimal(0);

			deliveryMapper.deleteDeliveryCustomer(id);
			for (DeliveryCustomer deliveryCustomer : customerList) {
				deliveryMapper.addDeliveryCustomerList(deliveryCustomer);
				// 计算总箱数,总件数
				auxnum = auxnum.add(deliveryCustomer.getAuxnum() == null ? new BigDecimal(0) : deliveryCustomer
						.getAuxnum());
				auxremainder = auxremainder.add(deliveryCustomer.getAuxremainder() == null ? new BigDecimal(0)
						: deliveryCustomer.getAuxremainder());
			}
			delivery.setAuxnum(auxnum);
			delivery.setAuxremainder(auxremainder);
		}
		int g = deliveryMapper.updateDelivery(delivery);

		Map map = new HashMap();
		boolean lockFlag = false;
		boolean flag = g > 0;
        if(flag){
            //回写发货单配货状态和回单配送状态
            Map map2 = new HashMap();
            map2.put("idsArr",delivery.getId().split(","));
            updateSaleOutReceiptIsdelivery(1,map2);
        }
		// map.put("lockFlag", lockFlag);
		map.put("flag", flag);

		return map;
	}

	public boolean updateDelivery(Delivery delivery) throws Exception {
		BigDecimal collectionamount = delivery.getCollectionamount();
		BigDecimal collectionsubsidy = new BigDecimal(0);
		//收款补助 超过10000 10000内按0.003计算 10000外按0.002计算
        BigDecimal base = BigDecimal.ZERO;
        SysParam baseSysParam = getBaseSysParamMapper().getSysParam("collectionSurpassAmount");
        if(null != baseSysParam){
            base = new BigDecimal(baseSysParam.getPvalue());
        }
        BigDecimal s = BigDecimal.ZERO;
        SysParam sSysParam = getBaseSysParamMapper().getSysParam("surpassRate");
        if(null != sSysParam){
            s = new BigDecimal(sSysParam.getPvalue());
        }
		BigDecimal us = BigDecimal.ZERO;
        SysParam usSysParam = getBaseSysParamMapper().getSysParam("unSurpassRate");
        if(null != usSysParam){
            us = new BigDecimal(usSysParam.getPvalue());
        }
		if (collectionamount.compareTo(base) > 0) {
            BigDecimal surpassAmount = base.multiply(us);
			collectionsubsidy = surpassAmount.add(collectionamount.subtract(base).multiply(s));
		} else {
			collectionsubsidy = collectionamount.multiply(us);
		}
		//
		delivery.setCollectionsubsidy(collectionsubsidy);
		if (deliveryMapper.updateDelivery(delivery) < 0)
			throw new Exception("更新配送单出错!");
		return true;
	}

    /**
     * 根据配送单编码集回写发货单、代配送出库单、回单配送状态
     * @param map
     * @return
     * @throws Exception
     */
    private boolean updateSaleOutReceiptIsdelivery(int flag,Map map)throws Exception{
        //配送单编码
        String[] idsArr = null != map.get("idsArr") ? (String[])map.get("idsArr") : new String[0];
        if(idsArr.length != 0){
            //回写发货单配送状态为"已配货"状态
            for(String deliveryid : idsArr){
                deliveryMapper.updateSaleOutIsdelivery(deliveryid,flag);
				deliveryMapper.updateDeliveryOutIsdelivery(deliveryid, flag);
                deliveryMapper.updateAllocateOutIsdelivery(deliveryid, flag);
            }
            //回写回单配送状态
            List<String> list = receiptMapper.getReceiptidsList(map);
            if(list.size() != 0){
                for(String id : list){
                    map.put("id",id);
                    Receipt receipt = receiptMapper.getReceipt(map);
                    String isdelivery = getIsDeliveryByReceiptSourceids(receipt);
                    receipt.setIsdelivery(isdelivery);
                    receiptMapper.updateReceipt(receipt);
                }
            }
        }
        return true;
    }

	@Override
	public boolean doAuditDeliverys(Map map) throws Exception {
		int ret = deliveryMapper.auditDeliverys(map);
		if (ret <= 0){
            throw new Exception("审核配送单出错!");
        }else{
            //回写发货单配货状态和回单配送状态
            updateSaleOutReceiptIsdelivery(2,map);
        }
		return ret > 0;
	}

	@Override
	public boolean changeCheckDelivery(String id, String flag) throws Exception {
		return deliveryMapper.changeCheckDelivery(id, flag) > 0;
	}

	@Override
	public int addLogisticsReport(String id,String type) throws Exception {
		Delivery delivery = deliveryMapper.getDelivery(id);
		Map map = getDeliverySaleoutList(id);
		LogisticsReport logisticsReport = new LogisticsReport();
		// LogisticsReport logisticsReport2 = new LogisticsReport();
		logisticsReport.setDeliveryid(id);
		logisticsReport.setRemark(delivery.getRemark());

		logisticsReport.setAdduserid(delivery.getAdduserid());
		logisticsReport.setAddusername(delivery.getAddusername());
		logisticsReport.setAdddeptid(delivery.getAdddeptid());
		logisticsReport.setAdddeptname(delivery.getAdddeptname());
		logisticsReport.setAddtime(delivery.getAddtime());
		logisticsReport.setModifyuserid(delivery.getModifyuserid());
		logisticsReport.setModifyusername(delivery.getModifyusername());
		logisticsReport.setModifytime(delivery.getModifytime());
		logisticsReport.setAudituserid(delivery.getAudituserid());
		logisticsReport.setAuditusername(delivery.getAuditusername());
		logisticsReport.setAudittime(delivery.getAudittime());
		logisticsReport.setStopuserid(delivery.getStopuserid());
		logisticsReport.setStopusername(delivery.getStopusername());
		logisticsReport.setStoptime(delivery.getStoptime());
		logisticsReport.setClosetime(delivery.getClosetime());
		logisticsReport.setPrinttimes(delivery.getPrinttimes());
		logisticsReport.setLineid(delivery.getLineid());
		logisticsReport.setCarid(delivery.getCarid());
		logisticsReport.setCartype(delivery.getCartype());
		logisticsReport.setDriverid(delivery.getDriverid());
		logisticsReport.setIsdriver("1");
		logisticsReport.setCustomernums(delivery.getCustomernums());
		logisticsReport.setAuxnum(delivery.getAuxnum());
		logisticsReport.setBoxnum(delivery.getBoxnum());
		logisticsReport.setAuxremainder(delivery.getAuxremainder());
		//
		logisticsReport.setVolume((BigDecimal) map.get("volume"));
		logisticsReport.setWeight((BigDecimal) map.get("weight"));
		logisticsReport.setSalesamount((BigDecimal) map.get("salesamount"));
		//销售额添加未关联发货单的合计数据
		DeliveryCustomer deliveryCustomer = deliveryMapper.getDeliveryCustomerNoRelateSaleoutSum(delivery.getId());
		if(null != deliveryCustomer){
			logisticsReport.setSalesamount(logisticsReport.getSalesamount().add(deliveryCustomer.getSalesamount()));
		}

		logisticsReport.setTruck(delivery.getTruck());
		logisticsReport.setTrucksubsidy(delivery.getTrucksubsidy());
		logisticsReport.setCarallowance(delivery.getCarallowance());
		logisticsReport.setCarsubsidy(delivery.getCarsubsidy());
		logisticsReport.setCustomersubsidy(delivery.getCustomersubsidy());
		logisticsReport.setCollectionamount(delivery.getCollectionamount());

		logisticsReport.setCollectionsubsidy(delivery.getCollectionsubsidy());
		logisticsReport.setOthersubsidy(delivery.getOthersubsidy());
		logisticsReport.setSafebonus(delivery.getSafebonus());
		logisticsReport.setReceiptbonus(delivery.getReceiptbonus());
		logisticsReport.setNightbonus(delivery.getNightbonus());

		//销售补助计算
		BigDecimal salessubsidy = BigDecimal.ZERO;//销售补助
		BigDecimal salesSurpassAmount = BigDecimal.ZERO;//超过销售总金额
		BigDecimal salesSurpassRate = BigDecimal.ZERO;//超过率
		BigDecimal salesUnSurpassRate = BigDecimal.ZERO;//未超过率
		SysParam salesSurpassAmountSysParam = getBaseSysParamMapper().getSysParam("salesSurpassAmount");
		if(null != salesSurpassAmountSysParam){
			salesSurpassAmount = new BigDecimal(salesSurpassAmountSysParam.getPvalue());
		}
		SysParam salesSurpassRateSysParam = getBaseSysParamMapper().getSysParam("salesSurpassRate");
		if(null != salesSurpassRateSysParam){
			salesSurpassRate = new BigDecimal(salesSurpassRateSysParam.getPvalue());
		}
		SysParam salesUnSurpassRateSysParam = getBaseSysParamMapper().getSysParam("salesUnSurpassRate");
		if(null != salesUnSurpassRateSysParam){
			salesUnSurpassRate = new BigDecimal(salesUnSurpassRateSysParam.getPvalue());
		}
		//销售补助计算公式：salesamount > salesSurpassAmount, salessubsidy = (salesamount-salesSurpassAmount)*salesSurpassRate+salesSurpassAmount*salesUnSurpassRate,否则，salessubsidy=salesamount*salesUnSurpassRate;
		if(null != logisticsReport.getSalesamount() && logisticsReport.getSalesamount().compareTo(salesSurpassAmount) > 0){
			salessubsidy = logisticsReport.getSalesamount().subtract(salesSurpassAmount).multiply(salesSurpassRate).add(salesSurpassAmount.multiply(salesUnSurpassRate));
		}else{
			salessubsidy = logisticsReport.getSalesamount().multiply(salesUnSurpassRate);
		}
		logisticsReport.setSalessubsidy(salessubsidy);

		//销售补助比例
		String LogisticsSalesSubsidy = "0:0";
		BigDecimal salesDR = BigDecimal.ZERO;
		BigDecimal salesFR = BigDecimal.ZERO;
		SysParam salesSysParam = getBaseSysParamMapper().getSysParam("LogisticsSalesSubsidy");
		if(null != salesSysParam){
			LogisticsSalesSubsidy = salesSysParam.getPvalue();
		}
		if(StringUtils.isNotEmpty(LogisticsSalesSubsidy)){
			String[] LSSArr = LogisticsSalesSubsidy.split(":");
			salesDR = new BigDecimal(LSSArr[0]);
			salesFR = new BigDecimal(LSSArr[1]);
		}
		//晚上出车比例
		String LogisticsNightBonus = "0:0";
		BigDecimal nightDR = BigDecimal.ZERO;
		BigDecimal nightFR = BigDecimal.ZERO;
		SysParam nightSysParam = getBaseSysParamMapper().getSysParam("LogisticsNightBonus");
		if(null != nightSysParam){
			LogisticsNightBonus = nightSysParam.getPvalue();
		}
		if(StringUtils.isNotEmpty(LogisticsNightBonus)){
			String[] LNBArr = LogisticsNightBonus.split(":");
			nightDR = new BigDecimal(LNBArr[0]);
			nightFR = new BigDecimal(LNBArr[1]);
		}
		//安全奖金比例
		String LogisticsSafeBonus = "0:0";
		BigDecimal safeDR = BigDecimal.ZERO;
		BigDecimal safeFR = BigDecimal.ZERO;
		SysParam safeSysParam = getBaseSysParamMapper().getSysParam("LogisticsSafeBonus");
		if(null != safeSysParam){
			LogisticsSafeBonus = safeSysParam.getPvalue();
		}
		if(StringUtils.isNotEmpty(LogisticsSafeBonus)){
			String[] LSBArr = LogisticsSafeBonus.split(":");
			safeDR = new BigDecimal(LSBArr[0]);
			safeFR = new BigDecimal(LSBArr[1]);
		}
		//回单奖金比例
		String LogisticsReceiptBonus = "0:0";
		BigDecimal receiptDR = BigDecimal.ZERO;
		BigDecimal receiptFR = BigDecimal.ZERO;
		SysParam receiptSysParam = getBaseSysParamMapper().getSysParam("LogisticsReceiptBonus");
		if(null != receiptSysParam){
			LogisticsReceiptBonus = receiptSysParam.getPvalue();
		}
		if(StringUtils.isNotEmpty(LogisticsReceiptBonus)){
			String[] LRBArr = LogisticsReceiptBonus.split(":");
			receiptDR = new BigDecimal(LRBArr[0]);
			receiptFR = new BigDecimal(LRBArr[1]);
		}
		//其他补助比例
		String LogisticsOtherSubsidy = "0:0";
		BigDecimal otherDR = BigDecimal.ZERO;
		BigDecimal otherFR = BigDecimal.ZERO;
		SysParam otherSysParam = getBaseSysParamMapper().getSysParam("LogisticsOtherSubsidy");
		if(null != otherSysParam){
			LogisticsOtherSubsidy = otherSysParam.getPvalue();
		}
		if(StringUtils.isNotEmpty(LogisticsOtherSubsidy)){
			String[] LOSArr = LogisticsOtherSubsidy.split(":");
			otherDR = new BigDecimal(LOSArr[0]);
			otherFR = new BigDecimal(LOSArr[1]);
		}
		//出车津贴比例
		String LogisticsCarAllowance = "0:0";
		BigDecimal carDR = BigDecimal.ZERO;
		BigDecimal carFR = BigDecimal.ZERO;
		SysParam carSysParam = getBaseSysParamMapper().getSysParam("LogisticsCarAllowance");
		if(null != carSysParam){
			LogisticsCarAllowance = carSysParam.getPvalue();
		}
		if(StringUtils.isNotEmpty(LogisticsCarAllowance)){
			String[] LCAArr = LogisticsCarAllowance.split(":");
			carDR = new BigDecimal(LCAArr[0]);
			carFR = new BigDecimal(LCAArr[1]);
		}
		//出车补助比例
		String LogisticsCarSubsidy = "0:0";
		BigDecimal carsDR = BigDecimal.ZERO;
		BigDecimal carsFR = BigDecimal.ZERO;
		SysParam carsSysParam = getBaseSysParamMapper().getSysParam("LogisticsCarSubsidy");
		if(null != carsSysParam){
			LogisticsCarSubsidy = carsSysParam.getPvalue();
		}
		if(StringUtils.isNotEmpty(LogisticsCarSubsidy)){
			String[] LCSArr = LogisticsCarSubsidy.split(":");
			carsDR = new BigDecimal(LCSArr[0]);
			carsFR = new BigDecimal(LCSArr[1]);
		}
		//家数补助比例
		String LogisticsCustomerSubsidy = "0:0";
		BigDecimal customerDR = BigDecimal.ZERO;
		BigDecimal customerFR = BigDecimal.ZERO;
		SysParam customerSysParam = getBaseSysParamMapper().getSysParam("LogisticsCustomerSubsidy");
		if(null != customerSysParam){
			LogisticsCustomerSubsidy = customerSysParam.getPvalue();
		}
		if(StringUtils.isNotEmpty(LogisticsCustomerSubsidy)){
			String[] LCusSArr = LogisticsCustomerSubsidy.split(":");
			customerDR = new BigDecimal(LCusSArr[0]);
			customerFR = new BigDecimal(LCusSArr[1]);
		}
		//收款补助比例
		String LogisticsCollectionSubsidy = "0:0";
		BigDecimal cllDR = BigDecimal.ZERO;
		BigDecimal cllFR = BigDecimal.ZERO;
		SysParam cllSysParam = getBaseSysParamMapper().getSysParam("LogisticsCollectionSubsidy");
		if(null != cllSysParam){
			LogisticsCollectionSubsidy = cllSysParam.getPvalue();
		}
		if(StringUtils.isNotEmpty(LogisticsCollectionSubsidy)){
			String[] LCllSArr = LogisticsCollectionSubsidy.split(":");
			cllDR = new BigDecimal(LCllSArr[0]);
			cllFR = new BigDecimal(LCllSArr[1]);
		}
		//装车补助比例
		String LogisticsTruckSubsidy = "0:0";
		BigDecimal truckDR = BigDecimal.ZERO;
		BigDecimal truckFR = BigDecimal.ZERO;
		SysParam truckSysParam = getBaseSysParamMapper().getSysParam("LogisticsTruckSubsidy");
		if(null != truckSysParam){
			LogisticsTruckSubsidy = truckSysParam.getPvalue();
		}
		if(StringUtils.isNotEmpty(LogisticsTruckSubsidy)){
			String[] LTSArr = LogisticsTruckSubsidy.split(":");
			truckDR = new BigDecimal(LTSArr[0]);
			truckFR = new BigDecimal(LTSArr[1]);
		}

		//合计金额（取司机对应比例） = （出车津贴*比例+装车补助*比例+其他补助*比例）+（出车补助*比例+家数补助*比例）+（收款补助*比例）+ (销售补助*比例)
		BigDecimal aD = delivery.getCarallowance().multiply(carDR).add(delivery.getTrucksubsidy().multiply(truckDR)).add(delivery.getOthersubsidy().multiply(otherDR));
		BigDecimal bD = delivery.getCarsubsidy().multiply(carsDR).add(delivery.getCustomersubsidy().multiply(customerDR));
		BigDecimal cD = delivery.getCollectionsubsidy().multiply(cllDR);
		BigDecimal sD = BigDecimal.ZERO;
		if(null != logisticsReport.getSalessubsidy()){
			sD = logisticsReport.getSalessubsidy().multiply(salesDR);
		}

		BigDecimal subamount = aD.add(bD).add(cD).add(sD);
		logisticsReport.setSubamount(subamount);

		logisticsReport.setTotalamount(subamount.add(delivery.getSafebonus().multiply(safeDR)).add(delivery.getReceiptbonus().multiply(receiptDR)).add(
				delivery.getNightbonus().multiply(nightDR)));
		if("1".equals(type)){
			logisticsReport.setTablename("t_report_storage_logistics_follow");
		}
		int ret = deliveryMapper.addLogisticsReport(logisticsReport);
		if (ret > 0) {
			//合计金额（取司机对应比例） = （出车津贴*比例+装车补助*比例+其他补助*比例）+（出车补助*比例+家数补助*比例）+（收款补助*比例）+ (销售补助*比例)
			BigDecimal aF = delivery.getCarallowance().multiply(carFR).add(delivery.getTrucksubsidy().multiply(truckFR)).add(delivery.getOthersubsidy().multiply(otherFR));
			BigDecimal bF = delivery.getCarsubsidy().multiply(carsFR).add(delivery.getCustomersubsidy().multiply(customerFR));
			BigDecimal cF = delivery.getCollectionsubsidy().multiply(cllFR);
			BigDecimal sF = BigDecimal.ZERO;
			if(null != logisticsReport.getSalessubsidy()){
				sF = logisticsReport.getSalessubsidy().multiply(salesFR);
			}
			subamount = aF.add(bF).add(cF).add(sF);
			BigDecimal totalamount = subamount.add(delivery.getSafebonus().multiply(safeFR)).add(delivery.getReceiptbonus().multiply(receiptFR)).add(
					delivery.getNightbonus().multiply(nightFR));
			if("0".equals(type)){
				logisticsReport.setDriverid(delivery.getFollowid());
				logisticsReport.setIsdriver("0");
				logisticsReport.setSubamount(subamount);
				logisticsReport.setTotalamount(totalamount);
				ret = deliveryMapper.addLogisticsReport(logisticsReport);
			}else if("1".equals(type)){//含多个跟车员的那张报表
				logisticsReport.setTablename("t_report_storage_logistics_follow");
				if(StringUtils.isEmpty(delivery.getFollowid()) || !delivery.getFollowid().contains(",")){
					logisticsReport.setDriverid(delivery.getFollowid());
					logisticsReport.setIsdriver("0");
					logisticsReport.setSubamount(subamount);
					logisticsReport.setTotalamount(totalamount);
					//多跟车员的物流奖金报表
					logisticsReport.setTablename("t_report_storage_logistics_follow");
					ret = deliveryMapper.addLogisticsReport(logisticsReport);
				}else{

					String[] followids = delivery.getFollowid().split(",");
                    BigDecimal flength = new BigDecimal(followids.length);
                    if(flength.compareTo(BigDecimal.ZERO)==0){
                        flength = BigDecimal.ONE;
                    }
                    BigDecimal salesamount = logisticsReport.getSalesamount().divide(flength,2,BigDecimal.ROUND_HALF_UP);
                    BigDecimal trucksubsidy = logisticsReport.getTrucksubsidy().divide(flength,2,BigDecimal.ROUND_HALF_UP);
                    BigDecimal carallowance = logisticsReport.getCarallowance().divide(flength,2,BigDecimal.ROUND_HALF_UP);
                    BigDecimal carsubsidy = logisticsReport.getCarsubsidy().divide(flength,2,BigDecimal.ROUND_HALF_UP);
                    BigDecimal customersubsidy = logisticsReport.getCustomersubsidy().divide(flength,2,BigDecimal.ROUND_HALF_UP);
                    BigDecimal collectionamount = logisticsReport.getCollectionamount().divide(flength,2,BigDecimal.ROUND_HALF_UP);
                    BigDecimal salessubsidy1 = logisticsReport.getSalessubsidy().divide(flength,2,BigDecimal.ROUND_HALF_UP);
                    BigDecimal collectionsubsidy = logisticsReport.getCollectionsubsidy().divide(flength,2,BigDecimal.ROUND_HALF_UP);
                    BigDecimal othersubsidy = logisticsReport.getOthersubsidy().divide(flength,2,BigDecimal.ROUND_HALF_UP);
                    BigDecimal safebonus = logisticsReport.getSafebonus().divide(flength,2,BigDecimal.ROUND_HALF_UP);
                    BigDecimal receiptbonus = logisticsReport.getReceiptbonus().divide(flength,2,BigDecimal.ROUND_HALF_UP);
                    BigDecimal nightbonus = logisticsReport.getNightbonus().divide(flength,2,BigDecimal.ROUND_HALF_UP);
                    BigDecimal subamount1 = logisticsReport.getSubamount().divide(flength,2,BigDecimal.ROUND_HALF_UP);
                    BigDecimal totalamount1 = logisticsReport.getTotalamount().divide(flength,2,BigDecimal.ROUND_HALF_UP);

					for (int i = 0; i < followids.length; i++) {
						LogisticsReport follow = (LogisticsReport)CommonUtils.deepCopy(logisticsReport);
						follow.setDriverid(followids[i]);
						follow.setIsdriver("0");
						follow.setSalesamount(salesamount);
						follow.setTrucksubsidy(trucksubsidy);
						follow.setCarallowance(carallowance);
						follow.setCarsubsidy(carsubsidy);
						follow.setCustomersubsidy(customersubsidy);
						follow.setCollectionamount(collectionamount);
						follow.setSalessubsidy(salessubsidy1);
						follow.setCollectionsubsidy(collectionsubsidy);
						follow.setOthersubsidy(othersubsidy);
						follow.setSafebonus(safebonus);
						follow.setReceiptbonus(receiptbonus);
						follow.setNightbonus(nightbonus);
						follow.setSubamount(subamount1);
						follow.setTotalamount(totalamount1);
						//多跟车员的物流奖金报表
						follow.setTablename("t_report_storage_logistics_follow");
						ret = deliveryMapper.addLogisticsReport(follow);
					}

				}

			}

		} else {
			throw new Exception("添加物流报表出错");
		}

		return ret;
	}

	@Override
	public void updateDeliveryCustomer(DeliveryCustomer deliveryCustomer) throws Exception {
		if (deliveryMapper.updateDeliveryCustomer(deliveryCustomer) < 0) {
			throw new Exception("更新交接单出错");
		}
	}

	@Override
	public void closeDelivery(Delivery delivery) throws Exception {
		if (deliveryMapper.closeDelivery(delivery) < 0) {
			throw new Exception("将配送单设置为关闭状态出错");
		}
	}

	@Override
	public boolean deleteDeliverys(Map map, String deliveryIds) throws Exception {
		// 取出每个deliveryid,删除对应的交接单和配送单
		String deliveryArr[] = deliveryIds.split(",");
        //回写发货单配货状态和回单配送状态
        updateSaleOutReceiptIsdelivery(0,map);
		for (int i = 0; i < deliveryArr.length; i++) {
			String id = deliveryArr[i];
			deliveryMapper.deleteDeliveryCustomer(id);
			deliveryMapper.deleteDeliverySaleOut(id);
		}
		return deliveryMapper.deleteDeliverys(map) > 0;
	}

	

	@Override
	public void updatePrintTimes(String id) throws Exception {

		if (deliveryMapper.updatePrintTimes(id) < 0)
			throw new Exception("更新打印次数出错");

	}

	@Override
	public Map saveAuditDelivery(Delivery delivery, String saleout, String customer) throws Exception {
        Delivery oldDelivery = deliveryMapper.getDelivery(delivery.getId());
        if(null==oldDelivery || "3".equals(oldDelivery.getStatus()) || "4".equals(oldDelivery.getStatus())){
            Map map = new HashMap();
            map.put("flag", false);
            return map;
        }
        List<DeliverySaleOut> saleoutList = null;
		List<DeliveryCustomer> customerList = null;

		if (saleout != null) {
			saleoutList = JSONUtils.jsonStrToList(saleout, new DeliverySaleOut());
		}
		if (customer != null) {
			customerList = JSONUtils.jsonStrToList(customer, new DeliveryCustomer());
			for (DeliveryCustomer deliveryCustomer : customerList) {
				deliveryCustomer.setDeliveryid(delivery.getId());
				if(StringUtils.isEmpty(deliveryCustomer.getIssaleout())){
					deliveryCustomer.setIssaleout("1");
				}
			}
		}

		SysUser sysUser = getSysUser();
		delivery.setModifyuserid(sysUser.getUserid());
		delivery.setModifyusername(sysUser.getName());
		// 保存
		Map map = editDelivery(delivery, saleoutList, customerList);
		boolean flag = (Boolean) map.get("flag");
		if (!flag)
			throw new Exception("配送单保存过程出错。");

		String oldIdStr = delivery.getId();
		String newIdStr = "";
		String[] idArr = oldIdStr.split(",");
		int invalidNum = 0, num = 0;
		Map retMap = new HashMap();
		// 检测要审核的状态，选中记录的状态为“保存”状态下才可审核
		for (int i = 0; i < idArr.length; i++) {
			delivery = getDelivery(idArr[i]);
			if (delivery != null) {
				if (!"2".equals(delivery.getStatus())) {// 状态不为保存启用无效
					invalidNum += 1;
				} else {
					if (StringUtils.isNotEmpty(newIdStr)) {
						newIdStr += "," + idArr[i];
					} else {
						newIdStr = idArr[i];
					}
				}
			}
		}
		if (StringUtils.isNotEmpty(newIdStr)) {

			LogisticsCar car = logisticsMapper.getDefaultCarByLineId(delivery.getCarid());
			

			// Delivery delivery = deliveryService.getDelivery(id);
			Map mapTruck = new HashMap();
			mapTruck.put("carid", car.getId());
			mapTruck.put("businessdate", delivery.getBusinessdate());
			mapTruck.put("status", "3");

			int truck = getTruckCount(mapTruck);
			delivery.setTruck(truck);
			if (truck == 1)
				delivery.setTrucksubsidy(car.getTruck1());
			else if (truck == 2)
				delivery.setTrucksubsidy(car.getTruck2());
			else if (truck == 3)
				delivery.setTrucksubsidy(car.getTruck3());
			else if (truck == 4)
				delivery.setTrucksubsidy(car.getTruck4());
			else if (truck >= 5)
				delivery.setTrucksubsidy(car.getTruck5());

			updateDelivery(delivery);

			num = idArr.length - invalidNum;
			map = new HashMap();
			map.put("idsArr", newIdStr.split(","));
			map.put("audituserid", sysUser.getUserid());
			map.put("auditusername", sysUser.getName());
			// boolean flag = logisticsService.enableLineInfos(map);
			flag = doAuditDeliverys(map);

		}
		// 添加日志内容
		retMap.put("flag", flag);
		retMap.put("id", newIdStr);
		retMap.put("invalidNum", invalidNum);
		retMap.put("num", num);
		return retMap;
	}

	public Map auditDeliverys(String oldIdStr) throws Exception {
		String newIdStr = "";
		String[] idArr = oldIdStr.split(",");
		int invalidNum = 0, num = 0;
		Map retMap = new HashMap();
		// 检测要审核的状态，选中记录的状态为“保存”状态下才可审核
		for (int i = 0; i < idArr.length; i++) {
			Delivery delivery = getDelivery(idArr[i]);
			if (delivery != null) {
				if (!"2".equals(delivery.getStatus())) {
					invalidNum += 1;
				} else {
					if (StringUtils.isNotEmpty(newIdStr)) {
						newIdStr += "," + idArr[i];
					} else {
						newIdStr = idArr[i];
					}
				}
			}
		}
		boolean flag = false;
		if (StringUtils.isNotEmpty(newIdStr)) {
			SysUser sysUser = getSysUser();
			for (String id : newIdStr.split(",")) {
				Delivery delivery = getDelivery(id);
				LogisticsCar car = logisticsMapper.getDefaultCarByLineId(delivery.getCarid());

				Map mapTruck = new HashMap();
				mapTruck.put("carid", car.getId());
				mapTruck.put("businessdate", delivery.getBusinessdate());
				mapTruck.put("status", "3");

				int truck = getTruckCount(mapTruck);
				delivery.setTruck(truck);
				if (truck == 1)
					delivery.setTrucksubsidy(car.getTruck1());
				else if (truck == 2)
					delivery.setTrucksubsidy(car.getTruck2());
				else if (truck == 3)
					delivery.setTrucksubsidy(car.getTruck3());
				else if (truck == 4)
					delivery.setTrucksubsidy(car.getTruck4());
				else if (truck >= 5)
					delivery.setTrucksubsidy(car.getTruck5());

				updateDelivery(delivery);

				Map map = new HashMap();
				map.put("idsArr", id.split(","));
				map.put("audituserid", sysUser.getUserid());
				map.put("auditusername", sysUser.getName());
				flag = doAuditDeliverys(map);
			}

			num = idArr.length - invalidNum;
		}else{
			newIdStr = oldIdStr;
		}
		// 添加日志内容
		retMap.put("flag", flag);
		retMap.put("id", newIdStr);
		retMap.put("invalidNum", invalidNum);
		retMap.put("num", num);
		return retMap;
	}

	public String getDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(new Date());
	}

	@Override
	public Map oppauditDeliverys(String oldIdStr) throws Exception {
		String newIdStr = "",msg = "";
		String[] idArr = oldIdStr.split(",");
		int invalidNum = 0, num = 0;
		Map retMap = new HashMap();
		// 检测要反审核的状态，选中记录的状态为“审核”状态下才可反审
		for (int i = 0; i < idArr.length; i++) {
			Delivery delivery = getDelivery(idArr[i]);
			if (delivery != null) {
				if (!"3".equals(delivery.getStatus())) {
					invalidNum += 1;
					continue;
				}
				// 判断是否最后一次装车
				LogisticsCar car = logisticsMapper.getDefaultCarByLineId(delivery.getCarid());

				Map mapTruck = new HashMap();
				mapTruck.put("carid", car.getId());
				mapTruck.put("businessdate", delivery.getBusinessdate());
				mapTruck.put("trucknum", delivery.getTruck());
				//mapTruck.put("status", "3");
				//int truck = getTruckCount(mapTruck);
				//判断是否存在大于最小装车次数的数据，存在true，则不是最有一次装车，否则false是
				boolean islast = deliveryMapper.checkIsLastTruck(mapTruck) > 0;
				if (islast) {
					msg = "只能反审最后一次装车";
					continue;
				}
				// 合计出可以修改的ID
				if (StringUtils.isNotEmpty(newIdStr)) {
					newIdStr += "," + idArr[i];
				} else {
					newIdStr = idArr[i];
				}

			}
		}
		boolean flag = false;
		if (StringUtils.isNotEmpty(newIdStr)) {
			for (String id : newIdStr.split(",")) {
				Delivery delivery = getDelivery(id);
				Map map = new HashMap();
				map.put("idsArr", id.split(","));
				flag = deliveryMapper.oppauditDeliverys(map) > 0;
                if(flag){
                    //回写发货单配货状态和回单配送状态
                    updateSaleOutReceiptIsdelivery(1,map);
                }
			}
			num = idArr.length - invalidNum;
		}
		// 添加日志内容
		if(StringUtils.isEmpty(newIdStr)){
			newIdStr = oldIdStr;
		}
		retMap.put("id", newIdStr);
		retMap.put("flag", flag);
		retMap.put("invalidNum", invalidNum);
		retMap.put("num", num);
		retMap.put("msg", msg);
		return retMap;
	}

	@Override
	public Map doCheckDelivery(String id, String customer) throws Exception {
		Map map = new HashMap();
		boolean flag = false;
		Delivery delivery = getDelivery(id);
		String status = "";
		if (delivery != null)
			status = delivery.getStatus();
		if (delivery != null && "3".equals(status)) {
//			List<DeliveryCustomer> customerList = null;
			
//			if (customer != null) {
//				BigDecimal collectionamountTotal = BigDecimal.ZERO;
//				// 把json字符串转换成对象
//				customerList = JSONUtils.jsonStrToList(customer, new DeliveryCustomer());
//				for (DeliveryCustomer deliveryCustomer : customerList) {
//					deliveryCustomer.setDeliveryid(delivery.getId());
//					// 更新回款数据
//					updateDeliveryCustomer(deliveryCustomer);
//					collectionamountTotal = collectionamountTotal.add((null != deliveryCustomer.getCollectionamount()) ? deliveryCustomer.getCollectionamount() : BigDecimal.ZERO);
//				}
//				delivery.setCollectionamount(collectionamountTotal);
//			}
			// 更新 回款金额和回款补助
			String checkdate = CommonUtils.getTodayDataStr();
			delivery.setCheckdate(checkdate);
			updateDelivery(delivery);
			// 将状态修改为关闭
			SysUser sysUser = getSysUser();
			delivery.setCheckuserid(sysUser.getUserid());
			delivery.setCheckusername(sysUser.getUsername());
			closeDelivery(delivery);
			boolean ret = changeCheckDelivery(id, "1");
			if (ret) {
                //回写发货单配货状态和回单配送状态
                Map map2 = new HashMap();
                map2.put("idsArr",id.split(","));
                updateSaleOutReceiptIsdelivery(3,map2);
                deliveryMapper.addLineInfoCustomerCarNum(delivery.getLineid(),delivery.getId());
				flag = addLogisticsReport(id,"0") > 0;
			}
		}
		map.put("flag", flag);
		return map;
	}

	@Override
	public Map doUnCheckDelivery(String id) throws Exception {
		Map map = new HashMap();
		boolean flag = false;
		Delivery delivery = getDelivery(id);
		String status = "";
		if (delivery != null)
			status = delivery.getStatus();
		if (delivery != null && "4".equals(status)) {
			boolean ret = deliveryMapper.unCheckDelivery(id) > 0;
			if (ret){
                //回写发货单配货状态和回单配送状态
                Map map2 = new HashMap();
                map2.put("idsArr",id.split(","));
                updateSaleOutReceiptIsdelivery(2,map2);
                deliveryMapper.deleteLineInfoCustomerCarNum(delivery.getLineid(),delivery.getId());
                flag = deliveryMapper.deleteLogisticsReport(id) > 0;
            }
		}
		map.put("flag", flag);
		return map;
	}
	
	@Override
	public Map getTruckSubsidyByTruck(String truckstr, String carid)
			throws Exception {
		Map map = new HashMap();
		BigDecimal trucksubsidy = BigDecimal.ZERO;
		Integer truck = Integer.valueOf(truckstr);
		LogisticsCar car = logisticsMapper.getCar(carid);
		if(null != car){
			if(1 == truck){
				trucksubsidy = (null != car.getTruck1()) ? car.getTruck1() : BigDecimal.ZERO;
			}else if(2 == truck){
				trucksubsidy = (null != car.getTruck2()) ? car.getTruck2() : BigDecimal.ZERO;
			}else if(3 == truck){
				trucksubsidy = (null != car.getTruck3()) ? car.getTruck3() : BigDecimal.ZERO;
			}else if(4 == truck){
				trucksubsidy = (null != car.getTruck4()) ? car.getTruck4() : BigDecimal.ZERO;
			}else if(truck >= 5){
				trucksubsidy = (null != car.getTruck5()) ? car.getTruck5() : BigDecimal.ZERO;
			}
		}
		map.put("trucksubsidy", trucksubsidy);
		return map;
	}

	@Override
	public Map getCustomersubsidyByCustomernums(String customernumsStr,
			String lineid) throws Exception {
		Map map = new HashMap();
		BigDecimal customersubsidy = BigDecimal.ZERO;
		Integer customernums = Integer.parseInt(customernumsStr);
		LogisticsLine line = logisticsMapper.getLineInfo(lineid);
		if(null != line){
			if(customernums <= line.getBasecustomers()){
				customersubsidy = line.getBaseallowance();
			}else{
				customersubsidy = (new BigDecimal(customernums)).multiply(line.getSingleallowance());
			}
		}
		map.put("customersubsidy", customersubsidy);
		return map;
	}

	@Override
	public boolean isExistSameTruck(String truckstr, String carid,
			String businessdate) throws Exception {
		Map map = new HashMap();
		map.put("truck", Integer.parseInt(truckstr));
		map.put("carid", carid);
		map.put("businessdate", businessdate);
		boolean flag = deliveryMapper.isExistSameTruck(map) > 0;
		return flag;
	}

	@Override
	public List<DeliverySaleOut> getDeliverySaleAndDeliveryListByIds(String ids) {
		String status = "3";
		SysParam sysParam = getBaseSysParamMapper().getSysParam("StatusOfAddDeliverySourceList");
		if(null != sysParam){
			status = sysParam.getPvalue();
		}
		Map map = new HashMap();
		map.put("status", status);
		map.put("ids",ids);
		List<DeliverySaleOut> saleOutList=deliveryMapper.getDeliverySaleOutListByIds(map);
		return saleOutList;
	}
}
