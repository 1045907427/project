/**
 * @(#)PropertiesUtils.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年10月20日 chenwei 创建版本
 */
package com.hd.agent.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

/**
 * 
 * Properties文件读写
 * @author chenwei
 */
public class PropertiesUtils {
	private static Map pMap;
	/**
	 * 写资源文件，含中文  
	 * @param map			参数
	 * @param filename		文件名
	 * @author chenwei 
	 * @date 2014年10月20日
	 */
	public static void writePropertiesFile(Map<String, String> map, String path,String filename) {
		Properties properties = new Properties();
		try {
			File pathfile = new File(path);
			if(!pathfile.exists()){
				pathfile.mkdir();
			}
			OutputStream outputStream = new FileOutputStream(path+File.separator+filename);
			if(null!=map){
				Set set = map.entrySet();
				Iterator it = set.iterator();
				while (it.hasNext()) {
					Map.Entry<String, String> entry = (Entry<String, String>) it.next();
					String keyStr = entry.getKey();
					String value = entry.getValue();
					properties.setProperty(keyStr, value);
				}
			}
			properties.store(outputStream, "");
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pMap = null;
	}
	/**
	 * 读取资源文件,并处理中文乱码  
	 * @param filename
	 * @return
	 * @author chenwei 
	 * @date 2014年10月20日
	 */
	public static Map readPropertiesFile(String filename) {
		if(null==pMap){
			pMap = new HashMap();
			Properties properties = new Properties();
			try {
				InputStream inputStream = new FileInputStream(filename);
				properties.load(new InputStreamReader(inputStream));
				inputStream.close(); // 关闭流
				Set<Entry<Object, Object>> entrySet = properties.entrySet();
	            for (Entry<Object, Object> entry : entrySet) {
	                if (!entry.getKey().toString().startsWith("#")) {
	                	pMap.put(((String) entry.getKey()).trim(), ((String) entry.getValue()).trim());
	                }
	            }
	            return pMap;
			} catch (IOException e) {
			}
		}
		return pMap;
	}
	/**
	 * 读取资源文件,并处理中文乱码  
	 * @param filename
	 * @return
	 * @author chenwei 
	 * @date 2014年10月20日
	 */
	public static Map readPropertiesFileNew(String filename) {
		Map aMap = new HashMap();
		Properties properties = new Properties();
		try {
			InputStream inputStream = new FileInputStream(filename);
			properties.load(new InputStreamReader(inputStream));
			inputStream.close(); // 关闭流
			Set<Entry<Object, Object>> entrySet = properties.entrySet();
            for (Entry<Object, Object> entry : entrySet) {
                if (!entry.getKey().toString().startsWith("#")) {
					aMap.put(((String) entry.getKey()).trim(), ((String) entry.getValue()).trim());
                }
            }
            return aMap;
		} catch (IOException e) {
		}
		return aMap;
	}
}

