package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 仓库档案
 * @author chenwei
 */
public class StorageInfo implements Serializable {
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
     * 名称
     */
    private String name;

    /**
     * 状态4新增3暂存2保存1启用0禁用
     */
    private String state;

    /**
     * 仓库类型
     */
    private String storagetype;
    /**
     * 仓库类型名称
     */
    private String storagetypename;
    /**
     * 车销人员
     */
    private String carsaleuser;
    /**
     * 金额管理方式1数量金额合并2数量金额分开3不管理金额
     */
    private String moenytype;
    /**
     * 金额管理方式名称
     */
    private String moenytypename;
    /**
     * 计价方式1次移动平均价
     */
    private String pricetype;
    /**
     * 计价方式名称
     */
    private String pricetypename;
    /**
     * 是否批次管理1是0否
     */
    private String isbatch;

    /**
     * 是否库位管理1是0否
     */
    private String isstoragelocation;

    /**
     * 是否允许负库存1是0否
     */
    private String islosestorage;

    /**
     * 是否参与总量控制1是0否
     */
    private String istotalcontrol;

    /**
     * 是否允许超可用量发货1是0否
     */
    private String issendusable;

    /**
     * 是否允许超可用量出库
     */
    private String isoutusable;
    /**
     * 是否发货仓库1是0否（车销不属于发货仓库）
     */
    private String issendstorage;
    /**
     * 仓库是否独立核算1是0否
     */
    private String isaloneaccount;
    /**
     * 是否允许配货操作（不允许配货，只能指定仓库发货）1是0否
     */
    private String isconfig;
    /**
     * 负责人用户编号
     */
    private String manageruserid;
    /**
     * 负责人姓名
     */
    private String managername;
    /**
     * 电话
     */
    private String telphone;
    /**
     * 传真
     */
    private String fax;

    /**
     * 地址
     */
    private String addr;

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
     * 建档部门名称
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
     * @return 仓库类型
     */
    public String getStoragetype() {
        return storagetype;
    }

    /**
     * @param storagetype 
	 *            仓库类型
     */
    public void setStoragetype(String storagetype) {
        this.storagetype = storagetype == null ? null : storagetype.trim();
    }

    /**
     * @return 金额管理方式1数量金额合并2数量金额分开3不管理金额
     */
    public String getMoenytype() {
        return moenytype;
    }

    /**
     * @param moenytype 
	 *            金额管理方式1数量金额合并2数量金额分开3不管理金额
     */
    public void setMoenytype(String moenytype) {
        this.moenytype = moenytype == null ? null : moenytype.trim();
    }

    /**
     * @return 计价方式1次移动平均价
     */
    public String getPricetype() {
        return pricetype;
    }

    /**
     * @param pricetype 
	 *            计价方式1次移动平均价
     */
    public void setPricetype(String pricetype) {
        this.pricetype = pricetype == null ? null : pricetype.trim();
    }

    /**
     * @return 是否批次管理1是0否
     */
    public String getIsbatch() {
        return isbatch;
    }

    /**
     * @param isbatch 
	 *            是否批次管理1是0否
     */
    public void setIsbatch(String isbatch) {
        this.isbatch = isbatch == null ? null : isbatch.trim();
    }

    /**
     * @return 是否库位管理1是0否
     */
    public String getIsstoragelocation() {
        return isstoragelocation;
    }

    /**
     * @param isstoragelocation 
	 *            是否库位管理1是0否
     */
    public void setIsstoragelocation(String isstoragelocation) {
        this.isstoragelocation = isstoragelocation == null ? null : isstoragelocation.trim();
    }

    /**
     * @return 是否允许父库存1是0否
     */
    public String getIslosestorage() {
        return islosestorage;
    }

    /**
     * @param islosestorage 
	 *            是否允许父库存1是0否
     */
    public void setIslosestorage(String islosestorage) {
        this.islosestorage = islosestorage == null ? null : islosestorage.trim();
    }

    /**
     * @return 是否参与总量控制1是0否
     */
    public String getIstotalcontrol() {
        return istotalcontrol;
    }

    /**
     * @param istotalcontrol 
	 *            是否参与总量控制1是0否
     */
    public void setIstotalcontrol(String istotalcontrol) {
        this.istotalcontrol = istotalcontrol == null ? null : istotalcontrol.trim();
    }

    /**
     * @return 是否允许超可用量发货1是0否
     */
    public String getIssendusable() {
        return issendusable;
    }

    /**
     * @param issendusable 
	 *            是否允许超可用量发货1是0否
     */
    public void setIssendusable(String issendusable) {
        this.issendusable = issendusable == null ? null : issendusable.trim();
    }

    /**
     * @return 是否允许超可用量出库
     */
    public String getIsoutusable() {
        return isoutusable;
    }

    /**
     * @param isoutusable 
	 *            是否允许超可用量出库
     */
    public void setIsoutusable(String isoutusable) {
        this.isoutusable = isoutusable == null ? null : isoutusable.trim();
    }

    /**
     * @return 负责人用户编号
     */
    public String getManageruserid() {
        return manageruserid;
    }

    /**
     * @param manageruserid 
	 *            负责人用户编号
     */
    public void setManageruserid(String manageruserid) {
        this.manageruserid = manageruserid == null ? null : manageruserid.trim();
    }

    /**
     * @return 电话
     */
    public String getTelphone() {
        return telphone;
    }

    /**
     * @param telphone 
	 *            电话
     */
    public void setTelphone(String telphone) {
        this.telphone = telphone == null ? null : telphone.trim();
    }

    /**
     * @return 地址
     */
    public String getAddr() {
        return addr;
    }

    /**
     * @param addr 
	 *            地址
     */
    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
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
     * @return 建档人姓名
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            建档人姓名
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
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
     * @return 建档部门名称
     */
    public String getAdddeptname() {
        return adddeptname;
    }

    /**
     * @param adddeptname 
	 *            建档部门名称
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
     * @return 修改人姓名
     */
    public String getModifyusername() {
        return modifyusername;
    }

    /**
     * @param modifyusername 
	 *            修改人姓名
     */
    public void setModifyusername(String modifyusername) {
        this.modifyusername = modifyusername == null ? null : modifyusername.trim();
    }

    /**
     * @return 修改时间
     */
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
     * @return 启用人姓名
     */
    public String getOpenusername() {
        return openusername;
    }

    /**
     * @param openusername 
	 *            启用人姓名
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
     * @return 禁用人姓名
     */
    public String getCloseusername() {
        return closeusername;
    }

    /**
     * @param closeusername 
	 *            禁用人姓名
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

	public String getStoragetypename() {
		return storagetypename;
	}

	public void setStoragetypename(String storagetypename) {
		this.storagetypename = storagetypename;
	}

	public String getMoenytypename() {
		return moenytypename;
	}

	public void setMoenytypename(String moenytypename) {
		this.moenytypename = moenytypename;
	}

	public String getPricetypename() {
		return pricetypename;
	}

	public void setPricetypename(String pricetypename) {
		this.pricetypename = pricetypename;
	}

	public String getManagername() {
		return managername;
	}

	public void setManagername(String managername) {
		this.managername = managername;
	}

	public String getCarsaleuser() {
		return carsaleuser;
	}

	public void setCarsaleuser(String carsaleuser) {
		this.carsaleuser = carsaleuser;
	}

	public String getIssendstorage() {
		return issendstorage;
	}

	public void setIssendstorage(String issendstorage) {
		this.issendstorage = issendstorage;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getIsconfig() {
		return isconfig;
	}

	public void setIsconfig(String isconfig) {
		this.isconfig = isconfig;
	}

    public String getIsaloneaccount() {
        return isaloneaccount;
    }

    public void setIsaloneaccount(String isaloneaccount) {
        this.isaloneaccount = isaloneaccount;
    }
}