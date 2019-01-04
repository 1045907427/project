package com.hd.agent.agprint.dao;

import com.hd.agent.agprint.model.PrintJobCallHandle;
import com.hd.agent.agprint.model.query.PrintJobCallHandleQuery;
import com.hd.agent.common.util.PageMap;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PrintJobCallHandleMapper {
	/**
	 * 获取打印的回调列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui
	 * @date Sep 11, 2016
	 */
	public List<PrintJobCallHandle> getPrintJobCallHandlePageList(PageMap pageMap);
	/**
	 * 获取打印的页内容列表合计条数
	 * @param pageMap
	 * @return
	 * @author zhanghonghui
	 * @date Sep 11, 2016
	 */
	public int getPrintJobCallHandlePageCount(PageMap pageMap);
	/**
	 * 添加任务回调
	 * @param printJobCallHandle
	 * @return
	 * @author zhanghonghui 
	 * @date 2016年5月27日
	 */
	public int addPrintJobCallHandle(PrintJobCallHandle printJobCallHandle);
	/**
	 * 更新打印任务回调状态
	 * @param printJobCallHandle
	 * @return
	 * @author zhanghonghui 
	 * @date 2016年5月27日
	 */
	public int updatePrintJobCallHandleStatus(PrintJobCallHandle printJobCallHandle);
	/**
	 * 查询打印任务回调列表<br/>
	 * 可查询参数：<br/>
	 * jobid : 打印任务编号<br/>
	 * status: 打印回调状态：1处理，0未处理<br/>
	 * adduserid : 添加人编号<br/>
	 * @param printJobBackHandleQuery
	 * @return
	 * @author zhanghonghui 
	 * @date 2016年5月27日
	 */
	public List<PrintJobCallHandle> getPrintJobCallHandleListBy(PrintJobCallHandleQuery printJobBackHandleQuery);

	/**
	 * 批量删除date之前的打印任务回调数据
	 * @param date
	 * @return
	 */
	public int deletePrintJobCallHandleBeforeDate(@Param("date") Date date);
}
