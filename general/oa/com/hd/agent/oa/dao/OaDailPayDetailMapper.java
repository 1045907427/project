/**
 * @(#)OaDailyPayDetailMapper.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-11-27 limin 创建版本
 */
package com.hd.agent.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.model.OaDailyPayDetail;

/**
 * 日常费用支付申请单明细Mapper
 * 
 * @author limin
 */
public interface OaDailPayDetailMapper {

	/**
	 * 查询日常费用支付申请单明细
	 * @param id
	 * @return
	 * @author limin 
	 * @date 2014-11-27
	 */
	public OaDailyPayDetail selectOaDailPayDetail(@Param("id") String id);

	/**
	 * 删除日常费用支付申请单明细
	 * @param billid
	 * @return
	 * @author limin 
	 * @date 2014-11-27
	 */
	public int deleteOaDailPayDetail(@Param("billid") String billid);

	/**
	 * 添加日常费用支付申请单明细
	 * @param id
	 * @return
	 * @author limin 
	 * @date 2014-11-27
	 */
	public int insertOaDailPayDetail(OaDailyPayDetail detail);

	/**
	 * 更新日常费用支付申请单明细
	 * @param id
	 * @return
	 * @author limin 
	 * @date 2014-11-27
	 */
	public int updateOaDailPayDetail(OaDailyPayDetail detail);

	/**
	 * 查询日常费用支付申请单明细
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2014-11-29
	 */
	public List selectOaDailPayDetailList(PageMap map);

	/**
	 * 查询日常费用支付申请单明细件数
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2014-11-29
	 */
	public int selectOaDailPayDetailListCnt(PageMap map);
	
	/**
	 * 查询日常费用支付申请单明细List
	 * @param billid
	 * @return
	 * @author limin 
	 * @date 2014-11-29
	 */
	public List selectOaDailPayDetailListByBillid(@Param("billid") String billid);
}

