package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 库位档案
 * @author chenwei
 */
public class StorageLocation implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    private String id;
    /**
     * 修改前编码
     */
    private String oldid;
    /**
     * 名称（上级名称+'/'+本级名称）
     */
    private String name;

    /**
     * 本级编码
     */
    private String thisid;

    /**
     * 本级名称
     */
    private String thisname;

    /**
     * 上级编码
     */
    private String pid;

    /**
     * 所属仓库编号
     */
    private String storageid;
    
    /**
     * 库位体积
     */
    private BigDecimal volume;
    
    /**
     * 库位当前商品箱数
     */
    private Integer boxnum;
    
    /**
     * 是否空置1是0否
     */
    private String isempty;

    /**
     * 状态4新增3暂存2保存1启用0禁用
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
        this.name = name == null ? null : name.trim();
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
     * @return 所属仓库编号
     */
    public String getStorageid() {
        return storageid;
    }

    /**
     * @param storageid 
	 *            所属仓库编号
     */
    public void setStorageid(String storageid) {
        this.storageid = storageid == null ? null : storageid.trim();
    }

    /**
     * @return 状态4新增3暂存2保存1启用0禁用
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态4新增3暂存2保存1启用0禁用
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
        this.adduserid = adduserid == null ? null : adduserid.trim();
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
        this.addusername = addusername == null ? null : addusername.trim();
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
        this.adddeptid = adddeptid == null ? null : adddeptid.trim();
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
        this.adddeptname = adddeptname == null ? null : adddeptname.trim();
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
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
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
        this.modifyusername = modifyusername == null ? null : modifyusername.trim();
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
        this.openuserid = openuserid == null ? null : openuserid.trim();
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
        this.openusername = openusername == null ? null : openusername.trim();
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
        this.closeuserid = closeuserid == null ? null : closeuserid.trim();
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
        this.closeusername = closeusername == null ? null : closeusername.trim();
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

	public String getOldid() {
		return oldid;
	}

	public void setOldid(String oldid) {
		this.oldid = oldid;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public Integer getBoxnum() {
		return boxnum;
	}

	public void setBoxnum(Integer boxnum) {
		this.boxnum = boxnum;
	}

	public String getIsempty() {
		return isempty;
	}

	public void setIsempty(String isempty) {
		this.isempty = isempty;
	}
    
}