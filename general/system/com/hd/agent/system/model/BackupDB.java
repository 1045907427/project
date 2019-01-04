package com.hd.agent.system.model;

import java.io.Serializable;

public class BackupDB implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 连接名称
     */
    private String connectname;

    /**
     * 数据库地址
     */
    private String dburl;

    /**
     * 数据库名称
     */
    private String dbname;

    /**
     * 用户名
     */
    private String dbusername;

    /**
     * 密码
     */
    private String dbpassword;

    /**
     * mysqldump路径
     */
    private String dumpurl;

    /**
     * 备份保存路径
     */
    private String fileurl;

    /**
     * 保存天数
     */
    private int savedaynum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 连接名称
     */
    public String getConnectname() {
        return connectname;
    }

    /**
     * @param connectname 
	 *            连接名称
     */
    public void setConnectname(String connectname) {
        this.connectname = connectname == null ? null : connectname.trim();
    }

    /**
     * @return 数据库地址
     */
    public String getDburl() {
        return dburl;
    }

    /**
     * @param dburl 
	 *            数据库地址
     */
    public void setDburl(String dburl) {
        this.dburl = dburl == null ? null : dburl.trim();
    }

    /**
     * @return 数据库名称
     */
    public String getDbname() {
        return dbname;
    }

    /**
     * @param dbname 
	 *            数据库名称
     */
    public void setDbname(String dbname) {
        this.dbname = dbname == null ? null : dbname.trim();
    }

    /**
     * @return 用户名
     */
    public String getDbusername() {
        return dbusername;
    }

    /**
     * @param dbusername 
	 *            用户名
     */
    public void setDbusername(String dbusername) {
        this.dbusername = dbusername == null ? null : dbusername.trim();
    }

    /**
     * @return 密码
     */
    public String getDbpassword() {
        return dbpassword;
    }

    /**
     * @param dbpassword 
	 *            密码
     */
    public void setDbpassword(String dbpassword) {
        this.dbpassword = dbpassword == null ? null : dbpassword.trim();
    }

    /**
     * @return mysqldump路径
     */
    public String getDumpurl() {
        return dumpurl;
    }

    /**
     * @param dumpurl 
	 *            mysqldump路径
     */
    public void setDumpurl(String dumpurl) {
        this.dumpurl = dumpurl == null ? null : dumpurl.trim();
    }

    /**
     * @return 备份保存路径
     */
    public String getFileurl() {
        return fileurl;
    }

    /**
     * @param fileurl 
	 *            备份保存路径
     */
    public void setFileurl(String fileurl) {
        this.fileurl = fileurl == null ? null : fileurl.trim();
    }

    public int getSavedaynum() {
        return savedaynum;
    }

    public void setSavedaynum(int savedaynum) {
        this.savedaynum = savedaynum;
    }

    private String updatepath;

    public String getUpdatepath() {
        return updatepath;
    }

    public void setUpdatepath(String updatepath) {
        this.updatepath = updatepath;
    }
}