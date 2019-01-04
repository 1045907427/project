/**
 * @(#)FilesLevelService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-18 chenwei 创建版本
 */
package com.hd.agent.basefiles.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.model.FilesLevel;

/**
 * 
 * 档案级次定义service
 * @author chenwei
 */
public interface IFilesLevelService {
	/**
	 * 获取表的档案级次定义列表
	 * @param tablename
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-18
	 */
	public List showFilesLevelList(String tablename) throws Exception;
	/**
	 * 保存基础档案级次定义
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-19
	 */
	public boolean saveFilesLevel(List<FilesLevel> list,String tablename) throws Exception;
	/**
	 * 获取基础档案级次定义被使用了多少长度
	 * @param tablename 表名
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-21
	 */
	public int getTableFileLevelLength(String tablename) throws Exception;
	/**
	 * 根据表名和级别获取该级别档案级次信息
	 * @param tablename
	 * @param level
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-21
	 */
	public int getFilesLevel(String tablename,int level) throws Exception;

    /**
     * 从级次档案信息中根据编码获取对应的本级编码，上级编码
     * @param tn
     * @param id
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-19
     */
    public Map getObjectThisidByidCaseFilesLevel(String tn,String id)throws Exception;
	
}

