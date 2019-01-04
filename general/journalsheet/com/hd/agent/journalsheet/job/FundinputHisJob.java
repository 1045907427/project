/**
 * @(#)FundinputHisJob.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Dec 10, 2013 chenwei 创建版本
 */
package com.hd.agent.journalsheet.job;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.journalsheet.dao.JournalSheetMapper;
import com.hd.agent.journalsheet.model.FundInput;
import com.hd.agent.system.job.BaseJob;

/**
 * 
 * 记录每天的资金信息
 * @author chenwei
 */
public class FundinputHisJob extends BaseJob {

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		JournalSheetMapper  journalSheetMapper = (JournalSheetMapper) SpringContextUtils.getBean("journalSheetMapper");
		try {
			String date = CommonUtils.getYestodayDateStr();
			List<FundInput> list = journalSheetMapper.getFundinputHisList(date);
			for(FundInput fundInput : list){
				journalSheetMapper.deleteFundInputHis(fundInput.getId());
				journalSheetMapper.addFundinputHisJob(fundInput.getId());
			}
			journalSheetMapper.updateFundInputHisState();
			flag = true;
		} catch (Exception e) {
			logger.error("定时器执行异常 记录每日资金信息", e);
		}
		super.executeInternal(jobExecutionContext);
	}
}

