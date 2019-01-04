package com.hd.agent.sales.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.Demand;
import com.hd.agent.sales.model.DemandDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DemandMapper {

	public List getDemandList(PageMap pageMap);
	
	public int getDemandCount(PageMap pageMap);
	
	public int addDemand(Demand demand);
	
	public int updateDemandStatus(Demand demand);
	
	public int updateDemand(Demand demand);
	
	public Demand getDemand(String id);
	
	
	public Map getDemandDetailTotal(String id);
	
	public int addDemandDetail(DemandDetail demandDetail);
	
	public List<DemandDetail> getDemandDetailByDemand(String id);
	
	public List<DemandDetail> getDemandDetailByDemandSum(String id);
	/**
	 * 删除要货申请单
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Apr 9, 2014
	 */
	public int deleteDemand(@Param("id")String id);
	/**
	 * 删除要货申请单明细
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Apr 9, 2014
	 */
	public int deleteDemandDetail(@Param("id")String id);
	/**
	 * 判断要货单是否重复 
	 * @param id
	 * @param customerid
	 * @param days
	 * @return
	 * @author chenwei 
	 * @date Apr 25, 2014
	 */
	public Map checkDemandRepeat(@Param("id")String id,@Param("customerid")String customerid,@Param("days")String days);
	/**
	 * 根据keyid判断要货单是否存在
	 * @param keyid
	 * @return
	 * @author chenwei 
	 * @date 2014年12月18日
	 */
	public Demand getDemandByKeyid(@Param("keyid")String keyid);

    /**
     * 获取要货单中的品牌列表
     * @param id        订单编号
     * @return
     */
    public List<String> getBrandListInDemand(@Param("id") String id);

    /**
     * 根据要货单编号列表 获取品牌列表
     * @param ids
     * @return
     */
    public List<String> getBrandListInDemandByIds(@Param("ids") List<String> ids);

	/**
	 * 获取每个人员的当天要货信息
	 * @return
	 */
	public List getDemandImageData();

	/**
	 * 查询当日要货单记录
	 */
	public List getDemandImageList(Map map);

	/**
	 * 查询当日合计要货总金额
	 */
	public String getDemandTotalToday();

	/**
	 *
	 * @param pageMap
	 * @return
	 */
	public List getPersonnelDemandList(PageMap pageMap);

	/**
	 *
	 * @param pageMap
	 * @return
	 */
	public int getPersonnelDemandCount(PageMap pageMap);

	/**
	 * 获取要货单明细按商品和商品类型合计，汇总商品数量的商品数量
	 * @param orderid
	 * @return int
	 * @throws
	 * @author luoqiang
	 * @date Oct 23, 2017
	 */
	public int getDemandGoodsNum(@Param("orderid")String orderid);

	/**
	 * 获取要货单明细的备注 用，隔开
	 * @param orderid
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Oct 23, 2017
	 */
	public String getDemandRemarks(@Param("orderid")String orderid);

	/**
	 * 修改要货单关联的订货单编号
	 * @param demandid
	 * @param ordergoodsid
	 * @return int
	 * @throws
	 * @author luoqiang
	 * @date Nov 01, 2017
	 */
	public int updateDemandOrderGoodsid(@Param("demandid")String demandid,@Param("ordergoodsid")String ordergoodsid);

	/**
	 * 判断订货单是否被要货单关联过
	 * @param ordergoodsid
	 * @return int
	 * @throws
	 * @author luoqiang
	 * @date Nov 01, 2017
	 */
	public String getOrderGoodsInDemand(@Param("ordergoodsid")String ordergoodsid);
}