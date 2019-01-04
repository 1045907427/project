package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 基础销售情况统计报表
 * @author chenwei
 */
public class BaseSalesReport implements Serializable {
    private static final long serialVersionUID = 1L;

    private String treeid;
    
    private String state;
    
    private String parentid;
    /**
     * 编号
     */
    private String id;

    /**
     * 业务日期
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
     * 供应商编码
     */
    private String supplierid;
    
    /**
     * 供应商名称
     */
    private String suppliername;

    /**
     * 仓库编号
     */
    private String storageid;

    /**
     * 仓库名称
     */
    private String storagename;
    
    /**
     * 客户助记符
     */
    private String shortcode;
    
    /**
     * 总店客户
     */
    private String pcustomerid;
    /**
     * 总店客户名称
     */
    private String pcustomername;
    /**
     * 所属部门编号
     */
    private String salesdept;
    /**
     * 所属部门名称
     */
    private String salesdeptname;
    /**
     * 所属客户分类
     */
    private String customersort;
    /**
     * 所属客户分类名称
     */
    private String customersortname;
    /**
     * 所属区域
     */
    private String salesarea;
    /**
     * 所属区域名称
     */
    private String salesareaname;
    /**
     * 客户业务员
     */
    private String salesuser;
    /**
     * 客户业务员名称
     */
    private String salesusername;
    
    /**
     * 司机编码
     */
    private String driverid;
    
    /**
     * 司机名称
     */
    private String drivername;
    /**
     * 商品编码
     */
    private String goodsid;
    /**
     * 商品名称
     */
    private String goodsname;
    
    /**
     * 商品分类
     */
    private String goodssort;
    
    /**
     * 商品分类名称
     */
    private String goodssortname;
    
    /**
     * 商品类型
     */
    private String goodstype;
    
    /**
     * 商品类型名称
     */
    private String goodstypename;
    
    /**
     * 条形码
     */
    private String barcode;

    /**
     * 助记符
     */
    private String spell;
    /**
     * 品牌编号
     */
    private String brandid;
    /**
     * 品牌名称
     */
    private String brandname;

    /**
     * 箱装量
     */
    private BigDecimal boxnum;
    /**
     * 品牌业务员
     */
    private String branduser;
    /**
     * 品牌业务员名称
     */
    private String brandusername;

    /**
     * 品牌业务员所属部门
     */
    private String branduserdept;

    /**
     * 品牌业务员所属部门名称
     */
    private String branduserdeptname;

    /**
     * 厂家业务员
     */
    private String supplieruser;
    
    /**
     * 厂家业务员名称
     */
    private String supplierusername;
    
    /**
     * 制单人编码
     */
    private String adduserid;
    
    /**
     * 制单人名称
     */
    private String addusername;
    /**
     * 品牌部门
     */
    private String branddept;
    /**
     * 品牌部门名称
     */
    private String branddeptname;
    /**
     * 单价
     */
    private String price;
    /**
     * 成本价
     */
    private BigDecimal costprice;
    /**
     * 订单数量
     */
    private BigDecimal ordernum;
    
    /**
     * 订单箱数
     */
    private BigDecimal ordertotalbox;
    /**
     * 订单金额
     */
    private BigDecimal orderamount;
    /**
     * 订单无税金额
     */
    private BigDecimal ordernotaxamount;
    /**
     * 发货单数量
     */
    private BigDecimal initsendnum;
    
    /**
     * 发货单箱数
     */
    private BigDecimal initsendtotalbox;
    /**
     * 发货单金额
     */
    private BigDecimal initsendamount;
    /**
     * 发货单未税金额
     */
    private BigDecimal initsendnotaxamount;
    /**
     * 发货数量
     */
    private BigDecimal sendnum;
    
    /**
     * 发货数量
     */
    private BigDecimal sendtotalbox;
    
    /**
     * 发货金额
     */
    private BigDecimal sendamount;

    /**
     * 无税发货金额
     */
    private BigDecimal sendnotaxamount;
    /**
     * 发货出库成本
     */
    private BigDecimal sendcostamount;
    /**
     * 退货数量
     */
    private BigDecimal returnnum;
    
    /**
     * 退货箱数
     */
    private BigDecimal returntotalbox;

    /**
     * 退货金额
     */
    private BigDecimal returnamount;

    /**
     * 无税退货金额
     */
    private BigDecimal returnnotaxamount;

    /**
     * 直退数量
     */
    private BigDecimal directreturnnum;
    
    /**
     * 直退箱数
     */
    private BigDecimal directreturntotalbox;
    
    /**
     * 主单位
     */
    private String unitid;
    
    /**
     * 主单位名称
     */
    private String unitname;
    
    /**
     * 辅单位
     */
    private String auxunitid;
    
    /**
     * 辅单位名称
     */
    private String auxunitname;

    /**
     * 直退金额
     */
    private BigDecimal directreturnamount;

    /**
     * 无税直退金额
     */
    private BigDecimal directreturnnotaxamount;

    /**
     * 验收退货数量
     */
    private BigDecimal checkreturnnum;
    
    /**
     * 验收退货箱数
     */
    private BigDecimal checkreturntotalbox;

    /**
     * 验收退货金额
     */
    private BigDecimal checkreturnamount;
    
    /**
     * 验收退货率
     */
    private BigDecimal checkreturnrate;

    /**
     * 无税售后退货金额
     */
    private BigDecimal checkreturnnotaxamount;

    /**
     * 销售数量
     */
    private BigDecimal salenum;
    
    /**
     * 销售箱数
     */
    private BigDecimal saletotalbox;
    /**
     * 销售金额
     */
    private BigDecimal saleamount;

    /**
     * 无税销售金额
     */
    private BigDecimal salenotaxamount;
    /**
     * 销售税额
     */
    private BigDecimal saletax;
    /**
     * 成本金额
     */
    private BigDecimal costamount;
    /**
     * 毛利额
     */
    private BigDecimal salemarginamount;
    /**
     * 实际毛利率
     */
    private BigDecimal realrate;
    /**
     * 计划毛利率
     */
    private BigDecimal planrate;
    /**
     * 添加时间
     */
    private Date addtime;
    /**
     * 回笼金额
     */
    private BigDecimal writeoffamount;
    /**
     * 回笼成本金额
     */
    private BigDecimal costwriteoffamount;
    
    /**
     * 回笼毛利率
     */
    private BigDecimal writeoffmarginamount;
    /**
     * 回笼毛利率
     */
    private BigDecimal writeoffrate;
    
    /**
     * 冲差金额
     */
    private BigDecimal pushbalanceamount;

    /**
     * 冲差未税金额
     */
    private BigDecimal pushbalancenotaxamount;
    
    /**
     * 退货总数辅数量（整数）
     */
    private BigDecimal auxreturnnum;
    
    /**
     * 退货总数辅数量（散）
     */
    private BigDecimal auxremainderreturnnum;
    
    /**
     * 退货总数辅数量
     */
    private String auxreturnnumdetail;
    
    /**
     * 直退辅数量（整）
     */
    private BigDecimal auxdirectnum;
    
    /**
     * 直退辅数量（散）
     */
    private BigDecimal auxremainderdirectnum;
    
    /**
     * 直退辅数量
     */
    private String auxdirectnumdetail;
    
    /**
     * 退货辅数量(整)
     */
    private BigDecimal auxchecknum;
    
    /**
     * 退货辅数量（散）
     */
    private BigDecimal auxremainderchecknum;
    
    /**
     * 退货辅数量
     */
    private String auxchecknumdetail;
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
        this.businessdate = businessdate == null ? null : businessdate.trim();
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
        this.customerid = customerid == null ? null : customerid.trim();
    }

    /**
     * @return 发货数量
     */
    public BigDecimal getSendnum() {
        return sendnum;
    }

    /**
     * @param sendnum 
	 *            发货数量
     */
    public void setSendnum(BigDecimal sendnum) {
        this.sendnum = sendnum;
    }

    /**
     * @return 发货金额
     */
    public BigDecimal getSendamount() {
        return sendamount;
    }

    /**
     * @param sendamount 
	 *            发货金额
     */
    public void setSendamount(BigDecimal sendamount) {
        this.sendamount = sendamount;
    }

    /**
     * @return 无税发货金额
     */
    public BigDecimal getSendnotaxamount() {
        return sendnotaxamount;
    }

    /**
     * @param sendnotaxamount 
	 *            无税发货金额
     */
    public void setSendnotaxamount(BigDecimal sendnotaxamount) {
        this.sendnotaxamount = sendnotaxamount;
    }

    /**
     * @return 退货数量
     */
    public BigDecimal getReturnnum() {
        return returnnum;
    }

    /**
     * @param returnnum 
	 *            退货数量
     */
    public void setReturnnum(BigDecimal returnnum) {
        this.returnnum = returnnum;
    }

    /**
     * @return 退货金额
     */
    public BigDecimal getReturnamount() {
        return returnamount;
    }

    /**
     * @param returnamount 
	 *            退货金额
     */
    public void setReturnamount(BigDecimal returnamount) {
        this.returnamount = returnamount;
    }

    /**
     * @return 无税退货金额
     */
    public BigDecimal getReturnnotaxamount() {
        return returnnotaxamount;
    }

    /**
     * @param returnnotaxamount 
	 *            无税退货金额
     */
    public void setReturnnotaxamount(BigDecimal returnnotaxamount) {
        this.returnnotaxamount = returnnotaxamount;
    }

    /**
     * @return 直退数量
     */
    public BigDecimal getDirectreturnnum() {
        return directreturnnum;
    }

    /**
     * @param directreturnnum 
	 *            直退数量
     */
    public void setDirectreturnnum(BigDecimal directreturnnum) {
        this.directreturnnum = directreturnnum;
    }

    /**
     * @return 直退金额
     */
    public BigDecimal getDirectreturnamount() {
        return directreturnamount;
    }

    /**
     * @param directreturnamount 
	 *            直退金额
     */
    public void setDirectreturnamount(BigDecimal directreturnamount) {
        this.directreturnamount = directreturnamount;
    }

    /**
     * @return 无税直退金额
     */
    public BigDecimal getDirectreturnnotaxamount() {
        return directreturnnotaxamount;
    }

    /**
     * @param directreturnnotaxamount 
	 *            无税直退金额
     */
    public void setDirectreturnnotaxamount(BigDecimal directreturnnotaxamount) {
        this.directreturnnotaxamount = directreturnnotaxamount;
    }

    /**
     * @return 验收退货数量
     */
    public BigDecimal getCheckreturnnum() {
        return checkreturnnum;
    }

    /**
     * @param checkreturnnum 
	 *            验收退货数量
     */
    public void setCheckreturnnum(BigDecimal checkreturnnum) {
        this.checkreturnnum = checkreturnnum;
    }

    /**
     * @return 验收退货金额
     */
    public BigDecimal getCheckreturnamount() {
        return checkreturnamount;
    }

    /**
     * @param checkreturnamount 
	 *            验收退货金额
     */
    public void setCheckreturnamount(BigDecimal checkreturnamount) {
        this.checkreturnamount = checkreturnamount;
    }

    /**
     * @return 无税售后退货金额
     */
    public BigDecimal getCheckreturnnotaxamount() {
        return checkreturnnotaxamount;
    }

    /**
     * @param checkreturnnotaxamount 
	 *            无税售后退货金额
     */
    public void setCheckreturnnotaxamount(BigDecimal checkreturnnotaxamount) {
        this.checkreturnnotaxamount = checkreturnnotaxamount;
    }

    /**
     * @return 销售额
     */
    public BigDecimal getSaleamount() {
        return saleamount;
    }

    /**
     * @param saleamount 
	 *            销售额
     */
    public void setSaleamount(BigDecimal saleamount) {
        this.saleamount = saleamount;
    }

    /**
     * @return 无税销售金额
     */
    public BigDecimal getSalenotaxamount() {
        return salenotaxamount;
    }

    /**
     * @param salenotaxamount 
	 *            无税销售金额
     */
    public void setSalenotaxamount(BigDecimal salenotaxamount) {
        this.salenotaxamount = salenotaxamount;
    }

    /**
     * @return 添加时间
     */
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

	public BigDecimal getCostamount() {
		if(null!=costamount && costamount.compareTo(BigDecimal.ZERO)==0){
			return BigDecimal.ZERO;
		}else{
			return costamount;
		}
	}

	public void setCostamount(BigDecimal costamount) {
		this.costamount = costamount;
	}

	public BigDecimal getRealrate() {
		return realrate;
	}

	public void setRealrate(BigDecimal realrate) {
		this.realrate = realrate;
	}

	public BigDecimal getPlanrate() {
		return planrate;
	}

	public void setPlanrate(BigDecimal planrate) {
		this.planrate = planrate;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public BigDecimal getSaletax() {
		return saletax;
	}

	public void setSaletax(BigDecimal saletax) {
		this.saletax = saletax;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getPcustomerid() {
		return pcustomerid;
	}

	public void setPcustomerid(String pcustomerid) {
		this.pcustomerid = pcustomerid;
	}

	public String getPcustomername() {
		return pcustomername;
	}

	public void setPcustomername(String pcustomername) {
		this.pcustomername = pcustomername;
	}

	public BigDecimal getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(BigDecimal ordernum) {
		this.ordernum = ordernum;
	}

	public BigDecimal getOrderamount() {
		return orderamount;
	}

	public void setOrderamount(BigDecimal orderamount) {
		this.orderamount = orderamount;
	}

	public BigDecimal getOrdernotaxamount() {
		return ordernotaxamount;
	}

	public void setOrdernotaxamount(BigDecimal ordernotaxamount) {
		this.ordernotaxamount = ordernotaxamount;
	}

	public BigDecimal getInitsendnum() {
		return initsendnum;
	}

	public void setInitsendnum(BigDecimal initsendnum) {
		this.initsendnum = initsendnum;
	}

	public BigDecimal getInitsendamount() {
		return initsendamount;
	}

	public void setInitsendamount(BigDecimal initsendamount) {
		this.initsendamount = initsendamount;
	}

	public BigDecimal getInitsendnotaxamount() {
		return initsendnotaxamount;
	}

	public void setInitsendnotaxamount(BigDecimal initsendnotaxamount) {
		this.initsendnotaxamount = initsendnotaxamount;
	}

	public String getSalesdept() {
		return salesdept;
	}

	public void setSalesdept(String salesdept) {
		this.salesdept = salesdept;
	}

	public String getSalesdeptname() {
		return salesdeptname;
	}

	public void setSalesdeptname(String salesdeptname) {
		this.salesdeptname = salesdeptname;
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

	public BigDecimal getWriteoffamount() {
		return writeoffamount;
	}

	public void setWriteoffamount(BigDecimal writeoffamount) {
		this.writeoffamount = writeoffamount;
	}

	public BigDecimal getCostwriteoffamount() {
		return costwriteoffamount;
	}

	public void setCostwriteoffamount(BigDecimal costwriteoffamount) {
		this.costwriteoffamount = costwriteoffamount;
	}

	public BigDecimal getWriteoffrate() {
		return writeoffrate;
	}

	public void setWriteoffrate(BigDecimal writeoffrate) {
		this.writeoffrate = writeoffrate;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getSalesusername() {
		return salesusername;
	}

	public void setSalesusername(String salesusername) {
		this.salesusername = salesusername;
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public String getSalesuser() {
		return salesuser;
	}

	public void setSalesuser(String salesuser) {
		this.salesuser = salesuser;
	}

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getBranduser() {
		return branduser;
	}

	public void setBranduser(String branduser) {
		this.branduser = branduser;
	}

	public String getBrandusername() {
		return brandusername;
	}

	public void setBrandusername(String brandusername) {
		this.brandusername = brandusername;
	}

	public String getBranddept() {
		return branddept;
	}

	public void setBranddept(String branddept) {
		this.branddept = branddept;
	}

	public String getBranddeptname() {
		return branddeptname;
	}

	public void setBranddeptname(String branddeptname) {
		this.branddeptname = branddeptname;
	}

	public BigDecimal getCheckreturnrate() {
		return checkreturnrate;
	}

	public void setCheckreturnrate(BigDecimal checkreturnrate) {
		this.checkreturnrate = checkreturnrate;
	}

	public String getTreeid() {
		return treeid;
	}

	public void setTreeid(String treeid) {
		this.treeid = treeid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public BigDecimal getSalemarginamount() {
		return salemarginamount;
	}

	public void setSalemarginamount(BigDecimal salemarginamount) {
		this.salemarginamount = salemarginamount;
	}

	public BigDecimal getSendcostamount() {
		return sendcostamount;
	}

	public void setSendcostamount(BigDecimal sendcostamount) {
		this.sendcostamount = sendcostamount;
	}

	public BigDecimal getCostprice() {
		return costprice;
	}

	public void setCostprice(BigDecimal costprice) {
		this.costprice = costprice;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCustomersort() {
		return customersort;
	}

	public void setCustomersort(String customersort) {
		this.customersort = customersort;
	}

	public String getCustomersortname() {
		return customersortname;
	}

	public void setCustomersortname(String customersortname) {
		this.customersortname = customersortname;
	}

	public String getUnitid() {
		return unitid;
	}

	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}

	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public String getAuxunitid() {
		return auxunitid;
	}

	public void setAuxunitid(String auxunitid) {
		this.auxunitid = auxunitid;
	}

	public String getAuxunitname() {
		return auxunitname;
	}

	public void setAuxunitname(String auxunitname) {
		this.auxunitname = auxunitname;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getShortcode() {
		return shortcode;
	}

	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}

	public String getSpell() {
		return spell;
	}

	public void setSpell(String spell) {
		this.spell = spell;
	}

	public BigDecimal getSalenum() {
		return salenum;
	}

	public void setSalenum(BigDecimal salenum) {
		this.salenum = salenum;
	}

	public BigDecimal getWriteoffmarginamount() {
		return writeoffmarginamount;
	}

	public void setWriteoffmarginamount(BigDecimal writeoffmarginamount) {
		this.writeoffmarginamount = writeoffmarginamount;
	}

	public BigDecimal getPushbalanceamount() {
		return pushbalanceamount;
	}

	public void setPushbalanceamount(BigDecimal pushbalanceamount) {
		this.pushbalanceamount = pushbalanceamount;
	}

	public String getGoodssort() {
		return goodssort;
	}

	public void setGoodssort(String goodssort) {
		this.goodssort = goodssort;
	}

	public String getGoodssortname() {
		return goodssortname;
	}

	public void setGoodssortname(String goodssortname) {
		this.goodssortname = goodssortname;
	}

	public String getGoodstype() {
		return goodstype;
	}

	public void setGoodstype(String goodstype) {
		this.goodstype = goodstype;
	}

	public String getGoodstypename() {
		return goodstypename;
	}

	public void setGoodstypename(String goodstypename) {
		this.goodstypename = goodstypename;
	}

	public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}

	public String getSupplieruser() {
		return supplieruser;
	}

	public void setSupplieruser(String supplieruser) {
		this.supplieruser = supplieruser;
	}

	public String getSupplierusername() {
		return supplierusername;
	}

	public void setSupplierusername(String supplierusername) {
		this.supplierusername = supplierusername;
	}

	public String getAdduserid() {
		return adduserid;
	}

	public void setAdduserid(String adduserid) {
		this.adduserid = adduserid;
	}

	public String getAddusername() {
		return addusername;
	}

	public void setAddusername(String addusername) {
		this.addusername = addusername;
	}

	public String getDriverid() {
		return driverid;
	}

	public void setDriverid(String driverid) {
		this.driverid = driverid;
	}

	public String getDrivername() {
		return drivername;
	}

	public void setDrivername(String drivername) {
		this.drivername = drivername;
	}

	public BigDecimal getAuxreturnnum() {
		return auxreturnnum;
	}

	public void setAuxreturnnum(BigDecimal auxreturnnum) {
		this.auxreturnnum = auxreturnnum;
	}

	public BigDecimal getAuxremainderreturnnum() {
		return auxremainderreturnnum;
	}

	public void setAuxremainderreturnnum(BigDecimal auxremainderreturnnum) {
		this.auxremainderreturnnum = auxremainderreturnnum;
	}

	public BigDecimal getAuxdirectnum() {
		return auxdirectnum;
	}

	public void setAuxdirectnum(BigDecimal auxdirectnum) {
		this.auxdirectnum = auxdirectnum;
	}

	public BigDecimal getAuxremainderdirectnum() {
		return auxremainderdirectnum;
	}

	public void setAuxremainderdirectnum(BigDecimal auxremainderdirectnum) {
		this.auxremainderdirectnum = auxremainderdirectnum;
	}

	public BigDecimal getAuxchecknum() {
		return auxchecknum;
	}

	public void setAuxchecknum(BigDecimal auxchecknum) {
		this.auxchecknum = auxchecknum;
	}

	public BigDecimal getAuxremainderchecknum() {
		return auxremainderchecknum;
	}

	public void setAuxremainderchecknum(BigDecimal auxremainderchecknum) {
		this.auxremainderchecknum = auxremainderchecknum;
	}

	public String getAuxreturnnumdetail() {
		return auxreturnnumdetail;
	}

	public void setAuxreturnnumdetail(String auxreturnnumdetail) {
		this.auxreturnnumdetail = auxreturnnumdetail;
	}

	public String getAuxdirectnumdetail() {
		return auxdirectnumdetail;
	}

	public void setAuxdirectnumdetail(String auxdirectnumdetail) {
		this.auxdirectnumdetail = auxdirectnumdetail;
	}

	public String getAuxchecknumdetail() {
		return auxchecknumdetail;
	}

	public void setAuxchecknumdetail(String auxchecknumdetail) {
		this.auxchecknumdetail = auxchecknumdetail;
	}

	public BigDecimal getOrdertotalbox() {
		return ordertotalbox;
	}

	public void setOrdertotalbox(BigDecimal ordertotalbox) {
		this.ordertotalbox = ordertotalbox;
	}

	public BigDecimal getInitsendtotalbox() {
		return initsendtotalbox;
	}

	public void setInitsendtotalbox(BigDecimal initsendtotalbox) {
		this.initsendtotalbox = initsendtotalbox;
	}

	public BigDecimal getSendtotalbox() {
		return sendtotalbox;
	}

	public void setSendtotalbox(BigDecimal sendtotalbox) {
		this.sendtotalbox = sendtotalbox;
	}

	public BigDecimal getReturntotalbox() {
		return returntotalbox;
	}

	public void setReturntotalbox(BigDecimal returntotalbox) {
		this.returntotalbox = returntotalbox;
	}

	public BigDecimal getDirectreturntotalbox() {
		return directreturntotalbox;
	}

	public void setDirectreturntotalbox(BigDecimal directreturntotalbox) {
		this.directreturntotalbox = directreturntotalbox;
	}

	public BigDecimal getCheckreturntotalbox() {
		return checkreturntotalbox;
	}

	public void setCheckreturntotalbox(BigDecimal checkreturntotalbox) {
		this.checkreturntotalbox = checkreturntotalbox;
	}

	public BigDecimal getSaletotalbox() {
		return saletotalbox;
	}

	public void setSaletotalbox(BigDecimal saletotalbox) {
		this.saletotalbox = saletotalbox;
	}

    public BigDecimal getPushbalancenotaxamount() {
        return pushbalancenotaxamount;
    }

    public void setPushbalancenotaxamount(BigDecimal pushbalancenotaxamount) {
        this.pushbalancenotaxamount = pushbalancenotaxamount;
    }

    public String getBranduserdeptname() {
        return branduserdeptname;
    }

    public void setBranduserdeptname(String branduserdeptname) {
        this.branduserdeptname = branduserdeptname;
    }

    public String getBranduserdept() {
        return branduserdept;
    }

    public void setBranduserdept(String branduserdept) {
        this.branduserdept = branduserdept;
    }

    public String getStoragename() {
        return storagename;
    }

    public void setStoragename(String storagename) {
        this.storagename = storagename;
    }

    public String getStorageid() {
        return storageid;
    }

    public void setStorageid(String storageid) {
        this.storageid = storageid;
    }

    public BigDecimal getBoxnum() {
        return boxnum;
    }

    public void setBoxnum(BigDecimal boxnum) {
        this.boxnum = boxnum;
    }
}