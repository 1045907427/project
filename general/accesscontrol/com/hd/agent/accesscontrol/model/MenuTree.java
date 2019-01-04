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

/**
 * 菜单树
 * @author chenwei
 */
public class MenuTree implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6172137521668191413L;
	/**
	 * 当前节点编号
	 */
	private String id;
	/**
	 * 父节点编号
	 */
	private String pId;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 描述名
	 */
	private String description;
	/**
	 * 是否展开
	 */
	private String open;
	/**
	 * 节点图片地址
	 */
	private String icon;
	/**
	 * 是否选中
	 */
	private String checked;
	
	private boolean chkDisabled;
	/**
	 * url地址
	 */
	private String urlStr;
    /**
     * 相关数据权限(数据库表名)
     */
    private String tablename;
	/**
	 * 状态
	 */
	private String state;
	
	/**
	 * 部门主管编号
	 */
	private String deptmanaguserid;
	/**
	 * 导航
	 */
	private String navigation;
	
	public String getDeptmanaguserid() {
		return deptmanaguserid;
	}
	public void setDeptmanaguserid(String deptmanaguserid) {
		this.deptmanaguserid = deptmanaguserid;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getUrlStr() {
		return urlStr;
	}
	public void setUrlStr(String urlStr) {
		this.urlStr = urlStr;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isChkDisabled() {
		return chkDisabled;
	}
	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }
	public String getNavigation() {
		return navigation;
	}
	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}
    
}

