package com.hd.agent.oa.service;

import com.hd.agent.oa.model.OaMatcost;
import com.hd.agent.oa.model.OaMatcostDetail;

import java.util.List;

public interface IOaMatcostService {

    /**
     * 查询代垫费用申请单
     *
     * @param id
     * @return
     * @author limin
     * @date Feb 12, 2018
     */
    public OaMatcost getOaMatcost(String id);

    /**
     * 根据单据编号查询代垫费用明细list
     *
     * @param billid
     * @return
     * @author limin
     * @date Feb 12, 2018
     */
    public List<OaMatcostDetail> getOaMatcostDetailListByBillid(String billid) throws Exception;

    /**
     * 新增代垫费用申请单
     *
     * @param matcost
     * @param detailList
     * @return
     * @date Feb 13, 2018
     */
    public int addOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception;

    /**
     * 新增代垫费用申请单
     *
     * @param matcost
     * @param detailList
     * @return
     * @date Feb 13, 2018
     */
    public int editOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception;
}
