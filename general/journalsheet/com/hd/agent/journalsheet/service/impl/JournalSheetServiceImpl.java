
/**
 * @(#)JournalSheetServiceImpl.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 14, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.journalsheet.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.model.BankAmountOthers;
import com.hd.agent.account.service.IAccountForOAService;
import com.hd.agent.basefiles.dao.BuySupplierMapper;
import com.hd.agent.basefiles.dao.DepartMentMapper;
import com.hd.agent.basefiles.dao.GoodsMapper;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.journalsheet.dao.CustomerCostPayableMapper;
import com.hd.agent.journalsheet.dao.JournalSheetMapper;
import com.hd.agent.journalsheet.model.ApprovalPrice;
import com.hd.agent.journalsheet.model.CapitalInput;
import com.hd.agent.journalsheet.model.CustomerCostPayable;
import com.hd.agent.journalsheet.model.ExpensesEntering;
import com.hd.agent.journalsheet.model.FundInput;
import com.hd.agent.journalsheet.model.MatcostsInput;
import com.hd.agent.journalsheet.model.MatcostsInputStatement;
import com.hd.agent.journalsheet.model.ReimburseInput;
import com.hd.agent.journalsheet.service.IJournalSheetService;
import com.hd.agent.system.model.SysCode;
/**
 *
 *
 * @author panxiaoxiao
 */
public class JournalSheetServiceImpl extends BaseFilesServiceImpl implements IJournalSheetService {

    private JournalSheetMapper journalSheetMapper;

    private DepartMentMapper departMentMapper;

    private BuySupplierMapper buySupplierMapper;

    private GoodsMapper goodsMapper;
    /**
     * 客户应付费用dao
     */
    private CustomerCostPayableMapper customerCostPayableMapper;
    public GoodsMapper getGoodsMapper() {
        return goodsMapper;
    }

    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    public BuySupplierMapper getBuySupplierMapper() {
        return buySupplierMapper;
    }

    public void setBuySupplierMapper(BuySupplierMapper buySupplierMapper) {
        this.buySupplierMapper = buySupplierMapper;
    }

    public DepartMentMapper getDepartMentMapper() {
        return departMentMapper;
    }

    public void setDepartMentMapper(DepartMentMapper departMentMapper) {
        this.departMentMapper = departMentMapper;
    }

    public JournalSheetMapper getJournalSheetMapper() {
        return journalSheetMapper;
    }

    public void setJournalSheetMapper(JournalSheetMapper journalSheetMapper) {
        this.journalSheetMapper = journalSheetMapper;
    }

    public CustomerCostPayableMapper getCustomerCostPayableMapper() {
        return customerCostPayableMapper;
    }

    public void setCustomerCostPayableMapper(
            CustomerCostPayableMapper customerCostPayableMapper) {
        this.customerCostPayableMapper = customerCostPayableMapper;
    }

    @Override
    public boolean addCapitalInput(CapitalInput capitalInput) throws Exception {
        journalSheetMapper.deleteCapitalInputBySupplieridAndSubjectid(capitalInput.getSupplierid(), capitalInput.getSubjectid());
        return journalSheetMapper.addCapitalInput(capitalInput) > 0;
    }

    @Override
    public Map addDRCapitalInput(List<CapitalInput> list)
            throws Exception {
        Map returnMap = new HashMap();
        boolean flag = false;
        Map map = new HashMap();
        for(CapitalInput capitalInput : list){
            if(StringUtils.isNotEmpty(capitalInput.getId())){
                map.put("id", capitalInput.getId());
            }
            if(null != capitalInput && StringUtils.isNotEmpty(capitalInput.getSupplierid())){
                CapitalInput isExistCapitalInput = journalSheetMapper.getCapitalInputDetail(map);
                if(null == isExistCapitalInput){
                    List<SysCode> codeList =  getBaseSysCodeMapper().getSysCodeListForeign("pricesubject");
                    //判断是否存在科目名称，若存在，从编码表中获取科目编码，储存至对应的科目编码
                    if(StringUtils.isNotEmpty(capitalInput.getSubjectname())){
                        for(SysCode sysCode:codeList){
                            if(capitalInput.getSubjectname().equals(sysCode.getCodename())){
                                capitalInput.setSubjectid(sysCode.getCode());
                            }
                        }
                    }
                    //判断是否存在科目编码，若存在，从编码表中获取科目名称，储存至对应的科目名称
                    if(StringUtils.isNotEmpty(capitalInput.getSubjectid())){
                        for(SysCode sysCode:codeList){
                            if(capitalInput.getSubjectid().equals(sysCode.getCode())){
                                capitalInput.setSubjectname(sysCode.getCodename());
                            }
                        }
                    }
                    BuySupplier buySupplier = buySupplierMapper.getBuySupplier(capitalInput.getSupplierid());
                    if(null!=buySupplier){
                        capitalInput.setSupplierdeptid(buySupplier.getBuydeptid());
                    }
                    SysUser sysUser = getSysUser();
                    capitalInput.setAdduserid(sysUser.getUserid());
                    capitalInput.setAddusername(sysUser.getName());
                    flag =  journalSheetMapper.addCapitalInput(capitalInput) > 0;
                }
            }
        }
        returnMap.put("flag", flag);
        return returnMap;
    }

    @Override
    public boolean deleteCapitalInput(String id)
            throws Exception {
        return journalSheetMapper.deleteCapitalInput(id) > 0;
    }

    @Override
    public boolean editCapitalInput(CapitalInput capitalInput) throws Exception {
        return journalSheetMapper.editCapitalInput(capitalInput) > 0;
    }

    @Override
    public CapitalInput getCapitalInputDetail(String id) throws Exception {
        Map map = getAccessColumnMap("t_js_capitalinput", null);//加字段查看权限
        map.put("id", id);
        return journalSheetMapper.getCapitalInputDetail(map);
    }

    @Override
    public PageData getCapitalInputListPage(PageMap pageMap) throws Exception {
        String cols = getAccessColumnList("t_js_capitalinput",null); //字段权限
        pageMap.setCols(cols);
        String sql = getDataAccessRule("t_js_capitalinput",null); //数据权限
        pageMap.setDataSql(sql);
        List<CapitalInput> list = journalSheetMapper.getCapitalInputListPage(pageMap);
        if(list.size() != 0){
            for(CapitalInput capitalInput : list){
                if(StringUtils.isNotEmpty(capitalInput.getSupplierdeptid())){
                    DepartMent departMent = departMentMapper.getDepartmentInfo(capitalInput.getSupplierdeptid());
                    if(null!=departMent){
                        capitalInput.setSupplierdeptName(departMent.getName());
                    }
                }
                BuySupplier buySupplier = buySupplierMapper.getBuySupplier(capitalInput.getSupplierid());
                if(null!=buySupplier){
                    capitalInput.setSuppliername(buySupplier.getName());
                }

            }
        }
        //合计
        Map footerMap = new HashMap();
        footerMap.put("suppliername", "合计");
        footerMap.put("subjectexpenses", journalSheetMapper.getCapitalInputSum(pageMap));
        List footerList = new ArrayList();
        footerList.add(footerMap);
        PageData pageData = new PageData(journalSheetMapper.getCapitalInputListCount(pageMap),list,pageMap);
        pageData.setFooter(footerList);
        return pageData;
    }

	
	/*---------------------------------资金统计报表------------------------------------------------------*/

    @Override
    public PageData getCapitalStatisticsSheetList(PageMap pageMap)
            throws Exception {
        String sql = getDataAccessRule("t_js_capitalinput",null); //数据权限
        pageMap.setDataSql(sql);
        //获取资金科目列表
        List<SysCode> codeList = getBaseSysCodeMapper().getSysCodeListForeign("pricesubject");
        //资金录入下的供应商列表
        List<CapitalInput> supplierList = journalSheetMapper.getCapitalStatisticsBySupplier(pageMap);
        List dataList = new ArrayList();
        //当前合计
        Map footerMap = new HashMap();
        //总计
        Map footerMap1 = new HashMap();
        String begintime = (String) pageMap.getCondition().get("begintime");
        String endtime = (String) pageMap.getCondition().get("endtime");
        if(StringUtils.isEmpty(begintime)){
            begintime = "null";
        }
        if(StringUtils.isEmpty(endtime)){
            endtime = "null";
        }

        BigDecimal allLastAmount = new BigDecimal(0);
        //父级分类
        if(supplierList.size() != 0){
            for(CapitalInput capitalInput : supplierList){
                Map map = new HashMap();
                map.put("id", capitalInput.getSupplierid()+"_"+capitalInput.getSupplierdeptid()+"_"+begintime+"_"+endtime);
                map.put("state", "closed");
                map.put("parentId", "");
                map.put("supplierid", capitalInput.getSupplierid());
                BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(capitalInput.getSupplierid());
                if(null != buySupplier){
                    map.put("suppliername", buySupplier.getName());
                }
                else{
                    map.put("suppliername", capitalInput.getSuppliername());
                }

                map.put("supplierdeptid", capitalInput.getSupplierdeptid());

                //供应商所属部门名称
                DepartMent departMent = departMentMapper.getDepartmentInfo(capitalInput.getSupplierdeptid());
                if(null!=departMent){
                    map.put("supplierdeptName", departMent.getName());
                }

                //合计金额
                BigDecimal lastAmount = new BigDecimal(0);
                if(codeList.size() != 0){
                    for(SysCode sysCode : codeList){

                        Map condition = new HashMap();
                        condition.putAll(pageMap.getCondition());
                        condition.put("supplierid", capitalInput.getSupplierid());
                        condition.put("subjectid", sysCode.getCode());
                        PageMap pageMap2 = new PageMap();
                        pageMap2.setCondition(condition);
                        pageMap2.setDataSql(pageMap.getDataSql());
                        pageMap2.setQuerySql(pageMap.getQuerySql());
                        pageMap2.setPage(pageMap.getPage());
                        pageMap2.setRows(pageMap.getRows());
                        //资金科目金额合计
                        BigDecimal amount = journalSheetMapper.getCapitalAmountBySupplierAndPriceSubject(pageMap2);
                        map.put("subjectid"+sysCode.getCode(), amount);
                        map.put("subjectname", sysCode.getCodename());
                        if(null == amount){
                            amount = new BigDecimal(0);
                        }
                        lastAmount = lastAmount.add(amount);
                    }
                }
                map.put("lastAmount", lastAmount);
                dataList.add(map);
            }
        }

        PageData pageData = new PageData(journalSheetMapper.getCapitalStatisticsBySupplierCount(pageMap),dataList,pageMap);
        //列表行底合计金额显示
        for(SysCode sysCode :codeList){
            pageMap.getCondition().put("subjectid", sysCode.getCode());
            //同个资金科目总金额
            BigDecimal priceSubjectAmount = journalSheetMapper.getCapitalStatisticsQuerySumByPriceSubject(pageMap);
            if(null == priceSubjectAmount){
                priceSubjectAmount = new BigDecimal(0);
            }
            allLastAmount = allLastAmount.add(priceSubjectAmount);
            footerMap.put("subjectid"+sysCode.getCode(),priceSubjectAmount);
            footerMap1.put("subjectid"+sysCode.getCode(), journalSheetMapper.getCapitalStatisticsAllSumByPriceSubejct(pageMap));
        }

        BigDecimal totalAmount = journalSheetMapper.getCapitalStatisticsAllSum(pageMap);

        footerMap1.put("suppliername", "全部合计");
        footerMap1.put("lastAmount", totalAmount);

        footerMap.put("suppliername", "当前查询合计");
        footerMap.put("lastAmount", allLastAmount);
        //列表行底显示
        List footerList = new ArrayList();
        footerList.add(footerMap);
        footerList.add(footerMap1);
        pageData.setFooter(footerList);
        return pageData;
    }

    @Override
    public List getCapitalStatisticsDetailList(Map map)
            throws Exception {
        PageMap pageMap = new PageMap();
        String sql = getDataAccessRule("t_js_capitalinput",null); //数据权限
        pageMap.setDataSql(sql);
        pageMap.setCondition(map);
        List<CapitalInput> list = journalSheetMapper.getCapitalStatisticsDetailList(pageMap);
        List dataList = new ArrayList();
        String begintime = (String) pageMap.getCondition().get("begintime");
        String endtime = (String) pageMap.getCondition().get("endtime");
        if(StringUtils.isEmpty(begintime)){
            begintime="null";
        }
        if(StringUtils.isEmpty(endtime)){
            endtime="null";
        }
        for(CapitalInput capitalInput:list){
            Map objectmap = new HashMap();
            objectmap.put("id", capitalInput.getId());
            objectmap.put("state", "open");
            objectmap.put("parentId", capitalInput.getSupplierid()+"_"+capitalInput.getSupplierdeptid()+"_"+begintime+"_"+endtime);
            objectmap.put("supplierid", capitalInput.getSupplierid());
            BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(capitalInput.getSupplierid());
            if(null!=buySupplier){
                objectmap.put("suppliername", buySupplier.getName());
            }else{
                objectmap.put("suppliername", capitalInput.getSuppliername());
            }
            objectmap.put("supplierdeptid", capitalInput.getSupplierdeptid());
            objectmap.put("businessdate", capitalInput.getBusinessdate());
            DepartMent departMent = departMentMapper.getDepartmentInfo(capitalInput.getSupplierdeptid());
            if(null!=departMent){
                objectmap.put("supplierdeptName", departMent.getName());
            }
            objectmap.put("subjectid"+capitalInput.getSubjectid(), capitalInput.getSubjectexpenses());
            dataList.add(objectmap);
        }
        return dataList;
    }

	/*-------------------------------资金平均金额统计报表-------------------------------------*/

    @Override
    public PageData getFundAverageStatisticsParentList(PageMap pageMap)
            throws Exception {
        String sql = getDataAccessRule("t_js_fundinput",null); //数据权限
        pageMap.setDataSql(sql);
        //资金录入下的供应商列表
        List<FundInput> list = journalSheetMapper.getAverageDataGroupBySupplierList(pageMap);
        for(FundInput fundInput : list){
            BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(fundInput.getSupplierid());
            if(null != buySupplier){
                fundInput.setSupplierName(buySupplier.getName());
            }
            else{
                fundInput.setSupplierName(fundInput.getSupplierName());
            }
            //供应商所属部门名称
            DepartMent departMent = departMentMapper.getDepartmentInfo(fundInput.getSupplierdeptid());
            if(null!=departMent){
                fundInput.setSupplierdeptName(departMent.getName());
            }
        }
        PageData pageData = new PageData(journalSheetMapper.getAverageDataGroupBySupplierCount(pageMap),list,pageMap);

        List footerList = new ArrayList();;
        FundInput currentFA = journalSheetMapper.getSumAvgDataGroupBySupplier(pageMap);
        if(null != currentFA){
            currentFA.setSupplierName("当前平均金额");
            footerList.add(currentFA);
        }
        FundInput totalFund = journalSheetMapper.getSumAllAvgData(pageMap);
        if(null != totalFund){
            totalFund.setSupplierName("全部平均金额");
            footerList.add(totalFund);
        }
        pageData.setFooter(footerList);
        return pageData;
    }

    @Override
    public List getFundAverageStatisticsDetailList(Map map) throws Exception {
        PageMap pageMap = new PageMap();
        String sql = getDataAccessRule("t_js_fundinput",null); //数据权限
        pageMap.setDataSql(sql);
        pageMap.setCondition(map);
        List<FundInput> list = journalSheetMapper.getFundStatisticsDetailList(pageMap);
        List dataList = new ArrayList();
        String begintime = (String) pageMap.getCondition().get("begintime");
        String endtime = (String) pageMap.getCondition().get("endtime");
        if(StringUtils.isEmpty(begintime)){
            begintime="null";
        }
        if(StringUtils.isEmpty(endtime)){
            endtime="null";
        }
        if(list.size() != 0){
            for(FundInput fundInput:list){
                if(null != fundInput){
                    Map objectmap = new HashMap();
                    objectmap.put("id", fundInput.getId());
                    objectmap.put("state", "open");
                    objectmap.put("isshow", "0");
                    objectmap.put("parentId", fundInput.getSupplierid()+"_"+fundInput.getSupplierdeptid()+"_"+begintime+"_"+endtime);
                    objectmap.put("supplierid", fundInput.getSupplierid());
                    objectmap.put("advanceamount", fundInput.getAdvanceamount());
                    objectmap.put("stockamount", fundInput.getStockamount());
                    objectmap.put("receivableamount", fundInput.getReceivableamount());
                    objectmap.put("actingmatamount", fundInput.getActingmatamount());
                    objectmap.put("payableamount", fundInput.getPayableamount());
                    objectmap.put("totalAmount", fundInput.getAdvanceamount().add(fundInput.getStockamount())
                            .add(fundInput.getReceivableamount()).add(fundInput.getActingmatamount())
                            .add(fundInput.getPayableamount()));
                    BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(fundInput.getSupplierid());
                    if(null!=buySupplier){
                        objectmap.put("supplierName", buySupplier.getName());
                    }else{
                        objectmap.put("supplierName", fundInput.getSupplierName());
                    }
                    objectmap.put("supplierdeptid", fundInput.getSupplierdeptid());
                    objectmap.put("businessdate", fundInput.getBusinessdate());
                    DepartMent departMent = departMentMapper.getDepartmentInfo(fundInput.getSupplierdeptid());
                    if(null!=departMent){
                        objectmap.put("supplierdeptName", departMent.getName());
                    }
                    dataList.add(objectmap);
                }
            }
        }
        return dataList;
    }

    @Override
    public PageData getFundAverageStatisticsDetailList(PageMap pageMap)
            throws Exception {
        String sql = getDataAccessRule("t_js_fundinput",null); //数据权限
        pageMap.setDataSql(sql);
        List<FundInput> list = journalSheetMapper.getFundStatisticsDetailList(pageMap);
        for(FundInput fundInput:list){
            if(null != fundInput){
                BigDecimal totalAmount = fundInput.getAdvanceamount().add(fundInput.getStockamount())
                        .add(fundInput.getReceivableamount()).add(fundInput.getActingmatamount())
                        .add(fundInput.getPayableamount());
                fundInput.setTotalamount(totalAmount);
                BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(fundInput.getSupplierid());
                if(null!=buySupplier){
                    fundInput.setSupplierName(buySupplier.getName());
                }else{
                    fundInput.setSupplierName(fundInput.getSupplierName());
                }
                DepartMent departMent = departMentMapper.getDepartmentInfo(fundInput.getSupplierdeptid());
                if(null!=departMent){
                    fundInput.setSupplierdeptName(departMent.getName());
                }
            }
        }
        int count = journalSheetMapper.getFundStatisticsDetailListCount(pageMap);
        PageData pageData = new PageData(count,list,pageMap);

        //合计
        FundInput fundSum = journalSheetMapper.getTotalFundDetailAmount(pageMap);
        List footerList = new ArrayList();
        if(null != fundSum){
            BigDecimal totalAmount = fundSum.getAdvanceamount().add(fundSum.getStockamount())
                    .add(fundSum.getReceivableamount()).add(fundSum.getActingmatamount())
                    .add(fundSum.getPayableamount());
            fundSum.setTotalamount(totalAmount);
            fundSum.setSupplierName("平均金额");
            footerList.add(fundSum);
        }
        pageData.setFooter(footerList);
        return pageData;
    }

    /*-------------------------------费用录入-----------------------------------------------*/
    @Override
    public boolean addExpensesEntering(ExpensesEntering expensesEntering)throws Exception {
        return journalSheetMapper.addExpensesEntering(expensesEntering) > 0;
    }

    @Override
    public boolean deleteExpensesEntering(String id)throws Exception {
        return journalSheetMapper.deleteExpensesEntering(id) > 0;
    }

    @Override
    public boolean editExpensesEntering(ExpensesEntering expensesEntering)throws Exception {
        return journalSheetMapper.editExpensesEntering(expensesEntering) > 0;
    }

    @Override
    public ExpensesEntering getExpensesEnteringDetail(String id)throws Exception {
        Map map = getAccessColumnMap("t_finance_expensesentering", null);//加字段查看权限
        map.put("id", id);
        return journalSheetMapper.getExpensesEnteringDetail(map);
    }

    @Override
    public PageData getExpensesEnteringListPage(PageMap pageMap)throws Exception {
        String cols = getAccessColumnList("t_finance_expensesentering",null); //字段权限
        pageMap.setCols(cols);
        String sql = getDataAccessRule("t_finance_expensesentering",null); //数据权限
        pageMap.setDataSql(sql);
        List<ExpensesEntering> list = journalSheetMapper.getExpensesEnteringListPage(pageMap);
        if(list.size() != 0){
            for(ExpensesEntering expensesEntering:list){
                if(StringUtils.isNotEmpty(expensesEntering.getSupplierdeptid())){
                    DepartMent departMent = departMentMapper.getDepartmentInfo(expensesEntering.getSupplierdeptid());
                    if(null!=departMent){
                        expensesEntering.setSupplierdeptName(departMent.getName());
                    }
                }
                if(StringUtils.isNotEmpty(expensesEntering.getSupplierid())){
                    BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(expensesEntering.getSupplierid());
                    if(null!=buySupplier){
                        expensesEntering.setSuppliername(buySupplier.getName());
                    }
                }
            }
        }
        //合计
        Map footerMap = new HashMap();
        footerMap.put("suppliername", "合计");
        footerMap.put("subjectexpenses", journalSheetMapper.getExpensesEnteringSum(pageMap));
        List footerList = new ArrayList();
        footerList.add(footerMap);
        PageData pageData = new PageData(journalSheetMapper.getExpensesEnteringListCount(pageMap),list,pageMap);
        pageData.setFooter(footerList);
        return pageData;
    }

    @Override
    public boolean isUsedSubjectid(String subjectid) throws Exception {
        return journalSheetMapper.isUsedSubjectid(subjectid) > 0;
    }

    @Override
    public boolean isUsedSupplierid(String supplierid) throws Exception {
        return journalSheetMapper.isUsedSupplierid(supplierid) > 0;
    }

    @Override
    public List<Map> getSupplierExpenseSumData(List<String> idarr) throws Exception {
        if(null!=idarr && idarr.size()>0){
            List<Map> list = journalSheetMapper.getSupplierExpenseSumData(idarr);
            return list;
        }
        return new ArrayList<Map>();
    }

    @Override
    public List<Map> getSupplierExpenseSumDataForThird(List<String> idarr) throws Exception {
        if(null!=idarr && idarr.size()>0){
            List<Map> list = journalSheetMapper.getSupplierExpenseSumDataForThird(idarr);
            return list;
        }
        return new ArrayList<Map>();
    }

    @Override
    public PageData getSubjectDayAccountList(PageMap pageMap)throws Exception{
        String cols = getAccessColumnList("t_finance_expensesentering",null); //字段权限
        pageMap.setCols(cols);
        String sql = getDataAccessRule("t_finance_expensesentering",null); //数据权限
        pageMap.setDataSql(sql);

        List<ExpensesEntering> list = journalSheetMapper.getSupplierListBySubjectid(pageMap);
        int count = journalSheetMapper.getSupplierListCountBySubjectid(pageMap);

        List dataList = new ArrayList();

        if(list.size() != 0){
            for(ExpensesEntering expensesEntering : list){
                Map condition = new HashMap();
                condition.putAll(pageMap.getCondition());
                condition.put("supplierid", expensesEntering.getSupplierid());
                PageMap pageMap2 = new PageMap();
                pageMap2.setCondition(condition);
                pageMap2.setDataSql(pageMap.getDataSql());
                pageMap2.setQuerySql(pageMap.getQuerySql());
                pageMap2.setPage(pageMap.getPage());
                pageMap2.setRows(pageMap.getRows());
                List<ExpensesEntering> list2  = journalSheetMapper.getSubjectDayAccountList(pageMap2);
                BigDecimal totalAmount = new BigDecimal(0);
                for(ExpensesEntering expensesEntering2 : list2){
                    Map map = BeanUtils.describe(expensesEntering2);
                    map.remove("class");
                    DepartMent departMent = departMentMapper.getDepartmentInfo(expensesEntering2.getSupplierdeptid());
                    if(null!=departMent){
                        map.put("supplierdeptName", departMent.getName());
                    }else{
                        map.put("supplierdeptName","");
                    }
                    BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(expensesEntering.getSupplierid());
                    if(null!=buySupplier){
                        map.put("suppliername", buySupplier.getName());
                    }else{
                        map.put("suppliername", expensesEntering.getSuppliername());
                    }
                    totalAmount = totalAmount.add(expensesEntering2.getSubjectexpenses());
                    dataList.add(map);
                }
                if(list2.size()>0){
                    //小计
                    Map subtotal = new HashMap();
                    subtotal.put("subjectname", "小计");
                    subtotal.put("subjectexpenses", totalAmount);
                    dataList.add(subtotal);
                }

            }
        }

        Map totalQueryMap = new HashMap();
        BigDecimal allTotalQueryAmount = journalSheetMapper.getSubjectQuerySum(pageMap);
        totalQueryMap.put("suppliername", "当前查询合计");
        totalQueryMap.put("subjectexpenses", allTotalQueryAmount);
        //合计项
        Map totalMap = new HashMap();
        BigDecimal allTotalAmount = journalSheetMapper.getSubjectAllSum(pageMap);
        totalMap.put("suppliername", "全部合计");
        totalMap.put("subjectexpenses", allTotalAmount);

        List footerList = new ArrayList();
        footerList.add(totalQueryMap);
        footerList.add(totalMap);

        PageData pageData = new PageData(count,dataList,pageMap);
        pageData.setFooter(footerList);
        return pageData;
    }
    @Override
    public Map getClienteleDayAccountList(PageMap pageMap)throws Exception{
        String sql = getDataAccessRule("t_finance_expensesentering",null); //数据权限
        pageMap.setDataSql(sql);
        List<ExpensesEntering> list  = journalSheetMapper.getClienteleDayAccountList(pageMap);
        List dataList = new ArrayList();
        String begintime = (String) pageMap.getCondition().get("begintime");
        String endtime = (String) pageMap.getCondition().get("endtime");
        if(null==begintime || "".equals(begintime)){
            begintime="null";
        }
        if(null==endtime || "".equals(endtime)){
            endtime="null";
        }
        String supplierid = (String) pageMap.getCondition().get("supplierid");
        //合计
        BigDecimal allAmount = new BigDecimal(0);
        PageMap realAtPageMap = new PageMap();
        realAtPageMap.setCondition(pageMap.getCondition());
        realAtPageMap.setDataSql(getDataAccessRule("t_finance_approvalprice", null));
        BigDecimal totalApprovalAmount = journalSheetMapper.getApprovalPriceBySupplierid(realAtPageMap);
        for(ExpensesEntering expensesEntering : list){
            Map map = BeanUtils.describe(expensesEntering);
            map.remove("class");
            DepartMent departMent = departMentMapper.getDepartmentInfo(expensesEntering.getSupplierdeptid());
            if(null!=departMent){
                map.put("supplierdeptName", departMent.getName());
            }else{
                map.put("supplierdeptName","");
            }
            BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(expensesEntering.getSupplierid());
            if(null!=buySupplier){
                map.put("suppliername", buySupplier.getName());
            }else{
                map.put("suppliername", expensesEntering.getSuppliername());
            }
            map.put("remark", "");
            map.put("isshow", "1");
            map.put("subjectexpenses", expensesEntering.getSubjectexpenses());
            map.put("id", expensesEntering.getSupplierid()+"_"+begintime+"_"+endtime+"_"+expensesEntering.getSubjectid());
            map.put("state", "closed");
            map.put("parentId", "");
            allAmount = allAmount.add(expensesEntering.getSubjectexpenses());
            dataList.add(map);
        }
        Map footerMap = new HashMap();
        footerMap.put("suppliername", "合计");
        footerMap.put("subjectexpenses", allAmount);
        footerMap.put("approvalAmount", totalApprovalAmount);
        List footerList = new ArrayList();
        footerList.add(footerMap);
        Map returnMap = new HashMap();
        returnMap.put("rows", dataList);
        returnMap.put("footer", footerList);
        return returnMap;
    }
    @Override
    public List getClienteleDayAccountDetailList(Map map) throws Exception{
        PageMap pageMap = new PageMap();
        String sql = getDataAccessRule("t_finance_expensesentering",null); //数据权限
        pageMap.setDataSql(sql);
        pageMap.setCondition(map);
        List<ExpensesEntering> list = journalSheetMapper.getClienteleDayAccountDetailList(pageMap);
        List dataList = new ArrayList();
        String begintime = (String) pageMap.getCondition().get("begintime");
        String endtime = (String) pageMap.getCondition().get("endtime");
        if(null==begintime || "".equals(begintime)){
            begintime="null";
        }
        if(null==endtime || "".equals(endtime)){
            endtime="null";
        }
        for(ExpensesEntering expensesEntering : list){
            Map objectmap = BeanUtils.describe(expensesEntering);
            objectmap.put("id", expensesEntering.getId());
            objectmap.put("state", "open");
            objectmap.put("parentId", expensesEntering.getSupplierid()+"_"+begintime+"_"+endtime+"_"+expensesEntering.getSubjectid());
            objectmap.put("supplierid", expensesEntering.getSupplierid());
            BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(expensesEntering.getSupplierid());
            if(null!=buySupplier){
                objectmap.put("suppliername", buySupplier.getName());
            }else{
                objectmap.put("suppliername", expensesEntering.getSuppliername());
            }
            objectmap.put("supplierdeptid", expensesEntering.getSupplierdeptid());
            objectmap.put("businessdate1", expensesEntering.getBusinessdate());
            DepartMent departMent = departMentMapper.getDepartmentInfo(expensesEntering.getSupplierdeptid());
            if(null!=departMent){
                objectmap.put("supplierdeptName", departMent.getName());
            }
            objectmap.put("subjectexpenses", expensesEntering.getSubjectexpenses());
            objectmap.put("isshow", "0");
            dataList.add(objectmap);
        }
        return dataList;
    }
    @Override
    public PageData getStatisticslist(PageMap pageMap) throws Exception {
        String sql = getDataAccessRule("t_finance_expensesentering",null); //数据权限
        pageMap.setDataSql(sql);
        String sql1 = getDataAccessRule("t_js_fundinput",null); //数据权限
        pageMap.getCondition().put("datasql1", sql1);
        //获取科目列表
        List<SysCode> codeList = getBaseSysCodeMapper().getSysCodeListForeign("subjectid");
        String begintime = (String) pageMap.getCondition().get("begintime");
        String endtime = (String) pageMap.getCondition().get("endtime");
        if(null==begintime || "".equals(begintime)){
            begintime="null";
        }
        if(null==endtime || "".equals(endtime)){
            endtime="null";
        }
        List<Map> list = journalSheetMapper.getStatisticsListData(pageMap);
        for(Map map : list){
            String supplierid = (String) map.get("supplierid");
            String supplierdeptid = (String) map.get("supplierdeptid");
            map.put("id", supplierid+"_"+supplierdeptid+"_"+begintime+"_"+endtime);
            map.put("state", "closed");
            map.put("parentId", "");
            map.put("supplierid", supplierid);
            BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(supplierid);
            if(null!=buySupplier){
                map.put("suppliername", buySupplier.getName());
            }
            DepartMent departMent = getDepartmentByDeptid(supplierdeptid);
            if(null!=departMent){
                map.put("supplierdeptName", departMent.getName());
            }
            for(SysCode sysCode :codeList){
                //统计供应商 +科目 总费用
                Map condition = new HashMap();
                condition.putAll(pageMap.getCondition());
                condition.put("supplierid", supplierid);
                condition.put("subjectid", sysCode.getCode());
                PageMap pageMap2 = new PageMap();
                pageMap2.setCondition(condition);
                pageMap2.setDataSql(pageMap.getDataSql());
                pageMap2.setQuerySql(pageMap.getQuerySql());
                pageMap2.setPage(pageMap.getPage());
                pageMap2.setRows(pageMap.getRows());
                BigDecimal amount = journalSheetMapper.getAmountBySupplierAndSubjectid(pageMap2);
                if(null==amount){
                    amount = BigDecimal.ZERO;
                }
                map.put("subjectid"+sysCode.getCode(), amount);
                map.put("subjectname", sysCode.getCodename());

            }
        }
        PageData pageData = new PageData(journalSheetMapper.getStatisticsListCount(pageMap),list,pageMap);
        //当前合计
        Map footerMap = new HashMap();
        //总计
        Map footerMap1 = new HashMap();
        BigDecimal allLastAmount = new BigDecimal(0);
        if(codeList.size() != 0){
            for(SysCode sysCode :codeList){
                //统计供应商 +科目 总费用
                pageMap.getCondition().put("subjectid", sysCode.getCode());
                BigDecimal subjectAmount = journalSheetMapper.getStatisticsQuerySumBySubjectid(pageMap);
                if(null==subjectAmount){
                    subjectAmount = new BigDecimal(0);
                }
                allLastAmount = allLastAmount.add(subjectAmount);
                footerMap.put("subjectid"+sysCode.getCode(),subjectAmount);
                footerMap1.put("subjectid"+sysCode.getCode(), journalSheetMapper.getStatisticsAllSumBySubejctid(pageMap));
            }
        }

        BigDecimal totalAmount = journalSheetMapper.getStatisticsAllSum(pageMap);
        String approvalsql = getDataAccessRule("t_finance_approvalprice",null); //数据权限
        pageMap.setDataSql(approvalsql);
        //核准总金额
        BigDecimal totalApprovalAmount = journalSheetMapper.getTotalApprovalPrice(pageMap);
        //实际占用总金额
        BigDecimal totalRealAtamount = new BigDecimal(0);
        FundInput fundTotal = journalSheetMapper.getTotalFundAmountByPageMap(pageMap);
        if(null != fundTotal){
            totalRealAtamount = fundTotal.getActingmatamount().add(fundTotal.getAdvanceamount()).add(fundTotal.getPayableamount())
                    .add(fundTotal.getReceivableamount()).add(fundTotal.getStockamount());
        }
        footerMap1.put("suppliername", "全部合计");
        footerMap1.put("lastAmount", totalAmount);
        footerMap1.put("approvalAmount", totalApprovalAmount);
        footerMap1.put("realatamount", totalRealAtamount);
        footerMap1.put("settleamount", totalRealAtamount.subtract(totalAmount));

        //当前查询核准金额
        Map allreturnMap = journalSheetMapper.getStatisticsListSumData(pageMap);
        BigDecimal allApprovalAmount = (BigDecimal) allreturnMap.get("approvalAmount");
        if(null==allApprovalAmount){
            allApprovalAmount = BigDecimal.ZERO;
        }
        BigDecimal allRealAtmount = (BigDecimal) allreturnMap.get("realatamount");
        if(null==allApprovalAmount){
            allApprovalAmount = BigDecimal.ZERO;
        }
        footerMap.put("suppliername", "当前查询合计");
        footerMap.put("lastAmount", allLastAmount);
        footerMap.put("approvalAmount", allApprovalAmount);
        footerMap.put("realatamount", allRealAtmount);
        footerMap.put("settleamount", allRealAtmount.subtract(allLastAmount));
        List footerList = new ArrayList();
        footerList.add(footerMap);
        footerList.add(footerMap1);

        pageData.setFooter(footerList);
        return pageData;
    }

    @Override
    public Map addDRExpensesEntering(List<ExpensesEntering> list)
            throws Exception {
        Map returnmap = new HashMap();
        boolean flag = false;
        Map map = new HashMap();
        int success = 0;
        int failure = 0;
        String failStr = "",nosupplierids="",msg = "";
        for(ExpensesEntering expensesEntering : list){
            if(null!=expensesEntering && StringUtils.isNotEmpty(expensesEntering.getSupplierid())){
                if(StringUtils.isNotEmpty(expensesEntering.getId())){
                    map.put("id", expensesEntering.getId());
                }
                ExpensesEntering isExistExpensesEnter = journalSheetMapper.getExpensesEnteringDetail(map);
                if(null == isExistExpensesEnter){//不 存在该费用录入
                    //判断是否存在科目编码，若不存在，从编码表中获取科目编码，储存至对应的科目名称
                    if(StringUtils.isEmpty(expensesEntering.getSubjectid())){
                        if(StringUtils.isNotEmpty(expensesEntering.getSubjectname())){
                            List<SysCode> codeList =  getBaseSysCodeMapper().getSysCodeListForeign("subjectid");
                            for(SysCode sysCode:codeList){
                                if(expensesEntering.getSubjectname().equals(sysCode.getCodename())){
                                    expensesEntering.setSubjectid(sysCode.getCode());
                                }
                            }
                        }
                    }
                    BuySupplier buySupplier = buySupplierMapper.getBuySupplier(expensesEntering.getSupplierid());
                    if(null!=buySupplier){
                        expensesEntering.setSupplierdeptid(buySupplier.getBuydeptid());
                        if(StringUtils.isEmpty(expensesEntering.getSuppliername())){
                            expensesEntering.setSuppliername(buySupplier.getName());
                        }
                        SysUser sysUser = getSysUser();
                        expensesEntering.setAdduserid(sysUser.getUserid());
                        expensesEntering.setAddusername(sysUser.getName());
                        flag =  journalSheetMapper.addExpensesEntering(expensesEntering) > 0;
                        if(flag){
                            success ++;
                        }else{
                            failure ++;
                            failStr += expensesEntering.getSupplierid()+",";
                        }
                    }else{
                        if(StringUtils.isEmpty(nosupplierids)){
                            nosupplierids = expensesEntering.getSupplierid();
                        }else{
                            nosupplierids += "," + expensesEntering.getSupplierid();
                        }
                        msg = "供应商：" + nosupplierids + "不存在,请完善供应商档案。";
                    }
                }else{
                    failure ++;
                }
            }
        }
        returnmap.put("flag", flag);
        returnmap.put("success", success);
        returnmap.put("failure", failure);
        returnmap.put("failStr", failStr);
        returnmap.put("msg", msg);
        return returnmap;
    }

    @Override
    public PageData getStatisticsDetailList(PageMap pageMap) throws Exception {
        String sql = getDataAccessRule("t_finance_expensesentering",null); //数据权限
        pageMap.setDataSql(sql);
        List<ExpensesEntering> list = journalSheetMapper.getStatisticsDetailList(pageMap);
        BigDecimal totalamount  = BigDecimal.ZERO;
        for(ExpensesEntering expensesEntering : list){
            if(StringUtils.isNotEmpty(expensesEntering.getSupplierdeptid())){
                DepartMent departMent = departMentMapper.getDepartmentInfo(expensesEntering.getSupplierdeptid());
                if(null!=departMent){
                    expensesEntering.setSupplierdeptName(departMent.getName());
                }
            }
            BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(expensesEntering.getSupplierid());
            if(null!=buySupplier){
                expensesEntering.setSuppliername(buySupplier.getName());
            }
            totalamount = totalamount.add(expensesEntering.getSubjectexpenses());
        }
        PageData pageData = new PageData(journalSheetMapper.getStatisticsDetailCount(pageMap),list,pageMap);

        List<ExpensesEntering> footer = new ArrayList<ExpensesEntering>();
        ExpensesEntering sumExpensesEntering = new ExpensesEntering();
        sumExpensesEntering.setSuppliername("合计");
        sumExpensesEntering.setSubjectexpenses(totalamount);
        footer.add(sumExpensesEntering);

        pageData.setFooter(footer);
        return pageData;
    }
    @Override
    public boolean addApprovalPrice(String idsArr)
            throws Exception {
        String[] array = idsArr.split(",");
        int count = 0;
        for(int i=0;i<array.length;i++){
            ApprovalPrice approvalPrice = new ApprovalPrice();
            BuySupplier buySupplier = buySupplierMapper.getBuySupplier(array[i]);
            if(buySupplier != null){
                approvalPrice.setSupplierdeptid(buySupplier.getBuydeptid());
            }
            approvalPrice.setSupplierid(array[i]);
            if(journalSheetMapper.addApprovalPrice(approvalPrice)>0){
                count++;
            }
        }
        if(count == array.length){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteApprovalPrice(String id) throws Exception {
        return journalSheetMapper.deleteApprovalPrice(id) > 0;
    }

    @Override
    public boolean editApprovalPrice(ApprovalPrice approvalPrice)
            throws Exception {
        return journalSheetMapper.editApprovalPrice(approvalPrice) > 0;
    }

    @Override
    public PageData getApprovalPriceList(PageMap pageMap) throws Exception {
        String sql = getDataAccessRule("t_finance_approvalprice",null); //数据权限
        pageMap.setDataSql(sql);
        List<ApprovalPrice> list = journalSheetMapper.getApprovalPriceList(pageMap);
        for(ApprovalPrice approvalPrice:list){
            if(StringUtils.isNotEmpty(approvalPrice.getSupplierid())){//供应商
                BuySupplier buySupplier = buySupplierMapper.getBuySupplier(approvalPrice.getSupplierid());
                if(buySupplier != null){
                    approvalPrice.setSupplierName(buySupplier.getName());
                }
            }
            if(StringUtils.isNotEmpty(approvalPrice.getSupplierdeptid())){//供应商所属部门
                DepartMent departMent = departMentMapper.getDepartmentInfo(approvalPrice.getSupplierdeptid());
                if(null != departMent){
                    approvalPrice.setSupplierdeptName(departMent.getName());
                }
            }
        }
        PageData pageData = new PageData(journalSheetMapper.getApprovalPriceCount(pageMap),list,pageMap);
        return pageData;
    }

    @Override
    public ApprovalPrice getApprovalPriceDetail(String id) throws Exception {
        return journalSheetMapper.getApprovalPriceDetail(id);
    }

    @Override
    public List getSupplierListForApproval() throws Exception {
        return journalSheetMapper.getSupplierListForApproval();
    }

    /*-------------------------------代垫录入-----------------------------------------------*/
    @Override
    public boolean addReimburseInput(ReimburseInput reimburseInput)
            throws Exception {
        return journalSheetMapper.addReimburseInput(reimburseInput) > 0;
    }

    @Override
    public boolean deleteReimburseInput(String id) throws Exception {
        return journalSheetMapper.deleteReimburseInput(id) > 0;
    }

    @Override
    public boolean editReimburseInput(ReimburseInput reimburseInput)
            throws Exception {
        return journalSheetMapper.editReimburseInput(reimburseInput) > 0;
    }

    @Override
    public PageData getReimburseInputListPage(PageMap pageMap) throws Exception {
        String cols = getAccessColumnList("t_js_reimburseinput",null); //字段权限
        pageMap.setCols(cols);
        String sql = getDataAccessRule("t_js_reimburseinput",null); //数据权限
        pageMap.setDataSql(sql);
        List<ReimburseInput> list = journalSheetMapper.getReimburseInputListPage(pageMap);
        if(list.size() != 0){
            for(ReimburseInput reimburseInput :list){
                if(StringUtils.isNotEmpty(reimburseInput.getSupplierdeptid())){//所属部门
                    DepartMent departMent = departMentMapper.getDepartmentInfo(reimburseInput.getSupplierdeptid());
                    if(null!=departMent){
                        reimburseInput.setSupplierdeptName(departMent.getName());
                    }
                }
                if(StringUtils.isNotEmpty(reimburseInput.getSupplierid())){//供应商
                    BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(reimburseInput.getSupplierid());
                    if(null!=buySupplier){
                        reimburseInput.setSupplierName(buySupplier.getName());
                    }
                }
                if(StringUtils.isNotEmpty(reimburseInput.getBrandid())){//商品品牌
                    Brand brand = goodsMapper.getBrandInfo(reimburseInput.getBrandid());
                    if(null != brand){
                        reimburseInput.setBrandName(brand.getName());
                    }
                }
                if(StringUtils.isNotEmpty(reimburseInput.getPlanreimbursetype())){//计划收回方式
                    List<SysCode> codelist = getBaseSysCodeMapper().getSysCodeListForeign("reimbursetype");
                    if(codelist.size() != 0){
                        for(SysCode sysCode:codelist){
                            if(reimburseInput.getPlanreimbursetype().equals(sysCode.getCode())){
                                reimburseInput.setPlanreimbursetypeName(sysCode.getCodename());
                            }
                        }
                    }
                }
                if(StringUtils.isNotEmpty(reimburseInput.getReimbursetype())){//收回方式
                    List<SysCode> codelist = getBaseSysCodeMapper().getSysCodeListForeign("reimbursetype");
                    if(codelist.size() != 0){
                        for(SysCode sysCode:codelist){
                            if(reimburseInput.getReimbursetype().equals(sysCode.getCode())){
                                reimburseInput.setReimbursetypeName(sysCode.getCodename());
                            }
                        }
                    }
                }
                if(StringUtils.isNotEmpty(reimburseInput.getSubjectid())){//科目
                    List<ExpensesSort> subjectCodelist = getBaseFinanceMapper().getExpensesSortByStateList();
                    if(subjectCodelist.size() != 0){
                        for(ExpensesSort expensesSort:subjectCodelist){
                            if(reimburseInput.getSubjectid().equals(expensesSort.getId())){
                                reimburseInput.setSubjectName(expensesSort.getThisid());
                            }
                        }
                    }
                }
            }
        }

        //合计
        Map footerMap = new HashMap();
        footerMap.put("suppliername", "合计");
        ReimburseInput rInput = journalSheetMapper.getReimberseInputSums(pageMap);
        if(null != rInput){
            footerMap.put("planamount", rInput.getPlanamount());
            footerMap.put("takebackamount", rInput.getTakebackamount());
            footerMap.put("untakebackamount", rInput.getUntakebackamount());
            footerMap.put("actingmatamount", rInput.getActingmatamount());
            footerMap.put("surplusamount", rInput.getSurplusamount());
        }
        else{
            footerMap.put("planamount", "0.000000");
            footerMap.put("takebackamount", "0.000000");
            footerMap.put("untakebackamount", "0.000000");
            footerMap.put("actingmatamount", "0.000000");
            footerMap.put("surplusamount", "0.000000");
        }
        List footerList = new ArrayList();
        footerList.add(footerMap);
        PageData pageData = new PageData(journalSheetMapper.getReimburseInputListCount(pageMap),list,pageMap);
        pageData.setFooter(footerList);
        return pageData;
    }

    @Override
    public ReimburseInput getReimberseInputDetail(String id) throws Exception {
        Map map = getAccessColumnMap("t_js_reimburseinput", null);//加字段查看权限
        map.put("id", id);
        return journalSheetMapper.getReimberseInputDetail(map);
    }

    @Override
    public Map addDRReimburseInput(List<ReimburseInput> list)
            throws Exception {
        Map map = new HashMap();
        boolean flag = false;
        SysUser sysUser = getSysUser();
        if(null != list && list.size() != 0){
            for(ReimburseInput reimburseInput : list){
                reimburseInput.setAdduserid(sysUser.getUserid());
                reimburseInput.setAddusername(sysUser.getName());
                flag = journalSheetMapper.addReimburseInput(reimburseInput) > 0;
            }
        }
        else{
            map.put("msg", "导入的数据为空!");
        }
        map.put("flag", flag);
        return map;
    }

	/*-------------------------------代垫统计报表-----------------------------------------------*/

    @Override
    public PageData getRIStatisticsSheetFirstList(PageMap pageMap)
            throws Exception {
        String sql = getDataAccessRule("t_js_reimburseinput",null); //数据权限
        pageMap.setDataSql(sql);
        //代垫录入下的供应商列表
        List<ReimburseInput> supplierList = journalSheetMapper.getRISSheetAllSupplier(pageMap);
        List dataList = new ArrayList();
        //当前合计
        Map<String,Object> footerMap = new HashMap<String,Object>();
        //总计
        Map<String,Object> footerMap1 = new HashMap<String,Object>();
        String brandid = (String) pageMap.getCondition().get("brandid");
        String subjectid = (String) pageMap.getCondition().get("subjectid");
        String begintime = (String) pageMap.getCondition().get("begintime");
        String endtime = (String) pageMap.getCondition().get("endtime");
        if(StringUtils.isEmpty(begintime)){
            begintime = "null";
        }
        if(StringUtils.isEmpty(endtime)){
            endtime = "null";
        }
        if(StringUtils.isEmpty(brandid)){
            brandid = "null";
        }
        if(StringUtils.isEmpty(subjectid)){
            subjectid = "null";
        }
        //父级分类
        if(supplierList.size() != 0){
            for(ReimburseInput reimburseInput : supplierList){
                if(null != reimburseInput){
                    Map map = new HashMap();
                    map.put("id", reimburseInput.getSupplierid()+"_"+reimburseInput.getSupplierdeptid()+"_"+brandid+"_"+subjectid+"_"+begintime+"_"+endtime+"_First");
                    map.put("state", "closed");
                    map.put("parentId", "");
                    map.put("isshow", "1");
                    map.put("supplierid", reimburseInput.getSupplierid());
                    BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(reimburseInput.getSupplierid());
                    if(null != buySupplier){
                        map.put("suppliername", buySupplier.getName());
                    }
                    else{
                        map.put("suppliername", "其他");
                    }

                    map.put("supplierdeptid", reimburseInput.getSupplierdeptid());

                    //供应商所属部门名称
                    DepartMent departMent = departMentMapper.getDepartmentInfo(reimburseInput.getSupplierdeptid());
                    if(null!=departMent){
                        map.put("supplierdeptName", departMent.getName());
                    }

                    //根据供应商编码合计五项金额
                    BigDecimal planamount = new BigDecimal(0);//计划金额
                    BigDecimal takebackamount = new BigDecimal(0);//收回金额
                    BigDecimal untakebackamount = new BigDecimal(0);//未收回金额
                    BigDecimal actingmatamount = new BigDecimal(0);//代垫金额
                    BigDecimal surplusamount = new BigDecimal(0);//盈余金额
                    if(null != reimburseInput.getPlanamount()){
                        planamount = reimburseInput.getPlanamount();
                    }
                    if(null != reimburseInput.getTakebackamount()){
                        takebackamount = reimburseInput.getTakebackamount();
                    }
                    if(null != reimburseInput.getActingmatamount()){
                        actingmatamount = reimburseInput.getActingmatamount();
                    }
                    untakebackamount = planamount.subtract(takebackamount);
                    surplusamount = takebackamount.subtract(actingmatamount);
                    map.put("planamount", planamount);
                    map.put("takebackamount", takebackamount);
                    map.put("untakebackamount", untakebackamount);
                    map.put("actingmatamount", actingmatamount);
                    map.put("surplusamount", surplusamount);
                    dataList.add(map);
                }
            }
        }
        PageData pageData = new PageData(journalSheetMapper.getRISSheetAllSupplierCount(pageMap),dataList,pageMap);
        //列表行底合计金额显示
        ReimburseInput totalRI = journalSheetMapper.getRISSheetAllSum(pageMap);
        footerMap1.put("suppliername", "全部合计");
        BigDecimal planamount2 = new BigDecimal(0);//计划金额
        BigDecimal takebackamount2 = new BigDecimal(0);//收回金额
        BigDecimal untakebackamount2 = new BigDecimal(0);//未收回金额
        BigDecimal actingmatamount2 = new BigDecimal(0);//代垫金额
        BigDecimal surplusamount2 = new BigDecimal(0);//盈余金额
        if(null != totalRI){
            if(null != totalRI.getPlanamount()){
                planamount2 = totalRI.getPlanamount();
            }
            if(null != totalRI.getTakebackamount()){
                takebackamount2 = totalRI.getTakebackamount();
            }
            if(null != totalRI.getActingmatamount()){
                actingmatamount2 = totalRI.getActingmatamount();
            }
            untakebackamount2 = planamount2.subtract(takebackamount2);
            surplusamount2 = takebackamount2.subtract(actingmatamount2);
        }
        footerMap1.put("planamount", planamount2);
        footerMap1.put("takebackamount", takebackamount2);
        footerMap1.put("untakebackamount", untakebackamount2);
        footerMap1.put("actingmatamount", actingmatamount2);
        footerMap1.put("surplusamount", surplusamount2);

        ReimburseInput amountRI = journalSheetMapper.getReimberseInputSums(pageMap);
        footerMap.put("suppliername", "当前查询合计");
        BigDecimal planamount3 = new BigDecimal(0);//计划金额
        BigDecimal takebackamount3 = new BigDecimal(0);//收回金额
        BigDecimal untakebackamount3 = new BigDecimal(0);//未收回金额 = 计划金额 - 收回金额
        BigDecimal actingmatamount3 = new BigDecimal(0);//代垫金额
        BigDecimal surplusamount3 = new BigDecimal(0);//盈余金额 = 收回金额 - 代垫金额
        if(null != amountRI){
            if(null != amountRI.getPlanamount()){
                planamount3 = amountRI.getPlanamount();
            }
            if(null != amountRI.getTakebackamount()){
                takebackamount3 = amountRI.getTakebackamount();
            }
            if(null != amountRI.getActingmatamount()){
                actingmatamount3 = amountRI.getActingmatamount();
            }
            untakebackamount3 = planamount3.subtract(takebackamount3);
            surplusamount3 = takebackamount3.subtract(actingmatamount3);
        }
        footerMap.put("planamount", planamount3);
        footerMap.put("takebackamount", takebackamount3);
        footerMap.put("untakebackamount", untakebackamount3);
        footerMap.put("actingmatamount", actingmatamount3);
        footerMap.put("surplusamount", surplusamount3);
        //列表行底显示
        List footerList = new ArrayList();
        footerList.add(footerMap);
        footerList.add(footerMap1);
        pageData.setFooter(footerList);
        return pageData;
    }

    @Override
    public List getRIStatisticsSheetSecondList(PageMap pageMap)
            throws Exception {
        String sql = getDataAccessRule("t_js_reimburseinput",null); //数据权限
        pageMap.setDataSql(sql);
        //代垫录入指定供应商下的所有品牌
        List<ReimburseInput> brandList = journalSheetMapper.getRISSheetAllBrandBySupplierid(pageMap);
        List dataList = new ArrayList();

        String supplierid = (String) pageMap.getCondition().get("supplierid");
        String supplierdeptid = (String) pageMap.getCondition().get("supplierdeptid");
        String subjectid = (String) pageMap.getCondition().get("subjectid");
        String begintime = (String) pageMap.getCondition().get("begintime");
        String endtime = (String) pageMap.getCondition().get("endtime");
        if(StringUtils.isEmpty(supplierdeptid)){
            supplierdeptid = "null";
        }
        if(StringUtils.isEmpty(begintime)){
            begintime = "null";
        }
        if(StringUtils.isEmpty(endtime)){
            endtime = "null";
        }
        if(StringUtils.isEmpty(subjectid)){
            subjectid = "null";
        }

        //父级分类（品牌）
        if(brandList.size() != 0){
            for(ReimburseInput RIBrand : brandList){
                if(null != RIBrand){
                    Map map2 = new HashMap();
                    map2.put("id", supplierid+"_"+supplierdeptid+"_"+RIBrand.getBrandid()+"_"+subjectid+"_"+begintime+"_"+endtime+"_Second");
                    map2.put("state", "closed");
                    map2.put("parentId", pageMap.getCondition().get("secondPid"));
                    map2.put("supplierid", RIBrand.getSupplierid());
                    map2.put("isshow", "0");
                    Brand brand = getGoodsMapper().getBrandInfo(RIBrand.getBrandid());
                    if(null != brand){
                        map2.put("suppliername", brand.getName());
                    }
                    else{
                        map2.put("suppliername", "其他");
                    }

                    map2.put("supplierdeptid", RIBrand.getSupplierdeptid());

                    //供应商所属部门名称
                    DepartMent departMent = departMentMapper.getDepartmentInfo(RIBrand.getSupplierdeptid());
                    if(null!=departMent){
                        map2.put("supplierdeptName", departMent.getName());
                    }

                    map2.put("brandid", RIBrand.getBrandid());//品牌

                    //根据供应商编码合计五项金额
                    Map condition = new HashMap();
                    condition.putAll(pageMap.getCondition());
                    condition.put("brandid", RIBrand.getBrandid());
                    PageMap pageMap2 = new PageMap();
                    pageMap2.setCondition(condition);
                    pageMap2.setDataSql(pageMap.getDataSql());
                    pageMap2.setQuerySql(pageMap.getQuerySql());
                    pageMap2.setPage(pageMap.getPage());
                    pageMap2.setRows(pageMap.getRows());
                    ReimburseInput rInput = journalSheetMapper.getReimberseInputSums(pageMap2);
                    BigDecimal planamount = new BigDecimal(0);//计划金额
                    BigDecimal takebackamount = new BigDecimal(0);//收回金额
                    BigDecimal untakebackamount = new BigDecimal(0);//未收回金额
                    BigDecimal actingmatamount = new BigDecimal(0);//代垫金额
                    BigDecimal surplusamount = new BigDecimal(0);//盈余金额
                    if(null != rInput){
                        if(null != rInput.getPlanamount()){
                            planamount = rInput.getPlanamount();
                        }
                        if(null != rInput.getTakebackamount()){
                            takebackamount = rInput.getTakebackamount();
                        }
                        if(null != rInput.getActingmatamount()){
                            actingmatamount = rInput.getActingmatamount();
                        }
                    }
                    untakebackamount = planamount.subtract(takebackamount);
                    surplusamount = takebackamount.subtract(actingmatamount);
                    map2.put("planamount", planamount);
                    map2.put("takebackamount", takebackamount);
                    map2.put("untakebackamount", untakebackamount);
                    map2.put("actingmatamount", actingmatamount);
                    map2.put("surplusamount", surplusamount);
                    dataList.add(map2);
                }
            }
        }
        return dataList;
    }

    @Override
    public List getRIStatisticsSheetThirdList(PageMap pageMap)
            throws Exception {
        String sql = getDataAccessRule("t_js_reimburseinput",null); //数据权限
        pageMap.setDataSql(sql);
        //代垫录入指定供应商下所指定品牌下的所有科目
        List<ReimburseInput> subjectList = journalSheetMapper.getRISSheetAllBrandBySupplieridAndBrandid(pageMap);
        List dataList = new ArrayList();

        String supplierid = (String) pageMap.getCondition().get("supplierid");
        String supplierdeptid = (String) pageMap.getCondition().get("supplierdeptid");
        String brandid = (String) pageMap.getCondition().get("brandid");
        String begintime = (String) pageMap.getCondition().get("begintime");
        String endtime = (String) pageMap.getCondition().get("endtime");
        if(StringUtils.isEmpty(supplierdeptid)){
            supplierdeptid = "null";
        }
        if(StringUtils.isEmpty(begintime)){
            begintime = "null";
        }
        if(StringUtils.isEmpty(endtime)){
            endtime = "null";
        }

        //父级分类（科目）
        if(subjectList.size() != 0){
            for(ReimburseInput RISubject : subjectList){
                if(null != RISubject){
                    Map map2 = new HashMap();
                    map2.put("id", supplierid+"_"+supplierdeptid+"_"+brandid+"_"+RISubject.getSubjectid()+"_"+begintime+"_"+endtime+"_Third");
                    map2.put("state", "closed");
                    map2.put("parentId", pageMap.getCondition().get("thirdPid"));
                    map2.put("supplierid", RISubject.getSupplierid());
                    map2.put("isshow", "0");
                    List<ExpensesSort> subjectCodelist = getBaseFinanceMapper().getExpensesSortByStateList();
                    for(ExpensesSort expensesSort : subjectCodelist){
                        if(RISubject.getSubjectid().equals(expensesSort.getId())){
                            map2.put("subjectid", RISubject.getSubjectid());
                            map2.put("suppliername", expensesSort.getThisname());
                        }
                    }

                    map2.put("supplierdeptid", RISubject.getSupplierdeptid());

                    //供应商所属部门名称
                    DepartMent departMent = departMentMapper.getDepartmentInfo(RISubject.getSupplierdeptid());
                    if(null!=departMent){
                        map2.put("supplierdeptName", departMent.getName());
                    }

                    //根据供应商编码合计五项金额
                    Map condition = new HashMap();
                    condition.putAll(pageMap.getCondition());
                    condition.put("subjectid", RISubject.getSubjectid());
                    PageMap pageMap2 = new PageMap();
                    pageMap2.setCondition(condition);
                    pageMap2.setDataSql(pageMap.getDataSql());
                    pageMap2.setQuerySql(pageMap.getQuerySql());
                    pageMap2.setPage(pageMap.getPage());
                    pageMap2.setRows(pageMap.getRows());
                    ReimburseInput rInput = journalSheetMapper.getReimberseInputSums(pageMap2);
                    BigDecimal planamount = new BigDecimal(0);//计划金额
                    BigDecimal takebackamount = new BigDecimal(0);//收回金额
                    BigDecimal untakebackamount = new BigDecimal(0);//未收回金额
                    BigDecimal actingmatamount = new BigDecimal(0);//代垫金额
                    BigDecimal surplusamount = new BigDecimal(0);//盈余金额
                    if(null != rInput){
                        if(null != rInput.getPlanamount()){
                            planamount = rInput.getPlanamount();
                        }
                        if(null != rInput.getTakebackamount()){
                            takebackamount = rInput.getTakebackamount();
                        }
                        if(null != rInput.getActingmatamount()){
                            actingmatamount = rInput.getActingmatamount();
                        }
                    }
                    untakebackamount = planamount.subtract(takebackamount);
                    surplusamount = takebackamount.subtract(actingmatamount);
                    map2.put("planamount", planamount);
                    map2.put("takebackamount", takebackamount);
                    map2.put("untakebackamount", untakebackamount);
                    map2.put("actingmatamount", actingmatamount);
                    map2.put("surplusamount", surplusamount);
                    dataList.add(map2);
                }
            }
        }
        return dataList;
    }

    @Override
    public List getRIStatisticsSheetDetailList(Map map)
            throws Exception {
        PageMap pageMap = new PageMap();
        String sql = getDataAccessRule("t_js_capitalinput",null); //数据权限
        pageMap.setDataSql(sql);
        pageMap.setCondition(map);
        List<ReimburseInput> list = journalSheetMapper.getRISSheetDetailList(pageMap);
        List dataList = new ArrayList();
        String begintime = (String) pageMap.getCondition().get("begintime");
        String endtime = (String) pageMap.getCondition().get("endtime");
        if(StringUtils.isEmpty(begintime)){
            begintime="null";
        }
        if(StringUtils.isEmpty(endtime)){
            endtime="null";
        }
        for(ReimburseInput detailRI:list){
            if(null != detailRI){
                Map objectmap = new HashMap();
                objectmap.put("id", detailRI.getId());
                objectmap.put("state", "open");
                objectmap.put("parentId", map.get("endPid"));
                objectmap.put("supplierid", detailRI.getSupplierid());//供应商编码
                objectmap.put("isshow", "0");
                BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(detailRI.getSupplierid());
                if(null!=buySupplier){
                    objectmap.put("suppliername", buySupplier.getName());//供应商名称
                }else{
                    objectmap.put("suppliername", "其他");
                }
                objectmap.put("supplierdeptid", detailRI.getSupplierdeptid());//所属部门
                objectmap.put("businessdate", detailRI.getBusinessdate());//业务日期
                objectmap.put("planreimbursetime", detailRI.getPlanreimbursetime());//计划收回时间
                DepartMent departMent = departMentMapper.getDepartmentInfo(detailRI.getSupplierdeptid());
                if(null!=departMent){
                    objectmap.put("supplierdeptName", departMent.getName());//所属部门名称
                }
                objectmap.put("subjectid", detailRI.getSubjectid());//科目编码

                objectmap.put("brandid", detailRI.getBrandid());//品牌编码
                Brand brand = getGoodsMapper().getBrandInfo(detailRI.getBrandid());
                if(null != brand){
                    objectmap.put("brandName", brand.getName());//品牌名称
                }

                objectmap.put("reimbursetype", detailRI.getReimbursetype());//收回方式
                objectmap.put("planreimbursetype", detailRI.getPlanreimbursetype());//计划收回方式
                List<SysCode> sysCodeList = getBaseSysCodeMapper().getSysCodeListForeign("reimbursetype");
                if(sysCodeList.size() != 0){
                    for(SysCode sysCode : sysCodeList){
                        if(StringUtils.isNotEmpty(detailRI.getReimbursetype())){
                            if(detailRI.getReimbursetype().equals(sysCode.getCode())){
                                objectmap.put("reimbursetypeName", sysCode.getCodename());
                            }
                        }
                        if(StringUtils.isNotEmpty(detailRI.getPlanreimbursetype())){
                            if(detailRI.getPlanreimbursetype().equals(sysCode.getCode())){
                                objectmap.put("planreimbursetypeName", sysCode.getCodename());
                            }
                        }
                    }
                }
                BigDecimal planamount = new BigDecimal(0);//计划金额
                BigDecimal takebackamount = new BigDecimal(0);//收回金额
                BigDecimal untakebackamount = new BigDecimal(0);//未收回金额
                BigDecimal actingmatamount = new BigDecimal(0);//代垫金额
                BigDecimal surplusamount = new BigDecimal(0);//盈余金额
                if(null != detailRI.getPlanamount()){
                    planamount = detailRI.getPlanamount();
                }
                if(null != detailRI.getTakebackamount()){
                    takebackamount = detailRI.getTakebackamount();
                }
                if(null != detailRI.getActingmatamount()){
                    actingmatamount = detailRI.getActingmatamount();
                }
                //untakebackamount = planamount.subtract(takebackamount);//未收回金额
                //surplusamount = takebackamount.subtract(actingmatamount);//盈余金额
                objectmap.put("planamount", planamount);
                objectmap.put("takebackamount", takebackamount);
                objectmap.put("actingmatamount", actingmatamount);
                objectmap.put("untakebackamount", untakebackamount);
                objectmap.put("surplusamount", surplusamount);
                dataList.add(objectmap);
            }
        }
        return dataList;
    }


    /*------------------------资金录入(t_js_fundinput)------------------------------------*/
    @Override
    public Map addDRFundInput(List<FundInput> list) throws Exception {
        Map map = new HashMap();
        boolean flag = false;
        int success = 0;
        int failure = 0;
        String failStr = "";
        for(FundInput fundInput :list){
            String changeDate = CommonUtils.getNextDateByDays(CommonUtils.stringToDate(fundInput.getBusinessdate()),7);
            String newDate = CommonUtils.getTodayDataStr();
			if(CommonUtils.compareDate(changeDate,newDate) >= 0){
                boolean checkflag = checkFundInputByMap(fundInput.getSupplierid(),fundInput.getBusinessdate());
                if(!checkflag){
                    if(journalSheetMapper.checkFundInputBySupplierid(fundInput.getSupplierid()) > 0){
                        if(journalSheetMapper.editFundInputBySupplierid(fundInput.getSupplierid())>0){
                            fundInput = computeFundInputSumAmount(fundInput);
                            flag =  journalSheetMapper.addFundInput(fundInput) > 0;
                        }
                    }
                    else{
                        fundInput = computeFundInputSumAmount(fundInput);
                        flag = journalSheetMapper.addFundInput(fundInput) > 0;
                    }
                    if(flag){
                        success ++;
                    }else{
                        failure ++;
                        failStr += fundInput.getSupplierid()+",";
                    }
                }else{//已存在该业务日期的供应商资金录入或业务日期小于最新日期，不允许新增!
                    Map map1 = null;
                    map1 = new HashMap();
                    map1.put("supplierid",fundInput.getSupplierid());
                    map1.put("businessdate",fundInput.getBusinessdate());
                    map1.put("state","1");
                    FundInput fundInput1 = journalSheetMapper.getFundInputDetail(map1);
                    if(null != fundInput1){
                        fundInput.setId(fundInput1.getId());
                        fundInput = computeFundInputSumAmount(fundInput);
                        flag = journalSheetMapper.editFundInput(fundInput) > 0;
                        if(flag){
                            success ++;
                        }else{
                            failure ++;
                            failStr += fundInput.getSupplierid()+",";
                        }
                    }else{
                        failure ++;
                        failStr += fundInput.getSupplierid()+",业务日期"+fundInput.getBusinessdate()+"小于最新日期，不允许导入;<br>";
                    }
                }
			}else{
				failure ++;
				failStr += fundInput.getSupplierid()+",业务日期"+fundInput.getBusinessdate()+"超出7天范围;<br>";
			}
        }
        map.put("flag", flag);
        map.put("success", success);
        map.put("failure", failure);
        map.put("failStr", failStr);
        return map;
    }

    /**
     * 计算累计金额
     * @param fundInput
     * @return
     */
    private FundInput computeFundInputSumAmount(FundInput fundInput){
        if(null != fundInput){
            //合计金额=预付金额+库存金额+应收金额+代垫金额+应付金额(负数)
            BigDecimal advanceamount = null != fundInput.getAdvanceamount() ? fundInput.getAdvanceamount() : BigDecimal.ZERO;
            BigDecimal stockamount = null != fundInput.getStockamount() ? fundInput.getStockamount() : BigDecimal.ZERO;
            BigDecimal receivableamount = null != fundInput.getReceivableamount() ? fundInput.getReceivableamount() : BigDecimal.ZERO;
            BigDecimal actingmatamount = null != fundInput.getActingmatamount() ? fundInput.getActingmatamount() : BigDecimal.ZERO;
            BigDecimal payableamount = null != fundInput.getPayableamount() ? fundInput.getPayableamount() : BigDecimal.ZERO;
            if(payableamount.compareTo(BigDecimal.ZERO) > 0){
                payableamount = payableamount.negate();
            }
            BigDecimal totalamount = advanceamount.add(stockamount).add(receivableamount).add(actingmatamount).add(payableamount);

            //上次累计资金录入信息
            FundInput lastFundInput = journalSheetMapper.getLastFundInputSum(fundInput.getSupplierid(), fundInput.getBusinessdate());
            BigDecimal lastsumactingmat = BigDecimal.ZERO;
            BigDecimal lastremittancerecovery = BigDecimal.ZERO;
            BigDecimal lastgoodsrecovery = BigDecimal.ZERO;
            if(null != lastFundInput){
                lastsumactingmat = lastFundInput.getCurrentactingmat();
                lastremittancerecovery = lastFundInput.getRemittancerecovery();
                lastgoodsrecovery = lastFundInput.getGoodsrecovery();
            }

            //累计代垫=上期累计代垫+本期代垫
            BigDecimal currentactingmat = null != fundInput.getCurrentactingmat() ? fundInput.getCurrentactingmat() : BigDecimal.ZERO;
            BigDecimal sumactingmat = lastsumactingmat.add(currentactingmat);

            //累计已收=上期汇款收回+上期货补收回+本期汇款收回+本期货补收回
            BigDecimal remittancerecovery = null != fundInput.getRemittancerecovery() ? fundInput.getRemittancerecovery() : BigDecimal.ZERO;
            BigDecimal goodsrecovery = null != fundInput.getGoodsrecovery() ? fundInput.getGoodsrecovery() : BigDecimal.ZERO;
            BigDecimal sumreceivable = lastremittancerecovery.add(lastgoodsrecovery).add(remittancerecovery).add(goodsrecovery);

            //累计未收=累计代垫-累计已收
            BigDecimal sumnorec = sumactingmat.subtract(sumreceivable);

            //代垫未收=累计未收（累计代垫-累计已收）
            fundInput.setNorecactingmat(sumnorec);

            //累计金额=合金金额-库存折差+代垫未收-费用未收
            BigDecimal stockdiscount = null != fundInput.getStockdiscount() ? fundInput.getStockdiscount() : BigDecimal.ZERO;
            BigDecimal norecactingmat = null != fundInput.getNorecactingmat() ? fundInput.getNorecactingmat() : BigDecimal.ZERO;
            BigDecimal norecexpenses = null != fundInput.getNorecexpenses() ? fundInput.getNorecexpenses() : BigDecimal.ZERO;
            BigDecimal sumamount = totalamount.subtract(stockdiscount).add(norecactingmat).subtract(norecexpenses);

            fundInput.setPayableamount(payableamount);
            fundInput.setTotalamount(totalamount);
            fundInput.setSumamount(sumamount);
            fundInput.setSumactingmat(sumactingmat);
            fundInput.setSumreceivable(sumreceivable);
            fundInput.setSumnorec(sumnorec);
        }
        return fundInput;
    }

    @Override
    public boolean addFundInput(FundInput fundInput) throws Exception {
        journalSheetMapper.editFundInputBySupplierid(fundInput.getSupplierid());

        SysUser sysUser = getSysUser();
        fundInput.setAdduserid(sysUser.getUserid());
        fundInput.setAddusername(sysUser.getName());
        fundInput = computeFundInputSumAmount(fundInput);
        return journalSheetMapper.addFundInput(fundInput) > 0;
    }

    @Override
    public boolean deleteFundInput(String id) throws Exception {
        return journalSheetMapper.deleteFundInput(id) > 0;
    }

	@Override
	public boolean editFundInput(FundInput fundInput) throws Exception {
		boolean flag = false;
		if(null!=fundInput){
			Map map = new HashMap();
			map.put("id", fundInput.getId());
			FundInput oldFundInput = journalSheetMapper.getFundInputDetail(map);
			//当前日期
            String newDate = CommonUtils.dateStringChange(fundInput.getBusinessdate(),"yyyy-MM-dd");
            //修改日期在7天范围内修改，否则新增
            String changeDate = CommonUtils.getNextDateByDays(CommonUtils.stringToDate(oldFundInput.getBusinessdate()), 7);
            if(CommonUtils.compareDate(changeDate,newDate) >= 0){//修改
                fundInput = computeFundInputSumAmount(fundInput);
				int i = journalSheetMapper.editFundInput(fundInput);
				flag = i>0;
			}else{//新增
                journalSheetMapper.editFundInputBySupplierid(fundInput.getSupplierid());

                fundInput = computeFundInputSumAmount(fundInput);
				fundInput.setId(null);
				flag = journalSheetMapper.addFundInput(fundInput) > 0;
			}
		}
		return flag;
	}

    @Override
    public FundInput getFundInputDetail(String id) throws Exception {
        Map map = getAccessColumnMap("t_js_fundinput", null);//加字段查看权限
        map.put("id", id);
        return journalSheetMapper.getFundInputDetail(map);
    }

    @Override
    public PageData getFundStatisticsSheetList(PageMap pageMap) throws Exception {
        String sql = getDataAccessRule("t_js_fundinput","t"); //数据权限
        pageMap.setDataSql(sql);
        List<FundInput> list = journalSheetMapper.getFundStatisticsSheetList(pageMap);
        if(list.size() != 0){
            for(FundInput fundInput : list){
                if(null != fundInput){
                    if(StringUtils.isNotEmpty(fundInput.getSupplierdeptid())){
                        DepartMent departMent = departMentMapper.getDepartmentInfo(fundInput.getSupplierdeptid());
                        if(null!=departMent){
                            fundInput.setSupplierdeptName(departMent.getName());
                        }
                    }
                    if(StringUtils.isNotEmpty(fundInput.getSupplierid())){
                        BuySupplier buySupplier = buySupplierMapper.getBuySupplier(fundInput.getSupplierid());
                        if(null!=buySupplier){
                            fundInput.setSupplierName(buySupplier.getName());
                        }
                    }
                }
            }
        }
        //合计
        FundInput FITotleAmount = journalSheetMapper.getFundStatisticsSheetListSum(pageMap);
        if(null == FITotleAmount){
            FITotleAmount = new FundInput();
        }
        FITotleAmount.setSupplierName("合计");
        List footerList = new ArrayList();
        footerList.add(FITotleAmount);
        PageData pageData = new PageData(journalSheetMapper.getFundStatisticsSheetListCount(pageMap),list,pageMap);
        pageData.setFooter(footerList);
        return pageData;
    }

    @Override
    public PageData getFundInputListPage(PageMap pageMap) throws Exception {
        String cols = getAccessColumnList("t_js_fundinput",null); //字段权限
        pageMap.setCols(cols);
        String sql = getDataAccessRule("t_js_fundinput",null); //数据权限
        pageMap.setDataSql(sql);
        List<FundInput> list = journalSheetMapper.getFundInputListPage(pageMap);
        if(list.size() != 0){
            for(FundInput fundInput : list){
                if(null != fundInput){
                    if(StringUtils.isNotEmpty(fundInput.getSupplierdeptid())){
                        DepartMent departMent = departMentMapper.getDepartmentInfo(fundInput.getSupplierdeptid());
                        if(null!=departMent){
                            fundInput.setSupplierdeptName(departMent.getName());
                        }
                    }
                    if(StringUtils.isNotEmpty(fundInput.getSupplierid())){
                        BuySupplier buySupplier = buySupplierMapper.getBuySupplier(fundInput.getSupplierid());
                        if(null!=buySupplier){
                            fundInput.setSupplierName(buySupplier.getName());
                        }
                    }
                    if(StringUtils.isNotEmpty(fundInput.getState())){
                        List<SysCode> codeList = getBaseSysCodeMapper().getSysCodeListForeign("state");
                        if(codeList.size() != 0){
                            for(SysCode sysCode : codeList){
                                if(fundInput.getState().equals(sysCode.getCode())){
                                    fundInput.setStateName(sysCode.getCodename());
                                }
                            }
                        }
                    }
                }
            }
        }
        //合计
        FundInput FITotleAmount = journalSheetMapper.getFundInputSum(pageMap);
        if(null == FITotleAmount){
            FITotleAmount = new FundInput();
        }
        FITotleAmount.setSupplierName("合计");
        List footerList = new ArrayList();
        footerList.add(FITotleAmount);
        PageData pageData = new PageData(journalSheetMapper.getFundInputListCount(pageMap),list,pageMap);
        pageData.setFooter(footerList);
        return pageData;
    }

    /*-------------------------------代垫录入-----------------------------------------------*/
    @Override

    public boolean addMatcostsInput(MatcostsInput matcostsInput)
            throws Exception {
    	return addMatcostsInput(matcostsInput, true);
    }
    @Override
    public boolean addMatcostsInput(MatcostsInput matcostsInput,boolean matblance)
            throws Exception {
        Date date=CommonUtils.stringToDate(matcostsInput.getBusinessdate(),"yyyy-MM-dd");
        String duefromdate = getSupplierDateBySettlement(date, matcostsInput.getSupplierid(), matcostsInput.getBrandid());
        matcostsInput.setDuefromdate(duefromdate);
        if (isAutoCreate("t_js_matcostsinput")) {
            // 获取自动编号
            matcostsInput.setId(getAutoCreateSysNumbderForeign(matcostsInput, "t_js_matcostsinput"));
        }else{
            matcostsInput.setId(null);
        }
        matcostsInput.setBranchaccount(BigDecimal.ZERO);
        matcostsInput.setActingmatamount(BigDecimal.ZERO);
        if(null==matcostsInput.getFactoryamount()){
            matcostsInput.setFactoryamount(BigDecimal.ZERO);
        }
        if(null==matcostsInput.getHtpayamount()){
            matcostsInput.setHtpayamount(BigDecimal.ZERO);
        }
        matcostsInput.setBranchaccount(matcostsInput.getFactoryamount().subtract(matcostsInput.getHtpayamount()));
        if(null==matcostsInput.getReimburseamount()){
            matcostsInput.setReimburseamount(BigDecimal.ZERO);
        }
        matcostsInput.setActingmatamount(matcostsInput.getFactoryamount().subtract(matcostsInput.getReimburseamount()));
        matcostsInput.setRemainderamount(matcostsInput.getFactoryamount().subtract(matcostsInput.getReimburseamount()));
		boolean flag= journalSheetMapper.insertMatcostsInput(matcostsInput) > 0;
		
		if(flag && matblance 
				&& null!=matcostsInput.getExpense() 
				&& BigDecimal.ZERO.compareTo(matcostsInput.getExpense())!=0
				&& StringUtils.isNotEmpty(matcostsInput.getCustomerid())){
			/**
			 * 添加 客户应付费用
			 */
			// 1：客户应付费用
			addCustomerCostPayByMatcost(matcostsInput);
			
		}
		return flag;
    }

    @Override
    public boolean deleteMatcostsInput(String id) throws Exception {
        Map map=new HashMap();
        String dataSql = getDataAccessRule("t_js_matcostsinput",null); //数据权限
        if(null!=dataSql && !"".equals(dataSql.trim())){
            map.put("dataAuthSql", dataSql);
        }
        map.put("id", id);
        map.put("deletebilltype", "1");	// 代垫
        map.put("deletesource", "1");	// 可删除来源
        boolean flag=journalSheetMapper.deleteMatcostsInputByAuth(map) > 0;
        
        if(flag){
        	// 删除对应的客户应付费用
			deleteCustomerCostPayByMatcost(id);
        }
        return flag;
    }
    @Override
    public Map deleteMatcostsInputMore(String idarrs)throws Exception{
        Map map=new HashMap();
        int iSuccess=0;
        int iFailure=0;
        int iNohandle=0;

        if(null==idarrs || "".equals(idarrs.trim())){

            map.put("flag", false);
            map.put("isuccess", iSuccess);
            map.put("ifailure", iFailure);
            return map;
        }
        String[] idarr=idarrs.trim().split(",");
        String dataSql = getDataAccessRule("t_js_matcostsinput",null); //数据权限
        Map delMap=new HashMap();
        if(null!=dataSql && !"".equals(dataSql.trim())){
            delMap.put("dataAuthSql", dataSql);
        }
        for(String id : idarr){
            if(null==id || "".equals(id.trim())){
                continue;
            }
            delMap.put("id", id);
            delMap.put("deletebilltype", "1");	// 代垫
            delMap.put("deletesource", "1");	// 可删除来源
            if(journalSheetMapper.deleteMatcostsInputByAuth(delMap)>0){        	

				// 删除对应的客户应付费用
				deleteCustomerCostPayByMatcost(id);
            	
                iSuccess=iSuccess+1;
            }else{
                iFailure=iFailure+1;
            }
        }
        map.clear();
        if(iSuccess>0){
            map.put("flag", true);
        }else{
            map.put("flag", false);
        }
        map.put("isuccess", iSuccess);
        map.put("ifailure", iFailure);
        return map;
    }

    @Override
    public boolean editMatcostsInput(MatcostsInput matcostsInput)
            throws Exception {
        if(null==matcostsInput){
            return false;
        }
        MatcostsInput oldData=journalSheetMapper.getMatcostsInput(matcostsInput.getId());
        if(null==oldData || "1".equals(oldData.getIswriteoff()) || "2".equals(oldData.getBilltype())){
            return false;
        }
        //红冲标志更新不改变
        matcostsInput.setHcflag(null);
        
        matcostsInput.setBranchaccount(BigDecimal.ZERO);
        matcostsInput.setActingmatamount(BigDecimal.ZERO);
        if(null==matcostsInput.getFactoryamount()){
            matcostsInput.setFactoryamount(BigDecimal.ZERO);
        }
        if(null==matcostsInput.getHtpayamount()){
            matcostsInput.setHtpayamount(BigDecimal.ZERO);
        }
        matcostsInput.setBranchaccount(matcostsInput.getFactoryamount().subtract(matcostsInput.getHtpayamount()));
        if(null==matcostsInput.getReimburseamount()){
            matcostsInput.setReimburseamount(BigDecimal.ZERO);
        }
        matcostsInput.setActingmatamount(matcostsInput.getFactoryamount().subtract(matcostsInput.getReimburseamount()));
        matcostsInput.setRemainderamount(matcostsInput.getFactoryamount().subtract(matcostsInput.getReimburseamount()));
        Date date=CommonUtils.stringToDate(matcostsInput.getBusinessdate(),"yyyy-MM-dd");
        String duefromdate = getSupplierDateBySettlement(date, matcostsInput.getSupplierid(), matcostsInput.getBrandid());
        matcostsInput.setDuefromdate(duefromdate);
        boolean flag= journalSheetMapper.updateMatcostsInput(matcostsInput) > 0;
        
        if(flag){        	

        	//添加新数据
        	if( StringUtils.isNotEmpty(matcostsInput.getCustomerid())){
				/**
				 * 添加 客户应付费用
				 */
        		matcostsInput.setAdduserid(oldData.getAdduserid());
        		matcostsInput.setSourcefrome(oldData.getSourcefrome());
        		updateCustomerCostPayByMatcost(matcostsInput);
			
        	}
    	}
        return flag;
    }
    /**
     * 在代垫处添加客户应付
     * @param matcostsInput
     * @return
     * @throws Exception
     * @author zhanghonghui 
     * @date 2015年5月14日
     */
    private boolean addCustomerCostPayByMatcost(MatcostsInput matcostsInput) throws Exception{
    	CustomerCostPayable customerCostPayable = new CustomerCostPayable();
		customerCostPayable.setSourcefrom("1");  //代垫
		customerCostPayable.setBillno(matcostsInput.getId());
		customerCostPayable.setCustomerid(matcostsInput.getCustomerid());
		customerCostPayable.setSupplierid(matcostsInput.getSupplierid());
		Customer customer = getCustomerByID(matcostsInput.getCustomerid());
		if(null!=customer){
			customerCostPayable.setPcustomerid(customer.getPid());
			if("0".equals(customer.getIslast())){
				customerCostPayable.setPcustomerid(matcostsInput.getCustomerid());
			}
		}
		if("0".equals(matcostsInput.getSourcefrome()) || "1".equals(matcostsInput.getSourcefrome())){
			customerCostPayable.setBusinessdate(matcostsInput.getBusinessdate());
		}else{
			//业务日期随着代垫的业务日期
			customerCostPayable.setBusinessdate(CommonUtils.getTodayDataStr());
		}
		customerCostPayable.setIspay("0");
		customerCostPayable.setExpensesort(matcostsInput.getSubjectid());
		customerCostPayable.setOaid(matcostsInput.getOaid());
		customerCostPayable.setBilltype("1");	//单据类型 1：客户应付费用；2：客户支付费用
		customerCostPayable.setRelateoaid(matcostsInput.getOaid());
        customerCostPayable.setApplyuserid(matcostsInput.getAdduserid());
        if(StringUtils.isNotEmpty(matcostsInput.getAdduserid())){
        	SysUser sysUser=getSysUserById(matcostsInput.getAdduserid());
        	if(null!=sysUser){                    
                customerCostPayable.setApplydeptid(sysUser.getDepartmentid());
        	}
        }
        customerCostPayable.setDeptid(matcostsInput.getSupplierdeptid());
        if("1".equals(matcostsInput.getHcflag())){
        	customerCostPayable.setHcflag(matcostsInput.getHcflag());
        }else{
        	customerCostPayable.setHcflag("0");
        }
		//费用金额
		customerCostPayable.setAmount(matcostsInput.getExpense()==null?BigDecimal.ZERO:matcostsInput.getExpense());
		boolean flag= addCustomerCostPayable(customerCostPayable);
		boolean queryCCP=true;
		if(flag && "1".equals(matcostsInput.getHcflag()) && BigDecimal.ZERO.compareTo(matcostsInput.getExpense()) != 0){
			Map queryMap=new HashMap();
			if("0".equals(matcostsInput.getSourcefrome()) || "1".equals(matcostsInput.getSourcefrome())){
				queryMap.put("billno", matcostsInput.getHcreferid());	//关联的代垫对应的客户应付费用
				queryMap.put("sourcefrom", "1");
			}else{
				queryMap.put("sourcefromnot", "0,1");
				if(StringUtils.isNotEmpty(matcostsInput.getOaid())){
					queryMap.put("oaid", matcostsInput.getOaid());
				}else{
					queryCCP=false;
				}
			}
			queryMap.put("hcflag", "0");
			CustomerCostPayable refCustomerCostPayable=null;
			if(queryCCP){
				refCustomerCostPayable=customerCostPayableMapper.getCustomerCostPayableByMap(queryMap);
			}
			if(null!=refCustomerCostPayable){
				CustomerCostPayable hcCustomerCostPayable=new CustomerCostPayable();
				hcCustomerCostPayable.setId(refCustomerCostPayable.getId());
				hcCustomerCostPayable.setHcflag("2");
				customerCostPayableMapper.updateCustomerCostPayable(hcCustomerCostPayable);
			}
		}
		return flag;
    }
    
    
    @Override
	public Map addMatcostsInputHC(MatcostsInput matcostsInput)throws Exception{
    	 Map resultMap=new HashMap();
    	 if (isAutoCreate("t_js_matcostsinput")) {
             // 获取自动编号
             matcostsInput.setId(getAutoCreateSysNumbderForeign(matcostsInput, "t_js_matcostsinput"));
         }else{
             matcostsInput.setId(null);
         }
    	 
    	 if(StringUtils.isEmpty(matcostsInput.getHcreferid())){
    		 resultMap.put("flag","false");
    		 resultMap.put("msg", "未能找到关联的代垫");
 			 return resultMap;
    	 }
    	 
    	 MatcostsInput refMatcostsInput=journalSheetMapper.getMatcostsInput(matcostsInput.getHcreferid());
    	 
    	 if(null==refMatcostsInput){
    		 resultMap.put("flag","false");
    		 resultMap.put("msg", "未能找到关联的代垫");
 			 return resultMap;    		 
    	 }
    	 if("2".equals(refMatcostsInput.getBilltype())){
    		 resultMap.put("flag","false");
    		 resultMap.put("msg", "收回不能进行红冲");
 			 return resultMap;    		 
    	 }
    	 
    	 if("1".equals(refMatcostsInput.getHcflag()) || "2".equals(refMatcostsInput.getHcflag())){
    		 resultMap.put("flag","false");
    		 resultMap.put("msg", "此单据已经被红冲");
 			 return resultMap;    		 
    	 }
    	 
    	 if("1".equals(refMatcostsInput.getIswriteoff()) || "2".equals(refMatcostsInput.getIswriteoff())){
    		 resultMap.put("flag","false");
    		 resultMap.put("msg", "核销后的代垫不能进行红冲");
 			 return resultMap;    		 
    	 }
    	 
    	 Date nowDate=new Date();
    	 

 		 SysUser sysUser = getSysUser();
 		 matcostsInput.setAdduserid(sysUser.getUserid());
 		 matcostsInput.setAddusername(sysUser.getName());
 		 matcostsInput.setReimburseamount(BigDecimal.ZERO);
 		 matcostsInput.setReimbursetype("");
 		 matcostsInput.setSourcefrome("0");
    	 
    	 matcostsInput.setActingmatamount(refMatcostsInput.getActingmatamount());
    	 matcostsInput.setBankid(refMatcostsInput.getBankid());	
    	 matcostsInput.setBilltype(refMatcostsInput.getBilltype());	//代垫类型1代垫2收回
    	 matcostsInput.setBranchaccount(refMatcostsInput.getBranchaccount());
    	 matcostsInput.setBrandid(refMatcostsInput.getBrandid()); //品牌编号
    	 matcostsInput.setCustomerid(refMatcostsInput.getCustomerid());	//客户编号 
    	 matcostsInput.setExpense(refMatcostsInput.getExpense().negate());
    	 matcostsInput.setFactoryamount(refMatcostsInput.getFactoryamount().negate());
    	 matcostsInput.setHcflag("1"); //红冲
    	 matcostsInput.setHtcompdiscount(refMatcostsInput.getHtcompdiscount());
    	 matcostsInput.setHtpayamount(refMatcostsInput.getHtpayamount());
    	 matcostsInput.setOaid(refMatcostsInput.getOaid());	// oa编号
    	 matcostsInput.setSubjectid(refMatcostsInput.getSubjectid()); //科目
    	 matcostsInput.setSupplierdeptid(refMatcostsInput.getSupplierdeptid());  //供应商部门
    	 matcostsInput.setSupplierid(refMatcostsInput.getSupplierid());  //供应商
    	 matcostsInput.setAddtime(nowDate);    	 
    	 
    	 matcostsInput.setPaydate(refMatcostsInput.getPaydate());
    	 matcostsInput.setTakebackdate(refMatcostsInput.getTakebackdate());
    	
    	 
    	 
    	 if(null==matcostsInput.getBranchaccount()){
    		 matcostsInput.setBranchaccount(BigDecimal.ZERO);
    	 }
    	 if(null==matcostsInput.getActingmatamount()){
    		 matcostsInput.setActingmatamount(BigDecimal.ZERO);
    	 }
         if(null==matcostsInput.getHtpayamount()){
             matcostsInput.setHtpayamount(BigDecimal.ZERO);
         }
         matcostsInput.setBranchaccount(matcostsInput.getFactoryamount().subtract(matcostsInput.getHtpayamount()));
         if(null==matcostsInput.getReimburseamount()){
             matcostsInput.setReimburseamount(BigDecimal.ZERO);
         }
         matcostsInput.setActingmatamount(matcostsInput.getFactoryamount().subtract(matcostsInput.getReimburseamount()));
         
         //红冲核销
		 matcostsInput.setIswriteoff("1"); //红冲 意味着核销
    	 matcostsInput.setWriteoffer(sysUser.getUserid());
    	 matcostsInput.setWriteoffername(sysUser.getName());
    	 matcostsInput.setWriteoffdate(CommonUtils.dataToStr(nowDate, "yyyy-MM-dd"));
    	 matcostsInput.setWriteoffamount(matcostsInput.getFactoryamount());	//工厂投入
    	 matcostsInput.setReimburseamount(BigDecimal.ZERO);
    	 matcostsInput.setHcrefcount(0);
         
 		 boolean flag= journalSheetMapper.insertMatcostsInputALL(matcostsInput) > 0;
 		
 		 if(flag){
 			 //红冲添加成功
 			 MatcostsInput woffMatcostsInput=new MatcostsInput();
 			 woffMatcostsInput.setId(matcostsInput.getHcreferid());
 			 woffMatcostsInput.setIswriteoff("1");
 			 woffMatcostsInput.setWriteoffamount(refMatcostsInput.getFactoryamount());
 			 woffMatcostsInput.setWriteoffer(sysUser.getUserid());
 			 woffMatcostsInput.setWriteoffername(sysUser.getName());
 			 woffMatcostsInput.setWriteoffdate(CommonUtils.dataToStr(nowDate, "yyyy-MM-dd"));
 			 woffMatcostsInput.setReimburseamount(BigDecimal.ZERO);
 			 woffMatcostsInput.setHcflag("2");	//原单据红冲状态，为红冲
 			 woffMatcostsInput.setHcrefcount(1);	//红冲引用1次
 			 //原单据核销
 			 flag=journalSheetMapper.updateMatcostsInputALL(woffMatcostsInput)>0;
 			 if(!flag){
 				 //原单据未能更新成功，回滚~~
 				 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
 				 
 	 			 resultMap.put("flag",false);
 	 			 return resultMap; 
 			 }
 		 }
 		
 		if(flag && null!=matcostsInput.getExpense() 
 				&& BigDecimal.ZERO.compareTo(matcostsInput.getExpense())!=0
 				&& StringUtils.isNotEmpty(matcostsInput.getCustomerid())){
 			/**
 			 * 添加 客户应付费用
 			 */
 			// 1：客户应付费用
 			matcostsInput.setSourcefrome(refMatcostsInput.getSourcefrome());
 			addCustomerCostPayByMatcost(matcostsInput);
 			
 		}
		 resultMap.put("flag",flag);
		 return resultMap; 
	}
    /**
     * 撤销代垫红冲
     */
    public Map removeMatcostsInputHC(String id)throws Exception{
    	Map resultMap=new HashMap();
		Map queryMap=new HashMap();
    	if(null==id || "".equals(id.trim())){
      		 resultMap.put("flag","false");
      		 resultMap.put("msg", "未能找到关联的代垫");
   			 return resultMap;    
    	}
    	MatcostsInput oldData=journalSheetMapper.getMatcostsInput(id.trim());
    	if(null==oldData){
     		 resultMap.put("flag","false");
     		 resultMap.put("msg", "未能找到关联的代垫");
  			 return resultMap;        		
    	}
    	if(!"1".equals(oldData.getHcflag()) && !"2".equals(oldData.getHcflag())){
    		 resultMap.put("flag","false");
    		 resultMap.put("msg", "请选择相应的红冲");
 			 return resultMap;     
    	}
    	MatcostsInput refMatData=null;
    	MatcostsInput hcMatData=null;
    	if("1".equals(oldData.getHcflag())){
    		hcMatData=oldData;
    		refMatData=journalSheetMapper.getMatcostsInput(oldData.getHcreferid());
    		if (null==refMatData) {
    			 resultMap.put("flag","false");
        		 resultMap.put("msg", "未能找到红冲关联的代垫");
     			 return resultMap;   
			}
    	}else{
    		refMatData=oldData;
    		queryMap.put("hcreferid", oldData.getId());
    		queryMap.put("hcflag", "1");//红冲
    		hcMatData=journalSheetMapper.getMatcostsInputByMap(queryMap);
    		if(null==hcMatData){
	   			 resultMap.put("flag","false");
	    		 resultMap.put("msg", "未能找到对应的红冲");
	 			 return resultMap;       			
    		}
    	}
    	
    	//原代垫处理
    	 //红冲添加成功
		MatcostsInput woffMatcostsInput=new MatcostsInput();
		woffMatcostsInput.setId(refMatData.getId());
		woffMatcostsInput.setIswriteoff("0");
		woffMatcostsInput.setWriteoffamount(BigDecimal.ZERO);
		woffMatcostsInput.setWriteoffer("");
		woffMatcostsInput.setWriteoffername("");
		woffMatcostsInput.setWriteoffdate("");
		woffMatcostsInput.setRemainderamount(refMatData.getFactoryamount());
		woffMatcostsInput.setHcflag("0");	//原单据红冲状态，还原为代垫
		woffMatcostsInput.setHcrefcount(0);	//红冲引用0次
		 //原单据核销
		boolean flag=journalSheetMapper.updateMatcostsInputALL(woffMatcostsInput)>0;
		if(flag){
			flag=journalSheetMapper.deleteMatcostsInput(hcMatData.getId())>0;			
			if(!flag){
				 //原单据未能更新成功，回滚~~
				 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				 
	 			 resultMap.put("flag",false);
	 			 return resultMap; 
			}
			//改回原客户应付费用
			queryMap.clear();
			boolean queryCCP=true;
			if("0".equals(refMatData.getSourcefrome()) || "1".equals(refMatData.getSourcefrome())){
				queryMap.put("billno", refMatData.getId());	//关联的代垫对应的客户应付费用
				queryMap.put("sourcefrom", "1");
			}else{
				queryMap.put("sourcefromnot", "0,1");
				if(StringUtils.isNotEmpty(refMatData.getOaid())){
					queryMap.put("oaid", refMatData.getOaid());
				}else{
					queryCCP=false;
				}
			}
			queryMap.put("hcflag", "2");
			CustomerCostPayable customerCostPayable=null;
			if(queryCCP){
				customerCostPayable=customerCostPayableMapper.getCustomerCostPayableByMap(queryMap);
			}
			if(null!=customerCostPayable){
				CustomerCostPayable upCustomerCostPayable=new CustomerCostPayable();
				upCustomerCostPayable.setId(customerCostPayable.getId());
				upCustomerCostPayable.setHcflag("0");
				customerCostPayableMapper.updateCustomerCostPayable(upCustomerCostPayable);
			}
			//删除代垫红冲
			queryMap.clear();
			queryMap.put("billno", hcMatData.getId());	//代垫红冲对应的客户应付费用
			queryMap.put("sourcefrom", "1");
			queryMap.put("hcflag", "1");
			customerCostPayableMapper.deleteCustomerCostPayableByMap(queryMap);
			
		}

		resultMap.put("flag",flag);
		return resultMap; 
    }
    
    
    /**
     * 在代垫处更新客户应付
     * @param matcostsInput
     * @return
     * @throws Exception
     * @author zhanghonghui 
     * @date 2015年5月14日
     */
    private boolean updateCustomerCostPayByMatcost(MatcostsInput matcostsInput) throws Exception{
    	CustomerCostPayable customerCostPayable = new CustomerCostPayable();
		customerCostPayable.setSourcefrom("1");  //代垫
		customerCostPayable.setBillno(matcostsInput.getId());
		customerCostPayable.setCustomerid(matcostsInput.getCustomerid());
		customerCostPayable.setSupplierid(matcostsInput.getSupplierid());
		Customer customer = getCustomerByID(matcostsInput.getCustomerid());
		if(null!=customer){
			customerCostPayable.setPcustomerid(customer.getPid());
			if("0".equals(customer.getIslast())){
				customerCostPayable.setPcustomerid(matcostsInput.getCustomerid());
			}
		}
		if("0".equals(matcostsInput.getSourcefrome()) || "1".equals(matcostsInput.getSourcefrome())){
			customerCostPayable.setBusinessdate(matcostsInput.getBusinessdate());
		}else{
			//业务日期随着代垫的业务日期
			customerCostPayable.setBusinessdate(CommonUtils.getTodayDataStr());
		}
		customerCostPayable.setIspay("0");
		customerCostPayable.setExpensesort(matcostsInput.getSubjectid());
		customerCostPayable.setOaid(matcostsInput.getOaid());
		customerCostPayable.setBilltype("1");	//单据类型 1：客户应付费用；2：客户支付费用
		customerCostPayable.setRelateoaid(matcostsInput.getOaid());
        
		//费用金额
		customerCostPayable.setAmount(matcostsInput.getExpense()==null?BigDecimal.ZERO:matcostsInput.getExpense());
		SysUser sysUser=null;
		Map map=new HashMap();
		map.put("sourcefrom", "1");	//代垫
		map.put("billno", matcostsInput.getId());
		boolean flag=false;
		if(customerCostPayableMapper.getCustomerCostPayableCountBySource(map)>0){			
			flag=customerCostPayableMapper.updateCustomerCostPayableBySource(customerCostPayable)>0;
		}else{			
	        customerCostPayable.setApplyuserid(matcostsInput.getAdduserid());
			
	        if(StringUtils.isNotEmpty(matcostsInput.getAdduserid())){
	        	sysUser = getSysUserById(matcostsInput.getAdduserid());
	        	if(null!=sysUser){                    
	                customerCostPayable.setApplydeptid(sysUser.getDepartmentid());
	        	}
	        }
			 flag=addCustomerCostPayable(customerCostPayable);			
		}
		return flag;
    }
    
    /**
     * 根据代垫编号，删除对应客户应付费用
     * @param id
     * @return
     * @throws Exception
     * @author zhanghonghui 
     * @date 2015年5月15日
     */
    private boolean deleteCustomerCostPayByMatcost(String id) throws Exception{
    	if(null==id || "".equals(id.trim())){
    		return false;
    	}
    	Map map=new HashMap();
		map.put("sourcefrom", "1");	//代垫
		map.put("billno", id.trim());
		return customerCostPayableMapper.deleteCustomerCostPayableByMap(map)>0;
    }
    

    @Override
    public PageData getMatcostsInputPageList(PageMap pageMap) throws Exception {
        String cols = getAccessColumnList("t_js_matcostsinput","t"); //字段权限
        pageMap.setCols(cols);
        String sql = getDataAccessRule("t_js_matcostsinput","t"); //数据权限
        pageMap.setDataSql(sql);
        Map condition=pageMap.getCondition();
        String showDyncReimburse =(String)condition.get("showDyncReimburse");
        StringBuilder sumsb=new StringBuilder();
        String isExportData=(String)condition.get("isExportData");
        if("true".equals(showDyncReimburse)){
            List<SysCode> reimbursetypeList = getBaseSysCodeMapper().getSysCodeListForeign("matcreimbursetype");
            StringBuilder sb=new StringBuilder();
            for(SysCode sysCode : reimbursetypeList){
                if(null!=sysCode && StringUtils.isNotEmpty(sysCode.getCode())){
                    sb.append(",IF(IFNULL(t.reimbursetype,'') ='");
                    sb.append(sysCode.getCode());
                    sb.append("' ,t.writeoffamount,0.000000) AS reimburse_");
                    sb.append(sysCode.getCode());

                    sumsb.append(",SUM(reimburse_");
                    sumsb.append(sysCode.getCode());
                    sumsb.append(")");
                    sumsb.append(" AS reimburse_");
                    sumsb.append(sysCode.getCode());
                }
            }
            if(sb.length()>0){
                condition.put("dyncReimburseColumn", sb.toString());
            }
        }else{
            if(condition.containsKey("dyncReimburseColumn")){
                condition.remove("dyncReimburseColumn");
            }
        }
        String sort=(String)condition.get("sort");
		if(null==sort){
			sort="";
		}
		String order=(String)condition.get("order");
		if(null==order){
			order="";
		}
		if(!"ASC".equals(order.toUpperCase().trim())&&!"DESC".equals(order.toUpperCase().trim())){
			order="ASC";
		}
		String ordersort="";
		if( ("".equals(sort.trim()) ||
		    "".equals(order.trim())) && StringUtils.isEmpty(pageMap.getOrderSql()) ){
			
			ordersort=" id desc ";
		}else{
			if(!"".equals(sort.trim())){
				if("".equals(order.trim())){
					order=" ASC ";
				}
				ordersort=sort.trim()+" "+order.trim();
				if("businessdate".equals(sort)){
					ordersort=ordersort+" ,id "+order.trim();
				}
			}else if(StringUtils.isNotEmpty(pageMap.getOrderSql())){
				ordersort=pageMap.getOrderSql();
			}
		}
		pageMap.setOrderSql(ordersort);
		if(condition.containsKey("sort")){
			condition.remove("sort");
		}
		if(condition.containsKey("order")){
			condition.remove("order");
		}

        List<Map> list = journalSheetMapper.getMatcostsInputPageList(pageMap);
        if(list.size() != 0){
            String tmpstr="";
            List<SysCode> codelist = getBaseSysCodeMapper().getSysCodeListForeign("matcreimbursetype");
            List<ExpensesSort> subjectCodelist = getBaseFinanceMapper().getExpensesSortByStateList();
            for(Map map :list){
                tmpstr=(String)map.get("supplierdeptid");
                if(null!=tmpstr && !"".equals(tmpstr.trim())){//所属部门
                    DepartMent departMent = departMentMapper.getDepartmentInfo(tmpstr.trim());
                    if(null!=departMent){
                        map.put("supplierdeptname",departMent.getName());
                    }
                }

                tmpstr=(String)map.get("supplierid");
                if(null!=tmpstr && !"".equals(tmpstr.trim())){//供应商
                    BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(tmpstr.trim());
                    if(null!=buySupplier){
                        map.put("suppliername",buySupplier.getName());
                    }
                }
                tmpstr=(String)map.get("customerid");
                if(null!=tmpstr && !"".equals(tmpstr.trim())){//
                    Customer customerInfo = getCustomerByID(tmpstr.trim());
                    if(null != customerInfo){
                        map.put("customername",customerInfo.getName());

                        map.put("customersort",customerInfo.getCustomersort());

                        if(StringUtils.isNotEmpty(customerInfo.getCustomersort())){
                            CustomerSort customerSortInfo=getCustomerSortByID(customerInfo.getCustomersort());
                            if(null!=customerSortInfo){
                                map.put("customersortname",customerSortInfo.getName());
                            }
                        }

                    }
                }
                tmpstr=(String)map.get("brandid");
                if(null!=tmpstr && !"".equals(tmpstr.trim())){//
                    Brand brand = getBaseGoodsMapper().getBrandInfo(tmpstr.trim());
                    if(null!=brand){
                        map.put("brandname",brand.getName());
                    }
                }
                tmpstr=(String)map.get("reimbursetype");
                if(null!=tmpstr && !"".equals(tmpstr.trim())){//收回方式
                    if(codelist.size() != 0){
                        for(SysCode sysCode:codelist){
                            if(tmpstr.trim().equals(sysCode.getCode())){
                                map.put("reimbursetypename",sysCode.getCodename());
                                break;
                            }
                        }
                    }
                }
                tmpstr=(String)map.get("subjectid");
                if(null!=tmpstr && !"".equals(tmpstr.trim())){//科目
                    if(subjectCodelist.size() != 0){
                        for(ExpensesSort expensesSort:subjectCodelist){
                            if(tmpstr.trim().equals(expensesSort.getId())){
                                map.put("subjectname",expensesSort.getThisname());
                                break;
                            }
                        }
                    }
                }
                tmpstr=(String)map.get("transactorid");
                if(null!=tmpstr && !"".equals(tmpstr.trim())){//经办人
                    Personnel personnelInfo=getPersonnelById(tmpstr.trim());
                    if(null!=personnelInfo){
                        map.put("transactorname", personnelInfo.getName());
                    }
                }
                String iswriteoff=(String)map.get("iswriteoff");
                if(null!=iswriteoff){
                    if("1".equals(iswriteoff.trim())){//是否核销
                        map.put("iswriteoffname", "核销");

                        BigDecimal factoryamount=(BigDecimal)map.get("factoryamount");
                        BigDecimal writeoffamount=(BigDecimal)map.get("writeoffamount");
                        if(null==factoryamount){
                            factoryamount=BigDecimal.ZERO;
                        }
                        if(null==writeoffamount){
                            writeoffamount=BigDecimal.ZERO;
                        }
                        map.put("actingmatamount", factoryamount.subtract(writeoffamount));
                    }else{
                        map.put("iswriteoffname", "");
                        map.put("writeoffdate", null);
                    }
                }else{
                    map.put("iswriteoffname", "");
                    map.put("writeoffdate", null);
                }
                tmpstr=(String)map.get("hcflag");
                if(null!=tmpstr){
                	if("1".equals(tmpstr.trim()) || "2".equals(tmpstr.trim())){
                		map.put("hcflagname", "是");
                	}else{
                		map.put("hcflagname", "否");
                	}
                }
            }
        }

        if(sumsb.length()>0){
            condition.put("dyncReimburseSumColumn", sumsb.toString());
        }
        List<Map> footerList = new ArrayList<Map>();
        Map sumMap = journalSheetMapper.getMatcostsInputSums(pageMap);
        if(null!=sumMap){
            sumMap.put("suppliername","合计");
            footerList.add(sumMap);
        }
        PageData pageData = new PageData(journalSheetMapper.getMatcostsInputListCount(pageMap),list,pageMap);
        pageData.setFooter(footerList);
        return pageData;
    }

    @Override
    public MatcostsInput getMatcostsInputDetail(String id) throws Exception {

        Map map = new HashMap();
        map.put("id", id);
        map.put("billtype", "1");
        MatcostsInput matcostsInput= journalSheetMapper.getMatcostsInputDetail(map);

        if(null!=matcostsInput){
            if(StringUtils.isNotEmpty(matcostsInput.getSupplierid())){//供应商
                BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(matcostsInput.getSupplierid());
                if(null!=buySupplier){
                    matcostsInput.setSuppliername(buySupplier.getName());
                }
            }
            if(StringUtils.isNotEmpty(matcostsInput.getCustomerid())){//客户编号
                Customer customer = getCustomerByID(matcostsInput.getCustomerid());
                if(null != customer){
                    matcostsInput.setCustomername(customer.getName());
                }
            }
            if(StringUtils.isNotEmpty(matcostsInput.getTransactorid())){//经办人
                Personnel personnelInfo=getPersonnelById(matcostsInput.getTransactorid());
                if(null!=personnelInfo){
                    matcostsInput.setTransactorname(personnelInfo.getName());
                }
            }
            if("1".equals(matcostsInput.getIswriteoff())){
                if(null==matcostsInput.getFactoryamount()){
                    matcostsInput.setFactoryamount(BigDecimal.ZERO);
                }
                if(null==matcostsInput.getWriteoffamount()){
                    matcostsInput.setWriteoffamount(BigDecimal.ZERO);
                }
                matcostsInput.setActingmatamount(matcostsInput.getFactoryamount().subtract(matcostsInput.getWriteoffamount()));
            }
        }
        return matcostsInput;
    }

    @Override
    public Map addDRMatcostsInput(List<Map> list)
            throws Exception {
        Map msgmap = new HashMap();
        boolean flag = false;
        int successNum = 0,failureNum = 0,errorNum = 0;
        StringBuilder failSb = new StringBuilder();
        StringBuilder errorSb  = new StringBuilder();
        SysUser sysUser = getSysUser();
        String tmpStr="";
        boolean isId=isAutoCreate("t_js_matcostsinput");
        if(null != list && list.size() != 0){
            List<SysCode> reimbursetypeList = getBaseSysCodeMapper().getSysCodeListForeign("matcreimbursetype");
            List<ExpensesSort> subjectCodelist = getBaseFinanceMapper().getExpensesSortByStateList();
            MatcostsInput matcostsInput=null;
            for(Map map : list){
                try{
                    if(null==map){
                        continue;
                    }
                    matcostsInput=new MatcostsInput();
                    matcostsInput.setSourcefrome("1");
                    matcostsInput.setBusinessdate((String) map.get("businessdate"));
                    if(StringUtils.isEmpty(matcostsInput.getBusinessdate())){
                        matcostsInput.setBusinessdate(CommonUtils.getTodayDataStr());
                    }
                    matcostsInput.setOaid((String)map.get("oaid"));
                    matcostsInput.setSupplierid((String)map.get("supplierid"));
                    if(map.containsKey("paydate")) {
                        matcostsInput.setPaydate((String) map.get("paydate"));
                    }
                    if(map.containsKey("takebackdate")) {
                        matcostsInput.setTakebackdate((String) map.get("takebackdate"));
                    }
                    tmpStr=(String)map.get("hcflag");
                    if(null==tmpStr || "".equals(tmpStr.trim())){
                    	matcostsInput.setHcflag("0");
                    }else if("1".equals(tmpStr.trim()) || "2".equals(tmpStr.trim()) || "是".equals(tmpStr.trim())){
                    	matcostsInput.setHcflag("1");
                    }else{
                    	matcostsInput.setHcflag("0");
                    }
                    
                    if("1".equals(matcostsInput.getHcflag()) || "2".equals(matcostsInput.getHcflag())){

                        failureNum=failureNum+1;
                        failSb.append("红冲不能导入");
                        if(StringUtils.isNotEmpty(matcostsInput.getBusinessdate())){
                            failSb.append(" 业务日期："+matcostsInput.getBusinessdate());
                        }
                        if(StringUtils.isNotEmpty(matcostsInput.getOaid())){
                            failSb.append(" OA编号："+matcostsInput.getOaid());
                        }
                        if(StringUtils.isNotEmpty(matcostsInput.getSupplierid())){
                            failSb.append(" 供应商编码："+matcostsInput.getSupplierid());
                        }else if(StringUtils.isNotEmpty(matcostsInput.getSuppliername())){
                            failSb.append(" 导入的供应商名称："+matcostsInput.getSuppliername());
                        }
                        continue;
                    }
                    
                    tmpStr=(String)map.get("factoryamount");
                    if(StringUtils.isNotEmpty(tmpStr) && CommonUtils.isNumStr(tmpStr)){
                        matcostsInput.setFactoryamount(new BigDecimal(tmpStr));
                    }else{
                        matcostsInput.setFactoryamount(BigDecimal.ZERO);
                    }
					
                    if(BigDecimal.ZERO.compareTo(matcostsInput.getFactoryamount())>0){
                        failureNum=failureNum+1;
                        failSb.append(" 工厂投入为负数，请在代垫收回处导入");
                        if(StringUtils.isNotEmpty(matcostsInput.getBusinessdate())){
                            failSb.append(" 业务日期："+matcostsInput.getBusinessdate());
                        }
                        if(StringUtils.isNotEmpty(matcostsInput.getOaid())){
                            failSb.append(" OA编号："+matcostsInput.getOaid());
                        }
                        if(StringUtils.isNotEmpty(matcostsInput.getSupplierid())){
                            failSb.append(" 供应商编码："+matcostsInput.getSupplierid());
                        }
                        continue;
                    }
                    matcostsInput.setSuppliername((String) map.get("suppliername"));
                    if(StringUtils.isNotEmpty(matcostsInput.getSupplierid())){//供应商
                        BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(matcostsInput.getSupplierid());
                        if(null!=buySupplier){
                            matcostsInput.setSuppliername(buySupplier.getName());
                            matcostsInput.setSupplierdeptid(buySupplier.getBuydeptid());
                        }else{
                            failureNum=failureNum+1;
                            failSb.append(" 未找到供应商信息");
                            if(StringUtils.isNotEmpty(matcostsInput.getBusinessdate())){
                                failSb.append(" 业务日期："+matcostsInput.getBusinessdate());
                            }
                            if(StringUtils.isNotEmpty(matcostsInput.getOaid())){
                                failSb.append(" OA编号："+matcostsInput.getOaid());
                            }
                            if(StringUtils.isNotEmpty(matcostsInput.getSupplierid())){
                                failSb.append(" 供应商编码："+matcostsInput.getSupplierid());
                            }else if(StringUtils.isNotEmpty(matcostsInput.getSuppliername())){
                                failSb.append(" 导入的供应商名称："+matcostsInput.getSuppliername());
                            }
                            continue;
                        }
                    }else{
                        failureNum=failureNum+1;
                        failSb.append(" 未找到供应商信息");
                        if(StringUtils.isNotEmpty(matcostsInput.getBusinessdate())){
                            failSb.append(" 业务日期："+matcostsInput.getBusinessdate());
                        }
                        if(StringUtils.isNotEmpty(matcostsInput.getOaid())){
                            failSb.append(" OA编号："+matcostsInput.getOaid());
                        }
                        if(StringUtils.isNotEmpty(matcostsInput.getSupplierid())){
                            failSb.append(" 供应商编码："+matcostsInput.getSupplierid());
                        }else if(StringUtils.isNotEmpty(matcostsInput.getSuppliername())){
                            failSb.append(" 导入的供应商名称："+matcostsInput.getSuppliername());
                        }
                        continue;
                    }
                    matcostsInput.setBrandid((String)map.get("brandid"));
                    matcostsInput.setCustomerid((String)map.get("customerid"));
                    matcostsInput.setCustomername((String)map.get("customername"));
                    if(StringUtils.isNotEmpty(matcostsInput.getCustomerid())){//客户
                        Customer customerInfo = getBaseCustomerMapper().getCustomerInfo(matcostsInput.getCustomerid());
                        if(null==customerInfo){
                            failureNum=failureNum+1;
                            failSb.append(" 未找到对应的客户信息");
                            if(StringUtils.isNotEmpty(matcostsInput.getBusinessdate())){
                                failSb.append(" 业务日期："+matcostsInput.getBusinessdate());
                            }
                            if(StringUtils.isNotEmpty(matcostsInput.getOaid())){
                                failSb.append(" OA编号："+matcostsInput.getOaid());
                            }
                            if(StringUtils.isNotEmpty(matcostsInput.getCustomerid())){
                                failSb.append(" 导入的客户编码："+matcostsInput.getCustomerid());
                            }else if(StringUtils.isNotEmpty(matcostsInput.getCustomername())){
                                failSb.append(" 导入的客户名称："+matcostsInput.getCustomername());
                            }
                            continue;
                        }
                    }

                    matcostsInput.setTransactorid((String) map.get("transactorid"));
                    matcostsInput.setTransactorname((String) map.get("transactorname"));

                    if(StringUtils.isNotEmpty(matcostsInput.getTransactorid())){//经办人
                        Personnel personnelInfo = getBasePersonnelMapper().getPersonnelInfo(matcostsInput.getTransactorid());
                        if(null==personnelInfo){
                            failureNum=failureNum+1;
                            failSb.append(" 未找到对应的经办人信息");
                            if(StringUtils.isNotEmpty(matcostsInput.getBusinessdate())){
                                failSb.append(" 业务日期："+matcostsInput.getBusinessdate());
                            }
                            if(StringUtils.isNotEmpty(matcostsInput.getOaid())){
                                failSb.append(" OA编号："+matcostsInput.getOaid());
                            }
                            if(StringUtils.isNotEmpty(matcostsInput.getTransactorid())){
                                failSb.append(" 导入的经办人编码："+matcostsInput.getTransactorid());
                            }else if(StringUtils.isNotEmpty(matcostsInput.getTransactorname())){
                                failSb.append(" 导入的经办人名称："+matcostsInput.getTransactorname());
                            }
                            continue;
                        }
                    }

                    matcostsInput.setSubjectname((String)map.get("subjectname"));

                    tmpStr=(String)map.get("htcompdiscount");
                    if(StringUtils.isNotEmpty(tmpStr) && CommonUtils.isNumStr(tmpStr)){
                        matcostsInput.setHtcompdiscount(new BigDecimal(tmpStr));
                    }
                    tmpStr=(String)map.get("htpayamount");
                    if(StringUtils.isNotEmpty(tmpStr) && CommonUtils.isNumStr(tmpStr)){
                        matcostsInput.setHtpayamount(new BigDecimal(tmpStr));
                    }else{
                        matcostsInput.setHtpayamount(BigDecimal.ZERO);
                    }
					/*
					if(StringUtils.isNotEmpty((String)map.get("branchaccount"))){
						matcostsInput.setBranchaccount(new BigDecimal((String)map.get("branchaccount")));
					}
					*/

                    matcostsInput.setBranchaccount(matcostsInput.getFactoryamount().subtract(matcostsInput.getHtpayamount()));
					/*
					matcostsInput.setReimburseamount(BigDecimal.ZERO);
					if(reimbursetypeList.size() != 0){
						BigDecimal tmpBig=null;
						for(SysCode sysCode:reimbursetypeList){
							if(StringUtils.isNotEmpty(sysCode.getCodename()) && StringUtils.isNotEmpty(sysCode.getCode())){
								tmpStr=(String)map.get("reimburse_"+sysCode.getCode());
								if(null!=tmpStr && !"".equals(tmpStr.trim()) && CommonUtils.isNumStr(tmpStr)){
									tmpBig=new BigDecimal(tmpStr);
									if(null!=tmpBig && BigDecimal.ZERO.compareTo(tmpBig)!=0){
										matcostsInput.setReimbursetype(sysCode.getCode());
										matcostsInput.setReimburseamount(tmpBig);
										break;
									}
								}
							}
						}
					}
					matcostsInput.setReimbursetypename((String)map.get("reimbursetypename"));
					tmpStr=(String)map.get("reimburseamount");
					if(StringUtils.isNotEmpty(tmpStr) && CommonUtils.isNumStr(tmpStr)){
						matcostsInput.setReimburseamount(new BigDecimal(tmpStr));
					}
					*/
                    if(null==matcostsInput.getReimburseamount()){
                        matcostsInput.setReimburseamount(BigDecimal.ZERO);
                    }

                    //if(StringUtils.isNotEmpty((String)map.get("actingmatamount"))){
                    //	matcostsInput.setActingmatamount(new BigDecimal((String)map.get("actingmatamount")));
                    //}

                    matcostsInput.setActingmatamount(matcostsInput.getFactoryamount().subtract(matcostsInput.getReimburseamount()));

                    matcostsInput.setRemainderamount(matcostsInput.getFactoryamount().subtract(matcostsInput.getReimburseamount()));

                    matcostsInput.setRemark((String)map.get("remark"));

                    tmpStr=(String)map.get("expense");
                    if(StringUtils.isNotEmpty(tmpStr) && CommonUtils.isNumStr(tmpStr)){
                        matcostsInput.setExpense(new BigDecimal(tmpStr));
                    }else{
                        matcostsInput.setExpense(BigDecimal.ZERO);
                    }
                    
                    if(null!=matcostsInput){

                        if(StringUtils.isNotEmpty(matcostsInput.getSubjectname())){
                            if(subjectCodelist.size() != 0){
                                for(ExpensesSort expensesSort:subjectCodelist){
                                    if(StringUtils.isNotEmpty(expensesSort.getThisname()) && expensesSort.getThisname().trim().equals(matcostsInput.getSubjectname())){
                                        matcostsInput.setSubjectid(expensesSort.getId());
                                        break;
                                    }
                                }
                            }
                        }
                        matcostsInput.setAdduserid(sysUser.getUserid());
                        matcostsInput.setAddusername(sysUser.getName());
                        if (isId) {
                            // 获取自动编号
                            matcostsInput.setId(getAutoCreateSysNumbderForeign(matcostsInput, "t_js_matcostsinput"));
                        }else{
                            matcostsInput.setId(null);
                        }
                        flag=journalSheetMapper.insertMatcostsInput(matcostsInput) > 0;
                        if(flag){
                            successNum=successNum+1;
                            if(flag 
                    				&& null!=matcostsInput.getExpense() 
                    				&& BigDecimal.ZERO.compareTo(matcostsInput.getExpense())!=0
                    				&& StringUtils.isNotEmpty(matcostsInput.getCustomerid())){
                    			/**
                    			 * 添加 客户应付费用
                    			 */
                    			// 1：客户应付费用
                    			addCustomerCostPayByMatcost(matcostsInput);
                    			
                    		}
                        }else{
                            failureNum=failureNum+1;
                            if(StringUtils.isNotEmpty(matcostsInput.getBusinessdate())){
                                failSb.append(" 业务日期："+matcostsInput.getBusinessdate());
                            }
                            if(StringUtils.isNotEmpty(matcostsInput.getOaid())){
                                failSb.append(" OA编号："+matcostsInput.getOaid());
                            }
                            if(StringUtils.isNotEmpty(matcostsInput.getSupplierid())){
                                failSb.append(" 供应商编码："+matcostsInput.getSupplierid());
                            }
                        }
                    }
                } catch (RuntimeException re) {
                    if("编号溢出".equals(re.getMessage())) {
                        throw re;
                    }
                    throw new Exception(re);
                } catch (Exception e) {
                    errorNum=errorNum+1;

                    if(null==matcostsInput){
                        continue;
                    }
                    if(StringUtils.isNotEmpty(matcostsInput.getBusinessdate())){
                        errorSb.append(" 业务日期："+matcostsInput.getBusinessdate());
                    }
                    if(StringUtils.isNotEmpty(matcostsInput.getOaid())){
                        errorSb.append(" OA编号："+matcostsInput.getOaid());
                    }
                    if(StringUtils.isNotEmpty(matcostsInput.getSupplierid())){
                        errorSb.append(" 供应商编码："+matcostsInput.getSupplierid());
                    }
                }
            }
        }
        else{
            msgmap.put("msg", "导入的数据为空!");
        }
        if(successNum>0){
            msgmap.put("flag", true);
        }else{
            msgmap.put("flag", false);
        }
        if(successNum==0 && failureNum==0 && errorNum==0 && list.size()>0){
            msgmap.put("msg", "导入的数据操失败!");
        }
        msgmap.put("success", successNum);
        msgmap.put("failure", failureNum);
        msgmap.put("failStr", failSb.toString());
        msgmap.put("isWholeMsgFlag", "true");
        msgmap.put("errorNum", errorNum);
        msgmap.put("errorVal", errorSb.toString());
        return msgmap;
    }

    @Override
    public PageData showMatcostsReportData(PageMap pageMap) throws Exception {
        Map conditionMap=pageMap.getCondition();
        if(conditionMap.containsKey("businessdate1")){
            conditionMap.put("begindate", conditionMap.get("businessdate1"));
        }else{
            conditionMap.put("begindate", "1900-01-01");
        }
        if(conditionMap.containsKey("businessdate2")){
            conditionMap.put("enddate", conditionMap.get("businessdate2"));
        }else{
            conditionMap.put("enddate", CommonUtils.getTodayDataStr());
        }
        String dataSql = getDataAccessRule("t_js_matcostsinput", "t");
        pageMap.setDataSql(dataSql);
        //小计列
        conditionMap.put("groupcols", "supplierid,supplierdeptid");	//瑞家特别，按供应商及所属部门


        List<SysCode> reimbursetypeList = getBaseSysCodeMapper().getSysCodeListForeign("matcreimbursetype");
        StringBuilder columnSqlsb=new StringBuilder();
        StringBuilder beginColumnSqlsb=new StringBuilder();
        StringBuilder sumColumnSqlsb=new StringBuilder();
        StringBuilder outerColumnSqlsb=new StringBuilder();
        for(SysCode sysCode : reimbursetypeList){
            if(null!=sysCode && StringUtils.isNotEmpty(sysCode.getCode())){
                columnSqlsb.append(",IF(IFNULL(t.reimbursetype,'') ='");
                columnSqlsb.append(sysCode.getCode());
                columnSqlsb.append("' ,t.reimburseamount,0.000000) AS amount");
                columnSqlsb.append(sysCode.getCode());

                beginColumnSqlsb.append(",0 as amount");
                beginColumnSqlsb.append(sysCode.getCode());

                sumColumnSqlsb.append(",sum(z.amount");
                sumColumnSqlsb.append(sysCode.getCode());
                sumColumnSqlsb.append(") as amount");
                sumColumnSqlsb.append(sysCode.getCode());

                outerColumnSqlsb.append(",z.amount");
                outerColumnSqlsb.append(sysCode.getCode());
            }
        }

        if(columnSqlsb.length()>0){
            conditionMap.put("dyncMatColumn", columnSqlsb.toString());
            conditionMap.put("dyncMatBeginColumn", beginColumnSqlsb.toString());
            conditionMap.put("dyncMatSumColumn", sumColumnSqlsb.toString());
            conditionMap.put("dyncMatOuterColumn", outerColumnSqlsb.toString());
        }else{
            if(conditionMap.containsKey("dyncMatColumn")){
                conditionMap.remove("dyncMatColumn");
            }
            if(conditionMap.containsKey("dyncMatBeginColumn")){
                conditionMap.remove("dyncMatBeginColumn");
            }
            if(conditionMap.containsKey("dyncMatSumColumn")){
                conditionMap.remove("dyncMatSumColumn");
            }
            if(conditionMap.containsKey("dyncMatOuterColumn")){
                conditionMap.remove("dyncMatOuterColumn");
            }
        }

        List<Map> list = journalSheetMapper.showMatcostsReportData(pageMap);
        for(Map map : list){
            if(map.containsKey("supplierid")){
                String supplierid = (String) map.get("supplierid");
                BuySupplier buySupplier = getSupplierInfoById(supplierid);
                if(null!=buySupplier){
                    map.put("suppliername", buySupplier.getName());
                }
            }
            if(map.containsKey("supplierdeptid")){
                String supplierdeptid = (String) map.get("supplierdeptid");
                DepartMent departMent = getDepartmentByDeptid(supplierdeptid);
                if(null!=departMent){
                    map.put("supplierdeptname", departMent.getName());
                }
            }
        }
        PageData pageData = new PageData(journalSheetMapper.showMatcostsReportDataCount(pageMap),list,pageMap);
        conditionMap.put("groupcols", "all");
        List<Map> footer = journalSheetMapper.showMatcostsReportData(pageMap);
        for(Map map : footer){
            if(null!=map){
                map.put("suppliername", "合计");
                map.put("supplierid", "");
                map.put("supplierdeptid", "");
                map.put("supplierdeptname", "");
            }
        }
        pageData.setFooter(footer);
        return pageData;
    }

	@Override
	public List showMatcostsReportDetail(Map map) throws Exception {
		String supplierid=(String)map.get("supplierid");
		String businessdate1=(String)map.get("businessdate1");
		String businessdate2=(String)map.get("businessdate2");
		List<Map> list = journalSheetMapper.showMatcostsReportDetail(map);
		BuySupplier buySupplier = getSupplierInfoById(supplierid);
		DepartMent departMent = null;
		if(null!=buySupplier){
			departMent = getDepartmentByDeptid(buySupplier.getBuydeptid());
		}
		if(null==businessdate1){
			businessdate1 = "1900-01-01";
		}
		Map beginQueryMap=new HashMap();
		beginQueryMap.putAll(map);
        beginQueryMap.put("businessdate",businessdate1);
        if(beginQueryMap.containsKey("businessdate2")){
            beginQueryMap.remove("businessdate2");
        }
		BigDecimal beginAmount = journalSheetMapper.getMatcostsBeginAmountByMap(beginQueryMap);
		if(null==beginAmount){
			beginAmount = BigDecimal.ZERO;
		}
		BigDecimal endamount = beginAmount;
		List<ExpensesSort> subjectCodelist = getBaseFinanceMapper().getExpensesSortByStateList();
		for(Map resultMap:list){
			if(endamount.compareTo(beginAmount) !=0){
				beginAmount = endamount;
			}
			String hcflag = (String) resultMap.get("hcflag");
            if("0".equals(hcflag)){
                resultMap.put("hcflag","否");
            }else{
                resultMap.put("hcflag","是");
            }
			resultMap.put("beginamount", beginAmount);
			BigDecimal actingmatamount = (BigDecimal) resultMap.get("actingmatamount");
			if(null==actingmatamount){
				actingmatamount = BigDecimal.ZERO;
			}
			endamount = beginAmount.add(actingmatamount);
			resultMap.put("endamount", endamount);
			resultMap.put("suppliername", buySupplier.getName());
			if(null!=departMent){
				resultMap.put("supplierdeptname", departMent.getName());
			}
			if(resultMap.containsKey("customerid")){
				String customerid = (String) resultMap.get("customerid");
				Customer customer = getCustomerByID(customerid);
				if(null!=customer){
					resultMap.put("customername", customer.getName());
				}
			}
			if(resultMap.containsKey("reimbursetype")){
				String reimbursetype = (String) resultMap.get("reimbursetype");
				resultMap.put("amount"+reimbursetype, resultMap.get("reimburseamount"));
			}
			if(resultMap.containsKey("subjectid")){
				String tmpstr=(String)resultMap.get("subjectid");
				if(null!=tmpstr && !"".equals(tmpstr.trim())){//科目
					if(subjectCodelist.size() != 0){
						for(ExpensesSort expensesSort:subjectCodelist){
							if(tmpstr.trim().equals(expensesSort.getId())){
								resultMap.put("subjectname",expensesSort.getThisname());
								break;
							}
						}
					}
				}
			}
		}
		return list;
	}
	
	
	@Override
	public boolean addMatcostsReimburse(MatcostsInput matcostsInput)
			throws Exception {
		if (isAutoCreate("t_js_matcostsreimburse")) {
            // 获取自动编号
            matcostsInput.setId(getAutoCreateSysNumbderForeign(matcostsInput, "t_js_matcostsreimburse"));
        }else{
            matcostsInput.setId(null);
        }
        SysUser sysUser = getSysUser();
        matcostsInput.setAdduserid(sysUser.getUserid());
        matcostsInput.setAddusername(sysUser.getName());
        matcostsInput.setRemainderamount(matcostsInput.getReimburseamount());
        matcostsInput.setFactoryamount(BigDecimal.ZERO);
        matcostsInput.setActingmatamount(matcostsInput.getFactoryamount().subtract(matcostsInput.getReimburseamount()));
        boolean flag =  journalSheetMapper.insertMatcostsReimburse(matcostsInput) > 0;
        if(flag && StringUtils.isNotEmpty(matcostsInput.getBankid())){
            //代垫收回 银行账户变更
            IAccountForOAService accountForOAService = (IAccountForOAService) SpringContextUtils.getBean("accountForOAService");
            BankAmountOthers bankAmountOthers = new BankAmountOthers();
            //借贷类型 1借(收入)2贷(支出)
            bankAmountOthers.setLendtype("1");
            bankAmountOthers.setBankid(matcostsInput.getBankid());
            bankAmountOthers.setDeptid(matcostsInput.getSupplierdeptid());
            //单据类型0期初1收款单2付款单3日常费用支付4客户费用支付5个人借款6预付款7转账8代垫收回
            bankAmountOthers.setBilltype("8");
            bankAmountOthers.setBillid(matcostsInput.getId());
            bankAmountOthers.setOaid(matcostsInput.getOaid());
            bankAmountOthers.setAmount(matcostsInput.getReimburseamount());
            bankAmountOthers.setUpamount("");
            bankAmountOthers.setOppid(matcostsInput.getSupplierid());
            BuySupplier buySupplier = getSupplierInfoById(matcostsInput.getSupplierid());
            if(null!=buySupplier){
                bankAmountOthers.setOppname(buySupplier.getName());
            }

            bankAmountOthers.setRemark(matcostsInput.getRemark());
            bankAmountOthers.setAdduserid(matcostsInput.getAdduserid());
            bankAmountOthers.setAddusername(matcostsInput.getAddusername());
            bankAmountOthers.setAdddeptid(sysUser.getDepartmentid());
            bankAmountOthers.setAdddeptname(sysUser.getDepartmentname());

            accountForOAService.addBankAmountOthers(bankAmountOthers);

        }
        return flag;
	}


	@Override
	public boolean editMatcostsReimburse(MatcostsInput matcostsInput) throws Exception {		
		if(null==matcostsInput){
            return false;
        }
        SysUser sysUser = getSysUser();
        MatcostsInput oldData=journalSheetMapper.getMatcostsInput(matcostsInput.getId());
        if(null==oldData || "1".equals(oldData.getBilltype())){
            return false;
        }
        matcostsInput.setRemainderamount(matcostsInput.getReimburseamount());
        matcostsInput.setFactoryamount(BigDecimal.ZERO);
        matcostsInput.setActingmatamount(matcostsInput.getFactoryamount().subtract(matcostsInput.getReimburseamount()));
        boolean flag = journalSheetMapper.updateMatcostsReimburse(matcostsInput) > 0;

        if(flag){
            //代垫收回 银行账户变更
            IAccountForOAService accountForOAService = (IAccountForOAService) SpringContextUtils.getBean("accountForOAService");
            if(StringUtils.isNotEmpty(oldData.getBankid())){
                BankAmountOthers oldBankAmountOthers = new BankAmountOthers();
                //借贷类型 1借(收入)2贷(支出)
                oldBankAmountOthers.setLendtype("2");
                oldBankAmountOthers.setBankid(oldData.getBankid());
                oldBankAmountOthers.setDeptid(oldData.getSupplierdeptid());
                //单据类型0期初1收款单2付款单3日常费用支付4客户费用支付5个人借款6预付款7转账8代垫收回
                oldBankAmountOthers.setBilltype("8");
                oldBankAmountOthers.setBillid(oldData.getId());
                oldBankAmountOthers.setOaid(oldData.getOaid());
                oldBankAmountOthers.setAmount(oldData.getReimburseamount());
                oldBankAmountOthers.setUpamount("");
                oldBankAmountOthers.setOppid(oldData.getSupplierid());
                BuySupplier buySupplier = getSupplierInfoById(oldData.getSupplierid());
                if(null!=buySupplier){
                    oldBankAmountOthers.setOppname(buySupplier.getName());
                }
                oldBankAmountOthers.setRemark("代垫收回修改");
                oldBankAmountOthers.setAdduserid(sysUser.getAdduserid());
                oldBankAmountOthers.setAddusername(sysUser.getName());
                oldBankAmountOthers.setAdddeptid(sysUser.getDepartmentid());
                oldBankAmountOthers.setAdddeptname(sysUser.getDepartmentname());
                accountForOAService.addBankAmountOthers(oldBankAmountOthers);
            }

            if(StringUtils.isNotEmpty(matcostsInput.getBankid())){
                BankAmountOthers newBankAmountOthers = new BankAmountOthers();
                //借贷类型 1借(收入)2贷(支出)
                newBankAmountOthers.setLendtype("1");
                newBankAmountOthers.setBankid(matcostsInput.getBankid());
                newBankAmountOthers.setDeptid(matcostsInput.getSupplierdeptid());
                //单据类型0期初1收款单2付款单3日常费用支付4客户费用支付5个人借款6预付款7转账8代垫收回
                newBankAmountOthers.setBilltype("8");
                newBankAmountOthers.setBillid(matcostsInput.getId());
                newBankAmountOthers.setOaid(matcostsInput.getOaid());
                newBankAmountOthers.setAmount(matcostsInput.getReimburseamount());
                newBankAmountOthers.setUpamount("");
                newBankAmountOthers.setOppid(matcostsInput.getSupplierid());
                BuySupplier buySupplier1 = getSupplierInfoById(matcostsInput.getSupplierid());
                if(null!=buySupplier1){
                    newBankAmountOthers.setOppname(buySupplier1.getName());
                }
                newBankAmountOthers.setRemark(matcostsInput.getRemark());
                newBankAmountOthers.setAdduserid(matcostsInput.getAdduserid());
                newBankAmountOthers.setAddusername(matcostsInput.getAddusername());
                newBankAmountOthers.setAdddeptid(sysUser.getDepartmentid());
                newBankAmountOthers.setAdddeptname(sysUser.getDepartmentname());

                accountForOAService.addBankAmountOthers(newBankAmountOthers);
            }

        }
        return flag;
	}
	

	@Override
	public boolean deleteMatcostsReimburse(String id) throws Exception {
		Map map=new HashMap();
        String dataSql = getDataAccessRule("t_js_matcostsinput",null); //数据权限
        if(null!=dataSql && !"".equals(dataSql.trim())){
            map.put("dataAuthSql", dataSql);
        }
        MatcostsInput matcostsInput=journalSheetMapper.getMatcostsInput(id);
        if(null==matcostsInput || "1".equals(matcostsInput.getBilltype())){
            return false;
        }
        map.put("id", id);
        map.put("deletebilltype", "2");	//收回
        boolean flag = journalSheetMapper.deleteMatcostsInputByAuth(map) > 0;
        if(flag && StringUtils.isNotEmpty(matcostsInput.getBankid())){
            //代垫收回 银行账户变更
            IAccountForOAService accountForOAService = (IAccountForOAService) SpringContextUtils.getBean("accountForOAService");
            BankAmountOthers bankAmountOthers = new BankAmountOthers();
            //借贷类型 1借(收入)2贷(支出)
            bankAmountOthers.setLendtype("2");
            bankAmountOthers.setBankid(matcostsInput.getBankid());
            bankAmountOthers.setDeptid(matcostsInput.getSupplierdeptid());
            //单据类型0期初1收款单2付款单3日常费用支付4客户费用支付5个人借款6预付款7转账8代垫收回
            bankAmountOthers.setBilltype("8");
            bankAmountOthers.setBillid(matcostsInput.getId());
            bankAmountOthers.setOaid(matcostsInput.getOaid());
            bankAmountOthers.setAmount(matcostsInput.getReimburseamount());
            bankAmountOthers.setUpamount("");
            bankAmountOthers.setOppid(matcostsInput.getSupplierid());
            BuySupplier buySupplier = getSupplierInfoById(matcostsInput.getSupplierid());
            if(null!=buySupplier){
                bankAmountOthers.setOppname(buySupplier.getName());
            }
            SysUser sysUser = getSysUser();
            bankAmountOthers.setRemark("代垫收回删除");
            bankAmountOthers.setAdduserid(matcostsInput.getAdduserid());
            bankAmountOthers.setAddusername(matcostsInput.getAddusername());
            bankAmountOthers.setAdddeptid(sysUser.getDepartmentid());
            bankAmountOthers.setAdddeptname(sysUser.getDepartmentname());

            accountForOAService.addBankAmountOthers(bankAmountOthers);
        }
		return flag;
	}
	@Override
	public PageData getMatcostsReimbursePageList(PageMap pageMap)throws Exception{
		String cols = getAccessColumnList("t_js_matcostsinput",null); //字段权限
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_js_matcostsinput",null); //数据权限
		pageMap.setDataSql(sql);
		Map condition=pageMap.getCondition();
		String showDyncReimburse =(String)condition.get("showDyncReimburse");
		StringBuilder sumsb=new StringBuilder();
		if("true".equals(showDyncReimburse)){
			List<SysCode> reimbursetypeList = getBaseSysCodeMapper().getSysCodeListForeign("matcreimbursetype");
			StringBuilder sb=new StringBuilder();
			for(SysCode sysCode : reimbursetypeList){
				if(null!=sysCode && StringUtils.isNotEmpty(sysCode.getCode())){
					sb.append(",IF(IFNULL(reimbursetype,'') ='");
					sb.append(sysCode.getCode());
					sb.append("' ,reimburseamount,0.000000) AS reimburse_");
					sb.append(sysCode.getCode());
					
					sumsb.append(",SUM(reimburse_");
					sumsb.append(sysCode.getCode());
					sumsb.append(")");
					sumsb.append(" AS reimburse_");
					sumsb.append(sysCode.getCode());
				}
			}
			if(sb.length()>0){
				condition.put("dyncReimburseColumn", sb.toString());
			}
		}else{
			if(condition.containsKey("dyncReimburseColumn")){
				condition.remove("dyncReimburseColumn");
			}
		}
		
		List<Map> list = journalSheetMapper.getMatcostsReimbursePageList(pageMap);
		if(list.size() != 0){
			String tmpstr="";
			List<SysCode> ReimburseCodeList = getBaseSysCodeMapper().getSysCodeListForeign("matcreimbursetype");
			for(Map map :list){
				tmpstr=(String)map.get("supplierdeptid");
				if(null!=tmpstr && !"".equals(tmpstr.trim())){//所属部门
					DepartMent departMent = departMentMapper.getDepartmentInfo(tmpstr.trim());
					if(null!=departMent){
						map.put("supplierdeptname",departMent.getName());
					}
				}

				tmpstr=(String)map.get("supplierid");
				if(null!=tmpstr && !"".equals(tmpstr.trim())){//供应商
					BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(tmpstr.trim());
					if(null!=buySupplier){
						map.put("suppliername",buySupplier.getName());
					}
				}
				
				tmpstr=(String)map.get("reimbursetype");
				if(null!=tmpstr && !"".equals(tmpstr.trim())){//收回方式
					if(ReimburseCodeList.size() != 0){
						for(SysCode sysCode:ReimburseCodeList){
							if(tmpstr.trim().equals(sysCode.getCode())){
								map.put("reimbursetypename",sysCode.getCodename());
								break;
							}
						}
					}
				}
				tmpstr=(String)map.get("bankid");
				if(null!=tmpstr && !"".equals(tmpstr.trim())){//银行名称
					Bank bank=getBankInfoByID(tmpstr.trim());
					if(null!=bank){
						map.put("bankname", bank.getName());
					}
				}
				tmpstr=(String)map.get("unitid");
				if(null!=tmpstr && !"".equals(tmpstr.trim())){ //单位
					MeteringUnit meteringUnit=getMeteringUnitById(tmpstr.trim());
					if(null!=meteringUnit){
						map.put("unitname", meteringUnit.getName());
					}
				}
				tmpstr=(String)map.get("shsubjectid");
				if(null!=tmpstr && !"".equals(tmpstr.trim())){ //收回科目
					Subject subject=getSubjectInfoById(tmpstr.trim());
					if(null!=subject){
						map.put("shsubjectname", subject.getThisname());
					}
				}
				
				String iswriteoff=(String)map.get("iswriteoff");
				if(null!=iswriteoff){
					if("1".equals(iswriteoff.trim())){//是否核销
						map.put("iswriteoffname", "核销");					
					}else if("2".equals(iswriteoff.trim())){
						map.put("iswriteoffname", "核销中");								
					}else{
						map.put("iswriteoffname", "");
						map.put("writeoffdate", null);
					}
				}else{
					map.put("iswriteoffname", "");
					map.put("writeoffdate", null);
				}
			}
		}

		if(sumsb.length()>0){
			condition.put("dyncReimburseSumColumn", sumsb.toString());
		}
		List<Map> footerList = new ArrayList<Map>();
		Map sumMap = journalSheetMapper.getMatcostsReimburseSums(pageMap);
		if(null!=sumMap){
			sumMap.put("suppliername","合计");
			footerList.add(sumMap);
		}
		PageData pageData = new PageData(journalSheetMapper.getMatcostsReimburseListCount(pageMap),list,pageMap);
		pageData.setFooter(footerList);
		return pageData;
	}
	@Override
	public MatcostsInput getMatcostsReimburseDetail(String id) throws Exception {
		
		Map map = new HashMap();
		map.put("id", id);
		/*
		String dataSql=getDataAccessRule("t_js_matcostsinput",null);
		if(null!=dataSql && !"".equals(dataSql.trim())){
			map.put("dataSql", dataSql.trim());
		}
		*/
		map.put("billtype", "2");
		MatcostsInput matcostsInput= journalSheetMapper.getMatcostsInputDetail(map);
		
		//MatcostsInput matcostsInput= journalSheetMapper.getMatcostsInput(id);
		if(null!=matcostsInput){			
			if(StringUtils.isNotEmpty(matcostsInput.getSupplierid())){//供应商
				BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(matcostsInput.getSupplierid());
				if(null!=buySupplier){
					matcostsInput.setSuppliername(buySupplier.getName());
				}
			}
		}
		return matcostsInput;
	}
	
	@Override
	public Map addDRMatcostsReimburse(List<Map> list)
			throws Exception {
        int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
        Map msgmap = new HashMap();
		boolean flag = false;
		int successNum = 0,failureNum = 0,errorNum = 0;
		StringBuilder failSb = new StringBuilder();
		StringBuilder errorSb  = new StringBuilder();
		SysUser sysUser = getSysUser();
        String tmpStr="";
		boolean isId=isAutoCreate("t_js_matcostsreimburse");
		if(null != list && list.size() != 0){
			List<SysCode> reimbursetypeList = getBaseSysCodeMapper().getSysCodeListForeign("matcreimbursetype");
			MatcostsInput matcostsInput=null;
			for(Map map : list){
				try{
					if(null==map){
						continue;
					}
					matcostsInput=new MatcostsInput();
					matcostsInput.setSourcefrome("1");
					matcostsInput.setBusinessdate((String) map.get("businessdate"));
					if(StringUtils.isEmpty(matcostsInput.getBusinessdate())){
						matcostsInput.setBusinessdate(CommonUtils.getTodayDataStr());
					}
					matcostsInput.setSupplierid((String)map.get("supplierid"));
					matcostsInput.setSuppliername((String) map.get("suppliername"));
					if(StringUtils.isNotEmpty(matcostsInput.getSupplierid())){//供应商
						BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(matcostsInput.getSupplierid());
						if(null!=buySupplier){
							matcostsInput.setSuppliername(buySupplier.getName());
							matcostsInput.setSupplierdeptid(buySupplier.getBuydeptid());
						}else{
							failureNum=failureNum+1;
							failSb.append(" 未找到供应商信息");
							if(StringUtils.isNotEmpty(matcostsInput.getBusinessdate())){
								failSb.append(" 业务日期："+matcostsInput.getBusinessdate());
							}
							if(StringUtils.isNotEmpty(matcostsInput.getSupplierid())){
								failSb.append(" 供应商编码："+matcostsInput.getSupplierid());
							}else if(StringUtils.isNotEmpty(matcostsInput.getSuppliername())){
								failSb.append(" 导入的供应商名称："+matcostsInput.getSuppliername());
							}	
							continue;
						}
					}else{
						failureNum=failureNum+1;
						failSb.append(" 未找到供应商信息");
						if(StringUtils.isNotEmpty(matcostsInput.getBusinessdate())){
							failSb.append(" 业务日期："+matcostsInput.getBusinessdate());
						}
						if(StringUtils.isNotEmpty(matcostsInput.getSupplierid())){
							failSb.append(" 供应商编码："+matcostsInput.getSupplierid());
						}else if(StringUtils.isNotEmpty(matcostsInput.getSuppliername())){
							failSb.append(" 导入的供应商名称："+matcostsInput.getSuppliername());
						}
					}
					
					matcostsInput.setReimburseamount(BigDecimal.ZERO);
					if(reimbursetypeList.size() != 0){
						BigDecimal tmpBig=null;
						for(SysCode sysCode:reimbursetypeList){
							if(StringUtils.isNotEmpty(sysCode.getCodename()) && StringUtils.isNotEmpty(sysCode.getCode())){
                                tmpStr=(String)map.get("reimburse_"+sysCode.getCode());
                                if(null!=tmpStr && !"".equals(tmpStr.trim()) && CommonUtils.isNumStr(tmpStr)){
                                    tmpBig=new BigDecimal(tmpStr);
                                    if(null!=tmpBig && BigDecimal.ZERO.compareTo(tmpBig)!=0){
                                        matcostsInput.setReimbursetype(sysCode.getCode());
                                        matcostsInput.setReimburseamount(tmpBig);
                                        break;
                                    }
                                }
                            }
                        }
                    }
					matcostsInput.setShsubjectid((String)map.get("shsubjectid"));
					if(StringUtils.isNotEmpty(matcostsInput.getShsubjectid())){
						Subject subject=getSubjectInfoById(matcostsInput.getShsubjectid());
						if(null==subject || !"DDREIMBURSE_SUBJECT".equals(subject.getTypecode())){
							matcostsInput.setShsubjectid("");
						}
					}
                    matcostsInput.setReimbursetypename((String)map.get("reimbursetypename"));
					/*
					tmpStr=(String)map.get("reimburseamount");
					if(StringUtils.isNotEmpty(tmpStr) && CommonUtils.isNumStr(tmpStr)){
						matcostsInput.setReimburseamount(new BigDecimal(tmpStr));
					}
					*/
					if(null==matcostsInput.getReimburseamount()){
						matcostsInput.setReimburseamount(BigDecimal.ZERO);
					}					

					matcostsInput.setUnitname((String)map.get("unitname"));
					if(StringUtils.isNotEmpty(matcostsInput.getUnitname())){
						MeteringUnit meteringUnit=getBaseFilesGoodsMapper().getMeteringUnitByName(matcostsInput.getUnitname());
						if(null!=meteringUnit){
							matcostsInput.setUnitid(meteringUnit.getId());
						}
					}
					String unitnumStr=(String)map.get("unitnum");
					if(null==unitnumStr || "".equals(unitnumStr.trim()) || !CommonUtils.isNumeric(unitnumStr.trim())){
						unitnumStr="1";
					}
					String taxpriceStr=(String)map.get("taxprice");
					if(null==taxpriceStr || "".equals(taxpriceStr.trim()) || !CommonUtils.isNumeric(taxpriceStr.trim())){
						taxpriceStr="0";
					}
					BigDecimal unitnum=new BigDecimal(unitnumStr);
					if(unitnum.compareTo(BigDecimal.ZERO)==0 ){
						unitnum=BigDecimal.ONE;
					}
					BigDecimal taxprice=new BigDecimal(taxpriceStr);
					BigDecimal amount=matcostsInput.getReimburseamount();
                    if(decimalScale == 0){
                        unitnum.setScale(decimalScale,BigDecimal.ROUND_DOWN);
                    }else{
                        unitnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
                    }
					if(amount.compareTo(BigDecimal.ZERO)==0){
						taxprice=BigDecimal.ZERO;
					}else{
						amount=amount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
						
						taxprice=amount.divide(unitnum, 6, BigDecimal.ROUND_HALF_UP);
					}
					
					matcostsInput.setUnitnum(unitnum);
					matcostsInput.setTaxprice(taxprice);
					
					
					if(null==matcostsInput.getFactoryamount()){
						matcostsInput.setFactoryamount(BigDecimal.ZERO);
					}
					
					matcostsInput.setActingmatamount(matcostsInput.getFactoryamount().subtract(matcostsInput.getReimburseamount()));
					
					matcostsInput.setRemainderamount(matcostsInput.getReimburseamount());	//未核销收回余额
					
					matcostsInput.setRemark((String)map.get("remark"));
					if(StringUtils.isNotEmpty(matcostsInput.getRemark())){
						if(matcostsInput.getRemark().length()>200){
							matcostsInput.setRemark(matcostsInput.getRemark().substring(0,200));
						}
					}
					if(null!=matcostsInput){					
						
						
						matcostsInput.setAdduserid(sysUser.getUserid());
						matcostsInput.setAddusername(sysUser.getName());
						if (isId) {
							// 获取自动编号
							matcostsInput.setId(getAutoCreateSysNumbderForeign(matcostsInput, "t_js_matcostsreimburse"));
						}else{
							matcostsInput.setId(null);
						}
                        if(addMatcostsReimburse(matcostsInput)){
							successNum=successNum+1;
						}else{
							failureNum=failureNum+1;
							if(StringUtils.isNotEmpty(matcostsInput.getBusinessdate())){
								failSb.append(" 业务日期："+matcostsInput.getBusinessdate());
							}							
							if(StringUtils.isNotEmpty(matcostsInput.getSupplierid())){
								failSb.append(" 供应商编码："+matcostsInput.getSupplierid());
							}
							
						}
					}
				}catch (Exception e) {
					errorNum=errorNum+1;
					
					if(null==matcostsInput){
						continue;
					}
					if(StringUtils.isNotEmpty(matcostsInput.getBusinessdate())){
						errorSb.append(" 业务日期："+matcostsInput.getBusinessdate());
					}					
					if(StringUtils.isNotEmpty(matcostsInput.getSupplierid())){
						errorSb.append(" 供应商编码："+matcostsInput.getSupplierid());
					}
									
				}
			}
		}
		else{
			msgmap.put("msg", "导入的数据为空!");
		}
		if(successNum>0){
			msgmap.put("flag", true);
		}else{
			msgmap.put("flag", false);			
		}
		msgmap.put("success", successNum);
		msgmap.put("failure", failureNum);
		msgmap.put("failStr", failSb.toString());
		msgmap.put("errorNum", errorNum);
		msgmap.put("errorVal", errorSb.toString());
		return msgmap;
	}
	@Override
	public List<MatcostsInput> getMatcostsReimburseListBy(Map map) throws Exception{
		String noDataSql=(String)map.get("noDataSql");
		if(!"1".equals(noDataSql)){
			String dataSql = getDataAccessRule("t_js_matcostsinput", null);
			map.put("dataSql", dataSql);
		}
		List<MatcostsInput> list=journalSheetMapper.getMatcostsReimburseListBy(map);
		List<SysCode> ReimburseCodeList = getBaseSysCodeMapper().getSysCodeListForeign("matcreimbursetype");
		for(MatcostsInput item :list){
			
			if(StringUtils.isNotEmpty(item.getSupplierdeptid())){//所属部门
				DepartMent departMent = departMentMapper.getDepartmentInfo(item.getSupplierdeptid());
				if(null!=departMent){
					item.setSupplierdeptname(departMent.getName());
				}
			}

			if(StringUtils.isNotEmpty(item.getSupplierid())){//供应商
				BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(item.getSupplierid());
				if(null!=buySupplier){
					item.setSuppliername(buySupplier.getName());
				}
			}
			
			if(StringUtils.isNotEmpty(item.getReimbursetype())){//收回方式
				if(ReimburseCodeList.size() != 0){
					for(SysCode sysCode:ReimburseCodeList){
						if(item.getReimbursetype().equals(sysCode.getCode())){
							item.setReimbursetypename(sysCode.getCodename());
							break;
						}
					}
				}
			}
			if(StringUtils.isNotEmpty(item.getBankid())){//银行名称
				Bank bank=getBankInfoByID(item.getBankid());
				if(null!=bank){
					item.setBankname( bank.getName());
				}
			}
			if(StringUtils.isNotEmpty(item.getUnitid())){ //单位
				MeteringUnit meteringUnit=getMeteringUnitById(item.getUnitid());
				if(null!=meteringUnit){
					item.setUnitname( meteringUnit.getName());
				}
			}
			if(StringUtils.isNotEmpty(item.getShsubjectid())){ //科目名称
				Subject subject=getSubjectInfoById(item.getShsubjectid());
				if(null!=subject){
					item.setShsubjectname(subject.getThisname());
				}
			}
		}
		return list;
	}
	@Override
	public boolean addMatcostsReimburseWriteoff(Map map) throws Exception{
		String inputIdarrs=(String)map.get("inputIdarrs");
		if(null==inputIdarrs || "".equals(inputIdarrs.trim())){
			return false;
		}
		String reimburseIdarrs=(String)map.get("reimburseIdarrs");
		if(null==reimburseIdarrs || "".equals(reimburseIdarrs.trim())){
			return false;
		}
		String businessdate=(String)map.get("businessdate");
		if(null==businessdate || "".equals(businessdate.trim())){
			return false;
		}
		boolean flag=true;
		PageMap pageMap=new PageMap();
		Map condition=new HashMap();
		condition.put("idarrs", inputIdarrs);
		condition.put("isPageflag", "true");
		condition.put("showNotWriteoff", "true");
		condition.put("isQueryByOrder", "true");
		condition.put("orderSort", "factoryamount");
		condition.put("orderBy", "asc");
		pageMap.setCondition(condition);
		
		Map inputSum=journalSheetMapper.getMatcostsInputSums(pageMap);
		//String sql = getDataAccessRule("t_js_matcostsinput",null); //数据权限
		//pageMap.setDataSql(sql);
		List<MatcostsInput> inputList = journalSheetMapper.getMatcostsInputList(pageMap);
		
		condition.clear();
		condition.put("idarrs", reimburseIdarrs);
		condition.put("isPageflag", "true");
		condition.put("showNotWriteoff", "true");
		condition.put("isQueryByOrder", "true");
		condition.put("orderSort", "remainderamount");
		condition.put("orderBy", "asc");
		pageMap.setCondition(condition);
		//String sql = getDataAccessRule("t_js_matcostsinput",null); //数据权限
		//pageMap.setDataSql(sql);
		Map reimburseSum=journalSheetMapper.getMatcostsReimburseSums(pageMap);
		List<MatcostsInput> reimburseList = journalSheetMapper.getMatcostsReimburseList(pageMap);
		
		if(null==inputSum || null==reimburseSum){
			return false;
		}
		BigDecimal sumFactoryamount=(BigDecimal)inputSum.get("factoryamount");
		BigDecimal sumRemainderamount=(BigDecimal) reimburseSum.get("remainderamount");
		if(null==sumFactoryamount || null==sumRemainderamount || sumRemainderamount.compareTo(sumFactoryamount)<0){
			return false;
		}
		if((null==inputList || inputList.size()==0) || (null==reimburseList || reimburseList.size()==0) ) {
			return false;
		}
		//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 回滚
		
		SysUser sysUser=getSysUser();
		int inputLength=inputList.size();
		int writeOffLength=0;
		BigDecimal inputAmount=BigDecimal.ZERO;
		BigDecimal reimburseAmount=BigDecimal.ZERO;
		BigDecimal writeoffAmount=BigDecimal.ZERO;
		for(MatcostsInput inputItem: inputList){
			if(null==inputItem  || "1".equals(inputItem.getIswriteoff())){
				continue;
			}
			if(null==inputItem.getFactoryamount()){
				inputItem.setFactoryamount(BigDecimal.ZERO);
			}
			if(null==inputItem.getWriteoffamount()){
				inputItem.setWriteoffamount(BigDecimal.ZERO);
			}
			inputItem.setRemainderamount(inputItem.getFactoryamount().subtract(inputItem.getWriteoffamount()));	//代垫余额=工厂投入-核销金额
			
			for(MatcostsInput reimburseItem:reimburseList){
				if(null==reimburseItem  || "1".equals(reimburseItem.getIswriteoff())){
					continue;
				}
                if(BigDecimal.ZERO.compareTo(inputItem.getRemainderamount())==0 && "1".equals(inputItem.getIswriteoff())){
                    break;
                }

				if(null==reimburseItem.getWriteoffamount()){
					reimburseItem.setWriteoffamount(BigDecimal.ZERO);	//核销金额
				}
				reimburseItem.setRemainderamount(reimburseItem.getReimburseamount().subtract(reimburseItem.getWriteoffamount()));//收回余额=收回金额-核销金额
				//代垫与收回哪个多
				if(reimburseItem.getRemainderamount().compareTo(inputItem.getRemainderamount())>=0){
					writeoffAmount=inputItem.getRemainderamount();	//代垫剩余
				}else{
					writeoffAmount=reimburseItem.getRemainderamount();
				}
				//核销记录
				MatcostsInputStatement matcostsInputStatement=new MatcostsInputStatement();
				matcostsInputStatement.setPayid(inputItem.getId());
				matcostsInputStatement.setTakebackid(reimburseItem.getId());
				matcostsInputStatement.setWriteoffdate(businessdate);
				matcostsInputStatement.setFactoryamount(inputItem.getFactoryamount());
				matcostsInputStatement.setReimbursetype(reimburseItem.getReimbursetype());
				matcostsInputStatement.setRelateamount(writeoffAmount);
				matcostsInputStatement.setRemainderamount(inputItem.getRemainderamount());
				matcostsInputStatement.setTakebackamount(reimburseItem.getReimburseamount());
				matcostsInputStatement.setTbremainderamount(reimburseItem.getRemainderamount());
				matcostsInputStatement.setIswriteoff("1");	// 暂时设置为未核销
				matcostsInputStatement.setAdduserid(sysUser.getUserid());	//添加者编号
				matcostsInputStatement.setAddusername(sysUser.getName());	//添加者名称
				
				if(journalSheetMapper.insertMatcostsStatement(matcostsInputStatement)>0){
					//代垫
					inputItem.setWriteoffamount(inputItem.getWriteoffamount().add(writeoffAmount));	//核销金额相加
					inputItem.setRemainderamount(inputItem.getRemainderamount().subtract(writeoffAmount));	//代垫余额
					
					//收回
					reimburseItem.setWriteoffamount(reimburseItem.getWriteoffamount().add(writeoffAmount)); 	//核销金额相加
					reimburseItem.setRemainderamount(reimburseItem.getReimburseamount().subtract(reimburseItem.getWriteoffamount())); //收回余额=收回-核销

					reimburseItem.setWriteoffdate(CommonUtils.getTodayDataStr());
					reimburseItem.setWriteoffer(sysUser.getUserid());
					reimburseItem.setWriteoffername(sysUser.getName());
					if(BigDecimal.ZERO.compareTo(reimburseItem.getRemainderamount())==0){
						reimburseItem.setIswriteoff("1");
					}else{
						reimburseItem.setIswriteoff("2");
					}
					if(!updateMatcostsReimburseWriteoff(reimburseItem)){
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	//如何保存失败后回滚
						return false;
					}
                    if(BigDecimal.ZERO.compareTo(inputItem.getRemainderamount())==0){
                        inputItem.setIswriteoff("1");
                    }
				}else{
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	//如何保存失败后回滚
					return false;
				}
			}
			if(BigDecimal.ZERO.compareTo(inputItem.getRemainderamount())==0){
				inputItem.setWriteoffdate(CommonUtils.getTodayDataStr());
				inputItem.setWriteoffer(sysUser.getUserid());
				inputItem.setWriteoffername(sysUser.getName());
				inputItem.setIswriteoff("1");
				
				if(!updateMatcostsInputWriteoff(inputItem)){
					flag=false;
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	//如何保存失败后回滚
				}
			}else{
				flag=false;
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	//回滚				
			}
		}		
		
		return flag;
	}
	@Override
	public Map updateMatcostsInputRewriteoff(String inputId) throws Exception{
		Map map=new HashMap();
		
		if(null==inputId || "".equals(inputId.trim())){
			map.put("flag", false);
			map.put("msg", "未能找到代垫信息");
			return map;
		}
		MatcostsInput matcostsInput = getMatcostsInputDetail(inputId.trim());
		if(null==matcostsInput){
			map.put("flag", false);
			map.put("msg", "未能找到代垫信息");
			return map;			
		}
		if(!"1".equals(matcostsInput.getIswriteoff())){
			map.put("flag", false);
			map.put("msg", "当前代垫未核销，不能进行反核销");
			return map;			
		}
		SysUser sysUser=getSysUser();
		Map queryMap=new HashMap();
		queryMap.put("payid", inputId.trim());
		List<MatcostsInputStatement> list=journalSheetMapper.getMatcostsStatementList(queryMap);
		if(null==list || list.size()==0){
			map.put("flag", false);
			map.put("msg", "代垫"+inputId+"未能找到相关核销记录");
			return map;			
		}
		for(MatcostsInputStatement item:list){
			if(null==item){
				continue;
			}
			
			matcostsInput.setWriteoffamount(matcostsInput.getWriteoffamount().subtract(item.getRelateamount()));
			
			/*
			if(BigDecimal.ZERO.compareTo(item.getRelateamount())==0){
				if(journalSheetMapper.deleteMatcostsStatement(item.getId().toString())==0){
				}
				continue;
			}
			*/
			
			MatcostsInput matcostsReimburse=getMatcostsReimburseDetail(item.getTakebackid());
			if(null==matcostsReimburse){	//未能找到收回，删除
				if(journalSheetMapper.deleteMatcostsStatement(item.getId().toString())==0){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	//异常回滚
					map.put("flag", false);
					return map;	
				}else{
					continue;
				}
			}
			
			if(!"1".equals(matcostsReimburse.getIswriteoff()) && !"2".equals(matcostsReimburse.getIswriteoff()) && BigDecimal.ZERO.compareTo(item.getRelateamount())==0 ){	//未处于核销状态
				//未能找到收回，删除
				if(journalSheetMapper.deleteMatcostsStatement(item.getId().toString())==0){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	//异常回滚
					map.put("flag", false);
					return map;	
				}else{
					continue;
				}
			}
			
			matcostsReimburse.setWriteoffamount(matcostsReimburse.getWriteoffamount().subtract(item.getRelateamount()));
			if(BigDecimal.ZERO.compareTo(matcostsReimburse.getWriteoffamount())>0){	//核销金额小于零，异常
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	//如何保存失败后回滚
				map.put("flag", false);
				return map;	
			}else if(BigDecimal.ZERO.compareTo(matcostsReimburse.getWriteoffamount())<0){	//反核销 中。。。
				matcostsReimburse.setIswriteoff("2");

				matcostsReimburse.setWriteoffdate(CommonUtils.getTodayDataStr());	//收回核销中，最新核销信息
				matcostsReimburse.setWriteoffer(sysUser.getUserid());
				matcostsReimburse.setWriteoffername(sysUser.getName());
			}else{
				matcostsReimburse.setIswriteoff("0");	//反核销完成
				
				matcostsReimburse.setWriteoffdate("");	//收回核销中，最新核销信息
				matcostsReimburse.setWriteoffer("");
				matcostsReimburse.setWriteoffername("");
			}
			if(!updateMatcostsReimburseWriteoff(matcostsReimburse)){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	//如何保存失败后回滚
				map.put("flag", false);
				return map;	
			}
			if(journalSheetMapper.deleteMatcostsStatement(item.getId().toString())==0){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	//异常回滚
				map.put("flag", false);
				return map;	
			}
		}
		if(BigDecimal.ZERO.compareTo(matcostsInput.getWriteoffamount())!=0){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	//异常回滚
			map.put("flag", false);
			return map;	
		}else{
			matcostsInput.setIswriteoff("0");
		}
		//代垫核销中，最新核销信息
		matcostsInput.setWriteoffdate("");
		matcostsInput.setWriteoffer("");
		matcostsInput.setWriteoffername("");
		if(!updateMatcostsInputWriteoff(matcostsInput)){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	//异常回滚
			map.put("flag", false);
			return map;				
		}
		map.clear();
		map.put("flag", true);
		return map;
	}
	//更新代垫核销
	private boolean updateMatcostsInputWriteoff(MatcostsInput matcostsInput) throws Exception{
		MatcostsInput mat=new MatcostsInput();
		mat.setId(matcostsInput.getId());	//代垫编号 
		mat.setFactoryamount(matcostsInput.getFactoryamount());	//工厂金额
		mat.setIswriteoff(matcostsInput.getIswriteoff());	//是否核销
		mat.setWriteoffdate(matcostsInput.getWriteoffdate()); //核销日期
		mat.setWriteoffer(matcostsInput.getWriteoffer());	//核销人编号
		mat.setWriteoffername(matcostsInput.getWriteoffername());	//核销人员名称
		if(null==mat.getFactoryamount()){
			mat.setFactoryamount(BigDecimal.ZERO);	//工厂金额为零
		}
		mat.setWriteoffamount(matcostsInput.getWriteoffamount());	//核销金额就是代垫的收回金额
		mat.setRemainderamount(mat.getFactoryamount().subtract(matcostsInput.getWriteoffamount()));	//代垫的余额=工厂投入-核销金额
		
		return journalSheetMapper.updateMatcostsInputWriteoff(mat)>0;
	}
	//更新收回核销
	private boolean updateMatcostsReimburseWriteoff(MatcostsInput matcostsReimburse) throws Exception{
		MatcostsInput mat=new MatcostsInput();
		mat.setId(matcostsReimburse.getId());	//收回编号 
		mat.setIswriteoff(matcostsReimburse.getIswriteoff());	//是否核销
		mat.setWriteoffdate(matcostsReimburse.getWriteoffdate());	//核销日期
		mat.setWriteoffer(matcostsReimburse.getWriteoffer());	//核销人编号
		mat.setWriteoffername(matcostsReimburse.getWriteoffername());	//核销人员名称
		mat.setWriteoffamount(matcostsReimburse.getWriteoffamount());	//
		mat.setRemainderamount(matcostsReimburse.getReimburseamount().subtract(matcostsReimburse.getWriteoffamount()));	//代垫的余额=收回金额-核销金额
		
		return journalSheetMapper.updateMatcostsInputWriteoff(mat)>0;
	}
	
	@Override
	public PageData getMatcostsStatementPageListData(PageMap pageMap) throws Exception{
		Map condition=pageMap.getCondition();
		String showDyncReimburse =(String)condition.get("showDyncReimburse");
		StringBuilder sumsb=new StringBuilder();
		if("true".equals(showDyncReimburse)){
			List<SysCode> reimbursetypeList = getBaseSysCodeMapper().getSysCodeListForeign("matcreimbursetype");
			StringBuilder sb=new StringBuilder();
			for(SysCode sysCode : reimbursetypeList){
				if(null!=sysCode && StringUtils.isNotEmpty(sysCode.getCode())){
					sb.append(",IF(IFNULL(a.reimbursetype,'') ='");
					sb.append(sysCode.getCode());
					sb.append("' ,a.relateamount,0.000000) AS reimburse_");
					sb.append(sysCode.getCode());
					
					sumsb.append(",SUM(reimburse_");
					sumsb.append(sysCode.getCode());
					sumsb.append(")");
					sumsb.append(" AS reimburse_");
					sumsb.append(sysCode.getCode());
				}
			}
			if(sb.length()>0){
				condition.put("dyncReimburseColumn", sb.toString());
			}
		}else{
			if(condition.containsKey("dyncReimburseColumn")){
				condition.remove("dyncReimburseColumn");
			}
		}
		
		List<Map> list = journalSheetMapper.getMatcostsStatementPageList(pageMap);
		if(list.size() != 0){
			String tmpstr="";
			for(Map map :list){
				tmpstr=(String)map.get("supplierdeptid");
				if(null!=tmpstr && !"".equals(tmpstr.trim())){//所属部门
					DepartMent departMent = departMentMapper.getDepartmentInfo(tmpstr.trim());
					if(null!=departMent){
						map.put("supplierdeptname",departMent.getName());
					}
				}

				tmpstr=(String)map.get("supplierid");
				if(null!=tmpstr && !"".equals(tmpstr.trim())){//供应商
					BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(tmpstr.trim());
					if(null!=buySupplier){
						map.put("suppliername",buySupplier.getName());
					}
				}
				
				tmpstr=(String)map.get("reimbursetype");
				if(null!=tmpstr && !"".equals(tmpstr.trim())){//收回方式
					List<SysCode> codelist = getBaseSysCodeMapper().getSysCodeListForeign("matcreimbursetype");
					if(codelist.size() != 0){
						for(SysCode sysCode:codelist){
							if(tmpstr.trim().equals(sysCode.getCode())){
								map.put("reimbursetypename",sysCode.getCodename());
								break;
							}
						}
					}
				}				
				String iswriteoff=(String)map.get("iswriteoff");
				if(null!=iswriteoff){
					if("1".equals(iswriteoff.trim())){//是否核销
						map.put("iswriteoffname", "核销");
					}
				}		
			}
		}
		if(sumsb.length()>0){
			condition.put("dyncReimburseSumColumn", sumsb.toString());
		}
		List<Map> footerList = new ArrayList<Map>();
		Map sumMap = journalSheetMapper.getMatcostsStatementSums(pageMap);
		if(null!=sumMap){
			sumMap.put("suppliername","合计");
			footerList.add(sumMap);
		}
		PageData pageData = new PageData(journalSheetMapper.getMatcostsStatementPageCount(pageMap),list,pageMap);
		pageData.setFooter(footerList);
		return pageData;
	}
	
	@Override
	public PageData getMatcostsInputWriteoffReportData(PageMap pageMap) throws Exception{
		Map condition=pageMap.getCondition();
		String sqlm = getDataAccessRule("t_js_matcostsinput","m"); //数据权限
		String sql = getDataAccessRule("t_js_matcostsinput",null); //数据权限
		condition.put("QueryDataSqlM", sqlm);
		condition.put("QueryDataSql", sql);
		StringBuilder sumsb=new StringBuilder();
		StringBuilder reimColumn=new StringBuilder();
		StringBuilder sb=new StringBuilder();
		String isExportData=(String)condition.get("isExportData");
		if("true".equals(isExportData)){
			condition.put("isPageflag", "true");
		}
		List<SysCode> reimbursetypeList = getBaseSysCodeMapper().getSysCodeListForeign("matcreimbursetype");
		for(SysCode sysCode : reimbursetypeList){
			if(null!=sysCode && StringUtils.isNotEmpty(sysCode.getCode())){
				sb.append(",IF(IFNULL(reimbursetype,'') ='");
				sb.append(sysCode.getCode());
				sb.append("' ,writeoffamount,0.000000) AS reimburse_");
				sb.append(sysCode.getCode());
				
				sumsb.append(",SUM(reimburse_");
				sumsb.append(sysCode.getCode());
				sumsb.append(")");
				sumsb.append(" AS reimburse_");
				sumsb.append(sysCode.getCode());
				
				reimColumn.append(" ,reimburse_");
				reimColumn.append(sysCode.getCode());
			}
		}
		if(sb.length()>0){
			condition.put("dyncReimburseColumn", sb.toString());
			condition.put("dyncReimburseSumColumn", sumsb.toString());
			condition.put("reimburseSumColumn", reimColumn.toString());
		}else{
			if(condition.containsKey("dyncReimburseColumn")){
				condition.remove("dyncReimburseColumn");
			}
			if(condition.containsKey("dyncReimburseSumColumn")){
				condition.remove("dyncReimburseSumColumn");
			}
			if(condition.containsKey("reimburseSumColumn")){
				condition.remove("reimburseSumColumn");
			}
		}
		//小计列
		String groupcols = (String)condition.get("groupcols");
		if(!"supplierid".equals(groupcols) && !"id".equals(groupcols)){
			condition.put("groupcols", "id");
			groupcols = "id";
		}
		List<Map> list=journalSheetMapper.getMatcostsInputWriteoffReportList(pageMap);
		List<ExpensesSort> subjectCodelist = getBaseFinanceMapper().getExpensesSortByStateList();
		for(Map map : list){
			String datatmp=(String)map.get("supplierid");
			if(null!=datatmp && !"".equals(datatmp.trim())){//供应商
				BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(datatmp.trim());
				if(null!=buySupplier){
					map.put("suppliername", buySupplier.getName());
				}
			}
			datatmp=(String)map.get("supplierdeptid");
			if(null!=datatmp && !"".equals(datatmp.trim())){//所属部门
				DepartMent departMent = departMentMapper.getDepartmentInfo(datatmp.trim());
				if(null!=departMent){
					map.put("supplierdeptname",departMent.getName());
				}
			}
			if("id".equals(groupcols)){
				datatmp=(String)map.get("customerid");
				if(null!=datatmp && !"".equals(datatmp.trim())){//客户编号
					Customer customer = getCustomerByID(datatmp.trim());
					if(null != customer){
						map.put("customername", customer.getName());
					}
				}
				datatmp=(String)map.get("subjectid");
				if(null!=datatmp && !"".equals(datatmp.trim())){
					if(null!=subjectCodelist && subjectCodelist.size() != 0){
						for(ExpensesSort expensesSort:subjectCodelist){
							if(datatmp.trim().equals(expensesSort.getId())){
								map.put("subjectname", expensesSort.getThisname());
								break;
							}
						}
					}
				}	
				datatmp=(String)map.get("brandid");
				if(null!=datatmp && !"".equals(datatmp.trim())){//商品品牌
					Brand brand = goodsMapper.getBrandInfo(datatmp.trim());
					if(null != brand){
						map.put("brandname", brand.getName());
					}
				}
                datatmp=(String)map.get("iswriteoff");
                if(null!=datatmp && !"".equals(datatmp.trim())){//商品品牌
                    if("1".equals(datatmp.trim())){
                        map.put("iswriteoffname", "核销");
                    }else{
                        map.put("iswriteoffname", "未核销");
                    }
                }
			}
		}

		if(condition.containsKey("dyncReimburseColumn")){
			condition.remove("dyncReimburseColumn");
		}
		if(condition.containsKey("dyncReimburseSumColumn")){
			condition.remove("dyncReimburseSumColumn");
		}
		if(condition.containsKey("reimburseSumColumn")){
			condition.remove("reimburseSumColumn");
		}
		int total=0;
		if(!"true".equals(isExportData)){
			total=journalSheetMapper.getMatcostsInputWriteoffReportCount(pageMap);
		}
		PageData pageData=new PageData(total, list, pageMap);
		if(sb.length()>0){
			condition.put("dyncReimburseColumn", sb.toString());
			condition.put("dyncReimburseSumColumn", sumsb.toString());
			condition.put("reimburseSumColumn", reimColumn.toString());
			
		}
		condition.put("showSumAll", "true");
		List<Map> footerList = new ArrayList<Map>();
		Map sumMap = journalSheetMapper.getMatcostsInputWriteoffReportSums(pageMap);
		if(null!=sumMap){
			sumMap.put("id", "");
			sumMap.put("businessdate", "");
			sumMap.put("oaid", "");
			sumMap.put("supplierid", "");
            sumMap.put("brandid", "");
            sumMap.put("customerid", "");
			sumMap.put("writeoffdate", "");
			sumMap.put("iswriteoff", "");
			sumMap.put("writeoffer", "");
			sumMap.put("writeoffername", "");
			sumMap.put("suppliername","合计");
			footerList.add(sumMap);
		}
		pageData.setFooter(footerList);
		return pageData;
	}
	@Override
	public PageData getMatcostsReimburseWriteoffReportData(PageMap pageMap) throws Exception{
		Map condition=pageMap.getCondition();
		String sqlm = getDataAccessRule("t_js_matcostsinput","m"); //数据权限
		String sql = getDataAccessRule("t_js_matcostsinput",null); //数据权限
		condition.put("QueryDataSqlM", sqlm);
		condition.put("QueryDataSql", sql);
		StringBuilder sumsb=new StringBuilder();
		StringBuilder reimColumn=new StringBuilder();
		StringBuilder sb=new StringBuilder();
		String isExportData=(String)condition.get("isExportData");
		if("true".equals(isExportData)){
			condition.put("isPageflag", "true");
		}
		List<SysCode> reimbursetypeList = getBaseSysCodeMapper().getSysCodeListForeign("matcreimbursetype");
		for(SysCode sysCode : reimbursetypeList){
			if(null!=sysCode && StringUtils.isNotEmpty(sysCode.getCode())){
				sb.append(",IF(IFNULL(reimbursetype,'') ='");
				sb.append(sysCode.getCode());
				sb.append("' ,writeoffamount,0.000000) AS reimburse_");
				sb.append(sysCode.getCode());
				
				sumsb.append(",SUM(reimburse_");
				sumsb.append(sysCode.getCode());
				sumsb.append(")");
				sumsb.append(" AS reimburse_");
				sumsb.append(sysCode.getCode());
				
				reimColumn.append(" ,reimburse_");
				reimColumn.append(sysCode.getCode());
			}
		}
		if(sb.length()>0){
			condition.put("dyncReimburseColumn", sb.toString());
			condition.put("dyncReimburseSumColumn", sumsb.toString());
			condition.put("reimburseSumColumn", reimColumn.toString());
		}else{
			if(condition.containsKey("dyncReimburseColumn")){
				condition.remove("dyncReimburseColumn");
			}
			if(condition.containsKey("dyncReimburseSumColumn")){
				condition.remove("dyncReimburseSumColumn");
			}
			if(condition.containsKey("reimburseSumColumn")){
				condition.remove("reimburseSumColumn");
			}
		}
		//小计列
		String groupcols = (String)condition.get("groupcols");
		if(!"supplierid".equals(groupcols) && !"id".equals(groupcols)){
			condition.put("groupcols", "id");
			groupcols = "id";
		}
		
		List<Map> list=journalSheetMapper.getMatcostsReimburseWriteoffReportList(pageMap);
		for(Map map : list){
			String datatmp=(String)map.get("supplierid");
			if(null!=datatmp && !"".equals(datatmp.trim())){//供应商
				BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(datatmp.trim());
				if(null!=buySupplier){
					map.put("suppliername", buySupplier.getName());
				}
			}
			datatmp=(String)map.get("supplierdeptid");
			if(null!=datatmp && !"".equals(datatmp.trim())){//所属部门
				DepartMent departMent = departMentMapper.getDepartmentInfo(datatmp.trim());
				if(null!=departMent){
					map.put("supplierdeptname",departMent.getName());
				}
			}
		}

		if(condition.containsKey("dyncReimburseColumn")){
			condition.remove("dyncReimburseColumn");
		}
		if(condition.containsKey("dyncReimburseSumColumn")){
			condition.remove("dyncReimburseSumColumn");
		}
		if(condition.containsKey("reimburseSumColumn")){
			condition.remove("reimburseSumColumn");
		}
		int total=0;
		if(!"true".equals(isExportData)){
			total=journalSheetMapper.getMatcostsReimburseWriteoffReportCount(pageMap);
		}
		PageData pageData=new PageData(total, list, pageMap);
		if(sb.length()>0){
			condition.put("dyncReimburseColumn", sb.toString());
			condition.put("dyncReimburseSumColumn", sumsb.toString());
			condition.put("reimburseSumColumn", reimColumn.toString());
			
		}
		condition.put("showSumAll", "true");
		List<Map> footerList = new ArrayList<Map>();
		Map sumMap = journalSheetMapper.getMatcostsReimburseWriteoffReportSums(pageMap);
		if(null!=sumMap){
			sumMap.put("id", "");
			sumMap.put("businessdate", "");
			sumMap.put("supplierid", "");
			sumMap.put("writeoffdate", "");
			sumMap.put("iswriteoff", "");
			sumMap.put("writeoffer", "");
			sumMap.put("writeoffername", "");
			sumMap.put("suppliername","合计");
			footerList.add(sumMap);
		}
		pageData.setFooter(footerList);
		return pageData;
	}
	@Override
	public Map updateMatcostsReimburseTypeChange(Map requestMap) throws Exception{
		Map map=new HashMap();
		String reimburseId=(String)requestMap.get("id");
		if(null==reimburseId || "".equals(reimburseId.trim())){
			map.put("flag", false);
			map.put("msg", "未能找到代垫收回信息");
			return map;
		}
		MatcostsInput matcostsInput = getMatcostsReimburseDetail(reimburseId.trim());
		if(null==matcostsInput){
			map.put("flag", false);
			map.put("msg", "未能找到代垫收回信息");
			return map;			
		}
		String reimburseType=(String)requestMap.get("reimburseType");
		if(null==reimburseType || "".equals(reimburseType.trim())){
			map.put("flag", false);
			map.put("msg", "未能找到变更后的收回方式");
			return map;
		}
		List<SysCode> reimbursetypeList = getBaseSysCodeMapper().getSysCodeListForeign("matcreimbursetype");
		boolean isType=false;
		for(SysCode sysCode : reimbursetypeList){
			if(null==sysCode){
				continue;
			}
			if(reimburseType.trim().equals(sysCode.getCode())){
				isType=true;
				break;
			}
		}
		if(!isType){
			map.put("flag", false);
			map.put("msg", "未知的收回方式");
			return map;
		}
		MatcostsInput upMatcostsReimburse=new MatcostsInput();
		upMatcostsReimburse.setId(matcostsInput.getId());
		upMatcostsReimburse.setReimbursetype(reimburseType);
		boolean flag=journalSheetMapper.updateMatcostsReimburseType(upMatcostsReimburse)>0;
		if(flag){
			MatcostsInputStatement matcostsInputStatement=new MatcostsInputStatement();
			matcostsInputStatement.setTakebackid(matcostsInput.getId());
			matcostsInputStatement.setReimbursetype(reimburseType);
			journalSheetMapper.updateMatcostsStatementReiburseType(matcostsInputStatement);
		}
		map.put("flag", flag);
		return map;
	}

	@Override
	public boolean deleteMatcostsInputByOA(String oaid) throws Exception {
		int i = journalSheetMapper.deleteMatcostsInputByOA(oaid);
		return i>0;
	}

	@Override
	public boolean addCustomerCostPayable(
			CustomerCostPayable customerCostPayable) throws Exception {
		if (isAutoCreate("t_js_customercost_payable")) {
			// 获取自动编号
			customerCostPayable.setId(getAutoCreateSysNumbderForeign(customerCostPayable, "t_js_customercost_payable"));
		}else{
			customerCostPayable.setId("FY-"+CommonUtils.getDataNumber());
		}
//		SysUser sysUser = getSysUser();
//		customerCostPayable.setApplyuserid(sysUser.getUserid());
//        if(customerCostPayable.getApplydeptid() == null) {
//
//            customerCostPayable.setApplydeptid(sysUser.getDepartmentid());
//        }
		int i = customerCostPayableMapper.addCustomerCostPayable(customerCostPayable);
		return i>0;
	}

	@Override
	public boolean deleteCustomerCostPayableByOaid(String id) throws Exception {
		int i = customerCostPayableMapper.deleteCustomerCostPayableByOaid(id);
		return i>0;
	}

	@Override
	public boolean updateCustomerCostPayableColse(String oaid) throws Exception {
		SysUser sysUser = getSysUser();
		int i = customerCostPayableMapper.updateCustomerCostPayableIsPay(oaid, CommonUtils.getTodayDataStr(), sysUser.getUserid(),sysUser.getDepartmentid(), "1");
		return i>0;
	}
	@Override
	public boolean updateCustomerCostPayableOpen(String oaid) throws Exception {
        int i = customerCostPayableMapper.updateCustomerCostPayableIsPay(oaid, "", "", "", "0");
        return i > 0;
    }

	@Override
	public List selectCustomerCostPayablByOaid(String oaid) throws Exception {

		return customerCostPayableMapper.selectCustomerCostPayableByOaid(oaid);
	}

	@Override
	public List selectMatcostsInputByOaid(String oaid) throws Exception {

		return journalSheetMapper.selectMatcostsInputByOaid(oaid);
	}

	@Override
	public PageData selectMatcostsBalanceList(PageMap map) throws Exception {

		String dataSql = getDataAccessRule("t_base_buy_supplier", "t2");
		//String dataSql2 = getDataAccessRule("t_base_department", "t2");
		
		map.setDataSql(dataSql);

		Map condition=map.getCondition();
		
		//其他报表引用代垫金额时，使用代垫收回金额还是代垫核销金额，默认为代垫核销金额
		String DDSZSRAmountUseHXOrSH=getSysParamValue("DDSZSRAmountUseHXOrSH");
		if(null==DDSZSRAmountUseHXOrSH ){
			DDSZSRAmountUseHXOrSH="";
		}
		DDSZSRAmountUseHXOrSH=DDSZSRAmountUseHXOrSH.trim();
		condition.put("queryAndUseMatcostsinputHXorSH", DDSZSRAmountUseHXOrSH);
		
		List list = journalSheetMapper.selectMatcostsBalanceList(map);
		int count = journalSheetMapper.selectMatcostsBalanceListCount(map);
		
		PageData data = new PageData(count, list, map);
		
		Map sum = journalSheetMapper.selectMatcostsBalanceSum(map);
		if(sum == null) {

			sum = new HashMap();
			sum.put("income", "0.00");
			sum.put("outcome", "0.00");
			sum.put("balance", "0.00");
		}
		
		sum.put("suppliername", "合计");

		List footer = new ArrayList();
		footer.add(sum);
		data.setFooter(footer);

		return data;
	}

	@Override
	public PageData selectMatcostsBalanceDetailList(PageMap map)
			throws Exception {

		Map condition =map.getCondition();
		String dataSql = getDataAccessRule("t_base_buy_supplier", "t2");
		map.setDataSql(dataSql);
		
		//其他报表引用代垫金额时，使用代垫收回金额还是代垫核销金额，默认为代垫核销金额
		String DDSZSRAmountUseHXOrSH=getSysParamValue("DDSZSRAmountUseHXOrSH");
		if(null==DDSZSRAmountUseHXOrSH ){
			DDSZSRAmountUseHXOrSH="";
		}
		DDSZSRAmountUseHXOrSH=DDSZSRAmountUseHXOrSH.trim();
		condition.put("queryAndUseMatcostsinputHXorSH", DDSZSRAmountUseHXOrSH);

		String isPageflag=(String)condition.get("isPageflag");
		List list = journalSheetMapper.selectMatcostsBalanceDetailList(map);
		int count =0;
		if(!"true".equals(isPageflag)){
		    count= journalSheetMapper.selectMatcostsBalanceDetailListCount(map);
        }
		
		for(int i = 0; i < list.size(); i++) {
			
			Map m = (Map) list.get(i);
			m.put("id", i);
		}
		
		PageData data = new PageData(count, list, map);
		
		Map sum = journalSheetMapper.selectMatcostsBalanceDetailSum(map);
		if(sum == null) {

			sum = new HashMap();
			sum.put("income", "0.00");
			sum.put("outcome", "0.00");
			sum.put("balance", "0.00");
		}
		
		sum.put("businessdate", "合计");

		List footer = new ArrayList();
		footer.add(sum);
		data.setFooter(footer);

		return data;
	}

    @Override
    public int deleteCustomerFee(String oaid, String billtype) throws Exception {

        return customerCostPayableMapper.deleteCustomerCostPayableByOaidBilltype(oaid, billtype);
    }

    @Override
    public int updateCustomerFee(CustomerCostPayable payable) throws Exception {

        return customerCostPayableMapper.updateCustomerFee(payable);
    }

    @Override
    public CustomerCostPayable selectCustomerFee(String oaid, String billtype) throws Exception {

        return customerCostPayableMapper.selectCustomerFee(oaid, billtype);
    }

    @Override
    public boolean doResetFundStatisticsSheetList() throws Exception {
        Map map1 = new HashMap();
        Map map = journalSheetMapper.getMaxDateMinDateFundInput(map1);
        if(null != map){
            String maxdatestr = null != map.get("maxdate") ? (String)map.get("maxdate") : "";
            Date maxdate = CommonUtils.stringToDate(maxdatestr,"yyyy-MM-dd");
            if(StringUtils.isEmpty(maxdatestr)){
                maxdate = CommonUtils.stringToDate(CommonUtils.getTodayDataStr(),"yyyy-MM-dd");
            }
            String mindatestr = null != map.get("mindate") ? (String)map.get("mindate") : "";
            String nexdatestr = CommonUtils.getNextDayByDate(mindatestr);

        }
        return false;
    }

    @Override
    public Map getMaxDateMinDateFundInput(Map map) throws Exception {
        return journalSheetMapper.getMaxDateMinDateFundInput(map);
    }

    @Override
    public boolean checkFundInputByMap(String supplierid,String businessdate) throws Exception {
        Map map = new HashMap();
        map.put("supplierid",supplierid);
        map.put("businessdate",businessdate);
        map.put("state","1");
        boolean flag = journalSheetMapper.checkFundInputByMap(map) > 0;
        String maxdate = getMaxDateFundInput(supplierid);
        int count = CommonUtils.compareDate(businessdate,maxdate);
        return flag || count < 0;
    }

    @Override
    public String getMaxDateFundInput(String supplierid) throws Exception {
        Map map = new HashMap();
        map.put("supplierid",supplierid);
        Map map1 = journalSheetMapper.getMaxDateMinDateFundInput(map);
        String maxdate = "";
        if(null != map1){
            maxdate = null != map1.get("maxdate") ? (String)map1.get("maxdate") : "";
        }
        return maxdate;
    }
    @Override
	public boolean updateMatcostsInputPrinttimes(MatcostsInput matcostsInput){
    	matcostsInput.setPrinttimes(1);
    	return journalSheetMapper.updateMatcostsInputPrinttimes(matcostsInput)>0;
    }
	@Override
	public boolean updateMatcostsReimbursePrinttimes(MatcostsInput matcostsInput){
		matcostsInput.setShprinttimes(1);
		return journalSheetMapper.updateMatcostsReimbursePrinttimes(matcostsInput)>0;
	}

    @Override
    public boolean updateMatcostsInputVouchertimes(MatcostsInput matcostsInput) {
        return journalSheetMapper.updateMatcostsInputVouchertimes(matcostsInput) >0;
    }

    @Override
    public List<MatcostsInput> getMatcostsInputListBy(Map map) throws Exception {
        String sql = getDataAccessRule("t_js_matcostsinput","t"); //数据权限
        map.put("dataSql", sql);
        List<MatcostsInput> list=journalSheetMapper.getMatcostsInputListBy(map);
        for(MatcostsInput matcostsInput : list){
            if(null!=matcostsInput){
                if(StringUtils.isNotEmpty(matcostsInput.getSupplierid())){//供应商
                    BuySupplier buySupplier = getBuySupplierMapper().getBuySupplier(matcostsInput.getSupplierid());
                    if(null!=buySupplier){
                        matcostsInput.setSuppliername(buySupplier.getName());
                    }
                }
                if(StringUtils.isNotEmpty(matcostsInput.getCustomerid())){//客户编号
                    Customer customer = getCustomerByID(matcostsInput.getCustomerid());
                    if(null != customer){
                        matcostsInput.setCustomername(customer.getName());
                    }
                }
                if(StringUtils.isNotEmpty(matcostsInput.getTransactorid())){//经办人
                    Personnel personnelInfo=getPersonnelById(matcostsInput.getTransactorid());
                    if(null!=personnelInfo){
                        matcostsInput.setTransactorname(personnelInfo.getName());
                    }
                }
                //科目
                ExpensesSort expensesSort = getExpensesSortByID(matcostsInput.getSubjectid());
                if(null != expensesSort){
                    matcostsInput.setSubjectname(expensesSort.getThisname());
                }
                //品牌
                Brand brand = getGoodsBrandByID(matcostsInput.getBrandid());
                if(null != brand){
                    matcostsInput.setBrandname(brand.getName());
                }
                //部门
                DepartMent departMent = getDepartMentById(matcostsInput.getSupplierdeptid());
                if(null != departMent){
                    matcostsInput.setSupplierdeptname(departMent.getName());
                }
                if("1".equals(matcostsInput.getIswriteoff())){
                    if(null==matcostsInput.getFactoryamount()){
                        matcostsInput.setFactoryamount(BigDecimal.ZERO);
                    }
                    if(null==matcostsInput.getWriteoffamount()){
                        matcostsInput.setWriteoffamount(BigDecimal.ZERO);
                    }
                    matcostsInput.setActingmatamount(matcostsInput.getFactoryamount().subtract(matcostsInput.getWriteoffamount()));
                }
            }
        }
        return list;
    }

    /**
     * 修改代垫录入单据的应收日期
     * @param supplierid
     * @return Boolean
     * @throws
     * @author luoqiang
     * @date Oct 26, 2017
     */
    @Override
    public Boolean updateMatcostsNoWriteoffDuefromdateBySupplier(String supplierid) throws Exception{
        BuySupplier buySupplier = getSupplierInfoById(supplierid);
        boolean flag = true;
        //获取系统参数 供应商档案修改结算方式后，是否调整应收日期
        String supplierSettletypeChageDuefromdate = getSysParamValue("SupplierSettletypeChageDuefromdate");
        if(null!=buySupplier && "1".equals(supplierSettletypeChageDuefromdate)){
            boolean isHasBrandSettle = isSupplierHasBrandSettle(supplierid);

            List<MatcostsInput> list = journalSheetMapper.getMatcostsNoWriteoffListBySupplierid(supplierid);
            for (MatcostsInput matcostsInput : list) {
                Date date =CommonUtils.stringToDate(matcostsInput.getBusinessdate(), "yyyy-MM-dd");
                if (isHasBrandSettle) {
                    String billDuefromdate = getSupplierDateBySettlement(date, matcostsInput.getSupplierid(), matcostsInput.getBrandid());
                    journalSheetMapper.updateMatcostsDuefromdateByBillid(matcostsInput.getId(), billDuefromdate);
                } else {
                    String billDuefromdate = getSupplierDateBySettlement(date, matcostsInput.getSupplierid(), null);
                    journalSheetMapper.updateMatcostsDuefromdateByBillid(matcostsInput.getId(), billDuefromdate);
                }
            }

        }
        return flag;
    }
}

