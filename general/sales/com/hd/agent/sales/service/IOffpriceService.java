/**
 * @(#)IOffpriceService.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 24, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.Offprice;
import com.hd.agent.sales.model.OffpriceDetail;
import com.hd.agent.sales.model.OffpriceExcel;

/**
 * 
 * 
 * @author zhengziyong
 */
public interface IOffpriceService {

	/**
	 * 添加销售特价调整单信息
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 25, 2013
	 */
	public boolean addOffprice(Offprice model) throws Exception;
	
	/**
	 * 获取销售特价调整单列表信息
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 25, 2013
	 */
	public PageData getOffpriceData(PageMap pageMap) throws Exception;
	
	/**
	 * 获取销售特价调整单信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 25, 2013
	 */
	public Offprice getOffprice(String id) throws Exception;
	
	/**
	 * 删除销售特价调整单信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 25, 2013
	 */
	public boolean deleteOffprice(String id) throws Exception;
	
	/**
	 * 修改特价调整单信息
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 25, 2013
	 */
	public boolean updateOffprice(Offprice model) throws Exception;
	
	/**
	 * 审核或反审特价调整单
	 * @param type 1为审核2为反审
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 25, 2013
	 */
	public boolean auditOffprice(String type, String id) throws Exception;
	
	/**
	 * 提交特价调整单进流程
	 * @param title
	 * @param userId
	 * @param processDefinitionKey
	 * @param businessKey
	 * @param variables
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jul 3, 2013
	 */
	public boolean submitOffpriceProcess(String title, String userId, String processDefinitionKey, String businessKey, Map<String, Object> variables) throws Exception;
	/**
	 * 添加并且审核特价调整单 （提供OA通路单接口）
	 * @param offprice
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月22日
	 */
	public String addAndAuditOffpriceToOa(Offprice offprice,List<OffpriceDetail> detailList) throws Exception;
	/**
	 * 通过OA编号删除特价调整单
	 * @param oaid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月24日
	 */
	public boolean deleteOffpriceByOA(String oaid) throws Exception;

    /**
     *
     * @param oaid
     * @return
     * @throws Exception
     */
    public Offprice selectOffPriceByOaid(String oaid) throws Exception;
    /**
     * 根据参数获取特价调整单<br/>
     * map中参数：<br/>
     * @param map
     * @return
     * @throws Exception
     * @author zhanghonghui 
     * @date 2015年8月31日
     */
    public List getOffpriceListBy(Map map) throws Exception;
    /**
	 * 更新打印次数
	 * @param list
	 * @author zhanghonghui 
	 * @date 2015-05-22
	 */
	public boolean updateOrderPrinttimes(Offprice offprice) throws Exception;
	/**
	 * 更新打印次数
	 * @param list
	 * @author zhanghonghui 
	 * @date 2015-05-22
	 */
	public void updateOrderPrinttimes(List<Offprice> list) throws Exception;

    /**
     * 获取导出的特价调整单列表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-10-06
     */
    public List<OffpriceExcel> getOffpriceExcelList(PageMap pageMap)throws Exception;

    /**
     * 特价促销导入
     * @param list
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-10-07
     */
    public Map addOffpriceExcel(List<OffpriceExcel> list)throws Exception;

    /**
     * 根据品牌和商品分类获取商品
     * @param pageMap
     * @return
     * @throws Exception
     */
    public PageData getGoodsByBrandAndSort(PageMap pageMap) throws Exception;

    /**
     * 作废对应编码的特价单据
     * @return
     * @throws Exception
     */
    public Map offpriceCancel(String ids,String operate) throws Exception;


}

