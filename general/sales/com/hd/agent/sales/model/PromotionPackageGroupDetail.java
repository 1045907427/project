package com.hd.agent.sales.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hd.agent.basefiles.model.GoodsInfo;

public class PromotionPackageGroupDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商品品牌
     */
    private String brand;

    /**
     * 箱价
     */
    private BigDecimal boxPrice;

    /**
     * 赠品信息
     */
	private List<GoodsInfo> giveGoodsInfo ;

    /**
     * 箱装量
     */
    private BigDecimal boxnum ;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String billid;

    /**
     * 产品组编号
     */
    private String groupid;

    /**
     * 商品类型1正常商品2赠品（针对买赠）
     */
    private String gtype;

    /**
     * 商品编号
     */
    private String goodsid;
    /**
     * 条形码
     */
    private String barcode;
    /**
     * 主计量单位
     */
    private String unitid;

    /**
     * 主计量单位名称
     */
    private String unitname;

    /**
     * 商品数量（或者赠品数量）
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
     * 辅单位整数数量
     */
    private BigDecimal auxnum;

    /**
     * 辅单位余数数量
     */
    private BigDecimal auxremainder;

    /**
     * 数量(辅计量显示)
     */
    private String auxnumdetail;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 原价
     */
    private BigDecimal oldprice;

    /**
     * 现价（赠品价格为0）
     */
    private BigDecimal price;

    /**
     * 备注
     */
    private String remar;

    /**
     * 商品名称
     */
    private String goodsname;
    /**
     * 商品详情
     */
    private GoodsInfo goodsInfo;
    /**
     * 未税单价
     */
    private BigDecimal notaxprice;
    /**
     * 可用量
     */
    private BigDecimal usablenum;
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
     * @return 单据编号
     */
    public String getBillid() {
        return billid;
    }

    /**
     * @param billid 
	 *            单据编号
     */
    public void setBillid(String billid) {
        this.billid = billid == null ? null : billid.trim();
    }

    /**
     * @return 产品组编号
     */
    public String getGroupid() {
        return groupid;
    }

    /**
     * @param groupid 
	 *            产品组编号
     */
    public void setGroupid(String groupid) {
        this.groupid = groupid == null ? null : groupid.trim();
    }

    /**
     * @return 商品类型1正常商品2赠品（针对买赠）
     */
    public String getGtype() {
        return gtype;
    }

    /**
     * @param gtype 
	 *            商品类型1正常商品2赠品（针对买赠）
     */
    public void setGtype(String gtype) {
        this.gtype = gtype == null ? null : gtype.trim();
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
     * @return 商品数量（或者赠品数量）
     */
    public BigDecimal getUnitnum() {
        return unitnum;
    }

    /**
     * @param unitnum 
	 *            商品数量（或者赠品数量）
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
     * @return 辅单位整数数量
     */
    public BigDecimal getAuxnum() {
        return auxnum;
    }

    /**
     * @param auxnum 
	 *            辅单位整数数量
     */
    public void setAuxnum(BigDecimal auxnum) {
        this.auxnum = auxnum;
    }

    /**
     * @return 辅单位余数数量
     */
    public BigDecimal getAuxremainder() {
        return auxremainder;
    }

    /**
     * @param auxremainder 
	 *            辅单位余数数量
     */
    public void setAuxremainder(BigDecimal auxremainder) {
        this.auxremainder = auxremainder;
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
     * @return 添加时间
     */
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
     * @return 原价
     */
    public BigDecimal getOldprice() {
        return oldprice;
    }

    /**
     * @param oldprice 
	 *            原价
     */
    public void setOldprice(BigDecimal oldprice) {
        this.oldprice = oldprice;
    }

    /**
     * @return 现价（赠品价格为0）
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price 
	 *            现价（赠品价格为0）
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return 备注
     */
    public String getRemar() {
        return remar;
    }

    /**
     * @param remar
	 *            备注
     */
    public void setRemar(String remar) {
        this.remar = remar == null ? null : remar.trim();
    }

    public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

    public GoodsInfo getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public BigDecimal getNotaxprice() {
        return notaxprice;
    }

    public void setNotaxprice(BigDecimal notaxprice) {
        this.notaxprice = notaxprice;
    }

    public BigDecimal getUsablenum() {
        return usablenum;
    }

    public void setUsablenum(BigDecimal usablenum) {
        this.usablenum = usablenum;
    }

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getBoxnum() {
        return boxnum;
    }

    public void setBoxnum(BigDecimal boxnum) {
        this.boxnum = boxnum;
    }

    public List<GoodsInfo> getGiveGoodsInfo() {
        return giveGoodsInfo;
    }

    public void setGiveGoodsInfo(List<GoodsInfo> giveGoodsInfo) {
        this.giveGoodsInfo = giveGoodsInfo;
    }

    public BigDecimal getBoxPrice() {
        return boxPrice;
    }

    public void setBoxPrice(BigDecimal boxPrice) {
        this.boxPrice = boxPrice;
    }
}