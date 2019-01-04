/**
 * @(#)IAccountingService.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 23, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.system.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.system.model.Accounting;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public interface IAccountingService {

	/**
	 * 获取会计年度列表-升序
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 24, 2013
	 */
	public List getAccountingYearsListOrder()throws Exception;
	
	/**
	 * 根据会计年度获取会计期间列表
	 * @param year
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 24, 2013
	 */
	public List getAccountingListByYear(String year)throws Exception;
	
	/**
	 * 新增会计期间
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 24, 2013
	 */
	public boolean addAccounting(List<Accounting> list)throws Exception;
	
	/**
	 * 修改会计期间
	 * @param dateArrStr
     * @param year
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 25, 2013
	 */
	public boolean editAccounting(String dateArrStr,String year)throws Exception;
	
	/**
	 * 删除会计期间
	 * @param year
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 25, 2013
	 */
	public boolean deleteAccouting(String year)throws Exception;

    /**
     * 获取启用状态的会计区间明细
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-04-01
     */
    public Accounting getOpenAccounting()throws Exception;

    /**
     * 根据区间编码获取区间信息
     * @param id
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-04-01
     */
    public Accounting getAccountingInfo(String id)throws Exception;

    /**
     * 根据编码修改会计区间
     * @param id
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-04-01
     */
    public boolean editAccountingInfo(String id)throws Exception;

    /**
     * 关账
     * @param accounting
     * @param nextid
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-04-01
     */
    public Map closeAccounting(Accounting accounting, String nextid)throws Exception;

    /**
     * 会计区间关账（定时器调用）
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-04-01
     */
    public void closeAccountingTask()throws Exception;

    /**
     * 获取修改会计结束日期变动的数据列表
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-04-01
     */
    public List getEditDateList(Map map)throws Exception;
}

