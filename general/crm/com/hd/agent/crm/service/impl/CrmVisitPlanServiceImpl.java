package com.hd.agent.crm.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.crm.dao.CrmVisitPlanMapper;
import com.hd.agent.crm.model.CrmVisitPlan;
import com.hd.agent.crm.model.CrmVisitPlanDetail;
import com.hd.agent.crm.service.ICrmVisitPlanService;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang3.StringUtils;

import java.util.*;


public class CrmVisitPlanServiceImpl extends BaseFilesServiceImpl implements ICrmVisitPlanService {

    private CrmVisitPlanMapper crmVisitPlanMapper;

    public CrmVisitPlanMapper getCrmVisitPlanMapper() {
        return crmVisitPlanMapper;
    }

    public void setCrmVisitPlanMapper(CrmVisitPlanMapper crmVisitPlanMapper) {
        this.crmVisitPlanMapper = crmVisitPlanMapper;
    }

    /**
     * 客户拜访计划分页列表数据
     * @param pageMap
     * @param type
     * @return
     * @throws Exception
     */
    public PageData getCrmData(PageMap pageMap,String type) throws Exception{
        String dataSql = getDataAccessRule("t_crm_visit_plan", "a");
        pageMap.setDataSql(dataSql);
        List<CrmVisitPlan> planList = crmVisitPlanMapper.getCrmData(pageMap);
        for(CrmVisitPlan item : planList){
            Personnel personnel=null;
            Brand brandInfo=null;
            Customer customer=null;
            //品牌信息
            String[] brands = item.getBrands().split(",");
            String[]  brandnames = new String[brands.length];
            for(int i = 0;i<brands.length ;i++){
                Brand brand = getGoodsBrandByID(brands[i]);
                if(brand != null){
                    brandnames[i] = brand.getName();
                }
            }
            item.setBrandname(brandnames);
            //人员信息
            if(StringUtils.isNotEmpty(item.getPersonid())){
                personnel=getPersonnelById(item.getPersonid());
                if(null!=personnel){
                    item.setPersonname(personnel.getName());
                }
            }
            //主管信息
            if(StringUtils.isNotEmpty(item.getLeadid())){
                personnel=getPersonnelById(item.getLeadid());
                if(null!=personnel){
                    item.setLeadname(personnel.getName());
                }
            }
        }
        return new PageData(crmVisitPlanMapper.getCrmDataCount(pageMap),planList,pageMap);
    }

    /**
     * 获取业务员对应的客户
     * @param pageMap
     * @return
     * @throws Exception
     */
    public  PageData getCrmWay(PageMap pageMap) throws Exception {
        String groupcols = (String)pageMap.getCondition().get("groupcols");
        if(null != groupcols){
            String datasql = getDataAccessRule("t_base_sales_customer","t");
            pageMap.setDataSql(datasql);
        }
        String personsort = (String)pageMap.getCondition().get("personsort");
        List<Customer> customerList = new ArrayList<Customer>();
        int count = 0 ;
        if("all".equals(groupcols)){
            if(pageMap.getCondition().containsKey("customerid")){
                Map condtion = pageMap.getCondition();
                condtion.put("id",condtion.get("customerid"));
            }
            customerList = getBaseCustomerMapper().getCustomerList(pageMap);
            count = getBaseCustomerMapper().getCustomerCount(pageMap);
        }else if(personsort.contains("1")){//品牌业务员
            customerList = getBaseCustomerMapper().getCustomerByBrandman(pageMap);
            count = getBaseCustomerMapper().getCustomerByBrandmanCount(pageMap);
        }else if(personsort.contains("2")){//客户业务员
            customerList = getBaseCustomerMapper().getCustomerListBySalesman(pageMap);
            count = getBaseCustomerMapper().getCustomerListBySalesmanCount(pageMap);
        }else if(personsort.contains("3")){//厂家业务员
            customerList = getBaseCustomerMapper().getCustomerByManufactedMan(pageMap);
            count = getBaseCustomerMapper().getCustomerByManufactedManCount(pageMap);
        }
        for(Customer cust : customerList){
            SalesArea salesArea=getSalesareaByID(cust.getSalesarea());
            if(salesArea != null){
                cust.setSalesareaname(salesArea.getName());
            }
            CustomerSort customerSort=getCustomerSortByID(cust.getCustomersort());
            if(customerSort != null)
                cust.setCustomersortname(customerSort.getName());
        }
        return new PageData(count ,customerList,pageMap);
    }

    /**
     * 保存新增的业务员拜访客户（路线）计划
     * @param visitPlan
     * @param planDetailList
     * @return
     * @throws Exception
     */
    public Map addCrmWay(CrmVisitPlan  visitPlan, List<CrmVisitPlanDetail> planDetailList) throws Exception{
        if(null!=visitPlan && StringUtils.isNotEmpty(visitPlan.getPersonid())){
            Personnel personnel = getPersonnelById(visitPlan.getPersonid());
            visitPlan.setLeadid(personnel.getLeadid());
        }
        List<CrmVisitPlanDetail> freshDetailList = new ArrayList();
        for(CrmVisitPlanDetail listDetail : planDetailList){
            //判断相应属性是否为空
            boolean flag = StringUtils.isNotEmpty(listDetail.getDay1()) ||StringUtils.isNotEmpty(listDetail.getDay2())||
                    StringUtils.isNotEmpty(listDetail.getDay3()) |StringUtils.isNotEmpty(listDetail.getDay4())
                    ||StringUtils.isNotEmpty(listDetail.getDay5()) ||StringUtils.isNotEmpty(listDetail.getDay6())
                    ||StringUtils.isNotEmpty(listDetail.getDay7());
            if(flag){//筛选出不为空的数据
                freshDetailList.add(listDetail);
            }
        }
        if(isAutoCreate("t_crm_visit_plan")&& StringUtils.isEmpty(visitPlan.getId())){
            visitPlan.setId(getAutoCreateSysNumbderForeign(visitPlan , "t_crm_visit_plan"));
        }else{
            visitPlan.setId("CP-" + CommonUtils.getDataNumberSendsWithRand());
        }
        String id = visitPlan.getId();
        int a = crmVisitPlanMapper.insertPlan(visitPlan);
        for(CrmVisitPlanDetail detail :  freshDetailList){
            detail.setPersonid( visitPlan.getPersonid());
            detail.setBillid(visitPlan.getId());
            crmVisitPlanMapper.insertDetail(detail);
        }
        Map map = new HashMap();
        if(a >0 ){
            map.put("flag",true);
        }else {
            map.put("flag",false);
        }
        map.put("id",id);
        return map;

    }

    /**
     * 保存修改的业务员拜客户（路线）计划
     * @param visitPlan
     * @param planDetailList
     * @return
     * @throws Exception
     */
    public Map editCrmWay(CrmVisitPlan  visitPlan, List<CrmVisitPlanDetail> planDetailList) throws Exception{
        if(null!=visitPlan && StringUtils.isNotEmpty(visitPlan.getPersonid())){
            Personnel personnel = getPersonnelById(visitPlan.getPersonid());
            visitPlan.setLeadid(personnel.getLeadid());
        }
        List<CrmVisitPlanDetail> freshDetailList = new ArrayList();
        for(CrmVisitPlanDetail listDetail : planDetailList){
            //判断相应属性是否为空
            boolean flag = StringUtils.isNotEmpty(listDetail.getDay1()) ||StringUtils.isNotEmpty(listDetail.getDay2())||
                    StringUtils.isNotEmpty(listDetail.getDay3()) |StringUtils.isNotEmpty(listDetail.getDay4())
                    ||StringUtils.isNotEmpty(listDetail.getDay5()) ||StringUtils.isNotEmpty(listDetail.getDay6())
                    ||StringUtils.isNotEmpty(listDetail.getDay7());
            if(flag){//筛选出不为空的数据
                freshDetailList.add(listDetail);
            }
        }
        int a = crmVisitPlanMapper.updateBySelectdId(visitPlan);
        crmVisitPlanMapper.deleteDetailByBillid(visitPlan.getId());
        for(CrmVisitPlanDetail detail :  freshDetailList){
            detail.setBillid(visitPlan.getId());
            crmVisitPlanMapper.insertDetail(detail);
        }
        Map map = new HashMap();
        if(a >0 ){
            map.put("flag",true);
        }else {
            map.put("flag",false);
        }
        map.put("id",visitPlan.getId());
        return map;

    }

    /**
     * 获取业务员对应路线详细
     * @param id
     * @return
     * @throws Exception
     */
    public CrmVisitPlan viewCrmWay(String id) throws Exception{
        CrmVisitPlan plan = crmVisitPlanMapper.selectById(id);
        if(null!=plan){
            //单据 人员属性 品牌列表
            String personid = plan.getPersonid();
            plan.setEmployetype(getPersonnelById(personid).getEmployetype());
            plan.setName(getPersonnelById(personid).getName());
            //根据单据编号查询明细
            List<CrmVisitPlanDetail> planDetails= crmVisitPlanMapper.getCrmPlanByID(id);
            if(planDetails.size()>0){
                for(CrmVisitPlanDetail cid : planDetails){
                    if(!cid.getDay1().equals("")){
                        Customer c = getCustomerByID(cid.getDay1());
                        if(c != null){
                            cid.setDay1info(c.getName());
                        }
                    }
                    if(!cid.getDay2().equals("")){
                        Customer c = getCustomerByID(cid.getDay2());
                        if(c != null){
                            cid.setDay2info(c.getName());
                        }
                    }
                    if(!cid.getDay3().equals("")){
                        Customer c = getCustomerByID(cid.getDay3());
                        if(c != null){
                            cid.setDay3info(c.getName());
                        }
                    }
                    if(!cid.getDay4().equals("")){
                        Customer c = getCustomerByID(cid.getDay4());
                        if(c != null){
                            cid.setDay4info(c.getName());
                        }
                    }
                    if(!cid.getDay5().equals("")){
                        Customer c = getCustomerByID(cid.getDay5());
                        if(c != null){
                            cid.setDay5info(c.getName());
                        }
                    }
                    if(!cid.getDay6().equals("")){
                        Customer c = getCustomerByID(cid.getDay6());
                        if(c != null){
                            cid.setDay6info(c.getName());
                        }
                    }
                    if(!cid.getDay7().equals("")){
                        Customer c = getCustomerByID(cid.getDay7());
                        if(c != null){
                            cid.setDay7info(c.getName());
                        }
                    }
                }
            }
            plan.setDetailList(planDetails);
        }
        return plan;
    }

    /**
     * 删除指定ID记录
     * @param id
     * @return
     * @throws Exception
     */
    public boolean deletePlanById(String id) throws  Exception{
        int a = crmVisitPlanMapper.deleteByPlanId(id);
        crmVisitPlanMapper.deleteDetailByBillid(id);
        return a>0 ;
    }

    /**
     * 根据业务员编号和对应日期获取当天计划
     * @param pid
     * @param weekday
     * @return
     * @throws Exception
     */
    public Map getCustomerId(String pid,String weekday) throws Exception{
        List<CrmVisitPlanDetail> detail =   crmVisitPlanMapper.selectByPersonId(pid);
        Map map = new HashMap();
        for(int i = 0;i<detail.size();i++){
            String day = "";
            if(weekday.contains("1")){
                day = detail.get(i).getDay1();
            }
            if(weekday.contains("2")){
                day = detail.get(i).getDay2();
            }
            if(weekday.contains("3")){
                day = detail.get(i).getDay3();
            }
            if(weekday.contains("4")){
                day = detail.get(i).getDay4();
            }
            if(weekday.contains("5")){
                day = detail.get(i).getDay5();
            }
            if(weekday.contains("6")){
                day = detail.get(i).getDay6();
            }
            if(weekday.contains("7")){
                day = detail.get(i).getDay7();
            }
            map.put("cid",day);
            if(StringUtils.isEmpty(day)){
                continue;
            }else{
                Customer customer = getCustomerByID(day);
                if(null == customer){
                    map.put("cname","("+day+")"+customer.getName());
                }
            }

        }
        return map;
    }
    //启用指定编码的计划
    public boolean enableCrmPlan(String id) throws  Exception{
        CrmVisitPlan plan = crmVisitPlanMapper.getCrmByPid(id);
        if(plan == null){//为空表示传过来的是主键ID，而不是业务员ID
            plan = crmVisitPlanMapper.selectById(id);
        }
        SysUser user = getSysUser();
        plan.setOpentime(new Date());
        plan.setOpenuserid(user.getUserid());
        plan.setOpenusername(user.getUsername());
        plan.setState("1");
        boolean flag = crmVisitPlanMapper.auditCrmPlan(plan) > 0 ;
        return flag;
    }

    /**
     * 禁用指定编码的计划单
     * @param id
     * @return
     * @throws Exception
     */

    public boolean disableCrmPlan(String id) throws  Exception{
        CrmVisitPlan plan = crmVisitPlanMapper.getCrmByPid(id);
        if(plan == null){//为空表示传过来的是主键ID，而不是业务员ID
            plan = crmVisitPlanMapper.selectById(id);
        }
        boolean flag = false;
        if(plan.getState().equals("2") || plan.getState().equals("4")){
            flag = false;
        }else {
            SysUser user = getSysUser();
            plan.setClosetime(new Date());
            plan.setCloseuserid(user.getUserid());
            plan.setCloseusername(user.getUsername());
            plan.setState("0");
            flag = crmVisitPlanMapper.auditCrmPlan(plan) > 0 ;
        }

        return flag;
    }

    /**
     * 获取当前用户的拜访计划信息
     *
     * @return
     * @throws Exception
     */
    @Override
    public Map getCrmVisitPlanBySysUser(String synctime) throws Exception {
        Map map = new HashMap();
        SysUser sysUser = getSysUser();
        List<CrmVisitPlan> list = new ArrayList<CrmVisitPlan>();
        List dataList = new ArrayList();
        if(StringUtils.isNotEmpty(sysUser.getPersonnelid())){
            if(StringUtils.isEmpty(synctime)){
                synctime = "2014-01-01";
            }
            //用户拜访计划是否修改过
            int changeCount = crmVisitPlanMapper.getCrmVisitPlanChangeCountBySyncdate(sysUser.getPersonnelid(),synctime);
            if(changeCount>0){
                list = crmVisitPlanMapper.getCrmVisitPlanByPersonid(sysUser.getPersonnelid());
                for(CrmVisitPlan crmVisitPlan : list){
                    List detailList = crmVisitPlanMapper.getCrmVisitPlanDetailByBillid(crmVisitPlan.getId());
                    dataList.addAll(detailList);
                }
            }
        }
        List<SysCode> codelist = getCodeByType("displayStandard");
        List standardlist = new ArrayList();
        for(SysCode sysCode:codelist){
            Map dataMap = new HashMap();
            dataMap.put("code",sysCode.getCode());
            dataMap.put("name",sysCode.getCodename());
            dataMap.put("brandid","");
            standardlist.add(dataMap);
        }
        map.put("billList",list);
        map.put("detailList",dataList);
        map.put("standardList",standardlist);
        return map;
    }

    /**
     * 验证对应编码的业务员在数据库中是否存在
     * @param id
     * @return
     * @throws Exception
     */
    public Map validatePersonid(String id) throws Exception{
        CrmVisitPlan plan = crmVisitPlanMapper.getCrmByPid(id);
        Map map = new HashMap();
        if(plan == null){
            map.put("flag",false);
        }else {
            map.put("flag",true);
            map.put("id",plan.getId());
        }
        return map;
    }


}
