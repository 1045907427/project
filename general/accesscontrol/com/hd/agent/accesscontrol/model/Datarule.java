/**
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-20 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
/**
 * 数据权限资源规则
 * @author chenwei
 */
public class Datarule implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 数据id
     */
    private String dataid;

    /**
     * 数据资源名称
     */
    private String dataname;

    /**
     * 数据资源针对的表
     */
    private String tablename;
    /**
     * 类型1数据字典2参照窗口
     */
    private String type;
    /**
     * 状态1启用0停用
     */
    private String state;

    /**
     * 所属模块id
     */
    private String moduleid;
    /**
     * 数据权限针对范围1全局2用户
     */
    private String scope;
    /**
     * 用户编号
     */
    private String userid;
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
     * 数据访问规则
     */
    private String rule;
    /**
     * 备注
     */
    private String remark;
    /**
     * @return 数据id
     */
    public String getDataid() {
        return dataid;
    }

    /**
     * @param dataid 
	 *            数据id
     */
    public void setDataid(String dataid) {
        this.dataid = dataid == null ? null : dataid.trim();
    }

    /**
     * @return 数据资源名称
     */
    public String getDataname() {
        return dataname;
    }

    /**
     * @param dataname 
	 *            数据资源名称
     */
    public void setDataname(String dataname) {
        this.dataname = dataname == null ? null : dataname.trim();
    }

    /**
     * @return 数据资源针对的表
     */
    public String getTablename() {
        return tablename;
    }

    /**
     * @param tablename 
	 *            数据资源针对的表
     */
    public void setTablename(String tablename) {
        this.tablename = tablename == null ? null : tablename.trim();
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
     * @return 数据访问规则
     */
    public String getRule() {
        return rule;
    }

    /**
     * @param rule 
	 *            数据访问规则
     */
    public void setRule(String rule) {
        this.rule = rule == null ? null : rule.trim();
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    
}