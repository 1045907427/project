package com.hd.agent.crm.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.crm.dao.CrmSalesOrderMapper;
import com.hd.agent.crm.model.CrmSalesOrder;
import com.hd.agent.crm.model.CrmSalesOrderDetail;
import com.hd.agent.crm.service.ICrmTerminalSalesService;
import com.hd.agent.sales.model.ModelOrder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by lin_xx on 2016/9/23.
 */
public class CrmTerminalSalesServiceImpl  extends BaseFilesServiceImpl implements ICrmTerminalSalesService{

    private CrmSalesOrderMapper crmSalesOrderMapper;

    public CrmSalesOrderMapper getCrmSalesOrderMapper() {
        return crmSalesOrderMapper;
    }

    public void setCrmSalesOrderMapper(CrmSalesOrderMapper crmSalesOrderMapper) {
        this.crmSalesOrderMapper = crmSalesOrderMapper;
    }

    @Override
    public PageData getTerminalOrderData(PageMap pageMap) throws Exception {

        List<CrmSalesOrder> list = crmSalesOrderMapper.getTerminalOrderData(pageMap);
        for(CrmSalesOrder crmSalesOrder : list ){
            BigDecimal totalboxweight = BigDecimal.ZERO;
            BigDecimal totalboxvolume = BigDecimal.ZERO;
            String customerid = crmSalesOrder.getCustomerid();
            Customer customer = getCustomerByID(customerid);
            if(null != customer){
                crmSalesOrder.setCustomername(customer.getName());
            }
            Personnel indoorPerson = getPersonnelById(crmSalesOrder.getIndooruserid());
            if (null != indoorPerson) {
                crmSalesOrder.setIndoorusername(indoorPerson.getName());
            }
            DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(crmSalesOrder.getSalesdept());
            if (departMent != null) {
                crmSalesOrder.setSalesdept(departMent.getName());
            }
            Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(crmSalesOrder.getSalesuser());
            if (personnel != null) {
                crmSalesOrder.setSalesuser(personnel.getName());
            }
            Map map = new HashMap();
            map.put("id", crmSalesOrder.getHandlerid());
            Contacter contacter = getBaseFilesContacterMapper().getContacterDetail(map);
            if (contacter != null) {
                crmSalesOrder.setHandlerid(contacter.getName());
            }
            Map total = crmSalesOrderMapper.getCrmOrderDetailTotal(crmSalesOrder.getId());
            if (total != null) {
                if (total.containsKey("taxamount")) {
                    crmSalesOrder.setField01(total.get("taxamount").toString());
                    crmSalesOrder.setField04(total.get("taxamount").toString());
                }
                if (total.containsKey("notaxamount")) {
                    crmSalesOrder.setField02(total.get("notaxamount").toString());
                }
                if (total.containsKey("tax")) {
                    crmSalesOrder.setField03(total.get("tax").toString());
                }
            }
            List<CrmSalesOrderDetail> detailList = crmSalesOrderMapper.getCrmOrderDetailByOrderid(crmSalesOrder.getId());
            for(CrmSalesOrderDetail detail : detailList){
                GoodsInfo goodsInfo = getGoodsInfoByID(detail.getGoodsid());
                if(null != goodsInfo){
                    if(null != goodsInfo.getGrossweight()){
                        BigDecimal goodsBoxweight = detail.getUnitnum().multiply(goodsInfo.getGrossweight()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        crmSalesOrder.setField04(goodsBoxweight.toString());
                        totalboxweight = totalboxweight.add(goodsBoxweight);
                    }
                    if(null != goodsInfo.getSinglevolume()){
                        BigDecimal goodsboxvolume = detail.getUnitnum().multiply(goodsInfo.getSinglevolume()).setScale(3, BigDecimal.ROUND_HALF_UP);
                        crmSalesOrder.setField05(goodsboxvolume.toString());
                        totalboxvolume = totalboxvolume.add(goodsboxvolume);
                    }
                }
            }
            //每张订单总重量 总体积 合计
            crmSalesOrder.setField04(totalboxweight.toString());
            crmSalesOrder.setField05(totalboxvolume.toString());
        }
        int count = crmSalesOrderMapper.getTerminalOrderDataCount(pageMap);
        return new PageData(count,list,pageMap);
    }

    @Override
    public Map addTerminalOrder(CrmSalesOrder crmSalesOrder) throws Exception {
        String id = "";
        if (isAutoCreate("t_crm_sales_order")) {
            // 获取自动编号
            id = getAutoCreateSysNumbderForeign(crmSalesOrder, "t_crm_sales_order");
        }else{
            id = "CTSD-"+ CommonUtils.getDataNumberSendsWithRand();
        }
        crmSalesOrder.setId(id);
        crmSalesOrder.setKeyid(id);
        List<CrmSalesOrderDetail> orderDetailList = crmSalesOrder.getCrmSalesOrderDetailList();
        int seq = 0;
        for(CrmSalesOrderDetail crmSalesOrderDetail : orderDetailList){
            if(null != crmSalesOrderDetail ){
                crmSalesOrderDetail.setOrderid(crmSalesOrder.getId());
            }
            GoodsInfo goodsInfo = getAllGoodsInfoByID(crmSalesOrderDetail.getGoodsid());
            if(null != goodsInfo){
                crmSalesOrderDetail.setGoodssort(goodsInfo.getDefaultsort());
                crmSalesOrderDetail.setBrandid(goodsInfo.getBrand());
                crmSalesOrderDetail.setCostprice(goodsInfo.getCostaccountprice());
                crmSalesOrderDetail.setOldprice(goodsInfo.getBasesaleprice());
                crmSalesOrderDetail.setFixnum(crmSalesOrderDetail.getUnitnum());
                crmSalesOrderDetail.setGoodssort(goodsInfo.getDefaultsort());

                //获取税种，税额，未税金额，未税单价
                crmSalesOrderDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
                if(null==crmSalesOrderDetail.getTaxamount()){
                    crmSalesOrderDetail.setTaxamount(BigDecimal.ZERO);
                }
                BigDecimal notaxanount = getNotaxAmountByTaxAmount(crmSalesOrderDetail.getTaxamount(), goodsInfo.getDefaulttaxtype());
                crmSalesOrderDetail.setTaxamount(crmSalesOrderDetail.getTaxamount());
                crmSalesOrderDetail.setNotaxamount(notaxanount);
                crmSalesOrderDetail.setTax(crmSalesOrderDetail.getTaxamount().subtract(crmSalesOrderDetail.getNotaxamount()));
                if(null!=notaxanount && null!=crmSalesOrderDetail.getUnitnum() && crmSalesOrderDetail.getUnitnum().compareTo(BigDecimal.ZERO)==1){
                    crmSalesOrderDetail.setNotaxprice(notaxanount.divide(crmSalesOrderDetail.getUnitnum(), 6, BigDecimal.ROUND_HALF_UP));
                }
                //计算辅单位数量
                Map map = countGoodsInfoNumber(crmSalesOrderDetail.getGoodsid(), crmSalesOrderDetail.getAuxunitid(), crmSalesOrderDetail.getUnitnum());
                if (map.containsKey("auxnum")) {
                    crmSalesOrderDetail.setTotalbox((BigDecimal) map.get("auxnum"));
                }
                //获取品牌部门
                Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                if (null != brand){
                    crmSalesOrderDetail.setBranddept(brand.getDeptid());
                }
                //根据客户编号和品牌编号 获取品牌业务员
                crmSalesOrderDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), crmSalesOrder.getCustomerid()));
                //厂家业务员
                crmSalesOrderDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), crmSalesOrder.getCustomerid()));
                crmSalesOrderDetail.setSupplierid(goodsInfo.getDefaultsupplier());

                //获取系统设置的原价，不包括特价
                crmSalesOrderDetail.setFixprice(getGoodsPriceByCustomer(crmSalesOrderDetail.getGoodsid(), crmSalesOrder.getCustomerid()));
            }
            crmSalesOrderDetail.setSeq(seq);
            seq++;
            crmSalesOrderMapper.addCrmSalesOrderDetail(crmSalesOrderDetail);
        }
        Map map = new HashMap();
        SysUser sysUser = getSysUser();
        if (sysUser != null && null == crmSalesOrder.getAdduserid()) {
            crmSalesOrder.setAdddeptid(sysUser.getDepartmentid());
            crmSalesOrder.setAdddeptname(sysUser.getDepartmentname());
            crmSalesOrder.setAdduserid(sysUser.getUserid());
            crmSalesOrder.setAddusername(sysUser.getName());
            crmSalesOrder.setAddtime(new Date());
        }
        SysUser sysUser1 = getSalesIndoorSysUserByCustomerid(crmSalesOrder.getCustomerid());
        if (sysUser1 != null) {
            //销售内勤
            crmSalesOrder.setIndooruserid(sysUser.getPersonnelid());
        }
        //获取销售区域上级客户
        Customer customer = getCustomerByID(crmSalesOrder.getCustomerid());
        if (null != customer) {
            crmSalesOrder.setSalesarea(customer.getSalesarea());
            crmSalesOrder.setPcustomerid(customer.getPid());
            crmSalesOrder.setCustomersort(customer.getCustomersort());
        }
        crmSalesOrder.setKeyid(crmSalesOrder.getId());
        boolean flag = crmSalesOrderMapper.addCrmSalesOrder(crmSalesOrder) > 0;
        if(flag){
            map.put("id",crmSalesOrder.getId());
            map.put("flag",true);
        }else{
            map.put("flag",false);
        }
        return map;
    }

    @Override
    public CrmSalesOrder getTerminalOrderById(String id) throws Exception {

        CrmSalesOrder crmSalesOrder= crmSalesOrderMapper.getCrmSalesOrderById(id);
        if(null != crmSalesOrder){
            Customer customer = getCustomerByID(crmSalesOrder.getCustomerid());
            if(null != customer){
                crmSalesOrder.setCustomername(customer.getName());
            }
            List<CrmSalesOrderDetail> detailList = crmSalesOrderMapper.getCrmOrderDetailByOrderid(id);
            for(CrmSalesOrderDetail detail : detailList){
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

            List<CrmSalesOrderDetail> addList = new ArrayList<CrmSalesOrderDetail>();
            if(detailList.size() <= 559){
                addList = detailList;
            }else{
                for (int i = 0; i < 559; i++) {
                    addList.add(detailList.get(i));
                }
            }
            crmSalesOrder.setCrmSalesOrderDetailList(addList);

            return crmSalesOrder;
        }
        return null;
    }

    @Override
    public boolean editTerminalOrder(CrmSalesOrder crmSalesOrder) throws Exception {

        //获取销售区域上级客户
        Customer customer = getCustomerByID(crmSalesOrder.getCustomerid());
        if (null != customer) {
            crmSalesOrder.setSalesarea(customer.getSalesarea());
            crmSalesOrder.setPcustomerid(customer.getPid());
            crmSalesOrder.setCustomersort(customer.getCustomersort());
        }
        SysUser sysUser = getSalesIndoorSysUserByCustomerid(crmSalesOrder.getCustomerid());
        if (sysUser != null) {
            //销售内勤
            crmSalesOrder.setIndooruserid(sysUser.getPersonnelid());
        }
        crmSalesOrderMapper.deleteByorderid(crmSalesOrder.getId());
        List<CrmSalesOrderDetail> detailList = crmSalesOrder.getCrmSalesOrderDetailList();
        for(CrmSalesOrderDetail orderDetail : detailList){
            orderDetail.setOrderid(crmSalesOrder.getId());
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
                orderDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), crmSalesOrder.getCustomerid()));
                //厂家业务员
                orderDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), crmSalesOrder.getCustomerid()));
                orderDetail.setSupplierid(goodsInfo.getDefaultsupplier());
                //获取系统设置的原价，不包括特价
                orderDetail.setFixprice(getGoodsPriceByCustomer(orderDetail.getGoodsid(), crmSalesOrder.getCustomerid()));
                crmSalesOrderMapper.addCrmSalesOrderDetail(orderDetail);
            }
        }
        boolean flag = crmSalesOrderMapper.updateCrmSalseOrder(crmSalesOrder) > 0;

        return flag;
    }

    @Override
    public boolean deleteTerminalSalesOrder(String id) throws Exception {
        crmSalesOrderMapper.deleteByorderid(id);
        boolean flag = crmSalesOrderMapper.deleteCrmSalesOrderById(id)>0;
        return flag;
    }

    @Override
    public Map changeModelForDetail(List<ModelOrder> wareList, String gtype) throws Exception {
        Map map = new HashMap();
        List disableGoods = new ArrayList();
        String errorIdentify = "";
        List<CrmSalesOrderDetail> detailList = new ArrayList<CrmSalesOrderDetail>();
        for(ModelOrder modelOrder : wareList){
            String goodsidentify = "";
            GoodsInfo goodsInfo = new GoodsInfo();
            if("1".equals(gtype)){
                goodsidentify = modelOrder.getBarcode();
                goodsInfo = getGoodsInfoByBarcode(goodsidentify);
            }else if("2".equals(gtype)){//查询店内码
                goodsidentify = modelOrder.getShopid();
                goodsInfo = getGoodsInfoByCustomerGoodsid(modelOrder.getBusid(),modelOrder.getShopid());
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
                CrmSalesOrderDetail detail = new CrmSalesOrderDetail();
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
                    auxnum = unitnum.divide(boxnum,0,BigDecimal.ROUND_DOWN);
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
                }else if("1".equals(priceType) &&!"".equals(modelOrder.getBusid())){
                    //系统参数取价
                    price = getGoodsPriceByCustomer(goodsInfo.getId(),modelOrder.getBusid());
                }else {
                    price = goodsInfo.getBasesaleprice();
                }
                detail.setTaxprice(price);
                BigDecimal taxamount = price.multiply(unitnum).setScale(2,BigDecimal.ROUND_HALF_UP);
                detail.setTaxamount(taxamount);

                detailList.add(detail);

            }else {
                if (org.apache.commons.lang3.StringUtils.isNotEmpty(modelOrder.getBarcode()) && "1".equals(gtype)
                        && !"null".equals(modelOrder.getBarcode())) {
                    errorIdentify += modelOrder.getBarcode()+",";
                } else if (org.apache.commons.lang3.StringUtils.isNotEmpty(modelOrder.getShopid()) && "2".equals(gtype)
                        && !"null".equals(modelOrder.getShopid())) {
                    errorIdentify += modelOrder.getShopid()+",";
                } else if (org.apache.commons.lang3.StringUtils.isNotEmpty(modelOrder.getSpell()) && "3".equals(gtype)
                        && !"null".equals(modelOrder.getSpell())) {
                    errorIdentify += modelOrder.getSpell()+",";
                } else if (org.apache.commons.lang3.StringUtils.isNotEmpty(modelOrder.getGoodsid())
                        && !"null".equals(modelOrder.getGoodsid())) {
                    errorIdentify += modelOrder.getGoodsid()+",";
                }

            }
        }
        if(StringUtils.isNotEmpty(errorIdentify)){
            map.put("errorIdentify",errorIdentify);//不存在的商品
        }
        String disable = disableGoods.toString();
        disable = disable.replace("[","");disable = disable.replace("]","");
        map.put("disableGoods",disable);
        map.put("detailList",detailList);
        return map;
    }

    @Override
    public PageData getTerminalSalesOrderReportData(PageMap pageMap) throws Exception {
        List<Map> list = crmSalesOrderMapper.getTerminalSalesOrderReportData(pageMap);
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
            param = (String) map.get("salesdept");
            DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(param);
            if (departMent != null) {
                map.put("salesdeptname", departMent.getName());
            }
            param = (String) map.get("salesuser");
            Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(param);
            if (personnel != null) {
                map.put("salesusername", personnel.getName());
            }
            param = (String) map.get("indooruserid");
            personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(param);
            if (personnel != null) {
                map.put("indoorusername", personnel.getName());
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
        int count = crmSalesOrderMapper.getTerminalSalesOrderReportDataCount(pageMap);
        return new PageData(count,list,pageMap);
    }

    @Override
    public Map addPhoneTerminalOrder(JSONObject jsonObject) throws Exception {
        Map returnMap = new HashMap();
        boolean flag = false;
        int seq = 0;
        String keyid = "";
        if(jsonObject.has("keyid")){
            keyid = jsonObject.getString("keyid");
        }else{
            keyid = jsonObject.getString("orderid");
        }
        CrmSalesOrder order = crmSalesOrderMapper.getCrmSalesOrderByKeyid(keyid);
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
            CrmSalesOrder crmSalesOrder = new CrmSalesOrder();
            if (isAutoCreate("t_crm_sales_order")) {
                // 获取自动编号
                String id = getAutoCreateSysNumbderForeign(crmSalesOrder, "t_crm_sales_order");
                crmSalesOrder.setId(id);
            }else{
                String id = "CTSD-"+ CommonUtils.getDataNumberSendsWithRand();
                crmSalesOrder.setId(id);
            }
            crmSalesOrder.setKeyid(keyid);
            crmSalesOrder.setBusinessdate(CommonUtils.getTodayDataStr());
            crmSalesOrder.setStatus("2");
            crmSalesOrder.setCustomerid(customerId);
            crmSalesOrder.setPcustomerid(customer.getPid());
            crmSalesOrder.setSourcetype("1");
            SysUser sysUser = getSysUser();
            if(customer != null){
                crmSalesOrder.setCustomersort(customer.getCustomersort());
                crmSalesOrder.setHandlerid(customer.getContact());
                crmSalesOrder.setSalesarea(customer.getSalesarea());
                crmSalesOrder.setSalesdept(customer.getSalesdeptid());
                crmSalesOrder.setSalesuser(customer.getSalesuserid());
            }
            SysUser c_user = getSalesIndoorSysUserByCustomerid(crmSalesOrder.getCustomerid());
            if (c_user != null) {
                //销售内勤
                crmSalesOrder.setIndooruserid(c_user.getPersonnelid());
            }
            if(sysUser != null){
                crmSalesOrder.setAdddeptid(sysUser.getDepartmentid());
                crmSalesOrder.setAdddeptname(sysUser.getDepartmentname());
                crmSalesOrder.setAdduserid(sysUser.getUserid());
                crmSalesOrder.setAddusername(sysUser.getName());
                crmSalesOrder.setAddtime(new Date());
                //销售内勤
                crmSalesOrder.setIndooruserid(sysUser.getPersonnelid());
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
                        CrmSalesOrderDetail detail = new CrmSalesOrderDetail();
                        detail.setOrderid(crmSalesOrder.getId());
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
                            String price = json.getString("price");
                            detail.setTaxprice(new BigDecimal(price));
                            String amount = json.getString("amount");
                            BigDecimal taxamount=new BigDecimal(amount);
                            detail.setTaxamount(taxamount);

                            BigDecimal noTaxAmount = getNotaxAmountByTaxAmount(taxamount, detail.getTaxtype());
                            detail.setNotaxamount(noTaxAmount);
                            BigDecimal tax = detail.getTaxamount().subtract(detail.getNotaxamount());

                            detail.setTax(tax);
                            TaxType taxType = getTaxType(detail.getTaxtype());
                            BigDecimal noTaxPrice = detail.getTaxprice().divide(taxType.getRate().divide(new BigDecimal(100)).add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP);
                            detail.setNotaxprice(noTaxPrice);
                            String remark = json.getString("remark")==null?"":json.getString("remark");
                            remark = CommonUtils.escapeStr(remark);
                            detail.setRemark(remark);

                            detail.setSeq(seq);
                            seq ++;
                            crmSalesOrderMapper.addCrmSalesOrderDetail(detail);
                        }
                    }
                }
                if(seq > 0) {
                    flag = crmSalesOrderMapper.addCrmSalesOrder(crmSalesOrder) > 0;
                    if (flag) {
                        returnMap.put("id", crmSalesOrder.getId());
                        returnMap.put("msg","上传成功。生成销售上报:"+crmSalesOrder.getId());
                    }
                }
            }
        }
        returnMap.put("flag", flag);
        if(!flag){
            returnMap.put("msg","生成失败，请检查订单明细！");
        }
        return returnMap;
    }

    @Override
    public PageData getCustomerTerminalSalesData(PageMap pageMap) throws Exception {
        List<Map> list = crmSalesOrderMapper.getCustomerTerminalSalesData(pageMap);
        for(Map map :list){
            String customerid = (String) map.get("customerid");
            Customer customer = getCustomerByID(customerid);
            if(null != customer){
                map.put("customername",customer.getName());
            }
            String salesdept = (String) map.get("salesdept");
            DepartMent departMent = getDepartMentById(salesdept);
            if(null != departMent){
                map.put("salesdept",departMent.getName());
            }
        }
        int count = crmSalesOrderMapper.getCustomerTerminalSalesDataCount(pageMap);
        return new PageData(count,list,pageMap);
    }


}
