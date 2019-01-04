/**
 * @(#)MonthSaleWithdrawnReport.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016年1月14日 huangzhiqian 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.agent.basefiles.model.Brand;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.MeteringUnit;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.model.SalesArea;

/**
 * 
 * 按月资金回笼统计报表
 * @author huangzhiqian
 */
public class MonthSaleWithdrawnReport implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	private String customerid;
	private String pcustomerid;
	private String salesarea;
	private String salesdept;
	private String salesuser;
	private String goodsid;
	private String brandid;
	private String branduser;
	private String branddept;
	private String supplierid;
	private String supplieruser;
	
	
	private String barcode;
	private String customername;
	private String pcustomername;
	private String salesdeptname;
	private String salesareaname;
	private String salesusername;
	private String brandusername;
	private String branddeptname;
	private String goodsname;
	private BigDecimal auxnum;
	private String auxnumdetail;
	private String suppliername;
	private String srandname;
	private String supplierusername;
	private String brandname;
	private BigDecimal unitnum;
	
	//回笼金额
		private BigDecimal withdrawnamount01;
		private BigDecimal withdrawnamount02;
		private BigDecimal withdrawnamount03;
		private BigDecimal withdrawnamount04;
		private BigDecimal withdrawnamount05;
		private BigDecimal withdrawnamount06;
		private BigDecimal withdrawnamount07;
		private BigDecimal withdrawnamount08;
		private BigDecimal withdrawnamount09;
		private BigDecimal withdrawnamount10;
		private BigDecimal withdrawnamount11;
		private BigDecimal withdrawnamount12;
		
		//回笼成本金额
		private BigDecimal costwriteoffamount01;
		private BigDecimal costwriteoffamount02;
		private BigDecimal costwriteoffamount03;
		private BigDecimal costwriteoffamount04;
		private BigDecimal costwriteoffamount05;
		private BigDecimal costwriteoffamount06;
		private BigDecimal costwriteoffamount07;
		private BigDecimal costwriteoffamount08;
		private BigDecimal costwriteoffamount09;
		private BigDecimal costwriteoffamount10;
		private BigDecimal costwriteoffamount11;
		private BigDecimal costwriteoffamount12;
		
		//回笼毛利率
		private BigDecimal writeoffrate01;
		private BigDecimal writeoffrate02;
		private BigDecimal writeoffrate03;
		private BigDecimal writeoffrate04;
		private BigDecimal writeoffrate05;
		private BigDecimal writeoffrate06;
		private BigDecimal writeoffrate07;
		private BigDecimal writeoffrate08;
		private BigDecimal writeoffrate09;
		private BigDecimal writeoffrate10;
		private BigDecimal writeoffrate11;
		private BigDecimal writeoffrate12;

	
	
	
	public BigDecimal getUnitnum() {
		return unitnum;
	}
	public void setUnitnum(BigDecimal unitnum) {
		this.unitnum = unitnum;
	}
	public String getBrandname() {
		return brandname;
	}
	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}
	public String getSuppliername() {
		return suppliername;
	}
	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
	public String getSrandname() {
		return srandname;
	}
	public void setSrandname(String srandname) {
		this.srandname = srandname;
	}
	public String getSupplierusername() {
		return supplierusername;
	}
	public void setSupplierusername(String supplierusername) {
		this.supplierusername = supplierusername;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getPcustomername() {
		return pcustomername;
	}
	public void setPcustomername(String pcustomername) {
		this.pcustomername = pcustomername;
	}
	public String getSalesdeptname() {
		return salesdeptname;
	}
	public void setSalesdeptname(String salesdeptname) {
		this.salesdeptname = salesdeptname;
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
	public String getBrandusername() {
		return brandusername;
	}
	public void setBrandusername(String brandusername) {
		this.brandusername = brandusername;
	}
	public String getBranddeptname() {
		return branddeptname;
	}
	public void setBranddeptname(String branddeptname) {
		this.branddeptname = branddeptname;
	}
	public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	public BigDecimal getAuxnum() {
		return auxnum;
	}
	public void setAuxnum(BigDecimal auxnum) {
		this.auxnum = auxnum;
	}
	public String getAuxnumdetail() {
		return auxnumdetail;
	}
	public void setAuxnumdetail(String auxnumdetail) {
		this.auxnumdetail = auxnumdetail;
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
	public String getBranddept() {
		return branddept;
	}
	public void setBranddept(String branddept) {
		this.branddept = branddept;
	}
	public String getSupplierid() {
		return supplierid;
	}
	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}
	public String getSupplieruser() {
		return supplieruser;
	}
	public void setSupplieruser(String supplieruser) {
		this.supplieruser = supplieruser;
	}
	public BigDecimal getWithdrawnamount01() {
		return withdrawnamount01;
	}
	public void setWithdrawnamount01(BigDecimal withdrawnamount01) {
		this.withdrawnamount01 = withdrawnamount01;
	}
	public BigDecimal getWithdrawnamount02() {
		return withdrawnamount02;
	}
	public void setWithdrawnamount02(BigDecimal withdrawnamount02) {
		this.withdrawnamount02 = withdrawnamount02;
	}
	public BigDecimal getWithdrawnamount03() {
		return withdrawnamount03;
	}
	public void setWithdrawnamount03(BigDecimal withdrawnamount03) {
		this.withdrawnamount03 = withdrawnamount03;
	}
	public BigDecimal getWithdrawnamount04() {
		return withdrawnamount04;
	}
	public void setWithdrawnamount04(BigDecimal withdrawnamount04) {
		this.withdrawnamount04 = withdrawnamount04;
	}
	public BigDecimal getWithdrawnamount05() {
		return withdrawnamount05;
	}
	public void setWithdrawnamount05(BigDecimal withdrawnamount05) {
		this.withdrawnamount05 = withdrawnamount05;
	}
	public BigDecimal getWithdrawnamount06() {
		return withdrawnamount06;
	}
	public void setWithdrawnamount06(BigDecimal withdrawnamount06) {
		this.withdrawnamount06 = withdrawnamount06;
	}
	public BigDecimal getWithdrawnamount07() {
		return withdrawnamount07;
	}
	public void setWithdrawnamount07(BigDecimal withdrawnamount07) {
		this.withdrawnamount07 = withdrawnamount07;
	}
	public BigDecimal getWithdrawnamount08() {
		return withdrawnamount08;
	}
	public void setWithdrawnamount08(BigDecimal withdrawnamount08) {
		this.withdrawnamount08 = withdrawnamount08;
	}
	public BigDecimal getWithdrawnamount09() {
		return withdrawnamount09;
	}
	public void setWithdrawnamount09(BigDecimal withdrawnamount09) {
		this.withdrawnamount09 = withdrawnamount09;
	}
	public BigDecimal getWithdrawnamount10() {
		return withdrawnamount10;
	}
	public void setWithdrawnamount10(BigDecimal withdrawnamount10) {
		this.withdrawnamount10 = withdrawnamount10;
	}
	public BigDecimal getWithdrawnamount11() {
		return withdrawnamount11;
	}
	public void setWithdrawnamount11(BigDecimal withdrawnamount11) {
		this.withdrawnamount11 = withdrawnamount11;
	}
	public BigDecimal getWithdrawnamount12() {
		return withdrawnamount12;
	}
	public void setWithdrawnamount12(BigDecimal withdrawnamount12) {
		this.withdrawnamount12 = withdrawnamount12;
	}
	public BigDecimal getCostwriteoffamount01() {
		return costwriteoffamount01;
	}
	public void setCostwriteoffamount01(BigDecimal costwriteoffamount01) {
		this.costwriteoffamount01 = costwriteoffamount01;
	}
	public BigDecimal getCostwriteoffamount02() {
		return costwriteoffamount02;
	}
	public void setCostwriteoffamount02(BigDecimal costwriteoffamount02) {
		this.costwriteoffamount02 = costwriteoffamount02;
	}
	public BigDecimal getCostwriteoffamount03() {
		return costwriteoffamount03;
	}
	public void setCostwriteoffamount03(BigDecimal costwriteoffamount03) {
		this.costwriteoffamount03 = costwriteoffamount03;
	}
	public BigDecimal getCostwriteoffamount04() {
		return costwriteoffamount04;
	}
	public void setCostwriteoffamount04(BigDecimal costwriteoffamount04) {
		this.costwriteoffamount04 = costwriteoffamount04;
	}
	public BigDecimal getCostwriteoffamount05() {
		return costwriteoffamount05;
	}
	public void setCostwriteoffamount05(BigDecimal costwriteoffamount05) {
		this.costwriteoffamount05 = costwriteoffamount05;
	}
	public BigDecimal getCostwriteoffamount06() {
		return costwriteoffamount06;
	}
	public void setCostwriteoffamount06(BigDecimal costwriteoffamount06) {
		this.costwriteoffamount06 = costwriteoffamount06;
	}
	public BigDecimal getCostwriteoffamount07() {
		return costwriteoffamount07;
	}
	public void setCostwriteoffamount07(BigDecimal costwriteoffamount07) {
		this.costwriteoffamount07 = costwriteoffamount07;
	}
	public BigDecimal getCostwriteoffamount08() {
		return costwriteoffamount08;
	}
	public void setCostwriteoffamount08(BigDecimal costwriteoffamount08) {
		this.costwriteoffamount08 = costwriteoffamount08;
	}
	public BigDecimal getCostwriteoffamount09() {
		return costwriteoffamount09;
	}
	public void setCostwriteoffamount09(BigDecimal costwriteoffamount09) {
		this.costwriteoffamount09 = costwriteoffamount09;
	}
	public BigDecimal getCostwriteoffamount10() {
		return costwriteoffamount10;
	}
	public void setCostwriteoffamount10(BigDecimal costwriteoffamount10) {
		this.costwriteoffamount10 = costwriteoffamount10;
	}
	public BigDecimal getCostwriteoffamount11() {
		return costwriteoffamount11;
	}
	public void setCostwriteoffamount11(BigDecimal costwriteoffamount11) {
		this.costwriteoffamount11 = costwriteoffamount11;
	}
	public BigDecimal getCostwriteoffamount12() {
		return costwriteoffamount12;
	}
	public void setCostwriteoffamount12(BigDecimal costwriteoffamount12) {
		this.costwriteoffamount12 = costwriteoffamount12;
	}
	public BigDecimal getWriteoffrate01() {
		return writeoffrate01;
	}
	public void setWriteoffrate01(BigDecimal writeoffrate01) {
		this.writeoffrate01 = writeoffrate01;
	}
	public BigDecimal getWriteoffrate02() {
		return writeoffrate02;
	}
	public void setWriteoffrate02(BigDecimal writeoffrate02) {
		this.writeoffrate02 = writeoffrate02;
	}
	public BigDecimal getWriteoffrate03() {
		return writeoffrate03;
	}
	public void setWriteoffrate03(BigDecimal writeoffrate03) {
		this.writeoffrate03 = writeoffrate03;
	}
	public BigDecimal getWriteoffrate04() {
		return writeoffrate04;
	}
	public void setWriteoffrate04(BigDecimal writeoffrate04) {
		this.writeoffrate04 = writeoffrate04;
	}
	public BigDecimal getWriteoffrate05() {
		return writeoffrate05;
	}
	public void setWriteoffrate05(BigDecimal writeoffrate05) {
		this.writeoffrate05 = writeoffrate05;
	}
	public BigDecimal getWriteoffrate06() {
		return writeoffrate06;
	}
	public void setWriteoffrate06(BigDecimal writeoffrate06) {
		this.writeoffrate06 = writeoffrate06;
	}
	public BigDecimal getWriteoffrate07() {
		return writeoffrate07;
	}
	public void setWriteoffrate07(BigDecimal writeoffrate07) {
		this.writeoffrate07 = writeoffrate07;
	}
	public BigDecimal getWriteoffrate08() {
		return writeoffrate08;
	}
	public void setWriteoffrate08(BigDecimal writeoffrate08) {
		this.writeoffrate08 = writeoffrate08;
	}
	public BigDecimal getWriteoffrate09() {
		return writeoffrate09;
	}
	public void setWriteoffrate09(BigDecimal writeoffrate09) {
		this.writeoffrate09 = writeoffrate09;
	}
	public BigDecimal getWriteoffrate10() {
		return writeoffrate10;
	}
	public void setWriteoffrate10(BigDecimal writeoffrate10) {
		this.writeoffrate10 = writeoffrate10;
	}
	public BigDecimal getWriteoffrate11() {
		return writeoffrate11;
	}
	public void setWriteoffrate11(BigDecimal writeoffrate11) {
		this.writeoffrate11 = writeoffrate11;
	}
	public BigDecimal getWriteoffrate12() {
		return writeoffrate12;
	}
	public void setWriteoffrate12(BigDecimal writeoffrate12) {
		this.writeoffrate12 = writeoffrate12;
	}
	
}

