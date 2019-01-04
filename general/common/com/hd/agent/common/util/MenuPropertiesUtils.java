package com.hd.agent.common.util;

import com.hd.agent.accesscontrol.util.CodeUtils;
import com.hd.agent.system.model.SysParam;
import com.hd.agent.system.service.ISysParamService;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by wanghongteng on 2017/6/21.
 */
public class MenuPropertiesUtils {
    private static Map pMap;

    private static boolean isSuperAccess;

    /**
     * 读取资源文件,并处理中文乱码 并解密
     * @param filename
     * @return
     * @author wanghongteng
     * @date 2017-6-20
     */
    public static Map readPropertiesFileNewAesDecrypt(String filename) throws Exception {
        isSuperAccess=false;
        pMap = new HashMap();
        Map map = new HashMap();
        ISysParamService baseSysParamService=(ISysParamService) SpringContextUtils.getBean("sysParamService");
        SysParam sysParam=baseSysParamService.getSysParam("COMPANYNAME");
       // String companyname = sysParam.getPvalue();
        String companyname = "浙江奋德软件有限公司";
        Properties properties = new Properties();
        try {
            InputStream inputStream = new FileInputStream(filename);
            properties.load(new InputStreamReader(inputStream));
            inputStream.close(); // 关闭流
            Set<Map.Entry<Object, Object>> entrySet = properties.entrySet();
            for (Map.Entry<Object, Object> entry : entrySet) {
                if (!entry.getKey().toString().startsWith("#")) {
                    if("superaccess".equals(CodeUtils.aesDecrypt(((String) entry.getKey()), KeyMD5(companyname)))){
                        isSuperAccess=true;
                    }
                    pMap.put(CodeUtils.aesDecrypt(((String) entry.getKey()), KeyMD5(companyname)), CodeUtils.aesDecrypt(((String) entry.getValue()), KeyMD5(companyname)));
                }
            }
            map.put("flag",true);
            return map;
        }catch (Exception e)  {
            map.put("flag",false);
            return map;
        }
    }
    /**
     * 获取读取后的pMap
     * @return
     * @author wanghongteng
     * @date 2017-6-20
     */
    public static Map getpMap() throws Exception {
        return pMap;
    }

    /**
     * 获取读取后的pMap
     * @return
     * @author wanghongteng
     * @date 2017-6-20
     */
    public static boolean getIsSuperAccess() throws Exception {
        return isSuperAccess;
    }

    /**
     * MD5加密
     *
     * @param inStr
     * @return
     */
    public static String KeyMD5(String inStr) {
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
}
