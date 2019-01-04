/**
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-01-12 chenwei 创建版本
 */
package com.hd.agent.common.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
/**
 * 通用查询
 * @author chenwei
 */
public class Query implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;
    /**
     * 查询相关编号
     */
    private String divid;
    /**
     * 通用查询名称
     */
    private String queryname;

    /**
     * 描述
     */
    private String description;

    /**
     * 查询类型1：公共0私有
     */
    private String type;

    /**
     * 添加人用户编号
     */
    private String adduserid;

    /**
     * 添加时间
     */
    private Date addtime;
    /**
     * 表名
     */
    private String tablename;
    /**
     * 查询规则
     */
    private String queryrule;
    /**
     * 排序规则
     */
    private String orderrule;
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
     * @return 通用查询名称
     */
    public String getQueryname() {
        return queryname;
    }

    /**
     * @param queryname 
	 *            通用查询名称
     */
    public void setQueryname(String queryname) {
        this.queryname = queryname == null ? null : queryname.trim();
    }

    /**
     * @return 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description 
	 *            描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * @return 查询类型1：公共0私有
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            查询类型1：公共0私有
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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
     * @return 查询规则
     */
    public String getQueryrule() {
        return queryrule;
    }

    /**
     * @param queryrule 
	 *            查询规则
     */
    public void setQueryrule(String queryrule) {
        this.queryrule = queryrule == null ? null : queryrule.trim();
    }

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getDivid() {
		return divid;
	}

	public void setDivid(String divid) {
		this.divid = divid;
	}

	public String getOrderrule() {
		return orderrule;
	}

	public void setOrderrule(String orderrule) {
		this.orderrule = orderrule;
	}
    
}