package com.hd.agent.agprint.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.agprint.dao.PrintJobCallHandleMapper;
import com.hd.agent.agprint.dao.PrintJobDetailImageMapper;
import com.hd.agent.agprint.dao.PrintJobMapper;
import com.hd.agent.agprint.dao.PrintJobDetailMapper;
import com.hd.agent.agprint.model.PrintJob;
import com.hd.agent.agprint.model.PrintJobCallHandle;
import com.hd.agent.agprint.model.PrintJobDetail;
import com.hd.agent.agprint.model.PrintJobDetailImage;
import com.hd.agent.agprint.model.query.PrintJobCallHandleQuery;
import com.hd.agent.agprint.service.IPrintJobService;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.common.util.UploadConfigUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.*;

public class PrintJobServiceImpl  extends BaseServiceImpl implements IPrintJobService {

	/**
	 * 打印任务
	 */
	private PrintJobMapper printJobMapper;

	public PrintJobMapper getPrintJobMapper() {
		return printJobMapper;
	}

	public void setPrintJobMapper(PrintJobMapper printJobMapper) {
		this.printJobMapper = printJobMapper;
	}

	private PrintJobCallHandleMapper printJobCallHandleMapper;

	public PrintJobCallHandleMapper getPrintJobCallHandleMapper() {
		return printJobCallHandleMapper;
	}

	public void setPrintJobCallHandleMapper(PrintJobCallHandleMapper printJobCallHandleMapper) {
		this.printJobCallHandleMapper = printJobCallHandleMapper;
	}
	private PrintJobDetailMapper printJobDetailMapper;

	public PrintJobDetailMapper getPrintJobDetailMapper() {
		return printJobDetailMapper;
	}

	public void setPrintJobDetailMapper(PrintJobDetailMapper printJobDetailMapper) {
		this.printJobDetailMapper = printJobDetailMapper;
	}


	private PrintJobDetailImageMapper printJobDetailImageMapper;

	public PrintJobDetailImageMapper getPrintJobDetailImageMapper() {
		return printJobDetailImageMapper;
	}

	public void setPrintJobDetailImageMapper(PrintJobDetailImageMapper printJobDetailImageMapper) {
		this.printJobDetailImageMapper = printJobDetailImageMapper;
	}
	//===============================================================================//
	//================================== 打印任务   ====================================//
	//===============================================================================//

	@Override
	public PageData showPrintJobPageListData(PageMap pageMap) throws Exception {
		Map condition=pageMap.getCondition();
		String sort=(String) condition.get("sort");
		String order=(String) condition.get("order");
		if((null==sort || "".equals(sort.trim())) || (null==order || "".equals(order.trim()))){
			condition.remove("sort");
			condition.remove("order");
			pageMap.setOrderSql("id desc");
		}
		List<PrintJob> list= getPrintJobMapper().getPrintJobPageList(pageMap);
		int iCount= getPrintJobMapper().getPrintJobPageCount(pageMap);
		PageData pageData=new PageData(iCount, list, pageMap);
		return pageData;
	}

	/**
	 * 添加打印任务
	 * @param printJob
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2016年5月27日
	 */
	public boolean addPrintJob(PrintJob printJob) throws Exception{
		if(StringUtils.isEmpty(printJob.getAdduserid())){
			SysUser sysUser=getSysUser();
			printJob.setAdduserid(sysUser.getUserid());
			printJob.setAddusername(sysUser.getName());
			printJob.setAddtime(new Date());
		}
		return printJobMapper.addPrintJob(printJob)>0;
	}
	/**
	 * 更新打印任务状态，status 状态：1打印，0为打印申请
	 * @param printJob
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2016年5月27日
	 */
	public boolean updatePrintJobStatus(PrintJob printJob) throws Exception{
		SysUser sysUser=getSysUser();
		printJob.setModifyuserid(sysUser.getUserid());
		printJob.setModifyusername(sysUser.getName());
		printJob.setModifytime(new Date());
		return printJobMapper.updatePrintJobStatus(printJob)>0;
	}
	
	/**
	 * 根据编号获取打印任务
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2016年5月31日
	 */
	public PrintJob getPrintJob(String id) throws Exception{
		return printJobMapper.getPrintJob(id);
	}
	/**
	 * 根据编号及添加用户编号获取打印任务信息
	 * @param id
	 * @param userid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2016年5月31日
	 */
	public PrintJob getPrintJobWithUserid(String id,String userid) throws Exception{
		return printJobMapper.getPrintJobWithUserid(id,userid);
	}


	//===============================================================================//
	//================================== 打印任务回调  =================================//
	//===============================================================================//

	@Override
	public PageData showPrintJobCallHandlePageListData(PageMap pageMap) throws Exception {
		Map condition=pageMap.getCondition();
		String sort=(String) condition.get("sort");
		String order=(String) condition.get("order");
		if((null==sort || "".equals(sort.trim())) || (null==order || "".equals(order.trim()))){
			condition.remove("sort");
			condition.remove("order");
			pageMap.setOrderSql("id desc");
		}
		List<PrintJobCallHandle> list= getPrintJobCallHandleMapper().getPrintJobCallHandlePageList(pageMap);
		int iCount= getPrintJobCallHandleMapper().getPrintJobCallHandlePageCount(pageMap);
		PageData pageData=new PageData(iCount, list, pageMap);
		return pageData;
	}
	/**
	 * 添加打印任务
	 * @param printJobCallHandle
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2016年5月27日
	 */
	public boolean addPrintJobCallHandle(PrintJobCallHandle printJobCallHandle) throws Exception{
		if(StringUtils.isEmpty(printJobCallHandle.getAdduserid())){
			SysUser sysUser=getSysUser();
			printJobCallHandle.setAdduserid(sysUser.getUserid());
			printJobCallHandle.setAddusername(sysUser.getName());
			printJobCallHandle.setAddtime(new Date());
		}
		return printJobCallHandleMapper.addPrintJobCallHandle(printJobCallHandle)>0;
	}
	/**
	 * 更新打印回调任务状态，status 状态：1打印，0为打印申请
	 * @param printJobCallHandle
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2016年5月27日
	 */
	public boolean updatePrintJobCallHandleStatus(PrintJobCallHandle printJobCallHandle) throws Exception{
		SysUser sysUser=getSysUser();
		printJobCallHandle.setModifyuserid(sysUser.getUserid());
		printJobCallHandle.setModifyusername(sysUser.getName());
		printJobCallHandle.setModifytime(new Date());
		return printJobCallHandleMapper.updatePrintJobCallHandleStatus(printJobCallHandle)>0;
	}
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
	public List<PrintJobCallHandle> getPrintJobCallHandleListBy(PrintJobCallHandleQuery printJobBackHandleQuery) throws Exception{
		return printJobCallHandleMapper.getPrintJobCallHandleListBy(printJobBackHandleQuery);
	}


	//===============================================================================//
	//================================== 打印任务单据页  =================================//
	//===============================================================================//
	@Override
	public PageData showPrintJobDetailPageListData(PageMap pageMap) throws Exception {
		Map condition=pageMap.getCondition();
		String sort=(String) condition.get("sort");
		String order=(String) condition.get("order");
		if((null==sort || "".equals(sort.trim())) || (null==order || "".equals(order.trim()))){
			condition.remove("sort");
			condition.remove("order");
			pageMap.setOrderSql("id desc");
		}
		List<PrintJobDetail> list= getPrintJobDetailMapper().getPrintJobDetailPageList(pageMap);
		int iCount= getPrintJobDetailMapper().getPrintJobDetailPageCount(pageMap);
		PageData pageData=new PageData(iCount, list, pageMap);
		return pageData;
	}
	/**
	 * 添加打印任务单据页
	 * @param printJobDetail
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2016年06月29日
	 */
	public boolean addPrintJobDetail(PrintJobDetail printJobDetail) throws Exception{
		return printJobDetailMapper.insertPrintJobDetail(printJobDetail)>0;
	}
	/**
	 * 获取打印任务单据页
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2016年06月29日
	 */
	public PrintJobDetail getPrintJobDetail(String id) throws Exception{
		return printJobDetailMapper.getPrintJobDetail(id);
	}


	/**
	 * 删除打印页内容
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2016年5月27日
	 */
	public boolean deletePrintJobDetail(String id) throws Exception{
		return printJobDetailMapper.deletePrintJobDetail(id)>0;
	}
	public boolean updatePrintJobDetailReadflag( String id) throws Exception{
		return printJobDetailMapper.updatePrintJobDetailReadflag(id)>0;
	}


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
	public boolean addPrintJobDetailImage(PrintJobDetailImage printJobDetailImage) throws Exception{
		return printJobDetailImageMapper.insertPrintJobDetailImage(printJobDetailImage)>0;
	}
	/**
	 * 获取打印任务单据页
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2016年06月29日
	 */
	public PrintJobDetailImage getPrintJobDetailImage(String id) throws Exception{
		return printJobDetailImageMapper.getPrintJobDetailImage(id);
	}


	/**
	 * 删除打印页内容
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2016年5月27日
	 */
	public boolean deletePrintJobDetailImage(String id) throws Exception{
		return printJobDetailImageMapper.deletePrintJobDetailImage(id)>0;
	}

	@Override
	public String doCreatePrintJobDetailImageFileId() throws Exception{
		return printJobDetailImageMapper.getPrintJobDetailImageFileId();
	}

	//===============================================================================//
	//================================== 其他  =================================//
	//===============================================================================//


	/**
	 * 删除打印数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2016年05月27日
	 */
	public void clearPrintDataBeforeDateJob() throws Exception{

		String clearPrintDataIntervalDayStr= getSysParamValue("ClearPrintDataIntervalDay");
		int intervalDay=7;
		if(null!=clearPrintDataIntervalDayStr
				&& !"".equals(clearPrintDataIntervalDayStr.trim())
				&& StringUtils.isNumeric(clearPrintDataIntervalDayStr.trim())){
			intervalDay=Integer.parseInt(clearPrintDataIntervalDayStr);
		}
		if(intervalDay<0){
			intervalDay=7;
		}
		Date nowDate=new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(nowDate);
		calendar.add(calendar.DATE,0-intervalDay);
		Date operDate=calendar.getTime();

		printJobMapper.deletePrintJobBeforeDate(operDate);
		printJobCallHandleMapper.deletePrintJobCallHandleBeforeDate(operDate);
		Map imgQueryMap=new HashMap();
		imgQueryMap.put("beforedate",operDate);
		imgQueryMap.put("emptyfullpath","true");
		List<PrintJobDetailImage> imageList=printJobDetailImageMapper.getPrintJobDetailImageListBy(imgQueryMap);
		if(null!=imageList && imageList.size()>0){
			for(PrintJobDetailImage item : imageList){
				if(StringUtils.isNotEmpty(item.getFullpath())){
					String imageFilePath= CommonUtils.getDownFilePhysicalpath(UploadConfigUtils.getFilepath(),item.getFullpath());
					File imageFile = new File(imageFilePath);
					if(null!=imageFile && imageFile.exists()
							&& !imageFile.isDirectory()
							&& imageFile.isFile()){
						imageFile.delete();
					}
				}
			}
		}
		printJobDetailImageMapper.deletePrintJobDetailImageBeforeDate(operDate);
		printJobDetailMapper.deletePrintJobDetailBeforeDate(operDate);
	}
}
