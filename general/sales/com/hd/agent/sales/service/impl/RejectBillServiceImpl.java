/**
 * @(#)RejectBillServiceImpl.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 5, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.*;
import com.hd.agent.sales.service.IOrderService;
import com.hd.agent.sales.service.IRejectBillService;
import com.hd.agent.sales.service.ext.IReceiptExtService;
import com.hd.agent.storage.model.SaleRejectEnter;
import com.hd.agent.storage.service.IStorageForSalesService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 
 * 
 * @author zhengziyong
 */
public class RejectBillServiceImpl extends BaseSalesServiceImpl implements IRejectBillService {

    private IReceiptExtService salesReceiptExtService;
    private IStorageForSalesService storageForSalesService;
    private IOrderService salesOrderService;

    public IReceiptExtService getSalesReceiptExtService() {
        return salesReceiptExtService;
    }

    public void setSalesReceiptExtService(IReceiptExtService salesReceiptExtService) {
        this.salesReceiptExtService = salesReceiptExtService;
    }

    public IStorageForSalesService getStorageForSalesService() {
        return storageForSalesService;
    }

    public void setStorageForSalesService(
            IStorageForSalesService storageForSalesService) {
        this.storageForSalesService = storageForSalesService;
    }

    public IOrderService getSalesOrderService() {
        return salesOrderService;
    }

    public void setSalesOrderService(IOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    @Override
    public boolean addRejectBill(RejectBill bill) throws Exception {
        return super.addRejectBill(bill);
    }

    @Override
    public boolean addRejectBillForPhone(RejectBill bill) throws Exception {
        int count = getSalesRejectBillMapper().getRejectBillCountById(bill.getId());
        if (count > 0) {
            return true;
        }
        //根据客户编号获取默认销售内勤用户信息
        SysUser sysUser = getSalesIndoorSysUserByCustomerid(bill.getCustomerid());
        if (sysUser != null) {
            bill.setIndooruserid(sysUser.getPersonnelid());
        }
        List<RejectBillDetail> billDetailList = bill.getBillDetailList();
        if (billDetailList != null) {
            for (RejectBillDetail billDetail : billDetailList) {
                if (billDetail != null) {
                    billDetail.setBillid(bill.getId());
                    if (null == billDetail.getCostprice() || billDetail.getCostprice().compareTo(BigDecimal.ZERO) == 0) {
                        GoodsInfo goodsInfo = getAllGoodsInfoByID(billDetail.getGoodsid());
                        if (null != goodsInfo) {
                            //计算辅单位数量
                            Map auxmap = countGoodsInfoNumber(billDetail.getGoodsid(), billDetail.getAuxunitid(), billDetail.getUnitnum());
                            if (auxmap.containsKey("auxnum")) {
                                billDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
                            }
                            billDetail.setGoodssort(goodsInfo.getDefaultsort());
                            //成本价
                            billDetail.setCostprice(getGoodsCostprice(bill.getStorageid(),goodsInfo));
                            billDetail.setBrandid(goodsInfo.getBrand());
                            billDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), bill.getCustomerid()));
                            //厂家业务员
                            billDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), bill.getCustomerid()));
                            Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                            if (null != brand) {
                                billDetail.setBranddept(brand.getDeptid());
                            }
                            billDetail.setSupplierid(goodsInfo.getDefaultsupplier());
                        }
                    }
                    getSalesRejectBillMapper().addRejectBillDetail(billDetail);
                }
            }
        }
        return getSalesRejectBillMapper().addRejectBill(bill) > 0;
    }

    @Override
    public boolean addRejectBillForCar(RejectBill bill) throws Exception {
        int count = getSalesRejectBillMapper().getRejectBillCountById(bill.getId());
        if (count > 0) {
            return true;
        }
        if (isAutoCreate("t_sales_rejectbill")) {
            // 获取自动编号
            String id = getAutoCreateSysNumbderForeign(bill, "t_sales_rejectbill");
            bill.setId(id);
        }else{
            bill.setId("THTZD-"+CommonUtils.getDataNumberSeconds());
        }
		//根据客户编号获取默认销售内勤用户信息
		SysUser sysUser = getSalesIndoorSysUserByCustomerid(bill.getCustomerid());
		if(sysUser != null){
			bill.setIndooruserid(sysUser.getPersonnelid());
		}
		bill.setSource("7");
		bill.setRemark("现场交易退货");
		List<RejectBillDetail> billDetailList = bill.getBillDetailList();
		if(billDetailList != null){
			for(RejectBillDetail billDetail : billDetailList){
				if(billDetail != null){
					billDetail.setBillid(bill.getId());
					if(null==billDetail.getCostprice() || billDetail.getCostprice().compareTo(BigDecimal.ZERO)==0){
						GoodsInfo goodsInfo = getAllGoodsInfoByID(billDetail.getGoodsid());
						if(null!=goodsInfo){
							//计算辅单位数量
							Map auxmap = countGoodsInfoNumber(billDetail.getGoodsid(), billDetail.getAuxunitid(), billDetail.getUnitnum());
							if(auxmap.containsKey("auxnum")){
								billDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
							}
							billDetail.setGoodssort(goodsInfo.getDefaultsort());
							//成本价
							billDetail.setCostprice(getGoodsCostprice(bill.getStorageid(),goodsInfo));
							billDetail.setBrandid(goodsInfo.getBrand());
							billDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), bill.getCustomerid()));
							//厂家业务员
							billDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), bill.getCustomerid()));
							Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
							if(null!=brand){
								billDetail.setBranddept(brand.getDeptid());
							}
							billDetail.setSupplierid(goodsInfo.getDefaultsupplier());
						}
					}
					getSalesRejectBillMapper().addRejectBillDetail(billDetail);
				}
			}
		}
		//现场交易退货属于售后退货
		bill.setBilltype("2");
		boolean addflag = getSalesRejectBillMapper().addRejectBill(bill)>0;
		boolean flag = addflag;
		//车销单据是否自动审核
		String sysparm = getSysParamValue("OrderCarAuditAuto");
		//添加成功
		if(addflag && "1".equals(sysparm)){
			Map auditMap = auditRejectBill("1", bill.getId(),"");
			if(null!=auditMap && auditMap.containsKey("flag")){
				flag = (Boolean) auditMap.get("flag");
			}
		}
		return flag;
	}
	@Override
	public boolean updateRejectBill(RejectBill bill) throws Exception {
		Map map = new HashMap();
		map.put("id", bill.getId());
		RejectBill rejectBill = getSalesRejectBillMapper().getRejectBill(map);
		if(null== rejectBill || "3".equals(rejectBill.getStatus()) || "4".equals(rejectBill.getStatus())){
			return false;
		}
		//销售区域
		Customer customer = getCustomerByID(bill.getCustomerid());
		if(null!=customer){
			bill.setSalesarea(customer.getSalesarea());
			bill.setPcustomerid(customer.getPid());
			bill.setCustomersort(customer.getCustomersort());
		}
		//是否残次 残次取残次仓库
		if("1".equals(bill.getIsincomplete())){
			String storageid = getSysParamValue("INCOMPLETESTORAGE");
			bill.setStorageid(storageid);
		}
		getSalesRejectBillMapper().deleteRejectBillDetailByBill(bill.getId());
		List<RejectBillDetail> billDetailList = bill.getBillDetailList();
		if(billDetailList.size() > 0){
			for(RejectBillDetail billDetail : billDetailList){
				if(billDetail != null && StringUtils.isNotEmpty(billDetail.getGoodsid())){
					billDetail.setBillid(bill.getId());
					GoodsInfo goodsInfo = getAllGoodsInfoByID(billDetail.getGoodsid());
					if(null!=goodsInfo){
						//计算辅单位数量
						Map auxmap = countGoodsInfoNumber(billDetail.getGoodsid(), billDetail.getAuxunitid(), billDetail.getUnitnum());
						if(auxmap.containsKey("auxnum")){
							billDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
						}
						billDetail.setGoodssort(goodsInfo.getDefaultsort());
						//成本价
						billDetail.setCostprice(getGoodsCostprice(bill.getStorageid(),goodsInfo));
						billDetail.setBrandid(goodsInfo.getBrand());
						billDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), bill.getCustomerid()));
						//厂家业务员
						billDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), bill.getCustomerid()));
						//品牌部门
						Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
						if(null!=brand){
							billDetail.setBranddept(brand.getDeptid());
						}
						billDetail.setSupplierid(goodsInfo.getDefaultsupplier());
                        //当单据中仓库编号不为空时，判断明细的仓库编号是否一致，不一致时设为空
                        if(StringUtils.isNotEmpty(bill.getStorageid()) && StringUtils.isNotEmpty(billDetail.getSummarybatchid())){
                            if(!bill.getStorageid().equals(billDetail.getStorageid())){
                                billDetail.setSummarybatchid("");
                                billDetail.setStorageid("");
                            }
                        }
					}
					getSalesRejectBillMapper().addRejectBillDetail(billDetail);
				}
			}
		}
		//根据客户编号获取默认销售内勤用户信息
		SysUser sysUser = getSalesIndoorSysUserByCustomerid(bill.getCustomerid());
		if(sysUser != null){
			bill.setIndooruserid(sysUser.getPersonnelid());
		}
		boolean flag = getSalesRejectBillMapper().updateRejectBillVersion(bill)>0;
		if(!flag){
		    throw new Exception("销售退货通知单修改失败。 单据编号："+bill.getId());
        }
		return flag;
	}

    @Override
    public boolean deleteRejectBill(String id) throws Exception {
        int count = getSalesRejectBillMapper().getDirectRejectBillListReferCount(id);
        if (count == 0) {
            return super.deleteRejectBill(id);
        } else {
            return false;
        }
    }

    @Override
    public PageData getRejectBillData(PageMap pageMap) throws Exception {
        String sql = getDataAccessRule("t_sales_rejectbill", "t"); //数据权限
        pageMap.setDataSql(sql);
        List<RejectBill> billList = getSalesRejectBillMapper().getRejectBillList(pageMap);
        for (RejectBill bill : billList) {
            //保存验收后的验收人出现过取用户名的BUG 这里对验收的记录进行显示修改 改为用户名称
            SysUser user = getSysUserByUserName(bill.getStopusername());
            if(null != user ){
                bill.setStopusername(user.getName());
            }
            Personnel indoorPerson = getPersonnelById(bill.getIndooruserid());
            if (null != indoorPerson) {
                bill.setIndoorusername(indoorPerson.getName());
            }
            Personnel driver = getPersonnelById(bill.getDriverid());
            if (null != driver) {
                bill.setDrivername(driver.getName());
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
                bill.setCustomername(customer.getName());
            }
            Personnel storager=getBaseFilesPersonnelMapper().getPersonnelInfo(bill.getStorager());
            if(storager!=null){
                bill.setStoragername(storager.getName());
            }
            bill.setCustomerInfo(customer);
            //获取总店客户
            Customer headCustomer = getHeadCustomer(bill.getPcustomerid());
            if (null != headCustomer) {
                bill.setHeadcustomerid(headCustomer.getId());
                bill.setHeadcustomername(headCustomer.getName());
            }
            map.put("id", bill.getHandlerid());
            Contacter contacter = getBaseFilesContacterMapper().getContacterDetail(map);
            if (contacter != null) {
                bill.setHandlerid(contacter.getName());
            }
            Map total = getSalesRejectBillMapper().getRejectBillDetailTotal(bill.getId());
            if (total != null) {
                if (total.containsKey("taxamount")) {
                    bill.setTotaltaxamount((BigDecimal) total.get("taxamount"));
                }
                if (total.containsKey("notaxamount")) {
                    bill.setTotalnotaxamount((BigDecimal) total.get("notaxamount"));
                }
                if (total.containsKey("tax")) {
                    bill.setTotaltax((BigDecimal) total.get("tax"));
                }
            }
        }
        return new PageData(getSalesRejectBillMapper().getRejectBillCount(pageMap), billList, pageMap);
    }

    @Override
    public RejectBill getRejectBill(String id) throws Exception {
        Map map = new HashMap();
        map.put("id", id);
        RejectBill rejectBill = getSalesRejectBillMapper().getRejectBill(map);
        if (null != rejectBill) {
            Customer customer = getCustomerByID(rejectBill.getCustomerid());
            if (null != customer) {
                rejectBill.setCustomername(customer.getName());
            }
            Contacter contacter = getContacterById(rejectBill.getHandlerid());
            if (null != contacter) {
                rejectBill.setHandlername(contacter.getName());
            }
            Personnel driver = getPersonnelById(rejectBill.getDriverid());
            if (null != driver) {
                rejectBill.setDrivername(driver.getName());
            }
            DepartMent departMent = getDepartmentByDeptid(rejectBill.getSalesdept());
            if (null != departMent) {
                rejectBill.setSalesdeptname(departMent.getName());
            }
            Personnel personnel = getPersonnelById(rejectBill.getSalesuser());
            if (null != personnel) {
                rejectBill.setSalesusername(personnel.getName());
            }
            Settlement settlement = getSettlementByID(rejectBill.getSettletype());
            if (null != settlement) {
                rejectBill.setSettletypename(settlement.getName());
            }
            Payment payment = getPaymentByID(rejectBill.getPaytype());
            if (null != payment) {
                rejectBill.setPaytypename(payment.getName());
            }
            StorageInfo storageInfo = getStorageInfoByID(rejectBill.getStorageid());
            if (null != storageInfo) {
                rejectBill.setStoragename(storageInfo.getName());
            }
            List<RejectBillDetail> billDetailList = getSalesRejectBillMapper().getRejectBillDetailListByBill(id);
            for (RejectBillDetail billDetail : billDetailList) {
                GoodsInfo goodsInfo = getGoodsInfoByID(billDetail.getGoodsid());
                if(goodsInfo!=null){
                    goodsInfo.setItemno(getItemnoByGoodsAndStorage(billDetail.getGoodsid(),rejectBill.getStorageid()));
                    billDetail.setGoodsInfo(goodsInfo);
                    billDetail.setBuyprice(goodsInfo.getNewbuyprice());
                }

                TaxType taxType = getTaxType(billDetail.getTaxtype());
                if (taxType != null) {
                    billDetail.setTaxtypename(taxType.getName());
                }
                //箱价
                if (null != goodsInfo) {
                    billDetail.setBoxprice(goodsInfo.getBoxnum().multiply(billDetail.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                }
                StorageInfo storageInfo1 = getStorageInfoByID(billDetail.getStorageid());
                if (null != storageInfo1) {
                	billDetail.setStoragename(storageInfo1.getName());
                }
            }
            rejectBill.setBillDetailList(billDetailList);
        }
        return rejectBill;
    }

    @Override
    public Map changeModelForDetail(List<ModelOrder> wareList, String gtype) throws Exception {
        Map map = new HashMap();
        List disableGoods = new ArrayList();
        List unimportGoods = new ArrayList();
        List<RejectBillDetail> detailList = new ArrayList<RejectBillDetail>();
        List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
        for(ModelOrder modelOrder : wareList){
            GoodsInfo goodsInfo = new GoodsInfo();
            String goodsidefity = "";
            if("1".equals(gtype)){
                goodsidefity = modelOrder.getBarcode();
                goodsInfo = getGoodsInfoByBarcode(modelOrder.getBarcode());
            }else if("2".equals(gtype)){//查询店内码
                goodsidefity = modelOrder.getShopid();
                goodsInfo = getGoodsInfoByCustomerGoodsid(modelOrder.getBusid(),modelOrder.getShopid());
            }else if("3".equals(gtype)){//查询助记符
                goodsidefity = modelOrder.getSpell();
                goodsInfo = getGoodsInfoBySpell(modelOrder.getSpell());
            }else if("4".equals(gtype)){//查询商品编码
                goodsidefity = modelOrder.getGoodsid();
                goodsInfo = getGoodsInfoByID(modelOrder.getGoodsid());
            }
            if(null != goodsInfo){
                goodsInfo = getAllGoodsInfoByID(goodsInfo.getId());
                //禁用商品存在相同条码或者相同编码的情况
                if( "0".equals(goodsInfo.getState())){
                    Map goodsMap = new HashMap();
                    if("1".equals(gtype)){
                        goodsMap.put("barcode",goodsInfo.getBarcode());
                    }else if("3".equals(gtype)){
                        goodsMap.put("spell",goodsInfo.getSpell());
                    }else if("4".equals(gtype)){
                        goodsMap.put("id",goodsInfo.getId());
                    }
                    List<GoodsInfo> goodsInfoList = getBaseGoodsMapper().getGoodsInfoListByMap(goodsMap);
                    for(GoodsInfo gInfo : goodsInfoList){
                        if(null != gInfo && "1".equals(gInfo.getState())){
                            goodsInfo = getAllGoodsInfoByID(gInfo.getId());
                            break;
                        }
                    }
                    if( "0".equals(goodsInfo.getState())){
                        goodsMap.remove("id");
                        goodsMap.put("barcode",goodsInfo.getId());
                        goodsInfoList = getBaseGoodsMapper().getGoodsInfoListByMap(goodsMap);
                        for(GoodsInfo gInfo : goodsInfoList){
                            if(null != gInfo && "1".equals(gInfo.getState())){
                                goodsInfo = getAllGoodsInfoByID(gInfo.getId());
                                break;
                            }
                        }
                    }
                }
                //禁用商品不导入
                if(null != goodsInfo &&  "0".equals(goodsInfo.getState())){
                    disableGoods.add(goodsidefity);
                    Map errorMap = new HashMap();
                    if("1".equals(gtype)){
                        errorMap.put("goodsid",modelOrder.getBarcode());
                    }else if("2".equals(gtype)){
                        errorMap.put("goodsid",modelOrder.getShopid());
                    }else if("3".equals(gtype)){
                        errorMap.put("goodsid",modelOrder.getSpell());
                    }else if("4".equals(gtype)){
                        errorMap.put("goodsid",modelOrder.getGoodsid());
                    }
                    errorMap.put("orderid",modelOrder.getOrderId());
                    errorMap.put("unitnum",modelOrder.getUnitnum());
                    errorMap.put("price",modelOrder.getTaxprice());
                    errorList.add(errorMap);
                    continue;
                }
                RejectBillDetail rejectDetail = new RejectBillDetail();
                rejectDetail.setGoodsid(goodsInfo.getId());
                rejectDetail.setGoodsInfo(goodsInfo);
                rejectDetail.setGoodssort(goodsInfo.getDefaultsort());
                rejectDetail.setBrandid(goodsInfo.getBrand());
                rejectDetail.setUnitid(goodsInfo.getMainunit());
                rejectDetail.setUnitname(goodsInfo.getMainunitName());

                BigDecimal boxnum = goodsInfo.getBoxnum();
                String num = modelOrder.getUnitnum();
                if(StringUtils.isEmpty(num)){
                    continue;
                }
                BigDecimal unitnum = new BigDecimal(num);
                if(unitnum.compareTo(BigDecimal.ZERO) < 0){
                    unitnum = unitnum.multiply(new BigDecimal(-1));
                }
                BigDecimal auxnum = new BigDecimal(0);
                BigDecimal overnum ;

                if(null != boxnum && boxnum.compareTo(BigDecimal.ZERO) > 0){
                    auxnum = unitnum.divide(boxnum,0,BigDecimal.ROUND_DOWN);
                    overnum = unitnum.subtract(auxnum.multiply(boxnum)).setScale(0,BigDecimal.ROUND_HALF_UP);
                }else{
                    overnum = unitnum;
                }
                rejectDetail.setUnitnum(unitnum);
                rejectDetail.setAuxunitid(goodsInfo.getAuxunitid());
                rejectDetail.setAuxunitname(goodsInfo.getAuxunitname());
                rejectDetail.setAuxnum(auxnum);
                rejectDetail.setAuxnumdetail(auxnum+goodsInfo.getAuxunitname()+overnum+goodsInfo.getMainunitName());
                rejectDetail.setAuxremainder(overnum);

                //0取基准销售价,1取合同价
                String priceType = getSysParamValue("SalesRejectCustomerGoodsPrice");
                BigDecimal price = new BigDecimal(0);
                if(StringUtils.isNotEmpty(modelOrder.getTaxprice())){
                    price = new BigDecimal(modelOrder.getTaxprice());
                }else if("1".equals(priceType) ){
                    Map lastGoodsMap = getSalesOrderMapper().getCustomerGoodsInfoByLast(modelOrder.getBusid(), goodsInfo.getId());
                    if (null != lastGoodsMap) {
                        price = (BigDecimal) lastGoodsMap.get("taxprice");
                    }
                }else if("2".equals(priceType)){
                    //取最近一段时间内的最低销售价
                    String rejectCustomerGoodsPriceInMonth = getSysParamValue("RejectCustomerGoodsPriceInMonth");
                    int month = 3;
                    if(StringUtils.isNotEmpty(rejectCustomerGoodsPriceInMonth)){
                        month = Integer.parseInt(rejectCustomerGoodsPriceInMonth);
                    }
                    String date = CommonUtils.getBeforeDateInMonth(month);
                    Map lastGoodsMap = getSalesOrderMapper().getCustomerGoodsInfoByLowest(modelOrder.getBusid(), goodsInfo.getId(),date);
                    if (null != lastGoodsMap) {
                        price = (BigDecimal) lastGoodsMap.get("taxprice");
                    }
                }
                if(price.compareTo(BigDecimal.ZERO) == 0){
                    //系统参数取价
                    price = getGoodsPriceByCustomer(goodsInfo.getId(),modelOrder.getBusid());
                }
                rejectDetail.setTaxprice(price);
                BigDecimal taxamount = price.multiply(unitnum);
                rejectDetail.setTaxamount(taxamount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP));

                rejectDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
                TaxType taxType = getTaxType(rejectDetail.getTaxtype());
                if (taxType != null) {
                    rejectDetail.setTaxtypename(taxType.getName());
                }
                BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, rejectDetail.getTaxtype());

                if (null != notaxamount && notaxamount.compareTo(BigDecimal.ZERO) != 0) {
                    BigDecimal notaxprice = notaxamount.divide(rejectDetail.getUnitnum(), 6, BigDecimal.ROUND_HALF_UP);
                    rejectDetail.setNotaxprice(notaxprice);
                }
                rejectDetail.setNotaxamount(notaxamount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
                rejectDetail.setTax(rejectDetail.getTaxamount().subtract(rejectDetail.getNotaxamount()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));

                detailList.add(rejectDetail);

            }else{
                if(StringUtils.isNotEmpty(goodsidefity)){
                    unimportGoods.add(goodsidefity);
                }
                if(StringUtils.isNotEmpty(modelOrder.getShopid()) && "2".equals(gtype)){
                    unimportGoods.add(modelOrder.getShopid());
                }
                if(StringUtils.isNotEmpty(modelOrder.getOrderId())){
                    Map errorMap = new HashMap();
                    if("1".equals(gtype)){
                        errorMap.put("goodsid",modelOrder.getBarcode());
                    }else if("2".equals(gtype)){
                        errorMap.put("goodsid",modelOrder.getShopid());
                    }else if("3".equals(gtype)){
                        errorMap.put("goodsid",modelOrder.getSpell());
                    }else if("4".equals(gtype)){
                        errorMap.put("goodsid",modelOrder.getGoodsid());
                    }
                    errorMap.put("orderid",modelOrder.getOrderId());
                    errorMap.put("unitnum",modelOrder.getUnitnum());
                    errorMap.put("price",modelOrder.getTaxprice());
                    errorList.add(errorMap);
                }

            }
        }
        if(unimportGoods.size() > 0){
            String unimport = unimportGoods.toString();
            unimport = unimport.replace("[","");
            unimport = unimport.replace("]","");
            map.put("unimportGoods",unimport);
        }
        String disable = disableGoods.toString();
        disable = disable.replace("[","");disable = disable.replace("]","");
        map.put("disableGoods",disable);
        map.put("detailList",detailList);
        if(errorList.size() > 0 ){
            map.put("errorList",errorList);
        }
        return map;
    }

    //现退货通知单只有无来源类型，为验收退货，直退则回单直接生成退货入库单，回写注释掉
    @Override
    public Map auditRejectBill(String type, String id,String storager) throws Exception {
        Map result = new HashMap();
        SysUser sysUser = getSysUser();
        RejectBill rejectBill = getRejectBill(id);
        if (null == sysUser) {
            sysUser = getSysUserById(rejectBill.getAdduserid());
        }
        //业务日期是否取审核日期 0否1是
        String auditBusinessdate = getAuditBusinessdate(rejectBill.getBusinessdate());
        Date businessdate = CommonUtils.stringToDate(auditBusinessdate);
        if ("1".equals(type) && "2".equals(rejectBill.getStatus())) { //审核
            //多少天之内或多少月之内的最低销售价；
            String rejectCustomerGoodsPriceInMonth = getSysParamValue("RejectCustomerGoodsPriceInMonth");
            int month = 3;
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(rejectCustomerGoodsPriceInMonth)){
                month = Integer.parseInt(rejectCustomerGoodsPriceInMonth);
            }
            String lowestdate = CommonUtils.getBeforeDateInMonth(month);

            List<RejectBillDetail> detaiList = rejectBill.getBillDetailList();
            List dataList = new ArrayList();
            String brandid = null;
            boolean brandMu = false;
            for (RejectBillDetail rejectBillDetail : detaiList) {
                //退货数量为0的时候 删除该条明细
                if (rejectBillDetail.getUnitnum().compareTo(BigDecimal.ZERO) == 0) {
                    getSalesRejectBillMapper().deleteRejectBillDetailByDetailid(rejectBillDetail.getId());
                } else {
                    if(null==brandid){
                        brandid = rejectBillDetail.getBrandid();
                    }else if(!brandid.equals(rejectBillDetail.getBrandid())){
                        brandMu = true;
                    }
                    //应收日期
                    String duefromdate = getReceiptDateBySettlement(businessdate,rejectBill.getCustomerid(),rejectBillDetail.getBrandid());

                    //取默认销售价（当时）（价格套价格或合同价）
                    BigDecimal defaultprice = getDefaultSalesPrice(rejectBill.getCustomerid(),rejectBillDetail.getGoodsid());
                    rejectBillDetail.setDefaultprice(defaultprice);
                    //多少天之内或多少月之内的最低销售价；
                    BigDecimal lowestprice = getSalesRejectBillMapper().getRejectBillLowestPrice(rejectBill.getCustomerid(),rejectBillDetail.getGoodsid(),lowestdate);
                    if(null == lowestprice){
                        lowestprice = defaultprice;
                    }
                    rejectBillDetail.setLowestprice(lowestprice);
                    //最近一次销售价（交易价）
                    BigDecimal lastprice = getSalesRejectBillMapper().getRejectBillLastPrice(rejectBill.getCustomerid(),rejectBillDetail.getGoodsid());
                    if(null == lastprice){
                        lastprice = defaultprice;
                    }
                    rejectBillDetail.setLastprice(lastprice);
                    rejectBillDetail.setDuefromdate(duefromdate);
                    getSalesRejectBillMapper().updateSaleRejectBillDetailPrice(rejectBillDetail);
                    dataList.add(rejectBillDetail);
                }
            }
            //判断单据里面是否只有单个品牌 单个品牌则单据应收日期取该品牌的应收日期
            if(brandMu){
                brandid = null;
            }
            //获取该单据的应收日期
            rejectBill.setDuefromdate(getReceiptDateBySettlement(businessdate, rejectBill.getCustomerid(),brandid));
            rejectBill.setStorager(storager);
            String billId = storageForSalesService.addSaleRejectEnterByRejectBill(rejectBill, dataList);//生成下游单据
            getSalesRejectBillMapper().updateRejectBillRefer("1", id); //更改参照状态

            RejectBill rejectBill2 = new RejectBill();
            rejectBill2.setBusinessdate(auditBusinessdate);
            rejectBill2.setAudituserid(sysUser.getUserid());
            rejectBill2.setAuditusername(sysUser.getName());
            rejectBill2.setAudittime(new Date());
            rejectBill2.setStatus("3");
            rejectBill2.setStorager(storager);
            rejectBill2.setBilltype(rejectBill.getBilltype());
            rejectBill2.setId(id);
            //获取该单据的应收日期
            rejectBill2.setDuefromdate(rejectBill.getDuefromdate());
            rejectBill2.setVersion(rejectBill.getVersion());
            boolean flag = getSalesRejectBillMapper().updateRejectBillStatus(rejectBill2) > 0;
            if(!flag){
                throw new Exception("销售退货通知单审核失败 单据编号："+id);
            }
            //是否自动验收退货通知单
            String isAutoRejectCheck = getSysParamValue("IsAutoRejectCheck");
            if (flag && "1".equals(isAutoRejectCheck)) {
                auditRejectBillCheck(rejectBill2);
            }
            result.put("flag", flag);
            result.put("billId", billId);
		}
		else if("2".equals(type)){ //反审
            if(StringUtils.isNotEmpty(rejectBill.getReceiptid())){
                result.put("flag", false);
                result.put("msg", "已关联回单不能反审");
                return result;
            }
			if(!"3".equals(rejectBill.getStatus())){ //只有状态为3（审核状态）才可进行反审
				result.put("flag", false);
				return result;
			}else if( "3".equals(rejectBill.getStatus()) && (!"0".equals(rejectBill.getIsinvoice()) || !"0".equals(rejectBill.getIsinvoicebill()))){
				//是否自动验收退货通知单
				//如果不是自动验收的 需要取消验收之后才能反审
				String isAutoRejectCheck = getSysParamValue("IsAutoRejectCheck");
                if("1".equals(isAutoRejectCheck)){
                    if("3".equals(rejectBill.getIsinvoice()) && "3".equals(rejectBill.getIsinvoicebill())){

                    }else{
                        result.put("flag", false);
                        result.put("msg", "已开票不能反审");
                        return result;
                    }
                }else{
                    result.put("flag", false);
                    result.put("msg", "已验收不能反审");
                    return result;
                }
			}
			//反审 并且删除 销售退货入库单
			boolean bl = storageForSalesService.deleteSaleRejectEnterBySourceid(rejectBill.getId());
			if(!bl){
				result.put("flag", false);
				result.put("billArg", bl);
				return result;
			}
			RejectBill rejectBill2 = new RejectBill();
			rejectBill2.setStatus("2");
			rejectBill2.setId(id);
			rejectBill2.setVersion(rejectBill.getVersion());
			boolean flag = getSalesRejectBillMapper().updateRejectBillStatus(rejectBill2) > 0;
			if(!flag){
                throw new Exception("销售退货通知单反审失败 单据编号："+id);
            }
			result.put("flag", flag);
			result.put("billArg", bl);
		}
		else if("3".equals(type)){
			String billId = storageForSalesService.addSaleRejectEnterByRejectBill(rejectBill, rejectBill.getBillDetailList());//生成下游单据
			getSalesRejectBillMapper().updateRejectBillRefer("1", id); //更改参照状态
		}
		return result;
	}
	
	@Override
	public Map checkBuyGoodsRejectBill(String id) throws Exception {
		Map result = new HashMap();
		String unbuymsg = "";
		RejectBill rejectBill = getRejectBill(id);
		//判断该销售退货通知单的客户有没有买过该商品
		List<RejectBillDetail> detaiList = getSalesRejectBillMapper().getRejectBillDetailListByBill(id);
		String ungoodsids = "";
		boolean isZero = false;
		for(RejectBillDetail rejectBillDetail : detaiList){
			boolean flag2 = checkCustomerGoods(rejectBill.getCustomerid(),rejectBillDetail.getGoodsid());
			if(!flag2){//未买过
				if(StringUtils.isEmpty(ungoodsids)){
					ungoodsids = rejectBillDetail.getGoodsid();
				}else{
					ungoodsids += "," + rejectBillDetail.getGoodsid();
				}
			}
			if(rejectBillDetail.getUnitnum().compareTo(BigDecimal.ZERO)==0){
				isZero = true;
			}
		}
		if(isZero){
			unbuymsg += "有0退货的商品，审核之后将自动删除。";
		}
		if(StringUtils.isNotEmpty(ungoodsids)){
			unbuymsg += "该客户没有买过编码为:" + ungoodsids +"的商品,是否继续审核?";
		}
		result.put("unbuymsg", unbuymsg);
		return result;
	}

    @Override
    public boolean checkRejectRepeat(String id) throws Exception {
        Map querymap = new HashMap();
        querymap.put("id", id);
        RejectBill rejectBill = getSalesRejectBillMapper().getRejectBill(querymap);
        if (null != rejectBill) {
            Map map = getSalesRejectBillMapper().checkRejectRepeat(id, rejectBill.getCustomerid());
            if (null == map) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    @Override
    public Map checkRejectStorage(String id) throws Exception {
        Map querymap = new HashMap();
        Map result = new HashMap();
        querymap.put("id", id);
        List<String> nullgoodsid=new ArrayList();
        RejectBill rejectBill = getSalesRejectBillMapper().getRejectBill(querymap);
        if (null != rejectBill) {
            if("".equals(rejectBill.getStorageid())){
            	List rejectBillList=getSalesRejectBillMapper().getRejectBillDetailListByBill(id);
            	if(null!=rejectBillList){
            	    for(Object obj:rejectBillList){
            		    RejectBillDetail  rejectBillDetail=(RejectBillDetail) obj;
            		    GoodsInfo goodsInfo = getGoodsInfoByID(rejectBillDetail.getGoodsid());
            		    if("".equals(goodsInfo.getStorageid())){
            		    	nullgoodsid.add(goodsInfo.getId());
            		    }
            	    }
            	    if(nullgoodsid.size()==0){
            	    	result.put("flag", true);
                        return result;
            	    }else{
            	    	String msg="";
            	    	for(String str : nullgoodsid){
            	    		if(""==msg){
            	    			msg=str;
            	    		}
            	    		else{
            	    			msg+=","+str;
            	    		}
            	    	}
            	    	result.put("msg", msg);
            	    	result.put("flag", false);
                        return result;
            	    }
            	}else{
            		result.put("flag", false);
                    return result;
            	}
            }
            else{
            	result.put("flag", true);
                return result;
            }
        } else {
        	result.put("flag", false);
            return result;
        }
    }
    
    @Override
    public boolean submitRejectBillProcess(String title, String userId, String processDefinitionKey, String businessKey, Map<String, Object> variables) throws Exception {
        return false;
    }

    @Override
    public List<RejectBillDetail> getRejectBillDetailListByBill(String id) throws Exception {
        List<RejectBillDetail> billDetailList = getSalesRejectBillMapper().getRejectBillDetailListByBill(id);
        for (RejectBillDetail billDetail : billDetailList) {
            GoodsInfo goodsInfo = getGoodsInfoByID(billDetail.getGoodsid());
            billDetail.setGoodsInfo(goodsInfo);
            TaxType taxType = getTaxType(billDetail.getTaxtype());
            if (taxType != null) {
                billDetail.setTaxtypename(taxType.getName());
            }
        }
        return billDetailList;
    }

    @Override
    public boolean updateOrderPrinttimes(RejectBill rejectBill) throws Exception {
        return getSalesRejectBillMapper().updateOrderPrinttimes(rejectBill) > 0;
    }

    @Override
    public void updateOrderPrinttimes(List<RejectBill> list) throws Exception {
        if (null != list) {
            for (RejectBill item : list) {
                getSalesRejectBillMapper().updateOrderPrinttimes(item);
            }
        }
    }

    @Override
    public List getRejectBillListBy(Map map) throws Exception {
        String sql = getDataAccessRule("t_sales_rejectbill", null); //数据权限
        map.put("dataSql", sql);
        List<RejectBill> list = getSalesRejectBillMapper().getRejectBillListBy(map);
        boolean showdetail = false;
        if (null != map.get("showdetail") && StringUtils.isNotEmpty(map.get("showdetail").toString()) && "1".equals(map.get("showdetail"))) {
            showdetail = true;
        }

        for (RejectBill rejectBill : list) {
            if (null != rejectBill) {
                Customer customer = getCustomerByID(rejectBill.getCustomerid());
                if (null != customer) {
                    rejectBill.setCustomerInfo(customer);
                    rejectBill.setCustomername(customer.getName());
                    if (null != customer.getSalesarea() && !"".equals(customer.getSalesarea())) {
                        Map queryMap = new HashMap();
                        queryMap.put("id", customer.getSalesarea());
                        SalesArea salesArea = getBaseFilesSalesAreaMapper().getSalesAreaDetail(queryMap);
                        if (null != salesArea) {
                            customer.setSalesareaname(salesArea.getName());
                        }
                    }
                }
                Contacter contacter = getContacterById(rejectBill.getHandlerid());
                if (null != contacter) {
                    rejectBill.setHandlername(contacter.getName());
                }
                DepartMent departMent = getDepartmentByDeptid(rejectBill.getSalesdept());
                if (null != departMent) {
                    rejectBill.setSalesdeptname(departMent.getName());
                }
                Personnel personnel = getPersonnelById(rejectBill.getSalesuser());
                if (null != personnel) {
                    rejectBill.setSalesusername(personnel.getName());
                }
                Settlement settlement = getSettlementByID(rejectBill.getSettletype());
                if (null != settlement) {
                    rejectBill.setSettletypename(settlement.getName());
                }
                Payment payment = getPaymentByID(rejectBill.getPaytype());
                if (null != payment) {
                    rejectBill.setPaytypename(payment.getName());
                }
                StorageInfo storageInfo = getStorageInfoByID(rejectBill.getStorageid());
                if (null != storageInfo) {
                    rejectBill.setStoragename(storageInfo.getName());
                }
                Personnel driver = getPersonnelById(rejectBill.getDriverid());
                if (null != driver) {
                    rejectBill.setDrivername(driver.getName());
                }
                if (showdetail) {
                    List<RejectBillDetail> billDetailList = getSalesRejectBillMapper().getRejectBillDetailListByBill(rejectBill.getId());
                    for (RejectBillDetail billDetail : billDetailList) {
                        GoodsInfo goodsInfo = getGoodsInfoByID(billDetail.getGoodsid());
                        billDetail.setGoodsInfo(goodsInfo);
                        TaxType taxType = getTaxType(billDetail.getTaxtype());
                        if (taxType != null) {
                            billDetail.setTaxtypename(taxType.getName());
                        }
                        if(null!=customer){
                            //获取客户店内码
                            CustomerPrice customerPrice=getCustomerPriceByCustomerAndGoodsid(customer.getId(), billDetail.getGoodsid());
                            if(null!=customerPrice && StringUtils.isNotEmpty(customerPrice.getShopid())){
                                billDetail.setShopid(customerPrice.getShopid());
                            }else if(StringUtils.isNotEmpty(customer.getPid())){
                                customerPrice=getCustomerPriceByCustomerAndGoodsid(customer.getPid(), billDetail.getGoodsid());
                                if(null!=customerPrice){
                                    billDetail.setShopid(customerPrice.getShopid());
                                }
                            }
                        }
                    }
                    rejectBill.setBillDetailList(billDetailList);
                }
            }
        }
        return list;
    }

    @Override
    public List showDirectRejectBillDetailByIds(String ids) throws Exception {
        if (null != ids) {
            String[] idArr = ids.split(",");
            List detailList = new ArrayList();
            for (String id : idArr) {
                List<RejectBillDetail> rejectDetaillist = getSalesRejectBillMapper().getRejectBillDetailListByBill(id);
                for (RejectBillDetail rejectBillDetail : rejectDetaillist) {
                    if ("0".equals(rejectBillDetail.getIsrefer())) {
                        GoodsInfo goodsInfo = getAllGoodsInfoByID(rejectBillDetail.getGoodsid());
                        rejectBillDetail.setGoodsInfo(goodsInfo);
                        TaxType taxType = getTaxType(rejectBillDetail.getTaxtype());
                        if (null != taxType) {
                            rejectBillDetail.setTaxtypename(taxType.getName());
                        }
                        detailList.add(rejectBillDetail);
                    }
                }
            }
            return detailList;
        }
        return null;
    }

    @Override
    public Map updateRejectBillSplit(String rejectid, List<RejectBillDetail> list)
            throws Exception {
        Map returnMap = new HashMap();
        boolean flag = false;
        Map map = new HashMap();
        map.put("id", rejectid);
        RejectBill rejectBill = getSalesRejectBillMapper().getRejectBill(map);
        boolean splitflag = true;
        //判断销售退货入库单 是否处于审核通过状态
        List<SaleRejectEnter> saleRejectEnterList = storageForSalesService.getSaleRejectEnterListByRejectid(rejectid);
        for (SaleRejectEnter saleRejectEnter : saleRejectEnterList) {
            if (!"3".equals(saleRejectEnter.getStatus())) {
                splitflag = false;
                break;
            }
        }
        if (null != rejectBill && splitflag) {
            List<RejectBillDetail> detailList = getSalesRejectBillMapper().getRejectBillDetailListByBill(rejectid);
            List<RejectBillDetail> splitList = new ArrayList<RejectBillDetail>();
            for (RejectBillDetail rejectBillDetail : detailList) {
                for (RejectBillDetail splitDetail : list) {
                    if (rejectBillDetail.getId().equals(splitDetail.getId())) {
                        rejectBillDetail.setUnitnum(rejectBillDetail.getUnitnum().subtract(splitDetail.getSplitnum()));
                        BigDecimal taxamount=rejectBillDetail.getUnitnum().multiply(rejectBillDetail.getTaxprice());
                        rejectBillDetail.setTaxamount(taxamount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                        rejectBillDetail.setNotaxprice(rejectBillDetail.getNotaxprice());
                        BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, rejectBillDetail.getTaxtype());
                        rejectBillDetail.setNotaxamount(notaxamount);
                        rejectBillDetail.setTax(rejectBillDetail.getTaxamount().subtract(rejectBillDetail.getNotaxamount()));
                        //辅数量
                        Map auxMap = countGoodsInfoNumber(rejectBillDetail.getGoodsid(), rejectBillDetail.getAuxunitid(), rejectBillDetail.getUnitnum());
                        String auxnumdetail = (String) auxMap.get("auxnumdetail");
                        String auxnumStr = (String) auxMap.get("auxInteger");
                        String auxremainderStr = (String) auxMap.get("auxremainder");
                        BigDecimal auxnum = new BigDecimal(auxnumStr);
                        BigDecimal auxremainder = new BigDecimal(auxremainderStr);
                        rejectBillDetail.setAuxnumdetail(auxnumdetail);
                        rejectBillDetail.setAuxnum(auxnum);
                        rejectBillDetail.setAuxremainder(auxremainder);
                        rejectBillDetail.setTotalbox((BigDecimal) auxMap.get("auxnum"));

                        if (rejectBillDetail.getUnitnum().compareTo(BigDecimal.ZERO) != 0) {
                            rejectBillDetail.setInnummain(rejectBillDetail.getUnitnum());
                            rejectBillDetail.setInnumaux(rejectBillDetail.getAuxnum());
                            rejectBillDetail.setInamounttax(rejectBillDetail.getTaxamount());
                            rejectBillDetail.setInamountnotax(rejectBillDetail.getNotaxamount());
                            rejectBillDetail.setNoinnummain(rejectBillDetail.getUnitnum().subtract(rejectBillDetail.getUnitnum()));
                            rejectBillDetail.setNoinnumaux(rejectBillDetail.getAuxnum().subtract(rejectBillDetail.getAuxnum()));
                            rejectBillDetail.setNoinamounttax(rejectBillDetail.getTaxamount().subtract(rejectBillDetail.getTaxamount()));
                            rejectBillDetail.setNoinamountnotax(rejectBillDetail.getNotaxamount().subtract(rejectBillDetail.getNotaxamount()));
                            //更新退货通知单明细
                            getSalesRejectBillMapper().updateRejectBillDetailBack(rejectBillDetail);
                            //更新销售退货入库单明细
                            storageForSalesService.updateSaleRejectEnterDetailByRejectDetail(rejectBillDetail);
                        } else {
                            //删除退货通知单明细
                            getSalesRejectBillMapper().deleteRejectBillDetailByDetailid(rejectBillDetail.getId());
                            storageForSalesService.deleteSaleRejectEnterDetailByRejectDetail(rejectBillDetail);
                        }
                        splitList.add(splitDetail);
                        break;
                    }
                }
            }
            //更新销售退货入库单
            storageForSalesService.updateSaleRejectEnterAmount(rejectid);
            if (null != splitList && splitList.size() > 0) {
                //拆分出来的明细 生成新的销售退货通知单
                RejectBill ndwRejectBill = (RejectBill) CommonUtils.deepCopy(rejectBill);
                if (isAutoCreate("t_sales_rejectbill")) {
                    // 获取自动编号
                    String id = getAutoCreateSysNumbderForeign(ndwRejectBill, "t_sales_rejectbill");
                    ndwRejectBill.setId(id);
                } else {
                    ndwRejectBill.setId("THTZD-" + CommonUtils.getDataNumberSendsWithRand());
                }
                ndwRejectBill.setKeyid(CommonUtils.getDataNumberSendsWithRand());
                for (RejectBillDetail rejectBillDetail : splitList) {
                    rejectBillDetail.setBillid(ndwRejectBill.getId());
                    rejectBillDetail.setUnitnum(rejectBillDetail.getSplitnum());
                    if (rejectBillDetail.getUnitnum().compareTo(BigDecimal.ZERO) != 0) {
                        BigDecimal taxamount=rejectBillDetail.getUnitnum().multiply(rejectBillDetail.getTaxprice());
                        rejectBillDetail.setTaxamount(taxamount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                        BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, rejectBillDetail.getTaxtype());
                        if (null != notaxamount && notaxamount.compareTo(BigDecimal.ZERO) != 0) {
                            BigDecimal notaxprice = notaxamount.divide(rejectBillDetail.getUnitnum(), 6, BigDecimal.ROUND_HALF_UP);
                            rejectBillDetail.setNotaxprice(notaxprice);
                        }
                        rejectBillDetail.setNotaxamount(notaxamount);
                        rejectBillDetail.setTax(rejectBillDetail.getTaxamount().subtract(rejectBillDetail.getNotaxamount()));
                        //辅数量
                        Map auxMap = countGoodsInfoNumber(rejectBillDetail.getGoodsid(), rejectBillDetail.getAuxunitid(), rejectBillDetail.getUnitnum());
                        String auxnumdetail = (String) auxMap.get("auxnumdetail");
                        String auxnumStr = (String) auxMap.get("auxInteger");
                        String auxremainderStr = (String) auxMap.get("auxremainder");
                        BigDecimal auxnum = new BigDecimal(auxnumStr);
                        BigDecimal auxremainder = new BigDecimal(auxremainderStr);
                        rejectBillDetail.setAuxnumdetail(auxnumdetail);
                        rejectBillDetail.setAuxnum(auxnum);
                        rejectBillDetail.setAuxremainder(auxremainder);
                        rejectBillDetail.setTotalbox((BigDecimal) auxMap.get("auxnum"));
                        //getSalesRejectBillMapper().updateRejectBillDetailBack(rejectBillDetail);
                        //添加退货通知单明细
                        getSalesRejectBillMapper().addRejectBillDetail(rejectBillDetail);
                    }
                }
                int i = getSalesRejectBillMapper().addRejectBill(ndwRejectBill);
                flag = i > 0;
                if (flag) {
                    //生成拆分的销售退货入库单
                    List addSplitList = getSalesRejectBillMapper().getRejectBillDetailListByBill(ndwRejectBill.getId());
                    storageForSalesService.addSaleRejectEnterByRejectSplit(ndwRejectBill, addSplitList);
                }
                returnMap.put("id", ndwRejectBill.getId());
            } else {
                returnMap.put("msg", "没有要拆分的单据明细!");
            }
        }
        returnMap.put("flag", flag);
        return returnMap;
    }

    /**
     * 审核验收退货通知单
     * @param rejectBill
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年8月12日
     */
    public boolean auditRejectBillCheck(RejectBill rejectBill) throws Exception {
        boolean flag = false;
        Map map = new HashMap();
        map.put("id", rejectBill.getId());
        RejectBill oldRejectBill = getSalesRejectBillMapper().getRejectBill(map);
        if (null != oldRejectBill.getReceiptid() && !"".equals(oldRejectBill.getReceiptid())) {
            return false;
        }
        //根据退货通知单 获取退货入库单列表
        List<SaleRejectEnter> salesEnterList = storageForSalesService.getSaleRejectEnterListByRejectid(rejectBill.getId());
        boolean checkFlag = true;
        for (SaleRejectEnter saleRejectEnter : salesEnterList) {
            if (!"3".equals(saleRejectEnter.getStatus()) && !"4".equals(saleRejectEnter.getStatus())) {
                checkFlag = false;
                break;
            }
        }
        if (!checkFlag) {
            return false;
        }
        if ("2".equals(rejectBill.getBilltype())) {
            rejectBill.setIsinvoice("3");
            rejectBill.setIsinvoicebill("3");

            rejectBill.setCheckdate(getAuditBusinessdate(rejectBill.getBusinessdate()));
            SysUser sysUser = getSysUser();
            rejectBill.setStopuserid(sysUser.getUserid());
            rejectBill.setStopusername(sysUser.getUsername());
        }
        flag = getSalesRejectBillMapper().updateRejectBill(rejectBill) > 0;
        if (flag) {
            //更新销售退货入库单 验收状态 客户 退货类型相关信息
            flag = storageForSalesService.updateSaleRejectEnterCheckByReject(rejectBill);
        }
        return flag;
    }

    @Override
    public Map updateRejectBillCheck(RejectBill rejectBill) throws Exception {
        Map returnMap = new HashMap();
        boolean flag = false;
        Map map = new HashMap();
        map.put("id", rejectBill.getId());
        RejectBill oldRejectBill = getSalesRejectBillMapper().getRejectBill(map);
        if (null != oldRejectBill.getReceiptid() && !"".equals(oldRejectBill.getReceiptid())) {
            returnMap.put("flag", flag);
            returnMap.put("msg", "销售发货通知单已关联回单。不能修改");
            return returnMap;
        }
//        if(!oldRejectBill.getIsinvoice().equals("0") && !oldRejectBill.getIsinvoice().equals("3")){
//            returnMap.put("flag", flag);
//            returnMap.put("msg", "该销售退货通知单已申请核销");
//            return returnMap;
//        }
//        if(!oldRejectBill.getIsinvoicebill().equals("0") && !oldRejectBill.getIsinvoicebill().equals("3")){
//            returnMap.put("flag", flag);
//            returnMap.put("msg", "该销售退货通知单已申请开票");
//            return returnMap;
//        }
        List<RejectBillDetail> detailList = getSalesRejectBillMapper().getRejectBillDetailListByBill(rejectBill.getId());
        for(int i = 0 ;i<detailList.size();i++){
            BigDecimal unitnum =  detailList.get(i).getUnitnum();
            BigDecimal oldunitnum =  rejectBill.getBillDetailList().get(i).getUnitnum();
            if(!unitnum.equals(oldunitnum)){
                returnMap.put("flag", flag);
                returnMap.put("msg", "该销售退货通知单已拆分");
                return returnMap;
            }
        }
        //根据退货通知单 获取退货入库单列表
        List<SaleRejectEnter> salesEnterList = storageForSalesService.getSaleRejectEnterListByRejectid(rejectBill.getId());
        boolean checkFlag = true;
        for (SaleRejectEnter saleRejectEnter : salesEnterList) {
            if(StringUtils.isNotEmpty(rejectBill.getRemark())){
                saleRejectEnter.setRemark(rejectBill.getRemark());
            }
            if(!oldRejectBill.getCustomerid().equals(rejectBill.getCustomerid())){
                Customer customer = getCustomerByID(rejectBill.getCustomerid());
                if (null != customer) {
                    saleRejectEnter.setSalesarea(customer.getSalesarea());
                    saleRejectEnter.setPcustomerid(customer.getPid());
                    saleRejectEnter.setCustomersort(customer.getCustomersort());
                    saleRejectEnter.setIndooruserid(customer.getIndoorstaff());
                }
            }
            storageForSalesService.updateSaleRejectEnter(saleRejectEnter);
            if (!"3".equals(saleRejectEnter.getStatus()) && !"4".equals(saleRejectEnter.getStatus())) {
                checkFlag = false;
                break;
            }
        }
        if (!checkFlag) {
            returnMap.put("flag", flag);
            returnMap.put("msg", "销售退货入库单已反审，或者未审核通过。不能验收。");
            return returnMap;
        }
        //销售区域
        Customer customer = getCustomerByID(rejectBill.getCustomerid());
        if (null != customer) {
            rejectBill.setSalesarea(customer.getSalesarea());
            rejectBill.setPcustomerid(customer.getPid());
            rejectBill.setCustomersort(customer.getCustomersort());
            rejectBill.setIndooruserid(customer.getIndoorstaff());
        }
        List<RejectBillDetail> billDetailList = rejectBill.getBillDetailList();
        String brandid = null;
        boolean brandMu = false;

        Date businessdate = CommonUtils.stringToDate(rejectBill.getBusinessdate());
        if (billDetailList.size() > 0) {
            for (RejectBillDetail billDetail : billDetailList) {
                GoodsInfo goodsInfo = getAllGoodsInfoByID(billDetail.getGoodsid());
                if (null != goodsInfo) {
                    billDetail.setGoodssort(goodsInfo.getDefaultsort());
                    billDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), rejectBill.getCustomerid()));
                    //厂家业务员
                    billDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), rejectBill.getCustomerid()));
                    //品牌部门
                    Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                    if (null != brand) {
                        billDetail.setBranddept(brand.getDeptid());
                    }
                    billDetail.setSupplierid(goodsInfo.getDefaultsupplier());

                    if(null==brandid){
                        brandid = goodsInfo.getBrand();
                    }else if(!brandid.equals(goodsInfo.getBrand())){
                        brandMu = true;
                    }
                    //应收日期
                    String duefromdate = getReceiptDateBySettlement(businessdate,rejectBill.getCustomerid(),goodsInfo.getBrand());
                    billDetail.setDuefromdate(duefromdate);
                }

                BigDecimal taxamount=billDetail.getUnitnum().multiply(billDetail.getTaxprice());
                billDetail.setTaxamount(taxamount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
//                billDetail.setNotaxprice(billDetail.getNotaxprice());
                BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, billDetail.getTaxtype());
                billDetail.setNotaxamount(notaxamount);
                if (null != notaxamount && notaxamount.compareTo(BigDecimal.ZERO) == 1) {
                    BigDecimal notaxprice = notaxamount.divide(billDetail.getUnitnum(), 6, BigDecimal.ROUND_HALF_UP);
                    billDetail.setNotaxprice(notaxprice);
                }
                billDetail.setTax(billDetail.getTaxamount().subtract(billDetail.getNotaxamount()) );
                billDetail.setInamountnotax(billDetail.getNotaxamount());
                billDetail.setInamountnotax(billDetail.getTaxamount());
                //辅数量
                getSalesRejectBillMapper().updateRejectBillDetailBack(billDetail);
                //更新销售退货入库单明细
                storageForSalesService.updateSaleRejectEnterDetailByRejectDetail(billDetail);
            }
        }
        if(brandMu){
            brandid = null;
        }
        //应收日期
        String duefromdate = getReceiptDateBySettlement(businessdate,rejectBill.getCustomerid(),brandid);
        if ("2".equals(rejectBill.getBilltype())) {
            rejectBill.setIsinvoice("3");
			rejectBill.setIsinvoicebill("3");
            rejectBill.setCheckdate(getCurrentDate());
            SysUser sysUser = getSysUser();
            rejectBill.setStopuserid(sysUser.getUserid());
            rejectBill.setStopusername(sysUser.getName());
            rejectBill.setDuefromdate(duefromdate);
        }
        flag = getSalesRejectBillMapper().updateRejectBill(rejectBill) > 0;
        if (flag) {
            //更新销售退货入库单 验收状态 客户 退货类型相关信息
            storageForSalesService.updateSaleRejectEnterCheckByReject(rejectBill);
        }
        returnMap.put("flag", flag);
        return returnMap;
    }

    @Override
    public Map updateRejectBillCheckCancel(String id) throws Exception {
        boolean flag = false;
        Map returnMap = new HashMap();
        Map map = new HashMap();
        map.put("id", id);
        RejectBill rejectBill = getSalesRejectBillMapper().getRejectBill(map);
        if (null != rejectBill.getReceiptid() && !"".equals(rejectBill.getReceiptid())) {
            returnMap.put("flag", flag);
            returnMap.put("msg", "销售发货通知单已关联回单。不能修改");
            return returnMap;
        }
        if(!rejectBill.getIsinvoice().equals("0") && !rejectBill.getIsinvoice().equals("3")){
            returnMap.put("flag", flag);
            returnMap.put("msg", "该销售退货通知单已申请核销");
            return returnMap;
        }
        if(!rejectBill.getIsinvoicebill().equals("0") && !rejectBill.getIsinvoicebill().equals("3")){
            returnMap.put("flag", flag);
            returnMap.put("msg", "该销售退货通知单已申请开票");
            return returnMap;
        }
        boolean isToday = true;
        //判断验收日期是否今天
//        List<SaleRejectEnter> list = storageForSalesService.getSaleRejectEnterListByRejectid(id);
//        for (SaleRejectEnter saleRejectEnter : list) {
//            if (!saleRejectEnter.getBusinessdate().equals(CommonUtils.getTodayDataStr())) {
//                isToday = false;
//                break;
//            }
//        }
		if(null!=rejectBill && isToday){
			RejectBill rejectBillUpdate = new RejectBill();
			rejectBillUpdate.setId(id);
			if("3".equals(rejectBill.getIsinvoice())){
				rejectBillUpdate.setIsinvoice("0");
			}
			if("3".equals(rejectBill.getIsinvoicebill())){
				rejectBillUpdate.setIsinvoicebill("0");
			}
            rejectBillUpdate.setCheckdate("");
            rejectBillUpdate.setStopuserid("");
            rejectBillUpdate.setStopusername("");
			flag = getSalesRejectBillMapper().updateRejectBill(rejectBillUpdate)>0;
			if(flag){
				flag = storageForSalesService.updateSaleRejectEnterCheckCancelByReject(id);
			}
		}
        returnMap.put("flag",flag);
		return returnMap;
	}
	@Override
	public Map auditCheckRejectBill(String ids) throws Exception {
        String uncheckids = "",succheckids = "",errorids ="",msg = "";
		boolean flag = false;
        if(StringUtils.isNotEmpty(ids)){
            String[] idArr = ids.split(",");
            for(String id : idArr){
                RejectBill rejectBill = getRejectBill(id);
                if(null != rejectBill){
                    //根据退货通知单 获取退货入库单列表
                    List<SaleRejectEnter> salesEnterList = storageForSalesService.getSaleRejectEnterListByRejectid(rejectBill.getId());
                    boolean checkFlag = true;
                    for (SaleRejectEnter saleRejectEnter : salesEnterList) {
                        if (!"3".equals(saleRejectEnter.getStatus()) && !"4".equals(saleRejectEnter.getStatus())) {
                            checkFlag = false;
                            break;
                        }
                    }
                    if (!checkFlag) {
                        if(StringUtils.isEmpty(uncheckids)){
                            uncheckids = id;
                        }else{
                            uncheckids += "," + id;
                        }
                    }else{
                        if("3".equals(rejectBill.getStatus()) && "0".equals(rejectBill.getIsinvoice())){
                            RejectBill rejectBillCheck = new RejectBill();
                            rejectBillCheck.setId(id);
                            if("2".equals(rejectBill.getBilltype())){
                                rejectBillCheck.setIsinvoice("3");
                                rejectBillCheck.setIsinvoicebill("3");
                                rejectBillCheck.setCheckdate(getCurrentDate());
                                SysUser sysUser = getSysUser();
                                rejectBillCheck.setStopuserid(sysUser.getUserid());
                                rejectBillCheck.setStopusername(sysUser.getUsername());
                                rejectBill.setCheckdate(getCurrentDate());
                            }
                            flag = getSalesRejectBillMapper().updateRejectBill(rejectBillCheck)>0;
                            if(flag){
                                if(StringUtils.isEmpty(succheckids)){
                                    succheckids = id;
                                }else{
                                    succheckids += "," + id;
                                }
                                //更新销售退货入库单 验收状态 客户 退货类型相关信息
                                storageForSalesService.updateSaleRejectEnterCheckByReject(rejectBill);
                            }else{
                                if(StringUtils.isEmpty(errorids)){
                                    errorids = id;
                                }else{
                                    errorids += "," + id;
                                }
                            }
                        }
                    }
                }
            }
        }
        if(StringUtils.isNotEmpty(uncheckids)){
            msg = "退货通知单编号："+uncheckids+"，销售退货入库单已反审，或者未审核通过。不能验收；";
        }
        if(StringUtils.isNotEmpty(succheckids)){
            if(StringUtils.isEmpty(msg)){
                msg = "退货通知单编号："+succheckids+"验收成功；";
            }else{
                msg += "<br>" + "退货通知单编号："+succheckids+"验收成功；";
            }
        }
        if(StringUtils.isNotEmpty(errorids)){
            if(StringUtils.isEmpty(msg)){
                msg = "退货通知单编号："+errorids+"验收失败；";
            }else{
                msg += "<br>" + "退货通知单编号："+errorids+"验收失败；";
            }
        }
        Map map2 = new HashMap();
        map2.put("msg",msg);
		return map2;
	}
    @Override
    public boolean auditRejectBillPhone(String id) throws Exception {
        Map map = new HashMap();
        map.put("id", id);
        boolean flag = false;
        RejectBill rejectBill = getSalesRejectBillMapper().getRejectBill(map);
        if ("1".equals(rejectBill.getStatus())) {
            SysUser sysUser = getSysUser();
            map.put("audituserid",sysUser.getUserid());
            map.put("auditusername",sysUser.getUsername());
            map.put("audittime",new Date());
            int i = getSalesRejectBillMapper().auditRejectBillPhone(map);
            flag = i > 0;
        }
        return flag;
    }

    @Override
    public boolean auditRejectBillCar(String id) throws Exception {
        boolean flag = false;
        Map map = auditRejectBill("1", id,"");
        if (null != map && map.containsKey("flag")) {
            flag = (Boolean) map.get("flag");
            if (flag) {
                //验收退货通知单
                RejectBill rejectBill = getRejectBill(id);
                updateRejectBillCheck(rejectBill);
            }
        }
        return flag;
    }

    @Override
    public boolean isHaveRejectBillByKeyid(String keyid) throws Exception {
        int count = getSalesRejectBillMapper().getRejectBillByKeyid(keyid);
        if (count>0) {
            return true;
        }
        return false;
    }

    @Override
    public List<Map<String, Object>> getRejectBillByExport(PageMap pageMap) throws Exception {
    	String dataSql = getAccessColumnList("t_sales_rejectbill", "t");
		pageMap.setDataSql(dataSql);
        List<Map<String, Object>> rejectBill = getSalesRejectBillMapper().getRejectBillListExport(pageMap);
        for(Map<String, Object> map :rejectBill){
            //客户
            String customerid = (String)map.get("customerid");
            Customer customerInfo = getBaseCustomerMapper().getCustomerInfo(customerid);
            if(null != customerInfo) {
                map.put("customername", customerInfo.getName());
            }

            //司机
            String driverid = (String) map.get("driverid");
            Personnel personnelInfo = getPersonnelById(driverid);
            if( null != personnelInfo){
                map.put("driver",personnelInfo.getName());
            }


            //销售部门
            String saledeptid =  (String) map.get("salesdept");
            DepartMent departMentInfo = getDepartMentById(saledeptid);
            if( null != departMentInfo){
                map.put("salesdeptname",departMentInfo.getName());
            }

            //客户业务员
            String salesuserid = (String) map.get("salesuser");
            Personnel salesuserInfo = getPersonnelById(salesuserid);
            if( null != salesuserInfo){
                map.put("salesusername",salesuserInfo.getName());
            }


            //入库仓库
            String storageid = (String) map.get("storageid");
            StorageInfo storageInfo = getStorageInfoByID(storageid);
            if(null != storageInfo) {
                map.put("storagename", storageInfo.getName());
            }

            //商品
            String goodsid = (String) map.get("goodsid");
            GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
            if(null != goodsInfo) {
                map.put("goodsname", goodsInfo.getName());
                map.put("barcode",goodsInfo.getBarcode());
                map.put("boxnum",goodsInfo.getBoxnum());
                BigDecimal taxprice = (BigDecimal)map.get("taxprice");
                map.put("boxprice",goodsInfo.getBoxnum().multiply(taxprice));
            }
            map.put("itemno",getItemnoByGoodsAndStorage(goodsid,storageid));

            //退货类型
            String billtype = (String) map.get("billtype");
            if(billtype.equals("1")){
                map.put("billtypename","直退");
            }else if(billtype.equals("2")){
                map.put("billtypename","售后退货");
            }else{
                map.put("billtypename","");
            }

            String state = (String) map.get("status");
            if(state.equals("2")){
                map.put("state","保存");
            }else if(state.equals("3")){
                map.put("state","审核通过");
            }else {
                map.put("state","关闭");
            }

        }

        return rejectBill;
    }

    @Override
    public List<Map> getRejectBillHistoryGoodsPriceList(Map map) throws Exception {
        String goodsid = (String)map.get("goodsid");
        GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
        String salesGoodsHistorySource = getSysParamValue("SalesGoodsHistorySource");
        List<Map> list = null;
        if("2".equals(salesGoodsHistorySource)) {
            list = getSalesReceiptMapper().getReceiptBillHistoryGoodsPriceList(map);
        } else {
            list = getBaseSaleoutMapper().getRejectBillHistoryGoodsPriceList(map);
        }
        for(Map map1 : list){
            if(null != goodsInfo && null != goodsInfo.getBoxnum()){
                BigDecimal taxprice = (BigDecimal)map1.get("taxprice");
                BigDecimal boxprice = taxprice.multiply(goodsInfo.getBoxnum());
                map1.put("boxprice",boxprice);
            }
        }
        return list;
    }
    public RejectBill getPureRejectBill(String id) throws Exception{
        RejectBill rejectBill = getSalesRejectBillMapper().getRejectBillById(id);
        return rejectBill;
    }

    @Override
    public Map importRejectBill(List<Map<String, Object>> list) throws Exception {
        String emptyindexs = "",sucbillid = "",msg = "",nulldata = "";
        int index = 2;
        Map billMap = new HashMap();
        for(Map<String, Object> map : list){
            String customerid = (null != map.get("customerid")) ? (String)map.get("customerid") : "";
            Customer customer = getCustomerByID(customerid);
            if(null == customer){
                nulldata += customerid;
                continue;
            }else{
                if(customerid.equals(customer.getId())){
                    customerid = customer.getId();
                }else{
                    nulldata += customerid+",";
                    continue;
                }

            }
            String billtypename = (null != map.get("billtypename")) ? (String)map.get("billtypename") : "";
            String goodsid = (null != map.get("goodsid")) ? (String)map.get("goodsid") : "";
            //根据已知商品编码or助记符or条形码获取商品档案信息
            GoodsInfo goodsInfoQ = getAllGoodsInfoByID(goodsid);

            //判断供应商编码、客户编号、仓库编号是否全都已输入
            if(StringUtils.isNotEmpty(customerid) && StringUtils.isNotEmpty(billtypename) && StringUtils.isNotEmpty(goodsid) && null != goodsInfoQ && "1".equals(goodsInfoQ.getState())){
                //入库仓库
                String storagename = (null != map.get("storagename")) ? (String)map.get("storagename") : "null";
                //司机
                String drivername = (null != map.get("driver")) ? (String)map.get("driver") : "null";
                //将客户、退货类型、仓库、司机相同的为同一张单据进行封装
                String key = customerid+"_"+billtypename+"_"+storagename+"_"+drivername;
                if (billMap.containsKey(key)) {
                    List detailList = (List) billMap.get(key);
                    detailList.add(map);
                    billMap.put(key, detailList);
                } else {
                    List detailList = new ArrayList();
                    detailList.add(map);
                    billMap.put(key, detailList);
                }
            }else{
                if(StringUtils.isEmpty(emptyindexs)){
                    emptyindexs = String.valueOf(index);
                }else{
                    emptyindexs += "," + String.valueOf(index);
                }
            }
            index++;
        }
        Set set = billMap.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            String key = entry.getKey();
            List<Map> detailList = (List<Map>) billMap.get(key);
            Map changemap = changeRejectbillByKey(key, detailList);
            boolean nodataflag = changemap.get("nodataflag").equals(true);
            if(nodataflag){
                RejectBill rejectBill = (RejectBill) changemap.get("rejectBill");
                boolean addflag = false;
                if (null != rejectBill) {
                    if (isAutoCreate("t_sales_rejectbill")) {
                        // 获取自动编号
                        String id = getAutoCreateSysNumbderForeign(rejectBill, "t_sales_rejectbill");
                        rejectBill.setId(id);
                    } else {
                        rejectBill.setId("THTZD-" + CommonUtils.getDataNumberSendsWithRand());
                    }
                    addflag  = super.addRejectBill(rejectBill);
                    if (addflag) {
                        if (StringUtils.isEmpty(sucbillid)) {
                            sucbillid = rejectBill.getId();
                        } else {
                            sucbillid += "," + rejectBill.getId();
                        }
                    }
                }
            }else{
                if(StringUtils.isEmpty(nulldata)){
                    nulldata = key.split("_")[0];
                }else{
                    nulldata += "," + key.split("_")[0];
                }
            }
        }
        if(StringUtils.isNotEmpty(emptyindexs)){
            msg = "第"+emptyindexs+"行中的数据，客户编号、退货类型、商品编码需必填或商品档案中要存在填写的启用商品，否则不允许导入数据。";
        }
        if(StringUtils.isNotEmpty(nulldata)){
            if(StringUtils.isNotEmpty(msg)){
                msg += "<br>" + "编号："+nulldata+"客户不存在,不允许导入数据。";
            }else{
                msg = "编号："+nulldata+"客户不存在,不允许导入数据。";
            }
        }
        if(StringUtils.isNotEmpty(sucbillid)){
            if(StringUtils.isNotEmpty(msg)){
                msg += "<br>" + "导入成功："+sucbillid.split(",").length+"条单据，编号："+sucbillid+"导入成功";
            }else{
                msg = "导入成功："+sucbillid.split(",").length+"条单据，编号："+sucbillid+"导入成功";
            }
        }
        Map map = new HashMap();
        map.put("msg",msg);
        map.put("sucbillid",sucbillid);
        return map;
    }

    /**
     * 根据客户、退货类型、仓库、司机和导入的明细数据 转换成退货通知单
     * @param key
     * @param list
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2017-01-05
     */
    private Map changeRejectbillByKey(String key,List<Map> list)throws Exception{
        //判断是否允许输入小数位
        int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
        Map returnMap = new HashMap();
        //判断供应商、仓库、客户是否全部存在，其中一个不存在不允许导入数据
        boolean nodataflag = true;
        String[] keyArr = key.split("_");
        String customerid = keyArr[0];
        String billtypename = keyArr[1];
        String storagename = keyArr[2];
        String drivername = keyArr[3];
        Customer customer = getCustomerByID(customerid);
        if(null == customer){
            nodataflag = false;
            returnMap.put("nodataflag",nodataflag);
            return returnMap;
        }
        //退货类型
        String billtype = "1";
        if("直退".equals(billtypename)){
            billtype = "1";
        }else if("售后退货".equals(billtypename)){
            billtype = "2";
        }
        String storageid = "";
        StorageInfo storageInfo = getStorageInfoByName(storagename);
        if(null != storageInfo){
            storageid = storageInfo.getId();
        }
        //司机编号
        String driverid = "";
        Personnel personnel = getPersonnelByName(drivername);
        if(null != personnel){
            driverid = personnel.getId();
        }
        RejectBill rejectBill = new RejectBill();
        rejectBill.setBilltype(billtype);
        rejectBill.setBusinessdate(CommonUtils.getTodayDataStr());
        rejectBill.setCustomerid(customerid);
        rejectBill.setStorageid(storageid);
        rejectBill.setCustomername(customer.getName());
        rejectBill.setStatus("2");
        rejectBill.setSalesdept(customer.getSalesdeptid());
        rejectBill.setSalesuser(customer.getSalesuserid());
        rejectBill.setSettletype(customer.getSettletype());
        rejectBill.setPaytype(customer.getPaytype());
        rejectBill.setDriverid(driverid);
        rejectBill.setRemark("excel导入");
        //生成订单明细信息
        List<RejectBillDetail> detailList = new ArrayList<RejectBillDetail>();
        //是否获取系统价格，为0时取Excel中的单价，但是如果Excel中为空，还是取系统价的
        String isSystemPrice = getSysParamValue("isSystemPrice");
        for(Map map : list){
            //获取商品详情
            String goodsid = (null != map.get("goodsid")) ? (String)map.get("goodsid") : "";
            GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
            if(null != goodsInfo){
                BigDecimal unitnum = (null != map.get("unitnum")) ? new BigDecimal((String) map.get("unitnum")) : BigDecimal.ZERO;// 数量
                String remark = (null != map.get("remark")) ? (String) map.get("remark") : "";
                BigDecimal taxprice = (null != map.get("taxprice")) ? new BigDecimal((String) map.get("taxprice")) : BigDecimal.ZERO;// 单价
                unitnum = unitnum.abs().setScale(decimalScale,BigDecimal.ROUND_HALF_UP);

                RejectBillDetail rejectDetail = new RejectBillDetail();
                rejectDetail.setGoodsid(goodsInfo.getId());
                rejectDetail.setGoodsInfo(goodsInfo);
                rejectDetail.setSupplierid(goodsInfo.getDefaultsupplier());
                rejectDetail.setUnitid(goodsInfo.getMainunit());
                rejectDetail.setUnitname(goodsInfo.getMainunitName());
                rejectDetail.setRemark(remark);

                rejectDetail.setUnitnum(unitnum);
                rejectDetail.setAuxunitid(goodsInfo.getAuxunitid());
                rejectDetail.setAuxunitname(goodsInfo.getAuxunitname());

                //根据系统业务获取商品的系统价格
                OrderDetail orderDetailprice = salesOrderService.getGoodsDetail(goodsInfo.getId(), rejectBill.getCustomerid(), rejectBill.getBusinessdate(), rejectDetail.getUnitnum(), "reject");
                if(null != orderDetailprice){
                    rejectDetail.setAuxnum(orderDetailprice.getAuxnum());
                    rejectDetail.setAuxnumdetail(orderDetailprice.getAuxnumdetail());
                    rejectDetail.setAuxremainder(orderDetailprice.getOvernum());
                    rejectDetail.setTotalbox(orderDetailprice.getTotalbox());
                }else{
                    Map auxmap = countGoodsInfoNumber(rejectDetail.getGoodsid(), rejectDetail.getAuxunitid(), rejectDetail.getUnitnum());
                    if(!auxmap.isEmpty()){
                        rejectDetail.setAuxnum(null != auxmap.get("auxInteger") ? new BigDecimal((String)auxmap.get("auxInteger")) : BigDecimal.ZERO);
                        rejectDetail.setAuxnumdetail(null != auxmap.get("auxnumdetail") ? (String)auxmap.get("auxnumdetail") : "");
                        rejectDetail.setAuxremainder(null != auxmap.get("auxremainder") ? new BigDecimal((String)auxmap.get("auxremainder")) : BigDecimal.ZERO);
                        rejectDetail.setTotalbox(null != auxmap.get("auxnum") ? new BigDecimal((String)auxmap.get("auxnum")) : BigDecimal.ZERO);
                    }
                }
                if("0".equals(isSystemPrice)){
                    //导入价格为空时取系统价格
                    if (taxprice.compareTo(BigDecimal.ZERO) == 0 && null != orderDetailprice) {
                        rejectDetail.setTaxprice(orderDetailprice.getTaxprice());
                        rejectDetail.setDefaultprice(orderDetailprice.getFixprice());
                        if(StringUtils.isNotEmpty(orderDetailprice.getRemark())){
                            String dremark = rejectDetail.getRemark();
                            if(StringUtils.isNotEmpty(dremark)){
                                dremark += " "+orderDetailprice.getRemark();
                            }else{
                                dremark = orderDetailprice.getRemark();
                            }
                            rejectDetail.setRemark(dremark);
                        }
                    }else{
                        rejectDetail.setTaxprice(taxprice);
                    }
                    rejectDetail.setTaxamount(rejectDetail.getUnitnum().multiply(rejectDetail.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                }else if ("1".equals(isSystemPrice) && null != orderDetailprice) {//获取系统价格
                    rejectDetail.setTaxprice(orderDetailprice.getTaxprice());
                    rejectDetail.setTaxamount(rejectDetail.getUnitnum().multiply(rejectDetail.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                    if(StringUtils.isNotEmpty(orderDetailprice.getRemark())){
                        String dremark = rejectDetail.getRemark();
                        if(StringUtils.isNotEmpty(dremark)){
                            dremark += " "+orderDetailprice.getRemark();
                        }else{
                            dremark = orderDetailprice.getRemark();
                        }
                        rejectDetail.setRemark(dremark);
                    }
                } else {
                    if (taxprice.compareTo(BigDecimal.ZERO) != 0) {
                        //含税单价存在 且不为0
                        //金额 = 单价*数量
                        rejectDetail.setTaxprice(taxprice);
                        rejectDetail.setTaxamount(rejectDetail.getTaxprice().multiply(rejectDetail.getUnitnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                    } else{
                        //单价不存在 或者为0
                        if(null != orderDetailprice){
                            rejectDetail.setTaxprice(orderDetailprice.getTaxprice());
                            rejectDetail.setTaxamount(rejectDetail.getTaxprice().multiply(rejectDetail.getUnitnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                            if(StringUtils.isNotEmpty(orderDetailprice.getRemark())){
                                String dremark = rejectDetail.getRemark();
                                if(StringUtils.isNotEmpty(dremark)){
                                    dremark += " "+orderDetailprice.getRemark();
                                }else{
                                    dremark = orderDetailprice.getRemark();
                                }
                                rejectDetail.setRemark(dremark);
                            }
                        }
                    }
                }
                rejectDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
                TaxType taxType = getTaxType(rejectDetail.getTaxtype());
                if (taxType != null) {
                    rejectDetail.setTaxtypename(taxType.getName());
                }
                BigDecimal notaxamount = getNotaxAmountByTaxAmount(rejectDetail.getUnitnum().multiply(rejectDetail.getTaxprice()), rejectDetail.getTaxtype());
                if (null != notaxamount && notaxamount.compareTo(BigDecimal.ZERO) != 0) {
                    BigDecimal notaxprice = notaxamount.divide(rejectDetail.getUnitnum(), 6, BigDecimal.ROUND_HALF_UP);
                    rejectDetail.setNotaxprice(notaxprice);
                }
                rejectDetail.setNotaxamount(notaxamount);
                rejectDetail.setTax(rejectDetail.getTaxamount().subtract(rejectDetail.getNotaxamount()) );
                detailList.add(rejectDetail);
            }
        }
        rejectBill.setBillDetailList(detailList);
        returnMap.put("nodataflag",nodataflag);
        returnMap.put("rejectBill",rejectBill);
        return returnMap;
    }

    @Override
    public List<RejectBillDetail> getRejectBillDetailListByMap(Map map) throws Exception{
        List<RejectBillDetail> billDetailList= getSalesRejectBillMapper().getRejectBillDetailListByMap(map);

        for (RejectBillDetail billDetail : billDetailList) {
            GoodsInfo goodsInfo = getGoodsInfoByID(billDetail.getGoodsid());
            billDetail.setGoodsInfo(goodsInfo);
            billDetail.setBuyprice(goodsInfo.getNewbuyprice());

            TaxType taxType = getTaxType(billDetail.getTaxtype());
            if (taxType != null) {
                billDetail.setTaxtypename(taxType.getName());
            }
            //箱价
            if (null != goodsInfo) {
                billDetail.setBoxprice(goodsInfo.getBoxnum().multiply(billDetail.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
            }
            StorageInfo storageInfo1 = getStorageInfoByID(billDetail.getStorageid());
            if (null != storageInfo1) {
                billDetail.setStoragename(storageInfo1.getName());
            }
        }
        return billDetailList;
    }


    @Override
    public RejectBill getRejectBillInfoById(String id) throws Exception {
        Map map = new HashMap();
        map.put("id", id);
        RejectBill rejectBill = getSalesRejectBillMapper().getRejectBill(map);
        if (null != rejectBill) {
            Customer customer = getCustomerByID(rejectBill.getCustomerid());
            if (null != customer) {
                rejectBill.setCustomername(customer.getName());
            }
            Contacter contacter = getContacterById(rejectBill.getHandlerid());
            if (null != contacter) {
                rejectBill.setHandlername(contacter.getName());
            }
            Personnel driver = getPersonnelById(rejectBill.getDriverid());
            if (null != driver) {
                rejectBill.setDrivername(driver.getName());
            }
            DepartMent departMent = getDepartmentByDeptid(rejectBill.getSalesdept());
            if (null != departMent) {
                rejectBill.setSalesdeptname(departMent.getName());
            }
            Personnel personnel = getPersonnelById(rejectBill.getSalesuser());
            if (null != personnel) {
                rejectBill.setSalesusername(personnel.getName());
            }
            Settlement settlement = getSettlementByID(rejectBill.getSettletype());
            if (null != settlement) {
                rejectBill.setSettletypename(settlement.getName());
            }
            Payment payment = getPaymentByID(rejectBill.getPaytype());
            if (null != payment) {
                rejectBill.setPaytypename(payment.getName());
            }
            StorageInfo storageInfo = getStorageInfoByID(rejectBill.getStorageid());
            if (null != storageInfo) {
                rejectBill.setStoragename(storageInfo.getName());
            }
        }
        return rejectBill;
    }
}

