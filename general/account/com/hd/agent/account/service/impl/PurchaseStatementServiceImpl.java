/**
 * @(#)PurchaseStatementServiceImpl.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 17, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.account.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.PayorderMapper;
import com.hd.agent.account.dao.PurchaseInvoiceMapper;
import com.hd.agent.account.dao.PurchaseInvoicePushMapper;
import com.hd.agent.account.dao.PurchaseStatementMapper;
import com.hd.agent.account.dao.SupplierCapitalMapper;
import com.hd.agent.account.model.PurchaseInvoice;
import com.hd.agent.account.model.PurchaseInvoiceDetail;
import com.hd.agent.account.model.PurchaseInvoicePush;
import com.hd.agent.account.model.PurchaseStatement;
import com.hd.agent.account.model.SupplierCapital;
import com.hd.agent.account.model.SupplierCapitalLog;
import com.hd.agent.account.service.IPurchaseStatementService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.purchase.dao.ArrivalOrderMapper;
import com.hd.agent.purchase.dao.ReturnOrderMapper;
import com.hd.agent.purchase.service.IArrivalOrderService;
import com.hd.agent.purchase.service.ext.IPurchaseForAccountService;

/**
 * 
 * 采购发票核销，对账单service实现类
 * @author panxiaoxiao
 */
public class PurchaseStatementServiceImpl extends BaseAccountServiceImpl implements IPurchaseStatementService{

	/**
	 * 采购发票付款单核销dao
	 */
	private PurchaseStatementMapper purchaseStatementMapper;
	
	/**
	 * 付款单dao
	 */
	private PayorderMapper payorderMapper;
	
	/**
	 * 采购发票dao
	 */
	private PurchaseInvoiceMapper purchaseInvoiceMapper;
	
	/**
	 * 采购模块对应采购发票模块service
	 */
	private IPurchaseForAccountService purchaseForAccountService;
	
	private ArrivalOrderMapper arrivalOrderMapper;

    private IArrivalOrderService arrivalOrderService;
	
	private ReturnOrderMapper returnOrderMapper;
	/**
	 * 采购发票冲差单dao
	 */
	private PurchaseInvoicePushMapper purchaseInvoicePushMapper;
	
	private SupplierCapitalMapper supplierCapitalMapper;

    public IArrivalOrderService getArrivalOrderService() {
        return arrivalOrderService;
    }

    public void setArrivalOrderService(IArrivalOrderService arrivalOrderService) {
        this.arrivalOrderService = arrivalOrderService;
    }

    public SupplierCapitalMapper getSupplierCapitalMapper() {
		return supplierCapitalMapper;
	}

	public void setSupplierCapitalMapper(SupplierCapitalMapper supplierCapitalMapper) {
		this.supplierCapitalMapper = supplierCapitalMapper;
	}

	public ArrivalOrderMapper getArrivalOrderMapper() {
		return arrivalOrderMapper;
	}

	public void setArrivalOrderMapper(ArrivalOrderMapper arrivalOrderMapper) {
		this.arrivalOrderMapper = arrivalOrderMapper;
	}

	public ReturnOrderMapper getReturnOrderMapper() {
		return returnOrderMapper;
	}

	public void setReturnOrderMapper(ReturnOrderMapper returnOrderMapper) {
		this.returnOrderMapper = returnOrderMapper;
	}

	public PurchaseStatementMapper getPurchaseStatementMapper() {
		return purchaseStatementMapper;
	}

	public void setPurchaseStatementMapper(
			PurchaseStatementMapper purchaseStatementMapper) {
		this.purchaseStatementMapper = purchaseStatementMapper;
	}

	public PayorderMapper getPayorderMapper() {
		return payorderMapper;
	}

	public void setPayorderMapper(PayorderMapper payorderMapper) {
		this.payorderMapper = payorderMapper;
	}

	public PurchaseInvoiceMapper getPurchaseInvoiceMapper() {
		return purchaseInvoiceMapper;
	}

	public void setPurchaseInvoiceMapper(PurchaseInvoiceMapper purchaseInvoiceMapper) {
		this.purchaseInvoiceMapper = purchaseInvoiceMapper;
	}

	public IPurchaseForAccountService getPurchaseForAccountService() {
		return purchaseForAccountService;
	}

	public void setPurchaseForAccountService(
			IPurchaseForAccountService purchaseForAccountService) {
		this.purchaseForAccountService = purchaseForAccountService;
	}
	
	public PurchaseInvoicePushMapper getPurchaseInvoicePushMapper() {
		return purchaseInvoicePushMapper;
	}

	public void setPurchaseInvoicePushMapper(
			PurchaseInvoicePushMapper purchaseInvoicePushMapper) {
		this.purchaseInvoicePushMapper = purchaseInvoicePushMapper;
	}

	@Override
	public Map auditWriteoffPayorder(String supplierid,List<PurchaseStatement> purchaseStatementList) throws Exception {
		boolean flag = false;
		Map map = new HashMap();
		SupplierCapital supplierCapital = supplierCapitalMapper.getSupplierCapital(supplierid);
		if(null != supplierCapital){
			SysUser sysUser = getSysUser();
			if(null != supplierCapital){
				BigDecimal totalWriteoffAmount = new BigDecimal(0);
				if(purchaseStatementList.size() != 0){
					for(PurchaseStatement purchaseStatement : purchaseStatementList){
						//采购发票详情
						PurchaseInvoice purchaseInvoice = purchaseInvoiceMapper.getPurchaseInvoiceInfo(purchaseStatement.getBillid());
						if(null != purchaseInvoice && "3".equals(purchaseInvoice.getStatus()) && "0".equals(purchaseInvoice.getIswriteoff())){//审核通过
							BigDecimal writeoffamount = (purchaseStatement.getWriteoffamount()!=null)?purchaseStatement.getWriteoffamount():(new BigDecimal(0));
							BigDecimal tailamount = (purchaseStatement.getTailamount()!=null)?purchaseStatement.getTailamount():(new BigDecimal(0));
							String writeoffdate = CommonUtils.getTodayDataStr();
							//采购发票核销
							purchaseInvoiceMapper.writeOffPurchaseInvoice(purchaseStatement.getBillid(), writeoffamount, tailamount, writeoffdate);
							//回写采购发票来源单据的数据
							writeBackPurchaseInvoiceSource(purchaseInvoice);
							//采购发票 有冲差金额时 核销后 更新采购发票冲差单状态
							if(purchaseInvoice.getTaxamount().compareTo(purchaseInvoice.getInvoiceamount())!=0){
								purchaseInvoicePushMapper.updatePurchaseInvoiceIswriteoff(purchaseInvoice.getId());
							}
							//总核销金额
							totalWriteoffAmount = totalWriteoffAmount.add(writeoffamount);
							//生成对账单
							purchaseStatement.setSupplierid(supplierid);
							purchaseStatement.setBusinessdate(writeoffdate);
							purchaseStatement.setBilltype("1");
							purchaseStatement.setAmount(writeoffamount);
							purchaseStatement.setBillamount(purchaseInvoice.getInvoiceamount());
							purchaseStatement.setAdduserid(sysUser.getUserid());
							purchaseStatement.setAddusername(sysUser.getName());
							
							purchaseStatementMapper.addPurchaseStatement(purchaseStatement);
							
							//更新供应商客户资金情况
							purchaseInvoice.setWriteoffamount(writeoffamount);
							updateSupplierCapital(purchaseInvoice);
						}
						else if(null==purchaseInvoice){
							PurchaseInvoicePush purchaseInvoicePush = purchaseInvoicePushMapper.showPurchaseInvoicePushInfo(purchaseStatement.getBillid());
							if(null != purchaseInvoicePush){
								//总核销金额
								totalWriteoffAmount = totalWriteoffAmount.add(purchaseInvoicePush.getAmount());
								//生成对账单
								purchaseStatement.setSupplierid(supplierid);
								purchaseStatement.setBusinessdate(CommonUtils.getTodayDataStr());
								purchaseStatement.setBilltype("3");
								purchaseStatement.setBillid(purchaseInvoicePush.getId());
								purchaseStatement.setAmount(purchaseInvoicePush.getAmount());
								purchaseStatement.setBillamount(purchaseInvoicePush.getAmount());
								purchaseStatement.setAdduserid(sysUser.getUserid());
								purchaseStatement.setAddusername(sysUser.getName());
								
								purchaseStatementMapper.addPurchaseStatement(purchaseStatement);
								purchaseInvoicePushMapper.closePurchaseInvoicePush(purchaseStatement.getBillid());
								
								updateSupplierCapital(purchaseInvoicePush);
							}
						}
					}
					flag = true;
				}
			}
		}else{
			map.put("msg", "不存在该供应商编码:"+supplierid+"付款单,请新增一张!");
		}
		map.put("flag", flag);
		return map;
	}

    @Override
    public boolean uncancelPurchaseInvoice(String invoiceid) throws Exception {
        boolean flag = false;
        PurchaseInvoice purchaseInvoice = purchaseInvoiceMapper.getPurchaseInvoiceInfo(invoiceid);
        if(null != purchaseInvoice && "4".equals(purchaseInvoice.getStatus())){
            //核销金额
            BigDecimal writeoffamount = purchaseInvoice.getWriteoffamount();
            //更新供应商客户资金情况
            purchaseInvoice.setWriteoffamount(writeoffamount);
            updateSupplierCapitalBack(purchaseInvoice);
            //删除核销生成的对账单
            purchaseStatementMapper.deletePurchaseStatement(purchaseInvoice.getSupplierid(),purchaseInvoice.getId(),"1");
            //采购发票 有冲差金额时 反核销后 更新采购发票冲差单状态
            purchaseInvoicePushMapper.updatePurchaseInvoiceUnIswriteoff(purchaseInvoice.getId());
            //回写采购发票来源单据的数据
            writeBackPurchaseInvoiceSourceCaseUncancel(purchaseInvoice);
            //采购发票反核销
            purchaseInvoiceMapper.uncancelPurchaseInvoice(invoiceid);
            flag = true;
        }
        return flag;
    }

	@Override
	public List showPurchaseStatementList(String orderid) throws Exception {
		List list = purchaseStatementMapper.getPurchaseStatementList(orderid);
		return list;
	}
	
	@Override
	public PageData showPurchaseStatementData(PageMap pageMap) throws Exception {
		List list = purchaseStatementMapper.getPurchaseStatementListPage(pageMap);
		PageData pageData = new PageData(purchaseStatementMapper.getPurchaseStatementCount(pageMap),list,pageMap);
		return pageData;
	}

	/**
	 * 发票核销后，回写采购发票来源单据的相关信息
	 * @param purchaseInvoice
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 17, 2013
	 */
	public boolean writeBackPurchaseInvoiceSource(PurchaseInvoice purchaseInvoice)throws Exception{
		String canceldate = CommonUtils.getTodayDataStr();
		boolean flag = false;
		if(null != purchaseInvoice){
			String sourceid = purchaseInvoice.getSourceid();
			if(StringUtils.isNotEmpty(sourceid)){
				String[] idArr = sourceid.split(",");
				for(String id : idArr){
					List<PurchaseInvoiceDetail> list1 = purchaseInvoiceMapper.getPurchaseInvoiceDetailListBySourceid(id);
					if(list1.size() != 0){
						String sourcetype = list1.get(0).getSourcetype();
						//进货单
						if("1".equals(sourcetype)){
							List<PurchaseInvoiceDetail> list= purchaseInvoiceMapper.getPurchaseInvoiceSumBySourceidGroup(id);
							purchaseForAccountService.updateArrivalOrderWriteBack(id, list,canceldate);
						}
						//退货通知单
						else if("2".equals(sourcetype)){
							List<PurchaseInvoiceDetail> list= purchaseInvoiceMapper.getPurchaseInvoiceSumBySourceidGroup(id);
							purchaseForAccountService.updateReturnOrderWriteBack(id,list,canceldate);
						}else if("3".equals(sourcetype)){
							List<PurchaseInvoiceDetail> list= purchaseInvoiceMapper.getPurchaseInvoiceSumBySourceidGroup(id);
							purchaseForAccountService.updateBeginDueWriteBack(id,list,canceldate,"1");
						}
					}
				}
				flag = true;
			}
		}
		return flag;
	}

    /**
     * 发票反核销后，回写采购发票来源单据的相关信息
     * @param purchaseInvoice
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-24
     */
    private boolean writeBackPurchaseInvoiceSourceCaseUncancel(PurchaseInvoice purchaseInvoice)throws Exception{
        boolean flag = false;
        if(null != purchaseInvoice){
            List sourceidList = purchaseInvoiceMapper.getPurchaseInvoiceSouceidList(purchaseInvoice.getId());
            arrivalOrderService.updateArrivalAndPurchaseEnterUncancel(sourceidList,purchaseInvoice);
            flag = true;
        }
        return flag;
    }
	/**
	 * 更新供应商资金情况
	 * @param purchaseInvoice
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 17, 2013
	 */
	public boolean updateSupplierCapital(PurchaseInvoice purchaseInvoice)throws Exception{
		SupplierCapital supplierCapital = supplierCapitalMapper.getSupplierCapital(purchaseInvoice.getSupplierid());
		SysUser sysUser = getSysUser();
		//流水明细
		SupplierCapitalLog supplierCapitalLog = new SupplierCapitalLog();
		supplierCapitalLog.setSupplierid(purchaseInvoice.getSupplierid());
		supplierCapitalLog.setBillid(purchaseInvoice.getId());
		supplierCapitalLog.setBilltype("2");
		supplierCapitalLog.setPrtype("2");
		supplierCapitalLog.setPayamount(purchaseInvoice.getWriteoffamount());
		supplierCapitalLog.setBalanceamount(supplierCapital.getAmount().subtract(purchaseInvoice.getWriteoffamount()));
		supplierCapitalLog.setAdddeptid(sysUser.getDepartmentid());
		supplierCapitalLog.setAdddeptname(sysUser.getDepartmentname());
		supplierCapitalLog.setAdduserid(sysUser.getUserid());
		supplierCapitalLog.setAddusername(sysUser.getName());
		if(StringUtils.isNotEmpty(purchaseInvoice.getRemark())){
			purchaseInvoice.setRemark(purchaseInvoice.getRemark()+",核销");
		}else {
			purchaseInvoice.setRemark("核销");
		}
		supplierCapitalLog.setRemark(purchaseInvoice.getRemark());
		int i = supplierCapitalMapper.addSupplierCapitalLog(supplierCapitalLog);
		supplierCapital.setAmount(supplierCapital.getAmount().subtract(purchaseInvoice.getWriteoffamount()));
		int j = supplierCapitalMapper.updateSupplierCapital(supplierCapital);
		return i>0&&j>0;
	}

    /**
     * 根据采购发票 更新供应商资金情况（反核销）
     * @param purchaseInvoice
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-24
     */
    private boolean updateSupplierCapitalBack(PurchaseInvoice purchaseInvoice)throws Exception{
        SupplierCapital supplierCapital = supplierCapitalMapper.getSupplierCapital(purchaseInvoice.getSupplierid());
        SysUser sysUser = getSysUser();
        //流水明细
        SupplierCapitalLog supplierCapitalLog = new SupplierCapitalLog();
        supplierCapitalLog.setSupplierid(purchaseInvoice.getSupplierid());
        supplierCapitalLog.setBillid(purchaseInvoice.getId());
        supplierCapitalLog.setBilltype("2");
        supplierCapitalLog.setPrtype("5");
        supplierCapitalLog.setPayamount(purchaseInvoice.getWriteoffamount().negate());
        supplierCapitalLog.setBalanceamount(supplierCapital.getAmount().add(purchaseInvoice.getWriteoffamount()));
        supplierCapitalLog.setAdddeptid(sysUser.getDepartmentid());
        supplierCapitalLog.setAdddeptname(sysUser.getDepartmentname());
        supplierCapitalLog.setAdduserid(sysUser.getUserid());
        supplierCapitalLog.setAddusername(sysUser.getName());
		if(StringUtils.isNotEmpty(purchaseInvoice.getRemark())){
			purchaseInvoice.setRemark(purchaseInvoice.getRemark()+",反核销");
		}else {
			purchaseInvoice.setRemark("反核销");
		}
		supplierCapitalLog.setRemark(purchaseInvoice.getRemark());
        int i = supplierCapitalMapper.addSupplierCapitalLog(supplierCapitalLog);

        supplierCapital.setAmount(supplierCapital.getAmount().add(purchaseInvoice.getWriteoffamount()));
        int j = supplierCapitalMapper.updateSupplierCapital(supplierCapital);
        return i>0&&j>0;
    }
	
	/**
	 * 根据冲差单 更新供应商资金情况
	 * @param purchaseInvoicePush
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 13, 2013
	 */
	public boolean updateSupplierCapital(PurchaseInvoicePush purchaseInvoicePush) throws Exception{
		SupplierCapital supplierCapital = supplierCapitalMapper.getSupplierCapital(purchaseInvoicePush.getSupplierid());
		SysUser sysUser = getSysUser();
		//流水明细
		SupplierCapitalLog supplierCapitalLog = new SupplierCapitalLog();
		supplierCapitalLog.setSupplierid(purchaseInvoicePush.getSupplierid());
		supplierCapitalLog.setBillid(purchaseInvoicePush.getId());
		supplierCapitalLog.setBilltype("4");
		supplierCapitalLog.setPrtype("2");
		supplierCapitalLog.setPayamount(purchaseInvoicePush.getAmount());
		supplierCapitalLog.setBalanceamount(supplierCapital.getAmount().subtract(purchaseInvoicePush.getAmount()));
		supplierCapitalLog.setAdddeptid(sysUser.getDepartmentid());
		supplierCapitalLog.setAdddeptname(sysUser.getDepartmentname());
		supplierCapitalLog.setAdduserid(sysUser.getUserid());
		supplierCapitalLog.setAddusername(sysUser.getName());
		if(StringUtils.isNotEmpty(purchaseInvoicePush.getRemark())){
			purchaseInvoicePush.setRemark(purchaseInvoicePush.getRemark()+",反核销");
		}else {
			purchaseInvoicePush.setRemark("反核销");
		}
		supplierCapitalLog.setRemark(purchaseInvoicePush.getRemark());
		int i = supplierCapitalMapper.addSupplierCapitalLog(supplierCapitalLog);
		supplierCapital.setAmount(supplierCapital.getAmount().subtract(purchaseInvoicePush.getAmount()));
		int j = supplierCapitalMapper.updateSupplierCapital(supplierCapital);
		return i>0&&j>0;
	}
}

