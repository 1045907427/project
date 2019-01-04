/**
 * @(#)DemandServiceImpl.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Oct 24, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.*;
import com.hd.agent.sales.service.IDemandService;
import com.hd.agent.sales.service.ext.IOrderExtService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author zhengziyong
 */
public class DemandServiceImpl extends BaseSalesServiceImpl implements IDemandService {

    private IOrderExtService salesOrderExtService;

    public IOrderExtService getSalesOrderExtService() {
        return salesOrderExtService;
    }

    public void setSalesOrderExtService(IOrderExtService salesOrderExtService) {
        this.salesOrderExtService = salesOrderExtService;
    }

    @Override
    public PageData getDemanData(PageMap pageMap) throws Exception {
        String sql = getDataAccessRule("t_sales_demand", null); //数据权限
        pageMap.setDataSql(sql);
        List<Demand> demandList = getSalesDemandMapper().getDemandList(pageMap);
        for (Demand demand : demandList) {
            Personnel indoorPerson = getPersonnelById(demand.getIndooruserid());
            if (null != indoorPerson) {
                demand.setIndoorusername(indoorPerson.getName());
            }
            DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(demand.getSalesdept());
            if (departMent != null) {
                demand.setSalesdept(departMent.getName());
            }
            Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(demand.getSalesuser());
            if (personnel != null) {
                demand.setSalesuser(personnel.getName());
            }
            Map map = new HashMap();
            map.put("id", demand.getCustomerid());
            Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(map);
            if (customer != null) {
                demand.setCustomername(customer.getName());
            }
            map.put("id", demand.getHandlerid());
            Contacter contacter = getBaseFilesContacterMapper().getContacterDetail(map);
            if (contacter != null) {
                demand.setHandlerid(contacter.getName());
            }
            Map total = getSalesDemandMapper().getDemandDetailTotal(demand.getId());
            if (total != null) {
                if (total.containsKey("taxamount")) {
                    demand.setField01(total.get("taxamount").toString());
                }
                if (total.containsKey("notaxamount")) {
                    demand.setField02(total.get("notaxamount").toString());
                }
                if (total.containsKey("tax")) {
                    demand.setField03(total.get("tax").toString());
                }
            }
            String remarks=getSalesDemandMapper().getDemandRemarks(demand.getId());
            demand.setRemark(remarks);
        }
        return new PageData(getSalesDemandMapper().getDemandCount(pageMap), demandList, pageMap);
    }

    @Override
    public boolean addDemand(Demand demand) throws Exception {
        Demand demand2 = getSalesDemandMapper().getDemand(demand.getId());
        if (demand2 != null && StringUtils.isNotEmpty(demand2.getId())) {
            return false;
        }
        List<DemandDetail> detailList = demand.getDetailList();
        if (detailList.size() > 0) {
            for (DemandDetail detail : detailList) {
                if (detail != null) {
                    detail.setOrderid(demand.getId());
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(detail.getGoodsid());
                    if (null != goodsInfo) {
                        detail.setGoodssort(goodsInfo.getDefaultsort());
                        detail.setBrandid(goodsInfo.getBrand());
                        detail.setCostprice(getGoodsCostprice(demand.getStorageid(),goodsInfo));
                        detail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), demand.getCustomerid()));
                        //厂家业务员
                        detail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), demand.getCustomerid()));
                        //获取品牌部门
                        Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                        if (null != brand) {
                            detail.setBranddept(brand.getDeptid());
                        }
                        detail.setSupplierid(goodsInfo.getDefaultsupplier());
                    }
                    getSalesDemandMapper().addDemandDetail(detail);
                }
            }
        }
        //根据客户编号获取该客户的销售内勤用户信息
        SysUser sysUser = getSalesIndoorSysUserByCustomerid(demand.getCustomerid());
        if (sysUser != null) {
            demand.setIndooruserid(sysUser.getPersonnelid());
        }
        return getSalesDemandMapper().addDemand(demand) > 0;
    }

    @Override
    public Demand getDemand(String id) throws Exception {
        Demand demand = getSalesDemandMapper().getDemand(id);
        List<DemandDetail> detailList = getSalesDemandMapper().getDemandDetailByDemand(id);
        Customer customer = getCustomerByID(demand.getCustomerid());
        if (customer != null) {
            demand.setCustomername(customer.getName());
        }
        for (DemandDetail demandDetail : detailList) {
            GoodsInfo goodsInfo = getGoodsInfoByID(demandDetail.getGoodsid());
            demandDetail.setGoodsInfo(goodsInfo);
            TaxType taxType = getTaxType(demandDetail.getTaxtype());
            if (taxType != null) {
                demandDetail.setTaxtypename(taxType.getName());
            }
            if (null != goodsInfo) {
                BigDecimal boxprice = goodsInfo.getBoxnum().multiply(demandDetail.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);
                demandDetail.setBoxprice(boxprice);
            }
        }
        demand.setDetailList(detailList);
        return demand;
    }

    /**
     * 单线程审核要货订单
     *
     * @param id
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 26, 2018
     */
    private synchronized String synchronizedAuditDemand(String id) throws Exception {
        SysUser user = getSysUser();
        if (id.endsWith(",")) {
            id.substring(0, id.length() - 1);
        }
        String[] idarr = id.split(",");
        String result = "";
        if (idarr.length < 1) {
            return null;
        }
        //要货审核订单时，是否合并单据
        String isDemandAuditMerge = getSysParamValue("IsDemandAuditMerge");
        if(StringUtils.isEmpty(isDemandAuditMerge)){
            isDemandAuditMerge = "1";
        }
        if("1".equals(isDemandAuditMerge)){
            Map customerMap = new HashMap();
            for (int i = 0; i < idarr.length; i++) {
                String nid = idarr[i];
                Demand demand = getSalesDemandMapper().getDemand(nid);
                if (!"0".equals(demand.getStatus())) {
                    return null;
                }
                String customerid = demand.getCustomerid();
                if (customerMap.containsKey(customerid)) {
                    String dids = (String) customerMap.get(customerid);
                    dids += "," + nid;
                    customerMap.put(customerid, dids);
                } else {
                    String dids = nid;
                    customerMap.put(customerid, dids);
                }
            }
            Set set = customerMap.entrySet();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = (Entry<String, String>) it.next();
                String customerid = entry.getKey();
                String billids = entry.getValue();
                String billid = billids.split(",")[0];
                Demand demand = getSalesDemandMapper().getDemand(billid);
                demand.setCustomerid(customerid);
                boolean isMulBill = false;
                if(billids.split(",").length>1){
                    isMulBill = true;
                }
                List<DemandDetail> detailList = getSalesDemandMapper().getDemandDetailByDemandSum(billids);
                int seq = 1;
                for (DemandDetail detail : detailList) {
                    Map map = countGoodsInfoNumber(detail.getGoodsid(), detail.getAuxunitid(), detail.getUnitnum());
                    if (map.containsKey("auxInteger")) {
                        detail.setAuxnum(new BigDecimal(map.get("auxInteger").toString()));
                    }
                    if (map.containsKey("auxremainder")) {
                        detail.setOvernum(new BigDecimal(map.get("auxremainder").toString()));
                    }
                    if (map.containsKey("auxnumdetail")) {
                        detail.setAuxnumdetail(map.get("auxnumdetail").toString());
                    }
                    if (map.containsKey("auxnum")) {
                        detail.setTotalbox(new BigDecimal(map.get("auxnum").toString()));
                    }
                    if(isMulBill){
                        // 重新设定seq
                        detail.setSeq(seq++);
                    }
                }
                demand.setDetailList(detailList);
                //是否根据品牌部门拆分要货单 1是0否
                String isDemandSplitByDept = getSysParamValue("IsDemandSplitByDept");
                String OpenDeptStorage = getSysParamValue("OpenDeptStorage");
                String orderid = null;
                if ("1".equals(isDemandSplitByDept) && !"1".equals(OpenDeptStorage)) {
                    orderid = salesOrderExtService.addOrderSplitByDept(demand);
                } else {
                    orderid = salesOrderExtService.addOrderAuto(demand);
                }
                if (StringUtils.isEmpty(result)) {
                    result = orderid;
                } else {
                    result += "," + orderid;
                }
                String[] billidsArr = billids.split(",");
                for(String demandid : billidsArr){
                    Demand demand2 = new Demand();
                    demand2.setId(demandid);
                    demand2.setStatus("1");
                    demand2.setAudittime(new Date());
                    demand2.setAudituserid(user.getUserid());
                    demand2.setAuditusername(user.getName());
                    demand2.setOrderid(orderid);
                    int i = getSalesDemandMapper().updateDemandStatus(demand2);
                    if(i==0){
                        throw new Exception("更新要货单："+billid+"状态失败，回滚数据");
                    }
                }

            }
        }else{
            for (int i = 0; i < idarr.length; i++) {
                String nid = idarr[i];
                Demand demand = getSalesDemandMapper().getDemand(nid);
                if (!"0".equals(demand.getStatus())) {
                    return null;
                }
                List<DemandDetail> detailList = getSalesDemandMapper().getDemandDetailByDemand(nid);
                for (DemandDetail detail : detailList) {
                    Map map = countGoodsInfoNumber(detail.getGoodsid(), detail.getAuxunitid(), detail.getUnitnum());
                    if (map.containsKey("auxInteger")) {
                        detail.setAuxnum(new BigDecimal(map.get("auxInteger").toString()));
                    }
                    if (map.containsKey("auxremainder")) {
                        detail.setOvernum(new BigDecimal(map.get("auxremainder").toString()));
                    }
                    if (map.containsKey("auxnumdetail")) {
                        detail.setAuxnumdetail(map.get("auxnumdetail").toString());
                    }
                    if (map.containsKey("auxnum")) {
                        detail.setTotalbox(new BigDecimal(map.get("auxnum").toString()));
                    }
                }
                demand.setDetailList(detailList);
                //是否根据品牌部门拆分要货单 1是0否
                String isDemandSplitByDept = getSysParamValue("IsDemandSplitByDept");
                String OpenDeptStorage = getSysParamValue("OpenDeptStorage");
                String orderid = null;
                if ("1".equals(isDemandSplitByDept) && !"1".equals(OpenDeptStorage)) {
                    orderid = salesOrderExtService.addOrderSplitByDept(demand);
                } else {
                    orderid = salesOrderExtService.addOrderAuto(demand);
                }
                if (StringUtils.isEmpty(result)) {
                    result = orderid;
                } else {
                    result += "," + orderid;
                }
                Demand demand2 = new Demand();
                demand2.setId(nid);
                demand2.setStatus("1");
                demand2.setAudittime(new Date());
                demand2.setAudituserid(user.getUserid());
                demand2.setAuditusername(user.getName());
                demand2.setOrderid(orderid);
                int z = getSalesDemandMapper().updateDemandStatus(demand2);
                if(z==0){
                    throw new Exception("更新要货单："+nid+"状态失败，回滚数据");
                }
            }
        }


        return result;
    }

    @Override
    public String auditDemand(String id) throws Exception {
        return synchronizedAuditDemand(id);
    }

    @Override
    public boolean deleteDemand(String id) throws Exception {
        Demand olddemand = getSalesDemandMapper().getDemand(id);
        if (null != olddemand && "0".equals(olddemand.getStatus())) {
            Demand demand = new Demand();
            demand.setId(id);
            demand.setStatus("2");
            demand.setClosetime(new Date());
            return getSalesDemandMapper().updateDemandStatus(demand) > 0;
        } else {
            return false;
        }
    }

    @Override
    public Map updateDemandToOrderCar(String id) throws Exception {
        Map map = new HashMap();
        map.put("id", id);
        boolean flag = false;
        String msg = "";
        Demand demand = getSalesDemandMapper().getDemand(id);
        if (null != demand && "0".equals(demand.getStatus())) {
            List<DemandDetail> detailList = getSalesDemandMapper().getDemandDetailByDemand(id);
            demand.setDetailList(detailList);
            boolean addflag = addDemandToOrderCar(demand);
            if (addflag) {
                int i = getSalesDemandMapper().deleteDemand(id);
                getSalesDemandMapper().deleteDemandDetail(id);
                flag = i > 0;
                msg = "生成直营销售单:" + demand.getId();
            }
        }
        Map returnMap = new HashMap();
        returnMap.put("flag", flag);
        returnMap.put("msg", msg);
        return returnMap;
    }

    public boolean addDemandToOrderCar(Demand demand) throws Exception {
        OrderCar order = new OrderCar();
        order.setId(demand.getId());
        order.setStatus("2");
        order.setBusinessdate(demand.getBusinessdate());
        order.setCustomerid(demand.getCustomerid());
        order.setCustomersort(demand.getCustomersort());
        order.setHandlerid(demand.getHandlerid());
        order.setSalesarea(demand.getSalesarea());
        order.setSalesdept(demand.getSalesdept());
        order.setSalesuser(demand.getSalesuser());
        order.setAdddeptid(demand.getAdddeptid());
        order.setAdddeptname(demand.getAdddeptname());
        order.setAdduserid(demand.getAdduserid());
        order.setAddusername(demand.getAddusername());
        order.setRemark(demand.getRemark());
        SysUser sysUser = getSalesIndoorSysUserByCustomeridForPhone(demand.getCustomerid());
        if (sysUser != null) {
            order.setIndooruserid(sysUser.getPersonnelid());
        }
        List detailList = new ArrayList();
        List<DemandDetail> orderCarDetailList = demand.getDetailList();
        for (DemandDetail demandDetail : orderCarDetailList) {
            OrderCarDetail detail = new OrderCarDetail();
            detail.setOrderid(demand.getId());
            detail.setGoodsid(demandDetail.getGoodsid());
            GoodsInfo goodsInfo = getAllGoodsInfoByID(demandDetail.getGoodsid());
            if (null != goodsInfo) {
                detail.setGoodssort(goodsInfo.getDefaultsort());
                detail.setBrandid(demandDetail.getBrandid());
                //品牌部门
                Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                if (null != brand) {
                    detail.setBranddept(brand.getDeptid());
                }
                detail.setSupplierid(goodsInfo.getDefaultsupplier());
                //根据客户编号和品牌编号 获取品牌业务员
                detail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), demand.getCustomerid()));
                //厂家业务员
                detail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), demand.getCustomerid()));
                detail.setUnitid(goodsInfo.getMainunit());
                detail.setUnitname(goodsInfo.getMainunitName());
                detail.setUnitnum(demandDetail.getUnitnum());
                detail.setAuxunitid(demandDetail.getAuxunitid());
                detail.setAuxunitname(demandDetail.getAuxunitname());
                detail.setAuxnumdetail(demandDetail.getAuxnumdetail());
                detail.setAuxnum(demandDetail.getAuxnum());
                detail.setOvernum(demandDetail.getOvernum());
                detail.setTotalbox(demandDetail.getTotalbox());

                detail.setTaxprice(demandDetail.getTaxprice());
                detail.setTaxtype(goodsInfo.getDefaulttaxtype());
                detail.setTaxamount(demandDetail.getTaxamount());
                detail.setNotaxamount(demandDetail.getNotaxamount());
                detail.setTax(demandDetail.getTax());
                detail.setNotaxprice(demandDetail.getNotaxprice());
                detail.setCostprice(getGoodsCostprice(demand.getStorageid(),goodsInfo));
                detail.setRemark(demandDetail.getRemark());
                getSalesOrderCarMapper().addOrderCarDetail(detail);
            }
        }
        return getSalesOrderCarMapper().addOrderCar(order) > 0;
    }

    @Override
    public boolean checkDemandRepeat(String id) throws Exception {
        String sysparm = getSysParamValue("checkOrderRepeatDays");
        if (StringUtils.isEmpty(sysparm) || !StringUtils.isNumeric(sysparm)) {
            sysparm = "3";
        }
        Demand demand = getSalesDemandMapper().getDemand(id);
        if (null != demand) {
            Map map = getSalesDemandMapper().checkDemandRepeat(id, demand.getCustomerid(), sysparm);
            if (null == map) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public boolean isDemandHaveByKeyid(String keyid) throws Exception {
        Demand demand = getSalesDemandMapper().getDemandByKeyid(keyid);
        if (null != demand) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断客户是否超账期
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Map isReceivablePassDateByIds( String id) throws Exception {
        String msg = "";
        boolean passDateflag = true;
        String[] idarr = id.split(",");
        String result = "";
        if (idarr.length < 1) {
            return null;
        }
        Map customerMap = new HashMap();
        for (int i = 0; i < idarr.length; i++) {
            String nid = idarr[i];
            Demand demand = getSalesDemandMapper().getDemand(nid);
            if (!"0".equals(demand.getStatus())) {
                return null;
            }
            String customerid = demand.getCustomerid();
            //判断该客户当天是否允许放单
            int freeorderCount = getSalesFreeOrderMapper().getSalesFreeOrderCountByCustomeridAndDate(customerid, CommonUtils.getTodayDataStr());
            if (freeorderCount == 0) {
                if (customerMap.containsKey(customerid)) {
                    String dids = (String) customerMap.get(customerid);
                    dids += "," + nid;
                    customerMap.put(customerid, dids);
                } else {
                    String dids = nid;
                    customerMap.put(customerid, dids);
                }
            }
        }
        Set set = customerMap.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Entry<String, String>) it.next();
            String customerid = entry.getKey();
            String billids = entry.getValue();

            Customer customer = getCustomerByID(customerid);
            //超账期判断 1按客户整体来判断 2按该订单中的品牌判断
            String orderPassdateTypeControl = getSysParamValue("OrderPassdateTypeControl");
            if(StringUtils.isEmpty(orderPassdateTypeControl)){
                orderPassdateTypeControl = "1";
            }


            //判断客户是否现结
            Settlement settlement = getSettlementByID(customer.getSettletype());
            boolean settleFlag = false ;
            if (null != settlement && "3".equals(settlement.getType())) {
                settleFlag = true ;
            }

            //判断客户是否超账期控制 现结客户不受该参数控制
            if (null != customer && "1".equals(customer.getOvercontrol()) && !settleFlag) {
                List<String> bList = null;
                if("2".equals(orderPassdateTypeControl)) {
                    if (StringUtils.isNotEmpty(id) && id.indexOf(",") == -1) {
                        bList = getSalesDemandMapper().getBrandListInDemand(id);
                    } else if (StringUtils.isNotEmpty(id) && id.indexOf(",") > 0) {
                        List<String> list = new ArrayList<String>();
                        String[] ids = id.split(",");
                        for (String idStr : ids) {
                            list.add(idStr);
                        }
                        bList = getSalesDemandMapper().getBrandListInDemandByIds(list);
                    }
                }
                BigDecimal passDateAmount = getSalesReceiptMapper().getReceivablePassDateAmountByCustomerid(customer.getId(), CommonUtils.getTodayDataStr(),bList);
                if (null != passDateAmount && passDateAmount.compareTo(BigDecimal.ZERO) != 0) {
                    passDateflag = false;
                    if(StringUtils.isEmpty(msg)){
                        msg += "客户：("+customerid+")"+customer.getName()+",超账期；";
                    }else{
                        msg += "<br/>客户：("+customerid+")"+customer.getName()+",超账期；";
                    }

                    if("2".equals(orderPassdateTypeControl)){
                        List<Map> bPassList = getSalesReceiptMapper().getReceivablePassDateBrandListByCustomerid(customerid, CommonUtils.getTodayDataStr(),bList);
                        if(null!=bPassList && bPassList.size()>0){
                            msg += "其中品牌：";
                            for(Map map : bPassList){
                                String brandid = (String) map.get("brandid");
                                BigDecimal amount = (BigDecimal) map.get("amount");
                                Brand brand = getGoodsBrandByID(brandid);
                                if(null!=brand){
                                    msg += brand.getName()+",超账:"+amount.setScale(2,BigDecimal.ROUND_HALF_UP).toString()+";";
                                }
                            }
                        }
                    }
                }
            }
        }

        Map map = new HashMap();
        map.put("flag",!passDateflag);
        map.put("msg",msg);
        return map;
    }
    /**
     * 查询当天要货单每个人员的数据
     * @param
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Dec 15, 2016
     */
    public List getDemandImageData(){
       List<Map> list=getSalesDemandMapper().getDemandImageData();
        for(Map m:list){
            if(m.get("customernum")==null){
                m.put("customernum",0);
            }if(m.get("addusername")==null){
                m.put("addusername","");
            }if(m.get("taxamount")==null){
                m.put("taxamount",0);
            }
        }
        return list;
    }
    /**
     * 查询当日要货单记录
     * @return
     */
    public List getDemandImageList() throws Exception{
        Map map=new HashMap();
        map.put("startNum",0);
        map.put("rows",10);
        List<Map> list=getSalesDemandMapper().getDemandImageList(map);
        for(Map m:list){
            if(m.get("customerid")!=null){
                String customerid = (String) m.get("customerid");
                Customer customer = getCustomerByID(customerid);
                if(customer != null){
                    m.put("customername", customer.getName());
                }
            }
            Map total = getSalesDemandMapper().getDemandDetailTotal((String)m.get("id"));
            if (total != null) {
                if (total.containsKey("taxamount")) {
                    m.put("taxamount",total.get("taxamount"));
                }
            }
        }
        return list;
    }
    public String getDemandTotalToday(){
        String totalamount=getSalesDemandMapper().getDemandTotalToday();
        if(!StringUtils.isNotEmpty(totalamount)){
            totalamount="0";
        }
        return totalamount;
    }
    /**
     * 查询当日改人员的要货情况
     * @param pageMap
     * @return
     */
    public PageData getPersonnelDemandData(PageMap pageMap) throws Exception{
        String sql = getDataAccessRule("t_sales_demand", "t2"); //数据权限
        pageMap.setDataSql(sql);
        List<Demand> list=getSalesDemandMapper().getPersonnelDemandList(pageMap);
        for(Demand demand:list){
            Personnel indoorPerson = getPersonnelById(demand.getIndooruserid());
            if (null != indoorPerson) {
                demand.setIndoorusername(indoorPerson.getName());
            }
            DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(demand.getSalesdept());
            if (departMent != null) {
                demand.setSalesdept(departMent.getName());
            }
            Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(demand.getSalesuser());
            if (personnel != null) {
                demand.setSalesuser(personnel.getName());
            }
            Map map = new HashMap();
            map.put("id", demand.getCustomerid());
            Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(map);
            if (customer != null) {
                demand.setCustomername(customer.getName());
            }
            map.put("id", demand.getHandlerid());
            Contacter contacter = getBaseFilesContacterMapper().getContacterDetail(map);
            if (contacter != null) {
                demand.setHandlerid(contacter.getName());
            }
            Map total = getSalesDemandMapper().getDemandDetailTotal(demand.getId());
            if (total != null) {
                if (total.containsKey("taxamount")) {
                    demand.setField01(total.get("taxamount").toString());
                }
                if (total.containsKey("notaxamount")) {
                    demand.setField02(total.get("notaxamount").toString());
                }
                if (total.containsKey("tax")) {
                    demand.setField03(total.get("tax").toString());
                }
            }
        }
        int count=getSalesDemandMapper().getPersonnelDemandCount(pageMap);
        PageData pageData=new PageData(count,list,pageMap);
        return pageData;
    }

    /**
     * 要货单关联订货单生成订单
     * @param demandid
     * @param ordergoodsid
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Oct 20, 2017
     */
    @Override
    public String addOrderByDemandAndOrderGoods(String demandid,String ordergoodsid) throws Exception{
        //判断要货单是否需要关联全部订货单商品才能生成订单
        String IsDemandAllOrderGoods=getSysParamValue("IsDemandAllOrderGoods");
        Demand demand=getDemand(demandid);
        List<DemandDetail> demandDetailList=getSalesDemandMapper().getDemandDetailByDemand(demandid);
//        OrderGoods orderGoods=getOrderGoodsMapper().getOrderGoods(ordergoodsid);
//
//        //客户不相等
//        if(!demand.getCustomerid().equals(orderGoods.getCustomerid())){
//            return "no_customer";
//        }

        int orderNum=getOrderGoodsMapper().getDemandGoodsInOrderGoodsNum(demandid, ordergoodsid);

        int demandnum=getSalesDemandMapper().getDemandGoodsNum(demandid);

        if("0".equals(IsDemandAllOrderGoods)){
            //商品不符合
            if(orderNum==0){
                return "no_goodsnum";
            }
        }else{
            //商品不符合
            if(orderNum!=demandnum){
                return "no_goodsnum";
            }
        }


        //记录要货单和订货单关联记录，记录在订货单关联表中
        List<OrderGoodsRelation> orderGoodsRelationList=new ArrayList<OrderGoodsRelation>();
//        List<OrderGoodsDetail> orderGoodsDetailList=getOrderGoodsMapper().getOrderGoodsDetailList(ordergoodsid,"goodsid");
        List<OrderGoodsDetail> orderGoodsDetailList=getOrderGoodsMapper().getOrderGoodsMapList(ordergoodsid);
        for(DemandDetail demandDetail:demandDetailList){
            BigDecimal demandgoodsnum=demandDetail.getUnitnum();
            for(OrderGoodsDetail orderGoodsDetail:orderGoodsDetailList){
                BigDecimal ordergoodsnum=orderGoodsDetail.getNotorderunitnum();
                //商品相同，并且订货单未生成订单的数量大于0,并且要货单的商品数量未完全关联
                if(demandDetail.getGoodsid().equals(orderGoodsDetail.getGoodsid())
                        &&demandDetail.getDeliverytype().equals(orderGoodsDetail.getDeliverytype())
                        &&ordergoodsnum.compareTo(BigDecimal.ZERO)>0
                        &&demandgoodsnum.compareTo(BigDecimal.ZERO)>0){
                    if(ordergoodsnum.compareTo(demandgoodsnum)>=0){
                        ordergoodsnum=ordergoodsnum.subtract(demandgoodsnum);
                        orderGoodsDetail.setOrderunitnum(orderGoodsDetail.getOrderunitnum().add(demandgoodsnum));
                        orderGoodsDetail.setNotorderunitnum(ordergoodsnum);
                        //修改生成订单金额和未生成订单金额
                        orderGoodsDetail.setOrdertaxamount(orderGoodsDetail.getOrderunitnum().multiply(orderGoodsDetail.getTaxprice()));
                        orderGoodsDetail.setOrdernotaxamount(orderGoodsDetail.getOrderunitnum().multiply(orderGoodsDetail.getNotaxprice()));
                        orderGoodsDetail.setNotordertaxamount(orderGoodsDetail.getNotorderunitnum().multiply(orderGoodsDetail.getTaxprice()));
                        orderGoodsDetail.setNotordernotaxamount(orderGoodsDetail.getNotorderunitnum().multiply(orderGoodsDetail.getNotaxprice()));

                        //记录关联订货单信息
                        OrderGoodsRelation orderGoodsRelation=new OrderGoodsRelation();
                        orderGoodsRelation.setBilltype("2");
                        orderGoodsRelation.setOrderid(demandDetail.getOrderid());
                        orderGoodsRelation.setOrderdetailid(demandDetail.getId()+"");
                        orderGoodsRelation.setOrdergoodsid(orderGoodsDetail.getOrderid());
                        orderGoodsRelation.setOrdergoodsdetailid(orderGoodsDetail.getId()+"");
                        orderGoodsRelation.setUnitnum(demandgoodsnum);
                        orderGoodsRelationList.add(orderGoodsRelation);

                        demandgoodsnum=BigDecimal.ZERO;
                    }else{
                        demandgoodsnum=demandgoodsnum.subtract(ordergoodsnum);
                        orderGoodsDetail.setOrderunitnum(orderGoodsDetail.getOrderunitnum().add(ordergoodsnum));
                        orderGoodsDetail.setNotorderunitnum(BigDecimal.ZERO);
                        //修改生成订单金额和未生成订单金额
                        orderGoodsDetail.setOrdertaxamount(orderGoodsDetail.getOrderunitnum().multiply(orderGoodsDetail.getTaxprice()));
                        orderGoodsDetail.setOrdernotaxamount(orderGoodsDetail.getOrderunitnum().multiply(orderGoodsDetail.getNotaxprice()));
                        orderGoodsDetail.setNotordertaxamount(orderGoodsDetail.getNotorderunitnum().multiply(orderGoodsDetail.getTaxprice()));
                        orderGoodsDetail.setNotordernotaxamount(orderGoodsDetail.getNotorderunitnum().multiply(orderGoodsDetail.getNotaxprice()));
                        //记录关联订货单信息
                        OrderGoodsRelation orderGoodsRelation=new OrderGoodsRelation();
                        orderGoodsRelation.setBilltype("2");
                        orderGoodsRelation.setOrderid(demandDetail.getOrderid());
                        orderGoodsRelation.setOrderdetailid(demandDetail.getId()+"");
                        orderGoodsRelation.setOrdergoodsid(orderGoodsDetail.getOrderid());
                        orderGoodsRelation.setOrdergoodsdetailid(orderGoodsDetail.getId()+"");
                        orderGoodsRelation.setUnitnum(ordergoodsnum);
                        ordergoodsnum=BigDecimal.ZERO;
                        orderGoodsRelationList.add(orderGoodsRelation);
                    }
                }
                if(demandgoodsnum.compareTo(BigDecimal.ZERO)==0){
                    continue;
                }
            }
            if(!"0".equals(IsDemandAllOrderGoods)){
                if(demandgoodsnum.compareTo(BigDecimal.ZERO)>0){
                    throw new Exception("回写要货单关联的订货单失败!");
                }
            }
        }
        //添加订货单和要货单关联关系
        int t = getOrderGoodsMapper().insertOrderGoodsRelationList(orderGoodsRelationList);
        if (t == 0) {
            throw new Exception("回写要货单关联的订货单关系失败!");
        }

        //更改要货单的relationordergoodsid字段，为了让这个字段带到订单明细里面
        getOrderGoodsMapper().updateDemandRelationId(demandid,"1");
        //修改要货单关联的订货单字段，要货单生成订单的时候订单的销售部门取订货单销售部门
        getSalesDemandMapper().updateDemandOrderGoodsid(demandid, ordergoodsid);
        String billid = synchronizedAuditDemand(demandid);
        if(StringUtils.isEmpty(billid)){
            //还原要货单的relationordergoodsid字段
            getOrderGoodsMapper().updateDemandRelationId(demandid,"2");
            //删除插入的订货单关系数据
            getOrderGoodsMapper().deleteRelationOrderGoods(demandid);
            //还原之前修改的要货单关联的订货单字段
            getSalesDemandMapper().updateDemandOrderGoodsid(demandid, "");
            return billid;
        }


        getOrderGoodsMapper().updateOrderGoodsDetailNum(demandid, "1", ordergoodsid);

//        }

        return billid;
    }
}

