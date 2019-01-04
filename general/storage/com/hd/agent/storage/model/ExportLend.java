package com.hd.agent.storage.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 出入库明细对象
 * User: lin_xx
 * Date: 2016/2/17
 * Time: 15:19
 */
public class ExportLend implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    private String id ;
    /**
     * 业务日期
     */
    private String businessdate;
    /**
     * 出入库仓库编码
     */
    private String storageid;
    /**
     * 出入库仓库名称
     */
    private String storagename;
    /**
     *相关部门编码
     */
    private String deptid;
    /**
     *相关部门名称
     */
    private String deptname;
    /**
     * 借货还货人类型
     */
    private String lendtype;

    /**
     *借货还货人编码
     */
    private String lendid;
    /**
     *借货还货人
     */
    private String lendname;
    /**
     * 商品编码
     */
    private String goodsid;
    /**
     * 商品名称
     */
    private String goodsname;
    /**
     * 商品助记符
     */
    private String spell;

    /**
     * 商品条形码
     */
    private String barcode;
    /**
     * 主数量
     */
    private BigDecimal unitnum;

    /**
     * 辅数量描述
     */
    private String auxnumdetail;
    /**
     * 单价
     */
    private BigDecimal taxprice;
    /**
     * 含税金额
     */
    private BigDecimal taxamount;
    /**
     * 所属库位编码
     */
    private String storagelocationid;
    /**
     * 所属库位名称
     */
    private String storagelocationname;
    /**
     * 批次号
     */
    private String batchno;
    /**
     * 生产日期
     */
    private String produceddate;
    /**
     * 有效截止日期
     */
    private String deadline;
    /**
     * 备注
     */
    private String remark;


    private String lendtypename;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessdate() {
        return businessdate;
    }

    public void setBusinessdate(String businessdate) {
        this.businessdate = businessdate;
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

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getLendid() {
        return lendid;
    }

    public void setLendid(String lendid) {
        this.lendid = lendid;
    }

    public String getLendname() {
        return lendname;
    }

    public void setLendname(String lendname) {
        this.lendname = lendname;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public BigDecimal getUnitnum() {
        return unitnum;
    }

    public void setUnitnum(BigDecimal unitnum) {
        this.unitnum = unitnum;
    }

    public String getAuxnumdetail() {
        return auxnumdetail;
    }

    public void setAuxnumdetail(String auxnumdetail) {
        this.auxnumdetail = auxnumdetail;
    }

    public BigDecimal getTaxprice() {
        return taxprice;
    }

    public void setTaxprice(BigDecimal taxprice) {
        this.taxprice = taxprice;
    }

    public BigDecimal getTaxamount() {
        return taxamount;
    }

    public void setTaxamount(BigDecimal taxamount) {
        this.taxamount = taxamount;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getProduceddate() {
        return produceddate;
    }

    public void setProduceddate(String produceddate) {
        this.produceddate = produceddate;
    }

    public String getStoragelocationid() {
        return storagelocationid;
    }

    public void setStoragelocationid(String storagelocationid) {
        this.storagelocationid = storagelocationid;
    }

    public String getStoragelocationname() {
        return storagelocationname;
    }

    public void setStoragelocationname(String storagelocationname) {
        this.storagelocationname = storagelocationname;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLendtypename() {
        return lendtypename;
    }

    public void setLendtypename(String lendtypename) {
        this.lendtypename = lendtypename;
    }

    public String getLendtype() {
        return lendtype;
    }

    public void setLendtype(String lendtype) {
        this.lendtype = lendtype;
    }

}
