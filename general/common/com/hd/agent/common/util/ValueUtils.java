package com.hd.agent.common.util;

import java.util.Map;

/**
 * Created by FD007 on 2016/12/15.
 */
public class ValueUtils {
    /**
     * 获取Map中Key的值，如果没有则为空字符串
     * @param key
     * @param dataMap
     * @return java.lang.String
     * @throws
     * @author zhang_honghui
     * @date Dec 27, 2016
     */
    public static String getStringValueInMap(String key,Map dataMap) throws Exception{
        String result="";
        if(null==key || "".equals(key.trim())){
            return result;
        }

        if(dataMap.containsKey(key.trim())){
            result=(String)dataMap.get(key.trim());
        }
        if(null==result){
            result="";
        }
        return result.trim();
    }
    /**
     * 如果传入的值为NULL，则用空字符串
     * @param val
     * @return java.lang.String
     * @throws
     * @author zhang_honghui
     * @date Dec 27, 2016
     */
    public static String getValueOrEmpty(String val) throws Exception{
        if(null==val){
            val="";
        }
        return val.trim();
    }

    /**
     * 按照len截取content，单位（文本，字数）
     * @param content
     * @param len
     * @return java.lang.String
     * @throws
     * @author zhang_honghui
     * @date Dec 30, 2016
     */
    public static String getCutString(String content,int len) throws Exception{
        content=getValueOrEmpty(content);
        if(content.length()<=len){
            return content;
        }
        return content.substring(0,len);
    }
}
