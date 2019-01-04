package com.hd.agent.oa.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.model.OaCustomerFee;
import com.hd.agent.oa.model.OaCustomerFeeDetail;

import java.util.List;

/**
 * 客户费用申请单（账扣）Service
 * @author limin
 * @date Mar 23, 2016
 */
public interface IOaCustomerFeeService {

    /**
     * 查询客户费用
     * @param id
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 23, 2016
     */
    public OaCustomerFee selectOaCustomerFee(String id) throws Exception;

    /**
     * 新增客户费用
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 29, 2016
     */
    public int addOaCustomerFee(OaCustomerFee fee, List<OaCustomerFeeDetail> list) throws Exception;

    /**
     * 编辑客户费用
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 29, 2016
     */
    public int editOaCustomerFee(OaCustomerFee fee, List<OaCustomerFeeDetail> list) throws Exception;

    /**
     * 查询客户费用明细
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 29, 2016
     */
    public PageData getCustomerFeeDetailList(PageMap map) throws Exception;


    /**
     * 根据billid查询客户费用明细
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 30, 2016
     */
    public List<OaCustomerFeeDetail> getCustomerFeeDetailListByBillid(String billid) throws Exception;
}
