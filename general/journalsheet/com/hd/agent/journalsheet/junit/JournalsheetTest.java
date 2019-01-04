/**
 * @(#)SalesReportServiceTest.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 15, 2013 chenwei 创建版本
 */
package com.hd.agent.journalsheet.junit;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.journalsheet.dao.JournalSheetMapper;
import com.hd.agent.journalsheet.model.FundInput;

/**
 * 
 * 
 * @author chenwei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:WebRoot/WEB-INF/applicationContext.xml"})
public class JournalsheetTest {
	@Autowired
	private JournalSheetMapper journalSheetMapper;
	@Test
	public void addStorageInoutReportByDay(){
		try {
			boolean flag = true;
			String date = CommonUtils.getYestodayDateStr();
			List<FundInput> list = journalSheetMapper.getFundinputHisList(date);
			for(FundInput fundInput : list){
				journalSheetMapper.deleteFundInputHis(fundInput.getId());
				journalSheetMapper.addFundinputHisJob(fundInput.getId());
			}
			journalSheetMapper.updateFundInputHisState();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

