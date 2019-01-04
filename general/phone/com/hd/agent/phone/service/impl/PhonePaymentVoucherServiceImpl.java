package com.hd.agent.phone.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.model.PaymentVoucher;
import com.hd.agent.account.model.PaymentVoucherDetail;
import com.hd.agent.account.service.IPaymentVoucherService;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.phone.service.IPhonePaymentVoucherService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.math.BigDecimal;
import java.util.*;

/**
 * 手机交款单service实现类
 * Created by chenwei on 2016-07-15.
 */
public class PhonePaymentVoucherServiceImpl extends BaseFilesServiceImpl implements IPhonePaymentVoucherService{

    private IPaymentVoucherService paymentVoucherService;

    public IPaymentVoucherService getPaymentVoucherService() {
        return paymentVoucherService;
    }

    public void setPaymentVoucherService(IPaymentVoucherService paymentVoucherService) {
        this.paymentVoucherService = paymentVoucherService;
    }

    /**
     * 添加交款单
     *
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public Map addPaymentVoucher(JSONObject jsonObject) throws Exception {
        boolean flag = false;
        String msg = "";
        String id = "";
        SysUser sysUser = getSysUser();
        if(null!=jsonObject && jsonObject.has("list")){
            //手机上传的单据编号 防止重复上传
            String billid = jsonObject.getString("billid");
            String oId = paymentVoucherService.hasPhoneBill(billid);
            if(null!=oId){
                Map map = new HashMap();
                map.put("flag",flag);
                map.put("msg","该单据已上传,交款单编号："+oId);
                map.put("id",oId);
                return map;
            }
            PaymentVoucher paymentVoucher = new PaymentVoucher();
            paymentVoucher.setField01(billid);
            if(jsonObject.has("remark")){
                String remark = jsonObject.getString("remark");
                paymentVoucher.setRemark(remark);
            }else{
                paymentVoucher.setRemark("");
            }
            if(jsonObject.has("bankid")){
                String bankid = jsonObject.getString("bankid");
                paymentVoucher.setBank(bankid);
            }else{
                paymentVoucher.setBank("");
            }
            if(jsonObject.has("date")){
                String date = jsonObject.getString("date");
                paymentVoucher.setBusinessdate(date);
            }else{
                paymentVoucher.setBusinessdate(CommonUtils.getTodayDataStr());
            }
            String personid = null;
            if(jsonObject.has("personid")){
                personid = jsonObject.getString("personid");
            }else{
                personid = sysUser.getPersonnelid();
            }
            Personnel person = getPersonnelById(personid);
            if(null!=person){
                if(null!=person.getEmployetype() && person.getEmployetype().indexOf("8")>=0){
                    paymentVoucher.setCollectuserid(person.getId());
                    paymentVoucher.setCollectusername(person.getName());
                }else{
                    msg = "交款人："+person.getName()+"不是收款人。";
                }
            }else{
                msg = sysUser.getName()+"不是收款人。";
            }

            paymentVoucher.setStatus("2");
            paymentVoucher.setAdduserid(sysUser.getUserid());
            paymentVoucher.setAddusername(sysUser.getName());
            paymentVoucher.setAdddeptid(sysUser.getDepartmentid());
            paymentVoucher.setAdddeptname(sysUser.getDepartmentname());
            paymentVoucher.setAddtime(new Date());

            List<PaymentVoucherDetail> list = new ArrayList<PaymentVoucherDetail>();
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            if(null!=jsonArray && jsonArray.size()>0){
                for(int i=0;i<jsonArray.size();i++){
                    JSONObject detailObject = jsonArray.getJSONObject(i);
                    PaymentVoucherDetail paymentVoucherDetail = new PaymentVoucherDetail();
                    String customerid = detailObject.getString("customerid");
                    Customer customer = getCustomerByID(customerid);
                    if(null!=customer){
                        paymentVoucherDetail.setCustomername(customer.getName());
                    }
                    String remark = "";
                    if(detailObject.has("remark")){
                        remark = detailObject.getString("remark");
                    }
                    BigDecimal amount = BigDecimal.ZERO;
                    if(detailObject.has("amount")){
                        String amountStr = detailObject.getString("amount");
                        amount = new BigDecimal(amountStr);
                    }
                    paymentVoucherDetail.setCustomerid(customerid);
                    paymentVoucherDetail.setRemark(remark);
                    paymentVoucherDetail.setAmount(amount);

                    list.add(paymentVoucherDetail);
                }
            }
            paymentVoucher.setPaymentVoucherDetailList(list);

            flag = paymentVoucherService.addPaymentVoucherAddDetail(paymentVoucher);
            if(flag){
                id = paymentVoucher.getId();
                msg += "生成交款单："+paymentVoucher.getId();
            }
        }
        Map map = new HashMap();
        map.put("flag",flag);
        map.put("msg",msg);
        map.put("id",id);
        return map;
    }

    /**
     * 获取交款单列表
     *
     * @param pageMap
     * @return
     * @throws Exception
     */
    @Override
    public List getPaymentVoucherList(PageMap pageMap) throws Exception {
        List<PaymentVoucher> list = paymentVoucherService.showPaymentVoucherListForPhone(pageMap);
        List dataList = new ArrayList();
        for(PaymentVoucher paymentVoucher : list){
            Map map = new HashMap();
            map.put("id",paymentVoucher.getId());
            map.put("businessdate",paymentVoucher.getBusinessdate());
            map.put("collectusername",paymentVoucher.getCollectusername()==null?"":paymentVoucher.getCollectusername());
            String statusname = "";
            if("2".equals(paymentVoucher.getStatus() )){
                statusname = "保存";
            }else if("3".equals(paymentVoucher.getStatus())){
                statusname = "审核通过";
            }else if("4".equals(paymentVoucher.getStatus())){
                statusname = "关闭";
            }
            map.put("statusname",statusname);
            map.put("billcount",paymentVoucher.getBillcount());
            map.put("totalamount",paymentVoucher.getTotalamount());
            map.put("remark",paymentVoucher.getRemark());

            dataList.add(map);
        }
        return dataList;
    }

    /**
     * 获取交款单信息
     *
     * @param billid
     * @return
     * @throws Exception
     */
    @Override
    public Map getPaymentVoucherInfo(String billid) throws Exception {
        PaymentVoucher paymentVoucher = paymentVoucherService.showPaymentVoucherAndDetail(billid);
        Map map = new HashMap();
        if(null!=paymentVoucher){
            map.put("billid",paymentVoucher.getId());
            map.put("businessdate",paymentVoucher.getBusinessdate());
            map.put("collectusername",paymentVoucher.getCollectusername()==null?"":paymentVoucher.getCollectusername());
            map.put("bankid",paymentVoucher.getBank()==null?"":paymentVoucher.getBank());
            map.put("remark",paymentVoucher.getRemark()==null?"":paymentVoucher.getRemark());
            map.put("totalamount",paymentVoucher.getTotalamount().setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            List list = new ArrayList();
            List<PaymentVoucherDetail> details = paymentVoucher.getPaymentVoucherDetailList();
            for(PaymentVoucherDetail paymentVoucherDetail : details){
                Map dmap = new HashMap();
                dmap.put("customerid",paymentVoucherDetail.getCustomerid());
                Customer customer = getCustomerByID(paymentVoucherDetail.getCustomerid());
                if(null!=customer){
                    dmap.put("customername",customer.getName());
                }else{
                    dmap.put("customername",paymentVoucherDetail.getCustomername());
                }
                dmap.put("remark",paymentVoucherDetail.getRemark()==null?"":paymentVoucherDetail.getRemark());
                dmap.put("amount",paymentVoucherDetail.getAmount().setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                list.add(dmap);
            }
            map.put("list",list);
        }
        return map;
    }
}
