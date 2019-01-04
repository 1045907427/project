/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2013 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.jasperreports.engine.export;

import com.hd.agent.agprint.model.PrintJobDetailImage;
import com.hd.agent.agprint.service.IPrintJobService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.common.util.UploadConfigUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.text.MessageFormat;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id$
 */
public class DBFileHtmlResourceHandler implements HtmlResourceHandler
{
	private static final Logger logger = Logger.getLogger(DBFileHtmlResourceHandler.class);
	private String weburlpath;
	private String filefullpath;
	private String imageDBId;
	private String agprintParamsData;

	/**
	 *
	 */
	public DBFileHtmlResourceHandler(String weburlpath)
	{
		this.weburlpath = weburlpath;
	}
	public DBFileHtmlResourceHandler(String weburlpath,String agprintParamsData){
		this.weburlpath = weburlpath;
		this.agprintParamsData=agprintParamsData;
	}
	/**
	 * 
	 */
	public String getResourcePath(String id)
	{
		return weburlpath+"/"+filefullpath;
	}

	/**
	 * 
	 */
	public void handleResource(String id, byte[] data)
	{
		try {
			IPrintJobService printJobService = (IPrintJobService) SpringContextUtils.getBean("printJobService");
			PrintJobDetailImage printJobDetailImage = new PrintJobDetailImage();
			printJobDetailImage.setName(id);
			//printJobDetailImage.setContent(data);
			fillUserData(printJobDetailImage);
			String filepath = UploadConfigUtils.getFilepath();
			String subPath = CommonUtils.getYearMonthDayDirPath();	//年月路径 格式yyyy/MM/dd
			String dirpath = filepath + "/printimg/" + subPath;
			File dirFile = new File(dirpath);
			if(dirFile!=null&&!dirFile.exists()){
				dirFile.mkdirs();
			}
			String fileseqid=printJobService.doCreatePrintJobDetailImageFileId();
			if(StringUtils.isEmpty(fileseqid)){
				fileseqid=CommonUtils.getDataNumberWithRand();
			}
			String filename=id+fileseqid+".png";
			String imgRealFilePath=dirpath+"/"+filename;
			String fullpath="upload/printimg/"+subPath+"/"+filename;
			File imageFile = new File(imgRealFilePath);
			//创建输出流
			FileOutputStream outStream = new FileOutputStream(imageFile);
			//写入数据
			outStream.write(data);
			//关闭输出流
			outStream.close();
			printJobDetailImage.setFullpath(fullpath);
			filefullpath=fullpath;
			if(printJobService.addPrintJobDetailImage(printJobDetailImage)) {
				imageDBId = printJobDetailImage.getId();
			}
		}catch (Exception ex){
			logger.error("生成二维码图片失败",ex);
		}
	}

	private void fillUserData(PrintJobDetailImage printJobDetailImage){
		try{
			if(null!=agprintParamsData && !"".equals(agprintParamsData.trim())) {
				JSONObject agpagedata = JSONObject.fromObject(agprintParamsData.trim());
				if (agpagedata != null) {
					if (agpagedata.containsKey("printJobId")) {
						printJobDetailImage.setJobid((String) agpagedata.get("printJobId"));
					}
				}
			}
		}catch (Exception ex){

		}
	}

}
