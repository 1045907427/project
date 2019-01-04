/**
 * @(#)CustomerPayDetailMapper.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-11-18 limin 创建版本
 */
package com.hd.agent.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.model.OaCustomerPayDetail;

/**
 * 客户费用支付申请单明细Mapper
 * 
 * @author limin
 */
public interface OaCustomerPayDetailMapper {

	/**
	 * 查询客户费用支付单明细List
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2014-11-18
	 */
	public List<OaCustomerPayDetail> selectOaCustomerPayDetailList(PageMap map);

	/**
	 * 查询客户费用支付单明细总数
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2014-11-18
	 */
	public int selectOaCustomerPayDetailListCnt(PageMap map);
	
	/**
	 * 删除客户费用支付单明细
	 * @param billid 客户费用申请单编号
	 * @return
	 * @author limin 
	 * @date 2014-11-18
	 */
	public int deleteOaCustomerPayDetail(@Param("billid")String billid);

    /**
     * 添加客户费用支付单明细
     * @param detail 客户费用支付单明细
     * @return
     * @author limin
     * @date 2014-11-18
     */
    public int insertOaCustomerPayDetail(OaCustomerPayDetail detail);

    /**
     * 查询客户费用支付单明细
     * @param billid
     * @return
     * @author limin
     * @date 2015-04-07
     */
    public List selectCustomerPayDetailInfo(String billid);
}

