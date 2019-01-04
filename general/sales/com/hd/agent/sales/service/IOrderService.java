/**
 * @(#)IOrderService.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 10, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.*;
import com.hd.agent.storage.model.StorageSummary;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author zhengziyong
 */
public interface IOrderService {

    /**
     * 添加销售订单
     * @param order
     * @param saveaudit
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date May 10, 2013
     */
    public Map addOrder(Order order,String saveaudit) throws Exception;

    /**
     * 添加销售订单（手机订单）
     * @param order
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Sep 3, 2013
     */
    public boolean addOrderForPhone(Order order) throws Exception;

    /**
     * 更新销售订单
     * @param order
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date May 10, 2013
     */
    public Map updateOrder(Order order,String saveaudit) throws Exception;

    /**
     * 更新订单状态
     * @param order 订单
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date May 16, 2013
     */
    public boolean updateOrderStatus(Order order) throws Exception;

    /**
     * 更新订单参照状态
     * @param isrefer
     * @param id 订单编号
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date May 28, 2013
     */
    public boolean updateOrderRefer(String isrefer, String id) throws Exception;

    /**
     * 删除订单信息，并删除订单下的商品明细信息
     * @param id 订单编码
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date May 17, 2013
     */
    public boolean deleteOrder(String id) throws Exception;

    /**
     * 作废或取消作废订单
     * @param id
     * @param type 1作废2取消作废
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Dec 26, 2013
     */
    public Map doInvalidOrder(String id, String type) throws Exception;

    /**
     * 批量作废订单
     * @param ids
     * @return
     * @throws Exception
     */
    public Map doBatchInvalidOrder(String ids)throws Exception;

    /**
     * 获取销售订单详细信息包括订单商品明细
     * @param id
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date May 10, 2013
     */
    public Order getOrder(String id) throws Exception;
    /**
     * 获取销售订单详细信息包括订单商品明细(用来销售订单复制)
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年7月1日
     */
    public Order getOrderByCopy(String id) throws Exception;
    /**
     * 获取销售订单详细信息不包括订单商品明细
     * @param id
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date May 17, 2013
     */
    public Order getOnlyOrder(String id) throws Exception;

    /**
     * 通过订单编码获取商品明细列表
     * @param id 订单编码
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date May 20, 2013
     */
    public List getDetailListOrder(String id) throws Exception;

    /**
     * 获取销售订单列表信息
     * @param pageMap
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date May 10, 2013
     */
    public PageData getOrderData(PageMap pageMap) throws Exception;

    /**
     * 添加销售订单明细信息
     * @param detail
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date May 11, 2013
     */
    public boolean addOrderDetail(OrderDetail detail) throws Exception;

    /**
     * 通过商品编号获取商品原价详细信息，包括价格套信息、税种信息、计量单位信息
     * @param goodsId 商品编码
     * @param customerId 客户编码，用于获取客户对应商品价格套数据
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date May 14, 2013
     */
    public OrderDetail getFixGoodsDetail(String goodsId, String customerId) throws Exception;


    /**
     * 通过商品编号获取商品详细信息，包括价格套信息、税种信息、计量单位信息
     * @param goodsId 商品编码
     * @param customerId 客户编码，用于获取客户对应商品价格套数据
     * @param businessDate 业务日期，用于计算特价
     * @param type 			reject表示退货
     * @param num 订单数据
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date May 14, 2013
     */
    public OrderDetail getGoodsDetail(String goodsId, String customerId, String businessDate, BigDecimal num, String type) throws Exception;

    /**
     * 审核或反审销售订单
     * @param type 1为审核2为反审
     * @param id 订单编号
     * @param model model为supper时，表示超级审核 不验证信用额度与账期
     * @return flag：审核或反审结果，
     * @throws Exception
     * @author zhengziyong
     * @date May 30, 2013
     */
    public Map auditOrder(String type, String id,String model) throws Exception;

    /**
     * 批量审核
     * @param ids 审核订单编号字符串，各订单编号以,隔开
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Aug 5, 2013
     */
    public Map auditMultiOrder(String ids) throws Exception;

    /**
     * 提交销售订单到工作流
     * @param title
     * @param userId
     * @param processDefinitionKey
     * @param businessKey
     * @param variables
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Jul 2, 2013
     */
    public boolean submitOrderProcess(String title, String userId, String processDefinitionKey, String businessKey, Map<String, Object> variables) throws Exception;

    /**
     * 导入销售订单
     * @param list
     * @return
     * @throws Exception
     * @author chenwei
     * @date Sep 26, 2013
     */
    public Map addDRSalesOrder(List<ImportSalesOrder> list) throws Exception;
    /**
     * 验证销售订单是否可以审核
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date Apr 26, 2014
     */
    public Map checkOrderAudit(String id) throws Exception;
    /**
     * 判断库存数量是否足够 是否需要配置库存
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年6月23日
     */
    public Map orderDeployInfo(String id) throws Exception;
    /**
     * 显示销售订单库存配置信息
     * @param id
     * @param barcodeflag
     * @param deploy            商品指定替换列表
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年6月23日
     */
    public Order getOrderDeployInfo(String id,String barcodeflag,String deploy) throws Exception;
    /**
     * 获取销售订单列表<br/>
     * map中参数<br/>
     * idarrs :编号字符串组<br/>
     * status : 状态<br/>
     * statusarr : 状态字符串组<br/>
     * notphprint : 配货未打印<br/>
     * notprint : 发货未打印<br/>
     * @param map
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-8-9
     */
    public List getSalesOrderListBy(Map map) throws Exception;
    /**
     * 根据缺货商品报表 补货
     * @param ids
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年12月25日
     */
    public Map addOrderByGoodsout(String ids) throws Exception;

    /**
     * 根据促销分组编号 获取促销详细信息
     * @param groupid
     * @return
     * @throws Exception
     */
    public PromotionPackageGroup getOrderGoodsPromotionDetailInfo(String groupid) throws Exception;

    /**
     * 根据订单编号获取分组编号 获取促销信息
     * @param orderid
     * @param groupid
     * @return
     * @throws Exception
     */
    public PromotionPackageGroup getOrderGoodsPromotionDetailInfoByOrderid(String orderid,String groupid) throws Exception;

    /**
     * 根据商品编码和仓库编号 获取商品库存信息
     * @param goodsid
     * @param storageid
     * @return
     * @throws Exception
     */
    public StorageSummary getStorageSummarySumByGoodsidAndStorageid(String goodsid,String storageid) throws Exception;

    /**
     * 根据商品编码获取商品库存信息
     * @param goodsid
     * @return
     * @throws Exception
     */
    public StorageSummary getStorageSummarySumByGoodsid(String goodsid) throws Exception;

    /**
     * 根据订单编号获取订单的历史修改记录（版本信息）
     * @param id
     * @return
     * @throws Exception
     */
    public List showOrderVersionList(String id) throws Exception;

    /**
     * 获取订单指定版本详细信息
     * @param id
     * @param version
     * @return
     * @throws Exception
     */
    public Order getOrderVersion(String id,String version) throws Exception;

    /**
     * 根据客户编号和商品编号 获取该商品是否符合满赠条件
     * @param customerid		客户编号
     * @param goodsid			商品编号
     * @param num				下单数量
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2015年9月16日
     */
    public boolean getFullFreeDetailByCustomeridAndGoodsid(String orderid,String customerid,String goodsid,BigDecimal num) throws Exception;
    /**
     * 根据客户编号和商品编号 获取满赠明细列表
     * @param orderid
     * @param customerid
     * @param goodsid
     * @param num
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2015年9月16日
     */
    public List getFullFreeListPage(String orderid,String customerid,String goodsid,BigDecimal num) throws Exception;

    /**
     *模板导入订单数据
     * @return
     * @throws Exception
     * @author lin_xx
     * @date July 6, 2015
     */
    public Map addMDSalesOrder(List<ModelOrder> wareList,String ctype,String gtype)throws Exception;

     /**
     * 销售订单总金额合计
     * @param id
     * @return
     * @throws Exception
     * @author zhanghonghui 
     * @date 2015年10月19日
     */
    public Map getOrderDetailTotal(String id) throws Exception;


	/**
	 * 获取批量添加的商品
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 2015-12-2
	 */
    public PageData getGoodsByBrandAndSort(PageMap pageMap,String customerid,String storageid,String date,String brands,String defaultsorts) throws Exception;
    /**
	 * 添加批量商品明细
	* 
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 2015-12-2
	 */
    public List AddOrderDetailByBrandAndSort(List<OrderDetail>  orderDetailList)throws Exception ;
    /**
     * 检测特价
     * @param 
     * @param 
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2015-12-7
     */
    public Map checkOffprice(String goodsid,String customerid,String date,BigDecimal num)throws Exception ;
    
    /**
     * 客户应收款、余额<br/>
     * 返回结果<br/>
     * receivableAmount:客户应收款<br/>
     * leftAmount : 余额<br/>
     * @param customerid
     * @return
     * @author zhanghonghui
     * @date 2015年11月12日
     */
    public Map showCustomerReceivableInfoData(String customerid) throws Exception;
    /**
     * 获取客户单据号
     * @param id
     * @return
     * @throws Exception
     * @author zhanghonghui 
     * @date 2015年12月28日
     */
    public String getCustomerBillId(String id) throws Exception;

    /**
     * 更改销售订单业务日期
     * @param order
     * @return
     * @throws Exception
     */
    public boolean updateOrderDate(Order order) throws Exception ;
    /**
     * 单纯获取订单信息
     * @param id
     * @return
     * @throws Exception
     * @author zhanghonghui 
     * @date 2016年4月11日
     */
    public Order getPureOrder(String id) throws Exception;
    
    /**
	 * 更新打印次数
	 * @param order
	 * @author zhanghonghui 
	 * @date 2013-9-10
	 */
	public boolean updateOrderPrinttimes(Order order) throws Exception;

    /**
     * 销售订单中修改商品合同价
     * @author lin_xx
     * @date 2016-5-10
     */
    public Map modifyGoodsContractPrice(String id , String goodsid ,String price) throws Exception ;

    /**
     * 根据来源单据编号查询销售订单
     *
     * @param sourceid
     * @param sourcetype
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 23, 2016
     */
    public Order getOrderBySourceid(String sourceid, String sourcetype) throws Exception;
     /**
      * 根据客户及商品编号获取客户商品最近三次销售价（销售订单内审核通过的）
      * @author lin_xx
      * @date 2017/3/14
      */
     public List getCustomerGoodsLastThreePrice(String customerid,String goodsid) throws Exception;

    /**
     * 更新销售订单的打印和配送状态
     * @throws Exception
     * @author wanghongteng
     * @date 2017-6-6
     */
    public void doUpdateOrderStatus()throws Exception;
}

