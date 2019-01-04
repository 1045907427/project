package com.hd.agent.storage.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Brand;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.MeteringUnit;
import com.hd.agent.basefiles.model.StorageInfo;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.storage.model.*;
import com.hd.agent.storage.service.IPurchaseEnterService;
import com.hd.agent.storage.service.ISplitMergeService;
import com.hd.agent.storage.service.IStorageSummaryService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 拆装单Action
 * @author limin
 * @date Sep 16, 2015
 */
public class SplitMergeAction extends BaseFilesAction {

    /**
     * 拆分单Service
     */
    private ISplitMergeService splitMergeService;

    private IPurchaseEnterService purchaseEnterService;

    private IStorageSummaryService storageSummaryService;

    private SplitMerge splitmerge;

    public ISplitMergeService getSplitMergeService() {
        return splitMergeService;
    }

    public void setSplitMergeService(ISplitMergeService splitMergeService) {
        this.splitMergeService = splitMergeService;
    }

    public SplitMerge getSplitmerge() {
        return splitmerge;
    }

    public void setSplitmerge(SplitMerge splitmerge) {
        this.splitmerge = splitmerge;
    }

    public IPurchaseEnterService getPurchaseEnterService() {
        return purchaseEnterService;
    }

    public void setPurchaseEnterService(IPurchaseEnterService purchaseEnterService) {
        this.purchaseEnterService = purchaseEnterService;
    }

    public IStorageSummaryService getStorageSummaryService() {
        return storageSummaryService;
    }

    public void setStorageSummaryService(IStorageSummaryService storageSummaryService) {
        this.storageSummaryService = storageSummaryService;
    }

    /**
     * 拆分单列表页面
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 17, 2015
     */
    public String splitListPage() throws Exception {

        return SUCCESS;
    }

    /**
     * 拆分单列表页面
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 17, 2015
     */
    public String selectSplitMergeList() throws Exception {

        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);

        PageData pageData = splitMergeService.selectSplitMergeList(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

    /**
     * 拆分单页面
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 17, 2015
     */
    public String splitPage() throws Exception {

        String id = request.getParameter("id");

        Map param = new HashMap();
        param.put("billid", id);
        param.put("type", 2);
        pageMap.setCondition(param);
        pageMap.setPage(1);
        pageMap.setRows(9999);

        List list = splitMergeService.selectSplitMergeDetailList(pageMap).getList();
        SplitMerge splitmerge = splitMergeService.selectSplitMerge(id);

        request.setAttribute("editPrice", getSysParamValue("SplitMergeEditPrice"));
        request.setAttribute("list", JSONUtils.listToJsonStr(list));
        request.setAttribute("splitmerge", splitmerge);
        return SUCCESS;
    }

    /**
     * 拆分单添加页面
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 17, 2015
     */
    public String splitAddPage() throws Exception {

        request.setAttribute("editPrice", getSysParamValue("SplitMergeEditPrice"));
        request.setAttribute("autoCreate", isAutoCreate("t_storage_splitmerge"));
        return SUCCESS;
    }

    /**
     * 拆分单编辑页面
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 17, 2015
     */
    public String splitEditPage() throws Exception {

        String id = request.getParameter("id");

        SplitMerge splitmerge = splitMergeService.selectSplitMerge(id);
        List<SplitMergeDetail> details = splitMergeService.selectSplitMergeDetailListByBillid(id);

        request.setAttribute("editPrice", getSysParamValue("SplitMergeEditPrice"));
        request.setAttribute("splitmerge", splitmerge);
        request.setAttribute("detaillist",JSONUtils.listToJsonStr(details));
        request.setAttribute("autoCreate", isAutoCreate("t_storage_splitmerge"));
        return SUCCESS;
    }

    /**
     * 拆分单查看页面
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 27, 2015
     */
    public String splitViewPage() throws Exception {

        String id = request.getParameter("id");

        SplitMerge splitmerge = splitMergeService.selectSplitMerge(id);

        request.setAttribute("splitmerge", splitmerge);
        return SUCCESS;
    }

    /**
     * 新增拆分单
     * @return
     * @throws Exception
     * @author limin
     * @date Oct 23, 2015
     */
    @UserOperateLog(key = "SplitMerge", type = 2)
    public String addSplitMerge() throws Exception {

        String detaillist = request.getParameter("detaillist");
        List<SplitMergeDetail> list = JSONUtils.jsonStrToList(detaillist, new SplitMergeDetail());

        Map check = checkSplitMerge(splitmerge, list);
        boolean flag = (Boolean) check.get("flag");
        if(!flag) {

            addJSONObject(check);
            return SUCCESS;
        }

        if (isAutoCreate("t_storage_splitmerge")) {

            // 获取自动编号
            String id = getAutoCreateSysNumbderForeign(splitmerge, "t_storage_splitmerge");
            splitmerge.setId(id);

        } else {

            splitmerge.setId("CZD-" + CommonUtils.getDataNumberSendsWithRand());
        }

        SysUser user = getSysUser();
        splitmerge.setAdduserid(user.getUserid());
        splitmerge.setAddusername(user.getName());
        splitmerge.setAdddeptid(user.getDepartmentid());
        splitmerge.setAdddeptname(user.getDepartmentname());

        int ret = splitMergeService.insertSplitMerge(splitmerge, list);

        Map map = new HashMap();
        map.put("flag", ret > 0);
        map.put("backid", splitmerge.getId());
        map.put("type", "add");
        addJSONObject(map);
        addLog(("1".equals(splitmerge.getBilltype()) ? "拆分单" : "组装单") + "新增 编号：" + splitmerge.getId(), ret > 0);
        return SUCCESS;
    }

    /**
     * 新增拆分单
     * @return
     * @throws Exception
     * @author limin
     * @date Oct 23, 2015
     */
    @UserOperateLog(key = "SplitMerge", type = 3)
    public String editSplitMerge() throws Exception {

        String detaillist = request.getParameter("detaillist");
        List<SplitMergeDetail> list = JSONUtils.jsonStrToList(detaillist, new SplitMergeDetail());

        Map check = checkSplitMerge(splitmerge, list);
        boolean flag = (Boolean) check.get("flag");
        if(!flag) {

            addJSONObject(check);
            return SUCCESS;
        }

        SysUser user = getSysUser();
        splitmerge.setModifyuserid(user.getUserid());
        splitmerge.setModifyusername(user.getName());

        int ret = splitMergeService.updateSplitMerge(splitmerge, list);

        Map map = new HashMap();
        map.put("flag", ret > 0);
        map.put("backid", splitmerge.getId());
        map.put("type", "edit");
        addJSONObject(map);
        addLog(("1".equals(splitmerge.getBilltype()) ? "拆分单" : "组装单") + "编辑 编号：" + splitmerge.getId(), ret > 0);
        return SUCCESS;
    }

    /**
     * 拆装单审核
     * @return
     * @throws Exception
     * @author limin
     * @date Oct 23, 2015
     */
    @UserOperateLog(key = "SplitMerge", type = 3)
    public String auditSplitMerge() throws Exception {

        String id = request.getParameter("id");
        Map map = new HashMap();
        map.put("id", id);

        SplitMerge splitMerge = splitMergeService.selectSplitMerge(id);
        List<SplitMergeDetail> details = splitMergeService.selectSplitMergeDetailListByBillid(id);

        if(splitMerge == null) {

            map.put("flag", false);
            map.put("msg", "审核失败，单据不存在！");
            addJSONObject(map);
            return SUCCESS;
        }

        if("3".equals(splitMerge.getStatus())) {

            map.put("flag", false);
            map.put("msg", "审核失败，单据已被审核！");
            addJSONObject(map);
            return SUCCESS;
        }

        Map check = checkSplitMerge(splitMerge, details);
        boolean flag = (Boolean) check.get("flag");
        if(!flag) {

            addJSONObject(check);
            return SUCCESS;
        }

        SysUser user = getSysUser();
        splitMerge.setBusinessdate(CommonUtils.getTodayDataStr());
        splitMerge.setAudituserid(user.getUserid());
        splitMerge.setAuditusername(user.getName());
        splitMerge.setStatus("3");  // 3: 审核成功

        // 生成其他出入库单
        Map outs = new HashMap();    // 其他出库单MAP
        Map enters = new HashMap();  // 其他入库单MAP
        int count = 1;

        // 拆分单
        if("1".equals(splitMerge.getBilltype())) {

            StorageOtherOut out = new StorageOtherOut();
            // 生成临时编号
            out.setId("0000000000".substring(String.valueOf(count).length()) + count);
            count++;
            out.setStatus("2");
            out.setBusinessdate(CommonUtils.getTodayDataStr());
            out.setRemark(splitMerge.getRemark());
            out.setAdduserid(user.getUserid());
            out.setAddusername(user.getName());
            out.setAdddeptid(user.getDepartmentid());
            out.setAdddeptname(user.getDepartmentname());
            out.setAudituserid(user.getUserid());
            out.setAuditusername(user.getName());
            out.setDeptid(user.getDepartmentid());
            out.setUserid(user.getUserid());
            out.setStorageid(splitMerge.getStorageid());
         //   out.setOuttype("4");
            out.setSourcetype("3");
            out.setSourceid(splitMerge.getId());

            List<StorageOtherOutDetail> outList = new ArrayList<StorageOtherOutDetail>();   // 出库单明细
            {

                StorageOtherOutDetail detail = new StorageOtherOutDetail();
                detail.setBillid(out.getId());
                detail.setSummarybatchid(splitMerge.getSummarybatchid());
                detail.setGoodsid(splitMerge.getGoodsid());

                GoodsInfo goods = getGoodsInfoByID(splitMerge.getGoodsid());
                if(goods != null && StringUtils.isNotEmpty(goods.getBrand())) {

                    Brand brand = getBaseGoodsService().getBrandInfo(goods.getBrand());
                    detail.setBrandid(brand == null ? null : brand.getId());
                }

                if(goods != null) {

                    detail.setUnitid(goods.getMainunit());
                    detail.setUnitname(goods.getMainunitName());
                    detail.setAuxunitid(goods.getAuxunitid());
                    detail.setAuxunitname(goods.getAuxunitname());
                }
                detail.setUnitnum(splitMerge.getUnitnum());

                Map auxnumMap = countGoodsInfoNumber(splitMerge.getGoodsid(), goods.getAuxunitid(), splitMerge.getUnitnum());

                detail.setAuxnum((BigDecimal) auxnumMap.get("auxnum"));
                detail.setAuxremainder(new BigDecimal((String) auxnumMap.get("auxremainder")));
                detail.setAuxnumdetail((String) auxnumMap.get("auxnumdetail"));
                detail.setTotalbox(detail.getUnitnum().divide(goods.getBoxnum(), decimalLen, BigDecimal.ROUND_HALF_UP));
                detail.setTaxprice(splitMerge.getPrice());
                detail.setTaxamount(splitMerge.getPrice().multiply(splitMerge.getUnitnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));

                Map taxinfoMap = getTaxInfosByTaxpriceAndTaxtype(splitMerge.getPrice(), goods.getDefaulttaxtype(), null);

                detail.setNotaxprice((BigDecimal) taxinfoMap.get("notaxprice"));
                detail.setNotaxamount(detail.getNotaxprice().multiply(splitMerge.getUnitnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                detail.setTaxtype(goods.getDefaulttaxtype());
                detail.setTax(detail.getTaxamount().subtract(detail.getNotaxamount()));
                detail.setCostprice(goods.getNewbuyprice());
                detail.setRealcostprice(goods.getCostaccountprice() != null ? goods.getCostaccountprice() : goods.getNewbuyprice());

                detail.setRemark("");
                detail.setStorageid(splitMerge.getStorageid());
                detail.setStoragelocationid(splitMerge.getStoragelocationid());
                detail.setBatchno(splitMerge.getBatchid());
                detail.setProduceddate(splitMerge.getProduceddate());
                detail.setDeadline(splitMerge.getDeadline());
                detail.setSeq(null);

                outList.add(detail);

                outs.put(out.getId() + "A", out);
                outs.put(out.getId() + "B", outList);
            }

            {


//                List<SplitMergeDetail> details = splitMergeService.selectSplitMergeDetailListByBillid(id);

                // 排序
                Collections.sort(details, new Comparator<SplitMergeDetail>() {

                    @Override
                    public int compare(SplitMergeDetail o1, SplitMergeDetail o2) {

                        String storageid1 = "".concat(o1.getStorageid());
                        String storageid2 = "".concat(o2.getStorageid());

                        return storageid1.compareTo(storageid2);
                    }
                });

                //for(SplitMergeDetail detail : details) {
                for(int i = 0; i < details.size();/* i++*/) {

                    SplitMergeDetail detail = details.get(i);

                    StorageOtherEnter enter = new StorageOtherEnter();
                    // 生成临时编号
                    enter.setId("0000000000".substring(String.valueOf(count).length()) + count);
                    count++;
                    enter.setStatus("2");
                    enter.setBusinessdate(CommonUtils.getTodayDataStr());
                  //  enter.setRemark("拆分单单据号：" + splitMerge.getId());
                    enter.setRemark(splitMerge.getRemark());
                    enter.setAdduserid(user.getUserid());
                    enter.setAddusername(user.getName());
                    enter.setAdddeptid(user.getDepartmentid());
                    enter.setAdddeptname(user.getDepartmentname());
                    enter.setAudituserid(user.getUserid());
                    enter.setAuditusername(user.getName());
                    enter.setDeptid(user.getDepartmentid());
                    enter.setUserid(user.getUserid());
                    enter.setStorageid(detail.getStorageid());
                    enter.setSourcetype("3");
                    enter.setSourceid(splitMerge.getId());
                  //  enter.setEntertype("4");

                    List<StorageOtherEnterDetail> enterList = new ArrayList<StorageOtherEnterDetail>();
                    do {

                        detail = details.get(i);

                        StorageOtherEnterDetail enterDetail = new StorageOtherEnterDetail();

                        enterDetail.setBillid(out.getId());
//                    enterDetail.setSummarybatchid("");
                        enterDetail.setGoodsid(detail.getGoodsid());

                        GoodsInfo goods = getGoodsInfoByID(detail.getGoodsid());
                        if(goods != null && StringUtils.isNotEmpty(goods.getBrand())) {

                            Brand brand = getBaseGoodsService().getBrandInfo(goods.getBrand());
                            enterDetail.setBrandid(brand == null ? null : brand.getId());
                        }

                        if(goods != null) {

                            enterDetail.setUnitid(goods.getMainunit());
                            enterDetail.setUnitname(goods.getMainunitName());
                            enterDetail.setAuxunitid(goods.getAuxunitid());
                            enterDetail.setAuxunitname(goods.getAuxunitname());
                        }

                        enterDetail.setUnitnum(detail.getUnitnum());

                        Map auxnumMap = countGoodsInfoNumber(detail.getGoodsid(), goods.getAuxunitid(), detail.getUnitnum());

                        enterDetail.setAuxnum((BigDecimal) auxnumMap.get("auxnum"));
                        enterDetail.setAuxremainder(new BigDecimal((String) auxnumMap.get("auxremainder")));
                        enterDetail.setAuxnumdetail((String) auxnumMap.get("auxnumdetail"));
                        enterDetail.setTotalbox(enterDetail.getUnitnum().divide(goods.getBoxnum(), decimalLen, BigDecimal.ROUND_HALF_UP));
                        enterDetail.setTaxprice(detail.getPrice());
                        enterDetail.setTaxamount(detail.getPrice().multiply(detail.getUnitnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));

                        Map taxinfoMap = getTaxInfosByTaxpriceAndTaxtype(detail.getPrice(), goods.getDefaulttaxtype(), null);

                        enterDetail.setNotaxprice((BigDecimal) taxinfoMap.get("notaxprice"));
                        enterDetail.setNotaxamount(enterDetail.getNotaxprice().multiply(detail.getUnitnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                        enterDetail.setTaxtype(goods.getDefaulttaxtype());
                        enterDetail.setTax(enterDetail.getTaxamount().subtract(enterDetail.getNotaxamount()));
                        enterDetail.setCostprice(goods.getNewbuyprice());
                        enterDetail.setRealcostprice(goods.getCostaccountprice() != null ? goods.getCostaccountprice() : goods.getNewbuyprice());

                        enterDetail.setRemark("");
                        enterDetail.setStorageid(detail.getStorageid());
                        enterDetail.setStoragelocationid("");
                        enterDetail.setBatchno(detail.getBatchid());
                        enterDetail.setProduceddate(detail.getProduceddate());
                        enterDetail.setDeadline(detail.getDeadline());
                        enterDetail.setSeq(null);

                        enterList.add(enterDetail);
                        i++;
                    } while(i < (details.size()) && details.get(i).getStorageid().equals(detail.getStorageid()));

                    enters.put(enter.getId() + "A", enter);
                    enters.put(enter.getId() + "B", enterList);
                }

            }

        // 组装单
        } else if("2".equals(splitMerge.getBilltype())) {

            StorageOtherEnter enter = new StorageOtherEnter();
            enter.setId("0000000000".substring(String.valueOf(count).length()) + count);
            count++;
            enter.setBusinessdate(CommonUtils.getTodayDataStr());
            enter.setStatus("2");
            enter.setBusinessdate(CommonUtils.getTodayDataStr());
            enter.setRemark(splitMerge.getRemark());
            enter.setAdduserid(user.getUserid());
            enter.setAddusername(user.getName());
            enter.setAdddeptid(user.getDepartmentid());
            enter.setAdddeptname(user.getDepartmentname());
            enter.setAudituserid(user.getUserid());
            enter.setAuditusername(user.getName());
            enter.setDeptid(user.getDepartmentid());
            enter.setUserid(user.getUserid());
            enter.setStorageid(splitMerge.getStorageid());
            enter.setSourcetype("4");
            enter.setSourceid(splitMerge.getId());
        //    enter.setEntertype("4");


            List<StorageOtherEnterDetail> enterList = new ArrayList<StorageOtherEnterDetail>(); // 入库单明细
            {
                StorageOtherEnterDetail detail = new StorageOtherEnterDetail();
                detail.setBillid(enter.getId());
                detail.setSummarybatchid(splitMerge.getSummarybatchid());
                detail.setGoodsid(splitMerge.getGoodsid());

                GoodsInfo goods = getGoodsInfoByID(splitMerge.getGoodsid());
                if(goods != null && StringUtils.isNotEmpty(goods.getBrand())) {

                    Brand brand = getBaseGoodsService().getBrandInfo(goods.getBrand());
                    detail.setBrandid(brand == null ? null : brand.getId());
                }

                if(goods != null) {

                    detail.setUnitid(goods.getMainunit());
                    detail.setUnitname(goods.getMainunitName());
                    detail.setAuxunitid(goods.getAuxunitid());
                    detail.setAuxunitname(goods.getAuxunitname());
                }
                detail.setUnitnum(splitMerge.getUnitnum());

                Map auxnumMap = countGoodsInfoNumber(splitMerge.getGoodsid(), goods.getAuxunitid(), splitMerge.getUnitnum());

                detail.setAuxnum((BigDecimal) auxnumMap.get("auxnum"));
                detail.setAuxremainder(new BigDecimal((String) auxnumMap.get("auxremainder")));
                detail.setAuxnumdetail((String) auxnumMap.get("auxnumdetail"));
                detail.setTotalbox(detail.getUnitnum().divide(goods.getBoxnum(), decimalLen, BigDecimal.ROUND_HALF_UP));
                detail.setTaxprice(splitMerge.getPrice());
                detail.setTaxamount(splitMerge.getPrice().multiply(splitMerge.getUnitnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));

                Map taxinfoMap = getTaxInfosByTaxpriceAndTaxtype(splitMerge.getPrice(), goods.getDefaulttaxtype(), null);

                detail.setNotaxprice((BigDecimal) taxinfoMap.get("notaxprice"));
                detail.setNotaxamount(detail.getNotaxprice().multiply(splitMerge.getUnitnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                detail.setTaxtype(goods.getDefaulttaxtype());
                detail.setTax(detail.getTaxamount().subtract(detail.getNotaxamount()));
                detail.setCostprice(goods.getNewbuyprice());
                detail.setRealcostprice(goods.getCostaccountprice() != null ? goods.getCostaccountprice() : goods.getNewbuyprice());

                detail.setRemark("");
                detail.setStorageid(splitMerge.getStorageid());
                detail.setStoragelocationid(splitMerge.getStoragelocationid());
                detail.setBatchno(splitMerge.getBatchid());
                detail.setProduceddate(splitMerge.getProduceddate());
                detail.setDeadline(splitMerge.getDeadline());
                detail.setSeq(null);

                enterList.add(detail);

                enters.put(enter.getId() + "A", enter);
                enters.put(enter.getId() + "B", enterList);
            }

            {
//                List<SplitMergeDetail> details = splitMergeService.selectSplitMergeDetailListByBillid(id);

                // 排序
                Collections.sort(details, new Comparator<SplitMergeDetail>() {

                    @Override
                    public int compare(SplitMergeDetail o1, SplitMergeDetail o2) {

                        String storageid1 = "".concat(o1.getStorageid());
                        String storageid2 = "".concat(o2.getStorageid());

                        return storageid1.compareTo(storageid2);
                    }
                });

                // for(SplitMergeDetail detail : details) {
                for(int i = 0; i < details.size();/* i++*/) {

                    SplitMergeDetail detail = details.get(i);

                    StorageOtherOut out = new StorageOtherOut();
                    // 生成临时编号
                    out.setId("0000000000".substring(String.valueOf(count).length()) + count);
                    count++;
                    out.setStatus("2");
                    out.setBusinessdate(CommonUtils.getTodayDataStr());
                  //  out.setRemark("组装单单据号：" + splitMerge.getId());
                    out.setRemark(splitMerge.getRemark());
                    out.setAdduserid(user.getUserid());
                    out.setAddusername(user.getName());
                    out.setAdddeptid(user.getDepartmentid());
                    out.setAdddeptname(user.getDepartmentname());
                    out.setAudituserid(user.getUserid());
                    out.setAuditusername(user.getName());
                    out.setDeptid(user.getDepartmentid());
                    out.setUserid(user.getUserid());
                    out.setStorageid(detail.getStorageid());
                    out.setSourceid(splitMerge.getId());
                    out.setSourcetype("4");
                 //   out.setOuttype("4");

                    List<StorageOtherOutDetail> outList = new ArrayList<StorageOtherOutDetail>();
                    do {

                        detail = details.get(i);

                        StorageOtherOutDetail outDetail = new StorageOtherOutDetail();

                        outDetail.setBillid(out.getId());
                        outDetail.setSummarybatchid(detail.getSummarybatchid());
                        outDetail.setGoodsid(detail.getGoodsid());

                        GoodsInfo goods = getGoodsInfoByID(detail.getGoodsid());
                        if(goods != null && StringUtils.isNotEmpty(goods.getBrand())) {

                            Brand brand = getBaseGoodsService().getBrandInfo(goods.getBrand());
                            outDetail.setBrandid(brand == null ? null : brand.getId());
                        }

                        if(goods != null) {

                            outDetail.setUnitid(goods.getMainunit());
                            outDetail.setUnitname(goods.getMainunitName());
                            outDetail.setAuxunitid(goods.getAuxunitid());
                            outDetail.setAuxunitname(goods.getAuxunitname());
                        }

                        outDetail.setUnitnum(detail.getUnitnum());

                        Map auxnumMap = countGoodsInfoNumber(detail.getGoodsid(), goods.getAuxunitid(), detail.getUnitnum());

                        outDetail.setAuxnum((BigDecimal) auxnumMap.get("auxnum"));
                        outDetail.setAuxremainder(new BigDecimal((String) auxnumMap.get("auxremainder")));
                        outDetail.setAuxnumdetail((String) auxnumMap.get("auxnumdetail"));
                        outDetail.setTotalbox(outDetail.getUnitnum().divide(goods.getBoxnum(), decimalLen, BigDecimal.ROUND_HALF_UP));
                        outDetail.setTaxprice(detail.getPrice());
                        outDetail.setTaxamount(detail.getPrice().multiply(detail.getUnitnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));

                        Map taxinfoMap = getTaxInfosByTaxpriceAndTaxtype(detail.getPrice(), goods.getDefaulttaxtype(), null);

                        outDetail.setNotaxprice((BigDecimal) taxinfoMap.get("notaxprice"));
                        outDetail.setNotaxamount(outDetail.getNotaxprice().multiply(detail.getUnitnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                        outDetail.setTaxtype(goods.getDefaulttaxtype());
                        outDetail.setTax(outDetail.getTaxamount().subtract(outDetail.getNotaxamount()));
                        outDetail.setCostprice(goods.getNewbuyprice());
                        outDetail.setRealcostprice(goods.getCostaccountprice() != null ? goods.getCostaccountprice() : goods.getNewbuyprice());

                        outDetail.setRemark("");
                        outDetail.setStorageid(detail.getStorageid());
                        outDetail.setStoragelocationid(detail.getStoragelocationid());
                        outDetail.setBatchno(detail.getBatchid());
                        outDetail.setProduceddate(detail.getProduceddate());
                        outDetail.setDeadline(detail.getDeadline());
                        outDetail.setSeq(null);

                        outList.add(outDetail);
                        i++;
                    } while(i < (details.size()) && details.get(i).getStorageid().equals(detail.getStorageid()));

                    outs.put(out.getId() + "A", out);
                    outs.put(out.getId() + "B", outList);
                }
            }
        }
        int ret = splitMergeService.auditSplitMerge(splitMerge, enters, outs);

        map.put("flag", ret > 0);
        map.put("backid", splitMerge.getId());
        map.put("type", "audit");
        addJSONObject(map);
        addLog(("1".equals(splitMerge.getBilltype()) ? "拆分单" : "组装单") + "审核 编号：" + splitMerge.getId(), ret > 0);
        return SUCCESS;
    }

    /**
     * 拆装单反审
     * @return
     * @throws Exception
     * @author limin
     * @date Oct 23, 2015
     */
    @UserOperateLog(key = "SplitMerge", type = 3)
    public String oppAuditSplitMerge() throws Exception {

        String id = request.getParameter("id");

        SplitMerge splitMerge = splitMergeService.selectSplitMerge(id);

        if(splitMerge == null) {

            Map map = new HashMap();
            map.put("flag", false);
            map.put("msg", "反审失败，单据不存在！");
            addJSONObject(map);
        }

        if("2".equals(splitMerge.getStatus())) {

            Map map = new HashMap();
            map.put("flag", false);
            map.put("msg", "反审失败，单据未被审核！");
            addJSONObject(map);
        }

        SysUser user = getSysUser();
        splitMerge.setAudituserid(user.getUserid());
        splitMerge.setAuditusername(user.getName());
        splitMerge.setStatus("2");  // 2： 保存

//        int ret = splitMergeService.auditSplitMerge(splitMerge);
        int ret = 0;

        Map map = new HashMap();
        map.put("flag", ret > 0);
        map.put("backid", splitmerge.getId());
        map.put("type", "audit");
        addJSONObject(map);
        addLog("拆装单反审 编号：" + splitmerge.getId(), ret > 0);
        return SUCCESS;
    }

    /**
     * 拆装单反审
     * @return
     * @throws Exception
     * @author limin
     * @date Oct 23, 2015
     */
    @UserOperateLog(key = "SplitMerge", type = 4)
    public String deleteSplitMerge() throws Exception {

        String id = request.getParameter("id");

        SplitMerge splitMerge = splitMergeService.selectSplitMerge(id);

        if(splitMerge == null) {

            Map map = new HashMap();
            map.put("flag", false);
            map.put("msg", "删除失败，单据不存在！");
            addJSONObject(map);
            return SUCCESS;
        }

        if("3".equals(splitMerge.getStatus())) {

            Map map = new HashMap();
            map.put("flag", false);
            map.put("msg", "删除失败，单据已被审核！");
            addJSONObject(map);
            return SUCCESS;
        }

        int ret = splitMergeService.deleteSplitMerge(id);

        Map map = new HashMap();
        map.put("flag", ret > 0);
        map.put("backid", id);
        map.put("type", "delete");
        addJSONObject(map);
        addLog(("1".equals(splitMerge.getBilltype()) ? "拆分单" : "组装单") + "删除 编号：" + id, ret > 0);
        return SUCCESS;
    }

    /**
     * 组装单列表页面
     * @return
     * @throws Exception
     * @author limin
     * @date Oct 29, 2015
     */
    public String mergeListPage() throws Exception {

        return SUCCESS;
    }

    /**
     * 组装单页面
     * @return
     * @throws Exception
     * @author limin
     * @date Oct 29, 2015
     */
    public String mergePage() throws Exception {

        String id = request.getParameter("id");

        Map param = new HashMap();
        param.put("billid", id);
        param.put("type", 2);
        pageMap.setCondition(param);
        pageMap.setPage(1);
        pageMap.setRows(9999);

        List list = splitMergeService.selectSplitMergeDetailList(pageMap).getList();
        SplitMerge splitmerge = splitMergeService.selectSplitMerge(id);

        request.setAttribute("editPrice", getSysParamValue("SplitMergeEditPrice"));
        request.setAttribute("list", JSONUtils.listToJsonStr(list));
        request.setAttribute("splitmerge", splitmerge);
        return SUCCESS;
    }

    /**
     * 组装单编辑页面
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 17, 2015
     */
    public String mergeEditPage() throws Exception {

        String id = request.getParameter("id");

        SplitMerge splitmerge = splitMergeService.selectSplitMerge(id);
        List<SplitMergeDetail> details = splitMergeService.selectSplitMergeDetailListByBillid(id);

        request.setAttribute("editPrice", getSysParamValue("SplitMergeEditPrice"));
        request.setAttribute("splitmerge", splitmerge);
        request.setAttribute("detaillist",JSONUtils.listToJsonStr(details));
        request.setAttribute("autoCreate", isAutoCreate("t_storage_splitmerge"));
        return SUCCESS;
    }

    /**
     * 组装单查看页面
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 27, 2015
     */
    public String mergeViewPage() throws Exception {

        String id = request.getParameter("id");

        SplitMerge splitmerge = splitMergeService.selectSplitMerge(id);

        request.setAttribute("splitmerge", splitmerge);
        return SUCCESS;
    }

    /**
     * 验证表单
     * @param splitMerge
     * @param details
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 9, 2016
     */
    private Map checkSplitMerge(SplitMerge splitMerge, List<SplitMergeDetail> details) throws Exception {

        Map map = new HashMap();
        map.put("flag", true);

        if("1".equals(splitMerge.getBilltype())) {

            // 拆分商品信息完整性check
            GoodsInfo goods = getGoodsInfoByID(splitMerge.getGoodsid());
            if(goods == null) {

                map = new HashMap();
                map.put("flag", false);
                map.put("msg", "拆分商品为空，请选择要拆分的商品！");
                return map;
            }

            StorageInfo storage = getStorageInfo(splitMerge.getStorageid());
            if(storage == null) {

                map.put("flag", false);
                map.put("msg", "出库仓库为空，请选择出库仓库！");
                return map;
            }

            if("1".equals(goods.getIsbatch()) && "1".equals(storage.getIsbatch()) && StringUtils.isEmpty(splitMerge.getBatchid())) {

                map.put("flag", false);
                map.put("msg", "请选择拆分商品的批次号！");
                return map;
            }
//
//            Map batchMap = getBatchno(splitMerge.getGoodsid(), splitMerge.getProduceddate());
//            splitMerge.setBatchid((String) batchMap.get("batchno"));
//            splitMerge.setDeadline((String) batchMap.get("deadline"));

            // 拆分商品库存check
            // 非批次管理
            if((!"1".equals(goods.getIsbatch())) || (!"1".equals(storage.getIsbatch())))  {

                List<Map> list = storageSummaryService.showStorageSummaryBatchList(splitMerge.getStorageid(), splitMerge.getGoodsid(),null);
                if(list == null || list.size() == 0) {

                    map.put("flag", false);
                    map.put("msg", "拆分商品数量超过可用量！");
                    return map;
                }

                BigDecimal usablenum = BigDecimal.ZERO;
                for(Map storageSummary : list) {

                    usablenum = usablenum.add((BigDecimal) storageSummary.get("usablenum"));
                }

                if(usablenum.compareTo(splitMerge.getUnitnum()) < 0) {

                    map.put("flag", false);
                    map.put("msg", "拆分商品数量超过可用量！");
                    return map;
                }

            // 批次管理
            } else {

                List<Map> list = storageSummaryService.showStorageSummaryBatchList(splitMerge.getStorageid(), splitMerge.getGoodsid(),null);
                if(list == null || list.size() == 0) {

                    map.put("flag", false);
                    map.put("msg", "拆分商品数量超过可用量！");
                    return map;
                }

                BigDecimal usablenum = BigDecimal.ZERO;
                for(Map storageSummary : list) {

                    if(splitMerge.getBatchid().equals(storageSummary.get("batchno"))) {

                        usablenum = usablenum.add((BigDecimal) storageSummary.get("usablenum"));
                    }
                }

                if(usablenum.compareTo(splitMerge.getUnitnum()) < 0) {

                    map.put("flag", false);
                    map.put("msg", "拆分商品数量超过可用量！");
                    return map;
                }
            }

            String errors = "";
            for(int i = 0; i < details.size(); i++) {

                SplitMergeDetail detail = details.get(i);
                boolean isbatch = false;

                if(StringUtils.isNotEmpty(detail.getGoodsid())) {

                    // 明细商品完整性check
                    if(StringUtils.isEmpty(detail.getStorageid())) {

                        map.put("flag", false);
                        errors = errors + "明细商品[" + detail.getGoodsid() + "]入库仓库未选择！\r\n";
                        continue;

                    }

                    // 设定拆分商品
                    GoodsInfo detailGoods = getGoodsInfoByID(detail.getGoodsid());
                    StorageInfo detailStorage = getStorageInfo(detail.getStorageid());
                    if("1".equals(detailGoods.getIsbatch()) && "1".equals(detailStorage.getIsbatch())) {

                        Map detailBatchMap = getBatchno(detail.getGoodsid(), splitMerge.getProduceddate());
                        detail.setProduceddate((String) detailBatchMap.get("produceddate"));
                        detail.setBatchid((String) detailBatchMap.get("batchno"));
                        detail.setDeadline((String) detailBatchMap.get("deadline"));
                    }

                }

            }
            if(StringUtils.isNotEmpty(errors)) {

                map.put("msg", errors);
                return map;
            }

        // 组装单
        } else if("2".equals(splitMerge.getBilltype())) {

            // 组装商品信息完整性check
            GoodsInfo goods = getGoodsInfoByID(splitMerge.getGoodsid());
            if(goods == null) {

                map = new HashMap();
                map.put("flag", false);
                map.put("msg", "组装商品为空，请选择要组装的商品！");
                return map;
            }

            StorageInfo storage = getStorageInfo(splitMerge.getStorageid());
            if(storage == null) {

                map.put("flag", false);
                map.put("msg", "入库仓库为空，请选择入库仓库！");
                return map;
            }

            String errors = "";                                     // error message
            String minDate = CommonUtils.getTodayDataStr();         // 明细商品最小生产日期
            for(int i = 0; i < details.size(); i++) {

                SplitMergeDetail detail = details.get(i);
                boolean isbatch = false;

                if(StringUtils.isNotEmpty(detail.getGoodsid())) {

                    // 明细商品完整性check
                    if(StringUtils.isEmpty(detail.getStorageid())) {

                        map.put("flag", false);
                        errors = errors + "明细商品[" + detail.getGoodsid() + "]出库仓库未选择！\r\n";
                        continue;

                    } else {

                        GoodsInfo detailGoods = getGoodsInfoByID(detail.getGoodsid());
                        StorageInfo detailStorage = getStorageInfo(detail.getStorageid());
                        if("1".equals(detailGoods.getIsbatch()) && "1".equals(detailStorage.getIsbatch())) {

                            isbatch = true;
                            if(StringUtils.isEmpty(detail.getBatchid())) {

                                map.put("flag", false);
                                errors = errors + "明细商品[" + detail.getGoodsid() + "]批次未选择！\r\n";
                                continue;
                            }
                        }
                    }

                    // 明细商品库存check
                    // 非批次管理
                    if(!isbatch) {

                        List<Map> list = storageSummaryService.showStorageSummaryBatchList(detail.getStorageid(), detail.getGoodsid(),null);
                        if(list == null || list.size() == 0) {

                            map.put("flag", false);
                            errors = errors + "明细商品[" + detail.getGoodsid() + "]数量超过可用量！\r\n";
                            continue;
                        }

                        BigDecimal usablenum = BigDecimal.ZERO;
                        for(Map storageSummary : list) {

                            usablenum = usablenum.add((BigDecimal) storageSummary.get("usablenum"));
                        }

                        if(usablenum.compareTo(detail.getUnitnum()) < 0) {

                            map.put("flag", false);
                            errors = errors + "明细商品[" + detail.getGoodsid() + "]数量超过可用量！\r\n";
                            continue;
                        }

                    // 批次管理
                    } else {

                        List<Map> list = storageSummaryService.showStorageSummaryBatchList(detail.getStorageid(), detail.getGoodsid(),null);
                        if(list == null || list.size() == 0) {

                            map.put("flag", false);
                            errors = errors + "明细商品[" + detail.getGoodsid() + "]数量超过可用量！\r\n";
                            continue;
                        }

                        BigDecimal usablenum = BigDecimal.ZERO;
                        for(Map storageSummary : list) {

                            if(detail.getBatchid().equals(storageSummary.get("batchno"))) {

                                usablenum = usablenum.add((BigDecimal) storageSummary.get("usablenum"));
                            }
                        }

                        if(usablenum.compareTo(detail.getUnitnum()) < 0) {

                            map.put("flag", false);
                            errors = errors + "明细商品[" + detail.getGoodsid() + "]数量超过可用量！\r\n";
                            continue;
                        }
                    }

                    if(StringUtils.isNotEmpty(detail.getProduceddate()) && minDate.compareTo(detail.getProduceddate()) > 0) {

                        minDate = detail.getProduceddate();
                    }
                }
            }
            if(StringUtils.isNotEmpty(errors)) {

                map.put("msg", errors);
                return map;
            }

            if("1".equals(goods.getIsbatch()) && "1".equals(storage.getIsbatch())) {

                Map batchMap = getBatchno(splitMerge.getGoodsid(), minDate);
                splitMerge.setProduceddate((String) batchMap.get("produceddate"));
                splitMerge.setBatchid((String) batchMap.get("batchno"));
                splitMerge.setDeadline((String) batchMap.get("deadline"));
            }
        }
        return map;
    }

    /**
     * 根据商品编号、生产日期获取批次号
     * @param goodsid
     * @param produceddate 为空时，默认为当前日期
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 9, 2016
     */
    private Map<String, String> getBatchno(String goodsid, String produceddate) throws Exception{

        if(StringUtils.isEmpty(produceddate)) {

            produceddate = CommonUtils.getTodayDataStr();
        }

        Map map = purchaseEnterService.getBatchno(goodsid, produceddate);
        map.put("produceddate", produceddate);
        return map;
    }

}
