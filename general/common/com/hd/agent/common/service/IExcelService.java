/**
 * @(#)IExcelService.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 30, 2013 zhengziyong 创建版本
 */
package com.hd.agent.common.service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.ExportSalesOrder;
import com.hd.agent.storage.model.ExportLend;
import com.hd.agent.storage.model.ExportStorageOtherEnterAndOut;
import com.hd.agent.storage.model.ExportLend;

/**
 * 
 * 
 * @author zhengziyong
 */
public interface IExcelService {

	/**
	 * 获取导出数据列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 30, 2013
	 */
	public List getList(PageMap pageMap) throws Exception;

    public Map getAutoList(PageMap pageMap) throws Exception;
	
	public List getAnotherList(PageMap pageMap)throws Exception;
	
	
	/**
	 * 获取导出的销售列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 8, 2014
	 */
	public List<ExportSalesOrder> getBillList(PageMap pageMap)throws Exception;
	
	public List getGoodsList(PageMap pageMap)throws Exception;
	
	public List getSupplierList(PageMap pageMap)throws Exception;
	
	public List getCustomerList(PageMap pageMap)throws Exception;

	/**
	 * 插入导入数据
	 * @param clazz
	 * @param list
	 * @param method
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 30, 2013
	 */
	public Map<String, Integer> insert(Object clazz, List list, Method method) throws Exception;
	
	public Map<String, Object> insertSalesOrder(Object clazz, List list, Method method)throws Exception;
	
	public Map<String, Object> comInsertMethod(Object object, List list, Method method)throws Exception;
	
	public Map<String, Object> insertCaseBySameMethod(Object object,Map map,Method method)throws Exception;
	
	public Map insertMain(Object clazz, Object object, Method method) throws Exception;

    /**
     * 获取导出的出入库单列表明细
     * @param pageMap
     * @return
     * @throws Exception
     * @author lin_xx
     * @date Feb 17, 2016
     */
    public List<ExportStorageOtherEnterAndOut> getStorageOtherEnterAndOutList(PageMap pageMap , String type)throws Exception;

	/**
	 * 获取借货还货单列表明细
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author lin_xx
	 * @date Feb 17, 2016
	 */
	public List<ExportLend> getLendList(PageMap pageMap)throws Exception;


}

