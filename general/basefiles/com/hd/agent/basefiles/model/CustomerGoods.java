/**
 * @(#)CustomerGoods.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 10, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.model;
/**
 * 
 * 
 * @author panxiaoxiao
 */
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CustomerGoods implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    private Integer id;

    /**
     * 客户编号
     */
    private String customerid;
    
    /**
     * 客户名称
     */
    private String customername;

    /**
     * 商品编号
     */
    private String goodsid;

    /**
     * 商品名称
     */
    private String goodsname;
    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 备注
     */
    private String remark;
    /**
     * 最后一次交易时间
     */
    private Date lastdate;
    /**
     * @return 编码
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            编码
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 客户编号
     */
    public String getCustomerid() {
        return customerid;
    }

    /**
     * @param customerid 
	 *            客户编号
     */
    public void setCustomerid(String customerid) {
        this.customerid = customerid == null ? null : customerid.trim();
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
     * @return 价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price 
	 *            价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
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

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public Date getLastdate() {
		return lastdate;
	}

	public void setLastdate(Date lastdate) {
		this.lastdate = lastdate;
	}
	
}

