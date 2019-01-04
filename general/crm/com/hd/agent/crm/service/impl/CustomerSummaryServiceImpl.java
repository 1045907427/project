package com.hd.agent.crm.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.crm.dao.CrmSalesOrderMapper;
import com.hd.agent.crm.dao.CustomerStorageOrderMapper;
import com.hd.agent.crm.dao.CustomerSummaryMapper;
import com.hd.agent.crm.model.CrmSalesOrder;
import com.hd.agent.crm.model.CrmSalesOrderDetail;
import com.hd.agent.crm.model.CustomerStorageOrder;
import com.hd.agent.crm.model.CustomerSummary;
import com.hd.agent.crm.service.ICustomerSummaryService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by lin_xx on 2016/10/25.
 */
public class CustomerSummaryServiceImpl extends BaseFilesServiceImpl implements ICustomerSummaryService {

    private CustomerSummaryMapper customerSummaryMapper;

    private CrmSalesOrderMapper crmSalesOrderMapper;

    public CustomerStorageOrderMapper customerStorageOrderMapper;

    public CustomerSummaryMapper getCustomerSummaryMapper() {
        return customerSummaryMapper;
    }

    public void setCustomerSummaryMapper(CustomerSummaryMapper customerSummaryMapper) {
        this.customerSummaryMapper = customerSummaryMapper;
    }

    public CrmSalesOrderMapper getCrmSalesOrderMapper() {
        return crmSalesOrderMapper;
    }

    public void setCrmSalesOrderMapper(CrmSalesOrderMapper crmSalesOrderMapper) {
        this.crmSalesOrderMapper = crmSalesOrderMapper;
    }

    public CustomerStorageOrderMapper getCustomerStorageOrderMapper() {
        return customerStorageOrderMapper;
    }

    public void setCustomerStorageOrderMapper(CustomerStorageOrderMapper customerStorageOrderMapper) {
        this.customerStorageOrderMapper = customerStorageOrderMapper;
    }

    @Override
    public PageData getCustomerSummaryReportData(PageMap pageMap) throws Exception {
        List<Map> list = customerSummaryMapper.getCustomerSummaryReportData(pageMap);
        for(Map map : list){
            String param = (String) map.get("customerid");
            Customer customer = getCustomerByID(param);
            if(null != customer){
                map.put("customername",customer.getName());
            }
            param = (String) map.get("supplierid");
            BuySupplier supplier = getSupplierInfoById(param);
            if (supplier != null) {
                map.put("suppliername", supplier.getName());
            }
            param = (String) map.get("goodsid");
            GoodsInfo goodsInfo = getAllGoodsInfoByID(param);
            BigDecimal unitnum = (BigDecimal) map.get("unitnum");
            if(null != goodsInfo){
                map.put("brandname",goodsInfo.getBrandName());
                map.put("goodsname",goodsInfo.getName());
                map.put("boxnum",goodsInfo.getBoxnum());
                map.put("spell",goodsInfo.getSpell());
                map.put("barcode",goodsInfo.getBarcode());
                map.put("taxprice",goodsInfo.getBasesaleprice());
                map.put("taxamount",goodsInfo.getBasesaleprice().multiply(unitnum));
                if(null != goodsInfo.getGrossweight()){
                    BigDecimal boxweight = unitnum.multiply(goodsInfo.getGrossweight()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    map.put("boxweight",boxweight);
                }
                if(null != goodsInfo.getSinglevolume()){
                    BigDecimal boxvolume = unitnum.multiply(goodsInfo.getSinglevolume()).setScale(3, BigDecimal.ROUND_HALF_UP);
                    map.put("boxvolume",boxvolume);
                }
            }
        }
        int count = customerSummaryMapper.getCustomerSummaryReportDataCount(pageMap);
        PageData pageData = new PageData(count,list,pageMap);
        if(list.size() > 0){
            Map map = customerSummaryMapper.getCustomerSummaryReportDataSum(pageMap);
            if(null != map){
                map.put("customername","合  计");
            }
            List footer = new ArrayList();
            footer.add(map);
            pageData.setFooter(footer);
        }
        return pageData;
    }

    @Override
    public String getLastSummaryDay(String customerid) throws Exception {
        String date = customerSummaryMapper.getLastSummaryDayByCustomerid(customerid);
        if(StringUtils.isEmpty(date)){
            date = customerSummaryMapper.getLastSummaryDayFromHistory(customerid);
        }
        return date;
    }

    @Override
    public Map updateSummaryByCustomer(PageMap pageMap) throws Exception {
        Map m = new HashMap();
        int count = 0 ;
        List successList = new ArrayList();
        List failureList = new ArrayList();
        Map condition = pageMap.getCondition();
        condition.put("customer","1");
        pageMap.setCondition(condition);
        //客户库存 = 时间段内发货单数量 - 时间段内退货入库单数量 - 时间段内客户销量 + 开始日期的客户库存数量
        List<Map> list = customerSummaryMapper.getCustomerSummaryFromSalesReport(pageMap);
        for(Map map : list){
            if(null != map){
                String goodsid = (String) map.get("goodsid");
                //系统销售数量
                BigDecimal newStorage = (BigDecimal) map.get("unitnum");
                String customerid = (String) condition.get("customerid");
                //验证客户商品在库存中是否存在
                CustomerSummary summary = customerSummaryMapper.validateGoodsForCustomer(goodsid,customerid);
                GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
                boolean flag = false ;
                if(null == summary){//新增客户库存商品
                    CustomerSummary customerSummary = new CustomerSummary();
                    customerSummary.setBusinessdate((String) condition.get("enddate"));
                    customerSummary.setCustomerid(customerid);
                    customerSummary.setGoodsid(goodsid);
                    customerSummary.setGoodssort(goodsInfo.getDefaultsort());
                    customerSummary.setBrandid(goodsInfo.getBrand());
                    customerSummary.setSupplierid(goodsInfo.getDefaultsupplier());
                    customerSummary.setUnitid(goodsInfo.getMainunit());
                    customerSummary.setUnitname(goodsInfo.getMainunitName());
                    customerSummary.setAuxunitid(goodsInfo.getAuxunitid());
                    customerSummary.setAuxunitname(goodsInfo.getAuxunitname());
                    //数量组装
                    BigDecimal boxnum = goodsInfo.getBoxnum();
                    if(boxnum.compareTo(BigDecimal.ZERO) > 0){
                        BigDecimal totalbox = newStorage.divide(boxnum,6,BigDecimal.ROUND_HALF_UP);
                        BigDecimal mainnum = newStorage.divide(boxnum,0,BigDecimal.ROUND_DOWN);
                        int remaindernum = newStorage.remainder(boxnum).intValue();
                        customerSummary.setTotalbox(totalbox);
                        customerSummary.setAuxnumdetail(mainnum+goodsInfo.getAuxunitname()+remaindernum+goodsInfo.getMainunitName());
                    }
                    customerSummary.setUnitnum(newStorage);
                    customerSummary.setAddtime(new Date());
                    flag = customerSummaryMapper.addCustomerSummary(customerSummary) > 0 ;
                }else if(condition.get("enddate").equals(summary.getBusinessdate())){
                    break;
                }else{
                    CustomerSummary summaryHistory = (CustomerSummary) CommonUtils.deepCopy(summary);
                    //验证在客户历史库存中是否存在同个日期对应客户的对应商品
                    int i = customerSummaryMapper.validateGoodsForCustomer_h(summaryHistory.getGoodsid(),summaryHistory.getCustomerid(),summaryHistory.getBusinessdate());
                    if(i>0){
                       continue;
                    }
                    summaryHistory.setId(null);
                    //将原来的客户库存变更为客户历史库存
                    customerSummaryMapper.addCustomerSummary_h(summaryHistory);

                    //更新当前日期的客户库存
                    summary.setUnitnum(newStorage);
                    //数量组装
                    BigDecimal boxnum = goodsInfo.getBoxnum();
                    if(boxnum.compareTo(BigDecimal.ZERO) > 0){
                        BigDecimal totalbox = newStorage.divide(boxnum,6,BigDecimal.ROUND_HALF_UP);
                        BigDecimal mainnum = newStorage.divide(boxnum,0,BigDecimal.ROUND_DOWN);
                        int remaindernum = newStorage.remainder(boxnum).intValue();
                        summary.setTotalbox(totalbox);
                        summary.setAuxnumdetail(mainnum+goodsInfo.getAuxunitname()+remaindernum+goodsInfo.getMainunitName());
                    }
                    summary.setUnitnum(newStorage);
                    summary.setAddtime(new Date());
                    summary.setBusinessdate((String) condition.get("enddate"));
                    flag = customerSummaryMapper.updateCustomerSummary(summary) > 0;
                }
                if(flag){
                    successList.add(goodsid);
                    ++ count ;
                }else{
                    failureList.add(goodsid);
                }
            }
        }
        m.put("successid",successList);
        if(failureList.size() > 0){
            m.put("failureid",failureList);
        }
        m.put("count",count);
        if(count > 0){
            m.put("flag",true);
        }
        return m;
    }

    @Override
    public Map crmSalesSync(PageMap pageMap) throws Exception {

        Map m = new HashMap();
        int count = 0 ;
        Map condition = pageMap.getCondition();
        String customerid = (String) pageMap.getCondition().get("customerid");
        //获取最近的库存日期
        condition.put("crmSalesSync","1");
        pageMap.setCondition(condition);
        //客户销量 = 时间段内发货单数量-时间段内退货入库单数量 - 时间段内客户销量 + 开始日期之前最近的客户库存历史数量 - 结束日期客户库存数量
        List<Map> list = customerSummaryMapper.getCustomerSummaryFromSalesReport(pageMap);
        //客户销量单据
        CrmSalesOrder crmSalesOrder = new CrmSalesOrder();
        crmSalesOrder.setCustomerid(customerid);
        String date = (String) pageMap.getCondition().get("enddate");
        if(StringUtils.isEmpty(date)){
            date = (String) pageMap.getCondition().get("enddate1");
        }
        if (isAutoCreate("t_crm_sales_order")) {
            // 获取自动编号
            String id = getAutoCreateSysNumbderForeign(crmSalesOrder, "t_crm_sales_order");
            crmSalesOrder.setId(id);
        }else{
            String id = "CTSD-"+ CommonUtils.getDataNumberSendsWithRand();
            crmSalesOrder.setId(id);
        }
        crmSalesOrder.setKeyid(crmSalesOrder.getId());
        int index = 0 ;
        for (Map map : list) {
            if(null != map){
                String goodsid = (String) map.get("goodsid");
                BigDecimal unitnum = (BigDecimal) map.get("unitnum");
                if(unitnum.compareTo(BigDecimal.ZERO) == 0){
                    ++ index ;
                    continue;
                }
                //商品更新信息
                CrmSalesOrderDetail crmSalesOrderDetail = new CrmSalesOrderDetail();
                crmSalesOrderDetail.setOrderid(crmSalesOrder.getId());
                crmSalesOrderDetail.setGoodsid(goodsid);
                crmSalesOrderDetail.setUnitnum(unitnum);
                crmSalesOrderDetail.setFixnum(unitnum);

                GoodsInfo goodsInfo = getAllGoodsInfoByID(crmSalesOrderDetail.getGoodsid());
                if(null != goodsInfo){
                    crmSalesOrderDetail.setGoodssort(goodsInfo.getDefaultsort());
                    crmSalesOrderDetail.setBrandid(goodsInfo.getBrand());
                    crmSalesOrderDetail.setCostprice(goodsInfo.getCostaccountprice());
                    crmSalesOrderDetail.setOldprice(goodsInfo.getBasesaleprice());
                    crmSalesOrderDetail.setGoodssort(goodsInfo.getDefaultsort());
                    crmSalesOrderDetail.setUnitid(goodsInfo.getMainunit());
                    crmSalesOrderDetail.setUnitname(goodsInfo.getMainunitName());
                    crmSalesOrderDetail.setAuxunitid(goodsInfo.getAuxunitid());
                    crmSalesOrderDetail.setAuxunitname(goodsInfo.getAuxunitname());
                    //0取基准销售价,1取合同价
                    String priceType = getSysParamValue("DELIVERYORDERPRICE");
                    BigDecimal price = new BigDecimal(0);
                    if("1".equals(priceType) &&!"".equals(customerid)){
                        //系统参数取价
                        price = getGoodsPriceByCustomer(goodsInfo.getId(),customerid);
                    }else {
                        price = goodsInfo.getBasesaleprice();
                    }
                    //获取税种，税额，未税金额，未税单价
                    crmSalesOrderDetail.setTaxprice(price);
                    crmSalesOrderDetail.setCostprice(goodsInfo.getCostaccountprice());
                    crmSalesOrderDetail.setTaxamount(price.multiply(unitnum).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
                    crmSalesOrderDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
                    BigDecimal notaxanount = getNotaxAmountByTaxAmount(price.multiply(unitnum), goodsInfo.getDefaulttaxtype());
                    crmSalesOrderDetail.setNotaxamount(notaxanount);
                    crmSalesOrderDetail.setTax(crmSalesOrderDetail.getTaxamount().subtract(crmSalesOrderDetail.getNotaxamount()));
                    if(null!=notaxanount && null!=crmSalesOrderDetail.getUnitnum() && crmSalesOrderDetail.getUnitnum().compareTo(BigDecimal.ZERO)==1){
                        crmSalesOrderDetail.setNotaxprice(notaxanount.divide(crmSalesOrderDetail.getUnitnum(), 6, BigDecimal.ROUND_HALF_UP));
                    }
                    //计算辅单位数量
                    Map map1 = countGoodsInfoNumber(crmSalesOrderDetail.getGoodsid(), crmSalesOrderDetail.getAuxunitid(), crmSalesOrderDetail.getUnitnum());
                    if (map1.containsKey("auxnumdetail")) {
                        crmSalesOrderDetail.setAuxnum(new BigDecimal((String) map1.get("auxInteger")));
                        crmSalesOrderDetail.setOvernum(new BigDecimal((String) map1.get("auxremainder")));
                        crmSalesOrderDetail.setAuxnumdetail((String) map1.get("auxnumdetail"));
                    }
                    if(null != goodsInfo.getBoxnum()){
                        BigDecimal totalbox = unitnum.divide(goodsInfo.getBoxnum(),3,BigDecimal.ROUND_HALF_UP);
                        crmSalesOrderDetail.setTotalbox(totalbox);
                    }
                    //获取品牌部门
                    Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                    if (null != brand){
                        crmSalesOrderDetail.setBranddept(brand.getDeptid());
                    }
                    //根据客户编号和品牌编号 获取品牌业务员
                    crmSalesOrderDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), customerid));
                    //厂家业务员
                    crmSalesOrderDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), customerid));
                    crmSalesOrderDetail.setSupplierid(goodsInfo.getDefaultsupplier());

                    //获取系统设置的原价，不包括特价
                    crmSalesOrderDetail.setFixprice(getGoodsPriceByCustomer(crmSalesOrderDetail.getGoodsid(), customerid));
                }
                crmSalesOrderDetail.setSeq(0);
                boolean flag = crmSalesOrderMapper.addCrmSalesOrderDetail(crmSalesOrderDetail) > 0;
                if(flag){
                    ++ count ;
                }
            }
        }
        boolean flag = false;
        if(count > 0) {
            crmSalesOrder.setBusinessdate(date);
            crmSalesOrder.setStatus("2");
            crmSalesOrder.setRemark("销量更新："+date);
            crmSalesOrder.setSourcetype("3");
            SysUser sysUser = getSysUser();
            if (sysUser != null && null == crmSalesOrder.getAdduserid()) {
                crmSalesOrder.setAdddeptid(sysUser.getDepartmentid());
                crmSalesOrder.setAdddeptname(sysUser.getDepartmentname());
                crmSalesOrder.setAdduserid(sysUser.getUserid());
                crmSalesOrder.setAddusername(sysUser.getName());
                crmSalesOrder.setAddtime(new Date());
            }
            //销售内勤
            SysUser indoorsysUser = getSalesIndoorSysUserByCustomerid(crmSalesOrder.getCustomerid());
            if(null != indoorsysUser){
                crmSalesOrder.setIndooruserid(indoorsysUser.getPersonnelid());
            }
            //获取销售区域上级客户
            Customer customer = getCustomerByID(crmSalesOrder.getCustomerid());
            if (null != customer) {
                crmSalesOrder.setSalesarea(customer.getSalesarea());
                crmSalesOrder.setPcustomerid(customer.getPid());
                crmSalesOrder.setCustomersort(customer.getCustomersort());
            }
            flag = crmSalesOrderMapper.addCrmSalesOrder(crmSalesOrder) > 0;
            m.put("orderids",crmSalesOrder.getId());
            m.put("count",count);

        }else if(list.size() == 0 || index == list.size()){
            flag = true;
        }
        m.put("count",count);
        m.put("flag",flag);
        return m;
    }

    @Override
    public Map clearCustomerSummary(String customerid) throws Exception {
        Map map = new HashMap();
        //删除客户库存
        int count = customerSummaryMapper.deleteSummaryListByCustomer(customerid);
        //删除客户历史库存
        int historyCount = customerSummaryMapper.deleteSummaryHistoryListByCustomer(customerid);
        count += historyCount ;
        String auditOrderid = "";
        if(count > 0){
            //反审该客户审核通过的客户库存单据
            List<CustomerStorageOrder> orderList = customerStorageOrderMapper.getStorageOrderListByCustomerid(customerid);
            for(CustomerStorageOrder order : orderList){
                order.setStatus("2");
                customerStorageOrderMapper.updateCustomerStorageOrder(order);
                auditOrderid += order.getId()+",";
            }
            map.put("flag",true);
        }else{
            map.put("flag",false);
        }
        if(auditOrderid.endsWith(",")){
            auditOrderid = auditOrderid.substring(0,auditOrderid.length()-1);
        }
        map.put("count",count);
        map.put("auditOrderid",auditOrderid);
        return map;
    }

    @Override
    public PageData getCustomerSummaryRevolutionReportData(PageMap pageMap) throws Exception {
        //日期天数计算
        String begindate = (String) pageMap.getCondition().get("businessdate");
        String enddate = (String) pageMap.getCondition().get("businessdate1");
        int mondays  =CommonUtils.daysBetween(begindate,enddate) + 1;
        Map condition = pageMap.getCondition();
        condition.put("mondays",mondays);
        List<Map> list = customerSummaryMapper.getCustomerSummaryReportData(pageMap);
        int count = customerSummaryMapper.getCustomerSummaryReportDataCount(pageMap);
        for(Map map : list){
            String customerid = (String) map.get("customerid");
            Customer customer = getCustomerByID(customerid);
            if(null != customer){
                map.put("customername",customer.getName());
            }
            String supplierid = (String) map.get("supplierid");
            BuySupplier supplier = getSupplierInfoById(supplierid);
            if (supplier != null) {
                map.put("suppliername", supplier.getName());
            }
            String brandid = (String) map.get("brandid");
            Brand brand = getGoodsBrandByID(brandid);
            if (brand != null ){
                map.put("branddeptname",brand.getDeptName());
            }
            String goodsid = (String) map.get("goodsid");
            GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
            BigDecimal summarynum = (BigDecimal) map.get("unitnum");
            if(null != goodsInfo){
                map.put("brandname",goodsInfo.getBrandName());
                map.put("goodsname",goodsInfo.getName());
                map.put("taxamount", goodsInfo.getBasesaleprice().multiply(summarynum));
            }
            condition.put("customerid",customerid);
            condition.put("goodsid",goodsid);
            pageMap.setCondition(condition);
            Map map1 = crmSalesOrderMapper.getCrmSalesGoodsNumByDate(pageMap);
            if(null != map1){
                BigDecimal salesnum = (BigDecimal) map1.get("salesnum");
                map.putAll(map1);
                //周转天数 = 库存数量*日期天数/销售数量
                BigDecimal days = summarynum.multiply(new BigDecimal(mondays)).divide(salesnum,2,BigDecimal.ROUND_HALF_UP);
                map.put("days",days.toString());

                BigDecimal days1 = new BigDecimal(0);
                if(condition.containsKey("days1")){
                    days1 = new BigDecimal((String) condition.get("days1"));
                }
                BigDecimal days2 = new BigDecimal(0);
                if(condition.containsKey("days2")){
                    days2 = new BigDecimal((String) condition.get("days2"));
                }
                //查询中的周转天数1 < 周转天数 < 查询中的周转天数2 畅销
                if(days1.compareTo(BigDecimal.ZERO)>0){
                    if(days.compareTo(days1) < 0){
                        map.put("salestype","畅销");
                    }else if(days2.compareTo(BigDecimal.ZERO)>0){
                        if(days.compareTo(days2) <= 0){
                            map.put("salestype","畅销");
                        }else{
                            map.put("salestype","滞销");
                        }
                    }else{
                        map.put("salestype","");
                    }
                }else if(days2.compareTo(BigDecimal.ZERO)>0){
                    if(days.compareTo(days2) <= 0){
                        map.put("salestype","畅销");
                    }else{
                        map.put("salestype","滞销");
                    }
                }
            }else{
                map.put("days", 0);
                if(mondays > 15){
                    map.put("salestype","滞销");
                }
            }
        }
        PageData pageData = new PageData(count,list,pageMap);
        if(list.size() > 0){
            Map map = customerSummaryMapper.getCustomerSummaryReportDataSum(pageMap);
            if(null != map){
                map.put("customername","合  计");
            }
            List footer = new ArrayList();
            footer.add(map);
            pageData.setFooter(footer);
        }
        return pageData;
    }

    @Override
    public List<Map> getCustomerSummaryForGoodsHistoryList(Map map) throws Exception {
        return customerSummaryMapper.getCustomerSummaryForGoodsHistoryList(map);
    }

    @Override
    public List<Map> getAllSummaryCustomer() throws Exception {
        return customerSummaryMapper.getCustomerFromStorage();
    }

    @Override
    public PageData getCustomerSummaryReportGroupData(PageMap pageMap) throws Exception {
        List<Map> list = customerSummaryMapper.getCustomerSummaryReportGroupData(pageMap);
        for(Map map : list){
            String param = (String) map.get("customerid");
            Customer customer = getCustomerByID(param);
            if(null != customer){
                map.put("customername",customer.getName());
            }
            param = (String) map.get("supplierid");
            BuySupplier supplier = getSupplierInfoById(param);
            if (supplier != null) {
                map.put("suppliername", supplier.getName());
            }
            param = (String) map.get("goodsid");
            GoodsInfo goodsInfo = getAllGoodsInfoByID(param);
            //BigDecimal unitnum = (BigDecimal) map.get("unitnum");
            if(null != goodsInfo){
                BigDecimal stocknum =  new BigDecimal((Double) map.get("stocknum"));
                map.put("brandname",goodsInfo.getBrandName());
                map.put("goodsname",goodsInfo.getName());
                map.put("boxnum",goodsInfo.getBoxnum());
                map.put("spell",goodsInfo.getSpell());

                BigDecimal costprice = goodsInfo.getBasesaleprice();
                BigDecimal costtaxamount = costprice.multiply(stocknum);
                map.put("costtaxamount",costtaxamount);
                BigDecimal taxamount = goodsInfo.getBasesaleprice().multiply(stocknum);
                map.put("taxamount",goodsInfo.getBasesaleprice().multiply(stocknum));
                TaxType taxType = getTaxType(goodsInfo.getDefaulttaxtype());
                if(null != taxType){
                    BigDecimal rate = taxType.getRate().divide(new BigDecimal(100));
                    BigDecimal costnotaxamount = costtaxamount.divide(BigDecimal.ONE.add(rate),6,BigDecimal.ROUND_HALF_UP);
                    map.put("costnotaxamount",costnotaxamount);
                    BigDecimal notaxamount = taxamount.divide(BigDecimal.ONE.add(rate),6,BigDecimal.ROUND_HALF_UP);
                    map.put("notaxamount",notaxamount);
                }

//                if(null != goodsInfo.getGrossweight()){
//                    BigDecimal boxweight = unitnum.multiply(goodsInfo.getGrossweight()).setScale(2, BigDecimal.ROUND_HALF_UP);
//                    map.put("boxweight",boxweight);
//                }
//                if(null != goodsInfo.getSinglevolume()){
//                    BigDecimal boxvolume = unitnum.multiply(goodsInfo.getSinglevolume()).setScale(3, BigDecimal.ROUND_HALF_UP);
//                    map.put("boxvolume",boxvolume);
//                }
            }
        }
        int count = customerSummaryMapper.getCustomerSummaryReportGroupCount(pageMap);
        PageData pageData = new PageData(count,list,pageMap);
        if(list.size() > 0){
            Map map = customerSummaryMapper.getCustomerSummaryReportGroupSum(pageMap);
            if(null != map){
                map.put("customername","合  计");
            }
            List footer = new ArrayList();
            footer.add(map);
            pageData.setFooter(footer);
        }
        return pageData;
    }


}
