/**
 * @(#)StorageSaleServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Oct 11, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.sales.dao.RejectBillMapper;
import com.hd.agent.storage.dao.SaleRejectEnterMapper;
import com.hd.agent.storage.dao.SaleoutMapper;
import com.hd.agent.storage.model.SaleRejectEnter;
import com.hd.agent.storage.model.SaleRejectEnterDetail;
import com.hd.agent.storage.model.Saleout;
import com.hd.agent.storage.model.SaleoutDetail;
import com.hd.agent.storage.service.IStorageSaleService;

/**
 * 
 * 库存提供销售调用接口
 * @author chenwei
 */
public class StorageSaleServiceImpl implements IStorageSaleService {
	
	private SaleoutMapper saleoutMapper;
	
	private SaleRejectEnterMapper saleRejectEnterMapper;

	private RejectBillMapper salesRejectBillMapper;
	
	public SaleoutMapper getSaleoutMapper() {
		return saleoutMapper;
	}

	public void setSaleoutMapper(SaleoutMapper saleoutMapper) {
		this.saleoutMapper = saleoutMapper;
	}

	public SaleRejectEnterMapper getSaleRejectEnterMapper() {
		return saleRejectEnterMapper;
	}

	public RejectBillMapper getSalesRejectBillMapper() {
		return salesRejectBillMapper;
	}

	public void setSalesRejectBillMapper(RejectBillMapper salesRejectBillMapper) {
		this.salesRejectBillMapper = salesRejectBillMapper;
	}

	public void setSaleRejectEnterMapper(SaleRejectEnterMapper saleRejectEnterMapper) {
		this.saleRejectEnterMapper = saleRejectEnterMapper;
	}
	@Override
	public boolean updateSaleOutDetailIsinvoiceBySalesInvoiceid(
			String invoiceid, String invoice,String invoicedate) throws Exception {
		boolean flag = true;
		//更新发货出库单明细的开票状态
		saleoutMapper.updateSaleOutDetailIsinvoice(invoiceid,invoice,invoicedate);
		//更新退货入库单（直退）明细的开票状态
		saleRejectEnterMapper.updateSaleRejectEnterDetailIsinvoiceByInvoiceid(invoiceid,invoice,invoicedate);
		//更新退货通知单（直退）明细的开票状态
		salesRejectBillMapper.updateRejectBillDetailIsinvoiceByInvoiceid(invoiceid,invoice);
		return flag;
	}
	
	@Override
	public boolean updateSaleOutDetailIsinvoicebillBySalesInvoicebillid(
			String invoicebillid, String isinvoicebill,String invoicebilldate) throws Exception {
		boolean flag = true;
		//更新发货出库单是否实际开票
		saleoutMapper.updateSaleOutDetailIsinvoicebill(invoicebillid, isinvoicebill,invoicebilldate);
		//更新直退是否实际开票
		saleRejectEnterMapper.updateSaleRejectEnterDetailIsinvoicebillByInvoicebillid(invoicebillid, isinvoicebill,invoicebilldate);
		//更新退货通知单（直退）明细的是否实际开票
		salesRejectBillMapper.updateRejectBillDetailIsinvoicebillByInvoicebillid(invoicebillid, isinvoicebill);
		return flag;
	}

	@Override
	public boolean updateSaleOutInvoiceAmount(String receiptid)
			throws Exception {
		String invoiceids = "";
		//根据回单编号（退货通知单编号） 获取相关的销售发票编号列表
		List<Map> invoiceList = saleoutMapper.getSaleInvoiceListByReceiptid(receiptid);
		for(Map map : invoiceList){
			String invoiceno = (String) map.get("invoiceno");
			if(null==invoiceno || "".equals(invoiceno)){
				invoiceno = (String) map.get("id");
			}
			if("".equals(invoiceids)){
				invoiceids = invoiceno;
			}else{
				invoiceids += ","+invoiceno;
			}
		}
		//获取回单是否有直退
		//更新退货入库单申请金额（直退）
		List<SaleRejectEnter> salerejectenterList = saleRejectEnterMapper.getErectSaleRejectEnterByReceiptid(receiptid);
		if(null!=salerejectenterList && salerejectenterList.size()>0){
			for(SaleRejectEnter saleRejectEnter : salerejectenterList){
				saleRejectEnter.setInvoicedate(CommonUtils.getTodayDataStr());
				SaleRejectEnterDetail saleRejectEnterDetail = saleRejectEnterMapper.getSaleRejectEnterSumInvoiceAmount(saleRejectEnter.getId());
				//更新开票金额
				if(null!=saleRejectEnterDetail && null!=invoiceList && invoiceList.size()>0){
//					saleRejectEnter.setInvoiceid(invoiceids);
					saleRejectEnter.setInvoiceamount(saleRejectEnterDetail.getTaxamount());
					saleRejectEnter.setInvoicenotaxamount(saleRejectEnterDetail.getNotaxamount());
					saleRejectEnterMapper.editSaleRejectEnter(saleRejectEnter);
				}else{
//					saleRejectEnter.setInvoiceid(invoiceids);
					saleRejectEnter.setInvoiceamount(BigDecimal.ZERO);
					saleRejectEnter.setInvoicenotaxamount(BigDecimal.ZERO);
					saleRejectEnter.setInvoicedate("");
					saleRejectEnterMapper.editSaleRejectEnter(saleRejectEnter);
				}
			}
		}
		List<Saleout> list = saleoutMapper.getSaleOutListByReceiptid(receiptid);
		for(Saleout saleout : list){
			saleout.setInvoicedate(CommonUtils.getTodayDataStr());
			SaleoutDetail saleoutDetail = saleoutMapper.getSaleOutSumInvoiceAmountBySaleoutid(saleout.getId());
			//更新开票金额
			if(null!=saleoutDetail && null!=invoiceList && invoiceList.size()>0){
//				saleout.setInvoiceid(invoiceids);
				saleout.setInvoiceamount(saleoutDetail.getRealtaxamount());
				saleout.setInvoicenotaxamount(saleoutDetail.getRealnotaxamount());
				saleoutMapper.editSaleOut(saleout);
			}else{
//				saleout.setInvoiceid(invoiceids);
				saleout.setInvoiceamount(BigDecimal.ZERO);
				saleout.setInvoicenotaxamount(BigDecimal.ZERO);
				saleout.setInvoicedate("");
				saleoutMapper.editSaleOut(saleout);
			}
		}
		return true;
	}
	
	@Override
	public boolean updateSaleOutInvoicebillAmount(String receiptid)
			throws Exception {
		boolean flag = true;
		String invoiceids = "";
		//根据回单编号 获取相关的销售发票编号列表
		List<Map> invoiceList = saleoutMapper.getSaleInvoiceBillListByReceiptid(receiptid);
		for(Map map : invoiceList){
			String invoiceno = (String) map.get("invoiceno");
			if(null==invoiceno || "".equals(invoiceno)){
				invoiceno = (String) map.get("id");
			}
			if("".equals(invoiceids)){
				invoiceids = invoiceno;
			}else{
				invoiceids += ","+invoiceno;
			}
		}
		List<Saleout> list = saleoutMapper.getSaleOutListByReceiptid(receiptid);
		for(Saleout saleout : list){
			saleout.setInvoicebilldate(CommonUtils.getTodayDataStr());
			SaleoutDetail saleoutDetail = saleoutMapper.getSaleOutSumInvoicebillAmountBySaleoutid(saleout.getId());
			//更新开票金额
			if(null!=saleoutDetail && null!=invoiceList && invoiceList.size()>0){
				saleout.setInvoiceid(invoiceids);
				saleout.setInvoicebillamount(saleoutDetail.getRealtaxamount());
				saleout.setInvoicebillnotaxamount(saleoutDetail.getRealnotaxamount());
				boolean flag1 = saleoutMapper.editSaleOut(saleout) > 0;
				flag = flag && flag1;
			}else{
				saleout.setInvoiceid(invoiceids);
				saleout.setInvoicebillamount(BigDecimal.ZERO);
				saleout.setInvoicebillnotaxamount(BigDecimal.ZERO);
				saleout.setInvoicebilldate("");
				boolean flag1 = saleoutMapper.editSaleOut(saleout) > 0;
				flag = flag && flag1;
			}
		}
		//获取回单是否有直退
		//更新退货入库单申请金额（直退）
		List<SaleRejectEnter> salerejectenterList = saleRejectEnterMapper.getErectSaleRejectEnterByReceiptid(receiptid);
		if(null!=salerejectenterList && salerejectenterList.size()>0){
			for(SaleRejectEnter saleRejectEnter : salerejectenterList){
				saleRejectEnter.setInvoicebilldate(CommonUtils.getTodayDataStr());
				SaleRejectEnterDetail saleRejectEnterDetail = saleRejectEnterMapper.getSaleRejectEnterSumInvoicebillAmount(saleRejectEnter.getId());
				//更新开票金额
				if(null!=saleRejectEnterDetail && null!=invoiceList && invoiceList.size()>0){
					saleRejectEnter.setInvoiceid(invoiceids);
//					saleRejectEnter.setInvoicebillamount(saleRejectEnterDetail.getTaxamount());
//					saleRejectEnter.setInvoicebillnotaxamount(saleRejectEnterDetail.getNotaxamount());
					saleRejectEnterMapper.editSaleRejectEnter(saleRejectEnter);
				}else{
					saleRejectEnter.setInvoiceid(invoiceids);
					saleRejectEnter.setInvoiceamount(BigDecimal.ZERO);
					saleRejectEnter.setInvoicenotaxamount(BigDecimal.ZERO);
					saleRejectEnter.setInvoicedate("");
					saleRejectEnterMapper.editSaleRejectEnter(saleRejectEnter);
				}
			}
		}
		return flag;
	}

	@Override
	public boolean updateSaleOutInvoicebilldate(String receiptid,String hasinvoicedate,String invoicebilldate) throws Exception {
		boolean flag = true;
		List<Saleout> list = saleoutMapper.getSaleOutListByReceiptid(receiptid);
		for(Saleout saleout : list){
			if("1".equals(hasinvoicedate)){
				saleout.setInvoicebilldate(invoicebilldate);
			}else if("0".equals(hasinvoicedate)){
				saleout.setInvoicebilldate("");
			}
			boolean flag2 = saleoutMapper.editSaleOut(saleout) > 0;
			flag = flag && flag2;
		}
		return flag;
	}

	@Override
	public boolean updateSaleOutWriteOffAmount(String receiptid)
			throws Exception {
		//获取回单是否有直退
		//退货入库单（直退）
		List<SaleRejectEnter> salerejectenterList = saleRejectEnterMapper.getErectSaleRejectEnterByReceiptid(receiptid);
		for(SaleRejectEnter saleRejectEnter : salerejectenterList){
			SaleRejectEnterDetail saleRejectEnterDetail = saleRejectEnterMapper.getSaleRejectEnterSumWriteoffAmount(saleRejectEnter.getId());
			//更新开票金额
			if(null!=saleRejectEnterDetail){
				saleRejectEnter.setWriteoffdate(CommonUtils.getTodayDataStr());
//				saleRejectEnter.setWriteoffamount(saleRejectEnterDetail.getTaxamount());
//				saleRejectEnter.setWriteoffnotaxamount(saleRejectEnterDetail.getNotaxamount());
				saleRejectEnterMapper.editSaleRejectEnter(saleRejectEnter);
			}
		}
		List<Saleout> list = saleoutMapper.getSaleOutListByReceiptid(receiptid);
		for(Saleout saleout : list){
			SaleoutDetail saleoutDetail = saleoutMapper.getSaleOutSumWriteoffAmountBySaleoutid(saleout.getId());
			//更新开票金额
			if(null!=saleoutDetail){
				saleout.setWriteoffdate(CommonUtils.getTodayDataStr());
				saleout.setWriteoffamount(saleoutDetail.getRealtaxamount());
				saleout.setWriteoffnotaxamount(saleoutDetail.getRealnotaxamount());
				//判断发货单是否核销完
				if(saleout.getSendamount().compareTo(saleoutDetail.getTaxamount())==0){
					saleout.setIswrite("1");
				}
				saleoutMapper.editSaleOut(saleout);
			}
		}
		return true;
	}
	
	@Override
	public boolean clearSaleOutWriteOffAmount(String receiptid)
			throws Exception {
		//获取回单是否有直退
		//退货入库单（直退）
		List<SaleRejectEnter> salerejectenterList = saleRejectEnterMapper.getErectSaleRejectEnterByReceiptid(receiptid);
		for(SaleRejectEnter saleRejectEnter : salerejectenterList){
			SaleRejectEnterDetail saleRejectEnterDetail = saleRejectEnterMapper.getSaleRejectEnterSumWriteoffAmount(saleRejectEnter.getId());
			//更新核销金额
			if(null!=saleRejectEnterDetail){
				saleRejectEnter.setWriteoffamount(saleRejectEnterDetail.getTaxamount());
				saleRejectEnter.setWriteoffnotaxamount(saleRejectEnterDetail.getNotaxamount());
			}else{
				saleRejectEnter.setWriteoffdate("");
				saleRejectEnter.setWriteoffamount(BigDecimal.ZERO);
				saleRejectEnter.setWriteoffnotaxamount(BigDecimal.ZERO);
				saleRejectEnter.setIswrite("0");
			}
			saleRejectEnterMapper.editSaleRejectEnter(saleRejectEnter);
		}
		List<Saleout> list = saleoutMapper.getSaleOutListByReceiptid(receiptid);
		for(Saleout saleout : list){
			SaleoutDetail saleoutDetail = saleoutMapper.getSaleOutSumWriteoffAmountBySaleoutid(saleout.getId());
			if(null != saleoutDetail){
				saleout.setWriteoffamount(saleoutDetail.getRealtaxamount());
				saleout.setWriteoffnotaxamount(saleoutDetail.getRealnotaxamount());
				//判断发货单是否核销完
				if(saleout.getSendamount().compareTo(saleoutDetail.getTaxamount())!=0){
                    saleout.setWriteoffdate("");
					saleout.setIswrite("0");
				}
			}else{
                saleout.setWriteoffdate("");
				saleout.setWriteoffamount(BigDecimal.ZERO);
				saleout.setWriteoffnotaxamount(BigDecimal.ZERO);
                saleout.setIswrite("0");
			}
			saleoutMapper.editSaleOut(saleout);
		}
		return true;
	}

	@Override
	public boolean updateSaleOutDetailIswriteoffByReceipt(String invoiceid,String writeoffdate) throws Exception {
		//更新发货出库单明细的核销状态
		int i = saleoutMapper.updateSaleOutDetailIswriteoff(invoiceid,writeoffdate);
		//更新退货入库单（直退）明细的核销状态
		saleRejectEnterMapper.updateSaleRejectEnterDetailIsWriteoffByReceipt(invoiceid,writeoffdate);
		//更新退货通知单（直退）明细的核销状态
		salesRejectBillMapper.updateRejectBillDetailIsWriteoffByReceipt(invoiceid,writeoffdate);
		return i>0;
	}
	@Override
	public boolean updateSaleOutDetailBackWriteoffByReceipt(String invoiceid)
			throws Exception {
		//更新发货出库单明细的核销状态（反核销）
		int i = saleoutMapper.updateSaleOutDetailBackWriteoff(invoiceid);
		//更新退货入库单（直退）明细的核销状态（反核销）
		saleRejectEnterMapper.updateSaleRejectEnterDetailBackWriteoffByReceipt(invoiceid);
		//更新退货通知单（直退）明细的核销状态（反核销）
		salesRejectBillMapper.updateRejectBillDetailBackWriteoffByReceipt(invoiceid);
		return i > 0;
	}

	@Override
	public boolean clearSaleoutInvoiceidByReceiptid(String receiptid) throws Exception {
		return saleoutMapper.clearSaleoutInvoiceidByReceiptid(receiptid) > 0;
	}

	@Override
	public boolean clearSaleRejectEnterInvoiceidByReceiptid(String receiptid)
			throws Exception {
		return saleRejectEnterMapper.clearSaleRejectEnterInvoiceidByReceiptid(receiptid) > 0;
	}
	
	@Override
	public boolean clearSaleRejectEnterInvoiceidByRejectbillid(
			String rejectbillid) throws Exception {
		return saleRejectEnterMapper.clearSaleRejectEnterInvoiceidByRejectbillid(rejectbillid) > 0;
	}

	@Override
	public List getSaleRejectEnterListByReceipt(String receiptid)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateRejectEnterDetailIswriteoffByReject(
			String invoiceid,String writeoffdate) throws Exception {
		int i = saleRejectEnterMapper.updateSaleRejectEnterDetailIsWriteoffBySalesInvoiceid(invoiceid,writeoffdate);
		return i>0;
	}
	
	@Override
	public boolean updateRejectEnterDetailIswriteoffByRejectInvoiceBill(
			String invoicebillid) throws Exception {
		int i = saleRejectEnterMapper.updateSaleRejectEnterDetailIsWriteoffBySalesInvoicebillid(invoicebillid);
		return i>0;
	}

	@Override
	public boolean updateRejectEnterDetailBackWriteoffByReject(String invoiceid)
			throws Exception {
		int i = saleRejectEnterMapper.updateSaleRejectEnterDetailBackWriteoffBySalesInvoiceid(invoiceid);
		return i > 0;
	}

	@Override
	public boolean updateRejectEnterDetailIsinvoiceBySalesInvoiceid(
			String invoiceid, String invoice,String invoicedate) throws Exception {
		int i = saleRejectEnterMapper.updateSaleRejectEnterDetailIsinvoiceByReject(invoiceid,invoice,invoicedate);
		return i>0;
	}
	
	@Override
	public boolean updateRejectEnterDetailIsinvoicebillBySalesInvoicebillid(
			String invoicebillid, String isinvoicebill,String invoicebilldate) throws Exception {
		int i = saleRejectEnterMapper.updateSaleRejectEnterDetailIsinvoicebillByReject(invoicebillid,isinvoicebill,invoicebilldate);
		return i > 0;
	}

	@Override
	public boolean updateRejectEnterInvoiceAmount(String salerejectid)
			throws Exception {
		List<SaleRejectEnter> list = saleRejectEnterMapper.getSaleRejectEnterListBySalerejectid(salerejectid);
		for(SaleRejectEnter saleRejectEnter : list){
			saleRejectEnter.setInvoicedate(CommonUtils.getTodayDataStr());
			SaleRejectEnterDetail saleRejectEnterDetail = saleRejectEnterMapper.getSaleRejectEnterSumInvoiceAmount(saleRejectEnter.getId());
			//根据回单编号（退货通知单编号） 获取相关的销售发票编号列表
			List<Map> invoiceList = saleoutMapper.getSaleInvoiceListByReceiptid(salerejectid);
			String invoiceids = "";
			for(Map map : invoiceList){
				String invoiceno = (String) map.get("invoiceno");
				if(null==invoiceno || "".equals(invoiceno)){
					invoiceno = (String) map.get("id");
				}
				if("".equals(invoiceids)){
					invoiceids = invoiceno;
				}else{
					invoiceids += ","+invoiceno;
				}
			}
			//更新开票金额
			if(null!=saleRejectEnterDetail && null!=invoiceList && invoiceList.size()>0){
//				saleRejectEnter.setInvoiceid(invoiceids);
				saleRejectEnter.setInvoiceamount(saleRejectEnterDetail.getTaxamount());
				saleRejectEnter.setInvoicenotaxamount(saleRejectEnterDetail.getNotaxamount());
				saleRejectEnterMapper.editSaleRejectEnter(saleRejectEnter);
			}else{
//				saleRejectEnter.setInvoiceid(invoiceids);
				saleRejectEnter.setInvoiceamount(BigDecimal.ZERO);
				saleRejectEnter.setInvoicenotaxamount(BigDecimal.ZERO);
				saleRejectEnter.setInvoicedate("");
				saleRejectEnterMapper.editSaleRejectEnter(saleRejectEnter);
			}
		}
		return true;
	}

	@Override
	public boolean updateRejectEnterInvoicebillAmount(String salerejectid)
			throws Exception {
		boolean flag = true;
		List<SaleRejectEnter> list = saleRejectEnterMapper.getSaleRejectEnterListBySalerejectid(salerejectid);
		for(SaleRejectEnter saleRejectEnter : list){
			saleRejectEnter.setInvoicebilldate(CommonUtils.getTodayDataStr());
			SaleRejectEnterDetail saleRejectEnterDetail = saleRejectEnterMapper.getSaleRejectEnterSumInvoicebillAmount(saleRejectEnter.getId());
			//根据回单编号（退货通知单编号） 获取相关的销售发票编号列表
			List<Map> invoiceList = saleoutMapper.getSaleInvoiceBillListByReceiptid(salerejectid);
			String invoiceids = "";
			for(Map map : invoiceList){
				String invoiceno = (String) map.get("invoiceno");
				if(null==invoiceno || "".equals(invoiceno)){
					invoiceno = (String) map.get("id");
				}
				if("".equals(invoiceids)){
					invoiceids = invoiceno;
				}else{
					invoiceids += ","+invoiceno;
				}
			}
			//更新开票金额
			if(null!=saleRejectEnterDetail && null!=invoiceList && invoiceList.size()>0){
				saleRejectEnter.setInvoiceid(invoiceids);
				saleRejectEnter.setInvoicebillamount(saleRejectEnterDetail.getTaxamount());
				saleRejectEnter.setInvoicebillnotaxamount(saleRejectEnterDetail.getNotaxamount());
				boolean flag1 = saleRejectEnterMapper.editSaleRejectEnter(saleRejectEnter) > 0;
				flag = flag && flag1;
			}else{
				saleRejectEnter.setInvoiceid(invoiceids);
				saleRejectEnter.setInvoicebillamount(BigDecimal.ZERO);
				saleRejectEnter.setInvoicebillnotaxamount(BigDecimal.ZERO);
				saleRejectEnter.setInvoicebilldate("");
				boolean flag1 = saleRejectEnterMapper.editSaleRejectEnter(saleRejectEnter) > 0;
				flag = flag && flag1;
			}
		}
		return flag;
	}

	@Override
	public boolean updateRejectEnterInvoicebilldate(String salerejectid,String hasinvoicedate,String invoicebilldate) throws Exception {
		boolean flag = true;
		List<SaleRejectEnter> list = saleRejectEnterMapper.getSaleRejectEnterListBySalerejectid(salerejectid);
		for(SaleRejectEnter saleRejectEnter : list){
			if("1".equals(hasinvoicedate)){
				saleRejectEnter.setInvoicebilldate(invoicebilldate);
			}else if("0".equals(hasinvoicedate)){
				saleRejectEnter.setInvoicebilldate("");
			}
			boolean flag2 = saleRejectEnterMapper.editSaleRejectEnter(saleRejectEnter) > 0;
			flag = flag && flag2;
		}
		return flag;
	}

	@Override
	public boolean updateRejectEnterWriteoffAmount(String salerejectid)
			throws Exception {
		List<SaleRejectEnter> list = saleRejectEnterMapper.getSaleRejectEnterListBySalerejectid(salerejectid);
		for(SaleRejectEnter saleRejectEnter : list){
//			saleRejectEnter.setInvoicedate(CommonUtils.getTodayDataStr());
			SaleRejectEnterDetail saleRejectEnterDetail = saleRejectEnterMapper.getSaleRejectEnterSumWriteoffAmount(saleRejectEnter.getId());
			//更新开票金额
			if(null!=saleRejectEnterDetail){
				saleRejectEnter.setWriteoffdate(CommonUtils.getTodayDataStr());
				saleRejectEnter.setWriteoffamount(saleRejectEnterDetail.getTaxamount());
				saleRejectEnter.setWriteoffnotaxamount(saleRejectEnterDetail.getNotaxamount());
				//判断退货入库单是否核销完
				if(saleRejectEnter.getSendamount().compareTo(saleRejectEnterDetail.getTaxamount())==0){
					saleRejectEnter.setIswrite("1");
				}
				saleRejectEnterMapper.editSaleRejectEnter(saleRejectEnter);
			}
		}
		return true;
	}
	
	@Override
	public boolean clearRejectEnterWriteoffAmount(String salerejectid)
			throws Exception {
		List<SaleRejectEnter> list = saleRejectEnterMapper.getSaleRejectEnterListBySalerejectid(salerejectid);
		for(SaleRejectEnter saleRejectEnter : list){
			SaleRejectEnterDetail saleRejectEnterDetail = saleRejectEnterMapper.getSaleRejectEnterSumWriteoffAmount(saleRejectEnter.getId());
			if(null != saleRejectEnterDetail){
				saleRejectEnter.setWriteoffamount(saleRejectEnterDetail.getTaxamount());
				saleRejectEnter.setWriteoffnotaxamount(saleRejectEnterDetail.getNotaxamount());
				//判断退货入库单是否核销完
				if(saleRejectEnter.getSendamount().compareTo(saleRejectEnterDetail.getTaxamount())!=0){
                    saleRejectEnter.setWriteoffdate("");
					saleRejectEnter.setIswrite("0");
				}
			}else{
                saleRejectEnter.setWriteoffdate("");
				saleRejectEnter.setWriteoffamount(BigDecimal.ZERO);
				saleRejectEnter.setWriteoffnotaxamount(BigDecimal.ZERO);
                saleRejectEnter.setIswrite("0");
			}
			saleRejectEnterMapper.editSaleRejectEnter(saleRejectEnter);
		}
		return true;
	}

	@Override
	public boolean updateRejectEnterWriteoffByReceipt(String receiptid)
			throws Exception {
		//退货入库单核销状态
		int i = saleRejectEnterMapper.updateRejectEnterWriteoffByReceipt(receiptid);
		//退货通知单核销状态
		salesRejectBillMapper.updateRejectBillWriteoffByReceipt(receiptid);
		return i>0;
	}

	@Override
	public boolean updateRejectEnterBackWriteoffByReceipt(String receiptid)
			throws Exception {
		int i = saleRejectEnterMapper.updateRejectEnterBackWriteoffByReceipt(receiptid);
		//发货通知单核销状态
		salesRejectBillMapper.updateRejectBillWriteoffByReceipt(receiptid);
		return i>0;
	}

}

