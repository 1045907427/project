/**
 * @(#)OrderExtServiceImpl.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 7, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.service.impl.ext;

import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.TaxType;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.sales.model.*;
import com.hd.agent.sales.service.IOrderService;
import com.hd.agent.sales.service.ext.IOrderExtService;
import com.hd.agent.sales.service.impl.BaseSalesServiceImpl;

import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * 销售订单对外接口
 *
 * @author zhengziyong
 */
public class OrderExtServiceImpl extends BaseSalesServiceImpl implements IOrderExtService {

    @Override
    public String addOrderAuto(Demand demand) throws Exception {
        if (null != demand) {
            Customer customer = getCustomerByID(demand.getCustomerid());
            if (null != customer) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                IOrderService orderService = (IOrderService) SpringContextUtils.getBean("salesOrderService");
                String result = null;
                Order order = new Order();
                String OpenDeptStorage = getSysParamValue("OpenDeptStorage");
                if(!"1".equals(OpenDeptStorage)){
                	if (StringUtils.isNotEmpty(demand.getStorageid())) {
                        order.setStorageid(demand.getStorageid());
                    }
                }else if("1".equals(OpenDeptStorage)){
        			DepartMent departMent = getDepartmentByDeptid(demand.getAdddeptid());
                    if (null != departMent) {
                    	order.setStorageid(departMent.getStorageid());
                    }
                }
                
                order.setBusinessdate(dateFormat.format(new Date()));
                order.setField01(demand.getField01());
                order.setField02(demand.getField02());
                order.setField03(demand.getField03());
                order.setField04(demand.getField04());
                order.setField05(demand.getField05());
                order.setField06(demand.getField06());
                order.setField07(demand.getField07());
                order.setField08(demand.getField08());
                order.setHandlerid(demand.getHandlerid());
                order.setPaytype(demand.getPaytype());
                order.setRemark(demand.getRemark());
                order.setSalesdept(demand.getSalesdept());
                List<OrderGoods> orderGoodsList=getOrderGoodsMapper().getOrderGoodsListByIds(demand.getOrdergoodsid());
                if(orderGoodsList.size()>0){
                    order.setSalesdept(orderGoodsList.get(0).getSalesdept());
                }
                order.setSalesuser(demand.getSalesuser());
                order.setSettletype(demand.getSettletype());
                order.setSourceid(demand.getId());
                order.setSourcetype("1");
                order.setStatus("2");
                order.setCustomerid(demand.getCustomerid());
                order.setAdduserid(demand.getAdduserid());
                order.setAddusername(demand.getAddusername());
                order.setAdddeptid(demand.getAdddeptid());
                order.setAdddeptname(demand.getAdddeptname());
                order.setDemandtime(demand.getAddtime());
                //判断是否按商品编码排序
                String orderDetailSortGoods = getSysParamValue("OrderDetailSortGoods");
                if ("1".equals(orderDetailSortGoods)) {
                    order.setIsgoodsseq("1");
                }
                if (!isAutoCreate("t_sales_order")) {
                    order.setId(demand.getId());
                }
                order.setOrderDetailList(demandDetailToOrderDetail(demand.getDetailList(), order.getId(), order.getCustomerid()));
                Map returnmap = orderService.addOrder(order, "save");
                boolean flag = (Boolean) returnmap.get("flag");
                if (flag) {
                    result = order.getId();
                }
                return result;
            }
        }
        return null;
    }

    @Override
    public String addOrderSplitByDept(Demand demand) throws Exception {
        String ids = "";
        if (null != demand) {
            List<DemandDetail> list = demand.getDetailList();
            if (null != list) {
                Map deptMap = new HashMap();
                for (DemandDetail demandDetail : list) {
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(demandDetail.getGoodsid());
                    if (null != goodsInfo) {
                        if (deptMap.containsKey(goodsInfo.getDeptid())) {
                            List<DemandDetail> detailList = (List<DemandDetail>) deptMap.get(goodsInfo.getDeptid());
                            detailList.add(demandDetail);
                            deptMap.put(goodsInfo.getDeptid(), detailList);
                        } else {
                            List<DemandDetail> detailList = new ArrayList<DemandDetail>();
                            detailList.add(demandDetail);
                            deptMap.put(goodsInfo.getDeptid(), detailList);
                        }
                    }
                }
                Set set = deptMap.entrySet();
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    Map.Entry<String, List<DemandDetail>> entry = (Entry<String, List<DemandDetail>>) it.next();
                    String deptid = entry.getKey();
                    List<DemandDetail> detailList = entry.getValue();

                    Demand demand2 = (Demand) CommonUtils.deepCopy(demand);
                    demand2.setSalesdept(deptid);
//					Personnel personnel = getSamePersonnelByDeptid(deptid,demand.getSalesuser());
//					if(null!=personnel){
//						demand2.setSalesuser(personnel.getId());
//					}else{
//						demand2.setSalesuser("");
//					}
                    DepartMent departMent = getDepartmentByDeptid(deptid);
                    if (null != departMent) {
                        demand2.setStorageid(departMent.getStorageid());
                    }
                    demand2.setDetailList(detailList);
                    String id = addOrderAuto(demand2);
                    if (StringUtils.isEmpty(ids)) {
                        ids = id;
                    } else {
                        ids += "," + id;
                    }
                }
            }
        }
        return ids;
    }

    /**
     * 审核发货通知单回写订单明细
     *
     * @throws Exception
     * @author zhengziyong
     * @date May 21, 2013
     */
    @Override
    public void updateOrderDetailBack(List<DispatchBillDetail> billDetailList,String id) throws Exception {
        if (billDetailList != null) {
        	List<OrderDetail> orderDetaiList = getSalesOrderDetailMapper().getOrderDetailByOrderWithDiscount(id, null);
        	for(OrderDetail orderDetail: orderDetaiList){
        		orderDetail.setSendnummain(new BigDecimal("0"));
                orderDetail.setSendnumaux(new BigDecimal("0"));
                orderDetail.setSendamounttax(new BigDecimal("0"));
                orderDetail.setSendamountnotax(new BigDecimal("0"));
                orderDetail.setNosendnummain(orderDetail.getUnitnum().subtract(new BigDecimal("0")));
                orderDetail.setNosendnumaux(orderDetail.getAuxnum().subtract(new BigDecimal("0")));
                orderDetail.setNosendamounttax(orderDetail.getTaxamount().subtract(new BigDecimal("0")));
                orderDetail.setNosendamountnotax(orderDetail.getNotaxamount().subtract(new BigDecimal("0")));
                getSalesOrderDetailMapper().updateOrderDetailBack(orderDetail);
        	}
            for (DispatchBillDetail billDetail : billDetailList) {
                if (!"1".equals(billDetail.getIsdiscount())) {
                    OrderDetail detail = getSalesOrderDetailMapper().getOrderDetail(billDetail.getBilldetailno());
                    if (null != detail) {
                        OrderDetail orderDetail = new OrderDetail();
                        BigDecimal sendnum = BigDecimal.ZERO;
                        if (null != detail.getSendnummain()) {
                            sendnum = detail.getSendnummain().add(billDetail.getUnitnum());
                        } else {
                            sendnum = billDetail.getUnitnum();
                        }
                        BigDecimal sendauxnum = BigDecimal.ZERO;
                        if (null != detail.getSendnumaux()) {
                            sendauxnum = detail.getSendnumaux().add(billDetail.getTotalbox());
                        } else {
                            sendauxnum = billDetail.getTotalbox();
                        }
                        BigDecimal sendamounttax = BigDecimal.ZERO;
                        if (null != detail.getSendamounttax()) {
                            sendamounttax = detail.getSendamounttax().add(billDetail.getTaxamount());
                        } else {
                            sendamounttax = billDetail.getTaxamount();
                        }
                        BigDecimal sendamountnotax = BigDecimal.ZERO;
                        if (null != detail.getSendamounttax()) {
                            sendamountnotax = detail.getSendamountnotax().add(billDetail.getNotaxamount());
                        } else {
                            sendamountnotax = billDetail.getNotaxamount();
                        }

                        orderDetail.setSendnummain(sendnum);
                        orderDetail.setSendnumaux(sendauxnum);
                        orderDetail.setSendamounttax(sendamounttax);
                        orderDetail.setSendamountnotax(sendamountnotax);
                        orderDetail.setNosendnummain(detail.getUnitnum().subtract(sendnum));
                        orderDetail.setNosendnumaux(detail.getAuxnum().subtract(sendauxnum));
                        orderDetail.setNosendamounttax(detail.getTaxamount().subtract(sendamounttax));
                        orderDetail.setNosendamountnotax(detail.getNotaxamount().subtract(sendamountnotax));
                        orderDetail.setId(billDetail.getBilldetailno());
                        orderDetail.setOrderid(billDetail.getBillno());
                        getSalesOrderDetailMapper().updateOrderDetailBack(orderDetail);
                    }
                }
            }
        }
    }

    /**
     * 发货通知单反审时清除订单商品明细回写的数据
     *
     * @param billDetailList
     * @throws Exception
     * @author zhengziyong
     * @date May 22, 2013
     */
    @Override
    public void updateClearOrderDetailBack(List<DispatchBillDetail> billDetailList) throws Exception {
        if (billDetailList != null) {
            for (DispatchBillDetail billDetail : billDetailList) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setSendnummain(new BigDecimal(0));
                orderDetail.setSendnumaux(new BigDecimal(0));
                orderDetail.setSendamounttax(new BigDecimal(0));
                orderDetail.setSendamountnotax(new BigDecimal(0));
                orderDetail.setNosendnummain(new BigDecimal(0));
                orderDetail.setNosendnumaux(new BigDecimal(0));
                orderDetail.setNosendamounttax(new BigDecimal(0));
                orderDetail.setNosendamountnotax(new BigDecimal(0));
                orderDetail.setId(billDetail.getBilldetailno());
                orderDetail.setOrderid(billDetail.getBillno());
                getSalesOrderDetailMapper().updateOrderDetailBack(orderDetail);
            }
        }
    }

    /**
     * 发货通知单审核后关闭销售订单
     *
     * @throws Exception
     * @author zhengziyong
     * @date May 21, 2013
     */
    public boolean updateOrderClose(String id) throws Exception {
        Order order = new Order();
        order.setId(id);
        order.setClosetime(new Date());
        order.setStatus("4");
        return getSalesOrderMapper().updateOrderStatus(order) > 0;
    }

    /**
     * 发货通知单反审后订单状态需改回审核状态
     *
     * @param id
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date May 22, 2013
     */
    public boolean updateOrderOpen(String id) throws Exception {
        Order order = new Order();
        order.setId(id);
        order.setStatus("3");
        return getSalesOrderMapper().updateOrderStatus(order) > 0;
    }

    private List<OrderDetail> demandDetailToOrderDetail(List<DemandDetail> list, String orderId, String customerId) throws Exception {
        List<OrderDetail> detailList = new ArrayList<OrderDetail>();
        IOrderService orderService = (IOrderService) SpringContextUtils.getBean("salesOrderService");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());
        for (DemandDetail demandDetail : list) {
            OrderDetail detail = new OrderDetail();
            detail.setSeq(demandDetail.getSeq()==null?0:demandDetail.getSeq());
            detail.setOrderid(orderId);
            detail.setGoodsid(demandDetail.getGoodsid());
            detail.setGroupid(demandDetail.getGroupid());
            detail.setDeliverytype(demandDetail.getDeliverytype());
            GoodsInfo goodsInfo = getGoodsInfoByID(detail.getGoodsid());
            detail.setGoodsInfo(goodsInfo);
            detail.setUnitnum(demandDetail.getUnitnum());
            detail.setUnitid(demandDetail.getUnitid());
            detail.setUnitname(demandDetail.getUnitname());
            detail.setAuxunitid(demandDetail.getAuxunitid());
            detail.setAuxunitname(demandDetail.getAuxunitname());
            detail.setAuxnum(demandDetail.getAuxnum());
            detail.setOvernum(demandDetail.getOvernum());
            detail.setTotalbox(demandDetail.getTotalbox());
            detail.setAuxnumdetail(demandDetail.getAuxnumdetail());
            detail.setTaxtype(demandDetail.getTaxtype());
            detail.setDemandprice(demandDetail.getTaxprice());
            detail.setDemandamount(demandDetail.getTaxamount());
            ////Relationordergoodsid
            detail.setRelationordergoodsid(demandDetail.getRelationordergoodsid());
            //1买赠
            if ("1".equals(demandDetail.getDeliverytype())) {
                detail.setRemark("赠送");
            }else if("2".equals(demandDetail.getDeliverytype())){
            	OrderDetail orderDetail = orderService.getGoodsDetail(demandDetail.getGoodsid(), customerId, date, demandDetail.getUnitnum(), null);
                detail.setTaxprice(demandDetail.getTaxprice());
                detail.setOldprice(orderDetail.getTaxprice());
                BigDecimal taxamount=detail.getUnitnum().multiply(detail.getTaxprice());
                detail.setTaxamount(taxamount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                BigDecimal noTaxAmount = getNotaxAmountByTaxAmount(taxamount, detail.getTaxtype());
                detail.setNotaxamount(noTaxAmount);
                BigDecimal tax = detail.getTaxamount().subtract(detail.getNotaxamount());
                detail.setTax(tax);
                TaxType taxType = getTaxType(detail.getTaxtype());
                BigDecimal noTaxPrice = detail.getTaxprice().divide(taxType.getRate().divide(new BigDecimal(100)).add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP);
                detail.setNotaxprice(noTaxPrice);
            	//捆绑
            	String remark = demandDetail.getRemark();
                if (null == remark) {
                    remark = "";
                }
            	 detail.setRemark(remark);
            } else {
        		OrderDetail orderDetail = orderService.getGoodsDetail(demandDetail.getGoodsid(), customerId, date, demandDetail.getUnitnum(), null);
                if(StringUtils.isNotEmpty(orderDetail.getGroupid())){
                    detail.setGroupid(orderDetail.getGroupid());
                }
                //判断提交上来的数据是否买赠 买赠则不从新取价
                PromotionPackageGroup promotionPackageGroup = getSalesPromotionMapper().getBundleGroup(demandDetail.getGroupid());
                if(null==promotionPackageGroup){
                    detail.setTaxprice(orderDetail.getTaxprice());
                }else{
                    detail.setTaxprice(demandDetail.getTaxprice());
                }
                detail.setOldprice(orderDetail.getTaxprice());
                BigDecimal taxamount=detail.getUnitnum().multiply(detail.getTaxprice());
                detail.setTaxamount(taxamount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                BigDecimal noTaxAmount = getNotaxAmountByTaxAmount(taxamount, detail.getTaxtype());
                detail.setNotaxamount(noTaxAmount);
                BigDecimal tax = detail.getTaxamount().subtract(detail.getNotaxamount());
                detail.setTax(tax);
                TaxType taxType = getTaxType(detail.getTaxtype());
                BigDecimal noTaxPrice = detail.getTaxprice().divide(taxType.getRate().divide(new BigDecimal(100)).add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP);
                detail.setNotaxprice(noTaxPrice);
                String remark = demandDetail.getRemark();
                if (null == remark) {
                    remark = "";
                }
                if(StringUtils.isNotEmpty(orderDetail.getRemark())){
                    if (null == remark) {
                        remark = orderDetail.getRemark();
                    }else{
                        remark += " " +orderDetail.getRemark();
                    }
                }
                detail.setRemark(remark);
            }
            detailList.add(detail);
        }
        return detailList;
    }

    /**
     * 获取导入TXT文件的第一行内容，导入时第一行必须是字段描述的标题
     *
     * @param file
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Dec 26, 2013
     */
    public static List<String> importTxtFirstRow(File file) throws Exception {
        List<String> paramList = new ArrayList<String>();
        try {
            InputStream raf = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(raf));
            StringBuffer buffer = new StringBuffer();
            String line; // 用来保存每行读取的内容
            line = reader.readLine(); // 读取第一行
            if (StringUtils.isNotEmpty(line) && line.indexOf(",") != -1) {
                String[] firstArr = line.split(",");
                for (String fielddscrb : firstArr) {
                    //字段描述
                    if ("单号".equals(fielddscrb)) {
                        paramList.add("id");
                    } else if ("收货单位".equals(fielddscrb)) {
                        paramList.add("shopno");
                    } else if ("商品代码".equals(fielddscrb)) {
                        paramList.add("shopid");
                    } else if ("计量单位".equals(fielddscrb)) {
                        paramList.add("unitname");
                    } else if ("数量".equals(fielddscrb)) {
                        paramList.add("unitnum");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramList;
    }

    /**
     * 获取要导入的TXT数据
     *
     * @param file
     * @param paramList
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Dec 26, 2013
     */
    public static Map<String, List<Map<String, Object>>> importTXT(File file, List<String> paramList, String pid) throws Exception {
        Map<String, List<Map<String, Object>>> map2 = new HashMap<String, List<Map<String, Object>>>();
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        try {
            InputStream is = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is));
            //StringBuffer buffer = new StringBuffer();
            String line, line1, id = "", nextid = ""; // 用来保存每行读取的内容
            line = reader.readLine(); // 读取第一行
            line1 = reader.readLine();// 读取第二行
            while (line1 != null) { // 如果 line 为空说明读完了
                Map<String, Object> map = new HashMap<String, Object>();
                if (StringUtils.isNotEmpty(line1) && line1.indexOf(",") != -1) {
                    String[] dataArr = line1.split(",");
                    id = dataArr[0];
                    //判断单号是否相同，若相同则为同一单据
                    map.put("shopno", dataArr[2]);
                    map.put("customergoodsid", dataArr[3]);
                    map.put("unitnum", dataArr[5]);
                    if (map2.containsKey(id)) {
                        List dataList = map2.get(id);
                        dataList.add(map);
                        map2.put(id, dataList);
                    } else {
                        List dataList = new ArrayList();
                        dataList.add(map);
                        map2.put(id, dataList);
                    }
                }
                line1 = reader.readLine(); // 读取下一行
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map2;
    }
}

