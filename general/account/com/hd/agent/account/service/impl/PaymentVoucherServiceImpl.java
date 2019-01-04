package com.hd.agent.account.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.CollectionOrderMapper;
import com.hd.agent.account.dao.PaymentVoucherMapper;
import com.hd.agent.account.model.CollectionOrder;
import com.hd.agent.account.model.PaymentVoucher;
import com.hd.agent.account.model.PaymentVoucherDetail;
import com.hd.agent.account.service.IPaymentVoucherService;
import com.hd.agent.basefiles.model.Bank;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.*;

public class PaymentVoucherServiceImpl extends BaseFilesServiceImpl implements IPaymentVoucherService {
	
	private PaymentVoucherMapper paymentVoucherMapper;

	public PaymentVoucherMapper getPaymentVoucherMapper() {
		return paymentVoucherMapper;
	}

	public void setPaymentVoucherMapper(PaymentVoucherMapper paymentVoucherMapper) {
		this.paymentVoucherMapper = paymentVoucherMapper;
	}
	

	private CollectionOrderMapper collectionOrderMapper;
	
	public CollectionOrderMapper getCollectionOrderMapper() {
		return collectionOrderMapper;
	}

	public void setCollectionOrderMapper(CollectionOrderMapper collectionOrderMapper) {
		this.collectionOrderMapper = collectionOrderMapper;
	}

	@Override
	public boolean addPaymentVoucherAddDetail(PaymentVoucher paymentVoucher) throws Exception{
		if (isAutoCreate("t_account_paymentvoucher")) {
			// 获取自动编号
			paymentVoucher.setId( getAutoCreateSysNumbderForeign(paymentVoucher, "t_account_paymentvoucher"));
		}else{
			paymentVoucher.setId( "JKD-"+CommonUtils.getDataNumberSendsWithRand());
		}
		if(StringUtils.isEmpty(paymentVoucher.getId())){
			return false;
		}
		boolean flag=insertPaymentVoucher(paymentVoucher);
		List<PaymentVoucherDetail> list=paymentVoucher.getPaymentVoucherDetailList();
		BigDecimal totalamount=BigDecimal.ZERO;
		if(flag && list!=null && list.size()>0){
			int iseq=1;
			for(PaymentVoucherDetail item : list){			
				
				item.setOrderid(paymentVoucher.getId());
				item.setSeq(iseq);
				if(null!=item.getAmount()){
					totalamount=totalamount.add(item.getAmount());
				}
				paymentVoucherMapper.insertPaymentVoucherDetail(item);
				iseq=iseq+1;
			}
		}
		if(null==paymentVoucher.getTotalamount() || paymentVoucher.getTotalamount().compareTo(totalamount)!=0){
			Map updateMap=new HashMap();
			updateMap.put("id", paymentVoucher.getId());
			updateMap.put("totalamount", totalamount);
			paymentVoucherMapper.updatePaymentVoucherAmount(updateMap);
		}
		return flag;		
	}
	
	@Override
	public boolean updatePaymentVoucherAddDetail(PaymentVoucher paymentVoucher) throws Exception{
		boolean flag=paymentVoucherMapper.updatePaymentVoucher(paymentVoucher)>0;
		if(flag ){
			paymentVoucherMapper.deletePaymentVoucherDetailByOrderid(paymentVoucher.getId());
			List<PaymentVoucherDetail> list=paymentVoucher.getPaymentVoucherDetailList();
			BigDecimal totalamount=BigDecimal.ZERO;
			if(null!=list && list.size()>0){
				int iseq=1;
				for(PaymentVoucherDetail item : list){	
					//计算辅单位数量
					item.setOrderid(paymentVoucher.getId());
					item.setSeq(iseq);
					if(null!=item.getAmount()){
						totalamount=totalamount.add(item.getAmount());
					}
					paymentVoucherMapper.insertPaymentVoucherDetail(item);
					iseq=iseq+1;
				}
			}
			
			if(null==paymentVoucher.getTotalamount() || paymentVoucher.getTotalamount().compareTo(totalamount)!=0){
				Map updateMap=new HashMap();
				updateMap.put("id", paymentVoucher.getId());
				updateMap.put("totalamount", totalamount);
				paymentVoucherMapper.updatePaymentVoucherAmount(updateMap);
			}
		}
		return flag;
	}

	/**
	 * 交款单分页列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	@Override
	public PageData showPaymentVoucherPageList(PageMap pageMap) throws Exception{
		String cols = getAccessColumnList("t_account_paymentvoucher",null);
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_account_paymentvoucher",null);
		pageMap.setDataSql(sql);
		List<PaymentVoucher> porderList=paymentVoucherMapper.getPaymentVoucherPageList(pageMap);
		for(PaymentVoucher paymentVoucher : porderList){
			String bankid = paymentVoucher.getBank();
			Bank bank = getBankInfoByID(bankid);
			if(null!=bank){
				paymentVoucher.setBankname(bank.getName());
			}
		}
		
		PageData pageData=new PageData(paymentVoucherMapper.getPaymentVoucherPageCount(pageMap), 
						porderList, pageMap);
		
		PaymentVoucher paymentVoucherSum = paymentVoucherMapper.getPaymentVoucherPageSum(pageMap);

		List<PaymentVoucher> footList=new ArrayList<PaymentVoucher>();
		if(null!=paymentVoucherSum){
			paymentVoucherSum.setId("合计金额");
			
			footList.add(paymentVoucherSum);
		}
		pageData.setFooter(footList);
		return pageData;
	}
	
	/**
	 * 新增交款单
	 * @param paymentVoucher
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	@Override
	public boolean insertPaymentVoucher(PaymentVoucher paymentVoucher) throws Exception{
		return paymentVoucherMapper.insertPaymentVoucher(paymentVoucher) >0;
	}
	/**
	 * 更新交款单
	 * @param paymentVoucher
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	@Override
	public boolean updatePaymentVoucher(PaymentVoucher paymentVoucher) throws Exception{
		return paymentVoucherMapper.updatePaymentVoucher(paymentVoucher) >0 ;
	}
	
	/**
	 * 根据ID编号获取交款单
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	@Override
	public PaymentVoucher showPaymentVoucher(String id) throws Exception{
		return paymentVoucherMapper.getPaymentVoucher(id);
	}
	
	@Override
	public PaymentVoucher showPaymentVoucherAndDetail(String id) throws Exception{
		PaymentVoucher paymentVoucher= paymentVoucherMapper.getPaymentVoucher(id);
		if(null!=paymentVoucher){
			List<PaymentVoucherDetail> list=paymentVoucherMapper.getPaymentVoucherDetailList(paymentVoucher.getId());
			if(null!=list && list.size()>0){
				for(PaymentVoucherDetail item :list){
					Customer customer = getCustomerByID(item.getCustomerid());
					if(null!=customer){
						item.setCustomername(customer.getName());
					}
				}
				paymentVoucher.setPaymentVoucherDetailList(list);
			}else{
				paymentVoucher.setPaymentVoucherDetailList(new ArrayList<PaymentVoucherDetail>());
			}
            if(StringUtils.isEmpty(paymentVoucher.getCollectusername())){
                Personnel personnel = getPersonnelById(paymentVoucher.getCollectuserid());
                if(null!=personnel){
                    paymentVoucher.setCollectusername(personnel.getName());
                }
            }
		}
		return paymentVoucher;
	}
	/**
	 * 根据ID编号删除交款单及其明细
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	@Override
	public boolean deletePaymentVoucherAndDetail(String id) throws Exception{
		boolean isok=false;
		Map queryMap=new HashMap();
		queryMap.put("id", id);
		queryMap.put("notAudit", "1");
		isok=paymentVoucherMapper.deletePaymentVoucherBy(queryMap)>0;
		paymentVoucherMapper.deletePaymentVoucherDetailByOrderid(id);
		return isok;
	}
	/**
	 * 审核交款单
	 */
	@Override
	public Map auditPaymentVoucher(String id) throws Exception{
		Map map=new HashMap();
		Boolean flag=false;
		PaymentVoucher oldPaymentVoucher=showPaymentVoucherByDataAuth(id);
		if("2".equals(oldPaymentVoucher.getStatus())){
			map.put("orderid", id);
			int acount=paymentVoucherMapper.getPaymentVoucherDetailCountBy(map);
			map.clear();
			if(acount==0){
				map.put("flag", false);
				map.put("nextBillFlag",false);
				map.put("msg", "未能找到交款单的明细信息!");
				return map;
			}
			String bankStr="";
			if(StringUtils.isEmpty(oldPaymentVoucher.getBank())){
				bankStr=getSysParamValue("DefaultBankID");
				if(bankStr==null ){
					map.put("flag", false);
					map.put("nextBillFlag",false);
					map.put("msg", "系统参数 DefaultBankID (默认银行名称)不存在!");
					return map;
				}
				if("".equals(bankStr.trim())){
					map.put("flag", false);
					map.put("nextBillFlag",false);
					map.put("msg", "系统参数 DefaultBankID (默认银行名称)值为空!");
					return map;
				}
				Bank baseBank=getBankInfoByID(bankStr.trim());
				if(baseBank==null || StringUtils.isEmpty(baseBank.getId())){
					map.put("flag", false);
					map.put("nextBillFlag",false);
					map.put("msg", "系统参数 DefaultBankID (默认银行名称)值不正确，无法找到对应的银行档案信息!");
					return map;
				}
			}else{
				bankStr =oldPaymentVoucher.getBank();
			}

			SysUser sysUser=getSysUser();
			oldPaymentVoucher.setBusinessdate(CommonUtils.getTodayDataStr());
			oldPaymentVoucher.setStatus("3");
			oldPaymentVoucher.setAudituserid(sysUser.getUserid());
			oldPaymentVoucher.setAuditusername(sysUser.getName());
			oldPaymentVoucher.setAudittime(new Date());
			flag=paymentVoucherMapper.updatePaymentVoucherStatus(oldPaymentVoucher)>0;
			if(flag){
				List<PaymentVoucherDetail> detailList=paymentVoucherMapper.getPaymentVoucherDetailList(oldPaymentVoucher.getId());
				oldPaymentVoucher.setPaymentVoucherDetailList(detailList);
				oldPaymentVoucher.setBank(bankStr.trim());
				int addCount=addCreateCollectionOrder(oldPaymentVoucher);
				if(addCount>0){
					map.put("nextBillFlag",true);
				}else{
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	//如何保存失败后回滚

					map.put("nextBillFlag",false);
					flag=false;
				}
			}
		}
		map.put("flag", flag);
		if(!map.containsKey("nextBillFlag")){
			map.put("nextBillFlag",false);
		}
		map.put("msg", "");
		return map;
	}

	/**
	 * 反审核交款单
	 */
	@Override
	public Map oppauditPaymentVoucher(String id) throws Exception{
		Map map=new HashMap();
		Boolean flag=false;
		PaymentVoucher oldPaymentVoucher=showPaymentVoucherByDataAuth(id);
		if("3".equals(oldPaymentVoucher.getStatus())){
			PaymentVoucher paymentVoucher=new PaymentVoucher();
			SysUser sysUser=getSysUser();
			paymentVoucher.setId(id);
			paymentVoucher.setStatus("2");
			paymentVoucher.setAudituserid(sysUser.getUserid());
			paymentVoucher.setAuditusername(sysUser.getName());
			paymentVoucher.setAudittime(new Date());
            List<CollectionOrder> list=collectionOrderMapper.getCollectionBySourceno(paymentVoucher.getId());
            boolean auditFlag = false ;
            String auditID = "";
            List<String> deleteID = new ArrayList<String>();
            for(CollectionOrder collectionOrder : list){
                if(null != collectionOrder && "2".equals(collectionOrder.getStatus())){
                    deleteID.add(collectionOrder.getId());
                }else if(null != collectionOrder){
                    auditFlag = true;
                    auditID = collectionOrder.getId();
                }
            }
            if(auditFlag){
                map.put("msg", "反审失败!付款单："+auditID+" 已被关闭或审核");
            }else{
                for(String collectionID : deleteID){
                    collectionOrderMapper.deleteCollectionOrder(collectionID);
                }
                flag=paymentVoucherMapper.updatePaymentVoucherStatus(paymentVoucher)>0;
            }
			map.put("flag", flag);
		}else if("4".equals(oldPaymentVoucher.getStatus())){
			map.put("flag", flag);
			map.put("msg", "反审失败!交款单已关闭");
		}else{
			map.put("flag", flag);
			map.put("msg", "反审失败!交款单未被审核");
		}
		return map;
	}
	@Override
	public PaymentVoucher showPaymentVoucherByDataAuth(String id) throws Exception{
		Map map=new HashMap();
		map.put("id", id.trim());
		String datasql = getDataAccessRule("t_account_paymentvoucher",null);
		map.put("authDataSql", datasql);		
		return paymentVoucherMapper.getPaymentVoucherBy(map);		
	}
	
	@Override
	public boolean updateOrderPrinttimes(PaymentVoucher paymentVoucher) throws Exception{
		return paymentVoucherMapper.updateOrderPrinttimes(paymentVoucher)>0;
	}
	@Override
	public void updateOrderPrinttimes(List<PaymentVoucher> list) throws Exception{
		if(null!=list){
			for(PaymentVoucher item : list){
				paymentVoucherMapper.updateOrderPrinttimes(item);
			}
		}
	}

    @Override
    public boolean updateOrderByStatus(List<PaymentVoucher> list) throws Exception {
        boolean flag = false;
        String ids = "";
        if(null!=list && list.size()>0){
            for(PaymentVoucher item : list){
                ids += item.getId()+",";
            }
            ids = ids.substring(0,ids.length()-1);
            String[] idsArr = ids.split(",");
            int i =  paymentVoucherMapper.updateOrderByStatus(idsArr);
            flag = i > 0;
        }
        return flag;
    }

    @Override
	public List showPaymentVoucherListBy(Map map) throws Exception{
		String datasql = getDataAccessRule("t_account_paymentvoucher",null);
		map.put("dataSql", datasql);
		boolean showdetail=false;
		if(null!=map.get("showdetail") && StringUtils.isNotEmpty(map.get("showdetail").toString()) &&"1".equals(map.get("showdetail").toString()) ){
			showdetail=true;
		}
		List<PaymentVoucher> list=paymentVoucherMapper.showPaymentVoucherListBy(map);
		for(PaymentVoucher item : list){
			if(showdetail){
				List<PaymentVoucherDetail> detailList=paymentVoucherMapper.getPaymentVoucherDetailList(item.getId());
				if(null!=list && list.size()>0){
					for(PaymentVoucherDetail detail :detailList){
						if(null!=detail ){
							if(StringUtils.isNotEmpty(detail.getCustomerid())){
								Customer customer=getCustomerByID(detail.getCustomerid());
								detail.setCustomername(customer.getName());
							}
						}
					}
					item.setPaymentVoucherDetailList(detailList);
				}
			}
		}
		return list;
	}

    /**
     * 判断手机上传的单据是否存在
     *
     * @param billid
     * @return
     * @throws Exception
     */
    @Override
    public String hasPhoneBill(String billid) throws Exception {
        int i = paymentVoucherMapper.hasPhoneBill(billid);
        if(i>0){
            return paymentVoucherMapper.getBillIDByPhoneBillid(billid);
        }
        return null;
    }

    /**
     * 交款单分页列表
     *
     * @param pageMap
     * @return
     * @author zhanghonghui
     * @date 2015年3月24日
     */
    @Override
    public List showPaymentVoucherListForPhone(PageMap pageMap) throws Exception {
        String sql = getDataAccessRule("t_account_paymentvoucher",null);
        pageMap.setDataSql(sql);
        List<PaymentVoucher> porderList=paymentVoucherMapper.getPaymentVoucherListForPhone(pageMap);
        return porderList;
    }

    /**
     * 获取已审核或关闭的付款单对应的交款单
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016-8-2
     */
    @Override
    public List<PaymentVoucher> getPaymentVoucherListByAudit() throws Exception {
        List<PaymentVoucher> paymentVoucherList = paymentVoucherMapper.getPaymentVoucherListByAudit();
        List<PaymentVoucher> removedVourcher = new ArrayList<PaymentVoucher>();
        for(PaymentVoucher paymentVoucher : paymentVoucherList){
            List<CollectionOrder> list=collectionOrderMapper.getCollectionBySourceno(paymentVoucher.getId());
            for(CollectionOrder order : list){
                if("2".equals(order.getStatus())){
                    removedVourcher.add(paymentVoucher);
                    continue;
                }
            }
        }
        if( null != removedVourcher && removedVourcher.size() > 0){
            for(PaymentVoucher paymentVoucher : removedVourcher){
                paymentVoucherList.remove(paymentVoucher);
            }
        }
        return paymentVoucherList;
    }

    /**
	 * 生成收款单
	 * @param paymentVoucher
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年3月27日
	 */
	public int addCreateCollectionOrder(PaymentVoucher paymentVoucher)
			throws Exception {
		List<PaymentVoucherDetail> detailList=paymentVoucher.getPaymentVoucherDetailList();
		if(null==detailList || detailList.size()==0){
			return 0;
		}
		int addCount=0;
		for(PaymentVoucherDetail item : detailList){
			CollectionOrder collectionOrder=new CollectionOrder();
			collectionOrder.setBusinessdate(paymentVoucher.getBusinessdate());//业务日期
			collectionOrder.setCollectionuser(paymentVoucher.getCollectuserid()); 	//收款人
			collectionOrder.setCollectionusername(paymentVoucher.getCollectusername());
			collectionOrder.setStatus("2");  //保存
			collectionOrder.setPaytype("1"); 	//货款
			collectionOrder.setBank(paymentVoucher.getBank());	//银行
			String bankid = paymentVoucher.getBank();
			Bank bank = getBankInfoByID(bankid);
			if(null!=bank){
				collectionOrder.setBankname(bank.getName());
			}else{
				collectionOrder.setBankname("现金");
			}
			collectionOrder.setCustomerid(item.getCustomerid());	//客户
			collectionOrder.setCustomername(item.getCustomername());	//客户名称
			collectionOrder.setAmount(item.getAmount());	//金额
			collectionOrder.setSource("1"); //交款单
			collectionOrder.setSourceno(paymentVoucher.getId());
			collectionOrder.setRemark(item.getRemark());
			if (isAutoCreate("t_account_collection_order")) {
				// 获取自动编号
				String id = getAutoCreateSysNumbderForeign(collectionOrder, "t_account_collection_order");
				collectionOrder.setId(id);
			}else{
				collectionOrder.setId("SKD-"+CommonUtils.getDataNumberSendsWithRand());
			} 
			SysUser sysUser = getSysUser();
			collectionOrder.setAdddeptid(sysUser.getDepartmentid());
			collectionOrder.setAdddeptname(sysUser.getDepartmentname());
			collectionOrder.setAdduserid(sysUser.getUserid());
			collectionOrder.setAddusername(sysUser.getName());
			collectionOrder.setRemainderamount(collectionOrder.getAmount());
			collectionOrder.setInitamount(collectionOrder.getAmount());
			//判断收款单是否指定了客户 1是0否
			if(null==collectionOrder.getCustomerid() || "".equals(collectionOrder.getCustomerid())){
				collectionOrder.setIscustomer("0");
			}
			if( collectionOrderMapper.addCollectionOrder(collectionOrder)>0){
				addCount=addCount+1;
			};
		}
		return addCount;
	}
}
