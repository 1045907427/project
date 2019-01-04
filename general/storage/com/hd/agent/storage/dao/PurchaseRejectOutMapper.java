package com.hd.agent.storage.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.PurchaseRejectOut;
import com.hd.agent.storage.model.PurchaseRejectOutDetail;

/**
 * 采购退货出库单dao
 * @author chenwei
 */
public interface PurchaseRejectOutMapper {
	/**
	 * 采购退货出库单添加
	 * @param purchaseRejectOut
	 * @return
	 * @author chenwei 
	 * @date Jun 20, 2013
	 */
	public int addPurchaseRejectOut(PurchaseRejectOut purchaseRejectOut);
	/**
	 * 采购退货出库单明细添加
	 * @param purchaseRejectOutDetail
	 * @return
	 * @author chenwei 
	 * @date Jun 20, 2013
	 */
	public int addPurchaseRejectOutDetail(PurchaseRejectOutDetail purchaseRejectOutDetail);
	/**
	 * 获取采购退货出库单信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 20, 2013
	 */
	public PurchaseRejectOut getPurchaseRejectOutByID(@Param("id")String id);
	/**
	 * 根据来源单据编号获取采购退货出库单
	 * @param sourceid
	 * @return
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	public PurchaseRejectOut getPurchaseRejectOutBySourceid(@Param("sourceid")String sourceid);
	/**
	 * 获取采购退货出库单明细列表
	 * @param orderid
	 * @return
	 * @author chenwei 
	 * @date Jun 20, 2013
	 */
	public List<PurchaseRejectOutDetail> getPurchaseRejectOutByOrderid(@Param("orderid")String orderid);
	/**
	 * 获取采购退货出库单列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jun 20, 2013
	 */
	public List showPurchaseRejectOutList(PageMap pageMap);
	/**
	 * 获取采购退货出库单数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jun 20, 2013
	 */
	public int showPurchaseRejectOutCount(PageMap pageMap);
	/**
	 * 根据采购退货出库编号 获取采购退货出库金额
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Mar 1, 2014
	 */
	public Map getPurchaseRejectOutAmount(@Param("id")String id);
	
	/**
	 * 合计采购退货出库单
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao
	 */
	public Map getPurchaseRejectOutTotalAmount(PageMap pageMap);
	/**
	 * 获取采购退货出库单明细详细信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	public PurchaseRejectOutDetail getPurchaseRejectOutDetailByID(String id);
	/**
	 * 获取采购退货出库单明细详细信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	public PurchaseRejectOutDetail getPurchaseRejectOutDetailBySource(@Param("billno")String billno,@Param("billdetailno")String billdetailno);
	/**
	 * 根据采购退货出库单编号 删除明细
	 * @param orderid
	 * @return
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	public int deletePurchaseRejectOutDetailByOrderid(@Param("orderid")String orderid);
	/**
	 * 采购退货出库单修改
	 * @param purchaseRejectOut
	 * @return
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	public int editPurchaseRejectOut(PurchaseRejectOut purchaseRejectOut);
	/**
	 * 删除采购退货单
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	public int deletePurchaseRejectOut(@Param("id")String id);
	/**
	 * 审核采购退货单 
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	public int auditPurchaseRejectOut(@Param("id")String id,@Param("userid")String userid,@Param("username")String username,@Param("businessdate")String businessdate);
	
	/**
	 * 审核采购退货单 反审
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	public int oppauditPurchaseRejectOut(@Param("id")String id,@Param("userid")String userid,@Param("username")String username,@Param("businessdate")String businessdate);
	/**
	 * 更新采购退货出库单明细开票状态
	 * @param billno
	 * @param billdetailno
	 * @param isinvoice
	 * @return
	 * @author chenwei 
	 * @date Oct 15, 2013
	 */
	public int updatePurchaseRejectDetailIsinvoice(@Param("billno")String billno,@Param("billdetailno")String billdetailno,@Param("isinvoice")String isinvoice);
	/**
	 * 更新采购退货出库单明细核销状态
	 * @param billno
	 * @param billdetailno
	 * @param iswriteoff
	 * @return
	 * @author chenwei 
	 * @date Oct 15, 2013
	 */
	public int updatePurchaseRejectDetailIswriteoff(@Param("billno")String billno,@Param("billdetailno")String billdetailno,@Param("iswriteoff")String iswriteoff);
	/**
	 * 获取采购退货出库单列表
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-12-28
	 */
	public List getPurchaseRejectOutListBy(Map map);
	/**
	 * 更新采购退货入库单列表
	 * @param purchaseRejectOut
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-12-28
	 */
	public int updateOrderPrinttimes(PurchaseRejectOut purchaseRejectOut);
	/**
	 * 根据上游单据编号，获取采购退货入库编号列表
	 * @param sourceid
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年1月13日
	 */
	public List showPurchaseRejectOutIdListBySourceid(@Param("sourceid")String sourceid);

    /**
     * 更新采购退货出库单明细的核销状态
     * @param invoiceid
     * @return
     * @author panxiaoxiao
     * @date 2015-03-24
     */
    public int updatePurchaseRejectDetailUncancel(@Param("invoiceid")String invoiceid);
    /**
     * 根据MAP中参数更新采购退货出库单明细<br/>
     * orderDetail: 需要更新的出库单明细信息<br/>
     * id: 出库单明细编号<br/>
     * orderid : 出库单编号<br/>
     * billno ： 上游单据编号 <br/>
     * billdetailno : 上游单据明细编号<br/>
     * @param map
     * @return
     * @author zhanghonghui 
     * @date 2015年6月19日
     */
    public int updatePurchaseRejectOutDetailBy(Map map);
    /**
     * 根据MAP中参数更新采购退货出库单<br/>
     * rejectOut: 需要更新的出库单信息<br/>
     * id : 出库单编号 <br/>
     * sourceid : 上游单据编号 <br/>
     * sourcetype : 上游单据类型<br/>
     * ischeck : 是否验收<br/>
     * @param map
     * @author zhanghonghui 
     * @date 2015年6月19日
     */
    public int updatePurchaseRejectOutBy(Map map);
    /**
     * 根据MAP中参数获取采购退货出库单<br/>
     * orderid : 出库单编号<br/>
     * billno ： 上游单据编号 <br/>
     * @param map
     * @return
     * @author zhanghonghui 
     * @date 2015年6月29日
     */
    public List getPurchaseRejectOutDetailByMap(Map map);
    /**
     * 代配送生成采购退货出库单
     * @param purchaseRejectOut
     * @return
     * @author huangzhiqian
     * @date 2015年9月30日
     */
	public int addPurchaseRejectOutForDeliveryJob(PurchaseRejectOut purchaseRejectOut);

    /**
     * 更新采购入库单 入库时的成本价
     * @param id
     * @param goodsid
     * @param addcostprice
     * @return
     */
    public int updatePurchaseRejectOutDetailAddcostprice(@Param("id")String id,@Param("goodsid")String goodsid,@Param("addcostprice")BigDecimal addcostprice);

    /**
     *根据退货出库单编号数组 获取供应商的合计信息
     * @param idarr
     * @return
     */
    public List<Map> getSupplierRejectSumData(List idarr) ;

	/**
	 *根据退货出库单编号数组 获取供应商的合计信息,按单据和商品汇总
	 * @param idarr
	 * @return
	 */
	public List<Map> getSupplierRejectSumDataForThird(List idarr) ;
}