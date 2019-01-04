package com.hd.agent.storage.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.PurchaseEnter;
import com.hd.agent.storage.model.PurchaseEnterDetail;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 采购入库单dao
 * @author chenwei
 */
public interface PurchaseEnterMapper {
	/**
	 * 添加采购入库单明细
	 * @param purchaseEnterDetail
	 * @return
	 * @author chenwei 
	 * @date Jun 10, 2013
	 */
	public int addPurchaseEnterDetail(PurchaseEnterDetail purchaseEnterDetail);
	/**
	 * 添加采购入库单基本信息
	 * @param purchaseEnter
	 * @return
	 * @author chenwei 
	 * @date Jun 10, 2013
	 */
	public int addPurchaseEnter(PurchaseEnter purchaseEnter);
	/**
	 * 获取采购入库单基本信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 13, 2013
	 */
	public PurchaseEnter getPurchaseEnterInfo(@Param("id")String id);
	/**
	 * 获取采购入库单明细数据
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Oct 15, 2013
	 */
	public PurchaseEnterDetail getPurchaseEnterDetail(@Param("id")String id);
	/**
	 * 根据采购入库单编号获取采购入库单明细列表
	 * @param purchaseEnterid
	 * @return
	 * @author chenwei 
	 * @date Jun 13, 2013
	 */
	public List getPurchaseEnterDetailList(@Param("purchaseEnterid")String purchaseEnterid);
	/**
	 * 根据采购订单编号获取审核通过的采购入库单明细列表
	 * @param buyorderid
	 * @return
	 * @author chenwei 
	 * @date Jun 17, 2013
	 */
	public List getPurchaseEnterDetailSumAuditList(@Param("buyorderid")String buyorderid);
	/**
	 * 根根采购订单编号 获取采购入库单商品数量合计列表
	 * @param buyorderid
	 * @return
	 * @author chenwei 
	 * @date Jun 15, 2013
	 */
	public List getPurchaseEnterDetailSumGoodsidList(@Param("buyorderid")String buyorderid);
	/**
	 * 获取采购入库单列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jun 13, 2013
	 */
	public List showPurchaseEnterList(PageMap pageMap);
	/**
	 * 获取采购入库单数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jun 13, 2013
	 */
	public int showPurchaseEnterCount(PageMap pageMap);
	/**
	 * 根据采购入库单编号 删除采购入库单明细
	 * @param purchaseEnterid
	 * @return
	 * @author chenwei 
	 * @date Jun 14, 2013
	 */
	public int deletePurchaseEnterDetail(@Param("purchaseEnterid")String purchaseEnterid);
	/**
	 * 修改采购入库单信息
	 * @param purchaseEnter
	 * @return
	 * @author chenwei 
	 * @date Jun 14, 2013
	 */
	public int updatePurchaseEnter(PurchaseEnter purchaseEnter);
	/**
	 * 删除采购入库单
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 14, 2013
	 */
	public int deletePurchaseEnter(@Param("id")String id);
	/**
	 * 采购入库单审核
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date Jun 14, 2013
	 */
	public int auditPurchaseEnter(@Param("id")String id,@Param("userid")String userid,@Param("username")String username,@Param("businessdate")String businessdate);
	/**
	 * 采购入库单反审
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date Jun 14, 2013
	 */
	public int oppauditPurchaseEnter(@Param("id")String id,@Param("userid")String userid,@Param("username")String username);
	/**
	 * 更新采购入库单参照状态
	 * @param isrefer
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 14, 2013
	 */
	public int updatePurchaseEnterRefer(@Param("isrefer")String isrefer,@Param("id")String id);
	/**
	 * 关闭采购入库单
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 14, 2013
	 */
	public int closePurchaseEnter(@Param("id")String id);
	/**
	 * 打开采购入库单
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 14, 2013
	 */
	public int openPurchaseEnter(@Param("id")String id);
	/**
	 * 根据来源单据编号 获取采购入库单列表
	 * @param sourceid
	 * @return
	 * @author chenwei 
	 * @date Jun 14, 2013
	 */
	public List getPurchaseEnterListBySourceid(@Param("sourceid")String sourceid);
	/**
	 * 更新采购入库单明细开票状态
	 * @param billid
	 * @param detailid
	 * @param isinvoice
	 * @return
	 * @author chenwei 
	 * @date Oct 15, 2013
	 */
	public int updatePurchaseEnterDetailIsinvoice(@Param("billid")String billid,@Param("detailid")String detailid,@Param("isinvoice")String isinvoice);
	/**
	 * 更新采购入库单明细核销状态
	 * @param billid
	 * @param detailid
	 * @param iswriteoff
	 * @return
	 * @author chenwei 
	 * @date Oct 15, 2013
	 */
	public int updatePurchaseEnterDetailIswriteoff(@Param("billid")String billid,@Param("detailid")String detailid,@Param("iswriteoff")String iswriteoff);
	/**
	 * 更新采购入库单明细数据
	 * @param purchaseEnterDetail
	 * @return
	 * @author chenwei 
	 * @date Oct 15, 2013
	 */
	public int updatePurchaseEnterDetail(PurchaseEnterDetail purchaseEnterDetail);

	/**
	 * 根据上游单据编号，获取采购入库编号列表
	 * @param sourceid
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年1月13日
	 */
	public List showPurchaseEnterIdListBySourceid(@Param("sourceid")String sourceid);

    /**
     * 更新采购入库单明细的核销状态
     * @param invoiceid
     * @return
     * @author panxiaoxiao
     * @date 2015-03-24
     */
    public int updatePurchaseEnterDetailUncancel(@Param("invoiceid")String invoiceid);
    
    /**
	 * 获取采购入库单列表<br/>
	 * map中参数:<br/>
	 * dataSql：数据权限<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * status: 表示状态3<br/>
	 * statusarr: 表示状态，类似 1,2,3<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2015-05-22
	 */
	public List showPurchaseEnterListBy(Map map);
	
	/**
	 * 更新打印次数
	 * @param purchaseEnter
	 * @return
	 * @author zhanghonghui 
	 * @date 2015-05-22
	 */
	public int updateOrderPrinttimes(PurchaseEnter purchaseEnter);
	/**
     * 获取未审核的采购入库单编号列表
     * @param buyorderid
     * @return
     * @throws Exception
     * @author zhanghonghui 
     * @date 2015年6月10日
     */
	public List getPuchaseEnterUnAuditIDList(@Param("buyorderid")String buyorderid);
	
	
	/**
	 * 代配送生成采购入库单
	 * @param purchaseEnter
	 * @return
	 * @author huangzhiqian
	 * @date 2015年11月19日
	 */
	public int addPurchaseEnterForDeliveyJob(PurchaseEnter purchaseEnter);

    /**
     * 更新采购入库明细 入库时成本价
     * @param id
     * @param goodsid
     * @param addcostprice
     * @return
     */
    public int updatePurchaseEnterDetailAddcostprice(@Param("id")String id,@Param("goodsid")String goodsid,@Param("addcostprice")BigDecimal addcostprice);

    /**
     * 为扫描枪提供
     * @param map
     * @return
     */
    public List getPurchaseEnterForScanList(Map map);
    
}