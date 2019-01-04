/**
 * @(#)AccessUtils.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 12, 2014 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.util;

import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PropertiesUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


/**
 * 
 * 权限控制工具类
 * @author chenwei
 */
public class AccessUtils {
	public final static String ACCESSALLOW="Hasp_Acceess_Type";
	
	public static String DiskSN = null;

    /**
     *
     * @param drive 硬盘驱动器分区 如C,D
     * @return 该分区的卷标
     */
    public static String getHDSerial(String drive) {
        String result = "";
        try {
            File file = File.createTempFile("tmp", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                    +"Set colDrives = objFSO.Drives\n"
                    +"Set objDrive = colDrives.item(\"" + drive + "\")\n"
                    +"Wscript.Echo objDrive.SerialNumber";  // see note
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
            file.delete();
        } catch (Exception e) {
        }
        if (result.trim().length() < 1 || result == null) {
            result = "无磁盘ID被读取";
        }
        return result.trim();
    }

    /**
     * 获取unix网卡的mac地址. 非windows的系统默认调用本方法获取. 如果有特殊系统请继续扩充新的取mac地址方法.
     *
     * @return mac地址
     */
    public static String getUnixMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;
        try {
            // linux下的命令，一般取eth0作为本地主网卡
            process = Runtime.getRuntime().exec("ifconfig eth0");
            // 显示信息中包含有mac地址信息
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                // 寻找标示字符串[hwaddr]
                index = line.toLowerCase().indexOf("hwaddr");
                if (index >= 0) {// 找到了
                    // 取出mac地址并去除2边空格
                    mac = line.substring(index + "hwaddr".length() + 1).trim();
                    break;
                }
            }
        }
        catch (IOException e) {
            System.out.println("unix/linux方式未获取到网卡地址");
        }
        finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
            bufferedReader = null;
            process = null;
        }
        return mac;
    }

    /**
	 * 获取硬盘序列号
	 * 
	 * @param drive
	 *            盘符
	 * @return
	 */
	public static String getHardDiskSN(String drive) {
        if(null!=DiskSN){
			return DiskSN;
		}else{
            String osName = System.getProperty("os.name");
            //判断是否是windows系统 widonws系统 取硬盘序列号
            //其他系统取mac地址
            if (osName.toLowerCase().startsWith("win")) {
                String result = getHDSerial(drive);
                DiskSN = result.trim();
                return result.trim();
            }else{
                String result = CommonUtils.MD5(getUnixMACAddress()+osName);
                DiskSN = result;
                return result;
            }
		}
	}
	/**
	 * 获取lib路径
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 12, 2014
	 */
	public static String getLibPath() throws Exception{

		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath().replaceAll("%20", " ");
	    int pos = -1;
	    if (path.indexOf("/") != -1)
	      pos = path.indexOf("/WEB-INF/");
	    else if (path.indexOf("\\") != -1) {
	      pos = path.indexOf("\\WEB-INF\\");
	    }
	    if (pos != -1) {
	      path = path.substring(0, pos);
	      if (path.indexOf("/") != -1)
	        path = path + "/WEB-INF/lib/";
	      else if (path.indexOf("\\") != -1) {
	        path = path + "\\WEB-INF\\lib\\";
	      }
	    }
	    String osName = System.getProperty("os.name");
	    if ((osName != null) && (osName.toLowerCase().startsWith("windows"))) {
	      path = path.replaceFirst("/", "");
	    }
	    return path;
	}
    /**
     * 获取lib路径
     * @return
     * @throws Exception
     * @author chenwei
     * @date Mar 12, 2014
     */
    public static String getTomcatPath() throws Exception{
        String path = System.getProperty("catalina.home").replaceAll("%20", " ");
        return path;
    }
	/**
	 * 获取配置文件名称
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月21日
	 */
	public static String getPropertiesName() throws Exception{
        String osName = System.getProperty("os.name");
        //判断是否是windows系统
        if (osName.toLowerCase().startsWith("win")) {
            return CommonUtils.MD5(getLibPath())+".properties";
        }else{
            return CommonUtils.MD5(getTomcatPath())+".bat";
        }
	}
	/**
	 * 获取配置文件路径
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月21日
	 */
	public static String getAccessPath() throws Exception{
        String osName = System.getProperty("os.name");
        //判断是否是windows系统
        if (osName.toLowerCase().startsWith("win")) {
            String path = getLibPath().substring(0, 1);
            path = path+":"+ File.separator+"agent"+File.separator+getPropertiesName();
            return path;
        }else{
            String path = getTomcatPath()+ File.separator+"bin"+File.separator+getPropertiesName();
            return path;
        }
	}
	/**
	 * 获取配置文件目录
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月21日
	 */
	public static String getAccessRoot() throws Exception{
        String osName = System.getProperty("os.name");
        //判断是否是windows系统
        if (osName.toLowerCase().startsWith("win")) {
            String path = getLibPath().substring(0, 1);
            path = path+":"+File.separator+"agent";
            return path;
        }else{
            String path = getTomcatPath()+ File.separator+"bin";
            return path;
        }
	}
	/**
	 * 生成密匙
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月20日
	 */
	public static boolean addAccessSet(String date,String name,String serializeid,String phonenum,String sysnum,String duedate) throws Exception{
		String diskid =  getHardDiskSN("C");
		//这是密钥
		String key = diskid+"_agent_hongdu";
		Map map = new HashMap();
		String [] arr = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		for(String str : arr){
			if("c".equals(str)){
				map.put(str, CodeUtils.aesEncrypt(date, key));
			}else if("k".equals(str)){
				map.put(str, CodeUtils.aesEncrypt(name, key));
			}else if("z".equals(str)){
				map.put(str, CodeUtils.aesEncrypt(serializeid, key));
			}else if("g".equals(str)){
				map.put(str, CodeUtils.aesEncrypt(diskid, key));
			}else if("p".equals(str)){
				map.put(str, CodeUtils.aesEncrypt(name+"_"+phonenum, key));
			}else if("s".equals(str)){
				map.put(str, CodeUtils.aesEncrypt(diskid+"_"+sysnum, key));
			}else if("i".equals(str)){
				if(StringUtils.isNotEmpty(duedate)){
					map.put(str, CodeUtils.aesEncrypt(diskid+"_1", key));
				}else{
					map.put(str, CodeUtils.aesEncrypt(diskid+"_0", key));
				}
			}else if("t".equals(str)){
				if(StringUtils.isNotEmpty(duedate)){
					map.put(str, CodeUtils.aesEncrypt(diskid+"_"+duedate, key));
				}else{
					map.put(str, CodeUtils.aesEncrypt(diskid+"_", key));
				}
				
			}else{
				map.put(str, CodeUtils.aesEncrypt(CodeUtils.getRandZ(), key));
			}
		}
		PropertiesUtils.writePropertiesFile(map, getAccessRoot(),getPropertiesName());
		return true;
	}
	/**
	 * 加密
	 * @param val
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月20日
	 */
	public static String aesDecrypt(String val) throws Exception{
		//这是密钥
		String key ="agent_hongdu";
		String aesVal = CodeUtils.aesEncrypt(val, key);
		return aesVal;
	} 
}

