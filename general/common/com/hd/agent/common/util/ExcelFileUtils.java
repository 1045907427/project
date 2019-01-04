package com.hd.agent.common.util;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by FD007 on 2016/12/15.
 */
public class ExcelFileUtils {

    private Map<String,HashMap[]> tempFileMap  = new HashMap<String,HashMap[]>();
    private Map<String,Map<String,Cell>> cellMap = new HashMap<String,Map<String,Cell>>();
    private Map<String,FileInputStream> tempStream = new HashMap<String, FileInputStream>();
    private Map<String,HSSFWorkbook> tempWorkbook = new HashMap<String, HSSFWorkbook>();
    private Map<String, HSSFWorkbook> dataWorkbook = new HashMap<String, HSSFWorkbook>();

    /**
     * 单无格类
     * @author lin_xx
     *
     */
    class Cell{
        private int column;//列
        private int line;//行
        private CellStyle cellStyle;

        public int getColumn() {
            return column;
        }
        public void setColumn(int column) {
            this.column = column;
        }
        public int getLine() {
            return line;
        }
        public void setLine(int line) {
            this.line = line;
        }
        public CellStyle getCellStyle() {
            return cellStyle;
        }
        public void setCellStyle(CellStyle cellStyle) {
            this.cellStyle = cellStyle;
        }
    }

    /**
     * 向Excel中输入相同title的多条数据
     * @param tempFilePath excel模板文件路径
     * @param cellList 需要填充的数据（模板<!%后的字符串）
     * @param dataList 填充的数据
     * @param sheet 填充的excel sheet,从0开始
     * @throws IOException
     */
    public void writeListData(String tempFilePath,List<String> cellList,List<Map<String,Object>> dataList,int sheet) throws IOException{
        //获取模板填充格式位置等数据
        HashMap temp = getTemp(tempFilePath,sheet);
        //按模板为写入板
        HSSFWorkbook temWorkbook = getTempWorkbook(tempFilePath);
        //获取数据填充开始行
        int startCell = Integer.parseInt((String)temp.get("STARTCELL"));
        //数据填充的sheet
        HSSFSheet wsheet = temWorkbook.getSheetAt(sheet);
        //移除模板开始行数据即<!%
        wsheet.removeRow(wsheet.getRow(startCell));
        if(dataList!=null&&dataList.size()>0){
            for(Map<String,Object> map:dataList){
                for(String cell:cellList){
                    //获取对应单元格数据
                    Cell c = getCell(cell,temp,temWorkbook,tempFilePath);
                    if(null==c){
                        continue;
                    }
                    //写入数据
                    ExcelModelUtils.setValue(wsheet, startCell, c.getColumn(), map.get(cell), c.getCellStyle());
                }
                startCell++;
            }
        }

        if(null!=dataList &&dataList.size()>0){
            // LinkedHashMap<String, Object> rowMap1 = (LinkedHashMap<String, Object>) dataList.get(0);
            int i = 0;
            for(Map.Entry<String, Object> entry : dataList.get(0).entrySet()){
                wsheet.autoSizeColumn(i);
                int maxColumnWidth = wsheet.getColumnWidth(i);
                wsheet.setColumnWidth(i, maxColumnWidth+500);
                i++;
            }
        }

    }

    /**
     * 按模板向Excel中相应地方填充数据
     * @param tempFilePath excel模板文件路径
     * @param cellList 需要填充的数据（模板<%后的字符串）
     * @param dataMap 填充的数据
     * @param sheet 填充的excel sheet,从0开始
     * @throws IOException
     */
    public void writeData(String tempFilePath,List<String> cellList,Map<String,Object> dataMap,int sheet) throws IOException{
        //获取模板填充格式位置等数据
        HashMap tem = getTemp(tempFilePath,sheet);
        //按模板为写入板
        HSSFWorkbook wbModule = getTempWorkbook(tempFilePath);
        //数据填充的sheet
        HSSFSheet wsheet = wbModule.getSheetAt(sheet);
        if(dataMap!=null&&dataMap.size()>0){
            for(String cell:cellList){
                //获取对应单元格数据
                Cell c = getCell(cell,tem,wbModule,tempFilePath);
                ExcelModelUtils.setValue(wsheet, c.getLine(), c.getColumn(), dataMap.get(cell), c.getCellStyle());
            }
        }
    }

    /**
     * Excel文件读值
     * @param tempFilePath
     * @param cell
     * @param sheet
     * @return
     * @throws IOException
     */
    public Object getValue(String tempFilePath,String cell,int sheet,File excelFile) throws IOException{
        //获取模板填充格式位置等数据
        HashMap tem = getTemp(tempFilePath,sheet);
        //模板工作区
        HSSFWorkbook temWorkbook = getTempWorkbook(tempFilePath);
        //数据工作区
        HSSFWorkbook dataWorkbook = getDataWorkbook(tempFilePath, excelFile);
        //获取对应单元格数据
        Cell c = getCell(cell,tem,temWorkbook,tempFilePath);
        //数据sheet
        HSSFSheet dataSheet = dataWorkbook.getSheetAt(sheet);
        //设置自适应列宽

        return ExcelModelUtils.getCellValue(dataSheet, c.getLine(), c.getColumn());
    }

    /**
     * 读值列表值
     * @param tempFilePath
     * @param sheet
     * @return
     * @throws IOException
     */
    public List<Map<String,Object>> getListValue(String tempFilePath,List<String> cellList,int sheet,File excelFile) throws IOException{
        List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
        //获取模板填充格式位置等数据
        HashMap tem = getTemp(tempFilePath,sheet);
        //获取数据填充开始行
        int startCell = Integer.parseInt((String)tem.get("STARTCELL"));
        //将Excel文件转换为工作区间
        HSSFWorkbook dataWorkbook = getDataWorkbook(tempFilePath,excelFile) ;
        //数据sheet
        HSSFSheet dataSheet = dataWorkbook.getSheetAt(sheet);
        //文件最后一行
        int lastLine = dataSheet.getLastRowNum();

        for(int i=startCell;i<=lastLine;i++){
            dataList.add(getListLineValue(i, tempFilePath, cellList, sheet, excelFile));
        }
        return dataList;
    }

    /**
     * 读值一行列表值
     * @param tempFilePath
     * @param sheet
     * @return
     * @throws IOException
     */
    public Map<String,Object> getListLineValue(int line,String tempFilePath,List<String> cellList,int sheet,File excelFile) throws IOException{
        Map<String,Object> lineMap = new HashMap<String, Object>();
        //获取模板填充格式位置等数据
        HashMap tem = getTemp(tempFilePath,sheet);
        //按模板为写入板
        HSSFWorkbook temWorkbook = getTempWorkbook(tempFilePath);
        //将Excel文件转换为工作区间
        HSSFWorkbook dataWorkbook = getDataWorkbook(tempFilePath,excelFile) ;
        //数据sheet
        HSSFSheet dataSheet = dataWorkbook.getSheetAt(sheet);
        for(String cell:cellList){
            //获取对应单元格数据
            Cell c = getCell(cell,tem,temWorkbook,tempFilePath);
            lineMap.put(cell, ExcelModelUtils.getCellValue(dataSheet, line, c.getColumn()));
        }
        return lineMap;
    }



    /**
     * 获得模板输入流
     * @param tempFilePath
     * @return
     * @throws FileNotFoundException
     */
    private FileInputStream getFileInputStream(String tempFilePath) throws FileNotFoundException {
        if(!tempStream.containsKey(tempFilePath)){
            tempStream.put(tempFilePath, new FileInputStream(tempFilePath));
        }

        return tempStream.get(tempFilePath);
    }

    /**
     * 获得输入工作区
     * @param tempFilePath
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     */
    private HSSFWorkbook getTempWorkbook(String tempFilePath) throws FileNotFoundException, IOException {
        if(!tempWorkbook.containsKey(tempFilePath)){
            tempWorkbook.put(tempFilePath, new HSSFWorkbook(getFileInputStream(tempFilePath)));
        }
        return tempWorkbook.get(tempFilePath);
    }

    /**
     * 获取对应单元格样式等数据数据
     * @param cell
     * @param tem
     * @param wbModule
     * @param tempFilePath
     * @return
     */
    private Cell getCell(String cell, HashMap tem, HSSFWorkbook wbModule, String tempFilePath) {
        if(!cellMap.get(tempFilePath).containsKey(cell)){
            Cell c = new Cell();

            int[] pos = ExcelModelUtils.getPos(tem, cell);
            if(pos.length==0){
                return null;
            }
            if(pos.length>1){
                c.setLine(pos[1]);
            }
            c.setColumn(pos[0]);
            c.setCellStyle((ExcelModelUtils.getStyle(tem, cell, wbModule)));
            cellMap.get(tempFilePath).put(cell, c);
        }
        return cellMap.get(tempFilePath).get(cell);
    }

    /**
     * 获取模板数据
     * @param tempFilePath 模板文件路径
     * @param sheet
     * @return
     * @throws IOException
     */
    private HashMap getTemp(String tempFilePath, int sheet) throws IOException {
        if(!tempFileMap.containsKey(tempFilePath)){
            tempFileMap.put(tempFilePath, ExcelModelUtils.getTemplateFile(tempFilePath));
            cellMap.put(tempFilePath, new HashMap<String,Cell>());
        }
        return tempFileMap.get(tempFilePath)[sheet];
    }

    /**
     * 资源关闭
     * @param tempFilePath 模板文件路径
     * @param os 输出流
     * @throws IOException
     * @throws FileNotFoundException
     */
    public void writeAndClose(String tempFilePath,OutputStream os) throws FileNotFoundException, IOException{
        if(getTempWorkbook(tempFilePath)!=null){
            getTempWorkbook(tempFilePath).write(os);
            tempWorkbook.remove(tempFilePath);
        }
        if(getFileInputStream(tempFilePath)!=null){
            getFileInputStream(tempFilePath).close();
            tempStream.remove(tempFilePath);
        }
    }

    /**
     * 获得读取数据工作间
     * @param tempFilePath
     * @param excelFile
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     */
    private HSSFWorkbook getDataWorkbook(String tempFilePath, File excelFile) throws FileNotFoundException, IOException {
        if(!dataWorkbook.containsKey(tempFilePath)){
            dataWorkbook.put(tempFilePath, new HSSFWorkbook(new FileInputStream(excelFile)));
        }
        return dataWorkbook.get(tempFilePath);
    }

    /**
     * 读取数据后关闭
     * @param tempFilePath
     */
    public void readClose(String tempFilePath){
        dataWorkbook.remove(tempFilePath);
    }

}
