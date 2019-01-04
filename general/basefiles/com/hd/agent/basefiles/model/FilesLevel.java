/**
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-18 chenwei 创建版本
 */
package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
/**
 * 基础档案数据级次
 * @author chenwei
 */
public class FilesLevel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 表名
     */
    private String tablename;

    /**
     * 长度
     */
    private Integer len;

    /**
     * 级次
     */
    private Integer level;

    /**
     * 添加人用户编号
     */
    private String adduserid;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 修改人用户编号
     */
    private String modifyuserid;

    /**
     * 修改时间
     */
    private Date modifytime;
    /**
     * 是否能修改1不能
     */
    private String flag;
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
     * @return 长度
     */
    public Integer getLen() {
        return len;
    }

    /**
     * @param len 
	 *            长度
     */
    public void setLen(Integer len) {
        this.len = len;
    }

    /**
     * @return 级次
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * @param level 
	 *            级次
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * @return 添加人用户编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            添加人用户编号
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
     * @return 修改人用户编号
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid 
	 *            修改人用户编号
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
    
}