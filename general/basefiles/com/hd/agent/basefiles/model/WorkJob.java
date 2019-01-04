/**
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-4-11 chenwei 创建版本
 */
package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
/**
 * 工作岗位
 * @author chenwei
 */
public class WorkJob implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;
    /**
     * 修改前编号
     */
    private String oldid;
    /**
     * 工作岗位名称
     */
    private String jobname;

    /**
     * 状态4新增/3暂存/2保存/1启用/0禁用
     */
    private String state;

    /**
     * 所属部门编号
     */
    private String deptid;
    /**
     * 所属部门名称
     */
    private String deptname;
    /**
     * 工作岗位拥有的角色
     */
    private String roleList;
    /**
     * 备注
     */
    private String remark;

    /**
     * 建档人用户编号
     */
    private String adduserid;
    /**
     * 建档人姓名
     */
    private String addusername;
    /**
     * 建档人部门编号
     */
    private String adddeptid;
    /**
     * 建档人部门名称
     */
    private String adddeptname;
    /**
     * 建档时间
     */
    private Date addtime;

    /**
     * 修改人用户编号
     */
    private String modifyuserid;
    /**
     * 修改人姓名
     */
    private String modifyusername;
    /**
     * 修改时间
     */
    private Date modifytime;

    /**
     * 启用人用户编号
     */
    private String openuserid;
    /**
     * 启用人姓名
     */
    private String openusername;
    /**
     * 启用时间
     */
    private Date opentime;

    /**
     * 禁用人用户编号
     */
    private String closeuserid;
    /**
     * 禁用人姓名
     */
    private String closeusername;
    /**
     * 禁用时间
     */
    private Date closetime;

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
        this.id = id;
    }

    /**
     * @return 工作岗位名称
     */
    public String getJobname() {
        return jobname;
    }

    /**
     * @param jobname 
	 *            工作岗位名称
     */
    public void setJobname(String jobname) {
        this.jobname = jobname == null ? null : jobname.trim();
    }

    /**
     * @return 状态4新增/3暂存/2保存/1启用/0禁用
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态4新增/3暂存/2保存/1启用/0禁用
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * @return 所属部门编号
     */
    public String getDeptid() {
        return deptid;
    }

    /**
     * @param deptid 
	 *            所属部门编号
     */
    public void setDeptid(String deptid) {
        this.deptid = deptid == null ? null : deptid.trim();
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
     * @return 建档人用户编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            建档人用户编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 建档人部门编号
     */
    public String getAdddeptid() {
        return adddeptid;
    }

    /**
     * @param adddeptid 
	 *            建档人部门编号
     */
    public void setAdddeptid(String adddeptid) {
        this.adddeptid = adddeptid == null ? null : adddeptid.trim();
    }

    /**
     * @return 建档时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
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
     * @return 启用人用户编号
     */
    public String getOpenuserid() {
        return openuserid;
    }

    /**
     * @param openuserid 
	 *            启用人用户编号
     */
    public void setOpenuserid(String openuserid) {
        this.openuserid = openuserid == null ? null : openuserid.trim();
    }

    /**
     * @return 启用时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
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
     * @return 禁用人用户编号
     */
    public String getCloseuserid() {
        return closeuserid;
    }

    /**
     * @param closeuserid 
	 *            禁用人用户编号
     */
    public void setCloseuserid(String closeuserid) {
        this.closeuserid = closeuserid == null ? null : closeuserid.trim();
    }

    /**
     * @return 禁用时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
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

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getRoleList() {
		return roleList;
	}

	public void setRoleList(String roleList) {
		this.roleList = roleList;
	}

	public String getOldid() {
		return oldid;
	}

	public void setOldid(String oldid) {
		this.oldid = oldid;
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

	public String getAdddeptname() {
		return adddeptname;
	}

	public void setAdddeptname(String adddeptname) {
		this.adddeptname = adddeptname;
	}
    
}