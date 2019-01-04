package com.hd.agent.oa.dao;

import com.hd.agent.oa.model.OaBrandFee;
import com.hd.agent.oa.model.OaBrandFeeDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 品牌费用申请单（支付）Mapper
 * @author limin
 */
public interface OaBrandFeeMapper {

    /**
     * 查询品牌费用申请单
     * @param id
     * @return
     * @author limin
     * @date Apr 8, 2016
     */
    public OaBrandFee selectOaBrandFee(@Param("id") String id);

    /**
     * 删除品牌费用申请单
     * @param id
     * @return
     * @author limin
     * @date Apr 8, 2016
     */
    public int deleteOaBrandFee(@Param("id") String id);

    /**
     * 新增品牌费用申请单
     * @param fee
     * @return
     * @author limin
     * @date Apr 8, 2016
     */
    public int insertOaBrandFee(OaBrandFee fee);

    /**
     * 更新品牌费用申请单
     * @param fee
     * @return
     * @author limin
     * @date Apr 8, 2016
     */
    public int updateOaBrandFee(OaBrandFee fee);

    /**
     * 新增品牌费用申请单明细
     * @param detail
     * @return
     * @author limin
     * @date Apr 8, 2016
     */
    public int insertOaBrandFeeDetail(OaBrandFeeDetail detail);

    /**
     * 删除品牌费用申请单明细
     * @param billid
     * @return
     * @author limin
     * @date Apr 8, 2016
     */
    public int deleteOaBrandFeeDetailByBillid(@Param("billid")String billid);

    /**
     * 查询品牌费用申请单（支付）明细
     * @param billid
     * @return
     * @author limin
     * @date Apr 11, 2016
     */
    public List<OaBrandFeeDetail> selectBrandFeeDetailByBillid(@Param("billid")String billid);
}