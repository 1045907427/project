package com.hd.agent.sales.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.PromotionPackage;
import com.hd.agent.sales.model.PromotionPackageGroup;
import com.hd.agent.sales.model.PromotionPackageGroupDetail;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface PromotionPackageMapper {
    /**
     * 获取对应的促销活动数据
     * @param pageMap
     * @return
     * @author lin_xx
     * @date 2015年1月8日
     */
	public List getPromotionList(PageMap pageMap);

	public int getPromotionCount(PageMap pageMap);
	/**
	 * 获取促销活动
	 * @param id
	 * @return
	 * @author lin_xx 
	 * @date 2015年1月8日
	 */
	public PromotionPackage getPromotion(String id);

	public List<PromotionPackageGroup> getGroupListByPromotion (String id);
	/**
	 * 买赠时通过购买商品的id找groupDetail
	 * @param buygoodsid
	 * @return
	 * @author lin_xx 
	 * @date 2015年1月9日
	 */
	public PromotionPackageGroupDetail getGroupDetailBybuygoodid (@Param("goodsid")String buygoodsid);
	 /**
	  * 通过促销编号查找赠送商品信息
	  * @param billid
	  * @return
	  * @author lin_xx 
	  * @date 2015年1月13日
	  */
	public List<PromotionPackageGroupDetail> getGiveGood(String billid);
	
	public int addPackage(PromotionPackage promotionPackage);
	
	public int addPackageGroup(PromotionPackageGroup promotionPackageGroup);

	public int addPackageGroupDetail(PromotionPackageGroupDetail detail);
	
	/**
	 * 查找赠送商品
	 * @param billid
	 * @return
	 * @author lin_xx 
	 * @date 2015年1月21日
	 */
	public List<PromotionPackageGroupDetail> selectPromotionPackageDetailList(String billid);

	/**
	 * 查找购买商品
	 * @param groupid
	 * @return
	 * @author lin_xx 
	 * @date 2015年1月21日
	 */
	public PromotionPackageGroupDetail getGroupDetailByid(String groupid);
	
	public String[] getGroupIdByPid(String billid);
	/**
	 * 改变单据状态,小范围刷新
	 * @param model
	 * @return
	 * @author lin_xx 
	 * @date 2015年1月26日
	 */
	public int updatePackageStatus(PromotionPackage model);
	/**
	 * 刷新单据
	 * @param model
	 * @return
	 * @author lin_xx 
	 * @date 2015年1月28日
	 */
	public int updateByPromotion(PromotionPackage model);
	/**
	 * 刷新group	
	 * @param groupModel
	 * @return
	 * @author lin_xx 
	 * @date 2015年1月29日
	 */
	public int updateByGroup(PromotionPackageGroup groupModel);

    /**
     * 更新特价单状态
     * @param model
     * @return
     */
    public int updatePromotionPackageStatus(PromotionPackage model);
	/**
	 * 刷新买赠商品详细
	 * @param detailModel
	 * @return
	 * @author lin_xx 
	 * @date 2015年1月29日
	 */
	public int updateByDetail(PromotionPackageGroupDetail detailModel);
	
	public int deleteByPackageId(String id);
	public int deleteByPromotionId(String id);
	public int deleteByGroupId(String id);
	
	public List<PromotionPackageGroupDetail> getDetailByGroupid(String groupid);
	/**
	 * 捆绑促销信息获取
	 * @param groupid
	 * @return
	 * @author lin_xx 
	 * @date 2015年2月5日
	 */
	public List<PromotionPackageGroup> getBundleGroupInfo(@Param("groupid")String groupid);

    public PromotionPackageGroup getBundleGroup(@Param("groupid")String groupid);

    public List<PromotionPackageGroup> getBundleInfo(@Param("billid")String billid);
    /**
     * 根据客户信息与商品信息 获取满赠信息
     * @param map
     * @return
     * @author chenwei 
     * @date 2015年9月16日
     */
	public List<PromotionPackageGroup> getPromotionPackageGroupByCustomerAndGoods(Map map);

    /**
     * 更新促销组已促销数量 
     * @param billid            促销单据编号
     * @param groupid           促销分组编号
     * @param sendnum           促销数量
     * @return
     */
    public int updatePromotionPackageGroupSendnum(@Param("billid")String billid,@Param("groupid")String groupid,@Param("sendnum")BigDecimal sendnum);

    /**
     * 获取截止日期为当前日期的促销单
     * @author lin_xx
     * @param today
     * @date 2016-5-27
     * @return
     */
    public List<PromotionPackage> getPromotionListByDate(String today);

}