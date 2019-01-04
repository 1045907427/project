package com.hd.agent.crm.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.*;
import com.hd.agent.crm.model.CustomerStorageOrder;
import com.hd.agent.crm.model.CustomerStorageOrderDetail;
import com.hd.agent.crm.service.ICustomerStorageOrderService;
import com.hd.agent.sales.model.ModelOrder;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Created by lin_xx on 2016/10/11.
 */
public class CustomerStorageOrderAction extends BaseFilesAction {

    public CustomerStorageOrder customerStorageOrder;

    public CustomerStorageOrder getCustomerStorageOrder() {
        return customerStorageOrder;
    }

    public void setCustomerStorageOrder(CustomerStorageOrder customerStorageOrder) {
        this.customerStorageOrder = customerStorageOrder;
    }

    public ICustomerStorageOrderService customerStorageOrderService;

    public ICustomerStorageOrderService getCustomerStorageOrderService() {
        return customerStorageOrderService;
    }

    public void setCustomerStorageOrderService(ICustomerStorageOrderService customerStorageOrderService) {
        this.customerStorageOrderService = customerStorageOrderService;
    }

    /**
      *显示客户库存单据列表
      * @author lin_xx
      * @date 2016/10/11
      */
    public String showCustomerStorageOrderListPage() throws Exception {
        return SUCCESS;
    }
     /**
      *删除客户库存单据
      * @author lin_xx
      * @date 2016/10/13
      */
     @UserOperateLog(key = "CustomerStorageOrder", type = 4)
    public String deleteCustomerStorageSalesOrder() throws Exception {
        String id = request.getParameter("id");
        boolean delFlag = canTableDataDelete("t_crm_customer_storage", id); // 判断是否被引用，被引用则无法删除。
        if (!delFlag) {
            addJSONObject("delFlag", true);
            return SUCCESS;
        }
        boolean flag = customerStorageOrderService.deleteCustomerStorageSalesOrder(id);
        addJSONObject("flag", flag);
        addLog("客户库存单据删除 编号：" + id, flag);

        return SUCCESS;
    }
     /**
      * 审核库存单据并添加对应客户库存
      * @author lin_xx
      * @date 2016/11/17
      */
    @UserOperateLog(key = "CustomerStorageOrder", type = 3)
    public String auditCustomerStorageSalesOrder() throws Exception {
        String id = request.getParameter("id");
        Map map = customerStorageOrderService.auditCustomerStorage(id);
        boolean flag =  (Boolean)map.get("flag");
        addJSONObject(map);
        addLog("客户库存单据审核 编号：" + id, flag);

        return SUCCESS;
    }
     /**
      * 反审库存单据并修改对应客户库存
      * @author lin_xx
      * @date 2016/11/17
      */
     @UserOperateLog(key = "CustomerStorageOrder", type = 3)
    public String oppAuditCustomerStorageSalesOrder() throws Exception {
         String id = request.getParameter("id");
         Map map = customerStorageOrderService.oppAuditCustomerStorage(id);
         map.put("id",id);
         boolean flag =  (Boolean)map.get("flag");
         addJSONObject(map);
         addLog("客户库存单据反审 编号：" + id, flag);
        return SUCCESS;
    }

     /**
      *获取客户库存单据列表数据
      * @author lin_xx
      * @date 2016/10/11
      */
    public String getCustomerStorageOrderListPageData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = customerStorageOrderService.getCustomerStorageOrderListPageData(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

    public String showCustomerStorageOrderPage() throws Exception{
        String type = request.getParameter("type");
        String id = request.getParameter("id");
        request.setAttribute("type",type);
        request.setAttribute("id",id);
        return SUCCESS;
    }
     /**
      *跳转到客户库存新增页面
      * @author lin_xx
      * @date 2016/10/12
      */
    public String showCustomerStorageOrderAddPage() throws Exception {
        String date = CommonUtils.getTodayDataStr();
        request.setAttribute("date",date);
        return SUCCESS;
    }
     /**
      * 新增的客户库存订单
      * @author lin_xx
      * @date 2016/10/12
      */
    public String addCustomerStorageOrder() throws Exception {
        String orderDetailJson = request.getParameter("goodsjson");
        List<CustomerStorageOrderDetail> orderDetailList = JSONUtils.jsonStrToList(orderDetailJson, new CustomerStorageOrderDetail());
        customerStorageOrder.setCustomerStorageOrderDetailList(orderDetailList);
        Map map = customerStorageOrderService.addCustomerStorageOrder(customerStorageOrder);
        if(map.containsKey("id")){
            String id = (String) map.get("id");
            addLog("客户库存新增 编号：" + id, true);
        }
        addJSONObject(map);

        return SUCCESS;
    }
     /**
      *跳转到客户库存修改页面
      * @author lin_xx
      * @date 2016/10/12
      */
    public String showCustomerStorageEditPage() throws Exception {
        String id = request.getParameter("id");
        CustomerStorageOrder customerStorageOrder =customerStorageOrderService.getCustomerStorageOrderById(id);
        String list = JSONUtils.listToJsonStr(customerStorageOrder.getCustomerStorageOrderDetailList());
        //当前客户应收款及余额情况
//        Map map = showCustomerReceivableInfoData(customerStorageOrder.getCustomerid());
//        BigDecimal receivableAmount  = (BigDecimal) map.get("receivableAmount");//客户应收款情况
//        BigDecimal leftAmount = (BigDecimal) map.get("leftAmount");//客户余额
//        request.setAttribute("receivableAmount", receivableAmount);
//        request.setAttribute("leftAmount", leftAmount);

        List statusList = getBaseSysCodeService().showSysCodeListByType("status");
        request.setAttribute("statusList", statusList);
        request.setAttribute("customerStorageOrder",customerStorageOrder);
        request.setAttribute("goodsJson",list);
        if("2".equals(customerStorageOrder.getStatus())){
            return "editSuccess";
        }else{
            return "viewSuccess";
        }
    }
    /**
     * 修改客户库存订单
     * @author lin_xx
     * @date 2016/10/12
     */
    @UserOperateLog(key = "CustomerStorageOrder", type = 3)
    public String editCustomerStorageOrder() throws Exception {
        boolean lock = isLock("t_crm_customer_storage", customerStorageOrder.getId()); // 判断锁定并解锁
        if (lock) { // 被锁定不能进行修改
            addJSONObject("lock", true);
            addLog("客户库存 编码：" + customerStorageOrder.getId() + "互斥，操作", false);
            return SUCCESS;
        }
        SysUser sysUser = getSysUser();
        customerStorageOrder.setModifyuserid(sysUser.getUserid());
        customerStorageOrder.setModifyusername(sysUser.getName());
        String orderDetailJson = request.getParameter("goodsjson");
        List<CustomerStorageOrderDetail> orderDetailList = JSONUtils.jsonStrToList(orderDetailJson, new CustomerStorageOrderDetail());
        customerStorageOrder.setCustomerStorageOrderDetailList(orderDetailList);
        boolean flag = customerStorageOrderService.editCustomerStorage(customerStorageOrder);
        addLog("客户库存修改 编号：" + customerStorageOrder.getId() , flag);
        Map map = new HashMap();
        map.put("flag", flag);
        map.put("id", customerStorageOrder.getId());
        addJSONObject(map);
        // 解锁数据
        isLockEdit("t_crm_customer_storage", customerStorageOrder.getId()); // 判断锁定并解锁
        return SUCCESS;
    }

    /**
     *新增 客户库存单据商品 明细新增页面
     * @author lin_xx
     * @date 2016/9/26
     */
    public String showCustStorageDetailAddPage() throws Exception {
        return SUCCESS;
    }

    /**
     *新增 客户库存单据商品 明细修改页面
     * @author lin_xx
     * @date 2016/9/26
     */
    public String showCustStorageDetailEditPage() throws Exception {
        // 获取小数位
        request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
        return SUCCESS;
    }

    @UserOperateLog(key="CustomerStorageOrder",type=2)
    public String importCustStorageModel() throws Exception {

        String busid = request.getParameter("busid");
        String gtype = request.getParameter("gtype");
        String ctype = request.getParameter("ctype");
        String pricetype = request.getParameter("pricetype");
        String fileparam = request.getParameter("fileparam");
        String[] info = fileparam.split(";");

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

        Map<Integer,String> goodsMap = new HashMap<Integer, String>();
        Map<Integer,String> priceMap = new HashMap<Integer, String>();
        Map<Integer,String> numMap = new HashMap<Integer, String>();
        Map<Integer,String> boxnumMap = new HashMap<Integer, String>();
        int beginRow = 0; //开始行
        int endRow = 0;//结束行
        int goodsCol = -1;
        int numCol = -1 ;
        int priceCol = 0;
        int boxnumCol = 0;
        int costpriceCol = 0;
        int customerParamCol = -1;//客户参数读取位置
        int customerParamRow = 0;
        int remarkCol = -1;
        int remarkRow = -1;
        String msg = "";
        int customerCol = -1;
        int customerRow = 0;
        String sourceid = "";
        for(int i = 0 ; i< info.length ; ++ i) {
            String value = info[i].split("=")[1];//获取参数值
            if (info[i].contains("开始单元格")) {
                String beginRowStr = value.replaceAll("[a-zA-Z]+","");
                beginRow = Integer.parseInt(beginRowStr) - 1;
            }else if (info[i].contains("结束单元格")) {
                String endRowStr = value.replaceAll("[a-zA-Z]+","");
                endRow = Integer.parseInt(endRowStr) - 1;
            }else if(info[i].contains("截止行")){
                endRow = Integer.parseInt(value) - 1;
            }else if(info[i].contains("商品条形码")){
                goodsCol = ExcelUtils.chagneCellColtoNumber(value);
            }else if(info[i].contains("商品数量")){
                numCol = ExcelUtils.chagneCellColtoNumber(value);
            }else if(info[i].contains("商品成本价")){
                costpriceCol = ExcelUtils.chagneCellColtoNumber(value);
            }else if(info[i].contains("商品零售价") && "1".equals(pricetype)){
                priceCol = ExcelUtils.chagneCellColtoNumber(value);
            }else if(info[i].contains("商品助记符")){
                goodsCol = ExcelUtils.chagneCellColtoNumber(value);
            }else if(info[i].contains("商品店内码")){
                goodsCol = ExcelUtils.chagneCellColtoNumber(value);
            }else if(info[i].contains("商品编码")){
                goodsCol = ExcelUtils.chagneCellColtoNumber(value);
            }else if(info[i].contains("客户单号位置")){
                String Column = value.replaceAll("\\d+", "");
                customerCol = ExcelUtils.chagneCellColtoNumber(Column);
                String customerRowStr = value.replaceAll("[a-zA-Z]+","");
                customerRow = Integer.parseInt(customerRowStr);
            }else if(info[i].contains("客户参数")){
                String Column = value.replaceAll("\\d+", "");
                customerParamCol = ExcelUtils.chagneCellColtoNumber(Column);
                String customerRowStr = value.replaceAll("[a-zA-Z]+","");
                customerParamRow = Integer.parseInt(customerRowStr);
            }else if (info[i].contains("备注")) {
                String Column = value.replaceAll("\\d+", "");
                remarkCol = ExcelUtils.chagneCellColtoNumber(Column);
                String customerRowStr = value.replaceAll("[a-zA-Z]+", "");
                remarkRow = Integer.parseInt(customerRowStr);
            }
        }
        //获取模板备注
        String remark = "";
        if(remarkCol >-1 && remarkRow >-1){
            List<String> customerRowInfo = ExcelUtils.importFirstRowByIndex(importFile,remarkRow-1, 0);
            if(customerRowInfo.size() > 0){
                remark = (String) customerRowInfo.get(remarkCol);
            }
        }

        String customerParam = "";//读取到的客户参数
        Map map1 = new HashMap();
        //获取客户参数具体信息
        String str2 = "";
        if(org.apache.commons.lang3.StringUtils.isEmpty(customerParam) && customerParamRow >-1){
            List<String> customerRowInfo = ExcelUtils.importFirstRowByIndex(importFile,customerParamRow-1, 0);
            if(customerRowInfo.size() > 0){
                customerParam = (String) customerRowInfo.get(customerParamCol);
                if(org.apache.commons.lang3.StringUtils.isNotEmpty(customerParam)  && ("2".equals(ctype) || "3".equals(ctype) ) ){//按店号按助记符 提取对应单元格中的数字
                    for(int i=0;i<customerParam.length();i++){
                        if(customerParam.charAt(i)>=48 && customerParam.charAt(i)<=57){
                            str2+=customerParam.charAt(i);
                        }
                    }
                    customerParam = str2;
                }
            }
        }
        //根据客户类型 获取导入的客户编码
        if("1".equals(ctype)){
            busid = request.getParameter("busid");
        }else if("2".equals(ctype)){
            map1.put("pid",busid);//总店
            map1.put("shopno",customerParam);//店号
            busid = returnCustomerID(map1);
        }else if("3".equals(ctype)){
            map1.put("shortcode",customerParam);
            busid = returnCustomerID(map1);
        }else if("4".equals(ctype)){
            map1.put("name",customerParam);
            busid = returnCustomerID(map1);
        }else if("5".equals(ctype)){
            map1.put("shortname",customerParam);
            busid = returnCustomerID(map1);
        }else if("6".equals(ctype)){
            map1.put("address",customerParam);
            busid = returnCustomerID(map1);
        }
        //分别读取对应列值
        for(int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++){
            if(goodsCol > -1){
                goodsMap = ExcelUtils.importExcelByGoodsGolumn(importFile,numSheet,beginRow,0,goodsCol);
            }else{
                continue;
            }
            if(numCol > -1){
                numMap =  ExcelUtils.importExcelByColumn(importFile,numSheet,beginRow,0,numCol);
            }else{
                continue;
            }
            priceMap =  ExcelUtils.importExcelByColumn(importFile,numSheet,beginRow,endRow,priceCol);
            Map costpriceMap = ExcelUtils.importExcelByColumn(importFile,numSheet,beginRow,endRow,costpriceCol);
            if(boxnumCol > -1){
                boxnumMap =  ExcelUtils.importExcelByColumn(importFile,numSheet,beginRow,endRow,boxnumCol);
            }
            //组装导入数据
            int orderNum = goodsMap.size();
            List<ModelOrder> wareList = new ArrayList<ModelOrder>();
            if(goodsMap.size() == orderNum && orderNum >= 0){
                List<String> rowInfo = ExcelUtils.importFirstRowByIndex(importFile,customerRow-1, 0);
                if(rowInfo.size() > 0){
                    sourceid = (String) rowInfo.get(customerCol);
                }
                Map sameBarcode = new HashMap();
                Set set = goodsMap.entrySet();
                Iterator i = set.iterator();
                while(i.hasNext()){
                    Map.Entry<String, String> entry =(Map.Entry<String, String>)i.next();
                    ModelOrder modelOrder = new ModelOrder();
                    String goodsidentify = (String)goodsMap.get(entry.getKey());
                    if(StringUtils.isEmpty(goodsidentify)){
                        continue;
                    }
                    modelOrder.setUnitnum((String)numMap.get(entry.getKey()));
                    if(sameBarcode.containsKey(goodsidentify)){
                        sameBarcode.put(goodsidentify, "2");
                        continue;
                    }else{
                        sameBarcode.put(goodsidentify,"1");
                    }
                    if("1".equals(gtype)){
                        modelOrder.setBarcode((String)goodsMap.get(entry.getKey()));
                    }else if("2".equals(gtype)){
                        modelOrder.setShopid((String)goodsMap.get(entry.getKey()));
                    }else if("3".equals(gtype)){
                        modelOrder.setSpell((String)goodsMap.get(entry.getKey()));
                    }else if("4".equals(gtype)){
                        modelOrder.setGoodsid((String)goodsMap.get(entry.getKey()));
                    }
                    modelOrder.setBusid(busid);
                    if(priceMap.size() > 0 && "1".equals(pricetype)){
                        modelOrder.setTaxprice((String)priceMap.get(entry.getKey()));
                    }
                    if(costpriceMap.size() >0){
                        modelOrder.setOtherMsg((String)costpriceMap.get(entry.getKey()));//该字段临时放置 成本价
                    }
                    if(boxnumMap.size() > 0){
                        modelOrder.setBoxnum((String) boxnumMap.get(entry.getKey()));
                    }
                    wareList.add(modelOrder);
                }

                //获取相同商品
                if(sameBarcode.containsValue("2")){
                    Iterator iterator = sameBarcode.keySet().iterator();
                    while (iterator.hasNext()){
                        String key = (String) iterator.next();
                        String value = (String) sameBarcode.get(key);
                        if("2".equals(value)){
                            msg += key+",";
                        }
                    }
                }
                if(StringUtils.isNotEmpty(msg) && wareList.size() >0){
                    msg = "一张单据中不允许存在相同商品,相同商品:"+msg+"第二条记录导入失败";
                }
                String emptySheet = "";
                int count = 0;
                //数据
                CustomerStorageOrder cso = new CustomerStorageOrder();
                cso.setStatus("2");
                cso.setCustomerid(busid);
                cso.setSourceid(sourceid);
                cso.setSourcetype("2");
                cso.setRemark(remark);
                cso.setBusinessdate(CommonUtils.getTodayDataStr());
                Customer customer = getCustomerById(busid);
                if(null != customer){
                    cso.setCustomername(customer.getName());
                }
                if(wareList.size()> 0) {
                    Map detailMap = customerStorageOrderService.changeModelForDetail(wareList,gtype);
                    List<CustomerStorageOrderDetail> detailList = (List<CustomerStorageOrderDetail>) detailMap.get("detailList");
                    if(detailList.size() > 0){
                        //插入数据
                        cso.setCustomerStorageOrderDetailList(detailList);
                        Map insertMap = customerStorageOrderService.addCustomerStorageOrder(cso);
                        // String id = null != insertMap.get("id") ? (String) insertMap.get("id") : "";
                        boolean flag = (Boolean) insertMap.get("flag");
                        if(flag){
                            if(StringUtils.isEmpty(msg)){
                                msg = "导入成功!";
                            }
                            String unimportGoods = (String) detailMap.get("unimportGoods");
                            String disableGoods = (String) detailMap.get("disableGoods");
                            if(StringUtils.isNotEmpty(unimportGoods)){
                                msg +="第"+(numSheet+1)+"张工作表没有对应商品："+unimportGoods+",";
                            }
                            if(StringUtils.isNotEmpty(disableGoods)){
                                msg +="第"+(numSheet+1)+"张工作表禁用商品："+disableGoods+";";
                            }
                        }else{
                            msg += "导入失败";
                        }

                    }else{
                        emptySheet += (numSheet + 1) + "";
                    }
                }else{
                    emptySheet += (numSheet + 1) + "";
                }
                if(emptySheet !="" && count != hssfWorkbook.getNumberOfSheets()){
                    msg += "第"+(numSheet+1)+"张工作表导入不成功;";
                }else if(count == hssfWorkbook.getNumberOfSheets()){
                    msg = "导入失败!";
                }
            } else {
                msg = "导入失败!";
            }
        }
        if(StringUtils.isEmpty(msg)){
            request.setAttribute("msg","导入失败,请检查导入模板是否正确！");
        }else{
            request.setAttribute("msg",msg);
        }
        return SUCCESS;
    }

}
