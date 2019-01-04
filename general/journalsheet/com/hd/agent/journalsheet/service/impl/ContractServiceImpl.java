package com.hd.agent.journalsheet.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.ExpensesSort;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.journalsheet.dao.ContractCaluseMapper;
import com.hd.agent.journalsheet.dao.ContractLiabilityMapper;
import com.hd.agent.journalsheet.dao.ContractMapper;
import com.hd.agent.journalsheet.dao.ContractReportMapper;
import com.hd.agent.journalsheet.model.*;
import com.hd.agent.journalsheet.service.IContractService;
import com.hd.agent.journalsheet.service.ICustomerCostPayableService;
import com.hd.agent.journalsheet.service.IJournalSheetService;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang.StringUtils;

import javax.print.DocFlavor;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wanghongteng on 2017/11/8.
 */
public class ContractServiceImpl extends BaseFilesServiceImpl implements IContractService{

    private ContractMapper contractMapper;

    private ContractCaluseMapper contractCaluseMapper;

    private ContractReportMapper contractReportMapper;

    private ContractLiabilityMapper contractLiabilityMapper;

    private ICustomerCostPayableService customerCostPayableService;

    private IJournalSheetService journalSheetService;

    public ContractMapper getContractMapper() {
        return contractMapper;
    }

    public void setContractMapper(ContractMapper contractMapper) {
        this.contractMapper = contractMapper;
    }

    public ContractCaluseMapper getContractCaluseMapper() {
        return contractCaluseMapper;
    }

    public void setContractCaluseMapper(ContractCaluseMapper contractCaluseMapper) {
        this.contractCaluseMapper = contractCaluseMapper;
    }

    public ContractReportMapper getContractReportMapper() {
        return contractReportMapper;
    }

    public void setContractReportMapper(ContractReportMapper contractReportMapper) {
        this.contractReportMapper = contractReportMapper;
    }

    public ContractLiabilityMapper getContractLiabilityMapper() {
        return contractLiabilityMapper;
    }

    public void setContractLiabilityMapper(ContractLiabilityMapper contractLiabilityMapper) {
        this.contractLiabilityMapper = contractLiabilityMapper;
    }

    public ICustomerCostPayableService getCustomerCostPayableService() {
        return customerCostPayableService;
    }

    public void setCustomerCostPayableService(ICustomerCostPayableService customerCostPayableService) {
        this.customerCostPayableService = customerCostPayableService;
    }

    public IJournalSheetService getJournalSheetService() {
        return journalSheetService;
    }

    public void setJournalSheetService(IJournalSheetService journalSheetService) {
        this.journalSheetService = journalSheetService;
    }

    /**
     * 获取客户费用合同列表数据
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public PageData getContractCaluseListData(PageMap pageMap) throws Exception{
        List<ContractCaluse> list=getContractCaluseMapper().getContractCaluseListData(pageMap);
        for(ContractCaluse contractCaluse : list){
            SysCode state = getBaseSysCodeMapper().getSysCodeInfo(contractCaluse.getState(), "state");
            if (state != null) {
                contractCaluse.setStatename(state.getCodename());
            }
            SysCode type = getBaseSysCodeMapper().getSysCodeInfo(contractCaluse.getType(), "caluseType");
            if (type != null) {
                contractCaluse.setTypename(type.getCodename());
            }
        }
        PageData pageData=new PageData(getContractCaluseMapper().getContractCaluseListDataCount(pageMap), list, pageMap);
        return pageData;
    }

    /**
     * 根据id获取客户费用合同条款
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public ContractCaluse getContractCaluseById(String id) throws Exception{
        ContractCaluse contractCaluse = getContractCaluseMapper().getContractCaluseById(id);
        return  contractCaluse;
    }

    /**
     * 添加客户费用合同条款
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public Map addContractCaluse(ContractCaluse contractCaluse) throws Exception{
        boolean flag = false;
        String msg = "";
        contractCaluse.setState("4");
        if(StringUtils.isEmpty(contractCaluse.getId())){
            if (isAutoCreate("t_finance_customer_contract_caluse")) {
                // 获取自动编号
                String id = getAutoCreateSysNumbderForeign(contractCaluse, "t_finance_customer_contract_caluse");
                contractCaluse.setId(id);
            }else{
                String id = "HTTK-"+ CommonUtils.getDataNumberSendsWithRand();
                contractCaluse.setId(id);
            }
        }
        ContractCaluse contractCaluse1 =getContractCaluseMapper().getContractCaluseById(contractCaluse.getId());
        if(null == contractCaluse1){
            //根据客户编号获取该客户的销售内勤用户信息
            SysUser sysUser = getSysUser();
            if (sysUser != null && null == contractCaluse.getAdduserid()) {
                contractCaluse.setAdddeptid(sysUser.getDepartmentid());
                contractCaluse.setAdddeptname(sysUser.getDepartmentname());
                contractCaluse.setAdduserid(sysUser.getUserid());
                contractCaluse.setAddusername(sysUser.getName());
            }
            flag = getContractCaluseMapper().addContractCaluse(contractCaluse) > 0;
        }else{
            msg = "编码已存在!";
        }


        Map map = new HashMap();
        map.put("flag",flag);
        map.put("msg",msg);
        return map;
    }

    /**
     * 修改客户费用合同条款
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public Map editContractCaluse(ContractCaluse contractCaluse) throws Exception{
        boolean flag = false;
        String msg = "";
        ContractCaluse contractCaluseIndex = getContractCaluseMapper().getContractCaluseById(contractCaluse.getId());
        if(!"1".equals(contractCaluseIndex.getState())){
            SysUser sysUser = getSysUser();
            if (sysUser != null && null == contractCaluse.getAdduserid()) {
                contractCaluse.setModifyuserid(sysUser.getUserid());
                contractCaluse.setModifyusername(sysUser.getName());
            }
            flag = getContractCaluseMapper().editContractCaluse(contractCaluse) > 0;
        }else {
            msg = "该条款已启用,不能修改！";
        }
        Map map = new HashMap();
        map.put("flag",flag);
        map.put("msg",msg);
        return map;
    }

    /**
     * 删除客户费用合同条款
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public Map deleteContractCaluse(String id)  throws Exception{
        boolean flag = false;
        String msg = "";
        ContractCaluse contractCaluse = getContractCaluseMapper().getContractCaluseById(id);
        if(null!=contractCaluse){
            if(!"1".equals(contractCaluse.getState())) {
                flag = getContractCaluseMapper().deleteContractCaluse(id) > 0;
            }else {
                msg = "已启用,不能删除！";
            }
        }else{
            msg="条款不存在！";
        }
        Map map = new HashMap();
        map.put("flag",flag);
        map.put("msg",msg);
        return map;
    }

    /**
     * 启用客户费用合同条款
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public Map openContractCaluse(String id)  throws Exception{
        boolean flag = false;
        String msg = "";
        ContractCaluse contractCaluse = getContractCaluseMapper().getContractCaluseById(id);
        if(null!=contractCaluse){
            if(!"1".equals(contractCaluse.getState())) {
                flag = getContractCaluseMapper().openContractCaluse(id) > 0;
            }else {
                msg = "已启用！";
            }
        }else{
            msg="条款不存在！";
        }

        Map map = new HashMap();
        map.put("flag",flag);
        map.put("msg",msg);
        return map;
    }

    /**
     * 禁用客户费用合同条款
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public Map closeContractCaluse(String id)  throws Exception{
        boolean flag = false;
        String msg = "";
        ContractCaluse contractCaluse = getContractCaluseMapper().getContractCaluseById(id);
        if(null!=contractCaluse){
            if("1".equals(contractCaluse.getState())) {
                int i = getContractMapper().getContractDetailNumByCaluseid(contractCaluse.getId());
                if(i == 0){
                    flag = getContractCaluseMapper().closeContractCaluse(id) > 0;
                }else{
                    msg = "条款被引用！";
                }

            }else {
                msg = "条款未启用！";
            }
        }else{
            msg="条款不存在！";
        }

        Map map = new HashMap();
        map.put("flag",flag);
        map.put("msg",msg);
        return map;
    }












//    客户费用合同
    /**
     * 获取客户费用合同列表数据
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public PageData getContractListData(PageMap pageMap) throws Exception{
        List<Contract> list=getContractMapper().getContractListData(pageMap);
        for(Contract contract : list){
            SysCode status = getBaseSysCodeMapper().getSysCodeInfo(contract.getStatus(), "status");
            if (status != null) {
                contract.setStatusname(status.getCodename());
            }

            String customerid = contract.getCustomerid();
            Customer customer = getCustomerByID(customerid);
            if(null != customer){
                contract.setCustomername(customer.getName());
            }

            String personnelid = contract.getPersonnelid();
            Personnel personnel = getPersonnelById(personnelid);
            if(null != personnel){
                contract.setPersonnelname(personnel.getName());
            }

        }
        PageData pageData=new PageData(getContractMapper().getContractListDataCount(pageMap), list, pageMap);
        return pageData;
    }

    /**
     * 根据id获取客户费用合同条款
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public Map getContractById(String id) throws Exception{
        Map map = new HashMap();
        Contract contract = getContractMapper().getContractById(id);
        String status = contract.getStatus();
        SysCode sysCode = getBaseEnableSysCodeInfo("status",status);
        if(null!=sysCode){
            contract.setStatusname(sysCode.getCodename());
        }
        List<ContractDetail> contractDetailList = getContractMapper().getContractDetailById(contract.getId());
        for( ContractDetail contractDetail : contractDetailList){
            ExpensesSort expensesSort = getExpensesSortByID(contractDetail.getSubjectexpenses());
            if(null!=expensesSort){
                contractDetail.setSubjectexpensesname(expensesSort.getName());
            }
            contractDetail.setCalculateamount(contractDetail.getCalculateamount().setScale(2,   BigDecimal.ROUND_HALF_UP));

            if("1".equals(contractDetail.getCalculatetype())){
                contractDetail.setCalculateamount(contractDetail.getCalculateamount().multiply(new BigDecimal("100")));
            }
        }
        map.put("contract",contract);
        map.put("detailList",contractDetailList);
        return  map;
    }

    /**
     * 添加客户费用合同条款
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public Map addContract(Contract contract, List<ContractDetail> contractDetailList) throws Exception{
        if (isAutoCreate("t_finance_customer_contract")) {
            // 获取自动编号
            String id = getAutoCreateSysNumbderForeign(contract, "t_finance_customer_contract");
            contract.setId(id);
        }else{
            String id = "HTTK-"+ CommonUtils.getDataNumberSendsWithRand();
            contract.setId(id);
        }
        //根据客户编号获取该客户的销售内勤用户信息
        SysUser sysUser = getSysUser();
        if (sysUser != null && null == contract.getAdduserid()) {
            contract.setAdddeptid(sysUser.getDepartmentid());
            contract.setAdddeptname(sysUser.getDepartmentname());
            contract.setAdduserid(sysUser.getUserid());
            contract.setAddusername(sysUser.getName());
        }
        boolean flag = getContractMapper().addContract(contract) > 0;
        if(flag){
            for(ContractDetail contractDetail : contractDetailList){
                contractDetail.setBillid(contract.getId());
                if("1".equals(contractDetail.getCalculatetype())){
                    contractDetail.setCalculateamount(contractDetail.getCalculateamount().divide(new BigDecimal("100"),6, BigDecimal.ROUND_HALF_UP));
                }
                getContractMapper().addContractDetail(contractDetail);
            }
        }
        Map map = new HashMap();
        map.put("id",contract.getId());
        map.put("flag",flag);
        return map;
    }

    /**
     * 修改客户费用合同条款
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public Map editContract(Contract contract, List<ContractDetail> contractDetailList) throws Exception{
        boolean flag = false;
        String msg = "";
        Contract contractIndex = getContractMapper().getContractById(contract.getId());
        if(!"3".equals(contractIndex.getStatus())&&!"4".equals(contractIndex.getStatus())){
            SysUser sysUser = getSysUser();
            if (sysUser != null && null == contract.getAdduserid()) {
                contract.setModifyuserid(sysUser.getUserid());
                contract.setModifyusername(sysUser.getName());
            }
            flag = getContractMapper().editContract(contract) > 0;
            if(flag){
                getContractMapper().deleteContractDetail(contract.getId());
                for(ContractDetail contractDetail : contractDetailList){
                    contractDetail.setBillid(contract.getId());
                    if("1".equals(contractDetail.getCalculatetype())){
                        contractDetail.setCalculateamount(contractDetail.getCalculateamount().divide(new BigDecimal("100"),6, BigDecimal.ROUND_HALF_UP));
                    }
                    getContractMapper().addContractDetail(contractDetail);
                }
            }
        }else {
            if("3".equals(contractIndex.getStatus())){
                msg = "该条款已审核,不能修改！";
            }else if("4".equals(contractIndex.getStatus())){
                msg = "该条款已关闭,不能修改！";
            }else{
                msg = "该条款非保存状态,不能修改！";
            }
        }
        Map map = new HashMap();
        map.put("id",contract.getId());
        map.put("flag",flag);
        map.put("msg",msg);
        return map;
    }

    /**
     * 删除客户费用合同
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public Map deleteContract(String id)  throws Exception{
        boolean flag = false;
        String msg = "";
        Contract contract = getContractMapper().getContractById(id);
        if(null!=contract){
            if(!"3".equals(contract.getStatus())&&!"4".equals(contract.getStatus())) {
                flag = getContractMapper().deleteContract(id) > 0;
                getContractMapper().deleteContractDetail(contract.getId());
            }else {
                if("3".equals(contract.getStatus())){
                    msg = "合同已审核,不能删除！";
                }else if("4".equals(contract.getStatus())){
                    msg = "合同已关闭,不能删除！";
                }else{
                    msg = "合同非保存状态,不能删除！";
                }
            }
        }else{
            msg = "合同不存在！";
        }

        Map map = new HashMap();
        map.put("flag",flag);
        map.put("msg",msg);
        return map;
    }

    /**
     * 审核客户费用合同
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public Map auditContract(String id)  throws Exception{
        boolean flag = false;
        String msg = "";
        Contract contract = getContractMapper().getContractById(id);
        if(null!=contract){
            if("2".equals(contract.getStatus())) {
                flag = getContractMapper().auditContract(id) > 0;
            }else {
                if("3".equals(contract.getStatus())){
                    msg = "该合同已审核！";
                }else if("4".equals(contract.getStatus())){
                    msg = "该合同已关闭！";
                }else{
                    msg = "该合同非保存状态,不能审核！";
                }
            }
        }else{
            msg = "合同不存在！";
        }

        Map map = new HashMap();
        map.put("flag",flag);
        map.put("msg",msg);
        return map;
    }

    /**
     * 禁用客户费用合同
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public Map oppauditContract(String id)  throws Exception{
        boolean flag = false;
        String msg = "";
        Contract contract = getContractMapper().getContractById(id);
        if(null!=contract){
            if("3".equals(contract.getStatus())) {
                int i = getContractReportMapper().getContractReportNumByContractbillid(id);
                if(i==0){
                    flag = getContractMapper().oppauditContract(id) > 0;
                }else {
                    msg = "合同已被引用！";
                }

            }else {
                if("2".equals(contract.getStatus())){
                    msg = "该合同未审核！";
                }else if("4".equals(contract.getStatus())){
                    msg = "该合同已关闭！";
                }else{
                    msg = "该合同非审核状态,不能反审！";
                }
            }
        }else{
            msg = "合同不存在！";
        }

        Map map = new HashMap();
        map.put("flag",flag);
        map.put("msg",msg);
        return map;
    }
    /**
     * 检测是否存在相关联的门店或者总店
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public Map checkCorrelationCustomer(String customerid)  throws Exception{
        boolean flag = false;
        boolean havestoredate=false;
        boolean haveheadstoredata = false;
        List<Customer> list=getCustomerListByPid(customerid);
        if(list.size()>0){
            for(Customer customer : list){
                if(getContractMapper().getContractCountByCustomerid(customer.getId())>0){
                    havestoredate = true;
                }
            }

        }

        Customer headcustomer = getHeadCustomer(customerid);
        if(!customerid.equals(headcustomer.getId())){
            haveheadstoredata =getContractMapper().getContractCountByCustomerid(headcustomer.getId())>0;
        }
        Map map = new HashMap();
        map.put("havestoredate",havestoredate);
        map.put("haveheadstoredata",haveheadstoredata);
        return map;
    }

//客户合同费用报表
    /**
     * 获取客户合同费用报表数据
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public PageData getAddContractReportData(PageMap pageMap) throws Exception{
        Map queryMap=pageMap.getCondition();
        String month = (String)queryMap.get("month");
        String m = month.substring(5,7);


        String seasonmonthnum="";
        String yearmonthnum = String.valueOf(Integer.parseInt(m));


        if(Integer.parseInt(m)>=1&&Integer.parseInt(m)<=3){
            seasonmonthnum = String.valueOf(Integer.parseInt(m));
        }else if(Integer.parseInt(m)>=4&&Integer.parseInt(m)<=6){
            seasonmonthnum = String.valueOf(Integer.parseInt(m)-3);
        }else if(Integer.parseInt(m)>=7&&Integer.parseInt(m)<=9){
            seasonmonthnum = String.valueOf(Integer.parseInt(m)-6);
        }else if(Integer.parseInt(m)>=10&&Integer.parseInt(m)<=12){
            seasonmonthnum = String.valueOf(Integer.parseInt(m)-9);
        }

        pageMap.getCondition().put("month",month);
        pageMap.getCondition().put("seasonmonthnum",seasonmonthnum);
        pageMap.getCondition().put("yearmonthnum",yearmonthnum);
        List<Contract> list=getContractMapper().getAddContractReportData(pageMap);
        for(Contract contract : list){
            String customerid = contract.getCustomerid();
            Customer customer = getCustomerByID(customerid);
            if(null!=customer){
                contract.setCustomername(customer.getName());
            }
            String deptid = contract.getDeptid();
            DepartMent departMent = getDepartMentById(deptid);
            if(null!= departMent){
                contract.setDeptname(departMent.getName());
            }
        }
        PageData pageData=new PageData(getContractMapper().getAddContractReportDataCount(pageMap), list, pageMap);
        return pageData;
    }

    /**
     * 根据月份客户编号获取合同费用统计明细数据
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public Map getContractReportDetailDataByMonthAndCustomerid(String month,String customerid) throws Exception{
        List<Map> titlelist = new ArrayList<Map>();
        List<Map> contractTitleList = getContractReportMapper().getContractTitleListByMonthAndCustomerid(month,customerid);
        Customer customer  =getCustomerByID(customerid);
        for(Map contractTitle : contractTitleList){
            String contractbillid = (String)contractTitle.get("contractbillid");
            Contract contract =getContractMapper().getContractById(contractbillid);
            Map contentinfo  = new HashMap();
            contentinfo.put("contractbillid",contractbillid);
            contentinfo.put("contractname",contract.getContractname());
            contentinfo.put("contractid",contract.getContractid());
            contentinfo.put("saleamount",(BigDecimal)contractTitle.get("saleamount"));
            contentinfo.put("storenum",(BigDecimal)contractTitle.get("storenum"));
            contentinfo.put("newstorenum",(BigDecimal)contractTitle.get("newstorenum"));
            contentinfo.put("newgoodsnum",(BigDecimal)contractTitle.get("newgoodsnum"));
            titlelist.add(contentinfo);
        }

        List<Map> contentlist = new ArrayList<Map>();
        List<Map> contractContentList = getContractReportMapper().getContractContentListByMonthAndCustomerid(month,customerid);

        for(Map contractContent : contractContentList){
            boolean flag = false;
            Map contractContentMap = new HashMap();
            List<Map> caluseList = new ArrayList<Map>();
            String type = (String)contractContent.get("type");
            SysCode sysCode =getBaseSysCodeInfo(type,"caluseType");
            String contractcaluseid = (String)contractContent.get("contractcaluseid");
            if(0==contentlist.size()){
                contractContentMap.put("calusetype",type);
                contractContentMap.put("calusetypename",sysCode.getCodename());
            }else{

                for(Map map : contentlist){
                    if(type.equals((String)map.get("calusetype"))){
                        contractContentMap=map;
                        caluseList = (List<Map>)map.get("caluseList");
                        flag=true;
                    }
                }
                if(!flag){
                    contractContentMap.put("calusetype",type);
                    contractContentMap.put("calusetypename",sysCode.getCodename());
                }
            }

            Map caluse = new HashMap();
            List<Map> indexList = new ArrayList<Map>();
            caluse.put("contractcaluseid",contractcaluseid);

            Map firsttd = new HashMap();
            firsttd.put("contractcalusename",contractContent.get("name"));
            indexList.add(firsttd);
            for(Map contractTitle : contractTitleList){
                Map indexMap = new HashMap();
                String contractid = (String)contractTitle.get("contractid");
                String contractbillid = (String)contractTitle.get("contractbillid");
                ContractDetail contractDetail = getContractMapper().getContractDetailByBillidAndCaluseid(contractbillid,contractcaluseid);
                ContractReport contractReport = getContractReportMapper().getContractContentListByMonthAndBillidAndCaluseid(month,contractbillid,contractcaluseid);
                if(null!=contractReport){
                    indexMap.put("isexist","1");
                    indexMap.put("contractbillid",contractbillid);
                    indexMap.put("contractcaluseid",contractcaluseid);
                    indexMap.put("calculateamount",contractDetail.getCalculateamount());
                    indexMap.put("calculatetype",contractDetail.getCalculatetype());
                    indexMap.put("costamount",contractReport.getCostamount());
                    indexMap.put("matcostsamount",contractReport.getMatcostsamount());
                    indexMap.put("selfamount",contractReport.getSelfamount());
                }else{
                    indexMap.put("isexist","0");
                    indexMap.put("contractbillid",contractbillid);
                    indexMap.put("contractcaluseid",contractcaluseid);
                    indexMap.put("calculateamount","");
                    indexMap.put("calculatetype","");
                    indexMap.put("costamount","");
                    indexMap.put("matcostsamount","");
                    indexMap.put("selfamount","");
                }
                indexList.add(indexMap);

            }
            caluse.put("indexlist",indexList);
            caluseList.add(caluse);

            contractContentMap.put("caluseList",caluseList);
            contractContentMap.put("size",caluseList.size());
            if(!flag){
                contentlist.add(contractContentMap);
            }
        }

        Map resultMap = new HashMap();
        resultMap.put("titlelist",titlelist);
        resultMap.put("contentlist",contentlist);
        resultMap.put("customername",customer.getName());
        return resultMap;
    }

    /**
     * 获取客户合同费用报表数据
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public PageData getContractReportData(PageMap pageMap) throws Exception{
        List<ContractReport> list=getContractReportMapper().getContractReportData(pageMap);
        for(ContractReport contractReport : list){
            contractReport.setInitnewstorenum(contractReport.getNewstorenum());
            contractReport.setInitnewgoodsnum(contractReport.getNewgoodsnum());
            contractReport.setInitmatcostsamount(contractReport.getMatcostsamount());
            contractReport.setInitselfamount(contractReport.getSelfamount());

            String customerid = contractReport.getCustomerid();
            Customer customer = getCustomerByID(customerid);
            if(null!=customer){
                contractReport.setCustomername(customer.getName());
            }

            String deptid = contractReport.getDeptid();
            DepartMent departMent = getDepartMentById(deptid);
            if(null!= departMent){
                contractReport.setDeptname(departMent.getName());
            }

            ContractDetail contractDetail =getContractMapper().getContractDetailByBillidAndCaluseid(contractReport.getContractbillid(),contractReport.getContractcaluseid());
            if(null != contractDetail){
                contractReport.setCalculatetype(contractDetail.getCalculatetype());
                contractReport.setCalculateamount(contractDetail.getCalculateamount());
            }


        }
        PageData pageData=new PageData(getContractReportMapper().getContractReportDataCount(pageMap), list, pageMap);
        return pageData;
    }


    /**
     * 禁用客户费用合同
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public Map addContractReport(String month,String type,String id)  throws Exception{
        boolean flag = false;
        String msg = "";
        int snum=0;

        String y = month.substring(0,4);
        String m = month.substring(5,7);

//        String beginseason = "";
//        String endseason  = "";

        String seasonmonthnum="";
        String yearmonthnum = String.valueOf(Integer.parseInt(m));


        if(Integer.parseInt(m)>=1&&Integer.parseInt(m)<=3){
//            beginseason = y + "-01-01";
//            endseason = y + "-03-31";
            seasonmonthnum = String.valueOf(Integer.parseInt(m));
        }else if(Integer.parseInt(m)>=4&&Integer.parseInt(m)<=6){
//            beginseason = y + "-04-01";
//            endseason = y + "-06-31";
            seasonmonthnum = String.valueOf(Integer.parseInt(m)-3);
        }else if(Integer.parseInt(m)>=7&&Integer.parseInt(m)<=9){
//            beginseason = y + "-07-01";
//            endseason = y + "-09-31";
            seasonmonthnum = String.valueOf(Integer.parseInt(m)-6);
        }else if(Integer.parseInt(m)>=10&&Integer.parseInt(m)<=12){
//            beginseason = y + "-10-01";
//            endseason = y + "-12-31";
            seasonmonthnum = String.valueOf(Integer.parseInt(m)-9);
        }

//        String beginyear = y +"-01-01";
//        String endyear   = y+"-12-31";
        Map queryMap = new HashMap();
        queryMap.put("month",month);
        queryMap.put("customerid",id);
        queryMap.put("seasonmonthnum",seasonmonthnum);
        queryMap.put("yearmonthnum",yearmonthnum);

        List<Map> contractDetailList = getContractMapper().getAddContractReportDataListByMonthAndCustomerid(queryMap);

        for(Map map : contractDetailList){
            String contractbillid = (String)map.get("id");
            String contractcaluseid = (String)map.get("caluseid");
            Contract contract = getContractMapper().getContractById(contractbillid);
            ContractReport contractReport = new ContractReport();
            contractReport.setMonth(month);
            contractReport.setContractid(contract.getContractid());
            contractReport.setContractbillid(contract.getId());
            contractReport.setCustomerid(contract.getCustomerid());
            contractReport.setDeptid(contract.getDeptid());
            contractReport.setSaleamount(getSaleamountByMonthAndCustomerid(contract.getDatasource(),month,contract.getCustomerid()));
            contractReport.setStorenum(getStorenumByCustomerid(contract.getCustomerid()));
            contractReport.setNewstorenum(getNewstorenumByMonthAndCustomerid(month,contract.getCustomerid()));
            contractReport.setNewgoodsnum(getNewgoodsnumByMonthAndCustomerid(month,contract.getCustomerid()));



            ContractDetail contractDetail = getContractMapper().getContractDetailByBillidAndCaluseid(contractbillid,contractcaluseid);

            contractReport.setContractcaluseid(contractDetail.getCaluseid());
            String calculatetype = contractDetail.getCalculatetype();
            if("0".equals(calculatetype)){//费用结算方式---固定费用
                BigDecimal costamount = contractDetail.getCalculateamount();
                contractReport.setCostamount(costamount);
                contractReport.setMatcostsamount(costamount);
            }else if("1".equals(calculatetype)){//费用结算方式---销售额百分比
                BigDecimal costamount = contractDetail.getCalculateamount().multiply(contractReport.getSaleamount());
                contractReport.setCostamount(costamount);
                contractReport.setMatcostsamount(costamount);
            }else if("2".equals(calculatetype)){//费用结算方式---按门店数计算
                BigDecimal costamount = contractDetail.getCalculateamount().multiply(contractReport.getStorenum());
                contractReport.setCostamount(costamount);
                contractReport.setMatcostsamount(costamount);
            }
            flag = getContractReportMapper().addContractReport(contractReport)>0;
            if(flag){
                snum++;
            }
        }
        Map map = new HashMap();
        map.put("flag",flag);
        map.put("msg",msg);
        return map;
    }

    public String getSeasonBusinessdate(String dateStr,String type) throws  Exception{

        if("1".equals(type)){
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
            Date date = new Date();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(sf.parse(dateStr));
            calendar.add(calendar.MONTH, -2);
            date = calendar.getTime();
            return sf.format(date);
        }else if("2".equals(type)){
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(sf.parse(dateStr));
            calendar.add(calendar.MONTH, -2);
            date = calendar.getTime();
            return sf.format(date);
        }
        else {
            return "";
        }
    }

    public  boolean isSeason(String now,String begindata) throws  Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(sdf.parse(begindata));
        aft.setTime(sdf.parse(now));
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        if((result+1)%3==0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 根据数据参考来源,月份和客户编号获取销售额
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public BigDecimal getSaleamountByMonthAndCustomerid(String type,String month,String customerid) throws Exception{
        BigDecimal saleamount = new BigDecimal("0");
        if("0".equals(type)){
            String begindate = month+"-01";
            String enddate = getMonthLastDay(begindate);
            Map map=getContractReportMapper().getCrmCustomerSaleamount(begindate,enddate,customerid);
            saleamount=(BigDecimal)map.get("taxamount");
            return saleamount;
        }else{
            String begindate = month+"-01";
            String enddate = getMonthLastDay(begindate);
            Map map=getContractReportMapper().getCustomerSaleamount(begindate,enddate,customerid);
            saleamount=(BigDecimal)map.get("taxamount");
            return saleamount;
        }

    }

    /**
     * 保存行修改的客户合同费用
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public Map editContractReport(ContractReport contractReport)  throws Exception{
        boolean flag = false;
        String msg = "";
        flag = getContractReportMapper().editContractReport(contractReport)>0;
        Map map = new HashMap();
        map.put("flag",flag);
        map.put("msg",msg);
        return map;
    }

    /**
     * 保存行修改的客户合同费用
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public Map editContractReportDetail(ContractReport contractReport)  throws Exception{
        boolean flag = false;
        String msg = "";
        flag = getContractReportMapper().editContractReportDetail(contractReport)>0;
        Map map = new HashMap();
        map.put("flag",flag);
        map.put("msg",msg);
        return map;
    }

    /**
     * 确认客户合同费用
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public Map auditContractReport(String month,String customerid)  throws Exception{
        boolean flag = false;
        String msg = "";
        List<ContractReport> contractReportList = getContractReportMapper().getContractReportListByMonthAndCustomerid(month,customerid);
        for(ContractReport contractReport :contractReportList){
            if("0".equals(contractReport.getState())){
                ContractDetail contractDetail = getContractMapper().getContractDetailByBillidAndCaluseid(contractReport.getContractbillid(),contractReport.getContractcaluseid());
                ContractLiability contractLiability = new ContractLiability();
                contractLiability.setContractid(contractReport.getContractid());
                contractLiability.setContractbillid(contractReport.getContractbillid());
                contractLiability.setContractcaluseid(contractReport.getContractcaluseid());
                contractLiability.setCustomerid(contractReport.getCustomerid());
                contractLiability.setSubjectexpenses(contractDetail.getSubjectexpenses());
                contractLiability.setDeptid(contractReport.getDeptid());
                if("0".equals(contractDetail.getSharetype())){//一次性分摊
                    if("0".equals(contractDetail.getReturntype())){//月返
                        contractLiability.setMonth(contractReport.getMonth());
                    }else if("1".equals(contractDetail.getReturntype())){//季返
                        contractLiability.setMonth(getSubtractMonth(getThisSeason(contractReport.getMonth()),contractDetail.getReturnmonthnum()-3));
                    }else if("2".equals(contractDetail.getReturntype())){//年返
                        contractLiability.setMonth(getSubtractMonth(getThisYear(contractReport.getMonth()),contractDetail.getReturnmonthnum()-12));
                    }
                    contractLiability.setLiabilityamount(contractReport.getCostamount());
                    getContractLiabilityMapper().addContractLiability(contractLiability);
                }else if("1".equals(contractDetail.getSharetype())){//分月平坦
                    if("0".equals(contractDetail.getReturntype())){//月返
                        contractLiability.setMonth(contractReport.getMonth());
                        contractLiability.setLiabilityamount(contractReport.getCostamount());
                        getContractLiabilityMapper().addContractLiability(contractLiability);
                    }else if("1".equals(contractDetail.getReturntype())){//季返
                        String thisseason=getThisSeason(contractReport.getMonth());
                        for(int i=0;i<3;i++){
                            contractLiability.setMonth(getSubtractMonth(thisseason,-i));
                            contractLiability.setLiabilityamount(contractReport.getCostamount().divide(new BigDecimal("3"),6, BigDecimal.ROUND_HALF_UP));
                            getContractLiabilityMapper().addContractLiability(contractLiability);
                        }
                    }else if("2".equals(contractDetail.getReturntype())){//年返
                        String thisyear=getThisYear(contractReport.getMonth());
                        for(int i=0;i<12;i++){
                            contractLiability.setMonth(getSubtractMonth(thisyear,-i));
                            contractLiability.setLiabilityamount(contractReport.getCostamount().divide(new BigDecimal("12"),6, BigDecimal.ROUND_HALF_UP));
                            getContractLiabilityMapper().addContractLiability(contractLiability);
                        }
                    }
                }
                flag = getContractReportMapper().auditContractReport(contractReport.getId().toString(),"1")>0;
            }else{
                msg="数据已确认";
            }
        }

        Map map = new HashMap();
        map.put("flag",flag);
        map.put("msg",msg);
        return map;
    }

    /**
     * 确认客户合同费用
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public Map oppauditContractReport(String month,String customerid)  throws Exception{
        boolean flag = false;
        String msg = "";
        List<ContractReport> contractReportList = getContractReportMapper().getContractReportListByMonthAndCustomerid(month,customerid);
        for(ContractReport contractReport :contractReportList){
            if("1".equals(contractReport.getState())){
                flag = getContractReportMapper().auditContractReport(contractReport.getId().toString(),"0")>0;
            }else{
                msg="数据未确认";
            }
        }
        if(flag){
            getContractLiabilityMapper().deleteContractLiabilityByMonthAndCustomerid(month,customerid);
        }
        Map map = new HashMap();
        map.put("flag",flag);
        map.put("msg",msg);
        return map;
    }


    /**
     * 生成客户应付费用和代垫录入费用
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public Map CreatPayableAndMatcost(String month,String  customerid)  throws Exception{
        boolean flag = false;
        String msg = "";
        List<ContractReport> contractReportList = getContractReportMapper().getContractReportListByMonthAndCustomerid(month,customerid);
        for(ContractReport contractReport : contractReportList){
            if("1".equals(contractReport.getState())){
                Contract contract = getContractMapper().getContractById(contractReport.getContractbillid());
                ContractDetail contractDetail = getContractMapper().getContractDetailByBillidAndCaluseid(contractReport.getContractbillid(),contractReport.getContractcaluseid());

                //生成客户应付费用
                CustomerCostPayable customerCostPayable = new CustomerCostPayable();
                customerCostPayable.setBusinessdate(CommonUtils.getTodayDataStr());
                customerCostPayable.setSupplierid(contract.getSupplierid());
                customerCostPayable.setCustomerid(contract.getCustomerid());
                customerCostPayable.setDeptid(contract.getDeptid());
                Customer customer = getCustomerByID(contract.getCustomerid());
                if(null!=customer){
                    customerCostPayable.setPcustomerid(customer.getPid());
                    if("0".equals(customer.getIslast())){
                        customerCostPayable.setPcustomerid(customerCostPayable.getCustomerid());
                    }
                }
                customerCostPayable.setExpensesort(contractDetail.getSubjectexpenses());
                customerCostPayable.setAmount(contractReport.getSelfamount());
                customerCostPayable.setIspay("0");
                SysUser sysUser=getSysUser();
                customerCostPayable.setApplyuserid(sysUser.getUserid());
                customerCostPayable.setApplydeptid(sysUser.getDepartmentid());
                customerCostPayable.setBilltype("1");
                customerCostPayable.setSourcefrom("1");//代垫
                customerCostPayable.setHcflag("0"); //非红冲
                customerCostPayable.setIsbegin("0"); //期初数据
                customerCostPayableService.addCustomerCostPayable(customerCostPayable);

                //生成代垫
                MatcostsInput matcostsInput = new MatcostsInput();
                matcostsInput.setBusinessdate(CommonUtils.getTodayDataStr());
                matcostsInput.setBilltype("1");
                matcostsInput.setSupplierid(contract.getSupplierid());
                matcostsInput.setSupplierdeptid(contract.getDeptid());
                matcostsInput.setCustomerid(contract.getCustomerid());
                matcostsInput.setBrandid(contract.getBrandid());
                matcostsInput.setSubjectid(contractDetail.getSubjectexpenses());
                matcostsInput.setFactoryamount(contractReport.getMatcostsamount());
                flag= journalSheetService.addMatcostsInput(matcostsInput);

                flag = getContractReportMapper().auditContractReport(contractReport.getId().toString(),"2")>0;
            }else{
                msg="只有已确认状态的数据能生成";
            }
        }
        Map map = new HashMap();
        map.put("flag",flag);
        map.put("msg",msg);
        return map;
    }

    /**
     * 获取客户合同权责金额数据
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @Override
    public PageData getContractLiabilityData(PageMap pageMap) throws Exception{
        List<Map> customerlist=getContractLiabilityMapper().getContractLiabilityDataCustomerList(pageMap);
        List dataList = new ArrayList();
        if(null!=customerlist && customerlist.size()>0){
            for(Map map : customerlist){
                String id = (String) map.get("id");
                Customer customer = getCustomerByID(id);
                if(null!=customer){
                    map.put("name",customer.getName());
                }

                String pid = (String) map.get("pid");
                String leaf = (String) map.get("leaf");
                if(StringUtils.isEmpty(pid)){
                    List childrenList = getContractSubjectDataList(id,pageMap);
                    if(childrenList.size()>0){
                        map.put("rid", CommonUtils.getRandomWithTime());
                        map.put("children", childrenList);
                        map.put("state","closed");
                    }
                    dataList.add(map);
                }
            }
        }
        List footerList = new ArrayList();
        Map sumMap = getContractLiabilityMapper().getContractLiabilityDataSums(pageMap);
        if(null!=sumMap){
            sumMap.put("name","合计");
            footerList.add(sumMap);
        }
        PageData pageData = new PageData(dataList.size(),dataList,pageMap);
        pageData.setFooter(footerList);
        return pageData;
    }
    /**
     * 获取客户合同费用客户下科目费用金额
     * @param customerid
     * @param pageMap
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-12-12
     */
    private List<Map> getContractSubjectDataList(String customerid,PageMap pageMap) throws Exception{
        Map queryMap = pageMap.getCondition();
        List<Map> dataList = getContractLiabilityMapper().getContractSubjectDataList(customerid,queryMap);
        List list = new ArrayList();
        for(Map map : dataList){
            String sid = (String) map.get("sid");
            String pid = (String) map.get("pid");
            String leaf = (String) map.get("leaf");
            BigDecimal subamount = BigDecimal.ZERO;
            if(map.containsKey("subamount")){
                subamount = (BigDecimal) map.get(subamount);
            }
            if(StringUtils.isEmpty(pid)){
                List childrenList = getContractSubjectChildList(dataList,sid);
                if(childrenList.size()>0){
                    map.put("children", childrenList);
                    map.put("state","closed");
                }
                map.put("rid", CommonUtils.getRandomWithTime());
                map.put("iconCls","icon-annex");
                list.add(map);
            }
        }
        return list;
    }
    /**
     * 递归组装费用科目数据
     * @param list
     * @param id
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-12-12
     */
    private List<Map> getContractSubjectChildList(List<Map> list,String id) throws Exception{
        List<Map> dataList = new ArrayList<Map>();
        for(Map map : list){
            String sid = (String) map.get("sid");
            String pid = (String) map.get("pid");
            String leaf = (String) map.get("leaf");
            if(id.equals(pid)){
                List childrenList = getContractSubjectChildList(list,sid);
                if(childrenList.size()>0){
                    map.put("children", childrenList);
                    map.put("state","closed");
                }
                map.put("rid", CommonUtils.getRandomWithTime());
                map.put("iconCls","icon-annex");
                dataList.add(map);
            }
        }
        return dataList;
    }
    /**
     * 根据月份和客户编号获取门店数
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public BigDecimal getStorenumByCustomerid(String customerid) throws Exception{
        BigDecimal storenum = new BigDecimal("0");
        List<Customer> list=getCustomerListByPid(customerid);
        if(null!=list){
            storenum = new BigDecimal(list.size());
        }
        return storenum;
    }

    /**
     * 根据月份和客户编号获取新门店数
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public BigDecimal getNewstorenumByMonthAndCustomerid(String month,String customerid) throws Exception{
        BigDecimal newstorenum = new BigDecimal("0");

        return newstorenum;
    }

    /**
     * 根据月份和客户编号获取新品数
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public BigDecimal getNewgoodsnumByMonthAndCustomerid(String month,String customerid) throws Exception{
        BigDecimal newgoodsnum = new BigDecimal("0");
        return newgoodsnum;
    }

    public String getSubtractMonth(String month,int subnum) throws  Exception{
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(sf.parse(month));
        calendar.add(calendar.MONTH, subnum);
        date = calendar.getTime();
        return sf.format(date);
    }

    public String getThisSeason(String month) throws  Exception{
        String y = month.substring(0,4);
        String m = month.substring(5,7);
        if(Integer.parseInt(m)>=1&&Integer.parseInt(m)<=3){
            m="03";
        }else if(Integer.parseInt(m)>=4&&Integer.parseInt(m)<=6){
            m="06";
        }else if(Integer.parseInt(m)>=7&&Integer.parseInt(m)<=9){
            m="09";
        }else if(Integer.parseInt(m)>=10&&Integer.parseInt(m)<=12){
            m="12";
        }
        return y+"-"+m;
    }

    public String getThisYear(String month) throws  Exception{
        String y = month.substring(0,4);
        String m="12";
        return y+"-"+m;
    }

    public String getMonthLastDay(String dateStr) throws  Exception{
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(sf.parse(dateStr));
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return sf.format(date);
    }
}
