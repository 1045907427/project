/**
 * @(#)OfficeUtils.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 21, 2013 chenwei 创建版本
 */
package com.hd.agent.common.util;

import org.apache.log4j.Logger;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.ExternalOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 *
 * office文件转换
 * @author chenwei
 */
public final class OfficeUtils {
    protected static final Logger logger = Logger.getLogger(OfficeUtils.class);
    /**
     * openoffice安装路径
     */
    private static String OpenOffice_HOME;
    /**
     * 服务器地址
     */
    private static String host;
    /**
     * 端口
     */
    private static String port;
    /**
     * SWFTOOLS安装路径
     */
    private static String SWFTOOLS_PATH;
    /**
     * 文件存放路径
     */
    private static String filepath;
    /**
     * OfficeManager管理
     */
    private static OfficeManager staticOfficeManager;

    private static OfficeDocumentConverter staticOfficeDocumentConverter;

    public OfficeUtils() {
    }
    static {
        Properties prop = new Properties();
        InputStream in = OfficeUtils.class.getResourceAsStream("/openoffice.properties");
        try {
            prop.load(in);
            OpenOffice_HOME = prop.getProperty("OpenOffice_HOME").trim();
            host = prop.getProperty("host").trim();
            port = prop.getProperty("port").trim();
            SWFTOOLS_PATH = prop.getProperty("SWFTOOLS_PATH").trim();
            filepath = prop.getProperty("filepath").trim();
            if(null!=filepath){
                filepath=filepath.replaceAll("\\\\","/");
            }
        } catch (IOException e) {
			logger.error("未能读取openoffice.properties文件",e);
        }
        //startOpenOfficeService();
    }
    public static String getOpenOffice_HOME() {
        return OpenOffice_HOME;
    }
    public static void setOpenOffice_HOME(String openOffice_HOME) {
        OpenOffice_HOME = openOffice_HOME;
    }
    public static String getHost() {
        return host;
    }
    public static void setHost(String host) {
        OfficeUtils.host = host;
    }
    public static String getPort() {
        return port;
    }
    public static void setPort(String port) {
        OfficeUtils.port = port;
    }

    public static String getSWFTOOLS_PATH() {
        return SWFTOOLS_PATH;
    }
    public static void setSWFTOOLS_PATH(String swftools_path) {
        SWFTOOLS_PATH = swftools_path;
    }

    public static String getFilepath() {
        return filepath;
    }
    public static void setFilepath(String filepath) {
        OfficeUtils.filepath = filepath;
    }
    /**
     * 将Office文档转换为PDF或者其他文件，已后缀名为准. 运行该函数需要用到OpenOffice, OpenOffice下载地址为 
     * http://www.openoffice.org/ 
     *
     * <pre> 
     * 方法示例: 
     * String sourcePath = "F:\\office\\source.doc"; 
     * String destFile = "F:\\pdf\\dest.pdf"; 
     * Converter.office2PDF(sourcePath, destFile); 
     * </pre> 
     *
     * @param sourceFile
     *            源文件, 绝对路径. 可以是Office2003-2007全部格式的文档, Office2010的没测试. 包括.doc, 
     *            .docx, .xls, .xlsx, .ppt, .pptx等. 示例: F:\\office\\source.doc 
     * @param destFile
     *            目标文件. 绝对路径. 示例: F:\\pdf\\dest.pdf 
     * @return 操作成功与否的提示信息. 如果返回 -1, 表示找不到源文件, 或url.properties配置错误; 如果返回 1,
     *         则表示操作成功; 返回0, 则表示转换失败
     */
    public static int office2Other(String sourceFile, String destFile) throws Exception{

        sourceFile = sourceFile.replaceAll("\\\\","/");
        destFile = destFile.replaceAll("\\\\","/");
        File inputFile = new File(sourceFile);
        if (!inputFile.exists()) {
            return -1;// 找不到源文件, 则返回-1
        }

        // 如果目标路径不存在, 则新建该路径
        File outputFile = new File(destFile);
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }
        /*
        // String OpenOffice_HOME = "C:\\Program Files\\OpenOffice 4";//这里是OpenOffice的安装目录, 在我的项目中,为了便于拓展接口,没有直接写成这个样子,但是这样是绝对没问题的
        // 如果从文件中读取的URL地址最后一个字符不是 '\'，则添加'\'
        if (OpenOffice_HOME.charAt(OpenOffice_HOME.length() - 1) != '\\') {
            OpenOffice_HOME += "\\";
        }
        // 启动OpenOffice的服务
        String command = OpenOffice_HOME.trim().replaceAll(" ","\" \"") + "program\\soffice.exe -headless -accept=\"socket,host="+host+",port="+port+";urp;\" -nofirststartwizard";
        command = "cmd /c start "+command;	//必须这样写，不然会报错
        Process pro = Runtime.getRuntime().exec(command);
        // connect to an OpenOffice.org instance running on port 8100
        OpenOfficeConnection connection = new SocketOpenOfficeConnection(host, new Integer(port));
        connection.connect();
        // convert
        DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
        converter.convert(inputFile, outputFile);

        // close the connection
        connection.disconnect();
        // 关闭OpenOffice服务的进程
        pro.destroy();
        */

        /* jodconvert 2.2
        OpenOfficeConnection openOfficeConnection = new SocketOpenOfficeConnection(host, new Integer(port));
        openOfficeConnection.connect();
        // convert
        DocumentConverter converter = new OpenOfficeDocumentConverter(openOfficeConnection);
        converter.convert(inputFile, outputFile);
        openOfficeConnection.disconnect();
        */

        // close the connection
        //jodconvert 3
        if (staticOfficeManager == null || !staticOfficeManager.isRunning()) {
            startOpenOfficeService();
        }

        try{
            if (staticOfficeManager != null && staticOfficeManager.isRunning()) {
                if(staticOfficeDocumentConverter==null){
                    staticOfficeDocumentConverter = new OfficeDocumentConverter(staticOfficeManager);
                }
                staticOfficeDocumentConverter.convert(inputFile, outputFile);
            }else{
                return 0;
            }
        }catch (Exception ex){
            staticOfficeManager=null;
            staticOfficeDocumentConverter=null;
            logger.error("文件转换时失败...", ex);
            return 0;
        }
        return 1;
    }
    public static synchronized void startOpenOfficeService(){
        if (staticOfficeManager != null && staticOfficeManager.isRunning()) {
            return;
        }
        OfficeManager officeManager=null;
        try {
            try {
                logger.info("尝试连接已启动的openoffice文件转换服务...");
                ExternalOfficeManagerConfiguration externalProcessOfficeManager = new ExternalOfficeManagerConfiguration();
                externalProcessOfficeManager.setConnectOnStart(true);
                externalProcessOfficeManager.setPortNumber(new Integer(port));
                officeManager = externalProcessOfficeManager.buildOfficeManager();
                officeManager.start();
                if(officeManager!=null && officeManager.isRunning()) {
                    logger.info("openoffice文件转换服务启动成功!");
                    staticOfficeManager = officeManager;
                    return;
                }
            } catch (Exception ex) {
                logger.error("连接已启动的openoffice文件转换服务失败！详细信息：", ex);
                officeManager=null;
            }

            logger.info("准备启动服务....");
            logger.info("准备启动安装在" + OpenOffice_HOME + "目录下的openoffice文件转换服务....");
            DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
            configuration.setOfficeHome(OpenOffice_HOME);//设置OpenOffice.org安装目录
            configuration.setPortNumber(new Integer(port)); //设置转换端口，默认为8100
            configuration.setTaskExecutionTimeout(1000 * 60 * 10L);//设置任务执行超时为10分钟
            configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);//设置任务队列超时为24小时

            officeManager = configuration.buildOfficeManager();
            officeManager.start();    //启动服务
            if(officeManager!=null && officeManager.isRunning()){
                logger.info("openoffice文件转换服务启动成功！");
                staticOfficeManager = officeManager;
            }
        } catch (Exception ex) {
            stopOpenOfficeService(officeManager);
            staticOfficeManager = null;
            logger.error("启动文件转换服务失败...", ex);
        }
    }

    /**
     * 关闭openoffice 服务
     */
    public static void stopOpenOfficeService(OfficeManager officeManager){
        if (officeManager != null) {
            logger.info("关闭office转换服务....");
            try {
                officeManager.stop();
                officeManager = null;
            }catch (Exception ex)
            {
                officeManager = null;
            }
            logger.info("关闭office转换成功!");
        }
    }


    /**
     * pdf文件转成swf文件
     * @param sourceFile
     * @param destFile
     * @return
     * @author chenwei
     * @date Sep 21, 2013
     */
    public static int pdf2swf(String sourceFile, String destFile) throws Exception{
        sourceFile = sourceFile.replaceAll( "\\\\","/");
        destFile = destFile.replaceAll( "\\\\","/");
        // 源文件不存在则返回
        File source = new File(sourceFile);
        if (!source.exists()) {
            return 0;
        }
        // 如果目标路径不存在, 则新建该路径
        File outputFile = new File(destFile);
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }
        String swftoolspath=SWFTOOLS_PATH.trim();
        swftoolspath=swftoolspath.replaceAll( "\\\\","/");
        // 调用pdf2swf命令进行转换
        String command = SWFTOOLS_PATH.trim().replaceAll(" ","\" \"") + "/pdf2swf.exe  -t \"" + sourceFile + "\" -o  \"" + destFile + "\" ";
        command = "cmd /c start "+command;	//必须这样写，不然会报错
        // 调用外部程序
        Runtime.getRuntime().exec(command);
        return 1;
    }
}

