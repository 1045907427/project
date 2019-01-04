package com.hd.agent.account.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PaymentVoucherDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 采购进货单编号
     */
    private String orderid;

    /**
     * 客户
     */
    private String customerid;

    /**
     * 客户名称
     */
    private String customername;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 备注
     */
    private String remark;
    
    /**
     * 表体自定义项1
     */
    private String field01;

    /**
     * 表体自定义项2
     */
    private String field02;

    /**
     * 表体自定义项3
     */
    private String field03;

    /**
     * 表体自定义项4
     */
    private String field04;

    /**
     * 表体自定义项5
     */
    private String field05;

    /**
     * 表体自定义项6
     */
    private String field06;

    /**
     * 表体自定义项7
     */
    private String field07;

    /**
     * 表体自定义项8
     */
    private String field08;

    /**
     * 排序(订单明细显示序号)
     */
    private Integer seq;


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
     * @return 采购进货单编号
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     * @param orderid 
	 *            采购进货单编号
     */
    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    /**
     * @return 客户
     */
    public String getCustomerid() {
        return customerid;
    }

    /**
     * @param customerid 
	 *            客户
     */
    public void setCustomerid(String customerid) {
        this.customerid = customerid == null ? null : customerid.trim();
    }

    /**
     * @return 金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount 
	 *            金额
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
    
    /**
     * @return 表体自定义项1
     */
    public String getField01() {
        return field01;
    }

    /**
     * @param field01 
	 *            表体自定义项1
     */
    public void setField01(String field01) {
        this.field01 = field01 == null ? null : field01.trim();
    }

    /**
     * @return 表体自定义项2
     */
    public String getField02() {
        return field02;
    }

    /**
     * @param field02 
	 *            表体自定义项2
     */
    public void setField02(String field02) {
        this.field02 = field02 == null ? null : field02.trim();
    }

    /**
     * @return 表体自定义项3
     */
    public String getField03() {
        return field03;
    }

    /**
     * @param field03 
	 *            表体自定义项3
     */
    public void setField03(String field03) {
        this.field03 = field03 == null ? null : field03.trim();
    }

    /**
     * @return 表体自定义项4
     */
    public String getField04() {
        return field04;
    }

    /**
     * @param field04 
	 *            表体自定义项4
     */
    public void setField04(String field04) {
        this.field04 = field04 == null ? null : field04.trim();
    }

    /**
     * @return 表体自定义项5
     */
    public String getField05() {
        return field05;
    }

    /**
     * @param field05 
	 *            表体自定义项5
     */
    public void setField05(String field05) {
        this.field05 = field05 == null ? null : field05.trim();
    }

    /**
     * @return 表体自定义项6
     */
    public String getField06() {
        return field06;
    }

    /**
     * @param field06 
	 *            表体自定义项6
     */
    public void setField06(String field06) {
        this.field06 = field06 == null ? null : field06.trim();
    }

    /**
     * @return 表体自定义项7
     */
    public String getField07() {
        return field07;
    }

    /**
     * @param field07 
	 *            表体自定义项7
     */
    public void setField07(String field07) {
        this.field07 = field07 == null ? null : field07.trim();
    }

    /**
     * @return 表体自定义项8
     */
    public String getField08() {
        return field08;
    }

    /**
     * @param field08 
	 *            表体自定义项8
     */
    public void setField08(String field08) {
        this.field08 = field08 == null ? null : field08.trim();
    }

    /**
     * @return 排序(订单明细显示序号)
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * @param seq 
	 *            排序(订单明细显示序号)
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}
}