/**
 * @(#)CommonUtils.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-8 chenwei 创建版本
 */
package com.hd.agent.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 基础公共工具类
 * 
 * @author chenwei
 */
public class CommonUtils {
	public static int decimalLen=2;
	/**
	 * request.getParameterMap()获取到的map转换为string=string的map 支持一对一的form表单提交的数据
	 * 比如select text radio等 不支持checkbox 该方法主要是把查询条件封装在map里面，方便mybatis里使用map查询
	 * 
	 * @param map
	 *            <String, String[]>
	 * @return Map<String, String>
	 */
	public static Map<String, String> changeMap(Map<String, String[]> map) {
		Map<String, String> resultMap = new HashMap<String, String>();
		Set<Entry<String, String[]>> set = map.entrySet();
		Iterator<Entry<String, String[]>> it = set.iterator();
		while (it.hasNext()) {
			Entry<String, String[]> entry = it.next();
			if (entry.getValue().length == 1
					&& !entry.getKey().equals("pageSize")
					&& !entry.getKey().equals("currentPage")) {
				// 值为空的数据不放入map中
				if (null != entry.getValue()[0] && !"".equals(entry.getValue()[0])) {
					if ("sort".equals(entry.getKey())|| "order".equals(entry.getKey())) {
						// 过滤sort order字段的sql注入
						resultMap.put(entry.getKey(), StringEscapeUtils.escapeSql(entry.getValue()[0]));
					} else {
						resultMap.put(entry.getKey(), entry.getValue()[0].trim());
					}
				}
			}else if(entry.getValue().length >1 && !entry.getKey().equals("pageSize")
					&& !entry.getKey().equals("currentPage")){
				//数组转为字符串
				String value = "";
				String key = entry.getKey().replace("[]", "");
				for(String val : entry.getValue()){
					val = StringEscapeUtils.escapeSql(val);
					if("".equals(value)){
						value = val;
					}else{
						value += ","+val;
					}
				}
				resultMap.put(key,value.trim());
			}
		}
		return resultMap;
	}
    /**
     * 过滤有sql注入风险的字符串
     *  request.getParameterMap()获取到的map转换为string=string的map 支持一对一的form表单提交的数据
     * 比如select text radio等 不支持checkbox 该方法主要是把查询条件封装在map里面，方便mybatis里使用map查询
     *
     * @param map
     *            <String, String[]>
     * @return Map<String, String>
     */
    public static Map<String, String> changeMapByEscapeSql(Map<String, String[]> map) {
        Map<String, String> resultMap = new HashMap<String, String>();
        Set<Entry<String, String[]>> set = map.entrySet();
        Iterator<Entry<String, String[]>> it = set.iterator();
        while (it.hasNext()) {
            Entry<String, String[]> entry = it.next();
            if (entry.getValue().length == 1
                    && !entry.getKey().equals("pageSize")
                    && !entry.getKey().equals("currentPage")) {
                // 值为空的数据不放入map中
                if (null != entry.getValue()[0] && !"".equals(entry.getValue()[0])) {
                        // 过滤sort order字段的sql注入
                    resultMap.put(entry.getKey(), StringEscapeUtils.escapeSql(entry.getValue()[0]));
                }
            }else if(entry.getValue().length >1 && !entry.getKey().equals("pageSize")
                    && !entry.getKey().equals("currentPage")){
                //数组转为字符串
                String value = "";
                String key = entry.getKey().replace("[]", "");
                for(String val : entry.getValue()){
                    val = StringEscapeUtils.escapeSql(val);
                    if("".equals(value)){
                        value = val;
                    }else{
                        value += ","+val;
                    }
                }
                resultMap.put(key,value.trim());
            }
        }
        return resultMap;
    }

    /**
     * 过滤有sql注入风险的字符串
     *  request.getParameterMap()获取到的map转换为string=string的map 支持一对一的form表单提交的数据
     * 比如select text radio等 不支持checkbox 该方法主要是把查询条件封装在map里面，方便mybatis里使用map查询
     *
     * @param map
     *            <String, String[]>
     * @return Map<String, String>
     */
    public static Map<String, String> changeAllMapByEscapeSql(Map<String, String[]> map) {
        Map<String, String> resultMap = new HashMap<String, String>();
        Set<Entry<String, String[]>> set = map.entrySet();
        Iterator<Entry<String, String[]>> it = set.iterator();
        while (it.hasNext()) {
            Entry<String, String[]> entry = it.next();
            if (entry.getValue().length == 1
                    && !entry.getKey().equals("pageSize")
                    && !entry.getKey().equals("currentPage")) {
                // 值为空的数据不放入map中
                if (null != entry.getValue()[0] && !"".equals(entry.getValue()[0])) {
                    // 过滤sort order字段的sql注入
                    resultMap.put(entry.getKey(), StringEscapeUtils.escapeSql(entry.getValue()[0]));
                }else if(null==entry.getValue()[0] || "".equals(entry.getValue()[0])){
                    resultMap.put(entry.getKey(), "");
                }
            }else if(entry.getValue().length >1 && !entry.getKey().equals("pageSize")
                    && !entry.getKey().equals("currentPage")){
                //数组转为字符串
                String value = "";
                String key = entry.getKey().replace("[]", "");
                for(String val : entry.getValue()){
                    val = StringEscapeUtils.escapeSql(val);
                    if("".equals(value)){
                        value = val;
                    }else{
                        value += ","+val;
                    }
                }
                resultMap.put(key,value.trim());
            }
        }
        return resultMap;
    }
    /**
     * 将javabean实体类转为map类型，然后返回一个map类型的值
     * @param obj
     * @return
     */
    public static Map<String, Object> beanToMap(Object obj) {
        Map<String, Object> params = new HashMap<String, Object>(0);
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!"class".equals(name)) {
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

	/**
	 * MD5加密
	 * 
	 * @param inStr
	 * @return
	 */
	public static String MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = (md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	/**
	 * 获取客户端IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多个路由时，取第一个非unknown的ip
        String ipString = "";
        String[] arr = ip.split(",");
        for (String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ipString = str;
                break;
            }
        }
        if (ipString.indexOf("0:") != -1) {
            ipString = "本地";
		}
        return ipString;
	}
	/**
	 * 流转二进制
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static byte[] Stream2Byte(InputStream in) throws IOException {
		byte[] temp = new byte[1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		int size = 0;
		while ((size = in.read(temp)) != -1) {
			out.write(temp, 0, size);
		}
		in.close();
		byte[] content = out.toByteArray();
		return content;
	}

	/**
	 * 日期格式转换 yyyy-MM-dd格式转换为yyyyMMdd格式
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String dateStringChange(String dateStr) throws Exception {
		String str = null;
		if (null != dateStr) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sf.parse(dateStr);
			SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
			str = sf1.format(date);
		}
		return str;
	}
	/**
	 * 日期格式转换 yyyy-MM-dd格式转换为format定义的格式
	 * @param dateStr
	 * @param format
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 13, 2013
	 */
	public static String dateStringChange(String dateStr,String format) throws Exception {
		String str = null;
		if (null != dateStr) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sf.parse(dateStr);
			SimpleDateFormat sf1 = new SimpleDateFormat(format);
			str = sf1.format(date);
		}
		return str;
	}
	/**
	 * 日期格式转换 yyyyMMdd格式转换为yyyy-MM-dd格式
	 * 
	 * @param dateStr
	 * @return
	 * @throws Exception
	 */
	public static String dateStringChange1(String dateStr) throws Exception {
		String str = null;
		if (null != dateStr) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			Date date = sf.parse(dateStr);
			SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
			str = sf1.format(date);
		}
		return str;
	}

	/**
	 * 判断字符串是否属于（yyyy-mm-dd）的日期格式
	 * 
	 * @param dataStr
	 * @return
	 * @author chenwei
	 * @date 2013-1-11
	 */
	public static boolean isDataStr(String dataStr) {
		Pattern p = Pattern
				.compile("(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)");
		Matcher m = p.matcher(dataStr);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断字符串是否属于 yyyy-mm或MM-dd或DD hh或h:mm或m:ss或s 的日期格式
	 * 
	 * @param datetimeStr
	 * @return
	 * @author zhanghonghui
	 * @date 2013-1-11
	 */
	public static boolean isDateTimeStr(String datetimeStr) {
		if (StringUtils.isEmpty(datetimeStr.trim())) {
			return false;
		}
		Pattern p = Pattern
				.compile("\\d{4}[-]([1-9]|[0][1-9]|1[0-2])[-]([1-9]|[0-2][0-9]|3[0-1]) ([1-9]|[01][0-9]|2[0-3]):([1-9]|[0-5][0-9]):([1-9]|[0-5][0-9])");
		Matcher m = p.matcher(datetimeStr);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断字符串是否属于 yyyy-MM-DD hh:mm:ss 的日期格式
	 * 
	 * @param datetimeStr
	 * @return
	 * @author zhanghonghui
	 * @date 2013-1-11
	 */
	public static boolean isDateTimeStandStr(String datetimeStr) {
		if (null==datetimeStr || "".equals(datetimeStr.trim())) {
			return false;
		}
		Pattern p = Pattern
				.compile("\\d{4}[-]([0][1-9]|1[0-2])[-]([0-2][0-9]|3[0-1]) ([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])");
		Matcher m = p.matcher(datetimeStr);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 时间转换成字符串格式
	 * 
	 * @param date
	 *            时间
	 * @param format
	 *            格式（yyyy-MM-dd）
	 * @return
	 * @author chenwei
	 * @date 2013-1-15
	 */
	public static String dataToStr(Date date, String format) {
		if (null == format || "".equals(format)) {
			format = "yyyy-MM-dd";
		}
		String dataStr = "";
		SimpleDateFormat sf = new SimpleDateFormat(format);
		dataStr = sf.format(date);
		return dataStr;
	}

	/**
	 * 获取当前时间的所属月份的第一天日期
	 * 
	 * @return
	 * @author chenwei
	 * @date 2013-1-15
	 */
	public static String getNowMonthDay() {
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH) + 1;
		int year = c.get(Calendar.YEAR);
		String mon = "";
		if (month < 10) {
			mon = "0" + month;
		}else{
			mon = month+"";
		}
		String dateStr = year + "-" + mon + "-01";
		return dateStr;
	}
	/**
	 * 获取当前时间的所属月份的第一天日期
	 * @param date
	 * @return
	 * @author chenwei
	 * @date 2013-1-15
	 */
	public static String getNowMonthDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH) + 1;
		int year = c.get(Calendar.YEAR);
		String mon = "";
		if (month < 10) {
			mon = "0" + month;
		}else{
			mon = month+"";
		}
		String resultStr = year + "-" + mon + "-01";
		return resultStr;
	}

	/**
	 * 获取当前时间所属季度的开始日期
	 * 
	 * @return (yyyy-MM-dd)
	 * @author chenwei
	 * @date 2013-1-15
	 */
	public static String getQuarterBeginDate() {
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH) + 1;
		int year = c.get(Calendar.YEAR);
		if (month >= 1 && month <= 3) {
			return year + "-01-01";
		} else if (month >= 4 && month <= 6) {
			return year + "-04-01";
		} else if (month >= 7 && month <= 9) {
			return year + "-07-01";
		} else if (month >= 10 && month <= 12) {
			return year + "-10-01";
		}
		return null;
	}

	/**
	 * 获取当前时间所属季度的开始日期
	 * 
	 * @return (yyyy-MM-dd)
	 * @author chenwei
	 * @date 2013-1-15
	 */
	public static String getQuarterEndDate() {
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH) + 1;
		int year = c.get(Calendar.YEAR);
		if (month >= 1 && month <= 3) {
			return year + "-03-31";
		} else if (month >= 4 && month <= 6) {
			return year + "-6-30";
		} else if (month >= 7 && month <= 9) {
			return year + "-9-30";
		} else if (month >= 10 && month <= 12) {
			return year + "-12-31";
		}
		return null;
	}

	/**
	 * 表名前缀操作<br/>
	 * 处理tablename,如果tablename没有前缀t_,给tablename加上前缀
	 * 
	 * @param tablename
	 * @return
	 * @author chenwei
	 * @date 2013-1-17
	 */
	public static String tablenameDealWith(String tablename) {
		// 判断表名中是否包含前缀t_
		if (!"t_".equals(tablename.substring(0, 2)) && !"RL".equals(tablename.substring(0, 2)) && !"RT".equals(tablename.substring(0, 2))) {
			tablename = "t_" + tablename;
		}
		return tablename;
	}

	/**
	 * 字符串转时间。（yyyy-MM-dd）
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2013-1-30
	 */
	public static Date stringToDate(String str) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sf.parse(str);
		return date;
	}
	
	/**
	 * 字符串转时间,指定日期格式
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2013-1-30
	 */
	public static Date stringToDate(String str,String format) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		Date date = sf.parse(str);
		return date;
	}

	/**
	 * 判断当前日期是星期几<br>
	 * <br>
	 * 
	 * @param day
	 *            修要判断的时间<br>
	 * @return dayForWeek 判断结果<br>
	 * @Exception 发生异常<br>
	 */
	public static String dayForWeek(String day) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		final String dayNames[] = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"}; 
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(day));
		int dayForWeek = 0;
		dayForWeek = c.get(Calendar.DAY_OF_WEEK);
		return dayNames[dayForWeek - 1];
	}
	/**
	 * 对象克隆<br/>
	 * 对数据进行转码时，克隆数据对象 防止影响缓存的数据
	 * @param src
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @author chenwei 
	 * @date Mar 2, 2013
	 */
	public static Object  deepCopy(Object  src) throws IOException, ClassNotFoundException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(src);
		out.close();
		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
		ObjectInputStream in = new ObjectInputStream(byteIn);
		Object  dest =  in.readObject();
		in.close();
		return dest;
	}
	/**
	 * 把list（Map）转成tree格式
	 * @param list
	 * @return
	 * @author chenwei 
	 * @date Mar 1, 2013
	 */
	public static List getTableMapDataTree(List<Map> list){
		for(Map map : list){
			String parentid = (String) map.get("pId");
			if(map.containsKey("url")){
				map.remove("url");
			}
			getTableMapDataTreeChild(map,list);
		}
		return list;
	}
	/**
	 * 递归查找子节点(Map)
	 * @param map
	 * @param list
	 * @author chenwei 
	 * @date Mar 1, 2013
	 */
	public static void getTableMapDataTreeChild(Map map,List<Map> list){
		String isParent = "false";
		for(Map childMap : list){
			String id = (String) map.get("id");
			String parentid = (String) childMap.get("pId");
				if(id.equals(parentid)){
					isParent = "true";
					break;
				}
		}
		if(!map.containsKey("isParent")){
			map.put("isParent", isParent);
		}
	}
	/**
	 * SQL过滤 ${}方式代替字符串传参
	 * 过滤SQL语句使语句转换成正常的SQL
	 * @param sql
	 * @return
	 * @author chenwei 
	 * @date Mar 8, 2013
	 */
	public static String sqlFilter(String sql){
		if(null!=sql){
			sql = StringEscapeUtils.escapeSql(sql);
			Pattern p = Pattern.compile("\\$\\{(.*?)\\}");
			Matcher m = p.matcher(sql);
	        ArrayList<String> params = new ArrayList<String>();
	        while (m.find()) {
	        	params.add(m.group(1));            
	        } 
	        for (String param : params){
	        	sql = sql.replaceAll("\\$\\{"+param+"\\}", "'"+param+"'");
	        }        
			return sql;
		}else{
			return "";
		}
	}
	/**
	 * 获取（yyyyMMddHHmmssSSS）格式的时间字符串（带毫秒）
	 * @return
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public static String getDataNumber(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String str = sf.format(new Date());
		return str;
	}

    /**
     *获取（yyyyMMddHHmmssSSS）格式的时间字符串（带随机数）
     * @return
     */
    public static String getDataNumberWithRand(){
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String str = sf.format(new Date());
        long i = Math.round(Math.random()*899+100);
        str += i;
        return str;
    }
	/**
	 * 获取（yyMMddHHmmssSSS）格式的时间字符串（带毫秒）
	 * @return
	 * @author chenwei 
	 * @date Mar 15, 2013
	 */
	public static String getDataNumber1(){
		SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmssSSS");
		String str = sf.format(new Date());
		return str;
	}
	/**
	 * 获取（yyyyMMddHHmmss）格式的时间字符串（带秒）
	 * @return
	 * @author chenwei 
	 * @date May 24, 2013
	 */
	public static String getDataNumberSeconds(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = sf.format(new Date());
		return str;
	}
	/**
	 * 获取当期毫秒数字符串 + 3位随机数
	 * @return
	 * @author chenwei 
	 * @date May 30, 2013
	 */
	public static String getDataNumberSendsWithRand(){
//		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
//		String str = sf.format(new Date());
		String s = System.currentTimeMillis()+"";
		String str=s.substring(3);
		long i = Math.round(Math.random()*899+100);
		str  = str + i;
		return str;
	}
	/**
	 * 获取（yyyyMMddHHmmssSSS）格式的时间字符串（带毫秒）+3位随机数
	 * @return
	 * @author chenwei 
	 * @date 2015年2月7日
	 */
	public static String getDateNowTimeWithRand(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String s = sf.format(new Date());
		String str=s.substring(7);
		long i = Math.round(Math.random()*899+100);
		str  = str + i;
		return str;
	}
	/**
	 * 获取今天日期
	 * @return
	 * @author chenwei 
	 * @date Jul 11, 2013
	 */
	public static String getTodayDataStr(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sf.format(new Date());
		return str;
	}
	/**
	 * 获取当前年月（yyyy-MM）
	 * @return
	 * @author chenwei 
	 * @date Apr 1, 2014
	 */
	public static String getTodayMonStr(){
		SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM");
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		String str = smf.format(now);
		return str;
	}
	/**
	 * 获取当前年第一个月
	 * @return
	 * @author chenwei 
	 * @date Apr 1, 2014
	 */
	public static String getCurrentYearFirstMonStr(){
		SimpleDateFormat smf = new SimpleDateFormat("yyyy");
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		String str = smf.format(now);
		if(null!=str && !"".equals(str)){
			str=str+"-01";
		}
		return str;
	}

	/**
	 * 获取上一个月的日期，格式年月
	 * @return
	 * @author chenwei 
	 * @date Jul 15, 2013
	 */
	public static String getCurrentYearLastMonStrMonStr(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.MONTH,-1);
		date=calendar.getTime();
		String str = sf.format(date);
		return str;
	}

    /**
     * 获取 当前日期的上一个月日期
     * @return
     * @author panxiaoxiao
     * @date 2015-12-11
     */
    public static String getCurrentDateLastMonDate(){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.MONTH,-1);
        date=calendar.getTime();
        String str = sf.format(date);
        return str;
    }

	/**
	 * 当前年份
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-4
	 */
	public static String getCurrentYearStr(){
		SimpleDateFormat smf = new SimpleDateFormat("yyyy");
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		String str = smf.format(now);
		return str;
	}
	/**
	 * 去年年份
	 * @return
	 * @author zhanghonghui
	 * @date 2014-7-4
	 */
	public static String getLastYearStr(){
		SimpleDateFormat smf = new SimpleDateFormat("yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.YEAR,-1);
		Date now = calendar.getTime();
		String str = smf.format(now);
		return str;
	}
	/**
	 * 获取当前月份
	 * @return
	 * @author chenwei 
	 * @date 2014年12月5日
	 */
	public static String getCurrentMonthStr(){
		SimpleDateFormat smf = new SimpleDateFormat("MM");
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		String str = smf.format(now);
		return str;
	}
    /**
     * 根据日期字符串获取该日期的月份
     * @param datestr
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-04-01
     */
    public static String getMonthStr(String datestr){
        String str = "";
        try {
            SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
            Date date =smf.parse(datestr);
            SimpleDateFormat smf1 = new SimpleDateFormat("MM");
            str = smf1.format(date);
        }catch (Exception e){
            return "";
        }
        return str;
    }

    /**
     * 根据日期字符串获取该日期的年
     * @param datestr
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-04-01
     */
    public static String getYearStr(String datestr){
        String str = "";
        try {
            SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
            Date date =smf.parse(datestr);
            SimpleDateFormat smf1 = new SimpleDateFormat("yyyy");
            str = smf1.format(date);
        }catch (Exception e){
            return "";
        }
        return str;
    }

	/**
	 * 获取昨天日期
	 * @return
	 * @author chenwei 
	 * @date Jul 15, 2013
	 */
	public static String getYestodayDateStr(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
		date=calendar.getTime();
		String str = sf.format(date);
		return str;
	}
	/**
	 * 获取昨天日期
	 * @return
	 * @author chenwei 
	 * @date Jul 15, 2013
	 */
	public static Date getYestodayDate(){
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
		date=calendar.getTime();
		return date;
	}
	/**
	 * 获取传入的时间的前一天
	 * @return
	 * @author chenwei 
	 * @date Jul 15, 2013
	 */
	public static Date getYestodayDate(Date date){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.setTime(date);
		calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
		date=calendar.getTime();
		return date;
	}
	/**
	 * 获取特定日期的前一天日期
	 * @param date
	 * @return
	 * @author chenwei 
	 * @date Aug 14, 2013
	 */
	public static String getYestodayByDate(String date){
		String str = null;
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			Date dateObject = sf.parse(date);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(dateObject);
			calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
			dateObject=calendar.getTime();
			str = sf.format(dateObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	/**
	 * 将字符串型业务日期转为日期型
	 * @param dateStr
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-1-7
	 */
	public static Date getBusinessdateByString(String dateStr){
		Date date=null;
		if(null!=dateStr && !"".equals(dateStr.trim())){
			try {
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				date = sf.parse(dateStr.trim());
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		return date;
	}
	/**
	 * 获取特定日期的下一天日期
	 * @param date
	 * @return
	 * @author chenwei 
	 * @date Aug 14, 2013
	 */
	public static String getNextDayByDate(String date){
		String str = null;
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			Date dateObject = sf.parse(date);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(dateObject);
			calendar.add(calendar.DATE,+1);//把日期往后增加一天.整数往后推,负数往前移动
			dateObject=calendar.getTime();
			str = sf.format(dateObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	/**
	 * 获取当前日期 前几天的日期
	 * @param days
	 * @return
	 * @author chenwei 
	 * @date Aug 16, 2013
	 */
	public static String getBeforeDateInDays(int days){
		String str = null;
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			Date dateObject = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(dateObject);
			//把日期往后增加一天.整数往后推,负数往前移动
			calendar.add(calendar.DATE,-days);
			dateObject=calendar.getTime();
			str = sf.format(dateObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
    /**
     * 获取当前日期 前几个月的日期
     * @param month
     * @return
     * @author chenwei
     * @date Aug 16, 2013
     */
    public static String getBeforeDateInMonth(int month){
        String str = null;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateObject = new Date();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(dateObject);
            //把日期往后增加一天.整数往后推,负数往前移动
            calendar.add(calendar.MONTH,-month);
            dateObject=calendar.getTime();
            str = sf.format(dateObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
	/**
	 * 获取当前日期 前几天的日期
	 * @param date
	 * @param days
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月17日
	 */
	public static String getBeforeDateInDays(Date date,int days){
		String str = null;
		try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			//把日期往后增加一天.整数往后推,负数往前移动
			calendar.add(calendar.DATE,-days);
			Date afDate=calendar.getTime();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			str = sf.format(afDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 获取当前日期 前几天的日期 ,返回类型为Date
	 * @param date
	 * @param days
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月17日
	 */
	public static Date getBeforeTheDateInDays(Date date,int days){
		Date theDate = null;
		try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			//把日期往后增加一天.整数往后推,负数往前移动
			calendar.add(calendar.DATE,-days);
			theDate=calendar.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return theDate;
	}
	/**
	 * 获取当前日期 后几天的日期，返回字符串格式yyyy-MM-dd
	 * @param date
	 * @param days
	 * @return
	 * @author zhanghonghui
	 * @date 2015年3月17日
	 */
	public static String getAfterDateInDays(Date date,int days){
		String str = null;
		try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			//把日期往后增加一天.整数往后推
			calendar.add(calendar.DATE,days);
			Date afDate=calendar.getTime();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			str = sf.format(afDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 获取本月第一天
	 * @return
	 * @author chenwei 
	 * @date Jul 18, 2013
	 */
	public static String getMonthDateStr(){
//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//		Calendar c = Calendar.getInstance();
//		c.add(Calendar.MONTH, -1);    //得到前一个月
//		Date date = c.getTime();
//		String str = sf.format(date);
		return getNowMonthDay();
	}
	/**
	 * 过滤html标签
	 * @param html
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-15
	 */
	public static String htmlFilter(String html){
		if(StringUtils.isEmpty(html)){
			return "";
		}
		String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script>      
        String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style>      
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式      
        String regEx_html1 = "<[^>]+";      
        Pattern pattern =null; 
        Matcher matcher =null;
        pattern= Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE); 
        if(pattern!=null){
        	matcher = pattern.matcher(html);
        	if(matcher!=null){
        		html = matcher.replaceAll(""); // 过滤script标签
        	}
        }
        pattern = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        if(pattern!=null){
        	matcher = pattern.matcher(html);
        	if(matcher!=null){
        		html = matcher.replaceAll(""); // 过滤style标签  
        	}
        }
        pattern = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        if(pattern!=null){
        	matcher = pattern.matcher(html);
        	if(matcher!=null){
        		html = matcher.replaceAll(""); // 过滤html标签
        	}
        }        
        pattern = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
        if(pattern!=null){
        	matcher = pattern.matcher(html);
        	if(matcher!=null){
        		html = matcher.replaceAll(""); // 过滤html标签
        	}
        }
        return html;
	}
	/**
	 * 根据时间获取quartz表达式.
	 * 精确到分钟
	 * @param date
	 * @return
	 * @author chenwei 
	 * @date Mar 19, 2013
	 */
	public static String getQuartzCronExpression(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String year = ""+calendar.get(Calendar.YEAR);
		String month = ""+ (calendar.get(Calendar.MONTH)+1);
		String day = "" + calendar.get(Calendar.DAY_OF_MONTH);
		String hour = "" +calendar.get(Calendar.HOUR_OF_DAY);
		String min = "" + calendar.get(Calendar.MINUTE);
		String sen = "" + calendar.get(Calendar.SECOND);
		String con = sen +" "+min+" "+hour+" "+day+" "+month+" ? "+year;
		return con;
	}
	
	/**
	 * 判断表达式是否验证通过
	 * 
	 * @param pattern
	 * @param validstr
	 * @return
	 * @author zhanghonghui
	 * @date 2013-1-11
	 */
	public static boolean isPatternValid(String pattern,String validstr) {
		if (StringUtils.isEmpty(pattern) || StringUtils.isEmpty(validstr)) {
			return false;
		}
		Pattern p = Pattern
				.compile(pattern);
		Matcher m = p.matcher(validstr);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	
	/**
	 * 获取当前时间的前一个年度的本月第一天日期
	 * 
	 * @return
	 * @author chenwei
	 * @date 2013-1-15
	 */
	public static String getPrevYearFirstMonthDay() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		year=year-1;
		c.set(Calendar.YEAR,year);
		int month = c.get(Calendar.MONTH)+1;
		String mon = "";
		if (month < 10) {
			mon = "0" + month;
		}else{
			mon = month+"";
		}
		String dateStr = year + "-" + mon + "-01";
		return dateStr;
	}
	/**
	 *  获取当前时间的前一个年度的本月第一天日期
	 * @param date
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-15
	 */
	public static String getPrevYearFirstMonthDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		year=year-1;
		c.set(Calendar.YEAR,year);
		int month = c.get(Calendar.MONTH)+1;
		String mon = "";
		if (month < 10) {
			mon = "0" + month;
		}else{
			mon = month+"";
		}
		String dateStr = year + "-" + mon + "-01";
		return dateStr;
	}
	/**
	 * 获取当前时间的前一个年度的本月同一天日期
	 * 
	 * @return
	 * @author chenwei
	 * @date 2013-1-15
	 */
	public static String getPrevYearCurrentMonthDay() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		year=year-1;
		c.set(Calendar.YEAR,year);
		int month = c.get(Calendar.MONTH)+1;
		int day=c.get(Calendar.DATE);
		String mon = "";
		String days="";
		if (month < 10) {
			mon = "0" + month;
		}else{
			mon = month+"";
		}
		if(day<10){
			days="0"+day;
		}else{
			days=day+"";
		}
		String dateStr = year + "-" + mon + "-"+days;
		return dateStr;
	}
	/**
	 * 获取当前时间的前一个年度的本月同一天日期
	 * 
	 * @return
	 * @author chenwei
	 * @date 2013-1-15
	 */
	public static String getPrevYearCurrentMonthDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		year=year-1;
		c.set(Calendar.YEAR,year);
		int month = c.get(Calendar.MONTH)+1;
		int day=c.get(Calendar.DATE);
		String mon = "";
		String days="";
		if (month < 10) {
			mon = "0" + month;
		}else{
			mon = month+"";
		}
		if(day<10){
			days="0"+day;
		}else{
			days=day+"";
		}
		String dateStr = year + "-" + mon + "-"+days;
		return dateStr;
	}
	/**
	 * 获取当前时间的前一个年度的本月最后一天日期
	 * 
	 * @return
	 * @author chenwei
	 * @date 2013-1-15
	 */
	public static String getPrevYearCurrentMonthLastDay() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		year=year-1;
		c.set(Calendar.YEAR,year);
		int month = c.get(Calendar.MONTH)+1;
		int day=c.getActualMaximum(Calendar.DAY_OF_MONTH);  
		String mon = "";
		String days="";
		if (month < 10) {
			mon = "0" + month;
		}else{
			mon = month+"";
		}
		if(day<10){
			days="0"+day;
		}else{
			days=day+"";
		}
		String dateStr = year + "-" + mon + "-"+days;
		return dateStr;
	}
	/**
	 * 获取当前时间的前一个年度的本月同一天日期
	 * 
	 * @return
	 * @author chenwei
	 * @date 2013-1-15
	 */
	public static String getCurrentMonthLastDay() {
		Calendar c = Calendar.getInstance();
		int mon = c.get(Calendar.MONTH);
		c.set(Calendar.MONTH,mon);
		int month = c.get(Calendar.MONTH)+1;
		int year =  c.get(Calendar.YEAR);
		String monstr = "";
		if (month < 10) {
			monstr = "0" + month;
		}else{
			monstr = month+"";
		}
		int day =  c.getActualMaximum(Calendar.DAY_OF_MONTH);  
		String daystr = "";
		if(day< 10){
			daystr = "0" + day;
		}else{
			daystr = day+"";
		}
		String dateStr = year + "-" + monstr +"-"+ daystr;
		return dateStr;
	}
	/**
	 * 获取上个月第一天日期
	 * @return
	 * @author chenwei 
	 * @date Nov 28, 2013
	 */
	public static String getPreMonthFirstDay(){
		Calendar c = Calendar.getInstance();
		int mon = c.get(Calendar.MONTH);
		mon=mon-1;
		c.set(Calendar.MONTH,mon);
		int month = c.get(Calendar.MONTH)+1;
		int year =  c.get(Calendar.YEAR);
		String monstr = "";
		if (month < 10) {
			monstr = "0" + month;
		}else{
			monstr = month+"";
		}
		String dateStr = year + "-" + monstr + "-01";
		return dateStr;
	}
	/**
	 * 获取上个月第一天日期
	 * @return
	 * @author chenwei 
	 * @date Nov 28, 2013
	 */
	public static String getPreMonthFirstDay(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int mon = c.get(Calendar.MONTH);
		mon=mon-1;
		c.set(Calendar.MONTH,mon);
		int month = c.get(Calendar.MONTH)+1;
		int year =  c.get(Calendar.YEAR);
		String monstr = "";
		if (month < 10) {
			monstr = "0" + month;
		}else{
			monstr = month+"";
		}
		String dateStr = year + "-" + monstr + "-01";
		return dateStr;
	}
	/**
	 * 获取前一个月的日期
	 * @return
	 * @author chenwei 
	 * @date Jul 15, 2013
	 */
	public static String getPrevMonthDay(Date date){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.MONTH,-1);//把月往前移一个月
		date=calendar.getTime();
		String str = sf.format(date);
		return str;
	}
	/**
	 * 获取下一个月的日期
	 * @return
	 * @author chenwei 
	 * @date Jul 15, 2013
	 */
	public static String getNextDateByDays(Date date,int days){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE,days);
		date=calendar.getTime();
		String str = sf.format(date);
		return str;
	}

    /**
     * 获取两个日期之间的天数
     * @param smdate     较小日期
     * @param bdate      较大日期
     * @return
     */
	public static int daysBetween(Date smdate,Date bdate) throws Exception{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }
	/**
	 * 获取下一个月的日期
	 * @return
	 * @author chenwei 
	 * @date Jul 15, 2013
	 */
	public static String getNextMonthDay(Date date){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.MONTH,1);//把月往后移一个月
		date=calendar.getTime();
		String str = sf.format(date);
		return str;
	}
	/**
	 * 根据月间隔，获取几个月间隔的日期,当间隔为负为向前几个月，当间隔为正时，向后几个月
	 * @return
	 * @author chenwei 
	 * @date Jul 15, 2013
	 */
	public static String getPrevMonthDay(Date date,int monthinter){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.MONTH,monthinter);//把月往前移一个月
		date=calendar.getTime();
		String str = sf.format(date);
		return str;
	}
	/**
	 * 获取上个月最后一天日期
	 * @return
	 * @author chenwei 
	 * @date Nov 28, 2013
	 */
	public static String getPreMonthLastDay(){
		Calendar c = Calendar.getInstance();
		int mon = c.get(Calendar.MONTH);
		mon=mon-1;
		c.set(Calendar.MONTH,mon);
		int month = c.get(Calendar.MONTH)+1;
		int year =  c.get(Calendar.YEAR);
		String monstr = "";
		if (month < 10) {
			monstr = "0" + month;
		}else{
			monstr = month+"";
		}
		int day =  c.getActualMaximum(Calendar.DAY_OF_MONTH);  
		String daystr = "";
		if(day< 10){
			daystr = "0" + day;
		}else{
			daystr = day+"";
		}
		String dateStr = year + "-" + monstr +"-"+ daystr;
		return dateStr;
	}
	/**
	 * 获取上个月的年度
	 * @return
	 * @author chenwei 
	 * @date Nov 28, 2013
	 */
	public static int getPreMonthYear(){
		Calendar c = Calendar.getInstance();
		int mon = c.get(Calendar.MONTH);
		mon=mon-1;
		c.set(Calendar.MONTH,mon);
		int month = c.get(Calendar.MONTH)+1;
		int year =  c.get(Calendar.YEAR);
		return year;
	}
	/**
	 * 获取上个月的月份
	 * @return
	 * @author chenwei 
	 * @date Nov 28, 2013
	 */
	public static int getPreMonth(){
		Calendar c = Calendar.getInstance();
		int mon = c.get(Calendar.MONTH);
		mon=mon-1;
		c.set(Calendar.MONTH,mon);
		int month = c.get(Calendar.MONTH)+1;
		int year =  c.get(Calendar.YEAR);
		return month;
	}
	/**
	 * 获取当期天数
	 * @return
	 * @author chenwei 
	 * @date Jan 6, 2014
	 */
	public static int getNowDay(){
		Calendar c = Calendar.getInstance();
		int day =  c.get(Calendar.DAY_OF_MONTH);
		return day;
	}
	/**
	 * 获取该时间是属于当月第几天
	 * @return
	 * @author chenwei 
	 * @date Mar 22, 2014
	 */
	public static int getDataMonthDay(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day =  c.get(Calendar.DAY_OF_MONTH);
		return day;
	}
	/**
	 * 判断字符串是否数字
	 * @param numberstr
	 * @return
	 * @author chenwei 
	 * @date Nov 28, 2013
	 */
	public static boolean isNumStr(String numberstr){
		return numberstr.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	/**
	 * 中文名称转拼音首字母
	 * @param name
	 * @return
	 * @author chenwei 
	 * @date Dec 3, 2013
	 */
	public static String getPinYingJC(String name){
        char[] t1 = null;
		t1 = name.toCharArray();
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String py = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				//判断是否为汉字字符 
				if (java.lang.Character.toString(t1[i]).matches(
						"[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					if (t2[0] != null) {
						py += t2[0].substring(0, 1);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
        return py.toLowerCase();
    }
	/**
	 * 获取这个月的最后一天的日期
	 * @param date
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-8-1
	 */
	public static Date getCurrentMonthLastDate(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, c.getMaximum(Calendar.DATE)); 
		return c.getTime();
	}
	
	/**
	 * 获取月的最后一天日期
	 * @param date
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 20, 2014
	 */
	public static Date getLastDayOfMonth(Date date){
		Calendar cDay1 = Calendar.getInstance();  
        cDay1.setTime(date);  
        final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);  
        Date lastDate = cDay1.getTime();  
        lastDate.setDate(lastDay);
        return lastDate;
	}
	/**
	 * 获取中文拼音简称，超过50位取50位字母
	 * @param name
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 3, 2013
	 */
	public static String getPinYingJCLen(String name) throws Exception{
		String pyname = getPinYingJC(name);
		if(pyname.length()>=50){
			pyname = pyname.substring(0, 50);
		}
		return pyname;
	}
	
	  /** 
	 * 将url参数转换成map 
	 * @param param aa=11&bb=22&cc=33 
	 * @return 
	 */  
	public static Map<String, Object> getUrlParams(String param) {  
	    Map<String, Object> map = new HashMap<String, Object>(0);  
	    if (StringUtils.isBlank(param)) {  
	        return map;  
	    }  
	    String[] params = param.split("&");  
	    for (int i = 0; i < params.length; i++) {  
	        String[] p = params[i].split("=");  
	        if (p.length == 2) {  
	            map.put(p[0], p[1]);  
	        }  
	    }  
	    return map;  
	}  
	  
	/** 
	 * 将map转换成url 
	 * @param map 
	 * @return 
	 */  
	public static String getUrlParamsByMap(Map<String, Object> map) {  
	    if (map == null) {  
	        return "";  
	    }  
	    StringBuffer sb = new StringBuffer();  
	    for (Map.Entry<String, Object> entry : map.entrySet()) {  
	        sb.append(entry.getKey() + "=" + entry.getValue());  
	        sb.append("&");  
	    }  
	    String s = sb.toString();  
	    if (s.endsWith("&")) {  
	        s = StringUtils.substringBeforeLast(s, "&"); 
	    }  
	    return s;  
	}  
	
	 /** 
	 * 将打印回调url参数转换成map 
	 * @param param aa＝11＆bb＝22＆cc＝33 ，其中＆＝是中文符号
	 * @return 
	 */  
	public static Map<String, Object> getAgprintUrlParams(String param) {  
	    Map<String, Object> map = new HashMap<String, Object>(0);  
	    if (StringUtils.isBlank(param)) {  
	        return map;  
	    }
	    param=URLDecoder.decode(param);
	    String[] params = param.split("＆");  
	    for (int i = 0; i < params.length; i++) {  
	        String[] p = params[i].split("＝");  
	        if (p.length == 2) {  
	            map.put(p[0], p[1]);  
	        }  
	    }  
	    return map;  
	}  
	  
	/** 
	 * 将打印回调map转换成url 其中＆＝是中文符号
	 * @param map 
	 * @return 
	 */  
	public static String getAgprintUrlParamsByMap(Map<String, Object> map) {  
	    if (map == null) {  
	        return "";  
	    }  
	    StringBuffer sb = new StringBuffer();  
	    for (Map.Entry<String, Object> entry : map.entrySet()) {  
	        sb.append(entry.getKey() + "＝" + entry.getValue());  
	        sb.append("＆");  
	    }  
	    String s = sb.toString();  
	    if (s.endsWith("＆")) {  
	        s = StringUtils.substringBeforeLast(s, "＆"); 
	    }
	    s=URLEncoder.encode(s);
	    return s;  
	}
	/**
	 * 返回带时间的随机字符串，到SS
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author zhanghonghui
	 * @date Nov 15, 2016
	 */
	public static String getRandomWithTime(){
		String result="";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddmmssSS");
		Random rand = new Random();
		int ird=rand.nextInt();
		result=sdf.format(date)+ird;
		return result;
	}

	/**
	 * 返回带时间的随机字符串，到秒
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author zhanghonghui
	 * @date Nov 15, 2016
	 */
	public static String getRandomWithSecondTime(){
		String result="";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddmmss");
		Random rand = new Random();
		int ird=rand.nextInt();
		result=sdf.format(date)+ird;
		return result;
	}
	/**
	 * 获取指定月份天数
	 * @param mon
	 * @return
	 * @author chenwei 
	 * @date Mar 10, 2014
	 */
	public static int getDayOfMonth(int mon){
		Calendar   calendar   =   Calendar.getInstance();   
		calendar.set(calendar.get(Calendar.YEAR),mon-1,1);   
		int day= calendar.getActualMaximum(Calendar.DATE);
		return day;
	}

	/**
	 * 数字转中文金额
	 * @param n
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-3-25
	 */
	public static String AmountUnitCnChange(double n){
		String fraction[] = {"角", "分"};
	    String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
	    String unit[][] = {{"元", "万", "亿"},
	                 {"", "拾", "佰", "仟"}};

	    String head = n < 0? "负": "";
	    n = Math.abs(n);
	    
	    String s = "";
	    for (int i = 0; i < fraction.length; i++) {
	    	BigDecimal tmpD=new BigDecimal(n * 10 * Math.pow(10, i));
	    	tmpD=tmpD.setScale(2, BigDecimal.ROUND_HALF_UP);
	        s += (digit[(int)(Math.floor(tmpD.doubleValue()) % 10)] + fraction[i]).replaceAll("(零.)+", "");
	    }
	    if(s.length()<1){
		    s = "整";	
	    }
	    int integerPart = (int)Math.floor(n);

	    for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
	        String p ="";
	        for (int j = 0; j < unit[1].length && integerPart > 0; j++) {
	            p = digit[integerPart%10]+unit[1][j] + p;
	            integerPart = integerPart/10;
	        }
	        s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
	    }
	    return head + s.replaceAll("(零.)*零元", "元").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
	}

	// 过滤特殊字符
	public static String StringFilter(String str) {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
//		String regEx = "[`~!@#$%^&*()+=|{}'\":'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		String regEx = "[`~!@#$%^&*()+=|{}'\":\\[\\]<>/?~！@#￥%……&*（）——+|{}]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

    /**
     * 过滤除数字以外的字符串
     * @param str
     */
    public static String StringFilterJustNumber(String str){
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

	/**
	 * 特殊字符串转义
	 * @param str
	 * @return
	 * @author chenwei 
	 * @date 2014年5月23日
	 */
	public static String escapeStr(String str){
		if(null!=str){
			str = StringFilter(str);
		}
		return str;
	}
	/**
	 * 判断IP地址是否在IP端内
	 * @param ipSection		ip段（192.168.1.1-192.168.1.100）
	 * @param ip			验证ip地址
	 * @return
	 * @author chenwei 
	 * @date 2014年7月25日
	 */
	public static boolean ipIsValid(String ipSection, String ip) {
		if (ipSection == null)
			throw new NullPointerException("IP段不能为空！");
		if (ip == null)
			throw new NullPointerException("IP不能为空！");
		ipSection = ipSection.trim();
		ip = ip.trim();
		final String REGX_IP = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)";
		final String REGX_IPB = REGX_IP + "\\-" + REGX_IP;
		if (!ipSection.matches(REGX_IPB) || !ip.matches(REGX_IP))
			return false;
		int idx = ipSection.indexOf('-');
		String[] sips = ipSection.substring(0, idx).split("\\.");
		String[] sipe = ipSection.substring(idx + 1).split("\\.");
		String[] sipt = ip.split("\\.");
		long ips = 0L, ipe = 0L, ipt = 0L;
		for (int i = 0; i < 4; ++i) {
			ips = ips << 8 | Integer.parseInt(sips[i]);
			ipe = ipe << 8 | Integer.parseInt(sipe[i]);
			ipt = ipt << 8 | Integer.parseInt(sipt[i]);
		}
		if (ips > ipe) {
			long t = ips;
			ips = ipe;
			ipe = t;
		}
        return ips <= ipt && ipt <= ipe;
	}
	
	/**
	 * 将null的字符串转换为""，如果原字符串不为null，则返回原字符串
	 * @param src 原字符串
	 * @return 非null字符串
	 * @author limin 
	 * @date 2014-10-10
	 */
	public static String nullToEmpty(String src) {
		
		if(src == null) {
			return "";
		}
		return src;
	}

	/**
	 * 将空字符串转换为null，如果原字符串不为空（""），则返回原字符串
	 * @param src 原字符串
	 * @return 非空字符串
	 * @author limin 
	 * @date 2014-11-20
	 */
	public static String emptyToNull(String src) {
		
		if("".equals(src)) {
			
			return null;
		}
		return src;
	}
	/**
	 * 计算两个日期之间 间隔的天数
	 * @param smdate
	 * @param bdate
	 * @return
	 * @author chenwei 
	 * @date 2014年12月25日
	 */
	public static int daysBetween(String smdate, String bdate){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(smdate));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(bdate));
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);
			return Integer.parseInt(String.valueOf(between_days));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

    /**
     * 比较两个日期的大小
     * @param datestr1
     * @param datestr2
     * @return 1：datestr1>datestr2,-1：datestr1<datestr2,0：datestr1=datestr2
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-04-01
     */
    public static int compareDate(String datestr1,String datestr2){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(datestr1);
            Date dt2 = df.parse(datestr2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * map对象转换为Object对象
     * @param map
     * @param class1
     * @param <T>
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws ParseException
     * @author limin
     * @date Apr 23, 2015
     */
    public static <T> T mapToObject(HashMap<String, Object> map, Class<T> class1)
            throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, ParseException {

        Field[] fields = class1.getDeclaredFields();
        T t = null;
        if (fields.length > 0) {

            t = class1.newInstance();
        }

        boolean flag;       // 该字段是否可访问，private/protected/public
        for (Field field : fields) {

            if (map.containsKey(field.getName()) && map.get(field.getName()) != null) {

                flag = false;
                // 不可访问？
                if (!field.isAccessible()) {

                    field.setAccessible(true);
                    flag = true;
                }

                // 时间类型的转换
                if ((field.getType() == java.util.Date.class || field.getType() == java.sql.Date.class) && map.get(field.getName()).getClass() != field.getType()) {

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    field.set(t, format.parse((String) map.get(field.getName())));

                // Timestamp转换
                } else if (field.getType() == java.sql.Timestamp.class && map.get(field.getName()).getClass() != field.getType()) {

                    field.set(t, java.sql.Timestamp.valueOf((String) map.get(field.getName())));

                // Long
                } else if (field.getType() == java.lang.Long.class && map.get(field.getName()).getClass() != field.getType()) {

                    field.set(t, Long.valueOf((String) map.get(field.getName())));

                // Integer
                } else if (field.getType() == java.lang.Integer.class && map.get(field.getName()).getClass() != field.getType()) {

                    field.set(t, Integer.parseInt((String) map.get(field.getName())));

                // int
                } else if ((field.getType() == int.class || field.getType() == java.lang.Integer.class) && map.get(field.getName()).getClass() != field.getType()) {

                    field.set(t, Integer.parseInt((String) map.get(field.getName())));

                // String
                } else {

                    field.set(t, map.get((String) field.getName()));
                }

                // 还原访问属性
                if (flag) {
                    field.setAccessible(false);
                }
            }
        }

        return t;
    }

    /**
     * 将byte数组的数据转换为指定编码的字符串
     * @param src 源数据（byte[]类型）
     * @param encode 文字编码（UTF-8、GB2312等）
     * @return
     * @author limin
     * @date May 20, 2015
     */
    public static String bytes2String(byte[] src, String... encode) throws Exception {

        String encode2 = "UTF-8";

        // 参数不正确
        if(encode.length > 1) {

            throw new Exception("Parameter is illegal!");
        }

        // 源数据为空
        if(src == null || src.length == 0) {

            return "";
        }

        if(encode == null || encode.length == 0) {

        } else {

            encode2 = encode[0];
        }

        return new String(src, encode2);
    }

    /**
     * 复制文件
     * S：phone,M:imgM,L:imgL
     * @author panxiaoxiao
     * @date 2015-06-23
     */
    public static void fileChannelCopy(File s, File t) throws Exception {
        File parent = t.getParentFile();
        if(parent!=null&&!parent.exists()){
            parent.mkdirs();
        }
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t);
            in = fi.getChannel();//得到对应的文件通道
            out = fo.getChannel();//得到对应的文件通道
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fi.close();
            in.close();
            fo.close();
            out.close();
        }
    }
    
    /**
     * 对List对象按照某个成员变量进行排序
     * @param list       List对象
     * @param sortField  排序的属性名称
     * @param sortMode   排序方式：ASC，DESC 任选其一
     */
    public static <T> void sortList(List<T> list, final String sortField, final String sortMode) {
        if(list == null || list.size() < 2) {
            return;
        }
        Collections.sort(list, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                try {
                    Class clazz = o1.getClass();
                    Field field = clazz.getDeclaredField(sortField); //获取成员变量
                    field.setAccessible(true); //设置成可访问状态
                    String typeName = field.getType().getName().toLowerCase(); //转换成小写

                    Object v1 = field.get(o1); //获取field的值
                    Object v2 = field.get(o2); //获取field的值

                    boolean ASC_order = (sortMode == null || "ASC".equalsIgnoreCase(sortMode));

                    //判断字段数据类型，并比较大小
                    if (typeName.endsWith("string")) {
                        String value1 = v1.toString();
                        String value2 = v2.toString();
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
                    } else if (typeName.endsWith("short")) {
                        Short value1 = Short.parseShort(v1.toString());
                        Short value2 = Short.parseShort(v2.toString());
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
                    } else if (typeName.endsWith("byte")) {
                        Byte value1 = Byte.parseByte(v1.toString());
                        Byte value2 = Byte.parseByte(v2.toString());
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
                    } else if (typeName.endsWith("char")) {
                        Integer value1 = (int) (v1.toString().charAt(0));
                        Integer value2 = (int) (v2.toString().charAt(0));
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
                    } else if (typeName.endsWith("int") || typeName.endsWith("integer")) {
                        Integer value1 = Integer.parseInt(v1.toString());
                        Integer value2 = Integer.parseInt(v2.toString());
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
                    } else if (typeName.endsWith("long")) {
                        Long value1 = Long.parseLong(v1.toString());
                        Long value2 = Long.parseLong(v2.toString());
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
                    } else if (typeName.endsWith("float")) {
                        Float value1 = Float.parseFloat(v1.toString());
                        Float value2 = Float.parseFloat(v2.toString());
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
                    } else if (typeName.endsWith("double")) {
                        Double value1 = Double.parseDouble(v1.toString());
                        Double value2 = Double.parseDouble(v2.toString());
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
                    } else if (typeName.endsWith("boolean")) {
                        Boolean value1 = Boolean.parseBoolean(v1.toString());
                        Boolean value2 = Boolean.parseBoolean(v2.toString());
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
                    } else if (typeName.endsWith("date")) {
                        Date value1 = (Date) (v1);
                        Date value2 = (Date) (v2);
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
                    } else if (typeName.endsWith("timestamp")) {
                        java.sql.Timestamp value1 = (java.sql.Timestamp) (v1);
                        java.sql.Timestamp value2 = (java.sql.Timestamp) (v2);
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);
                    } else {
                        //调用对象的compareTo()方法比较大小
                        Method method = field.getType().getDeclaredMethod("compareTo", new Class[]{field.getType()});
                        method.setAccessible(true); //设置可访问权限
                        int result = (Integer) method.invoke(v1, new Object[]{v2});
                        return ASC_order ? result : result * (-1);
                    }
                } catch (Exception e) {
                    String err = e.getLocalizedMessage();
                    System.out.println(err);
                    e.printStackTrace();
                }

                return 0; //未知类型，无法比较大小
            }
        });
    }

    /**
     * 获取文件编码格式
     *
     * @param sourceFile
     * @return
     * @throws Exception
     * @author limin
     * @date Jun 1, 2016
     */
    public static String getFileCharset(File sourceFile) throws Exception {
		return FileUtils.getFileEncode(sourceFile);
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    /**
     * 处理字符串中的小数位，去掉小数位中多余的0
     * (1箱3.54000瓶 -->1箱3.54瓶 )
     * @param s
     * @return
     */
    public static String strDigitNumDeal(String s){
        if(StringUtils.isNotEmpty(s)){
            Pattern p = Pattern.compile("\\d+(\\.\\d+)?");
            Matcher m = p.matcher(s);
            String str = s ;
            while (m.find()) {
                String numStr = m.group();
                String dNumStr = subZeroAndDot(new String(numStr));
                str = str.replaceAll(numStr,dNumStr);
            }
            return str;
        }else{
            return s;
        }
    }
	/**
	 * 判断字符串是否为整数
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str){
		if (str != null && !"".equals(str.trim())) {
			return str.trim().matches("^[0-9]*$");
		} else {
			return false;
		}
	}

	/**
	 * 判断字符串是否为数字
	 * （-11、11、1.11、-0.11）
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false;
		}
		return true;
	}
    /**
     * 判断字符串整数或者浮点数
     * （-11、11、1.11、-0.11）
     * @param str
     * @return
     */
    public static boolean isNumericDig(String str){
        //正则匹配是否整数
        Pattern pattern = Pattern.compile("-?[1-9]\\d*|0");
        Matcher isNum = pattern.matcher(str);
        //正则匹配是否为浮点数（0.5451,125.55，-52.11，-0.15）
        Pattern pattern2 = Pattern.compile("-?[1-9]\\d*\\.\\d*|-?0\\.\\d*[1-9]\\d*");
        Matcher isNum2 = pattern2.matcher(str);
        if( isNum.matches() || isNum2.matches()){
            return true;
        }
        return false;
    }
	/**
	 * 根据系统小数位获取页面<fmt:formatNumber></fmt:formatNumber>数字显示类型
	 * @return
	 */
	public static String getFormatNumberType(){
		String type = "#";
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
		if(decimalScale != 0){
			String lenstr = "";
			for(int i=0;i<decimalScale;i++){
				if(StringUtils.isEmpty(lenstr)){
					lenstr = "#";
				}else{
					lenstr += "#";
				}
			}
			type = "#." + lenstr;
		}
		return type;
	}


	/**
	 * 获取时间格式路径 yyyy/MM
	 * @param
	 * @return 字符串格式 yyyy/MM
	 * @throws
	 * @author zhang_honghui
	 * @date Sep 14, 2016
	 */
	public static String getYearMonthDirPath() throws Exception{
		String path = "";
		DateFormat yearFormat = new SimpleDateFormat("yyyy");
		DateFormat monthFormat = new SimpleDateFormat("MM");
		Date nowDate=new Date();
		path =MessageFormat.format("{0}/{1}",yearFormat.format(nowDate),monthFormat.format(nowDate));
		return path;
	}

	/**
	 * 获取时间格式路径 yyyy/MM/dd
	 * @param
	 * @return 字符串格式yyyy/MM/dd
	 * @throws
	 * @author zhang_honghui
	 * @date Sep 14, 2016
	 */
	public static String getYearMonthDayDirPath() throws Exception{
		String path = "";
		DateFormat yearFormat = new SimpleDateFormat("yyyy");
		DateFormat monthFormat = new SimpleDateFormat("MM");
		DateFormat dayFormat = new SimpleDateFormat("dd");
		Date nowDate=new Date();
		path =MessageFormat.format("{0}/{1}/{2}",yearFormat.format(nowDate),monthFormat.format(nowDate),dayFormat.format(nowDate));
		return path;
	}

	/**
	 * 把文件路径中的\\替换成File.separator
	 * @param path
	 * @return java.lang.String
	 * @throws
	 * @author zhanghonghui
	 * @date Nov 07, 2016
	 */
	public static String filterFilePathSaparator(String path) throws Exception{
		if(path==null || "".equals(path.trim())){
			return path;
		}
		path=path.trim();
		if(path.indexOf('\\')>=0){
			//不要用
			//path=path.replaceAll("\\\\", Matcher.quoteReplacement(File.separator));
			//用
			path=path.replaceAll("\\\\", "/");
		}
		if(path.indexOf("//")>=0) {
			path = path.replaceAll("/{2,}", "/");
		}
		return path;
	}

	/**
	 *  获取UUID字符，去掉 -
	 * @return
	 */
	public static String getUUIDString() {
		UUID uuid=UUID.randomUUID();
		return uuid.toString().replace("-","");
	}

	/**
	 * 获取下载文件物理路径
	 * @param filepath 来自openoffice.properties 中 filepath
	 * @param fullpath 来自数据库中的fullpath
	 * @return java.lang.String
	 * @throws
	 * @author zhanghonghui
	 * @date Sep 14, 2016
	 */
	public static String getDownFilePhysicalpath(String filepath,String fullpath ) throws Exception{
		fullpath=fullpath.replaceAll("\\\\","/");
		if(fullpath.startsWith("/upload/")){
			fullpath=fullpath.replace("/upload","");
		}else if(fullpath.startsWith("upload/")){
			fullpath=fullpath.replace("upload","");
		}
		//文件存放路径
		filepath=filepath.replaceAll("\\\\","/");
		if(filepath.endsWith("/")){
			filepath=filepath.substring(0,filepath.length()-1);
		}
		//物理路径
		String phyPath = filepath+(fullpath.startsWith("/")?fullpath:"/"+fullpath);
		return phyPath;
	}

	/**
	 * 传入自定义文件名，获取上传文件的物理路径，如目录不存在，则生成目录<br/>
	 * 目录格式 x:/docpathname/yyyy/MM/dd/
	 * @param docpathname
	 * @return java.lang.String
	 * @throws String
	 * @author zhang_honghui
	 * @date Dec 08, 2016
	 */
	public static String getUploadFileDateTimePhysicalDir(String docpathname) throws Exception{
		return getUploadFilePhysicalDir(OfficeUtils.getFilepath(),docpathname,getYearMonthDayDirPath());
	}

	/**
	 * 上传文件物理路径，目录格式x:/docpathname/datesSubPath,如果目录不存在，就创建目录
	 * @param secondPath
	 * @param thirdPath
	 * @return java.lang.String
	 * @throws
	 * @author zhang_honghui
	 * @date Dec 24, 2016
	 */
	public static String getUploadFilePhysicalDir(String secondPath,String thirdPath) throws Exception{
		return getUploadFilePhysicalDir(OfficeUtils.getFilepath(),secondPath,thirdPath);
	}
	/**
	 * 上传文件物理路径，rootPath/secondPath/thirdPath,如果目录不存在，就创建目录
	 * @param rootPath
	 * @param secondPath
	 * @param thirdPath
	 * @return java.lang.String
	 * @throws
	 * @author zhang_honghui
	 * @date Dec 24, 2016
	 */
	public static String getUploadFilePhysicalDir(String rootPath,String secondPath,String thirdPath) throws Exception{
		String dirPath="";
		if(null==rootPath || "".equals(rootPath.trim())){
			return dirPath;
		}
		if(null==secondPath){
			secondPath="";
		}
		if(null==thirdPath){
			thirdPath="";
		}
		dirPath=MessageFormat.format("{0}/{1}/{2}",rootPath.trim(),secondPath.trim(),thirdPath.trim());
		dirPath=filterFilePathSaparator(dirPath);
		File imageDir=new File(dirPath);
		if(!imageDir.exists()){
			imageDir.mkdirs();
		}
		return dirPath;
	}

	/**
	 * 17位时间格式（yyyyMMddHHmmssSSS）+ 10位uuid
	 * @param
	 * @return java.lang.String
	 * @throws String
	 * @author zhang_honghui
	 * @date Dec 08, 2016
	 */
	public static String getDateTimeUUID() throws Exception{
		String timeId=getDataNumber();
		String uuid=getUUIDString();
		return timeId+uuid.substring(0,10);
	}

	/**
	 * 判断文件是否存在
	 * @param filepath
	 * @return boolean
	 * @throws boolean
	 * @author zhang_honghui
	 * @date Dec 13, 2016
	 */
	public static boolean isFileExists(String filepath) throws Exception{
		if(filepath==null || "".equals(filepath.trim())){
			return false;
		}
		File file=new File(filepath);
		if(file!=null){
			return file.exists() && !file.isDirectory();
		}
		return false;
	}


	/**
	 * JSON字符串在html上显示
	 * @param content
	 * @return java.lang.String
	 * @throws
	 * @author zhang_honghui
	 * @date Dec 23, 2016
	 */
	public static String jsonSymbolOnHtmlFilter(String content) throws Exception{
		if(content==null || content==""){
			return "";
		}
		//content = content.replaceAll("<", "&lt;");
		//content = content.replaceAll(">", "&gt;");
		//content = content.replaceAll(":", "&#58;");
		content = content.replaceAll("\"", "&quot;");
		content = content.replaceAll("\'", "&#39;");
		content = content.replaceAll("\\\\", "╲");//对斜线的转义
		content = content.replaceAll("\r\n","<br>"); //easyui form不能用<br/>会报错
		content = content.replaceAll("\n\r", "<br>");//easyui form不能用<br/>会报错
		content = content.replace("\n", "<br>");//easyui form不能用<br/>会报错
		content = content.replace("\r", "<br>");//easyui form不能用<br/>会报错
		return content;
	}
	/**
	 * 过滤json中html符号如< 或 >
	 * @param content
	 * @return java.lang.String
	 * @throws
	 * @author zhang_honghui
	 * @date Dec 29, 2016
	 */
	public static String jsonHtmlSymbolFilter(String content) throws Exception{
		if(content==null || content==""){
			return "";
		}
		content = content.replaceAll("<", "&lt;");
		content = content.replaceAll(">", "&gt;");
		return content;
	}

	/**
	 * 导出下载文本文件
	 * @param data
	 * @param filename
	 * @param fileext
	 * @return void
	 * @throws
	 * @author zhang_honghui
	 * @date Dec 31, 2016
	 */
	public static void responseExportTxtFile(javax.servlet.http.HttpServletResponse response, byte[] data,String filename,String fileext) throws Exception{
		responseExportTxtFile(response,"utf-8",data,filename,fileext);
	}
	/**
	 * 导出下载文本文件
	 * @param data
	 * @param filename
	 * @param fileext
	 * @return void
	 * @throws
	 * @author zhang_honghui
	 * @date Dec 31, 2016
	 */
	public static void responseExportTxtFile(javax.servlet.http.HttpServletResponse response,String charset, byte[] data,String filename,String fileext) throws Exception{
		if(null==filename || "".equals(filename.trim())){
			filename="默认导出文件名";
		}
		filename=filename.trim();
		if(null==fileext || "".equals(fileext.trim())){
			fileext=".txt";
		}
		fileext=fileext.trim();
		if(!fileext.startsWith(".")){
			fileext="."+fileext;
		}
		OutputStream os = response.getOutputStream();
		try {
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=\"" +new String((filename+fileext).getBytes("UTF-8"), "ISO8859-1" )  +"\"");
			if(null!=charset&&!"".equals(charset.trim())) {
				response.setContentType("text/plain;charset="+charset);
			}
			os.write(data);
			os.flush();
		}

		finally {
			if (os != null) {
				os.close();
			}
		}
	}

	/**
	 * 字符串多个合并转换成集合
	 * 例:  "1,2," "3" null "3,4"  转换为集合 ["1","2","3","4"]
	 * @param split 分隔符
	 * @param multipleString 待合并分割的字符串(不限个数)
	 * @return 分割完的字符串集合
	 */
	public static List<String> stringsToList(String split, String... multipleString) {
		return stringsToList(true, false, split, multipleString);
	}

	/**
	 * 字符串多个合并转换成集合
	 * 例:  "1,2," "3" "3,4"  转换为集合 ["1","2","3","4"]
	 * @param isDistinct 是否排除重复
	 * @param containsNull 是否包含空值和null
	 * @param split 分隔符
	 * @param multipleString 待合并分割的字符串(不限个数)
	 * @return 分割完的字符串集合
	 */
	public static List<String> stringsToList(boolean isDistinct, boolean containsNull, String split, String... multipleString) {
		StringBuilder str = new StringBuilder();
		Map<String, String> keyHash = new HashMap<String,String>();
		for (int i = 0; i < multipleString.length; i++) {
			if (i > 0)
				str.append(split);
			str.append(multipleString[i]);
		}
		String[] list = str.toString().split(",");
		List<String> result = new LinkedList<String>();
		String nullkey="-*-*-*-#@$null$@#-*-*-*-";
		String emptykey="-*-*-*-#@$empty$@#-*-*-*-";
		for (String item : list) {
			if (null!=item && !"".equals(item.trim()) ) {
				if (isDistinct && keyHash.containsKey(item.trim())){
					continue;
				}
				result.add(item);
				keyHash.put(item.trim(),item.trim());
			}else {
				if (containsNull) {
					if (null == item && isDistinct) {
						if (keyHash.containsKey(nullkey)) {
							continue;
						}
						keyHash.put(nullkey, null);
					}
					if ("".equals(item.trim()) && isDistinct) {
						if (keyHash.containsKey(emptykey)) {
							continue;
						}
						keyHash.put(emptykey, "");
					}
					result.add(item);
				}
			}
		}
		return result;
	}

	/**
	 * 数字转成26进制
	 * @param dec
	 * @return java.lang.String
	 * @throws
	 * @author zhanghonghui
	 * @date May 13, 2017
	 */
	public static String baseTenTo26(int dec) throws Exception{
		StringBuffer builder = new StringBuffer();
		if(dec<=0){
			builder.append("A");
		}else {
			while (dec > 0) {
				builder.insert(0, (char) ((dec % 26) + 65));
				dec /= 26;
			}
		}
		return builder.toString();
	}

	/**
	 * 26进制字符串转整数
	 * @param base26
	 * @return int
	 * @throws
	 * @author zhanghonghui
	 * @date May 13, 2017
	 */
	public int base26ToTen(String base26) throws Exception{
		int result = 0;
		if(null==base26 || "".equals(base26.trim())){
			return result;
		}
		for(char c : base26.toCharArray())
		{
			int v = 0;
			if(c >= 'A' && c<='Z')
			{
				v = c - 'A';
			}
			else if(c >= 'a' && c <='z')
			{
				v = c - 'a';
			}
			else
			{
				return 0;
			}
			result = result * 26 + v;
		}
		return result;
	}

	/**
	 * 获取当前时间hh:mm:ss.SSS
	 * @return
	 * @author limin
	 * @date Nov 30, 2015
	 */
	public static String getNowTime(){

		SimpleDateFormat sf = new SimpleDateFormat("hh:mm:ss.SSS");
		String str = sf.format(new Date());
		return str;
	}

	public static boolean isMobileDevice(String requestHeader) {
		/**
		 * android : 所有android设备
		 * mac os : iphone ipad
		 * windows phone:Nokia等windows系统的手机
		 */
		String[] deviceArray = new String[]{"android", "mac os", "windows phone"};
		if (requestHeader == null)
			return false;
		requestHeader = requestHeader.toLowerCase();
		for (int i = 0; i < deviceArray.length; i++) {
			if (requestHeader.indexOf(deviceArray[i]) > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取今天日期
	 * @return
	 * @author chenwei
	 * @date Jul 11, 2013
	 */
	public static String getTodayDateStr() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sf.format(new Date());
		return str;
	}

}
