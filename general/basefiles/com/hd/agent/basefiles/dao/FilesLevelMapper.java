/**
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-18 chenwei 创建版本
 */
package com.hd.agent.basefiles.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.basefiles.model.FilesLevel;

/**
 * 基础档案级次定义dao
 * 
 * @author chenwei
 */
public interface FilesLevelMapper {
	/**
	 * 获取表的档案级次定义列表
	 * 
	 * @param tablename
	 * @return
	 * @author chenwei
	 * @date 2013-1-18
	 */
	public List showFilesLevelList(String tablename);

	/**
	 * 删除档案级次定义
	 * 
	 * @param tablename
	 * @return
	 * @author chenwei
	 * @date 2013-1-19
	 */
	public int deleteFilesLevel(String tablename);

	/**
	 * 添加档案级次定义
	 * 
	 * @param filesLevel
	 * @return
	 * @author chenwei
	 * @date 2013-1-19
	 */
	public int addFilesLevel(FilesLevel filesLevel);

	/**
	 * 取表中的某个字段的最大长度
	 * 
	 * @param tablename
	 * @param column
	 * @return
	 * @author chenwei
	 * @date 2013-1-21
	 */
	public int getTableFieldMaxLength(@Param("tablename") String tablename,
			@Param("column") String column);

	/**
	 * 根据表名和级别取该级别档案级次信息
	 * 
	 * @param tablename
	 * @param level
	 * @return
	 * @author chenwei
	 * @date 2013-1-21
	 */
	public FilesLevel getFilesLevel(@Param("tablename") String tablename,
			@Param("level") String level);

}