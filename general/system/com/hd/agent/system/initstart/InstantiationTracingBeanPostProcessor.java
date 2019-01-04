/**
 * @(#)InstantiationTracingBeanPostProcessor.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年8月20日 chenwei 创建版本
 */
package com.hd.agent.system.initstart;

import com.hd.agent.agprint.service.IPrintTempletSubjectService;
import com.hd.agent.common.util.*;
import com.hd.agent.system.dao.SysParamMapper;
import com.hd.agent.system.model.SysParam;
import com.hd.agent.system.model.TaskSchedule;
import com.hd.agent.system.service.ISysCodeService;
import com.hd.agent.system.service.ISysParamService;
import com.hd.agent.system.service.ITaskScheduleService;
import com.hd.agent.system.service.IUpdateDBService;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.File;
import java.util.List;

/**
 * 
 * spring 完成启动后 执行操作（更新数据）
 * @author chenwei
 */
public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger logger = Logger.getLogger(InstantiationTracingBeanPostProcessor.class);
	private ISysParamService sysParamService=null;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		//启动时执行更新数据库方法
		handleUpdateDB();
		//启动时执行定时任务方法
		handleScheduleJob();
		//启动时获取菜单配置文件生成静态类
		getSysBillGoodsNumDecimalLen();
		//启动时获取菜单配置文件生成静态类
		getMenuProperties();
		//创建编码表缓存js
		createSyscodeCacheJs();
		//启动时执行更新打印模板分类缓存JS
		createPrintTempletSubjectCacheJs();
		//创建打印方式js
		createSysPrintToolTypeJs();
	}

	/**
	 * 启动时执行更新数据库方法
	 * @param
	 * @return void
	 * @throws
	 * @author zhanghonghui
	 * @date Sep 08, 2017
	 */
	private void handleUpdateDB(){
		try {
			//执行更新数据库方法
			IUpdateDBService updateDBService = (IUpdateDBService) SpringContextUtils.getBean("updateDBService");
			updateDBService.updateDB();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("启动时执行更新数据库方法失败",ex);
		}
	}
	/**
	 * 启动时执行定时任务方法
	 * @param
	 * @return void
	 * @throws
	 * @author zhanghonghui
	 * @date Sep 08, 2017
	 */
	private void handleScheduleJob(){
		try{
			ITaskScheduleService taskScheduleService = (ITaskScheduleService) SpringContextUtils.getBean("taskScheduleService");
			//获取需自动启用state（8）的定时器任务，启用该定时器
			List<TaskSchedule> taskSchedulelist = taskScheduleService.getNeedAutoStartTaskScheduleList();
			for(TaskSchedule taskSchedule : taskSchedulelist){
				taskScheduleService.startTaskSchedule(taskSchedule.getTaskid(),taskSchedule.getTeam());
			}
		}catch (Exception ex){
			logger.error("启动时执行定时任务方法失败",ex);
		}
	}

	/**
	 * 初始化单据商品明细数量输入小数位位数
	 * @param
	 * @return void
	 * @throws
	 * @author zhanghonghui
	 * @date Sep 07, 2017
	 */
	private void getSysBillGoodsNumDecimalLen(){
		try{
			if(null==sysParamService){
				sysParamService=(ISysParamService) SpringContextUtils.getBean("sysParamService");
			}
			//初始化单据商品明细数量输入小数位位数，判断是否允许输入小数位，不允许位数为0，允许位数为3
			BillGoodsNumDecimalLenUtils.decimalLen = 0;
			SysParam ibgnadpSysParam = sysParamService.getSysParam("isBillGoodsNumAllowDecimalPlace");
			if(null != ibgnadpSysParam){
				if("1".equals(ibgnadpSysParam.getPvalue())){
					BillGoodsNumDecimalLenUtils.decimalLen = 3;
				}
			}
		}catch (Exception ex){
			logger.error("初始化单据商品明细数量输入小数位位数",ex);
		}
	}

	/**
	 * 启动时获取菜单配置文件生成静态类
	 * @param
	 * @return void
	 * @throws
	 * @author wanghongteng
	 * @date 2017-6-20
	 */
	private void getMenuProperties(){
		try {
			if(null==sysParamService){
				sysParamService=(ISysParamService) SpringContextUtils.getBean("sysParamService");
			}
			SysParam sysParam=sysParamService.getSysParam("COMPANYNAME");
			String companyname = sysParam.getPvalue();
			MenuPropertiesUtils.readPropertiesFileNewAesDecrypt(OfficeUtils.getFilepath()+"/"+CommonUtils.MD5(companyname)+".properties");
		}catch (Exception ex){
			logger.error("启动时获取菜单配置文件生成静态类失败",ex);
		}
	}
	/**
	 * 启动时执行更新编码表缓存JS
	 * @param
	 * @return void
	 * @throws
	 * @author zhang_honghui
	 * @date Apr 16, 2017
	 */
	private void createSyscodeCacheJs() {
		try{
			ISysCodeService sysCodeService=(ISysCodeService) SpringContextUtils.getBean("sysCodeService");
			String codestr = sysCodeService.getAllSysCodeList();
			String filepath = FileUtils.getApplicationRealPath();

			String syscodeFilepath = filepath + "/js/syscode.js";
			if(null!=codestr&&!"".equals(codestr.trim())){
				codestr="var codeJsonCache="+codestr+";";
			}else{
				codestr="var codeJsonCache={};";
			}
			String newStr =  codestr.trim();
			String oldStr = FileUtils.readTxtFile(new File(syscodeFilepath), "UTF-8").trim();
			if(!newStr.equals(oldStr)) {
				FileUtils.writeTxtFile(newStr, syscodeFilepath);
			}
		}catch (Exception ex){
			logger.error("启动时执行更新编码表缓存JS失败",ex);
		}
	}

	/**
	 * 启动时执行更新打印模板分类缓存JS
	 * @param
	 * @return void
	 * @throws
	 * @author zhang_honghui
	 * @date Apr 16, 2017
	 */
	private void createPrintTempletSubjectCacheJs(){
		try {
			IPrintTempletSubjectService printTempletSubjectService=(IPrintTempletSubjectService) SpringContextUtils.getBean("printTempletSubjectService");
			String codestr = printTempletSubjectService.getPrintTempletSubjectListJsonCache();
			String filepath = FileUtils.getApplicationRealPath();
			filepath += "/js/printTempletSubjectCache.js";
			filepath = CommonUtils.filterFilePathSaparator(filepath);
			if(null!=codestr&&!"".equals(codestr.trim())){
				codestr="var printTempletSubjectCache="+codestr+";";
			}else{
				codestr="var printTempletSubjectCache={};";
			}
			FileUtils.writeTxtFile(codestr,filepath);
		}catch (Exception ex){
			logger.error("启动时执行更新打印模板分类缓存JS失败",ex);
		}
	}

	/**
	 * 生成打印方式类型js
	 * @param
	 * @return void
	 * @throws
	 * @author zhanghonghui
	 * @date Sep 07, 2017
	 */
	private void createSysPrintToolTypeJs(){
		try{
			if(null==sysParamService){
				sysParamService=(ISysParamService) SpringContextUtils.getBean("sysParamService");
			}
			String filepath = FileUtils.getApplicationRealPath();
			filepath += "/js/sysPrintToolTypeCache.js";
			filepath = CommonUtils.filterFilePathSaparator(filepath);
			String printtoolstr=sysParamService.getPrintToolTypeJsonCache();
			if(null!=printtoolstr&&!"".equals(printtoolstr.trim())){
				printtoolstr="var sysPrintToolTypeCache="+printtoolstr+";";
			}else{
				printtoolstr="var sysPrintToolTypeCache={};";
			}
			FileUtils.writeTxtFile(printtoolstr,filepath);

		}catch (Exception ex){
			logger.error("启动时获取菜单配置文件生成静态类失败",ex);
		}
	}
}

