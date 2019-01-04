/**
 * @(#)ReceiptHandServiceImpl.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Nov 24, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.account.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.CustomerPushBalanceMapper;
import com.hd.agent.account.dao.ReceiptHandMapper;
import com.hd.agent.account.model.CustomerPushBalance;
import com.hd.agent.account.model.ReceiptHand;
import com.hd.agent.account.model.ReceiptHandBill;
import com.hd.agent.account.model.ReceiptHandCustomer;
import com.hd.agent.account.service.IReceiptHandService;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.sales.dao.ReceiptMapper;
import com.hd.agent.sales.dao.RejectBillMapper;
import com.hd.agent.sales.model.Order;
import com.hd.agent.sales.model.Receipt;
import com.hd.agent.sales.model.RejectBill;

import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 回单交接单service
 * 
 * @author panxiaoxiao
 */
public class ReceiptHandServiceImpl extends BaseFilesServiceImpl implements IReceiptHandService{

	private ReceiptHandMapper receiptHandMapper;
	
	private ReceiptMapper receiptMapper;
	
	private RejectBillMapper rejectBillMapper;
	
	private CustomerPushBalanceMapper customerPushBalanceMapper;
	
	public CustomerPushBalanceMapper getCustomerPushBalanceMapper() {
		return customerPushBalanceMapper;
	}

	public void setCustomerPushBalanceMapper(
			CustomerPushBalanceMapper customerPushBalanceMapper) {
		this.customerPushBalanceMapper = customerPushBalanceMapper;
	}

	public RejectBillMapper getRejectBillMapper() {
		return rejectBillMapper;
	}

	public void setRejectBillMapper(RejectBillMapper rejectBillMapper) {
		this.rejectBillMapper = rejectBillMapper;
	}

	public ReceiptHandMapper getReceiptHandMapper() {
		return receiptHandMapper;
	}

	public void setReceiptHandMapper(ReceiptHandMapper receiptHandMapper) {
		this.receiptHandMapper = receiptHandMapper;
	}

	public ReceiptMapper getReceiptMapper() {
		return receiptMapper;
	}

	public void setReceiptMapper(ReceiptMapper receiptMapper) {
		this.receiptMapper = receiptMapper;
	}

	/**
	 * 执行单据是否生成交接单
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 31, 2015
	 */
	private boolean doBillsIsHand(String billid,String ishand)throws Exception{
        boolean flag = false;
		Map map = new HashMap();
		map.put("id", billid);
		Receipt receipt = receiptMapper.getReceipt(map);
		if(null != receipt){
            flag = receiptMapper.updateReceiptIsHand(billid, ishand)>0;
		}else{
			RejectBill rejectBill = rejectBillMapper.getRejectBill(map);
			if(null != rejectBill){
                    flag = rejectBillMapper.updateRejectBillIsHand(billid,ishand)>0;
			}else{
				CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(billid);
				if(null != customerPushBalance){
                    flag = customerPushBalanceMapper.updateCustomerPushBalanceIsHand(billid,ishand)>0;
				}
			}
		}
        return flag;
	}
	
	@Override
	public Map addReceiptHand(String ids,boolean isphone) throws Exception {
		ReceiptHand receiptHand = new ReceiptHand();
		if(isAutoCreate("t_account_receipt")){
			String id = getAutoCreateSysNumbderForeign(receiptHand, "t_account_receipt");
			receiptHand.setId(id);
		}else{
			receiptHand.setId("JJD-"+CommonUtils.getDataNumberSeconds());
		}
		SysUser sysUser = getSysUser();
        if(isphone){
            Personnel personnel = getPersonnelById(sysUser.getPersonnelid());
            if(null!=personnel){
                receiptHand.setHanduserid(personnel.getId());
                receiptHand.setHanddeptid(personnel.getBelongdeptid());
            }
        }
        receiptHand.setAdddeptid(sysUser.getDepartmentid());
		receiptHand.setAdddeptname(sysUser.getDepartmentname());
		receiptHand.setAdduserid(sysUser.getUserid());
		receiptHand.setAddusername(sysUser.getName());
        receiptHand.setAddtime(new Date());
		receiptHand.setStatus("2");
		
		Map paramMap = new HashMap();
        String[] addIDArr = ids.split(",");
		paramMap.put("idArr",addIDArr );
		paramMap.put("billid", receiptHand.getId());
		List<ReceiptHandBill> billList = receiptHandMapper.getReceiptHandBillListByIds(paramMap);
		List<ReceiptHandCustomer> customerList = receiptHandMapper.getReceiptHandCustomerListByIds(paramMap);
		if(null != customerList){
			receiptHand.setCnums(customerList.size());
			BigDecimal totalamount = BigDecimal.ZERO;
			BigDecimal collectamount = BigDecimal.ZERO;
			for(ReceiptHandCustomer receiptCustomer : customerList){
				if(null != receiptCustomer){
					totalamount = totalamount.add((null != receiptCustomer.getAmount()) ? receiptCustomer.getAmount() : BigDecimal.ZERO);
					collectamount = collectamount.add((null != receiptCustomer.getCollectionamount()) ? receiptCustomer.getCollectionamount() : BigDecimal.ZERO);
				}
			}
			BigDecimal uncollectamount = totalamount.subtract(collectamount);
			receiptHand.setTotalamount(totalamount);
			receiptHand.setCollectamount(collectamount);
			receiptHand.setUncollectamount(uncollectamount);
		}
		
		boolean flag = receiptHandMapper.addReceiptHand(receiptHand) > 0;
		if(flag){
			boolean billflag = false;
			boolean customerflag = false;
			if(null != billList && billList.size() != 0){
				billflag = receiptHandMapper.addReceiptHandBillDetail(billList) > 0;
			}
			if(null != customerList && customerList.size() != 0){
				customerflag = receiptHandMapper.addReceiptHandCustomerDetail(customerList) > 0;
			}
			//回写销售回单、销售退货通知单、冲差单是否生成交接单
			if(StringUtils.isNotEmpty(ids) && billflag && customerflag){
				String[] idArr = ids.split(",");
				for(String id : idArr){
					boolean updateFlag = doBillsIsHand(id,"1");
				}
			}
		}
        String handIDs = null;
        for(String id : addIDArr){
            boolean addFlag = false;
            for(ReceiptHandBill receiptHandBill : billList){
                if(id.equals(receiptHandBill.getRelatebillid())){
                    addFlag = true;
                    break;
                }
            }
            if(!addFlag){
                if(handIDs==null){
                    handIDs = id;
                }else{
                    handIDs += ","+id;
                }
            }
        }
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", receiptHand.getId());
        if(StringUtils.isNotEmpty(handIDs)){
            map.put("msg", "单据："+handIDs+"已申请");
        }else{
            map.put("msg", "");
        }
		return map;
	}

	@Override
	public ReceiptHand getReceiptHandInfo(String id) throws Exception {
		ReceiptHand receiptHand = receiptHandMapper.getReceiptHandInfo(id);
		if(null != receiptHand){
			List billList = receiptHandMapper.getReceiptHandBillListByBillid(id);
			if(null != billList){
				receiptHand.setBilllist(billList);
			}
			List customerList = receiptHandMapper.getReceiptHandCustomerListByBillid(id);
			if(null != customerList && customerList.size() != 0){
				receiptHand.setCustomerlist(customerList);
			}
		}
		return receiptHand;
	}

	@Override
	public ReceiptHand getReceiptHandBaseInfo(String id) throws Exception {
		return receiptHandMapper.getReceiptHandInfo(id);
	}

	@Override
	public Map getReceiptHandBillListByBillid(String billid) throws Exception {
		List<ReceiptHandBill> list = receiptHandMapper.getReceiptHandBillListByBillid(billid);
		BigDecimal amount = BigDecimal.ZERO;
		for(ReceiptHandBill receiptHandBill : list){
			Customer customer = getCustomerByID(receiptHandBill.getCustomerid());
			if(null != customer){
				receiptHandBill.setCustomername(customer.getName());
			}
			Customer pCustomer = getCustomerByID(receiptHandBill.getPcustomerid());
			if(null != pCustomer){
				receiptHandBill.setPcustomername(pCustomer.getName());
			}
			CustomerSort customerSort = getCustomerSortByID(receiptHandBill.getCustomersort());
			if(null != customerSort){
				receiptHandBill.setCustomersortname(customerSort.getThisname());
			}
			SalesArea salesArea = getSalesareaByID(receiptHandBill.getSalesarea());
			if(null != salesArea){
				receiptHandBill.setSalesareaname(salesArea.getThisname());
			}
			DepartMent departMent = getDepartMentById(receiptHandBill.getSalesdept());
			if(null != departMent){
				receiptHandBill.setSalesdeptname(departMent.getName());
			}
			Personnel personnel = getPersonnelById(receiptHandBill.getSalesuser());
			if(null != personnel){
				receiptHandBill.setSalesusername(personnel.getName());
			}
			//单据类型名称
			if("1".equals(receiptHandBill.getBilltype())){
				receiptHandBill.setBilltypename("发货回单");
			}else if("2".equals(receiptHandBill.getBilltype())){
				receiptHandBill.setBilltypename("销售退货通知单");
			}else if("3".equals(receiptHandBill.getBilltype())){
				receiptHandBill.setBilltypename("冲差单");
			}
			//是否回收
			if("0".equals(receiptHandBill.getIsrecycle())){
				receiptHandBill.setIsrecyclename("否");
			}else if("1".equals(receiptHandBill.getIsrecycle())){
				receiptHandBill.setIsrecyclename("是");
			}
			amount = amount.add((null != receiptHandBill.getAmount()) ? receiptHandBill.getAmount() : BigDecimal.ZERO);

		}
		
		ReceiptHandBill receiptHandBillSum = new ReceiptHandBill();
		receiptHandBillSum.setRelatebillid("合计");
		receiptHandBillSum.setAmount(amount);
		
		List footer = new ArrayList();
		footer.add(receiptHandBillSum);
		
		Map map = new HashMap();
		map.put("list", list);
		map.put("footer", footer);
		return map;
	}

	@Override
	public Map getReceiptHandCustomerListByBillid(String billid) throws Exception {
		List<ReceiptHandCustomer> list = receiptHandMapper.getReceiptHandCustomerListByBillid(billid);
		List<ReceiptHandBill> receiptHandBillList =receiptHandMapper.getReceiptHandBillListByBillid(billid);
		Integer billnums = 0;
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal collectionamount = BigDecimal.ZERO;
		for(ReceiptHandCustomer receiptHandCustomer : list){
			if(null != receiptHandCustomer){
				Customer customer = getCustomerByID(receiptHandCustomer.getCustomerid());
				if(null != customer){
					receiptHandCustomer.setCustomername(customer.getName());
				}
				
				billnums += (null != receiptHandCustomer.getBillnums()) ? receiptHandCustomer.getBillnums() : Integer.parseInt("0");
				amount = amount.add((null != receiptHandCustomer.getAmount()) ? receiptHandCustomer.getAmount() : BigDecimal.ZERO);
				collectionamount = collectionamount.add((null != receiptHandCustomer.getCollectionamount()) ? receiptHandCustomer.getCollectionamount() : BigDecimal.ZERO);
			}

			String sourceids ="";
			for(ReceiptHandBill receiptHandBill : receiptHandBillList){
				if(StringUtils.isNotEmpty(receiptHandBill.getSourceid())){
					if(receiptHandCustomer.getCustomerid().equals(receiptHandBill.getCustomerid())){
						if(StringUtils.isEmpty(sourceids)){
							sourceids = receiptHandBill.getSourceid();
						}else{
							sourceids += ","+sourceids;
						}
					}
				}
			}
			receiptHandCustomer.setSourceids(sourceids);
		}
		
		ReceiptHandCustomer receiptHandCustomerSum = new ReceiptHandCustomer();
		receiptHandCustomerSum.setCustomerid("合计");
		receiptHandCustomerSum.setBillnums(billnums);
		receiptHandCustomerSum.setAmount(amount);
		receiptHandCustomerSum.setCollectionamount(collectionamount);
		List footer = new ArrayList();
		footer.add(receiptHandCustomerSum);
		
		Map map = new HashMap();
		map.put("list", list);
		map.put("footer", footer);
		return map;
	}

    /**
     * 根据回单交接单单据编号和客户编号 获取客户的单据明细列表
     *
     * @param billid
     * @param customerid
     * @return
     * @throws Exception
     */
    @Override
    public List<ReceiptHandBill> getReceiptHandBillListByBillidAndCustomerid(String billid, String customerid) throws Exception {
        List<ReceiptHandBill> list = receiptHandMapper.getReceiptHandBillListByBillidAndCustomerid(billid,customerid);
        for(ReceiptHandBill receiptHandBill : list){
            Customer customer = getCustomerByID(receiptHandBill.getCustomerid());
            if(null != customer){
                receiptHandBill.setCustomername(customer.getName());
            }
            Customer pCustomer = getCustomerByID(receiptHandBill.getPcustomerid());
            if(null != pCustomer){
                receiptHandBill.setPcustomername(pCustomer.getName());
            }
            CustomerSort customerSort = getCustomerSortByID(receiptHandBill.getCustomersort());
            if(null != customerSort){
                receiptHandBill.setCustomersortname(customerSort.getThisname());
            }
            SalesArea salesArea = getSalesareaByID(receiptHandBill.getSalesarea());
            if(null != salesArea){
                receiptHandBill.setSalesareaname(salesArea.getThisname());
            }
            DepartMent departMent = getDepartMentById(receiptHandBill.getSalesdept());
            if(null != departMent){
                receiptHandBill.setSalesdeptname(departMent.getName());
            }
            Personnel personnel = getPersonnelById(receiptHandBill.getSalesuser());
            if(null != personnel){
                receiptHandBill.setSalesusername(personnel.getName());
            }

        }
        return list;
    }

    @Override
	public PageData getReceiptHandList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_account_receipt", "t");
		pageMap.setDataSql(dataSql);
		int count = receiptHandMapper.getReceiptHandListCount(pageMap);
		List<ReceiptHand> list = receiptHandMapper.getReceiptHandList(pageMap);
		for(ReceiptHand receiptHand : list){
			Personnel personnel = getPersonnelById(receiptHand.getHanduserid());
			if(null != personnel){
				receiptHand.setHandusername(personnel.getName());
			}
			DepartMent departMent = getDepartMentById(receiptHand.getHanddeptid());
			if(null != departMent){
				receiptHand.setHanddeptname(departMent.getName());
			}
//			int billsnum = 0;
//			List<ReceiptHandCustomer> custoemrList = receiptHandMapper.getReceiptHandCustomerListByBillid(receiptHand.getId());
//			for(ReceiptHandCustomer receiptHandCustomer : custoemrList){
//				billsnum = billsnum + ((null != receiptHandCustomer.getBillnums()) ? receiptHandCustomer.getBillnums() : 0);
//			}
//			receiptHand.setBillnums(billsnum);
		}
		PageData pageData = new PageData(count, list, pageMap);
		//合计
		ReceiptHand receiptHand = receiptHandMapper.getReceiptHandListSum(pageMap);
		if(null != receiptHand){
			receiptHand.setId("合计");
		}else{
			receiptHand = new ReceiptHand();
			receiptHand.setId("合计");
		}
		List footer = new ArrayList();
		footer.add(receiptHand);
		pageData.setFooter(footer);
		return pageData;
	}

    /**
     * 获取回单交接列表（提供手机试用）
     *
     * @param pageMap
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2015-06-03
     */
    @Override
    public List<ReceiptHand> getReceiptHandListForPhone(PageMap pageMap) throws Exception {
        pageMap.getCondition().put("isPageFlag", "1");
        String dataSql = getDataAccessRule("t_account_receipt", "t");
        pageMap.setDataSql(dataSql);
        List<ReceiptHand> list = receiptHandMapper.getReceiptHandList(pageMap);
        for(ReceiptHand receiptHand : list){
            Personnel personnel = getPersonnelById(receiptHand.getHanduserid());
            if(null != personnel){
                receiptHand.setHandusername(personnel.getName());
            }
            DepartMent departMent = getDepartMentById(receiptHand.getHanddeptid());
            if(null != departMent){
                receiptHand.setHanddeptname(departMent.getName());
            }
        }
        return list;
    }

    @Override
	public Map deleteReceiptHands(String ids) throws Exception {
		String sucids = "",unsucids = "",msg = "";
		int sucnum = 0,invalidNum = 0;
		String[] idArr = ids.split(",");
		for(String id : idArr){
			ReceiptHand receiptHand = getReceiptHandBaseInfo(id);
			if(null != receiptHand){
				if("2".equals(receiptHand.getStatus())){
					boolean flag = receiptHandMapper.deleteReceiptHand(id) > 0;
					if(flag){
						if(StringUtils.isEmpty(sucids)){
							sucids = id;
						}else{
							sucids += "," + id;
						}
						//回写销售发货回单、销售退货通知单、冲差单是否生成交接单
						List<ReceiptHandBill> billList = receiptHandMapper.getReceiptHandBillListByBillid(id);
						for(ReceiptHandBill receiptHandBill : billList){
							Map map = new HashMap();
							map.put("id", receiptHandBill.getRelatebillid());
							Receipt receipt = receiptMapper.getReceipt(map);
							if(null != receipt){
								receiptMapper.updateReceiptIsHand(receiptHandBill.getRelatebillid(), "0");
							}else{
								RejectBill rejectBill = rejectBillMapper.getRejectBill(map);
								if(null != rejectBill){
									rejectBillMapper.updateRejectBillIsHand(receiptHandBill.getRelatebillid(),"0");
								}else{
									CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(receiptHandBill.getRelatebillid());
									if(null != customerPushBalance){
										customerPushBalanceMapper.updateCustomerPushBalanceIsHand(receiptHandBill.getRelatebillid(),"0");
									}
								}
							}
						}
						Map delmap = new HashMap();
						delmap.put("billid", id);
						receiptHandMapper.deleteReceiptHandBillListByMap(delmap);
						receiptHandMapper.deleteReceiptHandCustomerListByMap(delmap);
						sucnum++;
					}else{
						if(StringUtils.isEmpty(unsucids)){
							unsucids = id;
						}else{
							unsucids += "," + id;
						}
					}
				}else{
					invalidNum++;
				}
			}
		}
		if(StringUtils.isNotEmpty(unsucids)){
			msg = "删除交接单："+unsucids+"失败!";
		}
		Map map = new HashMap();
		map.put("sucnum", sucnum);
		map.put("sucids", sucids);
		map.put("invalidNum", invalidNum);
		map.put("msg", msg);
		return map;
	}

	@Override
	public Map auditReceiptHands(String ids) throws Exception {
		Map map = new HashMap();
		int invalidNum = 0, num = 0;
		int nohandusernum = 0;
		String newids = "";
		String[] idArr = ids.split(",");
		for(String id : idArr){
			ReceiptHand receiptHand = receiptHandMapper.getReceiptHandInfo(id);
			if(null != receiptHand){
				if(!"2".equals(receiptHand.getStatus())){
					invalidNum++;
				}else if(StringUtils.isEmpty(receiptHand.getHanduserid())){
					nohandusernum++;
				}else{
					if(StringUtils.isNotEmpty(newids)){
						newids += "," + id;
					}else{
						newids = id;
					}
				}
			}
		}
		boolean flag = false;
		if(StringUtils.isNotEmpty(newids)){
			SysUser sysUser = getSysUser();
			Map auditMap = new HashMap();
			auditMap.put("idArr", newids.split(","));
			auditMap.put("audituserid", sysUser.getUserid());
			auditMap.put("auditusername", sysUser.getName());
            auditMap.put("audittime",new Date());
			int i = receiptHandMapper.auditReceiptHands(auditMap);
			flag = i > 0;
			map.put("num", i);
		}else{
			newids = ids;
		}
		map.put("flag", flag);
		map.put("id", newids);
		map.put("invalidNum", invalidNum);
		map.put("nohandusernum", nohandusernum);
		return map;
	}

	@Override
	public Map editReceiptHand(ReceiptHand receiptHand,List<ReceiptHandBill> billList,List<ReceiptHandCustomer> customerList) throws Exception {
		ReceiptHand oldReceiptHand = receiptHandMapper.getReceiptHandInfo(receiptHand.getId());
		if(null==oldReceiptHand || "3".equals(oldReceiptHand.getStatus()) || "4".equals(oldReceiptHand.getStatus())){
			Map map = new HashMap();
			map.put("flag", false);
			return map;
		}
		SysUser sysUser = getSysUser();
		receiptHand.setModifyuserid(sysUser.getUserid());
		receiptHand.setModifyusername(sysUser.getName());
		boolean flag = receiptHandMapper.editReceiptHand(receiptHand) > 0;
		if(flag){
			//在插入新明细时，全部打算为回收标记且回单已生成交接单标记“1”
			if(null != billList){
				//获取该交接单的单据明细列表数据，删除其明细数据前，回写发货回单、销售退货通知单、冲差单是否生成交接单标记“0”
				List<ReceiptHandBill> oldBillList = receiptHandMapper.getReceiptHandBillListByBillid(receiptHand.getId());
				for(ReceiptHandBill oldReceiptHandBill : oldBillList){
					doBillsIsHand(oldReceiptHandBill.getRelatebillid(),"0");
				}
				receiptHandMapper.deleteReceiptHandBillByBillid(receiptHand.getId());
				if(billList.size() != 0){
					for(ReceiptHandBill receiptHandBill : billList){
						receiptHandBill.setBillid(receiptHand.getId());
						receiptHandBill.setIsrecycle("0");
						receiptHandBill.setRecycledate("");
					}
					boolean flag2 = receiptHandMapper.addReceiptHandBillDetail(billList) > 0;
					if(flag2){
						//回写销售发货回单、销售退货通知单、冲查单是否生成交接单
						for(ReceiptHandBill receiptHandBill : billList){
							doBillsIsHand(receiptHandBill.getRelatebillid(),"1");
						}
					}
				}
			}else{
				if(StringUtils.isNotEmpty(receiptHand.getDelcustomerids())){
					String customeriddelstr = receiptHand.getDelcustomerids();
					String[] customerArr = customeriddelstr.substring(0, customeriddelstr.length()-1).split(",");
					Map map = new HashMap();
					map.put("billid", receiptHand.getId());
					map.put("customerArr", customerArr);
					List<ReceiptHandBill> billList2 = receiptHandMapper.getReceiptHandBillListByParam(map);
					for(ReceiptHandBill oldReceiptHandBill : billList2){
						doBillsIsHand(oldReceiptHandBill.getRelatebillid(),"0");
						map.put("relatebillid", oldReceiptHandBill.getRelatebillid());
						receiptHandMapper.deleteReceiptHandBillListByMap(map);
					}
				}
			}
			//删除该交接单对应的客户明细，再重新插入客户明细
			receiptHandMapper.deleteReceiptHandCustomerByBill(receiptHand.getId());
			if(null != customerList && customerList.size() != 0){
				receiptHandMapper.addReceiptHandCustomerDetail(customerList);
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		return map;
	}

	@Override
	public Map saveAuditReceiptHand(ReceiptHand receiptHand,List<ReceiptHandBill> billList,List<ReceiptHandCustomer> customerList) throws Exception {
		String msg = "";
		Map retMap = new HashMap();
		Map map = editReceiptHand(receiptHand,billList,customerList);
		boolean flag = map.get("flag").equals(true);
		if(!flag){
			msg = "交接单保存失败;";
			retMap.put("flag", flag);
		}else{
			Map map2 = auditReceiptHands(receiptHand.getId());
			retMap.putAll(map2);
		}
		retMap.put("msg", msg);
		return retMap;
	}

	@Override
	public Map oppauditReceiptHands(String ids) throws Exception {
		Map map = new HashMap();
		int invalidNum = 0, num = 0;
		String newids = "";
		String[] idArr = ids.split(",");
		for(String id : idArr){
			ReceiptHand receiptHand = receiptHandMapper.getReceiptHandInfo(id);
			if(null != receiptHand){
				if(!"3".equals(receiptHand.getStatus())){
					invalidNum++;
				}else{
					if(StringUtils.isNotEmpty(newids)){
						newids += "," + id;
					}else{
						newids = id;
					}
				}
			}
		}
		boolean flag = false;
		if(StringUtils.isNotEmpty(newids)){
			SysUser sysUser = getSysUser();
			Map auditMap = new HashMap();
			auditMap.put("idArr", newids.split(","));
			auditMap.put("audituserid", sysUser.getUserid());
			auditMap.put("auditusername", sysUser.getName());
			int i = receiptHandMapper.oppauditReceiptHands(auditMap);
			flag = i > 0;
			map.put("num", i);
		}
		map.put("flag", flag);
		map.put("id", newids);
		map.put("invalidNum", invalidNum);
		return map;
	}

	@Override
	public PageData getReceiptListByHand(PageMap pageMap) throws Exception {
		String dataSql1 = getDataAccessRule("t_sales_receipt", "t2");
		pageMap.getCondition().put("datasql1",dataSql1);
		String dataSql2 = getDataAccessRule("t_sales_rejectbill", "t2");
		pageMap.getCondition().put("datasql2",dataSql2);
		String dataSql3 = getDataAccessRule("t_account_customer_push_balance", "t2");
		pageMap.getCondition().put("datasql3",dataSql3);
		int count = receiptHandMapper.getReceiptListByHandCount(pageMap);
		List<ReceiptHandBill> list = receiptHandMapper.getReceiptListByHand(pageMap);
		for(ReceiptHandBill receiptHandBill : list){
			Customer customer = getCustomerByID(receiptHandBill.getCustomerid());
			if(null != customer){
				receiptHandBill.setCustomername(customer.getName());
			}
			Customer pCustomer = getCustomerByID(receiptHandBill.getPcustomerid());
			if(null != pCustomer){
				receiptHandBill.setPcustomername(pCustomer.getName());
			}
			CustomerSort customerSort = getCustomerSortByID(receiptHandBill.getCustomersort());
			if(null != customerSort){
				receiptHandBill.setCustomersortname(customerSort.getThisname());
			}
			SalesArea salesArea = getSalesareaByID(receiptHandBill.getSalesarea());
			if(null != salesArea){
				receiptHandBill.setSalesareaname(salesArea.getThisname());
			}
			DepartMent departMent = getDepartMentById(receiptHandBill.getSalesdept());
			if(null != departMent){
				receiptHandBill.setSalesdeptname(departMent.getName());
			}
			Personnel personnel = getPersonnelById(receiptHandBill.getSalesuser());
			if(null != personnel){
				receiptHandBill.setSalesusername(personnel.getName());
			}
			Personnel personnel2 = getPersonnelById(receiptHandBill.getHanduserid());
			if(null != personnel2){
				receiptHandBill.setHandusername(personnel2.getName());
			}

			//单据类型
			if("1".equals(receiptHandBill.getBilltype())){
				receiptHandBill.setBilltypename("发货回单");
			}else if("2".equals(receiptHandBill.getBilltype())){
				receiptHandBill.setBilltypename("销售退货通知单");
			}else if("3".equals(receiptHandBill.getBilltype())){
				receiptHandBill.setBilltypename("冲差单");
			}
			//是否回收
			if("0".equals(receiptHandBill.getIsrecycle())){
				receiptHandBill.setIsrecyclename("否");
			}else if("1".equals(receiptHandBill.getIsrecycle())){
				receiptHandBill.setIsrecyclename("是");
			}
			//回单状态
			SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(receiptHandBill.getStatus(),"status");
			if(null != sysCode){
				receiptHandBill.setStatusname(sysCode.getCodename());
			}
		}
		PageData pageData = new PageData(count,list,pageMap);
		//合计
		ReceiptHandBill receiptHandBillSum = receiptHandMapper.getReceiptListByHandSum(pageMap);
		if(null != receiptHandBillSum){
			receiptHandBillSum.setBillid("合计");
		}else{
			receiptHandBillSum = new ReceiptHandBill();
			receiptHandBillSum.setBillid("合计");
		}
		List footer = new ArrayList();
		footer.add(receiptHandBillSum);
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public Map doBackReceiptHands(List<ReceiptHandBill> list) throws Exception {
		//invainreceiptids回收无效回单单据列表
		String msg = "",invainreceiptids = "",sucreceiptids = "";
		int sucnum = 0;
		if(null != list && list.size() != 0){
			for(ReceiptHandBill receiptHandBill : list){
				if("0".equals(receiptHandBill.getIsrecycle())){
					boolean flag = false;
					Map map = new HashMap();
					map.put("id", receiptHandBill.getRelatebillid());
					//回写发货回单、销售退货通知单、冲差单为未生成交接单“0”
					Receipt receipt = receiptMapper.getReceipt(map);
					if(null != receipt){
						if(!"4".equals(receipt.getStatus())){
							flag = receiptMapper.updateReceiptIsHand(receipt.getId(), "0") > 0;
						}else{
							if(StringUtils.isEmpty(invainreceiptids)){
								invainreceiptids = receipt.getId();
							}else{
								invainreceiptids += "," + receipt.getId();
							}
							continue;
						}
					}else{
						RejectBill rejectBill = rejectBillMapper.getRejectBill(map);
						if(null != rejectBill){
							if("3".equals(rejectBill.getIsinvoice()) || "4".equals(rejectBill.getIsinvoice()) || "1".equals(rejectBill.getIsinvoice())){
								flag = rejectBillMapper.updateRejectBillIsHand(rejectBill.getId(), "0") > 0;
							}else{
								if(StringUtils.isEmpty(invainreceiptids)){
									invainreceiptids = rejectBill.getId();
								}else{
									invainreceiptids += "," + rejectBill.getId();
								}
								continue;
							}
						}else{
							CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(receiptHandBill.getRelatebillid());
							if(null != customerPushBalance){
								if(!"4".equals(customerPushBalance.getStatus())){
									flag = customerPushBalanceMapper.updateCustomerPushBalanceIsHand(customerPushBalance.getId(), "0") > 0;
								}else{
									if(StringUtils.isEmpty(invainreceiptids)){
										invainreceiptids = customerPushBalance.getId();
									}else{
										invainreceiptids += "," + customerPushBalance.getId();
									}
									continue;
								}
							}
						}
					}
					if(flag){
						//交接单单据明细标记为“已回收”
						boolean flag2 = receiptHandMapper.updateReceiptHandBillIsrecycle(receiptHandBill.getBillid(),receiptHandBill.getRelatebillid()) > 0;
						if(!flag2){
							if(StringUtils.isEmpty(msg)){
								msg = "交接单编码："+receiptHandBill.getBillid()+",单据编码：" + receiptHandBill.getRelatebillid() +"回收失败!";
							}else{
								msg += "<br>" + "交接单编码："+receiptHandBill.getBillid()+",单据编码：" + receiptHandBill.getRelatebillid() +"回收失败!";
							}
							continue;
						}else{
                            if(StringUtils.isEmpty(sucreceiptids)){
                                sucreceiptids = receiptHandBill.getRelatebillid();
                            }else{
                                sucreceiptids += "," + receiptHandBill.getRelatebillid();
                            }
							sucnum++;
						}
					}
				}else{
					if(StringUtils.isEmpty(msg)){
						msg = "交接单编码："+receiptHandBill.getBillid()+",单据编码：" + receiptHandBill.getRelatebillid() +"已回收!";
					}else{
						msg += "<br>" + "交接单编码："+receiptHandBill.getBillid()+",单据编码：" + receiptHandBill.getRelatebillid() +"已回收!";
					}
					continue;
				}
			}
			if(StringUtils.isNotEmpty(invainreceiptids)){
				if(StringUtils.isEmpty(msg)){
					msg = "单据编号："+invainreceiptids+"状态不允许回收，回收无效!";
				}else{
					msg += "<br>" + "单据编号："+invainreceiptids+"状态不允许回收，回收无效!";
				}
			}
		}
		Map map = new HashMap();
		map.put("msg", msg);
		map.put("sucnum", sucnum);
        map.put("sucreceiptids", sucreceiptids);
		return map;
	}

	@Override
	public Map getReceiptHandCustomerListByBills(String billid,String relatebillid)
			throws Exception {
		Map map2 = new HashMap();
		map2.put("billid", billid);
		map2.put("idArr", relatebillid.split(","));
		List<ReceiptHandCustomer> list = receiptHandMapper.getReceiptHandCustomerListByBills(map2);
		for(ReceiptHandCustomer receiptHandCustomer : list){
			Customer customer = getCustomerByID(receiptHandCustomer.getCustomerid());
			if(null != customer){
				receiptHandCustomer.setCustomername(customer.getName());
			}
		}
		Map retMap = new HashMap();
		retMap.put("list", list);
		return retMap;
	}

	@Override
	public Map getReceiptHandDetailListMap(String receipthandid,
			String sourceids) throws Exception {
		Map paramMap = new HashMap();
		paramMap.put("idArr", sourceids.split(","));
		paramMap.put("billid", receipthandid);
		List<ReceiptHandBill> billList = receiptHandMapper.getReceiptHandBillListByBills(paramMap);
		for(ReceiptHandBill receiptHandBill : billList){
			receiptHandBill.setBillid(receipthandid);
			Customer customer = getCustomerByID(receiptHandBill.getCustomerid());
			if(null != customer){
				receiptHandBill.setCustomername(customer.getName());
			}
			Customer pCustomer = getCustomerByID(receiptHandBill.getPcustomerid());
			if(null != pCustomer){
				receiptHandBill.setPcustomername(pCustomer.getName());
			}
			CustomerSort customerSort = getCustomerSortByID(receiptHandBill.getCustomersort());
			if(null != customerSort){
				receiptHandBill.setCustomersortname(customerSort.getThisname());
			}
			SalesArea salesArea = getSalesareaByID(receiptHandBill.getSalesarea());
			if(null != salesArea){
				receiptHandBill.setSalesareaname(salesArea.getThisname());
			}
			Personnel personnel = getPersonnelById(receiptHandBill.getSalesuser());
			if(null != personnel){
				receiptHandBill.setSalesusername(personnel.getName());
			}
		}
//		List<ReceiptHandCustomer> customerList = receiptHandMapper.getReceiptHandCustomerListByIds(paramMap);
//		for(ReceiptHandCustomer receiptHandCustomer : customerList){
//			receiptHandCustomer.setBillid(receipthandid);
//			Customer customer = getCustomerByID(receiptHandCustomer.getCustomerid());
//			if(null != customer){
//				receiptHandCustomer.setCustomername(customer.getName());
//			}
//		}
		Map<String,List> map = new HashMap<String, List>();
		map.put("billList", billList);
//		map.put("customerList", customerList);
		return map;
	}

	@Override
	public boolean closeAllReceiptClosedOfReceiptHandList() throws Exception {
		boolean flag = true;
		List<String> list = receiptHandMapper.getAllReceiptClosedOfReceiptHandList();
		if(null != list && list.size() != 0){
			for(String id : list){
				boolean flag2 = receiptHandMapper.closeReceiptHand(id) > 0;
				flag = flag && flag2;
			}
		}else{
			flag = false;
		}
		return flag;
	}

	@Override
	public PageData getReceiptListGroupByCustomerForReceiptHand(PageMap pageMap)
			throws Exception {
        String dataSql = getDataAccessRule("t_account_receipthand_bill","t");
        if(StringUtils.isNotEmpty(dataSql)){
            dataSql = dataSql.replaceAll("t.payeeid","c.payeeid");
        }
        pageMap.setDataSql(dataSql);
        SysUser sysUser = getSysUser();
        //判断是否品牌业务员
        String brandUserRoleName = getSysParamValue("BrandUserRoleName");
        boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
        if(isBrandUser){
            pageMap.getCondition().put("isBrandUser", true);
            pageMap.getCondition().put("personnelid", sysUser.getPersonnelid());
        }
		int count = receiptHandMapper.getReceiptCountGroupByCustomer(pageMap);
		List<Receipt> list = receiptHandMapper.getReceiptListGroupByCustomer(pageMap);
		for(Receipt receipt : list){
			Customer pCustomer = getCustomerByID(receipt.getPcustomerid());
			if(null != pCustomer){
				receipt.setPcustomername(pCustomer.getName());
			}
			DepartMent departMent = getDepartMentById(receipt.getSalesdept());
			if(null != departMent){
				receipt.setSalesdeptname(departMent.getName());
			}
			Personnel personnel = getPersonnelById(receipt.getSalesuser());
			if(null != personnel){
				receipt.setSalesusername(personnel.getName());
			}
			Personnel personnel2 = getPersonnelById(receipt.getIndooruserid());
			if(null != personnel2){
				receipt.setIndoorusername(personnel2.getName());
			}
			Personnel personnel3 = getPersonnelById(receipt.getPayeeid());
			if(null != personnel3){
				receipt.setPayeename(personnel3.getName());
			}
			SalesArea salesArea = getSalesareaByID(receipt.getSalesarea());
			if(null != salesArea){
				receipt.setSalesareaname(salesArea.getThisname());
			}
		}
		PageData pageData = new PageData(count,list,pageMap);
		//合计
		Receipt receipt = receiptHandMapper.getReceiptSumGroupByCustomer(pageMap);
		if(null != receipt){
			receipt.setCustomerid("合计");
		}else{
			receipt = new Receipt();
			receipt.setCustomerid("合计");
		}
		List footer = new ArrayList();
		footer.add(receipt);
		pageData.setFooter(footer);
		return pageData;
	}

    /**
     * 分客户获取回单列表数据(提供手机客户端接口)
     *
     * @param con
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Dec 11, 2014
     */
    @Override
    public List<Receipt> getReceiptHandGroupCustomerForPhone(String con) throws Exception {
        PageMap pageMap = new PageMap();
        pageMap.setCondition(new HashMap());
        pageMap.getCondition().put("con", con);
        String dataSql = getDataAccessRule("t_account_receipthand_bill","t");
        if(StringUtils.isNotEmpty(dataSql)){
            dataSql = dataSql.replaceAll("t.payeeid","c.payeeid");
        }
        pageMap.setDataSql(dataSql);
        SysUser sysUser = getSysUser();
        //判断是否品牌业务员
        String brandUserRoleName = getSysParamValue("BrandUserRoleName");
        boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
        if(isBrandUser){
            pageMap.getCondition().put("isBrandUser", true);
            pageMap.getCondition().put("personnelid", sysUser.getPersonnelid());
        }
        List<Receipt> list = receiptHandMapper.getReceiptListGroupByCustomerForPhone(pageMap);
        return list;
    }

    @Override
	public Map getReceiptListOfReceiptDetail(PageMap pageMap) throws Exception {
        String dataSql = getDataAccessRule("t_account_receipthand_bill","t");
        if(StringUtils.isNotEmpty(dataSql)){
            dataSql = dataSql.replaceAll("t.payeeid","c.payeeid");
        }
        pageMap.setDataSql(dataSql);
        SysUser sysUser = getSysUser();
        //判断是否品牌业务员
        String brandUserRoleName = getSysParamValue("BrandUserRoleName");
        boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
        if(isBrandUser){
            pageMap.getCondition().put("isBrandUser", true);
            pageMap.getCondition().put("personnelid", sysUser.getPersonnelid());
        }
		List<Receipt> list = receiptHandMapper.getReceiptListForReceiptHand(pageMap);
		BigDecimal totalreceipttaxamount = BigDecimal.ZERO;
		for(Receipt receipt : list){
			DepartMent departMent = getDepartMentById(receipt.getSalesdept());
			if(null != departMent){
				receipt.setSalesdeptname(departMent.getName());
			}
			Personnel personnel = getPersonnelById(receipt.getSalesuser());
			if(null != personnel){
				receipt.setSalesusername(personnel.getName());
			}
			Personnel personnel2 = getPersonnelById(receipt.getIndooruserid());
			if(null != personnel2){
				receipt.setIndoorusername(personnel2.getName());
			}
			Personnel personnel3 = getPersonnelById(receipt.getPayeeid());
			if(null != personnel3){
				receipt.setPayeename(personnel3.getName());
			}
			if(null != receipt.getTotalreceipttaxamount()){
				totalreceipttaxamount = totalreceipttaxamount.add(receipt.getTotalreceipttaxamount());
			}
			if("1".equals(receipt.getBilltype())){
				receipt.setBilltypename("发货回单");
			}else if("2".equals(receipt.getBilltype())){
				receipt.setBilltypename("销售退货通知单");
			}else if("3".equals(receipt.getBilltype())){
				receipt.setBilltypename("冲差单");
			}
		}
		Map map = new HashMap();
		map.put("list", list);
		map.put("totalreceipttaxamount", totalreceipttaxamount);
		return map;
	}

	@Override
	public Map getReceiptHandCustomerDetailList(String id) throws Exception {
		Map map2 = new HashMap();
		ReceiptHand receiptHand = receiptHandMapper.getReceiptHandInfo(id);
		if("3".equals(receiptHand.getStatus())){
			List<Map> list = receiptHandMapper.getReceiptHandCustomerDetailList(id);
			for(Map map : list){
				String customerid = (String)map.get("customerid");
				String handuserid = (String)map.get("handuserid");
				String addtime1 = null != map.get("addtime1") ? map.get("addtime1").toString() : "";
				String addtime2 = null != map.get("addtime2") ? map.get("addtime2").toString() : "";
				String businessdate1 = null != map.get("businessdate1") ? map.get("businessdate1").toString() : "";
				String businessdate2 = null != map.get("businessdate2") ? map.get("businessdate2").toString() : "";
				
				Customer customer = getCustomerByID(customerid);
				if(null != customer){
					map.put("customername", customer.getName());
				}
				//发货时段
				if(StringUtils.isNotEmpty(addtime1) && StringUtils.isNotEmpty(addtime2)){
					map.put("saleouttime", "1");
				}else{
					map.put("saleouttime", "0");
				}
				//回单时段
				if(StringUtils.isNotEmpty(businessdate1) && StringUtils.isNotEmpty(businessdate2)){
					map.put("receipttime", "1");
				}else{
					map.put("receipttime", "0");
				}
			}
			map2.put("list", list);
		}
		return map2;
	}

	@Override
	public List getReceiptHandBillListForPrint(String billid) throws Exception {
		ReceiptMapper receiptMapper = (ReceiptMapper)SpringContextUtils.getBean("receiptMapper");
		List<ReceiptHandBill> list = receiptHandMapper.getReceiptHandBillListForPrint(billid);
		for(ReceiptHandBill receiptHandBill : list){
			if("1".equals(receiptHandBill.getBilltype())){
				Map map = new HashMap();
				map.put("id", receiptHandBill.getRelatebillid());
				Receipt receipt = receiptMapper.getReceipt(map);
				if(null != receipt){
					receiptHandBill.setSaleorderid(receipt.getSaleorderid());
				}
			}
			Personnel personnel = getPersonnelById(receiptHandBill.getSalesuser());
			if(null != personnel){
				receiptHandBill.setSalesusername(personnel.getName());
			}
		}
		return list;
	}

	@Override
	public boolean updatePrintTimes(String id) throws Exception {
		boolean flag = receiptHandMapper.updatePrintTimes(id) > 0;
		return flag;
	}
	
	@Override
	public PageData getReceiptListForReceiptHand(PageMap pageMap)
			throws Exception {
        String dataSql = getDataAccessRule("t_account_receipthand_bill","t");
        if(StringUtils.isNotEmpty(dataSql)){
            dataSql = dataSql.replaceAll("t.payeeid","c.payeeid");
        }
        pageMap.setDataSql(dataSql);
        SysUser sysUser = getSysUser();
        //判断是否品牌业务员
        String brandUserRoleName = getSysParamValue("BrandUserRoleName");
        boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
        if(isBrandUser){
            pageMap.getCondition().put("isBrandUser", true);
            pageMap.getCondition().put("personnelid", sysUser.getPersonnelid());
        }
		int count = receiptHandMapper.getReceiptCountForAddReceiptHand(pageMap);
		List<Receipt> list = receiptHandMapper.getReceiptListForAddReceiptHand(pageMap);
		for(Receipt receipt : list){
			DepartMent departMent = getDepartMentById(receipt.getSalesdept());
			if(null != departMent){
				receipt.setSalesdeptname(departMent.getName());
			}
			Personnel personnel = getPersonnelById(receipt.getSalesuser());
			if(null != personnel){
				receipt.setSalesusername(personnel.getName());
			}
			Personnel personnel2 = getPersonnelById(receipt.getIndooruserid());
			if(null != personnel2){
				receipt.setIndoorusername(personnel2.getName());
			}
			Personnel personnel3 = getPersonnelById(receipt.getPayeeid());
			if(null != personnel3){
				receipt.setPayeename(personnel3.getName());
			}
			SalesArea salesArea = getSalesareaByID(receipt.getSalesarea());
			if(null != salesArea){
				receipt.setSalesareaname(salesArea.getThisname());
			}
//			StorageInfo storageInfo = getStorageInfoByID(receipt.getStorageid());
//			if(null != storageInfo){
//				receipt.setStoragename(storageInfo.getName());
//			}
		}
		PageData pageData = new PageData(count,list,pageMap);
		//合计
		Receipt receipt = receiptHandMapper.getReceiptSumForAddReceiptHand(pageMap);
		if(null != receipt){
			receipt.setId("合计");
		}else{
			receipt = new Receipt();
			receipt.setId("合计");
		}
		List footer = new ArrayList();
		footer.add(receipt);
		pageData.setFooter(footer);
		return pageData;
	}
}

