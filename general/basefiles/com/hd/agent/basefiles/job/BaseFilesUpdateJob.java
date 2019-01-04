/**
 * @(#)BaseFilesUpdateJob.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Dec 9, 2013 chenwei 创建版本
 */
package com.hd.agent.basefiles.job;

import com.hd.agent.basefiles.service.IBaseFilesService;
import com.hd.agent.system.dao.SysParamMapper;
import com.hd.agent.system.model.SysParam;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hd.agent.basefiles.dao.GoodsMapper;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.job.BaseJob;

/**
 * 
 * 基础档案更新后，更新单据中相关的信息
 * @author chenwei
 */
public class BaseFilesUpdateJob extends BaseJob {

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		SysParamMapper sysParamMapper = (SysParamMapper)SpringContextUtils.getBean("sysParamMapper");
		IBaseFilesService baseFilesService = (IBaseFilesService)SpringContextUtils.getBean("baseFilesService");
		try {
			//获取是否执行客户历史数据系统参数
			String doEditCustomerTask = "1";
			SysParam sysParam = sysParamMapper.getSysParam("doEditCustomerTask");
			if(null != sysParam){
				doEditCustomerTask = sysParam.getPvalue();
			}
			if("1".equals(doEditCustomerTask)){
				baseFilesService.doAutoUpdateBillByBaseFiles();
				flag = true;
			}else{
				flag = true;
			}
		} catch (Exception e) {
			logger.error("定时器执行异常 基础档案（客户档案、品牌档案、品牌/厂家业务员）修改后更新单据数据失败", e);
		}
		super.executeInternal(jobExecutionContext);
	}
}

