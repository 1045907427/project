package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.util.Date;

public class CustomerLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户编号
     */
    private String customerid;

    /**
     * 客户坐标，如：120.132372,30.27981
     */
    private String location;

    /**
     * 地址，根据坐标逆解析的地址，如：浙江省杭州市西湖区
     */
    private String address;

    /**
     * 备注
     */
    private String remark;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 精度
     */
    private String accuracy;

    /**
     * @return 客户编号
     */
    public String getCustomerid() {
        return customerid;
    }

    /**
     * @param customerid 
	 *            客户编号
     */
    public void setCustomerid(String customerid) {
        this.customerid = customerid == null ? null : customerid.trim();
    }

    /**
     * @return 客户坐标，如：120.132372,30.27981
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location 
	 *            客户坐标，如：120.132372,30.27981
     */
    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    /**
     * @return 地址，根据坐标逆解析的地址，如：浙江省杭州市西湖区
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address 
	 *            地址，根据坐标逆解析的地址，如：浙江省杭州市西湖区
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark 
	 *            备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * @return 添加时间
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            添加时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }
}