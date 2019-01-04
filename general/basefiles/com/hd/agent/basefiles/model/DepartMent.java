/**
 * @(#)DepartMent.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-21 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class DepartMent implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 部门编码(上级编码+本级编码)
     */
    private String id;

	/**
     * 修改之前的部门编码
     */
    private String oldId;
    public String getOldId() {
		return oldId;
	}

	public void setOldId(String oldId) {
		this.oldId = oldId;
	}

	/**
     * 本级编码
     */
    private String thisid;

    /**
     * 上级编码
     */
    private String pid;
    
    /**
     * 上级部门
     */
    private String pDeptName;

	/**
     * 部门名称
     */
    private String name;

    /**
     * 4新增/3暂存/2保存/1启用/0禁用
     */
    private String state;
    
    /**
     * 状态名称 
     */
    private String stateName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 添加人用户编号
     */
    private String adduserid;

    /**
     * 添加时间
     */
    private Date addtime;
    
    /**
     * 添加人部门编号
     */
    private String adddeptid;

    /**
     * 修改人用户编号
     */
    private String modifyuserid;

    /**
     * 修改时间
     */
    private Date modifytime;
    
    /**
     * 修改人部门编号
     */
    private String modifydeptid;

    /**
     * 部门主管用户编号
     */
    private String manageruserid;
    
    /**
     * 部门主管
     */
    private String managerName;

    /**
     * 电话
     */
    private String tel;

    /**
     * 传真
     */
    private String fax;

    /**
     * 工作日历
     */
    private String workcalendar;

    /**
     * 末级标志(子节点标志)1是0否
     */
    private String leaf;
    
    /**
     * 启用人用户编号
     */
    private String openuserid;
    
    /**
     * 启用时间
     */
    private Date opentime;
    
    /**
     * 禁用人用户编号
     */
    private String closeuserid;
    
    /**
     * 禁用时间
     */
    private Date closetime;
    
    /**
     * 部门业务属性
     */
    private String depttype;
    
    /**
     * 部门业务属性名称
     */
    private String depttypeName;
    
    /**
     * 关联仓库
     */
    private String storageid;
    
    /**
     * 关联仓库名称
     */
    private String storagename;

    public String getDepttype() {
		return depttype;
	}

	public void setDepttype(String depttype) {
		this.depttype = depttype;
	}

	/**
     * 
     * @return 启用人用户编号
     * @author panxiaoxiao 
     * @date 2013-1-26
     */
    public String getOpenuserid() {
		return openuserid;
	}

    /**
     * 启用人用户编号
     * @param openuserid
     * @author panxiaoxiao 
     * @date 2013-1-26
     */
	public void setOpenuserid(String openuserid) {
		this.openuserid = openuserid;
	}

	/**
	 * 
	 * @return 启用时间
	 * @author panxiaoxiao 
	 * @date 2013-1-26
	 */
	public Date getOpentime() {
		return opentime;
	}

	/**
	 * 启用时间
	 * @param opentime
	 * @author panxiaoxiao 
	 * @date 2013-1-26
	 */
	public void setOpentime(Date opentime) {
		this.opentime = opentime;
	}

	/**
	 * 
	 * @return 禁用人用户编号
	 * @author panxiaoxiao 
	 * @date 2013-1-26
	 */
	public String getCloseuserid() {
		return closeuserid;
	}

	/**
	 * 禁用人用户编号
	 * @param closeuserid
	 * @author panxiaoxiao 
	 * @date 2013-1-26
	 */
	public void setCloseuserid(String closeuserid) {
		this.closeuserid = closeuserid;
	}

	/**
	 * 
	 * @return 禁用时间
	 * @author panxiaoxiao 
	 * @date 2013-1-26
	 */
	public Date getClosetime() {
		return closetime;
	}

	/**
	 * 禁用时间
	 * @param closetime
	 * @author panxiaoxiao 
	 * @date 2013-1-26
	 */
	public void setClosetime(Date closetime) {
		this.closetime = closetime;
	}

	/**
	 * 
	 * @return 添加人部门编号
	 * @author panxiaoxiao 
	 * @date 2013-1-26
	 */
	public String getAdddeptid() {
		return adddeptid;
	}

	/**
	 * 添加人部门编号
	 * @param adddeptid
	 * @author panxiaoxiao 
	 * @date 2013-1-26
	 */
	public void setAdddeptid(String adddeptid) {
		this.adddeptid = adddeptid;
	}

	/**
	 * 
	 * @return 修改人部门编号
	 * @author panxiaoxiao 
	 * @date 2013-1-26
	 */
	public String getModifydeptid() {
		return modifydeptid;
	}

	/**
	 * 修改人部门编号
	 * @param modifydeptid
	 * @author panxiaoxiao 
	 * @date 2013-1-26
	 */
	public void setModifydeptid(String modifydeptid) {
		this.modifydeptid = modifydeptid;
	}

	/**
     * @return 部门编码(上级编码+本级编码)
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            部门编码(上级编码+本级编码)
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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
     * @return 上级编码
     */
    public String getPid() {
        return pid;
    }

    /**
     * @param pid 
	 *            上级编码
     */
    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    /**
     * @return 部门名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            部门名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return 4新增/3暂存/2保存/1启用/0禁用
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            4新增/3暂存/2保存/1启用/0禁用
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
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
     * @return 部门主管用户编号
     */
    public String getManageruserid() {
        return manageruserid;
    }

    /**
     * @param manageruserid 
	 *            部门主管用户编号
     */
    public void setManageruserid(String manageruserid) {
        this.manageruserid = manageruserid == null ? null : manageruserid.trim();
    }

    /**
     * @return 电话
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel 
	 *            电话
     */
    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    /**
     * @return 传真
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax 
	 *            传真
     */
    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    /**
     * @return 工作日历
     */
    public String getWorkcalendar() {
        return workcalendar;
    }

    /**
     * @param workcalendar 
	 *            工作日历
     */
    public void setWorkcalendar(String workcalendar) {
        this.workcalendar = workcalendar == null ? null : workcalendar.trim();
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
    
    public String getpDeptName() {
		return pDeptName;
	}

	public void setpDeptName(String pDeptName) {
		this.pDeptName = pDeptName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getDepttypeName() {
		return depttypeName;
	}

	public void setDepttypeName(String depttypeName) {
		this.depttypeName = depttypeName;
	}

	public String getStorageid() {
		return storageid;
	}

	public void setStorageid(String storageid) {
		this.storageid = storageid;
	}

	public String getStoragename() {
		return storagename;
	}

	public void setStoragename(String storagename) {
		this.storagename = storagename;
	}
	
}