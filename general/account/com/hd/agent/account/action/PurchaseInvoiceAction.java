/**
 * @(#)PurchaseInvoiceAction.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 11, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.account.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.account.model.BeginDue;
import com.hd.agent.account.service.IBeginDueService;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.*;
import com.hd.agent.system.model.SysCode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.account.model.PurchaseInvoice;
import com.hd.agent.account.model.PurchaseInvoiceDetail;
import com.hd.agent.account.model.PurchaseInvoicePush;
import com.hd.agent.account.service.IPurchaseInvoiceService;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.purchase.model.ArrivalOrder;
import com.hd.agent.purchase.model.ArrivalOrderDetail;
import com.hd.agent.purchase.model.ReturnOrder;
import com.hd.agent.purchase.model.ReturnOrderDetail;
import com.hd.agent.purchase.service.IArrivalOrderService;
import com.hd.agent.purchase.service.IReturnOrderService;

/**
 *
 *
 * @author panxiaoxiao
 */
public class PurchaseInvoiceAction extends BaseFilesAction{

    private PurchaseInvoicePush purchaseInvoicePush;

    private IPurchaseInvoiceService purchaseInvoiceService;

    private PurchaseInvoice purchaseInvoice;

    private IArrivalOrderService arrivalOrderService;

    private IReturnOrderService returnOrderService;

    private IBeginDueService beginDueService;

    public PurchaseInvoicePush getPurchaseInvoicePush() {
        return purchaseInvoicePush;
    }

    public void setPurchaseInvoicePush(PurchaseInvoicePush purchaseInvoicePush) {
        this.purchaseInvoicePush = purchaseInvoicePush;
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

    public PurchaseInvoice getPurchaseInvoice() {
        return purchaseInvoice;
    }

    public void setPurchaseInvoice(PurchaseInvoice purchaseInvoice) {
        this.purchaseInvoice = purchaseInvoice;
    }

    public IPurchaseInvoiceService getPurchaseInvoiceService() {
        return purchaseInvoiceService;
    }

    public void setPurchaseInvoiceService(
            IPurchaseInvoiceService purchaseInvoiceService) {
        this.purchaseInvoiceService = purchaseInvoiceService;
    }

    public IBeginDueService getBeginDueService() {
        return beginDueService;
    }

    public void setBeginDueService(IBeginDueService beginDueService) {
        this.beginDueService = beginDueService;
    }

    /**
     * 显示采购发票列表页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 11, 2013
     */
    public String purchaseInvoiceListPage()throws Exception{
        return SUCCESS;
    }

    /**
     * 获取采购发票列表
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 11, 2013
     */
    public String showPurchaseInvoiceList()throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = purchaseInvoiceService.showPurchaseInvoiceList(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 显示采购发票页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 11, 2013
     */
    public String showPurchaseInvoicePage()throws Exception{
        request.setAttribute("type", "add");
        return SUCCESS;
    }

    /**
     * 显示参照上游单据查询页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 11, 2013
     */
    public String showPurchaseInvoiceRelationUpperPage()throws Exception{
        return SUCCESS;
    }

    /**
     * 显示上游单据列表页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 11, 2013
     */
    public String showPurchaseInvoiceSourceListPage()throws Exception{
        //1采购进货单2采购退货通知单
        String sourcetype = request.getParameter("sourcetype");
        request.setAttribute("sourcetype", sourcetype);
        return SUCCESS;
    }

    /**
     * 根据来源单据编码和来源类型生成采购发票
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 13, 2013
     */
    @UserOperateLog(key="PurchaseInvoice",type=2)
    public String addPurchaseInvoiceByRefer()throws Exception{
        String ids = request.getParameter("ids");
        Map map = purchaseInvoiceService.addPurchaseInvoice(ids);
        addJSONObject(map);
        String id = "";
        if(null!=map){
            id = (String) map.get("id");
        }
        addLog("采购发票新增,编号:"+id, map);
        return SUCCESS;
    }

    /**
     * 显示采购发票新增页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 11, 2013
     */
    public String showPurchaseInvoiceAddPage()throws Exception{
        request.setAttribute("type", "add");
        return SUCCESS;
    }

    /**
     * 显示新增页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 15, 2013
     */
    public String purchaseInvoiceAddPage()throws Exception{
        return SUCCESS;
    }

    /**
     * 显示采购发票修改页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 15, 2013
     */
    public String showPurchaseInvoiceEditPage()throws Exception{
        String id = request.getParameter("id");
        request.setAttribute("id", id);
        request.setAttribute("type", "edit");
        Map map = purchaseInvoiceService.getPurchaseInvoiceInfo(id);
        PurchaseInvoice purchaseInvoice = (PurchaseInvoice) map.get("purchaseInvoice");
        if(null != purchaseInvoice){
            request.setAttribute("writeoffdate", purchaseInvoice.getWriteoffdate());
            request.setAttribute("iswriteoff", purchaseInvoice.getIswriteoff());
        }
        return SUCCESS;
    }

    /**
     * 显示采购发票修改页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 11, 2013
     */
    public String purchaseInvoiceEditPage()throws Exception{
        String id = request.getParameter("id");
        Map map = purchaseInvoiceService.getPurchaseInvoiceInfo(id);
        PurchaseInvoice purchaseInvoice = (PurchaseInvoice) map.get("purchaseInvoice");
        List list = (List) map.get("detailList");
        String jsonStr = JSONUtils.listToJsonStr(list);
        List statusList = getBaseSysCodeService().showSysCodeListByType("status");
        request.setAttribute("statusList", statusList);
        request.setAttribute("purchaseInvoice", purchaseInvoice);
        request.setAttribute("detailList", jsonStr);
        request.setAttribute("listSize", list.size());
        if(null!=purchaseInvoice){
            if("1".equals(purchaseInvoice.getStatus()) || "2".equals(purchaseInvoice.getStatus()) || "6".equals(purchaseInvoice.getStatus())){
                return "success";
            }else{
                return "viewSuccess";
            }
        }else{
            return "addSuccess";
        }
    }

    /**
     * 显示采购发票明细列表根据商品编码
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-12-02
     */
    public String showPurchaseInvoiceDetailListByGoodsidPage()throws Exception{
        String billid = request.getParameter("billid");
        String goodsid = request.getParameter("goodsid");
        List list = purchaseInvoiceService.getPurchaseInvoiceDetailListByGoodsid(billid,goodsid);
        String jsonStr = JSONUtils.listToJsonStr(list);
        request.setAttribute("detailListByGoodsid", jsonStr);
        return SUCCESS;
    }

    /**
     * 采购发票修改
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 15, 2013
     */
    @UserOperateLog(key="PurchaseInvoice",type=3)
    public String editPurchaseInvoice()throws Exception{
        String type = request.getParameter("type");
        if("hold".equals(type)){
            purchaseInvoice.setStatus("1");
        }
        String detailJson = request.getParameter("detailJson");
        List detailList = JSONUtils.jsonStrToList(detailJson, new PurchaseInvoiceDetail());
        Map map = purchaseInvoiceService.editPurchaseInVoice(purchaseInvoice,detailList);
        addJSONObject(map);
        addLog("采购发票修改,编号:"+purchaseInvoice.getId(), map);
        return SUCCESS;
    }

    /**
     * 显示采购发票查看页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 11, 2013
     */
    public String showPurchaseInvoiceViewPage()throws Exception{
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        if("handle".equals(type)){
            request.setAttribute("type", type);
        }else{
            request.setAttribute("type", "view");
        }
        request.setAttribute("id", id);
        Map map = purchaseInvoiceService.getPurchaseInvoiceInfo(id);
        PurchaseInvoice purchaseInvoice = (PurchaseInvoice)map.get("purchaseInvoice");
        if(null != purchaseInvoice){
            request.setAttribute("iswriteoff", purchaseInvoice.getIswriteoff());
        }
        return 	SUCCESS;
    }

    /**
     * 显示采购发票详情页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 13, 2013
     */
    public String purchaseInvoiceViewPage()throws Exception{
        String id = request.getParameter("id");
        Map map = purchaseInvoiceService.getPurchaseInvoiceInfo(id);
        PurchaseInvoice purchaseInvoice = (PurchaseInvoice)map.get("purchaseInvoice");
        if(null != purchaseInvoice){
            List list = (List) map.get("detailList");
            String jsonStr = JSONUtils.listToJsonStr(list);
            List statusList = getBaseSysCodeService().showSysCodeListByType("status");
            request.setAttribute("statusList", statusList);
            request.setAttribute("purchaseInvoice", purchaseInvoice);
            request.setAttribute("detailList", jsonStr);
            request.setAttribute("listSize", list.size());
            return "success";
        }
        else{
            return "addSuccess";
        }
    }

    /**
     * 显示采购发票明细修改页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 15, 2013
     */
    public String showPurcharseInvoiceDetailEditPage()throws Exception{
        return SUCCESS;
    }

    /**
     * 采购发票删除
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 15, 2013
     */
    @UserOperateLog(key="PurchaseInvoice",type=4)
    public String deletePurchaseInvoice()throws Exception{
        String id = request.getParameter("id");
        boolean flag = purchaseInvoiceService.deletePurchaseInvoice(id);
        addJSONObject("flag", flag);
        addLog("采购发票删除,编号:"+id, flag);
        return SUCCESS;
    }

    /**
     * 显示采购发票明细修改
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 15, 2013
     */
    public String showPurchaseInvoiceDetailEditPage()throws Exception{
        String type = request.getParameter("type");
        request.setAttribute("type", type);
        request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
        return SUCCESS;
    }

    /**
     * 审核采购发票
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 15, 2013
     */
    @UserOperateLog(key="PurchaseInvoice",type=3)
    public String auditPurchaseInvoice()throws Exception{
        String id = request.getParameter("id");
        boolean flag = purchaseInvoiceService.auditPurchaseInvoice(id);
        addJSONObject("flag", flag);
        addLog("采购发票审核,编号:"+id, flag);
        return SUCCESS;
    }

    /**
     * 反审采购发票
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 15, 2013
     */
    @UserOperateLog(key="PurchaseInvoice",type=3)
    public String oppauditPurchaseInvoice()throws Exception{
        String id = request.getParameter("id");
        boolean flag = purchaseInvoiceService.oppauditPurchaseInvoice(id);
        addJSONObject("flag", flag);
        addLog("采购发票反审,编号:"+id, flag);
        return SUCCESS;
    }
    /**
     * 采购发票提交工作流
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jul 26, 2013
     */
    public String submitPurchaseInvoicePageProcess() throws Exception{
        String id = request.getParameter("id");
        boolean flag = purchaseInvoiceService.submitPurchaseInvoicePageProcess(id);
        addJSONObject("flag", flag);
        return "success";
    }

    /**
     * 显示上游单据
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Aug 31, 2013
     */
    public String showPurchaseInvoiceSourceListReferPage() throws Exception{
        //String sourcetype = request.getParameter("sourcetype");
        String sourceid = request.getParameter("sourceid");
        List list = purchaseInvoiceService.showPurchaseInvoiceSourceListReferData(sourceid, "");
        String jsonStr = JSONUtils.listToJsonStr(list);
        request.setAttribute("list", jsonStr);
        //request.setAttribute("sourcetype", sourcetype);
        return "success";
    }

    /**
     * 主单位数量改变的级联变化
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 10, 2013
     */
    public String getUnitnumChange()throws Exception{
        String type = request.getParameter("type"); //1含税单价改变2无税单价改变
        String goodsId = request.getParameter("id"); //商品编码
        String billid = request.getParameter("billid");//采购发票编码
        String sourceid = request.getParameter("sourceid");
        String sourcedetailid = request.getParameter("sourcedetailid");
        String unitnum = StringUtils.isEmpty(request.getParameter("unitnum")) ? "0" : request.getParameter("unitnum"); //主计量单位数量;
        String auxunitname = "",unitname="",auxStr = "",numStr = "",auxnumdetail = "";//辅单位名称
        //String tp = StringUtils.isEmpty(request.getParameter("tp")) ? "0" : request.getParameter("tp"); //改变后的含税单价
        //String notp = StringUtils.isEmpty(request.getParameter("notp")) ? "0" : request.getParameter("notp"); //改变后的未税单价
        //String auxnum = StringUtils.isEmpty(request.getParameter("auxnum")) ? "0" : request.getParameter("auxnum"); //辅单位数量;
        String tax = request.getParameter("taxtype"); //税种
        TaxType taxType = getTaxType(tax);
        BigDecimal bNum = new BigDecimal(unitnum);
        BigDecimal bAuxNum = new BigDecimal(0);
        BigDecimal bTaxPrice = new BigDecimal(0); //含税单价
        BigDecimal bNoTaxPrice = new BigDecimal(0); //无税单价
        BigDecimal bTaxAmount = new BigDecimal(0); //含税金额
        BigDecimal bNoTaxAmount = new BigDecimal(0); //无税金额
        BigDecimal bTax = new BigDecimal(0); //税额
        //计算辅单位数量
        List<GoodsInfo_MteringUnitInfo> muList = getBaseGoodsService().getMUListByGoodsId(goodsId);
        if(muList.size() != 0){
            for(GoodsInfo_MteringUnitInfo goodsMUInfo : muList){
                if("1".equals(goodsMUInfo.getIsdefault())){
                    Map auxMap = countGoodsInfoNumber(goodsId,goodsMUInfo.getMeteringunitid(),bNum);
                    bAuxNum = (BigDecimal) auxMap.get("auxnum");
                    auxnumdetail = (String) auxMap.get("auxnumdetail");
                }
            }
        }
        //根据来源单据编号，来源单据明细及发票编号，获取发票明细
        PurchaseInvoiceDetail purchaseInvoiceDetail = purchaseInvoiceService.getPurchaseInvoiceDetailInfoBySource(sourceid,billid,sourcedetailid);
        if(null != purchaseInvoiceDetail){
            bTaxPrice = (null != purchaseInvoiceDetail.getTaxprice()) ? purchaseInvoiceDetail.getTaxprice():new BigDecimal(0);
            bNoTaxPrice = (null != purchaseInvoiceDetail.getNotaxprice()) ? purchaseInvoiceDetail.getNotaxprice():new BigDecimal(0);
            auxunitname = (null != purchaseInvoiceDetail.getAuxunitname() && StringUtils.isNotEmpty(auxStr)) ? purchaseInvoiceDetail.getAuxunitname() : "";
            unitname = (null != purchaseInvoiceDetail.getUnitname() && StringUtils.isNotEmpty(numStr)) ? purchaseInvoiceDetail.getUnitname() : "";
        }
        //含税金额 = 含税单价*主数量
        bTaxAmount = bTaxPrice.multiply(bNum);
        //bTaxAmount = "1".equals(type)? bTaxAmount : (bTaxAmount.multiply(new BigDecimal(-1)));
        //未税金额 = 未税单价*主数量
        bNoTaxAmount = bNoTaxPrice.multiply(bNum);
        //bNoTaxAmount = "1".equals(type)? bNoTaxAmount : (bNoTaxAmount.multiply(new BigDecimal(-1)));
        //税额 = 含税金额-未税金额
        bTax = bTaxAmount.subtract(bNoTaxAmount);

        Map map = new HashMap();
        map.put("auxnum", bAuxNum);
        map.put("auxunitname", auxunitname);
        map.put("auxnumdetail", auxnumdetail);
        map.put("taxAmount", bTaxAmount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
        map.put("noTaxAmount", bNoTaxAmount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
        map.put("tax", bTax.setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
        map.put("type", type);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 验证主数量
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 11, 2013
     */
    public String checkUnitNum()throws Exception{
        String unitNum = request.getParameter("unitnum");
        String goodsid = request.getParameter("goodsid");
        String sourceid = request.getParameter("sourceid");
        String billid = request.getParameter("billid");
        String sourcedetailid = request.getParameter("sourcedetailid");
//		String billsourceid = request.getParameter("sourceid");
//		String[] sourceidArr;
//		if(orderid.indexOf(",") != -1){
//			sourceidArr = orderid.split(",");
//		}
//		else{
//			String str = orderid + ",";
//			sourceidArr = str.split(",");
//		}
        boolean flag = false;
        BigDecimal oldUnitNum = new BigDecimal(0);
        //根据来源单据编号，来源单据明细及发票编号，获取发票明细
        PurchaseInvoiceDetail purchaseInvoiceDetail = purchaseInvoiceService.getPurchaseInvoiceDetailInfoBySource(sourceid,billid, sourcedetailid);
        if(null != purchaseInvoiceDetail){
            //if(sourceidArr.length != 0){
            //	for(String sourceid : sourceidArr){
            if("1".equals(purchaseInvoiceDetail.getSourcetype())){
                //原先主数量
                oldUnitNum = (null != purchaseInvoiceDetail.getUnitnum()) ? purchaseInvoiceDetail.getUnitnum() : new BigDecimal(0);
                ArrivalOrderDetail arrivalOrderDetail = arrivalOrderService.showPureArrivalOrderDetail(sourcedetailid);
                if(null != arrivalOrderDetail ) {
                    if (unitNum != null) {
                        //输入的数量
                        BigDecimal bUnitNum = new BigDecimal(unitNum);
                        //未开票数量
                        BigDecimal uninvoicenum = (null != arrivalOrderDetail.getUninvoicenum()) ? arrivalOrderDetail.getUninvoicenum() : new BigDecimal(0);
                        //已开票数量
                        //BigDecimal invoicenum = (null != arrivalOrderDetail.getInvoicenum()) ? arrivalOrderDetail.getInvoicenum() : new BigDecimal(0);
                        if("1".equals(arrivalOrderDetail.getInvoicerefer())){
                           uninvoicenum=BigDecimal.ZERO;
                        }
                        //输入的数量-原先主数量+未开票数量 < 0
                        int ret = bUnitNum.subtract(oldUnitNum.add(uninvoicenum)).compareTo(BigDecimal.ZERO);
                        if (ret == -1 || ret == 0) {
                            flag = true;
                        }
                    }else{
                        flag = true;
                    }
                }
            }
            else if("2".equals(purchaseInvoiceDetail.getSourcetype())){
                oldUnitNum = (null != purchaseInvoiceDetail.getUnitnum()) ? purchaseInvoiceDetail.getUnitnum().negate() : new BigDecimal(0);
                ReturnOrderDetail returnOrderDetail = returnOrderService.showPureReturnOrderDetail(sourcedetailid);
                BigDecimal bUnitNum = null;
                if(null != returnOrderDetail){
                    //输入的数量
                    if(!"-".equals(unitNum)){
                        bUnitNum = new BigDecimal(unitNum);
                        if(bUnitNum.compareTo(BigDecimal.ZERO) == -1){
                            bUnitNum = bUnitNum.negate();
                        }
                        //未开票数量
                        BigDecimal uninvoicenum = (null != returnOrderDetail.getUninvoicenum()) ? returnOrderDetail.getUninvoicenum() : new BigDecimal(0);
                        //已开票数量
                        //BigDecimal invoicenum = (null != returnOrderDetail.getInvoicenum()) ? returnOrderDetail.getInvoicenum() : new BigDecimal(0);
                        if("1".equals(returnOrderDetail.getInvoicerefer())){
                            uninvoicenum=BigDecimal.ZERO;
                        }
                        //输入的数量-原先主数量+未开票数量 < 0
                        int ret = bUnitNum.subtract(oldUnitNum.add(uninvoicenum)).compareTo(BigDecimal.ZERO);
                        if(ret == -1 || ret == 0){
                            flag = true;
                        }
                    }else{
                        flag = true;
                    }
                }
            }
        }
        //}
        //}
        addJSONObject("flag", flag);
        return SUCCESS;
    }
    /**
     * 显示采购发票应付款冲差页面
     * @return
     * @throws Exception
     * @author chenwei
     * @date Oct 17, 2013
     */
    public String showPurchaseInvoicePushPage() throws Exception{
        String id = request.getParameter("id");
        String pushid = request.getParameter("pushid");
        String pushtype = request.getParameter("pushtype");
        PurchaseInvoicePush purchaseInvoicePush = new PurchaseInvoicePush();
        String date = CommonUtils.getTodayDataStr();
        if(null == pushtype){
            int count = purchaseInvoiceService.countPurchasePush(id);
            if(count > 0){
                purchaseInvoicePush = new PurchaseInvoicePush();
            }else {
                purchaseInvoicePush = null;
            }
        }else{
            purchaseInvoicePush = purchaseInvoiceService.getPurchaseInvoicePushInfo(id);
        }
        if(null != purchaseInvoicePush){
            request.setAttribute("purchaseInvoicePush", purchaseInvoicePush);
            if(StringUtils.isNotEmpty(purchaseInvoicePush.getBusinessdate())){
                date = purchaseInvoicePush.getBusinessdate();
            }
        }
        PurchaseInvoice purchaseInvoice = purchaseInvoiceService.showPurchaseInvoiceInfo(id);
        if(null!=purchaseInvoice){
            request.setAttribute("purchaseInvoice", purchaseInvoice);
        }
        //request.setAttribute("brandid", brandid);
        request.setAttribute("id", id);
        request.setAttribute("date", date);
        return "success";
    }

    /**
     * 显示冲差单修改界面
     * @return
     * @throws Exception
     */
    public String showPurchaseInvoicePushEditPage() throws Exception{
        String id = request.getParameter("id");
        PurchaseInvoicePush purchaseInvoicePush = purchaseInvoiceService.getPurchaseInvoicePushInfo(id);
        String date = CommonUtils.getTodayDataStr();
        if(null!=purchaseInvoicePush){
            request.setAttribute("purchaseInvoicePush", purchaseInvoicePush);
            date = purchaseInvoicePush.getBusinessdate();
        }
        PurchaseInvoice purchaseInvoice = purchaseInvoiceService.showPurchaseInvoiceInfo(purchaseInvoicePush.getInvoiceid());
        if(null!=purchaseInvoice){
            request.setAttribute("purchaseInvoice", purchaseInvoice);
            request.setAttribute("id", purchaseInvoice.getId());
        }
        //request.setAttribute("brandid", brandid);
        request.setAttribute("date", date);

        return SUCCESS;
    }

    public String UpdatePurchaseInvoicePush() throws  Exception {

        boolean flag = purchaseInvoiceService.updatePurchaseInvoicePush(purchaseInvoicePush);
        addJSONObject("flag", flag);
        addLog("采购发票冲差,发票号:"+purchaseInvoicePush.getInvoiceid(),flag);

        return SUCCESS;
    }
    /**
     * 添加或者修改采购发票应付款冲差
     * @return
     * @throws Exception
     * @author chenwei
     * @date Oct 17, 2013
     */
    @UserOperateLog(key="PurchaseInvoice",type=2)
    public String addOrUpdatePurchaseInvoicePush() throws Exception{
        boolean flag = purchaseInvoiceService.addOrUpdatePurchaseInvoicePush(purchaseInvoicePush);
        addJSONObject("flag", flag);
        addLog("采购发票冲差,发票号:"+purchaseInvoicePush.getInvoiceid(),flag);
        return "success";
    }
    /**
     * 显示采购发票冲差单列表页面
     * @return
     * @throws Exception
     * @author chenwei
     * @date Oct 17, 2013
     */
    public String showPurchaseInvoicePushListPage() throws Exception{
        return "success";
    }
    /**
     * 获取采购发票冲差单列表数据
     * @return
     * @throws Exception
     * @author chenwei
     * @date Oct 17, 2013
     */
    public String showPurchaseInvoicePushList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = purchaseInvoiceService.showPurchaseInvoicePushList(pageMap);
        addJSONObjectWithFooter(pageData);
        return "success";
    }

    /**
     * 导出采购发票冲差列表数据
     * @throws Exception
     * @author panxiaoxiao
     * @date Mar 10, 2014
     */
    public void exportPurchaseInvoicePushData()throws Exception{
        String groupcols = null;
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isflag", "true");
        pageMap.setCondition(map);
        String title = "";
        if(map.containsKey("excelTitle")){
            title = map.get("excelTitle").toString();
        }
        else{
            title = "list";
        }
        if(StringUtils.isEmpty(title)){
            title = "list";
        }
        //数据转换，list转化符合excel导出的数据格式
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("invoiceid", "采购发票号");
        firstMap.put("businessdate", "业务日期");
        firstMap.put("supplierid", "供应商编码");
        firstMap.put("suppliername", "供应商名称");
        firstMap.put("pushtypename", "冲差类型");
        firstMap.put("brandname", "品牌名称");
        firstMap.put("amount", "冲差金额");
        firstMap.put("iswriteoffname", "核销状态");
        firstMap.put("statusname", "状态");
        firstMap.put("remark", "备注");
        result.add(firstMap);

        PageData pageData = purchaseInvoiceService.showPurchaseInvoicePushList(pageMap);

        List<PurchaseInvoicePush> list = new ArrayList<PurchaseInvoicePush>();
        list.addAll(pageData.getList());
        list.addAll(pageData.getFooter());

        for(PurchaseInvoicePush purchaseInvoicePush : list){
            if(null != purchaseInvoicePush){
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2 = PropertyUtils.describe(purchaseInvoicePush);
                for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
                    if(map2.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
                        for(Map.Entry<String, Object> entry : map2.entrySet()){
                            if(fentry.getKey().equals(entry.getKey())){
                                objectCastToRetMap(retMap,entry);
                            }
                        }
                    }
                    else{
                        retMap.put(fentry.getKey(), "");
                    }
                }
                result.add(retMap);
            }
        }
        ExcelUtils.exportExcel(result, title);
    }

    /**
     * 删除采购发票冲差
     * @return
     * @throws Exception
     * @author chenwei
     * @date Oct 17, 2013
     */
    @UserOperateLog(key="PurchaseInvoice",type=4)
    public String deletePurchaseInvoicePush() throws Exception{
        String id = request.getParameter("id");
        boolean flag = purchaseInvoiceService.deletePurchaseInvoicePush(id);
        addJSONObject("flag", flag);
        addLog("采购发票冲差删除,编号:"+id,flag);
        return "success";
    }

    /**
     * 删除指定条件的采购发票冲差单
     * @author lin_xx
     * @date 2016-5-11
     * @return
     * @throws Exception
     */
    @UserOperateLog(key="PurchaseInvoice",type=4)
    public String deleteInvoicePushDetail() throws Exception {
        String id = request.getParameter("id");
        String pushtype = request.getParameter("pushtype");
        String brand = request.getParameter("brand");
        boolean flag = purchaseInvoiceService.deletePurchaseInvoicePushDetail(id,pushtype,brand);
        addJSONObject("flag", flag);
        addLog("采购发票冲差删除,编号:"+id,flag);
        return SUCCESS ;
    }

    /**
     * 删除采购发票应付款期初
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-10-10
     */
    @UserOperateLog(key="PurchaseInvoice",type=4)
    public String deleteInvoiceBeginDueDetail()throws Exception{
        String id = request.getParameter("id");
        boolean flag = purchaseInvoiceService.deleteInvoiceBeginDueDetail(id);
        addJSONObject("flag", flag);
        addLog("采购发票应付款期初删除,编号:"+id,flag);
        return SUCCESS;
    }

    //获取采购进货单和采购退货通知单的所有数据
    public String getPurchaseOrderList()throws Exception{
        Map map=CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData  pageData = purchaseInvoiceService.getPurchaseOrderListData(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

    /**
     * 获取采购进货单详情和采购退货通知单详情
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Nov 9, 2013
     */
    public String getPurchaseOrderDetailList()throws Exception{
        String aorderidarrs = request.getParameter("aorderidarrs");
        String rorderidarrs = request.getParameter("rorderidarrs");
        List list = new ArrayList();
        List list1=arrivalOrderService.showArrivalOrderDetailDownReferList(aorderidarrs);
        list.addAll(list1);
        Map map=new HashMap();
        map.put("orderidarrs", rorderidarrs);
        map.put("ischeckauth", "0");
        map.put("notinvoicerefer", "1");
        List list2=returnOrderService.showReturnOrderDetailListBy(map);
        list.addAll(list2);
        addJSONArray(list);
        return SUCCESS;
    }

    /**
     * 根据发票号或者发票单据编号 获取销售发票列表
     * @return
     * @throws Exception
     * @author chenwei
     * @date Nov 20, 2013
     */
    public String showPurchaseInvoiceListPageBySourceid() throws Exception{
        String sourceid = request.getParameter("sourceid");
        List list = purchaseInvoiceService.getPurchaseInvoiceListBySourceid(sourceid);
        if(null!=list){
            String jsonStr = JSONUtils.listToJsonStr(list);
            request.setAttribute("dataList", jsonStr);
        }
        return SUCCESS;
    }

    /**
     * @throws Exception
     * @author lin_xx
     * @date 2016-4-12
     */
    public void exportPurchaseInvoicePushPage() throws Exception{

        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isflag", "true");
        pageMap.setCondition(map);
        String title = "";
        if(map.containsKey("excelTitle")){
            title = map.get("excelTitle").toString();
        }
        else{
            title = "list";
        }
        if(StringUtils.isEmpty(title)){
            title = "list";
        }
        //数据转换，list转化符合excel导出的数据格式
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("id", "编号");
        firstMap.put("businessdate", "业务日期");
        firstMap.put("invoiceno", "发票号");
        firstMap.put("supplierid", "供应商编码");
        firstMap.put("suppliername", "供应商名称");
        firstMap.put("buydeptname","采购部门");
        firstMap.put("buyusername","采购员");
//        firstMap.put("sourceid","来源单据编号");
//        firstMap.put("taxamount","含税总金额");
//        firstMap.put("invoiceamount","应付总金额");
//        firstMap.put("writeoffamount","核销总金额");
//        firstMap.put("tailamount","尾差金额");
        firstMap.put("goodsid","商品编码");
        firstMap.put("goodsname","商品名称");
        firstMap.put("barcode","条形码");
        firstMap.put("unitnum","数量");
        firstMap.put("unitname","单位");
        firstMap.put("auxnumdetail","辅数量");
        firstMap.put("taxprice","单价");
        firstMap.put("goodstaxamount","金额");
        firstMap.put("status", "状态");
        firstMap.put("remark", "备注");
        result.add(firstMap);

        PageData pageData = purchaseInvoiceService.showPurchaseInvoiceList(pageMap);
        List<PurchaseInvoice> list = pageData.getList();
        List<PurchaseInvoice> allList = new ArrayList<PurchaseInvoice>();
        for(PurchaseInvoice purchaseInvoice : list){
            if(null != purchaseInvoice){
                Map map1 = purchaseInvoiceService.getPurchaseInvoiceInfo(purchaseInvoice.getId());
                List<PurchaseInvoiceDetail> detailList = (List) map1.get("detailList");
                for(PurchaseInvoiceDetail detail : detailList){
                    PurchaseInvoice addInvoice = new PurchaseInvoice();
                    addInvoice.setId(purchaseInvoice.getId());
                    addInvoice.setBusinessdate(purchaseInvoice.getBusinessdate());
                    addInvoice.setInvoiceno(purchaseInvoice.getInvoiceno());
                    addInvoice.setSupplierid(purchaseInvoice.getSupplierid());
                    addInvoice.setSuppliername(purchaseInvoice.getSuppliername());
                    addInvoice.setBuydeptname(purchaseInvoice.getBuydeptname());
                    addInvoice.setBuyusername(purchaseInvoice.getBuyusername());
//                    addInvoice.setSourceid(purchaseInvoice.getSourceid());
//                    addInvoice.setTaxamount(purchaseInvoice.getTaxamount());
//                    addInvoice.setInvoiceamount(purchaseInvoice.getInvoiceamount());
//                    addInvoice.setWriteoffamount(purchaseInvoice.getWriteoffamount());
//                    addInvoice.setTailamount(purchaseInvoice.getTailamount());
                    addInvoice.setGoodsid(detail.getGoodsid());
                    addInvoice.setGoodsname(detail.getGoodsname());
                    addInvoice.setGoodstaxamount(detail.getTaxamount());
                    addInvoice.setBarcode(detail.getBarcode());
                    addInvoice.setUnitnum(detail.getUnitnum());
                    addInvoice.setUnitname(detail.getUnitname());
                    GoodsInfo goodsInfo = getGoodsInfoByID(detail.getGoodsid());
                    if(null != goodsInfo){
                        //根据数量计算辅数量
                        BigDecimal boxnum = goodsInfo.getBoxnum();
                        if(null != boxnum){
                            int mainnum = detail.getUnitnum().intValue() / boxnum.intValue();
                            int remainnum = detail.getUnitnum().intValue() % boxnum.intValue();
                            addInvoice.setAuxnumdetail(mainnum+goodsInfo.getAuxunitname()+remainnum + goodsInfo.getMainunitName());
                        }else{
                            addInvoice.setAuxnumdetail(detail.getAuxnumdetail());
                        }
                    }

                    addInvoice.setTaxprice(detail.getTaxprice());
                    addInvoice.setRemark(purchaseInvoice.getRemark());
                    SysCode code = getBaseSysCodeService().showSysCodeInfo(purchaseInvoice.getStatus(),"status");
                    addInvoice.setStatus(code.getCodename());
                    allList.add(addInvoice);
                }
            }
        }
        for(PurchaseInvoice PurchaseInvoice : allList){
            if(null != PurchaseInvoice){
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2 = PropertyUtils.describe(PurchaseInvoice);
                for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
                    if(map2.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
                        for(Map.Entry<String, Object> entry : map2.entrySet()){
                            if(fentry.getKey().equals(entry.getKey())){
                                objectCastToRetMap(retMap,entry);
                            }
                        }
                    }else{
                        retMap.put(fentry.getKey(), "");
                    }
                }
                result.add(retMap);
            }
        }
        ExcelUtils.exportExcel(result, title);
    }
	
	
	/*---------------------anotherway-新增采购发票另一种方式--------------------------*/
    /**
     * 采购发票新增页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Nov 19, 2014
     */
    public String showPurchaseInvoiceAnotherAddPage()throws Exception{
        return SUCCESS;
    }

    /**
     * 获取可生成采购发票所有来源单据列表数据
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Nov 19, 2014
     */
    public String getPurchaseInvoiceSourceOfBillList()throws Exception{
        Map map=CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = purchaseInvoiceService.getPurchaseInvoiceSourceOfBillList(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 根据单据编码及单据类型获取其单据明细列表数据
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Nov 19, 2014
     */
    public String showPurchaseInvoiceSourceBillDetailListPage()throws Exception{
        String billid = request.getParameter("billid");
        String ordertype = request.getParameter("ordertype");
        List list = new ArrayList();
        BigDecimal totalamount = BigDecimal.ZERO;
        BigDecimal totalunitnum = BigDecimal.ZERO;
        if("1".equals(ordertype)){//采购进货单
            ArrivalOrder arrivalOrder = arrivalOrderService.getArrivalOrder(billid);
            if(null != arrivalOrder){
                List<ArrivalOrderDetail> list1 = arrivalOrderService.showArrivalOrderDetailDownReferList(billid);
                if(null != list1 && list1.size() != 0){
                    for(ArrivalOrderDetail detail : list1){
                        if("0".equals(detail.getInvoicerefer())){//判断该明细是否已完全开票
                            totalamount = totalamount.add(detail.getTaxamount());
                            totalunitnum = totalunitnum.add(detail.getUnitnum());
                            list.add(detail);
                        }
                    }
                }
            }
        }else if("2".equals(ordertype)){//采购退货通知单
            ReturnOrder returnOrder = returnOrderService.getReturnOrder(billid);
            if(null != returnOrder){
                Map map = new HashMap();
                map.put("orderidarrs", billid);
                map.put("ischeckauth", "0");
                map.put("notinvoicerefer", "1");
                List<ReturnOrderDetail> list1 = returnOrderService.showReturnOrderDetailListBy(map);
                if(null != list1 && list1.size() != 0){
                    for(ReturnOrderDetail detail : list1){
                        if("0".equals(detail.getInvoicerefer())){//判断该明细是否已完全开票
                            totalamount = totalamount.add(detail.getTaxamount());
                            totalunitnum = totalunitnum.add(detail.getUnitnum());
                            list.add(detail);
                        }
                    }
                }
            }
        }
        String jsonStr = JSONUtils.listToJsonStr(list);
        request.setAttribute("detailList", jsonStr);
        request.setAttribute("totalamount", totalamount);
        request.setAttribute("totalunitnum", totalunitnum);
        request.setAttribute("id", billid);
        return SUCCESS;
    }

    /**
     * 获取采购进货单和采购退货通知单明细合计列表
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Nov 20, 2014
     */
    public String showArrivalAndReturnBillDetailList()throws Exception{
        String ids = request.getParameter("id");
        String supplierid = request.getParameter("supplierid");
        String suppliername = request.getParameter("suppliername");
        //未选中的单据与明细
        String uncheckedjson = request.getParameter("uncheckedjson");
        Map unCheckMap = new HashMap();
        if(StringUtils.isNotEmpty(uncheckedjson)){
            JSONArray array = JSONArray.fromObject(uncheckedjson);
            for(int i=0; i<array.size(); i++){
                JSONObject jsonObject = (JSONObject)array.get(i);
                if(!jsonObject.isEmpty()){
                    String detailid = jsonObject.getString("id");
                    String orderid = jsonObject.getString("orderid");
                    if(!unCheckMap.containsKey(detailid)){
                        Map billMap = new HashMap();
                        billMap.put(orderid, orderid);
                        unCheckMap.put(detailid, billMap);
                    }else{
                        Map billMap = (Map) unCheckMap.get(detailid);
                        billMap.put(orderid, orderid);
                        unCheckMap.put(detailid, billMap);
                    }
                }
            }
        }
        if(StringUtils.isNotEmpty(ids)){
            String[] idArr = ids.split(",");
            List list = new ArrayList();
            BigDecimal totalamount = BigDecimal.ZERO;
            for(String id : idArr){
                ArrivalOrder arrivalOrder = arrivalOrderService.getArrivalOrder(id);
                if(null != arrivalOrder){
                    List<ArrivalOrderDetail> detailList = arrivalOrderService.showArrivalOrderDetailDownReferList(id);
                    for(ArrivalOrderDetail detail : detailList){
                        Map map = BeanUtils.describe(detail);
                        map.put("ordertype", "1");
                        map.put("goodsInfo", detail.getGoodsInfo());
                        map.put("taxamount", detail.getTaxamount());
                        map.put("notaxamount", detail.getNotaxamount());
                        map.put("tax", detail.getTax());
                        map.put("unitnum", detail.getUnitnum());
                        Map auxMap = countGoodsInfoNumber(detail.getGoodsid(), detail.getAuxunitid(), detail.getUnitnum());
                        String auxnumdetail = (String) auxMap.get("auxnumdetail");
                        map.put("auxnumdetail", auxnumdetail);
                        String invoicerefer = (String) map.get("invoicerefer");
                        if("0".equals(invoicerefer)){//判断该明细是否已完全开票
                            boolean addflag = true;
                            if(unCheckMap.containsKey(detail.getId().toString())){
                                Map billMap = (Map) unCheckMap.get(detail.getId().toString());
                                if(billMap.containsKey(detail.getOrderid())){
                                    addflag = false;
                                }
                            }
                            if(addflag){
                                list.add(map);
                                totalamount = totalamount.add(detail.getTaxamount());
                            }
                        }
                    }
                }else{
                    ReturnOrder returnOrder = returnOrderService.getReturnOrder(id);
                    if(null != returnOrder){
                        Map map2 = new HashMap();
                        map2.put("orderidarrs", id);
                        map2.put("ischeckauth", "0");
                        map2.put("notinvoicerefer", "1");
                        List<ReturnOrderDetail> detailList = returnOrderService.showReturnOrderDetailListBy(map2);
                        for(ReturnOrderDetail detail : detailList){
                            Map map = BeanUtils.describe(detail);
                            map.put("ordertype", "2");
                            map.put("goodsInfo", detail.getGoodsInfo());
                            map.put("taxamount", detail.getTaxamount());
                            map.put("notaxamount", detail.getNotaxamount());
                            map.put("tax", detail.getTax());
                            map.put("unitnum", detail.getUnitnum());
                            Map auxMap = countGoodsInfoNumber(detail.getGoodsid(),detail.getAuxunitid(), detail.getUnitnum().negate());
                            String auxnumdetail = (String) auxMap.get("auxnumdetail");
                            map.put("auxnumdetail", auxnumdetail);
                            String invoicerefer = (String) map.get("invoicerefer");
                            if("0".equals(invoicerefer)){//判断该明细是否已完全开票
                                boolean addflag = true;
                                if(unCheckMap.containsKey(detail.getId().toString())){
                                    Map billMap = (Map) unCheckMap.get(detail.getId().toString());
                                    if(billMap.containsKey(detail.getOrderid())){
                                        addflag = false;
                                    }
                                }
                                if(addflag){
                                    list.add(map);
                                    totalamount = totalamount.add(detail.getTaxamount());
                                }
                            }
                        }
                    }else{
                        BeginDue beginDue = beginDueService.getBeginDueInfo(id);
                        if(null != beginDue && "3".equals(beginDue.getStatus())){
                            Map map = new HashMap();
                            map.put("id", beginDue.getId());
                            map.put("orderid", beginDue.getId());
                            map.put("ordertype", "3");
                            GoodsInfo goodsInfo = new GoodsInfo();
                            map.put("goodsid", "QC");
                            goodsInfo.setId("");
                            goodsInfo.setName("供应商应付款期初");
                            map.put("goodsInfo", goodsInfo);
                            map.put("taxamount", beginDue.getAmount());
                            map.put("notaxamount", BigDecimal.ZERO);
                            map.put("isdiscount", "0");
                            map.put("auxnumdetail", "");
                            list.add(map);
                            totalamount = totalamount.add(beginDue.getAmount());
                        }
                    }
                }
            }
            request.setAttribute("list", list);
            request.setAttribute("totalamount", totalamount);
            request.setAttribute("suppliername", suppliername);
            request.setAttribute("supplierid", supplierid);

            request.setAttribute("pattern", CommonUtils.getFormatNumberType());
        }
        return SUCCESS;
    }

    /**
     * 根据供应商编码显示采购发票列表页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Nov 21, 2014
     */
    public String showPurchaseInvoiceListBySupplierPage()throws Exception{
        String supplierid = request.getParameter("supplierid");
        List list = purchaseInvoiceService.getPurchaseInvoiceListBySupplierid(supplierid);
        String jsonStr = JSONUtils.listToJsonStr(list);
        request.setAttribute("dataList", jsonStr);
        return SUCCESS;
    }

    /**
     * 根据商品编码,主单位数量,辅单位编码
     * 计算得到金额,辅单位数量,辅单位数量描述信息等
     * unitamount:金额，auxnum: 辅单位数量,auxnumDetail:辅单位数量描述信息
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-12-04
     */
    public String countGoodsInfoNumber()throws Exception{
        String goodsid = request.getParameter("goodsid");
        String priceStr = request.getParameter("price");
        String unitNumStr = request.getParameter("unitnum");
        String auxunitid = "";
        GoodsInfo_MteringUnitInfo auxunit = getDefaultGoodsAuxMeterUnitInfo(goodsid);
        if(null != auxunit){
            auxunitid = auxunit.getMeteringunitid();
        }
        BigDecimal price = BigDecimal.ZERO;
        if(StringUtils.isNotEmpty(priceStr)){
            price = new BigDecimal(priceStr);
        }
        BigDecimal unitnum = BigDecimal.ZERO;
        if(StringUtils.isNotEmpty(unitNumStr)){
            unitnum = new BigDecimal(unitNumStr);
        }
        Map map = countGoodsInfoNumber(goodsid, auxunitid, price, unitnum);
        addJSONObject(map);
        return SUCCESS;
    }
}

