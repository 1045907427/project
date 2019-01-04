/**
 * @(#)SalesReportServiceTest.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 15, 2013 chenwei 创建版本
 */
package com.hd.agent.report.junit;


import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.model.SysParam;
import com.hd.agent.system.service.ISysParamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.report.service.IPurchaseReportService;
import com.hd.agent.report.service.ISalesReportService;
import com.hd.agent.report.service.IStorageReportService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * 
 * @author chenwei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:WebRoot/WEB-INF/applicationContext.xml"})
public class SalesReportServiceTest {
	@Autowired
	private ISalesReportService salesReportService;
	@Autowired
	private IStorageReportService storageReportService;
	@Autowired
	private IPurchaseReportService purchaseReportService;
	@Test
	public void addStorageInoutReportByDay(){
        try {
            boolean flag = true;
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
            String yesdate = CommonUtils.getYestodayDateStr();
            Date date1 = sdf.parse(yesdate);
            ISysParamService sysParamService = (ISysParamService) SpringContextUtils.getBean("sysParamService");
            SysParam sysParam = sysParamService.getSysParam("unauditDays");
            if(null != sysParam){
                int days = Integer.parseInt(sysParam.getPvalue());
                Date date =sdf.parse(yesdate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-days);

                String langdate = sdf.format(calendar.getTime());
                for(int i=0;i<=days;i++){
                    Date date2 =sdf.parse(langdate);
                    if(!date2.after(date1)){
                        flag = storageReportService.addStorageNumEveryday(langdate);

                        Calendar calendar2 = Calendar.getInstance();
                        calendar2.setTime(date2);
                        calendar2.set(Calendar.DATE,calendar2.get(Calendar.DATE)+1);
                        langdate = sdf.format(calendar2.getTime());
                    }
                }
            }else{
                flag = storageReportService.addStorageNumEveryday(yesdate);
            }
        } catch (Exception e) {

        }
	}


}

