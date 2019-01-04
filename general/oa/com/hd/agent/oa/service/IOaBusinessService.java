/**
 * @(#)IOaBusinessService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年3月10日 limin 创建版本
 */
package com.hd.agent.oa.service;

import com.hd.agent.oa.model.*;
import com.hd.agent.sales.model.Offprice;

import java.util.List;
import java.util.Map;

/**
 * OA模块通用Service，
 *  预定此后所有OA模块与其他业务模块相关联的操作都在该接口中完成
 *
 * @author limin
 */
public interface IOaBusinessService {

    /**
     * 增加特价（OaOffPrice）
     * @param price
     * @param list
     * @return
     * @author limin
     * @date Mar 10, 2015
     */
    public Map addOffPriceByOffPrice(OaOffPrice price, List<OaOffPriceDetail> list) throws Exception;

    /**
     * 增加特价（OaOffPrice）
     * @param price
     * @return
     * @author limin
     * @date Mar 10, 2015
     */
    public Map rollbackOffPriceByOffPrice(OaOffPrice price) throws Exception;

    /**
     * 查询特价单是否已经生成
     * @param id
     * @return
     * @throws Exception
     */
    public Offprice oaOffPriceSelectOffPrice(String id) throws Exception;

    /**
     *商品价格调整单修改
     * @param oaGoodsPrice
     * @param oaGoodsPriceDetails
     * @return
     * @throws Exception
     */
    public int editGoodsInfoByOaGoodsPriceAdjustment(OaGoodsPrice oaGoodsPrice , List<OaGoodsPriceDetail> oaGoodsPriceDetails ) throws Exception;

    /**
     *
     * @param push
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 13, 2016
     */
    public boolean checkCustomerPushBanlanceByOaExpensePush(OaExpensePush push) throws Exception;

    /**
     * 生成客户应收款冲差单
     * @param push
     * @param list
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-1-9
     */
    public int addCustomerPushBanlanceByOaExpensePush(OaExpensePush push, List<OaExpensePushDetail> list) throws Exception;

    /**
     * 生成客户应收款冲差单
     * @param push
     * @param list
     * @param pushtype
     * @return
     * @throws Exception
     * @author limin
     * @date 2017-4-24
     */
    public int addCustomerPushBanlanceByOaExpensePush(OaExpensePush push, List<OaExpensePushDetail> list, String pushtype) throws Exception;

    /**
     * 删除客户应收款冲差单
     * @param push
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-1-16
     */
    public int rollbackCustomerPushBanlanceByOaExpensePush(OaExpensePush push) throws Exception;

    /**
     * 支付客户费用
     * @param push
     * @param list
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-1-19
     */
    public int addCustomerPayByOaExpensePush(OaExpensePush push, List<OaExpensePushDetail> list) throws Exception;

    /**
     * 支付客户费用
     * @param push
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-1-19
     */
    public int rollbackCustomerPayByOaExpensePush(OaExpensePush push) throws Exception;

    /**
     * check客户费用
     * @param push
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-04-08
     */
    public boolean checkCustomerPayByOaExpensePush(OaExpensePush push) throws Exception;

    /**
     * 根据工作委托流程新增工作委托规则
     * @param delegate
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 23, 2016
     */
    public int addDelegateByOaDelegate(OaDelegate delegate) throws Exception;

    /**
     * 根据工作委托流程回滚工作委托规则
     * @param delegate
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 23, 2016
     */
    public int rollbackDelegateByOaDelegate(OaDelegate delegate) throws Exception;

    /**
     *
     * @param delegate
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 23, 2016
     */
    public boolean checkDelegateByOaDelegate(OaDelegate delegate) throws Exception;
}
