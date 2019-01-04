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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;

import com.hd.agent.agprint.model.PrintJobDetailImage;
import com.hd.agent.agprint.service.IPrintJobService;
import com.hd.agent.common.util.SpringContextUtils;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.json.JSONObject;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id$
 */
public class DBHtmlResourceHandler implements HtmlResourceHandler
{
	private String pathPattern;

	private String imageDBId;
	private String agprintParamsData;

	/**
	 * 
	 */
	public DBHtmlResourceHandler(String pathPattern)
	{
		this.pathPattern = pathPattern;
	}
	public DBHtmlResourceHandler(String pathPattern,String agprintParamsData){
		this.pathPattern = pathPattern;
		this.agprintParamsData=agprintParamsData;
	}

	/**
	 * 
	 */
	public String getResourcePath(String id)
	{
		if(imageDBId==null || "".equals(imageDBId.trim())){
			return MessageFormat.format(pathPattern, new Object[]{id});
		}
		return MessageFormat.format(pathPattern, new Object[]{imageDBId});
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
			printJobDetailImage.setContent(data);
			fillUserData(printJobDetailImage);
			if(printJobService.addPrintJobDetailImage(printJobDetailImage)) {
				imageDBId = printJobDetailImage.getId();
			}
		}catch (Exception ex){

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
