/**
 * @(#)IReportTargetService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年12月5日 chenwei 创建版本
 */
package com.hd.agent.report.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.report.model.ReportTarget;

/**
 * 
 * 各类报表考核目标service
 * @author chenwei
 */
public interface IReportTargetService {
	/**
	 * 添加报表考核目标
	 * @param reportTarget
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月5日
	 */
	public boolean addReportTarget(ReportTarget reportTarget) throws Exception;
	
	/**
	 * 根据参数获取各类报表考核目标信息
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 23, 2015
	 */
	public ReportTarget getReportTargetInfo(Map map)throws Exception;

	/**
	 * 添加报表考核目标
	 * @param map
	 * @param billtype（SalesWithdrawnBranduser/SalesWithdrawnBrand）判断是品牌业务员或品牌
	 * @param businessdate 查询条件的日期
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2017-01-06
	 */
	public Map addMapToReportTarget(Map<String, Object> map,String billtype,String businessdate)throws Exception;

	/**
	 * 导入分客户业务员品牌销售回笼考核目标数据
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2017-01-06
	 */
	public Map importSalesuserBrandSWAData(List<Map<String,Object>> list,String billtype)throws Exception;

	/**
	 * 根据条件获取销售回笼考核目标合计
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2017-01-11
	 */
	public ReportTarget getReportTargetSumCaseSalesWithdrawnAssess(Map paramMap)throws Exception;
}

