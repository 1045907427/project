package com.hd.agent.account.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.model.GoldTaxCustomerInvoice;
import com.hd.agent.account.model.GoldTaxCustomerInvoiceDetail;
import com.hd.agent.account.service.IGoldTaxCustomerInvoiceService;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.service.IAttachFileService;
import com.hd.agent.common.util.*;
import com.hd.agent.common.util.ftl.CutStringMethodModel;
import com.hd.agent.common.util.ftl.PaddingStringMethodModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class GoldTaxCustomerInvoiceAction extends BaseFilesAction {

    private IGoldTaxCustomerInvoiceService goldTaxCustomerInvoiceService;

    public IGoldTaxCustomerInvoiceService getGoldTaxCustomerInvoiceService() {
        return goldTaxCustomerInvoiceService;
    }

    public void setGoldTaxCustomerInvoiceService(IGoldTaxCustomerInvoiceService goldTaxCustomerInvoiceService) {
        this.goldTaxCustomerInvoiceService = goldTaxCustomerInvoiceService;
    }

    private GoldTaxCustomerInvoice goldTaxCustomerInvoice;

    public GoldTaxCustomerInvoice getGoldTaxCustomerInvoice() {
        return goldTaxCustomerInvoice;
    }

    public void setGoldTaxCustomerInvoice(GoldTaxCustomerInvoice goldTaxCustomerInvoice) {
        this.goldTaxCustomerInvoice = goldTaxCustomerInvoice;
    }

    /**
     * 客户金税开票列表
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Dec 20, 2017
     */
    public String showGoldTaxCustomerInvoiceListPage() throws Exception{

        String printlimit=getPrintLimitInfo();
        request.setAttribute("printlimit", printlimit);
        return SUCCESS;
    }
    /**
     * 客户金税开票分页列表数据
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Dec 20, 2017
     */
    public String getGoldTaxCustomerInvoicePageData() throws Exception{
        Map map=CommonUtils.changeMap(request.getParameterMap());
        if(null!=map){
            if(map.containsKey("isNoPageflag")){
                map.remove("isNoPageflag");
            }
        }
        String sort = (String)map.get("sort");
        String order = (String) map.get("order");
        if(StringUtils.isEmpty(sort) || StringUtils.isEmpty(order)){
            if(map.containsKey("sort")){
                map.remove("sort");
            }
            if(map.containsKey("order")){
                map.remove("order");
            }
            pageMap.setOrderSql(" businessdate desc , id desc");
        }
        pageMap.setCondition(map);
        PageData pageData=goldTaxCustomerInvoiceService.getGoldTaxCustomerInvoicePageData(pageMap);

        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 客户金税开票申请页面
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Dec 20, 2017
     */
    public String showGoldTaxCustomerInvoicePage() throws Exception{
        String tmp=request.getParameter("id");
        request.setAttribute("id", tmp);
        tmp=request.getParameter("type");
        request.setAttribute("type", tmp);


        String jsGoodsVersion=getSysParamValue("HTKPJSGOODSVERSION");
        if(null==jsGoodsVersion || "".equals(jsGoodsVersion.trim())){
            jsGoodsVersion="12.0";
        }
        request.setAttribute("jsGoodsVersion",jsGoodsVersion);

        String jskpDefaultReceipter=getSysParamValue("JSKPDefaultReceipter");
        request.setAttribute("jskpDefaultReceipter",jskpDefaultReceipter);
        String jskpDefaultChecker=getSysParamValue("JSKPDefaultChecker");
        request.setAttribute("jskpDefaultChecker",jskpDefaultChecker);


        return SUCCESS;
    }
    /**
     * 客户金税开票数据页面
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Dec 20, 2017
     */
    public String showGoldTaxCustomerInvoiceAddPage() throws Exception{
        Map<String,Object> fieldMap=getColumnDIYFieldName("t_base_goods_info");
        SysUser sysUser=getSysUser();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        request.setAttribute("today", dateFormat.format(calendar.getTime()));
        request.setAttribute("user", sysUser);
        request.setAttribute("gfieldMap", fieldMap);
        List statusList = getBaseSysCodeService().showSysCodeListByType("status");
        request.setAttribute("statusList", statusList);
        return SUCCESS;
    }
    /**
     * 添加客户金税开票数据
     * @param
     * @return string
     * @throws
     * @author zhanghonghui
     * @date Dec 20, 2017
     */
    public String addGoldTaxCustomerInvoice() throws Exception{
        Map resultMap=new HashMap();
        String addType = request.getParameter("addType");
        String goldTaxCustomerInvoiceDetails=request.getParameter("goldTaxCustomerInvoiceDetails");
        List<GoldTaxCustomerInvoiceDetail> detailList=null;
        if(null!=goldTaxCustomerInvoiceDetails && !"".equals(goldTaxCustomerInvoiceDetails.trim())){
            detailList= JSONUtils.jsonStrToList(goldTaxCustomerInvoiceDetails.trim(),new GoldTaxCustomerInvoiceDetail());
            goldTaxCustomerInvoice.setDetailList(detailList);
        }

        if("temp".equals(addType)){
            goldTaxCustomerInvoice.setStatus("1");	//暂存
        }else if("real".equals(addType)){
            goldTaxCustomerInvoice.setStatus("2");
        }else{
            goldTaxCustomerInvoice.setStatus("1");//暂存
        }
        if(null==detailList || detailList.size()==0){
            goldTaxCustomerInvoice.setStatus("1"); //暂存
        }

        Map saveResultMap=goldTaxCustomerInvoiceService.addGoldTaxCustomerInvoiceAddDetail(goldTaxCustomerInvoice);
        Boolean flag=(Boolean) saveResultMap.get("flag");
        String saveMsg=(String) saveResultMap.get("msg");
        if(null==flag){
            flag=false;
            resultMap.put("flag",false);
        }
        Boolean auditFlag=null;
        String auditMsg="";
        if(flag) {
            //判断是否审核
            String saveaudit = request.getParameter("saveaudit");

            if ("saveaudit".equals(saveaudit)) {
                Map auditResultMap = goldTaxCustomerInvoiceService.auditGoldTaxCustomerInvoice(goldTaxCustomerInvoice.getId());
                //addJSONObject(result);
                auditFlag = (Boolean) auditResultMap.get("flag");
                auditMsg = (String) auditResultMap.get("msg");
                if(null==auditFlag){
                    auditFlag=false;
                }
                if(auditFlag==true){
                    logStr = "保存并审核客户金税开票成功，单据编号：" + goldTaxCustomerInvoice.getId();
                }else{
                    String logMessage="添加客户金税开票成功，审核时失败";
                    if(null!=auditMsg && !"".equals(auditMsg.trim())){
                        logMessage=logMessage+"，"+auditMsg.trim();
                    }
                    logStr = logMessage +"，单据编号："+ goldTaxCustomerInvoice.getId();
                }
            } else if (flag) {
                logStr = "添加客户金税开票成功，单据编号：" + goldTaxCustomerInvoice.getId();
            }
        }else{
            String logMessage="添加客户金税开票失败，";
            if(null!=saveMsg && !"".equals(saveMsg.trim())){
                logMessage=logMessage+"，"+saveMsg.trim();
            }
            logStr = logMessage+"，单据编号：" + goldTaxCustomerInvoice.getId();
        }

        resultMap.put("auditflag", auditFlag);
        resultMap.put("auditmsg",auditMsg);
        resultMap.put("msg", saveMsg);
        resultMap.put("flag", flag);
        resultMap.put("backid", goldTaxCustomerInvoice.getId());
        resultMap.put("opertype", "add");
        addJSONObject(resultMap);
        return SUCCESS;
    }

    /**
     * 客户金税开票编辑页面
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Dec 20, 2017
     */
    public String showGoldTaxCustomerInvoiceEditPage() throws Exception{
        String id=request.getParameter("id");
        GoldTaxCustomerInvoice gttInvoice=goldTaxCustomerInvoiceService.getGoldTaxCustomerInvoiceAndDetail(id);
        List statusList = getBaseSysCodeService().showSysCodeListByType("status");
        String jsonStr ="[]";
        if(null==gttInvoice){
            gttInvoice=new GoldTaxCustomerInvoice();
        }
        if(null!=gttInvoice.getDetailList()){
            jsonStr = JSONUtils.listToJsonStr(gttInvoice.getDetailList());
        }
        GoldTaxCustomerInvoiceDetail goldTaxCustomerInvoiceDetailSum=gttInvoice.getDetailSum();
        if(null==goldTaxCustomerInvoiceDetailSum){
            goldTaxCustomerInvoiceDetailSum=new GoldTaxCustomerInvoiceDetail();
            goldTaxCustomerInvoiceDetailSum.setTaxamount(BigDecimal.ZERO);
            goldTaxCustomerInvoiceDetailSum.setNotaxamount(BigDecimal.ZERO);
            goldTaxCustomerInvoiceDetailSum.setTax(BigDecimal.ZERO);
        }
        Map<String,Object> fieldMap=getColumnDIYFieldName("t_base_goods_info");
        SysUser sysUser=getSysUser();
        request.setAttribute("user", sysUser);
        request.setAttribute("gfieldMap", fieldMap);
        request.setAttribute("statusList", statusList);
        request.setAttribute("goldTaxCustomerInvoice", gttInvoice);
        request.setAttribute("goldTaxCustomerInvoiceDetailSum", goldTaxCustomerInvoiceDetailSum);
        request.setAttribute("goodsDataList", jsonStr);

        String printlimit=getPrintLimitInfo();
        request.setAttribute("printlimit", printlimit);

        if(!"1".equals(gttInvoice.getStatus()) && !"2".equals(gttInvoice.getStatus())){
            return "viewSuccess";
        }
        return SUCCESS;
    }

    /**
     * 客户金税开票查看页面
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Dec 20, 2017
     */
    public String showGoldTaxCustomerInvoiceViewPage() throws Exception{
        String id=request.getParameter("id");
        GoldTaxCustomerInvoice gttInvoice=goldTaxCustomerInvoiceService.getGoldTaxCustomerInvoiceAndDetail(id);
        List statusList = getBaseSysCodeService().showSysCodeListByType("status");
        String jsonStr ="[]";
        if(null==gttInvoice){
            gttInvoice=new GoldTaxCustomerInvoice();
        }
        if(null!=gttInvoice.getDetailList()){
            jsonStr = JSONUtils.listToJsonStr(gttInvoice.getDetailList());
        }
        GoldTaxCustomerInvoiceDetail goldTaxCustomerInvoiceDetailSum=gttInvoice.getDetailSum();
        if(null==goldTaxCustomerInvoiceDetailSum){
            goldTaxCustomerInvoiceDetailSum=new GoldTaxCustomerInvoiceDetail();
            goldTaxCustomerInvoiceDetailSum.setTaxamount(BigDecimal.ZERO);
            goldTaxCustomerInvoiceDetailSum.setNotaxamount(BigDecimal.ZERO);
            goldTaxCustomerInvoiceDetailSum.setTax(BigDecimal.ZERO);
        }
        Map<String,Object> fieldMap=getColumnDIYFieldName("t_base_goods_info");
        SysUser sysUser=getSysUser();
        request.setAttribute("user", sysUser);
        request.setAttribute("gfieldMap", fieldMap);
        request.setAttribute("statusList", statusList);
        request.setAttribute("goldTaxCustomerInvoice", gttInvoice);
        request.setAttribute("goldTaxCustomerInvoiceDetailSum", goldTaxCustomerInvoiceDetailSum);
        request.setAttribute("goodsDataList", jsonStr);

        String printlimit=getPrintLimitInfo();
        request.setAttribute("printlimit", printlimit);

        return SUCCESS;
    }
    /**
     * 编辑客户金税开票
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Dec 20, 2017
     */
    @UserOperateLog(key="goldtaxinvoice",type=3)
    public String editGoldTaxCustomerInvoice() throws Exception{
//		boolean lock = isLockEdit("t_goldtax_customer_invoice", order.getId()); // 判断锁定并解锁
//		if (!lock) { // 被锁定不能进行修改
//			addJSONObject("lock", true);
//			return SUCCESS;
//		}
        Map resultMap = new HashMap();
        if(StringUtils.isEmpty(goldTaxCustomerInvoice.getId())){
            resultMap.put("flag", false);
            resultMap.put("msg", "未能找到要修改的信息");
            addJSONObject(resultMap);
            return SUCCESS;
        }

        String goldTaxCustomerInvoiceDetails=request.getParameter("goldTaxCustomerInvoiceDetails");
        List<GoldTaxCustomerInvoiceDetail> detailList=null;
        if(null!=goldTaxCustomerInvoiceDetails && !"".equals(goldTaxCustomerInvoiceDetails.trim())){
            detailList=JSONUtils.jsonStrToList(goldTaxCustomerInvoiceDetails.trim(),new GoldTaxCustomerInvoiceDetail());
            goldTaxCustomerInvoice.setDetailList(detailList);
        }

        if("2".equals(goldTaxCustomerInvoice.getStatus())){
            if(null==detailList || detailList.size()==0){
                resultMap.put("flag", false);
                resultMap.put("backid", "");
                resultMap.put("msg", "保存状态下，请填写开票商品信息");
                addJSONObject(resultMap);
                return SUCCESS;
            }
        }
        GoldTaxCustomerInvoice oldgttInvoice=goldTaxCustomerInvoiceService.getGoldTaxCustomerInvoice(goldTaxCustomerInvoice.getId());
        if(null==oldgttInvoice){
            resultMap.put("flag", false);
            resultMap.put("msg", "未能找到要修改的信息");
            addJSONObject(resultMap);
            return SUCCESS;
        }

        String addType = request.getParameter("addType");
        if(!"1".equals(oldgttInvoice.getStatus()) && !"2".equals(oldgttInvoice.getStatus())){
            resultMap.put("flag", false);
            resultMap.put("msg", "抱歉，当前单据不可修改");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        if("1".equals(oldgttInvoice.getStatus())){
            if("real".equals(addType)){
                goldTaxCustomerInvoice.setStatus("2");
            }
        }else{
            goldTaxCustomerInvoice.setStatus(oldgttInvoice.getStatus());
        }
        SysUser sysUser = getSysUser();
        Map saveResultMap = goldTaxCustomerInvoiceService.updateGoldTaxCustomerInvoiceAndDetail(goldTaxCustomerInvoice);
        Boolean flag=(Boolean) saveResultMap.get("flag");
        String saveMsg=(String) saveResultMap.get("msg");
        if(null==flag){
            flag=false;
            resultMap.put("flag",false);
        }
        Boolean auditFlag=null;
        String auditMsg="";
        if(flag) {
            //判断是否审核
            String saveaudit = request.getParameter("saveaudit");

            if ("saveaudit".equals(saveaudit)) {
                Map auditResultMap = goldTaxCustomerInvoiceService.auditGoldTaxCustomerInvoice(goldTaxCustomerInvoice.getId());
                //addJSONObject(result);
                auditFlag = (Boolean) auditResultMap.get("flag");
                auditMsg = (String) auditResultMap.get("msg");
                if(null==auditFlag){
                    auditFlag=false;
                }
                if(auditFlag==true){
                    logStr = "保存并审核客户金税开票成功，单据编号：" + goldTaxCustomerInvoice.getId();
                }else{
                    String logMessage="修改客户金税开票成功，审核时失败";
                    if(null!=auditMsg && !"".equals(auditMsg.trim())){
                        logMessage=logMessage+"，"+auditMsg.trim();
                    }
                    logStr = logMessage +"，单据编号："+ goldTaxCustomerInvoice.getId();
                }
            } else if (flag) {
                logStr = "修改客户金税开票成功，单据编号：" + goldTaxCustomerInvoice.getId();
            }
        }else{
            String logMessage="修改客户金税开票失败，";
            if(null!=saveMsg && !"".equals(saveMsg.trim())){
                logMessage=logMessage+"，"+saveMsg.trim();
            }
            logStr = logMessage+"，单据编号：" + goldTaxCustomerInvoice.getId();
        }

        resultMap.put("auditflag", auditFlag);
        resultMap.put("auditmsg",auditMsg);
        resultMap.put("msg", saveMsg);
        resultMap.put("backid", goldTaxCustomerInvoice.getId());
        resultMap.put("opertype", "edit");
        resultMap.put("flag", flag);
        addJSONObject(resultMap);
        return SUCCESS;
    }


    /**
     * 删除申请客户金税开票
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2017-12-20
     */
    @UserOperateLog(key="goltaxinvoice",type=4)
    public String deleteGoldTaxCustomerInvoice() throws Exception{
        String id=request.getParameter("id");
        boolean delFlag = canTableDataDelete("t_goldtax_customer_invoice", id); //判断是否被引用，被引用则无法删除。
        if(!delFlag){
            addJSONObject("delFlag", true);
            return SUCCESS;
        }
        boolean flag=goldTaxCustomerInvoiceService.deleteGoldTaxCustomerInvoiceAndDetail(id);
        addJSONObject("flag", flag);
        if(flag){
            logStr="删除客户金税开票成功，单据编号："+id;
        }else{
            logStr="删除客户金税开票失败，单据编号："+id;
        }
        return SUCCESS;
    }
    /**
     * 审核申请客户金税开票
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2017-12-20
     */
    @UserOperateLog(key="goltaxinvoice",type=0)
    public String auditGoldTaxCustomerInvoice() throws Exception{
        String id = request.getParameter("id");
        if(null==id || "".equals(id)){
            addJSONObject("flag", false);
            return SUCCESS;
        }
        Map map=goldTaxCustomerInvoiceService.auditGoldTaxCustomerInvoice(id);
        addJSONObject(map);
        boolean flag = (Boolean) map.get("flag");
        if(flag){
            logStr="审核客户金税开票成功，单据编号："+id;
        }else{
            logStr="审核客户金税开票失败，单据编号："+id;
        }
        return SUCCESS;
    }
    /**
     * 反审申请客户金税开票
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2017-12-20
     */
    @UserOperateLog(key="goltaxinvoice",type=0)
    public String oppauditGoldTaxCustomerInvoice() throws Exception{
        String id = request.getParameter("id");
        if(null==id || "".equals(id)){
            addJSONObject("flag", false);
            return SUCCESS;
        }
        Map map=goldTaxCustomerInvoiceService.oppauditGoldTaxCustomerInvoice(id);
        addJSONObject(map);
        boolean flag = (Boolean) map.get("flag");
        if(flag){
            logStr="反审客户金税开票成功，单据编号："+id;
        }else{
            logStr="反审客户金税开票失败，单据编号："+id;
        }
        return SUCCESS;
    }

    /**
     *客户金税开票明细列表查看页面
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-5-6
     */
    public String showGoldTaxCustomerInvoiceDetailViewPage() throws Exception{
        request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
        return SUCCESS;
    }

    /**
     * 客户金税开票明细列表
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-9-30
     */
    public String getGoldTaxCustomerInvoiceDetailListByOrderid() throws Exception{
        String orderid=request.getParameter("id");
        List<GoldTaxCustomerInvoiceDetail> list=goldTaxCustomerInvoiceService.getGoldTaxCustomerInvoiceDetailListByBillid(orderid);
        addJSONArray(list);
        return SUCCESS;
    }

    /**
     * 客户金税开票客户金税开票明细列表添加页面
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-5-6
     */
    public String showGoldTaxCustomerInvoiceDetailAddPage() throws Exception{
        Map colMap = getEditAccessColumn("t_goldtax_customer_invoice_detail");
        request.setAttribute("colMap", colMap);

        request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
        return SUCCESS;
    }

    /**
     * 客户金税开票客户金税开票明细列表编辑页面
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-5-6
     */
    public String showGoldTaxCustomerInvoiceDetailEditPage() throws Exception{
        Map colMap = getEditAccessColumn("t_goldtax_customer_invoice_detail");
        request.setAttribute("colMap", colMap);
        request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
        return SUCCESS;
    }
    /**
     * 客户金税开票明细分页列表数据
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-5-17
     */
    public String exportGoldTaxCustomerInvoiceDetailData() throws Exception{
        String billid=(String)request.getParameter("billid");
        if(null==billid || "".equals(billid.trim())){
            return null;
        }

        String title = request.getParameter("title");
        if(null!=title && !"".equals(title.trim())){
            title = "客户金税开票明细";
        }
        GoldTaxCustomerInvoice billOrder=goldTaxCustomerInvoiceService.getGoldTaxCustomerInvoiceAndDetail(billid);
        List<GoldTaxCustomerInvoiceDetail> detailList=null;
        if(null!=billOrder){
            detailList=billOrder.getDetailList();
        }
        ExcelUtils.exportExcel(exportGoldTaxCustomerInvoiceDetailDataFillter(billOrder, detailList), title);
        return SUCCESS;
    }

    private List<Map<String, Object>> exportGoldTaxCustomerInvoiceDetailDataFillter(GoldTaxCustomerInvoice billOrder,List<GoldTaxCustomerInvoiceDetail> list) throws Exception{
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("billid", "单据编号");
        firstMap.put("businessdate", "业务日期");
        firstMap.put("customername", "开票客户名称");
        firstMap.put("customertaxno", "客户税号");
        firstMap.put("jstypeid", "金税分类编码");
        firstMap.put("goodsname", "开票商品名称");
        firstMap.put("model", "规格型号");
        firstMap.put("unitname", "单位");
        firstMap.put("unitnum", "数量");
        firstMap.put("taxprice", "单价");
        firstMap.put("taxamount", "金额");
        firstMap.put("notaxprice", "未税单价");
        firstMap.put("notaxamount", "未税金额");
        firstMap.put("taxrate", "税率");
        firstMap.put("tax", "税额");
        firstMap.put("remark", "备注");
        result.add(firstMap);

        if(list.size() != 0){
            for(GoldTaxCustomerInvoiceDetail item : list){
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map = new HashMap<String, Object>();
                map = PropertyUtils.describe(item);
                map.put("businessdate",billOrder.getBusinessdate());
                map.put("customername",billOrder.getCustomername());
                map.put("customertaxno",billOrder.getCustomertaxno());
                for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
                    if(map.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
                        for(Map.Entry<String, Object> entry : map.entrySet()){
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
        return result;
    }

    /**
     * 导出客户金税开票清单 航天开票系统 xml格式
     * @param
     * @return void
     * @throws
     * @author zhang_honghui
     * @date Dec 30, 2016
     */
    public String exportGoldTaxCustomerInvoiceXMLForHTKP()throws Exception{
        Map resultMap=new HashMap();
        String title = request.getParameter("title");
        if(null==title || "".equals(title.trim())){
            title="金税开票XML格式";
        }
        String exportid = request.getParameter("exportid");
        String msg="";
        if(null==exportid || "".equals(exportid.trim())){
            resultMap.put("flag", false);
            resultMap.put("msg", "未能找到相关单据信息");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        String checker=request.getParameter("checkerid");
        if(null==checker || "".equals(checker.trim())){
            resultMap.put("flag", false);
            resultMap.put("msg", "未能找到复核人信息");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        Personnel checkerPersonnel=getPersonnelInfoById(checker);
        if(null==checkerPersonnel || StringUtils.isEmpty(checkerPersonnel.getName())){
            resultMap.put("flag", false);
            resultMap.put("msg", "未能找到复核人信息");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        String receipter=request.getParameter("receipterid");
        if(null==receipter || "".equals(receipter.trim())){
            resultMap.put("flag", false);
            resultMap.put("msg", "未能找到收款人信息");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        Personnel receipterPersonnel=getPersonnelInfoById(receipter);
        if(null==receipterPersonnel || StringUtils.isEmpty(receipterPersonnel.getName())){
            resultMap.put("flag", false);
            resultMap.put("msg", "未能找到收款人信息");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        String jsgoodsversion=request.getParameter("jsgoodsversion");
        if(null==jsgoodsversion || "".equals(jsgoodsversion.trim())){
            jsgoodsversion="12.0";
        }
        jsgoodsversion=jsgoodsversion.trim();
        Map queryMap=CommonUtils.changeMap(request.getParameterMap());
        Map handleResultMap = goldTaxCustomerInvoiceService.getGoldTaxCustomerInvoiceBillXMLForHTKP(queryMap);
        Boolean flag=false;
        if(handleResultMap.containsKey("flag")){
            flag=(Boolean)handleResultMap.get("flag");
            if(null==flag){
                flag=false;
            }
        }
        if(false==flag){
            title="金税开票XML格式导出出错";
            if(handleResultMap.containsKey("msg")){
                msg=(String)handleResultMap.get("msg");
            }
            if(null==msg || "".equals(msg.trim())){
                msg="未能找到相关导出数据";
            }
            resultMap.put("flag", false);
            resultMap.put("msg", msg);
            addJSONObject(resultMap);
            return SUCCESS;
        }
        Map billMap = null;
        //开票单据是否存在
        if(handleResultMap.containsKey("goldTaxCustomerInvoiceBill")){
            billMap=(Map) handleResultMap.get("goldTaxCustomerInvoiceBill");
        }
        if(null==billMap || billMap.size()==0){
            msg="未能找到相关订单数据";
            resultMap.put("flag", false);
            resultMap.put("msg", msg);
            addJSONObject(resultMap);
            return SUCCESS;
        }
        List<Map> detailMapList = null;
        //开票明细是否存在
        if(handleResultMap.containsKey("detailList")){
            detailMapList=(List<Map>) handleResultMap.get("detailList");
        }
        if(null==detailMapList || detailMapList.size()==0){
            msg="未能找到相关订单明细数据";
            resultMap.put("flag", false);
            resultMap.put("msg", msg);
            addJSONObject(resultMap);
            return SUCCESS;
        }
        billMap.put("detailcount",detailMapList.size());

        String tplFileDir = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/ftl/");
        Map tplDataMap=new HashMap();
        tplDataMap.put("companyName",getSysParamValue("COMPANYNAME"));
        tplDataMap.put("billOrderData",billMap);
        tplDataMap.put("detailList",detailMapList);
        tplDataMap.put("billchecker",checkerPersonnel.getName());
        tplDataMap.put("billreceipter",receipterPersonnel.getName());
        tplDataMap.put("billgoodsname","");
        tplDataMap.put("jsgoodsversion",jsgoodsversion);
        tplDataMap.put("cutStringFunc",new CutStringMethodModel());
        tplDataMap.put("paddingStringFunc",new PaddingStringMethodModel());
        //创建freemarker配置实例
        Configuration config=new Configuration();
        config.setNumberFormat("#.######");
        config.setDirectoryForTemplateLoading(new File(tplFileDir));
        config.setDefaultEncoding("UTF-8");
        config.setTemplateUpdateDelay(0);
        Template template=config.getTemplate("goldtaxinvoice_htkp_xml.ftl");
        template.setEncoding("UTF-8");
        StringWriter stringWriter = new StringWriter();
        template.process(tplDataMap, stringWriter);

        //byte[] result =new String(stringWriter.getBuffer().toString().getBytes("UTF-8"),"gb2312").getBytes("gb2312");
        byte[] result =stringWriter.getBuffer().toString().getBytes("GBK");
        IAttachFileService attachFileService=(IAttachFileService)SpringContextUtils.getBean("attachFileService");
        String fileid=attachFileService.createFileAndAttachFile(result,"xml","htkp",title);

        String amountlog="客户金税开票“"+exportid+"”金税XML文件生成成功";
        String amountmsg="";
        if(handleResultMap.containsKey("amountmsg")){
            amountmsg=(String)handleResultMap.get("amountmsg");
            amountlog=amountmsg;
        }

        addLog(amountlog.trim());
        resultMap.put("flag",true);
        resultMap.put("amountmsg",amountmsg);
        resultMap.put("datafileid",fileid);

        SysUser sysUser=getSysUser();
        GoldTaxCustomerInvoice upBillExportTimes=new GoldTaxCustomerInvoice();
        upBillExportTimes.setId(exportid);
        upBillExportTimes.setJxexporttimes(1);
        upBillExportTimes.setJxexportuserid(sysUser.getUserid());
        upBillExportTimes.setJxexportusername(sysUser.getName());
        upBillExportTimes.setJxexportdatetime(new Date());
        goldTaxCustomerInvoiceService.updateOrderJSExportTimes(upBillExportTimes);

        addJSONObject(resultMap);
        return SUCCESS;
    }

    /**
     * 导入客户金税开票数据
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Apr 23, 2017
     */
    public String importCustomerGoldTaxInvoiceData() throws Exception{
        String billid=request.getParameter("billid");
        Map resultMap=new HashMap();
        if(null==billid || "".equals(billid.trim())){
            resultMap.put("msg","未找到相关单据号");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        //默认消息
        resultMap.put("msg","客户金税开票数据导入失败");

        List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
        if(null==paramList){
            resultMap.put("msg","无法读取上传文件内容");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        List<String> paramList2 = new ArrayList<String>();
        for(String str : paramList){
            if("金税分类编码".equals(str)){
                paramList2.add("jstypeid");
            }else if("客户商品编码".equals(str)){
                paramList2.add("sourcegoodsid");
            }else if("商品名称".equals(str)){
                paramList2.add("sourcegoodsname");
            }else if("规格型号".equals(str)){
                paramList2.add("model");
            }else if("计量单位".equals(str)){
                paramList2.add("unitname");
            }else if("数量".equals(str)){
                paramList2.add("unitnum");
            }else if("含税单价".equals(str)){
                paramList2.add("taxprice");
            }else if("含税金额".equals(str)){
                paramList2.add("taxamount");
            }else if("税率".equals(str)){
                paramList2.add("taxrate");
            }else{
                paramList2.add("null");
            }
        }
        List<String> dataCellList = new ArrayList<String>();
        dataCellList.add("jstypeid");
        dataCellList.add("sourcegoodsid");
        dataCellList.add("sourcegoodsname");
        dataCellList.add("model");
        dataCellList.add("unitname");
        dataCellList.add("unitnum");
        dataCellList.add("taxprice");
        dataCellList.add("taxamount");
        dataCellList.add("taxrate");
        dataCellList.add("errormessage");

        if(paramList.size() == paramList2.size()){
            List<Map<String, Object>> list = null;
            try{
                list=ExcelUtils.importExcelMoreSheet(excelFile, paramList2); //获取导入数据
            }catch (Exception ex){
                resultMap.put("flag",false);
                resultMap.put("msg","从导入文件中获取数据失败，请检查导入字段名称是否一致");
		        addJSONObject(resultMap);
		        return SUCCESS;
            }
            if(null==list ||list.size()==0){
                resultMap.put("flag",false);
                resultMap.put("msg","导入的明细数据为空");
                addJSONObject(resultMap);
                return SUCCESS;
            }

            resultMap=goldTaxCustomerInvoiceService.importCustomerGoldTaxInvoiceData(billid,list);

            if(resultMap.containsKey("errorDataList") && null!=resultMap.get("errorDataList")){
                List<Map<String,Object>> errorDataList=(List<Map<String,Object>>)resultMap.get("errorDataList");
                resultMap.remove("errorDataList");
                if(errorDataList.size() > 0){
                    //模板文件路径
                    String outTplFilePath = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/customerGoldTaxInvoice.xls");
                    IAttachFileService attachFileService=(IAttachFileService)SpringContextUtils.getBean("attachFileService");
                    String fileid=attachFileService.createExcelAndAttachFile(errorDataList, dataCellList, outTplFilePath,"客户金税开票数据导入失败");
                    resultMap.put("msg","导入失败"+errorDataList.size()+"条");
                    resultMap.put("errorid",fileid);
                }
            }
            Boolean flag=(Boolean)resultMap.get("flag");
            if(flag==true){
                String logs=(String)resultMap.get("updateLogs");
                if(null!=logs && !"".equals(logs.trim())){
                    addLog("客户金税开票数据导入，更新金税商品字段"+logs);
                }
            }
        }else{
            resultMap.put("flag",false);
            resultMap.put("msg","未进行导入操作，请检查导入字段名称是否一致");
	        addJSONObject(resultMap);
	        return SUCCESS;
        }
        addJSONObject(resultMap);
        return SUCCESS;
    }
    /**
     * 通过总数量计算商品含税 未税金额 以及辅单位数量等
     *  unitnum商品数量 auxunitid辅单位编码（不是必填） taxprice：含税单价 taxtype税种编号
     * @return
     * @throws Exception
     * @author zhang_hh
     * @date 2017-12-24
     */
    public String getJsUnitnumChanger() throws Exception{
        String unitnumStr = request.getParameter("unitnum");
        String taxpriceStr = request.getParameter("taxprice");
        String taxrateStr = request.getParameter("taxrate");

        BigDecimal unitnum = null;
        if(null==unitnumStr || "".equals(unitnumStr)){
            unitnum = new BigDecimal(0);
        }else{
            unitnum = new BigDecimal(unitnumStr);
        }
        BigDecimal taxprice = null;
        if(null==taxpriceStr || "".equals(taxpriceStr)){
            taxprice = new BigDecimal(0);
        }else{
            taxprice = new BigDecimal(taxpriceStr);
        }
        BigDecimal taxrate = null;
        if(null==unitnumStr || "".equals(taxrateStr)){
            taxrate = new BigDecimal(0);
        }else{
            taxrate = new BigDecimal(taxrateStr);
        }

        //含税金额 = 含税单价*数量
        BigDecimal taxamount = taxprice.multiply(unitnum).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);
        //无税金额 = 含税金额/（1+税率） 保存2位小数
        BigDecimal notaxamount = taxamount.divide(taxrate.divide(new BigDecimal(100)).add(new BigDecimal(1)),2,BigDecimal.ROUND_HALF_UP);
        //无税单价 = 无税金额/（数量） 保存9位小数
        BigDecimal notaxprice = BigDecimal.ZERO;
        if(BigDecimal.ZERO.compareTo(unitnum)!=0){
            notaxprice=notaxamount.divide(unitnum,9,BigDecimal.ROUND_HALF_UP);
        }
        //税额
        BigDecimal tax = taxamount.subtract(notaxamount).setScale(2,BigDecimal.ROUND_HALF_UP);

        Map returnMap = new HashMap();
        returnMap.put("unitnum", unitnum);
        returnMap.put("taxrate", taxrate);
        returnMap.put("taxprice", taxprice);
        returnMap.put("notaxprice", notaxprice);
        returnMap.put("taxamount", taxamount);
        returnMap.put("notaxamount", notaxamount);
        returnMap.put("tax", tax);
        addJSONObject(returnMap);
        return "success";
    }
    /**
     * 修改含税单价或无税单价时，获取对应单价、金额和税额改变的结果。
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date 2017-12-24
     */
    public String getJsPriceChanger() throws Exception{
        String type = request.getParameter("type"); //1含税单价改变2无税单价改变
        String priceStr = request.getParameter("price"); //改变后的无税或含税单价
        String taxrateStr = request.getParameter("taxrate"); //税率
        String unitnumStr = request.getParameter("unitnum"); //主计量单位数量;

        if(null==unitnumStr || "".equals(unitnumStr.trim())){
            unitnumStr="0";
        }
        BigDecimal price = null;
        if(null==priceStr || "".equals(priceStr.trim())){
            priceStr="0";
        }
        BigDecimal taxrate = null;
        if(null==taxrateStr || "".equals(taxrateStr)){
            taxrate = new BigDecimal(0);
        }else{
            taxrate = new BigDecimal(taxrateStr);
        }

        BigDecimal bPrice = new BigDecimal(priceStr);
        BigDecimal bUnitnum = new BigDecimal(unitnumStr);
        BigDecimal bTaxPrice = new BigDecimal(0); //含税单价
        BigDecimal bNoTaxPrice = new BigDecimal(0); //无税单价
        BigDecimal bTaxAmount = new BigDecimal(0); //含税金额
        BigDecimal bNoTaxAmount = new BigDecimal(0); //无税金额
        BigDecimal bTax = new BigDecimal(0); //税额
        if("1".equals(type)){
            bTaxPrice = bPrice;
            bTaxAmount = bTaxPrice.multiply(bUnitnum).setScale(2,BigDecimal.ROUND_HALF_UP);
            //无税金额 = 含税金额/（1+税率） 保存2位小数
            bNoTaxAmount = bTaxAmount.divide(taxrate.divide(new BigDecimal(100)).add(new BigDecimal(1)),2,BigDecimal.ROUND_HALF_UP);
            if(BigDecimal.ZERO.compareTo(bUnitnum)!=0) {
                //无税单价 = 无税金额/（数量） 保存6位小数
                bNoTaxPrice = bNoTaxAmount.divide(bUnitnum, 9, BigDecimal.ROUND_HALF_UP);
            }
        }
        else if("2".equals(type)){
            bNoTaxPrice = bPrice;
            bNoTaxAmount = bNoTaxPrice.multiply(bUnitnum).setScale(2,BigDecimal.ROUND_HALF_UP);
            //含税金额 = 无税金额 *（1+税率） 保存2位小数
            bTaxAmount = bNoTaxAmount.multiply(taxrate.divide(new BigDecimal(100)).add(new BigDecimal(1))).setScale(2,BigDecimal.ROUND_HALF_UP);
            if(BigDecimal.ZERO.compareTo(bUnitnum)!=0) {
                //含税单价 = 含税金额/（数量） 保存6位小数
                bTaxPrice = bTaxAmount.divide(bUnitnum, 6, BigDecimal.ROUND_HALF_UP);
            }
        }
        bTax=bTaxAmount.subtract(bNoTaxAmount).setScale(2,BigDecimal.ROUND_HALF_UP);
        Map map = new HashMap();
        map.put("taxrate",taxrate);
        map.put("unitnum",bUnitnum);
        map.put("taxprice", bTaxPrice);
        map.put("taxamount", bTaxAmount);
        map.put("notaxprice", bNoTaxPrice);
        map.put("notaxamount", bNoTaxAmount);
        map.put("tax", bTax);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 修改含税金额时，获取对应含税单价，税单价和税额改变的结果
     * @return
     * @throws Exception
     * @author zhanghh
     * @date 2017-12-24
     */
    public String getJsAmountChanger() throws Exception{
        String type = request.getParameter("type"); //1含税单价改变2无税单价改变
        String amountStr = request.getParameter("amount"); //改变后的无税或含税金额
        String taxrateStr = request.getParameter("taxrate"); //税率
        String unitnumStr = request.getParameter("unitnum"); //主计量单位数量;

        if(null==unitnumStr || "".equals(unitnumStr.trim())){
            unitnumStr="0";
        }
        if(null==amountStr || "".equals(amountStr.trim())){
            amountStr="0";
        }
        BigDecimal taxrate = null;
        if(null==taxrateStr || "".equals(taxrateStr)){
            taxrate = new BigDecimal(0);
        }else{
            taxrate = new BigDecimal(taxrateStr);
        }

        BigDecimal bAmount = new BigDecimal(amountStr);
        BigDecimal bUnitnum = new BigDecimal(unitnumStr);
        BigDecimal bTaxPrice = new BigDecimal(0); //含税单价
        BigDecimal bNoTaxPrice = new BigDecimal(0); //无税单价
        BigDecimal bTaxAmount = new BigDecimal(0); //含税金额
        BigDecimal bNoTaxAmount = new BigDecimal(0); //无税金额
        BigDecimal bTax = new BigDecimal(0); //税额
        if("1".equals(type)){
            bTaxAmount = bAmount;
            //无税金额 = 含税金额/（1+税率） 保存2位小数
            bNoTaxAmount = bTaxAmount.divide(taxrate.divide(new BigDecimal(100)).add(new BigDecimal(1)),2,BigDecimal.ROUND_HALF_UP);
            if(BigDecimal.ZERO.compareTo(bUnitnum)!=0) {
                //含税单价 = 含税金额/（数量） 保存6位小数
                bTaxPrice = bTaxAmount.divide(bUnitnum, 6, BigDecimal.ROUND_HALF_UP);
                //无税单价 = 无税金额/（数量） 保存6位小数
                bNoTaxPrice = bNoTaxAmount.divide(bUnitnum, 9, BigDecimal.ROUND_HALF_UP);
            }
        }
        else if("2".equals(type)){
            bNoTaxAmount = bAmount;
            //含税金额 = 无税金额 *（1+税率） 保存2位小数
            bTaxAmount = bNoTaxAmount.multiply(taxrate.divide(new BigDecimal(100)).add(new BigDecimal(1))).setScale(2,BigDecimal.ROUND_HALF_UP);
            if(BigDecimal.ZERO.compareTo(bUnitnum)!=0) {
                //无税单价 = 无税金额/（数量） 保存6位小数
                bNoTaxPrice = bNoTaxAmount.divide(bUnitnum, 9, BigDecimal.ROUND_HALF_UP);
                //含税单价 = 含税金额/（数量） 保存6位小数
                bTaxPrice = bTaxAmount.divide(bUnitnum, 6, BigDecimal.ROUND_HALF_UP);
            }
        }
        bTax=bTaxAmount.subtract(bNoTaxAmount).setScale(2,BigDecimal.ROUND_HALF_UP);
        Map map = new HashMap();
        map.put("taxrate",taxrate);
        map.put("unitnum",bUnitnum);
        map.put("taxprice", bTaxPrice);
        map.put("taxamount", bTaxAmount);
        map.put("notaxprice", bNoTaxPrice);
        map.put("notaxamount", bNoTaxAmount);
        map.put("tax", bTax);
        addJSONObject(map);
        return SUCCESS;
    }
    /**
     * 显示修改发票号、发票代码信息页面
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2017-12-26
     */
    public String showGoldTaxCustomerInvoiceNoEditPage()throws Exception{
        String id = request.getParameter("id");
        GoldTaxCustomerInvoice orderData=goldTaxCustomerInvoiceService.getGoldTaxCustomerInvoice(id);
        request.setAttribute("goldTaxCustomerInvoice",orderData);
        return SUCCESS;
    }

    /**
     * 修改客户金税开票发票号、发票代码信息
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2017-12-26
     */
    @UserOperateLog(key="goltaxinvoice",type=3)
    public String editGoldTaxCustomerInvoiceNo()throws Exception{
        Map resultMap=new HashMap();
        if(StringUtils.isEmpty(goldTaxCustomerInvoice.getId())){
            resultMap.put("flag",false);
            resultMap.put("msg","未找到客户金税开票数据");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        if(StringUtils.isEmpty(goldTaxCustomerInvoice.getInvoiceno())){
            goldTaxCustomerInvoice.setInvoiceno("");
        }
        if(StringUtils.isEmpty(goldTaxCustomerInvoice.getInvoicecode())){
            goldTaxCustomerInvoice.setInvoicecode("");
        }
        boolean flag = goldTaxCustomerInvoiceService.editGoldTaxCustomerInvoiceNo(goldTaxCustomerInvoice);
        addJSONObject("flag", flag);
        return SUCCESS;
    }
}
