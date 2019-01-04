package com.hd.agent.storage.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class DeliverySaleOut implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 配送单编号
	 */
	private String deliveryid;

	/**
	 * 发货单编号
	 */
	private String saleoutid;

	/**
	 * 订单编号
	 */
	private String orderid;

	/**
	 * 发货日期
	 */
	private String businessdate;

	/**
	 * 客户编号
	 */
	private String customerid;

	/**
	 * 客户名称
	 */
	private String customername;

	/**
	 * 客户业务员ID
	 */
	private String salesuser;
	
	/**
	 * 客户业务员名称
	 */
	private String salesusername;
	
	/**
	 * 销售区域
	 */
	private String salesarea;
	
	/**
	 * 销售区域名称
	 */
	private String salesareaname;
	
	/**
	 * 所属线路
	 */
	private String lineid;
	
	/**
	 * 所属线路名称
	 */
	private String linename;

	/**
	 * 单据数
	 */
	private Integer billnums;

	/**
	 * 销售额
	 */
	private BigDecimal salesamount;

	/**
	 * 商品总箱数
	 */
	private BigDecimal boxnum;
	/**
	 * 商品箱数
	 */
	private BigDecimal auxnum;

	/**
	 * 商品件数
	 */
	private BigDecimal auxremainder;

	/**
	 * 商品体积
	 */
	private BigDecimal volume;

	/**
	 * 商品重量
	 */
	private BigDecimal weight;
	
	/**
	 * 是否默认选中
	 */
	private String check;
	
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 配送类型  0 销售订单 1 供应商代配送客户订单
	 */
	private String deliverytype;
	
	private String remark;

    /**
     * 是否线路内
     */
    private String inline;

	/**
	 * 单据类型；1：发货单；2：带配送订单；3：调拨单
	 */
	private String billtype;

	/**
	 * 仓库编码
	 */
	private String storageid;

	/**
	 * 仓库名称
	 */
	private String storagename;
	
	public String getDeliverytype() {
		return deliverytype;
	}

	public void setDeliverytype(String deliverytype) {
		this.deliverytype = deliverytype;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

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

	public BigDecimal getBoxnum() {
		return boxnum;
	}

	public void setBoxnum(BigDecimal boxnum) {
		this.boxnum = boxnum;
	}

	public String getSalesuser() {
		return salesuser;
	}

	public void setSalesuser(String salesuser) {
		this.salesuser = salesuser;
	}

	/**
	 * @return 发货单编号
	 */
	public String getSaleoutid() {
		return saleoutid;
	}

	/**
	 * @param saleoutid
	 *            发货单编号
	 */
	public void setSaleoutid(String saleoutid) {
		this.saleoutid = saleoutid;
	}

	/**
	 * @return 订单编号
	 */
	public String getOrderid() {
		return orderid;
	}

	/**
	 * @param orderid
	 *            订单编号
	 */
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	/**
	 * @return 发货日期
	 */
	public String getBusinessdate() {
		return businessdate;
	}

	/**
	 * @param businessdate
	 *            发货日期
	 */
	public void setBusinessdate(String businessdate) {
		this.businessdate = businessdate;
	}

	/**
	 * @return 客户编号
	 */
	public String getCustomerid() {
		return customerid;
	}

	/**
	 * @param customerid
	 *            客户编号
	 */
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	/**
	 * @return 单据数
	 */
	public Integer getBillnums() {
		return billnums;
	}

	/**
	 * @param billnums
	 *            单据数
	 */
	public void setBillnums(Integer billnums) {
		this.billnums = billnums;
	}

	/**
	 * @return 销售额
	 */
	public BigDecimal getSalesamount() {
		return salesamount;
	}

	/**
	 * @param salesamount
	 *            销售额
	 */
	public void setSalesamount(BigDecimal salesamount) {
		this.salesamount = salesamount;
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
	 * @return 商品件数
	 */
	public BigDecimal getAuxremainder() {
		return auxremainder;
	}

	/**
	 * @param auxremainder
	 *            商品件数
	 */
	public void setAuxremainder(BigDecimal auxremainder) {
		this.auxremainder = auxremainder;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public BigDecimal getVolume() {
		if (volume == null)
			return new BigDecimal(0);
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getWeight() {
		if (weight == null)
			return new BigDecimal(0);
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public String getSalesusername() {
		return salesusername;
	}

	public void setSalesusername(String salesusername) {
		this.salesusername = salesusername;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSalesarea() {
		return salesarea;
	}

	public void setSalesarea(String salesarea) {
		this.salesarea = salesarea;
	}

	public String getSalesareaname() {
		return salesareaname;
	}

	public void setSalesareaname(String salesareaname) {
		this.salesareaname = salesareaname;
	}

	public String getLineid() {
		return lineid;
	}

	public void setLineid(String lineid) {
		this.lineid = lineid;
	}

	public String getLinename() {
		return linename;
	}

	public void setLinename(String linename) {
		this.linename = linename;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

    public String getInline() {
        return inline;
    }

    public void setInline(String inline) {
        this.inline = inline;
    }

	public String getBilltype() {
		return billtype;
	}

	public void setBilltype(String billtype) {
		this.billtype = billtype;
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