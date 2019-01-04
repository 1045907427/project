package com.hd.agent.system.model;

import java.io.Serializable;
/**
 * 单据编号规则实体类
 * @author pan_xx
 *
 */
public class SysNumberRule implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 单据编号规则细则id
     */
    private String numberruleid;

    /**
     * 单据编号规则id
     */
    private String numberid;

    /**
     * 类型：1固定值2字段3系统日期
     */
    private String coltype;

    /**
     * 值。(当类型为字段时存放字段名)
     */
    private String val;
    
    /**
     * 字段值名称显示
     */
    private String valName;
    
    /**
     * 排序
     */
    private Integer sequencing;

    public Integer getSequencing() {
		return sequencing;
	}

	public void setSequencing(Integer sequencing) {
		this.sequencing = sequencing;
	}

	/**
     * 前缀
     */
    private String prefix;

    /**
     * 后缀
     */
    private String suffix;

    /**
     * 长度
     */
    private Integer length;

    /**
     * 截取方向1:从前向后2从后向前
     */
    private String subtype;

    /**
     * 截取开始位置
     */
    private Integer substart;

    /**
     * 补位符(长度不足时，用该符合补足)
     */
    private String cover;

    /**
     * 是否以该数据作为流水依据:1是0否
     */
    private String state;

    /**
     * @return 单据编号规则细则id
     */
    public String getNumberruleid() {
        return numberruleid;
    }

    /**
     * @param numberruleid 
	 *            单据编号规则细则id
     */
    public void setNumberruleid(String numberruleid) {
        this.numberruleid = numberruleid == null ? null : numberruleid.trim();
    }

    /**
     * @return 单据编号规则id
     */
    public String getNumberid() {
        return numberid;
    }

    /**
     * @param numberid 
	 *            单据编号规则id
     */
    public void setNumberid(String numberid) {
        this.numberid = numberid == null ? null : numberid.trim();
    }

    /**
     * @return 类型：1固定值2字段3系统日期
     */
    public String getColtype() {
        return coltype;
    }

    /**
     * @param coltype 
	 *            类型：1固定值2字段3系统日期
     */
    public void setColtype(String coltype) {
        this.coltype = coltype == null ? null : coltype.trim();
    }

    /**
     * @return 值。(当类型为字段时存放字段名)
     */
    public String getVal() {
        return val;
    }

    /**
     * @param val 
	 *            值。(当类型为字段时存放字段名)
     */
    public void setVal(String val) {
        this.val = val == null ? null : val.trim();
    }

    /**
     * @return 前缀
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @param prefix 
	 *            前缀
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix == null ? null : prefix.trim();
    }

    /**
     * @return 后缀
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * @param suffix 
	 *            后缀
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? null : suffix.trim();
    }

    /**
     * @return 长度
     */
    public Integer getLength() {
        return length;
    }

    /**
     * @param length 
	 *            长度
     */
    public void setLength(Integer length) {
        this.length = length;
    }

    /**
     * @return 截取方向1:从前向后2从后向前
     */
    public String getSubtype() {
        return subtype;
    }

    /**
     * @param subtype 
	 *            截取方向1:从前向后2从后向前
     */
    public void setSubtype(String subtype) {
        this.subtype = subtype == null ? null : subtype.trim();
    }

    /**
     * @return 截取开始位置
     */
    public Integer getSubstart() {
        return substart;
    }

    /**
     * @param substart 
	 *            截取开始位置
     */
    public void setSubstart(Integer substart) {
        this.substart = substart;
    }

    /**
     * @return 补位符(长度不足时，用该符合补足)
     */
    public String getCover() {
        return cover;
    }

    /**
     * @param cover 
	 *            补位符(长度不足时，用该符合补足)
     */
    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    /**
     * @return 是否以该数据作为流水依据:1是0否
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            是否以该数据作为流水依据:1是0否
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

	public String getValName() {
		return valName;
	}

	public void setValName(String valName) {
		this.valName = valName;
	}
    
    
}