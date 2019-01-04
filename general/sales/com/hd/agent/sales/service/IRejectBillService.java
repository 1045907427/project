/**
 * @(#)IRejectBillService.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 5, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.ModelOrder;
import com.hd.agent.sales.model.RejectBill;
import com.hd.agent.sales.model.RejectBillDetail;

import java.util.List;
import java.util.Map;

/**
 * 
 * 销售发货通知单service
 * @author zhengziyong
 */
public interface IRejectBillService {

	/**
	 * 添加销售退货通知单信息
	 * @param bill
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 5, 2013
	 */
	public boolean addRejectBill(RejectBill bill) throws Exception;
	
	/**
	 * 添加手机销售退货通知单
	 * @param bill
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 2, 2014
	 */
	public boolean addRejectBillForPhone(RejectBill bill) throws Exception;
	/**
	 * 添加车销销售退货通知单
	 * @param bill			退货单据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 2, 2014
	 */
	public boolean addRejectBillForCar(RejectBill bill) throws Exception;
	/**
	 * 修改销售退货通知单信息
	 * @param bill
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 5, 2013
	 */
	public boolean updateRejectBill(RejectBill bill) throws Exception;
	
	/**
	 * 删除销售退货通知单信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 5, 2013
	 */
	public boolean deleteRejectBill(String id) throws Exception;
	
	/**
	 * 获取销售退货通知单列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 5, 2013
	 */
	public PageData getRejectBillData(PageMap pageMap) throws Exception;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 5, 2013
	 */
	public RejectBill getRejectBill(String id) throws Exception;
	 /**
	  * 将导入的模板数据转换为商品明细
	  * @author lin_xx
	  * @date 2016/12/6
	  */
	public Map changeModelForDetail(List<ModelOrder> wareList, String gtype) throws Exception;
	
	/**
	 * 审核或反审退货通知单
	 * @param type 1为审核2为反审
	 * @param id 通知单编号
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 6, 2013
	 */
	public Map auditRejectBill(String type, String id,String storager) throws Exception;
	
	/**
	 * 检验退货通知单审核前是否买过该商品
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Apr 7, 2014
	 */
	public Map checkBuyGoodsRejectBill(String id)throws Exception;
	/**
	 * 判断退货通知单是否重复
	 * 根据制单时间判断 相同日期下 是否存在相同的退货金额
	 * @param id
	 * @return			true 不重复 false 重复
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 29, 2014
	 */
	public boolean checkRejectRepeat(String id) throws Exception;
	/**
	 * 判断退货通知单仓库是否为空
	 * 先判断订单仓库是否为空，如果订单仓库为空，再判断商品的默认仓库是否为空
	 * @param id
	 * @return			true 不重复 false 重复
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 29, 2014
	 */
	public Map checkRejectStorage(String id) throws Exception;
	
	/**
	 * 提交退货通知单进流程
	 * @param title
	 * @param userId
	 * @param processDefinitionKey
	 * @param businessKey
	 * @param variables
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jul 3, 2013
	 */
	public boolean submitRejectBillProcess(String title, String userId, String processDefinitionKey, String businessKey, Map<String, Object> variables) throws Exception;
	/**
	 * 根据通知单编号查询明细
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-9-30
	 */
	public List<RejectBillDetail> getRejectBillDetailListByBill(String id) throws Exception;

	
	/**
	 * 更新打印次数
	 * @param rejectBill
	 * @author zhanghonghui 
	 * @date 2013-9-10
	 */
	public boolean updateOrderPrinttimes(RejectBill rejectBill) throws Exception;
	/**
	 * 更新打印次数
	 * @param list
	 * @author zhanghonghui 
	 * @date 2013-9-10
	 */
	public void updateOrderPrinttimes(List<RejectBill> list) throws Exception;
	/**
	 * 获取退货通知单列表
	 * map中参数：<br/>
	 * idarrs：编号字符串组，类似1,2,3<br/>
	 * status: 状态<br/>
	 * @param map
	 * @return
	 */
	public List getRejectBillListBy(Map map) throws Exception;
	/**
	 * 根据编号 获取直退的销售退货通知单 未与回单关联的明细列表
	 * @param ids
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 28, 2013
	 */
	public List showDirectRejectBillDetailByIds(String ids) throws Exception;
	/**
	 * 拆分退货通知单 并同时拆分下游退货入库单
	 * @param rejectid
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 14, 2014
	 */
	public Map updateRejectBillSplit(String rejectid,List<RejectBillDetail> list) throws Exception;
	/**
	 * 保存并验收销售退货通知单
	 * @param rejectBill
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 15, 2014
	 */
	public Map updateRejectBillCheck(RejectBill rejectBill) throws Exception;
	/**
	 * 取消验收销售退货通知单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 18, 2014
	 */
	public Map updateRejectBillCheckCancel(String id) throws Exception;
	/**
	 * 验收销售退货通知单
	 * @param ids
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月21日
	 */
	public Map auditCheckRejectBill(String ids) throws  Exception;
	/**
	 * 审核手机上传的销售退货通知单（暂存变为保存）
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 16, 2014
	 */
	public boolean auditRejectBillPhone(String id) throws Exception;
	/**
	 * 审核手机上传的车销销售退货通知单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年5月13日
	 */
	public boolean auditRejectBillCar(String id) throws Exception;
	/**
	 * 根据手机keyid 判断是否上传过该单据
	 * @param keyid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月18日
	 */
	public boolean isHaveRejectBillByKeyid(String keyid) throws Exception;
    /**
     * 销售退货通知单导出
     * @return
     * @throws Exception
     * @author lin_xx
     * @date June 12, 2015
     */
    public List<Map<String,Object>> getRejectBillByExport(PageMap pageMap) throws  Exception;

    /**
     * 根据客户编码、商品编码显示历史价格表数据页面
     * @return
     * @throws Exception
     * @author pan_xx
     * @date 2015年6月16日
     */
    public List<Map> getRejectBillHistoryGoodsPriceList(Map map)throws  Exception;
    /**
	 * 只获取销售退货通知信息，不查询单据中的其他信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 5, 2013
	 */
	public RejectBill getPureRejectBill(String id) throws Exception;

	/**
	 * 导入销售退货通知单
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2017-01-05
	 */
	public Map importRejectBill(List<Map<String,Object>> list)throws Exception;

	/**
	 * 根据Map中参数获取销售退货通知单明细列表<br/>
	 * billid:单据编号<br/>
	 * billidarrs:单据编号组<br/>
	 * goodsid:商品编号<br/>
	 * taxtype:税种<br/>
	 * @param map
	 * @return java.util.List<com.hd.agent.sales.model.ReceiptDetail>
	 * @throws
	 * @author zhanghonghui
	 * @date May 10, 2017
	 */
	public List<RejectBillDetail> getRejectBillDetailListByMap(Map map) throws Exception;
	/**
	 * 根据编号获取销售退货通知单信息<br/>
	 * @param id
	 * @return java.util.List<com.hd.agent.sales.model.ReceiptDetail>
	 * @throws
	 * @author zhanghonghui
	 * @date May 10, 2017
	 */
	public RejectBill getRejectBillInfoById(String id) throws Exception;
}

