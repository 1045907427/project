package com.hd.agent.storage.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
/**
 * 批次现存量
 * @author chenwei
 */
public class StorageSummaryBatch implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 库存现存量编号
     */
    private String summaryid;

    /**
     * 商品编码
     */
    private String goodsid;
    /**
     * 品牌编码
     */
    private String brandid;
    /**
     * 品牌部门
     */
    private String branddept;
    /**
     * 条形码
     */
    private String barcode;
    /**
     * 仓库编码
     */
    private String storageid;
    /**
     * 默认仓库编码
     */
    private String defaultstorageid;
    /**
     * 默认仓库名称
     */
    private String defaultstoragename;
    /**
     * 所属库位编码
     */
    private String storagelocationid;
    /**
     * 批次号
     */
    private String batchno;

    /**
     * 批次状态0初始化1已使用2关闭
     */
    private String batchstate;

    /**
     * 现存量
     */
    private BigDecimal existingnum;

    /**
     * 可用量
     */
    private BigDecimal usablenum;

    /**
     * 待发量
     */
    private BigDecimal waitnum;

    /**
     * 调拨待发量
     */
    private BigDecimal allotwaitnum;

    /**
     * 调拨待入量
     */
    private BigDecimal allotenternum;

    /**
     * 初始数量
     */
    private BigDecimal intinum;

    /**
     * 已发货量
     */
    private BigDecimal sendnum;

    /**
     * 计量单位编码
     */
    private String unitid;

    /**
     * 计量单位名称
     */
    private String unitname;

    /**
     * 辅助计量单位编码
     */
    private String auxunitid;

    /**
     * 辅助计量单位名称
     */
    private String auxunitname;

    /**
     * 库存单价
     */
    private BigDecimal price;

    /**
     * 库存金额
     */
    private BigDecimal amount;
    /**
     * 成本价
     */
    private BigDecimal costprice;
    /**
     * 生产日期
     */
    private String produceddate;

    /**
     * 有效截止日期
     */
    private String deadline;

    /**
     * 最新入库日期
     */
    private String enterdate;

    /**
     * 最新出库日期
     */
    private String gooutdate;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 更新时间
     */
    private Date modifytime;
     /**
     * 版本号
     */
    private int version;
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
     * @return 库存现存量编号
     */
    public String getSummaryid() {
        return summaryid;
    }

    /**
     * @param summaryid 
	 *            库存现存量编号
     */
    public void setSummaryid(String summaryid) {
        this.summaryid = summaryid == null ? null : summaryid.trim();
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
     * @return 仓库编码
     */
    public String getStorageid() {
        return storageid;
    }

    /**
     * @param storageid 
	 *            仓库编码
     */
    public void setStorageid(String storageid) {
        this.storageid = storageid == null ? null : storageid.trim();
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
     * @return 批次状态0初始化1已使用2关闭
     */
    public String getBatchstate() {
        return batchstate;
    }

    /**
     * @param batchstate 
	 *            批次状态0初始化1已使用2关闭
     */
    public void setBatchstate(String batchstate) {
        this.batchstate = batchstate == null ? null : batchstate.trim();
    }

    /**
     * @return 现存量
     */
    public BigDecimal getExistingnum() {
        return existingnum;
    }

    /**
     * @param existingnum 
	 *            现存量
     */
    public void setExistingnum(BigDecimal existingnum) {
        this.existingnum = existingnum;
    }

    /**
     * @return 可用量
     */
    public BigDecimal getUsablenum() {
        return usablenum;
    }

    /**
     * @param usablenum 
	 *            可用量
     */
    public void setUsablenum(BigDecimal usablenum) {
        this.usablenum = usablenum;
    }

    /**
     * @return 待发量
     */
    public BigDecimal getWaitnum() {
        return waitnum;
    }

    /**
     * @param waitnum 
	 *            待发量
     */
    public void setWaitnum(BigDecimal waitnum) {
        this.waitnum = waitnum;
    }

    /**
     * @return 调拨待发量
     */
    public BigDecimal getAllotwaitnum() {
        return allotwaitnum;
    }

    /**
     * @param allotwaitnum 
	 *            调拨待发量
     */
    public void setAllotwaitnum(BigDecimal allotwaitnum) {
        this.allotwaitnum = allotwaitnum;
    }

    /**
     * @return 调拨待入量
     */
    public BigDecimal getAllotenternum() {
        return allotenternum;
    }

    /**
     * @param allotenternum 
	 *            调拨待入量
     */
    public void setAllotenternum(BigDecimal allotenternum) {
        this.allotenternum = allotenternum;
    }

    /**
     * @return 初始数量
     */
    public BigDecimal getIntinum() {
        return intinum;
    }

    /**
     * @param intinum 
	 *            初始数量
     */
    public void setIntinum(BigDecimal intinum) {
        this.intinum = intinum;
    }

    /**
     * @return 已发货量
     */
    public BigDecimal getSendnum() {
        return sendnum;
    }

    /**
     * @param sendnum 
	 *            已发货量
     */
    public void setSendnum(BigDecimal sendnum) {
        this.sendnum = sendnum;
    }

    /**
     * @return 计量单位编码
     */
    public String getUnitid() {
        return unitid;
    }

    /**
     * @param unitid 
	 *            计量单位编码
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
     * @return 辅助计量单位编码
     */
    public String getAuxunitid() {
        return auxunitid;
    }

    /**
     * @param auxunitid 
	 *            辅助计量单位编码
     */
    public void setAuxunitid(String auxunitid) {
        this.auxunitid = auxunitid == null ? null : auxunitid.trim();
    }

    /**
     * @return 辅助计量单位名称
     */
    public String getAuxunitname() {
        return auxunitname;
    }

    /**
     * @param auxunitname 
	 *            辅助计量单位名称
     */
    public void setAuxunitname(String auxunitname) {
        this.auxunitname = auxunitname == null ? null : auxunitname.trim();
    }

    /**
     * @return 库存单价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price 
	 *            库存单价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return 库存金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount 
	 *            库存金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
     * @return 最新入库日期
     */
    public String getEnterdate() {
        return enterdate;
    }

    /**
     * @param enterdate 
	 *            最新入库日期
     */
    public void setEnterdate(String enterdate) {
        this.enterdate = enterdate == null ? null : enterdate.trim();
    }

    /**
     * @return 最新出库日期
     */
    public String getGooutdate() {
        return gooutdate;
    }

    /**
     * @param gooutdate 
	 *            最新出库日期
     */
    public void setGooutdate(String gooutdate) {
        this.gooutdate = gooutdate == null ? null : gooutdate.trim();
    }

    /**
     * @return 添加时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
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

    /**
     * @return 更新时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * @param modifytime 
	 *            更新时间
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

	public String getStoragelocationid() {
		return storagelocationid;
	}

	public void setStoragelocationid(String storagelocationid) {
		this.storagelocationid = storagelocationid;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public String getDefaultstorageid() {
		return defaultstorageid;
	}

	public void setDefaultstorageid(String defaultstorageid) {
		this.defaultstorageid = defaultstorageid;
	}

	public String getDefaultstoragename() {
		return defaultstoragename;
	}

	public void setDefaultstoragename(String defaultstoragename) {
		this.defaultstoragename = defaultstoragename;
	}

	public String getBranddept() {
		return branddept;
	}

	public void setBranddept(String branddept) {
		this.branddept = branddept;
	}

	public BigDecimal getCostprice() {
		return costprice;
	}

	public void setCostprice(BigDecimal costprice) {
		this.costprice = costprice;
	}

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
