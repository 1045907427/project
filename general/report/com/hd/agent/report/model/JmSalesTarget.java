package com.hd.agent.report.model;

import java.math.BigDecimal;

/**
 * Created by luoq on 2018/3/19.
 */
public class JmSalesTarget {
    private int id;

    /**
     * 人员编号
     */
    private String personnelid;

    /**
     * 目标日期(年月)
     */
    private String targetdate;

    /**
     * 任务量
     */
    private BigDecimal targetamount;

    /**
     * 工作天数
     */
    private BigDecimal workday;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPersonnelid() {
        return personnelid;
    }

    public void setPersonnelid(String personnelid) {
        this.personnelid = personnelid;
    }

    public String getTargetdate() {
        return targetdate;
    }

    public void setTargetdate(String targetdate) {
        this.targetdate = targetdate;
    }

    public BigDecimal getTargetamount() {
        return targetamount;
    }

    public void setTargetamount(BigDecimal targetamount) {
        this.targetamount = targetamount;
    }

    public BigDecimal getWorkday() {
        return workday;
    }

    public void setWorkday(BigDecimal workday) {
        this.workday = workday;
    }
}
