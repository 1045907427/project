package com.hd.agent.crm.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.crm.model.CrmVisitPlan;
import com.hd.agent.crm.model.CrmVisitPlanDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CrmVisitPlanMapper {

    public int getCrmDataCount(PageMap pageMap);

    public CrmVisitPlan selectById(String id);
    /**
     * 获取所有的计划单
     * @return
     */
    public List<CrmVisitPlan> getCrmPlan();
    /**
     * 获取所有的计划单详情
     * @return
     */
    public List getCrmData(PageMap pageMap);

    public int getCrmCount(PageMap pageMap);

    public CrmVisitPlan getCrmByPid(String personid);

    public List<CrmVisitPlanDetail> getCrmPlanByID(@Param("id")String id);

    int deleteDetailByBillid(@Param("billid")String billid);

    public int auditCrmPlan(CrmVisitPlan model);
    /**
     * 删除计划单
     * @param personid
     * @return
     */
    int deletePlanByPersonid(String personid);
    /**
     * 删除计划单详细
     * @param personid
     * @return
     */
    int deleteDetailByPersonid(String personid);
    /**
     * 根据主键删除记录
     */
    int deleteByPlanId(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insertVisitPlan(CrmVisitPlan record);

    /**
     * 保存属性不为空的记录
     */
    int insertPlan(CrmVisitPlan record);
    /**
     * 根据主表主键查询记录
     */
    CrmVisitPlan selectByPlanId(String id);

    List<CrmVisitPlanDetail> selectByPersonId(String pid);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateBySelectdId(CrmVisitPlan record);

    /**
     * 根据主键更新记录
     */
    int updateByMap(CrmVisitPlan record);

    /**
     * 根据主键更新记录
     */
    int updateBySelectedMap(CrmVisitPlan record);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insertPlanDetail(CrmVisitPlanDetail record);

    /**
     * 保存属性不为空的记录
     */
    int insertDetail(CrmVisitPlanDetail record);

    /**
     * 根据主键查询记录
     */
    CrmVisitPlanDetail selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateBySelectdDetailId(CrmVisitPlanDetail record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(CrmVisitPlanDetail record);


    /**
     * 根据人员档案编号获取该人员的客户拜访计划信息
     * @param personid
     * @return
     */
    public List getCrmVisitPlanByPersonid(@Param("personid")String personid);
    /**
     * 根据单据编号 获取客户拜访详细计划信息
     * @param billid
     * @return
     */
    public List getCrmVisitPlanDetailByBillid(@Param("billid")String billid);

    /**
     * 根据人员档案编号 获取该人员的拜访计划变更数量
     * @param personid
     * @param syncdate
     * @return
     */
    public int getCrmVisitPlanChangeCountBySyncdate(@Param("personid")String personid,@Param("syncdate")String syncdate);
}