package com.hd.agent.delivery.dao;


import com.hd.agent.common.util.PageMap;
import com.hd.agent.delivery.model.DeliveryAogorder;
import com.hd.agent.delivery.model.DeliveryAogorderDetail;
import com.hd.agent.purchase.model.BuyOrder;
import com.hd.agent.storage.model.AllocateOut;
import com.hd.agent.storage.model.AllocateOutDetail;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DeliveryAogorderMapper {
	/**
	 * 获取到货订单数量
	 * @param pageMap
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	int getDeliveryAogorderListCount(PageMap pageMap);
	/**
	 * 获取到货订单列表
	 * @param pageMap
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	List getDeliveryAogorderList(PageMap pageMap);
	/**
	 * 添加到货订单明细
	 * @param deliveryAogorderDetail
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int addDeliveryAogorderDetail(DeliveryAogorderDetail deliveryAogorderDetail);
	/**
	 * 添加到货订单
	 * @param deliveryAogorder
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int addDeliveryAogorder(DeliveryAogorder deliveryAogorder);
	/**
	 * 通过ID获取到货订单
	 * @param id
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public DeliveryAogorder getDeliveryAogorderByID(@Param("id")String id);
	/**
	 * 通过ID获取到货订单详情
	 * @param billid
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public List<DeliveryAogorderDetail> getDeliveryAogorderDetailList(@Param("billid")String billid);
	/**
	 * 删除到货订单
	 * @param id
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int deleteDeliveryAogorder(@Param("id")String id);
	/**
	 * 删除到货订单详情
	 * @param billid
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int deleteDeliveryAogorderDetail(@Param("billid")String billid);
	/**
	 * 保存修改的到货订单
	 * @param deliveryAogorder
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int editDeliveryAogorder(DeliveryAogorder deliveryAogorder);
	/**
	 * 审核到货订单
	 * @param id userid username date
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int auditDeliveryAogorder(@Param("id")String id,@Param("userid")String userid,@Param("username")String username,@Param("date")Date date,@Param("businessdate")String businessdate);
	/**
	 * 反审到货订单
	 * @param id userid username date
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int oppauditDeliveryAogorder(@Param("id")String id,@Param("userid")String userid,@Param("username")String username,@Param("date")Date date);
	/**
	 * 入库单审核通过关闭到货订单
	 * @param id date
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int closeDeliveryAogorder(@Param("id")String id,@Param("date")Date date);
	/**
	 * 获取到货订单导出列表
	 * @param pageMap
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public List getAogorderList(PageMap pageMap);
	/**
	 * 获取到货订单列表<br/>
	 * map中参数:<br/>
	 * dataSql：数据权限<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * status: 表示状态3<br/>
	 * statusarr: 表示状态，类似 1,2,3<br/>
	 * @param map
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public List showAogorderListBy(Map map);
	/**
	 * 更新打印次数
	 * @param buyOrder
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int updateAogorderPrinttimes(DeliveryAogorder deliveryAogorder);
	/**
	 * 反审入库单后改变入库订单状态为审核
	 * @param id date
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int oppDeliveryEnter(@Param("id")String id);
}