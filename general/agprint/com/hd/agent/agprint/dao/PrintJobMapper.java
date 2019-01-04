package com.hd.agent.agprint.dao;

import java.util.Date;
import java.util.List;

import com.hd.agent.agprint.model.PrintJob;
import com.hd.agent.common.util.PageMap;
import org.apache.ibatis.annotations.Param;

public interface PrintJobMapper {

	/**
	 * 获取打印的任务列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui
	 * @date Sep 11, 2016
	 */
	public List<PrintJob> getPrintJobPageList(PageMap pageMap);
	/**
	 * 获取打印的任务列表合计条数
	 * @param pageMap
	 * @return
	 * @author zhanghonghui
	 * @date Sep 11, 2016
	 */
	public int getPrintJobPageCount(PageMap pageMap);
	/**
	 * 添加打印任务
	 * @param printJob
	 * @return
	 * @author zhanghonghui 
	 * @date 2016年5月27日
	 */
	public int addPrintJob(PrintJob printJob);
	/***
	 * 更新打印任务状态
	 * @param printJob
	 * @return
	 * @author zhanghonghui 
	 * @date 2016年5月27日
	 */
	public int updatePrintJobStatus(PrintJob printJob);
	/**
	 * 获取打印任务
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2016年5月31日
	 */
	public PrintJob getPrintJob(@Param("id")String id);
	/**
	 * 根据打印任务编号及用户编号获取打印任务
	 * @param id
	 * @param userid
	 * @return
	 * @author zhanghonghui 
	 * @date 2016年5月31日
	 */
	public PrintJob getPrintJobWithUserid(@Param("id")String id,@Param("userid")String userid);

	/**
	 * 批量删除date之前的打印任务数据
	 * @param date
	 * @return
	 */
	public int deletePrintJobBeforeDate(@Param("date")Date date);
}
