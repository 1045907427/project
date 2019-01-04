package com.hd.agent.oa.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.account.model.CustomerPushBalance;
import com.hd.agent.account.service.ICustomerPushBanlanceService;
import com.hd.agent.activiti.model.Delegate;
import com.hd.agent.activiti.service.IWorkService;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.journalsheet.model.CustomerCostPayable;
import com.hd.agent.journalsheet.service.IJournalSheetService;
import com.hd.agent.oa.model.*;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.service.ISysCodeService;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.oa.service.IOaBusinessService;
import com.hd.agent.oa.service.IOaOffPriceService;
import com.hd.agent.sales.model.Offprice;
import com.hd.agent.sales.model.OffpriceDetail;
import com.hd.agent.sales.service.IOffpriceService;

/**
 * OA模块接口
 *
 * @author limin
 * @date Mar 10, 2015
 */
public class OaBusinessServiceImpl extends BaseFilesServiceImpl implements IOaBusinessService {

    /**
     * 销售特价接口
     */
    private IOffpriceService offpriceService;

    /**
     *
     */
    private IOaOffPriceService oaOffPriceService;

    private ISysCodeService sysCodeService;

    private ICustomerPushBanlanceService customerPushBanlanceService;

    private IJournalSheetService journalSheetService;

    private IWorkService workService;

    public IOffpriceService getOffpriceService() {
        return offpriceService;
    }

    public void setOffpriceService(IOffpriceService offpriceService) {
        this.offpriceService = offpriceService;
    }

    public IOaOffPriceService getOaOffPriceService() {
        return oaOffPriceService;
    }

    public void setOaOffPriceService(IOaOffPriceService oaOffPriceService) {
        this.oaOffPriceService = oaOffPriceService;
    }

    public ISysCodeService getSysCodeService() {
        return sysCodeService;
    }

    public void setSysCodeService(ISysCodeService sysCodeService) {
        this.sysCodeService = sysCodeService;
    }

    public ICustomerPushBanlanceService getCustomerPushBanlanceService() {
        return customerPushBanlanceService;
    }

    public void setCustomerPushBanlanceService(ICustomerPushBanlanceService customerPushBanlanceService) {
        this.customerPushBanlanceService = customerPushBanlanceService;
    }

    public IJournalSheetService getJournalSheetService() {
        return journalSheetService;
    }

    public void setJournalSheetService(IJournalSheetService journalSheetService) {
        this.journalSheetService = journalSheetService;
    }

    public IWorkService getWorkService() {
        return workService;
    }

    public void setWorkService(IWorkService workService) {
        this.workService = workService;
    }

    @Override
    public Map addOffPriceByOffPrice(OaOffPrice price, List<OaOffPriceDetail> list) throws Exception {

        Map map = new HashMap();
        String billid = null;
        String message = null;
        boolean flag = false;

        if(price == null || list == null || list.size() == 0) {

            map.put("message", "客户或商品未指定！");
            map.put("flag", false);
        } else {

            String customerid = price.getCustomerid();
            Customer customer = getCustomerByID(customerid);
            if(customer == null) {

                map.put("message", "客户不存在！");
                map.put("flag", false);

            } else {

                Offprice offprice = new Offprice();
                if("1".equals(customer.getIslast())){
                    offprice.setCustomertype("1");
                }else{
                    offprice.setCustomertype("6");
                }
                offprice.setCustomerid(price.getCustomerid());
                offprice.setBusinessdate(CommonUtils.getTodayDataStr());
                offprice.setBegindate(price.getPricebegindate());
                offprice.setEnddate(price.getPriceenddate());
                offprice.setSchedule(price.getSchedule());
                SysUser applyUser = getSysUserById(price.getAdduserid());

                if(applyUser != null){
                    offprice.setApplyuserid(applyUser.getPersonnelid());
                    offprice.setApplydeptid(applyUser.getDepartmentid());
                }

                offprice.setRemark("通过批量特价调整单生成, OA号:" + price.getOaid() + ", " + price.getRemark());
                offprice.setOaid(price.getOaid());

                List<OffpriceDetail> detailList = new ArrayList<OffpriceDetail>();
                for(OaOffPriceDetail detail : list){

                    OffpriceDetail offpriceDetail = new OffpriceDetail();

                    if(StringUtils.isNotEmpty(detail.getGoodsid())){

                        offpriceDetail.setGoodsid(detail.getGoodsid());
                        offpriceDetail.setOldprice(detail.getOldprice());
                        offpriceDetail.setOffprice(detail.getOffprice());
                        offpriceDetail.setLownum(BigDecimal.ZERO);
                        offpriceDetail.setUpnum(new BigDecimal(9999));
                        offpriceDetail.setRemark(detail.getRemark());
                        detailList.add(offpriceDetail);
                    }
                }

                billid = offpriceService.addAndAuditOffpriceToOa(offprice, detailList);
                if(StringUtils.isNotEmpty(billid)){
                    flag = true;
                }else{
                    message = "生成特价调整单失败！";
                }

                map.put("message", message);
                map.put("flag", flag);
            }
        }

        return map;
    }

    @Override
    public Map rollbackOffPriceByOffPrice(OaOffPrice price) throws Exception {

        Map map = new HashMap();
        boolean flag = offpriceService.deleteOffpriceByOA(price.getOaid());

        map.put("flag", flag);
        if(flag) {
            map.put("message", "删除特价调整单成功。");
        } else {
            map.put("message", "删除特价调整单失败！");
        }

        return map;
    }

    @Override
    public Offprice oaOffPriceSelectOffPrice(String id) throws Exception {

        return offpriceService.selectOffPriceByOaid(id);
    }

    @Override
    public int editGoodsInfoByOaGoodsPriceAdjustment(OaGoodsPrice oaGoodsPrice, List<OaGoodsPriceDetail> oaGoodsPriceDetails) throws Exception {
       //修改的商品信息条数
        int amout = 0;
       for(OaGoodsPriceDetail detail : oaGoodsPriceDetails){

           String goodsid = detail.getGoodsid();
           GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
           GoodsInfo oldGoods = getGoodsInfoByID(goodsid);

           // 4544 通用版：商品调价申请单，如果采购价调整了那么要修改商品档案中的最新采购价
           if(oldGoods.getHighestbuyprice().compareTo(detail.getNewbuytaxprice()) != 0) {
               goodsInfo.setNewbuyprice(detail.getNewbuytaxprice());
           }

           goodsInfo.setHighestbuyprice(detail.getNewbuytaxprice());
           goodsInfo.setCostaccountprice(detail.getNewcostaccountprice());
           goodsInfo.setBasesaleprice(detail.getNewbasesaleprice());

           List<GoodsInfo_PriceInfo> priceInfoList = new ArrayList<GoodsInfo_PriceInfo>();
           List<SysCode> list = sysCodeService.showSysCodeListByType("price_list");
           for(int i = 0; i < list.size(); i++) {

               GoodsInfo_PriceInfo price = new GoodsInfo_PriceInfo();
               price.setGoodsid(detail.getGoodsid());

               price.setCode(list.get(i).getCode());
               price.setName(list.get(i).getCodename());

               price.setTaxprice(detail.getNewprice1());
               price.setBoxprice(detail.getNewprice1() == null ? BigDecimal.ZERO : detail.getNewprice1().multiply(new BigDecimal(detail.getBoxnum())));

               switch (i) {
                   case 0: {
                       BigDecimal newprice = detail.getNewprice1();
                       price.setTaxprice(newprice);
                       price.setBoxprice(newprice == null ? BigDecimal.ZERO : newprice.multiply(new BigDecimal(detail.getBoxnum())));
                       break;
                   }
                   case 1: {
                       BigDecimal newprice = detail.getNewprice2();
                       price.setTaxprice(newprice);
                       price.setBoxprice(newprice == null ? BigDecimal.ZERO : newprice.multiply(new BigDecimal(detail.getBoxnum())));
                       break;
                   }
                   case 2: {
                       BigDecimal newprice = detail.getNewprice3();
                       price.setTaxprice(newprice);
                       price.setBoxprice(newprice == null ? BigDecimal.ZERO : newprice.multiply(new BigDecimal(detail.getBoxnum())));
                       break;
                   }
                   case 3: {
                       BigDecimal newprice = detail.getNewprice4();
                       price.setTaxprice(newprice);
                       price.setBoxprice(newprice == null ? BigDecimal.ZERO : newprice.multiply(new BigDecimal(detail.getBoxnum())));
                       break;
                   }
                   case 4: {
                       BigDecimal newprice = detail.getNewprice5();
                       price.setTaxprice(newprice);
                       price.setBoxprice(newprice == null ? BigDecimal.ZERO : newprice.multiply(new BigDecimal(detail.getBoxnum())));
                       break;
                   }
                   case 5: {
                       BigDecimal newprice = detail.getNewprice6();
                       price.setTaxprice(newprice);
                       price.setBoxprice(newprice == null ? BigDecimal.ZERO : newprice.multiply(new BigDecimal(detail.getBoxnum())));
                       break;
                   }
                   case 6: {
                       BigDecimal newprice = detail.getNewprice7();
                       price.setTaxprice(newprice);
                       price.setBoxprice(newprice == null ? BigDecimal.ZERO : newprice.multiply(new BigDecimal(detail.getBoxnum())));
                       break;
                   }
                   case 7: {
                       BigDecimal newprice = detail.getNewprice8();
                       price.setTaxprice(newprice);
                       price.setBoxprice(newprice == null ? BigDecimal.ZERO : newprice.multiply(new BigDecimal(detail.getBoxnum())));
                       break;
                   }
               }

               priceInfoList.add(price);
           }

           boolean flag = modifyGoodsPrices(goodsInfo, priceInfoList);
           if(flag){
               amout ++;
           }
       }
        return amout;
    }

    @Override
    public boolean checkCustomerPushBanlanceByOaExpensePush(OaExpensePush push) throws Exception {

        List<CustomerPushBalance> list = customerPushBanlanceService.selectCustomerPushBanlanceByOaid(push.getOaid());

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
    public int addCustomerPushBanlanceByOaExpensePush(OaExpensePush push, List<OaExpensePushDetail> list) throws Exception {

        if(!checkCustomerPushBanlanceByOaExpensePush(push)) {
            return 0;
        }

        if(push == null || "2".equals(push.getPtype())) {

            return 0;
        }

        int ret = 0;
        for(OaExpensePushDetail detail : list) {

            CustomerPushBalance customerPushBalance = new CustomerPushBalance();
            customerPushBalance.setCustomerid(detail.getCustomerid());
            customerPushBalance.setPushtype(detail.getPushtype());
            customerPushBalance.setBrandid(detail.getBrandid());
            customerPushBalance.setAmount(detail.getAmount().negate());
            customerPushBalance.setRemark("OA编号：" + push.getOaid() + "； " + detail.getRemark());
            customerPushBalance.setBusinessdate(CommonUtils.getTodayDataStr());
            customerPushBalance.setIsinvoice("0");
            customerPushBalance.setOaid(push.getOaid());
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
                        BigDecimal noTaxamount = getNotaxAmountByTaxAmount(detail.getAmount(), taxType.getId()).negate();
                        BigDecimal taxAmount = detail.getAmount().negate().subtract(noTaxamount);

//                        customerPushBalance.setAmount(detail.getAmount());

                        customerPushBalance.setNotaxamount(noTaxamount);
                        customerPushBalance.setTax(taxAmount);
                    }
                }
            }

            customerPushBalance.setSalesdept(detail.getDeptid());
            DepartMent dept = getDepartMentById(detail.getDeptid());
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
    public int addCustomerPushBanlanceByOaExpensePush(OaExpensePush push, List<OaExpensePushDetail> list, String pushtype) throws Exception {

        if(!checkCustomerPushBanlanceByOaExpensePush(push)) {
            return 0;
        }

        if(push == null || "2".equals(push.getPtype())) {

            return 0;
        }

        int ret = 0;
        for(OaExpensePushDetail detail : list) {

            CustomerPushBalance customerPushBalance = new CustomerPushBalance();
            customerPushBalance.setCustomerid(detail.getCustomerid());
            customerPushBalance.setPushtype(pushtype);
            customerPushBalance.setBrandid(detail.getBrandid());
            customerPushBalance.setAmount(detail.getAmount().negate());
            customerPushBalance.setRemark("OA编号：" + push.getOaid() + "； " + detail.getRemark());
            customerPushBalance.setBusinessdate(CommonUtils.getTodayDataStr());
            customerPushBalance.setIsinvoice("0");
            customerPushBalance.setOaid(push.getOaid());
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
                        BigDecimal noTaxamount = getNotaxAmountByTaxAmount(detail.getAmount(), taxType.getId()).negate();
                        BigDecimal taxAmount = detail.getAmount().negate().subtract(noTaxamount);

//                        detail.setAmount(detail.getAmount());

                        customerPushBalance.setNotaxamount(noTaxamount);
                        customerPushBalance.setTax(taxAmount);
                    }
                }
            }

            customerPushBalance.setSalesdept(detail.getDeptid());
            DepartMent dept = getDepartMentById(detail.getDeptid());
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
    public int rollbackCustomerPushBanlanceByOaExpensePush(OaExpensePush push) throws Exception {

        if(checkCustomerPushBanlanceByOaExpensePush(push)) {
            return 0;
        }

        List<CustomerPushBalance> list = customerPushBanlanceService.selectCustomerPushBanlanceByOaid(push.getOaid());

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
    public int addCustomerPayByOaExpensePush(OaExpensePush push, List<OaExpensePushDetail> list)
            throws Exception {

        if(!checkCustomerPayByOaExpensePush(push)) {
            return 0;
        }

        int count = 0;

        for(OaExpensePushDetail detail : list) {

            CustomerCostPayable customerCostPayable = new CustomerCostPayable();
            customerCostPayable.setCustomerid(push.getCustomerid());
            Customer customer = getCustomerByID(push.getCustomerid());
            if(null!=customer){
                customerCostPayable.setPcustomerid(customer.getPid());
                if("0".equals(customer.getIslast())){
                    customerCostPayable.setPcustomerid(push.getCustomerid());
                }
            }
            customerCostPayable.setBusinessdate(CommonUtils.getTodayDataStr());
            customerCostPayable.setIspay("1");
            // customerCostPayable.setExpensesort(push.getExpensesort());
            customerCostPayable.setOaid(push.getOaid());
            customerCostPayable.setRelateoaid(detail.getOaid());
            customerCostPayable.setBilltype("2");	// 支付
            customerCostPayable.setApplyuserid(push.getAdduserid());

            SysUser applyUser = getSysUserById(push.getAdduserid());
            if(applyUser != null) {
                customerCostPayable.setApplydeptid(applyUser.getDepartmentid());
            }
            customerCostPayable.setDeptid(detail.getDeptid());
            // 供应商
            Brand brand = getGoodsBrandByID(detail.getBrandid());
            if(brand != null) {
                customerCostPayable.setSupplierid(brand.getSupplierid());
            }
            //费用金额
            customerCostPayable.setAmount(detail.getAmount() == null ? BigDecimal.ZERO : detail.getAmount());
            customerCostPayable.setSourcefrom("11");    // 11：费用冲差支付单
            customerCostPayable.setPaytype("2");		// 2:冲差
            // 4552
            customerCostPayable.setExpensesort(detail.getExpensesort());
            boolean ret = journalSheetService.addCustomerCostPayable(customerCostPayable);
            if(ret) {
                count ++;
            }
        }

        return count;
    }

    @Override
    public int rollbackCustomerPayByOaExpensePush(OaExpensePush push) throws Exception {

        if(checkCustomerPayByOaExpensePush(push)) {
            return 0;
        }

//        boolean flag = journalSheetService.deleteCustomerCostPayableByOaid(push.getOaid());
//        return flag ? 1 : 0;
        List<CustomerCostPayable> list = journalSheetService.selectCustomerCostPayablByOaid(push.getOaid());
        int count = 0;
        for(CustomerCostPayable oldCustomerCostPayable : list) {

            if("19".equals(oldCustomerCostPayable.getSourcefrom())) {
                break;
            }

            CustomerCostPayable newCustomerCostPayable = new CustomerCostPayable();
            newCustomerCostPayable.setCustomerid(oldCustomerCostPayable.getCustomerid());
            newCustomerCostPayable.setPcustomerid(oldCustomerCostPayable.getPcustomerid());
            newCustomerCostPayable.setBusinessdate(CommonUtils.getTodayDataStr());
            newCustomerCostPayable.setIspay("1");
            // customerCostPayable.setExpensesort(push.getExpensesort());
            newCustomerCostPayable.setOaid(oldCustomerCostPayable.getOaid());
            newCustomerCostPayable.setRelateoaid(oldCustomerCostPayable.getRelateoaid());
            newCustomerCostPayable.setBilltype("2");	// 支付
            newCustomerCostPayable.setApplyuserid(push.getAdduserid());

            SysUser applyUser = getSysUserById(push.getAdduserid());
            if(applyUser != null) {
                newCustomerCostPayable.setApplydeptid(applyUser.getDepartmentid());
            }

            newCustomerCostPayable.setDeptid(oldCustomerCostPayable.getDeptid());
            // 供应商
            newCustomerCostPayable.setSupplierid(oldCustomerCostPayable.getSupplierid());
            //费用金额
            newCustomerCostPayable.setAmount(oldCustomerCostPayable.getAmount().negate());
            newCustomerCostPayable.setSourcefrom("19");    // 19：费用冲差支付单(驳回)
            newCustomerCostPayable.setPaytype("2");		   // 2:冲差
            newCustomerCostPayable.setExpensesort(oldCustomerCostPayable.getExpensesort());
            boolean ret = journalSheetService.addCustomerCostPayable(newCustomerCostPayable);
            if(ret) {
                count ++;
            }
        }

        return count;
    }

    @Override
    public boolean checkCustomerPayByOaExpensePush(OaExpensePush push) throws Exception {

        List<CustomerCostPayable> list = journalSheetService.selectCustomerCostPayablByOaid(push.getOaid());

        if(list.size() % 2 == 1) {
            return false;
        }

        if(list.size() == 0) {
            return true;
        }

        if("19".equals(list.get(0).getSourcefrom())) {

            return true;
        }

        return false;
    }

    @Override
    public int addDelegateByOaDelegate(OaDelegate oaDelegate) throws Exception {

        if(!checkDelegateByOaDelegate(oaDelegate)) {
            return 0;
        }

        SysUser user = getSysUser();

        Delegate delegate = new Delegate();
        delegate.setStatus("3");
        delegate.setAdduserid(user.getUserid());
        delegate.setAddusername(user.getName());
        delegate.setAdddeptid(user.getDepartmentid());
        delegate.setAdddeptname(user.getDepartmentname());
        delegate.setRemark("OA编号：" + oaDelegate.getOaid());
        delegate.setDefinitionkey(oaDelegate.getDefinitionkey());
        delegate.setUserid(oaDelegate.getUserid());
        delegate.setDelegateuserid(oaDelegate.getDelegateuserid());
        delegate.setBegindate(oaDelegate.getBegindate());
        delegate.setEnddate(oaDelegate.getEnddate());
        delegate.setRemain(oaDelegate.getRemain());
        delegate.setOaid(oaDelegate.getOaid());
        boolean flag = workService.addDelegate(delegate);
        return flag ? 1 : 0;
    }

    @Override
    public int rollbackDelegateByOaDelegate(OaDelegate oaDelegate) throws Exception {

        if(checkDelegateByOaDelegate(oaDelegate)) {
            return 0;
        }
        return workService.deleteDelegateByOaid(oaDelegate.getOaid());
    }

    @Override
    public boolean checkDelegateByOaDelegate(OaDelegate delegate) throws Exception {
        return workService.selectDelegateByOaid(delegate.getOaid()) == null;
    }
}
