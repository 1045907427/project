/**
 * @(#)ICustomerCostPayableService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年11月12日 chenwei 创建版本
 */
package com.hd.agent.journalsheet.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.journalsheet.model.CustomerCostPayable;

/**
 * 
 * 客户应付费用service接口
 * @author chenwei
 */
public interface ICustomerCostPayableService {
	/**
	 * 获取客户应付费用合计数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月12日
	 */
	public PageData showCustomerCostPayableListData(PageMap pageMap) throws Exception;
	/**
	 * 获取客户应付费用明细列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月12日
	 */
	public PageData showCustomerCostPayableDetailList(PageMap pageMap) throws Exception;
	/**
	 * 添加客户应付费用期初
	 * @param customerCostPayable
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月20日
	 */
	public boolean addCustomerCostPayableInit(CustomerCostPayable customerCostPayable) throws Exception;
	/**
	 * 获取客户应付费用期初列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月20日
	 */
	public PageData showCustomerCostPayableInitList(PageMap pageMap) throws Exception;
	/**
	 * 根据编号获取客户应付费用信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月20日
	 */
	public CustomerCostPayable getCustomerCostPayableByID(String id) throws Exception;
	/**
	 * 修改客户应付费用期初
	 * @param customerCostPayable
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月20日
	 */
	public boolean editCustomerCostPayableInit(CustomerCostPayable customerCostPayable) throws Exception;
	/**
	 * 删除客户应付费用期初
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月20日
	 */
	public boolean deleteCustomerCostPayableInit(String id) throws Exception;
	/**
	 * 客户应付费用期初导入
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月20日
	 */
	public Map addCustomerCostPayableInitList(List<Map> list) throws Exception;
	
	/**
	 * 添加客户应付费用
	 * @param customerCostPayable
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月4日
	 */
	public boolean addCustomerCostPayable(CustomerCostPayable customerCostPayable) throws Exception;

	/**
	 * 添加客户应付费用
	 * @param customerCostPayable
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月4日
	 */
	public Map editCustomerCostPayable(CustomerCostPayable customerCostPayable) throws Exception;
	/**
	 * 新增代垫红冲
	 * @param customerCostPayable
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public Map addCustomerCostPayableHC(CustomerCostPayable customerCostPayable)throws Exception;

	/**
	 * 撤销代垫红冲
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public Map removeCustomerCostPayableHC(String id)throws Exception;
	/**
	 * 删除客户应付费用
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月4日
	 */
	public boolean deleteCustomerCostPayable(String id) throws Exception;
	/**
	 * 批量删除客户应付费用
	 * @param idarrs
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月4日
	 */
	public Map deleteCustomerCostPayableMore(String idarrs) throws Exception;

	/**
	 * 新增导入功能
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Map addDRCustomerCostPayable(List<Map> list) throws Exception;

	/**
	 * 获取客户费用单据生成凭证的数据
	 * @param idList
	 * @return java.util.List<java.util.Map>
	 * @throws
	 * @author luoqiang
	 * @date Dec 20, 2017
	 */
	public List<Map> getCustomerCostPayableSumData(List idList);
}

