package com.hd.agent.account.service.impl.ext;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.CollectionOrderMapper;
import com.hd.agent.account.dao.CustomerCapitalMapper;
import com.hd.agent.account.dao.SalesStatementMapper;
import com.hd.agent.account.model.*;
import com.hd.agent.account.service.ext.ICollectionOrderForOaService;
import com.hd.agent.account.service.impl.BaseAccountServiceImpl;
import com.hd.agent.basefiles.model.Bank;
import com.hd.agent.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionOrderForOaServiceImpl extends BaseAccountServiceImpl implements ICollectionOrderForOaService {

    private CollectionOrderMapper collectionOrderMapper;

    private CustomerCapitalMapper customerCapitalMapper;

    private SalesStatementMapper salesStatementMapper;

    public CollectionOrderMapper getCollectionOrderMapper() {
        return collectionOrderMapper;
    }

    public void setCollectionOrderMapper(CollectionOrderMapper collectionOrderMapper) {
        this.collectionOrderMapper = collectionOrderMapper;
    }

    @Override
    public CustomerCapitalMapper getCustomerCapitalMapper() {
        return customerCapitalMapper;
    }

    @Override
    public void setCustomerCapitalMapper(CustomerCapitalMapper customerCapitalMapper) {
        this.customerCapitalMapper = customerCapitalMapper;
    }

    public SalesStatementMapper getSalesStatementMapper() {
        return salesStatementMapper;
    }

    public void setSalesStatementMapper(SalesStatementMapper salesStatementMapper) {
        this.salesStatementMapper = salesStatementMapper;
    }

    @Override
    public List<CollectionOrder> selectCollectionOrderByOaid(String oaid) throws Exception {
        return collectionOrderMapper.selectCollectionOrderByOaid(oaid);
    }

    @Override
    public boolean addCollectionOrder(CollectionOrder collectionOrder) throws Exception {
        if (isAutoCreate("t_account_collection_order")) {
            // 获取自动编号
            String id = getAutoCreateSysNumbderForeign(collectionOrder, "t_account_collection_order");
            collectionOrder.setId(id);
        }else{
            collectionOrder.setId("SKD-"+ CommonUtils.getDataNumberSendsWithRand());
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
        int i = collectionOrderMapper.addCollectionOrder(collectionOrder);
        return i>0;
    }

    @Override
    public Map auditCollectionOrder(String id, boolean issuper) throws Exception {
        Map map = new HashMap();
        CollectionOrder collectionOrder = collectionOrderMapper.getCollectionOrderInfo(id);
        SysUser sysUser = getSysUser();
        boolean flag = false;
        String msg = "";
        if(null!=collectionOrder){
            if(issuper){
                if(("2".equals(collectionOrder.getStatus()) || "6".equals(collectionOrder.getStatus()))){
                    CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(collectionOrder.getCustomerid());
                    if(null!=customerCapital){
                        customerCapital.setAmount(customerCapital.getAmount().add(collectionOrder.getAmount()));
                        //更新客户余额
                        customerCapitalMapper.updateCustomerCapitalAmont(customerCapital.getId(),collectionOrder.getAmount());
                    }else{
                        customerCapital = new CustomerCapital();
                        customerCapital.setId(collectionOrder.getCustomerid());
                        customerCapital.setAmount(collectionOrder.getAmount());
                        customerCapitalMapper.addCustomerCapital(customerCapital);
                    }
                    //更新银行账户金额 添加借贷单
                    BankAmountOthers bankAmountOthers = new BankAmountOthers();
                    bankAmountOthers.setBankid(collectionOrder.getBank());
                    bankAmountOthers.setDeptid(collectionOrder.getBankdeptid());
                    bankAmountOthers.setBillid(collectionOrder.getId());
                    bankAmountOthers.setBilltype("1");
                    bankAmountOthers.setLendtype("1");
                    bankAmountOthers.setAmount(collectionOrder.getAmount());
                    if(StringUtils.isEmpty(collectionOrder.getRemark())){
                        bankAmountOthers.setRemark("收款单："+collectionOrder.getId());
                    }else{
                        bankAmountOthers.setRemark(collectionOrder.getRemark()+" 收款单："+collectionOrder.getId());
                    }
                    bankAmountOthers.setBusinessdate(getAuditBusinessdate(collectionOrder.getBusinessdate()));
                    addAndAuditBankAmountOthers(bankAmountOthers);

                    //流水明细
                    CustomerCapitalLog customerCapitalLog = new CustomerCapitalLog();
                    customerCapitalLog.setCustomerid(collectionOrder.getCustomerid());
                    customerCapitalLog.setBillid(id);
                    customerCapitalLog.setBilltype("1");
                    customerCapitalLog.setPrtype("1");
                    customerCapitalLog.setIncomeamount(collectionOrder.getAmount());
                    customerCapitalLog.setBalanceamount(customerCapital.getAmount());
                    customerCapitalLog.setAdddeptid(sysUser.getDepartmentid());
                    customerCapitalLog.setAdddeptname(sysUser.getDepartmentname());
                    customerCapitalLog.setAdduserid(sysUser.getUserid());
                    customerCapitalLog.setAddusername(sysUser.getName());
                    if(StringUtils.isNotEmpty(collectionOrder.getRemark())){
                        collectionOrder.setRemark(collectionOrder.getRemark()+",收款单审核");
                    }else {
                        collectionOrder.setRemark("收款单审核");
                    }
                    customerCapitalLog.setRemark(collectionOrder.getRemark());
                    customerCapitalMapper.addCustomerCapitalLog(customerCapitalLog);

                    //生成客户对账单
                    SalesStatement salesStatement = new SalesStatement();
                    salesStatement.setCustomerid(collectionOrder.getCustomerid());
                    salesStatement.setBusinessdate(collectionOrder.getBusinessdate());
                    salesStatement.setBilltype("4");
                    salesStatement.setBillid(collectionOrder.getId());
                    salesStatement.setAmount(collectionOrder.getAmount());
                    salesStatement.setBillamount(collectionOrder.getAmount());
                    salesStatement.setRemark(collectionOrder.getRemark());
                    salesStatement.setAdduserid(sysUser.getUserid());
                    salesStatement.setAddusername(sysUser.getName());
                    salesStatementMapper.addSalesStatement(salesStatement);
//					//当收款单指定客户后 审核通过后 自动关闭
//					//未指定客户 收款单处于审核通过状态
//					if(null!=collectionOrder.getCustomerid() && !"".equals(collectionOrder.getCustomerid())){
//						int i = collectionOrderMapper.auditCollectionOrder(id,"3", sysUser.getUserid(), sysUser.getName());
//						flag = i>0;
//					}else{
//						int i = collectionOrderMapper.auditCollectionOrder(id,"3", sysUser.getUserid(), sysUser.getName());
//						flag = i>0;
//					}
                    String billdate=getAuditBusinessdate(collectionOrder.getBusinessdate());
                    int i = collectionOrderMapper.auditCollectionOrder(id,"3", sysUser.getUserid(), sysUser.getName(),collectionOrder.getVersion(),billdate);
                    flag = i>0;
                    if(!flag){
                        throw new Exception("收款单"+id+"审核失败，回滚数据");
                    }
                }else{
                    msg = "收款单+"+id+"已审核";
                }
            }else{
                msg = "收款单"+id+"金额小于0,需要超级审核";
            }
        }else{
            msg = "未找到收款单"+id;
        }

        map.put("flag", flag);
        map.put("msg", msg);
        return map;
    }
    /**
     * 添加并且审核关闭借贷单
     * @param bankAmountOthers
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年12月16日
     */
    public boolean addAndAuditBankAmountOthers(BankAmountOthers bankAmountOthers) throws Exception{
        boolean flag = false;
        if(null!=bankAmountOthers && org.apache.commons.lang.StringUtils.isNotEmpty(bankAmountOthers.getBankid()) && org.apache.commons.lang.StringUtils.isNotEmpty(bankAmountOthers.getLendtype())){
            if (isAutoCreate("t_account_bankamount_others")) {
                // 获取自动编号
                String id = getAutoCreateSysNumbderForeign(bankAmountOthers, "t_account_bankamount_others");
                bankAmountOthers.setId(id);
            }else{
                bankAmountOthers.setId("YHQT-"+CommonUtils.getDataNumber1());
            }
            SysUser sysUser = getSysUser();
            if(org.apache.commons.lang.StringUtils.isEmpty(bankAmountOthers.getBusinessdate())){
                bankAmountOthers.setBusinessdate(CommonUtils.getTodayDataStr());
            }

            if(org.apache.commons.lang.StringUtils.isEmpty(bankAmountOthers.getAdduserid())){
                bankAmountOthers.setAdduserid(sysUser.getUserid());
                bankAmountOthers.setAddusername(sysUser.getName());
                bankAmountOthers.setAdddeptid(sysUser.getDepartmentid());
                bankAmountOthers.setAdddeptname(sysUser.getDepartmentname());
            }
            if(org.apache.commons.lang.StringUtils.isEmpty(bankAmountOthers.getDeptid())){
                Bank bank = getBankInfoByID(bankAmountOthers.getBankid());
                if(null!=bank){
                    bankAmountOthers.setDeptid(bank.getBankdeptid());
                }
            }
            bankAmountOthers.setAudittime(new Date());
            bankAmountOthers.setAudituserid(sysUser.getUserid());
            bankAmountOthers.setAuditusername(sysUser.getName());
            bankAmountOthers.setClosetime(new Date());
            bankAmountOthers.setStatus("4");
            int i = bankAmountMapper.addBankAmountOthers(bankAmountOthers);
            if("1".equals(bankAmountOthers.getLendtype())){
                updateBankAmountIncome(bankAmountOthers.getBilltype(), bankAmountOthers.getId(), bankAmountOthers.getBankid(),bankAmountOthers.getDeptid(), bankAmountOthers.getAmount(), bankAmountOthers.getRemark());
            }else if("2".equals(bankAmountOthers.getLendtype())){
                updateBankAmountPay(bankAmountOthers.getBilltype(), bankAmountOthers.getId(), bankAmountOthers.getBankid(),bankAmountOthers.getDeptid(), bankAmountOthers.getAmount(), bankAmountOthers.getRemark());
            }
            flag = i>0;
        }
        return flag;
    }
}
