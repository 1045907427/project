/**
 * @(#)ExcelUtils.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-3-11 zhengziyong 创建版本
 */
package com.hd.agent.common.util;

import com.hd.agent.common.model.AttachFile;
import com.hd.agent.common.service.IAttachFileService;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Excel操作类
 *
 * @author zhengziyong
 */
public class ExcelUtils{
	public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();
    public final static ScriptEngineManager sem = new ScriptEngineManager();
    public final static ScriptEngine engine = sem.getEngineByName("javascript");
    //public final static int CELL_HR_NUM = 19 ;
	static{    
        getAllFileType();  //初始化文件类型信息    
    }
	
	/**
     * 常见文件头信息
     * @author panxiaoxiao 
     * @date Nov 3, 2014
     */
    private static void getAllFileType()    
    {    
//        FILE_TYPE_MAP.put("jpg", "FFD8FF"); //JPEG (jpg)    
//        FILE_TYPE_MAP.put("png", "89504E47");  //PNG (png)    
//        FILE_TYPE_MAP.put("gif", "47494638");  //GIF (gif)    
//        FILE_TYPE_MAP.put("tif", "49492A00");  //TIFF (tif)    
//        FILE_TYPE_MAP.put("bmp", "424D"); //Windows Bitmap (bmp)    
//        FILE_TYPE_MAP.put("dwg", "41433130"); //CAD (dwg)    
//        FILE_TYPE_MAP.put("html", "68746D6C3E");  //HTML (html)    
//        FILE_TYPE_MAP.put("rtf", "7B5C727466");  //Rich Text Format (rtf)    
//        FILE_TYPE_MAP.put("xml", "3C3F786D6C");    
//        FILE_TYPE_MAP.put("zip", "504B0304");    
//        FILE_TYPE_MAP.put("rar", "52617221");    
//        FILE_TYPE_MAP.put("psd", "38425053");  //Photoshop (psd)    
//        FILE_TYPE_MAP.put("eml", "44656C69766572792D646174653A");  //Email [thorough only] (eml)    
//        FILE_TYPE_MAP.put("dbx", "CFAD12FEC5FD746F");  //Outlook Express (dbx)    
//        FILE_TYPE_MAP.put("pst", "2142444E");  //Outlook (pst)
    	  FILE_TYPE_MAP.put("xls", "D0CF11E0");  //MS Excel 2003
    	  FILE_TYPE_MAP.put("xlsx", "504B0304");  //MS Excel 2007 无法判断 pptx zip xlsx docx
          FILE_TYPE_MAP.put("csv", "B6A9BBF5B5A5C3F7C"); //CSV
//        FILE_TYPE_MAP.put("doc", "D0CF11E0");  //MS Word 注意：word 和 excel的文件头一样    
//        FILE_TYPE_MAP.put("mdb", "5374616E64617264204A");  //MS Access (mdb)    
//        FILE_TYPE_MAP.put("wpd", "FF575043"); //WordPerfect (wpd)     
//        FILE_TYPE_MAP.put("eps", "252150532D41646F6265");    
//        FILE_TYPE_MAP.put("ps", "252150532D41646F6265");    
//        FILE_TYPE_MAP.put("pdf", "255044462D312E");  //Adobe Acrobat (pdf)    
//        FILE_TYPE_MAP.put("qdf", "AC9EBD8F");  //Quicken (qdf)    
//        FILE_TYPE_MAP.put("pwl", "E3828596");  //Windows Password (pwl)    
//        FILE_TYPE_MAP.put("wav", "57415645");  //Wave (wav)    
//        FILE_TYPE_MAP.put("avi", "41564920");    
//        FILE_TYPE_MAP.put("ram", "2E7261FD");  //Real Audio (ram)    
//        FILE_TYPE_MAP.put("rm", "2E524D46");  //Real Media (rm)    
//        FILE_TYPE_MAP.put("mpg", "000001BA");  //    
//        FILE_TYPE_MAP.put("mov", "6D6F6F76");  //Quicktime (mov)    
//        FILE_TYPE_MAP.put("asf", "3026B2758E66CF11"); //Windows Media (asf)    
//        FILE_TYPE_MAP.put("mid", "4D546864");  //MIDI (mid)    
    }    
    
    /**
     * 获取文件类型,包括图片,若格式不是已配置的,则返回null
     * @param file
     * @return
     * @author panxiaoxiao 
     * @date Nov 3, 2014
     */
    public final static String getFileByFile(File file)    
    {    
        String filetype = null;    
        byte[] b = new byte[50];
        try{    
            InputStream is = new FileInputStream(file);
            is.read(b, 0, b.length);
            filetype = getFileTypeByStream(b);
            is.close();    
        }catch(FileNotFoundException e){    
            e.printStackTrace();    
        }catch(IOException e){    
            e.printStackTrace();    
        }
        return filetype;    
    }    
        
    /**
     * 根据读取的数据获取文件类型
     * @param b
     * @return
     * @author panxiaoxiao 
     * @date Nov 3, 2014
     */    
    public final static String getFileTypeByStream(byte[] b)    
    {    
        String filetypeHex = String.valueOf(getFileHexString(b));    
        Iterator<Entry<String, String>> entryiterator = FILE_TYPE_MAP.entrySet().iterator();    
        while (entryiterator.hasNext()){    
            Entry<String,String> entry =  entryiterator.next();    
            String fileTypeHexValue = entry.getValue();    
            if (filetypeHex.toUpperCase().startsWith(fileTypeHexValue)){
                return entry.getKey();    
            }
        }
        return null;    
    }    
        
   /**
    * 获取文件十六进制数字字符串
    * @param src
    * @return
    * @author panxiaoxiao 
    * @date Nov 3, 2014
    */  
    private final static String getFileHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder();    
        if (src == null || src.length <= 0){
            return null;    
        }    
        for (int i = 0; i < src.length; i++){
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);    
            if (hv.length() < 2){    
                stringBuilder.append(0);    
            }    
            stringBuilder.append(hv);    
        }    
        return stringBuilder.toString();    
    }
	
	/**
	 * 获取导入Excel文件的第一行内容，导入时第一行必须是字段描述的标题
	 * @param file
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 30, 2013
	 */
	public static List<String> importExcelFirstRow(File file) throws Exception{
		List<String> paramList = new ArrayList<String>();
		InputStream is = new FileInputStream(file);
		Workbook hssfWorkbook = null; 
		String type = getFileByFile(file);
        if(StringUtils.isEmpty(type)){
            paramList = null;
        }else {
            if ("xls".equals(type)) {
                hssfWorkbook = new HSSFWorkbook(is);

            } else if ("xlsx".equals(type)) {
                hssfWorkbook = new XSSFWorkbook(file.getPath());
            }
            Sheet hssfSheet = hssfWorkbook.getSheetAt(0);
            if (hssfSheet != null) {
                Row firstRow = hssfSheet.getRow(0);
                if (firstRow != null) {
                    for (int firstRowCellNum = 0; firstRowCellNum < firstRow.getLastCellNum(); firstRowCellNum++) {
                        Cell hssfCell = firstRow.getCell(firstRowCellNum);
                        if (hssfCell == null) {
                            continue;
                        }
                        if (null != getValue(hssfCell)) {
                            paramList.add(getValue(hssfCell).toString());
                        } else {
                            paramList.add("null");
                        }
                    }
                }
            }
        }
		return paramList;
	}

    /**
     * 获取导入Excel文件指定行的内容
     * @param file 文件
     * @param rownum 指定行
     * @param numSheet 指定工作表
     * @return
     * @throws Exception
     * @author lin_xx
     * @date Sep 30, 2015
     */
    public static List<String> importRowInfoBySheet(File file , int rownum , int numSheet) throws Exception{
        List<String> paramList = new ArrayList<String>();
        InputStream is = new FileInputStream(file);
        Workbook hssfWorkbook = null;
        String type = getFileByFile(file);
        if("xls".equals(type)){
            hssfWorkbook = new HSSFWorkbook(is);
        }else if("xlsx".equals(type)){
            hssfWorkbook = new XSSFWorkbook(file.getPath());
        }else{
            hssfWorkbook = new XSSFWorkbook(file.getPath()); //大宁模板 type为null的情况
        }
        Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
        Row firstRow = hssfSheet.getRow(rownum);
        if(null != firstRow){
            for(int firstRowCellNum = 0; firstRowCellNum < firstRow.getLastCellNum(); firstRowCellNum++){
                Cell hssfCell = firstRow.getCell(firstRowCellNum);
                if(hssfCell == null){
                    continue;
                }
                if(null!=getValue(hssfCell)){
                    paramList.add(getValue(hssfCell).toString());
                }else{
                    paramList.add("null");
                }
            }
        }else{
        }

        return paramList;
    }

    /**
     * 获取导入Excel文件指定行的内容(婴知岛订单 根据条件获取对应客户信息)
     * @param file
     * @return
     * @throws Exception
     * @author lin_xx
     * @date Sep 30, 2015
     */
    public static  List<String> importMoreRowInfoByIndex(File file , int index,String ctype) throws Exception{
        List<String> customerList = new ArrayList<String>();
        List<String> paramList = new ArrayList<String>();
        InputStream is = new FileInputStream(file);
        Workbook hssfWorkbook = null;
        String type = getFileByFile(file);
        if("xls".equals(type)){
            hssfWorkbook = new HSSFWorkbook(is);
        }else if("xlsx".equals(type)){
            hssfWorkbook = new XSSFWorkbook(file.getPath());
        }
        for(int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++){
            Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if(hssfSheet == null){
                continue;
            }
            Row firstRow = hssfSheet.getRow(index);
            if(firstRow == null){
                continue;
            }

            for(int firstRowCellNum = 0; firstRowCellNum < firstRow.getLastCellNum(); firstRowCellNum++){
                Cell hssfCell = firstRow.getCell(firstRowCellNum);
                if(hssfCell == null){
                    continue;
                }
                if(null!=getValue(hssfCell)){
                    paramList.add(getValue(hssfCell).toString());
                }else{
                    paramList.add("null");
                }
            }
        }
       for(int i = 0 ;i<paramList.size();i++){//获取店号
           if(paramList.get(i).contains("要货单位")){
               String customer = paramList.get(i+1);
               String reg = "";
               if("2".equals(ctype)){
                   customer = customer.substring(1,customer.indexOf("]"));
                   customerList.add(customer);
               }else{
                   customer = customer.substring(customer.indexOf("]")+1,customer.length());
                   customerList.add(customer);
               }
           }
//           if(paramList.get(i).contains("店铺")){
//               String customer = paramList.get(i+1);
//               String reg = "";
//               Pattern p = Pattern.compile(reg);
//               Matcher matcher = p.matcher(customer);
//               customer = matcher.replaceAll("").trim();
//               customerList.add(customer);
//           }
       }
        return customerList;
    }


    /**
	 * 获取多表的第一行字段
	 * @param file
	 * @param tnstr
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 8, 2014
	 */
	public static Map<String,List<String>> importExcelMoreFirstRow(File file,String tnstr)throws Exception{
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		InputStream is = new FileInputStream(file);
		Workbook hssfWorkbook = null;
		String type = getFileByFile(file);
		if("xls".equals(type)){
			hssfWorkbook = new HSSFWorkbook(is);
		}else if("xlsx".equals(type)){
			hssfWorkbook = new XSSFWorkbook(file.getPath());
		}
		//HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		//读取表名tnkey和表描述名tnval
		JSONObject json = JSONObject.fromObject(tnstr);
		for(int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++){
			Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if(hssfSheet == null){
				continue;
			}
			String tndescribe = hssfSheet.getSheetName();//表描述名
			if(json.containsKey(tndescribe)){
				String tnkey = json.getString(tndescribe);//表名
				List<String> paramList = new ArrayList<String>();
	    		Row firstRow = hssfSheet.getRow(0);
				if(firstRow == null){
					continue;
				}
				//获取excel每个单元格数据
				for(int firstRowCellNum = 0; firstRowCellNum < firstRow.getLastCellNum(); firstRowCellNum++){
					Cell hssfCell = firstRow.getCell(firstRowCellNum);
					if(hssfCell == null){
						continue;
					}
					if(null!=getValue(hssfCell)){
						paramList.add(getValue(hssfCell).toString());
					}else{
						paramList.add("null");
					}
				}
				map.put(tnkey+"_First", paramList);
			}
    	}
		return map;
	}
	
	/**
	 * 将多个excel中多个sheet表数据转化字段存入map中
	 * @param file
	 * @param paramListMap
	 * @param tnstr
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 8, 2014
	 */
	public static Map<String,List<Map<String,Object>>> importExcelMore(File file, Map<String,List<String>> paramListMap,String tnstr) throws Exception{
		Map<String,List<Map<String, Object>>> map = new HashMap<String,List<Map<String, Object>>>();
		InputStream is = new FileInputStream(file);
		Workbook hssfWorkbook = null; 
		String type = getFileByFile(file);
		if("xls".equals(type)){
			hssfWorkbook = new HSSFWorkbook(is);
		}else if("xlsx".equals(type)){
			hssfWorkbook = new XSSFWorkbook(file.getPath());
		}
		JSONObject json = JSONObject.fromObject(tnstr);
		for(int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++){
			Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if(hssfSheet == null){
				continue;
			}
			String sheetName = hssfSheet.getSheetName();//excel sheet名称
			if(json.containsKey(sheetName)){
				String tnkey = json.getString(sheetName);//表名
				List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
				for(int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++){
					Map<String, Object> map2 = new HashMap<String, Object>();
					Row hssfRow = hssfSheet.getRow(rowNum);
					if(hssfRow == null){
						continue;
					}
					for(int cellNum = 0; cellNum < hssfRow.getLastCellNum(); cellNum++){
						Cell hssfCell = hssfRow.getCell(cellNum);
						if(hssfCell == null){
							continue;
						}
						List<String> paramList = paramListMap.get(tnkey+"_Field");
						if(paramList.size()>cellNum && !"null".equals(paramList.get(cellNum))){
							//当列为id列时，读取的数字不取小数位
							if(null!=paramList.get(cellNum)&&paramList.get(cellNum).equals("id")){
								Object o = getValue(hssfCell);
								if(o instanceof String){
									map2.put(paramList.get(cellNum),o);
								}else{
									BigDecimal dou = new BigDecimal(o.toString());
									Integer integer = dou.intValue();
									map2.put(paramList.get(cellNum),integer+"");
								}
							}else{
								if(null != getValue(hssfCell) && !"".equals(getValue(hssfCell))){
									map2.put(paramList.get(cellNum), getValue(hssfCell));
								}
							}
						}
					}
					if(!map2.isEmpty()){
						result.add(map2);
					}
				}
				map.put(tnkey, result);
			}
		}
		return map;
	}
	
	/**
	 * 导入数据--从第一个sheet表中获取数据
	 * @param file
	 * @param paramList
	 * @throws Exception
	 * @author zhengziyong 
	 * @date 2013-3-12
	 */
	public static List<Map<String, Object>> importExcel(File file, List<String> paramList) throws Exception{
		InputStream is = new FileInputStream(file);
		Workbook hssfWorkbook = null; 
		String type = getFileByFile(file);
        if("xls".equals(type)){
			hssfWorkbook = new HSSFWorkbook(is);
		}else if("xlsx".equals(type)){
			hssfWorkbook = new XSSFWorkbook(file.getPath());
		}
		//HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Sheet hssfSheet = hssfWorkbook.getSheetAt(0);
        if(hssfSheet != null){
            for(int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++){
                Map<String, Object> map = new HashMap<String, Object>();
                Row hssfRow = hssfSheet.getRow(rowNum);
                if(hssfRow == null){
                    continue;
                }
                for(int cellNum = 0; cellNum < hssfRow.getLastCellNum(); cellNum++){
                    Cell hssfCell = hssfRow.getCell(cellNum);
                    if(hssfCell == null || getValue(hssfCell) == null){
                        continue;
                    }
                    if(paramList.size()>cellNum && !"null".equals(paramList.get(cellNum))){
                        //当列为id列时，读取的数字不取小数位
                        if(null!=paramList.get(cellNum)&&paramList.get(cellNum).equals("id")){
                            Object o = getValue(hssfCell);
                            if(o instanceof String){
                                map.put(paramList.get(cellNum),o);
                            }else{
                                BigDecimal dou = new BigDecimal(o.toString());
                                Integer integer = dou.intValue();
                                map.put(paramList.get(cellNum),integer+"");
                            }
                        }else{
                            if(null != getValue(hssfCell) && !"".equals(getValue(hssfCell))){
                                map.put(paramList.get(cellNum), getValue(hssfCell));
                            }
                        }
                    }
                }
                if(!map.isEmpty()){
                    result.add(map);
                }
            }
        }
		return result;
	}

    /**
     * 导入数据--从多个sheet表中获取数据
     * @param file
     * @param paramList
     * @throws Exception
     * @author zhengziyong
     * @date 2013-3-12
     */
    public static List<Map<String, Object>> importExcelMoreSheet(File file, List<String> paramList) throws Exception{
        InputStream is = new FileInputStream(file);
        Workbook hssfWorkbook = null;
        String type = getFileByFile(file);
        if("xls".equals(type)){
            hssfWorkbook = new HSSFWorkbook(is);
        }else if("xlsx".equals(type)){
            hssfWorkbook = new XSSFWorkbook(file.getPath());
        }
        //HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        for(int isheet=0;isheet<hssfWorkbook.getNumberOfSheets();isheet++) {
            Sheet hssfSheet = hssfWorkbook.getSheetAt(isheet);
            if (hssfSheet != null) {
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    Row hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow == null) {
                        continue;
                    }
                    for (int cellNum = 0; cellNum < hssfRow.getLastCellNum(); cellNum++) {
                        Cell hssfCell = hssfRow.getCell(cellNum);
                        if (hssfCell == null || getValue(hssfCell) == null) {
                            continue;
                        }
                        if (paramList.size() > cellNum && !"null".equals(paramList.get(cellNum))) {
                            //当列为id列时，读取的数字不取小数位
                            if (null != paramList.get(cellNum) && paramList.get(cellNum).equals("id")) {
                                Object o = getValue(hssfCell);
                                if (o instanceof String) {
                                    map.put(paramList.get(cellNum), o);
                                } else {
                                    BigDecimal dou = new BigDecimal(o.toString());
                                    Integer integer = dou.intValue();
                                    map.put(paramList.get(cellNum), integer + "");
                                }
                            } else {
                                if (null != getValue(hssfCell) && !"".equals(getValue(hssfCell))) {
                                    map.put(paramList.get(cellNum), getValue(hssfCell));
                                }
                            }
                        }
                    }
                    if (!map.isEmpty()) {
                        result.add(map);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 获取文件在特定区域内某列的值
     * @param file
     * @param  sheetnum 第几张工作表
     * @param beginRow 开始行
     * @param endRow 结束行
     * @param column 获取的列
     * @return
     * @throws Exception
     */
    public static Map<Integer, String> importExcelByColumn(File file,int sheetnum,int beginRow,int endRow,int column) throws Exception{
        Map<Integer, String> map = new HashMap<Integer, String>();
        InputStream is = new FileInputStream(file);
        Workbook hssfWorkbook = null;
        String type = ExcelUtils.getFileByFile(file);
        if("xls".equals(type)){
            hssfWorkbook = new HSSFWorkbook(is);
        }else if("xlsx".equals(type)){
            hssfWorkbook = new XSSFWorkbook(file.getPath());
        }else{
            hssfWorkbook = new XSSFWorkbook(file.getPath()); //大宁模板 type为null的情况
        }
        Sheet hssfSheet = hssfWorkbook.getSheetAt(sheetnum);
        if(endRow == 0){
            endRow = 10000;//默认读取10000条数据
        }
        for(int rowNum = beginRow; rowNum <= endRow; rowNum++) {

            Row hssfRow = hssfSheet.getRow(rowNum);
            if (hssfRow == null) {
                continue;
            }
            Cell hssfCell = hssfRow.getCell(column);
            if(null != hssfCell){
                Object cellValue = getValue(hssfCell);
                if(cellValue != null){
                    String cellTrim = cellValue.toString().replaceAll("[ ]", " ").trim();
                    if(cellValue != null){
                        map.put(rowNum,cellTrim);
                    }else{
                        map.put(rowNum,"");
                    }
                }
            }
        }
        return  map;
    }

    /**
     * 根据条件获取标识商品（条形码 店内码 助记符 商品编码）所在列的值
     * @param file
     * @param  sheetnum 第几张工作表
     * @param beginRow 开始行
     * @param endRow 结束行
     * @param column 获取的列
     * @return
     * @throws Exception
     */
    public static Map<Integer, String> importExcelByGoodsGolumn(File file,int sheetnum,int beginRow,int endRow,int column) throws Exception{
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        InputStream is = new FileInputStream(file);
        Workbook hssfWorkbook = null;
        String type = ExcelUtils.getFileByFile(file);
        if("xls".equals(type)){
            hssfWorkbook = new HSSFWorkbook(is);
        }else if("xlsx".equals(type)){
            hssfWorkbook = new XSSFWorkbook(file.getPath());
        }else{
            hssfWorkbook = new XSSFWorkbook(file.getPath()); //大宁模板 type为null的情况
        }
        Sheet hssfSheet = hssfWorkbook.getSheetAt(sheetnum);
        if(endRow == 0){
            endRow = 10000;//默认读取10000条数据
        }
        for(int rowNum = beginRow; rowNum <= endRow; rowNum++) {

            Row hssfRow = hssfSheet.getRow(rowNum);
            if (hssfRow == null) {
                continue;
            }
            Cell hssfCell = hssfRow.getCell(column);
            if(null != hssfCell){
                Object cellValue = getValue(hssfCell);
                if(cellValue != null){
                    String cellTrim = cellValue.toString().replaceAll("[ ]", " ").trim();
                    if(cellValue != null){
                        map.put(rowNum,cellTrim);
                    }else{
                        map.put(rowNum,"null");
                    }
                }else{
                    map.put(rowNum,"null");
                }
            }
        }
        return  map;
    }

    /**
     * 根据条件获取标识商品（条形码 店内码 助记符 商品编码）所在列的值
     * @param file
     * @param beginRow 开始行
     * @param column 获取的列
     * @return
     * @throws Exception
     */
    public static Map<String, String> importExcelByColumn(File file,int beginRow,int column) throws Exception{
        Map<String, String> map = new LinkedHashMap<String, String>();
        InputStream is = new FileInputStream(file);
        Workbook hssfWorkbook = null;
        String type = ExcelUtils.getFileByFile(file);
        if("xls".equals(type)){
            hssfWorkbook = new HSSFWorkbook(is);
        }else if("xlsx".equals(type)){
            hssfWorkbook = new XSSFWorkbook(file.getPath());
        }else{
            hssfWorkbook = new XSSFWorkbook(file.getPath()); //大宁模板 type为null的情况
        }
        Sheet hssfSheet = hssfWorkbook.getSheetAt(0);
        int endRow = 10000;//默认读取10000条数据
        for(int rowNum = beginRow; rowNum <= endRow; rowNum++) {

            Row hssfRow = hssfSheet.getRow(rowNum);
            if (hssfRow == null) {
                continue;
            }
            Cell hssfCell = hssfRow.getCell(column);
            if(null != hssfCell){
                Object cellValue = getValue(hssfCell);
                if(cellValue != null){
                    String cellTrim = cellValue.toString().replaceAll("[ ]", " ").trim();
                    if(cellValue != null){
                        map.put(rowNum+"",cellTrim);
                    }
                }
            }
        }
        return  map;
    }

    /**
     * 获取excel单元格位置的列值
     * @param cellPostion
     * @return
     * @throws Exception
     */
    public static int chagneCellColtoNumber(String cellPostion) throws  Exception  {
        int number = 0 ;
        String Column = cellPostion.replaceAll("\\d+", "");
        if(Column.length() == 1){
            number = (int)Column.charAt(0);
            number = number - 64 ; //根据字母的Unicode编码和读取时的顺序 获取客户编码位置对应的列位
        }else{
            byte[] word = Column.getBytes();
            if(word.length == 1){
                number = word[0] - 64;
            }else if(word.length == 2){
                number = 26*(word[0] -64) + word[1] - 64;
            }
//            int leng = word.length - 1;
//            number = leng * 26;
//            byte w = word[leng];
//            number = number + w - 64;

        }
        return number - 1 ;
    }


    /**
     * 导入数据--从一个sheet表中获取从固定行开始的数据
     * @param file
     * @param paramList
     * @throws Exception
     * @author lin_xx
     * @date 2015-9-9
     */
    public static List<Map<String, Object>> importExcel1(File file, List<String> paramList,int rowindex) throws Exception{
        InputStream is = new FileInputStream(file);
        Workbook hssfWorkbook = null;
        String type = getFileByFile(file);
        if("xls".equals(type)){
            hssfWorkbook = new HSSFWorkbook(is);
        }else if("xlsx".equals(type)){
            hssfWorkbook = new XSSFWorkbook(file.getPath());
        }
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        for(int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++){
            Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if(hssfSheet == null){
                continue;
            }
            // Sheet hssfSheet = hssfWorkbook.getSheetAt(0);
            for(int rowNum = rowindex; rowNum <= hssfSheet.getLastRowNum(); rowNum++){
                Map<String, Object> map = new HashMap<String, Object>();
                Row hssfRow = hssfSheet.getRow(rowNum);
                if(hssfRow == null){
                    continue;
                }
                for(int cellNum = 0; cellNum < hssfRow.getLastCellNum(); cellNum++){
                    Cell hssfCell = hssfRow.getCell(cellNum);
                    if(hssfCell == null || getValue(hssfCell) == null){
                        continue;
                    }
                    if(paramList.size()>cellNum ){
                        //当列为id列时，读取的数字不取小数位
                        if(null!=paramList.get(cellNum)&&paramList.get(cellNum).equals("id")){
                            Object o = getValue(hssfCell);
                            if(o instanceof String){
                                map.put(paramList.get(cellNum),o);
                            }else{
                                BigDecimal dou = new BigDecimal(o.toString());
                                Integer integer = dou.intValue();
                                map.put(paramList.get(cellNum),integer+"");
                            }
                        }
                        else{
                            if(null != getValue(hssfCell) && !"".equals(getValue(hssfCell))){
                                map.put(paramList.get(cellNum), getValue(hssfCell));
                            }
                        }
                    }
                }
                if(!map.isEmpty()){
                    result.add(map);
                }
            }
        }
        return result;
    }

    /**
     * 导入数据--从excel指定sheet表中获取固定行的数据
     * @param file
     * @param paramList
     * @throws Exception
     * @author lin_xx
     * @date 2015-10-7
     */
    public static List<Map<String, Object>> importExcelBySheet(File file, List<String> paramList,int rowindex,int sheetnum) throws Exception{
        InputStream is = new FileInputStream(file);
        Workbook hssfWorkbook = null;
        String type = getFileByFile(file);
        if("xls".equals(type)){
            hssfWorkbook = new HSSFWorkbook(is);
        }else if("xlsx".equals(type)){
            hssfWorkbook = new XSSFWorkbook(file.getPath());
        }else{
            hssfWorkbook = new XSSFWorkbook(file.getPath()); //大宁模板 type为null的情况
        }
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        //for(int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++){
            Sheet hssfSheet = hssfWorkbook.getSheetAt(sheetnum);
            for(int rowNum = rowindex; rowNum <= hssfSheet.getLastRowNum(); rowNum++){
                Map<String, Object> map = new HashMap<String, Object>();
                Row hssfRow = hssfSheet.getRow(rowNum);
                if(hssfRow == null){
                    continue;
                }
                for(int cellNum = 0; cellNum < hssfRow.getLastCellNum(); cellNum++){
                    Cell hssfCell = hssfRow.getCell(cellNum);
                    if(hssfCell == null || getValue(hssfCell) == null){
                        continue;
                    }
                    if(paramList.size()>cellNum ){
                        //当列为id列时，读取的数字不取小数位
                        if(null!=paramList.get(cellNum)&&paramList.get(cellNum).equals("id")){
                            Object o = getValue(hssfCell);
                            if(o instanceof String){
                                map.put(paramList.get(cellNum),o);
                            }else{
                                BigDecimal dou = new BigDecimal(o.toString());
                                Integer integer = dou.intValue();
                                map.put(paramList.get(cellNum),integer+"");
                            }
                        }else{
                            if(null != getValue(hssfCell) && !"".equals(getValue(hssfCell))){
                                if("null".equals(paramList.get(cellNum))){//美思伯乐订单格式存在空格问题，这里进行判断筛选
                                    map.put(paramList.get(cellNum+1), getValue(hssfCell));
                                }else{
                                    map.put(paramList.get(cellNum), getValue(hssfCell));
                                }
                            }
                        }
                    }
                }
                if(!map.isEmpty() && map.size()>= 1){
                    result.add(map);
                }
            }
        //}
        return result;
    }

    /**
     * 导入数据--天虹excel指定sheet表中获取固定行的数据
     * @param file
     * @param paramList
     * @throws Exception
     * @author lin_xx
     * @date 2015-10-21
     */
    public static List<Map<String, Object>> importTHExcelBySheet(File file, List<String> paramList,int rowindex,int sheetnum) throws Exception{

        InputStream is = new FileInputStream(file);
        Workbook hssfWorkbook = null;
        String type = getFileByFile(file);
        if("xls".equals(type)){
            hssfWorkbook = new HSSFWorkbook(is);
        }else if("xlsx".equals(type)){
            hssfWorkbook = new XSSFWorkbook(file.getPath());
        }
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Sheet hssfSheet = hssfWorkbook.getSheetAt(sheetnum);
        for(int rowNum = rowindex; rowNum <= hssfSheet.getLastRowNum(); rowNum++){
            Map<String, Object> map = new HashMap<String, Object>();
            Row hssfRow = hssfSheet.getRow(rowNum);
            if(hssfRow == null){
                continue;
            }
            for(int cellNum = 0; cellNum < hssfRow.getLastCellNum(); cellNum++){
                Cell hssfCell = hssfRow.getCell(cellNum);
                if(hssfCell == null || getValue(hssfCell) == null){
                    continue;
                }
                if(paramList.size()>cellNum ){
                    //当列为id列时，读取的数字不取小数位
                    if(null!=paramList.get(cellNum)&&paramList.get(cellNum).equals("id")){
                        Object o = getValue(hssfCell);
                        if(o instanceof String){
                            map.put(paramList.get(cellNum),o);
                        }else{
                            BigDecimal dou = new BigDecimal(o.toString());
                            Integer integer = dou.intValue();
                            map.put(paramList.get(cellNum),integer+"");
                        }
                    }else{
                        if(null != getValue(hssfCell) && !"".equals(getValue(hssfCell))){
                            if("null".equals(paramList.get(cellNum))){
                                map.put(paramList.get(cellNum+1), getValue(hssfCell));
                            }else{
                                map.put(paramList.get(cellNum), getValue(hssfCell));
                            }
                        }
                    }
                }
            }
            if(map.size() == 8){//天虹订单第二种格式的首行
                List<String> copyParam = paramList;
                paramList.removeAll(copyParam);
                paramList.add("");
                paramList.add("库区");paramList.add("null");paramList.add("null");paramList.add("null");paramList.add("null");
                paramList.add("商品条码");paramList.add("null");
                paramList.add("商品名称");paramList.add("null");paramList.add("null");paramList.add("null");paramList.add("null");
                paramList.add("规格");paramList.add("null");
                paramList.add("单位");paramList.add("null");
                paramList.add("箱数");paramList.add("null");
                paramList.add("箱装数");paramList.add("null");
                paramList.add("订货数量");paramList.add("null");paramList.add("null");paramList.add("null");
                paramList.add("入库量");paramList.add("null");
                paramList.add("不含税进价");
                paramList.add("要货库区");paramList.add("null");paramList.add("null");
                --rowNum ;
            }
            if(!map.isEmpty() && map.size()>8){
                result.add(map);
            }
        }
        return result;
    }

    /**
     * 获取Excel指定工作表指定单元格的值
     * @throws
     * @author lin_xx
     * @date 2017/11/15
     */
    public static String getFileValueByParam(File file,int numSheet,int rownum,int colnum) throws Exception{
        String value = "";
        InputStream is = new FileInputStream(file);
        Workbook hssfWorkbook = null;
        String type = getFileByFile(file);
        if("xls".equals(type)){
            hssfWorkbook = new HSSFWorkbook(is);
        }else if("xlsx".equals(type)){
            hssfWorkbook = new XSSFWorkbook(file.getPath());
        }else{
            try{
                //大宁模板 type为null的情况
                hssfWorkbook = new XSSFWorkbook(file.getPath());
            }catch (Exception e){
            }
        }
        Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
        if(null != hssfSheet){
            Row row = hssfSheet.getRow(rownum-1);
            if(null != row){
                Cell cell = row.getCell(colnum);
                if(null != cell){
                    value = getValue(cell).toString();
                }
            }
        }
        return value;
    }


    /**
     * 固定格式的单元格合并Execl 首行字段
     * @param file
     * @param beginRow 固定行
     * @throws Exception
     * @author lin_xx
     * @date 2015-9-17
     */
    public static List<String> importFirstRowByIndex(File file,int beginRow) throws Exception{
        List<String> paramList = new ArrayList<String>();

        InputStream is = new FileInputStream(file);
        Workbook hssfWorkbook = null;
        String type = getFileByFile(file);
        if("xls".equals(type)){
            hssfWorkbook = new HSSFWorkbook(is);
        }else if("xlsx".equals(type)){
            hssfWorkbook = new XSSFWorkbook(file.getPath());
        }else{
            //大宁模板 type为null的情况
            hssfWorkbook = new XSSFWorkbook(file.getPath());
        }
        for(int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++){
            Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if(hssfSheet == null){
                continue;
            }
            Row firstRow = hssfSheet.getRow(beginRow);
            if(firstRow == null){
                continue;
            }
            paramList.add("");
            for(int firstRowCellNum = 0; firstRowCellNum < firstRow.getLastCellNum(); firstRowCellNum++){
                Cell hssfCell = firstRow.getCell(firstRowCellNum);
                if(hssfCell == null){
                    continue;
                }
                if(null!=getValue(hssfCell)){
                    String cellValue = getValue(hssfCell).toString();
                    cellValue = cellValue.replace(" ","");
                    paramList.add(cellValue);
                }else{
                    paramList.add("null");
                }
            }
            break;
        }

        return paramList ;
    }

    /**
     * 固定格式的单元格合并Execl 首行字段
     * @param file
     * @param beginRow 固定行
     * @throws Exception
     * @author lin_xx
     * @date 2015-9-17
     */
    public static List<String> importFirstRowByIndex(File file,int beginRow, List<String> paramList) throws Exception{

        InputStream is = new FileInputStream(file);
        Workbook hssfWorkbook = null;
        String type = getFileByFile(file);
        if("xls".equals(type)){
            hssfWorkbook = new HSSFWorkbook(is);
        }else if("xlsx".equals(type)){
            hssfWorkbook = new XSSFWorkbook(file.getPath());
        }else{
            //大宁模板 type为null的情况
            hssfWorkbook = new XSSFWorkbook(file.getPath());
        }
        for(int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++){
            Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if(hssfSheet == null){
                continue;
            }
            Row firstRow = hssfSheet.getRow(beginRow);
            if(firstRow == null){
                continue;
            }
            for(int firstRowCellNum = 0; firstRowCellNum < firstRow.getLastCellNum(); firstRowCellNum++){
                Cell hssfCell = firstRow.getCell(firstRowCellNum);
                if(hssfCell == null){
                    continue;
                }
                if(null!=getValue(hssfCell)){
                    paramList.add(getValue(hssfCell).toString());
                }else{
                    paramList.add("null");
                }
            }
            break;
        }

        return paramList ;
    }

    /**
     * 固定格式的单元格合并Execl 首行字段（适用于自适应模板 调整不同模板的单元格读取）
     * @param file
     * @param beginRow 固定行
     * @param beginColumn 开始读取的行
     * @throws Exception
     * @author lin_xx
     * @date 2015-9-17
     */
    public static List<String> importFirstRowByIndex(File file,int beginRow, int beginColumn) throws Exception{

        List<String> paramList = new ArrayList<String>();

        InputStream is = new FileInputStream(file);
        Workbook hssfWorkbook = null;
        String type = getFileByFile(file);
        if("xls".equals(type)){
            hssfWorkbook = new HSSFWorkbook(is);
        }else if("xlsx".equals(type)){
            hssfWorkbook = new XSSFWorkbook(file.getPath());
        }else{
            try{
                //大宁模板 type为null的情况
                hssfWorkbook = new XSSFWorkbook(file.getPath());
            }catch (Exception e){
                paramList.add("errorFile");
                return paramList;
            }

        }
        for(int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++){
            Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if(hssfSheet == null){
                continue;
            }
            Row firstRow = hssfSheet.getRow(beginRow);
            if(firstRow == null){
                continue;
            }
            if(beginColumn != 0){
                paramList.add("");
                for(int i = 1 ;i< beginColumn;++i){
                    paramList.add("null");
                }
            }
            for(int firstRowCellNum = beginColumn; firstRowCellNum < firstRow.getLastCellNum(); firstRowCellNum++){
                Cell hssfCell = firstRow.getCell(firstRowCellNum);
                if(hssfCell == null){
                    paramList.add("null");
                    continue;
                }
                if(null!=getValue(hssfCell)){
                    paramList.add(getValue(hssfCell).toString());
                }else{
                    paramList.add("null");
                }
            }
            break;
        }
        return paramList ;
    }

    /**
	 * 导入数据（多表）从多个sheet表中获取数据
	 * @param file
	 * @param paramListMap {t_base_personnel_Field:List...}
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 18, 2013
	 */
	public static Map<String,List<Map<String, Object>>> importExcel(File file, Map<String,List<String>> paramListMap,String tnstr) throws Exception{
		Map<String,List<Map<String, Object>>> map = new HashMap<String,List<Map<String, Object>>>();
		InputStream is = new FileInputStream(file);
		Workbook hssfWorkbook = null; 
		String type = getFileByFile(file);
		if("xls".equals(type)){
			hssfWorkbook = new HSSFWorkbook(is);
		}else if("xlsx".equals(type)){
			hssfWorkbook = new XSSFWorkbook(file.getPath());
		}
		//HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		//读取表名tnkey和表描述名tnval
		JSONObject json = JSONObject.fromObject(tnstr);
		Iterator<String> it = json.keys();
	    while(it.hasNext()) {
	    	String tnkey = it.next();//表名
	    	String tnval = json.getString(tnkey);//表描述名
	    	for(int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++){
				Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if(hssfSheet == null){
					continue;
				}
				//表描述名
				String sheetName = hssfSheet.getSheetName();
				
				if(tnval.equals(sheetName)){
					List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
					for(int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++){
						Map<String, Object> map2 = new HashMap<String, Object>();
						Row hssfRow = hssfSheet.getRow(rowNum);
						if(hssfRow == null){
							continue;
						}
						for(int cellNum = 0; cellNum < hssfRow.getLastCellNum(); cellNum++){
							Cell hssfCell = hssfRow.getCell(cellNum);
							if(hssfCell == null){
								continue;
							}
							List<String> paramList = paramListMap.get(tnkey+"_Field");
							if(paramList.size()>cellNum && !"null".equals(paramList.get(cellNum))){
								//当列为id列时，读取的数字不取小数位
								if(null!=paramList.get(cellNum)&&paramList.get(cellNum).equals("id")){
									Object o = getValue(hssfCell);
									if(o instanceof String){
										map2.put(paramList.get(cellNum),o);
									}else{
										BigDecimal dou = new BigDecimal(o.toString());
										Integer integer = dou.intValue();
										map2.put(paramList.get(cellNum),integer+"");
									}
								}else{
									if(null != getValue(hssfCell) && !"".equals(getValue(hssfCell))){
										map2.put(paramList.get(cellNum), getValue(hssfCell));
									}
								}
							}
						}
						if(!map.isEmpty()){
							result.add(map2);
						}
					}
					map.put(tnkey, result);
					break;
				}
			}
	    }
		return map;
	}
	
	/**
	 * Excel导出方法
	 * @param list 导出的数据
     * @param title 导出文件名称
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date 2013-3-12
	 */
	public  static boolean exportExcel(List<Map<String, Object>> list, String title) throws Exception{
        //文件存放路径
        String phyPathDir=CommonUtils.getUploadFileDateTimePhysicalDir("export");
        String fileName = CommonUtils.getDateTimeUUID() + ".xls";
        File file = new File(phyPathDir, fileName);
		if(!file.exists()){
            file.createNewFile();
		}
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("sheet1");
        CellStyle intStyle = book.createCellStyle();
        CellStyle decimalStyle = book.createCellStyle();
        CellStyle decimalStyle2 = book.createCellStyle();
        DataFormat format = book.createDataFormat();
		for(int rowNum = 0; rowNum < list.size(); rowNum++){
			LinkedHashMap<String, Object> rowMap = (LinkedHashMap<String, Object>) list.get(rowNum);
			Row row = sheet.createRow(rowNum);
			int cellNum = 0;
			for(Map.Entry<String, Object> entry : rowMap.entrySet()){
				Cell cell = row.createCell(cellNum++);
                doSetExportExcelCellTypeAndValue(entry.getValue(),cell,entry.getKey(),book,intStyle,decimalStyle,decimalStyle2,format,"0");
			}
		}
		//设置自适应列宽
		if(null!=list && list.size()>0){
			LinkedHashMap<String, Object> rowMap1 = (LinkedHashMap<String, Object>) list.get(0);
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
		downloadExcel(phyPathDir, fileName,title+".xls");
		deleteExcelFile(phyPathDir, fileName);
		return true;
	}

    /**
     * Excel导出方法
     * @param dataListMap 导出的数据
     * @param sheetMap sheet名
     * @param title 导出文件名称
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date 2013-3-12
     */
    public  static boolean exportExcel(Map<String,List<Map<String, Object>>> dataListMap, Map<String,Object>sheetMap, String title) throws Exception{
        //文件存放路径
        String phyPathDir=CommonUtils.getUploadFileDateTimePhysicalDir("export");
        String fileName = CommonUtils.getDateTimeUUID() + ".xlsx";
        File file = new File(phyPathDir, fileName);
        if(!file.exists()){
            file.createNewFile();
        }
        Workbook book = new SXSSFWorkbook(1000);
        //把json字符串转换成对象

        CellStyle intStyle = book.createCellStyle();
        CellStyle decimalStyle = book.createCellStyle();
        CellStyle decimalStyle2 = book.createCellStyle();

        for(Map.Entry<String, Object> sheetEntry : sheetMap.entrySet()){
            String key = (String) sheetEntry.getKey();
            for(Map.Entry<String, List<Map<String, Object>>> entry : dataListMap.entrySet()){
                if(key.equals(entry.getKey())){
                    String sheetName = (String) sheetMap.get("name_"+key);
                    Sheet sheet = book.createSheet(sheetName);
                    List<Map<String, Object>> list = dataListMap.get(entry.getKey());
                    if(list.size() != 0){
                        DataFormat format = book.createDataFormat();
                        for(int rowNum = 0; rowNum < list.size(); rowNum++){
                            LinkedHashMap<String, Object> rowMap = (LinkedHashMap<String, Object>) list.get(rowNum);
                            Row row = sheet.createRow(rowNum);
                            int cellNum = 0;
                            for(Map.Entry<String, Object> rowEntry : rowMap.entrySet()){
                                Cell cell = row.createCell(cellNum++);
                                doSetExportExcelCellTypeAndValue(rowEntry.getValue(),cell,rowEntry.getKey(),book,intStyle,decimalStyle,decimalStyle2,format,"0");
                            }
                        }
                        //设置自适应列宽
                        if(null!=list && list.size()>0){
                            LinkedHashMap<String, Object> rowMap1 = (LinkedHashMap<String, Object>) list.get(0);
                            int i = 0;
                            for(Map.Entry<String, Object> entry2 : rowMap1.entrySet()){
                                sheet.autoSizeColumn(i);
                                int maxColumnWidth = sheet.getColumnWidth(i);
                                sheet.setColumnWidth(i, maxColumnWidth+500);
                                i++;
                            }
                        }
                    }
                }
            }
        }
        OutputStream out = new FileOutputStream(file);
        book.write(out);
        out.close();

        downloadExcel(phyPathDir, fileName,title+".xlsx");
        deleteExcelFile(phyPathDir, fileName);
        return true;
    }

	
	/**
	 * txt导出方法
	 * @param list
	 * @param title
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 13, 2014
	 */
	public static boolean exportTXT(List<Map<String, Object>> list, String title)throws Exception{
        String phyPathDir=CommonUtils.getUploadFileDateTimePhysicalDir("export");
		String fileName = CommonUtils.getDateTimeUUID() + ".txt";
		File file = new File(phyPathDir, fileName);
		if(!file.exists()){
			file.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(file);
		for(Map<String, Object> map : list){
			String goodsid = (String)map.get("goodsid");
			for(int i=goodsid.length();goodsid.length()<15;i++){
				goodsid += " ";
			}
			//条形码+商品名称
			String barcode = "0";
			if(null != map.get("barcode")){
				barcode = (String)map.get("barcode");
			}
			for(int i=barcode.length();barcode.length()<13;i++){
				barcode += " ";
			}
			String name = barcode+(String)map.get("name");
			if(name.getBytes().length > 63){
				char[] namechar = name.toCharArray();
				List list2 = new ArrayList();
				char[] chara = new char[63];
				int charlen = 0;
				for(int i=0;i<namechar.length;i++){
					String b = namechar[i] + "";
					byte[] bytes = b.getBytes();
					charlen = charlen + bytes.length;
					if(charlen <= 63){
						list2.add(namechar[i]);
					}else{
						break;
					}
				}
				String subname = "";
				for(int i=0;i<list2.size();i++){
					if(StringUtils.isEmpty(subname)){
						subname = list2.get(i).toString();
					}else{
						subname += list2.get(i).toString();
					}
				}
				name = subname;
			}
			for(int i=name.getBytes().length;name.getBytes().length < 63;i++){
				name += " ";
			}
			//真实数量
			BigDecimal realnum = (BigDecimal)map.get("realnum");
			String realnumstr = realnum.setScale(0, BigDecimal.ROUND_HALF_UP).toEngineeringString();
			for(int i=realnumstr.length();realnumstr.length() < 12;i++){
				realnumstr += " ";
			}
			String outstr = goodsid + name + realnumstr + "\r\n";
			fos.write(outstr.getBytes());
		}
		fos.flush(); 
		fos.close();
		downloadExcel(phyPathDir, fileName,title+".txt");
		deleteExcelFile(phyPathDir, fileName);
		return true;
	}
	
	/**
	 * Excel多表导出方法
	 * @param map2 List<Map<String, Object>> 导出数据
	 * @param title 导出文件默认名称
	 * @param tnjson 需要导出的表名json字符串
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 17, 2013
	 */
	public static boolean exportMoreExcel(Map<String,List<Map<String, Object>>> map2, String title,String tnjson) throws Exception{
        //物理路径
        String phyPathDir=CommonUtils.getUploadFileDateTimePhysicalDir("export");
        String fileName = CommonUtils.getDateTimeUUID() + ".xls";
		File file = new File(phyPathDir, fileName);
		if(!file.exists()){
			file.createNewFile();
		}
        Workbook book = new HSSFWorkbook();
		if(StringUtils.isNotEmpty(tnjson)){
			//把json字符串转换成对象
			JSONObject jsonMap = JSONObject.fromObject(tnjson);
			Iterator<String> it = jsonMap.keys();
		    while(it.hasNext()){
		        String key = (String) it.next();//表名：t_base_personnel
		        for(Map.Entry<String, List<Map<String, Object>>> entry : map2.entrySet()){
		        	if(key.equals(entry.getKey().substring(0, entry.getKey().lastIndexOf("_")))){
		        		String u = jsonMap.get(key).toString();
						Sheet sheet = book.createSheet(u);
						List<Map<String, Object>> list = map2.get(entry.getKey());
						if(list.size() != 0){
                            CellStyle intStyle = book.createCellStyle();
                            CellStyle decimalStyle = book.createCellStyle();
                            CellStyle decimalStyle2 = book.createCellStyle();
                            DataFormat format = book.createDataFormat();
							for(int rowNum = 0; rowNum < list.size(); rowNum++){
								LinkedHashMap<String, Object> rowMap = (LinkedHashMap<String, Object>) list.get(rowNum);
								Row row = sheet.createRow(rowNum);
								int cellNum = 0;
								for(Map.Entry<String, Object> rowEntry : rowMap.entrySet()){
									Cell cell = row.createCell(cellNum++);
                                    doSetExportExcelCellTypeAndValue(rowEntry.getValue(),cell,rowEntry.getKey(),book,intStyle,decimalStyle,decimalStyle2,format,"0");
								}
							}
							//设置自适应列宽
							if(null!=list && list.size()>0){
								LinkedHashMap<String, Object> rowMap1 = (LinkedHashMap<String, Object>) list.get(0);
								int i = 0;
								for(Map.Entry<String, Object> entry2 : rowMap1.entrySet()){
									sheet.autoSizeColumn(i);
									int maxColumnWidth = sheet.getColumnWidth(i);
									sheet.setColumnWidth(i, maxColumnWidth+500);
									i++;
								}
							}
							OutputStream out = new FileOutputStream(file);
							book.write(out);
							out.close();
						}
		        	}
		        }
		    }
		    downloadExcel(phyPathDir, fileName,title+".xls");
			deleteExcelFile(phyPathDir, fileName);
			return true;
		}
		return false;
	}
    /**
     * 下载已经导出的文件到客户端
     * @param path 下载文件路径
     * @param fileName 文件名称
     * @throws Exception
     * @author zhengziyong
     * @date 2013-3-12
     */
    public static void downloadExcel(String path, String fileName) throws Exception{
        HttpServletResponse response = ServletActionContext.getResponse();
        File file = new File(path, fileName);
        InputStream is = new BufferedInputStream(new FileInputStream(file));
//		byte[] buffer = new byte[is.available()];
//		is.read(buffer);
//		is.close();
        response.addHeader("Content-Length", "" + file.length());
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO8859-1" )+"\"");
        PrintWriter os = response.getWriter();
//		OutputStream os = new BufferedOutputStream(response.getOutputStream());
        int i;
        while ((i = is.read()) != -1) {
            os.write(i);
        }
        is.close();
        os.flush();
        os.close();
    }
	/**
	 * 下载已经导出的文件到客户端
	 * @param path 下载文件路径
	 * @param fileName 文件名称
     * @param title     文件名
	 * @throws Exception
	 * @author zhengziyong 
	 * @date 2013-3-12
	 */
	public static void downloadExcel(String path, String fileName,String title) throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		File file = new File(path, fileName);
		InputStream is = new BufferedInputStream(new FileInputStream(file));
//		byte[] buffer = new byte[is.available()];
//		is.read(buffer);
//		is.close();
		response.addHeader("Content-Length", "" + file.length());
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment;filename=\"" + new String(title.getBytes("UTF-8"), "ISO8859-1" )+"\"");
		PrintWriter os = response.getWriter();
//		OutputStream os = new BufferedOutputStream(response.getOutputStream());
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
	
	private static String getFieldName(String str){
		Pattern pattern = Pattern.compile("([a-zA-Z_]+)");
		Matcher matcher = pattern.matcher(str);
		String result = "";
		if(matcher.find()){
			result = matcher.group();
		}
		return result;
	}
	
	/**
	 * 获取excel表格中各单元格的值
	 * @param hssfCell
	 * @return
	 * @date 2013-3-23
	 */
	private static Object getValue(Cell hssfCell){
		if(hssfCell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN){
			return hssfCell.getBooleanCellValue();
		}
		else if(hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
			if(HSSFDateUtil.isCellDateFormatted(hssfCell)){
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				return sf.format(hssfCell.getDateCellValue());
			}else{
				hssfCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				return hssfCell.getStringCellValue();
			}
		}else if(hssfCell.getCellType() == HSSFCell.CELL_TYPE_FORMULA){
            try {
                return String.valueOf(hssfCell.getNumericCellValue());
            } catch (IllegalStateException e) {
                return String.valueOf(hssfCell.getRichStringCellValue());
            }
		}
		else if(hssfCell.getCellType() == HSSFCell.CELL_TYPE_STRING){
			return hssfCell.getStringCellValue().trim();
		}
		else if(hssfCell.getCellType() == HSSFCell.CELL_TYPE_BLANK){
			return null;
		}
		else{
			return hssfCell.getErrorCellValue();
		}
	}

    /**
     * 全局导出 数据解析
     * @param map
     * @throws Exception
     * @author lin_xx
     * @date 2016-4-2
     */
    public static void exportAnalysExcel(Map<String, String> map,List list) throws Exception {
        String fileType = "xls";
        if(map.containsKey("fileType")){
            fileType = map.get("fileType");
        }
        Map formmaterMap = new HashMap();
        //JS方法的备用参数 组装
        Map backMap = new HashMap();
        //获取return goodInfo.××× 中的×××属性
        Map<String,String> goodsMap = new HashMap();
        if(map.containsKey("formmater")){
            String func = map.get("formmater");
            func = func.substring(0,func.length()-1);

            String[] funcs = func.split("},");
            for(String dataFunc : funcs){
                String[] data = dataFunc.split(":");
                String field = data[0].replace("{","");
                String funStr = "";
                //给formmater添加方法名为 formatter
                funStr = data[1].replace("function","function formatter");
                funStr = funStr + "}" ;
                if(funStr.indexOf("\\") > -1){
                    funStr = funStr.replace("\\", "");
                }
                //获取商品中对应的字段
                if(data[1].contains("goodsInfo")){
                    int begin = data[1].lastIndexOf(".");
                    int end = data[1].lastIndexOf(";");
                    String str = data[1].substring(begin+1,end);
                    if(str.contains("else") && str.contains(";")){
                        str = str.substring(0,str.indexOf(";"));
                    }
                    str = str.replace(")","");
                    goodsMap.put(field,str);
                    continue;
                }else{
                    if(funStr.contains("toFix")){//页面自定义保留几位小数
                        formmaterMap.put(field,funStr);
                    }else{
                        String funStrTrim = funStr.replace(" ","");
                        if(funStrTrim.contains("%")){
                            formmaterMap.put(field,"function formatter(val,row,index)" +
                                    "{if(val != null && val != ''){return Number(val).toFixed(2) + \"%\";} else { return '';}}");
                            //pan_xx修改（2016-12-09）==》对应的js函数方法名称，以备getExportMap方法中，java执行该js方法
                            formmaterMap.put(field+"fuc","formatter");
                        }else if(funStrTrim.contains("formatterMoney")){
                            formmaterMap.put(field,"function formatterMoney(val,row,index)" +
                                    "{if(val != null && val != ''){return Number(val).toFixed(2) ;} else { return 0;}}");
                            formmaterMap.put(field+"fuc","formatterMoney");
                        }else if(funStrTrim.contains("returnNumber")){
                        }else if(funStrTrim.contains("formatterBigNumNoLen")){
                            formmaterMap.put(field,"function formatterBigNumNoLen(val){if(val!=null &&val!=\"\"){return Number(val);}else{return \"0\";}}");
                            formmaterMap.put(field+"fuc","formatterBigNumNoLen");
                        }else if(funStrTrim.contains("formatterDefineMoney")){
                            String nstr = funStrTrim.substring(funStrTrim.lastIndexOf(")")-1,funStrTrim.lastIndexOf(")"));
                            int n = 2;
                            if(StringUtils.isNotEmpty(nstr) && StringUtils.isNumeric(nstr)){
                                n = Integer.parseInt(nstr);
                            }
                            formmaterMap.put(field, "function formatterDefineMoney(val){if(val!=null && (val!=\"\" || val==0)){if(Number(val)<0){var newdata= Number(val).toFixed("+n+");if(newdata==0){return Number(newdata).toFixed(0);}return newdata;}else if(Number(val) == 0){return Number(val).toFixed("+n+");}else{return Number(val).toFixed("+n+");}}else{return \"\";}}");
                            formmaterMap.put(field+"fuc","formatterDefineMoney");
                        }
                    }

                    //判断使用row中另一个参数进行判断
                    if(funStr.contains("if(row") ){
                        if(funStr.contains("return value")){
                            formmaterMap.put(field,funStr);
                        }else if(funStr.contains("<a href=")){//有链接直接取值
                            formmaterMap.put(field,"function formatter(val,row,index)" +
                                    "{if(val != null && val != ''){return Number(val).toFixed(2) ;} else { return '0';}}");
                            formmaterMap.put(field+"fuc","formatter");
                        }else if(funStr.contains("else if") || funStr.contains("return \"")){
                            formmaterMap.put(field,funStr);
                        }else{
                            int begin = data[1].lastIndexOf("if(row");
                            int end = data[1].lastIndexOf("){");
                            String str = data[1].substring(begin,end);
                            str = str.substring(str.indexOf("."),str.length());
                            String reg = "[^\u4e00-\u9fa5a-zA-Z]";//是否为字母
                            str = str.replaceAll(reg, "");
                            backMap.put(field,str);
                        }
                    }
                    //返回的是另外一个参数
                    if(funStr.replace(" ","").contains("returnrow")){
                        int begin = data[1].lastIndexOf(".");
                        int end = data[1].lastIndexOf(";");
                        String str = data[1].substring(begin+1,end);
                        //如果formmater解析出来的字符串包含 分号
                        if(str.contains(";")){
                            str = str.substring(0,str.indexOf(";"));
                        }
                        backMap.put(field,str);
                    }

                }

            }
        }
        String title = "";
        if(map.containsKey("exportname")){
            title = map.get("exportname").toString();
        }else{
            title = "导出";
        }
        if(StringUtils.isEmpty(title)){
            title = "导出";
        }
        String commmonCol = map.get("commonCol");
        String[] cols = commmonCol.split(",");
        //获取首行列名
        String firstCol = map.get("colName");
        String[] firstColumn = firstCol.split(",");
        Map firstMap = new LinkedHashMap();
        for(int i=0;i< firstColumn.length;i++){
            firstMap.put(cols[i],firstColumn[i]);
        }
        if(list.size()>60000){
            ExcelTheadUtils.multiThreadWriteCSV(list, formmaterMap, backMap, cols, firstMap, title, goodsMap);
        }else{
            if("csv".equals(fileType)){
                exportCSVReport(list, formmaterMap, backMap, cols, firstMap, title,goodsMap);
            }else if("xlsx".equals(fileType)){
                exportExcel2007Report(list, formmaterMap, backMap, cols, firstMap, title,goodsMap);
            }else{
                exportExcelReport(list, formmaterMap, backMap, cols, firstMap, title,goodsMap);
            }
        }

    }
    /**
     * Excel导出方法
     * @param dataList 组装后的导出数据
     * @param formmaterMap js中的formmater方法
     * @param backMap 组装的参数Map
     * @param cols 需要导出的字段
     * @param firstMap 首行列名List
     * @param title 导出文件名称
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date 2013-3-12
     */
    public static boolean exportCSVReport(List<Object> dataList ,Map formmaterMap ,Map backMap, String[] cols ,Map<String, Object> firstMap, String title,Map<String,String> goodsMap) throws Exception{
        String fileName = title+".csv";
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO8859-1" )+"\"");
        response.setCharacterEncoding("GBK");
        PrintWriter os = response.getWriter();
        BufferedWriter bw = new BufferedWriter(os,8*1024);
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
        for(int i = 0; i < dataList.size(); i++){
            Object dataObject = dataList.get(i);
            Map dataMap = null;
            if(dataObject instanceof Map){
                dataMap = (Map) dataObject;
            }else{
                dataMap = PropertyUtils.describe(dataList.get(i));
            }
            if(dataMap.containsKey("goodsInfo")){
                Object object = dataMap.get("goodsInfo");
                if (null != object){
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

                //组装对应col列的值
//                Map exportMap = getExportMap(formmaterMap ,backMap,dataMap ,col , i);
                Object object = getExportMap(formmaterMap, backMap, dataMap, col, i);
                String key = col;

                if(null!=object){
                    if (object instanceof String) {
                        rowStr.append(object.toString()).append(",");
                    }else if(object instanceof BigDecimal){
                        BigDecimal bignum = (BigDecimal) object;
                        if(null!=bignum){
                            rowStr.append(bignum.toString()).append(",");
//                            if("taxprice".equals(key)){
//                                double bignumstr = bignum.doubleValue();
//                                String[] strarr =(bignumstr+"").split("\\.");
//                                if(strarr.length>1 && strarr[1].length()<=2){
//                                    rowStr.append(bignum.setScale(2,BigDecimal.ROUND_HALF_UP).toString()).append(",");
//                                }else{
//                                    rowStr.append(bignumstr).append(",");
//                                }
//                            }
//                            else if("totalweight".equals(key) || "totalvolume".equals(key) || "singlevolume".equals(key) || "glength".equals(key) ||
//                                    "ghight".equals(key) || "gwidth".equals(key) || "gdiameter".equals(key) || "grossweight".equals(key) || "netweight".equals(key) || "nowprice".equals(key) || "nowboxprice".equals(key) || "oldprice".equals(key) || "oldboxprice".equals(key)){
//                                rowStr.append(bignum.setScale(6,BigDecimal.ROUND_HALF_UP).toString()).append(",");
//                            }
//                            else if(key.contains("num") || key.contains("auxremainder")){
//                                int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
//                                if(decimalScale != 0){
//                                    bignum = bignum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
//                                }
//                                rowStr.append(bignum).append(",");
//                            }else if(key.contains("totalbox")){
//                                bignum = bignum.setScale(3,BigDecimal.ROUND_HALF_UP);
//                                rowStr.append(bignum).append(",");
//                            }else{
//                                rowStr.append(bignum.setScale(2,BigDecimal.ROUND_HALF_UP).toString()).append(",");
//                            }
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
        }
        bw.flush();
        bw.close();
        os.close();
        return true;
    }
    /**
     * Excel导出方法
     * @param dataList 组装后的导出数据
     * @param formmaterMap js中的formmater方法
     * @param backMap 组装的参数Map
     * @param cols 需要导出的字段
     * @param firstMap 首行列名List
     * @param title 导出文件名称
     * @param goodsMap
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date 2013-3-12
     */
    public static boolean exportExcel2007Report(List<Object> dataList ,Map formmaterMap ,Map backMap, String[] cols ,Map<String, Object> firstMap, String title,Map<String,String> goodsMap) throws Exception{
        //文件存放路径
        String phyPathDir=CommonUtils.getUploadFileDateTimePhysicalDir("export");
        String fileName = CommonUtils.getDateTimeUUID() + ".xlsx";
        File file = new File(phyPathDir, fileName);
        if(!file.exists()){
            file.createNewFile();
        }

        Workbook book = new SXSSFWorkbook(1000);
        Sheet sheet = book.createSheet("sheet1");
        CellStyle intStyle = book.createCellStyle();
        CellStyle decimalStyle = book.createCellStyle();
        CellStyle decimalStyle2 = book.createCellStyle();
        DataFormat format = book.createDataFormat();
        Row firstRow = sheet.createRow(0);
        //首行
        int firstCellNum = 0;
        for(Map.Entry<String, Object> entry : firstMap.entrySet()){
            Cell cell = firstRow.createCell(firstCellNum++);
            doSetExportExcelCellTypeAndValue(entry.getValue(),cell,entry.getKey(),book,intStyle,decimalStyle,decimalStyle2,format,"1");
        }
        for(int i = 0; i < dataList.size(); i++){
            Row row = sheet.createRow(i+1);
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
                if (null != object){
                    Map goodsValueMap = CommonUtils.beanToMap(object);
                    for(Map.Entry<String, String> entry : goodsMap.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        dataMap.put(key,goodsValueMap.get(value));
                    }
                }
            }
//            Map dataMap = dataList.get(i);
            for(String col : cols){
                //组装对应col列的值
                Object object = getExportMap(formmaterMap ,backMap,dataMap ,col , i);
                Cell cell = row.createCell(cellNum++);
//                Object object = exportMap.get(col);
                doSetExportExcelCellTypeAndValue(object,cell,col,book,intStyle,decimalStyle,decimalStyle2,format,"1");
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
        downloadExcel(phyPathDir, fileName,title+".xlsx");
        deleteExcelFile(phyPathDir, fileName);
        return true;
    }
    /**
     * Excel导出方法
     * @param dataList 组装后的导出数据
     * @param formmaterMap js中的formmater方法
     * @param backMap 组装的参数Map
     * @param cols 需要导出的字段
     * @param firstMap 首行列名List
     * @param title 导出文件名称
     * @param goodsMap
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date 2013-3-12
     */
    public static boolean exportExcelReport(List<Object> dataList ,Map formmaterMap ,Map backMap, String[] cols ,Map<String, Object> firstMap, String title,Map<String,String> goodsMap) throws Exception{
        //文件存放路径
        String phyPathDir=CommonUtils.getUploadFileDateTimePhysicalDir("export");
        String fileName = CommonUtils.getDateTimeUUID() + ".xls";
        File file = new File(phyPathDir, fileName);
        if(!file.exists()){
            file.createNewFile();
        }
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("sheet1");
        CellStyle intStyle = book.createCellStyle();
        CellStyle decimalStyle = book.createCellStyle();
        CellStyle decimalStyle2 = book.createCellStyle();
        DataFormat format = book.createDataFormat();
        //首行
        Row firstRow = sheet.createRow(0);
        //首行
        int firstCellNum = 0;
        for(Map.Entry<String, Object> entry : firstMap.entrySet()){
            Cell cell = firstRow.createCell(firstCellNum++);
            doSetExportExcelCellTypeAndValue(entry.getValue(),cell,entry.getKey(),book,intStyle,decimalStyle,decimalStyle2,format,"1");
        }
        for(int i = 0; i < dataList.size(); i++){
            Row row = sheet.createRow(i+1);
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
                if(null != object ){
                    Map goodsValueMap = CommonUtils.beanToMap(object);
                    for(Map.Entry<String, String> entry : goodsMap.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        dataMap.put(key,goodsValueMap.get(value));
                    }
                }
            }
//            Map dataMap = dataList.get(i);
            for(String col : cols){
                //组装对应col列的值
                Object object = getExportMap(formmaterMap ,backMap,dataMap ,col , i);

                Cell cell = row.createCell(cellNum++);
//                Object object = exportMap.get(col);
                doSetExportExcelCellTypeAndValue(object,cell,col,book,intStyle,decimalStyle,decimalStyle2,format,"1");
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
        downloadExcel(phyPathDir, fileName,title+".xls");
        deleteExcelFile(phyPathDir, fileName);
        return true;
    }

    /**
     * 判断key是否字符串类型
     * @param key
     * @return
     */
    private static boolean isKeyStringCol(String key){
        String[] colArr = {"goodsid","jsgoodsid","customerid","pcustomerid","barcode","boxbarcode","brandid","spell","itemno"};
        for(String col : colArr){
            if(col.equals(key)){
                return true;
            }
        }
        return false;
    }
    /**
     *
     * @param object
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-06-13
     */
    private static boolean doSetExportExcelCellTypeAndValue(Object object,Cell cell,String key,Workbook book,CellStyle intStyle,CellStyle decimalStyle,CellStyle decimalStyle2,DataFormat format,String isAnalyse)throws Exception{
        if(null!=object){
            if (object instanceof String) {
                String result = (String) object;
//                //判断字符串是否符合要求的整数或者浮点数
//                if(!isKeyStringCol(key)&& CommonUtils.isNumericDig(result)){
//                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//                    cell.setCellValue((new BigDecimal(result).doubleValue()));
//                }else{
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(result);
//                }
            }else if(object instanceof BigDecimal){
                BigDecimal bignum = (BigDecimal) object;
                if(null!=bignum){
                    if("0".equals(isAnalyse)){
                        //格式只能设置一次
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
                        }else if("totalweight".equals(key) || "totalvolume".equals(key) || "singlevolume".equals(key) || "glength".equals(key) || "volume".equals(key) ||
                                "ghight".equals(key) || "gwidth".equals(key) || "gdiameter".equals(key) || "grossweight".equals(key) || "netweight".equals(key) || "nowprice".equals(key) ||
                                "nowboxprice".equals(key) || "oldprice".equals(key) || "oldboxprice".equals(key) || "nocostprice".equals(key)){
                            decimalStyle2.setDataFormat(format.getFormat("0.000000"));
                            cell.setCellStyle(decimalStyle2);
                        }else if( "jsnotaxprice".equals(key)){
                            decimalStyle2.setDataFormat(format.getFormat("0.000000000"));
                            cell.setCellStyle(decimalStyle2);
                        }else if(key.contains("num") || key.contains("auxremainder") || key.equals("rate") || key.equals("highestinventory") || key.equals("lowestinventory")){
                            int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
                            if(decimalScale != 0){
                                bignum = bignum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
                            }
                        } else if (key.contains("totalbox")){
                            bignum = bignum.setScale(3,BigDecimal.ROUND_HALF_UP);
                        }
                        else{
                            decimalStyle.setDataFormat(format.getFormat("0.00"));
                            cell.setCellStyle(decimalStyle);
                        }
                        cell.setCellValue(bignum.doubleValue());
                    }else{
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(bignum.doubleValue());
                    }
                }else{
                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
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
            }else if(object instanceof Long){
                Long longval = (Long) object;
                intStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
                cell.setCellStyle(intStyle);
                cell.setCellValue(longval);
            }else if(object instanceof Double){
                cell.setCellValue((Double)object);
            }else{
                cell.setCellValue((String)object);
            }
        }else{
            cell.setCellValue("");
        }
        return true;
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
            String script = (String) formmaterMap.get(col);
            //js方法名称
            String fucname = null != formmaterMap.get(col+"fuc") ? (String)formmaterMap.get(col+"fuc") : "formatter";
            try{
                // 读取js脚本
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
                    //执行指定的js方法
                    obj = inv.invokeFunction(fucname, value , dataMap , index);
                }
//                if(script.contains("Number") && !script.contains("%")){
//                    obj = new BigDecimal((String)obj);
//                }

                if(null == obj){
                    String returnName = (String) backMap.get(col);
                    result = dataMap.get(returnName);
                }else{
                     if("formatter".equals(fucname) || "formatterMoney".equals(fucname) || "formatterBigNumNoLen".equals(fucname)
                            || "formatterDefineMoney".equals(fucname)){
                        if(StringUtils.isNotEmpty(obj.toString())){
                            //先判断obj是否能转为BigDecimal，例如20%不能转为数字需要按照字符串格式打印
                            if (obj instanceof BigDecimal) {
                                result = new BigDecimal(obj.toString());
                            }else{
                                result=obj.toString();
                            }

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
            if("status".equals(col)){
                if("1".equals(dataMap.get(col))){
                    return "暂存";
                }else if("2".equals(dataMap.get(col))){
                    return "保存";
                }else if("3".equals(dataMap.get(col))){
                    return "审核通过";
                }else if("4".equals(dataMap.get(col))){
                    return "关闭";
                }else if("5".equals(dataMap.get(col))){
                    return "中止";
                }else if("6".equals(dataMap.get(col))){
                    return "审核中";
                }else {
                    return "";
                }
            }else if(!dataMap.containsKey(col) || "null".equals(dataMap.get(col)) ){
                return "";
            }else{
                return dataMap.get(col);
            }

        }
    }

    public static String createErrorExcelFile(List<Map<String, Object>> errorList, String title) throws Exception {

        //文件存放路径
        String path = OfficeUtils.getFilepath() + "/error/" + CommonUtils.getTodayDateStr() + "/";
        File file2 = new File(path);
        //判断文件夹是否存在,如果不存在则创建文件夹
        if (!file2.exists()) {
            file2.mkdir();
        }
        String fileName = title + ".xls";
        File pathdir = new File(path);
        if (!pathdir.exists()) {
            pathdir.mkdirs();
        }
        File file = new File(path, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("sheet1");
        CellStyle intStyle = book.createCellStyle();
        CellStyle decimalStyle = book.createCellStyle();
        CellStyle decimalStyle2 = book.createCellStyle();
        DataFormat format = book.createDataFormat();
        for (int rowNum = 0; rowNum < errorList.size(); rowNum++) {
            HashMap<String, Object> rowMap = (HashMap<String, Object>) errorList.get(rowNum);
            Row row = sheet.createRow(rowNum);
            int cellNum = 0;
            for (Map.Entry<String, Object> entry : rowMap.entrySet()) {
                Cell cell = row.createCell(cellNum++);
                doSetExportExcelCellTypeAndValue(entry.getValue(), cell, entry.getKey(), book, intStyle, decimalStyle, decimalStyle2, format,"0");
            }
        }
        //设置自适应列宽
        if (null != errorList && errorList.size() > 0) {
            HashMap<String, Object> rowMap1 = (HashMap<String, Object>) errorList.get(0);
            int i = 0;
            for (Map.Entry<String, Object> entry : rowMap1.entrySet()) {
                sheet.autoSizeColumn(i);
                int maxColumnWidth = sheet.getColumnWidth(i);
                if (maxColumnWidth == 65280) {
                    sheet.setColumnWidth(i, 5000);//数据过长时设置最大的列宽
                } else {
                    sheet.setColumnWidth(i, maxColumnWidth + 500);
                }

                i++;
            }
        }
        OutputStream out = new FileOutputStream(file);
        book.write(out);
        out.close();

        AttachFile attachFile = new AttachFile();
        attachFile.setExt(".xls");
        attachFile.setFilename(file.getName());
        attachFile.setFullpath("upload/error/" + CommonUtils.getTodayDateStr() + "/" + file.getName());
        attachFile.setOldfilename(file.getName());
        //将临时文件信息插入数据库
        ((IAttachFileService) SpringContextUtils.getBean("attachFileService")).addAttachFile(attachFile);

        return attachFile.getId();
    }

    /**
     * 获取某一个sheet里的某行某列的数据
     * @param file
     * @param sheetnum
     * @param row
     * @param col
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Jan 04, 2018
     */
    public static String getSheetRowCellValue(File file,int sheetnum,int row,int col) throws Exception {
        InputStream is = new FileInputStream(file);
        Workbook hssfWorkbook = null;
        String type = ExcelUtils.getFileByFile(file);
        if("xls".equals(type)){
            hssfWorkbook = new HSSFWorkbook(is);
        }else if("xlsx".equals(type)){
            hssfWorkbook = new XSSFWorkbook(file.getPath());
        }else{
            hssfWorkbook = new XSSFWorkbook(file.getPath()); //大宁模板 type为null的情况
        }
        Sheet hssfSheet = hssfWorkbook.getSheetAt(sheetnum);
        Row hssfRow = hssfSheet.getRow(row-1);
        Cell hssfCell = hssfRow.getCell(col);
        String value=getValue(hssfCell).toString();
        return value;
    }
    /**
     * 转换为HTML代码
     *
     * @param map
     * @throws Exception
     * @author xx
     * @date 2017-4-10
     */
    public static String dataToHTML(Map<String, String> map, List list, Integer rownum) throws Exception {
        Map formmaterMap = new HashMap();
        //JS方法的备用参数 组装
        Map backMap = new HashMap();
        //获取return goodInfo.××× 中的×××属性
        Map<String, String> goodsMap = new HashMap();
        if (map.containsKey("formmater")) {
            String func = map.get("formmater");
            func = func.substring(0, func.length() - 1);

            String[] funcs = func.split("},");
            for (String dataFunc : funcs) {
                String[] data = dataFunc.split(":");
                for(int i=2;i<data.length;i++){
                    data[1]+=data[i];
                }
                String field = data[0].replace("{", "");
                String funStr = "";
                //给formmater添加方法名为 formatter
                funStr = data[1].replace("function", "function formatter");
                funStr = funStr + "}";
                if (funStr.indexOf("\\") > -1) {
                    funStr = funStr.replace("\\", "");
                }
                //获取商品中对应的字段
                if (data[1].contains("goodsInfo")) {
                    int begin = data[1].lastIndexOf(".");
                    int end = data[1].lastIndexOf(";");
                    String str = data[1].substring(begin + 1, end);
                    str = str.replace(")", "");
                    goodsMap.put(field, str);
                    continue;
                }
                if (funStr.contains("toFix")) {//页面自定义保留几位小数
                    formmaterMap.put(field, funStr);
                } else {
                    String funStrTrim = funStr.replace(" ", "");
                    if (funStrTrim.contains("%")) {
                        formmaterMap.put(field, "function formatter(val,row,index)" +
                                "{if(val != null && val != ''){return Number(val).toFixed(2) + \"%\";} else { return '';}}");
                        formmaterMap.put(field+"fuc","formatter");
                    } else if (funStrTrim.contains("formatterMoney")) {
                        formmaterMap.put(field, "function formatterMoney(val,row,index)" +
                                "{if(val != null && val != ''){return Number(val).toFixed(2) ;} else { return 0;}}");
                        formmaterMap.put(field+"fuc","formatterMoney");
                    } else if (funStrTrim.contains("returnNumber")) {
                    } else if (funStrTrim.contains("formatterBigNumNoLen")) {
                        formmaterMap.put(field, "function formatterBigNumNoLen(val){if(val!=null &&val!=\"\"){return Number(val);}else{return \"0\";}}");
                        formmaterMap.put(field+"fuc","formatterBigNumNoLen");
                    }
                }

                //判断使用row中另一个参数进行判断
                if (funStr.contains("if(row")) {
                    if (funStr.contains("return value")) {
                        formmaterMap.put(field, funStr);
                    } else if (funStr.contains("<a href=")) {//有链接直接取值
                        formmaterMap.put(field, "function formatter(val,row,index)" +
                                "{if(val != null && val != ''){return Number(val).toFixed(2) ;} else { return '0';}}");
                        formmaterMap.put(field+"fuc","formatter");
                    } else {
                        int begin = data[1].lastIndexOf("if(row");
                        int end = data[1].lastIndexOf("){");
                        String str = data[1].substring(begin, end);
                        str = str.substring(str.indexOf("."), str.length());
                        String reg = "[^\u4e00-\u9fa5a-zA-Z]";//是否为字母
                        str = str.replaceAll(reg, "");
                        backMap.put(field, str);
                    }
                }
                //返回的是另外一个参数
                if (funStr.replace(" ", "").contains("returnrow")) {
                    int begin = data[1].lastIndexOf(".");
                    int end = data[1].lastIndexOf(";");
                    String str = data[1].substring(begin + 1, end);
                    //如果formmater解析出来的字符串包含 分号
                    if (str.contains(";")) {
                        str = str.substring(0, str.indexOf(";"));
                    }
                    backMap.put(field, str);
                }
            }
        }


        Map mapCol = JSONUtils.jsonStrToMap(map.get("commonCol"));
        List<List> commonList = (List<List>) mapCol.get("common");
//        List commonArrList =commonList.get(0);

        List colLists = new ArrayList();
        List<Map> firstMapList=new ArrayList();
        //保存组装好的列顺序(用于有colspan的情况)
        List cols=new ArrayList();

        for(int j=0;j<commonList.size();j++) {
            //根据第一行colspan获取数据
            List commonArrList =commonList.get(j);
            Map firstMap = new LinkedHashMap();
            List<String> colList = new ArrayList();
            for (int i = 0; i < commonArrList.size(); i++) {
                Map mmap = (Map) commonArrList.get(i);
                if (null == mmap) {
                    continue;
                }
                String field = (String) mmap.get("field");
                if (!"ck".equals(field)) {
                    String title = (String) mmap.get("title");
                    Integer width = (Integer) mmap.get("width");
                    Integer colspan = (Integer) mmap.get("colspan");
                    Integer rowspan = (Integer) mmap.get("rowspan");
                    colList.add(title);
                    Map parammap = new HashMap();
                    parammap.put("title", title);
                    parammap.put("colspan", colspan);
                    parammap.put("rowspan", rowspan);
                    parammap.put("width", width);
                    if(mmap.containsKey("align")){
                        parammap.put("align", (String) mmap.get("align"));
                    }
                    firstMap.put(title, parammap);
                }
            }
            firstMapList.add(firstMap);
            colLists.add(colList);
        }

        int []arr=new int[commonList.size()];
        //获取显示数据的列的顺序（有colspan的时候需要排列顺序）
        List<Map> datacols=getColspanColstr(0,arr,commonList,commonList.get(0).size());


        return dataToHTML(list, rownum, formmaterMap, backMap, colLists, firstMapList, goodsMap,datacols);
    }
    /**
     * Excel导出方法
     *
     * @param dataList     组装后的导出数据
     * @param formmaterMap js中的formmater方法
     * @param backMap      组装的参数Map
     * @param datacols     需要存放数据的列（有colspan的时候会和正常的有差异）
     * @param goodsMap
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date 2013-3-12
     */
    private static String dataToHTML(List<Object> dataList, Integer rownum, Map formmaterMap, Map backMap, List<List> colList, List<Map> firstMapList, Map<String, String> goodsMap,List<Map> datacols) throws Exception {
        StringBuilder result = new StringBuilder();
        StringBuilder head = new StringBuilder();
        int pages = dataList.size() / rownum + (dataList.size() % rownum == 0 ? 0 : 1);
        int pIndex = 0;
        head.append("<table>");
        //表头

        for(int i=0;i<colList.size();i++) {
            head.append("<tr>");
            List<String> cols=colList.get(i);
            Map firstMap=firstMapList.get(i);
            //记录序号
            head.append("<th style='width:50px;'>序号</th>");
            for (String col : cols) {
                head.append("<th");
                Map parammap = (Map) firstMap.get(col);
                String title = (String) parammap.get("title");
                Integer width = (Integer) parammap.get("width");
                Integer colspan = 1, rowspan = 1;
                if (parammap.get("colspan") != null) {
                    String colspanstr = parammap.get("colspan").toString();
                    colspan = Integer.parseInt(colspanstr);
                }
                if (parammap.get("rowspan") != null) {
                    String rowspanstr = parammap.get("rowspan").toString();
                    rowspan = Integer.parseInt(rowspanstr);
                }

                String str = "";
                str += " colspan='" + colspan + "'";
                str += " rowspan='" + rowspan + "'";
                str += " field='" + title + "'";
                head.append(str + "><div style='width:"+width+"px;'>" + title);
                head.append("</div></th>");
            }
            head.append("</tr>");
        }

        //记录
        for (int i = 0; i < dataList.size(); i++) {
            if (i % rownum == 0) {
                result.append(head.toString());
            }
            result.append("<tr>");
            //记录序号
            result.append("<td>"+(i+1)+"</td>");
            int cellNum = 0;
            Object dataObject = dataList.get(i);
            Map dataMap;
            if (dataObject instanceof Map) {
                dataMap = (Map) dataObject;
            } else {
                dataMap = PropertyUtils.describe(dataList.get(i));
            }
            if (dataMap.containsKey("goodsInfo")) {
                Object object = dataMap.get("goodsInfo");
                if (null != object) {
                    Map goodsValueMap = CommonUtils.beanToMap(object);
                    for (Map.Entry<String, String> entry : goodsMap.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        dataMap.put(key, goodsValueMap.get(value));
                    }
                }
            }
            for (Map colMap : datacols) {
                String col=(String)colMap.get("field");
                Integer width=(Integer)colMap.get("width");
                String align = "left";
                if(colMap.containsKey("align")){
                    align=(String)colMap.get("align");
                }
                //组装对应col列的值
                Object obj = getExportMap(formmaterMap, backMap, dataMap, col, i);
                result.append("<td><div style='text-align:"+align+";width:"+width+"px;'>");
                if (null != obj)
                    result.append(obj);
                result.append("</div></td>");
            }
            result.append("</tr>");
            if (i % rownum == rownum - 1 || i == dataList.size() - 1) {
                result.append("</table>");
                pIndex++;
                if (pIndex < pages)
                    result.append("<div style='page-break-after:always;'></div>");
            }
        }
        return result.toString();
    }

    public static List getColspanColstr(int line,int []arr,List<List> commonList,int length){
        List firstList=commonList.get(line);
        List list=new ArrayList();
        int num=1;
        for(int t=arr[line];t<firstList.size();t++){
            if(num<=length){
                Map mmap = (Map) firstList.get(t);
                if (null == mmap) {
                    continue;
                }
                String field = (String) mmap.get("field");
                Integer colspan = (Integer) mmap.get("colspan");
                if (colspan == null || colspan == 1) {
                    arr[line] = t+1;
                    list.add(mmap);
                } else {
                    list.addAll(getColspanColstr(line + 1, arr, commonList,colspan));
                }
            }
            num++;
        }
        return list;
    }

    /**
     * 获取第sheetnum工作表里单元格里的值
     * @throws
     * @author lin_xx
     * @date 2018-02-23
     */
    public static String getSheetRowCellValue(File file,int sheetnum,String cell) throws Exception {

        InputStream is = new FileInputStream(file);
        Workbook hssfWorkbook = null;
        String type = ExcelUtils.getFileByFile(file);
        if("xls".equals(type)){
            hssfWorkbook = new HSSFWorkbook(is);
        }else if("xlsx".equals(type)){
            hssfWorkbook = new XSSFWorkbook(file.getPath());
        }else{
            hssfWorkbook = new XSSFWorkbook(file.getPath());
        }
        //拆分单元格的行列值
        String Column = cell.replaceAll("\\d+", "");
        int col = ExcelUtils.chagneCellColtoNumber(Column);
        String remarkRowStr = cell.replaceAll("[a-zA-Z]+", "");
        int row = Integer.parseInt(remarkRowStr);
        String value="";
        Sheet hssfSheet = hssfWorkbook.getSheetAt(sheetnum);
        if(null != hssfSheet){
            Row hssfRow = hssfSheet.getRow(row-1);
            if(null != hssfRow){
                Cell hssfCell = hssfRow.getCell(col);
                if(null != hssfCell){
                    value = getValue(hssfCell).toString();
                }
            }
        }
        return value;
    }

}
