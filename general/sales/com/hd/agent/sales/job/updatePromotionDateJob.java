package com.hd.agent.sales.job;

import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.sales.dao.OffpriceMapper;
import com.hd.agent.sales.dao.PromotionPackageMapper;
import com.hd.agent.sales.model.Offprice;
import com.hd.agent.sales.model.PromotionPackage;
import com.hd.agent.system.job.BaseJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;
import java.util.List;

/**
 * Created by lin_xx on 2016/5/27.
 */
public class updatePromotionDateJob extends BaseJob {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        try {

            OffpriceMapper offpriceMapper = (OffpriceMapper) SpringContextUtils.getBean("salesOffpriceMapper");
            String date = CommonUtils.getTodayDataStr();
            List<Offprice> list = offpriceMapper.getOffpriceListByDate(date);
            for(Offprice offprice : list){
                offprice.setStatus("4");
                offprice.setAudittime(null);
                offprice.setStoptime(new Date());
                offpriceMapper.updateOffpriceStatus(offprice);
            }
            PromotionPackageMapper promotionPackageMapper = (PromotionPackageMapper) SpringContextUtils.getBean("salesPromotionMapper");

            List<PromotionPackage> list1 = promotionPackageMapper.getPromotionListByDate(date);
            for(PromotionPackage promotionPackage : list1){
                promotionPackage.setStatus("4");
                promotionPackage.setAudittime(null);
                promotionPackage.setStoptime(new Date());
                promotionPackageMapper.updatePackageStatus(promotionPackage);
            }
            flag = true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("定时器执行异常 关闭促销期到期的促销单", e);
        }
        super.executeInternal(jobExecutionContext);
    }


}
