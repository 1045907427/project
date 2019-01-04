/**
 * @(#)SaleMonthReport.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016年1月13日 huangzhiqian 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;


/**
 * 
 * 分月汇总销售报表
 * @author huangzhiqian
 */
public class SaleMonthReport implements Serializable{
	private static final long serialVersionUID = 1L;
	private String month;
	private String customerid;
	private String pcustomerid;
	private String customersort;
	private String salesarea;
	private String salesdept;
	private String salesuser;
	private String goodssort;
	private String supplierid;
	private String adduserid;
	private String addusername;
	private String goodsid;
	private String brandid;
	private String branduser;
	private String supplieruser;
	private String branddept;
	private String unitid;
	private String unitname;
	private String auxunitid;
	private String auxunitname;
	private String barcode;
	private String goodsname;
	private String customername;
	private String pcustomername;
	private String salesdeptname;
	private String customersortname;
	private String salesareaname;
	private String salesusername;
	private String supplierusername;
	private String brandusername;
	private String branddeptname;
	private String brandname;
	private String goodssortname;
	private String suppliername;
	private String goodstype;
	private String goodstypename;
	
	public String getGoodstypename() {
		return goodstypename;
	}
	public void setGoodstypename(String goodstypename) {
		this.goodstypename = goodstypename;
	}
	public String getGoodstype() {
		return goodstype;
	}
	public void setGoodstype(String goodstype) {
		this.goodstype = goodstype;
	}
	public String getSuppliername() {
		return suppliername;
	}
	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
	public String getGoodssortname() {
		return goodssortname;
	}
	public void setGoodssortname(String goodssortname) {
		this.goodssortname = goodssortname;
	}
	public String getBrandname() {
		return brandname;
	}
	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}
	public String getBranddeptname() {
		return branddeptname;
	}
	public void setBranddeptname(String branddeptname) {
		this.branddeptname = branddeptname;
	}
	public String getBrandusername() {
		return brandusername;
	}
	public void setBrandusername(String brandusername) {
		this.brandusername = brandusername;
	}
	public String getSalesareaname() {
		return salesareaname;
	}
	public void setSalesareaname(String salesareaname) {
		this.salesareaname = salesareaname;
	}
	public String getSalesusername() {
		return salesusername;
	}
	public void setSalesusername(String salesusername) {
		this.salesusername = salesusername;
	}
	public String getSupplierusername() {
		return supplierusername;
	}
	public void setSupplierusername(String supplierusername) {
		this.supplierusername = supplierusername;
	}
	public String getCustomersortname() {
		return customersortname;
	}
	public void setCustomersortname(String customersortname) {
		this.customersortname = customersortname;
	}
	public String getSalesdeptname() {
		return salesdeptname;
	}
	public void setSalesdeptname(String salesdeptname) {
		this.salesdeptname = salesdeptname;
	}
	public String getPcustomername() {
		return pcustomername;
	}
	public void setPcustomername(String pcustomername) {
		this.pcustomername = pcustomername;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	private BigDecimal costamount01;
	private BigDecimal costamount02;
	private BigDecimal costamount03;
	private BigDecimal costamount04;
	private BigDecimal costamount05;
	private BigDecimal costamount06;
	private BigDecimal costamount07;
	private BigDecimal costamount08;
	private BigDecimal costamount09;
	private BigDecimal costamount10;
	private BigDecimal costamount11;
	private BigDecimal costamount12;

	private BigDecimal sendamount01;
	private BigDecimal sendamount02;
	private BigDecimal sendamount03;
	private BigDecimal sendamount04;
	private BigDecimal sendamount05;
	private BigDecimal sendamount06;
	private BigDecimal sendamount07;
	private BigDecimal sendamount08;
	private BigDecimal sendamount09;
	private BigDecimal sendamount10;
	private BigDecimal sendamount11;
	private BigDecimal sendamount12;


	private BigDecimal returnamount01;
	private BigDecimal returnamount02;
	private BigDecimal returnamount03;
	private BigDecimal returnamount04;
	private BigDecimal returnamount05;
	private BigDecimal returnamount06;
	private BigDecimal returnamount07;
	private BigDecimal returnamount08;
	private BigDecimal returnamount09;
	private BigDecimal returnamount10;
	private BigDecimal returnamount11;
	private BigDecimal returnamount12;

	private BigDecimal pushbalanceamount01;	
	private BigDecimal pushbalanceamount02;	
	private BigDecimal pushbalanceamount03;	
	private BigDecimal pushbalanceamount04;	
	private BigDecimal pushbalanceamount05;	
	private BigDecimal pushbalanceamount06;	
	private BigDecimal pushbalanceamount07;	
	private BigDecimal pushbalanceamount08;	
	private BigDecimal pushbalanceamount09;	
	private BigDecimal pushbalanceamount10;	
	private BigDecimal pushbalanceamount11;	
	private BigDecimal pushbalanceamount12;	

	
	
	//销售额
	private BigDecimal saleamount01;
	private BigDecimal saleamount02;
	private BigDecimal saleamount03;
	private BigDecimal saleamount04;
	private BigDecimal saleamount05;
	private BigDecimal saleamount06;
	private BigDecimal saleamount07;
	private BigDecimal saleamount08;
	private BigDecimal saleamount09;
	private BigDecimal saleamount10;
	private BigDecimal saleamount11;
	private BigDecimal saleamount12;
	//销售毛利率
	private BigDecimal realrate01;
	private BigDecimal realrate02;
	private BigDecimal realrate03;
	private BigDecimal realrate04;
	private BigDecimal realrate05;
	private BigDecimal realrate06;
	private BigDecimal realrate07;
	private BigDecimal realrate08;
	private BigDecimal realrate09;
	private BigDecimal realrate10;
	private BigDecimal realrate11;
	private BigDecimal realrate12;

	//销售箱数
	private BigDecimal saleboxnum01;
	private BigDecimal saleboxnum02;
	private BigDecimal saleboxnum03;
	private BigDecimal saleboxnum04;
	private BigDecimal saleboxnum05;
	private BigDecimal saleboxnum06;
	private BigDecimal saleboxnum07;
	private BigDecimal saleboxnum08;
	private BigDecimal saleboxnum09;
	private BigDecimal saleboxnum10;
	private BigDecimal saleboxnum11;
	private BigDecimal saleboxnum12;

	//销售毛利额
	private BigDecimal realsaleamount01;
	private BigDecimal realsaleamount02;
	private BigDecimal realsaleamount03;
	private BigDecimal realsaleamount04;
	private BigDecimal realsaleamount05;
	private BigDecimal realsaleamount06;
	private BigDecimal realsaleamount07;
	private BigDecimal realsaleamount08;
	private BigDecimal realsaleamount09;
	private BigDecimal realsaleamount10;
	private BigDecimal realsaleamount11;
	private BigDecimal realsaleamount12;
	
	
	public BigDecimal getRealrate01() {
		return realrate01;
	}
	public void setRealrate01(BigDecimal realrate01) {
		this.realrate01 = realrate01;
	}
	public BigDecimal getRealrate02() {
		return realrate02;
	}
	public void setRealrate02(BigDecimal realrate02) {
		this.realrate02 = realrate02;
	}
	public BigDecimal getRealrate03() {
		return realrate03;
	}
	public void setRealrate03(BigDecimal realrate03) {
		this.realrate03 = realrate03;
	}
	public BigDecimal getRealrate04() {
		return realrate04;
	}
	public void setRealrate04(BigDecimal realrate04) {
		this.realrate04 = realrate04;
	}
	public BigDecimal getRealrate05() {
		return realrate05;
	}
	public void setRealrate05(BigDecimal realrate05) {
		this.realrate05 = realrate05;
	}
	public BigDecimal getRealrate06() {
		return realrate06;
	}
	public void setRealrate06(BigDecimal realrate06) {
		this.realrate06 = realrate06;
	}
	public BigDecimal getRealrate07() {
		return realrate07;
	}
	public void setRealrate07(BigDecimal realrate07) {
		this.realrate07 = realrate07;
	}
	public BigDecimal getRealrate08() {
		return realrate08;
	}
	public void setRealrate08(BigDecimal realrate08) {
		this.realrate08 = realrate08;
	}
	public BigDecimal getRealrate09() {
		return realrate09;
	}
	public void setRealrate09(BigDecimal realrate09) {
		this.realrate09 = realrate09;
	}
	public BigDecimal getRealrate10() {
		return realrate10;
	}
	public void setRealrate10(BigDecimal realrate10) {
		this.realrate10 = realrate10;
	}
	public BigDecimal getRealrate11() {
		return realrate11;
	}
	public void setRealrate11(BigDecimal realrate11) {
		this.realrate11 = realrate11;
	}
	public BigDecimal getRealrate12() {
		return realrate12;
	}
	public void setRealrate12(BigDecimal realrate12) {
		this.realrate12 = realrate12;
	}
	public BigDecimal getSaleamount01() {
		return saleamount01;
	}
	public void setSaleamount01(BigDecimal saleamount01) {
		this.saleamount01 = saleamount01;
	}
	public BigDecimal getSaleamount02() {
		return saleamount02;
	}
	public void setSaleamount02(BigDecimal saleamount02) {
		this.saleamount02 = saleamount02;
	}
	public BigDecimal getSaleamount03() {
		return saleamount03;
	}
	public void setSaleamount03(BigDecimal saleamount03) {
		this.saleamount03 = saleamount03;
	}
	public BigDecimal getSaleamount04() {
		return saleamount04;
	}
	public void setSaleamount04(BigDecimal saleamount04) {
		this.saleamount04 = saleamount04;
	}
	public BigDecimal getSaleamount05() {
		return saleamount05;
	}
	public void setSaleamount05(BigDecimal saleamount05) {
		this.saleamount05 = saleamount05;
	}
	public BigDecimal getSaleamount06() {
		return saleamount06;
	}
	public void setSaleamount06(BigDecimal saleamount06) {
		this.saleamount06 = saleamount06;
	}
	public BigDecimal getSaleamount07() {
		return saleamount07;
	}
	public void setSaleamount07(BigDecimal saleamount07) {
		this.saleamount07 = saleamount07;
	}
	public BigDecimal getSaleamount08() {
		return saleamount08;
	}
	public void setSaleamount08(BigDecimal saleamount08) {
		this.saleamount08 = saleamount08;
	}
	public BigDecimal getSaleamount09() {
		return saleamount09;
	}
	public void setSaleamount09(BigDecimal saleamount09) {
		this.saleamount09 = saleamount09;
	}
	public BigDecimal getSaleamount10() {
		return saleamount10;
	}
	public void setSaleamount10(BigDecimal saleamount10) {
		this.saleamount10 = saleamount10;
	}
	public BigDecimal getSaleamount11() {
		return saleamount11;
	}
	public void setSaleamount11(BigDecimal saleamount11) {
		this.saleamount11 = saleamount11;
	}
	public BigDecimal getSaleamount12() {
		return saleamount12;
	}
	public void setSaleamount12(BigDecimal saleamount12) {
		this.saleamount12 = saleamount12;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getCustomerid() {
		return customerid;
	}
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	public String getPcustomerid() {
		return pcustomerid;
	}
	public void setPcustomerid(String pcustomerid) {
		this.pcustomerid = pcustomerid;
	}
	public String getCustomersort() {
		return customersort;
	}
	public void setCustomersort(String customersort) {
		this.customersort = customersort;
	}
	public String getSalesarea() {
		return salesarea;
	}
	public void setSalesarea(String salesarea) {
		this.salesarea = salesarea;
	}
	public String getSalesdept() {
		return salesdept;
	}
	public void setSalesdept(String salesdept) {
		this.salesdept = salesdept;
	}
	public String getSalesuser() {
		return salesuser;
	}
	public void setSalesuser(String salesuser) {
		this.salesuser = salesuser;
	}
	public String getGoodssort() {
		return goodssort;
	}
	public void setGoodssort(String goodssort) {
		this.goodssort = goodssort;
	}
	public String getSupplierid() {
		return supplierid;
	}
	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
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
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public String getBrandid() {
		return brandid;
	}
	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}
	public String getBranduser() {
		return branduser;
	}
	public void setBranduser(String branduser) {
		this.branduser = branduser;
	}
	public String getSupplieruser() {
		return supplieruser;
	}
	public void setSupplieruser(String supplieruser) {
		this.supplieruser = supplieruser;
	}
	public String getBranddept() {
		return branddept;
	}
	public void setBranddept(String branddept) {
		this.branddept = branddept;
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
	public BigDecimal getCostamount01() {
		return costamount01;
	}
	public void setCostamount01(BigDecimal costamount01) {
		this.costamount01 = costamount01;
	}
	public BigDecimal getCostamount02() {
		return costamount02;
	}
	public void setCostamount02(BigDecimal costamount02) {
		this.costamount02 = costamount02;
	}
	public BigDecimal getCostamount03() {
		return costamount03;
	}
	public void setCostamount03(BigDecimal costamount03) {
		this.costamount03 = costamount03;
	}
	public BigDecimal getCostamount04() {
		return costamount04;
	}
	public void setCostamount04(BigDecimal costamount04) {
		this.costamount04 = costamount04;
	}
	public BigDecimal getCostamount05() {
		return costamount05;
	}
	public void setCostamount05(BigDecimal costamount05) {
		this.costamount05 = costamount05;
	}
	public BigDecimal getCostamount06() {
		return costamount06;
	}
	public void setCostamount06(BigDecimal costamount06) {
		this.costamount06 = costamount06;
	}
	public BigDecimal getCostamount07() {
		return costamount07;
	}
	public void setCostamount07(BigDecimal costamount07) {
		this.costamount07 = costamount07;
	}
	public BigDecimal getCostamount08() {
		return costamount08;
	}
	public void setCostamount08(BigDecimal costamount08) {
		this.costamount08 = costamount08;
	}
	public BigDecimal getCostamount09() {
		return costamount09;
	}
	public void setCostamount09(BigDecimal costamount09) {
		this.costamount09 = costamount09;
	}
	public BigDecimal getCostamount10() {
		return costamount10;
	}
	public void setCostamount10(BigDecimal costamount10) {
		this.costamount10 = costamount10;
	}
	public BigDecimal getCostamount11() {
		return costamount11;
	}
	public void setCostamount11(BigDecimal costamount11) {
		this.costamount11 = costamount11;
	}
	public BigDecimal getCostamount12() {
		return costamount12;
	}
	public void setCostamount12(BigDecimal costamount12) {
		this.costamount12 = costamount12;
	}
	public BigDecimal getSendamount01() {
		return sendamount01;
	}
	public void setSendamount01(BigDecimal sendamount01) {
		this.sendamount01 = sendamount01;
	}
	public BigDecimal getSendamount02() {
		return sendamount02;
	}
	public void setSendamount02(BigDecimal sendamount02) {
		this.sendamount02 = sendamount02;
	}
	public BigDecimal getSendamount03() {
		return sendamount03;
	}
	public void setSendamount03(BigDecimal sendamount03) {
		this.sendamount03 = sendamount03;
	}
	public BigDecimal getSendamount04() {
		return sendamount04;
	}
	public void setSendamount04(BigDecimal sendamount04) {
		this.sendamount04 = sendamount04;
	}
	public BigDecimal getSendamount05() {
		return sendamount05;
	}
	public void setSendamount05(BigDecimal sendamount05) {
		this.sendamount05 = sendamount05;
	}
	public BigDecimal getSendamount06() {
		return sendamount06;
	}
	public void setSendamount06(BigDecimal sendamount06) {
		this.sendamount06 = sendamount06;
	}
	public BigDecimal getSendamount07() {
		return sendamount07;
	}
	public void setSendamount07(BigDecimal sendamount07) {
		this.sendamount07 = sendamount07;
	}
	public BigDecimal getSendamount08() {
		return sendamount08;
	}
	public void setSendamount08(BigDecimal sendamount08) {
		this.sendamount08 = sendamount08;
	}
	public BigDecimal getSendamount09() {
		return sendamount09;
	}
	public void setSendamount09(BigDecimal sendamount09) {
		this.sendamount09 = sendamount09;
	}
	public BigDecimal getSendamount10() {
		return sendamount10;
	}
	public void setSendamount10(BigDecimal sendamount10) {
		this.sendamount10 = sendamount10;
	}
	public BigDecimal getSendamount11() {
		return sendamount11;
	}
	public void setSendamount11(BigDecimal sendamount11) {
		this.sendamount11 = sendamount11;
	}
	public BigDecimal getSendamount12() {
		return sendamount12;
	}
	public void setSendamount12(BigDecimal sendamount12) {
		this.sendamount12 = sendamount12;
	}
	public BigDecimal getReturnamount01() {
		return returnamount01;
	}
	public void setReturnamount01(BigDecimal returnamount01) {
		this.returnamount01 = returnamount01;
	}
	public BigDecimal getReturnamount02() {
		return returnamount02;
	}
	public void setReturnamount02(BigDecimal returnamount02) {
		this.returnamount02 = returnamount02;
	}
	public BigDecimal getReturnamount03() {
		return returnamount03;
	}
	public void setReturnamount03(BigDecimal returnamount03) {
		this.returnamount03 = returnamount03;
	}
	public BigDecimal getReturnamount04() {
		return returnamount04;
	}
	public void setReturnamount04(BigDecimal returnamount04) {
		this.returnamount04 = returnamount04;
	}
	public BigDecimal getReturnamount05() {
		return returnamount05;
	}
	public void setReturnamount05(BigDecimal returnamount05) {
		this.returnamount05 = returnamount05;
	}
	public BigDecimal getReturnamount06() {
		return returnamount06;
	}
	public void setReturnamount06(BigDecimal returnamount06) {
		this.returnamount06 = returnamount06;
	}
	public BigDecimal getReturnamount07() {
		return returnamount07;
	}
	public void setReturnamount07(BigDecimal returnamount07) {
		this.returnamount07 = returnamount07;
	}
	public BigDecimal getReturnamount08() {
		return returnamount08;
	}
	public void setReturnamount08(BigDecimal returnamount08) {
		this.returnamount08 = returnamount08;
	}
	public BigDecimal getReturnamount09() {
		return returnamount09;
	}
	public void setReturnamount09(BigDecimal returnamount09) {
		this.returnamount09 = returnamount09;
	}
	public BigDecimal getReturnamount10() {
		return returnamount10;
	}
	public void setReturnamount10(BigDecimal returnamount10) {
		this.returnamount10 = returnamount10;
	}
	public BigDecimal getReturnamount11() {
		return returnamount11;
	}
	public void setReturnamount11(BigDecimal returnamount11) {
		this.returnamount11 = returnamount11;
	}
	public BigDecimal getReturnamount12() {
		return returnamount12;
	}
	public void setReturnamount12(BigDecimal returnamount12) {
		this.returnamount12 = returnamount12;
	}
	public BigDecimal getPushbalanceamount01() {
		return pushbalanceamount01;
	}
	public void setPushbalanceamount01(BigDecimal pushbalanceamount01) {
		this.pushbalanceamount01 = pushbalanceamount01;
	}
	public BigDecimal getPushbalanceamount02() {
		return pushbalanceamount02;
	}
	public void setPushbalanceamount02(BigDecimal pushbalanceamount02) {
		this.pushbalanceamount02 = pushbalanceamount02;
	}
	public BigDecimal getPushbalanceamount03() {
		return pushbalanceamount03;
	}
	public void setPushbalanceamount03(BigDecimal pushbalanceamount03) {
		this.pushbalanceamount03 = pushbalanceamount03;
	}
	public BigDecimal getPushbalanceamount04() {
		return pushbalanceamount04;
	}
	public void setPushbalanceamount04(BigDecimal pushbalanceamount04) {
		this.pushbalanceamount04 = pushbalanceamount04;
	}
	public BigDecimal getPushbalanceamount05() {
		return pushbalanceamount05;
	}
	public void setPushbalanceamount05(BigDecimal pushbalanceamount05) {
		this.pushbalanceamount05 = pushbalanceamount05;
	}
	public BigDecimal getPushbalanceamount06() {
		return pushbalanceamount06;
	}
	public void setPushbalanceamount06(BigDecimal pushbalanceamount06) {
		this.pushbalanceamount06 = pushbalanceamount06;
	}
	public BigDecimal getPushbalanceamount07() {
		return pushbalanceamount07;
	}
	public void setPushbalanceamount07(BigDecimal pushbalanceamount07) {
		this.pushbalanceamount07 = pushbalanceamount07;
	}
	public BigDecimal getPushbalanceamount08() {
		return pushbalanceamount08;
	}
	public void setPushbalanceamount08(BigDecimal pushbalanceamount08) {
		this.pushbalanceamount08 = pushbalanceamount08;
	}
	public BigDecimal getPushbalanceamount09() {
		return pushbalanceamount09;
	}
	public void setPushbalanceamount09(BigDecimal pushbalanceamount09) {
		this.pushbalanceamount09 = pushbalanceamount09;
	}
	public BigDecimal getPushbalanceamount10() {
		return pushbalanceamount10;
	}
	public void setPushbalanceamount10(BigDecimal pushbalanceamount10) {
		this.pushbalanceamount10 = pushbalanceamount10;
	}
	public BigDecimal getPushbalanceamount11() {
		return pushbalanceamount11;
	}
	public void setPushbalanceamount11(BigDecimal pushbalanceamount11) {
		this.pushbalanceamount11 = pushbalanceamount11;
	}
	public BigDecimal getPushbalanceamount12() {
		return pushbalanceamount12;
	}
	public void setPushbalanceamount12(BigDecimal pushbalanceamount12) {
		this.pushbalanceamount12 = pushbalanceamount12;
	}

	public BigDecimal getSaleboxnum01() {
		return saleboxnum01;
	}

	public void setSaleboxnum01(BigDecimal saleboxnum01) {
		this.saleboxnum01 = saleboxnum01;
	}

	public BigDecimal getSaleboxnum02() {
		return saleboxnum02;
	}

	public void setSaleboxnum02(BigDecimal saleboxnum02) {
		this.saleboxnum02 = saleboxnum02;
	}

	public BigDecimal getSaleboxnum03() {
		return saleboxnum03;
	}

	public void setSaleboxnum03(BigDecimal saleboxnum03) {
		this.saleboxnum03 = saleboxnum03;
	}

	public BigDecimal getSaleboxnum04() {
		return saleboxnum04;
	}

	public void setSaleboxnum04(BigDecimal saleboxnum04) {
		this.saleboxnum04 = saleboxnum04;
	}

	public BigDecimal getSaleboxnum05() {
		return saleboxnum05;
	}

	public void setSaleboxnum05(BigDecimal saleboxnum05) {
		this.saleboxnum05 = saleboxnum05;
	}

	public BigDecimal getSaleboxnum06() {
		return saleboxnum06;
	}

	public void setSaleboxnum06(BigDecimal saleboxnum06) {
		this.saleboxnum06 = saleboxnum06;
	}

	public BigDecimal getSaleboxnum07() {
		return saleboxnum07;
	}

	public void setSaleboxnum07(BigDecimal saleboxnum07) {
		this.saleboxnum07 = saleboxnum07;
	}

	public BigDecimal getSaleboxnum08() {
		return saleboxnum08;
	}

	public void setSaleboxnum08(BigDecimal saleboxnum08) {
		this.saleboxnum08 = saleboxnum08;
	}

	public BigDecimal getSaleboxnum09() {
		return saleboxnum09;
	}

	public void setSaleboxnum09(BigDecimal saleboxnum09) {
		this.saleboxnum09 = saleboxnum09;
	}

	public BigDecimal getSaleboxnum10() {
		return saleboxnum10;
	}

	public void setSaleboxnum10(BigDecimal saleboxnum10) {
		this.saleboxnum10 = saleboxnum10;
	}

	public BigDecimal getSaleboxnum11() {
		return saleboxnum11;
	}

	public void setSaleboxnum11(BigDecimal saleboxnum11) {
		this.saleboxnum11 = saleboxnum11;
	}

	public BigDecimal getSaleboxnum12() {
		return saleboxnum12;
	}

	public void setSaleboxnum12(BigDecimal saleboxnum12) {
		this.saleboxnum12 = saleboxnum12;
	}

	public BigDecimal getRealsaleamount01() {
		return realsaleamount01;
	}

	public void setRealsaleamount01(BigDecimal realsaleamount01) {
		this.realsaleamount01 = realsaleamount01;
	}

	public BigDecimal getRealsaleamount02() {
		return realsaleamount02;
	}

	public void setRealsaleamount02(BigDecimal realsaleamount02) {
		this.realsaleamount02 = realsaleamount02;
	}

	public BigDecimal getRealsaleamount03() {
		return realsaleamount03;
	}

	public void setRealsaleamount03(BigDecimal realsaleamount03) {
		this.realsaleamount03 = realsaleamount03;
	}

	public BigDecimal getRealsaleamount04() {
		return realsaleamount04;
	}

	public void setRealsaleamount04(BigDecimal realsaleamount04) {
		this.realsaleamount04 = realsaleamount04;
	}

	public BigDecimal getRealsaleamount05() {
		return realsaleamount05;
	}

	public void setRealsaleamount05(BigDecimal realsaleamount05) {
		this.realsaleamount05 = realsaleamount05;
	}

	public BigDecimal getRealsaleamount06() {
		return realsaleamount06;
	}

	public void setRealsaleamount06(BigDecimal realsaleamount06) {
		this.realsaleamount06 = realsaleamount06;
	}

	public BigDecimal getRealsaleamount07() {
		return realsaleamount07;
	}

	public void setRealsaleamount07(BigDecimal realsaleamount07) {
		this.realsaleamount07 = realsaleamount07;
	}

	public BigDecimal getRealsaleamount08() {
		return realsaleamount08;
	}

	public void setRealsaleamount08(BigDecimal realsaleamount08) {
		this.realsaleamount08 = realsaleamount08;
	}

	public BigDecimal getRealsaleamount09() {
		return realsaleamount09;
	}

	public void setRealsaleamount09(BigDecimal realsaleamount09) {
		this.realsaleamount09 = realsaleamount09;
	}

	public BigDecimal getRealsaleamount10() {
		return realsaleamount10;
	}

	public void setRealsaleamount10(BigDecimal realsaleamount10) {
		this.realsaleamount10 = realsaleamount10;
	}

	public BigDecimal getRealsaleamount11() {
		return realsaleamount11;
	}

	public void setRealsaleamount11(BigDecimal realsaleamount11) {
		this.realsaleamount11 = realsaleamount11;
	}

	public BigDecimal getRealsaleamount12() {
		return realsaleamount12;
	}

	public void setRealsaleamount12(BigDecimal realsaleamount12) {
		this.realsaleamount12 = realsaleamount12;
	}
}

