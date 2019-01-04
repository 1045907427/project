package com.hd.agent.report.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.report.model.PaymentdaysSet;

/**
 * 超账期设置dao
 * @author chenwei
 */
public interface PaymentdaysSetMapper {
    /**
     * 添加超账期设置
     * @param paymentdaysSet
     * @return
     * @author chenwei
     * @date Aug 15, 2013
     */
    public int addPaymentdays(PaymentdaysSet paymentdaysSet);
    /**
     * 根据用户编号删除超账期设置
     * @param userid
     * @return
     * @author chenwei
     * @date Aug 15, 2013
     */
    public int deletePaymentdays(@Param("userid")String userid,@Param("type") String type);
    /**
     * 根据用户编号获取超账期区间设置
     * @param userid
     * @return
     * @author chenwei
     * @date Aug 15, 2013
     */
    public List getPaymentdaysSetByUserid(@Param("userid")String userid,@Param("type") String type);
    /**
     * 根据用户编号获取超支期区间设置
     * @param userid
     * @param seq
     * @return
     * @author chenwei
     * @date Sep 12, 2013
     */
    public PaymentdaysSet getPaymentdaysSetByUseridAndSeq(@Param("userid")String userid,@Param("seq")String seq,@Param("type") String type);

    /**
     * 根据用户编号获取库龄区间设置
     * @param userid
     * @return
     * @author chenwei
     * @date Aug 15, 2013
     */
    public List getInventoryAgedaysSetByUserid(@Param("userid")String userid);
}