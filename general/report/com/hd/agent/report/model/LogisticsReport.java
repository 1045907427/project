package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class LogisticsReport implements Serializable {
    private static final long serialVersionUID = 1L;

     /**
      * 物流奖金报表 插入的表格
      * @author lin_xx
      * @date 2017/3/13
      */
     private String tablename;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 配送单编号
     */
    private String deliveryid;

    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 制单人编号
     */
    private String adduserid;

    /**
     * 制单人姓名
     */
    private String addusername;

    /**
     * 制单人部门编号
     */
    private String adddeptid;

    /**
     * 制单人部门名称
     */
    private String adddeptname;

    /**
     * 制单时间
     */
    private Date addtime;

    /**
     * 最后修改人编号
     */
    private String modifyuserid;

    /**
     * 最后修改人名称
     */
    private String modifyusername;

    /**
     * 最后修改时间
     */
    private Date modifytime;

    /**
     * 审核人编号
     */
    private String audituserid;

    /**
     * 审核人名称
     */
    private String auditusername;

    /**
     * 审核时间
     */
    private Date audittime;

    /**
     * 中止人编号
     */
    private String stopuserid;

    /**
     * 中止人名称
     */
    private String stopusername;

    /**
     * 中止时间
     */
    private Date stoptime;

    /**
     * 关闭时间
     */
    private Date closetime;

    /**
     * 打印次数
     */
    private Integer printtimes;

    /**
     * 线路编号
     */
    private String lineid;
    /**
     * 线路名称
     */
    private String linename;

    /**
     * 车辆编号
     */
    private String carid;
    /**
     * 车辆名称
     */
    private String carname;

    /**
     * 车型1大车2中车3小车
     */
    private String cartype;

    /**
     * 司机编号
     */
    private String driverid;

    /**
     * 司机名称
     */
    private String drivername;
    
    /**
     * 1是司机0是跟车
     */
    private String isdriver;

    /**
     * 送货家数
     */
    private Integer customernums;

    /**
     * 商品总箱数
     */
    private BigDecimal boxnum;
    /**
     * 商品箱数
     */
    private BigDecimal auxnum;

    /**
     * 商品个数
     */
    private BigDecimal auxremainder;

    private BigDecimal volume;

    private BigDecimal weight;
    
    private BigDecimal salesamount;

    /**
     * 装车次数
     */
    private Integer truck;

    /**
     * 装车补助
     */
    private BigDecimal trucksubsidy;

    /**
     * 出车津贴
     */
    private BigDecimal carallowance;

    /**
     * 出车补助
     */
    private BigDecimal carsubsidy;

    /**
     * 客户家数补助
     */
    private BigDecimal customersubsidy;

    /**
     * 收款总金额
     */
    private BigDecimal collectionamount;

    /**
     * 销售补助
     */
    private BigDecimal salessubsidy;

    /**
     * 收款补助
     */
    private BigDecimal collectionsubsidy;

    /**
     * 其他补助
     */
    private BigDecimal othersubsidy;

    /**
     * 安全奖金
     */
    private BigDecimal safebonus;

    /**
     * 回单奖金
     */
    private BigDecimal receiptbonus;

    /**
     * 晚上出车奖金
     */
    private BigDecimal nightbonus;

    /**
     * 合计金额
     */
    private BigDecimal subamount;

    /**
     * 总计金额
     */
    private BigDecimal totalamount;

    /**
     * @return 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 配送单编号
     */
    public String getDeliveryid() {
        return deliveryid;
    }

    /**
     * @param deliveryid 
	 *            配送单编号
     */
    public void setDeliveryid(String deliveryid) {
        this.deliveryid = deliveryid;
    }

    public BigDecimal getSalesamount() {
		return salesamount;
	}

	public void setSalesamount(BigDecimal salesamount) {
		this.salesamount = salesamount;
	}

	/**
     * @return 业务日期
     */
    public String getBusinessdate() {
        return businessdate;
    }

    /**
     * @param businessdate 
	 *            业务日期
     */
    public void setBusinessdate(String businessdate) {
        this.businessdate = businessdate;
    }

    /**
     * @return 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            状态
     */
    public void setStatus(String status) {
        this.status = status;
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
     * @return 制单人编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            制单人编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid;
    }

    /**
     * @return 制单人姓名
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            制单人姓名
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername;
    }

    /**
     * @return 制单人部门编号
     */
    public String getAdddeptid() {
        return adddeptid;
    }

    /**
     * @param adddeptid 
	 *            制单人部门编号
     */
    public void setAdddeptid(String adddeptid) {
        this.adddeptid = adddeptid;
    }

    /**
     * @return 制单人部门名称
     */
    public String getAdddeptname() {
        return adddeptname;
    }

    /**
     * @param adddeptname 
	 *            制单人部门名称
     */
    public void setAdddeptname(String adddeptname) {
        this.adddeptname = adddeptname;
    }

    /**
     * @return 制单时间
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            制单时间
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
        this.modifyuserid = modifyuserid;
    }

    /**
     * @return 最后修改人名称
     */
    public String getModifyusername() {
        return modifyusername;
    }

    /**
     * @param modifyusername 
	 *            最后修改人名称
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
     * @return 审核人编号
     */
    public String getAudituserid() {
        return audituserid;
    }

    /**
     * @param audituserid 
	 *            审核人编号
     */
    public void setAudituserid(String audituserid) {
        this.audituserid = audituserid;
    }

    /**
     * @return 审核人名称
     */
    public String getAuditusername() {
        return auditusername;
    }

    /**
     * @param auditusername 
	 *            审核人名称
     */
    public void setAuditusername(String auditusername) {
        this.auditusername = auditusername;
    }

    /**
     * @return 审核时间
     */
    public Date getAudittime() {
        return audittime;
    }

    /**
     * @param audittime 
	 *            审核时间
     */
    public void setAudittime(Date audittime) {
        this.audittime = audittime;
    }

    /**
     * @return 中止人编号
     */
    public String getStopuserid() {
        return stopuserid;
    }

    /**
     * @param stopuserid 
	 *            中止人编号
     */
    public void setStopuserid(String stopuserid) {
        this.stopuserid = stopuserid;
    }

    /**
     * @return 中止人名称
     */
    public String getStopusername() {
        return stopusername;
    }

    /**
     * @param stopusername 
	 *            中止人名称
     */
    public void setStopusername(String stopusername) {
        this.stopusername = stopusername;
    }

    /**
     * @return 中止时间
     */
    public Date getStoptime() {
        return stoptime;
    }

    /**
     * @param stoptime 
	 *            中止时间
     */
    public void setStoptime(Date stoptime) {
        this.stoptime = stoptime;
    }

    /**
     * @return 关闭时间
     */
    public Date getClosetime() {
        return closetime;
    }

    /**
     * @param closetime 
	 *            关闭时间
     */
    public void setClosetime(Date closetime) {
        this.closetime = closetime;
    }

    /**
     * @return 打印次数
     */
    public Integer getPrinttimes() {
        return printtimes;
    }

    /**
     * @param printtimes 
	 *            打印次数
     */
    public void setPrinttimes(Integer printtimes) {
        this.printtimes = printtimes;
    }

    /**
     * @return 线路编号
     */
    public String getLineid() {
        return lineid;
    }

    /**
     * @param lineid 
	 *            线路编号
     */
    public void setLineid(String lineid) {
        this.lineid = lineid;
    }

    /**
     * @return 车辆编号
     */
    public String getCarid() {
        return carid;
    }

    /**
     * @param carid 
	 *            车辆编号
     */
    public void setCarid(String carid) {
        this.carid = carid;
    }

    /**
     * @return 车型1大车2中车3小车
     */
    public String getCartype() {
        return cartype;
    }

    /**
     * @param cartype 
	 *            车型1大车2中车3小车
     */
    public void setCartype(String cartype) {
        this.cartype = cartype;
    }

    /**
     * @return 司机编号
     */
    public String getDriverid() {
        return driverid;
    }

    /**
     * @param driverid 
	 *            司机编号
     */
    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

    /**
     * @return 1是司机0是跟车
     */
    public String getIsdriver() {
        return isdriver;
    }

    /**
     * @param isdriver 
	 *            1是司机0是跟车
     */
    public void setIsdriver(String isdriver) {
        this.isdriver = isdriver;
    }

    /**
     * @return 送货家数
     */
    public Integer getCustomernums() {
        return customernums;
    }

    /**
     * @param customernums 
	 *            送货家数
     */
    public void setCustomernums(Integer customernums) {
        this.customernums = customernums;
    }

    /**
     * @return 商品箱数
     */
    public BigDecimal getAuxnum() {
        return auxnum;
    }

    /**
     * @param auxnum 
	 *            商品箱数
     */
    public void setAuxnum(BigDecimal auxnum) {
        this.auxnum = auxnum;
    }

    /**
     * @return 商品个数
     */
    public BigDecimal getAuxremainder() {
        return auxremainder;
    }

    /**
     * @param auxremainder 
	 *            商品个数
     */
    public void setAuxremainder(BigDecimal auxremainder) {
        this.auxremainder = auxremainder;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * @return 装车次数
     */
    public Integer getTruck() {
        return truck;
    }

    /**
     * @param truck 
	 *            装车次数
     */
    public void setTruck(Integer truck) {
        this.truck = truck;
    }

    /**
     * @return 装车补助
     */
    public BigDecimal getTrucksubsidy() {
        return trucksubsidy;
    }

    /**
     * @param trucksubsidy 
	 *            装车补助
     */
    public void setTrucksubsidy(BigDecimal trucksubsidy) {
        this.trucksubsidy = trucksubsidy;
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
     * @return 客户家数补助
     */
    public BigDecimal getCustomersubsidy() {
        return customersubsidy;
    }

    /**
     * @param customersubsidy 
	 *            客户家数补助
     */
    public void setCustomersubsidy(BigDecimal customersubsidy) {
        this.customersubsidy = customersubsidy;
    }

    /**
     * @return 收款总金额
     */
    public BigDecimal getCollectionamount() {
        return collectionamount;
    }

    /**
     * @param collectionamount 
	 *            收款总金额
     */
    public void setCollectionamount(BigDecimal collectionamount) {
        this.collectionamount = collectionamount;
    }

    /**
     * @return 收款补助
     */
    public BigDecimal getCollectionsubsidy() {
        return collectionsubsidy;
    }

    /**
     * @param collectionsubsidy 
	 *            收款补助
     */
    public void setCollectionsubsidy(BigDecimal collectionsubsidy) {
        this.collectionsubsidy = collectionsubsidy;
    }

    /**
     * @return 其他补助
     */
    public BigDecimal getOthersubsidy() {
        return othersubsidy;
    }

    /**
     * @param othersubsidy 
	 *            其他补助
     */
    public void setOthersubsidy(BigDecimal othersubsidy) {
        this.othersubsidy = othersubsidy;
    }

    /**
     * @return 安全奖金
     */
    public BigDecimal getSafebonus() {
        return safebonus;
    }

    /**
     * @param safebonus 
	 *            安全奖金
     */
    public void setSafebonus(BigDecimal safebonus) {
        this.safebonus = safebonus;
    }

    /**
     * @return 回单奖金
     */
    public BigDecimal getReceiptbonus() {
        return receiptbonus;
    }

    /**
     * @param receiptbonus 
	 *            回单奖金
     */
    public void setReceiptbonus(BigDecimal receiptbonus) {
        this.receiptbonus = receiptbonus;
    }

    /**
     * @return 晚上出车奖金
     */
    public BigDecimal getNightbonus() {
        return nightbonus;
    }

    /**
     * @param nightbonus 
	 *            晚上出车奖金
     */
    public void setNightbonus(BigDecimal nightbonus) {
        this.nightbonus = nightbonus;
    }

    /**
     * @return 合计金额
     */
    public BigDecimal getSubamount() {
        return subamount;
    }

    /**
     * @param subamount 
	 *            合计金额
     */
    public void setSubamount(BigDecimal subamount) {
        this.subamount = subamount;
    }

    /**
     * @return 总计金额
     */
    public BigDecimal getTotalamount() {
        return totalamount;
    }

    /**
     * @param totalamount 
	 *            总计金额
     */
    public void setTotalamount(BigDecimal totalamount) {
        this.totalamount = totalamount;
    }

	public BigDecimal getBoxnum() {
		return boxnum;
	}

	public void setBoxnum(BigDecimal boxnum) {
		this.boxnum = boxnum;
	}

	public String getDrivername() {
		return drivername;
	}

	public void setDrivername(String drivername) {
		this.drivername = drivername;
	}

	public String getLinename() {
		return linename;
	}

	public void setLinename(String linename) {
		this.linename = linename;
	}

	public String getCarname() {
		return carname;
	}

	public void setCarname(String carname) {
		this.carname = carname;
	}

    public BigDecimal getSalessubsidy() {
        return salessubsidy;
    }

    public void setSalessubsidy(BigDecimal salessubsidy) {
        this.salessubsidy = salessubsidy;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }
}