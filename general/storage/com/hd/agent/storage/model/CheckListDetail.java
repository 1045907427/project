package com.hd.agent.storage.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.hd.agent.basefiles.model.GoodsInfo;

/**
 * 盘点单明细
 * @author chenwei
 */
public class CheckListDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 盘点单编号
     */
    private String checklistid;
   /**
    * 盘点人员
    */
    private String checkuserid;
    /**
     * 盘点人员名称
     */
    private String checkusername;
    /**
     * 盘点是否正确1是0否
     */
    private String istrue;
    /**
     * 批次现存量编号
     */
    private String summarybatchid;
    /**
     * 商品编码
     */
    private String goodsid;
    /**
     * 商品名称
     */
    private String goodsname;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 商品类别
     */
    private String goodssortname;
    /**
     * 规格型号
     */
    private String model;
    /**
     * 条形码
     */
    private String barcode;
    /**
     * 助记符
     */
    private String spell;
    /**
     * 商品货位
     */
    private String itemno;
    public String getItemno() {
		return itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
	}

	/**
     * 商品品牌
     */
    private String brandid;
    /**
     * 商品信息
     */
    private GoodsInfo goodsInfo;
    /**
     * 箱装量
     */
    private BigDecimal boxnum;
    /**
     * 所属仓库
     */
    private String storageid;

    /**
     * 所属库位
     */
    private String storagelocationid;
    /**
     * 所属库位名称
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
     * 账面数量
     */
    private BigDecimal booknum;

    /**
     * 实际数量
     */
    private BigDecimal realnum;

    /**
     * 盈亏数量
     */
    private BigDecimal profitlossnum;

    /**
     * 辅计量单位
     */
    private String auxunitid;

    /**
     * 辅计量单位名称
     */
    private String auxunitname;

    /**
     * 辅单位账面数量
     */
    private BigDecimal auxbooknum;

    /**
     * 辅单位账面数量描述
     */
    private String auxbooknumdetail;

    /**
     * 辅单位实际数量
     */
    private BigDecimal auxrealnum;
    /**
     * 辅单位实际余数数量
     */
    private BigDecimal auxrealremainder;
    /**
     * 辅单位实际数量描述
     */
    private String auxrealnumdetail;

    /**
     * 辅单位盈亏数量
     */
    private BigDecimal auxprofitlossnum;

    /**
     * 辅单位盈亏数量描述
     */
    private String auxprofitlossnumdetail;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 盈亏金额
     */
    private BigDecimal profitlossamount;
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
     * 现存量
     */
    private BigDecimal existingnum;

    private Date audittime;
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
     * @return 盘点单编号
     */
    public String getChecklistid() {
        return checklistid;
    }

    /**
     * @param checklistid 
	 *            盘点单编号
     */
    public void setChecklistid(String checklistid) {
        this.checklistid = checklistid == null ? null : checklistid.trim();
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
     * @return 账面数量
     */
    public BigDecimal getBooknum() {
        return booknum;
    }

    /**
     * @param booknum 
	 *            账面数量
     */
    public void setBooknum(BigDecimal booknum) {
        this.booknum = booknum;
    }

    /**
     * @return 实际数量
     */
    public BigDecimal getRealnum() {
     return realnum;
    }

    /**
     * @param realnum 
	 *            实际数量
     */
    public void setRealnum(BigDecimal realnum) {
        this.realnum = realnum;
    }

    /**
     * @return 盈亏数量
     */
    public BigDecimal getProfitlossnum() {
        return profitlossnum;
    }

    /**
     * @param profitlossnum 
	 *            盈亏数量
     */
    public void setProfitlossnum(BigDecimal profitlossnum) {
        this.profitlossnum = profitlossnum;
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
     * @return 辅单位账面数量
     */
    public BigDecimal getAuxbooknum() {
        return auxbooknum;
    }

    /**
     * @param auxbooknum 
	 *            辅单位账面数量
     */
    public void setAuxbooknum(BigDecimal auxbooknum) {
        this.auxbooknum = auxbooknum;
    }

    /**
     * @return 辅单位账面数量描述
     */
    public String getAuxbooknumdetail() {
        return auxbooknumdetail;
    }

    /**
     * @param auxbooknumdetail 
	 *            辅单位账面数量描述
     */
    public void setAuxbooknumdetail(String auxbooknumdetail) {
        this.auxbooknumdetail = auxbooknumdetail == null ? null : auxbooknumdetail.trim();
    }

    /**
     * @return 辅单位实际数量
     */
    public BigDecimal getAuxrealnum() {
     return auxrealnum;
    }

    /**
     * @param auxrealnum 
	 *            辅单位实际数量
     */
    public void setAuxrealnum(BigDecimal auxrealnum) {
        this.auxrealnum = auxrealnum;
    }

    /**
     * @return 辅单位实际数量描述
     */
    public String getAuxrealnumdetail() {
        return auxrealnumdetail;
    }

    /**
     * @param auxrealnumdetail 
	 *            辅单位实际数量描述
     */
    public void setAuxrealnumdetail(String auxrealnumdetail) {
        this.auxrealnumdetail = auxrealnumdetail == null ? null : auxrealnumdetail.trim();
    }

    /**
     * @return 辅单位盈亏数量
     */
    public BigDecimal getAuxprofitlossnum() {
        return auxprofitlossnum;
    }

    /**
     * @param auxprofitlossnum 
	 *            辅单位盈亏数量
     */
    public void setAuxprofitlossnum(BigDecimal auxprofitlossnum) {
        this.auxprofitlossnum = auxprofitlossnum;
    }

    /**
     * @return 辅单位盈亏数量描述
     */
    public String getAuxprofitlossnumdetail() {
        return auxprofitlossnumdetail;
    }

    /**
     * @param auxprofitlossnumdetail 
	 *            辅单位盈亏数量描述
     */
    public void setAuxprofitlossnumdetail(String auxprofitlossnumdetail) {
        this.auxprofitlossnumdetail = auxprofitlossnumdetail == null ? null : auxprofitlossnumdetail.trim();
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

	public String getCheckuserid() {
		return checkuserid;
	}

	public void setCheckuserid(String checkuserid) {
		this.checkuserid = checkuserid;
	}

	public String getIstrue() {
		return istrue;
	}

	public void setIstrue(String istrue) {
		this.istrue = istrue;
	}

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public BigDecimal getAuxrealremainder() {
     return auxrealremainder;
	}

	public void setAuxrealremainder(BigDecimal auxrealremainder) {
		this.auxrealremainder = auxrealremainder;
	}

	public String getCheckusername() {
		return checkusername;
	}

	public void setCheckusername(String checkusername) {
		this.checkusername = checkusername;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public BigDecimal getBoxnum() {
		return boxnum;
	}

	public void setBoxnum(BigDecimal boxnum) {
		this.boxnum = boxnum;
	}

	public BigDecimal getExistingnum() {
		return existingnum;
	}

	public void setExistingnum(BigDecimal existingnum) {
		this.existingnum = existingnum;
	}

	public BigDecimal getProfitlossamount() {
		return profitlossamount;
	}

	public void setProfitlossamount(BigDecimal profitlossamount) {
		this.profitlossamount = profitlossamount;
	}

	public String getGoodssortname() {
		return goodssortname;
	}

	public void setGoodssortname(String goodssortname) {
		this.goodssortname = goodssortname;
	}

    public String getSpell() {
     return spell;
    }

    public void setSpell(String spell) {
     this.spell = spell;
    }

   public Date getAudittime() {
    return audittime;
   }

   public void setAudittime(Date audittime) {
    this.audittime = audittime;
   }
}
