package com.hd.agent.phone.model;

import java.io.Serializable;
import java.util.Date;

public class RouteDistance implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 用户编号
     */
    private String userid;
    
    private String username;
    
    private String distancedesc;
    
    /**
     * 行程数
     */
    private Integer distance;

    /**
     * 日期
     */
    private String adddate;

    /**
     * 添加时间
     */
    private Date addtime;

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

    /**
     * @param userid 
	 *            用户编号
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDistancedesc() {
		return distancedesc;
	}

	public void setDistancedesc(String distancedesc) {
		this.distancedesc = distancedesc;
	}

	/**
     * @return 行程数
     */
    public Integer getDistance() {
        return distance;
    }

    /**
     * @param distance 
	 *            行程数
     */
    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    /**
     * @return 日期
     */
    public String getAdddate() {
        return adddate;
    }

    /**
     * @param adddate 
	 *            日期
     */
    public void setAdddate(String adddate) {
        this.adddate = adddate;
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
}