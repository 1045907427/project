/**
 * @(#)SysNumberRule.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-25 panxiaoxiao 创建版本
 */
package com.hd.agent.system.model;

import java.io.Serializable;
/**
 * 单据编号实体类
 * @author pan_xx
 *
 */
public class SysNumber implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String numberid;

    /**
     * 表名(t_sys_user)
     */
    private String tablename;

    /**
     * 原状态
     */
    private String oldValue;
    
    /**
     * 新状态
     */
    private String newValue;
    public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	/**
     * 单据编码
     */
    private String billcode;

    /**
     * 单据名称
     */
    private String billname;

    /**
     * 编号规则名称
     */
    private String name;

    /**
     * 备注说明
     */
    private String remark;

    /**
     * 状态1:启用0:停用
     */
    private String state;
    
    /**
     * 状态名称
     */
    private String stateName;

    /**
     * 类型:1系统预制，2系统自建
     */
    private String type;

    /**
     * 预览效果
     */
    private String preview;

    /**
     * 单据编号自动生成1:是2:否
     */
    private String autocreate;

    /**
     * 是否允许修改1允许2不允许
     */
    private String modifyflag;

    /**
     * 当前流水号
     */
    private String serialnumber;
    
    /**
     * 自动生成后的流水号起始值
     */
    private Integer afterserialstart;

	/**
     * @return 自动生成后的流水号起始值
     */
	public Integer getAfterserialstart() {
		return afterserialstart;
	}

	/**
     * @param afterserialstart
	 *            自动生成后的流水号起始值
     */
	public void setAfterserialstart(Integer afterserialstart) {
		this.afterserialstart = afterserialstart;
	}

	/**
     * 流水号长度
     */
    private Integer seriallength;

    /**
     * 流水号步长(每次增加多少)
     */
    private Integer serialstep;

    /**
     * 流水号起始值
     */
    private Integer serialstart;

    /**
     * 流水号依据字段值
     */
    private String testvalue;

    /**
     * @return 编号
     */
    public String getNumberid() {
        return numberid;
    }

    /**
     * @param numberid 
	 *            编号
     */
    public void setNumberid(String numberid) {
        this.numberid = numberid == null ? null : numberid.trim();
    }

    /**
     * @return 表名(t_sys_user)
     */
    public String getTablename() {
        return tablename;
    }

    /**
     * @param tablename 
	 *            表名(t_sys_user)
     */
    public void setTablename(String tablename) {
        this.tablename = tablename == null ? null : tablename.trim();
    }

    /**
     * @return 单据编码
     */
    public String getBillcode() {
        return billcode;
    }

    /**
     * @param billcode 
	 *            单据编码
     */
    public void setBillcode(String billcode) {
        this.billcode = billcode == null ? null : billcode.trim();
    }

    /**
     * @return 单据名称
     */
    public String getBillname() {
        return billname;
    }

    /**
     * @param billname 
	 *            单据名称
     */
    public void setBillname(String billname) {
        this.billname = billname == null ? null : billname.trim();
    }

    /**
     * @return 编号规则名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            编号规则名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return 备注说明
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark 
	 *            备注说明
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * @return 状态1:启用0:停用
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态1:启用0:停用
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * @return 类型:1系统预制，2系统自建
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            类型:1系统预制，2系统自建
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * @return 预览效果
     */
    public String getPreview() {
        return preview;
    }

    /**
     * @param preview 
	 *            预览效果
     */
    public void setPreview(String preview) {
        this.preview = preview == null ? null : preview.trim();
    }

    /**
     * @return 单据编号自动生成1:是2:否
     */
    public String getAutocreate() {
        return autocreate;
    }

    /**
     * @param autocreate 
	 *            单据编号自动生成1:是2:否
     */
    public void setAutocreate(String autocreate) {
        this.autocreate = autocreate == null ? null : autocreate.trim();
    }

    /**
     * @return 是否允许修改1允许2不允许
     */
    public String getModifyflag() {
        return modifyflag;
    }

    /**
     * @param modifyflag 
	 *            是否允许修改1允许2不允许
     */
    public void setModifyflag(String modifyflag) {
        this.modifyflag = modifyflag == null ? null : modifyflag.trim();
    }

    /**
     * @return 当前流水号
     */
    public String getSerialnumber() {
        return serialnumber;
    }

    /**
     * @param serialnumber 
	 *            当前流水号
     */
    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    /**
     * @return 流水号长度
     */
    public Integer getSeriallength() {
        return seriallength;
    }

    /**
     * @param seriallength 
	 *            流水号长度
     */
    public void setSeriallength(Integer seriallength) {
        this.seriallength = seriallength;
    }

    /**
     * @return 流水号步长(每次增加多少)
     */
    public Integer getSerialstep() {
        return serialstep;
    }

    /**
     * @param serialstep 
	 *            流水号步长(每次增加多少)
     */
    public void setSerialstep(Integer serialstep) {
        this.serialstep = serialstep;
    }

    /**
     * @return 流水号起始值
     */
    public Integer getSerialstart() {
        return serialstart;
    }

    /**
     * @param serialstart 
	 *            流水号起始值
     */
    public void setSerialstart(Integer serialstart) {
        this.serialstart = serialstart;
    }

    /**
     * @return 流水号依据字段值
     */
    public String getTestvalue() {
        return testvalue;
    }

    /**
     * @param testvalue 
	 *            流水号依据字段值
     */
    public void setTestvalue(String testvalue) {
        this.testvalue = testvalue == null ? null : testvalue.trim();
    }

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
    
}