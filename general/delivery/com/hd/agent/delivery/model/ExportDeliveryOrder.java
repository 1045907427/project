/**
 * @(#)ExportDeliveryOrder.java
 *
 * @author wanghongteng
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年8月31日 wanghongteng 创建版本
 */
package com.hd.agent.delivery.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * @author wanghongteng
 */
/**
 * @author Administrator
 *
 */
public class ExportDeliveryOrder implements Serializable {
	 private static final long serialVersionUID = 1L;

	private String id;
		/**
		 * 业务日期
		 */
		private String businessdate;
		/**
		 * 客户编号
		 */
		private String customerid;
		/**
		 * 客户名称
		 */
		private String customername;
		/**
		 * 仓库编号
		 */
		private String storageid;
		/**
		 * 仓库名称
		 */
		private String storagename;
		/**
		 * 商品编号
		 */
		private String goodsid;
		/**
		 * 商品名称
		 */
		private String goodsname;
		/**
		 * 商品分类
		 */
		private String goodssort;
		/**
		 * 品牌编码
		 */
		private String brandid;
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
		private int auxnum;
		/**
		 * 辅数量
		 */
		private String auxnumdetail;
		/**
		 * 主单位余数
		 */
		private BigDecimal overnum;
		/**
		 * 合计箱数
		 */
		private BigDecimal totalboxDetail;
		/**
		 * 价格
		 */
		private BigDecimal price;
		/**
		 * 金额
		 */
		private BigDecimal taxamount;
		/**
		 * 备注
		 */
		private String remark;
		
		
		
		/**
		 * 总店客户'
		 */
		private String pcustomerid;
		/**
		 * 客户分类
		 */
		private String customersort;
		/**
		 * 所属部门
		 */
		private String deptid;
		
		private String supplierid;
	private String suppliername;

	    private String adduserid;
		private String addusername;
	private String adddeptname;
		private Date addtime;
		private BigDecimal totalvolume;
		private BigDecimal totalweight;
		private BigDecimal totalbox;
		private BigDecimal totalamount;
		private String mainremark;
	/**
	 * 客户单号
	 */
	private String sourceid;

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
		public String getCustomerid() {
			return customerid;
		}
		public void setCustomerid(String customerid) {
			this.customerid = customerid;
		}
		public String getCustomername() {
			return customername;
		}
		public void setCustomername(String customername) {
			this.customername = customername;
		}
		public String getStorageid() {
			return storageid;
		}
		public void setStorageid(String storageid) {
			this.storageid = storageid;
		}
		public String getStoragename() {
			return storagename;
		}
		public void setStoragename(String storagename) {
			this.storagename = storagename;
		}
		public String getGoodsid() {
			return goodsid;
		}
		public void setGoodsid(String goodsid) {
			this.goodsid = goodsid;
		}
		public String getGoodsname() {
			return goodsname;
		}
		public void setGoodsname(String goodsname) {
			this.goodsname = goodsname;
		}
		public String getGoodssort() {
			return goodssort;
		}
		public void setGoodssort(String goodssort) {
			this.goodssort = goodssort;
		}
		public String getBrandid() {
			return brandid;
		}
		public void setBrandid(String brandid) {
			this.brandid = brandid;
		}
		public String getUnitid() {
			return unitid;
		}
		public void setUnitid(String unitid) {
			this.unitid = unitid;
		}
		public String getUnitname() {
			return unitname;
		}
		public void setUnitname(String unitname) {
			this.unitname = unitname;
		}
		public BigDecimal getUnitnum() {
			return unitnum;
		}
		public void setUnitnum(BigDecimal unitnum) {
			this.unitnum = unitnum;
		}
		public String getAuxunitid() {
			return auxunitid;
		}
		public void setAuxunitid(String auxunitid) {
			this.auxunitid = auxunitid;
		}
		public String getAuxunitname() {
			return auxunitname;
		}
		public void setAuxunitname(String auxunitname) {
			this.auxunitname = auxunitname;
		}
		public int getAuxnum() {
			return auxnum;
		}
		public void setAuxnum(int auxnum) {
			this.auxnum = auxnum;
		}
		public String getAuxnumdetail() {
			return auxnumdetail;
		}
		public void setAuxnumdetail(String auxnumdetail) {
			this.auxnumdetail = auxnumdetail;
		}
		public BigDecimal getOvernum() {
			return overnum;
		}
		public void setOvernum(BigDecimal overnum) {
			this.overnum = overnum;
		}
		public BigDecimal getTotalboxDetail() {
			return totalboxDetail;
		}
		public void setTotalboxDetail(BigDecimal totalboxDetail) {
			this.totalboxDetail = totalboxDetail;
		}
		public BigDecimal getPrice() {
			return price;
		}
		public void setPrice(BigDecimal price) {
			this.price = price;
		}
		public BigDecimal getTaxamount() {
			return taxamount;
		}
		public void setTaxamount(BigDecimal taxamount) {
			this.taxamount = taxamount;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getPcustomerid() {
			return pcustomerid;
		}
		public void setPcustomerid(String pcustomerid) {
			this.pcustomerid = pcustomerid;
		}
		public String getCustomersort() {
			return customersort;
		}
		public void setCustomersort(String customersort) {
			this.customersort = customersort;
		}
		public String getDeptid() {
			return deptid;
		}
		public void setDeptid(String deptid) {
			this.deptid = deptid;
		}
		public String getSupplierid() {
			return supplierid;
		}
		public void setSupplierid(String supplierid) {
			this.supplierid = supplierid;
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

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public BigDecimal getTotalvolume() {
		return totalvolume;
	}

	public void setTotalvolume(BigDecimal totalvolume) {
		this.totalvolume = totalvolume;
	}

	public BigDecimal getTotalweight() {
		return totalweight;
	}

	public void setTotalweight(BigDecimal totalweight) {
		this.totalweight = totalweight;
	}

	public BigDecimal getTotalbox() {
		return totalbox;
	}

	public void setTotalbox(BigDecimal totalbox) {
		this.totalbox = totalbox;
	}

	public BigDecimal getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(BigDecimal totalamount) {
		this.totalamount = totalamount;
	}

	public String getMainremark() {
		return mainremark;
	}

	public void setMainremark(String mainremark) {
		this.mainremark = mainremark;
	}

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}

	public String getAdddeptname() {
		return adddeptname;
	}

	public void setAdddeptname(String adddeptname) {
		this.adddeptname = adddeptname;
	}

	public String getSourceid() {
		return sourceid;
	}

	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}
}

