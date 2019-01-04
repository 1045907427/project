/**
 * @(#)IOrderCarService.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 2, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.service;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.OrderCar;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhengziyong
 */
public interface IOrderCarService {
	
	/**
	 * 添加直营销售订单
	 * @param order
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 10, 2013
	 */
	public Map addOrderCar(OrderCar order) throws Exception;

	/**
	 * 更新直营销售单信息
	 * @param order
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Sep 29, 2013
	 */
	public boolean updateOrderCar(OrderCar order) throws Exception;
	
	/**
	 * 获取车售订单详细信息包括订单商品明细
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 10, 2013
	 */
	public OrderCar getOrderCar(String id) throws Exception;
	
	/**
	 * 获取直营销售订单列表信息
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 10, 2013
	 */
	public PageData getOrderCarData(PageMap pageMap) throws Exception;
	
	/**
	 * 审核直营销售订单
	 * @param id 订单编号
	 * @return flag：审核或反审结果，
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 30, 2013
	 */
	public Map auditOrderCar(String id,SysUser sysUser) throws Exception;
	/**
	 * 反审直营销售订单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2014
	 */
	public Map oppauditOrderCar(String id) throws Exception;
	
	/**
	 * 批量审核
	 * @param id 审核订单编号字符串
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Aug 5, 2013
	 */
	public Map auditMultiOrderCar(String id) throws Exception;
	/**
	 * 批量删除车销订单
	 * @param ids
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 9, 2014
	 */
	public Map deleteMultiOrderCar(String ids) throws Exception;
	/**
	 * 直营销售单转换成要货申请单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 9, 2014
	 */
	public Map updateOrderCarToDemand(String id) throws Exception;
	

	/**
	 * 车销打印分页列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-4-9
	 */
	public PageData showOrderCarPrintListData(PageMap pageMap) throws Exception;
	/**
	 * 车销打印列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-4-10
	 */
	public List getOrderCarPrintList(PageMap pageMap) throws Exception;
	/**
	 * 验证直营销售单最近几天是否重复
	 * @param id
	 * @return true 不重复 false 重复
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 25, 2014
	 */
	public boolean checkOrderCarRepeat(String id) throws Exception;	
	/**
	 * 根据keyid 判断在车销单是否存在
	 * @param keyid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月18日
	 */
	public boolean isHaveOrderCarByKeyid(String keyid) throws Exception;

    /**
     * 根据keyid 获取零售单单据编号
     * @param keyid
     * @return
     * @throws Exception
     */
    public String getOrderCarBillidByKeyid(String keyid) throws Exception;
}

