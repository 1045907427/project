package com.hd.agent.agprint.model;

import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.util.Date;

/**
 * 打印任务回调处理
 * @author master
 *
 */
public class PrintJobCallHandle implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4704970875156276358L;

    /**
     * 编号
     */
    private String id;

    /**
     * 任务编号
     */
    private String jobid;

    /**
     * 打印的单据号
     */
    private String printorderid;

    /**
     * 打印所属单据
     */
    private String printordername;

    /**
     * 来源单据号
     */
    private String sourceorderid;

    /**
     * 来源所属单据名称
     */
    private String sourceordername;

    /**
     * 调用的类名
     */
    private String classname;

    /**
     * 调用的方法名
     */
    private String methodname;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 添加人编号
     */
    private String adduserid;

    /**
     * 添加人名称
     */
    private String addusername;

    /**
     * 状态：1处理，0未处理
     */
    private String status;

    /**
     * 打印页面数
     */
    private Integer pages;

    /**
     * 修改者编号
     */
    private String modifyuserid;

    /**
     * 修改者姓名
     */
    private String modifyusername;

    /**
     * 修改时间
     */
    private Date modifytime;

    /**
     * 类型：1次数更新
     */
    private String type;

    /**
     * 方法参数值，字符串型的json
     */
    private String methodparam;

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
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return 任务编号
     */
    public String getJobid() {
        return jobid;
    }

    /**
     * @param jobid 
	 *            任务编号
     */
    public void setJobid(String jobid) {
        this.jobid = jobid == null ? null : jobid.trim();
    }

    /**
     * @return 打印的单据号
     */
    public String getPrintorderid() {
        return printorderid;
    }

    /**
     * @param printorderid 
	 *            打印的单据号
     */
    public void setPrintorderid(String printorderid) {
        this.printorderid = printorderid == null ? null : printorderid.trim();
    }

    /**
     * @return 打印所属单据
     */
    public String getPrintordername() {
        return printordername;
    }

    /**
     * @param printordername 
	 *            打印所属单据
     */
    public void setPrintordername(String printordername) {
        this.printordername = printordername == null ? null : printordername.trim();
    }

    /**
     * @return 来源单据号
     */
    public String getSourceorderid() {
        return sourceorderid;
    }

    /**
     * @param sourceorderid 
	 *            来源单据号
     */
    public void setSourceorderid(String sourceorderid) {
        this.sourceorderid = sourceorderid == null ? null : sourceorderid.trim();
    }

    /**
     * @return 来源所属单据名称
     */
    public String getSourceordername() {
        return sourceordername;
    }

    /**
     * @param sourceordername 
	 *            来源所属单据名称
     */
    public void setSourceordername(String sourceordername) {
        this.sourceordername = sourceordername == null ? null : sourceordername.trim();
    }

    /**
     * @return 调用的类名
     */
    public String getClassname() {
        return classname;
    }

    /**
     * @param classname 
	 *            调用的类名
     */
    public void setClassname(String classname) {
        this.classname = classname == null ? null : classname.trim();
    }

    /**
     * @return 调用的方法名
     */
    public String getMethodname() {
        return methodname;
    }

    /**
     * @param methodname 
	 *            调用的方法名
     */
    public void setMethodname(String methodname) {
        this.methodname = methodname == null ? null : methodname.trim();
    }

    /**
     * @return 添加时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
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
     * @return 添加人名称
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            添加人名称
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
    }

    /**
     * @return 状态：1处理，0未处理
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            状态：1处理，0未处理
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * @return 打印页面数
     */
    public Integer getPages() {
        return pages;
    }

    /**
     * @param pages 
	 *            打印页面数
     */
    public void setPages(Integer pages) {
        this.pages = pages;
    }

    /**
     * @return 修改者编号
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid 
	 *            修改者编号
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
    }

    /**
     * @return 修改者姓名
     */
    public String getModifyusername() {
        return modifyusername;
    }

    /**
     * @param modifyusername 
	 *            修改者姓名
     */
    public void setModifyusername(String modifyusername) {
        this.modifyusername = modifyusername == null ? null : modifyusername.trim();
    }

    /**
     * @return 修改时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
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
     * @return 类型：1次数更新
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            类型：1次数更新
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * @return 方法参数值，字符串型的json
     */
    public String getMethodparam() {
        return methodparam;
    }

    /**
     * @param methodparam 
	 *            方法参数值，字符串型的json
     */
    public void setMethodparam(String methodparam) {
        this.methodparam = methodparam == null ? null : methodparam.trim();
    }
}