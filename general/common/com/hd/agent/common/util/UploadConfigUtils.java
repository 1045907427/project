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
import java.util.Properties;

/**
 *
 * office文件转换
 * @author chenwei
 */
public final class UploadConfigUtils {
    protected static final Logger logger = Logger.getLogger(UploadConfigUtils.class);

    /**
     * 文件存放路径
     */
    private static String filepath;


    public UploadConfigUtils() {
    }
    static {
        Properties prop = new Properties();
        InputStream in = UploadConfigUtils.class.getResourceAsStream("/openoffice.properties");
        try {
            prop.load(in);
            filepath = prop.getProperty("filepath").trim();
            if(null!=filepath){
                filepath=filepath.replaceAll("\\\\","/");
            }
        } catch (IOException e) {
			logger.error("未能读取openoffice.properties文件",e);
        }
        //startOpenOfficeService();
    }
    public static String getFilepath() {
        return filepath;
    }
    public static void setFilepath(String filepath) {
        UploadConfigUtils.filepath = filepath;
    }
}

