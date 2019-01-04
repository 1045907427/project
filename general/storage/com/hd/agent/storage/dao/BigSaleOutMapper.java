/**
 * @(#)bigSaleOutMapper.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 11, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.storage.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.BigSaleOut;
import com.hd.agent.storage.model.BigSaleOutDetail;
import com.hd.agent.storage.model.SaleoutDetail;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public interface BigSaleOutMapper {

	/*----------------------大单发货单----------------------------------------*/
	
	/**
	 * 获取大单发货单列表
	 */
	public List getBigSaleOutList(PageMap pageMap);
	
	/**
	 * 获取大单发货单数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 12, 2014
	 */
	public int getBigSaleOutCount(PageMap pageMap);
	
	/**
	 * 新增大单发货
	 * @param bigSaleOut
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 12, 2014
	 */
	public int addBigSaleOut(BigSaleOut bigSaleOut);
	
	/**
	 * 修改大单发货单
	 * @param bigSaleOutDetail
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 12, 2014
	 */
	public int editBigSaleOut(BigSaleOut bigSaleOut);
	
	/**
	 * 根据大单发货编码获取其信息
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 12, 2014
	 */
	public BigSaleOut getBigSaleOutInfo(@Param("id")String id);
	
	/**
	 * 审核大单发货单
	 * @param bigSaleOut
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 13, 2014
	 */
	public int auditBigSaleOut(BigSaleOut bigSaleOut);
	
	/**
	 * 中止大单发货单
	 * @param bigSaleOut
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 13, 2014
	 */
	public int stopBigSaleOut(BigSaleOut bigSaleOut);
	
	/**
	 * 反审大单发货单
	 * @param bigSaleOut
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 14, 2014
	 */
	public int oppauditBigSaleOut(BigSaleOut bigSaleOut);
	
	/**
	 * 删除大单发货单
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 14, 2014
	 */
	public int deleteBigSaleOut(@Param("id")String id);
	
	/**
	 * 关闭大单发货单
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 20, 2014
	 */
	public int closeBigSaleOut(@Param("id")String id);

	/**
	 * 取消关闭大单发货单
	 * @param id
	 * @return
	 * @author panxiaoxiao
	 * @date 2016-09-14
	 */
	public int updateCancelCloseBigSaleOut(@Param("id")String id);

	/**
	 * 根据大单发货编号判断是否存在已关闭状态的来源单据
	 * @param id
	 * @return
	 * @author panxiaoxiao
	 * @date 2016-09-19
	 */
	public int getCloseSaleoutByBigSaleOutId(@Param("id")String id);
	
	/**
	 * 更新大单发货单打印次数
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 25, 2014
	 */
	public int updateBigSaleoutPrinttimes(@Param("id")String id,@Param("printtimes")Integer printtimes);
	
	/*------------------------------大单发货单明细----------------------------------*/
	
	/**
	 * 根据大单发货单单据编号获取大单发货单明细列表
	 */
	public List getBigSaleOutDetailList(@Param("billid")String billid);
	
	/**
	 * 根据发货单编码集获取商品明细列表
	 * @param sourceids
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 12, 2014
	 */
	public List<SaleoutDetail> getBigSaleOutGoodsList(PageMap pageMap);
	
	/**
	 * 根据发货单编码集获取商品明细数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 13, 2014
	 */
	public int getBigSaleOutGoodsCount(PageMap pageMap);
	
	/**
	 * 新增大单发货单明细
	 * @param bigSaleOutDetail
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 12, 2014
	 */
	public int addBigSaleOutDetail(BigSaleOutDetail bigSaleOutDetail);
	
	/**
	 * 获取大单发货单明细来源编号列表
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 12, 2014
	 */
	public List getBigSaleOutDetailSourceidList();
	
	/**
	 * 根据单据编号删除大单发货单明细
	 * @param saleoutid
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 14, 2014
	 */
	public int deleteBigSaleOutDetailByBillid(@Param("billid")String billid);
	
	/**
	 * 获取打印大单发货单商品明细列表数据
	 * @param bigsaleoutid
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 24, 2014
	 */
	public List getBigSaleOutGoodsListForPrint(@Param("bigsaleoutid")String bigsaleoutid);

	/**
	 * 分拣单打印数据
	 * @param bigsaleoutid
	 * @return
	 * @author panxiaoxiao
	 * @date panxiaoxiao
	 */
	public List getBigSaleOutSaleoutDetailListForPrint(@Param("bigsaleoutid")String bigsaleoutid);
	
	/**
	 * 获取大单发货单按商品分客户明细列表数据
	 * @param bigsaleoutid
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 9, 2014
	 */
	public List getBigSaleOutGoodsCustomerListForPrint(@Param("bigsaleoutid")String bigsaleoutid);

	/**
	 * 根据大单发货单编号获取来源单据明细按商品合计（同商品分区块）
	 * @param bigsaleoutid
	 * @return
	 */
	public List<SaleoutDetail> getBigSaleOutGoodsSum(@Param("bigsaleoutid")String bigsaleoutid);

	/**
	 * 根据大单发货单编号,指定商品获取来源单据明细按客户合计（同商品分区块）
	 * @param bigsaleoutid
	 * @return
	 */
	public List<SaleoutDetail> getBigSaleOutGoodsCustomerList(@Param("bigsaleoutid")String bigsaleoutid,@Param("goodsid")String goodsid);
	
	/**
	 * 获取按商品获取分客户对应该商品数量的数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 23, 2014
	 */
	public List getBigSaleOutCustomerGoodsNumList(PageMap pageMap);
	
	/**
	 * 获取按商品获取分客户对应该商品数量的数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 23, 2014
	 */
	public int getBigSaleOutCustomerGoodsNumCount(PageMap pageMap);

	/**
	 * 分品牌合计大单发货单据列表明细
	 * @param bigsaleoutid
	 * @return
	 * @author panxiaoxiao
	 * @date 2016-09-27
	 */
	public List<SaleoutDetail> getBigSaleOutBrandSumList(@Param("bigsaleoutid")String bigsaleoutid);

	/**
	 * 分品牌分商品获取发货单明细列表
	 * @param bigsaleoutid
	 * @param brandid
	 * @return
	 * @author panxiaoxiao
	 * @date 2016-09-27
	 */
	public List<SaleoutDetail> getBigSaleOutBrandGoodsList(@Param("bigsaleoutid")String bigsaleoutid,@Param("brandid")String brandid);
}

