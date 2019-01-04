/**
 * @(#)PhoneMapper.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Nov 29, 2013 zhengziyong 创建版本
 */
package com.hd.agent.phone.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.report.model.BaseSalesWithdrawnReport;
import com.hd.agent.report.model.SalesDemandReport;
import org.activiti.engine.impl.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhengziyong
 */
public interface PhoneMapper {
	
	/**
	 * 添加反馈信息
	 * @param map
	 * @return
	 * @author zhengziyong 
	 * @date Dec 31, 2013
	 */
	public int addFeed(Map map);
	
	public Map getLoginUserInfo(String userid);
	
	public String getManagerUserDepartment(String uid);
	
	public List<Map> getDepartmentUserList(String deptid, @Param("con")String con);

    public List<Map> getBaseOrderQueryReprot(Map map);

	/**
	 * 客户抄货汇总查询
	 * @param map 查询条件
	 * @return
	 * @author zhengziyong 
	 * @date Dec 7, 2013
	 */
	public List<Map> getCustomerOrderQueryReport(Map map);
	
	/**
	 * 客户抄货汇总单品明细查询
	 * @param map
	 * @return
	 * @author zhengziyong 
	 * @date Dec 7, 2013
	 */
	public List<Map> getCustomerGoodsOrderQueryReport(Map map);
	
	/**
	 * 产品抄单汇总查询
	 * @param map （t1开始日期,t2结束日期,con条件）
	 * @return
	 * @author zhengziyong 
	 * @date Dec 9, 2013
	 */
	public List<Map> getGoodsOrderQueryReport(Map map);
	
	/**
	 * 产品抄单汇总客户明细查询
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Dec 9, 2013
	 */
	public List<Map> getGoodsCustomerOrderQueryReport(Map map);
	
	/**
	 * 业务员抄单汇总查询
	 * @param map
	 * @return
	 * @author zhengziyong 
	 * @date Dec 9, 2013
	 */
	public List<Map> getSalerOrderQueryReport(Map map);
	
	/**
	 * 业务员抄单汇总客户明细查询
	 * @param map
	 * @return
	 * @author zhengziyong 
	 * @date Dec 10, 2013
	 */
	public List<Map> getSalerCustomerOrderQueryReport(Map map);
	
	/**
	 * 业务员抄单汇总客户商品明细查询
	 * @param map
	 * @return
	 * @author zhengziyong 
	 * @date Dec 10, 2013
	 */
	public List<Map> getSalerCustomerGoodsOrderQueryReport(Map map);
	
	/**
	 * 客户销售汇总查询
	 * @param map
	 * @return
	 * @author zhengziyong 
	 * @date Dec 13, 2013
	 */
	public List<Map> getCustomerSaleQueryReport(Map map);
	
	/**
	 * 客户销售汇总-产品明细查询
	 * @param map
	 * @return
	 * @author zhengziyong 
	 * @date Dec 13, 2013
	 */
	public List<Map> getCustomerGoodsSaleQueryReport(Map map);
	
	/**
	 * 业务员销售汇总查询
	 * @param map
	 * @return
	 * @author zhengziyong 
	 * @date Dec 13, 2013
	 */
	public List<Map> getSalerSaleQueryReport(Map map);
	/**
	 * 业务员销售汇总-产品明细查询
	 * @param map
	 * @return
	 * @author zhengziyong 
	 * @date Dec 13, 2013
	 */
	public List<Map> getSalerGoodsSaleQueryReport(Map map);
	/**
	 * 获取要货申请单追踪明细
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2014-5-9
	 */
	public Map getOrderTrack(String id);
	/**
	 * 获取车销单追踪明细
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2014-5-9
	 */ 
	public Map getOrderCarTrack(String id);
	/**
	 * 根据发货单获取配送单信息
	 * @param saleoutid
	 * @return
	 * @author chenwei 
	 * @date 2014年11月29日
	 */
	public List getLogisticsBillBySaleoutid(@Param("saleoutid")String saleoutid);
	/**
	 * 获取销售基础统计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年6月3日
	 */
	public List<Map> getBaseSaleReport(PageMap pageMap);
	
	/**
	 * 获取销售回笼统计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年9月13日
	 */
	public List getBaseWithdrawReport(PageMap pageMap);

}

