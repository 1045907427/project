package com.hd.agent.delivery.dao;


import com.hd.agent.common.util.PageMap;
import com.hd.agent.delivery.model.DeliveryAogorder;
import com.hd.agent.delivery.model.DeliveryAogorderDetail;
import com.hd.agent.delivery.model.DeliveryAogreturn;
import com.hd.agent.delivery.model.DeliveryAogreturnDetail;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DeliveryAogreturnMapper {

	/**
	 * 获取退货订单数量
	 * @param pageMap
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	int getDeliveryAogreturnListCount(PageMap pageMap);
	/**
	 * 获取退货订单列表
	 * @param pageMap
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	List getDeliveryAogreturnList(PageMap pageMap);
	/**
	 * 添加退货订单明细
	 * @param deliveryAogreturnDetail
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int addDeliveryAogreturnDetail(DeliveryAogreturnDetail deliveryAogreturnDetail);
	/**
	 * 添加退货订单
	 * @param deliveryAogreturn
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int addDeliveryAogreturn(DeliveryAogreturn deliveryAogreturn);
	/**
	 * 审核到货订单
	 * @param id userid username date
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int auditDeliveryAogreturn(@Param("id")String id,@Param("userid")String userid,@Param("username")String username,@Param("date")Date date,@Param("businessdate")String businessdate);
	/**
	 * 通过ID获取退货订单
	 * @param id
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public DeliveryAogreturn getDeliveryAogreturnByID(@Param("id")String id);
	/**
	 * 通过ID获取退货订单详情
	 * @param billid
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public List<DeliveryAogreturnDetail> getDeliveryAogreturnDetailList(@Param("billid")String billid);
	/**
	 * 反审退货订单
	 * @param id userid username date
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int oppauditDeliveryAogreturn(@Param("id")String id,@Param("userid")String userid,@Param("username")String username,@Param("date")Date date);
	/**
	 * 删除退货订单
	 * @param id
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int deleteDeliveryAogreturn(@Param("id")String id);
	/**
	 * 删除退货订单详情
	 * @param billid
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int deleteDeliveryAogreturnDetail(@Param("billid")String billid);
	/**
	 * 保存修改的退货订单
	 * @param deliveryAogreturn
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int editDeliveryAogreturn(DeliveryAogreturn deliveryAogreturn);
	/**
	 * 出库单审核通过关闭退货订单
	 * @param id date
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int closeDeliveryAogreturn(@Param("id")String id,@Param("date")Date date);
	/**
	 * 获取退货订单导出列表
	 * @param pageMap
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public List getAogreturnList(PageMap pageMap);
	/**
	 * 获取退货订单列表<br/>
	 * map中参数:<br/>
	 * dataSql：数据权限<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * status: 表示状态3<br/>
	 * statusarr: 表示状态，类似 1,2,3<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-10-16
	 */
	public List showAogreturnListBy(Map map);
	/**
	 * 更新打印次数
	 * @param deliveryAogreturn
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int updateAogreturnPrinttimes(DeliveryAogreturn deliveryAogreturn);
	/**
	 * 反审出库单后改变退货订单状态为审核
	 * @param id date
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int oppDeliveryOut(@Param("id")String id);
}