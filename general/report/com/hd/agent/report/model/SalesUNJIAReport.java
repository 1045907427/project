/**
 * @(#)SalesUNJIAReport.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Dec 31, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class SalesUNJIAReport implements Serializable{

	 private static final long serialVersionUID = 1L;
	 
	 /**
	  * 销售订单编码
	  */
	 private String id;
	 
	 /**
	  * 客户编码
	  */
	 private String customerid;
	 
	 /**
	  * 客户名称
	  */
	 private String customername;
	 
	 /**
	  * 客户助记符
	  */
	 private String shortcode;
	 
	 /**
	  * 时间
	  */
	 private String businessdate;
	 
	 /**
	  * CN01000111
	  */
	 private String field1;
	 
	 /**
	  * 1
	  */
	 private String field2;
	 
	 /**
	  * 尤妮佳大仓
	  */
	 private String field3;
	 
	 /**
	  * 0
	  */
	 private String field4;
	 
	 /**
	  * 7
	  */
	 private String field5;
	 
	 /**
	  * 商品编码
	  */
	 private String goodsid;
	 
	 /**
	  * 商品助记符
	  */
	 private String spell;
	 
	 /**
	  * 商品名称
	  */
	 private String goodsname;
	 
	 /**
	  * 数量
	  */
	 private BigDecimal unitnum;
	 
	 /**
	  * 单价：不含税
	  */
	 private BigDecimal notaxprice;
	 
	 /**
	  * 时间1
	  */
	 private String businessdate1;
	 
	 /**
	  * 126
	  */
	 private String field6;
	 
	 /**
	  * 时间2
	  */
	 private String businessdate2;
	 
	 /**
	  * 0
	  */
	 private String field7;
	 
	 /**
	  * 条形码(空)
	  */
	 private String barcode;
	 
	 /**
	  * 0
	  */
	 private String field8;
	 
	 /**
	  * 0
	  */
	 private String field9;
	 
	 /**
	  * 0
	  */
	 private String field10;
	 
	 /**
	  * 客户编码1
	  */
	 private String customerid1;
	 
	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getBusinessdate() {
		return businessdate;
	}

	public void setBusinessdate(String businessdate) {
		this.businessdate = businessdate;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public String getField3() {
		return field3;
	}

	public void setField3(String field3) {
		this.field3 = field3;
	}

	public String getField4() {
		return field4;
	}

	public void setField4(String field4) {
		this.field4 = field4;
	}

	public String getField5() {
		return field5;
	}

	public void setField5(String field5) {
		this.field5 = field5;
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public BigDecimal getUnitnum() {
		return unitnum;
	}

	public void setUnitnum(BigDecimal unitnum) {
		this.unitnum = unitnum;
	}

	public BigDecimal getNotaxprice() {
		return notaxprice;
	}

	public void setNotaxprice(BigDecimal notaxprice) {
		this.notaxprice = notaxprice;
	}

	public String getField6() {
		return field6;
	}

	public void setField6(String field6) {
		this.field6 = field6;
	}

	public String getField7() {
		return field7;
	}

	public void setField7(String field7) {
		this.field7 = field7;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getField8() {
		return field8;
	}

	public void setField8(String field8) {
		this.field8 = field8;
	}

	public String getField9() {
		return field9;
	}

	public void setField9(String field9) {
		this.field9 = field9;
	}

	public String getField10() {
		return field10;
	}

	public void setField10(String field10) {
		this.field10 = field10;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBusinessdate1() {
		return businessdate1;
	}

	public void setBusinessdate1(String businessdate1) {
		this.businessdate1 = businessdate1;
	}

	public String getBusinessdate2() {
		return businessdate2;
	}

	public void setBusinessdate2(String businessdate2) {
		this.businessdate2 = businessdate2;
	}

	public String getCustomerid1() {
		return customerid1;
	}

	public void setCustomerid1(String customerid1) {
		this.customerid1 = customerid1;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
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
}

