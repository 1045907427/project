package com.hd.agent.basefiles.model;

import java.io.Serializable;

public class Personnelworks implements Serializable {
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
     * 工作单位名称
     */
    private String workname;

    /**
     * 担任职务
     */
    private String post;

    /**
     * 主要工作业绩
     */
    private String mainachievement;

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
     * @return 工作单位名称
     */
    public String getWorkname() {
        return workname;
    }

    /**
     * @param workname 
	 *            工作单位名称
     */
    public void setWorkname(String workname) {
        this.workname = workname == null ? null : workname.trim();
    }

    /**
     * @return 担任职务
     */
    public String getPost() {
        return post;
    }

    /**
     * @param post 
	 *            担任职务
     */
    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    /**
     * @return 主要工作业绩
     */
    public String getMainachievement() {
        return mainachievement;
    }

    /**
     * @param mainachievement 
	 *            主要工作业绩
     */
    public void setMainachievement(String mainachievement) {
        this.mainachievement = mainachievement == null ? null : mainachievement.trim();
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