/**
 * @(#)WechatProperties.java
 * @author limin
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016年5月9日 limin 创建版本
 */
package com.hd.agent.common.util;

import java.io.*;
import java.util.*;

/**
 * 微信properties 类
 *
 * @author limin
 */
public class WechatProperties {

    public WechatProperties(){}

    /**
     * wechat.properties 内容
     */
    private static Properties props = new Properties();

    private static String[] keys = {"serviceid","servicesecret"};

    public static Map<String, String> content = new HashMap<String, String>();

    static{
        try {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("wechat.properties"));

            for(String key : keys) {
                content.put(key, getValue(key));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取key对应的内容
     * @param key
     * @return
     * @author limin
     * @date May 9, 2016
     */
    public static String getValue(String key) {

        try {
            String v = props.getProperty(key);
            return new DESHelper().decrypt(v);
        } catch (Exception e) { }
        return "";
    }

    /**
     * 获取key对应的内容
     * @param key
     * @return
     * @author limin
     * @date May 9, 2016
     */
    public static String returnValue(String key) {

        try {
            return props.getProperty(key);
        } catch (Exception e) { }
        return "";
    }

    /**
     * 写资源文件，含中文
     *
     * @author chenwei
     * @date 2014年10月20日
     */
    public static void write() {
        Properties properties = new Properties();
        try {

            String classpath = Thread.currentThread().getContextClassLoader().getResource("").toString();
            String path = classpath.substring(5);
            String filename = "wechat.properties";

            File pathfile = new File(path);
            if (!pathfile.exists()) {
                pathfile.mkdir();
            }
            OutputStream outputStream = new FileOutputStream(path + "\\" + filename);

            if (null != content) {
                Set set = content.entrySet();
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
                    String keyStr = entry.getKey();
                    String value = entry.getValue();
                    properties.setProperty(keyStr, new DESHelper().encrypt(value));
                    props.setProperty(keyStr, new DESHelper().encrypt(value));
                }
            }
            properties.store(outputStream, "");
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
