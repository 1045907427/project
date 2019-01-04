/**
 * @(#)AccountingMapper.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 23, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.system.dao;

import java.util.List;

import com.hd.agent.system.model.Accounting;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public interface AccountingMapper {

	public List getAccountingYearsListOrder();

	public List getAccountingListByYear(String year);
	
	public int addAccounting(List<Accounting> list);
	
	public int editAccounting(Accounting accounting);
	
	public int deleteAccouting(String year);

    public Accounting getOpenAccountting();

    /**
     * 根据区间编码获取区间信息
     * @param id
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-04-01
     */
    public Accounting getAccountingInfo(@Param("id")String id);
}

