package com.hd.agent.common.action;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.model.AttachFile;
import com.hd.agent.common.service.IAttachFileService;
import com.hd.agent.common.util.*;
import com.hd.agent.sales.action.ModelAction;
import com.hd.agent.sales.model.ModelOrder;
import com.hd.agent.sales.service.IOrderService;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AutoImportAction extends BaseFilesAction {

    public IOrderService salesOrderService;

    public IOrderService getSalesOrderService() {
        return salesOrderService;
    }

    public void setSalesOrderService(IOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    private IAttachFileService attachFileService;

    public IAttachFileService getAttachFileService() {
        return attachFileService;
    }

    public void setAttachFileService(IAttachFileService attachFileService) {
        this.attachFileService = attachFileService;
    }

    /**
     * 自选（智能）导入页面
     * @throws
     * @author lin_xx
     * @date 2018-02-09
     */
    public String showAutoImportPage() throws Exception{
        //String type = request.getParameter("type");

        return SUCCESS;
    }

    /**
     * 读取导入文件
     * @throws
     * @author lin_xx
     * @date 2018-02-07
     */
    public String readImportFile() throws Exception {
        Map map = new HashMap();
        String path = "";
        String fileType = request.getParameter("fileType");
        String param = request.getParameter("param");
        //拆分导入参数
        Map parseMap = parseParamToList(param);
        if(parseMap.containsKey("flag")){
            map = parseMap;
        }else{

            File excelFile = null;
            if("excel".equals(fileType)){
                excelFile = importFile;
            }else if("html".equals(fileType)){
                //文件保存到classpath目录下面
                path= OfficeUtils.getFilepath()+"/htmlToExcel/";
                Document doc = Jsoup.parse(importFile, "UTF-8", "http://example.com/");
                Element body = doc.body();
                Elements tables = body.getElementsByTag("table");
                if(null != tables){
                    File file2 = new File(path);
                    //判断文件夹是否存在,如果不存在则创建文件夹
                    if (!file2.exists()) {
                        file2.mkdir();
                    }
                    String name =  CommonUtils.getDataNumberWithRand()+".xls";
                    path+= name;
                    try {
                        File file = new File(path);
                        WritableWorkbook book = Workbook.createWorkbook(file);
                        if(parseMap.containsKey("sheetnum")){
                            int sheetnum = (Integer) parseMap.get("sheetnum");//数据所在工作表
                            if(sheetnum > tables.size() ){
                                map.put("msg","参数：工作表="+sheetnum+",模板有工作表"+tables.size()+"张，参数错误！");
                            }else {
                                if(tables.size() == sheetnum){
                                    -- sheetnum;
                                }
                                Element table = tables.get(sheetnum);
                                //得到所有行
                                Elements trs = table.getElementsByTag("tr");
                                ///得到列宽集合
                                Elements colgroups=table.getElementsByTag("colgroup");

                                WritableSheet sheet = book.createSheet("html数据"+sheetnum, sheetnum);
                                ExcelAction.setColWidth(colgroups,sheet);
                                ExcelAction.mergeColRow(trs,sheet);
                                book.write();
                                book.close();
                            }
                        }else{
                            for (int i = 0; i < tables.size(); i++) {
                                Element table = tables.get(i);
                                //得到所有行
                                Elements trs = table.getElementsByTag("tr");
                                ///得到列宽集合
                                Elements colgroups=table.getElementsByTag("colgroup");
                                try{
                                    WritableSheet sheet = book.createSheet("html数据"+i, i);
                                    ExcelAction.setColWidth(colgroups,sheet);
                                    ExcelAction.mergeColRow(trs,sheet);

                                }catch (Exception e){
                                    continue;
                                }
                            }
                            book.write();
                            book.close();
                        }

                        AttachFile attachFile = new AttachFile();
                        attachFile.setExt(".xls");
                        attachFile.setFilename(name);
                        attachFile.setFullpath(CommonUtils.filterFilePathSaparator("/htmlToExcel/"+name));
                        attachFile.setOldfilename(name);
                        //将临时文件信息插入数据库
                        attachFileService.addAttachFile(attachFile);
                        if(null!=attachFile){
                            map.put("modelfile",attachFile.getId());
                        }

                    } catch (RowsExceededException e) {
                        e.printStackTrace();
                    } catch (WriteException e) {
                        e.printStackTrace();
                    }
                }
                excelFile = new File(path);
            }else if("csv".equals(fileType) || "txt".equals(fileType) ){
                Map m = convertCSVorTxt2Xls(importFile);
                excelFile = (File) m.get("file");
                map.put("modelfile",m.get("fileid"));
            }
            if(map.containsKey("msg")){

            }else{
                String type = ExcelUtils.getFileByFile(importFile);
                if("excel".equals(fileType)){
                    if(!"xls".equals(type) && !"xlsx".equals(type) ){
                        map.put("msg","文件类型错误，请选择模板正确的文件类型重新导入!");
                    }else{
                        Map map1 = organizeExcelData(parseMap,excelFile);
                        map.putAll(map1);
                    }
                }else{
                    try{
                        Map map1 = organizeExcelData(parseMap,excelFile);
                        map.putAll(map1);
                    }catch (Exception e){
                        map.put("msg","文件类型或参数错误，组装数据失败!");
                    }
                }
            }
            List<Map> paramList = (List<Map>) parseMap.get("paramList");//参数组装
            map.put("paramJson", JSONUtils.listToJsonStr(paramList));
        }
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 将模板参数解析成map
     * @throws
     * @author lin_xx
     * @date 2018-02-24
     */
    public Map parseParamToList(String param) throws Exception {
        Map map = new HashMap();
        if(StringUtils.isNotEmpty(param)){
            int beginRow = -1;
            int divideCol = -1;
            int sheetnum = -1 ; //数据工作表
            String[] info = param.split(";");
            List<Map> paramList = new ArrayList<Map>();
            for (int i = 0; i < info.length; ++i) {
                String value = info[i].split("=")[1];//获取参数值
                Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
                Matcher m = p.matcher(value);
                if (m.find()) {
                    map.put("flag",false);
                    break;
                }
                Map paramMap = new HashMap();
                if (info[i].contains("开始单元格")) {
                    String beginRowStr = value.replaceAll("[a-zA-Z]+", "");
                    beginRow = Integer.parseInt(beginRowStr) - 1;
                    paramMap.put("paramname","开始行");
                    paramMap.put("modelCol",beginRow);
                    paramList.add(paramMap);
                }else if (info[i].contains("商品条形码") || info[i].contains("商品编码") || info[i].contains("商品助记符") || info[i].contains("商品店内码")) {
                    String goodsParamName = info[i].split("=")[0];
                    paramMap.put("paramname",goodsParamName);
                    paramMap.put("modelCol", value);
                    paramList.add(paramMap);
                } else if (info[i].contains("拆分所在列")) {
                    paramMap.put("paramname","拆分所在列");
                    paramMap.put("modelCol", value);
                    paramList.add(paramMap);
                    divideCol = ExcelUtils.chagneCellColtoNumber(value);
                } else if (info[i].contains("商品数量")) {
                    paramMap.put("paramname","商品数量");
                    paramMap.put("modelCol", value);
                    paramList.add(paramMap);
                } else if (info[i].contains("商品箱数")) {
                    paramMap.put("paramname","商品箱数");
                    paramMap.put("modelCol",value);
                    paramList.add(paramMap);
                } else if (info[i].contains("商品单价") ) {
                    paramMap.put("paramname","商品单价");
                    paramMap.put("modelCol", value);
                    paramList.add(paramMap);
                } else if (info[i].contains("客户单号位置")) {
                    paramMap.put("paramname","客户单号");
                    paramMap.put("modelCol", value);
                    paramList.add(paramMap);
                } else if (info[i].contains("客户单元格")) {
                    paramMap.put("paramname","客户单元格");
                    paramMap.put("modelCol", value);
                    paramList.add(paramMap);
                } else if (info[i].contains("备注")) {
                    paramMap.put("paramname","备注");
                    paramMap.put("modelCol", value);
                    paramList.add(paramMap);
                } else if (info[i].contains("日期/其它列")) {
                    paramMap.put("paramname","日期/其它列");
                    paramMap.put("modelCol", value);
                    paramList.add(paramMap);
                }else if (info[i].contains("客户正则") ) {
                    paramMap.put("paramname","客户正则");
                    paramMap.put("modelCol", value);
                    paramList.add(paramMap);
                }else if (info[i].contains("工作表") ) {
                    sheetnum = Integer.parseInt(value);
                }

            }
            map.put("paramList",paramList);
            map.put("beginRow",beginRow);
            map.put("divideCol",divideCol);
            if(sheetnum >-1){
                map.put("sheetnum",sheetnum);
            }
        }else{
            map.put("flag",false);
        }
        return map;

    }

    /**
     * 根据模板方法的参数组装参数值 传入前台
     * @throws
     * @author lin_xx
     * @date 2018-02-24
     */
    public Map organizeExcelData(Map parseMap,File importFile) throws Exception {
        Map map = new HashMap();
        List<Map> paramList = (List<Map>) parseMap.get("paramList");//参数组装
        int beginRow = (Integer) parseMap.get("beginRow");//模板商品开始列
        int divideCol = (Integer) parseMap.get("divideCol");//拆分所在列
        for (int i = 0; i < paramList.size(); i++) {
            Map m = paramList.get(i);
            String paramname = (String) m.get("paramname");
            String modelCol = m.get("modelCol").toString();
            if("商品条形码".equals(paramname) || "商品编码".equals(paramname)  || "商品助记符".equals(paramname)  || "商品店内码".equals(paramname)){
                int colnum =  ExcelUtils.chagneCellColtoNumber(modelCol);
                Map goodsMap = ExcelUtils.importExcelByColumn(importFile,beginRow,colnum);
                if(goodsMap.size() >0){
                    map.put("goodsValue",JSONUtils.mapToJsonStr(goodsMap));
                }else{
                    map.put("goodsValue","");
                }
                if(divideCol >-1){
                    Map divideMap = ExcelUtils.importExcelByColumn(importFile,beginRow,divideCol);
                    map.put("divideValue",JSONUtils.mapToJsonStr(divideMap));
                }
            }else if("商品数量".equals(paramname)){
                int colnum =  ExcelUtils.chagneCellColtoNumber(modelCol);
                Map goodsMap = ExcelUtils.importExcelByColumn(importFile,beginRow,colnum);
                if(goodsMap.size() >0){
                    map.put("goodsNumValue",JSONUtils.mapToJsonStr(goodsMap));
                }else{
                    map.put("goodsNumValue","");
                }
            }else if("商品箱数".equals(paramname)){
                int colnum =  ExcelUtils.chagneCellColtoNumber(modelCol);
                Map goodsBoxnumValue = ExcelUtils.importExcelByColumn(importFile,beginRow,colnum);
                if(goodsBoxnumValue.size() >0){
                    map.put("goodsBoxnumValue",JSONUtils.mapToJsonStr(goodsBoxnumValue));
                }else{
                    map.put("goodsBoxnumValue","");
                }
            }else if("商品单价".equals(paramname)){
                int colnum =  ExcelUtils.chagneCellColtoNumber(modelCol);
                Map goodsPriceValue = ExcelUtils.importExcelByColumn(importFile,beginRow,colnum);
                map.put("goodsPriceValue",JSONUtils.mapToJsonStr(goodsPriceValue));
            }else if("客户单号".equals(paramname)){
                if(divideCol >-1){
                    String Column = modelCol.replaceAll("\\d+", "");
                    int cidCol = ExcelUtils.chagneCellColtoNumber(Column);
                    Map customerBillidValue = ExcelUtils.importExcelByColumn(importFile,beginRow,cidCol);
                    map.put("customerBillid",JSONUtils.mapToJsonStr(customerBillidValue));
                }else{
                    String customerBillid = ExcelUtils.getSheetRowCellValue(importFile,0,modelCol);
                    map.put("customerBillid",customerBillid);
                }
            }else if("客户单元格".equals(paramname)){
                if(divideCol >-1){
                    String Column = modelCol.replaceAll("\\d+", "");
                    int customerCol = ExcelUtils.chagneCellColtoNumber(Column);
                    Map customerValue = ExcelUtils.importExcelByColumn(importFile,beginRow,customerCol);
                    map.put("customerValue",JSONUtils.mapToJsonStr(customerValue));
                }else{
                    String customerValue = ExcelUtils.getSheetRowCellValue(importFile,0,modelCol);
                    map.put("customerValue",customerValue);
                }
            }else if("备注".equals(paramname)){
                String remarkValue = ExcelUtils.getSheetRowCellValue(importFile,0,modelCol);
                map.put("remarkValue",remarkValue);
            }else if("日期/其它列".equals(paramname)){
                if(divideCol >-1){
                    String Column = modelCol.replaceAll("\\d+", "");
                    int Col = ExcelUtils.chagneCellColtoNumber(Column);
                    Map otherInfo = ExcelUtils.importExcelByColumn(importFile,beginRow,Col);
                    map.put("otherInfo",JSONUtils.mapToJsonStr(otherInfo));
                }else{
                    String otherInfo = ExcelUtils.getSheetRowCellValue(importFile,0,modelCol);
                    map.put("otherInfo",otherInfo);
                }

            }else if("客户正则".equals(paramname)){
                map.put("customerRegular",modelCol);
            }

        }
        return map;

    }

    /**
     * 组装导入数据
     * @throws
     * @author lin_xx
     * @date 2018-02-22
     */
    public String ImportMapToObject() throws Exception{

        Map map = new HashMap();
        String msg = "";
        boolean flag = false;
        List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
        String goodsValue = request.getParameter("goodsValue");
        if(StringUtils.isEmpty(goodsValue)){
            map.put("flag",false);
            map.put("msg","读取商品信息失败,请检查模板和参数是否正确！");
            addJSONObject(map);
            return SUCCESS;
        }
        String goodsNumValue = request.getParameter("goodsNumValue");
        String goodsBoxnumValue = request.getParameter("goodsBoxnumValue");
        Map goodsNumMap = new HashMap();
        Map goodsBoxnumMap = new HashMap();
        if(StringUtils.isEmpty(goodsNumValue) && StringUtils.isEmpty(goodsBoxnumValue)){
            map.put("flag",false);
            map.put("msg","读取商品数量失败,请检查模板和参数是否正确！");
            addJSONObject(map);
            return SUCCESS;
        }else if(StringUtils.isNotEmpty(goodsNumValue)){
            goodsNumMap = JSONUtils.jsonStrToMap(goodsNumValue);
        }else if(StringUtils.isNotEmpty(goodsBoxnumValue)){
            goodsBoxnumMap = JSONUtils.jsonStrToMap(goodsBoxnumValue);
        }

        String goodsPriceValue = request.getParameter("goodsPriceValue");
        String gtype = request.getParameter("gtype");
        String ctype = request.getParameter("ctype");
        //模板商品
        Map goodsMap = JSONUtils.jsonStrToMap(goodsValue);
        String divideValue = request.getParameter("divideValue");
        String otherInfo = request.getParameter("otherInfo");
        String customerBillid = request.getParameter("customerBillid");
        //拆分列
        if(StringUtils.isNotEmpty(divideValue)){
            Map divideMap = JSONUtils.jsonStrToMap(divideValue);
            Map divideColMap = new LinkedHashMap();//key 拆分列的值 value 拆分值相同的行号
            Iterator<Map.Entry<String, String>> it = divideMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                String divide = entry.getValue();
                if (StringUtils.isNotEmpty(divide)) {
                    String modelColumeValue = entry.getKey();
                    if (divideColMap.containsKey(divide)) {
                        String value = divideColMap.get(divide) + "";
                        divideColMap.put(divide, value + "," + modelColumeValue);
                    } else {
                        divideColMap.put(divide, modelColumeValue+ "");
                    }
                }
            }
            Iterator<Map.Entry<String, String>> divideColIt = divideColMap.entrySet().iterator();
            while (divideColIt.hasNext()) {
                Map.Entry<String, String> entry = divideColIt.next();
                String[] rownums = entry.getValue().split(",");
                Map orderGoodsMap = new HashMap();
                for (int i = 0; i < rownums.length; i++) {
                    orderGoodsMap.put(rownums[i],goodsMap.get(rownums[i]));
                }
                String customerInfo="";
                String customerValue = request.getParameter("customerValue");
                if(StringUtils.isNotEmpty(customerValue)){
                    Map customerMap = JSONUtils.jsonStrToMap(customerValue);
                    if(customerMap.containsKey(rownums[0])){
                        customerInfo = (String) customerMap.get(rownums[0]);
                    }
                }
                String other = "";//日期或其它
                if(StringUtils.isNotEmpty(otherInfo)){
                    Map otherMap = JSONUtils.jsonStrToMap(otherInfo);
                    if(otherMap.containsKey(rownums[0])){
                        other = (String) otherMap.get(rownums[0]);
                    }
                }
                String sourceid = "";//客户单号
                if(StringUtils.isNotEmpty(customerBillid)){
                    Map sourceMap = JSONUtils.jsonStrToMap(customerBillid);
                    if(sourceMap.containsKey(rownums[0])){
                        sourceid = (String) sourceMap.get(rownums[0]);
                    }
                }
                if (orderGoodsMap.size() > 0){
                    Map resultMap =
                            addImportData(orderGoodsMap,goodsNumMap,goodsBoxnumMap,goodsPriceValue,gtype,ctype,customerInfo,other,sourceid);
                    if(resultMap.containsKey("errorList")){
                        List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap.get("errorList");
                        errorList.addAll(list);
                    }
                    String addFlag = (String) resultMap.get("flag");
                    if("true".equals(addFlag)){
                        flag = true;
                    }
                    if(resultMap.containsKey("disablemsg")){
                        msg += (String) resultMap.get("disablemsg");
                    }
                }
            }
        }else{
            String customerInfo = request.getParameter("customerValue");
            Map resultMap =
                    addImportData(goodsMap,goodsNumMap,goodsBoxnumMap,goodsPriceValue,gtype,ctype,customerInfo,otherInfo,customerBillid);
            if(resultMap.containsKey("errorList")){
                List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap.get("errorList");
                errorList.addAll(list);
            }
            String addFlag = (String) resultMap.get("flag");
            if("true".equals(addFlag)){
                flag = true;
            }
            if(resultMap.containsKey("disablemsg")){
                msg += (String) resultMap.get("disablemsg");
            }
        }

        if(errorList.size() > 0){
            String fileid = createErrorDetailFile(errorList);
            msg += "&"+fileid ;
        }
        if(flag){
            map.put("msg","导入成功!"+msg);
        }else{
            map.put("msg","导入失败!"+msg);
        }
        addJSONObject(map);
        return SUCCESS;

    }

    /**
     * 根据数据组装单据值并插入数据库
     * @throws
     * @author lin_xx
     * @date 2018-02-23
     */
    public Map addImportData(Map goodsMap,Map goodsNumMap,Map goodsBoxnumMap,
                             String goodsPriceValue,String gtype ,String ctype,String customerInfo, String other,String sourceid) throws Exception {
        //系统参数（是否允许重复商品）
        String isreapt = getSysParamValue("IsSalesGoodsRepeat");
        //客户正则
        String customerRegular = request.getParameter("customerRegular");

        if("0".equals(isreapt)){
            Map goodsKeyMap = new HashMap();
            Iterator<Map.Entry> it = goodsMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = it.next();
                if(goodsKeyMap.containsValue(entry.getValue())){
                    //获取相同商品信息的key值（即第一个商品的所在行）
                    String firstRow = "";
                    Set set = goodsKeyMap.entrySet();
                    Iterator keyit=set.iterator();
                    while(keyit.hasNext()) {
                        Map.Entry entryKey = (Map.Entry) keyit.next();
                        if(entryKey.getValue().equals(entry.getValue())) {
                            firstRow = (String) entryKey.getKey();
                            break;
                        }
                    }
                    if(goodsNumMap.containsKey(firstRow) && goodsNumMap.containsKey(entry.getKey())){
                        int keyNum = Integer.parseInt(goodsNumMap.get(firstRow).toString());
                        int entryNum = Integer.parseInt(goodsNumMap.get(entry.getKey()).toString());
                        goodsNumMap.put(firstRow,keyNum+entryNum);
                    }

                }else{
                    goodsKeyMap.put(entry.getKey(),entry.getValue());
                }

            }
            goodsMap = goodsKeyMap;
        }
        //模板价格
        Map goodsPriceMap = new HashMap();
        if(StringUtils.isNotEmpty(goodsPriceValue)){
            goodsPriceMap = JSONUtils.jsonStrToMap(goodsPriceValue);
        }
        Iterator<Map.Entry> it = goodsMap.entrySet().iterator();
        List<ModelOrder> wareList = new ArrayList<ModelOrder>();
        while (it.hasNext()) {
            Map.Entry entry = it.next();
            ModelOrder modelOrder = new ModelOrder();
            modelOrder.setOtherMsg(other);
            modelOrder.setOrderId(sourceid);
            if("1".equals(gtype)){
                modelOrder.setBarcode((String) entry.getValue());
            }else if("2".equals(gtype)){
                modelOrder.setShopid((String) entry.getValue());
            }else if("3".equals(gtype)){
                modelOrder.setSpell((String) entry.getValue());
            }else if("4".equals(gtype)){
                modelOrder.setGoodsid((String) entry.getValue());
            }
            if(goodsNumMap.containsKey(entry.getKey())){
                modelOrder.setUnitnum(goodsNumMap.get(entry.getKey()).toString());
            }
            if(goodsBoxnumMap.containsKey(entry.getKey())){
                modelOrder.setBoxnum(goodsBoxnumMap.get(entry.getKey()).toString());
            }
            if(goodsPriceMap.containsKey(entry.getKey())){
                modelOrder.setTaxprice(goodsPriceMap.get(entry.getKey()).toString());
            }
            String remark = request.getParameter("remarkValue");
            modelOrder.setRemark(remark);
            String customerRefferid = request.getParameter("customerRefferid");
            String customerid = "";
            if("1".equals(ctype)){
                customerid = customerRefferid;
            }else{
                Map map1 = new HashMap();
                if(StringUtils.isNotEmpty(customerRegular)){
                    String cust = "";
                    //读取客户信息
                    Pattern pattern = Pattern.compile(customerRegular);
                    Matcher matcher = pattern.matcher(customerInfo);
                    while(matcher.find()){
                        cust += matcher.group(0);
                    }
                    customerInfo = cust;
                }
                if ("2".equals(ctype)) {
                    map1.put("pid", customerRefferid);//总店
                    map1.put("shopno", customerInfo);//店号
                    customerid = returnCustomerID(map1);
                } else if ("3".equals(ctype)) {
                    map1.put("shortcode", customerInfo);
                    customerid = returnCustomerID(map1);
                } else if ("4".equals(ctype)) {
                    map1.put("name", customerInfo);
                    customerid = returnCustomerID(map1);
                } else if ("5".equals(ctype)) {
                    map1.put("shortname", customerInfo);
                    customerid = returnCustomerID(map1);
                } else if ("6".equals(ctype)) {
                    map1.put("address", customerInfo);
                    customerid = returnCustomerID(map1);
                } else if ("7".equals(ctype)) {
                    customerid = customerInfo;
                }
            }
            modelOrder.setBusid(customerid);
            wareList.add(modelOrder);
        }
        Map map = salesOrderService.addMDSalesOrder(wareList, ctype, gtype);
        return map;

    }

    /**
     * 导出导入失败的商品明细
     * @author lin_xx
     * @date 2017/6/26
     */
    public String createErrorDetailFile(List<Map<String, Object>> errorList) throws  Exception{

        //模板文件路径
        String tempFilePath = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/importFalseDetail.xls");
        List<String> dataListCell = new ArrayList<String>();
        dataListCell.add("orderid");
        dataListCell.add("goodsid");
        dataListCell.add("unitnum");
        dataListCell.add("price");
        dataListCell.add("customerid");

        ExcelFileUtils handle = new ExcelFileUtils();
        handle.writeListData(tempFilePath, dataListCell, errorList, 0);

        String dateSubPath= CommonUtils.getYearMonthDayDirPath();
        String phyDirPath=CommonUtils.getUploadFilePhysicalDir("errorimportfile", dateSubPath);

        String fileName = CommonUtils.getDateTimeUUID();
        fileName= fileName+ ".xls";

        File errorFile = new File(phyDirPath, fileName);

        if(!errorFile.exists()){
            errorFile.createNewFile();
        }
        OutputStream os = new FileOutputStream(errorFile);
        //写到输出流并关闭资源
        handle.writeAndClose(tempFilePath, os);
        os.flush();
        os.close();
        handle.readClose(tempFilePath);

        String fullPath = "upload/errorimportfile/"+ dateSubPath + "/" + fileName;
        fullPath=CommonUtils.filterFilePathSaparator(fullPath);
        AttachFile attachFile = new AttachFile();
        attachFile.setExt(".xls");
        attachFile.setFilename(fileName);
        attachFile.setFullpath(fullPath);
        attachFile.setOldfilename(fileName);
        //将临时文件信息插入数据库
        attachFileService.addAttachFile(attachFile);
        String id = "";
        if(null!=attachFile){
            id=attachFile.getId();
        }
        return id;
    }

    /**
     * 将csv模板组装成excel
     * @throws
     * @author lin_xx
     * @date 2018-02-24
     */
    public Map convertCSVorTxt2Xls(File importFile) throws Exception {
        String id = "";
        String path= OfficeUtils.getFilepath() + "/csvToExcel/";
        File file2 = new File(path);
        //判断文件夹是否存在,如果不存在则创建文件夹
        if (!file2.exists()) {
            file2.mkdir();
        }
        String name = CommonUtils.getDataNumberWithRand()+".xls";
        path+= name ;

        ArrayList arList=null;
        ArrayList al=null;
        String readLint = "";
        InputStream raf = new FileInputStream(importFile);
        //设置输出内容格式，防止乱码
        String charsetName = ModelAction.getFileCodeString(importFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(raf,charsetName));
        int i=0;
        arList = new ArrayList();
        while ((readLint = reader.readLine()) != null){
            al = new ArrayList();
            String strar[] = readLint.split(",");
            for(int j=0;j<strar.length;j++){
                al.add(strar[j]);
            }
            arList.add(al);
            i++;
        }
        try{
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("csv数据");
            for(int k=0;k<arList.size();k++)
            {
                ArrayList ardata = (ArrayList)arList.get(k);
                HSSFRow row = sheet.createRow((short) 0+k);
                for(int p=0;p<ardata.size();p++){
                    HSSFCell cell = row.createCell((short) p);
                    String data = ardata.get(p).toString();
                    if(data.startsWith("=")){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        data=data.replaceAll("\"", "");
                        data=data.replaceAll("=", "");
                        cell.setCellValue(data);
                    }else if(data.startsWith("\"")){
                        data=data.replaceAll("\"", "");
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        cell.setCellValue(data);
                    }else{
                        data=data.replaceAll("\"", "");
                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(data);
                    }
                }
            }
            FileOutputStream fileOut = new FileOutputStream(path);
            hwb.write(fileOut);
            fileOut.close();
            AttachFile attachFile = new AttachFile();
            attachFile.setExt(".xls");
            attachFile.setFilename(name);
            attachFile.setFullpath(CommonUtils.filterFilePathSaparator(path));
            attachFile.setOldfilename(name);
            //将临时文件信息插入数据库
            attachFileService.addAttachFile(attachFile);
            if(null!=attachFile){
                id=attachFile.getId();
            }

        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
        Map map = new HashMap();
        map.put("fileid",id);
        map.put("file",new File(path));
        return map;
    }


}







