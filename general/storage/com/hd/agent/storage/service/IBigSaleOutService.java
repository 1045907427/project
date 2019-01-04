/**
 * @(#)ILargeSingleService.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 11, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.storage.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.BigSaleOut;

/**
 * 大单发货事物 接口
 * 
 * @author panxiaoxiao
 */
public interface IBigSaleOutService {

	/**
	 * 新增大单发货单
	 * @param ids
	 * @param storageid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 12, 2014
	 */
	public Map addBigSaleOut(String ids,String storageid)throws Exception;
	
	/**
	 * 获取大单发货单列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 12, 2014
	 */
	public PageData getBigSaleOutList(PageMap pageMap)throws Exception;
	
	/**
	 * 获取发货单列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 12, 2014
	 */
	public PageData getSaleOutListForBigSaleOut(PageMap pageMap)throws Exception;
	
	/**
	 * 根据编号获取大单发货单详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 12, 2014
	 */
	public BigSaleOut getBigSaleOutInfo(String id)throws Exception;
	
	/**
	 * 审核大单发货单
	 * @param ids
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 13, 2014
	 */
	public Map auditBigSaleOuts(String ids,BigSaleOut bigSaleOut)throws Exception;
	
	/**
	 * 确认发货
	 * @param ids
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 20, 2014
	 */
	public Map doSaleoutBigSaleOuts(String ids)throws Exception;
	
	/**
	 * 反审大单发货单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 14, 2014
	 */
	public Map oppauditBigSaleOut(String id)throws Exception;
	
	/**
	 * 删除大单发货单
	 * @param ids
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 14, 2014
	 */
	public Map deleteBigSaleOuts(String ids)throws Exception;
	
	/**
	 * 修改大单发货单
	 * @param bigSaleOut
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 20, 2014
	 */
	public Boolean saveBigSaleOut(String billid,String editsaleoutid,String remark,String isdel)throws Exception;
	
	/*-------------------------大单发货单明细-------------------------*/
	
	/**
	 * 获取大单发货单明细来源编号列表
	 */
	public List<String> getBigSaleOutDetailSourceidList()throws Exception;
	
	/**
	 * 根据大单发货单编码获取其商品明细列表
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 13, 2014
	 */
	public PageData getBigSaleOutGoodsList(PageMap pageMap)throws Exception;
	
	/**
	 * 获取大单发货单单据明细列表
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 13, 2014
	 */
	public List getBigSaleOutSourceBillList(String id)throws Exception;
	/**
	 * 更新大单发货单单据明细列表
	 * @param pageMap ids
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 12,28, 2015
	 */
	public PageData getUpdateBigSaleOutSourceBillList(PageMap pageMap,String ids)throws Exception;
	/**
	 * 获取按商品获取分客户对应该商品数量的数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 23, 2014
	 */
	public PageData getBigSaleOutCustomerGoodsNumList(PageMap pageMap)throws Exception;

	/**
	 *
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public Map cancelDoSaleoutBigSaleOuts(String ids)throws Exception;
}

