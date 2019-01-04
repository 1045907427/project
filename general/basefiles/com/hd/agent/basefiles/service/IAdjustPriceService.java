/**
 * @(#)IAdjustPriceService.java
 *
 * @author wanghongteng
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年10月9日 wanghongteng 创建版本
 */
package com.hd.agent.basefiles.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.model.AdjustPrice;
import com.hd.agent.basefiles.model.AdjustPriceDetail;
import com.hd.agent.basefiles.model.AdjustPriceExport;
import com.hd.agent.basefiles.model.CustomerPrice;
import com.hd.agent.basefiles.model.GoodsInfo_PriceInfo;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author wanghongteng
 */
public interface IAdjustPriceService {

	// 调价单部分
	/**
	 * 获取调价单列表
	 * 
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	public PageData showAdjustPriceList(PageMap pageMap) throws Exception;

	/**
	 * 根据商品编码，客户编码获取含税单价
	 * 
	 * @param goodsid
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	public CustomerPrice getPriceDataByCustomerid(String goodsid, String customerid) throws Exception;

	/**
	 * 根据客户编码获取合同商品列表
	 * 
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	public List<CustomerPrice> getCustomerPriceListByTypeCode(String customerid) throws Exception;
	/**
	 * 根据价格套编码获取价格套商品列表
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	public List<GoodsInfo_PriceInfo> getPriceListByTypeCode(String code) throws Exception;
	// 通过品牌自动生成
	/**
	 * 获取品牌编码
	 * 
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	public List getBrandList() throws Exception;

	/**
	 * 通过涨幅和商品品牌获取调价商品
	 * 
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	public List getAdjustPriceDetailByBrand(String rate, String brands, String type, String busid) throws Exception;

	/**
	 * 通过客户编码获取品牌编码
	 * 
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	public List getBrandListByCustomerid(String customerid) throws Exception;

	/**
	 * 通过涨幅和品牌获取合同价调价商品
	 * 
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	public List getAdjustCustomerPriceDetailByBrand(String rate, String brands, String customerid) throws Exception;

	// 通过分类自动生成
	/**
	 * 获取分类编码
	 * 
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	public List getDefaultSortList() throws Exception;

	/**
	 * 通过涨幅和商品分类获取调价商品
	 * 
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	public List getAdjustPriceDetailByDefaultSort(String rate, String goodssorts, String type, String busid) throws Exception;

	/**
	 * 保存新增的调价单
	 * 
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	public Map addAdjustPrice(AdjustPrice adjustPrice, List<AdjustPriceDetail> detailList) throws Exception;

	/**
	 * 审核调价单
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	public Map auditAdjustPrice(String id) throws Exception;

	/**
	 * 保存修改的调价单
	 * 
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	public Map editAdjustPrice(AdjustPrice adjustPrice, List<AdjustPriceDetail> detailList) throws Exception;

	/**
	 * 删除调价单
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	public boolean deleteAdjustPrice(String id) throws Exception;

	/**
	 * 反审调价单
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	public Map oppauditAdjustPrice(String id) throws Exception;

	/**
	 * 通过id获取调价单
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	public Map getAdjustPriceInfo(String id) throws Exception;
	/**
	 * 获取导出列表
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 10,5,2015
	 */
	public List<AdjustPriceExport> getAdjustPriceExportList(PageMap pageMap) throws Exception ;
	/**
	 * 导入商品调价单
	 * @param list
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 24, 2015
	 */
	public Map importAdjustPrice(List<Map<String, Object>> list) throws Exception;
}
