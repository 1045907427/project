/**
 * @(#)OAPayServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年11月24日 chenwei 创建版本
 */
package com.hd.agent.oa.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.model.BankAmountOthers;
import com.hd.agent.account.model.CollectionOrder;
import com.hd.agent.account.model.CustomerPushBalance;
import com.hd.agent.account.model.Payorder;
import com.hd.agent.account.service.IAccountForOAService;
import com.hd.agent.account.service.ICollectionOrderService;
import com.hd.agent.account.service.ICustomerPushBanlanceService;
import com.hd.agent.account.service.IPayorderService;
import com.hd.agent.account.service.ext.ICollectionOrderForOaService;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.journalsheet.dao.DeptDailyCostMapper;
import com.hd.agent.journalsheet.model.CustomerCostPayable;
import com.hd.agent.journalsheet.model.DeptDailyCost;
import com.hd.agent.journalsheet.model.MatcostsInput;
import com.hd.agent.journalsheet.service.IDeptDailyCostService;
import com.hd.agent.journalsheet.service.IJournalSheetService;
import com.hd.agent.oa.model.*;
import com.hd.agent.oa.service.IOaPayService;
import org.apache.commons.lang3.StringUtils;

/**
 * OA支付相关接口实现方法
 *
 * @author chenwei
 */
public class OaPayServiceImpl extends BaseFilesServiceImpl implements IOaPayService {

    /**
     * 银行账户金额接口
     */
    private IAccountForOAService accountForOAService;

    /**
     * 部门日常费用
     */
    private IDeptDailyCostService deptDailyCostService;

    private DeptDailyCostMapper deptDailyCostMapper;

    private IPayorderService payorderService;

    private IJournalSheetService journalSheetService;

    private ICollectionOrderForOaService collectionOrderForOaService;

    private ICustomerPushBanlanceService customerPushBanlanceService;

    public DeptDailyCostMapper getDeptDailyCostMapper() {
        return deptDailyCostMapper;
    }

    public void setAccountForOAService(IAccountForOAService accountForOAService) {
        this.accountForOAService = accountForOAService;
    }

    public IDeptDailyCostService getDeptDailyCostService() {
        return deptDailyCostService;
    }

    public void setDeptDailyCostService(IDeptDailyCostService deptDailyCostService) {
        this.deptDailyCostService = deptDailyCostService;
    }

    public void setDeptDailyCostMapper(DeptDailyCostMapper deptDailyCostMapper) {
        this.deptDailyCostMapper = deptDailyCostMapper;
    }

    public IAccountForOAService getAccountForOAService() {
        return accountForOAService;
    }

    public IPayorderService getPayorderService() {
        return payorderService;
    }

    public void setPayorderService(IPayorderService payorderService) {
        this.payorderService = payorderService;
    }

    public IJournalSheetService getJournalSheetService() {
        return journalSheetService;
    }

    public void setJournalSheetService(IJournalSheetService journalSheetService) {
        this.journalSheetService = journalSheetService;
    }

    public ICollectionOrderForOaService getCollectionOrderForOaService() {
        return collectionOrderForOaService;
    }

    public void setCollectionOrderForOaService(ICollectionOrderForOaService collectionOrderForOaService) {
        this.collectionOrderForOaService = collectionOrderForOaService;
    }

    public ICustomerPushBanlanceService getCustomerPushBanlanceService() {
        return customerPushBanlanceService;
    }

    public void setCustomerPushBanlanceService(ICustomerPushBanlanceService customerPushBanlanceService) {
        this.customerPushBanlanceService = customerPushBanlanceService;
    }

    @Override
    public int addPayOrderBySupplierPay(OaSupplierPay oaSupplierpay)
            throws Exception {
        Payorder payorder = new Payorder();
        payorder.setSupplierid(oaSupplierpay.getSupplierid());
        payorder.setOaid(oaSupplierpay.getOaid());
        payorder.setBank(oaSupplierpay.getPaybank());
        payorder.setAmount(oaSupplierpay.getPayamount());
        payorder.setPaytype(oaSupplierpay.getIsprepay());
        payorder.setRemark("OA编号：" + oaSupplierpay.getOaid() + "，" + oaSupplierpay.getRemark());
        payorder.setInvoiceno("");

        payorder.setAdduserid(oaSupplierpay.getAdduserid());
        payorder.setAddusername(oaSupplierpay.getAddusername());
        payorder.setAdddeptid(oaSupplierpay.getAdddeptid());
        payorder.setAdddeptname(oaSupplierpay.getAdddeptname());
        boolean flag = accountForOAService.addPayOrder(payorder);
        return flag ? 1 : 0;
    }

    @Override
    public int addBankBillByOaCustomerPay(OaCustomerPay oaCustomerPay) throws Exception {

        if(oaCustomerPay == null) {
            return 0;
        }

        if(!checkBankBillByOaCustomerPay(oaCustomerPay)) {
            return 0;
        }

        BankAmountOthers bankAmountOthers = new BankAmountOthers();
        //借贷类型 1借(收入)2贷(支出)
        bankAmountOthers.setLendtype("2");
        bankAmountOthers.setBankid(oaCustomerPay.getPaybank());
        bankAmountOthers.setDeptid(oaCustomerPay.getDeptid());
        //单据类型（0期初1收款单2付款单,不在该单据内）3日常费用支付4客户费用支付5个人借款6预付款7转账
        bankAmountOthers.setBilltype("4");
        bankAmountOthers.setBillid(oaCustomerPay.getId());
        bankAmountOthers.setOaid(oaCustomerPay.getOaid());
        bankAmountOthers.setAmount(oaCustomerPay.getPayamount());
        bankAmountOthers.setUpamount(oaCustomerPay.getUpperpayamount());
        bankAmountOthers.setOppid(oaCustomerPay.getCustomerid());
        bankAmountOthers.setOppname(oaCustomerPay.getCollectionname());
        bankAmountOthers.setOppbank(oaCustomerPay.getCollectionbank());
        bankAmountOthers.setOppbankno(oaCustomerPay.getCollectionbankno());
        bankAmountOthers.setInvoiceamount(oaCustomerPay.getBillamount());
        bankAmountOthers.setInvoicedate(oaCustomerPay.getBilldate());
        bankAmountOthers.setInvoiceid("");
        bankAmountOthers.setInvoicetype(oaCustomerPay.getBilltype());
        bankAmountOthers.setRemark(oaCustomerPay.getRemark());

        bankAmountOthers.setAdduserid(oaCustomerPay.getAdduserid());
        bankAmountOthers.setAddusername(oaCustomerPay.getAddusername());
        bankAmountOthers.setAdddeptid(oaCustomerPay.getAdddeptid());
        bankAmountOthers.setAdddeptname(oaCustomerPay.getAdddeptname());
        boolean flag = accountForOAService.addBankAmountOthers(bankAmountOthers);
        return flag ? 1 : 0;
    }

    @Override
    public boolean addBankBillByOaPersonalLoan(OaPersonalLoan oaPersonalLoan)
            throws Exception {
        boolean flag = false;
        if(null!=oaPersonalLoan){
            BankAmountOthers bankAmountOthers = new BankAmountOthers();
            //借贷类型 1借(收入)2贷(支出)
            bankAmountOthers.setLendtype("2");
            bankAmountOthers.setBankid(oaPersonalLoan.getPaybank());
            bankAmountOthers.setDeptid(oaPersonalLoan.getPaydeptid());
            //单据类型0期初1收款单2付款单3日常费用支付4客户费用支付5个人借款6预付款7转账
            if("1".equals(oaPersonalLoan.getBilltype())){
                bankAmountOthers.setBilltype("5");
            }else if("2".equals(oaPersonalLoan.getBilltype())){
                bankAmountOthers.setBilltype("6");
            }
            bankAmountOthers.setBillid(oaPersonalLoan.getId());
            bankAmountOthers.setOaid(oaPersonalLoan.getOaid());
            bankAmountOthers.setAmount(oaPersonalLoan.getAmount());
            bankAmountOthers.setUpamount(oaPersonalLoan.getUpamount());
            bankAmountOthers.setOppid(oaPersonalLoan.getCollectuserid());
            bankAmountOthers.setOppname(oaPersonalLoan.getCollectusername());
            bankAmountOthers.setOppbank("");
            bankAmountOthers.setOppbankno("");
            bankAmountOthers.setInvoiceamount(BigDecimal.ZERO);
            bankAmountOthers.setInvoicedate("");
            bankAmountOthers.setInvoiceid("");
            bankAmountOthers.setInvoicetype("");
            bankAmountOthers.setRemark(oaPersonalLoan.getRemark());

            bankAmountOthers.setAdduserid(oaPersonalLoan.getAdduserid());
            bankAmountOthers.setAddusername(oaPersonalLoan.getAddusername());
            bankAmountOthers.setAdddeptid(oaPersonalLoan.getAdddeptid());
            bankAmountOthers.setAdddeptname(oaPersonalLoan.getAdddeptname());
            bankAmountOthers.setOppbank(oaPersonalLoan.getCollectbank());
            bankAmountOthers.setOppbankno(oaPersonalLoan.getCollectbankno());
            flag = accountForOAService.addBankAmountOthers(bankAmountOthers);
        }
        return flag;
    }

    @Override
    public boolean addDeptDailyCostByOaInnerShare(OaInnerShare OaInnerShare)
            throws Exception {
        boolean flag = false;
        if(null!=OaInnerShare){
            //费用摊出部门
            DeptDailyCost deptDailyCostOut = new DeptDailyCost();
            deptDailyCostOut.setBankid("");
            deptDailyCostOut.setIsbankpay("0");
            deptDailyCostOut.setDeptid(OaInnerShare.getCollectdeptid());
            deptDailyCostOut.setCostsort(OaInnerShare.getCostsort());
            deptDailyCostOut.setAmount(OaInnerShare.getAmount().negate());
            deptDailyCostOut.setOaid(OaInnerShare.getOaid());
            deptDailyCostOut.setRemark(OaInnerShare.getRemark());
            deptDailyCostOut.setAdduserid(OaInnerShare.getAdduserid());
            deptDailyCostOut.setAddusername(OaInnerShare.getAddusername());
            boolean flagout = deptDailyCostService.addAndAuditDeptDailyCost(deptDailyCostOut);
            //费用摊入部门
            DeptDailyCost deptDailyCostIn = new DeptDailyCost();
            deptDailyCostIn.setBankid("");
            deptDailyCostIn.setIsbankpay("0");
            deptDailyCostIn.setDeptid(OaInnerShare.getPaydeptid());
            deptDailyCostIn.setCostsort(OaInnerShare.getCostsort());
            deptDailyCostIn.setAmount(OaInnerShare.getAmount());
            deptDailyCostIn.setOaid(OaInnerShare.getOaid());
            deptDailyCostIn.setRemark(OaInnerShare.getRemark());
            deptDailyCostIn.setAdduserid(OaInnerShare.getAdduserid());
            deptDailyCostIn.setAddusername(OaInnerShare.getAddusername());
            boolean flagin = deptDailyCostService.addAndAuditDeptDailyCost(deptDailyCostIn);
            if(flagout && flagin){
                flag = true;
            }else{
                throw new Exception("内部分摊出错！");
            }
        }

        return flag;
    }

    @Override
    public boolean addDeptDailCostByDailyPay(OaDailyPay dailyPay, List<OaDailyPayDetail> detailList) throws Exception {
        boolean flag = false;
        if(null!=dailyPay){
            //费用摊入部门
            DeptDailyCost deptDailyCost = new DeptDailyCost();
            deptDailyCost.setBankid(dailyPay.getPaybank());
            deptDailyCost.setIsbankpay("1");
            deptDailyCost.setDeptid(dailyPay.getApplydeptid());
            deptDailyCost.setCostsort(dailyPay.getCostsort());
            deptDailyCost.setAmount(dailyPay.getPayamount());
            deptDailyCost.setOaid(dailyPay.getOaid());
            deptDailyCost.setRemark(dailyPay.getRemark());
            deptDailyCost.setAdduserid(dailyPay.getAdduserid());
            deptDailyCost.setAddusername(dailyPay.getAddusername());

            deptDailyCost.setUpamount(dailyPay.getUpperpayamount());
            deptDailyCost.setOppname(dailyPay.getCollectionname());
            deptDailyCost.setOppbank(dailyPay.getCollectionbank());
            deptDailyCost.setOppbankno(dailyPay.getCollectionbankno());
            flag = deptDailyCostService.addAndAuditDeptDailyCost(deptDailyCost);
        }
        return flag;
    }

    @Override
    public int rollbackBankBillByOaCustomerPay(OaCustomerPay pay) throws Exception {

        if(checkBankBillByOaCustomerPay(pay)) {
            return 0;
        }

        Map param = new HashMap();
        param.put("oaid", pay.getOaid());
        param.put("billtype", 4);
        List<BankAmountOthers> list = accountForOAService.selectBankAmountOthersList(param);
        Collections.sort(list, new Comparator<BankAmountOthers>() {
            @Override
            public int compare(BankAmountOthers b1, BankAmountOthers b2) {
                return b2.getAddtime().compareTo(b1.getAddtime());
            }
        });

        BankAmountOthers oldBankAmountOthers = list.get(0);

        // 支出单已生成，未生成收入单
        BankAmountOthers bankAmountOthers = new BankAmountOthers();
        //借贷类型 1借(收入)2贷(支出)
        bankAmountOthers.setLendtype("1");
        bankAmountOthers.setBankid(oldBankAmountOthers.getBankid());
        bankAmountOthers.setDeptid(oldBankAmountOthers.getDeptid());
        //单据类型（0期初1收款单2付款单,不在该单据内）3日常费用支付4客户费用支付5个人借款6预付款7转账
        bankAmountOthers.setBilltype("4");
        bankAmountOthers.setBillid(oldBankAmountOthers.getBillid());
        bankAmountOthers.setOaid(oldBankAmountOthers.getOaid());
        bankAmountOthers.setAmount(oldBankAmountOthers.getAmount());
        bankAmountOthers.setUpamount(oldBankAmountOthers.getUpamount());
        bankAmountOthers.setOppid(oldBankAmountOthers.getOppid());
        bankAmountOthers.setOppname(oldBankAmountOthers.getOppname());
        bankAmountOthers.setOppbank(oldBankAmountOthers.getOppbank());
        bankAmountOthers.setOppbankno(oldBankAmountOthers.getOppbankno());
        bankAmountOthers.setInvoiceamount(oldBankAmountOthers.getInvoiceamount());
        bankAmountOthers.setInvoicedate(oldBankAmountOthers.getInvoicedate());
        bankAmountOthers.setInvoiceid("");
        bankAmountOthers.setInvoicetype(oldBankAmountOthers.getInvoicetype());
        bankAmountOthers.setRemark("OA回退，编号：" + pay.getOaid());

        bankAmountOthers.setAdduserid(pay.getAdduserid());
        bankAmountOthers.setAddusername(pay.getAddusername());
        bankAmountOthers.setAdddeptid(pay.getAdddeptid());
        bankAmountOthers.setAdddeptname(pay.getAdddeptname());
        boolean flag = accountForOAService.addBankAmountOthers(bankAmountOthers);
        return flag ? 1 : 0;
    }

    @Override
    public boolean rollbackDeptDailCostByDailyPay(OaDailyPay pay, List<OaDailyPayDetail> list) throws Exception {

        if(checkDeptDailCostByDailyPay(pay)) {

            return false;
        }

        boolean flag = false;
        if(null != pay){
            //费用摊入部门
            DeptDailyCost deptDailyCost = new DeptDailyCost();
            deptDailyCost.setBankid(pay.getPaybank());
            deptDailyCost.setIsbankpay("1");
            deptDailyCost.setDeptid(pay.getApplydeptid());
            deptDailyCost.setCostsort(pay.getCostsort());
            deptDailyCost.setAmount(pay.getPayamount() == null ? new BigDecimal(0) : pay.getPayamount().negate());
            deptDailyCost.setOaid(pay.getOaid());
            deptDailyCost.setRemark("OA回退，编号" + pay.getOaid());
            deptDailyCost.setAdduserid(pay.getAdduserid());
            deptDailyCost.setAddusername(pay.getAddusername());

            deptDailyCost.setUpamount(pay.getUpperpayamount());
            deptDailyCost.setOppname(pay.getCollectionname());
            deptDailyCost.setOppbank(pay.getCollectionbank());
            deptDailyCost.setOppbankno(pay.getCollectionbankno());
            flag = deptDailyCostService.rollbackAndAuditDeptDailyCost(deptDailyCost);
        }
        return flag;
    }

    @Override
    public int rollbackPayOrderBySupplierPay(OaSupplierPay pay) throws Exception {

        if(checkPayOrderBySupplierPay(pay)) {

            return 0;
        }

        Payorder payorder = new Payorder();
        payorder.setSupplierid(pay.getSupplierid());
        payorder.setOaid(pay.getOaid());
        payorder.setBank(pay.getPaybank());
        payorder.setAmount(pay.getPayamount() == null ? new BigDecimal(0) : pay.getPayamount().negate());
        payorder.setPaytype(pay.getIsprepay());
        // payorder.setRemark(pay.getRemark());
        payorder.setRemark("OA回退，编号：" + pay.getOaid());
        payorder.setInvoiceno("");

        payorder.setAdduserid(pay.getAdduserid());
        payorder.setAddusername(pay.getAddusername());
        payorder.setAdddeptid(pay.getAdddeptid());
        payorder.setAdddeptname(pay.getAdddeptname());

        boolean flag = accountForOAService.rollbackdPayOrder(payorder);
        return flag ? 1 : 0;

    }

    @Override
    public boolean checkBankBillByOaCustomerPay(OaCustomerPay pay) throws Exception {

        Map param = new HashMap();
        param.put("oaid", pay.getOaid());
        param.put("billtype", 4);
        List<BankAmountOthers> list = accountForOAService.selectBankAmountOthersList(param);

        // 接单记录有2n条，说明有借和贷相互抵消
        if(list.size() % 2 == 0) {

            return true;
        }

        return false;
    }

    @Override
    public boolean checkDeptDailCostByDailyPay(OaDailyPay pay) throws Exception {

        List list2 = deptDailyCostMapper.selectDeptDailyCostByOaid(pay.getOaid());

        // 部门费用存在2n条记录，说明已经生成相反的部门费用
        if(list2.size() % 2 == 0) {

            return true;
        }

        return false;
    }

    @Override
    public boolean checkPayOrderBySupplierPay(OaSupplierPay order) throws Exception {

        List list = payorderService.selectPayOrderByOaid(order.getOaid());
        if(list.size() % 2 == 0) {

            return true;
        }

        return false;
    }

    @Override
    public int addCustomerFeeByCustomerPay(OaCustomerPay pay) throws Exception {

        if(pay == null) {
            return 0;
        }

        if(!checkCustomerFeeByOaCustomerPay(pay)) {
            return 0;
        }

        CustomerCostPayable customerCostPayable = new CustomerCostPayable();
        customerCostPayable.setCustomerid(pay.getCustomerid());
        // customerCostPayable.setSupplierid(pay.getSupplierid());
        Customer customer = getCustomerByID(pay.getCustomerid());
        if(null!=customer){
            customerCostPayable.setPcustomerid(customer.getPid());
            if("0".equals(customer.getIslast())){
                customerCostPayable.setPcustomerid(pay.getCustomerid());
            }
        }
        customerCostPayable.setBusinessdate(CommonUtils.getTodayDataStr());
        customerCostPayable.setIspay("1");
        customerCostPayable.setExpensesort(pay.getExpensesort());
        customerCostPayable.setOaid(pay.getOaid());
        customerCostPayable.setRelateoaid(pay.getRelateoaid());
        customerCostPayable.setBilltype("2");	// 支付
        customerCostPayable.setSupplierid(pay.getSupplierid());
        customerCostPayable.setApplyuserid(pay.getAdduserid());
//            customerCostPayable.setApplydeptid(getSysUserById(pay.getAdduserid()).getDepartmentid());
        // 3418 瑞家：品牌部门取错了、
        customerCostPayable.setApplydeptid(pay.getDeptid());
        customerCostPayable.setDeptid(pay.getDeptid());
        //费用金额
        customerCostPayable.setAmount(pay.getPayamount() == null ? BigDecimal.ZERO : pay.getPayamount());
        customerCostPayable.setSourcefrom("13");    // 13：客户费用申请单
        customerCostPayable.setPaytype("1");        // 1：支付
        customerCostPayable.setRemark("OA号：" + pay.getOaid() + " " + pay.getRemark());
        customerCostPayable.setBankid(pay.getPaybank());
        boolean flag = journalSheetService.addCustomerCostPayable(customerCostPayable);
        return flag ? 1 : 0;
    }

    @Override
    public int rollbackCustomerFeeByCustomerPay(OaCustomerPay pay) throws Exception {

        if(checkCustomerFeeByOaCustomerPay(pay)) {
            return 0;
        }

        List<CustomerCostPayable> list = journalSheetService.selectCustomerCostPayablByOaid(pay.getOaid());

        int payCount = 0;
        CustomerCostPayable pre = null;
        for(CustomerCostPayable payable : list) {
            if("3".equals(payable.getBilltype())) {
                payCount++;
                pre = ((pre == null) ? payable : pre);
            }
        }
        if(payCount % 2 == 1) {
            return 0;
        }

        CustomerCostPayable customerCostPayable = new CustomerCostPayable();
        customerCostPayable.setCustomerid(pre.getCustomerid());
        // customerCostPayable.setSupplierid(pay.getSupplierid());
        Customer customer = getCustomerByID(pre.getCustomerid());
        customerCostPayable.setPcustomerid(pre.getCustomerid());
        customerCostPayable.setBusinessdate(CommonUtils.getTodayDataStr());
        customerCostPayable.setIspay("1");
        customerCostPayable.setExpensesort(pre.getExpensesort());
        customerCostPayable.setOaid(pre.getOaid());
        customerCostPayable.setRelateoaid(pre.getRelateoaid());
        customerCostPayable.setBilltype("1");	// 支付
        customerCostPayable.setSupplierid(pre.getSupplierid());
        customerCostPayable.setApplyuserid(pay.getAdduserid());
//            customerCostPayable.setApplydeptid(getSysUserById(pay.getAdduserid()).getDepartmentid());
        // 3418 瑞家：品牌部门取错了、
        customerCostPayable.setApplydeptid(pay.getDeptid());
        customerCostPayable.setDeptid(pre.getDeptid());
        //费用金额
        customerCostPayable.setAmount(pre.getAmount());
        customerCostPayable.setSourcefrom("13");    // 13：客户费用申请单
        customerCostPayable.setPaytype("1");        // 1：支付
        customerCostPayable.setRemark("OA驳回：" + pay.getOaid());
        customerCostPayable.setBankid(pre.getBankid());
        boolean flag = journalSheetService.addCustomerCostPayable(customerCostPayable);

//        boolean flag = journalSheetService.deleteCustomerCostPayableByOaid(pay.getOaid());
        return flag ? 1 : 0;
    }

    @Override
    public boolean rollbackBankBillByOaPersonalLoan(OaPersonalLoan oaPersonalLoan) throws Exception {

        List list = accountForOAService.selectBankAmountOthersByOaid(oaPersonalLoan.getOaid());

        if(list.size() % 2 == 0) {

            return true;
        }

        boolean flag = false;
        if(null!=oaPersonalLoan){
            BankAmountOthers bankAmountOthers = new BankAmountOthers();
            //借贷类型 1借(收入)2贷(支出)
            bankAmountOthers.setLendtype("1");
            bankAmountOthers.setBankid(oaPersonalLoan.getPaybank());
            bankAmountOthers.setDeptid(oaPersonalLoan.getPaydeptid());
            //单据类型0期初1收款单2付款单3日常费用支付4客户费用支付5个人借款6预付款7转账
            if("1".equals(oaPersonalLoan.getBilltype())){
                bankAmountOthers.setBilltype("5");
            }else if("2".equals(oaPersonalLoan.getBilltype())){
                bankAmountOthers.setBilltype("6");
            }
            bankAmountOthers.setBillid(oaPersonalLoan.getId());
            bankAmountOthers.setOaid(oaPersonalLoan.getOaid());
            bankAmountOthers.setAmount(oaPersonalLoan.getAmount());
            bankAmountOthers.setUpamount(oaPersonalLoan.getUpamount());
            bankAmountOthers.setOppid(oaPersonalLoan.getCollectuserid());
            bankAmountOthers.setOppname(oaPersonalLoan.getCollectusername());
            bankAmountOthers.setOppbank("");
            bankAmountOthers.setOppbankno("");
            bankAmountOthers.setInvoiceamount(BigDecimal.ZERO);
            bankAmountOthers.setInvoicedate("");
            bankAmountOthers.setInvoiceid("");
            bankAmountOthers.setInvoicetype("");
            bankAmountOthers.setRemark(oaPersonalLoan.getRemark());

            bankAmountOthers.setAdduserid(oaPersonalLoan.getAdduserid());
            bankAmountOthers.setAddusername(oaPersonalLoan.getAddusername());
            bankAmountOthers.setAdddeptid(oaPersonalLoan.getAdddeptid());
            bankAmountOthers.setAdddeptname(oaPersonalLoan.getAdddeptname());
            bankAmountOthers.setOppbank(oaPersonalLoan.getCollectbank());
            bankAmountOthers.setOppbankno(oaPersonalLoan.getCollectbankno());
            flag = accountForOAService.addBankAmountOthers(bankAmountOthers);
        }
        return flag;
    }

    @Override
    public boolean checkBankBillByOaPersonalLoan(OaPersonalLoan oaPersonalLoan) throws Exception {

        List list = accountForOAService.selectBankAmountOthersByOaid(oaPersonalLoan.getOaid());

        if(list.size() % 2 == 0) {

            return true;
        }

        return false;
    }

    @Override
    public boolean checkDeptDailyCostByOaInnerShare(OaInnerShare oaInnerShare) throws Exception {
        return false;
    }

    @Override
    public int rollbackBillByOaCustomerFee(OaCustomerFee fee) throws Exception {
        return 0;
    }

    @Override
    public boolean checkCollectionOrderByOaCustomerFee(OaCustomerFee fee) throws Exception {
        return collectionOrderForOaService.selectCollectionOrderByOaid(fee.getOaid()).size() % 2 == 0;
    }

    @Override
    public boolean checkMatcostsInputByOaCustomerFee(OaCustomerFee fee) throws Exception {

        List<MatcostsInput> list = journalSheetService.selectMatcostsInputByOaid(fee.getOaid());

        if(list.size() % 2 == 1) {
            return false;
        }

        if(list.size() == 0) {
            return true;
        }

        if("3".equals(list.get(0).getSourcefrome())) {

            return true;
        }

        return false;
    }

    @Override
    public boolean checkCustomerFeeByOaCustomerFee(OaCustomerFee fee) throws Exception {

        List<CustomerCostPayable> list = journalSheetService.selectCustomerCostPayablByOaid(fee.getOaid());

        if(list.size() % 2 == 1) {
            return false;
        }

        if(list.size() == 0) {
            return true;
        }

        if("16".equals(list.get(0).getSourcefrom())) {

            return true;
        }

        return false;
    }

    @Override
    public boolean checkCustomerFeeByOaCustomerPay(OaCustomerPay fee) throws Exception {

        List<CustomerCostPayable> list = journalSheetService.selectCustomerCostPayablByOaid(fee.getOaid());

        if(list.size() % 2 == 1) {
            return false;
        }

        if(list.size() == 0) {
            return true;
        }

        return false;
    }

    @Override
    public int addCollectionOrderByOaCustomerFee(OaCustomerFee fee) throws Exception {

        if(!checkCollectionOrderByOaCustomerFee(fee)) {

            return 0;
        }

        CollectionOrder order = new CollectionOrder();
        order.setBusinessdate(CommonUtils.getTodayDataStr());
        order.setCustomerid(fee.getCustomerid());
        order.setAmount(fee.getPayamount());
        order.setCollectiontype("3");       // 3:费用
        order.setBank(fee.getPaybank());
        order.setSource("11");      // 11：客户费用申请单（账扣）
        order.setOaid(fee.getOaid());
        String remark = CommonUtils.nullToEmpty(fee.getRemark());
        order.setRemark("OA号：" + fee.getOaid() + " 客户费用账扣转收款 " + remark.substring(0, remark.length() >= 150 ? 150 : remark.length()));
        order.setStatus("2");

        int ret = collectionOrderForOaService.addCollectionOrder(order) ? 1 : 0;
        collectionOrderForOaService.auditCollectionOrder(order.getId(), false);
        return ret;
    }

    @Override
    public int rollbackCollectionOrderByOaCustomerFee(OaCustomerFee fee) throws Exception {

        if(checkCollectionOrderByOaCustomerFee(fee)) {

            return 0;
        }

        List<CollectionOrder> list = collectionOrderForOaService.selectCollectionOrderByOaid(fee.getOaid());
        CollectionOrder oldOrder = list.get(0);
        CollectionOrder newOrder = new CollectionOrder();
        newOrder.setBusinessdate(CommonUtils.getTodayDataStr());
        newOrder.setCustomerid(oldOrder.getCustomerid());
        newOrder.setAmount(oldOrder.getAmount().negate());
        newOrder.setCollectiontype("3");       // 3:费用
        newOrder.setBank(oldOrder.getBank());
        newOrder.setSource("11");      // 11：客户费用申请单（账扣）
        newOrder.setOaid(oldOrder.getOaid());
        newOrder.setRemark("OA号：" + fee.getOaid() + "驳回 客户费用账扣转收款");
        newOrder.setStatus("2");

        int ret = collectionOrderForOaService.addCollectionOrder(newOrder) ? 1 : 0;
        collectionOrderForOaService.auditCollectionOrder(newOrder.getId(), true);
        return ret;
    }

    @Override
    public int addMatcostsInputByOaCustomerFee(OaCustomerFee fee, List<OaCustomerFeeDetail> list) throws Exception {

        if(!checkMatcostsInputByOaCustomerFee(fee)) {

            return 0;
        }

        int count = 0;
        for(OaCustomerFeeDetail detail : list) {

            if(BigDecimal.ZERO.compareTo(detail.getFactoryamount()) == 0) {
                continue;
            }

            SysUser user = getSysUser();
            MatcostsInput matcosts = new MatcostsInput();
            matcosts.setBusinessdate(CommonUtils.getTodayDataStr());
            matcosts.setSubjectid(fee.getExpensesort());
            matcosts.setSupplierid(detail.getSupplierid());
            matcosts.setSupplierdeptid(detail.getDeptid());
            matcosts.setBrandid(detail.getBrandid());
            matcosts.setCustomerid(fee.getCustomerid());
            matcosts.setFactoryamount(detail.getFactoryamount());
            // 4595 通用版：百润新加的两个工作流生成代垫时不要把费用带过去
            //matcosts.setExpense(detail.getFactoryamount().add(detail.getSelfamount()));
            matcosts.setRemark(detail.getReason());
            matcosts.setOaid(fee.getOaid());
            matcosts.setSourcefrome("2");       // 2:流程
            matcosts.setAdduserid(user.getUserid());
            matcosts.setAddusername(user.getName());
            // 5160 通用版：OA生成代垫的时候经办人取当前工作处理人，相关OA：通路单、品牌费用申请单（支付）、客户费用申请单（账扣）
            matcosts.setTransactorid(user.getPersonnelid());

            boolean ret = journalSheetService.addMatcostsInput(matcosts, false);
            if(ret) {
                count ++;
            }
        }

        return count;
    }

    @Override
    public int rollbackMatcostsInputByOaCustomerFee(OaCustomerFee fee) throws Exception {

        if(checkMatcostsInputByOaCustomerFee(fee)) {
            return 0;
        }

        // 示例：+ + +  - - - + + +
        List<MatcostsInput> list = journalSheetService.selectMatcostsInputByOaid(fee.getOaid());
        List<MatcostsInput> list2 = new ArrayList<MatcostsInput>();
        for(int i = list.size() - 1; i >= 0; i--) {

            MatcostsInput matcosts = list.get(i);

            if("2".equals(matcosts.getSourcefrome())) {
                list2.add(matcosts);
            } else if("3".equals(matcosts.getSourcefrome())) {
                list2.remove(0);
            }
        }

        SysUser user = getSysUser();

        int count = 0;
        for(MatcostsInput oldMatcosts : list2) {

            MatcostsInput newMatcosts = new MatcostsInput();
            newMatcosts.setBusinessdate(CommonUtils.getTodayDataStr());
            newMatcosts.setSubjectid(oldMatcosts.getSubjectid());
            newMatcosts.setSupplierid(oldMatcosts.getSupplierid());
            newMatcosts.setSupplierdeptid(oldMatcosts.getSupplierdeptid());
            newMatcosts.setBrandid(oldMatcosts.getBrandid());
            newMatcosts.setCustomerid(oldMatcosts.getCustomerid());
            newMatcosts.setFactoryamount(oldMatcosts.getFactoryamount().negate());
            newMatcosts.setExpense(oldMatcosts.getExpense().negate());
            newMatcosts.setRemark("OA号：" + fee.getOaid() + "驳回 " + oldMatcosts.getRemark());
            newMatcosts.setOaid(oldMatcosts.getOaid());
            newMatcosts.setSourcefrome("3");        // 3:流程（驳回）
            // 5160 通用版：OA生成代垫的时候经办人取当前工作处理人，相关OA：通路单、品牌费用申请单（支付）、客户费用申请单（账扣）
            newMatcosts.setTransactorid(user.getPersonnelid());

            boolean ret = journalSheetService.addMatcostsInput(newMatcosts, false);
            if(ret) {
                count ++;
            }
        }

        return count;
    }

    @Override
    public int addCustomerFeeByOaCustomerFee(OaCustomerFee fee, List<OaCustomerFeeDetail> list) throws Exception {

        if(!checkCustomerFeeByOaCustomerFee(fee)) {

            return 0;
        }

        int count = 0;
        for(OaCustomerFeeDetail detail : list) {

            SysUser user = getSysUser();

            CustomerCostPayable lendPayable = new CustomerCostPayable();
            lendPayable.setBusinessdate(CommonUtils.getTodayDataStr());
            lendPayable.setExpensesort(fee.getExpensesort());
            lendPayable.setCustomerid(fee.getCustomerid());
            lendPayable.setAmount(detail.getFactoryamount().add(detail.getSelfamount()));
            lendPayable.setSupplierid(detail.getSupplierid());
            lendPayable.setDeptid(detail.getDeptid());
            lendPayable.setRemark(detail.getReason());
            lendPayable.setOaid(fee.getOaid());
            lendPayable.setSourcefrom("15");    // 15：客户费用申请单（账扣）
            lendPayable.setBilltype("2");       // 2:贷
            lendPayable.setPaytype("1");        // 1:支付
            lendPayable.setApplyuserid(user.getUserid());
            lendPayable.setApplyusername(user.getName());

            boolean ret1 = journalSheetService.addCustomerCostPayable(lendPayable);
            if(ret1) {
                count ++;
            }

            CustomerCostPayable payPayable = new CustomerCostPayable();
            payPayable.setBusinessdate(CommonUtils.getTodayDataStr());
            payPayable.setExpensesort(fee.getExpensesort());
            payPayable.setCustomerid(fee.getCustomerid());
            payPayable.setAmount(detail.getFactoryamount().add(detail.getSelfamount()));
            payPayable.setSupplierid(detail.getSupplierid());
            payPayable.setDeptid(detail.getDeptid());
            payPayable.setRemark(detail.getRemark());
            payPayable.setOaid(fee.getOaid());
            payPayable.setSourcefrom("15");    // 15：客户费用申请单（账扣）
            payPayable.setBilltype("1");       // 1:借
            payPayable.setPaytype("1");        // 1:支付
            payPayable.setApplyuserid(user.getUserid());
            payPayable.setApplyusername(user.getName());

            boolean ret2 = journalSheetService.addCustomerCostPayable(payPayable);
            if(ret2) {
                count ++;
            }

        }
        return count;
    }

    @Override
    public int rollbackCustomerFeeByOaCustomerFee(OaCustomerFee fee) throws Exception {

        if(checkCustomerFeeByOaCustomerFee(fee)) {
            return 0;
        }

        // 示例：+ + +  - - - + + +
        List<CustomerCostPayable> list = journalSheetService.selectCustomerCostPayablByOaid(fee.getOaid());
        List<CustomerCostPayable> list2 = new ArrayList<CustomerCostPayable>();
        for(int i = list.size() - 1; i >= 0; i--) {

            CustomerCostPayable payable = list.get(i);

            if("15".equals(payable.getSourcefrom())) {
                list2.add(payable);
            } else if("16".equals(payable.getSourcefrom())) {
                list2.remove(0);
            }
        }

        int count = 0;
        SysUser user = getSysUser();
        for(CustomerCostPayable oldPayable : list2) {

            CustomerCostPayable newPayable = new CustomerCostPayable();
            newPayable.setBusinessdate(CommonUtils.getTodayDataStr());
            newPayable.setExpensesort(oldPayable.getExpensesort());
            newPayable.setCustomerid(oldPayable.getCustomerid());
            newPayable.setAmount(oldPayable.getAmount().negate());
            newPayable.setSupplierid(oldPayable.getSupplierid());
            newPayable.setRemark(oldPayable.getRemark());
            newPayable.setOaid(oldPayable.getOaid());
            newPayable.setSourcefrom("16");    // 16：客户费用申请单（账扣）(驳回)
            newPayable.setBilltype(oldPayable.getBilltype());
            newPayable.setPaytype("1");        // 1:支付
            newPayable.setApplyuserid(user.getUserid());
            newPayable.setApplyusername(user.getName());

            boolean ret = journalSheetService.addCustomerCostPayable(newPayable);
            if(ret) {
                count ++;
            }
        }

        return count;
    }

    @Override
    public boolean checkMatcostsInputByOaBrandFee(OaBrandFee fee) throws Exception {

        List<MatcostsInput> list = journalSheetService.selectMatcostsInputByOaid(fee.getOaid());

        if(list.size() % 2 == 1) {
            return false;
        }

        if(list.size() == 0) {
            return true;
        }

        if("3".equals(list.get(0).getSourcefrome())) {

            return true;
        }

        return false;
    }

    @Override
    public boolean checkCustomerFeeByOaBrandFee(OaBrandFee fee) throws Exception {

        List<CustomerCostPayable> list = journalSheetService.selectCustomerCostPayablByOaid(fee.getOaid());

        if(list.size() % 2 == 1) {
            return false;
        }

        if(list.size() == 0) {
            return true;
        }

        if("18".equals(list.get(0).getSourcefrom())) {

            return true;
        }

        return false;
    }

    @Override
    public int addMatcostsInputByOaBrandFee(OaBrandFee fee, List<OaBrandFeeDetail> list) throws Exception {

        if(!checkMatcostsInputByOaBrandFee(fee)) {

            return 0;
        }

        int count = 0;
        for(OaBrandFeeDetail detail : list) {

            if(BigDecimal.ZERO.compareTo(detail.getFactoryamount()) == 0) {
                continue;
            }

            SysUser user = getSysUser();
            MatcostsInput matcosts = new MatcostsInput();
            matcosts.setBusinessdate(CommonUtils.getTodayDataStr());
            matcosts.setSubjectid(fee.getExpensesort());
            matcosts.setSupplierid(fee.getSupplierid());
            matcosts.setSupplierdeptid(fee.getDeptid());
            matcosts.setBrandid(fee.getBrandid());
            matcosts.setCustomerid(detail.getCustomerid());
            matcosts.setFactoryamount(detail.getFactoryamount());
            // 4595 通用版：百润新加的两个工作流生成代垫时不要把费用带过去
//            matcosts.setExpense(detail.getFactoryamount());
            matcosts.setRemark(detail.getReason() + " " + detail.getRemark());
            matcosts.setOaid(fee.getOaid());
            matcosts.setSourcefrome("2");       // 2:流程
            matcosts.setAdduserid(user.getUserid());
            matcosts.setAddusername(user.getName());
            // 5160 通用版：OA生成代垫的时候经办人取当前工作处理人，相关OA：通路单、品牌费用申请单（支付）、客户费用申请单（账扣）
            matcosts.setTransactorid(user.getPersonnelid());

            boolean ret = journalSheetService.addMatcostsInput(matcosts, false);
            if(ret) {
                count ++;
            }
        }

        return count;
    }

    @Override
    public int rollbackMatcostsInputByOaBrandFee(OaBrandFee fee) throws Exception {

        if(checkMatcostsInputByOaBrandFee(fee)) {
            return 0;
        }

        // 示例：+ + +  - - - + + +
        List<MatcostsInput> list = journalSheetService.selectMatcostsInputByOaid(fee.getOaid());
        List<MatcostsInput> list2 = new ArrayList<MatcostsInput>();
        for(int i = list.size() - 1; i >= 0; i--) {

            MatcostsInput matcosts = list.get(i);

            if("2".equals(matcosts.getSourcefrome())) {
                list2.add(matcosts);
            } else if("3".equals(matcosts.getSourcefrome())) {
                list2.remove(0);
            }
        }

        SysUser user = getSysUser();

        int count = 0;
        for(MatcostsInput oldMatcosts : list2) {

            MatcostsInput newMatcosts = new MatcostsInput();
            newMatcosts.setBusinessdate(CommonUtils.getTodayDataStr());
            newMatcosts.setSubjectid(oldMatcosts.getSubjectid());
            newMatcosts.setSupplierid(oldMatcosts.getSupplierid());
            newMatcosts.setSupplierdeptid(oldMatcosts.getSupplierdeptid());
            newMatcosts.setBrandid(oldMatcosts.getBrandid());
            newMatcosts.setCustomerid(oldMatcosts.getCustomerid());
            newMatcosts.setFactoryamount(oldMatcosts.getFactoryamount().negate());
            newMatcosts.setExpense(oldMatcosts.getExpense().negate());
            newMatcosts.setRemark("OA号：" + fee.getOaid() + "驳回 " + oldMatcosts.getRemark());
            newMatcosts.setOaid(oldMatcosts.getOaid());
            newMatcosts.setSourcefrome("3");        // 3:流程（驳回）
            // 5160 通用版：OA生成代垫的时候经办人取当前工作处理人，相关OA：通路单、品牌费用申请单（支付）、客户费用申请单（账扣）
            newMatcosts.setTransactorid(user.getPersonnelid());

            boolean ret = journalSheetService.addMatcostsInput(newMatcosts, false);
            if(ret) {
                count ++;
            }
        }

        return count;
    }

    @Override
    public int addCustomerFeeByOaBrandFee(OaBrandFee fee, List<OaBrandFeeDetail> list) throws Exception {

        if(!checkCustomerFeeByOaBrandFee(fee)) {

            return 0;
        }

        int count = 0;
        for(OaBrandFeeDetail detail : list) {

            SysUser user = getSysUser();

            CustomerCostPayable lendPayable = new CustomerCostPayable();
            lendPayable.setBusinessdate(CommonUtils.getTodayDataStr());
            lendPayable.setExpensesort(fee.getExpensesort());
            lendPayable.setCustomerid(detail.getCustomerid());
            lendPayable.setAmount(detail.getFactoryamount());
            lendPayable.setSupplierid(fee.getSupplierid());
            lendPayable.setDeptid(fee.getDeptid());
            lendPayable.setRemark(detail.getReason() + " " + detail.getRemark());
            lendPayable.setOaid(fee.getOaid());
            lendPayable.setSourcefrom("17");    // 17：客户费用申请单（账扣）
            lendPayable.setBilltype("2");       // 2:贷
            lendPayable.setPaytype("1");        // 1:支付
            lendPayable.setApplyuserid(user.getUserid());
            lendPayable.setApplyusername(user.getName());

            boolean ret1 = journalSheetService.addCustomerCostPayable(lendPayable);
            if(ret1) {
                count ++;
            }

            CustomerCostPayable payPayable = new CustomerCostPayable();
            payPayable.setBusinessdate(CommonUtils.getTodayDataStr());
            payPayable.setExpensesort(fee.getExpensesort());
            payPayable.setCustomerid(detail.getCustomerid());
            payPayable.setAmount(detail.getFactoryamount());
            payPayable.setSupplierid(fee.getSupplierid());
            payPayable.setDeptid(fee.getDeptid());
            payPayable.setRemark(detail.getReason() + " " + detail.getRemark());
            payPayable.setOaid(fee.getOaid());
            payPayable.setSourcefrom("17");    // 17：品牌费用申请单（支付）
            payPayable.setBilltype("1");       // 1:借
            payPayable.setPaytype("1");        // 1:支付
            payPayable.setApplyuserid(user.getUserid());
            payPayable.setApplyusername(user.getName());

            boolean ret2 = journalSheetService.addCustomerCostPayable(payPayable);
            if(ret2) {
                count ++;
            }

        }
        return count;
    }

    @Override
    public int rollbackCustomerFeeByOaBrandFee(OaBrandFee fee) throws Exception {

        if(checkCustomerFeeByOaBrandFee(fee)) {
            return 0;
        }

        // 示例：+ + +  - - - + + +
        List<CustomerCostPayable> list = journalSheetService.selectCustomerCostPayablByOaid(fee.getOaid());
        List<CustomerCostPayable> list2 = new ArrayList<CustomerCostPayable>();
        for(int i = list.size() - 1; i >= 0; i--) {

            CustomerCostPayable payable = list.get(i);

            if("17".equals(payable.getSourcefrom())) {
                list2.add(payable);
            } else if("18".equals(payable.getSourcefrom())) {
                list2.remove(0);
            }
        }

        int count = 0;
        SysUser user = getSysUser();
        for(CustomerCostPayable oldPayable : list2) {

            CustomerCostPayable newPayable = new CustomerCostPayable();
            newPayable.setBusinessdate(CommonUtils.getTodayDataStr());
            newPayable.setExpensesort(oldPayable.getExpensesort());
            newPayable.setCustomerid(oldPayable.getCustomerid());
            newPayable.setAmount(oldPayable.getAmount().negate());
            newPayable.setSupplierid(oldPayable.getSupplierid());
            newPayable.setRemark(oldPayable.getRemark());
            newPayable.setOaid(oldPayable.getOaid());
            newPayable.setSourcefrom("18");    // 18：品牌费用申请单（支付）(驳回)
            newPayable.setBilltype(oldPayable.getBilltype());
            newPayable.setPaytype("1");        // 1:支付
            newPayable.setApplyuserid(user.getUserid());
            newPayable.setApplyusername(user.getName());

            boolean ret = journalSheetService.addCustomerCostPayable(newPayable);
            if(ret) {
                count ++;
            }
        }

        return count;
    }

    @Override
    public int addBankBillByOaPurchasePay(OaPurchasePay pay) throws Exception {

        if(pay == null) {
            return 0;
        }

        if(!checkBankBillByOaPurchasePay(pay)) {
            return 0;
        }

        BankAmountOthers bankAmountOthers = new BankAmountOthers();
        //借贷类型 1借(收入)2贷(支出)
        bankAmountOthers.setLendtype("2");
        bankAmountOthers.setBankid(pay.getPaybank());

        String bankid = pay.getPaybank();
        if(StringUtils.isNotEmpty(bankid)) {

            Bank bank = getBankInfoByID(bankid);
            if (bank != null) {

                bankAmountOthers.setDeptid(bank.getBankdeptid());
            }
        }

        bankAmountOthers.setBilltype("31");                             // 31: 行政采购付款单
        bankAmountOthers.setBillid(pay.getId());
        bankAmountOthers.setOaid(pay.getOaid());
        bankAmountOthers.setAmount(pay.getPayamount());
        bankAmountOthers.setUpamount(pay.getUpperpayamount());
        bankAmountOthers.setOppname(pay.getReceivername());
        bankAmountOthers.setOppbank(pay.getReceiverbank());
        bankAmountOthers.setOppbankno(pay.getReceiverbankno());
        bankAmountOthers.setInvoiceamount(pay.getInvoiceamount());
        bankAmountOthers.setInvoiceid("");
        bankAmountOthers.setRemark(pay.getRemark());

        bankAmountOthers.setAdduserid(pay.getAdduserid());
        bankAmountOthers.setAddusername(pay.getAddusername());
        bankAmountOthers.setAdddeptid(pay.getAdddeptid());
        bankAmountOthers.setAdddeptname(pay.getAdddeptname());
        boolean flag = accountForOAService.addBankAmountOthers(bankAmountOthers);
        return flag ? 1 : 0;
    }

    @Override
    public int rollbackBankBillByOaPurchasePay(OaPurchasePay pay) throws Exception {

        if(checkBankBillByOaPurchasePay(pay)) {
            return 0;
        }

        Map param = new HashMap();
        param.put("oaid", pay.getOaid());
        param.put("billtype", 31);
        List<BankAmountOthers> list = accountForOAService.selectBankAmountOthersList(param);
        Collections.sort(list, new Comparator<BankAmountOthers>() {
            @Override
            public int compare(BankAmountOthers b1, BankAmountOthers b2) {
                return b2.getAddtime().compareTo(b1.getAddtime());
            }
        });

        BankAmountOthers oldBankAmountOthers = list.get(0);

        // 支出单已生成，未生成收入单
        BankAmountOthers bankAmountOthers = new BankAmountOthers();
        //借贷类型 1借(收入)2贷(支出)
        bankAmountOthers.setLendtype("1");
        bankAmountOthers.setBankid(oldBankAmountOthers.getBankid());
        bankAmountOthers.setDeptid(oldBankAmountOthers.getDeptid());
        bankAmountOthers.setBilltype("31");                             // 31: 行政采购付款单
        bankAmountOthers.setBillid(oldBankAmountOthers.getBillid());
        bankAmountOthers.setOaid(oldBankAmountOthers.getOaid());
        bankAmountOthers.setAmount(oldBankAmountOthers.getAmount());
        bankAmountOthers.setUpamount(oldBankAmountOthers.getUpamount());
        bankAmountOthers.setOppid(oldBankAmountOthers.getOppid());
        bankAmountOthers.setOppname(oldBankAmountOthers.getOppname());
        bankAmountOthers.setOppbank(oldBankAmountOthers.getOppbank());
        bankAmountOthers.setOppbankno(oldBankAmountOthers.getOppbankno());
        bankAmountOthers.setInvoiceamount(oldBankAmountOthers.getInvoiceamount());
        bankAmountOthers.setInvoicedate(oldBankAmountOthers.getInvoicedate());
        bankAmountOthers.setInvoiceid("");
        bankAmountOthers.setInvoicetype(oldBankAmountOthers.getInvoicetype());
        bankAmountOthers.setRemark("OA回退，编号：" + pay.getOaid());

        bankAmountOthers.setAdduserid(pay.getAdduserid());
        bankAmountOthers.setAddusername(pay.getAddusername());
        bankAmountOthers.setAdddeptid(pay.getAdddeptid());
        bankAmountOthers.setAdddeptname(pay.getAdddeptname());
        boolean flag = accountForOAService.addBankAmountOthers(bankAmountOthers);
        return flag ? 1 : 0;
    }

    @Override
    public boolean checkBankBillByOaPurchasePay(OaPurchasePay pay) throws Exception {

        Map param = new HashMap();
        param.put("oaid", pay.getOaid());
        param.put("billtype", 31);
        List<BankAmountOthers> list = accountForOAService.selectBankAmountOthersList(param);

        // 接单记录有2n条，说明有借和贷相互抵消
        if(list.size() % 2 == 0) {

            return true;
        }

        return false;
    }

    @Override
    public int addBankBillByOaBrandFee(OaBrandFee oaBrandFee, List<OaBrandFeeDetail> details) throws Exception {

        if(oaBrandFee == null) {
            return 0;
        }

        if(!checkBankBillByOaBrandFee(oaBrandFee)) {
            return 0;
        }

        SysUser sysUser = getSysUser();
        int count = 0;
        for(OaBrandFeeDetail detail : details) {

            BankAmountOthers bankAmountOthers = new BankAmountOthers();
            //借贷类型 1借(收入)2贷(支出)
            bankAmountOthers.setLendtype("2");
            bankAmountOthers.setBankid(oaBrandFee.getPaybank());
            bankAmountOthers.setDeptid(oaBrandFee.getDeptid());
            bankAmountOthers.setBilltype("32");
            bankAmountOthers.setBillid(oaBrandFee.getId());
            bankAmountOthers.setOaid(oaBrandFee.getOaid());
            bankAmountOthers.setAmount(detail.getFactoryamount());
            bankAmountOthers.setUpamount(CommonUtils.AmountUnitCnChange(detail.getFactoryamount().doubleValue()));
            bankAmountOthers.setOppid(detail.getCustomerid());

            Customer customer = getCustomerByID(detail.getCustomerid());
            if(customer != null) {

                bankAmountOthers.setOppname(customer.getName());
            }

            bankAmountOthers.setInvoiceamount(detail.getFactoryamount());
            bankAmountOthers.setInvoicedate("");
            bankAmountOthers.setInvoiceid("");
            bankAmountOthers.setInvoicetype("");
            bankAmountOthers.setRemark(detail.getRemark());

            bankAmountOthers.setAdduserid(sysUser.getUserid());
            bankAmountOthers.setAddusername(sysUser.getName());
            bankAmountOthers.setAdddeptid(sysUser.getDepartmentid());
            bankAmountOthers.setAdddeptname(sysUser.getDepartmentname());
            boolean flag = accountForOAService.addBankAmountOthers(bankAmountOthers);
            if(flag) {
                count ++;
            }
        }

        return count;
    }

    @Override
    public int rollbackBankBillByOaBrandFee(OaBrandFee oaBrandFee) throws Exception {

        if(checkBankBillByOaBrandFee(oaBrandFee)) {
            return 0;
        }

        Map param = new HashMap();
        param.put("oaid", oaBrandFee.getOaid());
        param.put("billtype", 32);
        List<BankAmountOthers> list = accountForOAService.selectBankAmountOthersList(param);

        SysUser sysUser = getSysUser();
        int count = 0;
        for(BankAmountOthers old : list) {

            if("1".equals(old.getLendtype())) {
                break;
            }

            BankAmountOthers bankAmountOthers = new BankAmountOthers();
            //借贷类型 1借(收入)2贷(支出)
            bankAmountOthers.setLendtype("1");
            bankAmountOthers.setBankid(old.getBankid());
            bankAmountOthers.setDeptid(old.getDeptid());
            bankAmountOthers.setBilltype("32");
            bankAmountOthers.setBillid(old.getId());
            bankAmountOthers.setOaid(oaBrandFee.getOaid());
            bankAmountOthers.setAmount(old.getAmount().negate());
            bankAmountOthers.setUpamount(CommonUtils.AmountUnitCnChange(old.getAmount().negate().doubleValue()));
            bankAmountOthers.setOppid(old.getOppid());
            bankAmountOthers.setOppname(old.getOppname());
            bankAmountOthers.setInvoiceamount(old.getInvoiceamount());
            bankAmountOthers.setInvoicedate(old.getInvoicedate());
            bankAmountOthers.setInvoiceid(old.getInvoiceid());
            bankAmountOthers.setInvoicetype(old.getInvoicetype());
            bankAmountOthers.setRemark(old.getRemark());

            bankAmountOthers.setAdduserid(sysUser.getUserid());
            bankAmountOthers.setAddusername(sysUser.getName());
            bankAmountOthers.setAdddeptid(sysUser.getDepartmentid());
            bankAmountOthers.setAdddeptname(sysUser.getDepartmentname());
            boolean flag = accountForOAService.addBankAmountOthers(bankAmountOthers);
            if(flag) {
                count ++;
            }
        }

        return count;
    }

    @Override
    public boolean checkBankBillByOaBrandFee(OaBrandFee oaBrandFee) throws Exception {

        Map param = new HashMap();
        param.put("oaid", oaBrandFee.getOaid());
        param.put("billtype", 32);
        List<BankAmountOthers> list = accountForOAService.selectBankAmountOthersList(param);

        if(list == null || list.size() == 0) {
            return true;
        }

        if("2".equals(list.get(0).getLendtype())) {
            return false;
        }

        return true;
    }

    @Override
    public int addMatcostByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception {

        if(!checkMatcostByOaMatcost(matcost, detailList)) {

            return 0;
        }

        int count = 0;
        for(OaMatcostDetail detail : detailList) {

            if(BigDecimal.ZERO.compareTo(detail.getFactoryamount()) == 0) {
                continue;
            }

            SysUser user = getSysUser();
            MatcostsInput matcosts = new MatcostsInput();
            matcosts.setBusinessdate(CommonUtils.getTodayDataStr());
            matcosts.setSubjectid(detail.getExpensesort());
            matcosts.setSupplierid(matcost.getSupplierid());
            matcosts.setSupplierdeptid(matcost.getDeptid());
            matcosts.setBrandid(detail.getBrandid());
            matcosts.setCustomerid(detail.getCustomerid());
            matcosts.setFactoryamount(detail.getFactoryamount());
            matcosts.setRemark(CommonUtils.nullToEmpty(detail.getRemark()));
            matcosts.setOaid(matcost.getOaid());
            matcosts.setSourcefrome("2");       // 2:流程
            matcosts.setAdduserid(user.getUserid());
            matcosts.setAddusername(user.getName());
            matcosts.setTransactorid(user.getPersonnelid());

            boolean ret = journalSheetService.addMatcostsInput(matcosts, false);
            if(ret) {
                count ++;
            }
        }

        return count;
    }

    @Override
    public int rollbackMatcostByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception {

        if(checkMatcostByOaMatcost(matcost, detailList)) {
            return 0;
        }

        // 示例：+ + +  - - - + + +
        List<MatcostsInput> list = journalSheetService.selectMatcostsInputByOaid(matcost.getOaid());
        List<MatcostsInput> list2 = new ArrayList<MatcostsInput>();
        for(int i = list.size() - 1; i >= 0; i--) {

            MatcostsInput matcosts = list.get(i);

            if("2".equals(matcosts.getSourcefrome())) {
                list2.add(matcosts);
            } else if("3".equals(matcosts.getSourcefrome())) {
                list2.remove(0);
            }
        }

        SysUser user = getSysUser();

        int count = 0;
        for(MatcostsInput oldMatcosts : list2) {

            MatcostsInput newMatcosts = new MatcostsInput();
            newMatcosts.setBusinessdate(CommonUtils.getTodayDataStr());
            newMatcosts.setSubjectid(oldMatcosts.getSubjectid());
            newMatcosts.setSupplierid(oldMatcosts.getSupplierid());
            newMatcosts.setSupplierdeptid(oldMatcosts.getSupplierdeptid());
            newMatcosts.setBrandid(oldMatcosts.getBrandid());
            newMatcosts.setCustomerid(oldMatcosts.getCustomerid());
            newMatcosts.setFactoryamount(oldMatcosts.getFactoryamount().negate());
            newMatcosts.setExpense(oldMatcosts.getExpense().negate());
            newMatcosts.setRemark("OA号：" + matcost.getOaid() + "驳回 " + oldMatcosts.getRemark());
            newMatcosts.setOaid(oldMatcosts.getOaid());
            newMatcosts.setSourcefrome("3");        // 3:流程（驳回）
            // 5160 通用版：OA生成代垫的时候经办人取当前工作处理人，相关OA：通路单、品牌费用申请单（支付）、客户费用申请单（账扣）
            newMatcosts.setTransactorid(user.getPersonnelid());

            boolean ret = journalSheetService.addMatcostsInput(newMatcosts, false);
            if(ret) {
                count ++;
            }
        }

        return count;
    }

    @Override
    public boolean checkMatcostByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception {

        List<MatcostsInput> list = journalSheetService.selectMatcostsInputByOaid(matcost.getOaid());

        if(list.size() % 2 == 1) {
            return false;
        }

        if(list.size() == 0) {
            return true;
        }

        if("3".equals(list.get(0).getSourcefrome())) {

            return true;
        }

        return false;
    }

    @Override
    public int addCustomerFeeByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList, String billtype) throws Exception {

        if(!checkCustomerFeeByOaMatcost(matcost, detailList, billtype)) {

            return 0;
        }

        int count = 0;
        for(OaMatcostDetail detail : detailList) {

            SysUser user = getSysUser();

            CustomerCostPayable payable = new CustomerCostPayable();
            payable.setBusinessdate(CommonUtils.getTodayDataStr());
            payable.setExpensesort(detail.getExpensesort());
            payable.setCustomerid(detail.getCustomerid());
            payable.setAmount(detail.getFeeamount());
            payable.setSupplierid(matcost.getSupplierid());
            payable.setDeptid(matcost.getDeptid());
            payable.setRemark(detail.getReason() + " " + detail.getRemark());
            payable.setOaid(matcost.getOaid());
            payable.setSourcefrom("20");    // 20：代垫费用申请单
            payable.setBilltype(billtype);
            payable.setPaytype("1");        // 1:支付
            payable.setApplyuserid(user.getUserid());
            payable.setApplyusername(user.getName());
            payable.setBankid(matcost.getPaybank());

            boolean ret1 = journalSheetService.addCustomerCostPayable(payable);
            if(ret1) {
                count ++;
            }

        }
        return count;
    }

    @Override
    public int rollbackCustomerFeeByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList, String billtype) throws Exception {

        if(checkCustomerFeeByOaMatcost(matcost, detailList, billtype)) {
            return 0;
        }

        // 示例：+ + +  - - - + + +
        List<CustomerCostPayable> list = journalSheetService.selectCustomerCostPayablByOaid(matcost.getOaid());
        List<CustomerCostPayable> list2 = new ArrayList<CustomerCostPayable>();
        for(int i = list.size() - 1; i >= 0; i--) {

            CustomerCostPayable payable = list.get(i);
            if(!billtype.equals(payable.getBilltype())) {
                continue;
            }

            if("20".equals(payable.getSourcefrom())) {
                list2.add(payable);
            } else if("21".equals(payable.getSourcefrom())) {
                list2.remove(0);
            }
        }

        int count = 0;
        SysUser user = getSysUser();
        for(CustomerCostPayable oldPayable : list2) {

            CustomerCostPayable newPayable = new CustomerCostPayable();
            newPayable.setBusinessdate(CommonUtils.getTodayDataStr());
            newPayable.setExpensesort(oldPayable.getExpensesort());
            newPayable.setCustomerid(oldPayable.getCustomerid());
            newPayable.setAmount(oldPayable.getAmount().negate());
            newPayable.setSupplierid(oldPayable.getSupplierid());
            newPayable.setRemark(oldPayable.getRemark());
            newPayable.setOaid(oldPayable.getOaid());
            newPayable.setSourcefrom("21");    // 20：品牌费用申请单（支付）(驳回)
            newPayable.setBilltype(oldPayable.getBilltype());
            newPayable.setPaytype("1");        // 1:支付
            newPayable.setApplyuserid(user.getUserid());
            newPayable.setApplyusername(user.getName());
            newPayable.setBankid(oldPayable.getBankid());

            boolean ret = journalSheetService.addCustomerCostPayable(newPayable);
            if(ret) {
                count ++;
            }
        }

        return count;
    }

    @Override
    public boolean checkCustomerFeeByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList, String billtype) throws Exception {

        List<CustomerCostPayable> list = journalSheetService.selectCustomerCostPayablByOaid(matcost.getOaid());
        List<CustomerCostPayable> list2 = new ArrayList<CustomerCostPayable>();
        for(CustomerCostPayable customerCostPayable : list) {
            if(billtype.equals(customerCostPayable.getBilltype())) {
                list2.add(customerCostPayable);
            }
        }

        if(list2.size() % 2 == 1) {
            return false;
        }

        if(list2.size() == 0) {
            return true;
        }

        if("21".equals(list2.get(0).getSourcefrom())) {

            return true;
        }

        return false;
    }

    @Override
    public int addCustomerPushBalanceByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception {

        if(!checkCustomerPushBalanceByOaMatcost(matcost, detailList)) {
            return 0;
        }

        int ret = 0;
        for(OaMatcostDetail detail : detailList) {

            CustomerPushBalance customerPushBalance = new CustomerPushBalance();
            customerPushBalance.setCustomerid(detail.getCustomerid());
            customerPushBalance.setPushtype("1");   // 品牌冲差
            customerPushBalance.setBrandid(detail.getBrandid());
            customerPushBalance.setAmount(detail.getFeeamount().negate());
            customerPushBalance.setRemark("OA编号：" + matcost.getOaid() + "； " + detail.getRemark());
            customerPushBalance.setBusinessdate(CommonUtils.getTodayDataStr());
            customerPushBalance.setIsinvoice("0");
            customerPushBalance.setOaid(matcost.getOaid());
            customerPushBalance.setSubject(detail.getExpensesort());
            customerPushBalance.setOaback("1");     // 1:正常流转

            // 4173 通用版：OA生成冲差单时，默认税种取该品牌档案中的默认税种，并计算出未税金额和税额
            if(StringUtils.isNotEmpty(detail.getBrandid())) {

                Brand brand = getGoodsBrandByID(detail.getBrandid());
                if(brand != null) {

                    String defaultTaxType = brand.getDefaulttaxtype();
                    TaxType taxType = getTaxType(defaultTaxType);

                    if(taxType != null) {

                        customerPushBalance.setDefaulttaxtype(taxType.getId());
                        customerPushBalance.setDefaulttaxtypename(taxType.getName());

                        // 无税
                        BigDecimal noTaxamount = getNotaxAmountByTaxAmount(detail.getFactoryamount(), taxType.getId()).negate();
                        BigDecimal taxAmount = detail.getFeeamount().negate().subtract(noTaxamount);

                        customerPushBalance.setNotaxamount(noTaxamount);
                        customerPushBalance.setTax(taxAmount);
                    }
                }
            }

            customerPushBalance.setSalesdept(matcost.getDeptid());
            DepartMent dept = getDepartMentById(matcost.getDeptid());
            if(dept != null) {

                customerPushBalance.setSalesdeptname(dept.getName());
            }

            customerPushBalance.setSendamount(customerPushBalance.getAmount());
            customerPushBalance.setSendnotaxamount(customerPushBalance.getNotaxamount());
            String cpid = customerPushBanlanceService.addAndAuditCustomerPushBanlanceByOA(customerPushBalance);

            if(StringUtils.isNotEmpty(cpid)) {

                ret ++;
            }
        }

        return ret;
    }

    @Override
    public int rollbackCustomerPushBalanceByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception {

        if(checkCustomerPushBalanceByOaMatcost(matcost, detailList)) {
            return 0;
        }

        List<CustomerPushBalance> list = customerPushBanlanceService.selectCustomerPushBanlanceByOaid(matcost.getOaid());

        int count = 0;
        for(CustomerPushBalance oldCustomerPushBalance : list) {

            if("2".equals(oldCustomerPushBalance.getOaback())) {

                break;
            }

            CustomerPushBalance newCustomerPushBalance = new CustomerPushBalance();
            newCustomerPushBalance.setCustomerid(oldCustomerPushBalance.getCustomerid());
            newCustomerPushBalance.setPushtype(oldCustomerPushBalance.getPushtype());
            newCustomerPushBalance.setBrandid(oldCustomerPushBalance.getBrandid());
            newCustomerPushBalance.setAmount(oldCustomerPushBalance.getAmount().negate());
            newCustomerPushBalance.setRemark("OA编号：" + oldCustomerPushBalance.getOaid() + " 驳回");
            newCustomerPushBalance.setBusinessdate(CommonUtils.getTodayDataStr());
            newCustomerPushBalance.setIsinvoice("0");
            newCustomerPushBalance.setOaid(oldCustomerPushBalance.getOaid());
            newCustomerPushBalance.setSubject(oldCustomerPushBalance.getSubject());
            newCustomerPushBalance.setOaback("2");     // 2:驳回

            newCustomerPushBalance.setDefaulttaxtype(oldCustomerPushBalance.getDefaulttaxtype());
            newCustomerPushBalance.setDefaulttaxtypename(oldCustomerPushBalance.getDefaulttaxtypename());
            newCustomerPushBalance.setNotaxamount(oldCustomerPushBalance.getNotaxamount());
            newCustomerPushBalance.setTax(oldCustomerPushBalance.getTax());
            newCustomerPushBalance.setSalesdept(oldCustomerPushBalance.getSalesdept());
            newCustomerPushBalance.setSalesdeptname(oldCustomerPushBalance.getSalesdeptname());

            String cpid = customerPushBanlanceService.addAndAuditCustomerPushBanlanceByOA(newCustomerPushBalance);

            if(StringUtils.isNotEmpty(cpid)) {

                count ++;
            }
        }

        return count;
    }

    @Override
    public boolean checkCustomerPushBalanceByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception {

        List<CustomerPushBalance> list = customerPushBanlanceService.selectCustomerPushBanlanceByOaid(matcost.getOaid());

        if(list.size() % 2 == 1) {
            return false;
        }

        if(list.size() == 0) {
            return true;
        }

        if("2".equals(list.get(0).getOaback())) {
            return true;
        }

        return false;
    }

    @Override
    public int addBankBillByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception {

        if(matcost == null) {
            return 0;
        }

        if(!checkBankBillByOaMatcost(matcost, detailList)) {
            return 0;
        }

        int count = 0;
        for(OaMatcostDetail detail : detailList) {

            BankAmountOthers bankAmountOthers = new BankAmountOthers();
            //借贷类型 1借(收入)2贷(支出)
            bankAmountOthers.setLendtype("2");
            bankAmountOthers.setBankid(matcost.getPaybank());
            bankAmountOthers.setDeptid(matcost.getDeptid());
            //单据类型（0期初1收款单2付款单,不在该单据内）3日常费用支付4客户费用支付5个人借款6预付款7转账
            bankAmountOthers.setBilltype("42");
            bankAmountOthers.setBillid(matcost.getId());
            bankAmountOthers.setOaid(matcost.getOaid());
            bankAmountOthers.setAmount(detail.getFeeamount());
            bankAmountOthers.setUpamount(CommonUtils.AmountUnitCnChange(detail.getFeeamount().doubleValue()));
            bankAmountOthers.setOppid(detail.getCustomerid());
            Customer customer = getCustomerByID(detail.getCustomerid());
            if (customer != null) {
                bankAmountOthers.setOppname(customer.getName());
            }
            bankAmountOthers.setRemark(detail.getRemark());
            bankAmountOthers.setOatype("1");

            boolean flag = accountForOAService.addBankAmountOthers(bankAmountOthers);
            if(flag) {
                count ++;
            }
        }

        return count;
    }

    @Override
    public int rollbackBankBillByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception {

        if(matcost == null) {
            return 0;
        }

        if(checkBankBillByOaMatcost(matcost, detailList)) {
            return 0;
        }

        List<BankAmountOthers> list = accountForOAService.selectBankAmountOthersByOaid(matcost.getOaid());
        int count = 0;
        for(BankAmountOthers oldBankBill : list) {

            if("2".equals(oldBankBill.getOatype())) {
                break;
            }

            BankAmountOthers bankAmountOthers = new BankAmountOthers();
            //借贷类型 1借(收入)2贷(支出)
            bankAmountOthers.setLendtype("2");
            bankAmountOthers.setBankid(oldBankBill.getBankid());
            bankAmountOthers.setDeptid(oldBankBill.getDeptid());
            //单据类型（0期初1收款单2付款单,不在该单据内）3日常费用支付4客户费用支付5个人借款6预付款7转账
            bankAmountOthers.setBilltype("42");
            bankAmountOthers.setBillid(oldBankBill.getBillid());
            bankAmountOthers.setOaid(oldBankBill.getOaid());
            bankAmountOthers.setAmount(oldBankBill.getAmount().negate());
            bankAmountOthers.setUpamount(CommonUtils.AmountUnitCnChange(oldBankBill.getAmount().negate().doubleValue()));
            bankAmountOthers.setOppid(oldBankBill.getOppid());
            bankAmountOthers.setOppname(oldBankBill.getOppname());
            bankAmountOthers.setRemark("OA驳回：" + matcost.getOaid() + "；" + CommonUtils.nullToEmpty(oldBankBill.getRemark()));
            bankAmountOthers.setOatype("2");

            boolean flag = accountForOAService.addBankAmountOthers(bankAmountOthers);
            if(flag) {
                count ++;
            }
        }

        return count;
    }

    @Override
    public boolean checkBankBillByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception {

        List<BankAmountOthers> list = accountForOAService.selectBankAmountOthersByOaid(matcost.getOaid());
        if(list.size() == 0) {
            return true;
        }

        BankAmountOthers bankAmountOthers = list.get(0);
        if("2".equals(bankAmountOthers.getOatype())) {
            return true;
        }

        return false;
    }

    @Override
    public int addCollectionOrderByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception {

        if (matcost == null) {
            return 0;
        }

        if(!checkCollectionOrderByOaMatcost(matcost, detailList)) {

            return 0;
        }

        int count = 0;
        for(OaMatcostDetail detail : detailList) {

            CollectionOrder order = new CollectionOrder();
            order.setBusinessdate(CommonUtils.getTodayDataStr());
            order.setCustomerid(detail.getCustomerid());
            order.setAmount(detail.getFeeamount());
            order.setCollectiontype("3");       // 3:费用
            order.setBank(matcost.getPaybank());
            order.setSource("13");      // 13:代垫费用申请单
            order.setOaid(matcost.getOaid());
            String remark = CommonUtils.nullToEmpty(detail.getRemark());
            order.setRemark("OA号：" + matcost.getOaid() + "，" + remark);
            order.setStatus("2");

            Customer customer = getCustomerByID(detail.getCustomerid());
            if (customer != null) {
                order.setBankdeptid(customer.getSalesdeptid());
            }

            int ret = collectionOrderForOaService.addCollectionOrder(order) ? 1 : 0;
            collectionOrderForOaService.auditCollectionOrder(order.getId(), true);

            count ++;
        }
        return count;
    }

    @Override
    public int rollbackCollectionOrderByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception {

        if (matcost == null) {
            return 0;
        }

        if(checkCollectionOrderByOaMatcost(matcost, detailList)) {

            return 0;
        }

        List<CollectionOrder> list = collectionOrderForOaService.selectCollectionOrderByOaid(matcost.getOaid());
        int count = 0;
        for(CollectionOrder oldCollectOrder : list) {

            if("14".equals(oldCollectOrder.getSource())) {
                break;
            }

            CollectionOrder order = new CollectionOrder();
            order.setBusinessdate(CommonUtils.getTodayDataStr());
            order.setCustomerid(oldCollectOrder.getCustomerid());
            order.setAmount(oldCollectOrder.getAmount().negate());
            order.setCollectiontype("3");       // 3:费用
            order.setBank(oldCollectOrder.getBank());
            order.setSource("14");      // 14:代垫费用申请单(驳回)
            order.setOaid(matcost.getOaid());
            order.setRemark("OA驳回：" + matcost.getOaid());
            order.setStatus("2");
            order.setBankdeptid(oldCollectOrder.getBankdeptid());

            int ret = collectionOrderForOaService.addCollectionOrder(order) ? 1 : 0;
            collectionOrderForOaService.auditCollectionOrder(order.getId(), true);

            count ++;
        }

        return count;
    }

    @Override
    public boolean checkCollectionOrderByOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception {

        List<CollectionOrder> list = collectionOrderForOaService.selectCollectionOrderByOaid(matcost.getOaid());
        if(list.size() == 0) {
            return true;
        }

        CollectionOrder collectionOrder = list.get(0);
        if("14".equals(collectionOrder.getSource())) {
            return true;
        }

        return false;
    }
}
