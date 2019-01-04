/**
 * @(#)FileUtils.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年8月20日 chenwei 创建版本
 */
package com.hd.agent.common.util;

import org.apache.log4j.Logger;

import java.io.*;

/**
 * 
 * 文件操作工具类
 * @author chenwei
 */
public class FileUtils {
    private static final Logger logger = Logger.getLogger(FileUtils.class);
	/**
	 * 创建文件
	 * 
	 * @param file
	 * @return
	 */
	public static boolean createFile(File file) throws Exception {
		boolean flag = false;
		try {
            if(file==null){
                return flag;
            }
            File parentDir = file.getParentFile();
            if(parentDir!=null&&!parentDir.exists()){
                parentDir.mkdirs();
            }
			if (!file.exists()) {
                file.createNewFile();
				flag = true;
			}
		} catch (Exception e) {
            logger.error("创建文件时异常",e);
		}
		return true;
	}

	/**
	 * 读TXT文件内容
	 * 
	 * @param fileName
	 * @return
	 */
	public static String readTxtFile(File fileName) throws Exception {
		String result = "";
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			try {
				String read = null;
				while ((read = bufferedReader.readLine()) != null) {
					result = result + read + "\r\n";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
            logger.error("读取文件时异常",e);
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (fileReader != null) {
				fileReader.close();
			}
		}
		//logger.info("读取出来的文件内容是：" + "\r\n" + result);
		return result;
	}
    /**
     * 读TXT文件内容
     *
     * @param fileName
     * @param charset
     * @return
     */
    public static String readTxtFile(File fileName,String charset ) throws Exception {
        String result = "";
        InputStreamReader inputStreamReader=null;
        BufferedReader bufferedReader = null;
        try {
            inputStreamReader=new InputStreamReader(new FileInputStream(fileName),charset);
            bufferedReader = new BufferedReader(inputStreamReader);
            try {
                String read = null;
                while ((read = bufferedReader.readLine()) != null) {
                    result = result + read + "\r\n";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            logger.error("读取文件时异常",e);
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
        }
        //System.out.println("读取出来的文件内容是：" + "\r\n" + result);
        return result;
    }
	/**
	 * 把字符串内容写入文件
	 * @param content		文件内容
	 * @param filePath		文件路径
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年8月20日
	 */
	public static void writeTxtFile(String content, String filePath)
			throws Exception {
        writeTxtFile(content,"UTF-8",filePath);
	}
    /**
     * 把字符串内容写入文件
     * @param content		文件内容
     * @param contentcharset		文件路径
     * @param filePath		文件路径
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年8月20日
     */
    public static void writeTxtFile(String content,String contentcharset, String filePath)
            throws Exception {
        FileOutputStream foStream = null;
        //String newfilePath = new String(fileName.getBytes("UTF-8"),System.getProperty("file.encoding"));
        File file = new File(filePath);
        File parent = file.getParentFile();
        if(parent!=null&&!parent.exists()){
            parent.mkdirs();
        }
        if (!file.exists()) {
            FileUtils.createFile(file);
//				file.createNewFile();// 不存在则创建
        }
        foStream = new FileOutputStream(file);
        foStream.write(content.getBytes(contentcharset));
        foStream.close();
    }

    /**
     * 把字节内容内容写入文件
     * @param content		文件内容,字节
     * @param filePath		文件路径
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年8月20日
     */
    public static void writeByteToFile(byte[] content,String filePath)
            throws Exception {
        FileOutputStream foStream = null;
        //String newfilePath = new String(fileName.getBytes("UTF-8"),System.getProperty("file.encoding"));
        File file = new File(filePath);
        File parent = file.getParentFile();
        if(parent!=null&&!parent.exists()){
            parent.mkdirs();
        }
        if (!file.exists()) {
            FileUtils.createFile(file);
//				file.createNewFile();// 不存在则创建
        }
        foStream = new FileOutputStream(file);
        foStream.write(content);
        foStream.close();
    }

    /**
     * 获取文件编码类型
     * @param sourceFile
     * @return java.lang.String
     * @throws String
     * @author zhang_honghui
     * @date Dec 15, 2016
     */
    public static String getFileEncode(File sourceFile) throws Exception {
        String charset = System.getProperty("file.encoding");
        byte[] first3Bytes = new byte[3];
        FileInputStream fis = null;
        BufferedInputStream bis = null;

        try {

            if(sourceFile == null) {
                return "";
            }

            boolean checked = false;
            fis = new FileInputStream(sourceFile);
            bis = new BufferedInputStream(fis);
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                return charset; //文件编码为 ANSI
            } else if (first3Bytes[0] == (byte) 0xFF
                    && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE"; //文件编码为 Unicode
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE"; //文件编码为 Unicode big endian
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8"; //文件编码为 UTF-8
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
                            // (0x80
                            // - 0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }

        } catch (Exception e) {

            logger.error("获取文件类型失败", e);

        } finally {

            if (fis != null) {
                fis.close();
            }
            if (bis != null) {
                bis.close();
            }
        }
        return charset;
    }

    /**
     * 获取class路径
     * @param
     * @return java.lang.String
     * @throws
     * @author zhang_honghui
     * @date Feb 05, 2017
     */
    public static String getClassPath() throws Exception{
        String path = FileUtils.class.getResource("").getPath().replaceAll("%20", " ");

        if(path.indexOf('\\')>=0){
            path=path.replaceAll("\\\\", "/");
        }
        int pos = -1;
        if (path.indexOf("/") != -1) {
            pos = path.indexOf("/WEB-INF/");
        }
        if (pos != -1) {
            path = path.substring(0, pos);
            if (path.indexOf("/") != -1) {
                path = path + "/WEB-INF/classes/";
            }
        }
        String osName = System.getProperty("os.name");
        if ((osName != null) && (osName.toLowerCase().startsWith("windows"))) {
            path = path.replaceFirst("/", "");
        }

        return path;
    }

    /**
     * 获取application 物理路径
     * @param
     * @return java.lang.String
     * @throws
     * @author zhang_honghui
     * @date Feb 05, 2017
     */
    public static String getApplicationRealPath() throws Exception{
        String path = FileUtils.class.getResource("").getPath().replaceAll("%20", " ");

        if(path.indexOf('\\')>=0){
            path=path.replaceAll("\\\\", "/");
        }
        int pos = -1;
        if (path.indexOf("/") != -1) {
            pos = path.indexOf("/WEB-INF/");
        }
        if (pos != -1) {
            path = path.substring(0, pos);
        }
        String osName = System.getProperty("os.name");
        if ((osName != null) && (osName.toLowerCase().startsWith("windows"))) {
            path = path.replaceFirst("/", "");
        }

        return path;
    }
}

