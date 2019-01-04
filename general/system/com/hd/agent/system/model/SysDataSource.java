package com.hd.agent.system.model;

import java.io.Serializable;
import java.util.Date;

public class SysDataSource implements Serializable {

    private static final long serialVersionUID = 6127821489233091857L;
    /**
     * 编号
     */
    private String id;

    /**
     * 代码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态,1启用，其他0
     */
    private String state;

    private String jdbcdriver;

    private String jdbcurl;

    private String dbuser;

    private String dbpasswd;

    private String dbpasswdclear;

    /**
     * JDBC类型
     */
    private String jdbctype;


    /**
     * 添加用户编号
     */
    private String adduserid;

    /**
     * 添加用户名称
     */
    private String addusername;

    /**
     * 添加时间
     */
    private Date addtime;

    private String modifyuserid;

    /**
     * 修改用户名称
     */
    private String modifyusername;

    /**
     * 修改时间
     */
    private Date modifytime;

    /**
     * 启用人用户编号
     */
    private String openuserid;

    /**
     * 启用人姓名
     */
    private String openusername;

    /**
     * 启用时间
     */
    private Date opentime;

    /**
     * 禁用人用户编号
     */
    private String closeuserid;

    /**
     * 禁用人姓名
     */
    private String closeusername;

    /**
     * 禁用时间
     */
    private Date closetime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否修改密码
     */
    private String ismodifypwd;
    /**
     * 数据库名称
     */
    private String dbname;

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
     * @return 代码
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code 
	 *            代码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
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
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return 状态,1启用，其他0
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态,1启用，其他0
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * 数据库jdbc驱动
     * @return
     */
    public String getJdbcdriver() {
        return jdbcdriver;
    }

    /**
     * 数据库jdbc驱动
     * @param jdbcdriver
     */
    public void setJdbcdriver(String jdbcdriver) {
        this.jdbcdriver = jdbcdriver == null ? null : jdbcdriver.trim();
    }

    /**
     * 数据库连接
     * @return
     */
    public String getJdbcurl() {
        return jdbcurl;
    }

    /**
     * 数据库连接
     * @param jdbcurl
     */
    public void setJdbcurl(String jdbcurl) {
        this.jdbcurl = jdbcurl == null ? null : jdbcurl.trim();
    }

    /**
     * 数据库登录用户名
     * @return
     */
    public String getDbuser() {
        return dbuser;
    }

    /**
     * 数据库登录用户名
     * @param dbuser
     */
    public void setDbuser(String dbuser) {
        this.dbuser = dbuser == null ? null : dbuser.trim();
    }

    /**
     * 数据库密码（加密）
     * @return
     */
    public String getDbpasswd() {
        return dbpasswd;
    }

    /**
     * 数据库密码（加密）
     * @param dbpasswd
     */
    public void setDbpasswd(String dbpasswd) {
        this.dbpasswd = dbpasswd == null ? null : dbpasswd.trim();
    }

    /**
     * 数据库密码（明文）
     * @return
     */
    public String getDbpasswdclear() {
        return dbpasswdclear;
    }

    /**
     * 数据库密码（明文）
     * @param dbpasswdclear
     */
    public void setDbpasswdclear(String dbpasswdclear) {
        this.dbpasswdclear = dbpasswdclear == null ? null : dbpasswdclear.trim();
    }


    /**
     * @return JDBC类型
     */
    public String getJdbctype() {
        return jdbctype;
    }

    /**
     * @param jdbctype 
	 *            JDBC类型
     */
    public void setJdbctype(String jdbctype) {
        this.jdbctype = jdbctype == null ? null : jdbctype.trim();
    }

    public String getAdduserid() {
        return adduserid;
    }

    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    public String getAddusername() {
        return addusername;
    }

    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getModifyuserid() {
        return modifyuserid;
    }

    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
    }

    public String getModifyusername() {
        return modifyusername;
    }

    public void setModifyusername(String modifyusername) {
        this.modifyusername = modifyusername == null ? null : modifyusername.trim();
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public String getOpenuserid() {
        return openuserid;
    }

    public void setOpenuserid(String openuserid) {
        this.openuserid = openuserid == null ? null : openuserid.trim();
    }

    public String getOpenusername() {
        return openusername;
    }

    public void setOpenusername(String openusername) {
        this.openusername = openusername == null ? null : openusername.trim();
    }

    public Date getOpentime() {
        return opentime;
    }

    public void setOpentime(Date opentime) {
        this.opentime = opentime;
    }

    public String getCloseuserid() {
        return closeuserid;
    }

    public void setCloseuserid(String closeuserid) {
        this.closeuserid = closeuserid == null ? null : closeuserid.trim();
    }

    public String getCloseusername() {
        return closeusername;
    }

    public void setCloseusername(String closeusername) {
        this.closeusername = closeusername == null ? null : closeusername.trim();
    }

    public Date getClosetime() {
        return closetime;
    }

    public void setClosetime(Date closetime) {
        this.closetime = closetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIsmodifypwd() {
        return ismodifypwd;
    }

    public void setIsmodifypwd(String ismodifypwd) {
        this.ismodifypwd = ismodifypwd == null ? null : ismodifypwd.trim();
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }
}