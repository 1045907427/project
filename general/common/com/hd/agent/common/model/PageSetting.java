/**
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-28 chenwei 创建版本
 */
package com.hd.agent.common.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
/**
 * 页面个性化
 * @author chenwei
 */
public class PageSetting implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 用户编号
     */
    private String userid;

    /**
     * 所属datagrid的ID
     */
    private String grid;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 修改时间
     */
    private Date modifytime;
    /**
     * 冻结列
     */
    private String frozencol;

    /**
     * 普通列
     */
    private String commoncol;
    /**
     * 表名
     */
    private String tablename;
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
     * @return 用户编号
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid 
	 *            用户编号
     */
    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    /**
     * @return 所属datagrid的ID
     */
    public String getGrid() {
        return grid;
    }

    /**
     * @param grid 
	 *            所属datagrid的ID
     */
    public void setGrid(String grid) {
        this.grid = grid == null ? null : grid.trim();
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
     * @return 冻结列
     */
    public String getFrozencol() {
        return frozencol;
    }

    /**
     * @param frozencol 
	 *            冻结列
     */
    public void setFrozencol(String frozencol) {
        this.frozencol = frozencol == null ? null : frozencol.trim();
    }

    /**
     * @return 普通列
     */
    public String getCommoncol() {
        return commoncol;
    }

    /**
     * @param commoncol 
	 *            普通列
     */
    public void setCommoncol(String commoncol) {
        this.commoncol = commoncol == null ? null : commoncol.trim();
    }

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
    
}