package com.hd.agent.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OaGoodsPriceDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String billid;

    /**
     * 商品编码
     */
    private String goodsid;

    /**
     * 商品名称
     */
    private String goodsname;

    /**
     * 品牌编号
     */
    private String brandid;

    /**
     * 条形码
     */
    private String barcode;

    /**
     * 箱装条形码
     */
    private String boxbarcode;

    /**
     * 商品分类
     */
    private String goodssort;

    /**
     * 单位编号
     */
    private String unitid;

    /**
     * 单位名称
     */
    private String unitname;

    /**
     * 辅单位编号
     */
    private String auxunitid;

    /**
     * 辅单位名称
     */
    private String auxunitname;

    /**
     * 箱装量
     */
    private Integer boxnum;

    /**
     * 毛重
     */
    private BigDecimal grossweight;

    /**
     * 单体积
     */
    private BigDecimal singlevolume;

    /**
     * 仓库编码
     */
    private String storageid;

    /**
     * 含税进价(对应档案的最高采购价)(旧)
     */
    private BigDecimal oldbuytaxprice;

    /**
     * 含税进价(对应档案的最高采购价)(新)
     */
    private BigDecimal newbuytaxprice;

    /**
     * 未税进价
     */
    private BigDecimal buynotaxprice;

    /**
     * 税种编号
     */
    private String taxtype;

    /**
     * 税率
     */
    private BigDecimal taxrate;

    /**
     * 价格套1(旧)
     */
    private BigDecimal oldprice1;

    /**
     * 价格套2(旧)
     */
    private BigDecimal oldprice2;

    /**
     * 价格套3(旧)
     */
    private BigDecimal oldprice3;

    /**
     * 价格套4(旧)
     */
    private BigDecimal oldprice4;

    /**
     * 价格套5(旧)
     */
    private BigDecimal oldprice5;

    /**
     * 价格套6(旧)
     */
    private BigDecimal oldprice6;

    /**
     * 价格套7(旧)
     */
    private BigDecimal oldprice7;

    /**
     * 价格套8(旧)
     */
    private BigDecimal oldprice8;

    /**
     * 价格套1(新)
     */
    private BigDecimal newprice1;

    /**
     * 价格套2(新)
     */
    private BigDecimal newprice2;

    /**
     * 价格套3(新)
     */
    private BigDecimal newprice3;

    /**
     * 价格套4(新)
     */
    private BigDecimal newprice4;

    /**
     * 价格套5(新)
     */
    private BigDecimal newprice5;

    /**
     * 价格套6(新)
     */
    private BigDecimal newprice6;

    /**
     * 价格套7(新)
     */
    private BigDecimal newprice7;

    /**
     * 价格套8(新)
     */
    private BigDecimal newprice8;

    /**
     * 箱体积
     */
    private BigDecimal totalvolume;

    /**
     * 箱重
     */
    private BigDecimal totalweight;

    /**
     * 长度(m)
     */
    private BigDecimal glength;

    /**
     * 宽度
     */
    private BigDecimal gwidth;

    /**
     * 高度
     */
    private BigDecimal gheight;

    /**
     * 基准销售价(旧)
     */
    private BigDecimal oldbasesaleprice;

    /**
     * 基准销售价(新)
     */
    private BigDecimal newbasesaleprice;

    /**
     * 备注
     */
    private String remark;

    /**
     * 核算成本价(旧)
     */
    private BigDecimal oldcostaccountprice;

    /**
     * 核算成本价(新)
     */
    private BigDecimal newcostaccountprice;

    /**
     * @return 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键
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
     * @return 商品名称
     */
    public String getGoodsname() {
        return goodsname;
    }

    /**
     * @param goodsname 
	 *            商品名称
     */
    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname == null ? null : goodsname.trim();
    }

    /**
     * @return 品牌编号
     */
    public String getBrandid() {
        return brandid;
    }

    /**
     * @param brandid 
	 *            品牌编号
     */
    public void setBrandid(String brandid) {
        this.brandid = brandid == null ? null : brandid.trim();
    }

    /**
     * @return 条形码
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * @param barcode 
	 *            条形码
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    /**
     * @return 箱装条形码
     */
    public String getBoxbarcode() {
        return boxbarcode;
    }

    /**
     * @param boxbarcode 
	 *            箱装条形码
     */
    public void setBoxbarcode(String boxbarcode) {
        this.boxbarcode = boxbarcode == null ? null : boxbarcode.trim();
    }

    /**
     * @return 商品分类
     */
    public String getGoodssort() {
        return goodssort;
    }

    /**
     * @param goodssort 
	 *            商品分类
     */
    public void setGoodssort(String goodssort) {
        this.goodssort = goodssort == null ? null : goodssort.trim();
    }

    /**
     * @return 单位编号
     */
    public String getUnitid() {
        return unitid;
    }

    /**
     * @param unitid 
	 *            单位编号
     */
    public void setUnitid(String unitid) {
        this.unitid = unitid == null ? null : unitid.trim();
    }

    /**
     * @return 单位名称
     */
    public String getUnitname() {
        return unitname;
    }

    /**
     * @param unitname 
	 *            单位名称
     */
    public void setUnitname(String unitname) {
        this.unitname = unitname == null ? null : unitname.trim();
    }

    /**
     * @return 辅单位编号
     */
    public String getAuxunitid() {
        return auxunitid;
    }

    /**
     * @param auxunitid 
	 *            辅单位编号
     */
    public void setAuxunitid(String auxunitid) {
        this.auxunitid = auxunitid == null ? null : auxunitid.trim();
    }

    /**
     * @return 辅单位名称
     */
    public String getAuxunitname() {
        return auxunitname;
    }

    /**
     * @param auxunitname 
	 *            辅单位名称
     */
    public void setAuxunitname(String auxunitname) {
        this.auxunitname = auxunitname == null ? null : auxunitname.trim();
    }

    /**
     * @return 箱装量
     */
    public Integer getBoxnum() {
        return boxnum;
    }

    /**
     * @param boxnum 
	 *            箱装量
     */
    public void setBoxnum(Integer boxnum) {
        this.boxnum = boxnum;
    }

    /**
     * @return 毛重
     */
    public BigDecimal getGrossweight() {
        return grossweight;
    }

    /**
     * @param grossweight 
	 *            毛重
     */
    public void setGrossweight(BigDecimal grossweight) {
        this.grossweight = grossweight;
    }

    /**
     * @return 单体积
     */
    public BigDecimal getSinglevolume() {
        return singlevolume;
    }

    /**
     * @param singlevolume 
	 *            单体积
     */
    public void setSinglevolume(BigDecimal singlevolume) {
        this.singlevolume = singlevolume;
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
     * @return 含税进价(对应档案的最高采购价)(旧)
     */
    public BigDecimal getOldbuytaxprice() {
        return oldbuytaxprice;
    }

    /**
     * @param oldbuytaxprice 
	 *            含税进价(对应档案的最高采购价)(旧)
     */
    public void setOldbuytaxprice(BigDecimal oldbuytaxprice) {
        this.oldbuytaxprice = oldbuytaxprice;
    }

    /**
     * @return 含税进价(对应档案的最高采购价)(新)
     */
    public BigDecimal getNewbuytaxprice() {
        return newbuytaxprice;
    }

    /**
     * @param newbuytaxprice 
	 *            含税进价(对应档案的最高采购价)(新)
     */
    public void setNewbuytaxprice(BigDecimal newbuytaxprice) {
        this.newbuytaxprice = newbuytaxprice;
    }

    /**
     * @return 未税进价
     */
    public BigDecimal getBuynotaxprice() {
        return buynotaxprice;
    }

    /**
     * @param buynotaxprice 
	 *            未税进价
     */
    public void setBuynotaxprice(BigDecimal buynotaxprice) {
        this.buynotaxprice = buynotaxprice;
    }

    /**
     * @return 税种编号
     */
    public String getTaxtype() {
        return taxtype;
    }

    /**
     * @param taxtype 
	 *            税种编号
     */
    public void setTaxtype(String taxtype) {
        this.taxtype = taxtype == null ? null : taxtype.trim();
    }

    /**
     * @return 税率
     */
    public BigDecimal getTaxrate() {
        return taxrate;
    }

    /**
     * @param taxrate 
	 *            税率
     */
    public void setTaxrate(BigDecimal taxrate) {
        this.taxrate = taxrate;
    }

    /**
     * @return 价格套1(旧)
     */
    public BigDecimal getOldprice1() {
        return oldprice1;
    }

    /**
     * @param oldprice1 
	 *            价格套1(旧)
     */
    public void setOldprice1(BigDecimal oldprice1) {
        this.oldprice1 = oldprice1;
    }

    /**
     * @return 价格套2(旧)
     */
    public BigDecimal getOldprice2() {
        return oldprice2;
    }

    /**
     * @param oldprice2 
	 *            价格套2(旧)
     */
    public void setOldprice2(BigDecimal oldprice2) {
        this.oldprice2 = oldprice2;
    }

    /**
     * @return 价格套3(旧)
     */
    public BigDecimal getOldprice3() {
        return oldprice3;
    }

    /**
     * @param oldprice3 
	 *            价格套3(旧)
     */
    public void setOldprice3(BigDecimal oldprice3) {
        this.oldprice3 = oldprice3;
    }

    /**
     * @return 价格套4(旧)
     */
    public BigDecimal getOldprice4() {
        return oldprice4;
    }

    /**
     * @param oldprice4 
	 *            价格套4(旧)
     */
    public void setOldprice4(BigDecimal oldprice4) {
        this.oldprice4 = oldprice4;
    }

    /**
     * @return 价格套5(旧)
     */
    public BigDecimal getOldprice5() {
        return oldprice5;
    }

    /**
     * @param oldprice5 
	 *            价格套5(旧)
     */
    public void setOldprice5(BigDecimal oldprice5) {
        this.oldprice5 = oldprice5;
    }

    /**
     * @return 价格套6(旧)
     */
    public BigDecimal getOldprice6() {
        return oldprice6;
    }

    /**
     * @param oldprice6 
	 *            价格套6(旧)
     */
    public void setOldprice6(BigDecimal oldprice6) {
        this.oldprice6 = oldprice6;
    }

    /**
     * @return 价格套7(旧)
     */
    public BigDecimal getOldprice7() {
        return oldprice7;
    }

    /**
     * @param oldprice7 
	 *            价格套7(旧)
     */
    public void setOldprice7(BigDecimal oldprice7) {
        this.oldprice7 = oldprice7;
    }

    /**
     * @return 价格套8(旧)
     */
    public BigDecimal getOldprice8() {
        return oldprice8;
    }

    /**
     * @param oldprice8 
	 *            价格套8(旧)
     */
    public void setOldprice8(BigDecimal oldprice8) {
        this.oldprice8 = oldprice8;
    }

    /**
     * @return 价格套1(新)
     */
    public BigDecimal getNewprice1() {
        return newprice1;
    }

    /**
     * @param newprice1 
	 *            价格套1(新)
     */
    public void setNewprice1(BigDecimal newprice1) {
        this.newprice1 = newprice1;
    }

    /**
     * @return 价格套2(新)
     */
    public BigDecimal getNewprice2() {
        return newprice2;
    }

    /**
     * @param newprice2 
	 *            价格套2(新)
     */
    public void setNewprice2(BigDecimal newprice2) {
        this.newprice2 = newprice2;
    }

    /**
     * @return 价格套3(新)
     */
    public BigDecimal getNewprice3() {
        return newprice3;
    }

    /**
     * @param newprice3 
	 *            价格套3(新)
     */
    public void setNewprice3(BigDecimal newprice3) {
        this.newprice3 = newprice3;
    }

    /**
     * @return 价格套4(新)
     */
    public BigDecimal getNewprice4() {
        return newprice4;
    }

    /**
     * @param newprice4 
	 *            价格套4(新)
     */
    public void setNewprice4(BigDecimal newprice4) {
        this.newprice4 = newprice4;
    }

    /**
     * @return 价格套5(新)
     */
    public BigDecimal getNewprice5() {
        return newprice5;
    }

    /**
     * @param newprice5 
	 *            价格套5(新)
     */
    public void setNewprice5(BigDecimal newprice5) {
        this.newprice5 = newprice5;
    }

    /**
     * @return 价格套6(新)
     */
    public BigDecimal getNewprice6() {
        return newprice6;
    }

    /**
     * @param newprice6 
	 *            价格套6(新)
     */
    public void setNewprice6(BigDecimal newprice6) {
        this.newprice6 = newprice6;
    }

    /**
     * @return 价格套7(新)
     */
    public BigDecimal getNewprice7() {
        return newprice7;
    }

    /**
     * @param newprice7 
	 *            价格套7(新)
     */
    public void setNewprice7(BigDecimal newprice7) {
        this.newprice7 = newprice7;
    }

    /**
     * @return 价格套8(新)
     */
    public BigDecimal getNewprice8() {
        return newprice8;
    }

    /**
     * @param newprice8 
	 *            价格套8(新)
     */
    public void setNewprice8(BigDecimal newprice8) {
        this.newprice8 = newprice8;
    }

    /**
     * @return 箱体积
     */
    public BigDecimal getTotalvolume() {
        return totalvolume;
    }

    /**
     * @param totalvolume 
	 *            箱体积
     */
    public void setTotalvolume(BigDecimal totalvolume) {
        this.totalvolume = totalvolume;
    }

    /**
     * @return 箱重
     */
    public BigDecimal getTotalweight() {
        return totalweight;
    }

    /**
     * @param totalweight 
	 *            箱重
     */
    public void setTotalweight(BigDecimal totalweight) {
        this.totalweight = totalweight;
    }

    /**
     * @return 长度(m)
     */
    public BigDecimal getGlength() {
        return glength;
    }

    /**
     * @param glength 
	 *            长度(m)
     */
    public void setGlength(BigDecimal glength) {
        this.glength = glength;
    }

    /**
     * @return 宽度
     */
    public BigDecimal getGwidth() {
        return gwidth;
    }

    /**
     * @param gwidth 
	 *            宽度
     */
    public void setGwidth(BigDecimal gwidth) {
        this.gwidth = gwidth;
    }

    /**
     * @return 高度
     */
    public BigDecimal getGheight() {
        return gheight;
    }

    /**
     * @param gheight 
	 *            高度
     */
    public void setGheight(BigDecimal gheight) {
        this.gheight = gheight;
    }

    /**
     * @return 基准销售价(旧)
     */
    public BigDecimal getOldbasesaleprice() {
        return oldbasesaleprice;
    }

    /**
     * @param oldbasesaleprice 
	 *            基准销售价(旧)
     */
    public void setOldbasesaleprice(BigDecimal oldbasesaleprice) {
        this.oldbasesaleprice = oldbasesaleprice;
    }

    /**
     * @return 基准销售价(新)
     */
    public BigDecimal getNewbasesaleprice() {
        return newbasesaleprice;
    }

    /**
     * @param newbasesaleprice 
	 *            基准销售价(新)
     */
    public void setNewbasesaleprice(BigDecimal newbasesaleprice) {
        this.newbasesaleprice = newbasesaleprice;
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
     * @return 核算成本价(旧)
     */
    public BigDecimal getOldcostaccountprice() {
        return oldcostaccountprice;
    }

    /**
     * @param oldcostaccountprice 
	 *            核算成本价(旧)
     */
    public void setOldcostaccountprice(BigDecimal oldcostaccountprice) {
        this.oldcostaccountprice = oldcostaccountprice;
    }

    /**
     * @return 核算成本价(新)
     */
    public BigDecimal getNewcostaccountprice() {
        return newcostaccountprice;
    }

    /**
     * @param newcostaccountprice 
	 *            核算成本价(新)
     */
    public void setNewcostaccountprice(BigDecimal newcostaccountprice) {
        this.newcostaccountprice = newcostaccountprice;
    }
}