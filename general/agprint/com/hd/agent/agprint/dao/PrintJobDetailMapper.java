package com.hd.agent.agprint.dao;

import com.hd.agent.agprint.model.PrintJobDetail;
import com.hd.agent.common.util.PageMap;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PrintJobDetailMapper {

	/**
	 * 获取打印的页内容列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui
	 * @date Sep 11, 2016
	 */
	public List<PrintJobDetail> getPrintJobDetailPageList(PageMap pageMap);
	/**
	 * 获取打印的页内容列表合计条数
	 * @param pageMap
	 * @return
	 * @author zhanghonghui
	 * @date Sep 11, 2016
	 */
	public int getPrintJobDetailPageCount(PageMap pageMap);
	/**
	 * 添加打印的页内容
	 * @date 2016-06-29
	 * @author zhanghonghui
	 * @param printJobDetail
	 * @return
	 */
	public int insertPrintJobDetail(PrintJobDetail printJobDetail);

	/**
	 * 获取打印的页内容
	 * @date 2016-06-29
	 * @author zhanghonghui
	 * @param id
	 * @return
	 */
	public PrintJobDetail getPrintJobDetail(@Param("id") String id);

	/**
	 * 设置打印页面容已读
	 * @param id
	 * @return
	 */
	public int updatePrintJobDetailReadflag(@Param("id") String id);

	/**
	 * 删除打印页内容
	 * @param id
	 * @author zhanghonghui
	 * @date 2016年5月27日
	 * @return
	 */
	public int deletePrintJobDetail(@Param("id") String id);
	/**
	 * 批量删除date之前的打印任务内容数据
	 * @param date
	 * @return
	 */
	public int deletePrintJobDetailBeforeDate(@Param("date") Date date);
}
