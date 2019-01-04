package com.hd.agent.sales.action;

import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.sales.model.ImportSet;
import com.hd.agent.sales.model.ModelOrder;
import com.hd.agent.sales.service.impl.ext.OrderExtServiceImpl;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lin_xx on 2015/9/11.
 */
public class ModelAction extends BaseSalesAction {

    private OrderExtServiceImpl salesOrderExtService;

    public OrderExtServiceImpl getSalesOrderExtService() {
        return salesOrderExtService;
    }

    public void setSalesOrderExtService(OrderExtServiceImpl salesOrderExtService) {
        this.salesOrderExtService = salesOrderExtService;
    }

    /**
     * 兴福兴销售模板导入
     *
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2015年9月10日
     */
    public String importHtmlByXFX() throws Exception {
        String busid = request.getParameter("busid");
        String ctype = request.getParameter("ctype");
        String gtype = request.getParameter("gtype");
        String fileparam = request.getParameter("fileparam");
        int rowCount = -1;
        if (StringUtils.isNotEmpty(fileparam)) {
            String[] info = fileparam.split(";");
            for (int i = 0; i < info.length; ++i) {
                if (info[i].contains("有效列")) {
                    rowCount = Integer.parseInt(info[i].split("=")[1]);
                } else {
                    rowCount = 15;
                }
            }
        }
        File file = importFile;
        List<String> paramList = new ArrayList<String>();
        //读取模板文件
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\r\n");
        }
        //读取订单开始的地方
        Pattern pattern = Pattern.compile(">([^<]*)</td>");
        Matcher matcher = pattern.matcher(sb.toString());
        int begingOrder = -1;
        while (matcher.find()) {
            String param = matcher.group(1).trim();
            if (StringUtils.isEmpty(param)) {
                //continue;
                paramList.add("null");
            } else if ("&nbsp;".equals(param)) {
                break;
            } else {
                paramList.add(param);
                if (param.contains("编码")) {
                    begingOrder = paramList.size() - 1;
                }
            }
        }
        //对信息进行组装
        List<ModelOrder> wareList = new ArrayList<ModelOrder>();
        for (int i = begingOrder + rowCount; i < paramList.size(); i = i + rowCount) {
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(paramList.get(i))) {
                ModelOrder modelOrder = new ModelOrder();
                modelOrder.setBarcode(paramList.get(i + 2));
                modelOrder.setUnitnum(paramList.get(i + 11));
                modelOrder.setBoxnum(paramList.get(i + 9));
                if ("1".equals(ctype)) {
                    modelOrder.setBusid(busid);
                }
                wareList.add(modelOrder);
            } else {
                continue;
            }
        }
        Map map = new HashMap();
        if (wareList.size() > 0) {
            if (StringUtils.isEmpty(wareList.get(0).getBusid())) {
                map.put("info", "busidEmpty");
            } else {
                map = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
            }
        } else {
            map.put("info", "empty");
        }
        modelMsg(map);
        return SUCCESS;
    }

    /**
     * 益康模板
     *
     * @return
     * @throws Exception
     */
    public String importHtmlByYK() throws Exception {

        String busid = request.getParameter("busid");
        String ctype = request.getParameter("ctype");
        String gtype = request.getParameter("gtype");
        String fileparam = request.getParameter("fileparam");

        int rowCount = -1;
        if (StringUtils.isNotEmpty(fileparam)) {
            String[] info = fileparam.split(";");
            for (int i = 0; i < info.length; ++i) {
                if (info[i].contains("有效列")) {
                    rowCount = Integer.parseInt(info[i].split("=")[1]);
                } else {
                    rowCount = 10;
                }
            }
        }
        File file = importFile;
        List<String> paramList = new ArrayList<String>();
        //读取模板文件
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\r\n");
        }
        //读取客户信息
        Pattern pattern = Pattern.compile(">([^<]+)</TD>");
        Matcher matcher = pattern.matcher(sb.toString());
        int begingOrder = -1;
        while (matcher.find()) {
            String param = matcher.group(1).trim();
            if ("".equals(param)) {
                //continue;
                paramList.add("null");
            } else if ("&nbsp;".equals(param)) {
                break;
            } else {
                paramList.add(param);
                if (param.contains("商品编码")) {
                    begingOrder = paramList.size() - 1;
                }
            }
        }
        //对信息进行组装
        List<ModelOrder> wareList = new ArrayList<ModelOrder>();
        for (int i = begingOrder + rowCount; i < paramList.size(); i = i + rowCount) {
            if (i + rowCount <= paramList.size()) {
                ModelOrder modelOrder = new ModelOrder();
                modelOrder.setBarcode(paramList.get(i + 2));
                modelOrder.setUnitnum(paramList.get(i + 7));
                modelOrder.setBoxnum(paramList.get(i + 6));
                if ("1".equals(ctype)) {
                    modelOrder.setBusid(busid);
                }
                wareList.add(modelOrder);
            } else {
                continue;
            }
        }
        Map map = new HashMap();
        if (wareList.size() > 0) {
            if (StringUtils.isEmpty(wareList.get(0).getBusid())) {
                map.put("info", "busidEmpty");
            } else {
                map = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
            }
        } else {
            map.put("info", "empty");
        }
        modelMsg(map);

        return SUCCESS;
    }

    /**
     * 汉广销售模板导入
     *
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016年9月10日
     */
    public String importHtmlByHG() throws Exception {
        String busid = request.getParameter("busid");
        String ctype = request.getParameter("ctype");
        String gtype = request.getParameter("gtype");
        File file = importFile;
        List<String> paramList = new ArrayList<String>();
        //读取模板文件
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\r\n");
        }
        //读取客户信息
        Pattern pattern = Pattern.compile(">([^<]+)</span>");
        Matcher matcher = pattern.matcher(sb.toString());
        String shopno = "";
        String customername = "";
        int infoCount = 0;
        while (matcher.find()) {
            ++infoCount;
            String param = matcher.group(1).trim();
            if (infoCount == 3) {//客户对应的店号
                shopno = param;
            } else if (infoCount == 4) {//客户名称
                customername = param;
            }
        }
        //列数统计
        int rowCount = 0;
        pattern = Pattern.compile(">([^<]+)</th");
        matcher = pattern.matcher(sb.toString());
        while (matcher.find()) {
            ++rowCount;
        }
        //读取订单信息
        pattern = Pattern.compile(">([^<]+)</td");
        matcher = pattern.matcher(sb.toString());
        while (matcher.find()) {
            String param = matcher.group(1).trim();
            if ("".equals(param)) {
                //continue;
                paramList.add("null");
            } else if ("&nbsp;".equals(param)) {
                break;
            } else {
                paramList.add(param);
            }
        }
        if ("null".equals(paramList.get(0))) {
            paramList.remove(0);
        }
        //对信息进行组装
        List<ModelOrder> wareList = new ArrayList<ModelOrder>();
        for (int i = 0; i < paramList.size(); i = i + rowCount) {
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(paramList.get(i))) {
                ModelOrder modelOrder = new ModelOrder();
                modelOrder.setCustomername(customername);
                modelOrder.setBarcode(paramList.get(i + 1));
                modelOrder.setShopid(paramList.get(i));
                modelOrder.setUnitnum(paramList.get(i + 4));
                modelOrder.setBoxnum(paramList.get(i + 5));
                //汉广订单 先取箱数再取数量
                if (StringUtils.isNotEmpty(modelOrder.getBoxnum()) && !"0.00".equals(modelOrder.getBoxnum())) {
                    modelOrder.setUnitnum("0");
                }
                //根据条件返回对应的客户
                Map map1 = new HashMap();
                if ("1".equals(ctype)) {
                    modelOrder.setBusid(busid);

                } else if ("2".equals(ctype) && busid != "" && shopno != "") {
                    map1.put("pid", busid);//总店
                    map1.put("shopno", shopno);//店号
                    modelOrder.setBusid(returnCustomerID(map1));//返回总店下对应店号的客户
                    modelOrder.setMainbusid(busid);

                } else if ("4".equals(ctype) && customername != "") {
                    map1.put("name", customername);
                    modelOrder.setBusid(returnCustomerID(map1));

                }
                wareList.add(modelOrder);
            } else {
                continue;
            }
        }
        Map map = new HashMap();
        if (wareList.size() > 0) {
            if (StringUtils.isEmpty(wareList.get(0).getBusid())) {
                map.put("info", "busidEmpty");
            } else {
                map = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
            }
        } else {
            map.put("info", "empty");
        }
        modelMsg(map);
        return SUCCESS;
    }

    /**
     * 统一大仓销售模板导入
     *
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016年9月10日
     */
    public String importHtmlByDC() throws Exception {
        String busid = request.getParameter("busid");
        String ctype = request.getParameter("ctype");
        String gtype = request.getParameter("gtype");
        File file = importFile;
        List<String> paramList = new ArrayList<String>();
        //读取模板文件
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\r\n");
        }

        int begingColumn = 0;
        int rowCount = -1;
        //读取订单信息
        Pattern pattern = Pattern.compile(">([^<]*)</td");
        Matcher matcher = pattern.matcher(sb.toString());
        while (matcher.find()) {
            String param = matcher.group(0).trim();
            if (param.contains(">&nbsp;</td")) {
                continue;
            } else {
                param = param.replace(">\r\n", "");
                param = param.replace("\r\n", "");
                param = param.replace("</td", "");
                param = param.replace(">", "");
                param = param.trim();
                if ("序号".equals(param)) {
                    begingColumn = paramList.size();
                }
                if ("商品条码".equals(param)) {
                    rowCount = paramList.size() - begingColumn + 1;
                }
                paramList.add(param);
            }
        }
        //获取客户单号
        String orderid = "";
        pattern = Pattern.compile("lbOrderNo\">([^<]*)</span");
        matcher = pattern.matcher(sb.toString());
        while (matcher.find()) {
            orderid = matcher.group(1).trim();

        }
        //对信息进行组装
        List<ModelOrder> wareList = new ArrayList<ModelOrder>();
        for (int i = begingColumn; i < paramList.size(); i = i + rowCount) {
            if (i + rowCount > paramList.size()) {
                continue;
            } else {
                ModelOrder modelOrder = new ModelOrder();
                modelOrder.setBarcode(paramList.get(i + 12));//商品条码
                modelOrder.setUnitnum(paramList.get(i + 8));//数量
                modelOrder.setBoxnum(paramList.get(i + 6));//箱数
                modelOrder.setBusid(busid);
                modelOrder.setOrderId(orderid);
                wareList.add(modelOrder);
            }

        }
        Map map = new HashMap();
        if (wareList.size() > 0) {
            if (StringUtils.isEmpty(wareList.get(0).getBusid())) {
                map.put("info", "busidEmpty");
            } else {
                map = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
            }
        } else {
            map.put("info", "empty");
        }
        modelMsg(map);
        return SUCCESS;
    }

    /**
     * 台客隆销售模板导入
     *
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016年1月11日
     */
    public String importHtmlByTKL() throws Exception {
        String busid = request.getParameter("busid");
        String ctype = request.getParameter("ctype");
        String gtype = request.getParameter("gtype");
        File file = importFile;
        List<String> paramList = new ArrayList<String>();
        //读取模板文件
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        boolean flag = false;
        String orderID = "";
        while ((line = br.readLine()) != null) {
            if (line.contains("商品代码")) {
                flag = true;
            }
            if (line.contains("合计")) {
                flag = false;
            }
            if (line.contains("inp_receiptid") && line.contains("value")) {
                orderID = line;
            }
            if (flag) {
                sb.append(line + "\r\n");
            }
        }
        //读取模板里的订单号
        orderID = orderID.substring(orderID.indexOf("value"), orderID.indexOf("readonly"));
        String[] strings = orderID.split("\"");
        orderID = strings[1];

        //读取数据
        Pattern pattern = Pattern.compile(">([^<]+)</td");
        Matcher matcher = pattern.matcher(sb.toString());
        while (matcher.find()) {
            String param = matcher.group(1).trim();
            //去除首行单元格里的冗余和空单元格
            if ("订货".equals(param) || "实供".equals(param) || "".equals(param)) {
                continue;
            } else {
                paramList.add(param);
            }
        }
        //对信息进行组装
        List<ModelOrder> wareList = new ArrayList<ModelOrder>();
        for (int i = 0; i < paramList.size(); i = i + 13) {//该模板有13个字段
            if (StringUtils.isNotEmpty(paramList.get(i))) {
                ModelOrder modelOrder = new ModelOrder();
                modelOrder.setShopid(paramList.get(i));
                modelOrder.setBarcode(paramList.get(i + 1));
                modelOrder.setUnitnum(paramList.get(i + 5));
                modelOrder.setBoxnum(paramList.get(i + 8));
                modelOrder.setOrderId(orderID);
                modelOrder.setBusid(busid);
                wareList.add(modelOrder);
            } else {
                continue;
            }
        }
        Map map = new HashMap();
        if (wareList.size() > 0) {
            if (StringUtils.isEmpty(wareList.get(0).getBusid())) {
                map.put("info", "busidEmpty");
            } else {
                map = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
            }
        } else {
            map.put("info", "empty");
        }
        modelMsg(map);

        return SUCCESS;
    }

    /**
     * 联华销售模板导入
     *
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2015年10月10日
     */
    public String importHtmlByLH() throws Exception {
        String busid = request.getParameter("busid");
        String ctype = request.getParameter("ctype");
        String gtype = request.getParameter("gtype");
        File file = importFile;
        //读取模板文件
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\r\n");
        }
        //读取订单基本信息
        Pattern pattern = Pattern.compile(">([^<]+)</td");
        Matcher matcher = pattern.matcher(sb.toString());
        List<String> paramList = new ArrayList<String>();
        String orderId = "";
        String cid = "";
        while (matcher.find()) {
            String param = matcher.group(1).trim();
            if (param.contains("订货单编号")) {
                orderId = param.substring(param.indexOf("：") + 1, param.length());
            }
            if ("1".equals(ctype)) {
                cid = busid;
            } else if ("2".equals(ctype) && param.contains("门店")) {
                String shopno = "";
                for (int i = 0; i < param.length(); i++) {
                    if (param.charAt(i) >= 48 && param.charAt(i) <= 57) {
                        shopno += param.charAt(i);
                    }
                }
                Map map1 = new HashMap();
                map1.put("pid", busid);//指定总店
                map1.put("shopno", shopno);//客户店号
                if (busid != "" && shopno != "") {
                    cid = returnCustomerID(map1);
                }
            }
            paramList.add(param);
        }
        //对读取的信息进行组装
        List<ModelOrder> wareList = new ArrayList<ModelOrder>();
        for (int i = 46; i < paramList.size(); i = i + 21) {
            String param = paramList.get(i);
            pattern = Pattern.compile("^[0-9]*$");
            matcher = pattern.matcher(param);
            if (matcher.find()) {
                ModelOrder modelOrder = new ModelOrder();
                modelOrder.setBarcode(param);
                modelOrder.setShopid(paramList.get(i + 1));
                modelOrder.setBusid(cid);
                modelOrder.setOrderId(orderId);
                wareList.add(modelOrder);
            }
        }
        //读取订单数量信息
        pattern = Pattern.compile(".*[hH][tT]\">([^<]+)</td");
        matcher = pattern.matcher(sb.toString());
        List<String> amountList = new ArrayList<String>();
        while (matcher.find()) {
            String param = matcher.group(1).trim();
            amountList.add(param);
        }
        //给每个条码配置相应的数量
        List<String> numList = new ArrayList<String>();
        for (int i = 3; i < amountList.size(); i = i + 9) {
            numList.add(amountList.get(i));
        }
        if (numList.size() >= wareList.size()) {
            for (int j = 0; j < wareList.size(); j++) {
                wareList.get(j).setUnitnum(numList.get(j));
            }
        }

        Map map = new HashMap();
        if (wareList.size() > 0) {
            if (StringUtils.isEmpty(wareList.get(0).getBusid())) {
                map.put("info", "busidEmpty");
            } else {
                map = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
            }
        } else {
            map.put("info", "empty");
        }
        modelMsg(map);

        return SUCCESS;
    }

    /**
     * 常客隆销售模板导入
     *
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2015年10月12日
     */
    public String importHtmlByCKL() throws Exception {
        String busid = request.getParameter("busid");
        String ctype = request.getParameter("ctype");
        String gtype = request.getParameter("gtype");
        File file = importFile;
        //读取模板文件
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\r\n");
        }
        Pattern pattern = Pattern.compile(".*\">([^<]+)");
        Matcher matcher = pattern.matcher(sb.toString());
        List<String> paramList = new ArrayList<String>();
        while (matcher.find()) {
            String param = matcher.group(1);
            paramList.add(param);
        }
        //对读取的信息进行组装
        List<ModelOrder> wareList = new ArrayList<ModelOrder>();
        for (int i = 45; i < paramList.size() - 10; i = i + 10) {
            ModelOrder modelOrder = new ModelOrder();
            if ("".equals(paramList.get(i).trim())) {//单子与单子之间商品信息的间隙为14个间距
                i = i + 14;
            }
            if (!"".equals(paramList.get(i))) {
                modelOrder.setBarcode(paramList.get(i));
                modelOrder.setShopid(paramList.get(i + 1));
                modelOrder.setUnitname(paramList.get(i + 7));
                modelOrder.setBusid(busid);
                String num = paramList.get(i + 6);
                if (num.charAt(0) >= 0x4e00 && num.charAt(0) <= 0x9fbb) {//验证数量是否为数字，预防订单有些单元格为图片读不出来
                    num = paramList.get(i + 5);
                    --i;
                }
                String num1 = "";
                for (int j = 0; j < num.length(); j++) {//循环输出小数位前面的数量
                    char a = '.';
                    if (a != num.charAt(j)) {
                        num1 += num.charAt(j);
                    } else {
                        break;
                    }
                }
                modelOrder.setUnitnum(num1);
                wareList.add(modelOrder);
            }
        }

        Map map = new HashMap();
        if (wareList.size() > 0) {
            if (StringUtils.isEmpty(wareList.get(0).getBusid())) {
                map.put("info", "busidEmpty");
            } else {
                map = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
            }
        } else {
            map.put("info", "empty");
        }
        modelMsg(map);
        return SUCCESS;
    }

    /**
     * 永辉销售模板导入
     *
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2015年10月14日
     */
    public String importHtmlByYH() throws Exception {
        String busid = request.getParameter("busid");
        String gtype = request.getParameter("gtype");
        String ctype = request.getParameter("ctype");
        File file = importFile;
        //读取模板文件
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\r\n");
        }
        Pattern pattern = Pattern.compile(">([^<]+)</t");
        Matcher matcher = pattern.matcher(sb.toString());
        List<String> paramList = new ArrayList<String>();
        while (matcher.find()) {
            String param = matcher.group(1).trim();
            paramList.add(param);
        }

        List<ModelOrder> wareList = new ArrayList<ModelOrder>();
        for (int i = 68; i < paramList.size(); i = i + 13) {
            ModelOrder modelOrder = new ModelOrder();
            String num = paramList.get(i + 3);
            if (num.contains(".")) {
                num = num.substring(0, num.indexOf("."));//截取小数点前面的数字
                modelOrder.setUnitnum(num);
                modelOrder.setShopid(paramList.get(i - 1));
                modelOrder.setBarcode(paramList.get(i));
                modelOrder.setBusid(busid);
                wareList.add(modelOrder);
            } else {
                break;
            }
        }

        Map map = new HashMap();
        if (StringUtils.isEmpty(wareList.get(0).getBusid())) {
            map.put("info", "busidEmpty");
        } else {
            if (wareList.size() > 0) {
                map = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
            } else {
                map.put("info", "empty");
            }
        }
        modelMsg(map);

        return SUCCESS;
    }

    /**
     * 永旺销售模板导入
     *
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2015年10月14日
     */
    public String importExcelByYW() throws Exception {
        String busid = request.getParameter("busid");
        String ctype = request.getParameter("ctype");
        String gtype = request.getParameter("gtype");
        String pricetype = request.getParameter("pricetype");

        List<String> paramList = ExcelUtils.importFirstRowByIndex(importFile, 6);//第7行为信息开始行
        boolean paramflag = true;
        for (String param : paramList) {
            String regEx = "[\u4e00-\u9fa5]";//是否有文字
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(param);
            if (m.find()) {
                paramflag = false;
            }
        }
        if (paramflag) {
            paramList = ExcelUtils.importFirstRowByIndex(importFile, 7);//第8行为信息开始行
        }
        String param = paramList.get(1);
        if (param.equals("null") || param.equals("")) {
            paramList.remove(1);
        }
        //读取导入文件
        InputStream is = new FileInputStream(importFile);
        Workbook hssfWorkbook = null;
        String type = ExcelUtils.getFileByFile(importFile);
        if ("xls".equals(type)) {
            hssfWorkbook = new HSSFWorkbook(is);
        } else if ("xlsx".equals(type)) {
            hssfWorkbook = new XSSFWorkbook(importFile.getPath());
        }
        List<String> rowInfo = ExcelUtils.importRowInfoBySheet(importFile, 3, 0);//旧版永旺从第4行读取客户信息
        String customerInfo = "", oldOrderID = "", mainbusid = "";
        for (String info : rowInfo) {
            if (info.contains("店铺")) {//店铺中包含 店号 + 店名
                int cindex = rowInfo.indexOf(info) + 1;
                customerInfo = rowInfo.get(cindex);
                break;
            }
        }

        if (org.apache.commons.lang3.StringUtils.isEmpty(customerInfo)) {
            rowInfo = ExcelUtils.importRowInfoBySheet(importFile, 4, 0);//新版永旺从第5行读取客户信息
            for (String info : rowInfo) {
                if (info.contains("店铺")) {//店铺中包含 店号 + 店名
                    int cindex = rowInfo.indexOf(info) + 2;
                    customerInfo = rowInfo.get(cindex);
                    break;
                }
            }
        }

        if ("2".equals(ctype)) {
            int splitIndex = customerInfo.indexOf(" ") + 1;
            String shopno = "";
            if (splitIndex > 0) {
                shopno = customerInfo.substring(0, splitIndex - 1);
            }
            Map map1 = new HashMap();
            map1.put("shopno", shopno);
            map1.put("pid", busid);
            mainbusid = busid;
            busid = returnCustomerID(map1);

        } else if ("4".equals(ctype)) {
            int splitIndex = customerInfo.indexOf(" ") + 1;
            String name = customerInfo.substring(splitIndex, customerInfo.length());
            Map map1 = new HashMap();
            map1.put("name", name);
            busid = returnCustomerID(map1);
        }
        Map map = new HashMap();
        map.put("gtype", gtype);
        Map barcodeMap = new HashMap();
        if (StringUtils.isEmpty(busid)) {
            map.put("info", "busidEmpty");
        } else {
            String emptySheet = "";
            int count = 0;
            if (paramList.size() > 0) {
                for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                    if (paramflag) {
                        list = ExcelUtils.importExcelBySheet(importFile, paramList, 8, numSheet);//从第9行开始读取数据
                    } else {
                        list = ExcelUtils.importExcelBySheet(importFile, paramList, 7, numSheet);//从第8行开始读取数据
                    }

                    //根据客户插入单据
                    List<String> idInfo = ExcelUtils.importRowInfoBySheet(importFile, 2, numSheet);//永旺订单 从第3行读取订单号码
                    for (String info : idInfo) {
                        if (info.contains("订单号码")) {
                            int cindex = idInfo.indexOf(info) + 1;
                            oldOrderID = idInfo.get(cindex);
                        }
                    }
                    if (org.apache.commons.lang3.StringUtils.isEmpty(oldOrderID)) {
                        idInfo = ExcelUtils.importRowInfoBySheet(importFile, 3, numSheet);//永旺订单 从第4行读取订单号码
                        for (String info : idInfo) {
                            if (info.contains("订单号码")) {
                                int cindex = idInfo.indexOf(info) + 1;
                                oldOrderID = idInfo.get(cindex);
                            }
                        }
                    }
                    List<ModelOrder> wareList = new ArrayList<ModelOrder>();
                    for (int i = 0; i < list.size(); i++) {
                        ModelOrder modelOrder = new ModelOrder();
                        modelOrder.setBusid(busid);
                        modelOrder.setMainbusid(mainbusid);
                        modelOrder.setOrderId(oldOrderID);
                        modelOrder.setBarcode((String) list.get(i).get("条码"));
                        modelOrder.setShopid((String) list.get(i).get("货号"));
                        modelOrder.setUnitnum((String) list.get(i).get("订货数量"));
                        if ("1".equals(pricetype)) {
                            modelOrder.setTaxprice((String) list.get(i).get("进单价"));
                        }
                        wareList.add(modelOrder);
                    }
                    if (wareList.size() > 0) {
                        Map insertMap = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
                        String flag = (String) insertMap.get("flag");
                        if ("true".equals(flag)) {
                            if (insertMap.containsKey("barcode")) {
                                String barcode = insertMap.get("barcode").toString();
                                barcode = barcode.replace("[", "");
                                barcode = barcode.replace("]", "");
                                barcodeMap.put(Integer.valueOf(numSheet), barcode);
                            } else if (insertMap.containsKey("shopid")) {
                                String barcode = insertMap.get("shopid").toString();
                                barcode = barcode.replace("[", "");
                                barcode = barcode.replace("]", "");
                                barcodeMap.put(Integer.valueOf(numSheet), barcode);
                            } else {
                                map.put("sheet", true);
                            }
                        } else if ("false".equals(flag)) {
                            if (emptySheet == "") {
                                emptySheet = (numSheet + 1) + "";
                                ++count;
                            } else {
                                emptySheet += "," + (numSheet + 1);
                                ++count;
                            }
                        }
                    } else {
                        if (emptySheet == "") {
                            emptySheet = (numSheet + 1) + "";
                            ++count;
                        } else {
                            emptySheet += "," + (numSheet + 1);
                            ++count;
                        }
                    }
                }
            } else {
                map.put("info", "empty");
            }
            if (emptySheet != "" && count != hssfWorkbook.getNumberOfSheets()) {
                map.put("emptysheet", emptySheet);
            } else if (count == hssfWorkbook.getNumberOfSheets()) {
                map.put("info", "empty");
            }
        }
        sheetModelMsg(barcodeMap, map);
        return SUCCESS;
    }

    /**
     * 工作表导入提醒
     *
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2015年10月10日
     */
    public void sheetModelMsg(Map barcodeMap, Map map) throws Exception {
        String barcodeMSG = "";
        if (map.containsKey("info") && barcodeMap.size() == 0) {
            request.setAttribute("msg", "客户编号不存在,请检查相关配置或联系管理员.");
        } else if (map.containsKey("num")) {
            request.setAttribute("msg", "模板中读取数量为空,请检查模板和方法参数是否匹配.");
        } else if (barcodeMap.size() > 0) {
            if(barcodeMap.containsKey("count")){
                barcodeMSG = barcodeMap.get("count")+"条数据导入成功;";
                barcodeMap.remove("count");
            }else{
                barcodeMSG = "数据导入成功;";
            }
            Iterator<Map.Entry> entries = barcodeMap.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry entry = entries.next();
                if ("1".equals(map.get("gtype"))) {
                    barcodeMSG = barcodeMSG + "第" + entry.getKey() + "张订单没有对应条形码:" + entry.getValue() + ";";
                } else if ("2".equals(map.get("gtype"))) {
                    barcodeMSG = barcodeMSG + "第" + entry.getKey() + "张订单没有对应店内码:" + entry.getValue() + ";";
                } else if ("3".equals(map.get("gtype"))) {
                    barcodeMSG = barcodeMSG + "第" + entry.getKey() + "张订单没有对应助记符:" + entry.getValue() + ";";
                } else if(("0".equals(map.get("gtype")) || "4".equals(map.get("gtype"))) ){
                    barcodeMSG = barcodeMSG + "第" + entry.getKey() + "张订单没有对应编码:" + entry.getValue() + ";";
                }
            }
            if(map.containsKey("customerid")){
                barcodeMSG = barcodeMSG + "客户编号:"+map.get("customerid")+"不存在";
            }
            if (map.containsKey("errorsheet")) {
                barcodeMSG = barcodeMSG + "第" + map.get("errorsheet") + "张工作表客户编号不存在;";
            }
            if (map.containsKey("emptysheet")) {
                barcodeMSG = barcodeMSG + "第" + map.get("emptysheet") + "张工作表导入不成功;";
            }
            if (map.containsKey("disablemsg")) {
                barcodeMSG += map.get("disablemsg");
            }
            if (barcodeMSG == "") {
                barcodeMSG = "数据导入成功;";
            }else{
                List<Map<String, Object>> errorList = (List<Map<String, Object>>) map.get("errorList");
                if(null != errorList && errorList.size() > 0){
                    String fileid = createErrorDetailFile(errorList);
                    barcodeMSG += "&"+fileid ;
                }
            }
            request.setAttribute("msg", barcodeMSG);
        } else if (map.containsKey("info")) {
            request.setAttribute("msg", "没有符合的导入数据,请检查导入模板或数据是否有误");
        } else if (map.containsKey("emptysheet")) {
            //有工作表商品全部倒入成功时加入键sheet
            if (map.containsKey("sheet")) {
                barcodeMSG = barcodeMSG + "导入成功!";
            }
            barcodeMSG = barcodeMSG + "第" + map.get("emptysheet") + "张工作表导入不成功;";
            if (map.containsKey("errorsheet")) {
                barcodeMSG = barcodeMSG + "第" + map.get("errorsheet") + "张工作表客户编号不存在;";
            }
            if (map.containsKey("disablemsg")) {
                barcodeMSG += map.get("disablemsg");
            }
            request.setAttribute("msg", barcodeMSG);
        } else if (map.containsKey("disablemsg")) {
            request.setAttribute("msg", "数据导入成功" + map.get("disablemsg"));
        } else if(map.containsKey("errorsheet")){
            request.setAttribute("msg", "第" + map.get("errorsheet") + "张工作表客户编号不存在;");
        }else {
            request.setAttribute("msg", "数据导入成功");
        }
    }

    /**
     * HTML模板导入提醒
     *
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2015年10月10日
     */
    public void modelMsg(Map map) throws Exception {

        String flag = (String) map.get("flag");
        String empty = (String) map.get("info");
        if ("busidEmpty".equals(empty)) {
            request.setAttribute("msg", "客户编号不存在，请检查相关配置或联系管理员");
        } else if ("true".equals(flag)) {
            if (map.containsKey("barcode")) {
                String barcode = map.get("barcode").toString();
                barcode = barcode.replaceAll("\\'", "");
                if (map.containsKey("disablemsg")) {
                    request.setAttribute("msg", "数据导入成功,其中条形码:" + barcode + "没有对应商品" + map.get("disablemsg").toString());
                } else {
                    request.setAttribute("msg", "数据导入成功,其中条形码:" + barcode + "没有对应商品");
                }
            } else if (map.containsKey("shopid")) {
                String shopid = map.get("shopid").toString();
                shopid = shopid.replaceAll("\\'", "");
                if (map.containsKey("disablemsg")) {
                    request.setAttribute("msg", "数据导入成功,其中店内码:" + shopid + "没有对应商品" + map.get("disablemsg").toString());
                } else {
                    request.setAttribute("msg", "数据导入成功,其中店内码:" + shopid + "没有对应商品");
                }
            } else {
                if (map.containsKey("disablemsg")) {
                    request.setAttribute("msg", "数据导入成功" + map.get("disablemsg").toString());
                } else {
                    request.setAttribute("msg", "数据导入成功");
                }

            }


        } else {
            //text文档的提醒
            if (map.containsKey("msg")) {
                String msg = (String) map.get("msg");
                request.setAttribute("msg", msg);
            } else if (map.containsKey("success")) {
                request.setAttribute("msg", "数据导入成功");
            } else if (map.containsKey("info")) {
                request.setAttribute("msg", "没有符合的导入数据,请检查导入模板或数据是否有误");
            } else {
                request.setAttribute("msg", "数据导入失败");
            }
        }

    }

    /**
     * 华润销售模板导入
     *
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2015年10月10日
     */
    public String importExcelByHR() throws Exception {
        String busid = request.getParameter("busid");
        String ctype = request.getParameter("ctype");
        String gtype = request.getParameter("gtype");
        String pricetype = request.getParameter("pricetype");

        List<String> paramList = new ArrayList<String>();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        String type = ExcelUtils.getFileByFile(importFile);
        if (StringUtils.isEmpty(type)) {
            InputStream is = new FileInputStream(importFile);
            list = readExcelByXml(is);
        } else {
            paramList = ExcelUtils.importExcelFirstRow(importFile); //获取第一行数据为字段的描述列表
            list = ExcelUtils.importExcel(importFile, paramList); //获取导入数据
        }
        List<ModelOrder> wareList = new ArrayList<ModelOrder>();
        for (int i = 0; i < list.size(); i++) {
            ModelOrder modelOrder = new ModelOrder();
            modelOrder.setBarcode((String) list.get(i).get("条形码"));
            modelOrder.setBoxnum((String) list.get(i).get("订货箱数"));
            modelOrder.setShopid((String) list.get(i).get("商品编码"));
            String numInfo = (String) list.get(i).get("订货数");
            if (StringUtils.isNotEmpty(numInfo)) {
                //对数量和单位进行分割 取数量
                Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]+|\\d+");
                Matcher m = p.matcher(numInfo);
                if (m.find()) {
                    modelOrder.setUnitnum(m.group());
                }
            } else {
                modelOrder.setUnitnum("0");
            }
            if ("1".equals(pricetype)) {
                modelOrder.setTaxprice((String) list.get(i).get("进价"));
            }
            if ("1".equals(ctype)) {
                modelOrder.setBusid(busid);
            } else if ("6".equals(ctype)) {
                String address = (String) list.get(0).get("要货地");
                Map map1 = new HashMap();
                map1.put("address", address);//按地址分配
                if (address != "" && !"".equals(returnCustomerID(map1))) {
                    busid = returnCustomerID(map1);
                    modelOrder.setBusid(busid);
                }
            }
            wareList.add(modelOrder);
        }

        Map map = new HashMap();
        if (StringUtils.isEmpty(busid)) {
            map.put("info", "busidEmpty");
        } else {
            if (wareList.size() > 0) {
                map = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
            } else {
                map.put("info", "empty");
            }
        }
        modelMsg(map);
        return SUCCESS;
    }

    /**
     * IO流读取Excel表格xml格式数据
     * 根据读取的参数自主判断有效列返回商品信息
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> readExcelByXml(InputStream inputStream) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
        StringBuilder sb = new StringBuilder();
        int rowCount = 0, countCell = 0;
        int column = 0;
        String line = null;
        List<String> paramList = new ArrayList<String>();
        List<String> wareList = new ArrayList<String>();
        while ((line = br.readLine()) != null) {
            Pattern pattern = Pattern.compile("<Row>");
            Matcher matcher = pattern.matcher(line);
            while ((matcher.find())) {
                ++rowCount;
            }
            pattern = Pattern.compile("<Cell>");
            matcher = pattern.matcher(line);
            while ((matcher.find())) {
                ++countCell;
            }
            sb.append(line + "\r\n");
        }
        column = countCell / rowCount;//计算出Excel中的列数
        Pattern pattern = Pattern.compile("<Data.*?/Data>");//取出每个单元格里的数据
        Matcher matcher = pattern.matcher(sb.toString());
        int count = 0;
        while (matcher.find()) {
            String param = matcher.group(0).replaceAll("<Data ss:Type=\"Number\">|<Data ss:Type=\"String\">|</Data>|<Data ss:Type=\"String\" x:Ticked=\"1\">", "");
            if (count < column) {
                paramList.add(param);
                ++count;
            } else {
                wareList.add(param);
            }
        }
        //将读取到的文件数据进行组装
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map map = new HashMap();
        int guide = 0;
        for (int i = 0; i < wareList.size(); ++i) {
            if (guide < column) {
                map.put(paramList.get(guide), wareList.get(i));
                ++guide;
            } else {
                list.add(map);
                map = new HashMap();
                --i;
                guide = 0;
            }
        }
        list.add(map);
        return list;
    }

    /**
     * 山东统一单店模板
     *
     * @return
     * @throws Exception
     */
    public String importXmlByTY() throws Exception {
        String busid = request.getParameter("busid");
        String ctype = request.getParameter("ctype");
        String gtype = request.getParameter("gtype");
        String pricetype = request.getParameter("pricetype");

        List<String> paramList = new ArrayList<String>();
        String type = ExcelUtils.getFileByFile(importFile);
        if (StringUtils.isEmpty(type)) {
            InputStream is = new FileInputStream(importFile);
            paramList = readInfoByXml(is, "山东统一单店");
        }
        //客户单号取传票号码
        String orderId = paramList.get(5);
        List<ModelOrder> wareList = new ArrayList<ModelOrder>();
        for (int i = 36; i < paramList.size(); i = i + 11) {
            ModelOrder modelOrder = new ModelOrder();
            modelOrder.setOrderId(orderId);
            modelOrder.setBusid(busid);
            modelOrder.setBarcode(paramList.get(i + 1));
            modelOrder.setUnitnum(paramList.get(i + 4));
            if ("1".equals(pricetype)) {
                modelOrder.setTaxprice(paramList.get(i + 7));
            }
            wareList.add(modelOrder);

        }
        Map map = new HashMap();
        if (StringUtils.isEmpty(busid)) {
            map.put("info", "busidEmpty");
        } else {
            if (wareList.size() > 0) {
                map = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
            } else {
                map.put("info", "empty");
            }
        }
        modelMsg(map);
        return SUCCESS;
    }

    /**
     * 根据传入的模板参数返回读取到的所有文件信息
     *
     * @param inputStream 读取的IO流
     * @param fileparam   xml文件名称 不同模板文件根据不同的正则表达式了获取订单信息
     * @return
     * @throws Exception
     */
    public List<String> readInfoByXml(InputStream inputStream, String fileparam) throws Exception {
        List<String> paramList = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\r\n");
        }
        if ("山东统一单店".equals(fileparam)) {
            Pattern pattern = Pattern.compile("<ss:Data .*?/ss:Data>");//取出每个单元格里的数据
            Matcher matcher = pattern.matcher(sb.toString());
            while (matcher.find()) {
                String param = matcher.group(0).replaceAll("<ss:Data ss:Type=\"String\">|<ss:Data ss:Type=\"Number\">|<ss:Data ss:Type=\"String\" x:Ticked=\"1\">|</ss:Data>", "");
                if (param.startsWith("合        计")) {
                    break;
                }
                paramList.add(param);
            }
        }
        return paramList;
    }

    /**
     * 婴知岛销售模板导入
     *
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2015年10月20日
     */
    public String importExcelByYZD() throws Exception {
        String busid = request.getParameter("busid");
        String ctype = request.getParameter("ctype");
        String gtype = request.getParameter("gtype");
        String pricetype = request.getParameter("pricetype");

        List<String> customerMap = ExcelUtils.importMoreRowInfoByIndex(importFile, 3, ctype);//从第4行读取客户信息
        List<String> paramList = ExcelUtils.importFirstRowByIndex(importFile, 6);//第六行为信息开始行
        String param = paramList.get(0);
        if (param.equals("")) {
            paramList.remove(0);
        }

        InputStream is = new FileInputStream(importFile);
        Workbook hssfWorkbook = null;
        String type = ExcelUtils.getFileByFile(importFile);

        if ("xls".equals(type)) {
            hssfWorkbook = new HSSFWorkbook(is);
        } else if ("xlsx".equals(type)) {
            hssfWorkbook = new XSSFWorkbook(importFile.getPath());
        }

        Map map = new HashMap();
        map.put("gtype", gtype);
        Map barcodeMap = new HashMap();
        String emptySheet = "";
        String errorSheet = "";
        int count = 0;
        if (paramList.size() > 0) {
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                //从第七行开始读取数据
                List<Map<String, Object>> list = ExcelUtils.importExcelBySheet(importFile, paramList, 7, numSheet);
                List<ModelOrder> wareList = new ArrayList<ModelOrder>();
                for (int i = 0; i < list.size(); i++) {
                    ModelOrder modelOrder = new ModelOrder();
                    modelOrder.setBarcode((String) list.get(i).get("商品条码"));
                    modelOrder.setShopid((String) list.get(i).get("商品代码"));
                    modelOrder.setUnitnum((String) list.get(i).get("数量"));
                    modelOrder.setBoxnum((String) list.get(i).get("箱数"));
                    if ("1".equals(pricetype)) {
                        modelOrder.setTaxprice((String) list.get(i).get("合同进价"));
                    }
                    //根据客户类型选择客户
                    if ("1".equals(ctype)) {
                        modelOrder.setBusid(busid);
                    } else if ("2".equals(ctype)) {
                        if (customerMap.size() <= numSheet) {
                            break;
                        } else {
                            String shopno = customerMap.get(numSheet);
                            Map map1 = new HashMap();
                            map1.put("shopno", shopno);
                            map1.put("pid", busid);
                            modelOrder.setBusid(returnCustomerID(map1));//根据条件返回对应客户
                            modelOrder.setMainbusid(busid);
                        }
                    } else if ("4".equals(ctype)) {
                        if (customerMap.size() <= numSheet) {
                            break;
                        } else {
                            String name = customerMap.get(numSheet);
                            Map map1 = new HashMap();
                            map1.put("name", name);
                            modelOrder.setBusid(returnCustomerID(map1));//根据条件返回对应客户
                        }
                    }

                    wareList.add(modelOrder);
                }
                Map insertMap = new HashMap();
                if (wareList.size() > 0 && !"".equals(wareList.get(0).getBusid())) {
                    insertMap = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
                } else if (wareList.size() > 0 && "".equals(wareList.get(0).getBusid())) {
                    insertMap.put("info", "busidEmpty");
                } else {
                    insertMap.put("flag", "false");
                }

                String flag = (String) insertMap.get("flag");
                if (insertMap.containsKey("busidEmpty")) {
                    errorSheet += (numSheet + 1) + ",";
                } else if ("true".equals(flag)) {
                    String barcode = "";
                    if (insertMap.containsKey("barcode")) {
                        barcode = insertMap.get("barcode").toString();
                        barcode = barcode.replace("[", "");
                        barcode = barcode.replace("]", "");
                        barcodeMap.put(Integer.valueOf(numSheet), barcode);
                    } else if (insertMap.containsKey("shopid")) {
                        barcode = insertMap.get("shopid").toString();
                        barcode = barcode.replace("[", "");
                        barcode = barcode.replace("]", "");
                        barcodeMap.put(Integer.valueOf(numSheet), barcode);
                    }
                } else if ("false".equals(flag)) {
                    if (emptySheet == "") {
                        emptySheet = (numSheet + 1) + ",";
                        ++count;
                    }
                } else {
                    if (emptySheet == "") {
                        emptySheet = (numSheet + 1) + ",";
                        ++count;
                    }
                }
            }
        } else {
            map.put("info", "empty");
        }
        if (errorSheet != "") {
            map.put("errorsheet", errorSheet);
        }
        if (emptySheet != "") {
            map.put("emptysheet", emptySheet);
        } else if (count == hssfWorkbook.getNumberOfSheets() || count != 0) {
            map.put("info", "empty");
        }
        sheetModelMsg(barcodeMap, map);

        return SUCCESS;
    }

    /**
     * 孩子王销售模板导入
     *
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2015年10月10日
     */
    public String importExcelByHZW() throws Exception {
        String busid = request.getParameter("busid");
        String ctype = request.getParameter("ctype");
        String gtype = request.getParameter("gtype");
        String pricetype = request.getParameter("pricetype");
        //第10行为信息开始行
        List<String> paramList = ExcelUtils.importFirstRowByIndex(importFile, 9);
        //从第11行开始获取导入数据
        List<Map<String, Object>> list = ExcelUtils.importExcelBySheet(importFile, paramList, 10, 0);
        List<ModelOrder> wareList = new ArrayList<ModelOrder>();
        for (int i = 0; i < list.size(); i++) {
            ModelOrder modelOrder = new ModelOrder();
            modelOrder.setBarcode((String) list.get(i).get("商品条码"));
            modelOrder.setShopid((String) list.get(i).get("商品编码"));
            modelOrder.setUnitnum((String) list.get(i).get("单品合计"));
            if ("1".equals(pricetype)) {
                modelOrder.setTaxprice((String) list.get(i).get("订货单价"));
            }
            if ("1".equals(ctype)) {
                modelOrder.setBusid(busid);
            } else if ("2".equals(ctype)) {
                //孩子王订单 从第8行读取客户信息
                List<String> rowInfo1 = ExcelUtils.importRowInfoBySheet(importFile, 7, 0);
                String shopno = "";
                for (String info : rowInfo1) {
                    if (info.contains("入货部门")) {//客户店号
                        int cindex = rowInfo1.indexOf(info) + 4;
                        shopno = rowInfo1.get(cindex);
                    }
                }
                Map map1 = new HashMap();
                map1.put("shopno", shopno);
                map1.put("pid", busid);
                modelOrder.setMainbusid(busid);
                modelOrder.setBusid(returnCustomerID(map1));
            } else if ("6".equals(ctype)) {
                //孩子王订单 从第23行读取客户地址
                List<String> infoList = ExcelUtils.importRowInfoBySheet(importFile, 22, 0);
                String[] addressInfo = infoList.get(0).split("s");
                int begindex = addressInfo[0].indexOf("：") + 1;
                int endindex = addressInfo[0].indexOf(" ");
                String address = addressInfo[0].substring(begindex, endindex);
                if (address != "") {
                    Map map1 = new HashMap();
                    map1.put("address", address);
                    modelOrder.setBusid(returnCustomerID(map1));
                }
            }
            wareList.add(modelOrder);
        }
        Map map = new HashMap();
        if (StringUtils.isEmpty(wareList.get(0).getBusid())) {
            map.put("info", "busidEmpty");
        } else {
            if (wareList.size() > 0) {
                map = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
            } else {
                map.put("info", "empty");
            }
        }
        modelMsg(map);

        return SUCCESS;
    }

    /**
     * 新百信销售模板导入
     *
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2015年10月16日
     */
    public String importExcelByXBX() throws Exception {
        String busid = request.getParameter("busid");
        String gtype = request.getParameter("gtype");
        String ctype = request.getParameter("ctype");
        String pricetype = request.getParameter("pricetype");
        List<String> paramList = ExcelUtils.importFirstRowByIndex(importFile, 8);//第9行为信息开始行
        paramList.remove(0);
        //从第10行开始获取导入数据
        List<Map<String, Object>> list = ExcelUtils.importExcelBySheet(importFile, paramList, 9, 0);
        List<ModelOrder> wareList = new ArrayList<ModelOrder>();
        for (int i = 0; i < list.size(); i++) {
            ModelOrder modelOrder = new ModelOrder();
            modelOrder.setBusid(busid);
            modelOrder.setBarcode((String) list.get(i).get("商品条码"));
            modelOrder.setShopid((String) list.get(i).get("商品编码"));
            modelOrder.setUnitnum((String) list.get(i).get("订货数量"));
            modelOrder.setBoxnum((String) list.get(i).get("箱数"));
            if ("1".equals(pricetype)) {
                modelOrder.setTaxprice((String) list.get(i).get("订货单价"));
            }
            wareList.add(modelOrder);
        }
        Map map = new HashMap();
        if (StringUtils.isEmpty(wareList.get(0).getBusid())) {
            map.put("info", "busidEmpty");
        } else {
            if (wareList.size() > 0) {
                map = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
            } else {
                map.put("info", "empty");
            }
        }
        modelMsg(map);

        return SUCCESS;
    }

    /**
     * 大宁销售模板导入
     *
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2015年10月25日
     */
    public String importExcelByDN() throws Exception {
        String busid = request.getParameter("busid");
        String ctype = request.getParameter("ctype");
        String gtype = request.getParameter("gtype");
        String pricetype = request.getParameter("pricetype");
        List<String> paramList = ExcelUtils.importFirstRowByIndex(importFile, 5);//第6行为信息开始行
        String param = paramList.get(0);
        if (param.equals("null") || param.equals("")) {
            paramList.remove(0);
        }
        //读取导入文件
        InputStream is = new FileInputStream(importFile);
        Workbook hssfWorkbook = null;
        String type = ExcelUtils.getFileByFile(importFile);
        if ("xls".equals(type)) {
            hssfWorkbook = new HSSFWorkbook(is);
        } else if ("xlsx".equals(type)) {
            hssfWorkbook = new XSSFWorkbook(importFile.getPath());
        } else {
            hssfWorkbook = new XSSFWorkbook(importFile.getPath());
        }
        //从第3行读取客户信息
        List<String> rowInfo = ExcelUtils.importRowInfoBySheet(importFile, 2, 0);
        String customerInfo = "";
        for (String info : rowInfo) {
            if (info.contains("订货店")) {//店铺中包含 店号 + 店名
                int cindex = rowInfo.indexOf(info) + 2;
                customerInfo = rowInfo.get(cindex);
                break;
            }
        }

        Map map = new HashMap();
        map.put("gtype", gtype);
        Map barcodeMap = new HashMap();

        if (StringUtils.isEmpty(busid)) {
            map.put("info", "busidEmpty");
        } else {
            String emptySheet = "";
            int count = 0;
            if (paramList.size() > 0) {
                for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                    //从第七行开始读取数据
                    List<Map<String, Object>> list = ExcelUtils.importExcelBySheet(importFile, paramList, 6, numSheet);
                    if (list.size() == 0) {
                        break;
                    }
                    String oldOrderID = "";
                    //从第1行读取订单号码
                    List<String> idInfo = ExcelUtils.importRowInfoBySheet(importFile, 0, numSheet);
                    for (String info : idInfo) {
                        if (info.contains("订单号")) {
                            int cindex = idInfo.indexOf(info) + 1;
                            oldOrderID = idInfo.get(cindex);
                        }
                    }
                    //数据组装
                    List<ModelOrder> wareList = new ArrayList<ModelOrder>();
                    for (int i = 0; i < list.size(); i++) {
                        ModelOrder modelOrder = new ModelOrder();
                        modelOrder.setOrderId(oldOrderID);
                        modelOrder.setBarcode((String) list.get(i).get("商品条码"));
                        modelOrder.setShopid((String) list.get(i).get("商品编码"));
                        modelOrder.setUnitnum((String) list.get(i).get("最小单位订货数量"));
                        if ("1".equals(pricetype)) {
                            modelOrder.setTaxprice((String) list.get(i).get("最小单位进价"));
                        }
                        if ("1".equals(ctype)) {
                            modelOrder.setBusid(busid);
                        } else if ("2".equals(ctype)) {
                            int splitIndex = customerInfo.indexOf(" ") + 1;
                            String shopno = customerInfo.substring(0, splitIndex - 1);
                            Map map1 = new HashMap();
                            map1.put("shopno", shopno);
                            map1.put("pid", busid);
                            modelOrder.setMainbusid(busid);
                            modelOrder.setBusid(returnCustomerID(map1));
                        } else {
                            map.put("info", "busidEmpty");
                        }
                        wareList.add(modelOrder);
                    }
                    //插入单据
                    Map insertMap = new HashMap();
                    if (wareList.size() > 0 && !"".equals(wareList.get(0).getBusid()) && !map.containsKey("info")) {
                        insertMap = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
                    } else if ("".equals(wareList.get(0).getBusid())) {
                        map.put("info", "busidEmpty");
                    } else {
                        insertMap.put("flag", "false");
                    }

                    String flag = (String) insertMap.get("flag");
                    if ("true".equals(flag)) {
                        if (insertMap.containsKey("barcode")) {
                            String barcode = insertMap.get("barcode").toString();
                            barcode = barcode.replace("[", "");
                            barcode = barcode.replace("]", "");
                            barcodeMap.put(Integer.valueOf(numSheet), barcode);
                        }
                        if (insertMap.containsKey("shopid")) {
                            String barcode = insertMap.get("shopid").toString();
                            barcode = barcode.replace("[", "");
                            barcode = barcode.replace("]", "");
                            barcodeMap.put(Integer.valueOf(numSheet), barcode);
                        }
                    } else if ("false".equals(flag)) {
                        if (emptySheet == "") {
                            emptySheet = (numSheet + 1) + "";
                            ++count;
                        } else {
                            emptySheet += "," + (numSheet + 1);
                            ++count;
                        }
                    }
                }
            } else {
                map.put("info", "empty");
            }
            if (emptySheet != "" && count != hssfWorkbook.getNumberOfSheets()) {
                map.put("emptysheet", emptySheet);
            } else if (count == hssfWorkbook.getNumberOfSheets()) {
                map.put("info", "empty");
            }
        }
        sheetModelMsg(barcodeMap, map);
        return SUCCESS;
    }

    /**
     * 天虹销售模板导入
     *
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2015年10月25日
     */
    public String importExcelByTH() throws Exception {
        String busid = request.getParameter("busid");
        String gtype = request.getParameter("gtype");
        String ctype = request.getParameter("ctype");
        String pricetype = request.getParameter("pricetype");
        if ("1".equals(gtype)) {
            List<String> paramList = ExcelUtils.importFirstRowByIndex(importFile, 21);//第22行为信息首行
            //从第23行开始获取导入数据
            List<Map<String, Object>> list = ExcelUtils.importTHExcelBySheet(importFile, paramList, 22, 0);
            List<ModelOrder> wareList = new ArrayList<ModelOrder>();
            for (int i = 0; i < list.size(); i++) {
                ModelOrder modelOrder = new ModelOrder();
                modelOrder.setBusid(busid);
                modelOrder.setBarcode((String) list.get(i).get("商品条码"));
                modelOrder.setUnitnum((String) list.get(i).get("订货数量"));
                if ("1".equals(pricetype)) {
                    modelOrder.setTaxprice((String) list.get(i).get("不含税进价"));
                }
                String orderID = ExcelUtils.importFirstRowByIndex(importFile, 10).get(1);//获取第11行的订单号
                if (orderID.contains("：")) {
                    orderID = orderID.substring(orderID.indexOf("：") + 1, orderID.length());
                }
                modelOrder.setOrderId(orderID);
                wareList.add(modelOrder);
            }

            Map map = new HashMap();
            if (StringUtils.isEmpty(busid)) {
                map.put("info", "busidEmpty");
            } else {
                if (wareList.size() > 0) {
                    map = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
                } else {
                    map.put("info", "empty");
                }
            }
            modelMsg(map);
        } else {
            request.setAttribute("msg", "该模板只允许按商品条码导入，请到设置页面修改模板导入类型");
        }
        return SUCCESS;
    }

    /**
     * 三和txt模板导入
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015年12月31日
     */
    public String importTxt2() throws Exception {
        String busid = request.getParameter("busid");//获取指定总店编码
        Object object2 = SpringContextUtils.getBean("salesOrderService");
        Class entity = Class.forName("com.hd.agent.sales.model.ImportSalesOrder");
        Method[] methods = object2.getClass().getMethods();
        Method method = null;
        for (Method m : methods) {
            if (m.getName().equals("addDRSalesOrder")) {
                method = m;
            }
        }
        //判断文件格式filestype: 1.excel文件、2.txt文件
        String filestype = "2";
        List<String> paramList = null;
        List<Map<String, Object>> list = null;
        int successNum = 0, failureNum = 0, ordernum = 0;
        String msg = "", backorderids = "", goodsidmsg = "", customergoodsidmsg = "", customerids = "", spellmsg = "", barcodemsg = "", disablegoodsidsmsg = "";
        boolean flag = true;
        //.excel格式获取第一行字段
        if ("1".equals(filestype)) {

        } else if ("2".equals(filestype)) {
            Map resultMap3 = new HashMap();
            paramList = salesOrderExtService.importTxtFirstRow(importFile); //获取第一行数据为字段的描述列表
            //获取三和总店编码
            String pid = getSysParamValue("SHORDERIMPORT");
            Map<String, List<Map<String, Object>>> map2 = salesOrderExtService.importTXT(importFile, paramList, busid); //获取导入数据
            Iterator it = map2.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                List<Map<String, Object>> list2 = (List<Map<String, Object>>) entry.getValue();
                for (Map<String, Object> map : list2) {
                    //根据总店编码+店号获取客户信息
                    Customer customer = getBaseSalesService().getCustomerInfoByShopno(busid, (String) map.get("shopno"));
                    if (null != customer) {
                        map.put("customerid", customer.getId());
                        map.put("customername", customer.getName());
                    }
                }
                List result = new ArrayList();
                if (null != list2 && list2.size() != 0) {
                    for (Map<String, Object> map : list2) {
                        Object object = entity.newInstance();
                        Field[] fields = entity.getDeclaredFields();
                        //获取的导入数据格式转换
                        DRCastTo(map, fields);
                        //BeanUtils.populate(object, map);
                        PropertyUtils.copyProperties(object, map);
                        result.add(object);
                    }
                    if (result.size() != 0) {
                        Map resultMap2 = excelService.insertSalesOrder(object2, result, method);
                        if (resultMap2.containsKey("success") && null != resultMap2.get("success")) {
                            successNum += (Integer) resultMap2.get("success");
                        }
                        if (resultMap2.containsKey("failure") && null != resultMap2.get("failure")) {
                            failureNum += (Integer) resultMap2.get("failure");
                        }
                        if (resultMap2.containsKey("customerid") && null != resultMap2.get("customerid")) {
                            if (StringUtils.isEmpty(customerids)) {
                                customerids = (String) resultMap2.get("customerid");
                            } else {
                                customerids += "," + (String) resultMap2.get("customerid");
                            }
                            msg = "客户编号：" + customerids + "不存在,导入失败";
                        }
                        if (resultMap2.containsKey("flag") && null != resultMap2.get("flag")) {
                            flag = resultMap2.get("flag").equals(true) && flag;
                        }
                        if (resultMap2.containsKey("goodsidmsg") && null != resultMap2.get("goodsidmsg")) {
                            goodsidmsg += (String) resultMap2.get("goodsidmsg");
                        }
                        if (resultMap2.containsKey("customergoodsidmsg") && null != resultMap2.get("customergoodsidmsg")) {
                            customergoodsidmsg += (String) resultMap2.get("customergoodsidmsg");
                        }
                        if (resultMap2.containsKey("spellmsg") && null != resultMap2.get("spellmsg")) {
                            spellmsg += (String) resultMap2.get("spellmsg");
                        }
                        if (resultMap2.containsKey("backorderids") && null != resultMap2.get("backorderids")) {
                            if (StringUtils.isEmpty(backorderids)) {
                                backorderids = (String) resultMap2.get("backorderids");
                            } else {
                                backorderids += "," + (String) resultMap2.get("backorderids");
                            }
                        }
                    }
                }
            }
            if (successNum == 0) {
                resultMap3.put("info", "empty");
            } else {
                resultMap3.put("success", successNum);
                resultMap3.put("failure", failureNum);
                resultMap3.put("repeatNum", 0);
                resultMap3.put("closeNum", 0);
                if (StringUtils.isEmpty(msg)) {
                    if (StringUtils.isNotEmpty(customergoodsidmsg)) {
                        msg = "店内码:" + customergoodsidmsg + "不存在,导入失败";
                    } else if (StringUtils.isNotEmpty(goodsidmsg)) {
                        msg = "商品编码:" + goodsidmsg + "不存在,导入失败";
                    } else if (StringUtils.isNotEmpty(spellmsg)) {
                        msg = "商品助记符:" + spellmsg + "不存在,导入失败";
                    }
                } else {
                    msg = "客户编号不存在,导入失败";
                }
                if (msg != "") {
                    resultMap3.put("msg", msg);
                }

                if (StringUtils.isNotEmpty("backorderids")) {
                    addLog("销售订单导入 编号:" + backorderids, flag);
                }
                if (StringUtils.isNotEmpty(backorderids)) {
                    if (backorderids.indexOf(",") != -1) {
                        ordernum = backorderids.split(",").length;
                    } else {
                        ordernum = 1;
                    }
                }
                resultMap3.put("ordernum", ordernum);
            }

            modelMsg(resultMap3);
        }
        return SUCCESS;
    }

    /**
     * 一号店模板导入
     *
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016年2月25日
     */
    public String importExcelByYHD() throws Exception {
        String busid = request.getParameter("busid");
        String ctype = request.getParameter("ctype");
        String gtype = request.getParameter("gtype");
        String pricetype = request.getParameter("pricetype");

        //第22行为信息开始行
        List<String> paramList = ExcelUtils.importFirstRowByIndex(importFile, 21);
        String param = paramList.get(0);
        if (param.equals("")) {
            paramList.remove(0);
        }
        //从第23行开始获取导入数据
        List<Map<String, Object>> list = ExcelUtils.importExcelBySheet(importFile, paramList, 22, 0);
        List<String> rowInfo = ExcelUtils.importRowInfoBySheet(importFile, 6, 0);
        String oldOrderID = rowInfo.get(0);
        oldOrderID = oldOrderID.substring(oldOrderID.indexOf("：") + 1, oldOrderID.length());

        List<ModelOrder> wareList = new ArrayList<ModelOrder>();
        for (int i = 0; i < list.size(); i++) {
            ModelOrder modelOrder = new ModelOrder();
            modelOrder.setOrderId(oldOrderID);
            modelOrder.setBarcode((String) list.get(i).get("条形码"));
            modelOrder.setBoxnum((String) list.get(i).get("箱规"));
            modelOrder.setShopid((String) list.get(i).get("商品\n编码"));
            String numInfo = (String) list.get(i).get("下单\n数量");
            modelOrder.setUnitnum(numInfo);
            if ("1".equals(pricetype)) {
                modelOrder.setTaxprice((String) list.get(i).get("采购\n进价"));
            }
            if ("1".equals(ctype)) {
                modelOrder.setBusid(busid);
            }
            wareList.add(modelOrder);
        }
        Map map = new HashMap();
        if (StringUtils.isEmpty(busid)) {
            map.put("info", "busidEmpty");
        } else {
            if (list.size() > 0) {
                map = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
            } else {
                map.put("info", "empty");
            }
        }
        modelMsg(map);

        return SUCCESS;
    }

    /**
     * 苏杭时代（合兴店）销售模板导入
     *
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016年4月2日
     */
    public String importExcelByTable() throws Exception {

        String busid = request.getParameter("busid");
        String ctype = request.getParameter("ctype");
        String gtype = request.getParameter("gtype");
        String pricetype = request.getParameter("pricetype");

        List<String> paramList = ExcelUtils.importFirstRowByIndex(importFile, 8);//第9行为信息开始行
        String param = paramList.get(0);
        if (param.equals("null") || param.equals("")) {
            paramList.remove(0);
        }
        //读取导入文件
        InputStream is = new FileInputStream(importFile);
        Workbook hssfWorkbook = null;
        String type = ExcelUtils.getFileByFile(importFile);
        if ("xls".equals(type)) {
            hssfWorkbook = new HSSFWorkbook(is);
        } else if ("xlsx".equals(type)) {
            hssfWorkbook = new XSSFWorkbook(importFile.getPath());
        } else {
            hssfWorkbook = new XSSFWorkbook(importFile.getPath());
        }

        Map map = new HashMap();
        map.put("gtype", gtype);
        if (StringUtils.isEmpty(busid)) {
            map.put("info", "busidEmpty");
        } else {
            if (paramList.size() > 0) {
                for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                    List<Map<String, Object>> list = ExcelUtils.importExcelBySheet(importFile, paramList, 10, numSheet);
                    if (list.size() == 0) {
                        continue;
                    } else {
                        String oldOrderID = "";
                        //从第1行读取订单号码
                        List<String> idInfo = ExcelUtils.importRowInfoBySheet(importFile, 0, numSheet);
                        for (String info : idInfo) {
                            if (info.contains("订单流水号")) {
                                int cindex = idInfo.indexOf(info) + 2;
                                oldOrderID = idInfo.get(cindex);
                            }
                        }
                        //数据组装
                        List<ModelOrder> wareList = new ArrayList<ModelOrder>();
                        for (int i = 0; i < list.size(); i++) {
                            ModelOrder modelOrder = new ModelOrder();
                            modelOrder.setOrderId(oldOrderID);
                            modelOrder.setBarcode((String) list.get(i).get("商品条码"));
                            modelOrder.setShopid((String) list.get(i).get("商品编码"));
                            modelOrder.setUnitnum((String) list.get(i).get("订货"));
                            if ("1".equals(pricetype)) {
                                modelOrder.setTaxprice((String) list.get(i).get("订货价"));
                            }
                            if ("1".equals(ctype)) {
                                modelOrder.setBusid(busid);
                            } else if ("2".equals(ctype)) {
                                //从第3行读取客户信息
                                List<String> rowInfo = ExcelUtils.importRowInfoBySheet(importFile, 4, numSheet);
                                for (String info : rowInfo) {
                                    if (info.contains("厂商编码")) {
                                        Map map1 = new HashMap();
                                        int cindex = rowInfo.indexOf(info) + 2;
                                        String shopno = rowInfo.get(cindex);
                                        map1.put("shopno", shopno);
                                        map1.put("pid", busid);
                                        modelOrder.setMainbusid(busid);
                                        modelOrder.setBusid(returnCustomerID(map1));

                                        break;
                                    }
                                }

                            } else {
                                map.put("info", "busidEmpty");
                            }
                            wareList.add(modelOrder);
                        }
                        //插入单据
                        Map insertMap = new HashMap();
                        if (wareList.size() > 0) {
                            insertMap = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
                        } else {
                            insertMap.put("flag", "false");
                        }
                        map.putAll(insertMap);
                        modelMsg(map);
                        break;

                    }
                }
            }
        }
        return SUCCESS;
    }
    /**
     * 判断文件的编码格式
     * @param file :file
     * @return 文件编码格式
     * @throws Exception
     */
    public static String getFileCodeString(File file) throws Exception{
        BufferedInputStream bin = new BufferedInputStream( new FileInputStream(file));
        int p = (bin.read() << 8) + bin.read();
        String code = null;
        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }

        return code;
    }
    /**
     * 根据参数读取excel对应列的自适应导入
     * @return
     * @throws Exception
     */
    public String importByDigitalParam() throws Exception {
        request.setCharacterEncoding("utf-8");
        String busid = request.getParameter("busid");//客户编码
        String ctype = request.getParameter("ctype");
        String gtype = request.getParameter("gtype");
        String pricetype = request.getParameter("pricetype");
        String fileparam = request.getParameter("fileparam");
        if(StringUtils.isEmpty(fileparam)){
            String id = request.getParameter("id");
            ImportSet importSet = getImportService().showImportModelById(id);
            fileparam = importSet.getFileparam();
        }
        String[] info = fileparam.split(";");
        List<String> readerInformation = new ArrayList<String>();
        //读取导入文件
        InputStream is = new FileInputStream(importFile);
        Workbook hssfWorkbook = null;
        String type = ExcelUtils.getFileByFile(importFile);
        if ("xls".equals(type)) {
            hssfWorkbook = new HSSFWorkbook(is);
        } else if ("xlsx".equals(type)) {
            hssfWorkbook = new XSSFWorkbook(importFile.getPath());
        } else if ("csv".equals(type)) {
            InputStream raf = new FileInputStream(importFile);
            //设置输出内容格式，防止乱码
            String charsetName = getFileCodeString(importFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(raf,charsetName));
            String line = reader.readLine();
            while (line != null) {
                readerInformation.add(line);
                line = reader.readLine();
            }
            reader.close();
        } else {
            //type=null 的情况下 先按Excel 2007版本以上的文件读取方式读取，读取不到则按txt格式读取
            try {
                hssfWorkbook = new XSSFWorkbook(importFile.getPath());
            } catch (InvalidOperationException e) {
                type = "txt";
                InputStream raf = new FileInputStream(importFile);
                BufferedReader reader = new BufferedReader(new InputStreamReader(raf));
                String line = reader.readLine();
                while (line != null) {
                    readerInformation.add(line);
                    line = reader.readLine();
                }
            }
        }
        Map map = new HashMap();
        Map barcodeMap = new HashMap();
        map.put("gtype", gtype);
        int beginRow = 0; //开始行
        int goodsCol = -1;
        int divideCol = -1;
        int customerTypeCol = -1;//客户类型读取位置
        int customerTypeRow = -1;
        int numCol = -1;
        int priceCol = -1;
        int boxnumCol = -1;
        int customerCol = -1;
        int customerRow = 0;
        int remarkCol = -1;
        int remarkRow = -1;
        int otherCol = -1 ;//和拆分所在列一起用
        int detailcount = 0 ;//拆分时导入多少条商品明细统计
        String custRegular = null;
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
            } else if (info[i].contains("客户单元格")) {
                String Column = value.replaceAll("\\d+", "");
                customerTypeCol = ExcelUtils.chagneCellColtoNumber(Column);
                String customerRowStr = value.replaceAll("[a-zA-Z]+", "");
                customerTypeRow = Integer.parseInt(customerRowStr);
            } else if (info[i].contains("备注")) {
                String Column = value.replaceAll("\\d+", "");
                remarkCol = ExcelUtils.chagneCellColtoNumber(Column);
                String customerRowStr = value.replaceAll("[a-zA-Z]+", "");
                remarkRow = Integer.parseInt(customerRowStr);
            } else if (info[i].contains("日期/其它列")) {
                otherCol = ExcelUtils.chagneCellColtoNumber(value);
            }else if (info[i].contains("客户正则") ) {
                String[] str = info[i].split("=");
                if(str.length >1){
                    custRegular = info[i].split("=")[1];
                }
            }
        }
        //获取客户单号
        String orderid = getCellValueByParam(importFile, customerCol, customerRow, readerInformation);
        //获取模板备注
        String remark = getCellValueByParam(importFile, remarkCol, remarkRow, readerInformation);

        Map map1 = new HashMap();
        //根据客户类型 获取导入的客户编码
        if ("1".equals(ctype)) {
            busid = request.getParameter("busid");
        } else {
            if (customerTypeRow != -1) {
                String custParam = getCellValueByParam(importFile, customerTypeCol, customerTypeRow, readerInformation);
                //根据正则解析客户编号
                if(StringUtils.isNotEmpty(custRegular)){
                    custParam = getCusidByCustRegular(custRegular,custParam);
                }
                if (StringUtils.isNotEmpty(custParam)) {
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
        //系统参数（是否允许重复商品）
        String isreapt = getSysParamValue("IsSalesGoodsRepeat");
        if ("csv".equals(type) || "txt".equals(type)) {
            List<ModelOrder> wareList = readTextModelOrder(readerInformation, isreapt, beginRow, goodsCol, numCol, priceCol, boxnumCol, pricetype, gtype, busid, orderid);
            if (wareList.size() > 0) {
                wareList.get(0).setRemark(remark);
                if (StringUtils.isEmpty(wareList.get(0).getBusid())) {
                    map.put("info", "busidEmpty");
                } else {
                    map = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
                }
            } else {
                map.put("info", "empty");
            }
            modelMsg(map);
        } else {
            String emptySheet = "";
            int count = -1;
            //根据分隔列导入多张订单,不合并重复的商品
            if (divideCol > -1) {
                Map modelResultMap = getModelOrderMapByPram(beginRow, divideCol, goodsCol, numCol, priceCol, boxnumCol, pricetype, gtype, ctype,otherCol);
                Iterator<Map.Entry<String, List>> it = modelResultMap.entrySet().iterator();
                while (it.hasNext()) {
                    ++ count;
                    Map.Entry<String, List> entry = it.next();
                    List wareList = entry.getValue();
                    if (StringUtils.isEmpty(busid)) {
                        map.put("info", "busidEmpty");
                    } else if (wareList.size() > 0) {
                        //组装导入结果
                        ModelOrder modelOrder = (ModelOrder)wareList.get(0);
                        modelOrder.setRemark(remark);
                        Map insertMap = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
                        String ordrid = "0";
                        if(null != modelOrder && StringUtils.isNotEmpty(modelOrder.getOrderId())){
                            ordrid = getNumbers(modelOrder.getOrderId());
                        }
                        int oid = Integer.valueOf(ordrid).intValue();
                        List list = insertMapReturn(insertMap,oid - 1, emptySheet, count, map, barcodeMap );
                        map = (Map) list.get(0);
                        barcodeMap = (Map) list.get(1);
                        String flag = (String) insertMap.get("flag");
                        if("true".equals(flag)){
                            if(insertMap.containsKey("barcode")){
                                List<String> errorList = (List<String>) insertMap.get("barcode");
                                detailcount += wareList.size() - errorList.size();
                            }else if(insertMap.containsKey("shopid")){
                                List<String> errorList = (List<String>) insertMap.get("shopid");
                                detailcount += wareList.size() - errorList.size();
                            }if(insertMap.containsKey("spell")){
                                List<String> errorList = (List<String>) insertMap.get("spell");
                                detailcount += wareList.size() - errorList.size();
                            }if(insertMap.containsKey("goodsid")){
                                List<String> errorList = (List<String>) insertMap.get("goodsid");
                                detailcount += wareList.size() - errorList.size();
                            }else{
                                detailcount += wareList.size();
                            }
                        }

                    }
                }
                barcodeMap.put("count",detailcount);
            } else {
                //分别读取对应列值
                for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                    //Excel按工作表读，分别读取每个工作表指定位置的客户信息
                    if(numSheet > 0 && !"1".equals(ctype)){
                        String custParam = ExcelUtils.getFileValueByParam(importFile,numSheet,customerTypeRow,customerTypeCol);
                        if(StringUtils.isNotEmpty(custRegular)){
                            custParam = getCusidByCustRegular(custRegular,custParam);
                        }
                        if (StringUtils.isNotEmpty(custParam)) {
                            if ("2".equals(ctype)) {
                                Customer c = getCustomerById(busid);
                                map1.put("pid", c.getPid());//总店
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
                    Customer customer =getCustomerById(busid);
                    if(null != customer){
//                        if(customerCol>=0){
//                            //获取客户单号
//                            orderid = ExcelUtils.getSheetRowCellValue(importFile,numSheet, customerRow, customerCol);
//                        }
                        //根据是否合并相同商品组装商品明细
                        List<ModelOrder> wareList = getModelOrderListByPram(isreapt, beginRow, numSheet, goodsCol, numCol, priceCol, boxnumCol, pricetype, gtype, busid, orderid);
                        if (StringUtils.isEmpty(busid)) {
                            map.put("info", "busidEmpty");
                        } else if (wareList.size() > 0) {
                            if(remarkCol > -1 && remarkRow >-1){
                                String sheetRemark = ExcelUtils.getSheetRowCellValue(importFile,numSheet, remarkRow, remarkCol);
                                wareList.get(0).setRemark(sheetRemark);
                            }
                            Map insertMap = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
                            //组装导入结果
                            List list = insertMapReturn(insertMap,numSheet, emptySheet, count, map, barcodeMap );
                            map = (Map) list.get(0);
                            barcodeMap = (Map) list.get(1);
                        }
                    }else{
                        if(map.containsKey("errorsheet")){
                            String errorsheet = map.get("errorsheet").toString();
                            map.put("errorsheet",errorsheet+","+(numSheet+1));
                        }else{
                            map.put("errorsheet",numSheet+1);
                        }
                    }
                }
                if (emptySheet != "" && count != hssfWorkbook.getNumberOfSheets()) {
                    map.put("emptysheet", emptySheet);
                } else if (count == hssfWorkbook.getNumberOfSheets()) {
                    map.put("info", "empty");
                }
            }
            sheetModelMsg(barcodeMap, map);
        }
        return SUCCESS;
    }

    public String getNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        if(StringUtils.isNotEmpty(content)){
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                return matcher.group(0);
            }
        }
        return "";
    }

    /**
     * 根据 customerCol，customerRow 两个参数 获取模板中制定单元格的值
     * @author lin_xx
     * @date 2017/5/15
     */
    public String getCellValueByParam(File importFile,int customerCol,int customerRow,List<String> readerInformation) throws Exception {
        String cellValue = "";
        String customerParam = "";
        //txt格式 获取单元格值
        if(customerCol != -1 && customerRow >= 1 && readerInformation.size() >= customerRow){
            String[] lineValue = readerInformation.get(customerRow-1).split("\\t");
            customerParam = lineValue[customerCol];
            if(StringUtils.isEmpty(customerParam)){
                lineValue = readerInformation.get(customerRow-1).split("\\t");
                cellValue = lineValue[customerCol];
            }else{
                cellValue = customerParam;
            }
        }
        //excel格式 获取单元格值
        if(StringUtils.isEmpty(customerParam) && customerRow > 0){
            List<String> customerRowInfo = ExcelUtils.importFirstRowByIndex(importFile,customerRow-1, 0);
            if(customerRowInfo.size() > 0){
                cellValue = (String) customerRowInfo.get(customerCol);
            }
        }
        return cellValue;

    }

    /**
     * 对txt格式的模板导入的数据进行是否合并相同商品的数据处理
     * @author lin_xx
     * @date 2017/5/15
     */
    public List<ModelOrder> readTextModelOrder(List<String> readerInformation,String isreapt ,int beginRow,int goodsCol,int numCol,int priceCol,int boxnumCol,
                                               String pricetype,String gtype,String busid,String orderid) throws Exception {
        List<ModelOrder> wareList = new ArrayList<ModelOrder>();
        if("1".equals(isreapt)){
            Map goodsMap = new LinkedHashMap();
            Map numMap= new LinkedHashMap();
            Map boxnumMap = new LinkedHashMap();
            Map priceMap = new LinkedHashMap();
            for(int i = beginRow ; i< readerInformation.size() ; ++ i) {
                String Line = readerInformation.get(i);
                if(StringUtils.isNotEmpty(Line)){
                    String[] lineDate = Line.split("\\t");
                    if(goodsCol > -1 ){
                        goodsMap.put(i,lineDate[goodsCol]);
                    }
                    if(numCol > -1){
                        numMap.put(i,lineDate[numCol]);
                    }
                    if(boxnumCol > -1){
                        boxnumMap.put(i,lineDate[boxnumCol]);
                    }
                    if(priceCol > -1){
                        priceMap.put(i,lineDate[priceCol]);
                    }
                }
            }
            for(int i = beginRow; i< beginRow+goodsMap.size(); ++ i){
                ModelOrder modelOrder = new ModelOrder();
                if(numMap.size() > 0){
                    modelOrder.setUnitnum((String)numMap.get(i));
                }
                modelOrder.setBusid(busid);
                modelOrder.setOrderId(orderid);
                String goodsid = (String)goodsMap.get(i);
                if("1".equals(gtype)){
                    modelOrder.setBarcode(goodsid);
                }else if("2".equals(gtype)){
                    modelOrder.setShopid(goodsid);
                }else if("3".equals(gtype)){
                    modelOrder.setSpell(goodsid);
                }else{
                    modelOrder.setGoodsid((String)goodsMap.get(i));
                }
                if(priceMap.size() > 0 && "1".equals(pricetype)){
                    modelOrder.setTaxprice((String)priceMap.get(i));
                }
                if(boxnumMap.size() > 0){
                    modelOrder.setBoxnum((String) boxnumMap.get(i));
                }
                wareList.add(modelOrder);
            }

        }else{

            Map keyMap = new LinkedHashMap();
            Map priceKeyMap = new LinkedHashMap();
            Map boxnumKeyMap = new LinkedHashMap();
            for(int i = beginRow ; i< readerInformation.size() ; ++ i) {
                String Line = readerInformation.get(i);
                if(StringUtils.isNotEmpty(Line)){
                    String[] lineDate = Line.split("\\t");
                    String goods = "";
                    BigDecimal num = new BigDecimal(0);
                    BigDecimal boxnum = new BigDecimal(0);
                    BigDecimal price = new BigDecimal(0);
                    if(goodsCol > -1 ){
                        goods = lineDate[goodsCol];
                    }
                    if(numCol > -1){
                        num = new BigDecimal(lineDate[numCol]);
                    }
                    if(boxnumCol > -1){
                        boxnum = new BigDecimal(lineDate[boxnumCol]);
                    }
                    if(priceCol > -1){
                        price = new BigDecimal(lineDate[priceCol]);
                    }
                    if(StringUtils.isNotEmpty(goods)){
                        if(keyMap.containsKey(goods)){
                            BigDecimal keynum = (BigDecimal) keyMap.get(goods);
                            keynum = keynum.add(num);
                            keyMap.put(goods,keynum);
                            BigDecimal keyboxnum = (BigDecimal) boxnumKeyMap.get(goods);
                            keyboxnum = keyboxnum.add(boxnum);
                            boxnumKeyMap.put(goods,keyboxnum);
                            BigDecimal keyprice = (BigDecimal) priceKeyMap.get(goods);
                            keyprice = keyprice.add(price);
                            priceKeyMap.put(goods,keyprice);
                        }else{
                            keyMap.put(goods,num);
                            priceKeyMap.put(goods,price);
                            boxnumKeyMap.put(goods,price);
                        }
                    }
                }
            }
            for (Object goodsMark : keyMap.keySet()) {
                ModelOrder modelOrder = new ModelOrder();
                if("1".equals(gtype)){
                    modelOrder.setBarcode((String) goodsMark);
                }else if("2".equals(gtype)){
                    modelOrder.setShopid((String) goodsMark);
                }else if("3".equals(gtype)){
                    modelOrder.setSpell((String) goodsMark);
                }else if("4".equals(gtype)){
                    modelOrder.setGoodsid((String) goodsMark);
                }
                modelOrder.setUnitnum(keyMap.get(goodsMark).toString());
                modelOrder.setOrderId(orderid);
                modelOrder.setBusid(busid);
                if(priceKeyMap.size()>0 && "1".equals(pricetype)){
                    modelOrder.setTaxprice( priceKeyMap.get(goodsMark).toString());
                }
                if(boxnumKeyMap.size()>0){
                    modelOrder.setBoxnum( boxnumKeyMap.get(goodsMark).toString());
                }
                wareList.add(modelOrder);
            }

        }
        return wareList;
    }

    /**
     *
     * @param insertMap 数据导入返回的结果集
     * @param numSheet excel工作表
     * @param emptySheet 空工作表
     * @param count 工作表的技术
     * @param map 导入参数
     * @param barcodeMap 导入不成功的结果集
     */
    public List<Map> insertMapReturn(Map insertMap , int numSheet , String emptySheet ,int count , Map map , Map barcodeMap) {

        List<Map> list = new ArrayList<Map>();
        String flag = (String) insertMap.get("flag");
        if (insertMap.containsKey("disablemsg")) {
            map.put("disablemsg", insertMap.get("disablemsg"));
        }
        if ("true".equals(flag)) {
            if(numSheet == -99){
                numSheet = count;
            }
            if (insertMap.containsKey("barcode")) {
                String barcode = insertMap.get("barcode").toString();
                barcode = barcode.replace("[", "");
                barcode = barcode.replace("]", "");
                barcodeMap.put(Integer.valueOf(numSheet) + 1, barcode);
            } else if (insertMap.containsKey("shopid")) {
                String barcode = insertMap.get("shopid").toString();
                barcode = barcode.replace("[", "");
                barcode = barcode.replace("]", "");
                barcodeMap.put(Integer.valueOf(numSheet) + 1, barcode);
            } else if (insertMap.containsKey("spell")) {
                String barcode = insertMap.get("spell").toString();
                barcode = barcode.replace("[", "");
                barcode = barcode.replace("]", "");
                barcodeMap.put(Integer.valueOf(numSheet) + 1, barcode);
            } else if (insertMap.containsKey("goodsid")) {
                String barcode = insertMap.get("goodsid").toString();
                barcode = barcode.replace("[", "");
                barcode = barcode.replace("]", "");
                barcodeMap.put(Integer.valueOf(numSheet) + 1, barcode);
            } else {
                map.put("sheet", true);
            }
        }else if ("false".equals(flag)) {
            if(map.containsKey("emptysheet")){
                String value = (String) map.get("emptysheet");
                map.put("emptysheet", value+","+(numSheet + 1));
            }else{
                map.put("emptysheet",(numSheet + 1)+"");
            }
        }else{
            map.put("emptysheet",emptySheet+1);
        }
        if(insertMap.containsKey("errorList")){
            if(map.containsKey("errorList")){
                List errorList = (List) map.get("errorList");
                List failList = (List) insertMap.get("errorList");
                errorList.addAll(failList);
                map.put("errorList",errorList);
            }else{
                map.put("errorList",insertMap.get("errorList"));
            }
        }
        list.add(map);
        list.add(barcodeMap);
        return list;
    }


    /**
     * 使用jsoup技术 html自适应导入模板方法
     * @return
     * @throws Exception
     */
    public String importHtmlBydigitalParam() throws Exception {

        String busid = request.getParameter("busid");
        String ctype = request.getParameter("ctype");
        String gtype = request.getParameter("gtype");
        String fileparam = request.getParameter("fileparam");
        String[] info = fileparam.split(";");

        File file = importFile ;
        int validColumn = 0,goodsID = 0,boxnumID = 0,numID = 0,priceID =0,tableNum = -1,ignore = -1,listRemove = -1,beginRow = -1 ;

        for(int i = 0 ; i< info.length ; ++ i) {
            String value = info[i].split("=")[1];
            if(info[i].contains("商品条形码")){
                goodsID = Integer.parseInt(value);

            }else if(info[i].contains("商品数量")){
                numID = Integer.parseInt(value);

            }else if(info[i].contains("商品箱数")){
                boxnumID = Integer.parseInt(value);

            }else if(info[i].contains("商品单价")){
                priceID = Integer.parseInt(value);

            }else if(info[i].contains("商品助记符")){
                goodsID = Integer.parseInt(value);

            }else if(info[i].contains("商品店内码")){
                goodsID = Integer.parseInt(value);
            }else if(info[i].contains("有效列")){
                validColumn = Integer.parseInt(value);
            }else if(info[i].contains("数据位置")){
                tableNum = Integer.parseInt(value);
            }else if(info[i].contains("不相关")){
                ignore = Integer.parseInt(value);
            }else if(info[i].contains("客户单号位置")|| info[i].contains("数据去空")){
                listRemove = Integer.parseInt(value);
            }else if(info[i].contains("模板首行")){
                beginRow = Integer.parseInt(value);
            }
        }

        Document doc = Jsoup.parse(file, "UTF-8", "http://example.com/");
        Element body = doc.body();
        Elements table = body.getElementsByTag("table");
        int tableIndex = -1 ;
        if(null != table && tableNum == -1){
            for (int i = 0 ; i< table.size();++ i) {
                Elements tbody = table.get(i).getElementsByTag("tbody");
                if(tbody.size() > 0){
                    Elements tr = tbody.get(0).getElementsByTag("tr");
                    if(tr.size() > 0){
                        Elements td = tr.get(0).getElementsByTag("td");
                        Elements th = tr.get(0).getElementsByTag("th");
                        //根据订单有效列参数排除不是订单数据的table
                        if(td.size() == validColumn || th.size() == validColumn){
                            tableIndex = i;
                            break;
                        }
                    }
                }
            }
        }else{
            tableIndex = tableNum ;
        }
        List list = new ArrayList();
        if(tableIndex > -1){
            Element dataTable = table.get(tableIndex);
            Elements trs = dataTable.getElementsByTag("tr");
            //模板数据是否从第一行开始读
            if(beginRow == -1){
                beginRow = 1;
            }
            for(int i = beginRow ; i< trs.size();++ i){
                Elements tds = trs.get(i).getElementsByTag("td");
                for(Element td : tds){
                    String text = td.text();
                    list.add(text);
                }
            }
        }
        //去除
        if(listRemove > -1){
            list.remove(listRemove);
        }
        List<ModelOrder> wareList = new ArrayList<ModelOrder>();
        for(int i = 0 ;i<list.size() ; i = i+ validColumn){
            if(i+validColumn <= list.size()){
                ModelOrder modelOrder = new ModelOrder();
                modelOrder.setBarcode((String) list.get(i+ goodsID - 1));
                modelOrder.setUnitnum((String) list.get(i+ numID -1));
                if(boxnumID != 0){
                    modelOrder.setBoxnum((String) list.get(i+ boxnumID -1));
                }
                if(priceID != 0){
                    modelOrder.setTaxprice((String)list.get(i + priceID - 1));
                }
                modelOrder.setBusid(busid);
                //汉广订单 先取箱数再取数量
                if(StringUtils.isNotEmpty(modelOrder.getBoxnum()) && !"0.00".equals(modelOrder.getBoxnum())){
                    modelOrder.setUnitnum("0");
                }
                wareList.add(modelOrder);
            }else{
                break;
            }
        }
        if(ignore != -1){
            wareList.remove(ignore);
        }
        Map map = new HashMap();
        if(wareList.size()>0){
            if(StringUtils.isEmpty(wareList.get(0).getBusid())){
                map.put("info","busidEmpty");
            }else{
                map = getSalesOrderService().addMDSalesOrder(wareList,ctype,gtype);
            }
        }else{
            map.put("info","empty");
        }
        modelMsg(map);

        return SUCCESS;
    }

    /**
     * 列自适应模板导入
     * 该方法具有普遍适用性 适用于大多数常规格式的导入模板
     * 商品导入格式：助记符 条形码 客户导入格式：指定客户编号
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016年5月25日
     */
    public String importAdaptiveExcelByColumn() throws Exception{
        String busid = request.getParameter("busid");
        String ctype = request.getParameter("ctype");
        String gtype = request.getParameter("gtype");
        String pricetype = request.getParameter("pricetype");
        String fileparam = request.getParameter("fileparam");
        String[] info = fileparam.split(";");

        String beginOfGoods = "";//商品开始行
        String beginColumn = "";//商品开始列
        String beginOfCustomer = "";//客户单号所在行
        String goodsIdetify = "";//商品条形码
        String goodsNum = "";
        int customerColumn = -1;
        String goodsboxnum = "";
        String spell = "";
        String shopid = "";
        String goodsid = "";
        String price = "";
        String cutLine = "";
        String cutCol = "";
        String firstCol = "";//商品信息行
        int beginColInt = -1;
        int customerParamCol = -1;//客户参数读取位置
        int customerParamRow = 0;
        for(int i = 0 ; i< info.length ; ++ i){
            String value = info[i].split("=")[1];
            if(info[i].contains("商品行")){
                beginOfGoods = value;
            }
            if(info[i].contains("开始列")){
                beginColumn = value;
            }
            if(StringUtils.isNotEmpty(beginColumn)){
                beginColInt = Integer.parseInt(beginColumn);//开始列的int值
            }
            if(info[i].contains("首行")){
                firstCol = value;
            }
        }
        List<String> paramList = new ArrayList<String>();
        if(StringUtils.isEmpty(firstCol)){
            paramList = ExcelUtils.importFirstRowByIndex(importFile,Integer.parseInt(beginOfGoods)-2,beginColInt);//商品信息行
        }else{
            paramList = ExcelUtils.importFirstRowByIndex(importFile,Integer.parseInt(firstCol)-1,beginColInt);//商品信息行
        }
        if(paramList.size() == 1){
            String param = paramList.get(0);
            if("errorFile".equals(param)){
                request.setAttribute("msg","文件导入格式错误，请联系管理员");
            }else{
                request.setAttribute("msg","文件读取错误，请检查配置参数问题");
            }
        }else{
            for(int i = 0 ; i< info.length ; ++ i){
                String value = info[i].split("=")[1];
                String param = info[i].split("=")[0];
                //分离单号位置中的数字和字母
                if(info[i].contains("客户单号位置")){
                    String Column = value.replaceAll("\\d+", "");
                    if(Column.length() == 1){
                        customerColumn = (int)Column.charAt(0);
                        customerColumn = customerColumn - 64 ; //根据字母的Unicode编码和读取时的顺序 获取客户编码位置对应的列位
                    }else{
                        byte[] word = Column.getBytes();
                        int leng = word.length - 1;
                        customerColumn = leng * 26;
                        byte w = word[leng];
                        customerColumn = customerColumn + w - 64;
                    }
                    beginOfCustomer = value.replaceAll("[a-zA-Z]+","");
                }else if("商品条形码".equals(param)){
                    goodsIdetify = value;
                }else if("商品编码".equals(param)){
                    goodsid = value;
                }else if("商品数量".equals(param) ){
                    goodsNum = value;
                }else if("商品箱数".equals(param) ){
                    goodsboxnum =value;
                }else if("商品单价".equals(param) ){
                    price = value;
                }else if("商品助记符".equals(param)){
                    spell = value;
                }else if("商品店内码".equals(param)){
                    shopid = value;
                }else if(info[i].contains("截止行")){
                    cutLine = value ;
                    cutCol = value.split(":")[0];
                    if(cutLine.endsWith(":")){
                        cutLine = " ";
                    }else{
                        cutLine = cutLine.split(":")[1];
                    }
                } else if(info[i].contains("客户参数")){
                    String Column = value.replaceAll("\\d+", "");
                    customerParamCol = ExcelUtils.chagneCellColtoNumber(Column);
                    String customerRowStr = value.replaceAll("[a-zA-Z]+","");
                    customerParamRow = Integer.parseInt(customerRowStr);
                }
            }
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
            String customerParam = "";//读取到的客户参数
            Map map1 = new HashMap();
            //根据客户类型 获取导入的客户编码
            if(customerParamCol != -1){
                List<String> customerRowInfo = ExcelUtils.importFirstRowByIndex(importFile,customerParamRow-1, 0);
                if(customerRowInfo.size() > 0){
                    customerParam = (String) customerRowInfo.get(customerParamCol);
                }
            }
            //获取客户参数具体信息
            String str2 = "";
            if("2".equals(ctype) || "3".equals(ctype) ){//按店号按助记符 提取对应单元格中的数字
                for(int i=0;i<customerParam.length();i++){
                    if(customerParam.charAt(i)>=48 && customerParam.charAt(i)<=57){
                        str2+=customerParam.charAt(i);
                    }
                }
                customerParam = str2;
            }
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
            Map map = new HashMap();
            map.put("gtype",gtype);
            Map barcodeMap = new HashMap();
            if(StringUtils.isEmpty(busid)){
                map.put("info","busidEmpty");
            }else{
                String emptySheet = "";
                String oldOrderID = "";
                int count = 0;
                if(paramList.size()>0){
                    for(int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++){
                        List<Map<String, Object>> list =
                                ExcelUtils.importExcelBySheet(importFile,paramList,Integer.parseInt(beginOfGoods)-1,numSheet);//读取数据
                        //获取客户信息
                        if(StringUtils.isNotEmpty(beginOfCustomer) && customerColumn > 0){
                            List<String> idInfo = ExcelUtils.importFirstRowByIndex(importFile, Integer.parseInt(beginOfCustomer) - 1, beginColInt);
                            oldOrderID = idInfo.get(customerColumn - 1);//行信息从0位置开始
                        }
                        List<ModelOrder> wareList = new ArrayList<ModelOrder>();
                        for(int i = 0 ;i<list.size();i++){
                            //判断截止行是否等于对应的值
                            String judgetColValue = (String) list.get(i).get(cutCol);
                            if((StringUtils.isNotEmpty(judgetColValue) && judgetColValue.contains(cutLine) && StringUtils.isNotEmpty(cutLine))){
                                break;
                            }
                            //如果截止行对应列名的值为空 就判断list中是否有对应列名
                            boolean flag = list.get(i).containsKey(cutCol);
                            if(!flag && StringUtils.isNotEmpty(judgetColValue)){
                                break;
                            }
                            ModelOrder modelOrder = new ModelOrder();
                            //根据参数获取条形码和箱装量
                            String boxnum = (String) list.get(i).get(goodsboxnum);
                            String barcode = "";
                            try{
                                barcode = (String) list.get(i).get(goodsIdetify);
                            }catch (Exception e){
                                DecimalFormat df = new DecimalFormat("0");
                                Double barcode1 = (Double) list.get(i).get(goodsIdetify);
                                barcode = df.format(barcode1);
                            }

                            modelOrder.setBusid(busid);
                            modelOrder.setOrderId(oldOrderID);
                            modelOrder.setBoxnum(boxnum);
                            Object object =  list.get(i).get(goodsNum);
                            modelOrder.setUnitnum(String.valueOf(object));
                            //取表格价格
                            if("1".equals(pricetype) || StringUtils.isNotEmpty(price)){
                                modelOrder.setTaxprice((String) list.get(i).get(price));
                            }
                            //根据不同的参数 按不同的商品类型导入商品
                            if(StringUtils.isNotEmpty(barcode)){
                                modelOrder.setBarcode(barcode);
                                gtype = "1";
                            }else if(StringUtils.isNotEmpty(spell)){
                                modelOrder.setSpell((String) list.get(i).get(spell));
                                gtype = "3";
                            }else if(StringUtils.isNotEmpty(shopid)){
                                modelOrder.setShopid((String)list.get(i).get(shopid));
                                gtype = "2";
                            }else if(StringUtils.isNotEmpty(goodsid)){
                                modelOrder.setGoodsid((String)list.get(i).get(goodsid));
                                gtype = "4";
                            }
                            wareList.add(modelOrder);
                        }
                        if(wareList.size()> 0) {
                            Map insertMap = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
                            //组装导入结果
                            List resultList = insertMapReturn(insertMap,0, emptySheet, count, map, barcodeMap );
                            map = (Map) resultList.get(0);
                            barcodeMap = (Map) resultList.get(1);
                        }
                    }
                }else{
                    map.put("info","empty");
                }
                if(emptySheet !="" && count != hssfWorkbook.getNumberOfSheets()){
                    map.put("emptysheet",emptySheet);
                }else if(count == hssfWorkbook.getNumberOfSheets()){
                    map.put("info","empty");
                }
            }
            sheetModelMsg(barcodeMap,map);
        }
        return SUCCESS ;
    }

    /**
     * 根据参数读取excel对应列的自适应导入
     *
     * @return
     * @throws Exception
     */
    public String importMultiInfoByDigitalParam() throws Exception {
        request.setCharacterEncoding("utf-8");
        String busid = request.getParameter("busid");//客户编码
        String ctype = request.getParameter("ctype");
        String gtype = request.getParameter("gtype");
        String pricetype = request.getParameter("pricetype");
        String fileparam = request.getParameter("fileparam");
        if(StringUtils.isEmpty(fileparam)){
            String id = request.getParameter("id");
            ImportSet importSet = getImportService().showImportModelById(id);
            fileparam = importSet.getFileparam();
        }
        String[] info = fileparam.split(";");
        List<String> readerInformation = new ArrayList<String>();
        //读取导入文件
        InputStream is = new FileInputStream(importFile);
        Workbook hssfWorkbook = null;
        String type = ExcelUtils.getFileByFile(importFile);
        if ("xls".equals(type)) {
            hssfWorkbook = new HSSFWorkbook(is);
        } else if ("xlsx".equals(type)) {
            hssfWorkbook = new XSSFWorkbook(importFile.getPath());
        } else if ("csv".equals(type)) {
            InputStream raf = new FileInputStream(importFile);
            //设置输出内容格式，防止乱码
            String charsetName = getFileCodeString(importFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(raf,charsetName));
            String line = reader.readLine();
            while (line != null) {
                readerInformation.add(line);
                line = reader.readLine();
            }
            reader.close();
        } else {
            //type=null 的情况下 先按Excel 2007版本以上的文件读取方式读取，读取不到则按txt格式读取
            try {
                hssfWorkbook = new XSSFWorkbook(importFile.getPath());
            } catch (InvalidOperationException e) {
                type = "txt";
                InputStream raf = new FileInputStream(importFile);
                BufferedReader reader = new BufferedReader(new InputStreamReader(raf));
                String line = reader.readLine();
                while (line != null) {
                    readerInformation.add(line);
                    line = reader.readLine();
                }
            }
        }
        Map map = new HashMap();
        Map barcodeMap = new HashMap();
        map.put("gtype", gtype);
        int beginRow = 0; //开始行
        int goodsCol = -1;
        int divideCol = -1;
        int customerTypeCol = -1;//客户类型读取位置
        int customerTypeRow = -1;
        int numCol = -1;
        int priceCol = -1;
        int boxnumCol = -1;
        int customerCol = -1;
        int customerRow = 0;
        int remarkCol = -1;
        int remarkRow = -1;
        int otherCol = -1 ;//和拆分所在列一起用
        int detailcount = 0 ;//拆分时导入多少条商品明细统计
        String custRegular = null;
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
            } else if (info[i].contains("客户单元格")) {
                String Column = value.replaceAll("\\d+", "");
                customerTypeCol = ExcelUtils.chagneCellColtoNumber(Column);
                String customerRowStr = value.replaceAll("[a-zA-Z]+", "");
                customerTypeRow = Integer.parseInt(customerRowStr);
            } else if (info[i].contains("备注")) {
                String Column = value.replaceAll("\\d+", "");
                remarkCol = ExcelUtils.chagneCellColtoNumber(Column);
                String customerRowStr = value.replaceAll("[a-zA-Z]+", "");
                remarkRow = Integer.parseInt(customerRowStr);
            } else if (info[i].contains("日期/其它列")) {
                otherCol = ExcelUtils.chagneCellColtoNumber(value);
            }else if (info[i].contains("客户正则") ) {
                String[] str = info[i].split("=");
                if(str.length >1){
                    custRegular = info[i].split("=")[1];
                }
            }
        }
        //获取客户单号
        String orderid = getCellValueByParam(importFile, customerCol, customerRow, readerInformation);
        //获取模板备注
        String remark = getCellValueByParam(importFile, remarkCol, remarkRow, readerInformation);

        Map map1 = new HashMap();
        //根据客户类型 获取导入的客户编码
        String customerValue = "";
        if ("1".equals(ctype)) {
            customerValue = request.getParameter("busid");
        } else {
            if (customerTypeRow != -1) {
                String custParam = getCellValueByParam(importFile, customerTypeCol, customerTypeRow, readerInformation);
                //根据正则解析客户编号
                if(StringUtils.isNotEmpty(custRegular)){
                    custParam = getCusidByCustRegular(custRegular,custParam);
                }
                if (StringUtils.isNotEmpty(custParam) && "null" != custParam ) {
                    if ("2".equals(ctype)) {
                        map1.put("pid", busid);//总店
                        map1.put("shopno", custParam);//店号
                        customerValue = returnCustomerID(map1);
                    } else if ("3".equals(ctype)) {
                        map1.put("shortcode", custParam);
                        customerValue = returnCustomerID(map1);
                    } else if ("4".equals(ctype)) {
                        map1.put("name", custParam);
                        customerValue = returnCustomerID(map1);
                    } else if ("5".equals(ctype)) {
                        map1.put("shortname", custParam);
                        customerValue = returnCustomerID(map1);
                    } else if ("6".equals(ctype)) {
                        map1.put("address", custParam);
                        customerValue = returnCustomerID(map1);
                    } else if ("7".equals(ctype)) {
                        map1.put("id", custParam);
                        customerValue = returnCustomerID(map1);
                    }
                }
            }
        }
        //系统参数（是否允许重复商品）
        String isreapt = getSysParamValue("IsSalesGoodsRepeat");
        if ("csv".equals(type) || "txt".equals(type)) {
            List<ModelOrder> wareList = readTextModelOrder(readerInformation, isreapt, beginRow, goodsCol, numCol, priceCol, boxnumCol, pricetype, gtype, customerValue, orderid);
            if (wareList.size() > 0) {
                wareList.get(0).setRemark(remark);
                if (StringUtils.isEmpty(wareList.get(0).getBusid())) {
                    map.put("info", "busidEmpty");
                } else {
                    map = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
                }
            } else {
                map.put("info", "empty");
            }
            modelMsg(map);
        } else {
            String emptySheet = "";
            int count = -1;
            //根据分隔列导入多张订单,不合并重复的商品
            if (divideCol > -1) {
                Map modelResultMap = getModelOrderMapByDivideCol(beginRow, divideCol, goodsCol, numCol, priceCol, boxnumCol,
                        customerTypeRow -1,pricetype, gtype, ctype,otherCol,orderid,busid,custRegular);
                Iterator<Map.Entry<String, List>> it = modelResultMap.entrySet().iterator();
                while (it.hasNext()) {
                    ++ count;
                    Map.Entry<String, List> entry = it.next();
                    List wareList = entry.getValue();
                    ModelOrder modelOrder = (ModelOrder)wareList.get(0);
                    Customer valideCustomer = getCustomerById(modelOrder.getBusid());
                    if (null == valideCustomer) {
                        if(map.containsKey("customerid")){
                            map.put("customerid", map.get("info")+","+modelOrder.getBusid());
                        }else{
                            map.put("customerid", modelOrder.getBusid());
                        }
                    } else if (wareList.size() > 0) {
                        Map insertMap = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
                        List list = insertMapReturn(insertMap,-99, emptySheet, count, map, barcodeMap );
                        map = (Map) list.get(0);
                        barcodeMap = (Map) list.get(1);
                        String flag = (String) insertMap.get("flag");
                        if("true".equals(flag)){
                            if(insertMap.containsKey("barcode")){
                                List<String> errorList = (List<String>) insertMap.get("barcode");
                                detailcount += wareList.size() - errorList.size();
                            }else if(insertMap.containsKey("shopid")){
                                List<String> errorList = (List<String>) insertMap.get("shopid");
                                detailcount += wareList.size() - errorList.size();
                            }else if(insertMap.containsKey("spell")){
                                List<String> errorList = (List<String>) insertMap.get("spell");
                                detailcount += wareList.size() - errorList.size();
                            }else if(insertMap.containsKey("goodsid")){
                                List<String> errorList = (List<String>) insertMap.get("goodsid");
                                detailcount += wareList.size() - errorList.size();
                            }else{
                                detailcount += wareList.size();
                            }
                        }

                    }
                }
                barcodeMap.put("count",detailcount);
            } else {
                String customerid = customerValue;
                //分别读取对应列值
                for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                    //Excel按工作表读，分别读取每个工作表指定位置的客户信息
                    if(numSheet > 0 && !"1".equals(ctype)){
                        String custParam = ExcelUtils.getFileValueByParam(importFile,numSheet,customerTypeRow,customerTypeCol);
                        if(StringUtils.isNotEmpty(custRegular)){
                            custParam = getCusidByCustRegular(custRegular,custParam);
                        }
                        if (StringUtils.isNotEmpty(custParam)) {
                            if ("2".equals(ctype)) {
                                Customer c = getCustomerById(busid);
                                map1.put("pid", c.getPid());//总店
                                map1.put("shopno", custParam);//店号
                                customerid = returnCustomerID(map1);
                            } else if ("3".equals(ctype)) {
                                map1.put("shortcode", custParam);
                                customerid = returnCustomerID(map1);
                            } else if ("4".equals(ctype)) {
                                map1.put("name", custParam);
                                customerid = returnCustomerID(map1);
                            } else if ("5".equals(ctype)) {
                                map1.put("shortname", custParam);
                                customerid = returnCustomerID(map1);
                            } else if ("6".equals(ctype)) {
                                map1.put("address", custParam);
                                customerid = returnCustomerID(map1);
                            } else if ("7".equals(ctype)) {
                                map1.put("id", custParam);
                                customerid = returnCustomerID(map1);
                            }
                        }
                    }
                    Customer customer =getCustomerById(customerid);
                    if(null != customer){
                        //根据是否合并相同商品组装商品明细
                        List<ModelOrder> wareList = getModelOrderListByPram(isreapt, beginRow, numSheet, goodsCol, numCol, priceCol, boxnumCol, pricetype, gtype, customerid, orderid);
                        if (StringUtils.isEmpty(customerid)) {
                            map.put("info", "busidEmpty");
                        } else if (wareList.size() > 0) {
                            wareList.get(0).setRemark(remark);
                            Map insertMap = getSalesOrderService().addMDSalesOrder(wareList, ctype, gtype);
                            //组装导入结果
                            List list = insertMapReturn(insertMap,numSheet, emptySheet, count, map, barcodeMap );
                            map = (Map) list.get(0);
                            barcodeMap = (Map) list.get(1);
                        }
                    }else{
                        if(map.containsKey("errorsheet")){
                            String errorsheet = map.get("errorsheet").toString();
                            map.put("errorsheet",errorsheet+","+(numSheet+1));
                        }else{
                            map.put("errorsheet",numSheet+1);
                        }
                    }
                }
                if (emptySheet != "" && count != hssfWorkbook.getNumberOfSheets()) {
                    map.put("emptysheet", emptySheet);
                } else if (count == hssfWorkbook.getNumberOfSheets()) {
                    map.put("info", "empty");
                }
            }
            sheetModelMsg(barcodeMap, map);
        }
        return SUCCESS;
    }


}

