package com.hd.agent.storage.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.hd.agent.basefiles.model.GoodsInfo;

/**
 * 调拨入库单明细
 * @author chenwei
 */
public class AllocateEnterDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 调拨入库单编号
     */
    private String billno;

    /**
     * 来源单据编号
     */
    private String sourceid;

    /**
     * 来源单据明细编号
     */
    private String sourcedetailid;

    /**
     * 批次现存量编号
     */
    private String summarybatchid;

    /**
     * 商品编码
     */
    private String goodsid;
    /**
     * 商品详情
     */
    private GoodsInfo goodsInfo;
    /**
     * 收货仓库编码
     */
    private String storageid;
    /**
     * 仓库名称
     */
    private String storagename;
    /**
     * 库位
     */
    private String storagelocationid;
    /**
     * 库位名称
     */
    private String storagelocationname;
    /**
     * 批次号
     */
    private String batchno;

    /**
     * 主计量单位
     */
    private String unitid;

    /**
     * 主计量单位名称
     */
    private String unitname;

    /**
     * 数量
     */
    private BigDecimal unitnum;

    /**
     * 辅计量单位
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
     * 数量(辅计量显示)
     */
    private String auxnumdetail;
    /**
     * 合计箱数
     */
    private BigDecimal totalbox;
    /**
     * 含税单价
     */
    private BigDecimal taxprice;

    /**
     * 含税金额
     */
    private BigDecimal taxamount;

    /**
     * 无税单价
     */
    private BigDecimal notaxprice;

    /**
     * 无税金额
     */
    private BigDecimal notaxamount;

    /**
     * 税种
     */
    private String taxtype;
    /**
     * 税种名称
     */
    private String taxtypename;
    /**
     * 税额
     */
    private BigDecimal tax;

    /**
     * 备注
     */
    private String remark;

    /**
     * 生产日期
     */
    private String produceddate;

    /**
     * 有效截止日期
     */
    private String deadline;

    /**
     * 排序
     */
    private Integer seq;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 调拨入库单编号
     */
    public String getBillno() {
        return billno;
    }

    /**
     * @param billno 
	 *            调拨入库单编号
     */
    public void setBillno(String billno) {
        this.billno = billno == null ? null : billno.trim();
    }

    /**
     * @return 来源单据编号
     */
    public String getSourceid() {
        return sourceid;
    }

    /**
     * @param sourceid 
	 *            来源单据编号
     */
    public void setSourceid(String sourceid) {
        this.sourceid = sourceid == null ? null : sourceid.trim();
    }

    /**
     * @return 来源单据明细编号
     */
    public String getSourcedetailid() {
        return sourcedetailid;
    }

    /**
     * @param sourcedetailid 
	 *            来源单据明细编号
     */
    public void setSourcedetailid(String sourcedetailid) {
        this.sourcedetailid = sourcedetailid == null ? null : sourcedetailid.trim();
    }

    /**
     * @return 批次现存量编号
     */
    public String getSummarybatchid() {
        return summarybatchid;
    }

    /**
     * @param summarybatchid 
	 *            批次现存量编号
     */
    public void setSummarybatchid(String summarybatchid) {
        this.summarybatchid = summarybatchid == null ? null : summarybatchid.trim();
    }

    /**
     * @return 商品编码
     */
    public String getGoodsid() {
        return goodsid;
    }

    /**
     * @param goodsid 
	 *            商品编码
     */
    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid == null ? null : goodsid.trim();
    }

    /**
     * @return 发货仓库编码
     */
    public String getStorageid() {
        return storageid;
    }

    /**
     * @param storageid 
	 *            发货仓库编码
     */
    public void setStorageid(String storageid) {
        this.storageid = storageid == null ? null : storageid.trim();
    }

    /**
     * @return 库位
     */
    public String getStoragelocationid() {
        return storagelocationid;
    }

    /**
     * @param storagelocationid 
	 *            库位
     */
    public void setStoragelocationid(String storagelocationid) {
        this.storagelocationid = storagelocationid == null ? null : storagelocationid.trim();
    }

    /**
     * @return 批次号
     */
    public String getBatchno() {
        return batchno;
    }

    /**
     * @param batchno 
	 *            批次号
     */
    public void setBatchno(String batchno) {
        this.batchno = batchno == null ? null : batchno.trim();
    }

    /**
     * @return 主计量单位
     */
    public String getUnitid() {
        return unitid;
    }

    /**
     * @param unitid 
	 *            主计量单位
     */
    public void setUnitid(String unitid) {
        this.unitid = unitid == null ? null : unitid.trim();
    }

    /**
     * @return 主计量单位名称
     */
    public String getUnitname() {
        return unitname;
    }

    /**
     * @param unitname 
	 *            主计量单位名称
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
     * @return 辅计量单位
     */
    public String getAuxunitid() {
        return auxunitid;
    }

    /**
     * @param auxunitid 
	 *            辅计量单位
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
     * @return 数量(辅计量显示)
     */
    public String getAuxnumdetail() {
        return auxnumdetail;
    }

    /**
     * @param auxnumdetail 
	 *            数量(辅计量显示)
     */
    public void setAuxnumdetail(String auxnumdetail) {
        this.auxnumdetail = auxnumdetail == null ? null : auxnumdetail.trim();
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
     * @return 生产日期
     */
    public String getProduceddate() {
        return produceddate;
    }

    /**
     * @param produceddate 
	 *            生产日期
     */
    public void setProduceddate(String produceddate) {
        this.produceddate = produceddate == null ? null : produceddate.trim();
    }

    /**
     * @return 有效截止日期
     */
    public String getDeadline() {
        return deadline;
    }

    /**
     * @param deadline 
	 *            有效截止日期
     */
    public void setDeadline(String deadline) {
        this.deadline = deadline == null ? null : deadline.trim();
    }

    /**
     * @return 排序
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * @param seq 
	 *            排序
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
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

	public GoodsInfo getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(GoodsInfo goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public String getStoragename() {
		return storagename;
	}

	public void setStoragename(String storagename) {
		this.storagename = storagename;
	}

	public String getStoragelocationname() {
		return storagelocationname;
	}

	public void setStoragelocationname(String storagelocationname) {
		this.storagelocationname = storagelocationname;
	}

	public String getTaxtypename() {
		return taxtypename;
	}

	public void setTaxtypename(String taxtypename) {
		this.taxtypename = taxtypename;
	}

	public BigDecimal getTotalbox() {
		return totalbox;
	}

	public void setTotalbox(BigDecimal totalbox) {
		this.totalbox = totalbox;
	}
    
}