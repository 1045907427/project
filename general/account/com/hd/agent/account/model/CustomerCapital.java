package com.hd.agent.account.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 客户资金情况（余额）
 * @author chenwei
 */
public class CustomerCapital implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 客户编号
     */
    private String id;
    /**
     * 客户名称
     */
    private String customername;
    
    /**
     * 资金余额
     */
    private BigDecimal amount;

    /**
     * 备注
     */
    private String remark;

    private Date addtime;

    private Date modifytime;

    /**
     * @return 客户编号
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            客户编号
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return 资金余额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount 
	 *            资金余额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}
    
}