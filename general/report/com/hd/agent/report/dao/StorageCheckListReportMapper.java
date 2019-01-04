package com.hd.agent.report.dao;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.report.model.StorageCheckListReport;

/**
 * 盘点报表
 * @author chenwei
 */
public interface StorageCheckListReportMapper {
	/**
	 * 添加盘点报表
	 * @param storageCheckListReport
	 * @return
	 * @author chenwei 
	 * @date Aug 9, 2013
	 */
	public int addStorageCheckListReport(StorageCheckListReport storageCheckListReport);
	/**
	 * 根据盘点单编号 删除盘点报表数据
	 * @param checklistid
	 * @return
	 * @author chenwei 
	 * @date Aug 9, 2013
	 */
	public int deleteStorageCheckListReportByChecklistid(@Param("checklistid")String checklistid);
	/**
	 * 根据盘点单编号获取盘点报表数据
	 * 第一次盘点编号
	 * @param billid
	 * @return
	 * @author chenwei 
	 * @date Aug 9, 2013
	 */
	public StorageCheckListReport getStorageCheckListReportByBillid(@Param("billid")String billid,@Param("checkuserid")String checkuserid);
	/**
	 * 根据盘点单编号获取盘点单报表数据
	 * @param checklistid
	 * @return
	 * @author chenwei 
	 * @date Feb 7, 2014
	 */
	public StorageCheckListReport getStorageCheckListReportByChecklistid(@Param("billid")String billid,@Param("checkno")int checkno);
	/**
	 * 更新盘点单报表数据
	 * @param storageCheckListReport
	 * @return
	 * @author chenwei 
	 * @date Feb 7, 2014
	 */
	public int updateStorageCheckListReport(StorageCheckListReport storageCheckListReport);
}