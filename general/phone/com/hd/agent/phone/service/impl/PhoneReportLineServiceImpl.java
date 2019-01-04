package com.hd.agent.phone.service.impl;

import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.phone.dao.PhoneMapper;
import com.hd.agent.phone.dao.PhoneReportLineMapper;
import com.hd.agent.phone.service.IPhoneReportLineService;
import com.hd.agent.sales.service.impl.BaseSalesServiceImpl;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luoq on 2016/11/10.
 */
public class PhoneReportLineServiceImpl extends BaseSalesServiceImpl implements IPhoneReportLineService {
    private PhoneReportLineMapper phoneReportLineMapper;
    private PhoneMapper phoneMapper;

    public PhoneReportLineMapper getPhoneReportLineMapper() {
        return phoneReportLineMapper;
    }

    public void setPhoneReportLineMapper(PhoneReportLineMapper phoneReportLineMapper) {
        this.phoneReportLineMapper = phoneReportLineMapper;
    }

    public PhoneMapper getPhoneMapper() {
        return phoneMapper;
    }

    public void setPhoneMapper(PhoneMapper phoneMapper) {
        this.phoneMapper = phoneMapper;
    }

    /**
     * 查询要货单及订单数据
     * luoq 2016/11/10
     * @param pageMap
     * @return
     * @throws Exception
     */
    public List getSalesDemandBillReportData(PageMap pageMap) throws Exception {
        String dataSql = getDataAccessRule("t_sales_order", "t1");
        pageMap.setDataSql(dataSql);
        List<Map> list=phoneReportLineMapper.getSalesDemandBillReportData(pageMap);
        for(Map m:list){
            if(StringUtils.isNotEmpty((String) m.get("customerid"))){
                Map map = new HashMap();
                map.put("id", (String)m.get("customerid"));
                Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(map);
                if(customer != null){
                    m.put("customername",customer.getName());
                }
            }
            if(m.get("field01")!=null){
                BigDecimal taxamount = new BigDecimal(m.get("field01").toString());
                m.put("field01",taxamount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            }
        }
        return  list;
    }

    /**
     * 查询物流及单据状态
     * luoq 2016/11/10
     * @param parammap
     * @return
     * @throws Exception
     */
    @Override
    public Map getDemandBillOrderTrack(Map parammap) throws Exception {
        String id=(String)parammap.get("id");
        Map map=new HashMap();
        //没有要货单编号就查询订单信息
        if(StringUtils.isNotEmpty(id)) {
            map = phoneMapper.getOrderTrack(id);
        }
        //有要货单编号就查询要货单与订单信息
        else {
            String orderid = (String) parammap.get("orderid");
            map = phoneReportLineMapper.getOrderTrackByOrderid(orderid);
        }
        if(null==map){
            map = phoneMapper.getOrderCarTrack(id);
        }
        if(null!=map && map.containsKey("outid")){
            String outids = (String) map.get("outid");
            String[] outidarr = outids.split(",");
            String psid = "";
            String psmsg = "";
            List<Map> list = phoneMapper.getLogisticsBillBySaleoutid(outids);
            for(Map psmap : list){
                String psbillid = (String) psmap.get("id");
                if(!psid.equals(psbillid)){
                    String driverid = (String) psmap.get("driverid");
                    String followid = (String) psmap.get("followid");
                    Personnel drpersonnel = getPersonnelById(driverid);
                    Personnel flpersonnel = getPersonnelById(followid);
                    if(null!=drpersonnel){
                        psmsg +="<br/>配送单："+psbillid+";<br/>司机："+drpersonnel.getName()+",联系电话:<a href='javascript:callAndroid(\""+drpersonnel.getName()+"\",\""+drpersonnel.getTelphone()+"\");'>"+drpersonnel.getTelphone()+"</a>;";
                    }else{
                        psmsg +="<br/>配送单："+psbillid+";";
                    }
                    if(null!=flpersonnel){
                        psmsg +="<br/>跟车："+flpersonnel.getName()+",联系电话:<a href=\'javascript:callAndroid(\""+flpersonnel.getName()+"\",\""+flpersonnel.getTelphone()+"\");'>"+flpersonnel.getTelphone()+"</a>;";
                    }else{
                        psmsg +=";";
                    }
                }
            }
            if(StringUtils.isNotEmpty(psmsg)){
                map.put("showpsmsg", true);
                map.put("psmsg", psmsg);
            }else{
                map.put("showpsmsg", false);
                map.put("psmsg", "查询不到配送信息。");
            }

        }
        return map;
    }
}
