package com.hd.agent.salestarget.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.Brand;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.CustomerSort;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.salestarget.dao.SalesTargetInputMapper;
import com.hd.agent.salestarget.model.SalesTargetInput;
import com.hd.agent.salestarget.service.ISalesTargetInputService;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 销售目标录入服务层
 * Created by master on 2016/7/15.
 */
public class SalesTargetInputServiceImpl extends BaseFilesServiceImpl implements ISalesTargetInputService {
    public SalesTargetInputMapper salesTargetInputMapper;

    public SalesTargetInputMapper getSalesTargetInputMapper() {
        return salesTargetInputMapper;
    }

    public void setSalesTargetInputMapper(SalesTargetInputMapper salesTargetInputMapper) {
        this.salesTargetInputMapper = salesTargetInputMapper;
    }

    /**
     * 销售目标分页列表数据
     *
     * @param pageMap
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    @Override
    public PageData showSalesTargetInputListPageData(PageMap pageMap) throws Exception {
        String cols = getAccessColumnList("t_salestarget_input","t");
        pageMap.setCols(cols);
        String sql = getDataAccessRule("t_salestarget_input","t");
        pageMap.setDataSql(sql);
        Map condition=pageMap.getCondition();
        String isExportData=(String)condition.get("isExportData");
        if("true".equals(isExportData)){
            condition.put("isPageflag", "true");
        }
        List<SalesTargetInput> list=salesTargetInputMapper.getSalesTargetInputPageList(pageMap);
        for(SalesTargetInput item : list){
            if(StringUtils.isNotEmpty(item.getBrandid())){
                Brand brandInfo=getGoodsBrandByID(item.getBrandid());
                if(null!=brandInfo){
                    item.setBrandname(brandInfo.getName());
                }
            }
            if(StringUtils.isNotEmpty(item.getCustomerid())){
                Customer customerInfo=getCustomerByID(item.getCustomerid());
                if(null!=customerInfo){
                    item.setCustomername(customerInfo.getName());
                    item.setCustomersort(customerInfo.getCustomersort());
                    item.setSalesuserid(customerInfo.getSalesuserid());
                }
            }
            if(StringUtils.isNotEmpty(item.getCustomersort())){
                CustomerSort customerSortInfo=getCustomerSortByID(item.getCustomersort());
                if(null!=customerSortInfo){
                    item.setCustomersortname(customerSortInfo.getName());
                }
            }
            if(StringUtils.isNotEmpty(item.getSalesuserid())){
                Personnel personnelInfo=getPersonnelById(item.getSalesuserid());
                if(null!=personnelInfo){
                    item.setSalesusername(personnelInfo.getName());
                }
            }
            if(StringUtils.isNotEmpty(item.getStatus())) {
                SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(item.getStatus(), "status");
                if (null != sysCode) {
                    item.setStatusname(sysCode.getCodename());
                }
            }
        }
        int total=salesTargetInputMapper.getSalesTargetInputPageCount(pageMap);
        List<SalesTargetInput> footerList=new ArrayList<SalesTargetInput>();
        /*
        SalesTargetInput salesTargetInputSum=salesTargetInputMapper.getSalesTargetInputPageSum(pageMap);
        if(salesTargetInputSum==null){
            salesTargetInputSum=new SalesTargetInput();
        }
        footerList.add(salesTargetInputSum);
        pageData.setFooter(footerList);
        */
        PageData pageData=new PageData(total,list,pageMap);
        SalesTargetInput salesTargetInputSum=new SalesTargetInput();
        footerList.add(salesTargetInputSum);
        pageData.setFooter(footerList);
        return pageData;
    }

    /**
     * 添加销售目标
     *
     * @param salesTargetInput
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    @Override
    public Map addSalesTargetInput(SalesTargetInput salesTargetInput) throws Exception {
        Map resultMap=new HashMap();
        boolean flag=false;
        SysUser sysUser=getSysUser();

        String[] date = salesTargetInput.getYearmonth().split("-");
        salesTargetInput.setYear(Integer.parseInt(date[0]));
        salesTargetInput.setMonth(Integer.parseInt(date[1]));

        salesTargetInput.setAdduserid(sysUser.getAdduserid());
        salesTargetInput.setAddusername(sysUser.getName());
        salesTargetInput.setAddtime(new Date());
        salesTargetInput.setAdddeptid(sysUser.getDepartmentid());
        salesTargetInput.setAdddeptname(sysUser.getDepartmentname());

        if(salesTargetInput.getFirstgrossprofit()==null){
            salesTargetInput.setFirstgrossprofit(BigDecimal.ZERO);
        }

        if(salesTargetInput.getSecondgrossprofit()==null){
            salesTargetInput.setSecondgrossprofit(BigDecimal.ZERO);
        }

        PageMap pageMap=new PageMap();
        Map condition=new HashMap();
        condition.put("yearmonth",salesTargetInput.getYearmonth());
        condition.put("customerid",salesTargetInput.getCustomerid());
        condition.put("brandid",salesTargetInput.getBrandid());
        pageMap.setCondition(condition);
        int iCount=salesTargetInputMapper.getSalesTargetInputPageCount(pageMap);
        if(iCount>0){
            resultMap.put("flag",false);
            resultMap.put("msg","已经存在相同年月且同门店且同品牌的销售目标");
            return resultMap;
        }

        if (isAutoCreate("t_salestarget_input")) {
            // 获取自动编号
            salesTargetInput.setId(getAutoCreateSysNumbderForeign(salesTargetInput, "t_salestarget_input"));
        }else{
            salesTargetInput.setId("XM-"+ CommonUtils.getDataNumber());
        }

        salesTargetInput.setFirstgrossprofitrate(
                updateCalcTargetRate(salesTargetInput.getFirstsalestarget(),salesTargetInput.getFirstgrossprofit()));

        salesTargetInput.setSecondgrossprofitrate(
                updateCalcTargetRate(salesTargetInput.getSecondsalestarget(), salesTargetInput.getSecondgrossprofit()));

        flag=salesTargetInputMapper.insertSalesTargetInput(salesTargetInput)>0;

        resultMap.put("flag",flag);
        return resultMap;
    }

    /**
     * 添加销售目标
     *
     * @param salesTargetInput
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    @Override
    public Map editSalesTargetInput(SalesTargetInput salesTargetInput) throws Exception {

        Map resultMap=new HashMap();

        if(StringUtils.isEmpty(salesTargetInput.getId())){
            resultMap.put("flag",false);
            resultMap.put("msg","未能找到相关销售目标数据");
            return resultMap;
        }
        SalesTargetInput oldSalesTargetInput=salesTargetInputMapper.getSalesTargetInput(salesTargetInput.getId());
        if(null==oldSalesTargetInput){
            resultMap.put("flag",false);
            resultMap.put("msg","未能找到相关销售目标数据");
            return resultMap;
        }
        if("3".equals(oldSalesTargetInput.getStatus()) || "4".equals(oldSalesTargetInput.getStatus())){
            resultMap.put("flag",false);
            resultMap.put("msg","保存状态下才能修改销售目标数据");
            return resultMap;
        }
        if(salesTargetInput.getFirstgrossprofit()==null){
            salesTargetInput.setFirstgrossprofit(BigDecimal.ZERO);
        }

        if(salesTargetInput.getSecondgrossprofit()==null){
            salesTargetInput.setSecondgrossprofit(BigDecimal.ZERO);
        }
        boolean flag=false;
        SysUser sysUser=getSysUser();

        String[] date = salesTargetInput.getYearmonth().split("-");
        salesTargetInput.setYear(Integer.parseInt(date[0]));
        salesTargetInput.setMonth(Integer.parseInt(date[1]));

        salesTargetInput.setModifytime(new Date());
        salesTargetInput.setModifyuserid(sysUser.getUserid());
        salesTargetInput.setModifyusername(sysUser.getName());

        PageMap pageMap=new PageMap();
        Map condition=new HashMap();
        condition.put("yearmonth", salesTargetInput.getYearmonth());
        condition.put("customerid",salesTargetInput.getCustomerid());
        condition.put("brandid",salesTargetInput.getBrandid());

        condition.put("notyearmonth", oldSalesTargetInput.getYearmonth());
        condition.put("notcustomerid", oldSalesTargetInput.getCustomerid());
        condition.put("notbrandid", oldSalesTargetInput.getBrandid());
        pageMap.setCondition(condition);

        salesTargetInput.setFirstgrossprofitrate(
                updateCalcTargetRate(salesTargetInput.getFirstsalestarget(),salesTargetInput.getFirstgrossprofit()));

        salesTargetInput.setSecondgrossprofitrate(
                updateCalcTargetRate(salesTargetInput.getSecondsalestarget(), salesTargetInput.getSecondgrossprofit()));

        int iCount=salesTargetInputMapper.getSalesTargetInputPageCount(pageMap);
        if(iCount>0){
            resultMap.put("flag",false);
            resultMap.put("msg","已经存在相同年月且同渠道且同品牌且同客户业务员的销售目标");
            return resultMap;
        }

        flag=salesTargetInputMapper.updateSalesTargetInput(salesTargetInput)>0;

        resultMap.put("flag",flag);
        return resultMap;
    }

    /**
     * 计算销售目标毛利率
     * @param target
     * @param gross
     * @return
     * @throws Exception
     */
    public BigDecimal updateCalcTargetRate(BigDecimal target,BigDecimal gross) throws Exception{
        BigDecimal tmpd=BigDecimal.ZERO;
        if(target==null || gross==null){
            return tmpd;
        }
        if(target.compareTo(BigDecimal.ZERO)!=0){
            tmpd=gross.divide(target,6,BigDecimal.ROUND_HALF_UP);
            tmpd=tmpd.multiply(new BigDecimal(100)).setScale(6,BigDecimal.ROUND_HALF_UP);
        }
        return tmpd;
    }

    @Override
    public SalesTargetInput showSalesTargetInput(String id) throws Exception{
        SalesTargetInput salesTargetInput=salesTargetInputMapper.getSalesTargetInput(id);
        return salesTargetInput;
    }

    /**
     * 删除销售目标
     *
     * @param id
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    @Override
    public Map deleteSalesTargetInput(String id) throws Exception {
        Map resultMap=new HashMap();

        if(null == id || "".equals(id.trim())){
            resultMap.put("flag",false);
            resultMap.put("msg","未能找到相关销售目标数据");
            return resultMap;
        }
        SalesTargetInput oldSalesTargetInput=salesTargetInputMapper.getSalesTargetInput(id.trim());

        if(null==oldSalesTargetInput){
            resultMap.put("flag",false);
            resultMap.put("msg","未能找到相关销售目标数据");
            return resultMap;
        }
        if("3".equals(oldSalesTargetInput.getStatus())){
            resultMap.put("flag",false);
            resultMap.put("msg","审核通过的销售目标不能被删除");
            return resultMap;
        }
        if("4".equals(oldSalesTargetInput.getStatus())){
            resultMap.put("flag",false);
            resultMap.put("msg","关闭状态下的销售目标不能被删除");
            return resultMap;
        }
        boolean flag=salesTargetInputMapper.deleteSalesTargetInput(id.trim())>0;
        resultMap.put("flag",flag);
        return resultMap;
    }

    public Map deleteSalesTargetInputMore(String idarrs) throws Exception{
        Map resultMap=new HashMap();
        int iSuccess=0;
        int iFailure=0;
        int iNohandle=0;
        StringBuffer msgBuffer=new StringBuffer();
        StringBuffer successIdBuffer=new StringBuffer();
        StringBuffer failIdBuffer=new StringBuffer();
        if(null==idarrs || "".equals(idarrs.trim())){

            resultMap.put("flag", false);
            resultMap.put("isuccess", iSuccess);
            resultMap.put("ifailure", iFailure);
            resultMap.put("inohandle", iNohandle);
            resultMap.put("succssids","");
            resultMap.put("failids", "");
            return resultMap;
        }
        String[] idArr=idarrs.trim().split(",");
        for(String id : idArr){
            if(null==id || "".equals(id.trim())){
                //iNohandle=iNohandle+1;
                continue;
            }
            Map delResultMap=deleteSalesTargetInput(id);
            Boolean flag=false;
            String msg=null;
            if(null!=delResultMap){
                flag=(Boolean)delResultMap.get("flag");
                if(null==flag){
                    flag=false;
                }
                if(delResultMap.containsKey("msg")){
                    msg=(String)delResultMap.get("msg");
                }
            }
            if(!flag){
                msgBuffer.append(" 单据号");
                msgBuffer.append(id);
                msgBuffer.append(" ");
                if(msg!=null && !"".equals(msg.trim())){
                    msgBuffer.append(msg);
                }else{
                    msgBuffer.append("删除失败");
                }
                msgBuffer.append("<br/>");
            }
            if(flag){
                if(successIdBuffer.length()>0){
                    successIdBuffer.append(",");
                }
                successIdBuffer.append(id);
                iSuccess=iSuccess+1;
            }else{
                if(failIdBuffer.length()>0){
                    failIdBuffer.append(",");
                }
                failIdBuffer.append(id);
                iFailure=iFailure+1;
            }
        }
        resultMap.clear();
        if(iSuccess>0){
            resultMap.put("flag", true);
        }else{
            resultMap.put("flag", false);
        }
        resultMap.put("isuccess", iSuccess);
        resultMap.put("ifailure", iFailure);
        resultMap.put("inohandle", iNohandle);
        resultMap.put("succssids", successIdBuffer.toString());
        resultMap.put("failids", failIdBuffer.toString());
        resultMap.put("msg",msgBuffer.toString());
        return resultMap;
    }

    /**
     * 审核销售目标
     *
     * @param id
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    @Override
    public Map auditSalesTargetInput(String id) throws Exception {
        Map resultMap=new HashMap();

        if(null == id || "".equals(id.trim())){
            resultMap.put("flag",false);
            resultMap.put("msg","未能找到相关销售目标数据");
            return resultMap;
        }
        SalesTargetInput oldSalesTargetInput=salesTargetInputMapper.getSalesTargetInput(id.trim());

        if(null==oldSalesTargetInput){
            resultMap.put("flag",false);
            resultMap.put("msg","未能找到相关销售目标数据");
            return resultMap;
        }
        if("3".equals(oldSalesTargetInput.getStatus())){
            resultMap.put("flag",false);
            resultMap.put("msg","审核通过的销售目标不能被审核");
            return resultMap;
        }
        if("4".equals(oldSalesTargetInput.getStatus())){
            resultMap.put("flag",false);
            resultMap.put("msg","关闭状态下的销售目标不能被审核");
            return resultMap;
        }
        SalesTargetInput auditSalesTargetInput=new SalesTargetInput();
        SysUser sysUser=getSysUser();
        auditSalesTargetInput.setId(oldSalesTargetInput.getId());
        auditSalesTargetInput.setStatus("3");
        auditSalesTargetInput.setAudittime(new Date());
        auditSalesTargetInput.setAudituserid(sysUser.getUserid());
        auditSalesTargetInput.setAuditusername(sysUser.getName());
        boolean flag=salesTargetInputMapper.auditSalesTargetInput(auditSalesTargetInput)>0;
        resultMap.put("flag",flag);
        return resultMap;
    }

    /**
     * 审核销售目标
     *
     * @param idarrs
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    @Override
    public Map auditSalesTargetInputMore(String idarrs) throws Exception {
        Map resultMap=new HashMap();
        int iSuccess=0;
        int iFailure=0;
        int iNohandle=0;
        StringBuffer successIdBuffer=new StringBuffer();
        StringBuffer failIdBuffer=new StringBuffer();
        StringBuffer msgBuffer=new StringBuffer();
        if(null==idarrs || "".equals(idarrs.trim())){

            resultMap.put("flag", false);
            resultMap.put("isuccess", iSuccess);
            resultMap.put("ifailure", iFailure);
            resultMap.put("inohandle", iNohandle);
            resultMap.put("succssids","");
            resultMap.put("failids", "");
            return resultMap;
        }
        String[] idArr=idarrs.trim().split(",");
        for(String id : idArr){
            if(null==id || "".equals(id.trim())){
                //iNohandle=iNohandle+1;
                continue;
            }
            Map delResultMap=auditSalesTargetInput(id);
            Boolean flag=false;
            if(null!=delResultMap){
                flag=(Boolean)delResultMap.get("flag");
                if(null==flag){
                    flag=false;
                }
            }
            String msg=null;
            if(delResultMap.containsKey("msg")){
                msg=(String)delResultMap.get("msg");
            }
            if(!flag){
                msgBuffer.append(" 单据号");
                msgBuffer.append(id);
                msgBuffer.append(" ");
                if(msg!=null && !"".equals(msg.trim())){
                    msgBuffer.append(msg);
                }else{
                    msgBuffer.append("审核失败");
                }
                msgBuffer.append("<br/>");
            }
            if(flag){
                if(successIdBuffer.length()>0){
                    successIdBuffer.append(",");
                }
                successIdBuffer.append(id);
                iSuccess=iSuccess+1;
            }else{
                if(failIdBuffer.length()>0){
                    failIdBuffer.append(",");
                }
                failIdBuffer.append(id);
                iFailure=iFailure+1;
            }
        }
        resultMap.clear();
        if(iSuccess>0){
            resultMap.put("flag", true);
        }else{
            resultMap.put("flag", false);
        }
        resultMap.put("isuccess", iSuccess);
        resultMap.put("ifailure", iFailure);
        resultMap.put("inohandle", iNohandle);
        resultMap.put("succssids", successIdBuffer.toString());
        resultMap.put("failids", failIdBuffer.toString());
        resultMap.put("msg",msgBuffer.toString());
        return resultMap;
    }
    /**
     * 反审销售目标
     *
     * @param id
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    @Override
    public Map oppauditSalesTargetInput(String id) throws Exception {
        Map resultMap=new HashMap();

        if(null == id || "".equals(id.trim())){
            resultMap.put("flag",false);
            resultMap.put("msg","未能找到相关销售目标数据");
            return resultMap;
        }
        SalesTargetInput oldSalesTargetInput=salesTargetInputMapper.getSalesTargetInput(id.trim());
        if(null==oldSalesTargetInput){
            resultMap.put("flag",false);
            resultMap.put("msg","未能找到相关销售目标数据");
            return resultMap;
        }
        if(!"3".equals(oldSalesTargetInput.getStatus())){
            resultMap.put("flag",false);
            resultMap.put("msg","审核通过的销售目标才能被反审");
            return resultMap;
        }
        boolean flag=salesTargetInputMapper.oppauditSalesTargetInput(id.trim())>0;
        resultMap.put("flag",flag);
        return resultMap;
    }
    /**
     * 反审销售目标
     *
     * @param idarrs
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    @Override
    public Map oppauditSalesTargetInputMore(String idarrs) throws Exception {
        Map resultMap=new HashMap();
        int iSuccess=0;
        int iFailure=0;
        int iNohandle=0;
        StringBuffer msgBuffer=new StringBuffer();
        StringBuffer successIdBuffer=new StringBuffer();
        StringBuffer failIdBuffer=new StringBuffer();
        if(null==idarrs || "".equals(idarrs.trim())){

            resultMap.put("flag", false);
            resultMap.put("isuccess", iSuccess);
            resultMap.put("ifailure", iFailure);
            resultMap.put("inohandle", iNohandle);
            resultMap.put("succssids","");
            resultMap.put("failids", "");
            return resultMap;
        }
        String[] idArr=idarrs.trim().split(",");
        for(String id : idArr){
            if(null==id || "".equals(id.trim())){
                //iNohandle=iNohandle+1;
                continue;
            }
            Map delResultMap=oppauditSalesTargetInput(id);
            Boolean flag=false;
            if(null!=delResultMap){
                flag=(Boolean)delResultMap.get("flag");
                if(null==flag){
                    flag=false;
                }
            }
            String msg=null;
            if(delResultMap.containsKey("msg")){
                msg=(String)delResultMap.get("msg");
            }
            if(!flag){
                msgBuffer.append(" 单据号");
                msgBuffer.append(id);
                msgBuffer.append(" ");
                if(msg!=null && !"".equals(msg.trim())){
                    msgBuffer.append(msg);
                }else{
                    msgBuffer.append("反审失败");
                }
                msgBuffer.append("<br/>");
            }
            if(flag){
                iSuccess=iSuccess+1;
            }else{
                iFailure=iFailure+1;
            }
        }
        resultMap.clear();
        if(iSuccess>0){
            resultMap.put("flag", true);
        }else{
            resultMap.put("flag", false);
        }
        resultMap.put("isuccess", iSuccess);
        resultMap.put("ifailure", iFailure);
        resultMap.put("inohandle", iNohandle);
        resultMap.put("succssids", successIdBuffer.toString());
        resultMap.put("failids", failIdBuffer.toString());
        resultMap.put("msg",msgBuffer.toString());
        return resultMap;
    }

    @Override
    public Map addDRSalesTargetInput(List<Map> list) throws Exception {
        Map resultMap = new HashMap();
        boolean flag = false;
        int successNum = 0;
        int failureNum = 0;
        int repeatNum = 0;
        int closeNum = 0;
        int errorNum = 0;
        int levelNum=0;
        List<SalesTargetInput> errorDataList=new ArrayList<SalesTargetInput>();
        SysUser sysUser = getSysUser();
        String tmpStr="";
        boolean isId=isAutoCreate("t_salestarget_input");
        if(null != list && list.size() != 0){
            SalesTargetInput salesTargetInput=null;
            Map paramMap=new HashMap();
            for(Map dataMap : list){
                boolean isError=false;
                int errorLine=0;
                StringBuffer errorInfoSb=new StringBuffer();
                try{
                    flag=false;
                    salesTargetInput=new SalesTargetInput();

                    salesTargetInput.setAdduserid(sysUser.getAdduserid());
                    salesTargetInput.setAddusername(sysUser.getName());
                    salesTargetInput.setAddtime(new Date());
                    salesTargetInput.setAdddeptid(sysUser.getDepartmentid());
                    salesTargetInput.setAdddeptname(sysUser.getDepartmentname());

                    Integer tmpInt=null;
                    if(dataMap.containsKey("year")) {
                        tmpStr = (String) dataMap.get("year");
                        if (StringUtils.isNotEmpty(tmpStr) && CommonUtils.isNumStr(tmpStr)) {
                            tmpInt=Integer.parseInt(tmpStr);
                            salesTargetInput.setYear(tmpInt);
                        }
                    }
                    if(dataMap.containsKey("month")) {
                        tmpStr = (String) dataMap.get("month");
                        if (StringUtils.isNotEmpty(tmpStr) && CommonUtils.isNumStr(tmpStr)) {
                            tmpInt=Integer.parseInt(tmpStr);
                            salesTargetInput.setMonth(tmpInt);
                        }
                    }
                    if(dataMap.containsKey("customername")) {
                        tmpStr = (String) dataMap.get("customername");
                        salesTargetInput.setCustomername(tmpStr);
                    }
                    if(dataMap.containsKey("brandname")) {
                        tmpStr = (String) dataMap.get("brandname");
                        salesTargetInput.setBrandname(tmpStr);
                    }
                    BigDecimal tmpd=BigDecimal.ZERO;

                    if(dataMap.containsKey("firstsalestarget")) {
                        tmpStr = (String) dataMap.get("firstsalestarget");
                        if (StringUtils.isNotEmpty(tmpStr) && CommonUtils.isNumStr(tmpStr)) {
                            tmpd=new BigDecimal(tmpStr);
                            salesTargetInput.setFirstsalestarget(tmpd);
                        }
                    }
                    if(dataMap.containsKey("firstgrossprofit")) {
                        tmpStr = (String) dataMap.get("firstgrossprofit");
                        if (StringUtils.isNotEmpty(tmpStr) && CommonUtils.isNumStr(tmpStr)) {
                            tmpd=new BigDecimal(tmpStr);
                            salesTargetInput.setFirstgrossprofit(tmpd);
                        }
                    }
                    if(dataMap.containsKey("secondsalestarget")) {
                        tmpStr = (String) dataMap.get("secondsalestarget");
                        if (StringUtils.isNotEmpty(tmpStr) && CommonUtils.isNumStr(tmpStr)) {
                            tmpd=new BigDecimal(tmpStr);
                            salesTargetInput.setSecondsalestarget(tmpd);
                        }
                    }
                    if(dataMap.containsKey("secondgrossprofit")) {
                        tmpStr = (String) dataMap.get("secondgrossprofit");
                        if (StringUtils.isNotEmpty(tmpStr) && CommonUtils.isNumStr(tmpStr)) {
                            tmpd=new BigDecimal(tmpStr);
                            salesTargetInput.setSecondgrossprofit(tmpd);
                        }
                    }
                    if (dataMap.containsKey("remark")) {
                        tmpStr = (String) dataMap.get("remark");
                        salesTargetInput.setRemark(tmpStr);
                    }


                    if(salesTargetInput.getYear()==null){
                        isError=true;
                        errorLine=errorLine+1;
                        errorInfoSb.append(errorLine);
                        errorInfoSb.append(")年份不能为空\n");
                    }else if(salesTargetInput.getYear()<=0){
                        isError=true;
                        errorLine=errorLine+1;
                        errorInfoSb.append(errorLine);
                        errorInfoSb.append(")年份不正确\n");
                    }

                    if(salesTargetInput.getMonth()==null){
                        isError=true;
                        errorLine=errorLine+1;
                        errorInfoSb.append(errorLine);
                        errorInfoSb.append(")月份不能为空\n");
                    }else if(salesTargetInput.getMonth()<=0 || salesTargetInput.getMonth()>12){
                        isError=true;
                        errorLine=errorLine+1;
                        errorInfoSb.append(errorLine);
                        errorInfoSb.append(")月份不正确\n");
                    }
                    String smonth=salesTargetInput.getMonth()<10?"0"+salesTargetInput.getMonth():salesTargetInput.getMonth()+"";
                    salesTargetInput.setYearmonth(salesTargetInput.getYear()+"-"+smonth);
                    Customer customerInfo =null;
                    Brand brandInfo=null;
                    if(StringUtils.isEmpty(salesTargetInput.getCustomername())) {
                        isError=true;
                        errorLine=errorLine+1;
                        errorInfoSb.append(errorLine);
                        errorInfoSb.append(")客户名称不能为空\n");
                    }else {
                        List<Customer> customerList = getBaseCustomerMapper().getCustomerByName(salesTargetInput.getCustomername());
                        if (customerList == null || customerList.size() == 0) {
                            isError=true;
                            errorLine=errorLine+1;
                            errorInfoSb.append(errorLine);
                            errorInfoSb.append(")根据客户名称未能找到系统中对应的客户档案信息\n");
                        }else {
                            customerInfo = customerList.get(0);
                            salesTargetInput.setCustomerid(customerInfo.getId());
                        }
                    }
                    if(StringUtils.isEmpty(salesTargetInput.getBrandname())) {
                        isError=true;
                        errorLine=errorLine+1;
                        errorInfoSb.append(errorLine);
                        errorInfoSb.append(")品牌名称不能为空\n");
                    }else{
                        paramMap.clear();
                        paramMap.put("name",salesTargetInput.getBrandname());
                        List<Brand> brandList=getBaseFilesGoodsMapper().getBrandListByMap(paramMap);
                        if(brandList==null || brandList.size()==0){
                            isError=true;
                            errorLine=errorLine+1;
                            errorInfoSb.append(errorLine);
                            errorInfoSb.append(")根据品牌名称未能找到系统中对应的品牌档案信息\n");
                        }else{
                            brandInfo=brandList.get(0);
                            salesTargetInput.setBrandid(brandInfo.getId());
                        }
                    }
                    if(null==salesTargetInput.getFirstsalestarget() ){
                        isError=true;
                        errorLine=errorLine+1;
                        errorInfoSb.append(errorLine);
                        errorInfoSb.append(")第一销售目标不能为空\n");
                    }else if(salesTargetInput.getFirstsalestarget().compareTo(BigDecimal.ZERO)<0){
                        isError=true;
                        errorLine=errorLine+1;
                        errorInfoSb.append(errorLine);
                        errorInfoSb.append(")第一销售目标不能为负数\n");
                    }

                    if(null==salesTargetInput.getSecondsalestarget()){
                        isError=true;
                        errorLine=errorLine+1;
                        errorInfoSb.append(errorLine);
                        errorInfoSb.append(")第二销售目标不能为空\n");
                    }else if(salesTargetInput.getSecondsalestarget().compareTo(BigDecimal.ZERO)<0){
                        isError=true;
                        errorLine=errorLine+1;
                        errorInfoSb.append(errorLine);
                        errorInfoSb.append(")第二销售目标不能为负数\n");
                    }

                    if(isError){
                        failureNum=failureNum+1;
                        salesTargetInput.setErrormessage(errorInfoSb.toString());
                        errorDataList.add(salesTargetInput);
                        continue;
                    }

                    PageMap pageMap=new PageMap();
                    paramMap.clear();
                    paramMap.put("yearmonth",salesTargetInput.getYearmonth());
                    paramMap.put("customerid",salesTargetInput.getCustomerid());
                    paramMap.put("brandid",salesTargetInput.getBrandid());
                    pageMap.setCondition(paramMap);
                    int iCount=salesTargetInputMapper.getSalesTargetInputPageCount(pageMap);
                    if(iCount>0){
                        failureNum=failureNum+1;
                        salesTargetInput.setErrormessage("已经存在相同年月且同门店且同品牌的销售目标");
                        errorDataList.add(salesTargetInput);
                        continue;
                    }

                    if(salesTargetInput.getFirstgrossprofit()==null) {
                        salesTargetInput.setFirstgrossprofit(BigDecimal.ZERO);
                    }
                    if(salesTargetInput.getSecondgrossprofit()==null) {
                        salesTargetInput.setSecondgrossprofit(BigDecimal.ZERO);
                    }
                    salesTargetInput.setFirstgrossprofitrate(
                            updateCalcTargetRate(salesTargetInput.getFirstsalestarget(),salesTargetInput.getFirstgrossprofit()));

                    salesTargetInput.setSecondgrossprofitrate(
                            updateCalcTargetRate(salesTargetInput.getSecondsalestarget(), salesTargetInput.getSecondgrossprofit()));

                    salesTargetInput.setStatus("2");
                    if (isId) {
                        // 获取自动编号
                        salesTargetInput.setId(getAutoCreateSysNumbderForeign(salesTargetInput, "t_salestarget_input"));
                    }else{
                        salesTargetInput.setId("XM-"+CommonUtils.getDataNumber());
                    }
                    flag=salesTargetInputMapper.insertSalesTargetInput(salesTargetInput) > 0;
                    if(flag){
                        successNum=successNum+1;
                    }else{
                        failureNum=failureNum+1;
                    }
                }catch (Exception ex){

                    errorNum=errorNum+1;
                }
            }
        }else{
            resultMap.put("nolevel", true);
        }
        if(successNum>0){
            resultMap.put("flag", true);
        }else{
            resultMap.put("flag", false);
        }
        if(successNum==0 && list.size()>0){
            resultMap.put("msg", "导入的数据操失败!");
        }
        resultMap.put("flag", flag);
        resultMap.put("success", successNum);
        resultMap.put("failure", failureNum);
        resultMap.put("repeatNum", repeatNum);
        resultMap.put("closeNum", closeNum);
        resultMap.put("errorNum", errorNum);
        resultMap.put("levelNum", levelNum);
        resultMap.put("errorDataList", errorDataList);
        return resultMap;
    }

    public List<Map> getSalesTargetInputGroupListBy(Map paramMap) throws Exception{
        List<Map> list=salesTargetInputMapper.getSalesTargetInputGroupListBy(paramMap);
        String grouptype=(String)paramMap.get("grouptype");
        if(list!=null && list.size()>0){
            for(Map itemMap:list){
                String tmpId=null;
                String tmpName=null;
                if(itemMap.containsKey("customersort")){
                    tmpId=(String)itemMap.get("customersort");
                    if(null!=tmpId&&!"".equals(tmpId.trim())){
                        CustomerSort customerSortInfo=getCustomerSortByID(tmpId.trim());
                        if(null!=customerSortInfo){
                            tmpName=customerSortInfo.getName();
                            itemMap.put("customersortname", customerSortInfo.getName());
                        }
                        if("customersort".equals(grouptype)){
                            itemMap.put("id",tmpId);
                            itemMap.put("name",tmpName);
                        }
                    }
                }
                if(itemMap.containsKey("brandid")){
                    tmpId=(String)itemMap.get("brandid");
                    if(null!=tmpId&&!"".equals(tmpId.trim())){
                        Brand brandInfo=getGoodsBrandByID(tmpId.trim());
                        if(null!=brandInfo){
                            tmpName=brandInfo.getName();
                            itemMap.put("brandname", brandInfo.getName());
                        }
                        if("brandid".equals(grouptype)){
                            itemMap.put("id",tmpId);
                            itemMap.put("name",tmpName);
                        }
                    }
                }
            }
        }
        return list;
    }

    public List<Map> getBrandCustomerSortListInSalesReport(Map paramMap) throws Exception{
        List<Map> list=salesTargetInputMapper.getBrandCustomersortListInSalesReport(paramMap);
        String grouptype=(String)paramMap.get("grouptype");
        List<Map> resultList=new ArrayList<Map>();
        if(list!=null && list.size()>0){
            for(Map itemMap:list){
                String tmpId=null;
                String tmpName=null;
                if(itemMap.containsKey("customersort")){
                    tmpId=(String)itemMap.get("customersort");
                    if(null!=tmpId&&!"".equals(tmpId.trim())){
                        CustomerSort customerSortInfo=getCustomerSortByID(tmpId.trim());
                        if(null!=customerSortInfo){
                            tmpName=customerSortInfo.getName();
                            itemMap.put("customersortname", customerSortInfo.getName());
                        }
                        if("customersort".equals(grouptype)){
                            itemMap.put("id",tmpId);
                            itemMap.put("name",tmpName);
                            resultList.add(itemMap);
                        }
                    }
                }
                if(itemMap.containsKey("brandid")){
                    tmpId=(String)itemMap.get("brandid");
                    if(null!=tmpId&&!"".equals(tmpId.trim())){
                        Brand brandInfo=getGoodsBrandByID(tmpId.trim());
                        if(null!=brandInfo){
                            tmpName=brandInfo.getName();
                            itemMap.put("brandname", brandInfo.getName());
                        }
                        if("brandid".equals(grouptype)){
                            itemMap.put("id",tmpId);
                            itemMap.put("name",tmpName);
                            resultList.add(itemMap);
                        }
                    }
                }
            }
        }
        return resultList;
    }
}
