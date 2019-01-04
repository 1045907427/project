package com.hd.agent.crm.model;

import java.io.Serializable;

public class CrmVisitPlanDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String billid;

    /**
     * 人员编号(人员档案)
     */
    private String personid;

    /**
     * 星期一拜访客户名称
     */
    private String day1info;

    /**
     * 星期二
     */
    private String day2info;

    /**
     * 星期三
     */
    private String day3info;

    /**
     * 星期四
     */
    private String day4info;

    /**
     * 星期五
     */
    private String day5info;

    /**
     * 星期六
     */
    private String day6info;

    /**
     * 星期天
     */
    private String day7info;

    /**
     * 星期一拜访客户
     */
    private String day1;

    /**
     * 星期二
     */
    private String day2;

    /**
     * 星期三
     */
    private String day3;

    /**
     * 星期四
     */
    private String day4;

    /**
     * 星期五
     */
    private String day5;

    /**
     * 星期六
     */
    private String day6;

    /**
     * 星期天
     */
    private String day7;

    /**
     * @return 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 单据编号
     */
    public String getBillid() {
        return billid;
    }

    /**
     * @param billid
     *            单据编号
     */
    public void setBillid(String billid) {
        this.billid = billid == null ? null : billid.trim();
    }

    /**
     * @return 人员编号(人员档案)
     */
    public String getPersonid() {
        return personid;
    }

    /**
     * @param personid
     *            人员编号(人员档案)
     */
    public void setPersonid(String personid) {
        this.personid = personid == null ? null : personid.trim();
    }

    /**
     * @return 星期一拜访客户
     */
    public String getDay1() {
        return day1;
    }

    /**
     * @param day1
     *            星期一拜访客户
     */
    public void setDay1(String day1) {
        this.day1 = day1 == null ? null : day1.trim();
    }

    /**
     * @return 星期二
     */
    public String getDay2() {
        return day2;
    }

    /**
     * @param day2
     *            星期二
     */
    public void setDay2(String day2) {
        this.day2 = day2 == null ? null : day2.trim();
    }

    /**
     * @return 星期三
     */
    public String getDay3() {
        return day3;
    }

    /**
     * @param day3
     *            星期三
     */
    public void setDay3(String day3) {
        this.day3 = day3 == null ? null : day3.trim();
    }

    /**
     * @return 星期四
     */
    public String getDay4() {
        return day4;
    }

    /**
     * @param day4
     *            星期四
     */
    public void setDay4(String day4) {
        this.day4 = day4 == null ? null : day4.trim();
    }

    /**
     * @return 星期五
     */
    public String getDay5() {
        return day5;
    }

    /**
     * @param day5
     *            星期五
     */
    public void setDay5(String day5) {
        this.day5 = day5 == null ? null : day5.trim();
    }

    /**
     * @return 星期六
     */
    public String getDay6() {
        return day6;
    }

    /**
     * @param day6
     *            星期六
     */
    public void setDay6(String day6) {
        this.day6 = day6 == null ? null : day6.trim();
    }

    /**
     * @return 星期天
     */
    public String getDay7() {
        return day7;
    }

    /**
     * @param day7
     *            星期天
     */
    public void setDay7(String day7) {
        this.day7 = day7 == null ? null : day7.trim();
    }

    public String getDay1info() {
        return day1info;
    }

    public void setDay1info(String day1info) {
        this.day1info = day1info;
    }

    public String getDay2info() {
        return day2info;
    }

    public void setDay2info(String day2info) {
        this.day2info = day2info;
    }

    public String getDay3info() {
        return day3info;
    }

    public void setDay3info(String day3info) {
        this.day3info = day3info;
    }

    public String getDay4info() {
        return day4info;
    }

    public void setDay4info(String day4info) {
        this.day4info = day4info;
    }

    public String getDay5info() {
        return day5info;
    }

    public void setDay5info(String day5info) {
        this.day5info = day5info;
    }

    public String getDay6info() {
        return day6info;
    }

    public void setDay6info(String day6info) {
        this.day6info = day6info;
    }

    public String getDay7info() {
        return day7info;
    }

    public void setDay7info(String day7info) {
        this.day7info = day7info;
    }
}