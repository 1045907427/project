package com.hd.agent.oa.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.model.OaCustomerFee;
import com.hd.agent.oa.model.OaCustomerFeeDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户费用申请单（账扣）Mapper
 * @author limin
 * @date Mar 23, 2016
 */
public interface OaCustomerFeeMapper {

    /**
     * 查询客户费用
     * @param id
     * @return
     * @author limin
     * @date Mar 23, 2016
     */
    public OaCustomerFee selectOaCustomerFee(String id);

    /**
     * 删除客户费用
     * @param id
     * @return
     * @author limin
     * @date Mar 23, 2016
     */
    public int deleteOaCustomerFee(String id);

    /**
     * 客户费用登录
     * @param fee
     * @return
     * @author limin
     * @date Mar 23, 2016
     */
    public int insertOaCustomerFee(OaCustomerFee fee);

    /**
     * 客户费用更新
     * @param fee
     * @return
     * @author limin
     * @date Mar 23, 2016
     */
    public int updateOaCustomerFee(OaCustomerFee fee);

    /**
     * 客户费用明细登录
     * @param detail
     * @return
     * @author limin
     * @date Mar 23, 2016
     */
    public int insertOaCustomerFeeDetail(OaCustomerFeeDetail detail);

    /**
     * 根据billid删除客户费用明细
     * @param billid
     * @return
     * @author limin
     * @date Mar 23, 2016
     */
    public int deleteOaCustomerFeeDetailByBillid(String billid);

    /**
     * 查询客户费用明细List
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 29, 2016
     */
    public List<OaCustomerFeeDetail> getCustomerFeeDetailList(PageMap map);

    /**
     * 查询客户费用明细List
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 29, 2016
     */
    public int getCustomerFeeDetailListCount(PageMap map);

    /**
     * 查询客户费用明细List
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 29, 2016
     */
    public List<OaCustomerFeeDetail> getCustomerFeeDetailListByBillid(@Param("billid") String billid);
}