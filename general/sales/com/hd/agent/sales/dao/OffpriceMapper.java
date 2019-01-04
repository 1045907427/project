package com.hd.agent.sales.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.Offprice;
import com.hd.agent.sales.model.OffpriceDetail;
import com.hd.agent.sales.model.OffpriceExcel;
import com.hd.agent.storage.model.PurchaseEnter;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OffpriceMapper {

    /**
     * 添加特价单
     * @param model
     * @return
     */
	public int addOffprice(Offprice model);
    /**
     * 获取特价单列表
     * @param pageMap
     * @return
     */
	public List getOffpriceList(PageMap pageMap);
    /**
     * 获取特价单数量
     * @param pageMap
     * @return
     */
	public int getOffpriceCount(PageMap pageMap);

    /**
     * 根据促销编号获取特价单
     * @param id
     * @return
     */
	public Offprice getOffprice(String id);
    /**
     * 删除对应编码的特价单
     * @param id
     * @return
     */
	public int deleteOffprice(String id);

    /**
     * 更新特价单
     * @param model
     * @return
     */
	public int updateOffprice(Offprice model);

    /**
     * 更新特价单状态
     * @param model
     * @return
     */
	public int updateOffpriceStatus(Offprice model);
	
	public Offprice getOffpriceByCustomer(Map map);

    /**
     * 根据编码获取特价明细
     * @param id
     * @return
     */
	public OffpriceDetail getOffpriceDetail(String id);
    /**
     * 根据编码获取特价明细列表
     * @param id
     * @return
     */
	public List getDetailListByOffprice(String id);

    /**
     * 添加特价明细
     * @param detail
     * @return
     */
	public int addOffpriceDetail(OffpriceDetail detail);
    /**
     * 更新特价明细
     * @param detail
     * @return
     */
	public int updateOffpriceDetail(OffpriceDetail detail);
    /**
     * 删除特价明细
     * @param id
     * @return
     */
	public int deleteDetailByOffprice(String id);
	
	public OffpriceDetail getOffpriceDetailByCustomer(Map map);
	
	public OffpriceDetail getOffpriceByCustomerGoodsNum(Map map);
	/**
	 * 通过OA编号删除特价调整单明细
	 * @param oaid
	 * @return
	 * @author chenwei 
	 * @date 2014年10月24日
	 */
	public int deleteOffpriceDetailByOA(@Param("oaid")String oaid);
	/**
	 * 通过OA编号 删除特价调整单
	 * @param oaid
	 * @return
	 * @author chenwei 
	 * @date 2014年10月24日
	 */
	public int deleteOffpriceByOA(@Param("oaid")String oaid);

    /**
     * 通过oa号查询特价单
     * @param oaid
     * @return
     */
    public Offprice selectOffPriceByOaid(@Param("oaid")String oaid);
    /**
     * 根据参数获取特价调整单列表
     * @param map
     * @return
     * @author zhanghonghui 
     * @date 2015年8月31日
     */
    public List getOffpriceListBy(Map map);
    
	/**
	 * 更新打印次数
	 * @param offprice
	 * @return
	 * @author zhanghonghui 
	 * @date 2015-05-22
	 */
	public int updateOrderPrinttimes(Offprice offprice);

    /**
     * 获取导出的特价调整单列表数据
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date 2015-10-06
     */
    public List<OffpriceExcel> getOffpriceExcelList(PageMap pageMap);

    /**
     * 获取截止日期为当前日期的特价单
     * @author lin_xx
     * @param today
     * @date 2016-5-27
     * @return
     */
    public List<Offprice> getOffpriceListByDate(String today);

}