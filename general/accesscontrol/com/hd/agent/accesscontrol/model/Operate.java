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
 * 功能权限实体类
 * @author chenwei
 */
public class Operate implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 操作id
     */
    private String operateid;

    /**
     * 菜单(操作)名称
     */
    private String operatename;

    /**
     * 菜单(操作)描述
     */
    private String description;

    /**
     * URL地址
     */
    private String url;
    /**
     * 数据权限表名
     */
    private String tablename;
    /**
     * 0菜单1操作
     */
    private String type;

    /**
     * 状态1有效0无效
     */
    private String state;

    /**
     * 父节点
     */
    private String pid;

    /**
     * 所属模块id
     */
    private String moduleid;

    /**
     * 图片地址
     */
    private String image;

    /**
     * 排序
     */
    private Integer seq;

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
     * 修改人用户编号
     */
    private String modifyuserid;
    /**
     * 导航
     */
    private String navigation;

    private String icon;


    /**
     * @return 操作id
     */
    public String getOperateid() {
        return operateid;
    }

    /**
     * @param operateid 
	 *            操作id
     */
    public void setOperateid(String operateid) {
        this.operateid = operateid == null ? null : operateid.trim();
    }

    /**
     * @return 菜单(操作)名称
     */
    public String getOperatename() {
        return operatename;
    }

    /**
     * @param operatename 
	 *            菜单(操作)名称
     */
    public void setOperatename(String operatename) {
        this.operatename = operatename == null ? null : operatename.trim();
    }

    /**
     * @return 菜单(操作)描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description 
	 *            菜单(操作)描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * @return URL地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url 
	 *            URL地址
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * @return 0菜单1操作
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            0菜单1操作
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * @return 状态1有效0无效
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态1有效0无效
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * @return 父节点
     */
    public String getPid() {
        return pid;
    }

    /**
     * @param pid 
	 *            父节点
     */
    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    /**
     * @return 所属模块id
     */
    public String getModuleid() {
        return moduleid;
    }

    /**
     * @param moduleid 
	 *            所属模块id
     */
    public void setModuleid(String moduleid) {
        this.moduleid = moduleid == null ? null : moduleid.trim();
    }

    /**
     * @return 图片地址
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image 
	 *            图片地址
     */
    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    /**
     * @return 排序
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * @param seq 
	 *            排序
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}