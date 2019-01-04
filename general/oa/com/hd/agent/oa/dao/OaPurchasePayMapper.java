/**
 * @(#)OaPurchasePayMapper.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-9-14 limin 创建版本
 */
package com.hd.agent.oa.dao;

import com.hd.agent.oa.model.OaPurchasePay;
import org.apache.ibatis.annotations.Param;

/**
 * 行政采购付款申请单Mapper
 *
 * Created by limin on 2016/9/14.
 */
public interface OaPurchasePayMapper {

    /**
     * 新增行政采购付款申请单
     *
     * @param pay
     * @return
     * @author limin
     * @date Sep 14, 2016
     */
    public int insertPurchasePay(OaPurchasePay pay);

    /**
     * 新增行政采购付款申请单
     *
     * @param pay
     * @return
     * @author limin
     * @date Sep 14, 2016
     */
    public int updatePurchasePay(OaPurchasePay pay);

    /**
     * 新增行政采购付款申请单
     *
     * @param id
     * @return
     * @author limin
     * @date Sep 18, 2016
     */
    public OaPurchasePay selectPurchasePay(@Param("id") String id);
}