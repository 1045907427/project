package com.hd.agent.phone.service.impl;

import com.hd.agent.account.model.ReceiptHand;
import com.hd.agent.account.model.ReceiptHandBill;
import com.hd.agent.account.model.ReceiptHandCustomer;
import com.hd.agent.account.service.IReceiptHandService;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.phone.service.IPhoneReceiptHandService;
import com.hd.agent.sales.model.Receipt;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 回单交接单service实现方法
 * Created by chenwei on 2015-05-27.
 */
public class PhoneReceiptHandServiceImpl extends BaseFilesServiceImpl implements IPhoneReceiptHandService {
    /**
     * 回单交接单Service
     */
    private IReceiptHandService receiptHandService;

    public IReceiptHandService getReceiptHandService() {
        return receiptHandService;
    }

    public void setReceiptHandService(IReceiptHandService receiptHandService) {
        this.receiptHandService = receiptHandService;
    }

    /**
     * 通过查询条件 获取客户的应收款信息
     *
     * @param con
     * @return
     * @throws Exception
     */
    @Override
    public List getReceiptListGroupByCustomerForReceiptHand(String con) throws Exception {
        List<Receipt> list = receiptHandService.getReceiptHandGroupCustomerForPhone(con);
        if(null!=list && list.size()>0){
            List dataList = new ArrayList();
            for(Receipt receipt : list){
                Map map = new HashMap();
                Customer customer = getCustomerByID(receipt.getCustomerid());
                if(null!=customer){
                    map.put("customerid",receipt.getCustomerid());
                    map.put("customername","["+receipt.getCustomerid()+"]"+customer.getName());
                }
                map.put("unhandAmount",receipt.getTotalreceipttaxamount());
                dataList.add(map);
            }
            return dataList;
        }
        return null;
    }

    /**
     * 根据条件 获取客户回单交接单相关单据列表
     *
     * @param pageMap
     * @return
     * @throws Exception
     */
    @Override
    public Map getCustomerReceiptHandDetailList(PageMap pageMap) throws Exception {
        if(pageMap.getCondition().containsKey("customerid")){
            String customerid = (String) pageMap.getCondition().get("customerid");
            Customer customer = getCustomerByID(customerid);
            if(null!=customer){
                Map map = receiptHandService.getReceiptListOfReceiptDetail(pageMap);
                List<Receipt> list = (List)map.get("list");
                BigDecimal totalreceipttaxamount = (BigDecimal)map.get("totalreceipttaxamount");
                List dataList = new ArrayList();
                for (Receipt receipt : list){
                    Map objMap = new HashMap();
                    objMap.put("id",receipt.getId());
                    objMap.put("orderid",receipt.getSaleorderid());
                    objMap.put("billtype",receipt.getBilltype());
                    objMap.put("billtypename",receipt.getBilltypename());
                    objMap.put("businessdate",receipt.getBusinessdate());
                    objMap.put("customerid",receipt.getCustomerid());
                    objMap.put("customername",receipt.getCustomername());
                    objMap.put("receiptAmount",receipt.getTotalreceipttaxamount());
                    objMap.put("addusername", receipt.getAddusername());
                    dataList.add(objMap);
                }
                Map customerinfo = new HashMap();
                customerinfo.put("customerid",customerid);
                customerinfo.put("customername",customer.getName());
                customerinfo.put("unhandAmount",totalreceipttaxamount);
                customerinfo.put("handAmount",BigDecimal.ZERO);
                Map returnMap = new HashMap();
                returnMap.put("billList",dataList);
                returnMap.put("customerinfo",customerinfo);
                return returnMap;
            }
        }
        return null;
    }

    /**
     * 根据单据号 生成回单交接单
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public Map addReceiptHand(String ids) throws Exception {
        Map map = receiptHandService.addReceiptHand(ids,true);
        boolean flag = (Boolean)map.get("flag");
        if(flag){
            String id = (String) map.get("id");
            String rmsg = (String) map.get("msg");
            String msg = "生成回单交接单："+id+"。"+rmsg;
            map.put("msg",msg);
        }else{
            map.put("msg","失败");
        }
        return map;
    }

    /**
     * 根据条件获取回单交接单列表
     *
     * @param pageMap
     * @return
     * @throws Exception
     */
    @Override
    public List getReceiptHandList(PageMap pageMap) throws Exception {
        List<ReceiptHand> list = receiptHandService.getReceiptHandListForPhone(pageMap);
        List dataList = new ArrayList();
        for(ReceiptHand receiptHand : list){
            Map map = new HashMap();
            map.put("id",receiptHand.getId());
            map.put("businessdate",receiptHand.getBusinessdate());
            map.put("handusername",receiptHand.getHandusername()==null?"":receiptHand.getHandusername());
            map.put("handdeptname",receiptHand.getHanddeptname()==null?"":receiptHand.getHanddeptname());
            map.put("billnums",receiptHand.getBillnums());
            String statusname = "";
            if("2".equals(receiptHand.getStatus() )){
                statusname = "保存";
            }else if("3".equals(receiptHand.getStatus())){
                statusname = "审核通过";
            }else if("4".equals(receiptHand.getStatus())){
                statusname = "关闭";
            }
            map.put("statusname",statusname);
            map.put("cnums",receiptHand.getCnums());
            map.put("collectamount",receiptHand.getCollectamount());
            map.put("remark",receiptHand.getRemark());

            dataList.add(map);
        }
        return dataList;
    }

    /**
     * 获取回单交接单客户列表
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public List getReceiptHandCustomerList(String id) throws Exception {
        Map map = receiptHandService.getReceiptHandCustomerListByBillid(id);
        if(null!=map && map.containsKey("list")){
            List<ReceiptHandCustomer> list = (List<ReceiptHandCustomer>) map.get("list");
            List dataList = new ArrayList();
            for(ReceiptHandCustomer receiptHandCustomer : list){
                Map dataMap = new HashMap();
                dataMap.put("customerid",receiptHandCustomer.getCustomerid());
                dataMap.put("customername","["+receiptHandCustomer.getCustomerid()+"]"+receiptHandCustomer.getCustomername());
                dataMap.put("billnums",receiptHandCustomer.getBillnums());
                dataMap.put("amount",receiptHandCustomer.getCollectionamount());
                dataMap.put("remark",receiptHandCustomer.getRemark());
                dataMap.put("sourceids",receiptHandCustomer.getSourceids());
                dataList.add(dataMap);
            }
            return dataList;
        }
        return null;
    }

    /**
     * 根据交接单单据编号和客户编号 获取客户的单据列表
     *
     * @param id
     * @param customerid
     * @return
     * @throws Exception
     */
    @Override
    public List getReceiptHandBillList(String id, String customerid) throws Exception {
        List<ReceiptHandBill> list = receiptHandService.getReceiptHandBillListByBillidAndCustomerid(id,customerid);
        List dataList = new ArrayList();
        for(ReceiptHandBill receiptHandBill : list){
            String saleorderid="";
            String sourceid="";
            Map map = new HashMap();
            map.put("id",receiptHandBill.getRelatebillid());
            //1发货回单2销售退货通知单3冲差单
            String billtypename = "";
            if("1".equals(receiptHandBill.getBilltype())){
                billtypename = "发货回单";
                if(StringUtils.isNotEmpty(receiptHandBill.getRelatebillid())){
                    Map map1 = new HashMap();
                    map1.put("id",receiptHandBill.getRelatebillid());
                    Receipt receipt =getSalesReceiptMapper().getReceipt(map1);
                    if(null!=receipt){
                        sourceid=receipt.getSourceid();
                        saleorderid = receipt.getSaleorderid();
                    }
                }
            }else if("2".equals(receiptHandBill.getBilltype())){
                billtypename = "销售退货通知单";
            }else if("3".equals(receiptHandBill.getBilltype())){
                billtypename = "冲差单";
            }
            map.put("billtype",billtypename);
            map.put("businessdate",receiptHandBill.getBusinessdate());
            map.put("amount",receiptHandBill.getAmount());
            map.put("sourceid",sourceid);
            map.put("saleorderid",saleorderid);
            map.put("remark",receiptHandBill.getRemark());
            dataList.add(map);
        }
        return dataList;
    }

}
