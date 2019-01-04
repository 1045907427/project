/**
 * @(#)IPayorderService.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 16, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.account.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.account.model.Payorder;
import com.hd.agent.account.model.SupplierCapital;
import com.hd.agent.account.model.TransferOrder;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 付款单service
 * @author panxiaoxiao
 */
public interface IPayorderService {

	/**
	 * 新增付款单
	 * @param payorder
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	public boolean addPayorder(Payorder payorder)throws Exception;
	
	/**
	 * 获取付款单详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	public Payorder getPayorderInfo(String id)throws Exception;
	
	/**
	 * 获取付款单列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	public PageData showPayorderList(PageMap pageMap)throws Exception;
	
	/**
	 * 删除付款单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	public boolean deletePayorder(String id)throws Exception;
	
	/**
	 * 修改付款单
	 * @param payorder
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	public boolean editPayorder(Payorder payorder)throws Exception;
	
	/**
	 * 审核付款单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	public boolean auditPayorder(String id)throws Exception;
	
	/**
	 * 反审付款单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	public Map oppauditPayorder(String id)throws Exception;
	
	/**
	 * 根据付款单编码获取付款单列表
	 * @param ids
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 17, 2013
	 */
	public List getPayorderListByIds(String ids)throws Exception;
	
	/**
	 * 付款单合并
	 * @param ids
	 * @param orderid
	 * @param remark
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 17, 2013
	 */
	public boolean setPayorderMerge(String ids,String orderid,String remark)throws Exception;
	/**
	 * 付款单提交工作流
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 26, 2013
	 */
	public boolean submitPayorderPageProcess(String id) throws Exception;
	
	/**
	 * 获取供应商资金表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 12, 2013
	 */
	public PageData showSupplierAccountList(PageMap pageMap)throws Exception;
	
	/**
	 * 获取供应商资金流水表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 12, 2013
	 */
	public PageData showSupplierCapitalLogList(PageMap pageMap)throws Exception;
	
	/**
	 * 根据供应商编号获取 供应商余额
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 8, 2013
	 */
	public SupplierCapital getSupplierCapitalBySupplierid(String supplierid) throws Exception;
	
	/**
	 * 付款单转账确认
	 * @param transferOrder
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 13, 2013
	 */
	public boolean setPayOrderTransfer(TransferOrder transferOrder)throws Exception;
	/**
	 * 添加并且审核付款单
	 * @param payorder
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月24日
	 */
	public boolean addAndAuditPayOrder(Payorder payorder) throws Exception;
	
	/**
	 * 根据OA号查询付款单
	 * @param oaid
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-10
	 */
	public List<Payorder> selectPayOrderByOaid(String oaid) throws Exception;
	
	/**
	 * 付款单回滚（for oa）
	 * @param payorder
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-12
	 */
	public boolean rollbackAndAuditPayOrder(Payorder payorder) throws Exception;
	
	/**
	 * 付款单回滚（for oa）
	 * @param id
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-12
	 */
	public boolean rollbackPayorder(String id) throws Exception;

    /**
     * 根据付款单编号 获取供应商的汇总金额
     * @param  idarr
     * @return
     * @author lin_xx
     * @date 2016年7月7日
     */
    public List getSupplierPaySumData(List idarr) throws Exception;

	/**
	 * 更新凭证生成次数
	 * @throws
	 * @author lin_xx
	 * @date 2017-11-30
	 */
	public boolean updatePayorderVouchertimes(Payorder payorder) throws Exception;

}

