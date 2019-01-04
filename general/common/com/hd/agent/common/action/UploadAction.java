/**
 * @(#)UploadAction.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-3-18 zhengziyong 创建版本
 */
package com.hd.agent.common.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.common.model.AttachFile;
import com.hd.agent.common.service.IAttachFileService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.MenuPropertiesUtils;
import com.hd.agent.common.util.OfficeUtils;
import com.hd.agent.system.model.SysParam;
import com.hd.agent.system.service.ISysParamService;
import com.hd.agent.system.service.ITaskScheduleService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * 
 * 
 * @author zhengziyong
 */
public class UploadAction extends BaseAction {
	
	private IAttachFileService attachFileService;
	private ITaskScheduleService taskScheduleService;
	private ISysParamService sysParamService;
	
	private int imgwidth;  
    private int imgheight;

	public int getImgwidth() {
		return imgwidth;
	}

	public void setImgwidth(int imgwidth) {
		this.imgwidth = imgwidth;
	}

	public int getImgheight() {
		return imgheight;
	}

	public void setImgheight(int imgheight) {
		this.imgheight = imgheight;
	}

	public ITaskScheduleService getTaskScheduleService() {
		return taskScheduleService;
	}

	public void setTaskScheduleService(ITaskScheduleService taskScheduleService) {
		this.taskScheduleService = taskScheduleService;
	}

	public ISysParamService getSysParamService() {
		return sysParamService;
	}

	public void setSysParamService(ISysParamService sysParamService) {
		this.sysParamService = sysParamService;
	}

	/**
	 * 文件上传页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date 2013-3-19
	 */
	public String uploadPage() throws Exception{
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		request.setAttribute("scheme", request.getScheme());
		request.setAttribute("serverUrl", request.getServerName());
		request.setAttribute("port", request.getServerPort());
		request.setAttribute("contextPath", request.getContextPath());
		return SUCCESS;
	}
	
	/**
	 * 文件上传
	 * @throws Exception
	 * @author zhengziyong 
	 * @date 2013-3-19
	 */
	public void upload() throws Exception{
        String converthtml = request.getParameter("converthtml");
        String convertpdf = request.getParameter("convertpdf");
		MultiPartRequestWrapper requestWrapper = (MultiPartRequestWrapper)request;
		File[] files = requestWrapper.getFiles("file");
		String[] fileNames = requestWrapper.getFileNames("file");
		//文件存放路径
		String filepath = OfficeUtils.getFilepath();
		String subPath = CommonUtils.getYearMonthDayDirPath();	//年月路径 格式yyyy/MM/dd
		String path = filepath + "/" + subPath;
        String imgtypes = ".gif,.jpg,.jpeg,.bmp,.png";
		for(int i=0; i<files.length; i++){
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Random rand = new Random();
			String randString = "" + rand.nextInt(100000);
			String fileName = dateFormat.format(new Date()) + randString;
			String oldFileName = fileNames[i];
			String ext = oldFileName.substring(oldFileName.lastIndexOf("."));
			String newFileName = fileName + ext;
			String pdfFileName = fileName+".pdf";
			String htmlFileName = fileName+".html";
			String swfFileName = fileName+".swf";
			File file = new File(path, newFileName);
            CommonUtils.fileChannelCopy(files[i], file);
//			FileUtils.copyFile(files[i], file);
			String fullPath = "upload/" + subPath + "/" + newFileName;
			AttachFile attachFile = new AttachFile();
			attachFile.setExt(ext);
			attachFile.setFilename(newFileName);
			attachFile.setFullpath(fullPath);
			attachFile.setOldfilename(oldFileName);
			String pdfpath = "/pdf/"+ subPath + "/" + pdfFileName;
			String htmlpath = "/html/" + subPath + "/" + htmlFileName;
			String swfpath = "/swf/" + subPath + "/" + swfFileName;
			if(".pdf".equals(ext) || ("true".equals(convertpdf) && imgtypes.indexOf(ext.toLowerCase()) == -1)){
				attachFile.setPdfpath("upload"+pdfpath);
			}
            if("true".equals(converthtml) && imgtypes.indexOf(ext.toLowerCase()) == -1){
                if(".ppt".equals(ext) || ".pptx".equals(ext)){
                    attachFile.setPdfpath("upload"+pdfpath);
                }else{
                    attachFile.setHtmlpath("upload"+htmlpath);
                }
            }
			String id = insertFile(attachFile);
//			if(StringUtils.isNotEmpty(id) &&
//					( ".doc".equals(ext) || ".docx".equals(ext)
//					|| ".xls".equals(ext) || ".xlsx".equals(ext)
//					|| ".ppt".equals(ext) || ".pptx".equals(ext)
//					|| ".txt".equals(ext))){
//                //定时器转换为pdf
//                if(("true".equals(convertpdf) || ("true".equals(converthtml) && (".ppt".equals(ext) || ".pptx".equals(ext)))) && (!".xls".equals(ext) && !".xlsx".equals(ext)) && imgtypes.indexOf(ext.toLowerCase()) == -1){
//                    try{
//                        Calendar calendar=Calendar.getInstance();
//                        calendar.add(Calendar.MINUTE,1);
//                        String con = CommonUtils.getQuartzCronExpression(calendar.getTime());
//                        Map<String,Object> dataMap = new HashMap<String,Object>();
//                        dataMap.put("id",id);
//                        taskScheduleService.addTaskScheduleAndStart(CommonUtils.getDateNowTimeWithRand()  ,"文件上传格式转换PDF(按单次计划)", "com.hd.agent.common.job.AttachOneConvertJob", "AttachOneConvertJob", con, "1",dataMap);
//
//                        Calendar today= Calendar.getInstance();
//                        int hour=today.HOUR_OF_DAY;
//                        Map<String,Object> queryMap=new HashMap<String, Object>();
//                        queryMap.put("jobcondition", "1");
//                        if(8<hour && hour<23){ //8~23内，计算当天转换
//                            queryMap.put("interdate", 0);
//                        }else{ //计算3内天转换
//                            queryMap.put("interdate", 3);
//                        }
//                        //转换次数
//                        queryMap.put("wiconverts", 3);
//                        List<AttachFile> list=attachFileService.getAttachFileList(queryMap);
//                    }catch(Exception ex){
//                        logStr="文件上传格式转换PDF(按单次计划)失败，"+ex.getMessage();
//                    }
//                }
//                //转换为html
//                if("true".equals(converthtml) && imgtypes.indexOf(ext.toLowerCase()) == -1){
//                    try{
//                        Calendar calendar=Calendar.getInstance();
//                        calendar.add(Calendar.MINUTE,1);
//                        String con = CommonUtils.getQuartzCronExpression(calendar.getTime());
//                        Map<String,Object> dataMap = new HashMap<String,Object>();
//                        String src = path + "/" + newFileName;
//                        String dis = filepath + htmlpath;
//                        dataMap.put("src",src);
//                        dataMap.put("dis",dis);
//                        dataMap.put("ext",ext);
//                        taskScheduleService.addTaskScheduleAndStart( CommonUtils.getDateNowTimeWithRand()  ,"文件上传格式转换html(按单次计划)", "com.hd.agent.common.job.AttachOneConvertHtmlJob", "AttachOneConvertHtmlJob", con, "1",dataMap);
//
//                    }catch(Exception ex){
//                        logStr="文件上传格式转换html(按单次计划)，"+ex.getMessage();
//                    }
//                }
//			}
			if(files[i].length() == file.length()){
				response.setCharacterEncoding("utf-8");
				response.getWriter().write("{\"flag\":\"1\",\"id\":\"" + id + "\",\"fullPath\":\"" + fullPath + "\",\"oldFileName\":\""+oldFileName+"\",\"newFileName\":\""+newFileName+"\"}");
			}
		}
	}
	
	/**
	 * 文件下载
	 * @throws Exception
	 * @author zhengziyong 
	 * @date 2013-3-19
	 */
	public void download() throws Exception{
		String id = request.getParameter("id");
		AttachFile attachFile = attachFileService.getAttachFile(id);
		String path = attachFile.getFullpath();
		//文件存放路径
		String filepath = OfficeUtils.getFilepath();
		String fullPath = CommonUtils.getDownFilePhysicalpath(filepath,path);
		File file = new File(fullPath);
		if(null==file || !file.exists()){
			return;
		}
		InputStream is = new FileInputStream(file);
		if(null==is){
			return;
		}
//		if(!canOperate(attachFile)){
//			String tip = "您没有权限下载该文件！";
//			is = new ByteArrayInputStream(tip.getBytes());
//		}
		int len = -1;
		byte[] b = new byte[1024];
		response.addHeader("Content-Length", "" + file.length());
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");
		String fileName = attachFile.getOldfilename();
		String userAgent = request.getHeader("User-Agent");
		//针对IE或者以IE为内核的浏览器：
		if (userAgent.contains("MSIE")||userAgent.contains("Trident")) {
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		} else {
			//非IE浏览器的处理：
			fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
		}
		response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
		OutputStream os = response.getOutputStream();
		while((len = is.read(b)) != -1){
			os.write(b, 0, len);
		}
		os.flush();
		is.close();
		os.close(); 
	}
	/**
	 * 文件查看
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 21, 2013
	 */
	public String viewFile() throws Exception{
		String id = request.getParameter("id");
		AttachFile attachFile = attachFileService.getAttachFile(id);
		if(null!=attachFile && !"".equals(attachFile.getHtmlpath())){
			addJSONObject("file", attachFile);
		}else{
			addJSONObject("file", null);
		}
		return "success";
	}
	/**
	 * 文件查看（flash形式）
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 21, 2013
	 */
	public String viewFileFlash() throws Exception{
		String id = request.getParameter("id");
		AttachFile attachFile = attachFileService.getAttachFile(id);
		String WebOffice = getSysParamValue("WebOffice");
		if("1".equals(WebOffice)){
            if(null!=attachFile){
                if(".pdf".equals(attachFile.getExt())){
                    if(StringUtils.isNotEmpty(attachFile.getPdfpath())){
                        response.sendRedirect("../"+attachFile.getPdfpath());
                    }else{
                        response.sendRedirect("../"+attachFile.getFullpath());
                    }
                    return null;
                }
                if(StringUtils.isNotEmpty(attachFile.getPdfpath())){
                    response.sendRedirect("../"+attachFile.getPdfpath());
                    return null;
                }
                if(".xls".equals(attachFile.getExt())
                        || ".xlsx".equals(attachFile.getExt())
                        || ".doc".equals(attachFile.getExt())
                        || ".docx".equals(attachFile.getExt())){
                    if(StringUtils.isNotEmpty(attachFile.getHtmlpath())){
                        response.sendRedirect("../"+attachFile.getHtmlpath());
                        return null;
                    }else{
                        response.sendRedirect("../"+attachFile.getFullpath());
                        return null;
                    }
                }else if(".jpg".equals(attachFile.getExt()) || ".gif".equals(attachFile.getExt()) || ".png".equals(attachFile.getExt())
                        || ".bmp".equals(attachFile.getExt()) || ".jpeg".equals(attachFile.getExt())){
                    response.sendRedirect("../"+attachFile.getFullpath());
                    return null;
                }
                if(StringUtils.isNotEmpty( attachFile.getSwfpath())){
                    request.setAttribute("url", attachFile.getSwfpath());
                }else{
                    response.sendRedirect("../"+attachFile.getFullpath());
                    return null;
                }
            }
            return "success";
        }else if("2".equals(WebOffice)){
            if(".xls".equals(attachFile.getExt())
                    || ".xlsx".equals(attachFile.getExt())
                    || ".doc".equals(attachFile.getExt())
                    || ".docx".equals(attachFile.getExt())
                    || ".pdf".equals(attachFile.getExt())
                    || ".pdf".equals(attachFile.getExt())
                    || ".pptx".equals(attachFile.getExt())
                    || ".ppt".equals(attachFile.getExt())){
                //永中在线文件查看API地址
                String YongZhongWebApi = getSysParamValue("YongZhongWebApi");
                if(StringUtils.isNotEmpty(YongZhongWebApi)){
                    String FileWebUrl = getSysParamValue("FileWebUrl");
                    if(!FileWebUrl.endsWith("/")){
                        FileWebUrl += "/";
                    }
                    YongZhongWebApi += "&url="+FileWebUrl+attachFile.getFullpath();
                    response.sendRedirect(YongZhongWebApi);
                }
            }else if(".jpg".equals(attachFile.getExt()) || ".gif".equals(attachFile.getExt()) || ".png".equals(attachFile.getExt())
                    || ".bmp".equals(attachFile.getExt()) || ".jpeg".equals(attachFile.getExt())){
                response.sendRedirect("../"+attachFile.getFullpath());
                return null;
            }else{
                response.sendRedirect("../"+attachFile.getFullpath());
                return null;
            }
        }else if("0".equals(WebOffice)){
            response.sendRedirect("download.do?id="+id);
        }
        return null;
	}
	/**
	 * 根据文件编号获取文件信息
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date 2013-3-19
	 */
	public String getAttachFile() throws Exception{
		String id = request.getParameter("id");
		AttachFile file = attachFileService.getAttachFile(id);
		if(file == null){
			addJSONObject("file", null);
			return SUCCESS;
		}
		if(canOperate(file)){
			addJSONObject("file", file);
		}
		else{
			addJSONObject("file", null);
		}
		return SUCCESS;
	}
	
	/**
	 * 删除文件，多文件删除将编号以,号隔开，如1,2,3
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 2, 2013
	 */
	public String deleteAttachFile() throws Exception{
		String id = request.getParameter("id");
		AttachFile attachFile = attachFileService.getAttachFile(id);
		if(attachFileService.deleteAttachFile(id)){
			Map map = new HashMap();
			map.put("flag", true);
			map.put("file", attachFile);
			addJSONObject(map);
		}
		else{
			Map map = new HashMap();
			map.put("flag", false);
			addJSONObject(map);
		}
		return SUCCESS;
	}
	
	/**
	 * 删除商品图片，删除成功后直接更新商品档案中的图片信息
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 9, 2015
	 */
	public String deleteGoodsAttachFile()throws Exception{
		String id = request.getParameter("id");
		String goodsid = request.getParameter("goodsid");
		AttachFile attachFile = attachFileService.getAttachFile(id);
		Map map = attachFileService.deleteGoodsAttachFile(id, goodsid);
        if(map.get("flag").equals(true)){
            map.put("file", attachFile);
            GoodsInfo goodsInfo = getBaseGoodsService().showGoodsInfo(goodsid);
            if(null != goodsInfo){
                //文件存放路径
                String filepath = OfficeUtils.getFilepath();
                //存放当前文件夹下
                String path = filepath + "/goods/big/";
				path=path.replaceAll("\\\\","/");
                attachFileService.updateGoodsimgs(goodsInfo,path);
            }
            attachFileService.deleteAttachFileDir(attachFile.getFilename());
        }
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 判断该文件该用户是否可操作（列表中查看到或下载）
	 * @param file
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date 2013-3-19
	 */
	private boolean canOperate(AttachFile file) throws Exception{
		SysUser user = getSysUser();
		List<String> authList=getUserAuthorityList();
		return canOperate(file, user, authList);
	}
	/**
	 * 判断该文件该用户是否可操作（列表中查看到或下载）
	 * @param file
	 * @param user
	 * @param authList 数组
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date 2013-3-19
	 */
	private boolean canOperate(AttachFile file,SysUser user,List<String> authList) throws Exception{
		if(file.getOpdept() == null && file.getOprule() == null || file.getOpuser() == null){
			return true;
		}
		return attachFileService.canOperate(file, user, authList);
	}
	
	private String insertFile(AttachFile file) throws Exception{
		if(file!=null){
			if(StringUtils.isNotEmpty(file.getFullpath())) {
				file.setFullpath(file.getFullpath().replaceAll("\\\\", "/"));
			}
		}
		attachFileService.addAttachFile(file);
		return file.getId();
	}


	public IAttachFileService getAttachFileService() {
		return attachFileService;
	}

	public void setAttachFileService(IAttachFileService attachFileService) {
		this.attachFileService = attachFileService;
	}
	
	
	/**
	 * kindeditor文件上传
	 * @throws Exception
	 * @author zhengziyong 
	 * @date 2013-3-19
	 */
	public void kindEditorUpload() throws Exception{
		//定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
		
		//最大文件大小
		long maxSize = 10000000;
		
		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		response.setCharacterEncoding("utf-8");
		//msg
		StringBuilder sBuilder=new StringBuilder();
		if(!extMap.containsKey(dirName)){
			sBuilder.append("{");
			sBuilder.append("\"error\":1,");
			sBuilder.append("\"message\":\"目录不存在\"");

			sBuilder.append("}");
			response.getWriter().write(sBuilder.toString());
			return;
		}
		
		MultiPartRequestWrapper requestWrapper = (MultiPartRequestWrapper)request;
		
		File[] files = requestWrapper.getFiles("imgFile");
		String[] fileNames = requestWrapper.getFileNames("imgFile");
		//文件存放路径
		String filepath = OfficeUtils.getFilepath();
		String subPath = CommonUtils.getYearMonthDayDirPath();	//年月路径 格式yyyy/MM/dd
		String path = filepath + "/" + subPath;
		
		String hostpath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		for(int i=0; i<files.length; i++){

			String oldFileName = fileNames[i];
			String ext = oldFileName.substring(oldFileName.lastIndexOf("."));
			//检查文件大小
			if(files[i].length() > maxSize){
				sBuilder.append("{");
				sBuilder.append("\"error\":1,");
				sBuilder.append("\"message\":");
				sBuilder.append("\"上传文件大小超过限制。");
				sBuilder.append("\"");

				sBuilder.append("}");
				response.getWriter().write(sBuilder.toString());
				continue;
			}
			//检查扩展名
			String fileExt = ext.substring(ext.lastIndexOf(".") + 1).toLowerCase();
			if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt.toLowerCase())){
				sBuilder.append("{");
				sBuilder.append("\"error\":1,");
				sBuilder.append("\"message\":");
				sBuilder.append("\"上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。");
				sBuilder.append("\"");

				sBuilder.append("}");
				response.getWriter().write(sBuilder.toString());
				continue;
			}
			String newFileName = CommonUtils.getDateTimeUUID() + ext;
			File file = new File(path, newFileName);
			FileUtils.copyFile(files[i], file);
			String fullPath = "/upload/" + subPath + "/" + newFileName;
			AttachFile attachFile = new AttachFile();
			attachFile.setExt(ext);
			attachFile.setFilename(newFileName);
			attachFile.setFullpath(fullPath);
			attachFile.setOldfilename(oldFileName);
			String id = insertFile(attachFile);
			if(files[i].length() == file.length()){

				sBuilder.append("{");
				sBuilder.append("\"error\":0,");
				sBuilder.append("\"url\":");
				sBuilder.append("\"");
				fullPath=hostpath+"/"+fullPath;
				sBuilder.append(fullPath);
				sBuilder.append("\"");

				sBuilder.append("}");
				response.getWriter().write(sBuilder.toString());
			}
		}
	}	
	/**
	 * 根据文件编号获取文件信息
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date 2013-3-19
	 */
	public String getAttachFileList() throws Exception{
		String idarrs = request.getParameter("idarrs");
		Map<String, Object> map= new HashMap<String, Object>();
		map.put("idarrs", idarrs);
		List<AttachFile> list= attachFileService.getAttachFileList(map);
		if(null==list || list.size() == 0){
			addJSONArray(new ArrayList<AttachFile>());
			return SUCCESS;
		}
		SysUser user=getSysUser();
		List<String> authList=getUserAuthorityList();
		List<AttachFile> resultList=new ArrayList<AttachFile>();
		for(AttachFile item : list ){
			if(canOperate(item,user,authList)){
				resultList.add(item);
			}
		}
		addJSONArray(resultList);
		return SUCCESS;
	}

	
	/**
	 * 打印模板文件上传
	 * @throws Exception
	 * @author zhengziyong 
	 * @date 2013-3-19
	 */
	public void uploadPrintTemplet() throws Exception{
		MultiPartRequestWrapper requestWrapper = (MultiPartRequestWrapper)request;
		File[] files = requestWrapper.getFiles("file");
		String[] fileNames = requestWrapper.getFileNames("file");
		String prefix=request.getParameter("prefix");
		if(null==prefix || "".equals(prefix.trim())){
			prefix="";
		}else{
			prefix=prefix.trim();
			if(!prefix.matches("([a-zA-Z0-9_]|-)+")){
				prefix="";
			}
		}
		//文件存放路径
		String filepath = OfficeUtils.getFilepath();
		String subPath = CommonUtils.getYearMonthDayDirPath();	//年月路径 格式yyyy/MM/dd
		String path = filepath +"/printtemplet/" + subPath;
		for(int i=0; i<files.length; i++){
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Random rand = new Random();
			String randString = "" + rand.nextInt(100000);
			String fileName = dateFormat.format(new Date()) + randString;
			if(!"".equals(prefix)){
				fileName=prefix+"_"+fileName;
			}
			String oldFileName = fileNames[i];
			String ext = oldFileName.substring(oldFileName.lastIndexOf("."));
			String newFileName = fileName + ext;
			File file = new File(path, newFileName);
            CommonUtils.fileChannelCopy(files[i], file);
			String fullPath = "upload/printtemplet/" + subPath + "/" + newFileName;
			AttachFile attachFile = new AttachFile();
			attachFile.setExt(ext);
			attachFile.setFilename(newFileName);
			attachFile.setFullpath(fullPath);
			attachFile.setOldfilename(oldFileName);
			String id = insertFile(attachFile);
			if(files[i].length() == file.length()){
				response.setCharacterEncoding("utf-8");
				response.getWriter().write("{\"flag\":\"1\",\"id\":\"" + id + "\",\"fullPath\":\"" + fullPath + "\",\"oldFileName\":\""+oldFileName+"\",\"newFileName\":\""+newFileName+"\"}");
			}
		}
	}
	
	/** 
     * 按照宽度还是高度进行压缩
     * @param map
     */
    public void resizeFix(Map map) throws Exception {
    	Integer w = null != map.get("w") ? (Integer)map.get("w") : 0;
    	Integer h = null != map.get("h") ? (Integer)map.get("h") : 0;
        if (imgwidth / imgheight > w / h) {  
            resizeByWidth(map);  
        } else {  
            resizeByHeight(map);  
        }  
    }  
    /** 
     * 以宽度为基准，等比例放缩图片
     * @param map
     */
    public void resizeByWidth(Map map) throws Exception {
    	Integer w = null != map.get("w") ? (Integer)map.get("w") : 0;
    	Integer h = null != map.get("h") ? (Integer)map.get("h") : 0;
        h = (int) (imgheight * w / imgwidth);
        map.put("h", h);
        resize(map);  
    }  
    /** 
     * 以高度为基准，等比例缩放图片
     * @param map
     */
    public void resizeByHeight(Map map) throws Exception {
    	Integer w = null != map.get("w") ? (Integer)map.get("w") : 0;
    	Integer h = null != map.get("h") ? (Integer)map.get("h") : 0;
        w = (int) (imgwidth * h / imgheight);
        map.put("w", w);
        resize(map);  
    }  
    /** 
     * 强制压缩/放大图片到固定的大小
     * @param map
     */
    public void resize(Map map) throws Exception {  
    	String path = null != map.get("path") ? (String)map.get("path") : "";
    	Image img = null != map.get("img") ? (Image)map.get("img") : null;
    	Integer w = null != map.get("w") ? (Integer)map.get("w") : 0;
    	Integer h = null != map.get("h") ? (Integer)map.get("h") : 0;
    	String size = null != map.get("size") ? (String)map.get("size") : "";
    	String fileName = null != map.get("fileName") ? (String)map.get("fileName") : "";
    	// SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢  
        BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB );   
        image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
        String path2 = path;
        if("s".equals(size)){//小图
        	path2 = path2 + "/goods/phone/";
        }else if("m".equals(size)){
        	path2 = path2 + "/goods/middle/";
        }
        File file = new File(path2);
        //判断文件夹是否存在,如果不存在则创建文件夹
        if (!file.exists()) {
        	file.mkdir();
        }
        File destFile = null;
        if("s".equals(size)){//小图
        	destFile = new File(path2, fileName);
        }else if("m".equals(size)){
        	destFile = new File(path2, fileName);
        }else if("l".equals(size)){
        	destFile = new File(path2, fileName);
        }
        FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流  
        // 可以正常实现bmp、png、gif转jpg  
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        encoder.encode(image); // JPEG编码  
        out.close(); 
    } 
	
	/**
	 * 图片压缩
	 * @param fileName
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 7, 2015
	 */
	public void imgCompress(String path,String fileName,int w, int h,String size)throws Exception{
		File file = new File(path+"/goods/big/", fileName);
		Image img = ImageIO.read(file);
		imgwidth = img.getWidth(null);    // 得到源图宽  
		imgheight = img.getHeight(null);  // 得到源图长
		Map map = new HashMap();
		map.put("path", path);
		map.put("img", img);
		map.put("w", w);
		map.put("h", h);
		map.put("size", size);
		map.put("fileName", fileName);
		resizeFix(map);
	}

	/**
	 * 上传图片（图片压缩成三种大小：L、M、S）
	 * S：phone,M:imgM,L:imgL
	 * @author panxiaoxiao 
	 * @date Feb 7, 2015
	 */
	public void uploadGoodsImage()throws Exception{
        String goodsid = request.getParameter("goodsid");
        MultiPartRequestWrapper requestWrapper = (MultiPartRequestWrapper)request;
        File[] files = requestWrapper.getFiles("file");
        String[] fileNames = requestWrapper.getFileNames("file");
        //文件存放路径
        String filepath = OfficeUtils.getFilepath();
        //存放当前文件夹下
        String path = filepath + "/goods/big/";
        for(int i=0; i<files.length; i++){
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Random rand = new Random();
            String randString = "" + rand.nextInt(100000);
            String fileName = dateFormat.format(new Date()) + randString;
            String oldFileName = fileNames[i];
            String ext = ".jpg";

            //判断是否已存在商品编码为名称的图片，已存在，则将上传时间做图片名称，否则以商品编码做图片名称，且其为主图
            if(null == goodsid || "" == goodsid || "null" == goodsid){
                String oldFileNameSub = oldFileName.substring(0, oldFileName.lastIndexOf("."));
                goodsid = oldFileNameSub;
            }


            GoodsInfo goodsInfo = getBaseGoodsService().showGoodsInfo(goodsid);
            if(null==goodsInfo){
                goodsInfo =  getBaseGoodsService().getGoodsInfoByBarcode(goodsid);
                if(null!=goodsInfo){
                    goodsid = goodsInfo.getId();
                }
            }

            //新上传的文件
            String newFileName = goodsid+ext;
            if(null != goodsInfo){
                File file1 = new File(path, newFileName);
                if(file1.exists()){
                    boolean flag = attachFileService.isExistAttachFileByFilename(newFileName);
                    if(flag){
                        newFileName = goodsid+fileName + ext;
                    }else{
                        file1.delete();
                    }
                }
                File file2 = new File(path, newFileName);
                CommonUtils.fileChannelCopy(files[i], file2);
                //压缩图片
                imgCompress(filepath, newFileName, 200, 120, "s");
                imgCompress(filepath,newFileName,800, 600,"m");
                String fullPath = "upload/goods/big/" + newFileName;
                AttachFile attachFile = new AttachFile();
                attachFile.setExt(ext);
                attachFile.setFilename(newFileName);
                attachFile.setFullpath(fullPath);
                attachFile.setOldfilename(oldFileName);
                String id = insertFile(attachFile);

                attachFileService.updateGoodsimgs(goodsInfo,path);

                if(files[i].length() == file2.length()){
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write("{\"flag\":\"1\",\"id\":\"" + id + "\",\"fullPath\":\"" + fullPath + "\",\"oldFileName\":\""+oldFileName+"\",\"newFileName\":\""+newFileName+"\"}");
                }
            }else{
                response.getWriter().write("{\"nogoodsidimg\":\"" + oldFileName + "\"}");
            }
        }
    }
	
	/**
	 * 显示webuploader上传页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2015年6月5日
	 */
	public String showWebuploaderPage()throws Exception{
		String isimgtype = request.getParameter("isimgtype");
		request.setAttribute("isimgtype", isimgtype);
		String opeatype = request.getParameter("opeatype");
		request.setAttribute("opeatype", opeatype);
		List<AttachFile> filelist = new ArrayList<AttachFile>();
		//上传的模块
		String uploadmode = request.getParameter("uploadmode");
		request.setAttribute("uploadmode", uploadmode);
		if("person".equals(uploadmode)){
			String personid = request.getParameter("personid");
			request.setAttribute("personid", personid);
			filelist = attachFileService.getAttachFilesListByPersonid(personid);
		}
		request.setAttribute("filelist", filelist);
		request.setAttribute("filenum", filelist.size());
		return SUCCESS;
	}
	
	/**
	 * 显示上传页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2015年6月8日
	 */
	public String showCommonWebuploaderPage()throws Exception{
		return SUCCESS;
	}

    public String importHtmlPage() throws Exception{
        return SUCCESS;
    }

	/**
	 * 上传菜单配置文件
	 * @throws Exception
	 * @author zhengziyong
	 * @date 2013-3-19
	 */
	public void uploadMenuProperties() throws Exception{
		String converthtml = request.getParameter("converthtml");
		String convertpdf = request.getParameter("convertpdf");
		MultiPartRequestWrapper requestWrapper = (MultiPartRequestWrapper)request;
		File[] files = requestWrapper.getFiles("file");
		String[] fileNames = requestWrapper.getFileNames("file");
		//文件存放路径
		String filepath = OfficeUtils.getFilepath();
		String path = filepath + "/";
		String imgtypes = ".gif,.jpg,.jpeg,.bmp,.png";
		for(int i=0; i<files.length; i++){
			String fileName =CommonUtils.MD5(getSysParamValue("COMPANYNAME")) ;
			String oldFileName = fileNames[i];
			String ext = oldFileName.substring(oldFileName.lastIndexOf("."));
			String newFileName = fileName + ext;
			String pdfFileName = fileName+".pdf";
			String htmlFileName = fileName+".html";
			String swfFileName = fileName+".swf";
			File file = new File(path, newFileName);
			CommonUtils.fileChannelCopy(files[i], file);
//			FileUtils.copyFile(files[i], file);
			String fullPath = "upload/" + newFileName;
			AttachFile attachFile = new AttachFile();
			attachFile.setExt(ext);
			attachFile.setFilename(newFileName);
			attachFile.setFullpath(fullPath);
			attachFile.setOldfilename(oldFileName);
			String pdfpath = "/pdf/"+ "/" + pdfFileName;
			String htmlpath = "/html/" + "/" + htmlFileName;
			String swfpath = "/swf/" + "/" + swfFileName;
			if(".pdf".equals(ext) || ("true".equals(convertpdf) && imgtypes.indexOf(ext.toLowerCase()) == -1)){
				attachFile.setPdfpath("upload"+pdfpath);
			}
			if("true".equals(converthtml) && imgtypes.indexOf(ext.toLowerCase()) == -1){
				if(".ppt".equals(ext) || ".pptx".equals(ext)){
					attachFile.setPdfpath("upload"+pdfpath);
				}else{
					attachFile.setHtmlpath("upload"+htmlpath);
				}
			}
			String id = insertFile(attachFile);
			if(files[i].length() == file.length()){
				response.setCharacterEncoding("utf-8");
				SysParam sysParam=sysParamService.getSysParam("COMPANYNAME");
				String companyname = sysParam.getPvalue();
				Map map = MenuPropertiesUtils.readPropertiesFileNewAesDecrypt(OfficeUtils.getFilepath()+"/"+CommonUtils.MD5(companyname)+".properties");
				boolean flag = (Boolean) map.get("flag");
				response.getWriter().write("{\"flag\":\""+flag+"\",\"id\":\"" + id + "\",\"fullPath\":\"" + fullPath + "\",\"oldFileName\":\""+oldFileName+"\",\"newFileName\":\""+newFileName+"\"}");
			}
		}
	}

}

