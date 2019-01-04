/**
 * @(#)IOaBrandFeeService.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-4-8 limin 创建版本
 */
package com.hd.agent.oa.service;

import com.hd.agent.oa.model.OaBrandFee;
import com.hd.agent.oa.model.OaBrandFeeDetail;

import java.util.List;

/**
 * 品牌费用申请单（支付）Service
 * 
 * @author limin
 */
public interface IOaBrandFeeService {

    /**
     * 查询品牌费用申请单（支付）
     * @param id
     * @return
     * @author limin
     * @date Apr 11, 2016
     */
    public OaBrandFee selectOaBrandFee(String id) throws Exception;

    /**
     * 查询品牌费用申请单（支付）明细
     * @param billid
     * @return
     * @author limin
     * @date Apr 11, 2016
     */
    public List<OaBrandFeeDetail> selectBrandFeeDetailByBillid(String billid) throws Exception;

    /**
     * 新增品牌费用申请单（支付）
     * @param fee
     * @param list
     * @return
     * @author limin
     * @date Apr 11, 2016
     */
    public int addBrandFee(OaBrandFee fee, List<OaBrandFeeDetail> list) throws Exception;

    /**
     * 编辑品牌费用申请单（支付）
     * @param fee
     * @param list
     * @return
     * @author limin
     * @date Apr 11, 2016
     */
    public int editBrandFee(OaBrandFee fee, List<OaBrandFeeDetail> list) throws Exception;
}

