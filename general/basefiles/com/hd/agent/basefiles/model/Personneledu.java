package com.hd.agent.basefiles.model;

import java.io.Serializable;

public class Personneledu implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 人员编号
     */
    private String personid;

    /**
     * 开始日期
     */
    private String startdate;

    /**
     * 结束日期
     */
    private String enddate;

    /**
     * 教育机构名称
     */
    private String educname;

    /**
     * 教育方式0未知1全日制2自考3成教
     */
    private String type;

    /**
     * 获得证书
     */
    private String certificate;

    /**
     * 备注
     */
    private String remark;

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
     * @return 人员编号
     */
    public String getPersonid() {
        return personid;
    }

    /**
     * @param personid 
	 *            人员编号
     */
    public void setPersonid(String personid) {
        this.personid = personid == null ? null : personid.trim();
    }

    /**
     * @return 开始日期
     */
    public String getStartdate() {
        return startdate;
    }

    /**
     * @param startdate 
	 *            开始日期
     */
    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    /**
     * @return 结束日期
     */
    public String getEnddate() {
        return enddate;
    }

    /**
     * @param enddate 
	 *            结束日期
     */
    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    /**
     * @return 教育机构名称
     */
    public String getEducname() {
        return educname;
    }

    /**
     * @param educname 
	 *            教育机构名称
     */
    public void setEducname(String educname) {
        this.educname = educname == null ? null : educname.trim();
    }

    /**
     * @return 教育方式0未知1全日制2自考3成教
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            教育方式0未知1全日制2自考3成教
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * @return 获得证书
     */
    public String getCertificate() {
        return certificate;
    }

    /**
     * @param certificate 
	 *            获得证书
     */
    public void setCertificate(String certificate) {
        this.certificate = certificate == null ? null : certificate.trim();
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
}