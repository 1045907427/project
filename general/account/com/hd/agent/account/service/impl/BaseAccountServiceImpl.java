/**
 * @(#)BaseAccountServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 1, 2013 chenwei 创建版本
 */
package com.hd.agent.account.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.account.dao.SalesInvoiceBillMapper;
import com.hd.agent.account.dao.SalesInvoiceMapper;
import com.hd.agent.account.model.*;
import org.apache.commons.lang.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.BankAmountMapper;
import com.hd.agent.basefiles.model.Bank;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;

/**
 *
 * 账务基本service
 * @author chenwei
 */
public class BaseAccountServiceImpl extends BaseFilesServiceImpl {
    /**
     * 银行账户金额dao
     */
    protected BankAmountMapper bankAmountMapper;

    private SalesInvoiceMapper salesInvoiceMapper;

    private SalesInvoiceBillMapper salesInvoiceBillMapper;

    public BankAmountMapper getBankAmountMapper() {
        return bankAmountMapper;
    }

    public void setBankAmountMapper(BankAmountMapper bankAmountMapper) {
        this.bankAmountMapper = bankAmountMapper;
    }

    public SalesInvoiceBillMapper getSalesInvoiceBillMapper() {
        return salesInvoiceBillMapper;
    }

    public void setSalesInvoiceBillMapper(SalesInvoiceBillMapper salesInvoiceBillMapper) {
        this.salesInvoiceBillMapper = salesInvoiceBillMapper;
    }

    public SalesInvoiceMapper getSalesInvoiceMapper() {
        return salesInvoiceMapper;
    }

    public void setSalesInvoiceMapper(SalesInvoiceMapper salesInvoiceMapper) {
        this.salesInvoiceMapper = salesInvoiceMapper;
    }

    /**
     * 银行账户收入金额
     * @param billtype			单据类型	0期初1收款单2付款单3日常费用支付4客户费用支付
     * @param billid			单据编号
     * @param bankid			银行档案编号
     * @param deptid			部门编号
     * @param amount			金额
     * @param remark			备注
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月24日
     */
    public boolean updateBankAmountIncome(String billtype,String billid,String bankid,String deptid,BigDecimal amount,String remark) throws Exception{
        SysUser sysUser = getSysUser();
        boolean flag = false;
        Bank bank = getBankInfoByID(bankid);
        if(null==amount){
            amount = BigDecimal.ZERO;
        }
        if(null!=bank){
            int i = 0;
            BankAmount bankAmount = bankAmountMapper.getBankAmountByBankid(bankid);
            if(null==bankAmount){
                bankAmount = new BankAmount();
                bankAmount.setBankid(bankid);
                bankAmount.setAmount(amount);
                i = bankAmountMapper.addBankAmount(bankAmount);
            }else{
                bankAmount.setAmount(bankAmount.getAmount().add(amount));
                i = bankAmountMapper.updateBankAmountByChange(bankAmount.getBankid(),amount);
            }

            BankAmountLog bankAmountLog = new BankAmountLog();
            bankAmountLog.setBankid(bankid);
            bankAmountLog.setBilltype(billtype);
            bankAmountLog.setBillid(billid);
            bankAmountLog.setInamount(amount);
            bankAmountLog.setOutamount(BigDecimal.ZERO);
            bankAmountLog.setBalanceamount(bankAmount.getAmount());
            bankAmountLog.setRemark(remark);
            bankAmountLog.setAdduserid(sysUser.getUserid());
            bankAmountLog.setAddusername(sysUser.getName());
            bankAmountLog.setAdddeptid(sysUser.getDepartmentid());
            bankAmountLog.setAdddeptname(sysUser.getDepartmentname());
            bankAmountMapper.addBankAmountLog(bankAmountLog);
            flag = i>0;
            //获取银行的所属部门
            if(StringUtils.isEmpty(deptid)){
                deptid = getSysParamValue("DefaultBankDeptid");
            }
            //部门金额调整
            DeptAmount deptAmount = bankAmountMapper.getDeptAmountByDeptid(deptid);
            if(null==deptAmount){
                deptAmount = new DeptAmount();
                deptAmount.setDeptid(deptid);
                deptAmount.setAmount(amount);
                bankAmountMapper.addDeptAmount(deptAmount);
            }else{
                deptAmount.setAmount(deptAmount.getAmount().add(amount));
                bankAmountMapper.updateDeptAmountChange(deptid, amount);
            }
            //添加部门金额变更日志
            DeptAmountLog deptAmountLog = new DeptAmountLog();
            deptAmountLog.setDeptid(deptid);
            deptAmountLog.setBankid(bankid);
            deptAmountLog.setBilltype(billtype);
            deptAmountLog.setBillid(billid);
            deptAmountLog.setInamount(amount);
            deptAmountLog.setOutamount(BigDecimal.ZERO);
            deptAmountLog.setBalanceamount(deptAmount.getAmount());
            deptAmountLog.setRemark(remark);
            deptAmountLog.setAdduserid(sysUser.getUserid());
            deptAmountLog.setAddusername(sysUser.getName());
            deptAmountLog.setAdddeptid(sysUser.getDepartmentid());
            deptAmountLog.setAdddeptname(sysUser.getDepartmentname());
            bankAmountMapper.addDeptAmountLog(deptAmountLog);
        }
        return flag;
    }
    /**
     * 银行账号支付金额
     * @param billtype			单据类型	0期初1收款单2付款单3日常费用支付4客户费用支付
     * @param billid			单据编号
     * @param bankid			银行档案编号
     * @param deptid 			部门编号
     * @param amount			金额
     * @param remark			备注
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月24日
     */
    public boolean updateBankAmountPay(String billtype,String billid,String bankid,String deptid,BigDecimal amount,String remark) throws Exception{
        SysUser sysUser = getSysUser();
        boolean flag = false;
        Bank bank = getBankInfoByID(bankid);
        if(null==amount){
            amount = BigDecimal.ZERO;
        }
        if(null!=bank){
            int i = 0;
            BankAmount bankAmount = bankAmountMapper.getBankAmountByBankid(bankid);
            if(null==bankAmount){
                bankAmount = new BankAmount();
                bankAmount.setBankid(bankid);
                bankAmount.setAmount(amount.negate());
                i = bankAmountMapper.addBankAmount(bankAmount);
            }else{
                bankAmount.setAmount(bankAmount.getAmount().subtract(amount));
                i = bankAmountMapper.updateBankAmountByChange(bankAmount.getBankid(),amount.negate());
            }

            BankAmountLog bankAmountLog = new BankAmountLog();
            bankAmountLog.setBankid(bankid);
            bankAmountLog.setBilltype(billtype);
            bankAmountLog.setBillid(billid);
            bankAmountLog.setInamount(BigDecimal.ZERO);
            bankAmountLog.setOutamount(amount);
            bankAmountLog.setBalanceamount(bankAmount.getAmount());
            bankAmountLog.setRemark(remark);
            bankAmountLog.setAdduserid(sysUser.getUserid());
            bankAmountLog.setAddusername(sysUser.getName());
            bankAmountLog.setAdddeptid(sysUser.getDepartmentid());
            bankAmountLog.setAdddeptname(sysUser.getDepartmentname());
            bankAmountMapper.addBankAmountLog(bankAmountLog);

            flag = i>0;
            //获取银行的所属部门
            if(StringUtils.isEmpty(deptid)){
                deptid = getSysParamValue("DefaultBankDeptid");
            }
            //部门金额调整
            DeptAmount deptAmount = bankAmountMapper.getDeptAmountByDeptid(deptid);
            if(null==deptAmount){
                deptAmount = new DeptAmount();
                deptAmount.setDeptid(deptid);
                deptAmount.setAmount(amount.negate());
                bankAmountMapper.addDeptAmount(deptAmount);
            }else{
                deptAmount.setAmount(deptAmount.getAmount().subtract(amount));
                bankAmountMapper.updateDeptAmountChange(deptid, amount.negate());
            }
            //添加部门金额变更日志
            DeptAmountLog deptAmountLog = new DeptAmountLog();
            deptAmountLog.setDeptid(deptid);
            deptAmountLog.setBankid(bankid);
            deptAmountLog.setBilltype(billtype);
            deptAmountLog.setBillid(billid);
            deptAmountLog.setInamount(BigDecimal.ZERO);
            deptAmountLog.setOutamount(amount);
            deptAmountLog.setBalanceamount(deptAmount.getAmount());
            deptAmountLog.setRemark(remark);
            deptAmountLog.setAdduserid(sysUser.getUserid());
            deptAmountLog.setAddusername(sysUser.getName());
            deptAmountLog.setAdddeptid(sysUser.getDepartmentid());
            deptAmountLog.setAdddeptname(sysUser.getDepartmentname());
            bankAmountMapper.addDeptAmountLog(deptAmountLog);
        }
        return flag;
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
        if(null!=bankAmountOthers && StringUtils.isNotEmpty(bankAmountOthers.getBankid()) && StringUtils.isNotEmpty(bankAmountOthers.getLendtype())){
            if (isAutoCreate("t_account_bankamount_others")) {
                // 获取自动编号
                String id = getAutoCreateSysNumbderForeign(bankAmountOthers, "t_account_bankamount_others");
                bankAmountOthers.setId(id);
            }else{
                bankAmountOthers.setId("YHQT-"+CommonUtils.getDataNumber1());
            }
            SysUser sysUser = getSysUser();
            if(StringUtils.isEmpty(bankAmountOthers.getBusinessdate())){
                bankAmountOthers.setBusinessdate(CommonUtils.getTodayDataStr());
            }

            if(StringUtils.isEmpty(bankAmountOthers.getAdduserid())){
                bankAmountOthers.setAdduserid(sysUser.getUserid());
                bankAmountOthers.setAddusername(sysUser.getName());
                bankAmountOthers.setAdddeptid(sysUser.getDepartmentid());
                bankAmountOthers.setAdddeptname(sysUser.getDepartmentname());
            }
            if(StringUtils.isEmpty(bankAmountOthers.getDeptid())){
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

    /**
     * 根据销售开票编码对比该销售开票明细与对应的销售核销明细是否一致,true相同，false不相同
     * @param invoicebillid
     * @param invoiceid
     * @return
     * @throws Exception
     */
    public boolean checkSalesInvoiceBillDetailsSameAsSalesInvoiceDetails(String invoicebillid,String invoiceid)throws Exception{
        boolean flag = false;
        int count = 0;
        Map map = new HashMap();
        map.put("salesinvoicebillid",invoicebillid);
        map.put("salesinvoiceid",invoiceid);
        List detailList = getSalesInvoiceMapper().getSalesInvoiceDetailList(invoiceid);
        List billDetailList = getSalesInvoiceBillMapper().getSalesInvoiceBillDetailList(invoicebillid);
        if(detailList.size() >= billDetailList.size()){
            count = getSalesInvoiceMapper().checkInvoiceDetailSameAsInvoiceBillDetailByMap(map);
        }else{
            count = getSalesInvoiceBillMapper().checkInvoiceBillDetailSameAsInvoiceDetailByMap(map);
        }
        if(count > 0){
            flag = false;
        }else{
            flag = true;
        }
        return flag;
    }

}

