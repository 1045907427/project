package com.hd.agent.storage.model;

import com.hd.agent.basefiles.model.GoodsInfo;
import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 库存现存量汇总表
 * @author chenwei
 */
public class StorageSummary implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

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
     * 是否参与总量控制1是0否
     */
    private String istotalcontrol;
    /**
     * 是否发货仓1是0否
     */
    private String issendstorage;
    /**
     * 商品详细信息
     */
    private GoodsInfo goodsInfo;
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
     * 在途量
     */
    private BigDecimal transitnum;

    /**
     * 调拨待发量
     */
    private BigDecimal allotwaitnum;

    /**
     * 调拨待入量
     */
    private BigDecimal allotenternum;

    /**
     * 预计可用量
     */
    private BigDecimal projectedusablenum;

    /**
     * 安全库存
     */
    private BigDecimal safenum;
    /**
     * 允许超可用量发货数量
     */
    private BigDecimal outuseablenum;
    /**
     * 成本价
     */
    private BigDecimal costprice;
    /**
     * 库存金额
     */
    private BigDecimal storageamount;
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
     * 添加时间
     */
    private Date addtime;
    
    /**
     * 规格型号
     */
    private String model;
    
    /**
     * 更新时间
     */
    private Date modifytime;
    /**
     * 备注
     */
    private String remark;
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
     * @return 在途量
     */
    public BigDecimal getTransitnum() {
        return transitnum;
    }

    /**
     * @param transitnum 
	 *            在途量
     */
    public void setTransitnum(BigDecimal transitnum) {
        this.transitnum = transitnum;
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
     * @return 预计可用量
     */
    public BigDecimal getProjectedusablenum() {
        return projectedusablenum;
    }

    /**
     * @param projectedusablenum 
	 *            预计可用量
     */
    public void setProjectedusablenum(BigDecimal projectedusablenum) {
        this.projectedusablenum = projectedusablenum;
    }

    /**
     * @return 安全库存
     */
    public BigDecimal getSafenum() {
        return safenum;
    }

    /**
     * @param safenum 
	 *            安全库存
     */
    public void setSafenum(BigDecimal safenum) {
        this.safenum = safenum;
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

    @JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	public GoodsInfo getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(GoodsInfo goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public String getStorageid() {
		return storageid;
	}

	public void setStorageid(String storageid) {
		this.storageid = storageid;
	}

	public String getIstotalcontrol() {
		return istotalcontrol;
	}

	public void setIstotalcontrol(String istotalcontrol) {
		this.istotalcontrol = istotalcontrol;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getIssendstorage() {
		return issendstorage;
	}

	public void setIssendstorage(String issendstorage) {
		this.issendstorage = issendstorage;
	}

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public String getBranddept() {
		return branddept;
	}

	public void setBranddept(String branddept) {
		this.branddept = branddept;
	}

	public BigDecimal getOutuseablenum() {
		return outuseablenum;
	}

	public void setOutuseablenum(BigDecimal outuseablenum) {
		this.outuseablenum = outuseablenum;
	}

	public BigDecimal getCostprice() {
		return costprice;
	}

	public void setCostprice(BigDecimal costprice) {
		this.costprice = costprice;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

    public BigDecimal getStorageamount() {
        return storageamount;
    }

    public void setStorageamount(BigDecimal storageamount) {
        this.storageamount = storageamount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
