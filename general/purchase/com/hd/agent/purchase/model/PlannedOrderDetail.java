/**
 * @(#)PlannedOrderDetail.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-5-9 zhanghonghui 创建版本
 */
package com.hd.agent.purchase.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.hd.agent.basefiles.model.GoodsInfo;

/**
 * 采购计划单明细
 *
 * @author zhanghonghui
 */
public class PlannedOrderDetail implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3344288221136035031L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 采购计划单编号
     */
    private String orderid;

    /**
     * 商品编号
     */
    private String goodsid;
    /**
     * 商品档案
     */
    private GoodsInfo goodsInfo;

    /**
     * 计量单位编号
     */
    private String unitid;

    /**
     * 计量单位名称
     */
    private String unitname;

    /**
     * 数量
     */
    private BigDecimal unitnum;

    /**
     * 辅计量单位编号
     */
    private String auxunitid;

    /**
     * 辅计量单位名称
     */
    private String auxunitname;

    /**
     * 数量(辅计量)
     */
    private BigDecimal auxnum;

    /**
     * 辅计量单位数量描述
     */
    private String auxnumdetail;
    /**
     * 辅单位余数数量（主单位换算后剩余的数量）
     */
    private BigDecimal auxremainder;
    /**
     * 合计箱数
     */
    private BigDecimal totalbox;
    /**
     * 含税单价
     */
    private BigDecimal taxprice;
    /**
     * 含税箱价
     */
    private BigDecimal boxprice;
    /**
     * 含税金额
     */
    private BigDecimal taxamount;

    /**
     * 税种
     */
    private String taxtype;
    /**
     * 税种名称
     */
    private String taxtypename;

    /**
     * 无税单价
     */
    private BigDecimal notaxprice;
    /**
     * 未税箱价
     */
    private BigDecimal noboxprice;
    /**
     * 无税金额
     */
    private BigDecimal notaxamount;

    /**
     * 税额
     */
    private BigDecimal tax;

    /**
     * 备注
     */
    private String remark;

    /**
     * 要求到货日期
     */
    private String arrivedate;

    /**
     * 表体自定义项1
     */
    private String field01;

    /**
     * 表体自定义项2
     */
    private String field02;

    /**
     * 表体自定义项3
     */
    private String field03;

    /**
     * 表体自定义项4
     */
    private String field04;

    /**
     * 表体自定义项5
     */
    private String field05;

    /**
     * 表体自定义项6
     */
    private String field06;

    /**
     * 表体自定义项7
     */
    private String field07;

    /**
     * 表体自定义项8
     */
    private String field08;

    /**
     * 已下订单主单位数量
     */
    private BigDecimal orderunitnum;

    /**
     * 已下订单辅单位数量
     */
    private BigDecimal orderauxnum;
    /**
     * 已下订单含税金额
     */
    private BigDecimal ordertaxamount;

    /**
     * 未下订单主单位数量
     */
    private BigDecimal unorderunitnum;

    /**
     * 未下订单辅单位数量
     */
    private BigDecimal unorderauxnum;

    /**
     * 未下订单含税金额
     */
    private BigDecimal unordertaxamount;

    /**
     * 排序(订单明细显示序号)
     */
    private Integer seq;

    /**
     * @return 编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 采购计划单编号
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     * @param orderid
     *            采购计划单编号
     */
    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    /**
     * @return 商品编号
     */
    public String getGoodsid() {
        return goodsid;
    }

    /**
     * @param goodsid
     *            商品编号
     */
    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid == null ? null : goodsid.trim();
    }

    public GoodsInfo getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    /**
     * @return 计量单位编号
     */
    public String getUnitid() {
        return unitid;
    }

    /**
     * @param unitid
     *            计量单位编号
     */
    public void setUnitid(String unitid) {
        this.unitid = unitid == null ? null : unitid.trim();
    }

    /**
     * @return 计量单位名称
     */
    public String getUnitname() {
        return unitname;
    }

    /**
     * @param unitname
     *            计量单位名称
     */
    public void setUnitname(String unitname) {
        this.unitname = unitname == null ? null : unitname.trim();
    }

    /**
     * @return 数量
     */
    public BigDecimal getUnitnum() {
        return unitnum;
    }

    /**
     * @param unitnum
     *            数量
     */
    public void setUnitnum(BigDecimal unitnum) {
        this.unitnum = unitnum;
    }

    /**
     * @return 辅计量单位编号
     */
    public String getAuxunitid() {
        return auxunitid;
    }

    /**
     * @param auxunitid
     *            辅计量单位编号
     */
    public void setAuxunitid(String auxunitid) {
        this.auxunitid = auxunitid == null ? null : auxunitid.trim();
    }

    /**
     * @return 辅计量单位名称
     */
    public String getAuxunitname() {
        return auxunitname;
    }

    /**
     * @param auxunitname
     *            辅计量单位名称
     */
    public void setAuxunitname(String auxunitname) {
        this.auxunitname = auxunitname == null ? null : auxunitname.trim();
    }

    /**
     * @return 数量(辅计量)
     */
    public BigDecimal getAuxnum() {
        return auxnum;
    }

    /**
     * @param auxnum
     *            数量(辅计量)
     */
    public void setAuxnum(BigDecimal auxnum) {
        this.auxnum = auxnum;
    }

    /**
     * @return 辅计量单位数量描述
     */
    public String getAuxnumdetail() {
        return auxnumdetail;
    }

    /**
     * @param auxnumdetail
     *            辅计量单位数量描述
     */
    public void setAuxnumdetail(String auxnumdetail) {
        this.auxnumdetail = auxnumdetail == null ? null : auxnumdetail.trim();
    }

    public BigDecimal getAuxremainder() {
        return auxremainder;
    }

    public void setAuxremainder(BigDecimal auxremainder) {
        this.auxremainder = auxremainder;
    }

    /**
     * @return 含税单价
     */
    public BigDecimal getTaxprice() {
        return taxprice;
    }

    /**
     * @param taxprice
     *            含税单价
     */
    public void setTaxprice(BigDecimal taxprice) {
        this.taxprice = taxprice;
    }

    /**
     * @return 含税金额
     */
    public BigDecimal getTaxamount() {
        return taxamount;
    }

    /**
     * @param taxamount
     *            含税金额
     */
    public void setTaxamount(BigDecimal taxamount) {
        this.taxamount = taxamount;
    }

    /**
     * @return 税种
     */
    public String getTaxtype() {
        return taxtype;
    }

    /**
     * @param taxtype
     *            税种
     */
    public void setTaxtype(String taxtype) {
        this.taxtype = taxtype == null ? null : taxtype.trim();
    }

    public String getTaxtypename() {
        return taxtypename;
    }

    public void setTaxtypename(String taxtypename) {
        this.taxtypename = taxtypename;
    }

    /**
     * @return 无税单价
     */
    public BigDecimal getNotaxprice() {
        return notaxprice;
    }

    /**
     * @param notaxprice
     *            无税单价
     */
    public void setNotaxprice(BigDecimal notaxprice) {
        this.notaxprice = notaxprice;
    }

    /**
     * @return 无税金额
     */
    public BigDecimal getNotaxamount() {
        return notaxamount;
    }

    /**
     * @param notaxamount
     *            无税金额
     */
    public void setNotaxamount(BigDecimal notaxamount) {
        this.notaxamount = notaxamount;
    }

    /**
     * @return 税额
     */
    public BigDecimal getTax() {
        return tax;
    }

    /**
     * @param tax
     *            税额
     */
    public void setTax(BigDecimal tax) {
        this.tax = tax;
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
     * @return 要求到货日期
     */
    public String getArrivedate() {
        return arrivedate;
    }

    /**
     * @param arrivedate
     *            要求到货日期
     */
    public void setArrivedate(String arrivedate) {
        this.arrivedate = arrivedate;
    }

    /**
     * @return 表体自定义项1
     */
    public String getField01() {
        return field01;
    }

    /**
     * @param field01
     *            表体自定义项1
     */
    public void setField01(String field01) {
        this.field01 = field01 == null ? null : field01.trim();
    }

    /**
     * @return 表体自定义项2
     */
    public String getField02() {
        return field02;
    }

    /**
     * @param field02
     *            表体自定义项2
     */
    public void setField02(String field02) {
        this.field02 = field02 == null ? null : field02.trim();
    }

    /**
     * @return 表体自定义项3
     */
    public String getField03() {
        return field03;
    }

    /**
     * @param field03
     *            表体自定义项3
     */
    public void setField03(String field03) {
        this.field03 = field03 == null ? null : field03.trim();
    }

    /**
     * @return 表体自定义项4
     */
    public String getField04() {
        return field04;
    }

    /**
     * @param field04
     *            表体自定义项4
     */
    public void setField04(String field04) {
        this.field04 = field04 == null ? null : field04.trim();
    }

    /**
     * @return 表体自定义项5
     */
    public String getField05() {
        return field05;
    }

    /**
     * @param field05
     *            表体自定义项5
     */
    public void setField05(String field05) {
        this.field05 = field05 == null ? null : field05.trim();
    }

    /**
     * @return 表体自定义项6
     */
    public String getField06() {
        return field06;
    }

    /**
     * @param field06
     *            表体自定义项6
     */
    public void setField06(String field06) {
        this.field06 = field06 == null ? null : field06.trim();
    }

    /**
     * @return 表体自定义项7
     */
    public String getField07() {
        return field07;
    }

    /**
     * @param field07
     *            表体自定义项7
     */
    public void setField07(String field07) {
        this.field07 = field07 == null ? null : field07.trim();
    }

    /**
     * @return 表体自定义项8
     */
    public String getField08() {
        return field08;
    }

    /**
     * @param field08
     *            表体自定义项8
     */
    public void setField08(String field08) {
        this.field08 = field08 == null ? null : field08.trim();
    }

    /**
     * @return 已下订单主单位数量
     */
    public BigDecimal getOrderunitnum() {
        return orderunitnum;
    }

    /**
     * @param orderunitnum
     *            已下订单主单位数量
     */
    public void setOrderunitnum(BigDecimal orderunitnum) {
        this.orderunitnum = orderunitnum;
    }

    /**
     * @return 已下订单辅单位数量
     */
    public BigDecimal getOrderauxnum() {
        return orderauxnum;
    }

    /**
     * @param orderauxnum
     *            已下订单辅单位数量
     */
    public void setOrderauxnum(BigDecimal orderauxnum) {
        this.orderauxnum = orderauxnum;
    }

    /**
     * @return 已下订单含税金额
     */
    public BigDecimal getOrdertaxamount() {
        return ordertaxamount;
    }

    /**
     * @param ordertaxamount
     *            已下订单含税金额
     */
    public void setOrdertaxamount(BigDecimal ordertaxamount) {
        this.ordertaxamount = ordertaxamount;
    }

    /**
     * @return 未下订单主单位数量
     */
    public BigDecimal getUnorderunitnum() {
        return unorderunitnum;
    }

    /**
     * @param unorderunitnum
     *            未下订单主单位数量
     */
    public void setUnorderunitnum(BigDecimal unorderunitnum) {
        this.unorderunitnum = unorderunitnum;
    }

    /**
     * @return 未下订单辅单位数量
     */
    public BigDecimal getUnorderauxnum() {
        return unorderauxnum;
    }

    /**
     * @param unorderauxnum
     *            未下订单辅单位数量
     */
    public void setUnorderauxnum(BigDecimal unorderauxnum) {
        this.unorderauxnum = unorderauxnum;
    }

    /**
     * @return 未下订单含税金额
     */
    public BigDecimal getUnordertaxamount() {
        return unordertaxamount;
    }

    /**
     * @param unordertaxamount
     *            未下订单含税金额
     */
    public void setUnordertaxamount(BigDecimal unordertaxamount) {
        this.unordertaxamount = unordertaxamount;
    }

    /**
     * @return 排序(订单明细显示序号)
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * @param seq
     *            排序(订单明细显示序号)
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public BigDecimal getBoxprice() {
        return boxprice;
    }

    public void setBoxprice(BigDecimal boxprice) {
        this.boxprice = boxprice;
    }

    public BigDecimal getNoboxprice() {
        return noboxprice;
    }

    public void setNoboxprice(BigDecimal noboxprice) {
        this.noboxprice = noboxprice;
    }

    public BigDecimal getTotalbox() {
        return totalbox;
    }

    public void setTotalbox(BigDecimal totalbox) {
        this.totalbox = totalbox;
    }

}

