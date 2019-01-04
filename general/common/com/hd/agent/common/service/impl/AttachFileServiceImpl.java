/**
 * @(#)AttachFileServiceImpl.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-3-18 zhengziyong 创建版本
 */
package com.hd.agent.common.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.common.dao.AttachFileMapper;
import com.hd.agent.common.model.AttachFile;
import com.hd.agent.common.service.IAttachFileService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelFileUtils;
import com.hd.agent.common.util.FileUtils;
import com.hd.agent.common.util.OfficeUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 
 * @author zhengziyong
 */
public class AttachFileServiceImpl extends BaseServiceImpl implements IAttachFileService {

	private AttachFileMapper attachFileMapper;

	@Override
	public boolean addAttachFile(AttachFile file) throws Exception {
		return attachFileMapper.addAttachFile(file)>0;
	}

	@Override
	public AttachFile getAttachFile(String id) throws Exception {
		return attachFileMapper.getAttachFile(id);
	}

	@Override
	public boolean deleteAttachFile(String id) throws Exception {
		return attachFileMapper.deleteAttachFile(id)>0;
	}

	/**
	 * 根据图片编码集获取主图片路径
	 * @param imagids
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 9, 2015
	 */
	private String getMainImagePath(String imagids,String maingoodsimg)throws Exception{
		String[] imgidArr = imagids.split(",");
		String path = "";
		for(String imgid : imgidArr){
			AttachFile attachFile2 = attachFileMapper.getAttachFile(imgid);
			if(null != attachFile2){
				if(maingoodsimg.equals(attachFile2.getFilename())){
					path = attachFile2.getFullpath();
					break;
				}
			}
		}
		if(StringUtils.isEmpty(path)){
			AttachFile attachFile2 = attachFileMapper.getAttachFile(imgidArr[0]);
			if(null != attachFile2){
				path = attachFile2.getFullpath();
			}
		}
		return path;
	}
	
	@Override
	public Map deleteGoodsAttachFile(String id, String goodsid)
			throws Exception {
        String filepath = OfficeUtils.getFilepath();
		Map map = new HashMap();
		boolean flag = attachFileMapper.deleteAttachFile(id) > 0;
		if(flag){
			//更新商品档案中的图片信息
			GoodsInfo goodsInfo = getBaseGoodsMapper().getGoodsInfo(goodsid);
			if(null != goodsInfo){
				if(StringUtils.isNotEmpty(goodsInfo.getImageids())){
                    String imgids = "";
                    imgids = goodsInfo.getImageids();
                    String newimgidsapp = imgids + ",";
                    newimgidsapp = newimgidsapp.replace(id+",","");
                    if(StringUtils.isNotEmpty(newimgidsapp)){
                        String[] imgidsArr = newimgidsapp.substring(0, newimgidsapp.lastIndexOf(",")).split(",");
                        for(String imgid : imgidsArr){
                            AttachFile attachFile = attachFileMapper.getAttachFile(imgid);
                            if(null != attachFile){
                                File file3 = new File(filepath + "/goods/big/" + attachFile.getFilename());
                                if(!file3.exists()){//存放路径不存在该文件，删除商品及数据库文件数据
                                    newimgidsapp = newimgidsapp.replace(imgid+",","");
                                    attachFileMapper.deleteAttachFileByid(imgid);
                                }
                            }else{
                                newimgidsapp = newimgidsapp.replace(imgid+",","");
                            }
                        }
                    }

					if(StringUtils.isNotEmpty(newimgidsapp)){
                        newimgidsapp = newimgidsapp.replace(newimgidsapp+",","");
                        if(StringUtils.isNotEmpty(newimgidsapp)){
                            imgids = newimgidsapp.substring(0, newimgidsapp.lastIndexOf(","));
                            goodsInfo.setImageids(imgids);
                            String maingoodsimg = goodsid + ".jpg";
                            String path = getMainImagePath(imgids,maingoodsimg);
                            if(StringUtils.isNotEmpty(path)){
                                goodsInfo.setImage(path);
                            }
                        }else{
                            goodsInfo.setImageids("");
                            goodsInfo.setImage("");
                        }
					}else{
						goodsInfo.setImageids("");
						goodsInfo.setImage("");
					}
					boolean flag2 = getBaseGoodsMapper().editGoodsImageInfo(goodsInfo) > 0;
					if(flag2){
                        if(StringUtils.isEmpty(goodsInfo.getImageids())){
                            String imgname = goodsid+".jpg";
                            boolean flag3 = isExistAttachFileByFilename(imgname);
                            if(flag3){
                                deleteAttachFileByFilename(imgname);
                            }
                        }
						map.put("newimgids", imgids);
					}
				}
			}
		}
		map.put("flag", flag);
		return map;
	}

	
	public AttachFileMapper getAttachFileMapper() {
		return attachFileMapper;
	}

	public void setAttachFileMapper(AttachFileMapper attachFileMapper) {
		this.attachFileMapper = attachFileMapper;
	}
	
	@Override
	public List getAttachFileList(Map map) throws Exception{
		return attachFileMapper.getAttachFileList(map);
	}
	
	@Override
	public boolean isExistAttachFileByFilename(String filename)
			throws Exception {
		return attachFileMapper.getAttachFileCountByFilename(filename) > 0;
	}

	@Override
	public boolean deleteAttachFileByFilename(String filename)
			throws Exception {
		return attachFileMapper.deleteAttachFileByFilename(filename) > 0;
	}

	@Override
	public void deleteAttachWithPhysical(Map map) throws Exception{
		SysUser user=null;
		if(map.containsKey("sysUser")){
			user=(SysUser)map.get("sysUser");
			map.remove("sysUser");
		}
		List authList=new ArrayList();
		if(map.containsKey("authList")){
			authList=(List)map.get("authList");
			map.remove("authList");
		}
		List<AttachFile> list=attachFileMapper.getAttachFileList(map);
		if(null!=list && list.size()>0){
			String filepath="";
			String tmppath="";
			for(AttachFile item : list){
				if(canOperate(item, user, authList)){
					if(StringUtils.isNotEmpty(item.getId()) && deleteAttachFile(item.getId())){
						deletePhysicalFile(item.getFullpath());
						deletePhysicalFile(item.getPdfpath());
						deletePhysicalFile(item.getHtmlpath());
						deletePhysicalFile(item.getSwfpath());
					}
				}
			}
		}
	}
	
	private boolean deletePhysicalFile(String filepath){
		boolean isok=false;
		if(StringUtils.isNotEmpty(filepath)){
			String fullpath="";
			String tmppath="";
			fullpath=OfficeUtils.getFilepath();
			tmppath=filepath;
			tmppath=tmppath.replace('\\', '/');
			if(tmppath.indexOf("upload/")==0){
				tmppath=tmppath.substring("upload/".length());
			}
			if(tmppath.indexOf('/')==0){
				fullpath=fullpath+tmppath;
			}else{
				fullpath=fullpath+"/"+tmppath;
			}
			File file=new File(fullpath);
			if(null!=file && file.exists() && file.isFile()){
				isok=org.apache.commons.io.FileUtils.deleteQuietly(file);
			}
		}
		return isok;
	}
	
	private boolean canOperate(String opStr, String userStr) throws Exception{
		if((null==opStr || "".equals(opStr)) || (null==userStr || "".equals(userStr)) ){
			return false;
		}
		String[] strArr = opStr.split("@");
		for(String str : strArr){
			if(str.equals(userStr)){
				return true;
			}
		}
		return false;
	}
	
	private boolean canOperate(String opStr, String[] roles) throws Exception{
		if((null==opStr || "".equals(opStr)) || (null==roles || roles.length==0) ){
			return false;
		}
		String[] strArr = opStr.split("@");
		for(String str : strArr){
			for(String role : roles){
				if(role.equals(str)){
					return true;
				}
			}
		}
		return false;
	}
	private boolean canOperate(String opStr, List<String> roleList) throws Exception{
		if((null==opStr || "".equals(opStr)) || (null==roleList || roleList.size()==0) ){
			return false;
		}
		String[] strArr = opStr.split("@");
		for(String str : strArr){
			for(String role : roleList){
				if(role.equals(str)){
					return true;
				}
			}
		}
		return false;
	}
	@Override
	public boolean canOperate(AttachFile file,SysUser user,List<String> authList) throws Exception{
		if(null==file){
			return false;
		}
		if(StringUtils.isEmpty(file.getOpdept()) &&
		   StringUtils.isEmpty(file.getOprule()) && 
		   StringUtils.isEmpty(file.getOpuser())){
			return true;
		}
		if((StringUtils.isNotEmpty(file.getOpdept()) && "ALL".equals(file.getOpdept().toUpperCase())) ||
		   (StringUtils.isNotEmpty(file.getOprule()) && "ALL".equals(file.getOprule().toUpperCase())) || 
		   (StringUtils.isNotEmpty(file.getOpuser()) && "ALL".equals(file.getOpuser().toUpperCase()))){
			return true;
		}
		if(null!=user && StringUtils.isNotEmpty(user.getDepartmentid()) && StringUtils.isNotEmpty(user.getUserid())){
			if(canOperate(file.getOpdept(), user.getDepartmentid())){
				return true;
			}
			if(canOperate(file.getOpuser(), user.getUserid())){
				return true;
			}
		}
		if(null!=authList && authList.size()>0){
			if(canOperate(file.getOprule(), authList)){
				return true;
			}
		}
		return false;
	}
	@Override
	public boolean updateAttachFileAuth(AttachFile file) throws Exception{
		return attachFileMapper.updateAttachFileAuth(file)>0;
	}
	@Override
	public boolean updateAttachFileAuth(String idarrs,String opuser,String opdept,String oprule) throws Exception{
		idarrs=null!=idarrs ? idarrs.trim() : "";
		opuser=null!=opuser ? opuser.trim() : "";
		opdept=null!=opdept ? opdept.trim() : "";
		oprule=null!=oprule ? oprule.trim() : "";
		if("".equals(idarrs) && "".equals(opuser)  && "".equals(opdept) && "".equals(oprule) ){
			return false;
		}
		boolean isok=false;
		String[] idarr=idarrs.trim().split(",");
		if(idarr.length>0){
			AttachFile file=null;
			for(String item : idarr){
				if(null!=item && !"".equals(item.trim())){
					file=new AttachFile();
					file.setId(item.trim());
					file.setOpuser(opuser);
					file.setOpdept(opdept);
					file.setOprule(oprule);
					isok=updateAttachFileAuth(file) || isok;
				}
			}
		}
		return isok;
	}
	@Override
	public boolean updateAttachFileConvert(AttachFile file) throws Exception{
		return attachFileMapper.updateAttachFileConvert(file)>0;
	}
	/**
	 * JOB文件转换
	 * @param attach
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-11-12
	 */
	public boolean updateAttachConvertByJob(AttachFile attach) throws Exception{
		if(null==attach || StringUtils.isEmpty(attach.getId()) || !"1".equals(attach.getIsconvert()) || StringUtils.isEmpty(attach.getFullpath())){
			return false;
		}

		String filepath = OfficeUtils.getFilepath();
		String subPath = CommonUtils.getYearMonthDayDirPath();	//年月路径 格式yyyy/MM/dd
		String filename="";
		if(StringUtils.isNotEmpty(attach.getFilename())){
			String[] tmp=attach.getFilename().split("\\."); //split使用正则的，注意分割 字符串（分隔符如：* ^ ： | , .）
			if(null!=tmp && tmp.length>1 && !"".equals(tmp[0])){
				filename=tmp[0];
			}else{
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				Random rand = new Random();
				String randString = "" + rand.nextInt(100000);
				filename = dateFormat.format(new Date()) + randString;
			}
		}
		if(null==filename || "".equals(filename.trim())){
			return false;
		}
		String pdfpath="/pdf/"+ subPath + "/" + filename+".pdf";
		String subdirfile="";
		if(attach.getFullpath().indexOf("upload/")==0){
			subdirfile=attach.getFullpath().substring("upload/".length());
		}else{
			subdirfile=attach.getFullpath();
		}
		if(null==subdirfile || "".equals(subdirfile.trim())){
			return false;
		}
		if(".doc".equals(attach.getExt()) || ".docx".equals(attach.getExt()) 
				|| ".xls".equals(attach.getExt()) || ".xlsx".equals(attach.getExt()) 
				|| ".ppt".equals(attach.getExt()) || ".pptx".equals(attach.getExt())
				|| ".txt".equals(attach.getExt())){
			//转换成PDF文件
			int convertResult=OfficeUtils.office2Other(filepath+"/"+subdirfile, filepath+pdfpath);
			if(convertResult==1){
				attach.setPdfpath("upload"+pdfpath);
			}else if(convertResult == 0){
				throw new Exception("文件转换失败");
			}
		}
		boolean isok=false;
		if(StringUtils.isNotEmpty(attach.getPdfpath())){
			if(null==attach.getIconverts()){
				attach.setIcoverts(0);
			}else{
				attach.setIcoverts(attach.getIconverts()+1);
			}
			attach.setIscovert("0");
			attach.setConvertdate(new Date());
			isok=updateAttachFileConvert(attach);
		}
		return isok;
	}

	@Override
	public List<AttachFile> getAttachFilesListByPersonid(String personid)
			throws Exception {
		List<AttachFile> list = new ArrayList<AttachFile>();
		Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(personid);
		if(null != personnel && StringUtils.isNotEmpty(personnel.getAdjunctid())){
			String[] idsArr = personnel.getAdjunctid().split(",");
			list = attachFileMapper.getAttachFilesListByPersonid(idsArr);
		}
		return list;
	}

    @Override
    public void updateGoodsimgs(GoodsInfo goodsInfo, String path) throws Exception {
        String imgids = "";
        String imgpath = "";
        if(null != goodsInfo){
            String mainimg = goodsInfo.getId() + ".jpg";
            Map map = new HashMap();
            map.put("filename",goodsInfo.getId());
            List<AttachFile> list = attachFileMapper.getAttachFileList(map);
            if(list.size() != 0){
                for(AttachFile attachFile : list){
                    File file1 = new File(path, attachFile.getFilename());
                    if(file1.exists()){
                        if(mainimg.equals(attachFile.getFilename())){
                            imgpath = attachFile.getFullpath();
                        }
                        if(StringUtils.isEmpty(imgids)){
                            imgids = attachFile.getId();
                        }else{
                            imgids += "," + attachFile.getId();
                        }
                    }else{
                        deleteAttachFileDir(attachFile.getFilename());

                        attachFileMapper.deleteAttachFile(attachFile.getId());
                    }
                }
            }

            goodsInfo.setImageids(imgids);
            goodsInfo.setImage(imgpath);
            getBaseGoodsMapper().editGoodsImageInfo(goodsInfo);
        }
    }

    @Override
    public void deleteAttachFileDir(String filename) throws Exception {
        String filepath = OfficeUtils.getFilepath();
        //文件存放路径
        String path1 = filepath + "/goods/big/";
        File file1 = new File(path1+filename);
        if(file1.exists()){
            file1.delete();
        }
        String path2 = filepath + "/goods/middle/";
        File file2 = new File(path2+filename);
        if(file2.exists()){
            file2.delete();
        }
        String path3 = filepath + "/goods/phone/";
        File file3 = new File(path3+filename);
        if(file3.exists()){
            file3.delete();
        }
    }

		@Override
    public String createExcelAndAttachFile(List<Map<String, Object>> dataList,List<String> dataColumnList,String templetFilePath,String downFileName) throws  Exception{
		ExcelFileUtils handle = new ExcelFileUtils();
        handle.writeListData(templetFilePath, dataColumnList, dataList, 0);

		String userid="";
		SysUser sysUser=getSysUser();
		if(null!=sysUser){
			userid=sysUser.getUserid();
		}
        String phyFilepath = OfficeUtils.getFilepath();//获取临时文件总目录
        String subPath = CommonUtils.getYearMonthDayDirPath();	//年月路径 格式yyyy/MM/dd
		phyFilepath=phyFilepath.replaceAll("\\\\","/");


        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss") ;
        Random rand = new Random();
        String randString = "" + rand.nextInt(100000);
        String fileName = dateFormat.format(new Date()) + randString;
        fileName= userid+fileName+ ".xls";


		String theFileSubPath="/errorimportfile/" + subPath + "/" + fileName;//相对文件目录
		//物理地址
		phyFilepath = phyFilepath+theFileSubPath;
		//虚拟相对地址
		String fullVirtualPath = "upload"+theFileSubPath;


		File errorFile = new File(phyFilepath);

		FileUtils.createFile(errorFile);

        OutputStream os = new FileOutputStream(errorFile);

        //写到输出流并关闭资源
        handle.writeAndClose(templetFilePath, os);
        os.flush();
        os.close();
        handle.readClose(templetFilePath);

        AttachFile attachFile = new AttachFile();
        attachFile.setExt(".xls");
        attachFile.setFilename(fileName);
        attachFile.setFullpath(fullVirtualPath);
        if(null==downFileName || "".equals(downFileName.trim())){
        	downFileName=fileName;
		}else{
        	if(!downFileName.endsWith(".xls")){
				downFileName=downFileName+".xls";
			}
		}
		downFileName=downFileName.trim();
        attachFile.setOldfilename(downFileName);
        //将临时文件信息插入数据库
        addAttachFile(attachFile);

        String id = "";

        if(null!=attachFile){
            id=attachFile.getId();
        }
        return id;
    }

	@Override
	public String createFileAndAttachFile(byte[] content,String fileext,String userSubPath,String downFileName) throws  Exception{
		if(fileext==null ){
			fileext="";
		}
		fileext=fileext.trim();
		if(!"".equals(fileext) && !fileext.startsWith(".")){
			fileext="."+fileext;
		}
		if(null==userSubPath){
			userSubPath="";
		}
		userSubPath=userSubPath.trim();
		if(!"".equals(userSubPath)) {
			if (!userSubPath.startsWith("/")) {
				userSubPath = "/" + userSubPath;
			}
			if (!userSubPath.endsWith("/")) {
				userSubPath = userSubPath + "/";
			}
		}

		String userid="";
		SysUser sysUser=getSysUser();
		if(null!=sysUser){
			userid=sysUser.getUserid();
		}
		String phyFilepath = OfficeUtils.getFilepath();//获取临时文件总目录
		String subPath = CommonUtils.getYearMonthDayDirPath();	//年月路径 格式yyyy/MM/dd
		phyFilepath=phyFilepath.replaceAll("\\\\","/");

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss") ;
		Random rand = new Random();
		String randString = "" + rand.nextInt(100000);
		String fileName = dateFormat.format(new Date()) + randString;
		fileName= userid+fileName+ fileext;

		String theFileSubPath= MessageFormat.format("/outfile/{0}{1}/{2}",userSubPath,subPath,fileName) ;//相对文件目录
		//物理地址
		phyFilepath = phyFilepath+theFileSubPath;
		//虚拟相对地址
		String fullVirtualPath = "upload"+theFileSubPath;

		FileUtils.writeByteToFile(content,phyFilepath);

		AttachFile attachFile = new AttachFile();
		attachFile.setExt(fileext);
		attachFile.setFilename(fileName);
		attachFile.setFullpath(fullVirtualPath);
		if(null==downFileName || "".equals(downFileName.trim())){
			downFileName=fileName;
		}
		downFileName=downFileName.trim();
		attachFile.setOldfilename(downFileName+fileext);
		//将临时文件信息插入数据库
		addAttachFile(attachFile);

		String id = "";

		if(null!=attachFile){
			id=attachFile.getId();
		}
		return id;
	}
}

