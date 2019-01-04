package com.hd.agent.activiti.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 自定义表单信息
 * @author zhengziyong
 *
 */
public class Form implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    private String id;

    /**
     * 唯一标识
     */
    private String unkey;

    /**
     * 名称
     */
    private String name;

    /**
     * 分类
     */
    private String type;

    /**
     * 说明
     */
    private String intro;

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
     * 详细
     */
    private byte[] detail;
    
    private FormType formType;

    /**
     * 项目数
     */
    private String fieldnum;

    /**
     * form设计模板(form设计时用)
     */
    private byte[] template;

    /**
     * 手机表单html代码
     */
    private byte[] phonehtml;

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
     * @return 唯一标识
     */
    public String getUnkey() {
        return unkey;
    }

    /**
     * @param unkey 
	 *            唯一标识
     */
    public void setUnkey(String unkey) {
        this.unkey = unkey;
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
     * @return 分类
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            分类
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return 说明
     */
    public String getIntro() {
        return intro;
    }

    /**
     * @param intro 
	 *            说明
     */
    public void setIntro(String intro) {
        this.intro = intro;
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
    @JSON(format="yyyy-MM-dd HH:mm")
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
        this.modifyuserid = modifyuserid;
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
        this.modifyusername = modifyusername;
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

    /**
     * @return 详细
     */
    public byte[] getDetail() {
        return detail;
    }

    /**
     * @param detail 
	 *            详细
     */
    public void setDetail(byte[] detail) {
        this.detail = detail;
    }

	public FormType getFormType() {
		return formType;
	}

	public void setFormType(FormType formType) {
		this.formType = formType;
	}

    public String getFieldnum() {
        return fieldnum;
    }

    public void setFieldnum(String fieldnum) {
        this.fieldnum = fieldnum;
    }

    public byte[] getTemplate() {
        return template;
    }

    public void setTemplate(byte[] template) {
        this.template = template;
    }

    public byte[] getPhonehtml() {
        return phonehtml;
    }

    public void setPhonehtml(byte[] phonehtml) {
        this.phonehtml = phonehtml;
    }
}