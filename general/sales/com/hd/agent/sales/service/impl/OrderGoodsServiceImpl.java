package com.hd.agent.sales.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

import com.hd.agent.sales.model.*;
import com.hd.agent.sales.service.IOrderGoodsService;
import com.hd.agent.sales.service.IOrderService;
import com.hd.agent.storage.model.StorageSummary;
import com.hd.agent.storage.model.StorageSummaryBatch;
import com.hd.agent.storage.service.IStorageForSalesService;
import com.hd.agent.system.model.SysCode;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by luoq on 2017/10/16.
 */
public class OrderGoodsServiceImpl extends BaseSalesServiceImpl implements IOrderGoodsService {
    private IStorageForSalesService storageForSalesService;

    private IOrderService salesOrderService;

    public IStorageForSalesService getStorageForSalesService() {
        return storageForSalesService;
    }

    public void setStorageForSalesService(IStorageForSalesService storageForSalesService) {
        this.storageForSalesService = storageForSalesService;
    }

    public IOrderService getSalesOrderService() {
        return salesOrderService;
    }

    public void setSalesOrderService(IOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    /**
     * 销售订货单列表
     * @param pageMap
     * @return com.hd.agent.common.util.PageData
     * @throws
     * @author luoqiang
     * @date Oct 16, 2017
     */
    public PageData getOrderGoodsList(PageMap pageMap) throws Exception{
        List<OrderGoods> orderList=getOrderGoodsMapper().getOrderGoodsList(pageMap);

        for (OrderGoods order : orderList) {
            BigDecimal totalboxweight = BigDecimal.ZERO;
            BigDecimal totalboxvolume = BigDecimal.ZERO;
            Personnel indoorPerson = getPersonnelById(order.getIndooruserid());
            if (null != indoorPerson) {
                order.setIndoorusername(indoorPerson.getName());
            }
            DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(order.getSalesdept());
            if (departMent != null) {
                order.setSalesdept(departMent.getName());
            }
            Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(order.getSalesuser());
            if (personnel != null) {
                order.setSalesuser(personnel.getName());
            }
            Map map = new HashMap();
            map.put("id", order.getCustomerid());
            Customer customer = getCustomerByID(order.getCustomerid());
            if (customer != null) {
                order.setCustomerid(customer.getId());
                order.setCustomername(customer.getName());
                order.setAddress(customer.getAddress());
            }
            map.put("id", order.getHandlerid());
            Contacter contacter = getBaseFilesContacterMapper().getContacterDetail(map);
            if (contacter != null) {
                order.setHandlerid(contacter.getName());
            }
            String remarks = "";
            Map total = getOrderGoodsMapper().getOrderGoodsDetailTotal(order.getId());
            if (total != null) {
                if (total.containsKey("taxamount")) {
                    order.setField01(total.get("taxamount").toString());
                    order.setField04(total.get("taxamount").toString());
                }
                if (total.containsKey("notaxamount")) {
                    order.setField02(total.get("notaxamount").toString());
                }
                if (total.containsKey("tax")) {
                    order.setField03(total.get("tax").toString());
                }
                if (total.containsKey("totalboxweight")) {
                    order.setTotalboxweight((BigDecimal) total.get("totalboxweight"));
                }
                if (total.containsKey("totalboxvolume")) {
                    order.setTotalboxvolume((BigDecimal) total.get("totalboxvolume"));
                }
                if (total.containsKey("detailremark")) {
                    remarks = (String) total.get("detailremark");
                }
                if(total.containsKey("branduser")){
                    String brandusers = (String) total.get("branduser");
                    if(StringUtils.isNotEmpty(brandusers)){
                        String[] branduserArr = brandusers.split(",");
                        String brandusername = "";
                        for(String branduserid : branduserArr){
                            Personnel branduserPersonnel = getPersonnelById(branduserid);
                            if(null!=branduserPersonnel){
                                if("".equals(brandusername)){
                                    brandusername = branduserPersonnel.getName();
                                }else{
                                    brandusername += ","+branduserPersonnel.getName();
                                }
                            }
                        }
                        order.setBrandusername(brandusername);
                    }
                }
            }
            if(StringUtils.isNotEmpty(order.getRemark())){
                order.setRemark(order.getRemark()+" "+remarks);
            }else{
                order.setRemark(remarks);
            }

            SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(order.getStatus(), "status");
            if (sysCode != null) {
                order.setStatusname(sysCode.getCodename());
            }
        }
        int count=getOrderGoodsMapper().getOrderGoodsCount(pageMap);
        PageData pageData=new PageData(count,orderList,pageMap);
        return pageData;
    }

    /**
     * 添加销售订货单
     * @param orderGoods
     * @param saveaudit
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Oct 16, 2017
     */
    @Override
    public Map addOrderGoods(OrderGoods orderGoods,String saveaudit) throws Exception{
        if (isAutoCreate("t_sales_goodsorder")) {
            // 获取自动编号
            String id = getAutoCreateSysNumbderForeign(orderGoods, "t_sales_goodsorder");
            orderGoods.setId(id);
        }else{
            String id = "XSDHD-"+ CommonUtils.getDataNumberSendsWithRand();
            orderGoods.setId(id);
        }
        List<OrderGoodsDetail> orderDetailList = orderGoods.getOrderDetailList();

        if (orderDetailList.size() > 0) {
            int seq = 1;
            List detailList = new ArrayList();
            List<OrderGoodsDetail> brandDiscountList = new ArrayList();
            for (OrderGoodsDetail orderDetail : orderDetailList) {
                if (orderDetail != null) {
                    orderDetail.setNotordertaxamount(orderDetail.getTaxamount());
                    orderDetail.setNotordernotaxamount(orderDetail.getNotaxamount());
                    orderDetail.setNotorderunitnum(orderDetail.getUnitnum());
                    orderDetail.setOrderid(orderGoods.getId());
                    if(StringUtils.isEmpty(orderDetail.getStorageid()) && StringUtils.isNotEmpty(orderGoods.getStorageid())){
                        orderDetail.setStorageid(orderGoods.getStorageid());
                    }
                    if(StringUtils.isNotEmpty(orderDetail.getSummarybatchid())){
                        StorageSummaryBatch summaryBatch = storageForSalesService.getStorageSummaryBatchByID(orderDetail.getSummarybatchid());
                        if(null!=summaryBatch){
                            orderDetail.setStorageid(summaryBatch.getStorageid());
                        }
                    }
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(orderDetail.getGoodsid());
                    if(null != goodsInfo){
                        orderDetail.setGoodssort(goodsInfo.getDefaultsort());
                        orderDetail.setBrandid(goodsInfo.getBrand());
                        orderDetail.setCostprice(getGoodsCostprice(orderGoods.getStorageid(),goodsInfo));
                        orderDetail.setFixnum(orderDetail.getUnitnum());

                        String isOrderGoodsUseTax=getSysParamValue("isOrderGoodsUseTax");
                        if("1".equals(isOrderGoodsUseTax)){
                            orderDetail.setOldtaxprice(orderDetail.getTaxprice());
                        }

                        //获取税种，税额，未税金额，未税单价
//                        orderDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
                        if(null==orderDetail.getTaxamount()){
                            orderDetail.setTaxamount(BigDecimal.ZERO);
                        }
//                        BigDecimal notaxanount = getNotaxAmountByTaxAmount(orderDetail.getTaxamount(),goodsInfo.getDefaulttaxtype());
//                        orderDetail.setNotaxamount(notaxanount);
//                        orderDetail.setTax(orderDetail.getTaxamount().subtract(notaxanount));
//                        if(null!=notaxanount && null!=orderDetail.getUnitnum() && orderDetail.getUnitnum().compareTo(BigDecimal.ZERO)==1){
//                            orderDetail.setNotaxprice(notaxanount.divide(orderDetail.getUnitnum(),6,BigDecimal.ROUND_HALF_UP));
//                        }
                        orderDetail.setTaxamount(orderDetail.getTaxamount());
                        orderDetail.setNotaxamount(orderDetail.getNotaxamount());
                        orderDetail.setTax(orderDetail.getTaxamount().subtract(orderDetail.getNotaxamount()));
                        //计算辅单位数量
                        Map map = countGoodsInfoNumber(orderDetail.getGoodsid(), orderDetail.getAuxunitid(), orderDetail.getUnitnum());
                        if (map.containsKey("auxnum")) {
                            orderDetail.setTotalbox((BigDecimal) map.get("auxnum"));
                        }
                        //获取品牌部门
                        Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                        if (null != brand){
                            orderDetail.setBranddept(brand.getDeptid());
                        }
                        //根据客户编号和品牌编号 获取品牌业务员
                        orderDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), orderGoods.getCustomerid()));
                        //厂家业务员
                        orderDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), orderGoods.getCustomerid()));
                        orderDetail.setSupplierid(goodsInfo.getDefaultsupplier());

                        //获取系统设置的原价，不包括特价
                        orderDetail.setFixprice(getGoodsPriceByCustomer(orderDetail.getGoodsid(), orderGoods.getCustomerid()));
                    }
                    if(orderDetail.getSeq()==null||orderDetail.getSeq()==0){
                        orderDetail.setSeq(seq);
                    }
                    seq++;
                    //品牌折扣不直接插入 折算到各商品上后再插入
                    if (!"2".equals(orderDetail.getIsdiscount())) {
                        //数量为0的明细不添加
                        if (null != orderDetail.getUnitnum() && orderDetail.getUnitnum().compareTo(BigDecimal.ZERO) != 0) {
                            detailList.add(orderDetail);
                        } else if ("1".equals(orderDetail.getIsdiscount())) {
                            detailList.add(orderDetail);
                        }
                        //默认商品为不是折扣
                        if(StringUtils.isEmpty(orderDetail.getIsdiscount())){
                            orderDetail.setIsdiscount("0");
                        }
                    } else {
                        brandDiscountList.add(orderDetail);
                    }
                }
            }
            if (detailList.size() > 0) {
                //添加明细
                getOrderGoodsMapper().addOrderGoodsDetailList(detailList);
            }
            //品牌折扣 平摊到各商品中
            for (OrderGoodsDetail billDetail : brandDiscountList) {
                List<OrderGoodsDetail> brandGoodsList = getSalesOrderDetailMapper().getOrderDetailListByOrderidAndBrandid(orderGoods.getId(), billDetail.getBrandid());
                BigDecimal totalamount = BigDecimal.ZERO;
                for (OrderGoodsDetail billGoodsDetail : brandGoodsList) {
                    totalamount = totalamount.add(billGoodsDetail.getTaxamount());
                }
                if (null != billDetail.getTaxamount() && billDetail.getTaxamount().compareTo(BigDecimal.ZERO) != 0) {
                    BigDecimal useamount = BigDecimal.ZERO;
                    for (int i = 0; i < brandGoodsList.size(); i++) {
                        OrderGoodsDetail billGoodsDetail = brandGoodsList.get(i);
                        OrderGoodsDetail detail = new OrderGoodsDetail();
                        detail.setStorageid(billDetail.getStorageid());
                        detail.setGoodsid(billGoodsDetail.getGoodsid());
                        //商品分类
                        GoodsInfo goodsInfo = getAllGoodsInfoByID(billGoodsDetail.getGoodsid());
                        if (null != goodsInfo) {
                            detail.setGoodssort(goodsInfo.getDefaultsort());
                        }
                        detail.setBrandid(billGoodsDetail.getBrandid());
                        detail.setBranddept(billGoodsDetail.getBranddept());
                        detail.setBranduser(billGoodsDetail.getBranduser());
                        detail.setSupplieruser(billGoodsDetail.getSupplieruser());
                        //获取供应商
                        Brand brand = getGoodsBrandByID(billGoodsDetail.getBrandid());
                        if (null != brand) {
                            detail.setSupplierid(brand.getSupplierid());
                            detail.setTaxtype(brand.getDefaulttaxtype());
                        }
                        detail.setOrderid(billGoodsDetail.getOrderid());
                        detail.setIsdiscount("1");
                        detail.setIsbranddiscount("1");
                        seq++;
                        detail.setSeq(seq);
                        detail.setRemark(billDetail.getRemark());
                        //计算平摊到商品中的 各折扣金额
                        BigDecimal discountamount = billGoodsDetail.getTaxamount().divide(totalamount, decimalLen, BigDecimal.ROUND_HALF_UP).multiply(billDetail.getTaxamount());
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
                        detail.setTax(discountamount.subtract(notaxdiscountamount));

                        getOrderGoodsMapper().addOrderGoodsDetail(detail);
                    }
                }
            }
        }
        //根据客户编号获取该客户的销售内勤用户信息
        SysUser sysUser = getSysUser();
        if (sysUser != null && null == orderGoods.getAdduserid()) {
            orderGoods.setAdddeptid(sysUser.getDepartmentid());
            orderGoods.setAdddeptname(sysUser.getDepartmentname());
            orderGoods.setAdduserid(sysUser.getUserid());
            orderGoods.setAddusername(sysUser.getName());
        }
        //销售内勤
        SysUser indoorsysUser = getSalesIndoorSysUserByCustomerid(orderGoods.getCustomerid());
        orderGoods.setIndooruserid(indoorsysUser.getPersonnelid());
        //获取销售区域上级客户
        Customer customer = getCustomerByID(orderGoods.getCustomerid());
        if (null != customer) {
            orderGoods.setSalesarea(customer.getSalesarea());
            orderGoods.setPcustomerid(customer.getPid());
            orderGoods.setCustomersort(customer.getCustomersort());
        }
        boolean flag = getOrderGoodsMapper().addOrderGoods(orderGoods) > 0;
//        if(flag){
//            addCutomerFullFreeLog(orderGoods);
//        }
        Map map = new HashMap();
        if ("saveaudit".equals(saveaudit) && flag) {
            orderGoods.setStatus("3");
            map = auditOrderGoods("1",orderGoods.getId(),null);
            boolean auditflag = (Boolean) map.get("flag");
            map.put("auditflag", auditflag);
        }
        map.put("flag", flag);
        map.put("id",orderGoods.getId());
        return map;
    }

    @Override
    public Map auditOrderGoods(String type, String id, String model) throws Exception {
        Map result = new HashMap();
        SysUser sysUser = getSysUser();
        OrderGoods order = getOrderGoodsMapper().getOrderGoods(id);
        String orderStr = "goodsid";
//        if (null != order && "1".equals(order.getIsgoodsseq())) {
//            orderStr = "goodsid";
//        }
        List<OrderGoodsDetail> orderDetaiList = getOrderGoodsMapper().getOrderGoodsDetailList(id, orderStr);
        order.setOrderDetailList(orderDetaiList);
        String msg = "";
        //系统参数 控制销售订单 仓库是否必填
        String isOrderStorageSelect = getSysParamValue("IsOrderStorageSelect");
        if("1".equals(isOrderStorageSelect) && StringUtils.isEmpty(order.getStorageid())){
            result.put("flag", false);
            result.put("msg", "订单未选择发货仓库");
            return result;
        }
        //判断销售订单审核时， 是否自动生成发货单
        //1 先生成销售发货通知单 0 自动审核销售发货通知单并且生成发货单
        String sysparam = getSysParamValue("IsDispatchProcessUse");
        if ("1".equals(type) && null != order) { //审核
            boolean auditflag = true;
            //判断销售订单是否可以审核
            if (auditflag || "supper".equals(model)) {
                if ("2".equals(order.getStatus())) { //只有状态为2（保存状态）才可进行审核
                    OrderGoods order2 = new OrderGoods();
                    order2.setId(id);
                    if (orderDetaiList.size() == 0) {
                        order2.setStatus("4");
                    } else {
                        order2.setStatus("3");
                    }
                    String auditBusinessdate = getAuditBusinessdate(order.getBusinessdate());
                    order2.setBusinessdate(auditBusinessdate);
                    order.setBusinessdate(auditBusinessdate);
                    order2.setAudituserid(sysUser.getUserid());
                    order2.setAuditusername(sysUser.getName());
                    order2.setAudittime(new Date());
                    if ("supper".equals(model)) {
                        if (StringUtils.isNotEmpty(order.getRemark())) {
                            order2.setRemark(order.getRemark() + ",此单为超级审核");
                        } else {
                            order2.setRemark("此单为超级审核");
                        }
                    }
                    Boolean flag=getOrderGoodsMapper().auditOrderGoods(order2)>0;
                    result.put("flag", flag);
                    result.put("billId", id);
                }
            } else {
                result.put("flag", false);
                result.put("msg", msg);
            }
        } else if ("2".equals(type)) { //反审
            if (("3".equals(order.getStatus()) || ("4".equals(order.getStatus()) && "0".equals(sysparam))) /**&& !isbigsaleout**/) {
                //订货单是否生成过订单
               String orderids=getOrderGoodsMapper().getOrderGoodsInOrder(id);
                if (StringUtils.isEmpty(orderids)) {
                    //订货单是否关联过要货单
                    String demandids=getSalesDemandMapper().getOrderGoodsInDemand(id);
                    if(StringUtils.isNotEmpty(demandids)){
                        result.put("demandflag", true);
                    }else{
                        OrderGoods order2 = new OrderGoods();
                        order2.setId(id);
                        order2.setStatus("2");
                        boolean flag = getOrderGoodsMapper().auditOrderGoods(order2)>0;
                        result.put("flag", flag);
                    }

                }else{
                    result.put("orderflag", true);
                }

                result.put("bigflag", true);
            }else{
                result.put("bigflag", false);
            }
        }
        return result;
    }

    /**
     * 根据编号获取销售订货单
     * @param id
     * @return com.hd.agent.sales.model.OrderGoods
     * @throws
     * @author luoqiang
     * @date Oct 16, 2017
     */
    @Override
    public OrderGoods getOrderGoods(String id) throws Exception{
        Map map = getAccessColumnMap("t_sales_goodsorder", null);
        map.put("id", id);
        OrderGoods order = getOrderGoodsMapper().getOrderGoods(id);
        if(null != order){
            Map amountmap=getOrderGoodsMapper().getOrderGoodsAmount(order.getId());
            BigDecimal ordertaxamount=(BigDecimal)amountmap.get("ordertaxamount");
            BigDecimal ordernotaxamount=(BigDecimal)amountmap.get("ordernotaxamount");
            BigDecimal notordertaxamount=(BigDecimal)amountmap.get("notordertaxamount");
            BigDecimal notordernotaxamount=(BigDecimal)amountmap.get("notordernotaxamount");
            order.setOrdertaxamount(ordertaxamount);
            order.setOrdernotaxamount(ordernotaxamount);
            order.setNotordertaxamount(notordertaxamount);
            order.setNotordernotaxamount(notordernotaxamount);
            Customer customer = getCustomerByID(order.getCustomerid());
            if (customer != null) {
                order.setCustomername(customer.getName());
            }
            String orderStr = "goodsid";
//            if (null != order && "1".equals(order.getIsgoodsseq())) {
//                orderStr = "goodsid";
//            }
//            List lackList = getSalesOrderDetailMapper().getOrderDetailLackList(id);
//            if (null != lackList) {
//                order.setLackList(lackList);
//            }
            List<OrderGoodsDetail> orderDetaiList = getOrderGoodsMapper().getOrderGoodsDetailList(id, orderStr);
            //商品库存缓存 防止同一张单多条商品 可用量问题
            Map storageGoodsMap = new HashMap();
            for (OrderGoodsDetail orderDetail : orderDetaiList) {
//                CustomerPrice customerPrice = getCustomerPrice(order.getCustomerid(),orderDetail.getGoodsid());
//                if(null!=customerPrice){
//                    orderDetail.setShopid(customerPrice.getShopid());
//                }
                GoodsInfo Info = getGoodsInfoByID(orderDetail.getGoodsid());
                if(null != Info){
                    //商品客户价格套价格 ---》 参考价
                    BigDecimal referenceprice = getGoodsPriceSetByCustomer(orderDetail.getGoodsid(),order.getCustomerid());
                    orderDetail.setReferenceprice(referenceprice);
                    orderDetail.setBasesaleprice(Info.getBasesaleprice());
                    orderDetail.setBuyprice(Info.getHighestbuyprice());
                    if(null != Info.getGrossweight()){
                        BigDecimal goodsBoxweight = orderDetail.getUnitnum().multiply(Info.getGrossweight()).setScale(6, BigDecimal.ROUND_HALF_UP);
                        orderDetail.setTotalboxweight(goodsBoxweight);
                    }
                    if(null != Info.getSinglevolume()){
                        BigDecimal goodsboxvolume = orderDetail.getUnitnum().multiply(Info.getSinglevolume()).setScale(6, BigDecimal.ROUND_HALF_UP);
                        orderDetail.setTotalboxvolume(goodsboxvolume);
                    }

                }

                TaxType taxType = getTaxType(orderDetail.getTaxtype());
                if (taxType != null) {
                    orderDetail.setTaxtypename(taxType.getName());
                }
                StorageInfo storageInfo = getStorageInfoByID(orderDetail.getStorageid());
                if (storageInfo != null) {
                    orderDetail.setStoragename(storageInfo.getName());
                }

                //当销售发货通知单处于保存状态时 判断仓库中数量是否足够
                if ("2".equals(order.getStatus())) {
//				boolean flag = storageForSalesService.isGoodsEnoughByDispatchBillDetail(billDetail);
                    String storageid = "";
                    if (StringUtils.isNotEmpty(orderDetail.getStorageid())) {
                        storageid = orderDetail.getStorageid();
                    } else if (StringUtils.isNotEmpty(orderDetail.getStorageid())) {
                        storageid = orderDetail.getStorageid();
                    }
                    String keyid = orderDetail.getGoodsid() + "_" + storageid;
                    StorageSummary storageSummary = null;
                    if (storageGoodsMap.containsKey(keyid)) {
                        storageSummary = (StorageSummary) storageGoodsMap.get(keyid);
                    } else {
                        if(StringUtils.isNotEmpty(orderDetail.getSummarybatchid())){
                            StorageSummaryBatch storageSummaryBatch = storageForSalesService.getStorageSummaryBatchByID(orderDetail.getSummarybatchid());
                            if(null!=storageSummaryBatch){
                                storageSummary = new StorageSummary();
                                storageSummary.setExistingnum(storageSummaryBatch.getExistingnum());
                                storageSummary.setUsablenum(storageSummaryBatch.getUsablenum());
                                storageSummary.setOutuseablenum(storageSummary.getUsablenum());
                            }
                        }else if (StringUtils.isNotEmpty(storageid)) {
                            storageSummary = storageForSalesService.getStorageSummarySumByGoodsidAndStorageid(orderDetail.getGoodsid(), storageid);
                        } else {
                            storageSummary = storageForSalesService.getStorageSummarySumByGoodsid(orderDetail.getGoodsid());
                        }
                    }
                    if (null != storageSummary && storageSummary.getUsablenum().compareTo(orderDetail.getUnitnum()) != -1) {
                        orderDetail.setIsenough("1");
                    } else {
                        orderDetail.setIsenough("0");
                    }
                    if (null != storageSummary) {
                        orderDetail.setUsablenum(storageSummary.getUsablenum());
                        if (storageSummary.getUsablenum().compareTo(orderDetail.getUnitnum()) >= 0) {
                            //为下一条相同商品 计算可用量
                            storageSummary.setUsablenum(storageSummary.getUsablenum().subtract(orderDetail.getUnitnum()));
                        } else {
                            storageSummary.setUsablenum(BigDecimal.ZERO);
                        }
                        storageGoodsMap.put(keyid, storageSummary);
                    } else {
                        orderDetail.setUsablenum(BigDecimal.ZERO);
                    }
                }
                GoodsInfo orgGoodsInfo = getAllGoodsInfoByID(orderDetail.getGoodsid());
                if (null != orgGoodsInfo) {
                    orderDetail.setBoxprice(orderDetail.getTaxprice().multiply(orgGoodsInfo.getBoxnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                    orderDetail.setLowestsaleprice(orgGoodsInfo.getLowestsaleprice());
                    //折扣显示处理
                    GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(orgGoodsInfo);
                    if ("1".equals(orderDetail.getIsdiscount())) {
                        goodsInfo.setBarcode(null);
                        goodsInfo.setBoxnum(null);
                        orderDetail.setUnitnum(null);
                        orderDetail.setUsablenum(null);
                        orderDetail.setAuxnumdetail(null);
                        orderDetail.setTaxprice(null);
                        if ("1".equals(orderDetail.getIsbranddiscount())) {
                            orderDetail.setGoodsid("");
                            goodsInfo.setName(goodsInfo.getBrandName());
                            orderDetail.setIsdiscount("2");
                        }
                    }
                    orderDetail.setGoodsInfo(goodsInfo);
                }
            }
            order.setOrderDetailList(orderDetaiList);
        }
        return order;
    }

    /**
     * 修改销售订货单
     * @param order
     * @param saveaudit
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Oct 17, 2017
     */
    @Override
    public Map updateOrderGoods(OrderGoods order,String saveaudit) throws Exception{
        Map idmap = new HashMap();
        BigDecimal totalboxweight = BigDecimal.ZERO;
        BigDecimal totalboxvolume = BigDecimal.ZERO;
        idmap.put("id", order.getId());
        OrderGoods oldOrder = getOrderGoodsMapper().getOrderGoods(order.getId());
        if(null==oldOrder || "3".equals(oldOrder.getStatus()) || "4".equals(oldOrder.getStatus())){
            Map returnMap = new HashMap();
            returnMap.put("flag", false);
            returnMap.put("msg", "单据不存在，或者单据已经审核通过。不能保存");
            return returnMap;
        }
        //获取销售区域上级客户
        Customer customer = getCustomerByID(order.getCustomerid());
        if (null != customer) {
            order.setSalesarea(customer.getSalesarea());
            order.setPcustomerid(customer.getPid());
            order.setCustomersort(customer.getCustomersort());
        }
        SysUser sysUser = getSalesIndoorSysUserByCustomerid(order.getCustomerid());
        if (sysUser != null) {
            //销售内勤
            order.setIndooruserid(sysUser.getPersonnelid());
        }
        String isOpenOrderVersion = getSysParamValue("IsOpenOrderVersion");
//        if("1".equals(isOpenOrderVersion)){
//            //记录订单版本
//            addOrderVersionByID(order.getId());
//        }
        //删除满赠促销已记录信息
//        deleteCutomerFullFreeLog(order.getId());
        getOrderGoodsMapper().deleteOrderGoodsDetailByOrderId(order.getId());
        List<OrderGoodsDetail> orderDetailList = order.getOrderDetailList();
        if (orderDetailList.size() > 0) {
            int seq = 0;
            int maxseq = -1 ;
            Map seqMap = new HashMap();
            List detailList = new ArrayList();
            List<OrderGoodsDetail> brandDiscountList = new ArrayList<OrderGoodsDetail>();
            for (OrderGoodsDetail orderDetail : orderDetailList) {
                if (orderDetail != null) {
                    if(null != orderDetail.getTotalboxvolume()){
                        totalboxweight = totalboxweight.add(orderDetail.getTotalboxweight());
                        totalboxvolume = totalboxvolume.add(orderDetail.getTotalboxvolume());
                    }
//                    orderDetail.setIsview("1");
                    orderDetail.setOrderid(order.getId());
                    if(StringUtils.isEmpty(orderDetail.getStorageid()) && StringUtils.isNotEmpty(order.getStorageid())){
                        orderDetail.setStorageid(order.getStorageid());
                    }
                    if(StringUtils.isNotEmpty(orderDetail.getSummarybatchid())){
                        StorageSummaryBatch summaryBatch = storageForSalesService.getStorageSummaryBatchByID(orderDetail.getSummarybatchid());
                        if(null!=summaryBatch){
                            orderDetail.setStorageid(summaryBatch.getStorageid());
                        }
                    }
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(orderDetail.getGoodsid());
                    if (null != goodsInfo) {
                        //计算辅单位数量
                        Map map = countGoodsInfoNumber(orderDetail.getGoodsid(), orderDetail.getAuxunitid(), orderDetail.getUnitnum());
                        if (map.containsKey("auxnum")) {
                            orderDetail.setTotalbox((BigDecimal) map.get("auxnum"));
                        }
                        orderDetail.setGoodssort(goodsInfo.getDefaultsort());
                        orderDetail.setBrandid(goodsInfo.getBrand());
                        orderDetail.setCostprice(getGoodsCostprice(order.getStorageid(),goodsInfo));

                        String isOrderGoodsUseTax=getSysParamValue("isOrderGoodsUseTax");
                        if("1".equals(isOrderGoodsUseTax)){
                            orderDetail.setOldtaxprice(orderDetail.getTaxprice());
                        }

                        //获取税种，税额，未税金额，未税单价
//                        orderDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
                        if(null==orderDetail.getTaxamount()){
                            orderDetail.setTaxamount(BigDecimal.ZERO);
                        }
//                        BigDecimal notaxanount = getNotaxAmountByTaxAmount(orderDetail.getTaxamount(),goodsInfo.getDefaulttaxtype());
//                        orderDetail.setNotaxamount(notaxanount);
//                        orderDetail.setTax(orderDetail.getTaxamount().subtract(notaxanount));
//                        if(null!=notaxanount && null!=orderDetail.getUnitnum() && orderDetail.getUnitnum().compareTo(BigDecimal.ZERO)==1){
//                            orderDetail.setNotaxprice(notaxanount.divide(orderDetail.getUnitnum(),6,BigDecimal.ROUND_HALF_UP));
//                        }
                        orderDetail.setTaxamount(orderDetail.getTaxamount());
                        orderDetail.setNotaxamount(orderDetail.getNotaxamount());
                        orderDetail.setTax(orderDetail.getTaxamount().subtract(orderDetail.getNotaxamount()));
                        //获取品牌部门
                        Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                        if (null != brand) {
                            orderDetail.setBranddept(brand.getDeptid());
                        }
                        //根据客户编号和品牌编号 获取品牌业务员
                        orderDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), order.getCustomerid()));
                        //厂家业务员
                        orderDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), order.getCustomerid()));
                        orderDetail.setSupplierid(goodsInfo.getDefaultsupplier());
                        //获取系统设置的原价，不包括特价
                        orderDetail.setFixprice(getGoodsPriceByCustomer(orderDetail.getGoodsid(), order.getCustomerid()));
                        //商品折扣 指定仓库
                        if(StringUtils.isEmpty(order.getStorageid()) &&StringUtils.isNotEmpty(orderDetail.getStoragename())){
                            orderDetail.setStorageid(getStorageInfoByName(orderDetail.getStoragename()).getId());
                        }
                        orderDetail.setNotordertaxamount(orderDetail.getNotorderunitnum().multiply(orderDetail.getTaxprice()));
                        orderDetail.setNotordernotaxamount(orderDetail.getNotorderunitnum().multiply(orderDetail.getNotaxprice()));
                        orderDetail.setOrdertaxamount(orderDetail.getOrderunitnum().multiply(orderDetail.getTaxprice()));
                        orderDetail.setOrdernotaxamount(orderDetail.getOrderunitnum().multiply(orderDetail.getNotaxprice()));
                    }
//                    if (orderDetail.getSeq() >= seq) {
//                        seq = orderDetail.getSeq();
//                        seq++;
//                    }else{
//                        ++seq;
//                    }

                    if (orderDetail.getSeq()==null ||orderDetail.getSeq() == 0) {
                        ++ maxseq ;
                        orderDetail.setSeq(maxseq);
                        seqMap.put(maxseq,orderDetail.getGoodsid());
                    }else if(seqMap.containsKey(orderDetail.getSeq())){
                        ++ maxseq ;
                        orderDetail.setSeq(maxseq);
                        seqMap.put(maxseq,orderDetail.getGoodsid());
                    }else{
                        seqMap.put(orderDetail.getSeq(),orderDetail.getGoodsid());
                        maxseq = orderDetail.getSeq();
                    }
                    //品牌折扣不直接插入 折算到各商品上后再插入
                    if (!"2".equals(orderDetail.getIsdiscount())) {
                        //数量为0的明细不添加
                        if (null != orderDetail.getUnitnum() && orderDetail.getUnitnum().compareTo(BigDecimal.ZERO) != 0) {
                            detailList.add(orderDetail);
                        } else if ("1".equals(orderDetail.getIsdiscount())) {
                            detailList.add(orderDetail);
                        }
                        //默认商品为不是折扣
                        if(StringUtils.isEmpty(orderDetail.getIsdiscount())){
                            orderDetail.setIsdiscount("0");
                        }
                    } else {
                        brandDiscountList.add(orderDetail);
                    }
                }
            }
            if (detailList.size() > 0) {
                //添加明细
                getOrderGoodsMapper().addOrderGoodsDetailList(detailList);
            }
            //品牌折扣 平摊到各商品中
            for (OrderGoodsDetail billDetail : brandDiscountList) {
                List<OrderGoodsDetail> brandGoodsList = getOrderGoodsMapper().getOrderGoodsDetailListByOrderidAndBrandid(order.getId(), billDetail.getBrandid());
                BigDecimal totalamount = BigDecimal.ZERO;
                BigDecimal totalunitnum = BigDecimal.ZERO;
                BigDecimal totalboxSum = BigDecimal.ZERO;
                for (OrderGoodsDetail billGoodsDetail : brandGoodsList) {
                    totalamount = totalamount.add(billGoodsDetail.getTaxamount());
                    totalunitnum = totalunitnum.add(billGoodsDetail.getUnitnum());
                    totalboxSum = totalboxSum.add(billGoodsDetail.getTotalbox());
                }
                if (null != billDetail.getTaxamount() && billDetail.getTaxamount().compareTo(BigDecimal.ZERO) != 0) {
                    BigDecimal useamount = BigDecimal.ZERO;
                    for (int i = 0; i < brandGoodsList.size(); i++) {
                        OrderGoodsDetail billGoodsDetail = brandGoodsList.get(i);
                        OrderGoodsDetail detail = new OrderGoodsDetail();
                        detail.setStorageid(billDetail.getStorageid());
                        detail.setGoodsid(billGoodsDetail.getGoodsid());
                        detail.setBrandid(billGoodsDetail.getBrandid());
                        GoodsInfo goodsInfo = getAllGoodsInfoByID(billGoodsDetail.getGoodsid());
                        if (null != goodsInfo) {
                            detail.setGoodssort(goodsInfo.getDefaultsort());
                        }
                        detail.setBranddept(billGoodsDetail.getBranddept());
                        detail.setBranduser(billGoodsDetail.getBranduser());
                        detail.setSupplieruser(billGoodsDetail.getSupplieruser());
                        detail.setOrderid(billGoodsDetail.getOrderid());
                        //获取供应商
                        Brand brand = getGoodsBrandByID(billGoodsDetail.getBrandid());
                        if (null != brand) {
                            detail.setSupplierid(brand.getSupplierid());
                            detail.setTaxtype(brand.getDefaulttaxtype());
                        }
                        detail.setIsdiscount("1");
                        detail.setIsbranddiscount("1");
                        maxseq++;
                        detail.setSeq(maxseq);
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
                        detail.setTax(discountamount.subtract(notaxdiscountamount));
                        detail.setNotordertaxamount(detail.getNotorderunitnum().multiply(detail.getTaxprice()));
                        detail.setNotordernotaxamount(detail.getNotorderunitnum().multiply(detail.getNotaxprice()));
                        detail.setOrdertaxamount(detail.getOrderunitnum().multiply(detail.getTaxprice()));
                        detail.setOrdernotaxamount(detail.getOrderunitnum().multiply(detail.getNotaxprice()));

                        getOrderGoodsMapper().addOrderGoodsDetail(detail);
                    }
                }
            }
        }
        List<OrderGoodsDetail> lackGoodsList = order.getLackList();
        if (null != lackGoodsList && lackGoodsList.size() > 0) {
            int seq = 0;
            if (null != orderDetailList) {
                seq = orderDetailList.size() + 1;
            }
            List detailList = new ArrayList();
            for (OrderGoodsDetail orderDetail : lackGoodsList) {
                if (orderDetail != null) {
//                    totalboxweight = totalboxweight.add(orderDetail.getTotalboxweight());
//                    totalboxvolume = totalboxvolume.add(orderDetail.getTotalboxvolume());
                    orderDetail.setUnitnum(BigDecimal.ZERO);
//                    orderDetail.setIsview("0");
                    orderDetail.setOrderid(order.getId());
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(orderDetail.getGoodsid());
                    orderDetail.setNotordertaxamount(orderDetail.getNotorderunitnum().multiply(orderDetail.getTaxprice()));
                    orderDetail.setNotordernotaxamount(orderDetail.getNotorderunitnum().multiply(orderDetail.getNotaxprice()));
                    orderDetail.setOrdertaxamount(orderDetail.getOrderunitnum().multiply(orderDetail.getTaxprice()));
                    orderDetail.setOrdernotaxamount(orderDetail.getOrderunitnum().multiply(orderDetail.getNotaxprice()));
                    if (null != goodsInfo) {
                        //计算辅单位数量
                        Map map = countGoodsInfoNumber(orderDetail.getGoodsid(), orderDetail.getAuxunitid(), orderDetail.getUnitnum());
                        if (map.containsKey("auxnum")) {
                            orderDetail.setTotalbox((BigDecimal) map.get("auxnum"));
                        }
                        orderDetail.setGoodssort(goodsInfo.getDefaultsort());
                        orderDetail.setBrandid(goodsInfo.getBrand());
                        orderDetail.setCostprice(getGoodsCostprice(order.getStorageid(),goodsInfo));
                        //获取品牌部门
                        Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                        if (null != brand) {
                            orderDetail.setBranddept(brand.getDeptid());
                        }
                        //根据客户编号和品牌编号 获取品牌业务员
                        orderDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), order.getCustomerid()));
                        //厂家业务员
                        orderDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), order.getCustomerid()));
                        orderDetail.setSupplierid(goodsInfo.getDefaultsupplier());
                        //获取系统设置的原价，不包括特价
                        orderDetail.setFixprice(getGoodsPriceByCustomer(orderDetail.getGoodsid(), order.getCustomerid()));
                    }
                    if (orderDetail.getSeq() >= seq) {
                        seq = orderDetail.getSeq();
                        seq++;
                    }
                    if (orderDetail.getSeq() == 0) {
                        orderDetail.setSeq(seq);
                    }
                }
            }
            //添加明细
            getOrderGoodsMapper().addOrderGoodsDetailList(lackGoodsList);
        }
        //订单单据中添加总重量和总体积
        order.setTotalboxvolume(totalboxvolume);
        order.setTotalboxweight(totalboxweight);
        boolean flag = getOrderGoodsMapper().updateOrderGoods(order) > 0;
        boolean validateFlag = false ;
//        if(flag){
//            addCutomerFullFreeLog(order);
//        }
//        if(StringUtils.isNotEmpty(order.getSourceid())){
//            int a = getSalesOrderMapper().validateSourceId(order.getSourceid()) ;
//            validateFlag = a > 1 ;
//        }
        Map map = new HashMap();
        if ("saveaudit".equals(saveaudit) && flag) {
            map = auditOrderGoods("1",order.getId(),null);
            boolean auditflag = (Boolean) map.get("flag");
            map.put("auditflag", auditflag);
            if(validateFlag){
                String msg = (String) map.get("msg");
                msg = msg + "。该单据的客户单号重复!";
                map.put("msg",msg);
            }
        }else if(validateFlag){
            map.put("msg","该单据的客户单号重复!");
        }
        map.put("flag", flag);
        return map;
    }

    /**
     * 判断订货单能否审核
     * @param id
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Oct 17, 2017
     */
    @Override
    public Map checkOrderGoodsAudit(String id) throws Exception{
        Map returnMap = new HashMap();
        boolean flag = true;
        String msg = "";
        String sysparm = getSysParamValue("checkOrderRepeatDays");
        if (StringUtils.isEmpty(sysparm) || !StringUtils.isNumeric(sysparm)) {
            sysparm = "3";
        }
        //0天表示不去验证单据是否重复
        if(!"0".equals(sysparm)){
            Map querymap = new HashMap();
            querymap.put("id", id);
            OrderGoods order = getOrderGoodsMapper().getOrderGoods(id);
            if (null != order) {
                Customer customer = getCustomerByID(order.getCustomerid());
                //是否重复单据判断
                Map map = getOrderGoodsMapper().checkOrderGoodsRepeat(id, order.getCustomerid(), sysparm);
                boolean isNotRepeat = true;
                if (null != map) {
                    isNotRepeat = false;
                }
//                Map canAuditMap = isOrderGoodsCanAudit(id);
                boolean auditflag = true;
//                if (null != canAuditMap && canAuditMap.containsKey("flag")) {
//                    auditflag = (Boolean) canAuditMap.get("flag");
//                    if (!auditflag) {
//                        msg += (String) canAuditMap.get("msg");
//                    }
//                }
                if (!isNotRepeat) {
                    if (StringUtils.isEmpty(msg)) {
                        msg = "订货单" + id + ":" + "客户：" + customer.getId() + "," + customer.getName() + ",";
                    }
                    msg += "在最近" + sysparm + "天内存在订单重复情况.";
                }
                //系统参数 控制销售订单 仓库是否必填
                String isOrderStorageSelect = getSysParamValue("IsOrderStorageSelect");
                if("1".equals(isOrderStorageSelect) && StringUtils.isEmpty(order.getStorageid())){
                    flag = false;
                    msg += "订单未选择发货仓库.";
                }
                if (!auditflag || !isNotRepeat) {
                    flag = false;
                }
            } else {
                flag = true;
            }
        }
        returnMap.put("flag", flag);
        returnMap.put("msg", msg);
        return returnMap;
    }

    /**
     * 删除销售订货单
     * @param id
     * @return java.lang.Boolean
     * @throws
     * @author luoqiang
     * @date Oct 17, 2017
     */
    @Override
    public Boolean deleteOrderGoods(String id){
        OrderGoods oldOrder = getOrderGoodsMapper().getOrderGoods(id);
        if(null==oldOrder || "3".equals(oldOrder.getStatus()) || "4".equals(oldOrder.getStatus())){
            return false;
        }
//        deleteCutomerFullFreeLog(id);
        getOrderGoodsMapper().deleteOrderGoodsDetailByOrderId(id);
        boolean flag = getOrderGoodsMapper().deleteOrderGoods(id)>0;
        return flag;
    }

    /**
     * 销售订货单批量审核
     * @param ids
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Oct 17, 2017
     */
    @Override
    public Map auditMultiOrderGoods(String ids) throws Exception{
        int failure = 0;
        int success = 0;
        int noaudit = 0;
        boolean sFlag = true;
        String msg = "";
        SysUser sysUser = getSysUser();
        if (StringUtils.isNotEmpty(ids)) {
            if (ids.endsWith(",")) {
                ids = ids.substring(0, ids.length() - 1);
            }
            String[] idArr = ids.split(",");
            for (String id : idArr) {
                OrderGoods order = getOrderGoods(id);
                Map map = auditOrderGoods("1", order.getId(), null);
                boolean auditflag = (Boolean) map.get("flag");
                if ("".equals(msg)) {
                    msg += map.get("msg");
                } else {
                    msg += "<br/>" + map.get("msg");
                }
                if (auditflag) {
                    success++;
                } else {
                    failure++;
                }
            }
        }
        Map map = new HashMap();
        map.put("flag", sFlag);
        map.put("failure", failure);
        map.put("success", success);
        map.put("noaudit", noaudit);
        map.put("msg", msg);
        return map;
    }

    /**
     * 根据销售订货单生成销售订单
     * @param orderid
     * @param orderDetailList
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Oct 17, 2017
     */
    @Override
    public Map addOrderByOrderGoodsBill(String orderid,List<OrderDetail> orderDetailList) throws Exception{
        OrderGoods orderGoods=getOrderGoodsMapper().getOrderGoods(orderid);
        String orderGoodsStr=JSONUtils.objectToJsonStr(orderGoods);
        JSONObject json = JSONObject.fromObject(orderGoodsStr);
        Order order= (Order)JSONObject.toBean(json,Order.class);
        order.setSourceid(orderGoods.getId());
        order.setSourcetype("3");
        order.setStatus("2");
        order.setBusinessdate(CommonUtils.getTodayDataStr());
        if (isAutoCreate("t_sales_order")) {
            // 获取自动编号
            String id = getAutoCreateSysNumbderForeign(order, "t_sales_order");
            order.setId(id);
        }else{
            String id = "XSDD-"+CommonUtils.getDataNumberSendsWithRand();
            order.setId(id);
        }
//        List<OrderDetail> orderDetailList = order.getOrderDetailList();
        if (orderDetailList.size() > 0) {
            int seq = 1;
            List<OrderDetail> detailList = new ArrayList();
            List<OrderDetail> brandDiscountList = new ArrayList();
            for (OrderDetail orderDetail : orderDetailList) {
                orderDetail.setOrderid(order.getId());
                if (orderDetail != null) {
                    orderDetail.setBillno(orderGoods.getId());
                    orderDetail.setBilldetailno(orderDetail.getId());
                    orderDetail.setOrderid(order.getId());

                    if(StringUtils.isEmpty(orderDetail.getStorageid()) && StringUtils.isNotEmpty(order.getStorageid())){
                        orderDetail.setStorageid(order.getStorageid());
                    }
                    if(StringUtils.isNotEmpty(orderDetail.getSummarybatchid())){
                        StorageSummaryBatch summaryBatch = storageForSalesService.getStorageSummaryBatchByID(orderDetail.getSummarybatchid());
                        if(null!=summaryBatch){
                            orderDetail.setStorageid(summaryBatch.getStorageid());
                        }
                    }
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(orderDetail.getGoodsid());
                    if(null != goodsInfo){
                        orderDetail.setGoodssort(goodsInfo.getDefaultsort());
                        orderDetail.setBrandid(goodsInfo.getBrand());
                        orderDetail.setCostprice(getGoodsCostprice(order.getStorageid(),goodsInfo));
                        orderDetail.setFixnum(orderDetail.getUnitnum());

//                        //获取税种，税额，未税金额，未税单价
//                        orderDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
                        if(null==orderDetail.getTaxamount()){
                            orderDetail.setTaxamount(BigDecimal.ZERO);
                        }
//                        BigDecimal notaxanount = getNotaxAmountByTaxAmount(orderDetail.getTaxamount(),goodsInfo.getDefaulttaxtype());
//                        orderDetail.setNotaxamount(notaxanount);
//                        orderDetail.setTax(orderDetail.getTaxamount().subtract(notaxanount));
//                        if(null!=notaxanount && null!=orderDetail.getUnitnum() && orderDetail.getUnitnum().compareTo(BigDecimal.ZERO)==1){
//                            orderDetail.setNotaxprice(notaxanount.divide(orderDetail.getUnitnum(),6,BigDecimal.ROUND_HALF_UP));
//                        }
                        orderDetail.setTaxamount(orderDetail.getTaxamount());
                        orderDetail.setNotaxamount(orderDetail.getNotaxamount());
                        orderDetail.setTax(orderDetail.getTaxamount().subtract(orderDetail.getNotaxamount()));
                        //计算辅单位数量
                        Map map = countGoodsInfoNumber(orderDetail.getGoodsid(), orderDetail.getAuxunitid(), orderDetail.getUnitnum());
                        if (map.containsKey("auxnum")) {
                            orderDetail.setTotalbox((BigDecimal) map.get("auxnum"));
                        }
                        if (map.containsKey("auxnumdetail")) {
                            orderDetail.setAuxnumdetail((String) map.get("auxnumdetail"));
                        }
                        //获取品牌部门
                        Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                        if (null != brand){
                            orderDetail.setBranddept(brand.getDeptid());
                        }
                        //根据客户编号和品牌编号 获取品牌业务员
                        orderDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), order.getCustomerid()));
                        //厂家业务员
                        orderDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), order.getCustomerid()));
                        orderDetail.setSupplierid(goodsInfo.getDefaultsupplier());

                        //获取系统设置的原价，不包括特价
                        orderDetail.setFixprice(getGoodsPriceByCustomer(orderDetail.getGoodsid(), order.getCustomerid()));
                    }
                    if(orderDetail.getSeq()==0){
                        orderDetail.setSeq(seq);
                    }
                    seq++;
                    //品牌折扣不直接插入 折算到各商品上后再插入
                    if (!"2".equals(orderDetail.getIsdiscount())) {
                        //数量为0的明细不添加
                        if (null != orderDetail.getUnitnum() && orderDetail.getUnitnum().compareTo(BigDecimal.ZERO) != 0) {
                            detailList.add(orderDetail);
                        } else if ("1".equals(orderDetail.getIsdiscount())) {
                            detailList.add(orderDetail);
                        }
                        //默认商品为不是折扣
                        if(StringUtils.isEmpty(orderDetail.getIsdiscount())){
                            orderDetail.setIsdiscount("0");
                        }
                    } else {
                        brandDiscountList.add(orderDetail);
                    }
                }
            }
            if (detailList.size() > 0) {
                //添加明细
                getSalesOrderDetailMapper().addOrderDetailList(detailList);
            }

            //品牌折扣 平摊到各商品中
            for (OrderDetail billDetail : brandDiscountList) {
                List<OrderDetail> brandGoodsList = getSalesOrderDetailMapper().getOrderDetailListByOrderidAndBrandid(order.getId(), billDetail.getBrandid());
                BigDecimal totalamount = BigDecimal.ZERO;
                for (OrderDetail billGoodsDetail : brandGoodsList) {
                    totalamount = totalamount.add(billGoodsDetail.getTaxamount());
                }
                if (null != billDetail.getTaxamount() && billDetail.getTaxamount().compareTo(BigDecimal.ZERO) != 0) {
                    BigDecimal useamount = BigDecimal.ZERO;
                    for (int i = 0; i < brandGoodsList.size(); i++) {
                        OrderDetail billGoodsDetail = brandGoodsList.get(i);
                        OrderDetail detail = new OrderDetail();
                        detail.setStorageid(billDetail.getStorageid());
                        detail.setGoodsid(billGoodsDetail.getGoodsid());
                        //商品分类
                        GoodsInfo goodsInfo = getAllGoodsInfoByID(billGoodsDetail.getGoodsid());
                        if (null != goodsInfo) {
                            detail.setGoodssort(goodsInfo.getDefaultsort());
                        }
                        detail.setBrandid(billGoodsDetail.getBrandid());
                        detail.setBranddept(billGoodsDetail.getBranddept());
                        detail.setBranduser(billGoodsDetail.getBranduser());
                        detail.setSupplieruser(billGoodsDetail.getSupplieruser());
                        //获取供应商
                        Brand brand = getGoodsBrandByID(billGoodsDetail.getBrandid());
                        if (null != brand) {
                            detail.setSupplierid(brand.getSupplierid());
                            detail.setTaxtype(brand.getDefaulttaxtype());
                        }
                        detail.setOrderid(billGoodsDetail.getOrderid());
                        detail.setIsdiscount("1");
                        detail.setIsbranddiscount("1");
                        seq++;
                        detail.setSeq(seq);
                        detail.setRemark(billDetail.getRemark());
                        //计算平摊到商品中的 各折扣金额
                        BigDecimal discountamount = billGoodsDetail.getTaxamount().divide(totalamount, decimalLen, BigDecimal.ROUND_HALF_UP).multiply(billDetail.getTaxamount());
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
                        detail.setTax(discountamount.subtract(notaxdiscountamount));

                        getSalesOrderDetailMapper().addOrderDetail(detail);
                    }
                }
            }
        }
        //根据客户编号获取该客户的销售内勤用户信息
        SysUser sysUser = getSysUser();
        if (sysUser != null && null == order.getAdduserid()) {
            order.setAdddeptid(sysUser.getDepartmentid());
            order.setAdddeptname(sysUser.getDepartmentname());
            order.setAdduserid(sysUser.getUserid());
            order.setAddusername(sysUser.getName());
        }
        //销售内勤
        SysUser indoorsysUser = getSalesIndoorSysUserByCustomerid(order.getCustomerid());
        order.setIndooruserid(indoorsysUser.getPersonnelid());
        //获取销售区域上级客户
        Customer customer = getCustomerByID(order.getCustomerid());
        if (null != customer) {
            order.setSalesarea(customer.getSalesarea());
            order.setPcustomerid(customer.getPid());
            order.setCustomersort(customer.getCustomersort());
        }
        boolean flag = getSalesOrderMapper().addOrder(order) > 0;
//        if(flag){
//            addCutomerFullFreeLog(order);
//        }
        if(flag){
            List<OrderDetail> detailList=salesOrderService.getDetailListOrder(order.getId());
            List<OrderGoodsRelation> orderGoodsRelationList=new ArrayList<OrderGoodsRelation>();
            for(OrderDetail orderDetail:detailList){
                //记录关联订货单信息
                OrderGoodsRelation orderGoodsRelation=new OrderGoodsRelation();
                orderGoodsRelation.setBilltype("1");
                orderGoodsRelation.setOrderid(orderDetail.getOrderid());
                orderGoodsRelation.setOrderdetailid(orderDetail.getId()+"");
                orderGoodsRelation.setOrdergoodsid(orderDetail.getBillno());
                orderGoodsRelation.setOrdergoodsdetailid(orderDetail.getBilldetailno()+"");
                orderGoodsRelation.setUnitnum(orderDetail.getUnitnum());
                orderGoodsRelationList.add(orderGoodsRelation);
            }
            //添加订货单和要货单关联关系
            int t=getOrderGoodsMapper().insertOrderGoodsRelationList(orderGoodsRelationList);
            if(t==0){
                throw new Exception("回写要货单关联的订货单关系失败!");
            }
            updateOrderGoodsDetailNum(order.getId(),orderGoods.getId());
        }
        Map map = new HashMap();
//        if ("saveaudit".equals(saveaudit) && flag) {
//            map = auditOrder("1", order.getId(), null);
//            boolean auditflag = (Boolean) map.get("flag");
//            map.put("auditflag", auditflag);
//        }
        map.put("flag", flag);
        map.put("id",order.getId());
        return map;
    }

    public void updateOrderGoodsDetailNum(String orderid,String ordergoodsid){
        //更新订单明细的relationordergoodsid字段,记录和订货单的关联关系
        getOrderGoodsMapper().updateOrderRelationOrdergoodsid(orderid);
        getOrderGoodsMapper().updateOrderGoodsDetailNum(orderid,"1",ordergoodsid);
    }

    /**
     * 获取可以生成订单的订货单数据
     * @param pageMap
     * @return com.hd.agent.common.util.PageData
     * @throws
     * @author luoqiang
     * @date Oct 18, 2017
     */
    @Override
    public PageData getOrderGoodsListForAddOrder(PageMap pageMap) throws Exception{
        List<OrderGoods> orderList=getOrderGoodsMapper().getOrderGoodsListForAddOrderList(pageMap);
        for (OrderGoods order : orderList) {
            Map amountmap=getOrderGoodsMapper().getOrderGoodsAmount(order.getId());
            BigDecimal ordertaxamount=(BigDecimal)amountmap.get("ordertaxamount");
            BigDecimal ordernotaxamount=(BigDecimal)amountmap.get("ordernotaxamount");
            BigDecimal notordertaxamount=(BigDecimal)amountmap.get("notordertaxamount");
            BigDecimal notordernotaxamount=(BigDecimal)amountmap.get("notordernotaxamount");
            order.setOrdertaxamount(ordertaxamount);
            order.setOrdernotaxamount(ordernotaxamount);
            order.setNotordertaxamount(notordertaxamount);
            order.setNotordernotaxamount(notordernotaxamount);
            Personnel indoorPerson = getPersonnelById(order.getIndooruserid());
            if (null != indoorPerson) {
                order.setIndoorusername(indoorPerson.getName());
            }
            DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(order.getSalesdept());
            if (departMent != null) {
                order.setSalesdept(departMent.getName());
            }
            Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(order.getSalesuser());
            if (personnel != null) {
                order.setSalesuser(personnel.getName());
            }
            Map map = new HashMap();
            map.put("id", order.getCustomerid());
            Customer customer = getCustomerByID(order.getCustomerid());
            if (customer != null) {
                order.setCustomerid(customer.getId());
                order.setCustomername(customer.getName());
                order.setAddress(customer.getAddress());
            }
            map.put("id", order.getHandlerid());
            Contacter contacter = getBaseFilesContacterMapper().getContacterDetail(map);
            if (contacter != null) {
                order.setHandlerid(contacter.getName());
            }
            Map total = getOrderGoodsMapper().getOrderGoodsDetailTotal(order.getId());
            if (total != null) {
                if (total.containsKey("taxamount")) {
                    order.setField01(total.get("taxamount").toString());
                    order.setField04(total.get("taxamount").toString());
                }
                if (total.containsKey("notaxamount")) {
                    order.setField02(total.get("notaxamount").toString());
                }
                if (total.containsKey("tax")) {
                    order.setField03(total.get("tax").toString());
                }

            }

        }
        int count=getOrderGoodsMapper().getOrderGoodsListForAddOrderCount(pageMap);
        PageData pageData=new PageData(count,orderList,pageMap);
        return pageData;
    }

    /**
     * 获取销售订货单里未生成订单的数据
     * @param id
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Oct 18, 2017
     */
    @Override
    public List showGoodsToOrderData(String id) throws Exception{
        OrderGoods order=getOrderGoods(id);
        List<OrderGoodsDetail> list=getOrderGoodsMapper().showGoodsToOrderData(id);
        Map storageGoodsMap = new HashMap();
        for(OrderGoodsDetail orderDetail:list){
            //未生成订单金额作为金额穿进去
            orderDetail.setTaxamount(orderDetail.getNotordertaxamount());

            BigDecimal bUnitNum = orderDetail.getNotorderunitnum();
            Map result = getNumChangeResult(orderDetail.getGoodsid(), order.getCustomerid(), bUnitNum, order.getBusinessdate(),orderDetail.getTaxprice().toString(),"1");

//            orderDetail.setTaxamount((BigDecimal)result.get("taxamount"));
//            orderDetail.setNotaxamount((BigDecimal) result.get("notaxamount"));
//            orderDetail.setTax((BigDecimal) result.get("tax"));
            orderDetail.setOvernum((BigDecimal) result.get("overnum"));
            orderDetail.setAuxnum((BigDecimal) result.get("auxnum"));
            orderDetail.setUnitnum((BigDecimal) result.get("unitnum"));
            orderDetail.setAuxnumdetail(result.get("auxnumdetail").toString());
            BigDecimal notordertotal=(BigDecimal)result.get("totalbox");
            orderDetail.setNotorderbox(notordertotal.setScale(0,BigDecimal.ROUND_DOWN));
            orderDetail.setNotorderovernum((BigDecimal)result.get("overnum"));
            GoodsInfo Info = getGoodsInfoByID(orderDetail.getGoodsid());
            if(null != Info){
                //商品客户价格套价格 ---》 参考价
                BigDecimal referenceprice = getGoodsPriceSetByCustomer(orderDetail.getGoodsid(),order.getCustomerid());
                orderDetail.setReferenceprice(referenceprice);
                orderDetail.setBasesaleprice(Info.getBasesaleprice());
                orderDetail.setBuyprice(Info.getHighestbuyprice());
                if(null != Info.getGrossweight()){
                    BigDecimal goodsBoxweight = orderDetail.getUnitnum().multiply(Info.getGrossweight()).setScale(6, BigDecimal.ROUND_HALF_UP);
                    orderDetail.setTotalboxweight(goodsBoxweight);
                }
                if(null != Info.getSinglevolume()){
                    BigDecimal goodsboxvolume = orderDetail.getUnitnum().multiply(Info.getSinglevolume()).setScale(6, BigDecimal.ROUND_HALF_UP);
                    orderDetail.setTotalboxvolume(goodsboxvolume);
                }

            }

            TaxType taxType = getTaxType(orderDetail.getTaxtype());
            if (taxType != null) {
                orderDetail.setTaxtypename(taxType.getName());
            }
            StorageInfo storageInfo = getStorageInfoByID(orderDetail.getStorageid());
            if (storageInfo != null) {
                orderDetail.setStoragename(storageInfo.getName());
            }

            //当销售发货通知单处于保存状态时 判断仓库中数量是否足够
            if ("2".equals(order.getStatus())) {
//				boolean flag = storageForSalesService.isGoodsEnoughByDispatchBillDetail(billDetail);
                String storageid = "";
                if (StringUtils.isNotEmpty(orderDetail.getStorageid())) {
                    storageid = orderDetail.getStorageid();
                } else if (StringUtils.isNotEmpty(orderDetail.getStorageid())) {
                    storageid = orderDetail.getStorageid();
                }
                String keyid = orderDetail.getGoodsid() + "_" + storageid;
                StorageSummary storageSummary = null;
                if (storageGoodsMap.containsKey(keyid)) {
                    storageSummary = (StorageSummary) storageGoodsMap.get(keyid);
                } else {
                    if(StringUtils.isNotEmpty(orderDetail.getSummarybatchid())){
                        StorageSummaryBatch storageSummaryBatch = storageForSalesService.getStorageSummaryBatchByID(orderDetail.getSummarybatchid());
                        if(null!=storageSummaryBatch){
                            storageSummary = new StorageSummary();
                            storageSummary.setExistingnum(storageSummaryBatch.getExistingnum());
                            storageSummary.setUsablenum(storageSummaryBatch.getUsablenum());
                            storageSummary.setOutuseablenum(storageSummary.getUsablenum());
                        }
                    }else if (StringUtils.isNotEmpty(storageid)) {
                        storageSummary = storageForSalesService.getStorageSummarySumByGoodsidAndStorageid(orderDetail.getGoodsid(), storageid);
                    } else {
                        storageSummary = storageForSalesService.getStorageSummarySumByGoodsid(orderDetail.getGoodsid());
                    }
                }
                if (null != storageSummary && storageSummary.getUsablenum().compareTo(orderDetail.getUnitnum()) != -1) {
                    orderDetail.setIsenough("1");
                } else {
                    orderDetail.setIsenough("0");
                }
                if (null != storageSummary) {
                    orderDetail.setUsablenum(storageSummary.getUsablenum());
                    if (storageSummary.getUsablenum().compareTo(orderDetail.getUnitnum()) >= 0) {
                        //为下一条相同商品 计算可用量
                        storageSummary.setUsablenum(storageSummary.getUsablenum().subtract(orderDetail.getUnitnum()));
                    } else {
                        storageSummary.setUsablenum(BigDecimal.ZERO);
                    }
                    storageGoodsMap.put(keyid, storageSummary);
                } else {
                    orderDetail.setUsablenum(BigDecimal.ZERO);
                }
            }
            GoodsInfo orgGoodsInfo = getAllGoodsInfoByID(orderDetail.getGoodsid());
            if (null != orgGoodsInfo) {
                orderDetail.setBoxprice(orderDetail.getTaxprice().multiply(orgGoodsInfo.getBoxnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                orderDetail.setLowestsaleprice(orgGoodsInfo.getLowestsaleprice());
                //折扣显示处理
                GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(orgGoodsInfo);
                if ("1".equals(orderDetail.getIsdiscount())) {
                    goodsInfo.setBarcode(null);
                    goodsInfo.setBoxnum(null);
                    orderDetail.setUnitnum(null);
                    orderDetail.setUsablenum(null);
                    orderDetail.setAuxnumdetail(null);
                    orderDetail.setTaxprice(null);
                    if ("1".equals(orderDetail.getIsbranddiscount())) {
                        orderDetail.setGoodsid("");
                        goodsInfo.setName(goodsInfo.getBrandName());
                        orderDetail.setIsdiscount("2");
                    }
                }
                orderDetail.setGoodsInfo(goodsInfo);
            }
        }
        return list;
    }
    private Map getNumChangeResult(String goodsId, String customerId, BigDecimal bUnitNum, String date,String price,String taxpricechange) throws Exception{
        //商品明细中含税单价是否改动，0未改动1改动，若改动过，则无论商品数量怎么变化，不根据取价方式获取含税单价，若未改动过，则根据取价方式取价（如果数量是在特价数量区间外的 就取正常商品单价 在特价数量区间内的 取特价）
        if(StringUtils.isEmpty(taxpricechange)){
            taxpricechange = "0";
        }
        BigDecimal taxprice = null;
        if(StringUtils.isNotEmpty(price)){
            taxprice = new BigDecimal(price);
        }
        Map result = new HashMap();
        GoodsInfo_MteringUnitInfo mteringUnitInfo = getDefaultGoodsAuxMeterUnitInfo(goodsId);
        String auxUnitId = "";
        if(mteringUnitInfo != null){
            auxUnitId = mteringUnitInfo.getMeteringunitid();
        }
        OrderDetail orderDetail = salesOrderService.getGoodsDetail(goodsId, customerId, date, bUnitNum, null);
//		if(null !=orderDetail.getOffprice() || null==taxprice){
//			taxprice = orderDetail.getTaxprice();
//		}
        if("0".equals(taxpricechange) || null==taxprice){
            taxprice = orderDetail.getTaxprice();
            result.put("remark", orderDetail.getRemark());

            if(StringUtils.isNotEmpty(orderDetail.getGroupid())){
                result.put("groupid", orderDetail.getGroupid());
            }else{
                result.put("groupid", "");
            }
        }
        result.put("taxprice", taxprice);
        GoodsInfo goodsInfo = getGoodsInfoByID(goodsId);
        if(null!=goodsInfo){
            result.put("boxprice", taxprice.multiply(goodsInfo.getBoxnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
            if(null != goodsInfo.getGrossweight()){
                result.put("totalboxweight", bUnitNum.multiply(goodsInfo.getGrossweight()).setScale(6, BigDecimal.ROUND_HALF_UP));
            }else{
                result.put("totalboxweight","0.000000");
            }
            if(null != goodsInfo.getSinglevolume()){
                result.put("totalboxvolume", bUnitNum.multiply(goodsInfo.getSinglevolume()).setScale(6, BigDecimal.ROUND_HALF_UP));
            }else{
                result.put("totalboxvolume","0.000000");
            }

        }
        result.put("totalbox", orderDetail.getTotalbox());
        result.put("auxnum", orderDetail.getAuxnum());
        result.put("overnum", orderDetail.getOvernum());
        result.put("auxnumdetail", orderDetail.getAuxnumdetail());
        result.put("unitnum", bUnitNum);

        TaxType taxType = getTaxType(orderDetail.getTaxtype());

        BigDecimal bTaxPrice = taxprice;
        if(null==bTaxPrice){
            bTaxPrice = BigDecimal.ZERO;
        }
        if(null==bUnitNum){
            bUnitNum = BigDecimal.ZERO;
        }
        BigDecimal bTaxAmount = new BigDecimal(0); //含税金额
        BigDecimal bNoTaxAmount = new BigDecimal(0); //无税金额
        BigDecimal bTax = new BigDecimal(0); //税额
        //获取小数位
        int decimalLen = getAmountDecimalsLength();
        bTaxAmount = bTaxPrice.multiply(bUnitNum).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
        BigDecimal bTax1 = new BigDecimal(0);
        if(taxType != null){
            bTax1 = computeTax(1, bTaxPrice, taxType.getRate().divide(new BigDecimal(100)));
            bTax = bTax1.multiply(bUnitNum);
        }
        bNoTaxAmount = getNotaxAmountByTaxAmount(bTaxAmount, orderDetail.getTaxtype());
        BigDecimal notaxprice = BigDecimal.ZERO;
        if(null!=bNoTaxAmount && bNoTaxAmount.compareTo(BigDecimal.ZERO)!=0){
            notaxprice = bNoTaxAmount.divide(bUnitNum, 6,BigDecimal.ROUND_HALF_UP);
        }
        result.put("taxamount", bTaxAmount);
        result.put("notaxamount", bNoTaxAmount);
        result.put("notaxprice", notaxprice);
        result.put("tax", bTax.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
        return result;
    }

    /**
     * 获取符合要货单的订货单
     * @param pageMap
     * @return com.hd.agent.common.util.PageData
     * @throws
     * @author luoqiang
     * @date Oct 18, 2017
     */
    @Override
    public PageData getDemandAddOrderGoodsData(PageMap pageMap) throws Exception {
        List<OrderGoods> orderList=getOrderGoodsMapper().getDemandAddOrderGoodsList(pageMap);
        for (OrderGoods order : orderList) {
            Map amountmap=getOrderGoodsMapper().getOrderGoodsAmount(order.getId());
            BigDecimal ordertaxamount=(BigDecimal)amountmap.get("ordertaxamount");
            BigDecimal ordernotaxamount=(BigDecimal)amountmap.get("ordernotaxamount");
            BigDecimal notordertaxamount=(BigDecimal)amountmap.get("notordertaxamount");
            BigDecimal notordernotaxamount=(BigDecimal)amountmap.get("notordernotaxamount");
            order.setOrdertaxamount(ordertaxamount);
            order.setOrdernotaxamount(ordernotaxamount);
            order.setNotordertaxamount(notordertaxamount);
            order.setNotordernotaxamount(notordernotaxamount);
            Personnel indoorPerson = getPersonnelById(order.getIndooruserid());
            if (null != indoorPerson) {
                order.setIndoorusername(indoorPerson.getName());
            }
            DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(order.getSalesdept());
            if (departMent != null) {
                order.setSalesdept(departMent.getName());
            }
            Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(order.getSalesuser());
            if (personnel != null) {
                order.setSalesuser(personnel.getName());
            }
            Map map = new HashMap();
            map.put("id", order.getCustomerid());
            Customer customer = getCustomerByID(order.getCustomerid());
            if (customer != null) {
                order.setCustomerid(customer.getId());
                order.setCustomername(customer.getName());
                order.setAddress(customer.getAddress());
            }
            map.put("id", order.getHandlerid());
            Contacter contacter = getBaseFilesContacterMapper().getContacterDetail(map);
            if (contacter != null) {
                order.setHandlerid(contacter.getName());
            }
            Map total = getOrderGoodsMapper().getOrderGoodsDetailTotal(order.getId());
            if (total != null) {
                if (total.containsKey("taxamount")) {
                    order.setField01(total.get("taxamount").toString());
                    order.setField04(total.get("taxamount").toString());
                }
                if (total.containsKey("notaxamount")) {
                    order.setField02(total.get("notaxamount").toString());
                }
                if (total.containsKey("tax")) {
                    order.setField03(total.get("tax").toString());
                }

            }
            SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(order.getStatus(), "status");
            if (sysCode != null) {
                order.setStatusname(sysCode.getCodename());
            }
        }
        int count=getOrderGoodsMapper().getDemandAddOrderGoodsCount(pageMap);
        PageData pageData=new PageData(count,orderList,pageMap);
        return pageData;
    }

    @Override
    public List getSalesOrderGoodsListBy(Map map) throws Exception {
        String showPCustomerName=(String)map.get("showPCustomerName");
        List<OrderGoods> list = getOrderGoodsMapper().getSalesOrderGoodsListBy(map);
        Customer pCustomer =null;
        String showDetailListType=(String) map.get("showDetailListType");
        for (OrderGoods item : list) {
            if(StringUtils.isNotEmpty(item.getIndooruserid())) {
                Personnel indoorPerson = getPersonnelById(item.getIndooruserid());
                if (null != indoorPerson) {
                    item.setIndoorusername(indoorPerson.getName());
                }
            }
            if(StringUtils.isNotEmpty(item.getSalesdept())) {
                DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(item.getSalesdept());
                if (departMent != null) {
                    //20151229 为了打印转换为Saleout
                    item.setSalesdeptname(departMent.getName());
                }
            }
            if(StringUtils.isNotEmpty(item.getSalesuser())) {
                Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(item.getSalesuser());
                if (personnel != null) {
                    //20151229 为了打印转换为Saleout
                    item.setSalesusername(personnel.getName());
                }
            }
            Map queryMap = new HashMap();
            if(StringUtils.isNotEmpty(item.getCustomerid())) {
                queryMap.put("id", item.getCustomerid());
                Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(queryMap);
                if (null != customer) {
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
                    //20151229 为了打印转换为Saleout
                    item.setHandlername(contacter.getName());
                }
            }

            List<OrderGoodsDetail> detailList=null;
            if("print".equals(showDetailListType)){
                Map detailMap=new HashMap();

//                if (null != item && "1".equals(item.getIsgoodsseq())) {
//                    detailMap.put("orderseq", "goodsid");
//                }
                detailMap.put("orderid", item.getId());
                detailList=getOrderGoodsMapper().getOrderGoodsDetailList(item.getId(),"goodsid");
                for(OrderGoodsDetail orderDetail:detailList){

                    GoodsInfo Info = getGoodsInfoByID(orderDetail.getGoodsid());

                    TaxType taxType = getTaxType(orderDetail.getTaxtype());
                    if (taxType != null) {
                        orderDetail.setTaxtypename(taxType.getName());
                    }
                    StorageInfo storageInfo = getStorageInfoByID(orderDetail.getStorageid());
                    if (storageInfo != null) {
                        orderDetail.setStoragename(storageInfo.getName());
                    }

                    GoodsInfo orgGoodsInfo = getAllGoodsInfoByID(orderDetail.getGoodsid());
                    if (null != orgGoodsInfo) {
                        orderDetail.setBoxprice(orderDetail.getTaxprice().multiply(orgGoodsInfo.getBoxnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                        orderDetail.setLowestsaleprice(orgGoodsInfo.getLowestsaleprice());
                        //折扣显示处理
                        GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(orgGoodsInfo);
                        if ("1".equals(orderDetail.getIsdiscount())) {
                            goodsInfo.setBarcode(null);
                            goodsInfo.setBoxnum(null);
                            orderDetail.setUnitnum(null);
                            orderDetail.setUsablenum(null);
                            orderDetail.setAuxnumdetail(null);
                            orderDetail.setTaxprice(null);
                            if ("1".equals(orderDetail.getIsbranddiscount())) {
                                orderDetail.setGoodsid("");
                                goodsInfo.setName(goodsInfo.getBrandName());
                                orderDetail.setIsdiscount("2");
                            }
                        }
                        orderDetail.setGoodsInfo(goodsInfo);
                    }
                }
                Map paramMap=new HashMap();
                paramMap.put("showShopid", "true");
                paramMap.put("customerid", item.getCustomerid());
//                fillInOrderDetailInfo(detailList, paramMap);
                item.setOrderDetailList(detailList);
            }
        }
        return list;
    }
    /**
     * 更新打印次数
     * @param order
     * @author zhanghonghui
     * @date 2013-9-10
     */
    public boolean updateOrderGoodsPrinttimes(OrderGoods order) throws Exception{
        return getOrderGoodsMapper().updateOrderGoodsPrinttimes(order)>0;
    }
    @Override
    public Map doInvalidOrderGoods(String id, String type) throws Exception {
        Map result = new HashMap();
       OrderGoods orderGoods=getOrderGoods(id);
        boolean flag = false;
        String msg = "";
        if (orderGoods == null) {
            result.put("flag", flag);
            result.put("msg", "订货单不存在");
            return result;
        }
        if ("1".equals(type)) {
            if (!"2".equals(orderGoods.getStatus()) && !"3".equals(orderGoods.getStatus())) {
                msg = "非保存和审核通过状态订货单不可作废";
            } else {
                OrderGoods ordergoods2 = new OrderGoods();
                ordergoods2.setId(id);
                ordergoods2.setStatus("5");
                flag = getOrderGoodsMapper().updateOrderGoods(ordergoods2) > 0;
            }
        } else if ("2".equals(type)) {
            if (!"5".equals(orderGoods.getStatus())) {
                msg = "非作废状态订单不可取消作废";
            } else {
                OrderGoods ordergoods2 = new OrderGoods();
                ordergoods2.setId(id);
                ordergoods2.setStatus("2");
                flag = getOrderGoodsMapper().updateOrderGoods(ordergoods2)>0;
            }
        }
        result.put("flag", flag);
        result.put("msg", msg);
        return result;
    }
    /**
     * 获取销售订货单导出数据
     * @param
     * @return com.hd.agent.common.util.PageData
     * @throws
     * @author luoqiang
     * @date Jan 05, 2018
     */
    public List getOrderGoodsExportData(PageMap pageMap) throws Exception{
        String dataSql = getAccessColumnList("t_sales_goodsorder", "t");
        pageMap.setDataSql(dataSql);
        pageMap.setQueryAlias("t");
        List<ExportOrderGoods> list = getOrderGoodsMapper().getExportOrderGoodsData(pageMap);
        if(list.size() != 0){
            for(ExportOrderGoods goods : list){
                Customer customer = getBaseCustomerMapper().getCustomerInfo(goods.getCustomerid());
                if(null != customer){
                    goods.setCustomername(customer.getName());
                }
                DepartMent departMent = getDepartmentByDeptid(goods.getSalesdept());
                if(null != departMent){
                    goods.setSalesdeptname(departMent.getName());
                }
                GoodsInfo goodsInfo = getBaseGoodsMapper().getGoodsInfo(goods.getGoodsid());
                if(null != goodsInfo){
                    //获取商品箱装量
                    List<GoodsInfo_MteringUnitInfo> muInfo = getBaseGoodsMapper().getMUListByGoodsId(goods.getGoodsid()); //获取商品的辅助计量单位列表
                    if(muInfo.size() > 0 ){
                        goods.setBoxnum(muInfo.get(0).getRate());
                    }else if(null != goodsInfo.getBoxnum()){
                        goods.setBoxnum(goodsInfo.getBoxnum());
                    }

                    goods.setGoodsname(goodsInfo.getName());
                    goods.setModel(goodsInfo.getModel());
                    goods.setBarcode(goodsInfo.getBarcode());
                    goods.setSpell(goodsInfo.getSpell());
                    BigDecimal unitnum = goods.getUnitnum();
                    if(null != goodsInfo.getSinglevolume()){
                        BigDecimal volume = unitnum.multiply(goodsInfo.getSinglevolume()).setScale(6,BigDecimal.ROUND_HALF_UP);
                        goods.setVolume(volume.toString());
                    }else{
                        goods.setVolume("0.000000");
                    }
                    if(null != goodsInfo.getGrossweight()){
                        BigDecimal grossweight = unitnum.multiply(goodsInfo.getGrossweight()).setScale(6, BigDecimal.ROUND_HALF_UP);
                        goods.setGrossweight(grossweight.toString());
                    }else{
                        goods.setGrossweight("0.000000");
                    }
                }
                Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(goods.getSalesuser());
                if(null != personnel){
                    goods.setSalesusername(personnel.getName());
                }
                SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(goods.getStatus(), "status");
                if (sysCode != null) {
                    goods.setStatusname(sysCode.getCodename());
                }
            }
        }
        return list;
    }

    /**
     * 判断订货单是否关联过销售订单或要货单
     * @param id
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Mar 14, 2018
     */
    public Map checkInvalidOrderGoods(String id){
        Boolean flag=false;
        String msg="";
        Map result=new HashMap();
        //订货单是否关联过订单
        String orderids=getOrderGoodsMapper().getOrderGoodsInOrder(id);
        if (StringUtils.isEmpty(orderids)) {
            //订货单是否关联过要货单
            String demandids=getSalesDemandMapper().getOrderGoodsInDemand(id);
            if(StringUtils.isNotEmpty(demandids)){
                msg="该订货单已经关联要货单"+demandids+"，不能作废";
                flag=true;
            }
        }else{
            msg="该订货单已经关联销售订单"+orderids+"，不能作废";
            flag=true;
        }
        result.put("flag",flag);
        result.put("msg",msg);
        return result;
    }

    /**
     * 根据主键编码获取订货单明细数据
     * @param id
     * @return com.hd.agent.sales.model.OrderGoodsDetail
     * @throws
     * @author luoqiang
     * @date Mar 30, 2018
     */
    public OrderGoodsDetail getOrderGoodsDetail(int id){
        OrderGoodsDetail orderGoodsDetail=getOrderGoodsMapper().getOrderGoodsDetail(id);
        return orderGoodsDetail;
    }
}
