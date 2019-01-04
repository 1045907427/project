/**
 * @(#)SalesBillCheckMappler.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 19, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.account.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.account.model.SalesBillCheck;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 销售单据核对借口
 * @author panxiaoxiao
 */
public interface SalesBillCheckMapper {
	
	/**
	 * 获取销售单据核对列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date May 14, 2014
	 */
	public List getSalesBillCheckList(PageMap pageMap);
	
	/**
	 * 获取销售单据核对列表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date May 14, 2014
	 */
	public int getSalesBillCheckCount(PageMap pageMap);
	
	/**
	 * 根据客户编码获取销售单据数
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date May 14, 2014
	 */
	public int getSalesBillCheckNum(PageMap pageMap);
	
	/**
	 * 根据客户编码获取销售单据对应单据明细数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date May 20, 2014
	 */
	public List<Map> getSalesBillCheckDetailNum(PageMap pageMap);
	
	/**
	 * 根据客户编码获取销售单据核对信息
	 */
	public SalesBillCheck getSalesBillCheckInfo(@Param("customerid")String customerid,@Param("businessdate")String businessdate);
	
	/**
	 * 根据客户编码获取销售单据核对信息
	 * @param customerid
	 * @param businessdate
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 15, 2014
	 */
	public int repeatSalesBillCheck(@Param("customerid")String customerid,@Param("businessdate")String businessdate);
	
	/**
	 * 根据客户编码，业务日期获取销售单据核对信息
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date May 19, 2014
	 */
	public SalesBillCheck getSalesBillCheckInfoByPageMap(PageMap pageMap);
	
	/**
	 * 添加销售单据核对数据
	 * @param salesBillCheck
	 * @return
	 * @author panxiaoxiao 
	 * @date May 14, 2014
	 */
	public int addSalesBillCheck(SalesBillCheck salesBillCheck);
	
	/**
	 * 修改销售单据核对数据
	 * @param salesBillCheck
	 * @return
	 * @author panxiaoxiao 
	 * @date May 14, 2014
	 */
	public int editSalesBillCheck(SalesBillCheck salesBillCheck);
	
	/**
	 * 根据客户编码，业务日期计算销售单据核对合计
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date May 15, 2014
	 */
	public Map getSalesBillCheckDataSum(Map map);
	
	/**
	 * 获取要导出的销售单据核对
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date May 16, 2014
	 */
	public List getExportSalesBillCheckList(PageMap pageMap);
	/**
	 * 获取销售单据销售金额统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date May 14, 2014
	 */
	public Map getSalesBillSalesAmountSum(PageMap pageMap);
	/**
	 * 获取按供应商统计销售金额
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-7-9
	 */
	public List<Map<String, Object>> getSalesBillSupplierSalesAmountSum(PageMap pageMap);
	
}

