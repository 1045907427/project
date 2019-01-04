package com.hd.agent.account.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.account.model.CollectionOrder;
import com.hd.agent.account.model.CollectionOrderAssign;
import com.hd.agent.common.util.PageMap;

/**
 * 收款单dao
 * @author chenwei
 */
public interface CollectionOrderMapper {
	/**
	 * 收款单添加
	 * @param collectionOrder
	 * @return
	 * @author chenwei 
	 * @date Jul 6, 2013
	 */
	public int addCollectionOrder(CollectionOrder collectionOrder);
	/**
	 * 获取收款单详细信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jul 6, 2013
	 */
	public CollectionOrder getCollectionOrderInfo(@Param("id")String id);
	/**
	 * 根据客户编号获取未核销完的收款单列表
	 * @param customerid
	 * @return
	 * @author chenwei 
	 * @date Nov 30, 2013
	 */
	public List getCollectionOrderListByCustomerid(@Param("customerid")String customerid);
	/**
	 * 获取收款单列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 6, 2013
	 */
	public List showCollectionOrderList(PageMap pageMap);
	/**
	 * 获取收款单数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 6, 2013
	 */
	public int showCollectionOrderCount(PageMap pageMap);
    /**
     * 根据交款单编号获取对应的收款单
     * @return
     * @author lin_xx
     * @date Augest 1, 2016
     */
    public List<CollectionOrder> getCollectionBySourceno(String sourceno);
	/**
	 * 获取收款单合计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jan 2, 2014
	 */
	public CollectionOrder showCollectionOrderSumList(PageMap pageMap);
	/**
	 * 收款单删除
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jul 8, 2013
	 */
	public int deleteCollectionOrder(@Param("id")String id);
	/**
	 * 收款单修改
	 * @param collectionOrder
	 * @return
	 * @author chenwei 
	 * @date Jul 8, 2013
	 */
	public int editCollectionOrder(CollectionOrder collectionOrder);
	/**
	 * 收款单审核
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date Jul 9, 2013
	 */
	public int auditCollectionOrder(@Param("id")String id,@Param("status")String status,@Param("userid")String userid,@Param("username")String username,@Param("version") int version,@Param("businessdate")String businessdate);
	/**
	 * 收款单反审
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date Jul 9, 2013
	 */
	public int oppauditCollectionOrder(@Param("id")String id,@Param("userid")String userid,@Param("username")String username,@Param("version") int version);
	/**
	 * 收款单核销
	 * @param id
	 * @param writeoffamount
	 * @param remainderamount
	 * @param iswriteoff
	 * @return
	 * @author chenwei 
	 * @date Jul 11, 2013
	 */
	public int writeOffCollectionOrder(@Param("id")String id,@Param("writeoffamount")BigDecimal writeoffamount,
									   @Param("remainderamount")BigDecimal remainderamount,@Param("iswriteoff")String iswriteoff,
									   @Param("version")int version);
	
	/**
	 * 修改银行部门
	 * @param newbankdeptid
	 * @param oldbankdeptid
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 30, 2014
	 */
	public int updateCollectionOrderBankdept(@Param("newbankdeptid")String newbankdeptid,@Param("oldbankdeptid")String oldbankdeptid);
	/**
	 * 添加未指定客户收款单分配客户金额信息
	 * @param CollectionOrderAssign
	 * @return
	 * @author chenwei 
	 * @date Jan 4, 2014
	 */
	public int addCollectionOrderAssign(CollectionOrderAssign CollectionOrderAssign);
	
	/**
	 * 根据客户编码，业务日期获取审核通过、关闭的所有收款金额
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date May 23, 2014
	 */
	public BigDecimal getCollectionOrderAmountSum(Map map);

	/**
	 * 根据oaid查询收款单
	 * @param oaid
	 * @return
	 * @author limin
	 * @date Mar 29, 2016
     */
	public List<CollectionOrder> selectCollectionOrderByOaid(@Param("oaid") String oaid);
    /**
     * 获取对应收款单 信息
     * @return
     * @author limin
     * @date Mar 29, 2016
     */
    public List<Map> getCollectionAmountData(List idarr);

	/**
	 * 根据来源取收款单
	 *
	 * @param source
	 * @param sourceno
	 * @return
	 * @author limin
	 * @date Sep 5, 2016
	 */
	public CollectionOrder getCollectionOrderBySource(@Param("source")String source, @Param("sourceno")String sourceno) throws Exception;

	/**
	 * 获取收款单列表<br/>
	 * map中参数:<br/>
	 * dataSql：数据权限<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * status: 表示状态3<br/>
	 * statusarr: 表示状态，类似 1,2,3<br/>
	 * @param map
	 * @return java.util.List<com.hd.agent.account.model.CollectionOrder>
	 * @throws
	 * @author zhanghonghui
	 * @date May 22, 2017
	 */
	public List<CollectionOrder> showCollectionOrderListBy(Map map);

	/**
	 * 更新打印次数
	 * @param collectionOrder
	 * @return
	 * @author zhanghonghui
	 * @date 2013-9-30
	 */
	public int updateOrderPrinttimes(CollectionOrder collectionOrder);

}
