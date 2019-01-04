package com.hd.agent.sales.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PromotionPackageGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<PromotionPackageGroupDetail> groupDetails ;

    /**
     * 商品箱装量（捆绑）
     */
    private BigDecimal boxnum ;

    /**
     * 商品参考单价
     */
    private BigDecimal unitprice ;

    /**
     * 商品品牌
     */
    private String brand ;

    /**
     * 商品箱价
     */
    private String boxprice;

    /**
     * 套餐总价
     */
    private String totalprice ;

    /**
     * 套餐原价
     */
    private String totaloldprice ;

    /**
     * 商品编码
     */
    private String goodsid;

    /**
     * 打印次数
     */
    private Integer printtimes;

    /**
     * 商品类型(买赠 1.购买商品 2.赠品)
     */
    private String gtype;

    /**
     * 产品名称
     */
    private String goodsname;

    /**
     * 商品辅基准数量
     */
    private BigDecimal auxnum;

    /**
     * 商品促销需达到的数量值
     */
    private BigDecimal unitnum;

    /**
     * 商品促销需达到的数量值描述
     */
    private String auxnumdetail;

    /**
     * 商品基准数量
     */
    private BigDecimal auxremainder;

    /**
     * 主单位名称
     */
    private String unitname;

    /**
     * 辅单位名称
     */
    private String auxunitname;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String billid;

    /**
     * 单据状态（由单据中过来）
     */
    private String status;

    /**
     * 活动类型1买赠2捆绑
     */
    private String ptype;

    /**
     * 开始生效日期
     */
    private String begindate;

    /**
     * 有效截止日期
     */
    private String enddate;

    /**
     * 促销产品编号
     */
    private String groupid;
    /**
     * 促销产品编号(备用）
     */
    private String groupid1;

    /**
     * 促销产品名称
     */
    private String groupname;

    /**
     * 产品组名称拼音
     */
    private String pinyin;

    /**
     * 备注
     */
    private String remark;

    /**
     * 原价
     */
    private BigDecimal oldprice;

    /**
     * 促销价
     */
    private BigDecimal price;
    /**
     * 捆绑时商品原价
     */
    private BigDecimal goodsoldprice;

    /**
     * 捆绑时促销价
     */
    private BigDecimal goodsprice;

    /**
     * 促销数量
     */
    private BigDecimal limitnum;

    /**
     * 剩余数量
     */
    private BigDecimal remainnum;

    /**
     * 买赠商品的基本商品编号
     */
    private String buygoodsid;

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
     * @return 单据状态（由单据中过来）
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            单据状态（由单据中过来）
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * @return 活动类型1买赠2捆绑
     */
    public String getPtype() {
        return ptype;
    }

    /**
     * @param ptype
     *            活动类型1买赠2捆绑
     */
    public void setPtype(String ptype) {
        this.ptype = ptype == null ? null : ptype.trim();
    }

    /**
     * @return 开始生效日期
     */
    public String getBegindate() {
        return begindate;
    }

    /**
     * @param begindate
     *            开始生效日期
     */
    public void setBegindate(String begindate) {
        this.begindate = begindate == null ? null : begindate.trim();
    }

    /**
     * @return 有效截止日期
     */
    public String getEnddate() {
        return enddate;
    }

    /**
     * @param enddate
     *            有效截止日期
     */
    public void setEnddate(String enddate) {
        this.enddate = enddate == null ? null : enddate.trim();
    }

    /**
     * @return 促销产品编号
     */
    public String getGroupid() {
        return groupid;
    }

    /**
     * @param groupid
     *            促销产品编号
     */
    public void setGroupid(String groupid) {
        this.groupid = groupid == null ? null : groupid.trim();
    }

    /**
     * @return 促销产品名称
     */
    public String getGroupname() {
        return groupname;
    }

    /**
     * @param groupname
     *            促销产品名称
     */
    public void setGroupname(String groupname) {
        this.groupname = groupname == null ? null : groupname.trim();
    }

    /**
     * @return 产品组名称拼音
     */
    public String getPinyin() {
        return pinyin;
    }

    /**
     * @param pinyin
     *            产品组名称拼音
     */
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin == null ? null : pinyin.trim();
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
     * @return 促销价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price
     *            促销价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return 促销数量
     */
    public BigDecimal getLimitnum() {
        return limitnum;
    }

    /**
     * @param limitnum
     *            促销数量
     */
    public void setLimitnum(BigDecimal limitnum) {
        this.limitnum = limitnum;
    }

    /**
     * @return 剩余数量
     */
    public BigDecimal getRemainnum() {
        return remainnum;
    }

    /**
     * @param remainnum
     *            剩余数量
     */
    public void setRemainnum(BigDecimal remainnum) {
        this.remainnum = remainnum;
    }

    public List<PromotionPackageGroupDetail> getGroupDetails() {
        return groupDetails;
    }

    public void setGroupDetails(List<PromotionPackageGroupDetail> groupDetails) {
        this.groupDetails = groupDetails;
    }

    public BigDecimal getBoxnum() {
        return boxnum;
    }

    public void setBoxnum(BigDecimal boxnum) {
        this.boxnum = boxnum;
    }

    public BigDecimal getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(BigDecimal unitprice) {
        this.unitprice = unitprice;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBoxprice() {
        return boxprice;
    }

    public void setBoxprice(String boxprice) {
        this.boxprice = boxprice;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getTotaloldprice() {
        return totaloldprice;
    }

    public void setTotaloldprice(String totaloldprice) {
        this.totaloldprice = totaloldprice;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public Integer getPrinttimes() {
        return printtimes;
    }

    public void setPrinttimes(Integer printtimes) {
        this.printtimes = printtimes;
    }

    public String getGtype() {
        return gtype;
    }

    public void setGtype(String gtype) {
        this.gtype = gtype;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public BigDecimal getAuxnum() {
        return auxnum;
    }

    public void setAuxnum(BigDecimal auxnum) {
        this.auxnum = auxnum;
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

    public BigDecimal getAuxremainder() {
        return auxremainder;
    }

    public void setAuxremainder(BigDecimal auxremainder) {
        this.auxremainder = auxremainder;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public String getAuxunitname() {
        return auxunitname;
    }

    public void setAuxunitname(String auxunitname) {
        this.auxunitname = auxunitname;
    }

    public String getGroupid1() {
        return groupid1;
    }

    public void setGroupid1(String groupid1) {
        this.groupid1 = groupid1;
    }

    public BigDecimal getGoodsoldprice() {
        return goodsoldprice;
    }

    public void setGoodsoldprice(BigDecimal goodsoldprice) {
        this.goodsoldprice = goodsoldprice;
    }

    public BigDecimal getGoodsprice() {
        return goodsprice;
    }

    public void setGoodsprice(BigDecimal goodsprice) {
        this.goodsprice = goodsprice;
    }

    public String getBuygoodsid() {
        return buygoodsid;
    }

    public void setBuygoodsid(String buygoodsid) {
        this.buygoodsid = buygoodsid;
    }


}