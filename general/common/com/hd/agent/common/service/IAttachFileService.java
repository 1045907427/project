/**
 * @(#)IAttachFileService.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-3-18 zhengziyong 创建版本
 */
package com.hd.agent.common.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.common.model.AttachFile;

/**
 * 
 * 
 * @author zhengziyong
 */
public interface IAttachFileService {
	
	/**
	 * 添加附件信息
	 * @param file
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 25, 2013
	 */
	public boolean addAttachFile(AttachFile file) throws Exception;
	
	/**
	 * 获取附件详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 25, 2013
	 */
	public AttachFile getAttachFile(String id) throws Exception;
	
	/**
	 * 删除附件信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 25, 2013
	 */
	public boolean deleteAttachFile(String id) throws Exception;
	
	/**
	 * 删除商品档案图片图片信息
	 * @param id
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 9, 2015
	 */
	public Map deleteGoodsAttachFile(String id,String goodsid)throws Exception;
	
	/**
	 * 获取附件列表<br/>
	 * map中参数:<br/>
	 * id : 编号 <br/>
	 * idarrs : 编号字符串组，例如：1,2,3<br/>
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 25, 2013
	 */
	public List getAttachFileList(Map map) throws Exception;
	
	/**
	 * 根据文件名称判断文件是否存在
	 * @param filename
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 9, 2015
	 */
	public boolean isExistAttachFileByFilename(String filename)throws Exception;
	/**
	 * 根据文件名称删除文件
	 * @param filename
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 9, 2015
	 */
	public boolean deleteAttachFileByFilename(String filename)throws Exception;
	/**
	 * 删除附件并删除物理存储文件<br/>
	 * map中参数：<br/>
	 * idarrs：附件编号字符串，格式：1,2,3<br/>
	 * sysUser: 用户对象 <br/>
	 * authList : 权限列表<br/>
	 * @param map
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-10-25
	 */
	public void deleteAttachWithPhysical(Map map) throws Exception;
	/**
	 * 更新附件下载权限
	 * @param file
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-10-25
	 */
	public boolean updateAttachFileAuth(AttachFile file) throws Exception;
	/**
	 * 更新附件下载权限
	 * @param idarrs 附件编号字符串，格式：1,2,3
	 * @param opuser 用户权限
	 * @param opdept 部门权限
	 * @param oprule 角色权限
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-10-25
	 */
	public boolean updateAttachFileAuth(String idarrs,String opuser,String opdept,String oprule) throws Exception;
	
	/**
	 * 判断该文件该用户是否可操作（列表中查看到或下载）
	 * @param file 附件
	 * @param user 用户 
	 * @param authList 权限
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date 2013-3-19
	 */
	public boolean canOperate(AttachFile file,SysUser user,List<String> authList) throws Exception;
	/**
	 * 更新文件转换
	 * @param file
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-11-13
	 */
	public boolean updateAttachFileConvert(AttachFile file) throws Exception;
	/**
	 * JOB文件转换
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-11-12
	 */
	public boolean updateAttachConvertByJob(AttachFile attach) throws Exception;
	
	/**
	 * 根据人员编码获取对应人员的附件数据
	 * @param personid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2015年6月5日
	 */
	public List<AttachFile> getAttachFilesListByPersonid(String personid)throws Exception;

    /**
     * 更新商品档案图片信息
     * @param goodsInfo
     * @param path
     * @throws Exception
     */
    public void updateGoodsimgs(GoodsInfo goodsInfo, String path)throws Exception;

    /**
     * 删除存储路径中的文件
     * @param filename
     * @throws Exception
     */
    public void deleteAttachFileDir(String filename)throws Exception;

	/**
	 * 根据数据列表、数据字段，模板文件名、下载文件名生成Excel文件，并记录到附件数据库
	 * @param dataList
	 * @param dataColumnList
	 * @param templetFilePath
	 * @param downFileName
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2016年2月19日
	 */
	public String createExcelAndAttachFile(List<Map<String, Object>> dataList,List<String> dataColumnList,String templetFilePath,String downFileName) throws  Exception;

	/**
	 * 把字节内容生成指定后缀文件，并记录到附件数据库,生成文件路径/outfile/userSubPath(可以为空)/yyyy/MM/dd/yyyyMMddHHmmss.fileext
	 * @param content
	 * @param fileext
	 * @param userSubPath 用户自定义路径，可以为空
	 * @param downFileName 下载文件名称
	 * @return java.lang.String
	 * @throws
	 * @author zhanghonghui
	 * @date Apr 23, 2017
	 */
	public String createFileAndAttachFile(byte[] content,String fileext,String userSubPath,String downFileName) throws  Exception;
}

