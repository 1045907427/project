package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class SaleMode_Detail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 销售方式编码
     */
    private String salemodeid;

    /**
     * 销售阶段编码
     */
    private String code;

    /**
     * 销售阶段名称
     */
    private String name;

    /**
     * 阶段A发现销售机会B处理交易C正在关闭D失去的交易
     */
    private String stage;

    /**
     * 成功的概率%
     */
    private BigDecimal probability;

    /**
     * 备注
     */
    private String remark;

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
     * @return 销售方式编码
     */
    public String getSalemodeid() {
        return salemodeid;
    }

    /**
     * @param salemodeid 
	 *            销售方式编码
     */
    public void setSalemodeid(String salemodeid) {
        this.salemodeid = salemodeid == null ? null : salemodeid.trim();
    }

    /**
     * @return 销售阶段编码
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code 
	 *            销售阶段编码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * @return 销售阶段名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            销售阶段名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return 阶段A发现销售机会B处理交易C正在关闭D失去的交易
     */
    public String getStage() {
        return stage;
    }

    /**
     * @param stage 
	 *            阶段A发现销售机会B处理交易C正在关闭D失去的交易
     */
    public void setStage(String stage) {
        this.stage = stage == null ? null : stage.trim();
    }

    /**
     * @return 成功的概率%
     */
    public BigDecimal getProbability() {
        return probability;
    }

    /**
     * @param probability 
	 *            成功的概率%
     */
    public void setProbability(BigDecimal probability) {
        this.probability = probability;
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
}