package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.util.Date;

public class WaresClass implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 旧编码 
     */
    private String oldId;
    
    /**
     * 编码
     */
    private String id;
    
    /**
     * 上级分类编码
     */
    private String pid;
    
    /**
     * 上级分类名称
     */
    private String pidName;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态
     */
    private String state;
    
    /**
     * 状态名称
     */
    private String stateName;

    /**
     * 备用
     */
    private String remark;

    /**
     * 建档人编号
     */
    private String adduserid;

    /**
     * 建档部门编号
     */
    private String adddeptid;

    /**
     * 建档时间
     */
    private Date addtime;

    /**
     * 最后修改人编号
     */
    private String modifyuserid;

    /**
     * 最后修改时间
     */
    private Date modifytime;

    /**
     * 启用人编号
     */
    private String openuserid;

    /**
     * 启用时间
     */
    private Date opentime;

    /**
     * 禁用人编号
     */
    private String closeuserid;

    /**
     * 禁用人时间
     */
    private Date closetime;

    /**
     * 本级编码
     */
    private String thisid;

    /**
     * 本级名称
     */
    private String thisname;

    /**
     * 末级标志(子节点标志)1是0否
     */
    private String leaf;
    
    private String addusername;
    
    private String modifyusername;
    
    private String openusername;
    
    private String closeusername;
    
    private String adddeptidname;
    
    public String getAdddeptidname() {
		return adddeptidname;
	}

	public void setAdddeptidname(String adddeptidname) {
		this.adddeptidname = adddeptidname;
	}

	public String getAddusername() {
		return addusername;
	}

	public void setAddusername(String addusername) {
		this.addusername = addusername;
	}

	public String getModifyusername() {
		return modifyusername;
	}

	public void setModifyusername(String modifyusername) {
		this.modifyusername = modifyusername;
	}

	public String getOpenusername() {
		return openusername;
	}

	public void setOpenusername(String openusername) {
		this.openusername = openusername;
	}

	public String getCloseusername() {
		return closeusername;
	}

	public void setCloseusername(String closeusername) {
		this.closeusername = closeusername;
	}

	/**
     * @return 编码
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            编码
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
        this.state = state == null ? null : state.trim();
    }

    /**
     * @return 备用
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark 
	 *            备用
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * @return 建档人编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            建档人编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 建档部门编号
     */
    public String getAdddeptid() {
        return adddeptid;
    }

    /**
     * @param adddeptid 
	 *            建档部门编号
     */
    public void setAdddeptid(String adddeptid) {
        this.adddeptid = adddeptid == null ? null : adddeptid.trim();
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
     * @return 最后修改人编号
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid 
	 *            最后修改人编号
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
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
     * @return 启用人编号
     */
    public String getOpenuserid() {
        return openuserid;
    }

    /**
     * @param openuserid 
	 *            启用人编号
     */
    public void setOpenuserid(String openuserid) {
        this.openuserid = openuserid == null ? null : openuserid.trim();
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
     * @return 禁用人编号
     */
    public String getCloseuserid() {
        return closeuserid;
    }

    /**
     * @param closeuserid 
	 *            禁用人编号
     */
    public void setCloseuserid(String closeuserid) {
        this.closeuserid = closeuserid == null ? null : closeuserid.trim();
    }

    /**
     * @return 禁用人时间
     */
    public Date getClosetime() {
        return closetime;
    }

    /**
     * @param closetime 
	 *            禁用人时间
     */
    public void setClosetime(Date closetime) {
        this.closetime = closetime;
    }

    /**
     * @return 本级编码
     */
    public String getThisid() {
        return thisid;
    }

    /**
     * @param thisid 
	 *            本级编码
     */
    public void setThisid(String thisid) {
        this.thisid = thisid == null ? null : thisid.trim();
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
        this.thisname = thisname == null ? null : thisname.trim();
    }

    /**
     * @return 末级标志(子节点标志)1是0否
     */
    public String getLeaf() {
        return leaf;
    }

    /**
     * @param leaf 
	 *            末级标志(子节点标志)1是0否
     */
    public void setLeaf(String leaf) {
        this.leaf = leaf == null ? null : leaf.trim();
    }

    /**
	 * @return 旧编码
     */
	public String getOldId() {
		return oldId;
	}

	/**
     * @param oldId
	 *            旧编码
     */
	public void setOldId(String oldId) {
		this.oldId = oldId;
	}

	/**
	 * @return 状态名称
	 * @author panxiaoxiao 
	 * @date 2013-4-12
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * @param stateName 状态名称
	 * @author panxiaoxiao 
	 * @date 2013-4-12
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * @return 上级分类编码
	 * @author panxiaoxiao 
	 * @date 2013-4-12
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * @param pid 上级分类编码
	 * @author panxiaoxiao 
	 * @date 2013-4-12
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * 
	 * @return 上级分类名称
	 * @author panxiaoxiao 
	 * @date 2013-4-12
	 */
	public String getPidName() {
		return pidName;
	}

	/**
	 * @param pidName 上级分类名称
	 * @author panxiaoxiao 
	 * @date 2013-4-12
	 */
	public void setPidName(String pidName) {
		this.pidName = pidName;
	}
    
}