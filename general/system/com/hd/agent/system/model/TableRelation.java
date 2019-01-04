/**
 * @(#)TableRelation.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-22 zhanghonghui 创建版本
 */
package com.hd.agent.system.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 
 * 数据表级联关系
 * @author zhanghonghui
 */
public class TableRelation implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1719095010110980993L;

	/**
     * 编号
     */
    private int id;

    /**
     * 主表名
     */
    private String maintablename;

    /**
     * 主表列名
     */
    private String maincolumnname;
    /**
     * 主表名称列名
     */
    private String maintitlecolname;

    /**
     * 表名
     */
    private String tablename;

    /**
     * 列名
     */
    private String columnname;

    /**
     * 表功能描述
     */
    private String tabledescription;

    /**
     * 系统预制或自建
     */
    private String createmethod;

    /**
     * 删除校验
     */
    private String deleteverify;

    /**
     * 是否级联替换
     */
    private String cascadechange;

    /**
     * 创建时间
     */
    private Date adddate;

    /**
     * 创建人编号
     */
    private String adduserid;

    /**
     * 修改日期
     */
    private Date modifydate;

    /**
     * 修改人
     */
    private String modifyuserid;

    /**
     * @return 编号
     */
    public int getId() {
        return id;
    }

    /**
     * @param id 
	 *            编号
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return 主表名
     */
    public String getMaintablename() {
        return maintablename;
    }

    /**
     * @param maintablename 
	 *            主表名
     */
    public void setMaintablename(String maintablename) {
        this.maintablename = maintablename == null ? null : maintablename.trim();
    }

    /**
     * @return 主表列名
     */
    public String getMaincolumnname() {
        return maincolumnname;
    }

    /**
     * @param maincolumnname 
	 *            主表列名
     */
    public void setMaincolumnname(String maincolumnname) {
        this.maincolumnname = maincolumnname == null ? null : maincolumnname.trim();
    }

    /** 
     * 主表名称列名
     * @return
     * @author zhanghonghui 
     * @date 2013-1-4
     */
    public String getMaintitlecolname() {
		return maintitlecolname;
	}

    /**
     * 主表名称列名
     * @param maintitlecolname
     * @author zhanghonghui 
     * @date 2013-1-4
     */
	public void setMaintitlecolname(String maintitlecolname) {
		this.maintitlecolname = maintitlecolname;
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
     * @return 列名
     */
    public String getColumnname() {
        return columnname;
    }

    /**
     * @param columnname 
	 *            列名
     */
    public void setColumnname(String columnname) {
        this.columnname = columnname == null ? null : columnname.trim();
    }

    /**
     * @return 表功能描述
     */
    public String getTabledescription() {
        return tabledescription;
    }

    /**
     * @param tabledescription 
	 *            表功能描述
     */
    public void setTabledescription(String tabledescription) {
        this.tabledescription = tabledescription == null ? null : tabledescription.trim();
    }

    /**
     * @return 创建类型：系统预制或自建
     */
    public String getCreatemethod() {
        return createmethod;
    }

    /**
     * @param createmethod 
	 *            创建类型：系统预制或自建
     */
    public void setCreatemethod(String createmethod) {
        this.createmethod = createmethod == null ? null : createmethod.trim();
    }

    /**
     * @return 删除校验
     */
    public String getDeleteverify() {
        return deleteverify;
    }

    /**
     * @param deleteverify 
	 *            删除校验
     */
    public void setDeleteverify(String deleteverify) {
        this.deleteverify = deleteverify == null ? null : deleteverify.trim();
    }

    /**
     * @return 是否级联替换
     */
    public String getCascadechange() {
        return cascadechange;
    }

    /**
     * @param cascadechange 
	 *            是否级联替换
     */
    public void setCascadechange(String cascadechange) {
        this.cascadechange = cascadechange == null ? null : cascadechange.trim();
    }

    /**
     * @return 创建时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAdddate() {
        return adddate;
    }

    /**
     * @param adddate 
	 *            创建时间
     */
    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

    /**
     * @return 创建人编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param createman 
	 *            创建人编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 修改日期
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getModifydate() {
        return modifydate;
    }

    /**
     * @param lastmodifydate 
	 *            修改日期
     */
    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

    /**
     * @return 修改人编号
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param lastmodifyman 
	 *            修改人编号
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
    }
}

