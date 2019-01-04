/**
 * @(#)PurchaseInvoiceServiceImpl.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 11, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.account.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hd.agent.account.model.*;
import com.hd.agent.account.service.IBeginDueService;
import com.hd.agent.common.action.ExcelAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.PurchaseInvoiceMapper;
import com.hd.agent.account.dao.PurchaseInvoicePushMapper;
import com.hd.agent.account.dao.SupplierCapitalMapper;
import com.hd.agent.account.service.IPurchaseInvoiceService;
import com.hd.agent.basefiles.model.Brand;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.Contacter;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.model.TaxType;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.purchase.model.ArrivalOrder;
import com.hd.agent.purchase.model.ArrivalOrderDetail;
import com.hd.agent.purchase.model.ReturnOrder;
import com.hd.agent.purchase.model.ReturnOrderDetail;
import com.hd.agent.purchase.service.IArrivalOrderService;
import com.hd.agent.purchase.service.IReturnOrderService;
import com.hd.agent.purchase.service.ext.IPurchaseForAccountService;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.model.SysParam;

/**
 *
 *
 * @author panxiaoxiao
 */
public class PurchaseInvoiceServiceImpl extends BaseAccountServiceImpl implements IPurchaseInvoiceService{

    private PurchaseInvoiceMapper purchaseInvoiceMapper;

    private  IPurchaseForAccountService purchaseForAccountService;

    private IArrivalOrderService arrivalOrderService;

    private IReturnOrderService returnOrderService;

    private IBeginDueService beginDueService;
    /**
     * 采购发票冲差
     */
    private PurchaseInvoicePushMapper purchaseInvoicePushMapper;

    private SupplierCapitalMapper supplierCapitalMapper ;

    public SupplierCapitalMapper getSupplierCapitalMapper() {
        return supplierCapitalMapper;
    }

    public void setSupplierCapitalMapper(SupplierCapitalMapper supplierCapitalMapper) {
        this.supplierCapitalMapper = supplierCapitalMapper;
    }

    public IReturnOrderService getReturnOrderService() {
        return returnOrderService;
    }

    public void setReturnOrderService(IReturnOrderService returnOrderService) {
        this.returnOrderService = returnOrderService;
    }

    public IArrivalOrderService getArrivalOrderService() {
        return arrivalOrderService;
    }

    public void setArrivalOrderService(IArrivalOrderService arrivalOrderService) {
        this.arrivalOrderService = arrivalOrderService;
    }

    public IPurchaseForAccountService getPurchaseForAccountService() {
        return purchaseForAccountService;
    }

    public void setPurchaseForAccountService(
            IPurchaseForAccountService purchaseForAccountService) {
        this.purchaseForAccountService = purchaseForAccountService;
    }

    public PurchaseInvoiceMapper getPurchaseInvoiceMapper() {
        return purchaseInvoiceMapper;
    }

    public void setPurchaseInvoiceMapper(PurchaseInvoiceMapper purchaseInvoiceMapper) {
        this.purchaseInvoiceMapper = purchaseInvoiceMapper;
    }

    public PurchaseInvoicePushMapper getPurchaseInvoicePushMapper() {
        return purchaseInvoicePushMapper;
    }

    public void setPurchaseInvoicePushMapper(
            PurchaseInvoicePushMapper purchaseInvoicePushMapper) {
        this.purchaseInvoicePushMapper = purchaseInvoicePushMapper;
    }

    public IBeginDueService getBeginDueService() {
        return beginDueService;
    }

    public void setBeginDueService(IBeginDueService beginDueService) {
        this.beginDueService = beginDueService;
    }

    @Override
    public PageData showPurchaseInvoiceList(PageMap pageMap) throws Exception {
        String cols = getAccessColumnList("t_account_purchase_invoice",null);
        pageMap.setCols(cols);
        String dataSql = getDataAccessRule("t_account_purchase_invoice", null);
        pageMap.setDataSql(dataSql);
        List<PurchaseInvoice> list = purchaseInvoiceMapper.getPurchaseInvoiceListPage(pageMap);
        if(list.size() != 0){
            for(PurchaseInvoice purchaseInvoice : list){
                BuySupplier buySupplier = getSupplierInfoById(purchaseInvoice.getSupplierid());
                if(buySupplier != null){
                    purchaseInvoice.setSuppliername(buySupplier.getName());
                }
                Contacter contacter = getContacterById(purchaseInvoice.getHandlerid());
                if(null!=contacter){
                    purchaseInvoice.setHandlername(contacter.getName());
                }
                DepartMent departMent = getDepartmentByDeptid(purchaseInvoice.getBuydept());
                if(null!=departMent){
                    purchaseInvoice.setBuydeptname(departMent.getName());
                }
                Personnel personnel = getPersonnelById(purchaseInvoice.getBuyuser());
                if(null!=personnel){
                    purchaseInvoice.setBuyusername(personnel.getName());
                }
            }
        }
        PageData pageData = new PageData(purchaseInvoiceMapper.getPurchaseInvoiceListCount(pageMap),list,pageMap);

        //合计
        PurchaseInvoice purchaseInvoice = purchaseInvoiceMapper.getPurchaseInvoiceDataSum(pageMap);
        List footer = new ArrayList();
        if(null!=purchaseInvoice){
            purchaseInvoice.setId("合计");
            footer.add(purchaseInvoice);
        }
        pageData.setFooter(footer);
        return pageData;
    }


    @Override
    public Map addPurchaseInvoice(String ids)throws Exception {
        boolean flag = false;
        String purchaseInvoiceid = "";
        String msg = "",sysParamHoldStatusStr="0";
        //根据系统参数判断是否启用“暂存”状态,1是，0否
        SysParam sysParamHoldStatus = getBaseSysParamMapper().getSysParam("IsPurchaseInvoiceHoldStatus");
        if(null != sysParamHoldStatus){
            sysParamHoldStatusStr = sysParamHoldStatus.getPvalue();
        }
        if(StringUtils.isNotEmpty(ids)){
            JSONArray idArr = JSONArray.fromObject(ids);
            String supplierid = null;
            boolean addFlag = true;
            String sourceids = null;
            //用来存放来源单据编号
            Map sourceidMap = new HashMap();
            //验证多个回单是否能组成一张销售发票
            for(int i=0;i<idArr.size();i++){
                JSONObject detailObject = (JSONObject) idArr.get(i);
                //单据类型 1回单2销售退货通知单
                String billtype = detailObject.getString("billtype");
                //单据编号
                String id = detailObject.getString("billid");
                //明细编号
                String detailid = detailObject.getString("detailid");
                if(!sourceidMap.containsKey(id)){
                    //来源单据编号
                    if(sourceids==null){
                        sourceids = id;
                    }else{
                        sourceids += ","+id;
                    }
                    sourceidMap.put(id, billtype);
                }

                if("1".equals(billtype)){
                    ArrivalOrder arrivalOrder = purchaseForAccountService.showArrivalOrder(id);
                    //判读是否同个供应商
                    if(null == supplierid || supplierid.equals(arrivalOrder.getSupplierid())){
                        if(null == supplierid){
                            supplierid = arrivalOrder.getSupplierid();
                        }
                    }
                    else{
                        addFlag = false;
                        msg = "不为同个供应商,";
                        break;
                    }
                }else if("2".equals(billtype)){
                    ReturnOrder returnOrder = purchaseForAccountService.showReturnOrder(id);
                    //判读是否同个供应商
                    if(null == supplierid || supplierid.equals(returnOrder.getSupplierid())){
                        if(null == supplierid){
                            supplierid = returnOrder.getSupplierid();
                        }
                    }
                    else{
                        addFlag = false;
                        msg = "不为同个供应商,";
                        break;
                    }
                }else if ("3".equals(billtype)){
                    BeginDue beginDue = beginDueService.getBeginDueInfo(id);
                    //判读是否同个供应商
                    if(null == supplierid || supplierid.equals(beginDue.getSupplierid())){
                        if(null == supplierid){
                            supplierid = beginDue.getSupplierid();
                        }
                    }
                    else{
                        addFlag = false;
                        msg = "不为同个供应商,";
                        break;
                    }
                }
            }
            //判断是否可以生成采购发票
            if(addFlag){
                PurchaseInvoice purchaseInvoice = new PurchaseInvoice();
                if(isAutoCreate("t_account_purchase_invoice")){
                    //获取自动编号
                    String id = getAutoCreateSysNumbderForeign(purchaseInvoice, "t_account_purchase_invoice");
                    purchaseInvoice.setId(id);
                }
                else{
                    purchaseInvoice.setId("CGFP-"+CommonUtils.getDataNumberSendsWithRand());
                }
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                purchaseInvoice.setBusinessdate(dateFormat.format(calendar.getTime()));
                if("1".equals(sysParamHoldStatusStr)){
                    purchaseInvoice.setStatus("1");
                }else {
                    purchaseInvoice.setStatus("2");
                }
                //是否折扣 0否
                purchaseInvoice.setIsdiscount("0");
                //发票类型 1增值税
                purchaseInvoice.setInvoicetype("1");
                //是否核销 0否
                purchaseInvoice.setIswriteoff("0");

                BuySupplier buySupplier = getSupplierInfoById(supplierid);
                if(buySupplier != null){
                    purchaseInvoice.setSupplierid(buySupplier.getId());
                    purchaseInvoice.setHandlerid(buySupplier.getContact());
                }
                purchaseInvoice.setBuydept(buySupplier.getBuydeptid());
                purchaseInvoice.setBuyuser(buySupplier.getBuyuserid());
                purchaseInvoice.setSettletype(buySupplier.getSettletype());
                purchaseInvoice.setPaytype(buySupplier.getPaytype());

                List<PurchaseInvoiceDetail> purchaseInvoiceDetailList = new ArrayList<PurchaseInvoiceDetail>();

                for(int i=0;i<idArr.size();i++){
                    JSONObject detailObject = (JSONObject) idArr.get(i);
                    //单据类型 1进货单2采购退货通知单3应付款期初
                    String billtype = detailObject.getString("billtype");
                    //单据编号
                    String id = detailObject.getString("billid");
                    //明细编号
                    String detailid = detailObject.getString("detailid");
                    PurchaseInvoiceDetail purchaseInvoiceDetail = null;

                    if("1".equals(billtype)){
                        ArrivalOrder arrivalOrder = purchaseForAccountService.showArrivalOrder(id);
                        if(null!=arrivalOrder){
                            ArrivalOrderDetail arrivalOrderDetail = purchaseForAccountService.showArrivalOrderDetail(detailid);
                            if(null!=arrivalOrderDetail){
                                if("0".equals(arrivalOrderDetail.getInvoicerefer())){
                                    purchaseInvoiceDetail = new PurchaseInvoiceDetail();
                                    purchaseInvoiceDetail.setBillid(purchaseInvoice.getId());
                                    purchaseInvoiceDetail.setSourcetype("1");
                                    purchaseInvoiceDetail.setSourceid(arrivalOrder.getId());
                                    purchaseInvoiceDetail.setSourcedetailid(arrivalOrderDetail.getId().toString());
                                    purchaseInvoiceDetail.setGoodsid(arrivalOrderDetail.getGoodsid());
                                    //应付日期paydate
                                    purchaseInvoiceDetail.setUnitid(arrivalOrderDetail.getUnitid());
                                    purchaseInvoiceDetail.setUnitname(arrivalOrderDetail.getUnitname());
                                    if(null != arrivalOrderDetail.getUninvoicenum() && arrivalOrderDetail.getUninvoicenum().compareTo(new BigDecimal(0)) != 0){
                                        purchaseInvoiceDetail.setUnitnum(arrivalOrderDetail.getUninvoicenum());
                                    }else{
                                        purchaseInvoiceDetail.setUnitnum(arrivalOrderDetail.getUnitnum());
                                    }
                                    purchaseInvoiceDetail.setAuxunitid(arrivalOrderDetail.getAuxunitid());
                                    purchaseInvoiceDetail.setAuxunitname(arrivalOrderDetail.getAuxunitname());
                                    //通过商品编码、主单位数量、辅单位编码计算得到辅单位数量,辅单位数量描述信息
//									if(null != arrivalOrderDetail.getUninvoicenum() && arrivalOrderDetail.getUninvoicenum().compareTo(new BigDecimal(0)) != 0){
//										Map auxMap = countGoodsInfoNumber(arrivalOrderDetail.getGoodsid(), 
//																		arrivalOrderDetail.getAuxunitid(), 
//																		arrivalOrderDetail.getUninvoicenum());
//										purchaseInvoiceDetail.setAuxnum((BigDecimal) auxMap.get("auxnum"));
//										purchaseInvoiceDetail.setAuxnumdetail( (String) auxMap.get("auxnumdetail"));
//									}else{
                                    Map auxMap = countGoodsInfoNumber(arrivalOrderDetail.getGoodsid(),
                                            arrivalOrderDetail.getAuxunitid(),
                                            purchaseInvoiceDetail.getUnitnum());
                                    purchaseInvoiceDetail.setAuxnum((BigDecimal) auxMap.get("auxnum"));
                                    purchaseInvoiceDetail.setAuxnumdetail( (String) auxMap.get("auxnumdetail"));
                                    //}

                                    purchaseInvoiceDetail.setTaxprice(arrivalOrderDetail.getTaxprice());
                                    if(null != arrivalOrderDetail.getUninvoicetaxamount() && arrivalOrderDetail.getUninvoicetaxamount().compareTo(new BigDecimal(0)) != 0){
                                        purchaseInvoiceDetail.setTaxamount(arrivalOrderDetail.getUninvoicetaxamount());
                                    }else{
                                        purchaseInvoiceDetail.setTaxamount(arrivalOrderDetail.getTaxamount());
                                    }
                                    purchaseInvoiceDetail.setNotaxprice(arrivalOrderDetail.getNotaxprice());
                                    if(null != arrivalOrderDetail.getUninvoicenotaxamount() && arrivalOrderDetail.getUninvoicenotaxamount().compareTo(new BigDecimal(0)) != 0){
                                        purchaseInvoiceDetail.setNotaxamount(arrivalOrderDetail.getUninvoicenotaxamount());
                                    }else{
                                        purchaseInvoiceDetail.setNotaxamount(arrivalOrderDetail.getNotaxamount());
                                    }
                                    purchaseInvoiceDetail.setTaxtype(arrivalOrderDetail.getTaxtype());
                                    purchaseInvoiceDetail.setTax(purchaseInvoiceDetail.getTaxamount().subtract(purchaseInvoiceDetail.getNotaxamount()));
                                    purchaseInvoiceDetail.setRemark(arrivalOrderDetail.getRemark());

                                    purchaseInvoiceDetailList.add(purchaseInvoiceDetail);
                                }
                            }
                        }
                    }else if("2".equals(billtype)){
                        ReturnOrder returnOrder = purchaseForAccountService.showReturnOrder(id);
                        if(null!=returnOrder){
                            ReturnOrderDetail returnOrderDetail = purchaseForAccountService.showReturnOrderDetail(detailid);
                            if(null!=returnOrderDetail){
                                if("0".equals(returnOrderDetail.getInvoicerefer())){
                                    purchaseInvoiceDetail = new PurchaseInvoiceDetail();
                                    purchaseInvoiceDetail.setBillid(purchaseInvoice.getId());
                                    purchaseInvoiceDetail.setSourcetype("2");
                                    purchaseInvoiceDetail.setSourceid(returnOrder.getId());
                                    purchaseInvoiceDetail.setSourcedetailid(returnOrderDetail.getId().toString());
                                    purchaseInvoiceDetail.setGoodsid(returnOrderDetail.getGoodsid());
                                    //应付日期paydate
                                    purchaseInvoiceDetail.setUnitid(returnOrderDetail.getUnitid());
                                    purchaseInvoiceDetail.setUnitname(returnOrderDetail.getUnitname());
                                    if(null != returnOrderDetail.getUninvoicenum() && returnOrderDetail.getUninvoicenum().compareTo(new BigDecimal(0)) != 0){
                                        purchaseInvoiceDetail.setUnitnum(returnOrderDetail.getUninvoicenum().negate());
                                    }else{
                                        purchaseInvoiceDetail.setUnitnum(returnOrderDetail.getUnitnum().negate());
                                    }
                                    purchaseInvoiceDetail.setAuxunitid(returnOrderDetail.getAuxunitid());
                                    purchaseInvoiceDetail.setAuxunitname(returnOrderDetail.getAuxunitname());
                                    //通过商品编码、主单位数量、辅单位编码计算得到辅单位数量,辅单位数量描述信息
//									if(null != returnOrderDetail.getUninvoicenum() && returnOrderDetail.getUninvoicenum().compareTo(new BigDecimal(0)) != 0){
//										Map auxMap = countGoodsInfoNumber(returnOrderDetail.getGoodsid(), 
//												returnOrderDetail.getAuxunitid(), 
//												returnOrderDetail.getUninvoicenum());
//										purchaseInvoiceDetail.setAuxnum((BigDecimal) auxMap.get("auxnum"));
//										purchaseInvoiceDetail.setAuxnumdetail( (String) auxMap.get("auxnumdetail"));
//									}else{
                                    Map auxMap = countGoodsInfoNumber(returnOrderDetail.getGoodsid(),
                                            returnOrderDetail.getAuxunitid(),
                                            purchaseInvoiceDetail.getUnitnum());
                                    purchaseInvoiceDetail.setAuxnum((BigDecimal) auxMap.get("auxnum"));
                                    purchaseInvoiceDetail.setAuxnumdetail( (String) auxMap.get("auxnumdetail"));
                                    //}

                                    purchaseInvoiceDetail.setTaxprice(returnOrderDetail.getTaxprice());
                                    if(null != returnOrderDetail.getUninvoicetaxamount() && returnOrderDetail.getUninvoicetaxamount().compareTo(new BigDecimal(0)) != 0){
                                        purchaseInvoiceDetail.setTaxamount(returnOrderDetail.getUninvoicetaxamount().negate());
                                    }else{
                                        purchaseInvoiceDetail.setTaxamount(returnOrderDetail.getTaxamount().negate());
                                    }
                                    purchaseInvoiceDetail.setNotaxprice(returnOrderDetail.getNotaxprice());
                                    if(null != returnOrderDetail.getUninvoicenotaxamount() && returnOrderDetail.getUninvoicenotaxamount().compareTo(new BigDecimal(0)) != 0){
                                        purchaseInvoiceDetail.setNotaxamount(returnOrderDetail.getUninvoicenotaxamount().negate());
                                    }else{
                                        purchaseInvoiceDetail.setNotaxamount(returnOrderDetail.getNotaxamount().negate());
                                    }
                                    purchaseInvoiceDetail.setTaxtype(returnOrderDetail.getTaxtype());
                                    purchaseInvoiceDetail.setTax(purchaseInvoiceDetail.getTaxamount().subtract(purchaseInvoiceDetail.getNotaxamount()));
                                    purchaseInvoiceDetail.setRemark(returnOrderDetail.getRemark());

                                    purchaseInvoiceDetailList.add(purchaseInvoiceDetail);
                                }
                            }
                        }
                    }else if("3".equals(billtype)){
                        BeginDue beginDue = beginDueService.getBeginDueInfo(id);
                        if(null != beginDue && "0".equals(beginDue.getIsinvoice())){
                            purchaseInvoiceDetail = new PurchaseInvoiceDetail();
                            purchaseInvoiceDetail.setBillid(purchaseInvoice.getId());
                            purchaseInvoiceDetail.setSourcetype("3");
                            purchaseInvoiceDetail.setSourceid(beginDue.getId());
                            purchaseInvoiceDetail.setSourcedetailid(beginDue.getId());
                            purchaseInvoiceDetail.setGoodsid("QC");
                            purchaseInvoiceDetail.setUnitid("");
                            purchaseInvoiceDetail.setUnitname("");
                            purchaseInvoiceDetail.setUnitnum(BigDecimal.ZERO);
                            purchaseInvoiceDetail.setAuxunitid("");
                            purchaseInvoiceDetail.setAuxunitname("");
                            purchaseInvoiceDetail.setAuxnum(BigDecimal.ZERO);
                            purchaseInvoiceDetail.setAuxnumdetail("");

                            purchaseInvoiceDetail.setTaxprice(BigDecimal.ZERO);
                            purchaseInvoiceDetail.setTaxamount(beginDue.getAmount());
                            purchaseInvoiceDetail.setNotaxprice(BigDecimal.ZERO);

                            //取系统默认税种
                            String taxtype = "";
                            SysParam taxtypeParam = getBaseSysParamMapper().getSysParam("DEFAULTAXTYPE");
                            if(null != taxtypeParam){
                                taxtype = taxtypeParam.getPvalue();
                            }
                            purchaseInvoiceDetail.setTaxtype(taxtype);
                            //重新计算税额税额
                            BigDecimal notaxamount = getNotaxAmountByTaxAmount(purchaseInvoiceDetail.getTaxamount(), purchaseInvoiceDetail.getTaxtype());
                            purchaseInvoiceDetail.setNotaxamount(notaxamount);
                            purchaseInvoiceDetail.setTax(purchaseInvoiceDetail.getTaxamount().subtract(purchaseInvoiceDetail.getNotaxamount()));
                            purchaseInvoiceDetail.setRemark(beginDue.getRemark());
                            purchaseInvoiceDetail.setSeq(999);
                            purchaseInvoiceDetailList.add(purchaseInvoiceDetail);
                        }
                    }
                }

                if(purchaseInvoiceDetailList.size() != 0){
                    purchaseInvoiceMapper.addPurchaseInvoiceDetailList(purchaseInvoiceDetailList);

                    SysUser sysUser = getSysUser();
                    purchaseInvoice.setAdddeptid(sysUser.getDepartmentid());
                    purchaseInvoice.setAdddeptname(sysUser.getDepartmentname());
                    purchaseInvoice.setAdduserid(sysUser.getUserid());
                    purchaseInvoice.setAddusername(sysUser.getName());
                    //含税总金额
                    BigDecimal alltaxamount = new BigDecimal(0);
                    //未税总金额
                    BigDecimal allnotaxamount = new BigDecimal(0);
                    //应收总金额
                    BigDecimal invoiceamount =  new BigDecimal(0);
                    for(PurchaseInvoiceDetail purchaseInvoiceDetail : purchaseInvoiceDetailList){
                        alltaxamount = alltaxamount.add(purchaseInvoiceDetail.getTaxamount());
                        allnotaxamount = allnotaxamount.add(purchaseInvoiceDetail.getNotaxamount());
                        invoiceamount = invoiceamount.add(purchaseInvoiceDetail.getTaxamount());
                    }
                    purchaseInvoice.setTaxamount(alltaxamount);
                    purchaseInvoice.setNotaxamount(allnotaxamount);
                    purchaseInvoice.setInvoiceamount(invoiceamount);
                    purchaseInvoice.setSourceid(sourceids);
                    flag = purchaseInvoiceMapper.addPurchaseInvoice(purchaseInvoice) > 0;
                    if(flag){
                        purchaseInvoiceid=purchaseInvoice.getId();
                        List<PurchaseInvoiceDetail> detailSourceTypeList=purchaseInvoiceMapper.getPurchaseInvoiceDetailSourcetypeList(purchaseInvoice.getId());
                        Map sumMap=new HashMap();
                        for(PurchaseInvoiceDetail item : detailSourceTypeList){
                            if("1".equals(item.getSourcetype())){
                                sumMap.put("sourceid", item.getSourceid());
                                sumMap.put("sourcetype", item.getSourcetype());
                                List<PurchaseInvoiceDetail> list = purchaseInvoiceMapper.getPurchaseInvoiceDetailSumListBySourceidGroup(sumMap);
                                purchaseForAccountService.updateArrivalOrderInvoiceReferWriteBack(item.getSourceid(),list);
                            }else if("2".equals(item.getSourcetype())){
                                sumMap.put("sourceid", item.getSourceid());
                                sumMap.put("sourcetype", item.getSourcetype());
                                List<PurchaseInvoiceDetail> list = purchaseInvoiceMapper.getPurchaseInvoiceDetailSumListBySourceidGroup(sumMap);
                                purchaseForAccountService.updateReturnOrderInvoiceReferWriteBack(item.getSourceid(),list);
                            }else if("3".equals(item.getSourcetype())){
                                sumMap.put("sourceid", item.getSourceid());
                                sumMap.put("sourcetype", item.getSourcetype());
                                List<PurchaseInvoiceDetail> list = purchaseInvoiceMapper.getPurchaseInvoiceDetailSumListBySourceidGroup(sumMap);
                                purchaseForAccountService.updateBeginDueInvoiceReferWriteBack(item.getSourceid(),list);
                            }
                        }
                    }
                }else{
                    msg = "已生成该来源单据明细的采购发票!";
                }
            }
        }
        Map map = new HashMap();
        map.put("flag", flag);
        map.put("id", purchaseInvoiceid);
        map.put("msg", msg);
        return map;
    }

    @Override
    public Map addPurchaseInvoiceByArrivalOrder(String ids,List<ArrivalOrderDetail> arrivalDetailList) throws Exception {
        boolean flag = false;
        String purchaseInvoiceid = "";
        String msg = "";
        if(StringUtils.isNotEmpty(ids)){
            String[] idArr = ids.split(",");
            String supplierid = null, buydept = null, buyuser = null,settletype = null,paytype = null;
            boolean addFlag = true;

            //进货的单据
            List<ArrivalOrder> arrivalList = new ArrayList<ArrivalOrder>();
            //验证多个进货单是否能组成一张采购发票
            if(idArr.length != 0){
                for(String id : idArr){
                    ArrivalOrder arrivalOrder = purchaseForAccountService.showArrivalOrder(id);
                    //判读是否同个供应商
                    if(null == supplierid || supplierid.equals(arrivalOrder.getSupplierid())){
                        if(null == supplierid){
                            supplierid = arrivalOrder.getSupplierid();
                        }
                    }
                    else{
                        addFlag = false;
                        msg = "不为同个供应商,";
                        break;
                    }
                    arrivalList.add(arrivalOrder);
                }
                //生成采购发票
                if(addFlag && arrivalList.size() > 0){
                    PurchaseInvoice purchaseInvoice = new PurchaseInvoice();
                    if(isAutoCreate("t_account_purchase_invoice")){
                        //获取自动编号
                        String id = getAutoCreateSysNumbderForeign(purchaseInvoice, "t_account_purchase_invoice");
                        purchaseInvoice.setId(id);
                    }
                    else{
                        purchaseInvoice.setId("CGFP-"+CommonUtils.getDataNumberSendsWithRand());
                    }
                    ArrivalOrder arrivalOrder = arrivalList.get(0);
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar calendar = Calendar.getInstance();
                    purchaseInvoice.setBusinessdate(dateFormat.format(calendar.getTime()));
                    purchaseInvoice.setStatus("2");
                    purchaseInvoice.setSourcetype("1");
                    //是否折扣 0否
                    purchaseInvoice.setIsdiscount("0");
                    //发票类型 1增值税
                    purchaseInvoice.setInvoicetype("1");
                    //是否核销 0否
                    purchaseInvoice.setIswriteoff("0");

                    BuySupplier buySupplier = getSupplierInfoById(arrivalOrder.getSupplierid());
                    if(buySupplier != null){
                        purchaseInvoice.setSupplierid(buySupplier.getId());
                        purchaseInvoice.setHandlerid(buySupplier.getContact());
                    }
                    purchaseInvoice.setBuydept(arrivalOrder.getBuydeptid());
                    purchaseInvoice.setBuyuser(arrivalOrder.getBuyuserid());
                    purchaseInvoice.setSettletype(arrivalOrder.getSettletype());
                    purchaseInvoice.setPaytype(arrivalOrder.getPaytype());

                    List<PurchaseInvoiceDetail> purchaseInvoiceDetailList = new ArrayList<PurchaseInvoiceDetail>();
                    String sourceid = "",invoicerefer = "";
                    List<String> dealArrailDeailIdList=new ArrayList<String>();
                    for(ArrivalOrder arrivalOrder2 : arrivalList){
                        if(StringUtils.isEmpty(sourceid)){
                            sourceid = arrivalOrder2.getId();
                        }else{
                            sourceid += ","+arrivalOrder2.getId();
                        }
                        if(arrivalDetailList.size() != 0){
                            for(ArrivalOrderDetail arrivalOrderDetail : arrivalDetailList){
                                if(arrivalOrder2.getId().equals(arrivalOrderDetail.getOrderid())){
                                    PurchaseInvoiceDetail purchaseInvoiceDetail = new PurchaseInvoiceDetail();
                                    purchaseInvoiceDetail.setBillid(purchaseInvoice.getId());
                                    purchaseInvoiceDetail.setSourceid(arrivalOrder2.getId());
                                    purchaseInvoiceDetail.setSourcedetailid(arrivalOrderDetail.getId().toString());
                                    purchaseInvoiceDetail.setGoodsid(arrivalOrderDetail.getGoodsid());
                                    //应付日期paydate
                                    purchaseInvoiceDetail.setUnitid(arrivalOrderDetail.getUnitid());
                                    purchaseInvoiceDetail.setUnitname(arrivalOrderDetail.getUnitname());
                                    purchaseInvoiceDetail.setUnitnum(arrivalOrderDetail.getUnitnum());
                                    purchaseInvoiceDetail.setAuxunitid(arrivalOrderDetail.getAuxunitid());
                                    purchaseInvoiceDetail.setAuxunitname(arrivalOrderDetail.getAuxunitname());
                                    //通过商品编码、主单位数量、辅单位编码计算得到辅单位数量,辅单位数量描述信息
                                    Map auxMap = countGoodsInfoNumber(arrivalOrderDetail.getGoodsid(),
                                            arrivalOrderDetail.getAuxunitid(),
                                            arrivalOrderDetail.getUnitnum());
                                    purchaseInvoiceDetail.setAuxnum((BigDecimal) auxMap.get("auxnum"));
                                    purchaseInvoiceDetail.setAuxnumdetail( (String) auxMap.get("auxnumdetail"));

                                    purchaseInvoiceDetail.setTaxprice(arrivalOrderDetail.getTaxprice());
                                    purchaseInvoiceDetail.setTaxamount(arrivalOrderDetail.getTaxamount());
                                    purchaseInvoiceDetail.setNotaxprice(arrivalOrderDetail.getNotaxprice());
                                    purchaseInvoiceDetail.setNotaxamount(arrivalOrderDetail.getNotaxamount());
                                    purchaseInvoiceDetail.setTaxtype(arrivalOrderDetail.getTaxtype());
                                    purchaseInvoiceDetail.setTax(arrivalOrderDetail.getTaxamount().subtract(arrivalOrderDetail.getNotaxamount()));
                                    purchaseInvoiceDetail.setRemark(arrivalOrderDetail.getRemark());

                                    int i = purchaseInvoiceMapper.addPurchaseInvoiceDetail(purchaseInvoiceDetail);
                                    if(i > 0){
                                        dealArrailDeailIdList.add(arrivalOrderDetail.getId().toString());
                                        purchaseInvoiceDetailList.add(purchaseInvoiceDetail);
                                    }
                                    else{
                                        msg = "采购发票明细添加失败,";
                                        break;
                                    }
                                }
                            }
                        }
                        //purchaseForAccountService.updateArrivalOrderInvoice(invoiceState,  arrivalOrder2.getId(),dealArrailDeailIdList);
                    }
                    SysUser sysUser = getSysUser();
                    purchaseInvoice.setAdddeptid(sysUser.getDepartmentid());
                    purchaseInvoice.setAdddeptname(sysUser.getDepartmentname());
                    purchaseInvoice.setAdduserid(sysUser.getUserid());
                    purchaseInvoice.setAddusername(sysUser.getName());
                    //含税总金额
                    BigDecimal alltaxamount = new BigDecimal(0);
                    //未税总金额
                    BigDecimal allnotaxamount = new BigDecimal(0);
                    //应收总金额
                    BigDecimal invoiceamount =  new BigDecimal(0);
                    for(PurchaseInvoiceDetail purchaseInvoiceDetail : purchaseInvoiceDetailList){
                        alltaxamount = alltaxamount.add(purchaseInvoiceDetail.getTaxamount());
                        allnotaxamount = allnotaxamount.add(purchaseInvoiceDetail.getNotaxamount());
                        invoiceamount = invoiceamount.add(purchaseInvoiceDetail.getTaxamount());
                    }
                    purchaseInvoice.setTaxamount(alltaxamount);
                    purchaseInvoice.setNotaxamount(allnotaxamount);
                    purchaseInvoice.setInvoiceamount(invoiceamount);
                    purchaseInvoice.setSourceid(sourceid);
                    int i = purchaseInvoiceMapper.addPurchaseInvoice(purchaseInvoice);
                    flag = i > 0;
                    if(flag){
                        purchaseInvoiceid = purchaseInvoice.getId();
//						//回写上游单据开票状态
//						String[] sourceidArr = sourceids.split(",");
//						for(String sourceid : sourceidArr){
//							//List list = purchaseInvoiceMapper.getPurchaseInvoiceDetailListBySourceid(sourceid);
//							String billtype = (String) sourceidMap.get(sourceid);
//							returnNumBack(billtype, sourceid);
//						}
                    }
                }
            }
        }
        Map map = new HashMap();
        map.put("flag", flag);
        map.put("id", purchaseInvoiceid);
        map.put("msg", msg);
        return map;
    }

    @Override
    public Map addPurchaseInvoiceByReturnOrder(String ids,List<ReturnOrderDetail> returnOrderDetailList) throws Exception {
        boolean flag = false;
        String purchaseInvoiceid = "";
        String msg = "";
        if(StringUtils.isNotEmpty(ids)){
            String[] idArr = ids.split(",");
            String supplierid = null, buydept = null, buyuser = null,settletype = null,paytype = null;
            boolean addFlag = true;

            List<ReturnOrder> returnOrderList = new ArrayList<ReturnOrder>();
            //验证多个回单是否能组成一张采购发票
            if(idArr.length != 0){
                for(String id : idArr){
                    ReturnOrder returnOrder = purchaseForAccountService.showReturnOrder(id);
                    //判读是否同个供应商
                    if(null == supplierid || supplierid.equals(returnOrder.getSupplierid())){
                        if(null == supplierid){
                            supplierid = returnOrder.getSupplierid();
                        }
                    }
                    else{
                        addFlag = false;
                        msg = "不为同个供应商,";
                        break;
                    }
                    returnOrderList.add(returnOrder);
                }
            }
            //生成采购发票
            if(addFlag && returnOrderList.size() > 0){
                PurchaseInvoice purchaseInvoice = new PurchaseInvoice();
                if(isAutoCreate("t_account_purchase_invoice")){
                    //获取自动编号
                    String id = getAutoCreateSysNumbderForeign(purchaseInvoice, "t_account_purchase_invoice");
                    purchaseInvoice.setId(id);
                }
                else{
                    purchaseInvoice.setId("CGFP-"+CommonUtils.getDataNumberSendsWithRand());
                }
                ReturnOrder returnOrder = returnOrderList.get(0);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                purchaseInvoice.setBusinessdate(dateFormat.format(calendar.getTime()));
                purchaseInvoice.setStatus("2");
                purchaseInvoice.setSourcetype("2");
                //是否折扣 0否
                purchaseInvoice.setIsdiscount("0");
                //发票类型 1增值税
                purchaseInvoice.setInvoicetype("1");
                //是否核销 0否
                purchaseInvoice.setIswriteoff("0");

                BuySupplier buySupplier = getSupplierInfoById(returnOrder.getSupplierid());
                if(buySupplier != null){
                    purchaseInvoice.setSupplierid(buySupplier.getId());
                    purchaseInvoice.setHandlerid(buySupplier.getContact());
                }
                purchaseInvoice.setBuydept(returnOrder.getBuydeptid());
                purchaseInvoice.setBuyuser(returnOrder.getBuyuserid());
                purchaseInvoice.setSettletype(returnOrder.getSettletype());
                purchaseInvoice.setPaytype(returnOrder.getPaytype());

                List<PurchaseInvoiceDetail> purchaseInvoiceDetailList = new ArrayList<PurchaseInvoiceDetail>();
                String sourceid = "",invoiceState = "";
                List<String> dealReturnDeailIdList=new ArrayList<String>();
                for(ReturnOrder returnOrder2 : returnOrderList){
                    if(StringUtils.isEmpty(sourceid)){
                        sourceid = returnOrder2.getId();
                    }else{
                        sourceid += ","+returnOrder2.getId();
                    }
                    if(returnOrderDetailList.size() != 0){
                        for(ReturnOrderDetail returnOrderDetail : returnOrderDetailList){
                            if(returnOrder2.getId().equals(returnOrderDetail.getOrderid())){
                                PurchaseInvoiceDetail purchaseInvoiceDetail = new PurchaseInvoiceDetail();
                                purchaseInvoiceDetail.setBillid(purchaseInvoice.getId());
                                purchaseInvoiceDetail.setSourceid(returnOrder2.getId());
                                purchaseInvoiceDetail.setSourcedetailid(returnOrderDetail.getId().toString());
                                purchaseInvoiceDetail.setGoodsid(returnOrderDetail.getGoodsid());
                                //应付日期paydate
                                purchaseInvoiceDetail.setUnitid(returnOrderDetail.getUnitid());
                                purchaseInvoiceDetail.setUnitname(returnOrderDetail.getUnitname());
                                purchaseInvoiceDetail.setUnitnum(returnOrderDetail.getUnitnum());
                                purchaseInvoiceDetail.setAuxunitid(returnOrderDetail.getAuxunitid());
                                purchaseInvoiceDetail.setAuxunitname(returnOrderDetail.getAuxunitname());
                                //通过商品编码、主单位数量、辅单位编码计算得到辅单位数量,辅单位数量描述信息
                                Map auxMap = countGoodsInfoNumber(returnOrderDetail.getGoodsid(),
                                        returnOrderDetail.getAuxunitid(),
                                        returnOrderDetail.getUnitnum());
                                purchaseInvoiceDetail.setAuxnum((BigDecimal) auxMap.get("auxnum"));
                                purchaseInvoiceDetail.setAuxnumdetail( (String) auxMap.get("auxnumdetail"));

                                purchaseInvoiceDetail.setTaxprice(returnOrderDetail.getTaxprice());
                                purchaseInvoiceDetail.setTaxamount(returnOrderDetail.getTaxamount().negate());
                                purchaseInvoiceDetail.setNotaxprice(returnOrderDetail.getNotaxprice());
                                purchaseInvoiceDetail.setNotaxamount(returnOrderDetail.getNotaxamount().negate());
                                purchaseInvoiceDetail.setTaxtype(returnOrderDetail.getTaxtype());
                                purchaseInvoiceDetail.setTax(returnOrderDetail.getTaxamount().subtract(returnOrderDetail.getNotaxamount()).negate());
                                purchaseInvoiceDetail.setRemark(returnOrderDetail.getRemark());

                                int i = purchaseInvoiceMapper.addPurchaseInvoiceDetail(purchaseInvoiceDetail);
                                if(i > 0){
                                    dealReturnDeailIdList.add(returnOrderDetail.getId().toString());
                                    purchaseInvoiceDetailList.add(purchaseInvoiceDetail);
                                }
                                else{
                                    msg = "采购发票明细添加失败,";
                                    //throw new RuntimeException("采购发票明细添加失败");
                                    break;
                                }
                            }
                        }
                    }
                    //purchaseForAccountService.updateReturnOrderInvoice(invoiceState,  returnOrder2.getId(),dealReturnDeailIdList);
                }
                SysUser sysUser = getSysUser();
                purchaseInvoice.setAdddeptid(sysUser.getDepartmentid());
                purchaseInvoice.setAdddeptname(sysUser.getDepartmentname());
                purchaseInvoice.setAdduserid(sysUser.getUserid());
                purchaseInvoice.setAddusername(sysUser.getName());
                //含税总金额
                BigDecimal alltaxamount = new BigDecimal(0);
                //未税总金额
                BigDecimal allnotaxamount = new BigDecimal(0);
                //应收总金额
                BigDecimal invoiceamount =  new BigDecimal(0);
                for(PurchaseInvoiceDetail purchaseInvoiceDetail : purchaseInvoiceDetailList){
                    alltaxamount = alltaxamount.add(purchaseInvoiceDetail.getTaxamount());
                    allnotaxamount = allnotaxamount.add(purchaseInvoiceDetail.getNotaxamount());
                    invoiceamount = invoiceamount.add(purchaseInvoiceDetail.getTaxamount());
                }
                purchaseInvoice.setTaxamount(alltaxamount);
                purchaseInvoice.setNotaxamount(allnotaxamount);
                purchaseInvoice.setInvoiceamount(invoiceamount);
                purchaseInvoice.setSourceid(sourceid);
                int i = purchaseInvoiceMapper.addPurchaseInvoice(purchaseInvoice);
                flag = i > 0;
                if(flag){
                    purchaseInvoiceid = purchaseInvoice.getId();
                    //returnNumBack(purchaseInvoice,"2");
                }
            }
        }
        Map map = new HashMap();
        map.put("flag", flag);
        map.put("id", purchaseInvoiceid);
        map.put("msg", msg);
        return map;
    }

    @Override
    public Map getPurchaseInvoiceInfo(String id) throws Exception {
        PurchaseInvoice purchaseInvoice = purchaseInvoiceMapper.getPurchaseInvoiceInfo(id);
        List<PurchaseInvoiceDetail> detailList =getPurchaseInvoiceDetailFillInfo(id);
        Map map = new HashMap();
        map.put("purchaseInvoice", purchaseInvoice);
        map.put("detailList", detailList);
        return map;
    }

    public int countPurchasePush(String invoiceid) throws Exception{
        int count = 0 ;
        List<PurchaseInvoicePush> pushList = purchaseInvoicePushMapper.getPurchaseInvoicePushInfoList(invoiceid);
        if(null != pushList && pushList.size() > 0){
            count = pushList.size();
        }
        return count ;
    }

    /**
     * 填充采购发票明细
     * @param id 发票编号
     * @throws Exception
     * @author zhanghonghui
     * @date 2015年9月9日
     */
    private List<PurchaseInvoiceDetail> getPurchaseInvoiceDetailFillInfo(String id) throws Exception{
        List<PurchaseInvoiceDetail> detailList = purchaseInvoiceMapper.getPurchaseInvoiceDetailListGroupGoodsid(id);
        List<PurchaseInvoiceDetail> addpushDetailList = new ArrayList<PurchaseInvoiceDetail>();
        List<PurchaseInvoicePush> pushList = purchaseInvoicePushMapper.getPurchaseInvoicePushInfoList(id);

        for(PurchaseInvoicePush push : pushList){
            //验证发票明细里是否已添加折扣信息
            for(PurchaseInvoiceDetail purchaseInvoiceDetail : detailList){
                if(purchaseInvoiceDetail.getGoodsid().equals(push.getBrand())){
                    purchaseInvoiceDetail.setGoodsid(push.getBrand());//品牌的折扣替换到发票明细里的goodsid
                    purchaseInvoiceDetail.setTaxamount(push.getAmount());
                    purchaseInvoiceDetail.setSourceid(push.getId());
                }
            }
            if( push.getPushtype().equals("1")){
                PurchaseInvoiceDetail detail = new PurchaseInvoiceDetail();
                detail.setField01(push.getId());//放入采购发票冲差单编号
                detail.setGoodsid(push.getBrand());//品牌的折扣替换到发票明细里的goodsid
                detail.setTaxamount(push.getAmount());
                Brand brand = getGoodsBrandByID(push.getBrand());
                if(null != brand){
                    detail.setTaxtype(brand.getDefaulttaxtype());
                }
                BigDecimal notaxamount = BigDecimal.ZERO;
                BigDecimal taxamount=push.getAmount();
                if(StringUtils.isNotEmpty(detail.getTaxtype())){
                    notaxamount = getNotaxAmountByTaxAmount(taxamount,detail.getTaxtype());
                }else{
                    notaxamount = getNotaxAmountByTaxAmount(taxamount,null);
                }
                detail.setNotaxamount(notaxamount);
                BigDecimal tax = detail.getTaxamount().subtract(detail.getNotaxamount());

                detail.setTax(tax);
                detail.setSourceid(push.getId());//折扣的sourceid为空 方便前台判断
                detail.setRemark(push.getRemark());
                //将冲差单类型写入到商品列表的里
                detail.setField05(push.getPushtype());
                addpushDetailList.add(detail);

            }else{
                PurchaseInvoiceDetail detail = new PurchaseInvoiceDetail();
                detail.setField01(push.getId());//放入采购发票冲差单编号
                detail.setGoodsid(push.getBrand());//品牌的折扣替换到发票明细里的goodsid
                if(StringUtils.isNotEmpty(push.getBrand())){
                    Brand brand = getGoodsBrandByID(push.getBrand());
                    if(null != brand){
                        detail.setBrandname(brand.getName());
                    }
                }
                detail.setTaxamount(push.getAmount());
                BigDecimal notaxamount = getNotaxAmountByTaxAmount(push.getAmount(),null);
                detail.setNotaxamount(notaxamount);
                BigDecimal tax = detail.getTaxamount().subtract(detail.getNotaxamount());

                detail.setTax(tax);
                detail.setSourceid("");//折扣的sourceid为空 方便前台判断
                SysParam sysParam = getBaseSysParamMapper().getSysParam("DEFAULTAXTYPE");
                if(null != sysParam){
                    detail.setTaxtype(sysParam.getPvalue());
                }
                //将冲差单类型写入到商品列表的里
                detail.setField05(push.getPushtype());
                detail.setRemark(push.getRemark());
                addpushDetailList.add(detail);
            }
        }
        detailList.addAll(addpushDetailList);//发票明细中添加冲差明细

        if(detailList.size() != 0){
            for(PurchaseInvoiceDetail purchaseInvoiceDetail : detailList){
                if(!"3".equals(purchaseInvoiceDetail.getSourcetype())){
                    Map auxMap = countGoodsInfoNumber(purchaseInvoiceDetail.getGoodsid(),purchaseInvoiceDetail.getAuxunitid(),purchaseInvoiceDetail.getUnitnum());
                    BigDecimal auxNum = (BigDecimal) auxMap.get("auxnum");
                    String auxnumdetail = (String) auxMap.get("auxnumdetail");
                    purchaseInvoiceDetail.setAuxnum(auxNum);
                    purchaseInvoiceDetail.setAuxnumdetail(auxnumdetail);

                    GoodsInfo goodsInfo  = getGoodsInfoByID(purchaseInvoiceDetail.getGoodsid());
                    if(goodsInfo == null ||StringUtils.isEmpty(goodsInfo.getId())){
                        GoodsInfo goodsInfo1 = new GoodsInfo() ;
                        Brand brand = getGoodsBrandByID(purchaseInvoiceDetail.getGoodsid());

                        if(brand == null){
                            if(StringUtils.isNotEmpty(purchaseInvoiceDetail.getBrandname())){
                                if("1".equals(purchaseInvoiceDetail.getField05())){
                                    purchaseInvoiceDetail.setGoodsname("(代垫费用 折扣)"+purchaseInvoiceDetail.getBrandname());
                                }else if("2".equals(purchaseInvoiceDetail.getField05())){
                                    purchaseInvoiceDetail.setGoodsname("(价格冲差 折扣)"+purchaseInvoiceDetail.getBrandname());
                                }else if("3".equals(purchaseInvoiceDetail.getField05())){
                                    purchaseInvoiceDetail.setGoodsname("(货物冲差 折扣)"+purchaseInvoiceDetail.getBrandname());
                                }
                            }else{
                                purchaseInvoiceDetail.setGoodsname("折扣");
                                if("1".equals(purchaseInvoiceDetail.getField05())){
                                    purchaseInvoiceDetail.setGoodsname("代垫费用 折扣");
                                }else if("2".equals(purchaseInvoiceDetail.getField05())){
                                    purchaseInvoiceDetail.setGoodsname("价格冲差 折扣");
                                }else if("3".equals(purchaseInvoiceDetail.getField05())){
                                    purchaseInvoiceDetail.setGoodsname("货物冲差 折扣");
                                }
                            }
                            goodsInfo1.setName("折扣");
                            purchaseInvoiceDetail.setGoodsInfo(goodsInfo1);
                        }else{
                            if("1".equals(purchaseInvoiceDetail.getField05())){
                                purchaseInvoiceDetail.setGoodsname("(代垫费用 折扣)"+brand.getName());
                            }else if("2".equals(purchaseInvoiceDetail.getField05())){
                                purchaseInvoiceDetail.setGoodsname("(价格冲差 折扣)"+brand.getName());
                            }else if("3".equals(purchaseInvoiceDetail.getField05())){
                                purchaseInvoiceDetail.setGoodsname("(货物冲差 折扣)"+brand.getName());
                            }
                            purchaseInvoiceDetail.setTaxtype(brand.getDefaulttaxtype());
                            TaxType taxType = getTaxType(purchaseInvoiceDetail.getTaxtype());
                            if(null != taxType){
                                purchaseInvoiceDetail.setTaxtypename(taxType.getName());
                            }
                            purchaseInvoiceDetail.setBrandname(brand.getName());
                            goodsInfo1.setName("(折扣)"+brand.getName());
                            purchaseInvoiceDetail.setGoodsInfo(goodsInfo1);
                        }

                    }else{
                        purchaseInvoiceDetail.setGoodsname(goodsInfo.getName());
                        purchaseInvoiceDetail.setBarcode(goodsInfo.getBarcode());
                        purchaseInvoiceDetail.setModel(goodsInfo.getModel());
                        Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                        if(null != brand){
                            purchaseInvoiceDetail.setBrandname(brand.getName());
                        }

                        purchaseInvoiceDetail.setGoodsInfo(goodsInfo);
                        TaxType taxType = getTaxType(purchaseInvoiceDetail.getTaxtype());
                        if(null != taxType){
                            purchaseInvoiceDetail.setTaxtypename(taxType.getName());
                        }
                    }
                }else{
                    GoodsInfo goodsInfo = new GoodsInfo();
                    goodsInfo.setName("应付款期初");
                    purchaseInvoiceDetail.setIsedit("");
                    purchaseInvoiceDetail.setGoodsname("应付款期初");
                    purchaseInvoiceDetail.setGoodsInfo(goodsInfo);
                    purchaseInvoiceDetail.setUnitnum(new BigDecimal(1));
                    purchaseInvoiceDetail.setTaxprice(purchaseInvoiceDetail.getTaxamount());
                    purchaseInvoiceDetail.setNotaxprice(purchaseInvoiceDetail.getNotaxamount());
                    if(StringUtils.isNotEmpty(purchaseInvoiceDetail.getTaxtype())){
                        TaxType taxType = getTaxType(purchaseInvoiceDetail.getTaxtype());
                        if(null!=taxType){
                            purchaseInvoiceDetail.setTaxtypename(taxType.getName());
                        }
                    }
                }
            }
        }
        return detailList;
    }

    @Override
    public PurchaseInvoice showPurchaseInvoiceInfo(String id) throws Exception {
        PurchaseInvoice purchaseInvoice = purchaseInvoiceMapper.getPurchaseInvoiceInfo(id);

        if(null!=purchaseInvoice){
            BuySupplier buySupplier = getSupplierInfoById(purchaseInvoice.getSupplierid());
            if(null!=buySupplier){
                purchaseInvoice.setSuppliername(buySupplier.getName());
            }
        }
        return purchaseInvoice;
    }
    @Override
    public PurchaseInvoice showPurchaseInvoicePureInfo(String id) throws Exception{
        return purchaseInvoiceMapper.getPurchaseInvoiceInfo(id);
    }

    @Override
    public List getPurchaseInvoiceDetailListByGoodsid(String billid, String goodsid) throws Exception {
        List<PurchaseInvoiceDetail> detailList = getPurchaseInvoiceDetailFillInfoByGoodsid(billid,goodsid );
        return detailList;
    }

    @Override
    public List<PurchaseInvoiceDetail> getPurchaseInvoiceDetailList(String billid) throws Exception {
        return  purchaseInvoiceMapper.getPurchaseInvoiceDetailListGroupGoodsid(billid);
    }

    /**
     * 获取供应商采购发票总计
     * @param idarr
     * @return
     * @throws Exception
     */
    @Override
    public List<Map> getSupplierPushSumData(List<String> idarr) throws Exception {
        if(null!=idarr && idarr.size()>0){
            List<Map> list = purchaseInvoiceMapper.getSupplierRejectSumData(idarr);
            return list;
        }
        return new ArrayList<Map>();
    }

    /**
     * 获取采购发票明细列表根据商品编码
     * @param id
     * @param goodsid
     * @return
     * @throws Exception
     */
    private List<PurchaseInvoiceDetail> getPurchaseInvoiceDetailFillInfoByGoodsid(String id, String goodsid)throws Exception{
        List<PurchaseInvoiceDetail> detailList = purchaseInvoiceMapper.getPurchaseInvoiceDetailListByGoodsid(id,goodsid);

        if(detailList.size() != 0){
            for(PurchaseInvoiceDetail purchaseInvoiceDetail : detailList){
                if(!"3".equals(purchaseInvoiceDetail.getSourcetype())){
                    Map auxMap = countGoodsInfoNumber(purchaseInvoiceDetail.getGoodsid(),purchaseInvoiceDetail.getAuxunitid(),purchaseInvoiceDetail.getUnitnum());
                    BigDecimal auxNum = (BigDecimal) auxMap.get("auxnum");
                    String auxnumdetail = (String) auxMap.get("auxnumdetail");
                    purchaseInvoiceDetail.setAuxnum(auxNum);
                    purchaseInvoiceDetail.setAuxnumdetail(auxnumdetail);

                    GoodsInfo goodsInfo  = getGoodsInfoByID(purchaseInvoiceDetail.getGoodsid());
                    if(goodsInfo == null ||StringUtils.isEmpty(goodsInfo.getId())){
                        GoodsInfo goodsInfo1 = new GoodsInfo() ;
                        Brand brand = getGoodsBrandByID(purchaseInvoiceDetail.getGoodsid());
                        if(brand == null){
                            purchaseInvoiceDetail.setGoodsname("折扣");
                            goodsInfo1.setName("折扣");
                            purchaseInvoiceDetail.setGoodsInfo(goodsInfo1);
                        }else{
                            purchaseInvoiceDetail.setGoodsname("(折扣)"+brand.getName());
                            purchaseInvoiceDetail.setTaxtype(brand.getDefaulttaxtype());
                            TaxType taxType = getTaxType(purchaseInvoiceDetail.getTaxtype());
                            if(null != taxType){
                                purchaseInvoiceDetail.setTaxtypename(taxType.getName());
                            }
                            purchaseInvoiceDetail.setBrandname(brand.getName());

                            goodsInfo1.setName("(折扣)"+brand.getName());
                            purchaseInvoiceDetail.setGoodsInfo(goodsInfo1);
                        }

                    }else{
                        purchaseInvoiceDetail.setGoodsname(goodsInfo.getName());
                        purchaseInvoiceDetail.setBarcode(goodsInfo.getBarcode());
                        purchaseInvoiceDetail.setModel(goodsInfo.getModel());
                        Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                        if(null != brand){
                            purchaseInvoiceDetail.setBrandname(brand.getName());
                        }

                        purchaseInvoiceDetail.setGoodsInfo(goodsInfo);
                        TaxType taxType = getTaxType(purchaseInvoiceDetail.getTaxtype());
                        if(null != taxType){
                            purchaseInvoiceDetail.setTaxtypename(taxType.getName());
                        }
                    }
                }else{
                    GoodsInfo goodsInfo = new GoodsInfo();
                    goodsInfo.setName("应付款期初");
                    purchaseInvoiceDetail.setGoodsname("应付款期初");
                    purchaseInvoiceDetail.setGoodsInfo(goodsInfo);
                    purchaseInvoiceDetail.setUnitnum(new BigDecimal(1));
                    purchaseInvoiceDetail.setTaxprice(purchaseInvoiceDetail.getTaxamount());
                    purchaseInvoiceDetail.setNotaxprice(purchaseInvoiceDetail.getNotaxamount());
                    if(StringUtils.isNotEmpty(purchaseInvoiceDetail.getTaxtype())){
                        TaxType taxType = getTaxType(purchaseInvoiceDetail.getTaxtype());
                        if(null!=taxType){
                            purchaseInvoiceDetail.setTaxtypename(taxType.getName());
                        }
                    }
                }
            }
        }
        return detailList;
    }

    @Override
    public boolean deletePurchaseInvoice(String id) throws Exception {
        PurchaseInvoice purchaseInvoice = purchaseInvoiceMapper.getPurchaseInvoiceInfo(id);
        boolean flag = false;
        if(null != purchaseInvoice){
            if("1".equals(purchaseInvoice.getStatus()) || "2".equals(purchaseInvoice.getStatus())){
                //未删除发票明细里相同来源单据号及其明细列表
                List<PurchaseInvoiceDetail> detailSourceTypeList=purchaseInvoiceMapper.getPurchaseInvoiceDetailSourcetypeList(id);

                int i = purchaseInvoiceMapper.deletePurchaseInvoice(id);
                purchaseInvoiceMapper.deletePurchaseInvoiceDetail(id);
                purchaseInvoicePushMapper.deletePurchaseInvoicePush(id);
                flag = i > 0;
                if(flag && null!=detailSourceTypeList && detailSourceTypeList.size()>0){
                    Map sumMap=new HashMap();
                    for(PurchaseInvoiceDetail item : detailSourceTypeList){
                        if("1".equals(item.getSourcetype())){
                            sumMap.put("sourceid", item.getSourceid());
                            sumMap.put("sourcetype", item.getSourcetype());
                            List<PurchaseInvoiceDetail> list = purchaseInvoiceMapper.getPurchaseInvoiceDetailSumListBySourceidGroup(sumMap);
                            purchaseForAccountService.updateArrivalOrderInvoiceReferWriteBack(item.getSourceid(),list);
                        }else if("2".equals(item.getSourcetype())){
                            sumMap.put("sourceid", item.getSourceid());
                            sumMap.put("sourcetype", item.getSourcetype());
                            List<PurchaseInvoiceDetail> list = purchaseInvoiceMapper.getPurchaseInvoiceDetailSumListBySourceidGroup(sumMap);
                            purchaseForAccountService.updateReturnOrderInvoiceReferWriteBack(item.getSourceid(),list);
                        }else if("3".equals(item.getSourcetype())){
                            sumMap.put("sourceid", item.getSourceid());
                            sumMap.put("sourcetype", item.getSourcetype());
                            List<PurchaseInvoiceDetail> list = purchaseInvoiceMapper.getPurchaseInvoiceDetailSumListBySourceidGroup(sumMap);
                            purchaseForAccountService.updateBeginDueInvoiceReferWriteBack(item.getSourceid(),list);
                        }
                    }
                }
            }
        }
        return flag;
    }

    @Override
    public Map editPurchaseInVoice(PurchaseInvoice purchaseInvoice,
                                   List<PurchaseInvoiceDetail> detailList) throws Exception {
        boolean flag = false;
        Map map = new HashMap();
        PurchaseInvoice oldPurchaseInvoice = purchaseInvoiceMapper.getPurchaseInvoiceInfo(purchaseInvoice.getId());
        if(null==oldPurchaseInvoice || "3".equals(oldPurchaseInvoice.getStatus()) || "4".equals(oldPurchaseInvoice.getStatus())){
            map.put("flag", false);
            map.put("id", "");
            return map;
        }
        BigDecimal taxamount = null != oldPurchaseInvoice.getTaxamount() ? oldPurchaseInvoice.getTaxamount() : BigDecimal.ZERO;
        BigDecimal notaxamount = null != oldPurchaseInvoice.getNotaxamount() ? oldPurchaseInvoice.getNotaxamount() : BigDecimal.ZERO;
        BigDecimal invoiceamount = null != oldPurchaseInvoice.getInvoiceamount()?oldPurchaseInvoice.getInvoiceamount() : BigDecimal.ZERO;

        for(PurchaseInvoiceDetail purchaseInvoiceDetail : detailList){
            String goodsid = purchaseInvoiceDetail.getGoodsid();
            if(purchaseInvoiceDetail != null && StringUtils.isNotEmpty(goodsid)){
                GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
                if(null != goodsInfo){
                    PurchaseInvoiceDetail oldDetail = purchaseInvoiceMapper.getPurchaseInvoiceGoodsByGoodsidSourceid
                            (purchaseInvoice.getId(),purchaseInvoiceDetail.getSourceid(), goodsid);
                    if(null != oldDetail){
                        BigDecimal taxamountExtra = purchaseInvoiceDetail.getTaxamount().subtract(oldDetail.getTaxamount());
                        taxamount = taxamount.add(taxamountExtra);
                        invoiceamount = invoiceamount.add(taxamountExtra);
                        BigDecimal notaxamountExtra = purchaseInvoiceDetail.getNotaxamount().subtract(oldDetail.getNotaxamount());
                        notaxamount = notaxamount.add(notaxamountExtra);
                        //对非折扣的商品记录进行更新操作
                        purchaseInvoiceMapper.deletePurchaseInvoiceDetailById(purchaseInvoiceDetail.getId());
                        purchaseInvoiceDetail.setBillid(purchaseInvoice.getId());
                        purchaseInvoiceMapper.addPurchaseInvoiceDetail(purchaseInvoiceDetail);
                    }
                }
            }
        }

        purchaseInvoice.setTaxamount(taxamount);
        purchaseInvoice.setNotaxamount(notaxamount);
        //purchaseInvoice.setWriteoffamount(taxamount);//核销金额
        purchaseInvoice.setInvoiceamount(invoiceamount);//应付金额
        SysUser sysUser = getSysUser();
        purchaseInvoice.setModifyuserid(sysUser.getUserid());
        purchaseInvoice.setModifyusername(sysUser.getName());

        int i = purchaseInvoiceMapper.editPurchaseInvoice(purchaseInvoice);
        flag = i>0;
        if(flag){
            List<PurchaseInvoiceDetail> detailSourceTypeList=purchaseInvoiceMapper.getPurchaseInvoiceDetailSourcetypeList(purchaseInvoice.getId());
            Map sumMap=new HashMap();
            for(PurchaseInvoiceDetail item : detailSourceTypeList){
                if("1".equals(item.getSourcetype())){
                    sumMap.put("sourceid", item.getSourceid());
                    sumMap.put("sourcetype", item.getSourcetype());
                    List<PurchaseInvoiceDetail> list = purchaseInvoiceMapper.getPurchaseInvoiceDetailSumListBySourceidGroup(sumMap);
                    purchaseForAccountService.updateArrivalOrderInvoiceReferWriteBack(item.getSourceid(),list);
                }else if("2".equals(item.getSourcetype())){
                    sumMap.put("sourceid", item.getSourceid());
                    sumMap.put("sourcetype", item.getSourcetype());
                    List<PurchaseInvoiceDetail> list = purchaseInvoiceMapper.getPurchaseInvoiceDetailSumListBySourceidGroup(sumMap);
                    purchaseForAccountService.updateReturnOrderInvoiceReferWriteBack(item.getSourceid(),list);
                }else if("3".equals(item.getSourcetype())){
                    sumMap.put("sourceid", item.getSourceid());
                    sumMap.put("sourcetype", item.getSourcetype());
                    List<PurchaseInvoiceDetail> list = purchaseInvoiceMapper.getPurchaseInvoiceDetailSumListBySourceidGroup(sumMap);
                    purchaseForAccountService.updateBeginDueInvoiceReferWriteBack(item.getSourceid(),list);
                }
            }
        }

        map.put("flag", flag);
        map.put("id", purchaseInvoice.getId());
        return map;
    }

    /**
     * 回写来源单据发票状态
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 16, 2013
     */
    public void returnNumBackIsinvoice(String type,String sourceid)throws Exception{
        boolean flag = true;
        if("1".equals(type)){
        }
        else if("2".equals(type)){
        }
    }

    /**
     * 回写来源单据明细是否开票引用,回写未开票，已开票数量，开票、未开票金额
     * @param sourceid
     * @param type 0无，1采购进货单，2采购退货通知单
     * @author panxiaoxiao
     * @date Sep 11, 2013
     */
    public void returnNumBack(String type,String sourceid)throws Exception{
        if("1".equals(type)){
        }
        else if("2".equals(type)){
        }
    }

    /**
     * 删除时,释放未开票，已开票数量,未开票，开票金额
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 16, 2013
     */
    public void releaseNumBackByDelete(String type,String sourceid,String purchaseid)throws Exception{
        if("1".equals(type)){
        }
        else if("2".equals(type)){
        }
    }

    /**
     * 释放未开票，已开票数量
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 13, 2013
     */
    public void releaseNumBack(String type,String sourceid)throws Exception{
        boolean flag = true;
        if("1".equals(type)){
        }
        else if("2".equals(type)){
        }
    }

    @Override
    public boolean auditPurchaseInvoice(String id) throws Exception {
        PurchaseInvoice purchaseInvoice = purchaseInvoiceMapper.getPurchaseInvoiceInfo(id);
        boolean flag = false;
        Map map = new HashMap();
        if("2".equals(purchaseInvoice.getStatus()) || "6".equals(purchaseInvoice.getStatus())){
            SysUser sysUser = getSysUser();
            map.put("audituserid", sysUser.getUserid());
            map.put("auditusername", sysUser.getName());
            map.put("id", id);
            int i = purchaseInvoiceMapper.auditPurchaseInvoice(map);
            flag = i>0;
            if(flag){
                //审核通过后 判断采购发票金额是否一致 如果不一致 更新采购发票冲差单状态
                if(purchaseInvoice.getTaxamount().compareTo(purchaseInvoice.getInvoiceamount())!=0){
                    purchaseInvoicePushMapper.updatePurchaseInvoicePushStatus(purchaseInvoice.getId(), "3");
                }
                List<PurchaseInvoiceDetail> detailSourceTypeList=purchaseInvoiceMapper.getPurchaseInvoiceDetailSourcetypeList(purchaseInvoice.getId());
                Map sumMap=new HashMap();
                for(PurchaseInvoiceDetail item : detailSourceTypeList){
                    if("1".equals(item.getSourcetype())){
                        sumMap.put("sourceid", item.getSourceid());
                        sumMap.put("sourcetype", item.getSourcetype());
                        sumMap.put("statusarr", "3,4");
                        List<PurchaseInvoiceDetail> list = purchaseInvoiceMapper.getPurchaseInvoiceDetailSumListBySourceidGroup(sumMap);
                        purchaseForAccountService.updateArrivalOrderInvoiceAuditWriteBack(item.getSourceid(),list);
                    }else if("2".equals(item.getSourcetype())){
                        sumMap.put("sourceid", item.getSourceid());
                        sumMap.put("sourcetype", item.getSourcetype());
                        sumMap.put("statusarr", "3,4");
                        List<PurchaseInvoiceDetail> list = purchaseInvoiceMapper.getPurchaseInvoiceDetailSumListBySourceidGroup(sumMap);
                        purchaseForAccountService.updateReturnOrderInvoiceAuditWriteBack(item.getSourceid(),list);
                    }else if("3".equals(item.getSourcetype())){
                        sumMap.put("sourceid", item.getSourceid());
                        sumMap.put("sourcetype", item.getSourcetype());
                        sumMap.put("statusarr", "3");
                        List<PurchaseInvoiceDetail> list = purchaseInvoiceMapper.getPurchaseInvoiceDetailSumListBySourceidGroup(sumMap);
                        purchaseForAccountService.updateBeginDueInvoiceAuditWriteBack(item.getSourceid(),list, "1");
                    }
                }
            }
        }
        return flag;
    }

    @Override
    public boolean oppauditPurchaseInvoice(String id) throws Exception {
        PurchaseInvoice purchaseInvoice = purchaseInvoiceMapper.getPurchaseInvoiceInfo(id);
        boolean flag = false;
        Map map = new HashMap();
        if("3".equals(purchaseInvoice.getStatus())){
            SysUser sysUser = getSysUser();
            map.put("audituserid", sysUser.getUserid());
            map.put("auditusername", sysUser.getName());
            map.put("id", id);
            int i = purchaseInvoiceMapper.oppauditPurchaseInvoice(map);
            flag = i>0;
            if(flag){
                //审核通过后 判断采购发票金额是否一致 如果不一致 更新采购发票冲差单状态
                if(purchaseInvoice.getTaxamount().compareTo(purchaseInvoice.getInvoiceamount())!=0){
                    purchaseInvoicePushMapper.updatePurchaseInvoicePushStatus(purchaseInvoice.getId(), "2");
                }

                List<PurchaseInvoiceDetail> detailSourceTypeList=purchaseInvoiceMapper.getPurchaseInvoiceDetailSourcetypeList(purchaseInvoice.getId());
                Map sumMap=new HashMap();
                for(PurchaseInvoiceDetail item : detailSourceTypeList){
                    if("1".equals(item.getSourcetype())){
                        sumMap.put("sourceid", item.getSourceid());
                        sumMap.put("sourcetype", item.getSourcetype());
                        sumMap.put("statusarr", "3,4");
                        List<PurchaseInvoiceDetail> list = purchaseInvoiceMapper.getPurchaseInvoiceDetailSumListBySourceidGroup(sumMap);
                        purchaseForAccountService.updateArrivalOrderInvoiceAuditWriteBack(item.getSourceid(),list);
                    }else if("2".equals(item.getSourcetype())){
                        sumMap.put("sourceid", item.getSourceid());
                        sumMap.put("sourcetype", item.getSourcetype());
                        sumMap.put("statusarr", "3,4");
                        List<PurchaseInvoiceDetail> list = purchaseInvoiceMapper.getPurchaseInvoiceDetailSumListBySourceidGroup(sumMap);
                        purchaseForAccountService.updateReturnOrderInvoiceAuditWriteBack(item.getSourceid(),list);
                    }else if("3".equals(item.getSourcetype())){
                        sumMap.put("sourceid", item.getSourceid());
                        sumMap.put("sourcetype", item.getSourcetype());
                        sumMap.put("statusarr", "3");
                        List<PurchaseInvoiceDetail> list = purchaseInvoiceMapper.getPurchaseInvoiceDetailSumListBySourceidGroup(sumMap);
                        purchaseForAccountService.updateBeginDueInvoiceAuditWriteBack(item.getSourceid(),list, "0");
                    }
                }
            }
        }
        return flag;
    }

    @Override
    public List showPurchaseInvoiceListBySupplier(String supplierid)
            throws Exception {
        List<PurchaseInvoice> list = purchaseInvoiceMapper.getPurchaseInvoiceListBySupplierid(supplierid);
        if(list.size() != 0){
            for(PurchaseInvoice purchaseInvoice : list){
                BuySupplier buySupplier = getSupplierInfoById(purchaseInvoice.getSupplierid());
                if(null != buySupplier){
                    purchaseInvoice.setSuppliername(buySupplier.getName());
                }
                Contacter contacter = getContacterById(purchaseInvoice.getHandlerid());
                if(null!=contacter){
                    purchaseInvoice.setHandlername(contacter.getName());
                }
                DepartMent departMent = getDepartmentByDeptid(purchaseInvoice.getBuydept());
                if(null!=departMent){
                    purchaseInvoice.setBuydeptname(departMent.getName());
                }
                Personnel personnel = getPersonnelById(purchaseInvoice.getBuyuser());
                if(null!=personnel){
                    purchaseInvoice.setBuyusername(personnel.getName());
                }
            }
        }
        return list;
    }

    @Override
    public List getPurchaseInvoiceListBySupplierid(String supplierid)
            throws Exception {
        List<PurchaseInvoice> list = purchaseInvoiceMapper.getPurchaseInvoiceListDataBySupplierid(supplierid);
        if(list.size() != 0){
            for(PurchaseInvoice purchaseInvoice : list){
                BuySupplier buySupplier = getSupplierInfoById(purchaseInvoice.getSupplierid());
                if(null != buySupplier){
                    purchaseInvoice.setSuppliername(buySupplier.getName());
                }
                Contacter contacter = getContacterById(purchaseInvoice.getHandlerid());
                if(null!=contacter){
                    purchaseInvoice.setHandlername(contacter.getName());
                }
                DepartMent departMent = getDepartmentByDeptid(purchaseInvoice.getBuydept());
                if(null!=departMent){
                    purchaseInvoice.setBuydeptname(departMent.getName());
                }
                Personnel personnel = getPersonnelById(purchaseInvoice.getBuyuser());
                if(null!=personnel){
                    purchaseInvoice.setBuyusername(personnel.getName());
                }
            }
        }
        return list;
    }

    @Override
    public List getPurchaseInvoiceListByInvoiceids(String invoiceid)
            throws Exception {
        List list = new ArrayList();
        if(StringUtils.isNotEmpty(invoiceid)){
            String[] invoiceidArr = invoiceid.split(",");
            for(String id : invoiceidArr){
                PurchaseInvoice purchaseInvoice = purchaseInvoiceMapper.getPurchaseInvoiceInfo(id);
                list.add(purchaseInvoice);
            }
        }
        return list;
    }
    @Override
    public boolean submitPurchaseInvoicePageProcess(String id) throws Exception {
        return false;
    }

    @Override
    public List showPurchaseInvoiceSourceListReferData(String sourceid,
                                                       String sourcetype) throws Exception {
        if(null!=sourceid){
            String[] ids = sourceid.split(",");
            List list = new ArrayList();
            for(String id: ids){
                ArrivalOrder arrivalOrder =  arrivalOrderService.getArrivalOrder(id);
                if(null != arrivalOrder){
                    list.add(arrivalOrder);
                }
                ReturnOrder returnOrder =  returnOrderService.getReturnOrder(id);
                if(null != returnOrder){
                    list.add(returnOrder);
                }
            }
            return list;
        }
        return null;
    }

    @Override
    public PurchaseInvoiceDetail getPurchaseInvoiceDetailInfo(String sourceid,String billid,
                                                              String goodsid) throws Exception {
        return purchaseInvoiceMapper.getPurchaseInvoiceDetailInfo(sourceid,billid,goodsid);
    }

    @Override
    public PurchaseInvoiceDetail getPurchaseInvoiceDetailInfoBySource(String sourceid,String billid,
                                                                      String sourcedetailid) throws Exception {
        return purchaseInvoiceMapper.getPurchaseInvoiceDetailInfoBySource(sourceid,billid,sourcedetailid);
    }

    @Override
    public PurchaseInvoicePush getPurchaseInvoicePushInfo(String id)
            throws Exception {
        PurchaseInvoicePush purchaseInvoicePush= purchaseInvoicePushMapper.getPurchaseInvoicePushInfoByType(id);
        return purchaseInvoicePush;
    }

    @Override
    public boolean addOrUpdatePurchaseInvoicePush(
            PurchaseInvoicePush purchaseInvoicePush) throws Exception {
        if(null!=purchaseInvoicePush){

            PurchaseInvoice purchaseInvoice=purchaseInvoiceMapper.getPurchaseInvoiceInfo(purchaseInvoicePush.getInvoiceid());
            if(null==purchaseInvoice || "3".equals(purchaseInvoice.getStatus()) || "4".equals(purchaseInvoice.getStatus())){
                return false;
            }
            int i = 0;
            SysUser sysUser = getSysUser();
            String brand = purchaseInvoicePush.getBrand();
            if(("2".equals(purchaseInvoicePush.getPushtype()) && StringUtils.isEmpty(brand)) || ("3".equals(purchaseInvoicePush.getPushtype())&& StringUtils.isEmpty(brand))){
                List<Map> list=purchaseInvoiceMapper.getPurchaseInvoiceBrandList(purchaseInvoicePush.getInvoiceid());
                if(null!=list && list.size()>0){

                    int tmpi=0;
                    PurchaseInvoicePush aPurchaseInvoicePush=null;
                    PurchaseInvoicePush maxPurchaseInvoicePush=null;//最大品牌的采购金额
                    BigDecimal totalAmount=BigDecimal.ZERO;	//合计总冲差金额
                    List<PurchaseInvoicePush> brandPIPList=new ArrayList<PurchaseInvoicePush>();

                    for(Map map:list){
                        String brandid=(String)map.get("brandid");
                        if(null==brandid || "".equals(brandid.trim())){
                            continue;
                        }
                        aPurchaseInvoicePush=(PurchaseInvoicePush)CommonUtils.deepCopy(purchaseInvoicePush);
                        aPurchaseInvoicePush.setBrand(brandid);
                        //按品牌采购金额进行分摊 开始
                        BigDecimal brandamount=(BigDecimal)map.get("amount");
                        BigDecimal tmpd=brandamount.divide(purchaseInvoice.getTaxamount(),6,BigDecimal.ROUND_HALF_UP);
                        tmpd=tmpd.multiply(purchaseInvoicePush.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP);
                        //按品牌采购金额进行分摊 结束

                        aPurchaseInvoicePush.setAmount(tmpd);

                        totalAmount=totalAmount.add(tmpd);	//合计所有冲差金额

                        if(null==maxPurchaseInvoicePush){
                            maxPurchaseInvoicePush=aPurchaseInvoicePush;
                        }else if(maxPurchaseInvoicePush.getAmount().abs().compareTo(tmpd.abs())<0){
                            maxPurchaseInvoicePush=aPurchaseInvoicePush;
                        }

                        aPurchaseInvoicePush.setStatus("2");
                        aPurchaseInvoicePush.setAdduserid(sysUser.getUserid());
                        aPurchaseInvoicePush.setAddusername(sysUser.getName());
                        aPurchaseInvoicePush.setAdddeptid(sysUser.getDepartmentid());
                        aPurchaseInvoicePush.setAdddeptname(sysUser.getDepartmentname());
                        aPurchaseInvoicePush.setField08("amountbrandfentan");//金额品牌分摊

                        //添加到分摊列表
                        brandPIPList.add(aPurchaseInvoicePush);
                    }
                    //当中最大的金额，且出现最早采购订单品牌
                    if(totalAmount.abs().compareTo(purchaseInvoicePush.getAmount().abs())<0){
                        maxPurchaseInvoicePush.setAmount(
                                maxPurchaseInvoicePush.getAmount().add(
                                        purchaseInvoicePush.getAmount().subtract(totalAmount)
                                ));
                    }else if(totalAmount.abs().compareTo(purchaseInvoicePush.getAmount().abs())>0){
                        maxPurchaseInvoicePush.setAmount(
                                maxPurchaseInvoicePush.getAmount().subtract(
                                        totalAmount.subtract(purchaseInvoicePush.getAmount())
                                ));
                    }
                    //根据编码 invoiceid 类型 pushtype 和品牌brand 判断更新或增加冲差单
                    for(PurchaseInvoicePush itempush:brandPIPList){
                        tmpi = purchaseInvoicePushMapper.addPurchaseInvoicePursh(itempush);
                        if(tmpi>0){
                            i=i+1;
                        }
                    }
                }else{
                    purchaseInvoicePush.setStatus("2");
                    purchaseInvoicePush.setAdduserid(sysUser.getUserid());
                    purchaseInvoicePush.setAddusername(sysUser.getName());
                    purchaseInvoicePush.setAdddeptid(sysUser.getDepartmentid());
                    purchaseInvoicePush.setAdddeptname(sysUser.getDepartmentname());
                    i = purchaseInvoicePushMapper.addPurchaseInvoicePursh(purchaseInvoicePush);
                }
            }else{
                purchaseInvoicePush.setStatus("2");
                purchaseInvoicePush.setAdduserid(sysUser.getUserid());
                purchaseInvoicePush.setAddusername(sysUser.getName());
                purchaseInvoicePush.setAdddeptid(sysUser.getDepartmentid());
                purchaseInvoicePush.setAdddeptname(sysUser.getDepartmentname());
                i = purchaseInvoicePushMapper.addPurchaseInvoicePursh(purchaseInvoicePush);
            }

            //更新采购发票应付金额
            if(i>0){
                if(null!=purchaseInvoice){
                    BigDecimal taxamount = BigDecimal.ZERO;
                    //发票金额
                    PurchaseInvoiceDetail purchaseInvoiceDetailSum = purchaseInvoiceMapper.getPurchaseInvoiceDetailSum(purchaseInvoicePush.getInvoiceid());
                    if(null != purchaseInvoiceDetailSum){
                        taxamount = null != purchaseInvoiceDetailSum.getTaxamount() ? purchaseInvoiceDetailSum.getTaxamount() : BigDecimal.ZERO;
                    }
                    //冲差单金额
                    PurchaseInvoicePush pushSum = purchaseInvoicePushMapper.getPurchaseInvoicePushInfo(purchaseInvoicePush.getInvoiceid());
                    if(null != pushSum){
                        taxamount = null != pushSum.getAmount() ? taxamount.add(pushSum.getAmount()) : taxamount ;
                    }
                    purchaseInvoice.setInvoiceamount(taxamount);
                    //purchaseInvoice.setRemark("采购发票冲差金额为:"+purchaseInvoicePush.getAmount());
                    purchaseInvoiceMapper.editPurchaseInvoice(purchaseInvoice);
                }
            }
            return i>0;
        }
        return false;
    }

    @Override
    public boolean updatePurchaseInvoicePush(PurchaseInvoicePush purchaseInvoicePush) throws Exception {

        boolean flag = false ;
        PurchaseInvoicePush modifyPush = purchaseInvoicePushMapper.showPurchaseInvoicePushInfo(purchaseInvoicePush.getId());//当前修改的冲差单
        BigDecimal invoiceamount = purchaseInvoicePush.getAmount().subtract(modifyPush.getAmount());
        boolean upFlag = purchaseInvoicePushMapper.updatePurchaseInvoicePushById(purchaseInvoicePush) > 0 ;
        //更新所在发票单据的应付金额
        if(upFlag){
            PurchaseInvoice purchaseInvoice = purchaseInvoiceMapper.getPurchaseInvoiceInfo(modifyPush.getInvoiceid());
            if(null != purchaseInvoice){
                invoiceamount = invoiceamount.add(purchaseInvoice.getInvoiceamount());
                purchaseInvoice.setInvoiceamount(invoiceamount);
                int i = purchaseInvoiceMapper.editPurchaseInvoice(purchaseInvoice);
                if(i > 0){
                    flag = true ;
                }
            }

        }
        return flag;
    }

    @Override
    public PageData showPurchaseInvoicePushList(PageMap pageMap)
            throws Exception {
        String dataSql = getDataAccessRule("t_report_purchase_base", "z");
        if(StringUtils.isNotEmpty(dataSql)){
            dataSql = dataSql.replaceAll("z.brandid","z.brand");
        }
        pageMap.setDataSql(dataSql);
        List<PurchaseInvoicePush> list = purchaseInvoicePushMapper.showPurchaseInvoicePushList(pageMap);
        for(PurchaseInvoicePush purchaseInvoicePush : list){
            BuySupplier buySupplier = getSupplierInfoById(purchaseInvoicePush.getSupplierid());
            if(null!=buySupplier){
                purchaseInvoicePush.setSuppliername(buySupplier.getName());
            }
            Brand brand = getGoodsBrandByID(purchaseInvoicePush.getBrand());
            if(null!=brand){
                purchaseInvoicePush.setBrandname(brand.getName());
            }
            //冲差类型0其他冲差1品牌冲差
            if(StringUtils.isNotEmpty(purchaseInvoicePush.getPushtype())){
                SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(purchaseInvoicePush.getPushtype(), "paypushtype");
                if(null != sysCode){
                    purchaseInvoicePush.setPushtypename(sysCode.getCodename());
                }
            }
            //是否核销1是0否
            if("0".equals(purchaseInvoicePush.getIswriteoff())){
                purchaseInvoicePush.setIswriteoffname("未核销");
            }else if("1".equals(purchaseInvoicePush.getIswriteoff())){
                purchaseInvoicePush.setIswriteoffname("已核销");
            }
            SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(purchaseInvoicePush.getStatus(), "status");
            if(null != sysCode){
                purchaseInvoicePush.setStatusname(sysCode.getCodename());
            }
        }
        PageData pageData = new PageData(purchaseInvoicePushMapper.showPurchaseInvoicePushCount(pageMap),list,pageMap);
        List<PurchaseInvoicePush> footer = new ArrayList<PurchaseInvoicePush>();
        PurchaseInvoicePush purchaseInvoicePush = purchaseInvoicePushMapper.getPurchaseInvoicePushSum(pageMap);
        if(null != purchaseInvoicePush){
            purchaseInvoicePush.setInvoiceid("合计");
        }
        footer.add(purchaseInvoicePush);
        pageData.setFooter(footer);
        return pageData;
    }

    @Override
    public boolean deletePurchaseInvoicePush(String id) throws Exception {
        PurchaseInvoicePush purchaseInvoicePush = purchaseInvoicePushMapper.getPurchaseInvoicePushInfo(id);
        if(purchaseInvoicePush != null && "2".equals(purchaseInvoicePush.getStatus())){
            int i = purchaseInvoicePushMapper.deletePurchaseInvoicePush(id);
            //更新采购发票应付金额
            if(i>0){
                PurchaseInvoice purchaseInvoice = purchaseInvoiceMapper.getPurchaseInvoiceInfo(purchaseInvoicePush.getInvoiceid());
                if(null!=purchaseInvoice){
                    purchaseInvoice.setInvoiceamount(purchaseInvoice.getTaxamount());
                    purchaseInvoice.setRemark("");
                    purchaseInvoiceMapper.editPurchaseInvoice(purchaseInvoice);
                }
            }
            return i>0;
        }
        return false;
    }

    @Override
    public boolean deleteInvoiceBeginDueDetail(String id) throws Exception {
        boolean flag = true;
        List<PurchaseInvoiceDetail> detailList = purchaseInvoiceMapper.getPurchaseInvoiceDetailListByBillAndGoodsid(id,"QC");
        for(PurchaseInvoiceDetail detail : detailList){
            BeginDue beginDue = beginDueService.getBeginDueInfo(detail.getSourceid());
            if(null != beginDue && "3".equals(beginDue.getStatus())){
                Map map = new HashMap();
                map.put("sourcetype","3");
                map.put("billid",id);
                map.put("sourceid",detail.getSourceid());
                List<PurchaseInvoiceDetail> list = purchaseInvoiceMapper.getPurchaseInvoiceDetailSumListBySourceidGroup(map);

                int i = purchaseInvoiceMapper.deletePurchaseInvoiceBySourceid(id,beginDue.getId());
                //更新应付款期初采购发票是否引用标记，采购发票应付金额
                if(i>0){
                    beginDue.setInvoicerefer("0");
                    beginDueService.updateBeginDueInvoicerefer(beginDue);

                    PurchaseInvoiceDetail purchaseInvoiceDetail = null;
                    if(list.size() != 0){
                        purchaseInvoiceDetail = list.get(0);
                    }
                    if(purchaseInvoiceDetail == null){
                        purchaseInvoiceDetail = new PurchaseInvoiceDetail();
                        purchaseInvoiceDetail.setTaxamount(BigDecimal.ZERO);
                        purchaseInvoiceDetail.setNotaxamount(BigDecimal.ZERO);
                    }

                    PurchaseInvoice purchaseInvoice = purchaseInvoiceMapper.getPurchaseInvoiceInfo(id);
                    if(null!=purchaseInvoice){
                        purchaseInvoice.setTaxamount(purchaseInvoice.getTaxamount().subtract(purchaseInvoiceDetail.getTaxamount()));
                        purchaseInvoice.setNotaxamount(purchaseInvoice.getNotaxamount().subtract(purchaseInvoiceDetail.getNotaxamount()));
                        purchaseInvoice.setInvoiceamount(purchaseInvoice.getInvoiceamount().subtract(beginDue.getAmount()));
                        if(StringUtils.isNotEmpty(purchaseInvoice.getSourceid())){
                            String newsourceid = "";
                            String[] souceidArr = purchaseInvoice.getSourceid().split(",");
                            for(String souceid : souceidArr){
                                if(!souceid.equals(detail.getSourceid())){
                                    if(StringUtils.isEmpty(newsourceid)){
                                        newsourceid = souceid;
                                    }else{
                                        newsourceid += "," + souceid;
                                    }
                                }
                            }
                            purchaseInvoice.setSourceid(newsourceid);
                        }
                        purchaseInvoice.setRemark("");
                        purchaseInvoiceMapper.editPurchaseInvoice(purchaseInvoice);
                    }
                }else{
                    throw new Exception("采购发票删除应付款期初失败");
                }
                flag = flag && i>0;
            }
        }
        return flag;
    }

    @Override
    public boolean deletePurchaseInvoicePushDetail(String id, String pushtype,String brand) throws Exception {
        PurchaseInvoicePush purchaseInvoicePush = purchaseInvoicePushMapper.getPurchaseInvoicePushInfoByType(id);
        if(null != purchaseInvoicePush){
            int i = purchaseInvoicePushMapper.deletePurchaseInvoicePushByType(id);
            //更新采购发票应付金额
            if(i>0){
                PurchaseInvoice purchaseInvoice = purchaseInvoiceMapper.getPurchaseInvoiceInfo(purchaseInvoicePush.getInvoiceid());
                if(null!=purchaseInvoice){
                    BigDecimal invoiceamount = purchaseInvoice.getInvoiceamount().subtract(purchaseInvoicePush.getAmount());
                    purchaseInvoice.setInvoiceamount(invoiceamount);
                    purchaseInvoice.setRemark("");
                    purchaseInvoiceMapper.editPurchaseInvoice(purchaseInvoice);
                }
            }
            return i > 0;
        }else{
            return false;
        }
    }
    @Override
    public PageData getPurchaseOrderListData(PageMap pageMap) throws Exception {
        //多表取字段权限 key:表名 value:表别名
//		Map tableMap = new HashMap();
//		tableMap.put("t_purchase_arrivalorder", "t");
//		tableMap.put("t_purchase_returnorder", "t1");
        //给表字段生成别名 key:表名.列名 value:列别名
        //Map columnMap = new HashMap();
        //columnMap.put("t_sys_user.name", "usernameddd");
        //返回列字符串
        //String cols1 = getAccessColumnListByTables(tableMap, columnMap);

        Map condition = pageMap.getCondition();
        String arivalAuthSql = getDataAccessRule("t_purchase_arrivalorder","a");
        condition.put("arivalAuthSql",arivalAuthSql);

        String returnAuthSql = getDataAccessRule("t_purchase_returnorder","b");
        condition.put("returnAuthSql",returnAuthSql);
        List<PurchaseOrder> list = purchaseInvoiceMapper.getPurchaseOrderList(pageMap);
        for(PurchaseOrder purchaseOrder : list){
            //订单类型1采购进货单,2采购退货通知单
            if("1".equals(purchaseOrder.getOrdertype())){
                purchaseOrder.setOrdertypename("采购进货单");
            }
            else if("2".equals(purchaseOrder.getOrdertype())){
                purchaseOrder.setOrdertypename("采购退货通知单");
                purchaseOrder.setTotalamount(purchaseOrder.getTotalamount().multiply(new BigDecimal(-1)).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
            }
            //供应商
            if(StringUtils.isNotEmpty(purchaseOrder.getSupplierid())){
                BuySupplier buySupplier = getSupplierInfoById(purchaseOrder.getSupplierid());
                if(null != buySupplier){
                    purchaseOrder.setSuppliername(buySupplier.getName());
                }
            }
            //对方经手人
            if(StringUtils.isNotEmpty(purchaseOrder.getHandlerid())){
                Contacter contacter = getContacterById(purchaseOrder.getHandlerid());
                if(null != contacter){
                    purchaseOrder.setHandlername(contacter.getName());
                }
            }
            //采购部门
            if(StringUtils.isNotEmpty(purchaseOrder.getBuydeptid())){
                DepartMent departMent = getDepartmentByDeptid(purchaseOrder.getBuydeptid());
                if(null != departMent){
                    purchaseOrder.setBuydeptname(departMent.getName());
                }
            }
            //采购人员
            if(StringUtils.isNotEmpty(purchaseOrder.getBuyuserid())){
                Personnel personnel = getPersonnelById(purchaseOrder.getBuyuserid());
                if(null != personnel){
                    purchaseOrder.setBuyusername(personnel.getName());
                }
            }
        }
        PageData pageData = new PageData(purchaseInvoiceMapper.getPurchaseOrderCount(pageMap),list,pageMap);
        return pageData;
    }

    /**
     * 根据来源编号获取采购发票
     * @param sourceid
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-12-17
     */
    public List getPurchaseInvoiceListBySourceid(String sourceid) throws Exception{
        List<PurchaseInvoice> list = purchaseInvoiceMapper.getPurchaseInvoiceListBySourceid(sourceid);
        if(list.size() != 0){
            for(PurchaseInvoice purchaseInvoice : list){
                BuySupplier buySupplier = getSupplierInfoById(purchaseInvoice.getSupplierid());
                if(buySupplier != null){
                    purchaseInvoice.setSuppliername(buySupplier.getName());
                }
                Contacter contacter = getContacterById(purchaseInvoice.getHandlerid());
                if(null!=contacter){
                    purchaseInvoice.setHandlername(contacter.getName());
                }
                DepartMent departMent = getDepartmentByDeptid(purchaseInvoice.getBuydept());
                if(null!=departMent){
                    purchaseInvoice.setBuydeptname(departMent.getName());
                }
                Personnel personnel = getPersonnelById(purchaseInvoice.getBuyuser());
                if(null!=personnel){
                    purchaseInvoice.setBuyusername(personnel.getName());
                }
            }
        }
        return list;
    }

    @Override
    public PageData getPurchaseInvoiceSourceOfBillList(PageMap pageMap) throws Exception {
        Map condition = pageMap.getCondition();
        String arivalAuthSql = getDataAccessRule("t_purchase_arrivalorder","t");
        condition.put("arivalAuthSql",arivalAuthSql);

        String returnAuthSql = getDataAccessRule("t_purchase_returnorder","t");
        condition.put("returnAuthSql",returnAuthSql);

        String duesql = getDataAccessRule("t_account_begin_due","t");
        condition.put("duesql",duesql);

        List<Map> list = purchaseInvoiceMapper.getPurchaseInvoiceSourceOfBillList(pageMap);
        for(Map map : list){
            //供应商
            String supplierid = (null != map.get("supplierid")) ? (String)map.get("supplierid") : null;
            if(StringUtils.isNotEmpty(supplierid)){
                SupplierCapital supplierCapital = supplierCapitalMapper.getSupplierCapital(supplierid);
                if(null != supplierCapital){
                    map.put("supplieramount",supplierCapital.getAmount());
                }
                BuySupplier buySupplier = getSupplierInfoById(supplierid);
                if(null != buySupplier){
                    map.put("suppliername", buySupplier.getName());
                }
            }
            //对方经手人
            String handlerid = (null != map.get("handlerid")) ? (String)map.get("handlerid") : null;
            if(StringUtils.isNotEmpty(handlerid)){
                Contacter contacter = getContacterById(handlerid);
                if(null != contacter){
                    map.put("contactername", contacter.getName());
                }
            }
            //采购部门
            String buydeptid = (null != map.get("buydeptid")) ? (String)map.get("buydeptid") : null;
            if(StringUtils.isNotEmpty(buydeptid)){
                DepartMent departMent = getDepartmentByDeptid(buydeptid);
                if(null != departMent){
                    map.put("buydeptname", departMent.getName());
                }
            }
            //采购人员
            String buyuserid = (null != map.get("buyuserid")) ? (String)map.get("buyuserid") : null;
            if(StringUtils.isNotEmpty(buyuserid)){
                Personnel personnel = getPersonnelById(buyuserid);
                if(null != personnel){
                    map.put("buyusername", personnel.getName());
                }
            }
        }
        PageData pageData = new PageData(purchaseInvoiceMapper.getPurchaseInvoiceSourceOfBillCount(pageMap),list,pageMap);
        //合计
        List footer = new ArrayList();
        Map dataSum = purchaseInvoiceMapper.getPurchaseInvoiceSourceOfBillSumData(pageMap);
        if(null != dataSum){
            dataSum.put("id", "合计");
            footer.add(dataSum);
        }else{
            dataSum = new HashMap();
            dataSum.put("id", "合计");
        }
        pageData.setFooter(footer);
        return pageData;
    }

    public String[] getBrandList(PurchaseInvoicePush purchaseInvoicePush) throws Exception{

        List<Map> list=purchaseInvoiceMapper.getPurchaseInvoiceBrandList(purchaseInvoicePush.getInvoiceid());
        String[] brand = new String[list.size()];

        for(Map map : list){

        }

        return null;
    }

    @Override
    public List showPurchaseInvoiceListBy(Map map) throws Exception{
        String datasql = getDataAccessRule("t_account_purchase_invoice",null);
        map.put("dataSql", datasql);
        boolean showdetail=false;
        if(null!=map.get("showdetail") && StringUtils.isNotEmpty(map.get("showdetail").toString()) &&"1".equals(map.get("showdetail").toString()) ){
            showdetail=true;
        }

        List<PurchaseInvoice> list = purchaseInvoiceMapper.getPurchaseInvoiceListByMap(map);
        if(list.size() != 0){
            for(PurchaseInvoice purchaseInvoice : list){
                BuySupplier buySupplier = getSupplierInfoById(purchaseInvoice.getSupplierid());
                if(buySupplier != null){
                    purchaseInvoice.setSuppliername(buySupplier.getName());
                }
                Contacter contacter = getContacterById(purchaseInvoice.getHandlerid());
                if(null!=contacter){
                    purchaseInvoice.setHandlername(contacter.getName());
                }
                DepartMent departMent = getDepartmentByDeptid(purchaseInvoice.getBuydept());
                if(null!=departMent){
                    purchaseInvoice.setBuydeptname(departMent.getName());
                }
                Personnel personnel = getPersonnelById(purchaseInvoice.getBuyuser());
                if(null!=personnel){
                    purchaseInvoice.setBuyusername(personnel.getName());
                }

                if(showdetail){
                    List<PurchaseInvoiceDetail> detailList=getPurchaseInvoiceDetailFillInfo(purchaseInvoice.getId());
                    purchaseInvoice.setDetailList(detailList);
                }
            }
        }
        return list;
    }

    @Override
    public boolean updateOrderPrinttimes(PurchaseInvoice purchaseInvoice) throws Exception{
        return purchaseInvoiceMapper.updateOrderPrinttimes(purchaseInvoice)>0;
    }

    @Override
    public boolean updatePurchaseInvoiceVouchertimes(PurchaseInvoice purchaseInvoice) throws Exception {
        return purchaseInvoiceMapper.updatePurchaseInvoiceVouchertimes(purchaseInvoice) >0;
    }

    @Override
    public void updateOrderPrinttimes(List<PurchaseInvoice> list) throws Exception{
        if(null!=list){
            for(PurchaseInvoice item : list){
                purchaseInvoiceMapper.updateOrderPrinttimes(item);
            }
        }
    }

    /**
     * 获取供应商发票总计,生成凭证时，按单据取数据
     * @param idarr
     * @return
     * @throws Exception
     */
    public List<Map> getSupplierPushSumDataForThird(List<String> idarr) throws Exception{
        if(null!=idarr && idarr.size()>0){
            List<Map> list = purchaseInvoiceMapper.getSupplierRejectSumDataForThird(idarr);
            return list;
        }
        return new ArrayList<Map>();
    }

    /**
     * 获取采购发票冲差按品牌的冲差金额
     * @param id
     * @return java.util.List<java.util.Map>
     * @throws
     * @author luoqiang
     * @date Jan 12, 2018
     */
    public List<Map> getPurchaseInvoicePushSumData(String id){
        List<Map> list=purchaseInvoiceMapper.getPurchaseInvoicePushSumData(id);
        return list;
    }
}

