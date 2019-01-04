/**
 * @(#)IOAPayService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年11月24日 chenwei 创建版本
 */
package com.hd.agent.oa.service;

import java.util.List;

import com.hd.agent.oa.model.*;

/**
 * OA支付相关接口
 *
 * @author chenwei
 */
public interface IOaPayService {

    /**
     * 通过货款支付单生成供应商付款单
     * @param oaSupplierpay		供应商付款单
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月24日
     */
    public int addPayOrderBySupplierPay(OaSupplierPay oaSupplierpay) throws Exception;

    /**
     * 通过客户费用支付单  生成银行支付单
     * @param oaCustomerPay
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月24日
     */
    public int addBankBillByOaCustomerPay(OaCustomerPay oaCustomerPay) throws Exception;

    /**
     * 通过个人借款(预付款) 生成银行支付单
     * @param oaPersonalLoan
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月24日
     */
    public boolean addBankBillByOaPersonalLoan(OaPersonalLoan oaPersonalLoan) throws Exception;

    /**
     * 通过内部分摊单据 生成部门日常费用
     * @param OaInnerShare
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月24日
     */
    public boolean addDeptDailyCostByOaInnerShare(OaInnerShare OaInnerShare) throws Exception;

    /**
     * 根据日常费用支付单 生成部门日常费用
     * @param dailyPay			日常费用支付单
     * @param detailList		日常费用支付单明细
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月24日
     */
    public boolean addDeptDailCostByDailyPay(OaDailyPay dailyPay,List<OaDailyPayDetail> detailList) throws Exception;

    /**
     * 删除银行支付单（客户费用支付申请单）
     * @param pay
     * @return
     * @author limin
     * @date 2015-1-7
     */
    public int rollbackBankBillByOaCustomerPay(OaCustomerPay pay) throws Exception;

    /**
     * 删除部门日常费用
     * @param pay
     * @param list
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-1-10
     */
    public boolean rollbackDeptDailCostByDailyPay(OaDailyPay pay, List<OaDailyPayDetail> list) throws Exception;

    /**
     * 删除供应商付款单
     * @param oaSupplierpay
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-1-10
     */
    public int rollbackPayOrderBySupplierPay(OaSupplierPay oaSupplierpay) throws Exception;

    /**
     * 判断银行支付单是否生成（客户费用支付申请单）
     * @param pay
     * @return
     * @author limin
     * @date 2015-1-16
     */
    public boolean checkBankBillByOaCustomerPay(OaCustomerPay pay) throws Exception;

    /**
     * 判断部门日常费用是否生成
     * @param pay
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-1-16
     */
    public boolean checkDeptDailCostByDailyPay(OaDailyPay pay) throws Exception;

    /**
     * 删除供应商付款单
     * @param oaSupplierpay
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-1-16
     */
    public boolean checkPayOrderBySupplierPay(OaSupplierPay oaSupplierpay) throws Exception;

    /**
     * 支付客户费用
     * @param pay
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-1-19
     */
    public int addCustomerFeeByCustomerPay(OaCustomerPay pay) throws Exception;

    /**
     * 还原客户费用（相对于支付）
     * @param pay
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-1-19
     */
    public int rollbackCustomerFeeByCustomerPay(OaCustomerPay pay) throws Exception;

    /**
     * 通过个人借款(预付款) 生成银行支付单
     * @param oaPersonalLoan
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-04-08
     */
    public boolean rollbackBankBillByOaPersonalLoan(OaPersonalLoan oaPersonalLoan) throws Exception;

    /**
     * 判断借贷单是否生成
     * @param oaPersonalLoan
     * @return
     * @author limin
     * @date 2015-04-08
     */
    public boolean checkBankBillByOaPersonalLoan(OaPersonalLoan oaPersonalLoan) throws Exception;

    /**
     * check生成部门日常费用
     * @param oaInnerShare
     * @return
     * @throws Exception
     * @author limin
     * @date 2015年4月8日
     */
    public boolean checkDeptDailyCostByOaInnerShare(OaInnerShare oaInnerShare) throws Exception;

    /**
     * 删除客户费用（账扣）关联单据
     * @param fee
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 29, 2016
     */
    public int rollbackBillByOaCustomerFee(OaCustomerFee fee) throws Exception;

    /**
     * check客户费用申请单（账扣）生成的收款单
     * @param fee
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 29, 2016
     */
    public boolean checkCollectionOrderByOaCustomerFee(OaCustomerFee fee) throws Exception;

    /**
     * check客户费用申请单（账扣）生成的代垫
     * @param fee
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 29, 2016
     */
    public boolean checkMatcostsInputByOaCustomerFee(OaCustomerFee fee) throws Exception;

    /**
     * check客户费用申请单（账扣）生成的客户费用
     * @param fee
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 29, 2016
     */
    public boolean checkCustomerFeeByOaCustomerFee(OaCustomerFee fee) throws Exception;

    /**
     * check客户费用支付申请单生成的客户费用
     * @param fee
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 20, 2016
     */
    public boolean checkCustomerFeeByOaCustomerPay(OaCustomerPay fee) throws Exception;

    /**
     * 根据客户费用申请单（账扣）生成收款单
     * @param fee
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 30, 2016
     */
    public int addCollectionOrderByOaCustomerFee(OaCustomerFee fee) throws Exception;

    /**
     * 回滚客户费用申请单（账扣）生成的收款单
     * @param fee
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 30, 2016
     */
    public int rollbackCollectionOrderByOaCustomerFee(OaCustomerFee fee) throws Exception;

    /**
     * 根据客户费用申请单（账扣）生成代垫
     * @param fee
     * @param list
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 30, 2016
     */
    public int addMatcostsInputByOaCustomerFee(OaCustomerFee fee, List<OaCustomerFeeDetail> list) throws Exception;

    /**
     * 回滚客户费用申请单（账扣）生成的代垫
     * @param fee
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 30, 2016
     */
    public int rollbackMatcostsInputByOaCustomerFee(OaCustomerFee fee) throws Exception;

    /**
     * 根据客户费用申请单（账扣）生成客户应付费用
     * @param fee
     * @param list
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 30, 2016
     */
    public int addCustomerFeeByOaCustomerFee(OaCustomerFee fee, List<OaCustomerFeeDetail> list) throws Exception;

    /**
     * 根据客户费用申请单（账扣）生成客户应付费用
     * @param fee
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 30, 2016
     */
    public int rollbackCustomerFeeByOaCustomerFee(OaCustomerFee fee) throws Exception;

    /**
     * check品牌费用申请单（支付）生成的代垫
     * @param fee
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 11, 2016
     */
    public boolean checkMatcostsInputByOaBrandFee(OaBrandFee fee) throws Exception;

    /**
     * check品牌费用申请单（支付）生成的客户费用
     * @param fee
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 11, 2016
     */
    public boolean checkCustomerFeeByOaBrandFee(OaBrandFee fee) throws Exception;

    /**
     * 根据品牌费用申请单（支付）生成代垫
     * @param fee
     * @param list
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 11, 2016
     */
    public int addMatcostsInputByOaBrandFee(OaBrandFee fee, List<OaBrandFeeDetail> list) throws Exception;

    /**
     * 回滚品牌费用申请单（支付）生成的代垫
     * @param fee
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 11, 2016
     */
    public int rollbackMatcostsInputByOaBrandFee(OaBrandFee fee) throws Exception;

    /**
     * 根据品牌费用申请单（支付）生成客户应付费用
     * @param fee
     * @param list
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 11, 2016
     */
    public int addCustomerFeeByOaBrandFee(OaBrandFee fee, List<OaBrandFeeDetail> list) throws Exception;

    /**
     * 根据品牌费用申请单（支付）生成客户应付费用
     * @param fee
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 11, 2016
     */
    public int rollbackCustomerFeeByOaBrandFee(OaBrandFee fee) throws Exception;

    /**
     * 根据行政采购付款申请单生成银行借贷单
     *
     * @param pay
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 18, 2016
     */
    public int addBankBillByOaPurchasePay(OaPurchasePay pay) throws Exception;

    /**
     * 根据行政采购付款申请单生成银行借贷单
     *
     * @param pay
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 18, 2016
     */
    public int rollbackBankBillByOaPurchasePay(OaPurchasePay pay) throws Exception;

    /**
     * 判断银行支付单是否生成（行政采购付款申请单）
     *
     * @param pay
     * @return
     * @author limin
     * @date Sep 18, 2016
     */
    public boolean checkBankBillByOaPurchasePay(OaPurchasePay pay) throws Exception;

    /**
     * 通过品牌费用申请单（支付） 生成银行支付单
     * @param oaBrandFee
     * @param details
     * @return
     * @throws Exception
     * @author limin
     * @date Nov 8, 2016
     */
    public int addBankBillByOaBrandFee(OaBrandFee oaBrandFee, List<OaBrandFeeDetail> details) throws Exception;

    /**
     * 通过品牌费用申请单（支付） 生成银行支付单
     * @param oaBrandFee
     * @return
     * @throws Exception
     * @author limin
     * @date Nov 8, 2016
     */
    public int rollbackBankBillByOaBrandFee(OaBrandFee oaBrandFee) throws Exception;

    /**
     * 判断银行支付单是否生成（通过品牌费用申请单（支付））
     *
     * @param oaBrandFee
     * @return
     * @author limin
     * @date Nov 8, 2016
     */
    public boolean checkBankBillByOaBrandFee(OaBrandFee oaBrandFee) throws Exception;

    /**
     * 通过代垫费用申请单生成代垫费用
     *
     * @param matcost
     * @param detailList
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 13, 2018
     */
    public int addMatcostByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception;

    /**
     * 通过代垫费用申请单回滚代垫费用
     *
     * @param matcost
     * @param detailList
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 13, 2018
     */
    public int rollbackMatcostByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception;

    /**
     * 通过代垫费用申请单check代垫费用
     *
     * @param matcost
     * @param detailList
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 13, 2018
     */
    public boolean checkMatcostByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception;

    /**
     * 通过代垫费用申请单生成客户费用
     *
     * @param matcost
     * @param detailList
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 13, 2018
     */
    public int addCustomerFeeByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList, String billtype) throws Exception;

    /**
     * 通过代垫费用申请单回滚客户费用
     *
     * @param matcost
     * @param detailList
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 13, 2018
     */
    public int rollbackCustomerFeeByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList, String billtype) throws Exception;

    /**
     * 通过代垫费用申请单check客户费用
     *
     * @param matcost
     * @param detailList
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 13, 2018
     */
    public boolean checkCustomerFeeByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList, String billtype) throws Exception;

    /**
     * 通过代垫费用申请单生成客户冲差
     *
     * @param matcost
     * @param detailList
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 13, 2018
     */
    public int addCustomerPushBalanceByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception;

    /**
     * 通过代垫费用申请单回滚客户冲差
     *
     * @param matcost
     * @param detailList
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 13, 2018
     */
    public int rollbackCustomerPushBalanceByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception;

    /**
     * 通过代垫费用申请单check客户冲差
     *
     * @param matcost
     * @param detailList
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 13, 2018
     */
    public boolean checkCustomerPushBalanceByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception;

    /**
     * 通过代垫费用申请单生成银行借贷单
     *
     * @param matcost
     * @param detailList
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 13, 2018
     */
    public int addBankBillByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception;

    /**
     * 通过代垫费用申请单回滚银行借贷单
     *
     * @param matcost
     * @param detailList
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 13, 2018
     */
    public int rollbackBankBillByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception;

    /**
     * 通过代垫费用申请单check银行借贷单
     *
     * @param matcost
     * @param detailList
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 13, 2018
     */
    public boolean checkBankBillByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception;

    /**
     * 通过代垫费用申请单生成收款单
     *
     * @param matcost
     * @param detailList
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 13, 2018
     */
    public int addCollectionOrderByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception;

    /**
     * 通过代垫费用申请单回滚收款单
     *
     * @param matcost
     * @param detailList
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 13, 2018
     */
    public int rollbackCollectionOrderByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception;

    /**
     * 通过代垫费用申请单check收款单
     *
     * @param matcost
     * @param detailList
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 13, 2018
     */
    public boolean checkCollectionOrderByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception;
}

