/**
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-15 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
/**
 * 字段权限实体类
 * @author chenwei
 */
public class AccessColumn implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 字段权限编号
     */
    private String columnid;

    /**
     * 表名
     */
    private String tablename;

    /**
     * 表描述名
     */
    private String tabledescname;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 状态1启用0停用
     */
    private String state;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 添加人编号
     */
    private String adduserid;

    /**
     * 修改时间
     */
    private Date modifytime;

    /**
     * 修改人编号
     */
    private String modifyuserid;

    /**
     * 可以访问的字段列表
     */
    private String collist;
    /**
     * 可以访问的字段名称列表
     */
    private String collistname;
    /**
     * 可以编辑的字段列表
     */
    private String editcollist;
    /**
     * 可以编辑的字段名称列表
     */
    private String editcollistname;
    /**
     * @return 字段权限编号
     */
    public String getColumnid() {
        return columnid;
    }

    /**
     * @param columnid 
	 *            字段权限编号
     */
    public void setColumnid(String columnid) {
        this.columnid = columnid == null ? null : columnid.trim();
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
     * @return 表描述名
     */
    public String getTabledescname() {
        return tabledescname;
    }

    /**
     * @param tabledescname 
	 *            表描述名
     */
    public void setTabledescname(String tabledescname) {
        this.tabledescname = tabledescname == null ? null : tabledescname.trim();
    }

    /**
     * @return 描述信息
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description 
	 *            描述信息
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * @return 状态1启用0停用
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态1启用0停用
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
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
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
    }

    /**
     * @return 可以访问的字段列表
     */
    public String getCollist() {
        return collist;
    }

    /**
     * @param collist 
	 *            可以访问的字段列表
     */
    public void setCollist(String collist) {
        this.collist = collist == null ? null : collist.trim();
    }

	public String getCollistname() {
		return collistname;
	}

	public void setCollistname(String collistname) {
		this.collistname = collistname;
	}

	public String getEditcollist() {
		return editcollist;
	}

	public void setEditcollist(String editcollist) {
		this.editcollist = editcollist;
	}

	public String getEditcollistname() {
		return editcollistname;
	}

	public void setEditcollistname(String editcollistname) {
		this.editcollistname = editcollistname;
	}
    
    
}