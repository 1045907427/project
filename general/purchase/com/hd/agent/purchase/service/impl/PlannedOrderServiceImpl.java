/**
 * @(#)BuyApplyImpl.java
 * @author zhanghonghui
 * <p>
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-5-6 zhanghonghui 创建版本
 */
package com.hd.agent.purchase.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.purchase.model.PlannedOrder;
import com.hd.agent.purchase.model.PlannedOrderDetail;
import com.hd.agent.purchase.service.IPlannedOrderService;
import com.hd.agent.purchase.service.ext.IPurchaseSelfExtService;
import com.hd.agent.report.model.StorageBuySaleReport;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

/**
 * 采购计划单服务实现
 *
 * @author zhanghonghui
 */
public class PlannedOrderServiceImpl extends BasePurchaseServiceImpl implements IPlannedOrderService {

    private IPurchaseSelfExtService purchaseSelfExtService;

    public IPurchaseSelfExtService getPurchaseSelfExtService() {
        return purchaseSelfExtService;
    }

    public void setPurchaseSelfExtService(IPurchaseSelfExtService purchaseSelfExtService) {
        this.purchaseSelfExtService = purchaseSelfExtService;
    }

    /**
     * 添加单采购计划单
     */
    private boolean insertPlannedOrder(PlannedOrder plannedOrder) throws Exception {
        return plannedOrderMapper.insertPlannedOrder(plannedOrder) > 0;
    }

    public boolean addPlannedOrderAddDetail(PlannedOrder plannedOrder) throws Exception {
        plannedOrder.setId(getBillNumber(plannedOrder, "t_purchase_plannedorder"));
        if (StringUtils.isEmpty(plannedOrder.getId())) {
            return false;
        }
        if (StringUtils.isEmpty(plannedOrder.getOrderappend())) {
            BuySupplier buySupplier = getSupplierInfoById(plannedOrder.getSupplierid());
            if (null != buySupplier) {
                plannedOrder.setOrderappend(buySupplier.getOrderappend());
            }
        }
        boolean flag = insertPlannedOrder(plannedOrder);
        List<PlannedOrderDetail> list = plannedOrder.getPlannedOrderDetailList();
        if (flag && list != null && list.size() > 0) {
            int iseq = 1;
            for (PlannedOrderDetail item : list) {
                //计算辅单位数量
                Map auxmap = countGoodsInfoNumber(item.getGoodsid(), item.getAuxunitid(), item.getUnitnum());
                if (auxmap.containsKey("auxnum")) {
                    item.setTotalbox((BigDecimal) auxmap.get("auxnum"));
                }
                item.setOrderid(plannedOrder.getId());
                item.setSeq(iseq);
                insertPlannedOrderDetail(item);
                iseq = iseq + 1;
            }
        }
        return flag;

    }

    public PlannedOrder showPlannedOrderAndDetail(String id) throws Exception {
        PlannedOrder pOrder = plannedOrderMapper.getPlannedOrder(id);
        if (null != pOrder) {
            DepartMent departMent = null;
            TaxType taxType = null;
            MeteringUnit meteringUnit = null;

            if (StringUtils.isNotEmpty(pOrder.getApplydeptid())) {
                departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(pOrder.getApplydeptid());
                if (departMent != null) {
                    pOrder.setApplydeptname(departMent.getName());
                }
            }
            List<PlannedOrderDetail> list = showPlannedOrderDetailListByOrderId(pOrder.getId());
            if (null != list && list.size() > 0) {
                pOrder.setPlannedOrderDetailList(list);
            } else {
                pOrder.setPlannedOrderDetailList(new ArrayList<PlannedOrderDetail>());
            }
        }
        return pOrder;
    }

    public boolean updatePlannedOrderAddDetail(PlannedOrder plannedOrder) throws Exception {
        PlannedOrder oldPlannedOrder = plannedOrderMapper.getPlannedOrder(plannedOrder.getId());
        if (null == oldPlannedOrder || "3".equals(oldPlannedOrder.getStatus()) || "4".equals(oldPlannedOrder.getStatus())) {
            return false;
        }

        boolean flag = plannedOrderMapper.updatePlannedOrder(plannedOrder) > 0;
        if (flag) {
            deletePlannedOrderDetailByOrderid(plannedOrder.getId());
            List<PlannedOrderDetail> list = plannedOrder.getPlannedOrderDetailList();
            if (flag && list != null && list.size() > 0) {
                int iseq = 1;
                for (PlannedOrderDetail item : list) {
                    //计算辅单位数量
                    Map auxmap = countGoodsInfoNumber(item.getGoodsid(), item.getAuxunitid(), item.getUnitnum());
                    if (auxmap.containsKey("auxnum")) {
                        item.setTotalbox((BigDecimal) auxmap.get("auxnum"));
                    }
                    item.setOrderid(plannedOrder.getId());
                    item.setSeq(iseq);
                    insertPlannedOrderDetail(item);
                    iseq = iseq + 1;
                }
            }
        }
        return flag;
    }

    public boolean deletePlannedOrderAndDetail(String id) throws Exception {
        PlannedOrder plannedOrder = plannedOrderMapper.getPlannedOrder(id);
        if (null == plannedOrder || "3".equals(plannedOrder.getStatus()) || "4".equals(plannedOrder.getStatus())) {
            return false;
        }
        boolean flag = plannedOrderMapper.deletePlannedOrder(id) > 0;
        if (flag) {
            plannedOrderMapper.deletePlannedOrderDetailByOrderid(id);
        }
        return flag;
    }

    @Override
    public PlannedOrder showPurePlannedOrderAndDetail(String id) throws Exception {
        return showBasePurePlannedOrderAndDetail(id);
    }

    @Override
    public PlannedOrder showPlannedOrder(String id) throws Exception {
        Map map = new HashMap();
        map.put("id", id.trim());
        String cols = getAccessColumnList("t_purchase_plannedorder", null);
        String datasql = getDataAccessRule("t_purchase_plannedorder", null);
        map.put("cols", cols);
        map.put("authDataSql", datasql);
        return plannedOrderMapper.getPlannedOrderBy(map);
    }

    @Override
    public PlannedOrder showPurePlannedOrder(String id) throws Exception {
        return plannedOrderMapper.getPlannedOrder(id);
    }

    @Override
    public PlannedOrder showPlannedOrderByDataAuth(String id) throws Exception {
        Map map = new HashMap();
        map.put("id", id.trim());
        String datasql = getDataAccessRule("t_purchase_plannedorder", null);
        map.put("authDataSql", datasql);
        return plannedOrderMapper.getPlannedOrderBy(map);
    }

    @Override
    public PageData showPlannedOrderPageList(PageMap pageMap) throws Exception {
        String cols = getAccessColumnList("t_purchase_plannedorder", null);
        pageMap.setCols(cols);
        String sql = getDataAccessRule("t_purchase_plannedorder", null);
        pageMap.setDataSql(sql);
        List<PlannedOrder> porderList = plannedOrderMapper.getPlannedOrderPageList(pageMap);
        if (porderList != null && porderList.size() > 0) {
            DepartMent departMent = null;
            Personnel personnel = null;
            BuySupplier buySupplier = null;
            Contacter contacter = null;
            Map map = null;
            for (PlannedOrder item : porderList) {
                if (StringUtils.isNotEmpty(item.getApplydeptid())) {
                    departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(item.getApplydeptid());
                    if (departMent != null) {
                        item.setApplydeptname(departMent.getName());
                    }
                }
                if (StringUtils.isNotEmpty(item.getApplyuserid())) {
                    personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(item.getApplyuserid());
                    if (personnel != null) {
                        item.setApplyusername(personnel.getName());
                    }
                }
//                if(StringUtils.isNotEmpty(item.getBuyuserid()) && StringUtils.isEmpty(item.getBuyusername())){
//                    personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(item.getApplyuserid());
//                    item.setBuyusername(personnel.getName());
//                }
                if (StringUtils.isNotEmpty(item.getBuydeptid())) {
                    departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(item.getBuydeptid());
                    if (departMent != null) {
                        item.setBuydeptname(departMent.getName());
                    }
                }
                if (StringUtils.isNotEmpty(item.getSupplierid())) {
                    buySupplier = getSupplierInfoById(item.getSupplierid());
                    if (null != buySupplier) {
                        item.setSuppliername(buySupplier.getName());
                    }
                }
                if (StringUtils.isNotEmpty(item.getHandlerid())) {
                    map = new HashMap();
                    map.put("id", item.getHandlerid());
                    contacter = getBaseFilesContacterMapper().getContacterDetail(map);
                    if (contacter != null) {
                        item.setHandlername(contacter.getName());
                    }
                }
                Map total = plannedOrderMapper.getPlannedOrderDetailTotal(item.getId());
                if (total != null) {
                    if (total.containsKey("taxamount")) {
                        item.setField01(total.get("taxamount").toString());
                    }
                    if (total.containsKey("notaxamount")) {
                        item.setField02(total.get("notaxamount").toString());
                    }
                    if (total.containsKey("tax")) {
                        item.setField03(total.get("tax").toString());
                    }
                }
            }
        }
        PageData pageData = new PageData(plannedOrderMapper.getPlannedOrderPageCount(pageMap),
                porderList, pageMap);
        Map footerMap=plannedOrderMapper.getPlannedOrderPageSum(pageMap);
        List<Map> footerList=new ArrayList<Map>();
        footerList.add(footerMap);
        pageData.setFooter(footerList);
        return pageData;
    }

    /**
     * 根据参数更新采购计划单<br/>
     * map中的参数：<br/>
     * 更新参数条件：<br/>
     * plannedOrder :采购计划单对象<br/>
     * 条件参数：<br/>
     * id : 编号
     * @param
     * @return
     * @author zhanghonghui
     * @date 2013-5-18
     */
    public boolean updatePlannedOrderBy(Map map) throws Exception {
        String sql = getDataAccessRule("t_purchase_plannedorder", null);
        map.put("authDataSql", sql);
        return plannedOrderMapper.updatePlannedOrderBy(map) > 0;
    }

    /**
     * 审核
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-5-29
     */
    public Map auditPlannedOrder(String id) throws Exception {
        Map map = new HashMap();
        map.put("flag", false);
        PlannedOrder pOrder = showPlannedOrderByDataAuth(id);
        if (null == pOrder) {
            map.put("flag", false);
            return map;
        }
        if (StringUtils.isEmpty(pOrder.getStorageid())) {
            map.put("flag", false);
            map.put("msg", "请选择采购入库仓库");
            return map;
        }
        List<PlannedOrderDetail> list = showPurePlannedOrderDetailListByOrderId(id);
        if (null == list || list.size() == 0) {
            map.put("flag", false);
            map.put("msg", "请添加采购计划单明细信息");
            return map;
        }
        pOrder.setPlannedOrderDetailList(list);
        boolean flag = false;
        String nextbillno = "";
        SysUser sysUser = getSysUser();
        Date today = new Date();
        if ("2".equals(pOrder.getStatus())) {
            PlannedOrder plannedOrder = new PlannedOrder();
            plannedOrder.setId(id);
            plannedOrder.setStatus("3");
            plannedOrder.setAudituserid(sysUser.getUserid());
            plannedOrder.setAuditusername(sysUser.getName());
            plannedOrder.setAudittime(today);
            plannedOrder.setBusinessdate(getAuditBusinessdate(pOrder.getBusinessdate()));
            flag = updatePlannedOrderStatus(plannedOrder);
        }
        if (flag) {
            nextbillno = purchaseSelfExtService.addCreateBuyOrderByPlanAudit(pOrder);
            updatePlannedOrderRefer(id, "1");
        }
        map.clear();
        map.put("flag", flag);
        map.put("billid", nextbillno);
        return map;
    }

    /**
     * 反审核
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-5-29
     */
    public Map oppauditPlannedOrder(String id) throws Exception {
        Map map = new HashMap();
        Map queryMap = new HashMap();
        PlannedOrder pOrder = showPlannedOrderByDataAuth(id);
        if (null == pOrder) {
            map.put("flag", false);
            return map;
        }

        boolean flag = false;
        SysUser sysUser = getSysUser();
        if ("3".equals(pOrder.getStatus())) {
            flag = purchaseSelfExtService.deleteBuyOrderAndDetailByBillno(pOrder.getId());
            if (flag) {
                PlannedOrder plannedOrder = new PlannedOrder();
                plannedOrder.setId(id);
                plannedOrder.setStatus("2");
                plannedOrder.setAudituserid(sysUser.getUserid());
                plannedOrder.setAuditusername(sysUser.getName());
                plannedOrder.setAudittime(new Date());
                updatePlannedOrderStatus(plannedOrder);
                if (flag) {
                    updatePlannedOrderRefer(id, "0");
                }
            }
        }
        map.put("flag", flag);
        return map;
    }

    @Override
    public boolean auditWorkflowPlannedOrder(String id) throws Exception {
        PlannedOrder pOrder = showPlannedOrderAndDetail(id);
        List<PlannedOrderDetail> list = showPurePlannedOrderDetailListByOrderId(id);
        pOrder.setPlannedOrderDetailList(list);
        boolean flag = false;
        String nextbillno = "";
        SysUser sysUser = getSysUser();
        Date today = new Date();
        if ("3".equals(pOrder.getStatus())) {
            PlannedOrder plannedOrder = new PlannedOrder();
            plannedOrder.setId(id);
            plannedOrder.setStatus("3");
            plannedOrder.setAudituserid(sysUser.getUserid());
            plannedOrder.setAuditusername(sysUser.getName());
            plannedOrder.setAudittime(today);
            flag = updatePlannedOrderStatus(plannedOrder);
        }
        if (flag) {
            pOrder.setBusinessdate(CommonUtils.dataToStr(today, "yyyy-MM-dd"));
            nextbillno = purchaseSelfExtService.addCreateBuyOrderByPlanAudit(pOrder);
            updatePlannedOrderRefer(id, "1");
        }
        return flag;
    }


    /**
     * 设置采购计划单引用 标志
     * @param id
     * @param isrefer 1表示引用，0表示未引用
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-5-29
     */
    public boolean updatePlannedOrderRefer(String id, String isrefer) throws Exception {
        if (null == id || "".equals(id.trim())) {
            return false;
        }
        boolean flag = false;
        PlannedOrder pOrder = new PlannedOrder();
        pOrder.setIsrefer(isrefer);
        pOrder.setId(id);
        flag = plannedOrderMapper.updatePlannedOrderStatus(pOrder) > 0;
        return flag;
    }

    public boolean updatePlannedOrderStatus(PlannedOrder plannedOrder) throws Exception {
        return plannedOrderMapper.updatePlannedOrderStatus(plannedOrder) > 0;
    }

    //------------------------------------------------------------------------//
    //采购计划单详细
    //-----------------------------------------------------------------------//

    public boolean insertPlannedOrderDetail(PlannedOrderDetail plannedOrderDetail) throws Exception {
        return plannedOrderMapper.insertPlannedOrderDetail(plannedOrderDetail) > 0;
    }

    public List showPlannedOrderDetailListByOrderId(String orderid) throws Exception {
        if (null == orderid || "".equals(orderid)) {
            return new ArrayList<PlannedOrderDetail>();
        }
        orderid = orderid.trim();
        Map map = new HashMap();
        map.put("orderid", orderid);
        String cols = getAccessColumnList("t_purchase_plannedorder_detail", null);
        map.put("cols", cols);
        String sql = getDataAccessRule("t_purchase_plannedorder_detail", null);
        map.put("authDataSql", sql);
        List<PlannedOrderDetail> list = plannedOrderMapper.getPlannedOrderDetailListBy(map);
        if (null != list && list.size() > 0) {
            for (PlannedOrderDetail item : list) {
                GoodsInfo goodsInfo = getGoodsInfoByID(item.getGoodsid());
                item.setGoodsInfo(goodsInfo);
                if (StringUtils.isNotEmpty(item.getTaxtype())) {
                    TaxType taxType = getTaxType(item.getTaxtype());
                    if (null != taxType) {
                        item.setTaxtypename(taxType.getName());
                    }
                }
                if (StringUtils.isNotEmpty(item.getAuxunitid())) {
                    MeteringUnit meteringUnit = getMeteringUnitById(item.getAuxunitid());
                    if (null != meteringUnit) {
                        item.setAuxunitname(meteringUnit.getName());
                    }
                }
                if (null != goodsInfo) {
                    BigDecimal boxprice = goodsInfo.getBoxnum().multiply(item.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);
                    BigDecimal noboxprice = goodsInfo.getBoxnum().multiply(item.getNotaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);
                    item.setBoxprice(boxprice);
                    item.setNoboxprice(noboxprice);
                }
            }
        }
        return list;
    }

    public List showPurePlannedOrderDetailListByOrderId(String orderid) throws Exception {
        orderid = orderid.trim();
        Map map = new HashMap();
        map.put("orderid", orderid);
        return plannedOrderMapper.getPlannedOrderDetailListBy(map);
    }

    public boolean deletePlannedOrderDetailByOrderid(String orderid) throws Exception {
        return plannedOrderMapper.deletePlannedOrderDetailByOrderid(orderid) > 0;
    }

    @Override
    public boolean updatePlannedOrderDetail(PlannedOrderDetail plannedOrderDetail) throws Exception {
        return plannedOrderMapper.updatePlannedOrderDetail(plannedOrderDetail) > 0;
    }

    @Override
    public boolean updatePlannedOrderDetailWriteBack(PlannedOrderDetail plannedOrderDetail) throws Exception {
        return plannedOrderMapper.updatePlannedOrderDetailWriteBack(plannedOrderDetail) > 0;

    }

    @Override
    public PlannedOrderDetail showPurePlannedOrderDetail(String id) throws Exception {
        return showBasePurePlannedOrderDetail(id);
    }

    @Override
    public boolean submitPlannedOrderProcess(String title, String userId, String processDefinitionKey, String businessKey, Map<String, Object> variables) throws Exception {
        return false;
    }

    @Override
    public Map addPlannedOrderByReport(List<StorageBuySaleReport> list, String remark, String queryForm)
            throws Exception {
        Map returnMap = new HashMap();
        Map supplierMap = new HashMap();
        String storageid = "";
        for (StorageBuySaleReport storageBuySaleReport : list) {
            storageid = storageBuySaleReport.getStorageid();
            String supplierid = "";
            //有第二供应商项目，先判断传入的供应商是否有
            if (StringUtils.isNotEmpty(storageBuySaleReport.getSupplierid())) {
                supplierid = storageBuySaleReport.getSupplierid();
            } else {
                //没有第二供应商项目的，代码
                GoodsInfo goodsInfo = getAllGoodsInfoByID(storageBuySaleReport.getGoodsid());
                supplierid = goodsInfo.getDefaultsupplier();
                if (null == supplierid || "".equals(supplierid)) {
                    supplierid = "";
                }
                //没有第二供应商项目的，代码
            }
            if (supplierMap.containsKey(supplierid)) {
                List detailList = (List) supplierMap.get(supplierid);
                detailList.add(storageBuySaleReport);
                supplierMap.put(supplierid, detailList);
            } else {
                List detailList = new ArrayList();
                detailList.add(storageBuySaleReport);
                supplierMap.put(supplierid, detailList);
            }
        }
        Set set = supplierMap.entrySet();
        Iterator it = set.iterator();
        boolean flag = false;
        int successNum = 0;
        String repeatVal = "";
        String orderid = "";
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Entry<String, String>) it.next();
            String supplierid = entry.getKey();
            List<StorageBuySaleReport> detailList = (List<StorageBuySaleReport>) supplierMap.get(supplierid);
            Map planorderMap = changePlanOrderBySupplierid(supplierid, detailList);
            if (null != planorderMap && planorderMap.containsKey("plannedOrder")) {
                PlannedOrder plannedOrder = (PlannedOrder) planorderMap.get("plannedOrder");
                //当前用户有默认仓库则选择用户对应的默认仓库
                String OpenDeptStorage = getSysParamValue("OpenDeptStorage");
                if ("1".equals(OpenDeptStorage)) {
                    if (null != storageid && storageid != "") {
                        plannedOrder.setStorageid(storageid);
                    }
                }
                plannedOrder.setRemark(remark);
                plannedOrder.setField08(queryForm);
                boolean addflag = false;
                if (plannedOrder.getPlannedOrderDetailList().size() > 0) {
                    addflag = addPlannedOrderAddDetail(plannedOrder);
                }
                if (addflag) {
                    successNum += plannedOrder.getPlannedOrderDetailList().size();
                    if ("".equals(orderid)) {
                        orderid = plannedOrder.getId();
                    } else {
                        orderid += "," + plannedOrder.getId();
                    }
                }
            }

            String goodsid = (String) planorderMap.get("goodsid");
            if (null != goodsid && !"".equals(goodsid)) {
                if ("".equals(repeatVal)) {
                    repeatVal = "商品：" + goodsid;
                } else {
                    repeatVal += goodsid;
                }
            }
        }
        if (!"".equals(orderid)) {
            flag = true;
        } else {
            flag = false;
        }
        if (!"".equals(repeatVal)) {
            repeatVal += "生成失败，商品采购数量为0或者该商品未指定供应商";
        }
        int failureNum = list.size() - successNum;
        returnMap.put("flag", flag);
        returnMap.put("successNum", successNum);
        returnMap.put("failureNum", failureNum);
        returnMap.put("errormsg", repeatVal);
        returnMap.put("orderid", orderid);
        returnMap.put("msg", "生成采购计划单：" + orderid);
        return returnMap;
    }

    /**
     * 进销存报表数据转采购计划单
     * @param supplierid
     * @param detailList
     * @return
     * @throws Exception
     * @author chenwei
     * @date Sep 28, 2013
     */
    private Map changePlanOrderBySupplierid(String supplierid, List<StorageBuySaleReport> detailList) throws Exception {
        SysUser sysUser = getSysUser();
        Map returnMap = new HashMap();
        BuySupplier buySupplier = getSupplierInfoById(supplierid);
        String goodsids = "";
        if (null != buySupplier) {
            PlannedOrder plannedOrder = new PlannedOrder();
            /*
			if (isAutoCreate("t_purchase_plannedorder")) {
				// 获取自动编号
				plannedOrder.setId( getAutoCreateSysNumbderForeign(plannedOrder, "t_purchase_plannedorder"));
			}else{
		    	plannedOrder.setId("ST"+CommonUtils.getDataNumberSendsWithRand());
			}
			*/
            //临时编号
            plannedOrder.setId("ST" + CommonUtils.getDataNumberSendsWithRand());
            plannedOrder.setBusinessdate(CommonUtils.getTodayDataStr());
            plannedOrder.setStatus("2");
            plannedOrder.setSupplierid(supplierid);
            plannedOrder.setStorageid(buySupplier.getStorageid());
            plannedOrder.setHandlerid(buySupplier.getContact());
            plannedOrder.setBuydeptid(buySupplier.getBuydeptid());
            plannedOrder.setBuyuserid(buySupplier.getBuyuserid());
            plannedOrder.setApplydeptid(sysUser.getDepartmentid());
            plannedOrder.setApplyuserid(sysUser.getPersonnelid());
            plannedOrder.setAdduserid(sysUser.getUserid());
            plannedOrder.setAddusername(sysUser.getName());
            plannedOrder.setAdddeptid(sysUser.getDepartmentid());
            plannedOrder.setAdddeptname(sysUser.getDepartmentname());
            plannedOrder.setAddtime(new Date());

            //取系统参数中的要求到货日期
            String arrivedateDays = getSysParamValue("ARRIVEDAYS");
            int days = 2;
            if (null != arrivedateDays && !"".equals(arrivedateDays)) {
                days = new Integer(arrivedateDays);
            }
            Calendar calendar = Calendar.getInstance();
            calendar.add(calendar.DATE, days);
            String arrivedate = CommonUtils.dataToStr(calendar.getTime(), "yyyy-MM-dd");

            plannedOrder.setArrivedate(arrivedate);

            List pdetailList = new ArrayList();
            /**
             * 有采购价：采购价取最高采购价还是最新采购价
             */
            String purchasePriceType = getSysParamValue("PurchasePriceType");
            if (null == purchasePriceType || "".equals(purchasePriceType.trim())) {
                purchasePriceType = "1";
            }
            purchasePriceType = purchasePriceType.trim();
            /**
             * 有采购价：采购价取最高采购价还是最新采购价
             */

            for (StorageBuySaleReport storageBuySaleReport : detailList) {
                PlannedOrderDetail plannedOrderDetail = new PlannedOrderDetail();
                plannedOrderDetail.setOrderid(plannedOrder.getId());
                plannedOrderDetail.setGoodsid(storageBuySaleReport.getGoodsid());
                GoodsInfo goodsInfo = getAllGoodsInfoByID(storageBuySaleReport.getGoodsid());
                if (null != goodsInfo
                        && null != goodsInfo.getBoxnum()
                        && BigDecimal.ZERO.compareTo(goodsInfo.getBoxnum()) != 0
                        && null != storageBuySaleReport.getBuyinnum()
                        && BigDecimal.ZERO.compareTo(storageBuySaleReport.getBuyinnum()) == -1) {
                    plannedOrderDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
                    plannedOrderDetail.setUnitid(goodsInfo.getMainunit());
                    plannedOrderDetail.setUnitname(goodsInfo.getMainunitName());

                    plannedOrderDetail.setArrivedate(arrivedate);

                    BigDecimal unitnum = storageBuySaleReport.getBuyinnum();
                    plannedOrderDetail.setUnitnum(unitnum);

                    //辅单位转主单位
                    Map changeResultMap = countGoodsInfoNumber(goodsInfo.getId(), storageBuySaleReport.getAuxunitid(), unitnum);
                    BigDecimal auxunitnum = BigDecimal.ZERO;
                    if (changeResultMap.containsKey("auxInteger")) {
                        String auxIntegerStr = (String) changeResultMap.get("auxInteger");
                        if (null != auxIntegerStr && StringUtils.isNumeric(auxIntegerStr.trim())) {
                            auxunitnum = new BigDecimal(auxIntegerStr.trim());
                        }
                    }
                    plannedOrderDetail.setAuxnum(auxunitnum);

                    plannedOrderDetail.setAuxunitid(storageBuySaleReport.getAuxunitid());
                    String auxunitname = (String) changeResultMap.get("auxunitname");
                    if (null != auxunitname && !"".equals(auxunitname.trim())) {
                        plannedOrderDetail.setAuxunitname(auxunitname);
                    } else {
                        plannedOrderDetail.setAuxunitname(storageBuySaleReport.getAuxunitname());
                    }
                    BigDecimal auxremainder = BigDecimal.ZERO;
                    if (changeResultMap.containsKey("auxremainder")) {
                        String auxremainderStr = (String) changeResultMap.get("auxremainder");
                        if (StringUtils.isNotEmpty(auxremainderStr)) {
                            auxremainder = new BigDecimal(auxremainderStr.trim());
                        }
                    }
                    plannedOrderDetail.setAuxremainder(auxremainder);
                    if (changeResultMap.containsKey("auxnum")) {
                        plannedOrderDetail.setTotalbox((BigDecimal) changeResultMap.get("auxnum"));
                    }
                    String auxnumdetail = (String) changeResultMap.get("auxnumdetail");
                    plannedOrderDetail.setAuxnumdetail(auxnumdetail);

                    /**
                     * 有采购价：采购价取最高采购价还是最新采购价
                     */

                    if ("1".equals(purchasePriceType)) {
                        plannedOrderDetail.setTaxprice(goodsInfo.getHighestbuyprice());
                    } else if ("2".equals(purchasePriceType)) {
                        plannedOrderDetail.setTaxprice(goodsInfo.getNewbuyprice());
                    } else {
                        plannedOrderDetail.setTaxprice(goodsInfo.getHighestbuyprice());
                    }
                    /**
                     * 有采购价：采购价取最高采购价还是最新采购价
                     */

                    plannedOrderDetail.setTaxamount(plannedOrderDetail.getUnitnum().multiply(plannedOrderDetail.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));

                    BigDecimal notaxamount = getNotaxAmountByTaxAmount(plannedOrderDetail.getTaxamount(),plannedOrderDetail.getTaxtype());
                    if (plannedOrderDetail.getUnitnum().compareTo(BigDecimal.ZERO) != 0) {
                        BigDecimal notaxprice = notaxamount.divide(plannedOrderDetail.getUnitnum(), 6, BigDecimal.ROUND_HALF_UP);
                        plannedOrderDetail.setNotaxprice(notaxprice);
                        plannedOrderDetail.setNotaxamount(notaxamount);
                        plannedOrderDetail.setTax(plannedOrderDetail.getTaxamount().subtract(notaxamount));
                    }
                    pdetailList.add(plannedOrderDetail);
                } else {
                    goodsids += storageBuySaleReport.getGoodsid() + ",";
                }
            }
            plannedOrder.setPlannedOrderDetailList(pdetailList);
            returnMap.put("plannedOrder", plannedOrder);
        } else {
            for (StorageBuySaleReport storageBuySaleReport : detailList) {
                goodsids += storageBuySaleReport.getGoodsid() + ",";
            }
        }
        returnMap.put("goodsid", goodsids);
        return returnMap;
    }

    @Override
    public Map importPlannedOrder(List<Map<String, Object>> list)
            throws Exception {
        boolean emptySupplier = false;
        boolean emptyGoods = false;
        String nullgoodsids = "";
        String disabledgoodsids = "";
        String nullspells = "";
        String nullbarcode = "";
        String failorders = "";
        String errormsg = "";
        String drerrormsg = "";
        int emptySupplierNum = 0;
        int errorSupplierNum = 0;
        int emptyGoodsNum = 0;
        //取系统参数中的要求到货日期
        String arrivedateDays = getSysParamValue("ARRIVEDAYS");
        int days = 2;
        if (null != arrivedateDays && !"".equals(arrivedateDays)) {
            days = new Integer(arrivedateDays);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.DATE, days);

        //允许输入小数时，保留几位小数
        int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;

        //根据供应商编码分割单据
        Map<String, List<PlannedOrderDetail>> groupMap = new HashMap<String, List<PlannedOrderDetail>>();

        /**
         * 有采购价：采购价取最高采购价还是最新采购价
         */

        String purchasePriceType = getSysParamValue("PurchasePriceType");
        if (null == purchasePriceType || "".equals(purchasePriceType.trim())) {
            purchasePriceType = "1";
        }
        purchasePriceType = purchasePriceType.trim();

        for (Map<String, Object> map : list) {
            String supplierid = (null != map.get("supplierid")) ? (String) map.get("supplierid") : "";
            String businessdate = (null != map.get("businessdate")) ? (String) map.get("businessdate") : "null";
            supplierid = supplierid.trim();
            businessdate = businessdate.trim();
            String key = supplierid+","+businessdate;
            if (StringUtils.isNotEmpty(supplierid)) {
                BuySupplier buySupplier = getSupplierInfoById(supplierid);
                if (null == buySupplier) {
                    emptySupplier = true;
                    errorSupplierNum = errorSupplierNum + 1;
                    continue;
                }
                String goodsid = (null != map.get("goodsid")) ? (String) map.get("goodsid") : "";
                String spell = (null != map.get("spell")) ? (String) map.get("spell") : "";
                String barcode = (null != map.get("barcode")) ? (String) map.get("barcode") : "";
                String remark = (null != map.get("remark")) ? (String) map.get("remark") : "";

                //根据已知商品编码or助记符or条形码获取商品档案信息
                GoodsInfo goodsInfoQ = null;
                if (StringUtils.isNotEmpty(goodsid)) {
                    goodsInfoQ = getBaseGoodsMapper().getGoodsInfo(goodsid);
                } else if (StringUtils.isNotEmpty(spell)) {
                    goodsInfoQ = getGoodsInfoBySpell(spell);
                } else if (StringUtils.isNotEmpty(barcode)) {
                    goodsInfoQ = getGoodsInfoByBarcode(barcode);
                }
                GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(goodsInfoQ);
                if (null != goodsInfo) {
                    if ("0".equals(goodsInfo.getState())) {
                        if (StringUtils.isEmpty(disabledgoodsids)) {
                            disabledgoodsids = goodsInfo.getId();
                        } else {
                            disabledgoodsids = disabledgoodsids + "," + goodsInfo.getId();
                        }
                        continue;
                    }
                    MeteringUnit meteringUnit = getGoodsDefaulAuxunit(goodsInfo.getId());
                    if (null != meteringUnit) {
                        goodsInfo.setAuxunitid(meteringUnit.getId());
                    }
                    try {
                        BigDecimal auxnum = (null != map.get("auxnum")) ? new BigDecimal((String) map.get("auxnum")) : BigDecimal.ZERO;//辅数量整数
                        BigDecimal auxremainder = (null != map.get("auxremainder")) ? new BigDecimal((String) map.get("auxremainder")) : BigDecimal.ZERO;//辅单位余数数量
                        if (decimalScale == 0) {
                            auxnum = auxnum.setScale(decimalScale, BigDecimal.ROUND_DOWN);
                            auxremainder = auxremainder.setScale(decimalScale, BigDecimal.ROUND_DOWN);
                        } else {
                            auxnum = auxnum.setScale(decimalScale, BigDecimal.ROUND_HALF_UP);
                            auxremainder = auxremainder.setScale(decimalScale, BigDecimal.ROUND_HALF_UP);
                        }
                        if (groupMap.isEmpty() || !groupMap.containsKey(key)) {
                            List<PlannedOrderDetail> detaillist = new ArrayList<PlannedOrderDetail>();

                            PlannedOrderDetail plannedOrderDetail = new PlannedOrderDetail();
                            plannedOrderDetail.setGoodsid(goodsInfo.getId());
                            plannedOrderDetail.setGoodsInfo(goodsInfo);
                            plannedOrderDetail.setAuxunitid(goodsInfo.getAuxunitid());
                            if (null != meteringUnit) {
                                plannedOrderDetail.setAuxunitname(meteringUnit.getName());
                            }
                            plannedOrderDetail.setUnitid(goodsInfo.getMainunit());
                            MeteringUnit mainunit = getMeteringUnitById(goodsInfo.getMainunit());
                            if (null != mainunit) {
                                plannedOrderDetail.setUnitname(mainunit.getName());
                            }
                            //根据辅单位数量计算主单位，主单位=返回的主单位数量（mainUnitNum）+ 辅单位余数数量（auxremainder）
                            Map map2 = retMainUnitByUnitAndGoodid(auxnum, goodsInfo.getId());
                            BigDecimal mainUnitNum = (null != map2.get("mainUnitNum")) ? (BigDecimal) map2.get("mainUnitNum") : BigDecimal.ZERO;
                            BigDecimal unitnum = mainUnitNum.add(auxremainder);
                            plannedOrderDetail.setUnitnum(unitnum);



                            //根据商品编码，辅单位编码，主数量获取辅单位数量，辅单位数量描述=返回的辅单位数量描述+余数+住单位名称,辅单位名称,单位名称,余数
                            Map map3 = countGoodsInfoNumber(goodsInfo.getId(), goodsInfo.getAuxunitid(), unitnum);
                            BigDecimal retauxremainder = (null != map3.get("auxremainder")) ? new BigDecimal((String) map3.get("auxremainder")) : BigDecimal.ZERO;
                            String auxnumdetail = ((null != map3.get("auxnumdetail")) ? (String) map3.get("auxnumdetail") : "");
                            BigDecimal retauxnum = (null != map3.get("auxnum")) ? (BigDecimal) map3.get("auxnum") : BigDecimal.ZERO;
                            plannedOrderDetail.setAuxnum(retauxnum);
                            plannedOrderDetail.setAuxnumdetail(auxnumdetail);
                            plannedOrderDetail.setAuxremainder(retauxremainder);
                            plannedOrderDetail.setTotalbox((BigDecimal) map3.get("auxnum"));

                            //根据商品档案取默认税种
                            plannedOrderDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
                            //获取采购价
                            BigDecimal taxprice = BigDecimal.ZERO;
                            if(map.containsKey("taxprice")){
                                taxprice = new BigDecimal((String) map.get("taxprice"));
                            }
                            BigDecimal taxamount = BigDecimal.ZERO;
                            if(map.containsKey("taxamount") && unitnum.compareTo(BigDecimal.ZERO)!=0){
                                taxamount = new BigDecimal((String) map.get("taxamount"));
                                taxprice = taxamount.divide(unitnum,6,BigDecimal.ROUND_HALF_UP);
                            }
                            if(!map.containsKey("taxprice")){
                                //有采购价：采购价取最高采购价还是最新采购价
                                if ("1".equals(purchasePriceType)) {
                                    plannedOrderDetail.setTaxprice(goodsInfo.getHighestbuyprice());
                                } else if ("2".equals(purchasePriceType)) {
                                    plannedOrderDetail.setTaxprice(goodsInfo.getNewbuyprice());
                                } else {
                                    plannedOrderDetail.setTaxprice(goodsInfo.getHighestbuyprice());
                                }
                            }else{
                                 plannedOrderDetail.setTaxprice(taxprice);
                            }
                            //根据税种，主数量，含税单价计算相关金额
                            Map map4 = getAmountDetailByTaxWithUnitnum(plannedOrderDetail.getTaxprice(), plannedOrderDetail.getTaxtype(), unitnum);
                            plannedOrderDetail.setNotaxprice((BigDecimal) map4.get("notaxprice"));
                            plannedOrderDetail.setNotaxamount(((BigDecimal) map4.get("notaxamount")));
                            plannedOrderDetail.setTaxamount(((BigDecimal) map4.get("taxamount")));
                            plannedOrderDetail.setTax(((BigDecimal) map4.get("tax")));

                            //要求到货日期
                            plannedOrderDetail.setArrivedate(CommonUtils.dataToStr(calendar.getTime(), "yyyy-MM-dd"));

                            //备注
                            if (StringUtils.isNotEmpty(remark)) {
                                plannedOrderDetail.setRemark(remark);
                            }

                            detaillist.add(plannedOrderDetail);
                            groupMap.put(key, detaillist);
                        } else {
                            List<PlannedOrderDetail> detaillist = (List<PlannedOrderDetail>) groupMap.get(key);

                            PlannedOrderDetail plannedOrderDetail = new PlannedOrderDetail();
                            plannedOrderDetail.setGoodsid(goodsInfo.getId());
                            plannedOrderDetail.setGoodsInfo(goodsInfo);
                            plannedOrderDetail.setAuxunitid(goodsInfo.getAuxunitid());
                            if (null != meteringUnit) {
                                plannedOrderDetail.setAuxunitname(meteringUnit.getName());
                            }
                            plannedOrderDetail.setUnitid(goodsInfo.getMainunit());
                            MeteringUnit mainunit = getMeteringUnitById(goodsInfo.getMainunit());
                            if (null != mainunit) {
                                plannedOrderDetail.setUnitname(mainunit.getName());
                            }
                            //根据辅单位数量计算主单位，主单位=返回的主单位数量（mainUnitNum）+ 辅单位余数数量（auxremainder）
                            Map map2 = retMainUnitByUnitAndGoodid(auxnum, goodsInfo.getId());
                            BigDecimal mainUnitNum = (null != map2.get("mainUnitNum")) ? (BigDecimal) map2.get("mainUnitNum") : BigDecimal.ZERO;
                            BigDecimal unitnum = mainUnitNum.add(auxremainder);
                            plannedOrderDetail.setUnitnum(unitnum);
                            //根据商品编码，辅单位编码，主数量获取辅单位数量，辅单位数量描述=返回的辅单位数量描述+余数+住单位名称,辅单位名称,单位名称,余数
                            Map map3 = countGoodsInfoNumber(goodsInfo.getId(), goodsInfo.getAuxunitid(), unitnum);
                            BigDecimal retauxremainder = (null != map3.get("auxremainder")) ? new BigDecimal((String) map3.get("auxremainder")) : BigDecimal.ZERO;
                            String auxnumdetail = ((null != map3.get("auxnumdetail")) ? (String) map3.get("auxnumdetail") : "");
                            BigDecimal retauxnum = (null != map3.get("auxnum")) ? (BigDecimal) map3.get("auxnum") : BigDecimal.ZERO;
                            plannedOrderDetail.setAuxnum(retauxnum);
                            plannedOrderDetail.setAuxnumdetail(auxnumdetail);
                            plannedOrderDetail.setAuxremainder(retauxremainder);
                            plannedOrderDetail.setTotalbox((BigDecimal) map3.get("auxnum"));
                            //根据商品档案取默认税种
                            plannedOrderDetail.setTaxtype(goodsInfo.getDefaulttaxtype());

                           //获取采购价
                            BigDecimal taxprice = BigDecimal.ZERO;
                            if(map.containsKey("taxprice")){
                                taxprice = new BigDecimal((String) map.get("taxprice"));
                            }
                            BigDecimal taxamount = BigDecimal.ZERO;
                            if(map.containsKey("taxamount") && unitnum.compareTo(BigDecimal.ZERO)!=0){
                                taxamount = new BigDecimal((String) map.get("taxamount"));
                                taxprice = taxamount.divide(unitnum,6,BigDecimal.ROUND_HALF_UP);
                            }
                            if(!map.containsKey("taxprice")){
                                //有采购价：采购价取最高采购价还是最新采购价
                                if ("1".equals(purchasePriceType)) {
                                    plannedOrderDetail.setTaxprice(goodsInfo.getHighestbuyprice());
                                } else if ("2".equals(purchasePriceType)) {
                                    plannedOrderDetail.setTaxprice(goodsInfo.getNewbuyprice());
                                } else {
                                    plannedOrderDetail.setTaxprice(goodsInfo.getHighestbuyprice());
                                }
                            }else{
                                 plannedOrderDetail.setTaxprice(taxprice);
                            }

                            //根据税种，主数量，含税单价计算相关金额
                            Map map4 = getAmountDetailByTaxWithUnitnum(plannedOrderDetail.getTaxprice(), plannedOrderDetail.getTaxtype(), unitnum);
                            plannedOrderDetail.setNotaxprice((BigDecimal) map4.get("notaxprice"));
                            plannedOrderDetail.setNotaxamount(((BigDecimal) map4.get("notaxamount")));
                            plannedOrderDetail.setTaxamount(((BigDecimal) map4.get("taxamount")));
                            plannedOrderDetail.setTax(((BigDecimal) map4.get("tax")));

                            //要求到货日期
                            plannedOrderDetail.setArrivedate(CommonUtils.dataToStr(calendar.getTime(), "yyyy-MM-dd"));

                            //备注
                            if (StringUtils.isNotEmpty(remark)) {
                                plannedOrderDetail.setRemark(remark);
                            }

                            detaillist.add(plannedOrderDetail);
                            groupMap.put(key, detaillist);
                        }
                    } catch (Exception e) {
                        String msg = "";
                        if (StringUtils.isNotEmpty(goodsid)) {
                            msg = "商品编码为：" + goodsid;
                        } else if (StringUtils.isNotEmpty(spell)) {
                            msg = "助记符为：" + spell;
                        } else if (StringUtils.isNotEmpty(barcode)) {
                            msg = "条形码为：" + barcode;
                        }
                        if (StringUtils.isEmpty(errormsg)) {
                            errormsg = "供应商编码为：" + supplierid + msg + "出错;";
                        } else {
                            errormsg += "<br>" + "供应商编码为：" + supplierid + msg + "出错;";
                        }
                    }
                } else {
                    if (StringUtils.isNotEmpty(goodsid)) {
                        if (StringUtils.isNotEmpty(nullgoodsids)) {
                            nullgoodsids += "," + goodsid;
                        } else {
                            nullgoodsids = goodsid;
                        }
                    } else if (StringUtils.isNotEmpty(spell)) {
                        if (StringUtils.isNotEmpty(nullspells)) {
                            nullspells += "," + spell;
                        } else {
                            nullspells = spell;
                        }
                    } else if (StringUtils.isNotEmpty(barcode)) {
                        if (StringUtils.isNotEmpty(nullbarcode)) {
                            nullbarcode += "," + barcode;
                        } else {
                            nullbarcode = barcode;
                        }
                    } else {
                        emptyGoods = true;
                        emptyGoodsNum++;
                    }
                }
            } else {
                emptySupplier = true;
                emptySupplierNum++;
            }
        }
        //执行导入操作，即新增采购计划单
        if (!groupMap.isEmpty()) {
            String msg = "", failmsg = "";
            for (Map.Entry<String, List<PlannedOrderDetail>> entry : groupMap.entrySet()) {
                String key = entry.getKey();
                String[] arr = key.split(",");
                String supplierid = arr[0];
                String businessdate =  CommonUtils.dataToStr(new Date(), "yyyy-MM-dd");
                if(!"null".equals(arr[1])){
                    businessdate = key.split(",")[1];
                }
                List<PlannedOrderDetail> detailList = entry.getValue();
                try {
                    PlannedOrder plannedOrder = new PlannedOrder();
                    plannedOrder.setStatus("2");
                    SysUser sysUser = getSysUser();
                    plannedOrder.setAdduserid(sysUser.getUserid());
                    plannedOrder.setAddusername(sysUser.getName());
                    plannedOrder.setAdddeptid(sysUser.getDepartmentid());
                    plannedOrder.setAdddeptname(sysUser.getDepartmentname());
                    plannedOrder.setAddtime(new Date());
                    plannedOrder.setApplyuserid(sysUser.getPersonnelid());
                    plannedOrder.setApplydeptid(sysUser.getDepartmentid());
                    plannedOrder.setBusinessdate(businessdate);
                    BuySupplier buySupplier = getSupplierInfoById(supplierid);
                    if (null != buySupplier) {
                        plannedOrder.setSupplierid(buySupplier.getId());
                        plannedOrder.setHandlerid(buySupplier.getContact());
                        plannedOrder.setBuydeptid(buySupplier.getBuydeptid());
                        plannedOrder.setBuyuserid(buySupplier.getBuyuserid());
                        plannedOrder.setStorageid(buySupplier.getStorageid());
                        plannedOrder.setOrderappend(buySupplier.getOrderappend());
                    }
                    //要求到货日期
                    plannedOrder.setArrivedate(CommonUtils.dataToStr(calendar.getTime(), "yyyy-MM-dd"));
                    plannedOrder.setRemark("导入生成");
                    plannedOrder.setPlannedOrderDetailList(detailList);

                    //添加采购计划单
                    boolean flag = addPlannedOrderAddDetail(plannedOrder);
                    if (!flag) {
                        if (StringUtils.isEmpty(failmsg)) {
                            failmsg = supplierid;
                        } else {
                            failmsg += "," + supplierid;
                        }
                    }
                } catch (Exception e) {
                    if (StringUtils.isEmpty(msg)) {
                        msg = supplierid;
                    } else {
                        msg += "," + supplierid;
                    }
                }

            }
            if (StringUtils.isNotEmpty(msg)) {
                drerrormsg = "供应商编码为：" + msg + "的采购计划单的导入出错!";
            }
            if (StringUtils.isNotEmpty(failmsg)) {
                failorders = "供应商编码为：" + failmsg + "的采购计划单导入失败!";
            }
        }
        String msg2 = "";
        if (emptySupplier) {
            if (StringUtils.isEmpty(msg2)) {
                msg2 = "有" + emptySupplierNum + "条商品没有填写供应商!";
            } else {
                msg2 += "<br>" + "有" + emptySupplierNum + "条商品没有填写供应商!";
            }
            if (StringUtils.isEmpty(msg2)) {
                msg2 = "有" + errorSupplierNum + "条商品供应商不存在!";
            } else {
                msg2 += "<br>" + "有" + errorSupplierNum + "条商品供应商不存在!";
            }
        }
        if (emptyGoods) {
            if (StringUtils.isEmpty(msg2)) {
                msg2 = "有" + emptyGoodsNum + "条商品全部没有填写商品编码、助记符、条形码，请补充任意一个!";
            } else {
                msg2 += "<br>" + "有" + emptyGoodsNum + "条商品全部没有填写商品编码、助记符、条形码，请补充任意一个!";
            }
        }
        if (StringUtils.isNotEmpty(disabledgoodsids)) {
            if (StringUtils.isEmpty(msg2)) {
                msg2 = "商品编码：" + disabledgoodsids + "的商品为禁用商品!";
            } else {
                msg2 += "<br>" + "商品编码：" + disabledgoodsids + "的商品为禁用商品!";
            }
        }
        if (StringUtils.isNotEmpty(nullgoodsids)) {
            if (StringUtils.isEmpty(msg2)) {
                msg2 = "商品档案中不存在商品编码为：" + nullgoodsids + "的商品!";
            } else {
                msg2 += "<br>" + "商品档案中不存在商品编码为：" + nullgoodsids + "的商品!";
            }
        }
        if (StringUtils.isNotEmpty(nullspells)) {
            if (StringUtils.isEmpty(msg2)) {
                msg2 = "商品档案中不存在助记符为：" + nullspells + "的商品!";
            } else {
                msg2 += "<br>" + "商品档案中不存在助记符为：" + nullspells + "的商品!";
            }
        }
        if (StringUtils.isNotEmpty(nullbarcode)) {
            if (StringUtils.isEmpty(msg2)) {
                msg2 = "商品档案中不存在条形码为：" + nullbarcode + "的商品!";
            } else {
                msg2 += "<br>" + "商品档案中不存在条形码为：" + nullbarcode + "的商品!";
            }
        }
        if (StringUtils.isNotEmpty(errormsg)) {
            if (StringUtils.isEmpty(msg2)) {
                msg2 = errormsg;
            } else {
                msg2 += "<br>" + errormsg;
            }
        }
        if (StringUtils.isNotEmpty(drerrormsg)) {
            if (StringUtils.isEmpty(msg2)) {
                msg2 = drerrormsg;
            } else {
                msg2 += "<br>" + drerrormsg;
            }
        }
        if (StringUtils.isNotEmpty(failorders)) {
            if (StringUtils.isEmpty(msg2)) {
                msg2 = failorders;
            } else {
                msg2 += "<br>" + failorders;
            }
        }
        Map map = new HashMap();
        if (StringUtils.isEmpty(msg2)) {
            map.put("flag", true);
        } else {
            map.put("msg", msg2);
        }
        return map;
    }

    @Override
    public List showPlannedOrderListBy(Map map) throws Exception {
        String datasql = getDataAccessRule("t_purchase_buyorder", null);
        map.put("dataSql", datasql);
        boolean showdetail = false;
        if (null != map.get("showdetail") && StringUtils.isNotEmpty(map.get("showdetail").toString()) && "1".equals(map.get("showdetail").toString())) {
            showdetail = true;
        }
        List<PlannedOrder> list = plannedOrderMapper.showPlannedOrderListBy(map);
        for (PlannedOrder item : list) {
            if (StringUtils.isNotEmpty(item.getSupplierid())) {
                BuySupplier buySupplier = getSupplierInfoById(item.getSupplierid());
                if (null != buySupplier) {
                    item.setSuppliername(buySupplier.getName());
                }
            }
            if (StringUtils.isNotEmpty(item.getStorageid())) {
                StorageInfo storageInfo = getStorageInfoByID(item.getStorageid());
                if (null != storageInfo) {
                    item.setStoragename(storageInfo.getName());
                }
            }
            if (StringUtils.isNotEmpty(item.getBuydeptid())) {
                DepartMent departMent = getDepartmentByDeptid(item.getBuydeptid());
                if (null != departMent) {
                    item.setBuydeptname(departMent.getName());
                }
            }
            if (showdetail) {
                List<PlannedOrderDetail> detailList = showPurePlannedOrderDetailListByOrderId(item.getId());
                if (null != list && list.size() > 0) {
                    for (PlannedOrderDetail detail : detailList) {
                        if (null != detail) {
                            if (StringUtils.isNotEmpty(detail.getGoodsid())) {
                                detail.setGoodsInfo(getGoodsInfoByID(detail.getGoodsid()));
                            }
                            if (StringUtils.isNotEmpty(detail.getTaxtype())) {
                                TaxType taxType = getTaxType(detail.getTaxtype());
                                if (null != taxType) {
                                    detail.setTaxtypename(taxType.getName());
                                }
                            }
                            if (StringUtils.isNotEmpty(detail.getAuxunitid())) {
                                MeteringUnit meteringUnit = getMeteringUnitById(detail.getAuxunitid());
                                if (null != meteringUnit) {
                                    detail.setAuxunitname(meteringUnit.getName());
                                }
                            }
                        }
                    }
                    item.setPlannedOrderDetailList(detailList);
                }
            }
        }
        return list;
    }

    @Override
    public boolean updateOrderPrinttimes(PlannedOrder plannedOrder) throws Exception {
        return plannedOrderMapper.updateOrderPrinttimes(plannedOrder) > 0;
    }

    @Override
    public List getPlannedOrderDetailExport(PageMap pageMap) throws Exception {
        List<Map> list = plannedOrderMapper.getPlannedOrderDetailExport(pageMap);
        for (Map itemMap : list) {
            String tmp = "";
            if (itemMap.containsKey("buydeptid")) {
                tmp = (String) itemMap.get("buydeptid");
                if (null != tmp && !"".equals(tmp.trim())) {
                    DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(tmp.trim());
                    if (departMent != null) {
                        itemMap.put("buydeptname", departMent.getName());
                    }
                }
            }
            if (itemMap.containsKey("buyuserid")) {
                tmp = (String) itemMap.get("buyuserid");
                if (null != tmp && !"".equals(tmp.trim())) {
                    Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(tmp.trim());
                    if (personnel != null) {
                        itemMap.put("buyusername", personnel.getName());
                    }
                }
            }
            if (itemMap.containsKey("supplierid")) {
                tmp = (String) itemMap.get("supplierid");
                if (null != tmp && !"".equals(tmp.trim())) {
                    BuySupplier buySupplier = getSupplierInfoById(tmp.trim());
                    if (null != buySupplier) {
                        itemMap.put("suppliername", buySupplier.getName());
                    }
                }
            }
            if (itemMap.containsKey("handlerid")) {
                tmp = (String) itemMap.get("handlerid");
                if (null != tmp && !"".equals(tmp.trim())) {
                    Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(tmp.trim());
                    if (personnel != null) {
                        itemMap.put("handlername", personnel.getName());
                    }
                }
            }

            if (itemMap.containsKey("storageid")) {
                tmp = (String) itemMap.get("storageid");
                if (null != tmp && !"".equals(tmp.trim())) {
                    StorageInfo storageInfo = getStorageInfoByID(tmp.trim());
                    if (null != storageInfo) {
                        itemMap.put("storagename", storageInfo.getName());
                    }
                }
            }
            if (itemMap.containsKey("status")) {
                tmp = (String) itemMap.get("status");
                if (null != tmp && !"".equals(tmp.trim())) {
                    SysCode sysCode = getBaseSysCodeInfo(tmp.trim(), "status");
                    if (null != sysCode) {
                        itemMap.put("statusname", sysCode.getCodename());
                    }
                }
            }
            if (itemMap.containsKey("goodsid")) {
                tmp = (String) itemMap.get("goodsid");
                if (null != tmp && !"".equals(tmp.trim())) {
                    GoodsInfo goodsInfo = getGoodsInfoByID(tmp.trim());
                    if (null != goodsInfo) {
                        //itemMap.put("goodsInfo", goodsInfo);
                        itemMap.put("goodsname", goodsInfo.getName());
                        itemMap.put("model", goodsInfo.getModel());
                        itemMap.put("barcode", goodsInfo.getBarcode());
                        itemMap.put("brandname", goodsInfo.getBrandName());
                    }
                }
            }
            if (itemMap.containsKey("taxtype")) {
                tmp = (String) itemMap.get("taxtype");
                if (null != tmp && !"".equals(tmp.trim())) {
                    TaxType taxType = getTaxType(tmp.trim());
                    if (null != taxType) {
                        itemMap.put("taxtypename", taxType.getName());
                    }
                }
            }
            if (itemMap.containsKey("auxunitid")) {
                tmp = (String) itemMap.get("auxunitid");
                if (null != tmp && !"".equals(tmp.trim())) {
                    MeteringUnit meteringUnit = getMeteringUnitById(tmp.trim());
                    if (null != meteringUnit) {
                        itemMap.put("auxunitname", meteringUnit.getName());
                    }
                }
            }
        }
        return list;
    }
}

