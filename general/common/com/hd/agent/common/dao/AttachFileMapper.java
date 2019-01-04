package com.hd.agent.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.model.AttachFile;

public interface AttachFileMapper {
    
	public int addAttachFile(AttachFile file);
	
	public AttachFile getAttachFile(String id);
	
	public int deleteAttachFile(String id);

    public int deleteAttachFileByid(String id);
	
	/**
	 * 获取附件列表<br/>
	 * map中参数:<br/>
	 * id : 编号 <br/>
	 * idarrs : 编号字符串组，例如：1,2,3<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-10-24
	 */
	public List getAttachFileList(Map map);
	
	/**
	 * 根据文件名称获取文件数量
	 * @param filename
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 9, 2015
	 */
	public int getAttachFileCountByFilename(@Param("filename")String filename);
	/**
	 * 根据文件名称删除文件
	 * @param filename
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 9, 2015
	 */
	public int deleteAttachFileByFilename(@Param("filename")String filename);
	
	/**
	 * 更新附件下载权限
	 * @param file
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-10-25
	 */
	public int updateAttachFileAuth(AttachFile file);
	/**
	 * 更新文件转换
	 * @param file
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-11-13
	 */
	public int updateAttachFileConvert(AttachFile file);
	
	/**
	 * 根据人员编码获取对应人员的附件数据
	 * @param personid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2015年6月5日
	 */
	public List<AttachFile> getAttachFilesListByPersonid(@Param("idsArr")String[] idsArr);
}