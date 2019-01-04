package com.hd.agent.report.model;

import java.io.Serializable;
/**
 * 超账期设置
 * @author chenwei
 */
public class PaymentdaysSet implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 天数
     */
    private Integer days;

    /**
     * 区间描述
     */
    private String detail;

    /**
     * 开始天数
     */
    private Integer beginday;

    /**
     * 结束天数
     */
    private Integer endday;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 账期类型1应收款2回笼资金
     */
    private String type;

    /**
     * 添加人编号
     */
    private String adduserid;

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
     * @return 天数
     */
    public Integer getDays() {
        return days;
    }

    /**
     * @param days 
	 *            天数
     */
    public void setDays(Integer days) {
        this.days = days;
    }

    /**
     * @return 区间描述
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @param detail 
	 *            区间描述
     */
    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    /**
     * @return 开始天数
     */
    public Integer getBeginday() {
        return beginday;
    }

    /**
     * @param beginday 
	 *            开始天数
     */
    public void setBeginday(Integer beginday) {
        this.beginday = beginday;
    }

    /**
     * @return 结束天数
     */
    public Integer getEndday() {
        return endday;
    }

    /**
     * @param endday 
	 *            结束天数
     */
    public void setEndday(Integer endday) {
        this.endday = endday;
    }

    /**
     * @return 排序
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * @param seq 
	 *            排序
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    /**
     * @return 账期类型1应收款2回笼资金
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            账期类型1应收款2回笼资金
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * @return 添加人编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            添加人编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }
}