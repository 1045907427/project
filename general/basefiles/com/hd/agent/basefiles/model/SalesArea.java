package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 销售区域
 * @author zhengziyong
 *
 */
public class SalesArea implements Serializable {
	private static final long serialVersionUID = 1L;

    /**
     * 编号（上级编号+本级编号）
     */
    private String id;
    
    /**
     * 旧编号，用于修改时修改编号
     */
    private String oldid;

    /**
     * 名称（上级名称+'/'+本级名称）
     */
    private String name;

    /**
     * 本级编号
     */
    private String thisid;

    /**
     * 本级名称
     */
    private String thisname;

    /**
     * 上级分类
     */
    private String pid;

    /**
     * 状态
     */
    private String state;

    /**
     * 备注
     */
    private String remark;

    /**
     * 建档人
     */
    private String adduserid;
    
    /**
     * 建档人
     */
    private String addusername;

    /**
     * 建档部门
     */
    private String adddeptid;
    
    /**
     * 建档部门
     */
    private String adddeptname;

    /**
     * 建档时间
     */
    private Date addtime;

    /**
     * 最后修改人
     */
    private String modifyuserid;
    
    /**
     * 最后修改人
     */
    private String modifyusername;

    /**
     * 最后修改时间
     */
    private Date modifytime;

    /**
     * 启用人
     */
    private String openuserid;
    
    /**
     * 启用人
     */
    private String openusername;

    /**
     * 启用时间
     */
    private Date opentime;

    /**
     * 禁用人
     */
    private String closeuserid;
    
    /**
     * 禁用人
     */
    private String closeusername;

    /**
     * 禁用时间
     */
    private Date closetime;

    /**
     * @return 编号（上级编号+本级编号）
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            编号（上级编号+本级编号）
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 旧编号，用于修改时修改编号
     */
    public String getOldid() {
		return oldid;
	}

    /**
     * @param oldid 
	 *            旧编号，用于修改时修改编号
     */
	public void setOldid(String oldid) {
		this.oldid = oldid;
	}

	/**
     * @return 名称（上级名称+'/'+本级名称）
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            名称（上级名称+'/'+本级名称）
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 本级编号
     */
    public String getThisid() {
        return thisid;
    }

    /**
     * @param thisid 
	 *            本级编号
     */
    public void setThisid(String thisid) {
        this.thisid = thisid;
    }

    /**
     * @return 本级名称
     */
    public String getThisname() {
        return thisname;
    }

    /**
     * @param thisname 
	 *            本级名称
     */
    public void setThisname(String thisname) {
        this.thisname = thisname;
    }

    /**
     * @return 上级分类
     */
    public String getPid() {
        return pid;
    }

    /**
     * @param pid 
	 *            上级分类
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * @return 状态
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态
     */
    public void setState(String state) {
        this.state = state;
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
        this.remark = remark;
    }

    /**
     * @return 建档人
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            建档人
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid;
    }

    /**
     * @return 建档人
     */
    public String getAddusername() {
		return addusername;
	}

    /**
     * @param addusername
	 *            建档人
     */
	public void setAddusername(String addusername) {
		this.addusername = addusername;
	}

	/**
     * @return 建档部门
     */
    public String getAdddeptid() {
        return adddeptid;
    }

    /**
     * @param adddeptid 
	 *            建档部门
     */
    public void setAdddeptid(String adddeptid) {
        this.adddeptid = adddeptid;
    }

    /**
     * @return 建档部门
     */
    public String getAdddeptname() {
		return adddeptname;
	}

    /**
     * @param adddeptname
	 *            建档部门
     */
	public void setAdddeptname(String adddeptname) {
		this.adddeptname = adddeptname;
	}

	/**
     * @return 建档时间
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            建档时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * @return 最后修改人
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid 
	 *            最后修改人
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid;
    }

    /**
     * @return 最后修改人
     */
    public String getModifyusername() {
		return modifyusername;
	}

    /**
     * @param modifyusername
	 *            最后修改人
     */
	public void setModifyusername(String modifyusername) {
		this.modifyusername = modifyusername;
	}

	/**
     * @return 最后修改时间
     */
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * @param modifytime 
	 *            最后修改时间
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * @return 启用人
     */
    public String getOpenuserid() {
        return openuserid;
    }

    /**
     * @param openuserid 
	 *            启用人
     */
    public void setOpenuserid(String openuserid) {
        this.openuserid = openuserid;
    }

    /**
     * @return 启用人
     */
    public String getOpenusername() {
		return openusername;
	}

    /**
     * @param openusername
	 *            启用人
     */
	public void setOpenusername(String openusername) {
		this.openusername = openusername;
	}

	/**
     * @return 启用时间
     */
    public Date getOpentime() {
        return opentime;
    }

    /**
     * @param opentime 
	 *            启用时间
     */
    public void setOpentime(Date opentime) {
        this.opentime = opentime;
    }

    /**
     * @return 禁用人
     */
    public String getCloseuserid() {
        return closeuserid;
    }

    /**
     * @param closeuserid 
	 *            禁用人
     */
    public void setCloseuserid(String closeuserid) {
        this.closeuserid = closeuserid;
    }

    /**
     * @return 禁用人
     */
    public String getCloseusername() {
		return closeusername;
	}

    /**
     * @param closeusername
	 *            禁用人
     */
	public void setCloseusername(String closeusername) {
		this.closeusername = closeusername;
	}

	/**
     * @return 禁用时间
     */
    public Date getClosetime() {
        return closetime;
    }

    /**
     * @param closetime 
	 *            禁用时间
     */
    public void setClosetime(Date closetime) {
        this.closetime = closetime;
    }
}