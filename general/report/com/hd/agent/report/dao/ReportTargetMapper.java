package com.hd.agent.report.dao;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageMap;
import org.apache.ibatis.annotations.Param;

import com.hd.agent.report.model.ReportTarget;

/**
 * 各类报表考试目标dao
 * @author chenwei
 */
public interface ReportTargetMapper {
    /**
     * 添加报表考核目标
     * @param reportTarget
     * @return
     * @author chenwei 
     * @date 2014年12月5日
     */
	public int addReportTarget(ReportTarget reportTarget);
	/**
	 * 关闭历史相关考核目标
	 * @param reportTarget
	 * @return
	 * @author chenwei 
	 * @date 2014年12月5日
	 */
	public int updateReportTargetState(ReportTarget reportTarget);
	/**
	 * 获取相同考核目标数量
	 * @param reportTarget
	 * @return
	 * @author chenwei 
	 * @date 2014年12月5日
	 */
	public int getReportTargetCount(ReportTarget reportTarget);
	/**
	 * 根据年月获取 考核指标
	 * @param billtype
	 * @param busid
	 * @param year
	 * @param month
	 * @return
	 * @author chenwei 
	 * @date 2014年12月5日
	 */
	public ReportTarget getReportTargetByYearAndMonth(@Param("billtype")String billtype,@Param("busid")String busid,@Param("year")String year,@Param("month")String month);

	/**
	 * 根据参数获取各类报表考核目标信息
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 23, 2015
	 */
	public ReportTarget getReportTargetInfo(Map map);

	/**
	 * 根据条件获取数据合计（品牌业务员销售回笼考核目标）
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 23, 2015
	 */
	public ReportTarget getReportTargetSumCaseSalesWithdrawnAssess(Map map);

    /**
     * 统计指定月份所有的销售目标，回笼目标等数据
     * @param pageMap
     * @return
     */
    public Map reportTargetInfoSum(PageMap pageMap);

    /**
     * 根据条件获取符合的各类报表考核目标信息
     * @param map
     * @return
     * @author panxiaoxiao
     * @date Jan 23, 2015
     */
    public List getReportTargetInfoBy(Map map);

    /**
     * 根据参数获取符合条件的目标合计
     * @param map
     * @return
     * @author lin_xx
     * @date June 27,2016
     */
    public Map getReportTargetSum(Map map);


}