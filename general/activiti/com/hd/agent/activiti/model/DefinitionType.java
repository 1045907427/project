package com.hd.agent.activiti.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程分类
 * @author zhengziyong
 *
 */
public class DefinitionType implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    private String id;

    /**
     * 父节点
     */
    private String pid;

    /**
     * 名称
     */
    private String name;

    /**
     * 唯一标识
     */
    private String unkey;

    /**
     * 添加人ID
     */
    private String adduserid;

    /**
     * 添加人姓名
     */
    private String addusername;

    /**
     * 添加人部门编号
     */
    private String adddeptid;

    /**
     * 添加人部门名称
     */
    private String adddeptname;

    /**
     * 添加时间
     */
    private Date adddate;

    /**
     * @return 主键编号
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键编号
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 父节点
     */
    public String getPid() {
        return pid;
    }

    /**
     * @param pid 
	 *            父节点
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 唯一标识
     */
    public String getUnkey() {
        return unkey;
    }

    /**
     * @param key 
	 *            唯一标识
     */
    public void setUnkey(String unkey) {
        this.unkey = unkey;
    }

    /**
     * @return 添加人ID
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            添加人ID
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid;
    }

    /**
     * @return 添加人姓名
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            添加人姓名
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername;
    }

    /**
     * @return 添加人部门编号
     */
    public String getAdddeptid() {
        return adddeptid;
    }

    /**
     * @param adddeptid 
	 *            添加人部门编号
     */
    public void setAdddeptid(String adddeptid) {
        this.adddeptid = adddeptid;
    }

    /**
     * @return 添加人部门名称
     */
    public String getAdddeptname() {
        return adddeptname;
    }

    /**
     * @param adddeptname 
	 *            添加人部门名称
     */
    public void setAdddeptname(String adddeptname) {
        this.adddeptname = adddeptname;
    }

    /**
     * @return 添加时间
     */
    public Date getAdddate() {
        return adddate;
    }

    /**
     * @param adddate 
	 *            添加时间
     */
    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }
}