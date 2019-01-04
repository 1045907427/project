/**
 * @(#)Tables.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-14 zhanghonghui 创建版本
 */
package com.hd.agent.system.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 
 * 数据字典-表管理
 * @author zhanghonghui
 */
public class TableInfo implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4390519318525934311L;

	/**
	 * 编号
	 */
	private String id;
	/**
     * 表名
     */
    private String tablename;/**
     * 描述
     */
    private String tabledescname;

    /**
     * 表类型
     */
    private String tabletype;

    /**
     * 使用历史库
     */
    private String usehistory;

    /**
     * 使用版本库
     */
    private String useversion;

    /**
     * 建立日期
     */
    private Date adddate;

    /**
     * 建立人
     */
    private String addsignname;

    /**
     * 创建者编号
     */
    private String adduserid;

    /**
     * 修改日期
     */
    private Date modifydate;

    /**
     * 修改人
     */
    private String modifysignname;

    /**
     * 修改人编号
     */
    private String modifyuserid;

    /**
     * 描述
     */
    private String remark;

    /**
     * 模块类型
     */
    private String moduletype;
    
    /**
     * 模块类型名称
     */
    private String moduletypename;

    /**
     * 状态，0停用，1启用
     */
    private String state;

    /**
     * 系统预制或自建,预制1，自建2
     */
    private String createmethod;
    
    /**
     * 数据来源,1人工添加，2自动导入，3人工导入，4其他
     */
    private String datasource;
    
    /**
     * 是否树形显示
     */
    private String usetreelist;
    /**
     * 树形显示列名
     */
    private String refertreecol;
    /**
     * 是否支持自动编号
     */
    private String useautoencoded;
    /**
     * 是否支持数据权限1是0否
     */
    private String isdatarule;
    /**
     * 旧表名-更新时表信息时使用,不与数据库字段对应
     */
    private String oldtablename;
    
    /**
     * @return 表名
     */
    public String getTablename() {
        return tablename;
    }

    /**
     * 
     * @return 编号
     * @author zhanghonghui 
     * @date 2012-12-25
     */
    public String getId() {
		return id;
	}

    /**
     * 编号
     * @param id
     * @author zhanghonghui 
     * @date 2012-12-25
     */
	public void setId(String id) {
		this.id = id;
	}

	/**
     * @param tablename 
	 *            表名
     */
    public void setTablename(String tablename) {
        this.tablename = tablename == null ? null : tablename.trim();
    }

    /**
     * @return 描述名
     */
    public String getTabledescname() {
        return tabledescname;
    }

    /**
     * @param tabledescname
	 *            描述名
     */
    public void setTabledescname(String tabledescname) {
        this.tabledescname = tabledescname == null ? null : tabledescname.trim();
    }

	/**
     * @return 表类型
     */
    public String getTabletype() {
        return tabletype;
    }

    /**
     * @param tabletype 
	 *            表类型
     */
    public void setTabletype(String tabletype) {
        this.tabletype = tabletype == null ? null : tabletype.trim();
    }

    /**
     * @return 使用历史库
     */
    public String getUsehistory() {
        return usehistory;
    }

    /**
     * @param usehistory 
	 *            使用历史库
     */
    public void setUsehistory(String usehistory) {
        this.usehistory = usehistory == null ? null : usehistory.trim();
    }

    /**
     * @return 使用版本库
     */
    public String getUseversion() {
        return useversion;
    }

    /**
     * @param useversion 
	 *            使用版本库
     */
    public void setUseversion(String useversion) {
        this.useversion = useversion == null ? null : useversion.trim();
    }
    /**
     * @return 建立日期
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAdddate() {
        return adddate;
    }

    /**
     * @param adddate 
	 *            建立日期
     */
    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

    /**
     * @return 建立人
     */
    public String getAddsignname() {
        return addsignname;
    }

    /**
     * @param addsignname 
	 *            建立人
     */
    public void setAddsignname(String addsignname) {
        this.addsignname = addsignname == null ? null : addsignname.trim();
    }

    /**
     * @return 创建者编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            创建者编号
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
     * @param modifydate 
	 *            修改日期
     */
    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

    /**
     * @return 修改人
     */
    public String getModifysignname() {
        return modifysignname;
    }

    /**
     * @param modifysignname 
	 *            修改人
     */
    public void setModifysignname(String modifysignname) {
        this.modifysignname = modifysignname == null ? null : modifysignname.trim();
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
     * 获取备注信息
     * @return
     * @author zhanghonghui 
     * @date 2012-12-22
     */
    public String getRemark() {
		return remark;
	}

    /**
     * 设置备注信息
     * @param remark
     * @author zhanghonghui 
     * @date 2012-12-22
     */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
     * @return 维护类型
     */
    public String getModuletype() {
        return moduletype;
    }

    /**
     * @param moduletype 
	 *            维护类型
     */
    public void setModuletype(String moduletype) {
        this.moduletype = moduletype == null ? null : moduletype.trim();
    }

    public String getModuletypename() {
		return moduletypename;
	}

	public void setModuletypename(String moduletypename) {
        this.moduletypename = moduletypename == null ? null : moduletypename.trim();
	}

	/**
     * @return 状态，0停用，1启用
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态，0停用，1启用
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * @return 创建方式：系统预制1，自建2
     */
    public String getCreatemethod() {
        return createmethod;
    }

    /**
     * @param createmethod 
	 *            创建方式：系统预制1，自建2
     */
    public void setCreatemethod(String createmethod) {
        this.createmethod = createmethod;
    }

    /**
     * 旧表名-更新时表名时使用,不与数据库字段对应
     * @return
     * @author zhanghonghui 
     * @date 2012-12-19
     */
	public String getOldtablename() {
		return oldtablename;
	}

	/**
	 * 旧表名-更新时表信息时使用,不与数据库字段对应
	 * @param oldtablename
	 * @author zhanghonghui 
	 * @date 2012-12-19
	 */
	public void setOldtablename(String oldtablename) {
		this.oldtablename = oldtablename;
	}
	
    /**
     * 数据来源,1人工添加，2自动导入，3人工导入，4其他
     * @return
     * @author zhanghonghui 
     * @date 2012-12-25
     */
	public String getDatasource() {
		return datasource;
	}
	
    /**
     * 数据来源,1人工添加，2自动导入，3人工导入，4其他
     * @return
     * @author zhanghonghui 
     * @date 2012-12-25
     */
	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	/**
	 * 是否树形显示
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-4
	 */
	public String getUsetreelist() {
		return usetreelist;
	}

	/**
	 * 是否树形显示
	 * @param usetreelist
	 * @author zhanghonghui 
	 * @date 2013-1-4
	 */
	public void setUsetreelist(String usetreelist) {
		this.usetreelist = usetreelist;
	}

	/**
	 * 树形显示列名
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-4
	 */
	public String getRefertreecol() {
		return refertreecol;
	}

	/**
	 * 树形显示列名
	 * @param refertreecol
	 * @author zhanghonghui 
	 * @date 2013-1-4
	 */
	public void setRefertreecol(String refertreecol) {
		this.refertreecol = refertreecol;
	}

	public String getUseautoencoded() {
		return useautoencoded;
	}

	public void setUseautoencoded(String useautoencoded) {
		this.useautoencoded = useautoencoded;
	}

    /**
     * 是否支持数据权限1是0否
     * @return
     */
    public String getIsdatarule() {
        return isdatarule;
    }

    /**
     * 是否支持数据权限1是0否
     * @param isdatarule
     */
    public void setIsdatarule(String isdatarule) {
        this.isdatarule = isdatarule;
    }
}

