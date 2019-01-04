/**
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-5-24 chenwei 创建版本
 */
package com.hd.agent.storage.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.hd.agent.basefiles.model.GoodsInfo;

/**
 * 库存调账单明细
 * @author chenwei
 */
public class AdjustmentsDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 明细编号
     */
    private String id;

    /**
     * 调账单编号
     */
    private String adjustmentsid;
    /**
     * 批次现存量编号
     */
    private String summarybatchid;

    /**
     * 商品编码
     */
    private String goodsid;
    
    /**
     * 品牌编码
     */
    private String brandid;
    /**
     * 商品信息
     */
    private GoodsInfo goodsInfo;
    /**
     * 所属仓库
     */
    private String storageid;
    /**
     * 仓库名称
     */
    private String storagename;
    /**
     * 所属库位
     */
    private String storagelocationid;
    /**
     * 库位名称
     */
    private String storagelocationname;
    /**
     * 主计量单位
     */
    private String unitid;

    /**
     * 主计量单位名称
     */
    private String unitname;

    /**
     * 调账数量
     */
    private BigDecimal adjustnum;

    /**
     * 辅计量单位
     */
    private String auxunitid;

    /**
     * 辅计量单位名称
     */
    private String auxunitname;

    /**
     * 辅单位调账数量
     */
    private BigDecimal auxadjustnum;
    /**
     * 辅单位调账余数数量
     */
    private BigDecimal auxadjustremainder;
    /**
     * 辅单位调账数量描述
     */
    private String auxadjustnumdetail;
    /**
     * 合计箱数
     */
    private BigDecimal totalbox;
    /**
     * 单价
     */
    private BigDecimal price;
	/**
     * 无税单价
     */
    private BigDecimal notaxprice;

    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 无税金额
     */
    private BigDecimal notaxamount;

    /**
     * 现存量
     */
    private BigDecimal existingnum;

    /**
     * 可用量
     */
    private BigDecimal usablenum;

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

    /**
     * 排序
     */
    private Integer seq;
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
     * 税额
     */
    private BigDecimal tax;

    /**
     * 税种
     */
    private String taxtype;

    /**
     * @return 明细编号
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            明细编号
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 调账单编号
     */
    public String getAdjustmentsid() {
        return adjustmentsid;
    }

    /**
     * @param adjustmentsid 
	 *            调账单编号
     */
    public void setAdjustmentsid(String adjustmentsid) {
        this.adjustmentsid = adjustmentsid == null ? null : adjustmentsid.trim();
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
     * @return 所属仓库
     */
    public String getStorageid() {
        return storageid;
    }

    /**
     * @param storageid 
	 *            所属仓库
     */
    public void setStorageid(String storageid) {
        this.storageid = storageid == null ? null : storageid.trim();
    }

    /**
     * @return 所属库位
     */
    public String getStoragelocationid() {
        return storagelocationid;
    }

    /**
     * @param storagelocationid 
	 *            所属库位
     */
    public void setStoragelocationid(String storagelocationid) {
        this.storagelocationid = storagelocationid == null ? null : storagelocationid.trim();
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
     * @return 调账数量
     */
    public BigDecimal getAdjustnum() {
        return adjustnum;
    }

    /**
     * @param adjustnum 
	 *            调账数量
     */
    public void setAdjustnum(BigDecimal adjustnum) {
        this.adjustnum = adjustnum;
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
     * @return 辅单位调账数量
     */
    public BigDecimal getAuxadjustnum() {
        return auxadjustnum;
    }

    /**
     * @param auxadjustnum 
	 *            辅单位调账数量
     */
    public void setAuxadjustnum(BigDecimal auxadjustnum) {
        this.auxadjustnum = auxadjustnum;
    }

    /**
     * @return 辅单位调账数量描述
     */
    public String getAuxadjustnumdetail() {
        return auxadjustnumdetail;
    }

    /**
     * @param auxadjustnumdetail 
	 *            辅单位调账数量描述
     */
    public void setAuxadjustnumdetail(String auxadjustnumdetail) {
        this.auxadjustnumdetail = auxadjustnumdetail == null ? null : auxadjustnumdetail.trim();
    }

    /**
     * @return 单价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price 
	 *            单价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return 金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount 
	 *            金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

	public String getSummarybatchid() {
		return summarybatchid;
	}

	public void setSummarybatchid(String summarybatchid) {
		this.summarybatchid = summarybatchid;
	}

	public String getField01() {
		return field01;
	}

	public void setField01(String field01) {
		this.field01 = field01;
	}

	public String getField02() {
		return field02;
	}

	public void setField02(String field02) {
		this.field02 = field02;
	}

	public String getField03() {
		return field03;
	}

	public void setField03(String field03) {
		this.field03 = field03;
	}

	public String getField04() {
		return field04;
	}

	public void setField04(String field04) {
		this.field04 = field04;
	}

	public String getField05() {
		return field05;
	}

	public void setField05(String field05) {
		this.field05 = field05;
	}

	public String getField06() {
		return field06;
	}

	public void setField06(String field06) {
		this.field06 = field06;
	}

	public String getField07() {
		return field07;
	}

	public void setField07(String field07) {
		this.field07 = field07;
	}

	public String getField08() {
		return field08;
	}

	public void setField08(String field08) {
		this.field08 = field08;
	}

	public BigDecimal getAuxadjustremainder() {
        return auxadjustremainder;
	}

	public void setAuxadjustremainder(BigDecimal auxadjustremainder) {
		this.auxadjustremainder = auxadjustremainder;
	}

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public BigDecimal getTotalbox() {
		return totalbox;
	}

	public void setTotalbox(BigDecimal totalbox) {
		this.totalbox = totalbox;
	}
	public BigDecimal getNotaxprice() {
		return notaxprice;
	}

	public void setNotaxprice(BigDecimal notaxprice) {
		this.notaxprice = notaxprice;
	}

	public BigDecimal getNotaxamount() {
		return notaxamount;
	}

	public void setNotaxamount(BigDecimal notaxamount) {
		this.notaxamount = notaxamount;
	}

    public BigDecimal getExistingnum() {
        return existingnum;
    }

    public void setExistingnum(BigDecimal existingnum) {
        this.existingnum = existingnum;
    }

    public BigDecimal getUsablenum() {
        return usablenum;
    }

    public void setUsablenum(BigDecimal usablenum) {
        this.usablenum = usablenum;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public String getTaxtype() {
        return taxtype;
    }

    public void setTaxtype(String taxtype) {
        this.taxtype = taxtype;
    }
}