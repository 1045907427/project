package com.hd.agent.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.account.model.BeginAmount;
import com.hd.agent.common.util.PageMap;

/**
 * 客户应收款期初dao
 * @author chenwei
 */
public interface BeginAmountMapper {
	/**
	 * 添加客户应收款期初
	 * @param beginAmount
	 * @return
	 * @author chenwei 
	 * @date 2014年10月31日
	 */
	public int addBeignAmount(BeginAmount beginAmount);
	/**
	 * 获取客户应收款期初列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年10月31日
	 */
	public List showBeignAmountList(PageMap pageMap);
	/**
	 * 获取客户应收款期初数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年10月31日
	 */
	public int showBeignAmountCount(PageMap pageMap);
	/**
	 * 获取客户应收款期初合计
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年10月31日
	 */
	public BeginAmount showBeignAmountSum(PageMap pageMap);
	/**
	 * 获取客户应收款期初数据
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2014年10月31日
	 */
	public BeginAmount getBeginAmountByID(@Param("id")String id);
	/**
	 * 客户应收款期初修改
	 * @param beginAmount
	 * @return
	 * @author chenwei 
	 * @date 2014年10月31日
	 */
	public int editBeginAmount(BeginAmount beginAmount);
	/**
	 * 删除客户应收款期初
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2014年11月1日
	 */
	public int deleteBeginAmount(@Param("id")String id);
	/**
	 * 审核客户应收款期初
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date 2014年11月1日
	 */
	public int auditBeignAmount(@Param("id")String id,@Param("userid")String userid,@Param("username")String username);
	/**
	 * 反审客户应收款期初
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2014年11月1日
	 */
	public int oppauditBeignAmount(@Param("id")String id);
	/**
	 * 更新应收款期初开票状态
	 * @param id
	 * @param invoicedate
	 * @return
	 * @author chenwei 
	 * @date 2014年11月4日
	 */
	public int updateBeginAmountInvoice(@Param("id")String id,@Param("invoicedate")String invoicedate,@Param("isinvoice")String isinvoice);
	/**
	 * 更新应收款期初核销状态
	 * @param id
	 * @param writeoffdate
	 * @param iswriteoff
	 * @return
	 * @author chenwei 
	 * @date 2014年11月4日
	 */
	public int updateBeginAmountWriteoff(@Param("id")String id,@Param("writeoffdate")String writeoffdate,@Param("iswriteoff")String iswriteoff,@Param("userid")String userid,@Param("username")String username);
}