package com.hd.agent.phone.model;

import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.util.Date;

public class Location implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 用户编号
     */
    private String userid;
    
    private String personnelid;
    /**
     * 人员属性
     */
    private String employetype;
    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 纬度
     */
    private String x;

    /**
     * 经度
     */
    private String y;

    /**
     * 备注
     */
    private String remark;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * @return 编号
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            编号
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 用户编号
     */
    public String getUserid() {
        return userid;
    }

    public String getPersonnelid() {
		return personnelid;
	}

	public void setPersonnelid(String personnelid) {
		this.personnelid = personnelid;
	}

	/**
     * @param userid 
	 *            用户编号
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * @return 用户账号
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username 
	 *            用户账号
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return 用户姓名
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            用户姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 纬度
     */
    public String getX() {
        return x;
    }

    /**
     * @param x 
	 *            纬度
     */
    public void setX(String x) {
        this.x = x;
    }

    /**
     * @return 经度
     */
    public String getY() {
        return y;
    }

    /**
     * @param y 
	 *            经度
     */
    public void setY(String y) {
        this.y = y;
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
        this.remark = remark;
    }

    /**
     * @return 更新时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * @param updatetime 
	 *            更新时间
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
    
    public String getLatAndLot(){
    	return this.x+","+this.y;
    }

    public String getEmployetype() {
        return employetype;
    }

    public void setEmployetype(String employetype) {
        this.employetype = employetype;
    }
}