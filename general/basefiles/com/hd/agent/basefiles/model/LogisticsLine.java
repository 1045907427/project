package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class LogisticsLine implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    private String id;
    
    /**
     * 旧编码
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
    
    private String stateName;
    

    /**
     * 备注
     */
    private String remark;

    /**
     * 自定义信息1
     */
    private String field01;

    /**
     * 自定义信息2
     */
    private String field02;

    /**
     * 自定义信息3
     */
    private String field03;

    /**
     * 自定义信息4
     */
    private String field04;

    /**
     * 自定义信息5
     */
    private String field05;

    /**
     * 自定义信息6
     */
    private String field06;

    /**
     * 自定义信息7
     */
    private String field07;

    /**
     * 自定义信息8
     */
    private String field08;

    /**
     * 自定义信息9
     */
    private String field09;

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
     * 默认车辆
     */
    private String carid;
    
    /**
     * 默认车辆名称
     */
    private String carname;

    /**
     * 所属区域
     */
    private String salesarea;

    /**
     * 客户总家数
     */
    private Integer totalcustomers;

    /**
     * 线路路程长度
     */
    private String distance;

    /**
     * 线路类型
     */
    private String linetype;

    /**
     * 基准客户家数
     */
    private Integer basecustomers;

    /**
     * 基本补贴
     */
    private BigDecimal baseallowance;

    /**
     * 单家补贴
     */
    private BigDecimal singleallowance;

    /**
     * 出车补助
     */
    private BigDecimal carsubsidy;

    /**
     * 出车津贴
     */
    private BigDecimal carallowance;

    /**
     * 出车总次数
     */
    private Integer totalcars;

    /**
     * 出车总客户数
     */
    private Integer totalcarcustomers;

    /**
     * 收款总金额
     */
    private BigDecimal totalamount;

    /**
     * 最近收款金额
     */
    private BigDecimal newamount;

    /**
     * 配送起点
     */
    private String startpoint;

    /**
     * 线路客户默认配送点
     */
    private String defaultpoint;

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
        this.id = id;
    }

    public String getOldid() {
		return oldid;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public void setOldid(String oldid) {
		this.oldid = oldid;
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
        this.name = name;
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
     * @return 自定义信息1
     */
    public String getField01() {
        return field01;
    }

    /**
     * @param field01 
	 *            自定义信息1
     */
    public void setField01(String field01) {
        this.field01 = field01;
    }

    /**
     * @return 自定义信息2
     */
    public String getField02() {
        return field02;
    }

    /**
     * @param field02 
	 *            自定义信息2
     */
    public void setField02(String field02) {
        this.field02 = field02;
    }

    /**
     * @return 自定义信息3
     */
    public String getField03() {
        return field03;
    }

    /**
     * @param field03 
	 *            自定义信息3
     */
    public void setField03(String field03) {
        this.field03 = field03;
    }

    /**
     * @return 自定义信息4
     */
    public String getField04() {
        return field04;
    }

    /**
     * @param field04 
	 *            自定义信息4
     */
    public void setField04(String field04) {
        this.field04 = field04;
    }

    /**
     * @return 自定义信息5
     */
    public String getField05() {
        return field05;
    }

    /**
     * @param field05 
	 *            自定义信息5
     */
    public void setField05(String field05) {
        this.field05 = field05;
    }

    /**
     * @return 自定义信息6
     */
    public String getField06() {
        return field06;
    }

    /**
     * @param field06 
	 *            自定义信息6
     */
    public void setField06(String field06) {
        this.field06 = field06;
    }

    /**
     * @return 自定义信息7
     */
    public String getField07() {
        return field07;
    }

    /**
     * @param field07 
	 *            自定义信息7
     */
    public void setField07(String field07) {
        this.field07 = field07;
    }

    /**
     * @return 自定义信息8
     */
    public String getField08() {
        return field08;
    }

    /**
     * @param field08 
	 *            自定义信息8
     */
    public void setField08(String field08) {
        this.field08 = field08;
    }

    /**
     * @return 自定义信息9
     */
    public String getField09() {
        return field09;
    }

    /**
     * @param field09 
	 *            自定义信息9
     */
    public void setField09(String field09) {
        this.field09 = field09;
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
        this.adduserid = adduserid;
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
        this.addusername = addusername;
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
        this.adddeptid = adddeptid;
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
        this.adddeptname = adddeptname;
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
        this.modifyuserid = modifyuserid;
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
        this.modifyusername = modifyusername;
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
        this.openuserid = openuserid;
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
        this.openusername = openusername;
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
        this.closeuserid = closeuserid;
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
        this.closeusername = closeusername;
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

    /**
     * @return 默认车辆
     */
    public String getCarid() {
        return carid;
    }

    /**
     * @param carid 
	 *            默认车辆
     */
    public void setCarid(String carid) {
        this.carid = carid;
    }

    /**
     * @return 所属区域
     */
    public String getSalesarea() {
        return salesarea;
    }

    /**
     * @param salesarea 
	 *            所属区域
     */
    public void setSalesarea(String salesarea) {
        this.salesarea = salesarea;
    }

    /**
     * @return 客户总家数
     */
    public Integer getTotalcustomers() {
        return totalcustomers;
    }

    /**
     * @param totalcustomers 
	 *            客户总家数
     */
    public void setTotalcustomers(Integer totalcustomers) {
        this.totalcustomers = totalcustomers;
    }

    /**
     * @return 线路路程长度
     */
    public String getDistance() {
        return distance;
    }

    /**
     * @param distance 
	 *            线路路程长度
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }

    /**
     * @return 线路类型
     */
    public String getLinetype() {
        return linetype;
    }

    /**
     * @param linetype 
	 *            线路类型
     */
    public void setLinetype(String linetype) {
        this.linetype = linetype;
    }

    /**
     * @return 基准客户家数
     */
    public Integer getBasecustomers() {
        return basecustomers;
    }

    /**
     * @param basecustomers 
	 *            基准客户家数
     */
    public void setBasecustomers(Integer basecustomers) {
        this.basecustomers = basecustomers;
    }

    /**
     * @return 基本补贴
     */
    public BigDecimal getBaseallowance() {
        return baseallowance;
    }

    /**
     * @param baseallowance 
	 *            基本补贴
     */
    public void setBaseallowance(BigDecimal baseallowance) {
        this.baseallowance = baseallowance;
    }

    /**
     * @return 单家补贴
     */
    public BigDecimal getSingleallowance() {
        return singleallowance;
    }

    /**
     * @param singleallowance 
	 *            单家补贴
     */
    public void setSingleallowance(BigDecimal singleallowance) {
        this.singleallowance = singleallowance;
    }

    /**
     * @return 出车补助
     */
    public BigDecimal getCarsubsidy() {
        return carsubsidy;
    }

    /**
     * @param carsubsidy 
	 *            出车补助
     */
    public void setCarsubsidy(BigDecimal carsubsidy) {
        this.carsubsidy = carsubsidy;
    }

    /**
     * @return 出车津贴
     */
    public BigDecimal getCarallowance() {
        return carallowance;
    }

    /**
     * @param carallowance 
	 *            出车津贴
     */
    public void setCarallowance(BigDecimal carallowance) {
        this.carallowance = carallowance;
    }

    /**
     * @return 出车总次数
     */
    public Integer getTotalcars() {
        return totalcars;
    }

    /**
     * @param totalcars 
	 *            出车总次数
     */
    public void setTotalcars(Integer totalcars) {
        this.totalcars = totalcars;
    }

    /**
     * @return 出车总客户数
     */
    public Integer getTotalcarcustomers() {
        return totalcarcustomers;
    }

    /**
     * @param totalcarcustomers 
	 *            出车总客户数
     */
    public void setTotalcarcustomers(Integer totalcarcustomers) {
        this.totalcarcustomers = totalcarcustomers;
    }

    /**
     * @return 收款总金额
     */
    public BigDecimal getTotalamount() {
        return totalamount;
    }

    /**
     * @param totalamount 
	 *            收款总金额
     */
    public void setTotalamount(BigDecimal totalamount) {
        this.totalamount = totalamount;
    }

    /**
     * @return 最近收款金额
     */
    public BigDecimal getNewamount() {
        return newamount;
    }

    /**
     * @param newamount 
	 *            最近收款金额
     */
    public void setNewamount(BigDecimal newamount) {
        this.newamount = newamount;
    }
    
    /**
     * 自定义信息10
     */
    private String field10;

    /**
     * 自定义信息11
     */
    private String field11;

    /**
     * 自定义信息12
     */
    private String field12;

    /**
     * @return 自定义信息10
     */
    public String getField10() {
        return field10;
    }

    /**
     * @param field10 
	 *            自定义信息10
     */
    public void setField10(String field10) {
        this.field10 = field10;
    }

    /**
     * @return 自定义信息11
     */
    public String getField11() {
        return field11;
    }

    /**
     * @param field11 
	 *            自定义信息11
     */
    public void setField11(String field11) {
        this.field11 = field11;
    }

    /**
     * @return 自定义信息12
     */
    public String getField12() {
        return field12;
    }

    /**
     * @param field12 
	 *            自定义信息12
     */
    public void setField12(String field12) {
        this.field12 = field12;
    }

	public String getCarname() {
		return carname;
	}

	public void setCarname(String carName) {
		this.carname = carName;
	}

    public String getStartpoint() {
        return startpoint;
    }

    public void setStartpoint(String startpoint) {
        this.startpoint = startpoint;
    }

    public String getDefaultpoint() {
        return defaultpoint;
    }

    public void setDefaultpoint(String defaultpoint) {
        this.defaultpoint = defaultpoint;
    }
}