/**
 * @(#)DispatchBillServiceImpl.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 18, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.DispatchBill;
import com.hd.agent.sales.model.DispatchBillDetail;
import com.hd.agent.sales.service.IDispatchBillService;
import com.hd.agent.sales.service.ext.IOrderExtService;
import com.hd.agent.storage.model.StorageSummary;
import com.hd.agent.storage.model.StorageSummaryBatch;
import com.hd.agent.storage.service.IStorageForSalesService;
import com.hd.agent.system.model.SysCode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 销售发货通知单service
 *
 * @author zhengziyong
 */
public class DispatchBillServiceImpl extends BaseSalesServiceImpl implements IDispatchBillService {

    //调用自动生成出库单接口service
    private IStorageForSalesService storageForSalesService;
    //调用订单接口
    private IOrderExtService salesOrderExtService;

    public IStorageForSalesService getStorageForSalesService() {
        return storageForSalesService;
    }

    public void setStorageForSalesService(
            IStorageForSalesService storageForSalesService) {
        this.storageForSalesService = storageForSalesService;
    }

    public IOrderExtService getSalesOrderExtService() {
        return salesOrderExtService;
    }

    public void setSalesOrderExtService(IOrderExtService salesOrderExtService) {
        this.salesOrderExtService = salesOrderExtService;
    }

    @Override
    public boolean addDispatchBill(DispatchBill bill) throws Exception {
        return super.addDispatchBill(bill);
    }

    @Override
    public boolean updateDispatchBill(DispatchBill bill) throws Exception {
        DispatchBill oldBill = getDispatchBill(bill.getId());
		if(null==oldBill || "3".equals(oldBill.getStatus()) || "4".equals(oldBill.getStatus())){
            return false;
        }
        //判断是否有销售区域
        if (null == bill.getSalesarea() || "".equals(bill.getSalesarea())) {
            Customer customer = getCustomerByID(bill.getCustomerid());
            if (null != customer) {
                bill.setSalesarea(customer.getSalesarea());
                bill.setPcustomerid(customer.getPid());
            }
        }
        SysUser sysUser = getSalesIndoorSysUserByCustomerid(bill.getCustomerid());
        if (sysUser != null) {
            //销售内勤
            bill.setIndooruserid(sysUser.getPersonnelid());
        }
        getSalesDispatchBillMapper().deleteDispatchBillDetailByBill(bill.getId());
        List<DispatchBillDetail> billDetailList = bill.getBillDetailList();
        if (billDetailList.size() > 0) {
            int seq = 0;
            List<DispatchBillDetail> brandDiscountList = new ArrayList();
            List detailDataList = new ArrayList();
            for (DispatchBillDetail billDetail : billDetailList) {
                if (billDetail != null) {
                    billDetail.setBillid(bill.getId());
                    if (StringUtils.isEmpty(billDetail.getStorageid()) && org.apache.commons.lang3.StringUtils.isNotEmpty(bill.getStorageid())) {
                        billDetail.setStorageid(bill.getStorageid());
                    }
                    if(StringUtils.isNotEmpty(billDetail.getSummarybatchid())){
                        StorageSummaryBatch storageSummaryBatch = storageForSalesService.getStorageSummaryBatchByID(billDetail.getSummarybatchid());
                        if(null!=storageSummaryBatch){
                            billDetail.setStorageid(storageSummaryBatch.getStorageid());
                        }
                    }
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(billDetail.getGoodsid());
                    if (null != goodsInfo) {
                        //计算辅单位数量
                        Map map = countGoodsInfoNumber(billDetail.getGoodsid(), billDetail.getAuxunitid(), billDetail.getUnitnum());
                        if (map.containsKey("auxnum")) {
                            billDetail.setTotalbox((BigDecimal) map.get("auxnum"));
                        }
                        billDetail.setGoodssort(goodsInfo.getDefaultsort());
                        billDetail.setCostprice(getGoodsCostprice(bill.getStorageid(),goodsInfo));
                        //是否有品牌 和品牌业务员
                        if (null == billDetail.getBrandid() || "".equals(billDetail.getBrandid())) {
                            billDetail.setBrandid(goodsInfo.getBrand());
                            billDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), bill.getCustomerid()));
                            //厂家业务员
                            billDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), bill.getCustomerid()));
                            //获取品牌部门
                            Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                            if (null != brand) {
                                billDetail.setBranddept(brand.getDeptid());
                            }
                            //获取商品供应商
                            billDetail.setSupplierid(goodsInfo.getDefaultsupplier());
                        }
                    }
                    if (null == billDetail.getSeq() || billDetail.getSeq() == 0) {
                        billDetail.setSeq(seq + 1);
                        seq = billDetail.getSeq();
                    } else {
                        if (billDetail.getSeq() > 0) {
                            seq = billDetail.getSeq();
                        }
                    }
                    //品牌折扣不直接插入 折算到各商品上后再插入
                    if (!"2".equals(billDetail.getIsdiscount())) {
                        //数量为0的明细不添加
                        if (null != billDetail.getUnitnum() && billDetail.getUnitnum().compareTo(BigDecimal.ZERO) != 0) {
                            detailDataList.add(billDetail);
                        } else if ("1".equals(billDetail.getIsdiscount())) {
                            detailDataList.add(billDetail);
                        }
                        //getSalesDispatchBillMapper().addDispatchBillDetail(billDetail);
                    } else {
                        brandDiscountList.add(billDetail);
                    }
                }
            }
            if (null != detailDataList && detailDataList.size() > 0) {
                getSalesDispatchBillMapper().addDispatchBillDetailList(detailDataList);
            }
            //品牌折扣 平摊到各商品中
            for (DispatchBillDetail billDetail : brandDiscountList) {
                List<DispatchBillDetail> brandGoodsList = getSalesDispatchBillMapper().getDispatchBillDetailListByBillAndBrandid(bill.getId(), billDetail.getBrandid());
                BigDecimal totalamount = BigDecimal.ZERO;
                BigDecimal totalunitnum = BigDecimal.ZERO;
                BigDecimal totalboxSum = BigDecimal.ZERO;
                for (DispatchBillDetail billGoodsDetail : brandGoodsList) {
                    totalamount = totalamount.add(billGoodsDetail.getTaxamount());
                    totalunitnum = totalunitnum.add(billGoodsDetail.getUnitnum());
                    totalboxSum = totalboxSum.add(billGoodsDetail.getTotalbox());
                }
                if (null != billDetail.getTaxamount() && billDetail.getTaxamount().compareTo(BigDecimal.ZERO) != 0) {
                    BigDecimal useamount = BigDecimal.ZERO;
                    for (int i = 0; i < brandGoodsList.size(); i++) {
                        DispatchBillDetail billGoodsDetail = brandGoodsList.get(i);
                        DispatchBillDetail detail = new DispatchBillDetail();
                        detail.setStorageid(billDetail.getStorageid());
                        detail.setGoodsid(billGoodsDetail.getGoodsid());
                        detail.setBrandid(billGoodsDetail.getBrandid());
                        GoodsInfo goodsInfo = getAllGoodsInfoByID(billGoodsDetail.getGoodsid());
                        if (null != goodsInfo) {
                            detail.setGoodssort(goodsInfo.getDefaultsort());
                        }
                        detail.setBranddept(billGoodsDetail.getBranddept());
                        detail.setBranduser(billGoodsDetail.getBranduser());
                        Brand brand = getGoodsBrandByID(billGoodsDetail.getBrandid());
                        if (null != brand) {
                            //厂家业务员
                            detail.setSupplieruser(getSupplieruserByCustomeridAndBrand(billGoodsDetail.getBrandid(), bill.getCustomerid()));
                            detail.setSupplierid(brand.getSupplierid());
                            detail.setTaxtype(brand.getDefaulttaxtype());
                        }
                        detail.setBillid(billGoodsDetail.getBillid());
                        detail.setIsdiscount("1");
                        detail.setIsbranddiscount("1");
                        seq++;
                        detail.setSeq(seq);
                        detail.setRemark(billDetail.getRemark());
                        detail.setRepartitiontype(billDetail.getRepartitiontype());
                        //计算平摊到商品中的 各折扣金额
                        BigDecimal discountamount = BigDecimal.ZERO;
                        if("0".equals(billDetail.getRepartitiontype())){//金额
                            discountamount = billGoodsDetail.getTaxamount().divide(totalamount, 6, BigDecimal.ROUND_HALF_UP).multiply(billDetail.getTaxamount());
                        }else if("1".equals(billDetail.getRepartitiontype())){//数量
                            discountamount = billGoodsDetail.getUnitnum().divide(totalunitnum, 6, BigDecimal.ROUND_HALF_UP).multiply(billDetail.getTaxamount());
                        }else if("2".equals(billDetail.getRepartitiontype())){//箱数
                            discountamount = billGoodsDetail.getTotalbox().divide(totalboxSum, 6, BigDecimal.ROUND_HALF_UP).multiply(billDetail.getTaxamount());
                        }
                        if (i == brandGoodsList.size() - 1) {
                            discountamount = billDetail.getTaxamount().subtract(useamount);
                        }
                        //已分配金额
                        useamount = useamount.add(discountamount);

                        BigDecimal notaxdiscountamount = BigDecimal.ZERO;
                        if(StringUtils.isNotEmpty(detail.getTaxtype())){
                            notaxdiscountamount = getNotaxAmountByTaxAmount(discountamount, detail.getTaxtype());
                        }else{
                            notaxdiscountamount = getNotaxAmountByTaxAmount(discountamount, null);
                        }
                        detail.setTaxamount(discountamount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
                        detail.setNotaxamount(notaxdiscountamount);
                        detail.setTax(detail.getTaxamount().subtract(detail.getNotaxamount()));

                        detailDataList.add(billDetail);
                        getSalesDispatchBillMapper().addDispatchBillDetail(detail);
                    }
                }
            }
            //getSalesDispatchBillMapper().addDispatchBillDetailList(detailDataList);
        }
        return getSalesDispatchBillMapper().updateDispatchBill(bill) > 0;
    }

    @Override
    public DispatchBill getDispatchBill(String id) throws Exception {
        List<DispatchBillDetail> billDetailList = getSalesDispatchBillMapper().getDispatchBillDetailSumDiscountListByBill(id);
        DispatchBill dispatchBill = getSalesDispatchBillMapper().getDispatchBill(id);
        if (null != dispatchBill) {
            Customer customer = getCustomerByID(dispatchBill.getCustomerid());
            if (customer != null) {
                dispatchBill.setCustomername(customer.getName());
            }
            DepartMent departMent = getDepartMentById(dispatchBill.getSalesdept());
            if (null != departMent) {
                dispatchBill.setSalesdeptname(departMent.getName());
            }
            Personnel personnel = getPersonnelById(dispatchBill.getSalesuser());
            if (null != personnel) {
                dispatchBill.setSalesusername(personnel.getName());
            }
        }


        //商品库存记录信息 防止同一张订单出现多条相同明细时 商品可用量出现不正确
        Map storageGoodsMap = new HashMap();
        for (DispatchBillDetail billDetail : billDetailList) {
            TaxType taxType = getTaxType(billDetail.getTaxtype());
            if (taxType != null) {
                billDetail.setTaxtypename(taxType.getName());
            }
            StorageInfo storageInfo = getStorageInfoByID(billDetail.getStorageid());
            if (storageInfo != null) {
                billDetail.setStoragename(storageInfo.getName());
            }
            //当销售发货通知单处于保存状态时 判断仓库中数量是否足够
            if ("2".equals(dispatchBill.getStatus())) {
//				boolean flag = storageForSalesService.isGoodsEnoughByDispatchBillDetail(billDetail);
                String storageid = "";
                if (StringUtils.isNotEmpty(dispatchBill.getStorageid())) {
                    storageid = dispatchBill.getStorageid();
                } else if (StringUtils.isNotEmpty(billDetail.getStorageid())) {
                    storageid = billDetail.getStorageid();
                }
                String keyid = billDetail.getGoodsid() + "_" + storageid+"_"+billDetail.getSummarybatchid();
                StorageSummary storageSummary = null;
                if (storageGoodsMap.containsKey(keyid)) {
                    storageSummary = (StorageSummary) storageGoodsMap.get(keyid);
                } else {
                	if(StringUtils.isNotEmpty(billDetail.getSummarybatchid())){
                		StorageSummaryBatch storageSummaryBatch = storageForSalesService.getStorageSummaryBatchByID(billDetail.getSummarybatchid());
                		if(null!=storageSummaryBatch){
                			storageSummary = new StorageSummary();
                			storageSummary.setExistingnum(storageSummaryBatch.getExistingnum());
                			storageSummary.setUsablenum(storageSummaryBatch.getUsablenum());
                			storageSummary.setOutuseablenum(storageSummary.getUsablenum());
                		}
                	}else if (StringUtils.isNotEmpty(storageid)) {
                        storageSummary = storageForSalesService.getStorageSummarySumByGoodsidAndStorageid(billDetail.getGoodsid(), storageid);
                    } else {
                        storageSummary = storageForSalesService.getStorageSummarySumByGoodsid(billDetail.getGoodsid());
                    }
                }
                if (null != storageSummary && storageSummary.getOutuseablenum().compareTo(billDetail.getUnitnum()) != -1) {
                    billDetail.setIsenough("1");
                    billDetail.setPreusablenum(storageSummary.getOutuseablenum());
                } else {
                    billDetail.setPreusablenum(BigDecimal.ZERO);
                    billDetail.setIsenough("0");
                }
                if (null != storageSummary) {
                    billDetail.setUsablenum(storageSummary.getUsablenum());
                    if (storageSummary.getUsablenum().compareTo(billDetail.getUnitnum()) >= 0) {
                        //为下一条相同商品 计算可用量和预计可用量
                        storageSummary.setUsablenum(storageSummary.getUsablenum().subtract(billDetail.getUnitnum()));
                        storageSummary.setOutuseablenum(storageSummary.getOutuseablenum().subtract(billDetail.getUnitnum()));
                    } else {
                        storageSummary.setUsablenum(BigDecimal.ZERO);
                    }
                    storageGoodsMap.put(keyid, storageSummary);
                } else {
                    billDetail.setUsablenum(BigDecimal.ZERO);
                }
            }
            GoodsInfo orgGoodsInfo = getGoodsInfoByID(billDetail.getGoodsid());
            if (null != orgGoodsInfo) {
                //折扣显示处理
                GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(orgGoodsInfo);
                if ("1".equals(billDetail.getIsdiscount())) {
                    goodsInfo.setBarcode(null);
                    goodsInfo.setBoxnum(null);
                    billDetail.setUnitnum(null);
                    billDetail.setUsablenum(null);
                    billDetail.setAuxnumdetail(null);
                    billDetail.setTaxprice(null);
                    if ("1".equals(billDetail.getIsbranddiscount())) {
                        billDetail.setGoodsid("");
                        goodsInfo.setName(goodsInfo.getBrandName());
                        billDetail.setIsdiscount("2");
                    }
                } else {
                    BigDecimal boxprice = orgGoodsInfo.getBoxnum().multiply(billDetail.getTaxprice() == null ? BigDecimal.ZERO : billDetail.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);
                    billDetail.setBoxprice(boxprice);
                }
                billDetail.setGoodsInfo(goodsInfo);
            }
        }
        dispatchBill.setBillDetailList(billDetailList);
        return dispatchBill;
    }

    @Override
    public DispatchBill getDispatchBillByOrder(String id) throws Exception {
        return super.getDispatchBillByOrder(id);
    }

    @Override
    public PageData getDispatchBillData(PageMap pageMap) throws Exception {
        String sql = getDataAccessRule("t_sales_dispatchbill", null); //数据权限
        pageMap.setDataSql(sql);
        boolean showcanprint = false;
        if (null != pageMap.getCondition() && null != pageMap.getCondition().get("showcanprint")
                && StringUtils.isNotEmpty(pageMap.getCondition().get("showcanprint").toString())
                && "1".equals(pageMap.getCondition().get("showcanprint"))) {
            showcanprint = true;
        }

        List<DispatchBill> billList = getSalesDispatchBillMapper().getDispatchBillList(pageMap);
        for (DispatchBill bill : billList) {
            Personnel indoorPerson = getPersonnelById(bill.getIndooruserid());
            if (null != indoorPerson) {
                bill.setIndoorusername(indoorPerson.getName());
            }
            DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(bill.getSalesdept());
            if (departMent != null) {
                bill.setSalesdept(departMent.getName());
            }
            Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(bill.getSalesuser());
            if (personnel != null) {
                bill.setSalesuser(personnel.getName());
            }
            Map map = new HashMap();
            map.put("id", bill.getCustomerid());
            Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(map);
            if (customer != null) {
                bill.setCustomerid(customer.getId());
                bill.setCustomername(customer.getName());
            }
            map.put("id", bill.getHandlerid());
            Contacter contacter = getBaseFilesContacterMapper().getContacterDetail(map);
            if (contacter != null) {
                bill.setHandlerid(contacter.getName());
            }
            Map total = getSalesDispatchBillMapper().getDispatchBillDetailTotal(bill.getId());
            if (total != null) {
                if (total.containsKey("taxamount")) {
                    bill.setField01(total.get("taxamount").toString());
                }
                if (total.containsKey("notaxamount")) {
                    bill.setField02(total.get("notaxamount").toString());
                }
                if (total.containsKey("tax")) {
                    bill.setField03(total.get("tax").toString());
                }
            }

            if (showcanprint) {
                String printlimit = "0";
                if (null != pageMap.getCondition().get("printlimit")) {
                    printlimit = (String) pageMap.getCondition().get("printlimit");
                }
                if (null != printlimit && "1".equals(printlimit.trim())) {
                    printlimit = "1";
                } else {
                    printlimit = "0";
                }
                bill.setPrintlimit(printlimit);
                /*
				Map queryMap=new HashMap();
				int canprint=0;
				
				if("3".equals(bill.getStatus())){
					//配货单可否打印
					queryMap.put("phprint", "1");
					queryMap.put("printlimit", "1"); //查找 未完成打印
					queryMap.put("dispatchid", bill.getId());
					canprint=getSalesDispatchBillMapper().getCanPrinttimes(queryMap);
					bill.setCanprint(canprint);
				} else if("4".equals(bill.getStatus())){
					//发货单可否打印
					queryMap.put("fhprint", "1");
					queryMap.put("printlimit", "1"); //查找 未完成打印
					queryMap.put("dispatchid", bill.getId());
					canprint=getSalesDispatchBillMapper().getCanPrinttimes(queryMap);		
					bill.setCanprint(canprint);
				}
				 */
            }
            SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(bill.getStatus(), "status");
            if (sysCode != null) {
                bill.setStatusname(sysCode.getCodename());
            }
        }
        return new PageData(getSalesDispatchBillMapper().getDispatchBillCount(pageMap), billList, pageMap);
    }

    @Override
    public boolean deleteDispatchBill(String id) throws Exception {
        return super.deleteDispatchBill(id);
    }

    @Override
    public Map auditDispatchBill(String type, String id) throws Exception {
        Map result = new HashMap();
        SysUser sysUser = getSysUser();
        DispatchBill dispatchBill = getSalesDispatchBillMapper().getDispatchBill(id);
        List detailList = getSalesDispatchBillMapper().getDispatchBillDetailListByBill(id);
        if ("1".equals(type)) { //审核(判断状态、自动生成出库单、回写参照订单、关闭参照订单、更新单据状态)
            if (!"2".equals(dispatchBill.getStatus())) { //只有状态为2（保存状态）才可进行审核
                result.put("flag", false);
                return result;
            }
            //系统参数 控制销售订单 仓库是否必填
    		String isOrderStorageSelect = getSysParamValue("IsOrderStorageSelect");
    		if("1".equals(isOrderStorageSelect) && StringUtils.isEmpty(dispatchBill.getStorageid())){
    			result.put("flag", false);
                result.put("msg", "未选择发货仓库");
                return result;
    		}
            if (null != detailList && detailList.size() > 0) {
                Map juMap = new HashMap();
                List detailSumList = getSalesDispatchBillMapper().getDispatchBillDetailSumListByIdWithOutDiscount(id);
                if (StringUtils.isEmpty(dispatchBill.getStorageid())) {
                    juMap = storageForSalesService.isGoodsEnoughByDispatchBillDetail(detailSumList);
                } else {
                    juMap = storageForSalesService.isGoodsEnoughByDispatchBillDetailInStorage(dispatchBill.getStorageid(), detailSumList);
                }
                if (!juMap.containsKey("flag") || !(Boolean) juMap.get("flag")) {
                    result.put("flag", false);
                    result.put("msg", juMap.get("msg"));
                    return result;
                }
                Map map = storageForSalesService.addSaleOutByDispatchbill(dispatchBill, detailList); //自动生成出库单
                if (map.containsKey("flag") && map.get("flag").toString() == "true") {
                    getSalesDispatchBillMapper().updateDispatchBillRefer("1", id); //更新发货通知单的参照状态
                }
                if ("1".equals(dispatchBill.getSource())) { //参照订单则需要回写
                    String orderId = dispatchBill.getBillno(); //取参照的订单的编号
                    salesOrderExtService.updateOrderDetailBack(detailList,orderId); //回写上游单据数据
                    salesOrderExtService.updateOrderClose(orderId); //关闭上游单据
                }
                DispatchBill bill = new DispatchBill();
                bill.setAudituserid(sysUser.getUserid());
                bill.setAuditusername(sysUser.getName());
                bill.setAudittime(new Date());
                bill.setStatus("3");
                bill.setId(id);
                //审核之后 设置当天日期为业务日期
                bill.setBusinessdate(CommonUtils.getTodayDataStr());
                boolean flag = getSalesDispatchBillMapper().updateDispatchBillStatus(bill) > 0;
                result.put("flag", flag);
                result.put("billId", map.get("saleoutid"));
                result.put("msg", map.get("msg"));
            } else {
            	if ("1".equals(dispatchBill.getSource())) { //参照订单则需要回写
                    String orderId = dispatchBill.getBillno(); //取参照的订单的编号
                    salesOrderExtService.updateOrderDetailBack(detailList,orderId); //回写上游单据数据
                    salesOrderExtService.updateOrderClose(orderId); //关闭上游单据
                }
                DispatchBill bill = new DispatchBill();
                bill.setAudituserid(sysUser.getUserid());
                bill.setAuditusername(sysUser.getName());
                bill.setAudittime(new Date());
                bill.setStatus("4");
                bill.setId(id);
                //审核之后 设置当天日期为业务日期
                bill.setBusinessdate(CommonUtils.getTodayDataStr());
                boolean flag = getSalesDispatchBillMapper().updateDispatchBillStatus(bill) > 0;
                result.put("flag", true);
                result.put("billId", "");
                result.put("closeFlag", true);
                result.put("msg", "销售发货通知单没有明细数据或者发货数量为0，直接关闭。");
            }
        } else if ("2".equals(type)) { //反审
            //只有状态为3（审核状态）才可进行反审，判断发货单是否已生成大单，若生成不允许反审
            if (!"3".equals(dispatchBill.getStatus()) || storageForSalesService.doCheckSaleoutIsbigsaleout(dispatchBill.getId())) {
                result.put("flag", false);
                result.put("msg", "单据状态不为审核状态或发货单已生成大单发货，不允许反审");
                return result;
            }
//			if(dispatchBill.getPhprinttimes()>0){ //未打印才可反审
//				result.put("flag", false);
//				return result;
//			}
            boolean bl = storageForSalesService.deleteSaleOutBySourceid(id);
            if (bl) {
                String orderId = dispatchBill.getBillno(); //取参照的订单的编号
                salesOrderExtService.updateClearOrderDetailBack(detailList);
                salesOrderExtService.updateOrderOpen(orderId);
                DispatchBill bill = new DispatchBill();
                bill.setStatus("2");
                bill.setId(id);
                bill.setPhprinttimes(0);
                bill.setPrinttimes(0);
                boolean flag = getSalesDispatchBillMapper().updateDispatchBillStatus(bill) > 0;
                result.put("flag", flag);
            }
            result.put("billArg", bl);
        } else if ("3".equals(type)) {
            Map map = storageForSalesService.addSaleOutByDispatchbill(dispatchBill, dispatchBill.getBillDetailList()); //自动生成出库单
            if (map.containsKey("flag") && map.get("flag").toString() == "true") {
                getSalesDispatchBillMapper().updateDispatchBillRefer("1", id); //更新发货通知单的参照状态
            }
            List<DispatchBillDetail> billDetailList = dispatchBill.getBillDetailList();
            String orderId = dispatchBill.getBillno(); //取参照的订单的编号
            salesOrderExtService.updateOrderDetailBack(billDetailList,orderId); //回写上游单据数据
            salesOrderExtService.updateOrderClose(orderId); //关闭上游单据
        }
        return result;
    }

    @Override
    public Map auditSupperDispatchBill(String id) throws Exception {
        Map result = new HashMap();
        SysUser sysUser = getSysUser();
        DispatchBill dispatchBill = getSalesDispatchBillMapper().getDispatchBill(id);
        List detailList = getSalesDispatchBillMapper().getDispatchBillDetailListByBill(id);
        if (!"2".equals(dispatchBill.getStatus())) { //只有状态为2（保存状态）才可进行审核
            result.put("flag", false);
            return result;
        }
        //系统参数 控制销售订单 仓库是否必填
		String isOrderStorageSelect = getSysParamValue("IsOrderStorageSelect");
		if("1".equals(isOrderStorageSelect) && StringUtils.isEmpty(dispatchBill.getStorageid())){
			result.put("flag", false);
            result.put("msg", "未选择发货仓库");
            return result;
		}
        if (null != detailList && detailList.size() > 0) {
            dispatchBill.setIsSupperAudit(true);
            Map map = storageForSalesService.addSaleOutByDispatchbill(dispatchBill, detailList); //自动生成出库单
            if (map.containsKey("flag") && map.get("flag").toString() == "true") {
                getSalesDispatchBillMapper().updateDispatchBillRefer("1", id); //更新发货通知单的参照状态
            }
            if ("1".equals(dispatchBill.getSource())) { //参照订单则需要回写
                String orderId = dispatchBill.getBillno(); //取参照的订单的编号
                salesOrderExtService.updateOrderDetailBack(detailList,orderId); //回写上游单据数据
                salesOrderExtService.updateOrderClose(orderId); //关闭上游单据
            }
            DispatchBill bill = new DispatchBill();
            bill.setAudituserid(sysUser.getUserid());
            bill.setAuditusername(sysUser.getName());
            bill.setAudittime(new Date());
            bill.setStatus("3");
            bill.setId(id);
            //审核之后 设置当天日期为业务日期
            bill.setBusinessdate(CommonUtils.getTodayDataStr());
            boolean flag = getSalesDispatchBillMapper().updateDispatchBillStatus(bill) > 0;
            result.put("flag", flag);
            result.put("billId", map.get("saleoutid"));
            result.put("msg", map.get("msg"));
        } else {
            DispatchBill bill = new DispatchBill();
            bill.setAudituserid(sysUser.getUserid());
            bill.setAuditusername(sysUser.getName());
            bill.setAudittime(new Date());
            bill.setStatus("4");
            bill.setId(id);
            //审核之后 设置当天日期为业务日期
            bill.setBusinessdate(CommonUtils.getTodayDataStr());
            boolean flag = getSalesDispatchBillMapper().updateDispatchBillStatus(bill) > 0;
            result.put("flag", true);
            result.put("billId", "");
            result.put("closeFlag", true);
            result.put("msg", "销售发货通知单没有明细数据或者发货数量为0，直接关闭。");
        }
        return result;
    }

    @Override
    public List getDetailListByBill(String id) throws Exception {
        List<DispatchBillDetail> detailList = getSalesDispatchBillMapper().getDispatchBillDetailListByBill(id);
        for (DispatchBillDetail detail : detailList) {
            GoodsInfo goodsInfo = getGoodsInfoByID(detail.getGoodsid());
            detail.setGoodsInfo(goodsInfo);
        }
        return detailList;
    }

    @Override
    public Map submitDispatchBillProcess(String title, String userId, String processDefinitionKey, String businessKey, Map<String, Object> variables) throws Exception {
        Map map = new HashMap();
        return map;
    }

    @Override
    public Map dispatchBillDeploy(String id) throws Exception {
        boolean flag = true;
        boolean barcodeFlag = false;
        boolean batchFlag = false;
        String msg = "";
        List<DispatchBillDetail> billDetailList = getSalesDispatchBillMapper().getDispatchBillDetailSumListById(id);
        DispatchBill dispatchBill = getSalesDispatchBillMapper().getDispatchBill(id);
        for (DispatchBillDetail billDetail : billDetailList) {
            //当销售发货通知单处于保存状态时 判断仓库中数量是否足够
            if ("2".equals(dispatchBill.getStatus())) {
                boolean sendflag = storageForSalesService.isGoodsEnoughByDispatchBillDetail(billDetail);
                if (!sendflag) {
                	GoodsInfo goodsInfo = getAllGoodsInfoByID(billDetail.getGoodsid());
                	if(StringUtils.isNotEmpty(billDetail.getSummarybatchid())){
                		batchFlag = true;
                		flag = false;
                		msg += "商品：" + goodsInfo.getId() +",批次号："+billDetail.getBatchno()+ "数量不足，请手动调整<br/>";
                	}else{
                		//是否允许是否用相同条形码商品追加或者替换
	                	String IsDeployStorageGoodsByBarcode = getSysParamValue("IsDeployStorageGoodsByBarcode");
	                	if("1".equals(IsDeployStorageGoodsByBarcode)){
		                    List<StorageSummary> list = null;
		                    if (StringUtils.isNotEmpty(billDetail.getStorageid()) || StringUtils.isNotEmpty(billDetail.getStorageid())) {
                                String storageid = dispatchBill.getStorageid();
                                if(org.apache.commons.lang3.StringUtils.isNotEmpty(billDetail.getStorageid())){
                                    storageid = billDetail.getStorageid();
                                }
		                        list = storageForSalesService.getStorageSummarySumByBarcodeInStorageid(goodsInfo.getBarcode(), goodsInfo.getId(), storageid);
		                    } else {
		                        list = storageForSalesService.getStorageSummarySumByBarcode(goodsInfo.getBarcode(), goodsInfo.getId());
		                    }
		                    if (null != list && list.size() > 0) {
                                String goodsMsg = "";
                                for (StorageSummary storageSummary : list) {
                                    StorageInfo storageInfo = getStorageInfoByID(storageSummary.getStorageid());
                                    GoodsInfo goodsInfo1 = getGoodsInfoByID(storageSummary.getGoodsid());
                                    Map auxMap = countGoodsInfoNumber(storageSummary.getGoodsid(),goodsInfo1.getAuxunitid(),storageSummary.getUsablenum());
                                    String auxnumdetail = (String) auxMap.get("auxnumdetail");
                                    if (StringUtils.isEmpty(goodsMsg)) {
                                        goodsMsg ="<span><input type='checkbox' name='"+goodsInfo.getId()+"' checked='checked' class='deployStorage datagrid-cell-check' value='"+storageSummary.getGoodsid()+"' storageid='"+storageSummary.getStorageid()+"'/>仓库："+storageInfo.getName()+ ",("+storageSummary.getGoodsid()+")"+goodsInfo1.getName()+",可用量："+auxnumdetail+"</span>";
                                    } else {
                                        goodsMsg += "<br/><span><input type='checkbox' name='"+goodsInfo.getId()+"' checked='checked' class='deployStorage datagrid-cell-check' value='"+storageSummary.getGoodsid()+"' storageid='"+storageSummary.getStorageid()+"'/>仓库："+storageInfo.getName()+",("+storageSummary.getGoodsid()+")"+goodsInfo1.getName()+",可用量："+auxnumdetail+"</span>";
                                    }

                                }
                                Map auxMap = countGoodsInfoNumber(billDetail.getGoodsid(),goodsInfo.getAuxunitid(),billDetail.getUnitnum());
                                String auxnumdetail = (String) auxMap.get("auxnumdetail");

                                StorageSummary storageSummary = getBaseStorageSummaryService().getStorageSummarySumByGoodsid(billDetail.getGoodsid());
                                String storageNum = "";
                                if(null!=storageSummary){
                                    Map storageAuxMap = countGoodsInfoNumber(storageSummary.getGoodsid(),goodsInfo.getAuxunitid(),storageSummary.getUsablenum());
                                    if(null!=storageAuxMap && storageAuxMap.containsKey("auxnumdetail")){
                                        storageNum = (String) storageAuxMap.get("auxnumdetail");
                                    }else{
                                        storageNum = "空";
                                    }
                                }else{
                                    storageNum = "空";
                                }
                                msg += "商品：(" + goodsInfo.getId() + ")"+goodsInfo.getName()+"，需要数量："+auxnumdetail+"，仓库库存："+storageNum+",库存不足。<br/>仓库中存在同条形码的商品：<div style=\"color:blue;\">" + goodsMsg +"</div><br/>";
                                barcodeFlag = true;
		                    }
	                	}
	                }
                    flag = false;
                }
            }
        }
        Map map = new HashMap();
        map.put("flag", flag);
        map.put("barcodeFlag", barcodeFlag);
        map.put("batchFlag", batchFlag);
        map.put("msg", msg);
        return map;
    }

    @Override
    public DispatchBill getDispatchBillDeploy(String id, String barcodeflag,String deploy) throws Exception {
        List<DispatchBillDetail> billDetailList = getSalesDispatchBillMapper().getDispatchBillDetailSumDiscountListByBill(id);
        DispatchBill dispatchBill = getSalesDispatchBillMapper().getDispatchBill(id);
        Customer customer = getCustomerByID(dispatchBill.getCustomerid());
        if (customer != null) {
            dispatchBill.setCustomername(customer.getName());
        }
        //商品追加或者替换 其他商品的列表
        //key需要追加或者替换的商品编号  val=指定替换的商品与仓库
        Map deployMap = new HashMap();
        if(StringUtils.isNotEmpty(deploy)){
            JSONArray jsonArray = JSONArray.fromObject(deploy);
            if(null!=jsonArray && jsonArray.size()>0){
                for(int i=0;i<jsonArray.size();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String goodsid = jsonObject.getString("goodsid");
                    String rgoodsid = jsonObject.getString("rgoodsid");
                    String storageid = jsonObject.getString("storageid");

                    Map rMap = new HashMap();
                    rMap.put("goodsid",rgoodsid);
                    rMap.put("storageid",storageid);
                    if(deployMap.containsKey(goodsid)){
                        List<Map> rMapList = (List<Map>) deployMap.get(goodsid);
                        rMapList.add(rMap);
                        deployMap.put(goodsid,rMapList);
                    }else{
                        List<Map> rMapList = new ArrayList<Map>();
                        rMapList.add(rMap);
                        deployMap.put(goodsid,rMapList);
                    }
                }
            }
        }

        //条形码相同商品 补不足商品数量
        List addList = new ArrayList();
        //商品库存记录 防止同一张订单中 多条商品的配货出现问题
        Map storageGoodsMap = new HashMap();
        //商品库存追加记录  防止同一张订单中  多条商品 追加时出现数量问题
        Map storageAddGoodsMap = new HashMap();
        for (DispatchBillDetail billDetail : billDetailList) {
            String oldAuxnumDetail = billDetail.getAuxnumdetail();
            GoodsInfo goodsInfo = getGoodsInfoByID(billDetail.getGoodsid());
            TaxType taxType = getTaxType(billDetail.getTaxtype());
            if (taxType != null) {
                billDetail.setTaxtypename(taxType.getName());
            }
            if (null != goodsInfo) {
                //折扣显示处理
                GoodsInfo orgGoodsInfo = (GoodsInfo) CommonUtils.deepCopy(goodsInfo);
                if ("1".equals(billDetail.getIsdiscount())) {
                    orgGoodsInfo.setBarcode(null);
                    orgGoodsInfo.setBoxnum(null);
                    billDetail.setUnitnum(null);
                    billDetail.setUsablenum(null);
                    billDetail.setAuxnumdetail(null);
                    billDetail.setTaxprice(null);
                    if ("1".equals(billDetail.getIsbranddiscount())) {
                        billDetail.setGoodsid("");
                        orgGoodsInfo.setName(orgGoodsInfo.getBrandName());
                        billDetail.setIsdiscount("2");
                    }
                } else {
                    billDetail.setBoxprice(goodsInfo.getBoxnum().multiply(billDetail.getTaxprice()).setScale(2, BigDecimal.ROUND_HALF_UP));
                }
                billDetail.setGoodsInfo(orgGoodsInfo);
            }
            if (!"1".equals(billDetail.getIsdiscount()) && !"2".equals(billDetail.getIsdiscount())) {
                String storageid = billDetail.getStorageid();
                String keyid = billDetail.getGoodsid() + "_" + storageid;
                boolean flag = true;
                StorageSummary storageSummary = null;
                BigDecimal initnum = BigDecimal.ZERO;
                //判断商品指定了库存批次 指定库存批次后 配置库存不处理
                if(StringUtils.isNotEmpty(billDetail.getSummarybatchid())){
                	StorageSummaryBatch storageSummaryBatch = storageForSalesService.getStorageSummaryBatchByID(billDetail.getSummarybatchid());
                	//设置商品可用量
	                if (null != storageSummaryBatch) {
	                    billDetail.setUsablenum(storageSummaryBatch.getUsablenum());
	                } else {
	                    billDetail.setUsablenum(BigDecimal.ZERO);
	                }
	                //销售发货通知单 商品发货初始数量
	                initnum = billDetail.getUnitnum();
                }else{
	                //获取该商品库存信息  如果指定仓库，只获取该仓库商品信息 否则获取全部发货仓库存信息
	                if (StringUtils.isNotEmpty(storageid)) {
	                    StorageInfo storageInfo = getStorageInfoByID(billDetail.getStorageid());
	                    if (null != storageInfo) {
	                        billDetail.setStoragename(storageInfo.getName());
	                    }
	                    if (storageGoodsMap.containsKey(keyid)) {
	                        storageSummary = (StorageSummary) storageGoodsMap.get(keyid);
	                    } else {
	                        storageSummary = storageForSalesService.getStorageSummarySumByGoodsidAndStorageid(billDetail.getGoodsid(), billDetail.getStorageid());
	                    }
	                } else {
	                    if (storageGoodsMap.containsKey(keyid)) {
	                        storageSummary = (StorageSummary) storageGoodsMap.get(keyid);
	                    } else {
	                        storageSummary = storageForSalesService.getStorageSummarySumByGoodsid(billDetail.getGoodsid());
	                    }
	                }
	                //设置商品可用量
	                if (null != storageSummary) {
	                    billDetail.setUsablenum(storageSummary.getUsablenum());
	                } else {
	                    billDetail.setUsablenum(BigDecimal.ZERO);
	                }
	                //销售发货通知单 商品发货初始数量
	                initnum = billDetail.getUnitnum();
	                //判断商品可用量是否充足
	                if (null == storageSummary || storageSummary.getUsablenum().compareTo(billDetail.getUnitnum()) == -1) {
	                    flag = false;
	                }
                }
                if (!flag) {
                	//是否允许是否用相同条形码商品追加或者替换
                	String IsDeployStorageGoodsByBarcode = getSysParamValue("IsDeployStorageGoodsByBarcode");
                	if(!"1".equals(IsDeployStorageGoodsByBarcode)){
                		barcodeflag = "0";
                	}
                    String goodsBarcodeFlag = barcodeflag;
                    List<StorageSummary> list = null;
                    if (StringUtils.isNotEmpty(storageid)) {
                        list = storageForSalesService.getStorageSummarySumByBarcodeInStorageid(goodsInfo.getBarcode(), goodsInfo.getId(), storageid);
                    } else {
                        //相同条形码不同商品的 商品仓库库存信息列表
                        list = storageForSalesService.getStorageSummarySumByBarcode(goodsInfo.getBarcode(), goodsInfo.getId());
                    }
                    //当同条形码的商品不存在时  追加或者替换不生效 直接配置库存
                    if(null==list || list.size()==0){
                        goodsBarcodeFlag = "0";
                    }
                    //是否用相同条形码商品替换	1追加2替换
                    if ("1".equals(goodsBarcodeFlag)) {
                        if (null != storageSummary && storageSummary.getUsablenum().compareTo(BigDecimal.ZERO) == 1) {
                            billDetail.setUnitnum(storageSummary.getUsablenum());
                            Map auxmap = countGoodsInfoNumber(billDetail.getGoodsid(), billDetail.getAuxunitid(), storageSummary.getUsablenum());
                            String auxInteger = (String) auxmap.get("auxInteger");
                            String auxremainder = (String) auxmap.get("auxremainder");
                            String auxnumdetail = (String) auxmap.get("auxnumdetail");
                            billDetail.setAuxnum(new BigDecimal(auxInteger));
                            billDetail.setOvernum(new BigDecimal(auxremainder));
                            billDetail.setAuxnumdetail(auxnumdetail);
                            BigDecimal taxamount=storageSummary.getUsablenum().multiply(billDetail.getTaxprice());
                            billDetail.setTaxamount(taxamount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                            BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, billDetail.getTaxtype());
                            billDetail.setNotaxamount(notaxamount);
                            billDetail.setTax(billDetail.getTaxamount().subtract(billDetail.getNotaxamount()));
                        } else {
                            billDetail.setUnitnum(BigDecimal.ZERO);
                            billDetail.setAuxnum(BigDecimal.ZERO);
                            billDetail.setOvernum(BigDecimal.ZERO);
                            billDetail.setTaxamount(BigDecimal.ZERO);
                            billDetail.setNotaxamount(BigDecimal.ZERO);
                            billDetail.setAuxnumdetail("");
                        }
                    } else if ("2".equals(goodsBarcodeFlag)) {        //替换
                        billDetail.setUnitnum(BigDecimal.ZERO);
                        billDetail.setAuxnum(BigDecimal.ZERO);
                        billDetail.setOvernum(BigDecimal.ZERO);
                        billDetail.setTaxamount(BigDecimal.ZERO);
                        billDetail.setNotaxamount(BigDecimal.ZERO);
                        billDetail.setAuxnumdetail("");
                    } else {
                        if (null != storageSummary && storageSummary.getUsablenum().compareTo(BigDecimal.ZERO) == 1) {
                            billDetail.setUnitnum(storageSummary.getUsablenum());
                            Map auxmap = countGoodsInfoNumber(billDetail.getGoodsid(), billDetail.getAuxunitid(), storageSummary.getUsablenum());
                            String auxInteger = (String) auxmap.get("auxInteger");
                            String auxremainder = (String) auxmap.get("auxremainder");
                            String auxnumdetail = (String) auxmap.get("auxnumdetail");
                            billDetail.setAuxnum(new BigDecimal(auxInteger));
                            billDetail.setOvernum(new BigDecimal(auxremainder));
                            billDetail.setAuxnumdetail(auxnumdetail);
                            BigDecimal taxamount=storageSummary.getUsablenum().multiply(billDetail.getTaxprice());
                            billDetail.setTaxamount(taxamount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                            BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, billDetail.getTaxtype());
                            billDetail.setNotaxamount(notaxamount);
                            billDetail.setTax(billDetail.getTaxamount().subtract(billDetail.getNotaxamount()));
                        } else {
                            billDetail.setUnitnum(BigDecimal.ZERO);
                            billDetail.setAuxnum(BigDecimal.ZERO);
                            billDetail.setOvernum(BigDecimal.ZERO);
                            billDetail.setTaxamount(BigDecimal.ZERO);
                            billDetail.setNotaxamount(BigDecimal.ZERO);
                            billDetail.setAuxnumdetail("");
                        }
                    }
                    //商品不足数量 已其他相同条形码的商品代替
                    BigDecimal noEnoughNum = initnum.subtract(billDetail.getUnitnum());

                    if (null != list && list.size() > 0) {
                        //用相同条形码的商品代替数量不足
                        for (StorageSummary addStorageSummary : list) {
                            boolean rFlag = false;
                            //判断是否需要追加或者替换指定商品
                            if(deployMap.size()>0 && deployMap.containsKey(billDetail.getGoodsid())){
                                List<Map> rMapList = (List<Map>) deployMap.get(billDetail.getGoodsid());
                                if(null!=rMapList && rMapList.size()>0){
                                    for(Map rMap : rMapList){
                                        String rgoodsid = (String) rMap.get("goodsid");
                                        String rstorageid = (String) rMap.get("storageid");
                                        if(addStorageSummary.getGoodsid().equals(rgoodsid) && rstorageid.equals(addStorageSummary.getStorageid())){
                                            rFlag = true;
                                        }
                                    }
                                }
                            }
                            if(!rFlag){
                                continue;
                            }
                            if (addStorageSummary.getUsablenum().compareTo(BigDecimal.ZERO) == -1) {
                                continue;
                            }

                            BigDecimal thisnum = null;
                            StorageSummary storageSummary2 = null;
                            String addkeyid = addStorageSummary.getGoodsid() + "_" + addStorageSummary.getStorageid();
                            if (storageAddGoodsMap.containsKey(addkeyid)) {
                                storageSummary2 = (StorageSummary) storageAddGoodsMap.get(addkeyid);
                            } else {
                                storageSummary2 = addStorageSummary;
                            }
                            //可用量大于不足数量
                            if (storageSummary2.getUsablenum().compareTo(noEnoughNum) != -1) {
                                thisnum = noEnoughNum;
                                noEnoughNum = BigDecimal.ZERO;
                            } else {
                                thisnum = storageSummary2.getUsablenum();
                                noEnoughNum = noEnoughNum.subtract(thisnum);
                            }
                            DispatchBillDetail detail = new DispatchBillDetail();
                            if (StringUtils.isNotEmpty(storageSummary2.getStorageid())) {
                                StorageInfo storageInfo1 = getStorageInfoByID(storageSummary2.getStorageid());
                                if (null != storageInfo1) {
                                    detail.setStorageid(storageSummary2.getStorageid());
                                    detail.setStoragename(storageInfo1.getName());
                                }
                            }
                            detail.setBillno(billDetail.getBillno());
                            detail.setBillid(billDetail.getBillid());
                            detail.setBilldetailno(billDetail.getBilldetailno());
                            detail.setGoodsid(storageSummary2.getGoodsid());
                            detail.setGroupid(billDetail.getGroupid());
                            detail.setTaxtype(billDetail.getTaxtype());
                            detail.setTaxtypename(billDetail.getTaxtypename());
                            detail.setUnitid(billDetail.getUnitid());
                            detail.setUnitname(billDetail.getUnitname());
                            detail.setAuxunitid(storageSummary2.getAuxunitid());
                            detail.setAuxunitname(storageSummary2.getAuxunitname());
                            detail.setDeliverydate(billDetail.getDeliverydate());
                            detail.setDeliverytype(billDetail.getDeliverytype());
                            GoodsInfo goodsInfo2 = getGoodsInfoByID(storageSummary2.getGoodsid());
                            if (null != goodsInfo2) {
                                detail.setGoodsInfo(goodsInfo2);
                            }
                            detail.setUsablenum(storageSummary2.getUsablenum());
                            detail.setUnitnum(thisnum);
                            Map auxmap = countGoodsInfoNumber(detail.getGoodsid(), storageSummary2.getAuxunitid(), thisnum);
                            String auxInteger = (String) auxmap.get("auxInteger");
                            String auxremainder = (String) auxmap.get("auxremainder");
                            String auxnumdetail = (String) auxmap.get("auxnumdetail");
                            detail.setAuxnum(new BigDecimal(auxInteger));
                            detail.setOvernum(new BigDecimal(auxremainder));
                            detail.setAuxnumdetail(auxnumdetail);
                            detail.setTaxprice(billDetail.getTaxprice());
                            detail.setNotaxprice(billDetail.getNotaxprice());
                            BigDecimal taxamount=thisnum.multiply(detail.getTaxprice());
                            detail.setTaxamount(taxamount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                            BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, detail.getTaxtype());
                            detail.setNotaxamount(notaxamount);
                            detail.setTax(detail.getTaxamount().subtract(detail.getNotaxamount()));
                            if ("1".equals(barcodeflag)) {
                                String remark = billDetail.getRemark();
                                if(StringUtils.isNotEmpty(remark)){
                                    detail.setRemark(remark+";商品:" + billDetail.getGoodsid() + ",需:"+oldAuxnumDetail+",以此追加");
                                }else{
                                    detail.setRemark("商品:" + billDetail.getGoodsid() + ",需:"+oldAuxnumDetail+",以此追加");
                                }
                            } else {
                                String remark = billDetail.getRemark();
                                if(StringUtils.isNotEmpty(remark)) {
                                    detail.setRemark(remark+";商品:" + billDetail.getGoodsid() + ",需:" + oldAuxnumDetail + ",以此替换");
                                }else{
                                    detail.setRemark("商品:" + billDetail.getGoodsid() + ",需:" + oldAuxnumDetail + ",以此替换");
                                }
                            }
                            detail.setSeq(billDetail.getSeq());
                            //追加或者替换的 商品箱价
                            detail.setBoxprice(goodsInfo.getBoxnum().multiply(detail.getTaxprice()).setScale(2, BigDecimal.ROUND_HALF_UP));
                            storageSummary2.setUsablenum(storageSummary2.getUsablenum().subtract(thisnum));
                            storageAddGoodsMap.put(addkeyid, storageSummary2);
                            if (detail.getUnitnum().compareTo(BigDecimal.ZERO) > 0) {
                                addList.add(detail);
                            }
                            if (noEnoughNum.compareTo(BigDecimal.ZERO) == 0) {
                                break;
                            }
                        }
                    }
                }
                if (null != storageSummary) {
                    if (storageSummary.getUsablenum().compareTo(billDetail.getUnitnum()) >= 0) {
                        storageSummary.setUsablenum(storageSummary.getUsablenum().subtract(billDetail.getUnitnum()));
                    } else {
                        storageSummary.setUsablenum(BigDecimal.ZERO);
                    }
                    storageGoodsMap.put(keyid, storageSummary);
                }
            }
        }
        //是否用相同条形码商品追加或者替换
        if ("1".equals(barcodeflag) || "2".equals(barcodeflag)) {
            billDetailList.addAll(addList);
        }
        List list = new ArrayList();
        for (DispatchBillDetail dispatchBillDetail : billDetailList) {
            if (null != dispatchBillDetail.getUnitnum() && dispatchBillDetail.getUnitnum().compareTo(BigDecimal.ZERO) != 0) {
                list.add(dispatchBillDetail);
            } else if (!"0".equals(dispatchBillDetail.getIsdiscount())) {
                list.add(dispatchBillDetail);
            }
        }
        //对明细按seq进行排序
        Collections.sort(list,new Comparator<DispatchBillDetail>(){
            @Override
            public int compare(DispatchBillDetail b1, DispatchBillDetail b2) {
                return b1.getSeq().compareTo(b2.getSeq());
            }
        });
        dispatchBill.setBillDetailList(list);
         return dispatchBill;
    }

    @Override
    public boolean updateOrderPhPrinttimes(DispatchBill dispatchBill) throws Exception {
        dispatchBill.setPrinttimes(null);
        return getSalesDispatchBillMapper().updateOrderPrinttimes(dispatchBill) > 0;
    }

    @Override
    public boolean updateOrderPhPrinttimes(List<DispatchBill> list) throws Exception {
        boolean isok = false;
        for (DispatchBill item : list) {
            item.setPrinttimes(null);
            isok = getSalesDispatchBillMapper().updateOrderPrinttimes(item) > 0 || isok;
        }
        return isok;
    }

    @Override
    public boolean updateOrderFhPrinttimes(DispatchBill dispatchBill) throws Exception {
        dispatchBill.setPhprinttimes(null);
        return getSalesDispatchBillMapper().updateOrderPrinttimes(dispatchBill) > 0;
    }

    @Override
    public boolean updateOrderFhPrinttimes(List<DispatchBill> list) throws Exception {
        boolean isok = false;
        for (DispatchBill item : list) {
            item.setPhprinttimes(null);
            isok = getSalesDispatchBillMapper().updateOrderPrinttimes(item) > 0 || isok;
        }
        return isok;
    }

    @Override
    public int getCanPrinttimes(Map map) throws Exception {
        return getSalesDispatchBillMapper().getCanPrinttimes(map);
    }

    @Override
    public List getDispatchBillBy(Map map) throws Exception {		
    	String showPCustomerName=(String)map.get("showPCustomerName");
        List<DispatchBill> list = getSalesDispatchBillMapper().getDispatchBillBy(map);
        Customer pCustomer =null;
        for (DispatchBill item : list) {
            if(StringUtils.isNotEmpty(item.getIndooruserid())) {
                Personnel indoorPerson = getPersonnelById(item.getIndooruserid());
                if (null != indoorPerson) {
                    item.setIndoorusername(indoorPerson.getName());
                }
            }
            if(StringUtils.isNotEmpty(item.getSalesdept())) {
                DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(item.getSalesdept());
                if (departMent != null) {
                    item.setSalesdeptname(departMent.getName());
                }
            }
            if(StringUtils.isNotEmpty(item.getSalesuser())) {
                Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(item.getSalesuser());
                if (personnel != null) {
                    item.setSalesusername(personnel.getName());
                }
            }
            Map queryMap = new HashMap();
            if(StringUtils.isNotEmpty(item.getCustomerid())) {
                queryMap.put("id", item.getCustomerid());
                Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(queryMap);
                if (customer != null) {
                    //原先把客户名称放在item的customerid中，现在改为放在cutomername中
                    item.setCustomername(customer.getName());
                    item.setCustomerInfo(customer);

                    if ("true".equals(showPCustomerName)) {
                        if (StringUtils.isNotEmpty(customer.getPid())) {
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
                    }
                    if(StringUtils.isNotEmpty(customer.getSettletype())) {
                        Settlement settlement = getSettlementByID(customer.getSettletype());
                        if (null != settlement) {
                            item.setSettletypename(settlement.getName());
                        }
                    }
                }
            }
            if(StringUtils.isNotEmpty(item.getHandlerid())) {
                queryMap.clear();
                queryMap.put("id", item.getHandlerid());
                Contacter contacter = getBaseFilesContacterMapper().getContacterDetail(queryMap);
                if (contacter != null) {
                    item.setHandlername(contacter.getName());
                }
            }
        }
        return list;
    }

    @Override
    public void updateBillSyncPrinttimesProc() throws Exception {
        getSalesDispatchBillMapper().updateBillSyncPrinttimesProc();
    }

    @Override
    public void updateBillSyncPhprinttimesProc() throws Exception {
        getSalesDispatchBillMapper().updateBillSyncPhprinttimesProc();
    }
    @Override
    public Map getDispatchBillDetailTotal(String id) throws Exception{
    	Map total = getSalesDispatchBillMapper().getDispatchBillDetailTotal(id);
    	return total;
    }
    @Override
    public Map showCustomerReceivableInfoData(String customerid) throws Exception {
    	return super.showCustomerReceivableInfoData(customerid);
    }
    @Override
    public String getCustomerBillId(String saleorderid) throws Exception{
    	return getSalesOrderMapper().getCustomerBillId(saleorderid);
    }
    public DispatchBill getDispatchbillPrinttimesById(String id) throws Exception{
        return getSalesDispatchBillMapper().getDispatchbillPrinttimesById(id);
    }
}

