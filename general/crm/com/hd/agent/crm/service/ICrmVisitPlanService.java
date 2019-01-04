package com.hd.agent.crm.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.crm.model.CrmVisitPlan;
import com.hd.agent.crm.model.CrmVisitPlanDetail;

import java.util.List;
import java.util.Map;

public interface ICrmVisitPlanService {
    /**
     * 获取列表页数据
     * @param pageMap
     * @param type
     * @return
     * @throws Exception
     */
    public PageData getCrmData(PageMap pageMap,String type) throws Exception;
    /**
     * 获取业务员对应客户
     * @param pageMap
     * @return
     * @throws Exception
     */
    public PageData getCrmWay(PageMap pageMap) throws Exception ;

    /**
     * 新增时显示业务员对应的所有客户
     * @param visitPlan
     * @param planDetailList
     * @return
     * @throws Exception
     */

    public Map addCrmWay(CrmVisitPlan  visitPlan, List<CrmVisitPlanDetail> planDetailList) throws Exception;

    /**
     * 修改时显示业务员对应的所有客户并勾选已选中的客户
     * @param visitPlan
     * @param planDetailList
     * @return
     * @throws Exception
     */

    public Map editCrmWay(CrmVisitPlan  visitPlan, List<CrmVisitPlanDetail> planDetailList) throws Exception;

    /**
     * 根据业务员编码查看其客户路线详情
     * @param personid
     * @return
     * @throws Exception
     */
    public CrmVisitPlan viewCrmWay(String personid) throws Exception ;

    /**
     * 删除一个计划单
     * @param id
     * @return
     * @throws Exception
     */
    public boolean deletePlanById(String id) throws  Exception ;

    /**
     * 根据业务员编码和时间，获取该天的路线
     * @param pid
     * @param weekday
     * @return
     * @throws Exception
     */
    public Map getCustomerId(String pid,String weekday) throws Exception;
    /**
     * 启用计划单
     * @return
     * @throws Exception
     */
    public boolean enableCrmPlan(String id) throws  Exception;
    /**
     * 禁用计划单
     * @return
     * @throws Exception
     */
    public boolean disableCrmPlan(String id) throws  Exception;

    /**
     * 获取当前用户的拜访计划信息
     * @param synctime      同步时间
     * @return
     * @throws Exception
     */
    public Map getCrmVisitPlanBySysUser(String synctime) throws Exception;

    /**
     * 验证该编码的业务员是否存在
     * @param id
     * @return
     * @throws Exception
     */
    public Map validatePersonid(String id) throws Exception;
}
