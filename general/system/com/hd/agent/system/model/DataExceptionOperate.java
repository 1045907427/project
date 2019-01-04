package com.hd.agent.system.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 数据异常规则对应操作
 * @author chenwei
 */
public class DataExceptionOperate implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 数据异常规则编号
     */
    private String dataexceptionid;
    /**
     * 对应菜单名称
     */
    private String menu;

    /**
     * 对应菜单下的按钮
     */
    private String button;

    /**
     * 该功能对应的url地址
     */
    private String url;

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
     * @return 主键
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return 对应菜单名称
     */
    public String getMenu() {
        return menu;
    }

    /**
     * @param menu 
	 *            对应菜单名称
     */
    public void setMenu(String menu) {
        this.menu = menu == null ? null : menu.trim();
    }

    /**
     * @return 对应菜单下的按钮
     */
    public String getButton() {
        return button;
    }

    /**
     * @param button 
	 *            对应菜单下的按钮
     */
    public void setButton(String button) {
        this.button = button == null ? null : button.trim();
    }

    /**
     * @return 该功能对应的url地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url 
	 *            该功能对应的url地址
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
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

	public String getDataexceptionid() {
		return dataexceptionid;
	}

	public void setDataexceptionid(String dataexceptionid) {
		this.dataexceptionid = dataexceptionid;
	}
    
}