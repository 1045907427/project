/**
 * @(#)IDispatchBillService.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 18, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.DispatchBill;

import java.util.List;
import java.util.Map;

/**
 * 
 * 销售发货通知单service
 * @author zhengziyong
 */
public interface IDispatchBillService {

	/**
	 * 添加销售发货通知单
	 * @param bill
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 18, 2013
	 */
	public boolean addDispatchBill(DispatchBill bill) throws Exception;
	
	/**
	 * 更新销售发货通知单信息
	 * @param bill
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 18, 2013
	 */
	public boolean updateDispatchBill(DispatchBill bill) throws Exception;
	
	/**
	 * 获取销售发货通知单详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 18, 2013
	 */
	public DispatchBill getDispatchBill(String id) throws Exception; 
	
	/**
	 * 通过订单编号获取发货通知单信息，前提是发货通知单参照了该订单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 28, 2013
	 */
	public DispatchBill getDispatchBillByOrder(String id) throws Exception;
	
	/**
	 * 获取销售发货通知单列表信息
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 18, 2013
	 */
	public PageData getDispatchBillData(PageMap pageMap) throws Exception;
	
	/**
	 * 删除销售发货通知单信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 21, 2013
	 */
	public boolean deleteDispatchBill(String id) throws Exception;
	
	/**
	 * 审核或反审销售发货通知单
	 * @param type 1为审核2为反审
	 * @param id 发货通知单编码
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 22, 2013
	 */
	public Map auditDispatchBill(String type, String id) throws Exception;
	/**
	 * 销售发货通知单超级审核
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月27日
	 */
	public Map auditSupperDispatchBill(String id) throws Exception;
	
	/**
	 * 通过销售发货通知单获取明细列表
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 7, 2013
	 */
	public List getDetailListByBill(String id) throws Exception;
	
	/**
	 * 提交销售发货通知单进流程
	 * @param title
	 * @param userId
	 * @param processDefinitionKey
	 * @param businessKey
	 * @param variables
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jul 2, 2013
	 */
	public Map submitDispatchBillProcess(String title, String userId, String processDefinitionKey, String businessKey, Map<String, Object> variables) throws Exception;
	/**
	 * 判断库存数量是否足够 是否需要配置库存
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 10, 2013
	 */
	public Map dispatchBillDeploy(String id) throws Exception;
	/**
	 * 显示销售发货通知单库存配置信息
	 * @param id
	 * @param barcodeflag 		是否用相同条形码商品替换 1是
     * @param deploy              商品追加或者替换的商品和仓库列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 10, 2013
	 */
	public DispatchBill getDispatchBillDeploy(String id,String barcodeflag,String deploy) throws Exception;
	
	/**
	 * 更新配货打印次数
	 * @param dispatchBill
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-4
	 */
	public boolean updateOrderPhPrinttimes(DispatchBill dispatchBill) throws Exception;
	
	/**
	 * 批量更新配货打印次数
	 * @param list
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-4
	 */
	public boolean updateOrderPhPrinttimes(List<DispatchBill> list) throws Exception;
	/**
	 * 更新发货打印次数
	 * @param dispatchBill
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-4
	 */
	public boolean updateOrderFhPrinttimes(DispatchBill dispatchBill) throws Exception;
	
	/**
	 * 批量更新发货打印次数
	 * @param list
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-4
	 */
	public boolean updateOrderFhPrinttimes(List<DispatchBill> list) throws Exception;
	/**
	 * 可以打印次数<br/>
	 * map中参数：<br/>
	 * bhprint : 打印配货<br/>
	 * bhprint 不为null,printlimit 打印限制
	 * fhprint : 打印发货<br/>
	 * fhprint 不为null,printlimit 打印限制
	 * dispatchid : 通知单编号
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-12-30
	 */
	public int getCanPrinttimes(Map map) throws Exception;
	/**
	 * 获取销售发货通知单列表<br/>
	 * map中参数<br/>
	 * dataSql : 权限<br/>
	 * idarrs :编号字符串组<br/>
	 * status : 状态<br/>
	 * statusarr : 状态字符串组<br/>
	 * billnoarr : 销售订单编号字符串组<br/>
	 * notphprint : 配货未打印<br/>
	 * notprint : 发货未打印<br/>
	 * @param map
	 * @return
	 * @author chenwei 
	 * @date Jun 5, 2013
	 */
	public List getDispatchBillBy(Map map) throws Exception;
	

	/**
	 * 存储过程 调用 根据仓库发货单发货打印次数同步发货打印次数
	 * 
	 * @author zhanghonghui 
	 * @date 2014-1-14
	 */
	public void updateBillSyncPrinttimesProc() throws Exception;
	/**
	 * 存储过程 调用 根据仓库发货单配货打印次数同步配货打印次数
	 * 
	 * @author zhanghonghui 
	 * @date 2014-1-14
	 */
	public void updateBillSyncPhprinttimesProc() throws Exception;
	/**
	 * 根据单据编号合计单据明细金额
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年10月20日
	 */
	public Map getDispatchBillDetailTotal(String id) throws Exception;
	/**
     * 客户应收款、余额<br/>
     * 返回结果<br/>
     * receivableAmount:客户应收款<br/>
     * leftAmount : 余额<br/>
     * @param customerid
     * @return
     * @author zhanghonghui
     * @date 2015年11月12日
     */
    public Map showCustomerReceivableInfoData(String customerid) throws Exception;
    /**
     * 获取客户单据号
     * @param saleorderid
     * @return
     * @throws Exception
     * @author zhanghonghui 
     * @date 2015年12月28日
     */
    public String getCustomerBillId(String saleorderid) throws Exception;

	/**
	 * 根据编号获取打印次数相关信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public DispatchBill getDispatchbillPrinttimesById(String id) throws Exception;
}

