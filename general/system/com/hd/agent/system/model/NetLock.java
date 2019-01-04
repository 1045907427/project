package com.hd.agent.system.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
/**
 * 网络互斥
 * @author chenwei
 */
public class NetLock implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 表名
     */
    private String tablename;
    /**
     * 业务表名称
     */
    private String name;
    /**
     * 加锁业务数据编号
     */
    private String lockid;

    /**
     * 加锁时间
     */
    private Date locktime;

    /**
     * 加锁人用户编号
     */
    private String lockuserid;

    /**
     * 加锁人姓名
     */
    private String lockname;

    /**
     * 加锁人部门编号
     */
    private String lockdeptid;

    /**
     * 加锁人部门名称
     */
    private String lockdeptname;

    /**
     * 锁定超时时长
     */
    private Integer lockmins;

    /**
     * 锁定是否超时1是0否(选择是后，超时将自动解除锁定)
     */
    private String isovertime;

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
     * @return 表名
     */
    public String getTablename() {
        return tablename;
    }

    /**
     * @param tablename 
	 *            表名
     */
    public void setTablename(String tablename) {
        this.tablename = tablename == null ? null : tablename.trim();
    }

    /**
     * @return 加锁业务数据编号
     */
    public String getLockid() {
        return lockid;
    }

    /**
     * @param lockid 
	 *            加锁业务数据编号
     */
    public void setLockid(String lockid) {
        this.lockid = lockid == null ? null : lockid.trim();
    }

    /**
     * @return 加锁时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getLocktime() {
        return locktime;
    }

    /**
     * @param locktime 
	 *            加锁时间
     */
    public void setLocktime(Date locktime) {
        this.locktime = locktime;
    }

    /**
     * @return 加锁人用户编号
     */
    public String getLockuserid() {
        return lockuserid;
    }

    /**
     * @param lockuserid 
	 *            加锁人用户编号
     */
    public void setLockuserid(String lockuserid) {
        this.lockuserid = lockuserid == null ? null : lockuserid.trim();
    }

    /**
     * @return 加锁人姓名
     */
    public String getLockname() {
        return lockname;
    }

    /**
     * @param lockname 
	 *            加锁人姓名
     */
    public void setLockname(String lockname) {
        this.lockname = lockname == null ? null : lockname.trim();
    }

    /**
     * @return 加锁人部门编号
     */
    public String getLockdeptid() {
        return lockdeptid;
    }

    /**
     * @param lockdeptid 
	 *            加锁人部门编号
     */
    public void setLockdeptid(String lockdeptid) {
        this.lockdeptid = lockdeptid == null ? null : lockdeptid.trim();
    }

    /**
     * @return 加锁人部门名称
     */
    public String getLockdeptname() {
        return lockdeptname;
    }

    /**
     * @param lockdeptname 
	 *            加锁人部门名称
     */
    public void setLockdeptname(String lockdeptname) {
        this.lockdeptname = lockdeptname == null ? null : lockdeptname.trim();
    }

    /**
     * @return 锁定超时时长
     */
    public Integer getLockmins() {
        return lockmins;
    }

    /**
     * @param lockmins 
	 *            锁定超时时长
     */
    public void setLockmins(Integer lockmins) {
        this.lockmins = lockmins;
    }

    /**
     * @return 锁定是否超时1是0否(选择是后，超时将自动解除锁定)
     */
    public String getIsovertime() {
        return isovertime;
    }

    /**
     * @param isovertime 
	 *            锁定是否超时1是0否(选择是后，超时将自动解除锁定)
     */
    public void setIsovertime(String isovertime) {
        this.isovertime = isovertime == null ? null : isovertime.trim();
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
}