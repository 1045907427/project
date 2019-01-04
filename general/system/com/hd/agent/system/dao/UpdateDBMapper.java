/**
 * @(#)UpdateDBMapper.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年8月20日 chenwei 创建版本
 */
package com.hd.agent.system.dao;

import com.hd.agent.common.util.PageMap;
import org.apache.ibatis.annotations.Param;

import com.hd.agent.system.model.UpdateDB;

import java.util.List;

/**
 * 
 * 更新数据库日志
 * @author chenwei
 */
public interface UpdateDBMapper {
	/**
	 * 添加更新数据库日志记录
	 * @param updateDB
	 * @return
	 * @author chenwei 
	 * @date 2015年8月20日
	 */
	public int addUpdateDBLog(UpdateDB updateDB);
	/**
	 * 根据更新文件名获取更新日志记录
	 * @param name
	 * @return
	 * @author chenwei 
	 * @date 2015年8月20日
	 */
	public UpdateDB getUpdateDBByName(@Param("name")String name);
	/**
	 * 判断是否建了t_sys_update_db 表
	 * @return
	 * @author chenwei 
	 * @date 2015年8月20日
	 */
	public int isHasUpdateDB();
	/**
	 * 创建t_sys_update_db表
	 * @return
	 * @author chenwei 
	 * @date 2015年8月20日
	 */
	public int createUpdateDB();

	/**
	 * SQL补丁更新列表
	 * @param pageMap
	 * @return java.util.List
	 * @throws
	 * @author zhanghonghui
	 * @date Mar 20, 2017
	 */
	List<UpdateDB> getUpdateDBPageList(PageMap pageMap);

	/**
	 * SQL补丁更新列表分页条数
	 * @param pageMap
	 * @return java.util.List
	 * @throws
	 * @author zhanghonghui
	 * @date Mar 20, 2017
	 */
	int getUpdateDBCount(PageMap pageMap);
}

