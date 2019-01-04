package com.hd.agent.agprint.model;

import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.util.Date;
/**
 * 打印任务
 * @author master
 *
 */
public class PrintJob implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -9133532033019379217L;

	/**
     * 编号
     */
    private String id;

    /**
     * 打印任务名
     */
    private String jobname;

    /**
     * 添加人编号
     */
    private String adduserid;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 添加人名称
     */
    private String addusername;

    /**
     * 状态：0申请，1打印，2次数更新成功
     */
    private String status;

    /**
     * 打印人的IP地址
     */
    private String ip;

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
     * 请求的参数
     */
    private String requestparam;

    /**
     * 申请打印单据号
     */
    private String orderidarr;
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
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return 打印任务名
     */
    public String getJobname() {
        return jobname;
    }

    /**
     * @param jobname 
	 *            打印任务名
     */
    public void setJobname(String jobname) {
        this.jobname = jobname == null ? null : jobname.trim();
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
     * @return 状态：0申请，1打印，2次数更新成功
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            状态：0申请，1打印，2次数更新成功
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * @return 打印人的IP地址
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip 
	 *            打印人的IP地址
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
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
     * @return 请求的参数
     */
    public String getRequestparam() {
        return requestparam;
    }

    /**
     * @param requestparam 
	 *            请求的参数
     */
    public void setRequestparam(String requestparam) {
        this.requestparam = requestparam == null ? null : requestparam.trim();
    }

    /**
     * @return 申请打印单据号
     */
    public String getOrderidarr() {
        return orderidarr;
    }

    /**
     * @param orderidarr 
	 *            申请打印单据号
     */
    public void setOrderidarr(String orderidarr) {
        this.orderidarr = orderidarr == null ? null : orderidarr.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}