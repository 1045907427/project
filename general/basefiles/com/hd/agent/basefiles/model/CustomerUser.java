package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.util.Date;

public class CustomerUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 手机号码(登录账号)
     */
    private String mobilephone;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 关联客户编号
     */
    private String customerid;

    /**
     * 关联客户名称
     */
    private String customername;
    /**
     * 备注
     */
    private String remark;

    /**
     * 制单时间
     */
    private Date addtime;


    /**
     * 微信服务号id
     */
    private String wechatserid;
    /**
     * 状态
     */
    private String state;
    /**
     * 状态名称
     */
    private String stateName;

    /**
     * @return 编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 手机号码(登录账号)
     */
    public String getMobilephone() {
        return mobilephone;
    }

    /**
     * @param mobilephone 
	 *            手机号码(登录账号)
     */
    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone == null ? null : mobilephone.trim();
    }

    /**
     * @return 登录密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password 
	 *            登录密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * @return 关联客户编号
     */
    public String getCustomerid() {
        return customerid;
    }

    /**
     * @param customerid 
	 *            关联客户编号
     */
    public void setCustomerid(String customerid) {
        this.customerid = customerid == null ? null : customerid.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getWechatserid() {
        return wechatserid;
    }

    public void setWechatserid(String wechatserid) {
        this.wechatserid = wechatserid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}