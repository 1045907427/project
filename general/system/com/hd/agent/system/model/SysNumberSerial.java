package com.hd.agent.system.model;

import java.io.Serializable;

public class SysNumberSerial implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 单据编号流水id
     */
    private Integer id;

    /**
     * 单据编号规则id
     */
    private String numberid;

    /**
     * 流水字段值
     */
    private String serialkey;

    /**
     * 当前流水号
     */
    private String serialval;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
     * @return 流水字段值
     */
    public String getSerialkey() {
        return serialkey;
    }

    /**
     * @param serialkey 
	 *            流水字段值
     */
    public void setSerialkey(String serialkey) {
        this.serialkey = serialkey == null ? null : serialkey.trim();
    }

    /**
     * @return 当前流水号
     */
    public String getSerialval() {
        return serialval;
    }

    /**
     * @param serialval 
	 *            当前流水号
     */
    public void setSerialval(String serialval) {
        this.serialval = serialval == null ? null : serialval.trim();
    }
}