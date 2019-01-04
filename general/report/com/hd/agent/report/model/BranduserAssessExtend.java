/**
 * @(#)BranduserAssessExtend.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 2, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hd.agent.common.util.PageMap;

/**
 * 
 * 品牌业务员考核扩展
 * @author panxiaoxiao
 */
public class BranduserAssessExtend implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 编码
	 */
	private String id;
	
	/**
     * 业务日期
     */
    private String businessdate;
    
    /**
     * 品牌业务员编码
     */
    private String branduser;
    
    /**
     * 品牌业务员名称
     */
    private String brandusername;
    
    /**
     * 销售区域
     */
    private String salesarea;
    
    /**
     * 销售区域名称
     */
    private String salesareaname;
    
    /**
     * 回笼目标金额
     */
    private BigDecimal wdtargetamount;
    
    /**
     * 其他回笼
     */
    private BigDecimal otherwdamount;
    
    /**
     * kpi目标金额
     */
    private BigDecimal kpitargetamount;
    
    /**
     * KPI奖金金额
     */
    private BigDecimal kpibonusamount;
    
    /**
     * 区长
     */
    private BigDecimal wardenamount;
    
    /**
     * 超账金额1
     */
    private BigDecimal totalpassamount1;
    
    /**
     * 超账扣减1
     */
    private BigDecimal totalpasssubamount1;
    
    /**
     * 超账金额2
     */
    private BigDecimal totalpassamount2;
    
    /**
     * 超账扣减2
     */
    private BigDecimal totalpasssubamount2;
    
    /**
     * 超账金额3
     */
    private BigDecimal totalpassamount3;
    
    /**
     * 超账扣减3
     */
    private BigDecimal totalpasssubamount3;
    
    /**
     * 超账金额4
     */
    private BigDecimal totalpassamount4;
    
    /**
     * 超账扣减4
     */
    private BigDecimal totalpasssubamount4;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 添加用户编码
     */
    private String adduserid;

    /**
     * 添加用户
     */
    private String addusername;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 最后修改人编号
     */
    private String modifyuserid;

    /**
     * 最后修改人名称
     */
    private String modifyusername;

    /**
     * 最后修改时间
     */
    private Date modifytime;
    
    /**
     * 获取合计列参数
     */
    private PageMap pageMap;

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

	public String getBranduser() {
		return branduser;
	}

	public void setBranduser(String branduser) {
		this.branduser = branduser;
	}

	public String getBrandusername() {
		return brandusername;
	}

	public void setBrandusername(String brandusername) {
		this.brandusername = brandusername;
	}

	public BigDecimal getWdtargetamount() {
		return wdtargetamount;
	}

	public void setWdtargetamount(BigDecimal wdtargetamount) {
		this.wdtargetamount = wdtargetamount;
	}

	public BigDecimal getOtherwdamount() {
		return otherwdamount;
	}

	public void setOtherwdamount(BigDecimal otherwdamount) {
		this.otherwdamount = otherwdamount;
	}

	public BigDecimal getKpitargetamount() {
		return kpitargetamount;
	}

	public void setKpitargetamount(BigDecimal kpitargetamount) {
		this.kpitargetamount = kpitargetamount;
	}

	public BigDecimal getKpibonusamount() {
		return kpibonusamount;
	}

	public void setKpibonusamount(BigDecimal kpibonusamount) {
		this.kpibonusamount = kpibonusamount;
	}

	public BigDecimal getWardenamount() {
		return wardenamount;
	}

	public void setWardenamount(BigDecimal wardenamount) {
		this.wardenamount = wardenamount;
	}

	public BigDecimal getTotalpassamount1() {
		return totalpassamount1;
	}

	public void setTotalpassamount1(BigDecimal totalpassamount1) {
		this.totalpassamount1 = totalpassamount1;
	}

	public BigDecimal getTotalpasssubamount1() {
		return totalpasssubamount1;
	}

	public void setTotalpasssubamount1(BigDecimal totalpasssubamount1) {
		this.totalpasssubamount1 = totalpasssubamount1;
	}

	public BigDecimal getTotalpassamount2() {
		return totalpassamount2;
	}

	public void setTotalpassamount2(BigDecimal totalpassamount2) {
		this.totalpassamount2 = totalpassamount2;
	}

	public BigDecimal getTotalpasssubamount2() {
		return totalpasssubamount2;
	}

	public void setTotalpasssubamount2(BigDecimal totalpasssubamount2) {
		this.totalpasssubamount2 = totalpasssubamount2;
	}

	public BigDecimal getTotalpassamount3() {
		return totalpassamount3;
	}

	public void setTotalpassamount3(BigDecimal totalpassamount3) {
		this.totalpassamount3 = totalpassamount3;
	}

	public BigDecimal getTotalpasssubamount3() {
		return totalpasssubamount3;
	}

	public void setTotalpasssubamount3(BigDecimal totalpasssubamount3) {
		this.totalpasssubamount3 = totalpasssubamount3;
	}

	public BigDecimal getTotalpassamount4() {
		return totalpassamount4;
	}

	public void setTotalpassamount4(BigDecimal totalpassamount4) {
		this.totalpassamount4 = totalpassamount4;
	}

	public BigDecimal getTotalpasssubamount4() {
		return totalpasssubamount4;
	}

	public void setTotalpasssubamount4(BigDecimal totalpasssubamount4) {
		this.totalpasssubamount4 = totalpasssubamount4;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAdduserid() {
		return adduserid;
	}

	public void setAdduserid(String adduserid) {
		this.adduserid = adduserid;
	}

	public String getAddusername() {
		return addusername;
	}

	public void setAddusername(String addusername) {
		this.addusername = addusername;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getModifyuserid() {
		return modifyuserid;
	}

	public void setModifyuserid(String modifyuserid) {
		this.modifyuserid = modifyuserid;
	}

	public String getModifyusername() {
		return modifyusername;
	}

	public void setModifyusername(String modifyusername) {
		this.modifyusername = modifyusername;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	public PageMap getPageMap() {
		return pageMap;
	}

	public void setPageMap(PageMap pageMap) {
		this.pageMap = pageMap;
	}

	public String getSalesarea() {
		return salesarea;
	}

	public void setSalesarea(String salesarea) {
		this.salesarea = salesarea;
	}

	public String getSalesareaname() {
		return salesareaname;
	}

	public void setSalesareaname(String salesareaname) {
		this.salesareaname = salesareaname;
	}
}

