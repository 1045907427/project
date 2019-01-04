package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 按品牌业务员销售情况统计报表
 * @author chenwei
 */
public class SalesBranduserReport implements Serializable {
    private static final long serialVersionUID = 1L;

    private String treeid;
    private String state;
    private String parentid;
    /**
     * 编号
     */
    private Integer id;

    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 品牌业务员
     */
    private String branduser;
    
    /**
     * 品牌业务员名称
     */
    private String brandusername;
    
    /**
     * 商品品牌
     */
    private String brandname;

    /**
     * 订单数量
     */
    private BigDecimal ordernum;
    /**
     * 订单金额
     */
    private BigDecimal orderamount;
    /**
     * 订单无税金额
     */
    private BigDecimal ordernotaxamount;
    /**
     * 发货单数量
     */
    private BigDecimal initsendnum;
    /**
     * 发货单金额
     */
    private BigDecimal initsendamount;
    /**
     * 发货单未税金额
     */
    private BigDecimal initsendnotaxamount;	
    /**
     * 发货数量
     */
    private BigDecimal sendnum;

    /**
     * 发货金额
     */
    private BigDecimal sendamount;
    
    /**
     * 发货税额
     */
    private BigDecimal sendtaxamount;

    /**
     * 无税发货金额
     */
    private BigDecimal sendnotaxamount;

    /**
     * 退货数量
     */
    private BigDecimal returnnum;

    /**
     * 退货金额
     */
    private BigDecimal returnamount;

    /**
     * 无税退货金额
     */
    private BigDecimal returnnotaxamount;

    /**
     * 直退数量
     */
    private BigDecimal directreturnnum;

    /**
     * 直退金额
     */
    private BigDecimal directreturnamount;

    /**
     * 无税直退金额
     */
    private BigDecimal directreturnnotaxamount;

    /**
     * 验收退货数量
     */
    private BigDecimal checkreturnnum;

    /**
     * 验收退货金额
     */
    private BigDecimal checkreturnamount;

    /**
     * 无税售后退货金额
     */
    private BigDecimal checkreturnnotaxamount;

    /**
     * 销售额
     */
    private BigDecimal saleamount;
    
    /**
     * 销售税额
     */
    private BigDecimal saletax;

    /**
     * 无税销售金额
     */
    private BigDecimal salenotaxamount;
    
    /**
     * 成本金额
     */
    private BigDecimal costamount;
    
    /**
     * 毛利额
     */
    private BigDecimal salemarginamount;
    
    /**
     * 实际毛利率
     */
    private BigDecimal realrate;

    /**
     * 添加时间
     */
    private Date addtime;
    /**
     * 回笼金额
     */
    private BigDecimal writeoffamount;
    /**
     * 回笼成本金额
     */
    private BigDecimal costwriteoffamount;
    /**
     * 回笼毛利率
     */
    private BigDecimal writeoffrate;

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
     * @return 品牌业务员
     */
    public String getBranduser() {
        return branduser;
    }

    /**
     * @param branduser 
	 *            品牌业务员
     */
    public void setBranduser(String branduser) {
        this.branduser = branduser == null ? null : branduser.trim();
    }

    /**
     * @return 发货数量
     */
    public BigDecimal getSendnum() {
        return sendnum;
    }

    /**
     * @param sendnum 
	 *            发货数量
     */
    public void setSendnum(BigDecimal sendnum) {
        this.sendnum = sendnum;
    }

    /**
     * @return 发货金额
     */
    public BigDecimal getSendamount() {
        return sendamount;
    }

    /**
     * @param sendamount 
	 *            发货金额
     */
    public void setSendamount(BigDecimal sendamount) {
        this.sendamount = sendamount;
    }

    /**
     * @return 无税发货金额
     */
    public BigDecimal getSendnotaxamount() {
        return sendnotaxamount;
    }

    /**
     * @param sendnotaxamount 
	 *            无税发货金额
     */
    public void setSendnotaxamount(BigDecimal sendnotaxamount) {
        this.sendnotaxamount = sendnotaxamount;
    }

    /**
     * @return 退货数量
     */
    public BigDecimal getReturnnum() {
        return returnnum;
    }

    /**
     * @param returnnum 
	 *            退货数量
     */
    public void setReturnnum(BigDecimal returnnum) {
        this.returnnum = returnnum;
    }

    /**
     * @return 退货金额
     */
    public BigDecimal getReturnamount() {
        return returnamount;
    }

    /**
     * @param returnamount 
	 *            退货金额
     */
    public void setReturnamount(BigDecimal returnamount) {
        this.returnamount = returnamount;
    }

    /**
     * @return 无税退货金额
     */
    public BigDecimal getReturnnotaxamount() {
        return returnnotaxamount;
    }

    /**
     * @param returnnotaxamount 
	 *            无税退货金额
     */
    public void setReturnnotaxamount(BigDecimal returnnotaxamount) {
        this.returnnotaxamount = returnnotaxamount;
    }

    /**
     * @return 直退数量
     */
    public BigDecimal getDirectreturnnum() {
        return directreturnnum;
    }

    /**
     * @param directreturnnum 
	 *            直退数量
     */
    public void setDirectreturnnum(BigDecimal directreturnnum) {
        this.directreturnnum = directreturnnum;
    }

    /**
     * @return 直退金额
     */
    public BigDecimal getDirectreturnamount() {
        return directreturnamount;
    }

    /**
     * @param directreturnamount 
	 *            直退金额
     */
    public void setDirectreturnamount(BigDecimal directreturnamount) {
        this.directreturnamount = directreturnamount;
    }

    /**
     * @return 无税直退金额
     */
    public BigDecimal getDirectreturnnotaxamount() {
        return directreturnnotaxamount;
    }

    /**
     * @param directreturnnotaxamount 
	 *            无税直退金额
     */
    public void setDirectreturnnotaxamount(BigDecimal directreturnnotaxamount) {
        this.directreturnnotaxamount = directreturnnotaxamount;
    }

    /**
     * @return 验收退货数量
     */
    public BigDecimal getCheckreturnnum() {
        return checkreturnnum;
    }

    /**
     * @param checkreturnnum 
	 *            验收退货数量
     */
    public void setCheckreturnnum(BigDecimal checkreturnnum) {
        this.checkreturnnum = checkreturnnum;
    }

    /**
     * @return 验收退货金额
     */
    public BigDecimal getCheckreturnamount() {
        return checkreturnamount;
    }

    /**
     * @param checkreturnamount 
	 *            验收退货金额
     */
    public void setCheckreturnamount(BigDecimal checkreturnamount) {
        this.checkreturnamount = checkreturnamount;
    }

    /**
     * @return 无税售后退货金额
     */
    public BigDecimal getCheckreturnnotaxamount() {
        return checkreturnnotaxamount;
    }

    /**
     * @param checkreturnnotaxamount 
	 *            无税售后退货金额
     */
    public void setCheckreturnnotaxamount(BigDecimal checkreturnnotaxamount) {
        this.checkreturnnotaxamount = checkreturnnotaxamount;
    }

    /**
     * @return 销售额
     */
    public BigDecimal getSaleamount() {
        return saleamount;
    }

    /**
     * @param saleamount 
	 *            销售额
     */
    public void setSaleamount(BigDecimal saleamount) {
        this.saleamount = saleamount;
    }

    /**
     * @return 无税销售金额
     */
    public BigDecimal getSalenotaxamount() {
        return salenotaxamount;
    }

    /**
     * @param salenotaxamount 
	 *            无税销售金额
     */
    public void setSalenotaxamount(BigDecimal salenotaxamount) {
        this.salenotaxamount = salenotaxamount;
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
     * 品牌业务员名称
     * @return
     * @author panxiaoxiao 
     * @date Aug 14, 2013
     */
	public String getBrandusername() {
		return brandusername;
	}

	/**
	 * 品牌业务员名称
	 * @param brandusername
	 * @author panxiaoxiao 
	 * @date Aug 14, 2013
	 */
	public void setBrandusername(String brandusername) {
		this.brandusername = brandusername;
	}

	/**
	 * 发货税额
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 14, 2013
	 */
	public BigDecimal getSendtaxamount() {
		return sendtaxamount;
	}

	/**
	 * 发货税额
	 * @param sendtaxamount
	 * @author panxiaoxiao 
	 * @date Aug 14, 2013
	 */
	public void setSendtaxamount(BigDecimal sendtaxamount) {
		this.sendtaxamount = sendtaxamount;
	}

	/**
	 * 成本金额
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 14, 2013
	 */
	public BigDecimal getCostamount() {
		if(null!=costamount && costamount.compareTo(BigDecimal.ZERO)==0){
			return BigDecimal.ZERO;
		}else{
			return costamount;
		}
	}

	/**
	 * 成本金额
	 * @param costamount
	 * @author panxiaoxiao 
	 * @date Aug 14, 2013
	 */
	public void setCostamount(BigDecimal costamount) {
		this.costamount = costamount;
	}

	/**
	 * 实际毛利率
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 14, 2013
	 */
	public BigDecimal getRealrate() {
		return realrate;
	}

	/**
	 * 实际毛利率
	 * @param realrate
	 * @author panxiaoxiao 
	 * @date Aug 14, 2013
	 */
	public void setRealrate(BigDecimal realrate) {
		this.realrate = realrate;
	}

	public BigDecimal getSaletax() {
		return saletax;
	}

	public void setSaletax(BigDecimal saletax) {
		this.saletax = saletax;
	}

	public String getTreeid() {
		return treeid;
	}

	public void setTreeid(String treeid) {
		this.treeid = treeid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public BigDecimal getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(BigDecimal ordernum) {
		this.ordernum = ordernum;
	}

	public BigDecimal getOrderamount() {
		return orderamount;
	}

	public void setOrderamount(BigDecimal orderamount) {
		this.orderamount = orderamount;
	}

	public BigDecimal getOrdernotaxamount() {
		return ordernotaxamount;
	}

	public void setOrdernotaxamount(BigDecimal ordernotaxamount) {
		this.ordernotaxamount = ordernotaxamount;
	}

	public BigDecimal getInitsendnum() {
		return initsendnum;
	}

	public void setInitsendnum(BigDecimal initsendnum) {
		this.initsendnum = initsendnum;
	}

	public BigDecimal getInitsendamount() {
		return initsendamount;
	}

	public void setInitsendamount(BigDecimal initsendamount) {
		this.initsendamount = initsendamount;
	}

	public BigDecimal getInitsendnotaxamount() {
		return initsendnotaxamount;
	}

	public void setInitsendnotaxamount(BigDecimal initsendnotaxamount) {
		this.initsendnotaxamount = initsendnotaxamount;
	}

	public BigDecimal getWriteoffamount() {
		return writeoffamount;
	}

	public void setWriteoffamount(BigDecimal writeoffamount) {
		this.writeoffamount = writeoffamount;
	}

	public BigDecimal getCostwriteoffamount() {
		return costwriteoffamount;
	}

	public void setCostwriteoffamount(BigDecimal costwriteoffamount) {
		this.costwriteoffamount = costwriteoffamount;
	}

	public BigDecimal getWriteoffrate() {
		return writeoffrate;
	}

	public void setWriteoffrate(BigDecimal writeoffrate) {
		this.writeoffrate = writeoffrate;
	}

	public BigDecimal getSalemarginamount() {
		return salemarginamount;
	}

	public void setSalemarginamount(BigDecimal salemarginamount) {
		this.salemarginamount = salemarginamount;
	}
    
}