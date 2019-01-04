package com.hd.agent.crm.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.crm.dao.CustomerStorageOrderMapper;
import com.hd.agent.crm.dao.CustomerSummaryMapper;
import com.hd.agent.crm.model.CustomerStorageOrder;
import com.hd.agent.crm.model.CustomerStorageOrderDetail;
import com.hd.agent.crm.model.CustomerSummary;
import com.hd.agent.crm.service.ICustomerStorageOrderService;
import com.hd.agent.sales.model.ModelOrder;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import net.sf.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.*;

/**
 * Created by lin_xx on 2016/10/12.
 */
public class CustomerStorageOrderServiceImpl extends BaseFilesServiceImpl implements ICustomerStorageOrderService {

    public CustomerStorageOrderMapper customerStorageOrderMapper;

    public CustomerSummaryMapper customerSummaryMapper;

    public CustomerStorageOrderMapper getCustomerStorageOrderMapper() {
        return customerStorageOrderMapper;
    }

    public void setCustomerStorageOrderMapper(CustomerStorageOrderMapper customerStorageOrderMapper) {
        this.customerStorageOrderMapper = customerStorageOrderMapper;
    }

    public CustomerSummaryMapper getCustomerSummaryMapper() {
        return customerSummaryMapper;
    }

    public void setCustomerSummaryMapper(CustomerSummaryMapper customerSummaryMapper) {
        this.customerSummaryMapper = customerSummaryMapper;
    }

    @Override
    public PageData getCustomerStorageOrderListPageData(PageMap pageMap) throws Exception {
        List<CustomerStorageOrder> list = customerStorageOrderMapper.getCustomerStorageOrderData(pageMap);
        for(CustomerStorageOrder customerStorageOrder : list ){
            BigDecimal totalboxweight = BigDecimal.ZERO;
            BigDecimal totalboxvolume = BigDecimal.ZERO;
            String customerid = customerStorageOrder.getCustomerid();
            Customer customer = getCustomerByID(customerid);
            if(null != customer){
                customerStorageOrder.setCustomername(customer.getName());
            }
            Personnel indoorPerson = getPersonnelById(customerStorageOrder.getIndooruserid());
            if (null != indoorPerson) {
                customerStorageOrder.setIndoorusername(indoorPerson.getName());
            }
            DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(customerStorageOrder.getSalesdept());
            if (departMent != null) {
                customerStorageOrder.setSalesdept(departMent.getName());
            }
            Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(customerStorageOrder.getSalesuser());
            if (personnel != null) {
                customerStorageOrder.setSalesuser(personnel.getName());
            }
            Map map = new HashMap();
            map.put("id", customerStorageOrder.getHandlerid());
            Contacter contacter = getBaseFilesContacterMapper().getContacterDetail(map);
            if (contacter != null) {
                customerStorageOrder.setHandlerid(contacter.getName());
            }
            Map total = customerStorageOrderMapper.getCustomerStorageOrderDetailTotal(customerStorageOrder.getId());
            if (total != null) {
                if (total.containsKey("taxamount")) {
                    customerStorageOrder.setField01(total.get("taxamount").toString());
                    customerStorageOrder.setField04(total.get("taxamount").toString());
                }
                if (total.containsKey("notaxamount")) {
                    customerStorageOrder.setField02(total.get("notaxamount").toString());
                }
                if (total.containsKey("tax")) {
                    customerStorageOrder.setField03(total.get("tax").toString());
                }
            }
            List<CustomerStorageOrderDetail> detailList = customerStorageOrderMapper.getCustomerStorageOrderDetailByOrderid(customerStorageOrder.getId());
            for(CustomerStorageOrderDetail detail : detailList){
                GoodsInfo goodsInfo = getGoodsInfoByID(detail.getGoodsid());
                if(null != goodsInfo){
                    if(null != goodsInfo.getGrossweight()){
                        BigDecimal goodsBoxweight = detail.getUnitnum().multiply(goodsInfo.getGrossweight()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        customerStorageOrder.setField04(goodsBoxweight.toString());
                        totalboxweight = totalboxweight.add(goodsBoxweight);
                    }
                    if(null != goodsInfo.getSinglevolume()){
                        BigDecimal goodsboxvolume = detail.getUnitnum().multiply(goodsInfo.getSinglevolume()).setScale(3, BigDecimal.ROUND_HALF_UP);
                        customerStorageOrder.setField05(goodsboxvolume.toString());
                        totalboxvolume = totalboxvolume.add(goodsboxvolume);
                    }
                }
            }
            //每张订单总重量 总体积 合计
            customerStorageOrder.setField04(totalboxweight.toString());
            customerStorageOrder.setField05(totalboxvolume.toString());
        }
        int count = customerStorageOrderMapper.getCustomerStorageOrderDataCount(pageMap);
        return new PageData(count,list,pageMap);
    }

    @Override
    public CustomerStorageOrder getCustomerStorageOrderById(String id) throws Exception {
        CustomerStorageOrder customerStorageOrder= customerStorageOrderMapper.getCustomerStorageOrderById(id);
        if(null != customerStorageOrder){
            Customer customer = getCustomerByID(customerStorageOrder.getCustomerid());
            if(null != customer){
                customerStorageOrder.setCustomername(customer.getName());
            }
            List<CustomerStorageOrderDetail> detailList = customerStorageOrderMapper.getCustomerStorageOrderDetailByOrderid(id);
            for(CustomerStorageOrderDetail detail : detailList){
                GoodsInfo goodsInfo = getAllGoodsInfoByID(detail.getGoodsid());
                if(null != goodsInfo){
                    if(null != goodsInfo.getGrossweight()){
                        BigDecimal goodsBoxweight = detail.getUnitnum().multiply(goodsInfo.getGrossweight()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        detail.setTotalboxweight(goodsBoxweight.toString());
                    }
                    if(null != goodsInfo.getSinglevolume()){
                        BigDecimal goodsboxvolume = detail.getUnitnum().multiply(goodsInfo.getSinglevolume()).setScale(3, BigDecimal.ROUND_HALF_UP);
                        detail.setTotalboxvolume(goodsboxvolume.toString());
                    }
                }
                detail.setGoodsInfo(goodsInfo);
                TaxType taxType = getTaxType(detail.getTaxtype());
                if(null!=taxType){
                    detail.setTaxtypename(taxType.getName());
                }

            }
            customerStorageOrder.setCustomerStorageOrderDetailList(detailList);

            return customerStorageOrder;
        }
        return null;
    }

    @Override
    public Map addCustomerStorageOrder(CustomerStorageOrder customerStorageOrder) throws Exception {
        String id = "";
        if (isAutoCreate("t_crm_customer_storage")) {
            // 获取自动编号
            id = getAutoCreateSysNumbderForeign(customerStorageOrder, "t_crm_customer_storage");
        }else{
            id = "CSO-"+ CommonUtils.getDataNumberSendsWithRand();
        }
        customerStorageOrder.setId(id);
        customerStorageOrder.setKeyid(id);
        List<CustomerStorageOrderDetail> orderDetailList = customerStorageOrder.getCustomerStorageOrderDetailList();
        int seq = 0;
        for(CustomerStorageOrderDetail detail : orderDetailList){
            if(null != detail ){
                detail.setOrderid(customerStorageOrder.getId());
            }
            GoodsInfo goodsInfo = getAllGoodsInfoByID(detail.getGoodsid());
            if(null != goodsInfo){
                detail.setGoodssort(goodsInfo.getDefaultsort());
                detail.setBrandid(goodsInfo.getBrand());
                if(null == detail.getCostprice()){
                    detail.setCostprice(goodsInfo.getCostaccountprice());
                }
                detail.setOldprice(goodsInfo.getBasesaleprice());
                detail.setFixnum(detail.getUnitnum());
                detail.setGoodssort(goodsInfo.getDefaultsort());

                //获取税种，税额，未税金额，未税单价
                detail.setTaxtype(goodsInfo.getDefaulttaxtype());
                if(null==detail.getTaxamount()){
                    detail.setTaxamount(BigDecimal.ZERO);
                }

                BigDecimal notaxanount = getNotaxAmountByTaxAmount(detail.getTaxamount(), goodsInfo.getDefaulttaxtype());
                detail.setTaxamount(detail.getTaxamount());
                detail.setNotaxamount(notaxanount);
                detail.setTax(detail.getTaxamount().subtract(detail.getNotaxamount()));
                if(null!=notaxanount && null!=detail.getUnitnum() && detail.getUnitnum().compareTo(BigDecimal.ZERO)==1){
                    detail.setNotaxprice(notaxanount.divide(detail.getUnitnum(), 6, BigDecimal.ROUND_HALF_UP));
                }
                //计算辅单位数量
                Map map = countGoodsInfoNumber(detail.getGoodsid(), detail.getAuxunitid(), detail.getUnitnum());
                if (map.containsKey("auxnum")) {
                    detail.setTotalbox((BigDecimal) map.get("auxnum"));
                }
                //获取品牌部门
                Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                if (null != brand){
                    detail.setBranddept(brand.getDeptid());
                }
                //根据客户编号和品牌编号 获取品牌业务员
                detail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), customerStorageOrder.getCustomerid()));
                //厂家业务员
                detail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), customerStorageOrder.getCustomerid()));
                detail.setSupplierid(goodsInfo.getDefaultsupplier());

                //获取系统设置的原价，不包括特价
                detail.setFixprice(getGoodsPriceByCustomer(detail.getGoodsid(), customerStorageOrder.getCustomerid()));
            }
            detail.setSeq(seq);
            seq++;
            customerStorageOrderMapper.addCustomerStorageOrderDetail(detail);
        }
        Map map = new HashMap();
        //根据客户编号获取该客户的销售内勤用户信息
        SysUser sysUser = getSysUser();
        if (sysUser != null && null == customerStorageOrder.getAdduserid()) {
            customerStorageOrder.setAdddeptid(sysUser.getDepartmentid());
            customerStorageOrder.setAdddeptname(sysUser.getDepartmentname());
            customerStorageOrder.setAdduserid(sysUser.getUserid());
            customerStorageOrder.setAddusername(sysUser.getName());
            customerStorageOrder.setAddtime(new Date());
        }
        //销售内勤
        SysUser indoorsysUser = getSalesIndoorSysUserByCustomerid(customerStorageOrder.getCustomerid());
        customerStorageOrder.setIndooruserid(indoorsysUser.getPersonnelid());
        //获取销售区域上级客户
        Customer customer = getCustomerByID(customerStorageOrder.getCustomerid());
        if (null != customer) {
            customerStorageOrder.setSalesarea(customer.getSalesarea());
            customerStorageOrder.setPcustomerid(customer.getPid());
            customerStorageOrder.setCustomersort(customer.getCustomersort());
        }
        boolean flag = customerStorageOrderMapper.addCustomerStorageOrder(customerStorageOrder) > 0;
        if(flag){
            map.put("id",customerStorageOrder.getId());
            map.put("flag",true);
        }else{
            map.put("flag",false);
        }
        return map;
    }

    @Override
    public boolean deleteCustomerStorageSalesOrder(String id) throws Exception {
        customerStorageOrderMapper.deleteByOrderid(id);
        boolean flag = customerStorageOrderMapper.deleteByCustomerStorageOrderId(id) > 0;
        return flag;
    }

    @Override
    public boolean editCustomerStorage(CustomerStorageOrder customerStorageOrder) throws Exception {
        
        //获取销售区域上级客户
        Customer customer = getCustomerByID(customerStorageOrder.getCustomerid());
        if (null != customer) {
            customerStorageOrder.setSalesarea(customer.getSalesarea());
            customerStorageOrder.setPcustomerid(customer.getPid());
            customerStorageOrder.setCustomersort(customer.getCustomersort());
        }
        SysUser sysUser = getSalesIndoorSysUserByCustomerid(customerStorageOrder.getCustomerid());
        if (sysUser != null) {
            //销售内勤
            customerStorageOrder.setIndooruserid(sysUser.getPersonnelid());
        }
        customerStorageOrderMapper.deleteByOrderid(customerStorageOrder.getId());
        List<CustomerStorageOrderDetail> detailList = customerStorageOrder.getCustomerStorageOrderDetailList();
        for(CustomerStorageOrderDetail orderDetail : detailList){
            orderDetail.setOrderid(customerStorageOrder.getId());
            GoodsInfo goodsInfo = getAllGoodsInfoByID(orderDetail.getGoodsid());
            if (null != goodsInfo) {
                //计算辅单位数量
                Map map = countGoodsInfoNumber(orderDetail.getGoodsid(), orderDetail.getAuxunitid(), orderDetail.getUnitnum());
                if (map.containsKey("auxnum")) {
                    orderDetail.setTotalbox((BigDecimal) map.get("auxnum"));
                }
                orderDetail.setGoodssort(goodsInfo.getDefaultsort());
                orderDetail.setBrandid(goodsInfo.getBrand());
                //获取税种，税额，未税金额，未税单价
                orderDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
                if(null==orderDetail.getTaxamount()){
                    orderDetail.setTaxamount(BigDecimal.ZERO);
                }

                BigDecimal notaxanount = getNotaxAmountByTaxAmount(orderDetail.getTaxamount(), goodsInfo.getDefaulttaxtype());
                orderDetail.setTaxamount(orderDetail.getTaxamount());
                orderDetail.setNotaxamount(notaxanount);
                orderDetail.setTax(orderDetail.getTaxamount().subtract(orderDetail.getNotaxamount()));
                if(null!=notaxanount && null!=orderDetail.getUnitnum() && orderDetail.getUnitnum().compareTo(BigDecimal.ZERO)==1){
                    orderDetail.setNotaxprice(notaxanount.divide(orderDetail.getUnitnum(), 6, BigDecimal.ROUND_HALF_UP));
                }
                //获取品牌部门
                Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                if (null != brand) {
                    orderDetail.setBranddept(brand.getDeptid());
                }
                //根据客户编号和品牌编号 获取品牌业务员
                orderDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), customerStorageOrder.getCustomerid()));
                //厂家业务员
                orderDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), customerStorageOrder.getCustomerid()));
                orderDetail.setSupplierid(goodsInfo.getDefaultsupplier());
                //获取系统设置的原价，不包括特价
                orderDetail.setFixprice(getGoodsPriceByCustomer(orderDetail.getGoodsid(), customerStorageOrder.getCustomerid()));
                customerStorageOrderMapper.addCustomerStorageOrderDetail(orderDetail);
            }
        }
        boolean flag = customerStorageOrderMapper.updateCustomerStorageOrder(customerStorageOrder) > 0;

        return flag;
    }

    @Override
    public Map auditCustomerStorage(String id) throws Exception {
        Map map = new HashMap();
        boolean flag = false ;
        int successCount = 0 , failurCount = 0;
        String msg = "", failurGoods = "";
        Map storageMap = new HashMap();//key 单据商品编码 value 单据商品数量
        Map detailMap = new HashMap();//key 单据商品编码 value 单据商品明细
        Map changeMap = new HashMap();//key 库存商品编码 value 截止到单据日期的库存商品数量
        CustomerStorageOrder customerStorageOrder= customerStorageOrderMapper.getCustomerStorageOrderById(id);
        if(null != customerStorageOrder){
            List<CustomerStorageOrderDetail> detailList = customerStorageOrderMapper.getCustomerStorageOrderDetailByOrderid(id);
            if(detailList.size() > 0){
                for(CustomerStorageOrderDetail detail : detailList){
                    storageMap.put(detail.getGoodsid(),detail.getUnitnum());
                    detailMap.put(detail.getGoodsid(),detail);
                }
            }
            String lastDate = customerSummaryMapper.getLastSummaryDayByCustomerid(customerStorageOrder.getCustomerid());//获取该客户最新的库存时间
            String orderDate = customerStorageOrder.getBusinessdate();//当前单据业务日期
            int compare = CommonUtils.compareDate(orderDate,lastDate);
            Map conditon = new HashMap();
            conditon.put("begindate",lastDate);
            conditon.put("enddate",orderDate);
            conditon.put("customerid",customerStorageOrder.getCustomerid());
            PageMap pageMap = new PageMap();
            pageMap.setCondition(conditon);
            //获取库存日期到订单日期时间内的客户库存变动
            //客户库存 = 时间段内发货单数量 - 时间段内退货入库单数量 - 时间段内客户销量 + 开始日期的客户库存数量
            List<Map> list = customerSummaryMapper.getCustomerSummaryFromSalesReport(pageMap);
            if(list.size() > 0){
                for(Map m : list){
                    changeMap.put(m.get("goodsid"),m.get("unitnum"));
                }
            }
            //客户有库存　且　审核单据的订单日期 大于等于 客户商品库存日期
            if(compare >= 0 && StringUtils.isNotEmpty(lastDate)){
                //获取该客户的库存商品 加入到库存历史表
                List<CustomerSummary> summaryList = customerSummaryMapper.getCustomerSummaryByCustomerid(customerStorageOrder.getCustomerid());
                for(CustomerSummary beginSummary : summaryList){
                    CustomerSummary summaryHistory = (CustomerSummary) CommonUtils.deepCopy(beginSummary);
                    summaryHistory.setId(null);
                    //将客户库存商品加入到历史表
                    customerSummaryMapper.addCustomerSummary_h(summaryHistory);
                    //修改客户库存表里的商品信息
                    beginSummary.setBusinessdate(orderDate);
                    String goodsid = beginSummary.getGoodsid();
                    if(storageMap.containsKey(goodsid)){//如果当前单据中存在该库存商品
                        CustomerStorageOrderDetail detail = (CustomerStorageOrderDetail) detailMap.get(goodsid);
                        beginSummary.setUnitnum(detail.getUnitnum());
                        beginSummary.setTotalbox(detail.getTotalbox());
                        beginSummary.setAuxnumdetail(detail.getAuxnumdetail());
//                        BigDecimal orderNum = (BigDecimal) storageMap.get(goodsid);
//                        BigDecimal unitnum = beginSummary.getUnitnum();
//                        //如果库存有更新 则取更新后的数量
//                        if(changeMap.containsKey(goodsid)){
//                            unitnum = (BigDecimal) changeMap.get(goodsid);
//                        }
//                        BigDecimal finalnum = unitnum.add(orderNum);
//                        beginSummary.setUnitnum(finalnum);
//                        GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
//                        //数量组装
//                        BigDecimal boxnum = goodsInfo.getBoxnum();
//                        if(boxnum.compareTo(BigDecimal.ZERO) > 0){
//                            BigDecimal totalbox = finalnum.divide(boxnum,6,BigDecimal.ROUND_HALF_UP);
//                            BigDecimal mainnum = finalnum.divide(boxnum,0,BigDecimal.ROUND_DOWN);
//                            int remaindernum = finalnum.remainder(boxnum).intValue();
//                            beginSummary.setTotalbox(totalbox);
//                            beginSummary.setAuxnumdetail(mainnum+goodsInfo.getAuxunitname()+remaindernum+goodsInfo.getMainunitName());
//                        }
                    }
                    //删除客户旧的库存商品
                    customerSummaryMapper.deleteCustomerSummary(beginSummary.getId());
                    //添加新的客户库存商品
                    int i = customerSummaryMapper.addCustomerSummary(beginSummary);
                    if(i > 0){
                        ++ successCount;
                        storageMap.remove(goodsid);
                    }else{
                        ++ failurCount;
                        failurGoods += beginSummary.getGoodsid() + "," ;
                    }
                }
                if(storageMap.size() > 0){
                    for(Object key : storageMap.keySet()){
                        CustomerStorageOrderDetail detail = (CustomerStorageOrderDetail) detailMap.get(key);
                        flag = addCustomerSummary(detail,customerStorageOrder);//新增客户库存
                        if(flag){
                            ++ successCount;
                        }else{
                            ++ failurCount;
                            failurGoods += detail.getGoodsid() + "," ;
                        }
                    }

                }
            }else if(StringUtils.isEmpty(lastDate)){//该客户不存在库存
                for(CustomerStorageOrderDetail detail : detailList){
                    flag = addCustomerSummary(detail,customerStorageOrder);//新增客户库存
                    if(flag){
                        ++ successCount;
                    }else{
                        ++ failurCount;
                        failurGoods += detail.getGoodsid() + "," ;
                    }
                }
            }else{
                msg = "审核失败，当前客户的库存已更新至"+lastDate+",该客户单据不允许审核";
            }
            if(failurGoods.endsWith(",")){
                failurGoods = failurGoods.substring(0,failurGoods.length() - 1);
            }
            if(successCount > 0){
                customerStorageOrder.setStatus("3");
                SysUser user = getSysUser();
                customerStorageOrder.setAuditusername(user.getName());
                customerStorageOrder.setAudituserid(user.getUserid());
                customerStorageOrder.setAudittime(new Date());
                flag = customerStorageOrderMapper.updateCustomerStorageOrder(customerStorageOrder) > 0;
                if(flag){
                    if(failurCount == 0){
                        msg = "审核成功,客户库存商品更新"+successCount+"条";
                    }else{
                        msg = "审核成功,其中该客户的商品:"+failurGoods+"已存在最新的库存,在客户库存商品中不予添加,导入成功"+successCount+"条";
                    }
                }else{
                    msg = "审核失败,该单据中的客户商品存在最新库存,"+failurGoods+"条导入失败";
                }
            }
        }
        map.put("msg",msg);
        map.put("flag",flag);
        return map;
    }

    @Override
    public Map oppAuditCustomerStorage(String id) throws Exception {
        Map map = new HashMap();
        boolean flag = false ;
        CustomerStorageOrder customerStorageOrder= customerStorageOrderMapper.getCustomerStorageOrderById(id);
        String customerid = customerStorageOrder.getCustomerid();
        List<CustomerStorageOrderDetail> detailList = customerStorageOrderMapper.getCustomerStorageOrderDetailByOrderid(id);
        //删除商品的库存
        for(CustomerStorageOrderDetail detail : detailList){
            customerSummaryMapper.deleteSummaryByCustomerAndGoods(customerid,detail.getGoodsid());
            customerSummaryMapper.deleteSummaryHistoryByCustomerAndGoods(customerid,detail.getGoodsid());
        }
        //反审单据
        customerStorageOrder.setStatus("2");
        flag = customerStorageOrderMapper.updateCustomerStorageOrder(customerStorageOrder) > 0;
        map.put("flag",flag);
        return map;
    }

     /**
      * 根据客户库存单据明细 生成 客户库存记录
      * @author lin_xx
      * @date 2016/12/2
      */
    public boolean addCustomerSummary(CustomerStorageOrderDetail detail,CustomerStorageOrder order) throws Exception {
        boolean flag = false ;
        CustomerSummary summary = new CustomerSummary();
        summary.setBusinessdate(order.getBusinessdate());
        summary.setCustomerid(order.getCustomerid());
        summary.setGoodsid(detail.getGoodsid());
        summary.setGoodssort(detail.getGoodssort());
        summary.setBrandid(detail.getBrandid());
        summary.setSupplierid(detail.getSupplierid());
        summary.setUnitid(detail.getUnitid());
        summary.setUnitnum(detail.getUnitnum());
        summary.setUnitname(detail.getUnitname());
        summary.setAuxnumdetail(detail.getAuxnumdetail());
        summary.setAuxunitid(detail.getAuxunitid());
        summary.setAuxunitname(detail.getAuxunitname());
        summary.setTotalbox(detail.getTotalbox());
        summary.setAddtime(new Date());
        flag = customerSummaryMapper.addCustomerSummary(summary) > 0;
        return flag;
    }

    @Override
    public Map changeModelForDetail(List<ModelOrder> wareList, String gtype) throws Exception {
        Map map = new HashMap();
        List disableGoods = new ArrayList();
        List unimportGoods = new ArrayList();
        List<CustomerStorageOrderDetail> detailList = new ArrayList<CustomerStorageOrderDetail>();
        for(ModelOrder modelOrder : wareList){
            String goodsidentify = "";
            GoodsInfo goodsInfo = new GoodsInfo();
            if("1".equals(gtype)){
                goodsidentify = modelOrder.getBarcode();
                goodsInfo = getGoodsInfoByBarcode(goodsidentify);
            }else if("2".equals(gtype)){//查询店内码
                goodsidentify = modelOrder.getShopid();
                goodsInfo = getGoodsInfoByCustomerGoodsid(modelOrder.getBusid(),goodsidentify);
            }else if("3".equals(gtype)){//查询助记符
                goodsidentify = modelOrder.getSpell();
                goodsInfo = getGoodsInfoBySpell(goodsidentify);
            }else if("4".equals(gtype)){//查询商品编码
                goodsidentify = modelOrder.getGoodsid();
                goodsInfo = getGoodsInfoByID(goodsidentify);
            }
            if(null != goodsInfo){
                goodsInfo = getAllGoodsInfoByID(goodsInfo.getId());
                //禁用商品存在相同条码或者相同编码的情况
                if( "0".equals(goodsInfo.getState())){
                    Map goodsMap = new HashMap();
                    goodsMap.put("id",goodsInfo.getId());
                    List<GoodsInfo> goodsInfoList = getBaseGoodsMapper().getGoodsInfoListByMap(goodsMap);
                    for(GoodsInfo gInfo : goodsInfoList){
                        if(null != gInfo && "1".equals(gInfo.getState())){
                            goodsInfo = getAllGoodsInfoByID(gInfo.getId());
                            break;
                        }
                    }
                    if( "0".equals(goodsInfo.getState())){
                        goodsMap.remove("id");
                        goodsMap.put("barcode",goodsInfo.getBarcode());
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
                    disableGoods.add(goodsidentify);
                    continue;
                }
                CustomerStorageOrderDetail detail = new CustomerStorageOrderDetail();
                detail.setGoodsid(goodsInfo.getId());
                detail.setGoodsInfo(goodsInfo);
                detail.setGoodssort(goodsInfo.getDefaultsort());
                detail.setBrandid(goodsInfo.getBrand());
                detail.setUnitid(goodsInfo.getMainunit());
                detail.setUnitname(goodsInfo.getMainunitName());

                BigDecimal boxnum = goodsInfo.getBoxnum();
                String num = modelOrder.getUnitnum();
                if(StringUtils.isEmpty(num)){
                    continue;
                }
                BigDecimal unitnum = new BigDecimal(num);

                BigDecimal auxnum = new BigDecimal(0);
                BigDecimal overnum = new BigDecimal(0);

                if(null != boxnum && boxnum.compareTo(BigDecimal.ZERO) > 0){
                    auxnum = unitnum.divide(boxnum,0,BigDecimal.ROUND_HALF_UP);
                    overnum = unitnum.subtract(auxnum.multiply(boxnum)).setScale(0,BigDecimal.ROUND_HALF_UP);
                }else{
                    overnum = unitnum;
                }
                detail.setUnitnum(unitnum);
                detail.setAuxunitid(goodsInfo.getAuxunitid());
                detail.setAuxunitname(goodsInfo.getAuxunitname());
                detail.setAuxnum(auxnum);
                detail.setAuxnumdetail(auxnum+goodsInfo.getAuxunitname()+overnum+goodsInfo.getMainunitName());
                detail.setOvernum(overnum);

                //0取基准销售价,1取合同价
                String priceType = getSysParamValue("DELIVERYORDERPRICE");
                BigDecimal price = new BigDecimal(0);
                if(StringUtils.isNotEmpty(modelOrder.getTaxprice())){
                    price = new BigDecimal(modelOrder.getTaxprice());
                }else if( "1".equals(priceType) && !"".equals(modelOrder.getBusid()) ){
                    //系统参数取价
                    price = getGoodsPriceByCustomer(goodsInfo.getId(),modelOrder.getBusid());
                }else {
                    price = goodsInfo.getBasesaleprice();
                }
                detail.setTaxprice(price);
                BigDecimal taxamount = price.multiply(unitnum).setScale(2,BigDecimal.ROUND_HALF_UP);
                detail.setTaxamount(taxamount);

                BigDecimal costprice = BigDecimal.ZERO;

                if(StringUtils.isNotEmpty(modelOrder.getOtherMsg())){
                    costprice = new BigDecimal(modelOrder.getOtherMsg());
                }else{
                    costprice = goodsInfo.getNewstorageprice();
                }
                detail.setCostprice(costprice);
                BigDecimal costtaxamount = costprice.multiply(unitnum);
                detail.setCosttaxamount(costtaxamount);
                TaxType taxType = getTaxType(goodsInfo.getDefaulttaxtype());
                if(null != taxType){
                    BigDecimal rate = taxType.getRate() != null ? taxType.getRate():BigDecimal.ZERO;
                    rate = rate.divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);
                    BigDecimal costnotaxamount = costtaxamount.divide(BigDecimal.ONE.add(rate),6,BigDecimal.ROUND_HALF_UP);
                    detail.setCostnotaxamount(costnotaxamount);
                }

                detailList.add(detail);

            }else{
                if(StringUtils.isNotEmpty(goodsidentify)){
                    unimportGoods.add(goodsidentify);
                }
            }
        }
        if(unimportGoods.size() > 0){
            String unimport = unimportGoods.toString();
            unimport = unimport.replace("[","");unimport = unimport.replace("]","");
            map.put("unimportGoods",unimport);
        }
        String disable = disableGoods.toString();
        disable = disable.replace("[","");disable = disable.replace("]","");
        map.put("disableGoods",disable);
        map.put("detailList",detailList);
        return map;
    }

    @Override
    public Map addPhoneStorageOrder(JSONObject jsonObject) throws Exception {
        Map returnMap = new HashMap();
        boolean flag = false;
        int seq = 0;
        String keyid = "";
        if(jsonObject.has("keyid")){
            keyid = jsonObject.getString("keyid");
        }else{
            keyid = jsonObject.getString("orderid");
        }
        CustomerStorageOrder order = customerStorageOrderMapper.getCustomerStorageOrderByKeyid(keyid);
        if(null != order){
            returnMap.put("flag", flag);
            returnMap.put("isupload", true);
            returnMap.put("msg", "");
            return returnMap;
        }
        String customerId = jsonObject.getString("customerid");
        Customer customer = getCustomerByID(customerId);
        //判断客户是否在系统中存在
        if(null==customer){
            returnMap.put("flag", false);
            returnMap.put("msg", "客户编号:"+customerId+"，在系统中不存在。");
            return returnMap;
        }else{
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            CustomerStorageOrder cso = new CustomerStorageOrder();
            if (isAutoCreate("t_crm_customer_storage")) {
                // 获取自动编号
                String id = getAutoCreateSysNumbderForeign(cso, "t_crm_customer_storage");
                cso.setId(id);
            }else{
                String id = "CSO-"+ CommonUtils.getDataNumberSendsWithRand();
                cso.setId(id);
            }
            cso.setKeyid(keyid);
            cso.setBusinessdate(CommonUtils.getTodayDataStr());
            cso.setStatus("2");
            cso.setCustomerid(customerId);
            cso.setPcustomerid(customer.getPid());
            cso.setSourcetype("1");
            SysUser  sysUser = getSysUser();
            if(customer != null){
                cso.setCustomersort(customer.getCustomersort());
                cso.setHandlerid(customer.getContact());
                cso.setSalesarea(customer.getSalesarea());
                cso.setSalesdept(customer.getSalesdeptid());
                cso.setSalesuser(customer.getSalesuserid());
            }
            SysUser c_user = getSalesIndoorSysUserByCustomerid(cso.getCustomerid());
            if (c_user != null) {
                //销售内勤
                cso.setIndooruserid(c_user.getPersonnelid());
            }
            if(sysUser != null){
                cso.setAdddeptid(sysUser.getDepartmentid());
                cso.setAdddeptname(sysUser.getDepartmentname());
                cso.setAdduserid(sysUser.getUserid());
                cso.setAddusername(sysUser.getName());
                cso.setAddtime(new Date());
            }
            if(jsonArray != null && !jsonArray.isEmpty()){
                for(int i=0; i<jsonArray.size(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    String num = "0";
                    if (json.has("num")) {
                        num = json.getString("num");
                        if ("null".equals(num)) {
                            num = "0";
                        }
                    }
                    if (StringUtils.isNotEmpty(num)) {
                        String goodsId = json.getString("goodsid");
                        CustomerStorageOrderDetail detail = new CustomerStorageOrderDetail();
                        detail.setOrderid(cso.getId());
                        GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsId);
                        if(null != goodsInfo){
                            detail.setGoodsid(goodsId);
                            detail.setGoodssort(goodsInfo.getDefaultsort());
                            detail.setBrandid(goodsInfo.getBrand());
                            Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                            if(null != brand){
                                detail.setBranddept(brand.getDeptid());
                            }
                            //根据客户编号和品牌编号 获取品牌业务员
                            detail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), customerId));
                            //厂家业务员
                            detail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), customerId));
                            detail.setSupplierid(goodsInfo.getDefaultsupplier());
                            detail.setUnitid(goodsInfo.getMainunit());
                            detail.setUnitname(goodsInfo.getMainunitName());
                            detail.setAuxunitid(goodsInfo.getAuxunitid());
                            detail.setAuxunitname(goodsInfo.getAuxunitname());
                            detail.setCostprice(goodsInfo.getCostaccountprice());
                            detail.setOldprice(goodsInfo.getBasesaleprice());
                            detail.setGoodssort(goodsInfo.getDefaultsort());
                            //数量
                            detail.setUnitnum(new BigDecimal(num));
                            detail.setFixnum(new BigDecimal(num));
                            Map map = countGoodsInfoNumber(goodsId, goodsInfo.getAuxunitid(), new BigDecimal(num));
                            if(map.containsKey("auxInteger")){
                                detail.setAuxnum(new BigDecimal(map.get("auxInteger").toString()));
                            }
                            if(map.containsKey("auxremainder")){
                                detail.setOvernum(new BigDecimal(map.get("auxremainder").toString()));
                            }
                            if(map.containsKey("auxnumdetail")){
                                detail.setAuxnumdetail(map.get("auxnumdetail").toString());
                            }
                            if(map.containsKey("auxnum")){
                                detail.setTotalbox(new BigDecimal(map.get("auxnum").toString()));
                            }
                            //价格
//                            if(json.has("price")){
//                                String price = json.getString("price");
//                                detail.setTaxprice(new BigDecimal(price));
//                            }else{
//                                BigDecimal price = getGoodsPriceByCustomer(goodsInfo.getId(),customerId);
//                                detail.setTaxprice(price);
//                            }
                            //手机上传的单据，成本价取商品的成本价，零售价取客户的销售价格
                            //0取基准销售价,1取合同价
                            String priceType = getSysParamValue("DELIVERYORDERPRICE");
                            BigDecimal price = new BigDecimal(0);
                            if( "1".equals(priceType) ){
                                //系统参数取价
                                price = getGoodsPriceByCustomer(goodsInfo.getId(),customerId);
                            }else {
                                price = goodsInfo.getBasesaleprice();
                            }
                            detail.setTaxprice(price);
                            BigDecimal taxamount = price.multiply(new BigDecimal(num)).setScale(2,BigDecimal.ROUND_HALF_UP);
                            detail.setTaxamount(taxamount);
//                            String amount = json.getString("amount");
//                            detail.setTaxamount(new BigDecimal(amount));
                            BigDecimal noTaxAmount = getNotaxAmountByTaxAmount(taxamount, detail.getTaxtype());
                            detail.setNotaxamount(noTaxAmount);
                            BigDecimal tax = detail.getTaxamount().subtract(detail.getNotaxamount());
                            detail.setTax(tax);
                            TaxType taxType = getTaxType(detail.getTaxtype());
                            BigDecimal noTaxPrice = detail.getTaxprice().divide(taxType.getRate().divide(new BigDecimal(100)).add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP);
                            detail.setNotaxprice(noTaxPrice);

                            BigDecimal costprice = goodsInfo.getNewstorageprice();
                            detail.setCostprice(costprice);
                            BigDecimal costtaxamount = costprice.multiply(new BigDecimal(num)).setScale(2,BigDecimal.ROUND_HALF_UP);
                            detail.setCosttaxamount(costtaxamount);
                            BigDecimal costnotaxamount = costtaxamount.divide(taxType.getRate().divide(new BigDecimal(100)).add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP);
                            detail.setCostnotaxamount(costnotaxamount);

                            String remark = json.getString("remark")==null?"":json.getString("remark");
                            remark = CommonUtils.escapeStr(remark);
                            detail.setRemark(remark);

                            detail.setSeq(seq);
                            seq ++;
                            customerStorageOrderMapper.addCustomerStorageOrderDetail(detail);
                        }
                    }
                }
                if(seq > 0){
                    flag = customerStorageOrderMapper.addCustomerStorageOrder(cso) > 0 ;
                    if(flag){
                        returnMap.put("id", cso.getId());
                        returnMap.put("msg","上传成功。生成库存上报:"+cso.getId());
                    }
                }
            }
        }
        returnMap.put("flag", flag);
        if(!flag){
            returnMap.put("msg","生成失败。请检查订单明细");
        }
        return returnMap;

    }


}
