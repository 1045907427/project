/**
 * @(#)IDistributeService.java
 *
 * @author wanghongteng
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年8月4日 wanghongteng 创建版本
 */
package com.hd.agent.delivery.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.delivery.model.DeliveryAogorder;
import com.hd.agent.delivery.model.DeliveryAogorderDetail;
import com.hd.agent.delivery.model.DeliveryAogreturn;
import com.hd.agent.delivery.model.DeliveryAogreturnDetail;
import com.hd.agent.delivery.model.DeliveryOrder;
import com.hd.agent.delivery.model.DeliveryOrderDetail;
import com.hd.agent.delivery.model.DeliveryRejectbill;
import com.hd.agent.delivery.model.DeliveryRejectbillDetail;
import com.hd.agent.delivery.model.ExportDeliveryAogorder;
import com.hd.agent.delivery.model.ExportDeliveryAogreturn;
import com.hd.agent.delivery.model.ExportDeliveryOrder;
import com.hd.agent.delivery.model.ExportDeliveryRejectbill;
import com.hd.agent.sales.model.ModelOrder;
import com.hd.agent.storage.model.StorageSummaryBatch;

/**
 * 
 * 
 * @author wanghongteng
 */
/**
 * @author Administrator
 *
 */
public interface IDistributeService {
	/**
	 * 获取到货订单列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 7, 2015
	 */
	PageData showDeliveryAogorderList(PageMap pageMap) throws Exception;
	/**
	 * 到货订单添加
	 * @param deliveryAogorder
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 7, 2015
	 */
	public Map addDeliveryAogorder(DeliveryAogorder deliveryAogorder,List<DeliveryAogorderDetail> detailList) throws Exception;
	/**
	 * 到货订单审核
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 7, 2015
	 */
	public Map auditDeliveryAogorder(String id) throws Exception;
	/**
	 * 到货订单反审
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 7, 2015
	 */
	public Map oppauditDeliveryAogorder(String id) throws Exception;
	/**
	 * 通过ID获取到货订单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 10, 2015
	 */
    public Map getDeliveryAogorderInfo(String id) throws Exception;
    /**
	 * 删除到货订单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 10, 2015
	 */
    public boolean deleteDeliveryAogorder(String id) throws Exception;
    /**
	 * 保存修改的到货订单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 10, 2015
	 */
    public Map editDeliveryAogorder(DeliveryAogorder deliveryAogorder,List<DeliveryAogorderDetail> detailList) throws Exception;
    /**
	 * 获取到货订单列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 7, 2015
	 */
    PageData showDeliveryAogreturnList(PageMap pageMap) throws Exception;
    /**
	 * 退货订单添加
	 * @param deliveryAogreturn
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 7, 2015
	 */
	public Map addDeliveryAogreturn(DeliveryAogreturn deliveryAogreturn,List<DeliveryAogreturnDetail> detailList) throws Exception;
	/**
	 * 退货订单审核
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 7, 2015
	 */
	public Map auditDeliveryAogreturn(String id) throws Exception;
	/**
	 * 通过ID获取退货订单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 10, 2015
	 */
    public Map getDeliveryAogreturnInfo(String id) throws Exception;
    /**
	 * 退货订单反审
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 7, 2015
	 */
	public Map oppauditDeliveryAogreturn(String id) throws Exception;
	/**
	 * 保存修改的退货订单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 10, 2015
	 */
    public Map editDeliveryAogreturn(DeliveryAogreturn deliveryAogreturn,List<DeliveryAogreturnDetail> detailList) throws Exception;
    /**
   	 * 删除退货订单
   	 * @param id
   	 * @return
   	 * @throws Exception
   	 * @author wanghongteng 
   	 * @date Aug 10, 2015
   	 */
       public boolean deleteDeliveryAogreturn(String id) throws Exception;
   	/**
   	 * 获取客户订单列表
   	 * @param pageMap
   	 * @return
   	 * @throws Exception
   	 * @author wanghongteng 
   	 * @date Aug 7, 2015
   	 */
   	PageData showDeliveryOrderList(PageMap pageMap) throws Exception;
   	/**
	 * 通过ID获取客户订单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 10, 2015
	 */
    public Map getDeliveryOrderInfo(String id) throws Exception;
    /**
	 * 客户订单添加
	 * @param deliveryOrder
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 7, 2015
	 */
	public Map addDeliveryOrder(DeliveryOrder deliveryOrder,List<DeliveryOrderDetail> detailList) throws Exception;
	/**
	 * 客户订单审核
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date Aug 7, 2015
	 */
	public Map auditDeliveryOrder(String id) throws Exception;
	   /**
		 * 保存修改的客户订单
		 * @param id
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 10, 2015
		 */
	 public Map editDeliveryOrder(DeliveryOrder deliveryOrder,List<DeliveryOrderDetail> detailList) throws Exception;
	 /**
		 * 删除客户订单
		 * @param id
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 10, 2015
		 */
	    public boolean deleteDeliveryOrder(String id) throws Exception;
		/**
		 * 客户订单反审
		 * @param id
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 7, 2015
		 */
		public Map oppauditDeliveryOrder(String id) throws Exception;
		
		
		/**
	   	 * 获取客户退货订单列表
	   	 * @param pageMap
	   	 * @return
	   	 * @throws Exception
	   	 * @author wanghongteng 
	   	 * @date Aug 7, 2015
	   	 */
	   	PageData showDeliveryRejectbillList(PageMap pageMap) throws Exception;
	    /**
		 * 客户退货订单添加
		 * @param deliveryOrder
		 * @param detailList
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 7, 2015
		 */
		public Map addDeliveryRejectbill(DeliveryRejectbill deliveryRejectbill,List<DeliveryRejectbillDetail> detailList) throws Exception;
		   /**
			 * 保存修改的客户退货订单
			 * @param id
			 * @return
			 * @throws Exception
			 * @author wanghongteng 
			 * @date Aug 10, 2015
			 */
		 public Map editDeliveryRejectbill(DeliveryRejectbill deliveryRejectbill,List<DeliveryRejectbillDetail> detailList) throws Exception;
		/**
		 * 客户退货订单审核
		 * @param id
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 7, 2015
		 */
		public Map auditDeliveryRejectbill(String id) throws Exception;
	  	/**
		 * 通过ID获取客户退货订单
		 * @param id
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 10, 2015
		 */
	    public Map getDeliveryRejectbillInfo(String id) throws Exception;
	    /**
		 * 删除客户退货订单
		 * @param id
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 10, 2015
		 */
	    public boolean deleteDeliveryRejectbill(String id) throws Exception;
	    /**
		 * 客户退货订单反审
		 * @param id
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 7, 2015
		 */
		public Map oppauditDeliveryRejectbill(String id) throws Exception;
		/**
		 * 到货订单审核后添加到供应商代配送入库单
		 * @param deliveryAogorder,detailList
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 24, 2015
		 */
		public boolean addDistributeRejectByAogorder(DeliveryAogorder deliveryAogorder,List detailList) throws Exception;
		/**
		 * 退货订单审核后添加到供应商代配送出库单
		 * @param deliveryAogreturn,detailList
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 24, 2015
		 */
		public Map<String,Object> addDistributeOutByAogreturn(DeliveryAogreturn deliveryAogreturn,List detailList) throws Exception;
		/**
		 * 客户订单审核后添加到供应商代配送出库单
		 * @param deliveryOrder,detailList
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 24, 2015
		 */
		public Map<String,Object> addDistributeOutByOrder(DeliveryOrder deliveryOrder,List detailList) throws Exception;
		/**
		 * 客户退货订单审核后添加到供应商代配送入库单
		 * @param deliveryRejectbill,detailList
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 24, 2015
		 */
		public boolean addDistributeRejectByRejectbill(DeliveryRejectbill deliveryRejectbill,List detailList) throws Exception;
		
		/**
		 * 入库单审核通过关闭到货订单
		 * @param id
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 24, 2015
		 */
		public boolean closeDeliveryAogorder(String id) throws Exception;
		/**
		 * 出库单审核通过关闭退货订单
		 * @param id
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 24, 2015
		 */
		public boolean closeDeliveryAogreturn(String id) throws Exception;
		/**
		 * 客户出库单审核通过关闭客户订单
		 * @param id
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 24, 2015
		 */
		public boolean closeDeliveryOrder(String id) throws Exception;
		/**
		 * 客户退货入库单审核通过关闭客户退货订单
		 * @param id
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 24, 2015
		 */
		public boolean closeDeliveryRejectbill(String id) throws Exception;
		/**
		 * 导出获取到货订单列表
		 * @param pageMap
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 24, 2015
		 */
		public List<ExportDeliveryAogorder> getAogorderList(PageMap pageMap) throws Exception ;
		/**
		 * 导出获取退货订单列表
		 * @param pageMap
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 24, 2015
		 */
		public List<ExportDeliveryAogreturn> getAogreturnList(PageMap pageMap) throws Exception ;
		/**
		 * 导出获取客户订单列表
		 * @param pageMap
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 24, 2015
		 */
		public List<ExportDeliveryOrder> getOrderList(PageMap pageMap) throws Exception ;
		/**
		 * 导出获取客户退货订单列表
		 * @param pageMap
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 24, 2015
		 */
		public List<ExportDeliveryRejectbill> getRejectbillList(PageMap pageMap) throws Exception ;
		/**
		 * 导入到货订单列表
		 * @param list
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 24, 2015
		 */
		public Map importAogorder(List<Map<String, Object>> list) throws Exception;

        /**
         * 根据导入的模板参数 获取代配送单明细
         * @param wareList
         * @return
         * @throws Exception
         */
        public Map changeModelForDetail(List<ModelOrder> wareList,String gtype) throws Exception;
		/**
		 * 导入退货订单列表
		 * @param list
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 24, 2015
		 */
		public Map importAogreturn(List<Map<String, Object>> list) throws Exception;
		/**
		 * 导入客户订单列表
		 * @param list
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 24, 2015
		 */
		public Map importOrder(List<Map<String, Object>> list) throws Exception;
		/**
		 * 导入客户退货订单列表
		 * @param list
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 24, 2015
		 */
		public Map importRejectbill(List<Map<String, Object>> list) throws Exception;
		/**
		 * 打印到货订单
		 * @param list
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 24, 2015
		 */
		public List printAogorder(Map map) throws Exception;
		/**
		 * 打印退货订单
		 * @param list
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 24, 2015
		 */
		public List printAogreturn(Map map) throws Exception;
		/**
		 * 打印客户订单
		 * @param list
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 24, 2015
		 */
		public List printOrder(Map map) throws Exception;
		/**
		 * 打印客户退货订单
		 * @param list
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 24, 2015
		 */
		public List printRejectbill(Map map) throws Exception;
		/**
		 * 更新到货订单打印次数
		 * @param deliveryAogorder
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 24, 2015
		 */
		public boolean updateAogorderPrinttimes(DeliveryAogorder deliveryAogorder) throws Exception;
		/**
		 * 更新退货订单打印次数
		 * @param deliveryAogorder
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 24, 2015
		 */
		public boolean updateAogreturnPrinttimes(DeliveryAogreturn deliveryAogreturn) throws Exception;
		/**
		 * 更新客户订单打印次数
		 * @param deliveryAogorder
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 24, 2015
		 */
		public boolean updateOrderPrinttimes(DeliveryOrder deliveryOrder) throws Exception;
		/**
		 * 更新客户退货订单打印次数
		 * @param deliveryAogorder
		 * @return
		 * @throws Exception
		 * @author wanghongteng 
		 * @date Aug 24, 2015
		 */
		public boolean updateRejectbillPrinttimes(DeliveryRejectbill deliveryRejectbill) throws Exception;
		/**
		 * 批次现存量
		 * @return
		 * @throws Exception
		 * @author huangzhiqian
		 * @param batchNo 
		 * @param goodsId 
		 * @param storageId 
		 * @date 2015年12月17日
		 */
		StorageSummaryBatch getStorageSummaryByStorageidAndGoodsidAndBatchId(String storageId, String goodsId, String batchNo)  throws Exception;
		/**
		 * 批次现存量
		 * @param storageid
		 * @param goodsid
		 * @return
		 * @author huangzhiqian
		 * @date 2015年12月17日
		 */
		StorageSummaryBatch getStorageSummaryByStoreIdAndGoodsIdService(String storageid, String goodsid)throws Exception ;
		
}

