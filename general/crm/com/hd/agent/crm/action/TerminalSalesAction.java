package com.hd.agent.crm.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.*;
import com.hd.agent.crm.model.CrmSalesOrder;
import com.hd.agent.crm.model.CrmSalesOrderDetail;
import com.hd.agent.crm.service.ICrmTerminalSalesService;
import com.hd.agent.sales.action.BaseSalesAction;
import com.hd.agent.sales.model.ModelOrder;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lin_xx on 2016/9/23.
 */
public class TerminalSalesAction extends BaseSalesAction {

    private ICrmTerminalSalesService crmTerminalSalesService;

    public ICrmTerminalSalesService getCrmTerminalSalesService() {
        return crmTerminalSalesService;
    }

    public void setCrmTerminalSalesService(ICrmTerminalSalesService crmTerminalSalesService) {
        this.crmTerminalSalesService = crmTerminalSalesService;
    }

    private CrmSalesOrder crmSalesOrder;

    public CrmSalesOrder getCrmSalesOrder() {
        return crmSalesOrder;
    }

    public void setCrmSalesOrder(CrmSalesOrder crmSalesOrder) {
        this.crmSalesOrder = crmSalesOrder;
    }

    /**
      *显示客户销量页面
      * @author lin_xx
      * @date 2016/9/23
      */
    public String terminalSalesOrderListPage() throws Exception {
        return SUCCESS;
    }

     /**
      *客户销量列表数据
      * @author lin_xx
      * @date 2016/9/23
      */
    public String terminalSalesOrderListData() throws Exception {

        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = crmTerminalSalesService.getTerminalOrderData(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

    public String terminalSalesOrderPage() throws Exception {
        String type = request.getParameter("type");
        String id = request.getParameter("id");
        request.setAttribute("type",type);
        request.setAttribute("id",id);
        return SUCCESS;
    }

    /**
     * 客户销量数据新增
     * @author lin_xx
     * @date 2016/9/23
     */
    public String showTerminalSalesOrderAddPage() throws Exception{
        String date = CommonUtils.getTodayDataStr();
        request.setAttribute("date",date);
        return SUCCESS;
    }

    /**
     * 客户销量数据修改
     * @author lin_xx
     * @date 2016/9/23
     */
    public String terminalOrderEditPage() throws Exception{
        String id = request.getParameter("id");
        CrmSalesOrder crmSalesOrder =crmTerminalSalesService.getTerminalOrderById(id);
        String list = JSONUtils.listToJsonStr(crmSalesOrder.getCrmSalesOrderDetailList());
        //当前客户应收款及余额情况
        if(StringUtils.isNotEmpty(crmSalesOrder.getCustomerid())){
            Map map = showCustomerReceivableInfoData(crmSalesOrder.getCustomerid());
            BigDecimal receivableAmount  = (BigDecimal) map.get("receivableAmount");//客户应收款情况
            BigDecimal leftAmount = (BigDecimal) map.get("leftAmount");//客户余额
            request.setAttribute("receivableAmount", receivableAmount);
            request.setAttribute("leftAmount", leftAmount);
        }
        List statusList = getBaseSysCodeService().showSysCodeListByType("status");
        request.setAttribute("statusList", statusList);
        request.setAttribute("crmSalesOrder",crmSalesOrder);
        request.setAttribute("goodsJson",list);
        return SUCCESS;
    }

     /**
      *新增 客户销量商品 明细新增页面
      * @author lin_xx
      * @date 2016/9/26
      */
    public String terminalOrderDetailAddPage() throws Exception {
        String customerId = request.getParameter("customerid");
        request.setAttribute("customerId", customerId);
        return SUCCESS;
    }

    /**
     *新增 客户销量商品 明细修改页面
     * @author lin_xx
     * @date 2016/9/26
     */
    public String terminalOrderDetailEditPage() throws Exception {
        // 获取小数位
        request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
        String customerId = request.getParameter("customerid");
        request.setAttribute("customerId", customerId);
        return SUCCESS;
    }

     /**
      *保存新增的客户销量数据
      * @author lin_xx
      * @date 2016/9/23
      */
     @UserOperateLog(key = "CrmSaleOrder", type = 2)
    public String addTerminalSalesOrder() throws Exception {

        String orderDetailJson = request.getParameter("goodsjson");
        List<CrmSalesOrderDetail> orderDetailList = JSONUtils.jsonStrToList(orderDetailJson, new CrmSalesOrderDetail());
        crmSalesOrder.setCrmSalesOrderDetailList(orderDetailList);
        Map map = crmTerminalSalesService.addTerminalOrder(crmSalesOrder);
        if(map.containsKey("id")){
            String id = (String) map.get("id");
            addLog("销售终端数据新增 编号：" + id, true);
        }
        addJSONObject(map);
        return SUCCESS;
    }

     /**
      *修改客户销量数据
      * @author lin_xx
      * @date 2016/9/29
      */
     @UserOperateLog(key = "CrmSaleOrder", type = 3)
    public String editTerminalSalesOrder() throws Exception {

        boolean lock = isLock("t_crm_sales_order", crmSalesOrder.getId()); // 判断锁定并解锁
        if (lock) { // 被锁定不能进行修改
            addJSONObject("lock", true);
            addLog("客户销量 编码：" + crmSalesOrder.getId() + "互斥，操作", false);
            return SUCCESS;
        }
        SysUser sysUser = getSysUser();
        crmSalesOrder.setModifyuserid(sysUser.getUserid());
        crmSalesOrder.setModifyusername(sysUser.getName());
        String orderDetailJson = request.getParameter("goodsjson");
        List<CrmSalesOrderDetail> orderDetailList = JSONUtils.jsonStrToList(orderDetailJson, new CrmSalesOrderDetail());
        crmSalesOrder.setCrmSalesOrderDetailList(orderDetailList);
        boolean flag = crmTerminalSalesService.editTerminalOrder(crmSalesOrder);
        addLog("客户销量修改 编号：" + crmSalesOrder.getId() , flag);
         Map map = new HashMap();
         map.put("flag", flag);
         map.put("id", crmSalesOrder.getId());
         addJSONObject(map);
         // 解锁数据
         isLockEdit("t_crm_sales_order", crmSalesOrder.getId()); // 判断锁定并解锁

        return SUCCESS;
    }

     /**
      * 删除客户销量单据
      * @author lin_xx
      * @date 2016/12/7
      */
    @UserOperateLog(key = "CrmSaleOrder", type = 4)
    public String deleteTerminalSalesOrder() throws Exception {
        String id = request.getParameter("id");
        boolean delFlag = canTableDataDelete("t_crm_sales_order", id); // 判断是否被引用，被引用则无法删除。
        if (!delFlag) {
            addJSONObject("delFlag", true);
            return SUCCESS;
        }
        boolean flag = crmTerminalSalesService.deleteTerminalSalesOrder(id);
        addJSONObject("flag", flag);
        addLog("客户销量删除 编号：" + id, flag);

        return SUCCESS;
    }
     /**
      * 显示模板上传文件页面
      * @author lin_xx
      * @date 2016/12/7
      */
    public String showCrmModelParamPage() throws Exception {
        String type = request.getParameter("type");
        request.setAttribute("type",type);
        return SUCCESS;
    }

     /**
      * 模板文件数据导入
      * @author lin_xx
      * @date 2016/12/7
      */
   @UserOperateLog(key="CrmSaleOrder",type=2)
    public String importCrmOrderModel() throws Exception {

        String busid = request.getParameter("busid");
        String gtype = request.getParameter("gtype");
        String ctype = request.getParameter("ctype");
        String pricetype = request.getParameter("pricetype");
        String fileparam = request.getParameter("fileparam");
        String[] info = fileparam.split(";");
        Map map = new HashMap();
        Map barcodeMap = new HashMap();
        //读取导入文件
        InputStream is = new FileInputStream(importFile);
        Workbook hssfWorkbook = null;
        String type = ExcelUtils.getFileByFile(importFile);
        if("xls".equals(type)){
            hssfWorkbook = new HSSFWorkbook(is);
        }else if("xlsx".equals(type)){
            hssfWorkbook = new XSSFWorkbook(importFile.getPath());
        }else{
            hssfWorkbook = new XSSFWorkbook(importFile.getPath());
        }
        int beginRow = 0; //开始行
        int goodsCol = -1;
       int customerTypeCol = -1;//客户类型读取位置
       int customerTypeRow = -1;
        int numCol = -1 ;
        int priceCol = 0;
        int boxnumCol = 0;
        int customerCol = -1;
        int customerRow = 0;
       int divideCol = -1;
        int otherCol = -1 ;//和拆分所在列一起用
        int detailcount = 0 ;//拆分时导入多少条商品明细统计
       for (int i = 0; i < info.length; ++i) {
           String value = info[i].split("=")[1];//获取参数值
           if (info[i].contains("开始单元格")) {
               String beginRowStr = value.replaceAll("[a-zA-Z]+", "");
               beginRow = Integer.parseInt(beginRowStr) - 1;
           } else if (info[i].contains("商品条形码") || info[i].contains("商品编码") || info[i].contains("商品助记符") || info[i].contains("商品店内码")) {
               goodsCol = ExcelUtils.chagneCellColtoNumber(value);

           } else if (info[i].contains("拆分所在列")) {
               divideCol = ExcelUtils.chagneCellColtoNumber(value);

           } else if (info[i].contains("商品数量")) {
               numCol = ExcelUtils.chagneCellColtoNumber(value);

           } else if (info[i].contains("商品箱数")) {
               boxnumCol = ExcelUtils.chagneCellColtoNumber(value);

           } else if (info[i].contains("商品单价") && "1".equals(pricetype)) {
               priceCol = ExcelUtils.chagneCellColtoNumber(value);

           } else if (info[i].contains("客户单号位置")) {
               String Column = value.replaceAll("\\d+", "");
               customerCol = ExcelUtils.chagneCellColtoNumber(Column);
               String customerRowStr = value.replaceAll("[a-zA-Z]+", "");
               customerRow = Integer.parseInt(customerRowStr);
           }  else if (info[i].contains("客户单元格")) {
               String Column = value.replaceAll("\\d+", "");
               customerTypeCol = ExcelUtils.chagneCellColtoNumber(Column);
               String customerRowStr = value.replaceAll("[a-zA-Z]+", "");
               customerTypeRow = Integer.parseInt(customerRowStr);
           } else if (info[i].contains("日期/其它列")) {
               otherCol = ExcelUtils.chagneCellColtoNumber(value);
           }
       }
       //获取客户单号
       String sourceid = getCellValueByParam(importFile, customerCol, customerRow, null);
       Map map1 = new HashMap();
       //根据客户类型 获取导入的客户编码
       if ("1".equals(ctype)) {
           busid = request.getParameter("busid");
       } else {
           if (customerTypeRow != -1) {
               String custParam = getCellValueByParam(importFile, customerTypeCol, customerTypeRow, new ArrayList<String>());
               if (org.apache.commons.lang3.StringUtils.isNotEmpty(custParam)) {
                   if ("2".equals(ctype)) {
                       map1.put("pid", busid);//总店
                       map1.put("shopno", custParam);//店号
                       busid = returnCustomerID(map1);
                   } else if ("3".equals(ctype)) {
                       map1.put("shortcode", custParam);
                       busid = returnCustomerID(map1);
                   } else if ("4".equals(ctype)) {
                       map1.put("name", custParam);
                       busid = returnCustomerID(map1);
                   } else if ("5".equals(ctype)) {
                       map1.put("shortname", custParam);
                       busid = returnCustomerID(map1);
                   } else if ("6".equals(ctype)) {
                       map1.put("address", custParam);
                       busid = returnCustomerID(map1);
                   } else if ("7".equals(ctype)) {
                       map1.put("id", custParam);
                       busid = returnCustomerID(map1);
                   }
               }
           }
       }
       int count = -1;
       //根据分隔列导入多张单据
       if (divideCol > -1) {
           Map modelResultMap = getModelOrderMapByPram(beginRow, divideCol, goodsCol, numCol, priceCol, boxnumCol, pricetype, gtype, ctype,otherCol);
           Iterator<Map.Entry<String, List>> it = modelResultMap.entrySet().iterator();
           while (it.hasNext()) {
               ++ count;
               Map.Entry<String, List> entry = it.next();
               List wareList = entry.getValue();
               if (org.apache.commons.lang3.StringUtils.isEmpty(busid)) {
                   map.put("info", "busidEmpty");
               } else if (wareList.size() > 0) {
                   //订单 数据
                   CrmSalesOrder cso = new CrmSalesOrder();
                   cso.setStatus("2");
                   ModelOrder modelOrder = (ModelOrder)wareList.get(0);
                   Customer valideCustomer = getCustomerById(modelOrder.getBusid());
                   if (null == valideCustomer) {
                       cso.setCustomerid(busid);
                   }else{
                       cso.setCustomerid(modelOrder.getBusid());
                   }
                   cso.setSourceid(sourceid);
                   cso.setBusinessdate(CommonUtils.getTodayDataStr());
                   Map detailMap = crmTerminalSalesService.changeModelForDetail(wareList,gtype);
                   map.putAll(detailMap);
                   if(detailMap.containsKey("errorIdentify")){
                        barcodeMap.put(count,detailMap.get("errorIdentify"));
                   }
                   List<CrmSalesOrderDetail> detailList = (List<CrmSalesOrderDetail>) detailMap.get("detailList");
                   if(detailList.size() >0){
                       //插入数据
                       cso.setCrmSalesOrderDetailList(detailList);
                       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                       //模板导入中的日期/其它列x信息，如果该信息是日期，则作为业务日期导入
                       String othermsg = modelOrder.getOtherMsg();

                       if(org.apache.commons.lang3.StringUtils.isNotEmpty(othermsg)){
                           cso.setField07(othermsg);
                           try{
                               Date date = format.parse(othermsg);
                               cso.setBusinessdate(othermsg);
                           }catch (Exception e){
                               cso.setBusinessdate(format.format(new Date()));
                           }
                       }
                       if(detailList.size() >550){
                           addTerminalSalesList(cso,detailList);
                       }else{
                           Map insertMap = crmTerminalSalesService.addTerminalOrder(cso);
                           map.putAll(insertMap);
                       }
                       detailcount += detailList.size();
                   }

               }
           }
           barcodeMap.put("count",detailcount);
       } else {
           //分别读取对应列值
           for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
               //订单 数据
               CrmSalesOrder cso = new CrmSalesOrder();
               cso.setStatus("2");
               cso.setCustomerid(busid);
               cso.setSourceid(sourceid);
               cso.setBusinessdate(CommonUtils.getTodayDataStr());
               Customer customer = getCustomerById(busid);
               if(null != customer){
                   cso.setCustomername(customer.getName());
               }
               //不合并相同商品组装商品明细
               List<ModelOrder> wareList = getModelOrderListByPram("1", beginRow, numSheet, goodsCol, numCol, priceCol, boxnumCol, pricetype, gtype, busid, "");
               if (org.apache.commons.lang3.StringUtils.isEmpty(busid)) {
                   map.put("info", "busidEmpty");
               } else if (wareList.size() > 0) {
                   Map detailMap = crmTerminalSalesService.changeModelForDetail(wareList,gtype);
                   map.putAll(detailMap);
                   if(detailMap.containsKey("errorIdentify")){
                       barcodeMap.put(numSheet+1,detailMap.get("errorIdentify"));
                   }
                   List<CrmSalesOrderDetail> detailList = (List<CrmSalesOrderDetail>) detailMap.get("detailList");
                   if(detailList.size() >0){
                       if(detailList.size() >550){
                           addTerminalSalesList(cso,detailList);
                       }else{
                           //插入数据
                           cso.setCrmSalesOrderDetailList(detailList);
                           Map insertMap = crmTerminalSalesService.addTerminalOrder(cso);
                           map.putAll(insertMap);
                       }
                   }else{
                       int sheetindex = numSheet+ 1;
                       if(map.containsKey("emptysheet")){
                           map.put("emptysheet",map.get("emptysheet").toString()+","+sheetindex);
                       }else{
                           map.put("emptysheet",sheetindex);
                       }
                       map.put("flag",false);
                   }

               }
           }

       }
       map.put("gtype",gtype);
       sheetModelMsg(barcodeMap, map);

        return SUCCESS;
    }

    /**
     * 销量明细多于550条，则拆分开导入多条销量单据
     * @throws
     * @author lin_xx
     * @date 2018-02-12
     */
    public Map addTerminalSalesList(CrmSalesOrder crmSalesOrder, List<CrmSalesOrderDetail> detailList) throws Exception{
        Map map = new HashMap();
        int total = detailList.size();
        int count = total/550;
        int ordercount = 0;
        for (int i = 0; i < (count + 1); i++) {
            if(550*(i+1) < total){
                List list = detailList.subList(550*i,550*(i+1) );
                crmSalesOrder.setCrmSalesOrderDetailList(list);
            }else{
                List list = detailList.subList(550*i,detailList.size() );
                crmSalesOrder.setCrmSalesOrderDetailList(list);
            }
            Map insertMap = crmTerminalSalesService.addTerminalOrder(crmSalesOrder);
            if((Boolean) insertMap.get("flag")){
                ++ ordercount;
            }
        }
        map.put("count",ordercount);
        if(ordercount > 0){
            map.put("flag",true);
        }
        return map;
    }

    /**
     * 客户门店状态查询
     * @throws
     * @author lin_xx
     * @date 2017/10/20
     */
    public String showCustomerQeuryTeminalPage() throws Exception {
        String businessdate = CommonUtils.getNowMonthDay();
        request.setAttribute("businessdate",businessdate);
        String businessdate1 = CommonUtils.getTodayDataStr();
        request.setAttribute("businessdate1",businessdate1);
        return SUCCESS;
    }

    /**
     * 根据条件查找不同时间段客户门店销量情况
     * @throws
     * @author lin_xx
     * @date 2017/10/20
     */
    public String customerQeuryTeminalData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = crmTerminalSalesService.getCustomerTerminalSalesData(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

}
