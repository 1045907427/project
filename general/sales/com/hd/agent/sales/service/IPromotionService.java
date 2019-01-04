/**
 * @(#)IPromotionService.java
 *
 * @author lin_xx
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年1月7日 lin_xx 创建版本
 */
package com.hd.agent.sales.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.PromotionPackage;
import com.hd.agent.sales.model.PromotionPackageGroup;
import com.hd.agent.sales.model.PromotionPackageGroupDetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author lin_xx 
 */
public interface IPromotionService {
	/**
	 * 获取促销活动页面内容
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author lin_xx 
	 * @date 2015年1月7日
	 */
	public PageData getPromotionData(PageMap pageMap) throws Exception;
	/**
	 * 通过id查找对应的促销活动
	 * @param id
	 * @return
	 * @throws Exception
	 * @author lin_xx 
	 * @date 2015年1月8日
	 */
	public PromotionPackage getPromotionAndGroupById(String id) throws Exception;
	/**
	 * 促销数量和基准数量 辅基准数量之间的变换
	 * @param name
	 * @param auxremainder
	 * @param auxnum
	 * @param promotionNum
	 * @return
	 * @throws Exception
	 * @author lin_xx 
	 * @date 2015年1月13日
	 */
	public Map changeAux(String name,String promotionNum,String auxremainder,String auxnum,String type) throws Exception;
	/**
	 * 添加商品时获取商品的对应信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author lin_xx 
	 * @date 2015年1月14日
	 */
	public Map getClickGoodsInfo(String id) throws Exception;
	/**
	 * 插入新增的买赠促销活动
	 * @return
	 * @throws Exception
	 * @author lin_xx 
	 * @date 2015年1月19日
	 */
	public Map addBuyFreeInfo(PromotionPackage promotionPackage, List groupList) throws Exception;
	/**
	 * 查找赠送商品信息
	 * @param group_id
	 * @return
	 * @throws Exception
	 * @author lin_xx 
	 * @date 2015年1月26日
	 */
	public List<PromotionPackageGroupDetail> viewGiveDetail(String group_id) throws Exception ;
	/**
	 * 查找购买商品信息
	 * @param groupid
	 * @return
	 * @throws Exception
	 * @author lin_xx 
	 * @date 2015年1月26日
	 */
	public PromotionPackageGroupDetail getGroupDetailByid(String groupid) throws Exception; 
	
	public boolean auditPromotion(String type, String id) throws Exception;

	public PromotionPackageGroupDetail getGroupDetailBybuygoodid(String goodsid) throws Exception;
	/**
	 * 更新单据信息
	 * @param promotionPackage
	 * @param groupList
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author lin_xx 
	 * @date 2015年1月29日
	 */
	public boolean updateGroupAndGivePromotion(PromotionPackage promotionPackage, List groupList, List detailList) throws Exception;
	
	public boolean deletePromotionById(String id) ;
	
	public boolean auditBundleBill (String billid) throws Exception;
	
	public Map addBundleInfo(PromotionPackage promotionPackage, List groupList) throws Exception;

    /**
     * 根据编号获取该组促销详细
     * @param groupid
     * @return
     * @throws Exception
     */
	public PromotionPackageGroup getBundle(String groupid) throws Exception;

    /**
     * 判断该促销产品是否被销售订单引用
     * @param groupid
     * @return
     * @throws Exception
     */
    public boolean isPromotionQuote(String groupid) throws Exception ;

    /**
     * 作废促销单
     * @param ids
     * @return
     * @throws Exception
     */
    public Map promotionCancel(String ids,String operate) throws Exception;


}

