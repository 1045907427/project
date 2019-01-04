package com.hd.agent.sales.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.DispatchBill;
import com.hd.agent.sales.model.DispatchBillDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DispatchBillMapper {
    
	/**
	 * 
	 */
	public DispatchBill getDispatchBill(String id);
	
	/**
	 * 
	 */
	public DispatchBill getDispatchBillByOrder(String id);
	
	/**
	 * 
	 */
	public List getDispatchBillList(PageMap pageMap);
	
	/**
	 * 
	 */
	public int getDispatchBillCount(PageMap pageMap);
	
	/**
	 * 
	 */
	public int deleteDispatchBill(String id);
	
	/**
	 * 
	 */
	public int addDispatchBill(DispatchBill bill);
	
	/**
	 * 
	 */
	public int updateDispatchBill(DispatchBill bill);
	
	/**
	 * 更新状态
	 */
	public int updateDispatchBillStatus(DispatchBill bill);
	
	/**
	 * 更新参照状态
	 * @param isrefer
	 * @param id
	 * @return
	 * @author zhengziyong 
	 * @date May 28, 2013
	 */
	public int updateDispatchBillRefer(String isrefer, String id);
	
	/**
	 * 添加发货单商品明细
	 * @param billDetail
	 * @return
	 * @author zhengziyong 
	 * @date May 21, 2013
	 */
	public int addDispatchBillDetail(DispatchBillDetail billDetail);
	/**
	 * 添加发货通知单明细列表
	 * @param list
	 * @return
	 * @author chenwei 
	 * @date Jan 2, 2014
	 */
	public int addDispatchBillDetailList(@Param("list")List list);
	/**
	 * 通过发货单编号获取商品明细列表
	 * @param id 发货通知单编码
	 * @return
	 * @author zhengziyong 
	 * @date May 21, 2013
	 */
	public List getDispatchBillDetailListByBill(String id);
	/**
	 * 通过发货单编号获取商品明细列表(合计品牌折扣)
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Dec 17, 2013
	 */
	public List getDispatchBillDetailSumDiscountListByBill(String id);
	/**
	 * 根据发货单编号和品牌编号 获取明细列表
	 * @param billid
	 * @param brandid
	 * @return
	 * @author chenwei 
	 * @date Dec 17, 2013
	 */
	public List getDispatchBillDetailListByBillAndBrandid(@Param("billid")String billid,@Param("brandid")String brandid);
	/**
     * 获取发货通知单明细合计数量
     * @param id
     * @return
     * @author chenwei
     * @date Dec 12, 2013
     */
    public List getDispatchBillDetailSumListById(String id);

    /**
     * 获取发货通知单明细合计数量
     * @param id
     * @return
     * @author chenwei
     * @date Dec 12, 2013
     */
    public List getDispatchBillDetailSumListByIdWithOutDiscount(String id);
	/**
	 * 计算发货通知单商品明细总金额
	 * @param id
	 * @return
	 * @author zhengziyong 
	 * @date May 21, 2013
	 */
	public Map getDispatchBillDetailTotal(String id);
	
	/**
	 * 通过发货通知单编号删除商品明细信息
	 * @param id
	 * @return
	 * @author zhengziyong 
	 * @date May 21, 2013
	 */
	public int deleteDispatchBillDetailByBill(String id);
	
	/**
	 * 回写
	 * @param detail
	 * @return
	 * @author zhengziyong 
	 * @date Jun 6, 2013
	 */
	public int updateDispatchBillDetailBack(DispatchBillDetail detail);
	
	/**
	 * 获取发货通知单明细信息
	 * @param id
	 * @return
	 * @author zhengziyong 
	 * @date Jun 6, 2013
	 */
	public DispatchBillDetail getDispatchBillDetail(String id);
	/**
	 * 获取发货通知单品牌折扣明细列表
	 * @param billid
	 * @param brandid
	 * @return
	 * @author chenwei 
	 * @date Dec 19, 2013
	 */
	public List getDispatchBillDetailBrandDiscountList(@Param("billid")String billid,@Param("brandid")String brandid);
	/**
	 * 更新打印次数
	 * @param dispatchBill
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-4
	 */
	public int updateOrderPrinttimes(DispatchBill dispatchBill);
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
	public int getCanPrinttimes(Map map);
	
	/**
	 * 获取销售发货通知单列表<br/>
	 * map中参数<br/>
	 * dataSql : 权限<br/>
	 * idarrs :编号字符串组<br/>
	 * status : 状态<br/>
	 * statusarr : 状态字符串组<br/>
	 * billnoarr : 销售订单编号字符串组
	 * @param map
	 * @return
	 * @author chenwei 
	 * @date Jun 5, 2013
	 */
	public List getDispatchBillBy(Map map);
	

	/**
	 * 存储过程 调用 根据仓库发货单发货打印次数同步发货打印次数
	 * 
	 * @author zhanghonghui 
	 * @date 2014-1-14
	 */
	public void updateBillSyncPrinttimesProc();
	/**
	 * 存储过程 调用 根据仓库发货单配货打印次数同步配货打印次数
	 * 
	 * @author zhanghonghui 
	 * @date 2014-1-14
	 */
	public void updateBillSyncPhprinttimesProc();
	
	/**
	 * 根据销售订单编号 删除发货通知单
	 * @param orderid
	 * @return
	 * @author chenwei 
	 * @date Apr 17, 2014
	 */
	public int deleteDispatchbillByOrderid(@Param("orderid")String orderid);
	/**
	 * 根据销售订单编号 删除发货通知单明细
	 * @param orderid
	 * @return
	 * @author chenwei 
	 * @date Apr 17, 2014
	 */
	public int deleteDispatchbillDetailByOrderid(@Param("orderid")String orderid);

	/**
	 * 根据编号获取打印次数相关信息
	 * @param id
	 * @return
	 */
	public DispatchBill getDispatchbillPrinttimesById(@Param("id")String id);

	/**
	 * 获取审核或关闭状态的销售发货通知单的商品编号及日期
	 * @return
	 * @author panxiaoxiao
	 * @date 2016-11-21
	 */
	public List<Map> getDispatchBillGoodsidBusinessdate();

	/**
	 * 获取审核或关闭状态的销售发货通知单的客户及日期
	 * @return
	 * @author panxiaoxiao
	 * @date 2016-11-21
	 */
	public List<Map> getDispatchBillCustomeridBusinessdate();
}