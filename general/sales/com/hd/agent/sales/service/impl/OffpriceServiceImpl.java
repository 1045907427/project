/**
 * @(#)OffpriceServiceImpl.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 24, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.*;
import com.hd.agent.sales.model.OffpriceExcel;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.dao.CustomerSortMapper;
import com.hd.agent.sales.model.Offprice;
import com.hd.agent.sales.model.OffpriceDetail;
import com.hd.agent.sales.model.OrderDetail;
import com.hd.agent.sales.service.IOffpriceService;
import com.hd.agent.storage.model.PurchaseEnter;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhengziyong
 */
public class OffpriceServiceImpl extends BaseSalesServiceImpl implements IOffpriceService {

    @Override
    public boolean addOffprice(Offprice model) throws Exception {
        List<OffpriceDetail> detailList = model.getDetailList();
        if (detailList != null) {
            for (OffpriceDetail detail : detailList) {
                detail.setBillid(model.getId());
                getSalesOffpriceMapper().addOffpriceDetail(detail);
            }
        }
        return getSalesOffpriceMapper().addOffprice(model) > 0;
    }

    @Override
    public PageData getOffpriceData(PageMap pageMap) throws Exception {
        String dataSql = getDataAccessRule("t_sales_offprice", null);
        pageMap.setDataSql(dataSql);
        List<Offprice> list = getSalesOffpriceMapper().getOffpriceList(pageMap);
        for (Offprice model : list) {
            DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(model.getApplydeptid());
            if (departMent != null) {
                model.setApplydeptid(departMent.getName());
            }
            Personnel personnel = getPersonnelById(model.getApplyuserid());
            if (personnel != null) {
                model.setApplyuserid(personnel.getName());
            }
            if ("2".equals(model.getCustomertype())) {
                if(model.getCustomerid().contains(",")){//客户群名称存在多个
                    String[] cids = model.getCustomerid().split(",");
                    String customername = "";
                    for(int i = 0 ; i<cids.length ;i++){
                        SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(cids[i], "promotionsort");
                        if (sysCode != null) {
                            if(customername == ""){
                                customername =  sysCode.getCodename();
                            }else{
                                customername += "," + sysCode.getCodename() ;
                            }
                        }
                    }
                    model.setCustomername(customername);
                }else{
                    SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(model.getCustomerid(), "promotionsort");
                    if (sysCode != null) {
                        model.setCustomername(sysCode.getCodename());
                    }
                }
            } else if ("3".equals(model.getCustomertype())) {
                CustomerSortMapper customerSortMapper = (CustomerSortMapper) SpringContextUtils.getBean("customerSortMapper");
                Map map = new HashMap();
                if(model.getCustomerid().contains(",")){//客户群名称存在多个
                    String[] cids = model.getCustomerid().split(",");
                    String customername = "";
                    for(int i = 0 ; i<cids.length ;i++){
                        map.put("id",cids[i]);
                        CustomerSort customerSort = customerSortMapper.getCustomerSortDetail(map);
                        if (customerSort != null) {
                            if(customername == ""){
                                customername =  customerSort.getName() ;
                            }else{
                                customername += "," + customerSort.getName();
                            }
                        }
                    }
                    model.setCustomername(customername.toString());
                }else{
                    map.put("id", model.getCustomerid());

                    CustomerSort customerSort = customerSortMapper.getCustomerSortDetail(map);
                    if (customerSort != null) {
                        model.setCustomerid(customerSort.getId());
                        model.setCustomername(customerSort.getName());
                    }
                }
            } else if ("4".equals(model.getCustomertype())) {
                if(model.getCustomerid().contains(",")){//客户群名称存在多个
                    String[] cids = model.getCustomerid().split(",");
                    String customername = "";
                    for(int i = 0 ; i<cids.length ;i++){
                        SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(cids[i], "price_list");
                        if (sysCode != null) {
                            if(customername == ""){
                                customername =  sysCode.getCodename();
                            }else{
                                customername += "," + sysCode.getCodename() ;
                            }
                        }
                    }
                    model.setCustomername(customername);
                }else{
                    SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(model.getCustomerid(), "price_list");
                    if (sysCode != null) {
                        model.setCustomername(sysCode.getCodename());
                    }
                }
            } else if ("5".equals(model.getCustomertype())) {
                Map map = new HashMap();
                map.put("id", model.getCustomerid());
                if(model.getCustomerid().contains(",")){//客户群名称存在多个
                    String[] cids = model.getCustomerid().split(",");
                    String customername = "";
                    for(int i = 0 ; i<cids.length ;i++){
                        map.put("id",cids[i]);
                        SalesArea salesArea = getBaseFilesSalesAreaMapper().getSalesAreaDetail(map);
                        if (salesArea != null) {
                            if(customername == ""){
                                customername =  salesArea.getName() ;
                            }else{
                                customername += "," + salesArea.getName() ;
                            }
                        }
                    }
                    model.setCustomername(customername.toString());
                }else{
                    map.put("id", model.getCustomerid());

                    SalesArea salesArea = getBaseFilesSalesAreaMapper().getSalesAreaDetail(map);
                    if (salesArea != null) {
                        model.setCustomerid(salesArea.getId());
                        model.setCustomername(salesArea.getName());
                    }
                }
            } else if ("7".equals(model.getCustomertype())) {
                if(model.getCustomerid().contains(",")){//客户群名称存在多个
                    String[] cids = model.getCustomerid().split(",");
                    String customername = "";
                    for(int i = 0 ; i<cids.length ;i++){
                        SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(cids[i], "creditrating");
                        if (sysCode != null) {
                            if(customername == ""){
                                customername =  sysCode.getCodename();
                            }else{
                                customername += "," + sysCode.getCodename() ;
                            }
                        }
                    }
                    model.setCustomername(customername);
                }else{
                    SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(model.getCustomerid(), "creditrating");
                    if (sysCode != null) {
                        model.setCustomername(sysCode.getCodename());
                    }
                }
            } else if ("8".equals(model.getCustomertype())) {
                if(model.getCustomerid().contains(",")){//客户群名称存在多个
                    String[] cids = model.getCustomerid().split(",");
                    String customername = "";
                    for(int i = 0 ; i<cids.length ;i++){
                        SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(cids[i], "canceltype");
                        if (sysCode != null) {
                            if(customername == ""){
                                customername =  sysCode.getCodename();
                            }else{
                                customername += "," + sysCode.getCodename() ;
                            }
                        }
                    }
                    model.setCustomername(customername);
                }else{
                    SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(model.getCustomerid(), "canceltype");
                    if (sysCode != null) {
                        model.setCustomername(sysCode.getCodename());
                    }
                }

            } else {
                Map map = new HashMap();
                if(model.getCustomerid().contains(",")){//客户群名称存在多个
                    String[] cids = model.getCustomerid().split(",");
                    String customername = "";
                    for(int i = 0 ; i<cids.length ;i++){
                        map.put("id",cids[i]);
                        Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(map);
                        if (customer != null) {
                            if(customername == ""){
                                customername =  customer.getName() ;
                            }else{
                                customername += "," + customer.getName() ;
                            }
                        }
                    }
                    model.setCustomername(customername.toString());
                }else{
                    map.put("id", model.getCustomerid());

                    Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(map);
                    if (customer != null) {
                        model.setCustomername(customer.getName());
                    }
                }
            }
        }
        return new PageData(getSalesOffpriceMapper().getOffpriceCount(pageMap), list, pageMap);
    }

    @Override
    public Offprice getOffprice(String id) throws Exception {
        List<OffpriceDetail> detailList = getSalesOffpriceMapper().getDetailListByOffprice(id);
        for (OffpriceDetail detail : detailList) {
            GoodsInfo goodsInfo = getGoodsInfoByID(detail.getGoodsid());
            detail.setGoodsInfo(goodsInfo);
        }
        Offprice offprice = getSalesOffpriceMapper().getOffprice(id);
        offprice.setDetailList(detailList);
        return offprice;
    }

    @Override
    public boolean deleteOffprice(String id) throws Exception {
        getSalesOffpriceMapper().deleteDetailByOffprice(id);
        return getSalesOffpriceMapper().deleteOffprice(id) > 0;
    }

    @Override
    public boolean updateOffprice(Offprice model) throws Exception {
        Offprice offprice = getSalesOffpriceMapper().getOffprice(model.getId());
        if(null==offprice || "3".equals(offprice.getStatus()) || "4".equals(offprice.getStatus())){
            return false;
        }
        getSalesOffpriceMapper().deleteDetailByOffprice(model.getId());
        List<OffpriceDetail> detailList = model.getDetailList();
        for (OffpriceDetail detail : detailList) {
            detail.setBillid(model.getId());
            getSalesOffpriceMapper().addOffpriceDetail(detail);
        }
        return getSalesOffpriceMapper().updateOffprice(model) > 0;
    }

    @Override
    public boolean auditOffprice(String type, String id) throws Exception {
        SysUser sysUser = getSysUser();
        Offprice offprice = getOffprice(id);
        if ("1".equals(type)) { //审核
            if ("2".equals(offprice.getStatus())) { //只有状态为2（保存状态）才可进行审核
                Offprice offprice2 = new Offprice();
                offprice2.setId(id);
                offprice2.setStatus("3");
                offprice2.setAudituserid(sysUser.getUserid());
                offprice2.setAuditusername(sysUser.getName());
                offprice2.setAudittime(new Date());
                return getSalesOffpriceMapper().updateOffpriceStatus(offprice2) > 0;
            }
        } else if ("2".equals(type)) { //反审
            if ("3".equals(offprice.getStatus())) { //只有状态为3（审核状态）才可进行反审
                Offprice offprice2 = new Offprice();
                offprice2.setId(id);
                offprice2.setStatus("2");
                return getSalesOffpriceMapper().updateOffpriceStatus(offprice2) > 0;
            }
        }
        return false;
    }

    @Override
    public boolean submitOffpriceProcess(String title, String userId, String processDefinitionKey, String businessKey, Map<String, Object> variables) throws Exception {
        return false;
    }

    @Override
    public String addAndAuditOffpriceToOa(Offprice offprice, List<OffpriceDetail> detailList) throws Exception {
        SysUser sysUser = getSysUser();
        if (isAutoCreate("t_sales_offprice")) {
            // 获取自动编号
            String id = getAutoCreateSysNumbderForeign(offprice, "t_sales_offprice");
            offprice.setId(id);
        }
        offprice.setAdddeptid(sysUser.getDepartmentid());
        offprice.setAdddeptname(sysUser.getDepartmentname());
        offprice.setAdduserid(sysUser.getUserid());
        offprice.setAddusername(sysUser.getName());
        offprice.setAudittime(new Date());
        offprice.setAudituserid(sysUser.getUserid());
        offprice.setAuditusername(sysUser.getName());
        offprice.setStatus("3");
        if (detailList != null) {
            for (OffpriceDetail detail : detailList) {
                detail.setBillid(offprice.getId());
                getSalesOffpriceMapper().addOffpriceDetail(detail);
            }
        }
        boolean flag = getSalesOffpriceMapper().addOffprice(offprice) > 0;
        if (flag) {
            return offprice.getId();
        } else {
            return null;
        }

    }

    @Override
    public boolean deleteOffpriceByOA(String oaid) throws Exception {
        getSalesOffpriceMapper().deleteOffpriceDetailByOA(oaid);
        int i = getSalesOffpriceMapper().deleteOffpriceByOA(oaid);
        return i > 0;
    }

    @Override
    public Offprice selectOffPriceByOaid(String oaid) throws Exception {

        return getSalesOffpriceMapper().selectOffPriceByOaid(oaid);
    }
    @Override
    public List getOffpriceListBy(Map map) throws Exception{
        String sql = getDataAccessRule("t_sales_offprice", null); //数据权限
        map.put("dataSql", sql);
        List<Offprice> list = getSalesOffpriceMapper().getOffpriceListBy(map);
        boolean showdetail = false;
        if (null != map.get("showdetail") && StringUtils.isNotEmpty(map.get("showdetail").toString()) && "1".equals(map.get("showdetail"))) {
            showdetail = true;
        }
        Map queryMap = new HashMap();
        SysCode sysCode=null;
        for(Offprice item : list){
            if (null != item) {

                if(true==showdetail){
                    List<OffpriceDetail> detailList = getSalesOffpriceMapper().getDetailListByOffprice(item.getId());
                    for (OffpriceDetail detail : detailList) {
                        GoodsInfo goodsInfo = getGoodsInfoByID(detail.getGoodsid());
                        detail.setGoodsInfo(goodsInfo);
                    }
                    item.setDetailList(detailList);
                }


                DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(item.getApplydeptid());
                if (departMent != null) {
                    item.setApplydeptid(departMent.getName());
                }
                Personnel personnel = getPersonnelById(item.getApplyuserid());
                if (personnel != null) {
                    item.setApplyuserid(personnel.getName());
                }
                if(null!=item.getCustomertype()){
                    sysCode = getBaseSysCodeMapper().getSysCodeInfo(item.getCustomertype(), "customertype");
                    if(null!=sysCode){
                        item.setCustomertypename(sysCode.getCodename());
                    }
                }
                if ("2".equals(item.getCustomertype())) {
                    if(item.getCustomerid().contains(",")){//客户群名称存在多个
                        String[] cids = item.getCustomerid().split(",");
                        String customername = "";
                        for(int i = 0 ; i<cids.length ;i++){
                            sysCode= getBaseSysCodeMapper().getSysCodeInfo(cids[i], "promotionsort");
                            if (sysCode != null) {
                                if(customername == ""){
                                    customername =  sysCode.getCodename();
                                }else{
                                    customername += "," + sysCode.getCodename() ;
                                }
                            }
                        }
                        item.setCustomername(customername);
                    }else{
                        sysCode = getBaseSysCodeMapper().getSysCodeInfo(item.getCustomerid(), "promotionsort");
                        if (sysCode != null) {
                            item.setCustomername(sysCode.getCodename());
                        }
                    }
                } else if ("3".equals(item.getCustomertype())) {
                    CustomerSortMapper customerSortMapper = (CustomerSortMapper) SpringContextUtils.getBean("customerSortMapper");
                    queryMap = new HashMap();
                    if(item.getCustomerid().contains(",")){//客户群名称存在多个
                        String[] cids = item.getCustomerid().split(",");
                        String customername = "";
                        for(int i = 0 ; i<cids.length ;i++){
                            map.put("id",cids[i]);
                            CustomerSort customerSort = customerSortMapper.getCustomerSortDetail(map);
                            if (customerSort != null) {
                                item.setCustomerid(customerSort.getId());
                                if(customername == ""){
                                    customername =  customerSort.getName() ;
                                }else{
                                    customername += "," + customerSort.getName() ;
                                }
                            }
                        }
                        item.setCustomername(customername.toString());
                    }else{
                        map.put("id", item.getCustomerid());

                        CustomerSort customerSort = customerSortMapper.getCustomerSortDetail(map);
                        if (customerSort != null) {
                            item.setCustomerid(customerSort.getId());
                            item.setCustomername(customerSort.getName());
                        }
                    }
                } else if ("4".equals(item.getCustomertype()) || "7".equals(item.getCustomertype())) {
                    if(item.getCustomerid().contains(",")){//客户群名称存在多个
                        String[] cids = item.getCustomerid().split(",");
                        String customername = "";
                        for(int i = 0 ; i<cids.length ;i++){
                            sysCode = getBaseSysCodeMapper().getSysCodeInfo(cids[i], "price_list");
                            if (sysCode != null) {
                                if(customername == ""){
                                    customername =  sysCode.getCodename();
                                }else{
                                    customername += "," + sysCode.getCodename() ;
                                }
                            }
                        }
                        item.setCustomername(customername);
                    }else{
                        sysCode = getBaseSysCodeMapper().getSysCodeInfo(item.getCustomerid(), "price_list");
                        if (sysCode != null) {
                            item.setCustomername(sysCode.getCodename());
                        }
                    }
                } else if ("5".equals(item.getCustomertype())) {
                    queryMap = new HashMap();
                    map.put("id", item.getCustomerid());
                    if(item.getCustomerid().contains(",")){//客户群名称存在多个
                        String[] cids = item.getCustomerid().split(",");
                        String customername = "";
                        for(int i = 0 ; i<cids.length ;i++){
                            map.put("id",cids[i]);
                            SalesArea salesArea = getBaseFilesSalesAreaMapper().getSalesAreaDetail(map);
                            if (salesArea != null) {
                                item.setCustomerid(salesArea.getId());
                                if(customername == ""){
                                    customername =  salesArea.getName() ;
                                }else{
                                    customername += "," + salesArea.getName() ;
                                }
                            }
                        }
                        item.setCustomername(customername.toString());
                    }else{
                        map.put("id", item.getCustomerid());

                        SalesArea salesArea = getBaseFilesSalesAreaMapper().getSalesAreaDetail(map);
                        if (salesArea != null) {
                            item.setCustomerid(salesArea.getId());
                            item.setCustomername(salesArea.getName());
                        }
                    }
                } /*else if ("7".equals(item.getCustomertype())) {
                    SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(item.getCustomerid(), "price_list");
                    if (sysCode != null) {
                        item.setCustomername(sysCode.getCodename());
                    }
                } */else if ("8".equals(item.getCustomertype())) {
                    if(item.getCustomerid().contains(",")){//客户群名称存在多个
                        String[] cids = item.getCustomerid().split(",");
                        String customername = "";
                        for(int i = 0 ; i<cids.length ;i++){
                            sysCode = getBaseSysCodeMapper().getSysCodeInfo(cids[i], "canceltype");
                            if (sysCode != null) {
                                if(customername == ""){
                                    customername =  sysCode.getCodename();
                                }else{
                                    customername += "," + sysCode.getCodename() ;
                                }
                            }
                        }
                        item.setCustomername(customername);
                    }else{
                        sysCode = getBaseSysCodeMapper().getSysCodeInfo(item.getCustomerid(), "canceltype");
                        if (sysCode != null) {
                            item.setCustomername(sysCode.getCodename());
                        }
                    }

                } else {
                    queryMap = new HashMap();
                    if(item.getCustomerid().contains(",")){//客户群名称存在多个
                        String[] cids = item.getCustomerid().split(",");
                        String customername = "";
                        for(int i = 0 ; i<cids.length ;i++){
                            map.put("id",cids[i]);
                            Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(map);
                            if (customer != null) {
                                if(customername == ""){
                                    customername =  customer.getName() ;
                                }else{
                                    customername += "," + customer.getName() ;
                                }
                            }
                        }
                        item.setCustomername(customername.toString());
                    }else{
                        map.put("id", item.getCustomerid());

                        Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(map);
                        if (customer != null) {
                            item.setCustomername(customer.getName());
                        }
                    }
                }

            }
        }
        return list;
    }

    @Override
    public boolean updateOrderPrinttimes(Offprice offprice) throws Exception{
        return getSalesOffpriceMapper().updateOrderPrinttimes(offprice)>0;
    }
    @Override
    public void updateOrderPrinttimes(List<Offprice> list) throws Exception{
        if(null!=list){
            for(Offprice item : list){
                getSalesOffpriceMapper().updateOrderPrinttimes(item);
            }
        }
    }

    @Override
    public List<OffpriceExcel> getOffpriceExcelList(PageMap pageMap) throws Exception {
        String dataSql = getDataAccessRule("t_sales_offprice", null);
        pageMap.setDataSql(dataSql);
        List<OffpriceExcel> list = getSalesOffpriceMapper().getOffpriceExcelList(pageMap);
        if(list.size() != 0){
            for(OffpriceExcel offpriceExcel : list){
                SysCode sysCode2 = getBaseSysCodeMapper().getSysCodeInfo(offpriceExcel.getCustomertype(),"customertype");
                if(null != sysCode2){
                    offpriceExcel.setCustomertypename(sysCode2.getCodename());
                }
                MeteringUnit meteringUnit = getMeteringUnitById(offpriceExcel.getMainunit());
                if(null != meteringUnit){
                    offpriceExcel.setMainunitname(meteringUnit.getName());
                }
                String numextent = "";
                BigDecimal lownum = offpriceExcel.getLownum();
                BigDecimal upnum = offpriceExcel.getUpnum();
                numextent += lownum + "-";
                if(upnum.compareTo(BigDecimal.ZERO) != 0){
                    numextent += upnum;
                }
                else{
                    numextent += "";
                }
                offpriceExcel.setNumextent(CommonUtils.strDigitNumDeal(numextent));
                if ("2".equals(offpriceExcel.getCustomertype())) {
                    if(offpriceExcel.getCustomerid().contains(",")){//客户群名称存在多个
                        String[] cids = offpriceExcel.getCustomerid().split(",");
                        String customername = "";
                        for(int i = 0 ; i<cids.length ;i++){
                            SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(cids[i], "promotionsort");
                            if (sysCode != null) {
                                if(customername == ""){
                                    customername =  sysCode.getCodename();
                                }else{
                                    customername += "," + sysCode.getCodename() ;
                                }
                            }
                        }
                        offpriceExcel.setCustomername(customername);
                    }else{
                        SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(offpriceExcel.getCustomerid(), "promotionsort");
                        if (sysCode != null) {
                            offpriceExcel.setCustomername(sysCode.getCodename());
                        }
                    }
                } else if ("3".equals(offpriceExcel.getCustomertype())) {
                    CustomerSortMapper customerSortMapper = (CustomerSortMapper) SpringContextUtils.getBean("customerSortMapper");
                    Map map = new HashMap();
                    if(offpriceExcel.getCustomerid().contains(",")){//客户群名称存在多个
                        String[] cids = offpriceExcel.getCustomerid().split(",");
                        String customername = "";
                        for(int i = 0 ; i<cids.length ;i++){
                            map.put("id",cids[i]);
                            CustomerSort customerSort = customerSortMapper.getCustomerSortDetail(map);
                            if (customerSort != null) {
                                if(customername == ""){
                                    customername =  customerSort.getName() ;
                                }else{
                                    customername += "," + customerSort.getName();
                                }
                            }
                        }
                        offpriceExcel.setCustomername(customername.toString());
                    }else{
                        map.put("id", offpriceExcel.getCustomerid());

                        CustomerSort customerSort = customerSortMapper.getCustomerSortDetail(map);
                        if (customerSort != null) {
                            offpriceExcel.setCustomerid(customerSort.getId());
                            offpriceExcel.setCustomername(customerSort.getName());
                        }
                    }
                } else if ("4".equals(offpriceExcel.getCustomertype())) {
                    if(offpriceExcel.getCustomerid().contains(",")){//客户群名称存在多个
                        String[] cids = offpriceExcel.getCustomerid().split(",");
                        String customername = "";
                        for(int i = 0 ; i<cids.length ;i++){
                            SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(cids[i], "price_list");
                            if (sysCode != null) {
                                if(customername == ""){
                                    customername =  sysCode.getCodename();
                                }else{
                                    customername += "," + sysCode.getCodename() ;
                                }
                            }
                        }
                        offpriceExcel.setCustomername(customername);
                    }else{
                        SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(offpriceExcel.getCustomerid(), "price_list");
                        if (sysCode != null) {
                            offpriceExcel.setCustomername(sysCode.getCodename());
                        }
                    }
                } else if ("5".equals(offpriceExcel.getCustomertype())) {
                    Map map = new HashMap();
                    map.put("id", offpriceExcel.getCustomerid());
                    if(offpriceExcel.getCustomerid().contains(",")){//客户群名称存在多个
                        String[] cids = offpriceExcel.getCustomerid().split(",");
                        String customername = "";
                        for(int i = 0 ; i<cids.length ;i++){
                            map.put("id",cids[i]);
                            SalesArea salesArea = getBaseFilesSalesAreaMapper().getSalesAreaDetail(map);
                            if (salesArea != null) {
                                if(customername == ""){
                                    customername =  salesArea.getName() ;
                                }else{
                                    customername += "," + salesArea.getName() ;
                                }
                            }
                        }
                        offpriceExcel.setCustomername(customername.toString());
                    }else{
                        map.put("id", offpriceExcel.getCustomerid());

                        SalesArea salesArea = getBaseFilesSalesAreaMapper().getSalesAreaDetail(map);
                        if (salesArea != null) {
                            offpriceExcel.setCustomerid(salesArea.getId());
                            offpriceExcel.setCustomername(salesArea.getName());
                        }
                    }
                } else if ("7".equals(offpriceExcel.getCustomertype())) {
                    if(offpriceExcel.getCustomerid().contains(",")){//客户群名称存在多个
                        String[] cids = offpriceExcel.getCustomerid().split(",");
                        String customername = "";
                        for(int i = 0 ; i<cids.length ;i++){
                            SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(cids[i], "creditrating");
                            if (sysCode != null) {
                                if(customername == ""){
                                    customername =  sysCode.getCodename();
                                }else{
                                    customername += "," + sysCode.getCodename() ;
                                }
                            }
                        }
                        offpriceExcel.setCustomername(customername);
                    }else{
                        SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(offpriceExcel.getCustomerid(), "creditrating");
                        if (sysCode != null) {
                            offpriceExcel.setCustomername(sysCode.getCodename());
                        }
                    }
                } else if ("8".equals(offpriceExcel.getCustomertype())) {
                    if(offpriceExcel.getCustomerid().contains(",")){//客户群名称存在多个
                        String[] cids = offpriceExcel.getCustomerid().split(",");
                        String customername = "";
                        for(int i = 0 ; i<cids.length ;i++){
                            SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(cids[i], "canceltype");
                            if (sysCode != null) {
                                if(customername == ""){
                                    customername =  sysCode.getCodename();
                                }else{
                                    customername += "," + sysCode.getCodename() ;
                                }
                            }
                        }
                        offpriceExcel.setCustomername(customername);
                    }else{
                        SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(offpriceExcel.getCustomerid(), "canceltype");
                        if (sysCode != null) {
                            offpriceExcel.setCustomername(sysCode.getCodename());
                        }
                    }

                } else {
                    Map map = new HashMap();
                    if(offpriceExcel.getCustomerid().contains(",")){//客户群名称存在多个
                        String[] cids = offpriceExcel.getCustomerid().split(",");
                        String customername = "";
                        for(int i = 0 ; i<cids.length ;i++){
                            map.put("id",cids[i]);
                            Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(map);
                            if (customer != null) {
                                if(customername == ""){
                                    customername =  customer.getName() ;
                                }else{
                                    customername += "," + customer.getName() ;
                                }
                            }
                        }
                        offpriceExcel.setCustomername(customername.toString());
                    }else{
                        map.put("id", offpriceExcel.getCustomerid());

                        Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(map);
                        if (customer != null) {
                            offpriceExcel.setCustomername(customer.getName());
                        }
                    }
                }
            }
        }
        return list;
    }

    @Override
    public Map addOffpriceExcel(List<OffpriceExcel> list) throws Exception {
        Map returnMap = new HashMap();
        Map customerMap = new HashMap();
        for(OffpriceExcel offpriceExcel : list){
            String uniquekey = offpriceExcel.getCustomertypename() + "_" + offpriceExcel.getCustomername() + "_" + offpriceExcel.getBegindate() + "_" + offpriceExcel.getEnddate();
            if(customerMap.containsKey(uniquekey)){
                List detailList = (List) customerMap.get(uniquekey);
                detailList.add(offpriceExcel);
                customerMap.put(uniquekey, detailList);
            }else{
                List detailList = new ArrayList();
                detailList.add(offpriceExcel);
                customerMap.put(uniquekey, detailList);
            }
        }
        Set set = customerMap.entrySet();
        Iterator it = set.iterator();
        boolean flag = false;
        int successNum = 0,requirednullnumsum = 0;
        String repeatVal = "",msg = "",backorderids = null,customeridmsg = "",goodsidmsg = "",spellmsg = "",barcodemsg = "",disablegoodsidsmsg = "";//backorderids返回导入成功的特价促销编码
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            String uniquekey = entry.getKey();
            List<OffpriceExcel> detailList = (List<OffpriceExcel>) customerMap.get(uniquekey);
            Map orderMap = changeOrderByDetailList(uniquekey,detailList);
            Offprice offprice = (Offprice) orderMap.get("offprice");
            String goodsid = (String) orderMap.get("goodsid");
            String spells = (String) orderMap.get("spells");
            String barcodes = (String) orderMap.get("barcodes");
            String disablegoodsids = (String) orderMap.get("disablegoodsids");
            String customernames = (String) orderMap.get("customernames");
            Integer requirednullnum = (Integer)orderMap.get("requirednullnum");
            requirednullnumsum += requirednullnum;
            boolean addflag = false;
            if (null != offprice) {
                if (isAutoCreate("t_sales_offprice")) {
                    // 获取自动编号
                    String id = getAutoCreateSysNumbderForeign(offprice, "t_sales_offprice");
                    offprice.setId(id);
                }
                addflag = addOffprice(offprice);
            }else{
                if(StringUtils.isNotEmpty(customernames)){
                    if (StringUtils.isEmpty(customeridmsg)) {
                        customeridmsg = customernames;
                    } else {
                        customeridmsg += "," + customernames;
                    }
                }
            }
            if (addflag) {
                successNum += offprice.getDetailList().size();
                if (StringUtils.isEmpty(backorderids)) {
                    backorderids = offprice.getId();
                } else {
                    backorderids += "," + offprice.getId();
                }
                if (null != goodsid && !"".equals(goodsid)) {
                    if ("".equals(goodsidmsg)) {
                        goodsidmsg = goodsid;
                    } else {
                        goodsidmsg += goodsid;
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
                if(StringUtils.isNotEmpty(disablegoodsids)){
                    if("".equals(disablegoodsidsmsg)){
                        disablegoodsidsmsg = disablegoodsids;
                    }else{
                        disablegoodsidsmsg += disablegoodsids;
                    }
                }
                flag = true;
            } else {
                flag = false;
            }
        }
        int failureNum = list.size() - successNum;
        returnMap.put("flag", flag);
        returnMap.put("success", successNum);
        returnMap.put("failure", failureNum);
        returnMap.put("repeatNum", 0);
        returnMap.put("closeNum", 0);
        returnMap.put("goodsidmsg", goodsidmsg);
        returnMap.put("spellmsg", spellmsg);
        returnMap.put("barcodemsg", barcodemsg);
        returnMap.put("disablegoodsidsmsg", disablegoodsidsmsg);
        returnMap.put("msg", msg);
        returnMap.put("backorderids", backorderids);
        returnMap.put("requirednullnum", requirednullnumsum);
        returnMap.put("customeridmsg", customeridmsg);
        return returnMap;
    }

    @Override
    public PageData getGoodsByBrandAndSort(PageMap pageMap) throws Exception {

        String sql = getDataAccessRule("t_base_goods_info", null); //数据权限
        pageMap.setDataSql(sql);

        List<GoodsInfo> GoodsInfoList = getBaseFilesGoodsMapper().getGoodsInfoListByBrandAndSort(pageMap);
        List<OffpriceDetail> list=new ArrayList();
        for(GoodsInfo goodsInfo : GoodsInfoList){
            OffpriceDetail detail = new OffpriceDetail();

            goodsInfo = getAllGoodsInfoByID(goodsInfo.getId());
            detail.setGoodsInfo(goodsInfo);
            detail.setGoodsid(goodsInfo.getId());
            detail.setOldprice(goodsInfo.getBasesaleprice());
            list.add(detail);
        }
        int count = getBaseFilesGoodsMapper().getGoodsInfoListByBrandAndSortCount(pageMap);
        return new PageData(count,list,pageMap);
    }

    /**
     * 根据唯一关键字符串和导入的明细数据 转换成特价促销单据
     * @param list
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-10-07
     */
    private Map changeOrderByDetailList(String uniquekey,List<OffpriceExcel> list)throws Exception{
        Map returnMap = new HashMap();
        int requirednullnum = 0;
        String goodsids = "",spells ="",barcodes = "",disablegoodsids = "";
        String[] keyArr = uniquekey.split("_");
        String customertypename = keyArr[0];
        String customernames = keyArr[1];
        String begindate = keyArr[2];
        String enddate = keyArr[3];
        String businessdate = null != list.get(0).getBusinessdate() ? list.get(0).getBusinessdate() : "";

        if(!"null".equals(customertypename) && !"null".equals(customernames) && !"null".equals(begindate) && !"null".equals(enddate)){
            Offprice offprice =  null;
            String customertype = getBaseSysCodeMapper().codenametocode(customertypename,"customertype");
            String customerid = "";
            String[] cnames = customernames.split(",");
            if ("2".equals(customertype)) {
                for(int i = 0 ; i<cnames.length ;i++){
                    if(!"null".equals(cnames[i])){
                        String promotionsort = getBaseSysCodeMapper().codenametocode(cnames[i], "promotionsort");
                        if (StringUtils.isNotEmpty(promotionsort)) {
                            if(customerid == ""){
                                customerid =  promotionsort;
                            }else{
                                customerid += "," + promotionsort;
                            }
                        }
                    }
                }
            } else if ("3".equals(customertype)) {
                CustomerSortMapper customerSortMapper = (CustomerSortMapper) SpringContextUtils.getBean("customerSortMapper");
                for(int i = 0 ; i<cnames.length ;i++){
                    if(!"null".equals(cnames[i])){
                        CustomerSort customerSort = getBaseCustomerSortMapper().returnCustomerSortIdByName(cnames[i]);
                        if (customerSort != null) {
                            if(customerid == ""){
                                customerid =  customerSort.getId() ;
                            }else{
                                customerid += "," + customerSort.getId();
                            }
                        }
                    }
                }
            } else if ("4".equals(customertype)) {
                for(int i = 0 ; i<cnames.length ;i++){
                    if(!"null".equals(cnames[i])){
                        String price_list = getBaseSysCodeMapper().codenametocode(cnames[i], "price_list");
                        if (StringUtils.isNotEmpty(price_list)) {
                            if(customerid == ""){
                                customerid =  price_list;
                            }else{
                                customerid += "," + price_list ;
                            }
                        }
                    }
                }
            } else if ("5".equals(customertype)) {
                for(int i = 0 ; i<cnames.length ;i++){
                    if(!"null".equals(cnames[i])){
                        List<SalesArea> areaList = getBaseFilesSalesAreaMapper().returnSalesAreaIdByName(cnames[i]);
                        if (areaList != null && areaList.size() != 0) {
                            if(customerid == ""){
                                customerid =  areaList.get(0).getId() ;
                            }else{
                                customerid += "," + areaList.get(0).getName() ;
                            }
                        }
                    }
                }
            } else if ("7".equals(customertype)) {
                for(int i = 0 ; i<cnames.length ;i++){
                    if(!"null".equals(cnames[i])){
                        String creditrating = getBaseSysCodeMapper().codenametocode(cnames[i], "creditrating");
                        if (StringUtils.isNotEmpty(creditrating)) {
                            if(customerid == ""){
                                customerid =  creditrating;
                            }else{
                                customerid += "," + creditrating;
                            }
                        }
                    }
                }
            } else if ("8".equals(customertype)) {
                for(int i = 0 ; i<cnames.length ;i++){
                    if(!"null".equals(cnames[i])){
                        String canceltype = getBaseSysCodeMapper().codenametocode(cnames[i], "canceltype");
                        if (StringUtils.isNotEmpty(canceltype)) {
                            if(customerid == ""){
                                customerid =  canceltype;
                            }else{
                                customerid += "," + canceltype;
                            }
                        }
                    }
                }
            }else{
                for(int i = 0 ; i<cnames.length ;i++){
                    if(!"null".equals(cnames[i])){
                        Customer customer = getCustomerByName(cnames[i]);
                        if (customer != null) {
                            if(customerid == ""){
                                customerid =  customer.getId() ;
                            }else{
                                customerid += "," + customer.getId() ;
                            }
                        }
                    }
                }
            }
            if(StringUtils.isNotEmpty(customerid)){
                offprice = new Offprice();
                offprice.setStatus("2");
                offprice.setCustomertype(customertype);
                offprice.setCustomerid(customerid);
                offprice.setBegindate(begindate);
                offprice.setEnddate(enddate);
                if(StringUtils.isNotEmpty(businessdate)){
                    offprice.setBusinessdate(businessdate);
                }else{
                    offprice.setBusinessdate(CommonUtils.getTodayDataStr());
                }
                SysUser sysUser = getSysUser();
                offprice.setAdddeptid(sysUser.getDepartmentid());
                offprice.setAdddeptname(sysUser.getDepartmentname());
                offprice.setAdduserid(sysUser.getUserid());
                offprice.setAddusername(sysUser.getName());
                int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
                //生成特价促销信息
                List detailList = new ArrayList();
                for(OffpriceExcel offpriceExcel2 : list){
                    OffpriceDetail offpriceDetail = null;
                    offpriceDetail = new OffpriceDetail();
                    offpriceDetail.setRemark(offpriceExcel2.getRemark());
                    GoodsInfo goodsInfo = null;
                    //根据商品编码、商品助记符获取商品信息
                    if (StringUtils.isNotEmpty(offpriceExcel2.getGoodsid())) {
                        goodsInfo = getAllGoodsInfoByID(offpriceExcel2.getGoodsid());
                        if (null == goodsInfo) {
                            goodsids += offpriceExcel2.getGoodsid() + ",";
                        }else if(!"1".equals(goodsInfo.getState())){
                            disablegoodsids += offpriceExcel2.getGoodsid() +",";
                        }
                    }else if (StringUtils.isNotEmpty(offpriceExcel2.getSpell())) {
                        goodsInfo = getGoodsInfoBySpell(offpriceExcel2.getSpell());
                        if (null == goodsInfo) {
                            spells += offpriceExcel2.getSpell() + ",";
                        }else if(!"1".equals(goodsInfo.getState())){
                            disablegoodsids += offpriceExcel2.getSpell() +",";
                        }
                    } else if (StringUtils.isNotEmpty(offpriceExcel2.getBarcode())) {
                        goodsInfo = getGoodsInfoByBarcode(offpriceExcel2.getBarcode());
                        if (null == goodsInfo) {
                            barcodes += offpriceExcel2.getBarcode() + ",";
                        }else if(!"1".equals(goodsInfo.getState())){
                            disablegoodsids += offpriceExcel2.getBarcode() +",";
                        }
                    }

                    if(null!=goodsInfo && "1".equals(goodsInfo.getState())){
                        offpriceDetail.setGoodsid(goodsInfo.getId());
                        offpriceDetail.setOffprice(offpriceExcel2.getOffprice());
                        offpriceDetail.setOldprice(goodsInfo.getBasesaleprice());

                        String numextent = null != offpriceExcel2.getNumextent() ? offpriceExcel2.getNumextent() : "";
                        if (StringUtils.isNotEmpty(numextent)){
                            String[] extent = numextent.split("-");
                            BigDecimal lownum = new BigDecimal(extent[0]);
                            if(decimalScale == 0){
                                lownum = lownum.setScale(decimalScale,BigDecimal.ROUND_DOWN);
                            }else{
                                lownum = lownum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
                            }
                            offpriceDetail.setLownum(lownum);
                            if(extent.length == 2){
                                BigDecimal upnum = new BigDecimal(extent[1]);
                                if(decimalScale == 0){
                                    upnum = upnum.setScale(decimalScale,BigDecimal.ROUND_DOWN);
                                }else{
                                    upnum = upnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
                                }
                                offpriceDetail.setUpnum(upnum);
                            }
                        }
                        detailList.add(offpriceDetail);
                    }
                }
                offprice.setDetailList(detailList);
                returnMap.put("offprice", offprice);
                returnMap.put("goodsid", goodsids);
                returnMap.put("spells", spells);
                returnMap.put("disablegoodsids", disablegoodsids);
                returnMap.put("barcodes", barcodes);
                returnMap.put("customernames",customernames);
            }else{
                returnMap.put("customernames",customernames);
                returnMap.put("offprice", null);
                for (OffpriceExcel offpriceExcel : list) {
                    if (StringUtils.isNotEmpty(offpriceExcel.getGoodsid())) {
                        goodsids += offpriceExcel.getGoodsid() + ",";
                    } else if (StringUtils.isNotEmpty(offpriceExcel.getSpell())) {
                        spells += offpriceExcel.getSpell() + ",";
                    } else if (StringUtils.isNotEmpty(offpriceExcel.getBarcode())) {
                        barcodes += offpriceExcel.getBarcode() + ",";
                    }
                }
                returnMap.put("goodsid", goodsids);
                returnMap.put("spells", spells);
                returnMap.put("barcodes", barcodes);
            }
        }else{
            requirednullnum = list.size();
        }
        returnMap.put("requirednullnum", requirednullnum);
        return returnMap;
    }

    public Map offpriceCancel(String ids,String operate) throws Exception{
        Map returnMap = new HashMap();
        boolean flag = false;
        int failure = 0;
        int success = 0;
        if (StringUtils.isNotEmpty(ids)) {
            String[] idArr = ids.split(",");
            for (String id : idArr) {
                Offprice offprice = getSalesOffpriceMapper().getOffprice(id);
                if(null != offprice){
                    if("1".equals(operate)){
                        offprice.setStatus("5");//单据作废时状态为中止
                    }else if("2".equals(operate)){
                        offprice.setStatus("2");//单据取消作废时状态为保存
                    }
                    boolean delflag = getSalesOffpriceMapper().updateOffpriceStatus(offprice) > 0;
                    if (delflag) {
                        success++;
                    } else {
                        failure++;
                    }
                }else{
                    failure++;
                }
            }
            flag = true ;
        }
        returnMap.put("flag", flag);
        returnMap.put("failure", failure);
        returnMap.put("success", success);
        return returnMap;
    }

}

