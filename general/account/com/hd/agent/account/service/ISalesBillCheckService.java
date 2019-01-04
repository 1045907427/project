/**
 * @(#)ISalesBillCheckService.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 19, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.account.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.account.model.SalesBillCheck;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 销售单据核对service
 * @author panxiaoxiao
 */
public interface ISalesBillCheckService {
	
	/**
	 * 获取销售单据核对列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 14, 2014
	 */
	public PageData showSalesBillCheckData(PageMap pageMap)throws Exception;
	
	/**
	 * 根据销售编码获取销售单据核对信息
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 14, 2014
	 */
	public SalesBillCheck getSalesBillCheckInfo(String customerid,String businessdate)throws Exception;
	
	/**
	 * 判断销售单据核对是否重复
	 * @param customerid
	 * @param businessdate
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 15, 2014
	 */
	public boolean repeatSalesBillCheck(String customerid,String businessdate)throws Exception;
	
	/**
	 * 根据客户编码，业务日期获取销售单据核对信息
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 19, 2014
	 */
	public SalesBillCheck getSalesBillCheckInfoByPageMap(PageMap pageMap)throws Exception;
	
	/**
	 * 添加销售单据核对数据
	 * @param salesBillCheck
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 14, 2014
	 */
	public Map addSalesBillCheck(SalesBillCheck salesBillCheck)throws Exception;
	
	/**
	 * 修改销售单据核对数据
	 * @param salesBillCheck
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 14, 2014
	 */
	public Map editSalesBillCheck(SalesBillCheck salesBillCheck)throws Exception;
	
	/**
	 * 获取要导出的销售单据核对
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 16, 2014
	 */
	public List<SalesBillCheck> getExportSalesBillCheckList(PageMap pageMap)throws Exception;
	
	/**
	 * 导入销售单据核对
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 16, 2014
	 */
	public Map addDRSalesBillCheck(List<SalesBillCheck> list)throws Exception;
}

