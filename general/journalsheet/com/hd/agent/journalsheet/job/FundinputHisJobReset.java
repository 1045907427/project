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

import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.journalsheet.dao.JournalSheetMapper;
import com.hd.agent.journalsheet.model.FundInput;
import com.hd.agent.system.job.BaseJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * 
 * 每天的资金信息重新生成
 * @author chenwei
 */
public class FundinputHisJobReset extends BaseJob {

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		JournalSheetMapper  journalSheetMapper = (JournalSheetMapper) SpringContextUtils.getBean("journalSheetMapper");
		try {
			journalSheetMapper.deleteTotalFundInputHis();

			String now = CommonUtils.getTodayDataStr();
			String date = "2013-01-01";
			while (date.compareTo(now) < 0){
				List<FundInput> list = journalSheetMapper.getFundinputHisList(date);
				for(FundInput fundInput : list){
					journalSheetMapper.deleteFundInputHis(fundInput.getId());
					journalSheetMapper.addFundinputHisJob(fundInput.getId());
				}
				journalSheetMapper.updateFundInputHisState();

				date = CommonUtils.getNextDayByDate(date);
			}
			flag = true;
		} catch (Exception e) {
			logger.error("定时器执行异常 每天的资金信息重新生成", e);
		}
		super.executeInternal(jobExecutionContext);
	}
}

