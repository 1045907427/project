package com.hd.agent.system.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 参照窗口信息
 * @author chenwei
 */
public class ReferWindow implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 参照窗口编号
     */
    private String id;

    /**
     * 窗口名称
     */
    private String wname;

    /**
     * 状态4新增/3暂存/2保存/1启用/0禁用
     */
    private String state;
    /**
     * 数据结构normal普通tree树形
     */
    private String model;
    /**
     * 相关表
     */
    private String tables;

    /**
     * 备注
     */
    private String remark;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 添加人用户编号
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
     * sql语句
     */
    private String sqlstr;
    /**
     * 控件查看sql
     */
    private String viewsql;
    /**
     * @return 参照窗口编号
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            参照窗口编号
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return 窗口名称
     */
    public String getWname() {
        return wname;
    }

    /**
     * @param wname 
	 *            窗口名称
     */
    public void setWname(String wname) {
        this.wname = wname == null ? null : wname.trim();
    }

    /**
     * @return 状态4新增/3暂存/2保存/1启用/0禁用
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态4新增/3暂存/2保存/1启用/0禁用
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * @return 相关表
     */
    public String getTables() {
        return tables;
    }

    /**
     * @param tables 
	 *            相关表
     */
    public void setTables(String tables) {
        this.tables = tables == null ? null : tables.trim();
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

    /**
     * @return 添加时间
     */
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
     * @return sql语句
     */
    public String getSqlstr() {
        return sqlstr;
    }

    /**
     * @param sqlstr 
	 *            sql语句
     */
    public void setSqlstr(String sqlstr) {
        this.sqlstr = sqlstr == null ? null : sqlstr.trim();
    }

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getViewsql() {
		return viewsql;
	}

	public void setViewsql(String viewsql) {
		this.viewsql = viewsql;
	}
    
}