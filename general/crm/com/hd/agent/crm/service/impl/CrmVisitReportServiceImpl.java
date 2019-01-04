/**
 * @(#)CrmVisitReportServiceImpl.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-8-25 limin 创建版本
 */
package com.hd.agent.crm.service.impl;

import com.hd.agent.basefiles.model.CustomerSort;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.model.SalesArea;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.crm.dao.CrmVisitReportMapper;
import com.hd.agent.crm.service.ICrmVisitReportService;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 客户拜访报表Service实现类
 *
 * Created by limin on 2016/8/25.
 */
public class CrmVisitReportServiceImpl extends BaseFilesServiceImpl implements ICrmVisitReportService {

    private CrmVisitReportMapper crmVisitReportMapper;

    public CrmVisitReportMapper getCrmVisitReportMapper() {
        return crmVisitReportMapper;
    }

    public void setCrmVisitReportMapper(CrmVisitReportMapper crmVisitReportMapper) {
        this.crmVisitReportMapper = crmVisitReportMapper;
    }

    @Override
    public PageData getVisitReportData(PageMap pageMap) throws Exception {

        String groupcols = (String) pageMap.getCondition().get("groupcols");
        pageMap.getCondition().put("groupcols", StringEscapeUtils.escapeSql(groupcols));

        String dataSql = getDataAccessRule("t_crm_visit_report", "t");
        pageMap.setDataSql(dataSql);

        List<Map> list = crmVisitReportMapper.getVisitReportList(pageMap);
        for(Map map : list) {

            String personid = (String) map.get("personid");
            if(StringUtils.isNotEmpty(personid)) {
                Personnel person = getPersonnelById(personid);
                if (person != null) {
                    map.put("personname", person.getName());

                    String deptid = person.getBelongdeptid();
                    if(StringUtils.isNotEmpty(deptid)) {

                        DepartMent dept = getDepartmentByDeptid(deptid);
                        if (dept != null) {

                            map.put("deptid", dept.getId());
                            map.put("deptname", dept.getName());
                        }
                    }
                }
            }

            String customersort = (String) map.get("customersort");
            if(StringUtils.isNotEmpty(customersort)) {

                CustomerSort customerSort = getCustomerSortByID(customersort);
                if (customerSort != null) {

                    map.put("customersortname", customerSort.getThisname());
                }
            }

            String salesarea = (String) map.get("salesarea");
            if(StringUtils.isNotEmpty(salesarea)) {

                SalesArea salesArea = getSalesareaByID(salesarea);
                if (salesArea != null) {

                    map.put("salesareaname", salesArea.getThisname());
                }
            }
        }
        int count = crmVisitReportMapper.getVisitReportCount(pageMap);

        PageData data = new PageData(count, list, pageMap);

        Map sum = crmVisitReportMapper.getVisitReportSumData(pageMap);
        Iterator<String> iterator = sum.keySet().iterator();
        for(; iterator.hasNext();) {

            String key = iterator.next();
            Object object = sum.get(key);
            if(object instanceof BigDecimal) {

                String strVal = object.toString();
                sum.put(key, Long.parseLong(strVal));
            }
        }

        String firstGroupCol = groupcols.split(",")[0];
        if("personid".equals(firstGroupCol)) {
            sum.put("personname", "合计");
        } else if("customersort".equals(firstGroupCol)) {
            sum.put("customersortname", "合计");
        } else if("salesarea".equals(firstGroupCol)) {
            sum.put("salesareaname", "合计");
        }

        List footer = new ArrayList();
        footer.add(sum);
        data.setFooter(footer);

        return data;
    }

    /**
     * 获取业务员拜访客户汇总
     * @param pageMap
     * @return
     * @throws Exception
     */
    public PageData getPersonReportData(PageMap pageMap) throws Exception {
        String groupcols = (String) pageMap.getCondition().get("groupcols");
        pageMap.getCondition().put("groupcols", StringEscapeUtils.escapeSql(groupcols));
        String dataSql = getDataAccessRule("t_crm_visit_report", "r");
        pageMap.setDataSql(dataSql);
        List<Map> list = crmVisitReportMapper.getPersonReportList(pageMap);
        for(Map map : list) {
            String personid = (String) map.get("personid");
            if(StringUtils.isNotEmpty(personid)) {
                Personnel person = getPersonnelById(personid);
                if (person != null) {
                    map.put("personname", person.getName());
                    String deptid = person.getBelongdeptid();
                    if(StringUtils.isNotEmpty(deptid)) {
                        DepartMent dept = getDepartmentByDeptid(deptid);
                        if (dept != null) {
                            map.put("deptid", dept.getId());
                            map.put("deptname", dept.getName());
                        }
                    }
                }
            }
        }
        int count = crmVisitReportMapper.getPersonReportCount(pageMap);
        return new PageData(count, list, pageMap);
    }
}
