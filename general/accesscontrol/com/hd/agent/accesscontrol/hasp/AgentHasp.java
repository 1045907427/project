package com.hd.agent.accesscontrol.hasp;
/*****************************************************************************
*
* Demo program for Sentinel LDK licensing services
*
*
* Copyright (C) 2013, SafeNet, Inc. All rights reserved.
*
*
* Sentinel DEMOMA key required with features 1 and 42 enabled
*
*****************************************************************************/

import com.hd.agent.accesscontrol.util.AccessUtils;
import com.hd.agent.accesscontrol.util.CodeUtils;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PropertiesUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.Date;
import java.util.Map;


public class AgentHasp
{
	public static boolean LAST_TRUE = false;
	public static int LOGIN_TIME = 0;
	/**
     * 判断加密狗是否是对应的服务器
     * @return
     * @throws Exception
     * @author chenwei 
     * @date Mar 13, 2014
     */
    public static boolean isTrueHasp() throws Exception{
    	boolean flag = false;
    	long ran = Math.round(Math.random()*9+1);
    	if(ran<5 || !LAST_TRUE){
	    	String keyid = AccessUtils.getHardDiskSN("C");
	    	Map map = PropertiesUtils.readPropertiesFile(AccessUtils.getAccessPath());
	    	if(null==map || map.size()==0){
	    		map = PropertiesUtils.readPropertiesFileNew("C:"+ File.separator+"agent"+File.separator+"agentkey.properties");
	    		if(null!=keyid && null!=map && map.containsKey("sessionkey")){
		    		String sessionkey = "bQLazo1kBJE29wS5iZPKN2y11ZPc8O3yNDx56HMwg1bla1Pk1Kn2YG4il4pbBzGQ5JD70T6dyQj3A";
		    		String psessionkey = (String) map.get("sessionkey");
		    		String bkeyid = keyid;
		    		if(keyid.equals(bkeyid) && psessionkey.equals(sessionkey)){
		    			flag = true;
		    		}
			    	LAST_TRUE = flag;
			    	if(LOGIN_TIME>100){
			    		flag = false;
			    	}
		    	}else{
		    		flag = false;
		    		LAST_TRUE = flag;
		    	}
	    	}else{
	    		flag = isTrueDisk();
	    		LAST_TRUE = flag;
	    	}
	    	
    	}else{
    		flag = LAST_TRUE;
    	}
    	LOGIN_TIME ++;
    	return flag;
    }
    
    public static boolean isTrueHaspCash() throws Exception{
    	if(!LAST_TRUE){
    		isTrueHasp();
    	}
    	return LAST_TRUE;
    }
    /**
     * 判断公司是否一致
     * @param name
     * @return
     * @throws Exception
     * @author chenwei 
     * @date 2014年10月21日
     */
    public static boolean isTrueCompany(String name) throws Exception{
    	try {
    		String diskid =  AccessUtils.getHardDiskSN("C");
    		//这是密钥
    		String key = diskid+"_agent_hongdu";
        	Map map = PropertiesUtils.readPropertiesFile(AccessUtils.getAccessPath());
        	String cname = "";
        	if(null!=map && map.containsKey("k")){
        		cname = (String) map.get("k");
        		cname = CodeUtils.aesDecrypt(cname, key);
        	}
        	if(name.equals(cname)){
        		return true;
        	}
		} catch (Exception e) {
		}
    	return false;
    }
    /**
     * 判断硬盘是否一致
     * @return
     * @throws Exception
     * @author chenwei 
     * @date 2014年10月21日
     */
    public static boolean isTrueDisk(){
    	try {
    		String diskid =  AccessUtils.getHardDiskSN("C");
    		//这是密钥
    		String key = diskid+"_agent_hongdu";
        	Map map = PropertiesUtils.readPropertiesFile(AccessUtils.getAccessPath());
        	String cname = "";
        	if(null!=map && map.containsKey("g")){
        		cname = (String) map.get("g");
        		cname = CodeUtils.aesDecrypt(cname, key);
        	}
        	if(diskid.equals(cname)){
        		return true;
        	}
		} catch (Exception e) {
		}
    	return false;
    }
    /**
     * 判断序列号是否正确
     * @param serializeid
     * @return
     * @throws Exception
     * @author chenwei 
     * @date 2014年10月21日
     */
    public static boolean isTrueSerializeid(String serializeid){
    	try {
    		String diskid =  AccessUtils.getHardDiskSN("C");
    		//这是密钥
    		String key = diskid+"_agent_hongdu";
        	Map map = PropertiesUtils.readPropertiesFile(AccessUtils.getAccessPath());
        	String cname = "";
        	if(null!=map && map.containsKey("z")){
        		cname = (String) map.get("z");
        		cname = CodeUtils.aesDecrypt(cname, key);
        	}
        	if(serializeid.equals(cname)){
        		return true;
        	}
		} catch (Exception e) {
		}
    	return false;
    }
    /**
     * 获取系统开始使用日期
     * @return
     * @throws Exception
     * @author chenwei 
     * @date 2014年10月21日
     */
    public static String getSysBeginDate() throws Exception{
    	String diskid =  AccessUtils.getHardDiskSN("C");
		//这是密钥
		String key = diskid+"_agent_hongdu";
    	Map map = PropertiesUtils.readPropertiesFile(AccessUtils.getAccessPath());
    	String cname = "";
    	if(null!=map && map.containsKey("c")){
    		cname = (String) map.get("c");
    		cname = CodeUtils.aesDecrypt(cname, key);
    	}
    	return cname;
    }
    /**
     * 获取系统是否试用
     * @return
     * @throws Exception
     * @author chenwei 
     * @date 2014年10月21日
     */
    public static boolean isTry() throws Exception{
    	String diskid =  AccessUtils.getHardDiskSN("C");
		//这是密钥
		String key = diskid+"_agent_hongdu";
    	Map map = PropertiesUtils.readPropertiesFile(AccessUtils.getAccessPath());
    	String cname = "";
    	boolean istry = false;
    	if(null!=map && map.containsKey("i")){
    		cname = (String) map.get("i");
    		cname = CodeUtils.aesDecrypt(cname, key);
    		if(StringUtils.isNotEmpty(cname)){
	    		String[] arr = cname.split("_");
	    		int len = arr.length;
	    		String istrystr = arr[len-1];
	    		if("1".equals(istrystr)){
	    			istry = true;
	    		}
	    	}
    	}
    	return istry;
    }
    
    /**
     * 获取系统试用截止日期
     * @return
     * @throws Exception
     * @author chenwei 
     * @date 2014年10月21日
     */
    public static String getSysTryDate() throws Exception{
    	String diskid =  AccessUtils.getHardDiskSN("C");
		//这是密钥
		String key = diskid+"_agent_hongdu";
    	Map map = PropertiesUtils.readPropertiesFile(AccessUtils.getAccessPath());
    	String cname = "";
    	String trydate = "";
    	if(null!=map && map.containsKey("t")){
    		cname = (String) map.get("t");
    		cname = CodeUtils.aesDecrypt(cname, key);
    		if(StringUtils.isNotEmpty(cname)){
	    		String[] arr = cname.split("_");
	    		int len = arr.length;
	    		trydate = arr[len-1];
	    	}
    	}
    	if(StringUtils.isNotEmpty(trydate)){
    		trydate = CommonUtils.dataToStr(CommonUtils.stringToDate(trydate,"yyyyMMdd"), "yyyy-MM-dd");
    	}
    	return trydate;
    }
    /**
     * 判断系统是否试用期或者正式版
     * @return
     * @throws Exception
     * @author chenwei 
     * @date 2014年12月24日
     */
    public static boolean isInTryDate() throws Exception{
    	boolean istry = isTry();
    	if(istry){
    		String trydate = getSysTryDate();
    		Date date = CommonUtils.stringToDate(trydate);
    		Date today = CommonUtils.stringToDate(CommonUtils.getTodayDataStr());
    		if(date.compareTo(today)>=0){
    			return true;
    		}else{
    			return false;
    		}
    	}else{
    		return true;
    	}
    }
    /**
     * 获取系统手机用户限制数量
     * @return
     * @throws Exception
     * @author chenwei 
     * @date 2014年10月21日
     */
    public static int getPhoneNum(){
		try {
			String diskid =  AccessUtils.getHardDiskSN("C");
			//这是密钥
			String key = diskid+"_agent_hongdu";
			Map map = PropertiesUtils.readPropertiesFile(AccessUtils.getAccessPath());
			String cname = "";
	    	if(null!=map && map.containsKey("p")){
	    		cname = (String) map.get("p");
	    		cname = CodeUtils.aesDecrypt(cname, key);
	    	}
	    	if(StringUtils.isNotEmpty(cname)){
	    		String[] arr = cname.split("_");
	    		int len = arr.length;
	    		return Integer.parseInt(arr[len-1]);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return 0;
    }
    /**
     * 获取系统用户限制数量
     * @return
     * @throws Exception
     * @author chenwei 
     * @date 2014年10月21日
     */
    public static int getSysNum() throws Exception{
    	try {
	    	String diskid =  AccessUtils.getHardDiskSN("C");
			//这是密钥
			String key = diskid+"_agent_hongdu";
	    	Map map = PropertiesUtils.readPropertiesFile(AccessUtils.getAccessPath());
	    	String cname = "";
	    	if(null!=map && map.containsKey("s")){
	    		cname = (String) map.get("s");
	    		cname = CodeUtils.aesDecrypt(cname, key);
	    	}
	    	if(StringUtils.isNotEmpty(cname)){
	    		String[] arr = cname.split("_");
	    		int len = arr.length;
	    		return Integer.parseInt(arr[len-1]);
	    	}
	    } catch (Exception e) {
		}
    	return 0;
    }
}
