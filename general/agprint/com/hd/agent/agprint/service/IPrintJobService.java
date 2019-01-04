package com.hd.agent.agprint.service;

import com.hd.agent.agprint.model.PrintJob;
import com.hd.agent.agprint.model.PrintJobCallHandle;
import com.hd.agent.agprint.model.PrintJobDetail;
import com.hd.agent.agprint.model.PrintJobDetailImage;
import com.hd.agent.agprint.model.query.PrintJobCallHandleQuery;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

import java.util.List;

public interface IPrintJobService {


	//===============================================================================//
	//================================== 打印任务   ====================================//
	//===============================================================================//
	/**
	 * 打印任务列表数据
	 * @param pageMap
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author zhanghonghui
	 * @date Oct 08, 2016
	 */
	public PageData showPrintJobPageListData(PageMap pageMap) throws Exception;
	/**
	 * 添加打印任务
	 * @param printJob
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2016年5月27日
	 */
	public boolean addPrintJob(PrintJob printJob) throws Exception;
	/**
	 * 更新打印任务状态，status 状态：1打印，0为打印申请
	 * @param printJob
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2016年5月27日
	 */
	public boolean updatePrintJobStatus(PrintJob printJob) throws Exception;
	/**
	 * 根据编号获取打印任务
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2016年5月31日
	 */
	public PrintJob getPrintJob(String id) throws Exception;
	/**
	 * 根据编号及添加用户编号获取打印任务信息
	 * @param id
	 * @param userid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2016年5月31日
	 */
	public PrintJob getPrintJobWithUserid(String id,String userid) throws Exception;
	

	//===============================================================================//
	//================================== 打印任务回调  =================================//
	//===============================================================================//
	/**
	 * 打印任务回调列表数据
	 * @param pageMap
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author zhanghonghui
	 * @date Oct 08, 2016
	 */
	public PageData showPrintJobCallHandlePageListData(PageMap pageMap) throws Exception;
	/**
	 * 添加打印任务回调
	 * @param printJobCallHandle
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2016年5月27日
	 */
	public boolean addPrintJobCallHandle(PrintJobCallHandle printJobCallHandle) throws Exception;
	/**
	 * 更新打印回调任务状态，status 状态：1打印，0为打印申请
	 * @param printJobCallHandle
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2016年5月27日
	 */
	public boolean updatePrintJobCallHandleStatus(PrintJobCallHandle printJobCallHandle) throws Exception;
	
	/**
	 * 获取打印回调任务列表<br/>
	 * printJobBackHandleQuery中参数：<br/>
	 * status : 状态，1处理，0未处理 <br/>
	 * jobid ：打印任务编号 <br/>
	 * adduserid: 打印任务添加人<br/>
	 * @param printJobBackHandleQuery
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2016年5月27日
	 */
	public List<PrintJobCallHandle> getPrintJobCallHandleListBy(PrintJobCallHandleQuery printJobBackHandleQuery) throws Exception;


	//===============================================================================//
	//================================== 打印任务单据页  =================================//
	//===============================================================================//
	/**
	 * 打印任务单据内容页列表数据
	 * @param pageMap
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author zhanghonghui
	 * @date Oct 08, 2016
	 */
	public PageData showPrintJobDetailPageListData(PageMap pageMap) throws Exception;
	/**
	 * 添加打印任务单据页
	 * @param printJobDetail
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2016年06月29日
	 */
	public boolean addPrintJobDetail(PrintJobDetail printJobDetail) throws Exception;
	/**
	 * 获取打印任务单据页
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2016年06月29日
	 */
	public PrintJobDetail getPrintJobDetail(String id) throws Exception;

	/**
	 * 更新打印读取标志
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean updatePrintJobDetailReadflag( String id) throws Exception;

	/**
	 * 删除打印页内容
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2016年5月27日
	 */
	public boolean deletePrintJobDetail(String id) throws Exception;

	//===============================================================================//
	//================================== 打印任务单据页图片  =================================//
	//===============================================================================//


	/**
	 * 添加打印任务单据页图片
	 * @param printJobDetailImage
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2016年06月29日
	 */
	public boolean addPrintJobDetailImage(PrintJobDetailImage printJobDetailImage) throws Exception;
	/**
	 * 获取打印任务单据页
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2016年06月29日
	 */
	public PrintJobDetailImage getPrintJobDetailImage(String id) throws Exception;

	/**
	 * 删除打印页内容
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2016年5月27日
	 */
	public boolean deletePrintJobDetailImage(String id) throws Exception;
	/**
	 * 获取打印内容图片列表
	 * @return
	 * @throws
	 * @author zhanghonghui
	 * @date Oct 22, 2017
	 */
	public String doCreatePrintJobDetailImageFileId() throws Exception;



	//===============================================================================//
	//================================== 其他  ======================================//
	//===============================================================================//

	/**
	 * 定时器清理打印数据
	 * @return
	 * @throws Exception
	 */
	public void clearPrintDataBeforeDateJob() throws Exception;
	
}
