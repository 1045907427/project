package com.hd.agent.basefiles.model;

import java.io.Serializable;

/**
 * 联系人对应分类
 * @author zhengziyong
 *
 */
public class ContacterAndSort implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 联系人编码
     */
    private String linkmanid;

    /**
     * 联系人分类编码
     */
    private String linkmansort;

    /**
     * 联系人分类名称
     */
    private String linkmansortname;

    /**
     * 是否默认分类1是0否
     */
    private String isdefault;

    /**
     * 备注
     */
    private String remark;

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
     * @return 联系人编码
     */
    public String getLinkmanid() {
        return linkmanid;
    }

    /**
     * @param linkmanid 
	 *            联系人编码
     */
    public void setLinkmanid(String linkmanid) {
        this.linkmanid = linkmanid;
    }

    /**
     * @return 联系人分类编码
     */
    public String getLinkmansort() {
        return linkmansort;
    }

    /**
     * @param linkmansort 
	 *            联系人分类编码
     */
    public void setLinkmansort(String linkmansort) {
        this.linkmansort = linkmansort;
    }

    /**
     * @return 联系人分类名称
     */
    public String getLinkmansortname() {
        return linkmansortname;
    }

    /**
     * @param linkmansortname 
	 *            联系人分类名称
     */
    public void setLinkmansortname(String linkmansortname) {
        this.linkmansortname = linkmansortname;
    }

    /**
     * @return 是否默认分类1是0否
     */
    public String getIsdefault() {
        return isdefault;
    }

    /**
     * @param isdefault 
	 *            是否默认分类1是0否
     */
    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
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
}