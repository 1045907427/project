/**
 * @(#)SupplierCapitalMapper.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Nov 12, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.account.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.account.model.SupplierCapital;
import com.hd.agent.account.model.SupplierCapitalLog;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public interface SupplierCapitalMapper {

	/**
	 * 新增供应商资金情况添加
	 * @param supplierCapital
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 12, 2013
	 */
	public int addSupplierCapital(SupplierCapital supplierCapital);
	
	/**
	 * 修改供应商资金情况添加
	 * @param supplierCapital
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 12, 2013
	 */
	public int updateSupplierCapital(SupplierCapital supplierCapital);
	
	/**
	 * 获取供应商资金情况
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 12, 2013
	 */
	public SupplierCapital getSupplierCapital(@Param("id")String id);
	
	/**
	 * 获取供应商资金情况列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 12, 2013
	 */
	public List showSupplierAccountList(PageMap pageMap);
	
	/**
	 * 获取供应商资金情况数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 12, 2013
	 */
	public int showSupplierAccountCount(PageMap pageMap);
	
	/**
	 * 更新供应商资金余额
	 * @param supplierid
	 * @param amount
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 12, 2013
	 */
	public int updateSupplierCapitalAmount(@Param("supplierid")String supplierid,@Param("amount")BigDecimal amount);
	
	/**
	 * 添加供应商资金流水
	 * @param supplierCapitalLog
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 12, 2013
	 */
	public int addSupplierCapitalLog(SupplierCapitalLog supplierCapitalLog);
	
	/**
	 * 删除供应商资金流水
	 * @param supplierid
	 * @param billid
	 * @param billtype
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 12, 2013
	 */
	public int deleteSupplierCapitalLog(@Param("supplierid")String supplierid,@Param("billid")String billid,@Param("billtype")String billtype);

    /**
     * 根据参数删除供应商资金流水
     * @param map
     * @return
     * @author panxiaoxiao
     * @date 2015-03-24
     */
    public int deleteSupplierCapitalLogByMap(Map map);
	
	/**
	 * 获取供应商资金流水列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 12, 2013
	 */
	public List showSupplierCapitalLogList(PageMap pageMap);
	
	/**
	 * 获取供应商资金流水数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 12, 2013
	 */
	public int showSupplierCapitalLogCount(PageMap pageMap);
	
	/**
	 * 合计供应商余额
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 12, 2014
	 */
	public BigDecimal showSupplierAccountSum(PageMap pageMap);
}

