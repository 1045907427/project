/**
 * @(#)IOaPurchasePayService.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-9-14 limin 创建版本
 */
package com.hd.agent.oa.service;

import com.hd.agent.oa.model.OaPurchasePay;

/**
 * 行政采购付款申请单Service
 *
 * Created by limin on 2016/9/14.
 */
public interface IOaPurchasePayService {
    /**
     * 新增行政采购付款申请单
     *
     * @param id
     * @return
     * @author limin
     * @date Sep 18, 2016
     */
    public OaPurchasePay selectPurchasePay(String id) throws Exception;


    /**
     * 行政采购付款申请单新增
     *
     * @param pay
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 18, 2016
     */
    public int addOaPurchasePay(OaPurchasePay pay) throws Exception;

    /**
     * 行政采购付款申请单修改
     *
     * @param pay
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 18, 2016
     */
    public int editOaPurchasePay(OaPurchasePay pay) throws Exception;
}
