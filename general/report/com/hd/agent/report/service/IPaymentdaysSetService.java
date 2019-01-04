/**
 * @(#)IPaymentdaysSetService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Aug 15, 2013 chenwei 创建版本
 */
package com.hd.agent.report.service;

import com.hd.agent.report.model.PaymentdaysSet;

import java.util.List;

/**
 * 
 * 超账期设置service
 * @author chenwei
 */
public interface IPaymentdaysSetService {
    /**
     * 添加超账期区间设置
     * @param list
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 15, 2013
     */
    public boolean addPaymentdays(List<PaymentdaysSet> list,String type) throws Exception;
    /**
     * 获取当前用户的超账期区间设置
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 15, 2013
     */
    public List getPaymentdaysSetInfo() throws Exception;

    /**
     * 获取当前用户的超账期区间设置
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 15, 2013
     */
    public List getPaymentdaysSetInfo(String type) throws Exception;
}

