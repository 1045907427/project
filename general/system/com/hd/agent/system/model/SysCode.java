package com.hd.agent.system.model;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 系统公共代码
 * @author chenwei
 */
public class SysCode implements Serializable {
    private static final long serialVersionUID = 1L;
	
	 /**
     * 代码
     */
    private String code;

    /**
     * 代码类型
     */
    private String type;
    
    /**
     * 代码名称
     */
    private String codename;
    /**
     * 代码值
     */
    private String codevalue;

    /**
     * 代码类型名称
     */
    private String typename;
    
    /**
     * 价格套价格
     */
    private BigDecimal val;

    /**
     * 价格套箱价
     */
    private BigDecimal boxval;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 状态1有效0无效
     */
    private String state;

    /**
     * @return 代码名称
     */
    public String getCodename() {
        return codename;
    }

    /**
     * @param codename 
	 *            代码名称
     */
    public void setCodename(String codename) {
        this.codename = codename == null ? null : codename.trim();
    }

    /**
     * @return 代码类型名称
     */
    public String getTypename() {
        return typename;
    }

    /**
     * @param typename 
	 *            代码类型名称
     */
    public void setTypename(String typename) {
        this.typename = typename == null ? null : typename.trim();
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
     * @return 状态1有效0无效
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态1有效0无效
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
	/**
     * @return 代码
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code 
	 *            代码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getCodevalue() {
        return codevalue;
    }

    public void setCodevalue(String codevalue) {
        this.codevalue = codevalue == null ? null : codevalue.trim();
    }

    /**
     * @return 代码类型
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            代码类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

	public BigDecimal getVal() {
		return val;
	}

	public void setVal(BigDecimal val) {
		this.val = val;
	}

    public BigDecimal getBoxval() {
        return boxval;
    }

    public void setBoxval(BigDecimal boxval) {
        this.boxval = boxval;
    }
}