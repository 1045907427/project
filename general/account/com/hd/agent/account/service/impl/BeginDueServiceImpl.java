package com.hd.agent.account.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.BeginDueMapper;
import com.hd.agent.account.model.BeginDue;
import com.hd.agent.account.service.IBeginDueService;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: PXX
 * Date: 2016/9/22
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class BeginDueServiceImpl extends BaseAccountServiceImpl implements IBeginDueService{
    private BeginDueMapper beginDueMapper;

    public BeginDueMapper getBeginDueMapper() {
        return beginDueMapper;
    }

    public void setBeginDueMapper(BeginDueMapper beginDueMapper) {
        this.beginDueMapper = beginDueMapper;
    }

    @Override
    public PageData getBeginDueList(PageMap pageMap) throws Exception {
        String dataSql = getDataAccessRule("t_account_begin_due", null);
        pageMap.setDataSql(dataSql);
        List<BeginDue> list = beginDueMapper.getBeginDueList(pageMap);
        for (BeginDue beginDue : list) {
            BuySupplier supplier = getSupplierInfoById(beginDue.getSupplierid());
            if (null != supplier) {
                beginDue.setSuppliername(supplier.getName());
            }
            if ("2".equals(beginDue.getStatus())) {
                beginDue.setStatusname("保存");
            } else if ("3".equals(beginDue.getStatus())) {
                beginDue.setStatusname("审核通过");
            } else if ("4".equals(beginDue.getStatus())) {
                beginDue.setStatusname("关闭");
            }
        }
        PageData pageData = new PageData(
                beginDueMapper.getBeginDueCount(pageMap), list, pageMap);
        BeginDue beginDue = beginDueMapper.getBeginDueSum(pageMap);
        List footer = new ArrayList();
        if (null != beginDue) {
            beginDue.setBusinessdate("合计");
            footer.add(beginDue);
        }
        pageData.setFooter(footer);
        return pageData;
    }

    @Override
    public boolean addBeginDue(BeginDue beginDue) throws Exception {
        if (null != beginDue) {
            if (isAutoCreate("t_account_begin_due")) {
                // 获取自动编号
                String id = getAutoCreateSysNumbderForeign(beginDue,"t_account_begin_due");
                beginDue.setId(id);
            } else {
                beginDue.setId("QC-" + CommonUtils.getDataNumberSendsWithRand());
            }
            BuySupplier supplier = getSupplierInfoById(beginDue.getSupplierid());
            if (null != supplier) {
                beginDue.setBuyarea(supplier.getBuyarea());
                beginDue.setBuydeptid(supplier.getBuydeptid());
                beginDue.setBuyuserid(supplier.getBuyuserid());
                beginDue.setSuppliersort(supplier.getSuppliersort());
            }
            SysUser sysUser = getSysUser();
            beginDue.setAdddeptid(sysUser.getDepartmentid());
            beginDue.setAdddeptname(sysUser.getDepartmentname());
            beginDue.setAdduserid(sysUser.getUserid());
            beginDue.setAddusername(sysUser.getName());
            int i = beginDueMapper.addBeginDue(beginDue);
            return i > 0;
        } else {
            return false;
        }
    }

    @Override
    public BeginDue getBeginDueInfo(String id) throws Exception {
        BeginDue beginDue = beginDueMapper.getBeginDueByID(id);
        if(null != beginDue){
            BuySupplier buySupplier = getSupplierInfoById(beginDue.getSupplierid());
            if(null != buySupplier){
                beginDue.setSuppliername(buySupplier.getName());
            }
        }
        return beginDue;
    }

    @Override
    public boolean editBeginDue(BeginDue beginDue) throws Exception {
        BeginDue oldBeginDue = beginDueMapper.getBeginDueByID(beginDue.getId());
        if ("2".equals(oldBeginDue.getStatus())) {
            SysUser sysUser = getSysUser();
            BuySupplier supplier = getSupplierInfoById(beginDue.getSupplierid());
            if (null != supplier) {
                beginDue.setBuyarea(supplier.getBuyarea());
                beginDue.setBuydeptid(supplier.getBuydeptid());
                beginDue.setBuyuserid(supplier.getBuyuserid());
                beginDue.setSuppliersort(supplier.getSuppliersort());
            }
            beginDue.setModifyuserid(sysUser.getUserid());
            beginDue.setModifyusername(sysUser.getName());
            int i = beginDueMapper.editBeginDue(beginDue);
            return i > 0;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteBeginDue(String id) throws Exception {
        BeginDue oldBeginDue = beginDueMapper.getBeginDueByID(id);
        if ("2".equals(oldBeginDue.getStatus())) {
            int i = beginDueMapper.deleteBeginDue(id);
            return i > 0;
        }
        return false;
    }

    @Override
    public Map auditBeignDue(String id) throws Exception {
        boolean flag = false;
        String msg = "";
        BeginDue oldBeginDue = beginDueMapper.getBeginDueByID(id);
        if ("2".equals(oldBeginDue.getStatus())) {
            SysUser sysUser = getSysUser();
            int i = beginDueMapper.auditBeginDue(id, sysUser.getUserid(), sysUser.getName());
            flag = i > 0;
        } else {
            msg = "单据:" + id + "状态不对或者不存在";
        }
        Map map = new HashMap();
        map.put("flag", flag);
        map.put("msg", msg);
        return map;
    }

    @Override
    public Map oppauditBeignDue(String id) throws Exception {
        boolean flag = false;
        String msg = "";
        BeginDue oldBeginDue = beginDueMapper.getBeginDueByID(id);
        if ("3".equals(oldBeginDue.getStatus()) && "0".equals(oldBeginDue.getIsinvoice())) {
            if("0".equals(oldBeginDue.getInvoicerefer())){
                int i = beginDueMapper.oppauditBeginDue(id);
                flag = i > 0;
            }else{
                msg = "单据:" + id + "已生成采购发票无法审核";
            }
        } else {
            msg = "单据:" + id + "状态不对或者不存在";
        }
        Map map = new HashMap();
        map.put("flag", flag);
        map.put("msg", msg);
        return map;
    }

    @Override
    public Map importBeignDue(List<Map> list) throws Exception {
        boolean flag = false;
        String msg = "";
        int truenum = 0;
        int falsenum = 0;
        String ids = "";
        for (Map map : list) {
            String id = "";
            String businessdate = "";
            String supplierid = "";
            String suppliername = "";
            String amountstr = "";
            BigDecimal amount = BigDecimal.ZERO;
            BeginDue beginDue = new BeginDue();
            if(map.containsKey("id")){
                id = (String) map.get("id");
            }
            if(map.containsKey("businessdate")){
                businessdate = (String) map.get("businessdate");
            }
            if(map.containsKey("supplierid")){
                supplierid = (String) map.get("supplierid");
            }
            if(map.containsKey("suppliername")){
                suppliername = (String) map.get("suppliername");
            }
            if(map.containsKey("amount")){
                amountstr = (String) map.get("amount");
                amount = new BigDecimal(amountstr);
            }
            boolean isrepeat = false;
            if(StringUtils.isEmpty(id)){
                if (isAutoCreate("t_account_begin_due")) {
                    // 获取自动编号
                    id = getAutoCreateSysNumbderForeign(beginDue,"t_account_begin_due");
                } else {
                    id ="QC-"+ CommonUtils.getDataNumberSendsWithRand();
                }
            }else{
                BeginDue oldBeginDue = beginDueMapper.getBeginDueByID(id);
                if(null!=oldBeginDue){
                    isrepeat = true;
                }
            }
            if(!isrepeat){
                if(StringUtils.isEmpty(businessdate)){
                    businessdate = CommonUtils.getTodayDataStr();
                }
                BuySupplier supplier =null;
                if(StringUtils.isNotEmpty(supplierid)){
                    supplier = getSupplierInfoById(supplierid);
                }else if(StringUtils.isNotEmpty(suppliername)){
                    List<BuySupplier> list1 = getBaseBuySupplierMapper().returnSupplierIdByName(suppliername);
                    if(list1.size() != 0){
                        supplier = list1.get(0);
                    }
                }
                beginDue.setId(id);
                beginDue.setBusinessdate(businessdate);
                beginDue.setSupplierid(supplierid);
                beginDue.setAmount(amount);
                beginDue.setStatus("2");
                if (null != supplier) {
                    beginDue.setSupplierid(supplier.getId());
                    beginDue.setSuppliername(supplier.getName());
                    beginDue.setBuyarea(supplier.getBuyarea());
                    beginDue.setBuydeptid(supplier.getBuydeptid());
                    beginDue.setBuyuserid(supplier.getBuyuserid());
                    beginDue.setSuppliersort(supplier.getSuppliersort());

                    SysUser sysUser = getSysUser();
                    beginDue.setAdddeptid(sysUser.getDepartmentid());
                    beginDue.setAdddeptname(sysUser.getDepartmentname());
                    beginDue.setAdduserid(sysUser.getUserid());
                    beginDue.setAddusername(sysUser.getName());
                    int i = beginDueMapper.addBeginDue(beginDue);
                    if(i>0){
                        flag = true;
                        truenum ++;
                        ids += id+",";
                    }else{
                        msg +="导入失败 供应商:"+supplierid+"，金额"+amountstr+";";
                        falsenum ++;
                    }
                }else{
                    if(StringUtils.isNotEmpty(supplierid) || StringUtils.isNotEmpty(suppliername)){
                        msg +="找不到供应商:"+supplierid+suppliername+";";
                    }else{
                        msg +="供应商编号和供应商名称不允许都为空"+";";
                    }
                    falsenum ++;
                }
            }else{
                msg +="导入失败 单据号:"+id+"重复;";
                falsenum ++;
            }

        }
        Map map = new HashMap();
        map.put("flag", flag);
        map.put("msg", msg);
        map.put("truenum", truenum);
        map.put("falsenum", falsenum);
        map.put("ids", ids);
        return map;
    }

    @Override
    public boolean updateBeginDueInvoicerefer(BeginDue beginDue) throws Exception {
        return beginDueMapper.updateBeginDueInvoicerefer(beginDue) > 0;
    }
}
