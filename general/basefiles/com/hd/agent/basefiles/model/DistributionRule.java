package com.hd.agent.basefiles.model;

import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.util.Date;

public class DistributionRule implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 状态
     */
    private String state;

    /**
     * 备注
     */
    private String remark;

    /**
     * 建档人
     */
    private String adduserid;

    /**
     * 建档人
     */
    private String addusername;

    /**
     * 建档部门
     */
    private String adddeptid;

    /**
     * 建档部门
     */
    private String adddeptname;

    /**
     * 建档时间
     */
    private Date addtime;

    /**
     * 最后修改人
     */
    private String modifyuserid;

    /**
     * 最后修改人
     */
    private String modifyusername;

    /**
     * 最后修改时间
     */
    private Date modifytime;

    /**
     * 启用人
     */
    private String openuserid;

    /**
     * 启用人
     */
    private String openusername;

    /**
     * 启用时间
     */
    private Date opentime;

    /**
     * 禁用人
     */
    private String closeuserid;

    /**
     * 禁用人
     */
    private String closeusername;

    /**
     * 禁用时间
     */
    private Date closetime;

    /**
     * 客户群。1：按单一客户；2：按总店；3；按客户分类；4：按促销分类；5：按销售区域；6：按信用等级；7：按核销方式
     */
    private String customertype;

    /**
     * 商品规则；1：按商品；2：按品牌；3：按商品分类；4：按商品类型；5：按供应商；
     */
    private String goodsruletype;

    /**
     * 客户编码
     */
    private String customerid;

    /**
     * 总店客户
     */
    private String pcustomerid;

    /**
     * 客户分类
     */
    private String customersort;

    /**
     * 促销分类
     */
    private String promotionsort;

    /**
     * 销售区域
     */
    private String salesarea;

    /**
     * 信用等级
     */
    private String creditrating;

    /**
     * 核销方式
     */
    private String canceltype;

    /**
     * 客户名称
     */
    private String customername;

    /**
     * 总店客户名称
     */
    private String pcustomername;

    /**
     * 客户分类名称
     */
    private String customersortname;

    /**
     * 促销分类名称
     */
    private String promotionsortname;

    /**
     * 销售区域名称
     */
    private String salesareaname;

    /**
     * 信用等级名称
     */
    private String creditratingname;

    /**
     * 核销方式
     */
    private String canceltypename;

    /**
     * 是否可购买 1：是 0：否
     */
    private String canbuy;

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
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return 业务日期
     */
    public String getBusinessdate() {
        return businessdate;
    }

    /**
     * @param businessdate 
	 *            业务日期
     */
    public void setBusinessdate(String businessdate) {
        this.businessdate = businessdate == null ? null : businessdate.trim();
    }

    /**
     * @return 状态
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
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
     * @return 建档人
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            建档人
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 建档人
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            建档人
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
    }

    /**
     * @return 建档部门
     */
    public String getAdddeptid() {
        return adddeptid;
    }

    /**
     * @param adddeptid 
	 *            建档部门
     */
    public void setAdddeptid(String adddeptid) {
        this.adddeptid = adddeptid == null ? null : adddeptid.trim();
    }

    /**
     * @return 建档部门
     */
    public String getAdddeptname() {
        return adddeptname;
    }

    /**
     * @param adddeptname 
	 *            建档部门
     */
    public void setAdddeptname(String adddeptname) {
        this.adddeptname = adddeptname == null ? null : adddeptname.trim();
    }

    /**
     * @return 建档时间
     */
    @JSON(format = "yyyy-MM-dd HH:mm:ss")
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            建档时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * @return 最后修改人
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid 
	 *            最后修改人
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
    }

    /**
     * @return 最后修改人
     */
    public String getModifyusername() {
        return modifyusername;
    }

    /**
     * @param modifyusername 
	 *            最后修改人
     */
    public void setModifyusername(String modifyusername) {
        this.modifyusername = modifyusername == null ? null : modifyusername.trim();
    }

    /**
     * @return 最后修改时间
     */
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * @param modifytime 
	 *            最后修改时间
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * @return 启用人
     */
    public String getOpenuserid() {
        return openuserid;
    }

    /**
     * @param openuserid 
	 *            启用人
     */
    public void setOpenuserid(String openuserid) {
        this.openuserid = openuserid == null ? null : openuserid.trim();
    }

    /**
     * @return 启用人
     */
    public String getOpenusername() {
        return openusername;
    }

    /**
     * @param openusername 
	 *            启用人
     */
    public void setOpenusername(String openusername) {
        this.openusername = openusername == null ? null : openusername.trim();
    }

    /**
     * @return 启用时间
     */
    public Date getOpentime() {
        return opentime;
    }

    /**
     * @param opentime 
	 *            启用时间
     */
    public void setOpentime(Date opentime) {
        this.opentime = opentime;
    }

    /**
     * @return 禁用人
     */
    public String getCloseuserid() {
        return closeuserid;
    }

    /**
     * @param closeuserid 
	 *            禁用人
     */
    public void setCloseuserid(String closeuserid) {
        this.closeuserid = closeuserid == null ? null : closeuserid.trim();
    }

    /**
     * @return 禁用人
     */
    public String getCloseusername() {
        return closeusername;
    }

    /**
     * @param closeusername 
	 *            禁用人
     */
    public void setCloseusername(String closeusername) {
        this.closeusername = closeusername == null ? null : closeusername.trim();
    }

    /**
     * @return 禁用时间
     */
    public Date getClosetime() {
        return closetime;
    }

    /**
     * @param closetime 
	 *            禁用时间
     */
    public void setClosetime(Date closetime) {
        this.closetime = closetime;
    }

    /**
     * @return 客户群。1：按单一客户；2：按总店；3；按客户分类；4：按促销分类；5：按销售区域；6：按信用等级；7：按核销方式
     */
    public String getCustomertype() {
        return customertype;
    }

    /**
     * @param customertype 
	 *            客户群。1：按单一客户；2：按总店；3；按客户分类；4：按促销分类；5：按销售区域；6：按信用等级；7：按核销方式
     */
    public void setCustomertype(String customertype) {
        this.customertype = customertype == null ? null : customertype.trim();
    }

    /**
     * @return 商品规则；1：按商品；2：按品牌；3：按商品分类；4：按商品类型；5：按供应商；
     */
    public String getGoodsruletype() {
        return goodsruletype;
    }

    /**
     * @param goodsruletype 
	 *            商品规则；1：按商品；2：按品牌；3：按商品分类；4：按商品类型；5：按供应商；
     */
    public void setGoodsruletype(String goodsruletype) {
        this.goodsruletype = goodsruletype == null ? null : goodsruletype.trim();
    }

    /**
     * @return 客户编码
     */
    public String getCustomerid() {
        return customerid;
    }

    /**
     * @param customerid 
	 *            客户编码
     */
    public void setCustomerid(String customerid) {
        this.customerid = customerid == null ? null : customerid.trim();
    }

    /**
     * @return 总店客户
     */
    public String getPcustomerid() {
        return pcustomerid;
    }

    /**
     * @param pcustomerid 
	 *            总店客户
     */
    public void setPcustomerid(String pcustomerid) {
        this.pcustomerid = pcustomerid == null ? null : pcustomerid.trim();
    }

    /**
     * @return 客户分类
     */
    public String getCustomersort() {
        return customersort;
    }

    /**
     * @param customersort 
	 *            客户分类
     */
    public void setCustomersort(String customersort) {
        this.customersort = customersort == null ? null : customersort.trim();
    }

    /**
     * @return 促销分类
     */
    public String getPromotionsort() {
        return promotionsort;
    }

    /**
     * @param promotionsort 
	 *            促销分类
     */
    public void setPromotionsort(String promotionsort) {
        this.promotionsort = promotionsort == null ? null : promotionsort.trim();
    }

    /**
     * @return 销售区域
     */
    public String getSalesarea() {
        return salesarea;
    }

    /**
     * @param salesarea 
	 *            销售区域
     */
    public void setSalesarea(String salesarea) {
        this.salesarea = salesarea == null ? null : salesarea.trim();
    }

    /**
     * @return 信用等级
     */
    public String getCreditrating() {
        return creditrating;
    }

    /**
     * @param creditrating 
	 *            信用等级
     */
    public void setCreditrating(String creditrating) {
        this.creditrating = creditrating == null ? null : creditrating.trim();
    }

    /**
     * @return 核销方式
     */
    public String getCanceltype() {
        return canceltype;
    }

    /**
     * @param canceltype 
	 *            核销方式
     */
    public void setCanceltype(String canceltype) {
        this.canceltype = canceltype == null ? null : canceltype.trim();
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getPcustomername() {
        return pcustomername;
    }

    public void setPcustomername(String pcustomername) {
        this.pcustomername = pcustomername;
    }

    public String getCustomersortname() {
        return customersortname;
    }

    public void setCustomersortname(String customersortname) {
        this.customersortname = customersortname;
    }

    public String getPromotionsortname() {
        return promotionsortname;
    }

    public void setPromotionsortname(String promotionsortname) {
        this.promotionsortname = promotionsortname;
    }

    public String getSalesareaname() {
        return salesareaname;
    }

    public void setSalesareaname(String salesareaname) {
        this.salesareaname = salesareaname;
    }

    public String getCreditratingname() {
        return creditratingname;
    }

    public void setCreditratingname(String creditratingname) {
        this.creditratingname = creditratingname;
    }

    public String getCanceltypename() {
        return canceltypename;
    }

    public void setCanceltypename(String canceltypename) {
        this.canceltypename = canceltypename;
    }

    public String getCanbuy() {
        return canbuy;
    }

    public void setCanbuy(String canbuy) {
        this.canbuy = canbuy;
    }
}