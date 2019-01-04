package com.hd.agent.common.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenwei on 2016-05-05.
 */
public class ExcelTheadUtils {
    private static int theadCount = 8;

    /**
     * 使用多线程进行Excel写操作，提高写入效率。
     */
    public static void multiThreadWriteCSV(List<Object> dataList ,Map formmaterMap ,Map backMap, String[] cols ,Map<String, Object> firstMap, String title,Map<String,String> goodsMap) {
        /**
         * 使用线程池进行线程管理。
         */
        ExecutorService es = Executors.newCachedThreadPool();
        /**
         * 使用计数栅栏
         */
        CountDownLatch doneSignal = new CountDownLatch(theadCount);
        //文件存放路径
        String path = OfficeUtils.getFilepath() + "/export/";
        File file2 = new File(path);
        //判断文件夹是否存在,如果不存在则创建文件夹
        if (!file2.exists()) {
            file2.mkdir();
        }
        String fileName =title + ".csv";
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO8859-1" )+"\"");
            response.setCharacterEncoding("GBK");
            PrintWriter os = response.getWriter();
            BufferedWriter bw = new BufferedWriter(os);

            String firstRow = null;
            for(Map.Entry<String, Object> entry : firstMap.entrySet()){
                Object object = entry.getValue();
                if(null!=object){
                    if (object instanceof String) {
                        if(null==firstRow){
                            firstRow = object.toString();
                        }else{
                            firstRow += ","+object.toString();
                        }
                    }
                }
            }
            bw.append(firstRow+"\r\n");
            Map bwMap = new HashMap();
            long a1 = System.currentTimeMillis();
            int count = dataList.size()/theadCount;
            for(int i=0;i<theadCount;i++){
                if(i<theadCount-1){
                    int start = i*count;
                    int end = (i+1)*count-1;
                    List dlist = dataList.subList(start,end+1);
                    es.submit(new PoiWriterCSV(doneSignal,i+"",bwMap, start, end,dlist,formmaterMap,backMap,cols,goodsMap));
                }else{
                    int start = i*count;
                    int end = dataList.size()-1;
                    List dlist = dataList.subList(i * count, dataList.size());
                    es.submit(new PoiWriterCSV(doneSignal, i+"",bwMap, start, end,dlist,formmaterMap,backMap,cols,goodsMap));
                }
            }
            /**
             * 使用CountDownLatch的await方法，等待所有线程完成sheet操作
             */
            doneSignal.await();
            es.shutdown();
            long c1 = System.currentTimeMillis();
            System.out.println(c1-a1);
            for(int i=0;i<theadCount;i++){
                StringBuffer sb = (StringBuffer) bwMap.get(i+"");
                bw.append(sb);
            }
            bw.flush();
            bw.close();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用多线程进行Excel写操作，提高写入效率。
     */
    public static void multiThreadWriteExcel(List<Object> dataList ,Map formmaterMap ,Map backMap, String[] cols ,Map<String, Object> firstMap, String title,Map<String,String> goodsMap) {
        /**
         * 使用线程池进行线程管理。
         */
        ExecutorService es = Executors.newCachedThreadPool();
        /**
         * 使用计数栅栏
         */
        CountDownLatch doneSignal = new CountDownLatch(theadCount);
        //文件存放路径
        String path = OfficeUtils.getFilepath() + "/export/";
        File file2 = new File(path);
        //判断文件夹是否存在,如果不存在则创建文件夹
        if (!file2.exists()) {
            file2.mkdir();
        }
        try {
            int count = dataList.size()/theadCount;
            List<String> fileList = new ArrayList();
            long a1 = System.currentTimeMillis();
            for(int i=0;i<theadCount;i++){
                String filename = CommonUtils.getDataNumberWithRand()+".xls";
                fileList.add(filename);
                if(i<theadCount-1){
                    int start = i*count;
                    int end = (i+1)*count-1;
                    List dlist = dataList.subList(start,end+1);
                    es.submit(new PoiWriter(doneSignal,start, end,"xls",dlist,formmaterMap,backMap,cols,firstMap,filename,goodsMap));
                }else{
                    int start = i*count;
                    int end = dataList.size()-1;
                    List dlist = dataList.subList(i * count, dataList.size());
                    es.submit(new PoiWriter(doneSignal, start, end,"xls",dlist,formmaterMap,backMap,cols,firstMap,filename,goodsMap));
                }
            }
            /**
             * 使用CountDownLatch的await方法，等待所有线程完成sheet操作
             */
            doneSignal.await();
            es.shutdown();
            Workbook book  = new HSSFWorkbook();
            Sheet sheet = book.createSheet("sheet1");
            String dowloadFileName = CommonUtils.getDataNumberWithRand()+".xls";
            File file = new File(OfficeUtils.getFilepath() + "/export/", dowloadFileName);
            if(!file.exists()){
                file.createNewFile();
            }
            int i = 0;
            for(String fileName : fileList){
                InputStream input = new FileInputStream(OfficeUtils.getFilepath() + "/export/"+fileName);
                Workbook wbnew  =  new HSSFWorkbook(input);
//                Sheet sheet = book.createSheet("sheet1");
//                sheet =  wbnew.cloneSheet(0);
                PoiUtils.copySheet(book.getSheetAt(0),wbnew.getSheetAt(0),book,wbnew);
//                deleteExcelFile(OfficeUtils.getFilepath() + "/export/"+fileName, fileName);
            }
            OutputStream out = new FileOutputStream(file);
            book.write(out);
            out.close();
            downloadExcel(OfficeUtils.getFilepath() + "/export/", dowloadFileName,title+".xls");
            deleteExcelFile(OfficeUtils.getFilepath() + "/export/"+dowloadFileName, dowloadFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 使用多线程进行Excel写操作，提高写入效率。
     */
    public static void multiThreadWriteExcel2007(List<Object> dataList ,Map formmaterMap ,Map backMap, String[] cols ,Map<String, Object> firstMap, String title,Map<String,String> goodsMap) {
        /**
         * 使用线程池进行线程管理。
         */
        ExecutorService es = Executors.newCachedThreadPool();
        /**
         * 使用计数栅栏
         */
        CountDownLatch doneSignal = new CountDownLatch(theadCount);
        try {
            int count = dataList.size()/theadCount;
            List<String> fileList = new ArrayList();
            long a1 = System.currentTimeMillis();
            for(int i=0;i<theadCount;i++){
                String filename = CommonUtils.getDataNumberWithRand()+".xlsx";
                fileList.add(filename);
                if(i<theadCount-1){
                    int start = i*count;
                    int end = (i+1)*count-1;
                    List dlist = dataList.subList(start,end+1);
                    es.submit(new PoiWriter(doneSignal,  start, end,"xlsx",dlist,formmaterMap,backMap,cols,firstMap,filename,goodsMap));
                }else{
                    int start = i*count;
                    int end = dataList.size()-1;
                    List dlist = dataList.subList(i * count, dataList.size());
                    es.submit(new PoiWriter(doneSignal,  start, end,"xlsx",dlist,formmaterMap,backMap,cols,firstMap,filename,goodsMap));
                }
            }
            /**
             * 使用CountDownLatch的await方法，等待所有线程完成sheet操作
             */
            doneSignal.await();
            es.shutdown();
            long c1 = System.currentTimeMillis();
            System.out.println(c1-a1);
            Workbook book  = new SXSSFWorkbook(-1);
            String dowloadFileName = CommonUtils.getDataNumberWithRand()+".xlsx";
            File file = new File(OfficeUtils.getFilepath() + "/export/", dowloadFileName);
            if(!file.exists()){
                file.createNewFile();
            }
            long a = System.currentTimeMillis();
            int i = 1;
            for(String fileName : fileList){
                InputStream input = new FileInputStream(OfficeUtils.getFilepath() + "/export/"+fileName);
                Workbook wbnew  =  new XSSFWorkbook(input);
                Sheet sheet = book.createSheet("sheet"+i);
                sheet =  wbnew.getSheetAt(0);
                input.close();
                i++;
//                PoiUtils.copySheet(book.getSheetAt(0),wbnew.getSheetAt(0),book,wbnew);
                deleteExcelFile(OfficeUtils.getFilepath() + "/export/"+fileName, fileName);
            }
            long c = System.currentTimeMillis();
            System.out.println(c-a);
            OutputStream out = new FileOutputStream(file);
            book.write(out);
            out.close();
            downloadExcel(OfficeUtils.getFilepath() + "/export/", dowloadFileName,title+".xlsx");
            deleteExcelFile(OfficeUtils.getFilepath() + "/export/"+dowloadFileName, dowloadFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * sheet的row使用treeMap存储的，是非线程安全的，所以在创建row时需要进行同步操作。
     * @param sheet
     * @param rownum
     * @return
     */
    private synchronized static Row getRow(Sheet sheet, int rownum) {
        return sheet.createRow(rownum);
    }

    /**
     * 进行sheet写操作的sheet。
     * @author alex
     *
     */
    protected static class PoiWriterCSV implements Runnable {

        private final CountDownLatch doneSignal;
        private Map bwMap;
        private int start;
        private int end;

        private List dataList;
        private Map formmaterMap;
        private Map backMap;
        private String[] cols ;
        private Map<String,String> goodsMap;
        private String index;

        public PoiWriterCSV(CountDownLatch doneSignal,String index,Map bwMap, int start,int end,
                         List dataList ,Map formmaterMap ,Map backMap, String[] cols ,Map<String,String> goodsMap) {
            this.doneSignal = doneSignal;
            this.bwMap = bwMap;
            this.start = start;
            this.end = end;

            this.dataList = dataList;
            this.formmaterMap = formmaterMap;
            this.backMap = backMap;
            this.cols = cols;
            this.goodsMap = goodsMap;
            this.index = index;
        }

        public void run() {
            int i = 0;
            int j = start;
            StringBuffer bw = new StringBuffer();
            try {
                while (j <= end) {
                    Object dataObject = dataList.get(i);
                    Map dataMap = null;
                    if(dataObject instanceof Map){
                        dataMap = (Map) dataObject;
                    }else{
                        dataMap = PropertyUtils.describe(dataList.get(i));
                    }
                    if(dataMap.containsKey("goodsInfo")){
                        Object object = dataMap.get("goodsInfo");
                        if(null != object && object.toString().startsWith("{")){
                            Map goodsValueMap = CommonUtils.beanToMap(object);
                            for(Map.Entry<String, String> entry : goodsMap.entrySet()) {
                                String key = entry.getKey();
                                String value = entry.getValue();
                                dataMap.put(key,goodsValueMap.get(value));
                            }
                        }
                    }
                    StringBuffer rowStr = new StringBuffer();
                    for(String col : cols){
                        Object object = getExportMap(formmaterMap, backMap, dataMap, col, i);
                        String key = col;
                        if(null!=object){
                            if (object instanceof String) {
                                rowStr.append(object.toString()).append(",");
                            }else if(object instanceof BigDecimal){
                                BigDecimal bignum = (BigDecimal) object;
                                if(null!=bignum){
                                    if("taxprice".equals(key)){
                                        double bignumstr = bignum.doubleValue();
                                        String[] strarr =(bignumstr+"").split("\\.");
                                        if(strarr.length>1 && strarr[1].length()<=2){
                                            rowStr.append(bignum.setScale(2,BigDecimal.ROUND_HALF_UP).toString()).append(",");
                                        }else{
                                            rowStr.append(bignumstr).append(",");
                                        }
                                    }else{
                                        rowStr.append(bignum.setScale(2,BigDecimal.ROUND_HALF_UP).toString()).append(",");
                                    }
                                }else{
                                    rowStr.append("0,");
                                }

                            }else if(object instanceof Timestamp){
                                Timestamp timestamp = (Timestamp) object;
                                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                rowStr.append(sdf.format(timestamp)).append(",");
                            }else if(object instanceof Date){
                                Date date = (Date) object;
                                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                rowStr.append(sdf.format(date)).append(",");
                            }else if(object instanceof Integer){
                                Integer intnum = (Integer) object;
                                rowStr.append(intnum).append(",");
                            }
                        }else{
                            rowStr.append(",");
                        }

                    }
                    rowStr = rowStr.deleteCharAt(rowStr.length()-1);
                    bw.append(rowStr+"\r\n");
                    ++i;
                    ++j;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                bwMap.put(index,bw);
                doneSignal.countDown();
            }
        }
    }

    /**
     * 进行sheet写操作的sheet。
     * @author alex
     *
     */
    protected static class PoiWriter implements Runnable {

        private final CountDownLatch doneSignal;
        private int start;
        private int end;

        private List dataList;
        private Map formmaterMap;
        private Map backMap;
        private String[] cols ;
        private Map<String, Object> firstMap;
        private String fileName;
        private Map<String,String> goodsMap;
        private String type;
        public PoiWriter(CountDownLatch doneSignal,int start,int end,String type,List dataList ,Map formmaterMap ,Map backMap, String[] cols ,Map<String, Object> firstMap, String fileName,Map<String,String> goodsMap) {
            this.doneSignal = doneSignal;
            this.start = start;
            this.end = end;
            this.type = type;
            this.dataList = dataList;
            this.formmaterMap = formmaterMap;
            this.backMap = backMap;
            this.cols = cols;
            this.firstMap = firstMap;
            this.fileName = fileName;
            this.goodsMap = goodsMap;

        }

        public void run() {
            //文件存放路径
            String path = OfficeUtils.getFilepath() + "/export/";
            File file2 = new File(path);
            //判断文件夹是否存在,如果不存在则创建文件夹
            if (!file2.exists()) {
                file2.mkdir();
            }
            try {
                File file = new File(path, fileName);
                if(!file.exists()){
                    file.createNewFile();
                }
                Workbook book = null;
                if("xls".equals(type)){
                    book = new HSSFWorkbook();
                }else if("xlsx".equals(type)){
                    book = new SXSSFWorkbook(1000);
                }

                CellStyle intStyle = book.createCellStyle();
                CellStyle decimalStyle = book.createCellStyle();
                CellStyle decimalStyle2 = book.createCellStyle();
                DataFormat format = book.createDataFormat();
                Sheet sheet = book.createSheet("sheet1");
                int beginNum = 0;
                if(start==0){
                    Row firstRow = sheet.createRow(0);
                    //首行
                    int firstCellNum = 0;
                    for(Map.Entry<String, Object> entry : firstMap.entrySet()){
                        Cell cell = firstRow.createCell(firstCellNum++);
                        Object object = entry.getValue();
                        if(null!=object){
                            if (object instanceof String) {
                                cell.setCellValue((String)object);
                                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                            }
                        }else{
                            cell.setCellValue("");
                        }
                    }
                    beginNum ++;
                }
                for(int i=0;i<dataList.size();i++){
                    Row row = sheet.createRow(i+beginNum);

                    int cellNum = 0;
                    Object dataObject = dataList.get(i);
                    Map dataMap = null;
                    if(dataObject instanceof Map){
                        dataMap = (Map) dataObject;
                    }else{
                        dataMap = PropertyUtils.describe(dataList.get(i));
                    }
                    if(dataMap.containsKey("goodsInfo")){
                        Object object = dataMap.get("goodsInfo");
                        if(null != object && object.toString().startsWith("{")){
                            Map goodsValueMap = CommonUtils.beanToMap(object);
                            for(Map.Entry<String, String> entry : goodsMap.entrySet()) {
                                String key = entry.getKey();
                                String value = entry.getValue();
                                dataMap.put(key,goodsValueMap.get(value));
                            }
                        }
                    }
                    for(String col : cols){
                        //组装对应col列的值
                        Object object = getExportMap(formmaterMap ,backMap,dataMap ,col , i);
                        Cell cell = row.createCell(cellNum++);
                        String key = col;
                        if(null!=object){
                            if (object instanceof String) {
                                cell.setCellValue((String)object);
                                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                            }else if(object instanceof BigDecimal){
                                BigDecimal bignum = (BigDecimal) object;
                                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                                if("taxprice".equals(key)){
                                    if(null!=bignum){
                                        double bignumstr = bignum.doubleValue();
                                        String[] strarr =(bignumstr+"").split("\\.");
                                        if(strarr.length>1 && strarr[1].length()<=2){
                                            decimalStyle.setDataFormat(format.getFormat("0.00"));
                                            cell.setCellStyle(decimalStyle);
                                        }
                                    }
                                }else if("totalweight".equals(key) || "totalvolume".equals(key) || "singlevolume".equals(key) || "glength".equals(key) ||
                                        "ghight".equals(key) || "gwidth".equals(key) || "gdiameter".equals(key) || "grossweight".equals(key) || "netweight".equals(key) || "nowprice".equals(key) || "nowboxprice".equals(key) || "oldprice".equals(key) || "oldboxprice".equals(key)){
                                    decimalStyle2.setDataFormat(format.getFormat("0.000000"));
                                    cell.setCellStyle(decimalStyle2);
                                }else if("boxnum".equals(key)){
                                    intStyle.setDataFormat(format.getFormat("0"));
                                    cell.setCellStyle(intStyle);
                                }else{
                                    decimalStyle.setDataFormat(format.getFormat("0.00"));
                                    cell.setCellStyle(decimalStyle);
                                }
                                if(null!=bignum){
                                    cell.setCellValue(bignum.doubleValue());
                                }
                            }else if(object instanceof Timestamp){
                                Timestamp timestamp = (Timestamp) object;
                                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                cell.setCellValue(sdf.format(timestamp));
                            }else if(object instanceof Date){
                                Date date = (Date) object;
                                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                cell.setCellValue(sdf.format(date));
                            }else if(object instanceof Integer){
                                Integer intnum = (Integer) object;
                                intStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
                                cell.setCellStyle(intStyle);
                                cell.setCellValue(intnum);
                            }
                        }else{
                            cell.setCellValue("");
                        }
                    }
                }
                //设置自适应列宽
                if(null!=dataList && dataList.size()>0){
                    Object dataObject = dataList.get(0);
                    Map<String, Object> rowMap1 = null;
                    if(dataObject instanceof Map){
                        rowMap1 = (Map) dataObject;
                    }else{
                        rowMap1 = PropertyUtils.describe(dataList.get(0));
                    }
                    int i = 0;
                    for(Map.Entry<String, Object> entry : rowMap1.entrySet()){
                        sheet.autoSizeColumn(i);
                        int maxColumnWidth = sheet.getColumnWidth(i);
                        if(maxColumnWidth == 65280){
                            sheet.setColumnWidth(i, 5000);//数据过长时设置最大的列宽
                        }else{
                            sheet.setColumnWidth(i, maxColumnWidth+500);
                        }
                        i++;
                    }
                }

                OutputStream out = new FileOutputStream(file);
                book.write(out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                doneSignal.countDown();
            }
        }
    }
    /**
     * 根据参数组装导出的一条记录
     * @param formmaterMap js中的formmater方法
     * @param backMap 组装的参数Map
     * @param dataMap 需要导出的数值
     * @param col 导出的某列i
     *@param i  第几条记录
     * @return
     * @author lin_xx
     * @date 2016-4-2
     * @throws Exception
     */
    public static Object getExportMap(Map formmaterMap ,Map backMap, Map dataMap , String col ,int i) throws Exception {
        if(backMap.containsKey(col)){
            String key = (String) backMap.get(col);
            return dataMap.get(key);
        }else if(formmaterMap.containsKey(col)){
            ScriptEngineManager sem = new ScriptEngineManager();
            ScriptEngine engine = sem.getEngineByName("javascript");
            String script = (String) formmaterMap.get(col);
             //js方法名称
            String fucname = null != formmaterMap.get(col+"fuc") ? (String)formmaterMap.get(col+"fuc") : "formatter";
            try{
                // 执行脚本
                engine.eval(script);
                Invocable inv = (Invocable) engine;
                // 执行方法并传递参数
                Object value = null ;
                //参数在数据库中不存在 就从JS方法里去对应参数
                if(dataMap.containsKey(col)){
                    value = dataMap.get(col);
                }else{
                    value =  backMap.get(col);
                }
                if(null == value){
                    value = backMap.get(col);
                }
                String index = i + "";
                Object result = null;
                Object obj = new Object();
                if("status".equals(col)){
                    if("1".equals(value)){
                        obj = "暂存";
                    }else if("2".equals(value)){
                        obj = "保存";
                    }else if("3".equals(value)){
                        obj = "审核通过";
                    }else if("4".equals(value)){
                        obj = "关闭";
                    }else if("5".equals(value)){
                        obj = "中止";
                    }else if("6".equals(value)){
                        obj = "审核中";
                    }
                }else{
                    obj = inv.invokeFunction("formatter", value , dataMap , index);
                }

                if(null == obj){
                    String returnName = (String) backMap.get(col);
                    result = dataMap.get(returnName);
                }else{
                     if("formatter".equals(fucname) || "formatterMoney".equals(fucname) || "formatterBigNumNoLen".equals(fucname)
                            || "formatterDefineMoney".equals(fucname)){
                        if(StringUtils.isNotEmpty((String)obj)){
                            result = new BigDecimal((String)obj);
                        }else{
                            result = BigDecimal.ZERO;
                        }
                    }else{
                        result = obj;
                    }
                }
                if("null".equals(result) || "NaN".equals(result) || "NaN%".equals(result)){
                    return "";
                }else{
                    return result;
                }
            }catch (Exception e){
                return dataMap.get(col);
            }

        }else{
            if(!dataMap.containsKey(col) || "null".equals(dataMap.get(col)) ){
                return "";
            }else{
                return dataMap.get(col);
            }
        }
    }
    /**
     * 下载已经导出的文件到客户端
     * @param path 下载文件路径
     * @param fileName 文件名称
     * @throws Exception
     * @author zhengziyong
     * @date 2013-3-12
     */
    public static void downloadExcel(String path, String fileName,String title) throws Exception{
        HttpServletResponse response = ServletActionContext.getResponse();
        File file = new File(path, fileName);
        InputStream is = new BufferedInputStream(new FileInputStream(file));
        response.addHeader("Content-Length", "" + file.length());
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=\"" + new String(title.getBytes("gb2312"), "ISO8859-1" )+"\"");
        PrintWriter os = response.getWriter();
        int i;
        while ((i = is.read()) != -1) {
            os.write(i);
        }
        is.close();
        os.flush();
        os.close();
    }

    /**
     * 删除文件
     * @param path 文件路径
     * @param fileName 文件名称
     * @throws Exception
     * @author zhengziyong
     * @date 2013-3-12
     */
    public static void deleteExcelFile(String path, String fileName) throws Exception{
        File file = new File(path, fileName);
        if(file.exists()){
            file.delete();
        }
    }

}
