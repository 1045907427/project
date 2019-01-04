package com.hd.agent.common.util;

import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * Created by Administrator on 2015/6/18.
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class ExcelModelUtils {

    /**
     *
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2015/6/18
     */

    /**
     * 取得指定单元格行和列
     * @param keyMap 所有单元格行、列集合
     * @param key 单元格标识
     * @return 0：列 1：行（列表型数据不记行，即1无值）
     */
    public static int[] getPos(HashMap keyMap, String key){
        int[] ret = new int[0];

        String val = (String)keyMap.get(key);

        if(val == null || val.length() == 0)
            return ret;

        String pos[] = val.split(",");

        if(pos.length == 1 || pos.length == 2){
            ret = new int[pos.length];
            for(int i0 = 0; i0 < pos.length; i0++){
                if(pos[i0] != null && pos[i0].trim().length() > 0){
                    ret[i0] = Integer.parseInt(pos[i0].trim());
                } else {
                    ret[i0] = 0;
                }
            }
        }
        return ret;
    }

    /**
     * 取对应格子的值
     * @param sheet
     * @param rowNo 行
     * @param cellNo 列
     * @return
     * @throws IOException
     */
    public static String getCellValue(HSSFSheet sheet,int rowNo,int cellNo) {
        String cellValue = null;
        HSSFRow row = sheet.getRow(rowNo);
        HSSFCell cell = row.getCell(cellNo);
        if (cell != null) {
            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                cellValue = getCutDotStr(Double.toString(cell.getNumericCellValue()));
            } else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                cellValue = cell.getStringCellValue();
            }
            if (cellValue != null) {
                cellValue = cellValue.trim();
            }
        } else {
            cellValue = null;
        }
        return cellValue;
    }

    /**
     * 取整数
     * @param srcString
     * @return
     */
    private static String getCutDotStr(String srcString) {
        String newString = "";
        if (srcString != null && srcString.endsWith(".0")) {
            newString = srcString.substring(0,srcString.length()-2);
        } else {
            newString = srcString;
        }
        return newString;
    }

    /**
     * 读数据模板
     * @param templateFileName //模板地址
     * @throws IOException
     */
    public static HashMap[] getTemplateFile(String templateFileName) throws IOException {
        FileInputStream fis = new FileInputStream(templateFileName);
        HSSFWorkbook wbPartModule = new HSSFWorkbook(fis);
        int numOfSheet = wbPartModule.getNumberOfSheets();
        HashMap[] templateMap = new HashMap[numOfSheet];
        for(int i = 0; i < numOfSheet; i++){
            HSSFSheet sheet = wbPartModule.getSheetAt(i);
            templateMap[i] = new HashMap();
            readSheet(templateMap[i], sheet);
        }

        fis.close();
        return templateMap;
    }

    /**
     * 读模板数据的样式值置等信息
     * @param keyMap
     * @param sheet
     */
    private static void readSheet(HashMap keyMap, HSSFSheet sheet){
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();

        for (int j = firstRowNum; j <= lastRowNum; j++) {
            HSSFRow rowIn = sheet.getRow(j);
            if(rowIn == null) {
                continue;
            }
            int firstCellNum = rowIn.getFirstCellNum();
            int lastCellNum = rowIn.getLastCellNum();
            for (int k = firstCellNum; k <= lastCellNum; k++) {
//              HSSFCell cellIn = rowIn.getCell((short) k);
                HSSFCell cellIn = rowIn.getCell(k);
                if(cellIn == null) {
                    continue;
                }

                int cellType = cellIn.getCellType();
                System.out.println("celltype:" + cellType);
                if(HSSFCell.CELL_TYPE_STRING != cellType) {
                    continue;
                }
                String cellValue = cellIn.getStringCellValue();
                System.out.println("cellValue:"+cellValue);
                if(cellValue == null) {
                    continue;
                }
                cellValue = cellValue.trim();

                if(cellValue.length() > 3 && cellValue.substring(0,3).equals("<!%")) {
                    String key = cellValue.substring(3, cellValue.length());
                    String keyPos = Integer.toString(k)+","+Integer.toString(j);
                    keyMap.put(key, keyPos);
                    keyMap.put(key+"CellStyle", cellIn.getCellStyle());
                } else if(cellValue.length() > 2 && cellValue.substring(0,2).equals("<%")) {
                    String key = cellValue.substring(2, cellValue.length());
                    keyMap.put("STARTCELL", Integer.toString(j));
                    keyMap.put(key, Integer.toString(k));
                    keyMap.put(key+"CellStyle", cellIn.getCellStyle());
                }
            }
        }
    }

    /**
     * 获取格式，不适于循环方法中使用，wb.createCellStyle()次数超过4000将抛异常
     * @param keyMap
     * @param key
     * @return
     */
    public static CellStyle getStyle(HashMap keyMap, String key,HSSFWorkbook wb) {
        CellStyle cellStyle = null;

        cellStyle = (CellStyle) keyMap.get(key+"CellStyle");
        //当字符超出时换行
        cellStyle.setWrapText(true);
        CellStyle newStyle = wb.createCellStyle();
        newStyle.cloneStyleFrom(cellStyle);
        return newStyle;
    }
    /**
     * Excel单元格输出
     * @param sheet
     * @param row 行
     * @param cell 列
     * @param value 值
     * @param cellStyle 样式
     */
    public static void setValue(HSSFSheet sheet, int row, int cell, Object value, CellStyle cellStyle){
        HSSFRow rowIn = sheet.getRow(row);
        if(rowIn == null) {
            rowIn = sheet.createRow(row);
        }
        HSSFCell cellIn = rowIn.getCell(cell);
        if(cellIn == null) {
            cellIn = rowIn.createCell(cell);
        }
        if(cellStyle != null) {
            //修复产生多超过4000 cellStyle 异常
            //CellStyle newStyle = wb.createCellStyle();
            //newStyle.cloneStyleFrom(cellStyle);
            cellIn.setCellStyle(cellStyle);
        }
        //对时间格式进行单独处理
        if(value==null){
            cellIn.setCellValue("");
        }else{
            if (isCellDateFormatted(cellStyle)) {
                cellIn.setCellValue((Date) value);
            } else {
                cellIn.setCellValue(new HSSFRichTextString(value.toString()));
            }
        }
    }

    /**
     * 根据表格样式判断是否为日期格式
     * @param cellStyle
     * @return
     */
    public static boolean isCellDateFormatted(CellStyle cellStyle){
        if(cellStyle==null){
            return false;
        }
        int i = cellStyle.getDataFormat();
        String f = cellStyle.getDataFormatString();

        return org.apache.poi.ss.usermodel.DateUtil.isADateFormat(i, f);
    }
    /**
     * 适用于导出的数据Excel格式样式重复性较少
     * 不适用于循环方法中使用
     * @param wbModule
     * @param sheet
     * @param pos 模板文件信息
     * @param startCell 开始的行
     * @param value 要填充的数据
     * @param cellStyle 表格样式
     */
    public static void createCell(HSSFWorkbook wbModule, HSSFSheet sheet,HashMap pos, int startCell,Object value,String cellStyle){
        int[] excelPos = getPos(pos, cellStyle);
        setValue(sheet, startCell, excelPos[0], value, getStyle(pos, cellStyle,wbModule));
    }

    public static void main(String args[]) throws IOException{
        String tempFilePath = FileUtils.class.getResource("test.xls").getPath();
        List<String> dataListCell = new ArrayList<String>();
        dataListCell.add("names");
        dataListCell.add("ages");
        dataListCell.add("sexs");
        dataListCell.add("deses");
        List<Map<String,Object>> dataList = new  ArrayList<Map<String,Object>>();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("names", "names");
        map.put("ages", 22);
        map.put("sexs", "男");
        map.put("deses", "测试");
        dataList.add(map);
        Map<String,Object> map1 = new HashMap<String, Object>();
        map1.put("names", "names1");
        map1.put("ages", 23);
        map1.put("sexs", "男");
        map1.put("deses", "测试1");
        dataList.add(map1);
        Map<String,Object> map2 = new HashMap<String, Object>();
        map2.put("names", "names2");
        map2.put("ages", 24);
        map2.put("sexs", "女");
        map2.put("deses", "测试2");
        dataList.add(map2);
        Map<String,Object> map3 = new HashMap<String, Object>();
        map3.put("names", "names3");
        map3.put("ages", 25);
        map3.put("sexs", "男");
        map3.put("deses", "测试3");
        dataList.add(map3);

        ExcelFileUtils handle = new ExcelFileUtils();
        handle.writeListData(tempFilePath, dataListCell, dataList, 0);

//        List<String> dataCell = new ArrayList<String>();
//        dataCell.add("name");
//        dataCell.add("age");
//        dataCell.add("sex");
//        dataCell.add("des");
//        Map<String,Object> dataMap = new  HashMap<String, Object>();
//        dataMap.put("name", "name");
//        dataMap.put("age", 11);
//        dataMap.put("sex", "女");
//        dataMap.put("des", "测试");
//
//        handle.writeData(tempFilePath, dataCell, dataMap, 0);

        File file = new File("d:/data.xlsx");
        OutputStream os = new FileOutputStream(file);
        //写到输出流并关闭资源
        handle.writeAndClose(tempFilePath, os);

        os.flush();
        os.close();

//        System.out.println("读取写入的数据----------------------------------%%%");
//        System.out.println("name:"+handle.getValue(tempFilePath, "name", 0, file));
//        System.out.println("age:"+handle.getValue(tempFilePath, "age", 0, file));
//        System.out.println("sex:"+handle.getValue(tempFilePath, "sex", 0, file));
//        System.out.println("des:"+handle.getValue(tempFilePath, "des", 0, file));
//        System.out.println("读取写入的列表数据----------------------------------%%%");
//        List<Map<String,Object>> list = handle.getListValue(tempFilePath, dataListCell, 0, file);
//        for(Map<String,Object> data:list){
//            for(String key:data.keySet()){
//                System.out.print(key+":"+data.get(key)+"--");
//            }
//            System.out.println("");
//        }

        handle.readClose(tempFilePath);
    }


}
