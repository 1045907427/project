/**
 * @(#)OaMatcostMapper.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2018-2-12 limin 创建版本
 */
package com.hd.agent.oa.dao;

import com.hd.agent.oa.model.OaMatcost;
import com.hd.agent.oa.model.OaMatcostDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 代垫费用申请单Mapper
 */
public interface OaMatcostMapper {

    /**
     * 新增代垫费用申请单
     *
     * @param matcost
     * @return
     * @author limin
     * @date Feb 12, 2018
     */
    public int insertOaMatcost(OaMatcost matcost);

    /**
     * 修改代垫费用申请单
     *
     * @param matcost
     * @return
     * @author limin
     * @date Feb 12, 2018
     */
    public int updateOaMatcost(OaMatcost matcost);

    /**
     * 查询代垫费用申请单
     *
     * @param id
     * @return
     * @author limin
     * @date Feb 12, 2018
     */
    public OaMatcost selectOaMatcost(@Param("id") String id);

    /**
     * 新增代垫费用申请单明细
     *
     * @param detail
     * @return
     * @author limin
     * @date Feb 12, 2018
     */
    public int insertOaMatcostDetail(OaMatcostDetail detail);

    /**
     * 根据单据编号查询代垫费用明细list
     *
     * @param billid
     * @return
     * @author limin
     * @date Feb 12, 2018
     */
    public List<OaMatcostDetail> selectOaMatcostDetailListByBillid(@Param("billid") String billid);

    /**
     * 删除代垫费用申请单明细
     *
     * @param billid
     * @return
     * @author limin
     * @date Feb 13, 2018
     */
    public int deleteOaMatcostDetailByBillid(@Param("billid") String billid);
}