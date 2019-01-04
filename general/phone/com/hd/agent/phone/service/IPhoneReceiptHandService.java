package com.hd.agent.phone.service;

import com.hd.agent.common.util.PageMap;

import java.util.List;
import java.util.Map;

/**
 * 回单交接单service
 * Created by chenwei on 2015-05-27.
 */
public interface IPhoneReceiptHandService {
    /**
     * 通过查询条件 获取客户的应收款信息
     * @param con
     * @return
     * @throws Exception
     */
    public List getReceiptListGroupByCustomerForReceiptHand(String con) throws Exception;

    /**
     * 根据条件 获取客户回单交接单相关单据列表
     * @param pageMap
     * @return
     * @throws Exception
     */
    public Map getCustomerReceiptHandDetailList(PageMap pageMap) throws Exception;

    /**
     * 根据单据号 生成回单交接单
     * @param ids
     * @return
     * @throws Exception
     */
    public Map addReceiptHand(String ids) throws Exception;

    /**
     * 根据条件获取回单交接单列表
     * @param pageMap
     * @return
     * @throws Exception
     */
    public List getReceiptHandList(PageMap pageMap) throws Exception;

    /**
     * 获取回单交接单客户列表
     * @param id
     * @return
     * @throws Exception
     */
    public List getReceiptHandCustomerList(String id) throws Exception;

    /**
     * 根据交接单单据编号和客户编号 获取客户的单据列表
     * @param id
     * @param customerid
     * @return
     * @throws Exception
     */
    public List getReceiptHandBillList(String id,String customerid) throws Exception;
}
