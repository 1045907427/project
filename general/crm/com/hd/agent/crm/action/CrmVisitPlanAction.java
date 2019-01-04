package com.hd.agent.crm.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Brand;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.*;
import com.hd.agent.crm.model.CrmVisitPlan;
import com.hd.agent.crm.model.CrmVisitPlanDetail;
import com.hd.agent.crm.service.ICrmVisitPlanService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;

public class CrmVisitPlanAction extends BaseFilesAction {

    protected ICrmVisitPlanService crmVisitPlanService;
    protected CrmVisitPlan visitPlan;

    public CrmVisitPlan getVisitPlan() {
        return visitPlan;
    }

    public void setVisitPlan(CrmVisitPlan visitPlan) {
        this.visitPlan = visitPlan;
    }

    public ICrmVisitPlanService getCrmVisitPlanService() {
        return crmVisitPlanService;
    }

    public void setCrmVisitPlanService(ICrmVisitPlanService crmVisitPlanService) {
        this.crmVisitPlanService = crmVisitPlanService;
    }

    /**
     * 显示计划单页面
     * @return
     */
    public String crmPlanListPage(){
        return SUCCESS;
    }
    /**
     * 显示计划单列表
     * @return
     */
    public String getCrmPlanListPage() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        if(map.containsKey("employetype")){
            String emp = (String) map.get("employetype");
            if(emp.contains("品牌")){
                map.put("employetype",3);
            }
            if(emp.contains("厂家")){
                map.put("employetype",7);
            }
            if(emp.contains("客户")){
                map.put("employetype",0);
            }
        }
        pageMap.setCondition(map);
        PageData pageData = crmVisitPlanService.getCrmData(pageMap,"");
        addJSONObject(pageData);
        return SUCCESS;
    }

    /**
     * 显示panel面
     * @return
     */
    public String crmPlanPage() throws Exception {
        request.setAttribute("type",request.getParameter("type"));
        request.setAttribute("personid",request.getParameter("personid"));
        request.setAttribute("id",request.getParameter("id"));
        return SUCCESS;
    }

    /**
     * 显示计划单增加页面
     * @return
     */
    public String crmPlanAddPage() throws Exception {
        request.setAttribute("autoCreate", isAutoCreate("t_crm_visit_plan"));
        return  SUCCESS;
    }

    /**
     * 新增时跳转到业务员对应的客户页面
     * @return
     */
    public String crmAddWayPage()throws Exception{
        String weekday = request.getParameter("field");
        String personsort = request.getParameter("personsort");
        request.setAttribute("personsort",personsort);
        String personid = request.getParameter("personid");
        request.setAttribute("personid",personid);
        String cid = request.getParameter("cid");
        request.setAttribute("weekday",weekday);
        if(cid == null || StringUtils.isEmpty(cid)){
            Map map = crmVisitPlanService.getCustomerId(personid,weekday);
            request.setAttribute("cid",map.get("cid"));
        }else {
            request.setAttribute("cid",cid);
            String[] cids = cid.split(",");
            String name = "";
            for(String id : cids) {
                if (name == "") {
                    name = getCustomerById(id).getName();
                }else{
                    name = name + "," + getCustomerById(id).getName();
                }

            }
            request.setAttribute("cname",name);
        }

        return SUCCESS;
    }

    /**
     * 修改时跳转到业务员对应的客户页面
     * @return
     */
    public String crmEditWayPage()throws Exception{
        String weekday = request.getParameter("field");
        String personsort = request.getParameter("personsort");
        request.setAttribute("personsort",personsort);
        String personid = request.getParameter("personid");
        request.setAttribute("personid",personid);
        String cid = request.getParameter("cid");
        request.setAttribute("weekday",weekday);
        if(cid == null){
            Map map = crmVisitPlanService.getCustomerId(personid,weekday);
            request.setAttribute("cid",map.get("cid"));
            request.setAttribute("cname",map.get("cname"));
        }else {
            request.setAttribute("cid",cid);
            request.setAttribute("cname",request.getParameter("cname"));
        }

        return SUCCESS;
    }

    /**
     * 显示业务员对应的客户列表
     * @return
     */
    public String getCrmAddPageList()throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData customerList = crmVisitPlanService.getCrmWay(pageMap);
        addJSONObject(customerList);
        return SUCCESS;
    }



    /**
     * 新增保存计划单
     * @return
     */
    @UserOperateLog(key="visitPlan",type=4)
    public String addCrmPlan() throws Exception{
        String saveJson = request.getParameter("saveJson");
        List<CrmVisitPlanDetail> planDetailList = JSONUtils.jsonStrToList(saveJson.trim(), new CrmVisitPlanDetail());
        SysUser sysUser = getSysUser();
        visitPlan.setAddusername(sysUser.getName());
        visitPlan.setAdduserid(sysUser.getUserid());
        visitPlan.setAddtime(new Date());
        visitPlan.setAdddeptid(sysUser.getDepartmentid());
        visitPlan.setAdddeptname(sysUser.getDepartmentname());
        Map map = crmVisitPlanService.addCrmWay(visitPlan,planDetailList);
        if(map.get("flag").equals(true)){
            map.put("wayname",visitPlan.getWayname());
            map.put("personid",visitPlan.getPersonid());
            map.put("state",visitPlan.getState());
            map.put("id",visitPlan.getId());
            addJSONObject(map);
            addLog("拜访计划单新增 编号："+ map.get("id"), true);
            return SUCCESS;
        }else{
            addJSONObject("flag",false);
            addLog("拜访计划单新增 失败");
            return SUCCESS;
        }
    }

    /**
     * 计划单查看
     * @return
     */
    public String crmEditPage()throws Exception{
        String id = request.getParameter("id");
        CrmVisitPlan crmVisitPlan =  crmVisitPlanService.viewCrmWay(id);
        if(null != crmVisitPlan.getDetailList()){
            List<CrmVisitPlanDetail> planDetails = crmVisitPlan.getDetailList();
            if(planDetails != null){
                for(CrmVisitPlanDetail p : planDetails){
                    if(StringUtils.isNotEmpty(p.getDay1info())){
                        p.setDay1info("("+p.getDay1()+") "+ p.getDay1info()); }

                    if(StringUtils.isNotEmpty(p.getDay2info())){
                        p.setDay2info("("+p.getDay2()+") "+ p.getDay2info());}

                    if(StringUtils.isNotEmpty(p.getDay3info())){
                        p.setDay3info("("+p.getDay3()+") "+ p.getDay3info());}

                    if(StringUtils.isNotEmpty(p.getDay4info())){
                        p.setDay4info("("+p.getDay4()+") "+ p.getDay4info());}

                    if(StringUtils.isNotEmpty(p.getDay5info())){
                        p.setDay5info("("+p.getDay5()+") "+ p.getDay5info());}

                    if(StringUtils.isNotEmpty(p.getDay6info())){
                        p.setDay6info("("+p.getDay6()+") "+ p.getDay6info());}

                    if(StringUtils.isNotEmpty(p.getDay7info())){
                        p.setDay7info("("+p.getDay7()+") "+ p.getDay7info());}
                }
            }
            String sort = crmVisitPlan.getEmployetype();
            if(sort.contains("3")){//品牌业务员
                request.setAttribute("sort",1);
            }else if(sort.contains("7")){//厂家业务员
                request.setAttribute("sort",3);
            }else{//客户业务员
                request.setAttribute("sort",2);
            }
            request.setAttribute("visitPlan",crmVisitPlan);
            request.setAttribute("planDetails", JSONUtils.listToJsonStr(planDetails));
        }
        return  SUCCESS;

    }

    /**
     * 修改保存计划单
     * @return
     */
    @UserOperateLog(key="visitPlan",type=4)
    public String editCrmPlan() throws Exception{
        String saveJson = request.getParameter("saveJson");
        List<CrmVisitPlanDetail> planDetailList = JSONUtils.jsonStrToList(saveJson.trim(), new CrmVisitPlanDetail());
        SysUser sysUser = getSysUser();
        visitPlan.setModifyuserid(sysUser.getUserid());
        visitPlan.setModifyusername(sysUser.getName());
        visitPlan.setModifytime(new Date());
        Map map = crmVisitPlanService.editCrmWay(visitPlan,planDetailList);
        if(map.get("flag").equals(true)){
            map.put("wayname",visitPlan.getWayname());
            map.put("personid",visitPlan.getPersonid());
            map.put("state",visitPlan.getState());
            addJSONObject(map);
            addLog("拜访计划单修改 编号："+ map.get("id"), true);
            return SUCCESS;
        }else{
            addJSONObject("flag",false);
            addLog("拜访计划单修改 失败");
            return SUCCESS;
        }
    }
    /**
     * 删除计划单
     * @return
     */
    @UserOperateLog(key="visitPlan",type=4)
    public String deletePlan()throws Exception{
        Map map = new HashMap();
        String id = request.getParameter("id");
        boolean delFlag = canTableDataDelete("t_crm_visit_plan", id); //判断是否被引用，被引用则无法删除。
        if(!delFlag){
            map.put("delFlag", true);
            return SUCCESS;
        }
        boolean flag = crmVisitPlanService.deletePlanById(id);
        map.put("flag", flag);
        map.put("delFlag", false);
        addJSONObject(map);
        addLog("拜访计划单删除 编号："+id, flag);
        return SUCCESS;
    }

    /**
     * 启用计划单
     * @return
     * @throws Exception
     */
    public String enableCrm() throws  Exception{
        String id = request.getParameter("pid");
        boolean flag = crmVisitPlanService.enableCrmPlan(id);
        addJSONObject("flag",flag);
        return  SUCCESS;
    }

    /**
     * 禁用计划单
     * @return
     * @throws Exception
     */
    public String disableCrm() throws  Exception{
        String id = request.getParameter("pid");
        boolean flag = crmVisitPlanService.disableCrmPlan(id);
        addJSONObject("flag",flag);
        return  SUCCESS;
    }

    /**
     * 验证该业务员是否存在（即是否已添加计划单）
     * @return
     * @throws Exception
     */
    public String validateEmploy() throws  Exception{
        String id = request.getParameter("id");
        Map map = crmVisitPlanService.validatePersonid(id);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 批量删除
     * @return
     * @throws Exception
     */
    @UserOperateLog(key="visitPlan",type=4)
    public String deleteMultiPlan() throws  Exception{
        String ids = request.getParameter("ids");
        if(null!=ids){
            String[] idArr = ids.split(",");
            String succssids = "";
            String errorids = "";
            int success = 0 ;
            int failure = 0 ;
            for(String id : idArr){
                boolean flag = crmVisitPlanService.deletePlanById(id);
                if(flag){
                    success ++ ;
                    succssids += id+",";
                }else{
                    failure ++ ;
                    errorids += id+",";
                }
            }

            Map map = new HashMap();
            map.put("success",success);
            map.put("failure",failure);
            map.put("flag", true);

            addJSONObject(map);
            addLog("拜访计划单批量删除 成功编号："+succssids+";失败编号："+errorids, true);
        }else{
            addJSONObject("flag", false);
        }
        return SUCCESS;
    }
    /**
     * 批量禁用
     * @return
     * @throws Exception
     */
    public String multiDisableCrm() throws  Exception{
        String ids = request.getParameter("ids");
        if(null!=ids){
            String[] idArr = ids.split(",");
            String succssids = "";
            String errorids = "";
            int count = 0 ;
            for(String id : idArr){
                boolean flag = crmVisitPlanService.disableCrmPlan(id);
                if(flag){
                    count ++ ;
                    succssids += id+",";
                }else{
                    errorids += id+",";
                }

            }
            Map map = new HashMap();
            map.put("count",count);
            map.put("flag", true);
            if(!"".equals(succssids)){
                map.put("succssids", "禁用成功编号:"+succssids);
            }else{
                map.put("succssids", "");
            }
            if(!"".equals(errorids)){
                map.put("errorids", "禁用失败编号:"+errorids);
            }else{
                map.put("errorids", "");
            }
            addJSONObject(map);
            addLog("拜访计划单批量禁用 成功编号："+succssids+";失败编号："+errorids, true);
        }else{
            addJSONObject("flag", false);
        }
        return SUCCESS;
    }
    /**
     * 批量启用
     * @return
     * @throws Exception
     */
    public String multiEnableCrm() throws  Exception{
        String ids = request.getParameter("ids");
        if(null!=ids){
            String[] idArr = ids.split(",");
            String succssids = "";
            String errorids = "";
            int count = 0 ;
            for(String id : idArr){
                boolean flag = crmVisitPlanService.enableCrmPlan(id);
                if(flag){
                    count ++ ;
                    succssids += id+",";
                }else{
                    errorids += id+",";
                }

            }
            Map map = new HashMap();
            map.put("count",count);
            map.put("flag", true);
            if(!"".equals(succssids)){
                map.put("succssids", "启用成功编号:"+succssids);
            }else{
                map.put("succssids", "");
            }
            if(!"".equals(errorids)){
                map.put("errorids", "启用失败编号:"+errorids);
            }else{
                map.put("errorids", "");
            }
            addJSONObject(map);
            addLog("拜访计划单批量启用 成功编号："+succssids+";失败编号："+errorids, true);
        }else{
            addJSONObject("flag", false);
        }
        return SUCCESS;
    }

    /**
     * 客户拜访计划导入
     * @author lin_xx
     * @date 2017/4/21
     */
    @UserOperateLog(key = "visitPlan", type = 2)
    public String importCrmData() throws Exception {

        CrmVisitPlan crmVisitPlan = new CrmVisitPlan();
        String msg = "";
        boolean flag = false ;
        List<String> businessParam = ExcelUtils.importFirstRowByIndex(excelFile, 0);
        String personid = businessParam.get(2);
        Map vmap = crmVisitPlanService.validatePersonid(personid);
        if((Boolean) vmap.get("flag")){
            msg = "编号为："+personid+"的业务员已存在拜访计划，导入失败！";
        }else{
            Personnel personnel = getPersonnelInfoById(personid);
            if(null != personnel){
                crmVisitPlan.setPersonid(personid);
                crmVisitPlan.setLeadid(personnel.getLeadid());
            }else{
                msg = "业务员不存在，导入失败！";
            }
            String brandis = businessParam.get(4);
            if(StringUtils.isNotEmpty(brandis)){
                String bids = "";
                String[] bid = brandis.split(",");
                for (int i = 0; i < bid.length; i++) {
                    Brand brand = getBaseGoodsService().getBrandInfo(bid[i]);
                    if(null != brand){
                        if(bids == ""){
                            bids = brand.getId();
                        }else{
                            bids += "," + brand.getId();
                        }
                    }
                }
                crmVisitPlan.setBrands(bids);
            }
            if(!"null".equals(businessParam.get(6))){
                crmVisitPlan.setWayname(businessParam.get(6));
            }
            if(!"null".equals(businessParam.get(8))){
                crmVisitPlan.setRemark(businessParam.get(8));
            }
        }
        if(StringUtils.isEmpty(msg)){
            List<CrmVisitPlanDetail> planDetailList = new ArrayList<CrmVisitPlanDetail>();
            List<String> paramList = ExcelUtils.importFirstRowByIndex(excelFile, 1);
            paramList.remove(0);
            List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList); //获取导入数据
            if (list!=null && list.size() != 0) {
                for (Map map : list) {
                    CrmVisitPlanDetail detail = new CrmVisitPlanDetail();
                    String cid1 = (String) map.get("周一");
                    Customer customer1 = getCustomerById(cid1);
                    if(null != customer1){
                        detail.setDay1(cid1);
                    }
                    String cid2 = (String) map.get("周二");
                    Customer customer2 = getCustomerById(cid2);
                    if(null != customer2){
                        detail.setDay2(cid2);
                    }
                    String cid3 = (String) map.get("周三");
                    Customer customer3 = getCustomerById(cid3);
                    if(null != customer3){
                        detail.setDay3(cid3);
                    }
                    String cid4 = (String) map.get("周四");
                    Customer customer4 = getCustomerById(cid4);
                    if(null != customer4){
                        detail.setDay4(cid4);
                    }
                    String cid5 = (String) map.get("周五");
                    Customer customer5 = getCustomerById(cid5);
                    if(null != customer5){
                        detail.setDay5(cid5);
                    }
                    String cid6 = (String) map.get("周六");
                    Customer customer6 = getCustomerById(cid6);
                    if(null != customer6){
                        detail.setDay6(cid6);
                    }
                    String cid7 = (String) map.get("周日");
                    Customer customer7 = getCustomerById(cid7);
                    if(null != customer7){
                        detail.setDay7(cid7);
                    }
                    planDetailList.add(detail);
                }
                planDetailList.remove(0);
                SysUser sysUser = getSysUser();
                crmVisitPlan.setAddusername(sysUser.getName());
                crmVisitPlan.setAdduserid(sysUser.getUserid());
                crmVisitPlan.setAddtime(new Date());
                crmVisitPlan.setAdddeptid(sysUser.getDepartmentid());
                crmVisitPlan.setAdddeptname(sysUser.getDepartmentname());
                Map returnMap = crmVisitPlanService.addCrmWay(crmVisitPlan,planDetailList);
                flag = (Boolean) returnMap.get("flag");
                if(flag){
                    msg = "生成客户拜访计划单："+ returnMap.get("id");
                }else{
                    msg = "导入失败！";
                }
                addLog("拜访计划单新增 编号："+ returnMap.get("id"), flag);

            }
        }
        Map map = new HashedMap();
        map.put("flag",flag);
        map.put("msg",msg);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 导出客户拜访计划
     * @author lin_xx
     * @date 2017/4/24
     */
    public void exportCrmPlanList() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        String title = "";
        if (map.containsKey("excelTitle")) {
            title = map.get("excelTitle").toString();
        } else {
            title = "list";
        }
        if (StringUtils.isEmpty(title)) {
            title = "list";
        }
        //首行列
        List<String> dataListCell = new ArrayList<String>();
        dataListCell.add("person");
        dataListCell.add("brand");
        dataListCell.add("wayname");
        dataListCell.add("remark");
        dataListCell.add("day1");
        dataListCell.add("day2");
        dataListCell.add("day3");
        dataListCell.add("day4");
        dataListCell.add("day5");
        dataListCell.add("day6");
        dataListCell.add("day7");
        //组装列的字段和值
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        pageMap.setCondition(map);
        PageData pageData = crmVisitPlanService.getCrmData(pageMap,"");
        List<CrmVisitPlan> visitPlanList = pageData.getList();
        for(CrmVisitPlan crmVisitPlan : visitPlanList){
            CrmVisitPlan plan = crmVisitPlanService.viewCrmWay(crmVisitPlan.getId());
            List<CrmVisitPlanDetail> list = plan.getDetailList();
            for(CrmVisitPlanDetail detail : list){
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("person" ,crmVisitPlan.getPersonname());
                String[] brands = plan.getBrands().split(",");
                String brandname = "";
                for (int i = 0; i < brands.length; i++) {
                    Brand brand = getBaseGoodsService().getBrandInfo(brands[i]);
                    if(null != brand){
                        brandname += brand.getName()+",";
                    }
                }
                if(brandname.contains(",")){
                    brandname = brandname.substring(0,brandname.lastIndexOf(","));
                }
                map1.put("brand",brandname);
                map1.put("wayname",plan.getWayname());
                map1.put("remark",plan.getRemark());
                if(StringUtils.isNotEmpty(detail.getDay1info())){
                    map1.put("day1","("+detail.getDay1()+") "+ detail.getDay1info());
                }
                if(StringUtils.isNotEmpty(detail.getDay2info())){
                    map1.put("day2","("+detail.getDay2()+") "+ detail.getDay2info());
                }
                if(StringUtils.isNotEmpty(detail.getDay3info())){
                    map1.put("day3","("+detail.getDay3()+") "+ detail.getDay3info());
                }
                if(StringUtils.isNotEmpty(detail.getDay4info())){
                    map1.put("day4","("+detail.getDay4()+") "+ detail.getDay4info());
                }
                if(StringUtils.isNotEmpty(detail.getDay5info())){
                    map1.put("day5","("+detail.getDay5()+") "+ detail.getDay5info());
                }
                if(StringUtils.isNotEmpty(detail.getDay6info())){
                    map1.put("day6","("+detail.getDay6()+") "+ detail.getDay6info());
                }
                if(StringUtils.isNotEmpty(detail.getDay7info())){
                    map1.put("day7","("+detail.getDay7()+") "+ detail.getDay7info());
                }
                dataList.add(map1);
            }
        }
        // 模板文件路径
        String tempFilePath = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/CrmPlanExportSample.xls");
        String filename = title + ".xls";

        ExcelFileUtils handle = new ExcelFileUtils();
        handle.writeListData(tempFilePath, dataListCell, dataList, 0);
        // 文件导出路径
        String path = ServletActionContext.getServletContext().getRealPath("common");
        File file = new File(path, filename);
        if (!file.exists()) {
            file.createNewFile();
        }
        OutputStream os = new FileOutputStream(file);
        // 写到输出流并关闭资源
        handle.writeAndClose(tempFilePath, os);
        os.flush();
        os.close();
        handle.readClose(tempFilePath);
        // 下载已经导出的文件到客户端
        ExcelUtils.downloadExcel(path, filename);

    }




}
