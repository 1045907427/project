/**
 * @(#)IDemandService.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Oct 24, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.Demand;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhengziyong
 */
public interface IDemandService {

	public PageData getDemanData(PageMap pageMap) throws Exception;
	
	public boolean addDemand(Demand demand) throws Exception;
	
	public Demand getDemand(String id) throws Exception;
	
	public String auditDemand(String id) throws Exception;
	
	public boolean deleteDemand(String id) throws Exception;
	/**
	 * 要货申请单转换直营销售单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 9, 2014
	 */
	public Map updateDemandToOrderCar(String id) throws Exception; 
	/**
	 * 验证要货单最近几天是否重复
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 25, 2014
	 */
	public boolean checkDemandRepeat(String id) throws Exception;
	/**
	 * 判断要货单是否上传过
	 * @param keyid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月18日
	 */
	public boolean isDemandHaveByKeyid(String keyid) throws Exception;

    /**
     * 判断客户是否超账期
     * @param id
     * @return
     * @throws Exception
     */
    public Map isReceivablePassDateByIds(String id) throws Exception;
	/**
	 * 查询当天要货单每个人员的数据
	 * @param
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Dec 15, 2016
	 */
	public List getDemandImageData();

	/**
	 * 查询当日要货单记录
	 * @return
	 */
	public List getDemandImageList() throws Exception;

	/**
	 * 获得当日要货合计金额
	 * @return
	 */
	public String getDemandTotalToday();

	/**
	 * 查询当日改人员的要货情况
	 * @param pageMap
	 * @return
	 */
	public PageData getPersonnelDemandData(PageMap pageMap) throws Exception;

	/**
	 * 要货单关联订货单生成订单
	 * @param demandid
	 * @param ordergoodsid
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Oct 20, 2017
	 */
	public String addOrderByDemandAndOrderGoods(String demandid,String ordergoodsid) throws Exception;
}

