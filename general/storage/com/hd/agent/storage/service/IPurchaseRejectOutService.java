/**
 * @(#)IPurchaseRejectOutService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 19, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.PurchaseRejectOut;
import com.hd.agent.storage.model.PurchaseRejectOutDetail;

/**
 * 
 * 采购退货出库单service
 * @author chenwei
 */
public interface IPurchaseRejectOutService {
	/**
	 * 采购退货出库单添加
	 * @param purchaseRejectOut
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 20, 2013
	 */
	public Map addPurchaseRejectOut(PurchaseRejectOut purchaseRejectOut,List<PurchaseRejectOutDetail> detailList) throws Exception;
	/**
	 * 获取采购退货出库单详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 20, 2013
	 */
	public Map getPurchaseRejectOut(String id) throws Exception;
	/**
	 * 获取采购退货出库列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 20, 2013
	 */
	public PageData showPurchaseRejectOutList(PageMap pageMap) throws Exception;
	/**
	 * 采购退货出库单修改
	 * @param purchaseRejectOut
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	public Map editPurchaseRejectOut(PurchaseRejectOut purchaseRejectOut,List<PurchaseRejectOutDetail> detailList) throws Exception;
	/**
	 * 获取采购退货出库单明细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	public PurchaseRejectOutDetail getPurchaseRejectOutDetailInfo(String id) throws Exception;
	/**
	 * 删除采购退货出库单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	public boolean deletePurchaseRejectOut(String id) throws Exception;
	/**
	 * 采购退货出库单审核
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	public Map auditPurchaseRejectOut(String id) throws Exception;

	/**
	 * 采购退货出库单反审
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	public Map oppauditPurchaseRejectOut(String id) throws Exception;
	/**
	 * 采购退货出库单提交工作流
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 25, 2013
	 */
	public boolean submitPurchaseRejectOutProcess(String id) throws Exception;
	
	/**
	 * 获取采购退货出库单列表
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-12-28
	 */
	public List getPurchaseRejectOutListBy(Map map) throws Exception;

	/**
	 * 更新凭证生成次数
	 * @throws
	 * @author lin_xx
	 * @date 2017-11-30
	 */
	public boolean updateVouchertimes(PurchaseRejectOut purchaseRejectOut) throws Exception;
	/**
	 * 更新打印次数
	 * @param
	 * @author zhanghonghui 
	 * @date 2013-9-10
	 */
	public boolean updateOrderPrinttimes(PurchaseRejectOut purchaseRejectOut) throws Exception;
	/**
	 * 更新打印次数
	 * @param list
	 * @author zhanghonghui 
	 * @date 2013-9-10
	 */
	public void updateOrderPrinttimes(List<PurchaseRejectOut> list) throws Exception;
	/**
	 * 获取采购退货出库信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-1-10
	 */
	public PurchaseRejectOut getPurchaseRejectOutPureInfo(String id) throws Exception;
	/**
	 * 根据上游单据编号，获取采购退货入库编号列表
	 * @param sourceid
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年1月13日
	 */
	public List showPurchaseRejectOutIdListBySourceid(String sourceid) throws Exception;

    /**
     * 根据采购退货单编号 获取供应商的汇总金额
     * @param  idarr
     * @return
     * @author lin_xx
     * @date 2016年7月7日
     */
    public List getSupplierRejectSumData(List idarr) throws Exception;

	/**
	 * 根据采购退货单编号 获取供应商的汇总金额,按单据和商品汇总
	 * @param  idarr
	 * @return
	 * @author lin_xx
	 * @date 2016年7月7日
	 */
	public List getSupplierRejectSumDataForThird(List idarr) throws Exception;
}

