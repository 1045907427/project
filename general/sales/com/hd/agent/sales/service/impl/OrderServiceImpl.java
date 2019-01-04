/**
 * @(#)OrderServiceImpl.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 10, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.dao.CustomerMapper;
import com.hd.agent.basefiles.dao.DistributionRuleMapper;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.*;
import com.hd.agent.sales.service.IOrderService;
import com.hd.agent.sales.service.ext.IDispatchBillExtService;
import com.hd.agent.storage.model.StorageSummary;
import com.hd.agent.storage.model.StorageSummaryBatch;
import com.hd.agent.storage.service.IStorageForSalesService;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.model.SysParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 销售订单service
 *
 * @author zhengziyong
 */
public class OrderServiceImpl extends BaseSalesServiceImpl implements IOrderService {

    private IDispatchBillExtService salesDispatchBillExtService;
    private CustomerMapper customerMapper;

    private IStorageForSalesService storageForSalesService;

    private DistributionRuleMapper distributionRuleMapper;


    public CustomerMapper getCustomerMapper() {
        return customerMapper;
    }

    public void setCustomerMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    public IDispatchBillExtService getSalesDispatchBillExtService() {
        return salesDispatchBillExtService;
    }

    public void setSalesDispatchBillExtService(
            IDispatchBillExtService salesDispatchBillExtService) {
        this.salesDispatchBillExtService = salesDispatchBillExtService;
    }

    public IStorageForSalesService getStorageForSalesService() {
        return storageForSalesService;
    }

    public void setStorageForSalesService(
            IStorageForSalesService storageForSalesService) {
        this.storageForSalesService = storageForSalesService;
    }

    public DistributionRuleMapper getDistributionRuleMapper() {
        return distributionRuleMapper;
    }

    public void setDistributionRuleMapper(DistributionRuleMapper distributionRuleMapper) {
        this.distributionRuleMapper = distributionRuleMapper;
    }

    @Override
    public Map addOrder(Order order, String saveaudit) throws Exception {
        if (isAutoCreate("t_sales_order")) {
            // 获取自动编号
            String id = getAutoCreateSysNumbderForeign(order, "t_sales_order");
            order.setId(id);
        }else{
            String id = "XSDD-"+CommonUtils.getDataNumberSendsWithRand();
            order.setId(id);
        }
        List<OrderDetail> orderDetailList = order.getOrderDetailList();
        if (orderDetailList.size() > 0) {
            int seq = 1;
            List detailList = new ArrayList();
            List<OrderDetail> brandDiscountList = new ArrayList();
            for (OrderDetail orderDetail : orderDetailList) {
                if (orderDetail != null) {
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

                        //获取税种，税额，未税金额，未税单价
                        orderDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
                        if(null==orderDetail.getTaxamount()){
                            orderDetail.setTaxamount(BigDecimal.ZERO);
                        }
                        BigDecimal notaxanount = getNotaxAmountByTaxAmount(orderDetail.getTaxamount(),goodsInfo.getDefaulttaxtype());
                        orderDetail.setTaxamount(orderDetail.getTaxamount().setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
                        orderDetail.setNotaxamount(notaxanount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
                        orderDetail.setTax(orderDetail.getTaxamount().subtract(orderDetail.getNotaxamount()));
                        if(null!=notaxanount && null!=orderDetail.getUnitnum() && orderDetail.getUnitnum().compareTo(BigDecimal.ZERO)==1){
                            orderDetail.setNotaxprice(notaxanount.divide(orderDetail.getUnitnum(),6,BigDecimal.ROUND_HALF_UP));
                        }
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
                        detail.setNotaxamount(notaxdiscountamount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
                        detail.setTax(detail.getTaxamount().subtract(detail.getNotaxamount()));

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
        if(flag){
            addCutomerFullFreeLog(order);
        }
        Map map = new HashMap();
        if ("saveaudit".equals(saveaudit) && flag) {
            map = auditOrder("1", order.getId(), null);
            boolean auditflag = (Boolean) map.get("flag");
            map.put("auditflag", auditflag);
        }
        map.put("flag", flag);
        map.put("id",order.getId());
        return map;
    }

    @Override
    public boolean addOrderForPhone(Order order) throws Exception {
        Map map = new HashMap();
        map.put("id", order.getId());
        Order order2 = getSalesOrderMapper().getOrderDetail(map);
        if (order2 != null && StringUtils.isNotEmpty(order2.getId())) {
            return true;
        }
        List<OrderDetail> orderDetailList = order.getOrderDetailList();
        if (orderDetailList.size() > 0) {
            List detailList = new ArrayList();
            for (OrderDetail orderDetail : orderDetailList) {
                if (orderDetail != null) {
                    orderDetail.setOrderid(order.getId());
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(orderDetail.getGoodsid());
                    if (null != goodsInfo) {
                        orderDetail.setGoodssort(goodsInfo.getDefaultsort());
                        orderDetail.setBrandid(goodsInfo.getBrand());
                        orderDetail.setCostprice(getGoodsCostprice(order.getStorageid(),goodsInfo));
                        orderDetail.setFixnum(orderDetail.getUnitnum());
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

                        if (StringUtils.isNotEmpty(order.getStorageid())) {
                            orderDetail.setStorageid(order.getStorageid());
                        }
                        //获取系统设置的原价，不包括特价
                        orderDetail.setFixprice(getGoodsPriceByCustomer(orderDetail.getGoodsid(), order.getCustomerid()));
                    }
                    detailList.add(orderDetail);
                    //addOrderDetail(orderDetail);
                }
            }
            if (detailList.size() > 0) {
                //添加明细
                getSalesOrderDetailMapper().addOrderDetailList(detailList);
            }

        }
        //根据客户编号获取该客户的销售内勤用户信息
        SysUser sysUser = getSalesIndoorSysUserByCustomerid(order.getCustomerid());
        if (sysUser != null) {
            //销售内勤
            order.setIndooruserid(sysUser.getPersonnelid());
        }
        //获取销售区域上级客户
        Customer customer = getCustomerByID(order.getCustomerid());
        if (null != customer) {
            order.setSalesarea(customer.getSalesarea());
            order.setPcustomerid(customer.getPid());
            order.setCustomersort(customer.getCustomersort());
        }
        return getSalesOrderMapper().addOrder(order) > 0;
    }

    @Override
    public Map updateOrder(Order order, String saveaudit) throws Exception {
        Map idmap = new HashMap();
        BigDecimal totalboxweight = BigDecimal.ZERO;
        BigDecimal totalboxvolume = BigDecimal.ZERO;
        idmap.put("id", order.getId());
        Order oldOrder = getSalesOrderMapper().getOrderDetail(idmap);
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
        if("1".equals(isOpenOrderVersion)){
            //记录订单版本
            addOrderVersionByID(order.getId());
        }
        //删除满赠促销已记录信息
        deleteCutomerFullFreeLog(order.getId());
        getSalesOrderDetailMapper().deleteOrderDetailByOrderId(order.getId());
        List<OrderDetail> orderDetailList = order.getOrderDetailList();
        if (orderDetailList.size() > 0) {
            int seq = 0;
            int maxseq = -1 ;
            Map seqMap = new HashMap();
            List detailList = new ArrayList();
            List<OrderDetail> brandDiscountList = new ArrayList<OrderDetail>();
            for (OrderDetail orderDetail : orderDetailList) {
                if (orderDetail != null) {
                    if(null != orderDetail.getTotalboxvolume()){
                        totalboxweight = totalboxweight.add(orderDetail.getTotalboxweight());
                        totalboxvolume = totalboxvolume.add(orderDetail.getTotalboxvolume());
                    }
                    orderDetail.setIsview("1");
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
                    }
//                    if (orderDetail.getSeq() >= seq) {
//                        seq = orderDetail.getSeq();
//                        seq++;
//                    }else{
//                        ++seq;
//                    }

                    if (orderDetail.getSeq() == 0) {
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
                getSalesOrderDetailMapper().addOrderDetailList(detailList);
            }
            //品牌折扣 平摊到各商品中
            for (OrderDetail billDetail : brandDiscountList) {
                List<OrderDetail> brandGoodsList = getSalesOrderDetailMapper().getOrderDetailListByOrderidAndBrandid(order.getId(), billDetail.getBrandid());
                BigDecimal totalamount = BigDecimal.ZERO;
                BigDecimal totalunitnum = BigDecimal.ZERO;
                BigDecimal totalboxSum = BigDecimal.ZERO;
                for (OrderDetail billGoodsDetail : brandGoodsList) {
                    totalamount = totalamount.add(billGoodsDetail.getTaxamount());
                    totalunitnum = totalunitnum.add(billGoodsDetail.getUnitnum());
                    totalboxSum = totalboxSum.add(billGoodsDetail.getTotalbox());
                }
                if (null != billDetail.getTaxamount() && billDetail.getTaxamount().compareTo(BigDecimal.ZERO) != 0) {
                    BigDecimal useamount = BigDecimal.ZERO;
                    for (int i = 0; i < brandGoodsList.size(); i++) {
                        OrderDetail billGoodsDetail = brandGoodsList.get(i);
                        OrderDetail detail = new OrderDetail();
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
                        detail.setTaxamount(discountamount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                        detail.setNotaxamount(notaxdiscountamount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                        detail.setTax(detail.getTaxamount().subtract(detail.getNotaxamount()));

                        getSalesOrderDetailMapper().addOrderDetail(detail);
                    }
                }
            }
        }
        List<OrderDetail> lackGoodsList = order.getLackList();
        if (null != lackGoodsList && lackGoodsList.size() > 0) {
            int seq = 0;
            if (null != orderDetailList) {
                seq = orderDetailList.size() + 1;
            }
            List detailList = new ArrayList();
            for (OrderDetail orderDetail : lackGoodsList) {
                if (orderDetail != null) {
//                    totalboxweight = totalboxweight.add(orderDetail.getTotalboxweight());
//                    totalboxvolume = totalboxvolume.add(orderDetail.getTotalboxvolume());
                    orderDetail.setUnitnum(BigDecimal.ZERO);
                    orderDetail.setIsview("0");
                    orderDetail.setOrderid(order.getId());
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
            getSalesOrderDetailMapper().addOrderDetailList(lackGoodsList);
        }
        //订单单据中添加总重量和总体积
        order.setTotalboxvolume(totalboxvolume);
        order.setTotalboxweight(totalboxweight);
        boolean flag = getSalesOrderMapper().updateOrder(order) > 0;
        boolean validateFlag = false ;
        if(flag){
            addCutomerFullFreeLog(order);
            if("3".equals(oldOrder.getSourcetype())){
                //回写关联的销售订货单的已生成未生成数量,金额
                updateOrderGoodsDetail(oldOrder.getId(),oldOrder.getSourceid());
            }
            if("1".equals(oldOrder.getSourcetype())){
                Demand demand=getSalesDemandMapper().getDemand(oldOrder.getSourceid());
                //判断是否关联过订货单
                if(StringUtils.isNotEmpty(demand.getOrdergoodsid())){
                    //回写关联的销售订货单的已生成未生成数量,金额
                    updateDemandOrderGoodsDetail(oldOrder.getId(),demand.getOrdergoodsid(),oldOrder.getSourceid());
                }
            }
        }
        if(StringUtils.isNotEmpty(order.getSourceid())){
            int a = getSalesOrderMapper().validateSourceId(order.getSourceid(),order.getBusinessdate()) ;
            validateFlag = a > 1 ;
        }
        Map map = new HashMap();
        if ("saveaudit".equals(saveaudit) && flag) {
            map = auditOrder("1", order.getId(), null);
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

    @Override
    public boolean updateOrderStatus(Order order) throws Exception {
        return getSalesOrderMapper().updateOrderStatus(order) > 0;
    }

    @Override
    public boolean updateOrderRefer(String isrefer, String id) throws Exception {
        return getSalesOrderMapper().updateOrderRefer(isrefer, id) > 0;
    }

    @Override
    public boolean deleteOrder(String id) throws Exception {
        Map idmap = new HashMap();
        idmap.put("id", id);
        Order oldOrder = getSalesOrderMapper().getOrderDetail(idmap);
        if(null==oldOrder || "3".equals(oldOrder.getStatus()) || "4".equals(oldOrder.getStatus())){
            return false;
        }
        //销售订货单生成的删除时回写订货单商品发货数量
        if("3".equals(oldOrder.getSourcetype())){
            int t=getOrderGoodsMapper().updateOrderGoodsDetailNum(id,"2",oldOrder.getSourceid());
            if(t>0){
                //删除订货单和销售订单的关联关系
                getOrderGoodsMapper().deleteRelationOrderGoods(id);
            }
        }
        if("1".equals(oldOrder.getSourcetype())&&StringUtils.isNotEmpty(oldOrder.getSourceid())){
            updateDemandGoodsDetailNum(oldOrder.getSourceid());
        }
        deleteCutomerFullFreeLog(id);
        getSalesOrderDetailMapper().deleteOrderDetailByOrderId(id);
        boolean flag = getSalesOrderMapper().deleteOrder(id)>0;
        return flag;

    }

    @Override
    public Map doInvalidOrder(String id, String type) throws Exception {
        Map result = new HashMap();
        Order order = getOnlyOrder(id);
        boolean flag = false;
        String msg = "";
        if (order == null) {
            result.put("flag", flag);
            result.put("msg", msg);
            return result;
        }
        if ("1".equals(type)) {
            if (!"2".equals(order.getStatus())) {
                msg = "非保存状态订单不可作废";
            } else {
                SysUser sysUser = getSysUser();
                Order order2 = new Order();
                order2.setId(id);
                order2.setStatus("5");
                flag = updateOrderStatus(order2);
            }
        } else if ("2".equals(type)) {
            if (!"5".equals(order.getStatus())) {
                msg = "非作废状态订单不可取消作废";
            } else {
                SysUser sysUser = getSysUser();
                Order order2 = new Order();
                order2.setId(id);
                order2.setStatus("2");
                flag = updateOrderStatus(order2);
            }
        }
        result.put("flag", flag);
        result.put("msg", msg);
        return result;
    }

    @Override
    public Map doBatchInvalidOrder(String ids) throws Exception {
        Map retmap = new HashMap();
        String retmsg = "",unsucids = "",sucids = "",undoids = "",undomsg = "";
        String msg2 = "";
        int unsucnum = 0,sucnum = 0;
        if(StringUtils.isNotEmpty(ids)){
            String[] idArr = ids.split(",");
            for(String id : idArr){
                Map map = doInvalidOrder(id,"1");
                String msg = map.get("msg") != null ? (String)map.get("msg") : "";
                boolean flag = map.get("flag").equals(true);
                if(StringUtils.isEmpty(msg)){
                    if(!flag){
                        if(StringUtils.isEmpty(unsucids)){
                            unsucids = id;
                        }else{
                            unsucids += "," + id;
                        }
                        unsucnum++;
                    }else{
                        if(StringUtils.isEmpty(sucids)){
                            sucids = id;
                        }else{
                            sucids += "," + id;
                        }
                        sucnum++;
                    }
                }else{
                    if(StringUtils.isEmpty(undoids)){
                        undoids = id;
                    }else{
                        undoids += "," + id;
                    }
                    if(StringUtils.isEmpty(msg2)){
                        msg2 = msg;
                    }
                }
            }
        }
        if(StringUtils.isNotEmpty(unsucids)){
            retmsg = "销售订单编号："+unsucids+"作废失败！";
        }
        if(StringUtils.isNotEmpty(sucids)){
            if(StringUtils.isEmpty(retmsg)){
                retmsg = "销售订单编号："+sucids+"作废成功！";
            }else{
                retmsg += "<br>" + "销售订单编号："+sucids+"作废成功！";
            }
        }
        if(StringUtils.isNotEmpty(undoids)){
            undomsg = "销售订单编号："+undoids+msg2+"；";
        }
        retmap.put("unsucnum",unsucnum);
        retmap.put("sucnum",sucnum);
        retmap.put("msg",retmsg);
        retmap.put("undomsg",undomsg);
        return retmap;
    }

    @Override
    public Order getOrder(String id) throws Exception {
        Map map = getAccessColumnMap("t_sales_order", null);
        map.put("id", id);
        Order order = getSalesOrderMapper().getOrderDetail(map);
        if(null != order){
            Customer customer = getCustomerByID(order.getCustomerid());
            if (customer != null) {
                order.setCustomername(customer.getName());
            }
            String orderStr = null;
            if (null != order && "1".equals(order.getIsgoodsseq())) {
                orderStr = "goodsid";
            }
            List lackList = getSalesOrderDetailMapper().getOrderDetailLackList(id);
            if (null != lackList) {
                order.setLackList(lackList);
            }
            List<OrderDetail> orderDetaiList = getSalesOrderDetailMapper().getOrderDetailByOrderWithDiscount(id, orderStr);
            //商品库存缓存 防止同一张单多条商品 可用量问题
            Map storageGoodsMap = new HashMap();
            for (OrderDetail orderDetail : orderDetaiList) {
                CustomerPrice customerPrice = getCustomerPrice(order.getCustomerid(),orderDetail.getGoodsid());
                if(null!=customerPrice){
                    orderDetail.setShopid(customerPrice.getShopid());
                }

                if("2".equals(order.getStatus())) {
                    CustomerGoods customerGoods = customerMapper.getCustomerGoodsByCustomerAndGoodsid(order.getCustomerid(), orderDetail.getGoodsid());
                    if (customerGoods == null) {
                        orderDetail.setIsnew("1");
                    }
                }

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

    @Override
    public Order getOrderByCopy(String id) throws Exception {
        Map map = getAccessColumnMap("t_sales_order", null);
        map.put("id", id);
        Order order = getSalesOrderMapper().getOrderDetail(map);
        Customer customer = getCustomerByID(order.getCustomerid());
        if (customer != null) {
            order.setCustomername(customer.getName());
        }
        String orderStr = null;
        if (null != order && "1".equals(order.getIsgoodsseq())) {
            orderStr = "goodsid";
        }
        List<OrderDetail> orderDetaiList = getSalesOrderDetailMapper().getOrderDetailByOrderWithoutDiscount(id, orderStr);
        for (OrderDetail orderDetail : orderDetaiList) {
            TaxType taxType = getTaxType(orderDetail.getTaxtype());
            if (taxType != null) {
                orderDetail.setTaxtypename(taxType.getName());
            }
            StorageInfo storageInfo = getStorageInfoByID(orderDetail.getStorageid());
            if (storageInfo != null) {
                orderDetail.setStoragename(storageInfo.getName());
            }

            //当销售发货通知单处于保存状态时 判断仓库中数量是否足够
//			boolean flag = storageForSalesService.isGoodsEnoughByDispatchBillDetail(billDetail);
            StorageSummary storageSummary = null;
            if (null != orderDetail.getStorageid() && !"".equals(orderDetail.getStorageid())) {
                storageSummary = storageForSalesService.getStorageSummarySumByGoodsidAndStorageid(orderDetail.getGoodsid(), orderDetail.getStorageid());
            } else {
                storageSummary = storageForSalesService.getStorageSummarySumByGoodsid(orderDetail.getGoodsid());
            }
            if (null != storageSummary && storageSummary.getUsablenum().compareTo(orderDetail.getUnitnum()) != -1) {
                orderDetail.setIsenough("1");
            } else {
                orderDetail.setIsenough("0");
            }
            if (null != storageSummary) {
                orderDetail.setUsablenum(storageSummary.getUsablenum());
            } else {
                orderDetail.setUsablenum(BigDecimal.ZERO);
            }
            GoodsInfo orgGoodsInfo = getAllGoodsInfoByID(orderDetail.getGoodsid());
            if (null != orgGoodsInfo) {
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
                if(null != goodsInfo.getGrossweight()){
                    BigDecimal goodsBoxweight = orderDetail.getUnitnum().multiply(goodsInfo.getGrossweight()).setScale(6, BigDecimal.ROUND_HALF_UP);
                    orderDetail.setTotalboxweight(goodsBoxweight);
                }
                if(null != goodsInfo.getSinglevolume()){
                    BigDecimal goodsboxvolume = orderDetail.getUnitnum().multiply(goodsInfo.getSinglevolume()).setScale(6, BigDecimal.ROUND_HALF_UP);
                    orderDetail.setTotalboxvolume(goodsboxvolume);
                }
                orderDetail.setBasesaleprice(goodsInfo.getBasesaleprice());
                orderDetail.setGoodsInfo(goodsInfo);
                orderDetail.setBuyprice(goodsInfo.getHighestbuyprice());
                orderDetail.setBoxprice(orgGoodsInfo.getBoxnum().multiply(orderDetail.getFixprice()));
            }
        }
        order.setOrderDetailList(orderDetaiList);
        return order;
    }

    @Override
    public Order getOnlyOrder(String id) throws Exception {
        Map map = getAccessColumnMap("t_sales_order", null);
        map.put("id", id);
        Order order = getSalesOrderMapper().getOrderDetail(map);
        return order;
    }

    @Override
    public List getDetailListOrder(String id) throws Exception {
        Map map = new HashMap();
        map.put("id", id);
        Order order = getSalesOrderMapper().getOrderDetail(map);
        Customer customer = getCustomerByID(order.getCustomerid());
        if (customer != null) {
            order.setCustomername(customer.getName());
        }
        String orderStr = null;
        if (null != order && "1".equals(order.getIsgoodsseq())) {
            orderStr = "goodsid";
        }
        List<OrderDetail> orderDetaiList = getSalesOrderDetailMapper().getOrderDetailByOrder(id, orderStr);
        for (OrderDetail orderDetail : orderDetaiList) {
            GoodsInfo goodsInfo = getGoodsInfoByID(orderDetail.getGoodsid());
            orderDetail.setGoodsInfo(goodsInfo);
        }
        return orderDetaiList;
    }

    @Override
    public PageData getOrderData(PageMap pageMap) throws Exception {
        String sql = getDataAccessRule("t_sales_order", null); //数据权限
        pageMap.setDataSql(sql);
        List<Order> orderList = getSalesOrderMapper().getOrderList(pageMap);
        for (Order order : orderList) {
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
            Map total = getSalesOrderDetailMapper().getOrderDetailTotal(order.getId());
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
        return new PageData(getSalesOrderMapper().getOrderCount(pageMap), orderList, pageMap);
    }

    @Override
    public boolean addOrderDetail(OrderDetail detail) throws Exception {
        return getSalesOrderDetailMapper().addOrderDetail(detail) > 0;
    }

    @Override
    public OrderDetail getFixGoodsDetail(String goodsId, String customerId) throws Exception {
        OrderDetail orderDetail = new OrderDetail();
        GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsId); //商品信息
        if (goodsInfo != null) {
            BigDecimal rate = BigDecimal.ZERO;
            TaxType taxType = getTaxType(goodsInfo.getDefaulttaxtype()); //获取默认税种
            if (taxType != null) {
                orderDetail.setTaxtype(taxType.getId()); //税种档案中的编码
                orderDetail.setTaxtypename(taxType.getName()); //税种名称
                rate = taxType.getRate().divide(new BigDecimal(100));
            }

            String salesPriceRule = getSysParamValue("SalesPriceRule");
            BigDecimal lastestPrice = getLastestPriceByCustomerGoods(customerId, goodsId, salesPriceRule);
            if (lastestPrice != null && BigDecimal.ZERO.compareTo(lastestPrice) < 0) {
                orderDetail.setFixprice(lastestPrice);
                orderDetail.setTaxprice(lastestPrice);
                orderDetail.setNotaxprice(lastestPrice.divide(rate.add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP));
                orderDetail.setRemark("");
            } else {

                CustomerPrice customerPrice = getCustomerPrice(customerId, goodsId);
                if (customerPrice != null) { //取合同价
                    BigDecimal customerTaxPrice = customerPrice.getPrice();
                    if (customerTaxPrice == null) {
                        customerTaxPrice = new BigDecimal(0);
                    }
                    orderDetail.setFixprice(customerTaxPrice);
                    orderDetail.setTaxprice(customerTaxPrice);
                    orderDetail.setNotaxprice(customerTaxPrice.divide(rate.add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP));
                    orderDetail.setRemark("");
                } else {
                    GoodsInfo_PriceInfo priceInfo = getPriceInfo(goodsId, customerId); //客户的价格套信息
                    if (priceInfo != null) { //如果客户设置了价格套信息，则取价格套信息中的价格
                        orderDetail.setFixprice(priceInfo.getTaxprice());
                        orderDetail.setTaxprice(priceInfo.getTaxprice()); //从价格套中取含税价格
                        orderDetail.setNotaxprice(priceInfo.getTaxprice().divide(rate.add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP)); //从价格套中取无税价格
                        orderDetail.setRemark("");
                    } else {
                        BigDecimal baseTaxPrice = goodsInfo.getBasesaleprice(); //取基准销售价
                        if (baseTaxPrice == null) {
                            baseTaxPrice = new BigDecimal(0);
                        }
                        orderDetail.setFixprice(baseTaxPrice);
                        orderDetail.setTaxprice(baseTaxPrice);
                        orderDetail.setNotaxprice(baseTaxPrice.divide(rate.add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP));
                        orderDetail.setRemark("");
                    }
                }

            }
            orderDetail.setGoodsInfo(goodsInfo);
        }
        return orderDetail;
    }


    @Override
    public OrderDetail getGoodsDetail(String goodsId, String customerId, String businessDate, BigDecimal num, String type) throws Exception {
        OrderDetail orderDetail = new OrderDetail();
        GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsId); //商品信息
        if (goodsInfo != null) {
            BigDecimal rate = new BigDecimal(1);
            TaxType taxType = getTaxType(goodsInfo.getDefaulttaxtype()); //获取默认税种
            if (taxType != null) {
                orderDetail.setTaxtype(taxType.getId()); //税种档案中的编码
                orderDetail.setTaxtypename(taxType.getName()); //税种名称
                rate = taxType.getRate().divide(new BigDecimal(100));
            }
            if (StringUtils.isEmpty(businessDate)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                businessDate = dateFormat.format(new Date());
            }
            OffpriceDetail offpriceDetail = null;
            Map priceMap = new HashMap();
            priceMap.put("today", businessDate);
            priceMap.put("goodsid", goodsId);
            priceMap.put("customerid", customerId);
            priceMap.put("num", num);
            boolean hasOffprice = false;
            if (!"reject".equals(type)) {
                //取特价信息
                Customer customer = getCustomerByID(customerId);
                //1单一客户2促销分类3分类客户4价格套5销售区域7信用等级6总店客户
                priceMap.put("type", "1"); //TODO 1:单一客户
                priceMap.put("customerid", customerId);
                if (customer != null) {
                    //促销分类
                    String csort = customer.getPromotionsort();
                    if (StringUtils.isNotEmpty(csort)) {
                        priceMap.put("promotionsort", csort);
                    }
                    //取客户的默认分类
                    String customerSort = customer.getCustomersort();
                    if (StringUtils.isNotEmpty(customerSort)) {
                        priceMap.put("customersort", customerSort);
                    }
                    //取客户价格套
                    String priceSort = customer.getPricesort();
                    if (StringUtils.isNotEmpty(priceSort)) {
                        priceMap.put("pricesort", priceSort);
                    }
                    //取客户所属销售区域
                    String saleArea = customer.getSalesarea();
                    if (StringUtils.isNotEmpty(saleArea)) {
                        priceMap.put("salesarea", saleArea);
                    }
                    //取客户 总店
                    String pcustomerid = customer.getPid();
                    if (StringUtils.isNotEmpty(pcustomerid)) {
                        priceMap.put("pcustomerid", pcustomerid);
                    }
                    //取信用等级
                    String credit = customer.getCreditrating();
                    if (StringUtils.isNotEmpty(credit)) {
                        priceMap.put("credit", credit);
                    }
                    //取核销方式
                    String canceltype = customer.getCanceltype();
                    if (StringUtils.isNotEmpty(canceltype)) {
                        priceMap.put("canceltype", canceltype);
                    }
                    //获取特价信息
                    offpriceDetail = getSalesOffpriceMapper().getOffpriceByCustomerGoodsNum(priceMap);
                    if (null != offpriceDetail) {
                        hasOffprice = true;
                    }
                }
            }
            //客户退货 商品价格取最近一次
            //是否需要继续取价格
            boolean rejectPriceFlag = true;
            //客户退货时，商品价格取价方式 1取最近一次销售价2取最近一段时间的最低销售价
            String salesRejectCustomerGoodsPrice = getSysParamValue("SalesRejectCustomerGoodsPrice");
            if ("reject".equals(type)) {
                //取最近一次销售价
                if("1".equals(salesRejectCustomerGoodsPrice)){
                    Map lastGoodsMap = getSalesOrderMapper().getCustomerGoodsInfoByLast(customerId, goodsId);
                    if (null != lastGoodsMap) {
                        BigDecimal taxprice = (BigDecimal) lastGoodsMap.get("taxprice");
                        BigDecimal notaxprice = (BigDecimal) lastGoodsMap.get("notaxprice");
                        orderDetail.setFixprice(taxprice);
                        orderDetail.setTaxprice(taxprice);
                        orderDetail.setNotaxprice(notaxprice);
                        rejectPriceFlag = false;
                    }
                }else if("2".equals(salesRejectCustomerGoodsPrice)){
                    //取最近一段时间内的最低销售价
                    String rejectCustomerGoodsPriceInMonth = getSysParamValue("RejectCustomerGoodsPriceInMonth");
                    int month = 3;
                    if(StringUtils.isNotEmpty(rejectCustomerGoodsPriceInMonth)){
                        month = Integer.parseInt(rejectCustomerGoodsPriceInMonth);
                    }
                    String date = CommonUtils.getBeforeDateInMonth(month);
                    Map lastGoodsMap = getSalesOrderMapper().getCustomerGoodsInfoByLowest(customerId, goodsId,date);
                    if (null != lastGoodsMap) {
                        BigDecimal taxprice = (BigDecimal) lastGoodsMap.get("taxprice");
                        BigDecimal notaxprice = (BigDecimal) lastGoodsMap.get("notaxprice");
                        orderDetail.setFixprice(taxprice);
                        orderDetail.setTaxprice(taxprice);
                        orderDetail.setNotaxprice(notaxprice);
                        rejectPriceFlag = false;
                    }
                }

            }
            //判断是否退货取上次退货价格
            if (rejectPriceFlag) {
                //获取客户商品的合同价或者价格套数据
                OrderDetail fixOrderDetail = getFixGoodsDetail(goodsId, customerId);
                //判断是否有特价
                if (!hasOffprice) {
                    //获取客户商品的合同价或者价格套数据
//                    orderDetail = fixOrderDetail;
                    orderDetail.setFixprice(fixOrderDetail.getFixprice());
                    orderDetail.setTaxprice(fixOrderDetail.getTaxprice());
                    orderDetail.setNotaxprice(fixOrderDetail.getNotaxprice());
                    orderDetail.setRemark("");
                } else {
                    String begindate = CommonUtils.dateStringChange(offpriceDetail.getField01(), "MM-dd");
                    String enddate = CommonUtils.dateStringChange(offpriceDetail.getField02(), "MM-dd");
                    //特价单据号
                    orderDetail.setGroupid(offpriceDetail.getBillid());
                    //原价（系统设置的价格合同价或者价格套价格）
                    orderDetail.setFixprice(fixOrderDetail.getTaxprice());
                    orderDetail.setOffprice(offpriceDetail.getOffprice());
                    orderDetail.setTaxprice(offpriceDetail.getOffprice());
                    orderDetail.setNotaxprice(offpriceDetail.getOffprice().divide(rate.add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP));
                    orderDetail.setRemark("特价：" + begindate + "至" + enddate);
                }
            }
            //商品客户价格套价格 ---》 参考价
            BigDecimal referenceprice = getGoodsPriceSetByCustomer(goodsId,customerId);
            orderDetail.setReferenceprice(referenceprice);

            List<GoodsInfo_MteringUnitInfo> list = getBaseFilesGoodsMapper().getMUListByGoodsId(goodsId);
            GoodsInfo_MteringUnitInfo auxUnitInfo = null; //默认辅计量
            for (GoodsInfo_MteringUnitInfo info : list) { //查找默认辅计量单位
                if ("1".equals(info.getIsdefault())) {
                    auxUnitInfo = info;
                    break;
                }
            }
            if (auxUnitInfo == null && list.size() > 0) { //如果没有设置默认辅单位，则设第一个辅单位为默认
                auxUnitInfo = list.get(0);
            }
            if (auxUnitInfo != null) { //如果商品没有设置辅计量单位，则不设置辅计量单位信息
                orderDetail.setAuxunitid(auxUnitInfo.getMeteringunitid()); //辅计量单位编码
                Map map = countGoodsInfoNumber(goodsId, auxUnitInfo.getMeteringunitid(), num);
                if (map.containsKey("auxunitname")) {
                    orderDetail.setAuxunitname(map.get("auxunitname").toString()); //辅计量单位名称
                }
                if (map.containsKey("auxnumdetail")) {
                    orderDetail.setAuxnumdetail(map.get("auxnumdetail").toString()); //辅单位数量描述(辅单位数量+辅单位+主单位余数+主单位)
                }
                if (map.containsKey("auxremainder")) {
                    orderDetail.setOvernum(new BigDecimal(map.get("auxremainder").toString())); //辅单位数量
                }
                if (map.containsKey("auxInteger")) {
                    orderDetail.setAuxnum(new BigDecimal(map.get("auxInteger").toString())); //主单位余数
                }
                if (map.containsKey("auxnum")) {
                    orderDetail.setTotalbox((BigDecimal) map.get("auxnum")); //主单位余数
                }
            }
            if(null!=orderDetail){
                if(null==orderDetail.getTaxprice()){
                    orderDetail.setTaxprice(BigDecimal.ZERO);
                }
                if(null==goodsInfo.getBoxnum()){
                    goodsInfo.setBoxnum(BigDecimal.ONE);
                }

                orderDetail.setBoxprice(orderDetail.getTaxprice().multiply(goodsInfo.getBoxnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                orderDetail.setGoodsInfo(goodsInfo);
            }
        }

        CustomerGoods customerGoods = customerMapper.getCustomerGoodsByCustomerAndGoodsid(customerId, goodsId);
        if (customerGoods == null) {
            orderDetail.setIsnew("1");
        } else {
            orderDetail.setIsnew("0");
        }

        return orderDetail;
    }

    @Override
    public Map auditOrder(String type, String id, String model) throws Exception {
        Map result = new HashMap();
        SysUser sysUser = getSysUser();
        Map map = new HashMap();
        map.put("id", id);
        Order order = getSalesOrderMapper().getOrderDetail(map);
        String orderStr = null;
        if (null != order && "1".equals(order.getIsgoodsseq())) {
            orderStr = "goodsid";
        }
        List<OrderDetail> orderDetaiList = getSalesOrderDetailMapper().getOrderDetailIsViewByOrder(id, orderStr);
        order.setOrderDetailList(orderDetaiList);
        String billId = "";
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
            //自动审核销售发货通知单需要判断商品可用量是否足够
            if ("0".equals(sysparam)) {
                Map juMap = new HashMap();
                List detailSumList = getSalesOrderDetailMapper().getOrderDetailSumListByID(id);
                if (StringUtils.isEmpty(order.getStorageid())) {
                    juMap = storageForSalesService.isGoodsEnoughByOrderDetailLit(detailSumList);
                } else {
                    juMap = storageForSalesService.isGoodsEnoughByOrderDetailInStorage(order.getStorageid(), detailSumList);
                }
                if (!juMap.containsKey("flag") || !(Boolean) juMap.get("flag")) {
                    result.put("flag", false);
                    result.put("msg", "销售订单:" + id + juMap.get("msg"));
                    return result;
                }
            }
            //判断订单是否可以审核
            Map canAuditMap = isOrderCanAudit(id);
            boolean auditflag = false;
            if (null != canAuditMap && canAuditMap.containsKey("flag")) {
                auditflag = (Boolean) canAuditMap.get("flag");
                if (!auditflag) {
                    msg = (String) canAuditMap.get("msg");
                }
            }
            //判断销售订单是否可以审核
            if (auditflag || "supper".equals(model)) {
                if ("2".equals(order.getStatus())) { //只有状态为2（保存状态）才可进行审核
                    Order order2 = new Order();
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
                    boolean flag = updateOrderStatus(order2);
                    if (flag && orderDetaiList.size() > 0) {
                        //审核成功，添加已买商品
                        addCustomerGoods(order, id);
                        //发货通知单
                        if ("0".equals(sysparam)) {
                            result = salesDispatchBillExtService.addAndAuditDispatchBill(order);
                        } else {
                            billId = salesDispatchBillExtService.addDispatchBillAuto(order);
                            result.put("flag", flag);
                            result.put("msg", "生成销售发货通知单：" + billId);
                            result.put("billid", billId);
                        }
                        //updateOrderRefer("1", id); //更新订单的参照状态为已参照
                    } else {
                        if (flag) {
                            result.put("msg", "订单明细为空或者商品数量为0，直接关闭！");
                        }
                        result.put("flag", flag);
                    }
                }
            } else {
                result.put("flag", false);
                result.put("msg", msg);
            }
        } else if ("2".equals(type)) { //反审
            //下游单据发货通知单生成的发货单已生成大单发货，则不允许反审销售订单
            boolean isbigsaleout = storageForSalesService.doCheckSaleoutIsbigsaleoutByOrderid(order.getId());
            //只有状态为3（审核状态）才可进行反审
            if (("3".equals(order.getStatus()) || ("4".equals(order.getStatus()) && "0".equals(sysparam))) && !isbigsaleout) {
                //反审时判断有无下游单据，如有，下游单据未审核，则自动删除下游单据再反审
                boolean bl = salesDispatchBillExtService.deleteDispatchBillOppauditOrder(id);
                if (bl) {
                    Order order2 = new Order();
                    order2.setId(id);
                    order2.setStatus("2");
                    boolean flag = updateOrderStatus(order2);
                    //来源直营销售单时 反审后同时反审直营销售单
                    if (flag && "2".equals(order.getSourcetype())) {
                        OrderCar orderCar = new OrderCar();
                        orderCar.setId(order.getSourceid());
                        orderCar.setStatus("2");
                        getSalesOrderCarMapper().updateOrderCarStatus(orderCar);
                    }
                    result.put("flag", flag);
                }
                result.put("billArg", bl);
                result.put("bigflag", true);
            }else{
                result.put("bigflag", false);
            }
        } else if ("3".equals(type)) { //工作流审核
            billId = salesDispatchBillExtService.addDispatchBillAuto(order); //自动生成发货通知单
            updateOrderRefer("1", id); //更新订单的参照状态为已参照
        }
        return result;
    }

    /**
     * 添加已买商品，添加客户满赠已赠送信息
     *
     * @param order
     * @param id
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 24, 2013
     */
    public void addCustomerGoods(Order order, String id) throws Exception {
        if(null!=order){
            List<OrderDetail> list = order.getOrderDetailList();
            if (list.size() != 0) {
                for (OrderDetail orderDetail : list) {
                    CustomerGoods customerGoods = customerMapper.getCustomerGoodsByCustomerAndGoodsid(order.getCustomerid(),
                            orderDetail.getGoodsid());
                    if (null != customerGoods) {//已存在该已买商品，判断价格是否相同
                        BigDecimal taxprice = (null != orderDetail.getTaxprice()) ? orderDetail.getTaxprice() : (new BigDecimal(0));
                        BigDecimal price = (null != customerGoods.getPrice()) ? customerGoods.getPrice() : (new BigDecimal(0));
                        if (taxprice.compareTo(price) != 0) {//更新该已买商品
                            customerGoods.setPrice(taxprice);
                            customerMapper.updateCustomerGoods(customerGoods);
                        }
                    } else {//商品不存在，新增已买商品
                        CustomerGoods customerGoods2 = new CustomerGoods();
                        customerGoods2.setCustomerid(order.getCustomerid());
                        customerGoods2.setGoodsid(orderDetail.getGoodsid());
                        customerGoods2.setPrice(orderDetail.getTaxprice());
                        customerMapper.addCustomerGoods(customerGoods2);
                    }
                }
            }

        }
    }

    /**
     * 添加客户满赠已促销信息
     * @param order
     * @throws Exception
     */
    public void addCutomerFullFreeLog(Order order) throws Exception{
        List<Map> freeList  = getSalesOrderDetailMapper().getOrderPromotionFullFreeList(order.getId());
        for(Map map:freeList){
            String groupid = (String) map.get("groupid");
            String billid = (String) map.get("billid");
            BigDecimal unitnum = (BigDecimal) map.get("unitnum");
            BigDecimal basenum = (BigDecimal) map.get("basenum");
            String beginDate = (String) map.get("begindate");
            String endDate = (String) map.get("enddate");
            BigDecimal mucount = unitnum.divide(basenum,0,BigDecimal.ROUND_DOWN);
            BigDecimal taxamount = BigDecimal.ZERO;
            BigDecimal taxprice = (BigDecimal) map.get("taxprice");
            List<PromotionPackageGroupDetail> detailList = getSalesPromotionMapper().getDetailByGroupid(groupid);
            if(null != detailList){
                for(PromotionPackageGroupDetail detail : detailList){
                    if("1".equals(detail.getGtype())){
                        String customerid = order.getCustomerid();
                        String goodsid = detail.getGoodsid();
                        BigDecimal sendnum = detail.getUnitnum().multiply(mucount);
                        taxamount = sendnum.multiply(taxprice);
                        getSalesOrderDetailMapper().addCustomerFullFreeLog(customerid, order.getId(), goodsid, groupid, sendnum, taxamount);
                        break;
                    }
                }
            }
        }
        updatePromotionGroupByAuditOrder(order.getId());
    }
    /**
     * 记录客户满赠已促销信息(该方法需要在订单明细删除前操作 或者订单修改前操作)
     * @param orderid
     * @param
     * @throws Exception
     * @author chenwei
     * @date 2015年9月18日
     */
    public void deleteCutomerFullFreeLog(String orderid) throws Exception{
        getSalesOrderDetailMapper().deleteCustomerFullFreeLog(orderid);
        //更新促销 上限
        updatePromotionGroupByOppauditOrder(orderid);
    }

    /**
     * 订单审核后 更新促销分组的 已促销信息
     * @param orderid
     * @throws Exception
     */
    public void updatePromotionGroupByAuditOrder(String orderid) throws Exception{
        List<Map> freeList  = getSalesOrderDetailMapper().getOrderPromotionGroupList(orderid);
        if(null!=freeList && freeList.size()>0) {
            for (Map map : freeList) {
                String groupid = (String) map.get("groupid");
                String billid = (String) map.get("billid");
                BigDecimal unitnum = (BigDecimal) map.get("unitnum");
                BigDecimal basenum = (BigDecimal) map.get("basenum");
                //不计算多余的份数（不足1份按0算）
                BigDecimal mucount = unitnum.divide(basenum, 0, BigDecimal.ROUND_DOWN);
                getSalesPromotionMapper().updatePromotionPackageGroupSendnum(billid, groupid, mucount);
            }
        }
    }
    /**
     * 订单反审后 更新促销分组的 已促销信息
     * @param orderid
     * @throws Exception
     */
    public void updatePromotionGroupByOppauditOrder(String orderid) throws Exception{
        List<Map> freeList  = getSalesOrderDetailMapper().getOrderPromotionGroupList(orderid);
        if(null!=freeList && freeList.size()>0){
            for(Map map:freeList){
                String groupid = (String) map.get("groupid");
                String billid = (String) map.get("billid");
                BigDecimal unitnum = (BigDecimal) map.get("unitnum");
                BigDecimal basenum = (BigDecimal) map.get("basenum");
                BigDecimal mucount = unitnum.divide(basenum,0,BigDecimal.ROUND_DOWN);
                mucount = mucount.negate();
                getSalesPromotionMapper().updatePromotionPackageGroupSendnum(billid,groupid,mucount);
            }
        }
    }
    @Override
    public Map auditMultiOrder(String ids) throws Exception {
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
                Order order = getOrder(id);
                Map map = auditOrder("1", order.getId(), null);
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

    @Override
    public boolean submitOrderProcess(String title, String userId, String processDefinitionKey, String businessKey, Map<String, Object> variables) throws Exception {
        return false;
    }

    @Override
    public Map addDRSalesOrder(List<ImportSalesOrder> list) throws Exception {
        Map returnMap = new HashMap();
        Map customerMap = new HashMap();
        List mainCustomer = new ArrayList();
        String emptCustomerID = "";//客户档案中不存在的客户编码
        for (ImportSalesOrder importSalesOrder : list) {
            String customerid = importSalesOrder.getCustomerid() ;
            if(StringUtils.isEmpty(customerid)){
                List detailList = new ArrayList();
                detailList.add(importSalesOrder);
                customerMap.put("", detailList);
            }else{
                Customer customer = getCustomerByID(customerid);
                if(null == customer){
                    List detailList = new ArrayList();
                    detailList.add(importSalesOrder);
                    String key = "null"+customerid;
                    customerMap.put(key, detailList);
                    continue;
                }else{
                    if(customerid.equals(customer.getId())){
                        customerid = customer.getId();
                    }else{
                        if(StringUtils.isNotEmpty(emptCustomerID)){
                            emptCustomerID += customerid+",";
                        }else{
                            emptCustomerID += customerid;
                        }
                        continue;
                    }
                }
                if(StringUtils.isNotEmpty(customer.getIslast()) && "0".equals(customer.getIslast())){
                    if(mainCustomer.contains(customerid)){
                        continue;
                    }else{
                        mainCustomer.add(customerid);
                        continue;
                    }
                }
                if (customerMap.containsKey(customerid)) {
                    List detailList = (List) customerMap.get(customerid);
                    detailList.add(importSalesOrder);
                    customerMap.put(customerid, detailList);
                } else {
                    List detailList = new ArrayList();
                    detailList.add(importSalesOrder);
                    customerMap.put(customerid, detailList);
                }
            }

        }
        Set set = customerMap.entrySet();
        Iterator it = set.iterator();
        boolean flag = false;
        int successNum = 0;
        String msg = "",backorderids = null,goodsidmsg = "",customergoodsidmsg = "",spellmsg = "",barcodemsg = "",disableInfoidsmsg = "";//backorderids返回导入成功的销售订单编码
        int emptSize = 0,failureCustomerNum = 0;
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Entry<String, String>) it.next();
            String customerid = entry.getKey();
            List<ImportSalesOrder> detailList = (List<ImportSalesOrder>) customerMap.get(customerid);
            Map orderMap = changeOrderByCustomerid(customerid, detailList, "");
            Order order = (Order) orderMap.get("order");
            String goodsid = (String) orderMap.get("goodsid");
            String customergoodsid = (String) orderMap.get("customergoodsid");//店内码
            String spells = (String) orderMap.get("spells");
            String barcodes = (String) orderMap.get("barcodes");
            String disableInfoids = (String) orderMap.get("disableInfoids");
            boolean addflag = false;
            if (null != order) {
                Map returnmap = addOrder(order, "save");
                addflag = (Boolean) returnmap.get("flag");
            }
            if (addflag) {
                successNum += order.getOrderDetailList().size();
                if (StringUtils.isEmpty(backorderids)) {
                    backorderids = order.getId();
                } else {
                    backorderids += "," + order.getId();
                }
                if (null != goodsid && !"".equals(goodsid)) {
                    if ("".equals(goodsidmsg)) {
                        goodsidmsg = goodsid;
                    } else {
                        goodsidmsg += goodsid;
                    }
                }
                if (StringUtils.isNotEmpty(customergoodsid)) {
                    if ("".equals(customergoodsidmsg)) {
                        customergoodsidmsg = customergoodsid;
                    } else {
                        customergoodsidmsg += customergoodsid;
                    }
                }
                if (StringUtils.isNotEmpty(spells)) {
                    if ("".equals(spellmsg)) {
                        spellmsg = spells;
                    } else {
                        spellmsg += spells;
                    }
                }
                if (StringUtils.isNotEmpty(barcodes)) {
                    if ("".equals(barcodemsg)) {
                        barcodemsg = barcodes;
                    } else {
                        barcodemsg += barcodes;
                    }
                }
                if(StringUtils.isNotEmpty(disableInfoids)){
                    if("".equals(disableInfoidsmsg)){
                        disableInfoidsmsg = disableInfoids;
                    }else{
                        disableInfoidsmsg += disableInfoids;
                    }
                }
                flag = true;
            } else {
                if (StringUtils.isEmpty(customerid)) {
                    failureCustomerNum = failureCustomerNum + detailList.size();
                }else{
                    String emptcid = customerid.replace("null","");
                    emptCustomerID += emptcid+",";
                    emptSize = emptSize + detailList.size() ;
                }
            }
        }
        if(failureCustomerNum > 0){
            returnMap.put("failureCustomerNum", failureCustomerNum);
        }
        if(StringUtils.isNotEmpty(emptCustomerID)){
            returnMap.put("emptVal","客户不存在");
            returnMap.put("customerid",emptCustomerID);
            returnMap.put("emptSize",emptSize);
        }
//        int failureNum = list.size() - successNum;
//        returnMap.put("failure", failureNum);
        if(mainCustomer.size() > 0){
            returnMap.put("mainCustomer",mainCustomer.toString());
        }
        returnMap.put("flag", flag);
        returnMap.put("success", successNum);
        returnMap.put("repeatNum", 0);
        returnMap.put("closeNum", 0);
        returnMap.put("goodsidmsg", goodsidmsg);
        returnMap.put("customergoodsidmsg", customergoodsidmsg);
        returnMap.put("spellmsg", spellmsg);
        returnMap.put("barcodemsg", barcodemsg);
        returnMap.put("disableInfoidsmsg", disableInfoidsmsg);
        returnMap.put("msg", msg);
        returnMap.put("backorderids", backorderids);
        return returnMap;
    }

    public Map addDRSalesOrder(List<ImportSalesOrder> list, String remark) throws Exception {
        Map returnMap = new HashMap();
        Map customerMap = new HashMap();
        for (ImportSalesOrder importSalesOrder : list) {
            if (customerMap.containsKey(importSalesOrder.getCustomerid())) {
                List detailList = (List) customerMap.get(importSalesOrder.getCustomerid());
                detailList.add(importSalesOrder);
                customerMap.put(importSalesOrder.getCustomerid(), detailList);
            } else {
                List detailList = new ArrayList();
                detailList.add(importSalesOrder);
                customerMap.put(importSalesOrder.getCustomerid(), detailList);
            }
        }
        Set set = customerMap.entrySet();
        Iterator it = set.iterator();
        boolean flag = false;
        String msg = "";
        String billid = "";
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Entry<String, String>) it.next();
            String customerid = entry.getKey();
            List<ImportSalesOrder> detailList = (List<ImportSalesOrder>) customerMap.get(customerid);
            Map orderMap = changeOrderByCustomerid(customerid, detailList, remark);
            Order order = (Order) orderMap.get("order");
            if (null != order) {
                Map returnmap = addOrder(order, "save");
                boolean addflag = (Boolean) returnmap.get("flag");
                if (addflag) {
                    flag = true;
                    if (StringUtils.isEmpty(billid)) {
                        billid = order.getId();
                    } else {
                        billid += "," + order.getId();
                    }
                }
            }
        }
        returnMap.put("flag", flag);
        returnMap.put("msg", msg);
        returnMap.put("billid", billid);
        return returnMap;
    }

    /**
     * 根据客户编号和导入的明细数据 转换成销售订单
     *
     * @param customerid
     * @param list
     * @return
     * @throws Exception
     * @author chenwei
     * @date Sep 26, 2013
     */
    public Map changeOrderByCustomerid(String customerid, List<ImportSalesOrder> list, String remark) throws Exception {
        //判断是否允许输入小数位
        int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
        Map returnMap = new HashMap();
        Customer customer = getCustomerByID(customerid);
        String goodsids = "",customergoodsids = "",spells ="",barcodes = "",disableInfoids = "";
        String sourceid = "";
        if (null != customer) {
            //生成订单基本信息
            Order order = new Order();
            order.setCustomerid(customer.getId());
            order.setBusinessdate(CommonUtils.getTodayDataStr());
            order.setStatus("2");
            order.setHandlerid(customer.getContact());
            order.setSalesdept(customer.getSalesdeptid());
            order.setSalesuser(customer.getSalesuserid());
            order.setSettletype(customer.getSettletype());
            order.setPaytype(customer.getPaytype());
            order.setRemark(remark);
            //生成订单明细信息
            List detailList = new ArrayList();
            for (ImportSalesOrder importSalesOrder : list) {
                if(StringUtils.isNotEmpty(importSalesOrder.getSourceid())){
                    sourceid = importSalesOrder.getSourceid();
                }
                if (null != importSalesOrder.getBusinessdate() && !"".equals(importSalesOrder.getBusinessdate())) {
                    order.setBusinessdate(importSalesOrder.getBusinessdate());
                }
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderid(order.getId());
                orderDetail.setRemark(importSalesOrder.getRemark());
                GoodsInfo goodsInfo = null;
                Map goodsMap = new HashMap();
                //根据商品编码、店内码、商品助记符、条形码获取商品信息
                if (null != importSalesOrder.getGoodsid() && !"".equals(importSalesOrder.getGoodsid())) {
                    goodsInfo = getAllGoodsInfoByID(importSalesOrder.getGoodsid());
                    if (null == goodsInfo) {
                        goodsids += importSalesOrder.getGoodsid() + ",";
                    }else if(!"1".equals(goodsInfo.getState())){
                        goodsMap.put("id",importSalesOrder.getGoodsid());
                    }
                } else if (null != importSalesOrder.getCustomergoodsid() && !"".equals(importSalesOrder.getCustomergoodsid())) {
                    goodsInfo = getGoodsInfoByCustomerGoodsid(customerid, importSalesOrder.getCustomergoodsid());
                    if (null == goodsInfo) {
                        customergoodsids += importSalesOrder.getCustomergoodsid() + ",";
                    }else if(!"1".equals(goodsInfo.getState())){
                        disableInfoids += importSalesOrder.getCustomergoodsid() +",";
                    }
                } else if (StringUtils.isNotEmpty(importSalesOrder.getSpell())) {
                    goodsInfo = getGoodsInfoBySpell(importSalesOrder.getSpell());
                    if (null == goodsInfo) {
                        spells += importSalesOrder.getSpell() + ",";
                    }else if(!"1".equals(goodsInfo.getState())){
                        goodsMap.put("spell",importSalesOrder.getSpell());
                    }
                } else if (StringUtils.isNotEmpty(importSalesOrder.getBarcode())) {
                    goodsInfo = getGoodsInfoByBarcode(importSalesOrder.getBarcode());
                    if (null == goodsInfo) {
                        barcodes += importSalesOrder.getBarcode() + ",";
                    }else if(!"1".equals(goodsInfo.getState())){
                        goodsMap.put("barcode",importSalesOrder.getBarcode());
                    }
                }
                //禁用的商品查找其他同类型启用的商品
                if(null != goodsInfo  && "0".equals(goodsInfo.getState())){
                    List<GoodsInfo> goodsInfoList = getBaseGoodsMapper().getGoodsInfoListByMap(goodsMap);
                    if(goodsInfoList.size() == 1){
                        if(goodsMap.containsKey("id")){
                            disableInfoids += importSalesOrder.getGoodsid() +",";
                        }else if(goodsMap.containsKey("spell")){
                            disableInfoids += importSalesOrder.getSpell() +",";
                        }else if(goodsMap.containsKey("barcode")){
                            disableInfoids += importSalesOrder.getBarcode() +",";
                        }
                    }else{
                        for(GoodsInfo gInfo : goodsInfoList){
                            if(null != gInfo && "1".equals(gInfo.getState())){
                                goodsInfo = gInfo;
                                break;
                            }
                        }
                    }
                }
                if(null!=goodsInfo && "1".equals(goodsInfo.getState())){
                    orderDetail.setGoodsid(goodsInfo.getId());
                    orderDetail.setBrandid(goodsInfo.getBrand());
                    orderDetail.setCostprice(getGoodsCostprice(order.getStorageid(),goodsInfo));
                    orderDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
                    orderDetail.setUnitid(goodsInfo.getMainunit());
                    if (StringUtils.isEmpty(goodsInfo.getMainunitName())) {
                        if (StringUtils.isNotEmpty(goodsInfo.getMainunit())) {
                            MeteringUnit meteringUnit = getMeteringUnitById(goodsInfo.getMainunit());
                            if (null != meteringUnit) {
                                orderDetail.setUnitname(meteringUnit.getName());
                            }
                        }
                    } else {
                        orderDetail.setUnitname(goodsInfo.getMainunitName());
                    }
                    if(decimalScale == 0){
                        importSalesOrder.setUnitnum(importSalesOrder.getUnitnum().setScale(decimalScale,BigDecimal.ROUND_DOWN));
                    }else{
                        importSalesOrder.setUnitnum(importSalesOrder.getUnitnum().setScale(decimalScale,BigDecimal.ROUND_HALF_UP));
                    }
                    orderDetail.setUnitnum(importSalesOrder.getUnitnum());
                    Map auxMap = countGoodsInfoNumber(goodsInfo.getId(), "", importSalesOrder.getUnitnum());
                    if (null != auxMap.get("auxunitid")) {
                        orderDetail.setAuxunitid((String) auxMap.get("auxunitid"));
                    }
                    if (null != auxMap.get("auxunitname")) {
                        orderDetail.setAuxunitname((String) auxMap.get("auxunitname"));
                    }
                    if (null != auxMap.get("auxInteger")) {
                        orderDetail.setAuxnum(new BigDecimal((String) auxMap.get("auxInteger")));
                    }
                    if (null != auxMap.get("auxremainder")) {
                        orderDetail.setOvernum(new BigDecimal((String) auxMap.get("auxremainder")));
                    }
                    if (null != auxMap.get("auxnumdetail")) {
                        orderDetail.setAuxnumdetail((String) auxMap.get("auxnumdetail"));
                    }
                    //是否获取系统价格
                    String isSystemPrice = getSysParamValue("isSystemPrice");

                    //根据系统业务获取商品的系统价格
                    OrderDetail orderDetailprice = getGoodsDetail(goodsInfo.getId(), order.getCustomerid(), order.getBusinessdate(), orderDetail.getUnitnum(), "");
                    if("0".equals(isSystemPrice)){
                        //根据商品编码和对应客户取商品原价
                        OrderDetail fixOrderDetail = getFixGoodsDetail(importSalesOrder.getGoodsid(), importSalesOrder.getCustomerid());
                        if(null != fixOrderDetail.getGoodsInfo()){
                            orderDetail.setOldprice(fixOrderDetail.getTaxprice());
                            orderDetail.setLowestsaleprice(fixOrderDetail.getLowestsaleprice());
                        }else{
                            orderDetail.setOldprice(orderDetailprice.getTaxprice());
                            orderDetail.setLowestsaleprice(orderDetailprice.getBuyprice());
                        }
                        //导入价格为空时取系统价格
                        if (null == importSalesOrder.getTaxprice()/* || importSalesOrder.getTaxprice().compareTo(BigDecimal.ZERO) == 0*/) {
                            orderDetail.setTaxprice(orderDetailprice.getTaxprice());
                            //有特价时，关联特价编号
                            if(StringUtils.isNotEmpty(orderDetailprice.getGroupid())){
                                orderDetail.setGroupid(orderDetailprice.getGroupid());
                            }
                        }else{
                            orderDetail.setTaxprice(importSalesOrder.getTaxprice());
                        }
                        orderDetail.setTaxamount(importSalesOrder.getUnitnum().multiply(orderDetail.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                        orderDetail.setRemark(importSalesOrder.getRemark());
                        if(StringUtils.isNotEmpty(orderDetailprice.getRemark())){
                            String dremark = orderDetail.getRemark();
                            if(StringUtils.isNotEmpty(dremark)){
                                dremark += " "+orderDetailprice.getRemark();
                            }else{
                                dremark = orderDetailprice.getRemark();
                            }
                            orderDetail.setRemark(dremark);
                        }
                    }else if (null != orderDetailprice) {//获取系统价格
                        orderDetail.setOldprice(orderDetailprice.getTaxprice());
                        orderDetail.setTaxprice(orderDetailprice.getTaxprice());
                        orderDetail.setTaxamount(orderDetail.getUnitnum().multiply(orderDetail.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                        orderDetail.setRemark(orderDetailprice.getRemark());
                        //有特价时，关联特价编号
                        if(StringUtils.isNotEmpty(orderDetailprice.getGroupid())){
                            orderDetail.setGroupid(orderDetailprice.getGroupid());
                        }
                    } else {
                        //导入的金额不为0时
                        if (null != importSalesOrder.getTaxamount() && importSalesOrder.getTaxamount().compareTo(BigDecimal.ZERO) != 0) {
                            orderDetail.setTaxamount(importSalesOrder.getTaxamount());
                            if (importSalesOrder.getTaxamount().compareTo(BigDecimal.ZERO) != 0
                                    && importSalesOrder.getUnitnum().compareTo(BigDecimal.ZERO) != 0) {
                                orderDetail.setTaxprice(importSalesOrder.getTaxamount().divide(importSalesOrder.getUnitnum(), 6, BigDecimal.ROUND_HALF_UP));
                            }
                        } else if (null != importSalesOrder.getTaxprice() && importSalesOrder.getTaxprice().compareTo(BigDecimal.ZERO) != 0) {
                            //含税单价存在 且不为0
                            //金额 = 单价*数量
                            orderDetail.setTaxprice(importSalesOrder.getTaxprice());
                            orderDetail.setTaxamount(importSalesOrder.getTaxprice().multiply(importSalesOrder.getUnitnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                        } else if (null == importSalesOrder.getTaxprice() || importSalesOrder.getTaxprice().compareTo(BigDecimal.ZERO) == 0) {
                            //单价不存在 或者为0
                            //商品单价 通过价格套去取
                            OrderDetail orderDetail2 = getGoodsDetail(orderDetail.getGoodsid(), customerid, CommonUtils.getTodayDataStr(), importSalesOrder.getUnitnum(), null);
                            //有特价时，关联特价编号
                            if(StringUtils.isNotEmpty(orderDetailprice.getGroupid())){
                                orderDetail.setGroupid(orderDetailprice.getGroupid());
                            }
                            orderDetail.setTaxprice(orderDetail2.getTaxprice());
                            orderDetail.setTaxamount(orderDetail.getTaxprice().multiply(importSalesOrder.getUnitnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                            orderDetail.setRemark(orderDetail2.getRemark());
                            if(StringUtils.isNotEmpty(orderDetailprice.getRemark())){
                                String dremark = orderDetail.getRemark();
                                if(StringUtils.isNotEmpty(dremark)){
                                    dremark += " "+orderDetailprice.getRemark();
                                }else{
                                    dremark = orderDetailprice.getRemark();
                                }
                                orderDetail.setRemark(dremark);
                            }
                        }
                    }

                    BigDecimal notaxamount = getNotaxAmountByTaxAmount(orderDetail.getTaxamount(),orderDetail.getTaxtype());
                    if (notaxamount.compareTo(BigDecimal.ZERO) != 0) {
                        BigDecimal notaxprice = notaxamount.divide(importSalesOrder.getUnitnum(), 6, BigDecimal.ROUND_HALF_UP);
                        orderDetail.setNotaxprice(notaxprice);
                        orderDetail.setNotaxamount(notaxamount);
                        orderDetail.setTax(orderDetail.getTaxamount().subtract(orderDetail.getNotaxamount()));
                    }
//                    orderDetail.
                    detailList.add(orderDetail);
                }
                order.setOrderDetailList(detailList);
            }
            order.setSourceid(sourceid);
            returnMap.put("order", order);
            returnMap.put("goodsid", goodsids);
            returnMap.put("customergoodsid", customergoodsids);
            returnMap.put("spells", spells);
            returnMap.put("disableInfoids", disableInfoids);
            returnMap.put("barcodes", barcodes);
        } else {
            returnMap.put("order", null);
            for (ImportSalesOrder importSalesOrder : list) {
                if (StringUtils.isNotEmpty(importSalesOrder.getGoodsid())) {
                    goodsids += importSalesOrder.getGoodsid() + ",";
                } else if (StringUtils.isNotEmpty(importSalesOrder.getCustomergoodsid())) {
                    customergoodsids += importSalesOrder.getCustomergoodsid() + ",";
                } else if (StringUtils.isNotEmpty(importSalesOrder.getSpell())) {
                    spells += importSalesOrder.getSpell() + ",";
                } else if (StringUtils.isNotEmpty(importSalesOrder.getBarcode())) {
                    barcodes += importSalesOrder.getBarcode() + ",";
                }
            }
            returnMap.put("goodsid", goodsids);
            returnMap.put("customergoodsid", customergoodsids);
            returnMap.put("spells", spells);
            returnMap.put("barcodes", barcodes);
        }
        return returnMap;
    }

    @Override
    public Map checkOrderAudit(String id) throws Exception {
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
            Order order = getSalesOrderMapper().getOrderDetail(querymap);
            if (null != order) {
                Customer customer = getCustomerByID(order.getCustomerid());
                //是否重复单据判断
                Map map = getSalesOrderMapper().checkOrderRepeat(id, order.getCustomerid(), sysparm);
                boolean isNotRepeat = true;
                if (null != map) {
                    isNotRepeat = false;
                }
                Map canAuditMap = isOrderCanAudit(id);
                boolean auditflag = false;
                if (null != canAuditMap && canAuditMap.containsKey("flag")) {
                    auditflag = (Boolean) canAuditMap.get("flag");
                    if (!auditflag) {
                        msg += (String) canAuditMap.get("msg");
                    }
                }
                if (!isNotRepeat) {
                    if (StringUtils.isEmpty(msg)) {
                        msg = "订单" + id + ":" + "客户：" + customer.getId() + "," + customer.getName() + ",";
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

    @Override
    public Map orderDeployInfo(String id) throws Exception {
        boolean flag = true;
        boolean barcodeFlag = false;
        boolean batchFlag = false;
        String msg = "";
        Map orderQuery = new HashMap();
        orderQuery.put("id", id);
        Order order = getSalesOrderMapper().getOrderDetail(orderQuery);
        String orderStr = null;
        if (null != order && "1".equals(order.getIsgoodsseq())) {
            orderStr = "goodsid";
        }
        //获取
        List<OrderDetail> billDetailList = getSalesOrderDetailMapper().getOrderDetailSumListByID(id);

        for (OrderDetail billDetail : billDetailList) {
            //当销售发货通知单处于保存状态时 判断仓库中数量是否足够
            if ("2".equals(order.getStatus())) {
                boolean sendflag = storageForSalesService.isGoodsEnoughByOrderDetail(billDetail);
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
                            if (StringUtils.isNotEmpty(order.getStorageid()) || StringUtils.isNotEmpty(billDetail.getStorageid())) {
                                String storageid = order.getStorageid();
                                if(StringUtils.isNotEmpty(billDetail.getStorageid())){
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
                                    if (org.apache.commons.lang.StringUtils.isEmpty(goodsMsg)) {
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
                        flag = false;
                    }
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
    public Order getOrderDeployInfo(String id, String barcodeflag,String deploy)
            throws Exception {
        Map orderQuery = new HashMap();
        orderQuery.put("id", id);
        Order order = getSalesOrderMapper().getOrderDetail(orderQuery);
        String orderStr = null;
        if (null != order && "1".equals(order.getIsgoodsseq())) {
            orderStr = "goodsid";
        }
        //商品库存记录 防止同一张订单中 多条商品的配货出现问题
        Map storageGoodsMap = new HashMap();
        //商品库存追加记录  防止同一张订单中  多条商品 追加时出现数量问题
        Map storageAddGoodsMap = new HashMap();

        //商品追加或者替换 其他商品的列表
        //key需要追加或者替换的商品编号  val=指定替换的商品与仓库
        Map deployMap = new HashMap();
        if(org.apache.commons.lang.StringUtils.isNotEmpty(deploy)){
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

        List<OrderDetail> billDetailList = getSalesOrderDetailMapper().getOrderDetailByOrderWithDiscount(id, orderStr);
        Customer customer = getCustomerByID(order.getCustomerid());
        if (customer != null) {
            order.setCustomername(customer.getName());
        }
        //条形码相同商品 补不足商品数量
        List addList = new ArrayList();
        for (OrderDetail billDetail : billDetailList) {
            String oldAuxnumDetail = billDetail.getAuxnumdetail();
            GoodsInfo goodsInfo = getGoodsInfoByID(billDetail.getGoodsid());
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
                billDetail.setBasesaleprice(orgGoodsInfo.getBasesaleprice());

            }
            TaxType taxType = getTaxType(billDetail.getTaxtype());
            if (taxType != null) {
                billDetail.setTaxtypename(taxType.getName());
            }
            //商品
            if (!"1".equals(billDetail.getIsdiscount()) && !"2".equals(billDetail.getIsdiscount())) {
                String storageid = billDetail.getStorageid();
                String keyid = billDetail.getGoodsid() + "_" + storageid;
                boolean flag = true;
                StorageSummary storageSummary = null;
                BigDecimal initnum = BigDecimal.ZERO;
                ////判断商品指定了库存批次 指定库存批次后 配置库存不处理
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
                    if (null == storageSummary || storageSummary.getUsablenum().compareTo(billDetail.getUnitnum()) == -1) {
                        flag = false;
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
                        BigDecimal totAddNum = BigDecimal.ZERO;
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

                                OrderDetail detail = new OrderDetail();
                                detail.setIsdiscount("0");
                                detail.setIsbranddiscount("0");
                                detail.setBillno(billDetail.getBillno());
                                detail.setOrderid(billDetail.getOrderid());
                                detail.setBilldetailno(billDetail.getBilldetailno());
                                detail.setGoodsid(storageSummary2.getGoodsid());
                                detail.setGroupid(billDetail.getGroupid());
                                detail.setTaxtype(billDetail.getTaxtype());
                                detail.setTaxtypename(billDetail.getTaxtypename());
                                detail.setUnitid(billDetail.getUnitid());
                                detail.setUnitname(billDetail.getUnitname());
                                detail.setAuxunitid(storageSummary2.getAuxunitid());
                                detail.setAuxunitname(storageSummary2.getAuxunitname());
                                detail.setDeliverytype(billDetail.getDeliverytype());
                                detail.setDeliverydate(billDetail.getDeliverydate());
                                detail.setBasesaleprice(billDetail.getBasesaleprice());
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
                                //获取该商品的原价
                                OrderDetail orderDetail = getGoodsDetail(detail.getGoodsid(),order.getCustomerid(),null,detail.getUnitnum(),null);
                                if(null!=orderDetail){
                                    detail.setOldprice(orderDetail.getTaxprice());
                                }
                                detail.setFixprice(billDetail.getFixprice());
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
                                    if(StringUtils.isNotEmpty(remark)){
                                        detail.setRemark(remark+";商品:" + billDetail.getGoodsid() + ",需:"+oldAuxnumDetail+",以此替换");
                                    }else{
                                        detail.setRemark("商品:" + billDetail.getGoodsid() + ",需:"+oldAuxnumDetail+",以此替换");
                                    }

                                }
                                detail.setSeq(billDetail.getSeq());
                                //追加或者替换的 商品箱价
                                detail.setBoxprice(goodsInfo.getBoxnum().multiply(detail.getTaxprice()).setScale(2, BigDecimal.ROUND_HALF_UP));
                                storageSummary2.setUsablenum(storageSummary2.getUsablenum().subtract(thisnum));
                                storageAddGoodsMap.put(addkeyid, storageSummary2);
                                if (detail.getUnitnum().compareTo(BigDecimal.ZERO) > 0) {
                                    totAddNum = totAddNum.add(detail.getUnitnum());
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
        }
        //是否用相同条形码商品追加或者替换
        if ("1".equals(barcodeflag) || "2".equals(barcodeflag)) {
            billDetailList.addAll(addList);
        }
        List list = new ArrayList();
        List lackList = new ArrayList();
        for (OrderDetail orderDetail : billDetailList) {
            //商品客户价格套价格 ---》 参考价
            BigDecimal referenceprice = getGoodsPriceSetByCustomer(orderDetail.getGoodsid(),order.getCustomerid());
            orderDetail.setReferenceprice(referenceprice);
            if (null != orderDetail.getUnitnum() && orderDetail.getUnitnum().compareTo(BigDecimal.ZERO) != 0) {
                list.add(orderDetail);
            } else if (!"0".equals(orderDetail.getIsdiscount())) {
                list.add(orderDetail);
            }
            if (null!=orderDetail.getUnitnum() && orderDetail.getUnitnum().compareTo(BigDecimal.ZERO) == 0 && orderDetail.getFixnum().compareTo(BigDecimal.ZERO) == 1) {
                lackList.add(orderDetail);
            }
        }
        //对明细按seq进行排序
        Collections.sort(list,new Comparator<OrderDetail>(){
            @Override
            public int compare(OrderDetail b1, OrderDetail b2) {
                if(b1.getSeq()>b2.getSeq()){
                    return 1;
                }else if(b1.getSeq()==b2.getSeq()){
                    return 0;
                }else{
                    return -1;
                }
            }
        });
        order.setOrderDetailList(list);
        order.setLackList(lackList);
        return order;
    }

    @Override
    public List getSalesOrderListBy(Map map) throws Exception {
        String showPCustomerName=(String)map.get("showPCustomerName");
        List<Order> list = getSalesOrderMapper().getSalesOrderListBy(map);
        Customer pCustomer =null;
        String showDetailListType=(String) map.get("showDetailListType");
        for (Order item : list) {
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

            List<OrderDetail> detailList=null;
            if("print".equals(showDetailListType)){
                Map detailMap=new HashMap();

                if (null != item && "1".equals(item.getIsgoodsseq())) {
                    detailMap.put("orderseq", "goodsid");
                }
                detailMap.put("orderid", item.getId());
                detailList=getSalesOrderDetailMapper().getOrderDetailPrintBy(detailMap);

                Map paramMap=new HashMap();
                paramMap.put("showShopid", "true");
                paramMap.put("customerid", item.getCustomerid());
                fillInOrderDetailInfo(detailList, paramMap);
                item.setOrderDetailList(detailList);
            }
        }
        return list;
    }

    @Override
    public Map addOrderByGoodsout(String ids) throws Exception {
        boolean flag = false;
        String msg = "";
        String billid = "";
        Map returnMap = new HashMap();
        if (StringUtils.isNotEmpty(ids)) {
            String[] idArr = ids.split(",");
            List<ImportSalesOrder> list = new ArrayList();
            for (String id : idArr) {
                OrderDetail orderDetail = getSalesOrderDetailMapper().getOrderDetail(id);
                Order order = getSalesOrderMapper().getOrderById(orderDetail.getOrderid());
                if (null != orderDetail && null != order && "0".equals(orderDetail.getIssupply())) {
                    ImportSalesOrder importSalesOrder = new ImportSalesOrder();
                    importSalesOrder.setCustomerid(order.getCustomerid());
                    importSalesOrder.setGoodsid(orderDetail.getGoodsid());
                    importSalesOrder.setUnitnum(orderDetail.getFixnum().subtract(orderDetail.getSendnummain()));
                    importSalesOrder.setTaxprice(orderDetail.getTaxprice());
                    importSalesOrder.setTaxamount(importSalesOrder.getUnitnum().multiply(orderDetail.getTaxprice()).setScale(2, BigDecimal.ROUND_HALF_UP));
                    list.add(importSalesOrder);
                    //更新订单明细补货状态
                    getSalesOrderDetailMapper().updateOrderDetailIssupply(id);

                    msg += "订：" + order.getId() + ",商：" + orderDetail.getGoodsid() + ",补:" + importSalesOrder.getUnitnum().intValue() + ";";
                }
            }
            returnMap = addDRSalesOrder(list, "缺货商品补单");
            returnMap.put("msg", msg);
        } else {
            returnMap.put("flag", false);
            returnMap.put("msg", msg);
            returnMap.put("billid", billid);
        }
        return returnMap;
    }

    @Override
    public PromotionPackageGroup getOrderGoodsPromotionDetailInfo(String groupid) throws Exception {

        PromotionPackageGroup promotionPackageGroup = getSalesPromotionMapper().getBundleGroup(groupid);
        if(null != promotionPackageGroup){
            List<PromotionPackageGroupDetail> detailList = getSalesPromotionMapper().getDetailByGroupid(groupid);
            for(PromotionPackageGroupDetail detail:detailList){
                GoodsInfo goodsInfo = getAllGoodsInfoByID(detail.getGoodsid());
                if(null != goodsInfo){
                    detail.setGoodsname(goodsInfo.getName());
                    detail.setGoodsInfo(goodsInfo);
                    detail.setBoxPrice(detail.getPrice().multiply(goodsInfo.getBoxnum()).setScale(2,BigDecimal.ROUND_HALF_UP));
                    if(null!=detail.getPrice() && detail.getPrice().compareTo(BigDecimal.ZERO)!=0){
                        BigDecimal notaxprice = getNotaxAmountByTaxAmount(detail.getPrice(),goodsInfo.getDefaulttaxtype());
                        detail.setNotaxprice(notaxprice);
                    }

                    String storageid = "";
                    StorageSummary storageSummary = null;
                    if (StringUtils.isNotEmpty(storageid)) {
                        storageSummary = storageForSalesService.getStorageSummarySumByGoodsidAndStorageid(detail.getGoodsid(), storageid);
                    } else {
                        storageSummary = storageForSalesService.getStorageSummarySumByGoodsid(detail.getGoodsid());
                    }
                    if (null != storageSummary) {
                        detail.setUsablenum(storageSummary.getUsablenum());

                    } else {
                        detail.setUsablenum(BigDecimal.ZERO);
                    }
                }
            }
            promotionPackageGroup.setGroupDetails(detailList);
        }
        return promotionPackageGroup;
    }

    /**
     * 根据订单编号获取分组编号 获取促销信息
     *
     * @param groupid
     * @param orderid
     * @return
     * @throws Exception
     */
    @Override
    public PromotionPackageGroup getOrderGoodsPromotionDetailInfoByOrderid(String orderid, String groupid) throws Exception {
        PromotionPackageGroup promotionPackageGroup = getSalesPromotionMapper().getBundleGroup(groupid);
        if(null != promotionPackageGroup){
            //该订单已经赠送促销分数
            BigDecimal addPronum = BigDecimal.ZERO;
            if(StringUtils.isNotEmpty(orderid)){
                Map map  = getSalesOrderDetailMapper().getOrderPromotionGroupByGroupid(orderid, groupid);
                if(null!=map) {
                    BigDecimal unitnum = (BigDecimal) map.get("unitnum");
                    BigDecimal basenum = (BigDecimal) map.get("basenum");
                    addPronum = unitnum.divide(basenum, 0, BigDecimal.ROUND_DOWN);
                }
            }
            promotionPackageGroup.setRemainnum(promotionPackageGroup.getRemainnum().add(addPronum));
            List<PromotionPackageGroupDetail> detailList = getSalesPromotionMapper().getDetailByGroupid(groupid);
            for(PromotionPackageGroupDetail detail:detailList){
                GoodsInfo goodsInfo = getAllGoodsInfoByID(detail.getGoodsid());
                if(null != goodsInfo){
                    detail.setGoodsname(goodsInfo.getName());
                    detail.setGoodsInfo(goodsInfo);
                    detail.setBoxPrice(detail.getPrice().multiply(goodsInfo.getBoxnum()).setScale(2,BigDecimal.ROUND_HALF_UP));
                    if(null!=detail.getPrice() && detail.getPrice().compareTo(BigDecimal.ZERO)!=0){
                        BigDecimal notaxprice = getNotaxAmountByTaxAmount(detail.getPrice(),goodsInfo.getDefaulttaxtype());
                        detail.setNotaxprice(notaxprice);
                    }

                    String storageid = "";
                    StorageSummary storageSummary = null;
                    if (StringUtils.isNotEmpty(storageid)) {
                        storageSummary = storageForSalesService.getStorageSummarySumByGoodsidAndStorageid(detail.getGoodsid(), storageid);
                    } else {
                        storageSummary = storageForSalesService.getStorageSummarySumByGoodsid(detail.getGoodsid());
                    }
                    if (null != storageSummary) {
                        detail.setUsablenum(storageSummary.getUsablenum());

                    } else {
                        detail.setUsablenum(BigDecimal.ZERO);
                    }
                }
            }
            promotionPackageGroup.setGroupDetails(detailList);
        }
        return promotionPackageGroup;
    }

    /**
     * 根据商品编码和仓库编号 获取商品库存信息
     *
     * @param goodsid
     * @param storageid
     * @return
     * @throws Exception
     */
    @Override
    public StorageSummary getStorageSummarySumByGoodsidAndStorageid(String goodsid, String storageid) throws Exception {
        StorageSummary storageSummary  = storageForSalesService.getStorageSummarySumByGoodsidAndStorageid(goodsid, storageid);
        return storageSummary;
    }

    /**
     * 根据商品编码获取商品库存信息
     *
     * @param goodsid
     * @return
     * @throws Exception
     */
    @Override
    public StorageSummary getStorageSummarySumByGoodsid(String goodsid) throws Exception {
        StorageSummary storageSummary = storageForSalesService.getStorageSummarySumByGoodsid(goodsid);
        return storageSummary;
    }

    /**
     * 根据订单编号获取订单的历史修改记录（版本信息）
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public List showOrderVersionList(String id) throws Exception {
        List<Order> orderList = getSalesOrderDetailMapper().showOrderVersionList(id);
        for (Order order : orderList) {
            Personnel indoorPerson = getPersonnelById(order.getIndooruserid());
            if (null != indoorPerson) {
                order.setIndoorusername(indoorPerson.getName());
            }
            DepartMent departMent = getDepartMentById(order.getSalesdept());
            if (departMent != null) {
                order.setSalesdept(departMent.getName());
            }
            Personnel personnel = getPersonnelById(order.getSalesuser());
            if (personnel != null) {
                order.setSalesuser(personnel.getName());
            }
            Map map = new HashMap();
            map.put("id", order.getCustomerid());
            Customer customer = getCustomerByID(order.getCustomerid());
            if (customer != null) {
                order.setCustomerid(customer.getId());
                order.setCustomername(customer.getName());
            }
            Map total = getSalesOrderDetailMapper().getOrderVersionDetailTotal(order.getId(), order.getVersion());
            if (total != null) {
                if (total.containsKey("taxamount")) {
                    order.setField01(total.get("taxamount").toString());
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
        return orderList;
    }

    /**
     * 获取订单指定版本详细信息
     *
     * @param id
     * @param version
     * @return
     * @throws Exception
     */
    @Override
    public Order getOrderVersion(String id, String version) throws Exception {
        Order order = getSalesOrderDetailMapper().getOrderVersion(id, version);
        if(null!=order){
            Customer customer = getCustomerByID(order.getCustomerid());
            if (customer != null) {
                order.setCustomername(customer.getName());
            }
            String orderStr = null;
            if (null != order && "1".equals(order.getIsgoodsseq())) {
                orderStr = "goodsid";
            }
            List<OrderDetail> orderDetaiList = getSalesOrderDetailMapper().getOrderVersionDetailByOrderWithDiscount(id, version,orderStr);
            //商品库存缓存 防止同一张单多条商品 可用量问题
            Map storageGoodsMap = new HashMap();
            for (OrderDetail orderDetail : orderDetaiList) {
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
            order.setOrderDetailList(orderDetaiList);
        }
        return order;
    }

    /**
     * 根据订单编号 生成订单的版本记录
     * @param id
     * @return
     */
    public boolean addOrderVersionByID(String id){
        int version = getSalesOrderDetailMapper().getOrderVersionByID(id);
        version ++;
        getSalesOrderDetailMapper().addOrderWithVersion(id,version);
        getSalesOrderDetailMapper().addOrderDetailWithVersion(id,version);
        return true;
    }

    @Override
    public boolean getFullFreeDetailByCustomeridAndGoodsid(String orderid,String customerid,String goodsid, BigDecimal num) throws Exception {
        boolean flag = false;
        Map queryMap = new HashMap();
        queryMap.put("goodsid", goodsid);
        Customer customer = getCustomerByID(customerid);
        //1单一客户2促销分类3分类客户4价格套5销售区域7信用等级6总店客户
        if (customer != null) {
            Map priceMap = new HashMap();
            //单一客户
            priceMap.put("type", "1");
            priceMap.put("customerid", customerid);
            //促销分类
            String csort = customer.getPromotionsort();
            if (StringUtils.isNotEmpty(csort)) {
                priceMap.put("promotionsort", csort);
            }
            //取客户的默认分类
            String customerSort = customer.getCustomersort();
            if (StringUtils.isNotEmpty(customerSort)) {
                priceMap.put("customersort", customerSort);
            }
            //取客户价格套
            String priceSort = customer.getPricesort();
            if (StringUtils.isNotEmpty(priceSort)) {
                priceMap.put("pricesort", priceSort);
            }
            //取客户所属销售区域
            String saleArea = customer.getSalesarea();
            if (StringUtils.isNotEmpty(saleArea)) {
                priceMap.put("salesarea", saleArea);
            }
            //取客户 总店
            String pcustomerid = customer.getPid();
            if (StringUtils.isNotEmpty(pcustomerid)) {
                priceMap.put("pcustomerid", pcustomerid);
            }
            //取信用等级
            String credit = customer.getCreditrating();
            if (StringUtils.isNotEmpty(credit)) {
                priceMap.put("credit", credit);
            }
            //取核销方式
            String canceltype = customer.getCanceltype();
            if (StringUtils.isNotEmpty(canceltype)) {
                priceMap.put("canceltype", canceltype);
            }
            queryMap.put("promotionMap",priceMap);
            List<PromotionPackageGroup> list = getSalesPromotionMapper().getPromotionPackageGroupByCustomerAndGoods(queryMap);
            for(PromotionPackageGroup promotionPackageGroup : list){
                BigDecimal punitnum = BigDecimal.ZERO;
                punitnum = promotionPackageGroup.getUnitnum();
                if(punitnum.compareTo(BigDecimal.ZERO)==1){
                    BigDecimal unFreeNum = num;
                    Map salesMap = getSalesOrderDetailMapper().getGoodsSalesInfoByCustomeridAndGoodsid(orderid,promotionPackageGroup.getBillid(),customerid, goodsid, promotionPackageGroup.getBegindate(), promotionPackageGroup.getEnddate());
                    if(null!=salesMap && salesMap.containsKey("unitnum") && salesMap.containsKey("unitnum")){
                        BigDecimal unitnum = (BigDecimal) salesMap.get("unitnum");
                        BigDecimal taxamount = (BigDecimal) salesMap.get("taxamount");
                        unFreeNum = unFreeNum.add(unitnum);
                    }
                    if(unFreeNum.compareTo(punitnum)>=0){
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    @Override
    public List getFullFreeListPage(String orderid,String customerid, String goodsid,BigDecimal num) throws Exception {
        List dataList = new ArrayList();
        Map queryMap = new HashMap();
        queryMap.put("goodsid", goodsid);
        Customer customer = getCustomerByID(customerid);
        //1单一客户2促销分类3分类客户4价格套5销售区域7信用等级6总店客户
        if (customer != null) {
            Map priceMap = new HashMap();
            //单一客户
            priceMap.put("type", "1");
            priceMap.put("customerid", customerid);
            //促销分类
            String csort = customer.getPromotionsort();
            if (StringUtils.isNotEmpty(csort)) {
                priceMap.put("promotionsort", csort);
            }
            //取客户的默认分类
            String customerSort = customer.getCustomersort();
            if (StringUtils.isNotEmpty(customerSort)) {
                priceMap.put("customersort", customerSort);
            }
            //取客户价格套
            String priceSort = customer.getPricesort();
            if (StringUtils.isNotEmpty(priceSort)) {
                priceMap.put("pricesort", priceSort);
            }
            //取客户所属销售区域
            String saleArea = customer.getSalesarea();
            if (StringUtils.isNotEmpty(saleArea)) {
                priceMap.put("salesarea", saleArea);
            }
            //取客户 总店
            String pcustomerid = customer.getPid();
            if (StringUtils.isNotEmpty(pcustomerid)) {
                priceMap.put("pcustomerid", pcustomerid);
            }
            //取信用等级
            String credit = customer.getCreditrating();
            if (StringUtils.isNotEmpty(credit)) {
                priceMap.put("credit", credit);
            }
            //取核销方式
            String canceltype = customer.getCanceltype();
            if (StringUtils.isNotEmpty(canceltype)) {
                priceMap.put("canceltype", canceltype);
            }
            queryMap.put("promotionMap",priceMap);
            List<PromotionPackageGroup> list = getSalesPromotionMapper().getPromotionPackageGroupByCustomerAndGoods(queryMap);

            for(PromotionPackageGroup promotionPackageGroup : list){
                BigDecimal punitnum = BigDecimal.ZERO;
                punitnum = promotionPackageGroup.getUnitnum();
                if(punitnum.compareTo(BigDecimal.ZERO)==1){
                    BigDecimal unFreeNum = num;
                    BigDecimal lastNum = BigDecimal.ZERO;
                    Map salesMap = getSalesOrderDetailMapper().getGoodsSalesInfoByCustomeridAndGoodsid(orderid,promotionPackageGroup.getGroupid(),customerid, goodsid, promotionPackageGroup.getBegindate(), promotionPackageGroup.getEnddate());
                    if(null!=salesMap && salesMap.containsKey("unitnum") && salesMap.containsKey("unitnum")){
                        BigDecimal unitnum = (BigDecimal) salesMap.get("unitnum");
                        BigDecimal taxamount = (BigDecimal) salesMap.get("taxamount");
                        unFreeNum = unFreeNum.add(unitnum);
                        lastNum = unitnum;
                    }
                    GoodsInfo freeGoods = getAllGoodsInfoByID(goodsid);
                    Map freeauxmap = countGoodsInfoNumber(goodsid, freeGoods.getAuxunitid(), unFreeNum);
                    String freeauxnumdetail = "";
                    if (freeauxmap.containsKey("auxnumdetail")) {
                        freeauxnumdetail = (String) freeauxmap.get("auxnumdetail");
                    }
                    Map thisNumMap = countGoodsInfoNumber(goodsid, freeGoods.getAuxunitid(), num);
                    String thisNumdetail = "";
                    if (thisNumMap.containsKey("auxnumdetail")) {
                        thisNumdetail = (String) thisNumMap.get("auxnumdetail");
                    }
                    Map lastNumMap = countGoodsInfoNumber(goodsid, freeGoods.getAuxunitid(), lastNum);
                    String lastNumdetail = "";
                    if (lastNumMap.containsKey("auxnumdetail")) {
                        lastNumdetail = (String) lastNumMap.get("auxnumdetail");
                    }

                    if(unFreeNum.compareTo(punitnum)>=0){
                        //计算赠送赠品的份数
                        int sendCount = unFreeNum.divide(punitnum,2,BigDecimal.ROUND_HALF_UP).intValue();

                        int initSendCount = sendCount;
                        //判断赠品是否足够赠送
                        if(promotionPackageGroup.getLimitnum().compareTo(BigDecimal.ZERO)==1 && promotionPackageGroup.getRemainnum().compareTo(BigDecimal.valueOf(sendCount))==-1){
                            sendCount = promotionPackageGroup.getRemainnum().intValue();
                        }
                        List<PromotionPackageGroupDetail> detailList = getSalesPromotionMapper().getDetailByGroupid(promotionPackageGroup.getGroupid());
                        if(null!=detailList && detailList.size()>0){
                            //主体商品价格
                            BigDecimal goodsprice = BigDecimal.ZERO;
                            BigDecimal goodsNotaxprice = BigDecimal.ZERO;
                            for(PromotionPackageGroupDetail detail : detailList){
                                GoodsInfo goodsInfo = getAllGoodsInfoByID(detail.getGoodsid());
                                if("1".equals(detail.getGtype())){
                                    goodsprice = detail.getPrice();
                                    goodsNotaxprice = getNotaxAmountByTaxAmount(goodsprice,goodsInfo.getDefaulttaxtype());
                                }else if("2".equals(detail.getGtype())){
                                    Map dataMap = new HashMap();
                                    BigDecimal unitnum = detail.getUnitnum().multiply(new BigDecimal(sendCount));

                                    if(null!=goodsInfo){
                                        Map auxmap = countGoodsInfoNumber(detail.getGoodsid(), goodsInfo.getAuxunitid(), unitnum);
                                        //总箱数
                                        BigDecimal totalbox = BigDecimal.ZERO;
                                        //订单中箱数
                                        BigDecimal auxnum = BigDecimal.ZERO;
                                        //订单中个数
                                        BigDecimal overnum = BigDecimal.ZERO;
                                        String auxnumdetail = "";
                                        if (auxmap.containsKey("auxnumdetail")) {
                                            auxnumdetail = (String) auxmap.get("auxnumdetail");
                                        }
                                        if (auxmap.containsKey("auxremainder")) {
                                            overnum = new BigDecimal(auxmap.get("auxremainder").toString()); //辅单位数量
                                        }
                                        if (auxmap.containsKey("auxInteger")) {
                                            auxnum = new BigDecimal(auxmap.get("auxInteger").toString());
                                        }
                                        if (auxmap.containsKey("auxnum")) {
                                            totalbox = (BigDecimal) auxmap.get("auxnum");
                                        }

                                        dataMap.put("goodsid", detail.getGoodsid());
                                        dataMap.put("goodsname", goodsInfo.getName());
                                        dataMap.put("boxnum", goodsInfo.getBoxnum().intValue());
                                        dataMap.put("groupid", detail.getGroupid());
                                        //符合赠送最低需要的数量
                                        BigDecimal passNum = punitnum.multiply(new BigDecimal(sendCount));
                                        Map passNumMap = countGoodsInfoNumber(goodsid, freeGoods.getAuxunitid(), passNum);
                                        String passNumdetail = "";
                                        if (passNumMap.containsKey("auxnumdetail")) {
                                            passNumdetail = (String) passNumMap.get("auxnumdetail");
                                        }
                                        BigDecimal moreNum = unFreeNum.subtract(passNum);
                                        Map moreNumMap = countGoodsInfoNumber(goodsid, freeGoods.getAuxunitid(), moreNum);
                                        String moreNumdetail = "";
                                        if (moreNumMap.containsKey("auxnumdetail")) {
                                            moreNumdetail = (String) moreNumMap.get("auxnumdetail");
                                        }

                                        String groupMsg = "满赠单据："+promotionPackageGroup.getBillid()+","+promotionPackageGroup.getGroupname()+";在<font style=\"font-weight:bold\">"+promotionPackageGroup.getBegindate()+"</font>到<font style=\"font-weight:bold\">"+promotionPackageGroup.getEnddate()+"</font>，满<font style=\"font-weight:bold\">"+promotionPackageGroup.getAuxnumdetail()+"</font>,送(1)份赠品,";
                                        if(promotionPackageGroup.getLimitnum().intValue()>0){
                                            groupMsg += "总共赠送<font style=\"font-weight:bold\">"+promotionPackageGroup.getLimitnum().intValue()+"</font>（剩<font style=\"font-weight:bold\">"+promotionPackageGroup.getRemainnum().intValue()+"</font>）份<br/>";
                                        }else{
                                            groupMsg += "<font color=red>无赠送上限。</font><br/>";
                                        }
                                        groupMsg += "总数量：<font style=\"font-weight:bold\">"+freeauxnumdetail+"</font>，本次下单数量：<font style=\"font-weight:bold\">"+thisNumdetail+"</font>，上次未赠送数量：<font style=\"font-weight:bold\">"+lastNumdetail+"</font></br>";

                                        if(initSendCount==sendCount){
                                            groupMsg += "符合条件赠送数量:<font style=\"font-weight:bold\">"+passNumdetail+"</font>，多余不赠送数量：<font color=red>"+moreNumdetail+"</font>，总共赠送(<font style=\"font-weight:bold\">"+sendCount+"</font>)份。";
                                            dataMap.put("groupname", groupMsg);
                                        }else{
                                            groupMsg += "符合赠送条件数量:<font style=\"font-weight:bold\">"+passNumdetail+"</font>，多余不赠送数量：<font color=red>"+moreNumdetail+"</font>，总共赠送(<font style=\"font-weight:bold\">"+sendCount+"</font>)份，<font color=red>已达到赠送上限。</font>";
                                            dataMap.put("groupname", groupMsg);
                                        }

                                        dataMap.put("deliverytype", "1");
                                        dataMap.put("unitid", goodsInfo.getMainunit());
                                        dataMap.put("unitname", goodsInfo.getMainunitName());
                                        dataMap.put("auxunitid", goodsInfo.getAuxunitid());
                                        dataMap.put("auxunitname", goodsInfo.getAuxunitname());
                                        dataMap.put("sendnumdetail", detail.getAuxnumdetail());
                                        dataMap.put("unitnum", unitnum);
                                        dataMap.put("totalbox", totalbox);
                                        dataMap.put("auxnum", auxnum);
                                        dataMap.put("overnum", overnum);
                                        dataMap.put("auxnumdetail", auxnumdetail);
                                        dataMap.put("goodsInfo", goodsInfo);
                                        //主体商品的价格
                                        dataMap.put("mainGoodsPrice", goodsprice);
                                        dataMap.put("mainGoodsNotaxPrice", goodsNotaxprice);
                                        dataList.add(dataMap);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return dataList;
    }

    public Map insertOrderByModel(ModelOrder modelOrder) throws  Exception{

        String customerid = modelOrder.getBusid();
        String oldOrderID = modelOrder.getOrderId();
        String remark= modelOrder.getRemark();
        String othermsg = modelOrder.getOtherMsg();
        Map map = new HashMap();
        SysUser sysUser = getSysUser();
        Order order = new Order();
        String id = "" ;
        if (isAutoCreate("t_sales_order")) {
            // 获取自动编号
            id = getAutoCreateSysNumbderForeign(order, "t_sales_order");
            order.setId(id);
        }else{
            id = "orderByMo-"+ CommonUtils.getDataNumberSendsWithRand();
            order.setId(id);
        }
        Customer c = getCustomerByID(customerid);
        if(null!=c){
            order.setCustomerid(c.getId());//设置默认客户
            order.setStatus("2");
            if(StringUtils.isNotEmpty(oldOrderID)){
                order.setSourceid(oldOrderID);
            }
            if(StringUtils.isNotEmpty(remark)){
                order.setRemark(remark);
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            //模板导入中的业务日期或其它除备注以外的信息，如果该信息是日期，则作为订单日期导入
            if(StringUtils.isNotEmpty(othermsg)){
                order.setField07(othermsg);
                try{
                    Date date = format.parse(othermsg);
                    order.setBusinessdate(othermsg);
                }catch (Exception e){
                    order.setBusinessdate(format.format(new Date()));
                }

            }
            if(StringUtils.isEmpty(order.getBusinessdate())){
                order.setBusinessdate(format.format(new Date()));
            }
            order.setAdduserid(sysUser.getUserid());
            order.setAddusername(sysUser.getName());
            order.setAdddeptid(sysUser.getDepartmentid());
            order.setAdddeptname(sysUser.getDepartmentname());

            //销售内勤
            SysUser indoorsysUser = getSalesIndoorSysUserByCustomerid(c.getId());
            order.setIndooruserid(indoorsysUser.getPersonnelid());
            //获取销售区域上级客户
            Customer customer = getCustomerByID(c.getId());
            if (null != customer) {
                order.setSalesarea(customer.getSalesarea());
                order.setPcustomerid(customer.getPid());
                order.setCustomersort(customer.getCustomersort());
                order.setSalesdept(customer.getSalesdeptid());
                order.setSalesuser(customer.getSalesuserid());
            }
            boolean flag = getSalesOrderMapper().addOrder(order) > 0;
            map.put("flag",flag);
            map.put("order",order);
        }else{
            map.put("flag",false);
            map.put("busidEmpty",true);
            map.put("customerid",customerid);
        }
        return map ;
    }

    /**
     * 根据销售订单及订单明细信息进行商品相关的仓库、部门、人员信息添加
     * @param orderDetail
     * @param goodsInfo
     * @param order
     * @author lin_xx
     * @return
     * @throws Exception
     */
    public OrderDetail addDetailInfo(OrderDetail orderDetail,GoodsInfo goodsInfo,Order order) throws Exception{
        orderDetail.setGoodsid(goodsInfo.getId());
        orderDetail.setGoodssort(goodsInfo.getDefaultsort());//商品分类
        //获取品牌部门
        Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
        if (null != brand) {
            orderDetail.setBranddept(brand.getDeptid());
        }
        //供应商
        orderDetail.setSupplierid(goodsInfo.getDefaultsupplier());
        orderDetail.setBrandid(goodsInfo.getBrand());
        //主单位 辅单位信息
        orderDetail.setUnitid(goodsInfo.getMainunit());
        orderDetail.setUnitname(goodsInfo.getMainunitName());
        //根据客户编号和品牌编号 获取品牌业务员
        orderDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), order.getCustomerid()));
        //厂家业务员
        orderDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), order.getCustomerid()));

        //辅单位信息
        MeteringUnit meteringUnit = getGoodsAuxUnitInfoByGoodsid(goodsInfo.getId());
        orderDetail.setAuxunitid(meteringUnit.getId());
        orderDetail.setAuxunitname(meteringUnit.getName());

        return orderDetail ;
    }

    /**
     * 添加模板中的折扣明细
     * @author lin_xx
     * @date 2017/6/7
     */
    public List addSalesOrderDiscountDetail(ModelOrder modelOrder,Map brandGoodsMap,String id) throws Exception{
        List<Map<String, Object>> errorList = new ArrayList();
        BigDecimal disamount = new BigDecimal(modelOrder.getTaxprice());
        List<OrderDetail> list = (List<OrderDetail>) brandGoodsMap.get(modelOrder.getGoodsid());
        //商品明细含税金额总和
        BigDecimal totalAmount = new BigDecimal(0);
        if(null == list || list.size() == 0){
            Map errorMap = new HashMap();
            errorMap.put("orderid",modelOrder.getOrderId());
            errorMap.put("unitnum",modelOrder.getUnitnum());
            errorMap.put("price",modelOrder.getTaxprice());
            errorList.add(errorMap);
        }else{
            for(OrderDetail detail : list){
                totalAmount = totalAmount.add(detail.getTaxamount());
            }
            for(OrderDetail detail : list){
                //当前商品明细分摊比例
                BigDecimal disrate = detail.getTaxamount().divide(totalAmount,6,BigDecimal.ROUND_HALF_UP);
                BigDecimal taxamount = disamount.multiply(disrate);
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderid(id);
                orderDetail.setGoodsid(detail.getGoodsid());
                orderDetail.setGoodssort(detail.getGoodssort());
                orderDetail.setBrandid(detail.getBrandid());
                orderDetail.setBranduser(detail.getBranduser());
                orderDetail.setBranddept(detail.getBranddept());
                orderDetail.setSupplierid(detail.getSupplierid());
                orderDetail.setIsdiscount("1");
                orderDetail.setIsbranddiscount("1");
                orderDetail.setTaxamount(taxamount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP));

                BigDecimal rate = BigDecimal.ZERO;
                TaxType taxType = getTaxType(detail.getTaxtype()); //获取默认税种
                if (taxType != null) {
                    orderDetail.setTaxtype(taxType.getId()); //税种档案中的编码
                    orderDetail.setTaxtypename(taxType.getName()); //税种名称
                }
                //无税金额 = 含税金额/（1+税率） 保存6位小数
                BigDecimal notaxamount = getNotaxAmountByTaxAmount(orderDetail.getTaxamount(),orderDetail.getTaxtype());
                orderDetail.setNotaxamount(notaxamount);
                orderDetail.setTax(orderDetail.getTaxamount().subtract(orderDetail.getNotaxamount()));

                getSalesOrderDetailMapper().addOrderDetail(orderDetail);
            }
        }

        return errorList;
    }

    /**
     * 模板导入数据
     * @return
     * @throws Exception
     */
    @Override
    public Map addMDSalesOrder(List<ModelOrder> wareList,String ctype,String gtype)throws Exception{
        int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
        Map map = new HashMap();
        //插入销售订单
        Map map1 = insertOrderByModel(wareList.get(0));
        List<String> errorBarcode = new ArrayList<String>();
        List<String> errorGoodsid = new ArrayList<String>();
        List<String> errorShopid = new ArrayList<String>();
        List<String> insesrtData = new ArrayList<String>();
        List<String> errorSpell = new ArrayList<String>();
        List<String> disableInfo = new ArrayList<String>();
        List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
        int seq = 1;
        //按品牌对商品进行整合
        Map<String,List<OrderDetail>> brandGoodsMap = new HashMap<String, List<OrderDetail>>();
        //订单折扣组装该订单所有商品明细
        List<OrderDetail> detailList = new ArrayList<OrderDetail>();
        if((Boolean) map1.get("flag")){
            Order order = (Order) map1.get("order");
            for(ModelOrder modelOrder : wareList){
                Brand brand = getGoodsBrandByID(modelOrder.getGoodsid());
                if(null != brand && brandGoodsMap.size() > 0){
                    //模板折扣添加
                   errorList = addSalesOrderDiscountDetail(modelOrder,brandGoodsMap,order.getId());
                }else{
                    GoodsInfo goodsInfo = new GoodsInfo();
                    Map goodsMap = new HashMap();
                    //模板取商品编码参数时 gtype 修改后为4 兼容以前商品导入类型 0
                    if(("0".equals(gtype) || "4".equals(gtype)) && StringUtils.isNotEmpty(modelOrder.getGoodsid())){
                        goodsInfo = getGoodsInfoByID(modelOrder.getGoodsid());
                        if(null == goodsInfo || "0".equals(goodsInfo.getState())){
                            goodsMap.put("id",modelOrder.getGoodsid());
                        }
                    }else if("1".equals(gtype) && StringUtils.isNotEmpty(modelOrder.getBarcode())){
                        String barcode = modelOrder.getBarcode();
                        String barcode1 = "";
                        for(int i=0;i<barcode.length();i++){
                            if(barcode.charAt(i)>=48 && barcode.charAt(i)<=57){
                                barcode1+=barcode.charAt(i);
                            }
                        }
                        modelOrder.setBarcode(barcode1);
                        goodsInfo = getGoodsInfoByBarcode(barcode1);//根据条形码查商品
                        if(null == goodsInfo || "0".equals(goodsInfo.getState())){
                            goodsMap.put("barcode",barcode1);
                        }
                    }else if("2".equals(gtype) && "2".equals(ctype) && StringUtils.isNotEmpty(modelOrder.getShopid())){//商品按店内码导入 客户为指定总店按店号
                        CustomerPrice customerPrice =
                                customerMapper.getCustomerPriceByCustomerAndShopid(modelOrder.getMainbusid(),modelOrder.getShopid());
                        if(customerPrice != null) {
                            goodsInfo = getGoodsInfoByID(customerPrice.getGoodsid());
                        }else{
                            Customer customer=getCustomerByID(modelOrder.getBusid());
                            if(customer!=null){
                                customerPrice =customerMapper.getCustomerPriceByCustomerAndShopid(customer.getPid(),modelOrder.getShopid());
                                if(customerPrice != null) {
                                    goodsInfo = getGoodsInfoByID(customerPrice.getGoodsid());
                                }
                            }
                        }
                    }else if("2".equals(gtype) && !"2".equals(ctype) && StringUtils.isNotEmpty(modelOrder.getShopid())){//商品按店内码导入
                        CustomerPrice customerPrice =
                                customerMapper.getCustomerPriceByCustomerAndShopid(modelOrder.getBusid(),modelOrder.getShopid());
                        if(customerPrice != null) {
                            goodsInfo = getGoodsInfoByID(customerPrice.getGoodsid());
                        }else{
                            Customer customer=getCustomerByID(modelOrder.getBusid());
                            if(customer!=null){
                                customerPrice =customerMapper.getCustomerPriceByCustomerAndShopid(customer.getPid(), modelOrder.getShopid());
                                if(customerPrice != null) {
                                    goodsInfo = getGoodsInfoByID(customerPrice.getGoodsid());
                                }
                            }
                        }
                    }else if("3".equals(gtype) && StringUtils.isNotEmpty(modelOrder.getSpell())){//按商品助记符导入
                        goodsInfo = getGoodsInfoBySpell(modelOrder.getSpell());
                        if(null == goodsInfo || "0".equals(goodsInfo.getState())){
                            goodsMap.put("spell",modelOrder.getSpell());
                        }
                    }
                    if(goodsMap.size() > 0){//按条形码或者助记符导入的商品导入不成功，判断不存在还是有相同条形码或助记符
                        List<GoodsInfo> goodsInfoList = getBaseGoodsMapper().getGoodsInfoListByMap(goodsMap);
                        for(GoodsInfo gInfo : goodsInfoList){
                            if(null != gInfo && "1".equals(gInfo.getState())){
                                goodsInfo = getAllGoodsInfoByID(gInfo.getId());
                                break;
                            }
                        }
                    }
                    //禁用商品不导入
                    if(null != goodsInfo && "0".equals(goodsInfo.getState())){
                        if("0".equals(gtype)){
                            disableInfo.add(modelOrder.getGoodsid());
                        }else if("1".equals(gtype)){
                            disableInfo.add(modelOrder.getBarcode());
                        }else if("2".equals(gtype)){
                            disableInfo.add(modelOrder.getShopid());
                        }else if("3".equals(gtype)){
                            disableInfo.add(modelOrder.getSpell());
                        }else if("4".equals(gtype)){
                            disableInfo.add(modelOrder.getGoodsid());
                        }
                        continue;
                    }
                    if(null != goodsInfo && StringUtils.isNotEmpty(goodsInfo.getId())){
                        insesrtData.add(goodsInfo.getId());
                        goodsInfo = getAllGoodsInfoByID(goodsInfo.getId());
                        OrderDetail orderDetail = new OrderDetail();
                        orderDetail.setOrderid(order.getId());
                        //商品相关仓库部门人员信息添加
                        orderDetail = addDetailInfo(orderDetail,goodsInfo,order);
                        orderDetail.setSeq(seq);
                        ++seq;
                        //如果单位是辅数量单位，则根据箱数计算总数量
                        if(StringUtils.isNotEmpty(modelOrder.getUnitname()) &&
                                modelOrder.getUnitname().equals(goodsInfo.getAuxunitname())){
                            BigDecimal boxnum = goodsInfo.getBoxnum();
                            BigDecimal num2 = boxnum.multiply(new BigDecimal(modelOrder.getUnitnum()));
                            modelOrder.setUnitnum(num2.toString());
                        }

                        String numStr = modelOrder.getUnitnum();
                        if(StringUtils.isNotEmpty(numStr)){
                            //剔除数量中的文字
                            String reg = "[\u4e00-\u9fa5]";
                            Pattern pat = Pattern.compile(reg);
                            Matcher mat=pat.matcher(numStr);
                            numStr = mat.replaceAll("");
                            //南京 大润发HTML 数量（件数） 只取字符串中的数量
                            if(numStr.contains("(")){
                                numStr = numStr.substring(0,numStr.indexOf("("));
                            }
                        }

                        BigDecimal num = BigDecimal.ZERO;
                        try{
                            if(StringUtils.isNotEmpty(numStr) && !"null".equals(numStr) ){
                                num = new BigDecimal(numStr);
                            }
                        }catch (Exception e){
                            continue;
                        }
                        if(num.compareTo(BigDecimal.ZERO) == 0){//数量为零取箱数
                            BigDecimal boxnum = goodsInfo.getBoxnum();
                            if(null != boxnum && StringUtils.isNotEmpty(modelOrder.getBoxnum())){
                                BigDecimal mdboxnum = new BigDecimal(modelOrder.getBoxnum());
                                if(decimalScale == 0){
                                    mdboxnum = mdboxnum.setScale(decimalScale, BigDecimal.ROUND_DOWN);
                                }
                                num = boxnum.multiply(mdboxnum);
                            }
                        }
                        if(num.compareTo(BigDecimal.ZERO) < 0){
                            num = num.multiply(new BigDecimal(-1));
                        }
                        if(decimalScale == 0){
                            num = num.setScale(decimalScale, BigDecimal.ROUND_DOWN);
                        }
                        orderDetail.setFixnum(num);
                        orderDetail.setUnitnum(num);
                        //根据条件获取商品销售信息
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String date = dateFormat.format(new Date());
                        OrderDetail detail = getGoodsDetail(goodsInfo.getId(),modelOrder.getBusid(),date,num,"");
                        orderDetail.setRemark(detail.getRemark());
                        //数量信息
                        orderDetail.setAuxnum(detail.getAuxnum());
                        orderDetail.setOvernum(detail.getOvernum());
                        orderDetail.setAuxnumdetail(detail.getAuxnumdetail());
                        orderDetail.setTotalbox(detail.getTotalbox());

                        //价格(原价 成本价 含税价 未税价等)
                        if(StringUtils.isEmpty(modelOrder.getTaxprice())){
                            orderDetail.setOldprice(detail.getFixprice());
                            orderDetail.setFixprice(detail.getFixprice());
                            orderDetail.setCostprice(getGoodsCostprice(order.getStorageid(),goodsInfo));
                            orderDetail.setLowestsaleprice(detail.getLowestsaleprice());
                            orderDetail.setTaxprice(detail.getTaxprice());
                            orderDetail.setNotaxprice(detail.getNotaxprice());
                            orderDetail.setBoxprice(detail.getBoxprice());
                            //取特价时，关联特价单据编号
                            if(StringUtils.isNotEmpty(detail.getGroupid())){
                                orderDetail.setGroupid(detail.getGroupid());
                            }
                        }else{
                            String reg = "￥|$";
                            Pattern pat = Pattern.compile(reg);
                            Matcher mat=pat.matcher(modelOrder.getTaxprice());
                            String price = mat.replaceAll("").trim();
                            BigDecimal taxprice = new BigDecimal(price);
                            OrderDetail fixOrderDetail = getFixGoodsDetail(goodsInfo.getId(),modelOrder.getBusid());
                            orderDetail.setFixprice(fixOrderDetail.getTaxprice());
                            orderDetail.setOldprice(fixOrderDetail.getTaxprice());
                            orderDetail.setCostprice(getGoodsCostprice(order.getStorageid(),goodsInfo));
                            orderDetail.setLowestsaleprice(fixOrderDetail.getLowestsaleprice());
                            orderDetail.setTaxprice(taxprice);

                            BigDecimal rate = BigDecimal.ZERO;
                            TaxType taxType = getTaxType(goodsInfo.getDefaulttaxtype()); //获取默认税种
                            if (taxType != null) {
                                orderDetail.setTaxtype(taxType.getId()); //税种档案中的编码
                                orderDetail.setTaxtypename(taxType.getName()); //税种名称
                                rate = taxType.getRate().divide(new BigDecimal(100));
                            }
                            orderDetail.setNotaxprice(taxprice.divide(rate.add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP));
                            orderDetail.setBoxprice(taxprice.multiply(goodsInfo.getBoxnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                        }
                        //税种
                        orderDetail.setTaxtype(detail.getTaxtype());
                        orderDetail.setTaxtypename(detail.getTaxtypename());
                        //含税金额
                        BigDecimal taxamount = num.multiply(orderDetail.getTaxprice());
                        BigDecimal notaxamount = num.multiply(orderDetail.getNotaxprice());

                        orderDetail.setTaxamount(taxamount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
                        orderDetail.setNotaxamount(notaxamount);
                        BigDecimal tax = orderDetail.getTaxamount().subtract(orderDetail.getNotaxamount());
                        orderDetail.setTax(tax);
                        getSalesOrderDetailMapper().addOrderDetail(orderDetail);
                        //根据商品品牌组装订单明细（用于折扣分摊）
                        if(brandGoodsMap.size() > 0 && brandGoodsMap.containsKey(goodsInfo.getBrand())){
                            List list = brandGoodsMap.get(goodsInfo.getBrand());
                            list.add(orderDetail);
                        }else{
                            List list = new LinkedList();
                            list.add(orderDetail);
                            brandGoodsMap.put(goodsInfo.getBrand(),list);
                        }
                        //订单商品明细
                        detailList.add(orderDetail);
                    }else{
                        Map errorMap = new HashMap();
                        if(StringUtils.isNotEmpty(modelOrder.getBarcode()) && "1".equals(gtype)){
                            errorBarcode.add(modelOrder.getBarcode());
                            errorMap.put("goodsid",modelOrder.getBarcode());
                        }else if(StringUtils.isNotEmpty(modelOrder.getShopid()) && "2".equals(gtype)){
                            errorShopid.add(modelOrder.getShopid());
                            errorMap.put("goodsid",modelOrder.getShopid());
                        }else if(StringUtils.isNotEmpty(modelOrder.getSpell()) && "3".equals(gtype)){
                            errorSpell.add(modelOrder.getSpell());
                            errorMap.put("goodsid",modelOrder.getSpell());
                        }else if(StringUtils.isNotEmpty(modelOrder.getGoodsid())){
                            errorGoodsid.add(modelOrder.getGoodsid());
                            errorMap.put("goodsid",modelOrder.getGoodsid());
                        }
                        errorMap.put("customerid",order.getCustomerid());
                        errorMap.put("orderid",modelOrder.getOrderId());
                        errorMap.put("unitnum",modelOrder.getUnitnum());
                        errorMap.put("price",modelOrder.getTaxprice());
                        errorList.add(errorMap);
                    }
                }
            }
            map.put("errorList",errorList);//导入出错记录
            if(null != errorGoodsid && errorGoodsid.size() >0){
                map.put("flag","false");
                map.put("goodsid",errorGoodsid);
            }
            if(null != errorBarcode && errorBarcode.size()>0){
                map.put("flag","false");
                map.put("barcode",errorBarcode);
            }
            if(null != errorShopid && errorShopid.size()>0){
                map.put("flag","false");
                map.put("shopid",errorShopid);
            }
            if(null != errorSpell && errorSpell.size()>0){
                map.put("flag","false");
                map.put("spell",errorSpell);
            }
            if(insesrtData.size() == 0 && null != order){
                getSalesOrderMapper().deleteOrder(order.getId());//商品为空，删除订单信息
                map.put("info","empty");
            }else{
                if(errorBarcode.size()>0){
                    map.put("barcode",errorBarcode);
                }else if(errorShopid.size()>0){
                    map.put("shopid",errorShopid);
                }else  if(errorSpell.size()>0){
                    map.put("spell",errorSpell);
                }else if(errorGoodsid.size() >0){
                    map.put("goodsid",errorGoodsid);
                }
                map.put("flag","true");
            }
        }else{
            map.put("flag","false");
            if(map1.containsKey("busidEmpty")){
                map.put("disablemsg","客户编码:"+map1.get("customerid")+"不存在或禁用;");
            }
        }
        if(disableInfo.size() > 0){
            if("0".equals(gtype)){
                map.put("disablemsg",",商品编码："+disableInfo.toString()+"是禁用商品，不允许导入");
            }else if("1".equals(gtype)){
                map.put("disablemsg",",商品条形码："+disableInfo.toString()+"是禁用商品，不允许导入");
            }else if("2".equals(gtype)){
                map.put("disablemsg",",商品店内码："+disableInfo.toString()+"是禁用商品，不允许导入");
            }else if("3".equals(gtype)){
                map.put("disablemsg",",商品助记符："+disableInfo.toString()+"是禁用商品，不允许导入");
            }else if("4".equals(gtype)){
                map.put("disablemsg",",商品编码："+disableInfo.toString()+"是禁用商品，不允许导入");
            }
        }
        return map ;
    }

    public Map getOrderDetailTotal(String id) throws Exception{
        Map total = getSalesOrderDetailMapper().getOrderDetailTotal(id);
        return total;
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
    public PageData getGoodsByBrandAndSort(PageMap pageMap,String customerid,String storageid,String date,String brands,String defaultsorts)  throws Exception {
        String[] brandArr  =null;
        String[] defaultsortArr=null;
        if(null!=brands){
            brandArr = brands.split(",");
        }
        if(null!=defaultsorts){
            defaultsortArr = defaultsorts.split(",");
        }
        BigDecimal unitNum = BigDecimal.ZERO;
        String sql = getDataAccessRule("t_base_goods_info", null); //数据权限
        pageMap.setDataSql(sql);

        Map priceMap = pageMap.getCondition();
        priceMap.put("customerid", customerid);
        priceMap.put("date",date);
        priceMap.put("brandArr",brandArr);
        priceMap.put("defaultsortArr",defaultsortArr);

        Customer customer = getCustomerByID(customerid);
        if (customer != null) {
            // 促销分类
            String csort = customer.getPromotionsort();
            if (StringUtils.isNotEmpty(csort)) {
                priceMap.put("promotionsort", csort);
            }
            // 取客户的默认分类
            String customerSort = customer.getCustomersort();
            if (StringUtils.isNotEmpty(customerSort)) {
                priceMap.put("customersort", customerSort);
            }
            // 取客户价格套
            String priceSort = customer.getPricesort();
            if (StringUtils.isNotEmpty(priceSort)) {
                priceMap.put("pricesort", priceSort);
            }
            // 取客户所属销售区域
            String saleArea = customer.getSalesarea();
            if (StringUtils.isNotEmpty(saleArea)) {
                priceMap.put("salesarea", saleArea);
            }
            // 取客户 总店
            String pcustomerid = customer.getPid();
            if (StringUtils.isNotEmpty(pcustomerid)) {
                priceMap.put("pcustomerid", pcustomerid);
            }
            // 取信用等级
            String credit = customer.getCreditrating();
            if (StringUtils.isNotEmpty(credit)) {
                priceMap.put("credit", credit);
            }
            // 取核销方式
            String canceltype = customer.getCanceltype();
            if (StringUtils.isNotEmpty(canceltype)) {
                priceMap.put("canceltype", canceltype);
            }
        }
        pageMap.setCondition(priceMap);
        // 分销规则
        {
            Map param = new HashMap();
            param.put("customerid", customer.getId());
            param.put("pcustomerid", CommonUtils.emptyToNull(customer.getPid()));
            param.put("customersort", CommonUtils.emptyToNull(customer.getCustomersort()));
            param.put("salesarea", CommonUtils.emptyToNull(customer.getSalesarea()));
            param.put("creditrating", CommonUtils.emptyToNull(customer.getCreditrating()));
            param.put("canceltype", CommonUtils.emptyToNull(customer.getCanceltype()));
            param.put("promotionsort", CommonUtils.emptyToNull(customer.getPromotionsort()));
            param.put("canbuy", "1");
            List<DistributionRule> okDistributions = distributionRuleMapper.selectDistributionRuleIdByCustomer(param);
            if(okDistributions.size() > 0) {
                pageMap.getCondition().put("okDistributions", okDistributions);
            }

            param.put("canbuy", "0");
            List<DistributionRule> ngDistributions = distributionRuleMapper.selectDistributionRuleIdByCustomer(param);
            if(ngDistributions.size() > 0) {
                pageMap.getCondition().put("ngDistributions", ngDistributions);
            }
        }
        List<GoodsInfo> GoodsInfoList = getBaseFilesGoodsMapper().getGoodsInfoListByBrandAndSort(pageMap);
        List<OrderDetail> list=new ArrayList();
        for(GoodsInfo goodsInfo : GoodsInfoList){
            GoodsInfo backInfo = getAllGoodsInfoByID(goodsInfo.getId());
            String groupid = "";
            String ptype = "";
            String name = goodsInfo.getName();
            BigDecimal taxprice = BigDecimal.ZERO ;
            if(null == backInfo){
                groupid = goodsInfo.getId();
                ptype = goodsInfo.getPtype();
                taxprice = goodsInfo.getHighestbuyprice();
                goodsInfo = getAllGoodsInfoByID(goodsInfo.getOldId());
            }
            OrderDetail orderDetail=getGoodsDetail(goodsInfo.getId(), customerid, date,unitNum, null);
            if(null == backInfo){
                orderDetail.setGoodsid(groupid);
                if("1".equals(ptype)){
                    orderDetail.setDeliverytype("3");

                }else if("2".equals(ptype)){
                    orderDetail.setDeliverytype(ptype);
                    orderDetail.setField05(name);
                }
                orderDetail.setTaxprice(taxprice);
            }else{
                orderDetail.setGoodsid(orderDetail.getGoodsInfo().getId());
            }
            if(null != orderDetail){
                orderDetail.setOldprice(orderDetail.getTaxprice());
                orderDetail.setStorageid(storageid);
                orderDetail.setUnitid(orderDetail.getGoodsInfo().getMainunit());
                orderDetail.setUnitname(orderDetail.getGoodsInfo().getMainunitName());

                //可用量
                StorageSummary storageSummary = null;
                if (StringUtils.isNotEmpty(storageid)) {
                    storageSummary = storageForSalesService.getStorageSummarySumByGoodsidAndStorageid(goodsInfo.getId(), storageid);
                } else {
                    storageSummary = storageForSalesService.getStorageSummarySumByGoodsid(goodsInfo.getId());
                }
                if (null != storageSummary) {
                    orderDetail.setUsablenum(storageSummary.getUsablenum());

                } else {
                    orderDetail.setUsablenum(BigDecimal.ZERO);
                }

                list.add(orderDetail);
            }
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
    public List AddOrderDetailByBrandAndSort(List<OrderDetail>  orderDetailList) throws Exception {
        int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
        List<OrderDetail>  list =new ArrayList();
        for(OrderDetail orderDetail:orderDetailList){
            BigDecimal boxnum = orderDetail.getGoodsInfo().getBoxnum();
            BigDecimal overnum = BigDecimal.ZERO;
            BigDecimal auxnum = BigDecimal.ZERO;
            if(null != orderDetail.getOvernum()){
                overnum=orderDetail.getOvernum();
            }
            if(null != orderDetail.getAuxnum()){
                auxnum=orderDetail.getAuxnum();
            }
            BigDecimal unitnum = boxnum.multiply(auxnum).add(overnum);
            if(overnum.compareTo(boxnum) >= 0){
                BigDecimal mod=overnum.divide(boxnum,0,BigDecimal.ROUND_DOWN);
                auxnum = auxnum.add(mod);
                overnum = overnum.subtract(mod.multiply(boxnum));
            }
            GoodsInfo goodsInfo = getGoodsInfoByID(orderDetail.getGoodsid());
            List<PromotionPackageGroupDetail> detailList = getSalesPromotionMapper().getDetailByGroupid(orderDetail.getGoodsid());
            if(null!=goodsInfo){
                orderDetail.setBrandid(goodsInfo.getBrand());
            }
            if(detailList.size() == 0){
                orderDetail.setOvernum(overnum);
                orderDetail.setAuxnum(auxnum);
                orderDetail.setUnitnum(unitnum);
                orderDetail.setTaxamount(orderDetail.getTaxprice().multiply(orderDetail.getUnitnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                BigDecimal tax=getBaseFilesFinanceMapper().getTaxTypeInfo(orderDetail.getTaxtype()).getRate().divide(new BigDecimal("100"),decimalLen, BigDecimal.ROUND_HALF_UP).add(new BigDecimal("1"));
                orderDetail.setNotaxprice(orderDetail.getTaxprice().divide(tax,decimalLen,BigDecimal.ROUND_HALF_UP));
                BigDecimal notaxamount = getNotaxAmountByTaxAmount(orderDetail.getTaxamount(),orderDetail.getTaxtype());
                orderDetail.setNotaxamount(notaxamount);
                orderDetail.setTax(orderDetail.getTaxamount().subtract(orderDetail.getNotaxamount()));
                orderDetail.setAuxnumdetail(CommonUtils.strDigitNumDeal(Integer.toString(auxnum.intValue())+orderDetail.getAuxunitname()+overnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP)+orderDetail.getUnitname()));
                orderDetail.setIsdiscount("0");
                list.add(orderDetail);
            }else{
                List<OrderDetail> promoList = getPromoListByDetail(detailList, orderDetail);
                list.addAll(promoList);
            }

        }
        return list;
    }

    /**
     * @param detailList
     * @param orderDetail
     * @return
     * @throws Exception
     */
    public List<OrderDetail> getPromoListByDetail
    (List<PromotionPackageGroupDetail> detailList ,OrderDetail orderDetail) throws Exception {

        List<OrderDetail> promotionOrderList = new ArrayList<OrderDetail>();

        for(PromotionPackageGroupDetail promoDetail :detailList){
            OrderDetail detailOrder = new OrderDetail();

            GoodsInfo info = getAllGoodsInfoByID(promoDetail.getGoodsid());
            detailOrder.setGoodsid(info.getId());
            detailOrder.setGoodsInfo(info);
            detailOrder.setTaxtype(info.getDefaulttaxtype());
            detailOrder.setTaxtypename(info.getDefaulttaxtypeName());
            detailOrder.setBrandid(info.getBrand());
            detailOrder.setUnitname(info.getMainunitName());
            detailOrder.setAuxunitname(info.getAuxunitname());
            detailOrder.setGroupid(orderDetail.getGoodsid());//设置促销的group编号

            //可用量
            StorageSummary storageSummary = null;
            if (StringUtils.isNotEmpty(orderDetail.getStorageid())) {
                storageSummary = storageForSalesService.getStorageSummarySumByGoodsidAndStorageid(info.getId(), orderDetail.getStorageid());
            } else {
                storageSummary = storageForSalesService.getStorageSummarySumByGoodsid(info.getId());
            }
            if (null != storageSummary) {
                detailOrder.setUsablenum(storageSummary.getUsablenum());

            } else {
                detailOrder.setUsablenum(BigDecimal.ZERO);
            }

            if("3".equals(orderDetail.getDeliverytype())){//表示买赠产品组

                if ( "1".equals( promoDetail.getGtype() ) ) {//购买的商品

                    BigDecimal intBoxnum=orderDetail.getGoodsInfo().getBoxnum();
                    detailOrder.setOldprice(orderDetail.getGoodsInfo().getBasesaleprice());
                    //商品份数
                    if(promoDetail.getUsablenum().intValue() != 0 && promoDetail.getUsablenum().intValue() < orderDetail.getOvernum().intValue()){
                        orderDetail.setOvernum(promoDetail.getUsablenum());
                    }
                    BigDecimal totalNum = orderDetail.getOvernum().multiply(promoDetail.getUnitnum());

                    int intAuxremainder = totalNum.intValue() % intBoxnum.intValue() ;
                    int intAux = totalNum.intValue() / intBoxnum.intValue() ;

                    detailOrder.setUnitnum(totalNum);
                    detailOrder.setAuxnum(new BigDecimal(intAux));
                    detailOrder.setOvernum(new BigDecimal(intAuxremainder));
                    detailOrder.setTaxprice(promoDetail.getPrice());
                    detailOrder.setBoxprice(intBoxnum.multiply(promoDetail.getPrice()));
                    detailOrder.setTaxamount(promoDetail.getPrice().multiply(totalNum).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));

                    BigDecimal tax=getBaseFilesFinanceMapper().getTaxTypeInfo
                            (orderDetail.getTaxtype()).getRate().divide(new BigDecimal("100"),decimalLen, BigDecimal.ROUND_HALF_UP).add(new BigDecimal("1"));
                    BigDecimal notaxprice = promoDetail.getPrice().divide(tax, decimalLen, BigDecimal.ROUND_HALF_UP) ;
                    detailOrder.setNotaxprice(notaxprice);
                    detailOrder.setNotaxamount(notaxprice.multiply(totalNum).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                    detailOrder.setTax(detailOrder.getTaxamount().subtract(detailOrder.getNotaxamount()));
                    detailOrder.setAuxnumdetail(detailOrder.getAuxnum()+detailOrder.getAuxunitname()+detailOrder.getOvernum()+detailOrder.getUnitname());
                    detailOrder.setIsdiscount("0");
                    detailOrder.setDeliverytype("0");
                    promotionOrderList.add(detailOrder);

                }else if( "2".equals(promoDetail.getGtype()) ){//赠品

                    detailOrder.setOldprice(orderDetail.getGoodsInfo().getBasesaleprice());
                    detailOrder.setLowestsaleprice(orderDetail.getGoodsInfo().getLowestsaleprice());
                    detailOrder.setTaxprice(BigDecimal.ZERO);
                    detailOrder.setBoxprice(BigDecimal.ZERO);
                    detailOrder.setTaxamount(BigDecimal.ZERO);
                    detailOrder.setNotaxprice(BigDecimal.ZERO);
                    detailOrder.setNotaxamount(BigDecimal.ZERO);
                    detailOrder.setTax(BigDecimal.ZERO);
                    //商品份数
                    if(promoDetail.getUsablenum().intValue() != 0 && promoDetail.getUsablenum().intValue() < orderDetail.getOvernum().intValue()){
                        orderDetail.setOvernum(promoDetail.getUsablenum());
                    }
                    BigDecimal totalNum = orderDetail.getOvernum().multiply(promoDetail.getUnitnum());
                    int intBoxnum=info.getBoxnum().intValue();
                    int intAuxremainder = totalNum.intValue() % intBoxnum ;
                    int intAux = totalNum.intValue() / intBoxnum ;

                    detailOrder.setOvernum(new BigDecimal(Integer.toString(intAuxremainder)));
                    detailOrder.setAuxnum(new BigDecimal(Integer.toString(intAux)));
                    detailOrder.setAuxnumdetail(intAux+ info.getAuxunitname() +intAuxremainder+ info.getMainunitName());
                    detailOrder.setUnitnum(totalNum);
                    detailOrder.setDeliverytype("1");

                    promotionOrderList.add(detailOrder);
                }
            }else if( "2".equals(orderDetail.getDeliverytype()) ){//表示捆绑产品组

                //捆绑的份数
                BigDecimal copy = orderDetail.getOvernum();
                if(promoDetail.getUsablenum().intValue() != 0 && promoDetail.getUsablenum().intValue() < copy.intValue()){//捆绑上限
                    copy = promoDetail.getUsablenum();
                }
                BigDecimal aux = promoDetail.getAuxnum();
                BigDecimal auxremainder = promoDetail.getAuxremainder();
                BigDecimal boxnum=orderDetail.getGoodsInfo().getBoxnum();
                BigDecimal totalNum = aux.multiply(boxnum).add(auxremainder);//每份总数量
                totalNum = totalNum.multiply(copy);

                aux = totalNum.divide(boxnum,0,BigDecimal.ROUND_DOWN) ;
                auxremainder = totalNum.subtract(aux.multiply(boxnum)).setScale(0);
                detailOrder.setAuxnum(aux);
                detailOrder.setOvernum(auxremainder);
                detailOrder.setUnitnum(totalNum);
                detailOrder.setAuxnumdetail(aux+promoDetail.getAuxunitname()+auxremainder+promoDetail.getUnitname());

                detailOrder.setOldprice(orderDetail.getGoodsInfo().getBasesaleprice());
                detailOrder.setLowestsaleprice(orderDetail.getGoodsInfo().getLowestsaleprice());
                detailOrder.setTaxprice(promoDetail.getPrice());
                detailOrder.setBoxprice(promoDetail.getPrice().multiply(boxnum));
                detailOrder.setTaxamount(promoDetail.getPrice().multiply(totalNum).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));

                BigDecimal tax=getBaseFilesFinanceMapper().getTaxTypeInfo
                        (orderDetail.getTaxtype()).getRate().divide(new BigDecimal("100"),decimalLen, BigDecimal.ROUND_HALF_UP).add(new BigDecimal("1"));
                BigDecimal notaxprice = promoDetail.getPrice().divide(tax, decimalLen, BigDecimal.ROUND_HALF_UP) ;
                detailOrder.setNotaxprice(notaxprice);

                detailOrder.setNotaxamount(notaxprice.multiply(totalNum).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                detailOrder.setTax(detailOrder.getTaxamount().subtract(detailOrder.getNotaxamount()));

                detailOrder.setDeliverytype("2");
                detailOrder.setIsdiscount("0");

                promotionOrderList.add(detailOrder);
            }
        }

        return promotionOrderList ;
    }

    /**
     * 检测特价
     * @param
     * @param
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2015-12-7
     */
    @Override
    public Map checkOffprice(String goodsId, String customerId, String businessDate, BigDecimal num) throws Exception {
        OrderDetail orderDetail = new OrderDetail();
        OffpriceDetail offpriceDetail = null;
        Map result =new HashMap();
        Map priceMap = new HashMap();
        priceMap.put("today", businessDate);
        priceMap.put("goodsid", goodsId);
        priceMap.put("customerid", customerId);
        priceMap.put("num", num);
        boolean hasOffprice = false;
        // 取特价信息
        Customer customer = getCustomerByID(customerId);
        // 1单一客户2促销分类3分类客户4价格套5销售区域7信用等级6总店客户
        priceMap.put("type", "1"); // TODO 1:单一客户
        priceMap.put("customerid", customerId);
        if (customer != null) {
            // 促销分类
            String csort = customer.getPromotionsort();
            if (StringUtils.isNotEmpty(csort)) {
                priceMap.put("promotionsort", csort);
            }
            // 取客户的默认分类
            String customerSort = customer.getCustomersort();
            if (StringUtils.isNotEmpty(customerSort)) {
                priceMap.put("customersort", customerSort);
            }
            // 取客户价格套
            String priceSort = customer.getPricesort();
            if (StringUtils.isNotEmpty(priceSort)) {
                priceMap.put("pricesort", priceSort);
            }
            // 取客户所属销售区域
            String saleArea = customer.getSalesarea();
            if (StringUtils.isNotEmpty(saleArea)) {
                priceMap.put("salesarea", saleArea);
            }
            // 取客户 总店
            String pcustomerid = customer.getPid();
            if (StringUtils.isNotEmpty(pcustomerid)) {
                priceMap.put("pcustomerid", pcustomerid);
            }
            // 取信用等级
            String credit = customer.getCreditrating();
            if (StringUtils.isNotEmpty(credit)) {
                priceMap.put("credit", credit);
            }
            // 取核销方式
            String canceltype = customer.getCanceltype();
            if (StringUtils.isNotEmpty(canceltype)) {
                priceMap.put("canceltype", canceltype);
            }
            // 获取特价信息
            offpriceDetail = getSalesOffpriceMapper().getOffpriceByCustomerGoodsNum(priceMap);
            if (null != offpriceDetail) {
                hasOffprice = true;
            }
        }
        //获取客户商品的合同价或者价格套数据
        OrderDetail fixOrderDetail = getFixGoodsDetail(goodsId, customerId);
        //判断是否有特价
        if (!hasOffprice) {
            //获取客户商品的合同价或者价格套数据
            result.put("flag", false);
        } else {
            String begindate = CommonUtils.dateStringChange(offpriceDetail.getField01(), "MM-dd");
            String enddate = CommonUtils.dateStringChange(offpriceDetail.getField02(), "MM-dd");
            String remark="特价：" + begindate + "至" + enddate;
            result.put("flag", true);
            result.put("price", offpriceDetail.getOffprice());
            result.put("remark", remark);
        }
        return result;
    }
    @Override
    public Map showCustomerReceivableInfoData(String customerid) throws Exception {
        return super.showCustomerReceivableInfoData(customerid);
    }
    @Override
    public String getCustomerBillId(String id) throws Exception{
        return getSalesOrderMapper().getCustomerBillId(id);
    }

    @Override
    public boolean updateOrderDate(Order order) throws Exception {
        return getSalesOrderMapper().updateOrderStatus(order) > 0;
    }
    /**
     * 装到订单明细
     * @param list
     * @param queryMap
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年4月11日
     */
    private void fillInOrderDetailInfo(List<OrderDetail> list,Map queryMap) throws Exception{
        if(null==list || list.size()==0){
            return;
        }
        String customerid="";
        String showShopid="";
        if(null!=queryMap){
            showShopid=(String)queryMap.get("showShopid");
            customerid=(String)queryMap.get("customerid");
        }

        for(OrderDetail orderDetail : list){
            if("true".equals(showShopid) && null!=customerid && !"".equals(customerid.trim())){
                Customer customer = getCustomerByID(customerid.trim());
                if(null!=customer){
                    //获取客户店内码
                    CustomerPrice customerPrice=getCustomerPriceByCustomerAndGoodsid(customerid, orderDetail.getGoodsid());
                    if(null!=customerPrice && StringUtils.isNotEmpty(customerPrice.getShopid())){
                        orderDetail.setShopid(customerPrice.getShopid());
                    }else if(StringUtils.isNotEmpty(customer.getPid())){
                        customerPrice=getCustomerPriceByCustomerAndGoodsid(customer.getPid(), orderDetail.getGoodsid());
                        if(null!=customerPrice){
                            orderDetail.setShopid(customerPrice.getShopid());
                        }
                    }
                }
            }

            StorageInfo storageInfo = getStorageInfoByID(orderDetail.getStorageid());
            if(null!=storageInfo){
                orderDetail.setStoragename(storageInfo.getName());
            }
            StorageLocation storageLocation = getStorageLocation(orderDetail.getStoragelocationid());
            if(null!=storageLocation){
                orderDetail.setStoragelocationname(storageLocation.getName());
            }
            TaxType taxType = getTaxType(orderDetail.getTaxtype());
            if(null!=taxType){
                orderDetail.setTaxtypename(taxType.getName());
            }
            GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(getGoodsInfoByID(orderDetail.getGoodsid()));
            //折扣显示处理
            if("1".equals(orderDetail.getIsdiscount())){
                goodsInfo.setName("(折扣)"+goodsInfo.getName());
                goodsInfo.setBarcode("");
                goodsInfo.setBoxnum(null);
                orderDetail.setUnitnum(null);
                orderDetail.setAuxnumdetail("");
                orderDetail.setTaxprice(null);
                orderDetail.setUnitname("");
                if("1".equals(orderDetail.getIsbranddiscount())){
                    orderDetail.setGoodsid("");
                    goodsInfo.setName("(折扣)"+goodsInfo.getBrandName());
                    orderDetail.setIsdiscount("2");
                }
            }
            orderDetail.setGoodsInfo(goodsInfo);
        }
    }

    public List<OrderDetail> getOrderDetailPrintList(Map queryMap) throws Exception{
        String showShopid=(String)queryMap.get("showShopid");
        String customerid=(String)queryMap.get("customerid");
        List<OrderDetail> list=getSalesOrderDetailMapper().getOrderDetailPrintBy(queryMap);

        Map paramMap=new HashMap();
        paramMap.put("showShopid", showShopid);
        paramMap.put("customerid", customerid);
        fillInOrderDetailInfo(list, paramMap);
        return list;
    }
    @Override
    public Order getPureOrder(String id) throws Exception {
        Order order = getSalesOrderMapper().getOrderById(id);
        return order;
    }

    /**
     * 更新打印次数
     * @param order
     * @author zhanghonghui
     * @date 2013-9-10
     */
    public boolean updateOrderPrinttimes(Order order) throws Exception{
        return getSalesOrderMapper().updateOrderPrinttimes(order)>0;
    }

    @Override
    public Map modifyGoodsContractPrice(String id, String goodsid ,String price) throws Exception {

        BigDecimal taxprice = new BigDecimal(price);
        Order order = getOrder(id);
        String customerid = order.getCustomerid();
        Map map = new HashMap();
        String msg = "";
        boolean flag = false;

        List<OrderDetail> detailList = getSalesOrderDetailMapper().getOrderDetailByOrder(id, null);
        OrderDetail orderDetail = null ;
        for(OrderDetail detail : detailList){
            if(StringUtils.isNotEmpty(detail.getGoodsid()) && goodsid.equals(detail.getGoodsid())){
                orderDetail = detail;
                break;
            }
        }
        GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid); //商品信息
        if (null != goodsInfo && null != orderDetail) {
            BigDecimal rate = BigDecimal.ZERO;
            TaxType taxType = getTaxType(goodsInfo.getDefaulttaxtype()); //获取默认税种
            if (taxType != null) {
                orderDetail.setTaxtype(taxType.getId()); //税种档案中的编码
                orderDetail.setTaxtypename(taxType.getName()); //税种名称
                rate = taxType.getRate().divide(new BigDecimal(100));
            }
            BigDecimal notaxprice = taxprice.divide(rate.add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP) ;
            //判断该客户是否存在对应的合同商品
            CustomerPrice customerPrice = getCustomerMapper().getCustomerPriceByCustomerAndGoods(customerid, goodsid);
            if(null == customerPrice){
                //合同商品不存在则添加合同商品
                CustomerPrice customerPrice2 = new CustomerPrice();
                customerPrice2.setCustomerid(customerid);
                customerPrice2.setGoodsid(goodsid);
                customerPrice2.setBarcode(goodsInfo.getBarcode());
                customerPrice2.setTaxrate(rate);
                customerPrice2.setPrice(taxprice);
                customerPrice2.setCtcboxprice(taxprice.multiply(goodsInfo.getBoxnum()));
                customerPrice2.setNoprice(notaxprice);
                GoodsInfo_PriceInfo priceInfo = getPriceInfo(goodsid, customerid); //客户的价格套信息
                if(null == priceInfo){
                    BigDecimal baseprice = goodsInfo.getBasesaleprice();
                    customerPrice2.setTaxprice(baseprice);
                }else {
                    customerPrice2.setTaxprice(priceInfo.getTaxprice());
                }
                int a = customerMapper.addCustomerPrice(customerPrice2);
                if(a > 0 ){
                    msg = "客户对应合同价商品添加成功";
                }
            }else{
                customerPrice.setPrice(taxprice);
                customerPrice.setCtcboxprice(taxprice.multiply(goodsInfo.getBoxnum()));
                customerPrice.setNoprice(notaxprice);
                int a = customerMapper.updateCustomerPrice(customerPrice);
                if(a > 0 ){
                    msg = "客户对应合同价商品修改成功";
                }
            }
            orderDetail.setFixprice(taxprice);
            orderDetail.setTaxprice(taxprice);
            orderDetail.setNotaxprice(notaxprice);
            flag  = getSalesOrderDetailMapper().updateOrderDetail(orderDetail) > 0 ;
        }
        map.put("flag",flag);
        if(StringUtils.isNotEmpty(msg)){
            map.put("msg",msg);
        }

        return map;
    }

    @Override
    public Order getOrderBySourceid(String sourceid, String sourcetype) throws Exception {

        return getSalesOrderMapper().getOrderBySourceid(sourceid, sourcetype);
    }

    @Override
    public List getCustomerGoodsLastThreePrice(String customerid, String goodsid) throws Exception {
        SysParam sysParam = getBaseSysParamMapper().getSysParam("orderPageShowHistrooyPriceFloat");
        List<Map> list = getSalesOrderMapper().getOrderAuditGoodsPrice(customerid,goodsid,sysParam.getPvalue());
        for(Map map : list){
            String remark = (String) map.get("remark");
            if(StringUtils.isNotEmpty(remark) && remark.length() > 7){
                map.put("remark",remark.substring(0,7));
            }
        }
        return list;
    }


    @Override
    public synchronized void doUpdateOrderStatus() throws Exception {
        List<Map> saleOrderList = getSalesOrderMapper().getNullOrderList();
        for(Map map : saleOrderList){
            String orderid = (String)map.get("saleorderid");
            getSalesOrderMapper().doUpdateOrderStatus(orderid);
        }
    }

    /**
     * 回写销售订单来源要货单关联的订货单
     * @param demandid
     * @return void
     * @throws
     * @author luoqiang
     * @date Nov 22, 2017
     */
    private void updateDemandGoodsDetailNum(String demandid) throws Exception{
        //回写要货单关联的订货单

        Demand demand=getSalesDemandMapper().getDemand(demandid);

        //回写要货单关联的订货单
        int t=getOrderGoodsMapper().updateOrderGoodsDetailNum(demandid,"2",demand.getOrdergoodsid());
        if(t>0){
            //删除订货单和销售订单的关联关系
            getOrderGoodsMapper().deleteRelationOrderGoods(demandid);
            //回写要货单里的订货单编号字段为空
            getSalesDemandMapper().updateDemandOrderGoodsid(demandid,"");
        }
    }

    /**
     * 获取客户最近一次商品销售价
     *
     * @param customerid
     * @param goodsid
     * @param type 2：最近订单价格；3：最近出库单价格；4：最近回单价格
     * @return
     * @author limin
     * @date Jan 4, 2018
     */
    private BigDecimal getLastestPriceByCustomerGoods(String customerid, String goodsid, String type) {

        return getSalesOrderMapper().getLastestPriceByCustomerGoods(customerid, goodsid, type);
    }

    /**
     * 保存销售订单时，回写订货单数据
     * @param orderid
     * @return void
     * @throws
     * @author luoqiang
     * @date Mar 15, 2018
     */
    private void updateOrderGoodsDetail(String orderid,String ordergoodsid) {
        //先根据订单编号把订货单关系表的相关数据回写一遍，订货单明细 已生成数量=已生成数量-关联数量，未生成数量=未生成数量+关联数量
        getOrderGoodsMapper().updateOrderGoodsByOrder(orderid,"1",ordergoodsid);
        //再删除订单里面本次保存删除掉的商品
        getOrderGoodsMapper().deleteRalationNotInOrder(orderid);
        //回写关系表数量，把关系表数量改为当前明细的商品数量
        getOrderGoodsMapper().updateRelationGoodsNum(orderid);
        //再回写订货单数据 根据订单编号把订货单关系表的相关数据回写一遍
        //订货单明细 已生成数量=已生成数量+当前订单明细商品数量,
        //未生成数量=未生成数量-当前订单明细商品数量
        getOrderGoodsMapper().updateOrderGoodsByOrder(orderid,"2",ordergoodsid);
    }

    /**
     * 保存销售订单时，回写订货单数据
     * @param orderid
     * @return void
     * @throws
     * @author luoqiang
     * @date Mar 15, 2018
     */
    private void updateDemandOrderGoodsDetail(String orderid,String ordergoodsid,String demandid) {
        //先根据订单编号把订货单关系表的相关数据回写一遍，订货单明细 已生成数量=已生成数量-关联数量，未生成数量=未生成数量+关联数量
        getOrderGoodsMapper().updateOrderGoodsByOrder(demandid,"1",ordergoodsid);
        //再删除订单里面本次保存删除掉的商品
        getOrderGoodsMapper().deleteRalationNotInDemandOrder(orderid,demandid);

        //回写关系表数量，把关系表数量改为当前明细的商品数量
        getOrderGoodsMapper().updateDemadRelationGoodsNum(orderid,demandid);

        //再回写订货单数据 根据订单编号把订货单关系表的相关数据回写一遍
        //订货单明细 已生成数量=已生成数量+当前订单明细商品数量,
        //未生成数量=未生成数量-当前订单明细商品数量
        getOrderGoodsMapper().updateOrderGoodsByOrder(demandid,"2",ordergoodsid);
    }

}

