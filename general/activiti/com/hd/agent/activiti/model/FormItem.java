package com.hd.agent.activiti.model;

import java.io.Serializable;
import java.util.Date;

public class FormItem implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    private Integer id;

    /**
     * form标识
     */
    private String unkey;

    /**
     * 项目编号
     */
    private String itemid;

    /**
     * 项目名称
     */
    private String itemname;

    /**
     * 项目分类 input、teatarea、select等
     */
    private String type;

    /**
     * 备注
     */
    private String remark;

    /**
     * 添加人编号
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
     * 修改人编号
     */
    private String modifyuserid;

    /**
     * 修改人姓名
     */
    private String modifyusername;

    /**
     * 修改时间
     */
    private Date modifytime;

    /**
     * @return 主键编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return form标识
     */
    public String getUnkey() {
        return unkey;
    }

    /**
     * @param unkey 
	 *            form标识
     */
    public void setUnkey(String unkey) {
        this.unkey = unkey == null ? null : unkey.trim();
    }

    /**
     * @return 项目编号
     */
    public String getItemid() {
        return itemid;
    }

    /**
     * @param itemid 
	 *            项目编号
     */
    public void setItemid(String itemid) {
        this.itemid = itemid == null ? null : itemid.trim();
    }

    /**
     * @return 项目名称
     */
    public String getItemname() {
        return itemname;
    }

    /**
     * @param itemname 
	 *            项目名称
     */
    public void setItemname(String itemname) {
        this.itemname = itemname == null ? null : itemname.trim();
    }

    /**
     * @return 项目分类 input、teatarea、select等
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            项目分类 input、teatarea、select等
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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
     * @return 添加人编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            添加人编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
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
        this.addusername = addusername == null ? null : addusername.trim();
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
        this.adddeptid = adddeptid == null ? null : adddeptid.trim();
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
        this.adddeptname = adddeptname == null ? null : adddeptname.trim();
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

    /**
     * @return 修改人编号
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid 
	 *            修改人编号
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
    }

    /**
     * @return 修改人姓名
     */
    public String getModifyusername() {
        return modifyusername;
    }

    /**
     * @param modifyusername 
	 *            修改人姓名
     */
    public void setModifyusername(String modifyusername) {
        this.modifyusername = modifyusername == null ? null : modifyusername.trim();
    }

    /**
     * @return 修改时间
     */
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * @param modifytime 
	 *            修改时间
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }
}